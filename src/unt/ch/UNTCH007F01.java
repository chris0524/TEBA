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


import util.*;

public class UNTCH007F01 extends SuperBean{

String enterOrg;
String enterOrgName;
String ownership;
String differencekind;
String propertyNo;
String propertyName;
String serialNo;
String propertyName1;
String signNo;
String signNo1;
String signNo2;
String signNo3;
String signNo4;
String signNo5;
String propertyKind;
String fundType;
String valuable;
String taxCredit;
String grass;
String area;
String holdNume;
String holdDeno;
String holdArea;
String accountingTitle;
String bookUnit;
String bookValue;
String useSeparate;
String useKind;
String proofDoc; 
String field;
String sourceDate;
String useState;
String useState1 ;
String oldPropertyNo;
String oldPropertyName;
String oldSerialNo;
String originalBasis;
String originalDate;
String originalUnit;
String caseNo;
String changeCause;
String notes1;
String newUseSeparate;
String newUseKind;

String q_enterOrg; 
String q_ownership; 
String q_caseNo;
String q_differenceKind; 
String q_reduceDate;
String q_verify;

String q_cause;
String q_cause1;
String q_newEnterOrg;
String q_newEnterOrgName;
String q_transferDate;
String q_dataState;
String q_propertyNoS;
String q_propertyNoE;
String q_propertyNoSName;
String q_propertyNoEName;
String q_serialNoS;
String q_serialNoE;
String q_signNo1;
String q_signNo2;
String q_signNo3;
String q_signNo4S;
String q_signNo4E;
String q_signNo5S;
String q_signNo5E;
String q_signNo = "";
String q_propertyKind;
String q_fundType;
String q_valuable;
String q_taxCredit;
String q_grass;


String sSQL = "";
String strKeySet[] = null;

String toggleAll;
public String getToggleAll(){ if (!"".equals(checkGet(toggleAll))) return "checked"; else return "unchecked"; }    
public void setToggleAll(String s){ toggleAll=checkSet(s); }    

public String getsSQL(){ return checkGet(sSQL); }
public void setsSQL(String s){ sSQL=checkSet(s); }
public String[] getsKeySet(){ return strKeySet; }
public void setsKeySet(String[] s){ strKeySet=s; }
public String getEnterOrg(){ return checkGet(enterOrg); }
public void setEnterOrg(String s){ enterOrg=checkSet(s); }
public String getEnterOrgName(){ return checkGet(enterOrgName); }
public void setEnterOrgName(String s){ enterOrgName=checkSet(s); }
public String getOwnership(){ return checkGet(ownership); }
public void setOwnership(String s){ ownership=checkSet(s); }
public String getDifferencekind(){ return checkGet(differencekind); }
public void setDifferencekind(String s){ differencekind=checkSet(s); }
public String getPropertyNo(){ return checkGet(propertyNo); }
public void setPropertyNo(String s){ propertyNo=checkSet(s); }
public String getPropertyName(){ return checkGet(propertyName); }
public void setPropertyName(String s){ propertyName=checkSet(s); }
public String getSerialNo(){ return checkGet(serialNo); }
public void setSerialNo(String s){ serialNo=checkSet(s); }
public String getPropertyName1(){ return checkGet(propertyName1); }
public void setPropertyName1(String s){ propertyName1=checkSet(s); }

public String getSignNo(){ return checkGet(signNo); }
public void setSignNo(String s){ signNo=checkSet(s); }
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

public String getPropertyKind(){ return checkGet(propertyKind); }
public void setPropertyKind(String s){ propertyKind=checkSet(s); }
public String getFundType(){ return checkGet(fundType); }
public void setFundType(String s){ fundType=checkSet(s); }
public String getValuable(){ return checkGet(valuable); }
public void setValuable(String s){ valuable=checkSet(s); }
public String getTaxCredit(){ return checkGet(taxCredit); }
public void setTaxCredit(String s){ taxCredit=checkSet(s); }
public String getGrass(){ return checkGet(grass); }
public void setGrass(String s){ grass=checkSet(s); }
public String getArea(){ return checkGet(area); }
public void setArea(String s){ area=checkSet(s); }
public String getHoldNume(){ return checkGet(holdNume); }
public void setHoldNume(String s){ holdNume=checkSet(s); }
public String getHoldDeno(){ return checkGet(holdDeno); }
public void setHoldDeno(String s){ holdDeno=checkSet(s); }
public String getHoldArea(){ return checkGet(holdArea); }
public void setHoldArea(String s){ holdArea=checkSet(s); }
public String getAccountingTitle(){ return checkGet(accountingTitle); }
public void setAccountingTitle(String s){ accountingTitle=checkSet(s); }
public String getBookUnit(){ return checkGet(bookUnit); }
public void setBookUnit(String s){ bookUnit=checkSet(s); }
public String getBookValue(){ return checkGet(bookValue); }
public void setBookValue(String s){ bookValue=checkSet(s); }
public String getUseSeparate(){ return checkGet(useSeparate); }
public void setUseSeparate(String s){ useSeparate=checkSet(s); }
public String getUseKind(){ return checkGet(useKind); }
public void setUseKind(String s){ useKind=checkSet(s); }
public String getProofDoc(){ return checkGet(proofDoc); }
public void setProofDoc(String s){ proofDoc=checkSet(s); }
public String getField(){ return checkGet(field); }
public void setField(String s){ field=checkSet(s); }
public String getSourceDate(){ return checkGet(sourceDate); }
public void setSourceDate(String s){ sourceDate=checkSet(s); }
public String getUseState(){ return checkGet(useState); }
public void setUseState(String s){ useState=checkSet(s); }
public String getUseState1(){ return checkGet(useState1); }
public void setUseState1(String s){ useState1=checkSet(s); }
public String getOldPropertyNo(){ return checkGet(oldPropertyNo); }
public void setOldPropertyNo(String s){ oldPropertyNo=checkSet(s); }
public String getOldPropertyName(){ return checkGet(oldPropertyName); }
public void setOldPropertyName(String s){ oldPropertyName=checkSet(s); }
public String getOldSerialNo(){ return checkGet(oldSerialNo); }
public void setOldSerialNo(String s){ oldSerialNo=checkSet(s); }
public String getOriginalBasis(){ return checkGet(originalBasis); }
public void setOriginalBasis(String s){ originalBasis=checkSet(s); }
public String getOriginalDate(){ return checkGet(originalDate); }
public void setOriginalDate(String s){ originalDate=checkSet(s); }
public String getOriginalUnit(){ return checkGet(originalUnit); }
public void setOriginalUnit(String s){ originalUnit=checkSet(s); }
public String getCaseNo(){ return checkGet(caseNo); }
public void setCaseNo(String s){ caseNo=checkSet(s); }
public String getChangeCause(){ return checkGet(changeCause); }
public void setChangeCause(String s){ changeCause=checkSet(s); }
public String getNotes1(){ return checkGet(notes1); }
public void setNotes1(String s){ notes1=checkSet(s); }
public String getNewUseSeparate(){ return checkGet(newUseSeparate); }
public void setNewUseSeparate(String s){ newUseSeparate=checkSet(s); }
public String getNewUseKind(){ return checkGet(newUseKind); }
public void setNewUseKind(String s){ newUseKind=checkSet(s); }

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
public String getQ_signNo1(){ return checkGet(q_signNo1); }
public void setQ_signNo1(String s){ q_signNo1=checkSet(s); }
public String getQ_signNo2(){ return checkGet(q_signNo2); }
public void setQ_signNo2(String s){ q_signNo2=checkSet(s); }
public String getQ_signNo3(){ return checkGet(q_signNo3); }
public void setQ_signNo3(String s){ q_signNo3=checkSet(s); }
public String getQ_signNo4S(){ return checkGet(q_signNo4S); }
public void setQ_signNo4S(String s){ q_signNo4S=checkSet(s); }
public String getQ_signNo4E(){ return checkGet(q_signNo4E); }
public void setQ_signNo4E(String s){ q_signNo4E=checkSet(s); }

public String getQ_signNo5S(){ return checkGet(q_signNo5S); }
public void setQ_signNo5S(String s){ q_signNo5S=checkSet(s); }
public String getQ_signNo5E(){ return checkGet(q_signNo5E); }
public void setQ_signNo5E(String s){ q_signNo5E=checkSet(s); }
public String getQ_propertyKind(){ return checkGet(q_propertyKind); }
public void setQ_propertyKind(String s){ q_propertyKind=checkSet(s); }
public String getQ_fundType(){ return checkGet(q_fundType); }
public void setQ_fundType(String s){ q_fundType=checkSet(s); }
public String getQ_valuable(){ return checkGet(q_valuable); }
public void setQ_valuable(String s){ q_valuable=checkSet(s); }
public String getQ_taxCredit(){ return checkGet(q_taxCredit); }
public void setQ_taxCredit(String s){ q_taxCredit=checkSet(s); }
public String getQ_grass(){ return checkGet(q_grass); }
public void setQ_grass(String s){ q_grass=checkSet(s); }
public String getQ_caseNo(){ return checkGet(q_caseNo); }
public void setQ_caseNo(String s){ q_caseNo=checkSet(s); }
public String getQ_reduceDate(){ return checkGet(q_reduceDate); }
public void setQ_reduceDate(String s){ q_reduceDate=checkSet(s); }
public String getQ_verify(){ return checkGet(q_verify); }
public void setQ_verify(String s){ q_verify=checkSet(s); }
public String getQ_cause(){ return checkGet(q_cause); }
public void setQ_cause(String s){ q_cause=checkSet(s); }
public String getQ_cause1(){ return checkGet(q_cause1); }
public void setQ_cause1(String s){ q_cause1=checkSet(s); }
public String getQ_newEnterOrg(){ return checkGet(q_newEnterOrg); }
public void setQ_newEnterOrg(String s){ q_newEnterOrg=checkSet(s); }
public String getQ_newEnterOrgName(){ return checkGet(q_newEnterOrgName); }
public void setQ_newEnterOrgName(String s){ q_newEnterOrgName=checkSet(s); }
public String getQ_transferDate(){ return checkGet(q_transferDate); }
public void setQ_transferDate(String s){ q_transferDate=checkSet(s); }
public String getQ_signNo(){ return checkGet(q_signNo); }
public void setQ_signNo(String s){ q_signNo=checkSet(s); }
public String getQ_enterOrg(){ return checkGet(q_enterOrg); }
public void setQ_enterOrg(String s){ q_enterOrg=checkSet(s); }
public String getQ_ownership(){ return checkGet(q_ownership); }
public void setQ_ownership(String s){ q_ownership=checkSet(s); }
public String getQ_differenceKind(){ return checkGet(q_differenceKind); }
public void setQ_differenceKind(String s){ q_differenceKind=checkSet(s); }

String q_useSeparate ;
String q_useKind ;
public String getQ_useSeparate(){ return checkGet(q_useSeparate); }
public void setQ_useSeparate(String s){ q_useSeparate=checkSet(s); }
public String getQ_useKind(){ return checkGet(q_useKind); }
public void setQ_useKind(String s){ q_useKind=checkSet(s); }

String closing;
public String getClosing(){ return checkGet(closing); }
public void setClosing(String s){ closing=checkSet(s); }


String q_jsp;
public String getQ_jsp() {return checkGet(q_jsp);}
public void setQ_jsp(String qJsp) {q_jsp = checkSet(qJsp);}

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
	String[] strKeys = null;

