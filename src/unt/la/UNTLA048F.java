/*
*<br>程式目的：土地合併分割資料查詢
*<br>程式代號：UNTLA048F
*<br>程式日期：0940825
*<br>程式作者：clive.chang
*<br>--------------------------------------------------------
*<br>修改作者　　修改日期　　　修改目的
*<br>--------------------------------------------------------
*/

package unt.la;

import java.sql.ResultSet;
import java.util.ArrayList;
import util.*;
import TDlib_Simple.tools.src.StringTools;

public class UNTLA048F extends SuperBean{


String enterOrg;
String enterOrgName;
String ownership;
String ownershipName;
String caseNo;
String causeName;
String cause;
String cause1;
String enterDate;
String kindNo;
String kindNoName;
String caseNo1;
String writeDate;
String proofDoc;
String proofNo;
String propertyNo;
String propertyName;
String serialNo;
String signNo;
String signName;
String signNo1;
String signNo2;
String signNo3;
String signNo4;
String signNo5;
String area;
String holdNume;
String holdDeno;
String holdArea;
String bookUnit;
String bookValue;
String useSeparate;
String useSeparateName;
String useKind;
String useKindName;
String field;
String fieldName;
String oldPropertyNo;
String oldSerialNo;

String q_enterOrg;
String q_enterOrgName;
String q_ownership;
String q_caseNo;
String q_cause;
String q_enterDate;
String q_caseNo1;
String q_writeDate;
String q_proofNo;
String q_signNo1;
String q_signNo2;
String q_signNo3;
String q_signNo4;
String q_signNo5;
String q_signNo = "";
String q_caseNoS;
String q_caseNoE;
String q_enterDateS;
String q_enterDateE;
String q_caseNo1S;
String q_caseNo1E;
String q_writeDateS;
String q_writeDateE;
String q_proofNoS;
String q_proofNoE;
String q_kindNo;

public String getEnterOrg(){ return checkGet(enterOrg); }
public void setEnterOrg(String s){ enterOrg=checkSet(s); }
public String getEnterOrgName(){ return checkGet(enterOrgName); }
public void setEnterOrgName(String s){ enterOrgName=checkSet(s); }
public String getOwnership(){ return checkGet(ownership); }
public void setOwnership(String s){ ownership=checkSet(s); }
public String getOwnershipName(){ return checkGet(ownershipName); }
public void setOwnershipName(String s){ ownershipName=checkSet(s); }
public String getCaseNo(){ return checkGet(caseNo); }
public void setCaseNo(String s){ caseNo=checkSet(s); }
public String getCause(){ return checkGet(cause); }
public void setCause(String s){ cause=checkSet(s); }
public String getCauseName(){ return checkGet(causeName); }
public void setCauseName(String s){ causeName=checkSet(s); }
public String getCause1(){ return checkGet(cause1); }
public void setCause1(String s){ cause1=checkSet(s); }
public String getEnterDate(){ return checkGet(enterDate); }
public void setEnterDate(String s){ enterDate=checkSet(s); }
public String getKindNo(){ return checkGet(kindNo); }
public void setKindNo(String s){ kindNo=checkSet(s); }
public String getKindNoName(){ return checkGet(kindNoName); }
public void setKindNoName(String s){ kindNoName=checkSet(s); }
public String getCaseNo1(){ return checkGet(caseNo1); }
public void setCaseNo1(String s){ caseNo1=checkSet(s); }
public String getWriteDate(){ return checkGet(writeDate); }
public void setWriteDate(String s){ writeDate=checkSet(s); }
public String getProofDoc(){ return checkGet(proofDoc); }
public void setProofDoc(String s){ proofDoc=checkSet(s); }
public String getProofNo(){ return checkGet(proofNo); }
public void setProofNo(String s){ proofNo=checkSet(s); }
public String getPropertyNo(){ return checkGet(propertyNo); }
public void setPropertyNo(String s){ propertyNo=checkSet(s); }
public String getPropertyName(){ return checkGet(propertyName); }
public void setPropertyName(String s){ propertyName=checkSet(s); }
public String getSerialNo(){ return checkGet(serialNo); }
public void setSerialNo(String s){ serialNo=checkSet(s);}
public String getArea(){ return checkGet(area); }
public void setArea(String s){ area=checkSet(s); }
public String getHoldNume(){ return checkGet(holdNume); }
public void setHoldNume(String s){ holdNume=checkSet(s); }
public String getHoldDeno(){ return checkGet(holdDeno); }
public void setHoldDeno(String s){ holdDeno=checkSet(s); }
public String getHoldArea(){ return checkGet(holdArea); }
public void setHoldArea(String s){ holdArea=checkSet(s); }
public String getBookUnit(){ return checkGet(bookUnit); }
public void setBookUnit(String s){ bookUnit=checkSet(s); }
public String getBookValue(){ return checkGet(bookValue); }
public void setBookValue(String s){ bookValue=checkSet(s); }
public String getUseSeparate(){ return checkGet(useSeparate); }
public void setUseSeparate(String s){ useSeparate=checkSet(s); }
public String getUseKind(){ return checkGet(useKind); }
public void setUseKind(String s){ useKind=checkSet(s); }
public String getField(){ return checkGet(field); }
public void setField(String s){ field=checkSet(s); }
public String getUseSeparateName(){ return checkGet(useSeparateName); }
public void setUseSeparateName(String s){ useSeparateName=checkSet(s); }
public String getUseKindName(){ return checkGet(useKindName); }
public void setUseKindName(String s){ useKindName=checkSet(s); }
public String getFieldName(){ return checkGet(fieldName); }
public void setFieldName(String s){ fieldName=checkSet(s); }
public String getOldPropertyNo(){ return checkGet(oldPropertyNo); }
public void setOldPropertyNo(String s){ oldPropertyNo=checkSet(s); }
public String getOldSerialNo(){ return checkGet(oldSerialNo); }
public void setOldSerialNo(String s){ oldSerialNo=checkSet(s); }
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
public String getSignNo(){ return checkGet(signNo); }
public void setSignNo(String s){ signNo=checkSet(s); }
public String getSignName(){ return checkGet(signName); }
public void setSignName(String s){ signName=checkSet(s); }

public String getQ_enterOrg(){ return checkGet(q_enterOrg); }
public void setQ_enterOrg(String s){ q_enterOrg=checkSet(s); }
public String getQ_enterOrgName(){ return checkGet(q_enterOrgName); }
public void setQ_enterOrgName(String s){ q_enterOrgName=checkSet(s); }
public String getQ_ownership(){ return checkGet(q_ownership); }
public void setQ_ownership(String s){ q_ownership=checkSet(s); }
public String getQ_caseNo(){ return checkGet(q_caseNo); }
public void setQ_caseNo(String s){ q_caseNo=checkSet(s); }
public String getQ_cause(){ return checkGet(q_cause); }
public void setQ_cause(String s){ q_cause=checkSet(s); }
public String getQ_enterDate(){ return checkGet(q_enterDate); }
public void setQ_enterDate(String s){ q_enterDate=checkSet(s); }
public String getQ_caseNo1(){ return checkGet(q_caseNo1); }
public void setQ_caseNo1(String s){ q_caseNo1=checkSet(s); }
public String getQ_writeDate(){ return checkGet(q_writeDate); }
public void setQ_writeDate(String s){ q_writeDate=checkSet(s); }
public String getQ_proofNo(){ return checkGet(q_proofNo); }
public void setQ_proofNo(String s){ q_proofNo=checkSet(s); }


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

public String getQ_caseNoS(){ return checkGet(q_caseNoS); }
public void setQ_caseNoS(String s){ q_caseNoS=checkSet(s); }
public String getQ_caseNoE(){ return checkGet(q_caseNoE); }
public void setQ_caseNoE(String s){ q_caseNoE=checkSet(s); }
public String getQ_enterDateS(){ return checkGet(q_enterDateS); }
public void setQ_enterDateS(String s){ q_enterDateS=checkSet(s); }
public String getQ_enterDateE(){ return checkGet(q_enterDateE); }
public void setQ_enterDateE(String s){ q_enterDateE=checkSet(s); }
public String getQ_caseNo1S(){ return checkGet(q_caseNo1S); }
public void setQ_caseNo1S(String s){ q_caseNo1S=checkSet(s); }
public String getQ_caseNo1E(){ return checkGet(q_caseNo1E); }
public void setQ_caseNo1E(String s){ q_caseNo1E=checkSet(s); }
public String getQ_writeDateS(){ return checkGet(q_writeDateS); }
public void setQ_writeDateS(String s){ q_writeDateS=checkSet(s); }
public String getQ_writeDateE(){ return checkGet(q_writeDateE); }
public void setQ_writeDateE(String s){ q_writeDateE=checkSet(s); }
public String getQ_proofNoS(){ return checkGet(q_proofNoS); }
public void setQ_proofNoS(String s){ q_proofNoS=checkSet(s); }
public String getQ_proofNoE(){ return checkGet(q_proofNoE); }
public void setQ_proofNoE(String s){ q_proofNoE=checkSet(s); }
public String getQ_kindNo(){ return checkGet(q_kindNo); }
public void setQ_kindNo(String s){ q_kindNo=checkSet(s); }

//傳回執行insert前之檢查sql
protected String[][] getInsertCheckSQL(){
	String[][] checkSQLArray = new String[1][4];
 	checkSQLArray[0][0]=" select count(*) as checkResult from UNTLA_MERGEDIVISION1 where 1=1 " + 
		" and enterOrg = " + Common.sqlChar(enterOrg) + 
		" and ownership = " + Common.sqlChar(ownership) + 
		" and caseNo = " + Common.sqlChar(caseNo) + 
		" and kindNo = " + Common.sqlChar(kindNo) + 
		" and propertyNo = " + Common.sqlChar(propertyNo) + 
		" and serialNo = " + Common.sqlChar(serialNo) + 
		"";
	checkSQLArray[0][1]=">";
	checkSQLArray[0][2]="0";
	checkSQLArray[0][3]="該筆資料己重複，請重新輸入！";
	return checkSQLArray;
}


//傳回insert sql
protected String[] getInsertSQL(){
	String[] execSQLArray = new String[1];
	execSQLArray[0]=" insert into UNTLA_MERGEDIVISION1(" +
			" enterOrg,"+
			" ownership,"+
			" caseNo,"+
			" cause,"+
			" cause1,"+
			" enterDate,"+
			" kindNo,"+
			" caseNo1,"+
			" writeDate,"+
			" proofDoc,"+
			" proofNo,"+
			" propertyNo,"+
			" serialNo,"+
			" signNo,"+
			" area,"+
			" holdNume,"+
			" holdDeno,"+
			" holdArea,"+
			" bookUnit,"+
			" bookValue,"+
			" useSeparate,"+
			" useKind,"+
			" field,"+
			" oldPropertyNo,"+
			" oldSerialNo,"+
			" editID,"+
			" editDate,"+
			" editTime"+
		" )Values(" +
			Common.sqlChar(enterOrg) + "," +
			Common.sqlChar(ownership) + "," +
			Common.sqlChar(caseNo) + "," +
			Common.sqlChar(cause) + "," +
			Common.sqlChar(cause1) + "," +
			Common.sqlChar(enterDate) + "," +
			Common.sqlChar(kindNo) + "," +
			Common.sqlChar(caseNo1) + "," +
			Common.sqlChar(writeDate) + "," +
			Common.sqlChar(proofDoc) + "," +
			Common.sqlChar(proofNo) + "," +
			Common.sqlChar(propertyNo) + "," +
			Common.sqlChar(serialNo) + "," +
			Common.sqlChar(signNo) + "," +
			Common.sqlChar(area) + "," +
			Common.sqlChar(holdNume) + "," +
			Common.sqlChar(holdDeno) + "," +
			Common.sqlChar(holdArea) + "," +
			Common.sqlChar(bookUnit) + "," +
			Common.sqlChar(bookValue) + "," +
			Common.sqlChar(useSeparate) + "," +
			Common.sqlChar(useKind) + "," +
			Common.sqlChar(field) + "," +
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
	execSQLArray[0]=" update UNTLA_MERGEDIVISION1 set " +
			" cause = " + Common.sqlChar(cause) + "," +
			" cause1 = " + Common.sqlChar(cause1) + "," +
			" enterDate = " + Common.sqlChar(enterDate) + "," +
			" caseNo1 = " + Common.sqlChar(caseNo1) + "," +
			" writeDate = " + Common.sqlChar(writeDate) + "," +
			" proofDoc = " + Common.sqlChar(proofDoc) + "," +
			" proofNo = " + Common.sqlChar(proofNo) + "," +
			" signNo = " + Common.sqlChar(signNo) + "," +
			" area = " + Common.sqlChar(area) + "," +
			" holdNume = " + Common.sqlChar(holdNume) + "," +
			" holdDeno = " + Common.sqlChar(holdDeno) + "," +
			" holdArea = " + Common.sqlChar(holdArea) + "," +
			" bookUnit = " + Common.sqlChar(bookUnit) + "," +
			" bookValue = " + Common.sqlChar(bookValue) + "," +
			" useSeparate = " + Common.sqlChar(useSeparate) + "," +
			" useKind = " + Common.sqlChar(useKind) + "," +
			" field = " + Common.sqlChar(field) + "," +
			" oldPropertyNo = " + Common.sqlChar(oldPropertyNo) + "," +
			" oldSerialNo = " + Common.sqlChar(oldSerialNo) + "," +
			" editID = " + Common.sqlChar(getEditID()) + "," +
			" editDate = " + Common.sqlChar(getEditDate()) + "," +
			" editTime = " + Common.sqlChar(getEditTime()) + 
		" where 1=1 " + 
			" and enterOrg = " + Common.sqlChar(enterOrg) +
			" and ownership = " + Common.sqlChar(ownership) +
			" and caseNo = " + Common.sqlChar(caseNo) +
			" and kindNo = " + Common.sqlChar(kindNo) +
			" and propertyNo = " + Common.sqlChar(propertyNo) +
			" and serialNo = " + Common.sqlChar(serialNo) +
			"";
	return execSQLArray;
}


//傳回delete sql
protected String[] getDeleteSQL(){
	String[] execSQLArray = new String[1];
	execSQLArray[0]=" delete UNTLA_MERGEDIVISION1 where 1=1 " +
			" and enterOrg = " + Common.sqlChar(enterOrg) +
			" and ownership = " + Common.sqlChar(ownership) +
			" and caseNo = " + Common.sqlChar(caseNo) +
			" and kindNo = " + Common.sqlChar(kindNo) +
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

public UNTLA048F  queryOne() throws Exception{
	Database db = new Database();
	UNTLA048F obj = this;
	try {
		String sql=" select a.enterOrg, b.organSName as enterOrgName, a.ownership, a.caseNo, a.cause, a.cause1, a.enterDate, a.kindNo, a.caseNo1, a.writeDate, a.proofDoc, a.proofNo, a.propertyNo, e.propertyName, a.serialNo, a.signNo, TO_CHAR(a.area,'9999999.99') as area, a.holdNume, a.holdDeno, TO_CHAR(a.holdArea, '9999999.99') as holdArea, a.bookUnit, a.bookValue, a.useSeparate, a.useKind, a.field, a.oldPropertyNo, a.oldSerialNo, a.editID, a.editDate, a.editTime  "+
			", case a.ownership when '2' then '國有' else '市有' end as ownershipName "+
			", case a.kindNo when '1' then '合併前' when '2' then '合併後' when '3' then '分割前' when '4' then '分割後' else '' end as kindNoName "+			
			", a.signNo, d.codeName as causeName "+
			", (select f.codeName from SYSCA_CODE f where f.codeKindID='SEP' and f.codeID=a.useSeparate)as useSeparateName "+
			", (select g.codeName from SYSCA_CODE g where g.codeKindID='UKD' and g.codeID=a.useKind)as useKindName "+			
			", (select h.codeName from SYSCA_CODE h where h.codeKindID='FIE' and h.codeID=a.field)as fieldName "+
			" from UNTLA_MERGEDIVISION1 a, SYSCA_ORGAN b, SYSCA_CODE d, SYSPK_PropertyCode e where 1=1 "+			
			" and a.enterOrg=b.organID "+
			" and d.codeKindID='CAA' and d.codeID=a.cause "+			
			" and a.propertyNo=e.propertyNo "+
			" and a.enterOrg = " + Common.sqlChar(enterOrg) +
			" and a.ownership = " + Common.sqlChar(ownership) +
			" and a.caseNo = " + Common.sqlChar(caseNo) +
			" and a.kindNo = " + Common.sqlChar(kindNo) +
			" and a.propertyNo = " + Common.sqlChar(propertyNo) +
			" and a.serialNo = " + Common.sqlChar(serialNo) +
			"";	
		ResultSet rs = db.querySQL(sql);
		if (rs.next()){
			obj.setEnterOrg(rs.getString("enterOrg"));
			obj.setEnterOrgName(rs.getString("enterOrgName"));		
			obj.setQ_enterOrgName(rs.getString("enterOrgName"));			
			obj.setOwnership(rs.getString("ownership"));
			obj.setOwnershipName(rs.getString("ownershipName"));			
			obj.setCaseNo(rs.getString("caseNo"));
			obj.setCause(rs.getString("cause"));
			obj.setCauseName(rs.getString("causeName"));
			obj.setCause1(rs.getString("cause1"));
			obj.setEnterDate(rs.getString("enterDate"));
			obj.setKindNo(rs.getString("kindNo"));
			obj.setKindNoName(rs.getString("kindNoName"));			
			obj.setCaseNo1(rs.getString("caseNo1"));
			obj.setWriteDate(rs.getString("writeDate"));
			obj.setProofDoc(rs.getString("proofDoc"));
			obj.setProofNo(rs.getString("proofNo"));
			obj.setPropertyNo(rs.getString("propertyNo"));
			obj.setPropertyName(rs.getString("propertyName"));
			obj.setSerialNo(rs.getString("serialNo"));
			obj.setSignNo(rs.getString("signNo"));
			obj.setSignName(getSignDescName(rs.getString("signNo").substring(0,7)) + rs.getString("signNo").substring(7,11) + '-' + rs.getString("signNo").substring(11));			
			obj.setArea(rs.getString("area"));
			obj.setHoldNume(rs.getString("holdNume"));
			obj.setHoldDeno(rs.getString("holdDeno"));
			obj.setHoldArea(rs.getString("holdArea"));
			obj.setBookUnit(rs.getString("bookUnit"));
			obj.setBookValue(rs.getString("bookValue"));
			obj.setUseSeparate(rs.getString("useSeparate"));
			obj.setUseKind(rs.getString("useKind"));
			obj.setField(rs.getString("field"));
			obj.setUseSeparateName(rs.getString("useSeparateName"));
			obj.setUseKindName(rs.getString("useKindName"));
			obj.setFieldName(rs.getString("fieldName"));			
			obj.setOldPropertyNo(rs.getString("oldPropertyNo"));
			obj.setOldSerialNo(rs.getString("oldSerialNo"));
			obj.setEditID(rs.getString("editID"));
			obj.setEditDate(rs.getString("editDate"));
			obj.setEditTime(rs.getString("editTime"));

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
		String sql=" select a.enterOrg, b.organSName as enterOrgName, a.ownership, case a.ownership when '2' then '國有' else '市有' end as ownershipName "+
			", a.caseNo, a.cause, a.enterDate, a.kindNo, a.propertyNo, a.serialNo, a.signNo, a.signNo, a.holdArea "+
			", a.kindNo, case a.kindNo when '1' then '合併前' when '2' then '合併後' when '3' then '分割前' when '4' then '分割後' else '' end as kindNoName "+
			", d.codeName as causeName "+			
			" from UNTLA_MERGEDIVISION1 a, SYSCA_ORGAN b, SYSCA_CODE d where 1=1 "+			
			" and a.enterOrg=b.organID "+
			" and d.codeKindID='CAA' and d.codeID=a.cause "+
			"";
			
			if (!"".equals(getQ_enterOrg()))
				sql+=" and a.enterOrg = " + Common.sqlChar(getQ_enterOrg()) ;
			if (!"".equals(getQ_ownership()))
				sql+=" and a.ownership = " + Common.sqlChar(getQ_ownership()) ;

			if (!"".equals(getQ_cause()))
				sql+=" and a.cause = " + Common.sqlChar(getQ_cause()) ;
			
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
			
			if (!"".equals(getQ_enterDateS()) && !"".equals(getQ_enterDateE())) {
				sql+=" and a.enterDate between " + Common.sqlChar(new Datetime().changeTaiwanYYMMDD(getQ_enterDateS(),"2")) + " and " + Common.sqlChar(new Datetime().changeTaiwanYYMMDD(getQ_enterDateE(),"2"));		
			} else if (!"".equals(getQ_enterDateS())) {
				sql+=" and a.enterDate=" + Common.sqlChar(new Datetime().changeTaiwanYYMMDD(getQ_enterDateS(),"2"));
			}

			if (!"".equals(getQ_writeDateS()) && !"".equals(getQ_writeDateE())) {
				sql+=" and a.writeDate between " + Common.sqlChar(new Datetime().changeTaiwanYYMMDD(getQ_writeDateS(),"2")) + " and " + Common.sqlChar(new Datetime().changeTaiwanYYMMDD(getQ_writeDateE(),"2"));		
			} else if (!"".equals(getQ_writeDateS())) {
				sql+=" and a.writeDate=" + Common.sqlChar(new Datetime().changeTaiwanYYMMDD(getQ_writeDateS(),"2"));
			}

			if (!"".equals(getQ_caseNoS()) && !"".equals(getQ_caseNoE())) {
				sql+=" and a.caseNo between " + Common.sqlChar(formatAfter99(getQ_caseNoS(),10)) + " and " + Common.sqlChar(formatAfter99(getQ_caseNoE(),10));		
			} else if (!"".equals(getQ_caseNoS())) {
				sql+=" and a.caseNo=" + Common.sqlChar(formatAfter99(getQ_caseNoS(),10));
			}

			if (!"".equals(getQ_caseNo1S()) && !"".equals(getQ_caseNo1E())) {
				sql+=" and a.caseNo1 between " + Common.sqlChar(formatAfter99(getQ_caseNo1S(),10)) + " and " + Common.sqlChar(formatAfter99(getQ_caseNo1E(),10));		
			} else if (!"".equals(getQ_caseNo1S())) {
				sql+=" and a.caseNo1=" + Common.sqlChar(formatAfter99(getQ_caseNo1S(),10));
			}

			if (!"".equals(getQ_proofNoS()) && !"".equals(getQ_proofNoE())) {
				sql+=" and a.proofNo between " + Common.sqlChar(getQ_proofNoS()) + " and " + Common.sqlChar(getQ_proofNoE());		
			} else if (!"".equals(getQ_proofNoS())) {
				sql+=" and a.proofNo=" + Common.sqlChar(getQ_proofNoS());
			}
						
		StringTools st = new StringTools();
		ResultSet rs = db.querySQL(sql);
		while (rs.next()){
			counter++;
			String rowArray[]=new String[14];
			rowArray[0]=rs.getString("enterOrgName"); 
			rowArray[1]=rs.getString("ownershipName"); 
			rowArray[2]=rs.getString("caseNo"); 
			rowArray[3]=rs.getString("causeName"); 
			rowArray[4]=rs.getString("enterDate"); 
			rowArray[5]=rs.getString("kindNoName");
			rowArray[6]=getSignDescName(rs.getString("signNo").substring(0,7)) + rs.getString("signNo").substring(7,11) + '-' + rs.getString("signNo").substring(11);
			rowArray[7]=st._getDotFormat(rs.getString("holdArea"),2);
			rowArray[8]=rs.getString("enterOrg"); 
			rowArray[9]=rs.getString("ownership"); 
			rowArray[10]=rs.getString("caseNo"); 
			rowArray[11]=rs.getString("kindNo"); 
			rowArray[12]=rs.getString("propertyNo"); 
			rowArray[13]=rs.getString("serialNo");
						
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

/**
*<br>目的：將字串後面補9傳回字串
*<br>參數：(1)資料字串 (2)所需長度
*<br>傳回：傳回字串
*<br>範例：formatAfter99("123",5)，傳回"12399"
*/
static public String formatAfter99(String s,int len){
	String format="";
	int sLen=s.length();
	for(int i=0; i<(len-sLen) ; i++){
		format += "9";
	}
	format = s + format;
	return format;
}

static public String getNumOption(String strValue, String strLabel, String selectedOne) {
	StringBuffer rtnStr = new StringBuffer();
	String[] arrValue = strValue.split(",");
	String[] arrLabel = strLabel.split(",");
	rtnStr.append("<option value=''>請選擇</option>");
	for (int i=0; i<arrValue.length; i++) {
		rtnStr.append("<option value='" + arrValue[i] +"'");
		if (selectedOne.equalsIgnoreCase(arrValue[i])) { 
			rtnStr.append(" selected");
		}		
		rtnStr.append(">" + arrLabel[i]+ "</option>");
	}
    return rtnStr.toString();
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
