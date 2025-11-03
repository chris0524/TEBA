/*
*<br>程式目的：他項權利證明書審核作業
*<br>程式代號：untrt011f
*<br>程式日期：0941018
*<br>程式作者：clive.chang
*<br>--------------------------------------------------------
*<br>修改作者　　修改日期　　　修改目的
*<br>--------------------------------------------------------
*/

package unt.rt;

import java.sql.ResultSet;
import java.util.ArrayList;
import util.*;

public class UNTRT011F extends SuperBean{

String organID;
String proofVerify;
String enterOrg;
String enterOrgName;
String ownership;
String ownershipName;
String propertyNo;
String serialNo;
String propertyName;
String propertyName1;
String proofDoc1;
String holdNume1;
String holdDeno2;
String originalBV;
String setPeriod;
String rent;
String dataState;
String nonProof;

String q_proofVerify;
String q_enterOrg;
String q_ownership;
String q_propertyNo;
String q_propertyName;
String q_dataState;
String q_nonProof;

public String getOrganID(){ return checkGet(organID); }
public void setOrganID(String s){ organID=checkSet(s); }
public String getProofVerify(){ return checkGet(proofVerify); }
public void setProofVerify(String s){ proofVerify=checkSet(s); }
public String getEnterOrg(){ return checkGet(enterOrg); }
public void setEnterOrg(String s){ enterOrg=checkSet(s); }
public String getEnterOrgName(){ return checkGet(enterOrgName); }
public void setEnterOrgName(String s){ enterOrgName=checkSet(s); }
public String getOwnership(){ return checkGet(ownership); }
public void setOwnership(String s){ ownership=checkSet(s); }
public String getOwnershipName(){ return checkGet(ownershipName); }
public void setOwnershipName(String s){ ownershipName=checkSet(s); }
public String getPropertyNo(){ return checkGet(propertyNo); }
public void setPropertyNo(String s){ propertyNo=checkSet(s); }
public String getSerialNo(){ return checkGet(serialNo); }
public void setSerialNo(String s){ serialNo=checkSet(s); }
public String getPropertyName(){ return checkGet(propertyName); }
public void setPropertyName(String s){ propertyName=checkSet(s); }
public String getPropertyName1(){ return checkGet(propertyName1); }
public void setPropertyName1(String s){ propertyName1=checkSet(s); }
public String getProofDoc1(){ return checkGet(proofDoc1); }
public void setProofDoc1(String s){ proofDoc1=checkSet(s); }
public String getHoldNume1(){ return checkGet(holdNume1); }
public void setHoldNume1(String s){ holdNume1=checkSet(s); }
public String getHoldDeno2(){ return checkGet(holdDeno2); }
public void setHoldDeno2(String s){ holdDeno2=checkSet(s); }
public String getOriginalBV(){ return checkGet(originalBV); }
public void setOriginalBV(String s){ originalBV=checkSet(s); }
public String getSetPeriod(){ return checkGet(setPeriod); }
public void setSetPeriod(String s){ setPeriod=checkSet(s); }
public String getRent(){ return checkGet(rent); }
public void setRent(String s){ rent=checkSet(s); }
public String getDataState(){ return checkGet(dataState); }
public void setDataState(String s){ dataState=checkSet(s); }
public String getNonProof(){ return checkGet(nonProof); }
public void setNonProof(String s){ nonProof=checkSet(s); }

public String getQ_proofVerify(){ 	
	if (!"".equals(checkGet(q_proofVerify))) return checkGet(q_proofVerify);
	else return "N";   
}
public void setQ_proofVerify(String s){ q_proofVerify=checkSet(s); }
public String getQ_enterOrg(){ return checkGet(q_enterOrg); }
public void setQ_enterOrg(String s){ q_enterOrg=checkSet(s); }
public String getQ_ownership() {
	if (!"".equals(checkGet(q_ownership))) return checkGet(q_ownership);
	else return "1"; 
}
public void setQ_ownership(String s){ q_ownership=checkSet(s); }
public String getQ_propertyNo(){ return checkGet(q_propertyNo); }
public void setQ_propertyNo(String s){ q_propertyNo=checkSet(s); }
public String getQ_propertyName(){ return checkGet(q_propertyName); }
public void setQ_propertyName(String s){ q_propertyName=checkSet(s); }
public String getQ_dataState(){ return checkGet(q_dataState); }
public void setQ_dataState(String s){ q_dataState=checkSet(s); }


String isOrganManager;
String isAdminManager;
public String getIsOrganManager() { return checkGet(isOrganManager); }
public void setIsOrganManager(String s) { isOrganManager = checkSet(s); }
public String getIsAdminManager() { return checkGet(isAdminManager); }
public void setIsAdminManager(String s) { isAdminManager = checkSet(s); } 

/**
 * <br>
 * <br>目的：依主鍵查詢單一資料
 * <br>參數：無
 * <br>傳回：傳回本物件
*/

public UNTRT011F  queryOne() throws Exception{
	Database db = new Database();
	UNTRT011F obj = this;
	try {
		String sql=" select case a.proofVerify when 'Y' then '審核' else '未審核' end as proofVerify, a.enterOrg, b.organSName as enterOrgName, a.ownership, case a.ownership when '2' then '國有' else '市有' end as ownershipName, a.propertyNo, c.propertyName, a.serialNo, a.propertyName1, a.proofDoc1, a.holdNume1, a.holdDeno2, a.originalBV, a.setPeriod, a.rent, a.dataState, a.nonProof, a.editID, a.editDate, a.editTime  "+
			" from UNTRT_AddProof a, SYSCA_Organ b, SYSPK_PropertyCode c where 1=1 " +
			" and a.enterOrg=b.organID and c.propertyNo=a.propertyNo " +
			" and a.enterOrg = " + Common.sqlChar(enterOrg) +
			" and a.ownership = " + Common.sqlChar(ownership) +
			" and a.propertyNo = " + Common.sqlChar(propertyNo) +
			" and a.serialNo = " + Common.sqlChar(serialNo) +
			"";
		ResultSet rs = db.querySQL(sql);
		if (rs.next()){
			obj.setProofVerify(rs.getString("proofVerify"));
			obj.setEnterOrg(rs.getString("enterOrg"));
			obj.setEnterOrgName(rs.getString("enterOrgName"));
			obj.setOwnershipName(rs.getString("ownershipName"));
			obj.setOwnership(rs.getString("ownership"));
			obj.setPropertyNo(rs.getString("propertyNo"));
			obj.setSerialNo(rs.getString("serialNo"));
			obj.setPropertyName(rs.getString("propertyName"));
			obj.setPropertyName1(rs.getString("propertyName1"));
			obj.setProofDoc1(rs.getString("proofDoc1"));
			obj.setHoldNume1(rs.getString("holdNume1"));
			obj.setHoldDeno2(rs.getString("holdDeno2"));
			obj.setOriginalBV(Common.valueFormat(rs.getString("originalBV")));
			obj.setSetPeriod(rs.getString("setPeriod"));
			obj.setRent(Common.valueFormat(rs.getString("rent")));
			obj.setEditID(rs.getString("editID"));
			obj.setEditDate(rs.getString("editDate"));
			obj.setEditTime(rs.getString("editTime"));
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
			String sql=" select case a.proofVerify when 'Y' then '審核' else '未審核' end as proofVerify, a.enterOrg, a.ownership, a.propertyNo, a.serialNo, b.propertyName, a.proofDoc1, a.originalBV "+
			" from UNTRT_AddProof a, SYSPK_PropertyCode b where b.propertyNo=a.propertyNo and a.dataState='1' and nonProof='Y' ";			
			sql+=" and a.enterOrg = " + Common.sqlChar(getOrganID()) ;			
			if (!"".equals(getQ_ownership()))
				sql+=" and a.ownership = " + Common.sqlChar(getQ_ownership()) ;
			if (!"".equals(getQ_propertyNo()))
				sql+=" and a.propertyNo = " + Common.sqlChar(getQ_propertyNo()) ;
			if (!"".equals(getQ_proofVerify()))
				sql+=" and a.proofVerify = " + Common.sqlChar(getQ_proofVerify()) ;			
			ResultSet rs = db.querySQL(sql+" order by a.enterOrg, a.ownership, a.propertyNo, a.serialNo");
			while (rs.next()){
				counter++;
				String rowArray[]=new String[8];				
				rowArray[0]=rs.getString("proofVerify");
				rowArray[1]=rs.getString("propertyName");
				rowArray[2]=rs.getString("proofDoc1");
				rowArray[3]=Common.valueFormat(rs.getString("originalBV"));				
				rowArray[4]=rs.getString("enterOrg"); 
				rowArray[5]=rs.getString("ownership"); 
				rowArray[6]=rs.getString("propertyNo"); 
				rowArray[7]=rs.getString("serialNo"); 
				objList.add(rowArray);
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
		String rowArray[]=new String[8];
		java.util.Iterator it= queryAll().iterator();
		String[] execSQLArray;
		String strSQL = "";
		while(it.hasNext()){
			rowArray= (String[])it.next();
			i++;
			enterOrg  = rowArray[4];
			ownership = rowArray[5];
			propertyNo= rowArray[6];
			serialNo  = rowArray[7];
			if (enterOrg.equals(getOrganID())) {			
				strSQL += "update UNTRT_AddProof set proofVerify='" + strVerify + "'" +
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
		//super.setState("queryAll");			
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
			execSQLArray[0] = "update UNTRT_AddProof set proofVerify='" + strVerify + "'" +
			", editID=" + Common.sqlChar(getEditID()) +
			", editDate=" + Common.sqlChar(Datetime.getYYYMMDD()) +			
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

}

