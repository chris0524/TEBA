package unt.ch;

import util.*;

public class UNTCH001Q extends SuperBean2 {

	private String querySelectValue;
	private String querySelect = "proof";
    
	private String q_enterOrg;
	private String q_enterOrgName;
	private String q_caseNoS;
	private String q_caseNoE;
	private String q_ownership;
	private String q_caseName;
	private String q_cause;
	private String q_causeName;
	private String q_enterDateS;
	private String q_enterDateE;
	private String q_sourceDateS;
	private String q_sourceDateE;  
	private String q_proofDoc;
	private String q_proofNoS;
	private String q_proofNoE;   
	private String q_writeDateS;
	private String q_writeDateE;     
	private String q_verify;  
	private String q_verify2;  

	private String q_propertyNoS;
	private String q_propertyNoE;
	private String q_propertyNoSName;
	private String q_propertyNoEName;
	private String q_propertyNoName;    
	private String q_serialNoS;
	private String q_serialNoE;
	private String q_signNo1;
	private String q_signNo2;
	private String q_signNo3;
	private String q_signNo4;
	private String q_signNo5;
	private String q_signNo = "";
	private String q_signLaNo1;
	private String q_signLaNo2;
	private String q_signLaNo3;
	private String q_signLaNo4S;
	private String q_signLaNo5S;
	private String q_signLaNo4E;
	private String q_signLaNo5E;
	private String q_signBuNo1;
	private String q_signBuNo2;
	private String q_signBuNo3;
	private String q_signBuNo4S;
	private String q_signBuNo5S;
	private String q_signBuNo4E;
	private String q_signBuNo5E;
	private String q_propertyKind;
	private String q_valuable;
	private String q_dataState;
	private String q_fundType;
	private String q_taxCredit;
	private String q_proceedDateS;
	private String q_proceedDateE;
	private String q_propertyName1;
	private String q_differenceKind;
	private String q_originalUserNote;
	private String q_place1;
	private String q_place1Name;
	private String q_place;
	private String q_engineeringNo;
	private String q_engineeringNoName;
	private String q_originalBuildFeeCB;
	private String q_originalDeprUnitCB;
	private String q_proofYear;
	private String q_otherlimityear;
	private String q_differenceKind_detail;

	public String getQ_otherlimityear() {return checkGet(q_otherlimityear);}
	public void setQ_otherlimityear(String q_otherlimityear) {this.q_otherlimityear = checkSet(q_otherlimityear);}
	public String getQ_proofYear() {return checkGet(q_proofYear);}
	public void setQ_proofYear(String q_proofYear) {this.q_proofYear = checkSet(q_proofYear);}
	public String getQ_originalBuildFeeCB() {return checkGet(q_originalBuildFeeCB);}
	public void setQ_originalBuildFeeCB(String q_originalBuildFeeCB) {this.q_originalBuildFeeCB = checkSet(q_originalBuildFeeCB);}
	public String getQ_originalDeprUnitCB() {return checkGet(q_originalDeprUnitCB);}
	public void setQ_originalDeprUnitCB(String q_originalDeprUnitCB) {this.q_originalDeprUnitCB = checkSet(q_originalDeprUnitCB);}
	public String getQ_engineeringNo() {return checkGet(q_engineeringNo);}
	public void setQ_engineeringNo(String q_engineeringNo) {this.q_engineeringNo = checkSet(q_engineeringNo);}
	public String getQ_engineeringNoName() {return checkGet(q_engineeringNoName);}
	public void setQ_engineeringNoName(String q_engineeringNoName) {this.q_engineeringNoName = checkSet(q_engineeringNoName);}
	public String getQ_place() {return checkGet(q_place);}
	public void setQ_place(String q_place) {this.q_place = checkSet(q_place);}
	public String getQ_place1Name() {return checkGet(q_place1Name);}
	public void setQ_place1Name(String q_place1Name) {this.q_place1Name = checkSet(q_place1Name);}
	public String getQ_place1() {return checkGet(q_place1);}
	public void setQ_place1(String q_place1) {this.q_place1 = checkSet(q_place1);}
	public String getQ_differenceKind() {return checkGet(q_differenceKind);}
	public void setQ_differenceKind(String q_differenceKind) {this.q_differenceKind = checkSet(q_differenceKind);}
	public String getQ_differenceKind_detail() {return checkGet(q_differenceKind_detail);}
	public void setQ_differenceKind_detail(String q_differenceKind_detail) {this.q_differenceKind_detail = checkSet(q_differenceKind_detail);}
	

