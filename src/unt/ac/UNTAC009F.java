package unt.ac;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Map;
import java.util.Vector;

import unt.gr.UNTGR009R2;
import util.BeanLocker;
import util.Common;
import util.Database;
import util.Datetime;
import util.QueryBean;
import util.TCGHCommon;
import util.User;

public class UNTAC009F extends QueryBean{
	String organID;
	String userID;
	String userName;
	String toggleAll;
	String strKeySet[];
	public String getOrganID(){ return checkGet(organID); }
	public void setOrganID(String s){ organID=checkSet(s); }
	public String getUserID(){ return checkGet(userID); }
	public void setUserID(String s){ userID=checkSet(s); }
	public String getUserName(){ return checkGet(userName); }
	public void setUserName(String s){ userName=checkSet(s); }
	public String getToggleAll(){ if (!"".equals(checkGet(toggleAll))) return "checked"; else return "unchecked"; }    
	public void setToggleAll(String s){ toggleAll=checkSet(s); }
	public String[] getsKeySet(){ return strKeySet; }
	public void setsKeySet(String[] s){ strKeySet=s; }

	public String getLockId(String organid) {
		return Common.get(organid) + "執行月結作業";
	}
	/**
	 * <br>
	 * <br>目的：依查詢欄位查詢多筆資料
	 * <br>參數：無
	 * <br>傳回：傳回ArrayList
	*/
	public ArrayList queryAll() throws Exception{

		Database db = new Database();
		ArrayList objList=new ArrayList();
		Map<String,String> differenceMap = TCGHCommon.getSysca_Code("DFK"); //財產區分別
		try {
			String sql = " select enterorg, " +
			" differencekind, " +
			" closing1ym, " +
			" isnull(closing1id,'') closing1id, " +
			" isnull(closing1name,'') closing1name, " +
			" closing1date, " +
			" isnull(restoration1id,'') restoration1id, " +
			" isnull(restoration1name,'') restoration1name, " +
			" restoration1date " +
			" from UNTAC_CLOSINGNE " +
			" where enterorg='" + getOrganID() + "'";

			//System.out.println(sql);
			ResultSet rs = db.querySQL(sql,true);
			processCurrentPageAttribute(rs);
			if (getTotalRecord() > 0) {
				int count = getRecordStart();
				int end = getRecordEnd();
				do {
				if (count > end) break;
				String rowArray[]=new String[10];
				rowArray[0]=rs.getString("enterorg");
				rowArray[1]=rs.getString("differencekind");
				rowArray[2]=differenceMap.get(rs.getString("differencekind")); 
				rowArray[3]=Datetime.changeTaiwanYYMM(rs.getString("closing1ym"), "1"); 
				rowArray[4]=rs.getString("closing1id"); 
				if (rs.getString("closing1name") == null) {
					rowArray[5]="";
				} else {
					rowArray[5]=rs.getString("closing1name");;
				}
				rowArray[6]=Datetime.changeTaiwanYYMMDD(rs.getString("closing1date"), "1"); 
				rowArray[7]=rs.getString("restoration1id"); 
				if (rs.getString("restoration1name") == null) {
					rowArray[8]="";
				} else {
					rowArray[8]=rs.getString("restoration1name");;
				}
				rowArray[9]=Datetime.changeTaiwanYYMMDD(rs.getString("restoration1date"), "1");
				objList.add(rowArray);
				count++;
				} while (rs.next());
			}
			setStateQueryAllSuccess();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.closeAll();
		}
		return objList;
	}
	
	
	
	/**
	 * <br>
	 * <br>目的：月結處理
	 * <br>參數：無
	 * <br>傳回：傳回ArrayList
	*/
	public void Closing(User user) throws Exception{
		int getcut=0;
		if(getsKeySet()!=null) {			
			String lockid = this.getLockId(user.getOrganID());
			if (BeanLocker.isLocked(lockid)) {
				this.setErrorMsg(BeanLocker.getLockedMsg(lockid));
			} else {
				BeanLocker.forceLock(user, lockid, "月結作業未完成，不可再執行月結作業資料");

				getcut = getsKeySet().length;	//有勾選的list中資料數
				Database db = new Database();  
				String[] strKeys = null;
				int i = 0;	
				try {
					for (i=0; i<getcut; i++) {
						strKeys = getsKeySet()[i].split(",");						
						String YYYMM = Datetime.getMonthDay(strKeys[2], 1, "month");	//取得closing1ym的下一個月
						
						insertDataIntoUNTGR_UNTGR009R2()		// 寫入次月初始檔
								._byEnterOrg(strKeys[0])
								._bydifferenceKind(strKeys[1])
								._byYYYMM(YYYMM)
								.ac();

						UpdateUNTAC_CLOSINGNE(db,YYYMM,strKeys[0],strKeys[1]);				//UPDATE 物品月結資料檔
						
						setErrorMsg("月結執行完成");
					}
				} catch (Exception e) {
					setErrorMsg("執行月結發生錯誤，已回復月結前狀態。(" + e.getMessage() + ")");
					e.printStackTrace();
				} finally {
					db.closeAll();
					BeanLocker.forceUnlock(lockid);
				}
			}
		} else {
			setErrorMsg("請勾選資料");
		}
	}
	
