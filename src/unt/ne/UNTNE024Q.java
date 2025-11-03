
/*
*<br>程式目的：非消耗品增減作業－增減值單資料
*<br>程式代號：untne024Q
*<br>程式日期：0941207
*<br>程式作者：judy
*<br>--------------------------------------------------------
*<br>修改作者　　修改日期　　　修改目的
*<br>--------------------------------------------------------
*/

package unt.ne;

import util.*;

public class UNTNE024Q extends SuperBean2{

	String q_caseName;
	String q_enterOrgName;
	String q_enterOrg;
	String q_ownership;
	String q_caseNoS;
	String q_caseNoE;
	String q_adjustDateS;
	String q_adjustDateE;
	String q_verify;
	String q_writeDateS;
	String q_writeDateE;
	String q_proofDoc;
	String q_proofNoS;
	String q_proofNoE;
	String q_approveOrg;
	String q_propertyNoS;
	String q_propertyNoE;
	String q_propertyNoSName;
	String q_propertyNoEName;
	String q_serialNoS;
	String q_serialNoE;
	String q_propertyKind;
	String q_fundType;
	String q_valuable;
	String q_taxCredit;
	String q_proofYear;
	String q_differenceKind;
	
	public String getQ_caseName(){ return checkGet(q_caseName); }
	public void setQ_caseName(String s){ q_caseName=checkSet(s); }
	public String getQ_enterOrgName(){ return checkGet(q_enterOrgName); }
	public void setQ_enterOrgName(String s){ q_enterOrgName=checkSet(s); }
	public String getQ_enterOrg(){ return checkGet(q_enterOrg); }
	public void setQ_enterOrg(String s){ q_enterOrg=checkSet(s); }
	public String getQ_ownership(){ return checkGet(q_ownership); }
	public void setQ_ownership(String s){ q_ownership=checkSet(s); }
	public String getQ_caseNoS(){ return checkGet(q_caseNoS); }
	public void setQ_caseNoS(String s){ q_caseNoS=checkSet(s); }
	public String getQ_caseNoE(){ return checkGet(q_caseNoE); }
	public void setQ_caseNoE(String s){ q_caseNoE=checkSet(s); }
	public String getQ_adjustDateS(){ return checkGet(q_adjustDateS); }
	public void setQ_adjustDateS(String s){ q_adjustDateS=checkSet(s); }
	public String getQ_adjustDateE(){ return checkGet(q_adjustDateE); }
	public void setQ_adjustDateE(String s){ q_adjustDateE=checkSet(s); }
	public String getQ_verify(){ return checkGet(q_verify); }
	public void setQ_verify(String s){ q_verify=checkSet(s); }
	public String getQ_writeDateS(){ return checkGet(q_writeDateS); }
	public void setQ_writeDateS(String s){ q_writeDateS=checkSet(s); }
	public String getQ_writeDateE(){ return checkGet(q_writeDateE); }
	public void setQ_writeDateE(String s){ q_writeDateE=checkSet(s); }
	public String getQ_proofDoc(){ return checkGet(q_proofDoc); }
	public void setQ_proofDoc(String s){ q_proofDoc=checkSet(s); }
	public String getQ_proofNoS(){ return checkGet(q_proofNoS); }
	public void setQ_proofNoS(String s){ q_proofNoS=checkSet(s); }
	public String getQ_proofNoE(){ return checkGet(q_proofNoE); }
	public void setQ_proofNoE(String s){ q_proofNoE=checkSet(s); }
	public String getQ_approveOrg(){ return checkGet(q_approveOrg); }
	public void setQ_approveOrg(String s){ q_approveOrg=checkSet(s); }
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
	public String getQ_taxCredit(){ return checkGet(q_taxCredit); }
	public void setQ_taxCredit(String s){ q_taxCredit=checkSet(s); }
	public String getQ_proofYear() {return checkGet(q_proofYear);}
	public void setQ_proofYear(String s) {q_proofYear = checkSet(s);}
	public String getQ_differenceKind() {return checkGet(q_differenceKind);}
	public void setQ_differenceKind(String s) {q_differenceKind = checkSet(s);}
	String q_propertyName1;
	public String getQ_propertyName1(){ return checkGet(q_propertyName1); }
	public void setQ_propertyName1(String s){ q_propertyName1=checkSet(s); }
	
	String querySelectValue;
	String querySelect;

	public String getQuerySelectValue(){ return checkGet(querySelectValue); }
	public void setQuerySelectValue(String s){ querySelectValue=checkSet(s); }
	public String getQuerySelect(){ return checkGet(querySelect); }
	public void setQuerySelect(String s){ querySelect=checkSet(s); }
	
	public String getQueryTab1() {
		String strCheck = "";
		if ("AddProof".equalsIgnoreCase(getQuerySelect())) strCheck = "checked";		
		return strCheck;
	}
	
	public String getQueryTab2() {
		String strCheck = "";
		if ("detail".equalsIgnoreCase(getQuerySelect())) strCheck = "checked";		
		return strCheck;
	}	
	