	String caseNo;
	public String getCaseNo() {return checkGet(caseNo);}
	public void setCaseNo(String caseNo) {this.caseNo = checkSet(caseNo);}
    
    String propertyKind;
    public String getPropertyKind(){ return checkGet(propertyKind); }
    public void setPropertyKind(String s){ propertyKind=checkSet(s); }    
    
    public String getQuerySelectValue(){ return checkGet(querySelectValue); }
    public void setQuerySelectValue(String s){ querySelectValue=checkSet(s); }
    public String getQuerySelect(){ return checkGet(querySelect); }
    public void setQuerySelect(String s){ querySelect=checkSet(s); }
    
    public String getQueryTab1() {
    	String strCheck = "";
    	if ("proof".equalsIgnoreCase(getQuerySelect())) strCheck = "checked";    	
    	return strCheck;
    }
    
    public String getQueryTab2() {
    	String strCheck = "";
    	if ("detail".equalsIgnoreCase(getQuerySelect())) strCheck = "checked";    	
    	return strCheck;
    }    

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
    public String getQ_signLaNo1() {return checkGet(q_signLaNo1);}
	public void setQ_signLaNo1(String q_signLaNo1) {this.q_signLaNo1 = checkSet(q_signLaNo1);}
	public String getQ_signLaNo2() {return checkGet(q_signLaNo2);}
	public void setQ_signLaNo2(String q_signLaNo2) {this.q_signLaNo2 = checkSet(q_signLaNo2);}
	public String getQ_signLaNo3() {return checkGet(q_signLaNo3);}
	public void setQ_signLaNo3(String q_signLaNo3) {this.q_signLaNo3 = checkSet(q_signLaNo3);}
	public String getQ_signLaNo4S() {return checkGet(q_signLaNo4S);}
	public void setQ_signLaNo4S(String q_signLaNo4S) {this.q_signLaNo4S = checkSet(q_signLaNo4S);}
	public String getQ_signLaNo5S() {return checkGet(q_signLaNo5S);}
	public void setQ_signLaNo5S(String q_signLaNo5S) {this.q_signLaNo5S = checkSet(q_signLaNo5S);}	
	public String getQ_signLaNo4E() {return checkGet(q_signLaNo4E);}
	public void setQ_signLaNo4E(String q_signLaNo4E) {this.q_signLaNo4E = checkSet(q_signLaNo4E);}
	public String getQ_signLaNo5E() {return checkGet(q_signLaNo5E);}
	public void setQ_signLaNo5E(String q_signLaNo5E) {this.q_signLaNo5E = checkSet(q_signLaNo5E);}	
	public String getQ_signBuNo1() {return checkGet(q_signBuNo1);}
	public void setQ_signBuNo1(String q_signBuNo1) {this.q_signBuNo1 = checkSet(q_signBuNo1);}
	public String getQ_signBuNo2() {return checkGet(q_signBuNo2);}
	public void setQ_signBuNo2(String q_signBuNo2) {this.q_signBuNo2 = checkSet(q_signBuNo2);}
	public String getQ_signBuNo3() {return checkGet(q_signBuNo3);}
	public void setQ_signBuNo3(String q_signBuNo3) {this.q_signBuNo3 = checkSet(q_signBuNo3);}
	public String getQ_signBuNo4S() {return checkGet(q_signBuNo4S);}
	public void setQ_signBuNo4S(String q_signBuNo4S) {this.q_signBuNo4S = checkSet(q_signBuNo4S);}
	public String getQ_signBuNo5S() {return checkGet(q_signBuNo5S);}
	public void setQ_signBuNo5S(String q_signBuNo5S) {this.q_signBuNo5S = checkSet(q_signBuNo5S);}
	public String getQ_signBuNo4E() {return checkGet(q_signBuNo4E);}
	public void setQ_signBuNo4E(String q_signBuNo4E) {this.q_signBuNo4E = checkSet(q_signBuNo4E);}
	public String getQ_signBuNo5E() {return checkGet(q_signBuNo5E);}
	public void setQ_signBuNo5E(String q_signBuNo5E) {this.q_signBuNo5E = checkSet(q_signBuNo5E);}
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
    public String getQ_proceedDateS(){ return checkGet(q_proceedDateS); }
    public void setQ_proceedDateS(String s){ q_proceedDateS=checkSet(s); } 
    public String getQ_proceedDateE(){ return checkGet(q_proceedDateE); }
    public void setQ_proceedDateE(String s){ q_proceedDateE=checkSet(s); } 
    
