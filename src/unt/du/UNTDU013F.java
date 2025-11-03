/*
*<br>程式目的：動產作業主檔資料修改
*<br>程式代號：untdu013f
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

public class UNTDU013F extends QueryBean{

//主ｋｅｙ
String enterOrg;
String enterOrgName;
String ownership;
String caseNo;
String propertyNo;
String lotNo;

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
String serialNoS;
String serialNoE;
String otherPropertyUnit;
String otherMaterial;
String otherLimitYear;
String propertyName1;
String cause;
String cause1;
String approveDate;
String approveDoc;
String enterDate;
String buyDate;
String dataState;
String verify;
String closing;
String propertyKind;
String fundType;
String valuable;
String originalAmount;
String originalUnit;
String originalBV;
String originalNote;
String accountingTitle;
String bookAmount;
String bookValue;
String fundsSource;
String grantValue;
String articleName;
String nameplate;
String specification;
String storeNo;
String sourceKind;
String sourceDate;
String sourceDoc;
String deprMethod;
String scrapValue;
String useEndYM;
String apportionEndYM;
String permitReduceDate;
String picture;
String notes;
String fundsSource1;
String computerType;

public String getSerialNoS(){ return checkGet(serialNoS); }	
public void setSerialNoS(String s){ serialNoS=checkSet(s); }
public String getSerialNoE(){ return checkGet(serialNoE); }	
public void setSerialNoE(String s){ serialNoE=checkSet(s); }
public String getOtherPropertyUnit(){ return checkGet(otherPropertyUnit); }	
public void setOtherPropertyUnit(String s){ otherPropertyUnit=checkSet(s); }
public String getOtherMaterial(){ return checkGet(otherMaterial); }	
public void setOtherMaterial(String s){ otherMaterial=checkSet(s); }
public String getOtherLimitYear(){ return checkGet(otherLimitYear); }	
public void setOtherLimitYear(String s){ otherLimitYear=checkSet(s); }
public String getPropertyName1(){ return checkGet(propertyName1); }	
public void setPropertyName1(String s){ propertyName1=checkSet(s); }
public String getCause(){ return checkGet(cause); }	
public void setCause(String s){ cause=checkSet(s); }
public String getCause1(){ return checkGet(cause1); }	
public void setCause1(String s){ cause1=checkSet(s); }
public String getApproveDate(){ return checkGet(approveDate); }	
public void setApproveDate(String s){ approveDate=checkSet(s); }
public String getApproveDoc(){ return checkGet(approveDoc); }	
public void setApproveDoc(String s){ approveDoc=checkSet(s); }
public String getEnterDate(){ return checkGet(enterDate); }	
public void setEnterDate(String s){ enterDate=checkSet(s); }
public String getBuyDate(){ return checkGet(buyDate); }	
public void setBuyDate(String s){ buyDate=checkSet(s); }
public String getDataState(){ return checkGet(dataState); }	
public void setDataState(String s){ dataState=checkSet(s); }
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
public String getOriginalAmount(){ return checkGet(originalAmount); }	
public void setOriginalAmount(String s){ originalAmount=checkSet(s); }
public String getOriginalUnit(){ return checkGet(originalUnit); }	
public void setOriginalUnit(String s){ originalUnit=checkSet(s); }
public String getOriginalBV(){ return checkGet(originalBV); }	
public void setOriginalBV(String s){ originalBV=checkSet(s); }
public String getOriginalNote(){ return checkGet(originalNote); }	
public void setOriginalNote(String s){ originalNote=checkSet(s); }
public String getAccountingTitle(){ return checkGet(accountingTitle); }	
public void setAccountingTitle(String s){ accountingTitle=checkSet(s); }
public String getBookAmount(){ return checkGet(bookAmount); }	
public void setBookAmount(String s){ bookAmount=checkSet(s); }
public String getBookValue(){ return checkGet(bookValue); }	
public void setBookValue(String s){ bookValue=checkSet(s); }
public String getFundsSource(){ return checkGet(fundsSource); }	
public void setFundsSource(String s){ fundsSource=checkSet(s); }
public String getGrantValue(){ return checkGet(grantValue); }	
public void setGrantValue(String s){ grantValue=checkSet(s); }
public String getArticleName(){ return checkGet(articleName); }	
public void setArticleName(String s){ articleName=checkSet(s); }
public String getNameplate(){ return checkGet(nameplate); }	
public void setNameplate(String s){ nameplate=checkSet(s); }
public String getSpecification(){ return checkGet(specification); }	
public void setSpecification(String s){ specification=checkSet(s); }
public String getStoreNo(){ return checkGet(storeNo); }	
public void setStoreNo(String s){ storeNo=checkSet(s); }
public String getSourceKind(){ return checkGet(sourceKind); }	
public void setSourceKind(String s){ sourceKind=checkSet(s); }
public String getSourceDate(){ return checkGet(sourceDate); }	
public void setSourceDate(String s){ sourceDate=checkSet(s); }
public String getSourceDoc(){ return checkGet(sourceDoc); }	
public void setSourceDoc(String s){ sourceDoc=checkSet(s); }
public String getDeprMethod(){ return checkGet(deprMethod); }	
public void setDeprMethod(String s){ deprMethod=checkSet(s); }
public String getScrapValue(){ return checkGet(scrapValue); }	
public void setScrapValue(String s){ scrapValue=checkSet(s); }
public String getUseEndYM(){ return checkGet(useEndYM); }	
public void setUseEndYM(String s){ useEndYM=checkSet(s); }
public String getApportionEndYM(){ return checkGet(apportionEndYM); }	
public void setApportionEndYM(String s){ apportionEndYM=checkSet(s); }
public String getPermitReduceDate(){ return checkGet(permitReduceDate); }	
public void setPermitReduceDate(String s){ permitReduceDate=checkSet(s); }
public String getPicture(){ return checkGet(picture); }	
public void setPicture(String s){ picture=checkSet(s); }
public String getNotes(){ return checkGet(notes); }	
public void setNotes(String s){ notes=checkSet(s); }
public String getFundsSource1(){ return checkGet(fundsSource1); }	
public void setFundsSource1(String s){ fundsSource1=checkSet(s); }
public String getComputerType(){ return checkGet(computerType); }	
public void setComputerType(String s){ computerType=checkSet(s); }


//傳回update sql
protected String[] getUpdateSQL(){
	String[] execSQLArray = new String[1];
	execSQLArray[0] = " update untmp_Movable set "
					+ " serialNoS= " +  Common.sqlChar(serialNoS) + ","
					+ " serialNoE= " +  Common.sqlChar(serialNoE) + ","
					+ " otherPropertyUnit= " +  Common.sqlChar(otherPropertyUnit) + ","
					+ " otherMaterial= " +  Common.sqlChar(otherMaterial) + ","
					+ " otherLimitYear= " +  Common.sqlChar(otherLimitYear) + ","
					+ " propertyName1= " +  Common.sqlChar(propertyName1) + ","
					+ " cause= " +  Common.sqlChar(cause) + ","
					+ " cause1= " +  Common.sqlChar(cause1) + ","
					+ " approveDate= " +  Common.sqlChar(approveDate) + ","
					+ " approveDoc= " +  Common.sqlChar(approveDoc) + ","
					+ " enterDate= " +  Common.sqlChar(enterDate) + ","
					+ " buyDate= " +  Common.sqlChar(buyDate) + ","
					+ " dataState= " +  Common.sqlChar(dataState) + ","
					+ " verify= " +  Common.sqlChar(verify) + ","
					+ " closing= " +  Common.sqlChar(closing) + ","
					+ " propertyKind= " +  Common.sqlChar(propertyKind) + ","
					+ " fundType= " +  Common.sqlChar(fundType) + ","
					+ " valuable= " +  Common.sqlChar(valuable) + ","
					+ " originalAmount= " +  Common.sqlChar(originalAmount) + ","
					+ " originalUnit= " +  Common.sqlChar(originalUnit) + ","
					+ " originalBV= " +  Common.sqlChar(originalBV) + ","
					+ " originalNote= " +  Common.sqlChar(originalNote) + ","
					+ " accountingTitle= " +  Common.sqlChar(accountingTitle) + ","
					+ " bookAmount= " +  Common.sqlChar(bookAmount) + ","
					+ " bookValue= " +  Common.sqlChar(bookValue) + ","
					+ " fundsSource= " +  Common.sqlChar(fundsSource) + ","
					+ " grantValue= " +  Common.sqlChar(grantValue) + ","
					+ " articleName= " +  Common.sqlChar(articleName) + ","
					+ " nameplate= " +  Common.sqlChar(nameplate) + ","
					+ " specification= " +  Common.sqlChar(specification) + ","
					//+ " storeNo= " +  Common.sqlChar(storeNo) + ","
					+ " sourceKind= " +  Common.sqlChar(sourceKind) + ","
					+ " sourceDate= " +  Common.sqlChar(sourceDate) + ","
					+ " sourceDoc= " +  Common.sqlChar(sourceDoc) + ","
					+ " deprMethod= " +  Common.sqlChar(deprMethod) + ","
					+ " scrapValue= " +  Common.sqlChar(scrapValue) + ","
					+ " useEndYM= " +  Common.sqlChar(useEndYM) + ","
					+ " apportionEndYM= " +  Common.sqlChar(apportionEndYM) + ","
					+ " permitReduceDate= " +  Common.sqlChar(permitReduceDate) + ","
					//+ " picture= " +  Common.sqlChar(picture) + ","
					+ " notes= " +  Common.sqlChar(notes) + ","
					+ " fundsSource1= " +  Common.sqlChar(fundsSource1)
					//+ " computerType= " +  Common.sqlChar(computerType) + ","
					+ " where 1=1 " 
			   		+ " and enterOrg = " + Common.sqlChar(enterOrg) 
			   		+ " and ownership = " + Common.sqlChar(ownership) 
			   		+ " and caseno = " + Common.sqlChar(caseNo)
			   		+ " and propertyNo = " + Common.sqlChar(propertyNo)
			   		+ " and lotNo = " + Common.sqlChar(lotNo)
					+ "";
	
	return execSQLArray;
}

/**
 * <br>
 * <br>目的：依主鍵查詢單一資料
 * <br>參數：無
 * <br>傳回：傳回本物件
*/

