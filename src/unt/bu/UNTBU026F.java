/*
*<br>程式目的：建物所有權狀審核作業
*<br>程式代號：untla026f
*<br>程式日期：0940920
*<br>程式作者：clive.chang
*<br>--------------------------------------------------------
*<br>修改作者　　修改日期　　　修改目的
*<br>--------------------------------------------------------
*/

package unt.bu;


import java.sql.ResultSet;
import java.util.ArrayList;
import util.*;
import TDlib_Simple.tools.src.StringTools;

public class UNTBU026F extends SuperBean{
String proofVerify;
String proofVerifyName;
String enterOrg;
String ownership;
String ownershipName;
String propertyNo;
String serialNo;
String propertyName;
String propertyName1;
String signNo;
String signName;
String ownershipDate;
String proofDoc;
String area;
String holdNume;
String holdDeno;
String holdRang;
String holdArea;

String q_enterOrg;
String q_dataState;
String q_ownership;
String q_signNo1;
String q_signNo2;
String q_signNo3;
String q_signNo4;
String q_signNo5;
String q_signNo = "";


String otherLimitYear;
String buildStyle;
String originalCArea;
String originalSArea;
String CArea;
String SArea;
String buildDate;
String floor1;
String floor2;
String stuff;
String damageDate;
String damageExpire;
String damageMark;
String deprMethod;
String scrapValue;
String deprAmount;
String apportionYear;
String monthDepr;
String useEndYM;
String apportionEndYM;
String accumDeprYM;
String accumDepr;
String permitReduceDate;
String propertyType;
String propertyUnit;
String material;
String limitYear;


String doorPlate1;
String doorPlate2;
String doorPlate3;
String doorPlate4;


String sSQL = "";
String strKeySet[] = null;

public String getDoorPlate1(){ return checkGet(doorPlate1); }
public void setDoorPlate1(String s){ doorPlate1=checkSet(s); }
public String getDoorPlate2(){ return checkGet(doorPlate2); }
public void setDoorPlate2(String s){ doorPlate2=checkSet(s); }
public String getDoorPlate3(){ return checkGet(doorPlate3); }
public void setDoorPlate3(String s){ doorPlate3=checkSet(s); }
public String getDoorPlate4(){ return checkGet(doorPlate4); }
public void setDoorPlate4(String s){ doorPlate4=checkSet(s); }
public String getOtherLimitYear(){ return checkGet(otherLimitYear); }
public void setOtherLimitYear(String s){ otherLimitYear=checkSet(s); }
public String getBuildStyle(){ return checkGet(buildStyle); }
public void setBuildStyle(String s){ buildStyle=checkSet(s); }
public String getOriginalCArea(){ return checkGet(originalCArea); }
public void setOriginalCArea(String s){ originalCArea=checkSet(s); }
public String getOriginalSArea(){ return checkGet(originalSArea); }
public void setOriginalSArea(String s){ originalSArea=checkSet(s); }
public String getCArea(){ return checkGet(CArea); }
public void setCArea(String s){ CArea=checkSet(s); }
public String getSArea(){ return checkGet(SArea); }
public void setSArea(String s){ SArea=checkSet(s); }
public String getBuildDate(){ return checkGet(buildDate); }
public void setBuildDate(String s){ buildDate=checkSet(s); }
public String getFloor1(){ return checkGet(floor1); }
public void setFloor1(String s){ floor1=checkSet(s); }
public String getFloor2(){ return checkGet(floor2); }
public void setFloor2(String s){ floor2=checkSet(s); }
public String getStuff(){ return checkGet(stuff); }
public void setStuff(String s){ stuff=checkSet(s); }
public String getDamageDate(){ return checkGet(damageDate); }
public void setDamageDate(String s){ damageDate=checkSet(s); }
public String getDamageExpire(){ return checkGet(damageExpire); }
public void setDamageExpire(String s){ damageExpire=checkSet(s); }
public String getDamageMark(){ return checkGet(damageMark); }
public void setDamageMark(String s){ damageMark=checkSet(s); }
public String getDeprMethod(){ return checkGet(deprMethod); }
public void setDeprMethod(String s){ deprMethod=checkSet(s); }
public String getScrapValue(){ return checkGet(scrapValue); }
public void setScrapValue(String s){ scrapValue=checkSet(s); }
public String getDeprAmount(){ return checkGet(deprAmount); }
public void setDeprAmount(String s){ deprAmount=checkSet(s); }
public String getApportionYear(){ return checkGet(apportionYear); }
public void setApportionYear(String s){ apportionYear=checkSet(s); }
public String getMonthDepr(){ return checkGet(monthDepr); }
public void setMonthDepr(String s){ monthDepr=checkSet(s); }
public String getUseEndYM(){ return checkGet(useEndYM); }
public void setUseEndYM(String s){ useEndYM=checkSet(s); }
public String getApportionEndYM(){ return checkGet(apportionEndYM); }
public void setApportionEndYM(String s){ apportionEndYM=checkSet(s); }
public String getAccumDeprYM(){ return checkGet(accumDeprYM); }
public void setAccumDeprYM(String s){ accumDeprYM=checkSet(s); }
public String getAccumDepr(){ return checkGet(accumDepr); }
public void setAccumDepr(String s){ accumDepr=checkSet(s); }
public String getPermitReduceDate(){ return checkGet(permitReduceDate); }
public void setPermitReduceDate(String s){ permitReduceDate=checkSet(s); }

public String getProofVerify(){ return checkGet(proofVerify); }
public void setProofVerify(String s){ proofVerify=checkSet(s); }
public String getProofVerifyName(){ return checkGet(proofVerifyName); }
public void setProofVerifyName(String s){ proofVerifyName=checkSet(s); }
public String getEnterOrg(){ return checkGet(enterOrg); }
public void setEnterOrg(String s){ enterOrg=checkSet(s); }
public String getOwnerShip() {return checkGet(ownership); }
public void setOwnerShip(String s) { ownership=checkSet(s); }
public String getPropertyNo() { return checkGet(propertyNo); }
public void setPropertyNo(String s) { propertyNo=checkSet(s); }
public String getSerialNo() { return checkGet(serialNo); }
public void setSerialNo(String s) { serialNo=checkSet(s); }
public String getPropertyName() { return checkGet(propertyName); }
public void setPropertyName(String s) { propertyName=checkSet(s); }
public String getPropertyName1() { return checkGet(propertyName1); }
public void setPropertyName1(String s) { propertyName1=checkSet(s); }
public String getSignNo() { return checkGet(signNo); }
public void setSignNo(String s) { signNo=checkSet(s); }
public String getSignName() { return checkGet(signName); }
public void setSignName(String s) { signName=checkSet(s); }
public String getOwnerShipDate() { return checkGet(ownershipDate); }
public void setOwnerShipDate(String s) { ownershipDate=checkSet(s); }
public String getProofDoc() { return checkGet(proofDoc); }
public void setProofDoc(String s) { proofDoc=checkSet(s); }
public String getArea() { return checkGet(area); }
public void setArea(String s) { area=checkSet(s); }
public String getHoldNume() { return checkGet(holdNume); }
public void setHoldNume(String s) { holdNume=checkSet(s); }
public String getHoldDeno() { return checkGet(holdDeno); }
public void setHoldDeno(String s) { holdDeno=checkSet(s); }
public String getHoldRang() { return checkGet(holdRang); }
public void setHoldRang(String s) { holdRang=checkSet(s); }
public String getHoldArea() { return checkGet(holdArea); }
public void setHoldArea(String s) { holdArea=checkSet(s); }
public String getOwnership(){ return checkGet(ownership); }
public void setOwnership(String s){ ownership=checkSet(s); }
public String getOwnershipName(){ return checkGet(ownershipName); }
public void setOwnershipName(String s){ ownershipName=checkSet(s); }
public String getQ_enterOrg(){ return checkGet(q_enterOrg); }
public void setQ_enterOrg(String s){ q_enterOrg=checkSet(s); }
public String getQ_dataState(){ return checkGet(q_dataState); }
public String getQ_ownership() {
	if (!"".equals(checkGet(q_ownership))) return checkGet(q_ownership);
	else return "1"; 
}
public void setQ_ownership(String s) { q_ownership=checkSet(s); }
public String getQ_signNo1(){ return checkGet(q_signNo1); }
public void setQ_signNo1(String s){ q_signNo1=checkSet(s); }
public String getQ_signNo2(){ return checkGet(q_signNo2); }
public void setQ_signNo2(String s){ q_signNo2=checkSet(s); }
public String getQ_signNo3(){ return checkGet(q_signNo3); }
public void setQ_signNo3(String s){ q_signNo3=checkSet(s); }
public String getQ_signNo4(){ return checkGet(q_signNo4); }
public void setQ_signNo4(String s){ q_signNo4=checkSet(s); }
public String getQ_signNo5(){ return checkGet(q_signNo5); }
public void setQ_signNo5(String s){ q_signNo5=checkSet(s); }

public String getsSQL(){ return checkGet(sSQL); }
public void setsSQL(String s){ sSQL=checkSet(s); }

public String[] getsKeySet(){ return strKeySet; }
public void setsKeySet(String[] s){ strKeySet=s; }


String isOrganManager;
String isAdminManager;
String organID;    
public String getOrganID() { return checkGet(organID); }
public void setOrganID(String s) { organID = checkSet(s); }
public String getIsOrganManager() { return checkGet(isOrganManager); }
public void setIsOrganManager(String s) { isOrganManager = checkSet(s); }
public String getIsAdminManager() { return checkGet(isAdminManager); }
public void setIsAdminManager(String s) { isAdminManager = checkSet(s); } 
String q_proofVerify;
public String getQ_proofVerify(){ return checkGet(q_proofVerify); }
public void setQ_proofVerify(String s){ q_proofVerify=checkSet(s); }

public UNTBU026F  queryOne() throws Exception{
	Database db = new Database();
	UNTBU026F obj = this;
	
	try {
		String sql="select a.proofVerify, case a.proofVerify when 'Y' then '審核' else '未審核' end as proofVerifyName, a.enterOrg, " +
			"a.ownership, a.propertyNo, a.serialNo, a.propertyName1, a.signNo, a.ownershipDate, a.proofDoc, a.CArea, a.SArea, a.area, a.holdNume, a.holdDeno, a.holdArea, a.editID, a.editDate, a.editTime " +
			", case a.ownership when '2' then '國有' else '市有' end as ownershipName "+
			", c.propertyName,d.addrName as doorPlate1, e.addrName as doorPlate2, " +
			"f.addrName as doorPlate3, a.doorPlate4, a.buildDate, g.codeName as stuff " +			
			" from UNTBU_Building a" +
			" left join SYSCA_Addr d on a.doorPlate1=d.addrID" +
			" left join SYSCA_Addr e on a.doorPlate2=e.addrID" +
			" left join SYSCA_Addr f on a.doorPlate3=f.addrID" +
			" left join SYSCA_Code g on a.doorPlate3=f.addrID and g.codekindID='STU'" +
			", SYSPK_PropertyCode c " +
			" where a.propertyNo=c.propertyNo and a.dataState=1 " +
			" and a.enterOrg = " + Common.sqlChar(enterOrg) +
			" and a.ownership = " + Common.sqlChar(ownership) +
			" and a.propertyNo = " + Common.sqlChar(propertyNo) +
			" and a.serialNo = " + Common.sqlChar(serialNo) +
			"";				
		
		StringTools st = new StringTools();
		ResultSet rs = db.querySQL(sql);
		if (rs.next()){
			obj.setProofVerify(rs.getString("proofVerify"));
			obj.setProofVerifyName(rs.getString("proofVerifyName"));			
			obj.setEnterOrg(rs.getString("enterOrg"));
			obj.setOwnerShip(rs.getString("ownership"));
			obj.setOwnershipName(rs.getString("ownershipName"));			
			obj.setPropertyNo(rs.getString("propertyNo"));
			obj.setSerialNo(rs.getString("serialNo"));
			obj.setPropertyName1(rs.getString("propertyName1"));
			obj.setPropertyName(rs.getString("propertyName"));			
			obj.setSignNo(rs.getString("signNo"));
			obj.setSignName(getSignDescName(rs.getString("signNo").substring(0,7)) + rs.getString("SignNo").substring(7,12) + "-" + rs.getString("SignNo").substring(12));
			obj.setOwnerShipDate(rs.getString("ownershipDate"));
			obj.setProofDoc(rs.getString("proofDoc"));
			obj.setArea(st._getDotFormat(rs.getString("area"),2));
			obj.setHoldNume(rs.getString("holdNume"));
			obj.setHoldDeno(rs.getString("holdDeno"));
			obj.setHoldArea(st._getDotFormat(rs.getString("holdArea"),2));
			obj.setEditID(rs.getString("editID"));
			obj.setEditDate(rs.getString("editDate"));
			obj.setEditTime(rs.getString("editTime"));	
			obj.setDoorPlate1(rs.getString("doorPlate1"));
			obj.setDoorPlate2(rs.getString("doorPlate2"));
			obj.setDoorPlate3(rs.getString("doorPlate3"));
			obj.setDoorPlate4(rs.getString("doorPlate4"));
			obj.setArea(rs.getString("area"));
			obj.setHoldNume(rs.getString("holdNume"));
			obj.setHoldDeno(rs.getString("holdDeno"));
			obj.setHoldArea(rs.getString("holdArea"));
			obj.setCArea(rs.getString("CArea"));
			obj.setSArea(rs.getString("SArea"));
			obj.setBuildDate(rs.getString("buildDate"));
			obj.setStuff(rs.getString("stuff"));			
		}
		setStateQueryOneSuccess();	
	} catch (Exception e) {
		e.printStackTrace();
	} finally {
		db.closeAll();
	}
	return obj;
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
	int counter=0;
	try {
		if (!"".equals(getOrganID())) {		
			String sql=" select case a.proofVerify when 'Y' then '審核' else '未審核' end as proofVerify, a.enterOrg, a.ownership, a.propertyNo, a.serialNo, " +			
				"a.signNo, (case a.proofDoc when null then ' ' else a.proofDoc end) as proofDoc, a.area, a.holdNume, a.holdDeno " +
				" from UNTBU_Building a where a.dataState='1' and a.nonProof='Y' " +			
				" and a.enterOrg = " + Common.sqlChar(getOrganID()) ;				

			if (!"".equals(getQ_ownership()))
				sql+=" and ownership = " + Common.sqlChar(getQ_ownership()) ;
			if (!"".equals(getQ_proofVerify()))
				sql+=" and a.proofVerify = " + Common.sqlChar(getQ_proofVerify()) ;			
			if (!"".equals(getQ_signNo1()))
				q_signNo=getQ_signNo1().substring(0,1)+"______";
			if (!"".equals(getQ_signNo2()))
				q_signNo=getQ_signNo2().substring(0,3)+"____";			
			if (!"".equals(getQ_signNo3())){
				if (getQ_signNo3().length()==4){
					q_signNo="E__" + getQ_signNo3();
				}else{
					q_signNo=getQ_signNo3();
				}	
			}
			if (!"".equals(getQ_signNo4())){
				setQ_signNo4(Common.formatFrontZero(getQ_signNo4(),5));
				setQ_signNo5(Common.formatFrontZero(getQ_signNo5(),3));	
				if ("".equals(q_signNo)){
					q_signNo="_______"+getQ_signNo4()+getQ_signNo5();
				}else{
					q_signNo=q_signNo+getQ_signNo4()+getQ_signNo5();				
				}
			}				
			if (!"".equals(q_signNo))
				sql+=" and a.signNo like '" + q_signNo + "%'" ;
			
			sql+=" order by a.enterOrg, a.ownership, a.signNo ";			
			
			ResultSet rs = db.querySQL(sql);
			StringTools st = new StringTools();
			while (rs.next()){
				counter++;
				String rowArray[]=new String[9];	
				rowArray[0]=rs.getString("proofVerify"); 
				rowArray[1]=getSignDescName(rs.getString("signNo").substring(0,7)) + rs.getString("signNo").substring(7,12) + '-' + rs.getString("signNo").substring(12);
				rowArray[2]=rs.getString("proofDoc");
				rowArray[3]=st._getDotFormat(rs.getString("area"),2);			
				rowArray[4]=rs.getString("holdNume") + "/" + rs.getString("holdDeno");			
				rowArray[5]=rs.getString("enterOrg");
				rowArray[6]=rs.getString("ownership");
				rowArray[7]=rs.getString("propertyNo");
				rowArray[8]=rs.getString("serialNo");			
	
				objList.add(rowArray);
				/**
				if (counter==getListLimit()){
					setErrorMsg(getListLimitError());
					break;
				}
				**/
			}
			setStateQueryAllSuccess();
		} else {
			super.setState("logout");
		}		
	} catch (Exception e) {
		e.printStackTrace();
	} finally {
		db.closeAll();
	}
	return objList;
}	

/**
 * <br>
 * <br>目的：審核或取消審核建物權狀資料
 * <br>參數：1或0, 1為審核, 0為取消審核
 * <br>傳回：無
*/
public void approveAll(int intVerify)throws Exception{	
	Database db = new Database();
	String strVerify="N";
	String sMsg = "全部取消審核完成!";
	if (intVerify==1) {
		strVerify="Y";
		sMsg = "全部審核完成!";
	}	
	try {    		
	    int i = 0;
		String rowArray[]=new String[9];
		java.util.Iterator it= queryAll().iterator();
		String[] execSQLArray;
		String strSQL = "";
		while(it.hasNext()){
			rowArray= (String[])it.next();
			i++;
			enterOrg  = rowArray[5];
			ownership = rowArray[6];
			propertyNo= rowArray[7];
			serialNo  = rowArray[8];
			if (enterOrg.equals(getOrganID())) {			
				strSQL += "update UNTBU_Building set proofVerify='" + strVerify + "'" +
				", editID=" + Common.sqlChar(getEditID()) +
				", editDate=" + Common.sqlChar(Datetime.getYYYYMMDD()) +				
				" where 1=1 " +
				" and enterOrg = " + Common.sqlChar(enterOrg) +
				" and ownership = " + Common.sqlChar(ownership) +
				" and propertyNo = " + Common.sqlChar(propertyNo) +
				" and serialNo = " + Common.sqlChar(serialNo) +
				":;:";	
			}
		}
		if (i>0) {
			execSQLArray = strSQL.split(":;:");
			db.excuteSQL(execSQLArray);			
		}
		setStateQueryAllSuccess();
		setErrorMsg(sMsg);		
		super.setState("queryAll");			
	} catch (Exception e) {
		e.printStackTrace();
	} finally {
		db.closeAll();
	}		
}

/**
 * <br>
 * <br>目的：審核或取消審核建物權狀資料
 * <br>參數：1或0, 1為審核, 0為取消審核
 * <br>傳回：無
*/
public void approveOne(int intVerify)throws Exception{	
	Database db = new Database();
	String strVerify="N";
	String sMsg = "取消審核完成!";
	if (intVerify==1) {
		strVerify="Y";
		sMsg = "審核完成!";
	}	
	try {    					
			String[]execSQLArray = new String[1];
			execSQLArray[0] = "update UNTBU_Building set proofVerify='" + strVerify + "'" +
			", editID=" + Common.sqlChar(getEditID()) +
			", editDate=" + Common.sqlChar(Datetime.getYYYYMMDD()) +			
				" where 1=1 " +
				" and enterOrg = " + Common.sqlChar(enterOrg) +
				" and ownership = " + Common.sqlChar(ownership) +
				" and propertyNo = " + Common.sqlChar(propertyNo) +
				" and serialNo = " + Common.sqlChar(serialNo) +
				"";	
				db.excuteSQL(execSQLArray);			
			queryOne();
			setErrorMsg(sMsg);		
	} catch (Exception e) {
		e.printStackTrace();
	} finally {
		db.closeAll();
	}		
}

public void approveAllBak(int intVerify) throws Exception{	
	Database db = new Database();
	String sSQL = "";
	String[] sSQLs = null;
	String[] strKeys = null; 
	String strVerify="N";
	if (intVerify==1) {
		strVerify="Y";
	}	
	int i = 0;	
	int counter=0;
	try {
		for (i=0; i<getsKeySet().length; i++) {
			counter++;			
			strKeys = getsKeySet()[i].split(",");
			if (counter==1){
				sSQL = "update UNTBU_Building set proofVerify='" + strVerify + "'" +
				" where 1=1 " +
				" and enterOrg = " + Common.sqlChar(strKeys[0]) +
				" and ownership = " + Common.sqlChar(strKeys[1]) +
				" and propertyNo = " + Common.sqlChar(strKeys[2]) +
				" and serialNo = " + Common.sqlChar(strKeys[3]);				
			} else {		
				sSQL += ",update UNTBU_Building set proofVerify='" + strVerify + "'" +
				" where 1=1 " +
				" and enterOrg = " + Common.sqlChar(strKeys[0]) +
				" and ownership = " + Common.sqlChar(strKeys[1]) +
				" and propertyNo = " + Common.sqlChar(strKeys[2]) +
				" and serialNo = " + Common.sqlChar(strKeys[3]) +
				",";
			}			
			if (counter==getListLimit()){
				break;
			}			
			
		}
		if (counter>0) {
			sSQLs = sSQL.split(",");		
			db.excuteSQL(sSQLs);						
		}	
		setStateQueryAllSuccess();
		super.setState("queryAll");
	} catch (Exception e) {
		e.printStackTrace();
	} finally {
		db.closeAll();
	}
}

public String getLookupName(String sSQL) throws Exception {
	Database db = new Database();
	String sStr = "";
	try {
		ResultSet rs = db.querySQL(sSQL);
		while (rs.next()) {
			sStr = rs.getString(1);			
		}
	} catch (Exception e) {
		e.printStackTrace();
	} finally {
		db.closeAll();
	}			
	return sStr;
}


private String getSignDescName(String signNo){
	Database db = null;
	ResultSet rs = null;
	String sql = null;
	String result = null;
	try{
		sql = "select signdesc from SYSCA_SIGN where" +
				" signNo = " + Common.sqlChar(signNo);
		
		db = new Database();
		rs = db.querySQL(sql);
		if(rs.next()){				
			result = rs.getString("signdesc");
		}
		rs.close();
	}catch(Exception e){
		System.out.println("getSignDescName Exception => " + e.getMessage());
	}finally{
		db.closeAll();
	}
	return result;
}

}
