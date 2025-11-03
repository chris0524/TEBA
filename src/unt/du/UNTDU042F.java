/*
*<br>程式目的：土地改良物主檔-基本資料
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

public class UNTDU042F extends QueryBean{

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
String propertyNoName;
String otherLimitYear;
String propertyName1;
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
String originalMeasure;
String measure;
String originalBV;
String originalNote;
String accountingTitle;
String bookValue;
String fundsSource;
String buildDate;
String sourceKind;
String sourceDate;
String sourceDoc;
String useUnit;
String useUnitName;
String useUnit1;
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
String appraiseDate;
String notes;
String oldPropertyNo;
String oldSerialNo;
String propertyType;
String propertyUnit;
String material;
String limitYear;
String initDtl;
String fundsSource1;

public String getPropertyNoName(){ return checkGet(propertyNoName); }	
public void setPropertyNoName(String s){ propertyNoName=checkSet(s); }
public String getOtherLimitYear(){ return checkGet(otherLimitYear); }	
public void setOtherLimitYear(String s){ otherLimitYear=checkSet(s); }
public String getPropertyName1(){ return checkGet(propertyName1); }	
public void setPropertyName1(String s){ propertyName1=checkSet(s); }
public String getCause(){ return checkGet(cause); }	
public void setCause(String s){ cause=checkSet(s); }
public String getCause1(){ return checkGet(cause1); }	
public void setCause1(String s){ cause1=checkSet(s); }
public String getEnterDate(){ return checkGet(enterDate); }	
public void setEnterDate(String s){ enterDate=checkSet(s); }
public String getDataState(){ return checkGet(dataState); }
public void setDataState(String s){ dataState=checkSet(s); }
public String getReduceDate(){ return checkGet(reduceDate); }	
public void setReduceDate(String s){ reduceDate=checkSet(s); }
public String getReduceCause(){ return checkGet(reduceCause); }	
public void setReduceCause(String s){ reduceCause=checkSet(s); }
public String getReduceCause1(){ return checkGet(reduceCause1); }	
public void setReduceCause1(String s){ reduceCause1=checkSet(s); }
public String getVerify(){ return checkGet(verify); }	
public void setVerify(String s){ verify=checkSet(s); }
public String getClosing(){ return checkGet(closing); }	
public void setClosing(String s){ closing=checkSet(s); }
public String getPropertyKind(){ return checkGet(propertyKind); }	
public void setPropertyKind(String s){ propertyKind=checkSet(s); }
public String getFundType(){ return checkGet(fundType); }	
public void setFundType(String s){ fundType=checkSet(s); }
public String getValuable(){ return checkGet(valuable); }	
public void setValuable(String s){ valuable=checkSet(s); }
public String getTaxCredit(){ return checkGet(taxCredit); }	
public void setTaxCredit(String s){ taxCredit=checkSet(s); }
public String getOriginalMeasure(){ return checkGet(originalMeasure); }	
public void setOriginalMeasure(String s){ originalMeasure=checkSet(s); }
public String getMeasure(){ return checkGet(measure); }	
public void setMeasure(String s){ measure=checkSet(s); }
public String getOriginalBV(){ return checkGet(originalBV); }	
public void setOriginalBV(String s){ originalBV=checkSet(s); }
public String getOriginalNote(){ return checkGet(originalNote); }	
public void setOriginalNote(String s){ originalNote=checkSet(s); }
public String getAccountingTitle(){ return checkGet(accountingTitle); }	
public void setAccountingTitle(String s){ accountingTitle=checkSet(s); }
public String getBookValue(){ return checkGet(bookValue); }	
public void setBookValue(String s){ bookValue=checkSet(s); }
public String getFundsSource(){ return checkGet(fundsSource); }	
public void setFundsSource(String s){ fundsSource=checkSet(s); }
public String getBuildDate(){ return checkGet(buildDate); }	
public void setBuildDate(String s){ buildDate=checkSet(s); }
public String getSourceKind(){ return checkGet(sourceKind); }	
public void setSourceKind(String s){ sourceKind=checkSet(s); }
public String getSourceDate(){ return checkGet(sourceDate); }	
public void setSourceDate(String s){ sourceDate=checkSet(s); }
public String getSourceDoc(){ return checkGet(sourceDoc); }	
public void setSourceDoc(String s){ sourceDoc=checkSet(s); }
public String getUseUnit(){ return checkGet(useUnit); }	
public void setUseUnit(String s){ useUnit=checkSet(s); }
public String getUseUnitName(){ return checkGet(useUnitName); }	
public void setUseUnitName(String s){ useUnitName=checkSet(s); }
public String getUseUnit1(){ return checkGet(useUnit1); }	
public void setUseUnit1(String s){ useUnit1=checkSet(s); }
public String getDamageDate(){ return checkGet(damageDate); }	
public void setDamageDate(String s){ damageDate=checkSet(s); }
public String getDamageExpire(){ return checkGet(damageExpire); }	
public void setDamageExpire(String s){ damageExpire=checkSet(s); }
public String getDamageMark(){ return checkGet(damageMark); }	
public void setDamageMark(String s){ damageMark=checkSet(s); }
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
public String getIsAccumDepr(){ return checkGet(isAccumDepr); }	
public void setIsAccumDepr(String s){ isAccumDepr=checkSet(s); }
public String getAccumDepr(){ return checkGet(accumDepr); }	
public void setAccumDepr(String s){ accumDepr=checkSet(s); }
public String getPermitReduceDate(){ return checkGet(permitReduceDate); }	
public void setPermitReduceDate(String s){ permitReduceDate=checkSet(s); }
public String getAppraiseDate(){ return checkGet(appraiseDate); }	
public void setAppraiseDate(String s){ appraiseDate=checkSet(s); }
public String getNotes(){ return checkGet(notes); }	
public void setNotes(String s){ notes=checkSet(s); }
public String getOldPropertyNo(){ return checkGet(oldPropertyNo); }	
public void setOldPropertyNo(String s){ oldPropertyNo=checkSet(s); }
public String getOldSerialNo(){ return checkGet(oldSerialNo); }	
public void setOldSerialNo(String s){ oldSerialNo=checkSet(s); }
public String getPropertyType(){ return checkGet(propertyType); }	
public void setPropertyType(String s){ propertyType=checkSet(s); }
public String getPropertyUnit(){ return checkGet(propertyUnit); }	
public void setPropertyUnit(String s){ propertyUnit=checkSet(s); }
public String getMaterial(){ return checkGet(material); }	
public void setMaterial(String s){ material=checkSet(s); }
public String getLimitYear(){ return checkGet(limitYear); }	
public void setLimitYear(String s){ limitYear=checkSet(s); }
public String getInitDtl(){ return checkGet(initDtl); }	
public void setInitDtl(String s){ initDtl=checkSet(s); }
public String getFundsSource1(){ return checkGet(fundsSource1); }	
public void setFundsSource1(String s){ fundsSource1=checkSet(s); }


//傳回update sql
protected String[] getUpdateSQL(){
	String[] execSQLArray = new String[1];
	execSQLArray[0] = " update UNTRF_Attachment set "
					+ " otherLimitYear= " +  Common.sqlChar(otherLimitYear) + ","
					+ " propertyName1= " +  Common.sqlChar(propertyName1) + ","
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
					+ " originalMeasure= " +  Common.sqlChar(originalMeasure) + ","
					+ " measure= " +  Common.sqlChar(measure) + ","
					+ " originalBV= " +  Common.sqlChar(originalBV) + ","
					+ " originalNote= " +  Common.sqlChar(originalNote) + ","
					+ " accountingTitle= " +  Common.sqlChar(accountingTitle) + ","
					+ " bookValue= " +  Common.sqlChar(bookValue) + ","
					+ " fundsSource= " +  Common.sqlChar(fundsSource) + ","
					+ " buildDate= " +  Common.sqlChar(buildDate) + ","
					+ " sourceKind= " +  Common.sqlChar(sourceKind) + ","
					+ " sourceDate= " +  Common.sqlChar(sourceDate) + ","
					+ " sourceDoc= " +  Common.sqlChar(sourceDoc) + ","
					+ " useUnit= " +  Common.sqlChar(useUnit) + ","
					+ " useUnit1= " +  Common.sqlChar(useUnit1) + ","
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
					+ " appraiseDate= " +  Common.sqlChar(appraiseDate) + ","
					+ " notes= " +  Common.sqlChar(notes) + ","
					+ " oldPropertyNo= " +  Common.sqlChar(oldPropertyNo) + ","
					+ " oldSerialNo= " +  Common.sqlChar(oldSerialNo) + ","
					+ " fundsSource1= " +  Common.sqlChar(fundsSource1)
					+ " where 1=1 " 
			   		+ " and enterOrg = " + Common.sqlChar(enterOrg)
			   		+ " and ownership = " + Common.sqlChar(ownership)
			   		+ " and caseNo = " + Common.sqlChar(caseNo)
			   		+ " and propertyNo = " + Common.sqlChar(propertyNo)
			   		+ " and serialNo = " + Common.sqlChar(serialNo)
					+ "";

	return execSQLArray;
}

/**
 * <br>
 * <br>目的：依主鍵查詢單一資料
 * <br>參數：無
 * <br>傳回：傳回本物件
*/