public UNTDU013F  queryOne() throws Exception{
	Database db = new Database();
	UNTDU013F obj = this;
	try {
		String sql = " select a.enterOrg ,o.organsname as enterOrgName ,a.ownership ,a.caseNo ,a.propertyNo ,a.lotNo " + "\n"
		   		   + " 		  ,a.serialNoS ,a.serialNoE ,a.otherPropertyUnit ,a.otherMaterial ,a.otherLimitYear " + "\n"
				   + " 		  ,a.propertyName1 ,a.cause ,a.cause1 ,a.approveDate ,a.approveDoc ,a.enterDate " + "\n"
				   + " 		  ,a.buyDate ,a.dataState ,a.verify ,a.closing ,a.propertyKind ,a.fundType ,a.valuable " + "\n"
				   + " 		  ,a.originalAmount ,a.originalUnit ,a.originalBV ,a.originalNote ,a.accountingTitle ,a.bookAmount " + "\n"
				   + " 		  ,a.bookValue ,a.fundsSource ,a.grantValue ,a.articleName ,a.nameplate " + "\n"
				   + " 		  ,a.specification ,a.storeNo ,a.sourceKind ,a.sourceDate ,a.sourceDoc ,a.deprMethod " + "\n"
				   + " 		  ,a.scrapValue ,a.useEndYM ,a.apportionEndYM ,a.permitReduceDate ,a.picture ,a.notes ,a.fundsSource1 " + "\n"
				   + " 		  ,a.computerType " + "\n"
				   + " 		  ,a.editid ,a.editDate ,a.editTime " + "\n"
		   		   + " from untmp_Movable a ,sysca_organ o " + "\n"
		   		   + " where 1=1 " + "\n"
		   		   + " and a.enterorg=o.organid " + "\n" 
		   		   + " and a.enterOrg = " + Common.sqlChar(enterOrg) 
		   		   + " and a.ownership = " + Common.sqlChar(ownership) 
		   		   + " and a.caseno = " + Common.sqlChar(caseNo)
		   		   + " and a.propertyNo = " + Common.sqlChar(propertyNo)
		   		   + " and a.lotNo = " + Common.sqlChar(lotNo);
		//System.out.println(sql);
		ResultSet rs = db.querySQL(sql);
		if (rs.next()){
			
			obj.setEnterOrg(rs.getString("enterOrg"));
			obj.setEnterOrgName(rs.getString("enterOrgName"));
			obj.setOwnership(rs.getString("ownership"));
			obj.setCaseNo(rs.getString("caseNo"));
			obj.setPropertyNo(rs.getString("propertyNo"));
			obj.setEditID(rs.getString("editID"));
			obj.setEditDate(rs.getString("editDate"));
			obj.setEditTime(rs.getString("editTime"));
			obj.setLotNo(rs.getString("lotNo"));
			
			obj.setSerialNoS(rs.getString("serialNoS"));
			obj.setSerialNoE(rs.getString("serialNoE"));
			obj.setOtherPropertyUnit(rs.getString("otherPropertyUnit"));
			obj.setOtherMaterial(rs.getString("otherMaterial"));
			obj.setOtherLimitYear(rs.getString("otherLimitYear"));
			obj.setPropertyName1(rs.getString("propertyName1"));
			obj.setCause(rs.getString("cause"));
			obj.setCause1(rs.getString("cause1"));
			obj.setApproveDate(rs.getString("approveDate"));
			obj.setApproveDoc(rs.getString("approveDoc"));
			obj.setEnterDate(rs.getString("enterDate"));
			obj.setBuyDate(rs.getString("buyDate"));
			obj.setDataState(rs.getString("dataState"));
			obj.setVerify(rs.getString("verify"));
			obj.setClosing(rs.getString("closing"));
			obj.setPropertyKind(rs.getString("propertyKind"));
			obj.setFundType(rs.getString("fundType"));
			obj.setValuable(rs.getString("valuable"));
			obj.setOriginalAmount(rs.getString("originalAmount"));
			obj.setOriginalUnit(rs.getString("originalUnit"));
			obj.setOriginalBV(rs.getString("originalBV"));
			obj.setOriginalNote(rs.getString("originalNote"));
			obj.setAccountingTitle(rs.getString("accountingTitle"));
			obj.setBookAmount(rs.getString("bookAmount"));
			obj.setBookValue(rs.getString("bookValue"));
			obj.setFundsSource(rs.getString("fundsSource"));
			obj.setGrantValue(rs.getString("grantValue"));
			obj.setArticleName(rs.getString("articleName"));
			obj.setNameplate(rs.getString("nameplate"));
			obj.setSpecification(rs.getString("specification"));
			obj.setStoreNo(rs.getString("storeNo"));
			obj.setSourceKind(rs.getString("sourceKind"));
			obj.setSourceDate(rs.getString("sourceDate"));
			obj.setSourceDoc(rs.getString("sourceDoc"));
			obj.setDeprMethod(rs.getString("deprMethod"));
			obj.setScrapValue(rs.getString("scrapValue"));
			obj.setUseEndYM(rs.getString("useEndYM"));
			obj.setApportionEndYM(rs.getString("apportionEndYM"));
			obj.setPermitReduceDate(rs.getString("permitReduceDate"));
			obj.setPicture(rs.getString("picture"));
			obj.setNotes(rs.getString("notes"));
			obj.setFundsSource1(rs.getString("fundsSource1"));
			obj.setComputerType(rs.getString("computerType"));

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
		String sql = " select a.enterOrg ,o.organsname as enterOrgName ,a.ownership ,a.caseNo ,a.propertyNo ,a.lotno " + "\n"
				   + "        ,a.serialnos ,a.serialnoe " + "\n"
				   + "		  ,(select x.codeName from sysca_code x where a.ownership = x.codeid and x.codekindid = 'OWA') as ownershipName"
				   + " from untmp_Movable a ,sysca_organ o " + "\n"
				   + " where 1=1 " + "\n"
				   + " and a.enterorg=o.organid " + "\n" ;
				   if (!"".equals(getQ_enterOrg()))
				   {	sql+=" and a.enterOrg = " + Common.sqlChar(getQ_enterOrg()) ;	}
				   if (!"".equals(getQ_ownership()))
				   {	sql+=" and a.ownership = " + Common.sqlChar(getQ_ownership()) ;	}
				   if (!"".equals(getQ_caseNo()))
				   {	sql+=" and a.caseNo = " + Common.sqlChar(getQ_caseNo()) ;		}
				   if (!"".equals(getQ_propertyNo()))
				   {	sql+=" and a.propertyNo = " + Common.sqlChar(getQ_propertyNo()) ;	}
				   if (!"".equals(getQ_lotNo()))
				   {	sql+=" and a.lotNo = " + Common.sqlChar(getQ_lotNo());			   }
				sql += " order by enterorg ,caseno ,propertyno ";
			
		//System.out.println(sql);
		ResultSet rs = db.querySQL(sql,true);
		processCurrentPageAttribute(rs);
		if (getTotalRecord() > 0) {
			int count = getRecordStart();
			int end = getRecordEnd();
			do {
				if (count > end) break;
			String rowArray[]=new String[9];
			rowArray[0]=Common.get(rs.getString("enterOrg")); 
			rowArray[1]=Common.get(rs.getString("enterOrgName")); 
			rowArray[2]=Common.get(rs.getString("ownership"));
			rowArray[3]=Common.get(rs.getString("ownershipName"));
			rowArray[4]=Common.get(rs.getString("caseNo")); 
			rowArray[5]=Common.get(rs.getString("propertyNo")); 
			rowArray[6]=Common.get(rs.getString("lotNo")); 
			rowArray[7]=Common.get(rs.getString("serialnos")); 
			rowArray[8]=Common.get(rs.getString("serialnoe"));
			
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


