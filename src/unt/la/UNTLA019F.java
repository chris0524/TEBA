/*
*<br>程式目的：土地增減值作業－增減值單明細
*<br>程式代號：untla019f
*<br>程式日期：0940909
*<br>程式作者：judy
*<br>--------------------------------------------------------
*<br>修改作者　　修改日期　　　修改目的
*<br>--------------------------------------------------------
*/ 

package unt.la;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import TDlib_Simple.com.src.SQLCreator;
import unt.ch.UNTCH_Tools;
import util.Common;
import util.Database;

public class UNTLA019F extends UNTLA018Q{
private String enterOrg;
private String caseNo;
private String ownership;
private String differenceKind;
private String engineeringNo;
private String caseSerialNo;
private String propertyNo;
private String serialNo;
private String propertyName1;
private String signNo;
private String cause;
private String cause1;
private String bulletinDate;
private String adjustDate;
private String verify;
private String propertyKind;
private String fundType;
private String valuable;
private String taxCredit;
private String originalBV;
private String sourceDate;
private String buyDate;
private String oldArea;
private String oldHoldNume;
private String oldHoldDeno;
private String oldHoldArea;
private String oldBookUnit;
private String oldBookValue;
private String oldNetUnit;
private String oldNetValue;
private String newArea;
private String newHoldNume;
private String newHoldDeno;
private String newHoldArea;
private String newBookUnit;
private String newBookValue;
private String newNetUnit;
private String newNetValue;
private String adjustArea;
private String adjustHoldArea;
private String addBookUnit;
private String addBookValue;
private String addNetUnit;
private String addNetValue;
private String reduceBookUnit;
private String reduceBookValue;
private String reduceNetUnit;
private String reduceNetValue;
private String bookNotes;
private String changeItem;
private String notes1;
private String useSeparate;
private String useKind;
private String oldPropertyNo;
private String oldSerialNo;
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
private String enterOrgName;
private String propertyNoName;
private String signNo1;
private String signNo2;
private String signNo3;
private String signNo4;
private String signNo5;
private String adjustBookUnit;
private String adjustBookValue;
private String oldPropertyName;
private String closing1ym;

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
public String getPropertyName1() {return checkGet(propertyName1);}
public void setPropertyName1(String propertyName1) {this.propertyName1 = checkSet(propertyName1);}
public String getSignNo() {return checkGet(signNo);}
public void setSignNo(String signNo) {this.signNo = checkSet(signNo);}
public String getCause() {return checkGet(cause);}
public void setCause(String cause) {this.cause = checkSet(cause);}
public String getCause1() {return checkGet(cause1);}
public void setCause1(String cause1) {this.cause1 = checkSet(cause1);}
public String getBulletinDate() {return checkGet(bulletinDate);}
public void setBulletinDate(String bulletinDate) {this.bulletinDate = checkSet(bulletinDate);}
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
public String getOldArea() {return checkGet(oldArea);}
public void setOldArea(String oldArea) {this.oldArea = checkSet(oldArea);}
public String getOldHoldNume() {return checkGet(oldHoldNume);}
public void setOldHoldNume(String oldHoldNume) {this.oldHoldNume = checkSet(oldHoldNume);}
public String getOldHoldDeno() {return checkGet(oldHoldDeno);}
public void setOldHoldDeno(String oldHoldDeno) {this.oldHoldDeno = checkSet(oldHoldDeno);}
public String getOldHoldArea() {return checkGet(oldHoldArea);}
public void setOldHoldArea(String oldHoldArea) {this.oldHoldArea = checkSet(oldHoldArea);}
public String getOldBookUnit() {return checkGet(oldBookUnit);}
public void setOldBookUnit(String oldBookUnit) {this.oldBookUnit = checkSet(oldBookUnit);}
public String getOldBookValue() {return checkGet(oldBookValue);}
public void setOldBookValue(String oldBookValue) {this.oldBookValue = checkSet(oldBookValue);}
public String getOldNetUnit() {return checkGet(oldNetUnit);}
public void setOldNetUnit(String oldNetUnit) {this.oldNetUnit = checkSet(oldNetUnit);}
public String getOldNetValue() {return checkGet(oldNetValue);}
public void setOldNetValue(String oldNetValue) {this.oldNetValue = checkSet(oldNetValue);}
public String getNewArea() {return checkGet(newArea);}
public void setNewArea(String newArea) {this.newArea = checkSet(newArea);}
public String getNewHoldNume() {return checkGet(newHoldNume);}
public void setNewHoldNume(String newHoldNume) {this.newHoldNume = checkSet(newHoldNume);}
public String getNewHoldDeno() {return checkGet(newHoldDeno);}
public void setNewHoldDeno(String newHoldDeno) {this.newHoldDeno = checkSet(newHoldDeno);}
public String getNewHoldArea() {return checkGet(newHoldArea);}
public void setNewHoldArea(String newHoldArea) {this.newHoldArea = checkSet(newHoldArea);}
public String getNewBookUnit() {return checkGet(newBookUnit);}
public void setNewBookUnit(String newBookUnit) {this.newBookUnit = checkSet(newBookUnit);}
public String getNewBookValue() {return checkGet(newBookValue);}
public void setNewBookValue(String newBookValue) {this.newBookValue = checkSet(newBookValue);}
public String getNewNetUnit() {return checkGet(newNetUnit);}
public void setNewNetUnit(String newNetUnit) {this.newNetUnit = checkSet(newNetUnit);}
public String getNewNetValue() {return checkGet(newNetValue);}
public void setNewNetValue(String newNetValue) {this.newNetValue = checkSet(newNetValue);}
public String getAdjustArea() {return checkGet(adjustArea);}
public void setAdjustArea(String adjustArea) {this.adjustArea = checkSet(adjustArea);}
public String getAdjustHoldArea() {return checkGet(adjustHoldArea);}
public void setAdjustHoldArea(String adjustHoldArea) {this.adjustHoldArea = checkSet(adjustHoldArea);}
public String getAddBookUnit() {return checkGet(addBookUnit);}
public void setAddBookUnit(String addBookUnit) {this.addBookUnit = checkSet(addBookUnit);}
public String getAddBookValue() {return checkGet(addBookValue);}
public void setAddBookValue(String addBookValue) {this.addBookValue = checkSet(addBookValue);}
public String getAddNetUnit() {return checkGet(addNetUnit);}
public void setAddNetUnit(String addNetUnit) {this.addNetUnit = checkSet(addNetUnit);}
public String getAddNetValue() {return checkGet(addNetValue);}
public void setAddNetValue(String addNetValue) {this.addNetValue = checkSet(addNetValue);}
public String getReduceBookUnit() {return checkGet(reduceBookUnit);}
public void setReduceBookUnit(String reduceBookUnit) {this.reduceBookUnit = checkSet(reduceBookUnit);}
public String getReduceBookValue() {return checkGet(reduceBookValue);}
public void setReduceBookValue(String reduceBookValue) {this.reduceBookValue = checkSet(reduceBookValue);}
public String getReduceNetUnit() {return checkGet(reduceNetUnit);}
public void setReduceNetUnit(String reduceNetUnit) {this.reduceNetUnit = checkSet(reduceNetUnit);}
public String getReduceNetValue() {return checkGet(reduceNetValue);}
public void setReduceNetValue(String reduceNetValue) {this.reduceNetValue = checkSet(reduceNetValue);}
public String getBookNotes() {return checkGet(bookNotes);}
public void setBookNotes(String bookNotes) {this.bookNotes = checkSet(bookNotes);}
public String getChangeItem() {return checkGet(changeItem);}
public void setChangeItem(String changeItem) {this.changeItem = checkSet(changeItem);}
public String getNotes1() {return checkGet(notes1);}
public void setNotes1(String notes1) {this.notes1 = checkSet(notes1);}
public String getUseSeparate() {return checkGet(useSeparate);}
public void setUseSeparate(String useSeparate) {this.useSeparate = checkSet(useSeparate);}
public String getUseKind() {return checkGet(useKind);}
public void setUseKind(String useKind) {this.useKind = checkSet(useKind);}
public String getOldPropertyNo() {return checkGet(oldPropertyNo);}
public void setOldPropertyNo(String oldPropertyNo) {this.oldPropertyNo = checkSet(oldPropertyNo);}
public String getOldSerialNo() {return checkGet(oldSerialNo);}
public void setOldSerialNo(String oldSerialNo) {this.oldSerialNo = checkSet(oldSerialNo);}
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
public String getEnterOrgName() {return checkGet(enterOrgName);}
public void setEnterOrgName(String enterOrgName) {this.enterOrgName = checkSet(enterOrgName);}
public String getPropertyNoName() {return checkGet(propertyNoName);}
public void setPropertyNoName(String propertyNoName) {this.propertyNoName = checkSet(propertyNoName);}
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
public String getAdjustBookUnit() {return checkGet(adjustBookUnit);}
public void setAdjustBookUnit(String adjustBookUnit) {this.adjustBookUnit = checkSet(adjustBookUnit);}
public String getAdjustBookValue() {return checkGet(adjustBookValue);}
public void setAdjustBookValue(String adjustBookValue) {this.adjustBookValue = checkSet(adjustBookValue);}
public String getOldPropertyName() {return checkGet(oldPropertyName);}
public void setOldPropertyName(String oldPropertyName) {this.oldPropertyName = checkSet(oldPropertyName);}

private String engineeringNoName;
public String getEngineeringNoName() {return checkGet(engineeringNoName);}
public void setEngineeringNoName(String engineeringNoName) {this.engineeringNoName = checkSet(engineeringNoName);}

String ownershipQuery;
public String getOwnershipQuery(){ return checkGet(ownershipQuery); }
public void setOwnershipQuery(String s){ ownershipQuery=checkSet(s); }

String closing;
public String getClosing(){ return checkGet(closing); }
public void setClosing(String s){ closing=checkSet(s); }

String q_bulletindate;
public String getQ_bulletindate(){ return checkGet(q_bulletindate); }	
public void setQ_bulletindate(String s){ q_bulletindate=checkSet(s); }
public String getClosing1ym() {return checkGet(closing1ym);}
public void setClosing1ym(String closing1ym) {this.closing1ym = checkSet(closing1ym);}

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
		map.put("PropertyName1", getPropertyName1());
		map.put("SignNo", getSignNo());
		map.put("Cause", getCause());
		map.put("Cause1", getCause1());
		map.put("BulletinDate", ul._transToCE_Year(getBulletinDate()));
		map.put("AdjustDate", ul._transToCE_Year(getAdjustDate()));
		map.put("Verify", getVerify());
		map.put("PropertyKind", getPropertyKind());
		map.put("FundType", getFundType());
		map.put("Valuable", getValuable());
		map.put("TaxCredit", getTaxCredit());
		map.put("OriginalBV", getOriginalBV());
		map.put("SourceDate", ul._transToCE_Year(getSourceDate()));
		map.put("BuyDate", ul._transToCE_Year(getBuyDate()));
		map.put("OldArea", getOldArea());
		map.put("OldHoldNume", getOldHoldNume());
		map.put("OldHoldDeno", getOldHoldDeno());
		map.put("OldHoldArea", getOldHoldArea());
		map.put("OldBookUnit", getOldBookUnit());
		map.put("OldBookValue", getOldBookValue());
		map.put("OldNetUnit", getOldNetUnit());
		map.put("OldNetValue", getOldNetValue());
		map.put("NewArea", getNewArea());
		map.put("NewHoldNume", getNewHoldNume());
		map.put("NewHoldDeno", getNewHoldDeno());
		map.put("NewHoldArea", getNewHoldArea());
		map.put("NewBookUnit", getNewBookUnit());
		map.put("NewBookValue", getNewBookValue());
		map.put("NewNetUnit", getNewNetUnit());
		map.put("NewNetValue", getNewNetValue());
		map.put("AdjustArea", getAdjustArea());
		map.put("AdjustHoldArea", getAdjustHoldArea());
		map.put("AddBookUnit", getAddBookUnit());
		map.put("AddBookValue", getAddBookValue());
		map.put("AddNetUnit", getAddNetUnit());
		map.put("AddNetValue", getAddNetValue());
		map.put("ReduceBookUnit", getReduceBookUnit());
		map.put("ReduceBookValue", getReduceBookValue());
		map.put("ReduceNetUnit", getReduceNetUnit());
		map.put("ReduceNetValue", getReduceNetValue());
		map.put("BookNotes", getBookNotes());
		map.put("ChangeItem", getChangeItem());
		map.put("Notes1", getNotes1());
		map.put("UseSeparate", getUseSeparate());
		map.put("UseKind", getUseKind());
		map.put("OldPropertyNo", getOldPropertyNo());
		map.put("OldSerialNo", getOldSerialNo());
		map.put("KeepUnit", getKeepUnit());
		map.put("Keeper", getKeeper());
		map.put("UseUnit", getUseUnit());
		map.put("UserNo", getUserNo());
		map.put("UserNote", getUserNote());
		map.put("Place1", getPlace1());
		map.put("Place", getPlace());
		map.put("Notes", getNotes());
		map.put("EditID", getEditID());
		map.put("EditDate", ul._transToCE_Year(getEditDate()));
		map.put("EditTime", getEditTime());
		