    public String getQ_enterOrg(){return checkGet(q_enterOrg);}
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
    public String getQ_sourceDateS() {return checkGet(q_sourceDateS);}
	public void setQ_sourceDateS(String qSourceDateS) {q_sourceDateS = checkSet(qSourceDateS);}
	public String getQ_sourceDateE() {return checkGet(q_sourceDateE);}
	public void setQ_sourceDateE(String qSourceDateE) {q_sourceDateE = checkSet(qSourceDateE);}
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
	public String getQ_verify2() { return checkGet(q_verify2); }
	public void setQ_verify2(String s) { this.q_verify2 = checkSet(s); }
	public String getQ_propertyName1() {return checkGet(q_propertyName1);}
	public void setQ_propertyName1(String qPropertyName1) {q_propertyName1 = checkSet(qPropertyName1);}
	public String getQ_originalUserNote() {return checkGet(q_originalUserNote);}
	public void setQ_originalUserNote(String q_originalUserNote) {this.q_originalUserNote = checkSet(q_originalUserNote);}
	
    String isOrganManager;
    String isAdminManager;
    String organID;    
    String userID; 
    public String getOrganID() { return checkGet(organID); }
    public void setOrganID(String s) { organID = checkSet(s); }
    public String getUserID() { return checkGet(userID); }
    public void setUserID(String s) { userID = checkSet(s); }
    public String getIsOrganManager() { return checkGet(isOrganManager); }
    public void setIsOrganManager(String s) { isOrganManager = checkSet(s); }
    public String getIsAdminManager() { return checkGet(isAdminManager); }
    public void setIsAdminManager(String s) { isAdminManager = checkSet(s); }  
    public String getQ_causeName() {return checkGet(q_causeName);}
	public void setQ_causeName(String q_causeName) {this.q_causeName = checkSet(q_causeName);}

	String q_doorPlate1;
    String q_doorPlate2;
    String q_doorPlate3;
    String q_doorPlate4;
    public String getQ_doorPlate1(){ return checkGet(q_doorPlate1); }
    public void setQ_doorPlate1(String s){ q_doorPlate1=checkSet(s); }
    public String getQ_doorPlate2(){ return checkGet(q_doorPlate2); }
    public void setQ_doorPlate2(String s){ q_doorPlate2=checkSet(s); }
    public String getQ_doorPlate3(){ return checkGet(q_doorPlate3); }
    public void setQ_doorPlate3(String s){ q_doorPlate3=checkSet(s); }
    public String getQ_doorPlate4(){ return checkGet(q_doorPlate4); }
    public void setQ_doorPlate4(String s){ q_doorPlate4=checkSet(s); }
    
    String q_keepUnit;
	String q_keeper;
    String q_useUnit;
    String q_userNo;
    public String getQ_keepUnit() {return checkGet(q_keepUnit);}
	public void setQ_keepUnit(String qKeepUnit) {q_keepUnit = checkSet(qKeepUnit);}
	public String getQ_keeper() {return checkGet(q_keeper);}
	public void setQ_keeper(String qKeeper) {q_keeper = checkSet(qKeeper);}
	public String getQ_useUnit() {return checkGet(q_useUnit);}
	public void setQ_useUnit(String qUseUnit) {q_useUnit = checkSet(qUseUnit);}
	public String getQ_userNo() {return checkGet(q_userNo);}
	public void setQ_userNo(String qUserNo) {q_userNo = checkSet(qUserNo);}
		
