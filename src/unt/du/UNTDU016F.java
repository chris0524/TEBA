/*
*<br>程式目的：土地主檔資料修改
*<br>程式代號：
*<br>程式日期：0970711
*<br>程式作者：shan
*<br>--------------------------------------------------------
*<br>修改作者　　修改日期　　　修改目的
*<br>--------------------------------------------------------
*/

package unt.du;

import java.sql.ResultSet;
import java.util.ArrayList;
import util.*;

public class UNTDU016F extends QueryBean{

//主ｋｅｙ
String enterOrg;
String enterOrgName;
String ownership;
String caseNo;
String propertyNo;
String propertyNoName;
String lotNo;
String serialNo;

public String getEnterOrg(){ return checkGet(enterOrg); }
public void setEnterOrg(String s){ enterOrg=checkSet(s); }
public String getEnterOrgName(){ return checkGet(enterOrgName); }
public void setEnterOrgName(String s){ enterOrgName=checkSet(s); }
public String getOwnership(){ return checkGet(ownership); }
public void setOwnership(String s){ ownership=checkSet(s); }
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

//查詢
String q_enterOrg;
String q_enterOrgName;
String q_ownership;
String q_caseNo;
String q_propertyNo;
String q_serialNo;
String q_lotNo;
public String getQ_enterOrg(){ return checkGet(q_enterOrg); }
public void setQ_enterOrg(String s){ q_enterOrg=checkSet(s); }
public String getQ_enterOrgName(){ return checkGet(q_enterOrgName); }
public void setQ_enterOrgName(String s){ q_enterOrgName=checkSet(s); }
public String getQ_ownership(){ return checkGet(q_ownership); }
public void setQ_ownership(String s){ q_ownership=checkSet(s); }
public String getQ_caseNo(){ return checkGet(q_caseNo); }
public void setQ_caseNo(String s){ q_caseNo=checkSet(s); }
public String getQ_propertyNo(){ return checkGet(q_propertyNo); }
public void setQ_propertyNo(String s){ q_propertyNo=checkSet(s); }
public String getQ_serialNo(){ return checkGet(q_serialNo); }
public void setQ_serialNo(String s){ q_serialNo=checkSet(s); }
public String getQ_lotNo(){ return checkGet(q_lotNo); }
public void setQ_lotNo(String s){ q_lotNo=checkSet(s); }
String q_chengClass;
public String getQ_chengClass(){ return checkGet(q_chengClass); }
public void setQ_chengClass(String s){ q_chengClass=checkSet(s); }

//提供修改欄位
String enterDate;
String dataState;
String verify;
String closing;
public String getEnterDate(){ return checkGet(enterDate); }
public void setEnterDate(String s){ enterDate=checkSet(s); }
public String getDataState(){ return checkGet(dataState); }
public void setDataState(String s){ dataState=checkSet(s); }
public String getVerify(){ return checkGet(verify); }
public void setVerify(String s){ verify=checkSet(s); }
public String getClosing(){ return checkGet(closing); }
public void setClosing(String s){ closing=checkSet(s); }

String propertyName1;
String oldPropertyNo;
String oldSerialNo;
String oldPropertyName;
public String getPropertyName1(){ return checkGet(propertyName1); }
public void setPropertyName1(String s){ propertyName1=checkSet(s); }
public String getOldPropertyNo(){ return checkGet(oldPropertyNo); }
public void setOldPropertyNo(String s){ oldPropertyNo=checkSet(s); }
public String getOldSerialNo(){ return checkGet(oldSerialNo); }
public void setOldSerialNo(String s){ oldSerialNo=checkSet(s); }
public String getOldPropertyName(){ return checkGet(oldPropertyName); }
public void setOldPropertyName(String s){ oldPropertyName=checkSet(s); }
String signNo1;
String signNo2;
String signNo3;
String signNo4;
String signNo5;
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
String doorplate;
String grass;
public String getDoorplate(){ return checkGet(doorplate); }
public void setDoorplate(String s){ doorplate=checkSet(s); }
public String getGrass(){ return checkGet(grass); }
public void setGrass(String s){ grass=checkSet(s); }
String propertyKind;
String fundType;
public String getPropertyKind(){ return checkGet(propertyKind); }
public void setPropertyKind(String s){ propertyKind=checkSet(s); }
public String getFundType(){ return checkGet(fundType); }
public void setFundType(String s){ fundType=checkSet(s); }
String valuable;
String taxCredit;
public String getValuable(){ return checkGet(valuable); }
public void setValuable(String s){ valuable=checkSet(s); }
public String getTaxCredit(){ return checkGet(taxCredit); }
public void setTaxCredit(String s){ taxCredit=checkSet(s); }

String cause;
String cause1;
public String getCause(){ return checkGet(cause); }
public void setCause(String s){ cause=checkSet(s); }
public String getCause1(){ return checkGet(cause1); }
public void setCause1(String s){ cause1=checkSet(s); }
String fundsSource;
String fundsSource1;
String accountingTitle;
public String getFundsSource(){ return checkGet(fundsSource); }
public void setFundsSource(String s){ fundsSource=checkSet(s); }
public String getFundsSource1(){ return checkGet(fundsSource1); }
public void setFundsSource1(String s){ fundsSource1=checkSet(s); }
public String getAccountingTitle(){ return checkGet(accountingTitle); }
public void setAccountingTitle(String s){ accountingTitle=checkSet(s); }
String sourceKind;
String sourceDate;
String sourceDoc;
public String getSourceKind(){ return checkGet(sourceKind); }
public void setSourceKind(String s){ sourceKind=checkSet(s); }
public String getSourceDate(){ return checkGet(sourceDate); }
public void setSourceDate(String s){ sourceDate=checkSet(s); }
public String getSourceDoc(){ return checkGet(sourceDoc); }
public void setSourceDoc(String s){ sourceDoc=checkSet(s); }
String oldOwner;
String manageOrg;
String manageOrgName;
String useState;
public String getOldOwner(){ return checkGet(oldOwner); }
public void setOldOwner(String s){ oldOwner=checkSet(s); }
public String getManageOrg(){ return checkGet(manageOrg); }
public void setManageOrg(String s){ manageOrg=checkSet(s); }
public String getManageOrgName(){ return checkGet(manageOrgName); }
public void setManageOrgName(String s){ manageOrgName=checkSet(s); }
public String getUseState(){ return checkGet(useState); }
public void setUseState(String s){ useState=checkSet(s); }
String originalArea;
String originalHoldNume;
String originalHoldDeno;
String originalHoldArea;
public String getOriginalArea(){ return checkGet(originalArea); }
public void setOriginalArea(String s){ originalArea=checkSet(s); }
public String getOriginalHoldNume(){ return checkGet(originalHoldNume); }
public void setOriginalHoldNume(String s){ originalHoldNume=checkSet(s); }
public String getOriginalHoldDeno(){ return checkGet(originalHoldDeno); }
public void setOriginalHoldDeno(String s){ originalHoldDeno=checkSet(s); }
public String getOriginalHoldArea(){ return checkGet(originalHoldArea); }
public void setOriginalHoldArea(String s){ originalHoldArea=checkSet(s); }
String area;
String holdNume;
String holdDeno;
String holdArea;
public String getArea(){ return checkGet(area); }
public void setArea(String s){ area=checkSet(s); }
public String getHoldNume(){ return checkGet(holdNume); }
public void setHoldNume(String s){ holdNume=checkSet(s); }
public String getHoldDeno(){ return checkGet(holdDeno); }
public void setHoldDeno(String s){ holdDeno=checkSet(s); }
public String getHoldArea(){ return checkGet(holdArea); }
public void setHoldArea(String s){ holdArea=checkSet(s); }
String originalBasis;
String originalDate;
String originalUnit;
String originalBV;
String originalNote;
public String getOriginalBasis(){ return checkGet(originalBasis); }
public void setOriginalBasis(String s){ originalBasis=checkSet(s); }
public String getOriginalDate(){ return checkGet(originalDate); }
public void setOriginalDate(String s){ originalDate=checkSet(s); }
public String getOriginalUnit(){ return checkGet(originalUnit); }
public void setOriginalUnit(String s){ originalUnit=checkSet(s); }
public String getOriginalBV(){ return checkGet(originalBV); }
public void setOriginalBV(String s){ originalBV=checkSet(s); }
public String getOriginalNote(){ return checkGet(originalNote); }
public void setOriginalNote(String s){ originalNote=checkSet(s); }
String bookUnit;
String bookValue;
public String getBookUnit(){ return checkGet(bookUnit); }
public void setBookUnit(String s){ bookUnit=checkSet(s); }
public String getBookValue(){ return checkGet(bookValue); }
public void setBookValue(String s){ bookValue=checkSet(s); }
String ownershipDate;
String ownershipCause;
String nonProof;
String proofDoc;
String proofVerify;
String ownershipNote;
public String getOwnershipDate(){ return checkGet(ownershipDate); }
public void setOwnershipDate(String s){ ownershipDate=checkSet(s); }
public String getOwnershipCause(){ return checkGet(ownershipCause); }
public void setOwnershipCause(String s){ ownershipCause=checkSet(s); }
public String getNonProof(){ return checkGet(nonProof); }
public void setNonProof(String s){ nonProof=checkSet(s); }
public String getProofDoc(){ return checkGet(proofDoc); }
public void setProofDoc(String s){ proofDoc=checkSet(s); }
public String getProofVerify(){ return checkGet(proofVerify); }
public void setProofVerify(String s){ proofVerify=checkSet(s); }
public String getOwnershipNote(){ return checkGet(ownershipNote); }
public void setOwnershipNote(String s){ ownershipNote=checkSet(s); }
String useSeparate;
String useKind;
public String getUseSeparate(){ return checkGet(useSeparate); }
public void setUseSeparate(String s){ useSeparate=checkSet(s); }
public String getUseKind(){ return checkGet(useKind); }
public void setUseKind(String s){ useKind=checkSet(s); }
String field;
String landRule;
public String getField(){ return checkGet(field); }
public void setField(String s){ field=checkSet(s); }
public String getLandRule(){ return checkGet(landRule); }
public void setLandRule(String s) {landRule = checkSet(s);}
String reduceDate;
String reduceCause;
String reduceCause1;
public String getReduceDate(){ return checkGet(reduceDate); }
public void setReduceDate(String s){ reduceDate=checkSet(s); }
public String getReduceCause(){ return checkGet(reduceCause); }
public void setReduceCause(String s){ reduceCause=checkSet(s); }
public String getReduceCause1(){ return checkGet(reduceCause1); }
public void setReduceCause1(String s){ reduceCause1=checkSet(s); }
String appraiseDate;
public String getAppraiseDate(){ return checkGet(appraiseDate); }
public void setAppraiseDate(String s){ appraiseDate=checkSet(s); }
String proceedType;
String proceedDateS;
String proceedDateE;
public String getProceedType(){ return checkGet(proceedType); }
public void setProceedType(String s){ proceedType=checkSet(s); }
public String getProceedDateS(){ return checkGet(proceedDateS); }
public void setProceedDateS(String s){ proceedDateS=checkSet(s); }
public String getProceedDateE(){ return checkGet(proceedDateE); }
public void setProceedDateE(String s){ proceedDateE=checkSet(s); }
String notes1;
public String getNotes1(){ return checkGet(notes1); }
public void setNotes1(String s){ notes1=checkSet(s); }
String notes;
public String getNotes(){ return checkGet(notes); }
public void setNotes(String s){ notes=checkSet(s); }

//傳回update sql
protected String[] getUpdateSQL(){
	String[] execSQLArray = new String[1];
	execSQLArray[0] = " update Untla_Land set "
		 			+ " enterDate = " + Common.sqlChar(enterDate) + ","
		 			+ " dataState = " + Common.sqlChar(dataState) + ","
		 			+ " verify = " + Common.sqlChar(verify) + ","
		 			+ " closing = " + Common.sqlChar(closing) + ","
		 			+ " propertyName1 = " + Common.sqlChar(propertyName1) + ","
		 			+ " oldPropertyNo = " + Common.sqlChar(oldPropertyNo) + ","
		 			+ " oldSerialNo = " + Common.sqlChar(oldSerialNo) + ","
		 			+ " signno = " + Common.sqlChar(signNo3+signNo4+signNo5) + ","
		 			+ " doorplate = " + Common.sqlChar(doorplate) + ","
		 			+ " grass = " + Common.sqlChar(grass) + ","
		 			+ " propertyKind = " + Common.sqlChar(propertyKind) + ","
		 			+ " fundType = " + Common.sqlChar(fundType) + ","
		 			+ " valuable = " + Common.sqlChar(valuable) + ","
		 			+ " taxCredit = " + Common.sqlChar(taxCredit) + ","
		 			+ " cause = " + Common.sqlChar(cause) + ","
					+ " cause1 = " + Common.sqlChar(cause1) + ","
					+ " fundsSource = " + Common.sqlChar(fundsSource) + ","
					+ " fundsSource1 = " + Common.sqlChar(fundsSource1) + ","
					+ " accountingTitle = " + Common.sqlChar(accountingTitle) + ","
					+ " sourceKind = " + Common.sqlChar(sourceKind) + ","
					+ " sourceDate = " + Common.sqlChar(sourceDate) + ","
					+ " sourceDoc = " + Common.sqlChar(sourceDoc) + ","
					+ " oldOwner = " + Common.sqlChar(oldOwner) + ","
					+ " manageOrg = " + Common.sqlChar(manageOrg) + ","
					+ " useState = " + Common.sqlChar(useState) + ","
					+ " originalArea = " + Common.sqlChar(originalArea) + ","
					+ " originalHoldNume = " + Common.sqlChar(originalHoldNume) + ","
					+ " originalHoldDeno = " + Common.sqlChar(originalHoldDeno) + ","
					+ " originalHoldArea = " + Common.sqlChar(originalHoldArea) + ","
					+ " area = " + Common.sqlChar(area) + ","
					+ " holdNume = " + Common.sqlChar(holdNume) + ","
					+ " holdDeno = " + Common.sqlChar(holdDeno) + ","
					+ " holdArea = " + Common.sqlChar(holdArea) + ","
					+ " originalBasis = " + Common.sqlChar(originalBasis) + ","
					+ " originalDate = " + Common.sqlChar(originalDate) + ","
					+ " originalUnit = " + Common.sqlChar(originalUnit) + ","
					+ " originalBV = " + Common.sqlChar(originalBV) + ","
					+ " originalNote = " + Common.sqlChar(originalNote) + ","
					+ " bookUnit = " + Common.sqlChar(bookUnit) + ","
					+ " bookValue = " + Common.sqlChar(bookValue) + ","
					+ " ownershipDate = " + Common.sqlChar(ownershipDate) + ","
					+ " ownershipCause = " + Common.sqlChar(ownershipCause) + ","
					+ " nonProof = " + Common.sqlChar(nonProof) + ","
					+ " proofDoc = " + Common.sqlChar(proofDoc) + ","
					+ " proofVerify = " + Common.sqlChar(proofVerify) + ","
					+ " ownershipNote = " + Common.sqlChar(ownershipNote) + ","
					+ " useSeparate = " + Common.sqlChar(useSeparate) + ","
					+ " useKind = " + Common.sqlChar(useKind) + ","
					+ " field = " + Common.sqlChar(field) + ","
					+ " landRule = " + Common.sqlChar(landRule) + ","
					+ " reduceDate = " + Common.sqlChar(reduceDate) + ","
					+ " reduceCause = " + Common.sqlChar(reduceCause) + ","
					+ " reduceCause1 = " + Common.sqlChar(reduceCause1) + ","
					+ " appraiseDate = " + Common.sqlChar(appraiseDate) + ","
					+ " proceedType = " + Common.sqlChar(proceedType) + ","
					+ " proceedDateS = " + Common.sqlChar(proceedDateS) + ","
					+ " proceedDateE = " + Common.sqlChar(proceedDateE) + ","
					+ " notes1 = " + Common.sqlChar(notes1) + ","
					+ " notes = " + Common.sqlChar(notes)
					+ " where 1=1 " 
			   		+ " and enterOrg = " + Common.sqlChar(enterOrg)
			   		+ " and ownership = " + Common.sqlChar(ownership)
			   		+ " and caseNo = " + Common.sqlChar(caseNo)
			   		+ " and propertyNo = " + Common.sqlChar(propertyNo)
			   		+ " and serialNo = " + Common.sqlChar(serialNo)
					+ "";

	return execSQLArray;
}

/**
 * <br>
 * <br>目的：依主鍵查詢單一資料
 * <br>參數：無
 * <br>傳回：傳回本物件
*/

public UNTDU016F queryOne() throws Exception{
	Database db = new Database();
	UNTDU016F obj = this;
	try {
		String sql = " select a.enterOrg ,o.organsname as enterOrgName ,a.ownership ,a.caseNo ,a.propertyNo ,a.serialNo " + "\n"
				   + " 		  ,a.enterDate ,a.dataState ,a.verify ,a.closing ,a.propertyName1 " + "\n"
				   + " 		  ,a.oldPropertyNo ,a.oldSerialNo ,a.signno ,a.doorplate " + "\n"
				   + " 		  ,a.grass ,a.propertyKind ,a.fundType ,a.valuable ,a.taxCredit " + "\n"
				   + " 		  ,a.cause ,a.cause1 ,a.fundsSource ,a.fundsSource1 ,a.accountingTitle " + "\n"
				   + " 		  ,a.sourceKind ,a.sourceDate ,a.sourceDoc ,a.oldOwner ,a.manageOrg " + "\n"
				   + " 		  ,a.useState ,a.originalArea ,a.originalHoldNume " + "\n"
				   + " 		  ,a.originalHoldDeno ,a.originalHoldArea ,a.area ,a.holdNume " + "\n"
				   + " 		  ,a.holdDeno ,a.holdArea ,a.originalBasis ,a.originalDate ,a.originalUnit " + "\n"
				   + " 		  ,a.originalBV ,a.originalNote ,a.bookUnit ,a.bookValue " + "\n"
				   + " 		  ,a.ownershipDate ,a.ownershipCause ,a.nonProof ,a.proofDoc " + "\n"
				   + " 		  ,a.proofVerify ,a.ownershipNote ,a.useSeparate ,a.useKind " + "\n"
				   + " 		  ,a.field ,a.landRule ,a.reduceDate ,a.reduceCause " + "\n"
				   + " 		  ,a.reduceCause1 ,a.appraiseDate ,a.proceedType ,a.proceedDateS " + "\n"
				   + " 		  ,a.proceedDateE ,a.notes1 ,a.notes " + "\n"
				   + " 		  ,substr(a.signno ,1,1) || '000000' as signNo1 " + "\n"
				   + " 		  ,substr(a.signno ,1,3) || '0000' as signNo2 " + "\n"
				   + " 		  ,substr(a.signno ,1,7) as signNo3 " + "\n"
				   + " 		  ,substr(a.signno ,8,4) as signNo4 " + "\n"
				   + " 		  ,substr(a.signno ,12,4) as signNo5 " + "\n"
				   + " 		  ,(select x.organsname from sysca_organ x where x.organid = a.manageorg ) as manageorgName " + "\n"
		   		   + " 		  ,a.editid ,a.editDate ,a.editTime " + "\n"
		   		   + " from Untla_Land a ,sysca_organ o " + "\n"
		   		   + " where 1=1 " + "\n"
		   		   + " and a.enterorg=o.organid " + "\n" 
		   		   + " and a.enterOrg = " + Common.sqlChar(enterOrg)
		   		   + " and a.ownership = " + Common.sqlChar(ownership)
		   		   + " and a.caseNo = " + Common.sqlChar(caseNo)
		   		   + " and a.propertyNo = " + Common.sqlChar(propertyNo)
		   		   + " and a.serialNo = " + Common.sqlChar(serialNo)
		   		   + "";

		//System.out.println(sql);
		ResultSet rs = db.querySQL(sql);
		if (rs.next()){
			
			obj.setEnterOrg(rs.getString("enterOrg"));
			obj.setEnterOrgName(rs.getString("enterOrgName"));
			obj.setOwnership(rs.getString("ownership"));
			obj.setPropertyNo(rs.getString("propertyNo"));
			obj.setSerialNo(rs.getString("serialNo"));
			
			obj.setEnterDate(rs.getString("enterDate"));
			obj.setDataState(rs.getString("dataState"));
			obj.setVerify(rs.getString("verify"));
			obj.setClosing(rs.getString("closing"));
			obj.setPropertyName1(rs.getString("propertyName1"));
			obj.setOldPropertyNo(rs.getString("oldPropertyNo"));
			obj.setOldSerialNo(rs.getString("oldSerialNo"));
			
			//obj.setSignno(rs.getString("signno"));
			obj.setSignNo1(rs.getString("signNo1"));
			obj.setSignNo2(rs.getString("signNo2"));
			obj.setSignNo3(rs.getString("signNo3"));
			obj.setSignNo4(rs.getString("signNo4"));
			obj.setSignNo5(rs.getString("signNo5"));
			
			obj.setDoorplate(rs.getString("doorplate"));
			obj.setGrass(rs.getString("grass"));
			obj.setPropertyKind(rs.getString("propertyKind"));
			obj.setFundType(rs.getString("fundType"));
			obj.setValuable(rs.getString("valuable"));
			obj.setTaxCredit(rs.getString("taxCredit"));
			obj.setCause(rs.getString("cause"));
			obj.setCause1(rs.getString("cause1"));
			obj.setFundsSource(rs.getString("fundsSource"));
			obj.setFundsSource1(rs.getString("fundsSource1"));
			obj.setAccountingTitle(rs.getString("accountingTitle"));
			obj.setSourceKind(rs.getString("sourceKind"));
			obj.setSourceDate(rs.getString("sourceDate"));
			obj.setSourceDoc(rs.getString("sourceDoc"));
			obj.setOldOwner(rs.getString("oldOwner"));
			obj.setManageOrg(rs.getString("manageOrg"));
			obj.setManageOrgName(rs.getString("manageorgName"));
			
			obj.setUseState(rs.getString("useState"));
			obj.setOriginalArea(rs.getString("originalArea"));
			obj.setOriginalHoldNume(rs.getString("originalHoldNume"));
			obj.setOriginalHoldDeno(rs.getString("originalHoldDeno"));
			obj.setOriginalHoldArea(rs.getString("originalHoldArea"));
			obj.setArea(rs.getString("area"));
			obj.setHoldNume(rs.getString("holdNume"));
			obj.setHoldDeno(rs.getString("holdDeno"));
			obj.setHoldArea(rs.getString("holdArea"));
			obj.setOriginalBasis(rs.getString("originalBasis"));
			obj.setOriginalDate(rs.getString("originalDate"));
			obj.setOriginalUnit(rs.getString("originalUnit"));
			obj.setOriginalBV(rs.getString("originalBV"));
			obj.setOriginalNote(rs.getString("originalNote"));
			obj.setBookUnit(rs.getString("bookUnit"));
			obj.setBookValue(rs.getString("bookValue"));
			obj.setOwnershipDate(rs.getString("ownershipDate"));
			obj.setOwnershipCause(rs.getString("ownershipCause"));
			obj.setNonProof(rs.getString("nonProof"));
			obj.setProofDoc(rs.getString("proofDoc"));
			obj.setProofVerify(rs.getString("proofVerify"));
			obj.setOwnershipNote(rs.getString("ownershipNote"));
			obj.setUseSeparate(rs.getString("useSeparate"));
			obj.setUseKind(rs.getString("useKind"));
			obj.setField(rs.getString("field"));
			obj.setLandRule(rs.getString("landRule"));
			obj.setReduceDate(rs.getString("reduceDate"));
			obj.setReduceCause(rs.getString("reduceCause"));
			obj.setReduceCause1(rs.getString("reduceCause1"));
			obj.setAppraiseDate(rs.getString("appraiseDate"));
			obj.setProceedType(rs.getString("proceedType"));
			obj.setProceedDateS(rs.getString("proceedDateS"));
			obj.setProceedDateE(rs.getString("proceedDateE"));
			obj.setNotes1(rs.getString("notes1"));
			obj.setNotes(rs.getString("notes"));


			
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
	try {
		String sql = " select a.enterOrg ,o.organsname as enterOrgName ,a.ownership ,a.caseno " + "\n"
				   + " 		  ,a.propertyNo ,a.serialNo "
				   + "		  ,(select x.codeName from sysca_code x where a.ownership = x.codeid and x.codekindid = 'OWA') as ownershipName "
				   + " from Untla_Land a ,sysca_organ o " + "\n"
				   + " where 1=1 " + "\n"
				   + " and a.enterorg=o.organid " + "\n" ;
				   if (!"".equals(getQ_enterOrg()))
				   {	sql += " and a.enterOrg = " + Common.sqlChar(getQ_enterOrg()) ;	}
				   if (!"".equals(getQ_ownership()))
				   {	sql += " and a.ownership = " + Common.sqlChar(getQ_ownership()) ;	}
				   if (!"".equals(getQ_caseNo()))
				   {	sql+=" and a.caseNo = " + Common.sqlChar(getQ_caseNo()) ;		}
				   if (!"".equals(getQ_propertyNo()))
				   {	sql += " and a.propertyNo = " + Common.sqlChar(getQ_propertyNo()) ;	}
				   if (!"".equals(getQ_serialNo()))
				   {	sql += " and a.serialNo = " + Common.sqlChar(getQ_serialNo());	}
				   
				sql += " order by enterorg ,ownership ,caseno ,propertyNo ,serialNo ";
			
		//System.out.println(sql);
		ResultSet rs = db.querySQL(sql,true);
		processCurrentPageAttribute(rs);
		if (getTotalRecord() > 0) {
			int count = getRecordStart();
			int end = getRecordEnd();
			do {
				if (count > end) break;
			String rowArray[]=new String[7];
			rowArray[0]=Common.get(rs.getString("enterOrg")); 
			rowArray[1]=Common.get(rs.getString("enterOrgName")); 
			rowArray[2]=Common.get(rs.getString("ownership"));
			rowArray[3]=Common.get(rs.getString("ownershipName"));
			rowArray[4]=Common.get(rs.getString("caseno"));
			rowArray[5]=Common.get(rs.getString("propertyNo"));
			rowArray[6]=Common.get(rs.getString("serialNo"));
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

}
