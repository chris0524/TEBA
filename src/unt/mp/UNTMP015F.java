/*
*<br>程式目的：動產減少作業－減損單明細
*<br>程式代號：untmp015f
*<br>程式日期：0941018
*<br>程式作者：judy
*<br>--------------------------------------------------------
*<br>修改作者　　修改日期　　　修改目的
*<br>--------------------------------------------------------
*/ 

package unt.mp;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import TDlib_Simple.com.src.SQLCreator;
import unt.ch.UNTCH_Tools;
import util.Common;
import util.Database;
import util.Datetime;
import util.NewDateUtil;
import util.NewDateUtil.DateFormat;

public class UNTMP015F extends UNTMP014Q{

	private String causeName;
	public String getCauseName() {return checkGet(causeName);}
	public void setCauseName(String causeName) {this.causeName = checkSet(causeName);}
	
	private String enterOrgName;
	private String engineeringNoName;
	private String differenceKindName;
	private String propertyNoName;
	public String getEnterOrgName() {return checkGet(enterOrgName);}
	public void setEnterOrgName(String enterOrgName) {this.enterOrgName = checkSet(enterOrgName);}
	public String getEngineeringNoName() {return checkGet(engineeringNoName);}
	public void setEngineeringNoName(String engineeringNoName) {this.engineeringNoName = checkSet(engineeringNoName);}
	public String getDifferenceKindName() {return checkGet(differenceKindName);}
	public void setDifferenceKindName(String differenceKindName) {this.differenceKindName = checkSet(differenceKindName);}
	public String getPropertyNoName() {return checkGet(propertyNoName);}
	public void setPropertyNoName(String propertyNoName) {this.propertyNoName = checkSet(propertyNoName);}

	private String newEnterOrgName;
	private String place1Name;
	public String getNewEnterOrgName() {return checkGet(newEnterOrgName);}
	public void setNewEnterOrgName(String newEnterOrgName) {this.newEnterOrgName = checkSet(newEnterOrgName);}
	public String getPlace1Name() {return checkGet(place1Name);}
	public void setPlace1Name(String place1Name) {this.place1Name = checkSet(place1Name);}

