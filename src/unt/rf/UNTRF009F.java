/*
*<br>程式目的：土地改良物減少作業－減損單明細
*<br>程式代號：untrf009f
*<br>程式日期：0941007
*<br>程式作者：judy
*<br>--------------------------------------------------------
*<br>修改作者　　修改日期　　　修改目的
*<br>--------------------------------------------------------
*/ 

package unt.rf;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import TDlib_Simple.com.src.SQLCreator;
import unt.ch.UNTCH_Tools;
import util.*;
import util.NewDateUtil.DateFormat;

public class UNTRF009F extends UNTRF008Q{

	private String causeName;
	public String getCauseName() {return checkGet(causeName);}
	public void setCauseName(String causeName) {this.causeName = checkSet(causeName);}

	private String enterOrg;
	private String caseNo;
	private String ownership;
	private String differenceKind;
	private String engineeringNo;
	private String caseSerialNo;
	private String propertyNo;
	private String serialNo;
	private String otherLimitYear;
	private String propertyName1;
	private String cause;
	private String cause1;
	private String reduceDate;
	private String newEnterOrg;
	private String transferDate;
	private String verify;
	private String propertyKind;
	private String fundType;
	private String valuable;
	private String taxCredit;
	private String measure;
	private String bookNotes;
	private String accountingTitle;
	private String bookValue;
	private String netValue;
	private String buildDate;
	private String sourceDate;
	private String buyDate;
	private String useYear;
	private String useMonth;
	private String oldDeprMethod;
	private String oldBuildFeeCB;
	private String oldDeprUnitCB;
	private String oldDeprPark;
	private String oldDeprUnit;
	private String oldDeprUnit1;
	private String oldDeprAccounts;
	private String oldScrapValue;
	private String oldDeprAmount;
	private String oldAccumDepr;
	private String oldApportionMonth;
	private String oldMonthDepr;
	private String newDeprMethod;
	private String newBuildFeeCB;
	private String newDeprUnitCB;
	private String newDeprPark;
	private String newDeprUnit;
	private String newDeprUnit1;
	private String newDeprAccounts;
	private String newScrapValue;
	private String newDeprAmount;
	private String newAccumDepr;
	private String newApportionMonth;
	private String newMonthDepr;
	private String cause2;
	private String reduceDeal2;
	private String moveDate;
	private String keepUnit;
	private String keeper;
	private String useUnit;
	private String userNo;
	private String userNote;
	private String place1;
	private String place;
	private String oldPropertyNo;
	private String oldSerialNo;
	private String newEnterOrgReceive;
	private String notes;
	private String editID;
	private String editDate;
	private String editTime;

	private String enterOrgName;
	private String differenceKindName;
	private String engineeringNoName;
	private String propertyNoName;
	