		return map;
	}

	
//傳回執行insert前之檢查sql
protected String[][] getInsertCheckSQL(){
	int count = 3;
	if("1".equals(cause) || "2".equals(cause)){
		count = 4;
	}
	
	String[][] checkSQLArray = new String[count][4];
 	checkSQLArray[0][0]=" select count(*) as checkResult from UNTLA_AdjustDetail where 1=1 " + 
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
	
 	checkSQLArray[1][0]=" select count(*) as checkResult from UNTLA_LAND where 1=1 " + 
						" and enterOrg = " + Common.sqlChar(enterOrg) + 
						" and ownership = " + Common.sqlChar(ownership) + 
						" and propertyNo = " + Common.sqlChar(propertyNo) + 
						" and differenceKind = " + Common.sqlChar(differenceKind) +
						" and serialNo = " + Common.sqlChar(serialNo) + 
						" and verify = 'Y' " +
						" and dataState = '1' " +
						"";
	checkSQLArray[1][1]="<=";
	checkSQLArray[1][2]="0";
	checkSQLArray[1][3]="土地主檔中找不到該財產編號和分號，請重新輸入！";
	
// 	checkSQLArray[2][0]=" select count(*) as checkResult from UNTLA_LAND where 1=1 " + 
//						" and enterOrg = " + Common.sqlChar(enterOrg) + 
//						" and ownership = " + Common.sqlChar(ownership) + 
//						" and differenceKind = " + Common.sqlChar(differenceKind) +
//						" and signNo = " + Common.sqlChar(signNo) + 	
//						" and verify = 'Y' " +
//						" and dataState = '1' " +
//						""; 	
//	checkSQLArray[2][1]="<=";
//	checkSQLArray[2][2]="0";
//	checkSQLArray[2][3]="土地主檔中找不到該筆土地標示資料，請重新輸入！";

 	checkSQLArray[2][0]=" select count(*) as checkResult from UNTLA_ReduceDetail where 1=1 " + 
						" and enterOrg = " + Common.sqlChar(enterOrg) + 
						" and ownership = " + Common.sqlChar(ownership) + 
						" and differenceKind = " + Common.sqlChar(differenceKind) +
						" and propertyNo = " + Common.sqlChar(propertyNo) + 
						" and serialNo = " + Common.sqlChar(serialNo) +
						" and verify = 'N' " +
						"";
	checkSQLArray[2][1]=">";
	checkSQLArray[2][2]="0";
	checkSQLArray[2][3]="減少作業存在未入帳的資料，請重新輸入!!";

	//公告年月檢查
	if("1".equals(cause) || "2".equals(cause)){
	 	checkSQLArray[4][0]=" select count(*) as checkResult from UNTLA_BulletinDate where 1=1 " + 
			" and bulletinKind = " + Common.sqlChar(cause) +  
			" and bulletinDate = " + Common.sqlChar(bulletinDate) +  	
			""; 	
		checkSQLArray[4][1]="<=";
		checkSQLArray[4][2]="0";
		checkSQLArray[4][3]="公告年月錯誤，請重新輸入！";	
	}
	
	return checkSQLArray;
}


