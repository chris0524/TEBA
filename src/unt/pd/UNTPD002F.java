package unt.pd;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Map;

import util.BeanLocker;
import util.Common;
import util.Database;
import util.Datetime;
import util.SuperBean;
import util.TCGHCommon;
import util.User;

/**
 *<br/>程式目的：動產產生盤點資料
 *<br/>程式代號：UNTPD002F
 *<br/>程式日期：
 *<br/>程式作者：shan
 *<br/>--------------------------------------------------------
 *<br/>修改作者　　修改日期　　　修改目的
 *<br/>--------------------------------------------------------
 */
public class UNTPD002F extends SuperBean {
	
	public String getLockId(String organid) {
		return Common.get(organid) + "產製財產盤點資料";
	}

	private String q_enterOrg;
	private String q_enterOrgName;
	public String getQ_enterOrg(){ return checkGet(q_enterOrg); }
	public void setQ_enterOrg(String s){ q_enterOrg=checkSet(s); }
	public String getQ_enterOrgName(){ return checkGet(q_enterOrgName); }
	public void setQ_enterOrgName(String s){ q_enterOrgName=checkSet(s); }
	private String q_verify;
	public String getQ_verify(){ return checkGet(q_verify); }
	public void setQ_verify(String s){ q_verify=checkSet(s); }
	private String q_ownership;
	public String getQ_ownership(){ return checkGet(q_ownership); }
	public void setQ_ownership(String s){ q_ownership=checkSet(s); }
	private String q_propertyNoS;
	private String q_propertyNoE;
	private String q_propertyNoSName;
	private String q_propertyNoEName;
	public String getQ_propertyNoS(){ return checkGet(q_propertyNoS); }
	public void setQ_propertyNoS(String s){ q_propertyNoS=checkSet(s); }
	public String getQ_propertyNoE(){ return checkGet(q_propertyNoE); }
	public void setQ_propertyNoE(String s){ q_propertyNoE=checkSet(s); }

	public String getQ_propertyNoSName(){ return checkGet(q_propertyNoSName); }
	public void setQ_propertyNoSName(String s){ q_propertyNoSName=checkSet(s); }
	public String getQ_propertyNoEName(){ return checkGet(q_propertyNoEName); }
	public void setQ_propertyNoEName(String s){ q_propertyNoEName=checkSet(s); }
	private String q_buyDateS;
	private String q_buyDateE;
	public String getQ_buyDateS(){ return checkGet(q_buyDateS); }
	public void setQ_buyDateS(String s){ q_buyDateS=checkSet(s); }
	public String getQ_buyDateE(){ return checkGet(q_buyDateE); }
	public void setQ_buyDateE(String s){ q_buyDateE=checkSet(s); }
	private String q_enterDateS;
	private String q_enterDateE;
	public String getQ_enterDateS(){ return checkGet(q_enterDateS); }
	public void setQ_enterDateS(String s){ q_enterDateS=checkSet(s); }
	public String getQ_enterDateE(){ return checkGet(q_enterDateE); }
	public void setQ_enterDateE(String s){ q_enterDateE=checkSet(s); }

	private String q_dataState;
	public String getQ_dataState(){ return checkGet(q_dataState); }
	public void setQ_dataState(String s){ q_dataState=checkSet(s); }
	private String q_keepUnit;
	private String q_keeper;
	private String q_useUnit;
	private String q_userNo;
	public String getQ_keepUnit(){ return checkGet(q_keepUnit); }
	public void setQ_keepUnit(String s){ q_keepUnit=checkSet(s); }
	public String getQ_keeper(){ return checkGet(q_keeper); }
	public void setQ_keeper(String s){ q_keeper=checkSet(s); }
	public String getQ_useUnit(){ return checkGet(q_useUnit); }
	public void setQ_useUnit(String s){ q_useUnit=checkSet(s); }
	public String getQ_userNo(){ return checkGet(q_userNo); }
	public void setQ_userNo(String s){ q_userNo=checkSet(s); }

	private String q_keepBureau;
	private String q_useBureau;
	public String getQ_keepBureau(){ return checkGet(q_keepBureau); }	
	public void setQ_keepBureau(String s){ q_keepBureau=checkSet(s); }
	public String getQ_useBureau(){ return checkGet(q_useBureau); }	
	public void setQ_useBureau(String s){ q_useBureau=checkSet(s); }

	public String q_allCheck;
	public String q_libraryCheck;
	public String q_engineerCheck;
	public String getQ_allCheck(){ return checkGet(q_allCheck); }
	public void setQ_allCheck(String s){ q_allCheck=checkSet(s); }
	public String getQ_libraryCheck(){ return checkGet(q_libraryCheck); }
	public void setQ_libraryCheck(String s){ q_libraryCheck=checkSet(s); }
	public String getQ_engineerCheck(){ return checkGet(q_engineerCheck); }
	public void setQ_engineerCheck(String s){ q_engineerCheck=checkSet(s); }
	public String q_differenceKind;
	public String q_propertyType;
	public String q_userNote;
	
	public String q_place;
	public String getQ_differenceKind(){ return checkGet(q_differenceKind); }
	public void setQ_differenceKind(String s){ q_differenceKind=checkSet(s); }
	public String getQ_propertyType(){ return checkGet(q_propertyType); }
	public void setQ_propertyType(String s){ q_propertyType=checkSet(s); }
	public String getQ_userNote(){ return checkGet(q_userNote); }
	public void setQ_userNote(String s){ q_userNote=checkSet(s); }
	
	public String getQ_place(){ return checkGet(q_place); }
	public void setQ_place(String s){ q_place=checkSet(s); }

	public String[] q_place1;
	public String[] getQ_place1(){ return q_place1; }
	public void setQ_place1(String[] s){ q_place1=s; }
	
	private String[] q_sDestField;
	public String[] getQ_sDestField(){ return q_sDestField; }
	public void setQ_sDestField(String[] s){ q_sDestField=s; }
	
	
	private String state;
	public String getState(){ return checkGet(state); }
	public void setState(String s){ state=checkSet(s); }
	String strEngFields="";
	public void del_addPdDate(User user) {
		try {
			this.export();
		} catch (Exception e) {
//			System.out.println(e);
		}
		String lockid = this.getLockId(user.getOrganID());
		if (BeanLocker.isLocked(lockid)) {
			this.setErrorMsg(BeanLocker.getLockedMsg(lockid));
		} else {
			BeanLocker.forceLock(user, lockid, "盤點資料產製未完成，不可執行產製/列印盤點資料");
			try {
				this.dlePdDate();
				this.addPdDate();
			} catch (Exception e) {
				// 雖然內部沒有明確指出會throws exception , 但唯恐runtimeException 導致鎖死 
			} finally {
				BeanLocker.forceUnlock(lockid);
			}
		}
	}
	