	private String newEnterOrgName;
	private String place1Name;
	public String getNewEnterOrgName() {return checkGet(newEnterOrgName);}
	public void setNewEnterOrgName(String newEnterOrgName) {this.newEnterOrgName = checkSet(newEnterOrgName);}
	public String getPlace1Name() {return checkGet(place1Name);}
	public void setPlace1Name(String place1Name) {this.place1Name = checkSet(place1Name);}
	public String getEnterOrg() {return checkGet(enterOrg);}
	public void setEnterOrg(String enterOrg) {this.enterOrg = checkSet(enterOrg);}
	public String getCaseNo() {return checkGet(caseNo);}
	public void setCaseNo(String caseNo) {this.caseNo = checkSet(caseNo);}
	public String getOwnership() {return checkGet(ownership);}
	public void setOwnership(String ownership) {this.ownership = checkSet(ownership);}
	public String getDifferenceKind() {return checkGet(differenceKind);}
	public void setDifferenceKind(String differenceKind) {this.differenceKind = checkSet(differenceKind);}
	public String getEngineeringNo() {return checkGet(engineeringNo);}
	public void setEngineeringNo(String engineeringNo) {this.engineeringNo = checkSet(engineeringNo);}
	public String getCaseSerialNo() {return checkGet(caseSerialNo);}
	public void setCaseSerialNo(String caseSerialNo) {this.caseSerialNo = checkSet(caseSerialNo);}
	public String getPropertyNo() {return checkGet(propertyNo);}
	public void setPropertyNo(String propertyNo) {this.propertyNo = checkSet(propertyNo);}
	public String getSerialNo() {return checkGet(serialNo);}
	public void setSerialNo(String serialNo) {this.serialNo = checkSet(serialNo);}
	public String getOtherLimitYear() {return checkGet(otherLimitYear);}
	public void setOtherLimitYear(String otherLimitYear) {this.otherLimitYear = checkSet(otherLimitYear);}
	public String getPropertyName1() {return checkGet(propertyName1);}
	public void setPropertyName1(String propertyName1) {this.propertyName1 = checkSet(propertyName1);}
	public String getCause() {return checkGet(cause);}
	public void setCause(String cause) {this.cause = checkSet(cause);}
	public String getCause1() {return checkGet(cause1);}
	public void setCause1(String cause1) {this.cause1 = checkSet(cause1);}
	public String getReduceDate() {return checkGet(reduceDate);}
	public void setReduceDate(String reduceDate) {this.reduceDate = checkSet(reduceDate);}
	public String getNewEnterOrg() {return checkGet(newEnterOrg);}
	public void setNewEnterOrg(String newEnterOrg) {this.newEnterOrg = checkSet(newEnterOrg);}
	public String getTransferDate() {return checkGet(transferDate);}
	public void setTransferDate(String transferDate) {this.transferDate = checkSet(transferDate);}
	public String getVerify() {return checkGet(verify);}
	public void setVerify(String verify) {this.verify = checkSet(verify);}
	public String getPropertyKind() {return checkGet(propertyKind);}
	public void setPropertyKind(String propertyKind) {this.propertyKind = checkSet(propertyKind);}
	public String getFundType() {return checkGet(fundType);}
	public void setFundType(String fundType) {this.fundType = checkSet(fundType);}
	public String getValuable() {return checkGet(valuable);}
	public void setValuable(String valuable) {this.valuable = checkSet(valuable);}
	public String getTaxCredit() {return checkGet(taxCredit);}
	public void setTaxCredit(String taxCredit) {this.taxCredit = checkSet(taxCredit);}
	public String getMeasure() {return checkGet(measure);}
	public void setMeasure(String measure) {this.measure = checkSet(measure);}
	public String getBookNotes() {return checkGet(bookNotes);}
	public void setBookNotes(String bookNotes) {this.bookNotes = checkSet(bookNotes);}
	public String getAccountingTitle() {return checkGet(accountingTitle);}
	public void setAccountingTitle(String accountingTitle) {this.accountingTitle = checkSet(accountingTitle);}
	public String getBookValue() {return checkGet(bookValue);}
	public void setBookValue(String bookValue) {this.bookValue = checkSet(bookValue);}
	public String getNetValue() {return checkGet(netValue);}
	public void setNetValue(String netValue) {this.netValue = checkSet(netValue);}
	public String getBuildDate() {return checkGet(buildDate);}
	public void setBuildDate(String buildDate) {this.buildDate = checkSet(buildDate);}
	public String getSourceDate() {return checkGet(sourceDate);}
	public void setSourceDate(String sourceDate) {this.sourceDate = checkSet(sourceDate);}
	public String getBuyDate() {return checkGet(buyDate);}
	public void setBuyDate(String buyDate) {this.buyDate = checkSet(buyDate);}
	public String getUseYear() {return checkGet(useYear);}
	public void setUseYear(String useYear) {this.useYear = checkSet(useYear);}
	public String getUseMonth() {return checkGet(useMonth);}
	public void setUseMonth(String useMonth) {this.useMonth = checkSet(useMonth);}
	public String getOldDeprMethod() {return checkGet(oldDeprMethod);}
	public void setOldDeprMethod(String oldDeprMethod) {this.oldDeprMethod = checkSet(oldDeprMethod);}
	public String getOldBuildFeeCB() {return checkGet(oldBuildFeeCB);}
	public void setOldBuildFeeCB(String oldBuildFeeCB) {this.oldBuildFeeCB = checkSet(oldBuildFeeCB);}
	public String getOldDeprUnitCB() {return checkGet(oldDeprUnitCB);}
	public void setOldDeprUnitCB(String oldDeprUnitCB) {this.oldDeprUnitCB = checkSet(oldDeprUnitCB);}
	public String getOldDeprPark() {return checkGet(oldDeprPark);}
	public void setOldDeprPark(String oldDeprPark) {this.oldDeprPark = checkSet(oldDeprPark);}
	public String getOldDeprUnit() {return checkGet(oldDeprUnit);}
	public void setOldDeprUnit(String oldDeprUnit) {this.oldDeprUnit = checkSet(oldDeprUnit);}
	public String getOldDeprUnit1() {return checkGet(oldDeprUnit1);}
	public void setOldDeprUnit1(String oldDeprUnit1) {this.oldDeprUnit1 = checkSet(oldDeprUnit1);}
	public String getOldDeprAccounts() {return checkGet(oldDeprAccounts);}
	public void setOldDeprAccounts(String oldDeprAccounts) {this.oldDeprAccounts = checkSet(oldDeprAccounts);}
	public String getOldScrapValue() {return checkGet(oldScrapValue);}
	public void setOldScrapValue(String oldScrapValue) {this.oldScrapValue = checkSet(oldScrapValue);}
	public String getOldDeprAmount() {return checkGet(oldDeprAmount);}
	public void setOldDeprAmount(String oldDeprAmount) {this.oldDeprAmount = checkSet(oldDeprAmount);}
	public String getOldAccumDepr() {return checkGet(oldAccumDepr);}
	public void setOldAccumDepr(String oldAccumDepr) {this.oldAccumDepr = checkSet(oldAccumDepr);}
	public String getOldApportionMonth() {return checkGet(oldApportionMonth);}
	public void setOldApportionMonth(String oldApportionMonth) {this.oldApportionMonth = checkSet(oldApportionMonth);}
	public String getOldMonthDepr() {return checkGet(oldMonthDepr);}
	public void setOldMonthDepr(String oldMonthDepr) {this.oldMonthDepr = checkSet(oldMonthDepr);}
	public String getNewDeprMethod() {return checkGet(newDeprMethod);}
	public void setNewDeprMethod(String newDeprMethod) {this.newDeprMethod = checkSet(newDeprMethod);}
	public String getNewBuildFeeCB() {return checkGet(newBuildFeeCB);}
	public void setNewBuildFeeCB(String newBuildFeeCB) {this.newBuildFeeCB = checkSet(newBuildFeeCB);}
	public String getNewDeprUnitCB() {return checkGet(newDeprUnitCB);}
	public void setNewDeprUnitCB(String newDeprUnitCB) {this.newDeprUnitCB = checkSet(newDeprUnitCB);}
	public String getNewDeprPark() {return checkGet(newDeprPark);}
	public void setNewDeprPark(String newDeprPark) {this.newDeprPark = checkSet(newDeprPark);}
	public String getNewDeprUnit() {return checkGet(newDeprUnit);}
	public void setNewDeprUnit(String newDeprUnit) {this.newDeprUnit = checkSet(newDeprUnit);}
	public String getNewDeprUnit1() {return checkGet(newDeprUnit1);}
	public void setNewDeprUnit1(String newDeprUnit1) {this.newDeprUnit1 = checkSet(newDeprUnit1);}
	public String getNewDeprAccounts() {return checkGet(newDeprAccounts);}
	public void setNewDeprAccounts(String newDeprAccounts) {this.newDeprAccounts = checkSet(newDeprAccounts);}
	public String getNewScrapValue() {return checkGet(newScrapValue);}
	public void setNewScrapValue(String newScrapValue) {this.newScrapValue = checkSet(newScrapValue);}
	public String getNewDeprAmount() {return checkGet(newDeprAmount);}
	public void setNewDeprAmount(String newDeprAmount) {this.newDeprAmount = checkSet(newDeprAmount);}
	public String getNewAccumDepr() {return checkGet(newAccumDepr);}
	public void setNewAccumDepr(String newAccumDepr) {this.newAccumDepr = checkSet(newAccumDepr);}
	public String getNewApportionMonth() {return checkGet(newApportionMonth);}
	public void setNewApportionMonth(String newApportionMonth) {this.newApportionMonth = checkSet(newApportionMonth);}
	public String getNewMonthDepr() {return checkGet(newMonthDepr);}
	public void setNewMonthDepr(String newMonthDepr) {this.newMonthDepr = checkSet(newMonthDepr);}
	public String getCause2() {return checkGet(cause2);}
	public void setCause2(String cause2) {this.cause2 = checkSet(cause2);}
	public String getReduceDeal2() {return checkGet(reduceDeal2);}
	public void setReduceDeal2(String reduceDeal2) {this.reduceDeal2 = checkSet(reduceDeal2);}
	public String getMoveDate() {return checkGet(moveDate);}
	public void setMoveDate(String moveDate) {this.moveDate = checkSet(moveDate);}
	public String getKeepUnit() {return checkGet(keepUnit);}
	public void setKeepUnit(String keepUnit) {this.keepUnit = checkSet(keepUnit);}
	public String getKeeper() {return checkGet(keeper);}
	public void setKeeper(String keeper) {this.keeper = checkSet(keeper);}
	public String getUseUnit() {return checkGet(useUnit);}
	public void setUseUnit(String useUnit) {this.useUnit = checkSet(useUnit);}
	public String getUserNo() {return checkGet(userNo);}
	public void setUserNo(String userNo) {this.userNo = checkSet(userNo);}
	public String getUserNote() {return checkGet(userNote);}
	public void setUserNote(String userNote) {this.userNote = checkSet(userNote);}
	public String getPlace1() {return checkGet(place1);}
	public void setPlace1(String place1) {this.place1 = checkSet(place1);}
	public String getPlace() {return checkGet(place);}
	public void setPlace(String place) {this.place = checkSet(place);}
	public String getOldPropertyNo() {return checkGet(oldPropertyNo);}
	public void setOldPropertyNo(String oldPropertyNo) {this.oldPropertyNo = checkSet(oldPropertyNo);}
	public String getOldSerialNo() {return checkGet(oldSerialNo);}
	public void setOldSerialNo(String oldSerialNo) {this.oldSerialNo = checkSet(oldSerialNo);}
	public String getNewEnterOrgReceive() {return checkGet(newEnterOrgReceive);}
	public void setNewEnterOrgReceive(String newEnterOrgReceive) {this.newEnterOrgReceive = checkSet(newEnterOrgReceive);}
	public String getNotes() {return checkGet(notes);}
	public void setNotes(String notes) {this.notes = checkSet(notes);}
	public String getEditID() {return checkGet(editID);}
	public void setEditID(String editID) {this.editID = checkSet(editID);}
	public String getEditDate() {return checkGet(editDate);}
	public void setEditDate(String editDate) {this.editDate = checkSet(editDate);}
	public String getEditTime() {return checkGet(editTime);}
	public void setEditTime(String editTime) {this.editTime = checkSet(editTime);}
	public String getEnterOrgName() {return checkGet(enterOrgName);}
	public void setEnterOrgName(String enterOrgName) {this.enterOrgName = checkSet(enterOrgName);}
	public String getDifferenceKindName() {return checkGet(differenceKindName);}
	public void setDifferenceKindName(String differenceKindName) {this.differenceKindName = checkSet(differenceKindName);}
	public String getEngineeringNoName() {return checkGet(engineeringNoName);}
	public void setEngineeringNoName(String engineeringNoName) {this.engineeringNoName = checkSet(engineeringNoName);}
	public String getPropertyNoName() {return checkGet(propertyNoName);}
	public void setPropertyNoName(String propertyNoName) {this.propertyNoName = checkSet(propertyNoName);}


