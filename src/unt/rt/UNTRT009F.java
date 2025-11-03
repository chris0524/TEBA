/*
*<br>程式目的：權利增減值作業－增減值單明細資料
*<br>程式代號：untrt009f
*<br>程式日期：0941011
*<br>程式作者：cherry
*<br>--------------------------------------------------------
*<br>修改作者　　修改日期　　　修改目的
*<br>--------------------------------------------------------
*/

package unt.rt;

import java.sql.ResultSet;
import java.util.ArrayList;
import util.*;

public class UNTRT009F extends UNTRT008Q{


String enterOrg;
String enterOrgName;
String ownership;
String caseNo;
String propertyNo;
String serialNo;
String serialNo1;
String oldSerialNo1;
String signNo;
String signName;
String setObligor;
String area;
String holdNume;
String holdDeno;
String holdArea;
String setArea;
String oldBookValue;
String adjustType;
String adjustBookValue;
String newBookValue;
String notes;

String q_enterOrg;
String q_enterOrgName;
String q_ownership;
String q_caseNo;
String q_propertyNo;
String q_serialNo;
String q_serialNo1;
String q_signNo;
String q_signName;

String initDtl;
String verify;
String adjustProofAdjustType;
String adjustProofAdjustBookValue;
String adjustProofAdjustBookValue1;
String adjustProofAdjustBookValue2;
String adjustProofNewBookValue;

public String getVerify(){ return checkGet(verify); }
public void setVerify(String s){ verify=checkSet(s); }
public String getInitDtl(){ return checkGet(initDtl); }
public void setInitDtl(String s){ initDtl=checkSet(s); }
public String getAdjustProofAdjustType(){ return checkGet(adjustProofAdjustType); }
public void setAdjustProofAdjustType(String s){ adjustProofAdjustType=checkSet(s); }
public String getAdjustProofAdjustBookValue(){ return checkGet(adjustProofAdjustBookValue); }
public void setAdjustProofAdjustBookValue(String s){ adjustProofAdjustBookValue=checkSet(s); }
public String getAdjustProofAdjustBookValue1(){ return checkGet(adjustProofAdjustBookValue1); }
public void setAdjustProofAdjustBookValue1(String s){ adjustProofAdjustBookValue1=checkSet(s); }
public String getAdjustProofAdjustBookValue2(){ return checkGet(adjustProofAdjustBookValue2); }
public void setAdjustProofAdjustBookValue2(String s){ adjustProofAdjustBookValue2=checkSet(s); }
public String getAdjustProofNewBookValue(){ return checkGet(adjustProofNewBookValue); }
public void setAdjustProofNewBookValue(String s){ adjustProofNewBookValue=checkSet(s); }

public String getEnterOrg(){ return checkGet(enterOrg); }
public void setEnterOrg(String s){ enterOrg=checkSet(s); }
public String getEnterOrgName(){ return checkGet(enterOrgName); }
public void setEnterOrgName(String s){ enterOrgName=checkSet(s); }
public String getOwnership(){ return checkGet(ownership); }
public void setOwnership(String s){ ownership=checkSet(s); }
public String getCaseNo(){ return checkGet(caseNo); }
public void setCaseNo(String s){ caseNo=checkSet(s); }
public String getPropertyNo(){ return checkGet(propertyNo); }
public void setPropertyNo(String s){ propertyNo=checkSet(s); }
public String getSerialNo(){ return checkGet(serialNo); }
public void setSerialNo(String s){ serialNo=checkSet(s); }
public String getSerialNo1(){ return checkGet(serialNo1); }
public void setSerialNo1(String s){ serialNo1=checkSet(s); }
public String getOldSerialNo1(){ return checkGet(oldSerialNo1); }
public void setOldSerialNo1(String s){ oldSerialNo1=checkSet(s); }
public String getSignNo(){ return checkGet(signNo); }
public void setSignNo(String s){ signNo=checkSet(s); }
public String getSignName(){ return checkGet(signName); }
public void setSignName(String s){ signName=checkSet(s); }
public String getSetObligor(){ return checkGet(setObligor); }
public void setSetObligor(String s){ setObligor=checkSet(s); }
public String getArea(){ return checkGet(area); }
public void setArea(String s){ area=checkSet(s); }
public String getHoldNume(){ return checkGet(holdNume); }
public void setHoldNume(String s){ holdNume=checkSet(s); }
public String getHoldDeno(){ return checkGet(holdDeno); }
public void setHoldDeno(String s){ holdDeno=checkSet(s); }
public String getHoldArea(){ return checkGet(holdArea); }
public void setHoldArea(String s){ holdArea=checkSet(s); }
public String getSetArea(){ return checkGet(setArea); }
public void setSetArea(String s){ setArea=checkSet(s); }
public String getOldBookValue(){ return checkGet(oldBookValue); }
public void setOldBookValue(String s){ oldBookValue=checkSet(s); }
public String getAdjustType(){ return checkGet(adjustType); }
public void setAdjustType(String s){ adjustType=checkSet(s); }
public String getAdjustBookValue(){ return checkGet(adjustBookValue); }
public void setAdjustBookValue(String s){ adjustBookValue=checkSet(s); }
public String getNewBookValue(){ return checkGet(newBookValue); }
public void setNewBookValue(String s){ newBookValue=checkSet(s); }
public String getNotes(){ return checkGet(notes); }
public void setNotes(String s){ notes=checkSet(s); }

public String getQ_enterOrg(){ return checkGet(q_enterOrg); }
public void setQ_enterOrg(String s){ q_enterOrg=checkSet(s); }
public String getQ_enterOrgName(){ return checkGet(q_enterOrgName); }
public void setQ_enterOrgName(String s){ q_enterOrgName=checkSet(s); }
public String getQ_ownership(){ return checkGet(q_ownership); }
public void setQ_ownership(String s){ q_ownership=checkSet(s); }
public String getQ_caseNo(){ return checkGet(q_caseNo); }
public void setQ_caseNo(String s){ q_caseNo=checkSet(s); }
public String getQ_propertyNo(){ return checkGet(q_propertyNo); }
public void setQ_propertyNo(String s){ q_propertyNo=checkSet(s); }
public String getQ_serialNo(){ return checkGet(q_serialNo); }
public void setQ_serialNo(String s){ q_serialNo=checkSet(s); }
public String getQ_serialNo1(){ return checkGet(q_serialNo1); }
public void setQ_serialNo1(String s){ q_serialNo1=checkSet(s); }
public String getQ_signNo(){ return checkGet(q_signNo); }
public void setQ_signNo(String s){ q_signNo=checkSet(s); }
public String getQ_signName(){ return checkGet(q_signName); }
public void setQ_signName(String s){ q_signName=checkSet(s); }


//傳回執行insert前之檢查sql
protected String[][] getInsertCheckSQL(){
	//取得增減帳面設定價值(增加)
	UNTRT009F obj = this;
	Database db = new Database();
	ResultSet rs1;	
	String sql1="select nvl(sum(adjustBookValue),0) as adjustBookValue1 from UNTRT_AdjustDetail where 1=1 and adjustType='1' " +
				" and enterOrg = " + Common.sqlChar(enterOrg) + 
				" and ownership = " + Common.sqlChar(ownership) + 
				" and caseNo = " + Common.sqlChar(caseNo) + 
				" and propertyNo = " + Common.sqlChar(propertyNo) + 
				" and serialNo = " + Common.sqlChar(serialNo) + 
				"";		
	try {	
		rs1 = db.querySQL(sql1);
		if (rs1.next()){
			if(adjustType.equals("1")){
				obj.setAdjustProofAdjustBookValue1(Float.parseFloat(rs1.getString("adjustBookValue1"))+Float.parseFloat(adjustBookValue)+"");
			}else obj.setAdjustProofAdjustBookValue1(rs1.getString("adjustBookValue1"));
		} else {
			obj.setAdjustProofAdjustBookValue1(adjustBookValue);
		}

		//取得增減帳面設定價值(減少)
		ResultSet rs2;	
		String sql2="select nvl(sum(adjustBookValue),0) as adjustBookValue2 from UNTRT_AdjustDetail where 1=1 and adjustType='2' " +
					" and enterOrg = " + Common.sqlChar(enterOrg) + 
					" and ownership = " + Common.sqlChar(ownership) + 
					" and caseNo = " + Common.sqlChar(caseNo) + 
					" and propertyNo = " + Common.sqlChar(propertyNo) + 
					" and serialNo = " + Common.sqlChar(serialNo) + 
					"";		
		rs2 = db.querySQL(sql2);
		if (rs2.next()){
			if(adjustType.equals("2")){
				obj.setAdjustProofAdjustBookValue2(Float.parseFloat(rs2.getString("adjustBookValue2"))+Float.parseFloat(adjustBookValue)+"");
			}else obj.setAdjustProofAdjustBookValue2(rs2.getString("adjustBookValue2"));
		} else {
			obj.setAdjustProofAdjustBookValue(adjustBookValue);
		}
	} catch (Exception e) {
		e.printStackTrace();
	} finally {
		db.closeAll();
	}
	//取得新帳面設定價值
	Database dbN = new Database();
	ResultSet rsN;	
	String sqlN="select nvl(oldBookValue,0) as oldBookValue, nvl(newBookValue,0) as newBookValue from UNTRT_AdjustProof where 1=1" +
				" and enterOrg = " + Common.sqlChar(enterOrg) + 
				" and ownership = " + Common.sqlChar(ownership) + 
				" and caseNo = " + Common.sqlChar(caseNo) + 
				" and propertyNo = " + Common.sqlChar(propertyNo) + 
				" and serialNo = " + Common.sqlChar(serialNo) + 
				"";		
	try {		
		rsN = dbN.querySQL(sqlN);
		if (rsN.next()){
			obj.setAdjustProofAdjustBookValue(Float.parseFloat(obj.getAdjustProofAdjustBookValue1())+(Float.parseFloat(obj.getAdjustProofAdjustBookValue2()))*-1+"");
			if(Float.parseFloat(obj.getAdjustProofAdjustBookValue()) < 0){
				obj.setAdjustProofAdjustType("2");
				obj.setAdjustProofNewBookValue(Float.parseFloat(rsN.getString("oldBookValue"))+Float.parseFloat(obj.getAdjustProofAdjustBookValue())+"");
				obj.setAdjustProofAdjustBookValue((Float.parseFloat(obj.getAdjustProofAdjustBookValue())*-1)+"");
			}else{
				obj.setAdjustProofAdjustType("1");
				obj.setAdjustProofNewBookValue(Float.parseFloat(rsN.getString("oldBookValue"))+Float.parseFloat(obj.getAdjustProofAdjustBookValue())+"");
			}

		} else {
			obj.setAdjustProofNewBookValue(rsN.getString("newBookValue"));
		}
	} catch (Exception e) {
		e.printStackTrace();
	} finally {
		dbN.closeAll();
	}
	String[][] checkSQLArray = new String[1][4];
 	checkSQLArray[0][0]=" select count(*) as checkResult from UNTRT_AdjustDetail where 1=1 " + 
		" and enterOrg = " + Common.sqlChar(enterOrg) + 
		" and ownership = " + Common.sqlChar(ownership) + 
		" and caseNo = " + Common.sqlChar(caseNo) + 
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
	UNTRT009F obj = this;
	String[] execSQLArray = new String[2];
	execSQLArray[0]=" insert into UNTRT_AdjustDetail(" +
			" enterOrg,"+
			" ownership,"+
			" caseNo,"+
			" propertyNo,"+
			" serialNo,"+
			" serialNo1,"+
			" oldSerialNo1,"+
			" signNo,"+
			" setObligor,"+
			" area,"+
			" holdNume,"+
			" holdDeno,"+
			" holdArea,"+
			" setArea,"+
			" oldBookValue,"+
			" adjustType,"+
			" adjustBookValue,"+
			" newBookValue,"+
			" notes,"+
			" editID,"+
			" editDate,"+
			" editTime"+
		" )Values(" +
			Common.sqlChar(enterOrg) + "," +
			Common.sqlChar(ownership) + "," +
			Common.sqlChar(caseNo) + "," +
			Common.sqlChar(propertyNo) + "," +
			Common.sqlChar(serialNo) + "," +
			Common.sqlChar(serialNo1) + "," +
			Common.sqlChar(oldSerialNo1) + "," +
			Common.sqlChar(signNo) + "," +
			Common.sqlChar(setObligor) + "," +
			Common.sqlChar(area) + "," +
			Common.sqlChar(holdNume) + "," +
			Common.sqlChar(holdDeno) + "," +
			Common.sqlChar(holdArea) + "," +
			Common.sqlChar(setArea) + "," +
			Common.sqlChar(oldBookValue) + "," +
			Common.sqlChar(adjustType) + "," +
			Common.sqlChar(adjustBookValue) + "," +
			Common.sqlChar(newBookValue) + "," +
			Common.sqlChar(notes) + "," +
			Common.sqlChar(getEditID()) + "," +
			Common.sqlChar(getEditDate()) + "," +
			Common.sqlChar(getEditTime()) + ")" ;
	execSQLArray[1]=" update UNTRT_AdjustProof set " +
					" adjustType = " + obj.getAdjustProofAdjustType()+ ","+
					" adjustBookValue = " + obj.getAdjustProofAdjustBookValue() + ","+
					" newBookValue = " + obj.getAdjustProofNewBookValue() +
				" where 1=1 " + 
					" and enterOrg = " + Common.sqlChar(enterOrg) +
					" and ownership = " + Common.sqlChar(ownership) +
					" and caseNo = " + Common.sqlChar(caseNo) +
					" and propertyNo = " + Common.sqlChar(propertyNo) +
					" and serialNo = " + Common.sqlChar(serialNo) +
					"";	
	
	return execSQLArray;
}
//傳回執行update前之檢查sql
protected void getUpdateQuerySQL(){
	//取得增減帳面設定價值(增加)
	UNTRT009F obj = this;
	Database db1 = new Database();
	ResultSet rs1;	
	String sql1="select nvl(sum(adjustBookValue),0) as adjustBookValue1 from UNTRT_AdjustDetail where 1=1 and adjustType='1' " +
				" and enterOrg = " + Common.sqlChar(enterOrg) + 
				" and ownership = " + Common.sqlChar(ownership) + 
				" and caseNo = " + Common.sqlChar(caseNo) + 
				" and propertyNo = " + Common.sqlChar(propertyNo) + 
				" and serialNo = " + Common.sqlChar(serialNo) + 
				" and serialNo1 != " + Common.sqlChar(serialNo1) +
				"";		
	try {	
		rs1 = db1.querySQL(sql1);
		if (rs1.next()){
			if(adjustType.equals("1")){
				obj.setAdjustProofAdjustBookValue1(Float.parseFloat(rs1.getString("adjustBookValue1"))+Float.parseFloat(adjustBookValue)+"");
			}else obj.setAdjustProofAdjustBookValue1(rs1.getString("adjustBookValue1"));
		} else {
			obj.setAdjustProofAdjustBookValue1(adjustBookValue);
		}
	} catch (Exception e) {
		e.printStackTrace();
	} finally {
		db1.closeAll();
	}
	//取得增減帳面設定價值(減少)
	Database db2 = new Database();
	ResultSet rs2;	
	String sql2="select nvl(sum(adjustBookValue),0) as adjustBookValue2 from UNTRT_AdjustDetail where 1=1 and adjustType='2' " +
				" and enterOrg = " + Common.sqlChar(enterOrg) + 
				" and ownership = " + Common.sqlChar(ownership) + 
				" and caseNo = " + Common.sqlChar(caseNo) + 
				" and propertyNo = " + Common.sqlChar(propertyNo) + 
				" and serialNo = " + Common.sqlChar(serialNo) + 
				" and serialNo1 != " + Common.sqlChar(serialNo1) +
				"";		
	try {	
		rs2 = db2.querySQL(sql2);
		if (rs2.next()){
			if(adjustType.equals("2")){
				obj.setAdjustProofAdjustBookValue2(Float.parseFloat(rs2.getString("adjustBookValue2"))+Float.parseFloat(adjustBookValue)+"");
			}else obj.setAdjustProofAdjustBookValue2(rs2.getString("adjustBookValue2"));
		} else {
			obj.setAdjustProofAdjustBookValue(adjustBookValue);
		}
	} catch (Exception e) {
		e.printStackTrace();
	} finally {
		db2.closeAll();
	}
	
	//取得新帳面設定價值
	Database dbN = new Database();
	ResultSet rsN;	
	String sqlN="select nvl(oldBookValue,0) as oldBookValue, nvl(newBookValue,0) as newBookValue from UNTRT_AdjustProof where 1=1" +
				" and enterOrg = " + Common.sqlChar(enterOrg) + 
				" and ownership = " + Common.sqlChar(ownership) + 
				" and caseNo = " + Common.sqlChar(caseNo) + 
				" and propertyNo = " + Common.sqlChar(propertyNo) + 
				" and serialNo = " + Common.sqlChar(serialNo) + 
				"";		
	try {		
		rsN = dbN.querySQL(sqlN);
		if (rsN.next()){
			obj.setAdjustProofAdjustBookValue(Float.parseFloat(obj.getAdjustProofAdjustBookValue1())+(Float.parseFloat(obj.getAdjustProofAdjustBookValue2()))*-1+"");
			if(Float.parseFloat(obj.getAdjustProofAdjustBookValue()) < 0){
				obj.setAdjustProofAdjustType("2");
				obj.setAdjustProofNewBookValue(Float.parseFloat(rsN.getString("oldBookValue"))+Float.parseFloat(obj.getAdjustProofAdjustBookValue())+"");
				obj.setAdjustProofAdjustBookValue((Float.parseFloat(obj.getAdjustProofAdjustBookValue())*-1)+"");
			}else{
				obj.setAdjustProofAdjustType("1");
				obj.setAdjustProofNewBookValue(Float.parseFloat(rsN.getString("oldBookValue"))+Float.parseFloat(obj.getAdjustProofAdjustBookValue())+"");
			}

		} else {
			obj.setAdjustProofNewBookValue(rsN.getString("newBookValue"));
		}
	} catch (Exception e) {
		e.printStackTrace();
	} finally {
		dbN.closeAll();
	}
}

//傳回update sql
protected String[] getUpdateSQL(){
	UNTRT009F obj = this;
	getUpdateQuerySQL();
	String[] execSQLArray = new String[2];
	execSQLArray[0]=" update UNTRT_AdjustDetail set " +
			" oldSerialNo1 = " + Common.sqlChar(oldSerialNo1) + "," +
			" signNo = " + Common.sqlChar(signNo) + "," +
			" setObligor = " + Common.sqlChar(setObligor) + "," +
			" area = " + Common.sqlChar(area) + "," +
			" holdNume = " + Common.sqlChar(holdNume) + "," +
			" holdDeno = " + Common.sqlChar(holdDeno) + "," +
			" holdArea = " + Common.sqlChar(holdArea) + "," +
			" setArea = " + Common.sqlChar(setArea) + "," +
			" oldBookValue = " + Common.sqlChar(oldBookValue) + "," +
			" adjustType = " + Common.sqlChar(adjustType) + "," +
			" adjustBookValue = " + Common.sqlChar(adjustBookValue) + "," +
			" newBookValue = " + Common.sqlChar(newBookValue) + "," +
			" notes = " + Common.sqlChar(notes) + "," +
			" editID = " + Common.sqlChar(getEditID()) + "," +
			" editDate = " + Common.sqlChar(getEditDate()) + "," +
			" editTime = " + Common.sqlChar(getEditTime()) + 
		" where 1=1 " + 
			" and enterOrg = " + Common.sqlChar(enterOrg) +
			" and ownership = " + Common.sqlChar(ownership) +
			" and caseNo = " + Common.sqlChar(caseNo) +
			" and propertyNo = " + Common.sqlChar(propertyNo) +
			" and serialNo = " + Common.sqlChar(serialNo) +
			" and serialNo1 = " + Common.sqlChar(serialNo1) +
			"";
	execSQLArray[1]=" update UNTRT_AdjustProof set " +
					" adjustType = " + obj.getAdjustProofAdjustType()+ ","+
					" adjustBookValue = " + obj.getAdjustProofAdjustBookValue() + ","+
					" newBookValue = " + obj.getAdjustProofNewBookValue() +
				" where 1=1 " + 
					" and enterOrg = " + Common.sqlChar(enterOrg) +
					" and ownership = " + Common.sqlChar(ownership) +
					" and caseNo = " + Common.sqlChar(caseNo) +
					" and propertyNo = " + Common.sqlChar(propertyNo) +
					" and serialNo = " + Common.sqlChar(serialNo) +
					"";	
	return execSQLArray;
}
//傳回執行delete前之檢查sql
protected void getDeleteQuerySQL(){
	//取得增減帳面設定價值(增加)
	UNTRT009F obj = this;
	Database db1 = new Database();
	ResultSet rs1;	
	String sql1="select nvl(sum(adjustBookValue),0) as adjustBookValue1 from UNTRT_AdjustDetail where 1=1 and adjustType='1' " +
				" and enterOrg = " + Common.sqlChar(enterOrg) + 
				" and ownership = " + Common.sqlChar(ownership) + 
				" and caseNo = " + Common.sqlChar(caseNo) + 
				" and propertyNo = " + Common.sqlChar(propertyNo) + 
				" and serialNo = " + Common.sqlChar(serialNo) + 
				" and serialNo1 != " + Common.sqlChar(serialNo1) +
				"";		
	try {	
		rs1 = db1.querySQL(sql1);
		if (rs1.next()){
				obj.setAdjustProofAdjustBookValue1(Float.parseFloat(rs1.getString("adjustBookValue1"))+"");
		} else {
			obj.setAdjustProofAdjustBookValue1(adjustBookValue);
		}
	} catch (Exception e) {
		e.printStackTrace();
	} finally {
		db1.closeAll();
	}
	//取得增減帳面設定價值(減少)
	Database db2 = new Database();
	ResultSet rs2;	
	String sql2="select nvl(sum(adjustBookValue),0) as adjustBookValue2 from UNTRT_AdjustDetail where 1=1 and adjustType='2' " +
				" and enterOrg = " + Common.sqlChar(enterOrg) + 
				" and ownership = " + Common.sqlChar(ownership) + 
				" and caseNo = " + Common.sqlChar(caseNo) + 
				" and propertyNo = " + Common.sqlChar(propertyNo) + 
				" and serialNo = " + Common.sqlChar(serialNo) + 
				" and serialNo1 != " + Common.sqlChar(serialNo1) +
				"";		
	try {	
		rs2 = db2.querySQL(sql2);
		if (rs2.next()){
				obj.setAdjustProofAdjustBookValue2(Float.parseFloat(rs2.getString("adjustBookValue2"))+"");
		} else {
			obj.setAdjustProofAdjustBookValue(adjustBookValue);
		}
	} catch (Exception e) {
		e.printStackTrace();
	} finally {
		db2.closeAll();
	}
	
	//取得新帳面設定價值
	Database dbN = new Database();
	ResultSet rsN;	
	String sqlN="select nvl(oldBookValue,0) as oldBookValue, nvl(newBookValue,0) as newBookValue from UNTRT_AdjustProof where 1=1" +
				" and enterOrg = " + Common.sqlChar(enterOrg) + 
				" and ownership = " + Common.sqlChar(ownership) + 
				" and caseNo = " + Common.sqlChar(caseNo) + 
				" and propertyNo = " + Common.sqlChar(propertyNo) + 
				" and serialNo = " + Common.sqlChar(serialNo) + 
				"";		
	try {		
		rsN = dbN.querySQL(sqlN);
		if (rsN.next()){
			obj.setAdjustProofAdjustBookValue(Float.parseFloat(obj.getAdjustProofAdjustBookValue1())+(Float.parseFloat(obj.getAdjustProofAdjustBookValue2()))*-1+"");
			if(Float.parseFloat(obj.getAdjustProofAdjustBookValue()) < 0){
				obj.setAdjustProofAdjustType("2");
				obj.setAdjustProofNewBookValue(Float.parseFloat(rsN.getString("oldBookValue"))+Float.parseFloat(obj.getAdjustProofAdjustBookValue())+"");
				obj.setAdjustProofAdjustBookValue((Float.parseFloat(obj.getAdjustProofAdjustBookValue())*-1)+"");
			}else{
				obj.setAdjustProofAdjustType("1");
				obj.setAdjustProofNewBookValue(Float.parseFloat(rsN.getString("oldBookValue"))+Float.parseFloat(obj.getAdjustProofAdjustBookValue())+"");
			}

		} else {
			obj.setAdjustProofNewBookValue(rsN.getString("newBookValue"));
		}
	} catch (Exception e) {
		e.printStackTrace();
	} finally {
		dbN.closeAll();
	}
}

//傳回delete sql
protected String[] getDeleteSQL(){
	UNTRT009F obj = this;
	getDeleteQuerySQL();
	String[] execSQLArray = new String[2];
	execSQLArray[0]=" delete UNTRT_AdjustDetail where 1=1 " +
			" and enterOrg = " + Common.sqlChar(enterOrg) +
			" and ownership = " + Common.sqlChar(ownership) +
			" and caseNo = " + Common.sqlChar(caseNo) +
			" and propertyNo = " + Common.sqlChar(propertyNo) +
			" and serialNo = " + Common.sqlChar(serialNo) +
			" and serialNo1 = " + Common.sqlChar(serialNo1) +
			"";
	execSQLArray[1]=" update UNTRT_AdjustProof set " +
					" adjustType = " + obj.getAdjustProofAdjustType()+ ","+
					" adjustBookValue = " + obj.getAdjustProofAdjustBookValue() + ","+
					" newBookValue = " + obj.getAdjustProofNewBookValue() +
				" where 1=1 " + 
					" and enterOrg = " + Common.sqlChar(enterOrg) +
					" and ownership = " + Common.sqlChar(ownership) +
					" and caseNo = " + Common.sqlChar(caseNo) +
					" and propertyNo = " + Common.sqlChar(propertyNo) +
					" and serialNo = " + Common.sqlChar(serialNo) +
					"";	
	return execSQLArray;
}


/**
 * <br>
 * <br>目的：依主鍵查詢單一資料
 * <br>參數：無
 * <br>傳回：傳回本物件
*/

public UNTRT009F  queryOne() throws Exception{
	Database db = new Database();
	UNTRT009F obj = this;
	try {
		String sql=" select a.enterOrg, a.ownership, a.caseNo, a.propertyNo, a.serialNo, a.serialNo1, a.signNo, a.setObligor, a.area, a.holdNume, a.holdDeno, a.holdArea, a.setArea, a.oldBookValue, a.adjustType, a.adjustBookValue, a.newBookValue, a.notes, a.editID, a.editDate, a.editTime," +
					" c.organSName, b.propertyName, d.signDesc as signName, e.verify, a.oldSerialNo1 "+
					" from UNTRT_AdjustDetail a, SYSPK_PropertyCode b, SYSCA_Organ c, SYSCA_SIGN d, UNTRT_AdjustProof e where 1=1 " +
					" and a.propertyNo = b.propertyNo and a.enterOrg = c.organID and substr(a.signNo,1,7)= d.signNo " +
					" and a.enterOrg=e.enterOrg and a.ownership=e.ownership and a.propertyNo=e.propertyNo and a.serialNo=e.serialNo and a.caseNo=e.caseNo"+ 
					" and a.enterOrg = " + Common.sqlChar(enterOrg) +
					" and a.ownership = " + Common.sqlChar(ownership) +
					" and a.caseNo = " + Common.sqlChar(caseNo) +
					" and a.propertyNo = " + Common.sqlChar(propertyNo) +
					" and a.serialNo = " + Common.sqlChar(serialNo) +
					" and a.serialNo1 = " + Common.sqlChar(serialNo1) +
					"";
		ResultSet rs = db.querySQL(sql);
		if (rs.next()){
			obj.setEnterOrg(rs.getString("enterOrg"));
			obj.setEnterOrgName(rs.getString("organSName"));
			obj.setOwnership(rs.getString("ownership"));
			obj.setCaseNo(rs.getString("caseNo"));
			obj.setPropertyNo(rs.getString("propertyNo"));
			obj.setPropertyNoName(rs.getString("propertyName"));
			obj.setSerialNo(rs.getString("serialNo"));
			obj.setSerialNo1(rs.getString("serialNo1"));
			obj.setOldSerialNo1(rs.getString("oldSerialNo1"));
			obj.setSignNo(rs.getString("signNo"));
			obj.setSignName(rs.getString("signName")+rs.getString("signNo").substring(7,11)+"-"+rs.getString("signNo").substring(11));
			obj.setSetObligor(rs.getString("setObligor"));
			obj.setArea(rs.getString("area"));
			obj.setHoldNume(rs.getString("holdNume"));
			obj.setHoldDeno(rs.getString("holdDeno"));
			obj.setHoldArea(rs.getString("holdArea"));
			obj.setSetArea(rs.getString("setArea"));
			obj.setOldBookValue(rs.getString("oldBookValue"));
			obj.setAdjustType(rs.getString("adjustType"));
			obj.setAdjustBookValue(rs.getString("adjustBookValue"));
			obj.setNewBookValue(rs.getString("newBookValue"));
			obj.setNotes(rs.getString("notes"));
			obj.setEditID(rs.getString("editID"));
			obj.setEditDate(rs.getString("editDate"));
			obj.setEditTime(rs.getString("editTime"));
			obj.setVerify(rs.getString("verify"));
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
	//UNTRT009F obj = this;
	Database db = new Database();
	ArrayList objList=new ArrayList();
	int counter=0;
	try {
		String sql=" select a.enterOrg, a.ownership, a.caseNo, a.propertyNo, a.serialNo, a.serialNo1, a.signNo, a.setObligor, a.oldBookValue, a.adjustBookValue, a.newBookValue," +
					" b.signDesc as signName, c.verify "+
					" from UNTRT_AdjustDetail a, SYSCA_SIGN b, UNTRT_AdjustProof c where 1=1 " +
					" and substr(a.signNo,1,7)= b.signNo and a.enterOrg=c.enterOrg and a.ownership=c.ownership and a.propertyNo=c.propertyNo and a.serialNo=c.serialNo and a.caseNo=c.caseNo"; 
		if ("adjustProof".equals(getQuerySelect()) || "".equals(getQuerySelect())) {
			sql+=" and a.enterOrg = " + Common.sqlChar(getEnterOrg()) ;
			sql+=" and a.ownership = " + Common.sqlChar(getOwnership()) ;
			sql+=" and a.caseNo = " + Common.sqlChar(getCaseNo()) ;
			sql+=" and a.propertyNo = " + Common.sqlChar(getPropertyNo()) ;
			sql+=" and a.serialNo=" + Common.sqlChar(getSerialNo());
		} else {
			if (!"".equals(getQ_enterOrg())) {
				sql+=" and a.enterOrg = " + Common.sqlChar(getQ_enterOrg()) ;
			} else {
				if (!getIsAdminManager().equalsIgnoreCase("Y")) {
					if ("Y".equals(getIsOrganManager())) {					
						//sql += " and a.enterOrg like '" + getOrganID().substring(0,5) + "%' ";	
						sql += " and ( a.enterOrg = "+ Common.sqlChar(getOrganID()) +" or a.enterOrg in ( select organID from SYSCA_Organ where organSuperior = "+ Common.sqlChar(getOrganID()) +" ) ) ";					
					} else {
						sql+=" and a.enterOrg = " + Common.sqlChar(getOrganID()) ;
					}
				}
			}
			if (!"".equals(getQ_ownership()))
				sql+=" and a.ownership = " + Common.sqlChar(getQ_ownership()) ;
			if (!"".equals(getQ_caseNoS()))
				sql+=" and a.caseNo >= " + Common.sqlChar(Common.formatRearString(getQ_caseNoS(),10,'0'));		
			if (!"".equals(getQ_caseNoE()))
				sql+=" and a.caseNo<=" + Common.sqlChar(Common.formatRearString(getQ_caseNoE(),10, '9'));
			if (!"".equals(getQ_caseName()))
				sql+=" and c.caseName  like " + Common.sqlChar(getQ_caseName()+"%") ;
			if (!"".equals(getQ_propertyNoS()))
				sql+=" and a.propertyNo >= " + Common.sqlChar(getQ_propertyNoS());		
			if (!"".equals(getQ_propertyNoE()))
				sql+=" and a.propertyNo<=" + Common.sqlChar(getQ_propertyNoE());						
			if (!"".equals(getQ_serialNoS()))
				sql+=" and a.serialNo >= " + Common.sqlChar(getQ_serialNoS());		
			if (!"".equals(getQ_serialNoE()))
				sql+=" and a.serialNo <=" + Common.sqlChar(getQ_serialNoE());			
			if (!"".equals(getQ_cause()))
				sql+=" and c.cause = " + Common.sqlChar(getQ_cause()) ;
			if (!"".equals(getQ_writeDateS()))
				sql+=" and c.writeDate >= " + Common.sqlChar(new Datetime().changeTaiwanYYMMDD(getQ_writeDateS(),"2"));		
			if (!"".equals(getQ_writeDateE()))
				sql+=" and c.writeDate<=" + Common.sqlChar(new Datetime().changeTaiwanYYMMDD(getQ_writeDateE(),"2"));   	    
			if (!"".equals(getQ_proofDoc()))
				sql+=" and c.proofDoc like " + Common.sqlChar(getQ_proofDoc()+"%") ;
			if (!"".equals(getQ_proofNoS())) 
				sql+=" and c.proofNo >= " + Common.sqlChar(getQ_proofNoS());		
			if (!"".equals(getQ_proofNoE())) 
				sql+=" and c.proofNo <= " + Common.sqlChar(getQ_proofNoE());		 
			if (!"".equals(getQ_adjustDateS()))
				sql+=" and c.adjustDate >= " + Common.sqlChar(getQ_adjustDateS()) ;
			if (!"".equals(getQ_adjustDateE()))
				sql+=" and c.adjustDate <= " + Common.sqlChar(getQ_adjustDateE()) ;   
			if (!"".equals(getQ_verify()))
				sql+=" and c.verify = " + Common.sqlChar(getQ_verify()) ;
			if (!"".equals(getQ_propertyKind()))
				sql+=" and c.propertyKind = " + Common.sqlChar(getQ_propertyKind()) ;
			if (!"".equals(getQ_fundType()))
				sql+=" and c.fundType = " + Common.sqlChar(getQ_fundType()) ;
			
			q_signNo = "";
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
			
		}

		ResultSet rs = db.querySQL(sql);
		while (rs.next()){
			counter++;
			String rowArray[]=new String[11];
			rowArray[0]=rs.getString("enterOrg"); 
			rowArray[1]=rs.getString("ownership"); 
			rowArray[2]=rs.getString("caseNo"); 
			rowArray[3]=rs.getString("propertyNo"); 
			rowArray[4]=rs.getString("serialNo"); 
			rowArray[5]=rs.getString("serialNo1"); 
			rowArray[6]=rs.getString("signName")+rs.getString("signNo").substring(7,11)+"-"+rs.getString("signNo").substring(11); 
			rowArray[7]=rs.getString("setObligor"); 
			rowArray[8]=rs.getString("oldBookValue"); 
			rowArray[9]=rs.getString("adjustBookValue"); 
			rowArray[10]=rs.getString("newBookValue"); 
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


