/*
*<br>程式目的：
*<br>程式代號：
*<br>程式日期：
*<br>程式作者：
*<br>--------------------------------------------------------
*<br>修改作者　　修改日期　　　修改目的
*<br>--------------------------------------------------------
*/

package unt.ch;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Map;

import util.Common;
import util.Database;
import util.Datetime;
import util.SuperBean2;
import util.TCGHCommon;
import TDlib_Simple.tools.src.ReflectTools;

public class UNTCH004F03 extends UNTCH004Q{

	private String q_overLimitYear;
	public String getQ_overLimitYear() {return checkGet(q_overLimitYear);}
	public void setQ_overLimitYear(String q_overLimitYear) {this.q_overLimitYear = checkSet(q_overLimitYear);}

	String causeName;
	public String getCauseName() {return checkGet(causeName);}
	public void setCauseName(String causeName) {this.causeName = checkSet(causeName);}

	String q_returnPlace;
	public String getQ_returnPlace() {return checkGet(q_returnPlace);}
	public void setQ_returnPlace(String q_returnPlace) {this.q_returnPlace = checkSet(q_returnPlace);}

	String dataSource;
	public String getDataSource() {return checkGet(dataSource);}
	public void setDataSource(String dataSource) {this.dataSource = checkSet(dataSource);}

	String enterOrg;
	String enterOrgName;
	String ownership;
	String propertyNo;
	String propertyName;
	String serialNo;
	String propertyName1;
	String signLaNo;
	String signLaNo1;
	String signLaNo2;
	String signLaNo3;
	String signLaNo4;
	String signLaNo5;
	String signBuNo;
	String signBuNo1;
	String signBuNo2;
	String signBuNo3;
	String signBuNo4;
	String signBuNo5;
	String propertyKind;
	String fundType;
	String valuable;
	String taxCredit;
	String sourceDate;
	String oldPropertyNo;
	String oldPropertyName;
	String oldSerialNo;
	String caseNo;
	String originalBV;

	String q_enterOrg; 
	String q_ownership; 
	String q_caseNo;
	String q_adjustDate;
	String q_verify;

	String q_differenceKind;
	String q_cause;
	String q_cause1;
	String q_cause2;
	String q_dataState;
	String q_propertyNoS;
	String q_propertyNoE;
	String q_propertyNoSName;
	String q_propertyNoEName;
	String q_serialNoS;
	String q_serialNoE;
	String q_signLaNo1;
	String q_signLaNo2;
	String q_signLaNo3;
	String q_signLaNo4;
	String q_signLaNo5;
	String q_signLaNo = "";
	String q_signBuNo1;
	String q_signBuNo2;
	String q_signBuNo3;
	String q_signBuNo4;
	String q_signBuNo5;
	String q_signBuNo = "";
	String q_propertyKind;
	String q_propertyName1;
	String q_fundType;
	String q_valuable;
	String q_taxCredit;
	String sSQL = "";
	String strKeySet[] = null;

	String CArea;
	String SArea;
	String area;
	String holdNume;
	String holdDeno;
	String holdArea;
	String bookValue;
	String q_userNote;
	String q_place;
	String q_placeName;
	String q_placeNote;
	String q_engineeringNo;
	String q_engineeringNoName;
	


	public String getQ_place() {return checkGet(q_place);}
	public void setQ_place(String q_place) {this.q_place = checkSet(q_place);}
	public String getQ_placeName() {return checkGet(q_placeName);}
	public void setQ_placeName(String q_placeName) {this.q_placeName = checkSet(q_placeName);}
	public String getQ_placeNote() {return checkGet(q_placeNote);}
	public void setQ_placeNote(String q_placeNote) {this.q_placeNote = checkSet(q_placeNote);}
	public String getQ_engineeringNo() {return checkGet(q_engineeringNo);}
	public void setQ_engineeringNo(String q_engineeringNo) {this.q_engineeringNo = checkSet(q_engineeringNo);}
	public String getQ_engineeringNoName() {return checkGet(q_engineeringNoName);}
	public void setQ_engineeringNoName(String q_engineeringNoName) {this.q_engineeringNoName = checkSet(q_engineeringNoName);}
	public String getQ_userNote() {return checkGet(q_userNote);}
	public void setQ_userNote(String q_userNote) {this.q_userNote = checkSet(q_userNote);}
	public String getQ_differenceKind() {return checkGet(q_differenceKind);}
	public void setQ_differenceKind(String q_differenceKind) {this.q_differenceKind = checkSet(q_differenceKind);}
	public String getCArea(){ return checkGet(CArea); }
	public void setCArea(String s){ CArea=checkSet(s); }
	public String getSArea(){ return checkGet(SArea); }
	public void setSArea(String s){ SArea=checkSet(s); }
	public String getArea(){ return checkGet(area); }
	public void setArea(String s){ area=checkSet(s); }
	public String getHoldNume(){ return checkGet(holdNume); }
	public void setHoldNume(String s){ holdNume=checkSet(s); }
	public String getHoldDeno(){ return checkGet(holdDeno); }
	public void setHoldDeno(String s){ holdDeno=checkSet(s); }
	public String getHoldArea(){ return checkGet(holdArea); }
	public void setHoldArea(String s){ holdArea=checkSet(s); }
	public String getBookValue(){ return checkGet(bookValue); }
	public void setBookValue(String s){ bookValue=checkSet(s); }

	public String getOriginalBV(){ return checkGet(originalBV); }
	public void setOriginalBV(String s){ originalBV=checkSet(s); }
	public String getsSQL(){ return checkGet(sSQL); }
	public void setsSQL(String s){ sSQL=checkSet(s); }
	public String[] getsKeySet(){ return strKeySet; }
	public void setsKeySet(String[] s){ strKeySet=s; }
	public String getEnterOrg(){ return checkGet(enterOrg); }
	public void setEnterOrg(String s){ enterOrg=checkSet(s); }
	public String getEnterOrgName(){ return checkGet(enterOrgName); }
	public void setEnterOrgName(String s){ enterOrgName=checkSet(s); }
	public String getOwnership(){ return checkGet(ownership); }
	public void setOwnership(String s){ ownership=checkSet(s); }
	public String getPropertyNo(){ return checkGet(propertyNo); }
	public void setPropertyNo(String s){ propertyNo=checkSet(s); }
	public String getPropertyName(){ return checkGet(propertyName); }
	public void setPropertyName(String s){ propertyName=checkSet(s); }
	public String getSerialNo(){ return checkGet(serialNo); }
	public void setSerialNo(String s){ serialNo=checkSet(s); }
	public String getPropertyName1(){ return checkGet(propertyName1); }
	public void setPropertyName1(String s){ propertyName1=checkSet(s); }

