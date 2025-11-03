/*
*<br>程式目的：非消耗品增減值作業-增減值單明細資料
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

public class UNTDU036F extends QueryBean{

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
String adjustDate;
String verify;
String propertyKind;
String fundType;
String valuable;
String taxCredit;
String originalBV;
String sourceDate;
String oldMeasure;
String oldBookValue;
String newMeasure;
String newBookValue;
String adjustType;
String adjustMeasure;
String adjustBookValue;
String bookNotes;
String notes;
String oldPropertyNo;
String oldSerialNo;
String closing;

public String getPropertyName1(){ return checkGet(propertyName1); }	
public void setPropertyName1(String s){ propertyName1=checkSet(s); }
public String getCause(){ return checkGet(cause); }	
public void setCause(String s){ cause=checkSet(s); }
public String getCause1(){ return checkGet(cause1); }	
public void setCause1(String s){ cause1=checkSet(s); }
public String getAdjustDate(){ return checkGet(adjustDate); }	
public void setAdjustDate(String s){ adjustDate=checkSet(s); }
public String getVerify(){ return checkGet(verify); }	
public void setVerify(String s){ verify=checkSet(s); }
public String getPropertyKind(){ return checkGet(propertyKind); }	
public void setPropertyKind(String s){ propertyKind=checkSet(s); }
public String getFundType(){ return checkGet(fundType); }	
public void setFundType(String s){ fundType=checkSet(s); }
public String getValuable(){ return checkGet(valuable); }	
public void setValuable(String s){ valuable=checkSet(s); }
public String getTaxCredit(){ return checkGet(taxCredit); }	
public void setTaxCredit(String s){ taxCredit=checkSet(s); }
public String getOriginalBV(){ return checkGet(originalBV); }	
public void setOriginalBV(String s){ originalBV=checkSet(s); }
public String getSourceDate(){ return checkGet(sourceDate); }	
public void setSourceDate(String s){ sourceDate=checkSet(s); }
public String getOldMeasure(){ return checkGet(oldMeasure); }	
public void setOldMeasure(String s){ oldMeasure=checkSet(s); }
public String getOldBookValue(){ return checkGet(oldBookValue); }	
public void setOldBookValue(String s){ oldBookValue=checkSet(s); }
public String getNewMeasure(){ return checkGet(newMeasure); }	
public void setNewMeasure(String s){ newMeasure=checkSet(s); }
public String getNewBookValue(){ return checkGet(newBookValue); }	
public void setNewBookValue(String s){ newBookValue=checkSet(s); }
public String getAdjustType(){ return checkGet(adjustType); }	
public void setAdjustType(String s){ adjustType=checkSet(s); }
public String getAdjustMeasure(){ return checkGet(adjustMeasure); }	
public void setAdjustMeasure(String s){ adjustMeasure=checkSet(s); }
public String getAdjustBookValue(){ return checkGet(adjustBookValue); }	
public void setAdjustBookValue(String s){ adjustBookValue=checkSet(s); }
public String getBookNotes(){ return checkGet(bookNotes); }	
public void setBookNotes(String s){ bookNotes=checkSet(s); }
public String getNotes(){ return checkGet(notes); }	
public void setNotes(String s){ notes=checkSet(s); }
public String getOldPropertyNo(){ return checkGet(oldPropertyNo); }	
public void setOldPropertyNo(String s){ oldPropertyNo=checkSet(s); }
public String getOldSerialNo(){ return checkGet(oldSerialNo); }	
public void setOldSerialNo(String s){ oldSerialNo=checkSet(s); }
public String getClosing(){ return checkGet(closing); }	
public void setClosing(String s){ closing=checkSet(s); }

//一般資訊欄位
String propertyUnit;
public String getPropertyUnit(){ return checkGet(propertyUnit); }
public void setPropertyUnit(String s){ propertyUnit=checkSet(s); }

//傳回update sql
protected String[] getUpdateSQL(){
	String[] execSQLArray = new String[1];
	
	execSQLArray[0] = " update UNTRF_AdjustDetail set "
					+ " propertyName1= " +  Common.sqlChar(propertyName1) + ","
					+ " cause= " +  Common.sqlChar(cause) + ","
					+ " cause1= " +  Common.sqlChar(cause1) + ","
					+ " adjustDate= " +  Common.sqlChar(adjustDate) + ","
					+ " verify= " +  Common.sqlChar(verify) + ","
					+ " propertyKind= " +  Common.sqlChar(propertyKind) + ","
					+ " fundType= " +  Common.sqlChar(fundType) + ","
					+ " valuable= " +  Common.sqlChar(valuable) + ","
					+ " taxCredit= " +  Common.sqlChar(taxCredit) + ","
					+ " originalBV= " +  Common.sqlChar(originalBV) + ","
					+ " sourceDate= " +  Common.sqlChar(sourceDate) + ","
					+ " oldMeasure= " +  Common.sqlChar(oldMeasure) + ","
					+ " oldBookValue= " +  Common.sqlChar(oldBookValue) + ","
					+ " newMeasure= " +  Common.sqlChar(newMeasure) + ","
					+ " newBookValue= " +  Common.sqlChar(newBookValue) + ","
					+ " adjustType= " +  Common.sqlChar(adjustType) + ","
					+ " adjustMeasure= " +  Common.sqlChar(adjustMeasure) + ","
					+ " adjustBookValue= " +  Common.sqlChar(adjustBookValue) + ","
					+ " bookNotes= " +  Common.sqlChar(bookNotes) + ","
					+ " notes= " +  Common.sqlChar(notes) + ","
					+ " oldPropertyNo= " +  Common.sqlChar(oldPropertyNo) + ","
					+ " oldSerialNo= " +  Common.sqlChar(oldSerialNo) + ","
					+ " closing= " +  Common.sqlChar(closing)
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

public UNTDU036F queryOne() throws Exception{
	Database db = new Database();
	UNTDU036F obj = this;
	try {
		String sql = " select a.enterOrg ,o.organsname as enterOrgName ,a.ownership ,a.caseNo ,a.propertyNo ,a.serialNo " + "\n"
		   		   + " 		  ,propertyName1 ,cause ,cause1 ,adjustDate ,verify " + "\n"
				   + " 		  ,propertyKind ,fundType ,valuable ,taxCredit " + "\n"
				   + " 		  ,originalBV ,sourceDate ,oldMeasure ,oldBookValue " + "\n"
				   + " 		  ,newMeasure ,newBookValue ,adjustType ,adjustMeasure " + "\n"
				   + " 		  ,adjustBookValue ,bookNotes ,notes ,oldPropertyNo " + "\n"
				   + " 		  ,oldSerialNo ,closing " + "\n"
				   + " 		  ,a.editid ,a.editDate ,a.editTime " + "\n"
		   		   + " from UNTRF_AdjustDetail a ,sysca_organ o " + "\n"
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
			obj.setEnterOrgName(rs.getString("enterOrgName"));
			
			obj.setPropertyName1(rs.getString("propertyName1"));
			obj.setCause(rs.getString("cause"));
			obj.setCause1(rs.getString("cause1"));
			obj.setAdjustDate(rs.getString("adjustDate"));
			obj.setVerify(rs.getString("verify"));
			obj.setPropertyKind(rs.getString("propertyKind"));
			obj.setFundType(rs.getString("fundType"));
			obj.setValuable(rs.getString("valuable"));
			obj.setTaxCredit(rs.getString("taxCredit"));
			obj.setOriginalBV(rs.getString("originalBV"));
			obj.setSourceDate(rs.getString("sourceDate"));
			obj.setOldMeasure(rs.getString("oldMeasure"));
			obj.setOldBookValue(rs.getString("oldBookValue"));
			obj.setNewMeasure(rs.getString("newMeasure"));
			obj.setNewBookValue(rs.getString("newBookValue"));
			obj.setAdjustType(rs.getString("adjustType"));
			obj.setAdjustMeasure(rs.getString("adjustMeasure"));
			obj.setAdjustBookValue(rs.getString("adjustBookValue"));
			obj.setBookNotes(rs.getString("bookNotes"));
			obj.setNotes(rs.getString("notes"));
			obj.setOldPropertyNo(rs.getString("oldPropertyNo"));
			obj.setOldSerialNo(rs.getString("oldSerialNo"));
			obj.setClosing(rs.getString("closing"));
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
				   + " from UNTRF_AdjustDetail a ,sysca_organ o " + "\n"
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
