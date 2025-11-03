/*
*<br>程式目的：動產資料維護-批號資料
*<br>程式代號：untmp002f
*<br>程式日期：0940928
*<br>程式作者：Cherry
*<br>--------------------------------------------------------
*<br>修改作者　　修改日期　　　修改目的
*<br>--------------------------------------------------------
*/

package unt.mp;

import java.sql.ResultSet;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import unt.ch.UNTCH_Tools;
import util.Common;
import util.Database;
import util.Datetime;
import TDlib_Simple.com.src.DBServerTools;
import TDlib_Simple.com.src.SQLCreator;
import TDlib_Simple.tools.src.MathTools;

public class UNTMP002F extends UNTMP001Q{
	
	public UNTMP002F() {
		
	}
	
	public UNTMP002F(String caller) {
		this.setCaller(caller);
	}
	
	/** 誰呼叫我執行 請傳入class simple name**/
	private String caller;
	public String getCaller() { return checkGet(caller); }
	public void setCaller(String caller) { this.caller = checkSet(caller); }

	private String deprUnit1;
	public String getDeprUnit1() {return checkGet(deprUnit1);}
	public void setDeprUnit1(String deprUnit1) {this.deprUnit1 = checkSet(deprUnit1);}

	private String place1Name;
	private String originalPlace1Name;
	public String getPlace1Name() {return checkGet(place1Name);}
	public void setPlace1Name(String place1Name) {this.place1Name = checkSet(place1Name);}
	public String getOriginalPlace1Name() {return checkGet(originalPlace1Name);}
	public void setOriginalPlace1Name(String originalPlace1Name) {this.originalPlace1Name = checkSet(originalPlace1Name);}
	
	private String sourceKindName;
	private String causeName;
	public String getSourceKindName() {return checkGet(sourceKindName);}
	public void setSourceKindName(String sourceKindName) {this.sourceKindName = checkSet(sourceKindName);}
	public String getCauseName() {return checkGet(causeName);}
	public void setCauseName(String causeName) {this.causeName = checkSet(causeName);}
	
	private String initDtl;	
	public String getInitDtl() {return checkGet(initDtl);}
	public void setInitDtl(String initDtl) {this.initDtl = checkSet(initDtl);}

	private String originalApportionEndYM;
	public String getOriginalApportionEndYM() {return checkGet(originalApportionEndYM);}
	public void setOriginalApportionEndYM(String originalApportionEndYM) {this.originalApportionEndYM = checkSet(originalApportionEndYM);}	
	
	private String storeNoName;
	public String getStoreNoName() {return checkGet(storeNoName);}
	public void setStoreNoName(String storeNoName) {this.storeNoName = checkSet(storeNoName);}
	private String material;
	private String propertyUnit;
	private String limitYear;
	private String engineeringNoName;
	private String propertyNoName;
	public String getMaterial() {return checkGet(material);}
	public void setMaterial(String material) {this.material = checkSet(material);}
	public String getPropertyUnit() {return checkGet(propertyUnit);}
	public void setPropertyUnit(String propertyUnit) {this.propertyUnit = checkSet(propertyUnit);}
	public String getLimitYear() {return checkGet(limitYear);}
	public void setLimitYear(String limitYear) {this.limitYear = checkSet(limitYear);}
	public String getEngineeringNoName() {return checkGet(engineeringNoName);}
	public void setEngineeringNoName(String engineeringNoName) {this.engineeringNoName = checkSet(engineeringNoName);}
	public String getPropertyNoName() {return checkGet(propertyNoName);}
	public void setPropertyNoName(String propertyNoName) {this.propertyNoName = checkSet(propertyNoName);}
	
	private String enterOrg;
	private String ownership;
	private String caseNo;
	private String differenceKind;
	private String engineeringNo;
	private String caseSerialNo;
	private String propertyNo;
	private String lotNo;
	private String serialNoS;
	private String serialNoE;
	private String otherPropertyUnit;
	private String otherMaterial;
	private String originaLlimitYear;
	private String otherLimitYear;
	private String propertyName1;
	private String propertyName1_movable;		// 批的財產別名 for 增加單基本資料頁s
	private String cause;
	private String cause1;
	private String approveDate;
	private String approveDoc;
	private String enterDate;
	private String buyDate;
	private String dataState;
	private String verify;
	private String propertyKind;
	private String fundType;
	private String valuable;
	private String originalAmount;
	private String originalUnit;
	private String originalBV;
	private String originalNote;
	private String accountingTitle;
	private String bookAmount;
	private String bookValue;
	private String netValue;
	private String fundsSource;
	private String fundsSource1;
	private String grantValue;
	private String articleName;
	private String nameplate;
	private String specification;
	private String storeNo;
	private String sourceKind;
	private String sourceDate;
	private String sourceDoc;
	private String originalDeprMethod;
	private String originalBuildFeeCB;
	private String originalDeprUnitCB;
	private String originalDeprPark;
	private String originalDeprUnit;
	private String originalDeprAccounts;
	private String originalScrapValue;
	private String originalDeprAmount;
	private String originalaccumdepr1;
	private String originalaccumdepr2;
	private String originalAccumDepr;
	private String originalApportionMonth;
	private String originalMonthDepr;
	private String escrowOriValue;
	private String escrowOriAccumDepr;
	private String oldPropertyNo;
	private String oldSerialNoS;
	private String oldSerialNoE;
	private String notes;
	private String editID;
	private String editDate;
	private String editTime;
	private String picture;