	private String q_oldPropertyNoS;
	private String q_oldPropertyNoE;
	private String q_oldPropertyNoSName;
	private String q_oldPropertyNoEName;
	private String q_oldSerialNoS;
	private String q_oldSerialNoE;
	private String q_oldlotNoS;
	private String q_oldlotNoE;
	public String getQ_oldPropertyNoS() {return checkGet(q_oldPropertyNoS);}
	public void setQ_oldPropertyNoS(String q_oldPropertyNoS) {this.q_oldPropertyNoS = checkSet(q_oldPropertyNoS);}
	public String getQ_oldPropertyNoE() {return checkGet(q_oldPropertyNoE);}
	public void setQ_oldPropertyNoE(String q_oldPropertyNoE) {this.q_oldPropertyNoE = checkSet(q_oldPropertyNoE);}
	public String getQ_oldPropertyNoSName() {return checkGet(q_oldPropertyNoSName);}
	public void setQ_oldPropertyNoSName(String q_oldPropertyNoSName) {this.q_oldPropertyNoSName = checkSet(q_oldPropertyNoSName);}
	public String getQ_oldPropertyNoEName() {return checkGet(q_oldPropertyNoEName);}
	public void setQ_oldPropertyNoEName(String q_oldPropertyNoEName) {this.q_oldPropertyNoEName = checkSet(q_oldPropertyNoEName);}
	public String getQ_oldSerialNoS() {return checkGet(q_oldSerialNoS);}
	public void setQ_oldSerialNoS(String q_oldSerialNoS) {this.q_oldSerialNoS = checkSet(q_oldSerialNoS);}
	public String getQ_oldSerialNoE() {return checkGet(q_oldSerialNoE);}
	public void setQ_oldSerialNoE(String q_oldSerialNoE) {this.q_oldSerialNoE = checkSet(q_oldSerialNoE);}
	public String getQ_oldlotNoS() {return checkGet(q_oldlotNoS);}
	public void setQ_oldlotNoS(String q_oldSerialNoS) {this.q_oldlotNoS = checkSet(q_oldSerialNoS);}
	public String getQ_oldlotNoE() {return checkGet(q_oldlotNoE);}
	public void setQ_oldlotNoE(String q_oldSerialNoS) {this.q_oldlotNoE = checkSet(q_oldSerialNoS);}
	
	private String q_nameplate;
	private String q_specification;
	public String getQ_nameplate() { return checkGet(q_nameplate); }
	public void setQ_nameplate(String q_nameplate) { this.q_nameplate = checkSet(q_nameplate); }
	public String getQ_specification() { return checkGet(q_specification); }
	public void setQ_specification(String q_specification) { this.q_specification = checkSet(q_specification); }

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
	
	String q_sourceKind;
	String q_sourceKindName;
	
	public String getQ_sourceKind(){ return checkGet(q_sourceKind); }
	public void setQ_sourceKind(String s){ q_sourceKind=checkSet(s); }
	public String getQ_sourceKindName(){ return checkGet(q_sourceKindName); }
	public void getQ_sourceKindName(String s){ q_sourceKindName=checkSet(s); }
	
	private String q_detail_propertyName1;
	public String getQ_detail_propertyName1() {return checkGet(q_detail_propertyName1);}
	public void setQ_detail_propertyName1(String q_detail_propertyName1) {this.q_detail_propertyName1 = checkSet(q_detail_propertyName1);}
	
	private String q_fundsSource;
	public String getQ_fundsSource() {return checkGet(q_fundsSource);}
	public void setQ_fundsSource(String q_fundsSource) {this.q_fundsSource = checkSet(q_fundsSource);}
	
	private String q_notes;
	public String getQ_notes() { return checkGet(q_notes); }
	public void setQ_notes(String q_notes) { this.q_notes = checkSet(q_notes); }
}