		//=================================================================
		private MethodHelper insertDataIntoUNTGR_UNTGR009R2(){
			return new MethodHelper();
		}
		private class MethodHelper{
			private String YYYMM;
			private String enterOrg;
			private String differenceKind;
			public MethodHelper _byEnterOrg(String enterOrg){this.enterOrg = enterOrg;return this;}
			public MethodHelper _byYYYMM(String YYYMM){this.YYYMM = YYYMM;return this;}
			public MethodHelper _bydifferenceKind(String differenceKind){this.differenceKind = differenceKind;return this;}
			public void ac(){
				String[] datas = new String[3];
				datas[0] = YYYMM;
				datas[1] = enterOrg;
				datas[2] = differenceKind;
				execInputData(datas);
			}
		}
			
		//=================================================================
		

	/**
	 * <br>
	 * <br>目的：檢查下列「月結table」是否存在，其中「YYYMM(民國年)」代表「勾選資料.最後月結年月(closing1YM).下一個月」，若不存在則比照主檔create table
	 * <br>參數：db Database
	 * <br>參數：YYYMM 民國年月
	 * <br>傳回：無
	*/
	public void creatTableUNTNE_NONEXP_YYYMM(Database db,String YYYMM) {
		try {
			String sql = "select COUNT(*) as count from sys.sysobjects where name = 'UNTNE_NONEXP_" + YYYMM + "'";
			String count = queryData_NoChange(sql);				
			
			if("0".equals(count)){
				sql = "select * into UNTNE_NONEXP_" + YYYMM + " from UNTNE_NONEXP;";
				sql += "truncate table UNTNE_NONEXP_" + YYYMM + " ;";
				execData_NoChange(sql);
				
				creatIndexUNTNE_NONEXP_YYYMM(db,YYYMM);
			}
//			StringBuffer CreatSQL = new StringBuffer("");
//			CreatSQL.append("CREATE TABLE [dbo].[UNTNE_NONEXP_").append(YYYMM).append("](").append(
//					" [enterorg] [nvarchar](10) NOT NULL,").append(
//					" [ownership] [nvarchar](1) NOT NULL,").append(
//					" [caseno] [nvarchar](10) NOT NULL,").append(
//					" [differencekind] [nvarchar](3) NOT NULL,").append(
//					" [caseserialno] [decimal](5, 0) NULL,").append(
//					" [propertyno] [nvarchar](12) NOT NULL,").append(
//					" [lotno] [nvarchar](7) NOT NULL,").append(
//					" [serialnos] [nvarchar](7) NOT NULL,").append(
//					" [serialnoe] [nvarchar](7) NOT NULL,").append(
//					" [otherpropertyunit] [nvarchar](50) NULL,").append(
//					" [othermaterial] [nvarchar](50) NULL,").append(
//					" [otherlimityear] [decimal](3, 0) NULL,").append(
//					" [propertyname1] [nvarchar](40) NULL,").append(
//					" [cause] [nvarchar](10) NULL,").append(
//					" [cause1] [nvarchar](40) NULL,").append(
//					" [approvedate] [nvarchar](8) NULL,").append(
//					" [approvedoc] [nvarchar](40) NULL,").append(
//					" [enterdate] [nvarchar](8) NULL,").append(
//					" [buydate] [nvarchar](8) NOT NULL,").append(
//					" [datastate] [nvarchar](1) NOT NULL,").append(
//					" [verify] [nvarchar](1) NOT NULL,").append(
//					" [propertykind] [nvarchar](2) NOT NULL,").append(
//					" [fundtype] [nvarchar](13) NULL,").append(
//					" [valuable] [nvarchar](1) NOT NULL,").append(
//					" [originalamount] [decimal](7, 0) NOT NULL,").append(
//					" [originalunit] [decimal](13, 0) NULL,").append(
//					" [originalbv] [decimal](15, 0) NOT NULL,").append(
//					" [originalnote] [nvarchar](40) NULL,").append(
//					" [accountingtitle] [nvarchar](40) NULL,").append(
//					" [bookamount] [decimal](7, 0) NOT NULL,").append(
//					" [bookvalue] [decimal](15, 0) NOT NULL,").append(
//					" [fundssource] [nvarchar](10) NULL,").append(
//					" [fundssource1] [nvarchar](40) NULL,").append(
//					" [grantvalue] [decimal](15, 0) NULL,").append(
//					" [articlename] [nvarchar](20) NULL,").append(
//					" [nameplate] [nvarchar](60) NULL,").append(
//					" [specification] [nvarchar](80) NULL,").append(
//					" [storeno] [nvarchar](10) NULL,").append(
//					" [sourcekind] [nvarchar](10) NULL,").append(
//					" [sourcedate] [nvarchar](8) NULL,").append(
//					" [sourcedoc] [nvarchar](40) NULL,").append(
//					" [oldpropertyno] [nvarchar](12) NULL,").append(
//					" [oldserialnos] [nvarchar](20) NULL,").append(
//					" [oldserialnoe] [nvarchar](20) NULL,").append(
//					" [notes] [nvarchar](500) NULL,").append(
//					" [editid] [nvarchar](20) NULL,").append(
//					" [editdate] [nvarchar](8) NULL,").append(
//					" [edittime] [nvarchar](6) NULL,").append(
//					" [originallimityear] [decimal](3) NULL ").append(
//					" ) ON [PRIMARY]");
//			db.excuteSQL(CreatSQL.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * <br>
	 * <br>目的：檢查下列「月結table」是否存在，其中「YYYMM(民國年)」代表「勾選資料.最後月結年月(closing1YM).下一個月」，若不存在則比照主檔create index
	 * <br>參數：db Database
	 * <br>參數：YYYMM 民國年月
	 * <br>傳回：無
	*/
	public void creatIndexUNTNE_NONEXP_YYYMM(Database db,String YYYMM) {
		try {
			StringBuffer IndexSQL1 = new StringBuffer(""); //索引1
			StringBuffer IndexSQL2 = new StringBuffer(""); //索引2
			IndexSQL1.append(" CREATE UNIQUE NONCLUSTERED INDEX [UNTNE_NONEXP_").append(YYYMM).append("_IDX1] ON [dbo].[UNTNE_NONEXP_").append(YYYMM).append("]").append(
				" (").append(
				" [enterorg] ASC,").append(
				" [ownership] ASC,").append(
				" [differencekind] ASC,").append(
				" [propertyno] ASC,").append(
				" [lotno] ASC").append(
				" )WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF,").append(
				" SORT_IN_TEMPDB = OFF, IGNORE_DUP_KEY = OFF, DROP_EXISTING = OFF,").append(
				" ONLINE = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]");
			IndexSQL2.append(" CREATE NONCLUSTERED INDEX [UNTNE_NONEXP_").append(YYYMM).append("_IDX2] ON [dbo].[UNTNE_NONEXP_").append(YYYMM).append("]").append(
				" (").append(
				" [enterorg] ASC,").append(
				" [caseno] ASC").append(
				" )WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF,").append(
				" SORT_IN_TEMPDB = OFF, IGNORE_DUP_KEY = OFF, DROP_EXISTING = OFF,").append(
				" ONLINE = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]");
				db.excuteSQL(IndexSQL1.append(IndexSQL2).toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * <br>
	 * <br>目的：檢查下列「月結table」是否存在，其中「YYYMM(民國年)」代表「勾選資料.最後月結年月(closing1YM).下一個月」，若不存在則比照主檔create table
	 * <br>參數：db Database
	 * <br>參數：YYYMM 民國年月
	 * <br>傳回：無
	*/
	public void creatTableUNTNE_NONEXPDETAIL_YYYMM(Database db,String YYYMM) {
		try {
			String sql = "select COUNT(*) as count from sys.sysobjects where name = 'UNTNE_NONEXPDETAIL_" + YYYMM + "'";
			String count = queryData_NoChange(sql);				
			
			if("0".equals(count)){
				sql = "select * into UNTNE_NONEXPDETAIL_" + YYYMM + " from UNTNE_NONEXPDETAIL;";
				sql += "truncate table UNTNE_NONEXPDETAIL_" + YYYMM + " ;";
				execData_NoChange(sql);
				creatIndexUNTNE_NONEXPDETAIL_YYYMM(db,YYYMM);
			}
			
//			StringBuffer CreatSQL = new StringBuffer("");
//			CreatSQL.append("CREATE TABLE [dbo].[UNTNE_NONEXPDETAIL_").append(YYYMM).append("](").append(
//				" [enterorg] [nvarchar](10) NOT NULL,").append(
//				" [ownership] [nvarchar](1) NOT NULL,").append(
//				" [caseno] [nvarchar](10) NOT NULL,").append(
//				" [differencekind] [nvarchar](3) NOT NULL,").append(
//				" [propertyno] [nvarchar](12) NOT NULL,").append(
//				" [lotno] [nvarchar](7) NOT NULL,").append(
//				" [serialno] [nvarchar](7) NOT NULL,").append(
//				" [datastate] [nvarchar](1) NOT NULL,").append(
//				" [reducedate] [nvarchar](8) NULL,").append(
//				" [reducecause] [nvarchar](10) NULL,").append(
//				" [reducecause1] [nvarchar](40) NULL,").append(
//				" [verify] [nvarchar](1) NOT NULL,").append(
//				" [enterdate] [nvarchar](8) NULL,").append(
//				" [originalamount] [decimal](7, 0) NOT NULL,").append(
//				" [originalbv] [decimal](15, 0) NOT NULL,").append(
//				" [bookamount] [decimal](7, 0) NOT NULL,").append(
//				" [bookvalue] [decimal](15, 0) NOT NULL,").append(
//				" [licenseplate] [nvarchar](20) NULL,").append(
//				" [purpose] [nvarchar](100) NULL,").append(
//				" [originalmovedate] [nvarchar](8) NULL,").append(
//				" [originalkeepunit] [nvarchar](10) NULL,").append(
//				" [originalkeeper] [nvarchar](10) NULL,").append(
//				" [originaluseunit] [nvarchar](10) NULL,").append(
//				" [originaluser] [nvarchar](10) NULL,").append(
//				" [originalusernote] [nvarchar](20) NULL,").append(
//				" [originalplace1] [nvarchar](10) NULL,").append(
//				" [originalplace] [nvarchar](50) NULL,").append(
//				" [movedate] [nvarchar](8) NULL,").append(
//				" [keepunit] [nvarchar](10) NULL,").append(
//				" [keeper] [nvarchar](10) NULL,").append(
//				" [useunit] [nvarchar](10) NULL,").append(
//				" [userno] [nvarchar](10) NULL,").append(
//				" [usernote] [nvarchar](20) NULL,").append(
//				" [place1] [nvarchar](10) NULL,").append(
//				" [place] [nvarchar](50) NULL,").append(
//				" [notes1] [nvarchar](120) NULL,").append(
//				" [barcode] [nvarchar](24) NULL,").append(
//				" [notes] [nvarchar](500) NULL,").append(
//				" [oldpropertyno] [nvarchar](12) NULL,").append(
//				" [oldserialno] [nvarchar](20) NULL,").append(
//				" [editid] [nvarchar](20) NULL,").append(
//				" [editdate] [nvarchar](8) NULL,").append(
//				" [edittime] [nvarchar](6) NULL,").append(
//				" [originallimityear] [decimal](3) NULL,").append(
//				" [otherlimityear] [decimal](4) NULL ").append(
//				" ) ON [PRIMARY]");
//			db.excuteSQL(CreatSQL.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * <br>
	 * <br>目的：檢查下列「月結table」是否存在，其中「YYYMM(民國年)」代表「勾選資料.最後月結年月(closing1YM).下一個月」，若不存在則比照主檔create index
	 * <br>參數：db Database
	 * <br>參數：YYYMM 民國年月
	 * <br>傳回：無
	*/
	public void creatIndexUNTNE_NONEXPDETAIL_YYYMM(Database db,String YYYMM) {
		try {
			StringBuffer IndexSQL1 = new StringBuffer(""); //索引1
			StringBuffer IndexSQL2 = new StringBuffer(""); //索引2
			StringBuffer IndexSQL3 = new StringBuffer(""); //索引3
			IndexSQL1.append(" CREATE UNIQUE NONCLUSTERED INDEX [UNTNE_NONEXPDETAIL_").append(YYYMM).append("_IDX1] ON [dbo].[UNTNE_NONEXPDETAIL_").append(YYYMM).append("]").append(
				" (").append(
				" [enterorg] ASC,").append(
				" [ownership] ASC,").append(
				" [differencekind] ASC,").append(
				" [propertyno] ASC,").append(
				" [serialno] ASC").append(
				" )WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF,").append(
				" SORT_IN_TEMPDB = OFF, IGNORE_DUP_KEY = OFF, DROP_EXISTING = OFF,").append(
				" ONLINE = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]");
			IndexSQL2.append(" CREATE NONCLUSTERED INDEX [UNTNE_NONEXPDETAIL_").append(YYYMM).append("_IDX2] ON [dbo].[UNTNE_NONEXPDETAIL_").append(YYYMM).append("]").append(
				" (").append(
				" [enterorg] ASC,").append(
				" [ownership] ASC,").append(
				" [differencekind] ASC,").append(
				" [propertyno] ASC,").append(
				" [lotno] ASC").append(
				" )WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF," +
				" SORT_IN_TEMPDB = OFF, IGNORE_DUP_KEY = OFF, DROP_EXISTING = OFF," +
				" ONLINE = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]");
				db.excuteSQL(IndexSQL1.append(IndexSQL2).toString());
			IndexSQL3.append(" CREATE NONCLUSTERED INDEX [UNTNE_NONEXPDETAIL_").append(YYYMM).append("_IDX3] ON [dbo].[UNTNE_NONEXPDETAIL_").append(YYYMM).append("]").append(
				" (").append(
				" [enterorg] ASC,").append(
				" [caseno] ASC").append(
				" )WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF," +
				" SORT_IN_TEMPDB = OFF, IGNORE_DUP_KEY = OFF, DROP_EXISTING = OFF," +
				" ONLINE = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]");
				db.excuteSQL(IndexSQL1.append(IndexSQL2).append(IndexSQL3).toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * <br>
	 * <br>目的：依勾選的資料之「入帳機關(enterOrg)、財產區分別(differenceKind)、勾選資料.最後月結年月(closing1YM).下一個月」刪除「各類別月結table」資料
	 * <br>參數：DB Database
	 * <br>參數：YYYMM 民國年月
	 * <br>參數：ENTERORG 機關代號
	 * <br>參數：DIFFERENCEKIND 財產區分別
	 * <br>傳回：無
	*/
	public void DeleteUNTNE_NONEXP_YYYMM(Database db,String YYYMM,String enterorg,String differencekind) throws Exception {
		try {
			StringBuffer DeleteSQL = new StringBuffer("");
			DeleteSQL.append("delete from UNTNE_NONEXP_").append(YYYMM).append(
				" where enterorg=").append(Common.sqlChar(enterorg)).append(
				" and differencekind=").append(Common.sqlChar(differencekind));
			db.excuteSQL(DeleteSQL.toString());
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
	
	/**
	 * <br>
	 * <br>目的：依勾選的資料之「入帳機關(enterOrg)、財產區分別(differenceKind)、勾選資料.最後月結年月(closing1YM).下一個月」刪除「各類別月結table」資料
	 * <br>參數：DB Database
	 * <br>參數：YYYMM 民國年月
	 * <br>參數：ENTERORG 機關代號
	 * <br>參數：DIFFERENCEKIND 財產區分別
	 * <br>傳回：無
	*/
	public void DeleteUNTNE_NONEXPDETAIL_YYYMM(Database db,String YYYMM,String enterorg,String differencekind) throws Exception {
		try {
			StringBuffer DeleteSQL = new StringBuffer("");
			DeleteSQL.append("delete from UNTNE_NONEXPDETAIL_").append(YYYMM).append(
				" where enterorg=").append(Common.sqlChar(enterorg)).append(
				" and differencekind=").append(Common.sqlChar(differencekind));
			db.excuteSQL(DeleteSQL.toString());
			db.excuteSQL(DeleteSQL.toString());
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
	
	/**
	 * <br>
	 * <br>目的：依勾選的資料之「入帳機關(enterOrg)、財產區分別(differenceKind) 、勾選資料.最後月結年月(closing1YM).下一個月」將「各類別主檔table」資料新增至「各類別月結table」
	 * <br>參數：DB Database
	 * <br>參數：YYYMM 民國年月
	 * <br>參數：ENTERORG 機關代號
	 * <br>參數：DIFFERENCEKIND 財產區分別
	 * <br>傳回：無
	*/
	public void InsertUNTNE_NONEXP_YYYMM(Database db,String YYYMM,String enterorg,String differencekind) throws Exception {
		try {
			StringBuffer InsertSQL = new StringBuffer("");
			InsertSQL.append("insert into UNTNE_NONEXP_").append(YYYMM).append(
				" select * from UNTNE_NONEXP").append(
				" where enterorg=").append(Common.sqlChar(enterorg)).append(
				" and differencekind=").append(Common.sqlChar(differencekind));
			db.excuteSQL(InsertSQL.toString());
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
	
	/**
	 * <br>
	 * <br>目的：依勾選的資料之「入帳機關(enterOrg)、財產區分別(differenceKind) 、勾選資料.最後月結年月(closing1YM).下一個月」將「各類別主檔table」資料新增至「各類別月結table」
	 * <br>參數：DB Database
	 * <br>參數：YYYMM 民國年月
	 * <br>參數：ENTERORG 機關代號
	 * <br>參數：DIFFERENCEKIND 財產區分別
	 * <br>傳回：無
	*/
	public void InsertUNTNE_NONEXPDETAIL_YYYMM(Database db,String YYYMM,String enterorg,String differencekind) throws Exception {
		try {
			StringBuffer InsertSQL = new StringBuffer("");
			InsertSQL.append("insert into UNTNE_NONEXPDETAIL_").append(YYYMM).append(
			" select * from UNTNE_NONEXPDETAIL").append(
			" where enterorg=").append(Common.sqlChar(enterorg)).append(
			" and differencekind=").append(Common.sqlChar(differencekind));
			db.excuteSQL(InsertSQL.toString());
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
	
	/**
	 * <br>
	 * <br>目的：依勾選的資料之「入帳機關(enterOrg)、財產區分別(differenceKind)」修改「物品月結紀錄檔(UNTAC_ClosingNE)」相關欄位
	 * <br>參數：DB Database
	 * <br>參數：YYYMM 民國年月
	 * <br>參數：ENTERORG 機關代號
	 * <br>參數：DIFFERENCEKIND 財產區分別
	 * <br>傳回：無
	*/
	public void UpdateUNTAC_CLOSINGNE(Database db,String YYYMM,String enterorg,String differencekind) throws Exception {
		try {
			StringBuffer UpdateSQL = new StringBuffer("");
			UpdateSQL.append("update UNTAC_CLOSINGNE ").append(
			" set ").append(
			" closing1ym = ").append(Common.sqlChar(Datetime.changeTaiwanYYMM(YYYMM, "2"))).append(",").append(
			" closing1id = ").append(Common.sqlChar(getUserID())).append(",").append(
			" closing1name = ").append(Common.sqlChar(getUserName())).append(",").append(
			" closing1date = ").append(Common.sqlChar(Datetime.getYYYYMMDD())).append("").append(
			" where enterorg=").append(Common.sqlChar(enterorg)).append(
			" and differencekind=").append(Common.sqlChar(differencekind));
			db.excuteSQL(UpdateSQL.toString());
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
	
	
	//=================================================
	private String queryData_NoChange(String sql){
		Database db = new Database();
		ResultSet rs = null;
		String result = "";
		try{
			rs = db.querySQL_NoChange(sql);
			if(rs.next()){
				result = rs.getString("count");
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			db.closeAll();
		}
		return result;
	}
	//=================================================
	private String execData_NoChange(String sql){
		Database db = new Database();
		String result = "";
		try{
			db.excuteSQL_NoChange(new String[]{sql});				
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			db.closeAll();
		}
		return result;
	}
	
	//=================================================
		private void execInputData(String[] datas){
			String sql = null;
			String ownership = null;
			String YYYMMplusOne = Datetime.getMonthDay(datas[0], 1, "month");
			String enterOrg = datas[1];
			String differenceKind = datas[2];
			Vector vector = null;
			boolean isValuable = true;
			
			UNTGR009R2 untgr = new UNTGR009R2();
			untgr.setQ_enterOrg(enterOrg);
			untgr.setQ_differenceKind(differenceKind);
			
			// 現在月結1月時產生1月初始檔, Q_reportYear這樣設定會有問題 
			// ex: YYYMMplusOne=10601 => Q_reportYear=106 but Q_enterDateS=10512
			// getResultModel()時會變成查詢10612
//			untgr.setQ_reportYear(YYYMMplusOne.substring(0,3));
//			untgr.setQ_reportMonth(YYYMMplusOne);
			untgr.setQ_enterDateS(Datetime.getMonthDay(YYYMMplusOne + "01", -1, "month"));
			untgr.setQ_enterDateE(Datetime.getMonthDay(YYYMMplusOne + "01", -1, "day"));
			// 暫時修正為取Q_enterDateS年月, 後續修改成月結12月產生1月初始檔時, 請確認是否要再修正!!! 
			untgr.setQ_reportYear(untgr.getQ_enterDateS().substring(0,3));
			untgr.setQ_reportMonth(untgr.getQ_enterDateS().substring(3,5));
			
			untgr.setQ_isorganmanager("N");
			untgr.setQ_verify("Y");

 
			for(int temp = 0; temp < 2; temp++){
				if(isValuable){	untgr.setQ_valuable("Y");
				}else{			untgr.setQ_valuable("N");
				}
				
				for(int i = 1; i <= 5; i++){
					untgr.setQ_ownership(String.valueOf(i));				
					
					try{
						ownership = String.valueOf(i);
						
						vector = untgr.getResultModel().getDataVector();
						
						String[] data = splitData(vector.get(0).toString());
						
						//土地:「10」			
						sql = combinationSQL_forUNTGR009R2(data,"10",ownership,isValuable,YYYMMplusOne,enterOrg,differenceKind);
						execData(sql);
						
						//土地改良物:「20」
						sql = combinationSQL_forUNTGR009R2(data,"20",ownership,isValuable,YYYMMplusOne,enterOrg,differenceKind);
						execData(sql);
						
						//房屋建築及設備  辦公房屋:「31」
						sql = combinationSQL_forUNTGR009R2(data,"31",ownership,isValuable,YYYMMplusOne,enterOrg,differenceKind);
						execData(sql);
						
						//房屋建築及設備  宿舍：「32」
						sql = combinationSQL_forUNTGR009R2(data,"32",ownership,isValuable,YYYMMplusOne,enterOrg,differenceKind);
						execData(sql);
						
						//房屋建築及設備  其他：「33」
						sql = combinationSQL_forUNTGR009R2(data,"33",ownership,isValuable,YYYMMplusOne,enterOrg,differenceKind);
						execData(sql);
						
						//機械及設備:「40」
						sql = combinationSQL_forUNTGR009R2(data,"40",ownership,isValuable,YYYMMplusOne,enterOrg,differenceKind);
						execData(sql);
						
						//交通及運輸設備  船：「51」
						sql = combinationSQL_forUNTGR009R2(data,"51",ownership,isValuable,YYYMMplusOne,enterOrg,differenceKind);
						execData(sql);
						
						//交通及運輸設備  飛機：「52」
						sql = combinationSQL_forUNTGR009R2(data,"52",ownership,isValuable,YYYMMplusOne,enterOrg,differenceKind);
						execData(sql);
						
						//交通及運輸設備  汽（機）車：「53」
						sql = combinationSQL_forUNTGR009R2(data,"53",ownership,isValuable,YYYMMplusOne,enterOrg,differenceKind);
						execData(sql);
						
						//交通及運輸設備  其他：「54」
						sql = combinationSQL_forUNTGR009R2(data,"54",ownership,isValuable,YYYMMplusOne,enterOrg,differenceKind);
						execData(sql);
						
						//雜項設備  圖書：「61」
						sql = combinationSQL_forUNTGR009R2(data,"61",ownership,isValuable,YYYMMplusOne,enterOrg,differenceKind);
						execData(sql);
						
						if(isValuable){
							//雜項設備  博物：「62」(限「珍貴」)
							sql = combinationSQL_forUNTGR009R2(data,"62",ownership,isValuable,YYYMMplusOne,enterOrg,differenceKind);
							execData(sql);
						}
						
						//雜項設備  其他：「63」
						sql = combinationSQL_forUNTGR009R2(data,"63",ownership,isValuable,YYYMMplusOne,enterOrg,differenceKind);
						execData(sql);
						
						//有價證券：「70」(限「一般」)
						sql = combinationSQL_forUNTGR009R2(data,"70",ownership,isValuable,YYYMMplusOne,enterOrg,differenceKind);
						execData(sql);
						
						//權利：「80」(限「一般」)
						sql = combinationSQL_forUNTGR009R2(data,"80",ownership,isValuable,YYYMMplusOne,enterOrg,differenceKind);
						execData(sql);
						
						//總值：「T0」
						sql = combinationSQL_forUNTGR009R2(data,"T0",ownership,isValuable,YYYMMplusOne,enterOrg,differenceKind);
						execData(sql);
					
						data = null;
					}catch(Exception e){
						e.printStackTrace();
					}
				}
				isValuable = false;
			}
			
			
			
		}
		private String combinationSQL_forUNTGR009R2(String[] data, String type, String ownership, boolean isValuable, String YYYMM, String enterOrg, String differenceKind){
			StringBuilder result = new StringBuilder();
			
			result.append("insert into UNTGR_UNTGR009R2(enterorg, ownership, differencekind, valuable, reporttype, reportyear, reportmonth, reportseason, propertytype, oldamount, oldarea, oldbvsubtotal)values(")
					.append(Common.sqlChar(enterOrg)).append(",")
					.append(Common.sqlChar(ownership)).append(",")
					.append(Common.sqlChar(differenceKind)).append(",");
			
			if(isValuable){		result.append("'Y'").append(",");
			}else{				result.append("'N'").append(",");
			}
					
			result.append(Common.sqlChar("1")).append(",")
					.append(Common.sqlChar(YYYMM.substring(0,3))).append(",")
					.append(Common.sqlChar(YYYMM.substring(3,5))).append(",")
					.append("null").append(",")
					.append(Common.sqlChar(type)).append(",");
	
			//土地:「10」
			if("10".equals(type)){
				result.append(Common.sqlChar(replaceData(data[84]))).append(",")
						.append(Common.sqlChar(replaceData(data[85]))).append(",")
						.append(Common.sqlChar(replaceData(data[86])));
	
			//土地改良物:「20」
			}else if("20".equals(type)){
				result.append(Common.sqlChar(replaceData(data[87]))).append(",")
						.append("null").append(",")
						.append(Common.sqlChar(replaceData(data[88])));
				
			//房屋建築及設備  辦公房屋:「31」
			}else if("31".equals(type)){
				result.append(Common.sqlChar(replaceData(data[89]))).append(",")
						.append(Common.sqlChar(replaceData(data[90]))).append(",")
						.append("null");
				
			//房屋建築及設備  宿舍：「32」
			}else if("32".equals(type)){
				result.append(Common.sqlChar(replaceData(data[91]))).append(",")
						.append(Common.sqlChar(replaceData(data[92]))).append(",")
						.append("null");
				
			//房屋建築及設備  其他：「33」
			}else if("33".equals(type)){
				result.append(Common.sqlChar(replaceData(data[93]))).append(",")								
						.append("null").append(",")
						.append(Common.sqlChar(replaceData(data[94])));
				
			//機械及設備:「40」
			}else if("40".equals(type)){
				result.append(Common.sqlChar(replaceData(data[95]))).append(",")
						.append("null").append(",")
						.append(Common.sqlChar(replaceData(data[96])));	
				
			//交通及運輸設備  船：「51」
			}else if("51".equals(type)){
				result.append(Common.sqlChar(replaceData(data[97]))).append(",")
						.append("null").append(",")
						.append(Common.sqlChar(replaceData(data[101])));
				
			//交通及運輸設備  飛機：「52」
			}else if("52".equals(type)){
				result.append(Common.sqlChar(replaceData(data[98]))).append(",")								
						.append("null").append(",")
						.append("null");
				
			//交通及運輸設備  汽（機）車：「53」
			}else if("53".equals(type)){
				result.append(Common.sqlChar(replaceData(data[99]))).append(",")								
						.append("null").append(",")
						.append("null");
				
			//交通及運輸設備  其他：「54」
			}else if("54".equals(type)){
				result.append(Common.sqlChar(replaceData(data[100]))).append(",")								
						.append("null").append(",")
						.append("null");
				
			//雜項設備  圖書：「61」
			}else if("61".equals(type)){
				result.append(Common.sqlChar(replaceData(data[102]))).append(",")
						.append("null").append(",")
						.append(Common.sqlChar(replaceData(data[105])));
				
			//雜項設備  博物：「62」(限「珍貴」)
			}else if("62".equals(type)){
				result.append(Common.sqlChar(replaceData(data[103]))).append(",")
						.append("null").append(",")
						.append("null");
				
			//雜項設備  其他：「63」
			}else if("63".equals(type)){
				result.append(Common.sqlChar(replaceData(data[104]))).append(",")
						.append("null").append(",")
						.append("null");
				
			//有價證券：「70」(限「一般」)
			}else if("70".equals(type)){
				result.append(Common.sqlChar(replaceData(data[106]))).append(",")
						.append("null").append(",")
						.append(Common.sqlChar(replaceData(data[107])));
				
			//權利：「80」(限「一般」)
			}else if("80".equals(type)){
				result.append(Common.sqlChar(replaceData(data[108]))).append(",")
						.append("null").append(",")
						.append(Common.sqlChar(replaceData(data[109])));						
				
			//總值：「T0」
			}else if("T0".equals(type)){
				result.append(Common.sqlChar(replaceData(data[110]))).append(",")
						.append("null").append(",")
						.append(Common.sqlChar(replaceData(data[111])));
			}
							
			result.append(");");			
			return result.toString();
		}
		
			private String replaceData(String s){
				return s.replace(",","");
			}			
			private String[] splitData(String temp){
				String newtemp = new String(temp.substring(1,temp.length() - 1));
				return newtemp.split(", ");
			}
			private String execData(String sql){
				Database db = new Database();
				String result = "";
				try{
					db.excuteSQL(new String[]{sql});
				}catch(Exception e){
					e.printStackTrace();
				}finally{
					db.closeAll();
				}
				return result;
			}
}