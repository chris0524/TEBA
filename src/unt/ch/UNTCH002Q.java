
package unt.ch;

import util.*;

public class UNTCH002Q extends SuperBean2{

	String querySelectValue;
    String querySelect = "proof";
    
    String q_enterOrg;
    String q_enterOrgName;
    String q_ownership;
    String q_caseNoS;
    String q_caseNoE;
    String q_caseName;
    String q_writeDateS;
    String q_writeDateE;
    String q_proofDoc;
    String q_proofNoS;
    String q_proofNoE;
    String q_moveDateS;
    String q_moveDateE;
    String q_verify;

    String q_propertyNoS;
    String q_propertyNoE;
    String q_propertyNoSName;
    String q_propertyNoEName;
    String q_propertyNoName;    
    String q_serialNoS;
    String q_serialNoE;
    String q_propertyKind;
    String q_valuable;
    String q_fundType;
    
    String q_oldKeepBureau;
    String q_oldKeepUnit;
    String q_oldKeeper;
    
    String q_oldUseBureau;
    String q_oldUseUnit;
    String q_oldUserNo;
    
    String q_newKeepBureau;
    String q_newKeepUnit;
    String q_newKeeper;
    
    String q_newUseBureau;
    String q_newUseUnit;
    String q_newUserNo;
    
    String organID;    
    String isOrganManager;
    String isAdminManager;
   
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
    public String getQ_moveDateS(){ return checkGet(q_moveDateS); }
    public void setQ_moveDateS(String s){ q_moveDateS=checkSet(s); }
    public String getQ_moveDateE(){ return checkGet(q_moveDateE); }
    public void setQ_moveDateE(String s){ q_moveDateE=checkSet(s); }
    public String getQ_verify(){ return checkGet(q_verify); }
    public void setQ_verify(String s){ q_verify=checkSet(s); }
    
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
    public String getQ_valuable(){ return checkGet(q_valuable); }
    public void setQ_valuable(String s){ q_valuable=checkSet(s); }
    public String getQ_fundType(){ return checkGet(q_fundType); }
    public void setQ_fundType(String s){ q_fundType=checkSet(s); }
    
    public String getQ_oldKeepBureau(){ return checkGet(q_oldKeepBureau); }
    public void setQ_oldKeepBureau(String s){ q_oldKeepBureau=checkSet(s); }
    public String getQ_oldKeepUnit(){ return checkGet(q_oldKeepUnit); }
    public void setQ_oldKeepUnit(String s){ q_oldKeepUnit=checkSet(s); }
    public String getQ_oldKeeper(){ return checkGet(q_oldKeeper); } 
    public void setQ_oldKeeper(String s){ q_oldKeeper=checkSet(s); }
    
    public String getQ_oldUseBureau(){ return checkGet(q_oldUseBureau); }
    public void setQ_oldUseBureau(String s){ q_oldUseBureau=checkSet(s); }
    public String getQ_oldUseUnit(){ return checkGet(q_oldUseUnit); }
    public void setQ_oldUseUnit(String s){ q_oldUseUnit=checkSet(s); }
    public String getQ_oldUserNo(){ return checkGet(q_oldUserNo); }
    public void setQ_oldUserNo(String s){ q_oldUserNo=checkSet(s); }
    
    public String getQ_newKeepBureau(){ return checkGet(q_newKeepBureau); }
    public void setQ_newKeepBureau(String s){ q_newKeepBureau=checkSet(s); }
    public String getQ_newKeepUnit(){ return checkGet(q_newKeepUnit); }
    public void setQ_newKeepUnit(String s){ q_newKeepUnit=checkSet(s); }
    public String getQ_newKeeper(){ return checkGet(q_newKeeper); }
    public void setQ_newKeeper(String s){ q_newKeeper=checkSet(s); }
    
    public String getQ_newUseBureau(){ return checkGet(q_newUseBureau); }
    public void setQ_newUseBureau(String s){ q_newUseBureau=checkSet(s); }
    public String getQ_newUseUnit(){ return checkGet(q_newUseUnit); }
    public void setQ_newUseUnit(String s){ q_newUseUnit=checkSet(s); }
    public String getQ_newUserNo(){ return checkGet(q_newUserNo); }
    public void setQ_newUserNo(String s){ q_newUserNo=checkSet(s); }
    
