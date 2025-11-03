/*
*<br>程式目的：土地基本資料與管理資料挑檔作業
*<br>程式代號：UNTLA046R
*<br>程式日期：0940829
*<br>程式作者：clive.chang
*<br>--------------------------------------------------------
*<br>修改作者　　修改日期　　　修改目的
*<br>--------------------------------------------------------
*/

package unt.la;

import java.io.*;
import java.sql.ResultSet;
import java.util.ArrayList;

import javax.servlet.ServletContext;

import sys.web.MyServletContext;
import util.*;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFPrintSetup;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

public class UNTLA046R extends QueryBean{

String[] sSourceField;
String[] sDestField;
String enterOrg;
String enterOrgName;
String ownership;
String propertyNo;
String propertyNo2;
String serialNo;
String serialNoS;
String serialNoE;
String signNo1;
String signNo2;
String signNo3;
String signNo4;
String signNo5;
String enterDateS;
String enterDateE;
String dataState;
String verify;
String propertyKind;
String fundType;
String valuable;
String taxCredit;
String areaS;
String areaE;
String holdAreaS;
String holdAreaE;
String bookValueS;
String bookValueE;
String fundsSource;
String useSeparate;
String useKind;
String ownershipCause;
String field;
String sourceKind;
String useState;
String useRelation;
String useAreaS;
String useAreaE;
String differenceKind;
String propertyName1;
String keepUnit;
String keepUnitName;
String keeper;
String useUnit;
String userNo;
String originalUstNote;
String place;
String placeName;
String placeNote;
String engineeringNo;
String engineeringNoName;
String userNote;

String q_enterOrg;
String q_enterOrgName;
String q_ownership;
String q_propertyNo;
String q_serialNoS;
String q_serialNoE;
String q_signNo1;
String q_signNo2;
String q_signNo3;
String q_signNo4;
String q_signNo5;
String q_enterDateS;
String q_enterDateE;
String q_dataState;
String q_verify;
String q_propertyKind;
String q_fundType;
String q_valuable;
String q_taxCredit;
String q_areaS;
String q_areaE;
String q_holdAreaS;
String q_holdAreaE;
String q_bookValueS;
String q_bookValueE;
String q_fundsSource;
String q_useSeparate;
String q_useKind;
String q_ownershipCause;
String q_field;
String q_sourceKind;
String q_useState1;
String q_useState;
String q_useRelation;
String q_useAreaS;
String q_useAreaE;
String q_differenceKind;
String q_propertyName1;
String q_keepUnit;
String q_keepUnitName;

String q_signNo = "";
String q_propertyNoS;
String q_propertyNoE;
String q_propertyNoSName;
String q_propertyNoEName;
String q_propertyNoName;
String q_keeper;
String q_useUnit;
String q_userNo;
String q_originalUstNote;
String q_place;
String q_placeName;
String q_placeNote;
String q_engineeringNo;
String q_engineeringNoName;
String q_userNote;

String strKeySet[] = null;
String isOrganManager;
String isAdminManager;
String organID;
String exportPath;
public String getOrganID() { return checkGet(organID); }
public void setOrganID(String s) { organID = checkSet(s); }
public String getIsOrganManager() { return checkGet(isOrganManager); }
public void setIsOrganManager(String s) { isOrganManager = checkSet(s); }
public String getIsAdminManager() { return checkGet(isAdminManager); }
public void setIsAdminManager(String s) { isAdminManager = checkSet(s); }
public String getExportPath() { return checkGet(exportPath); }
public void setExportPath(String s) { exportPath=checkSet(s); }

public String getQ_propertyNoS(){ return checkGet(q_propertyNoS); }
public void setQ_propertyNoS(String s){ q_propertyNoS=checkSet(s); }
public String getQ_propertyNoE(){ return checkGet(q_propertyNoE); }
public void setQ_propertyNoE(String s){ q_propertyNoE=checkSet(s); }
public String getQ_propertyNoName(){ return checkGet(q_propertyNoName); }
public void setQ_propertyNoName(String s){ q_propertyNoName=checkSet(s); }
public String getQ_propertyNoSName(){ return checkGet(q_propertyNoSName); }
public void setQ_propertyNoSName(String s){ q_propertyNoSName=checkSet(s); }
public String getQ_propertyNoEName(){ return checkGet(q_propertyNoEName); }
public void setQ_propertyNoEName(String s){ q_propertyNoEName=checkSet(s); }
public String getEnterOrg(){ return checkGet(enterOrg); }
public void setEnterOrg(String s){ enterOrg=checkSet(s); }
public String getEnterOrgName(){ return checkGet(enterOrgName); }
public void setEnterOrgName(String s){ enterOrgName=checkSet(s); }
public String getOwnership(){ return checkGet(ownership); }
public void setOwnership(String s){ ownership=checkSet(s); }
public String getPropertyNo(){ return checkGet(propertyNo); }
public void setPropertyNo(String s){ propertyNo=checkSet(s); }
public String getSerialNo(){ return checkGet(serialNo); }
public void setSerialNo(String s){ serialNo=checkSet(s); }


public String getSerialNoS(){ return checkGet(serialNoS); }
public void setSerialNoS(String s){ serialNoS=checkSet(s); }
public String getSerialNoE(){ return checkGet(serialNoE); }
public void setSerialNoE(String s){ serialNoE=checkSet(s); }
public String getSignNo1(){ return checkGet(signNo1); }
public void setSignNo1(String s){ signNo1=checkSet(s); }
public String getSignNo2(){ return checkGet(signNo2); }
public void setSignNo2(String s){ signNo2=checkSet(s); }
public String getSignNo3(){ return checkGet(signNo3); }
public void setSignNo3(String s){ signNo3=checkSet(s); }
public String getSignNo4(){ return checkGet(signNo4); }
public void setSignNo4(String s){ signNo4=checkSet(s); }
public String getSignNo5(){ return checkGet(signNo5); }
public void setSignNo5(String s){ signNo5=checkSet(s); }
public String getEnterDateS(){ return checkGet(enterDateS); }
public void setEnterDateS(String s){ enterDateS=checkSet(s); }
public String getEnterDateE(){ return checkGet(enterDateE); }
public void setEnterDateE(String s){ enterDateE=checkSet(s); }
public String getDataState(){ return checkGet(dataState); }
public void setDataState(String s){ dataState=checkSet(s); }
public String getVerify(){ return checkGet(verify); }
public void setVerify(String s){ verify=checkSet(s); }
public String getPropertyKind(){ return checkGet(propertyKind); }
public void setPropertyKind(String s){ propertyKind=checkSet(s); }
public String getFundType(){ return checkGet(fundType); }
public void setFundType(String s){ fundType=checkSet(s); }
public String getValuable(){ return checkGet(valuable); }
public void setValuable(String s){ valuable=checkSet(s); }
public String getTaxCredit(){ return checkGet(taxCredit); }
public void setTaxCredit(String s){ taxCredit=checkSet(s); }
public String getAreaS(){ return checkGet(areaS); }
public void setAreaS(String s){ areaS=checkSet(s); }
public String getAreaE(){ return checkGet(areaE); }
public void setAreaE(String s){ areaE=checkSet(s); }
public String getHoldAreaS(){ return checkGet(holdAreaS); }
public void setHoldAreaS(String s){ holdAreaS=checkSet(s); }
public String getHoldAreaE(){ return checkGet(holdAreaE); }
public void setHoldAreaE(String s){ holdAreaE=checkSet(s); }
public String getBookValueS(){ return checkGet(bookValueS); }
public void setBookValueS(String s){ bookValueS=checkSet(s); }
public String getBookValueE(){ return checkGet(bookValueE); }
public void setBookValueE(String s){ bookValueE=checkSet(s); }
public String getFundsSource(){ return checkGet(fundsSource); }
public void setFundsSource(String s){ fundsSource=checkSet(s); }
public String getUseSeparate(){ return checkGet(useSeparate); }
public void setUseSeparate(String s){ useSeparate=checkSet(s); }
public String getUseKind(){ return checkGet(useKind); }
public void setUseKind(String s){ useKind=checkSet(s); }
public String getOwnershipCause(){ return checkGet(ownershipCause); }
public void setOwnershipCause(String s){ ownershipCause=checkSet(s); }
public String getField(){ return checkGet(field); }
public void setField(String s){ field=checkSet(s); }
public String getSourceKind(){ return checkGet(sourceKind); }
public void setSourceKind(String s){ sourceKind=checkSet(s); }
public String getUseState(){ return checkGet(useState); }
public void setUseState(String s){ useState=checkSet(s); }
public String getUseRelation(){ return checkGet(useRelation); }
public void setUseRelation(String s){ useRelation=checkSet(s); }
public String getUseAreaS(){ return checkGet(useAreaS); }
public void setUseAreaS(String s){ useAreaS=checkSet(s); }
public String getUseAreaE(){ return checkGet(useAreaE); }
public void setUseAreaE(String s){ useAreaE=checkSet(s); }
public String getDifferenceKind(){ return checkGet(differenceKind); }
public void setDifferenceKind(String s){ differenceKind=checkSet(s); }
public String getPropertyName1(){ return checkGet(propertyName1); }
public void setPropertyName1(String s){ propertyName1=checkSet(s); }
public String getKeepUnit(){ return checkGet(keepUnit); }
public void setKeepUnit(String s){ keepUnit=checkSet(s); }
public String getKeepUnitName(){ return checkGet(keepUnitName); }
public void setKeepUnitName(String s){ keepUnitName=checkSet(s); }
public String getKeeper(){ return checkGet(keeper); }
public void setKeeper(String s){ keeper=checkSet(s); }
public String getUseUnit(){ return checkGet(useUnit); }
public void setUseUnit(String s){ useUnit=checkSet(s); }
public String getUserNo(){ return checkGet(userNo); }
public void setUserNo(String s){ userNo=checkSet(s); }
public String getOriginalUstNote(){ return checkGet(originalUstNote); }
public void setOriginalUstNote(String s){ originalUstNote=checkSet(s); }
public String getPlace(){ return checkGet(place); }
public void setPlace(String s){ place=checkSet(s); }
public String getPlaceName(){ return checkGet(placeName); }
public void setPlaceName(String s){ placeName=checkSet(s); }
public String getPlaceNote(){ return checkGet(placeNote); }
public void setPlaceNote(String s){ placeNote=checkSet(s); }
public String getEngineeringNo(){ return checkGet(engineeringNo); }
public void setEngineeringNo(String s){ engineeringNo=checkSet(s); }
public String getEngineeringNoName(){ return checkGet(engineeringNoName); }
public void setEngineeringNoName(String s){ engineeringNoName=checkSet(s); }
public String getUserNote(){ return checkGet(userNote); }
public void setUserNote(String s){ userNote=checkSet(s); }

String excelFileName;
public String getExcelFileName(){ return checkGet(excelFileName); }
public void setExcelFileName(String s){ excelFileName=checkSet(s); }

public String getQ_enterOrg(){ return checkGet(q_enterOrg); }
public void setQ_enterOrg(String s){ q_enterOrg=checkSet(s); }
public String getQ_enterOrgName(){ return checkGet(q_enterOrgName); }
public void setQ_enterOrgName(String s){ q_enterOrgName=checkSet(s); }
public String getQ_ownership(){ return checkGet(q_ownership); }
public void setQ_ownership(String s){ q_ownership=checkSet(s); }
public String getQ_propertyNo(){ return checkGet(q_propertyNo); }
public void setQ_propertyNo(String s){ q_propertyNo=checkSet(s); }
public String getQ_serialNoS(){ return checkGet(q_serialNoS); }
public void setQ_serialNoS(String s){ q_serialNoS=checkSet(s); }
public String getQ_serialNoE(){ return checkGet(q_serialNoE); }
public void setQ_serialNoE(String s){ q_serialNoE=checkSet(s); }
public String getQ_signNo1(){ return checkGet(q_signNo1); }
public void setQ_signNo1(String s){ q_signNo1=checkSet(s); }
public String getQ_signNo2(){ return checkGet(q_signNo2); }
public void setQ_signNo2(String s){ q_signNo2=checkSet(s); }
public String getQ_signNo3(){ return checkGet(q_signNo3); }
public void setQ_signNo3(String s){ q_signNo3=checkSet(s); }
public String getQ_signNo4(){ return checkGet(q_signNo4); }
public void setQ_signNo4(String s){ q_signNo4=checkSet(s); }
public String getQ_signNo5(){ return checkGet(q_signNo5); }
public void setQ_signNo5(String s){ q_signNo5=checkSet(s); }
public String getQ_enterDateS(){ return checkGet(q_enterDateS); }
public void setQ_enterDateS(String s){ q_enterDateS=checkSet(s); }
public String getQ_enterDateE(){ return checkGet(q_enterDateE); }
public void setQ_enterDateE(String s){ q_enterDateE=checkSet(s); }
public String getQ_dataState(){ return checkGet(q_dataState); }
public void setQ_dataState(String s){ q_dataState=checkSet(s); }
public String getQ_verify(){ return checkGet(q_verify); }
public void setQ_verify(String s){ q_verify=checkSet(s); }
public String getQ_propertyKind(){ return checkGet(q_propertyKind); }
public void setQ_propertyKind(String s){ q_propertyKind=checkSet(s); }
public String getQ_fundType(){ return checkGet(q_fundType); }
public void setQ_fundType(String s){ q_fundType=checkSet(s); }
public String getQ_valuable(){ return checkGet(q_valuable); }
public void setQ_valuable(String s){ q_valuable=checkSet(s); }
public String getQ_taxCredit(){ return checkGet(q_taxCredit); }
public void setQ_taxCredit(String s){ q_taxCredit=checkSet(s); }
public String getQ_areaS(){ return checkGet(q_areaS); }
public void setQ_areaS(String s){ q_areaS=checkSet(s); }
public String getQ_areaE(){ return checkGet(q_areaE); }
public void setQ_areaE(String s){ q_areaE=checkSet(s); }
public String getQ_holdAreaS(){ return checkGet(q_holdAreaS); }
public void setQ_holdAreaS(String s){ q_holdAreaS=checkSet(s); }
public String getQ_holdAreaE(){ return checkGet(q_holdAreaE); }
public void setQ_holdAreaE(String s){ q_holdAreaE=checkSet(s); }
public String getQ_bookValueS(){ return checkGet(q_bookValueS); }
public void setQ_bookValueS(String s){ q_bookValueS=checkSet(s); }
public String getQ_bookValueE(){ return checkGet(q_bookValueE); }
public void setQ_bookValueE(String s){ q_bookValueE=checkSet(s); }
public String getQ_fundsSource(){ return checkGet(q_fundsSource); }
public void setQ_fundsSource(String s){ q_fundsSource=checkSet(s); }
public String getQ_useSeparate(){ return checkGet(q_useSeparate); }
public void setQ_useSeparate(String s){ q_useSeparate=checkSet(s); }
public String getQ_useKind(){ return checkGet(q_useKind); }
public void setQ_useKind(String s){ q_useKind=checkSet(s); }
public String getQ_ownershipCause(){ return checkGet(q_ownershipCause); }
public void setQ_ownershipCause(String s){ q_ownershipCause=checkSet(s); }
public String getQ_field(){ return checkGet(q_field); }
public void setQ_field(String s){ q_field=checkSet(s); }
public String getQ_sourceKind(){ return checkGet(q_sourceKind); }
public void setQ_sourceKind(String s){ q_sourceKind=checkSet(s); }
public String getQ_useState1(){ return checkGet(q_useState1); }
public void setQ_useState1(String s){ q_useState1=checkSet(s); }
public String getQ_useState(){ return checkGet(q_useState); }
public void setQ_useState(String s){ q_useState=checkSet(s); }
public String getQ_useRelation(){ return checkGet(q_useRelation); }
public void setQ_useRelation(String s){ q_useRelation=checkSet(s); }
public String getQ_useAreaS(){ return checkGet(q_useAreaS); }
public void setQ_useAreaS(String s){ q_useAreaS=checkSet(s); }
public String getQ_useAreaE(){ return checkGet(q_useAreaE); }
public void setQ_useAreaE(String s){ q_useAreaE=checkSet(s); }

public String getQ_signNo(){ return checkGet(q_signNo); }
public void setQ_signNo(String s){ q_signNo=checkSet(s); }
public String getQ_differenceKind(){ return checkGet(q_differenceKind); }
public void setQ_differenceKind(String s){ q_differenceKind=checkSet(s); }
public String getQ_propertyName1(){ return checkGet(q_propertyName1); }
public void setQ_propertyName1(String s){ q_propertyName1=checkSet(s); }
public String getQ_keepUnit(){ return checkGet(q_keepUnit); }
public void setQ_keepUnit(String s){ q_keepUnit=checkSet(s); }
public String getQ_keepUnitName(){ return checkGet(q_keepUnitName); }
public void setQ_keepUnitName(String s){ q_keepUnitName=checkSet(s); }

public String getQ_keeper(){ return checkGet(q_keeper); }
public void setQ_keeper(String s){ q_keeper=checkSet(s); }
public String getQ_useUnit(){ return checkGet(q_useUnit); }
public void setQ_useUnit(String s){ q_useUnit=checkSet(s); }
public String getQ_userNo(){ return checkGet(q_userNo); }
public void setQ_userNo(String s){ q_userNo=checkSet(s); }
public String getQ_originalUstNote(){ return checkGet(q_originalUstNote); }
public void setQ_originalUstNote(String s){ q_originalUstNote=checkSet(s); }
public String getQ_place(){ return checkGet(q_place); }
public void setQ_place(String s){ q_place=checkSet(s); }
public String getQ_placeName(){ return checkGet(q_placeName); }
public void setQ_placeName(String s){ q_placeName=checkSet(s); }
public String getQ_placeNote(){ return checkGet(q_placeNote); }
public void setQ_placeNote(String s){ q_placeNote=checkSet(s); }
public String getQ_engineeringNo(){ return checkGet(q_engineeringNo); }
public void setQ_engineeringNo(String s){ q_engineeringNo=checkSet(s); }
public String getQ_engineeringNoName(){ return checkGet(q_engineeringNoName); }
public void setQ_engineeringNoName(String s){ q_engineeringNoName=checkSet(s); }
public String getQ_userNote(){ return checkGet(q_userNote); }
public void setQ_userNote(String s){ q_userNote=checkSet(s); }

public String[] getsKeySet(){ return strKeySet; }
public void setsKeySet(String[] s){ strKeySet=s; }
public String[] getSSourceField(){ return sSourceField; }
public void setSSourceField(String[] s){ sSourceField=s; }
public String[] getSDestField(){ return sDestField; }
public void setsDestField(String[] s){ sDestField=s; }

String q_bulletinDate ;
String q_bulletinDate1 ;
static String oldView = "" ;
public String getQ_bulletinDate(){ return checkGet(q_bulletinDate); }
public void setQ_bulletinDate(String s){ q_bulletinDate=checkSet(s); }
public String getQ_bulletinDate1(){ return checkGet(q_bulletinDate1); }
public void setQ_bulletinDate1(String s){ q_bulletinDate1=checkSet(s); }

public String untla0461="",untla0462="",untla0463="" ;
public String getQ_untla0461(){ return checkGet(untla0461); }
public void setQ_untla0461(String s){ untla0461=checkSet(s); }

public String getQ_untla0462(){ return checkGet(untla0462); }
public void setQ_untla0462(String s){ untla0462=checkSet(s); }

public String getQ_untla0463(){ return checkGet(untla0463); }
public void setQ_untla0463(String s){ untla0463=checkSet(s); }

public String getQ_oldView(){ return checkGet(oldView); }
public void setQ_oldView(String s){ oldView=checkSet(s); }





/**
 * <br>
 * <br>目的：依查詢欄位查詢多筆資料
 * <br>參數：無
 * <br>傳回：傳回ArrayList
*/

//public static String untla0461="",untla0462="",untla0463="",oldView="" ;

public ArrayList queryAll() throws Exception{	
	Database db = new Database();
	ArrayList objList=new ArrayList();
	
	int counter=0;
	try {
	
		       untla0461=" select distinct  " +
		    		   	 " (select signdesc + substring(a.signno,8,4) + '-' + substring(a.signno,12,4) from SYSCA_SIGN where signno = substring(a.signno,1,7)) as signdesc, "+
		 				 " a.enterOrg,c.organsName as enterOrgName, a.ownership, a.usestate1, c.organid,	" + "\n"+
		 				 " (case a.usestate1 when '01' then '空置' when '02' then '建物' when '03' then '農作' when '04' then '其他' else '' end) as useState1Name, " + "\n"+
		 				 " (select codename from sysca_code x where x.codekindid = 'OWA' and x.codeid = a.ownership ) as ownershipName," +"\n"+
		 				 " a.caseNo,a.propertyNo,d.propertyName,a.serialNo,a.signNo, " +"\n"+
		 				 " a.doorplate,a.cause1,a.enterDate,a.dataState,a.propertyName1," +"\n"+
		 				 " (case a.dataState when '1' then '現存' when '2' then '已減損' else ' ' end) dataStateName,a.reduceDate,a.reduceCause1," +"\n"+
		 				 " a.verify, (case a.verify when 'Y' then '已核章' else '未核' end) verifyName, " +"\n"+
		 				 " a.propertyKind, (case a.propertyKind when '00' then '公用' when '01' then '公務用' when '02' then '公共用' when '03' then '事業用' when '04' then '非公用' else '' end) propertyKindName," +"\n"+
		 				 " a.valuable, (case a.valuable when 'Y' then '珍貴' else '非珍貴' end) valuableName,a.taxCredit, (case a.taxCredit when 'Y' then '抵繳' else '非抵繳' end) taxCreditName," +"\n"+
		 				 " a.originalArea,a.originalHoldNume,a.originalHoldDeno,a.originalHoldArea,a.area,a.holdNume,a.holdDeno,a.holdArea," +"\n"+
		 				 " a.originalBasis, (case a.originalBasis when '1' then '公告地價' when '2' then '公告現值' when '3' then '取得價格' else a.originalBasis end) originalBasisName," +"\n"+
		 				 " a.originalDate,a.originalUnit,a.originalBV,a.originalNote,a.accountingTitle,a.bookUnit,a.bookValue," +"\n"+
		 				 " a.cause,(select f.codeName from sysca_code f where a.cause=f.codeid and f.codekindid='CAA') as causeName,a.fundsSource, (select g.codeName from sysca_code g where a.fundsSource=g.codeid and g.codekindid='FSO' ) as fundsSourceName,a.useSeparate, (select h.codeName from sysca_code h where a.useSeparate=h.codeid and h.codekindid='SEP') as useSeparateName," +"\n"+
		 				 " a.useKind,(select i.codeName from sysca_code i where a.useKind=i.codeid and i.codekindid='UKD') as useKindName,a.oriUseSeparate,(select j.codeName from sysca_code j where a.oriUseSeparate=j.codeid and j.codekindid='SEP') as oriUseSeparateName,a.oriUseKind,(select k.codeName from sysca_code k where a.oriUseKind=k.codeid and k.codekindid='UKD') as oriUseKindName," +"\n"+
		 				 " a.ownershipCause,(select l.codeName from sysca_code l where a.ownershipCause=l.codeid and l.codekindid='OCA') as ownershipCauseName,a.field,(select m.codeName from sysca_code m where a.field=m.codeid and m.codekindid='FIE') as fieldName,a.sourceKind,(select n.codeName from sysca_code n where a.sourceKind=n.codeid and n.codekindid='SKD') as sourceKindName," +"\n"+
		 				 " a.useState,(select o.codeName from sysca_code o where a.useState=o.codeid and o.codekindid= 'UST') as useStateName,b.useRelation,(select p.codeName from sysca_code p where b.useRelation=p.codeid and p.codekindid='URE') as useRelationName," +"\n"+
		 				 " (select r.organsname from sysca_organ r where b.useUnit = r.organID) as manageuseunitname, a.manageOrg,(select t.organsname from sysca_organ t where a.manageOrg=t.organID and a.reduceCause = u.codeid) as manageOrgName," +"\n"+
		 				 " a.reduceCause,u.codeName as reduceCauseName,a.fundType,(select v.codeName from sysca_code v where a.fundType = v.codeid and v.codekindid ='FUD') as fundTypeName," +"\n"+
		 				 " a.ownershipDate,a.nonProof,a.proofDoc," +"\n"+
		 				 " a.ownershipNote,a.landRule,a.sourceDate,a.sourceDoc,a.oldOwner,a.appraiseDate,a.notes1,a.notes," +"\n"+
		 				 " a.oldPropertyNo,a.oldSerialNo,b.serialNo1,b.useUnit1,(case b.useDateS when null then '0000000' else b.useDateS end) as useDateS,(case b.useDateE when null then '9999999' end) as useDateE," +"\n"+
		 				 " b.usearea,b.notes1 as mnotes1," +"\n"+
		 				 " b.Notes as mnotes, " +"\n"+
		 				 " w.serialno1 as serialno1_attach, (select z.codename from SYSCA_CODE z where z.codekindid='OWN' and z.codeid=w.ownership1) as ownership1_attach, " +
		 				 " (select z.organaname from sysca_organ z where z.organid = w.manageorg) as manageorg_attach, w.owner as owner_attach, " + 
		 				 " (select z.codeName from SYSCA_CODE z where z.codekindid='PUR' and z.codeid=w.purpose) as purpose_attach, w.state as state_attach, w.useArea as useArea_attach, w.mergeUseSign as mergeUseSign_attach, w.notes1 as notes1_attach,"+"\n"+
		                 " a.differencekind,"
		                 + " (select z.unitname from UNTMP_KEEPUNIT z where z.enterorg=a.enterorg and z.unitno=a.keepunit ) as keepunitname,"
		                 + " a.keepunit,"
		                 + " (select z.keepername from UNTMP_KEEPER z where z.enterorg=a.enterorg and z.keeperno=a.keeper) as keepername,"
		                 + " a.keeper,"
		                 + " (select z.unitname from UNTMP_KEEPUNIT z where z.enterorg=a.enterorg and z.unitno=a.keepunit ) as useunitname,"
		                 + " a.useunit,"
		                 + " (select z.keepername from UNTMP_KEEPER z where z.enterorg=a.enterorg and z.keeperno=a.userno)as usernoname,"
		                 + " a.userno,"
		                 + " a.usernote,"
		                 + " a.place1,"
		                 + " a.place,a.engineeringno,"+"\n"+
		                 " a.caseserialno,a.netunit,a.netvalue,a.buydate,a.originalmovedate,"
		                 + " (select z.unitname from UNTMP_KEEPUNIT z where z.enterorg=a.enterorg and z.unitno=a.originalkeepunit ) as originalkeepunit,"
		                 + " (select z.keepername from UNTMP_KEEPER z where z.enterorg=a.enterorg and z.keeperno=a.originalkeeper) as originalkeeper,"
		                 + " (select z.unitname from UNTMP_KEEPUNIT z where z.enterorg=a.enterorg and z.unitno=a.originaluseunit ) as originaluseunit,"
		                 + " (select z.keepername from UNTMP_KEEPER z where z.enterorg=a.enterorg and z.keeperno=a.originaluser) as originaluser,"+"\n"
		                 + " a.originalusernote,"+"\n"
		                 + " (select z.placename from SYSCA_PLACE z where z.enterorg = a.enterorg and z.placeno = a.originalplace1) as originalplace1,"
		                 + " a.originalplace,a.movedate,"+"\n"+
		                 " (Convert(varchar(4),DATEDIFF(M, a.buydate, getdate()) / 12) + '年' + convert(varchar(2),DATEDIFF(M, a.buydate, getdate()) % 12)) + '個月' AS 'useym',"+"\n";
		
/* ----------------- 修改前  ----------------- */    
//	if(!"".equals(getQ_bulletinDate())){         //公告現值年月
//		untla0462 += " (select b.bulletinDate from untla_price b where a.enterorg = b.enterorg and a.ownership = b.ownership and a.propertyNo = b.propertyNo and a.serialNo = b.serialNo and b.bulletinDate= " + Common.sqlChar(getQ_bulletinDate())+ " )as priceUnitYM, "+"\n";
//		untla0462 += " (select b.priceUnit    from untla_price b where a.enterorg = b.enterorg and a.ownership = b.ownership and a.propertyNo = b.propertyNo and a.serialNo = b.serialNo and b.bulletinDate= " + Common.sqlChar(getQ_bulletinDate())+ " )as priceUnit, " +"\n";
//	}else{
//		untla0462 += " (select b.bulletinDate from untla_price b where a.enterorg = b.enterorg and a.ownership = b.ownership and a.propertyNo = b.propertyNo and a.serialNo = b.serialNo  and b.bulletinDate = (select max(y.bulletinDate) from UNTLA_Price y where y.enterorg = b.enterorg and y.ownership = b.ownership and y.propertyno = b.propertyno and y.serialno = b.serialno)) as priceUnitYM ,"+"\n";
//		untla0462 += " (select b.priceUnit    from untla_price b where a.enterorg = b.enterorg and a.ownership = b.ownership and a.propertyNo = b.propertyNo and a.serialNo = b.serialNo  and b.bulletinDate = (select max(y.bulletinDate) from UNTLA_Price y where y.enterorg = b.enterorg and y.ownership = b.ownership and y.propertyno = b.propertyno and y.serialno = b.serialno)) as priceUnit ,"+"\n";		
//	}
//	
//	if(!"".equals(getQ_bulletinDate1())){        //公告地價年月
//		untla0462 += " (select b.bulletinDate from untla_Value b where a.enterorg = b.enterorg and a.ownership = b.ownership and a.propertyNo = b.propertyNo and a.serialNo = b.serialNo and b.bulletinDate= " + Common.sqlChar(getQ_bulletinDate1())+" )as valueUnitYM, "+"\n";
//	    untla0462 += " (select b.valueUnit    from untla_Value b where a.enterorg = b.enterorg and a.ownership = b.ownership and a.propertyNo = b.propertyNo and a.serialNo = b.serialNo and b.bulletinDate= " + Common.sqlChar(getQ_bulletinDate1())+" )as valueUnit "+"\n";
//	}else{
//		untla0462 += " (select b.bulletinDate from UNTLA_Value b where a.enterorg = b.enterorg and a.ownership = b.ownership and a.propertyNo = b.propertyNo and a.serialNo = b.serialNo  and b.bulletinDate = (select max(y.bulletinDate) from UNTLA_Value y where y.enterorg = b.enterorg and y.ownership = b.ownership and y.propertyno = b.propertyno and y.serialno = b.serialno)) as valueUnitYM , "+"\n";
//		untla0462 += " (select b.valueUnit    from UNTLA_Value b where a.enterorg = b.enterorg and a.ownership = b.ownership and a.propertyNo = b.propertyNo and a.serialNo = b.serialNo  and b.bulletinDate = (select max(y.bulletinDate) from UNTLA_Value y where y.enterorg = b.enterorg and y.ownership = b.ownership and y.propertyno = b.propertyno and y.serialno = b.serialno)) as valueUnit "+"\n";		
//	}

/* ----------------- 修改後_1  ----------------- */ 			   	
	String max_bulletinDate_as_priceUnitYM = " (select b.bulletinDate from untla_price b where a.enterorg = b.enterorg and a.ownership = b.ownership and a.propertyNo = b.propertyNo and a.serialNo = b.serialNo  and b.bulletinDate = (select max(y.bulletinDate) from UNTLA_Price y where y.enterorg = b.enterorg and y.ownership = b.ownership and y.propertyno = b.propertyno and y.serialno = b.serialno)) ";
	String max_priceUnit = " (select b.priceUnit    from untla_price b where a.enterorg = b.enterorg and a.ownership = b.ownership and a.propertyNo = b.propertyNo and a.serialNo = b.serialNo  and b.bulletinDate = (select max(y.bulletinDate) from UNTLA_Price y where y.enterorg = b.enterorg and y.ownership = b.ownership and y.propertyno = b.propertyno and y.serialno = b.serialno)) ";
	String max_bulletinDate_as_valueUnitYM = " (select b.bulletinDate from UNTLA_Value b where a.enterorg = b.enterorg and a.ownership = b.ownership and a.propertyNo = b.propertyNo and a.serialNo = b.serialNo  and b.bulletinDate = (select max(y.bulletinDate) from UNTLA_Value y where y.enterorg = b.enterorg and y.ownership = b.ownership and y.propertyno = b.propertyno and y.serialno = b.serialno)) ";
	String max_valueUnit = " (select b.valueUnit    from UNTLA_Value b where a.enterorg = b.enterorg and a.ownership = b.ownership and a.propertyNo = b.propertyNo and a.serialNo = b.serialNo  and b.bulletinDate = (select max(y.bulletinDate) from UNTLA_Value y where y.enterorg = b.enterorg and y.ownership = b.ownership and y.propertyno = b.propertyno and y.serialno = b.serialno)) ";
		   	
	String new_bulletinDate_as_priceUnitYM = " (select b.bulletinDate from untla_price b where a.enterorg = b.enterorg and a.ownership = b.ownership and a.propertyNo = b.propertyNo and a.serialNo = b.serialNo and b.bulletinDate= " + Common.sqlChar(getQ_bulletinDate())+ " ) ";
	String new_priceUnit = " (select b.priceUnit    from untla_price b where a.enterorg = b.enterorg and a.ownership = b.ownership and a.propertyNo = b.propertyNo and a.serialNo = b.serialNo and b.bulletinDate= " + Common.sqlChar(getQ_bulletinDate())+ " ) ";
	String new_bulletinDate_as_valueUnitYM = " (select b.bulletinDate from untla_Value b where a.enterorg = b.enterorg and a.ownership = b.ownership and a.propertyNo = b.propertyNo and a.serialNo = b.serialNo and b.bulletinDate= " + Common.sqlChar(getQ_bulletinDate1())+" ) ";
	String new_valueUnit = " (select b.valueUnit    from untla_Value b where a.enterorg = b.enterorg and a.ownership = b.ownership and a.propertyNo = b.propertyNo and a.serialNo = b.serialNo and b.bulletinDate= " + Common.sqlChar(getQ_bulletinDate1())+" ) ";

	untla0462 += " (case "+Common.sqlChar(getQ_bulletinDate()) +" when '' then "+max_bulletinDate_as_priceUnitYM+" else (case "+new_bulletinDate_as_priceUnitYM+" when null then "+max_bulletinDate_as_priceUnitYM+" else "+new_bulletinDate_as_priceUnitYM+" end) end) as priceUnitYM, " +"\n";
	untla0462 += " (case "+Common.sqlChar(getQ_bulletinDate()) +" when '' then "+max_priceUnit+" else (case "+new_priceUnit+" when null then "+max_priceUnit+" else "+new_priceUnit+" end) end) as priceUnit, " +"\n";
	untla0462 += " (case "+Common.sqlChar(getQ_bulletinDate1())+" when '' then "+max_bulletinDate_as_valueUnitYM+" else (case "+new_bulletinDate_as_valueUnitYM+" when null then "+max_bulletinDate_as_valueUnitYM+" else "+new_bulletinDate_as_valueUnitYM+" end) end) as valueUnitYM, " +"\n";
	untla0462 += " (case "+Common.sqlChar(getQ_bulletinDate1())+" when '' then "+max_valueUnit+" else (case "+new_valueUnit+" when null then "+max_valueUnit+" else "+new_valueUnit+" end) end) as valueUnit " +"\n";

	
	
	untla0463 = " from untla_land a " +
						" left join untla_manage b on a.enterOrg=b.enterOrg and a.ownership=b.ownership and a.propertyNo=b.propertyNo and a.serialNo=b.serialNo and a.differenceKind=b.differenceKind" +
						" left join UNTLA_Attachment w on a.enterorg = w.enterorg and a.ownership = w.ownership and a.propertyno = w.propertyno and a.serialno = w.serialno and a.differenceKind=w.differenceKind" +
						" left join UNTLA_Value v on a.enterorg = v.enterorg and a.ownership = v.ownership and a.propertyno = v.propertyno and a.serialno = v.serialno and a.differenceKind=v.differenceKind" +
						" left join UNTLA_Price p on a.enterorg = p.enterorg and a.ownership = p.ownership and a.propertyno = p.propertyno and a.serialno = p.serialno and a.differenceKind=p.differenceKind" +
						" left join SYSCA_CODE u on a.reducecause = u.codeid and  u.codekindid ='CAA' " +"\n"+
					    " left join SYSCA_ORGAN c on a.enterorg=c.organid " +"\n"+
					    " left join SYSPK_PROPERTYCODE d on a.propertyno=d.propertyno "+"\n"+
					    " where 1=1 " +					   
					    " and a.dataState= " + Common.sqlChar(getQ_dataState()) +
					    " and a.verify='Y' " +"\n"+
					    " " ;
				
	oldView = untla0461 + untla0462 + untla0463;
	

//System.out.println("--untla046r oldView--\n"+oldView);
		String sql=" select distinct a.enterOrg, a.enterOrgName, a.ownership, a.ownershipName, a.propertyNo, a.serialNo, a.signNo "+		
				" from (" + oldView + ") a where 1=1 ";		
		//System.out.println("46------------"+sql);
			//權限控制		
			if (!"".equals(getQ_enterOrg())) {
				sql+=" and a.enterOrg = " + Common.sqlChar(getQ_enterOrg()) ;				
			} else {
				if (!getIsAdminManager().equalsIgnoreCase("Y")) {
					if ("Y".equals(getIsOrganManager())) {					
						sql += " and ( a.enterOrg = "+ Common.sqlChar(getOrganID()) +" or a.enterOrg in ( select organID from SYSCA_Organ where organSuperior = "+ Common.sqlChar(getOrganID()) +" ) ) ";
					} else {
						sql+=" and a.enterOrg = " + Common.sqlChar(getOrganID()) ;
					}
				}
			}
		
			if (!"".equals(getQ_ownership()))
				sql+=" and a.ownership = " + Common.sqlChar(getQ_ownership()) ;
			if (!"".equals(getQ_propertyNoS()))
				sql+=" and a.propertyNo >= " + Common.sqlChar(getQ_propertyNoS()) ;
			if (!"".equals(getQ_propertyNoE()))
				sql+=" and a.propertyNo <= " + Common.sqlChar(getQ_propertyNoE()) ;			
			if (!"".equals(getQ_serialNoS()))
				sql+=" and a.serialNo >= " + Common.sqlChar(getQ_serialNoS());		
			if (!"".equals(getQ_serialNoE()))
				sql+=" and a.serialNo <= " + Common.sqlChar(getQ_serialNoE());
			

			if (!"".equals(getQ_signNo1()))
				q_signNo=getQ_signNo1().substring(0,1)+"______";
			if (!"".equals(getQ_signNo2()))
				q_signNo=getQ_signNo2().substring(0,3)+"____";			
			if (!"".equals(getQ_signNo3())){
				if (getQ_signNo3().length()==4){
					q_signNo="E__" + getQ_signNo3();
				}else{
					q_signNo=getQ_signNo3();
				}	
			}
			if (!"".equals(getQ_signNo4())){
				setQ_signNo4(Common.formatFrontZero(getQ_signNo4(),4));
				setQ_signNo5(Common.formatFrontZero(getQ_signNo5(),4));	
				if ("".equals(q_signNo)){
					q_signNo="_______"+getQ_signNo4()+getQ_signNo5();
				}else{
					q_signNo=q_signNo+getQ_signNo4()+getQ_signNo5();				
				}
			}	
			if (!"".equals(q_signNo))
				sql+=" and a.signNo like '" + q_signNo + "%'" ;								
			
			if (!"".equals(getQ_enterDateS()))
				sql+=" and a.enterDate >= " + Common.sqlChar(new Datetime().changeTaiwanYYMMDD(getQ_enterDateS(),"2"));
					
			if (!"".equals(getQ_enterDateE()))
				sql+=" and a.enterDate <= " + Common.sqlChar(new Datetime().changeTaiwanYYMMDD(getQ_enterDateE(),"2"));
			

			if (!"".equals(getQ_areaS()))
				sql+=" and a.area >= " + Common.sqlChar(getQ_areaS());		
			if (!"".equals(getQ_areaE()))
				sql+=" and a.area <= " + Common.sqlChar(getQ_areaE());		

			if (!"".equals(getQ_holdAreaS()))
				sql+=" and a.holdArea >= " + Common.sqlChar(getQ_holdAreaS());		
			if (!"".equals(getQ_holdAreaE()))
				sql+=" and a.holdArea <= " + Common.sqlChar(getQ_holdAreaE());			

			if (!"".equals(getQ_bookValueS()))
				sql+=" and a.bookValue >= " + Common.sqlChar(getQ_bookValueS());		
			if (!"".equals(getQ_bookValueE()))
				sql+=" and a.bookValue <= " + Common.sqlChar(getQ_bookValueE());							
			
			if (!"".equals(getQ_useAreaS()))
				sql+=" and a.useArea >= " + Common.sqlChar(getQ_useAreaS());		
			if (!"".equals(getQ_useAreaE()))
				sql+=" and a.useArea <= " + Common.sqlChar(getQ_useAreaE());
				
			
			if (!"".equals(getQ_dataState()))
				sql+=" and a.dataState = " + Common.sqlChar(getQ_dataState()) ;
				
				sql+=" and (select count(*) from untla_reducedetail z where 1=1 and a.enterorg=z.enterorg and a.ownership=z.ownership and a.propertyno=z.propertyno and a.serialno=z.serialno and a.differencekind=z.differencekind ";
			
				if (!"".equals(getQ_enterDateS()))
					sql+=" and a.reduceDate >= " + Common.sqlChar(new Datetime().changeTaiwanYYMMDD(getQ_enterDateS(),"2"));
						
				if (!"".equals(getQ_enterDateE()))
					sql+=" and a.reduceDate <= " + Common.sqlChar(new Datetime().changeTaiwanYYMMDD(getQ_enterDateE(),"2"));
			
				sql+=" and verify='Y') = 0";
				
			if (!"".equals(getQ_propertyName1()))
				sql+=" and a.propertyName1 like " + Common.sqlChar("%"+getQ_propertyName1()+"%") ;		
			if (!"".equals(getQ_verify()))
				sql+=" and a.verify = " + Common.sqlChar(getQ_verify()) ;
			if (!"".equals(getQ_propertyKind()))
				sql+=" and a.propertyKind = " + Common.sqlChar(getQ_propertyKind()) ;
			if (!"".equals(getQ_fundType()))
				sql+=" and a.fundType = " + Common.sqlChar(getQ_fundType()) ;
			if (!"".equals(getQ_valuable()))
				sql+=" and a.valuable = " + Common.sqlChar(getQ_valuable()) ;
			if (!"".equals(getQ_taxCredit()))
				sql+=" and a.taxCredit = " + Common.sqlChar(getQ_taxCredit()) ;			
			if (!"".equals(getQ_fundsSource()))
				sql+=" and a.fundsSource = " + Common.sqlChar(getQ_fundsSource()) ;
			if (!"".equals(getQ_useSeparate()))
				sql+=" and a.useSeparate = " + Common.sqlChar(getQ_useSeparate()) ;
			if (!"".equals(getQ_useKind()))
				sql+=" and a.useKind = " + Common.sqlChar(getQ_useKind()) ;
			if (!"".equals(getQ_ownershipCause()))
				sql+=" and a.ownershipCause = " + Common.sqlChar(getQ_ownershipCause()) ;
			if (!"".equals(getQ_field()))
				sql+=" and a.field = " + Common.sqlChar(getQ_field()) ;
			if (!"".equals(getQ_sourceKind()))
				sql+=" and a.sourceKind = " + Common.sqlChar(getQ_sourceKind()) ;
			if (!"".equals(getQ_useState1()))
				sql+=" and a.useState1 = " + Common.sqlChar(getQ_useState1()) ;
			if (!"".equals(getQ_useState()))
				sql+=" and a.useState = " + Common.sqlChar(getQ_useState()) ;
			if (!"".equals(getQ_useRelation()))
				sql+=" and a.useRelation = " + Common.sqlChar(getQ_useRelation()) ;
			if(!"".equals(getQ_differenceKind()))
				sql+=" and a.differenceKind = " + Common.sqlChar(getQ_differenceKind()) ;
			if(!"".equals(getQ_keepUnit()))
				sql+=" and a.keepUnit = " + Common.sqlChar(getQ_keepUnit()) ;
			if(!"".equals(getQ_keeper()))
				sql+=" and a.keeper = " + Common.sqlChar(getQ_keeper()) ;
			if(!"".equals(getQ_useUnit()))
				sql+=" and a.useUnit = " + Common.sqlChar(getQ_useUnit()) ;
			if(!"".equals(getQ_userNo()))
				sql+=" and a.userNo = " + Common.sqlChar(getQ_userNo()) ;
			if(!"".equals(getQ_userNote()))
				sql+=" and a.userNote like " + Common.sqlChar("%" + getQ_userNote() + "%");
			if(!"".equals(getQ_place()))
				sql+=" and a.place1 = " + Common.sqlChar(getQ_place());
			if(!"".equals(getQ_placeNote()))
				sql+=" and a.place like " + Common.sqlChar("%" + getQ_placeNote() + "%");
			if(!"".equals(getQ_engineeringNo()))
				sql+=" and a.engineeringNo = " + Common.sqlChar(getQ_engineeringNo()) ;
			
//			if (!"".equals(getQ_bulletinDate()))
//				sql+=" and a.priceUnitYM = " + Common.sqlChar(getQ_bulletinDate()) ;
//			if (!"".equals(getQ_bulletinDate1()))
//				sql+=" and a.valueUnitYM = " + Common.sqlChar(getQ_bulletinDate1()) ;
//			
			sql+=" order by a.enterOrg, a.ownership, a.propertyNo, a.serialNo ";
//            System.out.println("--untla046r oldView + Where條件--\n"+sql);	
			ResultSet rs = db.querySQL(sql,true);
			processCurrentPageAttribute(rs);

			if(getTotalRecord()> 0 ){
			        int count = getRecordStart();
			        int end = getRecordEnd();
			        do{
			            if(count > end )break;
			            String rowArray[]=new String[8];
			            rowArray[0]=rs.getString("enterOrgName"); 
			            rowArray[1]=rs.getString("ownershipName"); 
			            rowArray[2]=rs.getString("propertyNo")+"-"+rs.getString("serialNo"); 
			            rowArray[3]=getSignDescName(rs.getString("signNo").substring(0,7)) + rs.getString("signNo").substring(7,11) + '-' + rs.getString("signNo").substring(11); 
			            rowArray[4]=rs.getString("enterOrg"); 
			            rowArray[5]=rs.getString("ownership"); 
			            rowArray[6]=rs.getString("propertyNo");
			            rowArray[7]=rs.getString("serialNo");
			            objList.add(rowArray);
				    count++;
			       }while(rs.next());
		}
		setStateQueryAllSuccess();		
//		if (counter<=0) {								
//			super.setState("init");
//		}
	} catch (Exception e) {
		e.printStackTrace();
	} finally {
		db.closeAll();
	}
	return objList;
}

public String getFieldList(String mgrOrg, String userOrg) throws Exception {
	Database db = new Database();
	ResultSet rs = null;
	String sSQL = "";
	StringBuffer sb = new StringBuffer("");
	try {
		sSQL = "select engFieldName, zhFieldName from fieldMapping where tableName='QRY_UNTLA046R'";
		if (!checkGet(mgrOrg).equalsIgnoreCase(checkGet(userOrg))){
			sSQL += " and isMgr='N'";
		}
		sSQL += " order by orderby " ;
		rs = db.querySQL(sSQL);
		while (rs.next()) {
			sb.append("<option value='" ).append( rs.getString("engFieldName") ).append( ":;:" ).append( rs.getString("zhFieldName") ).append( "'>" ).append( rs.getString("zhFieldName") ).append( "</option>");
		}
		rs.close();
	} catch (Exception e) {
		e.printStackTrace();
	} finally {
		db.closeAll();
	}
	return sb.toString();
}

public void exportAll() throws Exception{	
	Database db = new Database();
	ResultSet rs = null; 
	String strFields = "", strContext="";
	String strZhFields="", strEngFields="";
	String[] arrContext = null, arrField=null;
	
	int i = 0, j=0;	
	try {
		//Get Selected Fields and Field Names
		for (i=0; i<sDestField.length; i++) {
			arrField = sDestField[i].split(":;:");
			//如果有選到存置地點就把代碼轉成中文
			if(arrField[0].equals("place1")) {
				arrField[0] = " ( " + 
						"        SELECT z.placename " + 
						"        FROM SYSCA_PLACE z" + 
						"        WHERE z.enterorg = a.enterorg " + 
						"              AND z.placeno = a.place1 " + 
						"       ) AS place1 ";
			}
			if (i==0) {
				strFields += arrField[0];
				strEngFields += Common.sqlChar(arrField[0]);
				strZhFields += arrField[1];
				strContext += arrField[1];
			} else {
				strFields += "," + arrField[0];
				strEngFields += "," + Common.sqlChar(arrField[0]);
				strZhFields += "," + arrField[1];
				strContext += "," + arrField[1];
			} 
			if (i==sDestField.length-1){
				strContext += ";;";
			}			
		}

		HSSFWorkbook wb = new HSSFWorkbook();
		
		HSSFSheet sheet1 = wb.createSheet("Sheet1");
		HSSFPrintSetup hps = sheet1.getPrintSetup(); //預設列印格式
		hps.setPaperSize(HSSFPrintSetup.A4_PAPERSIZE);
		//sheet1.getPrintSetup().setPaperSize(HSSFPrintSetup.A4_PAPERSIZE); //<-------2010-10-1
		HSSFRow row;
		HSSFCell cell;
		//Print Excel Column 
		arrContext = strContext.split(";;");
		for (i=0; i<arrContext.length;i++) {
			row = sheet1.createRow((short)i);				
			String[] arrValue = arrContext[i].split(",");
			for (j=0; j<arrValue.length; j++) {
				cell = row.createCell((short)j);
				//TODO cell.setEncoding(HSSFCell.ENCODING_UTF_16);
				cell.setCellValue(arrValue[j]);			
			}
		}	


		if (!"".equals(strEngFields)) {
			//Retrive Data
			String sql = "select distinct  " + strFields + " from ( " + oldView +  "   ) a where 1=1 ";	

/* ----------------- 修改後_2  ----------------- */ 					
			if (!"".equals(getQ_enterOrg())) {
				sql+=" and a.enterOrg = " + Common.sqlChar(getQ_enterOrg()) ;
			} else if ("".equals(getIsAdminManager()) || "N".equals(getIsAdminManager())) {
				if ("Y".equals(getIsOrganManager())) {
					sql += " and ( a.enterOrg = "+ Common.sqlChar(getOrganID()) +" or a.enterOrg in ( select organID from SYSCA_Organ where organSuperior = "+ Common.sqlChar(getOrganID()) +" ) ) ";					
				} else {
					sql+=" and a.enterOrg = " + Common.sqlChar(getOrganID()) ;
				}
			}
			if (!"".equals(getQ_ownership()))
				sql+=" and a.ownership = " + Common.sqlChar(getQ_ownership()) ;
			if (!"".equals(getQ_propertyNoS()))
				sql+=" and a.propertyNo >= " + Common.sqlChar(getQ_propertyNoS()) ;
			if (!"".equals(getQ_propertyNoE()))
				sql+=" and a.propertyNo <= " + Common.sqlChar(getQ_propertyNoE()) ;			
			if (!"".equals(getQ_serialNoS()))
				sql+=" and a.serialNo >= " + Common.sqlChar(getQ_serialNoS());		
			if (!"".equals(getQ_serialNoE()))
				sql+=" and a.serialNo<=" + Common.sqlChar(getQ_serialNoE());
			

			if (!"".equals(getQ_signNo1()))
				q_signNo=getQ_signNo1().substring(0,1)+"______";
			if (!"".equals(getQ_signNo2()))
				q_signNo=getQ_signNo2().substring(0,3)+"____";			
			if (!"".equals(getQ_signNo3())){
				if (getQ_signNo3().length()==4){
					q_signNo="E__" + getQ_signNo3();
				}else{
					q_signNo=getQ_signNo3();
				}	
			}
			if (!"".equals(getQ_signNo4())){
				setQ_signNo4(Common.formatFrontZero(getQ_signNo4(),4));
				setQ_signNo5(Common.formatFrontZero(getQ_signNo5(),4));	
				if ("".equals(q_signNo)){
					q_signNo="_______"+getQ_signNo4()+getQ_signNo5();
				}else{
					q_signNo=q_signNo+getQ_signNo4()+getQ_signNo5();				
				}
			}	
			if (!"".equals(q_signNo))
				sql+=" and a.signNo like '" + q_signNo + "%'" ;								
			
			if (!"".equals(getQ_enterDateS()))
				sql+=" and a.enterDate >= " + Common.sqlChar(new Datetime().changeTaiwanYYMMDD(getQ_enterDateS(),"2"));		
			if (!"".equals(getQ_enterDateE()))
				sql+=" and a.enterDate<=" + Common.sqlChar(new Datetime().changeTaiwanYYMMDD(getQ_enterDateE(),"2"));
			

			if (!"".equals(getQ_areaS()))
				sql+=" and a.area >= " + Common.sqlChar(getQ_areaS());		
			if (!"".equals(getQ_areaE()))
				sql+=" and a.area<=" + Common.sqlChar(getQ_areaE());		

			if (!"".equals(getQ_holdAreaS()))
				sql+=" and a.holdArea >= " + Common.sqlChar(getQ_holdAreaS());		
			if (!"".equals(getQ_holdAreaE()))
				sql+=" and a.holdArea<=" + Common.sqlChar(getQ_holdAreaE());			

			if (!"".equals(getQ_bookValueS()))
				sql+=" and a.bookValue >= " + Common.sqlChar(getQ_bookValueS());		
			if (!"".equals(getQ_bookValueE()))
				sql+=" and a.bookValue<=" + Common.sqlChar(getQ_bookValueE());							
			
			if (!"".equals(getQ_useAreaS()))
				sql+=" and a.useArea >= " + Common.sqlChar(getQ_useAreaS());		
			if (!"".equals(getQ_useAreaE()))
				sql+=" and a.useArea <=" + Common.sqlChar(getQ_useAreaE());
				
			
			if (!"".equals(getQ_dataState()))
				sql+=" and a.dataState = " + Common.sqlChar(getQ_dataState()) ;
				
				sql+=" and (select count(*) from untla_reducedetail z where 1=1 and a.enterorg=z.enterorg and a.ownership=z.ownership and a.propertyno=z.propertyno and a.serialno=z.serialno and a.differencekind=z.differencekind ";
			
				if (!"".equals(getQ_enterDateS()))
					sql+=" and a.reduceDate >= " + Common.sqlChar(new Datetime().changeTaiwanYYMMDD(getQ_enterDateS(),"2"));
						
				if (!"".equals(getQ_enterDateE()))
					sql+=" and a.reduceDate <= " + Common.sqlChar(new Datetime().changeTaiwanYYMMDD(getQ_enterDateE(),"2"));
			
				sql+=" and verify='Y') = 0";

				
			if (!"".equals(getQ_verify()))
				sql+=" and a.verify = " + Common.sqlChar(getQ_verify()) ;
			if (!"".equals(getQ_propertyKind()))
				sql+=" and a.propertyKind = " + Common.sqlChar(getQ_propertyKind()) ;
			if (!"".equals(getQ_fundType()))
				sql+=" and a.fundType = " + Common.sqlChar(getQ_fundType()) ;
			if (!"".equals(getQ_valuable()))
				sql+=" and a.valuable = " + Common.sqlChar(getQ_valuable()) ;
			if (!"".equals(getQ_taxCredit()))
				sql+=" and a.taxCredit = " + Common.sqlChar(getQ_taxCredit()) ;
			if (!"".equals(getQ_fundsSource()))
				sql+=" and a.fundsSource = " + Common.sqlChar(getQ_fundsSource()) ;
			if (!"".equals(getQ_useSeparate()))
				sql+=" and a.useSeparate = " + Common.sqlChar(getQ_useSeparate()) ;
			if (!"".equals(getQ_useKind()))
				sql+=" and a.useKind = " + Common.sqlChar(getQ_useKind()) ;
			if (!"".equals(getQ_ownershipCause()))
				sql+=" and a.ownershipCause = " + Common.sqlChar(getQ_ownershipCause()) ;
			if (!"".equals(getQ_field()))
				sql+=" and a.field = " + Common.sqlChar(getQ_field()) ;
			if (!"".equals(getQ_sourceKind()))
				sql+=" and a.sourceKind = " + Common.sqlChar(getQ_sourceKind()) ;
			if (!"".equals(getQ_useState1()))
				sql+=" and a.useState1 = " + Common.sqlChar(getQ_useState1()) ;
			if (!"".equals(getQ_useState()))
				sql+=" and a.useState = " + Common.sqlChar(getQ_useState()) ;
			if (!"".equals(getQ_useRelation()))
				sql+=" and a.useRelation = " + Common.sqlChar(getQ_useRelation()) ;
			if(!"".equals(getQ_differenceKind()))
				sql+=" and a.differenceKind = " + Common.sqlChar(getQ_differenceKind()) ;
			if(!"".equals(getQ_keepUnit()))
				sql+=" and a.keepUnit = " + Common.sqlChar(getQ_keepUnit()) ;
			if(!"".equals(getQ_keeper()))
				sql+=" and a.keeper = " + Common.sqlChar(getQ_keeper()) ;
			if(!"".equals(getQ_useUnit()))
				sql+=" and a.useUnit = " + Common.sqlChar(getQ_useUnit()) ;
			if(!"".equals(getQ_userNo()))
				sql+=" and a.userNo = " + Common.sqlChar(getQ_userNo()) ;
			if(!"".equals(getQ_userNote()))
				sql+=" and a.userNote like " + Common.sqlChar("%"+getQ_userNote()+"%") ;
			if(!"".equals(getQ_place()))
				sql+=" and a.place1 = " + Common.sqlChar(getQ_place()) ;
			if(!"".equals(getQ_placeNote()))
				sql+=" and a.place = " + Common.sqlChar(getQ_placeNote()) ;
			if(!"".equals(getQ_engineeringNo()))
				sql+=" and a.engineeringNo = " + Common.sqlChar(getQ_engineeringNo()) ;
//			sql+=" order by a.enterOrg, a.ownership, a.propertyNo, a.serialNo ";
//			if (!"".equals(getQ_bulletinDate()))
//				sql+=" and a.priceUnitYM = " + Common.sqlChar(getQ_bulletinDate()) ;
//			if (!"".equals(getQ_bulletinDate1()))
//				sql+=" and a.valueUnitYM = " + Common.sqlChar(getQ_bulletinDate1()) ;
System.out.println("exportAll------>"+sql);
	
			//<<<<<<<<<<<<<<<<<<< 20120905 modify
			
//			db.querySQL("insert into tabletemp "+sql);
			
//			sql="select * from tabletemp a";
			
			//20120905 modify >>>>>>>>>>>>>>>>>>>>

			if(sql.contains("signName")){
				sql = sql.replaceAll("signName", "'SIGNNAME'");
			}

			rs = db.querySQL(sql);
			
	        String uploadCaseID = new java.rmi.dgc.VMID().toString();
	        uploadCaseID = uploadCaseID.replaceAll(":","_");		
	        
	        ServletContext context = MyServletContext.getInstance().getServletContext();
	        File tempDirectory = new File(context.getInitParameter("filestoreLocation"));
			
			tempDirectory = new File(tempDirectory,uploadCaseID);
			tempDirectory.mkdirs();
			
			while (rs.next()){
				
					row = sheet1.createRow((short)i);				
					for (j=0; j<sDestField.length; j++) {
						cell = row.createCell((short)j);
						//TODO cell.setEncoding(HSSFCell.ENCODING_UTF_16);
						if("SIGNNAME".equals(rs.getString(j+1))){
							cell.setCellValue(getSignDescName(rs.getString("signNo").substring(0,7)));
						}else{
							cell.setCellValue(rs.getString(j+1));										
						}
					}
					
				i++;
			}
			rs.close();
			
			
			FileOutputStream fout = new FileOutputStream(tempDirectory.getAbsoluteFile()+File.separator+"untla046r.xls");			
			wb.write(fout);
			fout.flush();
			fout.close();
			this.setExcelFileName(tempDirectory.getAbsoluteFile()+File.separator+"untla046r.xls");
			
			super.setState("exportAll");			
		}
	} catch (Exception e) {
	/*	File tempDirectory = new File(System.getProperty("java.io.tmpdir"));
		HSSFWorkbook wb = new HSSFWorkbook();
		HSSFSheet sheet1 = wb.createSheet("Sheet1");
		HSSFPrintSetup hps = sheet1.getPrintSetup(); //預設列印格式
		hps.setPaperSize(HSSFPrintSetup.A4_PAPERSIZE);
		//sheet1.getPrintSetup().setPaperSize(HSSFPrintSetup.A4_PAPERSIZE); //<-------2010-10-1
		HSSFRow row;
		HSSFCell cell;
		FileOutputStream fout = new FileOutputStream(tempDirectory.getAbsoluteFile()+File.separator+"untla046r.xls");			
		wb.write(fout);
		fout.flush();
		fout.close();
		this.setExcelFileName(tempDirectory.getAbsoluteFile()+File.separator+"untla046r.xls");
		*/
		e.printStackTrace();
	} finally {
		db.closeAll();
	}
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
}