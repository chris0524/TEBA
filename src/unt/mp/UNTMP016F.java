/*
*<br>程式目的：動產減少作業－批次新增明細資料
*<br>程式代號：untmp016f
*<br>程式日期：0941019
*<br>程式作者：judy
*<br>--------------------------------------------------------
*<br>修改作者　　修改日期　　　修改目的
*<br>--------------------------------------------------------
*/

package unt.mp;

import java.sql.ResultSet;
import java.util.ArrayList;
import util.*;

public class UNTMP016F extends SuperBean{

String enterOrgName;
String oldPropertyName;
String propertyName;
String cause;
String cause1;
String newEnterOrg;
String transferDate;
String enterOrg;
String ownership;
String caseNo;
String reduceDate;
String verify;
String propertyNo;
String lotNo;
String serialNo;
String material;
String otherMaterial;
String propertyUnit;
String otherPropertyUnit;
String limitYear;
String otherLimitYear;
String propertyName1;
String enterDate;
String buyDate;
String propertyKind;
String fundType;
String valuable;
String accountingTitle;
String oldBookAmount;
String oldBookUnit;
String oldBookValue;
String articleName;
String nameplate;
String specification;
String sourceDate;
String licensePlate;
String moveDate;
String keepUnit;
String keepUnitName;
String keeper;
String keeperName;
String useUnit;
String useUnitName;
String userNo;
String userName;
String place;
String deprMethod;
String scrapValue;
String deprAmount;
String apportionYear;
String monthDepr;
String useEndYM;
String apportionEndYM;
String accumDeprYM;
String accumDepr;  
String permitReduceDate;
String oldPropertyNo;
String oldSerialNo;
String bookAmount;
String originalUnit;
String bookValue;

public String getMaterial(){ return checkGet(material); }
public void setMaterial(String s){ material=checkSet(s); }
public String getOtherMaterial(){ return checkGet(otherMaterial); }
public void setOtherMaterial(String s){ otherMaterial=checkSet(s); }
public String getPropertyUnit(){ return checkGet(propertyUnit); }
public void setPropertyUnit(String s){ propertyUnit=checkSet(s); }
public String getOtherPropertyUnit(){ return checkGet(otherPropertyUnit); }
public void setOtherPropertyUnit(String s){ otherPropertyUnit=checkSet(s); }
public String getLimitYear(){ return checkGet(limitYear); }
public void setLimitYear(String s){ limitYear=checkSet(s); }
public String getOtherLimitYear(){ return checkGet(otherLimitYear); }
public void setOtherLimitYear(String s){ otherLimitYear=checkSet(s); }
public String getBookAmount(){ return checkGet(bookAmount); }
public void setBookAmount(String s){ bookAmount=checkSet(s); }
public String getOriginalUnit(){ return checkGet(originalUnit); }
public void setOriginalUnit(String s){ originalUnit=checkSet(s); }
public String getBookValue(){ return checkGet(bookValue); }
public void setBookValue(String s){ bookValue=checkSet(s); }
public String getCause(){ return checkGet(cause); }
public void setCause(String s){ cause=checkSet(s); }
public String getCause1(){ return checkGet(cause1); }
public void setCause1(String s){ cause1=checkSet(s); }
public String getNewEnterOrg(){ return checkGet(newEnterOrg); }
public void setNewEnterOrg(String s){ newEnterOrg=checkSet(s); }
public String getTransferDate(){ return checkGet(transferDate); }
public void setTransferDate(String s){ transferDate=checkSet(s); }
public String getEnterOrg(){ return checkGet(enterOrg); }
public void setEnterOrg(String s){ enterOrg=checkSet(s); }
public String getOwnership(){ return checkGet(ownership); }
public void setOwnership(String s){ ownership=checkSet(s); }
public String getCaseNo(){ return checkGet(caseNo); }
public void setCaseNo(String s){ caseNo=checkSet(s); }
public String getReduceDate(){ return checkGet(reduceDate); }
public void setReduceDate(String s){ reduceDate=checkSet(s); }
public String getVerify(){ return checkGet(verify); }
public void setVerify(String s){ verify=checkSet(s); }
public String getPropertyNo(){ return checkGet(propertyNo); }
public void setPropertyNo(String s){ propertyNo=checkSet(s); }
public String getLotNo(){ return checkGet(lotNo); }
public void setLotNo(String s){ lotNo=checkSet(s); }
public String getSerialNo(){ return checkGet(serialNo); }
public void setSerialNo(String s){ serialNo=checkSet(s); }
public String getPropertyName1(){ return checkGet(propertyName1); }
public void setPropertyName1(String s){ propertyName1=checkSet(s); }
public String getEnterDate(){ return checkGet(enterDate); }
public void setEnterDate(String s){ enterDate=checkSet(s); }
public String getBuyDate(){ return checkGet(buyDate); }
public void setBuyDate(String s){ buyDate=checkSet(s); }
public String getPropertyKind(){ return checkGet(propertyKind); }
public void setPropertyKind(String s){ propertyKind=checkSet(s); }
public String getFundType(){ return checkGet(fundType); }
public void setFundType(String s){ fundType=checkSet(s); }
public String getValuable(){ return checkGet(valuable); }
public void setValuable(String s){ valuable=checkSet(s); }
public String getAccountingTitle(){ return checkGet(accountingTitle); }
public void setAccountingTitle(String s){ accountingTitle=checkSet(s); }
public String getOldBookAmount(){ return checkGet(oldBookAmount); }
public void setOldBookAmount(String s){ oldBookAmount=checkSet(s); }
public String getOldBookUnit(){ return checkGet(oldBookUnit); }
public void setOldBookUnit(String s){ oldBookUnit=checkSet(s); }
public String getOldBookValue(){ return checkGet(oldBookValue); }
public void setOldBookValue(String s){ oldBookValue=checkSet(s); }
public String getArticleName(){ return checkGet(articleName); }
public void setArticleName(String s){ articleName=checkSet(s); }
public String getNameplate(){ return checkGet(nameplate); }
public void setNameplate(String s){ nameplate=checkSet(s); }
public String getSpecification(){ return checkGet(specification); }
public void setSpecification(String s){ specification=checkSet(s); }
public String getSourceDate(){ return checkGet(sourceDate); }
public void setSourceDate(String s){ sourceDate=checkSet(s); }
public String getLicensePlate(){ return checkGet(licensePlate); }
public void setLicensePlate(String s){ licensePlate=checkSet(s); }
public String getMoveDate(){ return checkGet(moveDate); }
public void setMoveDate(String s){ moveDate=checkSet(s); }
public String getKeepUnit(){ return checkGet(keepUnit); }
public void setKeepUnit(String s){ keepUnit=checkSet(s); }
public String getKeepUnitName(){ return checkGet(keepUnitName); }
public void setKeepUnitName(String s){ keepUnitName=checkSet(s); }
public String getKeeper(){ return checkGet(keeper); }
public void setKeeper(String s){ keeper=checkSet(s); }
public String getKeeperName(){ return checkGet(keeperName); }
public void setKeeperName(String s){ keeperName=checkSet(s); }
public String getUseUnit(){ return checkGet(useUnit); }
public void setUseUnit(String s){ useUnit=checkSet(s); }
public String getUseUnitName(){ return checkGet(useUnitName); }
public void setUseUnitName(String s){ useUnitName=checkSet(s); }
public String getUserNo(){ return checkGet(userNo); }
public void setUserNo(String s){ userNo=checkSet(s); }
public String getUserName(){ return checkGet(userName); }
public void setUserName(String s){ userName=checkSet(s); }
public String getPlace(){ return checkGet(place); }
public void setPlace(String s){ place=checkSet(s); }
public String getDeprMethod(){ return checkGet(deprMethod); }
public void setDeprMethod(String s){ deprMethod=checkSet(s); }
public String getScrapValue(){ return checkGet(scrapValue); }
public void setScrapValue(String s){ scrapValue=checkSet(s); }
public String getDeprAmount(){ return checkGet(deprAmount); }
public void setDeprAmount(String s){ deprAmount=checkSet(s); }
public String getApportionYear(){ return checkGet(apportionYear); }
public void setApportionYear(String s){ apportionYear=checkSet(s); }
public String getMonthDepr(){ return checkGet(monthDepr); }
public void setMonthDepr(String s){ monthDepr=checkSet(s); }
public String getUseEndYM(){ return checkGet(useEndYM); }
public void setUseEndYM(String s){ useEndYM=checkSet(s); }
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
public String getOldSerialNo(){ return checkGet(oldSerialNo); }
public void setOldSerialNo(String s){ oldSerialNo=checkSet(s); }

String q_enterOrg;
String q_dataState;
String q_ownership; 
String q_propertyNoS;
String q_propertyNoE;
String q_propertyNoSName;
String q_propertyNoEName;
String q_serialNoS;
String q_serialNoE;
String q_propertyKind;
String q_fundType;
String q_valuable;
String q_permitReduceDateS;
String q_permitReduceDateE;

String q_caseNo;
String q_reduceDate;
String q_verify;

String q_cause;
String q_cause1;
String q_newEnterOrg;
String q_newEnterOrgName;
String q_transferDate;

String sSQL = "";
String strKeySet[] = null;

public String getQ_enterOrg(){ return checkGet(q_enterOrg); }
public void setQ_enterOrg(String s){ q_enterOrg=checkSet(s); }
public String getQ_ownership(){ return checkGet(q_ownership); }
public void setQ_ownership(String s){ q_ownership=checkSet(s); }
public String getsSQL(){ return checkGet(sSQL); }
public void setsSQL(String s){ sSQL=checkSet(s); }
public String[] getsKeySet(){ return strKeySet; }
public void setsKeySet(String[] s){ strKeySet=s; }

public String getEnterOrgName(){ return checkGet(enterOrgName); }
public void setEnterOrgName(String s){ enterOrgName=checkSet(s); }
public String getOldPropertyName(){ return checkGet(oldPropertyName); }
public void setOldPropertyName(String s){ oldPropertyName=checkSet(s); }
public String getPropertyName(){ return checkGet(propertyName); }
public void setPropertyName(String s){ propertyName=checkSet(s); }
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
public String getQ_propertyKind(){ return checkGet(q_propertyKind); }
public void setQ_propertyKind(String s){ q_propertyKind=checkSet(s); }
public String getQ_fundType(){ return checkGet(q_fundType); }
public void setQ_fundType(String s){ q_fundType=checkSet(s); }
public String getQ_valuable(){ return checkGet(q_valuable); }
public void setQ_valuable(String s){ q_valuable=checkSet(s); }
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
public String getQ_permitReduceDateS(){ return checkGet(q_permitReduceDateS); }
public void setQ_permitReduceDateS(String s){ q_permitReduceDateS=checkSet(s); }
public String getQ_permitReduceDateE(){ return checkGet(q_permitReduceDateE); }
public void setQ_permitReduceDateE(String s){ q_permitReduceDateE=checkSet(s); }
public String getQ_caseNo(){ return checkGet(q_caseNo); }
public void setQ_caseNo(String s){ q_caseNo=checkSet(s); }
public String getQ_reduceDate(){ return checkGet(q_reduceDate); }
public void setQ_reduceDate(String s){ q_reduceDate=checkSet(s); }
public String getQ_verify(){ return checkGet(q_verify); }
public void setQ_verify(String s){ q_verify=checkSet(s); }

String toggleAll;
public String getToggleAll(){ if (!"".equals(checkGet(toggleAll))) return "checked"; else return "unchecked"; }    
public void setToggleAll(String s){ toggleAll=checkSet(s); }    

String q_buyDateS;
String q_buyDateE;

public String getQ_buyDateS(){ return checkGet(q_buyDateS); }
public void setQ_buyDateS(String s){ q_buyDateS=checkSet(s); }
public String getQ_buyDateE(){ return checkGet(q_buyDateE); }
public void setQ_buyDateE(String s){ q_buyDateE=checkSet(s); }

String q_keepBureau;
String q_keepUnit;
String q_keeper;

public String getQ_keepBureau(){ return checkGet(q_keepBureau); }
public void setQ_keepBureau(String s){ q_keepBureau=checkSet(s); }
public String getQ_keepUnit(){ return checkGet(q_keepUnit); }
public void setQ_keepUnit(String s){ q_keepUnit=checkSet(s); }
public String getQ_keeper(){ return checkGet(q_keeper); }
public void setQ_keeper(String s){ q_keeper=checkSet(s); }


String returnPlace;			//繳存地點
String cause2;				//報廢或失竊原因
String dealSuggestion;		//擬予處理意見
String scrapValue2;			//預估殘值總價
public String getReturnPlace(){ return checkGet(returnPlace); }
public void setReturnPlace(String s){ returnPlace=checkSet(s); }
public String getCause2(){ return checkGet(cause2); }
public void setCause2(String s){ cause2=checkSet(s); }
public String getDealSuggestion(){ return checkGet(dealSuggestion); }
public void setDealSuggestion(String s){ dealSuggestion=checkSet(s); }
public String getScrapValue2(){ return checkGet(scrapValue2); }
public void setScrapValue2(String s){ scrapValue2=checkSet(s); }

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
			//先找出 movable 和 movabledetail 的值
		    String movablesql = " select b.propertyNo , b.lotNo , b.serialNo ,  "+
							    " a.otherMaterial, a.otherPropertyUnit, a.otherLimitYear , a.propertyName1 , a.enterDate ,  a.buyDate , " +
								" a.propertyKind , a.fundType , a.valuable , a.accountingTitle ," +
								" b.bookAmount , decode(a.originalUnit,'',a.originalUnit,b.bookValue) as originalUnit, b.bookValue , "+
								" a.articleName , a.nameplate , a.specification , a.sourceDate , "+
								" b.licensePlate, b.moveDate , b.keepUnit , b.keeper , b.useUnit , b.userNo , b.place , "+
								" a.deprMethod , b.scrapValue , b.deprAmount , b.apportionYear , decode(b.monthDepr,'','0',b.monthDepr) as monthDepr, "+
								" a.useEndYM , a.apportionEndYM , "+
								" b.accumDeprYM , decode(b.accumDepr,'','0',b.accumDepr) as accumDepr,  "+
								" a.permitReduceDate , b.oldPropertyNo , b.oldSerialNo ,a.computerType" +
								" from untmp_movable a , untmp_movableDetail b "+
								" where a.enterOrg = b.enterOrg "+
								" and a.ownership = b.ownership"+
								" and a.propertyno = b.propertyno " +
								" and a.lotNo=b.lotNo "+
								" and b.enterOrg = " + Common.sqlChar(strKeys[0]) +
								" and b.ownership = " + Common.sqlChar(strKeys[1]) +
								" and b.propertyNo = " + Common.sqlChar(strKeys[3]) +
								" and b.serialNo = " + Common.sqlChar(strKeys[4]) +
								" " ;