	private String oldMonthDepr1;
	private String newMonthDepr1;
	public String getOldMonthDepr1() {return checkGet(oldMonthDepr1);}
	public void setOldMonthDepr1(String oldMonthDepr1) {this.oldMonthDepr1 = checkSet(oldMonthDepr1);}
	public String getNewMonthDepr1() {return checkGet(newMonthDepr1);}
	public void setNewMonthDepr1(String newMonthDepr1) {this.newMonthDepr1 = checkSet(newMonthDepr1);}
	
	
	private Map getPKMap(){
		Map map = new HashMap();
		
		map.put("EnterOrg", getEnterOrg());
		map.put("CaseNo", getCaseNo());
		map.put("Ownership", getOwnership());
		map.put("DifferenceKind", getDifferenceKind());
		map.put("PropertyNo", getPropertyNo());
		map.put("SerialNo", getSerialNo());
		
		return map;
	}
	
	private Map getRecordMap(){
		Map map = new HashMap();
		UNTCH_Tools ut = new UNTCH_Tools();
		
		map.put("EnterOrg", getEnterOrg());
		map.put("CaseNo", getCaseNo());
		map.put("Ownership", getOwnership());
		map.put("DifferenceKind", getDifferenceKind());
		map.put("EngineeringNo", getEngineeringNo());
		map.put("CaseSerialNo", getCaseSerialNo());
		map.put("PropertyNo", getPropertyNo());
		map.put("SerialNo", getSerialNo());
		map.put("OtherLimitYear", getOtherLimitYear());
		map.put("PropertyName1", getPropertyName1());
		map.put("Cause", getCause());
		map.put("Cause1", getCause1());
		map.put("ReduceDate", ut._transToCE_Year(getReduceDate()));
		map.put("NewEnterOrg", getNewEnterOrg());
		map.put("TransferDate", ut._transToCE_Year(getTransferDate()));
		map.put("Verify", getVerify());
		map.put("PropertyKind", getPropertyKind());
		map.put("FundType", getFundType());
		map.put("Valuable", getValuable());
		map.put("TaxCredit", getTaxCredit());
		map.put("Measure", getMeasure());
		map.put("BookNotes", getBookNotes());
		map.put("AccountingTitle", getAccountingTitle());
		map.put("BookValue", getBookValue());
		map.put("NetValue", getNetValue());
		map.put("BuildDate", ut._transToCE_Year(getBuildDate()));
		map.put("SourceDate", ut._transToCE_Year(getSourceDate()));
		map.put("BuyDate", ut._transToCE_Year(getBuyDate()));
		map.put("UseYear", getUseYear());
		map.put("UseMonth", getUseMonth());
		map.put("OldDeprMethod", getOldDeprMethod());
		map.put("OldBuildFeeCB", getOldBuildFeeCB());
		map.put("OldDeprUnitCB", getOldDeprUnitCB());
		map.put("OldDeprPark", getOldDeprPark());
		map.put("OldDeprUnit", getOldDeprUnit());
		map.put("OldDeprUnit1", getOldDeprUnit1());
		map.put("OldDeprAccounts", getOldDeprAccounts());
		map.put("OldScrapValue", getOldScrapValue());
		map.put("OldDeprAmount", getOldDeprAmount());
		map.put("OldAccumDepr", getOldAccumDepr());
		map.put("OldApportionMonth", getOldApportionMonth());
		map.put("OldMonthDepr", getOldMonthDepr());
		map.put("OldMonthDepr1", getOldMonthDepr1());
		map.put("NewDeprMethod", getNewDeprMethod());
		map.put("NewBuildFeeCB", getNewBuildFeeCB());
		map.put("NewDeprUnitCB", getNewDeprUnitCB());
		map.put("NewDeprPark", getNewDeprPark());
		map.put("NewDeprUnit", getNewDeprUnit());
		map.put("NewDeprUnit1", getNewDeprUnit1());
		map.put("NewDeprAccounts", getNewDeprAccounts());
		map.put("NewScrapValue", getNewScrapValue());
		map.put("NewDeprAmount", getNewDeprAmount());
		map.put("NewAccumDepr", getNewAccumDepr());
		map.put("NewApportionMonth", getNewApportionMonth());
		map.put("NewMonthDepr", getNewMonthDepr());
		map.put("NewMonthDepr1", getNewMonthDepr1());
		map.put("Cause2", getCause2());
		map.put("ReduceDeal2", getReduceDeal2());
		map.put("MoveDate", ut._transToCE_Year(getMoveDate()));
		map.put("KeepUnit", getKeepUnit());
		map.put("Keeper", getKeeper());
		map.put("UseUnit", getUseUnit());
		map.put("UserNo", getUserNo());
		map.put("UserNote", getUserNote());
		map.put("Place1", getPlace1());
		map.put("Place", getPlace());
		map.put("OldPropertyNo", getOldPropertyNo());
		map.put("OldSerialNo", getOldSerialNo());
		map.put("NewEnterOrgReceive", getNewEnterOrgReceive());
		map.put("Notes", getNotes());
		map.put("EditID", getEditID());
		map.put("EditDate", ut._transToCE_Year(getEditDate()));
		map.put("EditTime", NewDateUtil.getNow(DateFormat.HHMMSS));

		return map;
	}
	
	
//傳回執行insert前之檢查sql
protected String[][] getInsertCheckSQL(){
	String[][] checkSQLArray = new String[3][4];
 	checkSQLArray[0][0]=" select count(*) as checkResult from UNTRF_ReduceDetail where 1=1 " + 
						" and enterOrg = " + Common.sqlChar(enterOrg) + 
						" and ownership = " + Common.sqlChar(ownership) +
						" and propertyNo = " + Common.sqlChar(propertyNo) + 
						" and serialNo = " + Common.sqlChar(serialNo) +  
						" and differenceKind = " + Common.sqlChar(differenceKind) +
						"";
	checkSQLArray[0][1]=">";
	checkSQLArray[0][2]="0";
	checkSQLArray[0][3]="該筆資料已重複，請重新輸入！";
	
 	checkSQLArray[1][0]=" select count(*) as checkResult from UNTRF_Attachment where 1=1 " + 
						" and enterOrg = " + Common.sqlChar(enterOrg) + 
						" and ownership = " + Common.sqlChar(ownership) + 
						" and propertyNo = " + Common.sqlChar(propertyNo) + 
						" and serialNo = " + Common.sqlChar(serialNo) + 
						" and differenceKind = " + Common.sqlChar(differenceKind) +
						" and dataState = '1' " +
						" and verify = 'Y' " +
						"";
 	checkSQLArray[1][1]="<=";
	checkSQLArray[1][2]="0";
	checkSQLArray[1][3]="土地改良物主檔中找不到該財產編號和分號，請重新輸入！";
	
 	checkSQLArray[2][0]=" select count(*) as checkResult from UNTRF_AdjustDetail where 1=1 " + 
						" and enterOrg = " + Common.sqlChar(enterOrg) + 
						" and ownership = " + Common.sqlChar(ownership) + 
						" and propertyNo = " + Common.sqlChar(propertyNo) + 
						" and serialNo = " + Common.sqlChar(serialNo) +
						" and differenceKind = " + Common.sqlChar(differenceKind) +
						" and verify = 'N' " +
						"";
	checkSQLArray[2][1]=">";
	checkSQLArray[2][2]="0";
	checkSQLArray[2][3]="增減值作業存在未入帳的資料，請重新輸入!!";
//System.out.println("-- untrf009f getInsertCheckSQL --\n"+checkSQLArray[2][0]);
	return checkSQLArray;
}


//傳回insert sql
protected String[] getInsertSQL(){
	String[] execSQLArray = new String[1];
	execSQLArray[0] = new SQLCreator()._obtainSQLforInsert("UNTRF_ReduceDetail", getPKMap(), getRecordMap());
	return execSQLArray;
}


//傳回update sql
protected String[] getUpdateSQL(){
	String[] execSQLArray = new String[1];
	execSQLArray[0] = new SQLCreator()._obtainSQLforUpdate("UNTRF_ReduceDetail", getPKMap(), getRecordMap());
	return execSQLArray;
}


//傳回delete sql
protected String[] getDeleteSQL(){
	String[] execSQLArray = new String[1];
	execSQLArray[0] = new SQLCreator()._obtainSQLforDelete("UNTRF_ReduceDetail", getPKMap(), getRecordMap());
	return execSQLArray;
}


/**
 * <br>
 * <br>目的：依主鍵查詢單一資料
 * <br>參數：無
 * <br>傳回：傳回本物件
*/

public UNTRF009F  queryOne() throws Exception{
	Database db = new Database();
	UNTRF009F obj = this;
	try {
		String sql=" select a.*, " +
			" (select c.organSName from SYSCA_ORGAN c where a.enterOrg = c.organID) as enterOrgName, " +
			" (select c.organSName from SYSCA_ORGAN c where a.newenterOrg = c.organID) as newenterOrgName, " +
			" (select codename from SYSCA_CODE z where codekindid = 'CAA' and z.codeid = a.cause and codecon1 in (2,4)) as causeName, " +
			" (select x.propertyName from SYSPK_PROPERTYCODE x where a.propertyNo = x.propertyNo ) as propertyNoName, " +
			" (select x.propertyName from SYSPK_PROPERTYCODE x where a.oldpropertyNo = x.propertyNo ) as oldpropertyNoName " +
			" from UNTRF_ReduceDetail a, UNTRF_Attachment b "+
			" where 1=1 " +
			" and a.enterOrg    = " + Common.sqlChar(enterOrg) +
			" and a.ownership   = " + Common.sqlChar(ownership) +
			" and a.propertyNo  = " + Common.sqlChar(propertyNo) +
			" and a.serialNo    = " + Common.sqlChar(serialNo) +
			" and a.differenceKind    = " + Common.sqlChar(differenceKind) +
			" and a.enterOrg 	= b.enterOrg " +
			" and a.ownership 	= b.ownership " +
			" and a.propertyNo 	= b.propertyNo " +
			" and a.serialNo 	= b.serialNo " +
			" and a.differenceKind 	= b.differenceKind " +
			" order by a.enterOrg , a.ownership  "+
			"";

		ResultSet rs = db.querySQL(sql);
		UNTCH_Tools ut = new UNTCH_Tools();
		if (rs.next()){
			obj.setEnterOrg(rs.getString("EnterOrg"));
			obj.setCaseNo(rs.getString("CaseNo"));
			obj.setOwnership(rs.getString("Ownership"));
			obj.setDifferenceKind(rs.getString("DifferenceKind"));
			obj.setEngineeringNo(rs.getString("EngineeringNo"));
			obj.setCaseSerialNo(rs.getString("CaseSerialNo"));
			obj.setPropertyNo(rs.getString("PropertyNo"));
			obj.setSerialNo(rs.getString("SerialNo"));
			obj.setOtherLimitYear(rs.getString("OtherLimitYear"));
			obj.setPropertyName1(rs.getString("PropertyName1"));
			obj.setCause(rs.getString("Cause"));
			obj.setCauseName(rs.getString("CauseName"));
			obj.setCause1(rs.getString("Cause1"));
			obj.setReduceDate(ut._transToROC_Year(rs.getString("ReduceDate")));
			obj.setNewEnterOrg(rs.getString("NewEnterOrg"));
			obj.setTransferDate(ut._transToROC_Year(rs.getString("TransferDate")));
			obj.setVerify(rs.getString("Verify"));
			obj.setPropertyKind(rs.getString("PropertyKind"));
			obj.setFundType(rs.getString("FundType"));
			obj.setValuable(rs.getString("Valuable"));
			obj.setTaxCredit(rs.getString("TaxCredit"));
			obj.setMeasure(rs.getString("Measure"));
			obj.setBookNotes(rs.getString("BookNotes"));
			obj.setAccountingTitle(rs.getString("AccountingTitle"));
			obj.setBookValue(rs.getString("BookValue"));
			obj.setNetValue(rs.getString("NetValue"));
			obj.setBuildDate(ut._transToROC_Year(rs.getString("BuildDate")));
			obj.setSourceDate(ut._transToROC_Year(rs.getString("SourceDate")));
			obj.setBuyDate(ut._transToROC_Year(rs.getString("BuyDate")));
			obj.setUseYear(rs.getString("UseYear"));
			obj.setUseMonth(rs.getString("UseMonth"));
			obj.setOldDeprMethod(rs.getString("OldDeprMethod"));
			obj.setOldBuildFeeCB(rs.getString("OldBuildFeeCB"));
			obj.setOldDeprUnitCB(rs.getString("OldDeprUnitCB"));
			obj.setOldDeprPark(rs.getString("OldDeprPark"));
			obj.setOldDeprUnit(rs.getString("OldDeprUnit"));
			obj.setOldDeprUnit1(rs.getString("OldDeprUnit1"));
			obj.setOldDeprAccounts(rs.getString("OldDeprAccounts"));
			obj.setOldScrapValue(rs.getString("OldScrapValue"));
			obj.setOldDeprAmount(rs.getString("OldDeprAmount"));
			obj.setOldAccumDepr(rs.getString("OldAccumDepr"));
			obj.setOldApportionMonth(rs.getString("OldApportionMonth"));
			obj.setOldMonthDepr(rs.getString("OldMonthDepr"));
			obj.setOldMonthDepr1(rs.getString("OldMonthDepr1"));
			obj.setNewDeprMethod(rs.getString("NewDeprMethod"));
			obj.setNewBuildFeeCB(rs.getString("NewBuildFeeCB"));
			obj.setNewDeprUnitCB(rs.getString("NewDeprUnitCB"));
			obj.setNewDeprPark(rs.getString("NewDeprPark"));
			obj.setNewDeprUnit(rs.getString("NewDeprUnit"));
			obj.setNewDeprUnit1(rs.getString("NewDeprUnit1"));
			obj.setNewDeprAccounts(rs.getString("NewDeprAccounts"));
			obj.setNewScrapValue(rs.getString("NewScrapValue"));
			obj.setNewDeprAmount(rs.getString("NewDeprAmount"));
			obj.setNewAccumDepr(rs.getString("NewAccumDepr"));
			obj.setNewApportionMonth(rs.getString("NewApportionMonth"));
			obj.setNewMonthDepr(rs.getString("NewMonthDepr"));
			obj.setNewMonthDepr1(rs.getString("NewMonthDepr1"));
			obj.setCause2(rs.getString("Cause2"));
			obj.setReduceDeal2(rs.getString("ReduceDeal2"));
			obj.setMoveDate(ut._transToROC_Year(rs.getString("MoveDate")));
			obj.setKeepUnit(rs.getString("KeepUnit"));
			obj.setKeeper(rs.getString("Keeper"));
			obj.setUseUnit(rs.getString("UseUnit"));
			obj.setUserNo(rs.getString("UserNo"));
			obj.setUserNote(rs.getString("UserNote"));
			obj.setPlace1(rs.getString("Place1"));
			obj.setPlace(rs.getString("Place"));
			obj.setOldPropertyNo(rs.getString("OldPropertyNo"));
			obj.setOldSerialNo(rs.getString("OldSerialNo"));
			obj.setNewEnterOrgReceive(rs.getString("NewEnterOrgReceive"));
			obj.setNotes(rs.getString("Notes"));
			obj.setEditID(rs.getString("EditID"));
			obj.setEditDate(ut._transToROC_Year(rs.getString("EditDate")));
			obj.setEditTime(rs.getString("EditTime"));
			obj.setEnterOrgName(rs.getString("EnterOrgName"));
			obj.setEngineeringNoName(ut._getEngineeringNoName(rs.getString("enterOrg"),rs.getString("engineeringNo")));
			obj.setPropertyNoName(rs.getString("PropertyNoName"));
			obj.setNewEnterOrgName(rs.getString("NewEnterOrgName"));
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
		String sql= " select a.enterOrg ,o.organSName ,a.ownership " +
					" ,(select x.codeName from sysca_code x where a.ownership = x.codeid and x.codekindid = 'OWA') as ownershipName " +
					" ,a.caseNo, a.propertyNo " +
					" ,(select x.propertyName from SYSPK_PropertyCode x where a.propertyNo = x.propertyNo ) as propertyName " +
					" ,a.serialNo " +
					" ,c.codeName as cause "+
					" ,a.propertyKind ,(select x.codeName from sysca_code x where a.propertyKind = x.codeid and x.codekindid = 'PKD') as propertyKindName,"+
					" decode(a.submitCityGov,'Y','是','否') submitCityGov , "+
					" a.bookValue, a.sourceDate ,a.bookNotes"+
					" from UNTRF_ReduceDetail a,UNTRF_ReduceProof b, SYSCA_Code c ,SYSCA_Organ o where 1=1 " +
					" and a.enterOrg(+)=b.enterOrg and a.ownership(+)=b.ownership and a.caseNo(+)=b.caseNo " +
					" and c.codeKindID='CAC' " +
					" and a.cause = c.codeID " +
					" and a.enterOrg = o.organID ";
					if ("AddProof".equals(getQuerySelect()) || "".equals(getQuerySelect())) {
						sql+=" and a.enterOrg = " + Common.sqlChar(getEnterOrg()) ;
						if(!"".equals(getOwnership())){
						    sql+=" and a.ownership = " + Common.sqlChar(getOwnership()) ;
						}
						sql+=" and a.caseNo=" + Common.sqlChar(getCaseNo());
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
							sql+=" and b.ownership = " + Common.sqlChar(getQ_ownership()) ;
						if (!"".equals(getQ_caseNoS()))
							sql+=" and b.caseNo >= " + Common.sqlChar(Common.formatFrontZero(getQ_caseNoS(),10));
						if (!"".equals(getQ_caseNoE()))
							sql+=" and b.caseNo <= " + Common.sqlChar(Common.formatFrontZero(getQ_caseNoE(),10));
						if (!"".equals(getQ_verify()))
							sql+=" and b.verify = " + Common.sqlChar(getQ_verify()) ;
						if (!"".equals(getQ_reduceDateS()))
							sql+=" and b.reduceDate >= " + Common.sqlChar(getQ_reduceDateS()) ;
						if (!"".equals(getQ_reduceDateE()))
							sql+=" and b.reduceDate <= " + Common.sqlChar(getQ_reduceDateE()) ;
						if (!"".equals(getQ_caseName()))
							sql+=" and b.caseName like " + Common.sqlChar(getQ_caseName()+"%") ;
						if (!"".equals(getQ_proofDoc()))
							sql+=" and b.proofDoc like " + Common.sqlChar(getQ_proofDoc()+"%") ;
						if (!"".equals(getQ_proofNoS()))
							sql+=" and b.proofNo >= " + Common.sqlChar(getQ_proofNoS()) ;
						if (!"".equals(getQ_proofNoE()))
							sql+=" and b.proofNo <= " + Common.sqlChar(getQ_proofNoE()) ;
						if (!"".equals(getQ_writeDateS()))
							sql+=" and b.writeDate >= " + Common.sqlChar(new Datetime().changeTaiwanYYMMDD(getQ_writeDateS(),"2")) ;
						if (!"".equals(getQ_writeDateE()))
							sql+=" and b.writeDate <= " + Common.sqlChar(new Datetime().changeTaiwanYYMMDD(getQ_writeDateE(),"2")) ;    
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
						if (!"".equals(getQ_taxCredit()))
							sql+=" and a.taxCredit = " + Common.sqlChar(getQ_taxCredit()) ;
					}
					sql+=" order by a.enterOrg , a.ownership  " ;
//System.out.println("-- untrf009f queryAll --\n"+sql);			
		ResultSet rs = db.querySQL(sql);
		while (rs.next()){
			counter++;
			String rowArray[]=new String[13];
			rowArray[0]=rs.getString("enterOrg");
			rowArray[1]=rs.getString("organSName");
			rowArray[2]=rs.getString("ownershipName");			
			rowArray[3]=rs.getString("propertyNo");
			rowArray[4]=rs.getString("serialNo");
			rowArray[5]=rs.getString("propertyName"); 
			rowArray[6]=rs.getString("cause"); 
			rowArray[7]=rs.getString("propertyKindName"); 
			rowArray[8]=rs.getString("bookValue"); 
			rowArray[9]=rs.getString("submitCityGov");
			rowArray[10]=rs.getString("caseNo");
			rowArray[11]=rs.getString("ownership");
			rowArray[12]=rs.getString("bookNotes");
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
