/*
*<br>程式目的：動產保管使用異動作業－批次新增明細資料
*<br>程式代號：untmp011f
*<br>程式日期：0950220
*<br>程式作者：Cherry
*<br>--------------------------------------------------------
*<br>修改作者　　修改日期　　　修改目的
*<br>--------------------------------------------------------
*/

package unt.mp;

import java.sql.ResultSet;
import java.util.ArrayList;
import util.*;

public class UNTMP011F extends SuperBean{

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
String scrapValue;
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
public String getScrapValue(){ return checkGet(scrapValue); }
public void setScrapValue(String s){ scrapValue=checkSet(s); }
public String getOldPropertyNo(){ return checkGet(oldPropertyNo); }
public void setOldPropertyNo(String s){ oldPropertyNo=checkSet(s); }
public String getOldSerialNo(){ return checkGet(oldSerialNo); }
public void setOldSerialNo(String s){ oldSerialNo=checkSet(s); }

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

/**
 * <br>
 * <br>目的：傳回insert sql
 * <br>參數：無 
 * <br>傳回：無 ; 直接更改變數為新增成功與否
*/	
protected String[] getInsertSQL(){
	StringBuffer sbSQL = new StringBuffer("");
	Database db = new Database();  
	int getcut=0;
	if(getsKeySet()!=null)
		getcut = getsKeySet().length;	//有勾選的list中資料數
	//String sSQL = "";
	//String[] execSQLArray = new String[getcut];
	String[] strKeys = null;
	ResultSet rs = null;
	int i = 0;	
	//int counter=0;
	try {
		for (i=0; i<getcut; i++) {	
			strKeys = getsKeySet()[i].split(",");
			//先找出 movable 和 movabledetail 的值
		    String movablesql = " select decode('" + Common.get(q_newPlace) + "','',b.place,'" + Common.get(q_newPlace) + "') as newPlace, b.propertyNo , b.lotNo , b.serialNo ,  "+
							    " a.otherMaterial, a.otherPropertyUnit, a.otherLimitYear , a.propertyName1 , a.enterDate ,  a.buyDate , " +
								" a.propertyKind , a.fundType , a.valuable , a.accountingTitle ," +
								" b.bookAmount , decode(a.originalUnit,'',a.originalUnit,b.bookValue) as originalUnit, b.bookValue , "+
								" a.articleName , a.nameplate , a.specification , a.sourceDate , "+
								" b.licensePlate, b.moveDate , b.keepUnit , b.keeper , b.useUnit , b.userNo , b.place , "+
								" a.deprMethod , b.scrapValue , b.deprAmount , b.apportionYear , b.monthDepr , "+
								" a.useEndYM , a.apportionEndYM , "+
								" b.accumDeprYM , b.accumDepr ,  "+
								" a.permitReduceDate , b.oldPropertyNo , b.oldSerialNo " +
								" from untmp_movable a , untmp_movableDetail b "+
								" where a.enterOrg = b.enterOrg "+
								" and a.ownership = b.ownership"+
								" and a.propertyno = b.propertyno " +
								" and a.lotNo=b.lotNo "+
								" and b.enterOrg = " + Common.sqlChar(strKeys[0]) +
								" and b.ownership = " + Common.sqlChar(strKeys[1]) +
								" and b.propertyNo = " + Common.sqlChar(strKeys[3]) +
								" and b.serialNo = " + Common.sqlChar(strKeys[4]) +
								" " ;
		    //System.out.println("movableSQl: "+movablesql);
			rs = db.querySQL(movablesql);
			if (rs.next()){
			    //已使用月數 = (系統日期 - 建築日期) ★/12 之餘額月數
				// total month = 月份 + 年*12月    format:yyymmdd
				int sMonth = Integer.parseInt(getEditDate().substring(3,5)) + (Integer.parseInt(getEditDate().substring(0,3)) * 12 ); 
				int eMonth = Integer.parseInt(rs.getString("buyDate").substring(3,5)) + (Integer.parseInt(rs.getString("buyDate").substring(0,3)) * 12 );
				String useMonth = ""+(sMonth - eMonth) % 12 ;

			    //已使用年數 = (系統日期 - 建築日期) ★/12 之商數
			    String useYear = ""+(sMonth - eMonth) / 12  ;
			    
			//insert 資料
			    sbSQL.append(" insert into UNTMP_MoveDetail(" ).append(
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
					" oldPlace,").append(
					" newMoveDate,").append(
					" newKeepUnit,").append(
					" newKeeper,").append(
					" newUseUnit,").append(
					" newUserNo,").append(
					" newPlace,").append(
					" useYear,").append(
					" useMonth,").append(
					" scrapValue,").append(
					" oldPropertyNo,").append(
					" oldSerialNo,").append(
					" closing,").append(
					" editID,").append(
					" editDate,").append(
					" editTime").append(
				  ") values ( ").append( 
				 	Common.sqlChar(q_enterOrg)						).append( "," ).append(
					Common.sqlChar(q_ownership)						).append( "," ).append(
					Common.sqlChar(q_caseNo)						).append( "," ).append(
					Common.sqlChar(rs.getString("propertyNo"))		).append( "," ).append(				  	
					Common.sqlChar(rs.getString("lotNo"))			).append( "," ).append(
					Common.sqlChar(rs.getString("serialNo"))		).append( "," ).append(
					Common.sqlChar(rs.getString("propertyName1"))	).append( "," ).append(
					Common.sqlChar(rs.getString("otherMaterial"))		).append( "," ).append(
					Common.sqlChar(rs.getString("otherPropertyUnit"))		).append( "," ).append(
					Common.sqlChar(rs.getString("otherLimitYear"))	).append( "," ).append(
					Common.sqlChar(rs.getString("buyDate"))			).append( "," ).append(
					Common.sqlChar(q_verify)						).append( "," ).append(
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
					Common.sqlChar(rs.getString("place"))		).append( "," ).append(
					Common.sqlChar(q_newMoveDate)		).append( "," ).append(
					Common.sqlChar(q_newKeepUnit)		).append( "," ).append(
					Common.sqlChar(q_newKeeper)		).append( "," ).append(
					Common.sqlChar(q_newUseUnit)		).append( "," ).append(
					Common.sqlChar(q_newUserNo)		).append( "," ).append(
					Common.sqlChar(Common.get(rs.getString("newPlace")))		).append( "," ).append(
					Common.sqlChar(useYear)		).append( "," ).append(
					Common.sqlChar(useMonth)		).append( "," ).append(
					Common.sqlChar(rs.getString("scrapValue"))		).append( "," ).append(
					Common.sqlChar(rs.getString("oldPropertyNo"))		).append( "," ).append(
					Common.sqlChar(rs.getString("oldSerialNo"))		).append( "," ).append(
					Common.sqlChar("N")                   	).append( "," ).append(
					Common.sqlChar(getEditID())                   	).append( "," ).append(
					Common.sqlChar(getEditDate())                 	).append( "," ).append(
					Common.sqlChar(getEditTime())                 	).append(
					" ):;:");
			}
			//System.out.println("sql: "+sbSQL.toString());
		}
		//setStateQueryOneSuccess();
	} catch (Exception e) {
		e.printStackTrace();
	} finally {
		db.closeAll();
	}
	//execSQLArray[i] = sSQL;
	setStateInsertSuccess();
	return sbSQL.toString().split(":;:");
}

/**
 * <br>
 * <br>目的：依主鍵查詢單一資料
 * <br>參數：無
 * <br>傳回：傳回本物件
*/

public UNTMP011F  queryOne() throws Exception{
	Database db = new Database();
	UNTMP011F obj = this;
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
					" b.deprMethod, a.scrapValue, a.deprAmount, a.apportionYear, a.monthDepr, b.useEndYM, b.apportionEndYM, " +
					" a.accumDeprYM, a.accumDepr, b.permitReduceDate, a.oldPropertyNo, a.oldSerialNo "+		
					" from UNTMP_MovableDetail a, UNTMP_Movable b, SYSCA_ORGAN c, "+
					" SYSPK_PropertyCode d " +
					" where 1=1 " +
					" and a.enterOrg=b.enterOrg and a.ownership=b.ownership and a.propertyNo=b.propertyNo and a.lotNo=b.lotNo "+
					" and a.enterOrg 	= " + Common.sqlChar(enterOrg) +
					" and a.ownership 	= " + Common.sqlChar(ownership) +
					" and b.caseNo 		= " + Common.sqlChar(Common.formatFrontZero(caseNo,10)) +
					" and a.propertyNo 	= " + Common.sqlChar(propertyNo) +
					" and a.serialNo 	= " + Common.sqlChar(serialNo) +
					" and a.enterOrg	= c.organID " +
					" and a.propertyNo 	= d.propertyNo "+
					" and d.enterOrg in('000000000A',a.enterOrg) and d.propertyType='1'" +
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
			obj.setScrapValue(rs.getString("scrapValue"));
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
		String sql=" select distinct a.enterOrg, " +
				" a.ownership, " +
				" b.caseNo, " +
				" a.propertyNo, " +
				" a.serialNo," +
				" d.propertyName," 	+			
				" decode(b.propertyKind,'01','公務用','02','公共用','03','事業用','04','非公用','') propertyKind,"+
				" a.keepUnit , (select f.unitName from UNTMP_KeepUnit f where a.enterOrg=f.enterOrg and a.keepUnit=f.unitNo) as keepUnitName, "+
				" a.keeper, (select f.keeperName from UNTMP_Keeper f where a.enterOrg=f.enterOrg and a.keepUnit=f.unitNo and a.keeper=f.keeperNo) as keeperName, "+
				" nvl(a.place,' ') as place"+
				" from UNTMP_MovableDetail a, UNTMP_Movable b,SYSPK_PropertyCode d " +
				" where 1=1 and a.dataState='1' and a.verify='Y' " +
				" and a.enterOrg=b.enterOrg and a.ownership=b.ownership and a.propertyNo=b.propertyNo and a.lotNo=b.lotNo " +
				" and a.propertyNo = d.propertyNo "+
				" and d.enterOrg in('000000000A',a.enterOrg) and d.propertyType='1'" +
				" and a.enterOrg 	= " + Common.sqlChar(q_enterOrg) +
				" and a.ownership 	= " + Common.sqlChar(q_ownership) +
				" and (a.enterOrg , a.ownership , a.propertyNo , a.serialNo)  not in ( " +
				"  select enterOrg , ownership , propertyNo , serialNo from UNTMP_MoveDetail  where verify='N' " +
				"  ) " +
				" and (a.enterOrg , a.ownership , a.propertyNo , a.serialNo)  not in ( " +
				"  select enterOrg , ownership , propertyNo , serialNo from UNTMP_reduceDetail where verify='N' " +
				"  ) " +
				" and (a.enterOrg , a.ownership , a.propertyNo , a.serialNo)  not in ( " +
				"  select enterOrg , ownership , propertyNo , serialNo from UNTMP_adjustDetail where verify='N' " +
				"  ) " +
				"" ;
			if (!"".equals(getQ_propertyNoS()))
				sql+=" and a.propertyNo >= " + Common.sqlChar(getQ_propertyNoS());		
			if (!"".equals(getQ_propertyNoE()))
				sql+=" and a.propertyNo<=" + Common.sqlChar(getQ_propertyNoE());						
			if (!"".equals(getQ_serialNoS())){
				setQ_serialNoS(Common.formatFrontZero(getQ_serialNoS(),7));
				sql+=" and a.serialNo >= " + Common.sqlChar(getQ_serialNoS()) ;
			}
			if (!"".equals(getQ_serialNoE())){
				setQ_serialNoE(Common.formatFrontZero(getQ_serialNoE(),7));
				sql+=" and a.serialNo <= " + Common.sqlChar(getQ_serialNoE()) ;
			}	
			if (!"".equals(getQ_keepUnit()))
				sql+=" and a.keepUnit = " + Common.sqlChar(getQ_keepUnit()) ;	    
			if (!"".equals(getQ_keeper()))
				sql+=" and a.keeper = " + Common.sqlChar(getQ_keeper()) ;	    
			if (!"".equals(getQ_useUnit()))
				sql+=" and a.useUnit = " + Common.sqlChar(getQ_useUnit()) ;	    
			if (!"".equals(getQ_userNo()))
				sql+=" and a.userNo = " + Common.sqlChar(getQ_userNo()) ;
			if (!"".equals(getQ_propertyKind()))
				sql+=" and b.propertyKind = " + Common.sqlChar(getQ_propertyKind()) ;
			if (!"".equals(getQ_fundType()))
				sql+=" and b.fundType = " + Common.sqlChar(getQ_fundType()) ;
			if (!"".equals(getQ_valuable()))
				sql+=" and b.valuable = " + Common.sqlChar(getQ_valuable()) ;
		sql += "order by a.enterOrg, a.ownership, a.propertyNo, a.serialNo ";
		
		ResultSet rs = db.querySQL(sql);
		//把查到的資料放入list裡..
		//和.jsp裡的 List區 內容要搭配起來
		while (rs.next()){
			counter++;
			String rowArray[]=new String[10];
			rowArray[0]=rs.getString("enterOrg");
			rowArray[1]=rs.getString("ownership");
			rowArray[2]=rs.getString("caseNo");
			rowArray[3]=rs.getString("propertyNo"); 
			rowArray[4]=rs.getString("serialNo"); 
			rowArray[5]=rs.getString("propertyName");
			rowArray[6]=rs.getString("propertyKind");
			rowArray[7]=rs.getString("keepUnitName");
			rowArray[8]=rs.getString("keeperName");
			rowArray[9]=rs.getString("place");
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