	public String getSignLaNo() {return checkGet(signLaNo);}
	public void setSignLaNo(String signLaNo) {this.signLaNo = checkSet(signLaNo);}
	public String getSignLaNo1() {return checkGet(signLaNo1);}
	public void setSignLaNo1(String signLaNo1) {this.signLaNo1 = checkSet(signLaNo1);}
	public String getSignLaNo2() {return checkGet(signLaNo2);}
	public void setSignLaNo2(String signLaNo2) {this.signLaNo2 = checkSet(signLaNo2);}
	public String getSignLaNo3() {return checkGet(signLaNo3);}
	public void setSignLaNo3(String signLaNo3) {this.signLaNo3 = checkSet(signLaNo3);}
	public String getSignLaNo4() {return checkGet(signLaNo4);}
	public void setSignLaNo4(String signLaNo4) {this.signLaNo4 = checkSet(signLaNo4);}
	public String getSignLaNo5() {return checkGet(signLaNo5);}
	public void setSignLaNo5(String signLaNo5) {this.signLaNo5 = checkSet(signLaNo5);}
	public String getSignBuNo() {return checkGet(signBuNo);}
	public void setSignBuNo(String signBuNo) {this.signBuNo = checkSet(signBuNo);}
	public String getSignBuNo1() {return checkGet(signBuNo1);}
	public void setSignBuNo1(String signBuNo1) {this.signBuNo1 = checkSet(signBuNo1);}
	public String getSignBuNo2() {return checkGet(signBuNo2);}
	public void setSignBuNo2(String signBuNo2) {this.signBuNo2 = checkSet(signBuNo2);}
	public String getSignBuNo3() {return checkGet(signBuNo3);}
	public void setSignBuNo3(String signBuNo3) {this.signBuNo3 = checkSet(signBuNo3);}
	public String getSignBuNo4() {return checkGet(signBuNo4);}
	public void setSignBuNo4(String signBuNo4) {this.signBuNo4 = checkSet(signBuNo4);}
	public String getSignBuNo5() {return checkGet(signBuNo5);}
	public void setSignBuNo5(String signBuNo5) {this.signBuNo5 = checkSet(signBuNo5);}

	public String getPropertyKind(){ return checkGet(propertyKind); }
	public void setPropertyKind(String s){ propertyKind=checkSet(s); }
	public String getFundType(){ return checkGet(fundType); }
	public void setFundType(String s){ fundType=checkSet(s); }
	public String getValuable(){ return checkGet(valuable); }
	public void setValuable(String s){ valuable=checkSet(s); }
	public String getTaxCredit(){ return checkGet(taxCredit); }
	public void setTaxCredit(String s){ taxCredit=checkSet(s); }
	public String getSourceDate(){ return checkGet(sourceDate); }
	public void setSourceDate(String s){ sourceDate=checkSet(s); }
	public String getOldPropertyNo(){ return checkGet(oldPropertyNo); }
	public void setOldPropertyNo(String s){ oldPropertyNo=checkSet(s); }
	public String getOldPropertyName(){ return checkGet(oldPropertyName); }
	public void setOldPropertyName(String s){ oldPropertyName=checkSet(s); }
	public String getOldSerialNo(){ return checkGet(oldSerialNo); }
	public void setOldSerialNo(String s){ oldSerialNo=checkSet(s); }
	public String getCaseNo(){ return checkGet(caseNo); }
	public void setCaseNo(String s){ caseNo=checkSet(s); }

	public String getQ_dataState(){ return checkGet(q_dataState); }
	public void setQ_dataState(String s){ q_dataState=checkSet(s); }
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

	public String getQ_signLaNo1() {return checkGet(q_signLaNo1);}
	public void setQ_signLaNo1(String qSignLaNo1) {q_signLaNo1 = checkSet(qSignLaNo1);}
	public String getQ_signLaNo2() {return checkGet(q_signLaNo2);}
	public void setQ_signLaNo2(String qSignLaNo2) {q_signLaNo2 = checkSet(qSignLaNo2);}
	public String getQ_signLaNo3() {return checkGet(q_signLaNo3);}
	public void setQ_signLaNo3(String qSignLaNo3) {q_signLaNo3 = checkSet(qSignLaNo3);}
	public String getQ_signLaNo4() {return checkGet(q_signLaNo4);}
	public void setQ_signLaNo4(String qSignLaNo4) {q_signLaNo4 = checkSet(qSignLaNo4);}
	public String getQ_signLaNo5() {return checkGet(q_signLaNo5);}
	public void setQ_signLaNo5(String qSignLaNo5) {q_signLaNo5 = checkSet(qSignLaNo5);}
	public String getQ_signLaNo() {return checkGet(q_signLaNo);}
	public void setQ_signLaNo(String qSignLaNo) {q_signLaNo = checkSet(qSignLaNo);}
	public String getQ_signBuNo1() {return checkGet(q_signBuNo1);}
	public void setQ_signBuNo1(String qSignBuNo1) {q_signBuNo1 = checkSet(qSignBuNo1);}
	public String getQ_signBuNo2() {return checkGet(q_signBuNo2);}
	public void setQ_signBuNo2(String qSignBuNo2) {q_signBuNo2 = checkSet(qSignBuNo2);}
	public String getQ_signBuNo3() {return checkGet(q_signBuNo3);}
	public void setQ_signBuNo3(String qSignBuNo3) {q_signBuNo3 = checkSet(qSignBuNo3);}
	public String getQ_signBuNo4() {return checkGet(q_signBuNo4);}
	public void setQ_signBuNo4(String qSignBuNo4) {q_signBuNo4 = checkSet(qSignBuNo4);}
	public String getQ_signBuNo5() {return checkGet(q_signBuNo5);}
	public void setQ_signBuNo5(String qSignBuNo5) {q_signBuNo5 = checkSet(qSignBuNo5);}
	public String getQ_signBuNo() {return checkGet(q_signBuNo);}
	public void setQ_signBuNo(String qSignBuNo) {q_signBuNo = checkSet(qSignBuNo);}