public UNTDU042F queryOne() throws Exception{
	Database db = new Database();
	UNTDU042F obj = this;
	try {
		String sql = " select a.enterOrg ,o.organsname as enterOrgName ,a.ownership ,a.caseNo ,a.propertyNo ,a.serialNo " + "\n"
				   + "        ,p.propertyName as propertyNoName ,a.otherLimitYear ,a.propertyName1 ,a.cause ,a.cause1 " + "\n"
				   + "        ,a.enterDate ,a.dataState ,a.reduceDate ,a.reduceCause ,a.reduceCause1 " + "\n"
				   + "        ,a.verify ,a.closing ,a.propertyKind ,a.fundType ,a.valuable ,a.taxCredit " + "\n"
				   + "        ,a.originalMeasure ,a.measure ,a.originalBV ,a.originalNote " + "\n"
				   + "        ,a.accountingTitle ,a.bookValue ,a.fundsSource ,a.buildDate " + "\n"
				   + "        ,a.sourceKind ,a.sourceDate ,a.sourceDoc ,a.useUnit " + "\n"
				   + "        ,(select x.organsname from sysca_organ x where x.organid = a.useunit )as useUnitName " + "\n"
				   + "        ,a.useUnit1 ,a.damageDate ,a.damageExpire ,a.damageMark ,a.deprMethod " + "\n"
				   + "        ,a.scrapValue ,a.deprAmount ,a.apportionYear ,a.monthDepr " + "\n"
				   + "        ,a.useEndYM ,a.apportionEndYM ,a.accumDeprYM ,a.isAccumDepr ,a.accumDepr " + "\n"
				   + "        ,a.permitReduceDate ,a.appraiseDate ,a.notes ,a.oldPropertyNo ,a.oldSerialNo " + "\n"
				   + "        ,p.propertyType ,p.propertyUnit ,p.material ,p.limitYear  ,a.fundsSource1 " + "\n"
				   + " 		  ,a.editid ,a.editDate ,a.editTime " + "\n"
				   + " from UNTRF_Attachment a ,sysca_organ o ,syspk_propertycode p " + "\n"
				   + "        where 1=1 " + "\n"
				   + "        and a.enterorg=o.organid " + "\n"
				   + "        and a.propertyno = p.propertyno(+) " + "\n"
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
			
			obj.setPropertyNoName(rs.getString("propertyNoName"));
			obj.setOtherLimitYear(rs.getString("otherLimitYear"));
			obj.setPropertyName1(rs.getString("propertyName1"));
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
			obj.setOriginalMeasure(rs.getString("originalMeasure"));
			obj.setMeasure(rs.getString("measure"));
			obj.setOriginalBV(rs.getString("originalBV"));
			obj.setOriginalNote(rs.getString("originalNote"));
			obj.setAccountingTitle(rs.getString("accountingTitle"));
			obj.setBookValue(rs.getString("bookValue"));
			obj.setFundsSource(rs.getString("fundsSource"));
			obj.setBuildDate(rs.getString("buildDate"));
			obj.setSourceKind(rs.getString("sourceKind"));
			obj.setSourceDate(rs.getString("sourceDate"));
			obj.setSourceDoc(rs.getString("sourceDoc"));
			obj.setUseUnit(rs.getString("useUnit"));
			obj.setUseUnitName(rs.getString("useUnitName"));
			obj.setUseUnit1(rs.getString("useUnit1"));
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
			obj.setAppraiseDate(rs.getString("appraiseDate"));
			obj.setNotes(rs.getString("notes"));
			obj.setOldPropertyNo(rs.getString("oldPropertyNo"));
			obj.setOldSerialNo(rs.getString("oldSerialNo"));
			obj.setPropertyType(rs.getString("propertyType"));
			obj.setPropertyUnit(rs.getString("propertyUnit"));
			obj.setMaterial(rs.getString("material"));
			obj.setLimitYear(rs.getString("limitYear"));
			obj.setFundsSource1(rs.getString("fundsSource1"));

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
				   + " from UNTRF_Attachment a ,sysca_organ o " + "\n"
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