	public void re_addPdDate(User user) {
		try {
			this.export();
		} catch (Exception e) {
//			System.out.println(e);
		}
		String lockid = this.getLockId(user.getOrganID());
		if (BeanLocker.isLocked(lockid)) {
			this.setErrorMsg(BeanLocker.getLockedMsg(lockid));
		} else {
			BeanLocker.forceLock(user, lockid, "盤點資料產製未完成，不可執行產製/列印盤點資料");
			try {
				this.re_del_PdDate();
				this.addPdDate();
			} catch (Exception e) {
				// 雖然內部沒有明確指出會throws exception , 但唯恐runtimeException 導致鎖死 
			} finally {
				BeanLocker.forceUnlock(lockid);
			}
		}
	}
	
	private String getInsertPreparedSQL() {
		StringBuilder sql = new StringBuilder();
		sql.append(" insert into UNTPD_CHECKMOVABLE(")
		   .append(" enterorg")   //enterOrg
		   .append(" ,serialno1") //serialno1(自動產生流水號)
		   .append(" ,barcode")   //barCode
		   .append(" ,ownership") //ownership
		   .append(" ,differencekind") //differencekind
		   .append(" ,differencekindname") //differencekindname
		   .append(" ,propertytype")       //propertytype
		   .append(" ,propertytypename")   //propertytypename
		   .append(" ,closingdate")        //(系統日期)
		   .append(" ,bookamount")         //bookAmount
		   .append(" ,actualamount")       //0
		   .append(" ,bookunit")           //case b.originalamount when null then null else m.bookvalue end as bookUnit
		   .append(" ,bookvalue")          //bookvalue
		   .append(" ,checkresult")        // 盤點結果預設為3(未盤點)
		   .append(" ,oddscause")          //Default 空白
		   .append(" ,scrappednote")       //Default 空白
		   .append(" ,labelnote")          //Default 空白
		   .append(" ,movenote")           //Default 空白
		   .append(" ,propertykind")       //propertykind
		   .append(" ,fundtype")           //fundtype
		   .append(" ,propertyno")         //propertyNo
		   .append(" ,serialno")           //serialNo
		   .append(" ,buydate")            //buyDate
		   .append(" ,sourcedate")         //sourceDate
		   .append(" ,propertyname")       //enterOrg+ propertyNo 取得SYSPK_PropertyCode2.propertyName
		   .append(" ,propertyname1")      //propertyName1
		   .append(" ,oldpropertyno")      //oldPropertyNo
		   .append(" ,oldserialno")
		   .append(" ,nameplate")          //nameplate
		   .append(" ,specification")      //specification
		   .append(" ,propertyunit")       //enterOrg + propertyNo 取得SYSPK_PropertyCode2.propertyUnit
		   .append(" ,material")           //enterOrg + propertyNo 取得SYSPK_PROPERTYCODE2.materia
		   .append(" ,limityear")          //enterOrg + propertyNo 取得SYSPK_PropertyCode2.limitYear
		   .append(" ,useyear")            //Default 0
		   .append(" ,keepunit")           //keepUnit
		   .append(" ,keepunitname")       //enterOrg + keepUnit取得UNTMP_KeepUnit.unitName
		   .append(" ,keeper")             //keeper
		   .append(" ,keepername")         //enterOrg + keeper取得UNTMP_Keeper.keeperName
		   .append(" ,useunit")            //useUnit
		   .append(" ,useunitname")        //enterOrg+ useUnit取得UNTMP_KeepUnit.unitName
		   .append(" ,userno")
		   .append(" ,usernoname")
		   .append(" ,usernote")
		   .append(" ,place1")
		   .append(" ,place1name")
		   .append(" ,place")
		   .append(" ,signno")
		   .append(" ,signnoname")
		   .append(" ,doorplate4")
		   .append(" ,notes")
		   .append(" ,editid")
		   .append(" ,editdate")
		   .append(" ,edittime")
		   .append(" ,originallimityear )")  
		   .append(" values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
		return sql.toString();
	}
	
	/**
	 * 產製盤點資料
	 */
	private void addPdDate(){
	
		String sql = sqlLand() + "union all" +
		             sqlBuilding() + "union all" +
		             sqlAttachment() + "union all" +
		             sqlMovable() + "union all" + 
		             this.getVP_SQL() + "union all" +
		             this.getRT_SQL();
		Map<String, String> DifferenceMap = TCGHCommon.getSysca_Code("DFK"); //財產區分別
		Map<String, String> PropertyTypeMap = TCGHCommon.getSysca_Code("PTT"); //財產大類
		Map<String, String> PropertyMap = TCGHCommon.getSyspk_PropertyCode(q_enterOrg); //財產名稱
		Map<String, String> KeepUnitMap = TCGHCommon.getUntmp_KeepUnitCode(q_enterOrg); //保管單位、使用單位
		Map<String, String> KeeperMap = TCGHCommon.getUntmp_KeeperCode(q_enterOrg); //保管人、使用人
		Map<String, String> PlaceMap = TCGHCommon.getSysca_PlaceCode(q_enterOrg); //存置地點
		//Map SignMap = TCGHCommon.getSysca_SignCode(); //縣市鄉鎮街

		System.out.println("start\n"+sql+"\nend");
		Database db = null;
		Database db2 = null;
		ResultSet rs = null;
		PreparedStatement preparedStatement = null;
		try {
			db = new Database();
			db2 = new Database();
			int serialno1 = 1;
			rs = db.querySQL("select isnull(max(serialno1),'0')+1 as maxSerialno1 from Untpd_Checkmovable where 1=1 and enterorg = " + Common.sqlChar(this.getQ_enterOrg()));
			if (rs.next()) {
				serialno1 = rs.getInt(1);
			}
			String yyymmdd = Datetime.getYYYYMMDD();
			String hhmmss = Datetime.getHHMMSS();
			
			rs = db.querySQL_NoChange(sql);
			while (rs.next()) {
				if (preparedStatement == null) {
					preparedStatement = db2.getConnection().prepareStatement(this.getInsertPreparedSQL());
				} else {
					preparedStatement.clearParameters();
				}
				String buyDate = Common.get(rs.getString("buydate"));
				int TempMonth = Datetime.BetweenTwoMonthCE(buyDate, yyymmdd);
				String useyear = String.valueOf(TempMonth / 12) + "年" + String.valueOf(TempMonth % 12) + "月";
				preparedStatement.setString(1, rs.getString("enterorg"));
				preparedStatement.setInt(2, serialno1++);
				preparedStatement.setString(3, rs.getString("barcode"));
				preparedStatement.setString(4, rs.getString("ownership"));
				preparedStatement.setString(5, rs.getString("differencekind"));
				preparedStatement.setString(6, DifferenceMap.get(rs.getString("differencekind")));
				preparedStatement.setString(7, rs.getString("propertytype"));
				preparedStatement.setString(8, PropertyTypeMap.get(rs.getString("propertytype")));
				preparedStatement.setString(9, yyymmdd);
				preparedStatement.setString(10, rs.getString("bookamount"));
				preparedStatement.setString(11, rs.getString("actualamount"));
				preparedStatement.setString(12, rs.getString("bookunit"));
				preparedStatement.setString(13, rs.getString("bookvalue"));
				preparedStatement.setString(14, "3");  // 盤點結果預設為3(未盤點)
				preparedStatement.setString(15, rs.getString("oddsCause"));
				preparedStatement.setString(16, rs.getString("scrappednote"));
				preparedStatement.setString(17, rs.getString("labelnote"));
				preparedStatement.setString(18, rs.getString("movenote"));
				preparedStatement.setString(19, rs.getString("propertykind"));
				preparedStatement.setString(20, rs.getString("fundtype"));
				preparedStatement.setString(21, rs.getString("propertyno"));
				preparedStatement.setString(22, rs.getString("serialno"));
				preparedStatement.setString(23, buyDate);
				preparedStatement.setString(24, rs.getString("sourceDate"));
				preparedStatement.setString(25, PropertyMap.get(rs.getString("propertyno")));
				preparedStatement.setString(26, rs.getString("propertyName1"));
				preparedStatement.setString(27, rs.getString("oldPropertyNo"));
				preparedStatement.setString(28, rs.getString("oldSerialNo"));
				preparedStatement.setString(29, rs.getString("nameplate"));
				preparedStatement.setString(30, rs.getString("specification"));
				preparedStatement.setString(31, rs.getString("propertyunit"));
				preparedStatement.setString(32, rs.getString("material"));
				preparedStatement.setString(33, rs.getString("limityear"));
				preparedStatement.setString(34, useyear);
				preparedStatement.setString(35, rs.getString("keepunit"));
				preparedStatement.setString(36, KeepUnitMap.get(rs.getString("keepunit")));
				preparedStatement.setString(37, rs.getString("keeper"));
				preparedStatement.setString(38, KeeperMap.get(rs.getString("keeper")));
				preparedStatement.setString(39, rs.getString("useunit"));
				preparedStatement.setString(40, KeepUnitMap.get(rs.getString("useunit")));
				preparedStatement.setString(41, rs.getString("userno"));
				preparedStatement.setString(42, KeeperMap.get(rs.getString("userno")));
				preparedStatement.setString(43, rs.getString("usernote"));
				preparedStatement.setString(44, rs.getString("place1"));
				preparedStatement.setString(45, PlaceMap.get(rs.getString("place1")));
				preparedStatement.setString(46, rs.getString("place"));
				preparedStatement.setString(47, rs.getString("signno"));
				preparedStatement.setString(48, rs.getString("signnoname"));
				preparedStatement.setString(49, rs.getString("doorplate4"));
				preparedStatement.setString(50, rs.getString("notes"));
				preparedStatement.setString(51, getUserID());
				preparedStatement.setString(52, yyymmdd);
				preparedStatement.setString(53, hhmmss);
				preparedStatement.setString(54, rs.getString("originallimityear"));
				preparedStatement.execute();
				//System.out.println(serialno1 + "筆完成");
			}
			
			setErrorMsg("盤點資料產生完成!!");
		} catch (Exception e) {
			e.printStackTrace();
			setErrorMsg("產製財產盤點資料時發生錯誤");
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (Exception e) {
					//ignore
				}
			}
			
			if (preparedStatement != null) {
				try {
					preparedStatement.close();
				} catch (Exception e) {
					//ignore
				}
			}
			
			if (db != null) {
				db.closeAll();
			}
			
			if (db2 != null) {
				db2.closeAll();
			}
		}
		setState("addPDdateSuccess");
	}
	
