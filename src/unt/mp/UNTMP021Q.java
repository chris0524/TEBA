/*
*<br>程式目的：動產廢品處理作業－處理單資料
*<br>程式代號：untmp021f
*<br>程式日期：0941201
*<br>程式作者：cherry
*<br>--------------------------------------------------------
*<br>修改作者　　修改日期　　　修改目的
*<br>--------------------------------------------------------
*/

package unt.mp;

import util.*;

public class UNTMP021Q extends SuperBean2{

String q_enterOrg;
String q_enterOrgName;
String q_caseNo1S;
String q_caseNo1E;
String q_caseNoS;
String q_caseNoE;
String q_dealDateS;
String q_dealDateE;
String q_verify;
String q_caseName;
String q_writeDateS;
String q_writeDateE;
String q_proofYear;
String q_proofDoc;
String q_proofNoS;
String q_proofNoE;
String q_reduceDeal;
String q_shiftOrg;
String q_shiftOrgName;
String q_propertyNo;
String q_propertyNoS;
String q_propertyNoE;
String q_propertyNoSName;
String q_propertyNoEName;
String q_propertyNoName;
String q_serialNoS;
String q_serialNoE;
String q_propertyKind;
String q_fundType;
String q_valuable;
String querySelect;
String q_differenceKind;
String q_ownership;

public String getQ_ownership(){ return checkGet(q_ownership); }
public void setQ_ownership(String s){ q_ownership=checkSet(s); }
public String getQuerySelect(){ return checkGet(querySelect); }
public void setQuerySelect(String s){ querySelect=checkSet(s); }
public String getQ_enterOrg(){ return checkGet(q_enterOrg); }
public void setQ_enterOrg(String s){ q_enterOrg=checkSet(s); }
public String getQ_enterOrgName(){ return checkGet(q_enterOrgName); }
public void setQ_enterOrgName(String s){ q_enterOrgName=checkSet(s); }
public String getQ_differenceKind(){ return checkGet(q_differenceKind); }
public void setQ_differenceKind(String s){ q_differenceKind=checkSet(s); }
public String getQ_caseNo1S(){ return checkGet(q_caseNo1S); }
public void setQ_caseNo1S(String s){ q_caseNo1S=checkSet(s); }
public String getQ_caseNo1E(){ return checkGet(q_caseNo1E); }
public void setQ_caseNo1E(String s){ q_caseNo1E=checkSet(s); }
public String getQ_caseNoS(){ return checkGet(q_caseNoS); }
public void setQ_caseNoS(String s){ q_caseNoS=checkSet(s); }
public String getQ_caseNoE(){ return checkGet(q_caseNoE); }
public void setQ_caseNoE(String s){ q_caseNoE=checkSet(s); }
public String getQ_dealDateS(){ return checkGet(q_dealDateS); }
public void setQ_dealDateS(String s){ q_dealDateS=checkSet(s); }
public String getQ_dealDateE(){ return checkGet(q_dealDateE); }
public void setQ_dealDateE(String s){ q_dealDateE=checkSet(s); }
public String getQ_verify(){ return checkGet(q_verify); }
public void setQ_verify(String s){ q_verify=checkSet(s); }
public String getQ_caseName(){ return checkGet(q_caseName); }
public void setQ_caseName(String s){ q_caseName=checkSet(s); }
public String getQ_writeDateS(){ return checkGet(q_writeDateS); }
public void setQ_writeDateS(String s){ q_writeDateS=checkSet(s); }
public String getQ_writeDateE(){ return checkGet(q_writeDateE); }
public void setQ_writeDateE(String s){ q_writeDateE=checkSet(s); }
public String getQ_proofYear(){ return checkGet(q_proofYear); }
public void setQ_proofYear(String s){ q_proofYear=checkSet(s); }
public String getQ_proofDoc(){ return checkGet(q_proofDoc); }
public void setQ_proofDoc(String s){ q_proofDoc=checkSet(s); }
public String getQ_proofNoS(){ return checkGet(q_proofNoS); }
public void setQ_proofNoS(String s){ q_proofNoS=checkSet(s); }
public String getQ_proofNoE(){ return checkGet(q_proofNoE); }
public void setQ_proofNoE(String s){ q_proofNoE=checkSet(s); }
public String getQ_reduceDeal(){ return checkGet(q_reduceDeal); }
public void setQ_reduceDeal(String s){ q_reduceDeal=checkSet(s); }
public String getQ_shiftOrg(){ return checkGet(q_shiftOrg); }
public void setQ_shiftOrg(String s){ q_shiftOrg=checkSet(s); }
public String getQ_shiftOrgName(){ return checkGet(q_shiftOrgName); }
public void setQ_shiftOrgName(String s){ q_shiftOrgName=checkSet(s); }
public String getQ_propertyNo(){ return checkGet(q_propertyNo); }
public void setQ_propertyNo(String s){ q_propertyNo=checkSet(s); }
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
public String getQ_propertyKind(){ return checkGet(q_propertyKind); }
public void setQ_propertyKind(String s){ q_propertyKind=checkSet(s); }
public String getQ_fundType(){ return checkGet(q_fundType); }
public void setQ_fundType(String s){ q_fundType=checkSet(s); }
public String getQ_valuable(){ return checkGet(q_valuable); }
public void setQ_valuable(String s){ q_valuable=checkSet(s); }

String isOrganManager;
String isAdminManager;
String organID;    
public String getOrganID() { return checkGet(organID); }
public void setOrganID(String s) { organID = checkSet(s); }
public String getIsOrganManager() { return checkGet(isOrganManager); }
public void setIsOrganManager(String s) { isOrganManager = checkSet(s); }
public String getIsAdminManager() { return checkGet(isAdminManager); }
public void setIsAdminManager(String s) { isAdminManager = checkSet(s); }  

}


