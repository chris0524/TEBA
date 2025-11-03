/*
*<br>程式目的：權利主檔資料維護-增加單資料
*<br>程式代號：untrt001Q
*<br>程式日期：0940929
*<br>程式作者：cherry
*<br>--------------------------------------------------------
*<br>修改作者　　修改日期　　　修改目的
*<br>--------------------------------------------------------
*/

package unt.rt;

import util.*;

public class UNTRT001Q extends SuperBean2{


String q_enterOrg;
String q_enterOrgName;
String q_ownership;
String q_caseNoS;
String q_caseNoE;
String q_caseName;
String q_propertyNo;
String q_propertyNoS;
String q_propertyNoE;
String q_propertyNoSName;
String q_propertyNoEName;
String q_propertyNoName;
String q_serialNoS;
String q_serialNoE;
String q_writeDateS;
String q_writeDateE;
String q_proofYear;
String q_proofDoc;
String q_proofNoS;
String q_proofNoE;
String q_cause;
String q_causeName;
String q_enterDateS;
String q_enterDateE;
String q_dataState;
String q_verify;
String q_closing;
String q_propertyKind;
String q_fundType;
String q_fundTypeName;

String setArea;
String serialNo1;
String querySelect;
String checkDataState;
String oldVerify;
String checkVerify;
String checkClosing;
String q_signNo1;
String q_signNo2;
String q_signNo3;
String q_signNo4;
String q_signNo5;

public String getQueryType(){ return checkGet(querySelect); }
public void setQueryType(String s){ querySelect=checkSet(s); }
public String getQuerySelect(){ return checkGet(querySelect); }
public void setQuerySelect(String s){ querySelect=checkSet(s); }

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
public String getQ_propertyNo(){ return checkGet(q_propertyNo); }
public void setQ_propertyNo(String s){ q_propertyNo=checkSet(s); }
public String getQ_propertyNoS(){ return checkGet(q_propertyNoS); }
public void setQ_propertyNoS(String s){ q_propertyNoS=checkSet(s); }
public String getQ_propertyNoE(){ return checkGet(q_propertyNoE); }
public void setQ_propertyNoE(String s){ q_propertyNoE=checkSet(s); }
public String getQ_propertyNoSName(){ return checkGet(q_propertyNoSName); }
public void setQ_propertyNoSName(String s){ q_propertyNoSName=checkSet(s); }
public String getQ_propertyNoEName(){ return checkGet(q_propertyNoEName); }
public void setQ_propertyNoEName(String s){ q_propertyNoEName=checkSet(s); }
public String getQ_propertyNoName(){ return checkGet(q_propertyNoName); }
public void setQ_propertyNoName(String s){ q_propertyNoName=checkSet(s); }
public String getQ_serialNoS(){ return checkGet(q_serialNoS); }
public void setQ_serialNoS(String s){ q_serialNoS=checkSet(s); }
public String getQ_serialNoE(){ return checkGet(q_serialNoE); }
public void setQ_serialNoE(String s){ q_serialNoE=checkSet(s); }
public String getQ_writeDateS(){ return checkGet(q_writeDateS); }
public void setQ_writeDateS(String s){ q_writeDateS=checkSet(s); }
public String getQ_writeDateE(){ return checkGet(q_writeDateE); }
public void setQ_writeDateE(String s){ q_writeDateE=checkSet(s); }
public String getQ_proofYear() {return checkGet(q_proofYear);}
public void setQ_proofYear(String qProofYear) {q_proofYear = checkSet(qProofYear);}
public String getQ_proofDoc(){ return checkGet(q_proofDoc); }
public void setQ_proofDoc(String s){ q_proofDoc=checkSet(s); }
public String getQ_proofNoS(){ return checkGet(q_proofNoS); }
public void setQ_proofNoS(String s){ q_proofNoS=checkSet(s); }
public String getQ_proofNoE(){ return checkGet(q_proofNoE); }
public void setQ_proofNoE(String s){ q_proofNoE=checkSet(s); }
public String getQ_cause(){ return checkGet(q_cause); }
public void setQ_cause(String s){ q_cause=checkSet(s); }
public String getQ_causeName(){ return checkGet(q_causeName); }
public void setQ_causeName(String s){ q_causeName=checkSet(s); }
public String getQ_enterDateS(){ return checkGet(q_enterDateS); }
public void setQ_enterDateS(String s){ q_enterDateS=checkSet(s); }
public String getQ_enterDateE(){ return checkGet(q_enterDateE); }
public void setQ_enterDateE(String s){ q_enterDateE=checkSet(s); }
public String getQ_dataState(){ return checkGet(q_dataState); }
public void setQ_dataState(String s){ q_dataState=checkSet(s); }
public String getQ_verify(){ return checkGet(q_verify); }
public void setQ_verify(String s){ q_verify=checkSet(s); }
public String getQ_closing(){ return checkGet(q_closing); }
public void setQ_closing(String s){ q_closing=checkSet(s); }
public String getQ_propertyKind(){ return checkGet(q_propertyKind); }
public void setQ_propertyKind(String s){ q_propertyKind=checkSet(s); }
public String getQ_fundType(){ return checkGet(q_fundType); }
public void setQ_fundType(String s){ q_fundType=checkSet(s); }
public String getQ_fundTypeName(){ return checkGet(q_fundTypeName); }
public void setQ_fundTypeName(String s){ q_fundTypeName=checkSet(s); }

public String getSerialNo1(){ return checkGet(serialNo1); }
public void setSerialNo1(String s){ serialNo1=checkSet(s); }
public String getSetArea(){ return checkGet(setArea); }
public void setSetArea(String s){ setArea=checkSet(s); }
public String getCheckDataState(){ return checkGet(checkDataState); }
public void setCheckDataState(String s){ checkDataState=checkSet(s); }
public String getOldVerify(){ return checkGet(oldVerify); }
public void setOldVerify(String s){ oldVerify=checkSet(s); }
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
public String getCheckVerify(){ return checkGet(checkVerify); }
public void setCheckVerify(String s){ checkVerify=checkSet(s); }
public String getCheckClosing(){ return checkGet(checkClosing); }
public void setCheckClosing(String s){ checkClosing=checkSet(s); }

String isOrganManager;
String isAdminManager;
String organID;    
public String getOrganID() { return checkGet(organID); }
public void setOrganID(String s) { organID = checkSet(s); }
public String getIsOrganManager() { return checkGet(isOrganManager); }
public void setIsOrganManager(String s) { isOrganManager = checkSet(s); }
public String getIsAdminManager() { return checkGet(isAdminManager); }
public void setIsAdminManager(String s) { isAdminManager = checkSet(s); }

String q_signNo="";

public String getQ_signNo() { return checkGet(q_signNo); }
public void setQ_signNo(String s) { q_signNo = checkSet(s); }

}


