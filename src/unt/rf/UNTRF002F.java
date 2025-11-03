/*
*<br>程式目的：土地改良物主檔資料維護--基本資料
*<br>程式代號：untrf002f
*<br>程式日期：0940923
*<br>程式作者：clive.chang
*<br>--------------------------------------------------------
*<br>修改作者　　修改日期　　　修改目的
*<br>--------------------------------------------------------
*/

package unt.rf;

import java.sql.ResultSet;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import unt.ch.UNTCH_Tools;
import util.Common;
import util.Database;
import util.Datetime;
import TDlib_Simple.com.src.SQLCreator;

public class UNTRF002F extends UNTRF001F_Q {
	
	/**
	 * 僅提供 useBean , 請勿使用此建構子
	 */
	public UNTRF002F() {
		
	}
	
	public UNTRF002F(String caller) {
		this.setCaller(caller);
	}
	
	/** 是誰透過他來呼叫 執行insert or update**/
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
	public String getOriginalApportionEndYM() {return checkGet(originalApportionEndYM);}
	public void setOriginalApportionEndYM(String originalApportionEndYM) {this.originalApportionEndYM = checkSet(originalApportionEndYM);}	
	private String apportionEndYM;
	public String getApportionEndYM() {return checkGet(apportionEndYM);}
	public void setApportionEndYM(String apportionEndYM) {this.apportionEndYM = checkSet(apportionEndYM);}

	private String enterOrg;
	private String ownership;
	private String caseNo;
	private String differenceKind;
	private String engineeringNo;
	private String caseSerialNo;
	private String propertyNo;
	private String serialNo;
	private String otherLimitYear;
	private String propertyName1;
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
	private String originalMeasure;
	private String measure;
	private String originalBV;
	private String originalNote;
	private String accountingTitle;
	private String bookValue;
	private String netValue;
	private String fundsSource;
	private String fundsSource1;
	private String buildDate;
	private String buyDate;
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
	private String escrowOriValue;
	private String escrowOriAccumDepr;
	private String oldPropertyNo;
	private String oldSerialNo;
	private String notes;
	private String editID;
	private String editDate;
	private String editTime;
	private String picture;
	private String otherPropertyUnit;
	private String otherMaterial;
	
	private String enterOrgname;
	private String engineeringNoName;
	
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
	public String getOriginalMeasure() {return checkGet(originalMeasure);}
	public void setOriginalMeasure(String originalMeasure) {this.originalMeasure = checkSet(originalMeasure);}
	public String getMeasure() {return checkGet(measure);}
	public void setMeasure(String measure) {this.measure = checkSet(measure);}
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
	public String getBuildDate() {return checkGet(buildDate);}
	public void setBuildDate(String buildDate) {this.buildDate = checkSet(buildDate);}
	public String getBuyDate() {return checkGet(buyDate);}
	public void setBuyDate(String buyDate) {this.buyDate = checkSet(buyDate);}
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
	public String getEnterOrgname() {return checkGet(enterOrgname);}
	public void setEnterOrgname(String enterOrgname) {this.enterOrgname = checkSet(enterOrgname);}
	public String getEngineeringNoName() {return checkGet(engineeringNoName);}
	public void setEngineeringNoName(String engineeringNoName) {this.engineeringNoName = checkSet(engineeringNoName);}
	private String propertyNoName;
	public String getPropertyNoName() {return checkGet(propertyNoName);}
	public void setPropertyNoName(String propertyNoName) {this.propertyNoName = checkSet(propertyNoName);}
	private String place1Name;	
	private String originalPlace1Name;
	public String getPlace1Name() {	return checkGet(place1Name);}
	public void setPlace1Name(String place1Name) {this.place1Name = checkSet(place1Name);}
	public String getOriginalPlace1Name() {return checkGet(originalPlace1Name);}
	public void setOriginalPlace1Name(String originalPlace1Name) {this.originalPlace1Name = checkSet(originalPlace1Name);}
	private String propertyType;
	private String propertyUnit;
	private String material;
	public String getPropertyType() {return checkGet(propertyType);}
	public void setPropertyType(String propertyType) {this.propertyType = checkSet(propertyType);}
	public String getPropertyUnit() {return checkGet(propertyUnit);}
	public void setPropertyUnit(String propertyUnit) {this.propertyUnit = checkSet(propertyUnit);}
	public String getMaterial() {return checkGet(material);}
	public void setMaterial(String material) {this.material = checkSet(material);}	
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
		map.put("SerialNo", getSerialNo());
		
