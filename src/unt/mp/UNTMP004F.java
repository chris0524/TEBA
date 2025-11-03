/*
*<br>程式目的：動產主檔資料維護－批號明細
*<br>程式代號：untmp004f
*<br>程式日期：0941021
*<br>程式作者：cherry
*<br>--------------------------------------------------------
*<br>修改作者　　修改日期　　　修改目的
*<br>--------------------------------------------------------
*/

package unt.mp;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import TDlib_Simple.com.src.DBServerTools;
import TDlib_Simple.com.src.SQLCreator;
import unt.ch.UNTCH_Tools;
import util.Common;
import util.Database;
import util.Datetime;
import util.TCGHCommon;

public class UNTMP004F extends UNTMP001Q{

	public UNTMP004F() {
		
	}
	
	public UNTMP004F(String caller) {
		this.setCaller(caller);
	}
	
	/** 呼叫者 **/
	private String caller;
	public String getCaller() { return checkGet(caller); }
	public void setCaller(String caller) { this.caller = checkSet(caller); }

	private String initDtl;	
	public String getInitDtl() {return checkGet(initDtl);}
	public void setInitDtl(String initDtl) {this.initDtl = checkSet(initDtl);}
	
	private String originalApportionEndYM;
	public String getOriginalApportionEndYM() {return checkGet(originalApportionEndYM);}
	public void setOriginalApportionEndYM(String originalApportionEndYM) {this.originalApportionEndYM = checkSet(originalApportionEndYM);}	
	private String apportionEndYM;
	public String getApportionEndYM() {return checkGet(apportionEndYM);}
	public void setApportionEndYM(String apportionEndYM) {this.apportionEndYM = checkSet(apportionEndYM);}
	
	private String enterDate;
	private String engineeringNoName;
	private String buyDate;
	private String material;
	private String propertyUnit;
	private String limitYear;
	private String otherMaterial;
	private String otherPropertyUnit;
	private String otherLimitYear;
	private String originalPlace1Name;
	private String place1Name;
	public String getPlace1Name() {return checkGet(place1Name);}
	public void setPlace1Name(String place1Name) {this.place1Name = checkSet(place1Name);}
	public String getOriginalPlace1Name() {return checkGet(originalPlace1Name);}
	public void setOriginalPlace1Name(String originalPlace1Name) {this.originalPlace1Name = checkSet(originalPlace1Name);}
	public String getEnterDate() {return checkGet(enterDate);}
	public void setEnterDate(String enterDate) {this.enterDate = checkSet(enterDate);}
	public String getBuyDate() {return checkGet(buyDate);}
	public void setBuyDate(String buyDate) {this.buyDate = checkSet(buyDate);}
	public String getMaterial() {return checkGet(material);}
	public void setMaterial(String material) {this.material = checkSet(material);}
	public String getPropertyUnit() {return checkGet(propertyUnit);}
	public void setPropertyUnit(String propertyUnit) {this.propertyUnit = checkSet(propertyUnit);}
	public String getLimitYear() {return checkGet(limitYear);}
	public void setLimitYear(String limitYear) {this.limitYear = checkSet(limitYear);}
	public String getOtherMaterial() {return checkGet(otherMaterial);}
	public void setOtherMaterial(String otherMaterial) {this.otherMaterial = checkSet(otherMaterial);}
	public String getOtherPropertyUnit() {return checkGet(otherPropertyUnit);}
	public void setOtherPropertyUnit(String otherPropertyUnit) {this.otherPropertyUnit = checkSet(otherPropertyUnit);}
	public String getOtherLimitYear() {return checkGet(otherLimitYear);}
	public void setOtherLimitYear(String otherLimitYear) {this.otherLimitYear = checkSet(otherLimitYear);}
	public String getEngineeringNoName() {return checkGet(engineeringNoName);}
	public void setEngineeringNoName(String engineeringNoName) {this.engineeringNoName = checkSet(engineeringNoName);}
	
	private String enterOrgName;
	private String ownershipName;
	private String differenceKindName;
	private String propertyNoName;
	private String propertyName1;
	public String getEnterOrgName() {return checkGet(enterOrgName);}
	public void setEnterOrgName(String enterOrgName) {this.enterOrgName = checkSet(enterOrgName);}
	public String getOwnershipName() {return checkGet(ownershipName);}
	public void setOwnershipName(String ownershipName) {this.ownershipName = checkSet(ownershipName);}
	public String getDifferenceKindName() {return checkGet(differenceKindName);}
	public void setDifferenceKindName(String differenceKindName) {this.differenceKindName = checkSet(differenceKindName);}
	public String getPropertyNoName() {return checkGet(propertyNoName);}
	public void setPropertyNoName(String propertyNoName) {this.propertyNoName = checkSet(propertyNoName);}
	public String getPropertyName1() {return checkGet(propertyName1);}
	public void setPropertyName1(String propertyName1) {this.propertyName1 = checkSet(propertyName1);}
		
