/*
*<br>程式目的：土地主檔資料維護－他項權利
*<br>程式代號：untla051f
*<br>程式日期：0980624
*<br>程式作者：jerry
*<br>--------------------------------------------------------
*<br>修改作者　　修改日期　　　修改目的
*<br>--------------------------------------------------------
*/

package unt.la;

import java.sql.ResultSet;
import java.util.ArrayList;

import unt.ch.UNTCH_Tools;
import util.*;

public class UNTLA051F extends UNTLA001Q{


String enterOrg;
String ownership;
String propertyNo;
String serialNo;
String serialNo1;
String rightNo;
String rightValue;
String rightDateS;
String rightDateE;
String holdNume;
String holdDeno;
String proofDoc;
String obligor;
String obligee;
String notes;
String editID;
String editDate;
String editTime;
String caseNo;

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
public String getRightNo(){ return checkGet(rightNo); }
public void setRightNo(String s){ rightNo=checkSet(s); }
public String getRightValue(){ return checkGet(rightValue); }
public void setRightValue(String s){ rightValue=checkSet(s); }
public String getRightDateS(){ return checkGet(rightDateS); }
public void setRightDateS(String s){ rightDateS=checkSet(s); }
public String getRightDateE(){ return checkGet(rightDateE); }
public void setRightDateE(String s){ rightDateE=checkSet(s); }
public String getHoldNume(){ return checkGet(holdNume); }
public void setHoldNume(String s){ holdNume=checkSet(s); }
public String getHoldDeno(){ return checkGet(holdDeno); }
public void setHoldDeno(String s){ holdDeno=checkSet(s); }
public String getProofDoc(){ return checkGet(proofDoc); }
public void setProofDoc(String s){ proofDoc=checkSet(s); }
public String getObligor(){ return checkGet(obligor); }
public void setObligor(String s){ obligor=checkSet(s); }
public String getObligee(){ return checkGet(obligee); }
public void setObligee(String s){ obligee=checkSet(s); }
public String getNotes(){ return checkGet(notes); }
public void setNotes(String s){ notes=checkSet(s); }
public String getEditID(){ return checkGet(editID); }
public void setEditID(String s){ editID=checkSet(s); }
public String getEditDate(){ return checkGet(editDate); }
public void setEditDate(String s){ editDate=checkSet(s); }
public String getEditTime(){ return checkGet(editTime); }
public void setEditTime(String s){ editTime=checkSet(s); }
public String getCaseNo(){ return checkGet(caseNo); }
public void setCaseNo(String s){ caseNo=checkSet(s); }

String differenceKind;
public String getDifferenceKind() {return checkGet(differenceKind);}
public void setDifferenceKind(String differenceKind) {this.differenceKind = checkSet(differenceKind);}


//傳回執行insert前之檢查sql
protected String[][] getInsertCheckSQL(){
	String[][] checkSQLArray = new String[1][4];
 	checkSQLArray[0][0]=" select count(*) as checkResult from UNTLA_Right where 1=1 " + 
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
	
	//取得他項權利次序最大編號加一
	String sSQL = "select max(serialNo1) serialNo1 from UNTLA_Right where 1 = 1 " +
				" and enterOrg = "+ Common.sqlChar(enterOrg) +
				" and ownership = "+ Common.sqlChar(ownership) +
				" and propertyNo = "+ Common.sqlChar(propertyNo) +
				" and serialNo = "+ Common.sqlChar(serialNo) +
				" and differenceKind = " + Common.sqlChar(differenceKind);
	serialNo1 = util.View.getLookupField(sSQL);
	serialNo1 = String.valueOf(Common.toInt(serialNo1) + 1);
		
	String[] execSQLArray = new String[1];
	execSQLArray[0]=" insert into UNTLA_Right(" +
			" enterOrg,"+
			" ownership,"+
			" propertyNo,"+
			" serialNo,"+
			" serialNo1,"+
			" rightNo,"+
			" rightValue,"+
			" rightDateS,"+
			" rightDateE,"+
			" holdNume,"+
			" holdDeno,"+
			" proofDoc,"+
			" obligor,"+
			" obligee,"+
			" notes,"+
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
			Common.sqlChar(rightNo) + "," +
			Common.sqlChar(rightValue) + "," +
			Common.sqlChar(new UNTCH_Tools()._transToCE_Year(rightDateS)) + "," +
			Common.sqlChar(new UNTCH_Tools()._transToCE_Year(rightDateE)) + "," +
			Common.sqlChar(holdNume) + "," +
			Common.sqlChar(holdDeno) + "," +
			Common.sqlChar(proofDoc) + "," +
			Common.sqlChar(obligor) + "," +
			Common.sqlChar(obligee) + "," +
			Common.sqlChar(notes) + "," +
			Common.sqlChar(getEditID()) + "," +
			Common.sqlChar(new UNTCH_Tools()._transToCE_Year(getEditDate())) + "," +
			Common.sqlChar(getEditTime()) + "," +
			Common.sqlChar(getDifferenceKind()) + ")" ;
	return execSQLArray;
}


//傳回update sql
protected String[] getUpdateSQL(){
	String[] execSQLArray = new String[1];
	execSQLArray[0]=" update UNTLA_Right set " +
			" rightNo = " + Common.sqlChar(rightNo) + "," +
			" rightValue = " + Common.sqlChar(rightValue) + "," +
			" rightDateS = " + Common.sqlChar(new UNTCH_Tools()._transToCE_Year(rightDateS)) + "," +
			" rightDateE = " + Common.sqlChar(new UNTCH_Tools()._transToCE_Year(rightDateE)) + "," +
			" holdNume = " + Common.sqlChar(holdNume) + "," +
			" holdDeno = " + Common.sqlChar(holdDeno) + "," +
			" proofDoc = " + Common.sqlChar(proofDoc) + "," +
			" obligor = " + Common.sqlChar(obligor) + "," +
			" obligee = " + Common.sqlChar(obligee) + "," +
			" notes = " + Common.sqlChar(notes) + "," +
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
	execSQLArray[0]=" delete UNTLA_Right where 1=1 " +
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

public UNTLA051F  queryOne() throws Exception{
	Database db = new Database();
	UNTLA051F obj = this;
	try {
		String sql=" select a.enterOrg, a.ownership, a.propertyNo, a.serialNo, a.serialNo1, a.rightNo, a.rightValue, a.rightDateS, a.rightDateE, a.holdNume, a.holdDeno, a.proofDoc, a.obligor, a.obligee, a.notes, a.editID, a.editDate, a.editTime, a.editID, a.editDate, a.editTime, a.differenceKind "+
			" from UNTLA_Right a where 1=1 " +
			" and a.enterOrg = " + Common.sqlChar(enterOrg) +
			" and a.ownership = " + Common.sqlChar(ownership) +
			" and a.propertyNo = " + Common.sqlChar(propertyNo) +
			" and a.serialNo = " + Common.sqlChar(serialNo) +
			" and a.serialNo1 = " + Common.sqlChar(serialNo1) +
			" and differenceKind = " + Common.sqlChar(differenceKind) +
			"";
		//System.out.println("queryOne:\n"+ sql);
		ResultSet rs = db.querySQL(sql);
		if (rs.next()){
			obj.setEnterOrg(rs.getString("enterOrg"));
			obj.setOwnership(rs.getString("ownership"));
			obj.setPropertyNo(rs.getString("propertyNo"));
			obj.setSerialNo(rs.getString("serialNo"));
			obj.setSerialNo1(rs.getString("serialNo1"));
			obj.setRightNo(rs.getString("rightNo"));
			obj.setRightValue(rs.getString("rightValue"));
			obj.setRightDateS(new UNTCH_Tools()._transToROC_Year(rs.getString("rightDateS")));
			obj.setRightDateE(new UNTCH_Tools()._transToROC_Year(rs.getString("rightDateE")));
			obj.setHoldNume(rs.getString("holdNume"));
			obj.setHoldDeno(rs.getString("holdDeno"));
			obj.setProofDoc(rs.getString("proofDoc"));
			obj.setObligor(rs.getString("obligor"));
			obj.setObligee(rs.getString("obligee"));
			obj.setNotes(rs.getString("notes"));
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
		String sql=" select a.enterOrg, a.ownership, a.propertyNo, a.serialNo, a.serialNo1, a.rightNo, a.rightValue, a.rightDateS, a.rightDateE, a.holdNume, a.holdDeno "+
			" ,(select codename from sysca_code where codekindid='RIG' and codeid=a.rightNo) rightNoName" +	
			" from UNTLA_Right a where 1=1 " +
			" and a.enterOrg = " + Common.sqlChar(enterOrg) +
			" and a.ownership = " + Common.sqlChar(ownership) +
			" and a.propertyNo = " + Common.sqlChar(propertyNo) +
			" and a.serialNo = " + Common.sqlChar(serialNo) +
			" and a.differenceKind = " + Common.sqlChar(differenceKind) +
			"";
		//System.out.println("queryAll:\n"+ sql);
		ResultSet rs = db.querySQL(sql,true);
		UNTCH_Tools ul = new UNTCH_Tools();
		processCurrentPageAttribute(rs);			
		if (getTotalRecord() > 0) {
			int count = getRecordStart();
			int end = getRecordEnd();
			do {
				if (count > end)
					break;
			String rowArray[]=new String[12];
			rowArray[0]=Common.get(rs.getString("enterOrg")); 
			rowArray[1]=Common.get(rs.getString("ownership")); 
			rowArray[2]=Common.get(rs.getString("propertyNo")); 
			rowArray[3]=Common.get(rs.getString("serialNo")); 
			rowArray[4]=Common.get(rs.getString("serialNo1")); 
			rowArray[5]=Common.get(rs.getString("rightNo")); 
			rowArray[6]=Common.get(rs.getString("rightNoName"));
			rowArray[7]=Common.get(rs.getString("rightValue")); 
			rowArray[8]=ul._transToROC_Year(Common.get(rs.getString("rightDateS"))); 
			rowArray[9]=ul._transToROC_Year(Common.get(rs.getString("rightDateE"))); 
			rowArray[10]=Common.get(rs.getString("holdNume")); 
			rowArray[11]=Common.get(rs.getString("holdDeno")); 

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