	public String getQ_propertyKind(){ return checkGet(q_propertyKind); }
	public void setQ_propertyKind(String s){ q_propertyKind=checkSet(s); }
	public String getQ_fundType(){ return checkGet(q_fundType); }
	public void setQ_fundType(String s){ q_fundType=checkSet(s); }
	public String getQ_valuable(){ return checkGet(q_valuable); }
	public void setQ_valuable(String s){ q_valuable=checkSet(s); }
	public String getQ_taxCredit(){ return checkGet(q_taxCredit); }
	public void setQ_taxCredit(String s){ q_taxCredit=checkSet(s); }
	public String getQ_caseNo(){ return checkGet(q_caseNo); }
	public void setQ_caseNo(String s){ q_caseNo=checkSet(s); }
	public String getQ_adjustDate(){ return checkGet(q_adjustDate); }
	public void setQ_adjustDate(String s){ q_adjustDate=checkSet(s); }
	public String getQ_verify(){ return checkGet(q_verify); }
	public void setQ_verify(String s){ q_verify=checkSet(s); }
	public String getQ_cause(){ return checkGet(q_cause); }
	public void setQ_cause(String s){ q_cause=checkSet(s); }
	public String getQ_cause1(){ return checkGet(q_cause1); }
	public void setQ_cause1(String s){ q_cause1=checkSet(s); }
	public String getQ_cause2(){ return checkGet(q_cause2); }
	public void setQ_cause2(String s){ q_cause2=checkSet(s); }
	public String getQ_enterOrg(){ return checkGet(q_enterOrg); }
	public void setQ_enterOrg(String s){ q_enterOrg=checkSet(s); }
	public String getQ_ownership(){ return checkGet(q_ownership); }
	public void setQ_ownership(String s){ q_ownership=checkSet(s); }
	public String getQ_propertyName1() {return checkGet(q_propertyName1);}
	public void setQ_propertyName1(String qPropertyName1) {q_propertyName1 = checkSet(qPropertyName1);}

	String toggleAll;
	public String getToggleAll(){ if (!"".equals(checkGet(toggleAll))) return "checked"; else return "unchecked"; }    
	public void setToggleAll(String s){ toggleAll=checkSet(s); }    

	String closing;
	public String getClosing(){ return checkGet(closing); }
	public void setClosing(String s){ closing=checkSet(s); }

	String q_addBookValue;
	String q_reduceBookValue;
	public String getQ_addBookValue() {return checkGet(q_addBookValue);}
	public void setQ_addBookValue(String q_addBookValue) {this.q_addBookValue = checkSet(q_addBookValue);}
	public String getQ_reduceBookValue() {return checkGet(q_reduceBookValue);}
	public void setQ_reduceBookValue(String q_reduceBookValue) {this.q_reduceBookValue = checkSet(q_reduceBookValue);}

	String verifyError;

	public String getVerifyError(){ return checkGet(verifyError); }
	public void setVerifyError(String s){ verifyError=checkSet(s); }

	String q_keepUnit;
	String q_keeper;
	String q_useUnit;
	String q_userNo;
	String q_newEnterOrg;
	String q_newEnterOrgName;
	String q_transferDate;
	String q_reduceDate;
	public String getQ_keepUnit() {return checkGet(q_keepUnit);}
	public void setQ_keepUnit(String qKeepUnit) {q_keepUnit = checkSet(qKeepUnit);}
	public String getQ_keeper() {return checkGet(q_keeper);}
	public void setQ_keeper(String qKeeper) {q_keeper = checkSet(qKeeper);}
	public String getQ_useUnit() {return checkGet(q_useUnit);}
	public void setQ_useUnit(String qUseUnit) {q_useUnit = checkSet(qUseUnit);}
	public String getQ_userNo() {return checkGet(q_userNo);}
	public void setQ_userNo(String qUserNo) {q_userNo = checkSet(qUserNo);}
	public String getQ_newEnterOrg() {return checkGet(q_newEnterOrg);}
	public void setQ_newEnterOrg(String q_newEnterOrg) {this.q_newEnterOrg = checkSet(q_newEnterOrg);}
	public String getQ_newEnterOrgName() {return checkGet(q_newEnterOrgName);}
	public void setQ_newEnterOrgName(String q_newEnterOrgName) {this.q_newEnterOrgName = checkSet(q_newEnterOrgName);}	
	public String getQ_transferDate() {return checkGet(q_transferDate);}
	public void setQ_transferDate(String q_transferDate) {this.q_transferDate = checkSet(q_transferDate);}
	public String getQ_reduceDate() {return checkGet(q_reduceDate);}
	public void setQ_reduceDate(String q_reduceDate) {this.q_reduceDate = checkSet(q_reduceDate);}
	
	String userID;
	String groupID;
	public String getUserID(){ return checkGet(userID); }
	public void setUserID(String s){ userID=checkSet(s); }
	public String getGroupID(){ return checkGet(groupID); }
	public void setGroupID(String s){ groupID=checkSet(s); }
	
