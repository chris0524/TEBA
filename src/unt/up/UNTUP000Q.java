package unt.up;

import java.sql.ResultSet;

import org.joda.time.DateTime;

import util.Database;
import util.Datetime;
import util.Function;
import util.QueryBean;
import util.Common;

/**
 * 資料上傳作業共用項目
 * 
 * @version 1.0, Jul 7, 2006
 * @author  clive.chang
 * @since   TAJK Project
 */
public class UNTUP000Q extends QueryBean {
	String enterOrg;
	String enterOrgName;
	String ownership;
	String isOrganManager;
	String isAdminManager;
	String organID;
	String excelFileName;
	
	String q_enterOrg;
	String q_enterOrgName;
	String uploadType;
	
	public String getUploadType(){ return checkGet(uploadType); }
	public void setUploadType(String s){ uploadType=checkSet(s); }	
	public String getQ_enterOrg(){ return checkGet(q_enterOrg); }
	public void setQ_enterOrg(String s){ q_enterOrg=checkSet(s); }
	public String getQ_enterOrgName(){ return checkGet(q_enterOrgName); }
	public void setQ_enterOrgName(String s){ q_enterOrgName=checkSet(s); }  	
	public String getExcelFileName() { return checkGet(excelFileName); }
	public void setExcelFileName(String s) { excelFileName = checkSet(s); }
	public String getEnterOrg(){ return checkGet(enterOrg); }
	public void setEnterOrg(String s){ enterOrg=checkSet(s); }
	public String getEnterOrgName(){ return checkGet(enterOrgName); }
	public void setEnterOrgName(String s){ enterOrgName=checkSet(s); }
	public String getOwnership(){ return checkGet(ownership); }
	public void setOwnership(String s){ ownership=checkSet(s); }
	public String getOrganID() { return checkGet(organID); }
	public void setOrganID(String s) { organID = checkSet(s); }
	public String getIsOrganManager() { return checkGet(isOrganManager); }
	public void setIsOrganManager(String s) { isOrganManager = checkSet(s); }
	public String getIsAdminManager() { return checkGet(isAdminManager); }
	public void setIsAdminManager(String s) { isAdminManager = checkSet(s); }	
	
	int errorRecordCount = 0;
	int successRecordCount = 0;
	int errorDtlRecordCount = 0;
	int successDtlRecordCount = 0;
	public void setErrorRecordCount(int s) { errorRecordCount = s ; }
	public int getErrorRecordCount() {return errorRecordCount;}
	public void setSuccessRecordCount(int s) { successRecordCount = s ; }
	public int getSuccessRecordCount() { return successRecordCount; }		
	public void setDtlErrorRecordCount(int s) { errorDtlRecordCount = s ; }
	public int getDtlErrorRecordCount() {return errorDtlRecordCount;}
	public void setDtlSuccessRecordCount(int s) { successDtlRecordCount = s ; }
	public int getDtlSuccessRecordCount() { return successDtlRecordCount; }	
	
	protected String getUploadType(String s) {
		String[] arrUploadName = {"土地","建物","土地改良物","動產","有價證券","權利"};
		String[] arrUploadType = {"LA","BU","RF","MP","VP","RT"};
		for (int i=0; i<arrUploadType.length; i++) {
			if (arrUploadType[i].equals(s)) return arrUploadName[i];
		}
		return "";
	}
	
	protected int getProcessType(String s) {
		int[] arrUploadName = {1,2,3,4,5,6};
		String[] arrUploadType = {"LA","BU","RF","MP","VP","RT"};
		for (int i=0; i<arrUploadType.length; i++) {
			if (arrUploadType[i].equals(s)) return arrUploadName[i];
		}
		return 1;
	}
	
	protected String getUploadTable(String s) {
		String[] arrUploadName = {"UNTUP_LAND","UNTUP_BUILDING","UNTUP_Attachment","UNTUP_Movable","UNTUP_ValuePaper1","UNTUP_ValuePaper2","UNTUP_Right1","UNPUP_Right2"};
		String[] arrUploadType = {"LA","BU","RF","MP","VP","VP1","RT","RT1"};
		for (int i=0; i<arrUploadType.length; i++) {
			if (arrUploadType[i].equals(s)) return arrUploadName[i];
		}
		return "";
	}
	
	protected String getUploadDtlTable(String s) {
		if (s.equals("VP")) return "UNTUP_ValuePaper2";
		else if (s.equals("RT")) return "UNTUP_Right2";
		else return "";
	}	
		
