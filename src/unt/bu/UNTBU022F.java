/*
*<br>程式目的：建物增減值作業－增減值單明細
*<br>程式代號：untbu022f
*<br>程式日期：0941004
*<br>程式作者：judy
*<br>--------------------------------------------------------
*<br>修改作者　　修改日期　　　修改目的
*<br>--------------------------------------------------------
*/ 

package unt.bu;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import TDlib_Simple.com.src.SQLCreator;
import unt.ch.UNTCH_Tools;
import util.Common;
import util.Database;

public class UNTBU022F extends UNTBU021Q{

	private String oldApportionEndYM;
	private String newApportionEndYM;
	public String getOldApportionEndYM() {return checkGet(oldApportionEndYM);}
	public void setOldApportionEndYM(String oldApportionEndYM) {this.oldApportionEndYM = checkSet(oldApportionEndYM);}
	public String getNewApportionEndYM() {return checkGet(newApportionEndYM);}
	public void setNewApportionEndYM(String newApportionEndYM) {this.newApportionEndYM = checkSet(newApportionEndYM);}
	
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
private String signNo;
private String cause;
private String cause1;
private String cause2;
private String adjustDate;
private String verify;
private String propertyKind;
private String fundType;
private String valuable;
private String taxCredit;
private String originalBV;
private String sourceDate;
private String buyDate;
private String oldCArea;
private String oldSArea;
private String oldArea;
private String oldHoldNume;
private String oldHoldDeno;
private String oldHoldArea;
private String oldBookValue;
private String oldNetValue;
private String adjustCArea;
private String adjustSArea;
private String adjustArea;
private String adjustHoldArea;
private String addBookValue;
private String addNetValue;
private String addLimitYear;
private String overLimitYear;
private String overLimitYear2;
private String reduceBookValue;
private String reduceAccumDepr;
private String reduceNetValue;
private String reduceLimitYear;
private String newCArea;
private String newSArea;
private String newArea;
private String newHoldNume;
private String newHoldDeno;
private String newHoldArea;
private String newBookValue;
private String newNetValue;
private String bookNotes;
private String keepUnit;
private String keeper;
private String useUnit;
private String userNo;
private String userNote;
private String place1;
private String place;
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
private String newOtherLimitYear;
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
private String notes;
private String editID;
private String editDate;
private String editTime;

private String enterOrgName;
private String engineeringNoName;
private String propertyNoName;
private String signNo1;
private String signNo2;
private String signNo3;
private String signNo4;
private String signNo5;

private String adjustType;
private String adjustBookValue;

private String oldPropertyName;
private String oldcause;

private String CArea;
private String SArea;
private String area;
private String holdNume;
private String holdDeno;
private String holdArea;
private String bookValue;
private String closing1ym;

public String getSignNo1(){ return checkGet(signNo1); }
public void setSignNo1(String s){ signNo1=checkSet(s); }
public String getSignNo2(){ return checkGet(signNo2); }
public void setSignNo2(String s){ signNo2=checkSet(s); }
public String getSignNo3(){ return checkGet(signNo3); }
public void setSignNo3(String s){ signNo3=checkSet(s); }
public String getSignNo4(){ return checkGet(signNo4); }
public void setSignNo4(String s){
	if (s!=null && s.length()>0) signNo4=checkSet(Common.formatFrontZero(s,5));
	else signNo4 = "";
}
public String getSignNo5(){ return checkGet(signNo5); }
public void setSignNo5(String s){
	if (s!=null && s.length()>0) signNo5=checkSet(Common.formatFrontZero(s,3));
	else signNo5 = ""; 
}
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
public String getSignNo() {return checkGet(signNo);}
public void setSignNo(String signNo) {this.signNo = checkSet(signNo);}
public String getCause() {return checkGet(cause);}
public void setCause(String cause) {this.cause = checkSet(cause);}
public String getCause1() {return checkGet(cause1);}
public void setCause1(String cause1) {this.cause1 = checkSet(cause1);}
public String getCause2() {return checkGet(cause2);}
public void setCause2(String cause2) {this.cause2 = checkSet(cause2);}
public String getAdjustDate() {return checkGet(adjustDate);}
public void setAdjustDate(String adjustDate) {this.adjustDate = checkSet(adjustDate);}
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
public String getOriginalBV() {return checkGet(originalBV);}
public void setOriginalBV(String originalBV) {this.originalBV = checkSet(originalBV);}
public String getSourceDate() {return checkGet(sourceDate);}
public void setSourceDate(String sourceDate) {this.sourceDate = checkSet(sourceDate);}
public String getBuyDate() {return checkGet(buyDate);}
public void setBuyDate(String buyDate) {this.buyDate = checkSet(buyDate);}
public String getOldCArea() {return checkGet(oldCArea);}
public void setOldCArea(String oldCArea) {this.oldCArea = checkSet(oldCArea);}
public String getOldSArea() {return checkGet(oldSArea);}
public void setOldSArea(String oldSArea) {this.oldSArea = checkSet(oldSArea);}
public String getOldArea() {return checkGet(oldArea);}
public void setOldArea(String oldArea) {this.oldArea = checkSet(oldArea);}
public String getOldHoldNume() {return checkGet(oldHoldNume);}
public void setOldHoldNume(String oldHoldNume) {this.oldHoldNume = checkSet(oldHoldNume);}
public String getOldHoldDeno() {return checkGet(oldHoldDeno);}
public void setOldHoldDeno(String oldHoldDeno) {this.oldHoldDeno = checkSet(oldHoldDeno);}
public String getOldHoldArea() {return checkGet(oldHoldArea);}
public void setOldHoldArea(String oldHoldArea) {this.oldHoldArea = checkSet(oldHoldArea);}
public String getOldBookValue() {return checkGet(oldBookValue);}
public void setOldBookValue(String oldBookValue) {this.oldBookValue = checkSet(oldBookValue);}
public String getOldNetValue() {return checkGet(oldNetValue);}
public void setOldNetValue(String oldNetValue) {this.oldNetValue = checkSet(oldNetValue);}
public String getAdjustCArea() {return checkGet(adjustCArea);}
public void setAdjustCArea(String adjustCArea) {this.adjustCArea = checkSet(adjustCArea);}
public String getAdjustSArea() {return checkGet(adjustSArea);}
public void setAdjustSArea(String adjustSArea) {this.adjustSArea = checkSet(adjustSArea);}
public String getAdjustArea() {return checkGet(adjustArea);}
public void setAdjustArea(String adjustArea) {this.adjustArea = checkSet(adjustArea);}
public String getAdjustHoldArea() {return checkGet(adjustHoldArea);}
public void setAdjustHoldArea(String adjustHoldArea) {this.adjustHoldArea = checkSet(adjustHoldArea);}
public String getAddBookValue() {return checkGet(addBookValue);}
public void setAddBookValue(String addBookValue) {this.addBookValue = checkSet(addBookValue);}
public String getAddNetValue() {return checkGet(addNetValue);}
public void setAddNetValue(String addNetValue) {this.addNetValue = checkSet(addNetValue);}
public String getAddLimitYear() {return checkGet(addLimitYear);}
public void setAddLimitYear(String addLimitYear) {this.addLimitYear = checkSet(addLimitYear);}
public String getOverLimitYear() {return checkGet(overLimitYear);}
public void setOverLimitYear(String overLimitYear) {this.overLimitYear = checkGet(overLimitYear);}
public String getOverLimitYear2() {return checkGet(overLimitYear2);}
public void setOverLimitYear2(String overLimitYear2) {this.overLimitYear2 = checkGet(overLimitYear2);}
public String getReduceBookValue() {return checkGet(reduceBookValue);}
public void setReduceBookValue(String reduceBookValue) {this.reduceBookValue = checkSet(reduceBookValue);}
public String getReduceAccumDepr() {return checkGet(reduceAccumDepr);}
public void setReduceAccumDepr(String reduceAccumDepr) {this.reduceAccumDepr = checkSet(reduceAccumDepr);}
public String getReduceNetValue() {return checkGet(reduceNetValue);}
public void setReduceNetValue(String reduceNetValue) {this.reduceNetValue = checkSet(reduceNetValue);}
public String getReduceLimitYear() {return checkGet(reduceLimitYear);}
public void setReduceLimitYear(String reduceLimitYear) {this.reduceLimitYear = checkSet(reduceLimitYear);}
public String getNewCArea() {return checkGet(newCArea);}
public void setNewCArea(String newCArea) {this.newCArea = checkSet(newCArea);}
public String getNewSArea() {return checkGet(newSArea);}
public void setNewSArea(String newSArea) {this.newSArea = checkSet(newSArea);}
public String getNewArea() {return checkGet(newArea);}
public void setNewArea(String newArea) {this.newArea = checkSet(newArea);}
public String getNewHoldNume() {return checkGet(newHoldNume);}
public void setNewHoldNume(String newHoldNume) {this.newHoldNume = checkSet(newHoldNume);}
public String getNewHoldDeno() {return checkGet(newHoldDeno);}
public void setNewHoldDeno(String newHoldDeno) {this.newHoldDeno = checkSet(newHoldDeno);}
public String getNewHoldArea() {return checkGet(newHoldArea);}
public void setNewHoldArea(String newHoldArea) {this.newHoldArea = checkSet(newHoldArea);}
public String getNewBookValue() {return checkGet(newBookValue);}
public void setNewBookValue(String newBookValue) {this.newBookValue = checkSet(newBookValue);}
public String getNewNetValue() {return checkGet(newNetValue);}
public void setNewNetValue(String newNetValue) {this.newNetValue = checkSet(newNetValue);}
public String getBookNotes() {return checkGet(bookNotes);}
public void setBookNotes(String bookNotes) {this.bookNotes = checkSet(bookNotes);}
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
public String getNewOtherLimitYear() {return checkGet(newOtherLimitYear);}
public void setNewOtherLimitYear(String newOtherLimitYear) {this.newOtherLimitYear = checkSet(newOtherLimitYear);}
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
public String getEngineeringNoName() {return checkGet(engineeringNoName);}
public void setEngineeringNoName(String engineeringNoName) {this.engineeringNoName = checkSet(engineeringNoName);}
public String getPropertyNoName() {return checkGet(propertyNoName);}
public void setPropertyNoName(String propertyNoName) {this.propertyNoName = checkSet(propertyNoName);}
public String getAdjustType() {return checkGet(adjustType);}
public void setAdjustType(String adjustType) {this.adjustType = checkSet(adjustType);}
public String getAdjustBookValue() {return checkGet(adjustBookValue);}
public void setAdjustBookValue(String adjustBookValue) {this.adjustBookValue = checkSet(adjustBookValue);}
public String getOldPropertyName() {return checkGet(oldPropertyName);}
public void setOldPropertyName(String oldPropertyName) {this.oldPropertyName = checkSet(oldPropertyName);}
public String getOldcause() {return checkGet(oldcause);}
public void setOldcause(String oldcause) {this.oldcause = checkSet(oldcause);}
public String getCArea() {return checkGet(CArea);}
public void setCArea(String cArea) {CArea = checkSet(cArea);}
public String getSArea() {return checkGet(SArea);}
public void setSArea(String sArea) {SArea = checkSet(sArea);}
public String getArea() {return checkGet(area);}
public void setArea(String area) {this.area = checkSet(area);}
public String getHoldNume() {return checkGet(holdNume);}
public void setHoldNume(String holdNume) {this.holdNume = checkSet(holdNume);}
public String getHoldDeno() {return checkGet(holdDeno);}
public void setHoldDeno(String holdDeno) {this.holdDeno = checkSet(holdDeno);}
public String getHoldArea() {return checkGet(holdArea);}
public void setHoldArea(String holdArea) {this.holdArea = checkSet(holdArea);}
public String getBookValue() {return checkGet(bookValue);}
public void setBookValue(String bookValue) {this.bookValue = checkSet(bookValue);}
public String getClosing1ym() {return checkGet(closing1ym);}
public void setClosing1ym(String closing1ym) {this.closing1ym = checkSet(closing1ym);}

String ownershipQuery;

public String getOwnershipQuery(){ return checkGet(ownershipQuery); }
public void setOwnershipQuery(String s){ ownershipQuery=checkSet(s); }

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
		UNTCH_Tools ul = new UNTCH_Tools();
		
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
		map.put("SignNo", getSignNo());
		map.put("Cause", getCause());
		map.put("Cause1", getCause1());
		map.put("Cause2", getCause2());
		map.put("AdjustDate", ul._transToCE_Year(getAdjustDate()));
		map.put("Verify", getVerify());
		map.put("PropertyKind", getPropertyKind());
		map.put("FundType", getFundType());
		map.put("Valuable", getValuable());
		map.put("TaxCredit", getTaxCredit());
		map.put("OriginalBV", getOriginalBV());
		map.put("SourceDate", ul._transToCE_Year(getSourceDate()));
		map.put("BuyDate", ul._transToCE_Year(getBuyDate()));
		map.put("OldCArea", getOldCArea());
		map.put("OldSArea", getOldSArea());
		map.put("OldArea", getOldArea());
		map.put("OldHoldNume", getOldHoldNume());
		map.put("OldHoldDeno", getOldHoldDeno());
		map.put("OldHoldArea", getOldHoldArea());
		map.put("OldBookValue", getOldBookValue());
		map.put("OldNetValue", getOldNetValue());
		map.put("AdjustCArea", getAdjustCArea());
		map.put("AdjustSArea", getAdjustSArea());
		map.put("AdjustArea", getAdjustArea());
		map.put("AdjustHoldArea", getAdjustHoldArea());
		map.put("AddBookValue", getAddBookValue());
		map.put("AddNetValue", getAddNetValue());
		map.put("AddLimitYear", getAddLimitYear());
		map.put("OverLimitYear", getOverLimitYear());
//		map.put("OverLimitYear2", getOverLimitYear2());
		map.put("ReduceBookValue", getReduceBookValue());
		map.put("ReduceAccumDepr", getReduceAccumDepr());
		map.put("ReduceNetValue", getReduceNetValue());
		map.put("ReduceLimitYear", getReduceLimitYear());
		map.put("NewCArea", getNewCArea());
		map.put("NewSArea", getNewSArea());
		map.put("NewArea", getNewArea());
		map.put("NewHoldNume", getNewHoldNume());
		map.put("NewHoldDeno", getNewHoldDeno());
		map.put("NewHoldArea", getNewHoldArea());
		map.put("NewBookValue", getNewBookValue());
		map.put("NewNetValue", getNewNetValue());
		map.put("BookNotes", getBookNotes());
		map.put("KeepUnit", getKeepUnit());
		map.put("Keeper", getKeeper());
		map.put("UseUnit", getUseUnit());
		map.put("UserNo", getUserNo());
		map.put("UserNote", getUserNote());
		map.put("Place1", getPlace1());
		map.put("Place", getPlace());
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
		map.put("Notes", getNotes());
		map.put("EditID", getEditID());
		map.put("EditDate", ul._transToCE_Year(getEditDate()));
		map.put("EditTime", getEditTime());		
		if (this.getOldApportionEndYM().length() != 6) {// 進來就是西元年了 就不要再轉了
			map.put("OldApportionEndYM", ul._transToCE_Year(this.getOldApportionEndYM()));
		} else {
			map.put("OldApportionEndYM", this.getOldApportionEndYM());
		}
		
