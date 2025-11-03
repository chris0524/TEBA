/*
*<br>程式目的：動產增減值作業－明細資料附屬設備
*<br>程式代號：untmp036f
*<br>程式日期：0941228
*<br>程式作者：cherry
*<br>--------------------------------------------------------
*<br>修改作者　　修改日期　　　修改目的
*<br>--------------------------------------------------------
*/

package unt.mp;

import java.sql.ResultSet;
import java.util.ArrayList;
import util.*;

public class UNTMP036F extends UNTMP025Q{


String enterOrg;
String ownership;
String propertyNo;
String lotNo;
String serialNo;
String serialNo1;
String equipmentName;
String buyDate;
String equipmentUnit;
String equipmentAmount;
String unitPrice;
String totalValue;
String dataState;
String Notes;

String q_enterOrg;
String q_ownership;
String q_propertyNo;
String q_lotNo;
String q_serialNoS;
String q_serialNoE;

public String getEnterOrg(){ return checkGet(enterOrg); }
public void setEnterOrg(String s){ enterOrg=checkSet(s); }
public String getOwnership(){ return checkGet(ownership); }
public void setOwnership(String s){ ownership=checkSet(s); }
public String getPropertyNo(){ return checkGet(propertyNo); }
public void setPropertyNo(String s){ propertyNo=checkSet(s); }
public String getLotNo(){ return checkGet(lotNo); }
public void setLotNo(String s){ lotNo=checkSet(s); }
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
public String getDataState(){ return checkGet(dataState); }
public void setDataState(String s){ dataState=checkSet(s); }
public String getNotes(){ return checkGet(Notes); }
public void setNotes(String s){ Notes=checkSet(s); }

public String getQ_enterOrg(){ return checkGet(q_enterOrg); }
public void setQ_enterOrg(String s){ q_enterOrg=checkSet(s); }
public String getQ_ownership(){ return checkGet(q_ownership); }
public void setQ_ownership(String s){ q_ownership=checkSet(s); }
public String getQ_propertyNo(){ return checkGet(q_propertyNo); }
public void setQ_propertyNo(String s){ q_propertyNo=checkSet(s); }
public String getQ_lotNo(){ return checkGet(q_lotNo); }
public void setQ_lotNo(String s){ q_lotNo=checkSet(s); }
public String getQ_serialNoS(){ return checkGet(q_serialNoS); }
public void setQ_serialNoS(String s){ q_serialNoS=checkSet(s); }
public String getQ_serialNoE(){ return checkGet(q_serialNoE); }
public void setQ_serialNoE(String s){ q_serialNoE=checkSet(s); }

String checkVerify;
String checkClosing;
String checkDataState;
String verify;
String closing;
String serialNoS;
String serialNoE;
String caseNo;

public String getCheckVerify(){ return checkGet(checkVerify); }
public void setCheckVerify(String s){ checkVerify=checkSet(s); }
public String getCheckClosing(){ return checkGet(checkClosing); }
public void setCheckClosing(String s){ checkClosing=checkSet(s); }
public String getCheckDataState(){ return checkGet(checkDataState); }
public void setCheckDataState(String s){ checkDataState=checkSet(s); }
public String getVerify(){ return checkGet(verify); }
public void setVerify(String s){ verify=checkSet(s); }
public String getClosing(){ return checkGet(closing); }
public void setClosing(String s){ closing=checkSet(s); }
public String getSerialNoS(){ return checkGet(serialNoS); }
public void setSerialNoS(String s){ serialNoS=checkSet(s); }
public String getSerialNoE(){ return checkGet(serialNoE); }
public void setSerialNoE(String s){ serialNoE=checkSet(s); }
public String getCaseNo(){ return checkGet(caseNo); }
public void setCaseNo(String s){ caseNo=checkSet(s); }

//傳回執行insert前之檢查sql
protected String[][] getInsertCheckSQL(){
	//取得附屬設備次序
	Database db = new Database();
	ResultSet rs;	
	String sql="select max(TO_NUMBER(serialNo1))+1 as serialNo1 from UNTMP_Attachment2 where 1=1 " +
				" and enterOrg = " + Common.sqlChar(enterOrg) + 
				" and ownership = " + Common.sqlChar(ownership) + 
				" and propertyNo = " + Common.sqlChar(propertyNo) + 
				" and serialNo = " + Common.sqlChar(serialNo) + 
				"";
	try {		
		rs = db.querySQL(sql);
		if (rs.next()){
			if (rs.getString("serialNo1")==null)
				setSerialNo1("001");
			else
				setSerialNo1(Common.formatFrontZero(rs.getString("serialNo1"), 3));
		} else {
			setSerialNo1("001");
		}
	} catch (Exception e) {
		e.printStackTrace();
	} finally {
		db.closeAll();
	}		
		
	String[][] checkSQLArray = new String[1][4];
 	checkSQLArray[0][0]=" select count(*) as checkResult from UNTMP_Attachment2 where 1=1 " + 
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
	UNTMP036F obj = this;
	String[] execSQLArray = new String[1];
	execSQLArray[0]=" insert into UNTMP_Attachment2(" +
			" enterOrg,"+
			" ownership,"+
			" propertyNo,"+
			" lotNo,"+
			" serialNo,"+
			" serialNo1,"+
			" equipmentName,"+
			" buyDate,"+
			" equipmentUnit,"+
			" equipmentAmount,"+
			" unitPrice,"+
			" totalValue,"+
			" dataState,"+
			" Notes,"+
			" editID,"+
			" editDate,"+
			" editTime"+
		" )Values(" +
			Common.sqlChar(enterOrg) + "," +
			Common.sqlChar(ownership) + "," +
			Common.sqlChar(propertyNo) + "," +
			Common.sqlChar(lotNo) + "," +
			Common.sqlChar(serialNo) + "," +
			Common.sqlChar(obj.getSerialNo1()) + "," +
			Common.sqlChar(equipmentName) + "," +
			Common.sqlChar(buyDate) + "," +
			Common.sqlChar(equipmentUnit) + "," +
			Common.sqlChar(equipmentAmount) + "," +
			Common.sqlChar(unitPrice) + "," +
			Common.sqlChar(totalValue) + "," +
			Common.sqlChar(dataState) + "," +
			Common.sqlChar(Notes) + "," +
			Common.sqlChar(getEditID()) + "," +
			Common.sqlChar(getEditDate()) + "," +
			Common.sqlChar(getEditTime()) + ")" ;
	return execSQLArray;
}


//傳回update sql
protected String[] getUpdateSQL(){
	String[] execSQLArray = new String[1];
	execSQLArray[0]=" update UNTMP_Attachment2 set " +
			" lotNo = " + Common.sqlChar(lotNo) + "," +
			" equipmentName = " + Common.sqlChar(equipmentName) + "," +
			" buyDate = " + Common.sqlChar(buyDate) + "," +
			" equipmentUnit = " + Common.sqlChar(equipmentUnit) + "," +
			" equipmentAmount = " + Common.sqlChar(equipmentAmount) + "," +
			" unitPrice = " + Common.sqlChar(unitPrice) + "," +
			" totalValue = " + Common.sqlChar(totalValue) + "," +
			" dataState = " + Common.sqlChar(dataState) + "," +
			" Notes = " + Common.sqlChar(Notes) + "," +
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
	execSQLArray[0]=" delete UNTMP_Attachment2 where 1=1 " +
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

public UNTMP036F  queryOne() throws Exception{
	Database db = new Database();
	UNTMP036F obj = this;
	try {
		String sql=" select a.enterOrg, a.ownership, a.propertyNo, a.lotNo, a.serialNo, a.serialNo1, a.equipmentName, a.buyDate, a.equipmentUnit, a.equipmentAmount, a.unitPrice, a.totalValue, a.dataState, a.Notes, a.editID, a.editDate, a.editTime  "+
			" from UNTMP_Attachment2 a where 1=1 " +
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
			obj.setLotNo(rs.getString("lotNo"));
			obj.setSerialNo(rs.getString("serialNo"));
			obj.setSerialNo1(rs.getString("serialNo1"));
			obj.setEquipmentName(rs.getString("equipmentName"));
			obj.setBuyDate(rs.getString("buyDate"));
			obj.setEquipmentUnit(rs.getString("equipmentUnit"));
			obj.setEquipmentAmount(rs.getString("equipmentAmount"));
			obj.setUnitPrice(rs.getString("unitPrice"));
			obj.setTotalValue(rs.getString("totalValue"));
			obj.setDataState(rs.getString("dataState"));
			obj.setNotes(rs.getString("Notes"));
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
		String sql=" select a.enterOrg, a.ownership, a.propertyNo, a.serialNo, a.serialNo1, a.equipmentName, a.equipmentAmount, a.unitPrice, a.totalValue, decode(a.dataState,'1','現存','2','已減損','') dataState "+
					" from UNTMP_Attachment2 a where 1=1 " ;
//			if (!"".equals(getEnterOrg()))
				sql+=" and a.enterOrg = " + Common.sqlChar(getEnterOrg()) ;
//			if (!"".equals(getOwnership()))
				sql+=" and a.ownership = " + Common.sqlChar(getOwnership()) ;
//			if (!"".equals(getPropertyNo()))
				sql+=" and a.propertyNo = " + Common.sqlChar(getPropertyNo()) ;
//			if (!"".equals(getLotNo()))
				sql+=" and a.lotNo = " + Common.sqlChar(getLotNo()) ;
//			if (!"".equals(getSerialNo()))
				sql+=" and a.serialNo = " + Common.sqlChar(getSerialNo()) ;
		ResultSet rs = db.querySQL(sql);
		while (rs.next()){
			counter++;
			String rowArray[]=new String[10];
			rowArray[0]=rs.getString("enterOrg"); 
			rowArray[1]=rs.getString("ownership"); 
			rowArray[2]=rs.getString("propertyNo"); 
			rowArray[3]=rs.getString("serialNo"); 
			rowArray[4]=rs.getString("serialNo1"); 
			rowArray[5]=rs.getString("equipmentName"); 
			rowArray[6]=rs.getString("equipmentAmount"); 
			rowArray[7]=rs.getString("unitPrice"); 
			rowArray[8]=rs.getString("totalValue"); 
			rowArray[9]=rs.getString("dataState"); 
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


