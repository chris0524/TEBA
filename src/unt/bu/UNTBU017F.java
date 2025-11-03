/*
*<br>程式目的：建物減少作業－批次新增明細資料
*<br>程式代號：untbu017f
*<br>程式日期：0940930
*<br>程式作者：judy
*<br>--------------------------------------------------------
*<br>修改作者　　修改日期　　　修改目的
*<br>--------------------------------------------------------
*/


package unt.bu;

import java.sql.ResultSet;
import java.util.ArrayList;
import util.*;

public class UNTBU017F extends SuperBean{

String enterOrg;
String enterOrgName;
String ownership;
String propertyNo;
String propertyName;
String material;
String propertyUnit;
String limitYear;
String otherLimitYear;
String serialNo;
String propertyName1;
String signNo;
String signNo1;
String signNo2;
String signNo3;
String signNo4;
String signNo5;
String propertyKind;
String fundType;
String valuable;
String taxCredit;
String CArea;
String SArea;
String area;
String holdNume;
String holdDeno;
String holdArea;
String accountingTitle;
String bookValue;
String proofDoc; 
String buildDate;
String sourceDate;
String useState;
String deprMethod;
String deprAmount;
String monthDepr;
String apportionEndYM;
String accumDeprYM;
String accumDepr;
String permitReduceDate;
String oldPropertyNo;
String oldPropertyName;
String oldSerialNo;
String caseNo;

String doorPlate1;
String doorPlate2;
String doorPlate3;
String doorPlate4;	

String q_enterOrg; 
String q_ownership; 
String q_caseNo;
String q_reduceDate;
String q_verify;

String q_lawBasis;
String q_cause;
String q_cause1;
String q_newEnterOrg;
String q_newEnterOrgName;
String q_transferDate;
String q_dataState;
String q_propertyNoS;
String q_propertyNoE;
String q_propertyNoSName;
String q_propertyNoEName;
String q_serialNoS;
String q_serialNoE;
String q_signNo1;
String q_signNo2;
String q_signNo3;
String q_signNo4;
String q_signNo5;
String q_signNo = "";
String q_propertyKind;
String q_fundType;
String q_valuable;
String q_taxCredit;
String q_permitReduceDateS;
String q_permitReduceDateE;

String sSQL = "";
String strKeySet[] = null;

String toggleAll;
public String getToggleAll(){ if (!"".equals(checkGet(toggleAll))) return "checked"; else return "unchecked"; }    
public void setToggleAll(String s){ toggleAll=checkSet(s); }    

public String getQ_enterOrg(){ return checkGet(q_enterOrg); }
public void setQ_enterOrg(String s){ q_enterOrg=checkSet(s); }
public String getQ_ownership(){ return checkGet(q_ownership); }
public void setQ_ownership(String s){ q_ownership=checkSet(s); }
public String getQ_caseNo(){ return checkGet(q_caseNo); }
public void setQ_caseNo(String s){ q_caseNo=checkSet(s); }
public String getQ_reduceDate(){ return checkGet(q_reduceDate); }
public void setQ_reduceDate(String s){ q_reduceDate=checkSet(s); }
public String getQ_verify(){ return checkGet(q_verify); }
public void setQ_verify(String s){ q_verify=checkSet(s); }

public String getDoorPlate1(){ return checkGet(doorPlate1); }
public void setDoorPlate1(String s){ doorPlate1=checkSet(s); }
public String getDoorPlate2(){ return checkGet(doorPlate2); }
public void setDoorPlate2(String s){ doorPlate2=checkSet(s); }
public String getDoorPlate3(){ return checkGet(doorPlate3); }
public void setDoorPlate3(String s){ doorPlate3=checkSet(s); }
public String getDoorPlate4(){ return checkGet(doorPlate4); }
public void setDoorPlate4(String s){ doorPlate4=checkSet(s); }

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
public String getMaterial(){ return checkGet(material); }
public void setMaterial(String s){ material=checkSet(s); }
public String getPropertyUnit(){ return checkGet(propertyUnit); }
public void setPropertyUnit(String s){ propertyUnit=checkSet(s); }
public String getLimitYear(){ return checkGet(limitYear); }
public void setLimitYear(String s){ limitYear=checkSet(s); }
public String getOtherLimitYear(){ return checkGet(otherLimitYear); }
public void setOtherLimitYear(String s){ otherLimitYear=checkSet(s); }
public String getSerialNo(){ return checkGet(serialNo); }
public void setSerialNo(String s){ serialNo=checkSet(s); }
public String getPropertyName1(){ return checkGet(propertyName1); }
public void setPropertyName1(String s){ propertyName1=checkSet(s); }

public String getSignNo(){ return checkGet(signNo); }
public void setSignNo(String s){ signNo=checkSet(s); }
public String getSignNo1(){ return checkGet(signNo1); }
public void setSignNo1(String s){ signNo1=checkSet(s); }
public String getSignNo2(){ return checkGet(signNo2); }
public void setSignNo2(String s){ signNo2=checkSet(s); }
public String getSignNo3(){ return checkGet(signNo3); }
public void setSignNo3(String s){ signNo3=checkSet(s); }
public String getSignNo4(){ return checkGet(signNo4); }
public void setSignNo4(String s){
	if (s!=null && s.length()>0) signNo4=checkSet(Common.formatFrontZero(s,5));
	else signNo4 = "";
}
public String getSignNo5(){ return checkGet(signNo5); }
public void setSignNo5(String s){
	if (s!=null && s.length()>0) signNo5=checkSet(Common.formatFrontZero(s,3));
	else signNo5 = ""; 
}

public String getPropertyKind(){ return checkGet(propertyKind); }
public void setPropertyKind(String s){ propertyKind=checkSet(s); }
public String getFundType(){ return checkGet(fundType); }
public void setFundType(String s){ fundType=checkSet(s); }
public String getValuable(){ return checkGet(valuable); }
public void setValuable(String s){ valuable=checkSet(s); }
public String getTaxCredit(){ return checkGet(taxCredit); }
public void setTaxCredit(String s){ taxCredit=checkSet(s); }
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
public String getAccountingTitle(){ return checkGet(accountingTitle); }
public void setAccountingTitle(String s){ accountingTitle=checkSet(s); }
public String getBookValue(){ return checkGet(bookValue); }
public void setBookValue(String s){ bookValue=checkSet(s); }
public String getProofDoc(){ return checkGet(proofDoc); }
public void setProofDoc(String s){ proofDoc=checkSet(s); }
public String getBuildDate(){ return checkGet(buildDate); }
public void setBuildDate(String s){ buildDate=checkSet(s); }
public String getSourceDate(){ return checkGet(sourceDate); }
public void setSourceDate(String s){ sourceDate=checkSet(s); }
public String getUseState(){ return checkGet(useState); }
public void setUseState(String s){ useState=checkSet(s); }
public String getDeprMethod(){ return checkGet(deprMethod); }
public void setDeprMethod(String s){ deprMethod=checkSet(s); }
public String getDeprAmount(){ return checkGet(deprAmount); }
public void setDeprAmount(String s){ deprAmount=checkSet(s); }
public String getMonthDepr(){ return checkGet(monthDepr); }
public void setMonthDepr(String s){ monthDepr=checkSet(s); }
public String getApportionEndYM(){ return checkGet(apportionEndYM); }
public void setApportionEndYM(String s){ apportionEndYM=checkSet(s); }
public String getAccumDeprYM(){ return checkGet(accumDeprYM); }
public void setAccumDeprYM(String s){ accumDeprYM=checkSet(s); }
public String getAccumDepr(){ return checkGet(accumDepr); }
public void setAccumDepr(String s){ accumDepr=checkSet(s); }
public String getPermitReduceDate(){ return checkGet(permitReduceDate); }
public void setPermitReduceDate(String s){ permitReduceDate=checkSet(s); }
public String getOldPropertyNo(){ return checkGet(oldPropertyNo); }
public void setOldPropertyNo(String s){ oldPropertyNo=checkSet(s); }
public String getOldPropertyName(){ return checkGet(oldPropertyName); }
public void setOldPropertyName(String s){ oldPropertyName=checkSet(s); }
public String getOldSerialNo(){ return checkGet(oldSerialNo); }
public void setOldSerialNo(String s){ oldSerialNo=checkSet(s); }
public String getCaseNo(){ return checkGet(caseNo); }
public void setCaseNo(String s){ caseNo=checkSet(s); }

public String getQ_lawBasis(){ return checkGet(q_lawBasis); }
public void setQ_lawBasis(String s){ q_lawBasis=checkSet(s); }
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
public String getQ_propertyKind(){ return checkGet(q_propertyKind); }
public void setQ_propertyKind(String s){ q_propertyKind=checkSet(s); }
public String getQ_fundType(){ return checkGet(q_fundType); }
public void setQ_fundType(String s){ q_fundType=checkSet(s); }
public String getQ_valuable(){ return checkGet(q_valuable); }
public void setQ_valuable(String s){ q_valuable=checkSet(s); }
public String getQ_taxCredit(){ return checkGet(q_taxCredit); }
public void setQ_taxCredit(String s){ q_taxCredit=checkSet(s); }
public String getQ_cause(){ return checkGet(q_cause); }
public void setQ_cause(String s){ q_cause=checkSet(s); }
public String getQ_cause1(){ return checkGet(q_cause1); }
public void setQ_cause1(String s){ q_cause1=checkSet(s); }
public String getQ_newEnterOrg(){ return checkGet(q_newEnterOrg); }
public void setQ_newEnterOrg(String s){ q_newEnterOrg=checkSet(s); }
public String getQ_newEnterOrgName(){ return checkGet(q_newEnterOrgName); }
public void setQ_newEnterOrgName(String s){ q_newEnterOrgName=checkSet(s); }
public String getQ_transferDate(){ return checkGet(q_transferDate); }
public void setQ_transferDate(String s){ q_transferDate=checkSet(s); }
public String getQ_signNo(){ return checkGet(q_signNo); }
public void setQ_signNo(String s){ q_signNo=checkSet(s); }
public String getQ_permitReduceDateS(){ return checkGet(q_permitReduceDateS); }
public void setQ_permitReduceDateS(String s){ q_permitReduceDateS=checkSet(s); }
public String getQ_permitReduceDateE(){ return checkGet(q_permitReduceDateE); }
public void setQ_permitReduceDateE(String s){ q_permitReduceDateE=checkSet(s); }

String closing;
public String getClosing(){ return checkGet(closing); }
public void setClosing(String s){ closing=checkSet(s); }
/**
 * <br>
 * <br>目的：傳回insert sql
 * <br>參數：無 
 * <br>傳回：無 ; 直接更改變數為新增成功與否
*/	
protected String[] getInsertSQL(){
	StringBuffer sbSQL = new StringBuffer("");
	Database db = new Database();  
	int getcut=0;
	if(getsKeySet()!=null)
		getcut = getsKeySet().length;	//有勾選的list中資料數
	//String sSQL = "";
	//String[] execSQLArray = new String[getcut];
	String[] strKeys = null;
	ResultSet rs = null;
	int i = 0;	
	//int counter=0;
	try {
		for (i=0; i<getcut; i++) {	
			strKeys = getsKeySet()[i].split(",");
			//先找出 building 的值
			String buildingsql = " select  " + 
								 " propertyNo, " +
								 " serialNo, " +
								 " propertyName1, " +
								 " signNo, " +
								 " doorPlate1,"+
								 " doorPlate2,"+
								 " doorPlate3,"+
								 " doorPlate4,"+		
								 " propertyKind, " +
								 " fundType, " +
								 " valuable, " +
								 " taxCredit, " +
								 " CArea, " +
								 " SArea, " +
								 " area, " +
								 " holdNume, " +
								 " holdDeno, " +
								 " holdArea, " +
								 " accountingTitle, " +
								 " bookValue, " +
								 " proofDoc, " +
								 " buildDate,"+
								 " sourceDate, "+
								 " useState, " +
								 " deprMethod,"+
								 " deprAmount,"+
								 " monthDepr,"+
								 " apportionEndYM,"+
								 " accumDeprYM,"+
								 " accumDepr,"+
								 " permitReduceDate,"+
								 " oldPropertyNo, " +
								 " oldSerialNo " +
								 " from UNTBU_Building "+
								 " where enterOrg = " + Common.sqlChar(strKeys[0]) +
								 " and ownership = " + Common.sqlChar(strKeys[1]) +
								 " and propertyNo = " + Common.sqlChar(strKeys[2]) +
								 " and serialNo = " + Common.sqlChar(strKeys[3]) +
								 "";
			rs = db.querySQL(buildingsql);
			if (rs.next()){
			    //已使用月數 = (系統日期 - 建築日期) ★/12 之餘額月數
				// total month = 月份 + 年*12月    format:yyymmdd
				int sMonth = Integer.parseInt(getEditDate().substring(3,5)) + (Integer.parseInt(getEditDate().substring(0,3)) * 12 ); 
				int eMonth = Integer.parseInt(rs.getString("buildDate").substring(3,5)) + (Integer.parseInt(rs.getString("buildDate").substring(0,3)) * 12 );
				String useMonth = ""+(sMonth - eMonth) % 12 ;

			    //已使用年數 = (系統日期 - 建築日期) ★/12 之商數
			    String useYear = ""+(sMonth - eMonth) / 12  ;
			    //累計折舊、殘餘價值
			    String deprMethod = rs.getString("deprMethod") ;
			    String accumDepr1 = "" ;
			    String scrapValue1 = "" ;
				if ( "1".equals(deprMethod) ) {
				    accumDepr1 = "0";
					scrapValue1 = "0";
				} else if ( "5".equals(deprMethod) )  {
				    accumDepr1 = ""+rs.getString("bookValue");
					scrapValue1 = "0";
				} else if (Integer.parseInt(deprMethod) >= 2 && Integer.parseInt(deprMethod) <= 4) {
					if ( Integer.parseInt(getEditDate().substring(0,5)) > Integer.parseInt(rs.getString("apportionEndYM")) ) {
					    accumDepr1 = rs.getString("deprAmount");
					} else {
					    accumDepr1 = ""+(Integer.parseInt(rs.getString("accumDepr")) + ((Datetime.BetweenTwoMonth(getEditDate().substring(0,5) , rs.getString("accumDeprYM")) - 1 ) * Integer.parseInt(rs.getString("monthDepr"))));
					}
					scrapValue1 = ""+ (Integer.parseInt(rs.getString("bookValue")) - Integer.parseInt(accumDepr1)) ;
				}			    
//|| ((Integer.parseInt(getEditDate()) < Integer.parseInt(rs.getString("permitReduceDate"))) && (getQ_cause()!="01" || getQ_cause()!="08")) ) {
			    
			    //需否呈報市府核定
			    String submitCityGov = "" ;
			    
			    if("01".equals(getQ_cause()) && "08".equals(getQ_cause()) && !"90".equals(getQ_cause())){
			    	if ( (Integer.parseInt(rs.getString("bookValue")) >= 2000000) || (Integer.parseInt(getEditDate()) < Integer.parseInt(rs.getString("permitReduceDate"))) ){	
			    		submitCityGov = "Y";
			    	}
			    }else{
			    	submitCityGov = "N";
			    }
//			    
//			    if (Integer.parseInt(rs.getString("bookValue")) >= 30000000){	
//			        submitCityGov = "Y";
//			    }else if( (Integer.parseInt(getEditDate()) < Integer.parseInt(rs.getString("permitReduceDate"))) ){
//			    	if(!"01".equals(getQ_cause()) && !"08".equals(getQ_cause())){
//			    	    submitCityGov = "Y";
//			        } else {
//			        	submitCityGov = "N";
//			        }
//			    }else if( (Integer.parseInt(rs.getString("bookValue"))< 30000000) && (Integer.parseInt(getEditDate()) >= Integer.parseInt(rs.getString("permitReduceDate"))) ){	    	
//			    	if(!"01".equals(getQ_cause()) && !"08".equals(getQ_cause())){
//			    	    submitCityGov = "Y";
//			        } else {
//			        	submitCityGov = "N";
//			        }
//			    } else {
//
//			    	submitCityGov = "N";
//			    }
			    	   
			//insert 資料
			    sbSQL.append(" insert into UNTBU_ReduceDetail(" ).append( 
				  " enterOrg,").append(
				  " ownership,").append(
				  " caseNo,").append(
				  " propertyNo,").append(
				  " serialNo,").append(
				  " propertyName1,").append(
				  " signNo,").append(
				  " doorPlate1,").append(
				  " doorPlate2,").append(
				  " doorPlate3,").append(
				  " doorPlate4,").append(	
				  " cause,").append(
				  " cause1,").append(
				  " lawBasis,").append(
				  " reduceDate,").append(
				  " newEnterOrg,").append(
				  " transferDate,").append(
				  " verify,").append(
				  " propertyKind,").append(
				  " fundType,").append(
				  " valuable, ").append(
				  " taxCredit,").append(
				  " CArea,").append(
				  " SArea,").append(
				  " area,").append(
				  " holdNume,").append(
				  " holdDeno,").append(
				  " holdArea,").append(
				  " accountingTitle,").append(
				  " bookValue,").append(
				  " proofDoc,").append(
				  " buildDate,").append(
				  " sourceDate,").append(
				  " useState,").append(
				  " deprMethod,").append(
			  	  " deprAmount,").append(
			 	  " monthDepr,").append(
				  " apportionEndYM,").append(
				  " accumDeprYM,").append(
				  " accumDepr,").append(
				  " permitReduceDate,").append(
				  " oldPropertyNo,").append(
				  " oldSerialNo,").append(
				  " useYear," ).append(
				  " useMonth,").append(
				  " accumDepr1," ).append(
				  " scrapValue1," ).append(
				  " submitCityGov," ).append(
				  " closing,").append(
				  " editID,").append(
				  " editDate,").append(
				  " editTime ").append(
				  ") values ( ").append( 
					Common.sqlChar(q_enterOrg)                    ).append( "," ).append(
					Common.sqlChar(q_ownership)                   ).append( "," ).append(
					Common.sqlChar(q_caseNo)                      ).append( "," ).append(
					Common.sqlChar(rs.getString("propertyNo"))    ).append( "," ).append(
					Common.sqlChar(rs.getString("serialNo"))      ).append( "," ).append(
					Common.sqlChar(rs.getString("propertyName1")) ).append( "," ).append(
					Common.sqlChar(rs.getString("signNo"))        ).append( "," ).append(
					Common.sqlChar(rs.getString("doorPlate1"))    ).append( "," ).append(
					Common.sqlChar(rs.getString("doorPlate2"))    ).append( "," ).append(
					Common.sqlChar(rs.getString("doorPlate3"))    ).append( "," ).append(
					Common.sqlChar(rs.getString("doorPlate4"))    ).append( "," ).append(
					Common.sqlChar(q_cause)                       ).append( "," ).append(
					Common.sqlChar(q_cause1)                      ).append( "," ).append(
					Common.sqlChar(q_lawBasis)                      ).append( "," ).append(
					Common.sqlChar(q_reduceDate)                  ).append( "," ).append(
					Common.sqlChar(q_newEnterOrg)                 ).append( "," ).append(
					Common.sqlChar(q_transferDate)                ).append( "," ).append(
					Common.sqlChar(q_verify)					  ).append( "," ).append(
					Common.sqlChar(rs.getString("propertyKind"))  ).append( "," ).append(
					Common.sqlChar(rs.getString("fundType"))      ).append( "," ).append(
					Common.sqlChar(rs.getString("valuable"))      ).append( "," ).append(
					Common.sqlChar(rs.getString("taxCredit"))     ).append( "," ).append(
					Common.sqlChar(rs.getString("CArea"))         ).append( "," ).append(
					Common.sqlChar(rs.getString("SArea"))         ).append( "," ).append(
					Common.sqlChar(rs.getString("area"))          ).append( "," ).append(
					Common.sqlChar(rs.getString("holdNume"))      ).append( "," ).append(
					Common.sqlChar(rs.getString("holdDeno"))      ).append( "," ).append(
					Common.sqlChar(rs.getString("holdArea"))      ).append( "," ).append(
					Common.sqlChar(rs.getString("accountingTitle")) ).append( "," ).append(
					Common.sqlChar(rs.getString("bookValue"))     ).append( "," ).append(
					Common.sqlChar(rs.getString("proofDoc"))      ).append( "," ).append(
					Common.sqlChar(rs.getString("buildDate"))     ).append( "," ).append(
					Common.sqlChar(rs.getString("sourceDate"))    ).append( "," ).append(
					Common.sqlChar(rs.getString("useState"))      ).append( "," ).append(
					Common.sqlChar(rs.getString("deprMethod"))    ).append( "," ).append(
					Common.sqlChar(rs.getString("deprAmount"))    ).append( "," ).append(
					Common.sqlChar(rs.getString("monthDepr"))     ).append( "," ).append(
					Common.sqlChar(rs.getString("apportionEndYM"))).append( "," ).append(
					Common.sqlChar(rs.getString("accumDeprYM"))   ).append( "," ).append(
					Common.sqlChar(rs.getString("accumDepr"))      ).append( "," ).append(
					Common.sqlChar(rs.getString("permitReduceDate")) ).append( "," ).append(
					Common.sqlChar(rs.getString("oldPropertyNo")) ).append( "," ).append(
					Common.sqlChar(rs.getString("oldSerialNo"))   ).append( "," ).append(
					Common.sqlChar(useYear)                       ).append( "," ).append(
					Common.sqlChar(useMonth)                      ).append( "," ).append(
					Common.sqlChar(accumDepr1)                    ).append( "," ).append(
					Common.sqlChar(scrapValue1)                   ).append( "," ).append(
					Common.sqlChar(submitCityGov)                 ).append( "," ).append(
					Common.sqlChar("N")   					      ).append( "," ).append(
					Common.sqlChar(getUserID())                   ).append( "," ).append(
					Common.sqlChar(getEditDate())                 ).append( "," ).append(
					Common.sqlChar(getEditTime())                 ).append(
					" ):;:");
			}
		}
		//setStateQueryOneSuccess();
	} catch (Exception e) {
		e.printStackTrace();
	} finally {
		db.closeAll();
	}
	//=====================================================================
	//execSQLArray[i] = sSQL;
	setStateInsertSuccess();
	return sbSQL.toString().split(":;:");
}

/**
 * <br>
 * <br>目的：依主鍵查詢單一資料
 * <br>參數：無
 * <br>傳回：傳回本物件
*/

public UNTBU017F  queryOne() throws Exception{
	Database db = new Database();
	UNTBU017F obj = this;
	try {
		String sql=" select a.enterOrg, a.ownership, a.caseNo, a.propertyNo, " +
			" a.serialNo, a.propertyName1, a.signNo,  " +
			" a.propertyKind, a.fundType, " + 
			" a.valuable, a.taxCredit,a.CArea,a.SArea, a.area, a.holdNume, a.holdDeno, a.holdArea, " +
			" a.accountingTitle, a.bookValue, " +
			" a.proofDoc, a.buildDate,a.sourceDate, a.useState, " +
			" a.oldPropertyNo, a.oldSerialNo, " +
			" c.organSName as enterOrgName, d.propertyName, e.propertyName as oldpropertyName, "+
			" a.doorPlate1 , a.doorPlate2 , a.doorPlate3 , a.doorPlate4 ,"+
			" a.deprMethod,a.deprAmount,a.monthDepr,a.apportionEndYM,a.accumDeprYM,a.accumDepr,a.permitReduceDate,"+
			" d.material, d.propertyUnit, d.limitYear, a.otherLimitYear " +
			" from UNTBU_Building a left join SYSPK_PropertyCode e on a.oldpropertyNo = e.propertyNo, SYSCA_ORGAN c, "+
			" SYSPK_PropertyCode d where 1=1 " +
			" and a.enterOrg 	= " + Common.sqlChar(enterOrg) +
			" and a.ownership 	= " + Common.sqlChar(ownership) +
			" and a.propertyNo 	= " + Common.sqlChar(propertyNo) +
			" and a.serialNo 	= " + Common.sqlChar(serialNo) +
			" and a.enterOrg	= c.organID " +
			" and a.propertyNo 	= d.propertyNo "+
			" order by a.signNo " +
			"";
		ResultSet rs = db.querySQL(sql);
		if (rs.next()){
			obj.setEnterOrg(rs.getString("enterOrg"));
			obj.setEnterOrgName(rs.getString("enterOrgName"));
			obj.setOwnership(rs.getString("ownership"));
			obj.setPropertyNo(rs.getString("propertyNo"));
			obj.setPropertyName(rs.getString("propertyName"));
			obj.setMaterial(rs.getString("material"));
			obj.setPropertyUnit(rs.getString("propertyUnit"));
			obj.setLimitYear(rs.getString("limitYear"));
			obj.setOtherLimitYear(rs.getString("otherLimitYear"));
			obj.setSerialNo(rs.getString("serialNo"));
			obj.setPropertyName1(rs.getString("propertyName1"));
			obj.setDoorPlate1(rs.getString("doorPlate1"));
			obj.setDoorPlate2(rs.getString("doorPlate2"));
			obj.setDoorPlate3(rs.getString("doorPlate3"));
			obj.setDoorPlate4(rs.getString("doorPlate4"));
			obj.setPropertyKind(rs.getString("propertyKind"));
			obj.setFundType(rs.getString("fundType"));
			obj.setValuable(rs.getString("valuable"));
			obj.setTaxCredit(rs.getString("taxCredit"));
			obj.setCArea(rs.getString("CArea"));
			obj.setSArea(rs.getString("SArea"));
			obj.setArea(rs.getString("area"));
			obj.setHoldNume(rs.getString("holdNume"));
			obj.setHoldDeno(rs.getString("holdDeno"));
			obj.setHoldArea(rs.getString("holdArea"));
			obj.setAccountingTitle(rs.getString("accountingTitle"));
			obj.setBookValue(rs.getString("bookValue"));
			obj.setProofDoc(rs.getString("proofDoc"));
			obj.setBuildDate(rs.getString("buildDate"));
			obj.setSourceDate(rs.getString("sourceDate"));
			obj.setUseState(rs.getString("useState"));
			obj.setDeprMethod(rs.getString("deprMethod"));
			obj.setDeprAmount(rs.getString("deprAmount"));
			obj.setMonthDepr(rs.getString("monthDepr"));
			obj.setApportionEndYM(rs.getString("apportionEndYM"));
			obj.setAccumDeprYM(rs.getString("accumDeprYM"));
			obj.setAccumDepr(rs.getString("accumDepr"));
			obj.setPermitReduceDate(rs.getString("permitReduceDate"));
			obj.setOldPropertyNo(rs.getString("oldPropertyNo"));
			obj.setOldPropertyName(rs.getString("oldpropertyName"));
			obj.setOldSerialNo(rs.getString("oldSerialNo"));
			obj.setSignNo(rs.getString("signNo"));
			obj.setSignNo1(rs.getString("signNo").substring(0,1) + "000000");
			obj.setSignNo2(rs.getString("signNo").substring(0,3) + "0000");
			obj.setSignNo3(rs.getString("signNo").substring(0,7));
			obj.setSignNo4(rs.getString("signNo").substring(7,12));
			obj.setSignNo5(rs.getString("signNo").substring(12));
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
	int counter=0;
	try {
		String sql=" select a.enterOrg, d.propertyName,a.ownership, a.caseNo, a.propertyNo, " +
				"a.serialNo, " +
				" (q.signDesc) as signName, a.signNo, " +
				" (case a.propertyKind when '01' then '公務用' when '02' then '公共用' when '03' then '事業用' when '04' then '非公用' else '' end) propertyKind,"+
				" a.holdArea, a.bookValue, a.sourceDate ," +
				" u.codeName useState "+
				" from UNTBU_Building a left join SYSCA_Code u on u.codeKindID='UST' and a.useState = u.codeID,SYSCA_Organ c ,SYSPK_PropertyCode d " +
				" where a.enterOrg = c.organID and a.propertyNo = d.propertyNo and a.verify = 'Y' "+
				" and a.enterOrg 	= " + Common.sqlChar(q_enterOrg) +
				" and a.ownership 	= " + Common.sqlChar(q_ownership) +
				" and a.dataState = " + Common.sqlChar(q_dataState) +
				" and (a.enterOrg)  not in ( " +
				"  select enterOrg from UNTBU_ReduceDetail " +
				"  ) " +
				" and (a.ownership)  not in ( " +
				"  select ownership from UNTBU_ReduceDetail " +
				"  ) " +
				" and (a.propertyNo)  not in ( " +
				"  select propertyNo from UNTBU_ReduceDetail " +
				"  ) " +
				" and (a.serialNo)  not in ( " +
				"  select serialNo from UNTBU_ReduceDetail " +
				"  ) " +
				" and (a.enterOrg)  not in ( " +
				" select enterOrg from UNTBU_AdjustDetail  where verify='N' " +
				" ) "  +
				" and (a.ownership)  not in ( " +
				" select ownership from UNTBU_AdjustDetail  where verify='N' " +
				" ) "  +
				" and (a.propertyNo)  not in ( " +
				" select propertyNo from UNTBU_AdjustDetail  where verify='N' " +
				" ) "  +
				" and (a.serialNo)  not in ( " +
				" select serialNo from UNTBU_AdjustDetail  where verify='N' " +
				" ) " ;
		
			if (!"".equals(getQ_propertyNoS()))
				sql+=" and a.propertyNo >= " + Common.sqlChar(getQ_propertyNoS());		
			if (!"".equals(getQ_propertyNoE()))
				sql+=" and a.propertyNo<=" + Common.sqlChar(getQ_propertyNoE());						
			if (!"".equals(getQ_serialNoS())){
				setQ_serialNoS(Common.formatFrontZero(getQ_serialNoS(),7));
				sql+=" and a.serialNo >= " + Common.sqlChar(getQ_serialNoS()) ;
			}
			if (!"".equals(getQ_serialNoE())){
				setQ_serialNoE(Common.formatFrontZero(getQ_serialNoE(),7));
				sql+=" and a.serialNo <= " + Common.sqlChar(getQ_serialNoE()) ;
			}
			
			if (!"".equals(getQ_signNo1()))
				q_signNo=getQ_signNo1().substring(0,1)+"______";
			if (!"".equals(getQ_signNo2()))
				q_signNo=getQ_signNo2().substring(0,3)+"____";			
			if (!"".equals(getQ_signNo3())){
				if (getQ_signNo3().length()==4){
					q_signNo="E__" + getQ_signNo3();
				}else{
					q_signNo=getQ_signNo3();
				}	
			}
			if (!"".equals(getQ_signNo4())){
				setQ_signNo4(Common.formatFrontZero(getQ_signNo4(),5));
				setQ_signNo5(Common.formatFrontZero(getQ_signNo5(),3));	
				if ("".equals(q_signNo)){
					q_signNo="_______"+getQ_signNo4()+getQ_signNo5();
				}else{
					q_signNo=q_signNo+getQ_signNo4()+getQ_signNo5();				
				}
			}	

			if (!"".equals(q_signNo))
				sql+=" and a.signNo like '" + q_signNo + "%'" ;			
			
			if (!"".equals(getQ_propertyKind()))
				sql+=" and a.propertyKind = " + Common.sqlChar(getQ_propertyKind()) ;
			if (!"".equals(getQ_fundType()))
				sql+=" and a.fundType = " + Common.sqlChar(getQ_fundType()) ;
			if (!"".equals(getQ_valuable()))
				sql+=" and a.valuable = " + Common.sqlChar(getQ_valuable()) ;
			if (!"".equals(getQ_taxCredit()))
				sql+=" and a.taxCredit = " + Common.sqlChar(getQ_taxCredit()) ;
			if (!"".equals(getQ_permitReduceDateS()))
				sql+=" and a.permitReduceDate >= " + Common.sqlChar(getQ_permitReduceDateS()) ;
			if (!"".equals(getQ_permitReduceDateE()))
				sql+=" and a.permitReduceDate <= " + Common.sqlChar(getQ_permitReduceDateE()) ;
			sql+=" order by a.signNo ";
		ResultSet rs = db.querySQL(sql);
		//把查到的資料放入list裡..
		//和.jsp裡的 List區 內容要搭配起來
		while (rs.next()){
			counter++;
			String rowArray[]=new String[9];
			rowArray[0]=rs.getString("enterOrg");
			rowArray[1]=rs.getString("ownership");
			rowArray[2]=rs.getString("propertyNo"); 
			rowArray[3]=rs.getString("serialNo"); 
			rowArray[4]=rs.getString("propertyName");
			rowArray[5]=getSignDescName(rs.getString("signNo").substring(0,7)) + rs.getString("signNo").substring(7,12) + '-' + rs.getString("signNo").substring(12);
			rowArray[6]=rs.getString("holdArea"); 
			rowArray[7]=rs.getString("bookValue"); 
			rowArray[8]=rs.getString("useState");
			objList.add(rowArray);
			if (counter==getListLimit()){
				setErrorMsg(getListLimitError());
				break;
			}
		}
		setStateQueryAllSuccess();
	} catch (Exception e) {
		e.printStackTrace();
	} finally {
		db.closeAll();
	}
	return objList;
}

private String getSignDescName(String signNo){
	Database db = null;
	ResultSet rs = null;
	String sql = null;
	String result = null;
	try{
		sql = "select signdesc from SYSCA_SIGN where" +
				" signNo = " + Common.sqlChar(signNo);
		
		db = new Database();
		rs = db.querySQL(sql);
		if(rs.next()){				
			result = rs.getString("signdesc");
		}
		rs.close();
	}catch(Exception e){
		System.out.println("getSignDescName Exception => " + e.getMessage());
	}finally{
		db.closeAll();
	}
	return result;
}
}
