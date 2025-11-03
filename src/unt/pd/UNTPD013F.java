/*
*<br>程式目的：非消耗品產生盤點資料
*<br>程式代號：untpd013f.jsp
*<br>程式日期：2007/11/12
*<br>程式作者：shan
*<br>--------------------------------------------------------
*<br>修改作者　　修改日期　　　修改目的
*<br>--------------------------------------------------------
*/

package unt.pd;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Map;

import util.BeanLocker;
import util.Common;
import util.Database;
import util.Datetime;
import util.SuperBean;
import util.TCGHCommon;
import util.User;

public class UNTPD013F extends SuperBean{

	public String getLockId(String organid) {
		return Common.get(organid) + "產製物品盤點資料";
	}
	String strEngFields="";
	String q_enterOrg;
	String q_enterOrgName;
	String q_verify;
	String q_ownership;
	String q_propertyNoS;
	String q_propertyNoE;
	String q_propertyNoSName;
	String q_propertyNoEName;
	String q_buyDateS;
	String q_buyDateE;
	String q_enterDateS;
	String q_enterDateE;
	String q_dataState;
	String q_keepUnit;
	String q_keeper;
	String q_useUnit;
	String q_userNo;
	public String q_allCheck;
	public String q_libraryCheck;
	String state;
	
	public String getQ_enterOrg(){ return checkGet(q_enterOrg); }
	public void setQ_enterOrg(String s){ q_enterOrg=checkSet(s); }
	public String getQ_enterOrgName(){ return checkGet(q_enterOrgName); }
	public void setQ_enterOrgName(String s){ q_enterOrgName=checkSet(s); }
	public String getQ_verify(){ return checkGet(q_verify); }
	public void setQ_verify(String s){ q_verify=checkSet(s); }
	public String getQ_ownership(){ return checkGet(q_ownership); }
	public void setQ_ownership(String s){ q_ownership=checkSet(s); }
	public String getQ_propertyNoS(){ return checkGet(q_propertyNoS); }
	public void setQ_propertyNoS(String s){ q_propertyNoS=checkSet(s); }
	public String getQ_propertyNoE(){ return checkGet(q_propertyNoE); }
	public void setQ_propertyNoE(String s){ q_propertyNoE=checkSet(s); }
	public String getQ_propertyNoSName(){ return checkGet(q_propertyNoSName); }
	public void setQ_propertyNoSName(String s){ q_propertyNoSName=checkSet(s); }
	public String getQ_propertyNoEName(){ return checkGet(q_propertyNoEName); }
	public void setQ_propertyNoEName(String s){ q_propertyNoEName=checkSet(s); }
	public String getQ_buyDateS(){ return checkGet(q_buyDateS); }
	public void setQ_buyDateS(String s){ q_buyDateS=checkSet(s); }
	public String getQ_buyDateE(){ return checkGet(q_buyDateE); }
	public void setQ_buyDateE(String s){ q_buyDateE=checkSet(s); }
	public String getQ_enterDateS(){ return checkGet(q_enterDateS); }
	public void setQ_enterDateS(String s){ q_enterDateS=checkSet(s); }
	public String getQ_enterDateE(){ return checkGet(q_enterDateE); }
	public void setQ_enterDateE(String s){ q_enterDateE=checkSet(s); }
	public String getQ_dataState(){ return checkGet(q_dataState); }
	public void setQ_dataState(String s){ q_dataState=checkSet(s); }
	public String getQ_keepUnit(){ return checkGet(q_keepUnit); }
	public void setQ_keepUnit(String s){ q_keepUnit=checkSet(s); }
	public String getQ_keeper(){ return checkGet(q_keeper); }
	public void setQ_keeper(String s){ q_keeper=checkSet(s); }
	public String getQ_useUnit(){ return checkGet(q_useUnit); }
	public void setQ_useUnit(String s){ q_useUnit=checkSet(s); }
	public String getQ_userNo(){ return checkGet(q_userNo); }
	public void setQ_userNo(String s){ q_userNo=checkSet(s); }
	public String getQ_allCheck(){ return checkGet(q_allCheck); }
	public void setQ_allCheck(String s){ q_allCheck=checkSet(s); }
	public String getQ_libraryCheck(){ return checkGet(q_libraryCheck); }
	public void setQ_libraryCheck(String s){ q_libraryCheck=checkSet(s); }
	public String getState(){ return checkGet(state); }
	public void setState(String s){ state=checkSet(s); }
	