	ResultSet rs = null;
	try {

		for (int i=0; i<getcut; i++) {	
			strKeys = getsKeySet()[i].split(",");
			int serial1 = getMaxSerialNo1(strKeys);
			//先找出 Land 的值
			String landsql = " select *" + 
							 " from UNTLA_Land "+
							 " where enterOrg = " + Common.sqlChar(strKeys[0]) +
							 " and ownership = " + Common.sqlChar(strKeys[1]) +
							 " and differencekind = " + Common.sqlChar(strKeys[2]) +
							 " and propertyNo = " + Common.sqlChar(strKeys[3]) +
							 " and serialNo = " + Common.sqlChar(strKeys[4]) +
							 "";
			rs = db.querySQL(landsql);
			if (rs.next()){
				sbSQL.append(" insert into UNTLA_USESEPARATE(" ).append(
						" enterorg,").append(
						" ownership,").append(
						" differencekind,").append(
						" propertyno,").append(
						" serialno,").append(
						" serialno1,").append(
						" signno,").append(
						" changedate,").append(
						" changecause,").append(
						" changeitem,").append(
						" olduseseparate,").append(
						" oldusekind,").append(
						" newuseseparate,").append(
						" newusekind,").append(
						" notes1,").append(
						" oldpropertyno,").append(
						" oldserialno,").append(
						" editid,").append(
						" editdate,").append(
						" edittime").append(
					  ") values ( ").append( 
						Common.sqlChar(rs.getString("enterorg"))								).append( "," ).append(
						Common.sqlChar(rs.getString("ownership"))								).append( "," ).append(
						Common.sqlChar(rs.getString("differencekind"))							).append( "," ).append(
						Common.sqlChar(rs.getString("propertyno"))								).append( "," ).append(
						Common.sqlChar(rs.getString("serialno"))								).append( "," ).append(
						Common.sqlChar("" + serial1)											).append( "," ).append(
						Common.sqlChar(rs.getString("signno"))									).append( "," ).append(
						Common.sqlChar(Datetime.changeTaiwanYYMMDD(getQ_transferDate(), "2"))	).append( "," ).append(
						Common.sqlChar(getChangeCause())										).append( "," ).append(
						Common.sqlChar(getChangeItem(rs))										).append( "," ).append(
						Common.sqlChar(rs.getString("useseparate"))								).append( "," ).append(
						Common.sqlChar(rs.getString("usekind"))									).append( "," ).append(
						Common.sqlChar(getNewUseSeparate())										).append( "," ).append(
						Common.sqlChar(getNewUseKind())											).append( "," ).append(
						Common.sqlChar(getNotes1())												).append( "," ).append(
						Common.sqlChar(rs.getString("oldpropertyno"))							).append( "," ).append(
						Common.sqlChar(rs.getString("oldserialno"))								).append( "," ).append(
						Common.sqlChar(getEditID())                   ).append( "," ).append(
						Common.sqlChar(getEditDate())                 ).append( "," ).append(
						Common.sqlChar(getEditTime())                 ).append(
						" ):;:");
				sbSQL.append(" update UNTLA_LAND").append(
						" set").append(
						" useseparate =").append(Common.sqlChar(getNewUseSeparate())).append(",").append(
						" usekind =").append(Common.sqlChar(getNewUseKind())).append(
						" where enterorg = ").append(Common.sqlChar(strKeys[0])).append(
						" and ownership = ").append(Common.sqlChar(strKeys[1])).append(
						" and differencekind = ").append(Common.sqlChar(strKeys[2])).append(
						" and propertyno = ").append(Common.sqlChar(strKeys[3])).append(
						" and serialno = ").append(Common.sqlChar(strKeys[4])).append(
						" :;:");
			}
		}
	} catch (Exception e) {
		e.printStackTrace();
	} finally {
		db.closeAll();
	}
	setStateInsertSuccess();
	System.out.println("sbSQL=" + sbSQL.toString());
	return sbSQL.toString().split(":;:");
}

