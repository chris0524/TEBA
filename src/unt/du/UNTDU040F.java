/*
*<br>程式目的：土地改良物減少作業-減損單明細資料
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

public class UNTDU040F extends QueryBean{

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
String cause;
String cause1;
String reduceDate;
String newEnterOrg;
String transferDate;
String verify;
String propertyKind;
String fundType;
String valuable;
String taxCredit;
String measure;
String bookNotes;
String accountingTitle;
String bookValue;
String buildDate;
String sourceDate;
String useYear;
String useMonth;
String deprMethod;
String deprAmount;
String monthDepr;
String apportionEndYM;
String accumDeprYM;
String accumDepr;
String permitReduceDate;
String accumdepr1;
String scrapValue1;
String submitCityGov;
String reducedeal;
String notes;
String oldPropertyNo;
String oldSerialNo;
String closing;
public String getPropertyName1(){ return checkGet(propertyName1); }	public void setPropertyName1(String s){ propertyName1=checkSet(s); }
public String getCause(){ return checkGet(cause); }	public void setCause(String s){ cause=checkSet(s); }
public String getCause1(){ return checkGet(cause1); }	public void setCause1(String s){ cause1=checkSet(s); }
public String getReduceDate(){ return checkGet(reduceDate); }	public void setReduceDate(String s){ reduceDate=checkSet(s); }
public String getNewEnterOrg(){ return checkGet(newEnterOrg); }	public void setNewEnterOrg(String s){ newEnterOrg=checkSet(s); }
public String getTransferDate(){ return checkGet(transferDate); }	public void setTransferDate(String s){ transferDate=checkSet(s); }
public String getVerify(){ return checkGet(verify); }	public void setVerify(String s){ verify=checkSet(s); }
public String getPropertyKind(){ return checkGet(propertyKind); }	public void setPropertyKind(String s){ propertyKind=checkSet(s); }
public String getFundType(){ return checkGet(fundType); }	public void setFundType(String s){ fundType=checkSet(s); }
public String getValuable(){ return checkGet(valuable); }	public void setValuable(String s){ valuable=checkSet(s); }
public String getTaxCredit(){ return checkGet(taxCredit); }	public void setTaxCredit(String s){ taxCredit=checkSet(s); }
public String getMeasure(){ return checkGet(measure); }	public void setMeasure(String s){ measure=checkSet(s); }
public String getBookNotes(){ return checkGet(bookNotes); }	public void setBookNotes(String s){ bookNotes=checkSet(s); }
public String getAccountingTitle(){ return checkGet(accountingTitle); }	public void setAccountingTitle(String s){ accountingTitle=checkSet(s); }
public String getBookValue(){ return checkGet(bookValue); }	public void setBookValue(String s){ bookValue=checkSet(s); }
public String getBuildDate(){ return checkGet(buildDate); }	public void setBuildDate(String s){ buildDate=checkSet(s); }
public String getSourceDate(){ return checkGet(sourceDate); }	public void setSourceDate(String s){ sourceDate=checkSet(s); }
public String getUseYear(){ return checkGet(useYear); }	public void setUseYear(String s){ useYear=checkSet(s); }
public String getUseMonth(){ return checkGet(useMonth); }	public void setUseMonth(String s){ useMonth=checkSet(s); }
public String getDeprMethod(){ return checkGet(deprMethod); }	public void setDeprMethod(String s){ deprMethod=checkSet(s); }
public String getDeprAmount(){ return checkGet(deprAmount); }	public void setDeprAmount(String s){ deprAmount=checkSet(s); }
public String getMonthDepr(){ return checkGet(monthDepr); }	public void setMonthDepr(String s){ monthDepr=checkSet(s); }
public String getApportionEndYM(){ return checkGet(apportionEndYM); }	public void setApportionEndYM(String s){ apportionEndYM=checkSet(s); }
public String getAccumDeprYM(){ return checkGet(accumDeprYM); }	public void setAccumDeprYM(String s){ accumDeprYM=checkSet(s); }
public String getAccumDepr(){ return checkGet(accumDepr); }	public void setAccumDepr(String s){ accumDepr=checkSet(s); }
public String getPermitReduceDate(){ return checkGet(permitReduceDate); }	public void setPermitReduceDate(String s){ permitReduceDate=checkSet(s); }
public String getAccumdepr1(){ return checkGet(accumdepr1); }	public void setAccumdepr1(String s){ accumdepr1=checkSet(s); }
public String getScrapValue1(){ return checkGet(scrapValue1); }	public void setScrapValue1(String s){ scrapValue1=checkSet(s); }
public String getSubmitCityGov(){ return checkGet(submitCityGov); }	public void setSubmitCityGov(String s){ submitCityGov=checkSet(s); }
public String getReducedeal(){ return checkGet(reducedeal); }	public void setReducedeal(String s){ reducedeal=checkSet(s); }
public String getNotes(){ return checkGet(notes); }	public void setNotes(String s){ notes=checkSet(s); }
public String getOldPropertyNo(){ return checkGet(oldPropertyNo); }	public void setOldPropertyNo(String s){ oldPropertyNo=checkSet(s); }
public String getOldSerialNo(){ return checkGet(oldSerialNo); }	public void setOldSerialNo(String s){ oldSerialNo=checkSet(s); }
public String getClosing(){ return checkGet(closing); }	public void setClosing(String s){ closing=checkSet(s); }

String newEnterOrgName;
public String getNewEnterOrgName(){ return checkGet(newEnterOrgName); }	public void setNewEnterOrgName(String s){ newEnterOrg=checkSet(s); }

//傳回update sql
protected String[] getUpdateSQL(){
	String[] execSQLArray = new String[1];
	execSQLArray[0] = " update UNTRF_ReduceDetail set "
					+ " propertyName1= " +  Common.sqlChar(propertyName1) + ","
					+ " cause= " +  Common.sqlChar(cause) + ","
					+ " cause1= " +  Common.sqlChar(cause1) + ","
					+ " reduceDate= " +  Common.sqlChar(reduceDate) + ","
					+ " newEnterOrg= " +  Common.sqlChar(newEnterOrg) + ","
					+ " transferDate= " +  Common.sqlChar(transferDate) + ","
					+ " verify= " +  Common.sqlChar(verify) + ","
					+ " propertyKind= " +  Common.sqlChar(propertyKind) + ","
					+ " fundType= " +  Common.sqlChar(fundType) + ","
					+ " valuable= " +  Common.sqlChar(valuable) + ","
					+ " taxCredit= " +  Common.sqlChar(taxCredit) + ","
					+ " measure= " +  Common.sqlChar(measure) + ","
					+ " bookNotes= " +  Common.sqlChar(bookNotes) + ","
					+ " accountingTitle= " +  Common.sqlChar(accountingTitle) + ","
					+ " bookValue= " +  Common.sqlChar(bookValue) + ","
					+ " buildDate= " +  Common.sqlChar(buildDate) + ","
					+ " sourceDate= " +  Common.sqlChar(sourceDate) + ","
					+ " useYear= " +  Common.sqlChar(useYear) + ","
					+ " useMonth= " +  Common.sqlChar(useMonth) + ","
					+ " deprMethod= " +  Common.sqlChar(deprMethod) + ","
					+ " deprAmount= " +  Common.sqlChar(deprAmount) + ","
					+ " monthDepr= " +  Common.sqlChar(monthDepr) + ","
					+ " apportionEndYM= " +  Common.sqlChar(apportionEndYM) + ","
					+ " accumDeprYM= " +  Common.sqlChar(accumDeprYM) + ","
					+ " accumDepr= " +  Common.sqlChar(accumDepr) + ","
					+ " permitReduceDate= " +  Common.sqlChar(permitReduceDate) + ","
					+ " accumdepr1= " +  Common.sqlChar(accumdepr1) + ","
					+ " scrapValue1= " +  Common.sqlChar(scrapValue1) + ","
					+ " submitCityGov= " +  Common.sqlChar(submitCityGov) + ","
					+ " reducedeal= " +  Common.sqlChar(reducedeal) + ","
					+ " notes= " +  Common.sqlChar(notes) + ","
					+ " oldPropertyNo= " +  Common.sqlChar(oldPropertyNo) + ","
					+ " oldSerialNo= " +  Common.sqlChar(oldSerialNo) + ","
					+ " closing= " +  Common.sqlChar(closing) + ","
					+ " editID = " + Common.sqlChar(getEditID()) + ","
					+ " editDate = " + Common.sqlChar(getEditDate()) + ","
					+ " editTime = " + Common.sqlChar(getEditTime())
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

public UNTDU040F queryOne() throws Exception{
	Database db = new Database();
	UNTDU040F obj = this;
	try {
		String sql = " select a.enterOrg ,o.organsname as enterOrgName ,a.ownership ,a.caseNo ,a.propertyNo ,a.serialNo " + "\n"
				   + " 		  ,a.propertyName1 ,a.cause ,a.cause1 ,a.reduceDate ,a.newEnterOrg" + "\n"
				   + " 		  ,a.transferDate ,a.verify ,a.propertyKind ,a.fundType ,a.valuable" + "\n"
				   + " 		  ,a.taxCredit ,a.measure ,a.bookNotes ,a.accountingTitle ,a.bookValue" + "\n"
				   + " 		  ,a.buildDate ,a.sourceDate ,a.useYear ,a.useMonth ,a.deprMethod" + "\n"
				   + " 		  ,a.deprAmount ,a.monthDepr ,a.apportionEndYM ,a.accumDeprYM ,a.accumDepr" + "\n"
				   + " 		  ,a.permitReduceDate ,a.accumdepr1 ,a.scrapValue1 ,a.submitCityGov ,a.reducedeal" + "\n"
				   + " 		  ,a.notes ,a.oldPropertyNo ,a.oldSerialNo ,a.closing" + "\n"
				   + "		  ,( select x.organsname from sysca_organ x where 1=1 and x.organid = a.newEnterOrg ) as newEnterOrgName" + "\n"
		   		   + " 		  ,a.editid ,a.editDate ,a.editTime " + "\n"
		   		   + " from UNTRF_ReduceDetail a ,sysca_organ o " + "\n"
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
			obj.setCause(rs.getString("cause"));
			obj.setCause1(rs.getString("cause1"));
			obj.setReduceDate(rs.getString("reduceDate"));
			obj.setNewEnterOrg(rs.getString("newEnterOrg"));
			obj.setTransferDate(rs.getString("transferDate"));
			obj.setVerify(rs.getString("verify"));
			obj.setPropertyKind(rs.getString("propertyKind"));
			obj.setFundType(rs.getString("fundType"));
			obj.setValuable(rs.getString("valuable"));
			obj.setTaxCredit(rs.getString("taxCredit"));
			obj.setMeasure(rs.getString("measure"));
			obj.setBookNotes(rs.getString("bookNotes"));
			obj.setAccountingTitle(rs.getString("accountingTitle"));
			obj.setBookValue(rs.getString("bookValue"));
			obj.setBuildDate(rs.getString("buildDate"));
			obj.setSourceDate(rs.getString("sourceDate"));
			obj.setUseYear(rs.getString("useYear"));
			obj.setUseMonth(rs.getString("useMonth"));
			obj.setDeprMethod(rs.getString("deprMethod"));
			obj.setDeprAmount(rs.getString("deprAmount"));
			obj.setMonthDepr(rs.getString("monthDepr"));
			obj.setApportionEndYM(rs.getString("apportionEndYM"));
			obj.setAccumDeprYM(rs.getString("accumDeprYM"));
			obj.setAccumDepr(rs.getString("accumDepr"));
			obj.setPermitReduceDate(rs.getString("permitReduceDate"));
			obj.setAccumdepr1(rs.getString("accumdepr1"));
			obj.setScrapValue1(rs.getString("scrapValue1"));
			obj.setSubmitCityGov(rs.getString("submitCityGov"));
			obj.setReducedeal(rs.getString("reducedeal"));
			obj.setNotes(rs.getString("notes"));
			obj.setOldPropertyNo(rs.getString("oldPropertyNo"));
			obj.setOldSerialNo(rs.getString("oldSerialNo"));
			obj.setClosing(rs.getString("closing"));
			obj.setEditID(rs.getString("editID"));
			obj.setEditDate(rs.getString("editDate"));
			obj.setEditTime(rs.getString("editTime"));
			
			obj.setNewEnterOrgName(rs.getString("newEnterOrgName"));

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
		String sql = " select a.enterOrg ,o.organsname as enterOrgName ,a.ownership ,a.caseno ,a.propertyNo ,a.serialNo " + "\n"
				   + "		  ,(select x.codeName from sysca_code x where a.ownership = x.codeid and x.codekindid = 'OWA') as ownershipName "
				   + " from UNTRF_ReduceDetail a ,sysca_organ o " + "\n"
				   + " where 1=1 " + "\n"
				   + " and a.enterorg=o.organid " + "\n" ;
				   if (!"".equals(getQ_enterOrg()))
				   {	sql += " and a.enterOrg = " + Common.sqlChar(getQ_enterOrg()) ;	}
				   if (!"".equals(getQ_ownership()))
				   {	sql += " and a.ownership = " + Common.sqlChar(getQ_ownership()) ;	}
				   if (!"".equals(getQ_caseNo()))
				   {	sql+=" and a.caseNo = " + Common.sqlChar(getQ_caseNo()) ;		}
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
