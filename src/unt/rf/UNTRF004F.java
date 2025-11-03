/*
*<br>程式目的：土地改良主檔資料維護--附屬設備
*<br>程式代號：untbu004f
*<br>程式日期：0940923
*<br>程式作者：clive.chang
*<br>--------------------------------------------------------
*<br>修改作者　　修改日期　　　修改目的
*<br>--------------------------------------------------------
*/

package unt.rf;

import java.sql.ResultSet;
import java.util.ArrayList;
import util.*;

public class UNTRF004F extends UNTRF001F_Q{


String enterOrg;
String ownership;
String propertyNo;
String serialNo;
String serialNo1;
String equipmentName;
String buyDate;
String equipmentUnit;
String equipmentAmount;
String unitPrice;
String totalValue;
String notes;


public String getEnterOrg(){ return checkGet(enterOrg); }
public void setEnterOrg(String s){ enterOrg=checkSet(s); }
public String getOwnership(){ return checkGet(ownership); }
public void setOwnership(String s){ ownership=checkSet(s); }
public String getPropertyNo(){ return checkGet(propertyNo); }
public void setPropertyNo(String s){ propertyNo=checkSet(s); }
public String getSerialNo(){ return checkGet(serialNo); }
public void setSerialNo(String s){ serialNo=checkSet(s); }
public String getSerialNo1(){ return checkGet(serialNo1); }
public void setSerialNo1(String s){ serialNo1=checkSet(s); }
public String getEquipmentName(){ return checkGet(equipmentName); }
public void setEquipmentName(String s){ equipmentName=checkSet(s); }
public String getBuyDate(){ return checkGet(buyDate); }
public void setBuyDate(String s){ buyDate=checkSet(s); }
public String getEquipmentUnit(){ return checkGet(equipmentUnit); }
public void setEquipmentUnit(String s){ equipmentUnit=checkSet(s); }
public String getEquipmentAmount(){ return checkGet(equipmentAmount); }
public void setEquipmentAmount(String s){ equipmentAmount=checkSet(s); }
public String getUnitPrice(){ return checkGet(unitPrice); }
public void setUnitPrice(String s){ unitPrice=checkSet(s); }
public String getTotalValue(){ return checkGet(totalValue); }
public void setTotalValue(String s){ totalValue=checkSet(s); }
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
	//取得自動編號
	Database db = new Database();
	ResultSet rs;	
	String sql="select max(serialNo1)+1 as serialNo1 from UNTRF_Attachment2 " +
		" where enterOrg = " + Common.sqlChar(enterOrg) +
		" and ownership = " + Common.sqlChar(ownership) +
		" and propertyNo = " + Common.sqlChar(propertyNo)+
		" and serialNo = " + Common.sqlChar(serialNo);	
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
 	checkSQLArray[0][0]=" select count(*) as checkResult from UNTRF_Attachment2 where 1=1 " + 
		" and enterOrg = " + Common.sqlChar(enterOrg) + 
		" and ownership = " + Common.sqlChar(ownership) + 
		" and propertyNo = " + Common.sqlChar(propertyNo) + 
		" and serialNo = " + Common.sqlChar(serialNo) + 
		" and serialNo1 = " + Common.sqlChar(serialNo1) + 
		"";
	checkSQLArray[0][1]=">";
	checkSQLArray[0][2]="0";
	checkSQLArray[0][3]="該筆資料己重複，請重新輸入！";
	return checkSQLArray;
}


//傳回insert sql
protected String[] getInsertSQL(){
	String[] execSQLArray = new String[1];
	execSQLArray[0]=" insert into UNTRF_Attachment2(" +
			" enterOrg,"+
			" ownership,"+
			" propertyNo,"+
			" serialNo,"+
			" serialNo1,"+
			" equipmentName,"+
			" buyDate,"+
			" equipmentUnit,"+
			" equipmentAmount,"+
			" unitPrice,"+
			" totalValue,"+
			" notes,"+
			" editID,"+
			" editDate,"+
			" editTime"+
		" )Values(" +
			Common.sqlChar(enterOrg) + "," +
			Common.sqlChar(ownership) + "," +
			Common.sqlChar(propertyNo) + "," +
			Common.sqlChar(serialNo) + "," +
			Common.sqlChar(serialNo1) + "," +
			Common.sqlChar(equipmentName) + "," +
			Common.sqlChar(buyDate) + "," +
			Common.sqlChar(equipmentUnit) + "," +
			Common.sqlChar(equipmentAmount) + "," +
			Common.sqlChar(unitPrice) + "," +
			Common.sqlChar(totalValue) + "," +
			Common.sqlChar(notes) + "," +
			Common.sqlChar(getEditID()) + "," +
			Common.sqlChar(getEditDate()) + "," +
			Common.sqlChar(getEditTime()) + ")" ;
	return execSQLArray;
}