		return map;
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private Map getRecordMap(String action){
		
		String caller = this.getCaller();
		// UNTCH001F02_2
		
		Map map = new HashMap();
		UNTCH_Tools ut = new UNTCH_Tools();
		
		if (!"UNTCH001F02_2".equals(caller)) { // 財產維護 不給異動, 會導致這些欄位被清空
			long originalAccumDepr = Common.getLong(this.getOriginalAccumDepr());
			long netValue = Common.getLong(this.getNetValue());
			
			map.put("enterDate", ut._transToCE_Year(getEnterDate()));
			map.put("originalMoveDate", ut._transToCE_Year(getOriginalMoveDate()));
			map.put("originalKeepUnit", getOriginalKeepUnit());
			map.put("originalKeeper", getOriginalKeeper());
			map.put("originalUseUnit", getOriginalUseUnit());
			map.put("originalUser", getOriginalUser());
			map.put("originalUserNote", getOriginalUserNote());
			map.put("originalPlace1", getOriginalPlace1());
			map.put("originalPlace", getOriginalPlace());
			map.put("netValue", netValue - originalAccumDepr);
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
			map.put("deprMethod", getDeprMethod());
			map.put("buildFeeCB", getBuildFeeCB());
			map.put("deprUnitCB", getDeprUnitCB());
			map.put("deprPark", getDeprPark());
			map.put("deprUnit", getDeprUnit());
			map.put("deprAccounts", getDeprAccounts());
			map.put("scrapValue", getScrapValue());
			map.put("deprAmount", getDeprAmount());
			map.put("accumDepr", getAccumDepr());
			map.put("apportionMonth", getApportionMonth());
			map.put("monthDepr", getMonthDepr());
			map.put("monthDepr1", getMonthDepr1());
			map.put("noDeprSet", getNoDeprSet());
			map.put("escrowOriValue", getEscrowOriValue());
			map.put("escrowOriAccumDepr", getEscrowOriAccumDepr());
			map.put("originalDeprUnit1", getOriginalDeprUnit1());
			map.put("deprUnit1", getDeprUnit1());
			map.put("originalApportionEndYM", ut._transToCE_Year(getOriginalApportionEndYM()));
			map.put("apportionEndYM", ut._transToCE_Year(getApportionEndYM()));
			map.put("enterOrg", getEnterOrg());
			map.put("ownership", getOwnership());
			map.put("caseNo", getCaseNo());
			map.put("differenceKind", getDifferenceKind());
			map.put("engineeringNo", getEngineeringNo());
			map.put("caseSerialNo", getCaseSerialNo());
			map.put("propertyNo", getPropertyNo());
			map.put("serialNo", getSerialNo());
			map.put("otherLimitYear",getOtherLimitYear());
			map.put("originalLimitYear",getOriginalLimitYear());
			map.put("cause", getCause());
			map.put("cause1", getCause1());		
			map.put("dataState", getDataState());
			map.put("reduceDate", ut._transToCE_Year(getReduceDate()));
			map.put("reduceCause", getReduceCause());
			map.put("reduceCause1", getReduceCause1());
			map.put("verify", getVerify());
			map.put("propertyKind", getPropertyKind());
			map.put("fundType", getFundType());
			map.put("valuable", getValuable());
			map.put("taxCredit", getTaxCredit());
			map.put("originalMeasure", getOriginalMeasure());
			map.put("measure", getMeasure());
			map.put("originalBV", getOriginalBV());
			map.put("originalNote", getOriginalNote());
			map.put("accountingTitle", getAccountingTitle());
			map.put("bookValue", getBookValue());
			map.put("fundsSource1", getFundsSource1());
			map.put("buildDate", ut._transToCE_Year(getBuildDate()));
			map.put("buyDate", ut._transToCE_Year(getBuyDate()));
			map.put("sourceKind", getSourceKind());
			map.put("sourceDate", ut._transToCE_Year(getSourceDate()));
			map.put("sourceDoc", getSourceDoc());
			map.put("appraiseDate", ut._transToCE_Year(getAppraiseDate()));		
			map.put("moveDate", ut._transToCE_Year(getMoveDate()));
			map.put("keepUnit", getKeepUnit());
			map.put("keeper", getKeeper());
			map.put("useUnit", getUseUnit());
			map.put("userNo", getUserNo());
			map.put("oldPropertyNo", getOldPropertyNo());
			map.put("oldSerialNo", getOldSerialNo());
			map.put("otherpropertyunit", (!"".equals(getOtherPropertyUnit()))? getOtherPropertyUnit(): getPropertyUnit());
			map.put("othermaterial", (!"".equals(getOtherMaterial()))? getOtherMaterial(): getMaterial());
		}		

		map.put("propertyName1", getPropertyName1());
		map.put("notes", getNotes());
		map.put("picture", getPicture());
		map.put("userNote", getUserNote());
		map.put("fundsSource", getFundsSource());
		map.put("place1", getPlace1());
		map.put("place", getPlace());
		map.put("editID", getEditID());
		map.put("editDate", ut._transToCE_Year(getEditDate()));
		map.put("editTime", getEditTime());
		map.put("individualSetDepr", getIndividualSetDepr());
		map.put("verifyYM", ut._transToCE_Year(getVerifyYM()));
		
		return map;
	}
	

//傳回執行insert前之檢查sql
protected String[][] getInsertCheckSQL(){
	String[][] checkSQLArray = new String[2][4];
	checkSQLArray[1][0]="";
	checkSQLArray[1][1]="";
	checkSQLArray[1][2]="";
	checkSQLArray[1][3]="";	
	
	//取得分號
	Database db = new Database();
	ResultSet rs;	
	String sql="select (max(serialNo) + 1) as serialNo from UNTRF_Attachment " +
		" where enterOrg = " + Common.sqlChar(enterOrg) + 
		" and ownership = " + Common.sqlChar(ownership) + 
		" and propertyNo = " + Common.sqlChar(propertyNo) +
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
		}else{
			setSerialNo("0000001");
		}
		rs.close();
		
