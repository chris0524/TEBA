package unt.ac;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import util.BeanLocker;
import util.Common;
import util.Database;
import util.Datetime;
import util.QueryBean;
import util.TCGHCommon;
import util.User;

public class UNTAC010F extends QueryBean{
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
			" closing2ym, " +
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
				String rowArray[]=new String[11];
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
				rowArray[10]=Datetime.changeTaiwanYYMM(rs.getString("closing2ym"), "1"); 
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
	 * <br>目的：月結回復處理
	 * <br>參數：無
	 * <br>傳回：傳回ArrayList
	*/
	public void ClosingRevert(User user) throws Exception{
		int getcut=0;
		if(getsKeySet()!=null) {
			String lockid = this.getLockId(user.getOrganID());
			if (BeanLocker.isLocked(lockid)) {
				this.setErrorMsg(BeanLocker.getLockedMsg(lockid));
			} else {
				BeanLocker.forceLock(user, lockid, "月結回復作業未完成，不可再執行月結回復作業");
				
				getcut = getsKeySet().length;	//有勾選的list中資料數
				Database db = new Database();  
				String[] strKeys = null;
				Map<String,String> yyymmMap = new HashMap<String,String>();
				int i = 0;	
				try {
					for (i=0; i<getcut; i++) {
						strKeys = getsKeySet()[i].split(",");
						if (strKeys[2].compareTo(strKeys[3]) > 0) {
							String YYYMM = Datetime.getMonthDay(strKeys[2], -1, "month");			//取得closing1ym的前一個月
							String YYYMMplusOne = Datetime.getMonthDay(strKeys[2], 1, "month");		//取得closing1ym的後一個月
							DeleteUNTGR_UNTGR009R2(db,YYYMMplusOne,strKeys[0],strKeys[1]);        	//DELETE TABLE物品初始檔
							UpdateUNTAC_CLOSINGNE(db,YYYMM,strKeys[0],strKeys[1]);					//UPDATE 物品月結資料檔
							setErrorMsg("月結回復執行完成");
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
					setErrorMsg("執行月結回復發生錯誤，回復已月結狀態(" + e.getMessage() + ")");
				} finally {
					db.closeAll();
					BeanLocker.forceUnlock(lockid);
				}
			}
		} else {
			setErrorMsg("請勾選資料");
		}
	}

	/**
	 * <br>
	 * <br>目的：依勾選的資料之「入帳機關(enterOrg)、財產區分別(differenceKind)、最後月結年月(closing1YM)」刪除「月結table」資料，下表「YYYMM(民國年)」代表「最後月結年月(closing1YM)」
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
	 * <br>目的：依勾選的資料之「入帳機關(enterOrg)、財產區分別(differenceKind)、最後月結年月(closing1YM)」刪除「月結table」資料，下表「YYYMM(民國年)」代表「最後月結年月(closing1YM)」
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
	 * <br>目的：2.2	依勾選的資料之「入帳機關(enterOrg)、財產區分別(differenceKind)」修改「物品月結紀錄檔(UNTAC_ClosingNE)」相關欄位
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
			" restoration1id = ").append(Common.sqlChar(getUserID())).append(",").append(
			" restoration1name = ").append(Common.sqlChar(getUserName())).append(",").append(
			" restoration1date = ").append(Common.sqlChar(Datetime.getYYYYMMDD())).append("").append(
			" where enterorg=").append(Common.sqlChar(enterorg)).append(
			" and differencekind=").append(Common.sqlChar(differencekind));
			db.excuteSQL(UpdateSQL.toString());
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
	
	/**
	 * <br>
	 * <br>目的：依勾選的資料之「入帳機關(enterOrg)、財產區分別(differenceKind)、最後月結年月(closing1YM)」刪除「月結table」資料，下表「YYYMM(民國年)」代表「最後月結年月(closing1YM)」
	 * <br>參數：DB Database
	 * <br>參數：YYYMM 民國年月
	 * <br>參數：ENTERORG 機關代號
	 * <br>參數：DIFFERENCEKIND 財產區分別
	 * <br>傳回：無
	*/
	public void DeleteUNTGR_UNTGR009R2(Database db,String YYYMM,String enterorg,String differencekind) throws Exception {
		try {
			StringBuffer DeleteSQL = new StringBuffer("");
			DeleteSQL.append("delete from UNTGR_UNTGR009R2 ").append(
				" where enterorg=").append(Common.sqlChar(enterorg)).append(
				" and differencekind=").append(Common.sqlChar(differencekind)).append(
				" and reportyear=").append(Common.sqlChar(YYYMM.substring(0,3))).append(
				" and reportmonth=").append(Common.sqlChar(YYYMM.substring(3,5)));
			db.excuteSQL(DeleteSQL.toString());
			db.excuteSQL(DeleteSQL.toString());
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}

}