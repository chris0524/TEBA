/*
*<br>程式目的：建物主檔資料維護--管理資料
*<br>程式代號：untrf026f
*<br>程式日期：0940918
*<br>程式作者：clive.chang
*<br>--------------------------------------------------------
*<br>修改作者　　修改日期　　　修改目的
*<br>--------------------------------------------------------
*/

package unt.rf;

import java.sql.ResultSet;
import java.util.ArrayList;

import util.Common;
import unt.ch.UNTCH_Tools;
import util.*;

public class UNTRF026F extends UNTRF001F_Q{
	
String enterOrg;
String enterOrgName;
String ownership;
String propertyNo;
String caseNo;
String serialNo;
String serialNo1;
String useUnit;
String useUnitName;
String useUnit1;
String useRelation;
String useDateS;
String useDateE;
//String useArea;
String notes1;
String approveYN;
String caseKind;
String caseNo1;
String Notes;
String measure ;

String importDate;

public String getImportDate(){ return checkGet(importDate); }
public void setImportDate(String s){ importDate=checkSet(s); }
public String getCaseNo(){ return checkGet(caseNo); }
public void setCaseNo(String s){ caseNo=checkSet(s); }
public String getEnterOrg(){ return checkGet(enterOrg); }
public void setEnterOrg(String s){ enterOrg=checkSet(s); }
public String getEnterOrgName(){ return checkGet(enterOrgName); }
public void setEnterOrgName(String s){ enterOrgName=checkSet(s); }
public String getOwnership(){ return checkGet(ownership); }
public void setOwnership(String s){ ownership=checkSet(s); }
public String getPropertyNo(){ return checkGet(propertyNo); }
public void setPropertyNo(String s){ propertyNo=checkSet(s); }
public String getSerialNo(){ return checkGet(serialNo); }
public void setSerialNo(String s){ serialNo=checkSet(s); }
public String getSerialNo1(){ return checkGet(serialNo1); }
public void setSerialNo1(String s){ serialNo1=checkSet(s); }
public String getUseUnit(){ return checkGet(useUnit); }
public void setUseUnit(String s){ useUnit=checkSet(s); }
public String getUseUnitName(){ return checkGet(useUnitName); }
public void setUseUnitName(String s){ useUnitName=checkSet(s); }
public String getUseUnit1(){ return checkGet(useUnit1); }
public void setUseUnit1(String s){ useUnit1=checkSet(s); }
public String getUseRelation(){ return checkGet(useRelation); }
public void setUseRelation(String s){ useRelation=checkSet(s); }
public String getUseDateS(){ return checkGet(useDateS); }
public void setUseDateS(String s){ useDateS=checkSet(s); }
public String getUseDateE(){ return checkGet(useDateE); }
public void setUseDateE(String s){ useDateE=checkSet(s); }
//public String getUseArea(){ return checkGet(useArea); }
//public void setUseArea(String s){ useArea=checkSet(s); }
public String getNotes1(){ return checkGet(notes1); }
public void setNotes1(String s){ notes1=checkSet(s); }
public String getApproveYN(){ return checkGet(approveYN); }
public void setApproveYN(String s){ approveYN=checkSet(s); }
public String getCaseKind(){ return checkGet(caseKind); }
public void setCaseKind(String s){ caseKind=checkSet(s); }
public String getCaseNo1(){ return checkGet(caseNo1); }
public void setCaseNo1(String s){ caseNo1=checkSet(s); }
public String getNotes(){ return checkGet(Notes); }
public void setNotes(String s){ Notes=checkSet(s); }
public String getMeasure(){ return checkGet(measure); }
public void setMeasure(String s){ measure=checkSet(s); }

String differenceKind;
public String getDifferenceKind() {return checkGet(differenceKind);}
public void setDifferenceKind(String differenceKind) {this.differenceKind = checkSet(differenceKind);}

//String q_enterOrg;
//String q_enterOrgName;
//String q_ownership;
//String q_propertyNo;
//String q_serialNo;
//String q_caseNoS;
//String q_dataState;
//
//public String getQ_enterOrg(){ return checkGet(q_enterOrg); }
//public void setQ_enterOrg(String s){ q_enterOrg=checkSet(s); }
//public String getQ_enterOrgName(){ return checkGet(q_enterOrgName); }
//public void setQ_enterOrgName(String s){ q_enterOrgName=checkSet(s); }
//public String getQ_ownership(){ return checkGet(q_ownership); }
//public void setQ_ownership(String s){ q_ownership=checkSet(s); }
//public String getQ_propertyNo(){ return checkGet(q_propertyNo); }
//public void setQ_propertyNo(String s){ q_propertyNo=checkSet(s); }
//public String getQ_serialNo(){ return checkGet(q_serialNo); }
//public void setQ_serialNo(String s){ q_serialNo=checkSet(s); }
//public String getQ_caseNoS(){ return checkGet(q_caseNoS); }
//public void setQ_caseNoS(String s){ q_caseNoS=checkSet(s); }
//public String getQ_dataState(){ return checkGet(q_dataState); }
//public void setQ_dataState(String s){ q_dataState=checkSet(s); }

//傳回執行insert前之檢查sql
protected String[][] getInsertCheckSQL(){
	//取得自動編號
	Database db = new Database();
	ResultSet rs;	
	String sql="select max(serialNo1)+1 as serialNo1 from UNTRF_Manage " +
		" where enterOrg = " + Common.sqlChar(enterOrg) +
		" and ownership = " + Common.sqlChar(ownership) +
		" and propertyNo = " + Common.sqlChar(propertyNo)+
		" and serialNo = " + Common.sqlChar(serialNo) +
		" and differenceKind = " + Common.sqlChar(differenceKind);	
	try {		
		rs = db.querySQL(sql);
		if (rs.next()){
			if (rs.getString("serialNo1")==null)
				setSerialNo1("1");
			else
				setSerialNo1(rs.getString("serialNo1"));
		}
	} catch (Exception e) {
		e.printStackTrace();
	} finally {
		db.closeAll();
	}		
	String[][] checkSQLArray = new String[1][4];
 	checkSQLArray[0][0]=" select count(*) as checkResult from UNTRF_Manage where 1=1 " + 
		" and enterOrg = " + Common.sqlChar(enterOrg) + 
		" and ownership = " + Common.sqlChar(ownership) + 
		" and propertyNo = " + Common.sqlChar(propertyNo) + 
		" and serialNo = " + Common.sqlChar(serialNo) + 
		" and serialNo1 = " + Common.sqlChar(serialNo1) + 
		" and differenceKind = " + Common.sqlChar(differenceKind) +
		"";
	checkSQLArray[0][1]=">";
	checkSQLArray[0][2]="0";
	checkSQLArray[0][3]="該筆資料己重複，請重新輸入！";
	return checkSQLArray;
}


//傳回insert sql
protected String[] getInsertSQL(){
	String[] execSQLArray = new String[1];
	
	execSQLArray[0]=" insert into UNTRF_Manage(" +
			" enterOrg,"+
			" ownership,"+
			" propertyNo,"+
			" serialNo,"+
			" serialNo1,"+
			" useUnit,"+
			" useUnit1,"+
			" useRelation,"+
			" useDateS,"+
			" useDateE,"+
			" measure,"+
			" notes1,"+
			" Notes,"+
			" editID,"+
			" editDate,"+
			" editTime,"+
			" differenceKind"+
		" )Values(" +
			Common.sqlChar(enterOrg) + "," +
			Common.sqlChar(ownership) + "," +
			Common.sqlChar(propertyNo) + "," +
			Common.sqlChar(serialNo) + "," +
			Common.sqlChar(serialNo1) + "," +
			Common.sqlChar(useUnit) + "," +
			Common.sqlChar(useUnit1) + "," +
			Common.sqlChar(useRelation) + "," +
			Common.sqlChar(new UNTCH_Tools()._transToCE_Year(useDateS)) + "," +
			Common.sqlChar(new UNTCH_Tools()._transToCE_Year(useDateE)) + "," +
			Common.sqlChar(measure) + "," +
			Common.sqlChar(notes1) + "," +
			Common.sqlChar(Notes) + "," +
			Common.sqlChar(getEditID()) + "," +
			Common.sqlChar(new UNTCH_Tools()._transToCE_Year(getEditDate())) + "," +
			Common.sqlChar(getEditTime()) + "," +
			Common.sqlChar(getDifferenceKind()) + ")" ;	
	

	return execSQLArray;
}


//傳回update sql
protected String[] getUpdateSQL(){
	String[] execSQLArray = new String[1];
	
	execSQLArray[0]=" update UNTRF_Manage set " +
			" useUnit = " + Common.sqlChar(useUnit) + "," +
			" useUnit1 = " + Common.sqlChar(useUnit1) + "," +
			" useRelation = " + Common.sqlChar(useRelation) + "," +
			" useDateS = " + Common.sqlChar(new UNTCH_Tools()._transToCE_Year(useDateS)) + "," +
			" useDateE = " + Common.sqlChar(new UNTCH_Tools()._transToCE_Year(useDateE)) + "," +
			" measure = " + Common.sqlChar(measure) + "," +
			" notes1 = " + Common.sqlChar(notes1) + "," +
			" Notes = " + Common.sqlChar(Notes) + "," +
			" editID = " + Common.sqlChar(getEditID()) + "," +
			" editDate = " + Common.sqlChar(new UNTCH_Tools()._transToCE_Year(getEditDate())) + "," +
			" editTime = " + Common.sqlChar(getEditTime()) + "," +
			" differenceKind = " + Common.sqlChar(getDifferenceKind()) + 
		" where 1=1 " + 
			" and enterOrg = " + Common.sqlChar(enterOrg) +
			" and ownership = " + Common.sqlChar(ownership) +
			" and propertyNo = " + Common.sqlChar(propertyNo) +
			" and serialNo = " + Common.sqlChar(serialNo) +
			" and serialNo1 = " + Common.sqlChar(serialNo1) +
			" and differenceKind = " + Common.sqlChar(differenceKind) +
			"";

	return execSQLArray;
}


//傳回delete sql
protected String[] getDeleteSQL(){
	String[] execSQLArray = new String[1];
	execSQLArray[0]=" delete UNTRF_Manage where 1=1 " +
			" and enterOrg = " + Common.sqlChar(enterOrg) +
			" and ownership = " + Common.sqlChar(ownership) +
			" and propertyNo = " + Common.sqlChar(propertyNo) +
			" and serialNo = " + Common.sqlChar(serialNo) +
			" and serialNo1 = " + Common.sqlChar(serialNo1) +
			" and differenceKind = " + Common.sqlChar(differenceKind) +
			"";
	return execSQLArray;
}

/**
 * <br>
 * <br>目的：依主鍵查詢單一資料
 * <br>參數：無
 * <br>傳回：傳回本物件
*/

public UNTRF026F  queryOne() throws Exception{
	Database db = new Database();
	UNTRF026F obj = this;
	try {
		String sql=" select a.enterOrg, a.ownership, a.propertyNo, a.serialNo, a.serialNo1, " +
			" a.useUnit, b.organAName as useUnitName, a.useUnit1, a.useRelation, a.useDateS, a.useDateE, " +
			" a.notes1, a.measure, a.Notes, a.differenceKind, " +
			" a.editID, a.editDate, a.editTime  "+		
			" from UNTRF_Manage a left join SYSCA_Organ b on a.useUnit=b.organID where 1=1 " +
			" and a.enterOrg = " + Common.sqlChar(enterOrg) +
			" and a.ownership = " + Common.sqlChar(ownership) +
			" and a.propertyNo = " + Common.sqlChar(propertyNo) +
			" and a.serialNo = " + Common.sqlChar(serialNo) +
			" and a.serialNo1 = " + Common.sqlChar(serialNo1) +
			" and a.differenceKind = " + Common.sqlChar(differenceKind) +
			"";
		
		ResultSet rs = db.querySQL(sql);
		if (rs.next()){
			obj.setEnterOrg(rs.getString("enterOrg"));
			obj.setOwnership(rs.getString("ownership"));
			obj.setPropertyNo(rs.getString("propertyNo"));
			obj.setSerialNo(rs.getString("serialNo"));
			obj.setSerialNo1(rs.getString("serialNo1"));
			obj.setUseUnit(rs.getString("useUnit"));
			obj.setUseUnitName(rs.getString("useUnitName"));			
			obj.setUseUnit1(rs.getString("useUnit1"));
			obj.setUseRelation(rs.getString("useRelation"));
			obj.setUseDateS(new UNTCH_Tools()._transToROC_Year(rs.getString("useDateS")));
			obj.setUseDateE(new UNTCH_Tools()._transToROC_Year(rs.getString("useDateE")));
			obj.setMeasure(rs.getString("measure"));
			obj.setNotes1(rs.getString("notes1"));
			obj.setNotes(rs.getString("Notes"));
			obj.setEditID(rs.getString("editID"));
			obj.setEditDate(new UNTCH_Tools()._transToROC_Year(rs.getString("editDate")));
			obj.setEditTime(rs.getString("editTime"));
			obj.setDifferenceKind(rs.getString("differenceKind"));
			
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
	
	try {
		String sql=" select a.enterOrg, b.organSName as useUnitName, a.ownership, " +
			" (select x.codeName from sysca_code x where a.ownership = x.codeid and x.codekindid = 'OWA') as ownershipName, " +
			" a.propertyNo, a.serialNo, a.serialNo1, a.useUnit, a.useUnit1, a.useRelation," +
			" (select x.codeName from sysca_code x where a.useRelation = x.codeid and x.codekindid = 'URE') as useRelationName," +
			" a.useDateS, a.useDateE, a.measure" +
			" from UNTRF_Manage a left join SYSCA_Organ b on a.useUnit=b.organID where 1=1 " +
			"";
		
			if (!"".equals(getEnterOrg()))
				sql+=" and a.enterOrg = " + Common.sqlChar(getEnterOrg()) ;
			if (!"".equals(getOwnership()))
				sql+=" and a.ownership = " + Common.sqlChar(getOwnership()) ;
			if (!"".equals(getPropertyNo()))
				sql+=" and a.propertyNo = " + Common.sqlChar(getPropertyNo()) ;
			if (!"".equals(getSerialNo()))
				sql+=" and a.serialNo = " + Common.sqlChar(getSerialNo()) ;

			UNTCH_Tools ul = new UNTCH_Tools();
		ResultSet rs = db.querySQL(sql + " order by serialNo1",true);
		processCurrentPageAttribute(rs);			
		if (getTotalRecord() > 0) {
			int count = getRecordStart();
			int end = getRecordEnd();
			do {
				if (count > end)
					break;
			String rowArray[]=new String[11];
			rowArray[0]=rs.getString("enterOrg"); 
			rowArray[1]=rs.getString("ownership"); 
			rowArray[2]=rs.getString("propertyNo"); 
			rowArray[3]=rs.getString("serialNo"); 
			rowArray[4]=rs.getString("serialNo1"); 
			rowArray[5]=rs.getString("useUnitName"); 
			rowArray[6]=rs.getString("useUnit1"); 
			rowArray[7]=rs.getString("useRelationName"); 
			rowArray[8]=ul._transToCE_Year(rs.getString("useDateS")); 
			rowArray[9]=ul._transToCE_Year(rs.getString("useDateE")); 
			rowArray[10]=rs.getString("measure"); 
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

}