	/**
	 * 土地
	 */
	private String sqlLand(){
		String sql = " select  b.enterorg , " + "\n"
		+ " (b.differencekind + '-' + b.propertyno + '-' + b.serialno) as barcode , " + "\n"
		+ " b.ownership , " + "\n"
		+ " b.differencekind, " + "\n"
		+ " case substring(b.propertyno,1,1) when '3' then '4' when '4' then '5' when '5' then '6' end propertytype, " + "\n"
		+ " '1' as bookamount , " + "\n"
		+ " '1' as actualAmount, " + "\n"
		+ " b.bookunit, " + "\n"
		+ " b.bookvalue , " + "\n"
		+ " '' as checkResult , " + "\n"
		+ " '' as oddsCause , " + "\n"
		+ " '' as scrappednote , " + "\n"
		+ " '' as labelnote , " + "\n"
		+ " '' as movenote , " + "\n"
		+ " b.propertykind, " + "\n"
		+ " b.fundtype , " + "\n"
		+ " b.propertyno , " + "\n"
		+ " b.serialno , " + "\n"
		+ " b.buydate , " + "\n"
		+ " b.sourcedate, " + "\n"
		+ " b.propertyname1 , " + "\n"
		+ " b.oldpropertyno , " + "\n"
		+ " b.oldserialno , " + "\n"
		+ " '' as nameplate , " + "\n"
		+ " '' as specification , " + "\n"
		+ " isnull(p.propertyunit,'') as propertyunit , " + "\n"
		+ " isnull(p.material,'') as material , " + "\n"
		+ " isnull(p.limityear,'0') as originallimityear , " + "\n"
		// 這邊直接抓年 可是有otherlimityear的財產 都會是月  所以統一為月
		+ " cast(case when isnull(p.limityear,0) <> 0 then cast(p.limityear*12 as int) else 0  end as varchar) as limityear, "
		+ " 0 as 'useyear', " + "\n"
		+ " b.keepunit , " + "\n"
		+ " b.keeper , " + "\n"
		+ " b.useunit , " + "\n"
		+ " b.userno , " + "\n"
		+ " b.usernote, " + "\n"
		+ " b.place1 , " + "\n"
		+ " b.place, " + "\n"
		+ " b.signno, " + "\n"
		+ " isnull(s1.signname,'') + isnull(s2.signname,'') + isnull(s3.signname,'') as signnoname, " + "\n"
		+ " '' as doorplate4, " + "\n"
		+ " b.notes " + "\n"
		+ " from UNTLA_LAND b "
		+ " left outer join SYSPK_PROPERTYCODE p on " + "\n"
		+ " b.enterorg = p.enterorg " + "\n"
		+ " and b.propertyno = p.propertyno " + "\n"
		+ " left outer join SYSCA_SIGN s1 on s1.signno = SUBSTRING(b.signno,1,1) + '000000' " + "\n"
		+ " left outer join SYSCA_SIGN s2 on s2.signno = SUBSTRING(b.signno,1,3) + '0000'  " + "\n"
		+ " left outer join SYSCA_SIGN s3 on s3.signno = SUBSTRING(b.signno,1,7) " + "\n"
		+ " where 1=1 " + "\n"
		+ " and b.datastate='1' " + "\n"
		+ " and b.verify='Y' " + "\n" 	   
		+ getSQLwhere2()
		+ "";
		return sql;
	}

