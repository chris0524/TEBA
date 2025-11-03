/*
*<br>程式目的：土地主檔資料維護－增加單資料
*<br>程式代號：untla001f
*<br>程式日期：0940818
*<br>程式作者：novia
*<br>--------------------------------------------------------
*<br>修改作者　　修改日期　　　修改目的
*<br>clive.chang 0941219	Debug & Modify for Testing and autual running..
*<br>--------------------------------------------------------
*/

package unt.la;

import util.*;

public class UNTLA001Q extends SuperBean2{

    String querySelectValue;
    String querySelect;
    
    String q_enterOrg;
    String q_enterOrgName;
    String q_caseNoS;
    String q_caseNoE;
    String q_ownership;
    String q_caseName;
    String q_cause;
    String q_enterDateS;
    String q_enterDateE;
    String q_proofDoc;
    String q_proofNoS;
    String q_proofNoE;   
    String q_writeDateS;
    String q_writeDateE;     
    String q_verify;

    String q_propertyNoS;
    String q_propertyNoE;
    String q_propertyNoSName;
    String q_propertyNoEName;
    String q_propertyNoName; 
    String q_serialNoS;
    String q_serialNoE;
    String q_signNo1;
    String q_signNo2;
    String q_signNo3;
    String q_signNo4;
    String q_signNo5;
    String q_signNo = "";
    String q_propertyKind;
    String q_valuable;
    String q_dataState;
    String q_fundType;
    String q_taxCredit;
    String q_grass;
    String q_proceedDateS;
    String q_proceedDateE;
    String q_differenceKind;
    
	public String getQ_differenceKind() {return checkGet(q_differenceKind);}
	public void setQ_differenceKind(String q_differenceKind) {this.q_differenceKind = checkSet(q_differenceKind);}
	public String getQuerySelectValue(){ return checkGet(querySelectValue); }
    public void setQuerySelectValue(String s){ querySelectValue=checkSet(s); }
    public String getQuerySelect(){ return checkGet(querySelect); }
    public void setQuerySelect(String s){ querySelect=checkSet(s); }
    
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
    public String getQ_propertyKind(){ return checkGet(q_propertyKind); }
    public void setQ_propertyKind(String s){ q_propertyKind=checkSet(s); }
    public String getQ_valuable(){ return checkGet(q_valuable); }
    public void setQ_valuable(String s){ q_valuable=checkSet(s); }
    public String getQ_dataState(){ return checkGet(q_dataState); }
    public void setQ_dataState(String s){ q_dataState=checkSet(s); }    
    public String getQ_fundType(){ return checkGet(q_fundType); }
    public void setQ_fundType(String s){ q_fundType=checkSet(s); } 
    public String getQ_taxCredit(){ return checkGet(q_taxCredit); }
    public void setQ_taxCredit(String s){ q_taxCredit=checkSet(s); } 
    public String getQ_grass(){ return checkGet(q_grass); }
    public void setQ_grass(String s){ q_grass=checkSet(s); } 
    public String getQ_proceedDateS(){ return checkGet(q_proceedDateS); }
    public void setQ_proceedDateS(String s){ q_proceedDateS=checkSet(s); } 
    public String getQ_proceedDateE(){ return checkGet(q_proceedDateE); }
    public void setQ_proceedDateE(String s){ q_proceedDateE=checkSet(s); } 
    
    public String getQ_enterOrg(){ return checkGet(q_enterOrg); }
    public void setQ_enterOrg(String s){ q_enterOrg=checkSet(s); }
    public String getQ_enterOrgName(){ return checkGet(q_enterOrgName); }
    public void setQ_enterOrgName(String s){ q_enterOrgName=checkSet(s); }
    public String getQ_caseNoS(){ return checkGet(q_caseNoS); }
    public void setQ_caseNoS(String s){ q_caseNoS=checkSet(s); }
    public String getQ_caseNoE(){ return checkGet(q_caseNoE); }
    public void setQ_caseNoE(String s){ q_caseNoE=checkSet(s); }    
    public String getQ_ownership(){ return checkGet(q_ownership); }
    public void setQ_ownership(String s){ q_ownership=checkSet(s); }
    public String getQ_caseName(){ return checkGet(q_caseName); }
    public void setQ_caseName(String s){ q_caseName=checkSet(s); }
    public String getQ_cause(){ return checkGet(q_cause); }
    public void setQ_cause(String s){ q_cause=checkSet(s); }
    public String getQ_enterDateS(){ return checkGet(q_enterDateS); }
    public void setQ_enterDateS(String s){ q_enterDateS=checkSet(s); }
    public String getQ_enterDateE(){ return checkGet(q_enterDateE); }
    public void setQ_enterDateE(String s){ q_enterDateE=checkSet(s); }
    public String getQ_proofDoc(){ return checkGet(q_proofDoc); }
    public void setQ_proofDoc(String s){ q_proofDoc=checkSet(s); }    
    public String getQ_proofNoS(){ return checkGet(q_proofNoS); }
    public void setQ_proofNoS(String s){ q_proofNoS=checkSet(s); }
    public String getQ_proofNoE(){ return checkGet(q_proofNoE); }
    public void setQ_proofNoE(String s){ q_proofNoE=checkSet(s); }       
    public String getQ_writeDateS(){ return checkGet(q_writeDateS); }
    public void setQ_writeDateS(String s){ q_writeDateS=checkSet(s); } 
    public String getQ_writeDateE(){ return checkGet(q_writeDateE); }
    public void setQ_writeDateE(String s){ q_writeDateE=checkSet(s); }     
    public String getQ_verify(){ return checkGet(q_verify); }
    public void setQ_verify(String s){ q_verify=checkSet(s); }
    
    public String getQueryTab1() {
    	String strCheck = "";
    	if ("AddProof".equalsIgnoreCase(getQuerySelect())) strCheck = "checked";    	
    	return strCheck;
    }
    
    public String getQueryTab2() {
    	String strCheck = "";
    	if ("Land".equalsIgnoreCase(getQuerySelect())) strCheck = "checked";    	
    	return strCheck;
    }    
    
    //String caseNo;
    //public String getCaseNo(){ return checkGet(caseNo); }
    //public void setCaseNo(String s){ caseNo=checkSet(s); }
    String dataState;
    public String getDataState(){ return checkGet(dataState); }
    public void setDataState(String s){ dataState=checkSet(s); }
    String propertyKind;
    public String getPropertyKind(){ return checkGet(propertyKind); }
    public void setPropertyKind(String s){ propertyKind=checkSet(s); }
    
    String isOrganManager;
    String isAdminManager;
    String organID;    
    public String getOrganID() { return checkGet(organID); }
    public void setOrganID(String s) { organID = checkSet(s); }
    public String getIsOrganManager() { return checkGet(isOrganManager); }
    public void setIsOrganManager(String s) { isOrganManager = checkSet(s); }
    public String getIsAdminManager() { return checkGet(isAdminManager); }
    public void setIsAdminManager(String s) { isAdminManager = checkSet(s); }
    
    String mergeDivision;

    public String getMergeDivision(){ return checkGet(mergeDivision); }
    public void setMergeDivision(String s){ mergeDivision=checkSet(s); }

}