/**
 * <br>
 * <br>目的：依主鍵查詢單一資料
 * <br>參數：無
 * <br>傳回：傳回本物件
*/

public UNTCH007F01  queryOne() throws Exception{
	Database db = new Database();
	UNTCH007F01 obj = this;
	try {
		String sql=" select a.enterOrg, a.ownership, a.caseNo, a.propertyNo, " +
			" a.serialNo, a.propertyName1, a.signNo,  " +
			" a.propertyKind, a.fundType, " + 
			" a.valuable, a.taxCredit, a.grass, a.area, a.holdNume, a.holdDeno, a.holdArea, " +
			" a.accountingTitle, a.bookUnit, a.bookValue, a.useSeparate, " +
			" a.useKind, a.proofDoc, a.field, a.sourceDate, a.useState, (case a.useState1 when '01' then '空置' when '02' then '建物' when '03' then '農作' when '04' then '其他' end)as useState1, " +
			" a.oldPropertyNo, a.oldSerialNo, " +
			" a.originalBasis , a.originalDate , a.originalUnit , " +
			" c.organSName as enterOrgName, d.propertyName, " +
			" (select e.propertyName from SYSPK_PropertyCode e where a.oldpropertyNo = e.propertyNo)as oldpropertyName, "+
			" a.editID,a.editDate,a.editTime " +
			" from UNTLA_Land a, SYSCA_ORGAN c, "+
			" SYSPK_PropertyCode d where 1=1 " +
			" and a.enterOrg 	= " + Common.sqlChar(getEnterOrg()) +
			" and a.ownership 	= " + Common.sqlChar(getOwnership()) +
			" and a.ownership 	= " + Common.sqlChar(getDifferencekind()) +
			" and a.propertyNo 	= " + Common.sqlChar(getPropertyNo()) +
			" and a.serialNo 	= " + Common.sqlChar(getSerialNo()) +
			" and a.enterOrg	= c.organID " +
			" and a.propertyNo 	= d.propertyNo "+
			" order by a.signNo " +
			"";
	
		//System.out.print("======queryOne  :  "+sql);
		ResultSet rs = db.querySQL(sql);
		if (rs.next()){
			obj.setEnterOrg(rs.getString("enterOrg"));
			obj.setEnterOrgName(rs.getString("enterOrgName"));
			obj.setOwnership(rs.getString("ownership"));
			obj.setPropertyNo(rs.getString("propertyNo"));
			obj.setPropertyName(rs.getString("propertyName"));
			obj.setSerialNo(rs.getString("serialNo"));
			obj.setPropertyName1(rs.getString("propertyName1"));
			obj.setPropertyKind(rs.getString("propertyKind"));
			obj.setFundType(rs.getString("fundType"));
			obj.setValuable(rs.getString("valuable"));
			obj.setTaxCredit(rs.getString("taxCredit"));
			obj.setGrass(rs.getString("grass"));
			obj.setArea(rs.getString("area"));
			obj.setHoldNume(rs.getString("holdNume"));
			obj.setHoldDeno(rs.getString("holdDeno"));
			obj.setHoldArea(rs.getString("holdArea"));
			obj.setAccountingTitle(rs.getString("accountingTitle"));
			obj.setBookUnit(rs.getString("bookUnit"));
			obj.setBookValue(rs.getString("bookValue"));
			obj.setUseSeparate(rs.getString("useSeparate"));
			obj.setUseKind(rs.getString("useKind"));
			obj.setProofDoc(rs.getString("proofDoc"));
			obj.setField(rs.getString("field"));
			obj.setSourceDate(rs.getString("sourceDate"));
			obj.setUseState(rs.getString("useState"));
			obj.setUseState1(rs.getString("useState1"));
			obj.setOldPropertyNo(rs.getString("oldPropertyNo"));
			obj.setOldPropertyName(rs.getString("oldpropertyName"));
			obj.setOldSerialNo(rs.getString("oldSerialNo"));
			obj.setEditID(rs.getString("editID"));
			obj.setEditDate(rs.getString("editDate"));
			obj.setEditTime(rs.getString("editTime"));
			obj.setOriginalBasis(rs.getString("originalBasis"));
			obj.setOriginalDate(rs.getString("originalDate"));
			obj.setOriginalUnit(rs.getString("originalUnit"));
			obj.setSignNo(rs.getString("signNo"));
			obj.setSignNo1(rs.getString("signNo").substring(0,1)+"000000");
			obj.setSignNo2(rs.getString("signNo").substring(0,3)+"0000");
			obj.setSignNo3(rs.getString("signNo").substring(0,7));
			obj.setSignNo4(rs.getString("signNo").substring(7,11));
			obj.setSignNo5(rs.getString("signNo").substring(11,15));
			
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
		String sql = " select" +
				" a.enterOrg," + "\n" +
				" a.differencekind," + "\n" +
				" a.ownership," + "\n" +
				" a.propertyNo," + "\n" +
				" a.serialNo," + "\n" +
				" (select (case x.codename when null then ' ' else x.codename end) from SYSCA_Code x where x.codeid = a.differencekind and x.codekindid = 'DFK') as differencekindName," + "\n" +
				" (select (case x.codename when null then ' ' else x.codename end) from SYSCA_Code x where x.codeid = a.ownership and x.codekindid = 'OWA') as ownershipName," + "\n" +
				" d.propertyName," + "\n" +
				" a.caseNo," + "\n" +
				" a.area," + "\n" +
				" a.holdNume," + "\n" +
				" a.holdDeno," + "\n" +
				" (select (case x.codename when null then ' ' else x.codename end) from SYSCA_Code x where x.codeid = a.useSeparate and x.codekindid = 'SEP') as useSeparate," + "\n" +
				" (select y.codename from SYSCA_Code y where y.codeid = a.useKind and y.codekindid = 'UKD') as useKind, " + "\n" +
				" a.signNo ," +
				" a.propertyKind ," +
				" a.holdArea," + "\n" +
				" a.bookValue," + "\n" +
				" a.sourceDate," + "\n" +
				" (select y.codename from SYSCA_Code y where y.codeid = a.useState and y.codekindid = 'UST' ) as useState" + "\n" +
				" from UNTLA_Land a ,SYSCA_Organ c ,SYSPK_PropertyCode d " + "\n" +
				" where 1=1 " + "\n" +
				" and a.enterOrg = c.organID " + "\n" +
				" and a.propertyNo = d.propertyNo " + "\n" +
				" and a.enterorg + a.ownership + a.differencekind + a.propertyno + a.serialno  not in (select enterorg + ownership + differencekind + propertyno + serialno from UNTLA_REDUCEDETAIL where 1=1) " + "\n" +
				" and a.enterorg + a.ownership + a.differencekind + a.propertyno + a.serialno  not in (select enterorg + ownership + differencekind + propertyno + serialno from UNTLA_ADJUSTDETAIL where verify='N') " + "\n" +
//				" and a.enterOrg not in (select enterOrg from UNTLA_REDUCEDETAIL where 1=1) " + "\n" +
//				" and a.ownership not in (select ownership from UNTLA_REDUCEDETAIL where 1=1) " + "\n" +
//				" and a.propertyNo not in (select propertyNo from UNTLA_REDUCEDETAIL where 1=1) " + "\n" +
//				" and a.serialNo not in (select serialNo from UNTLA_REDUCEDETAIL where 1=1) " + "\n" +
//				" and a.enterOrg not in (select enterOrg from UNTLA_AdjustDetail where verify='N') " + "\n" +
//				" and a.ownership not in (select ownership from UNTLA_AdjustDetail where verify='N') " + "\n" +
//				" and a.propertyNo not in (select propertyNo from UNTLA_AdjustDetail where verify='N') " + "\n" +
//				" and a.serialNo  not in (select serialNo from UNTLA_AdjustDetail where verify='N') " + "\n" +
				" and a.dataState = " + Common.sqlChar(q_dataState) + "\n" +
				" and a.verify = 'Y' " + "\n";

			if (!"".equals(getQ_enterOrg())) {
				sql+=" and a.enterOrg = " + Common.sqlChar(getQ_enterOrg()) ;
			}
			if (!"".equals(getQ_ownership())) {
				sql+=" and a.ownership = " + Common.sqlChar(getQ_ownership()) ;
			}
			if (!"".equals(getQ_differenceKind())) {
				sql+=" and a.differenceKind = " + Common.sqlChar(getQ_differenceKind()) ;
			}
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
			if (!"".equals(getQ_signNo4S())){
				setQ_signNo4S(Common.formatFrontZero(getQ_signNo4S(),4));
				setQ_signNo5S(Common.formatFrontZero(getQ_signNo5S(),4));	
				if ("".equals(q_signNo)){
					q_signNo="_______"+getQ_signNo4S()+getQ_signNo5S();
				}else{
					q_signNo=q_signNo+getQ_signNo4S()+getQ_signNo5S();				
				}
			}	

			if (!"".equals(q_signNo))
				sql+=" and a.signNo like '" + q_signNo + "%'" ;	

			if(!"".equals(getQ_signNo4S())){
			    setQ_signNo4S(Common.formatFrontZero(getQ_signNo4S(),4));
			    sql+=" and subStr(a.signNo,8,4) >="+ getQ_signNo4S();	
			}
			if(!"".equals(getQ_signNo4E())){
				setQ_signNo4E(Common.formatFrontZero(getQ_signNo4E(),4));
				sql+=" and subStr(a.signNo,8,4) <="+ getQ_signNo4E();	
			}
			
			if(!"".equals(getQ_signNo5S())){
				setQ_signNo5S(Common.formatFrontZero(getQ_signNo5S(),4));
				sql+=" and subStr(a.signNo,12,4) >="+ getQ_signNo5S();	
			}
			if(!"".equals(getQ_signNo5E())){
				setQ_signNo5E(Common.formatFrontZero(getQ_signNo5E(),4));
				sql+=" and subStr(a.signNo,12,4) <="+ getQ_signNo5E();	
			}		
			
			if (!"".equals(getQ_propertyKind()))
				sql+=" and a.propertyKind = " + Common.sqlChar(getQ_propertyKind()) ;
			if (!"".equals(getQ_fundType()))
				sql+=" and a.fundType = " + Common.sqlChar(getQ_fundType()) ;
			if (!"".equals(getQ_valuable()))
				sql+=" and a.valuable = " + Common.sqlChar(getQ_valuable()) ;
			if (!"".equals(getQ_taxCredit()))
				sql+=" and a.taxCredit = " + Common.sqlChar(getQ_taxCredit()) ;
			if (!"".equals(getQ_grass()))
				sql+=" and a.grass = " + Common.sqlChar(getQ_grass()) ;
			
			if (!"".equals(getQ_useSeparate()))
				sql+=" and a.useSeparate = " + Common.sqlChar(getQ_useSeparate()) ;
			if (!"".equals(getQ_useKind()))
				sql+=" and a.useKind = " + Common.sqlChar(getQ_useKind()) ;
			
			sql+=" order by a.signNo ";

		//System.out.println("===queryAll:"+sql);

		ResultSet rs = db.querySQL(sql);
		//把查到的資料放入list裡..
		//和.jsp裡的 List區 內容要搭配起來
		while (rs.next()){

			counter++;
			String rowArray[]=new String[14];
			rowArray[0]=rs.getString("enterOrg");
			rowArray[1]=rs.getString("ownership");
			rowArray[2]=rs.getString("differencekind"); 
			rowArray[3]=rs.getString("ownershipName");
			rowArray[4]=rs.getString("differencekindName");
			rowArray[5]=rs.getString("propertyNo"); 
			rowArray[6]=rs.getString("serialNo"); 
			rowArray[7]=rs.getString("propertyName");
			if (rs.getString("signNo") == null || rs.getString("signNo").length() == 0) {
				rowArray[8]="";
			} else {
				rowArray[8]=getSignDescName(rs.getString("signNo").substring(0,7))+rs.getString("signNo").substring(7,11)+"-"+rs.getString("signNo").substring(11); 
			}
			rowArray[9]=rs.getString("area");
			rowArray[10]=rs.getString("holdNume");
			rowArray[11]=rs.getString("holdDeno");
			if(rs.getString("useSeparate")== null )
			{
				rowArray[12]="" ;
			}
			else
			{
				rowArray[12]=rs.getString("useSeparate");
			}
			if(rs.getString("useKind")== null )
			{
				rowArray[13]="" ;
			}
			else
			{
				rowArray[13]=rs.getString("useKind");
			}
			
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

//取得untla_value欄位資料
protected String[] getValue(String penterOrg,String pownership,String pdifferencekind,String ppropertyNo,String pserialNo,String poriginalDate,String poriginalBasis,String poriginalUnit){	
    Database db = new Database();
	ResultSet rs;
	String rtndata[]=new String[2];
	//String differ = "";
	//String bulletinDate = "";
	String check="";
	rtndata[0] = "";
	String sql= " select x.suitDateS, x.valueUnit"+
				" from UNTLA_Value x "+
				" where x.enterOrg =" + Common.sqlChar(penterOrg) + 
				" and x.ownership = " + Common.sqlChar(pownership) + 
				" and x.differencekind = " + Common.sqlChar(pdifferencekind) + 
				" and x.propertyNo= " + Common.sqlChar(ppropertyNo) +
				" and x.serialNo= " + Common.sqlChar(pserialNo) +
				" and x.bulletindate =  "+
				" (select max(a.bulletindate) from UNTLA_Value a  "+
				" where a.enterOrg = x.enterOrg "+
				" and a.ownership = x.ownership "+
				" and a.differencekind = x.differencekind "+
				" and a.propertyNo = x.propertyNo "+
				" and a.serialNo = x.serialNo "+
				" ) ";
	String sql1= "select suitDateS from UNTLA_BulletinDate " +
				"where bulletinkind='1' and bulletinDate=" + Common.sqlChar(poriginalDate) +
				"";
	
	try {		
		rs = db.querySQL(sql);
		while (rs.next()){
		    check="Y";
			rtndata[0] = rs.getString("suitDateS");
			rtndata[1]  = rs.getString("valueUnit");
		}
		if(check==""){
			if(poriginalBasis != null && poriginalBasis.equals("1")){
				rs = db.querySQL(sql1);
				if(rs.next()){
					rtndata[0] = rs.getString("suitDateS");
					rtndata[1]  = poriginalUnit;
				}else{
					rtndata[0] = "";
					rtndata[1]  = poriginalUnit;
				}
			}else{
				rtndata[0] = "";
				rtndata[1]  = "";
			}
		}
	} catch (Exception e) {
		e.printStackTrace();
	} finally {
		db.closeAll();
	}
	return rtndata;
}

private int getMaxSerialNo1(String[] strKeys)  throws Exception{
	int serialno1 = 0;
	String sql = " select isnull(max(serialno1),0) + 1 as serialno1 from UNTLA_USESEPARATE" +
		 " where enterorg = " + Common.sqlChar(strKeys[0]) +
		 " and ownership = " + Common.sqlChar(strKeys[1]) +
		 " and differencekind = " + Common.sqlChar(strKeys[2]) +
		 " and propertyno = " + Common.sqlChar(strKeys[3]) +
		 " and serialno = " + Common.sqlChar(strKeys[4]) +
		 "";
	Database db = new Database();
	ResultSet rs = db.querySQL(sql);
	try {
		if (rs.next()) {
			serialno1 = rs.getInt("serialno1");
		}
	} catch(Exception e) {
		e.printStackTrace();
		throw e;
	} finally {
		if (rs != null) {
			rs.close();
		}
	}
	return serialno1;
}

//比對「原使用分區、原編定使用種類」與「新使用分區、新編定使用種類」，將有異動的項目寫入此欄位，項目與項目之間以「、」區隔
//範例：使用分區、編定使用種類
private String getChangeItem(ResultSet rs)  throws Exception{
	String ChangeItem = "";
	if (!getNewUseSeparate().equals(rs.getString("useSeparate"))) {
		ChangeItem = "使用分區";
	}
	if (!getNewUseKind().equals(rs.getString("useKind"))) {
		if ("".equals(ChangeItem)) {
			ChangeItem += "編定使用種類";
		} else {
			ChangeItem += "、編定使用種類";
		}
	}
	return ChangeItem;
}


}