	public String[] q_place1;
	public String[] getQ_place1(){ return q_place1; }
	public void setQ_place1(String[] s){ q_place1=s; }
	
	private String[] q_sDestField;
	public String[] getQ_sDestField(){ return q_sDestField; }
	public void setQ_sDestField(String[] s){ q_sDestField=s; }
	
	
	public void del_addPdDate(User user) {
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
	
	private String getSQLwhere(){
		try {
			this.export();
		} catch (Exception e) {
//			System.out.println(e);
		}
		String sqlwhere="";
		if ("N".equals(getQ_allCheck())){
			if ("N".equals(getQ_libraryCheck())){
				sqlwhere += " and m.propertyno not like '503%'" + "\n";
			}
			if (!"".equals(getQ_enterOrg())) {
				sqlwhere += " and m.enterorg = " + Common.sqlChar(getQ_enterOrg()) + "\n";
			}
			if (!"".equals(getQ_ownership())) {
				sqlwhere += " and m.ownership = " + Common.sqlChar(getQ_ownership()) + "\n";
			}
			if (!"".equals(getQ_verify())) {
				sqlwhere += " and m.verify = " + Common.sqlChar(getQ_verify()) + "\n";
			}
			if (!"".equals(getQ_propertyNoS())) {
				sqlwhere += " and m.propertyno >= " + Common.sqlChar(getQ_propertyNoS()) + "\n";
			}
			if (!"".equals(getQ_propertyNoE())) {
				sqlwhere += " and m.propertyno <= " + Common.sqlChar(getQ_propertyNoE()) + "\n";
			}
			if (!"".equals(getQ_buyDateS())) {
				sqlwhere += " and b.buyDate >= " + Common.sqlChar(new Datetime().changeTaiwanYYMMDD(getQ_buyDateS(),"2")) + "\n";
			}
			if (!"".equals(getQ_buyDateE())) {
				sqlwhere += " and b.buyDate <= " + Common.sqlChar(new Datetime().changeTaiwanYYMMDD(getQ_buyDateE(),"2")) + "\n";
			}
			if (!"".equals(getQ_enterDateS())) {
				sqlwhere += " and a.enterDate >= " + Common.sqlChar(new Datetime().changeTaiwanYYMMDD(getQ_enterDateS(),"2")) + "\n";
			}
			if (!"".equals(getQ_enterDateE())) {
				sqlwhere += " and a.enterDate <= " + Common.sqlChar(new Datetime().changeTaiwanYYMMDD(getQ_enterDateE(),"2")) + "\n";
			}
			if (!"".equals(getQ_keepUnit())) {
				sqlwhere += " and m.keepUnit = " + Common.sqlChar(getQ_keepUnit()) + "\n";
			}
			if (!"".equals(getQ_keeper())) {
				sqlwhere += " and m.keeper = " + Common.sqlChar(getQ_keeper()) + "\n";
			}
			if (!"".equals(getQ_useUnit())) {
				sqlwhere += " and m.useUnit = " + Common.sqlChar(getQ_useUnit()) + "\n";
			}
			if (!"".equals(getQ_userNo())) {
				sqlwhere += " and m.userNo = " + Common.sqlChar(getQ_userNo()) + "\n";
			}
			if (!(getQ_sDestField()==null)){
				sqlwhere += "and m.place1 in ("+ strEngFields +")\n";
			}
		}else{
			if (!"".equals(getQ_enterOrg())) {
				sqlwhere += " and m.enterorg = " + Common.sqlChar(getQ_enterOrg()) + "\n";
			}
			if ("N".equals(getQ_libraryCheck())){
				sqlwhere += " and m.propertyno not like '503%'" + "\n";
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
	//取後單位最小日期
	public boolean getMinCloseDate(){
		boolean reMincloseDate = false;
		String sql = " select ISNULL(min(closingdate),'0') as minClosingdate from Untpd_checknonexp "
				   + "where 1=1 and enterorg = " + Common.sqlChar(getQ_enterOrg());
		Database db = new Database();
		ResultSet rs;
		try{
			rs = db.querySQL(sql);
			if (rs.next()){
				if(rs.getString("minClosingdate").equals(Datetime.getYYYMMDD())){
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
	//清除盤點資料
	private void dlePdDate(){
		String[] deletesql = new String[1];
		deletesql[0] = " delete Untpd_checknonexp "
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
	
	//累加產製相同資料刪除
	private void re_del_PdDate(){
		String[] deletesql = new String[1];
		deletesql[0] = " delete Untpd_checknonexp "
					 + " where 1=1 "
					 + " and propertyno in ( "
					 + " select m.propertyno  "
					 + " from UNTNE_NONEXPDETAIL m " 
					 + " inner join UNTNE_NONEXP b on m.enterorg=b.enterorg and m.ownership=b.ownership and m.differencekind=b.differencekind and m.propertyno=b.propertyno and m.lotno=b.lotno "
					 + " left join UNTNE_ADDPROOF a on m.enterorg=a.enterorg and m.caseno=a.caseno "
					 + " left join SYSCA_PLACE sp on (m.place1 = sp.placeno and m.enterorg = sp.enterorg) " 
					 + " where 1=1 "
					 + " and m.datastate='1' "
			   		 + getSQLwhere()
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
	
	public int getUseYear(String date){
		int useYear = 0;
		if(date != null){
			String today = Datetime.getYYYMMDD();
			String sdate = Datetime.changeTaiwanYYMMDD(date, "1");
			long calDate = Datetime.DateSubtraction(sdate, today);
			useYear = (int) (calDate/365);
		}
		
		return useYear;
	}
	
	//產製盤點資料
	private void addPdDate(){
		
		String sql = " select m.enterorg ,'' as serial1 ,m.barcode ,m.ownership ,'' as closingDate, m.differencekind " + "\n"
		   		   + " ,ceiling(m.bookamount) as bookamount ,'' as actualAmount " + "\n"	// 合購數量(bookamount<1)也顯示1
		   		   + " ,CASE b.originalamount WHEN null THEN null ELSE m.bookvalue END as bookUnit " + "\n"
		   		   + " ,m.bookvalue ,'3' as checkResult ,'' as oddsCause ,b.propertykind " + "\n"
		   		   + " ,b.fundtype ,b.propertyno ,m.serialno ,b.buydate ,b.sourcedate ,m.propertyname1 " + "\n"
		   		   + " ,m.oldpropertyno ,m.oldserialno ,b.nameplate ,b.specification ,b.othermaterial " + "\n"
		   		   + " ,b.otherpropertyunit ,m.otherlimityear ,m.place " + "\n" 
		   		   + " ,m.keepunit ,m.keeper ,m.useunit ,m.userno ,'' as notes " + "\n"
		   		   + " ,m.place1, sp.placename as place1name, m.otherLimitYear " + "\n"
		   		   
				   + " from UNTNE_NONEXPDETAIL m " 
				   + " inner join UNTNE_NONEXP b on m.enterorg=b.enterorg and m.ownership=b.ownership and m.differencekind=b.differencekind and m.propertyno=b.propertyno and m.lotno=b.lotno "
				   + " left join UNTNE_ADDPROOF a on m.enterorg=a.enterorg and m.caseno=a.caseno "
				   + " left join SYSCA_PLACE sp on (m.place1 = sp.placeno and m.enterorg = sp.enterorg) " 
				   
		   		   + " where 1=1 " + "\n"
		   		   + " and m.datastate='1' " + "\n"
		   		   + " and m.verify='Y' " + "\n"
		   		   + getSQLwhere()
			 	   + "";
		Database db = new Database();
		Connection con = null;
		Statement statement = null;
		String updatesql;
		ResultSet rs;
		Map DifferenceMap = TCGHCommon.getSysca_Code("DFK"); //財產區分別
		Map PropertyTypeMap = TCGHCommon.getSysca_Code("PTT"); //財產大類
		Map PropertyMap = TCGHCommon.getSyspk_PropertyCode2(q_enterOrg); //物品名稱
		Map KeepUnitMap = TCGHCommon.getUntmp_KeepUnitCode(q_enterOrg); //保管單位、使用單位
		Map KeeperMap = TCGHCommon.getUntmp_KeeperCode(q_enterOrg); //保管人、使用人
		Map PropertyUnitMap = TCGHCommon.getPropertyUnit(q_enterOrg); //物品單位
		int  Maxserialno1 = TCGHCommon.getMaxserialno1(q_enterOrg);
		String yyymmdd = Datetime.getYYYYMMDD();
		
		try{
			con = db.getConnection();
			con.setAutoCommit(false);
			statement = con.createStatement();
			
			System.out.println("產生盤點資料sql:"+"\n"+sql);
			rs = db.querySQL(sql);
			while(rs.next()){				
				String buyDate = Common.get(rs.getString("buydate"));
				int TempMonth = Datetime.BetweenTwoMonthCE(buyDate, yyymmdd);
				String useyear = String.valueOf(TempMonth / 12) + "年" + String.valueOf(TempMonth % 12) + "月";
				
				updatesql = " insert into UNTPD_CHECKNONEXP ( "
			 		   + " enterorg, "
			 		   + " serialno1, "
			 		   + " barcode, "
			 		   + " ownership, "
			 		   + " differencekind , "//differencekind
					   + " differencekindname , "//differencekindname
		//			   + " propertytype , "//propertytype
		//			   + " propertytypename , "//propertytypename
			 		   + " closingdate, "
			 		   + " bookamount, "
			 		   + " actualamount, "
			 		   + " bookunit, "
			 		   + " bookvalue, "
			 		   + " checkresult, "      // 盤點結果預設為3(未盤點)
			 		   + " oddscause, "
			 		   + " propertykind, "
			 		   + " fundtype, "
			 		   + " propertyno, "
			 		   + " serialno, "
			 		   + " buydate, "
			 		   + " sourcedate, "
			 		   + " propertyname , "//enterOrg+ propertyNo 取得SYSPK_PropertyCode2.propertyName
			 		   + " propertyname1, "
			 		   + " oldpropertyno, "
			 		   + " oldserialno, "
			 		   + " nameplate, "
			 		   + " specification, "
			 		   + " propertyunit, "
			 		   + " place, "
			 		   + " limityear, "
			 		   + " useyear, "
			 		   + " keepunit, "
			 		   + " keepunitname , "//enterOrg + keepUnit取得UNTMP_KeepUnit.unitName
			 		   + " keeper, "
			 		   + " keepername , "//enterOrg + keeper取得UNTMP_Keeper.keeperName
			 		   + " useunit, "
			 		   + " useunitname , "//enterOrg+ useUnit取得UNTMP_KeepUnit.unitName
			 		   + " userno, "
			 		   + " usernoname , "////enterOrg+ userNo取得UNTMP_Keeper.keeperName
			 		   + " notes, "
			 		   + " editid, "
			 		   + " editdate, "
			 		   + " edittime, "
			 		   + " place1, "
			 		   + " place1name "
			 		   + " )Values("
			 		   + Common.sqlChar(Common.get(rs.getString("enterOrg") )) + ","
			 		   + Maxserialno1 + ","
		//			   + " ( select ISNULL(max(serialno1),'0')+1 as maxserialno1 from UNTPD_CHECKNONEXP where 1=1 and enterorg = " + Common.sqlChar(Common.get(rs.getString("enterOrg"))) + ")" + ","
					   + Common.sqlChar(Common.get(rs.getString("barCode"))) + ","
					   + Common.sqlChar(Common.get(rs.getString("ownership"))) + ","
					   + Common.sqlChar(rs.getString("differencekind")) + ","//differencekind
					   + Common.sqlChar((String)DifferenceMap.get(rs.getString("differencekind"))) + ","//differencekindname
		//			   + Common.sqlChar(rs.getString("propertytype")) + ","//propertytype
		//			   + Common.sqlChar((String)PropertyTypeMap.get(rs.getString("propertytype"))) + ","//propertytypename
					   + Common.sqlChar(new Datetime().changeTaiwanYYMMDD(Datetime.getYYYMMDD(),"2")) + ","
					   + String.valueOf(Double.parseDouble(rs.getString("bookAmount"))) + ","
					   + rs.getString("actualAmount") + ","
			           + rs.getInt("bookUnit") + ","
			           + rs.getInt("bookValue") + ","
			   		   + Common.sqlChar(Common.get(rs.getString("checkResult"))) + ","
					   + Common.sqlChar(Common.get(rs.getString("oddsCause"))) + ","
					   + Common.sqlChar(Common.get(rs.getString("propertyKind"))) + ","
					   + Common.sqlChar(Common.get(rs.getString("fundType"))) + ","
					   + Common.sqlChar(Common.get(rs.getString("propertyNo"))) + ","
					   + Common.sqlChar(Common.get(rs.getString("serialNo"))) + ","
			   		   + Common.sqlChar(Common.get(rs.getString("buyDate"))) + ","
			   		   + Common.sqlChar(Common.get(rs.getString("sourceDate"))) + ","
			   		   + Common.sqlChar((String)PropertyMap.get(rs.getString("propertyno"))) + ","//enterOrg+ propertyNo 取得SYSPK_PropertyCode2.propertyName
			 		   + Common.sqlChar(Common.get(rs.getString("propertyName1")))+ ","
			 		   + Common.sqlChar(Common.get(rs.getString("oldPropertyNo"))) + ","
			 		   + Common.sqlChar(Common.get(rs.getString("oldSerialNo"))) + ","
			 		   + Common.sqlChar(Common.get(rs.getString("nameplate"))) + ","
			 		   + Common.sqlChar(Common.get(rs.getString("specification"))) + ","
			 		   + Common.sqlChar((String)PropertyUnitMap.get(rs.getString("propertyno"))) + ","//enterOrg+ propertyNo 取得SYSPK_PropertyCode2.propertyUnit
			 		   + "N" + Common.sqlChar(Common.get(rs.getString("place"))) + ","
			 		   + Common.sqlChar(Common.get(rs.getInt("otherLimitYear"))) + ","
		//	 		   + "(select limityear from SYSPK_PROPERTYCODE2 where 1=1 and enterorg = " + Common.sqlChar(Common.get(rs.getString("enterOrg"))) + " and propertyno ="+ Common.sqlChar(Common.get(rs.getString("propertyno"))) + "),"  //limityear
			 		   + Common.sqlChar(useyear)+ ","  //useyear
			 		   + Common.sqlChar(rs.getString("keepunit")) + ","//keepUnit
					   + "N" + Common.sqlChar((String)KeepUnitMap.get(rs.getString("keepunit"))) + ","//enterOrg + keepUnit取得UNTMP_KeepUnit.unitName
					   + Common.sqlChar(rs.getString("keeper")) + ","//keeper
					   + "N" + Common.sqlChar((String)KeeperMap.get(rs.getString("keeper"))) + ","//enterOrg + keeper取得UNTMP_Keeper.keeperName
					   + Common.sqlChar(rs.getString("useunit")) + ","//useunit
					   + "N" + Common.sqlChar((String)KeepUnitMap.get(rs.getString("useunit"))) + ","//enterOrg + useunit取得UNTMP_KeepUnit.unitName
					   + Common.sqlChar(rs.getString("userno")) + ","//keeper
					   + "N" + Common.sqlChar((String)KeeperMap.get(rs.getString("userno"))) + ","//enterOrg + userno取得UNTMP_Keeper.keeperName
			 		   + "N" + Common.sqlChar(Common.get(rs.getString("notes"))) + ","
			 		   + Common.sqlChar(getUserID()) + ","
			 		   + Common.sqlChar(new Datetime().changeTaiwanYYMMDD(Datetime.getYYYMMDD(),"2")) + ","
			 		   + Common.sqlChar(Datetime.getHHMMSS()) + ","
			 		   + Common.sqlChar(rs.getString("place1")) + ","
			 		   + "N" + Common.sqlChar(rs.getString("place1name"))
			 		   + ")" ;
				Maxserialno1++;
				//System.out.println(updatesql);
				statement.addBatch(updatesql);
				
			}
			int[] execCnts = statement.executeBatch();
			//System.out.println("共exec:" + execCnts.length + "筆sql");
			con.commit();
			setErrorMsg("盤點資料批次產生完成");
		} catch (Exception e) {
			e.printStackTrace();
			if (con != null) {
				try {
					con.rollback();
				} catch (Exception rbe) {
					rbe.printStackTrace();
				}
			}
			setErrorMsg("非消盤點資料批次產生發生錯誤");
		} finally {
			if (statement != null) {
				try {
					statement.close();
				} catch (Exception e) {
					//ignore
				}
			}
			if (con != null) {
				try {
					con.close();
					con = null;
				} catch (Exception e) {
					//ignore 
				}
			}
			db.closeAll();
		}
		setState("addPDdateSuccess");
	}
	
	public String getFieldList(String mgrOrg, String userOrg) throws Exception {
		Database db = new Database();
		ResultSet rs = null;
		String sSQL = "";
		StringBuffer sb = new StringBuffer("");
		try {
			sSQL = "select placeno,placename from SYSCA_PLACE where enterorg = ";
			sSQL +="'"+userOrg+"'" + " order by placeno";
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
	
}//public class UNTPD013F extends QueryBean


