/*
*<br>程式目的：土地增減值修改
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

public class UNTDU021F extends QueryBean{

//主ｋｅｙ
String enterOrg;
String enterOrgName;
String ownership;
String caseNo;
String propertyNo;
String propertyNoName;
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
public String getPropertyNoName(){ return checkGet(propertyNoName); }
public void setPropertyNoName(String s){ propertyNoName=checkSet(s); }
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
String cause;
String cause1;
String bulletinDate;
String adjustDate;
String verify;
String propertyKind;
String fundType;
String valuable;
String taxCredit;
String grass;
String originalBV;
String sourceDate;
String oldArea;
String oldHoldNume;
String oldHoldDeno;
String oldHoldArea;
String newArea;
String newHoldNume;
String newHoldDeno;
String newHoldArea;	
String adjustArea;
String adjustHoldArea;
String bookNotes;
String oldBookUnit;
String oldBookValue;
String newBookUnit;
String newBookValue;
String adjustBookUnit;
String adjustBookValue;
String changeItem;
String notes1;
String useSeparate;
String useKind;
String notes;
String oldPropertyNo;
String oldPropertyName;
String oldSerialNo;


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
public void setSignNo4(String s){ signNo4=checkSet(s); }
public String getSignNo5(){ return checkGet(signNo5); }
public void setSignNo5(String s){ signNo5=checkSet(s); }
public String getCause(){ return checkGet(cause); }
public void setCause(String s){ cause=checkSet(s); }
public String getCause1(){ return checkGet(cause1); }
public void setCause1(String s){ cause1=checkSet(s); }
public String getBulletinDate(){ return checkGet(bulletinDate); }
public void setBulletinDate(String s){ bulletinDate=checkSet(s); }
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
public String getGrass(){ return checkGet(grass); }
public void setGrass(String s){ grass=checkSet(s); }
public String getOriginalBV(){ return checkGet(originalBV); }
public void setOriginalBV(String s){ originalBV=checkSet(s); }
public String getSourceDate(){ return checkGet(sourceDate); }
public void setSourceDate(String s){ sourceDate=checkSet(s); }
public String getOldArea(){ return checkGet(oldArea); }
public void setOldArea(String s){ oldArea=checkSet(s); }
public String getOldHoldNume(){ return checkGet(oldHoldNume); }
public void setOldHoldNume(String s){ oldHoldNume=checkSet(s); }
public String getOldHoldDeno(){ return checkGet(oldHoldDeno); }
public void setOldHoldDeno(String s){ oldHoldDeno=checkSet(s); }
public String getOldHoldArea(){ return checkGet(oldHoldArea); }
public void setOldHoldArea(String s){ oldHoldArea=checkSet(s); }
public String getNewArea(){ return checkGet(newArea); }
public void setNewArea(String s){ newArea=checkSet(s); }
public String getNewHoldNume(){ return checkGet(newHoldNume); }
public void setNewHoldNume(String s){ newHoldNume=checkSet(s); }
public String getNewHoldDeno(){ return checkGet(newHoldDeno); }
public void setNewHoldDeno(String s){ newHoldDeno=checkSet(s); }
public String getNewHoldArea(){ return checkGet(newHoldArea); }
public void setNewHoldArea(String s){ newHoldArea=checkSet(s); }
public String getAdjustArea(){ return checkGet(adjustArea); }
public void setAdjustArea(String s){ adjustArea=checkSet(s); }
public String getAdjustHoldArea(){ return checkGet(adjustHoldArea); }
public void setAdjustHoldArea(String s){ adjustHoldArea=checkSet(s); }
public String getBookNotes(){ return checkGet(bookNotes); }
public void setBookNotes(String s){ bookNotes=checkSet(s); }
public String getOldBookUnit(){ return checkGet(oldBookUnit); }
public void setOldBookUnit(String s){ oldBookUnit=checkSet(s); }
public String getOldBookValue(){ return checkGet(oldBookValue); }
public void setOldBookValue(String s){ oldBookValue=checkSet(s); }
public String getNewBookUnit(){ return checkGet(newBookUnit); }
public void setNewBookUnit(String s){ newBookUnit=checkSet(s); }
public String getNewBookValue(){ return checkGet(newBookValue); }
public void setNewBookValue(String s){ newBookValue=checkSet(s); }
public String getAdjustBookUnit(){ return checkGet(adjustBookUnit); }
public void setAdjustBookUnit(String s){ adjustBookUnit=checkSet(s); }
public String getAdjustBookValue(){ return checkGet(adjustBookValue); }
public void setAdjustBookValue(String s){ adjustBookValue=checkSet(s); }
public String getChangeItem(){ return checkGet(changeItem); }
public void setChangeItem(String s){ changeItem=checkSet(s); }
public String getNotes1(){ return checkGet(notes1); }
public void setNotes1(String s){ notes1=checkSet(s); }
public String getUseSeparate(){ return checkGet(useSeparate); }
public void setUseSeparate(String s){ useSeparate=checkSet(s); }
public String getUseKind(){ return checkGet(useKind); }
public void setUseKind(String s){ useKind=checkSet(s); }
public String getNotes(){ return checkGet(notes); }
public void setNotes(String s){ notes=checkSet(s); }
public String getOldPropertyNo(){ return checkGet(oldPropertyNo); }
public void setOldPropertyNo(String s){ oldPropertyNo=checkSet(s); }
public String getOldPropertyName(){ return checkGet(oldPropertyName); }
public void setOldPropertyName(String s){ oldPropertyName=checkSet(s); }
public String getOldSerialNo(){ return checkGet(oldSerialNo); }
public void setOldSerialNo(String s){ oldSerialNo=checkSet(s); }

String closing;
public String getClosing(){ return checkGet(closing); }
public void setClosing(String s){ closing=checkSet(s); }
//傳回update sql
protected String[] getUpdateSQL(){
	String[] execSQLArray = new String[1];
	execSQLArray[0] = " update untla_AdjustDetail set "
				 	+ " propertyName1 = " + Common.sqlChar(propertyName1) + ","
				 	+ " signNo = "  + Common.sqlChar(signNo3+signNo4+signNo5) + ","
				 	+ " cause = " + Common.sqlChar(cause) + ","
				 	+ " cause1 = " + Common.sqlChar(cause1) + ","
				 	+ " bulletinDate = " + Common.sqlChar(bulletinDate) + ","
				 	+ " adjustDate = " + Common.sqlChar(adjustDate) + ","
				 	+ " verify = " + Common.sqlChar(verify) + ","
				 	+ " propertyKind = " + Common.sqlChar(propertyKind) + ","
				 	+ " fundType = " + Common.sqlChar(fundType) + ","
				 	+ " valuable = " + Common.sqlChar(valuable) + ","
				 	+ " taxCredit = " + Common.sqlChar(taxCredit) + ","
				 	+ " grass = " + Common.sqlChar(grass) + ","
				 	+ " originalBV = " + Common.sqlChar(originalBV) + ","
				 	+ " sourceDate = " + Common.sqlChar(sourceDate) + ","
				 	+ " oldArea = " + Common.sqlChar(oldArea) + ","
				 	+ " oldHoldNume = " + Common.sqlChar(oldHoldNume) + ","
				 	+ " oldHoldDeno = " + Common.sqlChar(oldHoldDeno) + ","
				 	+ " oldHoldArea = " + Common.sqlChar(oldHoldArea) + ","
				 	+ " newArea = " + Common.sqlChar(newArea) + ","
				 	+ " newHoldNume = " + Common.sqlChar(newHoldNume) + ","
				 	+ " newHoldDeno = " + Common.sqlChar(newHoldDeno) + ","
				 	+ " newHoldArea = " + Common.sqlChar(newHoldArea) + ","
				 	+ " adjustArea = " + Common.sqlChar(adjustArea) + ","
				 	+ " adjustHoldArea = " + Common.sqlChar(adjustHoldArea) + ","
				 	+ " bookNotes = " + Common.sqlChar(bookNotes) + ","
				 	+ " oldBookUnit = " + Common.sqlChar(oldBookUnit) + ","
				 	+ " oldBookValue = " + Common.sqlChar(oldBookValue) + ","
				 	+ " newBookUnit = " + Common.sqlChar(newBookUnit) + ","
				 	+ " newBookValue = " + Common.sqlChar(newBookValue) + ","
				 	+ " adjustBookUnit = " + Common.sqlChar(adjustBookUnit) + ","
				 	+ " adjustBookValue = " + Common.sqlChar(adjustBookValue) + ","
				 	+ " changeItem = " + Common.sqlChar(changeItem) + ","
				 	+ " notes1 = " + Common.sqlChar(notes1) + ","
				 	+ " useSeparate = " + Common.sqlChar(useSeparate) + ","
				 	+ " useKind = " + Common.sqlChar(useKind) + ","
				 	+ " notes = " + Common.sqlChar(notes) + ","
				 	+ " oldPropertyNo = " + Common.sqlChar(oldPropertyNo) + ","
				 	+ " oldSerialNo = " + Common.sqlChar(oldSerialNo) + ","
				 	+ " closing = " + Common.sqlChar(closing) + ","
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

public UNTDU021F queryOne() throws Exception{
	Database db = new Database();
	UNTDU021F obj = this;
	try {
		String sql = " select a.enterOrg ,o.organsname as enterOrgName ,a.ownership ,a.caseNo ,a.propertyNo ,a.serialNo " + "\n"
		   		   + "		  ,a.propertyName1 ,a.signNo ,a.cause ,a.cause1 ,a.bulletinDate " + "\n"
		   		   + "		  ,a.adjustDate ,a.verify ,a.propertyKind ,a.fundType ,a.valuable " + "\n"
		   		   + "		  ,a.taxCredit ,a.grass ,a.originalBV ,a.sourceDate ,a.oldArea " + "\n"
		   		   + "		  ,a.oldHoldNume ,a.oldHoldDeno ,a.oldHoldArea ,a.newArea ,a.newHoldNume " + "\n"
		   		   + "		  ,a.newHoldDeno ,a.newHoldArea ,a.adjustArea ,a.adjustHoldArea " + "\n"
		   		   + "		  ,a.bookNotes ,a.oldBookUnit ,a.oldBookValue ,a.newBookUnit ,a.newBookValue " + "\n"
		   		   + "		  ,a.adjustBookUnit ,a.adjustBookValue ,a.changeItem ,a.notes1 " + "\n"
		   		   + "		  ,a.useSeparate ,a.useKind ,a.notes ,a.oldPropertyNo ,a.oldSerialNo ,a.closing" + "\n"
				   + " 		  ,substring(a.signno ,1,1) || '000000' as signNo1 " + "\n"
				   + " 		  ,substring(a.signno ,1,3) || '0000' as signNo2 " + "\n"
				   + " 		  ,substring(a.signno ,1,7) as signNo3 " + "\n"
				   + " 		  ,substring(a.signno ,8,4) as signNo4 " + "\n"
				   + " 		  ,substring(a.signno ,12,4) as signNo5 " + "\n"
		   		   + " 		  ,a.editid ,a.editDate ,a.editTime " + "\n"
		   		   + " from untla_AdjustDetail a ,sysca_organ o " + "\n"
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
			obj.setCaseNo(rs.getString("caseNo"));
			obj.setPropertyNo(rs.getString("propertyNo"));
			obj.setSerialNo(rs.getString("serialNo"));
			
			/* 修改資料區 */
			obj.setPropertyName1(rs.getString("propertyName1"));
			//obj.setSignNo(rs.getString("signNo"));
			obj.setSignNo1(rs.getString("signNo1"));
			obj.setSignNo2(rs.getString("signNo2"));
			obj.setSignNo3(rs.getString("signNo3"));
			obj.setSignNo4(rs.getString("signNo4"));
			obj.setSignNo5(rs.getString("signNo5"));
			obj.setCause(rs.getString("cause"));
			obj.setCause1(rs.getString("cause1"));
			obj.setBulletinDate(rs.getString("bulletinDate"));
			obj.setAdjustDate(rs.getString("adjustDate"));
			obj.setVerify(rs.getString("verify"));
			obj.setPropertyKind(rs.getString("propertyKind"));
			obj.setFundType(rs.getString("fundType"));
			obj.setValuable(rs.getString("valuable"));
			obj.setTaxCredit(rs.getString("taxCredit"));
			obj.setGrass(rs.getString("grass"));
			obj.setOriginalBV(rs.getString("originalBV"));
			obj.setSourceDate(rs.getString("sourceDate"));
			obj.setOldArea(rs.getString("oldArea"));
			obj.setOldHoldNume(rs.getString("oldHoldNume"));
			obj.setOldHoldDeno(rs.getString("oldHoldDeno"));
			obj.setOldHoldArea(rs.getString("oldHoldArea"));
			obj.setNewArea(rs.getString("newArea"));
			obj.setNewHoldNume(rs.getString("newHoldNume"));
			obj.setNewHoldDeno(rs.getString("newHoldDeno"));
			obj.setNewHoldArea(rs.getString("newHoldArea"));
			obj.setAdjustArea(rs.getString("adjustArea"));
			obj.setAdjustHoldArea(rs.getString("adjustHoldArea"));
			obj.setBookNotes(rs.getString("bookNotes"));
			obj.setOldBookUnit(rs.getString("oldBookUnit"));
			obj.setOldBookValue(rs.getString("oldBookValue"));
			obj.setNewBookUnit(rs.getString("newBookUnit"));
			obj.setNewBookValue(rs.getString("newBookValue"));
			obj.setAdjustBookUnit(rs.getString("adjustBookUnit"));
			obj.setAdjustBookValue(rs.getString("adjustBookValue"));
			obj.setChangeItem(rs.getString("changeItem"));
			obj.setNotes1(rs.getString("notes1"));
			obj.setUseSeparate(rs.getString("useSeparate"));
			obj.setUseKind(rs.getString("useKind"));
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
		String sql = " select a.enterOrg ,o.organsname as enterOrgName ,a.ownership ,a.caseno " + "\n"
				   + "		  ,(select x.codeName from sysca_code x where a.ownership = x.codeid and x.codekindid = 'OWA') as ownershipName "
				   + " 		  ,a.propertyNo ,a.serialNo "
				   + " from untla_AdjustDetail a ,sysca_organ o " + "\n"
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