	protected String getCodeName(String codekindid, String s) {
		Database db = new Database();
		try {
			ResultSet rs = db.querySQL("select codeName from sysca_code where codekindid='"+codekindid+"' AND codeid='"+s+"'");
			if (rs.next()) {
				return Common.get(rs.getString("codeName"));
			}
			rs.getStatement().close();
			rs.close();
		} catch(Exception e) {
			e.printStackTrace();
			return "";
		} finally {
			db.closeAll();
		}
		return "";
	}
	
	protected String getLookupField(String sql) {
		Database db = new Database();
		try {
			ResultSet rs = db.querySQL(sql);
			if (rs.next()) {
				return Common.get(rs.getString(1));
			}
			rs.getStatement().close();
			rs.close();
		} catch(Exception e) {
			e.printStackTrace();
			return "";
		} finally {
			db.closeAll();
		}
		return "";
	}	
	
	protected String getOrganSName(String s) {
		Database db = new Database();
		try {
			ResultSet rs = db.querySQL("select organSName from sysca_organ where organID='"+s+"'");
			if (rs.next()) {
				return Common.get(rs.getString(1));
			}
			rs.getStatement().close();
			rs.close();
		} catch(Exception e) {
			e.printStackTrace();
			return "";
		} finally {
			db.closeAll();
		}
		return "";
	}	
	
	protected String getSignName(String s) {
		Database db = new Database();
		try {
			ResultSet rs = db.querySQL("select signdesc||signName as signName from sysca_sign where signNo=substr('"+s+"',1,7)");
			if (rs.next()) {
				return Common.get(rs.getString(1));
			}
			rs.getStatement().close();
			rs.close();
		} catch(Exception e) {
			e.printStackTrace();
			return "";
		} finally {
			db.closeAll();
		}
		return "";
	}
	
	protected boolean checkPropertyKind(String s) {
		String[] arrCode = new String[]{"01","02","03","04"};
		for (int i=0; i<arrCode.length; i++) {
			if (s.equals(arrCode[i])) return true;
		}
		return false;
	}
	
	protected boolean checkSignNameExists(String type, String enterOrg, String ownership, String s) {
		Database db = new Database();
		try {
			String sql = " select count(signNo) from untla_land where dataState='1' and enterOrg=" + Common.sqlChar(enterOrg) + " and ownership=" + Common.sqlChar(ownership) +" and signNo="+Common.sqlChar(s);
			if (type.equals("BU")) {
				sql = " select count(signNo) from untbu_building where dataState='1' and enterOrg=" + Common.sqlChar(enterOrg) + " and ownership=" + Common.sqlChar(ownership) +" and signNo="+Common.sqlChar(s);
			}
			ResultSet rs = db.querySQL(sql);
			if (rs.next() && rs.getInt(1)>0) {
				return true;
			}
			rs.getStatement().close();
			rs.close();
		} catch(Exception e) {
			e.printStackTrace();
			return false;
		} finally {
			db.closeAll();
		}
		return false;
	}
	
