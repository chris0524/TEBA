/*
*<br>程式目的：非消耗品主檔資料維護－批號明細附屬設備
*<br>程式代號：untne004f
*<br>程式日期：0941102
*<br>程式作者：cherry
*<br>--------------------------------------------------------
*<br>修改作者　　修改日期　　　修改目的
*<br>--------------------------------------------------------
*/

package unt.ne;

import java.sql.ResultSet;
import java.util.ArrayList;

import unt.ch.UNTCH_Tools;
import util.*;

public class UNTNE004F_1 extends UNTNE001Q{


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
String sourceKind;
String nameplate;
String specification;
String differenceKind;

String q_enterOrg;
String q_ownership;
String q_propertyNo;
String q_lotNo;
String q_serialNo;

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
public String getSourceKind(){ return checkGet(sourceKind); }
public void setSourceKind(String s){ sourceKind=checkSet(s); }
public String getNameplate() {return checkGet(nameplate);}
public void setNameplate(String s) {this.nameplate = checkSet(s);}
public String getSpecification() {return checkGet(specification);}
public void setSpecification(String s) {this.specification = checkSet(s);}
public String getDifferenceKind() {return checkGet(this.differenceKind);}
public void setDifferenceKind(String s) {differenceKind = checkSet(s);}

public String getQ_enterOrg(){ return checkGet(q_enterOrg); }
public void setQ_enterOrg(String s){ q_enterOrg=checkSet(s); }
public String getQ_ownership(){ return checkGet(q_ownership); }
public void setQ_ownership(String s){ q_ownership=checkSet(s); }
public String getQ_propertyNo(){ return checkGet(q_propertyNo); }
public void setQ_propertyNo(String s){ q_propertyNo=checkSet(s); }
public String getQ_lotNo(){ return checkGet(q_lotNo); }
public void setQ_lotNo(String s){ q_lotNo=checkSet(s); }
public String getQ_serialNo(){ return checkGet(q_serialNo); }
public void setQ_serialNo(String s){ q_serialNo=checkSet(s); }

String checkVerify;
String checkClosing;
String serialNoS;
String serialNoE;

public String getCheckVerify(){ return checkGet(checkVerify); }
public void setCheckVerify(String s){ checkVerify=checkSet(s); }
public String getCheckClosing(){ return checkGet(checkClosing); }
public void setCheckClosing(String s){ checkClosing=checkSet(s); }
public String getSerialNoS(){ return checkGet(serialNoS); }
public void setSerialNoS(String s){ serialNoS=checkSet(s); }
public String getSerialNoE(){ return checkGet(serialNoE); }
public void setSerialNoE(String s){ serialNoE=checkSet(s); }

//傳回執行insert前之檢查sql
protected String[][] getInsertCheckSQL(){

	//取得附屬設備次序
	Database db = new Database();
	ResultSet rs;	
	String sql="select max(CAST(serialNo1 as int))+1 as serialNo1 from UNTNE_Attachment2 where 1=1 " +
				" and enterOrg = " + Common.sqlChar(enterOrg) + 
				" and ownership = " + Common.sqlChar(ownership) + 
				" and propertyNo = " + Common.sqlChar(propertyNo) + 
				" and serialNo = " + Common.sqlChar(serialNo) +
				" and differenceKind = " + Common.sqlChar(differenceKind) + 
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
 	checkSQLArray[0][0]=" select count(*) as checkResult from UNTNE_Attachment2 where 1=1 " + 
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
	UNTCH_Tools ul = new UNTCH_Tools();
	UNTNE004F_1 obj = this ;
	String[] execSQLArray = new String[1];
	execSQLArray[0]=" insert into UNTNE_Attachment2(" +
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
			" editTime,"+
			" differenceKind,"+
			" sourceKind,"+
			" nameplate,"+
			" specification"+
		" )Values(" +
			Common.sqlChar(enterOrg) + "," +
			Common.sqlChar(ownership) + "," +
			Common.sqlChar(propertyNo) + "," +
			Common.sqlChar(lotNo) + "," +
			Common.sqlChar(serialNo) + "," +
			Common.sqlChar(obj.getSerialNo1()) + "," +
			Common.sqlChar(equipmentName) + "," +
			Common.sqlChar(ul._transToCE_Year(buyDate)) + "," +
			Common.sqlChar(equipmentUnit) + "," +
			Common.sqlChar(equipmentAmount) + "," +
			Common.sqlChar(unitPrice) + "," +
			Common.sqlChar(totalValue) + "," +
			Common.sqlChar(dataState) + "," +
			Common.sqlChar(Notes) + "," +
			Common.sqlChar(getEditID()) + "," +
			Common.sqlChar(ul._transToCE_Year(getEditDate())) + "," +
			Common.sqlChar(getEditTime()) + "," +
			Common.sqlChar(differenceKind) + "," +
			Common.sqlChar(sourceKind) + "," +
			Common.sqlChar(nameplate) + "," +
			Common.sqlChar(specification) + ")" ;
	return execSQLArray;
}


//傳回update sql
protected String[] getUpdateSQL(){
	UNTCH_Tools ul = new UNTCH_Tools();
	String[] execSQLArray = new String[1];
	execSQLArray[0]=" update UNTNE_Attachment2 set " +
			" lotNo = " + Common.sqlChar(lotNo) + "," +
			" equipmentName = " + Common.sqlChar(equipmentName) + "," +
			" buyDate = " + Common.sqlChar(ul._transToCE_Year(buyDate)) + "," +
			" equipmentUnit = " + Common.sqlChar(equipmentUnit) + "," +
			" equipmentAmount = " + Common.sqlChar(equipmentAmount) + "," +
			" unitPrice = " + Common.sqlChar(unitPrice) + "," +
			" totalValue = " + Common.sqlChar(totalValue) + "," +
			" dataState = " + Common.sqlChar(dataState) + "," +
			" Notes = " + Common.sqlChar(Notes) + "," +
			" differenceKind = " + Common.sqlChar(differenceKind) + "," +
			" sourceKind = " + Common.sqlChar(sourceKind) + "," +
			" nameplate = " + Common.sqlChar(nameplate) + "," +
			" specification = " + Common.sqlChar(specification) + "," +
			" editID = " + Common.sqlChar(getEditID()) + "," +
			" editDate = " + Common.sqlChar(ul._transToCE_Year(getEditDate())) + "," +
			" editTime = " + Common.sqlChar(getEditTime()) + 
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
	execSQLArray[0]=" delete UNTNE_Attachment2 where 1=1 " +
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

public UNTNE004F_1  queryOne() throws Exception{
	UNTCH_Tools ul = new UNTCH_Tools();
	Database db = new Database();
	UNTNE004F_1 obj = this;
	try {
		String sql=" select a.enterOrg, a.ownership, a.propertyNo, a.lotNo, a.serialNo, a.serialNo1, a.equipmentName, a.buyDate, a.equipmentUnit, a.equipmentAmount, a.unitPrice, a.totalValue, a.dataState, a.Notes, a.editID, a.editDate, a.editTime,a.differenceKind,a.sourceKind,a.nameplate,a.specification  "+
			" from UNTNE_Attachment2 a where 1=1 " +
			" and a.enterOrg = " + Common.sqlChar(enterOrg) +
			" and a.ownership = " + Common.sqlChar(ownership) +
			" and a.propertyNo = " + Common.sqlChar(propertyNo) +
			" and a.serialNo = " + Common.sqlChar(serialNo) +
			" and a.serialNo1 = " + Common.sqlChar(serialNo1) +
			" and differenceKind = " + Common.sqlChar(differenceKind) +
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
			obj.setBuyDate(ul._transToROC_Year(rs.getString("buyDate")));
			obj.setEquipmentUnit(rs.getString("equipmentUnit"));
			obj.setEquipmentAmount(rs.getString("equipmentAmount"));
			obj.setUnitPrice(rs.getString("unitPrice"));
			obj.setTotalValue(rs.getString("totalValue"));
			obj.setDataState(rs.getString("dataState"));
			obj.setNotes(rs.getString("Notes"));
			obj.setEditID(rs.getString("editID"));
			obj.setEditDate(ul._transToROC_Year(rs.getString("editDate")));
			obj.setEditTime(rs.getString("editTime"));
			obj.setDifferenceKind(rs.getString("differenceKind"));
			obj.setSourceKind(rs.getString("sourceKind"));
			obj.setNameplate(rs.getString("nameplate"));
			obj.setSpecification(rs.getString("specification"));
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
		String sql=" select a.enterOrg, a.ownership, a.propertyNo, a.serialNo, a.serialNo1, a.equipmentName, a.equipmentAmount, a.unitPrice, a.totalValue, (case a.dataState when '1' then '現存' when '2' then '已減損' else '' end) dataState," +
				   " a.differenceKind,a.sourceKind,a.nameplate,a.specification "+
				   " from UNTNE_Attachment2 a where 1=1 "; 
			if (!"".equals(getEnterOrg()))
				sql+=" and a.enterOrg = " + Common.sqlChar(getEnterOrg()) ;
			if (!"".equals(getOwnership()))
				sql+=" and a.ownership = " + Common.sqlChar(getOwnership()) ;
			if (!"".equals(getPropertyNo()))
				sql+=" and a.propertyNo = " + Common.sqlChar(getPropertyNo()) ;
			if (!"".equals(getLotNo()))
				sql+=" and a.lotNo = " + Common.sqlChar(getLotNo()) ;
			if (!"".equals(getSerialNo()))
				sql+=" and a.serialNo = " + Common.sqlChar(getSerialNo()) ;
			if (!"".equals(getDifferenceKind()))
				sql+=" and a.differenceKind = " + Common.sqlChar(getDifferenceKind()) ;
			ResultSet rs = db.querySQL(sql.toString(),true);
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
			rowArray[5]=rs.getString("equipmentName"); 
			rowArray[6]=rs.getString("equipmentAmount"); 
			rowArray[7]=rs.getString("unitPrice"); 
			rowArray[8]=rs.getString("totalValue"); 
			rowArray[9]=rs.getString("dataState");
			rowArray[10]=rs.getString("differenceKind");
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


