/*
*<br>程式目的：
*<br>程式代號：
*<br>程式日期：
*<br>程式作者：
*<br>--------------------------------------------------------
*<br>修改作者　　修改日期　　　修改目的
*<br>--------------------------------------------------------
*/

package unt.ch;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import TDlib_Simple.com.src.DBServerTools;
import TDlib_Simple.com.src.SQLCreator;
import util.*;

public class UNTCH004F04 extends UNTCH004Q{
	

	private String getNewSerialNo1(){
		DBServerTools dbt = new DBServerTools();
		dbt._setDatabase(new Database());
		
		String sql="select case when max(serialNo1) is null then 1 else (max(serialNo1) + 1) end as serialNo1 from UNTCH_DEPRPERCENT " +
				" where enterOrg = " + Common.sqlChar(enterOrg) + 
				" and ownership = " + Common.sqlChar(ownership) + 
				" and propertyNo = " + Common.sqlChar(propertyNo) + 
				" and differenceKind = " + Common.sqlChar(differenceKind) +
				" and serialNo = " + Common.sqlChar(serialNo);
		
		return dbt._execSQL_asString(sql);
	}

protected String[] getInsertSQL(){
	String[] execSQLArray = new String[1];		
	this.setSerialNo1(this.getNewSerialNo1());
	execSQLArray[0] = new SQLCreator()._obtainSQLforInsert("UNTCH_DEPRPERCENT", getPKMap(), getRecordMap());		
	return execSQLArray;
}

protected String[] getUpdateSQL(){
	String[] execSQLArray = new String[1];
	execSQLArray[0] = new SQLCreator()._obtainSQLforUpdate("UNTCH_DEPRPERCENT", getPKMap(), getRecordMap());		
	return execSQLArray;
}

protected String[] getDeleteSQL(){
	String[] execSQLArray = new String[1];		
	execSQLArray[0] = new SQLCreator()._obtainSQLforDelete("UNTCH_DEPRPERCENT", getPKMap(), getRecordMap());		
	return execSQLArray;
}


public UNTCH004F04 queryOne() throws Exception{
	Database db = new Database();
	UNTCH004F04 obj = this;
	try {
		String sql=" select a.*  "+
					" ,(select organaname from SYSCA_ORGAN z where z.organid = a.enterorg) as enterOrgName" +
					" ,(select codename from SYSCA_CODE z where z.codekindid = 'OWA' and z.codeid = a.ownership) as ownershipName" +
					" ,(select codename from SYSCA_CODE z where z.codekindid = 'DFK' and z.codeid = a.ownership) as differenceKindName" +
					" ,(select propertyname from SYSPK_PROPERTYCODE z where z.enterorg = a.enterorg and z.propertyno = a.propertyNo) as propertyNoName" +						
					" from UNTCH_DeprPercent a where 1=1 " +
					" and a.enterorg = " + Common.sqlChar(enterOrg) +
					" and a.ownership = " + Common.sqlChar(ownership) +
					" and a.differencekind = "+ Common.sqlChar(differenceKind) +
					" and a.propertyNo = "+ Common.sqlChar(propertyNo) +
					" and a.serialNo = "+ Common.sqlChar(serialNo) +
					" and a.serialNo1 = "+ Common.sqlChar(serialNo1) +
					"";

		ResultSet rs = db.querySQL(sql,true);
		if (rs.next()){
			obj.setEnterOrg(rs.getString("EnterOrg"));
			obj.setOwnership(rs.getString("Ownership"));
			obj.setDifferenceKind(rs.getString("DifferenceKind"));
			obj.setPropertyNo(rs.getString("PropertyNo"));
			obj.setSerialNo(rs.getString("SerialNo"));
			obj.setSerialNo1(rs.getString("SerialNo1"));
			obj.setDeprPark(rs.getString("DeprPark"));
			obj.setDeprUnit(rs.getString("DeprUnit"));
			obj.setDeprAccounts(rs.getString("DeprAccounts"));
			obj.setDeprPercent(rs.getString("DeprPercent"));
			obj.setIsHistory(rs.getString("IsHistory"));
			obj.setNotes(rs.getString("Notes"));
			obj.setEditID(rs.getString("EditID"));
			obj.setEditDate(rs.getString("EditDate"));
			obj.setEditTime(rs.getString("EditTime"));

			obj.setEnterOrgName(rs.getString("EnterOrgName"));
			obj.setOwnershipName(rs.getString("OwnershipName"));
			obj.setDifferenceKindName(rs.getString("DifferenceKindName"));
			obj.setPropertyNoName(rs.getString("PropertyNoname"));
			
			obj.setDeprUnit1(rs.getString("deprUnit1"));
		}
		setStateQueryOneSuccess();
	} catch (Exception e) {
		e.printStackTrace();
	} finally {
		db.closeAll();
	}
	return obj;
}

public ArrayList queryAll() throws Exception{    
	Database db = new Database();
	ArrayList objList=new ArrayList();
	int counter=0;
	try {
		String sql = " select " +
						"enterOrg, " +
						"ownership, " +
						"differenceKind, " +
						"propertyNo, " +
						"serialNo, " +
						"serialNo1, " +
						"(select deprparkname from SYSCA_DEPRPARK z where z.enterorg = a.enterorg and z.deprparkno = a.deprPark) as deprParkName," +
						"(select deprunitname from SYSCA_DEPRUNIT z where z.enterorg = a.enterorg and z.deprunitno = a.deprUnit) as deprUnitName," +
						"(select depraccountsname from SYSCA_DEPRACCOUNTS z where z.enterorg = a.enterorg and z.depraccountsno = a.deprAccounts) as deprAccountsName," +
						"deprPercent, " +
						"(case isHistory when 'Y' then '是' else '否' end) as isHistory " +
				   " from UNTCH_DeprPercent a " +
				   " where 1=1" +
						" and a.enterorg = " + Common.sqlChar(enterOrg) +
						" and a.ownership = " + Common.sqlChar(ownership) +
						" and a.differencekind = "+ Common.sqlChar(differenceKind) +
						" and a.propertyNo = "+ Common.sqlChar(propertyNo) +
						" and a.serialNo = "+ Common.sqlChar(serialNo);
		

		ResultSet rs = db.querySQL(sql+ " order by enterorg, ownership, differencekind, propertyNo, serialNo, serialNo1",true);
		while (rs.next()){
			counter++;
			String rowArray[]=new String[11];
			rowArray[0] = checkGet(rs.getString("enterOrg"));
			rowArray[1] = checkGet(rs.getString("ownership"));
			rowArray[2] = checkGet(rs.getString("differenceKind"));
			rowArray[3] = checkGet(rs.getString("propertyNo"));
			rowArray[4] = checkGet(rs.getString("serialNo"));
			rowArray[5] = checkGet(rs.getString("serialNo1"));
			rowArray[6] = checkGet(rs.getString("deprParkName"));
			rowArray[7] = checkGet(rs.getString("deprUnitName"));
			rowArray[8] = checkGet(rs.getString("deprAccountsName"));
			rowArray[9] = checkGet(rs.getString("deprPercent"));
			rowArray[10] = checkGet(rs.getString("isHistory"));
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


public String checkDeprPercent_All(){
	String condition = " and enterorg = " + Common.sqlChar(getEnterOrg()) + 
					" and ownership = " + Common.sqlChar(getOwnership()) + 
					" and differencekind = " + Common.sqlChar(getDifferenceKind()) +
					" and propertyno = " + Common.sqlChar(getPropertyNo()) +
					" and serialno = " + Common.sqlChar(getSerialNo()) +
					" and (ishistory = 'N' or ishistory is null)";
	
	return new UNTCH_Tools()._queryData("SUM(deprpercent)")._from("UNTCH_DEPRPERCENT")._with(condition)._toString();
}

public String checkDeprPercent_queryOne(){
	String condition = " and enterorg = " + Common.sqlChar(getEnterOrg()) + 
					" and ownership = " + Common.sqlChar(getOwnership()) + 
					" and differencekind = " + Common.sqlChar(getDifferenceKind()) +
					" and propertyno = " + Common.sqlChar(getPropertyNo()) +
					" and serialno = " + Common.sqlChar(getSerialNo()) +
					("".equals(checkGet(getSerialNo1()))?"":" and serialno1 != " + Common.sqlChar(getSerialNo1())) +
					" and (ishistory = 'N' or ishistory is null)";
	
	return new UNTCH_Tools()._queryData("SUM(deprpercent)")._from("UNTCH_DEPRPERCENT")._with(condition)._toString();
}


private String enterOrg;
private String ownership;
private String differenceKind;
private String propertyNo;
private String serialNo;
private String serialNo1;
private String deprPark;
private String deprUnit;
private String deprAccounts;
private String deprPercent;
private String isHistory;
private String notes;
private String editID;
private String editDate;
private String editTime;
public String getEnterOrg() {return checkGet(enterOrg);}
public void setEnterOrg(String enterOrg) {this.enterOrg = checkSet(enterOrg);}
public String getOwnership() {return checkGet(ownership);}
public void setOwnership(String ownership) {this.ownership = checkSet(ownership);}
public String getDifferenceKind() {return checkGet(differenceKind);}
public void setDifferenceKind(String differenceKind) {this.differenceKind = checkSet(differenceKind);}
public String getPropertyNo() {return checkGet(propertyNo);}
public void setPropertyNo(String propertyNo) {this.propertyNo = checkSet(propertyNo);}
public String getSerialNo() {return checkGet(serialNo);}
public void setSerialNo(String serialNo) {this.serialNo = checkSet(serialNo);}
public String getSerialNo1() {return checkGet(serialNo1);}
public void setSerialNo1(String serialNo1) {this.serialNo1 = checkSet(serialNo1);}
public String getDeprPark() {return checkGet(deprPark);}
public void setDeprPark(String deprPark) {this.deprPark = checkSet(deprPark);}
public String getDeprUnit() {return checkGet(deprUnit);}
public void setDeprUnit(String deprUnit) {this.deprUnit = checkSet(deprUnit);}
public String getDeprAccounts() {return checkGet(deprAccounts);}
public void setDeprAccounts(String deprAccounts) {this.deprAccounts = checkSet(deprAccounts);}
public String getDeprPercent() {return checkGet(deprPercent);}
public void setDeprPercent(String deprPercent) {this.deprPercent = checkSet(deprPercent);}
public String getIsHistory() {return checkGet(isHistory);}
public void setIsHistory(String isHistory) {this.isHistory = checkSet(isHistory);}
public String getNotes() {return checkGet(notes);}
public void setNotes(String notes) {this.notes = checkSet(notes);}
public String getEditID() {return checkGet(editID);}
public void setEditID(String editID) {this.editID = checkSet(editID);}
public String getEditDate() {return checkGet(editDate);}
public void setEditDate(String editDate) {this.editDate = checkSet(editDate);}
public String getEditTime() {return checkGet(editTime);}
public void setEditTime(String editTime) {this.editTime = checkSet(editTime);}

private String enterOrgName;
private String ownershipName;
private String differenceKindName;
private String propertyNoName;
private String deprParkName;
private String deprUnitName;
private String deprAccountsName;	
public String getEnterOrgName() {return checkGet(enterOrgName);}
public String getOwnershipName() {return checkGet(ownershipName);}
public String getDifferenceKindName() {return checkGet(differenceKindName);}
public String getPropertyNoName() {return checkGet(propertyNoName);}
public String getDeprParkName() {return checkGet(deprParkName);}
public String getDeprUnitName() {return checkGet(deprUnitName);}
public String getDeprAccountsName() {return checkGet(deprAccountsName);}
public void setEnterOrgName(String enterOrgName) {this.enterOrgName = checkSet(enterOrgName);}
public void setOwnershipName(String ownershipName) {this.ownershipName = checkSet(ownershipName);}
public void setDifferenceKindName(String differenceKindName) {this.differenceKindName = checkSet(differenceKindName);}
public void setPropertyNoName(String propertyNoName) {this.propertyNoName = checkSet(propertyNoName);}
public void setDeprParkName(String deprParkName) {this.deprParkName = checkSet(deprParkName);}
public void setDeprUnitName(String deprUnitName) {this.deprUnitName = checkSet(deprUnitName);}
public void setDeprAccountsName(String deprAccountsName) {this.deprAccountsName = checkSet(deprAccountsName);}

private String deprUnit1;	
public String getDeprUnit1() {return checkGet(deprUnit1);}
public void setDeprUnit1(String deprUnit1) {this.deprUnit1 = checkSet(deprUnit1);}

private String verify;
public String getVerify() {return verify;}
public void setVerify(String verify) {this.verify = verify;}

private String newDeprMethod;
private String newDeprUnitCB;
public String getNewDeprMethod() {return newDeprMethod;}
public void setNewDeprMethod(String newDeprMethod) {this.newDeprMethod = newDeprMethod;}
public String getNewDeprUnitCB() {return newDeprUnitCB;}
public void setNewDeprUnitCB(String newDeprUnitCB) {this.newDeprUnitCB = newDeprUnitCB;}

	private Map getPKMap(){
		Map map = new HashMap();
		
		map.put("EnterOrg", getEnterOrg());
		map.put("Ownership", getOwnership());
		map.put("DifferenceKind", getDifferenceKind());
		map.put("PropertyNo", getPropertyNo());
		map.put("SerialNo", getSerialNo());
		map.put("SerialNo1", getSerialNo1());
		
		return map;
	}
	
	private Map getRecordMap(){
		Map map = new HashMap();
		
		map.put("EnterOrg", getEnterOrg());
		map.put("Ownership", getOwnership());
		map.put("DifferenceKind", getDifferenceKind());
		map.put("PropertyNo", getPropertyNo());
		map.put("SerialNo", getSerialNo());
		map.put("SerialNo1", getSerialNo1());
		map.put("DeprPark", getDeprPark());
		map.put("DeprUnit", getDeprUnit());
		map.put("DeprAccounts", getDeprAccounts());
		map.put("DeprPercent", getDeprPercent());
		map.put("IsHistory", getIsHistory());
		map.put("Notes", getNotes());
		map.put("EditID", getEditID());
		map.put("EditDate", getEditDate());
		map.put("EditTime", getEditTime());
		map.put("DeprUnit1", getDeprUnit1());			
		
		return map;
	}


}
