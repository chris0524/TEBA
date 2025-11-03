/*
*<br>程式目的：動產資料維護
*<br>程式代號：untmp001f_q
*<br>程式日期：0940928
*<br>程式作者：clive.chang
*<br>--------------------------------------------------------
*<br>修改作者　　修改日期　　　修改目的
*<br>--------------------------------------------------------
*/

package unt.mp;

import util.*;

public class UNTMP001Q extends SuperBean2{

String q_enterOrg;
String q_enterOrgName;
String q_ownership;
String q_caseNoS;
String q_caseNoE;
String q_verify;
String q_closing;
String q_enterDateS;
String q_enterDateE;
String q_caseName;
String q_writeDateS;
String q_writeDateE;
String q_proofDoc;
String q_proofNoS;
String q_proofNoE;
String q_propertyNo;
String q_propertyNoS;
String q_propertyNoE;
String q_propertyNoSName;
String q_propertyNoEName;
String q_propertyNoName;
String q_lotNo;
String q_dataState;
String q_cause;
String q_serialNoS;
String q_serialNoE;
String q_propertyKind;
String q_fundType;
String q_valuable;
String q_keepUnit;
String q_keeper;
String q_useUnit;
String q_userNo;
String q_buyDateS;
String q_buyDateE;

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
public String getQ_verify(){ return checkGet(q_verify); }
public void setQ_verify(String s){ q_verify=checkSet(s); }
public String getQ_closing(){ return checkGet(q_closing); }
public void setQ_closing(String s){ q_closing=checkSet(s); }
public String getQ_enterDateS(){ return checkGet(q_enterDateS); }
public void setQ_enterDateS(String s){ q_enterDateS=checkSet(s); }
public String getQ_enterDateE(){ return checkGet(q_enterDateE); }
public void setQ_enterDateE(String s){ q_enterDateE=checkSet(s); }
public String getQ_caseName(){ return checkGet(q_caseName); }
public void setQ_caseName(String s){ q_caseName=checkSet(s); }
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
public String getQ_lotNo(){ return checkGet(q_lotNo); }
public void setQ_lotNo(String s){ q_lotNo=checkSet(s); }
public String getQ_dataState(){ return checkGet(q_dataState); }
public void setQ_dataState(String s){ q_dataState=checkSet(s); }
public String getQ_cause(){ return checkGet(q_cause); }
public void setQ_cause(String s){ q_cause=checkSet(s); }
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
public String getQ_keepUnit(){ return checkGet(q_keepUnit); }
public void setQ_keepUnit(String s){ q_keepUnit=checkSet(s); }
public String getQ_keeper(){ return checkGet(q_keeper); }
public void setQ_keeper(String s){ q_keeper=checkSet(s); }
public String getQ_useUnit(){ return checkGet(q_useUnit); }
public void setQ_useUnit(String s){ q_useUnit=checkSet(s); }
public String getQ_userNo(){ return checkGet(q_userNo); }
public void setQ_userNo(String s){ q_userNo=checkSet(s); }
public String getQ_buyDateS(){ return checkGet(q_buyDateS); }
public void setQ_buyDateS(String s){ q_buyDateS=checkSet(s); }
public String getQ_buyDateE(){ return checkGet(q_buyDateE); }
public void setQ_buyDateE(String s){ q_buyDateE=checkSet(s); }

String isOrganManager;
String isAdminManager;
String organID;    
String queryone_enterOrg;
String queryone_ownership;
String queryone_caseNo;
String queryone_differenceKind;
public String getQueryone_enterOrg(){ return checkGet(queryone_enterOrg); }
public void setQueryone_enterOrg(String s){ queryone_enterOrg=checkSet(s); }
public String getQueryone_ownership(){ return checkGet(queryone_ownership); }
public void setQueryone_ownership(String s){ queryone_ownership=checkSet(s); }
public String getQueryone_caseNo(){ return checkGet(queryone_caseNo); }
public void setQueryone_caseNo(String s){ queryone_caseNo=checkSet(s); }
public String getQueryone_differenceKind(){ return checkGet(queryone_differenceKind); }
public void setQueryone_differenceKind(String s){ queryone_differenceKind=checkSet(s); }

public String getOrganID() { return checkGet(organID); }
public void setOrganID(String s) { organID = checkSet(s); }
public String getIsOrganManager() { return checkGet(isOrganManager); }
public void setIsOrganManager(String s) { isOrganManager = checkSet(s); }
public String getIsAdminManager() { return checkGet(isAdminManager); }
public void setIsAdminManager(String s) { isAdminManager = checkSet(s); }  

String caseNo;
String querySelect;
String lotNo;
String propertyNo;
String serialNo;
String dataState;

public String getQuerySelect(){ return checkGet(querySelect); }
public void setQuerySelect(String s){ querySelect=checkSet(s); }

public String getQueryType1() {
	String strCheck = "";
	if ("addProof".equalsIgnoreCase(getQuerySelect())) strCheck = "checked";    	
	return strCheck;
}

public String getQueryType2() {
	String strCheck = "";
	if ("movable".equalsIgnoreCase(getQuerySelect())) strCheck = "checked";    	
	return strCheck;
}    

public String getQueryType3() {
	String strCheck = "";
	if ("movableDetail".equalsIgnoreCase(getQuerySelect())) strCheck = "checked";    	
	return strCheck;
}    

public String getCaseNo(){ return checkGet(caseNo); }
public void setCaseNo(String s){ caseNo=checkSet(s); }
public String getLotNo(){ return checkGet(lotNo); }
public void setLotNo(String s){ lotNo=checkSet(s); }
public String getPropertyNo(){ return checkGet(propertyNo); }
public void setPropertyNo(String s){ propertyNo=checkSet(s); }
public String getSerialNo(){ return checkGet(serialNo); }
public void setSerialNo(String s){ serialNo=checkSet(s); }
public String getDataState(){ return checkGet(dataState); }
public void setDataState(String s){ dataState=checkSet(s); }

String checkDataState;

public String getCheckDataState(){ return checkGet(dataState); }
public void setCheckDataState(String s){ dataState=checkSet(s); }


String q_checkResult;
public String getQ_checkResult(){ return checkGet(q_checkResult); }
public void setQ_checkResult(String s){ q_checkResult=checkSet(s); }


String q_oldSerialNoS;
String q_oldSerialNoE;
public String getQ_oldSerialNoS(){ return checkGet(q_oldSerialNoS); }
public void setQ_oldSerialNoS(String s){ q_oldSerialNoS=checkSet(s); }
public String getQ_oldSerialNoE(){ return checkGet(q_oldSerialNoE); }
public void setQ_oldSerialNoE(String s){ q_oldSerialNoE=checkSet(s); }

String q_propertyName1;
public String getQ_propertyName1(){ return checkGet(q_propertyName1); }
public void setQ_propertyName1(String s){ q_propertyName1=checkSet(s); }

String q_keepBureau;
String q_useBureau;
public String getQ_keepBureau(){ return checkGet(q_keepBureau); }
public void setQ_keepBureau(String s){ q_keepBureau=checkSet(s); }
public String getQ_useBureau(){ return checkGet(q_useBureau); }
public void setQ_useBureau(String s){ q_useBureau=checkSet(s); }

}



