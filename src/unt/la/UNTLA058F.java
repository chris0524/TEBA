/*
*<br>程式目的：土地合併分割重測重劃作業－增加單明細
*<br>程式代號：UNTLA058F
*<br>程式日期：
*<br>程式作者：Yuan-Ren Jheng
*<br>--------------------------------------------------------
*<br>修改作者　　修改日期　　　修改目的
*<br>--------------------------------------------------------
*/

package unt.la;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import TDlib_Simple.tools.src.StringTools;
import util.Common;
import unt.ch.UNTCH_Tools;
import util.Database;

public class UNTLA058F extends UNTLA054Q{

	String enterOrg;
	String ownership;
	String caseNo;
	String propertyNo;
	String serialNo;
	String property_AddName;
	String propertyName1;
	String signNo;
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
	String propertyKind;
	String fundType;
	String valuable;
	String taxCredit;
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
	String fundsSource1;
	String useSeparate;
	String useKind;
	String oriUseSeparate;
	String oriUseKind;
	String ownershipDate;
	String ownershipCause;
	String nonProof;
	String proofDoc;
	String ownershipNote;
	String field;
	String landRule;
	String sourceKind;
	String sourceDate;
	String sourceDoc;
	String oldOwner;
	String manageOrg;
	String useState;
	String appraiseDate;
	String notes1;
	String oldPropertyNo;
	String oldSerialNo;
	String useState1;
	String mergeDivision;
	String mergeDivisionBatch;
	String notes;
	String editID;
	String editDate;
	String editTime;
	String enterOrgName;
	String manageOrgName;
	public String getEnterOrg() {return checkGet(enterOrg);}
	public void setEnterOrg(String enterOrg) {this.enterOrg = checkSet(enterOrg);}
	public String getOwnership() {return checkGet(ownership);}
	public void setOwnership(String ownership) {this.ownership = checkSet(ownership);}
	public String getCaseNo() {return checkGet(caseNo);}
	public void setCaseNo(String caseNo) {this.caseNo = checkSet(caseNo);}
	public String getPropertyNo() {return checkGet(propertyNo);}
	public void setPropertyNo(String propertyNo) {this.propertyNo = checkSet(propertyNo);}
	public String getSerialNo() {return checkGet(serialNo);}
	public void setSerialNo(String serialNo) {this.serialNo = checkSet(serialNo);}
	public String getProperty_AddName() {return checkGet(property_AddName);}
	public void setProperty_AddName(String property_AddName) {this.property_AddName = checkSet(property_AddName);}
	public String getPropertyName1() {return checkGet(propertyName1);}
	public void setPropertyName1(String propertyName1) {this.propertyName1 = checkSet(propertyName1);}	
	public String getSignNo() {return checkGet(signNo);}
	public void setSignNo(String signNo) {this.signNo = checkSet(signNo);}	
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
	public String getDoorplate() {return checkGet(doorplate);}
	public void setDoorplate(String doorplate) {this.doorplate = checkSet(doorplate);}
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
	public String getOriginalArea() {return checkGet(originalArea);}
	public void setOriginalArea(String originalArea) {this.originalArea = checkSet(originalArea);}
	public String getOriginalHoldNume() {return checkGet(originalHoldNume);}
	public void setOriginalHoldNume(String originalHoldNume) {this.originalHoldNume = checkSet(originalHoldNume);}
	public String getOriginalHoldDeno() {return checkGet(originalHoldDeno);}
	public void setOriginalHoldDeno(String originalHoldDeno) {this.originalHoldDeno = checkSet(originalHoldDeno);}
	public String getOriginalHoldArea() {return checkGet(originalHoldArea);}
	public void setOriginalHoldArea(String originalHoldArea) {this.originalHoldArea = checkSet(originalHoldArea);}
	public String getArea() {return checkGet(area);}
	public void setArea(String area) {this.area = checkSet(area);}
	public String getHoldNume() {return checkGet(holdNume);}
	public void setHoldNume(String holdNume) {this.holdNume = checkSet(holdNume);}
	public String getHoldDeno() {return checkGet(holdDeno);}
	public void setHoldDeno(String holdDeno) {this.holdDeno = checkSet(holdDeno);}
	public String getHoldArea() {return checkGet(holdArea);}
	public void setHoldArea(String holdArea) {this.holdArea = checkSet(holdArea);}
	public String getOriginalBasis() {return checkGet(originalBasis);}
	public void setOriginalBasis(String originalBasis) {this.originalBasis = checkSet(originalBasis);}
	public String getOriginalDate() {return checkGet(originalDate);}
	public void setOriginalDate(String originalDate) {this.originalDate = checkSet(originalDate);}
	public String getOriginalUnit() {return checkGet(originalUnit);}
	public void setOriginalUnit(String originalUnit) {this.originalUnit = checkSet(originalUnit);}
	public String getOriginalBV() {return checkGet(originalBV);}
	public void setOriginalBV(String originalBV) {this.originalBV = checkSet(originalBV);}
	public String getOriginalNote() {return checkGet(originalNote);}
	public void setOriginalNote(String originalNote) {this.originalNote = checkSet(originalNote);}
	public String getAccountingTitle() {return checkGet(accountingTitle);}
	public void setAccountingTitle(String accountingTitle) {this.accountingTitle = checkSet(accountingTitle);}
	public String getBookUnit() {return checkGet(bookUnit);}
	public void setBookUnit(String bookUnit) {this.bookUnit = checkSet(bookUnit);}
	public String getBookValue() {return checkGet(bookValue);}
	public void setBookValue(String bookValue) {this.bookValue = checkSet(bookValue);}
	public String getFundsSource() {return checkGet(fundsSource);}
	public void setFundsSource(String fundsSource) {this.fundsSource = checkSet(fundsSource);}
	public String getFundsSource1() {return checkGet(fundsSource1);}
	public void setFundsSource1(String fundsSource1) {this.fundsSource1 = checkSet(fundsSource1);}
	public String getUseSeparate() {return checkGet(useSeparate);}
	public void setUseSeparate(String useSeparate) {this.useSeparate = checkSet(useSeparate);}
	public String getUseKind() {return checkGet(useKind);}
	public void setUseKind(String useKind) {this.useKind = checkSet(useKind);}
	public String getOriUseSeparate() {return checkGet(oriUseSeparate);}
	public void setOriUseSeparate(String oriUseSeparate) {this.oriUseSeparate = checkSet(oriUseSeparate);}
	public String getOriUseKind() {return checkGet(oriUseKind);}
	public void setOriUseKind(String oriUseKind) {this.oriUseKind = checkSet(oriUseKind);}
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
	public String getField() {return checkGet(field);}
	public void setField(String field) {this.field = checkSet(field);}
	public String getLandRule() {return checkGet(landRule);}
	public void setLandRule(String landRule) {this.landRule = checkSet(landRule);}
	public String getSourceKind() {return checkGet(sourceKind);}
	public void setSourceKind(String sourceKind) {this.sourceKind = checkSet(sourceKind);}
	public String getSourceDate() {return checkGet(sourceDate);}
	public void setSourceDate(String sourceDate) {this.sourceDate = checkSet(sourceDate);}
	public String getSourceDoc() {return checkGet(sourceDoc);}
	public void setSourceDoc(String sourceDoc) {this.sourceDoc = checkSet(sourceDoc);}
	public String getOldOwner() {return checkGet(oldOwner);}
	public void setOldOwner(String oldOwner) {this.oldOwner = checkSet(oldOwner);}
	public String getManageOrg() {return checkGet(manageOrg);}
	public void setManageOrg(String manageOrg) {this.manageOrg = checkSet(manageOrg);}
	public String getUseState() {return checkGet(useState);}
	public void setUseState(String useState) {this.useState = checkSet(useState);}
	public String getAppraiseDate() {return checkGet(appraiseDate);}
	public void setAppraiseDate(String appraiseDate) {this.appraiseDate = checkSet(appraiseDate);}
	public String getNotes1() {return checkGet(notes1);}
	public void setNotes1(String notes1) {this.notes1 = checkSet(notes1);}
	public String getOldPropertyNo() {return checkGet(oldPropertyNo);}
	public void setOldPropertyNo(String oldPropertyNo) {this.oldPropertyNo = checkSet(oldPropertyNo);}
	public String getOldSerialNo() {return checkGet(oldSerialNo);}
	public void setOldSerialNo(String oldSerialNo) {this.oldSerialNo = checkSet(oldSerialNo);}
	public String getUseState1() {return checkGet(useState1);}
	public void setUseState1(String useState1) {this.useState1 = checkSet(useState1);}
	public String getMergeDivision() {return checkGet(mergeDivision);}
	public void setMergeDivision(String mergeDivision) {this.mergeDivision = checkSet(mergeDivision);}
	public String getMergeDivisionBatch() {return checkGet(mergeDivisionBatch);}
	public void setMergeDivisionBatch(String mergeDivisionBatch) {this.mergeDivisionBatch = checkSet(mergeDivisionBatch);}
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
	public String getManageOrgName() {return checkGet(manageOrgName);}
	public void setManageOrgName(String manageOrgName) {this.manageOrgName = checkSet(manageOrgName);}
	