		if (this.getNewApportionEndYM().length() != 6) {// 進來就是西元年了 就不要再轉了
			map.put("NewApportionEndYM", ul._transToCE_Year(this.getNewApportionEndYM()));
		} else {
			map.put("NewApportionEndYM", this.getNewApportionEndYM());
		}
		
		return map;
	}


//傳回執行insert前之檢查sql
protected String[][] getInsertCheckSQL(){
	String s1, s2, s3, s4, s5;
	if(getSignNo1()!=null && !getSignNo1().equals("") && getSignNo2()!=null && !getSignNo2().equals("") && getSignNo3()!=null && !getSignNo3().equals("")){	
		s1 = getSignNo1().substring(0,1);
		s2 = getSignNo2().substring(1,3);
		s3 = getSignNo3().substring(3,7);	
		s4 = getSignNo4();
		s5 = getSignNo5();
		this.setSignNo(s1+s2+s3+s4+s5);	
	}else{
		s4 = getSignNo4();
		s5 = getSignNo5();
		this.setSignNo(s4+s5);	
	}
	String[][] checkSQLArray = new String[5][5];
 	checkSQLArray[0][0]=" select count(*) as checkResult from UNTBU_AdjustDetail where 1=1 " + 
						" and enterOrg = " + Common.sqlChar(enterOrg) + 
						" and ownership = " + Common.sqlChar(ownership) +
						" and differenceKind = " + Common.sqlChar(differenceKind) +
						" and propertyNo = " + Common.sqlChar(propertyNo) + 
						" and serialNo = " + Common.sqlChar(serialNo) +  
						" and verify = 'N' " +
						"";
	checkSQLArray[0][1]=">";
	checkSQLArray[0][2]="0";
	checkSQLArray[0][3]="該筆資料已重複，請重新輸入！";
	
// 	checkSQLArray[1][0]=" select count(*) as checkResult from UNTBU_Building where 1=1 " + 
//						" and enterOrg = " + Common.sqlChar(enterOrg) + 
//						" and ownership = " + Common.sqlChar(ownership) + 
//						" and propertyNo = " + Common.sqlChar(propertyNo) + 
//						" and serialNo = " + Common.sqlChar(serialNo) + 
//						" and verify = 'Y' " +
//						" and dataState = '1' " +
//						"";
//	checkSQLArray[1][1]="<=";
//	checkSQLArray[1][2]="0";
//	checkSQLArray[1][3]="建物主檔中找不到該財產編號和分號，請重新輸入！";
	
// 	checkSQLArray[2][0]=" select count(*) as checkResult from UNTBU_Building where 1=1 " + 
//						" and enterOrg = " + Common.sqlChar(enterOrg) + 
//						" and ownership = " + Common.sqlChar(ownership) + 
//						" and propertyNo = " + Common.sqlChar(propertyNo) + 
//						" and serialNo = " + Common.sqlChar(serialNo) + 
//						" and signNo " + (signNo == null || signNo.equals("")?"is null":("="+Common.sqlChar(signNo))) + 	
//						" and verify = 'Y' " +
//						" and dataState = '1' " +
//						""; 	
//	checkSQLArray[2][1]="<=";
//	checkSQLArray[2][2]="0";
//	checkSQLArray[2][3]="建物主檔中找不到該筆建物標示資料，請重新輸入！";

 	checkSQLArray[3][0]=" select count(*) as checkResult from UNTBU_ReduceDetail where 1=1 " + 
						" and enterOrg = " + Common.sqlChar(enterOrg) + 
						" and ownership = " + Common.sqlChar(ownership) + 
						" and differenceKind = " + Common.sqlChar(differenceKind) +
						" and propertyNo = " + Common.sqlChar(propertyNo) + 
						" and serialNo = " + Common.sqlChar(serialNo) +
						" and verify = 'N' " +
						"";
	checkSQLArray[3][1]=">";
	checkSQLArray[3][2]="0";
	checkSQLArray[3][3]="減少作業存在未入帳的資料，請重新輸入!!";
    
    System.out.println(checkSQLArray[4][0]);
	return checkSQLArray;
}


