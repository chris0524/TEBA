/*
*<br>程式目的：非消耗品廢品處理作業－批次新增明細資料
*<br>程式代號：untne022f
*<br>程式日期：0941212
*<br>程式作者：Cherry
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

public class UNTNE022F extends UNTNE020Q{

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
    String toggleAll;
    public String getToggleAll(){ if (!"".equals(checkGet(toggleAll))) return "checked"; else return "unchecked"; }    
    public void setToggleAll(String s){ toggleAll=checkSet(s); }    
    
    String strKeySet[] = null;
    public String[] getSKeySet(){ return strKeySet; }
    public void setsKeySet(String[] s){ strKeySet=s; }


	String q_caseNo;
	String q_reduceDateS;
	String q_reduceDateE;
	String q_differenceKind;
	String q_useUnit;
	String q_userNo;
	String q_userNote;
	String q_place1;
	String q_place1Name;
	String q_placeNote;
	String q_keepUnit;
	String q_keeper;
	
	public String getQ_caseNo(){ return checkGet(q_caseNo); }
	public void setQ_caseNo(String s){ q_caseNo=checkSet(s); }
	public String getQ_reduceDateS(){ return checkGet(q_reduceDateS); }
	public void setQ_reduceDateS(String s){ q_reduceDateS=checkSet(s); }
	public String getQ_reduceDateE(){ return checkGet(q_reduceDateE); }
	public void setQ_reduceDateE(String s){ q_reduceDateE=checkSet(s); }
	public String getQ_differenceKind() {return checkGet(q_differenceKind);}
	public void setQ_differenceKind(String s) {q_differenceKind = checkSet(s);}
	public String getQ_useUnit() {return checkGet(q_useUnit);}
	public void setQ_useUnit(String qUseUnit) {q_useUnit = checkSet(qUseUnit);}
	public String getQ_userNo() {return checkGet(q_userNo);}
	public void setQ_userNo(String qUserNo) {q_userNo = checkSet(qUserNo);}
	public String getQ_userNote() {return checkGet(q_userNote);}
	public void setQ_userNote(String q_userNote) {this.q_userNote = checkSet(q_userNote);}
	public String getQ_place1() {return checkGet(q_place1);}
	public void setQ_place1(String q_place1) {this.q_place1 = checkSet(q_place1);}
	public String getQ_place1Name() {return checkGet(q_place1Name);}
	public void setQ_place1Name(String q_place1Name) {this.q_place1Name = checkSet(q_place1Name);}
	public String getQ_placeNote() {return checkGet(q_placeNote);}
	public void setQ_placeNote(String q_placeNote) {this.q_placeNote = checkSet(q_placeNote);}
	public String getQ_keepUnit(){ return checkGet(q_keepUnit); }
	public void setQ_keepUnit(String s){ q_keepUnit=checkSet(s); }
	public String getQ_keeper(){ return checkGet(q_keeper); }
	public void setQ_keeper(String s){ q_keeper=checkSet(s); }

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
	if(getSKeySet()!=null)
	    getcut = getSKeySet().length;	//有勾選的list中資料數
	//String sSQL = "";
	//String[] execSQLArray = new String[getcut];
	String[] strKeys = null;
	//String[] strgetValue = null; 
	ResultSet rs = null;
	int i = 0;	
	//int counter=0;
	try {
		for (i=0; i<getcut; i++) {	
		strKeys = getSKeySet()[i].split(",");
			//先找出 UNTNE_ReduceDetail 的值
			String landsql = " select caseNo, propertyNo, lotNo, serialNo, otherPropertyUnit, otherMaterial, otherLimitYear,differenceKind, " +
							" propertyName1, enterDate, buyDate, reduceDate, propertyKind, fundType, valuable, accountingTitle, oldBookAmount, " +
							" oldBookUnit, oldBookValue, adjustBookAmount, adjustBookValue, newBookAmount, newBookValue, articleName, nameplate, specification, " +
							" sourceDate, licensePlate, moveDate, keepUnit, keeper, useUnit, userNo, place, returnPlace, useYear, useMonth,place1,usernote, " +
							" oldPropertyNo, oldSerialNo " + 
							" from UNTNE_ReduceDetail "+
							" where 1=1 " +
							" and enterOrg = " + Common.sqlChar(strKeys[0]) +
							" and ownership = " + Common.sqlChar(strKeys[1]) +
							" and caseNo = " + Common.sqlChar(strKeys[2]) +
							" and propertyNo = " + Common.sqlChar(strKeys[3]) +
							" and serialNo = " + Common.sqlChar(strKeys[4]) +
							" and differenceKind = " + Common.sqlChar(strKeys[5]) +
							"";
			
			rs = db.querySQL(landsql);
			if (rs.next()){
				sbSQL.append(" insert into UNTNE_DealDetail(" ).append( 
						" enterOrg,").append(
						" ownership,").append(
						" caseNo1,").append(
						" caseNo,").append(
						" propertyNo,").append(
						" lotNo,").append(
						" serialNo,").append(
						" otherPropertyUnit,").append(
						" otherMaterial,").append(
						" otherLimitYear,").append(
						" propertyName1,").append(
						" enterDate,").append(
						" buyDate,").append(
						" reduceDate,").append(
						" dealDate,").append(
						" verify,").append(
						" propertyKind,").append(
						" fundType,").append(
						" valuable,").append(
						" accountingTitle,").append(
						" oldBookAmount,").append(
						" oldBookUnit,").append(
						" oldBookValue,").append(
						" adjustBookAmount,").append(
						" adjustBookValue,").append(
						" newBookAmount,").append(
						" newBookValue,").append(
						" articleName,").append(
						" nameplate,").append(
						" specification,").append(
						" sourceDate,").append(
						" licensePlate,").append(
						" moveDate,").append(
						" keepUnit,").append(
						" keeper,").append(
						" useUnit,").append(
						" userNo,").append(
						" place,").append(
						" returnPlace,").append(
						" useYear,").append(
						" useMonth,").append(
						" oldPropertyNo,").append(
						" oldSerialNo,").append(
						" differenceKind,").append(
						" userNote,").append(
						" place1").append(
					" )Values(" ).append(
						Common.sqlChar(enterOrg) ).append( "," ).append(
						Common.sqlChar(ownership) ).append( "," ).append(
						Common.sqlChar(caseNo1) ).append( "," ).append(
						Common.sqlChar(rs.getString("caseNo")) ).append( "," ).append(
						Common.sqlChar(rs.getString("propertyNo")) ).append( "," ).append(
						Common.sqlChar(rs.getString("lotNo")) ).append( "," ).append(
						Common.sqlChar(rs.getString("serialNo")) ).append( "," ).append(
						Common.sqlChar(rs.getString("otherPropertyUnit")) ).append( "," ).append(
						Common.sqlChar(rs.getString("otherMaterial")) ).append( "," ).append(
						Common.sqlChar(rs.getString("otherLimitYear")) ).append( "," ).append(
						Common.sqlChar(rs.getString("propertyName1")) ).append( "," ).append(
						Common.sqlChar(rs.getString("enterDate")) ).append( "," ).append(
						Common.sqlChar(rs.getString("buyDate")) ).append( "," ).append(
						Common.sqlChar(rs.getString("reduceDate")) ).append( "," ).append(
						Common.sqlChar(ul._transToCE_Year(dealDate)) ).append( "," ).append(
						Common.sqlChar(verify) ).append( "," ).append(
						Common.sqlChar(rs.getString("propertyKind")) ).append( "," ).append(
						Common.sqlChar(rs.getString("fundType")) ).append( "," ).append(
						Common.sqlChar(rs.getString("valuable")) ).append( "," ).append(
						Common.sqlChar(rs.getString("accountingTitle")) ).append( "," ).append(
						Common.sqlChar(rs.getString("oldBookAmount")) ).append( "," ).append(
						Common.sqlChar(rs.getString("oldBookUnit")) ).append( "," ).append(
						Common.sqlChar(rs.getString("oldBookValue")) ).append( "," ).append(
						Common.sqlChar(rs.getString("adjustBookAmount")) ).append( "," ).append(
						Common.sqlChar(rs.getString("adjustBookValue")) ).append( "," ).append(
						Common.sqlChar(rs.getString("newBookAmount")) ).append( "," ).append(
						Common.sqlChar(rs.getString("newBookValue")) ).append( "," ).append(
						Common.sqlChar(rs.getString("articleName")) ).append( "," ).append(
						Common.sqlChar(rs.getString("nameplate")) ).append( "," ).append(
						Common.sqlChar(rs.getString("specification")) ).append( "," ).append(
						Common.sqlChar(rs.getString("sourceDate")) ).append( "," ).append(
						Common.sqlChar(rs.getString("licensePlate")) ).append( "," ).append(
						Common.sqlChar(rs.getString("moveDate")) ).append( "," ).append(
						Common.sqlChar(rs.getString("keepUnit")) ).append( "," ).append(
						Common.sqlChar(rs.getString("keeper")) ).append( "," ).append(
						Common.sqlChar(rs.getString("useUnit")) ).append( "," ).append(
						Common.sqlChar(rs.getString("userNo")) ).append( "," ).append(
						Common.sqlChar(rs.getString("place")) ).append( "," ).append(
						Common.sqlChar(rs.getString("returnPlace")) ).append( "," ).append(
						Common.sqlChar(rs.getString("useYear")) ).append( "," ).append(
						Common.sqlChar(rs.getString("useMonth")) ).append( "," ).append(
						Common.sqlChar(rs.getString("oldPropertyNo")) ).append( "," ).append(
						Common.sqlChar(rs.getString("oldSerialNo")) ).append( "," ).append(
						Common.sqlChar(rs.getString("differenceKind")) 	).append( "," ).append(
						Common.sqlChar(rs.getString("userNote"))        ).append( "," ).append(
						Common.sqlChar(rs.getString("place1"))           ).append(
						" ):;:");	
			}
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

public UNTNE022F  queryOne() throws Exception{
	Database db = new Database();
	UNTNE022F obj = this;
	try {
		String sql=" select a.enterOrg, g.organSName as enterOrgName, a.ownership, a.caseNo, a.propertyNo, " +
					" a.lotNo, a.serialNo, b.propertyName, b.propertyUnit, b.material, " +
					" b.limitYear, a.otherPropertyUnit, a.otherMaterial, a.otherLimitYear, " +
					" a.propertyName1, a.enterDate, a.buyDate, a.reduceDate, a.dealDate, " +
					" a.verify, a.propertyKind, a.fundType, a.valuable, a.accountingTitle, " +
					" a.oldBookAmount, a.oldBookUnit, a.oldBookValue, a.adjustBookAmount, " +
					" a.adjustBookValue, a.newBookAmount, a.newBookValue, a.articleName, " +
					" a.nameplate, a.specification, a.sourceDate, a.licensePlate, a.moveDate, " +
					" a.keepUnit, a.keeper, a.useUnit, a.userNo, a.place, " +
					" (select c.unitName from UNTMP_KeepUnit c where a.enterOrg=c.enterOrg and a.keepUnit=c.unitNo) as keepUnitName, "+
					" (select d.unitName from UNTMP_KeepUnit d where a.enterOrg=d.enterOrg and a.useUnit=d.unitNo) as useUnitName, "+
					" (select e.keeperName from UNTMP_Keeper e where a.enterOrg=e.enterOrg and a.keepUnit=e.unitNo and a.keeper=e.keeperNo) as keeperName, "+		
					" (select f.keeperName from UNTMP_Keeper f where a.enterOrg=f.enterOrg and a.useUnit=f.unitNo and a.userNo=f.keeperNo) as userName, "+		
					" a.useYear, a.useMonth, a.permitReduceDate, a.notes, a.oldPropertyNo, a.oldSerialNo  "+
					" from UNTNE_reduceDetail a, SYSPK_PropertyCode2 b, SYSCA_Organ g where 1=1 " +
					" and a.enterOrg = b.enterOrg and a.propertyNo = b.propertyNo " +
					" and a.enterOrg=g.organID" +
					" and a.enterOrg = " + Common.sqlChar(enterOrg) +
					" and a.ownership = " + Common.sqlChar(ownership) +
					" and a.caseNo = " + Common.sqlChar(caseNo) +
					" and a.propertyNo = " + Common.sqlChar(propertyNo) +
					" and a.serialNo = " + Common.sqlChar(serialNo) +
					"";
		ResultSet rs = db.querySQL(sql);
		if (rs.next()){
			obj.setEnterOrg(rs.getString("enterOrg"));
			obj.setEnterOrgName(rs.getString("enterOrgName"));
			obj.setOwnership(rs.getString("ownership"));
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
			obj.setEnterDate(rs.getString("enterDate"));
			obj.setBuyDate(rs.getString("buyDate"));
			obj.setReduceDate(rs.getString("reduceDate"));
			obj.setDealDate(rs.getString("dealDate"));
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
			obj.setSourceDate(rs.getString("sourceDate"));
			obj.setLicensePlate(rs.getString("licensePlate"));
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
			obj.setUseYear(rs.getString("useYear"));
			obj.setUseMonth(rs.getString("useMonth"));
			obj.setNotes(rs.getString("notes"));
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
	Map<String,String> differencekindMap = TCGHCommon.getSysca_Code("DFK"); 					//財產區分別名稱
	Database db = new Database();
	ArrayList objList=new ArrayList();
	int counter=0;
	try {
		String sql=" select a.enterorg, a.ownership, a.caseno, a.propertyno, b.propertyname,a.propertyname1,a.serialno,a.differencekind,a.adjustbookamount,a.adjustbookvalue,chk.*" +
					" from UNTNE_REDUCEDETAIL a " +
					" left join SYSPK_PROPERTYCODE2 b on a.enterorg = b.enterorg and a.propertyno = b.propertyno " +
					" OUTER APPLY ( " +
					"	select top 1 x.* from (" +
					" 		select p.verify as chkverify ,isnull(p.proofyear, '') as chkproofyear ,isnull(p.proofdoc, '') as chkproofdoc ,isnull(p.proofno, '') as chkproofno " +
					"		from UNTNE_DEALDETAIL d left join UNTNE_DEALPROOF p on d.enterorg = p.enterorg and d.caseno1 = p.caseno1 " +		
					"		where d.enterorg = a.enterorg and d.ownership = a.ownership and d.differencekind = a.differencekind and d.propertyno = a.propertyno and d.serialno = a.serialno and d.verify!='Y' " +
					"  ) as x " +
					" ) as chk " +
					" where 1=1 " +
					" and a.verify = 'Y' and a.dealcaseno is null "+
					" and a.cause != '01' ";
		if (!"".equals(getQ_enterOrg()))	{		
			sql+=" and a.enterorg 	= " + Common.sqlChar(getQ_enterOrg());
		}
		if (!"".equals(getQ_ownership()))	{	
			sql+=" and a.ownership 	= " + Common.sqlChar(getQ_ownership()) ;
		}
		if (!"".equals(getQ_differenceKind()))	{	
			sql+=" and a.differencekind 	= " + Common.sqlChar(getQ_differenceKind());
		}
					
		
		if (!"".equals(getQ_caseNoS()))
			sql+=" and a.caseno >= " + Common.sqlChar(Common.formatRearString(getQ_caseNoS(),10,'0'));		
		if (!"".equals(getQ_caseNo1E()))
			sql+=" and a.caseno <= " + Common.sqlChar(Common.formatRearString(getQ_caseNoE(),10, '9'));
		if (!"".equals(getQ_propertyNoS()))
			sql+=" and a.propertyno >= " + Common.sqlChar(getQ_propertyNoS());		
		if (!"".equals(getQ_propertyNoE()))
			sql+=" and a.propertyno <= " + Common.sqlChar(getQ_propertyNoE());						
		if (!"".equals(getQ_serialNoS()))
			sql+=" and a.serialno >= " + Common.sqlChar(getQ_serialNoS());		
		if (!"".equals(getQ_serialNoE()))
			sql+=" and a.serialno <= " + Common.sqlChar(getQ_serialNoE());			
		if (!"".equals(getQ_reduceDateS()))
			sql+=" and a.reducedate >= " + Common.sqlChar(Datetime.getYYYYMMDDFromRocDate(getQ_reduceDateS()));
		if (!"".equals(getQ_reduceDateE()))
			sql+=" and a.reducedate <= " + Common.sqlChar(Datetime.getYYYYMMDDFromRocDate(getQ_reduceDateE()));
		if (!"".equals(getQ_propertyKind()))
			sql+=" and a.propertykind = " + Common.sqlChar(getQ_propertyKind()) ;
		if (!"".equals(getQ_fundType()))
			sql+=" and a.fundtype = " + Common.sqlChar(getQ_fundType()) ;
		if (!"".equals(getQ_valuable()))
			sql+=" and a.valuable = " + Common.sqlChar(getQ_valuable()) ;
		if (!"".equals(getQ_keepUnit())){ 
			sql += " and a.keepunit = " + Common.sqlChar(getQ_keepUnit());
		}		
		if (!"".equals(getQ_keeper())){ 
			sql += " and a.keeper = " + Common.sqlChar(getQ_keeper());
		}
		if (!"".equals(getQ_useUnit())){ 
			sql += " and a.useunit = " + Common.sqlChar(getQ_useUnit());
		}
		if (!"".equals(getQ_userNo())){ 
			sql += " and a.userno = " + Common.sqlChar(getQ_userNo());
		}
		
		if (!"".equals(getQ_userNote())){ 
			sql += " and a.usernote like " + Common.sqlChar("%" + getQ_userNote() + "%");
		}
		
		if (!"".equals(getQ_place1())){ 
			sql += " and a.place1 = " + Common.sqlChar(getQ_place1());
		}
		if (!"".equals(getQ_placeNote())){ 
			sql += " and a.place like " + Common.sqlChar("%" + getQ_placeNote() + "%");
		}

		sql += " order by a.propertyno,a.serialno";
		ResultSet rs = db.querySQL_NoChange(sql);
		//把查到的資料放入list裡..
		//和.jsp裡的 List區 內容要搭配起來
		while (rs.next()){
			counter++;
			String rowArray[]=new String[15];
			rowArray[0]=differencekindMap.get(rs.getString("differencekind"));
			rowArray[1]=checkGet(rs.getString("propertyNo"));
			rowArray[2]=checkGet(rs.getString("serialNo"));
			rowArray[3]=checkGet(rs.getString("propertyName"));
			rowArray[4]=checkGet(rs.getString("propertyName1"));
			rowArray[5]=checkGet(rs.getString("adjustbookamount"));
			rowArray[6]=checkGet(rs.getString("adjustbookvalue")); 
			rowArray[7]=checkGet(rs.getString("enterOrg")); 
			rowArray[8]=checkGet(rs.getString("ownership"));
			rowArray[9]=checkGet(rs.getString("caseNo")); 
			rowArray[10]=checkGet(rs.getString("propertyNo")); 
			rowArray[11]=checkGet(rs.getString("serialNo"));
			rowArray[12]=checkGet(rs.getString("differenceKind"));
			
			String chkproofdesc = Common.concatStrs("字第", Common.concatStrs("年", rs.getString("chkproofyear"), rs.getString("chkproofdoc")), rs.getString("chkproofno"));
			if (!"".equals(chkproofdesc)) {
				rowArray[13] = checkGet(chkproofdesc + "號");
			} else {
				rowArray[13] = "";
			}
			
			rowArray[14]=checkGet(rs.getString("caseno"));
			
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