	private String differenceKind;
	private String caseSerialNo;
	private String engineeringNo;
	private String engineeringNoName;
	private String buyDate;
	private String netUnit;
	private String netValue;
	private String keepUnit;
	private String keeper;
	private String useUnit;
	private String userNo;
	private String userNote;
	private String place1;
	private String place1Name;
	private String place;
	private String originalKeepUnit;
	private String originalKeeper;
	private String originalUseUnit;
	private String originalUser;
	private String originalUserNote;
	private String originalPlace1;
	private String originalPlace1Name;
	private String originalPlace;
	public String getDifferenceKind() {return checkGet(differenceKind);}
	public void setDifferenceKind(String differenceKind) {this.differenceKind = checkSet(differenceKind);}
	public String getCaseSerialNo() {return checkGet(caseSerialNo);}
	public void setCaseSerialNo(String caseSerialNo) {this.caseSerialNo = checkSet(caseSerialNo);}
	public String getEngineeringNo() {return checkGet(engineeringNo);}
	public void setEngineeringNo(String engineeringNo) {this.engineeringNo = checkSet(engineeringNo);}
	public String getEngineeringNoName() {return checkGet(engineeringNoName);}
	public void setEngineeringNoName(String engineeringNoName) {this.engineeringNoName = checkSet(engineeringNoName);}
	public String getBuyDate() {return checkGet(buyDate);}
	public void setBuyDate(String buyDate) {this.buyDate = checkSet(buyDate);}
	public String getNetUnit() {return checkGet(netUnit);}
	public void setNetUnit(String netUnit) {this.netUnit = checkSet(netUnit);}
	public String getNetValue() {return checkGet(netValue);}
	public void setNetValue(String netValue) {this.netValue = checkSet(netValue);}
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
	public String getPlace1Name() {return checkGet(place1Name);}
	public void setPlace1Name(String place1Name) {this.place1Name = checkSet(place1Name);}
	public String getPlace() {return checkGet(place);}
	public void setPlace(String place) {this.place = checkSet(place);}
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
	public String getOriginalPlace1Name() {return checkGet(originalPlace1Name);}
	public void setOriginalPlace1Name(String originalPlace1Name) {this.originalPlace1Name = checkSet(originalPlace1Name);}
	public String getOriginalPlace() {return checkGet(originalPlace);}
	public void setOriginalPlace(String originalPlace) {this.originalPlace = checkSet(originalPlace);}
	
	
	public void setSQLBeanValue(){
		UNTCH_Tools ut = new UNTCH_Tools();
		
		sqlbean.setTableName("UNTLA_Land");
		
		sqlbean.primarykeyMap.clear();
		sqlbean.columnMap.clear();
		
		sqlbean.primarykeyMap.put("enterOrg", 		this.getEnterOrg());
		sqlbean.primarykeyMap.put("ownership", 		this.getOwnership());
		sqlbean.primarykeyMap.put("caseNo",			this.getCaseNo());
		sqlbean.primarykeyMap.put("differenceKind",	this.getDifferenceKind());
		sqlbean.primarykeyMap.put("propertyNo",		this.getPropertyNo());
		sqlbean.primarykeyMap.put("serialNo",		this.getSerialNo());
		
		sqlbean.columnMap.put("enterOrg",					this.getEnterOrg());
		sqlbean.columnMap.put("ownership",					this.getOwnership());
		sqlbean.columnMap.put("caseNo",						this.getCaseNo());
		sqlbean.columnMap.put("propertyNo",					this.getPropertyNo());
		sqlbean.columnMap.put("serialNo",					this.getSerialNo());
		sqlbean.columnMap.put("propertyName1",				this.getPropertyName1());
		sqlbean.columnMap.put("signNo",						this.getSignNo());
		sqlbean.columnMap.put("doorplate",					this.getDoorplate());
		sqlbean.columnMap.put("cause",						this.getCause());
		sqlbean.columnMap.put("cause1",						this.getCause1());
		sqlbean.columnMap.put("enterDate",					ut._transToCE_Year(this.getEnterDate()));
		sqlbean.columnMap.put("dataState",					this.getDataState());
		sqlbean.columnMap.put("reduceDate",					ut._transToCE_Year(this.getReduceDate()));
		sqlbean.columnMap.put("reduceCause",				this.getReduceCause());
		sqlbean.columnMap.put("reduceCause1",				this.getReduceCause1());
		sqlbean.columnMap.put("verify",						this.getVerify());
		sqlbean.columnMap.put("propertyKind",				this.getPropertyKind());
		sqlbean.columnMap.put("fundType",					this.getFundType());
		sqlbean.columnMap.put("valuable",					this.getValuable());
		sqlbean.columnMap.put("taxCredit",					this.getTaxCredit());
		sqlbean.columnMap.put("originalArea",				this.getOriginalArea());
		sqlbean.columnMap.put("originalHoldNume",			this.getOriginalHoldNume());
		sqlbean.columnMap.put("originalHoldDeno",			this.getOriginalHoldDeno());
		sqlbean.columnMap.put("originalHoldArea",			this.getOriginalHoldArea());
		sqlbean.columnMap.put("area",						this.getArea());
		sqlbean.columnMap.put("holdNume",					this.getHoldNume());
		sqlbean.columnMap.put("holdDeno",					this.getHoldDeno());
		sqlbean.columnMap.put("holdArea",					this.getHoldArea());
		sqlbean.columnMap.put("originalBasis",				this.getOriginalBasis());
		sqlbean.columnMap.put("originalDate",				transToCE_Year(this.getOriginalDate(),5));
		sqlbean.columnMap.put("originalUnit",				this.getOriginalUnit());
		sqlbean.columnMap.put("originalBV",					this.getOriginalBV());
		sqlbean.columnMap.put("originalNote",				this.getOriginalNote());
		sqlbean.columnMap.put("accountingTitle",			this.getAccountingTitle());
		sqlbean.columnMap.put("bookUnit",					this.getBookUnit());
		sqlbean.columnMap.put("bookValue",					this.getBookValue());
		sqlbean.columnMap.put("netUnit",					this.getNetUnit());
		sqlbean.columnMap.put("netValue",					this.getNetValue());
		sqlbean.columnMap.put("fundsSource",				this.getFundsSource());
		sqlbean.columnMap.put("fundsSource1",				this.getFundsSource1());
		sqlbean.columnMap.put("useSeparate",				this.getUseSeparate());
		sqlbean.columnMap.put("useKind",					this.getUseKind());
		sqlbean.columnMap.put("oriUseSeparate",				this.getOriUseSeparate());
		sqlbean.columnMap.put("oriUseKind",					this.getOriUseKind());
		sqlbean.columnMap.put("ownershipDate",				ut._transToCE_Year(this.getOwnershipDate()));
		sqlbean.columnMap.put("ownershipCause",				this.getOwnershipCause());
		sqlbean.columnMap.put("nonProof",					this.getNonProof());
		sqlbean.columnMap.put("proofDoc",					this.getProofDoc());
		sqlbean.columnMap.put("ownershipNote",				this.getOwnershipNote());
		sqlbean.columnMap.put("field",						this.getField());
		sqlbean.columnMap.put("landRule",					this.getLandRule());
		sqlbean.columnMap.put("sourceKind",					this.getSourceKind());
		sqlbean.columnMap.put("sourceDate",					ut._transToCE_Year(this.getSourceDate()));
		sqlbean.columnMap.put("sourceDoc",					this.getSourceDoc());
		sqlbean.columnMap.put("oldOwner",					this.getOldOwner());
		sqlbean.columnMap.put("manageOrg",					this.getManageOrg());
		sqlbean.columnMap.put("useState",					this.getUseState());
		sqlbean.columnMap.put("appraiseDate",				ut._transToCE_Year(this.getAppraiseDate()));
		sqlbean.columnMap.put("notes1",						this.getNotes1());
		sqlbean.columnMap.put("oldPropertyNo",				this.getOldPropertyNo());
		sqlbean.columnMap.put("oldSerialNo",				this.getOldSerialNo());
		sqlbean.columnMap.put("useState1",					this.getUseState1());
		sqlbean.columnMap.put("mergeDivision",				this.getCaseNo_Merge());
		sqlbean.columnMap.put("mergeDivisionBatch",			this.getMergeDivisionBatch());
		sqlbean.columnMap.put("notes",						this.getNotes());
		sqlbean.columnMap.put("editID",						this.getEditID());
		sqlbean.columnMap.put("editDate",					ut._transToCE_Year(this.getEditDate()));
		sqlbean.columnMap.put("editTime",					this.getEditTime());	
		sqlbean.columnMap.put("differenceKind",				this.getDifferenceKind());
		sqlbean.columnMap.put("caseSerialNo",				this.getCaseSerialNo());		
		sqlbean.columnMap.put("engineeringNo",				this.getEngineeringNo());		
		sqlbean.columnMap.put("buyDate",					ut._transToCE_Year(this.getBuyDate()));		
		sqlbean.columnMap.put("keepUnit",					this.getKeepUnit());
		sqlbean.columnMap.put("keeper",						this.getKeeper());
		sqlbean.columnMap.put("useUnit",					this.getUseUnit());
		sqlbean.columnMap.put("userNo",						this.getUserNo());
		sqlbean.columnMap.put("userNote",					this.getUserNote());
		sqlbean.columnMap.put("place1",						this.getPlace1());
		sqlbean.columnMap.put("place1Name",					this.getPlace1Name());
		sqlbean.columnMap.put("place",						this.getPlace());
		sqlbean.columnMap.put("OriginalKeepUnit",			this.getOriginalKeepUnit());
		sqlbean.columnMap.put("OriginalKeeper",				this.getOriginalKeeper());
		sqlbean.columnMap.put("OriginalUseUnit",			this.getOriginalUseUnit());
		sqlbean.columnMap.put("OriginalUser",				this.getOriginalUser());
		sqlbean.columnMap.put("OriginalUserNote",			this.getOriginalUserNote());
		sqlbean.columnMap.put("OriginalPlace1",				this.getOriginalPlace1());
		sqlbean.columnMap.put("OriginalPlace",				this.getOriginalPlace());
	}

