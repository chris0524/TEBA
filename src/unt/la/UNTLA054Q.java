/*
*<br>程式目的：土地合併分割重測重劃作業－案件資料
*<br>程式代號：untla054f
*<br>程式日期：
*<br>程式作者：Yuan-Ren Jheng
*<br>--------------------------------------------------------
*<br>修改作者　　修改日期　　　修改目的
*<br>--------------------------------------------------------
*/

package unt.la;

import util.*;

class UNTLA054Q_data{
	public String tableName;
	public String enterOrg;
	public String ownership;
	public String caseNo;
	public String differenceKind;
	public String propertyNo;
	public String serialNo;
	public String serialNo1;	
}


public class UNTLA054Q extends UNTLA054_ServerBean{

	String q_enterOrg;
	String q_enterOrgName;
	String q_ownership;
	String q_caseNoS;
	String q_caseNoE;
	String q_caseName;
	String q_cause;
	String q_enterDateS;
	String q_enterDateE;
	String q_verify;
	String q_differenceKind;
	
	public String getQ_enterOrg() {return checkGet(q_enterOrg);}
	public void setQ_enterOrg(String qEnterOrg) {q_enterOrg = checkSet(qEnterOrg);}
	public String getQ_enterOrgName() {return checkGet(q_enterOrgName);}
	public void setQ_enterOrgName(String qEnterOrgName) {q_enterOrgName = checkSet(qEnterOrgName);}
	public String getQ_ownership() {return checkGet(q_ownership);}
	public void setQ_ownership(String qOwnership) {q_ownership = checkSet(qOwnership);}
	public String getQ_caseNoS() {return checkGet(q_caseNoS);}
	public void setQ_caseNoS(String qCaseNoS) {q_caseNoS = checkSet(qCaseNoS);}
	public String getQ_caseNoE() {return checkGet(q_caseNoE);}
	public void setQ_caseNoE(String qCaseNoE) {q_caseNoE = checkSet(qCaseNoE);}
	public String getQ_caseName() {return checkGet(q_caseName);}
	public void setQ_caseName(String qCaseName) {q_caseName = checkSet(qCaseName);}
	public String getQ_cause() {return checkGet(q_cause);}
	public void setQ_cause(String qCause) {q_cause = checkSet(qCause);}
	public String getQ_enterDateS() {return checkGet(q_enterDateS);}
	public void setQ_enterDateS(String qEnterDateS) {q_enterDateS = checkSet(qEnterDateS);}
	public String getQ_enterDateE() {return checkGet(q_enterDateE);}
	public void setQ_enterDateE(String qEnterDateE) {q_enterDateE = checkSet(qEnterDateE);}
	public String getQ_verify() {return checkGet(q_verify);}
	public void setQ_verify(String qVerify) {q_verify = checkSet(qVerify);}
	public String getQ_differenceKind() {return checkGet(q_differenceKind);}
	public void setQ_differenceKind(String q_differenceKind) {this.q_differenceKind = checkSet(q_differenceKind);}



	String enterorgNotConfirm;
	String alreadyVerify;
	String caseNo_Merge;
	String caseNo_Reduce;
	String caseNo_Add;
	String propertyNo_Reduce;
	String serialNo_Reduce;
	String propertyNo_Add;
	String serialNo_Add;
	
	public String getEnterorgNotConfirm() {return checkGet(enterorgNotConfirm);}
	public void setEnterorgNotConfirm(String enterorgNotConfirm) {this.enterorgNotConfirm = checkSet(enterorgNotConfirm);}
	public String getAlreadyVerify() {return checkGet(alreadyVerify);}
	public void setAlreadyVerify(String alreadyVerify) {this.alreadyVerify = checkSet(alreadyVerify);}
	public String getCaseNo_Merge() {return checkGet(caseNo_Merge);}
	public void setCaseNo_Merge(String caseNoMerge) {caseNo_Merge = checkSet(caseNoMerge);}
	public String getCaseNo_Reduce() {return checkGet(caseNo_Reduce);}
	public void setCaseNo_Reduce(String caseNoReduce) {caseNo_Reduce = checkSet(caseNoReduce);}
	public String getCaseNo_Add() {return checkGet(caseNo_Add);}
	public void setCaseNo_Add(String caseNoAdd) {caseNo_Add = checkSet(caseNoAdd);}
	public String getPropertyNo_Reduce() {return checkGet(propertyNo_Reduce);}
	public void setPropertyNo_Reduce(String propertyNoReduce) {propertyNo_Reduce = checkSet(propertyNoReduce);}
	public String getSerialNo_Reduce() {return checkGet(serialNo_Reduce);}
	public void setSerialNo_Reduce(String serialNoReduce) {serialNo_Reduce = checkSet(serialNoReduce);}
	public String getPropertyNo_Add() {return checkGet(propertyNo_Add);}
	public void setPropertyNo_Add(String propertyNoAdd) {propertyNo_Add = checkSet(propertyNoAdd);}
	public String getSerialNo_Add() {return checkGet(serialNo_Add);}
	public void setSerialNo_Add(String serialNoAdd) {serialNo_Add = checkSet(serialNoAdd);}

	protected UNTLA054Q_data getParameterData(){return new UNTLA054Q_data();}
	
	
	//==================================================================
	//插件函式
	//==================================================================
	//UNTLA054F, UNTLA055F, UNTLA056F, UNTLA057F, UNTLA058F
	//UNTLA062F, UNTLA064F
		protected void extend_getInsertSQL(){}
		protected void extend_getUpdateSQL(){}
		protected void extend_getDeleteSQL(){}
	
	//UNTLA054F
		protected void extend_get_ClosingYM_From_UNTGR_Closing(){}
		protected void extend_execCheckCanApproveRe(){}
		protected void extend_execVerifyProduce(){}
		
	//UNTLA059F, UNTLA060F, UNTLA061F
		protected void extend_beforeWork_Add(){}
		
	//UNTLA059F, UNTLA060F, UNTLA061F
	//UNTLA063F, UNTLA065F
		protected void extend_afterWork(){}
		protected void extend_cencelWork(){}

}


