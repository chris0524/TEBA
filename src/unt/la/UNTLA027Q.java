/*
*<br>程式目的：土地合併分割作業－案件資料
*<br>程式代號：untla027f
*<br>程式日期：0940818
*<br>程式作者：carey
*<br>--------------------------------------------------------
*<br>修改作者　　修改日期　　　修改目的
*<br>--------------------------------------------------------
*/

package unt.la;

import util.*;

public class UNTLA027Q extends SuperBean{

    String q_enterOrg;
    String q_enterOrgName;
    String q_ownership;
    String q_caseNoS;
    String q_caseNoE;
    String q_caseName;
    String q_cause;
    String q_enterDateS;
    String q_enterDateE;
    String q_approveOrg;
    String q_verify;
    String q_checkQuery;

    public String getQ_checkQuery(){ return checkGet(q_checkQuery); }
    public void setQ_checkQuery(String s){ q_checkQuery=checkSet(s); }
    public String getQ_enterOrg(){ return checkGet(q_enterOrg); }
    public void setQ_enterOrg(String s){ q_enterOrg=checkSet(s); }
    public String getQ_enterOrgName(){ return checkGet(q_enterOrgName); }
    public void setQ_enterOrgName(String s){ q_enterOrgName=checkSet(s); }
    public String getQ_ownership(){ return checkGet(q_ownership); }
    public void setQ_ownership(String s){ q_ownership=checkSet(s); }
    public String getQ_caseNoS(){ return checkGet(q_caseNoS); }
    public void setQ_caseNoS(String s){ q_caseNoS=checkSet(s); }
    public String getQ_caseNoE(){ return checkGet(q_caseNoE); }
    public void setQ_caseNoE(String s){ q_caseNoE=checkSet(s); }
    public String getQ_caseName(){ return checkGet(q_caseName); }
    public void setQ_caseName(String s){ q_caseName=checkSet(s); }
    public String getQ_cause(){ return checkGet(q_cause); }
    public void setQ_cause(String s){ q_cause=checkSet(s); }
    public String getQ_enterDateS(){ return checkGet(q_enterDateS); }
    public void setQ_enterDateS(String s){ q_enterDateS=checkSet(s); }
    public String getQ_enterDateE(){ return checkGet(q_enterDateE); }
    public void setQ_enterDateE(String s){ q_enterDateE=checkSet(s); }
    public String getQ_approveOrg(){ return checkGet(q_approveOrg); }
    public void setQ_approveOrg(String s){ q_approveOrg=checkSet(s); }
    public String getQ_verify(){ return checkGet(q_verify); }
    public void setQ_verify(String s){ q_verify=checkSet(s); }
    
    String isOrganManager;
    String isAdminManager;
    String organID;    
    public String getOrganID() { return checkGet(organID); }
    public void setOrganID(String s) { organID = checkSet(s); }
    public String getIsOrganManager() { return checkGet(isOrganManager); }
    public void setIsOrganManager(String s) { isOrganManager = checkSet(s); }
    public String getIsAdminManager() { return checkGet(isAdminManager); }
    public void setIsAdminManager(String s) { isAdminManager = checkSet(s); }
    
    String caseNo;
    String reduceCaseNo;
    String mergeDivisionVerify;
    String addCaseNo;
    String reduceCaseNo1;
    String addCaseNo1;
    
    public String getMergeDivisionVerify(){ return checkGet(mergeDivisionVerify); }
    public void setMergeDivisionVerify(String s){ mergeDivisionVerify=checkSet(s); }
    public String getCaseNo(){ return checkGet(caseNo); }
    public void setCaseNo(String s){ caseNo=checkSet(s); }
    public String getReduceCaseNo(){ return checkGet(reduceCaseNo); }
    public void setReduceCaseNo(String s){ reduceCaseNo=checkSet(s); }
    public String getAddCaseNo(){ return checkGet(addCaseNo); }
    public void setAddCaseNo(String s){ addCaseNo=checkSet(s); }
    public String getReduceCaseNo1(){ return checkGet(reduceCaseNo1); }
    public void setReduceCaseNo1(String s){ reduceCaseNo1=checkSet(s); }
    public String getAddCaseNo1(){ return checkGet(addCaseNo1); }
    public void setAddCaseNo1(String s){ addCaseNo1=checkSet(s); }

}


