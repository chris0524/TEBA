/*
*<br>程式目的：建物主檔資料維護－基本資料
*<br>程式代號：untbu002f
*<br>程式日期：0940915
*<br>程式作者：clive.chang
*<br>--------------------------------------------------------
*<br>修改作者　　修改日期　　　修改目的
*<br>--------------------------------------------------------
*/
 
package unt.bu;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import unt.ch.UNTCH_Tools;
import util.Common;
import util.Database;
import util.Datetime;
import TDlib_Simple.com.src.SQLCreator;
import TDlib_Simple.tools.src.StringTools;

public class UNTBU002F extends UNTBU001Q {

	/**
	 * 僅提供 useBean , 請勿使用此建構子
	 */
	public UNTBU002F() {
		
	}
	
	public UNTBU002F(String caller) {
		this.setCaller(caller);
	}
	
	/** 呼叫此程式執行 insert or update 的呼叫者**/
	private String caller;
	public String getCaller() { return checkGet(caller); }
	public void setCaller(String caller) { this.caller = checkSet(caller); }

	private String sourceKindName;
	private String causeName;
	public String getSourceKindName() {return checkGet(sourceKindName);}
	public void setSourceKindName(String sourceKindName) {this.sourceKindName = checkSet(sourceKindName);}
	public String getCauseName() {return checkGet(causeName);}
	public void setCauseName(String causeName) {this.causeName = checkSet(causeName);}

	String initDtl;
	public String getInitDtl(){ return checkGet(initDtl); }
	public void setInitDtl(String s){ initDtl=checkSet(s); }
	
	private String originalApportionEndYM;
	private String apportionEndYM;
	public String getOriginalApportionEndYM() {return checkGet(originalApportionEndYM);}
	public void setOriginalApportionEndYM(String originalApportionEndYM) {this.originalApportionEndYM = checkSet(originalApportionEndYM);}	
	public String getApportionEndYM() {return checkGet(apportionEndYM);}
	public void setApportionEndYM(String apportionEndYM) {this.apportionEndYM = checkSet(apportionEndYM);}

	private String enterOrg;
	private String ownership;
	private String caseNo;
	private String differenceKind;
	private String engineeringNo;
	private String engineeringNoName;
	private String caseSerialNo;
	private String propertyNo;
	private String propertyNoName;
	private String serialNo;
	private String otherLimitYear;
	private String propertyName1;
	private String signNo;
	private String doorPlate1;
	private String doorPlate2;
	private String doorPlate4;
	private String doorPlatevillage1;
	private String doorPlatevillage2;
	private String doorplateRd1;
	private String doorplateRd2;
	private String doorplateSec;
	private String doorplateLn;
	private String doorplateAly;
	private String doorplateNo;
	private String doorplateFloor1;
	private String doorplateFloor2;
	private String buildStyle;
	private String cause;
	private String cause1;
	private String enterDate;
	private String dataState;
	private String reduceDate;
	private String reduceCause;
	private String reduceCause1;
	private String verify;
	private String propertyKind;
	private String fundType;
	private String valuable;
	private String taxCredit;
	private String originalCArea;
	private String originalSArea;
	private String originalArea;
	private String originalHoldNume;
	private String originalHoldDeno;
	private String originalHoldArea;
	private String CArea;
	private String SArea;
	private String area;
	private String holdNume;
	private String holdDeno;
	private String holdArea;
	private String originalBV;
	private String originalNote;
	private String accountingTitle;
	private String bookValue;
	private String netValue;
	private String fundsSource;
	private String fundsSource1;
	private String ownershipDate;
	private String ownershipCause;
	private String nonProof;
	private String proofDoc;
	private String ownershipNote;
	private String buildDate;
	private String floor1;
	private String floor2;
	private String stuff;
	private String buyDate;
	private String sourceKind;
	private String sourceDate;
	private String sourceDoc;
	private String manageOrg;
	private String originalDeprMethod;
	private String originalBuildFeeCB;
	private String originalDeprUnitCB;
	private String originalDeprPark;
	private String originalDeprUnit;
	private String originalDeprAccounts;
	private String originalScrapValue;
	private String originalDeprAmount;
	private String originalaccumdepr1;	// 本年度累計折舊
	private String originalaccumdepr2;	// 以前年度累計折舊
	private String originalAccumDepr;
	private String originalApportionMonth;
	private String originalMonthDepr;
	private String deprMethod;
	private String buildFeeCB;
	private String deprUnitCB;
	private String deprPark;
	private String deprUnit;
	private String deprAccounts;
	private String scrapValue;
	private String deprAmount;
	private String accumDepr;
	private String apportionMonth;
	private String monthDepr;
	private String noDeprSet;
	private String appraiseDate;
	private String originalMoveDate;
	private String originalKeepUnit;
	private String originalKeeper;
	private String originalUseUnit;
	private String originalUser;
	private String originalUserNote;
	private String originalPlace1;
	private String originalPlace;
	private String moveDate;
	private String keepUnit;
	private String keeper;
	private String useUnit;
	private String userNo;
	private String userNote;
	private String place1;
	private String place;
	private String useLicense;
	private String escrowOriValue;
	private String escrowOriAccumDepr;
	private String oldPropertyNo;
	private String oldSerialNo;
	private String notes;
	private String editID;
	private String editDate;
	private String editTime;
	private String picture;