	/**
	 * 建物
	 */
	private String sqlBuilding(){
		String sql = " select  b.enterorg , " + "\n"
		+ " (b.differencekind + '-' + b.propertyno + '-' + b.serialno) , " + "\n"
		+ " b.ownership , " + "\n"
		+ " b.differencekind, " + "\n"
		+ " case substring(b.propertyno,1,1) when '3' then '4' when '4' then '5' when '5' then '6' end propertytype, " + "\n"
		+ " '1' as bookamount , " + "\n"
		+ " '1' as actualAmount, " + "\n"
		+ " b.bookvalue as bookunit, " + "\n"
		+ " b.bookvalue , " + "\n"
		+ " '' as checkResult , " + "\n"
		+ " '' as oddsCause , " + "\n"
		+ " '' as scrappednote , " + "\n"
		+ " '' as labelnote , " + "\n"
		+ " '' as movenote , " + "\n"
		+ " b.propertykind, " + "\n"
		+ " b.fundtype , " + "\n"
		+ " b.propertyno , " + "\n"
		+ " b.serialno , " + "\n"
		+ " b.buydate , " + "\n"
		+ " b.sourcedate, " + "\n"
		+ " b.propertyname1 , " + "\n"
		+ " b.oldpropertyno , " + "\n"
		+ " b.oldserialno , " + "\n"
		+ " '' as nameplate , " + "\n"
		+ " '' as specification , " + "\n"
		+ " isnull(p.propertyunit,'') as propertyunit , " + "\n"
		+ " isnull(p.material,'') as material , " + "\n"
		+ " isnull(b.originallimityear,'0') as originallimityear , " + "\n"
		+ " isnull(b.otherlimityear,'0') as limityear , " + "\n"
		+ " 0 as 'useyear', " + "\n"
		+ " b.keepunit , " + "\n"
		+ " b.keeper , " + "\n"
		+ " b.useunit , " + "\n"
		+ " b.userno , " + "\n"
		+ " b.usernote, " + "\n"
		+ " b.place1 , " + "\n"
		+ " b.place, " + "\n"
		+ " b.signno, " + "\n"
		+ " isnull(s1.signname,'') + isnull(s2.signname,'') + isnull(s3.signname,'') as signnoname, " + "\n"
		+ " '' as doorplate4, " + "\n"
		+ " b.notes " + "\n"
		+ " from UNTBU_BUILDING b "
		+ " left outer join SYSPK_PROPERTYCODE p on " + "\n"
		+ " b.enterorg = p.enterorg " + "\n"
		+ " and b.propertyno = p.propertyno " + "\n"
		+ " left outer join SYSCA_SIGN s1 on s1.signno = SUBSTRING(b.signno,1,1) + '000000' " + "\n"
		+ " left outer join SYSCA_SIGN s2 on s2.signno = SUBSTRING(b.signno,1,3) + '0000'  " + "\n"
		+ " left outer join SYSCA_SIGN s3 on s3.signno = SUBSTRING(b.signno,1,7) " + "\n"
		+ " where 1=1 " + "\n"
		+ " and b.datastate='1' " + "\n"
		+ " and b.verify='Y' " + "\n" 	   
		+ getSQLwhere2()
		+ "";
		return sql;
	}

	/**
	 * 土地改良
	 */
	private String sqlAttachment(){
		String sql = " select  b.enterorg , " + "\n"
		+ " (b.differencekind + '-' + b.propertyno + '-' + b.serialno) as barcode , " + "\n"
		+ " b.ownership , " + "\n"
		+ " b.differencekind, " + "\n"
		+ " case substring(b.propertyno,1,1) when '3' then '4' when '4' then '5' when '5' then '6' end propertytype, " + "\n"
		+ " '1' as bookamount , " + "\n"
		+ " '1' as actualAmount, " + "\n"
		+ " b.bookvalue as bookunit, " + "\n"
		+ " b.bookvalue , " + "\n"
		+ " '' as checkResult , " + "\n"
		+ " '' as oddsCause , " + "\n"
		+ " '' as scrappednote , " + "\n"
		+ " '' as labelnote , " + "\n"
		+ " '' as movenote , " + "\n"
		+ " b.propertykind, " + "\n"
		+ " b.fundtype , " + "\n"
		+ " b.propertyno , " + "\n"
		+ " b.serialno , " + "\n"
		+ " b.buydate , " + "\n"
		+ " b.sourcedate, " + "\n"
		+ " b.propertyname1 , " + "\n"
		+ " b.oldpropertyno , " + "\n"
		+ " b.oldserialno , " + "\n"
		+ " '' as nameplate , " + "\n"
		+ " '' as specification , " + "\n"
		+ " isnull(p.propertyunit,'') as propertyunit , " + "\n"
		+ " isnull(p.material,'') as material , " + "\n"
		+ " isnull(b.originallimityear,'0') as originallimityear , " + "\n"
		+ " isnull(b.otherlimityear,'0') as limityear , " + "\n"
		+ " 0 as 'useyear', " + "\n"
		+ " b.keepunit , " + "\n"
		+ " b.keeper , " + "\n"
		+ " b.useunit , " + "\n"
		+ " b.userno , " + "\n"
		+ " b.usernote, " + "\n"
		+ " b.place1 , " + "\n"
		+ " b.place, " + "\n"
		+ " '' as signno, " + "\n"
		+ " '' as signnoname, " + "\n"
		+ " '' as doorplate4, " + "\n"
		+ " b.notes " + "\n"
		+ " from UNTRF_ATTACHMENT b "
		+ " left outer join SYSPK_PROPERTYCODE p on " + "\n"
		+ " b.enterorg = p.enterorg " + "\n"
		+ " and b.propertyno = p.propertyno " + "\n"
		+ " where 1=1 " + "\n"
		+ " and b.datastate='1' " + "\n"
		+ " and b.verify='Y' " + "\n" 	   
		+ getSQLwhere2()
		+ "";
		return sql;
	}
	
