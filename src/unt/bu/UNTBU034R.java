/*
*<br>程式目的：建物基本資料與管理資料挑檔作業報表檔 
*<br>程式代號：untbu034p
*<br>撰寫日期：
*<br>程式作者：
*<br>--------------------------------------------------------
*<br>修改作者　　修改日期　　　修改目的
*<br>--------------------------------------------------------
*/

package unt.bu;

import java.io.File;
import java.io.FileOutputStream;
import java.sql.ResultSet;
import java.util.ArrayList;

import javax.servlet.ServletContext;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import sys.web.MyServletContext;
import util.Common;
import util.Database;
import util.Datetime;

public class UNTBU034R extends UNTBU001Q{

String[] sSourceField;
String[] sDestField;
String enterOrg;
String ownership;
String propertyNo;
String serialNo;
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
String q_ownership;
String q_propertyNo1;
String q_propertyNo2;
String q_propertyNo1Name;
String q_propertyNo2Name;
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
String q_ownershipCause;
String q_sourceKind;
String q_useState;
String q_proceedDateS;
String q_proceedDateE;
String q_proceedType;
String q_useRelation;
String q_useAreaS;
String q_useAreaE;
String q_differenceKind;
String q_propertyName1;
String q_keepUnit;
String q_keepUnitName;
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
public String getEnterOrg(){ return checkGet(enterOrg); }
public void setEnterOrg(String s){ enterOrg=checkSet(s); }
public String getOwnership(){ return checkGet(ownership); }
public void setOwnership(String s){ ownership=checkSet(s); }
public String getPropertyNo(){ return checkGet(propertyNo); }
public void setPropertyNo(String s){ propertyNo=checkSet(s); }
public String getSerialNo(){ return checkGet(serialNo); }
public void setSerialNo(String s){ serialNo=checkSet(s); }
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
public String getQ_enterOrg(){ return checkGet(q_enterOrg); }
public void setQ_enterOrg(String s){ q_enterOrg=checkSet(s); }
public String getQ_ownership(){ return checkGet(q_ownership); }
public void setQ_ownership(String s){ q_ownership=checkSet(s); }
public String getQ_propertyNo1(){ return checkGet(q_propertyNo1); }
public void setQ_propertyNo1(String s){ q_propertyNo1=checkSet(s); }
public String getQ_propertyNo2(){ return checkGet(q_propertyNo2); }
public void setQ_propertyNo2(String s){ q_propertyNo2=checkSet(s); }
public String getQ_propertyNo1Name(){ return checkGet(q_propertyNo1Name); }
public void setQ_propertyNo1Name(String s){ q_propertyNo1Name=checkSet(s); }
public String getQ_propertyNo2Name(){ return checkGet(q_propertyNo2Name); }
public void setQ_propertyNo2Name(String s){ q_propertyNo2Name=checkSet(s); }
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
public String getQ_ownershipCause(){ return checkGet(q_ownershipCause); }
public void setQ_ownershipCause(String s){ q_ownershipCause=checkSet(s); }
public String getQ_sourceKind(){ return checkGet(q_sourceKind); }
public void setQ_sourceKind(String s){ q_sourceKind=checkSet(s); }
public String getQ_useState(){ return checkGet(q_useState); }
public void setQ_useState(String s){ q_useState=checkSet(s); }
public String getQ_proceedDateS(){ return checkGet(q_proceedDateS); }
public void setQ_proceedDateS(String s){ q_proceedDateS=checkSet(s); }
public String getQ_proceedDateE(){ return checkGet(q_proceedDateE); }
public void setQ_proceedDateE(String s){ q_proceedDateE=checkSet(s); }
public String getQ_proceedType(){ return checkGet(q_proceedType); }
public void setQ_proceedType(String s){ q_proceedType=checkSet(s); }
public String getQ_useRelation(){ return checkGet(q_useRelation); }
public void setQ_useRelation(String s){ q_useRelation=checkSet(s); }
public String getQ_useAreaS(){ return checkGet(q_useAreaS); }
public void setQ_useAreaS(String s){ q_useAreaS=checkSet(s); }
public String getQ_useAreaE(){ return checkGet(q_useAreaE); }
public void setQ_useAreaE(String s){ q_useAreaE=checkSet(s); }
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

String excelFileName;
public String getExcelFileName(){ return checkGet(excelFileName); }
public void setExcelFileName(String s){ excelFileName=checkSet(s); }

String tableName;
String fileName;
private String getTableName() {return checkGet(tableName);}
private void setTableName(String tableName) {this.tableName = checkSet(tableName);}
public String getFileName() {return checkGet(fileName);}
public void setFileName(String fileName) {this.fileName = checkSet(fileName);}

//EMOM
private String EMOMTstr="";

//<<<<<<<<<<<<<<<<<<<<<<<<<<<<<< 需修改處 >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>

public UNTBU034R(){
	setFileName("UNTBU034R");				//設定輸出EXCEL的檔名
	setTableName("QRY_UNTBU034R");			//從 fieldMapping 這張 Table 取出 tableName = getTableName() 的資料
											//顯示在網頁上 ( 詳細見 getFieldList() )

											//****************************************************
											//	注意 : fieldMapping 需設定資料
											//****************************************************
}

/**
 * 產生EXCEL報表用SQL
 * @return SQL語法 
 */
private String getExportAllSQL(){
	String querySql=" select "+
			" a.enterOrg, b.organSName as enterOrgName, " +
			
			" a.ownership , a.propertyKind, a.fundType, a.valuable, a.taxCredit, a.fundsSource, a.ownershipCause, a.sourceKind," +
			
			" (select z.codeName from sysca_code z where 1=1 and (z.codeKindID='owa' or z.codeKindID='OWA') and z.codeid=a.ownership) as ownershipName,"+
			" a.enterdate, (case a.datastate when '1' then '現存' when '2' then '減損' end) as datastate,"+
			" (case a.verify when 'Y' then '是' else '否' end) as verify, a.propertyNo, a.serialNo, c.propertyName, a.propertyName1,"+
			" c.material, a.doorPlate1, a.doorPlate2," +
			" (select codename from SYSca_code where codekindid='FLA' and codeid=a.floor1) as floor1,"+
			" (select codename from SYSca_code where codekindid='FLA' and codeid=a.floor2) as floor2,"+
			" (select codename from SYSca_code where codekindid='BST' and codeid=a.buildStyle) as buildStyle,"+
			" a.buildDate, (case a.propertyKind when '01' then '公務用' when '02' then '公共用' when '03' then '事業用' when '04' then '非公用' end) as propertyKindName, " +
			" (select codename from SYSca_code where codekindid='FUD' and codeid=a.fundType) as fundTypeName,"+
			" (case a.valuable when 'Y' then '是' else '否' end) as valuableName, (case a.taxCredit when 'Y' then '是'else '否' end) as taxCreditName, " +
			" (select codename from SYSca_code where codekindid='CAB' and codeid=a.cause) as cause,"+
			" (select codename from SYSca_code where codekindid='FSO' and codeid=a.fundsSource) as fundsSourceName,"+
			" (select codename from SYSca_code where codekindid='SKB' and codeid=a.sourceKind) as sourceKindName,"+
			" a.sourceDate, a.sourceDoc, b.organSName as manageOrg, a.CArea, a.SArea,"+
			" a.area, a.holdNume, a.holdDeno, a.holdArea, a.bookValue,"+
			" a.ownershipDate, " +
			" (select codename from SYSca_code where codekindid='OCB' and codeid=a.ownershipCause) as ownershipCauseName,"+
			" (case a.nonProof when 'Y' then '是' else '否' end) as nonProof, a.proofDoc, a.ownershipNote,"+
			" e.serialNo1, " +
			" (select codename from SYSca_code where codekindid='URE' and codeid=e.useRelation) as useRelationName,"+
			" (select organaname from SYSCA_Organ where 1=1 and organid=e.useUnit) as manageuseunitname," +
			" e.useDateS, e.useDateE,"+
			" e.useArea, e.notes1, "+
			" f.serialno1 as serialNo1_floor," +
			" (select codename from SYSca_code where codekindid='FLC' and codeid=f.floor) as floor_floor,"+
			" f.purpose as purpose_floor, f.area as area_floor, "+
			" g.serialno1 as serialno1_Attachment1," +
			" (select codename from SYSca_code where codekindid='FLC' and codeid=g.attachment) as attachment_Attachment1,"+							
			" g.area as area_Attachment1, " +
			" (select codename from SYSca_code where codekindid='STU' and codeid=g.stuff) as stuff_Attachment1,"+
			" g.purpose as purpose_Attachment1,"+
			" h.serialNo1 as serialNo1_base, " +
			" (select codename from SYSca_code where codekindid='OWN' and codeid=h.ownership1) as ownership1_base,"+
			" h.area as area_base, h.holdNume as holdNume_base, h.holdDeno as holdDeno_base, h.holdArea as holdArea_base," +
			" (select organaname from SYSCA_Organ where 1=1 and organid=h.manageOrg) as manageOrg_base," +
			" h.owner as owner_base," +
			" (select codename from SYSca_code where codekindid='SEP' and codeid=h.useSeparate) as useSeparate_base,"+
			" (select codename from SYSca_code where codekindid='UKD' and codeid=h.useKind) as useKind_base,"+
			" (select codename from SYSca_code where codekindid='OWN' and codeid=h.field) as field_base,"+
			" h.landrule as landrule_base, h.notes1 as notes1_base,"+
			" i.serialno1 as serialno1_common, i.area as area_common, i.holdnume as holdnume_common, i.holddeno as holddeno_common,"+
			" i.holdarea as holdarea_common,"+
			" j.judgmentdate, j.judgmentvalue,"+
			" (d.signDesc + substring(a.signno,8,5) + '-' + substring(a.signno,13,3)) as signDesc, a.signno as signno,"+
			" h.signno as signno_base,"+
			" i.signno as signno_common,"+
			" a.differencekind"
			+ ",(select z.unitname from UNTMP_KEEPUNIT z where z.enterorg=a.enterorg and z.unitno=a.keepunit ) as keepunitname"
			+ ",a.keepunit"
			+ ",(select z.keepername from UNTMP_KEEPER z where z.enterorg=a.enterorg and z.keeperno=a.keeper) as keepername"
			+ ",a.keeper"
			+ ",(select z.unitname from UNTMP_KEEPUNIT z where z.enterorg=a.enterorg and z.unitno=a.useunit )as useunitname"
			+ ",a.useunit"
			+ ",(select z.keepername from UNTMP_KEEPER z where z.enterorg=a.enterorg and z.keeperno=a.userno)as usernoname"
			+ ",a.userno"
			+ ",a.usernote"
			+ ",(select z.placename from SYSCA_PLACE z where z.enterorg=a.enterorg and z.placeno =a.place1 )as place1"
			+ ",a.place1 as placeCode "
			+ ",a.place,a.engineeringno,"+
			" a.caseserialno,a.doorplatevillage1,a.doorplatevillage2,a.doorplaterd1,a.doorplaterd2,a.doorplatesec,a.doorplateln,a.doorplatealy,a.doorplateno,a.doorplatefloor1,"+
			" a.doorplatefloor2,a.netvalue,a.buydate,a.originaldeprmethod,a.originalbuildfeecb,a.originaldeprUnitcb,a.originaldeprpark,a.originaldeprunit,a.originaldeprunit1,a.originaldepraccounts,"+
			" a.originalscrapvalue,a.originaldepramount,a.originalaccumdepr,a.originalapportionmonth,a.originalmonthdepr,a.buildfeecb,a.deprunitcb"+
			
			",(select z.deprparkname from SYSCA_DEPRPARK z where z.enterorg=a.enterorg and z.deprparkno=a.deprpark )as deprpark"+
			",(select z.deprunitname from SYSCA_DEPRUNIT z where z.enterorg=a.enterorg and z.deprunitno=a.deprunit )as deprunit"+
			",(select z.deprunit1name from SYSCA_DEPRUNIT1 z where z.enterorg=a.enterorg and z.deprunit1no=a.deprunit1 ) as deprunit1"+
			",(select z.depraccountsname from SYSCA_DEPRACCOUNTS z where z.enterorg=a.enterorg and z.depraccountsno=a.depraccounts ) as depraccounts"+
			
			",a.apportionmonth,a.nodeprset,a.originalmovedate"
			+ ",(select z.unitname from UNTMP_KEEPUNIT z where z.enterorg=a.enterorg and z.unitno=a.originalkeepunit ) as originalkeepunit"
			+ ",(select z.keepername from UNTMP_KEEPER z where z.enterorg=a.enterorg and z.keeperno=a.originalKeeper) as originalkeeper"
			+ ",(select z.unitname from UNTMP_KEEPUNIT z where z.enterorg=a.enterorg and z.unitno=a.originaluseunit )as originaluseunit"
			+ ",(select z.keepername from UNTMP_KEEPER z where z.enterorg=a.enterorg and z.keeperno=a.originaluser) as originaluser"
			+ ",a.originalusernote"
			+ ",(select z.placename from SYSCA_PLACE z where z.enterorg=a.enterorg and z.placeno =a.originalplace1 )as originalplace1"
			+ ",a.originalplace,"+
			" a.movedate,a.uselicense,a.escroworivalue,a.escroworiaccumdepr,a.oldpropertyno,a.oldserialno,a.otherlimityear,"+
			" (Convert(varchar(4),DATEDIFF(M, a.buydate, getdate()) / 12) + '年' + convert(varchar(2),DATEDIFF(M, a.buydate, getdate()) % 12)) + '個月' AS 'useym'," +
			"a.depramount, a.accumdepr, a.monthdepr, a.monthdepr1, a.notes"+
			" from UNTBU_BUILDING a " +
			" left join SYSCA_ORGAN b on a.enterorg=b.organid " +
			" left join SYSPK_PROPERTYCODE c on a.propertyno=c.propertyno " +
			
			" left join UNTBU_MANAGE e on a.enterorg=e.enterorg and a.ownership=e.ownership and a.propertyno=e.propertyno and a.serialno=e.serialno and a.differencekind=e.differencekind" +
			" left join UNTBU_FLOOR f on a.enterorg=f.enterorg and a.ownership=f.ownership and a.propertyno=f.propertyno and a.serialno=f.serialno and a.differencekind=f.differencekind" +
			" left join UNTBU_ATTACHMENT1 g on a.enterorg=g.enterorg and a.ownership=g.ownership and a.propertyno=g.propertyno and a.serialno=g.serialno and a.differencekind=g.differencekind" +
			" left join UNTBU_BASE h on a.enterorg=h.enterorg and a.ownership=h.ownership and a.propertyno=h.propertyno and a.serialno=h.serialno and a.differencekind=h.differencekind" +
			" left join UNTBU_COMMONUSE i on a.enterorg=i.enterorg and a.ownership=i.ownership and a.propertyno=i.propertyno and a.serialno=i.serialno and a.differencekind=i.differencekind" +
			" left join UNTBU_PRICE j on a.enterorg=j.enterorg and a.ownership=j.ownership and a.propertyno=j.propertyno and a.serialno=j.serialno and a.differencekind=j.differencekind" +
			
			" left join SYSCA_SIGN d on substring(a.signno,1,7)=d.signno" +
			" left join SYSCA_SIGN k on substring(h.signno,1,7)=k.signno" +
			" left join SYSCA_SIGN l on substring(i.signno,1,7)=l.signno" +
			
			" where 1=1"+
		
			" and a.datastate = " + Common.sqlChar(getQ_dataState()) +
			" and a.verify = 'Y'";
		
	return querySql;
}

/**
 * 設定查詢視窗所使用的SQL
 * @param sql SQL語法
 * @return SQL語法
 */
private String getQuerySQL(String sql){
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
	if (!"".equals(getQ_propertyNoS()))
		sql+=" and a.propertyNo >= " + Common.sqlChar(getQ_propertyNoS()) ;
	if (!"".equals(getQ_propertyNoE()))
		sql+=" and a.propertyNo <= " + Common.sqlChar(getQ_propertyNoE()) ;
	if (!"".equals(getQ_serialNoS()))
		sql+=" and a.serialNo >= " + Common.sqlChar(getQ_serialNoS());		
	if (!"".equals(getQ_serialNoE()))
		sql+=" and a.serialNo <=" + Common.sqlChar(getQ_serialNoE());	
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
		setQ_signNo4(Common.formatFrontZero(getQ_signNo4(),5));
		setQ_signNo5(Common.formatFrontZero(getQ_signNo5(),3));	
		if ("".equals(q_signNo)){
			q_signNo="_______"+getQ_signNo4()+getQ_signNo5();
		}else{
			q_signNo=q_signNo+getQ_signNo4()+getQ_signNo5();				
		}
	}				
	if (!"".equals(q_signNo))
		sql+=" and a.signNo like '" + q_signNo + "%'" ;
	if (!"".equals(getQ_enterDateS()))
		sql+=" and a.enterDate >= " + Common.sqlChar(new Datetime().changeTaiwanYYMMDD(getQ_enterDateS(),"2")) ;
	if (!"".equals(getQ_enterDateE()))
		sql+=" and a.enterDate <= " + Common.sqlChar(new Datetime().changeTaiwanYYMMDD(getQ_enterDateE(),"2")) ;
	if (!"".equals(Common.get(getQ_propertyKind()))&&"00".equals(Common.get(getQ_propertyKind())))
		sql += " and a.propertyKind <'04' ";
	else if (!"".equals(Common.get(getQ_propertyKind())))
		sql += " and a.propertyKind = "+ util.Common.sqlChar(getQ_propertyKind());
	if (!"".equals(getQ_fundType()))
		sql+=" and a.fundType = " + Common.sqlChar(getQ_fundType()) ;
	if (!"".equals(getQ_valuable()))
		sql+=" and a.valuable = " + Common.sqlChar(getQ_valuable()) ;
	if (!"".equals(getQ_taxCredit()))
		sql+=" and a.taxCredit = " + Common.sqlChar(getQ_taxCredit()) ;
	if (!"".equals(getQ_areaS()))
		sql+=" and a.area >= " + Common.sqlChar(getQ_areaS()) ;
	if (!"".equals(getQ_areaE()))
		sql+=" and a.area <= " + Common.sqlChar(getQ_areaE()) ;
	if (!"".equals(getQ_holdAreaS()))
		sql+=" and a.holdArea >= " + Common.sqlChar(getQ_holdAreaS()) ;
	if (!"".equals(getQ_holdAreaE()))
		sql+=" and a.holdArea <= " + Common.sqlChar(getQ_holdAreaE()) ;
	if (!"".equals(getQ_bookValueS()))
		sql+=" and a.bookValue >= " + Common.sqlChar(getQ_bookValueS()) ;
	if (!"".equals(getQ_bookValueE()))
		sql+=" and a.bookValue <= " + Common.sqlChar(getQ_bookValueE()) ;
	if (!"".equals(getQ_fundsSource()))
		sql+=" and a.fundsSource = " + Common.sqlChar(getQ_fundsSource()) ;
	if (!"".equals(getQ_ownershipCause()))
		sql+=" and a.ownershipCause = " + Common.sqlChar(getQ_ownershipCause()) ;
	if (!"".equals(getQ_sourceKind()))
		sql+=" and a.sourceKind = " + Common.sqlChar(getQ_sourceKind()) ;
	if(!"".equals(getQ_differenceKind()))
		sql+=" and a.differenceKind = " + Common.sqlChar(getQ_differenceKind()) ;
	if (!"".equals(getQ_propertyName1()))
		sql+=" and a.propertyName1 like " + Common.sqlChar("%"+getQ_propertyName1()+"%") ;	
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
	return sql;
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
		String sql=" select a.enterOrg, a.ownership, a.propertyNo, a.serialNo, a.signNo, "+
				" b.organSName as enterOrgName, (select z.codeName from sysca_code z where 1=1 and (z.codeKindID='owa' or z.codeKindID='OWA') and z.codeid=a.ownership) as ownershipName, " +
				" c.propertyName " +
				" from untbu_building a left join SYSCA_ORGAN b on a.enterOrg=b.organID left join SYSPK_PropertyCode c on a.propertyNo=c.propertyNo " +
				" left join UNTBU_Manage e on a.enterorg=e.enterorg and a.ownership=e.ownership and a.propertyno=e.propertyno and a.serialno=e.serialno and a.differenceKind=e.differenceKind" +
				" left join UNTBU_Floor f on a.enterorg=f.enterorg and a.ownership=f.ownership and a.propertyno=f.propertyno and a.serialno=f.serialno and a.differenceKind=f.differenceKind" +
				" left join UNTBU_Attachment1 g on a.enterorg=g.enterorg and a.ownership=g.ownership and a.propertyno=g.propertyno and a.serialno=g.serialno and a.differenceKind=g.differenceKind" +
				" left join UNTBU_Base h on a.enterorg=h.enterorg and a.ownership=h.ownership and a.propertyno=h.propertyno and a.serialno=h.serialno and a.differenceKind=h.differenceKind" +
				" left join UNTBU_CommonUse i on a.enterorg=i.enterorg and a.ownership=i.ownership and a.propertyno=i.propertyno and a.serialno=i.serialno and a.differenceKind=i.differenceKind" +
				" left join UNTBU_Price j on a.enterorg=j.enterorg and a.ownership=j.ownership and a.propertyno=j.propertyno and a.serialno=j.serialno and a.differenceKind=j.differenceKind" +
				" where 1=1 and a.datastate = " + Common.sqlChar(getQ_dataState()) +
				" and a.verify = 'Y'";

