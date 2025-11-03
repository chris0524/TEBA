/*
*<br>程式目的：土地改良物減少作業－批次新增明細資料（Copy From KFCP）
*<br>程式代號：untrf010f
*<br>程式日期：0990129
*<br>程式作者：Timtoy.Tsai
*<br>--------------------------------------------------------
*<br>修改作者　　修改日期　　　修改目的
*<br>--------------------------------------------------------
*/


package unt.rf;

import java.sql.ResultSet;
import java.util.ArrayList;
import util.*;

public class UNTRF010F extends SuperBean{

String enterOrg;
String enterOrgName;
String ownership;
String propertyNo;
String propertyName;
String serialNo;
String propertyName1;
String propertyKind;
String fundType;
String valuable;
String taxCredit;
String measure;
String accountingTitle;
String bookValue; 
String buildDate;
String sourceDate;
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

String q_enterOrg; 
String q_ownership; 
String q_caseNo;
String q_reduceDate;
String q_verify;

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
String q_propertyKind;
String q_fundType;
String q_valuable;
String q_taxCredit;
String q_permitReduceDateS;
String q_permitReduceDateE;

String sSQL = "";
String strKeySet[] = null;

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

public String getPropertyKind(){ return checkGet(propertyKind); }
public void setPropertyKind(String s){ propertyKind=checkSet(s); }
public String getFundType(){ return checkGet(fundType); }
public void setFundType(String s){ fundType=checkSet(s); }
public String getValuable(){ return checkGet(valuable); }
public void setValuable(String s){ valuable=checkSet(s); }
public String getTaxCredit(){ return checkGet(taxCredit); }
public void setTaxCredit(String s){ taxCredit=checkSet(s); }
public String getMeasure(){ return checkGet(measure); }
public void setMeasure(String s){ measure=checkSet(s); }
public String getAccountingTitle(){ return checkGet(accountingTitle); }
public void setAccountingTitle(String s){ accountingTitle=checkSet(s); }
public String getBookValue(){ return checkGet(bookValue); }
public void setBookValue(String s){ bookValue=checkSet(s); }
public String getBuildDate(){ return checkGet(buildDate); }
public void setBuildDate(String s){ buildDate=checkSet(s); }
public String getSourceDate(){ return checkGet(sourceDate); }
public void setSourceDate(String s){ sourceDate=checkSet(s); }
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
public String getQ_permitReduceDateS(){ return checkGet(q_permitReduceDateS); }
public void setQ_permitReduceDateS(String s){ q_permitReduceDateS=checkSet(s); }
public String getQ_permitReduceDateE(){ return checkGet(q_permitReduceDateE); }
public void setQ_permitReduceDateE(String s){ q_permitReduceDateE=checkSet(s); }

String toggleAll;
public String getToggleAll(){ if (!"".equals(checkGet(toggleAll))) return "checked"; else return "unchecked"; }    
public void setToggleAll(String s){ toggleAll=checkSet(s); }    

String verify;
String reduceDate;

public String getVerify(){ return checkGet(verify); }
public void setVerify(String s){ verify=checkSet(s); }
public String getReduceDate(){ return checkGet(reduceDate); }
public void setReduceDate(String s){ reduceDate=checkSet(s); }

String closing;
public String getClosing(){ return checkGet(closing); }
public void setClosing(String s){ closing=checkSet(s); }

String q_lawBasis;
public String getQ_lawBasis(){ return checkGet(q_lawBasis); }
public void setQ_lawBasis(String s){ q_lawBasis=checkSet(s); }

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
			String attachmentsql =   " select  " + 
									 " propertyNo, " +
									 " serialNo, " +
									 " propertyName1, " +
									 " propertyKind, " +
									 " fundType, " +
									 " valuable, " +
									 " taxCredit, " +
									 " measure,"+
									 " accountingTitle, " +
									 " bookValue, " +
									 " buildDate,"+
									 " sourceDate, "+
									 " deprMethod,"+
									 " deprAmount,"+
									 " monthDepr,"+
									 " apportionEndYM,"+
									 " accumDeprYM,"+
									 " accumDepr,"+
									 " permitReduceDate,"+
									 " oldPropertyNo, " +
									 " oldSerialNo " +
									 " from UNTRF_Attachment "+
									 " where enterOrg = " + Common.sqlChar(strKeys[0]) +
									 " and ownership = " + Common.sqlChar(strKeys[1]) +
									 " and propertyNo = " + Common.sqlChar(strKeys[2]) +
									 " and serialNo = " + Common.sqlChar(strKeys[3]) +
									 "";
			rs = db.querySQL(attachmentsql);
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

