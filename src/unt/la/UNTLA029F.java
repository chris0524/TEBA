/*
*<br>程式目的：土地合併分割作業－合併減損單明細
*<br>程式代號：untla029f
*<br>程式日期：0940906
*<br>程式作者：carey
*<br>--------------------------------------------------------
*<br>修改作者　　修改日期　　　修改目的
*<br>--------------------------------------------------------
*/

package unt.la;

import java.sql.ResultSet;
import java.util.ArrayList;
import util.*;

public class UNTLA029F extends UNTLA027Q{


String enterOrg;
String enterOrgName;
String ownership;
String reduceCaseNo;
String propertyNo;
String propertyNoName;
String serialNo;
String propertyName1;
String signNo;
String signNoName;
String signNo1;
String signNo2;
String signNo3;
String signNo4;
String signNo5;
String cause;
String cause1;
String reduceDate;
String verify;
String propertyKind;
String fundType;
String valuable;
String taxCredit;
String grass;
String area;
String holdNume;
String holdDeno;
String holdArea;
String bookNotes;
String accountingTitle;
String bookUnit;
String bookValue;
String useSeparate;
String useKind;
String proofDoc;
String field;
String sourceDate;
String useState;
String bulletinDate;
String valueUnit;
String notes;
String oldPropertyNo;
String oldSerialNo;

String caseName;
String mergeDivision;
String mergeReduce;
String mergeAdd;
String divisionReduce;
String divisionAdd;
String mergeDivisionDate;
String approveOrg;
String approveDate;
String approveDoc;

public String getEnterOrg(){ return checkGet(enterOrg); }
public void setEnterOrg(String s){ enterOrg=checkSet(s); }
public String getEnterOrgName(){ return checkGet(enterOrgName); }
public void setEnterOrgName(String s){ enterOrgName=checkSet(s); }
public String getOwnership(){ return checkGet(ownership); }
public void setOwnership(String s){ ownership=checkSet(s); }
public String getReduceCaseNo(){ return checkGet(reduceCaseNo); }
public void setReduceCaseNo(String s){ reduceCaseNo=checkSet(s); }
public String getPropertyNo(){ return checkGet(propertyNo); }
public void setPropertyNo(String s){ propertyNo=checkSet(s); }
public String getPropertyNoName(){ return checkGet(propertyNoName); }
public void setPropertyNoName(String s){ propertyNoName=checkSet(s); }
public String getSerialNo(){ return checkGet(serialNo); }
public void setSerialNo(String s){ serialNo=checkSet(s); }
public String getPropertyName1(){ return checkGet(propertyName1); }
public void setPropertyName1(String s){ propertyName1=checkSet(s); }
public String getSignNo(){ return checkGet(signNo); }
public void setSignNo(String s){ signNo=checkSet(s); }
public String getSignNoName(){ return checkGet(signNoName); }
public void setSignNoName(String s){ signNoName=checkSet(s); }
public String getSignNo1(){ return checkGet(signNo1); }
public void setSignNo1(String s){ signNo1=checkSet(s); }
public String getSignNo2(){ return checkGet(signNo2); }
public void setSignNo2(String s){ signNo2=checkSet(s); }
public String getSignNo3(){ return checkGet(signNo3); }
public void setSignNo3(String s){ signNo3=checkSet(s); }
public String getSignNo4(){ return checkGet(signNo4); }
public void setSignNo4(String s){ signNo4=checkSet(s); }
public String getSignNo5(){ return checkGet(signNo5); }
public void setSignNo5(String s){ signNo5=checkSet(s); }
public String getCause(){ return checkGet(cause); }
public void setCause(String s){ cause=checkSet(s); }
public String getCause1(){ return checkGet(cause1); }
public void setCause1(String s){ cause1=checkSet(s); }
public String getReduceDate(){ return checkGet(reduceDate); }
public void setReduceDate(String s){ reduceDate=checkSet(s); }
public String getVerify(){ return checkGet(verify); }
public void setVerify(String s){ verify=checkSet(s); }
public String getPropertyKind(){ return checkGet(propertyKind); }
public void setPropertyKind(String s){ propertyKind=checkSet(s); }
public String getFundType(){ return checkGet(fundType); }
public void setFundType(String s){ fundType=checkSet(s); }
public String getValuable(){ return checkGet(valuable); }
public void setValuable(String s){ valuable=checkSet(s); }
public String getTaxCredit(){ return checkGet(taxCredit); }
public void setTaxCredit(String s){ taxCredit=checkSet(s); }
public String getGrass(){ return checkGet(grass); }
public void setGrass(String s){ grass=checkSet(s); }
public String getArea(){ return checkGet(area); }
public void setArea(String s){ area=checkSet(s); }
public String getHoldNume(){ return checkGet(holdNume); }
public void setHoldNume(String s){ holdNume=checkSet(s); }
public String getHoldDeno(){ return checkGet(holdDeno); }
public void setHoldDeno(String s){ holdDeno=checkSet(s); }
public String getHoldArea(){ return checkGet(holdArea); }
public void setHoldArea(String s){ holdArea=checkSet(s); }
public String getBookNotes(){ return checkGet(bookNotes); }
public void setBookNotes(String s){ bookNotes=checkSet(s); }
public String getAccountingTitle(){ return checkGet(accountingTitle); }
public void setAccountingTitle(String s){ accountingTitle=checkSet(s); }
public String getBookUnit(){ return checkGet(bookUnit); }
public void setBookUnit(String s){ bookUnit=checkSet(s); }
public String getBookValue(){ return checkGet(bookValue); }
public void setBookValue(String s){ bookValue=checkSet(s); }
public String getUseSeparate(){ return checkGet(useSeparate); }
public void setUseSeparate(String s){ useSeparate=checkSet(s); }
public String getUseKind(){ return checkGet(useKind); }
public void setUseKind(String s){ useKind=checkSet(s); }
public String getProofDoc(){ return checkGet(proofDoc); }
public void setProofDoc(String s){ proofDoc=checkSet(s); }
public String getField(){ return checkGet(field); }
public void setField(String s){ field=checkSet(s); }
public String getSourceDate(){ return checkGet(sourceDate); }
public void setSourceDate(String s){ sourceDate=checkSet(s); }
public String getUseState(){ return checkGet(useState); }
public void setUseState(String s){ useState=checkSet(s); }
public String getBulletinDate(){ return checkGet(bulletinDate); }
public void setBulletinDate(String s){ bulletinDate=checkSet(s); }
public String getValueUnit(){ return checkGet(valueUnit); }
public void setValueUnit(String s){ valueUnit=checkSet(s); }
public String getNotes(){ return checkGet(notes); }
public void setNotes(String s){ notes=checkSet(s); }
public String getOldPropertyNo(){ return checkGet(oldPropertyNo); }
public void setOldPropertyNo(String s){ oldPropertyNo=checkSet(s); }
public String getOldSerialNo(){ return checkGet(oldSerialNo); }
public void setOldSerialNo(String s){ oldSerialNo=checkSet(s); }

public String getCaseName(){ return checkGet(caseName); }
public void setCaseName(String s){ caseName=checkSet(s); }
public String getMergeDivision(){ return checkGet(mergeDivision); }
public void setMergeDivision(String s){ mergeDivision=checkSet(s); }
public String getMergeReduce(){ return checkGet(mergeReduce); }
public void setMergeReduce(String s){ mergeReduce=checkSet(s); }
public String getMergeAdd(){ return checkGet(mergeAdd); }
public void setMergeAdd(String s){ mergeAdd=checkSet(s); }
public String getDivisionReduce(){ return checkGet(divisionReduce); }
public void setDivisionReduce(String s){ divisionReduce=checkSet(s); }
public String getDivisionAdd(){ return checkGet(divisionAdd); }
public void setDivisionAdd(String s){ divisionAdd=checkSet(s); }
public String getMergeDivisionDate(){ return checkGet(mergeDivisionDate); }
public void setMergeDivisionDate(String s){ mergeDivisionDate=checkSet(s); }
public String getApproveOrg(){ return checkGet(approveOrg); }
public void setApproveOrg(String s){ approveOrg=checkSet(s); }
public String getApproveDate(){ return checkGet(approveDate); }
public void setApproveDate(String s){ approveDate=checkSet(s); }
public String getApproveDoc(){ return checkGet(approveDoc); }
public void setApproveDoc(String s){ approveDoc=checkSet(s); }

String closing;

public String getClosing(){ return checkGet(closing); }
public void setClosing(String s){ closing=checkSet(s); }

//傳回執行insert前之檢查sql
protected String[][] getInsertCheckSQL(){
	String[][] checkSQLArray = new String[1][4];
 	checkSQLArray[0][0]=" select count(*) as checkResult from UNTLA_ReduceDetail where 1=1 " + 
		" and enterOrg = " + Common.sqlChar(enterOrg) + 
		" and ownership = " + Common.sqlChar(ownership) + 
		" and propertyNo = " + Common.sqlChar(propertyNo) + 
		" and serialNo = " + Common.sqlChar(serialNo) + 
		"";
	checkSQLArray[0][1]=">";
	checkSQLArray[0][2]="0";
	checkSQLArray[0][3]="該筆資料己重複，請重新輸入！";
	return checkSQLArray;
}


//傳回insert sql
protected String[] getInsertSQL(){
	String[] execSQLArray = new String[1];
	execSQLArray[0]=" insert into UNTLA_ReduceDetail(" +
			" enterOrg,"+
			" ownership,"+
			" caseNo,"+
			" propertyNo,"+
			" serialNo,"+
			" propertyName1,"+
			" signNo,"+
			" cause,"+
			" cause1,"+
			" reduceDate,"+
			" verify,"+
			" propertyKind,"+
			" fundType,"+
			" valuable,"+
			" taxCredit,"+
			" grass,"+
			" area,"+
			" holdNume,"+
			" holdDeno,"+
			" holdArea,"+
			" bookNotes,"+
			" accountingTitle,"+
			" bookUnit,"+
			" bookValue,"+
			" useSeparate,"+
			" useKind,"+
			" proofDoc,"+
			" field,"+
			" sourceDate,"+
			" useState,"+
			" bulletinDate,"+
			" valueUnit,"+
			" notes,"+
			" closing,"+
			" oldPropertyNo,"+
			" oldSerialNo,"+
			" editID,"+
			" editDate,"+
			" editTime"+
		" )Values(" +
			Common.sqlChar(enterOrg) + "," +
			Common.sqlChar(ownership) + "," +
			Common.sqlChar(reduceCaseNo) + "," +
			Common.sqlChar(propertyNo) + "," +
			Common.sqlChar(serialNo) + "," +
			Common.sqlChar(propertyName1) + "," +
			Common.sqlChar(signNo1.substring(0,1)+signNo2.substring(1,3)+signNo3.substring(3,7)+signNo4+signNo5) + "," +
			Common.sqlChar(cause) + "," +
			Common.sqlChar(cause1) + "," +
			Common.sqlChar(reduceDate) + "," +
			Common.sqlChar(verify) + "," +
			Common.sqlChar(propertyKind) + "," +
			Common.sqlChar(fundType) + "," +
			Common.sqlChar(valuable) + "," +
			Common.sqlChar(taxCredit) + "," +
			Common.sqlChar(grass) + "," +
			Common.sqlChar(area) + "," +
			Common.sqlChar(holdNume) + "," +
			Common.sqlChar(holdDeno) + "," +
			Common.sqlChar(holdArea) + "," +
			Common.sqlChar(bookNotes) + "," +
			Common.sqlChar(accountingTitle) + "," +
			Common.sqlChar(bookUnit) + "," +
			Common.sqlChar(bookValue) + "," +
			Common.sqlChar(useSeparate) + "," +
			Common.sqlChar(useKind) + "," +
			Common.sqlChar(proofDoc) + "," +
			Common.sqlChar(field) + "," +
			Common.sqlChar(sourceDate) + "," +
			Common.sqlChar(useState) + "," +
			Common.sqlChar(bulletinDate) + "," +
			Common.sqlChar(valueUnit) + "," +
			Common.sqlChar(notes) + "," +
			Common.sqlChar(closing) + "," +
			Common.sqlChar(oldPropertyNo) + "," +
			Common.sqlChar(oldSerialNo) + "," +
			Common.sqlChar(getEditID()) + "," +
			Common.sqlChar(getEditDate()) + "," +
			Common.sqlChar(getEditTime()) + ")" ;

	return execSQLArray;
}


//傳回update sql
protected String[] getUpdateSQL(){
	String[] execSQLArray = new String[1];
	execSQLArray[0]=" update UNTLA_ReduceDetail set " +
			" bookNotes = " + Common.sqlChar(bookNotes) + "," +
			" notes = " + Common.sqlChar(notes) + "," +
			" closing = " + Common.sqlChar(closing) + "," +
			" editID = " + Common.sqlChar(getEditID()) + "," +
			" editDate = " + Common.sqlChar(getEditDate()) + "," +
			" editTime = " + Common.sqlChar(getEditTime()) + 
		" where 1=1 " + 
			" and enterOrg = " + Common.sqlChar(enterOrg) +
			" and ownership = " + Common.sqlChar(ownership) +
			" and propertyNo = " + Common.sqlChar(propertyNo) +
			" and serialNo = " + Common.sqlChar(serialNo) +
			"";
	return execSQLArray;
}


//傳回delete sql
protected String[] getDeleteSQL(){
	String[] execSQLArray = new String[1];
	execSQLArray[0]=" delete UNTLA_ReduceDetail where 1=1 " +
			" and enterOrg = " + Common.sqlChar(enterOrg) +
			" and ownership = " + Common.sqlChar(ownership) +
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

public UNTLA029F  queryOne() throws Exception{
	Database db = new Database();
	UNTLA029F obj = this;
	try {
		String sql=" select (select b.organSName from SYSCA_Organ b where b.organID = a.enterOrg ) organSName," +
				" a.enterOrg, a.ownership, a.caseNo, a.propertyNo," +
				" (select c.propertyName from SYSPK_PropertyCode c where c.propertyNo= a.propertyNo ) propertyName," +
				" a.serialNo, a.propertyName1, a.signNo, a.cause, a.cause1, a.reduceDate, a.verify, a.propertyKind, a.fundType, a.valuable, a.taxCredit, a.grass, a.area, a.holdNume, a.holdDeno, a.holdArea, a.bookNotes, a.accountingTitle, a.bookUnit, a.bookValue, a.useSeparate, a.useKind, a.proofDoc, a.field, a.sourceDate, a.useState, a.bulletinDate, a.valueUnit, a.notes, a.oldPropertyNo, a.oldSerialNo, a.editID, a.editDate, a.editTime, a.closing  "+
			" from UNTLA_ReduceDetail a where 1=1 " +
			" and a.enterOrg = " + Common.sqlChar(enterOrg) +
			" and a.ownership = " + Common.sqlChar(ownership) +
			" and a.propertyNo = " + Common.sqlChar(propertyNo) +
			" and a.serialNo = " + Common.sqlChar(serialNo) +
			"";
//System.out.println("untla029f--queryOne--"+sql);
		ResultSet rs = db.querySQL(sql);
		if (rs.next()){
			obj.setEnterOrg(rs.getString("enterOrg"));
			obj.setEnterOrgName(rs.getString("organSName"));
			obj.setOwnership(rs.getString("ownership"));
			obj.setReduceCaseNo(rs.getString("caseNo"));
			obj.setPropertyNo(rs.getString("propertyNo"));
			obj.setPropertyNoName(rs.getString("propertyName"));
			obj.setSerialNo(rs.getString("serialNo"));
			obj.setPropertyName1(rs.getString("propertyName1"));
			obj.setSignNo1(rs.getString("signNo").substring(0,1)+"000000");
			obj.setSignNo2(rs.getString("signNo").substring(0,3)+"0000");
			obj.setSignNo3(rs.getString("signNo").substring(0,7));
			obj.setSignNo4(rs.getString("signNo").substring(7,11));
			obj.setSignNo5(rs.getString("signNo").substring(11));
			obj.setCause(rs.getString("cause"));
			obj.setCause1(rs.getString("cause1"));
			obj.setReduceDate(rs.getString("reduceDate"));
			obj.setVerify(rs.getString("verify"));
			obj.setPropertyKind(rs.getString("propertyKind"));
			obj.setFundType(rs.getString("fundType"));
			obj.setValuable(rs.getString("valuable"));
			obj.setTaxCredit(rs.getString("taxCredit"));
			obj.setGrass(rs.getString("grass"));
			obj.setArea(rs.getString("area"));
			obj.setHoldNume(rs.getString("holdNume"));
			obj.setHoldDeno(rs.getString("holdDeno"));
			obj.setHoldArea(rs.getString("holdArea"));
			obj.setBookNotes(rs.getString("bookNotes"));
			obj.setAccountingTitle(rs.getString("accountingTitle"));
			obj.setBookUnit(rs.getString("bookUnit"));
			obj.setBookValue(rs.getString("bookValue"));
			obj.setUseSeparate(rs.getString("useSeparate"));
			obj.setUseKind(rs.getString("useKind"));
			obj.setProofDoc(rs.getString("proofDoc"));
			obj.setField(rs.getString("field"));
			obj.setSourceDate(rs.getString("sourceDate"));
			obj.setUseState(rs.getString("useState"));
			obj.setBulletinDate(rs.getString("bulletinDate"));
			obj.setValueUnit(rs.getString("valueUnit"));
			obj.setNotes(rs.getString("notes"));
			obj.setClosing(rs.getString("closing"));
			obj.setOldPropertyNo(rs.getString("oldPropertyNo"));
			obj.setOldSerialNo(rs.getString("oldSerialNo"));
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
		String sql=" select a.enterOrg, (select b.organSName from SYSCA_Organ b where b.organID = a.enterOrg ) organSName, " +
				" a.ownership, (case a.ownership when '1' then '市有' when '2' then '國有' else '' end) ownershipName, " +
				" a.caseNo, a.propertyNo, a.serialNo, " +
				" a.signNo, a.signno, " +
				" a.cause, (select codeName from SYSCA_Code d where d.codeKindID='CAA' and d.codeCon1='4' and d.codeID=a.cause) causeName, " +
				" a.propertyKind, (select e.codeName from SYSCA_Code e where e.codeKindID='PKD' and e.codeID=a.propertyKind ) propertyKindName, " +
				" a.holdArea, a.bookValue, " +
				" a.useState, (select f.codeName from SYSCA_Code f where f.codeKindID='UST' and f.codeID=a.useState ) useStateName " +
				" from UNTLA_ReduceDetail a where 1=1 " +
				" and a.enterOrg = " + Common.sqlChar(enterOrg) +
				" and a.ownership = "  + Common.sqlChar(ownership) +
				" and a.caseNo = " + Common.sqlChar(reduceCaseNo) ; 
//System.out.println(sql);
			ResultSet rs = db.querySQL(sql);
		while (rs.next()){
			counter++;
			String rowArray[]=new String[17];
			rowArray[0]=rs.getString("enterOrg");
			rowArray[1]=rs.getString("organSName");
			rowArray[2]=rs.getString("ownership");
			rowArray[3]=rs.getString("ownershipName");
			rowArray[4]=rs.getString("caseNo"); 
			rowArray[5]=rs.getString("propertyNo"); 
			rowArray[6]=rs.getString("serialNo"); 
			rowArray[7]=rs.getString("signNo");
			rowArray[8]=getSignDescName(rs.getString("signNoNo").substring(0,7)) + rs.getString("signNoNo").substring(7,11) + "-" + rs.getString("signNoNo").substring(11);
			rowArray[9]=rs.getString("cause");
			rowArray[10]=rs.getString("causeName");
			rowArray[11]=rs.getString("propertyKind");
			rowArray[12]=rs.getString("propertyKindName");
			rowArray[13]=rs.getString("holdArea"); 
			rowArray[14]=rs.getString("bookValue"); 
			rowArray[15]=rs.getString("useState");
			rowArray[16]=rs.getString("useStateName");
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