	/**
	 * 動產
	 */
	private String sqlMovable(){
		String sql = " select  m.enterorg , " + "\n"
		+ " m.barcode , " + "\n"
		+ " m.ownership , " + "\n"
		+ " m.differencekind, " + "\n"
		+ " case substring(m.propertyno,1,1) when '3' then '4' when '4' then '5' when '5' then '6' end propertytype, " + "\n"
		+ " ceiling(m.bookamount) as bookamount, " + "\n"	// 合購數量(bookamount<1)也顯示1
		+ " '0' as actualAmount, " + "\n"
		+ " case b.originalamount when null then null else m.bookvalue end as bookunit, " + "\n"
		+ " m.bookvalue , " + "\n"
		+ " '' as checkResult , " + "\n"
		+ " '' as oddsCause , " + "\n"
		+ " '' as scrappednote , " + "\n"
		+ " '' as labelnote , " + "\n"
		+ " '' as movenote , " + "\n"
		+ " b.propertykind, " + "\n"
		+ " b.fundtype , " + "\n"
		+ " b.propertyno , " + "\n"
		+ " m.serialno , " + "\n"
		+ " b.buydate , " + "\n"
		+ " b.sourcedate, " + "\n"
		+ " m.propertyname1 , " + "\n"
		+ " m.oldpropertyno , " + "\n"
		+ " m.oldserialno , " + "\n"
		+ " b.nameplate , " + "\n"
		+ " b.specification , " + "\n"
		+ " isnull(p.propertyunit,'') as propertyunit , " + "\n"
		+ " isnull(p.material,'') as material , " + "\n"
		+ " isnull(m.originallimityear,'0') as originallimityear , " + "\n"
		+ " isnull(m.otherlimityear,'0') as limityear , " + "\n"
		+ " 0 as 'useyear', " + "\n"
		+ " m.keepunit , " + "\n"
		+ " m.keeper , " + "\n"
		+ " m.useunit , " + "\n"
		+ " m.userno , " + "\n"
		+ " m.usernote , " + "\n"
		+ " m.place1 , " + "\n"
		+ " m.place, " + "\n"
		+ " '' as signno, " + "\n"
		+ " '' as signnoname, " + "\n"
		+ " '' as doorplate4, " + "\n"
		+ " m.notes " + "\n"
		
		+ " from UNTMP_MOVABLEDETAIL m "
		+ " inner join  UNTMP_MOVABLE b on b.enterorg = m.enterorg  and b.ownership =m.ownership  and b.propertyno = m.propertyno  and b.lotno = m.lotno  and b.differencekind = m.differencekind " 
		+ " left join UNTMP_ADDPROOF a on  a.enterorg = b.enterorg  and a.caseno = b.caseno " 
		+ " left outer join SYSPK_PROPERTYCODE2 p on  b.enterorg = p.enterorg  and b.propertyno = p.propertyno "

		+ " where 1=1 " + "\n"
		+ " and b.datastate='1' " + "\n"
		+ " and m.datastate='1' " + "\n"
		+ " and b.verify='Y' " + "\n" 	   
		+ getSQLwhere1()
		+ "";
		return sql;
	}

	private String getVP_SQL() {
		StringBuilder vpSQL = new StringBuilder();
		vpSQL.append(" select b.enterorg, (b.differencekind + '-' + b.propertyno + '-' + b.serialno) as barcode, b.ownership, b.differencekind,")
		     .append(" case substring(b.propertyno,1,1) when '3' then '4' when '4' then '5' when '5' then '6' when '9' then '7' when '8' then '8' end as propertytype,")
		     .append(" b.bookamount, '0' as actualAmount,")
		     .append(" case b.originalamount when null then null else b.bookvalue end as bookunit,")
		     .append(" b.bookvalue, '' as checkResult, '' as oddsCause, '' as scrappednote, '' as labelnote, '' as movenote,")
		     .append(" b.propertykind, b.fundtype, b.propertyno, b.serialno, b.buydate, b.sourcedate, b.propertyname1,")
		     .append(" b.oldpropertyno, b.oldserialno, '' as nameplate, '' as specification,")
		     .append(" case isnull(b.otherpropertyunit, '') when '' then isnull(p.propertyunit, '') else b.otherpropertyunit end as propertyunit,")
		     .append(" case isnull(b.othermaterial, '') when '' then isnull(p.material, '') else b.othermaterial end as material,")
		     .append(" isnull(p.limityear, '0') as originallimityear, ")
		     // 這邊直接抓年 可是有otherlimityear的財產 都會是月  所以統一為月
		     .append(" cast(case when isnull(p.limityear,0) <> 0 then cast(p.limityear*12 as int) else 0  end as varchar) as limityear, ")
		     .append(" 0 as useyear, b.keepunit, b.keeper, b.useunit, b.userno, b.usernote, b.place1, b.place, ")
		     .append(" '' as signno, '' as signnoname, '' as doorplate4, b.notes");
		vpSQL.append(" from UNTVP_ADDPROOF b left outer join SYSPK_PROPERTYCODE p ")
		     .append(" on ((b.enterorg = p.enterorg or p.enterorg = '000000000A') and b.propertyno = p.propertyno)")
			 .append(" where 1=1 ");
		vpSQL.append(" and b.datastate = '1' and b.verify = 'Y' ");
		vpSQL.append(this.getSQLwhere2());
		return vpSQL.toString();
	}
	