			    //需否呈報本府核定
			    String submitCityGov = "" ;
			    
			    if("01".equals(getQ_cause()) && "08".equals(getQ_cause()) && !"90".equals(getQ_cause())){
			    	if ( (Integer.parseInt(rs.getString("bookValue")) >= 2000000) || (Integer.parseInt(getEditDate()) < Integer.parseInt(rs.getString("permitReduceDate"))) ){	
			    		submitCityGov = "Y";
			    	}
			    }else{
			    	submitCityGov = "N";
			    }
			    
//			    if ( (Integer.parseInt(Common.get(rs.getString("bookValue"))) < 30000000) && ( Integer.parseInt(getEditDate()) >= Integer.parseInt(Common.get(rs.getString("permitReduceDate"))) ) && (!"01".equals(Common.get(q_cause)) && !"08".equals(Common.get(q_cause))) ) {
//			    	submitCityGov = "Y";
//			    }else if ( (Integer.parseInt(Common.get(rs.getString("bookValue"))) >= 30000000) || ( Integer.parseInt(getEditDate()) < Integer.parseInt(Common.get(rs.getString("permitReduceDate"))) && (!"01".equals(Common.get(q_cause)) && !"08".equals(Common.get(q_cause))) ) ) {
//			    	submitCityGov = "Y";
//			    } else {
//			        submitCityGov = "N";
//			    }
			    