	public UNTLA054Q_data getParameterData(){
		qbean.tableName="UNTLA_Land";
		qbean.caseNo=this.getCaseNo_Add();
		qbean.enterOrg=this.getEnterOrg();
		qbean.ownership=this.getOwnership();
		qbean.differenceKind=this.getDifferenceKind();
		qbean.propertyNo=this.getPropertyNo_Add();
		qbean.serialNo=this.getSerialNo_Add();
		return qbean;
	}

	//清除畫面所有欄位資料用
	public void clearAllDataForView(){
		this.setProperty_AddName("");
		this.setPropertyName1("");
		this.setSignNo("");
		this.setSignNo1("");
		this.setSignNo2("");
		this.setSignNo3("");
		this.setSignNo4("");
		this.setSignNo5("");
		this.setDoorplate("");
		this.setCause("");
		this.setCause1("");
		this.setEnterDate("");
		this.setDataState("");
		this.setReduceDate("");
		this.setReduceCause("");
		this.setReduceCause1("");
		this.setVerify("");
		this.setPropertyKind("");
		this.setFundType("");
		this.setValuable("");
		this.setTaxCredit("");
		this.setOriginalArea("");
		this.setOriginalHoldNume("");
		this.setOriginalHoldDeno("");
		this.setOriginalHoldArea("");
		this.setArea("");
		this.setHoldNume("");
		this.setHoldDeno("");
		this.setHoldArea("");
		this.setOriginalBasis("");
		this.setOriginalDate("");
		this.setOriginalUnit("");
		this.setOriginalBV("");
		this.setOriginalNote("");
		this.setAccountingTitle("");
		this.setBookUnit("");
		this.setBookValue("");
		this.setNetUnit("");
		this.setNetValue("");
		this.setFundsSource("");
		this.setFundsSource1("");
		this.setUseSeparate("");
		this.setUseKind("");
		this.setOriUseSeparate("");
		this.setOriUseKind("");
		this.setOwnershipDate("");
		this.setOwnershipCause("");
		this.setNonProof("");
		this.setProofDoc("");
		this.setOwnershipNote("");
		this.setField("");
		this.setLandRule("");
		this.setSourceKind("");
		this.setSourceDate("");
		this.setSourceDoc("");
		this.setOldOwner("");
		this.setManageOrg("");
		this.setUseState("");
		this.setAppraiseDate("");
		this.setNotes1("");
		this.setOldPropertyNo("");
		this.setOldSerialNo("");
		this.setUseState1("");
		this.setMergeDivision("");
		this.setMergeDivisionBatch("");
		this.setNotes("");
		this.setEditID("");
		this.setEditDate("");
		this.setEditTime("");
		this.setCaseSerialNo("");
		this.setOriginalKeepUnit("");
		this.setOriginalKeeper("");
		this.setOriginalUseUnit("");
		this.setOriginalUser("");
		this.setOriginalUserNote("");
		this.setOriginalPlace1("");
		this.setOriginalPlace1Name("");
		this.setOriginalPlace("");
		this.setKeepUnit("");
		this.setKeeper("");
		this.setUseUnit("");
		this.setUserNo("");
		this.setUserNote("");
		this.setPlace1("");
		this.setPlace1Name("");
		this.setPlace("");
	}
	