	public String getPicture() {return checkGet(picture);}
	public void setPicture(String picture) {this.picture = checkSet(picture);}
	public String getEngineeringNoName() {return checkGet(engineeringNoName);}
	public void setEngineeringNoName(String engineeringNoName) {this.engineeringNoName = checkSet(engineeringNoName);}
	public String getEnterOrg() {return checkGet(enterOrg);}
	public void setEnterOrg(String enterOrg) {this.enterOrg = checkSet(enterOrg);}
	public String getOwnership() {return checkGet(ownership);}
	public void setOwnership(String ownership) {this.ownership = checkSet(ownership);}
	public String getCaseNo() {return checkGet(caseNo);}
	public void setCaseNo(String caseNo) {this.caseNo = checkSet(caseNo);}
	public String getDifferenceKind() {return checkGet(differenceKind);}
	public void setDifferenceKind(String differenceKind) {this.differenceKind = checkSet(differenceKind);}
	public String getEngineeringNo() {return checkGet(engineeringNo);}
	public void setEngineeringNo(String engineeringNo) {this.engineeringNo = checkSet(engineeringNo);}
	public String getCaseSerialNo() {return checkGet(caseSerialNo);}
	public void setCaseSerialNo(String caseSerialNo) {this.caseSerialNo = checkSet(caseSerialNo);}
	public String getPropertyNo() {return checkGet(propertyNo);}
	public void setPropertyNo(String propertyNo) {this.propertyNo = checkSet(propertyNo);}
	public String getPropertyNoName() {return checkGet(propertyNoName);}
	public void setPropertyNoName(String propertyNoName) {this.propertyNoName = checkSet(propertyNoName);}
	public String getSerialNo() {return checkGet(serialNo);}
	public void setSerialNo(String serialNo) {this.serialNo = checkSet(serialNo);}
	public String getOtherLimitYear() {return checkGet(otherLimitYear);}
	public void setOtherLimitYear(String otherLimitYear) {this.otherLimitYear = checkSet(otherLimitYear);}
	public String getPropertyName1() {return checkGet(propertyName1);}
	public void setPropertyName1(String propertyName1) {this.propertyName1 = checkSet(propertyName1);}
	public String getSignNo() {return checkGet(signNo);}
	public void setSignNo(String signNo) {this.signNo = checkSet(signNo);}
	public String getDoorPlate1() {return checkGet(doorPlate1);}
	public void setDoorPlate1(String doorPlate1) {this.doorPlate1 = checkSet(doorPlate1);}
	public String getDoorPlate2() {return checkGet(doorPlate2);}
	public void setDoorPlate2(String doorPlate2) {this.doorPlate2 = checkSet(doorPlate2);}
	public String getDoorPlate4() {return checkGet(doorPlate4);}
	public void setDoorPlate4(String doorPlate4) {this.doorPlate4 = checkSet(doorPlate4);}
	public String getDoorPlatevillage1() {return checkGet(doorPlatevillage1);}
	public void setDoorPlatevillage1(String doorPlatevillage1) {this.doorPlatevillage1 = checkSet(doorPlatevillage1);}
	public String getDoorPlatevillage2() {return checkGet(doorPlatevillage2);}
	public void setDoorPlatevillage2(String doorPlatevillage2) {this.doorPlatevillage2 = checkSet(doorPlatevillage2);}
	public String getDoorplateRd1() {return checkGet(doorplateRd1);}
	public void setDoorplateRd1(String doorplateRd1) {this.doorplateRd1 = checkSet(doorplateRd1);}
	public String getDoorplateRd2() {return checkGet(doorplateRd2);}
	public void setDoorplateRd2(String doorplateRd2) {this.doorplateRd2 = checkSet(doorplateRd2);}
	public String getDoorplateSec() {return checkGet(doorplateSec);}
	public void setDoorplateSec(String doorplateSec) {this.doorplateSec = checkSet(doorplateSec);}
	public String getDoorplateLn() {return checkGet(doorplateLn);}
	public void setDoorplateLn(String doorplateLn) {this.doorplateLn = checkSet(doorplateLn);}
	public String getDoorplateAly() {return checkGet(doorplateAly);}
	public void setDoorplateAly(String doorplateAly) {this.doorplateAly = checkSet(doorplateAly);}
	public String getDoorplateNo() {return checkGet(doorplateNo);}
	public void setDoorplateNo(String doorplateNo) {this.doorplateNo = checkSet(doorplateNo);}
	public String getDoorplateFloor1() {return checkGet(doorplateFloor1);}
	public void setDoorplateFloor1(String doorplateFloor1) {this.doorplateFloor1 = checkSet(doorplateFloor1);}
	public String getDoorplateFloor2() {return checkGet(doorplateFloor2);}
	public void setDoorplateFloor2(String doorplateFloor2) {this.doorplateFloor2 = checkSet(doorplateFloor2);}
	public String getBuildStyle() {return checkGet(buildStyle);}
	public void setBuildStyle(String buildStyle) {this.buildStyle = checkSet(buildStyle);}
	public String getCause() {return checkGet(cause);}
	public void setCause(String cause) {this.cause = checkSet(cause);}
	public String getCause1() {return checkGet(cause1);}
	public void setCause1(String cause1) {this.cause1 = checkSet(cause1);}
	public String getEnterDate() {return checkGet(enterDate);}
	public void setEnterDate(String enterDate) {this.enterDate = checkSet(enterDate);}
	public String getDataState() {return checkGet(dataState);}
	public void setDataState(String dataState) {this.dataState = checkSet(dataState);}
	public String getReduceDate() {return checkGet(reduceDate);}
	public void setReduceDate(String reduceDate) {this.reduceDate = checkSet(reduceDate);}
	public String getReduceCause() {return checkGet(reduceCause);}
	public void setReduceCause(String reduceCause) {this.reduceCause = checkSet(reduceCause);}
	public String getReduceCause1() {return checkGet(reduceCause1);}
	public void setReduceCause1(String reduceCause1) {this.reduceCause1 = checkSet(reduceCause1);}
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
	public String getOriginalCArea() {return checkGet(originalCArea);}
	public void setOriginalCArea(String originalCArea) {this.originalCArea = checkSet(originalCArea);}
	public String getOriginalSArea() {return checkGet(originalSArea);}
	public void setOriginalSArea(String originalSArea) {this.originalSArea = checkSet(originalSArea);}
	public String getOriginalArea() {return checkGet(originalArea);}
	public void setOriginalArea(String originalArea) {this.originalArea = checkSet(originalArea);}
	public String getOriginalHoldNume() {return checkGet(originalHoldNume);}
	public void setOriginalHoldNume(String originalHoldNume) {this.originalHoldNume = checkSet(originalHoldNume);}
	public String getOriginalHoldDeno() {return checkGet(originalHoldDeno);}
	public void setOriginalHoldDeno(String originalHoldDeno) {this.originalHoldDeno = checkSet(originalHoldDeno);}
	public String getOriginalHoldArea() {return checkGet(originalHoldArea);}
	public void setOriginalHoldArea(String originalHoldArea) {this.originalHoldArea = checkSet(originalHoldArea);}
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
	public String getOriginalBV() {return checkGet(originalBV);}
	public void setOriginalBV(String originalBV) {this.originalBV = checkSet(originalBV);}
	public String getOriginalNote() {return checkGet(originalNote);}
	public void setOriginalNote(String originalNote) {this.originalNote = checkSet(originalNote);}
	public String getAccountingTitle() {return checkGet(accountingTitle);}
	public void setAccountingTitle(String accountingTitle) {this.accountingTitle = checkSet(accountingTitle);}
	public String getBookValue() {return checkGet(bookValue);}
	public void setBookValue(String bookValue) {this.bookValue = checkSet(bookValue);}
	public String getNetValue() {return checkGet(netValue);}
	public void setNetValue(String netValue) {this.netValue = checkSet(netValue);}
	public String getFundsSource() {return checkGet(fundsSource);}
	public void setFundsSource(String fundsSource) {this.fundsSource = checkSet(fundsSource);}
	public String getFundsSource1() {return checkGet(fundsSource1);}
	public void setFundsSource1(String fundsSource1) {this.fundsSource1 = checkSet(fundsSource1);}
	public String getOwnershipDate() {return checkGet(ownershipDate);}
	public void setOwnershipDate(String ownershipDate) {this.ownershipDate = checkSet(ownershipDate);}
	public String getOwnershipCause() {return checkGet(ownershipCause);}
	public void setOwnershipCause(String ownershipCause) {this.ownershipCause = checkSet(ownershipCause);}
	public String getNonProof() {return checkGet(nonProof);}
	public void setNonProof(String nonProof) {this.nonProof = checkSet(nonProof);}
	public String getProofDoc() {return checkGet(proofDoc);}
	public void setProofDoc(String proofDoc) {this.proofDoc = checkSet(proofDoc);}
	public String getOwnershipNote() {return checkGet(ownershipNote);}
	public void setOwnershipNote(String ownershipNote) {this.ownershipNote = checkSet(ownershipNote);}
	public String getBuildDate() {return checkGet(buildDate);}
	public void setBuildDate(String buildDate) {this.buildDate = checkSet(buildDate);}
	public String getFloor1() {return checkGet(floor1);}
	public void setFloor1(String floor1) {this.floor1 = checkSet(floor1);}
	public String getFloor2() {return checkGet(floor2);}
	public void setFloor2(String floor2) {this.floor2 = checkSet(floor2);}
	public String getStuff() {return checkGet(stuff);}
	public void setStuff(String stuff) {this.stuff = checkSet(stuff);}
	public String getBuyDate() {return checkGet(buyDate);}
	public void setBuyDate(String buyDate) {this.buyDate = checkSet(buyDate);}
	public String getSourceKind() {return checkGet(sourceKind);}
	public void setSourceKind(String sourceKind) {this.sourceKind = checkSet(sourceKind);}
	public String getSourceDate() {return checkGet(sourceDate);}
	public void setSourceDate(String sourceDate) {this.sourceDate = checkSet(sourceDate);}
	public String getSourceDoc() {return checkGet(sourceDoc);}
	public void setSourceDoc(String sourceDoc) {this.sourceDoc = checkSet(sourceDoc);}
	public String getManageOrg() {return checkGet(manageOrg);}
	public void setManageOrg(String manageOrg) {this.manageOrg = checkSet(manageOrg);}
	public String getOriginalDeprMethod() {return checkGet(originalDeprMethod);}
	public void setOriginalDeprMethod(String originalDeprMethod) {this.originalDeprMethod = checkSet(originalDeprMethod);}
	public String getOriginalBuildFeeCB() {return checkGet(originalBuildFeeCB);}
	public void setOriginalBuildFeeCB(String originalBuildFeeCB) {this.originalBuildFeeCB = checkSet(originalBuildFeeCB);}
	public String getOriginalDeprUnitCB() {return checkGet(originalDeprUnitCB);}
	public void setOriginalDeprUnitCB(String originalDeprUnitCB) {this.originalDeprUnitCB = checkSet(originalDeprUnitCB);}
	public String getOriginalDeprPark() {return checkGet(originalDeprPark);}
	public void setOriginalDeprPark(String originalDeprPark) {this.originalDeprPark = checkSet(originalDeprPark);}
	public String getOriginalDeprUnit() {return checkGet(originalDeprUnit);}
	public void setOriginalDeprUnit(String originalDeprUnit) {this.originalDeprUnit = checkSet(originalDeprUnit);}
	public String getOriginalDeprAccounts() {return checkGet(originalDeprAccounts);}
	public void setOriginalDeprAccounts(String originalDeprAccounts) {this.originalDeprAccounts = checkSet(originalDeprAccounts);}
	public String getOriginalScrapValue() {return checkGet(originalScrapValue);}
	public void setOriginalScrapValue(String originalScrapValue) {this.originalScrapValue = checkSet(originalScrapValue);}
	public String getOriginalDeprAmount() {return checkGet(originalDeprAmount);}
	public void setOriginalDeprAmount(String originalDeprAmount) {this.originalDeprAmount = checkSet(originalDeprAmount);}
	public String getOriginalaccumdepr1() { return checkGet(originalaccumdepr1); }
	public void setOriginalaccumdepr1(String originalaccumdepr1) { this.originalaccumdepr1 = checkSet(originalaccumdepr1); }
	public String getOriginalaccumdepr2() { return checkGet(originalaccumdepr2); }
	public void setOriginalaccumdepr2(String originalaccumdepr2) { this.originalaccumdepr2 = checkSet(originalaccumdepr2); }
	public String getOriginalAccumDepr() {return checkGet(originalAccumDepr);}
	public void setOriginalAccumDepr(String originalAccumDepr) {this.originalAccumDepr = checkSet(originalAccumDepr);}
	public String getOriginalApportionMonth() {return checkGet(originalApportionMonth);}
	public void setOriginalApportionMonth(String originalApportionMonth) {this.originalApportionMonth = checkSet(originalApportionMonth);}
	public String getOriginalMonthDepr() {return checkGet(originalMonthDepr);}
	public void setOriginalMonthDepr(String originalMonthDepr) {this.originalMonthDepr = checkSet(originalMonthDepr);}
	public String getDeprMethod() {return checkGet(deprMethod);}
	public void setDeprMethod(String deprMethod) {this.deprMethod = checkSet(deprMethod);}
	public String getBuildFeeCB() {return checkGet(buildFeeCB);}
	public void setBuildFeeCB(String buildFeeCB) {this.buildFeeCB = checkSet(buildFeeCB);}
	public String getDeprUnitCB() {return checkGet(deprUnitCB);}
	public void setDeprUnitCB(String deprUnitCB) {this.deprUnitCB = checkSet(deprUnitCB);}
	public String getDeprPark() {return checkGet(deprPark);}
	public void setDeprPark(String deprPark) {this.deprPark = checkSet(deprPark);}
	public String getDeprUnit() {return checkGet(deprUnit);}
	public void setDeprUnit(String deprUnit) {this.deprUnit = checkSet(deprUnit);}
	public String getDeprAccounts() {return checkGet(deprAccounts);}
	public void setDeprAccounts(String deprAccounts) {this.deprAccounts = checkSet(deprAccounts);}
	public String getScrapValue() {return checkGet(scrapValue);}
	public void setScrapValue(String scrapValue) {this.scrapValue = checkSet(scrapValue);}
	public String getDeprAmount() {return checkGet(deprAmount);}
	public void setDeprAmount(String deprAmount) {this.deprAmount = checkSet(deprAmount);}
	public String getAccumDepr() {return checkGet(accumDepr);}
	public void setAccumDepr(String accumDepr) {this.accumDepr = checkSet(accumDepr);}
	public String getApportionMonth() {return checkGet(apportionMonth);}
	public void setApportionMonth(String apportionMonth) {this.apportionMonth = checkSet(apportionMonth);}
	public String getMonthDepr() {return checkGet(monthDepr);}
	public void setMonthDepr(String monthDepr) {this.monthDepr = checkSet(monthDepr);}
	public String getNoDeprSet() {return checkGet(noDeprSet);}
	public void setNoDeprSet(String noDeprSet) {this.noDeprSet = checkSet(noDeprSet);}
	public String getAppraiseDate() {return checkGet(appraiseDate);}
	public void setAppraiseDate(String appraiseDate) {this.appraiseDate = checkSet(appraiseDate);}
	public String getOriginalMoveDate() {return checkGet(originalMoveDate);}
	public void setOriginalMoveDate(String originalMoveDate) {this.originalMoveDate = checkSet(originalMoveDate);}
	public String getOriginalKeepUnit() {return checkGet(originalKeepUnit);}
	public void setOriginalKeepUnit(String originalKeepUnit) {this.originalKeepUnit = checkSet(originalKeepUnit);}
	public String getOriginalKeeper() {return checkGet(originalKeeper);}
	public void setOriginalKeeper(String originalKeeper) {this.originalKeeper = checkSet(originalKeeper);}
	public String getOriginalUseUnit() {return checkGet(originalUseUnit);}
	public void setOriginalUseUnit(String originalUseUnit) {this.originalUseUnit = checkSet(originalUseUnit);}
	public String getOriginalUser() {return checkGet(originalUser);}
	public void setOriginalUser(String originalUser) {this.originalUser = checkSet(originalUser);}
	public String getOriginalUserNote() {return checkGet(originalUserNote);}
	public void setOriginalUserNote(String originalUserNote) {this.originalUserNote = checkSet(originalUserNote);}
	public String getOriginalPlace1() {return checkGet(originalPlace1);}
	public void setOriginalPlace1(String originalPlace1) {this.originalPlace1 = checkSet(originalPlace1);}
	public String getOriginalPlace() {return checkGet(originalPlace);}
	public void setOriginalPlace(String originalPlace) {this.originalPlace = checkSet(originalPlace);}
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
	public String getUseLicense() {return checkGet(useLicense);}
	public void setUseLicense(String useLicense) {this.useLicense = checkSet(useLicense);}
	public String getEscrowOriValue() {return checkGet(escrowOriValue);}
	public void setEscrowOriValue(String escrowOriValue) {this.escrowOriValue = checkSet(escrowOriValue);}
	public String getEscrowOriAccumDepr() {return checkGet(escrowOriAccumDepr);}
	public void setEscrowOriAccumDepr(String escrowOriAccumDepr) {this.escrowOriAccumDepr = checkSet(escrowOriAccumDepr);}
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