	private String enterOrg;
	private String caseNo;
	private String ownership;
	private String differenceKind;
	private String engineeringNo;
	private String caseSerialNo;
	private String propertyNo;
	private String lotNo;
	private String serialNo;
	private String otherPropertyUnit;
	private String otherMaterial;
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
	private String bookNotes;
	private String accountingTitle;
	private String oldBookAmount;
	private String oldBookUnit;
	private String oldBookValue;
	private String oldNetUnit;
	private String oldNetValue;
	private String adjustBookAmount;
	private String adjustBookUnit;
	private String adjustBookValue;
	private String adjustAccumDepr;
	private String adjustNetUnit;
	private String adjustNetValue;
	private String newBookAmount;
	private String newBookUnit;
	private String newBookValue;
	private String newNetUnit;
	private String newNetValue;
	private String articleName;
	private String nameplate;
	private String specification;
	private String sourceDate;
	private String buyDate;
	private String licensePlate;
	private String moveDate;
	private String keepUnit;
	private String keeper;
	private String useUnit;
	private String userNo;
	private String userNote;
	private String place1;
	private String place;
	private String useYear;
	private String useMonth;
	private String cause2;
	private String returnPlace;
	private String reduceDeal2;
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
	private String oldPropertyNo;
	private String oldSerialNo;
	private String newEnterOrgReceive;
	private String notes;
	private String editID;
	private String editDate;
	private String editTime;
	
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
	public String getLotNo() {return checkGet(lotNo);}
	public void setLotNo(String lotNo) {this.lotNo = checkSet(lotNo);}
	public String getSerialNo() {return checkGet(serialNo);}
	public void setSerialNo(String serialNo) {this.serialNo = checkSet(serialNo);}
	public String getOtherPropertyUnit() {return checkGet(otherPropertyUnit);}
	public void setOtherPropertyUnit(String otherPropertyUnit) {this.otherPropertyUnit = checkSet(otherPropertyUnit);}
	public String getOtherMaterial() {return checkGet(otherMaterial);}
	public void setOtherMaterial(String otherMaterial) {this.otherMaterial = checkSet(otherMaterial);}
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
	public String getBookNotes() {return checkGet(bookNotes);}
	public void setBookNotes(String bookNotes) {this.bookNotes = checkSet(bookNotes);}
	public String getAccountingTitle() {return checkGet(accountingTitle);}
	public void setAccountingTitle(String accountingTitle) {this.accountingTitle = checkSet(accountingTitle);}
	public String getOldBookAmount() {return checkGet(oldBookAmount);}
	public void setOldBookAmount(String oldBookAmount) {this.oldBookAmount = checkSet(oldBookAmount);}
	public String getOldBookUnit() {return checkGet(oldBookUnit);}
	public void setOldBookUnit(String oldBookUnit) {this.oldBookUnit = checkSet(oldBookUnit);}
	public String getOldBookValue() {return checkGet(oldBookValue);}
	public void setOldBookValue(String oldBookValue) {this.oldBookValue = checkSet(oldBookValue);}
	public String getOldNetUnit() {return checkGet(oldNetUnit);}
	public void setOldNetUnit(String oldNetUnit) {this.oldNetUnit = checkSet(oldNetUnit);}
	public String getOldNetValue() {return checkGet(oldNetValue);}
	public void setOldNetValue(String oldNetValue) {this.oldNetValue = checkSet(oldNetValue);}
	public String getAdjustBookAmount() {return checkGet(adjustBookAmount);}
	public void setAdjustBookAmount(String adjustBookAmount) {this.adjustBookAmount = checkSet(adjustBookAmount);}
	public String getAdjustBookUnit() {return checkGet(adjustBookUnit);}
	public void setAdjustBookUnit(String adjustBookUnit) {this.adjustBookUnit = checkSet(adjustBookUnit);}
	public String getAdjustBookValue() {return checkGet(adjustBookValue);}
	public void setAdjustBookValue(String adjustBookValue) {this.adjustBookValue = checkSet(adjustBookValue);}
	public String getAdjustAccumDepr() {return checkGet(adjustAccumDepr);}
	public void setAdjustAccumDepr(String adjustAccumDepr) {this.adjustAccumDepr = checkSet(adjustAccumDepr);}
	public String getAdjustNetUnit() {return checkGet(adjustNetUnit);}
	public void setAdjustNetUnit(String adjustNetUnit) {this.adjustNetUnit = checkSet(adjustNetUnit);}
	public String getAdjustNetValue() {return checkGet(adjustNetValue);}
	public void setAdjustNetValue(String adjustNetValue) {this.adjustNetValue = checkSet(adjustNetValue);}
	public String getNewBookAmount() {return checkGet(newBookAmount);}
	public void setNewBookAmount(String newBookAmount) {this.newBookAmount = checkSet(newBookAmount);}
	public String getNewBookUnit() {return checkGet(newBookUnit);}
	public void setNewBookUnit(String newBookUnit) {this.newBookUnit = checkSet(newBookUnit);}
	public String getNewBookValue() {return checkGet(newBookValue);}
	public void setNewBookValue(String newBookValue) {this.newBookValue = checkSet(newBookValue);}
	public String getNewNetUnit() {return checkGet(newNetUnit);}
	public void setNewNetUnit(String newNetUnit) {this.newNetUnit = checkSet(newNetUnit);}
	public String getNewNetValue() {return checkGet(newNetValue);}
	public void setNewNetValue(String newNetValue) {this.newNetValue = checkSet(newNetValue);}
	public String getArticleName() {return checkGet(articleName);}
	public void setArticleName(String articleName) {this.articleName = checkSet(articleName);}
	public String getNameplate() {return checkGet(nameplate);}
	public void setNameplate(String nameplate) {this.nameplate = checkSet(nameplate);}
	public String getSpecification() {return checkGet(specification);}
	public void setSpecification(String specification) {this.specification = checkSet(specification);}
	public String getSourceDate() {return checkGet(sourceDate);}
	public void setSourceDate(String sourceDate) {this.sourceDate = checkSet(sourceDate);}
	public String getBuyDate() {return checkGet(buyDate);}
	public void setBuyDate(String buyDate) {this.buyDate = checkSet(buyDate);}
	public String getLicensePlate() {return checkGet(licensePlate);}
	public void setLicensePlate(String licensePlate) {this.licensePlate = checkSet(licensePlate);}
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
	public String getUseYear() {return checkGet(useYear);}
	public void setUseYear(String useYear) {this.useYear = checkSet(useYear);}
	public String getUseMonth() {return checkGet(useMonth);}
	public void setUseMonth(String useMonth) {this.useMonth = checkSet(useMonth);}
	public String getCause2() {return checkGet(cause2);}
	public void setCause2(String cause2) {this.cause2 = checkSet(cause2);}
	public String getReturnPlace() {return checkGet(returnPlace);}
	public void setReturnPlace(String returnPlace) {this.returnPlace = checkSet(returnPlace);}
	public String getReduceDeal2() {return checkGet(reduceDeal2);}
	public void setReduceDeal2(String reduceDeal2) {this.reduceDeal2 = checkSet(reduceDeal2);}
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
	