	private String enterOrg;
	private String ownership;
	private String caseNo;
	private String differenceKind;
	private String engineeringNo;
	private String propertyNo;
	private String lotNo;
	private String serialNo;
	private String dataState;
	private String reduceDate;
	private String reduceCause;
	private String reduceCause1;
	private String verify;
	private String originalAmount;
	private String originalBV;
	private String bookAmount;
	private String bookValue;
	private String netValue;
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
	private String originalDeprMethod;
	private String originalBuildFeeCB;
	private String originalDeprUnitCB;
	private String originalDeprPark;
	private String originalDeprUnit;
	private String originalDeprAccounts;
	private String originalScrapValue;
	private String originalDeprAmount;
	private String originalAccumDepr;
	private String originalApportionMonth;
	private String originalMonthDepr;
	private String originalMonthDepr1;
	private String deprMethod;
	private String deprMethod1;
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
	private String barCode;
	private String oldPropertyNo;
	private String oldSerialNo;
	private String notes;
	private String editID;
	private String editDate;
	private String editTime;

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
	public String getPropertyNo() {return checkGet(propertyNo);}
	public void setPropertyNo(String propertyNo) {this.propertyNo = checkSet(propertyNo);}
	public String getLotNo() {return checkGet(lotNo);}
	public void setLotNo(String lotNo) {this.lotNo = checkSet(lotNo);}
	public String getSerialNo() {return checkGet(serialNo);}
	public void setSerialNo(String serialNo) {this.serialNo = checkSet(serialNo);}
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
	public String getOriginalAmount() {return checkGet(originalAmount);}
	public void setOriginalAmount(String originalAmount) {this.originalAmount = checkSet(originalAmount);}
	public String getOriginalBV() {return checkGet(originalBV);}
	public void setOriginalBV(String originalBV) {this.originalBV = checkSet(originalBV);}
	public String getBookAmount() {return checkGet(bookAmount);}
	public void setBookAmount(String bookAmount) {this.bookAmount = checkSet(bookAmount);}
	public String getBookValue() {return checkGet(bookValue);}
	public void setBookValue(String bookValue) {this.bookValue = checkSet(bookValue);}
	public String getNetValue() {return checkGet(netValue);}
	public void setNetValue(String netValue) {this.netValue = checkSet(netValue);}
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
	public String getNotes1() {return checkGet(notes1);}
	public void setNotes1(String notes1) {this.notes1 = checkSet(notes1);}
	public String getBarCode() {return checkGet(barCode);}
	public void setBarCode(String barCode) {this.barCode = checkSet(barCode);}
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
	public String getOriginalMonthDepr1() {return checkGet(originalMonthDepr1);}
	public void setOriginalMonthDepr1(String originalMonthDepr1) {this.originalMonthDepr1 = checkSet(originalMonthDepr1);}
	public String getDeprMethod1() {return checkGet(deprMethod1);}
	public void setDeprMethod1(String deprMethod1) {this.deprMethod1 = checkSet(deprMethod1);}

	String checkEnterOrg;
	String setMoveDate;
	String setKeepUnit;
	String setKeeper;
	String setUseUnit;
	String setUser;
	String setPlace;
	public String getCheckEnterOrg(){ return checkGet(checkEnterOrg); }
	public void setCheckEnterOrg(String s){ checkEnterOrg=checkSet(s); }
	public String getSetMoveDate() {return checkGet(setMoveDate);}
	public void setSetMoveDate(String setMoveDate) {this.setMoveDate = checkSet(setMoveDate);}
	public String getSetKeepUnit() {return checkGet(setKeepUnit);}
	public void setSetKeepUnit(String setKeepUnit) {this.setKeepUnit = checkSet(setKeepUnit);}
	public String getSetKeeper() {return checkGet(setKeeper);}
	public void setSetKeeper(String setKeeper) {this.setKeeper = checkSet(setKeeper);}
	public String getSetUseUnit() {return checkGet(setUseUnit);}
	public void setSetUseUnit(String setUseUnit) {this.setUseUnit = checkSet(setUseUnit);}
	public String getSetUser() {return checkGet(setUser);}
	public void setSetUser(String setUser) {this.setUser = checkSet(setUser);}
	public String getSetPlace() {return checkGet(setPlace);}
	public void setSetPlace(String setPlace) {this.setPlace = checkSet(setPlace);}
	
	String batchInsertFlag;
	public String getBatchInsertFlag(){ return checkGet(batchInsertFlag); }
	public void setBatchInsertFlag(String s){ batchInsertFlag=checkSet(s); }

	private String originalDeprUnit1;
	private String deprUnit1;	
	public String getOriginalDeprUnit1() {return checkGet(originalDeprUnit1);}
	public void setOriginalDeprUnit1(String originalDeprUnit1) {this.originalDeprUnit1 = checkSet(originalDeprUnit1);}
	public String getDeprUnit1() {return checkGet(deprUnit1);}
	public void setDeprUnit1(String deprUnit1) {this.deprUnit1 = checkSet(deprUnit1);}
	
	private String monthDepr1;
	public String getMonthDepr1() {return checkGet(monthDepr1);}
	public void setMonthDepr1(String monthDepr1) {this.monthDepr1 = checkSet(monthDepr1);}
	
