/*
*<br>程式目的：非消耗品增減值作業－增減值單明細
*<br>程式代號：untne025f
*<br>程式日期：0941121
*<br>程式作者：judy
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

public class UNTNE025F extends UNTNE024F{

String tOriginalUnit;
String enterOrg;
String enterOrgName;
String ownership;
String caseNo;
String propertyNo;
String propertyNoName;
String lotNo;
String serialNo;
String propertyName1;
String cause;
String adjustDate;
String verify;
String propertyKind;
String fundType;
String valuable;
String sourceDate;
String bookAmount;
String oldBookUnit;
String oldBookValue;
String newBookUnit;
String newBookValue;

String bookNotes;
String keepUnit;
String keepUnitName;
String keeper;
String keeperName;
String useUnit;
String useUnitName;
String userNo;
String userName;
String place;

String notes;
String oldPropertyNo;
String oldPropertyName;
String oldSerialNo;
String bookValue;
String material;
String otherMaterial;
String propertyUnit;
String otherPropertyUnit;
String limitYear;
String otherLimitYear;
String differenceKind;
String caseSerialNo;
String causeName;
String userNote;
String place1;
String place1Name;
String buyDate;

public String getMaterial(){ return checkGet(material); }
public void setMaterial(String s){ material=checkSet(s); }
public String getOtherMaterial(){ return checkGet(otherMaterial); }
public void setOtherMaterial(String s){ otherMaterial=checkSet(s); }
public String getPropertyUnit(){ return checkGet(propertyUnit); }
public void setPropertyUnit(String s){ propertyUnit=checkSet(s); }
public String getOtherPropertyUnit(){ return checkGet(otherPropertyUnit); }
public void setOtherPropertyUnit(String s){ otherPropertyUnit=checkSet(s); }
public String getLimitYear(){ return checkGet(limitYear); }
public void setLimitYear(String s){ limitYear=checkSet(s); }
public String getOtherLimitYear(){ return checkGet(otherLimitYear); }
public void setOtherLimitYear(String s){ otherLimitYear=checkSet(s); }

public String getTOriginalUnit(){ return checkGet(tOriginalUnit); }
public void setTOriginalUnit(String s){ tOriginalUnit=checkSet(s); }
public String getBookValue(){ return checkGet(bookValue); }
public void setBookValue(String s){ bookValue=checkSet(s); }
public String getEnterOrg(){ return checkGet(enterOrg); }
public void setEnterOrg(String s){ enterOrg=checkSet(s); }
public String getEnterOrgName(){ return checkGet(enterOrgName); }
public void setEnterOrgName(String s){ enterOrgName=checkSet(s); }
public String getOwnership(){ return checkGet(ownership); }
public void setOwnership(String s){ ownership=checkSet(s); }
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
public String getCause(){ return checkGet(cause); }
public void setCause(String s){ cause=checkSet(s); }

public String getAdjustDate(){ return checkGet(adjustDate); }
public void setAdjustDate(String s){ adjustDate=checkSet(s); }
public String getVerify(){ return checkGet(verify); }
public void setVerify(String s){ verify=checkSet(s); }
public String getPropertyKind(){ return checkGet(propertyKind); }
public void setPropertyKind(String s){ propertyKind=checkSet(s); }
public String getFundType(){ return checkGet(fundType); }
public void setFundType(String s){ fundType=checkSet(s); }
public String getValuable(){ return checkGet(valuable); }
public void setValuable(String s){ valuable=checkSet(s); }

public String getSourceDate(){ return checkGet(sourceDate); }
public void setSourceDate(String s){ sourceDate=checkSet(s); }

public String getBookAmount(){ return checkGet(bookAmount); }
public void setBookAmount(String s){ bookAmount=checkSet(s); }
public String getOldBookUnit(){ return checkGet(oldBookUnit); }
public void setOldBookUnit(String s){ oldBookUnit=checkSet(s); }

public String getOldBookValue(){ return checkGet(oldBookValue); }
public void setOldBookValue(String s){ oldBookValue=checkSet(s); }

public String getNewBookUnit(){ return checkGet(newBookUnit); }
public void setNewBookUnit(String s){ newBookUnit=checkSet(s); }

public String getNewBookValue(){ return checkGet(newBookValue); }
public void setNewBookValue(String s){ newBookValue=checkSet(s); }

public String getBookNotes(){ return checkGet(bookNotes); }
public void setBookNotes(String s){ bookNotes=checkSet(s); }

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

public String getNotes(){ return checkGet(notes); }
public void setNotes(String s){ notes=checkSet(s); }
public String getOldPropertyNo(){ return checkGet(oldPropertyNo); }
public void setOldPropertyNo(String s){ oldPropertyNo=checkSet(s); }
public String getOldPropertyName(){ return checkGet(oldPropertyName); }
public void setOldPropertyName(String s){ oldPropertyName=checkSet(s); }
public String getOldSerialNo(){ return checkGet(oldSerialNo); }
public void setOldSerialNo(String s){ oldSerialNo=checkSet(s); }
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
public String getBuyDate(){ return checkGet(buyDate); }
public void setBuyDate(String s){ buyDate=checkSet(s); }
String addBookValue;
String reduceBookValue;


public String getAddBookValue() {return checkGet(addBookValue);}
public void setAddBookValue(String addBookValue) {this.addBookValue = checkSet(addBookValue);}
public String getReduceBookValue() {return checkGet(reduceBookValue);}
public void setReduceBookValue(String reduceBookValue) {this.reduceBookValue = checkSet(reduceBookValue);}
String ownershipQuery;

public String getOwnershipQuery(){ return checkGet(ownershipQuery); }
public void setOwnershipQuery(String s){ ownershipQuery=checkSet(s); }

	
	@Override
	public void insert() throws Exception {
		try {
			super.insert();
		} catch (Exception e) {
			e.printStackTrace();
			this.setStateInsertError();
			this.setErrorMsg(e.getMessage());
		}
	}

//傳回執行insert前之檢查sql
protected String[][] getInsertCheckSQL(){
	Database db = new Database();
	ResultSet rs;
	String[][] checkSQLArray = new String[5][4];
 	checkSQLArray[0][0]=" select count(*) as checkResult from UNTNE_AdjustDetail where 1=1 " + 
						" and enterOrg = " + Common.sqlChar(enterOrg) + 
						" and ownership = " + Common.sqlChar(ownership) +
						" and propertyNo = " + Common.sqlChar(propertyNo) + 
						" and serialNo = " + Common.sqlChar(serialNo) + 
						" and differencekind = " + Common.sqlChar(differenceKind) +
						" and verify = 'N' " +
						"";
	checkSQLArray[0][1]=">";
	checkSQLArray[0][2]="0";
	checkSQLArray[0][3]="非消耗品增減值作業存在未審核的資料，請重新輸入!!";
	
	checkSQLArray[1][0]=" select count(*) as checkResult from UNTNE_Nonexp where 1=1 " + 
						" and enterOrg = " + Common.sqlChar(enterOrg) + 
						" and ownership = " + Common.sqlChar(ownership) + 
						" and propertyNo = " + Common.sqlChar(propertyNo) +
						" and differencekind = " + Common.sqlChar(differenceKind) +
						" and lotNo = " + Common.sqlChar(lotNo) +
						" and dataState = '1' " +
						" and verify = 'Y' " +
						"";
	
	checkSQLArray[1][1]="<=";
	checkSQLArray[1][2]="0";
	checkSQLArray[1][3]="非消耗品主檔-基本資料中找不到該財產編號和批號，請重新輸入！";
	
	checkSQLArray[2][0]=" select count(*) as checkResult from UNTNE_NonexpDetail where 1=1 " + 
						" and enterOrg = " + Common.sqlChar(enterOrg) + 
						" and ownership = " + Common.sqlChar(ownership) + 
						" and propertyNo = " + Common.sqlChar(propertyNo) + 
						" and serialNo = " + Common.sqlChar(serialNo) +
						" and differencekind = " + Common.sqlChar(differenceKind) +
						" and dataState = '1' " +
						" and verify = 'Y' " +
						"";
	
	checkSQLArray[2][1]="<=";
	checkSQLArray[2][2]="0";
	checkSQLArray[2][3]="非消耗品主檔-非消耗品明細中找不到該財產編號和分號，請重新輸入！";
	
	checkSQLArray[3][0]=" select count(*) as checkResult from UNTNE_ReduceDetail where 1=1 " + 
						" and enterOrg = " + Common.sqlChar(enterOrg) + 
						" and ownership = " + Common.sqlChar(ownership) + 
						" and propertyNo = " + Common.sqlChar(propertyNo) + 
						" and serialNo = " + Common.sqlChar(serialNo) +
						" and differencekind = " + Common.sqlChar(differenceKind) +
						" and verify = 'N' " +
						"";
	
	checkSQLArray[3][1]=">";
	checkSQLArray[3][2]="0";
	checkSQLArray[3][3]="非消耗品減少作業存在未審核的資料，請重新輸入!!";
	
	//取得序號
	String sql="select ISNULL(max(caseSerialNo),0)+1 as caseSerialNo "+
		" from UNTNE_ReduceDetail " +
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
	execSQLArray[0]=" insert into UNTNE_AdjustDetail(" +
			" enterOrg,"+
			" ownership,"+
			" caseNo,"+
			" propertyNo,"+
			" lotNo,"+
			" serialNo,"+
			" propertyName1,"+
			" cause,"+
			" adjustDate,"+
			" verify,"+
			" propertyKind,"+
			" fundType,"+
			" valuable,"+
			" sourceDate,"+
			" bookAmount,"+
			" oldbookUnit,"+
			" oldBookValue,"+
			" newBookUnit,"+
			" newBookValue,"+
			" bookNotes,"+
			" keepUnit,"+
			" keeper,"+
			" useUnit,"+
			" userNo,"+
			" place,"+
			" notes,"+
			" otherMaterial,"+
			" otherPropertyUnit,"+
			" otherLimitYear,"+
			" oldPropertyNo,"+
			" oldSerialNo,"+
			" editID,"+
			" editDate,"+
			" editTime,"+
			" differencekind,"+
			" caseSerialNo,"+
			" place1,"+
			" userNote,"+
			" addBookValue,"+
			" reduceBookValue,"+
			" addBookUnit,"+
			" reduceBookUnit,"+
			" buydate"+
		" )Values(" +
			Common.sqlChar(this.getEnterOrg()) + "," +
			Common.sqlChar(this.getOwnership()) + "," +
			Common.sqlChar(this.getCaseNo()) + "," +
			Common.sqlChar(this.getPropertyNo()) + "," +
			Common.sqlChar(this.getLotNo()) + "," +
			Common.sqlChar(this.getSerialNo()) + "," +
			Common.sqlChar(this.getPropertyName1()) + "," +
			Common.sqlChar(this.getCause()) + "," +
			Common.sqlChar(ul._transToCE_Year(this.getAdjustDate())) + "," +
			Common.sqlChar(this.getVerify()) + "," +
			Common.sqlChar(Common.get(this.getPropertyKind())) + "," +
			Common.sqlChar(this.getFundType()) + "," +
			Common.sqlChar(this.getValuable()) + "," +
			Common.sqlChar(ul._transToCE_Year(this.getSourceDate())) + "," +
			Common.sqlChar(this.getBookAmount()) + "," +
			Common.sqlChar(this.getOldBookValue()) + "," +
			Common.sqlChar(this.getOldBookValue()) + "," +
			Common.sqlChar(this.getNewBookValue()) + "," +
			Common.sqlChar(this.getNewBookValue()) + "," +
			Common.sqlChar(this.getBookNotes()) + "," +
			Common.sqlChar(this.getKeepUnit()) + "," +
			Common.sqlChar(this.getKeeper()) + "," +
			Common.sqlChar(this.getUseUnit()) + "," +
			Common.sqlChar(this.getUserNo()) + "," +
			Common.sqlChar(this.getPlace()) + "," +
			Common.sqlChar(this.getNotes()) + "," +
			Common.sqlChar(this.getOtherMaterial()) + "," +
			Common.sqlChar(this.getOtherPropertyUnit()) + "," +
			Common.sqlChar(this.getOtherLimitYear()) + "," +
			Common.sqlChar(this.getOldPropertyNo()) + "," +
			Common.sqlChar(this.getOldSerialNo()) + "," +
			Common.sqlChar(getEditID()) + "," +
			Common.sqlChar(ul._transToCE_Year(getEditDate())) + "," +
			Common.sqlChar(getEditTime()) + "," +
			Common.sqlChar(this.getDifferenceKind()) + "," +
			Common.sqlChar(this.getCaseSerialNo()) + "," +
			Common.sqlChar(this.getPlace1()) + "," +
			Common.sqlChar(this.getUserNote()) + "," +
			Common.sqlChar(this.getAddBookValue()) + "," +
			Common.sqlChar(this.getReduceBookValue()) + "," +
			Common.sqlChar(this.getAddBookValue()) + "," +
			Common.sqlChar(this.getReduceBookValue()) + "," +
	 		Common.sqlChar(ul._transToCE_Year(getBuyDate())) + ")" ;
//	System.out.println("^^^^^^^^^^^^^^^^^^^^^^^6==>"+execSQLArray.toString());
	return execSQLArray;
}


//傳回update sql
protected String[] getUpdateSQL(){
	UNTCH_Tools ul = new UNTCH_Tools();
	String[] execSQLArray = new String[1];
	execSQLArray[0]=" update UNTNE_AdjustDetail set " +
			" propertyName1 = " + Common.sqlChar(this.getPropertyName1()) + "," +
			" cause = " + Common.sqlChar(this.getCause()) + "," +
			" adjustDate = " + Common.sqlChar(ul._transToCE_Year(this.getAdjustDate())) + "," +
			" verify = " + Common.sqlChar(this.getVerify()) + "," +
			" propertyKind = " + Common.sqlChar(this.getPropertyKind()) + "," +
			" fundType = " + Common.sqlChar(this.getFundType()) + "," +
			" valuable = " + Common.sqlChar(this.getValuable()) + "," +
			" sourceDate = " + Common.sqlChar(ul._transToCE_Year(this.getSourceDate())) + "," +
			" bookAmount = " + Common.sqlChar(this.getBookAmount()) + "," +
			" oldBookUnit = " + Common.sqlChar(this.getOldBookUnit()) + "," +
			" oldBookValue = " + Common.sqlChar(this.getOldBookValue()) + "," +
			" newBookUnit = " + Common.sqlChar(this.getNewBookUnit()) + "," +
			" newBookValue = " + Common.sqlChar(this.getNewBookValue()) + "," +
			" bookNotes = " + Common.sqlChar(this.getBookNotes()) + "," +
			" keepUnit = " + Common.sqlChar(this.getKeepUnit()) + "," +
			" keeper = " + Common.sqlChar(this.getKeeper()) + "," +
			" useUnit = " + Common.sqlChar(this.getUseUnit()) + "," +
			" userNo = " + Common.sqlChar(this.getUserNo()) + "," +
			" place = " + Common.sqlChar(this.getPlace()) + "," +
			" notes = " + Common.sqlChar(this.getNotes()) + "," +
			" oldPropertyNo = " + Common.sqlChar(this.getOldPropertyNo()) + "," +
			" oldSerialNo = " + Common.sqlChar(this.getOldSerialNo()) + "," +
			" editID = " + Common.sqlChar(getEditID()) + "," +
			" editDate = " + Common.sqlChar(ul._transToCE_Year(getEditDate())) + "," +
			" editTime = " + Common.sqlChar(getEditTime()) + "," +
			" differenceKind = " + Common.sqlChar(this.getDifferenceKind()) + "," +
			" caseSerialNo = " + Common.sqlChar(this.getCaseSerialNo()) + "," +
			" place1 = " + Common.sqlChar(this.getPlace1()) + "," +
			" userNote = " + Common.sqlChar(this.getUserNote()) + "," +
			" addBookValue = " + Common.sqlChar(this.getAddBookValue()) + "," +
			" reduceBookValue = " + Common.sqlChar(this.getReduceBookValue()) +"," +
			" buydate = " + Common.sqlChar(ul._transToCE_Year(getBuyDate())) + 
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
	execSQLArray[0]=" delete UNTNE_AdjustDetail where 1=1 " +
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

public UNTNE025F  queryOne() throws Exception{
	UNTCH_Tools ul = new UNTCH_Tools();
	Database db = new Database();
	UNTNE025F obj = this;
	try {
		String sql=
			" select a.enterorg, a.ownership, a.caseno, a.propertyno, a.lotno,a.differencekind,a.caseserialno,a.usernote,a.place1,a.addbookvalue,a.reducebookvalue, " +
			" a.serialno, a.propertyname1, a.cause, a.adjustdate,a.buydate, " +
			" a.verify, a.propertykind, a.fundtype, " + 
			" a.valuable, a.sourcedate, a.bookamount , a.oldbookunit , " +
			" a.oldbookvalue, " +
			" a.newbookunit, a.newbookvalue, " +
			" a.booknotes, a.keepunit , a.keeper , a.useunit, a.userno , a.place, " +
			" a.notes, a.oldpropertyno, a.oldserialno, a.editid, a.editdate, " +
			" a.edittime , d.propertyunit, " + 
			" c.organsname as enterorgname, d.propertyname, a.oldpropertyno, " +
			" d.material, d.propertyunit, d.limityear, a.othermaterial, a.otherpropertyunit, a.otherlimityear, "+
			" (select f.unitname from UNTMP_KEEPUNIT f where a.enterorg=f.enterorg and a.keepunit=f.unitno) as keepunitname, "+
			" (select f.unitname from UNTMP_KEEPUNIT f where a.enterorg=f.enterorg and a.useunit=f.unitno) as useunitname, "+
			" (select f.keepername from UNTMP_KEEPER f where a.enterorg=f.enterorg and a.keeper=f.keeperno) as keepername, "+		
			" (select f.keepername from UNTMP_KEEPER f where a.enterorg=f.enterorg and a.userno=f.keeperno) as username " +		
			" from UNTNE_ADJUSTDETAIL a"+
			" left join SYSPK_PROPERTYCODE2 e on a.enterorg = e.enterorg and a.oldpropertyno = e.propertyno "+
			" left join SYSCA_ORGAN c on a.enterorg	= c.organid"+
			" left join SYSPK_PROPERTYCODE2 d on a.enterorg = d.enterorg and a.propertyno=d.propertyno"+
			" where 1=1 " +
			" and a.enterorg 	= " + Common.sqlChar(enterOrg) +
			" and a.ownership 	= " + Common.sqlChar(ownership) +
			" and a.caseno 		= " + Common.sqlChar(caseNo) +
			" and a.propertyno 	= " + Common.sqlChar(propertyNo) +
			" and a.serialno 	= " + Common.sqlChar(serialNo) +
			" and a.differencekind    = " + Common.sqlChar(differenceKind) +
			" order by a.enterorg , a.ownership , a.propertyno , a.serialno  ";
//		System.out.println("31221===>"+sql);
		ResultSet rs = db.querySQL(sql);
		if (rs.next()){
			obj.setEnterOrg(rs.getString("enterOrg"));
			obj.setPropertyUnit(rs.getString("propertyUnit"));
			obj.setEnterOrgName(rs.getString("enterOrgName"));
			obj.setOwnership(rs.getString("ownership"));
			obj.setCaseNo(rs.getString("caseNo"));
			obj.setPropertyNo(rs.getString("propertyNo"));
			obj.setLotNo(rs.getString("lotNo"));
			obj.setPropertyNoName(rs.getString("propertyName"));
			obj.setMaterial(rs.getString("material"));
			obj.setOtherMaterial(rs.getString("otherMaterial"));
			obj.setPropertyUnit(rs.getString("propertyUnit"));
			obj.setOtherPropertyUnit(rs.getString("otherPropertyUnit"));
			obj.setLimitYear(rs.getString("limitYear"));
			obj.setOtherLimitYear(rs.getString("otherLimitYear"));
			obj.setSerialNo(rs.getString("serialNo"));
			obj.setPropertyName1(rs.getString("propertyName1"));
			obj.setCause(rs.getString("cause"));
			obj.setAdjustDate(ul._transToROC_Year(rs.getString("adjustDate")));
			obj.setVerify(rs.getString("verify"));
			obj.setPropertyKind(rs.getString("propertyKind"));
			obj.setFundType(rs.getString("fundType"));
			obj.setValuable(rs.getString("valuable"));
			obj.setSourceDate(ul._transToROC_Year(rs.getString("sourceDate")));
			obj.setBookAmount(rs.getString("bookAmount"));
			obj.setOldBookUnit(rs.getString("oldBookUnit"));
			obj.setOldBookValue(rs.getString("oldBookValue"));
			obj.setNewBookUnit(rs.getString("newBookUnit"));
			obj.setNewBookValue(rs.getString("newBookValue"));
			obj.setBookNotes(rs.getString("bookNotes"));
			obj.setKeepUnit(rs.getString("keepUnit"));
			obj.setKeepUnitName(rs.getString("keepUnitName"));
			obj.setKeeper(rs.getString("keeper"));
			obj.setKeeperName(rs.getString("keeperName"));
			obj.setUseUnit(rs.getString("useUnit"));
			obj.setUseUnitName(rs.getString("useUnitName"));
			obj.setUserNo(rs.getString("userNo"));
			obj.setUserName(rs.getString("userName"));
			obj.setPlace(rs.getString("place"));
			obj.setNotes(rs.getString("notes"));
			obj.setOldPropertyNo(rs.getString("oldPropertyNo"));
			obj.setOldPropertyName(rs.getString("oldpropertyNo"));
			obj.setOldSerialNo(rs.getString("oldSerialNo"));
			obj.setEditID(rs.getString("editID"));
			obj.setEditDate(ul._transToROC_Year(rs.getString("editDate")));
			obj.setEditTime(rs.getString("editTime"));
			obj.setDifferenceKind(rs.getString("differenceKind"));
			obj.setCaseSerialNo(rs.getString("caseSerialNo"));
			obj.setUserNote(rs.getString("userNote"));
			obj.setPlace1(rs.getString("place1"));
			obj.setAddBookValue(rs.getString("addBookValue"));
			obj.setReduceBookValue(rs.getString("reduceBookValue"));
			obj.setBuyDate(ul._transToROC_Year(rs.getString("buydate")));
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
		String sql = "select proofno from UNTNE_ADJUSTPROOF where 1=1" +
						" and enterorg = " + Common.sqlChar(this.getEnterOrg()) +
						" and ownership = " + Common.sqlChar(this.getOwnership()) +
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
	Map<String,String> differencekindMap = TCGHCommon.getSysca_Code("DFK"); 					//財產區分別名稱
	int counter=0;
	try {
		String sql=" select a.enterOrg,o.organSName, a.ownership,a.differenceKind,a.caseSerialNo,a.lotno,a.addBookValue,a.reduceBookValue, " +
				" (select z.codeName from sysca_code z where 1=1 and (z.codeKindID='owa' or z.codeKindID='OWA') and a.ownership=z.codeID) as ownershipName, " +
				" a.caseNo, a.propertyNo, a.serialNo, a.cause," +
				" (select z.codename from sysca_code z where z.codekindid ='AJC' and z.codeid = a.cause) as causeName," +
				" a.adjustDate,"+
				" (case a.propertyKind when '01' then '公務用' when '02' then '公共用' when '03' then '事業用' when '04' then '非公用' else '' end) propertyKind, "+
				" a.oldBookValue, " +
				" a.newBookUnit, a.newBookValue, " +
				" d.propertyName "+
				" from UNTNE_AdjustDetail a"+
				" left join UNTNE_AdjustProof b on a.enterOrg=b.enterOrg and a.caseNo=b.caseNo,"+
				" SYSCA_Organ o, SYSPK_PROPERTYCODE2 d where 1=1 " +
				" and a.enterOrg = o.organID " +
				" and a.enterOrg = d.enterOrg " +
				" and a.propertyNo = d.propertyNo ";
		if ("AddProof".equals(getQuerySelect()) || "".equals(getQuerySelect())) {
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
						//sql += " and a.enterOrg like '" + getOrganID().substring(0,5) + "%' ";		
						sql += " and( a.enterOrg = " + Common.sqlChar(getOrganID()) ;
						sql += " or organID in (select organID from SYSCA_Organ where organSuperior='"+ Common.sqlChar(getOrganID())+"'))";
					} else {
						sql+=" and a.enterOrg = " + Common.sqlChar(getOrganID()) ;
					}
				}
			}
			if (!"".equals(getQ_ownership()))
				sql+=" and a.ownership = " + Common.sqlChar(getQ_ownership()) ;
			if (!"".equals(getQ_caseNoS()))
				sql+=" and a.caseNo >= " + Common.sqlChar(Common.formatFrontZero(getQ_caseNoS(),10));
			if (!"".equals(getQ_caseNoE()))
				sql+=" and a.caseNo <= " + Common.sqlChar(Common.formatFrontZero(getQ_caseNoE(),10));
			if (!"".equals(getQ_verify()))
				sql+=" and a.verify = " + Common.sqlChar(getQ_verify()) ;
			if (!"".equals(getQ_adjustDateS()))
				sql+=" and a.adjustDate >= " + Common.sqlChar(ul._transToCE_Year(getQ_adjustDateS())) ;
			if (!"".equals(getQ_adjustDateE()))
				sql+=" and a.adjustDate <= " + Common.sqlChar(ul._transToCE_Year(getQ_adjustDateE())) ;
			if (!"".equals(getQ_caseName()))
				sql+=" and b.caseName like " + Common.sqlChar(getQ_caseName()+"%") ;
			if (!"".equals(getQ_proofDoc()))
				sql+=" and b.proofDoc like " + Common.sqlChar(getQ_proofDoc()+"%") ;
			if (!"".equals(getQ_proofNoS()))
				sql+=" and b.proofNo >= " + Common.sqlChar(getQ_proofNoS()) ;
			if (!"".equals(getQ_proofNoE()))
				sql+=" and b.proofNo <= " + Common.sqlChar(getQ_proofNoE()) ;
			if (!"".equals(getQ_writeDateS()))
				sql+=" and b.writeDate >= " + Common.sqlChar(ul._transToCE_Year(getQ_writeDateS())) ;
			if (!"".equals(getQ_writeDateE()))
				sql+=" and b.writeDate <= " + Common.sqlChar(ul._transToCE_Year(getQ_writeDateE())) ;    
			if (!"".equals(getQ_approveOrg()))
				sql+=" and b.approveOrg = " + Common.sqlChar(getQ_approveOrg()) ;
			if (!"".equals(getQ_propertyNoS()))
				sql+=" and a.propertyNo >= " + Common.sqlChar(getQ_propertyNoS());		
			if (!"".equals(getQ_propertyNoE()))
				sql+=" and a.propertyNo<=" + Common.sqlChar(getQ_propertyNoE());						
			if (!"".equals(getQ_serialNoS()))
				sql+=" and a.serialNo >= " + Common.sqlChar(getQ_serialNoS());		
			if (!"".equals(getQ_serialNoE()))
				sql+=" and a.serialNo <= " + Common.sqlChar(getQ_serialNoE());			
			if (!"".equals(getQ_propertyKind()))
				sql+=" and a.propertyKind = " + Common.sqlChar(getQ_propertyKind());
			if (!"".equals(getQ_fundType()))
				sql+=" and a.fundType = " + Common.sqlChar(getQ_fundType()) ;
			if (!"".equals(getQ_valuable()))
				sql+=" and a.valuable = " + Common.sqlChar(getQ_valuable()) ;
			if (!"".equals(getQ_differenceKind()))
				sql+=" and a.differencekind = " + Common.sqlChar(getQ_differenceKind()) ;
			if (!"".equals(getQ_proofYear()))
				sql+=" and b.proofyear = " + Common.sqlChar(getQ_proofYear()) ;
			if (!"".equals(getQ_userNote()))
				sql+=" and a.userNote like " + Common.sqlChar("%"+getQ_userNote()+"%") ;
			if (!"".equals(getQ_keepUnit()))
				sql+=" and a.keepUnit = " + Common.sqlChar(getQ_keepUnit()) ;	    
			if (!"".equals(getQ_keeper()))
				sql+=" and a.keeper = " + Common.sqlChar(getQ_keeper()) ;	    
			if (!"".equals(getQ_useUnit()))
				sql+=" and a.useUnit = " + Common.sqlChar(getQ_useUnit()) ;	    
			if (!"".equals(getQ_userNo()))
				sql+=" and a.userNo = " + Common.sqlChar(getQ_userNo()) ;	   
			if(!"".equals(getQ_place1()))
				sql+=" and a.place1 like " + Common.sqlChar("%" + getQ_place1() + "%");
		}
		if ("1".equals(this.getRoleid())){
			sql += " and ( a.keeper = " + Common.sqlChar(this.getKeeperno())+
						 " or b.editid = " + Common.sqlChar(this.getUserID()) +
						 " )";	
			
		}else if ("2".equals(this.getRoleid())){
			sql += " and ( a.keepunit = " + Common.sqlChar(this.getUnitID())+
						 " or a.keeper = " + Common.sqlChar(this.getKeeperno())+
						 " or b.editid = " + Common.sqlChar(this.getUserID()) +
						 " )";	
			
		}else if ("3".equals(this.getRoleid())){
			
		}
		
		
		
		sql+=" order by a.enterOrg , a.ownership , a.propertyNo , a.serialNo ";
		ResultSet rs = db.querySQL(sql);
		while (rs.next()){
			counter++;
			String rowArray[]=new String[18];
			rowArray[0]=rs.getString("enterOrg");
			rowArray[1]=rs.getString("organSName");
			rowArray[2]=rs.getString("ownership"); 
			rowArray[3]=rs.getString("ownershipName");
			rowArray[4]=differencekindMap.get(rs.getString("differencekind"));
			rowArray[5]=rs.getString("caseSerialNo"); 
			rowArray[6]=rs.getString("caseNo"); 
			rowArray[7]=rs.getString("propertyNo"); 
			rowArray[8]=rs.getString("propertyName"); 
			rowArray[9]=rs.getString("serialNo"); 
			rowArray[10]=rs.getString("causeName"); 	
			rowArray[11]=ul._transToROC_Year(rs.getString("adjustDate"));
			rowArray[12]=rs.getString("propertyKind"); 
			rowArray[13]=rs.getString("oldBookValue");
			rowArray[14]=rs.getString("addBookValue"); 
			rowArray[15]=rs.getString("reduceBookValue");
			rowArray[16]=rs.getString("newBookValue"); 
			rowArray[17]=rs.getString("differenceKind");;
			
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
}