	String mergedivision;
	public String getMergedivision(){ return checkGet(mergedivision); }
	public void setMergedivision(String s){ mergedivision=checkSet(s); }
/**
 * <br>
 * <br>目的：傳回insert sql
 * <br>參數：無 
 * <br>傳回：無 ; 直接更改變數為新增成功與否
*/	
public void insert(){
	String[] execSQLArray;
	setEditDate(Datetime.getYYYMMDD());
	setEditTime(Datetime.getHHMMSS());		
	StringBuffer sbSQL = new StringBuffer("");
	Database db = new Database();  
	int getcut=0;
	if(getsKeySet()!=null)
		getcut = getsKeySet().length;	//有勾選的list中資料數
	String[] strKeys = null;
//	ResultSet rs = null;
	int i = 0;	
	
	SuperBean2 sbSource = null;
	UNTCH004F02 sbTarget = null;
	ReflectTools rf = new ReflectTools();
	try {
		for (i=0; i<getcut; i++) {
			sbTarget = new UNTCH004F02();
			strKeys = getsKeySet()[i].split(",");

			String checkPropertyNo1 = strKeys[3].substring(0,1);
			String checkPropertyNo3 = strKeys[3].substring(0,3);
			
			if("111".equals(checkPropertyNo3)){
				sbSource = new unt.rf.UNTRF002F(this.getClass().getSimpleName());				
			}else if("1".equals(checkPropertyNo1)){
				sbSource = new unt.la.UNTLA002F();
			}else if("2".equals(checkPropertyNo1)){
				sbSource = new unt.bu.UNTBU002F(this.getClass().getSimpleName());
			}else if("3".equals(checkPropertyNo1) || "4".equals(checkPropertyNo1) || "5".equals(checkPropertyNo1)){
				sbSource = new unt.mp.UNTMP002F(this.getClass().getSimpleName());
			}else if("8".equals(checkPropertyNo1)){
				sbSource = new unt.rt.UNTRT001F(this.getClass().getSimpleName());
			}else if("9".equals(checkPropertyNo1)){
				sbSource = new unt.vp.UNTVP001F();
			}
			
			sbSource.setEnterOrg(strKeys[0]);
			sbSource.setOwnership(strKeys[1]);
			sbSource.setDifferenceKind(strKeys[2]);
			sbSource.setPropertyNo(strKeys[3]);
			sbSource.setSerialNo(strKeys[4]);
		
			if(strKeys.length == 6){
				sbSource.setLotNo(strKeys[5]);
			}

			sbSource.queryOne();
			rf._setParameter_forNoLog_bothInteraction(sbSource, sbTarget);
			
			sbTarget.setCause(this.getQ_cause());
			sbTarget.setCause1(this.getQ_cause1());
			sbTarget.setCause2(this.getQ_cause2());
			sbTarget.setNewEnterOrg(this.getQ_newEnterOrg());
			sbTarget.setTransferDate(this.getQ_transferDate());
			sbTarget.setReturnPlace(this.getQ_returnPlace());
			sbTarget.setCaseNo(this.getQ_caseNo());
			sbTarget.setCaseSerialNo("");
			
			if(!"".equals(this.getMergedivision())){
				sbTarget.setMergeDivision(this.getMergedivision());
			}

			sbTarget.setOldDeprMethod(sbTarget.getDeprMethod());
			sbTarget.setOldBuildFeeCB(sbTarget.getBuildFeeCB());
			sbTarget.setOldDeprUnitCB(sbTarget.getDeprUnitCB());
			sbTarget.setOldDeprPark(sbTarget.getDeprPark());
			sbTarget.setOldDeprUnit(sbTarget.getDeprUnit());
			sbTarget.setOldDeprUnit1(sbTarget.getDeprUnit1());
			sbTarget.setOldDeprAccounts(sbTarget.getDeprAccounts());
			sbTarget.setOldScrapValue(sbTarget.getScrapValue());
			sbTarget.setOldDeprAmount(sbTarget.getDeprAmount());
			sbTarget.setOldAccumDepr(sbTarget.getAccumDepr());
			sbTarget.setOldApportionMonth(sbTarget.getApportionMonth());
			sbTarget.setOldMonthDepr(sbTarget.getMonthDepr());
			sbTarget.setNewDeprMethod(sbTarget.getDeprMethod());
			sbTarget.setNewBuildFeeCB(sbTarget.getBuildFeeCB());
			sbTarget.setNewDeprUnitCB(sbTarget.getDeprUnitCB());
			sbTarget.setNewDeprPark(sbTarget.getDeprPark());
			sbTarget.setNewDeprUnit(sbTarget.getDeprUnit());
			sbTarget.setNewDeprUnit1(sbTarget.getDeprUnit1());
			sbTarget.setNewDeprAccounts(sbTarget.getDeprAccounts());
			sbTarget.setNewScrapValue(sbTarget.getScrapValue());
			sbTarget.setNewDeprAmount(sbTarget.getDeprAmount());
			sbTarget.setNewAccumDepr(sbTarget.getAccumDepr());
			sbTarget.setNewApportionMonth(sbTarget.getApportionMonth());
			sbTarget.setNewMonthDepr(sbTarget.getMonthDepr());


			
			if("111".equals(checkPropertyNo3)){
			}else if("1".equals(checkPropertyNo1)){
				sbTarget.setLaArea(sbTarget.getArea());
				sbTarget.setLaHoldNume(sbTarget.getHoldNume());
				sbTarget.setLaHoldDeno(sbTarget.getHoldDeno());
				sbTarget.setLaHoldArea(sbTarget.getHoldArea());
				
				sbTarget.setLaSignNo1(sbTarget.getSignNo1());
				sbTarget.setLaSignNo2(sbTarget.getSignNo2());
				sbTarget.setLaSignNo3(sbTarget.getSignNo3());
				sbTarget.setLaSignNo4(sbTarget.getSignNo4());
				sbTarget.setLaSignNo5(sbTarget.getSignNo5());
				
			}else if("2".equals(checkPropertyNo1)){
				sbTarget.setBuArea(sbTarget.getArea());
				sbTarget.setBuSArea(sbTarget.getSArea());
				sbTarget.setBuCArea(sbTarget.getCArea());
				sbTarget.setBuHoldNume(sbTarget.getHoldNume());
				sbTarget.setBuHoldDeno(sbTarget.getHoldDeno());
				sbTarget.setBuHoldArea(sbTarget.getHoldArea());
				
				sbTarget.setBuSignNo1(sbTarget.getSignNo1());
				sbTarget.setBuSignNo2(sbTarget.getSignNo2());
				sbTarget.setBuSignNo3(sbTarget.getSignNo3());
				sbTarget.setBuSignNo4(sbTarget.getSignNo4());
				sbTarget.setBuSignNo5(sbTarget.getSignNo5());
			}else if("3".equals(checkPropertyNo1) || "4".equals(checkPropertyNo1) || "5".equals(checkPropertyNo1)){
				sbTarget.setOldBookAmount(sbTarget.getBookAmount());
				sbTarget.setOldBookUnit(sbTarget.getBookValue());
				sbTarget.setOldBookValue(sbTarget.getBookValue());
				sbTarget.setOldNetUnit(sbTarget.getNetValue());
				sbTarget.setOldNetValue(sbTarget.getNetValue());
				sbTarget.setAdjustBookAmount(sbTarget.getBookAmount());
				sbTarget.setAdjustAccumDepr(sbTarget.getAccumDepr());
				sbTarget.setAdjustBookUnit(sbTarget.getBookValue());
				sbTarget.setAdjustBookValue(sbTarget.getBookValue());
				// 比照單筆新增的規則
				if("".equals(sbTarget.getOriginalUnit())) { 
					sbTarget.setAdjustNetUnit(sbTarget.getNetValue());
				}else {
					sbTarget.setAdjustNetUnit(sbTarget.getOriginalUnit());
				}
				sbTarget.setAdjustNetValue(sbTarget.getNetValue());
				sbTarget.setNewBookAmount("0");
				sbTarget.setNewBookUnit("0");
				sbTarget.setNewBookValue("0");
				sbTarget.setNewNetUnit("0");
				sbTarget.setNewNetValue("0");
				
			}else if("8".equals(checkPropertyNo1)){
			}else if("9".equals(checkPropertyNo1)){
			}

			sbTarget._execInsertforDetail();			
			if("insertError".equals(getState())){
				setStateInsertError();
			}else{
				setStateInsertSuccess();
				setErrorMsg("新增完成");
			}
			
		}	
	} catch (Exception e) {
		e.printStackTrace();
	}
	
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
	Map PropertyMap = TCGHCommon.getSyspk_PropertyCode("000000000A"); 
	UNTCH_Tools ut = new UNTCH_Tools();
	try {
		
		String sql = "";
		
		if(!"untla056f".equals(dataSource)){

			sql = "select * from (" +
					"select " +
						"enterorg," +
						"ownership," +
						"differencekind," +
						"propertyno ," +
						"serialno," +
						"oldserialno," +
						"null as lotno," +
						"(select propertyname from SYSPK_PROPERTYCODE z where z.enterorg = a.enterorg and a.propertyno = z.propertyno) as propertyname," +
						"signno," +
						" ((select signdesc from SYSCA_SIGN z where z.signno = SUBSTRING(a.signno,0,8)) + SUBSTRING(a.signno,8,4) + '-' + SUBSTRING(a.signno,12,4)) as signNoName," +
						"(select propertyunit from SYSPK_PROPERTYCODE z where z.enterorg = a.enterorg and a.propertyno = z.propertyno) as propertyunit," +
						"'1' as bookamount ," +
						"bookvalue," +
						"netvalue," +
						"(select unitname from UNTMP_KEEPUNIT z where z.enterorg = a.enterorg and z.unitno = a.keepunit) as keepunit," +
						"(select keepername from UNTMP_KEEPER z where z.enterorg = a.enterorg and z.keeperno = a.keeper) as keeper," +
						"(select placename from SYSCA_PLACE z where z.enterorg = a.enterorg and z.placeno = a.place1) as place1name," +
						"null as useDate," +
						"null as limityear" +
						" ,chk.* " +
					" from UNTLA_LAND a " +
					this.getOtherProofCheck("LA") +
					" where 1=1 " +
					this.getCondition("LA") +
					" union" +
					" select " +
						"enterorg," +
						"ownership," +
						"differencekind," +
						"propertyno ," +
						"serialno," +
						"oldserialno," +
						"null as lotno," +
						"(select propertyname from SYSPK_PROPERTYCODE z where z.enterorg = a.enterorg and a.propertyno = z.propertyno) as propertyname," +
						"signno," +
						" ((select signdesc from SYSCA_SIGN z where z.signno = SUBSTRING(a.signno,0,8)) + SUBSTRING(a.signno,8,5) + '-' + SUBSTRING(a.signno,13,3)) as signNoName," +
						"(select propertyunit from SYSPK_PROPERTYCODE z where z.enterorg = a.enterorg and a.propertyno = z.propertyno) as propertyunit," +
						"'1' as bookamount ," +
						"bookvalue," +
						"netvalue," +
						"(select unitname from UNTMP_KEEPUNIT z where z.enterorg = a.enterorg and z.unitno = a.keepunit) as keepunit," +
						"(select keepername from UNTMP_KEEPER z where z.enterorg = a.enterorg and z.keeperno = a.keeper) as keeper," +
						"(select placename from SYSCA_PLACE z where z.enterorg = a.enterorg and z.placeno = a.place1) as place1name," +
						"(DATEDIFF(month,CONVERT(datetime,a.buydate),CONVERT(datetime,'" + ut._transToCE_Year(Datetime.getYYYMMDD()) + "')) ) as useDate," +
						"(select limityear from SYSPK_PROPERTYCODE z where z.propertyno = a.propertyno) as limityear" +
						" ,chk.*" +
					" from UNTBU_BUILDING a " +
					this.getOtherProofCheck("BU") + 
					" where 1=1 " +
					this.getCondition("BU") +
					" union" +
					" select " +
						"enterorg," +
						"ownership," +
						"differencekind," +
						"propertyno ," +
						"serialno," +
						"oldserialno," +
						"null as lotno," +
						"(select propertyname from SYSPK_PROPERTYCODE z where z.enterorg = a.enterorg and a.propertyno = z.propertyno) as propertyname," +
						"null as signno ," +
						"null as signnoname ," +
						"(select propertyunit from SYSPK_PROPERTYCODE z where z.enterorg = a.enterorg and a.propertyno = z.propertyno) as propertyunit ," +
						"'1' as bookamount ," +
						"bookvalue," +
						"netvalue," +
						"(select unitname from UNTMP_KEEPUNIT z where z.enterorg = a.enterorg and z.unitno = a.keepunit) as keepunit," +
						"(select keepername from UNTMP_KEEPER z where z.enterorg = a.enterorg and z.keeperno = a.keeper) as keeper," +
						"(select placename from SYSCA_PLACE z where z.enterorg = a.enterorg and z.placeno = a.place1) as place1name," +
						"(DATEDIFF(month,CONVERT(datetime,a.buydate),CONVERT(datetime,'" + ut._transToCE_Year(Datetime.getYYYMMDD()) + "')) ) as useDate," +
						"(select limityear from SYSPK_PROPERTYCODE z where z.propertyno = a.propertyno) as limityear" +
						" ,chk.* " +
					" from UNTRF_ATTACHMENT a " +
					this.getOtherProofCheck("RF") + 
					" where 1=1 " +
					this.getCondition("RF") +
					" union" +
					" select " +
						"b.enterorg," +
						"b.ownership," +
						"b.differencekind," +
						"b.propertyno ," +
						"a.serialno," +
						"a.oldserialno," +
						"b.lotno," +
						"(select distinct propertyname from SYSPK_PROPERTYCODE z where z.enterorg = b.enterorg and b.propertyno = z.propertyno) as propertyname," +
						"null as signno ," +
						"null as signnoname ," +
						"(select distinct propertyunit from SYSPK_PROPERTYCODE z where z.enterorg = b.enterorg and b.propertyno = z.propertyno) as propertyunit ," +
						"a.bookamount ," +
						"a.bookvalue," +
						"a.netvalue," +
						"(select unitname from UNTMP_KEEPUNIT z where z.enterorg = b.enterorg and z.unitno = a.keepunit) as keepunit," +
						"(select keepername from UNTMP_KEEPER z where z.enterorg = b.enterorg and z.keeperno = a.keeper) as keeper," +
						"(select placename from SYSCA_PLACE z where z.enterorg = b.enterorg and z.placeno = a.place1) as place1name," +
						"(DATEDIFF(month,CONVERT(datetime,b.buydate),CONVERT(datetime,'" + ut._transToCE_Year(Datetime.getYYYMMDD()) + "')) ) as useDate," +
						"case (select distinct limityear from SYSPK_PROPERTYCODE z where z.propertyno = b.propertyno and z.enterorg = b.enterorg) when null then (select distinct limityear from SYSPK_PROPERTYCODE z where z.propertyno in ('000000000A')) end as limityear" +
						",chk.* " +
					" from UNTMP_MOVABLE b" +
					" join UNTMP_MOVABLEDETAIL a on a.enterorg = b.enterorg and a.ownership = b.ownership and a.differencekind = b.differencekind and a.lotno = b.lotno and a.propertyno = b.propertyno" +
					this.getOtherProofCheck("MP") +
						" where 1=1 "+
						this.getCondition("MP") +
					" union" +
					" select " +
						"enterorg," +
						"ownership," +
						"differencekind," +
						"propertyno ," +
						"serialno," +
						"oldserialno," +
						"null as lotno," +
						"(select propertyname from SYSPK_PROPERTYCODE z where z.enterorg = a.enterorg and a.propertyno = z.propertyno) as propertyname," +
						"null as signno ," +
						"null as signnoname ," +
						"(select propertyunit from SYSPK_PROPERTYCODE z where z.enterorg = a.enterorg and a.propertyno = z.propertyno) as propertyunit ," +
						"'1' as bookamount ," +
						"bookvalue," +
						"null as netvalue," +
						"(select unitname from UNTMP_KEEPUNIT z where z.enterorg = a.enterorg and z.unitno = a.keepunit) as keepunit," +
						"(select keepername from UNTMP_KEEPER z where z.enterorg = a.enterorg and z.keeperno = a.keeper) as keeper," +
						"(select placename from SYSCA_PLACE z where z.enterorg = a.enterorg and z.placeno = a.place1) as place1name," +
						"(DATEDIFF(month,CONVERT(datetime,a.buydate),CONVERT(datetime,'" + ut._transToCE_Year(Datetime.getYYYMMDD()) + "')) ) as useDate," +
						"a.originallimityear as limityear" +
						",chk.* " +
					" from UNTRT_ADDPROOF a " +
						this.getOtherProofCheck("RT") +
					" where 1=1 " +
						getCondition("RT") +
					" union" +
					" select " +
						"enterorg," +
						"ownership," +
						"differencekind," +
						"propertyno ," +
						"serialno," +
						"oldserialno," +
						"null as lotno," +
						"(select propertyname from SYSPK_PROPERTYCODE z where z.enterorg = a.enterorg and a.propertyno = z.propertyno) as propertyname," +
						"null as signno ," +
						"null as signnoname ," +
						"(select propertyunit from SYSPK_PROPERTYCODE z where z.enterorg = a.enterorg and a.propertyno = z.propertyno) as propertyunit ," +
						"bookamount ," +
						"bookvalue," +
						"null as netvalue," +
						"(select unitname from UNTMP_KEEPUNIT z where z.enterorg = a.enterorg and z.unitno = a.keepunit) as keepunit," +
						"(select keepername from UNTMP_KEEPER z where z.enterorg = a.enterorg and z.keeperno = a.keeper) as keeper," +
						"(select placename from SYSCA_PLACE z where z.enterorg = a.enterorg and z.placeno = a.place1) as place1name," +
						"null as useDate," +
						"null as limityear" +
						",chk.* " +
					" from UNTVP_ADDPROOF a " +
						this.getOtherProofCheck("VP") +
					" where 1=1 " +
						getCondition("VP") +
					") b order by propertyno, serialno";
		}else{
			sql = "select * from (" +
					"select " +
						"enterorg," +
						"ownership," +
						"differencekind," +
						"propertyno ," +
						"serialno," +
						"oldserialno," +
						"null as lotno," +
						"(select propertyname from SYSPK_PROPERTYCODE z where z.enterorg = a.enterorg and a.propertyno = z.propertyno) as propertyname," +
						"signno," +
						" ((select signdesc from SYSCA_SIGN z where z.signno = SUBSTRING(a.signno,0,8)) + SUBSTRING(a.signno,8,4) + '-' + SUBSTRING(a.signno,12,4)) as signNoName," +
						"(select propertyunit from SYSPK_PROPERTYCODE z where z.enterorg = a.enterorg and a.propertyno = z.propertyno) as propertyunit," +
						"'1' as bookamount ," +
						"bookvalue," +
						"netvalue," +
						"(select unitname from UNTMP_KEEPUNIT z where z.enterorg = a.enterorg and z.unitno = a.keepunit) as keepunit," +
						"(select keepername from UNTMP_KEEPER z where z.enterorg = a.enterorg and z.keeperno = a.keeper) as keeper," +
						"(select placename from SYSCA_PLACE z where z.enterorg = a.enterorg and z.placeno = a.place1) as place1name," +
						"null as useDate," +
						"null as limityear," +
						" chk.* " +
					" from UNTLA_LAND a " +
					this.getOtherProofCheck("LA") +
					" where 1=1 " +
						getCondition("LA") + 
					") b order by propertyno, serialno";
		}
		
		int count = 0;
		ResultSet rs = db.querySQL_NoChange(sql);
		while(rs.next()){			
			String isOverLimitYear = "";
			String useDate = "";
			if("".equals(checkGet(rs.getString("useDate")))){
			}else{		
					if("".equals(checkGet(rs.getString("limityear")))){				
					}else{
						//未達年限
						if(Integer.parseInt(rs.getString("useDate")) > Integer.parseInt(rs.getString("limityear"))*12){					
							isOverLimitYear = "否";
						}else{
							isOverLimitYear = "是";
						}
					}
				
			}
			
			
			String rowArray[]=new String[21];
			rowArray[0]=checkGet(rs.getString("enterOrg"));
			rowArray[1]=checkGet(rs.getString("ownership"));
			rowArray[2]=checkGet(rs.getString("differenceKind"));
			rowArray[3]=checkGet(rs.getString("propertyNo")); 
			rowArray[4]=checkGet(rs.getString("serialNo"));
			rowArray[5]=checkGet(rs.getString("oldserialNo"));
			rowArray[6]=checkGet(rs.getString("lotNo"));
			rowArray[7]=checkGet((String)PropertyMap.get(rs.getString("propertyno")));
			rowArray[8]=checkGet(rs.getString("propertyname"));
			rowArray[9]=checkGet(rs.getString("signNo")); 
			rowArray[10]=checkGet(rs.getString("signNoName"));
			rowArray[11]=checkGet(ut._getPropertyUnit(rs.getString("propertyno"))); 
			rowArray[12]=checkGet(rs.getString("bookamount")); 
			rowArray[13]=checkGet(rs.getString("bookvalue"));
			rowArray[14]=checkGet(rs.getString("netvalue"));
			rowArray[15]=checkGet(rs.getString("keepunit"));
			rowArray[16]=checkGet(rs.getString("keeper"));
			rowArray[17]=checkGet(rs.getString("place1Name"));
			rowArray[18]=isOverLimitYear;
			
			if("".equals(checkGet(rs.getString("useDate")))){
				rowArray[19]="";
			}else{
				String useYear = String.valueOf(Integer.parseInt(rs.getString("useDate")) / 12);
				String useMonth = String.valueOf(Integer.parseInt(rs.getString("useDate")) % 12);
				rowArray[19]=useYear + "年" + useMonth + "月";					
			}
			
			String chkproofdesc = Common.concatStrs("字第", Common.concatStrs("年", rs.getString("chkproofyear"), rs.getString("chkproofdoc")), rs.getString("chkproofno"));
			if (!"".equals(chkproofdesc)) {
				rowArray[20] = checkGet(chkproofdesc + "號");
			} else {
				rowArray[20] = "";
			}
			
			objList.add(rowArray);
			count++;
		}
		setStateQueryAllSuccess();
	} catch (Exception e) {
		e.printStackTrace();
	} finally {
		db.closeAll();
	}
	return objList;
}

private String getOtherProofCheck(String table) {
	StringBuilder sql = new StringBuilder();
	if ("RT".equals(table) || "VP".equals(table)) {
		sql.append(" OUTER APPLY ( ")
		   .append("  select top 1 x.* from ( ")
		   .append("   select p.verify as chkverify ,isnull(p.proofyear, '') as chkproofyear ,isnull(p.proofdoc, '') as chkproofdoc ,isnull(p.proofno, '') as chkproofno")
		   .append("   from UNT" + table + "_ADJUSTPROOF p  ")
		   .append("   where p.enterorg = a.enterorg and p.ownership = a.ownership and p.differencekind = a.differencekind and p.propertyno = a.propertyno and p.serialno = a.serialno and p.verify!='Y' ")
		   .append("   union all")
		   .append("   select p.verify as chkverify ,isnull(p.proofyear, '') as chkproofyear ,isnull(p.proofdoc, '') as chkproofdoc ,isnull(p.proofno, '') as chkproofno ")
		   .append("   from UNT" + table + "_REDUCEPROOF p ")
		   .append("   where p.enterorg = a.enterorg and p.ownership = a.ownership and p.differencekind = a.differencekind and p.propertyno = a.propertyno and p.serialno = a.serialno and p.verify!='Y'")
		   .append("  ) as x ")
		   .append(" ) as chk")
		   .append("");
	} else {
		sql.append(" OUTER APPLY ( ")
		   .append("  select top 1 x.* from ( ")
		   .append("   select p.verify as chkverify ,isnull(p.proofyear, '') as chkproofyear ,isnull(p.proofdoc, '') as chkproofdoc ,isnull(p.proofno, '') as chkproofno")
		   .append("   from UNT" + table + "_ADJUSTDETAIL d  ")
		   .append("   left join UNT" + table + "_ADJUSTPROOF p on d.enterorg = p.enterorg and d.caseno = p.caseno ")
		   .append("   where d.enterorg = a.enterorg and d.ownership = a.ownership and d.differencekind = a.differencekind and d.propertyno = a.propertyno and d.serialno = a.serialno and d.verify!='Y' ")
		   .append("  union all  ")
		   .append("   select p.verify as chkverify ,isnull(p.proofyear, '') as chkproofyear ,isnull(p.proofdoc, '') as chkproofdoc ,isnull(p.proofno, '') as chkproofno ")
		   .append("   from UNT" + table + "_REDUCEDETAIL d ")
		   .append("   left join UNT" + table + "_REDUCEPROOF p on d.enterorg = p.enterorg and d.caseno = p.caseno ")
		   .append("   where d.enterorg = a.enterorg and d.ownership = a.ownership and d.differencekind = a.differencekind and d.propertyno = a.propertyno and d.serialno = a.serialno and d.verify!='Y'")
		   .append("  ) as x ")
		   .append(" ) as chk")
		   .append("");
	}
	
	
	return sql.toString();
}

private String getCondition(String table){
	String sql = "";
	String queryTable = "";
	if("RT".equals(table) || "VP".equals(table)){	queryTable = "PROOF";
	}else{											queryTable = "DETAIL";
	}	
	sql += " and a.enterorg 	= " + Common.sqlChar(q_enterOrg) +
				" and a.ownership 	= " + Common.sqlChar(q_ownership) +
				" and a.differencekind 	= " + Common.sqlChar(q_differenceKind) +
				" and a.datastate = " + Common.sqlChar(q_dataState) +
				" and a.verify = 'Y'" +
				"";
				
	if (!"".equals(getQ_propertyNoS()))
		sql+=" and a.propertyno >= " + Common.sqlChar(getQ_propertyNoS());		
	if (!"".equals(getQ_propertyNoE()))
		sql+=" and a.propertyno <= " + Common.sqlChar(getQ_propertyNoE());						
	if (!"".equals(getQ_serialNoS()))
		sql+=" and a.serialno >= " + Common.sqlChar(getQ_serialNoS());		
	if (!"".equals(getQ_serialNoE()))
		sql+=" and a.serialno <= " + Common.sqlChar(getQ_serialNoE());			

	if (!"".equals(getQ_propertyKind())){
		if("MP".equals(table)){
			sql+=" and b.propertykind = " + Common.sqlChar(getQ_propertyKind()) ;
		} else {
			sql+=" and a.propertykind = " + Common.sqlChar(getQ_propertyKind()) ;
		}
	}
	if (!"".equals(getQ_fundType())){
		if("MP".equals(table)){
			sql+=" and b.fundtype = " + Common.sqlChar(getQ_fundType()) ;
		}else{
			sql+=" and a.fundtype = " + Common.sqlChar(getQ_fundType()) ;
		}
	}
	if (!"".equals(getQ_valuable())){
		if("MP".equals(table)){
			sql+=" and b.valuable = " + Common.sqlChar(getQ_valuable()) ;
		}else{
			if(!"RT".equals(table) && !"VP".equals(table)){
				sql+=" and a.valuable = " + Common.sqlChar(getQ_valuable()) ;
			}
		}
	}
	if (!"".equals(getQ_taxCredit())){
		if(!"MP".equals(table) && !"RT".equals(table) && !"VP".equals(table)){
			sql+=" and a.taxcredit = " + Common.sqlChar(getQ_taxCredit()) ;
		}
	}

	if (!"".equals(getQ_keepUnit())){ 
		sql += " and a.keepunit = " + Common.sqlChar(getQ_keepUnit());
	}		
	if (!"".equals(getQ_keeper())){ 
		sql += " and a.keeper = " + Common.sqlChar(getQ_keeper());
	}
//	為了看到別的保管人財產，先註解起來
//	if (getGroupID().startsWith("keeper")) {
//		sql += " and a.keeper = " + Common.sqlChar(getUserID()) ;
//	}
	if (!"".equals(getQ_useUnit())){ 
		sql += " and a.useunit = " + Common.sqlChar(getQ_useUnit());
	}
	if (!"".equals(getQ_userNo())){ 
		sql += " and a.userno = " + Common.sqlChar(getQ_userNo());
	}
	if (!"".equals(getQ_propertyName1())){ 
		if("MP".equals(table)){
			sql += " and b.propertyname1 like " + Common.sqlChar("%" + getQ_propertyName1() + "%");
		}else{
			sql += " and a.propertyname1 like " + Common.sqlChar("%" + getQ_propertyName1() + "%");
		}
	}
	
	if (!"".equals(getQ_place())){ 
		sql += " and a.place1 = " + Common.sqlChar(getQ_place());
	}
	if (!"".equals(getQ_placeNote())){ 
		sql += " and a.place like " + Common.sqlChar("%" + getQ_placeNote() + "%");
	}
	
	if (!"".equals(getQ_userNote())){ 
		sql += " and a.usernote like " + Common.sqlChar("%" + getQ_userNote() + "%");
	}
	
	if (!"".equals(getQ_engineeringNo())){ 
		sql += " and a.engineeringno = " + Common.sqlChar(getQ_engineeringNo());
	}
	
	String q_signLaNo = "";
	
	if (!"".equals(Common.get(getQ_signLaNo1()))){
		q_signLaNo = getQ_signLaNo1().substring(0,1)+"______";
	}
	if (!"".equals(getQ_signLaNo2())){
		q_signLaNo = getQ_signLaNo2().substring(0,3)+"____";			
	}
	if (!"".equals(getQ_signLaNo3())){
		q_signLaNo = getQ_signLaNo3();
	}
	
		
	String q_signBuNo = "";
	if (!"".equals(getQ_signBuNo1())){
		q_signBuNo = getQ_signBuNo1().substring(0,1)+"______";
	}
	if (!"".equals(getQ_signBuNo2())){
		q_signBuNo = getQ_signBuNo2().substring(0,3)+"____";
	}
	if (!"".equals(getQ_signBuNo3())){
		q_signBuNo = getQ_signBuNo3();
	}
		

	if (!"".equals(q_signLaNo) || !"".equals(q_signBuNo)){
		if (!"".equals(q_signLaNo) && "LA".equals(table)){
			sql += " and signno like '" + q_signLaNo + "%'";
			
			if (!"".equals(getQ_signLaNo4S())){
				sql += " and substring(signno,8,8) >= '" + getQ_signLaNo4S() + getQ_signLaNo5S() + "'";				
			}	
			if (!"".equals(getQ_signLaNo4E())){
				sql += " and substring(signno,8,8) <= '" + getQ_signLaNo4E() + getQ_signLaNo5E() + "'";				
			}
			
			
		}else if (!"".equals(q_signBuNo) && "BU".equals(table)){
			sql += " and signno like '" + q_signBuNo + "%'";
			
			if (!"".equals(getQ_signBuNo4S())){
				sql += " and substring(signno,8,8) >= '" + getQ_signBuNo4S() + getQ_signBuNo5S() + "'";				
			}	
			if (!"".equals(getQ_signBuNo4E())){
				sql += " and substring(signno,8,8) <= '" + getQ_signBuNo4E() + getQ_signBuNo5E() + "'";				
			}					
			
		}else{
			sql += " and 1 != 1";
		}
		
	}
	
	//已逾使用年限未報廢
	if ("Y".equals(this.getQ_overLimitYear())){
		if("MP".equals(table)){
			sql += " and (substring(" +
					"(select b.buydate from UNTMP_MOVABLE b where a.enterorg = b.enterorg and a.ownership = b.ownership and a.differencekind = b.differencekind and a.lotno = b.lotno and a.propertyno = b.propertyno)" + 
					",1,6) + (select limityear from SYSPK_PROPERTYCODE z where z.propertyno = a.propertyno) * 100) < " + Datetime.getYYYYMM();
		}else{
			sql += " and (substring(a.buydate,1,6) + (select limityear from SYSPK_PROPERTYCODE z where z.propertyno = a.propertyno and z.enterorg IN (a.enterorg,'000000000A')) * 100) < " + Datetime.getYYYYMM();
		}
	}
	
	//減損原因為「已達年限不堪使用(214)」，應控制只能查詢「已達年限」之資料
	//			不必折舊：系統日 >= (購置日期＋年限)
	//			折舊：系統年月 > (購置年月＋年限)
	//減損原因為「未達年限不堪使用(215)」，應控制只能帶入「未達年限」之資料。
	//			不必折舊：系統日 < (購置日期＋年限)
	//			折舊：系統年月 <= (購置年月＋年限)
	if("BU".equals(table) || "RF".equals(table) || "MP".equals(table)){
		if("214".equals(this.getCause())){
			sql += " and " +
						" case a.deprmethod when '01'" +
						" then" + 
							" case when '" + Datetime.getYYYYMMDD() + "' >= cast(" + ("MP".equals(table)?"b.buydate":"a.buydate") + " as int) + cast((select limityear from SYSPK_PROPERTYCODE z where z.propertyno = a.propertyno and z.enterorg IN (a.enterorg,'000000000A')) as int) * 10000" +
							" then 'Y'" +
							" else 'N'" +
							" end" +
						" else " +
							" case when '" + Datetime.getYYYYMM() + "' > cast(substring(" + ("MP".equals(table)?"b.buydate":"a.buydate") + ",1,6) as int) + cast((select limityear from SYSPK_PROPERTYCODE z where z.propertyno = a.propertyno and z.enterorg IN (a.enterorg,'000000000A')) as int) * 100" +
							" then 'Y'" +
							" else 'N'" +
							" end" +
						" end" +
					" = 'Y'";
		}else if("215".equals(this.getCause())){
			sql += " and " +
					" case a.deprmethod when '01'" +
					" then" + 
						" case when '" + Datetime.getYYYYMMDD() + "' < cast(" + ("MP".equals(table)?"b.buydate":"a.buydate") + " as int) + cast((select limityear from SYSPK_PROPERTYCODE z where z.propertyno = a.propertyno and z.enterorg IN (a.enterorg,'000000000A')) as int) * 10000" +
						" then 'Y'" +
						" else 'N'" +
						" end" +
					" else " +
						" case when '" + Datetime.getYYYYMM() + "' <= cast(substring(" + ("MP".equals(table)?"b.buydate":"a.buydate") + ",1,6) as int) + cast((select limityear from SYSPK_PROPERTYCODE z where z.propertyno = a.propertyno and z.enterorg IN (a.enterorg,'000000000A')) as int) * 100" +
						" then 'Y'" +
						" else 'N'" +
						" end" +
					" end" +
				" = 'Y'";
			
		}
	
	}
	
	return sql;
}


}
