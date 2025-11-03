/*
*<br>程式目的：
*<br>程式代號：
*<br>程式日期：
*<br>程式作者：
*<br>--------------------------------------------------------
*<br>修改作者　　修改日期　　　修改目的
*<br>--------------------------------------------------------
*/

package unt.ch;

import java.sql.ResultSet;
import java.util.ArrayList;

import util.Common;
import util.Database;
import util.Datetime;

public class UNTCH002F03 extends UNTCH002Q{
	
String enterOrg;
String enterOrgName;
String ownership;
String differenceKind;
String caseNo;
String verify;
String propertyNo;
String propertyName;
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
String propertyKind;
String fundType;
String valuable;
String bookAmount;
String bookValue;
String nameplate;
String specification;
String sourceDate;
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
String scrapValue;
String oldPropertyNo;
String oldSerialNo;

String groupID;

public String getDifferenceKind() {return checkGet(differenceKind);}
public void setDifferenceKind(String differenceKind) {this.differenceKind = checkSet(differenceKind);}
public String getEnterOrgName(){ return checkGet(enterOrgName); }
public void setEnterOrgName(String s){ enterOrgName=checkSet(s); }
public String getPropertyName(){ return checkGet(propertyName); }
public void setPropertyName(String s){ propertyName=checkSet(s); }
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
public String getBookAmount(){ return checkGet(bookAmount); }
public void setBookAmount(String s){ bookAmount=checkSet(s); }
public String getBookValue(){ return checkGet(bookValue); }
public void setBookValue(String s){ bookValue=checkSet(s); }
public String getEnterOrg(){ return checkGet(enterOrg); }
public void setEnterOrg(String s){ enterOrg=checkSet(s); }
public String getOwnership(){ return checkGet(ownership); }
public void setOwnership(String s){ ownership=checkSet(s); }
public String getCaseNo(){ return checkGet(caseNo); }
public void setCaseNo(String s){ caseNo=checkSet(s); }
public String getVerify(){ return checkGet(verify); }
public void setVerify(String s){ verify=checkSet(s); }
public String getPropertyNo(){ return checkGet(propertyNo); }
public void setPropertyNo(String s){ propertyNo=checkSet(s); }
public String getLotNo(){ return checkGet(lotNo); }
public void setLotNo(String s){ lotNo=checkSet(s); }
public String getSerialNo(){ return checkGet(serialNo); }
public void setSerialNo(String s){ serialNo=checkSet(s); }
public String getPropertyName1(){ return checkGet(propertyName1); }
public void setPropertyName1(String s){ propertyName1=checkSet(s); }
public String getBuyDate(){ return checkGet(buyDate); }
public void setBuyDate(String s){ buyDate=checkSet(s); }
public String getPropertyKind(){ return checkGet(propertyKind); }
public void setPropertyKind(String s){ propertyKind=checkSet(s); }
public String getFundType(){ return checkGet(fundType); }
public void setFundType(String s){ fundType=checkSet(s); }
public String getValuable(){ return checkGet(valuable); }
public void setValuable(String s){ valuable=checkSet(s); }
public String getNameplate(){ return checkGet(nameplate); }
public void setNameplate(String s){ nameplate=checkSet(s); }
public String getSpecification(){ return checkGet(specification); }
public void setSpecification(String s){ specification=checkSet(s); }
public String getSourceDate(){ return checkGet(sourceDate); }
public void setSourceDate(String s){ sourceDate=checkSet(s); }
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
public String getScrapValue(){ return checkGet(scrapValue); }
public void setScrapValue(String s){ scrapValue=checkSet(s); }
public String getOldPropertyNo(){ return checkGet(oldPropertyNo); }
public void setOldPropertyNo(String s){ oldPropertyNo=checkSet(s); }
public String getOldSerialNo(){ return checkGet(oldSerialNo); }
public void setOldSerialNo(String s){ oldSerialNo=checkSet(s); }

public String getGroupID(){ return checkGet(groupID); }
public void setGroupID(String s){ groupID=checkSet(s); }

String q_enterOrg;
String q_ownership; 
String q_caseNo;
String q_newMoveDate;
String q_newKeepBureau;
String q_newKeepUnit;
String q_newKeeper;
String q_newUseBureau;
String q_newUseUnit;
String q_newUserNo;
String q_newPlace;
String q_verify;
String q_newUserNote;
String q_newPlace1;
String q_newPlace1Name;
String q_userNote;
String q_place;
String q_place1;
String q_place1Name;
String q_differenceKind;
String q_engineeringNo;
String q_engineeringNoName;
String q_propertyName1;
String q_IsFix;
String q_IsFix_newKeepUnit;
String q_IsFix_newKeeper;
String q_IsFix_newUseUnit;
String q_IsFix_newUserNo;

String sSQL = "";
String strKeySet[] = null;

public String getsSQL(){ return checkGet(sSQL); }
public void setsSQL(String s){ sSQL=checkSet(s); }
public String[] getsKeySet(){ return strKeySet; }
public void setsKeySet(String[] s){ strKeySet=s; }
public String getQ_enterOrg(){ return checkGet(q_enterOrg); }
public void setQ_enterOrg(String s){ q_enterOrg=checkSet(s); }
public String getQ_ownership(){ return checkGet(q_ownership); }
public void setQ_ownership(String s){ q_ownership=checkSet(s); }
public String getQ_newMoveDate(){ return checkGet(q_newMoveDate); }
public void setQ_newMoveDate(String s){ q_newMoveDate=checkSet(s); }

public String getQ_newKeepBureau(){ return checkGet(q_newKeepBureau); }
public void setQ_newKeepBureau(String s){ q_newKeepBureau=checkSet(s); }
public String getQ_newKeepUnit(){ return checkGet(q_newKeepUnit); }
public void setQ_newKeepUnit(String s){ q_newKeepUnit=checkSet(s); }
public String getQ_newKeeper(){ return checkGet(q_newKeeper); }
public void setQ_newKeeper(String s){ q_newKeeper=checkSet(s); }
public String getQ_newUseBureau(){ return checkGet(q_newUseBureau); }
public void setQ_newUseBureau(String s){ q_newUseBureau=checkSet(s); }
public String getQ_newUseUnit(){ return checkGet(q_newUseUnit); }
public void setQ_newUseUnit(String s){ q_newUseUnit=checkSet(s); }
public String getQ_newUserNo(){ return checkGet(q_newUserNo); }
public void setQ_newUserNo(String s){ q_newUserNo=checkSet(s); }
public String getQ_newPlace(){ return checkGet(q_newPlace); }
public void setQ_newPlace(String s){ q_newPlace=checkSet(s); }
public String getQ_newPlace1() {return checkGet(q_newPlace1);}
public void setQ_newPlace1(String q_newPlace1) {this.q_newPlace1 = checkSet(q_newPlace1);}
public String getQ_newPlace1Name() {return checkGet(q_newPlace1Name);}
public void setQ_newPlace1Name(String q_newPlace1Name) {this.q_newPlace1Name = checkSet(q_newPlace1Name);}
public String getQ_newUserNote() {return checkGet(q_newUserNote);}
public void setQ_newUserNote(String q_newUserNote) {this.q_newUserNote = checkSet(q_newUserNote);}
public String getQ_userNote() {return checkGet(q_userNote);}
public void setQ_userNote(String q_userNote) {this.q_userNote = checkSet(q_userNote);}
public String getQ_place1() {return checkGet(q_place1);}
public void setQ_place1(String q_place1) {this.q_place1 = checkSet(q_place1);}
public String getQ_place1Name() {return checkGet(q_place1Name);}
public void setQ_place1Name(String q_place1Name) {this.q_place1Name = checkSet(q_place1Name);}
public String getQ_differenceKind() {return checkGet(q_differenceKind);}
public void setQ_differenceKind(String q_differenceKind) {this.q_differenceKind = checkSet(q_differenceKind);}
public String getQ_engineeringNo() {return checkGet(q_engineeringNo);}
public void setQ_engineeringNo(String q_engineeringNo) {this.q_engineeringNo = checkSet(q_engineeringNo);}
public String getQ_engineeringNoName() {return checkGet(q_engineeringNoName);}
public void setQ_engineeringNoName(String q_engineeringNoName) {this.q_engineeringNoName = checkSet(q_engineeringNoName);}
public String getQ_place() {return checkGet(q_place);}
public void setQ_place(String q_place) {this.q_place = checkSet(q_place);}
public String getQ_propertyName1() {return checkGet(q_propertyName1);}
public void setQ_propertyName1(String q_propertyName1) {this.q_propertyName1 = checkSet(q_propertyName1);}
public String getQ_IsFix() {return checkGet(q_IsFix);}
public void setQ_IsFix(String q_IsFix) {this.q_IsFix = checkSet(q_IsFix);}
public String getQ_IsFix_newKeepUnit() {return checkGet(q_IsFix_newKeepUnit);}
public void setQ_IsFix_newKeepUnit(String q_IsFix_newKeepUnit) {this.q_IsFix_newKeepUnit = checkSet(q_IsFix_newKeepUnit);}
public String getQ_IsFix_newKeeper() {return checkGet(q_IsFix_newKeeper);}
public void setQ_IsFix_newKeeper(String q_IsFix_newKeeper) {this.q_IsFix_newKeeper = checkSet(q_IsFix_newKeeper);}
public String getQ_IsFix_newUseUnit() {return checkGet(q_IsFix_newUseUnit);}
public void setQ_IsFix_newUseUnit(String q_IsFix_newUseUnit) {this.q_IsFix_newUseUnit = checkSet(q_IsFix_newUseUnit);}
public String getQ_IsFix_newUserNo() {return checkGet(q_IsFix_newUserNo);}
public void setQ_IsFix_newUserNo(String q_IsFix_newUserNo) {this.q_IsFix_newUserNo = checkSet(q_IsFix_newUserNo);}

String q_dataState;
String q_propertyNoS;
String q_propertyNoE;
String q_propertyNoSName;
String q_propertyNoEName;
String q_serialNoS;
String q_serialNoE;
String q_propertyKind;
String q_fundType;
String q_valuable;

String q_keepBureau;
String q_keepUnit;
String q_keeper;
String q_useBureau;
String q_useUnit;
String q_userNo;

public String getQ_dataState(){ return checkGet(q_dataState); }
public void setQ_dataState(String s){ q_dataState=checkSet(s); }
public String getQ_propertyNoS(){ return checkGet(q_propertyNoS); }
public void setQ_propertyNoS(String s){ q_propertyNoS=checkSet(s); }
public String getQ_propertyNoE(){ return checkGet(q_propertyNoE); }
public void setQ_propertyNoE(String s){ q_propertyNoE=checkSet(s); }
public String getQ_propertyNoSName(){ return checkGet(q_propertyNoSName); }
public void setQ_propertyNoSName(String s){ q_propertyNoSName=checkSet(s); }
public String getQ_propertyNoEName(){ return checkGet(q_propertyNoEName); }
public void setQ_propertyNoEName(String s){ q_propertyNoEName=checkSet(s); }
public String getQ_serialNoS(){ return checkGet(q_serialNoS); }
public void setQ_serialNoS(String s){ q_serialNoS=checkSet(s); }
public String getQ_serialNoE(){ return checkGet(q_serialNoE); }
public void setQ_serialNoE(String s){ q_serialNoE=checkSet(s); }
public String getQ_propertyKind(){ return checkGet(q_propertyKind); }
public void setQ_propertyKind(String s){ q_propertyKind=checkSet(s); }
public String getQ_fundType(){ return checkGet(q_fundType); }
public void setQ_fundType(String s){ q_fundType=checkSet(s); }
public String getQ_valuable(){ return checkGet(q_valuable); }
public void setQ_valuable(String s){ q_valuable=checkSet(s); }
public String getQ_caseNo(){ return checkGet(q_caseNo); }
public void setQ_caseNo(String s){ q_caseNo=checkSet(s); }
public String getQ_verify(){ return checkGet(q_verify); }
public void setQ_verify(String s){ q_verify=checkSet(s); }

public String getQ_keepBureau(){ return checkGet(q_keepBureau); }
public void setQ_keepBureau(String s){ q_keepBureau=checkSet(s); }
public String getQ_keepUnit(){ return checkGet(q_keepUnit); }
public void setQ_keepUnit(String s){ q_keepUnit=checkSet(s); }
public String getQ_keeper(){ return checkGet(q_keeper); }
public void setQ_keeper(String s){ q_keeper=checkSet(s); }

public String getQ_useBureau(){ return checkGet(q_useBureau); }
public void setQ_useBureau(String s){ q_useBureau=checkSet(s); }
public String getQ_useUnit(){ return checkGet(q_useUnit); }
public void setQ_useUnit(String s){ q_useUnit=checkSet(s); }
public String getQ_userNo(){ return checkGet(q_userNo); }
public void setQ_userNo(String s){ q_userNo=checkSet(s); }

String toggleAll;
public String getToggleAll(){ if (!"".equals(checkGet(toggleAll))) return "checked"; else return "unchecked"; }    
public void setToggleAll(String s){ toggleAll=checkSet(s); }    

String closing;

public String getClosing(){ return checkGet(closing); }
public void setClosing(String s){ closing=checkSet(s); }

private String q_laSignNo1;
private String q_laSignNo2;
private String q_laSignNo3;
private String q_laSignNo4;
private String q_laSignNo5;
private String q_buSignNo1;
private String q_buSignNo2;
private String q_buSignNo3;
private String q_buSignNo4;
private String q_buSignNo5;
public String getQ_laSignNo1() {return checkGet(q_laSignNo1);}
public void setQ_laSignNo1(String q_laSignNo1) {this.q_laSignNo1 = checkSet(q_laSignNo1);}
public String getQ_laSignNo2() {return checkGet(q_laSignNo2);}
public void setQ_laSignNo2(String q_laSignNo2) {this.q_laSignNo2 = checkSet(q_laSignNo2);}
public String getQ_laSignNo3() {return checkGet(q_laSignNo3);}
public void setQ_laSignNo3(String q_laSignNo3) {this.q_laSignNo3 = checkSet(q_laSignNo3);}
public String getQ_laSignNo4() {return checkGet(q_laSignNo4);}
public void setQ_laSignNo4(String q_laSignNo4) {this.q_laSignNo4 = checkSet(q_laSignNo4);}
public String getQ_laSignNo5() {return checkGet(q_laSignNo5);}
public void setQ_laSignNo5(String q_laSignNo5) {this.q_laSignNo5 = checkSet(q_laSignNo5);}
public String getQ_buSignNo1() {return checkGet(q_buSignNo1);}
public void setQ_buSignNo1(String q_buSignNo1) {this.q_buSignNo1 = checkSet(q_buSignNo1);}
public String getQ_buSignNo2() {return checkGet(q_buSignNo2);}
public void setQ_buSignNo2(String q_buSignNo2) {this.q_buSignNo2 = checkSet(q_buSignNo2);}
public String getQ_buSignNo3() {return checkGet(q_buSignNo3);}
public void setQ_buSignNo3(String q_buSignNo3) {this.q_buSignNo3 = checkSet(q_buSignNo3);}
public String getQ_buSignNo4() {return checkGet(q_buSignNo4);}
public void setQ_buSignNo4(String q_buSignNo4) {this.q_buSignNo4 = checkSet(q_buSignNo4);}
public String getQ_buSignNo5() {return checkGet(q_buSignNo5);}
public void setQ_buSignNo5(String q_buSignNo5) {this.q_buSignNo5 = checkSet(q_buSignNo5);}

private String q_proofYear;
private String q_proofDoc;
private String q_proofNoS;
private String q_proofNoE;
private String q_moveProofYear;
private String q_moveProofDoc;
private String q_moveProofNoS;
private String q_moveProofNoE;

public String getQ_proofYear() {return checkGet(q_proofYear);}
public void setQ_proofYear(String q_proofYear) {this.q_proofYear = checkSet(q_proofYear);}
public String getQ_proofDoc(){ return checkGet(q_proofDoc); }
public void setQ_proofDoc(String s){ q_proofDoc=checkSet(s); }    
public String getQ_proofNoS(){ return checkGet(q_proofNoS); }
public void setQ_proofNoS(String s){ q_proofNoS=checkSet(s); }
public String getQ_proofNoE(){ return checkGet(q_proofNoE); }
public void setQ_proofNoE(String s){ q_proofNoE=checkSet(s); } 
public String getQ_moveProofYear() { return checkGet(q_moveProofYear); }
public void setQ_moveProofYear(String q_moveProofYear) { this.q_moveProofYear = checkSet(q_moveProofYear); }
public String getQ_moveProofDoc() { return checkGet(q_moveProofDoc); }
public void setQ_moveProofDoc(String q_moveProofDoc) { this.q_moveProofDoc = checkSet(q_moveProofDoc); }
public String getQ_moveProofNoS() { return checkGet(q_moveProofNoS); }
public void setQ_moveProofNoS(String q_moveProofNoS) { this.q_moveProofNoS = checkSet(q_moveProofNoS); }
public String getQ_moveProofNoE() { return checkGet(q_moveProofNoE); }
public void setQ_moveProofNoE(String q_moveProofNoE) { this.q_moveProofNoE = checkSet(q_moveProofNoE); }

 

/**
 * <br>
 * <br>目的：傳回insert sql
 * <br>參數：無 
 * <br>傳回：無 ; 直接更改變數為新增成功與否
*/	
public void insert(){
	int getcut=0;
	if(getsKeySet()!=null)
		getcut = getsKeySet().length;	//有勾選的list中資料數
	
	String[] strKeys = null;
	int i = 0;	
	
	UNTCH_Tools ut = new UNTCH_Tools();
	UNTCH002F02 sbTarget;
	for (i=0; i<getcut; i++) {	
		strKeys = getsKeySet()[i].split(",");
		
		String differenceKind = strKeys[2];
		String propertyNo = strKeys[3];
		String serialNo = strKeys[4];
		
		String sql = "select " +
				" propertyNo," +
				" serialNo," +
				" differenceKind," +
				" engineeringNo, " +
				" (select engineeringname from UNTEG_ENGINEERINGCASE z where z.enterorg = a.enterorg and z.engineeringno = a.engineeringno) as engineeringnoname, " +
				" propertyName1, " +
				" oldPropertyNo, " +
				" oldSerialNo, " +
				" area as oldLaArea, " +
				" holdnume as oldLaHoldNume, " +
				" holddeno as oldLaHoldDeno, " +
				" holdArea as oldLaHoldArea, " +
				" null as oldBuCArea, " +
				" null as oldBuSArea, " +
				" null as oldBuArea, " +
				" null as oldBuHoldNume, " +
				" null as oldBuHoldDeno, " +
				" null as oldBuHoldArea, " +
				" null as oldMeasure, " +
				" sourceDate, " +
				" originalBV, " +
				" buyDate, " +
				" null as bookNotes, " +
				" '1' as bookamount, " +
				" bookvalue, " +
				" bookunit, " +
				" netvalue, " +
				" netUnit, " +
				" null as oldBookAmount, " +
				" null as oldBookUnit, " +
				" null as oldBookValue, " +
				" null as oldNetUnit, " +
				" null as oldNetValue, " +
				" null as adjustBookAmount, " +
				" null as adjustBookUnit, " +
				" null as adjustBookValue, " +
				" null as adjustAccumDepr, " +
				" null as adjustNetUnit, " +
				" null as adjustNetValue, " +
				" null as newBookAmount, " +
				" null as newBookUnit, " +
				" null as newBookValue, " +
				" null as newNetUnit, " +
				" null as newNetValue, " +				
				" keepUnit, " +
				" keeper, " +
				" useUnit, " +
				" userNo, " +
				" userNote, " +
				" place1, " +
				" (select placename from SYSCA_PLACE z where z.enterorg = a.enterorg and z.placeno = a.place1) as place1name, " +
				" place, " +
				" null as oldDeprMethod, " +
				" null as oldBuildFeeCB, " +
				" null as oldDeprUnitCB, " +
				" null as oldDeprPark, " +
				" null as oldDeprUnit, " +
				" null as oldDeprUnit1, " +
				" null as oldDeprAccounts, " +
				" null as oldScrapValue, " +
				" null as oldDeprAmount, " +
				" '0' as oldAccumDepr, " +
				" null as oldApportionMonth, " +
				" null as oldMonthDepr, " +
				" null as oldMonthDepr1, " +
				" null as oldApportionEndYM, " +
				" (select material from SYSPK_PROPERTYCODE z where z.propertyno = a.propertyno and z.enterorg = '000000000A') as material," +
				" (select propertyunit from SYSPK_PROPERTYCODE z where z.propertyno = a.propertyno and z.enterorg = '000000000A') as propertyunit," +
				" (select limityear from SYSPK_PROPERTYCODE z where z.propertyno = a.propertyno and z.enterorg = '000000000A') as limityear," +
				" null as otherMaterial, " +
				" null as otherPropertyUnit, " +
				" null as otherLimitYear, " +
				" null as securityName, " +
				" signno as lasignNo, " +
				" null as busignNo, " +
				" propertyKind, " +
				" fundType, " +
				" valuable, " +
				" taxCredit, " +
				" null as nameplate," +
				" null as specification," +
				" null as lotNo " +
			" from UNTLA_LAND a where 1=1" + 
			" and enterorg = '" + enterOrg + "' and ownership = '" + ownership + "' and differenceKind = '" + differenceKind + "' and verify = 'Y'" +			
			("".equals(propertyNo)?"":" and propertyNo = '" + propertyNo + "'") + 
			("".equals(serialNo)?"":" and serialNo = '" + serialNo + "'") +
		" union " +
		"select " +
				" propertyNo," +
				" serialNo," +
				" differenceKind," +
				" engineeringNo, " +
				" (select engineeringname from UNTEG_ENGINEERINGCASE z where z.enterorg = a.enterorg and z.engineeringno = a.engineeringno) as engineeringnoname, " +
				" propertyName1, " +
				" oldPropertyNo, " +
				" oldSerialNo, " +
				" null as oldLaArea, " +
				" null as oldLaHoldNume, " +
				" null as oldLaHoldDeno, " +
				" null as oldLaHoldArea, " +
				" CArea as oldBuCArea, " +
				" SArea as oldBuSArea, " +
				" Area as oldBuArea, " +
				" HoldNume as oldBuHoldNume, " +
				" HoldDeno as oldBuHoldDeno, " +
				" HoldArea as oldBuHoldArea, " +
				" null as oldMeasure, " +
				" sourceDate, " +
				" originalBV, " +
				" buyDate, " +
				" null as bookNotes, " +
				" '1' as bookamount, " +
				" bookvalue, " +
				" null as BookUnit, " +
				" netvalue, " +
				" null as NetUnit, " +
				" null as oldBookAmount, " +
				" null as oldBookUnit, " +
				" null as oldBookValue, " +
				" null as oldNetUnit, " +
				" null as oldNetValue, " +
				" null as adjustBookAmount, " +
				" null as adjustBookUnit, " +
				" null as adjustBookValue, " +
				" null as adjustAccumDepr, " +
				" null as adjustNetUnit, " +
				" null as adjustNetValue, " +
				" null as newBookAmount, " +
				" null as newBookUnit, " +
				" null as newBookValue, " +
				" null as newNetUnit, " +
				" null as newNetValue, " +
				" keepUnit, " +
				" keeper, " +
				" useUnit, " +
				" userNo, " +
				" userNote, " +
				" place1, " +
				" (select placename from SYSCA_PLACE z where z.enterorg = a.enterorg and z.placeno = a.place1) as place1name, " +
				" place, " +
				" deprMethod as oldDeprMethod, " +
				" buildFeeCB as oldBuildFeeCB, " +
				" deprUnitCB as oldDeprUnitCB, " +
				" deprPark as oldDeprPark, " +
				" deprUnit as oldDeprUnit, " +
				" deprUnit1 as oldDeprUnit1, " +
				" deprAccounts as oldDeprAccounts, " +
				" scrapValue as oldScrapValue, " +
				" deprAmount as oldDeprAmount, " +
				" accumDepr as oldAccumDepr, " +
				" apportionMonth as oldApportionMonth, " +
				" monthDepr as oldMonthDepr, " +
				" monthDepr1 as oldMonthDepr1, " +
				" apportionEndYM as oldApportionEndYM, " +
				" (select material from SYSPK_PROPERTYCODE z where z.propertyno = a.propertyno and z.enterorg = '000000000A') as material," +
				" (select propertyunit from SYSPK_PROPERTYCODE z where z.propertyno = a.propertyno and z.enterorg = '000000000A') as propertyunit," +
				" (select limityear from SYSPK_PROPERTYCODE z where z.propertyno = a.propertyno and z.enterorg = '000000000A') as limityear," +
				" null as otherMaterial, " +
				" null as otherPropertyUnit, " +
				" otherLimitYear, " +
				" null as securityName, " +
				" null as lasignNo, " +
				" signno as busignNo, " +
				" propertyKind, " +
				" fundType, " +
				" valuable, " +
				" taxCredit, " +
				" null as nameplate," +
				" null as specification," +
				" null as lotNo " +
			" from UNTBU_BUILDING a where 1=1" + 
			" and enterorg = '" + enterOrg + "' and ownership = '" + ownership + "' and differenceKind = '" + differenceKind + "' and verify = 'Y'" + 
			("".equals(propertyNo)?"":" and propertyNo = '" + propertyNo + "'") + 
			("".equals(serialNo)?"":" and serialNo = '" + serialNo + "'") +
		" union " +
		"select " +
				" propertyNo," +
				" serialNo," +
				" differenceKind," +
				" engineeringNo, " +
				" (select engineeringname from UNTEG_ENGINEERINGCASE z where z.enterorg = a.enterorg and z.engineeringno = a.engineeringno) as engineeringnoname, " +
				" propertyName1, " +
				" oldPropertyNo, " +
				" oldSerialNo, " +
				" null as oldLaArea, " +
				" null as oldLaHoldNume, " +
				" null as oldLaHoldDeno, " +
				" null as oldLaHoldArea, " +
				" null as oldBuCArea, " +
				" null as oldBuSArea, " +
				" null as oldBuArea, " +
				" null as oldBuHoldNume, " +
				" null as oldBuHoldDeno, " +
				" null as oldBuHoldArea, " +
				" measure as oldMeasure, " +
				" sourceDate, " +
				" originalBV, " +
				" buyDate, " +
				" null as bookNotes, " +
				" '1' as bookamount, " +
				" bookvalue, " +
				" null as BookUnit, " +
				" netvalue, " +
				" null as dNetUnit, " +
				" '1' as oldBookAmount, " +
				" null as oldBookUnit, " +
				" null as oldBookValue, " +
				" null as oldNetUnit, " +
				" null as oldNetValue, " +
				" null as adjustBookAmount, " +
				" null as adjustBookUnit, " +
				" null as adjustBookValue, " +
				" null as adjustAccumDepr, " +
				" null as adjustNetUnit, " +
				" null as adjustNetValue, " +
				" null as newBookAmount, " +
				" null as newBookUnit, " +
				" null as newBookValue, " +
				" null as newNetUnit, " +
				" null as newNetValue, " +
				" keepUnit, " +
				" keeper, " +
				" useUnit, " +
				" userNo, " +
				" userNote, " +
				" place1, " +
				" (select placename from SYSCA_PLACE z where z.enterorg = a.enterorg and z.placeno = a.place1) as place1name, " +
				" place, " +
				" deprMethod as oldDeprMethod, " +
				" buildFeeCB as oldBuildFeeCB, " +
				" deprUnitCB as oldDeprUnitCB, " +
				" deprPark as oldDeprPark, " +
				" deprUnit as oldDeprUnit, " +
				" deprUnit1 as oldDeprUnit1, " +
				" deprAccounts as oldDeprAccounts, " +
				" scrapValue as oldScrapValue, " +
				" deprAmount as oldDeprAmount, " +
				" accumDepr as oldAccumDepr, " +
				" apportionMonth as oldApportionMonth, " +
				" monthDepr as oldMonthDepr, " +
				" monthDepr1 as oldMonthDepr1, " +
				" apportionEndYM as oldApportionEndYM, " +
				" (select material from SYSPK_PROPERTYCODE z where z.propertyno = a.propertyno and z.enterorg = '000000000A') as material," +
				" (select propertyunit from SYSPK_PROPERTYCODE z where z.propertyno = a.propertyno and z.enterorg = '000000000A') as propertyunit," +
				" (select limityear from SYSPK_PROPERTYCODE z where z.propertyno = a.propertyno and z.enterorg = '000000000A') as limityear," +
				" null as otherMaterial, " +
				" null as otherPropertyUnit, " +
				" otherLimitYear, " +
				" null as securityName, " +
				" null as lasignNo, " +
				" null as busignNo, " +
				" propertyKind, " +
				" fundType, " +
				" valuable, " +
				" taxCredit, " +
				" null as nameplate," +
				" null as specification," +
				" null as lotNo " +
			" from UNTRF_ATTACHMENT a where 1=1" + 
			" and enterorg = '" + enterOrg + "' and ownership = '" + ownership + "' and differenceKind = '" + differenceKind + "' and verify = 'Y'" + 
			("".equals(propertyNo)?"":" and propertyNo = '" + propertyNo + "'") + 
			("".equals(serialNo)?"":" and serialNo = '" + serialNo + "'") +
		" union " +
		"select " +
				" b.propertyNo," +
				" isnull(a.serialno, b.serialnos)," +
				" b.differenceKind," +
				" b.engineeringNo, " +
				" (select engineeringname from UNTEG_ENGINEERINGCASE z where z.enterorg = b.enterorg and z.engineeringno = b.engineeringno) as engineeringnoname, " +
				" a.propertyName1, " +
				" a.oldPropertyNo, " +
				" a.oldSerialNo, " +
				" null as oldLaArea, " +
				" null as oldLaHoldNume, " +
				" null as oldLaHoldDeno, " +
				" null as oldLaHoldArea, " +
				" null as oldBuCArea, " +
				" null as oldBuSArea, " +
				" null as oldBuArea, " +
				" null as oldBuHoldNume, " +
				" null as oldBuHoldDeno, " +
				" null as oldBuHoldArea, " +
				" null as oldMeasure, " +
				" sourceDate, " +
				" b.originalBV, " +
				" buyDate, " +
				" null as bookNotes, " +
				" null as bookamount, " +
				" null as BookValue, " +
				" null as BookUnit, " +
				" null as netvalue, " +
				" null as NetUnit, " +
				" a.bookamount as oldBookAmount, " +						
				" case when b.originalunit is null then null else a.bookvalue end as oldBookUnit, " +
				" isnull(a.bookvalue, b.bookvalue) as oldBookValue, " +
				" case when b.originalunit is null then null else a.netvalue end as oldNetUnit, " +
				" isnull(a.netvalue,  b.netvalue) as oldNetValue, " +
				" b.bookamount as adjustBookAmount, " +
				" null as adjustBookUnit, " +
				" b.bookvalue as adjustBookValue, " +
				" b.bookvalue as adjustAccumDepr, " +
				" null as adjustNetUnit, " +
				" b.netvalue as adjustNetValue, " +
				" '0' as newBookAmount, " +
				" '0' as newBookUnit, " +
				" '0' as newBookValue, " +
				" '0' as newNetUnit, " +
				" '0' as newNetValue, " +
				" keepUnit, " +
				" keeper, " +
				" useUnit, " +
				" userNo, " +
				" userNote, " +
				" place1, " +
				" (select placename from SYSCA_PLACE z where z.enterorg = a.enterorg and z.placeno = a.place1) as place1name, " +
				" place, " +
				" a.deprMethod as oldDeprMethod, " +
				" a.buildFeeCB as oldBuildFeeCB, " +
				" a.deprUnitCB as oldDeprUnitCB, " +
				" a.deprPark as oldDeprPark, " +
				" a.deprUnit as oldDeprUnit, " +
				" a.deprUnit1 as oldDeprUnit1, " +
				" a.deprAccounts as oldDeprAccounts, " +
				" a.scrapValue as oldScrapValue, " +
				" a.deprAmount as oldDeprAmount, " +
				" a.accumDepr as oldAccumDepr, " +
				" a.apportionMonth as oldApportionMonth, " +
				" a.monthDepr as oldMonthDepr, " +
				" a.monthDepr1 as oldMonthDepr1, " +
				" a.apportionEndYM as oldApportionEndYM, " +
				" (select material from SYSPK_PROPERTYCODE z where z.propertyno = b.propertyno and z.enterorg = '000000000A') as material," +
				" (select propertyunit from SYSPK_PROPERTYCODE z where z.propertyno = b.propertyno and z.enterorg = '000000000A') as propertyunit," +
				" (select limityear from SYSPK_PROPERTYCODE z where z.propertyno = b.propertyno and z.enterorg = '000000000A') as limityear," +
				" b.othermaterial as otherMaterial, " +
				" b.otherpropertyunit as otherPropertyUnit, " +
				" a.otherlimityear as otherLimitYear, " +
				" null as securityName, " +
				" null as lasignNo, " +
				" null as busignNo, " +
				" propertyKind, " +
				" fundType, " +
				" valuable, " +
				" null as taxCredit, " +
				" b.nameplate," +
				" b.specification," +
				" b.lotNo " +
			" from UNTMP_MOVABLEDETAIL a " +
				" left join UNTMP_MOVABLE b on a.enterorg = b.enterorg and a.ownership = b.ownership and a.caseno = b.caseno and a.differencekind = b.differencekind and a.propertyno = b.propertyno and a.lotno = b.lotno" +
				" where 1=1" + 
				" and a.enterorg = '" + enterOrg + "' and a.ownership = '" + ownership + "' and a.differenceKind = '" + differenceKind + "' and a.verify = 'Y'" + 
				("".equals(propertyNo)?"":" and a.propertyNo = '" + propertyNo + "'") + 
				("".equals(serialNo)?"":" and a.serialNo = '" + serialNo + "'") +
			//	("".equals(serialNo)?"":" and a.serialnos <= '" + serialNo + "' and a.serialnoe >= '" + serialNo + "'") +
		" union " +
		"select " +
				" propertyNo," +
				" serialNo," +
				" differenceKind," +
				" engineeringNo, " +
				" (select engineeringname from UNTEG_ENGINEERINGCASE z where z.enterorg = a.enterorg and z.engineeringno = a.engineeringno) as engineeringnoname, " +
				" propertyName1, " +
				" oldPropertyNo, " +
				" oldSerialNo, " +
				" null as oldLaArea, " +
				" null as oldLaHoldNume, " +
				" null as oldLaHoldDeno, " +
				" null as oldLaHoldArea, " +
				" null as oldBuCArea, " +
				" null as oldBuSArea, " +
				" null as oldBuArea, " +
				" null as oldBuHoldNume, " +
				" null as oldBuHoldDeno, " +
				" null as oldBuHoldArea, " +
				" null as oldMeasure, " +
				" sourceDate, " +
				" originalBV, " +
				" buyDate, " +
				" null as bookNotes, " +
				" bookamount, " +
				" bookvalue, " +
				" null as BookUnit, " +
				" null as NetValue, " +
				" null as NetUnit, " +
				" '1' as oldBookAmount, " +
				" null as oldBookUnit, " +
				" null as oldBookValue, " +
				" null as oldNetUnit, " +
				" null as oldNetValue, " +
				" null as adjustBookAmount, " +
				" null as adjustBookUnit, " +
				" null as adjustBookValue, " +
				" null as adjustAccumDepr, " +
				" null as adjustNetUnit, " +
				" null as adjustNetValue, " +
				" null as newBookAmount, " +
				" null as newBookUnit, " +
				" null as newBookValue, " +
				" null as newNetUnit, " +
				" null as newNetValue, " +
				" keepUnit, " +
				" keeper, " +
				" useUnit, " +
				" userNo, " +
				" userNote, " +
				" place1, " +
				" (select placename from SYSCA_PLACE z where z.enterorg = a.enterorg and z.placeno = a.place1) as place1name, " +
				" place, " +
				" null as oldDeprMethod, " +
				" null as oldBuildFeeCB, " +
				" null as oldDeprUnitCB, " +
				" null as oldDeprPark, " +
				" null as oldDeprUnit, " +
				" null as oldDeprUnit1, " +
				" null as oldDeprAccounts, " +
				" null as oldScrapValue, " +
				" null as oldDeprAmount, " +
				" '0' as oldAccumDepr, " +
				" null as oldApportionMonth, " +
				" null as oldMonthDepr, " +
				" null as oldMonthDepr1, " +
				" null as oldApportionEndYM, " +
				" (select material from SYSPK_PROPERTYCODE z where z.propertyno = a.propertyno and z.enterorg = '000000000A') as material," +
				" (select propertyunit from SYSPK_PROPERTYCODE z where z.propertyno = a.propertyno and z.enterorg = '000000000A') as propertyunit," +
				" (select limityear from SYSPK_PROPERTYCODE z where z.propertyno = a.propertyno and z.enterorg = '000000000A') as limityear," +
				" null as otherMaterial, " +
				" null as otherPropertyUnit, " +
				" null as otherLimitYear, " +
				" securityName, " +
				" null as lasignNo, " +
				" null as busignNo, " +
				" propertyKind, " +
				" fundType, " +
				" 'N' as valuable, " +
				" null as taxCredit, " +
				" null as nameplate," +
				" null as specification," +
				" null as lotNo " +
			" from UNTVP_ADDPROOF a where 1=1" + 
			" and enterorg = '" + enterOrg + "' and ownership = '" + ownership + "' and differenceKind = '" + differenceKind + "' and verify = 'Y'" + 
			("".equals(propertyNo)?"":" and propertyNo = '" + propertyNo + "'") + 
			("".equals(serialNo)?"":" and serialNo = '" + serialNo + "'") +
		" union " +
		"select " +
				" propertyNo," +
				" serialNo," +
				" differenceKind," +
				" engineeringNo, " +
				" (select engineeringname from UNTEG_ENGINEERINGCASE z where z.enterorg = a.enterorg and z.engineeringno = a.engineeringno) as engineeringnoname, " +
				" propertyName1, " +
				" oldPropertyNo, " +
				" oldSerialNo, " +
				" null as oldLaArea, " +
				" null as oldLaHoldNume, " +
				" null as oldLaHoldDeno, " +
				" null as oldLaHoldArea, " +
				" null as oldBuCArea, " +
				" null as oldBuSArea, " +
				" null as oldBuArea, " +
				" null as oldBuHoldNume, " +
				" null as oldBuHoldDeno, " +
				" null as oldBuHoldArea, " +
				" null as oldMeasure, " +
				" sourceDate, " +
				" originalBV, " +
				" buyDate, " +
				" null as bookNotes, " +
				" '1' as bookamount, " +
				" bookvalue, " +
				" null as BookUnit, " +
				" null as NetValue, " +
				" null as NetUnit, " +
				" null as oldBookAmount, " +
				" null as oldBookUnit, " +
				" null as oldBookValue, " +
				" null as oldNetUnit, " +
				" null as oldNetValue, " +
				" null as adjustBookAmount, " +
				" null as adjustBookUnit, " +
				" null as adjustBookValue, " +
				" null as adjustAccumDepr, " +
				" null as adjustNetUnit, " +
				" null as adjustNetValue, " +
				" null as newBookAmount, " +
				" null as newBookUnit, " +
				" null as newBookValue, " +
				" null as newNetUnit, " +
				" null as newNetValue, " +
				" keepUnit, " +
				" keeper, " +
				" useUnit, " +
				" userNo, " +
				" userNote, " +
				" place1, " +
				" (select placename from SYSCA_PLACE z where z.enterorg = a.enterorg and z.placeno = a.place1) as place1name, " +
				" place, " +
				" deprmethod as oldDeprMethod, " +
				" buildfeecb as oldBuildFeeCB, " +
				" deprunitcb as oldDeprUnitCB, " +
				" deprpark as oldDeprPark, " +
				" deprunit as oldDeprUnit, " +
				" deprunit1 as oldDeprUnit1, " +
				" depraccounts as oldDeprAccounts, " +
				" scrapvalue as oldScrapValue, " +
				" depramount as oldDeprAmount, " +
				" accumdepr as oldAccumDepr, " +
				" apportionmonth as oldApportionMonth, " +
				" monthdepr as oldMonthDepr, " +
				" monthDepr1 as oldMonthDepr1, " +
				" apportionendym as oldApportionEndYM, " +
				" (select material from SYSPK_PROPERTYCODE z where z.propertyno = a.propertyno and z.enterorg = '000000000A') as material," +
				" (select propertyunit from SYSPK_PROPERTYCODE z where z.propertyno = a.propertyno and z.enterorg = '000000000A') as propertyunit," +
				" (select limityear from SYSPK_PROPERTYCODE z where z.propertyno = a.propertyno and z.enterorg = '000000000A') as limityear," +
				" null as otherMaterial, " +
				" null as otherPropertyUnit, " +
				" otherlimityear as otherLimitYear, " +
				" null as securityName, " +
				" null as lasignNo, " +
				" null as busignNo, " +
				" propertyKind, " +
				" fundType, " +
				" 'N' as valuable, " +
				" null as taxCredit, " +
				" null as nameplate," +
				" null as specification," +
				" null as lotNo " +
			" from UNTRT_ADDPROOF a where 1=1" + 
			" and enterorg = '" + enterOrg + "' and ownership = '" + ownership + "' and differenceKind = '" + differenceKind + "' and verify = 'Y'" + 
			("".equals(propertyNo)?"":" and propertyNo = '" + propertyNo + "'") + 
			("".equals(serialNo)?"":" and serialNo = '" + serialNo + "'") +
		"";
		
		Database db = null;
		ResultSet rs = null;
		try{
			String propertyKind = ut._getDefaultValue(this.getEnterOrg(), "propertyKind");
			db = new Database();
			rs = db.querySQL(sql);
			while(rs.next()){
				sbTarget = new UNTCH002F02();
				
				sbTarget.setUserID(this.getUserID());
				sbTarget.setEnterOrg(this.getEnterOrg());
				sbTarget.setOwnership(this.getOwnership());
				sbTarget.setCaseNo(this.getCaseNo());
				sbTarget.setPropertyKind(propertyKind);
				sbTarget.setVerify("N");				
				
				sbTarget.setPropertyNo(rs.getString("propertyNo"));
				sbTarget.setSerialNo(rs.getString("serialNo"));
				sbTarget.setDifferenceKind(rs.getString("differenceKind"));
				
				sbTarget.setPropertyName1(rs.getString("propertyName1"));
				sbTarget.setOldPropertyNo(rs.getString("oldPropertyNo"));
				sbTarget.setOldSerialNo(rs.getString("oldSerialNo"));

				sbTarget.setAreaLA(rs.getString("oldLaArea"));
				sbTarget.setHoldNumeLA(rs.getString("oldLaHoldNume"));
				sbTarget.setHoldNumeLA(rs.getString("oldLaHoldDeno"));
				sbTarget.setHoldAreaLA(rs.getString("oldLaHoldArea"));
				
				sbTarget.setCAreaBU(rs.getString("oldBuCArea"));
				sbTarget.setSAreaBU(rs.getString("oldBuSArea"));
				sbTarget.setAreaBU(rs.getString("oldBuArea"));
				sbTarget.setHoldNumeBU(rs.getString("oldBuHoldNume"));
				sbTarget.setHoldDenoBU(rs.getString("oldBuHoldDeno"));
				sbTarget.setHoldAreaBU(rs.getString("oldBuHoldArea"));
				
				sbTarget.setMeasure(rs.getString("oldMeasure"));

				sbTarget.setSourceDate(ut._transToROC_Year(rs.getString("sourceDate")));
				sbTarget.setBuyDate(ut._transToROC_Year(rs.getString("buyDate")));
				
				String check1 = sbTarget.getPropertyNo().substring(0,1);
				String check3 = sbTarget.getPropertyNo().substring(0,3);
				
				if(check3.equals("111")){
					
				}else if(check1.equals("1")){
					sbTarget.setSignNoLA(rs.getString("lasignno"));
				}else if(check1.equals("2")){
					sbTarget.setSignNoBU(rs.getString("busignno"));
				}
				
				if(check1.equals("3") || check1.equals("4") || check1.equals("5")){
					sbTarget.setBookAmount(rs.getString("oldBookAmount"));
					sbTarget.setBookUnit(rs.getString("oldBookUnit"));
					sbTarget.setBookValue(rs.getString("oldBookValue"));
					sbTarget.setNetUnit(rs.getString("oldNetUnit"));
					sbTarget.setNetValue(rs.getString("oldNetValue"));				
					
				}else if(check1.equals("8") || check1.equals("9")){
					sbTarget.setBookAmount(rs.getString("bookAmount"));
					sbTarget.setBookValue(rs.getString("bookValue"));
					sbTarget.setNetValue(rs.getString("bookValue"));	
					
				}else{
					sbTarget.setBookAmount(rs.getString("bookAmount"));
					sbTarget.setBookUnit(rs.getString("bookUnit"));
					sbTarget.setBookValue(rs.getString("bookValue"));
					sbTarget.setNetUnit(rs.getString("netUnit"));
					sbTarget.setNetValue(rs.getString("netValue"));	
						
				}
					
				sbTarget.setOldKeeper(rs.getString("keeper"));
				sbTarget.setOldKeepUnit(rs.getString("keepUnit"));
				sbTarget.setOldUseUnit(rs.getString("useUnit"));
				sbTarget.setOldUserNo(rs.getString("userNo"));
				sbTarget.setOldUserNote(rs.getString("userNote"));
				sbTarget.setOldPlace1(rs.getString("place1"));
				sbTarget.setOldPlace1Name(rs.getString("place1name"));
				sbTarget.setOldPlace(rs.getString("place"));
				if ("Y".equals(getQ_IsFix_newKeeper())) {
					sbTarget.setNewKeeper(this.getQ_newKeeper());
				} else {
					sbTarget.setNewKeeper(rs.getString("keeper"));
				}
				if ("Y".equals(getQ_IsFix_newKeepUnit())) {
					sbTarget.setNewKeepUnit(this.getQ_newKeepUnit());
				} else {
					sbTarget.setNewKeepUnit(rs.getString("keepUnit"));
				}
				if ("Y".equals(getQ_IsFix_newUseUnit())) {
					sbTarget.setNewUseUnit(this.getQ_newUseUnit());
				} else {
					sbTarget.setNewUseUnit(rs.getString("useUnit"));
				}
				if ("Y".equals(getQ_IsFix_newUserNo())) {
					sbTarget.setNewUserNo(this.getQ_newUserNo());
				} else {
					sbTarget.setNewUserNo(rs.getString("userNo"));
				}
				if ("Y".equals(getQ_IsFix())) {
					sbTarget.setNewUserNote(this.getQ_newUserNote());
					sbTarget.setNewPlace1(this.getQ_newPlace1());
					sbTarget.setNewPlace1Name(this.getQ_newPlace1Name());
					sbTarget.setNewPlace(this.getQ_newPlace());
				} else {
					sbTarget.setNewUserNote(rs.getString("userNote"));
					sbTarget.setNewPlace1(rs.getString("place1"));
					sbTarget.setNewPlace1Name(rs.getString("place1name"));
					sbTarget.setNewPlace(rs.getString("place"));
				}
				
				sbTarget.setOldDeprMethod(rs.getString("oldDeprMethod"));
				sbTarget.setOldBuildFeeCB(rs.getString("oldBuildFeeCB"));
				sbTarget.setOldDeprUnitCB(rs.getString("oldDeprUnitCB"));
				sbTarget.setOldDeprPark(rs.getString("oldDeprPark"));
				sbTarget.setOldDeprUnit(rs.getString("oldDeprUnit"));
				sbTarget.setOldDeprUnit1(rs.getString("oldDeprUnit1"));
				sbTarget.setOldDeprAccounts(rs.getString("oldDeprAccounts"));
				sbTarget.setOldScrapValue(rs.getString("oldScrapValue"));
				sbTarget.setOldDeprAmount(rs.getString("oldDeprAmount"));
				sbTarget.setOldAccumDepr(rs.getString("oldAccumDepr"));
				sbTarget.setOldApportionMonth(rs.getString("oldApportionMonth"));
				sbTarget.setOldMonthDepr(rs.getString("oldMonthDepr"));
				sbTarget.setOldMonthDepr1(rs.getString("oldMonthDepr1"));
				sbTarget.setOldApportionEndYM(ut._transToROC_Year(rs.getString("oldApportionEndYM")));
				
				
				sbTarget.setNewDeprMethod(rs.getString("oldDeprMethod"));
				sbTarget.setNewBuildFeeCB(rs.getString("oldBuildFeeCB"));
				sbTarget.setNewDeprUnitCB(rs.getString("oldDeprUnitCB"));
				sbTarget.setNewDeprPark(rs.getString("oldDeprPark"));
				sbTarget.setNewDeprUnit(rs.getString("oldDeprUnit"));
				sbTarget.setNewDeprUnit1(rs.getString("oldDeprUnit1"));
				sbTarget.setNewDeprAccounts(rs.getString("oldDeprAccounts"));
				sbTarget.setNewScrapValue(rs.getString("oldScrapValue"));
				sbTarget.setNewDeprAmount(rs.getString("oldDeprAmount"));
				sbTarget.setNewAccumDepr(rs.getString("oldAccumDepr"));
				sbTarget.setNewApportionMonth(rs.getString("oldApportionMonth"));
				sbTarget.setNewMonthDepr(rs.getString("oldMonthDepr"));
				sbTarget.setNewMonthDepr1(rs.getString("oldMonthDepr1"));
				sbTarget.setNewApportionEndYM(ut._transToROC_Year(rs.getString("oldApportionEndYM")));
				
				sbTarget.setOtherLimitYear(rs.getString("otherLimitYear"));
				sbTarget.setOtherMaterial(rs.getString("otherMaterial"));
				sbTarget.setOtherPropertyUnit(rs.getString("otherPropertyUnit"));
				
				sbTarget.setSignNoLA(rs.getString("lasignNo"));
				sbTarget.setSignNoBU(rs.getString("busignNo"));
				
				sbTarget.setFundType(rs.getString("fundType"));
				sbTarget.setValuable(rs.getString("valuable"));
				sbTarget.setLotNo(rs.getString("lotNo"));
				
				sbTarget.setNameplate(rs.getString("nameplate"));
				sbTarget.setSpecification(rs.getString("specification"));
				
				sbTarget.insert();
			}
			rs.close();
			
			if("insertError".equals(getState())){
				setStateInsertError();
			}else{
				setStateInsertSuccess();
				setErrorMsg("新增完成");
				setQuerySelect("proof");
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			db.closeAll();
		}
		
	}	
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
	
	try {
		String sql = "select distinct * from (" + 
					" select" +
					" a.enterorg, a.ownership, a.differencekind, a.propertyno," +
					" a.serialno, a.oldserialno, a.propertyname1," +
					" (select z.propertyname from SYSPK_PROPERTYCODE z where z.enterorg in ('000000000A',a.enterorg) and z.propertyno = a.propertyno) as propertynoname," +
					" (select z.placename from SYSCA_PLACE z where z.enterorg = a.enterorg and z.placeno = a.place1) as place1," +
					" (select z.unitname from UNTMP_KEEPUNIT z where z.enterorg = a.enterorg and z.unitno = a.keepunit) as keepunit," +
					" (select z.keepername from UNTMP_KEEPER z where z.enterorg = a.enterorg and z.keeperno = a.keeper) as keeper" +
					" ,chk.* " +
					" from UNTLA_LAND a left join UNTLA_ADDPROOF c on a.enterorg=c.enterorg and a.ownership=c.ownership and a.caseno=c.caseno and a.differencekind=c.differencekind " +
					" LEFT JOIN UNTMP_MOVEDETAIL z ON z.enterorg = a.enterorg AND z.ownership = a.ownership AND z.differencekind = a.differencekind AND z.propertyno = a.propertyno AND z.serialno = a.serialno " +
					" LEFT JOIN UNTMP_MOVEPROOF x ON z.enterorg = x.enterorg AND z.caseno = x.caseno " +
					this.getOtherProofChk() +
					" where 1=1" +
					this.getCondition("LA") +
					" union" +
					" select" +
					" a.enterorg, a.ownership, a.differencekind, a.propertyno," +
					" a.serialno, a.oldserialno, a.propertyname1," +
					" (select z.propertyname from SYSPK_PROPERTYCODE z where z.enterorg in ('000000000A',a.enterorg) and z.propertyno = a.propertyno) as propertynoname," +
					" (select z.placename from SYSCA_PLACE z where z.enterorg = a.enterorg and z.placeno = a.place1) as place1," +
					" (select z.unitname from UNTMP_KEEPUNIT z where z.enterorg = a.enterorg and z.unitno = a.keepunit) as keepunit," +
					" (select z.keepername from UNTMP_KEEPER z where z.enterorg = a.enterorg and z.keeperno = a.keeper) as keeper" +
					" ,chk.* " +
					" from UNTBU_BUILDING a left join UNTBU_ADDPROOF c on a.enterorg=c.enterorg and a.ownership=c.ownership and a.caseno=c.caseno and a.differencekind=c.differencekind " +
					" LEFT JOIN UNTMP_MOVEDETAIL z ON z.enterorg = a.enterorg AND z.ownership = a.ownership AND z.differencekind = a.differencekind AND z.propertyno = a.propertyno AND z.serialno = a.serialno " +
					" LEFT JOIN UNTMP_MOVEPROOF x ON z.enterorg = x.enterorg AND z.caseno = x.caseno " +
					this.getOtherProofChk() +
					" where 1=1" +
					this.getCondition("BU") +
					" union" +
					" select" +
					" a.enterorg, a.ownership, a.differencekind, a.propertyno," +
					" a.serialno, a.oldserialno, a.propertyname1," +
					" (select z.propertyname from SYSPK_PROPERTYCODE z where z.enterorg in ('000000000A',a.enterorg) and z.propertyno = a.propertyno) as propertynoname," +
					" (select z.placename from SYSCA_PLACE z where z.enterorg = a.enterorg and z.placeno = a.place1) as place1," +
					" (select z.unitname from UNTMP_KEEPUNIT z where z.enterorg = a.enterorg and z.unitno = a.keepunit) as keepunit," +
					" (select z.keepername from UNTMP_KEEPER z where z.enterorg = a.enterorg and z.keeperno = a.keeper) as keeper" +
					" ,chk.* " +
					" from UNTRF_ATTACHMENT a left join UNTRF_ADDPROOF c on a.enterorg=c.enterorg and a.ownership=c.ownership and a.caseno=c.caseno and a.differencekind=c.differencekind " +
					" LEFT JOIN UNTMP_MOVEDETAIL z ON z.enterorg = a.enterorg AND z.ownership = a.ownership AND z.differencekind = a.differencekind AND z.propertyno = a.propertyno AND z.serialno = a.serialno " +
					" LEFT JOIN UNTMP_MOVEPROOF x ON z.enterorg = x.enterorg AND z.caseno = x.caseno " +
					this.getOtherProofChk() +	
					" where 1=1" +
					this.getCondition("RF") +
					" union" +
					" select" +
					" a.enterorg, a.ownership, a.differencekind, a.propertyno," +
					" a.serialno, a.oldserialno, " +
					" a.propertyname1," + 
					" (select z.propertyname from SYSPK_PROPERTYCODE z where z.enterorg in ('000000000A',a.enterorg) and z.propertyno = a.propertyno) as propertynoname," +
					" (select z.placename from SYSCA_PLACE z where z.enterorg = a.enterorg and z.placeno = a.place1) as place1," +
					" (select z.unitname from UNTMP_KEEPUNIT z where z.enterorg = a.enterorg and z.unitno = a.keepunit) as keepunit," +
					" (select z.keepername from UNTMP_KEEPER z where z.enterorg = a.enterorg and z.keeperno = a.keeper) as keeper" +
					" ,chk.* " +
					" from UNTMP_MOVABLE b left join UNTMP_ADDPROOF c on c.enterorg=b.enterorg and c.ownership=b.ownership and c.caseno=b.caseno and c.differencekind=b.differencekind" +
					" join UNTMP_MOVABLEDETAIL a on a.enterorg = b.enterorg and a.ownership = b.ownership and a.differencekind = b.differencekind and a.lotno = b.lotno and a.propertyno = b.propertyno" +
					" LEFT JOIN UNTMP_MOVEDETAIL z ON z.enterorg = a.enterorg AND z.ownership = a.ownership AND z.differencekind = a.differencekind AND z.propertyno = a.propertyno AND z.serialno = a.serialno " +
					" LEFT JOIN UNTMP_MOVEPROOF x ON z.enterorg = x.enterorg AND z.caseno = x.caseno " +
					this.getOtherProofChk() +
					" where 1=1"+
					this.getCondition("MP") +
					" union" +
					" select" +
					" a.enterorg, a.ownership, a.differencekind, a.propertyno," +
					" a.serialno, a.oldserialno, a.propertyname1," +
					" (select propertyname from SYSPK_PROPERTYCODE z where z.enterorg in ('000000000A',a.enterorg) and z.propertyno = a.propertyno) as propertynoname," +
					" (select z.placename from SYSCA_PLACE z where z.enterorg = a.enterorg and z.placeno = a.place1) as place1," +
					" (select z.unitname from UNTMP_KEEPUNIT z where z.enterorg = a.enterorg and z.unitno = a.keepunit) as keepunit," +
					" (select z.keepername from UNTMP_KEEPER z where z.enterorg = a.enterorg and z.keeperno = a.keeper) as keeper" +
					" ,chk.* " +
					" from UNTRT_ADDPROOF a " + 
					" LEFT JOIN UNTMP_MOVEDETAIL z ON z.enterorg = a.enterorg AND z.ownership = a.ownership AND z.differencekind = a.differencekind AND z.propertyno = a.propertyno AND z.serialno = a.serialno " +
					" LEFT JOIN UNTMP_MOVEPROOF x ON z.enterorg = x.enterorg AND z.caseno = x.caseno " +
					this.getOtherProofChk() +
					" where 1=1" +
					this.getCondition("RT") +
					" union" +
					" select" +
					" a.enterorg, a.ownership, a.differencekind, a.propertyno," +
					" a.serialno, a.oldserialno, a.propertyname1," +
					" (select z.propertyname from SYSPK_PROPERTYCODE z where z.enterorg in ('000000000A',a.enterorg) and z.propertyno = a.propertyno) as propertynoname," +
					" (select z.placename from SYSCA_PLACE z where z.enterorg = a.enterorg and z.placeno = a.place1) as place1," +
					" (select z.unitname from UNTMP_KEEPUNIT z where z.enterorg = a.enterorg and z.unitno = a.keepunit) as keepunit," +
					" (select z.keepername from UNTMP_KEEPER z where z.enterorg = a.enterorg and z.keeperno = a.keeper) as keeper" +
					" ,chk.* " +
					" from UNTVP_ADDPROOF a " +
					" LEFT JOIN UNTMP_MOVEDETAIL z ON z.enterorg = a.enterorg AND z.ownership = a.ownership AND z.differencekind = a.differencekind AND z.propertyno = a.propertyno AND z.serialno = a.serialno " +
					" LEFT JOIN UNTMP_MOVEPROOF x ON z.enterorg = x.enterorg AND z.caseno = x.caseno " +
					this.getOtherProofChk() +
					" where 1=1" +
					this.getCondition("VP") +
					") a";
		
		sql += " order by a.enterorg, a.ownership, a.propertyno, a.serialno ";

		int count = 0;
		ResultSet rs = db.querySQL_NoChange(sql);
		while(rs.next()){
			String rowArray[]=new String[12];
			rowArray[0] = checkGet(rs.getString("enterOrg"));
			rowArray[1] = checkGet(rs.getString("ownership"));
			rowArray[2] = checkGet(rs.getString("differenceKind"));
			rowArray[3] = checkGet(rs.getString("propertyNo"));
			rowArray[4] = checkGet(rs.getString("serialNo"));
			rowArray[5] = checkGet(rs.getString("oldserialNo")); 
			rowArray[6] = checkGet(rs.getString("propertyNoName"));
			rowArray[7] = checkGet(rs.getString("propertyName1"));
			rowArray[8] = checkGet(rs.getString("keepUnit"));
			rowArray[9] = checkGet(rs.getString("keeper"));
			rowArray[10] = checkGet(rs.getString("place1"));
			
			String chkproofdesc = Common.concatStrs("字第", Common.concatStrs("年", rs.getString("chkproofyear"), rs.getString("chkproofdoc")), rs.getString("chkproofno"));
			if (!"".equals(chkproofdesc)) {
				rowArray[11] = checkGet(chkproofdesc + "號");
			} else {
				rowArray[11] = "";
			}
			
			objList.add(rowArray);
			count++;
		}
		setStateQueryAllSuccess();
	} catch (Exception e) {
		e.printStackTrace();
	} finally {
		db.closeAll();
	}
	return objList;
}


	private String getOtherProofChk() {
		StringBuilder sql = new StringBuilder();
		sql.append(" OUTER APPLY ( ")
		   .append("  select top 1 x.* from ( ")
		   .append("   select p.verify as chkverify ,isnull(p.proofyear, '') as chkproofyear ,isnull(p.proofdoc, '') as chkproofdoc ,isnull(p.proofno, '') as chkproofno ")
		   .append("   from UNTMP_MOVEDETAIL d ")
		   .append("   left join UNTMP_MOVEPROOF p on d.enterorg=p.enterorg and d.caseno=p.caseno")
		   .append("   where d.enterorg = a.enterorg and d.ownership = a.ownership and d.differencekind = a.differencekind and d.propertyno = a.propertyno and d.serialno = a.serialno and d.verify!='Y' ")
		   .append("  ) as x ")
		   .append(" ) as chk")
		   .append("");
		return sql.toString();
		
	}

	private String getCondition(String table){
		String result = "";
		String queryTable = "";
		if("RT".equals(table) || "VP".equals(table)){	queryTable = "PROOF";
		}else{											queryTable = "DETAIL";
		}
		result += " and a.enterorg = " + Common.sqlChar(this.getEnterOrg()) +
					" and a.ownership = " + Common.sqlChar(this.getOwnership());
		
		if (!"".equals(getQ_propertyNoS()))
			result += " and a.propertyno >= " + Common.sqlChar(getQ_propertyNoS());		
		if (!"".equals(getQ_propertyNoE()))
			result += " and a.propertyno <= " + Common.sqlChar(getQ_propertyNoE());
		
		if (!"".equals(getQ_serialNoS())){
			result += " and a.serialno >= " + Common.sqlChar(getQ_serialNoS()) ;
		}
		if (!"".equals(getQ_serialNoE())){
			result += " and a.serialno <= " + Common.sqlChar(getQ_serialNoE()) ;
		}	
		if (!"".equals(getQ_propertyKind())){
			if("MP".equals(table)){
				result += " and b.propertykind = " + Common.sqlChar(getQ_propertyKind()) ;
			} else {
				result += " and a.propertykind = " + Common.sqlChar(getQ_propertyKind()) ;
			}
		}
		if (!"".equals(getQ_fundType())){
			if("MP".equals(table)){
				result+=" and b.fundtype = " + Common.sqlChar(getQ_fundType()) ;
			}else{
				result+=" and a.fundtype = " + Common.sqlChar(getQ_fundType()) ;
			}
		}
		
		if (!"".equals(getQ_valuable())){
			if("MP".equals(table)){
				result+=" and b.valuable = " + Common.sqlChar(getQ_valuable()) ;
			}else{
				if(!"RT".equals(table) && !"VP".equals(table)){
					result+=" and a.valuable = " + Common.sqlChar(getQ_valuable()) ;
				}
			}
		}
		
		if (!"".equals(getQ_keepUnit()))
			result += " and a.keepunit = " + Common.sqlChar(getQ_keepUnit()) ;	    
		if (!"".equals(getQ_keeper()))
			result += " and a.keeper = " + Common.sqlChar(getQ_keeper()) ;	    
		if (!"".equals(getQ_useUnit()))
			result += " and a.useunit = " + Common.sqlChar(getQ_useUnit()) ;	    
		if (!"".equals(getQ_userNo()))
			result += " and a.userno = " + Common.sqlChar(getQ_userNo()) ;
		
		if (!"".equals(getQ_userNote()))
			result += " and a.usernote like " + Common.sqlChar("%"+getQ_userNote()+"%") ;
		if (!"".equals(getQ_place1()))
			result += " and a.place1 = " + Common.sqlChar(getQ_place1()) ;
		if (!"".equals(getQ_place()))
			result += " and a.place like " + Common.sqlChar("%"+getQ_place()+"%") ;
		if (!"".equals(getQ_differenceKind()))
			result += " and a.differencekind = " + Common.sqlChar(getQ_differenceKind()) ;
		if (!"".equals(getQ_engineeringNo()))
			result += " and a.engineeringno = " + Common.sqlChar(getQ_engineeringNo()) ;
//		為了看到別的保管人財產，先註解起來
//		if (getGroupID().startsWith("keeper"))
//			result += " and a.keeper = " + Common.sqlChar(getUserID()) ;
		
		if (!"".equals(this.getQ_propertyName1())) {
			if ("MP".equals(table)) {
				result += " and b.propertyname1 like '%" + this.getQ_propertyName1() + "%'";
			} else {
				result += " and a.propertyname1 like '%" + this.getQ_propertyName1() + "%'";
			}
		}
		String q_signLaNo = "";
		
		if (!"".equals(Common.get(getQ_laSignNo1()))){
			q_signLaNo = getQ_laSignNo1().substring(0,1)+"______";
		}
		if (!"".equals(getQ_laSignNo2())){
			q_signLaNo = getQ_laSignNo2().substring(0,3)+"____";			
		}
		if (!"".equals(getQ_laSignNo3())){
			q_signLaNo = getQ_laSignNo3();
		}
		
			
		String q_signBuNo = "";
		if (!"".equals(getQ_buSignNo1())){
			q_signBuNo = getQ_buSignNo1().substring(0,1)+"______";
		}
		if (!"".equals(getQ_buSignNo2())){
			q_signBuNo = getQ_buSignNo2().substring(0,3)+"____";
		}
		if (!"".equals(getQ_buSignNo3())){
			q_signBuNo = getQ_buSignNo3();
		}
			
		if (!"".equals(getQ_proofYear())){
			if ("RT".equals(table) || "VP".equals(table)){
				result += " and a.proofyear = " +Common.sqlChar(getQ_proofYear())+" ";
			}else{
				result += " and c.proofyear = " +Common.sqlChar(getQ_proofYear())+" ";
			}
		}
		if (!"".equals(getQ_proofDoc())){
			if ("RT".equals(table) || "VP".equals(table)){
				result += " and a.proofdoc like '%" +getQ_proofDoc()+"%' ";
			}else{
				result += " and c.proofdoc like '%" +getQ_proofDoc()+"%' ";
			}
		}
		if (!"".equals(getQ_proofNoS())){
			if ("RT".equals(table) || "VP".equals(table)){
				result += " and a.proofno >= " +Common.sqlChar(getQ_proofNoS())+" ";
			}else{
				result += " and c.proofno >= " +Common.sqlChar(getQ_proofNoS())+" ";
			}
		}
		if (!"".equals(getQ_proofNoE())){ 
			if ("RT".equals(table) || "VP".equals(table)){
				result += " and a.proofno <= " +Common.sqlChar(getQ_proofNoE())+" ";
			}else{
				result += " and c.proofno <= " +Common.sqlChar(getQ_proofNoE())+" ";
			}
		}

		if (!"".equals(getQ_moveProofYear())){
			result += " and x.proofyear = " + Common.sqlChar(getQ_moveProofYear()) + " ";
		}
		if (!"".equals(getQ_moveProofDoc())){
			result += " and x.proofdoc like '%" + getQ_moveProofDoc() + "%' ";
		}
		if (!"".equals(getQ_moveProofNoS())){
			result += " and x.proofno >= " + Common.sqlChar(getQ_moveProofNoS()) + " ";
		}
		if (!"".equals(getQ_moveProofNoE())){ 
			result += " and x.proofno <= " + Common.sqlChar(getQ_moveProofNoE()) + " ";
		}
		
		
		

//		if (!"".equals(q_signLaNo) || !"".equals(q_signBuNo)){
//			if (!"".equals(q_signLaNo)){
//				result += " and signno like '" + q_signLaNo + "%'";
//				
//				if (!"".equals(getQ_laSignNo4())){
//					result += " and substring(signno,8,8) >= '" + getQ_laSignNo4() + getQ_laSignNo5() + "'";				
//				}	
//				
//				
//			}else if (!"".equals(q_signBuNo)){
//				result += " and signno like '" + q_signBuNo + "%'";
//				
//				if (!"".equals(getQ_buSignNo4())){
//					result += " and substring(signno,8,8) >= '" + getQ_buSignNo4() + getQ_buSignNo5() + "'";				
//				}					
//			}
//			
//		}
		
		result += " and a.verify = 'Y' and a.datastate = '1'";
		return result;
	}
}