	private String getRT_SQL() {
		StringBuilder vpSQL = new StringBuilder();
		vpSQL.append(" select b.enterorg, (b.differencekind + '-' + b.propertyno + '-' + b.serialno) as barcode, b.ownership, b.differencekind,")
		     .append(" case substring(b.propertyno,1,1) when '3' then '4' when '4' then '5' when '5' then '6' when '9' then '7' when '8' then '8' end as propertytype,")
		     .append(" '1' as bookamount, '0' as actualAmount, b.bookvalue as bookunit,")
		     .append(" b.bookvalue, '' as checkResult, '' as oddsCause, '' as scrappednote, '' as labelnote, '' as movenote,")
		     .append(" b.propertykind, b.fundtype, b.propertyno, b.serialno, b.buydate, b.sourcedate, b.propertyname1,")
		     .append(" b.oldpropertyno, b.oldserialno, proofdoc1 as nameplate, '' as specification,")
		     .append(" case isnull(b.otherpropertyunit, '') when '' then isnull(p.propertyunit, '') else b.otherpropertyunit end as propertyunit,")
		     .append(" case isnull(b.othermaterial, '') when '' then isnull(p.material, '') else b.othermaterial end as material,")
		     .append(" isnull(p.limityear,'0') as originallimityear,")
		     .append(" isnull(b.otherlimityear,'0') as limityear,")
		     .append(" 0 as useyear, b.keepunit, b.keeper, b.useunit, b.userno, b.usernote, b.place1, b.place, ")
		     .append(" '' as signno, '' as signnoname, '' as doorplate4, b.notes");
		vpSQL.append(" from UNTRT_ADDPROOF b left outer join SYSPK_PROPERTYCODE p ")
		     .append(" on ((b.enterorg = p.enterorg or p.enterorg = '000000000A') and b.propertyno = p.propertyno)")
			 .append(" where 1=1 ");
		vpSQL.append(" and b.datastate = '1' and b.verify = 'Y' ");
		vpSQL.append(this.getSQLwhere2());
		return vpSQL.toString();
	}
	
	private String getSQLwhere2(){
		String sqlwhere="";
		if ("Y".equals(getQ_allCheck())){
			if (!"".equals(getQ_enterOrg())) {
				sqlwhere += " and b.enterorg = " + Common.sqlChar(getQ_enterOrg()) + "\n";
			}
			if ("N".equals(getQ_libraryCheck())){
				sqlwhere += " and b.propertyno not like '503%'" + "\n";
			}
			if ("on".equals(getQ_engineerCheck())){
				sqlwhere += " and isnull(b.engineeringno,'') = ''" + "\n";
			}
		}else{
			//機關
			if (!"".equals(getQ_enterOrg())) {
				sqlwhere += " and b.enterorg = " + Common.sqlChar(getQ_enterOrg()) + "\n";
			}
			//權屬
			if (!"".equals(getQ_ownership())) {
				sqlwhere += " and b.ownership = " + Common.sqlChar(getQ_ownership()) + "\n";
			}
			//財產區分別
			if (!"".equals(getQ_differenceKind())) {
				sqlwhere += " and b.differencekind = " + Common.sqlChar(getQ_differenceKind()) + "\n";
			}
			//現存狀態
			if (!"".equals(getQ_dataState())) {
				sqlwhere += " and b.datastate = " + Common.sqlChar(getQ_dataState()) + "\n";
			}
			//財產大類
			if ("1".equals(getQ_propertyType())) {
				sqlwhere += " and (substring(b.propertyno,1,1) = '1' and not b.propertyno like '111%')" + "\n";
			} else if ("2".equals(getQ_propertyType())) {
				sqlwhere += " and substring(b.propertyno,1,3) = '111' " + "\n";
			} else if ("3".equals(getQ_propertyType())) {
				sqlwhere += " and substring(b.propertyno,1,1) = '2' " + "\n";
			} else if ("4".equals(getQ_propertyType())) {
				sqlwhere += " and substring(b.propertyno,1,1) = '3' " + "\n";
			} else if ("5".equals(getQ_propertyType())) {
				sqlwhere += " and substring(b.propertyno,1,1) = '4' " + "\n";
			} else if ("6".equals(getQ_propertyType())) {
				sqlwhere += " and substring(b.propertyno,1,1) = '5' " + "\n";
			} else if ("7".equals(this.getQ_propertyType())) {
				sqlwhere += " and substring(b.propertyno,1,1) = '9' ";
			} else if ("8".equals(this.getQ_propertyType())) {
				sqlwhere += " and substring(b.propertyno,1,1) = '8' ";
			}
			//財產編號起訖
			if (!"".equals(getQ_propertyNoS())) {
				sqlwhere += " and b.propertyno >= " + Common.sqlChar(getQ_propertyNoS()) + "\n";
			}
			//財產編號起訖
			if (!"".equals(getQ_propertyNoE())) {
				sqlwhere += " and b.propertyno <= " + Common.sqlChar(getQ_propertyNoE()) + "\n";
			}
			//購置日期起訖
			if (!"".equals(getQ_buyDateS())) {
				sqlwhere += " and b.buydate >= " + Common.sqlChar(Datetime.changeTaiwanYYMMDD(getQ_buyDateS(),"2")) + "\n";
			}
			//購置日期起訖
			if (!"".equals(getQ_buyDateE())) {
				sqlwhere += " and b.buydate <= " + Common.sqlChar(Datetime.changeTaiwanYYMMDD(getQ_buyDateE(),"2")) + "\n";
			}
			//入帳日期起訖
			if (!"".equals(getQ_enterDateS())) {
				sqlwhere += " and b.enterdate >= " + Common.sqlChar(Datetime.changeTaiwanYYMMDD(getQ_enterDateS(),"2")) + "\n";
			}
			//入帳日期起訖
			if (!"".equals(getQ_enterDateE())) {
				sqlwhere += " and b.enterdate <= " + Common.sqlChar(Datetime.changeTaiwanYYMMDD(getQ_enterDateE(),"2")) + "\n";
			}
			//保管單位
			if (!"".equals(getQ_keepUnit())) {
				sqlwhere += " and b.keepunit = " + Common.sqlChar(getQ_keepUnit()) + "\n";
			}
			//保管人
			if (!"".equals(getQ_keeper())) {
				sqlwhere += " and b.keeper = " + Common.sqlChar(getQ_keeper()) + "\n";
			}
			//使用單位
			if (!"".equals(getQ_useUnit())) {
				sqlwhere += " and b.useunit = " + Common.sqlChar(getQ_useUnit()) + "\n";
			}
			//使用人
			if (!"".equals(getQ_userNo())) {
				sqlwhere += " and b.userno = " + Common.sqlChar(getQ_userNo()) + "\n";
			}
			//使用註記
			if (!"".equals(getQ_userNote())) {
				sqlwhere += " and b.usernote like " + Common.sqlChar("%"+getQ_userNote()+"%") + "\n";
			}
			//存置地點
			if (!(getQ_sDestField()==null)){
				sqlwhere += "and b.place1 in ("+ strEngFields +")\n";
			}
//			if (!"".equals(getQ_place1())) {
//				sqlwhere += " and b.place1 = " + Common.sqlChar(getQ_place1()) + "\n";
//			}
			//存置地點說明
			if (!"".equals(getQ_place())) {
				sqlwhere += " and b.place like " + Common.sqlChar("%" + getQ_place() + "%") + "\n";
			}
			//包含圖書
			if ("N".equals(getQ_libraryCheck())){
				sqlwhere += " and b.propertyno not like '503%'" + "\n";
			}
			//不含工程類財產 
			if ("on".equals(getQ_engineerCheck())){
				sqlwhere += " and isnull(b.engineeringno,'') = '' " + "\n";
			}
		}
		return sqlwhere;
	}