		sql = " select ownership, verify from UNTRF_AddProof where 1=1 " +
			" and enterOrg = " + Common.sqlChar(enterOrg) +
			" and ownership = " + Common.sqlChar(ownership) +
			" and caseNo = " + Common.sqlChar(caseNo) +
			" and differenceKind = " + Common.sqlChar(differenceKind) +
			"";
		rs = db.querySQL(sql);
		if (rs.next()) {
			setOwnership(rs.getString("ownership"));
			setVerify(rs.getString("verify"));
//		} else {
//			checkSQLArray[1][0]=" select 222 as checkResult from dual ";
//			checkSQLArray[1][1]=">";
//			checkSQLArray[1][2]="1";
//			checkSQLArray[1][3]="查無此增加單編號，請重新輸入！";			
		}				
	} catch (Exception e) {
		e.printStackTrace();
	} finally {
		db.closeAll();
	}	

 	checkSQLArray[0][0]=" select count(*) as checkResult from UNTRF_Attachment where 1=1 " + 
		" and enterOrg = " + Common.sqlChar(enterOrg) + 
		" and ownership = " + Common.sqlChar(ownership) + 
		" and caseNo = " + Common.sqlChar(caseNo) +
		" and differenceKind = " + Common.sqlChar(differenceKind) + 
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
	execSQLArray[0] = new SQLCreator()._obtainSQLforInsert("UNTRF_ATTACHMENT", getPKMap(), getRecordMap("insert"));
	return execSQLArray;
}


//傳回執行Update前之檢查sql
protected String[][] getUpdateCheckSQL(){
    String[][] checkSQLArray = new String[1][4];
    
	checkSQLArray[0][0]="";
	checkSQLArray[0][1]="";
	checkSQLArray[0][2]="";
	checkSQLArray[0][3]="";	
	
	Database db = new Database();
	ResultSet rs;	
	try {		
		String sql = " select ownership, verify from UNTRF_AddProof " +
			" where enterOrg = " + Common.sqlChar(enterOrg) +
			" and ownership = " + Common.sqlChar(ownership) +
			" and caseNo = " + Common.sqlChar(caseNo) +
			" and differenceKind = " + Common.sqlChar(differenceKind) +
			"";   
		rs = db.querySQL(sql);
		if (rs.next()) {
			setOwnership(rs.getString("ownership"));
			setVerify(rs.getString("verify"));

//		} else {
//			checkSQLArray[0][0]=" select 222 as checkResult from dual ";
//			checkSQLArray[0][1]=">";
//			checkSQLArray[0][2]="1";
//			checkSQLArray[0][3]="查無此增加單編號，請重新輸入！";			
		}
		rs.close();
	} catch (Exception e) {
		e.printStackTrace();
	} finally {
		db.closeAll();
	}

	return checkSQLArray;
}