	private String oldMonthDepr1;
	private String newMonthDepr1;
	public String getOldMonthDepr1() {return checkGet(oldMonthDepr1);}
	public void setOldMonthDepr1(String oldMonthDepr1) {this.oldMonthDepr1 = checkSet(oldMonthDepr1);}
	public String getNewMonthDepr1() {return checkGet(newMonthDepr1);}
	public void setNewMonthDepr1(String newMonthDepr1) {this.newMonthDepr1 = checkSet(newMonthDepr1);}
	private String limitYear;
	public String getLimitYear() { return checkGet(limitYear); }
	public void setLimitYear(String limitYear) { this.limitYear = checkSet(limitYear); }
	
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
		map.put("LotNo", getLotNo());
		map.put("SerialNo", getSerialNo());
		map.put("OtherPropertyUnit", getOtherPropertyUnit());
		map.put("OtherMaterial", getOtherMaterial());
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
		map.put("BookNotes", getBookNotes());
		map.put("AccountingTitle", getAccountingTitle());
		map.put("OldBookAmount", getOldBookAmount());
		map.put("OldBookUnit", getOldBookUnit());
		map.put("OldBookValue", getOldBookValue());
		map.put("OldNetUnit", getOldNetUnit());
		map.put("OldNetValue", getOldNetValue());
		map.put("AdjustBookAmount", getAdjustBookAmount());
		map.put("AdjustBookUnit", getAdjustBookUnit());
		map.put("AdjustBookValue", getAdjustBookValue());
		map.put("AdjustAccumDepr", getAdjustAccumDepr());
		map.put("AdjustNetUnit", getAdjustNetUnit());
		map.put("AdjustNetValue", getAdjustNetValue());
		map.put("NewBookAmount", getNewBookAmount());
		map.put("NewBookUnit", getNewBookUnit());
		map.put("NewBookValue", getNewBookValue());
		map.put("NewNetUnit", getNewNetUnit());
		map.put("NewNetValue", getNewNetValue());
		map.put("ArticleName", getArticleName());
		map.put("Nameplate", getNameplate());
		map.put("Specification", getSpecification());
		map.put("SourceDate", ut._transToCE_Year(getSourceDate()));
		map.put("BuyDate", ut._transToCE_Year(getBuyDate()));
		map.put("LicensePlate", getLicensePlate());
		map.put("MoveDate", ut._transToCE_Year(getMoveDate()));
		map.put("KeepUnit", getKeepUnit());
		map.put("Keeper", getKeeper());
		map.put("UseUnit", getUseUnit());
		map.put("UserNo", getUserNo());
		map.put("UserNote", getUserNote());
		map.put("Place1", getPlace1());
		map.put("Place", getPlace());
		map.put("UseYear", getUseYear());
		map.put("UseMonth", getUseMonth());
		map.put("Cause2", getCause2());
		map.put("ReturnPlace", getReturnPlace());
		map.put("ReduceDeal2", getReduceDeal2());
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
	String[][] checkSQLArray = new String[5][4];
 	checkSQLArray[0][0]=" select count(*) as checkResult from UNTMP_ReduceDetail where 1=1 " + 
		" and enterOrg = " + Common.sqlChar(enterOrg) + 
		" and ownership = " + Common.sqlChar(ownership) +
		" and differenceKind = " + Common.sqlChar(differenceKind) +
		" and propertyNo = " + Common.sqlChar(propertyNo) + 
		" and serialNo = " + Common.sqlChar(serialNo) +  
		" and verify = 'N' " +
		"";
	checkSQLArray[0][1]=">";
	checkSQLArray[0][2]="0";
	checkSQLArray[0][3]="動產減少作業存在未審核的資料，請重新輸入!!";
	
