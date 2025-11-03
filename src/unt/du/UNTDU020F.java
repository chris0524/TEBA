/*
*<br>程式目的：建物主檔修改
*<br>程式代號：
*<br>程式日期：0970711
*<br>程式作者：shan
*<br>--------------------------------------------------------
*<br>修改作者　　修改日期　　　修改目的
*<br>--------------------------------------------------------
*/

package unt.du;

import java.sql.ResultSet;
import java.util.ArrayList;
import util.*;

public class UNTDU020F extends QueryBean{

//主ｋｅｙ
String enterOrg;
String enterOrgName;
String ownership;
String caseNo;
String propertyNo;
String lotNo;
String serialNo;

public String getEnterOrg(){ return checkGet(enterOrg); }
public void setEnterOrg(String s){ enterOrg=checkSet(s); }
public String getEnterOrgName(){ return checkGet(enterOrgName); }
public void setEnterOrgName(String s){ enterOrgName=checkSet(s); }
public String getOwnership(){ return checkGet(ownership); }
public void setOwnership(String s){ ownership=checkSet(s); }
public String getCaseNo(){ return checkGet(caseNo); }
public void setCaseNo(String s){ caseNo=checkSet(s); }
public String getPropertyNo(){ return checkGet(propertyNo); }
public void setPropertyNo(String s){ propertyNo=checkSet(s); }
public String getLotNo(){ return checkGet(lotNo); }
public void setLotNo(String s){ lotNo=checkSet(s); }
public String getSerialNo(){ return checkGet(serialNo); }
public void setSerialNo(String s){ serialNo=checkSet(s); }

//查詢
String q_enterOrg;
String q_enterOrgName;
String q_ownership;
String q_caseNo;
String q_propertyNo;
String q_serialNo;
String q_lotNo;
public String getQ_enterOrg(){ return checkGet(q_enterOrg); }
public void setQ_enterOrg(String s){ q_enterOrg=checkSet(s); }
public String getQ_enterOrgName(){ return checkGet(q_enterOrgName); }
public void setQ_enterOrgName(String s){ q_enterOrgName=checkSet(s); }
public String getQ_ownership(){ return checkGet(q_ownership); }
public void setQ_ownership(String s){ q_ownership=checkSet(s); }
public String getQ_caseNo(){ return checkGet(q_caseNo); }
public void setQ_caseNo(String s){ q_caseNo=checkSet(s); }
public String getQ_propertyNo(){ return checkGet(q_propertyNo); }
public void setQ_propertyNo(String s){ q_propertyNo=checkSet(s); }
public String getQ_serialNo(){ return checkGet(q_serialNo); }
public void setQ_serialNo(String s){ q_serialNo=checkSet(s); }
public String getQ_lotNo(){ return checkGet(q_lotNo); }
public void setQ_lotNo(String s){ q_lotNo=checkSet(s); }
String q_chengClass;
public String getQ_chengClass(){ return checkGet(q_chengClass); }
public void setQ_chengClass(String s){ q_chengClass=checkSet(s); }

//提供修改欄位
String propertyName1;

String signNo;
String signNo1;
String signNo2;
String signNo3;
String signNo4;
String signNo5;


String doorPlate1;
String doorPlate2;
String doorPlate3;
String doorPlate4;
String cause;
String cause1;
String enterDate;
String dataState;
String reduceDate;
String reduceCause;
String reduceCause1;
String verify;
String closing;
String propertyKind;
String fundType;
String valuable;
String taxCredit;
String originalArea;
String originalHoldNume;
String originalHoldDeno;
String originalHoldArea;
String area;
String holdNume;
String holdDeno;
String holdArea;
String originalBV;
String originalNote;
String accountingTitle;
String bookValue;
String fundsSource;
String fundsSource1;
String ownershipDate;
String ownershipCause;
String nonProof;
String proofDoc;
String proofVerify;
String ownershipNote;
String sourceKind;
String sourceDate;
String sourceDoc;
String manageOrg;
String useState;
String proceedDateS;
String proceedDateE;
String proceedType;
String appraiseDate;
String notes;
String oldPropertyNo;
String oldSerialNo;
String otherLimitYear;
String buildStyle;
String originalCArea;
String originalSArea;
String CArea;
String SArea;
String buildDate;
String floor1;
String floor2;
String stuff;
String damageDate;
String damageExpire;
String damageMark;
String deprMethod;
String scrapValue;
String deprAmount;
String apportionYear;
String monthDepr;
String useEndYM;
String apportionEndYM;
String accumDeprYM;
String isAccumDepr;
String accumDepr;
String permitReduceDate;

public String getPropertyName1(){ return checkGet(propertyName1); }	public void setPropertyName1(String s){ propertyName1=checkSet(s); }
public String getDoorPlate1(){ return checkGet(doorPlate1); }	public void setDoorPlate1(String s){ doorPlate1=checkSet(s); }
public String getDoorPlate2(){ return checkGet(doorPlate2); }	public void setDoorPlate2(String s){ doorPlate2=checkSet(s); }
public String getDoorPlate3(){ return checkGet(doorPlate3); }	public void setDoorPlate3(String s){ doorPlate3=checkSet(s); }
public String getDoorPlate4(){ return checkGet(doorPlate4); }	public void setDoorPlate4(String s){ doorPlate4=checkSet(s); }
public String getCause(){ return checkGet(cause); }	public void setCause(String s){ cause=checkSet(s); }
public String getCause1(){ return checkGet(cause1); }	public void setCause1(String s){ cause1=checkSet(s); }
public String getEnterDate(){ return checkGet(enterDate); }	public void setEnterDate(String s){ enterDate=checkSet(s); }
public String getDataState(){ return checkGet(dataState); }	public void setDataState(String s){ dataState=checkSet(s); }
public String getReduceDate(){ return checkGet(reduceDate); }	public void setReduceDate(String s){ reduceDate=checkSet(s); }
public String getReduceCause(){ return checkGet(reduceCause); }	public void setReduceCause(String s){ reduceCause=checkSet(s); }
public String getReduceCause1(){ return checkGet(reduceCause1); }	public void setReduceCause1(String s){ reduceCause1=checkSet(s); }
public String getVerify(){ return checkGet(verify); }	public void setVerify(String s){ verify=checkSet(s); }
public String getClosing(){ return checkGet(closing); }	public void setClosing(String s){ closing=checkSet(s); }
public String getPropertyKind(){ return checkGet(propertyKind); }	public void setPropertyKind(String s){ propertyKind=checkSet(s); }
public String getFundType(){ return checkGet(fundType); }	public void setFundType(String s){ fundType=checkSet(s); }
public String getValuable(){ return checkGet(valuable); }	public void setValuable(String s){ valuable=checkSet(s); }
public String getTaxCredit(){ return checkGet(taxCredit); }	public void setTaxCredit(String s){ taxCredit=checkSet(s); }
public String getOriginalArea(){ return checkGet(originalArea); }	public void setOriginalArea(String s){ originalArea=checkSet(s); }
public String getOriginalHoldNume(){ return checkGet(originalHoldNume); }	public void setOriginalHoldNume(String s){ originalHoldNume=checkSet(s); }
public String getOriginalHoldDeno(){ return checkGet(originalHoldDeno); }	public void setOriginalHoldDeno(String s){ originalHoldDeno=checkSet(s); }
public String getOriginalHoldArea(){ return checkGet(originalHoldArea); }	public void setOriginalHoldArea(String s){ originalHoldArea=checkSet(s); }
public String getArea(){ return checkGet(area); }	public void setArea(String s){ area=checkSet(s); }
public String getHoldNume(){ return checkGet(holdNume); }	public void setHoldNume(String s){ holdNume=checkSet(s); }
public String getHoldDeno(){ return checkGet(holdDeno); }	public void setHoldDeno(String s){ holdDeno=checkSet(s); }
public String getHoldArea(){ return checkGet(holdArea); }	public void setHoldArea(String s){ holdArea=checkSet(s); }
public String getOriginalBV(){ return checkGet(originalBV); }	public void setOriginalBV(String s){ originalBV=checkSet(s); }
public String getOriginalNote(){ return checkGet(originalNote); }	public void setOriginalNote(String s){ originalNote=checkSet(s); }
public String getAccountingTitle(){ return checkGet(accountingTitle); }	public void setAccountingTitle(String s){ accountingTitle=checkSet(s); }
public String getBookValue(){ return checkGet(bookValue); }	public void setBookValue(String s){ bookValue=checkSet(s); }
public String getFundsSource(){ return checkGet(fundsSource); }	public void setFundsSource(String s){ fundsSource=checkSet(s); }
public String getFundsSource1(){ return checkGet(fundsSource1); }	public void setFundsSource1(String s){ fundsSource1=checkSet(s); }
public String getOwnershipDate(){ return checkGet(ownershipDate); }	public void setOwnershipDate(String s){ ownershipDate=checkSet(s); }
public String getOwnershipCause(){ return checkGet(ownershipCause); }	public void setOwnershipCause(String s){ ownershipCause=checkSet(s); }
public String getNonProof(){ return checkGet(nonProof); }	public void setNonProof(String s){ nonProof=checkSet(s); }
public String getProofDoc(){ return checkGet(proofDoc); }	public void setProofDoc(String s){ proofDoc=checkSet(s); }
public String getProofVerify(){ return checkGet(proofVerify); }	public void setProofVerify(String s){ proofVerify=checkSet(s); }
public String getOwnershipNote(){ return checkGet(ownershipNote); }	public void setOwnershipNote(String s){ ownershipNote=checkSet(s); }
public String getSourceKind(){ return checkGet(sourceKind); }	public void setSourceKind(String s){ sourceKind=checkSet(s); }
public String getSourceDate(){ return checkGet(sourceDate); }	public void setSourceDate(String s){ sourceDate=checkSet(s); }
public String getSourceDoc(){ return checkGet(sourceDoc); }	public void setSourceDoc(String s){ sourceDoc=checkSet(s); }
public String getManageOrg(){ return checkGet(manageOrg); }	public void setManageOrg(String s){ manageOrg=checkSet(s); }
public String getUseState(){ return checkGet(useState); }	public void setUseState(String s){ useState=checkSet(s); }
public String getProceedDateS(){ return checkGet(proceedDateS); }	public void setProceedDateS(String s){ proceedDateS=checkSet(s); }
public String getProceedDateE(){ return checkGet(proceedDateE); }	public void setProceedDateE(String s){ proceedDateE=checkSet(s); }
public String getProceedType(){ return checkGet(proceedType); }	public void setProceedType(String s){ proceedType=checkSet(s); }
public String getAppraiseDate(){ return checkGet(appraiseDate); }	public void setAppraiseDate(String s){ appraiseDate=checkSet(s); }
public String getNotes(){ return checkGet(notes); }	public void setNotes(String s){ notes=checkSet(s); }
public String getOldPropertyNo(){ return checkGet(oldPropertyNo); }	public void setOldPropertyNo(String s){ oldPropertyNo=checkSet(s); }
public String getOldSerialNo(){ return checkGet(oldSerialNo); }	public void setOldSerialNo(String s){ oldSerialNo=checkSet(s); }
public String getOtherLimitYear(){ return checkGet(otherLimitYear); }	public void setOtherLimitYear(String s){ otherLimitYear=checkSet(s); }
public String getBuildStyle(){ return checkGet(buildStyle); }	public void setBuildStyle(String s){ buildStyle=checkSet(s); }
public String getOriginalCArea(){ return checkGet(originalCArea); }	public void setOriginalCArea(String s){ originalCArea=checkSet(s); }
public String getOriginalSArea(){ return checkGet(originalSArea); }	public void setOriginalSArea(String s){ originalSArea=checkSet(s); }
public String getCArea(){ return checkGet(CArea); }	public void setCArea(String s){ CArea=checkSet(s); }
public String getSArea(){ return checkGet(SArea); }	public void setSArea(String s){ SArea=checkSet(s); }
public String getBuildDate(){ return checkGet(buildDate); }	public void setBuildDate(String s){ buildDate=checkSet(s); }
public String getFloor1(){ return checkGet(floor1); }	public void setFloor1(String s){ floor1=checkSet(s); }
public String getFloor2(){ return checkGet(floor2); }	public void setFloor2(String s){ floor2=checkSet(s); }
public String getStuff(){ return checkGet(stuff); }	public void setStuff(String s){ stuff=checkSet(s); }
public String getDamageDate(){ return checkGet(damageDate); }	public void setDamageDate(String s){ damageDate=checkSet(s); }
public String getDamageExpire(){ return checkGet(damageExpire); }	public void setDamageExpire(String s){ damageExpire=checkSet(s); }
public String getDamageMark(){ return checkGet(damageMark); }	public void setDamageMark(String s){ damageMark=checkSet(s); }
public String getDeprMethod(){ return checkGet(deprMethod); }	public void setDeprMethod(String s){ deprMethod=checkSet(s); }
public String getScrapValue(){ return checkGet(scrapValue); }	public void setScrapValue(String s){ scrapValue=checkSet(s); }
public String getDeprAmount(){ return checkGet(deprAmount); }	public void setDeprAmount(String s){ deprAmount=checkSet(s); }
public String getApportionYear(){ return checkGet(apportionYear); }	public void setApportionYear(String s){ apportionYear=checkSet(s); }
public String getMonthDepr(){ return checkGet(monthDepr); }	public void setMonthDepr(String s){ monthDepr=checkSet(s); }
public String getUseEndYM(){ return checkGet(useEndYM); }	public void setUseEndYM(String s){ useEndYM=checkSet(s); }
public String getApportionEndYM(){ return checkGet(apportionEndYM); }	public void setApportionEndYM(String s){ apportionEndYM=checkSet(s); }
public String getAccumDeprYM(){ return checkGet(accumDeprYM); }	public void setAccumDeprYM(String s){ accumDeprYM=checkSet(s); }
public String getIsAccumDepr(){ return checkGet(isAccumDepr); }	public void setIsAccumDepr(String s){ isAccumDepr=checkSet(s); }
public String getAccumDepr(){ return checkGet(accumDepr); }	public void setAccumDepr(String s){ accumDepr=checkSet(s); }
public String getPermitReduceDate(){ return checkGet(permitReduceDate); }	public void setPermitReduceDate(String s){ permitReduceDate=checkSet(s); }

public String getSignNo(){ return checkGet(signNo); }	public void setSignNo(String s){ signNo=checkSet(s); }
public String getSignNo1(){ return checkGet(signNo1); }	public void setSignNo1(String s){ signNo1=checkSet(s); }
public String getSignNo2(){ return checkGet(signNo2); }	public void setSignNo2(String s){ signNo2=checkSet(s); }
public String getSignNo3(){ return checkGet(signNo3); }	public void setSignNo3(String s){ signNo3=checkSet(s); }
public String getSignNo4(){ return checkGet(signNo4); }	public void setSignNo4(String s){ signNo4=checkSet(s); }
public String getSignNo5(){ return checkGet(signNo5); }	public void setSignNo5(String s){ signNo5=checkSet(s); }

String manageOrgName;
public String getManageOrgName(){ return checkGet(manageOrgName); }	public void setManageOrgName(String s){ manageOrgName=checkSet(s); }

//傳回update sql
protected String[] getUpdateSQL(){
	String[] execSQLArray = new String[1];
	execSQLArray[0] = " update UNTBU_Building set "
					+ " propertyName1= " +  Common.sqlChar(propertyName1) + ","
					+ " signNo= " +  Common.sqlChar(Common.get(signNo3)+Common.get(signNo4)+Common.get(signNo5)) + ","
					+ " doorPlate1= " +  Common.sqlChar(doorPlate1) + ","
					+ " doorPlate2= " +  Common.sqlChar(doorPlate2) + ","
					+ " doorPlate3= " +  Common.sqlChar(doorPlate3) + ","
					+ " doorPlate4= " +  Common.sqlChar(doorPlate4) + ","
					+ " cause= " +  Common.sqlChar(cause) + ","
					+ " cause1= " +  Common.sqlChar(cause1) + ","
					+ " enterDate= " +  Common.sqlChar(enterDate) + ","
					+ " dataState= " +  Common.sqlChar(dataState) + ","
					+ " reduceDate= " +  Common.sqlChar(reduceDate) + ","
					+ " reduceCause= " +  Common.sqlChar(reduceCause) + ","
					+ " reduceCause1= " +  Common.sqlChar(reduceCause1) + ","
					+ " verify= " +  Common.sqlChar(verify) + ","
					+ " closing= " +  Common.sqlChar(closing) + ","
					+ " propertyKind= " +  Common.sqlChar(propertyKind) + ","
					+ " fundType= " +  Common.sqlChar(fundType) + ","
					+ " valuable= " +  Common.sqlChar(valuable) + ","
					+ " taxCredit= " +  Common.sqlChar(taxCredit) + ","
					+ " originalArea= " +  Common.sqlChar(originalArea) + ","
					+ " originalHoldNume= " +  Common.sqlChar(originalHoldNume) + ","
					+ " originalHoldDeno= " +  Common.sqlChar(originalHoldDeno) + ","
					+ " originalHoldArea= " +  Common.sqlChar(originalHoldArea) + ","
					+ " area= " +  Common.sqlChar(area) + ","
					+ " holdNume= " +  Common.sqlChar(holdNume) + ","
					+ " holdDeno= " +  Common.sqlChar(holdDeno) + ","
					+ " holdArea= " +  Common.sqlChar(holdArea) + ","
					+ " originalBV= " +  Common.sqlChar(originalBV) + ","
					+ " originalNote= " +  Common.sqlChar(originalNote) + ","
					+ " accountingTitle= " +  Common.sqlChar(accountingTitle) + ","
					+ " bookValue= " +  Common.sqlChar(bookValue) + ","
					+ " fundsSource= " +  Common.sqlChar(fundsSource) + ","
					+ " fundsSource1= " +  Common.sqlChar(fundsSource1) + ","
					+ " ownershipDate= " +  Common.sqlChar(ownershipDate) + ","
					+ " ownershipCause= " +  Common.sqlChar(ownershipCause) + ","
					+ " nonProof= " +  Common.sqlChar(nonProof) + ","
					+ " proofDoc= " +  Common.sqlChar(proofDoc) + ","
					+ " proofVerify= " +  Common.sqlChar(proofVerify) + ","
					+ " ownershipNote= " +  Common.sqlChar(ownershipNote) + ","
					+ " sourceKind= " +  Common.sqlChar(sourceKind) + ","
					+ " sourceDate= " +  Common.sqlChar(sourceDate) + ","
					+ " sourceDoc= " +  Common.sqlChar(sourceDoc) + ","
					+ " manageOrg= " +  Common.sqlChar(manageOrg) + ","
					+ " useState= " +  Common.sqlChar(useState) + ","
					+ " proceedDateS= " +  Common.sqlChar(proceedDateS) + ","
					+ " proceedDateE= " +  Common.sqlChar(proceedDateE) + ","
					+ " proceedType= " +  Common.sqlChar(proceedType) + ","
					+ " appraiseDate= " +  Common.sqlChar(appraiseDate) + ","
					+ " notes= " +  Common.sqlChar(notes) + ","
					+ " oldPropertyNo= " +  Common.sqlChar(oldPropertyNo) + ","
					+ " oldSerialNo= " +  Common.sqlChar(oldSerialNo) + ","
					+ " otherLimitYear= " +  Common.sqlChar(otherLimitYear) + ","
					+ " buildStyle= " +  Common.sqlChar(buildStyle) + ","
					+ " originalCArea= " +  Common.sqlChar(originalCArea) + ","
					+ " originalSArea= " +  Common.sqlChar(originalSArea) + ","
					+ " CArea= " +  Common.sqlChar(CArea) + ","
					+ " SArea= " +  Common.sqlChar(SArea) + ","
					+ " buildDate= " +  Common.sqlChar(buildDate) + ","
					+ " floor1= " +  Common.sqlChar(floor1) + ","
					+ " floor2= " +  Common.sqlChar(floor2) + ","
					+ " stuff= " +  Common.sqlChar(stuff) + ","
					+ " damageDate= " +  Common.sqlChar(damageDate) + ","
					+ " damageExpire= " +  Common.sqlChar(damageExpire) + ","
					//+ " damageMark= " +  Common.sqlChar(damageMark) + ","
					+ " deprMethod= " +  Common.sqlChar(deprMethod) + ","
					+ " scrapValue= " +  Common.sqlChar(scrapValue) + ","
					+ " deprAmount= " +  Common.sqlChar(deprAmount) + ","
					+ " apportionYear= " +  Common.sqlChar(apportionYear) + ","
					+ " monthDepr= " +  Common.sqlChar(monthDepr) + ","
					+ " useEndYM= " +  Common.sqlChar(useEndYM) + ","
					+ " apportionEndYM= " +  Common.sqlChar(apportionEndYM) + ","
					+ " accumDeprYM= " +  Common.sqlChar(accumDeprYM) + ","
					+ " isAccumDepr= " +  Common.sqlChar(isAccumDepr) + ","
					+ " accumDepr= " +  Common.sqlChar(accumDepr) + ","
					+ " permitReduceDate= " +  Common.sqlChar(permitReduceDate) + ","
					+ " editID= " +  Common.sqlChar(getEditID()) + ","
					+ " editDate= " +  Common.sqlChar(getEditDate()) + ","
					+ " editTime= " +  Common.sqlChar(getEditTime())
					+ " where 1=1 " 
			   		+ " and enterOrg = " + Common.sqlChar(enterOrg)
			   		+ " and ownership = " + Common.sqlChar(ownership)
			   		+ " and caseNo = " + Common.sqlChar(caseNo)
			   		+ " and propertyNo = " + Common.sqlChar(propertyNo)
			   		+ " and serialNo = " + Common.sqlChar(serialNo)
					+ "";
	//System.out.println(execSQLArray[0]);
	return execSQLArray;
}

/**
 * <br>
 * <br>目的：依主鍵查詢單一資料
 * <br>參數：無
 * <br>傳回：傳回本物件
*/

public UNTDU020F queryOne() throws Exception{
	Database db = new Database();
	UNTDU020F obj = this;
	try {
		String sql = " select a.enterOrg ,o.organsname as enterOrgName ,a.ownership ,a.caseNo ,a.propertyNo ,a.serialNo " + "\n"
				   + " 		  ,propertyName1 ,signNo ,doorPlate1 ,doorPlate2 ,doorPlate3 ,doorPlate4" + "\n"
				   + " 		  ,cause ,cause1 ,enterDate ,dataState ,reduceDate ,reduceCause" + "\n"
				   + " 		  ,reduceCause1 ,verify ,closing ,propertyKind ,fundType ,valuable" + "\n"
				   + " 		  ,taxCredit ,originalArea ,originalHoldNume ,originalHoldDeno ,originalHoldArea" + "\n"
				   + " 		  ,area ,holdNume ,holdDeno ,holdArea ,originalBV ,originalNote" + "\n"
				   + " 		  ,accountingTitle ,bookValue ,fundsSource ,fundsSource1 ,ownershipDate ,ownershipCause" + "\n"
				   + " 		  ,nonProof ,proofDoc ,proofVerify ,ownershipNote ,sourceKind ,sourceDate" + "\n"
				   + " 		  ,sourceDoc ,manageOrg ,useState ,proceedDateS ,proceedDateE ,proceedType" + "\n"
				   + " 		  ,appraiseDate ,notes ,oldPropertyNo ,oldSerialNo ,otherLimitYear ,buildStyle" + "\n"
				   + " 		  ,originalCArea ,originalSArea ,CArea ,SArea ,buildDate ,floor1" + "\n"
				   + " 		  ,floor2 ,stuff ,damageDate ,damageExpire ,damageMark ,deprMethod" + "\n"
				   + " 		  ,scrapValue ,deprAmount ,apportionYear ,monthDepr ,useEndYM ,apportionEndYM ,accumDeprYM" + "\n"
				   + " 		  ,isAccumDepr ,accumDepr ,permitReduceDate" + "\n"
				   + " 		  ,nvl((subStr(a.signNo,1,1) || '000000'),'') as signNo1 " + "\n"
				   + "   	  ,nvl((subStr(a.signNo,1,3) || '0000'),'') as signNo2 " + "\n"
				   + " 		  ,nvl(subStr(a.signNo,1,7),'') as signNo3 "+ "\n"
				   + " 		  ,nvl(subStr(a.signNo,8,5),'') as signNo4 "+ "\n"
				   + " 		  ,nvl(subStr(a.signNo,13,3),'') as signNo5 "+ "\n"
				   + "		  ,( select x.organsname from sysca_organ x where 1=1 and x.organid = a.manageOrg ) as manageOrgName" + "\n"
		   		   + " 		  ,a.editid ,a.editDate ,a.editTime " + "\n"
		   		   + " from UNTBU_Building a ,sysca_organ o " + "\n"
		   		   + " where 1=1 " + "\n"
		   		   + " and a.enterorg=o.organid " + "\n" 
		   		   + " and a.enterOrg = " + Common.sqlChar(enterOrg)
		   		   + " and a.ownership = " + Common.sqlChar(ownership)
		   		   + " and a.caseNo = " + Common.sqlChar(caseNo)
		   		   + " and a.propertyNo = " + Common.sqlChar(propertyNo)
		   		   + " and a.serialNo = " + Common.sqlChar(serialNo)
		   		   + " order by caseno ,propertyno ,serialno"
		   		   + "";

		//System.out.println(sql);
		ResultSet rs = db.querySQL(sql);
		if (rs.next()){
			
			obj.setEnterOrg(rs.getString("enterOrg"));
			obj.setEnterOrgName(rs.getString("enterOrgName"));
			obj.setOwnership(rs.getString("ownership"));
			obj.setPropertyNo(rs.getString("propertyNo"));
			obj.setSerialNo(rs.getString("serialNo"));
			
			obj.setPropertyName1(rs.getString("propertyName1"));
			
			obj.setSignNo(rs.getString("signNo"));
			obj.setSignNo1(rs.getString("signNo1"));
			obj.setSignNo2(rs.getString("signNo2"));
			obj.setSignNo3(rs.getString("signNo3"));
			obj.setSignNo4(rs.getString("signNo4"));
			obj.setSignNo5(rs.getString("signNo5"));
			
			obj.setDoorPlate1(rs.getString("doorPlate1"));
			obj.setDoorPlate2(rs.getString("doorPlate2"));
			obj.setDoorPlate3(rs.getString("doorPlate3"));
			obj.setDoorPlate4(rs.getString("doorPlate4"));
			obj.setCause(rs.getString("cause"));
			obj.setCause1(rs.getString("cause1"));
			obj.setEnterDate(rs.getString("enterDate"));
			obj.setDataState(rs.getString("dataState"));
			obj.setReduceDate(rs.getString("reduceDate"));
			obj.setReduceCause(rs.getString("reduceCause"));
			obj.setReduceCause1(rs.getString("reduceCause1"));
			obj.setVerify(rs.getString("verify"));
			obj.setClosing(rs.getString("closing"));
			obj.setPropertyKind(rs.getString("propertyKind"));
			obj.setFundType(rs.getString("fundType"));
			obj.setValuable(rs.getString("valuable"));
			obj.setTaxCredit(rs.getString("taxCredit"));
			obj.setOriginalArea(rs.getString("originalArea"));
			obj.setOriginalHoldNume(rs.getString("originalHoldNume"));
			obj.setOriginalHoldDeno(rs.getString("originalHoldDeno"));
			obj.setOriginalHoldArea(rs.getString("originalHoldArea"));
			obj.setArea(rs.getString("area"));
			obj.setHoldNume(rs.getString("holdNume"));
			obj.setHoldDeno(rs.getString("holdDeno"));
			obj.setHoldArea(rs.getString("holdArea"));
			obj.setOriginalBV(rs.getString("originalBV"));
			obj.setOriginalNote(rs.getString("originalNote"));
			obj.setAccountingTitle(rs.getString("accountingTitle"));
			obj.setBookValue(rs.getString("bookValue"));
			obj.setFundsSource(rs.getString("fundsSource"));
			obj.setFundsSource1(rs.getString("fundsSource1"));
			obj.setOwnershipDate(rs.getString("ownershipDate"));
			obj.setOwnershipCause(rs.getString("ownershipCause"));
			obj.setNonProof(rs.getString("nonProof"));
			obj.setProofDoc(rs.getString("proofDoc"));
			obj.setProofVerify(rs.getString("proofVerify"));
			obj.setOwnershipNote(rs.getString("ownershipNote"));
			obj.setSourceKind(rs.getString("sourceKind"));
			obj.setSourceDate(rs.getString("sourceDate"));
			obj.setSourceDoc(rs.getString("sourceDoc"));
			obj.setManageOrg(rs.getString("manageOrg"));
			obj.setUseState(rs.getString("useState"));
			obj.setProceedDateS(rs.getString("proceedDateS"));
			obj.setProceedDateE(rs.getString("proceedDateE"));
			obj.setProceedType(rs.getString("proceedType"));
			obj.setAppraiseDate(rs.getString("appraiseDate"));
			obj.setNotes(rs.getString("notes"));
			obj.setOldPropertyNo(rs.getString("oldPropertyNo"));
			obj.setOldSerialNo(rs.getString("oldSerialNo"));
			obj.setOtherLimitYear(rs.getString("otherLimitYear"));
			obj.setBuildStyle(rs.getString("buildStyle"));
			obj.setOriginalCArea(rs.getString("originalCArea"));
			obj.setOriginalSArea(rs.getString("originalSArea"));
			obj.setCArea(rs.getString("CArea"));
			obj.setSArea(rs.getString("SArea"));
			obj.setBuildDate(rs.getString("buildDate"));
			obj.setFloor1(rs.getString("floor1"));
			obj.setFloor2(rs.getString("floor2"));
			obj.setStuff(rs.getString("stuff"));
			obj.setDamageDate(rs.getString("damageDate"));
			obj.setDamageExpire(rs.getString("damageExpire"));
			obj.setDamageMark(rs.getString("damageMark"));
			obj.setDeprMethod(rs.getString("deprMethod"));
			obj.setScrapValue(rs.getString("scrapValue"));
			obj.setDeprAmount(rs.getString("deprAmount"));
			obj.setApportionYear(rs.getString("apportionYear"));
			obj.setMonthDepr(rs.getString("monthDepr"));
			obj.setUseEndYM(rs.getString("useEndYM"));
			obj.setApportionEndYM(rs.getString("apportionEndYM"));
			obj.setAccumDeprYM(rs.getString("accumDeprYM"));
			obj.setIsAccumDepr(rs.getString("isAccumDepr"));
			obj.setAccumDepr(rs.getString("accumDepr"));
			obj.setPermitReduceDate(rs.getString("permitReduceDate"));
			obj.setManageOrgName(rs.getString("manageOrgName"));
			
			obj.setEditID(rs.getString("editID"));
			obj.setEditDate(rs.getString("editDate"));
			obj.setEditTime(rs.getString("editTime"));
		}
		setStateQueryOneSuccess();
	} catch (Exception e) {
		e.printStackTrace();
	} finally {
		db.closeAll();
	}
	return obj;
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
		String sql = " select a.enterOrg ,o.organsname as enterOrgName ,a.ownership ,a.caseno " + "\n"
				   + " 		  ,a.propertyNo ,a.serialNo "
				   + "		  ,(select x.codeName from sysca_code x where a.ownership = x.codeid and x.codekindid = 'OWA') as ownershipName "
				   + " from UNTBU_Building a ,sysca_organ o " + "\n"
				   + " where 1=1 " + "\n"
				   + " and a.enterorg=o.organid " + "\n" ;
				   if (!"".equals(getQ_enterOrg()))
				   {	sql += " and a.enterOrg = " + Common.sqlChar(getQ_enterOrg()) ;	}
				   if (!"".equals(getQ_ownership()))
				   {	sql += " and a.ownership = " + Common.sqlChar(getQ_ownership()) ;	}
				   if (!"".equals(getQ_caseNo()))
				   {	sql += " and a.caseNo = " + Common.sqlChar(getQ_caseNo()) ;		}
				   if (!"".equals(getQ_propertyNo()))
				   {	sql += " and a.propertyNo = " + Common.sqlChar(getQ_propertyNo()) ;	}
				   if (!"".equals(getQ_serialNo()))
				   {	sql += " and a.serialNo = " + Common.sqlChar(getQ_serialNo());	}

				sql += " order by enterorg ,ownership ,caseno ,propertyNo ,serialNo ";
			
		//System.out.println(sql);
		ResultSet rs = db.querySQL(sql,true);
		processCurrentPageAttribute(rs);
		if (getTotalRecord() > 0) {
			int count = getRecordStart();
			int end = getRecordEnd();
			do {
				if (count > end) break;
			String rowArray[]=new String[7];
			rowArray[0]=Common.get(rs.getString("enterOrg")); 
			rowArray[1]=Common.get(rs.getString("enterOrgName")); 
			rowArray[2]=Common.get(rs.getString("ownership"));
			rowArray[3]=Common.get(rs.getString("ownershipName"));
			rowArray[4]=Common.get(rs.getString("caseno"));
			rowArray[5]=Common.get(rs.getString("propertyNo"));
			rowArray[6]=Common.get(rs.getString("serialNo"));
			objList.add(rowArray);
			count++;
			} while (rs.next());
		}
		setStateQueryAllSuccess();
	} catch (Exception e) {
		e.printStackTrace();
	} finally {
		db.closeAll();
	}
	return objList;
}

}