	private String enterOrgName;
	public String getEnterOrgName() {return checkGet(enterOrgName);}
	public void setEnterOrgName(String enterOrgName) {this.enterOrgName = checkSet(enterOrgName);}

	private String signNo1;
	private String signNo2;
	private String signNo3;
	private String signNo4;
	private String signNo5;
	public String getSignNo1() {return checkGet(signNo1);}
	public void setSignNo1(String signNo1) {this.signNo1 = checkSet(signNo1);}
	public String getSignNo2() {return checkGet(signNo2);}
	public void setSignNo2(String signNo2) {this.signNo2 = checkSet(signNo2);}
	public String getSignNo3() {return checkGet(signNo3);}
	public void setSignNo3(String signNo3) {this.signNo3 = checkSet(signNo3);}
	public String getSignNo4() {return checkGet(signNo4);}
	public void setSignNo4(String signNo4) {this.signNo4 = checkSet(signNo4);}
	public String getSignNo5() {return checkGet(signNo5);}
	public void setSignNo5(String signNo5) {this.signNo5 = checkSet(signNo5);}

	
	private String manageOrgName;
	private String propertyUnit;
	private String material;
	public String getManageOrgName() {return checkGet(manageOrgName);}
	public void setManageOrgName(String manageOrgName) {this.manageOrgName = checkSet(manageOrgName);}
	public String getPropertyUnit() {return checkGet(propertyUnit);}
	public void setPropertyUnit(String propertyUnit) {this.propertyUnit = checkSet(propertyUnit);}
	public String getMaterial() {return checkGet(material);}
	public void setMaterial(String material) {this.material = checkSet(material);}

	private String originalPlace1Name;
	private String place1Name;
	public String getOriginalPlace1Name() {return checkGet(originalPlace1Name);}
	public void setOriginalPlace1Name(String originalPlace1Name) {this.originalPlace1Name = checkSet(originalPlace1Name);}
	public String getPlace1Name() {	return checkGet(place1Name);}
	public void setPlace1Name(String place1Name) {this.place1Name = checkSet(place1Name);	}