//傳回update sql
protected String[] getUpdateSQL(){
	String[] execSQLArray = new String[1];
	
	execSQLArray[0] = new SQLCreator()._obtainSQLforUpdate("UNTRF_ATTACHMENT", getPKMap(), getRecordMap("update"));
	
	return execSQLArray;
}


//傳回執行Delete前之檢查sql
protected String[][] getDeleteCheckSQL(){
    String[][] checkSQLArray = new String[1][4];
    
 	checkSQLArray[0][0]=" select count(*) as checkResult from UNTRF_AddProof where verify='Y' " + 
		" and enterOrg = " + Common.sqlChar(enterOrg) + 
		" and ownership = " + Common.sqlChar(ownership) + 
		" and caseNo = " + Common.sqlChar(caseNo) +
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
		
	sbSQL.append(" delete from UNTRF_Attachment where 1=1 " ).append(
			" and enterOrg = " ).append( Common.sqlChar(enterOrg) ).append(
			" and ownership = " ).append( Common.sqlChar(ownership) ).append(
			" and caseNo = " ).append( Common.sqlChar(caseNo) ).append(
			" and differenceKind = " ).append( Common.sqlChar(differenceKind) ).append(
			" and propertyNo = " ).append( Common.sqlChar(propertyNo) ).append(
			" and serialNo = " ).append( Common.sqlChar(serialNo) ).append(
			":;:");
	
	sbSQL.append(" delete from UNTRF_Base where 1=1 " ).append(
	" and enterOrg = " ).append( Common.sqlChar(enterOrg) ).append(
	" and ownership = " ).append( Common.sqlChar(ownership) ).append(
	" and differenceKind = " ).append( Common.sqlChar(differenceKind) ).append(
	" and propertyNo = " ).append( Common.sqlChar(propertyNo) ).append(
	" and serialNo = " ).append( Common.sqlChar(serialNo) ).append(
	":;:");
	
	sbSQL.append(" delete from UNTRF_Attachment2 where 1=1 " ).append(
	" and enterOrg = " ).append( Common.sqlChar(enterOrg) ).append(
	" and ownership = " ).append( Common.sqlChar(ownership) ).append(
	" and propertyNo = " ).append( Common.sqlChar(propertyNo) ).append(
	" and serialNo = " ).append( Common.sqlChar(serialNo) ).append(
	":;:");

	sbSQL.append(" delete from UNTRF_ViewScene where 1=1 " ).append(
	" and enterOrg = " ).append( Common.sqlChar(enterOrg) ).append(
	" and ownership = " ).append( Common.sqlChar(ownership) ).append(
	" and propertyNo = " ).append( Common.sqlChar(propertyNo) ).append(
	" and serialNo = " ).append( Common.sqlChar(serialNo) ).append(
	":;:");
	
	sbSQL.append(" delete from UNTRF_Manage where 1=1 " ).append(
	" and enterOrg = " ).append( Common.sqlChar(enterOrg) ).append(
	" and ownership = " ).append( Common.sqlChar(ownership) ).append(
	" and differenceKind = " ).append( Common.sqlChar(differenceKind) ).append(
	" and propertyNo = " ).append( Common.sqlChar(propertyNo) ).append(
	" and serialNo = " ).append( Common.sqlChar(serialNo) ).append(
	":;:");
	
	return sbSQL.toString().split(":;:");
}


/**
 * <br>
 * <br>目的：依主鍵查詢單一資料
 * <br>參數：無
 * <br>傳回：傳回本物件
*/

