/*
*<br>程式目的：物品基本資料挑檔作業
*<br>程式代號：UNTNE060R
*<br>程式日期：1050511
*<br>程式作者：Jim.Lin
*<br>--------------------------------------------------------
*<br>修改作者　　修改日期　　　修改目的
*<br>--------------------------------------------------------
*/

package unt.ne;

import java.io.File;
import java.io.FileOutputStream;
import java.sql.ResultSet;
import java.util.ArrayList;

import javax.servlet.ServletContext;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import sys.web.MyServletContext;
import util.*;

public class UNTNE060R extends UNTNE001Q{

	String[] sSourceField;
	String[] sDestField;
	String enterOrg;
	String ownership;
	String propertyNo;
	String serialNo;
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

	int count=0;//計算資料筆數
	
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}

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

//<<<<<<<<<<<<<<<<<<<<<<<<<<<<<< 需修改處 >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>

public UNTNE060R(){	
	setFileName("UNTNE060R");				//設定輸出EXCEL的檔名
	setTableName("QRY_UNTNE060R");			//從 fieldMapping 這張 Table 取出 tableName = getTableName() 的資料
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
	String querySql=
	" select "+
	" (SELECT z.organaname FROM SYSCA_ORGAN z WHERE  z.organid = a.enterorg) AS enterorg,"+
	" (select z.codename from SYSCA_CODE z where 1=1 and (z.codekindid='owa' or z.codekindid='OWA') and z.codeid=a.ownership) as ownership,"+
	" (select z.codename from SYSCA_CODE z where 1=1 and (z.codekindid='dfk' or z.codekindid='DFK') and z.codeid=a.differencekind) as differencekind,"+
	" a.caseserialno,"+
	" a.propertyno,"+
	" (select propertyname from SYSPK_PROPERTYCODE2 where enterorg=a.enterorg and propertyno=a.propertyno) as propertyname," +
	" a.otherpropertyunit,"+
	" a.othermaterial,"+
	" a.otherlimityear,"+
	" a.propertyname1,"+
	" (select codename from SYSCA_CODE where codekindid='CAA' and codecon1 in ('1','4') and codeid=a.cause) as cause,"+
	" a.cause1,"+
	" a.approvedate,"+
	" a.approvedoc,"+
	" a.enterdate,"+
	" a.buydate,"+
	" ( CASE a.verify WHEN 'Y' THEN '是' ELSE '否' END ) AS verify,"+
	" (select codename from SYSCA_CODE where codekindid='PKD' and codeid=a.propertykind) as propertykind,"+
	" (select codename from SYSCA_CODE where codekindid='FUD' and codeid=a.fundtype) as fundtype,"+
	" a.accountingtitle,"+
	" (select codename from SYSCA_CODE where codekindid='FSO' and codeid=a.fundssource) as fundssource,"+
	" a.fundssource1,"+
	" a.grantvalue,"+
	" a.articlename,"+
	" a.nameplate,"+
	" a.specification,"+
	" (select storename from UNTMP_STORE where enterorg=a.enterorg and storeno=a.storeno) as storeno,"+
	" (select codename from SYSCA_CODE where codekindid='SKD' and codeid=a.sourcekind) as sourcekind,"+
	" a.sourcedate,"+
	" a.sourcedoc,"+
	" a.oldpropertyno,"+
	" a.oldlotno,"+
	" a.notes,"+
	" (Convert(varchar(4),DATEDIFF(M, a.buydate, getdate()) / 12) + '年' + convert(varchar(2),DATEDIFF(M, a.buydate, getdate()) % 12)) + '個月' AS 'useym'," +
	" b.serialno,"+
	" b.datastate,"+
	" b.reducedate,"+
	" (select codename from SYSCA_CODE where codekindid='CAA' and codecon1 in ('2','4') and codeid=b.reducecause) as reducecause,"+
	" b.reducecause1,"+
	" b.bookamount,"+
	" b.bookvalue,"+
	" b.licenseplate,"+
	" b.purpose,"+
	" b.movedate,"+
	" (select z.unitname from UNTMP_KEEPUNIT z where z.enterorg=a.enterorg and z.unitno=b.keepunit ) as keepunitname,"+
	" b.keepunit," +
	" (select z.keepername from UNTMP_KEEPER z where z.enterorg=a.enterorg and z.keeperno=b.keeper) as keepername,"+
	" b.keeper," +
	" (select z.unitname from UNTMP_KEEPUNIT z where z.enterorg=a.enterorg and z.unitno=b.useunit ) as useunitname,"+
	" b.useunit," +
	" (select z.keepername from UNTMP_KEEPER z where z.enterorg=a.enterorg and z.keeperno=b.userno) as usernoname,"+
	" b.userno," +
	" b.usernote,"+
	" (select z.placename from SYSCA_PLACE z where z.enterorg=a.enterorg and z.placeno=b.place1) as place1,"+
	" b.place,"+
	" b.notes1,"+
	" b.barcode,"+
	" b.notes as 'detnotes',"+
	" b.propertyname1 as 'detpropertyname1',"+
	" c.serialno1,"+
	" c.equipmentname,"+
	" c.buydate as 'attbuydate',"+
	" c.equipmentunit,"+
	" c.equipmentamount,"+
	" c.unitprice,"+
	" c.totalvalue,"+
	" (select codename from SYSCA_CODE where codekindid='SKD' and codeid=c.sourcekind) as attsourcekind,"+
	" c.nameplate as 'attnameplate',"+
	" c.specification as 'attspecification',"+
	" c.notes as 'attnotes' "+
	" from UNTNE_NONEXP a "+
	" left join UNTNE_NONEXPDETAIL b on "+
	" a.enterorg=b.enterorg and "+
	" a.ownership=b.ownership and "+
	" a.differencekind=b.differencekind and "+
	" a.propertyno=b.propertyno and "+
	" a.lotno=b.lotno"+
	" left join UNTNE_ATTACHMENT2 c on "+
	" b.enterorg=c.enterorg and "+
	" b.ownership=c.ownership and"+
	" b.differencekind=c.differencekind and "+
	" b.propertyno=c.propertyno and "+
	" b.serialno=c.serialno" +
	" where 1=1"+
	" and b.datastate = " + Common.sqlChar(getQ_dataState()) +
	" and a.verify = 'Y'";

	//System.out.println("WWWWWWWWWW==>"+querySql);
	return querySql;
}

