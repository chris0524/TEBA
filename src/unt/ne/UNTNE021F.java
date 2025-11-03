/*
*<br>程式目的：非消耗品廢品處理作業－處理單明細
*<br>程式代號：untne021f
*<br>程式日期：0941212
*<br>程式作者：cherry
*<br>--------------------------------------------------------
*<br>修改作者　　修改日期　　　修改目的
*<br>--------------------------------------------------------
*/

package unt.ne;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Map;

import unt.ch.UNTCH_Tools;
import util.*;

public class UNTNE021F extends UNTNE020Q{


String enterOrg;
String enterOrgName;
String ownership;
String caseNo1;
String caseNo;
String propertyNo;
String lotNo;
String serialNo;
String propertyNoName;
String propertyUnit;
String material;
String limitYear;
String otherPropertyUnit;
String otherMaterial;
String otherLimitYear;
String propertyName1;
String enterDate;
String buyDate;
String reduceDate;
String dealDate;
String verify;
String propertyKind;
String fundType;
String valuable;
String accountingTitle;
String oldBookAmount;
String oldBookUnit;
String oldBookValue;
String adjustBookAmount;
String adjustBookValue;
String newBookAmount;
String newBookValue;
String articleName;
String nameplate;
String specification;
String sourceDate;
String licensePlate;
String moveDate;
String keepUnit;
String keepUnitName;
String keeper;
String keeperName;
String useUnit;
String useUnitName;
String userNo;
String userName;
String place;
String returnPlace;
String useYear;
String useMonth;
String notes;
String oldPropertyNo;
String oldSerialNo;
String realizeValue1;
String differenceKind;
String caseSerialNo;
String causeName;
String userNote;
String place1;
String place1Name;

public String getEnterOrg(){ return checkGet(enterOrg); }
public void setEnterOrg(String s){ enterOrg=checkSet(s); }
public String getEnterOrgName(){ return checkGet(enterOrgName); }
public void setEnterOrgName(String s){ enterOrgName=checkSet(s); }
public String getOwnership(){ return checkGet(ownership); }
public void setOwnership(String s){ ownership=checkSet(s); }
public String getCaseNo1(){ return checkGet(caseNo1); }
public void setCaseNo1(String s){ caseNo1=checkSet(s); }
public String getCaseNo(){ return checkGet(caseNo); }
public void setCaseNo(String s){ caseNo=checkSet(s); }
public String getPropertyNo(){ return checkGet(propertyNo); }
public void setPropertyNo(String s){ propertyNo=checkSet(s); }
public String getLotNo(){ return checkGet(lotNo); }
public void setLotNo(String s){ lotNo=checkSet(s); }
public String getSerialNo(){ return checkGet(serialNo); }
public void setSerialNo(String s){ serialNo=checkSet(s); }
public String getPropertyNoName(){ return checkGet(propertyNoName); }
public void setPropertyNoName(String s){ propertyNoName=checkSet(s); }
public String getPropertyUnit(){ return checkGet(propertyUnit); }
public void setPropertyUnit(String s){ propertyUnit=checkSet(s); }
public String getMaterial(){ return checkGet(material); }
public void setMaterial(String s){ material=checkSet(s); }
public String getLimitYear(){ return checkGet(limitYear); }
public void setLimitYear(String s){ limitYear=checkSet(s); }
public String getOtherPropertyUnit(){ return checkGet(otherPropertyUnit); }
public void setOtherPropertyUnit(String s){ otherPropertyUnit=checkSet(s); }
public String getOtherMaterial(){ return checkGet(otherMaterial); }
public void setOtherMaterial(String s){ otherMaterial=checkSet(s); }
public String getOtherLimitYear(){ return checkGet(otherLimitYear); }
public void setOtherLimitYear(String s){ otherLimitYear=checkSet(s); }
public String getPropertyName1(){ return checkGet(propertyName1); }
public void setPropertyName1(String s){ propertyName1=checkSet(s); }
public String getEnterDate(){ return checkGet(enterDate); }
public void setEnterDate(String s){ enterDate=checkSet(s); }
public String getBuyDate(){ return checkGet(buyDate); }
public void setBuyDate(String s){ buyDate=checkSet(s); }
public String getReduceDate(){ return checkGet(reduceDate); }
public void setReduceDate(String s){ reduceDate=checkSet(s); }
public String getDealDate(){ return checkGet(dealDate); }
public void setDealDate(String s){ dealDate=checkSet(s); }
public String getVerify(){ return checkGet(verify); }
public void setVerify(String s){ verify=checkSet(s); }
public String getPropertyKind(){ return checkGet(propertyKind); }
public void setPropertyKind(String s){ propertyKind=checkSet(s); }
public String getFundType(){ return checkGet(fundType); }
public void setFundType(String s){ fundType=checkSet(s); }
public String getValuable(){ return checkGet(valuable); }
public void setValuable(String s){ valuable=checkSet(s); }
public String getAccountingTitle(){ return checkGet(accountingTitle); }
public void setAccountingTitle(String s){ accountingTitle=checkSet(s); }
public String getOldBookAmount(){ return checkGet(oldBookAmount); }
public void setOldBookAmount(String s){ oldBookAmount=checkSet(s); }
public String getOldBookUnit(){ return checkGet(oldBookUnit); }
public void setOldBookUnit(String s){ oldBookUnit=checkSet(s); }
public String getOldBookValue(){ return checkGet(oldBookValue); }
public void setOldBookValue(String s){ oldBookValue=checkSet(s); }
public String getAdjustBookAmount(){ return checkGet(adjustBookAmount); }
public void setAdjustBookAmount(String s){ adjustBookAmount=checkSet(s); }
public String getAdjustBookValue(){ return checkGet(adjustBookValue); }
public void setAdjustBookValue(String s){ adjustBookValue=checkSet(s); }
public String getNewBookAmount(){ return checkGet(newBookAmount); }
public void setNewBookAmount(String s){ newBookAmount=checkSet(s); }
public String getNewBookValue(){ return checkGet(newBookValue); }
public void setNewBookValue(String s){ newBookValue=checkSet(s); }
public String getArticleName(){ return checkGet(articleName); }
public void setArticleName(String s){ articleName=checkSet(s); }
public String getNameplate(){ return checkGet(nameplate); }
public void setNameplate(String s){ nameplate=checkSet(s); }
public String getSpecification(){ return checkGet(specification); }
public void setSpecification(String s){ specification=checkSet(s); }
public String getSourceDate(){ return checkGet(sourceDate); }
public void setSourceDate(String s){ sourceDate=checkSet(s); }
public String getLicensePlate(){ return checkGet(licensePlate); }
public void setLicensePlate(String s){ licensePlate=checkSet(s); }
public String getMoveDate(){ return checkGet(moveDate); }
public void setMoveDate(String s){ moveDate=checkSet(s); }
public String getKeepUnit(){ return checkGet(keepUnit); }
public void setKeepUnit(String s){ keepUnit=checkSet(s); }
public String getKeepUnitName(){ return checkGet(keepUnitName); }
public void setKeepUnitName(String s){ keepUnitName=checkSet(s); }
public String getKeeper(){ return checkGet(keeper); }
public void setKeeper(String s){ keeper=checkSet(s); }
public String getKeeperName(){ return checkGet(keeperName); }
public void setKeeperName(String s){ keeperName=checkSet(s); }
public String getUseUnit(){ return checkGet(useUnit); }
public void setUseUnit(String s){ useUnit=checkSet(s); }
public String getUseUnitName(){ return checkGet(useUnitName); }
public void setUseUnitName(String s){ useUnitName=checkSet(s); }
public String getUserNo(){ return checkGet(userNo); }
public void setUserNo(String s){ userNo=checkSet(s); }
public String getUserName(){ return checkGet(userName); }
public void setUserName(String s){ userName=checkSet(s); }
public String getPlace(){ return checkGet(place); }
public void setPlace(String s){ place=checkSet(s); }
public String getReturnPlace(){ return checkGet(returnPlace); }
public void setReturnPlace(String s){ returnPlace=checkSet(s); }
public String getUseYear(){ return checkGet(useYear); }
public void setUseYear(String s){ useYear=checkSet(s); }
public String getUseMonth(){ return checkGet(useMonth); }
public void setUseMonth(String s){ useMonth=checkSet(s); }

public String getNotes(){ return checkGet(notes); }
public void setNotes(String s){ notes=checkSet(s); }
public String getOldPropertyNo(){ return checkGet(oldPropertyNo); }
public void setOldPropertyNo(String s){ oldPropertyNo=checkSet(s); }
public String getOldSerialNo(){ return checkGet(oldSerialNo); }
public void setOldSerialNo(String s){ oldSerialNo=checkSet(s); }
public String getRealizeValue1(){ return checkGet(realizeValue1); }
public void setRealizeValue1(String s){ realizeValue1=checkSet(s); }
public String getDifferenceKind() {return checkGet(this.differenceKind);}
public void setDifferenceKind(String s) {differenceKind = checkSet(s);}
public String getCaseSerialNo() {return checkGet(caseSerialNo);}
public void setCaseSerialNo(String s) {this.caseSerialNo = checkSet(s);}
public String getCauseName() {return checkGet(causeName);}
public void setCauseName(String s) {this.causeName = checkSet(s);}
public String getUserNote() {return checkGet(userNote);}
public void setUserNote(String s) {this.userNote = checkSet(s);}
public String getPlace1() {return checkGet(place1);}
public void setPlace1(String s) {this.place1 = checkSet(s);}
public String getPlace1Name() {return checkGet(place1Name);}
public void setPlace1Name(String s) {this.place1Name = checkSet(s);}

String ownershipQuery;

public String getOwnershipQuery(){ return checkGet(ownershipQuery); }
public void setOwnershipQuery(String s){ ownershipQuery=checkSet(s); }

//傳回執行insert前之檢查sql
protected String[][] getInsertCheckSQL(){
	Database db = new Database();
	ResultSet rs;
	String[][] checkSQLArray = new String[1][4];
 	checkSQLArray[0][0]=" select count(*) as checkResult from UNTNE_DealDetail where 1=1 " + 
		" and enterOrg = " + Common.sqlChar(enterOrg) + 
		" and ownership = " + Common.sqlChar(ownership) + 
		" and caseNo = " + Common.sqlChar(caseNo) + 
		" and propertyNo = " + Common.sqlChar(propertyNo) + 
		" and serialNo = " + Common.sqlChar(Common.formatFrontZero(serialNo,7)) +
		" and differencekind = " + Common.sqlChar(differenceKind) +
		"";
	checkSQLArray[0][1]=">";
	checkSQLArray[0][2]="0";
	checkSQLArray[0][3]="該筆資料己重複，請重新輸入！";
	
	//取得序號
	String sql="select ISNULL(max(caseSerialNo),0)+1 as caseSerialNo "+
		" from UNTNE_ReduceDetail " +
		" where enterOrg = " + Common.sqlChar(enterOrg) + 
		" and caseNo = " + Common.sqlChar(caseNo) +
		" and ownership = " + Common.sqlChar(ownership) + 
		" and propertyNo = " + Common.sqlChar(propertyNo) + 
		" and serialNo = " + Common.sqlChar(serialNo) +
		" and differencekind = " + Common.sqlChar(differenceKind) +
		"";
	try {
	rs = db.querySQL(sql);
	if (rs.next()){
		setCaseSerialNo(Common.formatFrontZero(rs.getString("caseSerialNo"),5));
	} else {
		setCaseSerialNo("00001");
	}
	}catch (Exception e) {
		e.printStackTrace();
	} finally {
		db.closeAll();
	}

	return checkSQLArray;
}


//傳回insert sql
protected String[] getInsertSQL(){
	UNTCH_Tools ul = new UNTCH_Tools();
	String[] execSQLArray = new String[1];
	execSQLArray[0]=" insert into UNTNE_DealDetail(" +
			" enterOrg,"+
			" ownership,"+
			" caseNo1,"+
			" caseNo,"+
			" propertyNo,"+
			" lotNo,"+
			" serialNo,"+
			" otherPropertyUnit,"+
			" otherMaterial,"+
			" otherLimitYear,"+
			" propertyName1,"+
			" enterDate,"+
			" buyDate,"+
			" reduceDate,"+
			" dealDate,"+
			" verify,"+
			" propertyKind,"+
			" fundType,"+
			" valuable,"+
			" accountingTitle,"+
			" oldBookAmount,"+
			" oldBookUnit,"+
			" oldBookValue,"+
			" adjustBookAmount,"+
			" adjustBookValue,"+
			" newBookAmount,"+
			" newBookValue,"+
			" articleName,"+
			" nameplate,"+
			" specification,"+
			" sourceDate,"+
			" licensePlate,"+
			" moveDate,"+
			" keepUnit,"+
			" keeper,"+
			" useUnit,"+
			" userNo,"+
			" place,"+
			" returnPlace,"+
			" useYear,"+
			" useMonth,"+
			" notes,"+
			" oldPropertyNo,"+
			" oldSerialNo,"+
			" realizeValue1,"+			
			" editID,"+
			" editDate,"+
			" editTime,"+
			" differencekind,"+
			" caseSerialNo,"+
			" place1,"+
			" userNote"+
		" )Values(" +
			Common.sqlChar(this.getEnterOrg()) + "," +
			Common.sqlChar(this.getOwnership()) + "," +
			Common.sqlChar(this.getCaseNo1()) + "," +
			Common.sqlChar(this.getCaseNo()) + "," +
			Common.sqlChar(this.getPropertyNo()) + "," +
			Common.sqlChar(this.getLotNo()) + "," +
			Common.sqlChar(Common.formatFrontZero(this.getSerialNo(),7)) + "," +
			Common.sqlChar(this.getOtherPropertyUnit()) + "," +
			Common.sqlChar(this.getOtherMaterial()) + "," +
			Common.sqlChar(this.getOtherLimitYear()) + "," +
			Common.sqlChar(this.getPropertyName1()) + "," +
			Common.sqlChar(ul._transToCE_Year(this.getEnterDate())) + "," +
			Common.sqlChar(ul._transToCE_Year(this.getBuyDate())) + "," +
			Common.sqlChar(ul._transToCE_Year(this.getReduceDate())) + "," +
			Common.sqlChar(ul._transToCE_Year(this.getDealDate())) + "," +
			Common.sqlChar(this.getVerify()) + "," +
			Common.sqlChar(this.getPropertyKind()) + "," +
			Common.sqlChar(this.getFundType()) + "," +
			Common.sqlChar(this.getValuable()) + "," +
			Common.sqlChar(this.getAccountingTitle()) + "," +
			Common.sqlChar(this.getOldBookAmount()) + "," +
			Common.sqlChar(this.getOldBookUnit()) + "," +
			Common.sqlChar(this.getOldBookValue()) + "," +
			Common.sqlChar(this.getAdjustBookAmount()) + "," +
			Common.sqlChar(this.getAdjustBookValue()) + "," +
			Common.sqlChar(this.getNewBookAmount()) + "," +
			Common.sqlChar(this.getNewBookValue()) + "," +
			Common.sqlChar(this.getArticleName()) + "," +
			Common.sqlChar(this.getNameplate()) + "," +
			Common.sqlChar(this.getSpecification()) + "," +
			Common.sqlChar(ul._transToCE_Year(this.getSourceDate())) + "," +
			Common.sqlChar(this.getLicensePlate()) + "," +
			Common.sqlChar(ul._transToCE_Year(this.getMoveDate())) + "," +
			Common.sqlChar(this.getKeepUnit()) + "," +
			Common.sqlChar(this.getKeeper()) + "," +
			Common.sqlChar(this.getUseUnit()) + "," +
			Common.sqlChar(this.getUserNo()) + "," +
			Common.sqlChar(this.getPlace()) + "," +
			Common.sqlChar(this.getReturnPlace()) + "," +
			Common.sqlChar(this.getUseYear()) + "," +
			Common.sqlChar(this.getUseMonth()) + "," +
			Common.sqlChar(this.getNotes()) + "," +
			Common.sqlChar(this.getOldPropertyNo()) + "," +
			Common.sqlChar(this.getOldSerialNo()) + "," +
			Common.sqlChar(this.getRealizeValue1()) + "," +			
			Common.sqlChar(getEditID()) + "," +
			Common.sqlChar(ul._transToCE_Year(getEditDate())) + "," +
			Common.sqlChar(getEditTime()) + "," +
			Common.sqlChar(this.getDifferenceKind()) + "," +
			Common.sqlChar(this.getCaseSerialNo()) + "," +
			Common.sqlChar(this.getPlace1()) + "," +
			Common.sqlChar(this.getUserNote()) + ")" ;
	System.out.println();
	return execSQLArray;

}


//傳回update sql
protected String[] getUpdateSQL(){
	UNTCH_Tools ul = new UNTCH_Tools();
	String[] execSQLArray = new String[1];
	execSQLArray[0]=" update UNTNE_DealDetail set " +
			" lotNo = " + Common.sqlChar(this.getLotNo()) + "," +
			" otherPropertyUnit = " + Common.sqlChar(this.getOtherPropertyUnit()) + "," +
			" otherMaterial = " + Common.sqlChar(this.getOtherMaterial()) + "," +
			" otherLimitYear = " + Common.sqlChar(this.getOtherLimitYear()) + "," +
			" propertyName1 = " + Common.sqlChar(this.getPropertyName1()) + "," +
			" enterDate = " + Common.sqlChar(ul._transToCE_Year(this.getEnterDate())) + "," +
			" buyDate = " + Common.sqlChar(ul._transToCE_Year(this.getBuyDate())) + "," +
			" reduceDate = " + Common.sqlChar(ul._transToCE_Year(this.getReduceDate())) + "," +
			" dealDate = " + Common.sqlChar(ul._transToCE_Year(this.getDealDate())) + "," +
			" verify = " + Common.sqlChar(this.getVerify()) + "," +
			" propertyKind = " + Common.sqlChar(this.getPropertyKind()) + "," +
			" fundType = " + Common.sqlChar(this.getFundType()) + "," +
			" valuable = " + Common.sqlChar(this.getValuable()) + "," +
			" accountingTitle = " + Common.sqlChar(this.getAccountingTitle()) + "," +
			" oldBookAmount = " + Common.sqlChar(this.getOldBookAmount()) + "," +
			" oldBookUnit = " + Common.sqlChar(this.getOldBookUnit()) + "," +
			" oldBookValue = " + Common.sqlChar(this.getOldBookValue()) + "," +
			" adjustBookAmount = " + Common.sqlChar(this.getAdjustBookAmount()) + "," +
			" adjustBookValue = " + Common.sqlChar(this.getAdjustBookValue()) + "," +
			" newBookAmount = " + Common.sqlChar(this.getNewBookAmount()) + "," +
			" newBookValue = " + Common.sqlChar(this.getNewBookValue()) + "," +
			" articleName = " + Common.sqlChar(this.getArticleName()) + "," +
			" nameplate = " + Common.sqlChar(this.getNameplate()) + "," +
			" specification = " + Common.sqlChar(this.getSpecification()) + "," +
			" sourceDate = " + Common.sqlChar(ul._transToCE_Year(this.getSourceDate())) + "," +
			" licensePlate = " + Common.sqlChar(this.getLicensePlate()) + "," +
			" moveDate = " + Common.sqlChar(ul._transToCE_Year(this.getMoveDate())) + "," +
			" keepUnit = " + Common.sqlChar(this.getKeepUnit()) + "," +
			" keeper = " + Common.sqlChar(this.getKeeper()) + "," +
			" useUnit = " + Common.sqlChar(this.getUseUnit()) + "," +
			" userNo = " + Common.sqlChar(this.getUserNo()) + "," +
			" place = " + Common.sqlChar(this.getPlace()) + "," +
			" returnPlace = " + Common.sqlChar(this.getReturnPlace()) + "," +
			" useYear = " + Common.sqlChar(this.getUseYear()) + "," +
			" useMonth = " + Common.sqlChar(this.getUseMonth()) + "," +
			" notes = " + Common.sqlChar(this.getNotes()) + "," +
			" oldPropertyNo = " + Common.sqlChar(this.getOldPropertyNo()) + "," +
			" oldSerialNo = " + Common.sqlChar(this.getOldSerialNo()) + "," +
			" realizeValue1 = " + Common.sqlChar(this.getRealizeValue1()) + "," +			
			" editID = " + Common.sqlChar(getEditID()) + "," +
			" editDate = " + Common.sqlChar(ul._transToCE_Year(getEditDate())) + "," +
			" editTime = " + Common.sqlChar(getEditTime()) + "," +
			" differenceKind = " + Common.sqlChar(this.getDifferenceKind()) + "," +
			" caseSerialNo = " + Common.sqlChar(this.getCaseSerialNo()) + "," +
			" place1 = " + Common.sqlChar(this.getPlace1()) + "," +
			" userNote = " + Common.sqlChar(this.getUserNote()) +
		" where 1=1 " + 
			" and enterOrg = " + Common.sqlChar(enterOrg) +
			" and ownership = " + Common.sqlChar(ownership) +
			" and caseNo1 = " + Common.sqlChar(caseNo1) +
			" and caseNo = " + Common.sqlChar(caseNo) +
			" and propertyNo = " + Common.sqlChar(propertyNo) +
			" and serialNo = " + Common.sqlChar(serialNo) +
			" and differenceKind = " + Common.sqlChar(differenceKind) +
			"";
	return execSQLArray;
}


//傳回delete sql
protected String[] getDeleteSQL(){
	String[] execSQLArray = new String[1];
	execSQLArray[0]=" delete UNTNE_DealDetail where 1=1 " +
			" and enterOrg = " + Common.sqlChar(enterOrg) +
			" and ownership = " + Common.sqlChar(ownership) +
			" and caseNo1 = " + Common.sqlChar(caseNo1) +
			" and caseNo = " + Common.sqlChar(caseNo) +
			" and propertyNo = " + Common.sqlChar(propertyNo) +
			" and serialNo = " + Common.sqlChar(serialNo) +
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

public UNTNE021F  queryOne() throws Exception{
	Database db = new Database();
	UNTCH_Tools ul = new UNTCH_Tools();
	UNTNE021F obj = this;
	try {
		String sql=" select a.enterOrg, g.organSName as enterOrgName, a.ownership, a.caseNo1, a.caseNo, a.propertyNo,a.differencekind,a.caseserialno,a.usernote,a.place1, " +
					" a.lotNo, a.serialNo, b.propertyName, b.propertyUnit, b.material, " +
					" b.limitYear, a.otherPropertyUnit, a.otherMaterial, a.otherLimitYear, " +
					" a.propertyName1, a.enterDate, a.buyDate, a.reduceDate, a.dealDate, " +
					" a.verify, a.propertyKind, a.fundType, a.valuable, a.accountingTitle, " +
					" a.oldBookAmount, a.oldBookUnit, a.oldBookValue, a.adjustBookAmount, " +
					" a.adjustBookValue, a.newBookAmount, a.newBookValue, a.articleName, " +
					" a.nameplate, a.specification, a.sourceDate, a.licensePlate, a.moveDate, " +
					" a.keepUnit, a.keeper, a.useUnit, a.userNo, a.place, a.returnPlace, " +
					" (select c.unitName from UNTMP_KeepUnit c where a.enterOrg=c.enterOrg and a.keepUnit=c.unitNo) as keepUnitName, "+
					" (select sp.placeName from SYSCA_PLACE sp where a.enterOrg=sp.enterOrg and a.place1=sp.placeNo) as place1Name, "+
					" (select d.unitName from UNTMP_KeepUnit d where a.enterOrg=d.enterOrg and a.useUnit=d.unitNo) as useUnitName, "+
					" (select e.keeperName from UNTMP_Keeper e where a.enterOrg=e.enterOrg and a.keeper=e.keeperNo) as keeperName, "+		
					" (select f.keeperName from UNTMP_Keeper f where a.enterOrg=f.enterOrg and a.userNo=f.keeperNo) as userName, "+		
					" a.useYear, a.useMonth, a.notes, a.oldPropertyNo, a.oldSerialNo, a.realizeValue1, a.editID, a.editDate, a.editTime  "+
					" from UNTNE_DealDetail a, SYSPK_PropertyCode2 b, SYSCA_Organ g where 1=1 " +
					" and a.enterOrg = b.enterOrg and a.propertyNo = b.propertyNo " +
					" and a.enterOrg=g.organID" +
					" and a.enterOrg = " + Common.sqlChar(enterOrg) +
					" and a.ownership = " + Common.sqlChar(ownership) +
					" and a.caseNo = " + Common.sqlChar(caseNo) +
					" and a.propertyNo = " + Common.sqlChar(propertyNo) +
					" and a.serialNo = " + Common.sqlChar(serialNo) +
					" and a.differencekind    = " + Common.sqlChar(differenceKind) +
					"";
		ResultSet rs = db.querySQL(sql);
		if (rs.next()){
			obj.setEnterOrg(rs.getString("enterOrg"));
			obj.setEnterOrgName(rs.getString("enterOrgName"));
			obj.setOwnership(rs.getString("ownership"));
			obj.setCaseNo1(rs.getString("caseNo1"));
			obj.setCaseNo(rs.getString("caseNo"));
			obj.setPropertyNo(rs.getString("propertyNo"));
			obj.setLotNo(rs.getString("lotNo"));
			obj.setSerialNo(rs.getString("serialNo"));
			obj.setPropertyNoName(rs.getString("propertyName"));
			obj.setPropertyUnit(rs.getString("propertyUnit"));
			obj.setMaterial(rs.getString("material"));
			obj.setLimitYear(rs.getString("limitYear"));
			obj.setOtherPropertyUnit(rs.getString("otherPropertyUnit"));
			obj.setOtherMaterial(rs.getString("otherMaterial"));
			obj.setOtherLimitYear(rs.getString("otherLimitYear"));
			obj.setPropertyName1(rs.getString("propertyName1"));
			obj.setEnterDate(ul._transToROC_Year(rs.getString("enterDate")));
			obj.setBuyDate(ul._transToROC_Year(rs.getString("buyDate")));
			obj.setReduceDate(ul._transToROC_Year(rs.getString("reduceDate")));
			obj.setDealDate(ul._transToROC_Year(rs.getString("dealDate")));
			obj.setVerify(rs.getString("verify"));
			obj.setPropertyKind(rs.getString("propertyKind"));
			obj.setFundType(rs.getString("fundType"));
			obj.setValuable(rs.getString("valuable"));
			obj.setAccountingTitle(rs.getString("accountingTitle"));
			obj.setOldBookAmount(rs.getString("oldBookAmount"));
			obj.setOldBookUnit(rs.getString("oldBookUnit"));
			obj.setOldBookValue(rs.getString("oldBookValue"));
			obj.setAdjustBookAmount(rs.getString("adjustBookAmount"));
			obj.setAdjustBookValue(rs.getString("adjustBookValue"));
			obj.setNewBookAmount(rs.getString("newBookAmount"));
			obj.setNewBookValue(rs.getString("newBookValue"));
			obj.setArticleName(rs.getString("articleName"));
			obj.setNameplate(rs.getString("nameplate"));
			obj.setSpecification(rs.getString("specification"));
			obj.setSourceDate(ul._transToROC_Year(rs.getString("sourceDate")));
			obj.setLicensePlate(rs.getString("licensePlate"));
			obj.setMoveDate(ul._transToROC_Year(rs.getString("moveDate")));
			obj.setKeepUnit(rs.getString("keepUnit"));
			obj.setKeepUnitName(rs.getString("keepUnitName"));
			obj.setKeeper(rs.getString("keeper"));
			obj.setKeeperName(rs.getString("keeperName"));
			obj.setUseUnit(rs.getString("useUnit"));
			obj.setUseUnitName(rs.getString("useUnitName"));
			obj.setUserNo(rs.getString("userNo"));
			obj.setUserName(rs.getString("userName"));
			obj.setPlace(rs.getString("place"));
			obj.setReturnPlace(rs.getString("returnPlace"));
			obj.setUseYear(rs.getString("useYear"));
			obj.setUseMonth(rs.getString("useMonth"));
			obj.setNotes(rs.getString("notes"));
			obj.setOldPropertyNo(rs.getString("oldPropertyNo"));
			obj.setOldSerialNo(rs.getString("oldSerialNo"));
			obj.setRealizeValue1(rs.getString("realizeValue1"));			
			obj.setEditID(rs.getString("editID"));
			obj.setEditDate(ul._transToROC_Year(rs.getString("editDate")));
			obj.setEditTime(rs.getString("editTime"));
			obj.setDifferenceKind(rs.getString("differenceKind"));
			obj.setCaseSerialNo(rs.getString("caseSerialNo"));
			obj.setUserNote(rs.getString("userNote"));
			obj.setPlace1Name(rs.getString("place1Name"));
			obj.setPlace1(rs.getString("place1"));
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
	UNTCH_Tools ul = new UNTCH_Tools();
	Database db = new Database();
	ArrayList objList=new ArrayList();
	Map<String,String> differencekindMap = TCGHCommon.getSysca_Code("DFK"); 					//財產區分別名稱
	int counter=0;
	try {
		String sql=" select distinct b.enterOrg, b.ownership, b.caseNo1, b.caseNo, b.propertyNo, b.serialNo,a.differenceKind,b.caseSerialNo,b.lotno, " +
					" (select c.organSName from SYSCA_Organ c where b.enterOrg=c.organID ) as enterOrgName, " +
					" (select x.codename from sysca_code x where 1=1 and x.codekindid ='OWA' and b.ownership = x.codeid )as ownershipName, " +
					" (select d.propertyName from SYSPK_PropertyCode2 d where 1=1 and b.enterOrg = d.enterOrg and b.propertyNo = d.propertyNo ) as propertyName, " +
					" b.dealDate, b.adjustBookAmount, b.adjustBookValue "+
					" from UNTNE_DealProof a"+
					" left join UNTNE_DealDetail b on a.enterOrg=b.enterOrg  and a.caseNo1=b.caseNo1"+
					" where 1=1 ";
		if ("dealProof".equals(getQuerySelect()) || "".equals(getQuerySelect())) {
			sql+=" and b.enterOrg = " + Common.sqlChar(getEnterOrg()) ;
			
			sql+=" and b.caseNo1=" + Common.sqlChar(getCaseNo1());
		} else {
			if (!"".equals(getQ_enterOrg())) {
				sql+=" and b.enterOrg = " + Common.sqlChar(getQ_enterOrg()) ;
			} else {
				if (!getIsAdminManager().equalsIgnoreCase("Y")) {
					if ("Y".equals(getIsOrganManager())) {					
						//sql += " and b.enterOrg like '" + getOrganID().substring(0,5) + "%' ";	
						sql += " and( a.enterOrg = " + Common.sqlChar(getOrganID()) ;
						sql += " or organID in (select organID from SYSCA_Organ where organSuperior='"+ Common.sqlChar(getOrganID())+"'))";
					} else {
						sql+=" and b.enterOrg = " + Common.sqlChar(getOrganID()) ;
					}
				}
			}
			if (!"".equals(getQ_ownership()))
				sql+=" and b.ownership = " + Common.sqlChar(getQ_ownership()) ;
			if (!"".equals(getQ_caseNo1S()))
				sql+=" and b.caseNo1 >= " + Common.sqlChar(Common.formatRearString(getQ_caseNo1S(),10,'0'));		
			if (!"".equals(getQ_caseNo1E()))
				sql+=" and b.caseNo1<=" + Common.sqlChar(Common.formatRearString(getQ_caseNo1E(),10, '9'));
			if (!"".equals(getQ_dealDateS()))
				sql+=" and b.dealDate >= " + Common.sqlChar(ul._transToCE_Year(getQ_dealDateS()));
			if (!"".equals(getQ_dealDateE()))
				sql+=" and b.dealDate <= " + Common.sqlChar(ul._transToCE_Year(getQ_dealDateE()));
			if (!"".equals(getQ_verify()))
				sql+=" and b.verify  = " + Common.sqlChar(getQ_verify()) ;
			if (!"".equals(getQ_caseName()))
				sql+=" and a.caseName  like " + Common.sqlChar(getQ_caseName()+"%") ;   	    
			if (!"".equals(getQ_writeDateS()))
				sql+=" and a.writeDate >= " + Common.sqlChar(ul._transToCE_Year(getQ_writeDateS()));		
			if (!"".equals(getQ_writeDateE()))
				sql+=" and a.writeDate<=" + Common.sqlChar(ul._transToCE_Year(getQ_writeDateE()));   	    
			if (!"".equals(getQ_proofDoc()))
				sql+=" and a.proofDoc like '%" + getQ_proofDoc() + "%'" ;
			if (!"".equals(getQ_proofNoS())) 
				sql+=" and a.proofNo >= " + Common.sqlChar(getQ_proofNoS());		
			if (!"".equals(getQ_proofNoE())) 
				sql+=" and a.proofNo <= " + Common.sqlChar(getQ_proofNoE());		 
			if (!"".equals(getQ_reduceDeal()))
				sql+=" and a.reduceDeal = " + Common.sqlChar(getQ_reduceDeal()) ;
			if (!"".equals(getQ_shiftOrg()))
				sql+=" and a.shiftOrg = " + Common.sqlChar(getQ_shiftOrg()) ;
			if (!"".equals(getQ_propertyNoS()))
				sql+=" and b.propertyNo >= " + Common.sqlChar(getQ_propertyNoS());		
			if (!"".equals(getQ_propertyNoE()))
				sql+=" and b.propertyNo <= " + Common.sqlChar(getQ_propertyNoE());						
			if (!"".equals(getQ_serialNoS()))
				sql+=" and b.serialNo >= " + Common.sqlChar(getQ_serialNoS());		
			if (!"".equals(getQ_serialNoE()))
				sql+=" and b.serialNo <= " + Common.sqlChar(getQ_serialNoE());			
			if (!"".equals(getQ_propertyKind()))
				sql+=" and b.propertyKind = " + Common.sqlChar(getQ_propertyKind()) ;
			if (!"".equals(getQ_fundType()))
				sql+=" and b.fundType = " + Common.sqlChar(getQ_fundType()) ;
			if (!"".equals(getQ_valuable()))
				sql+=" and b.valuable = " + Common.sqlChar(getQ_valuable()) ;
			if (!"".equals(getQ_differenceKind()))
				sql+=" and a.differencekind = " + Common.sqlChar(getQ_differenceKind()) ;
			if (!"".equals(getQ_proofYear()))
				sql+=" and a.proofyear = " + Common.sqlChar(getQ_proofYear()) ;			
		}
		ResultSet rs = db.querySQL(sql.toString(),true);
		processCurrentPageAttribute(rs);	
		if (getTotalRecord() > 0) {
			int count = getRecordStart();
			int end = getRecordEnd();
			do {
				if (count > end)
					break;

			String rowArray[]=new String[18];
			rowArray[0]=rs.getString("enterOrgName"); 
			rowArray[1]=rs.getString("ownershipName");
			rowArray[2]=differencekindMap.get(rs.getString("differencekind"));
			rowArray[3]=rs.getString("caseNo1"); 
			rowArray[4]=rs.getString("caseNo"); 
			rowArray[5]=rs.getString("propertyNo"); 
			rowArray[6]=rs.getString("serialNo"); 
			rowArray[7]=rs.getString("propertyName"); 
			rowArray[8]=ul._transToROC_Year(rs.getString("dealDate")); 
			rowArray[9]=rs.getString("adjustBookAmount"); 
			rowArray[10]=rs.getString("adjustBookValue"); 
			rowArray[11]=rs.getString("enterOrg"); 
			rowArray[12]=rs.getString("ownership"); 
			rowArray[13]=rs.getString("caseNo1"); 
			rowArray[14]=rs.getString("caseNo"); 
			rowArray[15]=rs.getString("propertyNo"); 
			rowArray[16]=rs.getString("serialNo");
			rowArray[17]=(rs.getString("differencekind"));
			objList.add(rowArray);
			count++;
			} while (rs.next());
//			if (counter==getListLimit()){
//				setErrorMsg(getListLimitError());
//				break;
//			}
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


