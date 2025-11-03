/*
*<br>程式目的：土地主檔資料維護－基本資料
*<br>程式代號：untla002f
*<br>程式日期：0940825
*<br>程式作者：novia
*<br>--------------------------------------------------------
*<br>修改作者　　修改日期　　　修改目的
*<br>clive.chang 0941219	Debug & Modify for Testing and autual running..
*<br>--------------------------------------------------------
*/

package unt.la;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import unt.ch.UNTCH_Tools;
import util.Common;
import util.Database;
import util.Datetime;
import TDlib_Simple.com.src.DBServerTools;
import TDlib_Simple.com.src.SQLCreator;

public class UNTLA002F extends UNTLA001Q{

	public UNTLA002F() {
		
	}
	
	public UNTLA002F(String caller) {
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

	private String enterOrg;
	private String ownership;
	private String caseNo;
	private String differenceKind;
	private String engineeringNo;
	private String caseSerialNo;
	private String propertyNo;
	private String serialNo;
	private String propertyName1;
	private String signNo;
	private String doorplate;
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
	private String originalArea;
	private String originalHoldNume;
	private String originalHoldDeno;
	private String originalHoldArea;
	private String area;
	private String holdNume;
	private String holdDeno;
	private String holdArea;
	private String originalBasis;
	private String originalDate;
	private String originalUnit;
	private String originalBV;
	private String originalNote;
	private String accountingTitle;
	private String bookUnit;
	private String bookValue;
	private String netUnit;
	private String netValue;
	private String fundsSource;
	private String fundsSource1;
	private String useSeparate;
	private String useKind;
	private String oriUseSeparate;
	private String oriUseKind;
	private String ownershipDate;
	private String ownershipCause;
	private String nonProof;
	private String proofDoc;
	private String ownershipNote;
	private String field;
	private String landRule;
	private String buyDate;
	private String sourceKind;
	private String sourceDate;
	private String sourceDoc;
	private String oldOwner;
	private String manageOrg;
	private String manageOrgName;
	private String useState;
	private String appraiseDate;
	private String notes1;
	private String useState1;
	private String mergeDivision;
	private String mergeDivisionBatch;
	private String oldPropertyNo;
	private String oldSerialNo;
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
	private String notes;
	private String editID;
	private String editDate;
	private String editTime;
	private String picture;

	private String propertyNoName;
	private String signNo1;
	private String signNo2;
	private String signNo3;
	private String signNo4;
	private String signNo5;
	
	public String getPicture() {
		return checkGet(picture);
	}
	public void setPicture(String picture) {
		this.picture = checkSet(picture);
	}
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
	public String getPropertyName1() {return checkGet(propertyName1);}
	public void setPropertyName1(String propertyName1) {this.propertyName1 = checkSet(propertyName1);}
	public String getSignNo() {return checkGet(signNo);}
	public void setSignNo(String signNo) {this.signNo = checkSet(signNo);}
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
	public String getNetUnit() {return checkGet(netUnit);}
	public void setNetUnit(String netUnit) {this.netUnit = checkSet(netUnit);}
	public String getNetValue() {return checkGet(netValue);}
	public void setNetValue(String netValue) {this.netValue = checkSet(netValue);}
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
	public String getBuyDate() {return checkGet(buyDate);}
	public void setBuyDate(String buyDate) {this.buyDate = checkSet(buyDate);}
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
	public String getUseState1() {return checkGet(useState1);}
	public void setUseState1(String useState1) {this.useState1 = checkSet(useState1);}
	public String getMergeDivision() {return checkGet(mergeDivision);}
	public void setMergeDivision(String mergeDivision) {this.mergeDivision = checkSet(mergeDivision);}
	public String getMergeDivisionBatch() {return checkGet(mergeDivisionBatch);}
	public void setMergeDivisionBatch(String mergeDivisionBatch) {this.mergeDivisionBatch = checkSet(mergeDivisionBatch);}
	public String getOldPropertyNo() {return checkGet(oldPropertyNo);}
	public void setOldPropertyNo(String oldPropertyNo) {this.oldPropertyNo = checkSet(oldPropertyNo);}
	public String getOldSerialNo() {return checkGet(oldSerialNo);}
	public void setOldSerialNo(String oldSerialNo) {this.oldSerialNo = checkSet(oldSerialNo);}
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
	public String getNotes() {return checkGet(notes);}
	public void setNotes(String notes) {this.notes = checkSet(notes);}
	public String getEditID() {return checkGet(editID);}
	public void setEditID(String editID) {this.editID = checkSet(editID);}
	public String getEditDate() {return checkGet(editDate);}
	public void setEditDate(String editDate) {this.editDate = checkSet(editDate);}
	public String getEditTime() {return checkGet(editTime);}
	public void setEditTime(String editTime) {this.editTime = checkSet(editTime);}
	public String getPropertyNoName() {return checkGet(propertyNoName);}
	public void setPropertyNoName(String propertyNoName) {propertyNoName = checkSet(propertyNoName);}
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
	public String getManageOrgName() {return checkGet(manageOrgName);}
	public void setManageOrgName(String manageOrgName) {this.manageOrgName = checkSet(manageOrgName);}
	
	
	private String engineeringNoName;
	private String place1Name;
	private String originalPlace1Name;
	public String getEngineeringNoName() {return checkGet(engineeringNoName);}
	public void setEngineeringNoName(String engineeringNoName) {this.engineeringNoName = checkSet(engineeringNoName);}
	public String getPlace1Name() {return checkGet(place1Name);}
	public void setPlace1Name(String place1Name) {this.place1Name = checkSet(place1Name);}
	public String getOriginalPlace1Name() {return checkGet(originalPlace1Name);}
	public void setOriginalPlace1Name(String originalPlace1Name) {this.originalPlace1Name = checkSet(originalPlace1Name);}

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

//傳回執行insert前之檢查sql
protected String[][] getInsertCheckSQL(){
	String[][] checkSQLArray = new String[4][4];	
	
//	setDataState("1");
//	setArea(getOriginalArea());
//	setHoldNume(originalHoldNume);
//	setHoldDeno(originalHoldDeno);
//	setHoldArea(originalHoldArea);
//	setBookUnit(originalUnit);
//	setBookValue(originalBV);
		
	//取得分號
	Database db = new Database();
	ResultSet rs;	
	String sql="select (max(serialNo) + 1) as serialNo from UNTLA_LAND " +
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
		
		sql = " select ownership, verify from untla_addproof where 1=1 " +
			" and enterOrg = " + Common.sqlChar(enterOrg) +
			" and ownership = " + Common.sqlChar(ownership) +
			" and caseNo = " + Common.sqlChar(caseNo) +
			" and differenceKind = " + Common.sqlChar(differenceKind) +
			"";
		rs = db.querySQL(sql);
		if (rs.next()) {
			setOwnership(rs.getString("ownership"));
			setVerify(rs.getString("verify"));
		} else {
//			checkSQLArray[3][0]=" select 222 as checkResult from untla_land";
//			checkSQLArray[3][1]=">";
//			checkSQLArray[3][2]="1";
//			checkSQLArray[3][3]="查無此增加單編號，請重新輸入！";			
		}
		rs.close();
	} catch (Exception e) {
		e.printStackTrace();
	} finally {
		db.closeAll();
	}
	

 	checkSQLArray[0][0]=" select count(*) as checkResult from UNTLA_Land where 1=1 " + 
		" and enterOrg = " + Common.sqlChar(enterOrg) + 
		" and ownership = " + Common.sqlChar(ownership) + 
		" and propertyNo = " + Common.sqlChar(propertyNo) + 
		" and serialNo = " + Common.sqlChar(serialNo) + 
		" and differenceKind = " + Common.sqlChar(differenceKind) +
		"";
	checkSQLArray[0][1]=">";
	checkSQLArray[0][2]="0";
	checkSQLArray[0][3]="該筆資料己重複，請重新輸入！";

	//公告年月檢查
	if("1".equals(originalBasis) || "2".equals(originalBasis)){
	 	checkSQLArray[1][0]=" select count(*) as checkResult from UNTLA_BulletinDate where 1=1 " + 
			" and bulletinKind = " + Common.sqlChar(originalBasis) +  
			" and bulletinDate = " + Common.sqlChar(originalDate) +  	
			""; 	
		checkSQLArray[1][1]="<=";
		checkSQLArray[1][2]="0";
		checkSQLArray[1][3]="公告年月錯誤，請重新輸入！";	
	} else {
		checkSQLArray[1][0]="";
		checkSQLArray[1][1]="";
		checkSQLArray[1][2]="";
		checkSQLArray[1][3]="";		
	}
	

	
	if("0".equals(this.getHoldNume())){
		checkSQLArray[2][0]="";
		checkSQLArray[2][1]="";
		checkSQLArray[2][2]="";
		checkSQLArray[2][3]="";
		
		checkSQLArray[3][0]="";
		checkSQLArray[3][1]="";
		checkSQLArray[3][2]="";
		checkSQLArray[3][3]="";
	}else{
		checkSQLArray[2][0] = " select count(*) as checkResult from UNTLA_LAND where 1=1 "
					+ " and enterOrg != " + Common.sqlChar(enterOrg)
					+ " and holdnume / holddeno = 1 "
					+ " and ownership = " + Common.sqlChar(ownership)
					+ " and differenceKind = " + Common.sqlChar(differenceKind)
					+ " and signNo = " + Common.sqlChar(signNo)
					+ " and dataState = '1'"
					+ " and verify = 'Y'"
					+ " and ownership != '6'"
					+ "";
		
		checkSQLArray[2][1]=">";
		checkSQLArray[2][2]="0";
		checkSQLArray[2][3]="該筆地號已有其他單位持份1/1，請洽系統管理人員釐清產籍資料!!";

	//標示不能重複
	 	checkSQLArray[3][0]=" select count(*) as checkResult from UNTLA_LAND where 1=1 " + 
			" and enterOrg = " + Common.sqlChar(enterOrg) + 
			" and ownership = " + Common.sqlChar(ownership) + 
			" and differenceKind = " + Common.sqlChar(differenceKind) +
			" and signNo = " + Common.sqlChar(signNo) +
			" and dataState = " + Common.sqlChar(dataState) +
			""; 
	 	
		checkSQLArray[3][1]=">";
		checkSQLArray[3][2]="0";
		checkSQLArray[3][3]="土地標示己重複，請重新輸入！";
	}
	
	return checkSQLArray;
}
		