			//insert 資料
			    sbSQL.append(" insert into UNTRF_ReduceDetail(" ).append( 
				  " enterOrg,").append(
				  " ownership,").append(
				  " caseNo,").append(
				  " propertyNo,").append(
				  " serialNo,").append(
				  " propertyName1,").append(
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
				  " measure,").append(
				  " accountingTitle,").append(
				  " bookValue,").append(
				  " buildDate,").append(
				  " sourceDate,").append(
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
					Common.sqlChar(caseNo)                      ).append( "," ).append(
					Common.sqlChar(rs.getString("propertyNo"))    ).append( "," ).append(
					Common.sqlChar(rs.getString("serialNo"))      ).append( "," ).append(
					Common.sqlChar(rs.getString("propertyName1")) ).append( "," ).append(
					Common.sqlChar(q_cause)                       ).append( "," ).append(
					Common.sqlChar(q_cause1)                      ).append( "," ).append(
					Common.sqlChar(q_lawBasis)                    ).append( "," ).append(
					Common.sqlChar(reduceDate)                  ).append( "," ).append(
					Common.sqlChar(q_newEnterOrg)                 ).append( "," ).append(
					Common.sqlChar(q_transferDate)                ).append( "," ).append(
					Common.sqlChar(verify)					  ).append( "," ).append(
					Common.sqlChar(rs.getString("propertyKind"))  ).append( "," ).append(
					Common.sqlChar(rs.getString("fundType"))      ).append( "," ).append(
					Common.sqlChar(rs.getString("valuable"))      ).append( "," ).append(
					Common.sqlChar(rs.getString("taxCredit"))     ).append( "," ).append(
					Common.sqlChar(rs.getString("measure"))     ).append( "," ).append(
					Common.sqlChar(rs.getString("accountingTitle")) ).append( "," ).append(
					Common.sqlChar(rs.getString("bookValue"))     ).append( "," ).append(
					Common.sqlChar(rs.getString("buildDate"))     ).append( "," ).append(
					Common.sqlChar(rs.getString("sourceDate"))    ).append( "," ).append(
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
					Common.sqlChar("N")   					  ).append( "," ).append(
					Common.sqlChar(getEditID())                   ).append( "," ).append(
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

public UNTRF010F  queryOne() throws Exception{
	Database db = new Database();
	UNTRF010F obj = this;
	try {
		String sql= " select a.enterOrg, a.ownership, a.caseNo, a.propertyNo, " +
					" a.serialNo, a.propertyName1, " +
					" a.propertyKind, a.fundType, " + 
					" a.valuable, a.taxCredit, a.measure," +
					" a.accountingTitle, a.bookValue, " +
					" a.buildDate,a.sourceDate, " +
					" a.oldPropertyNo, a.oldSerialNo, " +
					" c.organSName as enterOrgName, d.propertyName, e.propertyName as oldpropertyName, "+
					" a.deprMethod,a.deprAmount,a.monthDepr,a.apportionEndYM,a.accumDeprYM,a.accumDepr,a.permitReduceDate,"+
					" a.editID,a.editDate,a.editTime " +
					" from UNTRF_Attachment a, SYSCA_ORGAN c, "+
					" SYSPK_PropertyCode d, SYSPK_PropertyCode e where 1=1 " +
					" and a.enterOrg 	= " + Common.sqlChar(enterOrg) +
					" and a.ownership 	= " + Common.sqlChar(ownership) +
					" and a.propertyNo 	= " + Common.sqlChar(propertyNo) +
					" and a.serialNo 	= " + Common.sqlChar(serialNo) +
					" and a.enterOrg	= c.organID " +
					" and a.propertyNo 	= d.propertyNo "+
					" and a.oldpropertyNo = e.propertyNo(+) "+
					"";
		ResultSet rs = db.querySQL(sql);
		if (rs.next()){
			obj.setEnterOrg(rs.getString("enterOrg"));
			obj.setEnterOrgName(rs.getString("enterOrgName"));
			obj.setOwnership(rs.getString("ownership"));
			obj.setPropertyNo(rs.getString("propertyNo"));
			obj.setPropertyName(rs.getString("propertyName"));
			obj.setSerialNo(rs.getString("serialNo"));
			obj.setPropertyName1(rs.getString("propertyName1"));
			obj.setPropertyKind(rs.getString("propertyKind"));
			obj.setFundType(rs.getString("fundType"));
			obj.setValuable(rs.getString("valuable"));
			obj.setTaxCredit(rs.getString("taxCredit"));
			obj.setMeasure(rs.getString("measure"));
			obj.setAccountingTitle(rs.getString("accountingTitle"));
			obj.setBookValue(rs.getString("bookValue"));
			obj.setBuildDate(rs.getString("buildDate"));
			obj.setSourceDate(rs.getString("sourceDate"));
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
	int counter=0;
	try {
		String sql=" select a.enterOrg, d.propertyName,a.ownership, a.caseNo, a.propertyNo, " +
				" a.serialNo," +
				" decode(a.propertyKind,'01','公務用','02','公共用','03','事業用','04','非公用','') propertyKind,"+
				" a.bookValue, a.sourceDate " +
				" from UNTRF_Attachment a ,SYSCA_Organ c ,SYSPK_PropertyCode d " +
				" where 1=1 and a.dataState='1' and a.verify='Y' " +
				" and a.enterOrg = c.organID and a.propertyNo = d.propertyNo "+
				" and a.enterOrg 	= " + Common.sqlChar(q_enterOrg) +
				" and a.ownership 	= " + Common.sqlChar(q_ownership) +
				" and (a.enterOrg , a.ownership , a.propertyNo , a.serialNo)  not in ( " +
				"  select enterOrg , ownership , propertyNo , serialNo from UNTRF_ReduceDetail " +
				"  ) " +
				" and (a.enterOrg , a.ownership , a.propertyNo , a.serialNo)  not in ( " +
				"  select enterOrg , ownership , propertyNo , serialNo from UNTRF_AdjustDetail  where verify='N' " +
				"  ) " ;
		
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
		ResultSet rs = db.querySQL(sql);
		//把查到的資料放入list裡..
		//和.jsp裡的 List區 內容要搭配起來
		while (rs.next()){
			counter++;
			String rowArray[]=new String[6];
			rowArray[0]=rs.getString("enterOrg");
			rowArray[1]=rs.getString("ownership");
			rowArray[2]=rs.getString("propertyNo"); 
			rowArray[3]=rs.getString("serialNo"); 
			rowArray[4]=rs.getString("propertyName");
			rowArray[5]=rs.getString("bookValue"); 
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