	public String getPicture() {return checkGet(picture);}
	public void setPicture(String picture) {this.picture = checkSet(picture);}
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
	public String getLotNo() {return checkGet(lotNo);}
	public void setLotNo(String lotNo) {this.lotNo = checkSet(lotNo);}
	public String getSerialNoS() {return checkGet(serialNoS);}
	public void setSerialNoS(String serialNoS) {this.serialNoS = checkSet(serialNoS);}
	public String getSerialNoE() {return checkGet(serialNoE);}
	public void setSerialNoE(String serialNoE) {this.serialNoE = checkSet(serialNoE);}
	public String getOtherPropertyUnit() {return checkGet(otherPropertyUnit);}
	public void setOtherPropertyUnit(String otherPropertyUnit) {this.otherPropertyUnit = checkSet(otherPropertyUnit);}
	public String getOtherMaterial() {return checkGet(otherMaterial);}
	public void setOtherMaterial(String otherMaterial) {this.otherMaterial = checkSet(otherMaterial);}
	public String getOriginaLlimitYear() {return checkGet(originaLlimitYear);}
	public void setOriginaLlimitYear(String originaLlimitYear) {this.originaLlimitYear = checkSet(originaLlimitYear);}	
	public String getOtherLimitYear() {return checkGet(otherLimitYear);}
	public void setOtherLimitYear(String otherLimitYear) {this.otherLimitYear = checkSet(otherLimitYear);}
	public String getPropertyName1() {return checkGet(propertyName1.replace("\"", ""));}
	public void setPropertyName1(String propertyName1) {this.propertyName1 = checkSet(propertyName1);}
	public String getPropertyName1_movable() {return checkGet(propertyName1_movable.replace("\"", ""));}
	public void setPropertyName1_movable(String propertyName1_movable) {this.propertyName1_movable = checkSet(propertyName1_movable);}
	public String getCause() {return checkGet(cause);}
	public void setCause(String cause) {this.cause = checkSet(cause);}
	public String getCause1() {return checkGet(cause1);}
	public void setCause1(String cause1) {this.cause1 = checkSet(cause1);}
	public String getApproveDate() {return checkGet(approveDate);}
	public void setApproveDate(String approveDate) {this.approveDate = checkSet(approveDate);}
	public String getApproveDoc() {return checkGet(approveDoc);}
	public void setApproveDoc(String approveDoc) {this.approveDoc = checkSet(approveDoc);}
	public String getEnterDate() {return checkGet(enterDate);}
	public void setEnterDate(String enterDate) {this.enterDate = checkSet(enterDate);}
	public String getBuyDate() {return checkGet(buyDate);}
	public void setBuyDate(String buyDate) {this.buyDate = checkSet(buyDate);}
	public String getDataState() {return checkGet(dataState);}
	public void setDataState(String dataState) {this.dataState = checkSet(dataState);}
	public String getVerify() {return checkGet(verify);}
	public void setVerify(String verify) {this.verify = checkSet(verify);}
	public String getPropertyKind() {return checkGet(propertyKind);}
	public void setPropertyKind(String propertyKind) {this.propertyKind = checkSet(propertyKind);}
	public String getFundType() {return checkGet(fundType);}
	public void setFundType(String fundType) {this.fundType = checkSet(fundType);}
	public String getValuable() {return checkGet(valuable);}
	public void setValuable(String valuable) {this.valuable = checkSet(valuable);}
	public String getOriginalAmount() {return checkGet(originalAmount);}
	public void setOriginalAmount(String originalAmount) {this.originalAmount = checkSet(originalAmount);}
	public String getOriginalUnit() {return checkGet(originalUnit);}
	public void setOriginalUnit(String originalUnit) {this.originalUnit = checkSet(originalUnit);}
	public String getOriginalBV() {return checkGet(originalBV);}
	public void setOriginalBV(String originalBV) {this.originalBV = checkSet(originalBV);}
	public String getOriginalNote() {return checkGet(originalNote);}
	public void setOriginalNote(String originalNote) {this.originalNote = checkSet(originalNote);}
	public String getAccountingTitle() {return checkGet(accountingTitle);}
	public void setAccountingTitle(String accountingTitle) {this.accountingTitle = checkSet(accountingTitle);}
	public String getBookAmount() {return checkGet(bookAmount);}
	public void setBookAmount(String bookAmount) {this.bookAmount = checkSet(bookAmount);}
	public String getBookValue() {return checkGet(bookValue);}
	public void setBookValue(String bookValue) {this.bookValue = checkSet(bookValue);}
	public String getNetValue() {return checkGet(netValue);}
	public void setNetValue(String netValue) {this.netValue = checkSet(netValue);}
	public String getFundsSource() {return checkGet(fundsSource);}
	public void setFundsSource(String fundsSource) {this.fundsSource = checkSet(fundsSource);}
	public String getFundsSource1() {return checkGet(fundsSource1);}
	public void setFundsSource1(String fundsSource1) {this.fundsSource1 = checkSet(fundsSource1);}
	public String getGrantValue() {return checkGet(grantValue);}
	public void setGrantValue(String grantValue) {this.grantValue = checkSet(grantValue);}
	public String getArticleName() {return checkGet(articleName);}
	public void setArticleName(String articleName) {this.articleName = checkSet(articleName);}
	public String getNameplate() {return checkGet(nameplate);}
	public void setNameplate(String nameplate) {this.nameplate = checkSet(nameplate);}
	public String getSpecification() {return checkGet(specification);}
	public void setSpecification(String specification) {this.specification = checkSet(specification);}
	public String getStoreNo() {return checkGet(storeNo);}
	public void setStoreNo(String storeNo) {this.storeNo = checkSet(storeNo);}
	public String getSourceKind() {return checkGet(sourceKind);}
	public void setSourceKind(String sourceKind) {this.sourceKind = checkSet(sourceKind);}
	public String getSourceDate() {return checkGet(sourceDate);}
	public void setSourceDate(String sourceDate) {this.sourceDate = checkSet(sourceDate);}
	public String getSourceDoc() {return checkGet(sourceDoc);}
	public void setSourceDoc(String sourceDoc) {this.sourceDoc = checkSet(sourceDoc);}
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
	public String getEscrowOriValue() {return checkGet(escrowOriValue);}
	public void setEscrowOriValue(String escrowOriValue) {this.escrowOriValue = checkSet(escrowOriValue);}
	public String getEscrowOriAccumDepr() {return checkGet(escrowOriAccumDepr);}
	public void setEscrowOriAccumDepr(String escrowOriAccumDepr) {this.escrowOriAccumDepr = checkSet(escrowOriAccumDepr);}
	public String getOldPropertyNo() {return checkGet(oldPropertyNo);}
	public void setOldPropertyNo(String oldPropertyNo) {this.oldPropertyNo = checkSet(oldPropertyNo);}
	public String getOldSerialNoS() {return checkGet(oldSerialNoS);}
	public void setOldSerialNoS(String oldSerialNoS) {this.oldSerialNoS = checkSet(oldSerialNoS);}
	public String getOldSerialNoE() {return checkGet(oldSerialNoE);}
	public void setOldSerialNoE(String oldSerialNoE) {this.oldSerialNoE = checkSet(oldSerialNoE);}
	public String getNotes() {return checkGet(notes);}
	public void setNotes(String notes) {this.notes = checkSet(notes);}
	public String getEditID() {return checkGet(editID);}
	public void setEditID(String editID) {this.editID = checkSet(editID);}
	public String getEditDate() {return checkGet(editDate);}
	public void setEditDate(String editDate) {this.editDate = checkSet(editDate);}
	public String getEditTime() {return checkGet(editTime);}
	public void setEditTime(String editTime) {this.editTime = checkSet(editTime);}
	public String getCheckOriginalAmount() {return checkGet(checkOriginalAmount);}
	public void setCheckOriginalAmount(String checkOriginalAmount) {this.checkOriginalAmount = checkSet(checkOriginalAmount);}
	public String getCheckOriginalUnit() {return checkGet(checkOriginalUnit);}
	public void setCheckOriginalUnit(String checkOriginalUnit) {this.checkOriginalUnit = checkSet(checkOriginalUnit);}
	public String getCheckOriginalBV() {return checkGet(checkOriginalBV);}
	public void setCheckOriginalBV(String checkOriginalBV) {this.checkOriginalBV = checkSet(checkOriginalBV);}
	public String getCheckDeprMethod() {return checkGet(checkDeprMethod);}
	public void setCheckDeprMethod(String checkDeprMethod) {this.checkDeprMethod = checkSet(checkDeprMethod);}
	public String getSerialNoAttachment2() {return checkGet(serialNoAttachment2);}
	public void setSerialNoAttachment2(String serialNoAttachment2) {this.serialNoAttachment2 = checkSet(serialNoAttachment2);}
	public String getTrueOriginalAmount() {return checkGet(trueOriginalAmount);}
	public void setTrueOriginalAmount(String trueOriginalAmount) {this.trueOriginalAmount = checkSet(trueOriginalAmount);}
	public String getTrueOriginalUnit() {return checkGet(trueOriginalUnit);}
	public void setTrueOriginalUnit(String trueOriginalUnit) {this.trueOriginalUnit = checkSet(trueOriginalUnit);}
	public String getTrueOriginalBV() {return checkGet(trueOriginalBV);}
	public void setTrueOriginalBV(String trueOriginalBV) {this.trueOriginalBV = checkSet(trueOriginalBV);}

	private String serialNo;
	private String reduceDate;
	private String reduceCause;
	private String reduceCause1;
	private String licensePlate;
	private String purpose;
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
	private String notes1;
	private String notes2;
	private String barCode;
	private String oldSerialNo;
	private String monthDepr1;
	private String apportionEndYM;

	
	public String getNotes2() {	return checkGet(notes2);}
	public void setNotes2(String notes2) {this.notes2 = checkSet(notes2);}
	public String getSerialNo() {return checkGet(serialNo);}
	public void setSerialNo(String serialNo) {this.serialNo = checkSet(serialNo);}
	public String getReduceDate() {return checkGet(reduceDate);}
	public void setReduceDate(String reduceDate) {this.reduceDate = checkSet(reduceDate);}
	public String getReduceCause() {return checkGet(reduceCause);}
	public void setReduceCause(String reduceCause) {this.reduceCause = checkSet(reduceCause);}
	public String getReduceCause1() {return checkGet(reduceCause1);}
	public void setReduceCause1(String reduceCause1) {this.reduceCause1 = checkSet(reduceCause1);}
	public String getLicensePlate() {return checkGet(licensePlate);}
	public void setLicensePlate(String licensePlate) {this.licensePlate = checkSet(licensePlate);}
	public String getPurpose() {return checkGet(purpose);}
	public void setPurpose(String purpose) {this.purpose = checkSet(purpose);}
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
	public String getNotes1() {return checkGet(notes1);}
	public void setNotes1(String notes1) {this.notes1 = checkSet(notes1);}
	public String getBarCode() {return checkGet(barCode);}
	public void setBarCode(String barCode) {this.barCode = checkSet(barCode);}
	public String getOldSerialNo() {return checkGet(oldSerialNo);}
	public void setOldSerialNo(String oldSerialNo) {this.oldSerialNo = checkSet(oldSerialNo);}
	public String getMonthDepr1() {return checkGet(monthDepr1);}
	public void setMonthDepr1(String monthDepr1) {this.monthDepr1 = checkSet(monthDepr1);}
	public String getApportionEndYM() {return checkGet(apportionEndYM);}
	public void setApportionEndYM(String apportionEndYM) {this.apportionEndYM = checkSet(apportionEndYM);}

	String OriginalAmount_detail;
	String OriginalBV_detail;
	String BookAmount_detail;
	String BookValue_detail;

	
	String checkOriginalAmount;
	String checkOriginalUnit;
	String checkOriginalBV;
	String checkDeprMethod;
	String serialNoAttachment2;
	
	String filestoreLocation;
	
	public String getFilestoreLocation(){ return checkGet(filestoreLocation); }
	public void setFilestoreLocation(String s){ filestoreLocation=checkSet(s); }

	String trueOriginalAmount = "";
	String trueOriginalUnit = "";
	String trueOriginalBV = "";

	private String originalDeprUnit1;
	public String getOriginalDeprUnit1() {return checkGet(originalDeprUnit1);}
	public void setOriginalDeprUnit1(String originalDeprUnit1) {this.originalDeprUnit1 = checkSet(originalDeprUnit1);}
	
	private String originalMonthDepr1;
	public String getOriginalMonthDepr1() {return checkGet(originalMonthDepr1);}
	public void setOriginalMonthDepr1(String originalMonthDepr1) {this.originalMonthDepr1 = checkSet(originalMonthDepr1);}
	
	private String detailOriginalAmount;
	private String detailOriginalBV;
	private String detailBookAmount;
	private String detailBookValue;
	private String detailNetValue;
	public String getDetailOriginalAmount() {return checkGet(detailOriginalAmount);}
	public void setDetailOriginalAmount(String detailOriginalAmount) {this.detailOriginalAmount = checkSet(detailOriginalAmount);}
	public String getDetailOriginalBV() {return checkGet(detailOriginalBV);}
	public void setDetailOriginalBV(String detailOriginalBV) {this.detailOriginalBV = checkSet(detailOriginalBV);}
	public String getDetailBookAmount() {return checkGet(detailBookAmount);}
	public void setDetailBookAmount(String detailBookAmount) {this.detailBookAmount = checkSet(detailBookAmount);}
	public String getDetailBookValue() {return checkGet(detailBookValue);}
	public void setDetailBookValue(String detailBookValue) {this.detailBookValue = checkSet(detailBookValue);}
	public String getDetailNetValue() {return checkGet(detailNetValue);}
	public void setDetailNetValue(String detailNetValue) {this.detailNetValue = checkSet(detailNetValue);}
	