/**
 * 設定查詢視窗所使用的SQL
 * @param sql SQL語法
 * @return SQL語法
 */
private String getQuerySQL(String sql,int i){
	if (!"".equals(getQ_enterOrg())) {
		sql+=" and a.enterorg = " + Common.sqlChar(getQ_enterOrg()) ;
	} else {
		if (!getIsAdminManager().equalsIgnoreCase("Y")) {
			if ("Y".equals(getIsOrganManager())) {					
				sql += " and a.enterorg like '" + getOrganID().substring(0,5) + "%' ";					
			} else {
				sql+=" and a.enterorg = " + Common.sqlChar(getOrganID()) ;
			}
		}
	}
	if (!"".equals(getQ_ownership()))
		sql+=" and a.ownership = " + Common.sqlChar(getQ_ownership()) ;
	if (!"".equals(getQ_propertyNoS()))
		sql+=" and a.propertyno >= " + Common.sqlChar(getQ_propertyNoS()) ;
	if (!"".equals(getQ_propertyNoE()))
		sql+=" and a.propertyno <= " + Common.sqlChar(getQ_propertyNoE()) ;
	if (!"".equals(getQ_serialNoS()))
		if (i==0) {
			sql+=" and b.serialno >= " + Common.sqlChar(Common.formatFrontZero(getQ_serialNoS(),7));
		} else {
			sql+=" and b.serialno >= " + Common.sqlChar(Common.formatFrontZero(getQ_serialNoS(),7));
		}
	if (!"".equals(getQ_serialNoE()))
		if (i==0) {
			sql+=" and b.serialno <= " + Common.sqlChar(Common.formatFrontZero(getQ_serialNoE(),7));
		} else {
			sql+=" and b.serialno <= " + Common.sqlChar(Common.formatFrontZero(getQ_serialNoE(),7));
		}
	if (!"".equals(getQ_enterDateS()))
		sql+=" and a.enterdate >= " + Common.sqlChar(new Datetime().changeTaiwanYYMMDD(getQ_enterDateS(),"2")) ;
	if (!"".equals(getQ_enterDateE()))
		sql+=" and a.enterdate <= " + Common.sqlChar(new Datetime().changeTaiwanYYMMDD(getQ_enterDateE(),"2")) ;
	if (!"".equals(Common.get(getQ_propertyKind()))&&"00".equals(Common.get(getQ_propertyKind())))
		sql += " and a.propertykind < '04' ";
	else if (!"".equals(Common.get(getQ_propertyKind())))
		sql += " and a.propertykind = "+ util.Common.sqlChar(getQ_propertyKind());
	if (!"".equals(getQ_fundType()))
		sql+=" and a.fundtype = " + Common.sqlChar(getQ_fundType()) ;
	if (!"".equals(getQ_valuable()))
		sql+=" and a.valuable = " + Common.sqlChar(getQ_valuable()) ;
	if (!"".equals(getQ_bookValueS()))
		sql+=" and a.bookvalue >= " + Common.sqlChar(getQ_bookValueS()) ;
	if (!"".equals(getQ_bookValueE()))
		sql+=" and a.bookvalue <= " + Common.sqlChar(getQ_bookValueE()) ;
	if (!"".equals(getQ_fundsSource()))
		sql+=" and a.fundssource = " + Common.sqlChar(getQ_fundsSource()) ;
	if (!"".equals(getQ_sourceKind()))
		sql+=" and a.sourcekind = " + Common.sqlChar(getQ_sourceKind()) ;
	if(!"".equals(getQ_differenceKind()))
		sql+=" and a.differencekind = " + Common.sqlChar(getQ_differenceKind()) ;
	if (!"".equals(getQ_propertyName1()))
		sql+=" and a.propertyname1 like " + Common.sqlChar("%"+getQ_propertyName1()+"%") ;	
	if(!"".equals(getQ_keepUnit()))
		sql+=" and b.keepunit = " + Common.sqlChar(getQ_keepUnit()) ;
	if(!"".equals(getQ_keeper()))
		sql+=" and b.keeper = " + Common.sqlChar(getQ_keeper()) ;
	if(!"".equals(getQ_useUnit()))
		sql+=" and b.useunit = " + Common.sqlChar(getQ_useUnit()) ;
	if(!"".equals(getQ_userNo()))
		sql+=" and b.userno = " + Common.sqlChar(getQ_userNo()) ;
	if(!"".equals(getQ_userNote()))
		sql+=" and b.usernote like " + Common.sqlChar("%" + getQ_userNote() + "%");
	if(!"".equals(getQ_place()))
		sql+=" and b.place1 = " + Common.sqlChar(getQ_place());
	if(!"".equals(getQ_placeNote()))
		sql+=" and b.place like " + Common.sqlChar("%" + getQ_placeNote() + "%");

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
		String sql="select a.enterorg,"
				+ " a.ownership,"
				+ " a.propertyno,"
				+ " b.serialno,"
				+ " d.organsname as enterorgname,"
				+ " (select z.codename from SYSCA_CODE z where 1=1 and (z.codekindid='owa' or z.codekindid='OWA') and z.codeid=a.ownership) as ownershipname,"
				+ " e.propertyname"
				+ " from UNTNE_NONEXP a"
				+ " left join UNTNE_NONEXPDETAIL b"
				+ " on  a.enterorg=b.enterorg and "
				+ " 	a.ownership=b.ownership and "
				+ " 	a.differencekind=b.differencekind and"
				+ " 	a.propertyno=b.propertyno and " + " 	a.lotno=b.lotno"
				+ " left join UNTNE_ATTACHMENT2 c"
				+ " on  b.enterorg=c.enterorg and "
				+ " 	b.ownership=c.ownership and"
				+ " 	b.differencekind=c.differencekind and"
				+ " 	b.propertyno=c.propertyno and "
				+ " 	b.serialno=c.serialno"
				+ " left join SYSCA_ORGAN d on a.enterorg = d.organid"
				+ " left join SYSPK_PROPERTYCODE2 e on a.enterorg = e.enterorg and a.propertyno = e.propertyno"
				+ " where 1=1 " 
				+ " and b.datastate = " + Common.sqlChar(getQ_dataState()) 
				+ " and a.verify = 'Y'";
		
		sql=getQuerySQL(sql,0);
		String errMsg="";
		ResultSet rs = db.querySQL_NoChange(sql);
		System.out.println("exec:"+sql);
		while (rs.next()){
			counter++;
			String rowArray[]=new String[8];
			rowArray[0]=rs.getString("enterorgname"); 
			rowArray[1]=rs.getString("ownershipname"); 
			rowArray[2]=rs.getString("propertyno")+"-"+rs.getString("serialno");
			rowArray[3]=rs.getString("propertyname");
			rowArray[4]=rs.getString("enterorg"); 
			rowArray[5]=rs.getString("ownership"); 
			rowArray[6]=rs.getString("propertyno");
			rowArray[7]=rs.getString("serialno");
			objList.add(rowArray);
		}
		setStateQueryAllSuccess();		
		if (counter<=0) {								
			super.setState("init");
		}
	} catch (Exception e) {
		e.printStackTrace();
	} finally {
		db.closeAll();
	}
	return objList;
}