	protected String[] getDeprResult(int deprMethod, String sDate, String limitYear, double originalBV){		
		double scrapValue = 0, monthDepr = 0, deprAmount = 0, accumDepr = 0;		
		String apportionYear = "", apportionEndYM = "", accumDeprYM="";
		DateTime enterDate =  new DateTime(Integer.parseInt(sDate.substring(0,3))+1911,Integer.parseInt(sDate.substring(3,5)),Integer.parseInt(sDate.substring(5)), 0, 0, 0, 0);		
		switch (deprMethod) {
		case 1:									
			break;
		case 2:							
			scrapValue = Math.round(originalBV/(Function.getNumeric(limitYear)+1));
			deprAmount = originalBV - scrapValue;
			apportionYear = limitYear;
			monthDepr = Math.round(originalBV/Function.getNumeric(apportionYear)/12);
			apportionEndYM = Datetime.getYYYMMDD(enterDate.plusYears(Integer.parseInt(limitYear)).minusMonths(1).toDate()).substring(0,5);
			accumDeprYM = Datetime.getYYYMMDD(enterDate.minusMonths(1).toDate()).substring(0,5);
			accumDepr = 0;
			break;
		case 3:
			scrapValue = Math.round(originalBV/(Function.getNumeric(limitYear)+1));
			deprAmount = originalBV;
			apportionYear = ""+(Function.getNumeric(limitYear)+1);
			monthDepr = Math.round(originalBV/Function.getNumeric(apportionYear)/12);
			apportionEndYM = Datetime.getYYYMMDD(enterDate.plusYears(Integer.parseInt(limitYear)).minusMonths(1).toDate()).substring(0,5);
			accumDeprYM = Datetime.getYYYMMDD(enterDate.minusMonths(1).toDate()).substring(0,5);
			accumDepr = 0;
			break;
		case 4:
			scrapValue = 0;
			deprAmount = originalBV;
			apportionYear = ""+(Function.getNumeric(limitYear)+1);
			monthDepr = Math.round(originalBV/Function.getNumeric(apportionYear)/12);
			apportionEndYM = Datetime.getYYYMMDD(enterDate.plusYears(Integer.parseInt(limitYear)).minusMonths(1).toDate()).substring(0,5);
			accumDeprYM = Datetime.getYYYMMDD(enterDate.minusMonths(1).toDate()).substring(0,5);
			accumDepr = 0;							
			break;
		case 5:
			scrapValue=0;
			break;																					
		}
		return new String[]{Double.toString(scrapValue),Double.toString(deprAmount),apportionYear,Double.toString(monthDepr),apportionEndYM,accumDeprYM,Double.toString(accumDepr)};
	}
	
	
	/*
	 * 000000000A為共用編號的機關代碼, 若不是用共用編號請記得setEnterOrg
	 */
	protected String getPropertyName(String enterOrg, String s) {
		Database db = new Database();
		try {		
			String sid = Common.get(s);
			String sql = "select propertyName from ";
			if (!"".equals(sid)) {
				if (sid.length()>3 && (sid.substring(0,3).equals("503")||sid.substring(0,3).equals("504")||sid.substring(0,3).equals("505"))){
					sql += " syspk_propertyCode where enterOrg="+Common.sqlChar(enterOrg) + " and propertyNo=" + Common.sqlChar(sid);
				} else if (sid.substring(0,1).equals("6")) {
					sql += " syspk_propertyCode2 where enterOrg="+Common.sqlChar(enterOrg) + " and propertyNo=" + Common.sqlChar(sid);
				} else {
					sql += " syspk_propertyCode where enterOrg='000000000A' and propertyNo="+Common.sqlChar(sid);
				}
				ResultSet rs = db.querySQL(sql);
				if (rs.next()) {
					return Common.get(rs.getString(1));
				}
				rs.getStatement().close();
				rs.close();
			}
		} catch(Exception e) {
			e.printStackTrace();
			return "";
		} finally {
			db.closeAll();
		}
		return "";
	}

	protected boolean checkRecordExists(String sTable, String enterOrg, String ownership, String propertyKind, String fundType, String valuable, String oldPropertyNo, String oldSerialNo) {
		Database db = new Database();
		try {
			String sSQL = "select count(*) from " + sTable + " where enterOrg=" + Common.sqlChar(Common.get(enterOrg)) +
				" and ownership="+Common.sqlChar(Common.get(ownership)) +
				" and propertyKind="+Common.sqlChar(Common.get(propertyKind))+
				" and nvl(fundType,'99')=nvl('"+Common.get(fundType)+"','99') " +
				" and nvl(valuable,'N')=nvl('"+Common.get(valuable)+"','N') " +
				" and oldPropertyNo="+Common.sqlChar(Common.get(oldPropertyNo))+
				" and oldSerialNo="+Common.sqlChar(Common.get(oldSerialNo));					
			ResultSet rs = db.querySQL(sSQL);
			if (rs.next() && rs.getInt(1)>0) {
				return true;
			}
			rs.getStatement().close();
			rs.close();			
		} catch(Exception e) {
			e.printStackTrace();
			return false;
		} finally {
			db.closeAll();
		}
		return false;
	}
	