	//儲存切換不同頁面時所需要的訊息
	public void setTransData_MainToTran(){
		this.setCaseNo(this.getCaseNo_Add());
		this.setPropertyNo(this.getPropertyNo_Add());
		this.setSerialNo(this.getSerialNo_Add());
	}
	
	//儲存切換不同頁面時所需要的訊息
	public void setTransData_TranToMain(){
		this.setCaseNo_Add(this.getCaseNo());
		this.setPropertyNo_Add(this.getPropertyNo());
		this.setSerialNo_Add(this.getSerialNo());
	}

//傳回執行insert前之檢查sql
protected String[][] getInsertCheckSQL(){	
	String[][] checkSQLArray = new String[1][4];
	
	UNTCH_Tools ut = new UNTCH_Tools();
	
	setTransData_MainToTran();
	
	checkSQLArray[0][0]=" select case " + Common.sqlChar(this.getOriginalBasis()) +
							" when '1' then count(*)" +
							" when '2' then count(*)" +
							" else 1" +
							" end as checkResult from UNTLA_BulletinDate where 1=1" + 
		" and bulletinDate = " + Common.sqlChar(transToCE_Year(this.getOriginalDate(),5)) + 
		" and bulletinKind =  " + Common.sqlChar(ut._transToCE_Year(this.getOriginalBasis()) )+
		"";
	checkSQLArray[0][1]="=";
	checkSQLArray[0][2]="0";
	checkSQLArray[0][3]="公告年月錯誤，請重新輸入！！";
	
	LogBean.outputLogDebug(checkSQLArray[0][0]);
	
	return checkSQLArray;
}
	
//傳回insert sql
protected String[] getInsertSQL(){
	String[] execSQLArray = new String[1];
	
    //取得新caseNo
    setSerialNo(getNewSerialNoFromDB(getParameterData()));
    
    setTransData_TranToMain();
    
	setSQLBeanValue();
	execSQLArray[0]=sqlbean.getSQLMethod_Insert(sqlbean);	
	LogBean.outputLogDebug(execSQLArray[0]);
	
	extend_getInsertSQL();
	
	return execSQLArray;
}


//傳回執行update前之檢查sql
protected String[][] getUpdateCheckSQL(){
	String[][] checkSQLArray = new String[1][4];
	
	UNTCH_Tools ut = new UNTCH_Tools();
	
	setTransData_MainToTran();
	
	checkSQLArray[0][0]=" select case " + Common.sqlChar(this.getOriginalBasis()) +
								" when '1' then count(*)" +
								" when '2' then count(*)" +
								" else 1" +
								" end as checkResult from UNTLA_BulletinDate where 1=1" + 
		" and bulletinDate = " + Common.sqlChar(transToCE_Year(this.getOriginalDate(),5)) + 
		" and bulletinKind =  " + Common.sqlChar(this.getOriginalBasis()) +
		"";
	checkSQLArray[0][1]="=";
	checkSQLArray[0][2]="0";
	checkSQLArray[0][3]="公告年月錯誤，請重新輸入！！";
	
	LogBean.outputLogDebug(checkSQLArray[0][0]);
	
	extend_getUpdateSQL();
	
	return checkSQLArray;
}

//傳回update sql
protected String[] getUpdateSQL(){
	String[] execSQLArray = new String[1];
	
	setSQLBeanValue();
	execSQLArray[0]=sqlbean.getSQLMethod_Update(sqlbean);
	LogBean.outputLogDebug(execSQLArray[0]);
	
	return execSQLArray;
}


//傳回delete sql
protected String[] getDeleteSQL(){
	String[] execSQLArray = new String[5];
	
	setTransData_MainToTran();	
	
	execSQLArray[0]=getDeleteSQL_UNTLA_Manage();
	execSQLArray[1]=getDeleteSQL_UNTLA_Attachment();	
	execSQLArray[2]=getDeleteSQL_UNTLA_Value();
	execSQLArray[3]=getUpdatefor_UNTLA_ReduceDetail();
	setSQLBeanValue();
	execSQLArray[4]=sqlbean.getSQLMethod_Delete(sqlbean);
	
	LogBean.outputLogDebug("execSQLArray[0] : "+execSQLArray[0]+"\n"+
							"execSQLArray[1] : "+execSQLArray[1]+"\n"+
							"execSQLArray[2] : "+execSQLArray[2]+"\n"+
							"execSQLArray[3] : "+execSQLArray[3]+"\n"+
							"execSQLArray[4] : "+execSQLArray[4]+"\n");
	
	extend_getDeleteSQL();
	
	return execSQLArray;
}
		