	private Map getPKMap(){
		Map map = new HashMap();
		
		map.put("enterOrg", getEnterOrg());
		map.put("ownership", getOwnership());
		map.put("caseNo", getCaseNo());
		map.put("differenceKind", getDifferenceKind());
		map.put("propertyNo", getPropertyNo());
		map.put("lotNo", getLotNo());
		map.put("serialNo", getSerialNo());
		
		return map;
	}	
	
	private Map getRecordMap(){
		Map map = new HashMap();
		UNTCH_Tools ut = new UNTCH_Tools();
		map.put("engineeringNo", getEngineeringNo());
		map.put("dataState", getDataState());
		map.put("reduceDate", ut._transToCE_Year(getReduceDate()));
		map.put("reduceCause", getReduceCause());
		map.put("reduceCause1", getReduceCause1());
		map.put("verify", getVerify());
		map.put("originalAmount", getOriginalAmount());
		map.put("originalBV", getOriginalBV());
		map.put("bookAmount", getBookAmount());
		map.put("bookValue", getBookValue());
		map.put("netValue", getNetValue());
		map.put("licensePlate", getLicensePlate());
		map.put("purpose", getPurpose());
		map.put("originalMoveDate", ut._transToCE_Year(getOriginalMoveDate()));
		map.put("originalKeepUnit", getOriginalKeepUnit());
		map.put("originalKeeper", getOriginalKeeper());
		map.put("originalUseUnit", getOriginalUseUnit());
		map.put("originalUser", getOriginalUser());
		map.put("originalUserNote", getOriginalUserNote());
		map.put("originalPlace1", getOriginalPlace1());
		map.put("originalPlace", getOriginalPlace());
		map.put("moveDate", ut._transToCE_Year(getMoveDate()));
		map.put("keepUnit", getKeepUnit());
		map.put("keeper", getKeeper());
		map.put("useUnit", getUseUnit());
		map.put("userNo", getUserNo());
		map.put("userNote", getUserNote());
		map.put("place1", getPlace1());
		map.put("place", getPlace());
		map.put("originalDeprMethod", getOriginalDeprMethod());
		map.put("originalBuildFeeCB", getOriginalBuildFeeCB());
		map.put("originalDeprUnitCB", getOriginalDeprUnitCB());
		map.put("originalDeprPark", getOriginalDeprPark());
		map.put("originalDeprUnit", getOriginalDeprUnit());
		map.put("originalDeprAccounts", getOriginalDeprAccounts());
		map.put("originalScrapValue", getOriginalScrapValue());
		map.put("originalDeprAmount", getOriginalDeprAmount());
		map.put("originalAccumDepr", getOriginalAccumDepr());
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
		//map.put("noDeprSet", getNoDeprSet());
		map.put("notes1", getNotes1());
		map.put("barCode", getBarCode());
		map.put("oldPropertyNo", getOldPropertyNo());
		map.put("oldSerialNo", getOldSerialNo());
		map.put("notes", getNotes());
		map.put("editID", getEditID());
		map.put("editDate", ut._transToCE_Year(getEditDate()));
		map.put("editTime", getEditTime());
		map.put("originalDeprUnit1", getOriginalDeprUnit1());
		map.put("deprUnit1", getDeprUnit1());
		map.put("originalApportionEndYM", ut._transToCE_Year(getOriginalApportionEndYM()));
		map.put("ApportionEndYM", ut._transToCE_Year(getApportionEndYM()));
System.out.println("111 >> " + getPropertyName1());		
		map.put("propertyName1", getPropertyName1());

		return map;
	}
	
	
//傳回update sql
protected String[] getUpdateSQL(){
	String[] execSQLArray = new String[1];
	
	execSQLArray[0] = new SQLCreator()._obtainSQLforUpdate("UNTMP_MovableDetail", getPKMap(), getRecordMap());
	
	return execSQLArray;
}

/**
 * <br>
 * <br>目的：依主鍵查詢單一資料
 * <br>參數：無
 * <br>傳回：傳回本物件
*/

public UNTMP004F  queryOne() throws Exception{
	UNTMP004F obj = this;
	Database db = new Database();
	try {
		String sql=" select a.*, b.organSName as enterOrgName, " +
					" (select z.codename from SYSCA_CODE z where codekindid = 'OWA' and z.codeid = a.ownership) as ownershipName," +
					" (select z.codename from SYSCA_CODE z where codekindid = 'DFK' and z.codeid = a.differencekind) as DifferenceKindName," +
					" (select propertyname from SYSPK_PROPERTYCODE z where z.enterorg = a.enterorg and z.propertyno = a.propertyno) as propertyNoName," +
					" c.propertyName, c.propertyUnit, c.material, c.limitYear, " +
					" d.caseNo, d.otherPropertyUnit, d.otherMaterial, (case d.otherLimitYear when null then '0' else d.otherLimitYear end) as otherLimitYear, " +
					" d.buyDate, d.enterDate,d.propertyname1,d.sourcekind,d.sourcedate,d.sourcedoc,d.propertykind,d.fundtype,d.valuable, "+
					" d.specification,d.nameplate,d.fundssource,d.fundssource1,d.accountingtitle, "+
					" (select placename from SYSCA_PLACE z where z.enterorg = a.enterorg and z.placeno = a.originalPlace1) as originalPlace1Name," +
					" (select placename from SYSCA_PLACE z where z.enterorg = a.enterorg and z.placeno = a.place1) as place1Name," +
					" a.originalMonthDepr1, a.MonthDepr1, a.propertyname1,d.originalapportionendym as originalapportionendym1 " +
					" from UNTMP_Movable d , UNTMP_MovableDetail a " +
					" left join SYSCA_Organ b on a.enterOrg=b.organID" +
					" left join SYSPK_PropertyCode c on c.enterOrg in ('000000000A',a.enterOrg) and a.propertyNo=c.propertyNo and c.propertyType='1'" +
					" where 1=1 " +
					" and a.lotNo=d.lotNo and a.enterOrg=d.enterOrg and a.ownership=d.ownership and a.propertyNo=d.propertyNo and a.differencekind = d.differencekind " +
					" and a.enterOrg = " + Common.sqlChar(enterOrg) +
					" and a.ownership = " + Common.sqlChar(ownership) +
					" and a.propertyNo = " + Common.sqlChar(propertyNo) +
					" and a.lotNo = " + Common.sqlChar(lotNo) +
					" and a.serialNo = " + Common.sqlChar(serialNo) +
					" and a.differenceKind = " + Common.sqlChar(differenceKind) +
					"";
		//System.out.println("one"+sql);
		UNTCH_Tools ul = new UNTCH_Tools();
		ResultSet rs = db.querySQL(sql);
		if (rs.next()){
			obj.setEnterOrg(rs.getString("EnterOrg"));
			obj.setOwnership(rs.getString("Ownership"));
			obj.setCaseNo(rs.getString("CaseNo"));
			obj.setDifferenceKind(rs.getString("DifferenceKind"));
			obj.setEngineeringNo(rs.getString("EngineeringNo"));
			obj.setPropertyNo(rs.getString("PropertyNo"));
			obj.setLotNo(rs.getString("LotNo"));
			obj.setSerialNo(rs.getString("SerialNo"));
			obj.setDataState(rs.getString("DataState"));
			obj.setPropertyName1(rs.getString("PropertyName1"));
			
			
			obj.setReduceDate(ul._transToROC_Year(rs.getString("ReduceDate")));
			obj.setReduceCause(rs.getString("ReduceCause"));
			obj.setReduceCause1(rs.getString("ReduceCause1"));
			obj.setVerify(rs.getString("Verify"));
			obj.setOriginalAmount(rs.getString("OriginalAmount"));
			obj.setOriginalBV(rs.getString("OriginalBV"));
			obj.setBookAmount(rs.getString("BookAmount"));
			obj.setBookValue(rs.getString("BookValue"));
			obj.setNetValue(rs.getString("NetValue"));
			obj.setLicensePlate(rs.getString("LicensePlate"));
			obj.setPurpose(rs.getString("Purpose"));
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
			obj.setOriginalDeprMethod(rs.getString("OriginalDeprMethod"));
			obj.setOriginalBuildFeeCB(rs.getString("OriginalBuildFeeCB"));
			obj.setOriginalDeprUnitCB(rs.getString("OriginalDeprUnitCB"));
			obj.setOriginalDeprPark(rs.getString("OriginalDeprPark"));
			obj.setOriginalDeprUnit(rs.getString("OriginalDeprUnit"));
			obj.setOriginalDeprAccounts(rs.getString("OriginalDeprAccounts"));
			obj.setOriginalScrapValue(rs.getString("OriginalScrapValue"));
			obj.setOriginalDeprAmount(rs.getString("OriginalDeprAmount"));
			obj.setOriginalAccumDepr(rs.getString("OriginalAccumDepr"));
			obj.setOriginalApportionMonth(rs.getString("OriginalApportionMonth"));
			obj.setOriginalMonthDepr(rs.getString("OriginalMonthDepr"));
			obj.setDeprMethod(rs.getString("DeprMethod"));
			obj.setBuildFeeCB(rs.getString("BuildFeeCB"));
			obj.setDeprUnitCB(rs.getString("DeprUnitCB"));
			obj.setDeprPark(rs.getString("DeprPark"));
			obj.setDeprUnit(rs.getString("DeprUnit"));
			obj.setDeprAccounts(rs.getString("DeprAccounts"));
			obj.setScrapValue(rs.getString("ScrapValue"));
			obj.setDeprAmount(rs.getString("DeprAmount"));
			obj.setAccumDepr(rs.getString("AccumDepr"));
			obj.setApportionMonth(rs.getString("ApportionMonth"));
			obj.setMonthDepr(rs.getString("MonthDepr"));
			obj.setNoDeprSet(rs.getString("NoDeprSet"));
			obj.setNotes1(rs.getString("Notes1"));
			obj.setBarCode(rs.getString("BarCode"));
			obj.setOldPropertyNo(rs.getString("OldPropertyNo"));
			obj.setOldSerialNo(rs.getString("OldSerialNo"));
			obj.setNotes(rs.getString("Notes"));
			obj.setEditID(rs.getString("EditID"));
			obj.setEditDate(ul._transToROC_Year(rs.getString("EditDate")));
			obj.setEditTime(rs.getString("EditTime"));
			obj.setEnterOrgName(rs.getString("EnterOrgName"));
			obj.setOwnershipName(rs.getString("OwnershipName"));
			obj.setDifferenceKindName(rs.getString("DifferenceKindName"));
			obj.setPropertyNoName(rs.getString("PropertyNoName"));
			
			obj.setBuyDate(ul._transToROC_Year(rs.getString("BuyDate")));
			obj.setMaterial(rs.getString("Material"));
			obj.setPropertyUnit(rs.getString("PropertyUnit"));
			obj.setLimitYear(rs.getString("LimitYear"));
			obj.setOtherMaterial(rs.getString("OtherMaterial"));
			obj.setOtherPropertyUnit(rs.getString("OtherPropertyUnit"));
			obj.setOtherLimitYear(rs.getString("OtherLimitYear"));
			obj.setEnterDate(ul._transToROC_Year(rs.getString("enterDate")));
			
			obj.setOriginalDeprUnit1(rs.getString("originalDeprUnit1"));
			obj.setDeprUnit1(rs.getString("deprUnit1"));
			obj.setOriginalApportionEndYM(ul._transToROC_Year(rs.getString("originalapportionendym1")));
			obj.setApportionEndYM(ul._transToROC_Year(rs.getString("apportionEndYM")));
			
			obj.setOriginalPlace1Name(rs.getString("originalPlace1Name"));
			obj.setPlace1Name(rs.getString("place1Name"));
			
			obj.setOriginalMonthDepr1(rs.getString("originalMonthDepr1"));
			obj.setMonthDepr1(rs.getString("MonthDepr1"));
			
			obj.setPropertyName1(rs.getString("propertyname1"));
		}else{
			obj.setSerialNo("");				
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
	 Map<String,String> placeMap = TCGHCommon.getSysca_PlaceCode(this.getEnterOrg());			//存置地點名稱
	try {
		String sql= " select a.enterOrg, a.ownership, a.propertyNo, a.caseno, a.differenceKind,a.place1," +
					" (select x.propertyName from SYSPK_PropertyCode x where x.propertyType='1' and x.propertyno = a.propertyno and x.enterOrg in (a.enterOrg,'000000000A') ) as propertyName, "+
					" a.lotNo, a.serialNo, a.bookValue, a.netvalue," +
					" (select z.codename from SYSCA_CODE z where codekindid = 'DFK' and z.codeid = a.differenceKind) as DifferenceKindName," +
					" case a.dataState when '1' then '現存' when '2' then '已減損' else '' end dataState, " +
					" (select d.unitName from UNTMP_KeepUnit d where a.enterOrg=d.enterOrg and a.keepUnit=d.unitNo) as keepUnitName, " +
					" (select e.keeperName from UNTMP_Keeper e where a.enterOrg=e.enterOrg and a.keeper=e.keeperNo) as keeperName, " +
					" a.place " +
					" from UNTMP_MovableDetail a where 1=1 ";
		
			sql +=" and a.enterOrg = " + Common.sqlChar(enterOrg);
			sql +=" and a.ownership = " + Common.sqlChar(ownership);
			sql +=" and a.propertyNo = " + Common.sqlChar(propertyNo);
			sql +=" and a.lotNo = " + Common.sqlChar(lotNo);
			sql +=" and a.differenceKind = " + Common.sqlChar(differenceKind);
			sql +=" and a.caseNo = " + Common.sqlChar(caseNo);
		
		sql+=" order by a.enterOrg, a.ownership, a.propertyNo, a.serialNo, a.dataState ";
//System.out.println("-- untmp004f queryAll -- "+sql);	
		ResultSet rs = db.querySQL(sql,true);
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
			rowArray[4]=rs.getString("differenceKindName");
			rowArray[5]=rs.getString("propertyNo"); 
			rowArray[6]=rs.getString("serialNo"); 
			rowArray[7]=rs.getString("lotNo");			
			rowArray[8]=rs.getString("propertyName");
			rowArray[9]=rs.getString("keepUnitName");
			rowArray[10]=rs.getString("keeperName");
			rowArray[11]=checkGet(placeMap.get(rs.getString("place1")));
			rowArray[12]=rs.getString("dataState"); 
			rowArray[13]=rs.getString("bookValue");
			rowArray[14]=rs.getString("netvalue");
			
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

//全部批次設定
public void batchSetButAll()throws Exception{	
	Database db = new Database();
	try {    
	    //int i = 0;
	    int counter = 0;
		String rowArray[]=new String[10];
		java.util.Iterator it= queryAll().iterator();
		String[] execSQLArray;
		StringBuffer sbSQL = new StringBuffer("");
		//String strSQL = "";
		while(it.hasNext()){
		    counter++;
			rowArray= (String[])it.next();
			if(rowArray[12].equals("N")){
				enterOrg = rowArray[0];
				ownership= rowArray[2];
				propertyNo=rowArray[4];
				serialNo   = rowArray[5];
				setEditID(getUserID());
				setEditDate(Datetime.getYYYMMDD());
				setEditTime(Datetime.getHHMMSS());
				if(enterOrg.equals(checkEnterOrg)){
					sbSQL.append("update UNTMP_MovableDetail set ").append(
                				" originalMoveDate = " ).append( Common.sqlChar(new UNTCH_Tools()._transToCE_Year(setMoveDate)) ).append( "," ).append(
								" originalPlace = " ).append( Common.sqlChar(setPlace) ).append( "," ).append(
								" originalKeepUnit = " ).append( Common.sqlChar(setKeepUnit) ).append( "," ).append(
								" originalKeeper = " ).append( Common.sqlChar(setKeeper) ).append( "," ).append(
								" originalUseUnit = " ).append( Common.sqlChar(setUseUnit) ).append( "," ).append(
								" originalUser = " ).append( Common.sqlChar(setUser) ).append( "," ).append(
								" moveDate = " ).append( Common.sqlChar(new UNTCH_Tools()._transToCE_Year(setMoveDate)) ).append( "," ).append(
								" place = " ).append( Common.sqlChar(setPlace) ).append( "," ).append(
								" keepUnit = " ).append( Common.sqlChar(setKeepUnit) ).append( "," ).append(
								" keeper = " ).append( Common.sqlChar(setKeeper) ).append( "," ).append(
								" useUnit = " ).append( Common.sqlChar(setUseUnit) ).append( "," ).append(
								" userNo = " ).append( Common.sqlChar(setUser) ).append( "," ).append(
								" editID = " ).append( Common.sqlChar(getEditID()) ).append( "," ).append(
								" editDate = " ).append( Common.sqlChar(new UNTCH_Tools()._transToCE_Year(getEditDate())) ).append( "," ).append(
								" editTime = " ).append( Common.sqlChar(getEditTime()) ).append( "," ).append(
								" differenceKind = " ).append( Common.sqlChar(getDifferenceKind()) ).append(
								" where 1=1 " ).append( 
								" and enterOrg = " ).append( Common.sqlChar(enterOrg) ).append(
								" and ownership = " ).append( Common.sqlChar(ownership) ).append(
								" and propertyNo = " ).append( Common.sqlChar(propertyNo) ).append(
								" and serialNo = " ).append( Common.sqlChar(serialNo) ).append(
								" and differenceKind = " ).append( Common.sqlChar(differenceKind) ).append(
								":;:");
				}
			}else{
				sbSQL.append("");
			}
		}
		if (counter>0) {
			execSQLArray = sbSQL.toString().split(":;:");
			db.excuteSQL(execSQLArray);
		}
		setStateUpdateSuccess();
		setErrorMsg("修改完成");	
	} catch (Exception e) {
		e.printStackTrace();
	} finally {
		db.closeAll();
	}		
}


/**
 * <br>
 * <br>目的：將動產明細列出，做批次修改的選項
 * <br>參數：無
 * <br>傳回：傳回String
*/
public String getDetailCodeList() throws Exception{
	Map<String,String> placeMap = TCGHCommon.getSysca_PlaceCode(getEnterOrg());			//存置地點名稱
	Database db = new Database();
	StringBuffer sbHTML = new StringBuffer();
	sbHTML.append("");
	int counter=0;
	String selectDetail = "";
	try {
		String sql = "select a.serialNo,a.enterorg, a.originalMoveDate, a.originalKeepUnit, a.originalKeeper, a.originalUseUnit, a.originalUser, a.originalPlace,a.keepunit,a.usernote,a.place,a.place1 from UNTMP_MovableDetail a" +
					" where 1=1 " +
					" and a.enterOrg = " + Common.sqlChar(getEnterOrg()) +
					" and a.ownership = " + Common.sqlChar(getOwnership()) +
					" and a.propertyNo = " + Common.sqlChar(getPropertyNo()) +
					" and a.lotNo = " + Common.sqlChar(getLotNo()) +
					" and a.differenceKind = " + Common.sqlChar(getDifferenceKind()) +
					" order by a.serialNo ";
		ResultSet rs = db.querySQL(sql);
		//System.out.println("-------------" + sql);
		sbHTML.append("<table class='queryTable'  border='1'>\n");
		sbHTML.append("<tr><td class='listTH'><input type=checkbox name=toggleAll onclick=\"ToggleAll(this, document.form1, 'strKeys');\"></td>\n");			
		sbHTML.append("<td class='listTH' align='center'>財產分號</td>\n");
//		sbHTML.append("<td class='listTH' align='center'>移動日期</td>\n");
//		sbHTML.append("<td class='TDLable' align='center'>保管處別</td>\n");
		sbHTML.append("<td class='listTH' align='center'>保管單位</td>\n");
		sbHTML.append("<td class='listTH' align='center'>保管人</td>\n");
//		sbHTML.append("<td class='TDLable' align='center'>使用處別</td>\n");
		sbHTML.append("<td class='listTH' align='center'>使用單位</td>\n");
		sbHTML.append("<td class='listTH' align='center'>使用人</td>\n");
		sbHTML.append("<td class='listTH' align='center'>使用註記</td>\n");
		sbHTML.append("<td class='listTH' align='center'>存置地點</td>\n");
		sbHTML.append("<td class='listTH' align='center'>存置地點說明</td>\n");
		sbHTML.append("</tr>\n");		
		while (rs.next()) {
			counter++;			
			sbHTML.append("<tr>\n");
			sbHTML.append("<td class='queryTDInput'><input type=\"checkbox\" name=\"strKeys\" value=\"" ).append( rs.getString("serialNo") ).append( "\" onClick=\"Toggle(this,document.form1,'strKeys');\" /></td>\n");
			sbHTML.append("<td class='queryTDInput'>[<input type='text' class='field_PRO' size='7' maxlength='7' name='serialNo_" ).append( rs.getString("serialNo")).append( "' value='" ).append( Common.get(rs.getString("serialNo")) ).append( "' readOnly=true>]</td>\n");
//			sbHTML.append("<td class='queryTDInput'><input type='text' class='field' size='7' maxlength='7' name='moveDate_" ).append( rs.getString("serialNo")).append( "' value='" ).append( Common.get(rs.getString("originalMoveDate")) ).append( "'></td>\n");
			
			sbHTML.append("<td class='queryTDInput'>\n" ).append(
			  "<input type='text' class='field' size='5' name='KeepUnitQuickly_").append( rs.getString("serialNo")).append("' value='").append(Common.checkGet(rs.getString("originalKeepUnit"))).append("' onChange='form1.useUnit_").append(rs.getString("serialNo")).append(".value = this.value").append(";form1.keepUnit_").append(rs.getString("serialNo")).append(".value = this.value").append(";form1.UseUnitQuickly_").append(rs.getString("serialNo")).append(".value = this.value").append(";'>" ).append(
			  "<select class='field' type='select' name='keepUnit_").append( rs.getString("serialNo")).append("' onChange='form1.useUnit_").append(rs.getString("serialNo")).append(".value = this.value").append(";form1.KeepUnitQuickly_").append(rs.getString("serialNo")).append(".value = this.value").append(";form1.UseUnitQuickly_").append(rs.getString("serialNo")).append(".value = this.value").append(";'>\n" ).append(
			  "<script>").append("changeKeepUnit(form1.enterOrg").append(", form1.keepUnit_").append(rs.getString("serialNo")).append(", '").append(rs.getString("originalKeepUnit")).append("');</script>\n").append(
			  "</select>").append(
			  "<input class='field_Q' type='button' name='btn_q_keepUnit_").append(rs.getString("serialNo")).append("' onclick=\"popUnitNo('").append(rs.getString("enterorg")).append("','keepUnit_").append(rs.getString("serialNo")).append("','useUnit_").append(rs.getString("serialNo")).append("')\" value='...' title='單位輔助視窗'>").append(
			  "</td>\n");
			sbHTML.append("<td class='queryTDInput'>\n" ).append(
			  "<input type='text' class='field' size='5' name='KeeperQuickly_").append( rs.getString("serialNo")).append("' value='").append(Common.checkGet(rs.getString("originalKeeper"))).append("' onChange='form1.user_").append(rs.getString("serialNo")).append(".value = this.value").append(";form1.keeper_").append(rs.getString("serialNo")).append(".value = this.value").append(";form1.UserQuickly_").append(rs.getString("serialNo")).append(".value = this.value").append(";'>" ).append(
			  "<select class='field' type='select' name='keeper_").append( rs.getString("serialNo")).append("' onChange='form1.user_").append(rs.getString("serialNo")).append(".value = this.value").append(";form1.KeeperQuickly_").append(rs.getString("serialNo")).append(".value = this.value").append(";form1.UserQuickly_").append(rs.getString("serialNo")).append(".value = this.value").append(";'>\n" ).append(
			  "<script>getKeeper(form1.enterOrg, form1.keeper_").append( rs.getString("serialNo")).append(",'").append( rs.getString("originalKeeper")).append("', '").append(rs.getString("originalKeeper")).append("');</script>\n").append(
			  "</select>").append(
			  "<input class='field_Q' type='button' name='btn_q_keeper_").append(rs.getString("serialNo")).append("' onclick=\"popUnitMan('").append(rs.getString("enterorg")).append("','keeper_").append(rs.getString("serialNo")).append("','user_").append(rs.getString("serialNo")).append("')\" value='...' title='人員輔助視窗'>").append(
			  "</td>\n");

			sbHTML.append("<td class='queryTDInput'>\n" ).append(
			  "<input type='text' class='field' size='5' name='UseUnitQuickly_").append( rs.getString("serialNo")).append("' value='").append(Common.checkGet(rs.getString("originalUseUnit"))).append("' onChange='form1.useUnit_").append(rs.getString("serialNo")).append(".value = this.value").append(";'>" ).append(					
			  "<select class='field' type='select' name='useUnit_").append( rs.getString("serialNo")).append("'  onChange='form1.UseUnitQuickly_").append(rs.getString("serialNo")).append(".value = this.value").append(";' >\n" ).append(
			  "<script>changeKeepUnit(form1.enterOrg").append(", form1.useUnit_").append(rs.getString("serialNo")).append(", '").append(rs.getString("originalUseUnit")).append("');</script>\n").append(
			  "</select>").append(
			  "<input class='field_Q' type='button' name='btn_q_useUnit_").append(rs.getString("serialNo")).append("' onclick=\"popUnitNo('").append(rs.getString("enterorg")).append("','useUnit_").append(rs.getString("serialNo")).append("','keepUnit_").append(rs.getString("serialNo")).append("','keepUnit_").append(rs.getString("serialNo")).append("')\" value='...' title='單位輔助視窗'>").append(
			  "</td>\n");
			sbHTML.append("<td class='queryTDInput'>\n" ).append(
			  "<input type='text' class='field' size='5' name='UserQuickly_").append( rs.getString("serialNo")).append("' value='").append(Common.checkGet(rs.getString("originalUser"))).append("' onChange='form1.user_").append(rs.getString("serialNo")).append(".value = this.value").append(";'>" ).append(					
			  "<select class='field' type='select' name='user_").append( rs.getString("serialNo")).append("'  onChange='form1.UserQuickly_").append(rs.getString("serialNo")).append(".value = this.value").append(";' >\n").append(
			  "<script>getKeeper(form1.enterOrg, form1.user_").append( rs.getString("serialNo")).append(",'").append( rs.getString("originalUser")).append("', '").append(rs.getString("originalUser")).append("');</script>\n").append(
			  "</select>").append(
			  "<input class='field_Q' type='button' name='btn_q_user_").append(rs.getString("serialNo")).append("' onclick=\"popUnitMan('").append(rs.getString("enterorg")).append("','user_").append(rs.getString("serialNo")).append("','keeper_").append(rs.getString("serialNo")).append("')\" value='...' title='人員輔助視窗'>").append(
			  "</td>\n");
			sbHTML.append("<td class='queryTDInput'><input type='text' class='field' size='15' maxlength='15' name='userNote_").append( rs.getString("serialNo")).append("' value='").append(Common.get(rs.getString("usernote"))).append("'></td>\n");
			sbHTML.append("<td class='queryTDInput'>").append(
			  "<input class='field_Q' type='text' name='place1_").append( rs.getString("serialNo"))
			  .append("' size='5' maxlength='10' value='").append(Common.get(rs.getString("place1"))).append("'")
			  .append(" onchange=\"queryPlaceName('").append("enterOrg").append("', 'place1_").append(rs.getString("serialNo"))
			  .append("')\">")
			  .append("&nbsp;<input class='field_Q' type='button' name='btn_q_place1_")
			  .append(rs.getString("serialNo")).append("' onclick=\"popPlace('")
			  .append(rs.getString("enterorg")).append("','place1_")
			  .append(rs.getString("serialNo")).append("','place1_")
			  .append(rs.getString("serialNo")).append("Name").append("')\" value='...' title='存置地點輔助視窗'>").append(
			  "&nbsp;[<input class='field_RO' type='text' name='place1_").append(rs.getString("serialNo")).append("Name")
			  .append("' size='20' maxlength='20' value='").append(Common.get(placeMap.get(rs.getString("place1"))))
			  .append("'>]").append("</td>\n");

			sbHTML.append("<td class='queryTDInput'><input type='text' class='field' size='15' maxlength='15' name='place_").append(rs.getString("serialNo")).append("' value='").append(Common.get(rs.getString("place"))).append("'></td>\n");
			sbHTML.append("</tr>\n");
		}
		sbHTML.append("</table>\n");
	} catch (Exception e) {
		e.printStackTrace();
	} finally {
		db.closeAll();
	}
	return sbHTML.toString();
}

public void insertProcess(String strSQL) {
	Database db = new Database();
	String[] arrSQL = strSQL.split(":;:");
    try {
		if (!beforeExecCheck(getInsertCheckSQL())){			
			setBatchInsertFlag("E");
		} else {
			db.excuteSQL(arrSQL);
			setBatchInsertFlag("Y");
			setStateInsertSuccess();
		}
	} catch (Exception e) {
		e.printStackTrace();
	} finally {
		db.closeAll();
	}
}

	public String queryVerify(){
		String sql = "select verify from UNTLA_ADDPROOF" +
						" where 1=1" +
						" and enterorg = " + Common.sqlChar(this.getEnterOrg()) +
						" and ownership = " + Common.sqlChar(this.getOwnership()) +
						" and caseno = " + Common.sqlChar(this.getCaseNo()) +
						" and differenceKind = " + Common.sqlChar(this.getDifferenceKind());
		
		DBServerTools dbt = new DBServerTools();
		dbt._setDatabase(new Database());
		String result = dbt._execSQL_asString(sql);
		dbt = null;
		sql = null;
		return result;
	}

}