//傳回update sql
protected String[] getUpdateSQL(){
	String[] execSQLArray = new String[1];
	execSQLArray[0]=" update UNTRF_Attachment2 set " +
			" equipmentName = " + Common.sqlChar(equipmentName) + "," +
			" buyDate = " + Common.sqlChar(buyDate) + "," +
			" equipmentUnit = " + Common.sqlChar(equipmentUnit) + "," +
			" equipmentAmount = " + Common.sqlChar(equipmentAmount) + "," +
			" unitPrice = " + Common.sqlChar(unitPrice) + "," +
			" totalValue = " + Common.sqlChar(totalValue) + "," +
			" notes = " + Common.sqlChar(notes) + "," +
			" editID = " + Common.sqlChar(getEditID()) + "," +
			" editDate = " + Common.sqlChar(getEditDate()) + "," +
			" editTime = " + Common.sqlChar(getEditTime()) + 
		" where 1=1 " + 
			" and enterOrg = " + Common.sqlChar(enterOrg) +
			" and ownership = " + Common.sqlChar(ownership) +
			" and propertyNo = " + Common.sqlChar(propertyNo) +
			" and serialNo = " + Common.sqlChar(serialNo) +
			" and serialNo1 = " + Common.sqlChar(serialNo1) +
			"";
	return execSQLArray;
}


//傳回delete sql
protected String[] getDeleteSQL(){
	String[] execSQLArray = new String[1];
	execSQLArray[0]=" delete UNTRF_Attachment2 where 1=1 " +
			" and enterOrg = " + Common.sqlChar(enterOrg) +
			" and ownership = " + Common.sqlChar(ownership) +
			" and propertyNo = " + Common.sqlChar(propertyNo) +
			" and serialNo = " + Common.sqlChar(serialNo) +
			" and serialNo1 = " + Common.sqlChar(serialNo1) +
			"";
	return execSQLArray;
}


/**
 * <br>
 * <br>目的：依主鍵查詢單一資料
 * <br>參數：無
 * <br>傳回：傳回本物件
*/

public UNTRF004F  queryOne() throws Exception{
	Database db = new Database();
	UNTRF004F obj = this;
	try {
		String sql=" select a.enterOrg, a.ownership, a.propertyNo, a.serialNo, a.serialNo1, a.equipmentName, a.buyDate, a.equipmentUnit, a.equipmentAmount, a.unitPrice, a.totalValue, a.notes, a.editID, a.editDate, a.editTime  "+
			" from UNTRF_Attachment2 a where 1=1 " +
			" and a.enterOrg = " + Common.sqlChar(enterOrg) +
			" and a.ownership = " + Common.sqlChar(ownership) +
			" and a.propertyNo = " + Common.sqlChar(propertyNo) +
			" and a.serialNo = " + Common.sqlChar(serialNo) +
			" and a.serialNo1 = " + Common.sqlChar(serialNo1) +
			"";
		ResultSet rs = db.querySQL(sql);
		if (rs.next()){
			obj.setEnterOrg(rs.getString("enterOrg"));
			obj.setOwnership(rs.getString("ownership"));
			obj.setPropertyNo(rs.getString("propertyNo"));
			obj.setSerialNo(rs.getString("serialNo"));
			obj.setSerialNo1(rs.getString("serialNo1"));
			obj.setEquipmentName(rs.getString("equipmentName"));
			obj.setBuyDate(rs.getString("buyDate"));
			obj.setEquipmentUnit(rs.getString("equipmentUnit"));
			obj.setEquipmentAmount(rs.getString("equipmentAmount"));
			obj.setUnitPrice(rs.getString("unitPrice"));
			obj.setTotalValue(rs.getString("totalValue"));
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
		String sql=" select a.enterOrg, a.ownership, a.propertyNo, a.serialNo, a.serialNo1, a.equipmentName, a.equipmentAmount, a.unitPrice, a.totalValue "+
			" from UNTRF_Attachment2 a where 1=1 "; 
			if (!"".equals(getEnterOrg()))
				sql+=" and a.enterOrg = " + Common.sqlChar(getEnterOrg()) ;
			if (!"".equals(getOwnership()))
				sql+=" and a.ownership = " + Common.sqlChar(getOwnership()) ;
			if (!"".equals(getPropertyNo()))
				sql+=" and a.propertyNo = " + Common.sqlChar(getPropertyNo()) ;
			if (!"".equals(getSerialNo()))
				sql+=" and a.serialNo = " + Common.sqlChar(getSerialNo()) ;		
		ResultSet rs = db.querySQL(sql);
		while (rs.next()){
			counter++;
			String rowArray[]=new String[9];
			rowArray[0]=rs.getString("enterOrg"); 
			rowArray[1]=rs.getString("ownership"); 
			rowArray[2]=rs.getString("propertyNo"); 
			rowArray[3]=rs.getString("serialNo"); 
			rowArray[4]=rs.getString("serialNo1"); 
			rowArray[5]=rs.getString("equipmentName"); 
			rowArray[6]=rs.getString("equipmentAmount"); 
			rowArray[7]=rs.getString("unitPrice"); 
			rowArray[8]=rs.getString("totalValue"); 
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