	private String getDeleteSQL_UNTLA_Manage(){
		String sql="DELETE FROM UNTLA_Manage" +						
					" WHERE 1=1" +
					" AND ENTERORG = " + Common.sqlChar(this.getEnterOrg())+
					" AND OWNERSHIP = " + Common.sqlChar(this.getOwnership())+
					" AND differenceKind = " + Common.sqlChar(this.getDifferenceKind())+
					" AND PROPERTYNO IN " + 
					" ("+
						" SELECT PROPERTYNO FROM UNTLA_LAND"+
						" WHERE 1=1"+
						" AND CASENO = " + Common.sqlChar(this.getCaseNo_Add())+
						" AND ENTERORG = " + Common.sqlChar(this.getEnterOrg())+
						" AND OWNERSHIP = " + Common.sqlChar(this.getOwnership())+
						" AND differenceKind = " + Common.sqlChar(this.getDifferenceKind())+
						" AND PROPERTYNO = " + Common.sqlChar(this.getPropertyNo())+
						" AND SERIALNO = " + Common.sqlChar(this.getSerialNo())+
					")" +
					" AND SERIALNO IN " + 
					" ("+
						" SELECT SERIALNO FROM UNTLA_LAND"+
						" WHERE 1=1"+
						" AND CASENO = " + Common.sqlChar(this.getCaseNo_Add())+
						" AND ENTERORG = " + Common.sqlChar(this.getEnterOrg())+
						" AND OWNERSHIP = " + Common.sqlChar(this.getOwnership())+
						" AND differenceKind = " + Common.sqlChar(this.getDifferenceKind())+
						" AND PROPERTYNO = " + Common.sqlChar(this.getPropertyNo())+
						" AND SERIALNO = " + Common.sqlChar(this.getSerialNo())+
					")";
		return sql;	
	}
	