	protected boolean checkVPExists(String sTable, String enterOrg, String ownership, String propertyKind, String fundType, String oldPropertyNo, String oldSerialNo) {
		Database db = new Database();
		try {
			String sSQL = "select count(*) from " + sTable + " where enterOrg=" + Common.sqlChar(Common.get(enterOrg)) +
				" and ownership="+Common.sqlChar(Common.get(ownership)) +
				" and propertyKind="+Common.sqlChar(Common.get(propertyKind))+
				" and nvl(fundType,'99')=nvl('"+Common.get(fundType)+"','99') " +
				" and oldPropertyNo="+Common.sqlChar(Common.get(oldPropertyNo))+
				" and oldSerialNo="+Common.sqlChar(Common.get(oldSerialNo));					
			ResultSet rs = db.querySQL(sSQL);
			if (rs.next() && rs.getInt(1)>0) {
				return true;
			}
			rs.getStatement().close();
			rs.close();			
		} catch(Exception e) {
			e.printStackTrace();
			return false;
		} finally {
			db.closeAll();
		}
		return false;
	}	
	
	
	
	//檢查動產資料是否重覆
	protected boolean checkMovableExists(String enterOrg, String ownership, String propertyKind, String fundType, String valuable, String oldPropertyNo, String oldSerialNo) {
		Database db = new Database();
		try {
			String sSQL = "select count(*) from UNTMP_Movable a, UNTMP_MovableDetail b where a.enterOrg=b.enterOrg " +
				" and a.ownership=b.ownership and a.propertyNo=b.propertyNo and a.lotno=b.lotno " +
				" and a.enterOrg=" + Common.sqlChar(Common.get(enterOrg)) +
				" and a.ownership="+Common.sqlChar(Common.get(ownership)) +
				" and a.propertyKind="+Common.sqlChar(Common.get(propertyKind))+
				" and nvl(a.fundType,'99')=nvl('"+Common.get(fundType)+"','99') " +
				" and nvl(a.valuable,'N')=nvl('"+Common.get(valuable)+"','N') " +
				" and b.oldPropertyNo="+Common.sqlChar(Common.get(oldPropertyNo))+
				" and b.oldSerialNo="+Common.sqlChar(Common.get(oldSerialNo));	
			ResultSet rs = db.querySQL(sSQL);
			if (rs.next() && rs.getInt(1)>0) {
				return true;
			}
			rs.getStatement().close();
			rs.close();
		} catch(Exception e) {
			e.printStackTrace();
			return false;
		} finally {
			db.closeAll();
		}
		return false;
	}	
		
	//originalBasis原始入帳依據, orginalDate公告年月
	protected boolean checkBulletinDateExists(String originalBasis, String orginalDate) {
		Database db = new Database();
		try {
			if (!"".equals(Common.get(originalBasis)) && !"".equals(Common.get(orginalDate))) {
				ResultSet rs = db.querySQL("select count(*) from UNTLA_BulletinDate where bulletinKind=" + Common.sqlChar(originalBasis) + " and bulletinDate=" + Common.sqlChar(orginalDate));
				if (rs.next() && rs.getInt(1)>0) {
					return true;
				}
				rs.getStatement().close();
				rs.close();
			}
		} catch(Exception e) {
			e.printStackTrace();
			return false;
		} finally {
			db.closeAll();
		}
		return false;
	}		
	
    // 取得最大的月結年月
	protected String getMaxClosingYM(String enterOrg){
		return Common.getMaxClosingYM(enterOrg);
		/**
		Database db = new Database();
    	String closingYM ="00000" ;    	
    	try {		
    		ResultSet rs = db.querySQL("select max(closingYM) as closingYM from UNTGR_Closing where enterOrg = " + Common.sqlChar(enterOrg));
    		if ((rs.next()) && (Common.get(rs.getString(1)).length()==5)){
    		    closingYM = Common.get(rs.getString("closingYM"));
    		}
    		rs.getStatement().close();
    		rs.close();
    	} catch (Exception e) {
    		e.printStackTrace();
    		return closingYM;
    	} finally {
    		db.closeAll();
    	}
    	return closingYM;
    	**/
	}
	

	protected String getKeepUnitSQL(String enterOrg, String unitNo, String unitName) {
		StringBuffer sbSQL = new StringBuffer(""); 
		if (!"".equals(Common.get(enterOrg)) && !"".equals(Common.get(unitNo)) && !"".equals(Common.get(unitName)) && "".equals(getLookupField(" select unitNo from untmp_keepunit where enterOrg='"+enterOrg+"' and unitNo='"+unitNo+"'"))) {
			sbSQL.append(" delete from UNTMP_KeepUnit where enterOrg='").append(enterOrg).append("' and unitNo='").append(unitNo).append("':;:");
			sbSQL.append(" insert into UNTMP_KeepUnit(" ).append(
				" enterOrg,").append(
				" unitNo,").append(
				" unitName,").append(
				" notes,").append(
				" editID,").append(
				" editDate,").append(
				" editTime").append(
			" )Values(" ).append(
				Common.sqlChar(enterOrg) ).append( "," ).append(
				Common.sqlChar(Common.get(unitNo)) ).append( "," ).append(
				Common.sqlChar(Common.get(unitName)) ).append( "," ).append(
				Common.sqlChar("由上傳作業輸入") ).append( "," ).append(
				Common.sqlChar(getEditID()) ).append( "," ).append(
				Common.sqlChar(Datetime.getYYYMMDD()) ).append( "," ).append(
				Common.sqlChar(Datetime.getHHMMSS()) ).append( "):;:") ;
		}
		return sbSQL.toString();
	}