		sql=getQuerySQL(sql);
		
		ResultSet rs = db.querySQL(sql,true);
		processCurrentPageAttribute(rs);			
		if (getTotalRecord() > 0) {
			int count = getRecordStart();
			int end = getRecordEnd();
			do {
				if (count > end)break;
				String rowArray[]=new String[8];
				rowArray[0]=rs.getString("enterOrgName"); 
				rowArray[1]=rs.getString("ownershipName"); 
				rowArray[2]=rs.getString("propertyNo")+"-"+rs.getString("serialNo");
				if(!"".equals(checkGet(rs.getString("signNo"))) && rs.getString("signNo").length() == 15 ){
				rowArray[3]=getSignDescName(rs.getString("signNo").substring(0,7)) + rs.getString("signNo").substring(7,12) + '-' + rs.getString("signNo").substring(12);
				}
				else{
				rowArray[3]="";	
				}
				rowArray[4]=rs.getString("enterOrg"); 
				rowArray[5]=rs.getString("ownership"); 
				rowArray[6]=rs.getString("propertyNo");
				rowArray[7]=rs.getString("serialNo");
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


//<<<<<<<<<<<<<<<<<<<<<<<<<<<<<< 以下不必修改 >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>

public String getFieldList(String mgrOrg, String userOrg) throws Exception {
	String sStr = "";
	Database db = new Database();
	ResultSet rs = null;
	String sSQL = "";
	try {
		sSQL = "select engFieldName, zhFieldName from fieldMapping where tableName='"+getTableName()+"' ";
	
		if (!checkGet(mgrOrg).equalsIgnoreCase(checkGet(userOrg))){
			sSQL += " and isMgr='N'";
		}
		sSQL += " order by orderby ";
		
		rs = db.querySQL(sSQL);
		
		while (rs.next()) {
			sStr += "<option value='" + Common.checkGet(rs.getString("engFieldName")) + ":;:" + Common.checkGet(rs.getString("zhFieldName")) + "'>" + Common.checkGet(rs.getString("zhFieldName")) + "</option>";
		}
		rs.close();
	} catch (Exception e) {
		e.printStackTrace();
	} finally {
		db.closeAll();
	}
	
	return sStr;
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
		
		int itemp=1;
		
		for (i=0; i<sDestField.length; i++) {
			arrField = sDestField[i].split(":;:");			
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
		HSSFRow row;
		HSSFCell cell;
		//Print Excel Column 
		arrContext = strContext.split(";;");
		for (i=0; i<arrContext.length;i++) {
			row = sheet1.createRow((short)i);				
			String[] arrValue = arrContext[i].split(",");
			for (j=0; j<arrValue.length; j++) {				
				cell = row.createCell((short)j);
				//TODO	cell.setEncoding(HSSFCell.ENCODING_UTF_16);
				cell.setCellValue(arrValue[j]);			
			}
		}		
		
		if (!"".equals(strEngFields)) {
			
			String querySql=this.getExportAllSQL();

			//Retrive Data
			String sql = "select distinct " + strFields + " from ("+querySql+") a where 1=1";
			
			sql=getQuerySQLForExport(sql);
			
	        String uploadCaseID = new java.rmi.dgc.VMID().toString();
	        uploadCaseID = uploadCaseID.replaceAll(":","_");
	        
	        ServletContext context = MyServletContext.getInstance().getServletContext();
	        File tempDirectory = new File(context.getInitParameter("filestoreLocation"));
			
			tempDirectory = new File(tempDirectory,uploadCaseID);
			tempDirectory.mkdirs();
			
			rs = db.querySQL(sql);
			System.out.println("SQL: " + sql);
			if(rs.wasNull()) {
					throw new Exception("報表產製異常，請洽系統管理員");
			}
			while (rs.next()){
				row = sheet1.createRow((short)i);				
				for (j=0; j<sDestField.length; j++) {
					cell = row.createCell((short)j);
					//TODO cell.setEncoding(HSSFCell.ENCODING_UTF_16);
					cell.setCellValue(rs.getString(j+1));
				}			
				i++;			
			}
			
			FileOutputStream fout = new FileOutputStream(tempDirectory.getAbsoluteFile()+File.separator+getFileName()+".xls");
			
			wb.write(fout);
			fout.flush();
			fout.close();
			
			this.setExcelFileName(tempDirectory.getAbsoluteFile()+File.separator+getFileName()+".xls");
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
		FileOutputStream fout = new FileOutputStream(tempDirectory.getAbsoluteFile()+File.separator+getFileName()+".xls");			
		wb.write(fout);
		fout.flush();
		fout.close();
		this.setExcelFileName(tempDirectory.getAbsoluteFile()+File.separator+getFileName()+".xls");
		*/
		e.printStackTrace();
		setState("exportAllError");
	} finally {
		db.closeAll();
	}
}
private String getQuerySQLForExport(String sql){
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
	if (!"".equals(getQ_propertyNoS()))
		sql+=" and a.propertyNo >= " + Common.sqlChar(getQ_propertyNoS()) ;
	if (!"".equals(getQ_propertyNoE()))
		sql+=" and a.propertyNo <= " + Common.sqlChar(getQ_propertyNoE()) ;
	if (!"".equals(getQ_serialNoS()))
		sql+=" and a.serialNo >= " + Common.sqlChar(getQ_serialNoS());		
	if (!"".equals(getQ_serialNoE()))
		sql+=" and a.serialNo <=" + Common.sqlChar(getQ_serialNoE());	
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
		setQ_signNo4(Common.formatFrontZero(getQ_signNo4(),5));
		setQ_signNo5(Common.formatFrontZero(getQ_signNo5(),3));	
		if ("".equals(q_signNo)){
			q_signNo="_______"+getQ_signNo4()+getQ_signNo5();
		}else{
			q_signNo=q_signNo+getQ_signNo4()+getQ_signNo5();				
		}
	}				
	if (!"".equals(q_signNo))
		sql+=" and a.signNo like '" + q_signNo + "%'" ;
	if (!"".equals(getQ_enterDateS()))
		sql+=" and a.enterDate >= " + Common.sqlChar(new Datetime().changeTaiwanYYMMDD(getQ_enterDateS(),"2")) ;
	if (!"".equals(getQ_enterDateE()))
		sql+=" and a.enterDate <= " + Common.sqlChar(new Datetime().changeTaiwanYYMMDD(getQ_enterDateE(),"2")) ;
	if (!"".equals(Common.get(getQ_propertyKind()))&&"00".equals(Common.get(getQ_propertyKind())))
		sql += " and a.propertyKind <'04' ";
	else if (!"".equals(Common.get(getQ_propertyKind())))
		sql += " and a.propertyKind = "+ util.Common.sqlChar(getQ_propertyKind());
	if (!"".equals(getQ_fundType()))
		sql+=" and a.fundType = " + Common.sqlChar(getQ_fundType()) ;
	if (!"".equals(getQ_valuable()))
		sql+=" and a.valuable = " + Common.sqlChar(getQ_valuable()) ;
	if (!"".equals(getQ_taxCredit()))
		sql+=" and a.taxCredit = " + Common.sqlChar(getQ_taxCredit()) ;
	if (!"".equals(getQ_areaS()))
		sql+=" and a.area >= " + Common.sqlChar(getQ_areaS()) ;
	if (!"".equals(getQ_areaE()))
		sql+=" and a.area <= " + Common.sqlChar(getQ_areaE()) ;
	if (!"".equals(getQ_holdAreaS()))
		sql+=" and a.holdArea >= " + Common.sqlChar(getQ_holdAreaS()) ;
	if (!"".equals(getQ_holdAreaE()))
		sql+=" and a.holdArea <= " + Common.sqlChar(getQ_holdAreaE()) ;
	if (!"".equals(getQ_bookValueS()))
		sql+=" and a.bookValue >= " + Common.sqlChar(getQ_bookValueS()) ;
	if (!"".equals(getQ_bookValueE()))
		sql+=" and a.bookValue <= " + Common.sqlChar(getQ_bookValueE()) ;
	if (!"".equals(getQ_fundsSource()))
		sql+=" and a.fundsSource = " + Common.sqlChar(getQ_fundsSource()) ;
	if (!"".equals(getQ_ownershipCause()))
		sql+=" and a.ownershipCause = " + Common.sqlChar(getQ_ownershipCause()) ;
	if (!"".equals(getQ_sourceKind()))
		sql+=" and a.sourceKind = " + Common.sqlChar(getQ_sourceKind()) ;
	if(!"".equals(getQ_differenceKind()))
		sql+=" and a.differenceKind = " + Common.sqlChar(getQ_differenceKind()) ;
	if (!"".equals(getQ_propertyName1()))
		sql+=" and a.propertyName1 like " + Common.sqlChar("%"+getQ_propertyName1()+"%") ;	
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
		sql+=" and a.placeCode = " + Common.sqlChar(getQ_place());
	if(!"".equals(getQ_placeNote()))
		sql+=" and a.place like " + Common.sqlChar("%" + getQ_placeNote() + "%");
	if(!"".equals(getQ_engineeringNo()))
		sql+=" and a.engineeringNo = " + Common.sqlChar(getQ_engineeringNo()) ;
	return sql;
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