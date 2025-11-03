/*
*<br>程式目的：非消耗品保管使用異動作業－明細資料
*<br>程式代號：untne009f
*<br>程式日期：0950222
*<br>程式作者：Cherry
*<br>--------------------------------------------------------
*<br>修改作者　　修改日期　　　修改目的
*<br>--------------------------------------------------------
*/

package unt.ne;

import java.sql.ResultSet;
import java.util.ArrayList;

import unt.ch.UNTCH_Tools;
import util.*;

public class UNTNE009F extends UNTNE008Q{


String enterOrg;
String enterOrgName;
String ownership;
String ownershipQuery;
String caseNo;
String propertyNo;
String propertyNoName;
String lotNo;
String serialNo;
String propertyName1;
String propertyUnit;
String material;
String limitYear;
String otherPropertyUnit;
String otherMaterial;
String otherLimitYear;
String buyDate;
String verify;
String propertyKind;
String fundType;
String valuable;
String bookAmount;
String bookUnit;
String bookValue;
String nameplate;
String specification;
String sourceDate;
String oldMoveDate;
String oldKeepUnit;
String oldKeeper;
String oldUseUnit;
String oldUserNo;
String oldPlace;
String newMoveDate;
String newKeepUnit;
String newKeeper;
String newUseUnit;
String newUserNo;
String newPlace;
String useYear;
String useMonth;
String notes;
String oldPropertyNo;
String oldSerialNo;
String differenceKind;
String caseSerialNo;

String oldUserNote;
String oldPlace1;
String oldPlaceName;
String newUserNote;
String newPlace1;
String newPlace1Name;

String moveDate;
String initDtl;

public String getEnterOrg(){ return checkGet(enterOrg); }
public void setEnterOrg(String s){ enterOrg=checkSet(s); }
public String getEnterOrgName(){ return checkGet(enterOrgName); }
public void setEnterOrgName(String s){ enterOrgName=checkSet(s); }
public String getOwnership(){ return checkGet(ownership); }
public void setOwnership(String s){ ownership=checkSet(s); }
public String getOwnershipQuery(){ return checkGet(ownershipQuery); }
public void setOwnershipQuery(String s){ ownershipQuery=checkSet(s); }
public String getCaseNo(){ return checkGet(caseNo); }
public void setCaseNo(String s){ caseNo=checkSet(s); }
public String getPropertyNo(){ return checkGet(propertyNo); }
public void setPropertyNo(String s){ propertyNo=checkSet(s); }
public String getPropertyNoName(){ return checkGet(propertyNoName); }
public void setPropertyNoName(String s){ propertyNoName=checkSet(s); }
public String getLotNo(){ return checkGet(lotNo); }
public void setLotNo(String s){ lotNo=checkSet(s); }
public String getSerialNo(){ return checkGet(serialNo); }
public void setSerialNo(String s){ serialNo=checkSet(s); }
public String getPropertyName1(){ return checkGet(propertyName1); }
public void setPropertyName1(String s){ propertyName1=checkSet(s); }
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
public String getBuyDate(){ return checkGet(buyDate); }
public void setBuyDate(String s){ buyDate=checkSet(s); }
public String getVerify(){ return checkGet(verify); }
public void setVerify(String s){ verify=checkSet(s); }
public String getPropertyKind(){ return checkGet(propertyKind); }
public void setPropertyKind(String s){ propertyKind=checkSet(s); }
public String getFundType(){ return checkGet(fundType); }
public void setFundType(String s){ fundType=checkSet(s); }
public String getValuable(){ return checkGet(valuable); }
public void setValuable(String s){ valuable=checkSet(s); }
public String getBookAmount(){ return checkGet(bookAmount); }
public void setBookAmount(String s){ bookAmount=checkSet(s); }
public String getBookUnit(){ return checkGet(bookUnit); }
public void setBookUnit(String s){ bookUnit=checkSet(s); }
public String getBookValue(){ return checkGet(bookValue); }
public void setBookValue(String s){ bookValue=checkSet(s); }
public String getNameplate(){ return checkGet(nameplate); }
public void setNameplate(String s){ nameplate=checkSet(s); }
public String getSpecification(){ return checkGet(specification); }
public void setSpecification(String s){ specification=checkSet(s); }
public String getSourceDate(){ return checkGet(sourceDate); }
public void setSourceDate(String s){ sourceDate=checkSet(s); }
public String getOldMoveDate(){ return checkGet(oldMoveDate); }
public void setOldMoveDate(String s){ oldMoveDate=checkSet(s); }
public String getOldKeepUnit(){ return checkGet(oldKeepUnit); }
public void setOldKeepUnit(String s){ oldKeepUnit=checkSet(s); }
public String getOldKeeper(){ return checkGet(oldKeeper); }
public void setOldKeeper(String s){ oldKeeper=checkSet(s); }
public String getOldUseUnit(){ return checkGet(oldUseUnit); }
public void setOldUseUnit(String s){ oldUseUnit=checkSet(s); }
public String getOldUserNo(){ return checkGet(oldUserNo); }
public void setOldUserNo(String s){ oldUserNo=checkSet(s); }
public String getOldPlace(){ return checkGet(oldPlace); }
public void setOldPlace(String s){ oldPlace=checkSet(s); }
public String getNewMoveDate(){ return checkGet(newMoveDate); }
public void setNewMoveDate(String s){ newMoveDate=checkSet(s); }
public String getNewKeepUnit(){ return checkGet(newKeepUnit); }
public void setNewKeepUnit(String s){ newKeepUnit=checkSet(s); }
public String getNewKeeper(){ return checkGet(newKeeper); }
public void setNewKeeper(String s){ newKeeper=checkSet(s); }
public String getNewUseUnit(){ return checkGet(newUseUnit); }
public void setNewUseUnit(String s){ newUseUnit=checkSet(s); }
public String getNewUserNo(){ return checkGet(newUserNo); }
public void setNewUserNo(String s){ newUserNo=checkSet(s); }
public String getNewPlace(){ return checkGet(newPlace); }
public void setNewPlace(String s){ newPlace=checkSet(s); }
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
public String getDifferenceKind() {return checkGet(this.differenceKind);}
public void setDifferenceKind(String s) {differenceKind = checkSet(s);}
public String getCaseSerialNo() {return checkGet(caseSerialNo);}
public void setCaseSerialNo(String s) {this.caseSerialNo = checkSet(s);}
public String getOldUserNote() {return checkGet(oldUserNote);}
public void setOldUserNote(String s) {this.oldUserNote = checkSet(s);}
public String getOldPlace1() {return checkGet(oldPlace1);}
public void setOldPlace1(String s) {this.oldPlace1 = checkSet(s);}
public String getOldPlaceName() {return checkGet(oldPlaceName);}
public void setOldPlaceName(String s) {this.oldPlaceName = checkSet(s);}
public String getNewUserNote() {return checkGet(newUserNote);}
public void setNewUserNote(String s) {this.newUserNote = checkSet(s);}
public String getNewPlace1() {return checkGet(newPlace1);}
public void setNewPlace1(String s) {this.newPlace1 = checkSet(s);}
public String getNewPlace1Name() {return checkGet(newPlace1Name);}
public void setNewPlace1Name(String s) {this.newPlace1Name = checkSet(s);
}
public String getMoveDate(){ return checkGet(moveDate); }
public void setMoveDate(String s){ moveDate=checkSet(s); }
public String getInitDtl(){ return checkGet(initDtl); }
public void setInitDtl(String s){ initDtl=checkSet(s); }
private String roleid;
public String getRoleid() {return checkGet(roleid);}
public void setRoleid(String roleid) {this.roleid = checkSet(roleid);}

