/*
*<br>程式目的：非消耗品資料維護-明細資料
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

public class UNTDU044F extends QueryBean{

//主ｋｅｙ
String enterOrg;
String enterOrgName;
String ownership;
String propertyNo;
String lotNo;
String serialNo;

public String getEnterOrg(){ return checkGet(enterOrg); }
public void setEnterOrg(String s){ enterOrg=checkSet(s); }
public String getEnterOrgName(){ return checkGet(enterOrgName); }
public void setEnterOrgName(String s){ enterOrgName=checkSet(s); }
public String getOwnership(){ return checkGet(ownership); }
public void setOwnership(String s){ ownership=checkSet(s); }
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
String q_propertyNo;
String q_serialNo;
String q_lotNo;
String q_caseNo;
public String getQ_enterOrg(){ return checkGet(q_enterOrg); }
public void setQ_enterOrg(String s){ q_enterOrg=checkSet(s); }
public String getQ_enterOrgName(){ return checkGet(q_enterOrgName); }
public void setQ_enterOrgName(String s){ q_enterOrgName=checkSet(s); }
public String getQ_ownership(){ return checkGet(q_ownership); }
public void setQ_ownership(String s){ q_ownership=checkSet(s); }
public String getQ_propertyNo(){ return checkGet(q_propertyNo); }
public void setQ_propertyNo(String s){ q_propertyNo=checkSet(s); }
public String getQ_serialNo(){ return checkGet(q_serialNo); }
public void setQ_serialNo(String s){ q_serialNo=checkSet(s); }
public String getQ_lotNo(){ return checkGet(q_lotNo); }
public void setQ_lotNo(String s){ q_lotNo=checkSet(s); }
public String getQ_caseNo(){ return checkGet(q_caseNo); }
public void setQ_caseNo(String s){ q_caseNo=checkSet(s); }
String q_chengClass;
public String getQ_chengClass(){ return checkGet(q_chengClass); }
public void setQ_chengClass(String s){ q_chengClass=checkSet(s); }

//提供修改欄位

String reduceDate;
String reduceCause;
String reduceCause1;

String licensePlate;
String purpose;
String originalMoveDate;
String originalKeepUnit;
String originalKeeper;
String originalUseUnit;
String originalUser;
String originalPlace;
String moveDate;
String keepUnit;
String keeper;
String useUnit;
String userNo;
String place;
String damageDate;
String damageExpire;
String damageMark;
String notes1;
String notes;
String oldPropertyNo;
String oldSerialNo;
String editID;
String editDate;
String editTime;

String oddsCause;

public String getReduceDate(){ return checkGet(reduceDate); }	
public void setReduceDate(String s){ reduceDate=checkSet(s); }
public String getReduceCause(){ return checkGet(reduceCause); }	
public void setReduceCause(String s){ reduceCause=checkSet(s); }
public String getReduceCause1(){ return checkGet(reduceCause1); }	
public void setReduceCause1(String s){ reduceCause1=checkSet(s); }

public String getLicensePlate(){ return checkGet(licensePlate); }	
public void setLicensePlate(String s){ licensePlate=checkSet(s); }
public String getPurpose(){ return checkGet(purpose); }	
public void setPurpose(String s){ purpose=checkSet(s); }
public String getOriginalMoveDate(){ return checkGet(originalMoveDate); }	
public void setOriginalMoveDate(String s){ originalMoveDate=checkSet(s); }
public String getOriginalKeepUnit(){ return checkGet(originalKeepUnit); }	
public void setOriginalKeepUnit(String s){ originalKeepUnit=checkSet(s); }
public String getOriginalKeeper(){ return checkGet(originalKeeper); }	
public void setOriginalKeeper(String s){ originalKeeper=checkSet(s); }
public String getOriginalUseUnit(){ return checkGet(originalUseUnit); }	
public void setOriginalUseUnit(String s){ originalUseUnit=checkSet(s); }
public String getOriginalUser(){ return checkGet(originalUser); }	
public void setOriginalUser(String s){ originalUser=checkSet(s); }
public String getOriginalPlace(){ return checkGet(originalPlace); }
public void setOriginalPlace(String s){ originalPlace=checkSet(s); }
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
public String getDamageDate(){ return checkGet(damageDate); }	
public void setDamageDate(String s){ damageDate=checkSet(s); }
public String getDamageExpire(){ return checkGet(damageExpire); }	
public void setDamageExpire(String s){ damageExpire=checkSet(s); }
public String getDamageMark(){ return checkGet(damageMark); }	
public void setDamageMark(String s){ damageMark=checkSet(s); }
public String getNotes1(){ return checkGet(notes1); }	
public void setNotes1(String s){ notes1=checkSet(s); }
public String getNotes(){ return checkGet(notes); }	
public void setNotes(String s){ notes=checkSet(s); }
public String getOldPropertyNo(){ return checkGet(oldPropertyNo); }	
public void setOldPropertyNo(String s){ oldPropertyNo=checkSet(s); }
public String getOldSerialNo(){ return checkGet(oldSerialNo); }	
public void setOldSerialNo(String s){ oldSerialNo=checkSet(s); }
public String getOddsCause(){ return checkGet(oddsCause); }	
public void setOddsCause(String s){ oddsCause=checkSet(s); }

String dataState;
String verify;
String closing;

public String getDataState(){ return checkGet(dataState); }	
public void setDataState(String s){ dataState=checkSet(s); }
public String getVerify(){ return checkGet(verify); }	
public void setVerify(String s){ verify=checkSet(s); }
public String getClosing(){ return checkGet(closing); }	
public void setClosing(String s){ closing=checkSet(s); }

String originalAmount;
String originalBV;
public String getOriginalAmount(){ return checkGet(originalAmount); }	
public void setOriginalAmount(String s){ originalAmount=checkSet(s); }
public String getOriginalBV(){ return checkGet(originalBV); }	
public void setOriginalBV(String s){ originalBV=checkSet(s); }

String bookAmount;
String bookValue;
public String getBookAmount(){ return checkGet(bookAmount); }	
public void setBookAmount(String s){ bookAmount=checkSet(s); }
public String getBookValue(){ return checkGet(bookValue); }	
public void setBookValue(String s){ bookValue=checkSet(s); }

String barCode;
String checkDateS;
String checkDateE;
String checkResult;
public String getBarCode(){ return checkGet(barCode); }	
public void setBarCode(String s){ barCode=checkSet(s); }
public String getCheckDateS(){ return checkGet(checkDateS); }	
public void setCheckDateS(String s){ checkDateS=checkSet(s); }
public String getCheckDateE(){ return checkGet(checkDateE); }	
public void setCheckDateE(String s){ checkDateE=checkSet(s); }
public String getCheckResult(){ return checkGet(checkResult); }	
public void setCheckResult(String s){ checkResult=checkSet(s); }
//傳回update sql
protected String[] getUpdateSQL(){
	String[] execSQLArray = new String[1];
	execSQLArray[0] = " update UNTNE_NonexpDetail set "
					+ " dataState= " +  Common.sqlChar(dataState) + ","
					+ " verify= " +  Common.sqlChar(verify) + ","
					+ " closing= " +  Common.sqlChar(closing) + ","
					+ " oldPropertyNo= " +  Common.sqlChar(oldPropertyNo) + ","
					+ " oldSerialNo= " +  Common.sqlChar(oldSerialNo) + ","
					+ " licensePlate= " +  Common.sqlChar(licensePlate) + ","
					+ " purpose= " +  Common.sqlChar(purpose) + ","
					+ " originalAmount= " +  Common.sqlChar(originalAmount) + ","
					+ " originalBV= " +  Common.sqlChar(originalBV) + ","
					+ " bookAmount= " +  Common.sqlChar(bookAmount) + ","
					+ " bookValue= " +  Common.sqlChar(bookValue) + ","
					+ " barCode= " +  Common.sqlChar(barCode) + ","
					+ " checkResult= " +  Common.sqlChar(checkResult) + ","
					+ " oddsCause= " +  Common.sqlChar(oddsCause) + ","
					+ " checkDateS= " +  Common.sqlChar(checkDateS) + ","
					+ " checkDateE= " +  Common.sqlChar(checkDateE) + ","
					+ " damageDate= " +  Common.sqlChar(damageDate) + ","
					+ " reduceDate= " +  Common.sqlChar(reduceDate) + ","
					+ " reduceCause= " +  Common.sqlChar(reduceCause) + ","
					+ " reduceCause1= " +  Common.sqlChar(reduceCause1) + ","
					
					+ " originalMoveDate= " +  Common.sqlChar(originalMoveDate) + ","
					+ " originalKeepUnit= " +  Common.sqlChar(originalKeepUnit) + ","
					+ " originalKeeper= " +  Common.sqlChar(originalKeeper) + ","
					+ " originalUseUnit= " +  Common.sqlChar(originalUseUnit) + ","
					+ " originalUser= " +  Common.sqlChar(originalUser) + ","
					+ " originalPlace= " +  Common.sqlChar(originalPlace) + ","
					+ " moveDate= " +  Common.sqlChar(moveDate) + ","
					+ " keepUnit= " +  Common.sqlChar(keepUnit) + ","
					+ " keeper= " +  Common.sqlChar(keeper) + ","
					+ " useUnit= " +  Common.sqlChar(useUnit) + ","
					+ " userNo= " +  Common.sqlChar(userNo) + ","
					+ " place= " +  Common.sqlChar(place) + ","
							
					+ " notes1= " +  Common.sqlChar(notes1)
					+ " where 1=1 " 
			   		+ " and enterOrg = " + Common.sqlChar(enterOrg) 
			   		+ " and ownership = " + Common.sqlChar(ownership) 
			   		+ " and propertyno = " + Common.sqlChar(propertyNo)
			   		+ " and lotNo = " + Common.sqlChar(lotNo)
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

public UNTDU044F  queryOne() throws Exception{
	Database db = new Database();
	UNTDU044F obj = this;
	try {
		String sql = " select a.enterOrg ,o.organsname as enterOrgName ,a.ownership ,a.propertyNo ,a.lotNo ,a.serialno " + "\n"
		   		   + "        ,a.dataState ,a.reduceDate ,a.reduceCause ,a.reduceCause1 ,a.verify " + "\n"
		   		   + "		  ,a.originalAmount ,a.originalBV "
		   		   + "        ,a.closing ,a.bookAmount ,a.bookValue ,a.licensePlate ,a.purpose " + "\n"
		   		   + "        ,a.originalMoveDate ,a.originalKeepUnit ,a.originalKeeper ,a.originalUseUnit " + "\n"
		   		   + "        ,a.originalUser ,a.originalPlace ,a.moveDate ,a.keepUnit ,a.keeper " + "\n"
		   		   + "        ,a.useUnit ,a.userNo ,a.place ,a.damageDate ,a.damageExpire " + "\n"
		   		   + "        ,a.damageMark ,a.notes1 ,a.notes ,a.oldPropertyNo ,a.oldSerialNo " + "\n"
		   		   + "        ,a.editID ,a.editDate ,a.editTime ,a.barCode ,a.checkDateS ,a.checkDateE " + "\n"
		   		   + "        ,a.checkResult ,a.oddsCause " + "\n"
		   		   + " 		  ,a.editid ,a.editDate ,a.editTime " + "\n"
		   		   + " from UNTNE_NonexpDetail a ,sysca_organ o " + "\n"
		   		   + " where 1=1 " + "\n"
		   		   + " and a.enterorg=o.organid " + "\n" 
		   		   + " and a.enterOrg = " + Common.sqlChar(enterOrg) 
		   		   + " and a.ownership = " + Common.sqlChar(ownership) 
		   		   + " and a.propertyno = " + Common.sqlChar(propertyNo)
		   		   + " and a.lotNo = " + Common.sqlChar(lotNo)
		   		   + " and a.serialNo = " + Common.sqlChar(serialNo);

		//System.out.println(sql);
		ResultSet rs = db.querySQL(sql);
		if (rs.next()){

			obj.setEnterOrg(rs.getString("enterOrg"));
			obj.setEnterOrgName(rs.getString("enterOrgName"));
			obj.setOwnership(rs.getString("ownership"));
			obj.setPropertyNo(rs.getString("propertyNo"));
			obj.setLotNo(rs.getString("lotNo"));
			obj.setSerialNo(rs.getString("serialNo"));
			
			obj.setDataState(rs.getString("dataState"));
			obj.setReduceDate(rs.getString("reduceDate"));
			obj.setReduceCause(rs.getString("reduceCause"));
			obj.setReduceCause1(rs.getString("reduceCause1"));
			obj.setVerify(rs.getString("verify"));
			obj.setClosing(rs.getString("closing"));
			obj.setBookAmount(rs.getString("bookAmount"));
			obj.setBookValue(rs.getString("bookValue"));
			obj.setLicensePlate(rs.getString("licensePlate"));
			obj.setPurpose(rs.getString("purpose"));
			obj.setOriginalMoveDate(rs.getString("originalMoveDate"));
			obj.setOriginalKeepUnit(rs.getString("originalKeepUnit"));
			obj.setOriginalKeeper(rs.getString("originalKeeper"));
			obj.setOriginalUseUnit(rs.getString("originalUseUnit"));
			obj.setOriginalUser(rs.getString("originalUser"));
			obj.setOriginalPlace(rs.getString("originalPlace"));
			obj.setMoveDate(rs.getString("moveDate"));
			obj.setKeepUnit(rs.getString("keepUnit"));
			obj.setKeeper(rs.getString("keeper"));
			obj.setUseUnit(rs.getString("useUnit"));
			obj.setUserNo(rs.getString("userNo"));
			obj.setPlace(rs.getString("place"));
			obj.setDamageDate(rs.getString("damageDate"));
			obj.setDamageExpire(rs.getString("damageExpire"));
			obj.setDamageMark(rs.getString("damageMark"));
			obj.setNotes1(rs.getString("notes1"));
			obj.setNotes(rs.getString("notes"));
			obj.setOldPropertyNo(rs.getString("oldPropertyNo"));
			obj.setOldSerialNo(rs.getString("oldSerialNo"));
			obj.setEditID(rs.getString("editID"));
			obj.setEditDate(rs.getString("editDate"));
			obj.setEditTime(rs.getString("editTime"));
			obj.setBarCode(rs.getString("barCode"));
			obj.setCheckDateS(rs.getString("checkDateS"));
			obj.setCheckDateE(rs.getString("checkDateE"));
			obj.setCheckResult(rs.getString("checkResult"));
			obj.setOddsCause(rs.getString("oddsCause"));
			obj.setOriginalAmount(rs.getString("originalAmount"));
			obj.setOriginalBV(rs.getString("originalBV"));

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
		String sql = " select a.enterOrg ,o.organsname as enterOrgName ,a.ownership ,a.propertyNo ,a.lotno ,a.serialno " + "\n"
				   + "		  ,(select x.codeName from sysca_code x where a.ownership = x.codeid and x.codekindid = 'OWA') as ownershipName "
				   + " from  UNTNE_NonexpDetail a ,sysca_organ o " + "\n"
				   + " where 1=1 " + "\n"
				   + " and a.enterorg=o.organid " + "\n" ;
				   if (!"".equals(getQ_enterOrg()))
				   {	sql += " and a.enterOrg = " + Common.sqlChar(getQ_enterOrg()) ;	}
				   if (!"".equals(getQ_ownership()))
				   {	sql += " and a.ownership = " + Common.sqlChar(getQ_ownership()) ;	}
				   if (!"".equals(getQ_propertyNo()))
				   {	sql += " and a.propertyNo = " + Common.sqlChar(getQ_propertyNo()) ;	}
				   if (!"".equals(getQ_serialNo()))
				   {	sql += " and a.serialNo = " + Common.sqlChar(getQ_serialNo());	}

				sql += " order by enterorg ,ownership ,propertyno ,serialno";
			
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
			rowArray[4]=Common.get(rs.getString("propertyNo")); 
			rowArray[5]=Common.get(rs.getString("lotNo")); 
			rowArray[6]=Common.get(rs.getString("serialno")); 
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