	private String originalLimitYear;
	public String getOriginalLimitYear() {return checkGet(originalLimitYear);}
	public void setOriginalLimitYear(String originalLimitYear) {this.originalLimitYear = checkSet(originalLimitYear);}
	
	/**
	 * 因為不是很明白 detailOriginalAmount etc... 與 OriginalAmount_detail etc... 之間的關係到底是什麼
	 * 避免影響到其他共用此隻程式的作業, 此為新的財產查詢 & 維護 QueryOne後 ReadOnly欄位專用
	 */
	private String dtlOriginalAmountRO, dtlOriginalBVRO, dtlBookAmountRO, dtlBookValueRO, dtlNetValueRO;
	public String getDtlOriginalAmountRO() { return dtlOriginalAmountRO; }
	public void setDtlOriginalAmountRO(String dtlOriginalAmountRO) { this.dtlOriginalAmountRO = dtlOriginalAmountRO; }
	public String getDtlOriginalBVRO() { return dtlOriginalBVRO; }
	public void setDtlOriginalBVRO(String dtlOriginalBVRO) { this.dtlOriginalBVRO = dtlOriginalBVRO; }
	public String getDtlBookAmountRO() { return dtlBookAmountRO; }
	public void setDtlBookAmountRO(String dtlBookAmountRO) { this.dtlBookAmountRO = dtlBookAmountRO; }
	public String getDtlBookValueRO() { return dtlBookValueRO; }
	public void setDtlBookValueRO(String dtlBookValueRO) { this.dtlBookValueRO = dtlBookValueRO; }
	public String getDtlNetValueRO() { return dtlNetValueRO; }
	public void setDtlNetValueRO(String dtlNetValueRO) { this.dtlNetValueRO = dtlNetValueRO; }
	
	/** 明細的原始攤提截止年月  , 西元年格式  **/
	private String dtlOriginalApportionEndYMRO;
	public String getDtlOriginalApportionEndYMRO() { return checkGet(dtlOriginalApportionEndYMRO); }
	public void setDtlOriginalApportionEndYMRO(String dtlOriginalApportionEndYMRO) { this.dtlOriginalApportionEndYMRO = checkSet(dtlOriginalApportionEndYMRO); }

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
		map.put("PropertyNo", getPropertyNo());
		map.put("lotno", getLotNo());
		return map;
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private Map getRecordMap(){// TODO
		Map map = new HashMap();
		UNTCH_Tools ut = new UNTCH_Tools();
		
		if (!"UNTCH001F02_2".equals(this.getCaller())) { // 財產維護 不抓以下欄位 避免被清空
			
			long originalAccumDepr = Common.getLong(this.getOriginalAccumDepr());
			long originalAmount = Common.getLong(this.getOriginalAmount());
			long netValue = Common.getLong(this.getNetValue());
			
			map.put("buyDate", ut._transToCE_Year(getBuyDate()));
			
			map.put("enterDate", ut._transToCE_Year(getEnterDate()));
			
			map.put("bookAmount", getBookAmount());
			map.put("bookValue", getBookValue());
			map.put("netValue", Common.getDoubleVal(netValue - (originalAccumDepr * originalAmount)));
			
			map.put("originalAmount", getOriginalAmount());
			map.put("originalUnit", getOriginalUnit());
			map.put("originalBV", getOriginalBV());
			map.put("originalNote", getOriginalNote());
			
			map.put("originalDeprMethod", getOriginalDeprMethod());
			map.put("originalBuildFeeCB", getOriginalBuildFeeCB());
			map.put("originalDeprUnitCB", getOriginalDeprUnitCB());
			map.put("originalDeprPark", getOriginalDeprPark());
			map.put("originalDeprUnit", getOriginalDeprUnit());
			map.put("originalDeprAccounts", getOriginalDeprAccounts());
			map.put("originalScrapValue", getOriginalScrapValue());
			map.put("originalDeprAmount", getOriginalDeprAmount());
			map.put("originalaccumdepr1", this.getOriginalaccumdepr1());
			map.put("originalaccumdepr2", this.getOriginalaccumdepr2());
			map.put("originalAccumDepr", originalAccumDepr);
			map.put("originalApportionMonth", getOriginalApportionMonth());
			map.put("originalMonthDepr", getOriginalMonthDepr());
			map.put("originalMonthDepr1", getOriginalMonthDepr1());
			map.put("originalDeprUnit1", getOriginalDeprUnit1());
			map.put("originalApportionEndYM", ut._transToCE_Year(getOriginalApportionEndYM()));map.put("enterOrg", getEnterOrg());
			map.put("ownership", getOwnership());
			map.put("caseNo", getCaseNo());
			map.put("differenceKind", getDifferenceKind());
			map.put("engineeringNo", getEngineeringNo());
			map.put("caseSerialNo", getCaseSerialNo());
			map.put("propertyNo", getPropertyNo());
			map.put("lotNo", getLotNo());
			map.put("serialNoS", getSerialNoS());
			map.put("serialNoE", getSerialNoE());
			map.put("otherLimitYear",getOtherLimitYear());
			map.put("originalLimitYear",getOriginalLimitYear());
			map.put("propertyName1", getPropertyName1());
			map.put("cause", getCause());
			map.put("cause1", getCause1());
			map.put("approveDate", ut._transToCE_Year(getApproveDate()));
			map.put("approveDoc", getApproveDoc());
			
			map.put("dataState", getDataState());
			map.put("verify", getVerify());
			map.put("propertyKind", getPropertyKind());
			map.put("fundType", getFundType());
			map.put("valuable", getValuable());
			
			map.put("accountingTitle", getAccountingTitle());
			
			map.put("fundsSource", getFundsSource());
			map.put("fundsSource1", getFundsSource1());
			map.put("grantValue", getGrantValue());
			map.put("articleName", getArticleName());
			map.put("nameplate", getNameplate());
			map.put("specification", getSpecification());
			map.put("storeNo", getStoreNo());
			map.put("sourceKind", getSourceKind());
			map.put("sourceDate", ut._transToCE_Year(getSourceDate()));
			map.put("sourceDoc", getSourceDoc());
			
			map.put("escrowOriValue", getEscrowOriValue());
			map.put("escrowOriAccumDepr", getEscrowOriAccumDepr());
			map.put("oldPropertyNo", getOldPropertyNo());
			map.put("oldSerialNoS", getOldSerialNoS());
			map.put("oldSerialNoE", getOldSerialNoE());
			map.put("notes", getNotes());
			map.put("editID", getEditID());
			map.put("editDate", ut._transToCE_Year(getEditDate()));
			map.put("editTime", getEditTime());
			map.put("otherpropertyunit", (!"".equals(getOtherPropertyUnit()))? getOtherPropertyUnit(): getPropertyUnit());
			map.put("othermaterial", (!"".equals(getOtherMaterial()))? getOtherMaterial(): getMaterial());
			
			map.put("picture", getPicture());
		}
		
//		System.out.println("xx="+getPicture());
		return map;
	}
	
