/*
*<br>程式目的：非消耗品主檔資料維護－批號附屬設備
*<br>程式代號：untne034f
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

public class UNTNE034F_2 extends UNTNE001Q{


String enterOrg;
String ownership;
String propertyNo;
String lotNo;
String serialNo1;
String equipmentName;
String buyDate;
String equipmentUnit;
String equipmentAmount;
String unitPrice;
String totalValue;
String notes;
String sourceKind;
String nameplate;
String specification;
String differenceKind;

String q_enterOrg;
String q_ownership;
String q_propertyNo;
String q_lotNo;

public String getEnterOrg(){ return checkGet(enterOrg); }
public void setEnterOrg(String s){ enterOrg=checkSet(s); }
public String getOwnership(){ return checkGet(ownership); }
public void setOwnership(String s){ ownership=checkSet(s); }
public String getPropertyNo(){ return checkGet(propertyNo); }
public void setPropertyNo(String s){ propertyNo=checkSet(s); }
public String getLotNo(){ return checkGet(lotNo); }
public void setLotNo(String s){ lotNo=checkSet(s); }
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

String serialNo;
String serialNoS;
String serialNoE;
String serialNoUntneAttachment2;

public String getSerialNo(){ return checkGet(serialNo); }
public void setSerialNo(String s){ serialNo=checkSet(s); }
public String getSerialNoS(){ return checkGet(serialNoS); }
public void setSerialNoS(String s){ serialNoS=checkSet(s); }
public String getSerialNoE(){ return checkGet(serialNoE); }
public void setSerialNoE(String s){ serialNoE=checkSet(s); }
public String getSerialNoUntneAttachment2(){ return checkGet(serialNoUntneAttachment2); }
public void setSerialNoUntneAttachment2(String s){ serialNoUntneAttachment2=checkSet(s); }


//傳回執行insert前之檢查sql
protected String[][] getInsertCheckSQL(){

	//取得附屬設備次序
	Database db = new Database();
	ResultSet rs;	
	String sql="select max(CONVERT(int,serialNo1))+1 as serialNo1 from UNTNE_Attachment1 where 1=1 " +
				" and enterOrg = " + Common.sqlChar(enterOrg) + 
				" and ownership = " + Common.sqlChar(ownership) + 
				" and propertyNo = " + Common.sqlChar(propertyNo) + 
				" and lotNo = " + Common.sqlChar(lotNo) + 
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
 	checkSQLArray[0][0]=" select count(*) as checkResult from UNTNE_Attachment1 where 1=1 " + 
		" and enterOrg = " + Common.sqlChar(enterOrg) + 
		" and ownership = " + Common.sqlChar(ownership) + 
		" and propertyNo = " + Common.sqlChar(propertyNo) + 
		" and lotNo = " + Common.sqlChar(lotNo) + 
		" and serialNo1 = " + Common.sqlChar(serialNo1) + 
		" and differenceKind = " + Common.sqlChar(differenceKind) + 
		"";
	checkSQLArray[0][1]=">";
	checkSQLArray[0][2]="0";
	checkSQLArray[0][3]="該筆資料己重複，請重新輸入！";
	return checkSQLArray;
}

protected void getInsertSQLAttachment2(){
	UNTNE034F_2 obj = this;
	Database db = new Database();
	//ResultSet rs = null;
	//String sql = "";
	StringBuffer sbSQL = new StringBuffer("");
	//String strSQL = "";
	String[] execSQLArray;
	sbSQL.append("delete UNTNE_Attachment2 " ).append(
				" where 1=1 " ).append(
				" and enterOrg = " ).append( Common.sqlChar(enterOrg) ).append(
				" and ownership = " ).append( Common.sqlChar(ownership) ).append(
				" and propertyNo = " ).append( Common.sqlChar(propertyNo) ).append(
				" and lotNo = " ).append( Common.sqlChar(lotNo) ).append(
				" and differenceKind = " ).append( Common.sqlChar(differenceKind) ).append(
				" and serialNo1 = " ).append( Common.sqlChar(obj.getSerialNo1()) ).append(
				":;:");		
	int counter=0;
	int serialNoSAttachment2 = Integer.parseInt(serialNoS);
	int serialNoEAttachment2 = Integer.parseInt(serialNoE);
	counter = serialNoEAttachment2-serialNoSAttachment2+1;
	obj.setSerialNoUntneAttachment2(serialNoS);
	try {    
		while(counter > 0){
		    counter--;
		    sbSQL.append( " insert into UNTNE_Attachment2(" ).append(
					" enterOrg,").append(
					" ownership,").append(
					" propertyNo,").append(
					" lotNo,").append(
					" serialNo,").append(
					" serialNo1,").append(
					" equipmentName,").append(
					" buyDate,").append(
					" equipmentUnit,").append(
					" equipmentAmount,").append(
					" unitPrice,").append(
					" totalValue,").append(
					" dataState,").append(
					" notes,").append(
					" editID,").append(
					" editDate,").append(
					" editTime,").append(
					" differenceKind,").append(
					" sourceKind,").append(
					" nameplate,").append(
					" specification").append(
				" )Values(" ).append(
					Common.sqlChar(enterOrg) ).append( "," ).append(
					Common.sqlChar(ownership) ).append( "," ).append(
					Common.sqlChar(propertyNo) ).append( "," ).append(
					Common.sqlChar(lotNo) ).append( "," ).append(
					Common.sqlChar(serialNoUntneAttachment2) ).append( "," ).append(
					Common.sqlChar(obj.getSerialNo1()) ).append( "," ).append(
					Common.sqlChar(equipmentName) ).append( "," ).append(
					Common.sqlChar(buyDate) ).append( "," ).append(
					Common.sqlChar(equipmentUnit) ).append( "," ).append(
					Common.sqlChar(equipmentAmount) ).append( "," ).append(
					Common.sqlChar(unitPrice) ).append( "," ).append(
					Common.sqlChar(totalValue) ).append( "," ).append(
					Common.sqlChar("1") ).append( "," ).append(
					Common.sqlChar(notes) ).append( "," ).append(
					Common.sqlChar(getEditID()) ).append( "," ).append(
					Common.sqlChar(getEditDate()) ).append( "," ).append(
					Common.sqlChar(getEditTime()) ).append( "," ).append(
					Common.sqlChar(differenceKind) ).append( "," ).append(
					Common.sqlChar(sourceKind) ).append( "," ).append(
					Common.sqlChar(nameplate) ).append( "," ).append(
					Common.sqlChar(specification) ).append( ")" ).append(
					":;:");		
		    int changeSerialNo = Integer.parseInt(obj.getSerialNoUntneAttachment2())+1;
		    obj.setSerialNoUntneAttachment2(Common.formatFrontZero((changeSerialNo+""),7));
		}
		execSQLArray = sbSQL.toString().split(":;:");
		db.excuteSQL(execSQLArray);
	} catch (Exception e) {
		e.printStackTrace();
	} finally {
		db.closeAll();
	}		
}

//傳回insert sql
protected String[] getInsertSQL(){
	UNTNE034F_2 obj = this;
	getInsertSQLAttachment2();
	String[] execSQLArray = new String[1];
	execSQLArray[0]=" insert into UNTNE_Attachment1(" +
			" enterOrg,"+
			" ownership,"+
			" propertyNo,"+
			" lotNo,"+
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
			Common.sqlChar(obj.getSerialNo1()) + "," +
			Common.sqlChar(equipmentName) + "," +
			Common.sqlChar(buyDate) + "," +
			Common.sqlChar(equipmentUnit) + "," +
			Common.sqlChar(equipmentAmount) + "," +
			Common.sqlChar(unitPrice) + "," +
			Common.sqlChar(totalValue) + "," +
			Common.sqlChar(notes) + "," +
			Common.sqlChar(getEditID()) + "," +
			Common.sqlChar(getEditDate()) + "," +
			Common.sqlChar(getEditTime()) + "," +
			Common.sqlChar(differenceKind) + "," +
			Common.sqlChar(sourceKind) + "," +
			Common.sqlChar(nameplate) + "," +
			Common.sqlChar(specification) + ")" ;
	return execSQLArray;
}


//傳回update sql
protected String[] getUpdateSQL(){
	String[] execSQLArray = new String[2];
	execSQLArray[0]=" update UNTNE_Attachment1 set " +
			" equipmentName = " + Common.sqlChar(equipmentName) + "," +
			" buyDate = " + Common.sqlChar(buyDate) + "," +
			" equipmentUnit = " + Common.sqlChar(equipmentUnit) + "," +
			" equipmentAmount = " + Common.sqlChar(equipmentAmount) + "," +
			" unitPrice = " + Common.sqlChar(unitPrice) + "," +
			" totalValue = " + Common.sqlChar(totalValue) + "," +
			" notes = " + Common.sqlChar(notes) + "," +
			" differenceKind = " + Common.sqlChar(differenceKind) + "," +
			" sourceKind = " + Common.sqlChar(sourceKind) + "," +
			" nameplate = " + Common.sqlChar(nameplate) + "," +
			" specification = " + Common.sqlChar(specification) + "," +
			" editID = " + Common.sqlChar(getEditID()) + "," +
			" editDate = " + Common.sqlChar(getEditDate()) + "," +
			" editTime = " + Common.sqlChar(getEditTime()) + 
		" where 1=1 " + 
			" and enterOrg = " + Common.sqlChar(enterOrg) +
			" and ownership = " + Common.sqlChar(ownership) +
			" and propertyNo = " + Common.sqlChar(propertyNo) +
			" and lotNo = " + Common.sqlChar(lotNo) +
			" and serialNo1 = " + Common.sqlChar(serialNo1) +
			" and differenceKind = " + Common.sqlChar(differenceKind) +
			"";
	
	execSQLArray[1]=" update UNTNE_Attachment2 set " +
			" lotNo = " + Common.sqlChar(lotNo) + "," +
			" equipmentName = " + Common.sqlChar(equipmentName) + "," +
			" buyDate = " + Common.sqlChar(buyDate) + "," +
			" equipmentUnit = " + Common.sqlChar(equipmentUnit) + "," +
			" equipmentAmount = " + Common.sqlChar(equipmentAmount) + "," +
			" unitPrice = " + Common.sqlChar(unitPrice) + "," +
			" totalValue = " + Common.sqlChar(totalValue) + "," +
			" Notes = " + Common.sqlChar(notes) + "," +
			" differenceKind = " + Common.sqlChar(differenceKind) + "," +
			" sourceKind = " + Common.sqlChar(sourceKind) + "," +
			" nameplate = " + Common.sqlChar(nameplate) + "," +
			" specification = " + Common.sqlChar(specification) + "," +
			" editID = " + Common.sqlChar(getEditID()) + "," +
			" editDate = " + Common.sqlChar(getEditDate()) + "," +
			" editTime = " + Common.sqlChar(getEditTime()) + 
		" where 1=1 and dataState='1' " + 
			" and enterOrg = " + Common.sqlChar(enterOrg) +
			" and ownership = " + Common.sqlChar(ownership) +
			" and propertyNo = " + Common.sqlChar(propertyNo) +
			" and lotNo = " + Common.sqlChar(lotNo) +
			" and serialNo1 = " + Common.sqlChar(serialNo1) +
			" and differenceKind = " + Common.sqlChar(differenceKind) +
			"";	
	return execSQLArray;
}


//傳回delete sql
protected String[] getDeleteSQL(){
	String[] execSQLArray = new String[2];
	execSQLArray[0]=" delete UNTNE_Attachment1 where 1=1 " +
			" and enterOrg = " + Common.sqlChar(enterOrg) +
			" and ownership = " + Common.sqlChar(ownership) +
			" and propertyNo = " + Common.sqlChar(propertyNo) +
			" and lotNo = " + Common.sqlChar(lotNo) +
			" and serialNo1 = " + Common.sqlChar(serialNo1) +
			" and differenceKind = " + Common.sqlChar(differenceKind) +
			"";
	execSQLArray[1]=" delete UNTNE_Attachment2 where 1=1 and dataState='1' " +
			" and enterOrg = " + Common.sqlChar(enterOrg) +
			" and ownership = " + Common.sqlChar(ownership) +
			" and propertyNo = " + Common.sqlChar(propertyNo) +
			" and lotNo = " + Common.sqlChar(lotNo) +
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

public UNTNE034F_2  queryOne() throws Exception{
	UNTCH_Tools ul = new UNTCH_Tools();
	Database db = new Database();
	UNTNE034F_2 obj = this;
	try {
		String sql=" select a.enterOrg, a.ownership, a.propertyNo, a.lotNo, a.serialNo1, a.equipmentName, a.buyDate, a.equipmentUnit, a.equipmentAmount, a.unitPrice, a.totalValue, a.notes, a.editID, a.editDate, a.editTime,a.differenceKind,a.sourceKind,a.nameplate,a.specification  "+
			" from UNTNE_Attachment1 a where 1=1 " +
			" and a.enterOrg = " + Common.sqlChar(enterOrg) +
			" and a.ownership = " + Common.sqlChar(ownership) +
			" and a.propertyNo = " + Common.sqlChar(propertyNo) +
			" and a.lotNo = " + Common.sqlChar(lotNo) +
			" and a.serialNo1 = " + Common.sqlChar(serialNo1) +
			" and differenceKind = " + Common.sqlChar(differenceKind) +
			"";
		ResultSet rs = db.querySQL(sql);
		if (rs.next()){
			obj.setEnterOrg(rs.getString("enterOrg"));
			obj.setOwnership(rs.getString("ownership"));
			obj.setPropertyNo(rs.getString("propertyNo"));
			obj.setLotNo(rs.getString("lotNo"));
			obj.setSerialNo1(rs.getString("serialNo1"));
			obj.setEquipmentName(rs.getString("equipmentName"));
			obj.setBuyDate(ul._transToROC_Year(rs.getString("buyDate")));
			obj.setEquipmentUnit(rs.getString("equipmentUnit"));
			obj.setEquipmentAmount(rs.getString("equipmentAmount"));
			obj.setUnitPrice(rs.getString("unitPrice"));
			obj.setTotalValue(rs.getString("totalValue"));
			obj.setNotes(rs.getString("notes"));
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
		String sql=" select a.enterOrg, a.ownership, a.propertyNo, a.lotNo, a.serialNo1, a.equipmentName, a.equipmentAmount, a.unitPrice, a.totalValue, "+
		           " a.differenceKind,a.sourceKind,a.nameplate,a.specification "+	
		           " from UNTNE_Attachment1 a where 1=1 "; 
			if (!"".equals(getEnterOrg()))
				sql+=" and a.enterOrg = " + Common.sqlChar(getEnterOrg()) ;
			if (!"".equals(getOwnership()))
				sql+=" and a.ownership = " + Common.sqlChar(getOwnership()) ;
			if (!"".equals(getPropertyNo()))
				sql+=" and a.propertyNo = " + Common.sqlChar(getPropertyNo()) ;
			if (!"".equals(getLotNo()))
				sql+=" and a.lotNo = " + Common.sqlChar(getLotNo()) ;
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
			String rowArray[]=new String[10];
			rowArray[0]=rs.getString("enterOrg"); 
			rowArray[1]=rs.getString("ownership"); 
			rowArray[2]=rs.getString("propertyNo"); 
			rowArray[3]=rs.getString("lotNo"); 
			rowArray[4]=rs.getString("serialNo1"); 
			rowArray[5]=rs.getString("equipmentName"); 
			rowArray[6]=rs.getString("equipmentAmount"); 
			rowArray[7]=rs.getString("unitPrice"); 
			rowArray[8]=rs.getString("totalValue");
			rowArray[9]=rs.getString("differenceKind");
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