	private String originalDeprUnit1;
	private String deprUnit1;	
	public String getOriginalDeprUnit1() {return checkGet(originalDeprUnit1);}
	public void setOriginalDeprUnit1(String originalDeprUnit1) {this.originalDeprUnit1 = checkSet(originalDeprUnit1);}
	public String getDeprUnit1() {return checkGet(deprUnit1);}
	public void setDeprUnit1(String deprUnit1) {this.deprUnit1 = checkSet(deprUnit1);}
	
	private String monthDepr1;
	private String originalMonthDepr1;
	public String getMonthDepr1() {return checkGet(monthDepr1);}
	public void setMonthDepr1(String monthDepr1) {this.monthDepr1 = checkSet(monthDepr1);}
	public String getOriginalMonthDepr1() {return checkGet(originalMonthDepr1);}
	public void setOriginalMonthDepr1(String originalMonthDepr1) {this.originalMonthDepr1 = checkSet(originalMonthDepr1);}
	
	private String originalLimitYear;
	public String getOriginalLimitYear() {return checkGet(originalLimitYear);}
	public void setOriginalLimitYear(String originalLimitYear) {this.originalLimitYear = checkSet(originalLimitYear);}

	private String otherPropertyUnit;
	private String otherMaterial;
	public String getOtherPropertyUnit() {return checkGet(otherPropertyUnit);}	
	public void setOtherPropertyUnit(String otherPropertyUnit) {this.otherPropertyUnit = checkSet(otherPropertyUnit);}	
	public String getOtherMaterial() {return checkGet(otherMaterial);}	
	public void setOtherMaterial(String otherMaterial) {this.otherMaterial = checkSet(otherMaterial);}	
	
	// 問題單2277 加上自行設定累計折舊
	private String individualSetDepr;
	private String verifyYM;
	public String getIndividualSetDepr() { return checkGet(individualSetDepr); }
	public void setIndividualSetDepr(String individualSetDepr) { this.individualSetDepr = checkSet(individualSetDepr); }
	public String getVerifyYM() { return checkGet(verifyYM); }
	public void setVerifyYM(String verifyYM) {this.verifyYM = checkSet(verifyYM); }
	
	//問題單2314 加上個別填寫折舊資料
	private String selfdepr;
	public String getSelfdepr() { return checkGet(selfdepr); }
	public void setSelfdepr(String selfdepr) { this.selfdepr = checkSet(selfdepr); }
	
	@Override
	public void insert() throws Exception{
		Database db = new Database();
		try {			
			if (!beforeExecCheck(getInsertCheckSQL())){
				setStateInsertError();
				throw new Exception(getErrorMsg());
			}else{
				setEditDate(Datetime.getYYYMMDD());
				setEditTime(Datetime.getHHMMSS());	
				db.excuteSQL_UnicodePrefix(getInsertSQL());		
				setStateInsertSuccess();
				setErrorMsg("新增完成");
			}

		} catch (Exception e) {
			throw e;
		} finally {
			db.closeAll();
		}			
	}
	
	@Override
	public void update() throws Exception{
		Database db = new Database();
		String tempSql[] = new String[1];
		try {			
			if (!beforeExecCheck(getUpdateCheckSQL())){
				setStateUpdateError();
				throw new Exception(getErrorMsg());
			}else{
				db.excuteSQL_UnicodePrefix(getUpdateSQL());
				setStateUpdateSuccess();
				setErrorMsg("修改完成");
			}

		} catch (Exception e) {
			throw e;
		} finally {
			db.closeAll();
		}			
	}
	
