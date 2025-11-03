/*
*<br>程式目的：土地合併分割作業－合併增加單明細
*<br>程式代號：untla031f
*<br>程式日期：0940926
*<br>程式作者：carey
*<br>--------------------------------------------------------
*<br>修改作者　　修改日期　　　修改目的
*<br>--------------------------------------------------------
*/

package unt.la;

import java.sql.ResultSet;
import java.util.ArrayList;
import util.*;

public class UNTLA031F extends UNTLA027Q{


String enterOrg;
String enterOrgName;
String ownership;
String addCaseNo;
String propertyNo;
String serialNo;
String propertyNoName;
String propertyName1;
String signNo1;
String signNo2;
String signNo3;
String signNo4;
String signNo5;
String doorplate;
String cause;
String cause1;
String enterDate;
String dataState;
String reduceDate;
String reduceCause;
String reduceCause1;
String verify;
String closing;
String propertyKind;
String fundType;
String valuable;
String taxCredit;
String grass;
String originalArea;
String originalHoldNume;
String originalHoldDeno;
String originalHoldArea;
String area;
String holdNume;
String holdDeno;
String holdArea;
String originalBasis;
String originalDate;
String originalUnit;
String originalBV;
String originalNote;
String accountingTitle;
String bookUnit;
String bookValue;
String fundsSource;
String useSeparate;
String useKind;
String oriUseSeparate;
String oriUseKind;
String ownershipDate;
String ownershipCause;
String nonProof;
String proofDoc;
String proofVerify;
String ownershipNote;
String field;
String landRule;
String sourceKind;
String sourceDate;
String sourceDoc;
String oldOwner;
String manageOrg;
String manageOrgName;
String useState;
String proceedDateS;
String proceedDateE;
String proceedType;
String appraiseDate;
String notes1;
String notes;
String oldPropertyNo;
String oldSerialNo;
String oldPropertyName;

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
public String getAddCaseNo(){ return checkGet(addCaseNo); }
public void setAddCaseNo(String s){ addCaseNo=checkSet(s); }
public String getPropertyNo(){ return checkGet(propertyNo); }
public void setPropertyNo(String s){ propertyNo=checkSet(s); }
public String getSerialNo(){ return checkGet(serialNo); }
public void setSerialNo(String s){ serialNo=checkSet(s); }
public String getPropertyNoName(){ return checkGet(propertyNoName); }
public void setPropertyNoName(String s){ propertyNoName=checkSet(s); }
public String getPropertyName1(){ return checkGet(propertyName1); }
public void setPropertyName1(String s){ propertyName1=checkSet(s); }
public String getSignNo1(){ return checkGet(signNo1); }
public void setSignNo1(String s){ signNo1=checkSet(s); }
public String getSignNo2(){ return checkGet(signNo2); }
public void setSignNo2(String s){ signNo2=checkSet(s); }
public String getSignNo3(){ return checkGet(signNo3); }
public void setSignNo3(String s){ signNo3=checkSet(s); }
public String getSignNo4(){ return checkGet(signNo4); }
public void setSignNo4(String s){ signNo4=checkSet(Common.formatFrontZero(s,4)); }
public String getSignNo5(){ return checkGet(signNo5); }
public void setSignNo5(String s){ signNo5=checkSet(Common.formatFrontZero(s,4)); }
public String getDoorplate(){ return checkGet(doorplate); }
public void setDoorplate(String s){ doorplate=checkSet(s); }
public String getCause(){ return checkGet(cause); }
public void setCause(String s){ cause=checkSet(s); }
public String getCause1(){ return checkGet(cause1); }
public void setCause1(String s){ cause1=checkSet(s); }
public String getEnterDate(){ return checkGet(enterDate); }
public void setEnterDate(String s){ enterDate=checkSet(s); }
public String getDataState(){ return checkGet(dataState); }
public void setDataState(String s){ dataState=checkSet(s); }
public String getReduceDate(){ return checkGet(reduceDate); }
public void setReduceDate(String s){ reduceDate=checkSet(s); }
public String getReduceCause(){ return checkGet(reduceCause); }
public void setReduceCause(String s){ reduceCause=checkSet(s); }
public String getReduceCause1(){ return checkGet(reduceCause1); }
public void setReduceCause1(String s){ reduceCause1=checkSet(s); }
public String getVerify(){ return checkGet(verify); }
public void setVerify(String s){ verify=checkSet(s); }
public String getClosing(){ return checkGet(closing); }
public void setClosing(String s){ closing=checkSet(s); }
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
public String getOriginalArea(){ return checkGet(originalArea); }
public void setOriginalArea(String s){ originalArea=checkSet(s); }
public String getOriginalHoldNume(){ return checkGet(originalHoldNume); }
public void setOriginalHoldNume(String s){ originalHoldNume=checkSet(s); }
public String getOriginalHoldDeno(){ return checkGet(originalHoldDeno); }
public void setOriginalHoldDeno(String s){ originalHoldDeno=checkSet(s); }
public String getOriginalHoldArea(){ return checkGet(originalHoldArea); }
public void setOriginalHoldArea(String s){ originalHoldArea=checkSet(s); }
public String getArea(){ return checkGet(area); }
public void setArea(String s){ area=checkSet(s); }
public String getHoldNume(){ return checkGet(holdNume); }
public void setHoldNume(String s){ holdNume=checkSet(s); }
public String getHoldDeno(){ return checkGet(holdDeno); }
public void setHoldDeno(String s){ holdDeno=checkSet(s); }
public String getHoldArea(){ return checkGet(holdArea); }
public void setHoldArea(String s){ holdArea=checkSet(s); }
public String getOriginalBasis(){ return checkGet(originalBasis); }
public void setOriginalBasis(String s){ originalBasis=checkSet(s); }
public String getOriginalDate(){ return checkGet(originalDate); }
public void setOriginalDate(String s){ originalDate=checkSet(s); }
public String getOriginalUnit(){ return checkGet(originalUnit); }
public void setOriginalUnit(String s){ originalUnit=checkSet(s); }
public String getOriginalBV(){ return checkGet(originalBV); }
public void setOriginalBV(String s){ originalBV=checkSet(s); }
public String getOriginalNote(){ return checkGet(originalNote); }
public void setOriginalNote(String s){ originalNote=checkSet(s); }
public String getAccountingTitle(){ return checkGet(accountingTitle); }
public void setAccountingTitle(String s){ accountingTitle=checkSet(s); }
public String getBookUnit(){ return checkGet(bookUnit); }
public void setBookUnit(String s){ bookUnit=checkSet(s); }
public String getBookValue(){ return checkGet(bookValue); }
public void setBookValue(String s){ bookValue=checkSet(s); }
public String getFundsSource(){ return checkGet(fundsSource); }
public void setFundsSource(String s){ fundsSource=checkSet(s); }
public String getUseSeparate(){ return checkGet(useSeparate); }
public void setUseSeparate(String s){ useSeparate=checkSet(s); }
public String getUseKind(){ return checkGet(useKind); }
public void setUseKind(String s){ useKind=checkSet(s); }
public String getOriUseSeparate(){ return checkGet(oriUseSeparate); }
public void setOriUseSeparate(String s){ oriUseSeparate=checkSet(s); }
public String getOriUseKind(){ return checkGet(oriUseKind); }
public void setOriUseKind(String s){ oriUseKind=checkSet(s); }
public String getOwnershipDate(){ return checkGet(ownershipDate); }
public void setOwnershipDate(String s){ ownershipDate=checkSet(s); }
public String getOwnershipCause(){ return checkGet(ownershipCause); }
public void setOwnershipCause(String s){ ownershipCause=checkSet(s); }
public String getNonProof(){ return checkGet(nonProof); }
public void setNonProof(String s){ nonProof=checkSet(s); }
public String getProofDoc(){ return checkGet(proofDoc); }
public void setProofDoc(String s){ proofDoc=checkSet(s); }
public String getProofVerify(){ return checkGet(proofVerify); }
public void setProofVerify(String s){ proofVerify=checkSet(s); }
public String getOwnershipNote(){ return checkGet(ownershipNote); }
public void setOwnershipNote(String s){ ownershipNote=checkSet(s); }
public String getField(){ return checkGet(field); }
public void setField(String s){ field=checkSet(s); }
public String getLandRule(){ return checkGet(landRule); }
public void setLandRule(String s){ landRule=checkSet(s); }
public String getSourceKind(){ return checkGet(sourceKind); }
public void setSourceKind(String s){ sourceKind=checkSet(s); }
public String getSourceDate(){ return checkGet(sourceDate); }
public void setSourceDate(String s){ sourceDate=checkSet(s); }
public String getSourceDoc(){ return checkGet(sourceDoc); }
public void setSourceDoc(String s){ sourceDoc=checkSet(s); }
public String getOldOwner(){ return checkGet(oldOwner); }
public void setOldOwner(String s){ oldOwner=checkSet(s); }
public String getManageOrg(){ return checkGet(manageOrg); }
public void setManageOrg(String s){ manageOrg=checkSet(s); }
public String getManageOrgName(){ return checkGet(manageOrgName); }
public void setManageOrgName(String s){ manageOrgName=checkSet(s); }
public String getUseState(){ return checkGet(useState); }
public void setUseState(String s){ useState=checkSet(s); }
public String getProceedDateS(){ return checkGet(proceedDateS); }
public void setProceedDateS(String s){ proceedDateS=checkSet(s); }
public String getProceedDateE(){ return checkGet(proceedDateE); }
public void setProceedDateE(String s){ proceedDateE=checkSet(s); }
public String getProceedType(){ return checkGet(proceedType); }
public void setProceedType(String s){ proceedType=checkSet(s); }
public String getAppraiseDate(){ return checkGet(appraiseDate); }
public void setAppraiseDate(String s){ appraiseDate=checkSet(s); }
public String getNotes1(){ return checkGet(notes1); }
public void setNotes1(String s){ notes1=checkSet(s); }
public String getNotes(){ return checkGet(notes); }
public void setNotes(String s){ notes=checkSet(s); }
public String getOldPropertyNo(){ return checkGet(oldPropertyNo); }
public void setOldPropertyNo(String s){ oldPropertyNo=checkSet(s); }
public String getOldSerialNo(){ return checkGet(oldSerialNo); }
public void setOldSerialNo(String s){ oldSerialNo=checkSet(s); }
public String getOldPropertyName(){ return checkGet(oldPropertyName); }
public void setOldPropertyName(String s){ oldPropertyName=checkSet(s); }

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

//傳回執行insert前之檢查sql
protected String[][] getInsertCheckSQL(){
	String[][] checkSQLArray = new String[2][4];
 	checkSQLArray[0][0]=" select count(*) as checkResult from UNTLA_Land where 1=1 " + 
		" and enterOrg = " + Common.sqlChar(enterOrg) + 
		" and ownership = " + Common.sqlChar(ownership) + 
		" and propertyNo = " + Common.sqlChar(propertyNo) + 
		" and serialNo = " + Common.sqlChar(serialNo) + 
		"";
	checkSQLArray[0][1]=">";
	checkSQLArray[0][2]="0";
	checkSQLArray[0][3]="該筆資料己重複，請重新輸入！";
	
	/*
	//標示不能重複
 	checkSQLArray[1][0]=" select count(*) as checkResult from UNTLA_LAND where 1=1 " + 
		" and enterOrg = " + Common.sqlChar(enterOrg) + 
		" and ownership = " + Common.sqlChar(ownership) + 
		" and signNo = " + Common.sqlChar(signNo1.substring(0,1)+signNo2.substring(1,3)+signNo3.substring(3,7)+signNo4+signNo5) +
		" and dataState = " + Common.sqlChar(dataState) +
		""; 	
	checkSQLArray[1][1]=">";
	checkSQLArray[1][2]="0";
	checkSQLArray[1][3]="土地標示己重複，請重新輸入！";	
	*/
	
	//公告年月檢查
	if("1".equals(originalBasis) || "2".equals(originalBasis)){
	 	checkSQLArray[1][0]=" select count(*) as checkResult from UNTLA_BulletinDate where 1=1 " + 
			" and bulletinKind = " + Common.sqlChar(originalBasis) +  
			" and bulletinDate = " + Common.sqlChar(originalDate) +  	
			""; 	
		checkSQLArray[1][1]="<=";
		checkSQLArray[1][2]="0";
		checkSQLArray[1][3]="公告年月錯誤，請重新輸入！";	
	}else{
		checkSQLArray[1][0]="";
		checkSQLArray[1][1]="";
		checkSQLArray[1][2]="";
		checkSQLArray[1][3]="";
	}
	return checkSQLArray;
}


//傳回insert sql
protected String[] getInsertSQL(){
	//取得分號
	Database db = new Database();
	ResultSet rs;	
	String sql="select (max(serialNo) + 1) as serialNo from UNTLA_LAND " +
		" where enterOrg = " + Common.sqlChar(enterOrg) + 
		" and ownership = " + Common.sqlChar(ownership) + 
		" and propertyNo = " + Common.sqlChar(propertyNo) + 
		"";		
	try {		
		rs = db.querySQL(sql);
		if (rs.next()){		    
			if("".equals(checkGet(rs.getString("serialNo")))){
				setSerialNo("0000001");
			}else{
				setSerialNo(rs.getString("serialNo"));
			}
		} else {
			setSerialNo("0000001");
		}
	} catch (Exception e) {
		e.printStackTrace();
	} finally {
		db.closeAll();
	}

	String[] execSQLArray = new String[1];
	execSQLArray[0]=" insert into UNTLA_Land(" +
			" enterOrg,"+
			" ownership,"+
			" caseNo,"+
			" propertyNo,"+
			" serialNo,"+
			" propertyName1,"+
			" signNo,"+
			" doorplate,"+
			" cause,"+
			" cause1,"+
			" enterDate,"+
			" dataState,"+
			" reduceDate,"+
			" reduceCause,"+
			" reduceCause1,"+
			" verify,"+
			" closing,"+
			" propertyKind,"+
			" fundType,"+
			" valuable,"+
			" taxCredit,"+
			" grass,"+
			" originalArea,"+
			" originalHoldNume,"+
			" originalHoldDeno,"+
			" originalHoldArea,"+
			" area,"+
			" holdNume,"+
			" holdDeno,"+
			" holdArea,"+
			" originalBasis,"+
			" originalDate,"+
			" originalUnit,"+
			" originalBV,"+
			" originalNote,"+
			" accountingTitle,"+
			" bookUnit,"+
			" bookValue,"+
			" fundsSource,"+
			" useSeparate,"+
			" useKind,"+
			" oriUseSeparate,"+
			" oriUseKind,"+
			" ownershipDate,"+
			" ownershipCause,"+
			" nonProof,"+
			" proofDoc,"+
			" proofVerify,"+
			" ownershipNote,"+
			" field,"+
			" landRule,"+
			" sourceKind,"+
			" sourceDate,"+
			" sourceDoc,"+
			" oldOwner,"+
			" manageOrg,"+
			" useState,"+
			" proceedDateS,"+
			" proceedDateE,"+
			" proceedType,"+
			" appraiseDate,"+
			" notes1,"+
			" notes,"+
			" oldPropertyNo,"+
			" oldSerialNo,"+
			" editID,"+
			" editDate,"+
			" editTime"+
		" )Values(" +
			Common.sqlChar(enterOrg) + "," +
			Common.sqlChar(ownership) + "," +
			Common.sqlChar(addCaseNo) + "," +
			Common.sqlChar(propertyNo) + "," +
			Common.sqlChar(serialNo) + "," +
			Common.sqlChar(propertyName1) + "," +
			Common.sqlChar(signNo1.substring(0,1)+signNo2.substring(1,3)+signNo3.substring(3,7)+signNo4+signNo5) + "," +
			Common.sqlChar(doorplate) + "," +
			Common.sqlChar(cause) + "," +
			Common.sqlChar(cause1) + "," +
			Common.sqlChar(enterDate) + "," +
			Common.sqlChar(dataState) + "," +
			Common.sqlChar(reduceDate) + "," +
			Common.sqlChar(reduceCause) + "," +
			Common.sqlChar(reduceCause1) + "," +
			Common.sqlChar(verify) + "," +
			Common.sqlChar(closing) + "," +
			Common.sqlChar(propertyKind) + "," +
			Common.sqlChar(fundType) + "," +
			Common.sqlChar(valuable) + "," +
			Common.sqlChar(taxCredit) + "," +
			Common.sqlChar(grass) + "," +
			Common.sqlChar(originalArea) + "," +
			Common.sqlChar(originalHoldNume) + "," +
			Common.sqlChar(originalHoldDeno) + "," +
			Common.sqlChar(originalHoldArea) + "," +
			Common.sqlChar(area) + "," +
			Common.sqlChar(holdNume) + "," +
			Common.sqlChar(holdDeno) + "," +
			Common.sqlChar(holdArea) + "," +
			Common.sqlChar(originalBasis) + "," +
			Common.sqlChar(originalDate) + "," +
			Common.sqlChar(originalUnit) + "," +
			Common.sqlChar(originalBV) + "," +
			Common.sqlChar(originalNote) + "," +
			Common.sqlChar(accountingTitle) + "," +
			Common.sqlChar(bookUnit) + "," +
			Common.sqlChar(bookValue) + "," +
			Common.sqlChar(fundsSource) + "," +
			Common.sqlChar(useSeparate) + "," +
			Common.sqlChar(useKind) + "," +
			Common.sqlChar(oriUseSeparate) + "," +
			Common.sqlChar(oriUseKind) + "," +
			Common.sqlChar(ownershipDate) + "," +
			Common.sqlChar(ownershipCause) + "," +
			Common.sqlChar(nonProof) + "," +
			Common.sqlChar(proofDoc) + "," +
			Common.sqlChar(proofVerify) + "," +
			Common.sqlChar(ownershipNote) + "," +
			Common.sqlChar(field) + "," +
			Common.sqlChar(landRule) + "," +
			Common.sqlChar(sourceKind) + "," +
			Common.sqlChar(sourceDate) + "," +
			Common.sqlChar(sourceDoc) + "," +
			Common.sqlChar(oldOwner) + "," +
			Common.sqlChar(manageOrg) + "," +
			Common.sqlChar(useState) + "," +
			Common.sqlChar(proceedDateS) + "," +
			Common.sqlChar(proceedDateE) + "," +
			Common.sqlChar(proceedType) + "," +
			Common.sqlChar(appraiseDate) + "," +
			Common.sqlChar(notes1) + "," +
			Common.sqlChar(notes) + "," +
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
	execSQLArray[0]=" update UNTLA_Land set " +
			" signNo = " + Common.sqlChar(signNo1.substring(0,1)+signNo2.substring(1,3)+signNo3.substring(3,7)+signNo4+signNo5) + "," +
			" propertyName1 = " + Common.sqlChar(propertyName1) + "," +
			" doorplate = " + Common.sqlChar(doorplate) + "," +
			" cause = " + Common.sqlChar(cause) + "," +
			" cause1 = " + Common.sqlChar(cause1) + "," +
			" enterDate = " + Common.sqlChar(enterDate) + "," +
			" dataState = " + Common.sqlChar(dataState) + "," +
			" reduceDate = " + Common.sqlChar(reduceDate) + "," +
			" reduceCause = " + Common.sqlChar(reduceCause) + "," +
			" reduceCause1 = " + Common.sqlChar(reduceCause1) + "," +
			" verify = " + Common.sqlChar(verify) + "," +
			" closing = " + Common.sqlChar(closing) + "," +
			" propertyKind = " + Common.sqlChar(propertyKind) + "," +
			" fundType = " + Common.sqlChar(fundType) + "," +
			" valuable = " + Common.sqlChar(valuable) + "," +
			" taxCredit = " + Common.sqlChar(taxCredit) + "," +
			" grass = " + Common.sqlChar(grass) + "," +
			" originalArea = " + Common.sqlChar(originalArea) + "," +
			" originalHoldNume = " + Common.sqlChar(originalHoldNume) + "," +
			" originalHoldDeno = " + Common.sqlChar(originalHoldDeno) + "," +
			" originalHoldArea = " + Common.sqlChar(originalHoldArea) + "," +
			" area = " + Common.sqlChar(area) + "," +
			" holdNume = " + Common.sqlChar(holdNume) + "," +
			" holdDeno = " + Common.sqlChar(holdDeno) + "," +
			" holdArea = " + Common.sqlChar(holdArea) + "," +
			" originalBasis = " + Common.sqlChar(originalBasis) + "," +
			" originalDate = " + Common.sqlChar(originalDate) + "," +
			" originalUnit = " + Common.sqlChar(originalUnit) + "," +
			" originalBV = " + Common.sqlChar(originalBV) + "," +
			" originalNote = " + Common.sqlChar(originalNote) + "," +
			" accountingTitle = " + Common.sqlChar(accountingTitle) + "," +
			" bookUnit = " + Common.sqlChar(bookUnit) + "," +
			" bookValue = " + Common.sqlChar(bookValue) + "," +
			" fundsSource = " + Common.sqlChar(fundsSource) + "," +
			" useSeparate = " + Common.sqlChar(useSeparate) + "," +
			" useKind = " + Common.sqlChar(useKind) + "," +
			" oriUseSeparate = " + Common.sqlChar(oriUseSeparate) + "," +
			" oriUseKind = " + Common.sqlChar(oriUseKind) + "," +
			" ownershipDate = " + Common.sqlChar(ownershipDate) + "," +
			" ownershipCause = " + Common.sqlChar(ownershipCause) + "," +
			" nonProof = " + Common.sqlChar(nonProof) + "," +
			" proofDoc = " + Common.sqlChar(proofDoc) + "," +
			" proofVerify = " + Common.sqlChar(proofVerify) + "," +
			" ownershipNote = " + Common.sqlChar(ownershipNote) + "," +
			" field = " + Common.sqlChar(field) + "," +
			" landRule = " + Common.sqlChar(landRule) + "," +
			" sourceKind = " + Common.sqlChar(sourceKind) + "," +
			" sourceDate = " + Common.sqlChar(sourceDate) + "," +
			" sourceDoc = " + Common.sqlChar(sourceDoc) + "," +
			" oldOwner = " + Common.sqlChar(oldOwner) + "," +
			" manageOrg = " + Common.sqlChar(manageOrg) + "," +
			" useState = " + Common.sqlChar(useState) + "," +
			" proceedDateS = " + Common.sqlChar(proceedDateS) + "," +
			" proceedDateE = " + Common.sqlChar(proceedDateE) + "," +
			" proceedType = " + Common.sqlChar(proceedType) + "," +
			" appraiseDate = " + Common.sqlChar(appraiseDate) + "," +
			" notes1 = " + Common.sqlChar(notes1) + "," +
			" notes = " + Common.sqlChar(notes) + "," +
			" oldPropertyNo = " + Common.sqlChar(oldPropertyNo) + "," +
			" oldSerialNo = " + Common.sqlChar(oldSerialNo) + "," +
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
	String[] execSQLArray = new String[8];
	String delTable[] = {"UNTLA_Manage", "UNTLA_Person", "UNTLA_Attachment", "UNTLA_Value", "UNTLA_Price", "UNTLA_ViewScene", "UNTLA_Tax"};
	
	//刪除土地主檔之明細檔
	for(int i=0;i<delTable.length;i++){
	    execSQLArray[i]=" delete " + delTable[i]+ " a where exists ( " +
			" select * from UNTLA_Land b where 1=1 " +
			" and b.enterOrg = a.enterOrg " +
			" and b.ownership = a.ownership " +
			" and b.propertyNo = a.propertyNo " +
			" and b.serialNo = a.serialNo " +
			" and b.enterOrg = " + Common.sqlChar(enterOrg) +
			" and b.ownership = " + Common.sqlChar(ownership) +
			" and b.propertyNo = " + Common.sqlChar(propertyNo) +
			" and b.serialNo = " + Common.sqlChar(serialNo) +
			" ) " +
			"";	
	}

	//刪除土地主檔(刪除此檔前需先刪除其相關的明細檔，如土地管理檔...等)
		execSQLArray[7]=" delete UNTLA_Land where 1=1 " +
			" and enterOrg = " + Common.sqlChar(enterOrg) +
			" and ownership = " + Common.sqlChar(ownership) +
			" and propertyNo = " + Common.sqlChar(propertyNo) +
			" and serialNo = " + Common.sqlChar(serialNo) +
			"";
	
	//for (int i=0;i<execSQLArray.length;i++){System.out.println(execSQLArray[i]);}
	
	return execSQLArray;
}


/**
 * <br>
 * <br>目的：依主鍵查詢單一資料
 * <br>參數：無
 * <br>傳回：傳回本物件
*/

public UNTLA031F  queryOne() throws Exception{
	Database db = new Database();
	UNTLA031F obj = this;
	try {
		String sql=" select b.organSName,a.enterOrg, a.ownership, a.caseNo, " +
			" a.propertyNo, a.serialNo, c.propertyName propertyNoName, a.propertyName1, a.signNo , " +
			" a.doorplate, a.cause, a.cause1, a.enterDate, a.dataState, a.reduceDate, " +
			" a.reduceCause, a.reduceCause1, a.verify, a.closing, a.propertyKind, " +
			" a.fundType, a.valuable, a.taxCredit, a.grass, a.originalArea, " +
			" a.originalHoldNume, a.originalHoldDeno, a.originalHoldArea, a.area, " +
			" a.holdNume, a.holdDeno, a.holdArea, a.originalBasis, a.originalDate, " +
			" a.originalUnit, a.originalBV, a.originalNote, a.accountingTitle, " +
			" a.bookUnit, a.bookValue, a.fundsSource, a.useSeparate, a.useKind, " +
			" a.oriUseSeparate, a.oriUseKind, a.ownershipDate, a.ownershipCause, " +
			" a.nonProof, a.proofDoc, a.proofVerify, a.ownershipNote, a.field, " +
			" a.landRule, a.sourceKind, a.sourceDate, a.sourceDoc, a.oldOwner, " +
			" a.manageOrg, a.useState, a.proceedDateS, a.proceedDateE, a.proceedType, " +
			" a.appraiseDate, a.notes1, a.notes, a.oldPropertyNo, a.oldSerialNo, " +
			" (select d.propertyName from SYSPK_PropertyCode d where a.propertyNo = d.propertyNo) as oldPropertyName,"+
			" a.editID, a.editDate, a.editTime  "+
			" from UNTLA_Land a, SYSCA_Organ b , SYSPK_PropertyCode c where 1=1 " +
			" and a.enterOrg = " + Common.sqlChar(enterOrg) +
			" and a.ownership = " + Common.sqlChar(ownership) +
			" and a.caseNo = " + Common.sqlChar(addCaseNo) +
			" and a.propertyNo = " + Common.sqlChar(propertyNo) +
			" and a.serialNo = " + Common.sqlChar(serialNo) +
			" and b.organID = a.enterOrg" +
			" and a.propertyNo = c.propertyNo" +
			"";
		ResultSet rs = db.querySQL(sql);
		if (rs.next()){
			obj.setEnterOrg(rs.getString("enterOrg"));
			obj.setOwnership(rs.getString("ownership"));
			obj.setEnterOrgName(rs.getString("organSName"));
			obj.setAddCaseNo(rs.getString("caseNo"));
			obj.setPropertyNo(rs.getString("propertyNo"));
			obj.setSerialNo(rs.getString("serialNo"));
			obj.setPropertyNoName(rs.getString("propertyNoName"));
			obj.setPropertyName1(rs.getString("propertyName1"));
			obj.setSignNo1(rs.getString("signNo").substring(0,1)+"000000");
			obj.setSignNo2(rs.getString("signNo").substring(0,3)+"0000");
			obj.setSignNo3(rs.getString("signNo").substring(0,7));
			obj.setSignNo4(rs.getString("signNo").substring(7,11));
			obj.setSignNo5(rs.getString("signNo").substring(11));
			obj.setDoorplate(rs.getString("doorplate"));
			obj.setCause(rs.getString("cause"));
			obj.setCause1(rs.getString("cause1"));
			obj.setEnterDate(rs.getString("enterDate"));
			obj.setDataState(rs.getString("dataState"));
			obj.setReduceDate(rs.getString("reduceDate"));
			obj.setReduceCause(rs.getString("reduceCause"));
			obj.setReduceCause1(rs.getString("reduceCause1"));
			obj.setVerify(rs.getString("verify"));
			obj.setClosing(rs.getString("closing"));
			obj.setPropertyKind(rs.getString("propertyKind"));
			obj.setFundType(rs.getString("fundType"));
			obj.setValuable(rs.getString("valuable"));
			obj.setTaxCredit(rs.getString("taxCredit"));
			obj.setGrass(rs.getString("grass"));
			obj.setOriginalArea(rs.getString("originalArea"));
			obj.setOriginalHoldNume(rs.getString("originalHoldNume"));
			obj.setOriginalHoldDeno(rs.getString("originalHoldDeno"));
			obj.setOriginalHoldArea(rs.getString("originalHoldArea"));
			obj.setArea(rs.getString("area"));
			obj.setHoldNume(rs.getString("holdNume"));
			obj.setHoldDeno(rs.getString("holdDeno"));
			obj.setHoldArea(rs.getString("holdArea"));
			obj.setOriginalBasis(rs.getString("originalBasis"));
			obj.setOriginalDate(rs.getString("originalDate"));
			obj.setOriginalUnit(rs.getString("originalUnit"));
			obj.setOriginalBV(rs.getString("originalBV"));
			obj.setOriginalNote(rs.getString("originalNote"));
			obj.setAccountingTitle(rs.getString("accountingTitle"));
			obj.setBookUnit(rs.getString("bookUnit"));
			obj.setBookValue(rs.getString("bookValue"));
			obj.setFundsSource(rs.getString("fundsSource"));
			obj.setUseSeparate(rs.getString("useSeparate"));
			obj.setUseKind(rs.getString("useKind"));
			obj.setOriUseSeparate(rs.getString("oriUseSeparate"));
			obj.setOriUseKind(rs.getString("oriUseKind"));
			obj.setOwnershipDate(rs.getString("ownershipDate"));
			obj.setOwnershipCause(rs.getString("ownershipCause"));
			obj.setNonProof(rs.getString("nonProof"));
			obj.setProofDoc(rs.getString("proofDoc"));
			obj.setProofVerify(rs.getString("proofVerify"));
			obj.setOwnershipNote(rs.getString("ownershipNote"));
			obj.setField(rs.getString("field"));
			obj.setLandRule(rs.getString("landRule"));
			obj.setSourceKind(rs.getString("sourceKind"));
			obj.setSourceDate(rs.getString("sourceDate"));
			obj.setSourceDoc(rs.getString("sourceDoc"));
			obj.setOldOwner(rs.getString("oldOwner"));
			obj.setManageOrg(rs.getString("manageOrg"));
			obj.setUseState(rs.getString("useState"));
			obj.setProceedDateS(rs.getString("proceedDateS"));
			obj.setProceedDateE(rs.getString("proceedDateE"));
			obj.setProceedType(rs.getString("proceedType"));
			obj.setAppraiseDate(rs.getString("appraiseDate"));
			obj.setNotes1(rs.getString("notes1"));
			obj.setNotes(rs.getString("notes"));
			obj.setOldPropertyNo(rs.getString("oldPropertyNo"));
			obj.setOldSerialNo(rs.getString("oldSerialNo"));
			obj.setEditID(rs.getString("editID"));
			obj.setEditDate(rs.getString("editDate"));
			obj.setEditTime(rs.getString("editTime"));
		}else{
			obj.setEnterOrg(enterOrg);
			obj.setOwnership(ownership);
			obj.setEnterOrgName(enterOrgName);
			obj.setAddCaseNo(addCaseNo);
			obj.setPropertyNo("");
			obj.setSerialNo("");
			obj.setPropertyNoName("");
			obj.setPropertyName1("");
			obj.setSignNo1("");
			obj.setSignNo2("");
			obj.setSignNo3("");
			obj.setSignNo4("");
			obj.setSignNo5("");
			obj.setDoorplate("");
			obj.setCause(cause);
			obj.setCause1(cause1);
			obj.setEnterDate(mergeDivisionDate);
			obj.setDataState("");
			obj.setReduceDate("");
			obj.setReduceCause("");
			obj.setReduceCause1("");
			obj.setVerify(verify);
			obj.setClosing("");
			obj.setPropertyKind("");
			obj.setFundType("");
			obj.setValuable("");
			obj.setTaxCredit("");
			obj.setGrass("");
			obj.setOriginalArea("");
			obj.setOriginalHoldNume("");
			obj.setOriginalHoldDeno("");
			obj.setOriginalHoldArea("");
			obj.setArea("");
			obj.setHoldNume("");
			obj.setHoldDeno("");
			obj.setHoldArea("");
			obj.setOriginalBasis("");
			obj.setOriginalDate("");
			obj.setOriginalUnit("");
			obj.setOriginalBV("");
			obj.setOriginalNote("");
			obj.setAccountingTitle("");
			obj.setBookUnit("");
			obj.setBookValue("");
			obj.setFundsSource("");
			obj.setUseSeparate("");
			obj.setUseKind("");
			obj.setOriUseSeparate("");
			obj.setOriUseKind("");
			obj.setOwnershipDate("");
			obj.setOwnershipCause("");
			obj.setNonProof("");
			obj.setProofDoc("");
			obj.setProofVerify("");
			obj.setOwnershipNote("");
			obj.setField("");
			obj.setLandRule("");
			obj.setSourceKind("");
			obj.setSourceDate("");
			obj.setSourceDoc("");
			obj.setOldOwner("");
			obj.setManageOrg("");
			obj.setUseState("");
			obj.setProceedDateS("");
			obj.setProceedDateE("");
			obj.setProceedType("");
			obj.setAppraiseDate("");
			obj.setNotes1("");
			obj.setNotes("");
			obj.setOldPropertyNo("");
			obj.setOldSerialNo("");
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
		String sql=" select a.enterOrg, (select b.organSName from SYSCA_Organ b where b.organID = a.enterOrg ) organSName, a.caseNo, " +
				" a.ownership, (case a.ownership when '1' then '市有' when '2' then '國有' else '' end) ownershipName,"+
			" a.propertyNo, a.serialNo,a.signNo, " +
			" (select codeName from SYSCA_Code d where d.codeKindID='CAA' and d.codeCon1='4' and d.codeID=a.cause) causeName, "+
			" decode(a.dataState,'1','現存','2','已減損','') dataState,"+
			" decode(a.propertyKind,'01','公務用','02','公共用','03','事業用','04','非公用','') propertyKind "+
			" from UNTLA_Land a where 1=1  "+
			" and a.enterOrg = " + Common.sqlChar(enterOrg) +
			" and a.ownership = "  + Common.sqlChar(ownership) +
			" and a.caseNo = " + Common.sqlChar(addCaseNo) ; 

		//System.out.println("queryAll==>"+sql);
		
		ResultSet rs = db.querySQL(sql);
		while (rs.next()){
			counter++;
			String rowArray[]=new String[11];
			rowArray[0]=rs.getString("enterOrg"); 		
			rowArray[1]=rs.getString("ownership"); 
			rowArray[2]=rs.getString("organSName");
			rowArray[3]=rs.getString("ownershipName");				
			rowArray[4]=rs.getString("propertyNo"); 
			rowArray[5]=rs.getString("serialNo"); 		
			rowArray[6]=getSignDescName(rs.getString("signNoNo").substring(0,7)) + rs.getString("signNoNo").substring(7,11) + "-" + rs.getString("signNoNo").substring(11); 
			rowArray[7]=rs.getString("causeName"); 
			rowArray[8]=rs.getString("dataState"); 
			rowArray[9]=rs.getString("propertyKind");
			rowArray[10]=rs.getString("caseNo");
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