public UNTRF002F  queryOne() throws Exception{
	Database db = new Database();
	UNTRF002F obj = this;
	try {
		String sql=" select a.*, b.organSName as enterOrgName, " +
			" c.propertyName, c.propertyType, c.propertyUnit, c.material, c.limitYear, " +
			" (select codename from SYSCA_CODE z where codekindid = 'SKD' and z.codeid = a.sourcekind) as sourcekindName, " +
			" (select codename from SYSCA_CODE z where codekindid = 'CAA' and z.codeid = a.cause and codecon1 in (1,4)) as causeName, " +
			" d.organAName as useUnitName, " +
			" (select placename from SYSCA_PLACE z where z.enterorg = a.enterorg and z.placeno = a.originalPlace1) as originalPlace1Name," +
			" (select placename from SYSCA_PLACE z where z.enterorg = a.enterorg and z.placeno = a.place1) as place1Name" +
			" , a.picture, a.individualsetdepr, a.verifyym from UNTRF_Attachment a " +
			" left join SYSPK_PropertyCode c on a.propertyNo = c.propertyNo" +
			" left join SYSCA_Organ d on a.useUnit = d.organID," +
			" SYSCA_Organ b where 1=1 " +
			" and b.organID = a.enterOrg " +
			" and a.enterOrg = " + Common.sqlChar(enterOrg) +
			" and a.ownership = " + Common.sqlChar(ownership) +
			" and a.propertyNo = " + Common.sqlChar(propertyNo) +
			" and a.serialNo = " + Common.sqlChar(serialNo) +
			" and a.differenceKind = " + Common.sqlChar(differenceKind) +
			"";
//		System.out.println("untrf002fSQL"+sql);
		UNTCH_Tools ul = new UNTCH_Tools();
		ResultSet rs = db.querySQL(sql);
		if (rs.next()){
			obj.setPicture(rs.getString("picture"));
			obj.setEnterOrg(rs.getString("enterOrg"));
			obj.setEnterOrgName(rs.getString("enterOrgName"));			
			obj.setOwnership(rs.getString("ownership"));
			obj.setCaseNo(rs.getString("caseNo"));
			obj.setPropertyNo(rs.getString("propertyNo"));
			obj.setPropertyNoName(rs.getString("propertyName"));
			obj.setPropertyType(rs.getString("propertyType"));
			obj.setPropertyUnit(rs.getString("propertyUnit"));
			obj.setMaterial(rs.getString("material"));
			obj.setOtherLimitYear(rs.getString("otherLimitYear"));
			obj.setOriginalLimitYear(rs.getString("originallimityear"));
			obj.setSerialNo(rs.getString("serialNo"));
			obj.setPropertyName1(rs.getString("propertyName1"));
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
			obj.setOriginalMeasure(rs.getString("originalMeasure"));
			obj.setMeasure(rs.getString("measure"));
			obj.setOriginalBV(rs.getString("originalBV"));
			obj.setOriginalNote(rs.getString("originalNote"));
			obj.setAccountingTitle(rs.getString("accountingTitle"));
			obj.setBookValue(rs.getString("bookValue"));
			obj.setFundsSource(rs.getString("fundsSource"));
			obj.setFundsSource1(rs.getString("fundsSource1"));
			obj.setBuildDate(ul._transToROC_Year(rs.getString("buildDate")));
			obj.setSourceKind(rs.getString("sourceKind"));
			obj.setSourceKindName(rs.getString("sourceKindName"));
			obj.setSourceDate(ul._transToROC_Year(rs.getString("sourceDate")));
			obj.setSourceDoc(rs.getString("sourceDoc"));
			obj.setUseUnit(rs.getString("useUnit"));
			obj.setDeprMethod(rs.getString("deprMethod"));
			obj.setScrapValue(rs.getString("scrapValue"));
			obj.setDeprAmount(rs.getString("deprAmount"));
			obj.setMonthDepr(rs.getString("monthDepr"));
			obj.setMonthDepr1(rs.getString("monthDepr1"));
			obj.setAccumDepr(rs.getString("accumDepr"));
			obj.setAppraiseDate(ul._transToROC_Year(rs.getString("appraiseDate")));
			obj.setNotes(rs.getString("notes"));
			obj.setOldPropertyNo(rs.getString("oldPropertyNo"));
			obj.setOldSerialNo(rs.getString("oldSerialNo"));
			obj.setEditID(rs.getString("editID"));
			obj.setEditDate(ul._transToROC_Year(rs.getString("editDate")));
			obj.setEditTime(rs.getString("editTime"));
			obj.setDifferenceKind(rs.getString("differenceKind"));
			obj.setEngineeringNo(rs.getString("EngineeringNo"));
			obj.setEngineeringNoName(ul._getEngineeringNoName(rs.getString("enterOrg"),rs.getString("engineeringNo")));
			obj.setCaseSerialNo(rs.getString("CaseSerialNo"));
			obj.setNetValue(rs.getString("NetValue"));
			obj.setBuyDate(ul._transToROC_Year(rs.getString("BuyDate")));
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
			obj.setEscrowOriValue(rs.getString("EscrowOriValue"));
			obj.setEscrowOriAccumDepr(rs.getString("EscrowOriAccumDepr"));
		
			obj.setOriginalDeprUnit1(rs.getString("originalDeprUnit1"));
			obj.setDeprUnit1(rs.getString("deprUnit1"));
			obj.setOriginalApportionEndYM(ul._transToROC_Year(rs.getString("originalApportionEndYM")));
			obj.setApportionEndYM(ul._transToROC_Year(rs.getString("apportionEndYM")));
			
			obj.setOriginalPlace1Name(rs.getString("Originalplace1Name"));
			obj.setPlace1Name(rs.getString("place1Name"));
			obj.setOtherPropertyUnit(rs.getString("OtherPropertyUnit"));
			obj.setOtherMaterial(rs.getString("OtherMaterial"));
			
			obj.setIndividualSetDepr(rs.getString("individualsetdepr"));
			obj.setVerifyYM(ul._transToROC_Year(rs.getString("verifyym")));
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
		String sql=" select a.enterOrg, a.caseNo, a.differenceKind, a.netvalue, " +
				"(select so.organSName from SYSCA_Organ so where so.organid = a.enterOrg) as enterOrgName, " +
				" a.ownership ,(select x.codeName from sysca_code x where a.ownership = x.codeid and x.codekindid = 'OWA') as ownershipName, " +
				" a.caseNo, a.propertyNo, a.serialNo, a.propertyName1, a.cause, " +
				" (select sp.propertyName from SYSPK_PropertyCode sp where sp.propertyno=a.propertyno) as propertyName, " +
				" (select sc.codeName from SYSCA_Code sc where sc.codeid=a.cause and sc.codekindid='CAA' )as causeName, " +
				" case a.dataState when '2' then '己減損' else '現存' end as dataState," +
				" case a.propertyKind when '01' then '公務用' when '02' then '公共用' when '03' then '事業用' when '04' then '非公用' end as propertyKindName "+
				" ,a.propertyName1 ,a.bookvalue " +
				" from UNTRF_Attachment a" +
				" where 1=1 " +			
				"";
		
		
		sql+=" and a.enterOrg=" + Common.sqlChar(getEnterOrg());
		sql+=" and a.ownership=" + Common.sqlChar(getOwnership());		
		sql+=" and a.caseNo = " + Common.sqlChar(getCaseNo()) ;
		sql+=" and a.differenceKind = " + Common.sqlChar(getDifferenceKind()) ;
		sql+=" and a.propertyNo = " + Common.sqlChar(getPropertyNo()) ;
		sql+=" and a.serialNo = " + Common.sqlChar(getSerialNo()) ;
		
		
		ResultSet rs = db.querySQL(sql + " order by a.enterOrg, a.ownership, a.caseNo, a.propertyNo, a.serialNo ",true);
		UNTCH_Tools ucl = new UNTCH_Tools();
		processCurrentPageAttribute(rs);			
		if (getTotalRecord() > 0) {
			int count = getRecordStart();
			int end = getRecordEnd();
			do {
				if (count > end)
					break;
			String rowArray[]=new String[15];
			rowArray[0]=rs.getString("enterOrg"); 
			rowArray[1]=rs.getString("ownership");
			rowArray[2]=rs.getString("caseNo");
			rowArray[3]=rs.getString("differenceKind");
			rowArray[4]=rs.getString("propertyNo"); 
			rowArray[5]=rs.getString("serialNo");
			
			rowArray[6]=ucl._getDifferenceKindName(rs.getString("differenceKind")); 
			rowArray[7]=rs.getString("ownershipName");
			rowArray[8]=rs.getString("propertyNo")+"-"+rs.getString("serialNo");
			rowArray[9]=rs.getString("propertyName");
			rowArray[10]=rs.getString("causeName");
			rowArray[11]=rs.getString("dataState");
			rowArray[12]=rs.getString("propertyKindName");			
			rowArray[13]=rs.getString("bookValue");	
			rowArray[14]=rs.getString("netValue");	
			
			
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