public String getFieldList(String mgrOrg, String userOrg) throws Exception {
	String sStr = "";
	Database db = new Database();
	ResultSet rs = null;
	String sSQL = "";
	try {
		sSQL = "select engfieldname, zhfieldname from FIELDMAPPING where tablename='"+getTableName()+"' ";
		if (!checkGet(mgrOrg).equalsIgnoreCase(checkGet(userOrg))){
			sSQL += " and ismgr='N'";
		}
		sSQL += " order by orderby ";		
		rs = db.querySQL_NoChange(sSQL);
		while (rs.next()) {
			sStr += "<option value='" + rs.getString("engFieldName") + ":;:" + rs.getString("zhFieldName") + "'>" + rs.getString("zhFieldName") + "</option>";
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

		// 使用 XSSFWorkbook 產生 .xlsx
		try (XSSFWorkbook wb = new XSSFWorkbook()) {
			Sheet sheet1 = wb.createSheet("Sheet1");	
			Row row;
			Cell cell;
			//Print Excel Column (header)
			arrContext = strContext.split(";;");
			int headerRowCount = 0;
			for (i=0; i<arrContext.length;i++) {
				row = sheet1.createRow(headerRowCount++);				
				String[] arrValue = arrContext[i].split(",");
				for (j=0; j<arrValue.length; j++) {				
					cell = row.createCell(j);
					cell.setCellValue(arrValue[j]);			
				}
			}		

			if (!"".equals(strEngFields)) {
				
				String querySql=this.getExportAllSQL();
				querySql=getQuerySQL(querySql,1);
				//Retrive Data
				String sql = "select distinct " + strFields + " from ("+querySql+") a where 1=1";
				
				rs = db.querySQL_NoChange(sql);
				System.out.println("exportAllSQL :" +sql);

		        String uploadCaseID = new java.rmi.dgc.VMID().toString();
		        uploadCaseID = uploadCaseID.replaceAll(":","_");		
				
		        
		        ServletContext context = MyServletContext.getInstance().getServletContext();
		        File tempDirectory = new File(context.getInitParameter("filestoreLocation"));
				
		        
		        tempDirectory = new File(tempDirectory,uploadCaseID);
				tempDirectory.mkdirs();
				
				
				// 寫入資料列，從 headerRowCount 開始
				int rowIndex = headerRowCount;
				count = 0;
				while (rs.next() && count < 1048575) { // XSSF 上限約 1,048,576 rows；保留最後一列給 header
					row = sheet1.createRow(rowIndex++);
					for (j = 0; j < sDestField.length; j++) {
						cell = row.createCell(j);
						String value = rs.getString(j+1);
						cell.setCellValue(value == null ? "" : value);
					}
					count++;
				}

				// 輸出檔案為 .xlsx
				File outFile = new File(tempDirectory.getAbsoluteFile(), getFileName() + ".xlsx");
				try (FileOutputStream fout = new FileOutputStream(outFile)) {
					wb.write(fout);
					fout.flush();
				}
				
				this.setExcelFileName(outFile.getAbsolutePath());
				
				super.setState("exportAll");			
			}
		} // wb 自動關閉
	} catch (Exception e) {
		e.printStackTrace();
	} finally {
		
		db.closeAll();
	}
}

public  File exportToFile(Workbook workbook, String fileName) throws Exception {
	File outputFile = new File(Common.getTempDirectory(), fileName);
	FileOutputStream fos = null;
	try {
		fos = new FileOutputStream(outputFile);
		workbook.write(fos);
		fos.flush();
		return outputFile;
	} finally {
		if (fos != null) {
			try {
				fos.close();
			} catch (Exception e) {
				//ignore
			}
		}
	}
	
}

}