	private String getSQLwhere1(){
		String sqlwhere="";
		if ("Y".equals(getQ_allCheck())){
			if (!"".equals(getQ_enterOrg())) {
				sqlwhere += " and m.enterorg = " + Common.sqlChar(getQ_enterOrg()) + "\n";
			}
			if ("N".equals(getQ_libraryCheck())){
				sqlwhere += " and m.propertyno not like '503%'" + "\n";
			}
			if ("on".equals(getQ_engineerCheck())){
				sqlwhere += " and isnull(b.engineeringno,'') = ''" + "\n";
			}
		}else{
			//機關
			if (!"".equals(getQ_enterOrg())) {
				sqlwhere += " and m.enterorg = " + Common.sqlChar(getQ_enterOrg()) + "\n";
			}
			//權屬
			if (!"".equals(getQ_ownership())) {
				sqlwhere += " and m.ownership = " + Common.sqlChar(getQ_ownership()) + "\n";
			}
			//財產區分別
			if (!"".equals(getQ_differenceKind())) {
				sqlwhere += " and m.differencekind = " + Common.sqlChar(getQ_differenceKind()) + "\n";
			}
			//現存狀態
			if (!"".equals(getQ_dataState())) {
				sqlwhere += " and m.datastate = " + Common.sqlChar(getQ_dataState()) + "\n";
			}
			//財產大類
			if ("1".equals(getQ_propertyType())) {
				sqlwhere += " and (substring(m.propertyno,1,1) = '1' and not m.propertyno like '111%')" + "\n";
			} else if ("2".equals(getQ_propertyType())) {
				sqlwhere += " and substring(m.propertyno,1,3) = '111' " + "\n";
			} else if ("3".equals(getQ_propertyType())) {
				sqlwhere += " and substring(m.propertyno,1,1) = '2' " + "\n";
			} else if ("4".equals(getQ_propertyType())) {
				sqlwhere += " and substring(m.propertyno,1,1) = '3' " + "\n";
			} else if ("5".equals(getQ_propertyType())) {
				sqlwhere += " and substring(m.propertyno,1,1) = '4' " + "\n";
			} else if ("6".equals(getQ_propertyType())) {
				sqlwhere += " and substring(m.propertyno,1,1) = '5' " + "\n";
			} else if ("7".equals(this.getQ_propertyType())) {
				sqlwhere += " and substring(m.propertyno,1,1) = '9' " + "\n";
			} else if ("8".equals(this.getQ_propertyType())) {
				sqlwhere += " and substring(b.propertyno,1,1) = '8' ";
			}
			//財產編號起訖
			if (!"".equals(getQ_propertyNoS())) {
				sqlwhere += " and m.propertyno >= " + Common.sqlChar(getQ_propertyNoS()) + "\n";
			}
			//財產編號起訖
			if (!"".equals(getQ_propertyNoE())) {
				sqlwhere += " and m.propertyno <= " + Common.sqlChar(getQ_propertyNoE()) + "\n";
			}
			//購置日期起訖
			if (!"".equals(getQ_buyDateS())) {
				sqlwhere += " and b.buydate >= " + Common.sqlChar(Datetime.changeTaiwanYYMMDD(getQ_buyDateS(),"2")) + "\n";
			}
			//購置日期起訖
			if (!"".equals(getQ_buyDateE())) {
				sqlwhere += " and b.buydate <= " + Common.sqlChar(Datetime.changeTaiwanYYMMDD(getQ_buyDateE(),"2")) + "\n";
			}
			//入帳日期起訖
			if (!"".equals(getQ_enterDateS())) {
				sqlwhere += " and a.enterdate >= " + Common.sqlChar(Datetime.changeTaiwanYYMMDD(getQ_enterDateS(),"2")) + "\n";
			}
			//入帳日期起訖
			if (!"".equals(getQ_enterDateE())) {
				sqlwhere += " and a.enterdate <= " + Common.sqlChar(Datetime.changeTaiwanYYMMDD(getQ_enterDateE(),"2")) + "\n";
			}
			//保管單位
			if (!"".equals(getQ_keepUnit())) {
				sqlwhere += " and m.keepunit = " + Common.sqlChar(getQ_keepUnit()) + "\n";
			}
			//保管人
			if (!"".equals(getQ_keeper())) {
				sqlwhere += " and m.keeper = " + Common.sqlChar(getQ_keeper()) + "\n";
			}
			//使用單位
			if (!"".equals(getQ_useUnit())) {
				sqlwhere += " and m.useunit = " + Common.sqlChar(getQ_useUnit()) + "\n";
			}
			//使用人
			if (!"".equals(getQ_userNo())) {
				sqlwhere += " and m.userno = " + Common.sqlChar(getQ_userNo()) + "\n";
			}
			//使用註記
			if (!"".equals(getQ_userNote())) {
				sqlwhere += " and m.usernote like " + Common.sqlChar("%"+getQ_userNote()+"%") + "\n";
			}
			//存置地點
			if (!(getQ_sDestField()==null)){
				sqlwhere += "and m.place1 in ("+ strEngFields +")\n";
			}
//			if (!"".equals(getQ_place1())) {
//				sqlwhere += " and m.place1 = " + Common.sqlChar(getQ_place1()) + "\n";
//			}
			//存置地點說明
			if (!"".equals(getQ_place())) {
				sqlwhere += " and m.place like " + Common.sqlChar("%" + getQ_place() + "%") + "\n";
			}
			//包含圖書
			if ("N".equals(getQ_libraryCheck())){
				sqlwhere += " and m.propertyno not like '503%'" + "\n";
			}
			//不含工程類財產 
			if ("on".equals(getQ_engineerCheck())){
				sqlwhere += " and isnull(b.engineeringno,'') = ''" + "\n";
			}
		}
		return sqlwhere;
	}
	