    public String getOrganID() { return checkGet(organID); }
    public void setOrganID(String s) { organID = checkSet(s); }
    public String getIsOrganManager() { return checkGet(isOrganManager); }
    public void setIsOrganManager(String s) { isOrganManager = checkSet(s); }
    public String getIsAdminManager() { return checkGet(isAdminManager); }
    public void setIsAdminManager(String s) { isAdminManager = checkSet(s); }     
 
    private String closing;
    public String getClosing() {return checkGet(closing);}
	public void setClosing(String closing) {this.closing = checkSet(closing);}
    
	private String q_oldUserNote;
	private String q_newUserNote;
	private String q_oldPlace;
	private String q_newPlace;
	private String q_oldPlaceName;
	private String q_newPlaceName;
	private String q_oldPlaceNote;
	private String q_newPlaceNote;
	private String q_differenceKind;
	private String q_propertyName1;
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
	private String q_proofYear;
	private String q_engineeringNo;
	private String q_engineeringNoName;
	public String getQ_oldUserNote() {return checkGet(q_oldUserNote);}
	public void setQ_oldUserNote(String q_oldUserNote) {this.q_oldUserNote = checkSet(q_oldUserNote);}
	public String getQ_newUserNote() {return checkGet(q_newUserNote);}
	public void setQ_newUserNote(String q_newUserNote) {this.q_newUserNote = checkSet(q_newUserNote);}
	public String getQ_oldPlace() {return checkGet(q_oldPlace);}
	public void setQ_oldPlace(String q_oldPlace) {this.q_oldPlace = checkSet(q_oldPlace);}
	public String getQ_newPlace() {return checkGet(q_newPlace);}
	public void setQ_newPlace(String q_newPlace) {this.q_newPlace = checkSet(q_newPlace);}
	public String getQ_oldPlaceName() {return checkGet(q_oldPlaceName);}
	public void setQ_oldPlaceName(String q_oldPlaceName) {this.q_oldPlaceName = checkSet(q_oldPlaceName);}
	public String getQ_newPlaceName() {	return checkGet(q_newPlaceName);}
	public void setQ_newPlaceName(String q_newPlaceName) {this.q_newPlaceName = checkSet(q_newPlaceName);}
	public String getQ_oldPlaceNote() {return checkGet(q_oldPlaceNote);}
	public void setQ_oldPlaceNote(String q_oldPlaceNote) {this.q_oldPlaceNote = checkSet(q_oldPlaceNote);}
	public String getQ_newPlaceNote() {return checkGet(q_newPlaceNote);}
	public void setQ_newPlaceNote(String q_newPlaceNote) {this.q_newPlaceNote = checkSet(q_newPlaceNote);}
	public String getQ_differenceKind() {return checkGet(q_differenceKind);}
	public void setQ_differenceKind(String q_differenceKind) {this.q_differenceKind = checkSet(q_differenceKind);}
	public String getQ_propertyName1() {return checkGet(q_propertyName1);}
	public void setQ_propertyName1(String q_propertyName1) {this.q_propertyName1 = checkSet(q_propertyName1);}
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
	public String getQ_proofYear() {return checkGet(q_proofYear);}
	public void setQ_proofYear(String q_proofYear) {this.q_proofYear = checkSet(q_proofYear);}
	public String getQ_engineeringNo() {return checkGet(q_engineeringNo);}
	public void setQ_engineeringNo(String q_engineeringNo) {this.q_engineeringNo = checkSet(q_engineeringNo);}
	public String getQ_engineeringNoName() {return checkGet(q_engineeringNoName);}
	public void setQ_engineeringNoName(String q_engineeringNoName) {this.q_engineeringNoName = checkSet(q_engineeringNoName);}	

	private String differenceKind;
	public String getDifferenceKind() {return checkGet(differenceKind);}
	public void setDifferenceKind(String differenceKind) {this.differenceKind = checkSet(differenceKind);}
	
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


