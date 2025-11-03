/*
*<br>程式目的：非消耗品減少作業－減損單明細
*<br>程式代號：untne014f
*<br>程式日期：0941114
*<br>程式作者：judy
*<br>--------------------------------------------------------
*<br>修改作者　　修改日期　　　修改目的
*<br>--------------------------------------------------------
*/ 

package unt.ne;

import java.sql.ResultSet;
import java.util.ArrayList;

import unt.ch.UNTCH_Tools;
import util.*;

public class UNTNE014F extends UNTNE013Q{

String enterOrg;
String enterOrgName;
String ownership;
String caseNo;
String propertyNo;
String propertyNoName;
String lotNo;
String serialNo;
String propertyName1;
String material;
String otherMaterial;
String propertyUnit;
String otherPropertyUnit;
String limitYear;
String otherLimitYear;
String enterDate;
String buyDate;
String cause;
String cause1;
String reduceDate;
String newEnterOrg;
String newEnterOrgName;
String transferDate;
String verify;
String propertyKind;
String fundType;
String valuable;
String bookNotes;
String accountingTitle;
String oldBookAmount;
String oldBookUnit;
String oldBookValue;
String adjustBookAmount;
String adjustBookValue;
String newBookAmount;
String newBookValue;

String articleName;
String nameplate;
String specification;
String licensePlate;
String moveDate;
String keepUnit;
String keepUnitName;
String keeper;
String keeperName;
String useUnit;
String useUnitName;
String userNo;
String userName;
String place;
String returnPlace;
String cause2;
String dealCaseNo;
String dealDate;
String reduceDeal;
String realizeValue;
String shiftOrg;
String shiftOrgName;
String sourceDate;
String useYear;
String useMonth;
String notes;
String oldPropertyNo;
String oldPropertyName;
String oldSerialNo;
String differenceKind;
String caseSerialNo;
String causeName;
String userNote;
String place1;
String place1Name;
String adjustBookUnit;
String newBookUnit;


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
public String getPropertyNoName(){ return checkGet(propertyNoName); }
public void setPropertyNoName(String s){ propertyNoName=checkSet(s); }
public String getLotNo(){ return checkGet(lotNo); }
public void setLotNo(String s){ lotNo=checkSet(s); }
public String getSerialNo(){ return checkGet(serialNo); }

public void setSerialNo(String s){
	if (s!=null && s.length()>0) serialNo=checkSet(Common.formatFrontZero(s,7));
	else serialNo = ""; 
}
public String getPropertyName1(){ return checkGet(propertyName1); }
public void setPropertyName1(String s){ propertyName1=checkSet(s); }
public String getMaterial(){ return checkGet(material); }
public void setMaterial(String s){ material=checkSet(s); }
public String getOtherMaterial(){ return checkGet(otherMaterial); }
public void setOtherMaterial(String s){ otherMaterial=checkSet(s); }
public String getPropertyUnit(){ return checkGet(propertyUnit); }
public void setPropertyUnit(String s){ propertyUnit=checkSet(s); }
public String getOtherPropertyUnit(){ return checkGet(otherPropertyUnit); }
public void setOtherPropertyUnit(String s){ otherPropertyUnit=checkSet(s); }
public String getLimitYear(){ return checkGet(limitYear); }
public void setLimitYear(String s){ limitYear=checkSet(s); }
public String getOtherLimitYear(){ return checkGet(otherLimitYear); }
public void setOtherLimitYear(String s){ otherLimitYear=checkSet(s); }
public String getEnterDate(){ return checkGet(enterDate); }
public void setEnterDate(String s){ enterDate=checkSet(s); }
public String getBuyDate(){ return checkGet(buyDate); }
public void setBuyDate(String s){ buyDate=checkSet(s); }
public String getCause(){ return checkGet(cause); }
public void setCause(String s){ cause=checkSet(s); }
public String getCause1(){ return checkGet(cause1); }
public void setCause1(String s){ cause1=checkSet(s); }
public String getReduceDate(){ return checkGet(reduceDate); }
public void setReduceDate(String s){ reduceDate=checkSet(s); }
public String getNewEnterOrg(){ return checkGet(newEnterOrg); }
public void setNewEnterOrg(String s){ newEnterOrg=checkSet(s); }
public String getNewEnterOrgName(){ return checkGet(newEnterOrgName); }
public void setNewEnterOrgName(String s){ newEnterOrgName=checkSet(s); }
public String getTransferDate(){ return checkGet(transferDate); }
public void setTransferDate(String s){ transferDate=checkSet(s); }
public String getVerify(){ return checkGet(verify); }
public void setVerify(String s){ verify=checkSet(s); }
public String getPropertyKind(){ return checkGet(propertyKind); }
public void setPropertyKind(String s){ propertyKind=checkSet(s); }
public String getFundType(){ return checkGet(fundType); }
public void setFundType(String s){ fundType=checkSet(s); }
public String getValuable(){ return checkGet(valuable); }
public void setValuable(String s){ valuable=checkSet(s); }
public String getBookNotes(){ return checkGet(bookNotes); }
public void setBookNotes(String s){ bookNotes=checkSet(s); }
public String getAccountingTitle(){ return checkGet(accountingTitle); }
public void setAccountingTitle(String s){ accountingTitle=checkSet(s); }
public String getOldBookAmount(){ return checkGet(oldBookAmount); }
public void setOldBookAmount(String s){ oldBookAmount=checkSet(s); }
public String getOldBookUnit(){ return checkGet(oldBookUnit); }
public void setOldBookUnit(String s){ oldBookUnit=checkSet(s); }
public String getOldBookValue(){ return checkGet(oldBookValue); }
public void setOldBookValue(String s){ oldBookValue=checkSet(s); }
public String getAdjustBookAmount(){ return checkGet(adjustBookAmount); }
public void setAdjustBookAmount(String s){ adjustBookAmount=checkSet(s); }
public String getAdjustBookValue(){ return checkGet(adjustBookValue); }
public void setAdjustBookValue(String s){ adjustBookValue=checkSet(s); }
public String getNewBookAmount(){ return checkGet(newBookAmount); }
public void setNewBookAmount(String s){ newBookAmount=checkSet(s); }
public String getNewBookValue(){ return checkGet(newBookValue); }
public void setNewBookValue(String s){ newBookValue=checkSet(s); }
public String getArticleName(){ return checkGet(articleName); }
public void setArticleName(String s){ articleName=checkSet(s); }
public String getNameplate(){ return checkGet(nameplate); }
public void setNameplate(String s){ nameplate=checkSet(s); }
public String getSpecification(){ return checkGet(specification); }
public void setSpecification(String s){ specification=checkSet(s); }
public String getLicensePlate(){ return checkGet(licensePlate); }
public void setLicensePlate(String s){ licensePlate=checkSet(s); }
public String getMoveDate(){ return checkGet(moveDate); }
public void setMoveDate(String s){ moveDate=checkSet(s); }
public String getKeepUnit(){ return checkGet(keepUnit); }
public void setKeepUnit(String s){ keepUnit=checkSet(s); }
public String getKeepUnitName(){ return checkGet(keepUnitName); }
public void setKeepUnitName(String s){ keepUnitName=checkSet(s); }
public String getKeeper(){ return checkGet(keeper); }
public void setKeeper(String s){ keeper=checkSet(s); }
public String getKeeperName(){ return checkGet(keeperName); }
public void setKeeperName(String s){ keeperName=checkSet(s); }
public String getUseUnit(){ return checkGet(useUnit); }
public void setUseUnit(String s){ useUnit=checkSet(s); }
public String getUseUnitName(){ return checkGet(useUnitName); }
public void setUseUnitName(String s){ useUnitName=checkSet(s); }
public String getUserNo(){ return checkGet(userNo); }
public void setUserNo(String s){ userNo=checkSet(s); }
public String getUserName(){ return checkGet(userName); }
public void setUserName(String s){ userName=checkSet(s); }
public String getPlace(){ return checkGet(place); }
public void setPlace(String s){ place=checkSet(s); }
public String getReturnPlace(){ return checkGet(returnPlace); }
public void setReturnPlace(String s){ returnPlace=checkSet(s); }
public String getCause2(){ return checkGet(cause2); }
public void setCause2(String s){ cause2=checkSet(s); }
public String getDealCaseNo(){ return checkGet(dealCaseNo); }
public void setDealCaseNo(String s){ dealCaseNo=checkSet(s); }
public String getDealDate(){ return checkGet(dealDate); }
public void setDealDate(String s){ dealDate=checkSet(s); }
public String getReduceDeal(){ return checkGet(reduceDeal); }
public void setReduceDeal(String s){ reduceDeal=checkSet(s); }
public String getRealizeValue(){ return checkGet(realizeValue); }
public void setRealizeValue(String s){ realizeValue=checkSet(s); }
public String getShiftOrg(){ return checkGet(shiftOrg); }
public void setShiftOrg(String s){ shiftOrg=checkSet(s); }
public String getShiftOrgName(){ return checkGet(shiftOrgName); }   
public void setShiftOrgName(String s){ shiftOrgName=checkSet(s); }
public String getSourceDate(){ return checkGet(sourceDate); }
public void setSourceDate(String s){ sourceDate=checkSet(s); }
public String getUseYear(){ return checkGet(useYear); }
public void setUseYear(String s){ useYear=checkSet(s); }
public String getUseMonth(){ return checkGet(useMonth); }
public void setUseMonth(String s){ useMonth=checkSet(s); }
public String getNotes(){ return checkGet(notes); }
public void setNotes(String s){ notes=checkSet(s); }
public String getOldPropertyNo(){ return checkGet(oldPropertyNo); }
public void setOldPropertyNo(String s){ oldPropertyNo=checkSet(s); }
public String getOldPropertyName(){ return checkGet(oldPropertyName); }
public void setOldPropertyName(String s){ oldPropertyName=checkSet(s); }
public String getOldSerialNo(){ return checkGet(oldSerialNo); }
public void setOldSerialNo(String s){ oldSerialNo=checkSet(s); }
public String getDifferenceKind() {return checkGet(this.differenceKind);}
public void setDifferenceKind(String s) {differenceKind = checkSet(s);}
public String getCaseSerialNo() {return checkGet(caseSerialNo);}
public void setCaseSerialNo(String s) {this.caseSerialNo = checkSet(s);}
public String getCauseName() {return checkGet(causeName);}
public void setCauseName(String s) {this.causeName = checkSet(s);}
public String getUserNote() {return checkGet(userNote);}
public void setUserNote(String s) {this.userNote = checkSet(s);}
public String getPlace1() {return checkGet(place1);}
public void setPlace1(String s) {this.place1 = checkSet(s);}
public String getPlace1Name() {return checkGet(place1Name);}
public void setPlace1Name(String s) {this.place1Name = checkSet(s);}
public String getAdjustBookUnit() {	return checkGet(adjustBookUnit);}
public void setAdjustBookUnit(String s) {	this.adjustBookUnit = checkSet(s);}
public String getNewBookUnit() {return checkGet(newBookUnit);}
public void setNewBookUnit(String s) {this.newBookUnit = checkSet(s);}


String ownershipQuery;

public String getOwnershipQuery(){ return checkGet(ownershipQuery); }
public void setOwnershipQuery(String s){ ownershipQuery=checkSet(s); }


//傳回執行insert前之檢查sql
protected String[][] getInsertCheckSQL(){
	String[][] checkSQLArray = new String[5][4];
 	checkSQLArray[0][0]=" select count(*) as checkResult from UNTNE_ReduceDetail where 1=1 " + 
		" and enterOrg = " + Common.sqlChar(enterOrg) + 
		" and ownership = " + Common.sqlChar(ownership) +
		" and differencekind = " + Common.sqlChar(differenceKind) +
		" and propertyNo = " + Common.sqlChar(propertyNo) + 
		" and serialNo = " + Common.sqlChar(serialNo) +
		"";
	checkSQLArray[0][1]=">";
	checkSQLArray[0][2]="0";
	checkSQLArray[0][3]="該筆資料已重複，請重新輸入！";
	
 	checkSQLArray[1][0]=" select count(*) as checkResult from UNTNE_Nonexp where 1=1 " + 
		" and enterOrg = " + Common.sqlChar(enterOrg) + 
		" and ownership = " + Common.sqlChar(ownership) + 
		" and differencekind = " + Common.sqlChar(differenceKind) +
		" and propertyNo = " + Common.sqlChar(propertyNo) +
		" and lotno = " + Common.sqlChar(lotNo) +
		" and dataState = '1' " +
		" and verify = 'Y' " +
		"";
	checkSQLArray[1][1]="<=";
	checkSQLArray[1][2]="0";
	checkSQLArray[1][3]="基本資料中找不到該財產編號和批號，請重新輸入！";

 	checkSQLArray[2][0]=" select count(*) as checkResult from UNTNE_NonexpDetail where 1=1 " + 
		" and enterOrg = " + Common.sqlChar(enterOrg) + 
		" and ownership = " + Common.sqlChar(ownership) +
		" and differencekind = " + Common.sqlChar(differenceKind) + 
		" and propertyNo = " + Common.sqlChar(propertyNo) +
		" and serialNo = " + Common.sqlChar(serialNo) +
		" and dataState = '1' " +
		" and verify = 'Y' " +
		"";
	checkSQLArray[2][1]="<=";
	checkSQLArray[2][2]="0";
	checkSQLArray[2][3]="明細資料中找不到該財產編號和分號，請重新輸入！";
	
 	checkSQLArray[3][0]=" select count(*) as checkResult from UNTNE_AdjustDetail where 1=1 " + 
		" and enterOrg = " + Common.sqlChar(enterOrg) + 
		" and ownership = " + Common.sqlChar(ownership) + 
		" and differencekind = " + Common.sqlChar(differenceKind) +
		" and propertyNo = " + Common.sqlChar(propertyNo) + 
		" and serialNo = " + Common.sqlChar(serialNo) +
		" and verify = 'N' " +
		"";

	checkSQLArray[3][1]=">";
	checkSQLArray[3][2]="0";
	checkSQLArray[3][3]="非消耗品增減值作業存在未審核的資料，請重新輸入！";

	return checkSQLArray;
}


//傳回insert sql
protected String[] getInsertSQL(){
	//取得序號
	Database db = new Database();
	ResultSet rs;

	String sql="select ISNULL(max(caseSerialNo),0)+1 as caseSerialNo "+
		" from UNTNE_ReduceDetail " +
		" where enterOrg = " + Common.sqlChar(enterOrg) + 
		" and caseNo = " + Common.sqlChar(caseNo) +
		"";
	try {
		rs = db.querySQL(sql);
		if (rs.next()){
			setCaseSerialNo(Common.formatFrontZero(rs.getString("caseSerialNo"),5));
		} else {
			setCaseSerialNo("00001");
		}
	} catch (Exception e) {
		e.printStackTrace();
	} finally {
		db.closeAll();
	}
	
	UNTCH_Tools ul = new UNTCH_Tools();
	String[] execSQLArray = new String[1];
	execSQLArray[0]=" insert into UNTNE_ReduceDetail(" +
			" enterOrg,"+
			" ownership,"+
			" caseNo,"+
			" propertyNo,"+
			" lotNo,"+
			" serialNo,"+
			" propertyName1,"+	
			" otherMaterial,"+
			" otherPropertyUnit,"+
			" otherLimitYear,"+
			" enterDate,"+
			" buyDate,"+
			" cause,"+
			" cause1,"+
			" reduceDate,"+
			" newEnterOrg,"+
			" transferDate,"+
			" verify,"+
			" propertyKind,"+
			" fundType,"+
			" valuable,"+
			" bookNotes,"+
			" accountingTitle,"+
			" oldBookAmount,"+
			" oldBookUnit,"+
			" oldBookValue,"+
			" adjustBookAmount,"+
			" adjustBookValue,"+
			" newBookAmount,"+
			" newBookValue,"+
			" articleName,"+
			" nameplate,"+
			" specification,"+
			" licensePlate,"+
			" moveDate,"+
			" keepUnit,"+
			" keeper,"+
			" useUnit,"+
			" userNo,"+
			" place,"+
			" returnPlace,"+
			" cause2,"+
			" dealCaseNo,"+
			" dealDate,"+
			" reduceDeal,"+
			" realizeValue,"+
			" shiftOrg,"+
			" sourceDate,"+
			" useYear,"+
			" useMonth,"+
			" notes,"+
			" oldPropertyNo,"+
			" oldSerialNo,"+
			" editID,"+
			" editDate,"+
			" editTime,"+
			" differencekind,"+
			" caseSerialNo,"+
			" place1,"+
			" adjustBookUnit,"+
			" userNote"+
		" )Values(" +
			Common.sqlChar(this.getEnterOrg()) + "," +
			Common.sqlChar(this.getOwnership()) + "," +
			Common.sqlChar(this.getCaseNo()) + "," +
			Common.sqlChar(this.getPropertyNo()) + "," +
			Common.sqlChar(this.getLotNo()) + "," +
			Common.sqlChar(this.getSerialNo()) + "," +
			Common.sqlChar(this.getPropertyName1()) + "," +
			Common.sqlChar(this.getOtherMaterial()) + "," +
			Common.sqlChar(this.getOtherPropertyUnit()) + "," +
			Common.sqlChar(this.getOtherLimitYear()) + "," +
			Common.sqlChar(ul._transToCE_Year(this.getEnterDate())) + "," +
			Common.sqlChar(ul._transToCE_Year(this.getBuyDate())) + "," +
			Common.sqlChar(this.getCause()) + "," +
			Common.sqlChar(this.getCause1()) + "," +
			Common.sqlChar(this.getReduceDate()) + "," +
			Common.sqlChar(this.getNewEnterOrg()) + "," +
			Common.sqlChar(ul._transToCE_Year(this.getTransferDate())) + "," +
			Common.sqlChar(this.getVerify()) + "," +
			Common.sqlChar(Common.get(this.getPropertyKind())) + "," +
			Common.sqlChar(this.getFundType()) + "," +
			Common.sqlChar(this.getValuable()) + "," +
			Common.sqlChar(this.getBookNotes()) + "," +
			Common.sqlChar(this.getAccountingTitle()) + "," +
			Common.sqlChar(this.getOldBookAmount()) + "," +
			Common.sqlChar(this.getOldBookUnit()) + "," +
			Common.sqlChar(this.getOldBookValue()) + "," +
			Common.sqlChar(this.getAdjustBookAmount()) + "," +
			Common.sqlChar(this.getAdjustBookValue()) + "," +
			Common.sqlChar(this.getNewBookAmount()) + "," +
			Common.sqlChar(this.getNewBookValue()) + "," +
			Common.sqlChar(this.getArticleName()) + "," +
			Common.sqlChar(this.getNameplate()) + "," +
			Common.sqlChar(this.getSpecification()) + "," +
			Common.sqlChar(this.getLicensePlate()) + "," +
			Common.sqlChar(ul._transToCE_Year(this.getMoveDate())) + "," +
			Common.sqlChar(this.getKeepUnit()) + "," +
			Common.sqlChar(this.getKeeper()) + "," +
			Common.sqlChar(this.getUseUnit()) + "," +
			Common.sqlChar(this.getUserNo()) + "," +
			Common.sqlChar(this.getPlace()) + "," +
			Common.sqlChar(this.getReturnPlace()) + "," +
			Common.sqlChar(this.getCause2()) + "," +
			Common.sqlChar(this.getDealCaseNo()) + "," +
			Common.sqlChar(ul._transToCE_Year(this.getDealDate())) + "," +
			Common.sqlChar(this.getReduceDeal()) + "," +
			Common.sqlChar(this.getRealizeValue()) + "," +
			Common.sqlChar(this.getShiftOrg()) + "," +
			Common.sqlChar(ul._transToCE_Year(this.getSourceDate())) + "," +
			Common.sqlChar(this.getUseYear()) + "," +
			Common.sqlChar(this.getUseMonth()) + "," +
			Common.sqlChar(this.getNotes()) + "," +
			Common.sqlChar(this.getOldPropertyNo()) + "," +
			Common.sqlChar(this.getOldSerialNo()) + "," +
			Common.sqlChar(getEditID()) + "," +
			Common.sqlChar(ul._transToCE_Year(getEditDate())) + "," +
			Common.sqlChar(getEditTime()) + "," +
			Common.sqlChar(this.getDifferenceKind()) + "," +
			Common.sqlChar(getCaseSerialNo()) + "," +
			Common.sqlChar(this.getPlace1()) + "," +
			Common.sqlChar(this.getAdjustBookUnit()) + "," +
			Common.sqlChar(this.getUserNote()) + ")" ;
	return execSQLArray;
}


//傳回update sql
protected String[] getUpdateSQL(){
	UNTCH_Tools ul = new UNTCH_Tools();
	String[] execSQLArray = new String[1];
	execSQLArray[0]=" update UNTNE_ReduceDetail set " +
			" propertyName1 = " + Common.sqlChar(this.getPropertyName1()) + "," +
			" otherMaterial = " + Common.sqlChar(this.getOtherMaterial()) + "," +
			" otherPropertyUnit = " + Common.sqlChar(this.getOtherPropertyUnit()) + "," +
			" otherLimitYear = " + Common.sqlChar(this.getOtherLimitYear()) + "," +
			" enterDate = " + Common.sqlChar(ul._transToCE_Year(this.getEnterDate())) + "," +
			" buyDate = " + Common.sqlChar(ul._transToCE_Year(this.getBuyDate())) + "," +
			" cause = " + Common.sqlChar(this.getCause()) + "," +
			" cause1 = " + Common.sqlChar(this.getCause1()) + "," +
			" reduceDate = " + Common.sqlChar(ul._transToCE_Year(this.getReduceDate())) + "," +
			" newEnterOrg = " + Common.sqlChar(this.getNewEnterOrg()) + "," +
			" transferDate = " + Common.sqlChar(ul._transToCE_Year(this.getTransferDate())) + "," +
			" verify = " + Common.sqlChar(this.getVerify()) + "," +
			" propertyKind = " + Common.sqlChar(this.getPropertyKind()) + "," +
			" fundType = " + Common.sqlChar(this.getFundType()) + "," +
			" valuable = " + Common.sqlChar(this.getValuable()) + "," +
			" bookNotes = " + Common.sqlChar(this.getBookNotes()) + "," +
			" accountingTitle = " + Common.sqlChar(this.getAccountingTitle()) + "," +
			" oldBookAmount = " + Common.sqlChar(this.getOldBookAmount()) + "," +
			" oldBookUnit = " + Common.sqlChar(this.getOldBookUnit()) + "," +
			" oldBookValue = " + Common.sqlChar(this.getOldBookValue()) + "," +
			" adjustBookAmount = " + Common.sqlChar(this.getAdjustBookAmount()) + "," +
			" adjustBookValue = " + Common.sqlChar(this.getAdjustBookValue()) + "," +
			" newBookAmount = " + Common.sqlChar(this.getNewBookAmount()) + "," +
			" newBookValue = " + Common.sqlChar(this.getNewBookValue()) + "," +
			" articleName = " + Common.sqlChar(this.getArticleName()) + "," +
			" nameplate = " + Common.sqlChar(this.getNameplate()) + "," +
			" specification = " + Common.sqlChar(this.getSpecification()) + "," +
			" licensePlate = " + Common.sqlChar(this.getLicensePlate()) + "," +
			" moveDate = " + Common.sqlChar(ul._transToCE_Year(this.getMoveDate())) + "," +
			" keepUnit = " + Common.sqlChar(this.getKeepUnit()) + "," +
			" keeper = " + Common.sqlChar(this.getKeeper()) + "," +
			" useUnit = " + Common.sqlChar(this.getUseUnit()) + "," +
			" userNo = " + Common.sqlChar(this.getUserNo()) + "," +
			" place = " + Common.sqlChar(this.getPlace()) + "," +
			" returnPlace = " + Common.sqlChar(this.getReturnPlace()) + "," +
			" cause2 = " + Common.sqlChar(this.getCause2()) + "," +
			" dealCaseNo = " + Common.sqlChar(this.getDealCaseNo()) + "," +
			" dealDate = " + Common.sqlChar(ul._transToCE_Year(this.getDealDate())) + "," +
			" reduceDeal = " + Common.sqlChar(this.getReduceDeal()) + "," +
			" realizeValue = " + Common.sqlChar(this.getRealizeValue()) + "," +
			" shiftOrg  = " + Common.sqlChar(this.getShiftOrg()) + "," +
			" sourceDate = " + Common.sqlChar(ul._transToCE_Year(this.getSourceDate())) + "," +
			" useYear = " + Common.sqlChar(this.getUseYear()) + "," +
			" useMonth = " + Common.sqlChar(this.getUseMonth()) + "," +
			" notes = " + Common.sqlChar(this.getNotes()) + "," +
			" oldPropertyNo = " + Common.sqlChar(this.getOldPropertyNo()) + "," +
			" oldSerialNo = " + Common.sqlChar(this.getOldSerialNo()) + "," +
			" editID = " + Common.sqlChar(getEditID()) + "," +
			" editDate = " + Common.sqlChar(ul._transToCE_Year(getEditDate())) + "," +
			" editTime = " + Common.sqlChar(getEditTime()) + "," +
			" differenceKind = " + Common.sqlChar(differenceKind) + "," +
			" caseSerialNo = " + Common.sqlChar(this.getCaseSerialNo()) + "," +
			" place1 = " + Common.sqlChar(this.getPlace1()) + "," +
			" userNote = " + Common.sqlChar(this.getUserNote()) +
		" where 1=1 " + 
			" and enterOrg = " + Common.sqlChar(enterOrg) +
			" and ownership = " + Common.sqlChar(ownership) +
			" and caseNo = " + Common.sqlChar(caseNo) +
			" and propertyNo = " + Common.sqlChar(propertyNo) +
			" and serialNo = " + Common.sqlChar(serialNo) +
			" and differenceKind = " + Common.sqlChar(differenceKind) +
			"";
	return execSQLArray;
}


//傳回delete sql
protected String[] getDeleteSQL(){
	String[] execSQLArray = new String[1];
	execSQLArray[0]=" delete UNTNE_ReduceDetail where 1=1 " +
			" and enterOrg = " + Common.sqlChar(enterOrg) +
			" and ownership = " + Common.sqlChar(ownership) +
			" and caseNo = " + Common.sqlChar(caseNo) +
			" and propertyNo = " + Common.sqlChar(propertyNo) +
			" and serialNo = " + Common.sqlChar(serialNo) +
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

public UNTNE014F  queryOne() throws Exception{
	UNTCH_Tools ul = new UNTCH_Tools();
	Database db = new Database();
	UNTNE014F obj = this;
	try {
		String sql=
			" select a.enterorg, a.ownership, a.caseno, a.propertyno,a.lotno,a.differencekind,a.caseserialno,a.usernote,a.place1, " +
			" a.serialno, a.propertyname1, a.cause, a.cause1, a.reducedate, " +
			" a.enterdate, a.buydate,"+
			" a.newenterorg, a.transferdate, a.verify, a.propertykind, a.fundtype, " + 
			" a.valuable, " +
			" a.booknotes, a.accountingtitle, " +
			" a.oldbookamount , a.oldbookunit, a.oldbookvalue , "+
			" a.adjustbookamount , a.adjustbookvalue , "+
			" a.newbookamount , a.newbookvalue , "+
			" a.articlename,a.nameplate,a.specification,a.licenseplate,"+
			" a.movedate,a.keepunit,a.keeper,a.useunit,a.userno,a.place,"+
			" (select x.codename from SYSCA_CODE x where x.codeid = a.cause and x.codekindid = 'CAA' and codecon1 in('2','4')) as causename," +
			" (select f.unitname from UNTMP_KEEPUNIT f where a.enterorg=f.enterorg and a.keepunit=f.unitno) as keepunitname, "+
			" (select sp.placename from SYSCA_PLACE sp where a.enterorg=sp.enterorg and a.place1=sp.placeno) as place1Name, "+
			" (select f.unitname from UNTMP_KEEPUNIT f where a.enterorg=f.enterorg and a.useunit=f.unitno) as useunitname, "+
			" (select f.keepername from UNTMP_KEEPER f where a.enterorg=f.enterorg and a.keeper=f.keeperno) as keepername, "+		
			" (select f.keepername from UNTMP_KEEPER f where a.enterorg=f.enterorg and a.userno=f.keeperno) as username, "+		
			" a.returnplace,a.cause2,a.dealcaseno,a.dealdate,"+
			" a.reducedeal,a.realizevalue,a.shiftorg, "+
			" a.sourcedate, a.useyear,a.usemonth, "+
			" a.notes, a.oldpropertyno, a.oldserialno, a.editid, a.editdate, " +
			" a.edittime , " + 
			" c.organsname as enterorgname, d.propertyname, " +
			" d.material, a.othermaterial, d.propertyunit, a.otherpropertyunit, d.limityear, a.otherlimityear " +
			" from UNTNE_REDUCEDETAIL a"+
			" left join SYSPK_PROPERTYCODE e on a.oldpropertyno = e.propertyno,"+
			" SYSCA_ORGAN c, "+
			" SYSPK_PROPERTYCODE2 d where 1=1 " +
			" and a.enterorg    = " + Common.sqlChar(enterOrg) +
			" and a.ownership   = " + Common.sqlChar(ownership) +
			" and a.caseno 	= " + Common.sqlChar(caseNo) +
			" and a.propertyno  = " + Common.sqlChar(propertyNo) +
			" and a.serialno    = " + Common.sqlChar(serialNo) +
			" and a.differencekind    = " + Common.sqlChar(differenceKind) +
			" and a.enterorg	= c.organid " +
			" and a.enterorg = d.enterorg " +
			" and a.propertyno 	= d.propertyno "+
			" order by a.enterorg , a.ownership  "+
			"";
		ResultSet rs = db.querySQL_NoChange(sql);
		if (rs.next()){
			obj.setEnterOrg(rs.getString("enterOrg"));
			obj.setEnterOrgName(rs.getString("enterOrgName"));
			obj.setOwnership(rs.getString("ownership"));
			obj.setCaseNo(rs.getString("caseNo"));
			obj.setPropertyNo(rs.getString("propertyNo"));
			obj.setPropertyNoName(rs.getString("propertyName"));
			obj.setLotNo(rs.getString("lotNo"));
			obj.setSerialNo(rs.getString("serialNo"));
			obj.setPropertyName1(rs.getString("propertyName1"));
			obj.setMaterial(rs.getString("material"));
			obj.setOtherMaterial(rs.getString("otherMaterial"));
			obj.setPropertyUnit(rs.getString("propertyUnit"));
			obj.setOtherPropertyUnit(rs.getString("otherPropertyUnit"));
			obj.setLimitYear(rs.getString("limitYear"));
			obj.setOtherLimitYear(rs.getString("otherLimitYear"));
			obj.setEnterDate(ul._transToROC_Year(rs.getString("enterDate")));
			obj.setBuyDate(ul._transToROC_Year(rs.getString("buyDate")));
			obj.setCause(rs.getString("cause"));
			obj.setCauseName(rs.getString("causename"));
			obj.setCause1(rs.getString("cause1"));
			obj.setReduceDate(ul._transToROC_Year(rs.getString("reduceDate")));
			obj.setNewEnterOrg(rs.getString("newEnterOrg"));
			obj.setNewEnterOrgName(getLookupName("select organSName from SYSCA_ORGAN where organID='" + rs.getString("newEnterOrg") + "'"));
			obj.setTransferDate(ul._transToROC_Year(rs.getString("transferDate")));
			obj.setVerify(rs.getString("verify"));
			obj.setPropertyKind(rs.getString("propertyKind"));
			obj.setFundType(rs.getString("fundType"));
			obj.setValuable(rs.getString("valuable"));
			obj.setBookNotes(rs.getString("bookNotes"));
			obj.setAccountingTitle(rs.getString("accountingTitle"));
			obj.setOldBookAmount(rs.getString("oldBookAmount"));
			obj.setOldBookUnit(rs.getString("oldBookUnit"));
			obj.setOldBookValue(rs.getString("oldBookValue"));
			obj.setAdjustBookAmount(rs.getString("adjustBookAmount"));
			obj.setAdjustBookValue(rs.getString("adjustBookValue"));
			obj.setNewBookAmount(rs.getString("newBookAmount"));
			obj.setNewBookValue(rs.getString("newBookValue"));
			obj.setArticleName(rs.getString("articleName"));
			obj.setNameplate(rs.getString("nameplate"));
			obj.setSpecification(rs.getString("specification"));
			obj.setLicensePlate(rs.getString("licensePlate"));
			obj.setMoveDate(ul._transToROC_Year(rs.getString("moveDate")));
			obj.setKeepUnit(rs.getString("keepUnit"));
			obj.setKeepUnitName(rs.getString("keepUnitName"));
			obj.setKeeper(rs.getString("keeper"));
			obj.setKeeperName(rs.getString("keeperName"));
			obj.setUseUnit(rs.getString("useUnit"));
			obj.setUseUnitName(rs.getString("useUnitName"));
			obj.setUserNo(rs.getString("userNo"));
			obj.setUserName(rs.getString("userName"));
			obj.setPlace(rs.getString("place"));
			obj.setReturnPlace(rs.getString("returnPlace"));
			obj.setCause2(rs.getString("cause2"));
			obj.setDealCaseNo(rs.getString("dealCaseNo"));
			obj.setDealDate(ul._transToROC_Year(rs.getString("dealDate")));
			obj.setReduceDeal(rs.getString("reduceDeal"));
			obj.setRealizeValue(rs.getString("realizeValue"));
			obj.setShiftOrg(rs.getString("shiftOrg"));
			obj.setSourceDate(ul._transToROC_Year(rs.getString("sourceDate")));
			obj.setUseYear(rs.getString("useYear"));
			obj.setUseMonth(rs.getString("useMonth"));
			obj.setNotes(rs.getString("notes"));
			obj.setOldPropertyNo(rs.getString("oldPropertyNo"));
			obj.setOldPropertyName(rs.getString("oldpropertyNo"));
			obj.setOldSerialNo(rs.getString("oldSerialNo"));
			obj.setEditID(rs.getString("editID"));
			obj.setEditDate(ul._transToROC_Year(rs.getString("editDate")));
			obj.setEditTime(rs.getString("editTime"));
			obj.setDifferenceKind(rs.getString("differenceKind"));
			obj.setCaseSerialNo(rs.getString("caseSerialNo"));
			obj.setUserNote(rs.getString("userNote"));
			obj.setPlace1Name(rs.getString("place1Name"));
			obj.setPlace1(rs.getString("place1"));
		}
		setStateQueryOneSuccess();
		
		queryProofNo();
	} catch (Exception e) {
		e.printStackTrace();
	} finally {
		db.closeAll();
	}
	return obj;
} 
	
	public void updateNewData() throws Exception{
		try{
			Database db = new Database();			
			String sqlne = "update a" +	
					  " set a.useyear = (DateDiff(month, a.buydate , b.writedate))/12, " +
					  " a.usemonth = (DateDiff(month, a.buydate , b.writedate))%12 " +
					  " from UNTNE_REDUCEDETAIL a, UNTNE_REDUCEPROOF b" +
					  " where 1=1" +
					  " and a.enterorg = b.enterorg  and a.caseno = b.caseno and a.differencekind = b.differencekind and a.ownership = b.ownership" +
					  " and a.enterorg = " + Common.sqlChar(getEnterOrg()) +
					  " and a.propertyno = " + Common.sqlChar(getPropertyNo()) +
					  " and a.serialno = " + Common.sqlChar(getSerialNo()) +
					  " and a.caseno = " +  Common.sqlChar(getCaseNo()) +
					  " and a.verify = 'N' " +
					  " ;";
		
			db.excuteSQL(sqlne);
			setStateUpdateSuccess();
			setErrorMsg("帶入最新資料完成");
		}catch(Exception e){
			setStateUpdateError();
			setErrorMsg("帶入最新資料失敗!若問題持續,請洽詢系統管理者或相關承辦人員！");
		}
	}

	private void queryProofNo(){
		String sql = "select proofno from UNTNE_REDUCEPROOF where 1=1" +
						" and enterorg = " + Common.sqlChar(this.getEnterOrg()) +
						" and ownership = " + Common.sqlChar(this.getOwnership()) +
						" and differencekind = " + Common.sqlChar(this.getDifferenceKind()) +
						" and caseno = " + Common.sqlChar(this.getCaseNo());
		
		Database db = null;
		ResultSet rs = null;
		try{
			db = new Database();
			rs = db.querySQL(sql);
			if(rs.next()){
				this.setProofNo(rs.getString(1));
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			db.closeAll();
		}
	}

/**
 * <br>
 * <br>目的：依查詢欄位查詢多筆資料
 * <br>參數：無
 * <br>傳回：傳回ArrayList
*/

public ArrayList queryAll() throws Exception{
	UNTCH_Tools ul = new UNTCH_Tools();
	Database db = new Database();
	ArrayList objList=new ArrayList();
	int counter=0; 
	try {
		String sql= " select a.enterOrg,o.organSName, a.ownership,a.differenceKind,a.caseSerialNo,a.lotno," +
					" (select z.codeName from sysca_code z where 1=1 and (z.codeKindID='owa' or z.codeKindID='OWA') and a.ownership=z.codeID) as ownershipName, " +
					" a.caseNo, a.propertyNo, d.propertyName, a.lotNo, a.serialNo, " +
					" c.codeName cause, a.otherLimitYear , a.enterDate , a.buyDate, "+
					" (case a.propertyKind when '01' then '公務用' when '02' then '公共用' when '03' then '事業用' when '04' then '非公用' else '' end) propertyKind,"+
					" a.articleName,a.nameplate,a.specification,a.licensePlate,"+
					" a.moveDate,a.keepUnit,a.keeper,a.useUnit,a.userNo,"+
					" a.place,a.returnPlace,a.cause2, a.dealCaseNo," +
					" a.adjustBookAmount, a.adjustBookValue, "+
					" a.dealDate,a.realizeValue,a.shiftOrg, a.sourceDate, " +
					" (select f.codeName from SYSCA_Code f where 1=1 and f.codeKindID='RDL' and a.reduceDeal = f.codeID) as reduceDeal, "+
					" (select e.keeperName from UNTMP_Keeper e where a.enterOrg=e.enterOrg  and a.keeper=e.keeperNo) as keeperName " +
					" from UNTNE_REDUCEDETAIL a"+
					" left join UNTNE_REDUCEPROOF b on a.enterOrg=b.enterOrg and a.caseNo=b.caseNo "+
					" left join SYSCA_CODE c on a.cause = c.codeID and c.codeKindID='CAA' " +
					" left join SYSPK_PROPERTYCODE2 d on a.enterOrg = d.enterOrg and a.propertyNo=d.propertyNo " +
					" left join SYSCA_ORGAN o on a.enterOrg = o.organID where 1=1 " +
					""; 
		if ("AddProof".equals(getQuerySelect()) || "".equals(getQuerySelect())) {
			sql+=" and a.enterOrg = " + Common.sqlChar(getEnterOrg()) ;
			if(!"".equals(getOwnership())){
			    sql+=" and a.ownership = " + Common.sqlChar(getOwnership()) ;
			}else{
			    sql+=" and a.ownership = " + Common.sqlChar(getOwnershipQuery()) ;
			}
			sql+=" and a.caseNo=" + Common.sqlChar(getCaseNo());
		} else {
			if (!"".equals(getQ_enterOrg())) {
				sql+=" and a.enterOrg = " + Common.sqlChar(getQ_enterOrg()) ;
			} else {
				if (!getIsAdminManager().equalsIgnoreCase("Y")) {
					if ("Y".equals(getIsOrganManager())) {					
						sql += " and a.enterOrg like '" + getOrganID().substring(0,5) + "%' ";					
					} else {
						sql+=" and a.enterOrg = " + Common.sqlChar(getOrganID()) ;
					}
				}
			}
			if (!"".equals(getQ_ownership()))
				sql+=" and a.ownership = " + Common.sqlChar(getQ_ownership()) ;
			if (!"".equals(getQ_caseNoS()))
				sql+=" and a.caseNo >= " + Common.sqlChar(Common.formatFrontZero(getQ_caseNoS(),10));
			if (!"".equals(getQ_caseNoE()))
				sql+=" and a.caseNo <= " + Common.sqlChar(Common.formatFrontZero(getQ_caseNoE(),10));
			if (!"".equals(getQ_verify()))
				sql+=" and b.verify = " + Common.sqlChar(getQ_verify()) ;
			if (!"".equals(getQ_reduceDateS()))
				sql+=" and b.reduceDate >= " + Common.sqlChar(ul._transToCE_Year(getQ_reduceDateS())) ;
			if (!"".equals(getQ_reduceDateE()))
				sql+=" and b.reduceDate <= " + Common.sqlChar(ul._transToCE_Year(getQ_reduceDateE())) ;
			if (!"".equals(getQ_caseName()))
				sql+=" and b.caseName like " + Common.sqlChar(getQ_caseName()+"%") ;
			if (!"".equals(getQ_proofDoc()))
				sql+=" and b.proofDoc like " + Common.sqlChar(getQ_proofDoc()+"%") ;
			if (!"".equals(getQ_proofNoS()))
				sql+=" and b.proofNo >= " + Common.sqlChar(getQ_proofNoS()) ;
			if (!"".equals(getQ_proofNoE()))
				sql+=" and b.proofNo <= " + Common.sqlChar(getQ_proofNoE()) ;
			if (!"".equals(getQ_writeDateS()))
				sql+=" and b.writeDate >= " + Common.sqlChar(ul._transToCE_Year(getQ_writeDateS())) ;
			if (!"".equals(getQ_writeDateE()))
				sql+=" and b.writeDate <= " + Common.sqlChar(ul._transToCE_Year(getQ_writeDateE()));    
			if (!"".equals(getQ_approveOrg()))
				sql+=" and b.approveOrg = " + Common.sqlChar(getQ_approveOrg()) ;
			if (!"".equals(getQ_cause()))
				sql+=" and a.cause = " + Common.sqlChar(getQ_cause()) ;			
			if (!"".equals(getQ_newEnterOrg()))
				sql+=" and a.newEnterOrg = " + Common.sqlChar(getQ_newEnterOrg()) ;
			if (!"".equals(getQ_propertyNoS()))
				sql+=" and a.propertyNo >= " + Common.sqlChar(getQ_propertyNoS());		
			if (!"".equals(getQ_propertyNoE()))
				sql+=" and a.propertyNo <= " + Common.sqlChar(getQ_propertyNoE());						
			if (!"".equals(getQ_serialNoS()))
				sql+=" and a.serialNo >= " + Common.sqlChar(getQ_serialNoS());		
			if (!"".equals(getQ_serialNoE()))
				sql+=" and a.serialNo <= " + Common.sqlChar(getQ_serialNoE());			
			if (!"".equals(getQ_propertyKind()))
				sql+=" and a.propertyKind = " + Common.sqlChar(getQ_propertyKind());
			if (!"".equals(getQ_fundType()))
				sql+=" and a.fundType = " + Common.sqlChar(getQ_fundType()) ;
			if (!"".equals(getQ_valuable()))
				sql+=" and a.valuable = " + Common.sqlChar(getQ_valuable()) ;
			if (!"".equals(getQ_dealCaseNo()))
				sql+=" and a.dealCaseNo = " + Common.sqlChar(Common.formatFrontZero(getQ_dealCaseNo(),10)) ;
			if (!"".equals(getQ_reduceDeal()))
				sql+=" and a.reduceDeal = " + Common.sqlChar(getQ_reduceDeal()) ;
			if (!"".equals(getQ_shiftOrg()))
				sql+=" and a.shiftOrg = " + Common.sqlChar(getQ_shiftOrg()) ;
			if (!"".equals(getQ_differenceKind()))
				sql+=" and a.differencekind = " + Common.sqlChar(getQ_differenceKind()) ;
			if (!"".equals(getQ_proofYear()))
				sql+=" and b.proofyear = " + Common.sqlChar(getQ_proofYear()) ;
			if (!"".equals(getQ_userNote()))
				sql+=" and a.userNote like " + Common.sqlChar("%"+getQ_userNote()+"%") ;
			if (!"".equals(getQ_keepUnit()))
				sql+=" and a.keepUnit = " + Common.sqlChar(getQ_keepUnit()) ;	    
			if (!"".equals(getQ_keeper()))
				sql+=" and a.keeper = " + Common.sqlChar(getQ_keeper()) ;	    
			if (!"".equals(getQ_useUnit()))
				sql+=" and a.useUnit = " + Common.sqlChar(getQ_useUnit()) ;	    
			if (!"".equals(getQ_userNo()))
				sql+=" and a.userNo = " + Common.sqlChar(getQ_userNo()) ;	   
			if(!"".equals(getQ_place1()))
				sql+=" and a.place1 like " + Common.sqlChar("%" + getQ_place1() + "%");
		}
		if ("1".equals(this.getRoleid())){
			sql += " and ( a.keeper = " + Common.sqlChar(this.getKeeperno())+
						 " or b.editid = " + Common.sqlChar(this.getUserID()) +
						 " )";				
		}else if ("2".equals(this.getRoleid())){
			sql += " and ( a.keepunit = " + Common.sqlChar(this.getUnitID())+
						 " or a.keeper = " + Common.sqlChar(this.getKeeperno())+
						 " or b.editid = " + Common.sqlChar(this.getUserID()) +
						 " )";				
		}
		sql+=" order by a.enterOrg, a.ownership, a.propertyNo, a.serialNo " ;
		ResultSet rs = db.querySQL(sql);
		while (rs.next()){
			counter++;
			String rowArray[]=new String[19];
			rowArray[0]=rs.getString("enterOrg");
			rowArray[1]=rs.getString("organSName"); 
			rowArray[2]=rs.getString("ownership");
			rowArray[3]=rs.getString("ownershipName"); 
			rowArray[4]=rs.getString("caseNo");
			rowArray[5]=rs.getString("caseSerialNo");
			rowArray[6]=rs.getString("propertyNo");
			rowArray[7]=rs.getString("propertyName");
			rowArray[8]=rs.getString("serialNo");
			rowArray[9]=rs.getString("keeperName");
			rowArray[10]=rs.getString("cause"); 
			rowArray[11]=rs.getString("propertyKind"); 
			rowArray[12]=rs.getString("adjustBookAmount");
			rowArray[13]=rs.getString("adjustBookValue");
			rowArray[14]=rs.getString("keepUnit");
			rowArray[15]=rs.getString("keeper");
			rowArray[16]=rs.getString("place");
			rowArray[17]=rs.getString("differenceKind");
			rowArray[18]=rs.getString("reduceDeal");
			objList.add(rowArray);
			if (counter > getListLimit()){
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


public String getLookupName(String sSQL) throws Exception {
	Database db = new Database();
	String sStr = "";
	try {
		ResultSet rs = db.querySQL(sSQL);
		while (rs.next()) {
			sStr = rs.getString(1);			
		}
	} catch (Exception e) {
		e.printStackTrace();
	} finally {
		db.closeAll();
	}			
	return sStr;
}

}