	private String getDeleteSQL_UNTLA_Attachment(){
		String sql="DELETE FROM UNTLA_Attachment" +						
					" WHERE 1=1" +
					" AND ENTERORG = " + Common.sqlChar(this.getEnterOrg())+
					" AND OWNERSHIP = " + Common.sqlChar(this.getOwnership())+
					" AND differenceKind = " + Common.sqlChar(this.getDifferenceKind())+
					" AND PROPERTYNO IN " + 
					" ("+
						" SELECT PROPERTYNO FROM UNTLA_LAND"+
						" WHERE 1=1"+
						" AND CASENO = " + Common.sqlChar(this.getCaseNo_Add())+
						" AND ENTERORG = " + Common.sqlChar(this.getEnterOrg())+
						" AND OWNERSHIP = " + Common.sqlChar(this.getOwnership())+
						" AND differenceKind = " + Common.sqlChar(this.getDifferenceKind())+
						" AND PROPERTYNO = " + Common.sqlChar(this.getPropertyNo())+
						" AND SERIALNO = " + Common.sqlChar(this.getSerialNo())+
					")" +
					" AND SERIALNO IN " + 
					" ("+
						" SELECT SERIALNO FROM UNTLA_LAND"+
						" WHERE 1=1"+
						" AND CASENO = " + Common.sqlChar(this.getCaseNo_Add())+
						" AND ENTERORG = " + Common.sqlChar(this.getEnterOrg())+
						" AND OWNERSHIP = " + Common.sqlChar(this.getOwnership())+
						" AND differenceKind = " + Common.sqlChar(this.getDifferenceKind())+
						" AND PROPERTYNO = " + Common.sqlChar(this.getPropertyNo())+
						" AND SERIALNO = " + Common.sqlChar(this.getSerialNo())+
					")";
		return sql;	
	}

	private String getDeleteSQL_UNTLA_Value(){
		String sql="DELETE FROM UNTLA_Value" +						
					" WHERE 1=1" +
					" AND ENTERORG = " + Common.sqlChar(this.getEnterOrg())+
					" AND OWNERSHIP = " + Common.sqlChar(this.getOwnership())+
					" AND differenceKind = " + Common.sqlChar(this.getDifferenceKind())+
					" AND PROPERTYNO IN " + 
					" ("+
						" SELECT PROPERTYNO FROM UNTLA_LAND"+
						" WHERE 1=1"+
						" AND CASENO = " + Common.sqlChar(this.getCaseNo_Add())+
						" AND ENTERORG = " + Common.sqlChar(this.getEnterOrg())+
						" AND OWNERSHIP = " + Common.sqlChar(this.getOwnership())+
						" AND differenceKind = " + Common.sqlChar(this.getDifferenceKind())+
						" AND PROPERTYNO = " + Common.sqlChar(this.getPropertyNo())+
						" AND SERIALNO = " + Common.sqlChar(this.getSerialNo())+
					")" +
					" AND SERIALNO IN " + 
					" ("+
						" SELECT SERIALNO FROM UNTLA_LAND"+
						" WHERE 1=1"+
						" AND CASENO = " + Common.sqlChar(this.getCaseNo_Add())+
						" AND ENTERORG = " + Common.sqlChar(this.getEnterOrg())+
						" AND OWNERSHIP = " + Common.sqlChar(this.getOwnership())+
						" AND differenceKind = " + Common.sqlChar(this.getDifferenceKind())+
						" AND PROPERTYNO = " + Common.sqlChar(this.getPropertyNo())+
						" AND SERIALNO = " + Common.sqlChar(this.getSerialNo())+
					")";
		return sql;	
	}

