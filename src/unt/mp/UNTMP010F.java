/*
*<br>程式目的：動產保管使用異動作業－明細資料
*<br>程式代號：untmp010f
*<br>程式日期：0941019
*<br>程式作者：carey
*<br>--------------------------------------------------------
*<br>修改作者　　修改日期　　　修改目的
*<br>--------------------------------------------------------
*/

package unt.mp;

import java.sql.ResultSet;
import java.util.ArrayList;
import util.*;

public class UNTMP010F extends UNTMP009Q{


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
String newKeepBureau;
String newKeepUnit;
String newKeeper;
String newUseBureau;
String newUseUnit;
String newUserNo;
String newPlace;
String useYear;
String useMonth;
String scrapValue;
String notes;
String oldPropertyNo;
String oldSerialNo;

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
public String getNewKeepBureau(){ return checkGet(newKeepBureau); }
public void setNewKeepBureau(String s){ newKeepBureau=checkSet(s); }
public String getNewKeepUnit(){ return checkGet(newKeepUnit); }
public void setNewKeepUnit(String s){ newKeepUnit=checkSet(s); }
public String getNewKeeper(){ return checkGet(newKeeper); }
public void setNewKeeper(String s){ newKeeper=checkSet(s); }
public String getNewUseBureau(){ return checkGet(newUseBureau); }
public void setNewUseBureau(String s){ newUseBureau=checkSet(s); }
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
public String getScrapValue(){ return checkGet(scrapValue); }
public void setScrapValue(String s){ scrapValue=checkSet(s); }
public String getNotes(){ return checkGet(notes); }
public void setNotes(String s){ notes=checkSet(s); }
public String getOldPropertyNo(){ return checkGet(oldPropertyNo); }
public void setOldPropertyNo(String s){ oldPropertyNo=checkSet(s); }
public String getOldSerialNo(){ return checkGet(oldSerialNo); }
public void setOldSerialNo(String s){ oldSerialNo=checkSet(s); }

public String getMoveDate(){ return checkGet(moveDate); }
public void setMoveDate(String s){ moveDate=checkSet(s); }
public String getInitDtl(){ return checkGet(initDtl); }
public void setInitDtl(String s){ initDtl=checkSet(s); }

String closing;

public String getClosing(){ return checkGet(closing); }
public void setClosing(String s){ closing=checkSet(s); }

//傳回執行insert前之檢查sql
protected String[][] getInsertCheckSQL(){
	String[][] checkSQLArray = new String[3][4];
 	checkSQLArray[0][0]=" select count(*) as checkResult from UNTMP_MoveDetail where 1=1 " + 
		" and enterOrg = " + Common.sqlChar(enterOrg) + 
		" and ownership = " + Common.sqlChar(ownership) + 
		//" and caseNo = " + Common.sqlChar(caseNo) + 
		" and propertyNo = " + Common.sqlChar(propertyNo) + 
		" and serialNo = " + Common.sqlChar(serialNo) + 
		" and verify = 'N' " + 
		"";
 	//System.out.println(checkSQLArray[0][0]);
	checkSQLArray[0][1]=">";
	checkSQLArray[0][2]="0";
	checkSQLArray[0][3]="動產保管使用異動作業存在未審核的資料，請重新輸入！！";
	
	//檢查同一筆「入帳機關+權屬+財產編號+財產分號」是否減少作業已存在未審核的資料
	checkSQLArray[1][0]=" select count(*) as checkResult from UNTMP_ReduceDetail where 1=1 " + 
		" and enterOrg = " + Common.sqlChar(enterOrg) + 
		" and ownership = " + Common.sqlChar(ownership) + 
		" and propertyNo = " + Common.sqlChar(propertyNo) + 
		" and serialNo = " + Common.sqlChar(serialNo) +
		" and verify = 'N' " + 
		"";
	checkSQLArray[1][1]=">";
	checkSQLArray[1][2]="0";
	checkSQLArray[1][3]="動產減少作業存在未審核的資料，請重新輸入！！";

	//檢查同一筆「入帳機關+權屬+財產編號+財產分號」是否增減值作業已存在未審核的資料
	checkSQLArray[2][0]=" select count(*) as checkResult from UNTMP_AdjustDetail where 1=1 " + 
		" and enterOrg = " + Common.sqlChar(enterOrg) + 
		" and ownership = " + Common.sqlChar(ownership) + 
		" and propertyNo = " + Common.sqlChar(propertyNo) + 
		" and serialNo = " + Common.sqlChar(serialNo) +
		" and verify = 'N' " + 
		"";
	checkSQLArray[2][1]=">";
	checkSQLArray[2][2]="0";
	checkSQLArray[2][3]="動產增減值作業存在未審核的資料，請重新輸入！！";
	
	return checkSQLArray;
}


//傳回insert sql
protected String[] getInsertSQL(){
	String[] execSQLArray = new String[1];
	execSQLArray[0]=" insert into UNTMP_MoveDetail(" +
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
			" newMoveDate,"+
			" newKeepUnit,"+
			" newKeeper,"+
			" newUseUnit,"+
			" newUserNo,"+
			" newPlace,"+
			" useYear,"+
			" useMonth,"+
			" scrapValue,"+
			" notes,"+
			" closing,"+
			" oldPropertyNo,"+
			" oldSerialNo,"+
			" editID,"+
			" editDate,"+
			" editTime"+
		" )Values(" +
			Common.sqlChar(enterOrg) + "," +
			Common.sqlChar(ownership) + "," +
			Common.sqlChar(caseNo) + "," +
			Common.sqlChar(propertyNo) + "," +
			Common.sqlChar(lotNo) + "," +
			Common.sqlChar(serialNo) + "," +
			Common.sqlChar(propertyName1) + "," +
			Common.sqlChar(otherMaterial) + "," +
			Common.sqlChar(otherPropertyUnit) + "," +
			Common.sqlChar(otherLimitYear) + "," +
			Common.sqlChar(buyDate) + "," +
			Common.sqlChar(verify) + "," +
			Common.sqlChar(propertyKind) + "," +
			Common.sqlChar(fundType) + "," +
			Common.sqlChar(valuable) + "," +
			Common.sqlChar(bookAmount) + "," +
			Common.sqlChar(bookUnit) + "," +
			Common.sqlChar(bookValue) + "," +
			Common.sqlChar(nameplate) + "," +
			Common.sqlChar(specification) + "," +
			Common.sqlChar(sourceDate) + "," +
			Common.sqlChar(oldMoveDate) + "," +
			Common.sqlChar(oldKeepUnit) + "," +
			Common.sqlChar(oldKeeper) + "," +
			Common.sqlChar(oldUseUnit) + "," +
			Common.sqlChar(oldUserNo) + "," +
			Common.sqlChar(oldPlace) + "," +
			Common.sqlChar(newMoveDate) + "," +
			Common.sqlChar(newKeepUnit) + "," +
			Common.sqlChar(newKeeper) + "," +
			Common.sqlChar(newUseUnit) + "," +
			Common.sqlChar(newUserNo) + "," +
			Common.sqlChar(newPlace) + "," +
			Common.sqlChar(useYear) + "," +
			Common.sqlChar(useMonth) + "," +
			Common.sqlChar(scrapValue) + "," +
			Common.sqlChar(notes) + "," +
			Common.sqlChar(closing) + "," +
			Common.sqlChar(oldPropertyNo) + "," +
			Common.sqlChar(oldSerialNo) + "," +
			Common.sqlChar(getEditID()) + "," +
			Common.sqlChar(getEditDate()) + "," +
			Common.sqlChar(getEditTime()) + ")" ;
	return execSQLArray;
}


//傳回update sql
protected String[] getUpdateSQL(){
	String[] execSQLArray = new String[1];
	execSQLArray[0]=" update UNTMP_MoveDetail set " +
			" lotNo = " + Common.sqlChar(lotNo) + "," +
			" propertyName1 = " + Common.sqlChar(propertyName1) + "," +
			" otherMaterial = " + Common.sqlChar(otherMaterial) + "," +
			" otherPropertyUnit = " + Common.sqlChar(otherPropertyUnit) + "," +
			" otherLimitYear = " + Common.sqlChar(otherLimitYear) + "," +
			" buyDate = " + Common.sqlChar(buyDate) + "," +
			" verify = " + Common.sqlChar(verify) + "," +
			" propertyKind = " + Common.sqlChar(propertyKind) + "," +
			" fundType = " + Common.sqlChar(fundType) + "," +
			" valuable = " + Common.sqlChar(valuable) + "," +
			" bookAmount = " + Common.sqlChar(bookAmount) + "," +
			" bookUnit = " + Common.sqlChar(bookUnit) + "," +
			" bookValue = " + Common.sqlChar(bookValue) + "," +
			" nameplate = " + Common.sqlChar(nameplate) + "," +
			" specification = " + Common.sqlChar(specification) + "," +
			" sourceDate = " + Common.sqlChar(sourceDate) + "," +
			" oldMoveDate = " + Common.sqlChar(oldMoveDate) + "," +
			" oldKeepUnit = " + Common.sqlChar(oldKeepUnit) + "," +
			" oldKeeper = " + Common.sqlChar(oldKeeper) + "," +
			" oldUseUnit = " + Common.sqlChar(oldUseUnit) + "," +
			" oldUserNo = " + Common.sqlChar(oldUserNo) + "," +
			" oldPlace = " + Common.sqlChar(oldPlace) + "," +
			" newMoveDate = " + Common.sqlChar(newMoveDate) + "," +
			" newKeepUnit = " + Common.sqlChar(newKeepUnit) + "," +
			" newKeeper = " + Common.sqlChar(newKeeper) + "," +
			" newUseUnit = " + Common.sqlChar(newUseUnit) + "," +
			" newUserNo = " + Common.sqlChar(newUserNo) + "," +
			" newPlace = " + Common.sqlChar(newPlace) + "," +
			" useYear = " + Common.sqlChar(useYear) + "," +
			" useMonth = " + Common.sqlChar(useMonth) + "," +
			" scrapValue = " + Common.sqlChar(scrapValue) + "," +
			" notes = " + Common.sqlChar(notes) + "," +
			" closing = " + Common.sqlChar(closing) + "," +
			" oldPropertyNo = " + Common.sqlChar(oldPropertyNo) + "," +
			" oldSerialNo = " + Common.sqlChar(oldSerialNo) + "," +
			" editID = " + Common.sqlChar(getEditID()) + "," +
			" editDate = " + Common.sqlChar(getEditDate()) + "," +
			" editTime = " + Common.sqlChar(getEditTime()) + 
		" where 1=1 " + 
			" and enterOrg = " + Common.sqlChar(enterOrg) +
			" and ownership = " + Common.sqlChar(ownership) +
			" and caseNo = " + Common.sqlChar(caseNo) +
			" and propertyNo = " + Common.sqlChar(propertyNo) +
			" and serialNo = " + Common.sqlChar(serialNo) +
			"";
	return execSQLArray;
}


//傳回delete sql
protected String[] getDeleteSQL(){
	String[] execSQLArray = new String[1];
	execSQLArray[0]=" delete UNTMP_MoveDetail where 1=1 " +
			" and enterOrg = " + Common.sqlChar(enterOrg) +
			" and ownership = " + Common.sqlChar(ownership) +
			" and caseNo = " + Common.sqlChar(caseNo) +
			" and propertyNo = " + Common.sqlChar(propertyNo) +
			" and serialNo = " + Common.sqlChar(serialNo) +
			"";
	return execSQLArray;
}


/**
 * <br>
 * <br>目的：依主鍵查詢單一資料
 * <br>參數：無
 * <br>傳回：傳回本物件
*/

public UNTMP010F  queryOne() throws Exception{
	Database db = new Database();
	UNTMP010F obj = this;
	try {
		String sql=" select distinct (select b.organSName from SYSCA_Organ b where b.organID = a.enterOrg ) organSName, " +
				" a.enterOrg, a.ownership, a.caseNo, a.propertyNo, " +
				" c.propertyName, c.material, c.propertyUnit, c.limitYear, a.otherMaterial, a.otherPropertyUnit, a.otherLimitYear, " +
				" a.lotNo, a.serialNo, a.propertyName1, a.buyDate, a.verify, a.propertyKind, a.fundType, a.valuable, a.bookAmount, a.bookUnit, a.bookValue, a.nameplate, a.specification, a.sourceDate, a.oldMoveDate, a.oldKeepUnit, a.oldKeeper, a.oldUseUnit, a.oldUserNo, a.oldPlace, a.newMoveDate, a.newKeepUnit, a.newKeeper, a.newUseUnit, a.newUserNo, a.newPlace, a.useYear, a.useMonth, a.scrapValue, a.notes, a.oldPropertyNo, a.oldSerialNo, a.editID, a.editDate, a.editTime, a.closing "+
				" from UNTMP_MoveDetail a, SYSPK_PropertyCode c where 1=1 " +
				" and a.enterOrg = " + Common.sqlChar(enterOrg) +
				" and a.ownership = " + Common.sqlChar(ownership) +
				" and a.caseNo = " + Common.sqlChar(caseNo) +
				" and a.propertyNo = " + Common.sqlChar(propertyNo) +
				" and a.serialNo = " + Common.sqlChar(serialNo) +
				" and c.propertyNo= a.propertyNo " +
				" and c.enterOrg in('000000000A',a.enterOrg) and c.propertyType='1'" +
				"";

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
			obj.setBuyDate(rs.getString("buyDate"));
			obj.setVerify(rs.getString("verify"));
			obj.setPropertyKind(rs.getString("propertyKind"));
			obj.setFundType(rs.getString("fundType"));
			obj.setValuable(rs.getString("valuable"));
			obj.setBookAmount(rs.getString("bookAmount"));
			obj.setBookUnit(rs.getString("bookUnit"));
			obj.setBookValue(rs.getString("bookValue"));
			obj.setNameplate(rs.getString("nameplate"));
			obj.setSpecification(rs.getString("specification"));
			obj.setSourceDate(rs.getString("sourceDate"));
			obj.setOldMoveDate(rs.getString("oldMoveDate"));
			obj.setOldKeepUnit(rs.getString("oldKeepUnit"));
			obj.setOldKeeper(rs.getString("oldKeeper"));
			obj.setOldUseUnit(rs.getString("oldUseUnit"));
			obj.setOldUserNo(rs.getString("oldUserNo"));
			obj.setOldPlace(rs.getString("oldPlace"));
			obj.setNewMoveDate(rs.getString("newMoveDate"));
			obj.setNewKeepUnit(rs.getString("newKeepUnit"));
			obj.setNewKeeper(rs.getString("newKeeper"));
			obj.setNewUseUnit(rs.getString("newUseUnit"));
			obj.setNewUserNo(rs.getString("newUserNo"));
			obj.setNewPlace(rs.getString("newPlace"));
			obj.setUseYear(rs.getString("useYear"));
			obj.setUseMonth(rs.getString("useMonth"));
			obj.setScrapValue(rs.getString("scrapValue"));
			obj.setNotes(rs.getString("notes"));
			obj.setClosing(rs.getString("closing"));
			obj.setOldPropertyNo(rs.getString("oldPropertyNo"));
			obj.setOldSerialNo(rs.getString("oldSerialNo"));
			obj.setEditID(rs.getString("editID"));
			obj.setEditDate(rs.getString("editDate"));
			obj.setEditTime(rs.getString("editTime"));
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
		String sql=" select a.enterOrg, a.ownership, a.caseNo, a.propertyNo, a.serialNo, a.propertyKind, a.oldKeepUnit, a.oldKeeper, a.oldPlace, a.newMoveDate, a.newKeepUnit, a.newKeeper, a.newPlace, "+ "\n" +
				" (select b.organSName from SYSCA_Organ b where b.organID = a.enterOrg ) organSName, " + "\n" +
				" (select z.codeName from sysca_code z where 1=1 and (z.codeKindID='owa' or z.codeKindID='OWA') and a.ownership=z.codeID) as ownershipName, " + "\n" +
				" (select c.propertyName from SYSPK_PropertyCode c where c.enterOrg in('000000000A',a.enterOrg) and a.propertyNo=c.propertyNo and c.propertyType='1' ) propertyName, " + "\n" +
				" (select d.codeName from SYSCA_Code d where d.codeKindID='PKD' and d.codeID=a.propertyKind ) propertyKindName, " + "\n" +
				" (select e.unitName from UNTMP_KeepUnit e where e.enterOrg=a.enterOrg and e.unitNo=a.oldKeepUnit ) oldKeepUnitName, " + "\n" +
				" (select f.keeperName from UNTMP_Keeper f where f.enterOrg=a.enterOrg and f.unitNo=a.oldKeepUnit and f.keeperNo=a.oldKeeper ) oldKeeperName, " + "\n" +
				" (select g.unitName from UNTMP_KeepUnit g where g.enterOrg=a.enterOrg and g.unitNo=a.newKeepUnit ) newKeepUnitName, " + "\n" +
				" (select h.keeperName from UNTMP_Keeper h where h.enterOrg=a.enterOrg and h.unitNo=a.newKeepUnit and h.keeperNo=a.newKeeper ) newKeeperName " + "\n" +
				" from UNTMP_MoveDetail a, UNTMP_MoveProof b where 1=1 " +  "\n" +
				" and b.enterOrg=a.enterOrg(+) " +
				" and b.ownership=a.ownership(+) " +
				" and b.caseNo=a.caseNo(+)" +
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
				sql+=" and a.newMoveDate >= " + Common.sqlChar(getQ_moveDateS()) ;
			if (!"".equals(getQ_moveDateE()))
				sql+=" and a.newMoveDate <= " + Common.sqlChar(getQ_moveDateE()) ;
			if (!"".equals(getQ_verify()))
				sql+=" and a.verify = " + Common.sqlChar(getQ_verify()) ;
			if (!"".equals(getQ_caseName()))
			    sql+=" and b.caseName like " + Common.sqlChar("%"+getQ_caseName()+"%") ;
			if (!"".equals(getQ_writeDateS()))
				sql+=" and b.writeDate >= " + Common.sqlChar(new Datetime().changeTaiwanYYMMDD(getQ_writeDateS(),"2")) ;
			if (!"".equals(getQ_writeDateE()))
				sql+=" and b.writeDate <= " + Common.sqlChar(new Datetime().changeTaiwanYYMMDD(getQ_writeDateE(),"2")) ;
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
		}	
		sql += "order by a.propertyNo, a.serialNo ";
		//System.out.println("All: "+sql);		
		ResultSet rs = db.querySQL(sql);
		while (rs.next()){
			counter++;
			String rowArray[]=new String[21];
			rowArray[0]=rs.getString("enterOrg");
			rowArray[1]=rs.getString("organSName");
			rowArray[2]=rs.getString("ownership");
			rowArray[3]=rs.getString("ownershipName");
			rowArray[4]=rs.getString("caseNo"); 
			rowArray[5]=rs.getString("propertyNo");
			rowArray[6]=rs.getString("propertyName");
			rowArray[7]=rs.getString("serialNo"); 
			rowArray[8]=rs.getString("propertyKind");
			rowArray[9]=rs.getString("propertyKindName");
			rowArray[10]=rs.getString("oldKeepUnit");
			rowArray[11]=rs.getString("oldKeepUnitName");
			rowArray[12]=rs.getString("oldKeeper");
			rowArray[13]=rs.getString("oldKeeperName");
			rowArray[14]=rs.getString("oldPlace"); 
			rowArray[15]=rs.getString("newMoveDate"); 
			rowArray[16]=rs.getString("newKeepUnit"); 
			rowArray[17]=rs.getString("newKeepUnitName");
			rowArray[18]=rs.getString("newKeeper");
			rowArray[19]=rs.getString("newKeeperName");
			rowArray[20]=rs.getString("newPlace");
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