	private Map getPKMap(){
		Map map = new HashMap();
		
		map.put("enterOrg", getEnterOrg());
		map.put("ownership", getOwnership());
		map.put("caseNo", getCaseNo());
		map.put("differenceKind", getDifferenceKind());
		map.put("propertyNo", getPropertyNo());
		map.put("serialNo", getSerialNo());
		
		return map;
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private Map getRecordMap(String action){
		Map map = new HashMap();
		UNTCH_Tools ul = new UNTCH_Tools();
		Database db = new Database();
		
		try {
			if (!"UNTCH001F02_2".equals(caller)) { // 財產維護不給異動以下欄位, 以免被清空
				
				long originalAccumDepr = Common.getLong(this.getOriginalAccumDepr());
				long netValue = Common.getLong(this.getNetValue());
				
				map.put("enterDate",ul._transToCE_Year(getEnterDate())); // 入帳日期
				map.put("netValue", netValue - originalAccumDepr);
				map.put("bookValue",getBookValue());
				
				// 原始保管資料
				map.put("originalMoveDate",ul._transToCE_Year(getOriginalMoveDate()));
				map.put("originalKeepUnit",getOriginalKeepUnit());
				map.put("originalKeeper",getOriginalKeeper());
				map.put("originalUseUnit",getOriginalUseUnit());
				map.put("originalUser",getOriginalUser());
				map.put("originalUserNote",getOriginalUserNote());
				map.put("originalPlace1",getOriginalPlace1());
				map.put("originalPlace",getOriginalPlace());			
				// 折舊資料
				map.put("originalDeprMethod",getOriginalDeprMethod());
				map.put("originalBuildFeeCB",getOriginalBuildFeeCB());
				map.put("originalDeprUnitCB",getOriginalDeprUnitCB());
				map.put("originalDeprPark",getOriginalDeprPark());
				map.put("originalDeprUnit",getOriginalDeprUnit());
				map.put("originalDeprAccounts",getOriginalDeprAccounts());
				map.put("originalScrapValue",getOriginalScrapValue());
				map.put("originalDeprAmount",getOriginalDeprAmount());
				map.put("originalaccumdepr1", this.getOriginalaccumdepr1());
				map.put("originalaccumdepr2", this.getOriginalaccumdepr2());
				map.put("originalAccumDepr", originalAccumDepr);
				map.put("originalApportionMonth",getOriginalApportionMonth());
				map.put("originalMonthDepr",getOriginalMonthDepr());
				map.put("originalMonthDepr1",getOriginalMonthDepr1());
				map.put("deprMethod",getDeprMethod());
				map.put("buildFeeCB",getBuildFeeCB());
				map.put("deprUnitCB",getDeprUnitCB());
				map.put("deprPark",getDeprPark());
				map.put("deprUnit",getDeprUnit());
				map.put("deprAccounts",getDeprAccounts());
				map.put("scrapValue",getScrapValue());
				map.put("deprAmount",getDeprAmount());
				map.put("accumDepr",getAccumDepr());
				map.put("apportionMonth",getApportionMonth());
				map.put("monthDepr",getMonthDepr());
				map.put("monthDepr1",getMonthDepr1());
				map.put("noDeprSet",getNoDeprSet());
				map.put("escrowOriValue",getEscrowOriValue());
				map.put("escrowOriAccumDepr",getEscrowOriAccumDepr());
				map.put("originalDeprUnit1", getOriginalDeprUnit1());
				map.put("deprUnit1", getDeprUnit1());
				map.put("originalApportionEndYM", ul._transToCE_Year(getOriginalApportionEndYM()));
				map.put("apportionEndYM", ul._transToCE_Year(getApportionEndYM()));
				map.put("enterOrg",getEnterOrg());
				map.put("ownership",getOwnership());
				map.put("caseNo",getCaseNo());
				map.put("differenceKind",getDifferenceKind());
				map.put("engineeringNo",getEngineeringNo());
				map.put("caseSerialNo",getCaseSerialNo());
				map.put("propertyNo",getPropertyNo());
				map.put("serialNo",getSerialNo());
				map.put("otherLimitYear", this.getOtherLimitYear());
				map.put("originalLimitYear", this.getOriginalLimitYear());
				map.put("signNo",getSignNo());
				map.put("doorPlate1",getDoorPlate1());
				map.put("doorPlate2",getDoorPlate2());
				map.put("doorPlate4",getDoorPlate4());
				map.put("doorPlatevillage1",getDoorPlatevillage1());
				map.put("doorPlatevillage2",getDoorPlatevillage2());
				map.put("doorplateRd1",getDoorplateRd1());
				map.put("doorplateRd2",getDoorplateRd2());
				map.put("doorplateSec",getDoorplateSec());
				map.put("doorplateLn",getDoorplateLn());
				map.put("doorplateAly",getDoorplateAly());
				map.put("doorplateNo",getDoorplateNo());
				map.put("doorplateFloor1",getDoorplateFloor1());
				map.put("doorplateFloor2",getDoorplateFloor2());
				map.put("buildStyle",getBuildStyle());
				map.put("cause",getCause());
				map.put("cause1",getCause1());		
				map.put("dataState",getDataState());
				map.put("reduceDate",ul._transToCE_Year(getReduceDate()));
				map.put("reduceCause",getReduceCause());
				map.put("reduceCause1",getReduceCause1());
				map.put("verify",getVerify());
				map.put("propertyKind",getPropertyKind());
				map.put("fundType",getFundType());
				map.put("valuable",getValuable());
				map.put("taxCredit",getTaxCredit());
				map.put("originalCArea",getOriginalCArea());
				map.put("originalSArea",getOriginalSArea());
				map.put("originalArea",getOriginalArea());
				map.put("originalHoldNume",getOriginalHoldNume());
				map.put("originalHoldDeno",getOriginalHoldDeno());
				map.put("originalHoldArea",getOriginalHoldArea());
				map.put("cArea",getCArea());
				map.put("sArea",getSArea());
				map.put("area",getArea());
				map.put("holdNume",getHoldNume());
				map.put("holdDeno",getHoldDeno());
				map.put("holdArea",getHoldArea());
				map.put("originalBV",getOriginalBV());
				map.put("originalNote",getOriginalNote());
				map.put("accountingTitle",getAccountingTitle());
				map.put("fundsSource1",getFundsSource1());
				map.put("ownershipDate",ul._transToCE_Year(getOwnershipDate()));
				map.put("ownershipCause",getOwnershipCause());
				map.put("nonProof",getNonProof());
				map.put("proofDoc",getProofDoc());
				map.put("ownershipNote",getOwnershipNote());
				map.put("buildDate",ul._transToCE_Year(getBuildDate()));
				map.put("floor1",getFloor1());
				map.put("floor2",getFloor2());
				map.put("stuff",getStuff());
				map.put("buyDate",ul._transToCE_Year(getBuyDate()));
				map.put("sourceKind",getSourceKind());
				map.put("sourceDate",ul._transToCE_Year(getSourceDate()));
				map.put("sourceDoc",getSourceDoc());
				map.put("manageOrg",getManageOrg());
				map.put("appraiseDate",ul._transToCE_Year(getAppraiseDate()));		
				map.put("moveDate",ul._transToCE_Year(getMoveDate()));
				map.put("keepUnit",getKeepUnit());
				map.put("keeper",getKeeper());
				map.put("useUnit",getUseUnit());
				map.put("userNo",getUserNo());
				map.put("useLicense",getUseLicense());
				map.put("oldPropertyNo",getOldPropertyNo());
				map.put("oldSerialNo",getOldSerialNo());
				map.put("otherpropertyunit", (!"".equals(getOtherPropertyUnit()))? getOtherPropertyUnit(): getPropertyUnit());
				map.put("othermaterial", (!"".equals(getOtherMaterial()))? getOtherMaterial(): getMaterial());
			} else {
				// 問題單2179 開放修改建物標示
				if (this.getSignNo() != null && this.getSignNo().length() == 15) {
					String sql= " select * " +
							" from UNTBU_BUILDING a " +
							" where a.enterorg = " + Common.sqlChar(this.getEnterOrg()) +
							" and a.ownership = " + Common.sqlChar(this.getOwnership()) +
							" and a.differencekind = " + Common.sqlChar(this.getDifferenceKind()) +
							" and a.propertyno = " + Common.sqlChar(this.getPropertyNo()) +
							" and a.serialno = " + Common.sqlChar(this.getSerialNo()) +
							" and isnull(a.signno, '') = '' ";
					ResultSet rs = db.querySQL_NoChange(sql);
					
					while(rs.next()) {
						map.put("signNo", getSignNo());									
					}
				}
			}
			
			map.put("place1",getPlace1());
			map.put("place",getPlace());
			map.put("userNote",getUserNote());
			map.put("propertyName1",getPropertyName1());
			map.put("fundsSource",getFundsSource());
			map.put("picture", getPicture());
			map.put("notes",getNotes());
			map.put("editID",getEditID());
			map.put("editDate",ul._transToCE_Year(getEditDate()));
			map.put("editTime",getEditTime());
			map.put("individualSetDepr", getIndividualSetDepr());
			map.put("verifyYM", ul._transToCE_Year(getVerifyYM()));
			map.put("selfdepr", getSelfdepr());
		
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.closeAll();
		}
		
		return map;
	}
	
	
//傳回執行insert前之檢查sql
protected String[][] getInsertCheckSQL(){
	String[][] checkSQLArray = new String[3][4];	
	
	checkSQLArray[1][0]="";
	checkSQLArray[1][1]="";
	checkSQLArray[1][2]="";
	checkSQLArray[1][3]="";
	
	checkSQLArray[2][0]="";
	checkSQLArray[2][1]="";
	checkSQLArray[2][2]="";
	checkSQLArray[2][3]="";
	
	setDataState("1");
	setCArea(originalCArea);
	setSArea(originalSArea);
	setArea(originalArea);
	setHoldNume(originalHoldNume);
	setHoldDeno(originalHoldDeno);
	setHoldArea(originalHoldArea);
	setBookValue(originalBV);
	
	//取得分號
	Database db = new Database();
	ResultSet rs;	
	String sql="select (max(serialno)+1) as serialNo from UNTBU_BUILDING " +
		" where enterorg = " + Common.sqlChar(enterOrg) + 
		" and ownership = " + Common.sqlChar(ownership) + 
		" and propertyno = " + Common.sqlChar(propertyNo) +
		" and differenceKind = " + Common.sqlChar(differenceKind) + 
		"";		
	
	try {		
		rs = db.querySQL(sql);
		if (rs.next()){
			if("".equals(checkGet(rs.getString("serialNo")))){
				setSerialNo("0000001");
			}else{
				setSerialNo(Common.formatFrontZero(rs.getString("serialNo"),7));	
			}		    
		} else {
			setSerialNo("0000001");
		}
		
//		sql = " select ownership, verify, closing from UNTBU_ADDPROOF where 1=1 " +
//			" and enterorg = " + Common.sqlChar(enterOrg) +
//			" and ownership = " + Common.sqlChar(ownership) +
//			" and caseno = " + Common.sqlChar(caseNo) +
//			"";
//
//		rs = db.querySQL(sql);
//		if (rs.next()) {
//			setOwnership(rs.getString("ownership"));
//			setVerify(rs.getString("verify"));
//			setClosing(rs.getString("closing"));
//		} else {
//			checkSQLArray[2][0]=" select 222 as checkresult from dual ";
//			checkSQLArray[2][1]=">";
//			checkSQLArray[2][2]="1";
//			checkSQLArray[2][3]="查無此增加單編號，請重新輸入！";			
//		}		
		
	} catch (Exception e) {
		e.printStackTrace();
	} finally {
		db.closeAll();
	}
	

 	checkSQLArray[0][0]=" select count(*) as checkresult from UNTBU_BUILDING where 1=1 " + 
		" and enterorg = " + Common.sqlChar(enterOrg) + 
		" and ownership = " + Common.sqlChar(ownership) + 
		" and propertyno = " + Common.sqlChar(propertyNo) + 
		" and serialno = " + Common.sqlChar(serialNo) +
		" and differenceKind = " + Common.sqlChar(differenceKind) + 
		"";
	checkSQLArray[0][1]=">";
	checkSQLArray[0][2]="0";
	checkSQLArray[0][3]="該筆資料己重複，請重新輸入！";
/*	
	//標示不能重複
	if (!"".equals(getSignNo1())) {	
	 	checkSQLArray[1][0]=" select count(*) as checkResult from UNTBU_Building where 1=1 " + 
			" and enterOrg = " + Common.sqlChar(enterOrg) + 
			" and ownership = " + Common.sqlChar(ownership) + 
			" and signNo = " + Common.sqlChar(signNo1.substring(0,1)+signNo2.substring(1,3)+signNo3.substring(3,7)+signNo4+signNo5) +		
			" and dataState = " + Common.sqlChar(dataState) +
			""; 		 		 
		checkSQLArray[1][1]=">";
		checkSQLArray[1][2]="0";
		checkSQLArray[1][3]="建物標示己重複，請重新輸入！";	
	} else {
		checkSQLArray[1][0]="";
		checkSQLArray[1][1]="";
		checkSQLArray[1][2]="";
		checkSQLArray[1][3]="";		
	}
*/	
	
	return checkSQLArray;
}


//傳回insert sql
protected String[] getInsertSQL(){
	String[] execSQLArray = new String[1];
	execSQLArray[0] = new SQLCreator()._obtainSQLforInsert("UNTBU_BUILDING", getPKMap(), getRecordMap("insert"));
	return execSQLArray;
}


//傳回update sql
protected String[] getUpdateSQL(){	
	if("".equals(getSignNo())){
		setSignNo(getSignNo3() + getSignNo4() + getSignNo5());
	}
	
	
	String[] execSQLArray = new String[1];
	
	execSQLArray[0] = new SQLCreator()._obtainSQLforUpdate("UNTBU_BUILDING", getPKMap(), getRecordMap("update"));
	
	Database db=new Database();
	try{
		String[] sourceArray={"UNTBU_BASE", "UNTBU_ADJUSTDETAIL", "UNTBU_REDUCEDETAIL"};
		String sql=null;
		
		for(int i=0;i<sourceArray.length;i++){
			sql="UPDATE "+sourceArray[i].toString()+" SET"+
						" signno = '" + getSignNo() + "'" +
						" where 1=1 " +
						" and enterorg = " + Common.sqlChar(enterOrg) +
						" and ownership = " + Common.sqlChar(ownership) +
						" and differenceKind = " + Common.sqlChar(differenceKind) +
						" and propertyno = " + Common.sqlChar(propertyNo) +
						" and serialno = " + Common.sqlChar(serialNo);	
			db.excuteSQL(new String[]{sql});
		}
		
	}catch(Exception e){
		e.printStackTrace();
	}finally{
		db.closeAll();
	}
	
	return execSQLArray;
}


//傳回執行Delete前之檢查sql
protected String[][] getDeleteCheckSQL(){
    String[][] checkSQLArray = new String[1][4];
    
 	checkSQLArray[0][0]=" select count(*) as checkresult from UNTBU_ADDPROOF where verify='Y' " + 
		" and enterorg = " + Common.sqlChar(enterOrg) + 
		" and ownership = " + Common.sqlChar(ownership) + 
		" and caseno = " + Common.sqlChar(caseNo) +
		" and differenceKind = " + Common.sqlChar(differenceKind) + 
		"";
	checkSQLArray[0][1]=">";
	checkSQLArray[0][2]="0";
	checkSQLArray[0][3]="對不起，該筆增加單已入帳或月結，無法刪除！";	
	
	return checkSQLArray;
}


//傳回delete sql
protected String[] getDeleteSQL(){
	StringBuffer sbSQL = new StringBuffer();
	
	sbSQL.append(" delete from UNTBU_MANAGE " ).append(
	" where enterorg = " ).append( Common.sqlChar(enterOrg) ).append(
	" and ownership = " ).append( Common.sqlChar(ownership) ).append(
	" and differenceKind = " ).append( Common.sqlChar(differenceKind) ).append(
	" and propertyno = " ).append( Common.sqlChar(propertyNo) ).append(
	" and serialno = " ).append( Common.sqlChar(serialNo) ).append(
	":;:");

	sbSQL.append(" delete from UNTBU_FLOOR " ).append(
	" where enterorg = " ).append( Common.sqlChar(enterOrg) ).append(
	" and ownership = " ).append( Common.sqlChar(ownership) ).append(
	" and differenceKind = " ).append( Common.sqlChar(differenceKind) ).append(
	" and propertyno = " ).append( Common.sqlChar(propertyNo) ).append(
	" and serialno = " ).append( Common.sqlChar(serialNo) ).append(
	":;:");
	
	sbSQL.append(" delete from UNTBU_ATTACHMENT1 " ).append(
	" where enterorg = " ).append( Common.sqlChar(enterOrg) ).append(
	" and ownership = " ).append( Common.sqlChar(ownership) ).append(
	" and differenceKind = " ).append( Common.sqlChar(differenceKind) ).append(
	" and propertyno = " ).append( Common.sqlChar(propertyNo) ).append(
	" and serialno = " ).append( Common.sqlChar(serialNo) ).append(
	":;:");
	
	sbSQL.append(" delete from UNTBU_BASE " ).append(
	" where enterorg = " ).append( Common.sqlChar(enterOrg) ).append(
	" and ownership = " ).append( Common.sqlChar(ownership) ).append(
	" and differenceKind = " ).append( Common.sqlChar(differenceKind) ).append(
	" and propertyno = " ).append( Common.sqlChar(propertyNo) ).append(
	" and serialno = " ).append( Common.sqlChar(serialNo) ).append(
	":;:");
	
	sbSQL.append(" delete from UNTBU_COMMONUSE " ).append(
	" where enterorg = " ).append( Common.sqlChar(enterOrg) ).append(
	" and ownership = " ).append( Common.sqlChar(ownership) ).append(
	" and differenceKind = " ).append( Common.sqlChar(differenceKind) ).append(
	" and propertyno = " ).append( Common.sqlChar(propertyNo) ).append(
	" and serialno = " ).append( Common.sqlChar(serialNo) ).append(
	":;:");
	
	sbSQL.append(" delete from UNTBU_ATTACHMENT2 " ).append(
	" where enterorg = " ).append( Common.sqlChar(enterOrg) ).append(
	" and ownership = " ).append( Common.sqlChar(ownership) ).append(
	" and propertyno = " ).append( Common.sqlChar(propertyNo) ).append(
	" and serialno = " ).append( Common.sqlChar(serialNo) ).append(
	":;:");
	
	sbSQL.append(" delete from UNTBU_VIEWSCENE " ).append(
	" where enterorg = " ).append( Common.sqlChar(enterOrg) ).append(
	" and ownership = " ).append( Common.sqlChar(ownership) ).append(
	" and propertyno = " ).append( Common.sqlChar(propertyNo) ).append(
	" and serialno = " ).append( Common.sqlChar(serialNo) ).append(
	":;:");
	
	sbSQL.append(" delete from UNTBU_TAX " ).append(
	" where enterorg = " ).append( Common.sqlChar(enterOrg) ).append(
	" and ownership = " ).append( Common.sqlChar(ownership) ).append(
	" and propertyno = " ).append( Common.sqlChar(propertyNo) ).append(
	" and serialno = " ).append( Common.sqlChar(serialNo) ).append(
	":;:");

	sbSQL.append(" delete from UNTBU_DRAWPROOF " ).append(
	" where enterorg = " ).append( Common.sqlChar(enterOrg) ).append(
	" and ownership = " ).append( Common.sqlChar(ownership) ).append(
	" and propertyno = " ).append( Common.sqlChar(propertyNo) ).append(
	" and serialno = " ).append( Common.sqlChar(serialNo) ).append(
	":;:");

	sbSQL.append(" delete from UNTBU_BUILDING where 1=1 " ).append(
	" and enterorg = " ).append( Common.sqlChar(enterOrg) ).append(
	" and ownership = " ).append( Common.sqlChar(ownership) ).append(
	" and differenceKind = " ).append( Common.sqlChar(differenceKind) ).append(
	" and propertyno = " ).append( Common.sqlChar(propertyNo) ).append(
	" and serialno = " ).append( Common.sqlChar(serialNo) ).append(
	":;:");
	
	sbSQL.append(" delete from UNTBU_PRICE " ).append(
	" where enterorg = " ).append( Common.sqlChar(enterOrg) ).append(
	" and ownership = " ).append( Common.sqlChar(ownership) ).append(
	" and differenceKind = " ).append( Common.sqlChar(differenceKind) ).append(
	" and propertyno = " ).append( Common.sqlChar("propertyNo") ).append(
	" and serialno = " ).append( Common.sqlChar("serialNo") ).append(
	":;:");	
	
	return sbSQL.toString().split(":;:");	
}


/**
 * <br>
 * <br>目的：依主鍵查詢單一資料
 * <br>參數：無
 * <br>傳回：傳回本物件
*/

public UNTBU002F  queryOne() throws Exception{
	Database db = new Database(); 	
	UNTBU002F obj = this;
	try {
		StringBuffer sbSQL = new StringBuffer();
		
		sbSQL.append(" select b.organsname, c.propertyname, c.propertytype, c.propertyunit, " ).append(
			" (select codename from SYSCA_CODE z where codekindid = 'SKD' and z.codeid = a.sourcekind) as sourcekindName, "  ).append(
			" (select codename from SYSCA_CODE z where codekindid = 'CAA' and z.codeid = a.cause and codecon1 in (1,4)) as causeName, " ).append(
			" c.material, c.limityear, " ).append(
			" d.organsname as manageorgname, " ).append(
			" (select x.propertyname from SYSPK_PROPERTYCODE x where a.oldpropertyno = x.propertyno ) as oldpropertyname, " ).append(
			" a.*, ").append(
			" (select placename from SYSCA_PLACE z where z.enterorg = a.enterorg and z.placeno = a.originalPlace1) as originalPlace1Name,").append(
			" (select placename from SYSCA_PLACE z where z.enterorg = a.enterorg and z.placeno = a.place1) as place1Name").append(
			" , a.picture, a.individualsetdepr, a.verifyym, a.selfdepr from UNTBU_BUILDING a left join SYSCA_ORGAN d on a.manageorg = d.organid left join  SYSCA_ORGAN b on b.organid = a.enterorg left join SYSPK_PROPERTYCODE c on a.propertyno = c.propertyno where 1=1 " ).append(
			" and a.enterorg = " ).append( Common.sqlChar(enterOrg) ).append(
			" and a.ownership = " ).append( Common.sqlChar(ownership) ).append(
			" and a.differenceKind = " ).append( Common.sqlChar(differenceKind) ).append(
			" and a.propertyno = " ).append( Common.sqlChar(propertyNo) ).append(
			" and a.serialno = " ).append( Common.sqlChar(serialNo) ).append(
			"" );
//		System.out.println("-- untbu002f queryOne --\n"+sbSQL.toString());
		StringTools st = new StringTools();
		UNTCH_Tools ul = new UNTCH_Tools();
		ResultSet rs = db.querySQL(sbSQL.toString());
		if (rs.next()){
			obj.setPicture(rs.getString("picture"));
			obj.setEnterOrg(rs.getString("enterOrg"));
			obj.setOwnership(rs.getString("ownership"));
			obj.setEnterOrgName(rs.getString("organSName"));
			obj.setCaseNo(rs.getString("caseNo"));
			obj.setPropertyNo(rs.getString("propertyNo"));
			obj.setSerialNo(rs.getString("serialNo"));
			obj.setPropertyName1(rs.getString("propertyName1"));
			if ((rs.getString("signNo")!=null) && (Common.get(rs.getString("signNo")).length()>=7)) {
				obj.setSignNo1(rs.getString("signNo").substring(0,1)+"000000");
				obj.setSignNo2(rs.getString("signNo").substring(0,3)+"0000");
				obj.setSignNo3(rs.getString("signNo").substring(0,7));
				
				if (rs.getString("signNo").length() >= 12) {
					obj.setSignNo4(rs.getString("signNo").substring(7,12));
				}
				
				if (rs.getString("signNo").length() == 15) {
					obj.setSignNo5(rs.getString("signNo").substring(12));	
				}
			} else {
				obj.setSignNo1("");
				obj.setSignNo2("");
				obj.setSignNo3("");
				obj.setSignNo4("");
				obj.setSignNo5("");				
			}
			obj.setDoorPlate1(rs.getString("doorPlate1"));
			obj.setDoorPlate2(rs.getString("doorPlate2"));
			obj.setDoorPlate4(rs.getString("doorPlate4"));			
			obj.setCause(rs.getString("cause"));
			obj.setCauseName(rs.getString("causeName"));
			obj.setCause1(rs.getString("cause1"));
			obj.setEnterDate(ul._transToROC_Year(rs.getString("enterDate")));
			obj.setDataState(rs.getString("dataState"));
			obj.setReduceDate(ul._transToROC_Year(rs.getString("reduceDate")));
			obj.setReduceCause(rs.getString("reduceCause"));
			obj.setReduceCause1(rs.getString("reduceCause1"));
			obj.setVerify(rs.getString("verify"));
			obj.setPropertyKind(rs.getString("propertyKind"));
			obj.setFundType(rs.getString("fundType"));
			obj.setValuable(rs.getString("valuable"));
			obj.setTaxCredit(rs.getString("taxCredit"));
			obj.setOriginalArea(st._getDotFormat(rs.getString("originalArea"),2));
			obj.setOriginalHoldNume(rs.getString("originalHoldNume"));
			obj.setOriginalHoldDeno(rs.getString("originalHoldDeno"));
			obj.setOriginalHoldArea(st._getDotFormat(rs.getString("originalHoldArea"),2));
			obj.setArea(st._getDotFormat(rs.getString("area"),2));
			obj.setHoldNume(rs.getString("holdNume"));
			obj.setHoldDeno(rs.getString("holdDeno"));
			obj.setHoldArea(st._getDotFormat(rs.getString("holdArea"),2));
			obj.setOriginalBV(rs.getString("originalBV"));
			obj.setOriginalNote(rs.getString("originalNote"));
			obj.setAccountingTitle(rs.getString("accountingTitle"));
			obj.setBookValue(rs.getString("bookValue"));
			obj.setFundsSource(rs.getString("fundsSource"));
			obj.setFundsSource1(rs.getString("fundsSource1"));
			obj.setOwnershipDate(ul._transToROC_Year(rs.getString("ownershipDate")));
			obj.setOwnershipCause(rs.getString("ownershipCause"));
			obj.setNonProof(rs.getString("nonProof"));
			obj.setProofDoc(rs.getString("proofDoc"));
			obj.setOwnershipNote(rs.getString("ownershipNote"));
			obj.setSourceKind(rs.getString("sourceKind"));
			obj.setSourceKindName(rs.getString("sourceKindName"));
			obj.setSourceDate(ul._transToROC_Year(rs.getString("sourceDate")));
			obj.setSourceDoc(rs.getString("sourceDoc"));
			obj.setManageOrg(rs.getString("manageOrg"));
			obj.setAppraiseDate(ul._transToROC_Year(rs.getString("appraiseDate")));
			obj.setNotes(rs.getString("notes"));
			obj.setOldPropertyNo(rs.getString("oldPropertyNo"));
			obj.setOldSerialNo(rs.getString("oldSerialNo"));

			obj.setOtherLimitYear(rs.getString("otherLimitYear"));
			obj.setOriginalLimitYear(rs.getString("originallimityear"));
			obj.setBuildStyle(rs.getString("buildStyle"));
			obj.setOriginalCArea(rs.getString("originalCArea"));
			obj.setOriginalSArea(rs.getString("originalSArea"));
			obj.setCArea(rs.getString("CArea"));
			obj.setSArea(rs.getString("SArea"));
			obj.setBuildDate(ul._transToROC_Year(rs.getString("buildDate")));
			obj.setFloor1(rs.getString("floor1"));
			obj.setFloor2(rs.getString("floor2"));
			obj.setStuff(rs.getString("stuff"));
			obj.setDeprMethod(rs.getString("deprMethod"));
			obj.setScrapValue(rs.getString("scrapValue"));
			obj.setDeprAmount(rs.getString("deprAmount"));
			obj.setMonthDepr(rs.getString("monthDepr"));
			obj.setMonthDepr1(rs.getString("monthDepr1"));
			obj.setAccumDepr(rs.getString("accumDepr"));
			
			obj.setEditID(rs.getString("editID"));
			obj.setEditDate(ul._transToROC_Year(rs.getString("editDate")));
			obj.setEditTime(rs.getString("editTime"));			
			
			obj.setDifferenceKind(rs.getString("differenceKind"));
			obj.setBuyDate(ul._transToROC_Year(rs.getString("buyDate")));
			
			obj.setEngineeringNo(rs.getString("EngineeringNo"));
			obj.setEngineeringNoName(ul._getEngineeringNoName(rs.getString("enterOrg"),rs.getString("engineeringNo")));
			obj.setCaseSerialNo(rs.getString("CaseSerialNo"));
			obj.setDoorPlatevillage1(rs.getString("DoorPlatevillage1"));
			obj.setDoorPlatevillage2(rs.getString("DoorPlatevillage2"));
			obj.setDoorplateRd1(rs.getString("DoorplateRd1"));
			obj.setDoorplateRd2(rs.getString("DoorplateRd2"));
			obj.setDoorplateSec(rs.getString("DoorplateSec"));
			obj.setDoorplateLn(rs.getString("DoorplateLn"));
			obj.setDoorplateAly(rs.getString("DoorplateAly"));
			obj.setDoorplateNo(rs.getString("DoorplateNo"));
			obj.setDoorplateFloor1(rs.getString("DoorplateFloor1"));
			obj.setDoorplateFloor2(rs.getString("DoorplateFloor2"));
			obj.setNetValue(rs.getString("NetValue"));
			obj.setOriginalDeprMethod(rs.getString("OriginalDeprMethod"));
			obj.setOriginalBuildFeeCB(rs.getString("OriginalBuildFeeCB"));
			obj.setOriginalDeprUnitCB(rs.getString("OriginalDeprUnitCB"));
			obj.setOriginalDeprPark(rs.getString("OriginalDeprPark"));
			obj.setOriginalDeprUnit(rs.getString("OriginalDeprUnit"));
			obj.setOriginalDeprAccounts(rs.getString("OriginalDeprAccounts"));
			obj.setOriginalScrapValue(rs.getString("OriginalScrapValue"));
			obj.setOriginalDeprAmount(rs.getString("OriginalDeprAmount"));
			obj.setOriginalaccumdepr1(rs.getString("originalaccumdepr1"));
			obj.setOriginalaccumdepr2(rs.getString("originalaccumdepr2"));
			obj.setOriginalAccumDepr(rs.getString("OriginalAccumDepr"));
			obj.setOriginalApportionMonth(rs.getString("OriginalApportionMonth"));
			obj.setOriginalMonthDepr(rs.getString("OriginalMonthDepr"));
			obj.setOriginalMonthDepr1(rs.getString("OriginalMonthDepr1"));
			obj.setBuildFeeCB(rs.getString("BuildFeeCB"));
			obj.setDeprUnitCB(rs.getString("DeprUnitCB"));
			obj.setDeprPark(rs.getString("DeprPark"));
			obj.setDeprUnit(rs.getString("DeprUnit"));
			obj.setDeprAccounts(rs.getString("DeprAccounts"));
			obj.setApportionMonth(rs.getString("ApportionMonth"));
			obj.setNoDeprSet(rs.getString("NoDeprSet"));
			obj.setOriginalMoveDate(ul._transToROC_Year(rs.getString("OriginalMoveDate")));
			obj.setOriginalKeepUnit(rs.getString("OriginalKeepUnit"));
			obj.setOriginalKeeper(rs.getString("OriginalKeeper"));
			obj.setOriginalUseUnit(rs.getString("OriginalUseUnit"));
			obj.setOriginalUser(rs.getString("OriginalUser"));
			obj.setOriginalUserNote(rs.getString("OriginalUserNote"));
			obj.setOriginalPlace1(rs.getString("OriginalPlace1"));
			obj.setOriginalPlace(rs.getString("OriginalPlace"));
			obj.setMoveDate(ul._transToROC_Year(rs.getString("MoveDate")));
			obj.setKeepUnit(rs.getString("KeepUnit"));
			obj.setKeeper(rs.getString("Keeper"));
			obj.setUseUnit(rs.getString("UseUnit"));
			obj.setUserNo(rs.getString("UserNo"));
			obj.setUserNote(rs.getString("UserNote"));
			obj.setPlace1(rs.getString("Place1"));
			obj.setPlace(rs.getString("Place"));
			obj.setUseLicense(rs.getString("UseLicense"));
			obj.setEscrowOriValue(rs.getString("EscrowOriValue"));
			obj.setEscrowOriAccumDepr(rs.getString("EscrowOriAccumDepr"));

			obj.setOriginalDeprUnit1(rs.getString("originalDeprUnit1"));
			obj.setDeprUnit1(rs.getString("deprUnit1"));
			
			obj.setOriginalApportionEndYM(ul._transToROC_Year(rs.getString("originalApportionEndYM")));
			obj.setApportionEndYM(ul._transToROC_Year(rs.getString("apportionEndYM")));
			

			obj.setOriginalPlace1Name(rs.getString("Originalplace1Name"));
			obj.setPlace1Name(rs.getString("place1Name"));
			
			obj.setMaterial(rs.getString("material"));
			obj.setPropertyUnit(rs.getString("propertyUnit"));
			
			obj.setIndividualSetDepr(rs.getString("individualsetdepr"));
			obj.setVerifyYM(ul._transToROC_Year(rs.getString("verifyym")));
			obj.setSelfdepr(rs.getString("selfdepr"));
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
	ArrayList objList=new ArrayList();
	Database db = new Database();
	
	try {
		StringBuffer sbSQL = new StringBuffer().append(" select b.organsname,a.enterorg, a.ownership, a.caseNo, (select codename from SYSCA_CODE x where x.codekindid = 'OWA' and x.codeid = a.ownership ) as ownershipname,").append(
				" a.propertyNo, a.serialNo, a.signNo, c.codeName cause, ").append(
				" (case a.dataState when '1' then '現存' when '2' then '已減損' else '' end) dataState,").append(
				" a.holdArea, " ).append(
				" a.propertyName1, a.bookValue, a.differenceKind" ).append(
				" from UNTBU_BUILDING a left join SYSCA_ORGAN b on a.enterorg = b.organid left join SYSCA_CODE c on c.codekindid='CAB' and a.cause = c.codeid " ).append(
				" where 1=1 ");
		
		sbSQL.append(" and a.enterorg=" ).append( Common.sqlChar(getEnterOrg()));
		sbSQL.append(" and a.ownership=" ).append( Common.sqlChar(getOwnership()));
		sbSQL.append(" and a.caseno = " ).append( Common.sqlChar(getCaseNo())) ;
		sbSQL.append(" and a.differenceKind = " ).append( Common.sqlChar(getDifferenceKind())) ;
		sbSQL.append(" and a.propertyno = " ).append( Common.sqlChar(getPropertyNo())) ;
		sbSQL.append(" and a.serialno = " ).append( Common.sqlChar(getSerialNo())) ;
	
		
//System.out.println("-- untbu002f queryAll --\n"+sbSQL.toString());
		StringTools st = new StringTools();
		UNTCH_Tools ucl = new UNTCH_Tools();
		ResultSet rs = db.querySQL(sbSQL.append(" order by a.enterorg, a.ownership, a.caseno, a.propertyno, a.serialno").toString(),true);
		processCurrentPageAttribute(rs);			
		if (getTotalRecord() > 0) {
			int count = getRecordStart();
			int end = getRecordEnd();
			do {
				if (count > end)
					break;
			String rowArray[]=new String[14];			
			rowArray[0]=rs.getString("enterOrg");
			rowArray[1]=rs.getString("ownership"); 		
			rowArray[2]=rs.getString("caseNo");
			rowArray[3]=rs.getString("propertyNo"); 
			rowArray[4]=rs.getString("serialNo");
			rowArray[5]=ucl._getDifferenceKindName(rs.getString("differenceKind"));
			rowArray[6]=rs.getString("ownershipName");
			if("".equals(checkGet(rs.getString("signNo"))) || checkGet(rs.getString("signNo")).length()!=15){
				rowArray[7]="";
			}else{
				rowArray[7]=getSignDescName(rs.getString("signNo").substring(0,7)) + rs.getString("signNo").substring(7,12) + '-' + rs.getString("signNo").substring(12);
			}
			rowArray[8]=rs.getString("cause"); 
			rowArray[9]=rs.getString("dataState"); 
			rowArray[10]=st._getDotFormat(rs.getString("holdArea"),2);			
			rowArray[11]=rs.getString("propertyName1");
			rowArray[12]=rs.getString("bookValue");
			rowArray[13]=rs.getString("differenceKind");			
			
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

private String getSignDescName(String signNo){
	Database db = null;
	ResultSet rs = null;
	String sql = null;
	String result = null;
	try{
		sql = "select signdesc from SYSCA_SIGN where" +
				" signno = " + Common.sqlChar(signNo);
		
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


