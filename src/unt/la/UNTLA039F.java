/*
*<br>程式目的：土地所有權狀審核作業
*<br>程式代號：untla039f
*<br>程式日期：0940822
*<br>程式作者：clive.chang
*<br>--------------------------------------------------------
*<br>修改作者　　修改日期　　　修改目的
*<br>--------------------------------------------------------
*/

package unt.la;


import java.sql.ResultSet;
import java.util.ArrayList;
import util.*;
import TDlib_Simple.tools.src.StringTools;;

public class UNTLA039F extends SuperBean{
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
String field;
String fieldName;
String landRule;
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
String q_proofVerify;

String sSQL = "";
String strKeySet[] = null;
/**
 * <br>
 * <br>目的：依主鍵查詢單一資料
 * <br>參數：無
 * <br>傳回：傳回本物件
*/
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

public String getField() { return checkGet(field); }
public void setField(String s) { field=checkSet(s); }
public String getFieldName() { return checkGet(fieldName); }
public void setFieldName(String s) { fieldName=checkSet(s); }

public String getLandRule() { return checkGet(landRule); }
public void setLandRule(String s) { landRule=checkSet(s); }

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
public String getQ_proofVerify(){ return checkGet(q_proofVerify); }
public void setQ_proofVerify(String s){ q_proofVerify=checkSet(s); }

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


public UNTLA039F  queryOne() throws Exception{
	Database db = new Database();
	UNTLA039F obj = this;
	
	try {
		String sql="select a.proofVerify, case a.proofVerify when 'Y' then '審核' else '未審核' end as proofVerifyName, a.enterOrg, a.ownership, a.propertyNo, a.serialNo, a.propertyName1, a.signNo, a.ownershipDate, a.proofDoc, a.field, a.landRule, a.area, a.holdNume, a.holdDeno, a.holdArea, a.editID, a.editDate, a.editTime " +
			", case a.ownership when '2' then '國有' else '市有' end as ownershipName "+
			", a.signNo, c.propertyName " +			
			" from UNTLA_Land a, SYSCA_SIGN b, SYSPK_PropertyCode c " +
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
			obj.setSignName(getSignDescName(rs.getString("signNo").substring(0,7)) + rs.getString("signNo").substring(7,11) + '-' + rs.getString("signNo").substring(11));
			obj.setOwnerShipDate(rs.getString("ownershipDate"));
			obj.setProofDoc(rs.getString("proofDoc"));			
			obj.setField(rs.getString("field"));
			obj.setFieldName(getLookupName("select codeName from sysca_code where codekindid='FIE' and codeID='" + rs.getString("field") + "'"));
			obj.setLandRule(rs.getString("landRule"));
			obj.setArea(st._getDotFormat(rs.getString("area"),2));
			obj.setHoldNume(rs.getString("holdNume"));
			obj.setHoldDeno(rs.getString("holdDeno"));
			obj.setHoldArea(st._getDotFormat(rs.getString("holdArea"),2));
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
			String sql=" select case a.proofVerify when 'Y' then '審核' else '未審核' end as proofVerify, a.enterOrg, a.ownership, a.propertyNo, a.serialNo, " +			
				"a.signNo, a.proofDoc, a.area, a.holdNume, a.holdDeno, " +
				" a.signNo " +
				" from UNTLA_Land a where a.dataState='1' "; 			
				sql+=" and a.nonProof='Y' and a.enterOrg = " + Common.sqlChar(getOrganID()) ;				

			if (!"".equals(getQ_ownership()))
				sql+=" and a.ownership = " + Common.sqlChar(getQ_ownership()) ;
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
				setQ_signNo4(Common.formatFrontZero(getQ_signNo4(),4));
				setQ_signNo5(Common.formatFrontZero(getQ_signNo5(),4));	
				if ("".equals(q_signNo)){
					q_signNo="_______"+getQ_signNo4()+getQ_signNo5();
				}else{
					q_signNo=q_signNo+getQ_signNo4()+getQ_signNo5();				
				}
			}	

			if (!"".equals(q_signNo))
				sql+=" and a.signNo like '" + q_signNo + "%'" ;
			sql+=" order by a.enterOrg, a.ownership, a.signNo ";
			
			StringTools st = new StringTools();
			ResultSet rs = db.querySQL(sql);			
			while (rs.next()){
				counter++;
				String rowArray[]=new String[9];	
				rowArray[0]=rs.getString("proofVerify"); 
				rowArray[1]=getSignDescName(rs.getString("signNo").substring(0,7)) + rs.getString("signNo").substring(7,11) + '-' + rs.getString("signNo").substring(11);
				rowArray[2]=checkGet(rs.getString("proofDoc"));
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
 * <br>目的：審核或取消審核土地權狀資料
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
			execSQLArray[0] = "update UNTLA_Land set proofVerify='" + strVerify + "'" +
			", editID=" + Common.sqlChar(super.getEditID()) +
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

/**
 * <br>
 * <br>目的：審核或取消審核土地權狀資料
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
			strSQL += "update UNTLA_Land set proofVerify='" + strVerify + "'" +
			", editID=" + Common.sqlChar(getEditID()) +
			", editDate=" + Common.sqlChar(Datetime.getYYYMMDD()) +			
			" where 1=1 " +
			" and enterOrg = " + Common.sqlChar(enterOrg) +
			" and ownership = " + Common.sqlChar(ownership) +
			" and propertyNo = " + Common.sqlChar(propertyNo) +
			" and serialNo = " + Common.sqlChar(serialNo) +
			":;:";			
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
				sSQL = "update UNTLA_Land set proofVerify='" + strVerify + "'" +
				" where 1=1 " +
				" and enterOrg = " + Common.sqlChar(strKeys[0]) +
				" and ownership = " + Common.sqlChar(strKeys[1]) +
				" and propertyNo = " + Common.sqlChar(strKeys[2]) +
				" and serialNo = " + Common.sqlChar(strKeys[3]);				
			} else {		
				sSQL += ",update UNTLA_Land set proofVerify='" + strVerify + "'" +
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