//傳回insert sql
protected String[] getInsertSQL(){
	String[] execSQLArray = new String[1];
	execSQLArray[0] = new SQLCreator()._obtainSQLforInsert("UNTLA_ADJUSTDETAIL", getPKMap(), getRecordMap());
	return execSQLArray;
}

//傳回執行update前之檢查sql
protected String[][] getUpdateCheckSQL(){
	int i = 0;
	if("1".equals(cause) || "2".equals(cause)){
		i=1;
	}
	String[][] checkSQLArray = new String[i][4];
	//公告年月檢查
	if("1".equals(cause) || "2".equals(cause)){
		checkSQLArray[0][0]=" select count(*) as checkResult from UNTLA_BulletinDate where 1=1 " + 
			" and bulletinKind = " + Common.sqlChar(cause) +  
			" and bulletinDate = " + Common.sqlChar(bulletinDate) +  	
			""; 	
		checkSQLArray[0][1]="<=";
		checkSQLArray[0][2]="0";
		checkSQLArray[0][3]="公告年月錯誤，請重新輸入！";	
	}
	
	return checkSQLArray;
}

//傳回update sql
protected String[] getUpdateSQL(){
	String[] execSQLArray = new String[1];
	execSQLArray[0] = new SQLCreator()._obtainSQLforUpdate("UNTLA_ADJUSTDETAIL", getPKMap(), getRecordMap());
	return execSQLArray;
}