 	checkSQLArray[1][0]=" select count(*) as checkResult from UNTMP_Movable where 1=1 " + 
		" and enterOrg = " + Common.sqlChar(enterOrg) + 
		" and ownership = " + Common.sqlChar(ownership) + 
		" and differenceKind = " + Common.sqlChar(differenceKind) +
		" and propertyNo = " + Common.sqlChar(propertyNo) +
		" and lotNo = " + Common.sqlChar(lotNo) +
		" and dataState = '1' " +
		" and verify = 'Y' " +
		"";
	checkSQLArray[1][1]="<=";
	checkSQLArray[1][2]="0";
	checkSQLArray[1][3]="動產主檔-基本資料中找不到該財產編號和批號，請重新輸入！";

 	checkSQLArray[2][0]=" select count(*) as checkResult from UNTMP_MovableDetail where 1=1 " + 
		" and enterOrg = " + Common.sqlChar(enterOrg) + 
		" and ownership = " + Common.sqlChar(ownership) + 
		" and differenceKind = " + Common.sqlChar(differenceKind) +
		" and propertyNo = " + Common.sqlChar(propertyNo) + 
		" and serialNo = " + Common.sqlChar(serialNo) +
		" and dataState = '1' " +
		" and verify = 'Y' " +
		"";

	checkSQLArray[2][1]="<=";
	checkSQLArray[2][2]="0";
	checkSQLArray[2][3]="動產主檔-動產明細中找不到該財產編號和分號，請重新輸入！";
	
 	checkSQLArray[3][0]=" select count(*) as checkResult from UNTMP_AdjustDetail where 1=1 " + 
		" and enterOrg = " + Common.sqlChar(enterOrg) + 
		" and ownership = " + Common.sqlChar(ownership) + 
		" and differenceKind = " + Common.sqlChar(differenceKind) +
		" and propertyNo = " + Common.sqlChar(propertyNo) + 
		" and serialNo = " + Common.sqlChar(serialNo) +
		" and verify = 'N' " +
		"";

	checkSQLArray[3][1]=">";
	checkSQLArray[3][2]="0";
	checkSQLArray[3][3]="動產增減值作業存在未審核的資料，請重新輸入!!";
	
	return checkSQLArray;
}


//傳回insert sql
protected String[] getInsertSQL(){
	String[] execSQLArray = new String[1];
	execSQLArray[0] = new SQLCreator()._obtainSQLforInsert("UNTMP_ReduceDetail", getPKMap(), getRecordMap());
	return execSQLArray;
}


//傳回update sql
protected String[] getUpdateSQL(){
	String[] execSQLArray = new String[1];
	execSQLArray[0] = new SQLCreator()._obtainSQLforUpdate("UNTMP_ReduceDetail", getPKMap(), getRecordMap());
	return execSQLArray;
}

//傳回delete sql
protected String[] getDeleteSQL(){
	String[] execSQLArray = new String[1];
	execSQLArray[0] = new SQLCreator()._obtainSQLforDelete("UNTMP_ReduceDetail", getPKMap(), getRecordMap());
	return execSQLArray;
}


/**
 * <br>
 * <br>目的：依主鍵查詢單一資料
 * <br>參數：無
 * <br>傳回：傳回本物件
*/