			rs = db.querySQL(movablesql);
			if (rs.next()){
			    //已使用月數 = (系統日期 - 建築日期) ★/12 之餘額月數
				// total month = 月份 + 年*12月    format:yyymmdd
				int sMonth = Integer.parseInt(getEditDate().substring(3,5)) + (Integer.parseInt(getEditDate().substring(0,3)) * 12 ); 
				int eMonth = Integer.parseInt(rs.getString("buyDate").substring(3,5)) + (Integer.parseInt(rs.getString("buyDate").substring(0,3)) * 12 );
				String useMonth = ""+(sMonth - eMonth) % 12 ;

			    //已使用年數 = (系統日期 - 建築日期) ★/12 之商數
			    String useYear = ""+(sMonth - eMonth) / 12  ;
			    //累計折舊、殘餘價值
			    String deprMethod = rs.getString("deprMethod") ;
			    String accumDepr1 = "" ;
			    String scrapValue1 = "" ;
			    if ( "01".equals(deprMethod) ) {
				    accumDepr1 = "0";
					scrapValue1 = "0";
				} else if ( "05".equals(deprMethod) )  {
				    accumDepr1 = ""+rs.getString("bookValue");
					scrapValue1 = "0";
				} else if (Integer.parseInt(deprMethod) >= 2 && Integer.parseInt(deprMethod) <= 4) {
					if ( Integer.parseInt(getEditDate().substring(0,5)) > Integer.parseInt(rs.getString("apportionEndYM")) ) {
					    accumDepr1 = rs.getString("deprAmount");
					} else {
					    accumDepr1 = ""+(Integer.parseInt(rs.getString("accumDepr")) + (Datetime.BetweenTwoMonth(Datetime.getDateAdd("m",-1,rs.getString("accumDeprYM")+"01"),getEditDate()) * Integer.parseInt(rs.getString("monthDepr"))));
					}
					scrapValue1 = ""+ (Integer.parseInt(rs.getString("bookValue")) - Integer.parseInt(accumDepr1)) ;
				}			    

			    
			    //需否呈報市府核定
			    String submitCityGov = "" ;
			    /*if(q_cause.equals("01") || q_cause.equals("03") || q_cause.equals("04")){
			    	submitCityGov = "Y";
			    }else */
			    if ((Integer.parseInt(rs.getString("bookValue")) > 15000000) || ((Integer.parseInt(getEditDate()) < Integer.parseInt(rs.getString("permitReduceDate"))) && (!"01".equals(q_cause) && !"08".equals(q_cause) && !"90".equals(q_cause)))) {
			        submitCityGov = "Y";
			    } else if("03".equals(q_cause) || "04".equals(q_cause)) {
			    	submitCityGov = "Y";
			    } else {
			        submitCityGov = "N";
			    }
			    
				//if(( form1.bookValue.value >= 15000000 ) || (( getToday() < form1.permitReduceDate.value ) && (form1.cause.value!="01" && form1.cause.value!="08"))){
				//	form1.submitCityGov.value = "Y"; 
				//}else{
				//	form1.submitCityGov.value = "N"; 
				//}
			//insert 資料
			    sbSQL.append(" insert into UNTMP_ReduceDetail(" ).append(
				  " enterOrg,").append(
				  " ownership,").append(
				  " caseNo,").append(
				  " reduceDate,").append(
				  " verify,").append(
				  " cause,").append(
				  " cause1,").append(
				  " newEnterOrg,").append(
				  " transferDate,").append(
				  " propertyNo, " ).append(
				  " lotNo," ).append(
				  " serialNo," ).append(
				  " otherMaterial," ).append(
				  " otherPropertyUnit,").append(
				  " otherLimitYear," ).append(
				  " propertyName1," ).append(
				  " enterDate," ).append(
				  " buyDate," ).append(
				  " propertyKind," ).append(
				  " fundType," ).append(
				  " valuable," ).append(
				  " accountingTitle," ).append(
				  " oldBookAmount," ).append(
				  " oldBookUnit," ).append(
				  " oldBookValue," ).append(
				  " articleName," ).append(
				  " nameplate," ).append(
				  " specification," ).append(
				  " sourceDate," ).append(
				  " licensePlate," ).append(
				  " moveDate," ).append(
				  " keepUnit," ).append(
				  " keeper," ).append(
				  " useUnit," ).append(
				  " userNo," ).append(
				  " place," ).append(
				  " deprMethod," ).append(
				  " scrapValue," ).append(
				  " deprAmount," ).append(
				  " apportionYear," ).append(
				  " monthDepr," ).append(
				  " useEndYM," ).append(
				  " apportionEndYM," ).append(
				  " accumDeprYM," ).append(
				  " accumDepr," ).append(
				  " permitReduceDate," ).append(
				  " oldPropertyNo," ).append(
				  " oldSerialNo," ).append(	
				  " adjustBookAmount,").append(
				  " adjustBookValue,").append(
				  " newBookAmount,").append(
				  " newBookValue,").append(
				  " useYear," ).append(
				  " useMonth," ).append(
				  " accumDepr1," ).append(
				  " scrapValue1," ).append(
				  " submitCityGov," ).append(
				  " computerType," ).append(
				  " closing," ).append(
				  	" returnPlace," ).append(
				  	" cause2," ).append(
				  	" dealSuggestion," ).append(
				  	" scrapValue2," ).append(
				  " editID,").append(
				  " editDate,").append(
				  " editTime ").append(
				  ") values ( ").append( 
				 	Common.sqlChar(q_enterOrg)						).append( "," ).append(
					Common.sqlChar(q_ownership)						).append( "," ).append(
					Common.sqlChar(q_caseNo)						).append( "," ).append(
					Common.sqlChar(q_reduceDate)					).append( "," ).append(
					Common.sqlChar(q_verify)						).append( "," ).append(
					Common.sqlChar(q_cause)							).append( "," ).append(
					Common.sqlChar(q_cause1)						).append( "," ).append(
					Common.sqlChar(q_newEnterOrg)					).append( "," ).append(
					Common.sqlChar(q_transferDate)					).append( "," ).append(
					Common.sqlChar(rs.getString("propertyNo"))		).append( "," ).append(				  	
					Common.sqlChar(rs.getString("lotNo"))			).append( "," ).append(
					Common.sqlChar(rs.getString("serialNo"))		).append( "," ).append(
					Common.sqlChar(rs.getString("otherMaterial"))		).append( "," ).append(
					Common.sqlChar(rs.getString("otherPropertyUnit"))		).append( "," ).append(
					Common.sqlChar(rs.getString("otherLimitYear"))	).append( "," ).append(
					Common.sqlChar(rs.getString("propertyName1"))	).append( "," ).append(
					Common.sqlChar(rs.getString("enterDate"))		).append( "," ).append(
					Common.sqlChar(rs.getString("buyDate"))			).append( "," ).append(
					Common.sqlChar(rs.getString("propertyKind"))	).append( "," ).append(
					Common.sqlChar(rs.getString("fundType"))		).append( "," ).append(
					Common.sqlChar(rs.getString("valuable"))		).append( "," ).append(
					Common.sqlChar(rs.getString("accountingTitle"))	).append( "," ).append(
					Common.sqlChar(rs.getString("bookAmount"))	).append( "," ).append(
					Common.sqlChar(rs.getString("originalUnit"))		).append( "," ).append(					
					Common.sqlChar(rs.getString("bookValue"))	).append( "," ).append(
					Common.sqlChar(rs.getString("articleName"))		).append( "," ).append(
					Common.sqlChar(rs.getString("nameplate"))		).append( "," ).append(
					Common.sqlChar(rs.getString("specification"))	).append( "," ).append(
					Common.sqlChar(rs.getString("sourceDate"))		).append( "," ).append(
					Common.sqlChar(rs.getString("licensePlate"))	).append( "," ).append(
					Common.sqlChar(rs.getString("moveDate"))		).append( "," ).append(
					Common.sqlChar(rs.getString("keepUnit"))		).append( "," ).append(
					Common.sqlChar(rs.getString("keeper"))			).append( "," ).append(
					Common.sqlChar(rs.getString("useUnit"))			).append( "," ).append(
					Common.sqlChar(rs.getString("userNo"))			).append( "," ).append(
					Common.sqlChar(rs.getString("place"))			).append( "," ).append(
					Common.sqlChar(rs.getString("deprMethod"))		).append( "," ).append(
					Common.sqlChar(rs.getString("scrapValue"))		).append( "," ).append(
					Common.sqlChar(rs.getString("deprAmount"))		).append( "," ).append(
					Common.sqlChar(rs.getString("apportionYear"))	).append( "," ).append(
					Common.sqlChar(rs.getString("monthDepr"))		).append( "," ).append(
					Common.sqlChar(rs.getString("useEndYM"))		).append( "," ).append(
					Common.sqlChar(rs.getString("apportionEndYM"))	).append( "," ).append(
					Common.sqlChar(rs.getString("accumDeprYM"))		).append( "," ).append(
					Common.sqlChar(rs.getString("accumDepr"))		).append( "," ).append(
					Common.sqlChar(rs.getString("permitReduceDate"))).append( "," ).append(
					Common.sqlChar(rs.getString("oldPropertyNo"))	).append( "," ).append(
					Common.sqlChar(rs.getString("oldSerialNo"))		).append( "," ).append(
					Common.sqlChar(rs.getString("bookAmount"))		).append( "," ).append(
					Common.sqlChar(rs.getString("bookValue"))		).append( "," ).append(
					Common.sqlChar("0")		).append( "," ).append(
					Common.sqlChar("0")		).append( "," ).append(
					Common.sqlChar(useYear)		).append( "," ).append(
					Common.sqlChar(useMonth)		).append( "," ).append(
					Common.sqlChar(accumDepr1)		).append( "," ).append(
					Common.sqlChar(scrapValue1)		).append( "," ).append(
					Common.sqlChar(submitCityGov)		).append( "," ).append(
					Common.sqlChar(rs.getString("computerType"))	).append( "," ).append(
					Common.sqlChar("N")		).append( "," ).append(
							Common.sqlChar(getReturnPlace())		).append( "," ).append(
							Common.sqlChar(getCause2())				).append( "," ).append(
							Common.sqlChar(getDealSuggestion())		).append( "," ).append(
							Common.sqlChar(getScrapValue2())		).append( "," ).append(
					Common.sqlChar(getEditID())                   	).append( "," ).append(
					Common.sqlChar(getEditDate())                 	).append( "," ).append(
					Common.sqlChar(getEditTime())                 	).append(
					" ):;:");
			}
		}

		//setStateQueryOneSuccess();
	} catch (Exception e) {
		e.printStackTrace();
	} finally {
		db.closeAll();
	}
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