	String isOrganManager;
	String isAdminManager;
	String organID;   
	String propertyNo;
	String serialNo;

	public String getPropertyNo() { return checkGet(propertyNo); }
	public void setPropertyNo(String s) { propertyNo = checkSet(s); }
	public String getSerialNo() { return checkGet(serialNo); }
	public void setSerialNo(String s) { serialNo = checkSet(s); }
	public String getOrganID() { return checkGet(organID); }
	public void setOrganID(String s) { organID = checkSet(s); }
	public String getIsOrganManager() { return checkGet(isOrganManager); }
	public void setIsOrganManager(String s) { isOrganManager = checkSet(s); }
	public String getIsAdminManager() { return checkGet(isAdminManager); }
	public void setIsAdminManager(String s) { isAdminManager = checkSet(s); }  
	
	String q_keepUnit;
	String q_keeper;
	String q_useUnit;
	String q_userNo;
	String q_place;
	String q_place1Name;
	String q_place1;
	String q_userNote;
	String q_moveDateS;
	String q_moveDateE;
	
	public String getQ_keepUnit(){ return checkGet(q_keepUnit); }
	public void setQ_keepUnit(String s){ q_keepUnit=checkSet(s); }
	public String getQ_keeper(){ return checkGet(q_keeper); }
	public void setQ_keeper(String s){ q_keeper=checkSet(s); }
	public String getQ_useUnit(){ return checkGet(q_useUnit); }
	public void setQ_useUnit(String s){ q_useUnit=checkSet(s); }
	public String getQ_userNo(){ return checkGet(q_userNo); }
	public void setQ_userNo(String s){ q_userNo=checkSet(s); }
	public String getQ_place(){ return checkGet(q_place); }
	public void setQ_place(String s){ q_place=checkSet(s); }
	public String getQ_place1Name(){ return checkGet(q_place1Name); }
	public void setQ_place1Name(String s){ q_place1Name=checkSet(s); }
	public String getQ_place1(){ return checkGet(q_place1); }
	public void setQ_place1(String s){ q_place1=checkSet(s); }
	public String getQ_userNote(){ return checkGet(q_userNote); }
	public void setQ_userNote(String s){ q_userNote=checkSet(s); }
	public String getQ_moveDateS(){ return checkGet(q_moveDateS); }
	public void setQ_moveDateS(String s){ q_moveDateS=checkSet(s); }
	public String getQ_moveDateE(){ return checkGet(q_moveDateE); }
	public void setQ_moveDateE(String s){ q_moveDateE=checkSet(s); }
	
	String i_enterOrg;
	String i_ownership;
	String i_caseNo;
	String i_differenceKind;
	String queryone_enterOrg;
	String queryone_ownership;
	String queryone_caseNo;
	String queryone_differenceKind;

	public String getI_enterOrg(){ return checkGet(i_enterOrg); }
	public void setI_enterOrg(String s){ i_enterOrg=checkSet(s); }
	public String getI_ownership(){ return checkGet(i_ownership); }
	public void setI_ownership(String s){ i_ownership=checkSet(s); }
	public String getI_caseNo(){ return checkGet(i_caseNo); }
	public void setI_caseNo(String s){ i_caseNo=checkSet(s); }
	public String getI_differenceKind(){ return checkGet(i_differenceKind); }
	public void setI_differenceKind(String s){ i_differenceKind=checkSet(s); }
	public String getQueryone_enterOrg(){ return checkGet(queryone_enterOrg); }
	public void setQueryone_enterOrg(String s){ queryone_enterOrg=checkSet(s); }
	public String getQueryone_ownership(){ return checkGet(queryone_ownership); }
	public void setQueryone_ownership(String s){ queryone_ownership=checkSet(s); }
	public String getQueryone_caseNo(){ return checkGet(queryone_caseNo); }
	public void setQueryone_caseNo(String s){ queryone_caseNo=checkSet(s); }
	public String getQueryone_differenceKind(){ return checkGet(queryone_differenceKind); }
	public void setQueryone_differenceKind(String s){ queryone_differenceKind=checkSet(s); }
	
	String pageSize1;
	String totalPage1;
	String currentPage1;
	String totalRecord1;
	String recordStart1;
	String recordEnd1;

	public String getPageSize1(){ return checkGet(pageSize1); }
	public void setPageSize1(String s){ pageSize1=checkSet(s); }
	public String getTotalPage1(){ return checkGet(totalPage1); }
	public void setTotalPage1(String s){ totalPage1=checkSet(s); }
	public String getCurrentPage1(){ return checkGet(currentPage1); }
	public void setCurrentPage1(String s){ currentPage1=checkSet(s); }
	public String getTotalRecord1(){ return checkGet(totalRecord1); }
	public void setTotalRecord1(String s){ totalRecord1=checkSet(s); }
	public String getRecordStart1(){ return checkGet(recordStart1); }
	public void setRecordStart1(String s){ recordStart1=checkSet(s); }
	public String getRecordEnd1(){ return checkGet(recordEnd1); }
	public void setRecordEnd1(String s){ recordEnd1=checkSet(s); }
}