	private Map getPKMap_forDetail(String newserialNo){
		Map map = new HashMap();
		
		newserialNo = Common.formatFrontZero(newserialNo, 7);
		
		map.put("enterOrg", getEnterOrg());
		map.put("ownership", getOwnership());
		map.put("caseNo", getCaseNo());
		map.put("differenceKind", getDifferenceKind());
		map.put("propertyNo", getPropertyNo());
		map.put("lotNo", getLotNo());
		map.put("serialNo", newserialNo);
		
		return map;
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private Map getRecordMap_forDetail(String newserialNo){//TODO
		Map map = new HashMap();
		UNTCH_Tools ut = new UNTCH_Tools();
		
		newserialNo = Common.formatFrontZero(newserialNo, 7);

		long originalAccumdepr = Common.getLong(this.getOriginalAccumDepr());
		long detailNetvalue = Common.getLong(this.getDetailNetValue());
		
		map.put("EnterOrg", getEnterOrg());
		map.put("Ownership", getOwnership());
		map.put("CaseNo", getCaseNo());
		map.put("DifferenceKind", getDifferenceKind());
		map.put("EngineeringNo", getEngineeringNo());
		map.put("PropertyNo", getPropertyNo());
		map.put("LotNo", getLotNo());
		map.put("SerialNo", newserialNo);
		map.put("DataState", getDataState());
		map.put("ReduceDate", ut._transToCE_Year(getReduceDate()));
		map.put("ReduceCause", getReduceCause());
		map.put("ReduceCause1", getReduceCause1());
		map.put("Verify", getVerify());
		map.put("EnterDate", ut._transToCE_Year(getEnterDate()));
		map.put("OriginalAmount", getDetailOriginalAmount());
		map.put("OriginalBV", getDetailOriginalBV());
		map.put("BookAmount", getDetailBookAmount());
		map.put("BookValue", getDetailBookValue());
		map.put("NetValue", detailNetvalue - originalAccumdepr);
		map.put("LicensePlate", getLicensePlate());
		map.put("Purpose", getPurpose());
		map.put("OriginalMoveDate", ut._transToCE_Year(getOriginalMoveDate()));
		map.put("OriginalKeepUnit", getOriginalKeepUnit());
		map.put("OriginalKeeper", getOriginalKeeper());
		map.put("OriginalUseUnit", getOriginalUseUnit());
		map.put("OriginalUser", getOriginalUser());
		map.put("OriginalUserNote", getOriginalUserNote());
		map.put("OriginalPlace1", getOriginalPlace1());
		map.put("OriginalPlace", getOriginalPlace());
		map.put("MoveDate", ut._transToCE_Year(getMoveDate()));
		map.put("KeepUnit", getKeepUnit());		
		map.put("Keeper", getKeeper());
		map.put("UseUnit", getUseUnit());
		map.put("UserNo", getUserNo());
		map.put("UserNote", getUserNote());
		map.put("Place1", getPlace1());
		map.put("Place", getPlace());
		map.put("OriginalDeprMethod", getOriginalDeprMethod());
		map.put("OriginalBuildFeeCB", getOriginalBuildFeeCB());
		map.put("OriginalDeprUnitCB", getOriginalDeprUnitCB());
		map.put("OriginalDeprPark", getOriginalDeprPark());
		map.put("OriginalDeprUnit", getOriginalDeprUnit());
		map.put("OriginalDeprUnit1", getOriginalDeprUnit1());
		map.put("OriginalDeprAccounts", getOriginalDeprAccounts());
		map.put("OriginalScrapValue", getOriginalScrapValue());
		map.put("OriginalDeprAmount", getOriginalDeprAmount());
		map.put("originalaccumdepr1", this.getOriginalaccumdepr1());
		map.put("originalaccumdepr2", this.getOriginalaccumdepr2());
		map.put("OriginalAccumDepr", originalAccumdepr);
		map.put("OriginalApportionMonth", getOriginalApportionMonth());
		map.put("OriginalMonthDepr", getOriginalMonthDepr());
		map.put("OriginalMonthDepr1", getOriginalMonthDepr1());
		map.put("OriginalApportionEndYM", ut._transToCE_Year(getOriginalApportionEndYM()));
		map.put("DeprMethod", getDeprMethod());
		map.put("BuildFeeCB", getBuildFeeCB());
		map.put("DeprUnitCB", getDeprUnitCB());
		map.put("DeprPark", getDeprPark());
		map.put("DeprUnit", getDeprUnit());
		map.put("DeprUnit1", getDeprUnit1());
		map.put("DeprAccounts", getDeprAccounts());
		map.put("ScrapValue", getScrapValue());
		map.put("DeprAmount", getDeprAmount());
		map.put("AccumDepr", getAccumDepr());
		map.put("ApportionMonth", getApportionMonth());
		map.put("MonthDepr", getMonthDepr());
		map.put("MonthDepr1", getMonthDepr1());
		map.put("ApportionEndYM", ut._transToCE_Year(getApportionEndYM()));
		map.put("NoDeprSet", getNoDeprSet());
		map.put("Notes", getNotes());
		map.put("BarCode", getDifferenceKind() + "-" + getPropertyNo() + "-" + newserialNo);
		map.put("OldPropertyNo", getOldPropertyNo());
		map.put("OldSerialNo", getOldSerialNo());
		map.put("Notes1", getNotes1());
		map.put("EditID", getEditID());
		map.put("EditDate", ut._transToCE_Year(getEditDate()));
		map.put("EditTime", getEditTime());
		map.put("otherLimitYear",getOtherLimitYear());
		map.put("originalLimitYear",getOriginalLimitYear());
		
		map.put("propertyName1",getPropertyName1());
		
		map.put("individualSetDepr", getIndividualSetDepr());
		map.put("verifyYM", ut._transToCE_Year(getVerifyYM()));
		map.put("selfdepr", getSelfdepr());
		
		return map;
	}
	
	
		private DBServerTools doGetDBServerTools(){
			DBServerTools dbt = new DBServerTools();
			dbt._setDatabase(new Database());
			return dbt;
		}
	
		private String querySerialNo(){
			String result = "";
			String sql = "";
			sql = "select case when max(serialNo) is null then '0000001' else RIGHT('0000000' + CAST((max(serialNo) + 1) as varchar), 7) end from UNTMP_MovableDetail where enterOrg = " + Common.sqlChar(enterOrg) + "and ownership = " + Common.sqlChar(ownership) + "and differencekind = " + Common.sqlChar(differenceKind) + " and propertyNo = " + Common.sqlChar(propertyNo);
			return doGetDBServerTools()._execSQL_asString(sql);
		}
		
	
//傳回執行insert前之檢查sql
protected String[][] getInsertCheckSQL(){
	trueOriginalAmount = originalAmount==null || "".equals(originalAmount)?"0":originalAmount;
	trueOriginalUnit = originalUnit==null || "".equals(originalUnit)?"0":originalUnit;
	trueOriginalBV = originalBV==null || "".equals(originalBV)?"0":originalBV;
	//取得批號
	Database db = new Database();
	ResultSet rs;	
	String sql="select (max(lotNo) + 1) as lotNo from UNTMP_Movable " +
		" where enterOrg = " + Common.sqlChar(enterOrg) + 
		" and ownership = " + Common.sqlChar(ownership) + 
		" and propertyNo = " + Common.sqlChar(propertyNo) +
		" and differenceKind = " + Common.sqlChar(differenceKind) + 
		"";		
	try {		
		rs = db.querySQL(sql);
		if (rs.next()){
			if("".equals(checkGet(rs.getString("lotNo")))){
				setLotNo("0000001");
			}else{
				setLotNo(Common.formatFrontZero(rs.getString("lotNo"),7));	
			}		    
		}else{
			setLotNo("0000001");
		}
		
		//取得財產分號-起,訖
		int serialNoe = (int)Math.round(java.lang.Math.ceil(Double.parseDouble(this.getOriginalAmount())));
		sql="select (max(serialNo) + 1) as serialNoS, (max(serialNo) + " + serialNoe +") as serialNoE "+
			" from UNTMP_MovableDetail " +
			" where enterOrg = " + Common.sqlChar(enterOrg) + 
			" and ownership = " + Common.sqlChar(ownership) + 
			" and propertyNo = " + Common.sqlChar(propertyNo) + 
			" and differenceKind = " + Common.sqlChar(differenceKind) +
			"";		
			rs = db.querySQL(sql);
			if (rs.next()){
				if("".equals(checkGet(rs.getString("serialNoS"))) || "".equals(checkGet(rs.getString("serialNoE")))){
					setSerialNoS("0000001");
					setSerialNoE("0000001");
				}else{
					setSerialNoS(Common.formatFrontZero(rs.getString("serialNoS"),7));
					if(Integer.parseInt(trueOriginalUnit)==0){
						setSerialNoE(Common.formatFrontZero(rs.getString("serialNoS"),7));
					}else{
						setSerialNoE(Common.formatFrontZero(rs.getString("serialNoE"),7));
					}
				}
			} else {
				setSerialNoS("0000001");
				setSerialNoE("0000001");
			}

		//取得UNTMP_MovableDetail分號
		sql="select (max(serialNo) + 1) as serialNo from UNTMP_MovableDetail " +
			" where enterOrg = " + Common.sqlChar(enterOrg) + 
			" and ownership = " + Common.sqlChar(ownership) + 
			" and propertyNo = " + Common.sqlChar(propertyNo) + 
			" and differenceKind = " + Common.sqlChar(differenceKind) +
			"";		
			rs = db.querySQL(sql);
			if (rs.next()){
				if("".equals(checkGet(rs.getString("serialNo")))){
					setSerialNo("0000001");
				}else{
					setSerialNo(Common.formatFrontZero(rs.getString("serialNo"),7));	
				}		    
			}else{
				setSerialNo("0000001");
			}
			
	} catch (Exception e) {
		e.printStackTrace();
	} finally {
		db.closeAll();
	}
	String[][] checkSQLArray = new String[1][4];
 	checkSQLArray[0][0]=" select count(*) as checkResult from UNTMP_Movable where 1=1 " + 
		" and enterOrg = " + Common.sqlChar(enterOrg) + 
		" and ownership = " + Common.sqlChar(ownership) + 
		" and propertyNo = " + Common.sqlChar(propertyNo) + 
		" and lotNo = " + Common.sqlChar(lotNo) + 
		" and differenceKind = " + Common.sqlChar(differenceKind) +
		"";
	checkSQLArray[0][1]=">";
	checkSQLArray[0][2]="0";
	checkSQLArray[0][3]="該筆資料己重複，請重新輸入！";
	return checkSQLArray;
}


private String checkAmount(){
	String old_originalAmount = "";
	
	String sql = "select case when originalamount is null then 0 else originalamount end as originalamount from UNTMP_MOVABLE" +
					" where 1=1" +
					" and enterorg = " + Common.sqlChar(getEnterOrg()) +
					" and ownership = " + Common.sqlChar(getOwnership()) + 
					" and differencekind = " + Common.sqlChar(getDifferenceKind()) + 
					" and propertyno = " + Common.sqlChar(getPropertyNo()) + 
					" and lotno = " + Common.sqlChar(getLotNo()) + 
					"";
	old_originalAmount = doGetDBServerTools()._execSQL_asString(sql);
	

	return old_originalAmount;	
}

//依使用者所輸入的「原始入帳－數量」新增資料至「動產主檔－批號明細UNTMP_MovableDetail」
protected String getInsertUntmpMovableDetail(){
	UNTMP002F obj = this;
	StringBuffer sbSQL = new StringBuffer("");
	int counter=0;
	if(Double.valueOf(trueOriginalUnit)==0){
		counter=1;
		OriginalAmount_detail = bookAmount;
		OriginalBV_detail = bookValue;
		BookAmount_detail = bookAmount;
		BookValue_detail = bookValue;		
	}else{
		NumberFormat _format = NumberFormat.getInstance(Locale.US);
	    Number number;
		try {
			number = _format.parse(this.getOriginalAmount());
			counter = (int)Math.round(java.lang.Math.ceil(Double.parseDouble(number.toString())));
			if (counter == 1) {
				OriginalAmount_detail = this.getOriginalAmount();
				BookAmount_detail = this.getOriginalAmount();
				OriginalBV_detail = String.valueOf(Double.parseDouble(this.getOriginalAmount()) * Double.parseDouble(originalUnit));
				BookValue_detail = String.valueOf(Double.parseDouble(this.getOriginalAmount()) * Double.parseDouble(originalUnit));;
			} else {
				OriginalAmount_detail = "1";
				BookAmount_detail = "1";
				OriginalBV_detail = originalUnit;
				BookValue_detail = originalUnit;
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
	}
	
	String newserialNo = querySerialNo();
	// if (新分號起始號 == 修改前分號起始號+修改前數量) or (新數量 <= 原數量) => 使用原來的分號起始號
	if (Common.getInt(newserialNo) == Common.getInt(getSerialNo()) + Math.ceil(Common.getNumeric(checkOriginalAmount))
			|| Math.ceil(Common.getNumeric(trueOriginalAmount)) <= Math.ceil(Common.getNumeric(checkOriginalAmount))) {
		newserialNo = getSerialNo();
	}
	String stemp = "";
	SQLCreator sl = new SQLCreator();
	
	while(counter > 0){
	    counter--;
	    stemp = String.valueOf(Integer.parseInt(newserialNo) + counter);
	    calBookValue(counter);
	    sbSQL.append(sl._obtainSQLforInsert("UNTMP_MOVABLEDetail", getPKMap_forDetail(stemp), getRecordMap_forDetail(stemp))).append(":;:");
	}
	
	return sbSQL.toString();
}

	private void calBookValue(double counter){
		String totalCount = String.valueOf((int)java.lang.Math.ceil(Double.parseDouble(this.getBookAmount())));
		
		String detail_originalBV = "0";
		String detail_bookValue = "0";
		String detail_netValue = "0";
		
		MathTools mt = new MathTools();
		
		detail_originalBV = mt._division_withBigDecimal(this.getOriginalBV(), totalCount);
		detail_bookValue = mt._division_withBigDecimal(this.getBookValue(), totalCount);
		detail_netValue = mt._division_withBigDecimal(this.getNetValue(), totalCount);
		
		this.setDetailBookAmount(BookAmount_detail);
		this.setDetailOriginalAmount(OriginalAmount_detail);
		
		if(counter == 1){
			String countTemp = "";
			String tempStr = "";
			String resultStr = "";
			
			countTemp = mt._subtraction(totalCount, "1");
			
			tempStr = mt._multiplication_withBigDecimal(detail_originalBV, countTemp);
			resultStr = mt._subtraction_withBigDecimal(this.getOriginalBV(), tempStr);
			this.setDetailOriginalBV(resultStr);
			
			tempStr = mt._multiplication_withBigDecimal(detail_bookValue, countTemp);
			resultStr = mt._subtraction_withBigDecimal(this.getBookValue(), tempStr);
			this.setDetailBookValue(resultStr);
			
			tempStr = mt._multiplication_withBigDecimal(detail_netValue, countTemp);
			resultStr = mt._subtraction_withBigDecimal(this.getNetValue(), tempStr);
			this.setDetailNetValue(resultStr);
					
		}else{
			this.setDetailOriginalBV(detail_originalBV);
			this.setDetailBookValue(detail_bookValue);
			this.setDetailNetValue(detail_netValue);
		}
	}


//傳回insert sql
protected String[] getInsertSQL(){
	String movableDetailSQL[];
	movableDetailSQL = getInsertUntmpMovableDetail().split(":;:");
	String[] execSQLArray = new String[movableDetailSQL.length+1];
	
	for(int i=1;i<=movableDetailSQL.length;i++){
		execSQLArray[i]= movableDetailSQL[i-1];
	}
	int serialNoe = (int)Math.round(java.lang.Math.ceil(Double.parseDouble(this.getOriginalAmount())));
	this.setSerialNoE(Common.formatFrontZero(String.valueOf(Integer.parseInt(this.getSerialNoS()) + serialNoe - 1),7));
	
	execSQLArray[0] = new SQLCreator()._obtainSQLforInsert("UNTMP_MOVABLE", getPKMap(), getRecordMap());
	
	return execSQLArray;
}

protected String deleteUntmp_movableDetail(){
	String strSQL="";
	strSQL +=" delete UNTMP_MovableDetail where 1=1 " +
			" and enterOrg = " + Common.sqlChar(enterOrg) +
			" and ownership = " + Common.sqlChar(ownership) +
			" and differenceKind = " + Common.sqlChar(differenceKind) +	
			" and propertyNo = " + Common.sqlChar(propertyNo) +
			" and lotNo = " + Common.sqlChar(lotNo) +		
			":;:";
	strSQL +=" delete UNTMP_Attachment2 where 1=1 " +
			" and enterOrg = " + Common.sqlChar(enterOrg) +
			" and ownership = " + Common.sqlChar(ownership) +
			" and differenceKind = " + Common.sqlChar(differenceKind) +
			" and propertyNo = " + Common.sqlChar(propertyNo) +
			" and lotNo = " + Common.sqlChar(lotNo) +
			":;:";
	return strSQL;
}

//修改「原始入帳－數量 or 原始入帳－單價 or原始入帳－總價」時
protected String getUpdateUntmpMovableDetail(){
	trueOriginalAmount = (originalAmount==null || "".equals(originalAmount))?"0":originalAmount;
	trueOriginalUnit = (originalUnit==null || "".equals(originalUnit))?"0":originalUnit;
	trueOriginalBV = (originalBV==null || "".equals(originalBV))?"0":originalBV;
	String strSQL = "";
	//新增該批號的「批號明細」
	strSQL += getInsertUntmpMovableDetail();
	//	新增該批號的「批號明細附屬設備」，資料同該批號的「批號附屬設備」，並將「資料狀態」設為「1:現存」
	strSQL += getInsertUntmpAttachment1();
	return strSQL;
}

//新增該批號的「批號明細附屬設備」，資料同該批號的「批號附屬設備」，並將「資料狀態」設為「1:現存」
protected String getInsertUntmpAttachment1(){
	Database db = new Database();    
	String sql = "";
	String strSQL = "";
	ResultSet rs = null;
	//取得批號附屬設備檔	
	sql="select * from UNTMP_Attachment1 " +
		" where enterOrg = " + Common.sqlChar(enterOrg) + 
		" and ownership = " + Common.sqlChar(ownership) + 
		" and propertyNo = " + Common.sqlChar(propertyNo) + 
		" and lotNo = " + Common.sqlChar(lotNo) +
		" and differenceKind = " + Common.sqlChar(differenceKind) +
		"";		
	try {   
		rs = db.querySQL(sql);
		while (rs.next()){
			int counter=0;
			if(Integer.parseInt(trueOriginalUnit)==0){
				counter=1;
			}else{
				counter = Integer.parseInt(originalAmount);
			}
			while(counter > 0){
			    counter--;
			    strSQL+=" insert into UNTMP_Attachment2( " +
						" enterOrg,"+
						" ownership,"+
						" propertyNo,"+
						" lotNo,"+
						" serialNo,"+
						" serialNo1,"+
						" equipmentName,"+
						" buyDate,"+
						" equipmentUnit,"+
						" equipmentAmount,"+
						" unitPrice,"+
						" totalValue,"+
						" dataState,"+
						" notes,"+
						" editID,"+
						" editDate,"+
						" editTime,"+
						" differencekind"+
					" )Values(" +
						Common.sqlChar(rs.getString("enterOrg")) + "," +
						Common.sqlChar(rs.getString("ownership")) + "," +
						Common.sqlChar(rs.getString("propertyNo")) + "," +
						Common.sqlChar(rs.getString("lotNo")) + "," +
						"(select RIGHT('0000000' + CAST(serialNos+"+counter+" as varchar),7) from untmp_movable where enterOrg = "+Common.sqlChar(enterOrg)+" and ownership = " + Common.sqlChar(ownership) +" and propertyNo = " + Common.sqlChar(propertyNo) +" and lotNo = " + Common.sqlChar(lotNo) +")" + "," +
						Common.sqlChar(rs.getString("serialNo1")) + "," +
						Common.sqlChar(rs.getString("equipmentName")) + "," +
						Common.sqlChar(rs.getString("buyDate")) + "," +
						Common.sqlChar(rs.getString("equipmentUnit")) + "," +
						Common.sqlChar(rs.getString("equipmentAmount")) + "," +
						Common.sqlChar(rs.getString("unitPrice")) + "," +
						Common.sqlChar(rs.getString("totalValue")) + "," +
						"'" + 1 + "'" + "," +
						Common.sqlChar(rs.getString("notes")) + "," +
						Common.sqlChar(rs.getString("editID")) + "," +
						Common.sqlChar(rs.getString("editDate")) + "," +
						Common.sqlChar(rs.getString("editTime")) + "," +
						Common.sqlChar(rs.getString("differencekind")) + ")" +
						":;:";
			}
		}
	} catch (Exception e) {
		e.printStackTrace();
	} finally {
		db.closeAll();
	}
	return strSQL;
}

//傳回update sql
protected String[] getUpdateSQL(){
	String movableDetailSQL[]=null;
	String deleteMovalbeDetailSQL[]=null;
	
	trueOriginalAmount = originalAmount==null || "".equals(originalAmount)?"0":originalAmount;
	trueOriginalUnit = originalUnit==null || "".equals(originalUnit)?"0":originalUnit;
	trueOriginalBV = originalBV==null || "".equals(originalBV)?"0":originalBV;
	checkOriginalAmount = checkOriginalAmount==null || "".equals(checkOriginalAmount)?"0":checkOriginalAmount;
	checkOriginalUnit = checkOriginalUnit==null || "".equals(checkOriginalUnit)?"0":checkOriginalUnit;
	checkOriginalBV = checkOriginalBV==null || "".equals(checkOriginalBV)?"0":checkOriginalBV;
	
	if (!"UNTCH001F02_2".equals(this.getCaller())) { // 財產維護無論如何不能觸發這邊
		checkOriginalAmount = checkAmount();
		
		if(Double.parseDouble(trueOriginalAmount) != Double.parseDouble(checkOriginalAmount)){	//批的數量改變時
			deleteMovalbeDetailSQL = deleteUntmp_movableDetail().split(":;:");
			movableDetailSQL = getUpdateUntmpMovableDetail().split(":;:");
		}
	}
	
	ArrayList<String> sqls = new ArrayList<String>();
	if (deleteMovalbeDetailSQL != null) {
		sqls.addAll(Arrays.asList(deleteMovalbeDetailSQL));
	}
	if (movableDetailSQL != null) {
		sqls.addAll(Arrays.asList(movableDetailSQL));
	}
	
	//財產維護不修改批
	if (!"UNTCH001F02_2".equals(this.getCaller())) {
		sqls.add(new SQLCreator()._obtainSQLforUpdate("UNTMP_MOVABLE", getPKMap(), getRecordMap()));
	}
	
	UNTCH_Tools ut = new UNTCH_Tools();
	if ("UNTCH001F02_2".equals(this.getCaller())) { // 財產維護不是批的修改 且沒有原始保管使用資料
		String tmpSQL = "update UNTMP_MOVABLEDETAIL set" +
						" usernote = " + Common.sqlChar(getUserNote()) + "," +
						" place1 = " + Common.sqlChar(getPlace1()) + "," +
						" place = " + Common.sqlChar(getPlace()) + "," +
						" notes = " + Common.sqlChar(this.getNotes()) + "," + 
						" licenseplate = " + Common.sqlChar(getLicensePlate()) + "," +
						" purpose = " + Common.sqlChar(getPurpose()) + "," +
						" propertyname1 = " + Common.sqlChar(this.getPropertyName1()) + "," +
						" editdate = " + Common.sqlChar(this.getEditDate())+ "," +
						" editid = " + Common.sqlChar(this.getEditID()) + "," +
						" edittime = " + Common.sqlChar(this.getEditTime()) +
						" where enterorg = " + Common.sqlChar(getEnterOrg()) +
						" and ownership = " + Common.sqlChar(getOwnership()) +
						" and caseno = " + Common.sqlChar(getCaseNo()) +
						" and differencekind = " + Common.sqlChar(getDifferenceKind()) +
						" and propertyno = " + Common.sqlChar(getPropertyNo()) +
						" and lotno = " + Common.sqlChar(getLotNo()) +
						" and serialno = " + Common.sqlChar(getSerialNo());
		sqls.add(tmpSQL);
	} else { // 動產基本資料是批的修改  而且有原始保管使用資料
		String detailoriginalamount = (Common.getNumeric(getOriginalAmount()) >= 1)? "1" : getOriginalAmount();
		String tmpSQL = "update UNTMP_MOVABLEDETAIL set" + 
						" originaldeprmethod = " + Common.sqlChar(getOriginalDeprMethod()) + "," +
						" originalbuildfeecb = " + Common.sqlChar(getOriginalBuildFeeCB()) + "," +
						" originaldeprunitcb = " + Common.sqlChar(getOriginalDeprUnitCB()) + "," +
						" originaldeprpark = " + Common.sqlChar(getOriginalDeprPark()) + "," +
						" originaldeprunit = " + Common.sqlChar(getOriginalDeprUnit()) + "," +
						" originaldeprunit1 = " + Common.sqlChar(getOriginalDeprUnit1()) + "," +
						" originaldepraccounts = " + Common.sqlChar(getOriginalDeprAccounts()) + "," +
						" originalscrapvalue = " + Common.sqlChar(getOriginalScrapValue()) + "," +
						" originaldepramount = " + Common.sqlChar(getOriginalDeprAmount()) + "," +
						" originalaccumdepr1 = " + Common.sqlChar(this.getOriginalaccumdepr1()) + "," +
						" originalaccumdepr2 = " + Common.sqlChar(this.getOriginalaccumdepr2()) + "," +
						" originalaccumdepr = " + Common.sqlChar(getOriginalAccumDepr()) + "," +
						" originalapportionmonth = " + Common.sqlChar(getOriginalApportionMonth()) + "," +
						" originalmonthdepr = " + Common.sqlChar(getOriginalMonthDepr()) + "," +
						" originalmonthdepr1 = " + Common.sqlChar(getOriginalMonthDepr1()) + "," +
						" originalapportionendym = " + Common.sqlChar(getOriginalApportionEndYM()) + "," +
						" deprmethod = " + Common.sqlChar(getDeprMethod()) + "," +
						" buildfeecb = " + Common.sqlChar(getBuildFeeCB()) + "," +
						" deprunitcb = " + Common.sqlChar(getDeprUnitCB()) + "," +
						" deprpark = " + Common.sqlChar(getDeprPark()) + "," +
						" deprunit = " + Common.sqlChar(getDeprUnit()) + "," +
						" deprunit1 = " + Common.sqlChar(getDeprUnit1()) + "," +
						" depraccounts = " + Common.sqlChar(getDeprAccounts()) + "," +
						" scrapvalue = " + Common.sqlChar(getScrapValue()) + "," +
						" depramount = " + Common.sqlChar(getDeprAmount()) + "," +
						" accumdepr = " + Common.sqlChar(getAccumDepr()) + "," +
						" apportionmonth = " + Common.sqlChar(getApportionMonth()) + "," +
						" monthdepr = " + Common.sqlChar(getMonthDepr()) + "," +
						" monthdepr1 = " + Common.sqlChar(getMonthDepr1()) + "," +
						" apportionendym = " + Common.sqlChar(ut._transToCE_Year(getApportionEndYM())) + "," + //TODO
						" originalamount = " + Common.sqlChar(detailoriginalamount) + "," +
						" licenseplate = " + Common.sqlChar(getLicensePlate()) + "," +
						" purpose = " + Common.sqlChar(getPurpose()) + "," +
						" originallimityear = " + Common.sqlChar(getOriginalLimitYear()) + "," +
						" otherlimityear = " + Common.sqlChar(getOtherLimitYear()) + "," +
						" individualsetdepr = " + Common.sqlChar(getIndividualSetDepr()) + "," +
						" verifyym = " + Common.sqlChar(ut._transToCE_Year(getVerifyYM())) + "," +
						" selfdepr = " + Common.sqlChar(getSelfdepr()) +
						" where enterorg = " + Common.sqlChar(getEnterOrg()) +
						" and ownership = " + Common.sqlChar(getOwnership()) +
						" and caseno = " + Common.sqlChar(getCaseNo()) +
						" and differencekind = " + Common.sqlChar(getDifferenceKind()) +
						" and propertyno = " + Common.sqlChar(getPropertyNo()) +
						" and lotno = " + Common.sqlChar(getLotNo()) ;
		sqls.add(tmpSQL);
	}
	
	String upValue = "";
	// 財產維護 不會動到帳面資料 不應該跑此
	if(!"UNTCH001F02_2".equals(this.getCaller()) && (Double.parseDouble(trueOriginalUnit) != Double.parseDouble(checkOriginalUnit) || Double.parseDouble(trueOriginalBV) != Double.parseDouble(checkOriginalBV))){
		if ("".equals(originalUnit) || originalUnit == null ){
			upValue = " originalBV = "+ Common.sqlChar(originalBV)+", bookValue = "+ Common.sqlChar(originalBV) + ", netvalue = "+ Common.sqlChar(originalBV) + " - isnull(accumdepr,0) ";
		} //104.12.17 若originalAmount<=1, 則明細的價格設定為批的總價
		else if(Double.parseDouble(originalAmount)<=1){
			upValue = " originalBV = "+ Common.sqlChar(originalBV)+", bookValue = "+ Common.sqlChar(originalBV) + ", netvalue = "+ Common.sqlChar(originalBV) + " - isnull(accumdepr,0) ";
		}
		else{
			upValue = " originalBV = "+ Common.sqlChar(originalUnit)+", bookValue = "+ Common.sqlChar(originalUnit) + ", netvalue = "+ Common.sqlChar(originalUnit) + " - isnull(accumdepr,0) ";
		}
		String tmpsql = "update UNTMP_MovableDetail set " +
					    upValue +
						" where 1=1 " +
						" and enterOrg = " + Common.sqlChar(enterOrg) +
						" and ownership = " + Common.sqlChar(ownership) +
						" and propertyNo = " + Common.sqlChar(propertyNo) +
						" and lotNo = " + Common.sqlChar(lotNo) +
						" and differenceKind = " + Common.sqlChar(differenceKind);
		sqls.add(tmpsql);
	}
	Database db=new Database();
	try{
		String[] sourceArray={"UNTMP_REDUCEDETAIL", "UNTMP_MOVABLE", "UNTMP_DEALDETAIL"};
		String sql = null;
		
		for(int i=0;i<sourceArray.length;i++){
			sql = "UPDATE "+sourceArray[i].toString()+" SET"+
						" specification = "+Common.sqlChar(this.getSpecification())+","+
						" nameplate = "+Common.sqlChar(this.getNameplate())+
						" where 1=1 " +
						" and enterorg = " + Common.sqlChar(enterOrg) +
						" and ownership = " + Common.sqlChar(ownership) +
						" and propertyno = " + Common.sqlChar(propertyNo) +
						" and lotno = " + Common.sqlChar(lotNo) ;
			
			if("UNTMP_REDUCEDETAIL".equals(sourceArray[i].toString())){
			}else{
				sql += " and differencekind = " + Common.sqlChar(differenceKind);
			}
			db.exeSQL(sql);
		}
		
	}catch(Exception e){
		e.printStackTrace();
	}finally{
		db.closeAll();
	}
	
	return sqls.toArray(new String[sqls.size()]);
}

	

//傳回delete sql
protected String[] getDeleteSQL(){
	String[] execSQLArray = new String[4];
	execSQLArray[0]=" delete UNTMP_Movable where 1=1 " +
					" and enterOrg = " + Common.sqlChar(enterOrg) +
					" and ownership = " + Common.sqlChar(ownership) +
					" and caseNo = " + Common.sqlChar(caseNo) +
					" and propertyNo = " + Common.sqlChar(propertyNo) +
					" and lotNo = " + Common.sqlChar(lotNo) +
					" and differenceKind = " + Common.sqlChar(differenceKind) +
					"";
	execSQLArray[1]=" delete UNTMP_MovableDetail where 1=1 " +
					" and enterOrg = " + Common.sqlChar(enterOrg) +
					" and ownership = " + Common.sqlChar(ownership) +
					" and propertyNo = " + Common.sqlChar(propertyNo) +
					" and lotNo = " + Common.sqlChar(lotNo) +
					" and differenceKind = " + Common.sqlChar(differenceKind) +
					"";
	execSQLArray[2]=" delete UNTMP_Attachment2 where 1=1 " +
					" and enterOrg = " + Common.sqlChar(enterOrg) +
					" and ownership = " + Common.sqlChar(ownership) +
					" and propertyNo = " + Common.sqlChar(propertyNo) +
					" and lotNo = " + Common.sqlChar(lotNo) +
					" and differenceKind = " + Common.sqlChar(differenceKind) +
					"";
	execSQLArray[3]=" delete UNTMP_Attachment1 where 1=1 " +
					" and enterOrg = " + Common.sqlChar(enterOrg) +
					" and ownership = " + Common.sqlChar(ownership) +
					" and propertyNo = " + Common.sqlChar(propertyNo) +
					" and lotNo = " + Common.sqlChar(lotNo) +
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

public UNTMP002F  queryOne() throws Exception{
	Database db = new Database();
	UNTMP002F obj = this;
	try {
		String sql=" select b.organSName as enterOrgName, " +"\n"+
		
					
					" (select codename from SYSCA_CODE z where codekindid = 'SKD' and z.codeid = a.sourcekind) as sourcekindName, " +
					" (select codename from SYSCA_CODE z where codekindid = 'CAA' and z.codeid = a.cause and codecon1 in (1,4)) as causeName, " +
					" (select c.propertyName from SYSPK_PropertyCode c where 1=1 and c.enterOrg in('000000000A',a.enterOrg) and a.propertyNo=c.propertyNo and c.propertyType='1') as propertyName, " +"\n"+
					" (select c.propertyUnit from SYSPK_PropertyCode c where 1=1 and c.enterOrg in('000000000A',a.enterOrg) and a.propertyNo=c.propertyNo and c.propertyType='1') as propertyUnit, " +"\n"+
					" (select c.material from SYSPK_PropertyCode c where 1=1 and c.enterOrg in('000000000A',a.enterOrg) and a.propertyNo=c.propertyNo and c.propertyType='1') as material, " +"\n"+
					" (select c.limitYear from SYSPK_PropertyCode c where 1=1 and c.enterOrg in('000000000A',a.enterOrg) and a.propertyNo=c.propertyNo and c.propertyType='1') as limitYear, " +"\n"+
					" (select d.storeName from UNTMP_Store d where 1=1 and a.storeNo=d.storeNo and a.enterOrg=d.enterOrg) as storeName, " +"\n"+
					" (select placename from SYSCA_PLACE z where z.enterorg = a.enterorg and z.placeno = d.place1) as place1Name,"+"\n"+
					" (select placename from SYSCA_PLACE z where z.enterorg = a.enterorg and z.placeno = d.originalplace1) as originalplace1Name,"+"\n"+
					
					" case d.otherLimitYear when null then 0 else d.otherLimitYear end as otherLimitYear," +
					" a.SourceDate, a.BuyDate, a.PropertyKind, a.FundType, a.Valuable, a.CaseSerialNo, a.notes, d.notes as notes2," +
					" a.SerialNoS, a.SerialNoE, a.OtherPropertyUnit, a.OtherMaterial, a.PropertyName1 as propertyname1_movable," +
					" a.ApproveDate, a.ApproveDoc, a.OriginalNote, a.OriginalAmount as OriginalAmount_a, a.OriginalUnit," +
					" a.OriginalBV as OriginalBV_a,a.AccountingTitle," +
					" a.FundsSource, a.FundsSource1, a.GrantValue, a.ArticleName, a.Nameplate," +
					" a.Specification, a.StoreNo, a.SourceKind, a.SourceDoc, a.EscrowOriValue," +
					" d.keepunit, d.keeper, d.useunit, d.userno," +
					" a.EscrowOriAccumDepr, a.OldSerialNoS, a.OldSerialNoE, d.oldserialno, a.Cause, a.Cause1, a.picture, a.caseno as caseno2, " +
					" a.originalapportionendym as originalapportionendym1, a.EngineeringNo, " +
					" d.* " +
					//" d.originalMoveDate, d.originalPlace, d.originalKeepUnit, d.originalKeeper, d.originalUseUnit, d.originalUser, d.deprmethod, "+"\n"+
					//" d.originalplace1, d.place1, d.originalUserNote,d.bookvalue as bookValueDetail,d.netvalue as netValueDetail" + "\n"+
					
					
					" from UNTMP_Movable a, SYSCA_Organ b, UNTMP_MovableDetail d where 1=1 " +"\n"+
					
					" and a.enterOrg = b.organID " +"\n"+
					" and a.enterorg = d.enterorg " +
					" and a.ownership = d.ownership " +
					" and a.propertyno = d.propertyno " +
					" and a.lotno = d.lotno " + "\n"+
					" and a.differencekind = d.differencekind " + "\n"+
					" and d.enterOrg = " + Common.sqlChar(enterOrg) +"\n"+
					" and d.ownership = " + Common.sqlChar(ownership) +"\n"+
					" and d.propertyNo = " + Common.sqlChar(propertyNo) +"\n"+
					" and d.lotNo = " + Common.sqlChar(lotNo) +"\n"+
					" and d.differenceKind = " + Common.sqlChar(differenceKind) +"\n"+
					" and d.serialno = " + Common.sqlChar(serialNo) +"\n"+
					"";
//System.out.println("-- untmp002f queryOne -- "+sql);
		UNTCH_Tools ul = new UNTCH_Tools();		
		ResultSet rs = db.querySQL(sql);
		if (rs.next()){
			//其他資料
			obj.setSourceKindName(rs.getString("SourceKindName"));
			obj.setCauseName(rs.getString("causeName"));
			obj.setPropertyNoName(rs.getString("PropertyName"));
			obj.setPropertyUnit(rs.getString("PropertyUnit"));
			obj.setMaterial(rs.getString("Material"));
			obj.setLimitYear(rs.getString("otherLimitYear"));
			obj.setOriginalLimitYear(rs.getString("originallimityear"));
			obj.setStoreNoName(rs.getString("StoreName"));
			obj.setPlace1Name(rs.getString("place1Name"));
			obj.setOriginalPlace1Name(rs.getString("Originalplace1Name"));
			
			//動產主檔
			obj.setOtherLimitYear(rs.getString("OtherLimitYear"));
			obj.setSourceDate(ul._transToROC_Year(rs.getString("SourceDate")));
			obj.setBuyDate(ul._transToROC_Year(rs.getString("BuyDate")));
			obj.setPropertyKind(rs.getString("PropertyKind"));
			obj.setFundType(rs.getString("FundType"));
			obj.setValuable(rs.getString("Valuable"));
			obj.setSerialNoS(rs.getString("SerialNoS"));
			obj.setSerialNoE(rs.getString("SerialNoE"));
			if (rs.getString("caseserialno") != null) {
				obj.setCaseSerialNo(Common.formatFrontZero(rs.getString("caseserialno"),5));
			}
			obj.setPropertyName1(rs.getString("PropertyName1"));
			obj.setPropertyName1_movable(rs.getString("propertyname1_movable"));
			obj.setCause(rs.getString("Cause"));
			obj.setOtherPropertyUnit(rs.getString("OtherPropertyUnit"));
			obj.setOtherMaterial(rs.getString("OtherMaterial"));
			obj.setCause1(rs.getString("Cause1"));
			obj.setApproveDate(ul._transToROC_Year(rs.getString("ApproveDate")));
			obj.setApproveDoc(rs.getString("ApproveDoc"));
			obj.setOriginalNote(rs.getString("OriginalNote"));
			obj.setOriginalAmount(rs.getString("OriginalAmount_a"));
			obj.setOriginalUnit(rs.getString("OriginalUnit"));
			obj.setOriginalBV(rs.getString("OriginalBV_a"));
			obj.setAccountingTitle(rs.getString("AccountingTitle"));
			obj.setFundsSource(rs.getString("FundsSource"));
			obj.setFundsSource1(rs.getString("FundsSource1"));
			obj.setGrantValue(rs.getString("GrantValue"));
			obj.setArticleName(rs.getString("ArticleName"));
			obj.setNameplate(rs.getString("Nameplate"));
			obj.setSpecification(rs.getString("Specification"));
			obj.setStoreNo(rs.getString("StoreNo"));
			obj.setSourceKind(rs.getString("SourceKind"));
			obj.setSourceDoc(rs.getString("SourceDoc"));
			obj.setEscrowOriValue(rs.getString("EscrowOriValue"));
			obj.setEscrowOriAccumDepr(rs.getString("EscrowOriAccumDepr"));
			obj.setOldSerialNo(Common.get(rs.getString("oldserialno")));
			obj.setOldSerialNoS(rs.getString("OldSerialNoS"));
			obj.setOldSerialNoE(rs.getString("OldSerialNoE"));
			obj.setPicture(rs.getString("picture"));
			
			
			
			//動產明細檔
			obj.setEnterOrg(rs.getString("EnterOrg"));
			obj.setOwnership(rs.getString("Ownership"));
			obj.setCaseNo(rs.getString("caseno"));
			obj.setDifferenceKind(rs.getString("DifferenceKind"));
			obj.setEngineeringNo(rs.getString("EngineeringNo"));
			obj.setPropertyNo(rs.getString("PropertyNo"));
			obj.setLotNo(rs.getString("LotNo"));
			obj.setEnterDate(ul._transToROC_Year(rs.getString("EnterDate")));
			obj.setDataState(rs.getString("DataState"));
			obj.setVerify(rs.getString("Verify"));
			obj.setDtlOriginalAmountRO(rs.getString("OriginalAmount"));
			obj.setDtlOriginalBVRO(rs.getString("OriginalBV"));
			obj.setDtlBookAmountRO(rs.getString("BookAmount"));
			obj.setDtlBookValueRO(rs.getString("BookValue"));
			obj.setDtlNetValueRO(rs.getString("NetValue"));
			obj.setBookAmount(rs.getString("BookAmount"));
			obj.setBookValue(rs.getString("BookValue"));
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
			obj.setDtlOriginalApportionEndYMRO(rs.getString("originalapportionendym"));
			
			// 似乎從以前就漏抓了... 還是什麼原因我不知道 但852提出  我就加了
			obj.setDeprMethod(rs.getString("DeprMethod"));
			obj.setBuildFeeCB(rs.getString("BuildFeeCB"));
			obj.setDeprUnitCB(rs.getString("DeprUnitCB"));
			obj.setDeprPark(rs.getString("DeprPark"));
			obj.setDeprUnit(rs.getString("DeprUnit"));
			obj.setDeprUnit1(rs.getString("DeprUnit1"));
			obj.setDeprAccounts(rs.getString("DeprAccounts"));
			obj.setScrapValue(rs.getString("ScrapValue"));
			obj.setDeprAmount(rs.getString("DeprAmount"));
			obj.setAccumDepr(rs.getString("AccumDepr"));
			obj.setApportionMonth(rs.getString("ApportionMonth"));
			obj.setMonthDepr(rs.getString("MonthDepr"));
			obj.setMonthDepr1(rs.getString("MonthDepr1"));
			obj.setApportionEndYM(ul._transToROC_Year(rs.getString("ApportionEndYM")));
			
			obj.setOldPropertyNo(rs.getString("OldPropertyNo"));
			
			if ("UNTCH001F02_2".equals(this.getCaller()) || "UNTCH001F02_1".equals(this.getCaller())) {
				obj.setNotes(rs.getString("Notes2"));
			} else {
				obj.setNotes(rs.getString("Notes"));
			}
			obj.setNotes2(rs.getString("Notes2"));
			obj.setEditID(rs.getString("EditID"));
			obj.setEditDate(ul._transToROC_Year(rs.getString("EditDate")));
			obj.setEditTime(rs.getString("EditTime"));			
			obj.setOriginalMoveDate(ul._transToROC_Year(rs.getString("OriginalMoveDate")));
			obj.setOriginalPlace(rs.getString("OriginalPlace"));
			obj.setOriginalKeepUnit(rs.getString("OriginalKeepUnit"));
			obj.setOriginalKeeper(rs.getString("OriginalKeeper"));
			obj.setOriginalUseUnit(rs.getString("OriginalUseUnit"));
			obj.setOriginalUser(rs.getString("OriginalUser"));
			obj.setEngineeringNoName(ul._getEngineeringNoName(rs.getString("enterOrg"),rs.getString("engineeringNo")));
			obj.setOriginalDeprUnit1(rs.getString("originalDeprUnit1"));
			obj.setOriginalApportionEndYM(ul._transToROC_Year(rs.getString("originalapportionendym1")));
			obj.setPlace1(rs.getString("Place1"));
			obj.setOriginalPlace1(rs.getString("OriginalPlace1"));
			obj.setOriginalUserNote(rs.getString("originalUserNote"));
			obj.setKeeper(rs.getString("keeper"));
			obj.setKeepUnit(rs.getString("keepunit"));
			obj.setUseUnit(rs.getString("useunit"));
			obj.setUserNo(rs.getString("userno"));
			
			obj.setPlace(rs.getString("place"));
			obj.setUserNote(rs.getString("userNote"));
			obj.setLicensePlate(rs.getString("licensePlate"));
			obj.setPurpose(rs.getString("purpose"));
			
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
	UNTMP002F obj = this;
	Database db = new Database();
	
	try {
		String sql=" select distinct b.organSName,a.enterOrg, a.ownership, a.differenceKind, a.caseNo," +
					" (select z.codeName from sysca_code z where 1=1 and (z.codeKindID='owa' or z.codeKindID='OWA') and a.ownership=z.codeID) as ownershipName, " +
					" a.propertyNo, a.lotNo, c.codeName cause, " +
					" (select x.propertyName from SYSPK_PropertyCode x where x.propertyType='1' and x.propertyno = a.propertyno and x.enterOrg in (a.enterOrg,'000000000A') ) as propertyName, "+
					" (case a.dataState when '1' then '現存' when '2' then '已減損' else '' end) dataStateName, a.dataState, "+
					" (select codename from SYSCA_CODE z where codekindid = 'PKD' and z.codeid = a.propertyKind) propertyKind," +
					" a.bookAmount, a.bookValue, " +
					" a.serialNoS, a.serialNoE "+
					" from UNTMP_Movable a " +
					" left join SYSCA_Organ b on a.enterOrg = b.organID" +
					" left join SYSCA_Code c on a.cause = c.codeID and c.codeKindID='CAA'" +
					" "+
					" where 1=1 " +
					" ";
		
		sql+=" and a.enterOrg = " + Common.sqlChar(getEnterOrg()) ;
		sql+=" and a.ownership = " + Common.sqlChar(getOwnership()) ;
		sql+=" and a.caseNo = " + Common.sqlChar(getCaseNo()) ;
		sql+=" and a.differenceKind = " + Common.sqlChar(getDifferenceKind()) ;
		sql+=" and a.propertyNo = " + Common.sqlChar(getPropertyNo()) ;
		sql+=" and a.serialNoS = " + Common.sqlChar(getSerialNoS()) ;
		sql+=" and a.serialNoE = " + Common.sqlChar(getSerialNoE()) ;
		
		sql+=" order by a.enterOrg, a.ownership, a.propertyNo, a.lotNo, a.dataState ";
		//System.out.println("-- untmp002f queryAll -- "+sql);	
		ResultSet rs = db.querySQL(sql,true);
		UNTCH_Tools ucl = new UNTCH_Tools();
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
			rowArray[2]=rs.getString("caseno");
			rowArray[3]=rs.getString("differenceKind");
			rowArray[4]=rs.getString("propertyno");
			rowArray[5]=rs.getString("lotNo");			
			rowArray[6]=rs.getString("organSName"); 		
			rowArray[7]=ucl._getDifferenceKindName(rs.getString("differenceKind"));
			rowArray[8]=rs.getString("serialNoS")+"-"+rs.getString("serialNoE");
			rowArray[9]=rs.getString("propertyName");
			rowArray[10]=rs.getString("cause"); 
			rowArray[11]=rs.getString("dataStateName"); 
			rowArray[12]=rs.getString("bookAmount");
			rowArray[13]=rs.getString("bookValue");
			
			
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

	//取得條碼
	public String getMaxBarcode(){
		return this.getDifferenceKind() + "-" + this.getPropertyNo() + "-" + this.getSerialNo();
	}

}