public UNTMP016F  queryOne() throws Exception{
	Database db = new Database();
	UNTMP016F obj = this;
	try {
		String sql= " select distinct a.enterOrg, c.organSName as enterOrgName, a.ownership, " +
					" a.propertyNo, a.lotNo, a.serialNo, d.propertyName, " +
					" d.propertyUnit, b.otherPropertyUnit, d.material, b.otherMaterial, d.limitYear, b.otherLimitYear, " +
					" b.propertyName1, b.enterDate, b.buyDate, b.propertyKind, "+
					" b.fundType, b.valuable, b.accountingTitle, a.bookAmount, b.originalUnit, a.bookValue, b.articleName, b.nameplate, b.specification, b.sourceDate, " +
					" a.licensePlate,a.moveDate, a.keepUnit, a.keeper, a.useUnit, a.userNo, a.place, " +
					" (select f.unitName from UNTMP_KeepUnit f where a.enterOrg=f.enterOrg and a.keepUnit=f.unitNo) as keepUnitName, "+
					" (select f.unitName from UNTMP_KeepUnit f where a.enterOrg=f.enterOrg and a.useUnit=f.unitNo) as useUnitName, "+
					" (select f.keeperName from UNTMP_Keeper f where a.enterOrg=f.enterOrg and a.keepUnit=f.unitNo and a.keeper=f.keeperNo) as keeperName, "+		
					" (select f.keeperName from UNTMP_Keeper f where a.enterOrg=f.enterOrg and a.useUnit=f.unitNo and a.userNo=f.keeperNo) as userName, " +
					" b.deprMethod, a.scrapValue, a.deprAmount, a.apportionYear, a.monthDepr, b.useEndYM, b.apportionEndYM, " +
					" a.accumDeprYM, a.accumDepr, b.permitReduceDate, a.oldPropertyNo, a.oldSerialNo "+		
					" from UNTMP_MovableDetail a, UNTMP_Movable b, SYSCA_ORGAN c, "+
					" SYSPK_PropertyCode d " +
					" where 1=1 " +
					" and a.enterOrg=b.enterOrg and a.ownership=b.ownership and a.propertyNo=b.propertyNo and a.lotNo=b.lotNo "+
					" and a.enterOrg 	= " + Common.sqlChar(enterOrg) +
					" and a.ownership 	= " + Common.sqlChar(ownership) +
					" and b.caseNo 		= " + Common.sqlChar(Common.formatFrontZero(caseNo,10)) +
					" and a.propertyNo 	= " + Common.sqlChar(propertyNo) +
					" and a.serialNo 	= " + Common.sqlChar(serialNo) +
					" and a.enterOrg	= c.organID " +
					" and a.propertyNo 	= d.propertyNo "+
					" and d.enterOrg in('000000000A',a.enterOrg) and d.propertyType='1'" +
					" " ;
		//System.out.println(sql);
		ResultSet rs = db.querySQL(sql);
		if (rs.next()){
		    obj.setEnterOrg(rs.getString("enterOrg"));
		    obj.setEnterOrgName(rs.getString("enterOrgName"));
		    obj.setOwnership(rs.getString("ownership"));
			obj.setPropertyNo(rs.getString("propertyNo"));
			obj.setPropertyName(rs.getString("propertyName"));
			obj.setLotNo(rs.getString("lotNo"));
			obj.setSerialNo(rs.getString("serialNo"));
			obj.setMaterial(rs.getString("material"));
			obj.setOtherMaterial(rs.getString("otherMaterial"));
			obj.setPropertyUnit(rs.getString("propertyUnit"));
			obj.setOtherPropertyUnit(rs.getString("otherPropertyUnit"));
			obj.setLimitYear(rs.getString("limitYear"));
			obj.setOtherLimitYear(rs.getString("otherLimitYear"));
			obj.setPropertyName1(rs.getString("propertyName1"));
			obj.setEnterDate(rs.getString("enterDate"));
			obj.setBuyDate(rs.getString("buyDate"));
			obj.setPropertyKind(rs.getString("propertyKind"));
			obj.setFundType(rs.getString("fundType"));
			obj.setValuable(rs.getString("valuable"));
			obj.setAccountingTitle(rs.getString("accountingTitle"));
			obj.setBookAmount(rs.getString("bookAmount"));
			obj.setOriginalUnit(rs.getString("originalUnit"));
			obj.setBookValue(rs.getString("bookValue"));
			obj.setArticleName(rs.getString("articleName"));
			obj.setNameplate(rs.getString("nameplate"));
			obj.setSpecification(rs.getString("specification"));
			obj.setSourceDate(rs.getString("sourceDate"));
			obj.setLicensePlate(rs.getString("licensePlate"));
			obj.setMoveDate(rs.getString("moveDate"));
			obj.setKeepUnit(rs.getString("keepUnit"));
			obj.setKeepUnitName(rs.getString("keepUnitName"));
			obj.setKeeper(rs.getString("keeper"));
			obj.setKeeperName(rs.getString("keeperName"));
			obj.setUseUnit(rs.getString("useUnit"));
			obj.setUseUnitName(rs.getString("useUnitName"));
			obj.setUserNo(rs.getString("userNo"));
			obj.setUserName(rs.getString("userName"));
			obj.setPlace(rs.getString("place"));
			obj.setDeprMethod(rs.getString("deprMethod"));
			obj.setScrapValue(rs.getString("scrapValue"));
			obj.setDeprAmount(rs.getString("deprAmount"));
			obj.setApportionYear(rs.getString("apportionYear"));
			obj.setMonthDepr(rs.getString("monthDepr"));
			obj.setUseEndYM(rs.getString("useEndYM"));
			obj.setApportionEndYM(rs.getString("apportionEndYM"));
			obj.setAccumDeprYM(rs.getString("accumDeprYM"));
			obj.setAccumDepr(rs.getString("accumDepr"));
			obj.setPermitReduceDate(rs.getString("permitReduceDate"));
			obj.setOldPropertyNo(rs.getString("oldPropertyNo"));
			obj.setOldSerialNo(rs.getString("oldSerialNo"));
			obj.setOldPropertyName(rs.getString("oldPropertyNo"));
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
		String sql=" select distinct a.enterOrg, " +
				" a.ownership, " +
				" b.caseNo, " +
				" a.propertyNo, " +
				" a.serialNo," +
				" d.propertyName," 	+			
				" decode(b.propertyKind,'01','公務用','02','公共用','03','事業用','04','非公用','') propertyKind,"+
				" a.keepUnit , (select f.unitName from UNTMP_KeepUnit f where a.enterOrg=f.enterOrg and a.keepUnit=f.unitNo) as keepUnitName, "+
				" a.keeper, (select f.keeperName from UNTMP_Keeper f where a.enterOrg=f.enterOrg and a.keepUnit=f.unitNo and a.keeper=f.keeperNo) as keeperName, "+
				" nvl(a.place,' ') as place"+
				" from UNTMP_MovableDetail a, UNTMP_Movable b,SYSPK_PropertyCode d " +
				" where 1=1 and a.dataState='1' and a.verify='Y' " +
				" and a.enterOrg=b.enterOrg and a.ownership=b.ownership and a.propertyNo=b.propertyNo and a.lotNo=b.lotNo " +
				" and a.propertyNo = d.propertyNo "+
				" and d.enterOrg in('000000000A',a.enterOrg) and d.propertyType='1'" +
				" and a.enterOrg 	= " + Common.sqlChar(q_enterOrg) +
				" and a.ownership 	= " + Common.sqlChar(q_ownership) +
				" and (a.enterOrg , a.ownership , a.propertyNo , a.serialNo)  not in ( " +
				"  select enterOrg , ownership , propertyNo , serialNo from UNTMP_ReduceDetail where verify='N' " +
				"  ) " +
				" and (a.enterOrg , a.ownership , a.propertyNo , a.serialNo)  not in ( " +
				"  select enterOrg , ownership , propertyNo , serialNo from UNTMP_AdjustDetail  where verify='N' " +
				"  ) " +
				" and (a.enterOrg , a.ownership , a.propertyNo , a.serialNo)  not in ( " +
				"  select enterOrg , ownership , propertyNo , serialNo from UNTMP_moveDetail  where verify='N' " +
				"  ) " + 
				"";
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
			if (!"".equals(getQ_propertyKind()))
				sql+=" and b.propertyKind = " + Common.sqlChar(getQ_propertyKind()) ;
			if (!"".equals(getQ_fundType()))
				sql+=" and b.fundType = " + Common.sqlChar(getQ_fundType()) ;
			if (!"".equals(getQ_valuable()))
				sql+=" and b.valuable = " + Common.sqlChar(getQ_valuable()) ;
			if (!"".equals(getQ_permitReduceDateS()))
				sql+=" and b.permitReduceDate >= " + Common.sqlChar(getQ_permitReduceDateS()) ;
			if (!"".equals(getQ_permitReduceDateE()))
				sql+=" and b.permitReduceDate <= " + Common.sqlChar(getQ_permitReduceDateE()) ;
			if (!"".equals(getQ_buyDateS()))
				sql+=" and b.buyDate >= " + Common.sqlChar(getQ_buyDateS());
			if (!"".equals(getQ_buyDateE()))
				sql+=" and b.buyDate <= " + Common.sqlChar(getQ_buyDateE());
			if (!"".equals(getQ_keepUnit()))
				sql+=" and a.keepUnit = " + Common.sqlChar(getQ_keepUnit()) ;
			if (!"".equals(getQ_keeper()))
				sql+=" and a.keeper = " + Common.sqlChar(getQ_keeper()) ;
			sql+=" order by a.enterOrg, a.ownership, a.propertyNo, a.serialNo ";
		System.out.println(sql);
		ResultSet rs = db.querySQL(sql);
		//把查到的資料放入list裡..
		//和.jsp裡的 List區 內容要搭配起來
		while (rs.next()){
			counter++;
			String rowArray[]=new String[10];
			rowArray[0]=rs.getString("enterOrg");
			rowArray[1]=rs.getString("ownership");
			rowArray[2]=rs.getString("caseNo");
			rowArray[3]=rs.getString("propertyNo"); 
			rowArray[4]=rs.getString("serialNo"); 
			rowArray[5]=rs.getString("propertyName");
			rowArray[6]=rs.getString("propertyKind");
			rowArray[7]=rs.getString("keepUnitName");
			rowArray[8]=rs.getString("keeperName");
			rowArray[9]=rs.getString("place");
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

}
