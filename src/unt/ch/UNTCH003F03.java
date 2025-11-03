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

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.ResultSet;
import java.util.ArrayList;

import unt.mp.UNTMP002F;
import util.Common;
import util.Database;
import util.Datetime;
import util.SuperBean2;
import TDlib_Simple.tools.src.MathTools;
import TDlib_Simple.tools.src.ReflectTools;

public class UNTCH003F03 extends UNTCH003Q{

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

String toggleAll;
public String getToggleAll(){ if (!"".equals(checkGet(toggleAll))) return "checked"; else return "unchecked"; }    
public void setToggleAll(String s){ toggleAll=checkSet(s); }    

String closing;
public String getClosing(){ return checkGet(closing); }
public void setClosing(String s){ closing=checkSet(s); }


	private String q_addLimitYear;
	private String q_reduceLimitYear;
	public String getQ_addLimitYear() { return checkGet(q_addLimitYear); }
	public void setQ_addLimitYear(String q_addLimitYear) { this.q_addLimitYear = checkSet(q_addLimitYear); }
	public String getQ_reduceLimitYear() { return checkGet(q_reduceLimitYear); }
	public void setQ_reduceLimitYear(String q_reduceLimitYear) { this.q_reduceLimitYear = checkSet(q_reduceLimitYear); }

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
public String getQ_keepUnit() {return checkGet(q_keepUnit);}
public void setQ_keepUnit(String qKeepUnit) {q_keepUnit = checkSet(qKeepUnit);}
public String getQ_keeper() {return checkGet(q_keeper);}
public void setQ_keeper(String qKeeper) {q_keeper = checkSet(qKeeper);}
public String getQ_useUnit() {return checkGet(q_useUnit);}
public void setQ_useUnit(String qUseUnit) {q_useUnit = checkSet(qUseUnit);}
public String getQ_userNo() {return checkGet(q_userNo);}
public void setQ_userNo(String qUserNo) {q_userNo = checkSet(qUserNo);}

	
	private String engineeringNo;
	public String getEngineeringNo() { return checkGet(engineeringNo); }
	public void setEngineeringNo(String engineeringNo) { this.engineeringNo = checkSet(engineeringNo); }
	
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
	UNTCH003F02 sbTarget = new UNTCH003F02();
	ReflectTools rf = new ReflectTools();
	UNTCH_Tools ul = new UNTCH_Tools();
	try {
		sbTarget.setClosing1ym(this.queryClosingYMfromUNTAC_CLOSINGPT(this.getEnterOrg(), this.getDifferenceKind()));
		
		for (i=0; i<getcut; i++) {	
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
				sbSource = new UNTMP002F(this.getClass().getSimpleName());
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
			if ("3".equals(checkPropertyNo1) || "4".equals(checkPropertyNo1) || "5".equals(checkPropertyNo1)) {
				// untmp002f query one 的OriginalApportionEndYM 是主檔的且被轉成民國年
				((UNTMP002F) sbSource).setOriginalApportionEndYM(((UNTMP002F) sbSource).getDtlOriginalApportionEndYMRO());
			}
			rf._setParameter_forNoLog_bothInteraction(sbSource, sbTarget);
			
			sbTarget.setCause(this.getQ_cause());
			sbTarget.setCause1(this.getQ_cause1());
			sbTarget.setCause2(this.getQ_cause2());
			sbTarget.setAddLimitYear(this.getQ_addLimitYear());
			sbTarget.setReduceLimitYear(this.getQ_reduceLimitYear());
			sbTarget.setAddBookValue(this.getQ_addBookValue());
			sbTarget.setAddNetValue(this.getQ_addBookValue());
			sbTarget.setReduceBookValue(this.getQ_reduceBookValue());
			sbTarget.setCaseNo(this.getQ_caseNo());
			sbTarget.setCaseSerialNo("");
			sbTarget.setEngineeringNo(this.getEngineeringNo());
			
			sbTarget.setOldBookValue(sbTarget.getBookValue());
			sbTarget.setOldNetValue(sbTarget.getNetValue());
			sbTarget.setOriginalBV(sbTarget.getOriginalBV());
			
			sbTarget.setOldDeprMethod(sbTarget.getOriginalDeprMethod());			
			sbTarget.setOldBuildFeeCB(sbTarget.getOriginalBuildFeeCB());
			sbTarget.setOldDeprUnitCB(sbTarget.getOriginalDeprUnitCB());
			sbTarget.setOldDeprPark(sbTarget.getOriginalDeprPark());
			sbTarget.setOldDeprUnit(sbTarget.getOriginalDeprUnit());
			sbTarget.setOldDeprUnit1(sbTarget.getOriginalDeprUnit1());
			sbTarget.setOldDeprAccounts(sbTarget.getOriginalDeprAccounts());
			sbTarget.setOldScrapValue(sbTarget.getOriginalScrapValue());
			sbTarget.setOldDeprAmount(sbTarget.getOriginalDeprAmount());
			sbTarget.setOldAccumDepr(sbTarget.getAccumDepr());
			sbTarget.setOldApportionMonth(sbTarget.getOriginalApportionMonth());
			sbTarget.setOldMonthDepr(sbTarget.getOriginalMonthDepr());
			sbTarget.setOldMonthDepr1(sbTarget.getOriginalMonthDepr1());
			sbTarget.setOldApportionEndYM(sbTarget.getOriginalApportionEndYM());
			
			sbTarget.setNewDeprMethod(sbTarget.getDeprMethod());
			sbTarget.setNewBuildFeeCB(sbTarget.getBuildFeeCB());
			sbTarget.setNewDeprUnitCB(sbTarget.getDeprUnitCB());
			sbTarget.setNewDeprPark(sbTarget.getDeprPark());
			sbTarget.setNewDeprUnit(sbTarget.getDeprUnit());
			sbTarget.setNewDeprUnit1(sbTarget.getDeprUnit1());
			sbTarget.setNewDeprAccounts(sbTarget.getDeprAccounts());
			sbTarget.setNewScrapValue(sbTarget.getScrapValue());
			
			// 不必折舊 or 珍貴財產不計算折舊欄位相關
			boolean calDeprVal = "01".equals(sbTarget.getDeprMethod()) || "Y".equals(sbTarget.getValuable()) ? false : true;
			
			MathTools mt = new MathTools();
			int addLimitYear = Common.getInt(sbTarget.getAddLimitYear());
			int reduceLimitYear = Common.getInt(sbTarget.getReduceLimitYear());
			
			// oldapportionendym欄位過長, 限制為:6;傳入值為:2112808;長度為7
			// newapportionendym欄位過長, 限制為:6;傳入值為:2112808;長度為7
			
			String newBookValue = Common.ZeroInt(sbTarget.getBookValue());
			String newNetValue = "0";
			String reduceNetValue = "0";
			String reduceAccumdepr = "0";
			String newDeprAmount = "0";
			String newAccumdepr = "0";
			String newMonthDepr = "0";
			String newMonthDepr1 = "0";
			
			if (sbTarget.getOldApportionEndYM().length() == 5) { // 你媽的.. source有些會先轉成民國年 但又不能隨便改掉因為是共用 只好這邊先看長度轉成西元年
				sbTarget.setOldApportionEndYM(ul._transToCE_Year(sbTarget.getOldApportionEndYM()));
			}
			
			// 新攤提年限截止年月=原攤提年限截止年月+增加使用月數-減少使用月數
			String newApportionEndYM = sbTarget.getOldApportionEndYM();
			if (addLimitYear > 0 || reduceLimitYear > 0) {
				newApportionEndYM = Datetime.getDateAdd("m", (addLimitYear - reduceLimitYear), Datetime.changeTaiwanYYMM(newApportionEndYM, "1") + "01");
				newApportionEndYM = Datetime.changeTaiwanYYMM(newApportionEndYM, "2");
			}
			
			// 新折舊-攤提壽月
			String newApportionMonth = "0";
			int newApportionMonth_int = 0;
			if ("201512".equals(sbTarget.getClosing1ym()) && "110".equals(sbTarget.getDifferenceKind())) {
				// 新攤提壽月=原攤提壽月+增加使用月數-減少使用月數
				newApportionMonth_int = Common.getInt(sbTarget.getOldApportionMonth()) + addLimitYear - reduceLimitYear;
			} else {
				// 新攤提壽月=新攤提年限截止年月-最後月結年月
				newApportionMonth_int = Datetime.BetweenTwoMonthCE(sbTarget.getClosing1ym(), newApportionEndYM);
			}
			newApportionMonth = String.valueOf(newApportionMonth_int < 0 ? "0" : newApportionMonth_int);
				
			if (!(BigDecimal.ZERO.compareTo(new BigDecimal(sbTarget.getAddBookValue())) == 0)) {
				newBookValue = mt._addition_withBigDecimal(newBookValue, Common.ZeroInt(sbTarget.getAddBookValue()));
			}
			
			if (!(BigDecimal.ZERO.compareTo(new BigDecimal(sbTarget.getReduceBookValue())) == 0)) {
				newBookValue = mt._subtraction_withBigDecimal(newBookValue, Common.ZeroInt(sbTarget.getReduceBookValue()));
			}
			
			if (!(BigDecimal.ZERO.compareTo(new BigDecimal(sbTarget.getBookValue())) == 0)) {
				String reduceAccumdeprRate = "";
				// 比照jsp端, javascript 針對無窮小數預設是取小數20位
				reduceAccumdeprRate = mt._division_withBigDecimal(Common.ZeroInt(sbTarget.getReduceBookValue()), Common.ZeroInt(sbTarget.getOldBookValue()), 20);
				
				if (calDeprVal) {
					if (sbTarget.getOldScrapValue().equals(sbTarget.getOldNetValue())) {
						reduceAccumdepr = sbTarget.getReduceBookValue() ;
					}else {
						// 減少累計折舊 = 原累計折舊 * (減少原值/調整前原值)  四捨五入至整數
						reduceAccumdepr = new BigDecimal(Common.ZeroInt(sbTarget.getOldAccumDepr())).multiply(new BigDecimal(reduceAccumdeprRate)).setScale(0, RoundingMode.HALF_UP).toString();
					}
					newAccumdepr = mt._subtraction(Common.ZeroInt(sbTarget.getOldAccumDepr()), reduceAccumdepr);
					newDeprAmount = mt._subtraction_withBigDecimal(mt._subtraction_withBigDecimal(newBookValue, Common.ZeroInt(sbTarget.getNewScrapValue())), newAccumdepr);
				} else {
					// 當財產折舊方法為不必折舊OR珍貴財產 增減值不須計算折舊資料欄位(累折內定為0)
					reduceAccumdepr = "0";
					newAccumdepr = "0";
					newDeprAmount = "0";
				}
				
				if(!"9".equals(checkPropertyNo1)) { //有價證券無netvalue等欄位，故計算時排除有價證券，避免出錯
					reduceNetValue =  mt._subtraction_withBigDecimal(Common.ZeroInt(sbTarget.getReduceBookValue()), reduceAccumdepr);
					if(Common.getInt(Datetime.getYYYMMDD()) > Common.getInt(Datetime.getDateAdd("m", Common.getInt(sbTarget.getNewOtherLimitYear()), sbTarget.getBuyDate()))) {
						newNetValue = sbTarget.getOldNetValue();
					}else {
						newNetValue = mt._subtraction_withBigDecimal(mt._addition_withBigDecimal(sbTarget.getOldNetValue(), sbTarget.getAddNetValue()), reduceNetValue);
					}
				}
				
				if ("0".equals(newApportionMonth)) { // 新應攤提壽月 為 0 時, 提列金額與提列金額最後一個月  為 財產主檔的目前折舊欄位
					newMonthDepr = sbTarget.getMonthDepr();
					newMonthDepr1 = sbTarget.getMonthDepr1();
				} else {
					//1050527 俊宇 ：修改為四捨五入 
					Double calc= Math.floor(Double.parseDouble(newDeprAmount)/Double.parseDouble(newApportionMonth));
					newMonthDepr = calc.toString();
					newMonthDepr1 = mt._subtraction_withBigDecimal(newDeprAmount, mt._multiplication_withBigDecimal(newMonthDepr, mt._subtraction(newApportionMonth, "1")));
					
//					newMonthDepr = mt._division_withBigDecimal(newDeprAmount, newApportionMonth, 0);
//					newMonthDepr1 = mt._subtraction_withBigDecimal(newDeprAmount, mt._multiplication_withBigDecimal(newMonthDepr, mt._subtraction(newApportionMonth, "1")));
				}
				
			}		
	
			sbTarget.setReduceAccumDepr(reduceAccumdepr);
			sbTarget.setReduceNetValue(reduceNetValue);
		
			sbTarget.setNewBookValue(newBookValue);
			sbTarget.setNewNetValue(newNetValue);
			
			sbTarget.setNewDeprAmount(newDeprAmount);
			sbTarget.setNewAccumDepr(newAccumdepr);
			sbTarget.setNewApportionMonth(newApportionMonth);
			
			sbTarget.setNewMonthDepr(newMonthDepr);
			sbTarget.setNewMonthDepr1(newMonthDepr1);
			sbTarget.setNewApportionEndYM(newApportionEndYM);

			
			if("111".equals(checkPropertyNo3)){
				sbTarget.setOldMeasure(sbTarget.getMeasure());
				sbTarget.setAddMeasure("0");
				sbTarget.setReduceMeasure("0");
				sbTarget.setNewMeasure(sbTarget.getMeasure());
				
			}else if("1".equals(checkPropertyNo1)){
				sbTarget.setOldLaArea(sbTarget.getArea());
				sbTarget.setOldLaHoldNume(sbTarget.getHoldNume());
				sbTarget.setOldLaHoldDeno(sbTarget.getHoldDeno());
				sbTarget.setOldLaHoldArea(sbTarget.getHoldArea());
				
				sbTarget.setAdjustLaArea("0");
				sbTarget.setAdjustLaHoldArea("0");
				
				sbTarget.setNewLaArea(sbTarget.getArea());
				sbTarget.setNewLaHoldNume(sbTarget.getHoldNume());
				sbTarget.setNewLaHoldDeno(sbTarget.getHoldDeno());
				sbTarget.setNewLaHoldArea(sbTarget.getHoldArea());
				
				sbTarget.setLaSignNo1(sbTarget.getSignNo1());
				sbTarget.setLaSignNo2(sbTarget.getSignNo2());
				sbTarget.setLaSignNo3(sbTarget.getSignNo3());
				sbTarget.setLaSignNo4(sbTarget.getSignNo4());
				sbTarget.setLaSignNo5(sbTarget.getSignNo5());
				
			}else if("2".equals(checkPropertyNo1)){
				sbTarget.setOldBuArea(sbTarget.getArea());
				sbTarget.setOldBuSArea(sbTarget.getSArea());
				sbTarget.setOldBuCArea(sbTarget.getCArea());
				sbTarget.setOldBuHoldNume(sbTarget.getHoldNume());
				sbTarget.setOldBuHoldDeno(sbTarget.getHoldDeno());
				sbTarget.setOldBuHoldArea(sbTarget.getHoldArea());
				
				sbTarget.setAdjustBuArea("0");
				sbTarget.setAdjustBuSArea("0");
				sbTarget.setAdjustBuCArea("0");
				sbTarget.setAdjustBuHoldArea("0");
				
				sbTarget.setNewBuArea(sbTarget.getArea());
				sbTarget.setNewBuSArea(sbTarget.getSArea());
				sbTarget.setNewBuCArea(sbTarget.getCArea());
				sbTarget.setNewBuHoldNume(sbTarget.getHoldNume());
				sbTarget.setNewBuHoldDeno(sbTarget.getHoldDeno());
				sbTarget.setNewBuHoldArea(sbTarget.getHoldArea());
				
				sbTarget.setBuSignNo1(sbTarget.getSignNo1());
				sbTarget.setBuSignNo2(sbTarget.getSignNo2());
				sbTarget.setBuSignNo3(sbTarget.getSignNo3());
				sbTarget.setBuSignNo4(sbTarget.getSignNo4());
				sbTarget.setBuSignNo5(sbTarget.getSignNo5());
			}else if("3".equals(checkPropertyNo1) || "4".equals(checkPropertyNo1) || "5".equals(checkPropertyNo1)){
				sbTarget.setOldBookAmount(sbTarget.getBookAmount());
				sbTarget.setNewBookAmount(sbTarget.getBookAmount());
			}else if("8".equals(checkPropertyNo1)){
				sbTarget.setOldBookAmount(sbTarget.getBookAmount());
				sbTarget.setAddBookAmount("0");
				sbTarget.setReduceBookAmount("0");
				sbTarget.setNewBookAmount(sbTarget.getBookAmount());
			}else if("9".equals(checkPropertyNo1)){
				sbTarget.setOldBookAmount(sbTarget.getBookAmount());
				sbTarget.setAddBookAmount("0");
				sbTarget.setReduceBookAmount("0");
				sbTarget.setNewBookAmount(sbTarget.getBookAmount());
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
	 * @deprecated 重複計算 無用處
	 * @param sbTarget
	 */
	private void calValue(UNTCH003F02 sbTarget){
		if(!"".equals(Common.get(this.getQ_reduceBookValue()))){
			MathTools mt = new MathTools();
			
			String oldAccumDeprTemp = ("".equals(Common.get(sbTarget.getOldAccumDepr()))?"0":Common.get(sbTarget.getOldAccumDepr()));
			String oldBookValueTemp = ("".equals(Common.get(sbTarget.getOldBookValue()))?"0":Common.get(sbTarget.getOldBookValue()));
			String reduceBookValue = ("".equals(Common.get(sbTarget.getReduceBookValue()))?"0":Common.get(sbTarget.getReduceBookValue()));
			String reduceAccumDepr = ("".equals(Common.get(sbTarget.getReduceAccumDepr()))?"0":Common.get(sbTarget.getReduceAccumDepr()));
			String oldAccumDepr = ("".equals(Common.get(sbTarget.getOldAccumDepr()))?"0":Common.get(sbTarget.getOldAccumDepr()));
			String newBookValue = ("".equals(Common.get(sbTarget.getNewBookValue()))?"0":Common.get(sbTarget.getNewBookValue()));
			String newAccumDepr = ("".equals(Common.get(sbTarget.getNewAccumDepr()))?"0":Common.get(sbTarget.getNewAccumDepr()));
			
			String temp = mt._multiplication_withBigDecimal(this.getQ_reduceBookValue(), oldAccumDeprTemp);
			if(!"0".equals(temp)){	sbTarget.setReduceAccumDepr(mt._division_withBigDecimal(temp,oldBookValueTemp, 0)); 
			}else{					sbTarget.setReduceAccumDepr(temp);
			}
//			sbTarget.setReduceAccumDepr(mt._division_withBigDecimal(mt._multiplication_withBigDecimal(this.getQ_reduceBookValue(), sbTarget.getOldAccumDepr()),sbTarget.getOldBookValue(), 0));
			sbTarget.setReduceNetValue(mt._subtraction_withBigDecimal(reduceBookValue, reduceAccumDepr));
			sbTarget.setNewAccumDepr(mt._subtraction_withBigDecimal(oldAccumDepr, reduceAccumDepr));
			sbTarget.setNewNetValue(mt._subtraction_withBigDecimal(newBookValue, newAccumDepr));
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
	
	try {
		String sql = "select * from (" +
					"select" +
						" a.enterorg," +
						" a.ownership," +
						" a.differencekind," +
						" a.propertyno," +
						" a.serialno," +
						" a.oldserialno," +
						" '' as lotno," +
						" (select propertyname from SYSPK_PROPERTYCODE z where z.enterorg IN (a.enterorg ,'000000000A') and z.propertyno = a.propertyno) as propertyname,"+
						" a.propertyname1," +
						" a.signno," +
						" ((select signdesc from SYSCA_SIGN z where z.signno = SUBSTRING(a.signno,0,8)) + SUBSTRING(a.signno,8,4) + '-' + SUBSTRING(a.signno,12,4)) as signNoName," +
						" a.holdarea as area," +
						" a.bookvalue," +
						" a.netvalue," +
						" null as apportionendym" +
						" ,chk.* " +
					" from UNTLA_LAND a " +
					this.getOtherProofCheck("LA") +
					" where 1=1" +
					this.getCondition("LA") +
					" union" +
					" select" +
						" a.enterorg," +
						" a.ownership," +
						" a.differencekind," +
						" a.propertyno," +
						" a.serialno," +
						" a.oldserialno," +
						" '' as lotno," +
						" (select propertyname from SYSPK_PROPERTYCODE z where z.enterorg IN (a.enterorg ,'000000000A') and z.propertyno = a.propertyno) as propertyname,"+
						" a.propertyname1," +
						" a.signno," +
						" ((select signdesc from SYSCA_SIGN z where z.signno = SUBSTRING(a.signno,0,8)) + SUBSTRING(a.signno,8,5) + '-' + SUBSTRING(a.signno,13,3)) as signNoName," +
						" a.holdarea as area," +
						" a.bookvalue," +
						" a.netvalue," +
						" case when (a.apportionendym - cast(substring((SELECT CONVERT(varchar(100), GETDATE(), 112)),0,7) as int) ) <0 then '已超過使用年限' end" +
						",chk.* " +
					" from UNTBU_BUILDING a " +
					this.getOtherProofCheck("BU") +
					" where 1=1" +
						getCondition("BU") +
					" union" +
					" select" +
						" a.enterorg," +
						" a.ownership," +
						" a.differencekind," +
						" a.propertyno," +
						" a.serialno," +
						" a.oldserialno," +
						" '' as lotno," +
						" (select propertyname from SYSPK_PROPERTYCODE z where z.enterorg IN (a.enterorg ,'000000000A') and z.propertyno = a.propertyno) as propertyname,"+
						" a.propertyname1," +
						" null as signNo," +
						" null as signNoName," +
						" a.measure as area," +
						" a.bookvalue," +
						" a.netvalue," +
						" null as apportionendym" +
						" ,chk.* " +
					" from UNTRF_ATTACHMENT a " +
					this.getOtherProofCheck("RF") +
					" where 1=1" +
					this.getCondition("RF") +
					" union" +
					" select" +
						" a.enterorg," +
						" a.ownership," +
						" a.differencekind," +
						" a.propertyno," +
						" a.serialno," +
						" a.oldserialno," +
						" a.lotno," +
						" (select propertyname from SYSPK_PROPERTYCODE z where z.enterorg IN (a.enterorg ,'000000000A') and z.propertyno = a.propertyno) as propertyname,"+
						" a.propertyname1," +
						" null as signNo," +
						" null as signNoName," +
						" null as area," +
						" a.bookvalue," +
						" a.netvalue," +
						" case when (a.apportionendym - cast(substring((SELECT CONVERT(varchar(100), GETDATE(), 112)),0,7) as int) ) <0 then '已超過使用年限' end" +
						" ,chk.*" +
					" from UNTMP_MOVABLE b" +
					" join UNTMP_MOVABLEDETAIL a on a.enterorg = b.enterorg and a.ownership = b.ownership and a.differencekind = b.differencekind and a.lotno = b.lotno and a.propertyno = b.propertyno" +
					this.getOtherProofCheck("MP") +
					" where 1=1"+
						getCondition("MP") +
					" union" +
					" select" +
						" a.enterorg," +
						" a.ownership," +
						" a.differencekind," +
						" a.propertyno," +
						" a.serialno," +
						" a.oldserialno," +
						" '' as lotno," +
						" (select propertyname from SYSPK_PROPERTYCODE z where z.enterorg IN (a.enterorg ,'000000000A') and z.propertyno = a.propertyno) as propertyname,"+
						" a.propertyname1," +
						" null as signNo," +
						" null as signNoName," +
						" null as area," +
						" a.bookvalue," +
						" null as netvalue," +
						" case when (a.apportionendym - cast(substring((SELECT CONVERT(varchar(100), GETDATE(), 112)),0,7) as int) ) <0 then '已超過使用年限' end" +
						" ,chk.* " +
					" from UNTRT_ADDPROOF a " +
					this.getOtherProofCheck("RT") +
					" where 1=1" +
					this.getCondition("RT") +
					" union" +
					" select" +
						" a.enterorg," +
						" a.ownership," +
						" a.differencekind," +
						" a.propertyno," +
						" a.serialno," +
						" a.oldserialno," +
						" '' as lotno," +
						" (select propertyname from SYSPK_PROPERTYCODE z where z.enterorg IN (a.enterorg ,'000000000A') and z.propertyno = a.propertyno) as propertyname,"+
						" a.propertyname1," +
						" null as signNo," +
						" null as signNoName," +
						" null as area," +
						" a.bookvalue," +
						" null as netvalue," +
						" null as apportionendym" +
						" ,chk.* "+
					" from UNTVP_ADDPROOF a " +
					this.getOtherProofCheck("VP") +
					" where 1=1" +
						getCondition("VP") +
					") b order by propertyno, serialno";

		int count = 0;		
		ResultSet rs = db.querySQL_NoChange(sql);
		while(rs.next()){
			String rowArray[]=new String[16];
			rowArray[0]=checkGet(rs.getString("enterOrg"));
			rowArray[1]=checkGet(rs.getString("ownership"));
			rowArray[2]=checkGet(rs.getString("differenceKind"));
			rowArray[3]=checkGet(rs.getString("propertyNo")); 
			rowArray[4]=checkGet(rs.getString("serialNo"));
			rowArray[5]=checkGet(rs.getString("oldserialNo"));
			rowArray[6]=checkGet(rs.getString("lotNo")); 
			rowArray[7]=checkGet(rs.getString("propertyName"));
			rowArray[8]=checkGet(rs.getString("propertyName1"));
			rowArray[9]=checkGet(rs.getString("signNo")); 
			rowArray[10]=checkGet(rs.getString("signNoName"));
			rowArray[11]=checkGet(rs.getString("area")); 
			rowArray[12]=checkGet(rs.getString("bookValue")); 
			rowArray[13]=checkGet(rs.getString("netvalue"));
			
			String chkproofdesc = Common.concatStrs("字第", Common.concatStrs("年", rs.getString("chkproofyear"), rs.getString("chkproofdoc")), rs.getString("chkproofno"));
			if (!"".equals(chkproofdesc)) {
				rowArray[14] = checkGet(chkproofdesc + "號");
			} else {
				rowArray[14] = "";
			}
			rowArray[15]=checkGet(rs.getString("apportionendym"));
			
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
	if (!"".equals(getQ_useUnit())){ 
		sql += " and a.useunit = " + Common.sqlChar(getQ_useUnit());
	}
	if (!"".equals(getQ_userNo())){ 
		sql += " and a.userno = " + Common.sqlChar(getQ_userNo());
	}
	if (!"".equals(getQ_userNote())){ 
		sql += " and a.usernote like " + Common.sqlChar("%" + getQ_userNote() + "%");
	}
	
	if (!"".equals(getQ_place())){ 
		sql += " and a.place1 = " + Common.sqlChar(getQ_place());
	}
	if (!"".equals(getQ_placeNote())){ 
		sql += " and a.place like " + Common.sqlChar("%" + getQ_placeNote() + "%");
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
	
	return sql;
}


}