//傳回insert sql
protected String[] getInsertSQL(){
	String[] execSQLArray = new String[1];
	execSQLArray[0] = new SQLCreator()._obtainSQLforInsert("UNTBU_AdjustDetail", getPKMap(), getRecordMap());
	return execSQLArray;
}


//傳回update sql
protected String[] getUpdateSQL(){
	String[] execSQLArray = new String[1];
	execSQLArray[0] = new SQLCreator()._obtainSQLforUpdate("UNTBU_AdjustDetail", getPKMap(), getRecordMap());
	return execSQLArray;
}


//傳回delete sql
protected String[] getDeleteSQL(){
	String[] execSQLArray = new String[1];
	execSQLArray[0] = new SQLCreator()._obtainSQLforDelete("UNTBU_AdjustDetail", getPKMap(), getRecordMap());
	return execSQLArray;
}


/**
 * <br>
 * <br>目的：依主鍵查詢單一資料
 * <br>參數：無
 * <br>傳回：傳回本物件
*/

public UNTBU022F  queryOne() throws Exception{
	Database db = new Database();
	UNTBU022F obj = this;
	try {
		String sql=" select a.*, " +
			" c.organsname as enterorgname, d.propertyname, " +
			" isnull(otherlimityear,0) + isnull(addlimityear,0) + isnull(overlimityear,0) - isnull(reducelimityear,0) as newOtherLimitYear, " +
			" (select e.propertyname from SYSPK_PROPERTYCODE e where a.oldpropertyno = e.propertyno) as oldpropertyname, "+
			" b.closing1ym " +
			" from UNTBU_ADJUSTDETAIL a inner join UNTAC_CLOSINGPT b on a.enterorg = b.enterorg and a.differencekind = b.differencekind , SYSCA_ORGAN c, "+
			" SYSPK_PROPERTYCODE d where 1=1 " +
			" and a.enterorg 	= " + Common.sqlChar(enterOrg) +
			" and a.ownership 	= " + Common.sqlChar(ownership) +
			" and a.differencekind 	= " + Common.sqlChar(differenceKind) +
			" and a.caseno 	= " + Common.sqlChar(caseNo) +
			" and a.propertyno 	= " + Common.sqlChar(propertyNo) +
			" and a.serialno 	= " + Common.sqlChar(serialNo) +
			" and a.enterorg	= c.organid " +
			" and a.propertyno 	= d.propertyno "+
			" order by a.enterorg , a.ownership , a.signno ";
		
		ResultSet rs = db.querySQL_NoChange(sql);
		UNTCH_Tools ul = new UNTCH_Tools();
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
			obj.setSignNo(rs.getString("SignNo"));
			obj.setCause(rs.getString("Cause"));
			obj.setCause1(rs.getString("Cause1"));
			obj.setCause2(rs.getString("Cause2"));
			obj.setAdjustDate(ul._transToROC_Year(rs.getString("AdjustDate")));
			obj.setVerify(rs.getString("Verify"));
			obj.setPropertyKind(rs.getString("PropertyKind"));
			obj.setFundType(rs.getString("FundType"));
			obj.setValuable(rs.getString("Valuable"));
			obj.setTaxCredit(rs.getString("TaxCredit"));
			obj.setOriginalBV(rs.getString("OriginalBV"));
			obj.setSourceDate(ul._transToROC_Year(rs.getString("SourceDate")));
			obj.setBuyDate(ul._transToROC_Year(rs.getString("BuyDate")));
			obj.setOldCArea(rs.getString("OldCArea"));
			obj.setOldSArea(rs.getString("OldSArea"));
			obj.setOldArea(rs.getString("OldArea"));
			obj.setOldHoldNume(rs.getString("OldHoldNume"));
			obj.setOldHoldDeno(rs.getString("OldHoldDeno"));
			obj.setOldHoldArea(rs.getString("OldHoldArea"));
			obj.setOldBookValue(rs.getString("OldBookValue"));
			obj.setOldNetValue(rs.getString("OldNetValue"));
			obj.setAdjustCArea(rs.getString("AdjustCArea"));
			obj.setAdjustSArea(rs.getString("AdjustSArea"));
			obj.setAdjustArea(rs.getString("AdjustArea"));
			obj.setAdjustHoldArea(rs.getString("AdjustHoldArea"));
			obj.setAddBookValue(rs.getString("AddBookValue"));
			obj.setAddNetValue(rs.getString("AddNetValue"));
			obj.setAddLimitYear(rs.getString("AddLimitYear"));
			obj.setOverLimitYear(rs.getString("OverLimitYear"));
			String sdate = ul._transToROC_Year(rs.getString("OldApportionEndYM"));
			String edate = ul._transToROC_Year(rs.getString("closing1ym"));
			int sm = Integer.parseInt(sdate.substring(0,3)) * 12 + Integer.parseInt(sdate.substring(3,5));
			int em = Integer.parseInt(edate.substring(0,3)) * 12 + Integer.parseInt(edate.substring(3,5));
			int overdate =em-sm;
			if(overdate<0) {
				overdate=0;
			}
			obj.setOverLimitYear2(String.valueOf(overdate));
			
			obj.setReduceBookValue(rs.getString("ReduceBookValue"));
			obj.setReduceNetValue(rs.getString("ReduceNetValue"));
			obj.setReduceAccumDepr(rs.getString("ReduceAccumDepr"));
			obj.setReduceLimitYear(rs.getString("ReduceLimitYear"));
			obj.setNewCArea(rs.getString("NewCArea"));
			obj.setNewSArea(rs.getString("NewSArea"));
			obj.setNewArea(rs.getString("NewArea"));
			obj.setNewHoldNume(rs.getString("NewHoldNume"));
			obj.setNewHoldDeno(rs.getString("NewHoldDeno"));
			obj.setNewHoldArea(rs.getString("NewHoldArea"));
			obj.setNewBookValue(rs.getString("NewBookValue"));
			obj.setNewNetValue(rs.getString("NewNetValue"));
			obj.setBookNotes(rs.getString("BookNotes"));
			obj.setKeepUnit(rs.getString("KeepUnit"));
			obj.setKeeper(rs.getString("Keeper"));
			obj.setUseUnit(rs.getString("UseUnit"));
			obj.setUserNo(rs.getString("UserNo"));
			obj.setUserNote(rs.getString("UserNote"));
			obj.setPlace1(rs.getString("Place1"));
			obj.setPlace(rs.getString("Place"));
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
			obj.setNewOtherLimitYear(rs.getString("newOtherLimitYear"));
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
			obj.setNotes(rs.getString("Notes"));
			obj.setEditID(rs.getString("EditID"));
			obj.setEditDate(ul._transToROC_Year(rs.getString("EditDate")));
			obj.setEditTime(rs.getString("EditTime"));
			obj.setOldApportionEndYM(ul._transToROC_Year(rs.getString("OldApportionEndYM")));
			obj.setNewApportionEndYM(ul._transToROC_Year(rs.getString("NewApportionEndYM")));
			
			obj.setEnterOrgName(rs.getString("EnterOrgName"));
			obj.setEngineeringNoName(ul._getEngineeringNoName(rs.getString("enterOrg"),rs.getString("engineeringNo")));
			obj.setPropertyNoName(rs.getString("propertyName"));
			obj.setClosing1ym(rs.getString("closing1ym"));
			if(!"".equals(Common.get(rs.getString("signNo")))){
				if (Common.get(rs.getString("signNo")).length() >= 1) {
					obj.setSignNo1(rs.getString("signNo").substring(0,1) + "000000");
				}
				if (Common.get(rs.getString("signNo")).length() >= 3) {
					obj.setSignNo2(rs.getString("signNo").substring(0,3) + "0000");
				}
				if (Common.get(rs.getString("signNo")).length() >= 7) {
					obj.setSignNo3(rs.getString("signNo").substring(0,7));
				}
				if (Common.get(rs.getString("signNo")).length() >= 12) {
					obj.setSignNo4(rs.getString("signNo").substring(7,12));
				}
				if (Common.get(rs.getString("signNo")).length() > 12) {
					obj.setSignNo5(rs.getString("signNo").substring(12));
				}
			}
			
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
		String sql=" select a.enterOrg,o.organSName," +
				" a.ownership, (select codename from sysca_code x where x.codekindid = 'OWA' and x.codeid = a.ownership ) as ownershipName," +
				"a.caseNo, a.propertyNo, a.serialNo, " +
				" a.signNo, "+
				"a.cause, (case a.cause when '1' then '資產重估調整' when '2' then '整建' else '其它' end) causeName, " +
				" (case a.propertyKind when '01' then '公務用' when '02' then '公共用' when '03' then '事業用' when '04' then '非公用' else '' end) propertyKind,"+
				"a.originalBV, " +
				"a.oldHoldArea, a.oldBookValue, " +
				"a.newHoldArea, a.newBookValue, " +
				"a.adjustType,(case a.adjustType when '1' then '增加' when '2' then '減少' else '' end) adjustTypeName," +
				"a.adjustHoldArea, a.adjustBookValue, a.bookNotes" +
				" from UNTBU_AdjustDetail a, UNTBU_AdjustProof b ,SYSCA_Organ o where 1=1 " +
				" and a.enterOrg=b.enterOrg and a.ownership=b.ownership and a.caseNo=b.caseNo " +
				" and a.enterOrg = o.organID " +
				" and a.enterOrg=" + Common.sqlChar(getEnterOrg()) +
				" and a.ownership=" + Common.sqlChar(getOwnership()) +			
				" and a.caseNo=" + Common.sqlChar(getCaseNo()) +
				" and a.differenceKind=" + Common.sqlChar(getDifferenceKind()) +
			   	" order by a.enterOrg ,a.ownership ,a.differenceKind, a.propertyNo, a.serialNo ";
			   
		ResultSet rs = db.querySQL(sql);
		while (rs.next()){
			counter++;
			String rowArray[]=new String[21];
			rowArray[0]=rs.getString("enterOrg");
			rowArray[1]=rs.getString("organSName");
			rowArray[2]=rs.getString("ownership"); 
			rowArray[3]=rs.getString("ownershipName");
			rowArray[4]=rs.getString("caseNo"); 
			rowArray[5]=rs.getString("propertyNo"); 
			rowArray[6]=rs.getString("serialNo"); 
			rowArray[7]=getSignDescName(rs.getString("signNo").substring(0,7)) + rs.getString("signNo").substring(7,12) + '-' + rs.getString("signNo").substring(12);
			rowArray[8]=rs.getString("cause"); 
			rowArray[9]=rs.getString("causeName");
			rowArray[10]=rs.getString("propertyKind"); 
			rowArray[11]=rs.getString("originalBV");
			rowArray[12]=rs.getString("oldHoldArea"); 
			rowArray[13]=rs.getString("oldBookValue");  
			rowArray[14]=rs.getString("adjustType");
			rowArray[15]=rs.getString("adjustTypeName");
			rowArray[16]=rs.getString("adjustHoldArea");
			rowArray[17]=rs.getString("adjustBookValue");
			rowArray[18]=rs.getString("newHoldArea");
			rowArray[19]=rs.getString("newBookValue");
			rowArray[20]=rs.getString("bookNotes");
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

public String check_bookvalue(String pp ,String ss){
	String reValue="";
	String sql = " select c.bookvalue from Untbu_building c "
			   + " where 1=1 "
			   + " and c.enterorg = " + Common.sqlChar(enterOrg)
			   + " and c.ownership = " + Common.sqlChar(ownership)
			   + " and c.differenceKind = " + Common.sqlChar(differenceKind)
			   + " and c.propertyno = " + Common.sqlChar(pp)
			   + " and c.serialno = " + Common.sqlChar(ss);
	Database db = new Database();
	ResultSet rs;
	try{
		rs = db.querySQL(sql);
		if(rs.next()){
			reValue=rs.getString("bookvlue");
		}
	} catch (Exception e) {
		e.printStackTrace();
	} finally {
		db.closeAll();
	}
	return reValue;
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