	protected String getKeeperSQL(String enterOrg, String unitNo, String userNo, String userName) {
		StringBuffer sbSQL = new StringBuffer("");
		if (!"".equals(Common.get(unitNo)) && !"".equals(Common.get(userNo)) && !"".equals(Common.get(userName)) && "".equals(getLookupField(" select unitNo from UNTMP_Keeper where enterOrg='"+enterOrg+"' and unitNo='"+unitNo+"' and keeperNo='"+userNo+"'"))) {
			sbSQL.append(" delete from UNTMP_Keeper where enterOrg='").append(enterOrg).append("' and unitNo='").append(unitNo).append("' and keeperNo='").append(userNo).append("':;:");			
			sbSQL.append(" insert into UNTMP_Keeper(" ).append(
				" enterOrg,").append(
				" unitNo,").append(
				" keeperNo,").append(
				" keeperName,").append(									
				" notes,").append(
				" editID,").append(
				" editDate,").append(
				" editTime").append(
			" )Values(" ).append(
				Common.sqlChar(enterOrg) ).append( "," ).append(
				Common.sqlChar(Common.get(unitNo)) ).append( "," ).append(
				Common.sqlChar(Common.get(userNo)) ).append( "," ).append(
				Common.sqlChar(Common.get(userName)) ).append( "," ).append(									
				Common.sqlChar("由上傳作業輸入") ).append( "," ).append(
				Common.sqlChar(getEditID()) ).append( "," ).append(
				Common.sqlChar(Datetime.getYYYMMDD()) ).append( "," ).append(
				Common.sqlChar(Datetime.getHHMMSS()) ).append( "):;:") ;
		}	
		return sbSQL.toString();
	}

	protected String getSupplierSQL(String enterOrg, String storeNo, String storeName, String companyID, String tel1, String tel2, String seller, String fax) {
		StringBuffer sbSQL = new StringBuffer("");
		if (!"".equals(Common.get(storeNo)) && !"".equals(Common.get(storeName)) && "".equals(getLookupField(" select storeNo from UNTMP_Store where enterOrg='"+enterOrg+"' and storeNo='"+storeNo+"'"))) {
			sbSQL.append(" delete from UNTMP_Store where enterOrg='").append(enterOrg).append("' and storeNo='"+storeNo+"':;:");			
			sbSQL.append(" insert into UNTMP_Store(" ).append(
				" enterOrg,").append(
				" storeNo,").append(
				" storeName,").append(
				" companyID,").append(
				" tel1,").append(
				" tel2,").append(
				" seller,").append(
				" fax,").append(
				" notes,").append(
				" editID,").append(
				" editDate,").append(
				" editTime").append(
			" )Values(" ).append(
				Common.sqlChar(enterOrg) ).append( "," ).append(
				Common.sqlChar(Common.get(storeNo)) ).append( "," ).append(
				Common.sqlChar(Common.get(storeName)) ).append( "," ).append(
				Common.sqlChar(Common.get(companyID)) ).append( "," ).append(
				Common.sqlChar(Common.get(tel1)) ).append( "," ).append(
				Common.sqlChar(Common.get(tel2)) ).append( "," ).append(
				Common.sqlChar(Common.get(seller)) ).append( "," ).append(
				Common.sqlChar(Common.get(fax)) ).append( "," ).append(
				Common.sqlChar("由上傳作業輸入") ).append( "," ).append(
				Common.sqlChar(getEditID()) ).append( "," ).append(
				Common.sqlChar(Datetime.getYYYMMDD()) ).append( "," ).append(
				Common.sqlChar(Datetime.getHHMMSS()) ).append( "):;:") ;					
		}
		return sbSQL.toString();
	}	
}