		private Map getRecordMap(){
			Map map = new HashMap();
			UNTCH_Tools ul = new UNTCH_Tools();
			if (!"UNTCH001F02_2".equals(caller)) { // 財產維護不給異動以下欄位, 以免被清空{
				map.put("originalDate", ul._transToCE_Year(getOriginalDate()));
				map.put("bookValue", getBookValue());
				map.put("netValue", getNetValue());
				map.put("ownershipDate", ul._transToCE_Year(getOwnershipDate()));
				map.put("nonProof", getNonProof());
				map.put("proofDoc", getProofDoc());
				map.put("landRule", getLandRule());
				map.put("enterOrg", getEnterOrg());
				map.put("ownership", getOwnership());
				map.put("caseNo", getCaseNo());
				map.put("differenceKind", getDifferenceKind());
				map.put("engineeringNo", getEngineeringNo());
				map.put("caseSerialNo", getCaseSerialNo());
				map.put("propertyNo", getPropertyNo());
				map.put("serialNo", getSerialNo());
				map.put("signNo", getSignNo());
				map.put("doorplate", getDoorplate());
				map.put("cause", getCause());
				map.put("cause1", getCause1());
				map.put("enterDate", ul._transToCE_Year(getEnterDate()));
				map.put("dataState", getDataState());
				map.put("reduceDate", ul._transToCE_Year(getReduceDate()));
				map.put("reduceCause", getReduceCause());
				map.put("reduceCause1", getReduceCause1());
				map.put("verify", getVerify());
				map.put("propertyKind", getPropertyKind());
				map.put("fundType", getFundType());
				map.put("valuable", getValuable());
				map.put("taxCredit", getTaxCredit());
				map.put("originalArea", getOriginalArea());
				map.put("originalHoldNume", getOriginalHoldNume());
				map.put("originalHoldDeno", getOriginalHoldDeno());
				map.put("originalHoldArea", getOriginalHoldArea());
				map.put("area", getArea());
				map.put("holdNume", getHoldNume());
				map.put("holdDeno", getHoldDeno());
				map.put("holdArea", getHoldArea());
				map.put("originalBasis", getOriginalBasis());
				map.put("originalUnit", getOriginalUnit());
				map.put("originalBV", getOriginalBV());
				map.put("originalNote", getOriginalNote());
				map.put("accountingTitle", getAccountingTitle());
				map.put("bookUnit", getBookUnit());
				map.put("netUnit", getNetUnit());
				map.put("fundsSource1", getFundsSource1());
				map.put("useSeparate", getUseSeparate());
				map.put("useKind", getUseKind());
				map.put("oriUseSeparate", getOriUseSeparate());
				map.put("oriUseKind", getOriUseKind());
				map.put("ownershipCause", getOwnershipCause());
				map.put("ownershipNote", getOwnershipNote());
				map.put("field", getField());
				map.put("buyDate", ul._transToCE_Year(getBuyDate()));
				map.put("sourceKind", getSourceKind());
				map.put("sourceDate", ul._transToCE_Year(getSourceDate()));
				map.put("sourceDoc", getSourceDoc());
				map.put("oldOwner", getOldOwner());
				map.put("manageOrg", getManageOrg());
				map.put("useState", getUseState());
				map.put("appraiseDate", ul._transToCE_Year(getAppraiseDate()));
				map.put("notes1", getNotes1());
				map.put("useState1", getUseState1());
				map.put("mergeDivision", getMergeDivision());
				map.put("mergeDivisionBatch", getMergeDivisionBatch());
				map.put("oldPropertyNo", getOldPropertyNo());
				map.put("oldSerialNo", getOldSerialNo());
				map.put("originalMoveDate", ul._transToCE_Year(getOriginalMoveDate()));
				map.put("originalKeepUnit", getOriginalKeepUnit());
				map.put("originalKeeper", getOriginalKeeper());
				map.put("originalUseUnit", getOriginalUseUnit());
				map.put("originalUser", getOriginalUser());
				map.put("originalUserNote", getOriginalUserNote());
				map.put("originalPlace1", getOriginalPlace1());
				map.put("originalPlace", getOriginalPlace());
				map.put("moveDate", ul._transToCE_Year(getMoveDate()));
				map.put("keepUnit", getKeepUnit());
				map.put("keeper", getKeeper());
				map.put("useUnit", getUseUnit());
				map.put("userNo", getUserNo());
			}
			//財產查詢僅可異動以下欄位
			map.put("propertyName1", getPropertyName1());
			map.put("fundsSource", getFundsSource());
			map.put("notes", getNotes());
			map.put("userNote", getUserNote());
			map.put("place1", getPlace1());
			map.put("place", getPlace());
			map.put("picture", getPicture());
			map.put("editID", getEditID());
			map.put("editDate", ul._transToCE_Year(getEditDate()));
			map.put("editTime", getEditTime());
			
			return map;
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
		
//傳回insert sql
protected String[] getInsertSQL(){
	String[] execSQLArray;
	
	if("1".equals(getOriginalBasis())){		execSQLArray = new String[2];
	}else{									execSQLArray = new String[1];
	}
	
	execSQLArray[0] = new SQLCreator()._obtainSQLforInsert("UNTLA_LAND", getPKMap(), getRecordMap());
	
	if("1".equals(getOriginalBasis())){
		execSQLArray[1] = "insert into UNTLA_Value (" +
							" enterOrg," +
							" ownership," +
							" propertyNo," +
							" serialNo," +
							" bulletinDate," +
							" valueUnit," +
							" suitDateS," +
							" suitDateE," +
							" notes," +
							" editID," +
							" editDate," +
							" editTime," +
							" differenceKind," +
							" engineeringNo," +
							" caseSerialNo," +
							" netUnit," +
							" netValue," +
							" buyDate," +
							" originalUserNote," +
							" originalPlace1," +
							" userNote," +
							" place1" +
						")values(" +
							Common.sqlChar(getEnterOrg()) + "," +
							Common.sqlChar(getOwnership()) + "," +
							Common.sqlChar(getPropertyNo()) + "," +
							Common.sqlChar(getSerialNo()) + "," +
							Common.sqlChar(new UNTCH_Tools()._transToCE_Year(getOriginalDate())) + "," +
							Common.sqlChar(new UNTCH_Tools()._transToCE_Year(getOriginalUnit())) + "," +
							"(select a.suitDateS from UNTLA_BulletinDate a where a.bulletinKind='1' and a.bulletinDate = '" + this.getOriginalDate() + "')," + 
							"(select a.suitDateE from UNTLA_BulletinDate a where a.bulletinKind='1' and a.bulletinDate = '" + this.getOriginalDate() + "')," +
							"null," + 
							Common.sqlChar(getEditID()) + "," +
							Common.sqlChar(new UNTCH_Tools()._transToCE_Year(getEditDate())) + "," +
							Common.sqlChar(getEditTime()) + "," +
							Common.sqlChar(getDifferenceKind()) + "," +
							Common.sqlChar(getEngineeringNo()) + "," +
							Common.sqlChar(getCaseSerialNo()) + "," +
							Common.sqlChar(getNetUnit()) + "," +
							Common.sqlChar(getNetValue()) + "," +
							Common.sqlChar(new UNTCH_Tools()._transToCE_Year(getBuyDate())) + "," +
							Common.sqlChar(getOriginalUserNote()) + "," +
							Common.sqlChar(getOriginalPlace1()) + "," +
							Common.sqlChar(getUserNote()) + "," +
							Common.sqlChar(getPlace1()) + 
						")";	
	}
	
	
	return execSQLArray;
}

//傳回執行Update前之檢查sql
protected String[][] getUpdateCheckSQL(){
    String[][] checkSQLArray = new String[3][4];
	//標示不能重複
 	checkSQLArray[0][0]=" select count(*) as checkResult from UNTLA_LAND where 1=1 " + 
		" and enterOrg = " + Common.sqlChar(enterOrg) + 
		" and ownership = " + Common.sqlChar(ownership) +
		" and differenceKind = " + Common.sqlChar(differenceKind) +
		" and signNo = " + Common.sqlChar(signNo) +
		" and dataState = " + Common.sqlChar(dataState) +
		""; 	
	checkSQLArray[0][1]=">";
	checkSQLArray[0][2]="1";
	checkSQLArray[0][3]="土地標示己重複，請重新輸入！";	
	
	checkSQLArray[1][0]="";
	checkSQLArray[1][1]="";
	checkSQLArray[1][2]="";
	checkSQLArray[1][3]="";	
	
	Database db = new Database();
	try {
		String sql = " select ownership, verify from untla_addproof where 1=1 " +
		" and enterOrg = " + Common.sqlChar(enterOrg) +
		" and ownership = " + Common.sqlChar(ownership) +
		" and caseNo = " + Common.sqlChar(caseNo) +
		" and differenceKind = " + Common.sqlChar(differenceKind) +
		"";
		ResultSet rs = db.querySQL(sql);
		if (rs.next()) {
			setOwnership(rs.getString("ownership"));
			setVerify(rs.getString("verify"));
		} else {
//			checkSQLArray[1][0]=" select 222 as checkResult from untla_land ";
//			checkSQLArray[1][1]=">";
//			checkSQLArray[1][2]="1";
//			checkSQLArray[1][3]="查無此增加單編號，請重新輸入！";			
		}		    				
	} catch (Exception e) {
		e.printStackTrace();
	} finally {
		db.closeAll();
	}

	checkSQLArray[2][0] = " select count(*) as checkResult from UNTLA_LAND where 1=1 "
			+ " and enterOrg != " + Common.sqlChar(enterOrg)
			+ " and holdnume / holddeno = 1 "
			+ " and ownership = " + Common.sqlChar(ownership)
			+ " and differenceKind = " + Common.sqlChar(differenceKind)
			+ " and signNo = " + Common.sqlChar(signNo)
			+ " and dataState = '1'"
			+ " and verify = 'Y'"
			+ " and ownership != '6'"
			+ "";
	
	checkSQLArray[2][1]=">";
	checkSQLArray[2][2]="0";
	checkSQLArray[2][3]="該筆地號已有其他單位持份1/1，請洽系統管理人員釐清產籍資料!!";
	
		
	return checkSQLArray;
}

//傳回update sql
protected String[] getUpdateSQL(){
	String[] execSQLArray = new String[1];
	
	if("".equals(getSignNo())){
		signNo = signNo3 + signNo4 + signNo5;
	}
	execSQLArray[0] = new SQLCreator()._obtainSQLforUpdate("UNTLA_LAND", getPKMap(), getRecordMap());
	
	return execSQLArray;
}


//傳回執行Delete前之檢查sql
protected String[][] getDeleteCheckSQL(){
    String[][] checkSQLArray = new String[1][4];
    
 	checkSQLArray[0][0]=" select count(*) as checkResult from UNTLA_AddProof where verify='Y' " + 
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

	sbSQL.append(" delete from UNTLA_Person " ).append(
	" where enterOrg = " ).append( Common.sqlChar(enterOrg) ).append(
	" and ownership = " ).append( Common.sqlChar(ownership) ).append(
	" and propertyNo = " ).append( Common.sqlChar(propertyNo) ).append(
	" and serialNo = " ).append( Common.sqlChar(serialNo) ).append(
	":;:");
	
	sbSQL.append(" delete from UNTLA_Manage " ).append(
	" where enterOrg = " ).append( Common.sqlChar(enterOrg) ).append(
	" and ownership = " ).append( Common.sqlChar(ownership) ).append(
	" and propertyNo = " ).append( Common.sqlChar(propertyNo) ).append(
	" and serialNo = " ).append( Common.sqlChar(serialNo) ).append(
	" and differenceKind = " ).append( Common.sqlChar(differenceKind) ).append(
	":;:");
	
	sbSQL.append(" delete from UNTLA_Attachment " ).append(
	" where enterOrg = " ).append( Common.sqlChar(enterOrg) ).append(
	" and ownership = " ).append( Common.sqlChar(ownership) ).append(
	" and propertyNo = " ).append( Common.sqlChar(propertyNo) ).append(
	" and serialNo = " ).append( Common.sqlChar(serialNo) ).append(
	" and differenceKind = " ).append( Common.sqlChar(differenceKind) ).append(
	":;:");
	
	sbSQL.append(" delete from UNTLA_Value " ).append(
	" where enterOrg = " ).append( Common.sqlChar(enterOrg) ).append(
	" and ownership = " ).append( Common.sqlChar(ownership) ).append(
	" and propertyNo = " ).append( Common.sqlChar(propertyNo) ).append(
	" and serialNo = " ).append( Common.sqlChar(serialNo) ).append(
	" and differenceKind = " ).append( Common.sqlChar(differenceKind) ).append(
	":;:");
	
	sbSQL.append(" delete from UNTLA_Price " ).append(
	" where enterOrg = " ).append( Common.sqlChar(enterOrg) ).append(
	" and ownership = " ).append( Common.sqlChar(ownership) ).append(
	" and propertyNo = " ).append( Common.sqlChar(propertyNo) ).append(
	" and serialNo = " ).append( Common.sqlChar(serialNo) ).append(
	" and differenceKind = " ).append( Common.sqlChar(differenceKind) ).append(
	":;:");									
	
	sbSQL.append(" delete from UNTLA_ViewScene " ).append(
	" where enterOrg = " ).append( Common.sqlChar(enterOrg) ).append(
	" and ownership = " ).append( Common.sqlChar(ownership) ).append(
	" and propertyNo = " ).append( Common.sqlChar(propertyNo) ).append(
	" and serialNo = " ).append( Common.sqlChar(serialNo) ).append(
	":;:");
	
	sbSQL.append(" delete from UNTLA_Tax " ).append(
	" where enterOrg = " ).append( Common.sqlChar(enterOrg) ).append(
	" and ownership = " ).append( Common.sqlChar(ownership) ).append(
	" and propertyNo = " ).append( Common.sqlChar(propertyNo) ).append(
	" and serialNo = " ).append( Common.sqlChar(serialNo) ).append(
	":;:");

	sbSQL.append(" delete from UNTLA_DrawProof " ).append(
	" where enterOrg = " ).append( Common.sqlChar(enterOrg) ).append(
	" and ownership = " ).append( Common.sqlChar(ownership) ).append(
	" and propertyNo = " ).append( Common.sqlChar(propertyNo) ).append(
	" and serialNo = " ).append( Common.sqlChar(serialNo) ).append(
	":;:");					

	sbSQL.append(" delete UNTLA_Land where 1=1 " ).append(
		" and enterOrg = " ).append( Common.sqlChar(enterOrg) ).append(
		" and ownership = " ).append( Common.sqlChar(ownership) ).append(
		" and propertyNo = " ).append( Common.sqlChar(propertyNo) ).append(
		" and serialNo = " ).append( Common.sqlChar(serialNo) ).append(
		" and differenceKind = " ).append( Common.sqlChar(differenceKind) ).append(
		":;:");

	sbSQL.append(" delete UNTLA_Right where 1=1 " ).append(
	" and enterOrg = " ).append( Common.sqlChar(enterOrg) ).append(
	" and ownership = " ).append( Common.sqlChar(ownership) ).append(
	" and propertyNo = " ).append( Common.sqlChar(propertyNo) ).append(
	" and serialNo = " ).append( Common.sqlChar(serialNo) ).append(
	" and differenceKind = " ).append( Common.sqlChar(differenceKind) ).append(
	":;:");

	sbSQL.append(" delete UNTLA_Use where 1=1 " ).append(
	" and enterOrg = " ).append( Common.sqlChar(enterOrg) ).append(
	" and ownership = " ).append( Common.sqlChar(ownership) ).append(
	" and propertyNo = " ).append( Common.sqlChar(propertyNo) ).append(
	" and serialNo = " ).append( Common.sqlChar(serialNo) ).append(
	" and differenceKind = " ).append( Common.sqlChar(differenceKind) ).append(
	":;:");
	
	return sbSQL.toString().split(":;:");
}


/**
 * <br>
 * <br>目的：依主鍵查詢單一資料
 * <br>參數：無
 * <br>傳回：傳回本物件
*/

public UNTLA002F  queryOne() throws Exception{
	Database db = new Database();
	UNTLA002F obj = this;
	try {
		StringBuffer sbSQL = new StringBuffer().append(" select " ).append(
			"(select b.organSName from SYSCA_Organ b where 1=1 and b.organID = a.enterOrg ) as organSName," ).append(
			"a.enterOrg, a.ownership, a.caseNo, " ).append(
			"a.propertyNo, a.serialNo, (select x.propertyName from SYSPK_PropertyCode x where a.propertyNo = x.propertyNo ) as propertyName, a.propertyName1, a.signNo , " ).append(
			"a.doorplate, a.cause, a.cause1, a.enterDate, a.dataState, a.reduceDate, " ).append(
			"a.reduceCause, a.reduceCause1, a.verify, a.propertyKind, " ).append(
			"a.fundType, a.valuable, a.taxCredit, a.originalArea, " ).append(
			"a.originalHoldNume, a.originalHoldDeno, a.originalHoldArea, a.area, " ).append(
			"a.holdNume, a.holdDeno, a.holdArea, a.originalBasis, a.originalDate, " ).append(
			"a.originalUnit, a.originalBV, a.originalNote, a.accountingTitle, " ).append(
			"a.bookUnit, a.bookValue, a.fundsSource, a.fundsSource1, a.useSeparate, " ).append(
					
			"a.useKind, a.engineeringNo, a.caseSerialNo, a.netUnit, a.netValue, " ).append(
			"a.buyDate, a.originalUserNote, a.userNote, a.place1, " ).append(
			"a.oriUseSeparate, a.oriUseKind, a.ownershipDate, a.ownershipCause, " ).append(
			"a.nonProof, a.proofDoc, a.ownershipNote, a.field, " ).append(
			"a.landRule, a.sourceKind, a.sourceDate, a.sourceDoc, a.oldOwner, " ).append(
			"a.manageOrg, " ).append(
			"(select codename from SYSCA_CODE z where codekindid = 'SKD' and z.codeid = a.sourcekind) as sourceKindName, " ).append(
			"(select codename from SYSCA_CODE z where codekindid = 'CAA' and z.codeid = a.cause and codecon1 in (1,4)) as causeName, " ).append(
			"(select d.organSName from SYSCA_Organ d where 1=1 and d.organID = a.manageOrg ) as manageOrgName, " ).append(
			"a.useState, a.useState1, " ).append(
			"a.appraiseDate, a.notes1, a.notes, a.oldPropertyNo, a.oldSerialNo, e.propertyName as oldPropertyName," ).append(
			" a.editID, a.editDate, a.editTime,  ").append(
			" a.differenceKind, a.engineeringNo, a.caseSerialNo, a.netUnit, a.netValue,").append(
			" a.buyDate, a.originalMoveDate, a.originalKeepUnit, a.originalKeeper, a.originalUseUnit,").append(
			" a.originalUser, a.originalUserNote, a.originalPlace1, a.originalPlace, a.moveDate,").append(
			" a.keepUnit, a.keeper, a.useUnit, a.userNo, a.userNote,").append(
			" a.place1, a.place,").append(
			" (select placename from SYSCA_PLACE z where z.enterorg = a.enterorg and z.placeno = a.originalPlace1) as originalPlace1Name,").append(
			" (select placename from SYSCA_PLACE z where z.enterorg = a.enterorg and z.placeno = a.place1) as place1Name").append(	
			" ,a.picture from UNTLA_Land a " ).append(
			" left join SYSPK_PropertyCode e " ).append(
			" on a.oldPropertyNo = e.propertyNo " ).append(
			" where 1=1 " ).append(
			" and a.enterOrg = " ).append( Common.sqlChar(enterOrg) ).append(
			" and a.ownership = " ).append( Common.sqlChar(ownership) ).append(
			("".equals(checkGet(getCaseNo()))?"":" and a.caseNo = " + Common.sqlChar(caseNo))).append(
			" and a.propertyNo = " ).append( Common.sqlChar(propertyNo) ).append(
			" and a.serialNo = " ).append( Common.sqlChar(serialNo) ).append(			
			" and a.differenceKind = " ).append( Common.sqlChar(differenceKind) ).append(
			"");
		
//		System.out.println("-- untla002f queryOne --\n"+sbSQL.toString());
		UNTCH_Tools ul = new UNTCH_Tools();
		ResultSet rs = db.querySQL(sbSQL.toString());
		if (rs.next()){
			obj.setPicture(rs.getString("picture"));
			obj.setEnterOrg(rs.getString("enterOrg"));
			obj.setOwnership(rs.getString("ownership"));
			obj.setEnterOrgName(rs.getString("organSName"));
			obj.setCaseNo(rs.getString("caseNo"));
			obj.setDifferenceKind(rs.getString("differencekind"));			
			obj.setPropertyNo(rs.getString("propertyNo"));	
			obj.setPropertyNoName(rs.getString("propertyName"));			
			obj.setSerialNo(rs.getString("serialNo"));			
			obj.setPropertyName1(rs.getString("propertyName1"));
			if(rs.getString("signNo") != null && rs.getString("signNo").length() == 15){
				obj.setSignNo1(rs.getString("signNo").substring(0,1)+"000000");
				obj.setSignNo2(rs.getString("signNo").substring(0,3)+"0000");
				obj.setSignNo3(rs.getString("signNo").substring(0,7));
				obj.setSignNo4(rs.getString("signNo").substring(7,11));
				obj.setSignNo5(rs.getString("signNo").substring(11));
			}
			obj.setDoorplate(rs.getString("doorplate"));
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
			obj.setOriginalArea(rs.getDouble("originalArea")+"");
			obj.setOriginalHoldNume(rs.getString("originalHoldNume"));
			obj.setOriginalHoldDeno(rs.getString("originalHoldDeno"));
			obj.setOriginalHoldArea(rs.getDouble("originalHoldArea")+"");
			obj.setArea(rs.getDouble("area")+"");
			obj.setHoldNume(rs.getString("holdNume"));
			obj.setHoldDeno(rs.getString("holdDeno"));
			obj.setHoldArea(rs.getDouble("holdArea")+"");
			obj.setOriginalBasis(rs.getString("originalBasis"));
			obj.setOriginalDate(ul._transToROC_Year(rs.getString("originalDate")));
			obj.setOriginalUnit(rs.getString("originalUnit"));
			obj.setOriginalBV(rs.getString("originalBV"));
			obj.setOriginalNote(rs.getString("originalNote"));
			obj.setAccountingTitle(rs.getString("accountingTitle"));
			obj.setBookUnit(rs.getString("bookUnit"));
			obj.setBookValue(rs.getString("bookValue"));
			obj.setFundsSource(rs.getString("fundsSource"));
			obj.setFundsSource1(rs.getString("fundsSource1"));
			obj.setUseSeparate(rs.getString("useSeparate"));
			obj.setUseKind(rs.getString("useKind"));
			obj.setOriUseSeparate(rs.getString("oriUseSeparate"));
			obj.setOriUseKind(rs.getString("oriUseKind"));
			obj.setOwnershipDate(ul._transToROC_Year(rs.getString("ownershipDate")));
			obj.setOwnershipCause(rs.getString("ownershipCause"));
			obj.setNonProof(rs.getString("nonProof"));
			obj.setProofDoc(rs.getString("proofDoc"));
			obj.setOwnershipNote(rs.getString("ownershipNote"));
			obj.setField(rs.getString("field"));
			obj.setLandRule(rs.getString("landRule"));
			obj.setSourceKind(rs.getString("sourceKind"));
			obj.setSourceKindName(rs.getString("sourceKindName"));
			obj.setSourceDate(ul._transToROC_Year(rs.getString("sourceDate")));
			obj.setSourceDoc(rs.getString("sourceDoc"));
			obj.setOldOwner(rs.getString("oldOwner"));
			obj.setManageOrg(rs.getString("manageOrg"));
			obj.setManageOrgName(rs.getString("manageOrgName"));
			obj.setUseState(rs.getString("useState"));
			obj.setUseState1(rs.getString("useState1"));
			obj.setAppraiseDate(ul._transToROC_Year(rs.getString("appraiseDate")));
			obj.setNotes1(rs.getString("notes1"));
			obj.setNotes(rs.getString("notes"));
			obj.setOldPropertyNo(rs.getString("oldPropertyNo"));
			obj.setOldSerialNo(rs.getString("oldSerialNo"));
			obj.setEditID(rs.getString("editID"));
			obj.setEditDate(ul._transToROC_Year(rs.getString("editDate")));
			obj.setEditTime(rs.getString("editTime"));
			obj.setEngineeringNo(rs.getString("engineeringNo"));
			obj.setEngineeringNoName(ul._getEngineeringNoName(rs.getString("enterOrg"),rs.getString("engineeringNo")));
			obj.setCaseSerialNo(rs.getString("caseSerialNo"));
			obj.setNetUnit(rs.getString("netUnit"));
			obj.setNetValue(rs.getString("netValue"));
			obj.setBuyDate(ul._transToROC_Year(rs.getString("buyDate")));
			obj.setOriginalUserNote(rs.getString("originalUserNote"));
			obj.setOriginalPlace1(rs.getString("originalPlace1"));
			obj.setOriginalPlace(rs.getString("originalPlace"));
			obj.setUserNote(rs.getString("userNote"));
			obj.setPlace1(rs.getString("place1"));
			obj.setPlace(rs.getString("place"));
			obj.setOriginalPlace1Name(rs.getString("originalPlace1Name"));
			obj.setPlace1Name(rs.getString("place1Name"));
			obj.setKeeper(rs.getString("keeper"));
			obj.setKeepUnit(rs.getString("keepUnit"));
			obj.setUserNo(rs.getString("userNo"));
			obj.setUseUnit(rs.getString("UseUnit"));
			obj.setOriginalKeeper(rs.getString("originalKeeper"));
			obj.setOriginalKeepUnit(rs.getString("Originalkeepunit"));
			obj.setOriginalUser(rs.getString("Originaluser"));
			obj.setOriginalUseUnit(rs.getString("OriginalUseUnit"));
				
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
		String sql = " select distinct a.enterOrg ,a.ownership, a.differenceKind " + "\n";
		
		if ("".equals(getQ_enterOrg())) {
				sql+=" ,a.caseNo , (select z.organsname from sysca_organ z where z.organid=a.enterOrg) as organSName";
		}else{
				sql+=" ,a.caseNo ";
		}
				sql+= " ,(select codename from sysca_code x where x.codekindid = 'OWA' and x.codeid = a.ownership ) as ownershipName " + "\n"
					+ " ,a.propertyNo ,a.serialNo , " 
				   	+ " a.signNo "
				   	+ " ,(select c.codeName from SYSCA_CODE c where c.codekindid = 'CAA' and c.codeid = a.cause) as cause "
				   	+ " ,(case a.dataState when '1' then '現存' when '2' then '已減損' else '' end) dataState  "
				   	+ " ,(case a.propertyKind when '01' then '公務用' when '02' then '公共用' when '03' then '事業用' when '04' then '非公用' else '' end) propertyKind "
				   	+ " ,a.signNo, a.holdarea "
				   	+ " from UNTLA_Land a , UNTLA_AddProof e where 1=1"
				
					+ " and a.enterOrg = e.enterOrg and a.ownership = e.ownership and a.caseno = e.caseno and a.differenceKind = e.differenceKind"
					+ " and a.enterOrg=" + Common.sqlChar(getEnterOrg())
					+ " and a.ownership=" + Common.sqlChar(getOwnership())				
					+ " and a.caseNo = " + Common.sqlChar(getCaseNo())
					+ " and a.differenceKind = " + Common.sqlChar(getDifferenceKind())
					+ " and a.propertyNo = " + Common.sqlChar(getPropertyNo())
					+ " and a.serialNo = " + Common.sqlChar(getSerialNo());
		
		sql += " order by a.enterOrg ,a.ownership ,a.signNo";
		//System.out.println(sql);		
		UNTCH_Tools ucl = new UNTCH_Tools();
		ResultSet rs = db.querySQL(sql,true);
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
			
			String signdescName = "";
			if(rs.getString("signNo").length() == 15){
				signdescName = getSignDescName(rs.getString("signNo").substring(0,7)) + rs.getString("signNo").substring(7,11) + '-' + rs.getString("signNo").substring(11);
			}
			rowArray[7]=signdescName;
			rowArray[8]=rs.getString("cause"); 
			rowArray[9]=rs.getString("dataState");			
			rowArray[10]=rs.getString("holdarea");
			rowArray[11]="";
			rowArray[12]="";
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


