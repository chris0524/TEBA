/*
*<br>程式目的：非消耗品保管使用異動作業－批次新增明細資料
*<br>程式代號：untne010f
*<br>程式日期：0950222
*<br>程式作者：Cherry
*<br>--------------------------------------------------------
*<br>修改作者　　修改日期　　　修改目的
*<br>--------------------------------------------------------
*/

package unt.ne;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import unt.ch.UNTCH_Tools;
import util.Common;
import util.Database;
import util.Datetime;
import util.SuperBean;

public class UNTNE010F extends SuperBean{

String enterOrg;
String enterOrgName;
String ownership;
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
String oldPropertyNo;
String oldSerialNo;

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
public String getOldPropertyNo(){ return checkGet(oldPropertyNo); }
public void setOldPropertyNo(String s){ oldPropertyNo=checkSet(s); }
public String getOldSerialNo(){ return checkGet(oldSerialNo); }
public void setOldSerialNo(String s){ oldSerialNo=checkSet(s); }

String q_enterOrg;
String q_ownership; 
String q_caseNo;
String newMoveDate;
String q_newKeepBureau;
String q_newKeepUnit;
String q_newKeeper;
String q_newUseBureau;
String q_newUseUnit;
String q_newUserNo;
String q_newPlace;
String q_verify;
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
public String getNewMoveDate(){ return checkGet(newMoveDate); }
public void setNewMoveDate(String s){ newMoveDate=checkSet(s); }

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
String q_newUserNote;

String q_keepBureau;
String q_keepUnit;
String q_keeper;
String q_useBureau;
String q_useUnit;
String q_userNo;
String q_newPlace1;
String q_newPlace1Name;
String q_userNote;
String q_place1;
String q_place1Name;
String q_place;
String q_differenceKind;
String q_propertyName1;
String q_IsFix;
String q_IsFix_newKeepUnit;
String q_IsFix_newKeeper;
String q_IsFix_newUseUnit;
String q_IsFix_newUserNo;

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
public String getQ_newUserNote() {return checkGet(q_newUserNote);}
public void setQ_newUserNote(String q_newUserNote) {this.q_newUserNote = checkSet(q_newUserNote);}
public String getQ_newPlace1() {return checkGet(q_newPlace1);}
public void setQ_newPlace1(String q_newPlace1) {this.q_newPlace1 = checkSet(q_newPlace1);}
public String getQ_newPlace1Name() {return checkGet(q_newPlace1Name);}
public void setQ_newPlace1Name(String q_newPlace1Name) {this.q_newPlace1Name = checkSet(q_newPlace1Name);}
public String getQ_userNote() {return checkGet(q_userNote);}
public void setQ_userNote(String q_userNote) {this.q_userNote = checkSet(q_userNote);}
public String getQ_place1() {return checkGet(q_place1);}
public void setQ_place1(String q_place1) {this.q_place1 = checkSet(q_place1);}
public String getQ_place1Name() {return checkGet(q_place1Name);}
public void setQ_place1Name(String q_place1Name) {this.q_place1Name = checkSet(q_place1Name);}
public String getQ_place() {return checkGet(q_place);}
public void setQ_place(String q_place) {this.q_place = checkSet(q_place);}
public String getQ_differenceKind() {return checkGet(q_differenceKind);}
public void setQ_differenceKind(String q_differenceKind) {this.q_differenceKind = checkSet(q_differenceKind);}
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


String toggleAll;
public String getToggleAll(){ if (!"".equals(checkGet(toggleAll))) return "checked"; else return "unchecked"; }    
public void setToggleAll(String s){ toggleAll=checkSet(s); }    


private int getCaseSerialNo(String enterOrg, String caseNo, String ownership) {
	String sql = "select ISNULL(max(caseSerialNo),0)+1 as caseSerialNo "+
				 " from UNTNE_MoveDetail " +
				 " where enterOrg = " + Common.sqlChar(enterOrg) + 
				 " and caseNo = " + Common.sqlChar(caseNo) +
				 " and ownership = " + Common.sqlChar(ownership);
				 // " and propertyNo = " + Common.sqlChar(propertyNo) + 
				 // " and serialNo = " + Common.sqlChar(serialNo) +
				 // " and differencekind = " + Common.sqlChar(differenceKind);
	
	Database database = new Database();
	ResultSet rs = null;
	int caseSerialNo = 1;
	try {
		rs = database.querySQL(sql);
		if (rs.next()){
			caseSerialNo = rs.getInt("caseSerialNo");
		}
	} catch (Exception e) {
		e.printStackTrace();
	} finally {
		if (rs != null) {
			try {
				rs.close();
			} catch (Exception e) {
				//ignore
			}
		}
		
		database.closeAll();
	}
	return caseSerialNo;
	
}

/**
 * <br>
 * <br>目的：傳回insert sql
 * <br>參數：無 
 * <br>傳回：無 ; 直接更改變數為新增成功與否
*/	
protected String[] getInsertSQL(){
	UNTCH_Tools ul = new UNTCH_Tools();
	StringBuffer sbSQL = new StringBuffer("");
	Database db = new Database();  
	int getcut=0;
	if(getsKeySet()!=null)
		getcut = getsKeySet().length;	//有勾選的list中資料數
	//String sSQL = "";
	//String[] execSQLArray = new String[getcut];
	String[] strKeys = null;
	Database db1 = new Database();
	ResultSet rs = null;
	int i = 0;	
	//int counter=0;
	Map<String, Integer> caseSerialNoMap = new HashMap<String, Integer>();
	try {
		for (i=0; i<getcut; i++) {	
		strKeys = getsKeySet()[i].split(",");
		String differenceKind = strKeys[2];
		newMoveDate=Datetime.getYYYYMMDD();
			//先找出 nonexp 和 nonexpdetail 的值
		    String nonexpsql = " select (case '" + Common.get(q_newPlace) + "' when '' then b.place else '" + Common.get(q_newPlace) + "' end) as newPlace, b.propertyNo , b.lotNo , b.serialNo ,  "+
							    " a.otherMaterial, a.otherPropertyUnit, a.otherLimitYear , b.propertyName1 , a.enterDate ,  a.buyDate , " +
								" a.propertyKind , a.fundType , a.valuable , a.accountingTitle ," +
								" b.bookAmount , (case a.originalUnit when '' then a.originalUnit else b.bookValue end) as originalUnit, b.bookValue , "+
								" a.articleName , a.nameplate , a.specification , a.sourceDate , "+
								" b.licensePlate, b.moveDate , b.keepUnit , b.keeper , b.useUnit , b.userNo , b.userNote , b.place , b.place1 , "+
								" b.oldPropertyNo , b.oldSerialNo " +
								" from untne_nonexp a , untne_nonexpDetail b "+
								" where a.enterOrg = b.enterOrg "+
								" and a.ownership = b.ownership"+
								" and a.propertyno = b.propertyno " +
								" and a.lotNo=b.lotNo "+
								" and a.differenceKind=b.differenceKind "+
								" and b.enterOrg = " + Common.sqlChar(strKeys[0]) +
								" and b.ownership = " + Common.sqlChar(strKeys[1]) +
								" and b.differenceKind = " + Common.sqlChar(differenceKind) +
								" and b.propertyNo = " + Common.sqlChar(strKeys[3]) +
								" and b.serialNo = " + Common.sqlChar(strKeys[4]) +
								" " ;
		    //System.out.println("weeew==>"+nonexpsql);
			rs = db1.querySQL(nonexpsql);
			if (rs.next()){
			    //已使用月數 = (系統日期 - 建築日期) ★/12 之餘額月數
				// total month = 月份 + 年*12月    format:yyymmdd
				int sMonth = Integer.parseInt(getEditDate().substring(3,5)) + (Integer.parseInt(getEditDate().substring(0,3)) * 12 ); 
				int eMonth = Integer.parseInt(ul._transToROC_Year(rs.getString("buyDate")).substring(3,5)) + (Integer.parseInt(ul._transToROC_Year(rs.getString("buyDate")).substring(0,3)) * 12 );
				String useMonth = ""+(sMonth - eMonth) % 12 ;

			    //已使用年數 = (系統日期 - 建築日期) ★/12 之商數
			    String useYear = ""+(sMonth - eMonth) / 12  ;
			    
			    String propertyNo = rs.getString("propertyNo");
			    String serialNo = rs.getString("serialNo"); 
			    String caseSerialNoKey = enterOrg + "_" + caseNo + "_" + ownership;
			    
			    Integer caseSerialNo = caseSerialNoMap.get(caseSerialNoKey);
			    if (caseSerialNo == null) {
			    	caseSerialNo = this.getCaseSerialNo(enterOrg, caseNo, ownership);
			    }
			    caseSerialNoMap.put(caseSerialNoKey, caseSerialNo + 1);
			//insert 資料
			    sbSQL.append(" insert into UNTNE_MoveDetail(" ).append(
					" enterOrg,").append(
					" ownership,").append(
					" caseNo,").append(
					" propertyNo,").append(
					" lotNo,").append(
					" serialNo,").append(
					" propertyName1,").append(
					" otherMaterial,").append(
					" otherPropertyUnit,").append(
					" otherLimitYear,").append(
					" buyDate,").append(
					" verify,").append(
					" propertyKind,").append(
					" fundType,").append(
					" valuable,").append(
					" bookAmount,").append(
					" bookUnit,").append(
					" bookValue,").append(
					" nameplate,").append(
					" specification,").append(
					" sourceDate,").append(
					" oldMoveDate,").append(
					" oldKeepUnit,").append(
					" oldKeeper,").append(
					" oldUseUnit,").append(
					" oldUserNo,").append(
					" oldUserNote,").append(
					" oldPlace1,").append(
					" oldPlace,").append(
					" newMoveDate,").append(
					" newKeepUnit,").append(
					" newKeeper,").append(
					" newUseUnit,").append(
					" newUserNo,").append(
					" newPlace,").append(
					" useYear,").append(
					" useMonth,").append(
					" oldPropertyNo,").append(
					" oldSerialNo,").append(
					" editID,").append(
					" editDate,").append(
					" editTime,").append(
					" differenceKind,").append(
					" newUserNote,").append(
					" newPlace1,").append(
					" notes,").append(
					" caseserialno").append(
				  ") values ( ").append( 
				 	Common.sqlChar(enterOrg)						).append( "," ).append(
					Common.sqlChar(ownership)						).append( "," ).append(
					Common.sqlChar(caseNo)						).append( "," ).append(
					Common.sqlChar(propertyNo)		).append( "," ).append(				  	
					Common.sqlChar(rs.getString("lotNo"))			).append( "," ).append(
					Common.sqlChar(serialNo)		).append( "," ).append(
					Common.sqlChar(rs.getString("propertyName1"))	).append( "," ).append(
					Common.sqlChar(rs.getString("otherMaterial"))		).append( "," ).append(
					Common.sqlChar(rs.getString("otherPropertyUnit"))		).append( "," ).append(
					Common.sqlChar(rs.getString("otherLimitYear"))	).append( "," ).append(
					Common.sqlChar(rs.getString("buyDate"))			).append( "," ).append(
					Common.sqlChar(verify)						).append( "," ).append(
					Common.sqlChar(rs.getString("propertyKind"))	).append( "," ).append(
					Common.sqlChar(rs.getString("fundType"))		).append( "," ).append(
					Common.sqlChar(rs.getString("valuable"))		).append( "," ).append(
					Common.sqlChar(rs.getString("bookAmount"))	).append( "," ).append(
					Common.sqlChar(rs.getString("originalUnit"))		).append( "," ).append(
					Common.sqlChar(rs.getString("bookValue"))		).append( "," ).append(
					Common.sqlChar(rs.getString("nameplate"))		).append( "," ).append(
					Common.sqlChar(rs.getString("specification"))		).append( "," ).append(
					Common.sqlChar(rs.getString("sourceDate"))		).append( "," ).append(
					Common.sqlChar(rs.getString("moveDate"))		).append( "," ).append(
					Common.sqlChar(rs.getString("keepUnit"))		).append( "," ).append(
					Common.sqlChar(rs.getString("keeper"))		).append( "," ).append(
					Common.sqlChar(rs.getString("useUnit"))		).append( "," ).append(
					Common.sqlChar(rs.getString("userNo"))		).append( "," ).append(
					Common.sqlChar(rs.getString("userNote"))		).append( "," ).append(
					Common.sqlChar(rs.getString("place1"))		).append( "," ).append(
					Common.sqlChar(rs.getString("place"))		).append( "," ).append(
					Common.sqlChar("")		).append( "," );
				if ("Y".equals(getQ_IsFix_newKeepUnit())) {
					sbSQL.append(Common.sqlChar(q_newKeepUnit)		).append( "," );
				} else {
					sbSQL.append(Common.sqlChar(rs.getString("keepUnit"))		).append( "," );
				}
				if ("Y".equals(getQ_IsFix_newKeeper())) {
					sbSQL.append(Common.sqlChar(q_newKeeper)		).append( "," );
				} else {
					sbSQL.append(Common.sqlChar(rs.getString("keeper"))		).append( "," );
				}
				if ("Y".equals(getQ_IsFix_newUseUnit())) {
					sbSQL.append(Common.sqlChar(q_newUseUnit)		).append( "," );
				} else {
					sbSQL.append(Common.sqlChar(rs.getString("useUnit"))		).append( "," );
				}
				if ("Y".equals(getQ_IsFix_newUserNo())) {
					sbSQL.append(Common.sqlChar(q_newUserNo)		).append( "," );
				} else {
					sbSQL.append(Common.sqlChar(rs.getString("userNo"))		).append( "," );
				}
				if ("Y".equals(getQ_IsFix())) {
					sbSQL.append(Common.sqlChar(Common.get(rs.getString("newPlace")))		).append( "," );
				} else {
					sbSQL.append(Common.sqlChar(Common.get(rs.getString("place")))		).append( "," );
				}
					sbSQL.append(Common.sqlChar(useYear)		).append( "," ).append(
					Common.sqlChar(useMonth)		).append( "," ).append(
					Common.sqlChar(rs.getString("oldPropertyNo"))		).append( "," ).append(
					Common.sqlChar(rs.getString("oldSerialNo"))		).append( "," ).append(
					Common.sqlChar(getEditID())                   	).append( "," ).append(
					Common.sqlChar(ul._transToCE_Year(getEditDate()))                 	).append( "," ).append(
					Common.sqlChar(getEditTime())                 	).append("," ).append(
					Common.sqlChar(differenceKind)                 	).append("," );
					if ("Y".equals(getQ_IsFix())) {
						sbSQL.append(Common.sqlChar(q_newUserNote)		).append( "," ).append(		
						Common.sqlChar(q_newPlace1)		).append( "," );
					} else {
						sbSQL.append(Common.sqlChar(rs.getString("userNote"))		).append( "," ).append(		
						Common.sqlChar(rs.getString("place1"))		).append( "," );
					}
					sbSQL.append(Common.sqlChar("")	).append(",").append(
					Common.sqlChar(Common.formatFrontZero(String.valueOf(caseSerialNo),5))).append(
					" ):;:");	
			}
		}
			//setStateQueryOneSuccess();
	} catch (Exception e) {
		e.printStackTrace();
	} finally {
		db.closeAll();
		
		if (caseSerialNoMap != null) {
			caseSerialNoMap.clear();
			caseSerialNoMap = null;
		}
	}
	//execSQLArray[i] = sSQL;
	setStateInsertSuccess();
//	System.out.println(sbSQL.toString());
	return sbSQL.toString().split(":;:");
}

/**
 * <br>
 * <br>目的：依主鍵查詢單一資料
 * <br>參數：無
 * <br>傳回：傳回本物件
*/

public UNTNE010F  queryOne() throws Exception{
	Database db = new Database();
	UNTNE010F obj = this;
	try {
		String sql= " select a.enterOrg, c.organSName as enterOrgName, a.ownership, " +
					" a.propertyNo, a.lotNo, a.serialNo, d.propertyName, " +
					" d.propertyUnit, b.otherPropertyUnit, d.material, b.otherMaterial, d.limitYear, b.otherLimitYear, " +
					" b.propertyName1, b.enterDate, b.buyDate, b.propertyKind, "+
					" b.fundType, b.valuable, b.accountingTitle, a.bookAmount, b.originalUnit, a.bookValue, b.articleName, b.nameplate, b.specification, b.sourceDate, " +
					" a.licensePlate,a.moveDate, a.keepUnit, a.keeper, a.useUnit, a.userNo, a.place, " +
					" (select f.unitName from UNTMP_KeepUnit f where a.enterOrg=f.enterOrg and a.keepUnit=f.unitNo) as keepUnitName, "+
					" (select f.unitName from UNTMP_KeepUnit f where a.enterOrg=f.enterOrg and a.useUnit=f.unitNo) as useUnitName, "+
					" (select f.keeperName from UNTMP_Keeper f where a.enterOrg=f.enterOrg and a.keepUnit=f.unitNo and a.keeper=f.keeperNo) as keeperName, "+		
					" (select f.keeperName from UNTMP_Keeper f where a.enterOrg=f.enterOrg and a.useUnit=f.unitNo and a.userNo=f.keeperNo) as userName, " +
					"  a.oldPropertyNo, a.oldSerialNo "+		
					" from UNTNE_NonexpDetail a, UNTNE_Nonexp b, SYSCA_ORGAN c, "+
					" SYSPK_PropertyCode2 d " +
					" where 1=1 " +
					" and a.enterOrg=b.enterOrg and a.ownership=b.ownership and a.propertyNo=b.propertyNo and a.lotNo=b.lotNo "+
					" and a.enterOrg 	= " + Common.sqlChar(enterOrg) +
					" and a.ownership 	= " + Common.sqlChar(ownership) +
					" and b.caseNo 		= " + Common.sqlChar(Common.formatFrontZero(caseNo,10)) +
					" and a.propertyNo 	= " + Common.sqlChar(propertyNo) +
					" and a.serialNo 	= " + Common.sqlChar(serialNo) +
					" and a.enterOrg	= c.organID " +
					" and a.enterOrg = d.enterOrg " +
					" and a.propertyNo 	= d.propertyNo "+
					" " ;
		ResultSet rs = db.querySQL(sql);
		if (rs.next()){
		    obj.setEnterOrg(rs.getString("enterOrg"));
		    obj.setEnterOrgName(rs.getString("enterOrgName"));
		    obj.setOwnership(rs.getString("ownership"));
			obj.setPropertyNo(rs.getString("propertyNo"));
			obj.setPropertyName(rs.getString("propertyName"));
			obj.setLotNo(rs.getString("lotNo"));
			obj.setSerialNo(rs.getString("serialNo"));
			obj.setMaterial(rs.getString("material"));
			obj.setOtherMaterial(rs.getString("otherMaterial"));
			obj.setPropertyUnit(rs.getString("propertyUnit"));
			obj.setOtherPropertyUnit(rs.getString("otherPropertyUnit"));
			obj.setLimitYear(rs.getString("limitYear"));
			obj.setOtherLimitYear(rs.getString("otherLimitYear"));
			obj.setPropertyName1(rs.getString("propertyName1"));
			obj.setBuyDate(rs.getString("buyDate"));
			obj.setPropertyKind(rs.getString("propertyKind"));
			obj.setFundType(rs.getString("fundType"));
			obj.setValuable(rs.getString("valuable"));
			obj.setBookAmount(rs.getString("bookAmount"));
			obj.setBookValue(rs.getString("bookValue"));
			obj.setNameplate(rs.getString("nameplate"));
			obj.setSpecification(rs.getString("specification"));
			obj.setSourceDate(rs.getString("sourceDate"));
			obj.setMoveDate(rs.getString("moveDate"));
			obj.setKeepUnit(rs.getString("keepUnit"));
			obj.setKeepUnitName(rs.getString("keepUnitName"));
			obj.setKeeper(rs.getString("keeper"));
			obj.setKeeperName(rs.getString("keeperName"));
			obj.setUseUnit(rs.getString("useUnit"));
			obj.setUseUnitName(rs.getString("useUnitName"));
			obj.setUserNo(rs.getString("userNo"));
			obj.setUserName(rs.getString("userName"));
			obj.setPlace(rs.getString("place"));
			obj.setOldPropertyNo(rs.getString("oldPropertyNo"));
			obj.setOldSerialNo(rs.getString("oldSerialNo"));
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
		String sql= "select distinct * from (" + 
				" select a.enterorg,a.differencekind, " +
				" a.ownership, " +
				" a.propertyno, " +
				" a.serialno," +
				" a.oldserialno," +
				" (select distinct propertyname from SYSPK_PROPERTYCODE2 z where z.enterorg in ('000000000A',a.enterorg) and z.propertyno = a.propertyno) as propertynoname," +		
				" a.propertyname1," +
				" (select f.unitname from UNTMP_KEEPUNIT f where a.enterorg=f.enterorg and a.keepunit=f.unitno) as keepunitname, "+
				" (select f.keepername from UNTMP_KEEPER f where a.enterorg=f.enterorg and a.keeper=f.keeperno) as keepername, "+
				" (select placename from SYSCA_PLACE z where z.enterorg = a.enterorg and z.placeno = a.place1) as place1 " +
				" ,chk.* " + 
				" from UNTNE_NONEXPDETAIL a " +
				" left join UNTNE_NONEXP b on a.enterorg = b.enterorg and a.ownership = b.ownership and a.caseno = b.caseno and a.differencekind = b.differencekind and a.propertyno = b.propertyno and a.serialno between b.serialnos and b.serialnoe " +
				" left join SYSPK_PROPERTYCODE2 d on a.enterorg = d.enterorg and a.propertyno = d.propertyno " +
				" left join UNTNE_ADDPROOF c on a.enterorg=c.enterorg and a.ownership=c.ownership and a.caseno=c.caseno and a.differencekind=c.differencekind "+
				" LEFT JOIN UNTNE_MOVEDETAIL z ON z.enterorg = a.enterorg AND z.ownership = a.ownership AND z.differencekind = a.differencekind AND z.propertyno = a.propertyno AND z.serialno = a.serialno " +
				" LEFT JOIN UNTNE_MOVEPROOF x ON z.enterorg = x.enterorg AND z.caseno = x.caseno " +
				" OUTER APPLY ( " + 
				" select top 1 x.* from ( " +
				"  select p.verify as chkverify ,isnull(p.proofyear, '') as chkproofyear ,isnull(p.proofdoc, '') as chkproofdoc ,isnull(p.proofno, '') as chkproofno " + 
				"  from UNTNE_MOVEDETAIL d" + 
				"  left join UNTNE_MOVEPROOF p on d.enterorg=p.enterorg and d.caseno=p.caseno " +
				"  where d.enterorg = a.enterorg and d.ownership = a.ownership and d.differencekind = a.differencekind and d.propertyno = a.propertyno and d.serialno = a.serialno and d.verify!='Y' " + 
				"  ) as x" + 
				" ) as chk " + 
				" where 1=1  " +
				" and a.enterorg 	= " + Common.sqlChar(this.getEnterOrg()) +
				" and a.ownership 	= " + Common.sqlChar(this.getOwnership()) +
				" and a.datastate='1' and a.verify='Y'" +
				"" ;
		if (!"".equals(getQ_propertyNoS()))
			sql += " and a.propertyno >= " + Common.sqlChar(getQ_propertyNoS());		
		if (!"".equals(getQ_propertyNoE()))
			sql += " and a.propertyno <= " + Common.sqlChar(getQ_propertyNoE());
		
		if (!"".equals(getQ_serialNoS())){
			sql += " and a.serialno >= " + Common.sqlChar(getQ_serialNoS()) ;
		}
		if (!"".equals(getQ_serialNoE())){
			sql += " and a.serialno <= " + Common.sqlChar(getQ_serialNoE()) ;
		}	
		if (!"".equals(getQ_propertyKind()))
			sql += " and b.propertykind = " + Common.sqlChar(getQ_propertyKind()) ;
		if (!"".equals(getQ_fundType()))
			sql += " and b.fundtype = " + Common.sqlChar(getQ_fundType()) ;
		if (!"".equals(getQ_valuable()))
			sql += " and b.valuable = " + Common.sqlChar(getQ_valuable()) ;
		
		if (!"".equals(getQ_propertyName1()))
			sql += " and b.propertyname1 like " + Common.sqlChar("%"+getQ_propertyName1()+"%") ;
		
		if (!"".equals(getQ_keepUnit()))
			sql += " and a.keepunit = " + Common.sqlChar(getQ_keepUnit()) ;	    
		if (!"".equals(getQ_keeper()))
			sql += " and a.keeper = " + Common.sqlChar(getQ_keeper()) ;	    
		if (!"".equals(getQ_useUnit()))
			sql += " and a.useunit = " + Common.sqlChar(getQ_useUnit()) ;	    
		if (!"".equals(getQ_userNo()))
			sql += " and a.userno = " + Common.sqlChar(getQ_userNo()) ;
		
		if (!"".equals(getQ_proofYear())){
			sql += " and c.proofyear = " +Common.sqlChar(getQ_proofYear())+" ";
		}
		if (!"".equals(getQ_proofDoc())){
			sql += " and c.proofdoc like '%" +getQ_proofDoc()+"%' ";
		}
		if (!"".equals(getQ_proofNoS())){
			sql += " and c.proofno >= " +Common.sqlChar(getQ_proofNoS())+" ";
		}
		if (!"".equals(getQ_proofNoE())){ 
			sql += " and c.proofno <= " +Common.sqlChar(getQ_proofNoE())+" ";
		}
		
		if (!"".equals(getQ_moveProofYear())){
			sql += " and x.proofyear = " + Common.sqlChar(getQ_moveProofYear()) + " ";
		}
		if (!"".equals(getQ_moveProofDoc())){
			sql += " and x.proofdoc like '%" + getQ_moveProofDoc() + "%' ";
		}
		if (!"".equals(getQ_moveProofNoS())){
			sql += " and x.proofno >= " + Common.sqlChar(getQ_moveProofNoS()) + " ";
		}
		if (!"".equals(getQ_moveProofNoE())){ 
			sql += " and x.proofno <= " + Common.sqlChar(getQ_moveProofNoE()) + " ";
		}
		
		
		if (!"".equals(getQ_userNote()))
			sql += " and a.usernote like " + Common.sqlChar("%"+getQ_userNote()+"%") ;
		if (!"".equals(getQ_place1()))
			sql += " and a.place1 = " + Common.sqlChar(getQ_place1()) ;
		if (!"".equals(getQ_place()))
			sql += " and a.place like " + Common.sqlChar("%"+getQ_place()+"%") ;
		if (!"".equals(getQ_differenceKind()))
			sql += " and a.differencekind = " + Common.sqlChar(getQ_differenceKind()) ;
		sql += ") a";
		sql += " order by a.enterorg, a.ownership, a.propertyno, a.serialno ";			
		
//System.out.println("=> "+sql);		
		
		ResultSet rs = db.querySQL_NoChange(sql);
		//把查到的資料放入list裡..
		//和.jsp裡的 List區 內容要搭配起來
		while (rs.next()){
			counter++;
			String rowArray[]=new String[12];
			rowArray[0]=checkGet(rs.getString("enterOrg"));
			rowArray[1]=checkGet(rs.getString("ownership"));
			rowArray[2]=checkGet(rs.getString("differenceKind"));
			rowArray[3]=checkGet(rs.getString("propertyNo")); 
			rowArray[4]=checkGet(rs.getString("serialNo")); 
			rowArray[5]=checkGet(rs.getString("oldserialNo")); 
			rowArray[6]=checkGet(rs.getString("propertynoname"));
			rowArray[7]=checkGet(rs.getString("propertyName1"));
			rowArray[8]=checkGet(rs.getString("keepUnitName"));
			rowArray[9]=checkGet(rs.getString("keeperName"));
			rowArray[10]=checkGet(rs.getString("place1"));
			
			String chkproofdesc = Common.concatStrs("字第", Common.concatStrs("年", rs.getString("chkproofyear"), rs.getString("chkproofdoc")), rs.getString("chkproofno"));
			if (!"".equals(chkproofdesc)) {
				rowArray[11] = checkGet(chkproofdesc + "號");
			} else {
				rowArray[11] = "";
			}
			
			objList.add(rowArray);
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
