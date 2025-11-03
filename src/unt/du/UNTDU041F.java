/*
*<br>程式目的：非消耗品減少作業-減損單明細資料
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

public class UNTDU041F extends QueryBean{

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
String otherMaterial;
String otherPropertyUnit;
String otherLimitYear;
String enterDate;
String buyDate;
String cause;
String cause1;
String reduceDate;
String newEnterOrg;
String transferDate;
String verify;
String propertyKind;
String fundType;
String valuable;
String bookNotes;
String accountingTitle;
String oldBookAmount;
String oldBookUnit;
String oldBookValue;
String adjustBookAmount;
String adjustBookValue;
String newBookAmount;
String newBookValue;
String articleName;
String nameplate;
String specification;
String licensePlate;
String moveDate;
String keepUnit;
String keeper;
String useUnit;
String userNo;
String place;
String returnPlace;
String cause2;
String dealCaseNo;
String dealDate;
String reduceDeal;
String realizeValue;
String shiftOrg;
String sourceDate;
String useYear;
String useMonth;
String permitReduceDate;
String submitCityGov;
String notes;
String closing;
String oldPropertyNo;
String oldSerialNo;
String scrapValue2;
String dealSuggestion;
public String getPropertyName1(){ return checkGet(propertyName1); }	
public void setPropertyName1(String s){ propertyName1=checkSet(s); }
public String getOtherMaterial(){ return checkGet(otherMaterial); }	
public void setOtherMaterial(String s){ otherMaterial=checkSet(s); }
public String getOtherPropertyUnit(){ return checkGet(otherPropertyUnit); }	
public void setOtherPropertyUnit(String s){ otherPropertyUnit=checkSet(s); }
public String getOtherLimitYear(){ return checkGet(otherLimitYear); }	
public void setOtherLimitYear(String s){ otherLimitYear=checkSet(s); }
public String getEnterDate(){ return checkGet(enterDate); }	
public void setEnterDate(String s){ enterDate=checkSet(s); }
public String getBuyDate(){ return checkGet(buyDate); }	
public void setBuyDate(String s){ buyDate=checkSet(s); }
public String getCause(){ return checkGet(cause); }	
public void setCause(String s){ cause=checkSet(s); }
public String getCause1(){ return checkGet(cause1); }	
public void setCause1(String s){ cause1=checkSet(s); }
public String getReduceDate(){ return checkGet(reduceDate); }	
public void setReduceDate(String s){ reduceDate=checkSet(s); }
public String getNewEnterOrg(){ return checkGet(newEnterOrg); }	
public void setNewEnterOrg(String s){ newEnterOrg=checkSet(s); }
public String getTransferDate(){ return checkGet(transferDate); }	
public void setTransferDate(String s){ transferDate=checkSet(s); }
public String getVerify(){ return checkGet(verify); }	
public void setVerify(String s){ verify=checkSet(s); }
public String getPropertyKind(){ return checkGet(propertyKind); }	
public void setPropertyKind(String s){ propertyKind=checkSet(s); }
public String getFundType(){ return checkGet(fundType); }	
public void setFundType(String s){ fundType=checkSet(s); }
public String getValuable(){ return checkGet(valuable); }	
public void setValuable(String s){ valuable=checkSet(s); }
public String getBookNotes(){ return checkGet(bookNotes); }	
public void setBookNotes(String s){ bookNotes=checkSet(s); }
public String getAccountingTitle(){ return checkGet(accountingTitle); }	
public void setAccountingTitle(String s){ accountingTitle=checkSet(s); }
public String getOldBookAmount(){ return checkGet(oldBookAmount); }	
public void setOldBookAmount(String s){ oldBookAmount=checkSet(s); }
public String getOldBookUnit(){ return checkGet(oldBookUnit); }	
public void setOldBookUnit(String s){ oldBookUnit=checkSet(s); }
public String getOldBookValue(){ return checkGet(oldBookValue); }	
public void setOldBookValue(String s){ oldBookValue=checkSet(s); }
public String getAdjustBookAmount(){ return checkGet(adjustBookAmount); }	
public void setAdjustBookAmount(String s){ adjustBookAmount=checkSet(s); }
public String getAdjustBookValue(){ return checkGet(adjustBookValue); }	
public void setAdjustBookValue(String s){ adjustBookValue=checkSet(s); }
public String getNewBookAmount(){ return checkGet(newBookAmount); }	
public void setNewBookAmount(String s){ newBookAmount=checkSet(s); }
public String getNewBookValue(){ return checkGet(newBookValue); }	
public void setNewBookValue(String s){ newBookValue=checkSet(s); }
public String getArticleName(){ return checkGet(articleName); }	
public void setArticleName(String s){ articleName=checkSet(s); }
public String getNameplate(){ return checkGet(nameplate); }	
public void setNameplate(String s){ nameplate=checkSet(s); }
public String getSpecification(){ return checkGet(specification); }	
public void setSpecification(String s){ specification=checkSet(s); }
public String getLicensePlate(){ return checkGet(licensePlate); }	
public void setLicensePlate(String s){ licensePlate=checkSet(s); }
public String getMoveDate(){ return checkGet(moveDate); }	
public void setMoveDate(String s){ moveDate=checkSet(s); }
public String getKeepUnit(){ return checkGet(keepUnit); }	
public void setKeepUnit(String s){ keepUnit=checkSet(s); }
public String getKeeper(){ return checkGet(keeper); }	
public void setKeeper(String s){ keeper=checkSet(s); }
public String getUseUnit(){ return checkGet(useUnit); }	
public void setUseUnit(String s){ useUnit=checkSet(s); }
public String getUserNo(){ return checkGet(userNo); }	
public void setUserNo(String s){ userNo=checkSet(s); }
public String getPlace(){ return checkGet(place); }	
public void setPlace(String s){ place=checkSet(s); }
public String getReturnPlace(){ return checkGet(returnPlace); }	
public void setReturnPlace(String s){ returnPlace=checkSet(s); }
public String getCause2(){ return checkGet(cause2); }	
public void setCause2(String s){ cause2=checkSet(s); }
public String getDealCaseNo(){ return checkGet(dealCaseNo); }	
public void setDealCaseNo(String s){ dealCaseNo=checkSet(s); }
public String getDealDate(){ return checkGet(dealDate); }	
public void setDealDate(String s){ dealDate=checkSet(s); }
public String getReduceDeal(){ return checkGet(reduceDeal); }	
public void setReduceDeal(String s){ reduceDeal=checkSet(s); }
public String getRealizeValue(){ return checkGet(realizeValue); }	
public void setRealizeValue(String s){ realizeValue=checkSet(s); }
public String getShiftOrg(){ return checkGet(shiftOrg); }	
public void setShiftOrg(String s){ shiftOrg=checkSet(s); }
public String getSourceDate(){ return checkGet(sourceDate); }	
public void setSourceDate(String s){ sourceDate=checkSet(s); }
public String getUseYear(){ return checkGet(useYear); }	
public void setUseYear(String s){ useYear=checkSet(s); }
public String getUseMonth(){ return checkGet(useMonth); }	
public void setUseMonth(String s){ useMonth=checkSet(s); }
public String getPermitReduceDate(){ return checkGet(permitReduceDate); }	
public void setPermitReduceDate(String s){ permitReduceDate=checkSet(s); }
public String getSubmitCityGov(){ return checkGet(submitCityGov); }	
public void setSubmitCityGov(String s){ submitCityGov=checkSet(s); }
public String getNotes(){ return checkGet(notes); }	
public void setNotes(String s){ notes=checkSet(s); }
public String getClosing(){ return checkGet(closing); }	
public void setClosing(String s){ closing=checkSet(s); }
public String getOldPropertyNo(){ return checkGet(oldPropertyNo); }
public void setOldPropertyNo(String s){ oldPropertyNo=checkSet(s); }
public String getOldSerialNo(){ return checkGet(oldSerialNo); }
public void setOldSerialNo(String s){ oldSerialNo=checkSet(s); }
public String getScrapValue2(){ return checkGet(scrapValue2); }	
public void setScrapValue2(String s){ scrapValue2=checkSet(s); }
public String getDealSuggestion(){ return checkGet(dealSuggestion); }	
public void setDealSuggestion(String s){ dealSuggestion=checkSet(s); }

//純顯示資料
String newEnterOrgName;
public String getNewEnterOrgName(){ return checkGet(newEnterOrgName); }	
public void setNewEnterOrgName(String s){ newEnterOrgName=checkSet(s); }
String shiftOrgName;
public String getShiftOrgName(){ return checkGet(shiftOrgName); }	
public void setShiftOrgName(String s){ shiftOrgName=checkSet(s); }

//傳回update sql
protected String[] getUpdateSQL(){
	String[] execSQLArray = new String[1];
	execSQLArray[0] = " update UNTNE_ReduceDetail set "
					+ " propertyName1= " +  Common.sqlChar(propertyName1) + ","
					+ " otherMaterial= " +  Common.sqlChar(otherMaterial) + ","
					+ " otherPropertyUnit= " +  Common.sqlChar(otherPropertyUnit) + ","
					+ " otherLimitYear= " +  Common.sqlChar(otherLimitYear) + ","
					+ " enterDate= " +  Common.sqlChar(enterDate) + ","
					+ " buyDate= " +  Common.sqlChar(buyDate) + ","
					+ " cause= " +  Common.sqlChar(cause) + ","
					+ " cause1= " +  Common.sqlChar(cause1) + ","
					+ " reduceDate= " +  Common.sqlChar(reduceDate) + ","
					+ " newEnterOrg= " +  Common.sqlChar(newEnterOrg) + ","
					+ " transferDate= " +  Common.sqlChar(transferDate) + ","
					+ " verify= " +  Common.sqlChar(verify) + ","
					+ " propertyKind= " +  Common.sqlChar(propertyKind) + ","
					+ " fundType= " +  Common.sqlChar(fundType) + ","
					+ " bookNotes= " +  Common.sqlChar(bookNotes) + ","
					+ " accountingTitle= " +  Common.sqlChar(accountingTitle) + ","
					+ " oldBookAmount= " +  Common.sqlChar(oldBookAmount) + ","
					+ " oldBookUnit= " +  Common.sqlChar(oldBookUnit) + ","
					+ " oldBookValue= " +  Common.sqlChar(oldBookValue) + ","
					+ " adjustBookAmount= " +  Common.sqlChar(adjustBookAmount) + ","
					+ " adjustBookValue= " +  Common.sqlChar(adjustBookValue) + ","
					+ " newBookAmount= " +  Common.sqlChar(newBookAmount) + ","
					+ " newBookValue= " +  Common.sqlChar(newBookValue) + ","
					+ " articleName= " +  Common.sqlChar(articleName) + ","
					+ " nameplate= " +  Common.sqlChar(nameplate) + ","
					+ " specification= " +  Common.sqlChar(specification) + ","
					+ " licensePlate= " +  Common.sqlChar(licensePlate) + ","
					+ " moveDate= " +  Common.sqlChar(moveDate) + ","
					//+ " keepUnit= " +  Common.sqlChar(keepUnit) + ","
					//+ " keeper= " +  Common.sqlChar(keeper) + ","
					//+ " useUnit= " +  Common.sqlChar(useUnit) + ","
					//+ " userNo= " +  Common.sqlChar(userNo) + ","
					+ " place= " +  Common.sqlChar(place) + ","
					+ " returnPlace= " +  Common.sqlChar(returnPlace) + ","
					+ " cause2= " +  Common.sqlChar(cause2) + ","
					+ " dealCaseNo= " +  Common.sqlChar(dealCaseNo) + ","
					+ " dealDate= " +  Common.sqlChar(dealDate) + ","
					+ " reduceDeal= " +  Common.sqlChar(reduceDeal) + ","
					+ " realizeValue= " +  Common.sqlChar(realizeValue) + ","
					+ " shiftOrg= " +  Common.sqlChar(shiftOrg) + ","
					+ " sourceDate= " +  Common.sqlChar(sourceDate) + ","
					+ " useYear= " +  Common.sqlChar(useYear) + ","
					+ " useMonth= " +  Common.sqlChar(useMonth) + ","
					+ " permitReduceDate= " +  Common.sqlChar(permitReduceDate) + ","
					+ " submitCityGov= " +  Common.sqlChar(submitCityGov) + ","
					+ " notes= " +  Common.sqlChar(notes) + ","
					+ " closing= " +  Common.sqlChar(closing) + ","
					+ " oldPropertyNo= " +  Common.sqlChar(oldPropertyNo) + ","
					+ " oldSerialNo= " +  Common.sqlChar(oldSerialNo) + ","
					+ " scrapValue2= " +  Common.sqlChar(scrapValue2) + ","
					+ " dealSuggestion= " +  Common.sqlChar(dealSuggestion) + ","
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

public UNTDU041F queryOne() throws Exception{
	Database db = new Database();
	UNTDU041F obj = this;
	try {
		String sql = " select a.enterOrg ,o.organsname as enterOrgName ,a.ownership ,a.caseNo ,a.propertyNo ,a.serialNo ,a.lotno " + "\n"
				   + " 		  ,propertyName1 ,otherMaterial ,otherPropertyUnit ,otherLimitYear " + "\n"
				   + " 		  ,enterDate ,buyDate ,cause ,cause1 ,reduceDate " + "\n"
				   + " 		  ,newEnterOrg ,transferDate ,verify ,propertyKind ,fundType " + "\n"
				   + " 		  ,valuable ,bookNotes ,accountingTitle ,oldBookAmount ,oldBookUnit " + "\n"
				   + " 		  ,oldBookValue ,adjustBookAmount ,adjustBookValue ,newBookAmount " + "\n"
				   + " 		  ,newBookValue ,articleName ,nameplate ,specification ,licensePlate " + "\n"
				   + " 		  ,moveDate ,keepUnit ,keeper ,useUnit ,userNo ,place ,returnPlace " + "\n"
				   + " 		  ,cause2 ,dealCaseNo ,dealDate ,reduceDeal ,realizeValue " + "\n"
				   + " 		  ,shiftOrg ,sourceDate ,useYear ,useMonth ,permitReduceDate " + "\n"
				   + " 		  ,submitCityGov ,notes ,closing ,oldPropertyNo ,oldSerialNo " + "\n"
				   + " 		  ,scrapValue2 ,dealSuggestion " + "\n"
				   + "		  ,(select x.organsname from sysca_organ x where x.organid = a.newenterorg ) as newenterorgName " + "\n"
				   + "		  ,(select x.organsname from sysca_organ x where x.organid = a.shiftOrg ) as shiftOrgName " + "\n"
		   		   + " 		  ,a.editid ,a.editDate ,a.editTime " + "\n"
		   		   + " from UNTNE_ReduceDetail a ,sysca_organ o " + "\n"
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
			obj.setLotNo(rs.getString("lotNo"));
			
			obj.setSerialNo(rs.getString("serialNo"));
			obj.setPropertyName1(rs.getString("propertyName1"));
			obj.setOtherMaterial(rs.getString("otherMaterial"));
			obj.setOtherPropertyUnit(rs.getString("otherPropertyUnit"));
			obj.setOtherLimitYear(rs.getString("otherLimitYear"));
			obj.setEnterDate(rs.getString("enterDate"));
			obj.setBuyDate(rs.getString("buyDate"));
			obj.setCause(rs.getString("cause"));
			obj.setCause1(rs.getString("cause1"));
			obj.setReduceDate(rs.getString("reduceDate"));
			obj.setNewEnterOrg(rs.getString("newEnterOrg"));
			obj.setTransferDate(rs.getString("transferDate"));
			obj.setVerify(rs.getString("verify"));
			obj.setPropertyKind(rs.getString("propertyKind"));
			obj.setFundType(rs.getString("fundType"));
			obj.setValuable(rs.getString("valuable"));
			obj.setBookNotes(rs.getString("bookNotes"));
			obj.setAccountingTitle(rs.getString("accountingTitle"));
			obj.setOldBookAmount(rs.getString("oldBookAmount"));
			obj.setOldBookUnit(rs.getString("oldBookUnit"));
			obj.setOldBookValue(rs.getString("oldBookValue"));
			obj.setAdjustBookAmount(rs.getString("adjustBookAmount"));
			obj.setAdjustBookValue(rs.getString("adjustBookValue"));
			obj.setNewBookAmount(rs.getString("newBookAmount"));
			obj.setNewBookValue(rs.getString("newBookValue"));
			obj.setArticleName(rs.getString("articleName"));
			obj.setNameplate(rs.getString("nameplate"));
			obj.setSpecification(rs.getString("specification"));
			obj.setLicensePlate(rs.getString("licensePlate"));
			obj.setMoveDate(rs.getString("moveDate"));
			obj.setKeepUnit(rs.getString("keepUnit"));
			obj.setKeeper(rs.getString("keeper"));
			obj.setUseUnit(rs.getString("useUnit"));
			obj.setUserNo(rs.getString("userNo"));
			obj.setPlace(rs.getString("place"));
			obj.setReturnPlace(rs.getString("returnPlace"));
			obj.setCause2(rs.getString("cause2"));
			obj.setDealCaseNo(rs.getString("dealCaseNo"));
			obj.setDealDate(rs.getString("dealDate"));
			obj.setReduceDeal(rs.getString("reduceDeal"));
			obj.setRealizeValue(rs.getString("realizeValue"));
			obj.setShiftOrg(rs.getString("shiftOrg"));
			obj.setSourceDate(rs.getString("sourceDate"));
			obj.setUseYear(rs.getString("useYear"));
			obj.setUseMonth(rs.getString("useMonth"));
			obj.setPermitReduceDate(rs.getString("permitReduceDate"));
			obj.setSubmitCityGov(rs.getString("submitCityGov"));
			obj.setNotes(rs.getString("notes"));
			obj.setClosing(rs.getString("closing"));
			obj.setOldPropertyNo(rs.getString("oldPropertyNo"));
			obj.setOldSerialNo(rs.getString("oldSerialNo"));
			obj.setScrapValue2(rs.getString("scrapValue2"));
			obj.setDealSuggestion(rs.getString("dealSuggestion"));
			
			obj.setNewEnterOrgName(rs.getString("newEnterOrgName"));
			obj.setShiftOrgName(rs.getString("shiftOrgName"));

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
		String sql = " select a.enterOrg ,o.organsname as enterOrgName ,a.ownership ,a.caseno ,a.propertyNo ,a.serialNo " + "\n"
				   + "		  ,(select x.codeName from sysca_code x where a.ownership = x.codeid and x.codekindid = 'OWA') as ownershipName "
						   + " from UNTNE_ReduceDetail a ,sysca_organ o " + "\n"
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
