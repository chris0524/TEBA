/*
*<br>程式目的：建物主檔資料維護--稅藉資料
*<br>程式代號：untbu010f
*<br>程式日期：0940919
*<br>程式作者：clive.chang
*<br>--------------------------------------------------------
*<br>修改作者　　修改日期　　　修改目的
*<br>--------------------------------------------------------
*/

package unt.bu;

import java.sql.ResultSet;
import java.util.ArrayList;
import util.*;

public class UNTBU010F extends UNTBU001Q{


String enterOrg;
String ownership;
String propertyNo;
String serialNo;
String taxYear;
String taxKind;
String taxAmount;
String taxPrice;
String taxState;
String notes;


public String getEnterOrg(){ return checkGet(enterOrg); }
public void setEnterOrg(String s){ enterOrg=checkSet(s); }
public String getOwnership(){ return checkGet(ownership); }
public void setOwnership(String s){ ownership=checkSet(s); }
public String getPropertyNo(){ return checkGet(propertyNo); }
public void setPropertyNo(String s){ propertyNo=checkSet(s); }
public String getSerialNo(){ return checkGet(serialNo); }
public void setSerialNo(String s){ serialNo=checkSet(s); }
public String getTaxYear(){ return checkGet(taxYear); }
public void setTaxYear(String s){ taxYear=checkSet(s); }
public String getTaxKind(){ return checkGet(taxKind); }
public void setTaxKind(String s){ taxKind=checkSet(s); }
public String getTaxAmount(){ return checkGet(taxAmount); }
public void setTaxAmount(String s){ taxAmount=checkSet(s); }
public String getTaxPrice(){ return checkGet(taxPrice); }
public void setTaxPrice(String s){ taxPrice=checkSet(s); }
public String getTaxState(){ return checkGet(taxState); }
public void setTaxState(String s){ taxState=checkSet(s); }
public String getNotes(){ return checkGet(notes); }
public void setNotes(String s){ notes=checkSet(s); }


String q_enterOrg;
String q_enterOrgName;
String q_ownership;
String q_propertyNo;
String q_serialNo;
String q_caseNoS;
String q_dataState;

public String getQ_enterOrg(){ return checkGet(q_enterOrg); }
public void setQ_enterOrg(String s){ q_enterOrg=checkSet(s); }
public String getQ_enterOrgName(){ return checkGet(q_enterOrgName); }
public void setQ_enterOrgName(String s){ q_enterOrgName=checkSet(s); }
public String getQ_ownership(){ return checkGet(q_ownership); }
public void setQ_ownership(String s){ q_ownership=checkSet(s); }
public String getQ_propertyNo(){ return checkGet(q_propertyNo); }
public void setQ_propertyNo(String s){ q_propertyNo=checkSet(s); }
public String getQ_serialNo(){ return checkGet(q_serialNo); }
public void setQ_serialNo(String s){ q_serialNo=checkSet(s); }
public String getQ_caseNoS(){ return checkGet(q_caseNoS); }
public void setQ_caseNoS(String s){ q_caseNoS=checkSet(s); }
public String getQ_dataState(){ return checkGet(q_dataState); }
public void setQ_dataState(String s){ q_dataState=checkSet(s); }


//傳回執行insert前之檢查sql
protected String[][] getInsertCheckSQL(){
	String[][] checkSQLArray = new String[1][4];
 	checkSQLArray[0][0]=" select count(*) as checkResult from UNTBU_Tax where 1=1 " + 
		" and enterOrg = " + Common.sqlChar(enterOrg) + 
		" and ownership = " + Common.sqlChar(ownership) + 
		" and propertyNo = " + Common.sqlChar(propertyNo) + 
		" and serialNo = " + Common.sqlChar(serialNo) + 
		" and taxYear = " + Common.sqlChar(taxYear) + 
		"";
	checkSQLArray[0][1]=">";
	checkSQLArray[0][2]="0";
	checkSQLArray[0][3]="該筆資料己重複，請重新輸入！";
	return checkSQLArray;
}


//傳回insert sql
protected String[] getInsertSQL(){
	String[] execSQLArray = new String[1];
	execSQLArray[0]=" insert into UNTBU_Tax(" +
			" enterOrg,"+
			" ownership,"+
			" propertyNo,"+
			" serialNo,"+
			" taxYear,"+
			" taxKind,"+
			" taxAmount,"+
			" taxPrice,"+
			" taxState,"+
			" notes,"+
			" editID,"+
			" editDate,"+
			" editTime"+
		" )Values(" +
			Common.sqlChar(enterOrg) + "," +
			Common.sqlChar(ownership) + "," +
			Common.sqlChar(propertyNo) + "," +
			Common.sqlChar(serialNo) + "," +
			Common.sqlChar(taxYear) + "," +
			Common.sqlChar(taxKind) + "," +
			Common.sqlChar(taxAmount) + "," +
			Common.sqlChar(taxPrice) + "," +
			Common.sqlChar(taxState) + "," +
			Common.sqlChar(notes) + "," +
			Common.sqlChar(getEditID()) + "," +
			Common.sqlChar(getEditDate()) + "," +
			Common.sqlChar(getEditTime()) + ")" ;
	return execSQLArray;
}


//傳回update sql
protected String[] getUpdateSQL(){
	String[] execSQLArray = new String[1];
	execSQLArray[0]=" update UNTBU_Tax set " +
			" taxKind = " + Common.sqlChar(taxKind) + "," +
			" taxAmount = " + Common.sqlChar(taxAmount) + "," +
			" taxPrice = " + Common.sqlChar(taxPrice) + "," +
			" taxState = " + Common.sqlChar(taxState) + "," +
			" notes = " + Common.sqlChar(notes) + "," +
			" editID = " + Common.sqlChar(getEditID()) + "," +
			" editDate = " + Common.sqlChar(getEditDate()) + "," +
			" editTime = " + Common.sqlChar(getEditTime()) + 
		" where 1=1 " + 
			" and enterOrg = " + Common.sqlChar(enterOrg) +
			" and ownership = " + Common.sqlChar(ownership) +
			" and propertyNo = " + Common.sqlChar(propertyNo) +
			" and serialNo = " + Common.sqlChar(serialNo) +
			" and taxYear = " + Common.sqlChar(taxYear) +
			"";
	return execSQLArray;
}


//傳回delete sql
protected String[] getDeleteSQL(){
	String[] execSQLArray = new String[1];
	execSQLArray[0]=" delete UNTBU_Tax where 1=1 " +
			" and enterOrg = " + Common.sqlChar(enterOrg) +
			" and ownership = " + Common.sqlChar(ownership) +
			" and propertyNo = " + Common.sqlChar(propertyNo) +
			" and serialNo = " + Common.sqlChar(serialNo) +
			" and taxYear = " + Common.sqlChar(taxYear) +
			"";
	return execSQLArray;
}


/**
 * <br>
 * <br>目的：依主鍵查詢單一資料
 * <br>參數：無
 * <br>傳回：傳回本物件
*/

public UNTBU010F  queryOne() throws Exception{
	Database db = new Database();
	UNTBU010F obj = this;
	try {
		String sql=" select a.enterOrg, a.ownership, a.propertyNo, a.serialNo, a.taxYear, a.taxKind, a.taxAmount, a.taxPrice, a.taxState, a.notes, a.editID, a.editDate, a.editTime  "+
			" from UNTBU_Tax a where 1=1 " +
			" and a.enterOrg = " + Common.sqlChar(enterOrg) +
			" and a.ownership = " + Common.sqlChar(ownership) +
			" and a.propertyNo = " + Common.sqlChar(propertyNo) +
			" and a.serialNo = " + Common.sqlChar(serialNo) +
			" and a.taxYear = " + Common.sqlChar(taxYear) +
			"";
		ResultSet rs = db.querySQL(sql);
		if (rs.next()){
			obj.setEnterOrg(rs.getString("enterOrg"));
			obj.setOwnership(rs.getString("ownership"));
			obj.setPropertyNo(rs.getString("propertyNo"));
			obj.setSerialNo(rs.getString("serialNo"));
			obj.setTaxYear(rs.getString("taxYear"));
			obj.setTaxKind(rs.getString("taxKind"));
			obj.setTaxAmount(rs.getString("taxAmount"));
			obj.setTaxPrice(rs.getString("taxPrice"));
			obj.setTaxState(rs.getString("taxState"));
			obj.setNotes(rs.getString("notes"));
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
		String sql=" select a.enterOrg, a.ownership, a.propertyNo, a.serialNo, a.taxYear, a.taxKind, b.codeName as taxKindName, a.taxAmount, a.taxPrice, a.taxState "+
			" from UNTBU_Tax a left join SYSCA_Code b on a.taxKind=b.codeID and b.codeKindID='TKD' where 1=1 " +
			"";
			if (!"".equals(getEnterOrg()))
				sql+=" and a.enterOrg = " + Common.sqlChar(getEnterOrg()) ;
			if (!"".equals(getOwnership()))
				sql+=" and a.ownership = " + Common.sqlChar(getOwnership()) ;
			if (!"".equals(getPropertyNo()))
				sql+=" and a.propertyNo = " + Common.sqlChar(getPropertyNo()) ;
			if (!"".equals(getSerialNo()))
				sql+=" and a.serialNo = " + Common.sqlChar(getSerialNo()) ;			
			
		ResultSet rs = db.querySQL(sql + " order by taxYear ");
		while (rs.next()){
			counter++;
			String rowArray[]=new String[9];
			rowArray[0]=rs.getString("enterOrg"); 
			rowArray[1]=rs.getString("ownership"); 
			rowArray[2]=rs.getString("propertyNo"); 
			rowArray[3]=rs.getString("serialNo"); 
			rowArray[4]=rs.getString("taxYear"); 
			rowArray[5]=rs.getString("taxKindName"); 
			rowArray[6]=rs.getString("taxAmount"); 
			rowArray[7]=rs.getString("taxPrice"); 
			rowArray[8]=rs.getString("taxState"); 
			objList.add(rowArray);
			if (counter==getListLimit()){
				setErrorMsg(getListLimitError());
				break;
			}
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