	private String getUpdatefor_UNTLA_ReduceDetail(){
		String sql="Update UNTLA_REDUCEDETAIL set" +	
						" mergedivisionbatch = null" +
					" WHERE 1=1" +
						" AND CASENO = " + Common.sqlChar(this.getCaseNo_Reduce())+
						" AND ENTERORG = " + Common.sqlChar(this.getEnterOrg())+
						" AND OWNERSHIP = " + Common.sqlChar(this.getOwnership())+
						" AND differenceKind = " + Common.sqlChar(this.getDifferenceKind());
					
		return sql;	
	}
	
//依主鍵查詢單一資料
public UNTLA058F  queryOne() throws Exception{
	Database db = new Database();
	UNTLA058F obj = this;
	String sql="select (select placename from SYSCA_PLACE z where z.enterorg = a.enterorg and z.placeno = a.place1) as place1Name ," +
					"a.* from UNTLA_Land a where 1=1"+
					" and enterorg = "+ Common.sqlChar(getEnterOrg())+
					" and ownership = "+ Common.sqlChar(getOwnership())+
					" and caseno = "+ Common.sqlChar(getCaseNo_Add())+
					" and differenceKind = " + Common.sqlChar(this.getDifferenceKind())+
					" and propertyno = "+ Common.sqlChar(getPropertyNo())+
					" and serialno = "+ Common.sqlChar(getSerialNo());

	try {
		LogBean.outputLogDebug(sql);
		UNTCH_Tools ut = new UNTCH_Tools();
		ResultSet rs = db.querySQL(sql);
		if (rs.next()){
			obj.setEnterOrg(rs.getString("enterOrg"));
			obj.setOwnership(rs.getString("ownership"));
			obj.setCaseNo(rs.getString("caseNo"));
			obj.setCaseNo_Add(rs.getString("caseNo"));
			obj.setDifferenceKind(rs.getString("differenceKind"));
			obj.setPropertyNo(rs.getString("propertyNo"));
			obj.setPropertyNo_Add(rs.getString("propertyNo"));
			obj.setSerialNo(rs.getString("serialNo"));
			obj.setSerialNo_Add(rs.getString("serialNo"));
			obj.setPropertyName1(rs.getString("propertyName1"));
			obj.setSignNo(rs.getString("signNo"));
			obj.setDoorplate(rs.getString("doorplate"));
			obj.setCause(rs.getString("cause"));
			obj.setCause1(rs.getString("cause1"));
			obj.setEnterDate(ut._transToROC_Year(rs.getString("enterDate")));
			obj.setDataState(rs.getString("dataState"));
			obj.setReduceDate(ut._transToROC_Year(rs.getString("reduceDate")));
			obj.setReduceCause(rs.getString("reduceCause"));
			obj.setReduceCause1(rs.getString("reduceCause1"));		
			obj.setVerify(rs.getString("verify"));
			obj.setPropertyKind(rs.getString("propertyKind"));
			obj.setFundType(rs.getString("fundType"));
			obj.setValuable(rs.getString("valuable"));
			obj.setTaxCredit(rs.getString("taxCredit"));
			obj.setOriginalArea(rs.getString("originalArea"));
			obj.setOriginalHoldNume(rs.getString("originalHoldNume"));
			obj.setOriginalHoldDeno(rs.getString("originalHoldDeno"));
			obj.setOriginalHoldArea(rs.getString("originalHoldArea"));
			obj.setArea(rs.getString("area"));
			obj.setHoldNume(rs.getString("holdNume"));
			obj.setHoldDeno(rs.getString("holdDeno"));
			obj.setHoldArea(rs.getString("holdArea"));
			obj.setOriginalBasis(rs.getString("originalBasis"));
			obj.setOriginalDate(transToYYYMM(rs.getString("originalDate"),6));
			obj.setOriginalUnit(rs.getString("originalUnit"));
			obj.setOriginalBV(rs.getString("originalBV"));
			obj.setOriginalNote(rs.getString("originalNote"));
			obj.setAccountingTitle(rs.getString("accountingTitle"));
			obj.setBookUnit(rs.getString("bookUnit"));
			obj.setBookValue(rs.getString("bookValue"));
			obj.setNetUnit(rs.getString("netUnit"));
			obj.setNetValue(rs.getString("netValue"));
			obj.setFundsSource(rs.getString("fundsSource"));
			obj.setFundsSource1(rs.getString("fundsSource1"));
			obj.setUseSeparate(rs.getString("useSeparate"));
			obj.setUseKind(rs.getString("useKind"));
			obj.setOriUseSeparate(rs.getString("oriUseSeparate"));
			obj.setOriUseKind(rs.getString("oriUseKind"));
			obj.setOwnershipDate(ut._transToROC_Year(rs.getString("ownershipDate")));
			obj.setOwnershipCause(rs.getString("ownershipCause"));
			obj.setNonProof(rs.getString("nonProof"));
			obj.setProofDoc(rs.getString("proofDoc"));
			obj.setOwnershipNote(rs.getString("ownershipNote"));
			obj.setField(rs.getString("field"));
			obj.setLandRule(rs.getString("landRule"));
			obj.setSourceKind(rs.getString("sourceKind"));
			obj.setSourceDate(ut._transToROC_Year(rs.getString("sourceDate")));
			obj.setSourceDoc(rs.getString("sourceDoc"));
			obj.setOldOwner(rs.getString("oldOwner"));
			obj.setManageOrg(rs.getString("manageOrg"));
			obj.setUseState(rs.getString("useState"));
			obj.setAppraiseDate(ut._transToROC_Year(rs.getString("appraiseDate")));
			obj.setNotes1(rs.getString("notes1"));
			obj.setOldPropertyNo(rs.getString("oldPropertyNo"));
			obj.setOldSerialNo(rs.getString("oldSerialNo"));
			obj.setUseState1(rs.getString("useState1"));
			obj.setMergeDivision(rs.getString("mergeDivision"));
			obj.setMergeDivisionBatch(rs.getString("mergeDivisionBatch"));
			obj.setNotes(rs.getString("notes"));
			obj.setEditID(rs.getString("editID"));
			obj.setEditDate(ut._transToROC_Year(rs.getString("editDate")));
			obj.setEditTime(rs.getString("editTime"));
			
			obj.setEnterOrgName(getEnterOrgNameFromDB(getParameterData()));
			obj.setProperty_AddName(getPropertyNoNameFromDB(getParameterData()));
			obj.setManageOrgName(getEnterOrgNameFromDB(getParameterData()));

			if((!"".equals(obj.getSignNo())) && (obj.getSignNo().length() == 15)){
				obj.setSignNo1(obj.getSignNo().substring(0,1)+"000000");
				obj.setSignNo2(obj.getSignNo().substring(0,3)+"0000");
				obj.setSignNo3(obj.getSignNo().substring(0,7));
				obj.setSignNo4(obj.getSignNo().substring(7,11));
				obj.setSignNo5(obj.getSignNo().substring(11));
			}
			
			obj.setCaseSerialNo(rs.getString("caseSerialNo"));
			obj.setBuyDate(ut._transToROC_Year(rs.getString("buyDate")));
			obj.setKeepUnit(rs.getString("KeepUnit"));
			obj.setKeeper(rs.getString("Keeper"));
			obj.setUseUnit(rs.getString("UseUnit"));
			obj.setUserNo(rs.getString("UserNo"));
			obj.setUserNote(rs.getString("UserNote"));
			obj.setPlace1(rs.getString("Place1"));
			obj.setPlace(rs.getString("Place"));
			obj.setPlace1Name(rs.getString("place1Name"));
			obj.setOriginalKeepUnit(rs.getString("OriginalKeepUnit"));
			obj.setOriginalKeeper(rs.getString("OriginalKeeper"));
			obj.setOriginalUseUnit(rs.getString("OriginalUseUnit"));
			obj.setOriginalUser(rs.getString("OriginalUser"));
			obj.setOriginalUserNote(rs.getString("OriginalUserNote"));
			obj.setOriginalPlace1(rs.getString("OriginalPlace1"));
			obj.setOriginalPlace(rs.getString("OriginalPlace"));
			
			setTransData_TranToMain();	
		}
		
		setStateQueryOneSuccess();
	} catch (Exception e) {
		LogBean.outputLogError("Exception : "+e.getMessage()+"\n"+sql);
		
	} finally {
		db.closeAll();
	}
	return obj;
}

//依查詢欄位查詢多筆資料
public ArrayList queryAll() throws Exception{
	Database db = new Database();
	ArrayList objList=new ArrayList();
	
	String sql="select "+
					" a.enterorg, a.ownership, a.caseno, a.differenceKind, a.propertyno, a.serialno, a.signno, (case a.dataState when '1' then '現存' when '2' then '已減損' end) as dataState, " +
					" a.originalbv, a.netvalue, a.holdarea," +
					" (select z.organsname from sysca_organ z where 1=1 and z.organID = a.enterOrg) as enterOrgName," +
					" (select z.codeName from sysca_code z where 1=1 and (z.codeKindID = 'owa' or z.codeKindID = 'OWA') and z.codeid = a.ownership) as ownershipName,"+
					" (select z.codeName from SYSCA_Code z where 1=1 and z.codeKindID='CAA' and z.codeid=a.cause) as causeName,"+
					" (select z.codeName from SYSCA_Code z where 1=1 and z.codeKindID='PKD' and z.codeid=a.propertykind) as propertykindName, "+
					" (case a.useState1 when '01' then '空置' when '02' then '建物' when '03' then '農作' when '04' then '其他' end) as useState1"+
				" from UNTLA_Land a where 1=1"+
					" and a.enterorg = " + Common.sqlChar(this.getEnterOrg()) +
					" and a.ownership = " + Common.sqlChar(this.getOwnership()) +
					" and a.caseno = " + Common.sqlChar(this.getCaseNo_Add()) +
					" AND a.differenceKind = " + Common.sqlChar(this.getDifferenceKind());
	
		sql+=" order by enterOrg, ownership, caseNo, differenceKind, propertyNo, serialNo ";

	try {
		LogBean.outputLogDebug(sql);
		StringTools st = new StringTools();
		UNTCH_Tools ut = new UNTCH_Tools();
		ResultSet rs = db.querySQL(sql,true);
		processCurrentPageAttribute(rs);			
		if (getTotalRecord() > 0) {
			int count = getRecordStart();
			int end = getRecordEnd();
			do {
				if (count > end)
					break;
			String rowArray[]=new String[16];
			
			rowArray[0]=Common.get(rs.getString("enterOrg"));
			rowArray[1]=Common.get(rs.getString("enterOrgname"));
			rowArray[2]=Common.get(rs.getString("differenceKind"));
			rowArray[3]=ut._getDifferenceKindName(Common.get(rs.getString("differenceKind")));
			rowArray[4]=Common.get(rs.getString("ownership"));
			rowArray[5]=Common.get(rs.getString("ownershipName"));
			rowArray[6]=Common.get(rs.getString("caseNo"));
			rowArray[7]=Common.get(rs.getString("propertyNo"));
			rowArray[8]=Common.get(rs.getString("serialNo"));
			rowArray[9]=Common.get(rs.getString("signNo")); 
			rowArray[10]=Common.get(getSignDescName(rs.getString("signNo").substring(0,7)) + rs.getString("signNo").substring(7,11) + '-' + rs.getString("signNo").substring(11));
			rowArray[11]=Common.get(rs.getString("causeName"));
			rowArray[12]=Common.get(rs.getString("dataState"));
			rowArray[13]=Common.get(rs.getString("holdarea"));
			rowArray[14]=st._getMoneyFormat(Common.get(rs.getString("originalbv")));
			rowArray[15]=st._getMoneyFormat(Common.get(rs.getString("netvalue")));
			
			
			objList.add(rowArray);
			count++;
			} while (rs.next());
		}
		setStateQueryAllSuccess();
	} catch (Exception e) {
		LogBean.outputLogError("Exection : "+e.getMessage()+"\n"+sql);
		
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

	public void queryCause(){
		String sql = "select cause, cause1 from UNTLA_MergeDivision where 1=1" +
						" and enterorg = " + Common.sqlChar(this.getEnterOrg()) +
						" and ownership = " + Common.sqlChar(this.getOwnership()) +
						" and caseno = " + Common.sqlChar(this.getCaseNo_Merge()) +
						" and differenceKind = " + Common.sqlChar(this.getDifferenceKind());
		Database db = null;
		ResultSet rs = null;
		try{
			db = new Database();
			rs = db.querySQL(sql);
			while(rs.next()){
				this.setCause(rs.getString("cause"));
				this.setCause1(rs.getString("cause1"));
			}
		}catch(Exception e){
			System.out.println("queryCause Exception => " + e.getMessage());
		}finally{
			db.closeAll();
		}
	}
	
	private String transToCE_Year(String s, int len){
		String result = "";
		if(Common.get(s).equals("")){
		}else{
			result = String.valueOf(Integer.parseInt(s.substring(0,3)) + 1911) + s.substring(3,len);
		}
		return result;
	}
	
	private String transToYYYMM(String s, int len){
		String result = "";
		if(Common.get(s).equals("")){					
		}else{
			result = Common.formatFrontZero(String.valueOf(Integer.parseInt(s.substring(0,4)) - 1911),3) + s.substring(4,len);
		}
		return result;
	}
	
	//挑檔後，財產區分別會被自動清空，但找不到原因，暫用此解決方案
	public void setParameterValue(){
		String sql = "select differencekind from UNTLA_MERGEDIVISION" +
						" where 1=1" +
						" and enterOrg = " + Common.sqlChar(this.getEnterOrg()) +
						" and ownership = " + Common.sqlChar(this.getOwnership()) +
						" and caseno = " + Common.sqlChar(this.getCaseNo_Merge());
		this.setDifferenceKind(this.getNameData("differencekind", sql));
	}
}