//傳回delete sql
protected String[] getDeleteSQL(){
	String[] execSQLArray = new String[1];
	execSQLArray[0] = new SQLCreator()._obtainSQLforDelete("UNTLA_ADJUSTDETAIL", getPKMap(), getRecordMap());
	return execSQLArray;
}


/**
 * <br>
 * <br>目的：依主鍵查詢單一資料
 * <br>參數：無
 * <br>傳回：傳回本物件
*/

public UNTLA019F  queryOne() throws Exception{
	Database db = new Database();
	UNTLA019F obj = this;
	
	try {
		String sql=" select a.*, " +
			" c.organsname as enterorgname, d.propertyname, " +
			" b.closing1ym " +
			" from UNTLA_ADJUSTDETAIL a inner join UNTAC_CLOSINGPT b on a.enterorg = b.enterorg and a.differencekind = b.differencekind , SYSCA_ORGAN c, "+
			" SYSPK_PROPERTYCODE d " +
			" where 1=1 " +
			" and a.enterorg 	= " + Common.sqlChar(enterOrg) +
			" and a.ownership 	= " + Common.sqlChar(ownership) +
			" and a.caseno 	= " + Common.sqlChar(caseNo) +
			" and a.differencekind 	= " + Common.sqlChar(differenceKind) +
			" and a.propertyno 	= " + Common.sqlChar(propertyNo) +
			" and a.serialno 	= " + Common.sqlChar(serialNo) +
			" and a.enterorg	= c.organid " +
			" and a.propertyno 	= d.propertyno "+
			" order by a.enterorg , a.ownership , a.signno "+
			"";
		
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
			obj.setPropertyName1(rs.getString("PropertyName1"));
			obj.setSignNo(rs.getString("SignNo"));
			obj.setCause(rs.getString("Cause"));
			obj.setCause1(rs.getString("Cause1"));
			obj.setBulletinDate(ul._transToROC_Year(rs.getString("BulletinDate")));
			obj.setAdjustDate(ul._transToROC_Year(rs.getString("AdjustDate")));
			obj.setVerify(rs.getString("Verify"));
			obj.setPropertyKind(rs.getString("PropertyKind"));
			obj.setFundType(rs.getString("FundType"));
			obj.setValuable(rs.getString("Valuable"));
			obj.setTaxCredit(rs.getString("TaxCredit"));
			obj.setOriginalBV(rs.getString("OriginalBV"));
			obj.setSourceDate(ul._transToROC_Year(rs.getString("SourceDate")));
			obj.setBuyDate(ul._transToROC_Year(rs.getString("BuyDate")));
			obj.setOldArea(rs.getString("OldArea"));
			obj.setOldHoldNume(rs.getString("OldHoldNume"));
			obj.setOldHoldDeno(rs.getString("OldHoldDeno"));
			obj.setOldHoldArea(rs.getString("OldHoldArea"));
			obj.setOldBookUnit(rs.getString("OldBookUnit"));
			obj.setOldBookValue(rs.getString("OldBookValue"));
			obj.setOldNetUnit(rs.getString("OldNetUnit"));
			obj.setOldNetValue(rs.getString("OldNetValue"));
			obj.setNewArea(rs.getString("NewArea"));
			obj.setNewHoldNume(rs.getString("NewHoldNume"));
			obj.setNewHoldDeno(rs.getString("NewHoldDeno"));
			obj.setNewHoldArea(rs.getString("NewHoldArea"));
			obj.setNewBookUnit(rs.getString("NewBookUnit"));
			obj.setNewBookValue(rs.getString("NewBookValue"));
			obj.setNewNetUnit(rs.getString("NewNetUnit"));
			obj.setNewNetValue(rs.getString("NewNetValue"));
			obj.setAdjustArea(rs.getString("AdjustArea"));
			obj.setAdjustHoldArea(rs.getString("AdjustHoldArea"));
			obj.setAddBookUnit(rs.getString("AddBookUnit"));
			obj.setAddBookValue(rs.getString("AddBookValue"));
			obj.setAddNetUnit(rs.getString("AddNetUnit"));
			obj.setAddNetValue(rs.getString("AddNetValue"));
			obj.setReduceBookUnit(rs.getString("ReduceBookUnit"));
			obj.setReduceBookValue(rs.getString("ReduceBookValue"));
			obj.setReduceNetUnit(rs.getString("ReduceNetUnit"));
			obj.setReduceNetValue(rs.getString("ReduceNetValue"));
			obj.setBookNotes(rs.getString("BookNotes"));
			obj.setChangeItem(rs.getString("ChangeItem"));
			obj.setNotes1(rs.getString("Notes1"));
			obj.setUseSeparate(rs.getString("UseSeparate"));
			obj.setUseKind(rs.getString("UseKind"));
			obj.setOldPropertyNo(rs.getString("OldPropertyNo"));
			obj.setOldSerialNo(rs.getString("OldSerialNo"));
			obj.setKeepUnit(rs.getString("KeepUnit"));
			obj.setKeeper(rs.getString("Keeper"));
			obj.setUseUnit(rs.getString("UseUnit"));
			obj.setUserNo(rs.getString("UserNo"));
			obj.setUserNote(rs.getString("UserNote"));
			obj.setPlace1(rs.getString("Place1"));
			obj.setPlace(rs.getString("Place"));
			obj.setNotes(rs.getString("Notes"));
			obj.setEditID(rs.getString("EditID"));
			obj.setEditDate(ul._transToROC_Year(rs.getString("EditDate")));
			obj.setEditTime(rs.getString("EditTime"));
			obj.setEnterOrgName(rs.getString("enterOrgName"));
			obj.setEngineeringNoName(ul._getEngineeringNoName(rs.getString("enterOrg"),rs.getString("engineeringNo")));
			obj.setPropertyNoName(rs.getString("propertyName"));			
			obj.setClosing1ym(rs.getString("closing1ym"));
			if(Common.checkGet(rs.getString("signNo")).length() == 15){
				obj.setSignNo1(rs.getString("signNo").substring(0,1)+"000000");
				obj.setSignNo2(rs.getString("signNo").substring(0,3)+"0000");
				obj.setSignNo3(rs.getString("signNo").substring(0,7));
				obj.setSignNo4(rs.getString("signNo").substring(7,11));
				obj.setSignNo5(rs.getString("signNo").substring(11,15));	
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
		String sql = " select a.enterOrg ,(select o.organSName from SYSCA_Organ o where a.enterOrg = o.organID) as organSName ,a.ownership " + "\n"
				   + "       ,(select codename from sysca_code x where x.codekindid = 'OWA' and x.codeid = a.ownership ) as ownershipName " + "\n"
				   + "       ,a.caseNo ,a.propertyNo ,a.serialNo " + "\n"
				   + "       ,a.signNo " + "\n"
				   + "       ,(case a.cause when '1' then '公告地價調整' when '2' then '資產重估調整' when '3' then '其它' when '4' then '面積調整' else '' end) cause " + "\n"
				   + "       ,(select codename from sysca_code x where x.codekindid = 'PKD' and x.codeid = a.propertykind ) as propertyKind " + "\n"
				   + "       ,a.originalBV, a.oldHoldArea, a.newHoldArea,  a.adjustHoldArea, a.oldBookValue " + "\n"
				   + "       , a.newBookValue, a.adjustBookValue " + "\n"
				   + "       from UNTLA_AdjustDetail a " + "\n"
				   + "       right join UNTLA_AdjustProof b on a.enterOrg = b.enterOrg and a.ownership = b.ownership and a.caseNo = b.caseNo " + "\n"
				   + "       where 1=1 " + "\n"
				   + " and a.enterOrg=" + Common.sqlChar(getEnterOrg())
				   + " and a.ownership=" + Common.sqlChar(getOwnership())			
				   + " and a.caseNo=" + Common.sqlChar(getCaseNo())
				   + " and a.differenceKind=" + Common.sqlChar(getDifferenceKind())
				   + " order by a.enterOrg ,a.ownership ,a.differenceKind, a.propertyNo, a.serialNo ";

		ResultSet rs = db.querySQL(sql);
		while (rs.next()){
			counter++;
			String rowArray[]=new String[17];
			rowArray[0]=rs.getString("enterOrg"); 
			rowArray[1]=rs.getString("organSName");
			rowArray[2]=rs.getString("ownership"); 
			rowArray[3]=rs.getString("ownershipName");			
			rowArray[4]=rs.getString("propertyNo"); 
			rowArray[5]=rs.getString("serialNo"); 
			rowArray[6]=getSignDescName(rs.getString("signNo").substring(0,7)) +rs.getString("signNo").substring(7,11)+"-"+rs.getString("signNo").substring(11); 
			rowArray[7]=rs.getString("cause"); 
			rowArray[8]=rs.getString("propertyKind"); 
			rowArray[9]=rs.getString("originalBV");
			rowArray[10]=rs.getString("oldHoldArea"); 
			rowArray[11]=rs.getString("newHoldArea");
			rowArray[12]=rs.getString("adjustHoldArea");
			rowArray[13]=rs.getString("oldBookValue");  
			rowArray[14]=rs.getString("newBookValue");
			rowArray[15]=rs.getString("adjustBookValue");
			rowArray[16]=rs.getString("caseNo");
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

public void doValueUnitClass(){
	String[] sql = new String[1]; 
	sql[0] = " insert into untla_adjustDetail( "
		   + " enterOrg "
		   + " ,ownership "
		   + " ,caseNo "
		   + " ,differenceKind "
		   + " ,propertyNo "
		   + " ,serialNo "
		   + " ,propertyName1 "
		   + " ,signNo "
		   + " ,cause "
		   + " ,cause1 "
		   + " ,bulletinDate "
		   + " ,adjustDate "
		   + " ,verify "
		   + " ,propertykind "
		   + " ,fundtype "
		   + " ,valuable "
		   + " ,taxcredit "
		   + " ,grass "
		   + " ,originalbv "
		   + " ,sourceDate "
		   + " ,oldArea "
		   + " ,oldHoldNume "
		   + " ,oldHoldDeno "
		   + " ,oldHoldArea "
		   + " ,oldBookUnit "
		   + " ,oldBookValue "
		   + " ,newArea "
		   + " ,newHoldNume "
		   + " ,newHolddeno "
		   + " ,newHoldArea "
		   + " ,newBookUnit "
		   + " ,newBookValue "
		   + " ,adjustArea "
		   + " ,adjustHoldArea "
		   + " ,adjustBookUnit "
		   + " ,adjustBookValue "
		   + " ,bookNotes "
		   + " ,changeItem "
		   + " ,notes1 "
		   + " ,useseparate "
		   + " ,usekind "
		   + " ,notes "
		   + " ,oldpropertyno "
		   + " ,oldserialno "
		   + " ,editID "
		   + " ,editDate "
		   + " ,editTime "
		   + " ,closing "
		   + " )( "
		   + " select a.enterorg "
		   + " ,a.ownership "
		   + " ," + Common.sqlChar(caseNo) + " as caseNo "
		   + " ,a.differenceKind "
		   + " ,a.propertyno "
		   + " ,a.serialno "
		   + " ,a.propertyname1 "
		   + " ,a.signno " 
		   + " ,'1' as cause " 
		   + " ,'' as cause1 " 
		   + " ," + Common.sqlChar(q_bulletindate)+ " as bulletinDate "
		   + " ,'' as adjustDate "
		   + " ,'N' as verify "
		   + " ,a.propertykind " 
		   + " ,a.fundtype " 
		   + " ,a.valuable " 
		   + " ,a.taxcredit " 
		   + " ,a.grass "
		   + " ,a.originalbv " 
		   + " ,a.sourceDate " 
		   + " ,a.area as oldArea "
		   + " ,a.holdnume as oldHoldNume "
		   + " ,a.holddeno as oldHoldDeno "
		   + " ,a.holdarea as oldHoldArea "
		   + " ,a.bookunit as oldBookUnit "
		   + " ,a.bookvalue as oldBookValue "
		   + " ,a.area as newArea "
		   + " ,a.holdnume as newHoldNume "
		   + " ,a.holddeno as newHolddeno "
		   + " ,a.holdarea as newHoldArea "
		   + " ,b.valueunit as newBookUnit "
		   + " ,round(b.valueunit * a.holdarea) as newBookValue "
		   + " ,'0' as adjustArea "
		   + " ,'0' as adjustHoldArea "
		   + " ,b.valueunit - a.bookunit as adjustBookUnit "
		   + " ,round(b.valueunit * a.holdarea) - a.bookvalue as adjustBookValue "
		   + " ,'' as bookNotes "
		   + " ,'單價、總價' as changeItem "
		   + " ,'' as notes1 "
		   + " ,a.useseparate "
		   + " ,a.usekind "
		   + " ,'' as notes "
		   + " ,a.oldpropertyno "
		   + " ,a.oldserialno "
		   + " ," + Common.sqlChar(getEditID()) + " as editID "
		   + " ," + Common.sqlChar(getEditDate()) + " as editDate "
		   + " ," + Common.sqlChar(getEditTime()) + " as editTime "
		   + " ,'N' as closing "
		   + " from untla_land a ,untla_value b "
		   + " where 1=1 "
		   + " and a.enterorg = b.enterorg and a.ownership = b.ownership and a.propertyno = b.propertyno and a.serialno = b.serialno "
		   + " and a.enterorg = " + Common.sqlChar(enterOrg) 
		   + " and a.ownership = " + Common.sqlChar(ownership)
		   + " and b.bulletindate = " + Common.sqlChar(q_bulletindate)
		   + " and a.datastate = '1' "
		   + " and a.verify = 'Y' "
		   + " and (( b.valueunit > a.bookunit ) "
		   + " or ( a.bookunit < b.valueunit and a.cause not in ('101','105','108'))) "
		   + " ) " ;
	
	Database db = new Database();
	try {
			
		db.excuteSQL(sql);		
		setStateInsertSuccess();
		setErrorMsg("公告地價調整完成");				

	} catch (Exception e) {
		e.printStackTrace();
	} finally {
		db.closeAll();
	}	
}

}