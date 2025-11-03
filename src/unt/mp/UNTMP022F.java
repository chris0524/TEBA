/*
*<br>程式目的：動產廢品處理作業－處理單明細
*<br>程式代號：untmp022f
*<br>程式日期：0941205
*<br>程式作者：cherry
*<br>--------------------------------------------------------
*<br>修改作者　　修改日期　　　修改目的
*<br>--------------------------------------------------------
*/

package unt.mp;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Map;

import unt.ch.UNTCH_Tools;
import util.*;

public class UNTMP022F extends UNTMP021Q{


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
String deprMethod;
String scrapValue;
String deprAmount;
String apportionYear;
String monthDepr;
String useEndYM;
String apportionEndYM;
String accumDeprYM;
String accumDepr;
String permitReduceDate;
String accumDepr1;
String scrapValue1;
String notes;
String oldPropertyNo;
String oldSerialNo;
String realizeValue1;
String differenceKind;
String userNote;
String place1;
String placeName;
String buildFeeCB;
String deprUnitCB;
String deprPark;
String deprUnit;
String deprUnit1;
String deprAccounts;
String apportionMonth;
String caseSerialNo;

public String getDifferenceKind(){ return checkGet(differenceKind); }
public void setDifferenceKind(String s){ differenceKind=checkSet(s); }
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
public String getDeprMethod(){ return checkGet(deprMethod); }
public void setDeprMethod(String s){ deprMethod=checkSet(s); }
public String getScrapValue(){ return checkGet(scrapValue); }
public void setScrapValue(String s){ scrapValue=checkSet(s); }
public String getDeprAmount(){ return checkGet(deprAmount); }
public void setDeprAmount(String s){ deprAmount=checkSet(s); }
public String getApportionYear(){ return checkGet(apportionYear); }
public void setApportionYear(String s){ apportionYear=checkSet(s); }
public String getMonthDepr(){ return checkGet(monthDepr); }
public void setMonthDepr(String s){ monthDepr=checkSet(s); }
public String getUseEndYM(){ return checkGet(useEndYM); }
public void setUseEndYM(String s){ useEndYM=checkSet(s); }
public String getApportionEndYM(){ return checkGet(apportionEndYM); }
public void setApportionEndYM(String s){ apportionEndYM=checkSet(s); }
public String getAccumDeprYM(){ return checkGet(accumDeprYM); }
public void setAccumDeprYM(String s){ accumDeprYM=checkSet(s); }
public String getAccumDepr(){ return checkGet(accumDepr); }
public void setAccumDepr(String s){ accumDepr=checkSet(s); }
public String getPermitReduceDate(){ return checkGet(permitReduceDate); }
public void setPermitReduceDate(String s){ permitReduceDate=checkSet(s); }
public String getAccumDepr1(){ return checkGet(accumDepr1); }
public void setAccumDepr1(String s){ accumDepr1=checkSet(s); }
public String getScrapValue1(){ return checkGet(scrapValue1); }
public void setScrapValue1(String s){ scrapValue1=checkSet(s); }
public String getNotes(){ return checkGet(notes); }
public void setNotes(String s){ notes=checkSet(s); }
public String getOldPropertyNo(){ return checkGet(oldPropertyNo); }
public void setOldPropertyNo(String s){ oldPropertyNo=checkSet(s); }
public String getOldSerialNo(){ return checkGet(oldSerialNo); }
public void setOldSerialNo(String s){ oldSerialNo=checkSet(s); }
public String getRealizeValue1(){ return checkGet(realizeValue1); }
public void setRealizeValue1(String s){ realizeValue1=checkSet(s); }
public String getUserNote() {return checkGet(userNote);}
public void setUserNote(String s) {this.userNote = checkSet(s);}
public String getPlace1() {return checkGet(place1);}
public void setPlace1(String s) {this.place1 = checkSet(s);}
public String getPlaceName() {return checkGet(placeName);}
public void setPlaceName(String s) {this.placeName = checkSet(s);}
public String getBuildFeeCB() {	return checkGet(buildFeeCB);}
public void setBuildFeeCB(String s) {this.buildFeeCB = checkSet(s);	}
public String getDeprUnitCB() {return checkGet(deprUnitCB);}
public void setDeprUnitCB(String s) {this.deprUnitCB = checkSet(s);	}
public String getDeprPark() {return checkGet(deprPark);}
public void setDeprPark(String s) {this.deprPark = checkSet(s);	}
public String getDeprUnit() {return checkGet(deprUnit);}
public void setDeprUnit(String s) {this.deprUnit = checkSet(s);}
public String getDeprUnit1() {return checkGet(deprUnit1);	}
public void setDeprUnit1(String s) {this.deprUnit1 = checkSet(s);}
public String getDeprAccounts() {return checkGet(deprAccounts);}
public void setDeprAccounts(String s) {this.deprAccounts = checkSet(s);}
public String getApportionMonth() {return checkGet(apportionMonth);}
public void setApportionMonth(String s) {this.apportionMonth = checkSet(s);}
public String getCaseSerialNo() {return checkGet(caseSerialNo);}
public void setCaseSerialNo(String s) {this.caseSerialNo = checkSet(s);}


String ownershipQuery;

public String getOwnershipQuery(){ return checkGet(ownershipQuery); }
public void setOwnershipQuery(String s){ ownershipQuery=checkSet(s); }

//傳回執行insert前之檢查sql
protected String[][] getInsertCheckSQL(){
	Database db = new Database();
	ResultSet rs;
	String[][] checkSQLArray = new String[1][4];
 	checkSQLArray[0][0]=" select count(*) as checkResult from UNTMP_DEALDETAIL where 1=1 " + 
		" and enterorg = " + Common.sqlChar(enterOrg) + 
		" and caseno = " + Common.sqlChar(caseNo) + 
		" and propertyno = " + Common.sqlChar(propertyNo) + 
		" and serialno = " + Common.sqlChar(Common.formatFrontZero(serialNo,7)) +
		" and differencekind = " + Common.sqlChar(differenceKind) +
		"";
	checkSQLArray[0][1]=">";
	checkSQLArray[0][2]="0";
	checkSQLArray[0][3]="資料重覆，請重新輸入!!";


	//取得序號
	String sql="select ISNULL(max(caseSerialNo),0)+1 as caseSerialNo "+
	" from UNTMP_ReduceDetail " +
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
	execSQLArray[0]=" insert into UNTMP_DEALDETAIL(" +
			" enterorg,"+
			" ownership,"+
			" differencekind, "+
			" caseno1,"+
			" caseno,"+
			" propertyno,"+
			" lotno,"+
			" serialno,"+
			" otherpropertyunit,"+
			" othermaterial,"+
			" otherlimityear,"+
			" propertyname1,"+
			" buydate,"+
			" reducedate,"+
			" dealdate,"+
			" verify,"+
			" propertykind,"+
			" fundtype,"+
			" valuable,"+
			" accountingtitle,"+
			" oldbookamount,"+
			" oldbookunit,"+
			" oldbookvalue,"+
			" adjustbookamount,"+
			" adjustbookvalue,"+
			" newbookamount,"+
			" newbookvalue,"+
			" articlename,"+
			" nameplate,"+
			" specification,"+
			" sourcedate,"+
			" licenseplate,"+
			" movedate,"+
			" keepunit,"+
			" keeper,"+
			" useunit,"+
			" userno,"+
			" place,"+
			" returnplace,"+
			" useyear,"+
			" usemonth,"+
			" notes,"+
			" oldpropertyno,"+
			" oldserialno,"+
			" realizevalue1,"+
			" editid,"+
			" editdate,"+
			" edittime,"+
			" caseSerialNo,"+
			" place1,"+
			" userNote"+
		" )Values(" +
			Common.sqlChar(enterOrg) + "," +
			Common.sqlChar(ownership) + "," +
			Common.sqlChar(differenceKind) + "," +
			Common.sqlChar(caseNo1) + "," +
			Common.sqlChar(caseNo) + "," +
			Common.sqlChar(propertyNo) + "," +
			Common.sqlChar(lotNo) + "," +
			Common.sqlChar(Common.formatFrontZero(serialNo,7)) + "," +
			Common.sqlChar(otherPropertyUnit) + "," +
			Common.sqlChar(otherMaterial) + "," +
			Common.sqlChar(otherLimitYear) + "," +
			Common.sqlChar(propertyName1) + "," +
			Common.sqlChar(ul._transToCE_Year(buyDate)) + "," +
			Common.sqlChar(ul._transToCE_Year(reduceDate)) + "," +
			Common.sqlChar(ul._transToCE_Year(dealDate)) + "," +
			Common.sqlChar(verify) + "," +
			Common.sqlChar(propertyKind) + "," +
			Common.sqlChar(fundType) + "," +
			Common.sqlChar(valuable) + "," +
			Common.sqlChar(accountingTitle) + "," +
			Common.sqlChar(oldBookAmount) + "," +
			Common.sqlChar(oldBookUnit) + "," +
			Common.sqlChar(oldBookValue) + "," +
			Common.sqlChar(adjustBookAmount) + "," +
			Common.sqlChar(adjustBookValue) + "," +
			Common.sqlChar(newBookAmount) + "," +
			Common.sqlChar(newBookValue) + "," +
			Common.sqlChar(articleName) + "," +
			Common.sqlChar(nameplate) + "," +
			Common.sqlChar(specification) + "," +
			Common.sqlChar(ul._transToCE_Year(sourceDate)) + "," +
			Common.sqlChar(licensePlate) + "," +
			Common.sqlChar(ul._transToCE_Year(moveDate)) + "," +
			Common.sqlChar(keepUnit) + "," +
			Common.sqlChar(keeper) + "," +
			Common.sqlChar(useUnit) + "," +
			Common.sqlChar(userNo) + "," +
			Common.sqlChar(place) + "," +
			Common.sqlChar(returnPlace) + "," +
			Common.sqlChar(useYear) + "," +
			Common.sqlChar(useMonth) + "," +
			Common.sqlChar(notes) + "," +
			Common.sqlChar(oldPropertyNo) + "," +
			Common.sqlChar(oldSerialNo) + "," +
			Common.sqlChar(realizeValue1) + "," +
			Common.sqlChar(getEditID()) + "," +
			Common.sqlChar(ul._transToCE_Year(getEditDate())) + "," +
			Common.sqlChar(getEditTime()) + "," +
			Common.sqlChar(caseSerialNo) + "," +
			Common.sqlChar(place1) + "," +
			Common.sqlChar(userNote) + ")" ;
//	System.out.println("execSQLArray[0]="+execSQLArray[0]);
	return execSQLArray;
}


//傳回update sql
protected String[] getUpdateSQL(){
	UNTCH_Tools ul = new UNTCH_Tools();
	String[] execSQLArray = new String[1];
	execSQLArray[0]=" update UNTMP_DEALDETAIL set " +
			" lotNo = " + Common.sqlChar(lotNo) + "," +
			" otherPropertyUnit = " + Common.sqlChar(otherPropertyUnit) + "," +
			" otherMaterial = " + Common.sqlChar(otherMaterial) + "," +
			" otherLimitYear = " + Common.sqlChar(otherLimitYear) + "," +
			" propertyName1 = " + Common.sqlChar(propertyName1) + "," +
			" buyDate = " + Common.sqlChar(ul._transToCE_Year(buyDate)) + "," +
			" reduceDate = " + Common.sqlChar(ul._transToCE_Year(reduceDate)) + "," +
			" dealDate = " + Common.sqlChar(ul._transToCE_Year(dealDate)) + "," +
			" verify = " + Common.sqlChar(verify) + "," +
			" propertyKind = " + Common.sqlChar(propertyKind) + "," +
			" fundType = " + Common.sqlChar(fundType) + "," +
			" valuable = " + Common.sqlChar(valuable) + "," +
			" accountingTitle = " + Common.sqlChar(accountingTitle) + "," +
			" oldBookAmount = " + Common.sqlChar(oldBookAmount) + "," +
			" oldBookUnit = " + Common.sqlChar(oldBookUnit) + "," +
			" oldBookValue = " + Common.sqlChar(oldBookValue) + "," +
			" adjustBookAmount = " + Common.sqlChar(adjustBookAmount) + "," +
			" adjustBookValue = " + Common.sqlChar(adjustBookValue) + "," +
			" newBookAmount = " + Common.sqlChar(newBookAmount) + "," +
			" newBookValue = " + Common.sqlChar(newBookValue) + "," +
			" articleName = " + Common.sqlChar(articleName) + "," +
			" nameplate = " + Common.sqlChar(nameplate) + "," +
			" specification = " + Common.sqlChar(specification) + "," +
			" sourceDate = " + Common.sqlChar(ul._transToCE_Year(sourceDate)) + "," +
			" licensePlate = " + Common.sqlChar(licensePlate) + "," +
			" moveDate = " + Common.sqlChar(ul._transToCE_Year(moveDate)) + "," +
			" keepUnit = " + Common.sqlChar(keepUnit) + "," +
			" keeper = " + Common.sqlChar(keeper) + "," +
			" useUnit = " + Common.sqlChar(useUnit) + "," +
			" userNo = " + Common.sqlChar(userNo) + "," +
			" place = " + Common.sqlChar(place) + "," +
			" returnPlace = " + Common.sqlChar(returnPlace) + "," +
			" useYear = " + Common.sqlChar(useYear) + "," +
			" useMonth = " + Common.sqlChar(useMonth) + "," +
			" notes = " + Common.sqlChar(notes) + "," +
			" oldPropertyNo = " + Common.sqlChar(oldPropertyNo) + "," +
			" oldSerialNo = " + Common.sqlChar(oldSerialNo) + "," +
			" realizeValue1 = " + Common.sqlChar(realizeValue1) + "," +
			" editID = " + Common.sqlChar(getEditID()) + "," +
			" editDate = " + Common.sqlChar(ul._transToCE_Year(getEditDate())) + "," +
			" editTime = " + Common.sqlChar(getEditTime()) + "," +
			" differenceKind = " + Common.sqlChar(differenceKind) + "," +
			" caseSerialNo = " + Common.sqlChar(caseSerialNo) + "," +
			" place1 = " + Common.sqlChar(place1) + "," +
			" userNote = " + Common.sqlChar(userNote) +
		" where 1=1 " + 
			" and enterOrg = " + Common.sqlChar(enterOrg) +
			" and ownership = " + Common.sqlChar(ownership) +
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
	execSQLArray[0]=" delete UNTMP_DEALDETAIL where 1=1 " +
			" and enterOrg = " + Common.sqlChar(enterOrg) +
			" and ownership = " + Common.sqlChar(ownership) +
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

public UNTMP022F  queryOne() throws Exception{
	Database db = new Database();
	UNTCH_Tools ul = new UNTCH_Tools();
	UNTMP022F obj = this;
	try {
		String sql=" select distinct a.enterorg, g.organsname as enterOrgName, a.ownership, a.caseno1, a.caseno, a.propertyno,a.differencekind,a.caseserialno,a.usernote,a.place1, " +
					" a.lotNo, a.serialno, b.propertyname, b.propertyUnit, b.material,a.differencekind, " +
					" b.limitYear, a.otherPropertyUnit, a.otherMaterial, a.otherLimitYear, " +
					" a.propertyname1,  a.buyDate, a.reduceDate, a.dealdate, " +
					" a.verify, a.propertykind, a.fundtype, a.valuable, a.accountingTitle, " +
					" a.oldBookAmount, a.oldBookUnit, a.oldBookValue, a.adjustBookAmount, " +
					" a.adjustbookvalue, a.newBookAmount, a.newBookValue, a.articleName, " +
					" a.nameplate, a.specification, a.sourceDate, a.licensePlate, a.moveDate, " +
					" a.keepUnit, a.keeper, a.useUnit, a.userNo, a.place, a.returnPlace, " +
					" (select sp.placename from SYSCA_PLACE sp where sp.placeno=a.place1 and a.enterorg=sp.enterorg) as placeName, "+
					" (select c.unitName from UNTMP_KeepUnit c where a.enterorg=c.enterorg and a.keepUnit=c.unitno) as keepUnitName, "+
					" (select d.unitName from UNTMP_KeepUnit d where a.enterorg=d.enterorg and a.useUnit=d.unitNo) as useUnitName, "+
					" (select e.keeperName from UNTMP_Keeper e where a.enterorg=e.enterorg and a.keeper=e.keeperNo) as keeperName, "+		
					" (select f.keeperName from UNTMP_Keeper f where a.enterorg=f.enterorg and a.userNo=f.keeperNo) as userName, "+		
					" a.useYear, a.useMonth, " +
					" a.notes, a.oldPropertyNo, a.oldSerialNo, a.realizeValue1, a.editID, a.editDate, a.editTime  "+
					" from UNTMP_DEALDETAIL a, SYSPK_PROPERTYCODE b, SYSCA_ORGAN g where 1=1 " +
					" and a.propertyno = b.propertyno and b.enterorg in('000000000A',a.enterorg) and b.propertytype='1' " +
					" and a.enterorg=g.organid" +
					" and a.enterorg = " + Common.sqlChar(enterOrg) +
					" and a.ownership = " + Common.sqlChar(ownership) +
					" and a.caseno = " + Common.sqlChar(caseNo) +
					" and a.propertyno = " + Common.sqlChar(propertyNo) +
					" and a.serialno = " + Common.sqlChar(serialNo) +
					" and a.differenceKind = " + Common.sqlChar(differenceKind) +
					"";
		ResultSet rs = db.querySQL(sql);
		if (rs.next()){
			obj.setEnterOrg(rs.getString("enterOrg"));
			obj.setDifferenceKind(rs.getString("differencekind"));
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
			obj.setCaseSerialNo(rs.getString("caseSerialNo"));
			obj.setUserNote(rs.getString("userNote"));
			obj.setPlace1(rs.getString("place1"));
			obj.setPlaceName(rs.getString("placeName"));
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
		String sql=" select distinct b.enterorg, b.ownership, b.caseno1, b.caseno, b.propertyno, b.serialno,b.differencekind,b.caseSerialNo, " +
					" (select c.organsname from SYSCA_ORGAN c where b.enterorg=c.organid ) as enterOrgName, " +
					"  (select x.codename from sysca_code x where 1=1 and x.codekindid ='OWA' and b.ownership = x.codeid )as ownershipName, " +
					" (select d.propertyname from SYSPK_PROPERTYCODE d where 1=1 and d.enterorg in('000000000A',b.enterorg) and d.propertytype='1' and b.propertyno=d.propertyno) as propertyName, " +
					" b.dealdate, b.adjustbookamount, b.adjustbookvalue "+
					" from UNTMP_DEALPROOF a, UNTMP_DEALDETAIL b where 1=1 "+
					" and a.enterorg=b.enterorg  and a.caseno1=b.caseno1 ";
		if ("dealProof".equals(getQuerySelect()) || "".equals(getQuerySelect())) {
			sql+=" and b.enterorg = " + Common.sqlChar(getEnterOrg()) ;
			if(!"".equals(getOwnership())){
			    sql+=" and b.ownership = " + Common.sqlChar(getOwnership()) ;
			}
			sql+=" and b.caseno1=" + Common.sqlChar(getCaseNo1());
		} else {
			if (!"".equals(getQ_enterOrg())) {
				sql+=" and b.enterorg = " + Common.sqlChar(getQ_enterOrg()) ;
			} else {
				if (!getIsAdminManager().equalsIgnoreCase("Y")) {
					if ("Y".equals(getIsOrganManager())) {					
						sql += " and b.enterorg like '" + getOrganID().substring(0,5) + "%' ";					
					} else {
						sql+=" and b.enterorg = " + Common.sqlChar(getOrganID()) ;
					}
				}
			}
			if (!"".equals(getQ_caseNo1S()))
				sql+=" and b.caseno1 >= " + Common.sqlChar(Common.formatRearString(getQ_caseNo1S(),10,'0'));		
			if (!"".equals(getQ_caseNo1E()))
				sql+=" and b.caseno1<=" + Common.sqlChar(Common.formatRearString(getQ_caseNo1E(),10, '9'));
			if (!"".equals(getQ_dealDateS()))
				sql+=" and b.dealdate >= " + Common.sqlChar(getQ_dealDateS());
			if (!"".equals(getQ_dealDateE()))
				sql+=" and b.dealdate <= " + Common.sqlChar(getQ_dealDateE());
			if (!"".equals(getQ_verify()))
				sql+=" and b.verify  = " + Common.sqlChar(getQ_verify()) ;
			if (!"".equals(getQ_caseName()))
				sql+=" and a.casename  like " + Common.sqlChar(getQ_caseName()+"%") ;   	    
			if (!"".equals(getQ_writeDateS()))
				sql+=" and a.writedate >= " + Common.sqlChar(new Datetime().changeTaiwanYYMMDD(getQ_writeDateS(),"2"));		
			if (!"".equals(getQ_writeDateE()))
				sql+=" and a.writedate<=" + Common.sqlChar(new Datetime().changeTaiwanYYMMDD(getQ_writeDateE(),"2"));   	    
			if (!"".equals(getQ_proofDoc()))
				sql+=" and a.proofdoc like '%" + getQ_proofDoc() + "%'" ;
			if (!"".equals(getQ_proofNoS())) 
				sql+=" and a.proofno >= " + Common.sqlChar(Common.formatRearZero(getQ_proofNoS(), 7));		
			if (!"".equals(getQ_proofNoE())) 
				sql+=" and a.proofno<=" + Common.sqlChar(Common.formatRearString(getQ_proofNoE(),7,'9'));		 
			if (!"".equals(getQ_reduceDeal()))
				sql+=" and a.reducedeal = " + Common.sqlChar(getQ_reduceDeal()) ;
			if (!"".equals(getQ_shiftOrg()))
				sql+=" and a.shiftorg = " + Common.sqlChar(getQ_shiftOrg()) ;
			if (!"".equals(getQ_propertyNoS()))
				sql+=" and b.propertyno >= " + Common.sqlChar(getQ_propertyNoS());		
			if (!"".equals(getQ_propertyNoE()))
				sql+=" and b.propertyno<=" + Common.sqlChar(getQ_propertyNoE());						
			if (!"".equals(getQ_serialNoS()))
				sql+=" and b.serialno >= " + Common.sqlChar(getQ_serialNoS());		
			if (!"".equals(getQ_serialNoE()))
				sql+=" and b.serialno <= " + Common.sqlChar(getQ_serialNoE());			
			if (!"".equals(getQ_propertyKind()))
				sql+=" and b.propertykind = " + Common.sqlChar(getQ_propertyKind()) ;
			if (!"".equals(getQ_fundType()))
				sql+=" and b.fundtype = " + Common.sqlChar(getQ_fundType()) ;
			if (!"".equals(getQ_valuable()))
				sql+=" and b.valuable = " + Common.sqlChar(getQ_valuable()) ;
			if (!"".equals(getQ_differenceKind()))
				sql+=" and a.differencekind = " + Common.sqlChar(getQ_differenceKind()) ;
			if (!"".equals(getQ_proofYear()))
				sql+=" and a.proofyear = " + Common.sqlChar(getQ_proofYear()) ;		
		}
//System.out.println("untmp022_sql="+sql);
		ResultSet rs = db.querySQL(sql.toString(),true);
		processCurrentPageAttribute(rs);			
		if (getTotalRecord() > 0) {
			int count = getRecordStart();
			int end = getRecordEnd();
			do {
				if (count > end)
					break;
			String rowArray[]=new String[17];
			rowArray[0]=rs.getString("enterOrgName"); 
			rowArray[1]=differencekindMap.get(rs.getString("differencekind"));
			rowArray[2]=rs.getString("caseNo1"); 
			rowArray[3]=rs.getString("caseNo"); 
			rowArray[4]=rs.getString("propertyNo"); 
			rowArray[5]=rs.getString("serialNo"); 
			rowArray[6]=rs.getString("propertyName"); 
			rowArray[7]=rs.getString("dealDate"); 
			rowArray[8]=rs.getString("adjustBookAmount"); 
			rowArray[9]=rs.getString("adjustBookValue"); 
			rowArray[10]=rs.getString("enterOrg"); 
			rowArray[11]=rs.getString("ownership"); 
			rowArray[12]=rs.getString("caseNo1"); 
			rowArray[13]=rs.getString("caseNo"); 
			rowArray[14]=rs.getString("propertyNo"); 
			rowArray[15]=rs.getString("serialNo");
			rowArray[16]=rs.getString("differencekind"); 
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