	public String orgName(){
		String sql="select organSName from SYSCA_organ where " +
		" organID = " + Common.sqlChar(q_enterOrg) + 
		"";	
		String selorgName = "";
		Database db = new Database();
		ResultSet rs;
		//System.out.println(sql);
		try{
			
			rs = db.querySQL(sql);
			if (rs.next()){
				selorgName=rs.getString("organsname");
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.closeAll();
		}
		return selorgName;
	}
	
	/**
	 * 取後單位最小日期
	 * @return
	 */
	public boolean getMinCloseDate(){
		boolean reMincloseDate = false;
		String sql = " select isnull(min(closingdate),'0') as minClosingdate from UNTPD_CHECKMOVABLE "
				   + "where 1=1 and enterorg = " + Common.sqlChar(getQ_enterOrg());
		Database db = new Database();
		ResultSet rs;
		try{
			rs = db.querySQL(sql);
			if (rs.next()){
				if(rs.getString("minClosingdate").equals(Datetime.getYYYYMMDD())){
					reMincloseDate = true;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.closeAll();
		}
		return reMincloseDate;
	}

	/**
	 * 清除盤點資料
	 */
	private void dlePdDate(){
		String[] deletesql = new String[1];
		deletesql[0] = " delete untpd_checkmovable "
					 + " where enterorg = " + Common.sqlChar(getQ_enterOrg());
	
		Database db = new Database();
		try{
			db.excuteSQL(deletesql);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.closeAll();
		}
	}

	/**
	 * 累加產製相同資料刪除
	 */
	private void re_del_PdDate(){
		String[] deletesql = new String[1];
		deletesql[0] = " delete untpd_checkmovable "
					+ " where 1=1 "
					+ " and propertyno + serialno in ( "
					+ " select m.propertyno + m.serialno "
					
					+ " from UNTMP_MOVABLEDETAIL m "
					+ " inner join  UNTMP_MOVABLE b on b.enterorg = m.enterorg  and b.ownership =m.ownership  and b.propertyno = m.propertyno  and b.lotno = m.lotno  and b.differencekind = m.differencekind " 
					+ " left join UNTMP_ADDPROOF a on  a.enterorg = b.enterorg  and a.caseno = b.caseno " 
					+ " left outer join SYSPK_PROPERTYCODE2 p on  b.enterorg = p.enterorg  and b.propertyno = p.propertyno "
					
					+ " where 1=1 "
					+ " and m.datastate='1' "
					+ getSQLwhere1()
					+ " union all "
					+ " select b.propertyno + b.serialno "
					+ " from UNTLA_LAND b "
					+ " where 1=1 "
					+ getSQLwhere2()
					+ " union all "
					+ " select b.propertyno + b.serialno "
					+ " from UNTBU_BUILDING b "
					+ " where 1=1 "
					+ getSQLwhere2()
					+ " union all "
					+ " select b.propertyno + b.serialno "
					+ " from UNTRF_ATTACHMENT b "
					+ " where 1=1 "
					+ getSQLwhere2()
					+ " union all "
					+ " select b.propertyno + b.serialno  "
					+ " from UNTVP_ADDPROOF b where 1=1 "
					+ getSQLwhere2()
					+ " union all "
					+ " select b.propertyno + b.serialno "
					+ " from UNTRT_ADDPROOF b where 1=1"
					+ this.getSQLwhere2()
					+ " ) "
					+ "" ;
		Database db = new Database();
		try{
			db.excuteSQL(deletesql);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.closeAll();
		}
	}
	
	public void export() throws Exception{
		String strFields = "", strContext="";
		String strZhFields="";
		strEngFields="";
		String[] arrContext = null, arrField=null;
		int i = 0, j=0;
		try{
			//Get Selected Fields and Field Names
			for (i=0; i<q_sDestField.length; i++) {
				arrField = q_sDestField[i].split(":;:");			
				if (i==0) {
					strFields += arrField[0];
					strEngFields += Common.sqlChar(arrField[0]);
					strZhFields += arrField[1];
					strContext += arrField[1];
				} else {
					strFields += "," + arrField[0];
					strEngFields += "," + Common.sqlChar(arrField[0]);
					strZhFields += "," + arrField[1];
					strContext += "," + arrField[1];
				} 
				if (i==q_sDestField.length-1){
					strContext += ";;";
				}
			}
		}catch(Exception e)
		{
			System.out.println("getSignDescName Exception => " + e.getMessage());
		}
		
	}
	
	public String getFieldList(String mgrOrg, String userOrg) throws Exception {
		Database db = new Database();
		ResultSet rs = null;
		String sSQL = "";
		StringBuffer sb = new StringBuffer("");
		try {
			sSQL = "select placeno,placename from SYSCA_PLACE where enterorg = ";
			sSQL +="'"+getQ_enterOrg()+"'" + " order by placeno";
			/*if (!checkGet(mgrOrg).equalsIgnoreCase(checkGet(userOrg))){
				sSQL += " and isMgr='N'";
			}
			sSQL += " order by orderby " ;*/
			rs = db.querySQL(sSQL);
//			System.out.println(sSQL);
			while (rs.next()) {
				sb.append("<option value='" ).append( rs.getString("placeno") ).append( ":;:" ).append( rs.getString("placename") ).append( "'>" ).append(rs.getString("placeno")).append("\t").append(rs.getString("placename")).append( "</option>");
			}
			rs.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.closeAll();
		}
		return sb.toString();
	}
	
}