public UNTMP015F  queryOne() throws Exception{
	Database db = new Database();
	UNTMP015F obj = this;
	try {
		String sql=" select a.*, " +
			" (select c.organSName from SYSCA_ORGAN c where a.enterOrg	= c.organID) as enterOrgName, " +
			" (select c.organSName from SYSCA_ORGAN c where a.newenterOrg	= c.organID) as newenterOrgName, " +
			" (select codename from SYSCA_CODE z where codekindid = 'CAA' and z.codeid = a.cause and codecon1 in (2,4)) as causeName, " +
			" (select d.propertyName from SYSPK_PROPERTYCODE d where a.propertyNo = d.propertyNo and d.enterorg in (a.enterorg,'000000000A')) as propertyNoName, " +
			" (select d.material from SYSPK_PROPERTYCODE d where a.propertyNo = d.propertyNo and d.enterorg in (a.enterorg,'000000000A')) as material, " +
			" (select d.propertyUnit from SYSPK_PROPERTYCODE d where a.propertyNo = d.propertyNo and d.enterorg in (a.enterorg,'000000000A')) as propertyUnit, " +
			" (select d.limitYear from SYSPK_PROPERTYCODE d where a.propertyNo = d.propertyNo and d.enterorg in (a.enterorg,'000000000A')) as limitYear, " +
			" (select placename from SYSCA_PLACE z where z.enterorg = a.enterorg and z.placeno = a.place1) as place1Name " +
			" from UNTMP_ReduceDetail a "+
			" where 1=1 " +
			" and a.enterOrg    = " + Common.sqlChar(enterOrg) +
			" and a.ownership   = " + Common.sqlChar(ownership) +
			" and a.caseNo 	= " + Common.sqlChar(caseNo) +
			" and a.differenceKind    = " + Common.sqlChar(differenceKind) +
			" and a.propertyNo  = " + Common.sqlChar(propertyNo) +
			" and a.serialNo    = " + Common.sqlChar(serialNo) +			
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
			obj.setLotNo(rs.getString("LotNo"));
			obj.setSerialNo(rs.getString("SerialNo"));
			obj.setOtherPropertyUnit(rs.getString("OtherPropertyUnit"));
			obj.setOtherMaterial(rs.getString("OtherMaterial"));
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
			obj.setBookNotes(rs.getString("BookNotes"));
			obj.setAccountingTitle(rs.getString("AccountingTitle"));
			obj.setOldBookAmount(rs.getString("OldBookAmount"));
			obj.setOldBookUnit(rs.getString("OldBookUnit"));
			obj.setOldBookValue(rs.getString("OldBookValue"));
			obj.setOldNetUnit(rs.getString("OldNetUnit"));
			obj.setOldNetValue(rs.getString("OldNetValue"));
			obj.setAdjustBookAmount(rs.getString("AdjustBookAmount"));
			obj.setAdjustBookUnit(rs.getString("AdjustBookUnit"));
			obj.setAdjustBookValue(rs.getString("AdjustBookValue"));
			obj.setAdjustAccumDepr(rs.getString("AdjustAccumDepr"));
			obj.setAdjustNetUnit(rs.getString("AdjustNetUnit"));
			obj.setAdjustNetValue(rs.getString("AdjustNetValue"));
			obj.setNewBookAmount(rs.getString("NewBookAmount"));
			obj.setNewBookUnit(rs.getString("NewBookUnit"));
			obj.setNewBookValue(rs.getString("NewBookValue"));
			obj.setNewNetUnit(rs.getString("NewNetUnit"));
			obj.setNewNetValue(rs.getString("NewNetValue"));
			obj.setArticleName(rs.getString("ArticleName"));
			obj.setNameplate(rs.getString("Nameplate"));
			obj.setSpecification(rs.getString("Specification"));
			obj.setSourceDate(ut._transToROC_Year(rs.getString("SourceDate")));
			obj.setBuyDate(ut._transToROC_Year(rs.getString("BuyDate")));
			obj.setLicensePlate(rs.getString("LicensePlate"));
			obj.setMoveDate(ut._transToROC_Year(rs.getString("MoveDate")));
			obj.setKeepUnit(rs.getString("KeepUnit"));
			obj.setKeeper(rs.getString("Keeper"));
			obj.setUseUnit(rs.getString("UseUnit"));
			obj.setUserNo(rs.getString("UserNo"));
			obj.setUserNote(rs.getString("UserNote"));
			obj.setPlace1(rs.getString("Place1"));
			obj.setPlace1Name(rs.getString("place1Name"));
			obj.setPlace(rs.getString("Place"));
			obj.setUseYear(rs.getString("UseYear"));
			obj.setUseMonth(rs.getString("UseMonth"));
			obj.setCause2(rs.getString("Cause2"));
			obj.setReturnPlace(rs.getString("ReturnPlace"));
			obj.setReduceDeal2(rs.getString("ReduceDeal2"));
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
			obj.setLimitYear(rs.getString("limitYear"));
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
		String sql= " select distinct a.enterOrg, a.ownership," +
					" (select z.codeName from sysca_code z where 1=1 and (z.codeKindID='owa' or z.codeKindID='OWA') and a.ownership=z.codeID) as ownershipName, " +
					" a.caseNo, a.propertyNo, a.lotNo, a.serialNo, " +
					" c.codeName cause, a.otherLimitYear , a.enterDate , a.buyDate, "+
					" decode(a.propertyKind,'01','公務用','02','公共用','03','事業用','04','非公用','') propertyKind,"+
					" decode(a.submitCityGov,'Y','是','否') submitCityGov , "+
					" a.articleName,a.nameplate,a.specification,a.licensePlate,"+
					" a.moveDate,a.keepUnit,a.keeper,a.useUnit,a.userNo,"+
					" a.place,a.returnPlace,a.cause2,a.scrapValue2,a.dealSuggestion,"+
					" a.scrapValue,a.apportionYear,a.useEndYM,a.dealCaseNo," +
					" a.adjustBookAmount, a.adjustBookValue, "+
					" a.dealDate,a.realizeValue,a.shiftOrg, a.sourceDate, " +
					" (select f.codeName from SYSCA_Code f where 1=1 and f.codeKindID='RDL' and a.reduceDeal = f.codeID) as reduceDeal, "+
					" (select e.keeperName from UNTMP_Keeper e where a.enterOrg=e.enterOrg and a.keepUnit=e.unitNo and a.keeper=e.keeperNo) as keeperName, " +
					" (select o.organSName from SYSCA_Organ o where a.enterOrg = o.organID)organSName, "+
					" (select d.propertyName from SYSPK_PropertyCode d where d.propertyType='1' and a.propertyNo 	= d.propertyNo and d.enterOrg in('000000000A',a.enterOrg))propertyName "+
					" from UNTMP_ReduceDetail a, UNTMP_ReduceProof b, SYSCA_Code c where 1=1 " +
					" and a.enterOrg(+)=b.enterOrg and a.ownership(+)=b.ownership and a.caseNo(+)=b.caseNo " +
					" and c.codeKindID='CAC' " +
					" and a.cause = c.codeID " +
					//" and a.propertyNo 	= d.propertyNo " +
					//" and d.enterOrg in('000000000A',a.enterOrg) and d.propertyType='1'" +
					//" and a.enterOrg = o.organID "+
					""; 
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
						sql += " and a.enterOrg like '" + getOrganID().substring(0,5) + "%' ";					
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
				sql+=" and a.propertyNo<=" + Common.sqlChar(getQ_propertyNoE());						
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
		}
		sql+=" order by a.enterOrg, a.ownership, a.propertyNo, a.serialNo " ;
		//System.out.println(sql);
		ResultSet rs = db.querySQL(sql);
		while (rs.next()){
			counter++;
			String rowArray[]=new String[18];
			rowArray[0]=rs.getString("enterOrg");
			rowArray[1]=rs.getString("organSName"); 
			rowArray[2]=rs.getString("ownership");
			rowArray[3]=rs.getString("ownershipName"); 
			rowArray[4]=rs.getString("caseNo");
			rowArray[5]=rs.getString("propertyNo");
			rowArray[6]=rs.getString("propertyName");
			rowArray[7]=rs.getString("serialNo");
			rowArray[8]=rs.getString("keeperName");
			rowArray[9]=rs.getString("cause"); 
			rowArray[10]=rs.getString("propertyKind"); 
			rowArray[11]=rs.getString("adjustBookAmount");
			rowArray[12]=rs.getString("adjustBookValue");
			rowArray[13]=rs.getString("keepUnit");
			rowArray[14]=rs.getString("keeper");
			rowArray[15]=rs.getString("place");
			rowArray[16]=rs.getString("submitCityGov");
			rowArray[17]=rs.getString("reduceDeal");
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