	private String UNTNE008F_currentPage;
	public String getUNTNE008F_currentPage() { return checkGet(UNTNE008F_currentPage); }
	public void setUNTNE008F_currentPage(String UNTNE008F_currentPage) {
		this.UNTNE008F_currentPage = checkSet(UNTNE008F_currentPage);
	}
	
	private String PREPAGE_currentPage;
	public String getPREPAGE_currentPage() { return checkGet(PREPAGE_currentPage); }
	public void setPREPAGE_currentPage(String PREPAGE_currentPage) {
		this.PREPAGE_currentPage = checkSet(PREPAGE_currentPage);
	}
	
	@Override
	public void insert() throws Exception {
		try {
			super.insert();
		} catch (Exception e) {
			this.setErrorMsg("新增失敗!" + e.getMessage());
		}
	}

//傳回執行insert前之檢查sql
protected String[][] getInsertCheckSQL(){
	Database db = new Database();
	ResultSet rs;
	String[][] checkSQLArray = new String[3][4];	
	checkSQLArray[0][0]=" select count(*) as checkResult from UNTNE_MOVEDETAIL where 1=1 " + 
	" and enterOrg = " + Common.sqlChar(enterOrg) + 
	" and ownership = " + Common.sqlChar(ownership) + 
	" and differencekind = " + Common.sqlChar(differenceKind) +
	" and propertyNo = " + Common.sqlChar(propertyNo) + 
	" and serialNo = " + Common.sqlChar(serialNo) +
	" and verify = 'N' " + 
	"";
	checkSQLArray[0][1]=">";
	checkSQLArray[0][2]="0";
	checkSQLArray[0][3]="非消耗品保管使用異動作業存在未審核的資料，請重新輸入！！";

	//檢查同一筆「入帳機關+權屬+財產編號+財產分號」是否減少作業已存在未審核的資料
	checkSQLArray[1][0]=" select count(*) as checkResult from UNTNE_ReduceDetail where 1=1 " + 
		" and enterOrg = " + Common.sqlChar(enterOrg) + 
		" and ownership = " + Common.sqlChar(ownership) + 
		" and differencekind = " + Common.sqlChar(differenceKind) +
		" and propertyNo = " + Common.sqlChar(propertyNo) + 
		" and serialNo = " + Common.sqlChar(serialNo) +
		" and verify = 'N' " + 
		"";
	checkSQLArray[1][1]=">";
	checkSQLArray[1][2]="0";
	checkSQLArray[1][3]="非消耗品減少作業存在未審核的資料，請重新輸入！";

	//檢查同一筆「入帳機關+權屬+財產編號+財產分號」是否增減值作業已存在未審核的資料
	checkSQLArray[2][0]=" select count(*) as checkResult from UNTNE_AdjustDetail where 1=1 " + 
		" and enterOrg = " + Common.sqlChar(enterOrg) + 
		" and ownership = " + Common.sqlChar(ownership) + 
		" and differencekind = " + Common.sqlChar(differenceKind) +
		" and propertyNo = " + Common.sqlChar(propertyNo) + 
		" and serialNo = " + Common.sqlChar(serialNo) +
		" and verify = 'N' " + 
		"";
	checkSQLArray[2][1]=">";
	checkSQLArray[2][2]="0";
	checkSQLArray[2][3]="非消耗品增減值作業存在未審核的資料，請重新輸入！";
	
	//取得序號
	String sql="select ISNULL(max(caseSerialNo),0)+1 as caseSerialNo "+
		" from UNTNE_MoveDetail " +
		" where enterOrg = " + Common.sqlChar(enterOrg) + 
		" and caseNo = " + Common.sqlChar(caseNo) +
//		" and ownership = " + Common.sqlChar(ownership) + 
//		" and propertyNo = " + Common.sqlChar(propertyNo) + 
//		" and serialNo = " + Common.sqlChar(serialNo) +
//		" and differencekind = " + Common.sqlChar(differenceKind) +
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
	execSQLArray[0]=" insert into UNTNE_MoveDetail(" +
			" enterOrg,"+
			" ownership,"+
			" caseNo,"+
			" propertyNo,"+
			" lotNo,"+
			" serialNo,"+
			" propertyName1,"+
			" otherMaterial,"+
			" otherPropertyUnit,"+
			" otherLimitYear,"+
			" buyDate,"+
			" verify,"+
			" propertyKind,"+
			" fundType,"+
			" valuable,"+
			" bookAmount,"+
			" bookUnit,"+
			" bookValue,"+
			" nameplate,"+
			" specification,"+
			" sourceDate,"+
			" oldMoveDate,"+
			" oldKeepUnit,"+
			" oldKeeper,"+
			" oldUseUnit,"+
			" oldUserNo,"+
			" oldPlace,"+
			" newKeepUnit,"+
			" newKeeper,"+
			" newUseUnit,"+
			" newUserNo,"+
			" newPlace,"+
			" useYear,"+
			" useMonth,"+
			" notes,"+
			" oldPropertyNo,"+
			" oldSerialNo,"+
			" editID,"+
			" editDate,"+
			" editTime,"+
			" differencekind,"+
			" caseSerialNo,"+
			" oldPlace1,"+
			" oldUserNote,"+
			" newPlace1,"+
			" newUserNote"+
		" )Values(" +
			Common.sqlChar(this.getEnterOrg()) + "," +
			Common.sqlChar(this.getOwnership()) + "," +
			Common.sqlChar(this.getCaseNo()) + "," +
			Common.sqlChar(this.getPropertyNo()) + "," +
			Common.sqlChar(this.getLotNo()) + "," +
			Common.sqlChar(this.getSerialNo()) + "," +
			Common.sqlChar(this.getPropertyName1()) + "," +
			Common.sqlChar(this.getOtherMaterial()) + "," +
			Common.sqlChar(this.getOtherPropertyUnit()) + "," +
			Common.sqlChar(this.getOtherLimitYear()) + "," +
			Common.sqlChar(ul._transToCE_Year(this.getBuyDate())) + "," +
			Common.sqlChar(this.getVerify()) + "," +
			Common.sqlChar(this.getPropertyKind()) + "," +
			Common.sqlChar(this.getFundType()) + "," +
			Common.sqlChar(this.getValuable()) + "," +
			Common.sqlChar(this.getBookAmount()) + "," +
			Common.sqlChar(this.getBookUnit()) + "," +
			Common.sqlChar(this.getBookValue()) + "," +
			Common.sqlChar(this.getNameplate()) + "," +
			Common.sqlChar(this.getSpecification()) + "," +
			Common.sqlChar(ul._transToCE_Year(this.getSourceDate())) + "," +
			Common.sqlChar(ul._transToCE_Year(this.getOldMoveDate())) + "," +
			Common.sqlChar(this.getOldKeepUnit()) + "," +
			Common.sqlChar(this.getOldKeeper()) + "," +
			Common.sqlChar(this.getOldUseUnit()) + "," +
			Common.sqlChar(this.getOldUserNo()) + "," +
			Common.sqlChar(this.getOldPlace()) + "," +
			Common.sqlChar(this.getNewKeepUnit()) + "," +
			Common.sqlChar(this.getNewKeeper()) + "," +
			Common.sqlChar(this.getNewUseUnit()) + "," +
			Common.sqlChar(this.getNewUserNo()) + "," +
			Common.sqlChar(this.getNewPlace()) + "," +
			Common.sqlChar(this.getUseYear()) + "," +
			Common.sqlChar(this.getUseMonth()) + "," +
			Common.sqlChar(this.getNotes()) + "," +
			Common.sqlChar(this.getOldPropertyNo()) + "," +
			Common.sqlChar(this.getOldSerialNo()) + "," +
			Common.sqlChar(getEditID()) + "," +
			Common.sqlChar(ul._transToCE_Year(getEditDate())) + "," +
			Common.sqlChar(getEditTime()) + "," +
			Common.sqlChar(this.getDifferenceKind()) + "," +
			Common.sqlChar(this.getCaseSerialNo()) + "," +
			Common.sqlChar(this.getOldPlace1()) + "," +
			Common.sqlChar(this.getOldUserNote()) + "," +
			Common.sqlChar(this.getNewPlace1()) + "," +
			Common.sqlChar(this.getNewUserNote()) + ")" ;
	return execSQLArray;
}


//傳回update sql
protected String[] getUpdateSQL(){
	UNTCH_Tools ul = new UNTCH_Tools();
	String[] execSQLArray = new String[1];
	execSQLArray[0]=" update UNTNE_MoveDetail set " +
			" lotNo = " + Common.sqlChar(this.getLotNo()) + "," +
			" propertyName1 = " + Common.sqlChar(this.getPropertyName1()) + "," +
			" otherMaterial = " + Common.sqlChar(this.getOtherMaterial()) + "," +
			" otherPropertyUnit = " + Common.sqlChar(this.getOtherPropertyUnit()) + "," +
			" otherLimitYear = " + Common.sqlChar(this.getOtherLimitYear()) + "," +
			" buyDate = " + Common.sqlChar(ul._transToCE_Year(this.getBuyDate())) + "," +
			" verify = " + Common.sqlChar(this.getVerify()) + "," +
			" propertyKind = " + Common.sqlChar(this.getPropertyKind()) + "," +
			" fundType = " + Common.sqlChar(this.getFundType()) + "," +
			" valuable = " + Common.sqlChar(this.getValuable()) + "," +
			" bookAmount = " + Common.sqlChar(this.getBookAmount()) + "," +
			" bookUnit = " + Common.sqlChar(this.getBookUnit()) + "," +
			" bookValue = " + Common.sqlChar(this.getBookValue()) + "," +
			" nameplate = " + Common.sqlChar(this.getNameplate()) + "," +
			" specification = " + Common.sqlChar(this.getSpecification()) + "," +
			" sourceDate = " + Common.sqlChar(ul._transToCE_Year(this.getSourceDate())) + "," +
			" oldMoveDate = " + Common.sqlChar(ul._transToCE_Year(this.getOldMoveDate())) + "," +
			" oldKeepUnit = " + Common.sqlChar(this.getOldKeepUnit()) + "," +
			" oldKeeper = " + Common.sqlChar(this.getOldKeeper()) + "," +
			" oldUseUnit = " + Common.sqlChar(this.getOldUseUnit()) + "," +
			" oldUserNo = " + Common.sqlChar(this.getOldUserNo()) + "," +
			" oldPlace = " + Common.sqlChar(this.getOldPlace()) + "," +
			" newKeepUnit = " + Common.sqlChar(this.getNewKeepUnit()) + "," +
			" newKeeper = " + Common.sqlChar(this.getNewKeeper()) + "," +
			" newUseUnit = " + Common.sqlChar(this.getNewUseUnit()) + "," +
			" newUserNo = " + Common.sqlChar(this.getNewUserNo()) + "," +
			" newPlace = " + Common.sqlChar(this.getNewPlace()) + "," +
			" useYear = " + Common.sqlChar(this.getUseYear()) + "," +
			" useMonth = " + Common.sqlChar(this.getUseMonth()) + "," +
			" notes = " + Common.sqlChar(this.getNotes()) + "," +
			" oldPropertyNo = " + Common.sqlChar(this.getOldPropertyNo()) + "," +
			" oldSerialNo = " + Common.sqlChar(this.getOldSerialNo()) + "," +
			" editID = " + Common.sqlChar(getEditID()) + "," +
			" editDate = " + Common.sqlChar(ul._transToCE_Year(getEditDate())) + "," +
			" editTime = " + Common.sqlChar(getEditTime()) + "," +
			" differenceKind = " + Common.sqlChar(this.getDifferenceKind()) + "," +
			" caseSerialNo = " + Common.sqlChar(this.getCaseSerialNo()) + "," +
			" oldPlace1 = " + Common.sqlChar(this.getOldPlace1()) + "," +
			" oldUserNote = " + Common.sqlChar(this.getOldUserNote()) + "," +
			" newPlace1 = " + Common.sqlChar(this.getNewPlace1()) + "," +
			" newUserNote = " + Common.sqlChar(this.getNewUserNote()) + 
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
	execSQLArray[0]=" delete UNTNE_MoveDetail where 1=1 " +
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

public UNTNE009F  queryOne() throws Exception{
	UNTCH_Tools ul = new UNTCH_Tools();
	Database db = new Database();
	UNTNE009F obj = this;
	try {
		String sql=" select (select b.organSName from SYSCA_Organ b where b.organID = a.enterOrg ) organSName, " +
				" a.enterOrg, a.ownership, a.caseNo, a.propertyNo, " +
				" c.propertyName, c.material, c.propertyUnit, c.limitYear, a.otherMaterial, a.otherPropertyUnit, a.otherLimitYear, " +
				" a.differenceKind,a.caseSerialNo,a.oldPlace1,a.oldUserNote,a.newPlace1,a.newUserNote," +
				" (select f.placeName from SYSCA_Place f where a.enterOrg=f.enterOrg and a.oldplace1=f.placeNo) as oldPlaceName," +
				" (select f.placeName from SYSCA_Place f where a.enterOrg=f.enterOrg and a.newplace1=f.placeNo) as newPlaceName," +
				" a.lotNo, a.serialNo, a.propertyName1, a.buyDate, a.verify, a.propertyKind, a.fundType, a.valuable, a.bookAmount, a.bookUnit, a.bookValue, a.nameplate, a.specification, a.sourceDate, a.oldMoveDate, a.oldKeepUnit, a.oldKeeper, a.oldUseUnit, a.oldUserNo, a.oldPlace, a.newMoveDate, a.newKeepUnit, a.newKeeper, a.newUseUnit, a.newUserNo, a.newPlace, a.useYear, a.useMonth, a.notes, a.oldPropertyNo, a.oldSerialNo, a.editID, a.editDate, a.editTime  "+
				" from UNTNE_MoveDetail a, SYSPK_PropertyCode2 c where 1=1 " +
				" and a.enterOrg = " + Common.sqlChar(enterOrg) +
				" and a.ownership = " + Common.sqlChar(ownership) +
				" and a.caseNo = " + Common.sqlChar(caseNo) +
				" and a.propertyNo = " + Common.sqlChar(propertyNo) +
				" and a.serialNo = " + Common.sqlChar(serialNo) +
				" and a.differenceKind = " + Common.sqlChar(differenceKind) +
				" and c.enterOrg = a.enterOrg " +
				" and c.propertyNo = a.propertyNo ";
//System.out.println("=> "+sql);		
		ResultSet rs = db.querySQL(sql);
		if (rs.next()){
			obj.setEnterOrg(rs.getString("enterOrg"));
			obj.setEnterOrgName(rs.getString("organSName"));
			obj.setOwnership(rs.getString("ownership"));
			obj.setCaseNo(rs.getString("caseNo"));
			obj.setPropertyNo(rs.getString("propertyNo"));
			obj.setPropertyNoName(rs.getString("propertyName"));
			obj.setLotNo(rs.getString("lotNo"));
			obj.setSerialNo(rs.getString("serialNo"));
			obj.setPropertyName1(rs.getString("propertyName1"));
			obj.setMaterial(rs.getString("material"));
			obj.setPropertyUnit(rs.getString("propertyUnit"));
			obj.setLimitYear(rs.getString("limitYear"));
			obj.setOtherMaterial(rs.getString("otherMaterial"));
			obj.setOtherPropertyUnit(rs.getString("otherPropertyUnit"));
			obj.setOtherLimitYear(rs.getString("otherLimitYear"));
			obj.setBuyDate(ul._transToROC_Year(rs.getString("buyDate")));
			obj.setVerify(rs.getString("verify"));
			obj.setPropertyKind(rs.getString("propertyKind"));
			obj.setFundType(rs.getString("fundType"));
			obj.setValuable(rs.getString("valuable"));
			obj.setBookAmount(rs.getString("bookAmount"));
			obj.setBookUnit(rs.getString("bookUnit"));
			obj.setBookValue(rs.getString("bookValue"));
			obj.setNameplate(rs.getString("nameplate"));
			obj.setSpecification(rs.getString("specification"));
			obj.setSourceDate(ul._transToROC_Year(rs.getString("sourceDate")));
			obj.setOldMoveDate(ul._transToROC_Year(rs.getString("oldMoveDate")));
			obj.setOldKeepUnit(rs.getString("oldKeepUnit"));
			obj.setOldKeeper(rs.getString("oldKeeper"));
			obj.setOldUseUnit(rs.getString("oldUseUnit"));
			obj.setOldUserNo(rs.getString("oldUserNo"));
			obj.setOldPlace(rs.getString("oldPlace"));
			obj.setNewMoveDate(ul._transToROC_Year(rs.getString("newMoveDate")));
			obj.setNewKeepUnit(rs.getString("newKeepUnit"));
			obj.setNewKeeper(rs.getString("newKeeper"));
			obj.setNewUseUnit(rs.getString("newUseUnit"));
			obj.setNewUserNo(rs.getString("newUserNo"));
			obj.setNewPlace(rs.getString("newPlace"));
			obj.setUseYear(rs.getString("useYear"));
			obj.setUseMonth(rs.getString("useMonth"));
			obj.setNotes(rs.getString("notes"));
			obj.setOldPropertyNo(rs.getString("oldPropertyNo"));
			obj.setOldSerialNo(rs.getString("oldSerialNo"));
			obj.setEditID(rs.getString("editID"));
			obj.setEditDate(ul._transToROC_Year(rs.getString("editDate")));
			obj.setEditTime(rs.getString("editTime"));
			obj.setDifferenceKind(rs.getString("differenceKind"));
			obj.setCaseSerialNo(rs.getString("caseSerialNo"));
			obj.setOldPlace1(rs.getString("oldPlace1"));
			obj.setOldUserNote(rs.getString("oldUserNote"));
			obj.setNewPlace1(rs.getString("newPlace1"));
			obj.setNewUserNote(rs.getString("newUserNote"));
			obj.setOldPlaceName(rs.getString("oldPlaceName"));
			obj.setNewPlace1Name(rs.getString("newPlaceName"));
		}
		setStateQueryOneSuccess();
		
		queryProofNo();
	} catch (Exception e) {
		e.printStackTrace();
	} finally {
		db.closeAll();
	}
	return obj;
}
	
	private void queryProofNo(){
		String sql = "select proofno from UNTNE_MOVEPROOF where 1=1" +
						" and enterorg = " + Common.sqlChar(this.getEnterOrg()) +
						" and ownership = " + Common.sqlChar(this.getOwnership()) +
						//" and differencekind = " + Common.sqlChar(this.getDifferenceKind()) +  搞什麼啊 又沒這個欄位
						" and caseno = " + Common.sqlChar(this.getCaseNo());
		
		Database db = null;
		ResultSet rs = null;
		try{
			db = new Database();
			rs = db.querySQL(sql);
			if(rs.next()){
				this.setProofNo(rs.getString(1));
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			db.closeAll();
		}
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
	int counter=0;
	try {
		String sql=" select a.enterOrg, a.ownership, a.caseNo, a.propertyNo, a.serialNo, a.propertyKind, a.oldKeepUnit, a.oldKeeper, a.oldPlace, a.newMoveDate, a.newKeepUnit, a.newKeeper, a.newPlace,a.differenceKind,a.caseSerialNo,a.lotno, "+ "\n" +
				" (select b.organSName from SYSCA_Organ b where b.organID = a.enterOrg ) organSName, " + "\n" +
				" (select z.codeName from sysca_code z where 1=1 and (z.codeKindID='owa' or z.codeKindID='OWA') and a.ownership=z.codeID) as ownershipName, " + "\n" +
				" (select c.propertyName from SYSPK_PropertyCode2 c where c.propertyNo = a.propertyNo and c.enterOrg = a.enterOrg and c.propertyType='1') propertyName, " + "\n" +
				" (select d.codeName from SYSCA_Code d where d.codeKindID='PKD' and d.codeID=a.propertyKind ) propertyKindName, " + "\n" +
				" (select e.unitName from UNTMP_KeepUnit e where e.enterOrg=a.enterOrg and e.unitNo=a.oldKeepUnit ) oldKeepUnitName, " + "\n" +
				" (select f.keeperName from UNTMP_Keeper f where f.enterOrg=a.enterOrg and f.keeperNo=a.oldKeeper ) oldKeeperName, " + "\n" +
				" (select g.unitName from UNTMP_KeepUnit g where g.enterOrg=a.enterOrg and g.unitNo=a.newKeepUnit ) newKeepUnitName, " + "\n" +
				" (select I.placeName from SYSCA_PLACE I where I.enterOrg=a.enterOrg and I.placeNo=a.newplace1 ) newPlace1Name, " + "\n" +
				" (select h.keeperName from UNTMP_Keeper h where h.enterOrg=a.enterOrg and h.keeperNo=a.newKeeper ) newKeeperName " + "\n" +
				" from UNTNE_MoveDetail a"+
				" left join UNTNE_MoveProof b on b.enterOrg=a.enterOrg and b.caseNo=a.caseNo"+
				" where 1=1 " +  "\n" +
				"";
		
		if ("MoveProof".equals(getQuerySelect()) || "".equals(getQuerySelect())) {
			sql+=" and a.enterOrg = " + Common.sqlChar(getEnterOrg()) ;
			if(!"".equals(getOwnership())){
			    sql+=" and a.ownership = " + Common.sqlChar(getOwnership()) ;
			}else{
			    sql+=" and a.ownership = " + Common.sqlChar(getOwnershipQuery()) ;
			}
			sql+=" and a.caseNo=" + Common.sqlChar(getCaseNo());
		} else {
			if (!"".equals(getQ_enterOrg())) {
				sql+=" and a.enterOrg = " + Common.sqlChar(getQ_enterOrg()) ;
			} else {
				if (!getIsAdminManager().equalsIgnoreCase("Y")) {
					if ("Y".equals(getIsOrganManager())) {					
						sql += " and a.enterOrg like '" + getOrganID().substring(0,5) + "%' ";					
					} else {
						sql+=" and a.enterOrg = " + Common.sqlChar(getOrganID()) ;
					}
				}
			}
			if (!"".equals(getQ_ownership()))
				sql+=" and a.ownership = " + Common.sqlChar(getQ_ownership()) ;
			if (!"".equals(getQ_caseNoS()))
				sql+=" and a.caseNo >= rpad(" + Common.sqlChar(getQ_caseNoS()) + ",10,'0')";
			if (!"".equals(getQ_caseNoE()))
			    sql+=" and a.caseNo <= rpad(" + Common.sqlChar(getQ_caseNoE()) + ",10,'9')";
			if (!"".equals(getQ_moveDateS()))
				sql+=" and a.newMoveDate >= " + Common.sqlChar(ul._transToCE_Year(getQ_moveDateS())) ;
			if (!"".equals(getQ_moveDateE()))
				sql+=" and a.newMoveDate <= " + Common.sqlChar(ul._transToCE_Year(getQ_moveDateE())) ;
			if (!"".equals(getQ_verify()))
				sql+=" and a.verify = " + Common.sqlChar(getQ_verify()) ;
			if (!"".equals(getQ_caseName()))
			    sql+=" and b.caseName like " + Common.sqlChar("%"+getQ_caseName()+"%") ;
			if (!"".equals(getQ_writeDateS()))
				sql+=" and b.writeDate >= " + Common.sqlChar(ul._transToCE_Year(getQ_writeDateS())) ;
			if (!"".equals(getQ_writeDateE()))
				sql+=" and b.writeDate <= " + Common.sqlChar(ul._transToCE_Year(getQ_writeDateE())) ;
			if (!"".equals(getQ_proofDoc()))
				sql+=" and b.proofDoc like " + Common.sqlChar("%"+getQ_proofDoc()+"%") ;
			if (!"".equals(getQ_proofNoS()))
				sql+=" and b.proofNo >= " + Common.sqlChar(getQ_proofNoS()) ;
			if (!"".equals(getQ_proofNoE()))
				sql+=" and b.proofNo <= " + Common.sqlChar(getQ_proofNoE()) ;
			if (!"".equals(getQ_propertyNoS()))
				sql+=" and a.propertyNo >= " + Common.sqlChar(getQ_propertyNoS()) ;
			if (!"".equals(getQ_propertyNoE()))
				sql+=" and a.propertyNo <= " + Common.sqlChar(getQ_propertyNoE()) ;
			if (!"".equals(getQ_serialNoS()))
				sql+=" and a.serialNo >= " + Common.sqlChar(getQ_serialNoS()) ;
			if (!"".equals(getQ_serialNoE()))
				sql+=" and a.serialNo <= " + Common.sqlChar(getQ_serialNoE()) ;
			if (!"".equals(getQ_propertyKind()))
				sql+=" and a.propertyKind = " + Common.sqlChar(getQ_propertyKind());
			if (!"".equals(getQ_fundType()))
				sql+=" and a.fundType = " + Common.sqlChar(getQ_fundType()) ;
			if (!"".equals(getQ_valuable()))
				sql+=" and a.valuable = " + Common.sqlChar(getQ_valuable()) ;
			if (!"".equals(getQ_oldKeepUnit()))
				sql+=" and a.oldKeepUnit = " + Common.sqlChar(getQ_oldKeepUnit()) ;
			if (!"".equals(getQ_oldKeeper()))
				sql+=" and a.oldKeeper = " + Common.sqlChar(getQ_oldKeeper()) ;
			if (!"".equals(getQ_oldUseUnit()))
				sql+=" and a.oldUseUnit = " + Common.sqlChar(getQ_oldUseUnit()) ;
			if (!"".equals(getQ_oldUserNo()))
				sql+=" and a.oldUserNo = " + Common.sqlChar(getQ_oldUserNo()) ;
			if (!"".equals(getQ_newKeepUnit()))
				sql+=" and a.newKeepUnit = " + Common.sqlChar(getQ_newKeepUnit()) ;
			if (!"".equals(getQ_newKeeper()))
				sql+=" and a.newKeeper = " + Common.sqlChar(getQ_newKeeper()) ;
			if (!"".equals(getQ_newUseUnit()))
				sql+=" and a.newUseUnit = " + Common.sqlChar(getQ_newUseUnit()) ;
			if (!"".equals(getQ_newUserNo()))
				sql+=" and a.newUserNo = " + Common.sqlChar(getQ_newUserNo()) ;
			if (!"".equals(getQ_oldUserNote()))
				sql+=" and b.oldusernote like " + Common.sqlChar("%"+getQ_oldUserNote()+"%") ;
			if (!"".equals(getQ_oldPlace1()))
				sql+=" and b.oldplace1 = " + Common.sqlChar(getQ_oldPlace1()) ;
			if (!"".equals(getQ_oldPlace()))
				sql+=" and b.oldplace like " + Common.sqlChar("%"+getQ_oldPlace()+"%") ;
			if (!"".equals(getQ_newUserNote()))
				sql+=" and b.newusernote like " + Common.sqlChar("%"+getQ_newUserNote()+"%") ;
			if (!"".equals(getQ_newPlace()))
				sql+=" and b.newplace like " + Common.sqlChar("%"+getQ_newPlace()+"%") ;
			if (!"".equals(getQ_newPlace1()))
				sql+=" and b.newplace1 = " + Common.sqlChar(getQ_newPlace1()) ;
			if (!"".equals(getQ_differenceKind()))
				sql+=" and a.differencekind = " + Common.sqlChar(getQ_differenceKind()) ;
			if (!"".equals(getQ_proofYear()))
				sql+=" and b.proofyear = " + Common.sqlChar(getQ_proofYear()) ;
		}
		if ("1".equals(this.getRoleid())){
			sql += " and (" + 
						" a.oldkeeper = " + Common.sqlChar(this.getUserID()) +
						" or" +
						" a.olduserno = " + Common.sqlChar(this.getUserID()) +
						" or" +
						" a.newkeeper = " + Common.sqlChar(this.getUserID()) +
						" or" +
						" a.newuserno = " + Common.sqlChar(this.getUserID()) +
						" or" +
						" b.editid = " + Common.sqlChar(this.getUserID()) +
					")";				
			
		}else if ("2".equals(this.getRoleid())){
			sql += " and (" + 
						" a.oldkeepunit = " + Common.sqlChar(this.getUnitID()) +
						" or" +
						" a.olduseunit = " + Common.sqlChar(this.getUnitID()) +
						" or" +
						" a.newkeepunit = " + Common.sqlChar(this.getUnitID()) +
						" or" +
						" a.newuseunit = " + Common.sqlChar(this.getUnitID()) +
						" or" +
						" b.editid = " + Common.sqlChar(this.getUserID()) +
						" or" +
						" a.oldkeeper = " + Common.sqlChar(this.getUserID()) +
						" or" +
						" a.olduserno = " + Common.sqlChar(this.getUserID()) +
						" or" +
						" a.newkeeper = " + Common.sqlChar(this.getUserID()) +
						" or" +
						" a.newuserno = " + Common.sqlChar(this.getUserID()) +
					")";				
			
		}else if ("3".equals(this.getRoleid())){
			
		}
		
		
		sql += (" order by a.enterOrg, a.ownership, a.caseNo, a.caseSerialNo desc" );
		//System.out.println("All: "+sql);
		
		ResultSet rs = db.querySQL(sql, true);
		processCurrentPageAttribute(rs);
		
		if (getTotalRecord() > 0) {
			int count = getRecordStart();
			int end = getRecordEnd();
			do {
				if (count > end)
					break;
				String rowArray[]=new String[24];
				rowArray[0]=rs.getString("enterOrg");
				rowArray[1]=rs.getString("organSName");
				rowArray[2]=rs.getString("ownership");
				rowArray[3]=rs.getString("ownershipName");
				rowArray[4]=rs.getString("caseNo"); 
				rowArray[5]=rs.getString("caseSerialNo");
				rowArray[6]=rs.getString("propertyNo");
				rowArray[7]=rs.getString("propertyName");
				rowArray[8]=rs.getString("serialNo"); 
				rowArray[9]=rs.getString("propertyKind");
				rowArray[10]=rs.getString("propertyKindName");
				rowArray[11]=rs.getString("oldKeepUnit");
				rowArray[12]=rs.getString("oldKeepUnitName");
				rowArray[13]=rs.getString("oldKeeper");
				rowArray[14]=rs.getString("oldKeeperName");
				rowArray[15]=rs.getString("oldPlace"); 
				rowArray[16]=ul._transToROC_Year(rs.getString("newMoveDate")); 
				rowArray[17]=rs.getString("newKeepUnit"); 
				rowArray[18]=rs.getString("newKeepUnitName");
				rowArray[19]=rs.getString("newKeeper");
				rowArray[20]=rs.getString("newKeeperName");
				rowArray[21]=rs.getString("newPlace1Name");
				rowArray[22]=rs.getString("differenceKind");
				rowArray[23]=rs.getString("lotno");
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


