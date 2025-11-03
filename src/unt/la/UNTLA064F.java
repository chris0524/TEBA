/*
*<br>程式目的：土地合併分割重測重劃作業－增加單地上物資料
*<br>程式代號：untla064f
*<br>程式日期：
*<br>程式作者：Yuan-Ren Jheng
*<br>--------------------------------------------------------
*<br>修改作者　　修改日期　　　修改目的
*<br>--------------------------------------------------------
*/

package unt.la;

import java.sql.ResultSet;
import java.util.ArrayList;

import util.Common;
import util.*;

public class UNTLA064F extends UNTLA054Q{
	
	String enterOrg;
	String ownership;
	String differenceKind;
	String propertyNo;
	String serialNo;
	String serialNo1_064;
	String ownership1;
	String owner;
	String manageOrg;
	String state_064;
	String purpose;
	String useArea_064;
	String mergeUseSign;
	String notes1_064;
	String notes_064;
	String editID;
	String editDate;
	String editTime;

	String enterOrgName;
	String manageOrgName;
	String ownership1Name;
	
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
	public String getSerialNo1_064() {return checkGet(serialNo1_064);}
	public void setSerialNo1_064(String serialNo1_064) {this.serialNo1_064 = checkSet(serialNo1_064);}
	public String getOwnership1() {return checkGet(ownership1);}
	public void setOwnership1(String ownership1) {this.ownership1 = checkSet(ownership1);}
	public String getOwner() {return checkGet(owner);}
	public void setOwner(String owner) {this.owner = checkSet(owner);}
	public String getManageOrg() {return checkGet(manageOrg);}
	public void setManageOrg(String manageOrg) {this.manageOrg = checkSet(manageOrg);}
	public String getState_064() {return checkGet(state_064);}
	public void setState_064(String state_064) {this.state_064 = checkSet(state_064);}
	public String getPurpose() {return checkGet(purpose);}
	public void setPurpose(String purpose) {this.purpose = checkSet(purpose);}
	public String getUseArea_064() {return checkGet(useArea_064);}
	public void setUseArea_064(String useArea_064) {this.useArea_064 = checkSet(useArea_064);}
	public String getMergeUseSign() {return checkGet(mergeUseSign);}
	public void setMergeUseSign(String mergeUseSign) {this.mergeUseSign = checkSet(mergeUseSign);}
	public String getNotes1_064() {return checkGet(notes1_064);}
	public void setNotes1_064(String notes1_064) {this.notes1_064 = checkSet(notes1_064);}
	public String getNotes_064() {return checkGet(notes_064);}
	public void setNotes_064(String notes_064) {this.notes_064 = checkSet(notes_064);}
	public String getEditID() {return checkGet(editID);}
	public void setEditID(String editID) {this.editID = checkSet(editID);}
	public String getEditDate() {return checkGet(editDate);}
	public void setEditDate(String editDate) {this.editDate = checkSet(editDate);}
	public String getEditTime() {return checkGet(editTime);}
	public void setEditTime(String editTime) {this.editTime = checkSet(editTime);}
	public String getEnterOrgName() {return checkGet(enterOrgName);}
	public void setEnterOrgName(String enterOrgName) {this.enterOrgName = checkSet(enterOrgName);}
	public String getManageOrgName() {return checkGet(manageOrgName);}
	public void setManageOrgName(String manageOrgName) {this.manageOrgName = checkSet(manageOrgName);}
	public String getOwnership1Name() {return checkGet(ownership1Name);}
	public void setOwnership1Name(String ownership1Name) {this.ownership1Name = checkSet(ownership1Name);}
	
	
	
	public void setSQLBeanValue(){
		sqlbean.setTableName("UNTLA_Attachment");
		
		sqlbean.primarykeyMap.clear();
		sqlbean.columnMap.clear();
		
		sqlbean.primarykeyMap.put("enterOrg", 		this.getEnterOrg());
		sqlbean.primarykeyMap.put("ownership",		this.getOwnership());
		sqlbean.primarykeyMap.put("differencekind",	this.getDifferenceKind());
		sqlbean.primarykeyMap.put("propertyNo",		this.getPropertyNo_Add());
		sqlbean.primarykeyMap.put("serialNo",		this.getSerialNo_Add());
		sqlbean.primarykeyMap.put("serialNo1",		this.getSerialNo1_064());
		
		sqlbean.columnMap.put("enterOrg", 		this.getEnterOrg());
		sqlbean.columnMap.put("ownership",		this.getOwnership());
		sqlbean.columnMap.put("differencekind",	this.getDifferenceKind());
		sqlbean.columnMap.put("propertyNo",		this.getPropertyNo_Add());
		sqlbean.columnMap.put("serialNo",		this.getSerialNo_Add());
		sqlbean.columnMap.put("serialNo1",		this.getSerialNo1_064());
		sqlbean.columnMap.put("ownership1",		this.getOwnership1());
		sqlbean.columnMap.put("owner",			this.getOwner());
		sqlbean.columnMap.put("manageOrg",		this.getManageOrg());
		sqlbean.columnMap.put("state",			this.getState_064());
		sqlbean.columnMap.put("purpose",		this.getPurpose());
		sqlbean.columnMap.put("useArea",		this.getUseArea_064());
		sqlbean.columnMap.put("mergeUseSign",	this.getMergeUseSign());
		sqlbean.columnMap.put("notes1",			this.getNotes1_064());
		sqlbean.columnMap.put("notes",			this.getNotes_064());
		sqlbean.columnMap.put("editID",			this.getEditID());
		sqlbean.columnMap.put("editDate",		this.getEditDate());
		sqlbean.columnMap.put("editTime",		this.getEditTime());

	}

	public UNTLA054Q_data getParameterData(){		
		qbean.tableName="UNTLA_Attachment";
		qbean.caseNo=this.getCaseNo_Add();
		qbean.differenceKind=this.getDifferenceKind();
		qbean.enterOrg=this.getEnterOrg();
		qbean.ownership=this.getOwnership();
		qbean.propertyNo=this.getPropertyNo_Add();
		qbean.serialNo=this.getSerialNo_Add();
		qbean.serialNo1=this.getSerialNo1_064();
		return qbean;
	}
	
	//清除畫面所有欄位資料用
	public void clearAllDataForView(){		
		setOwnership1("");
		setOwner("");
		setManageOrg("");
		setState_064("");
		setPurpose("");
		setUseArea_064("");
		setMergeUseSign("");
		setNotes1_064("");
		setNotes_064("");
		setEditID("");
		setEditDate("");
		setEditTime("");

	}
	
//傳回insert sql
protected String[] getInsertSQL(){
	String[] execSQLArray = new String[1];
	
	setSerialNo1_064(getNewSerialNo1FromDB(getParameterData()));
	
	setSQLBeanValue();
	execSQLArray[0]=sqlbean.getSQLMethod_Insert(sqlbean);
	
	LogBean.outputLogDebug(execSQLArray[0]);
	
	extend_getInsertSQL();
	
	return execSQLArray;
}	

//傳回update sql
protected String[] getUpdateSQL(){
	String[] execSQLArray = new String[1];
		
	setSQLBeanValue();
	execSQLArray[0]=sqlbean.getSQLMethod_Update(sqlbean);
	
	LogBean.outputLogDebug(execSQLArray[0]);
	
	extend_getUpdateSQL();
	
	return execSQLArray;
}	
	
	


//傳回delete sql
protected String[] getDeleteSQL(){
	String[] execSQLArray = new String[1];
	
	setSQLBeanValue();
	execSQLArray[0]=sqlbean.getSQLMethod_Delete(sqlbean);
		
	LogBean.outputLogDebug(execSQLArray[0]);
		
	extend_getDeleteSQL();
	
	return execSQLArray;
}
	


//依主鍵查詢單一資料
public UNTLA064F  queryOne() throws Exception{
	Database db = new Database();
	UNTLA064F obj = this;
	
	String sql="select * from UNTLA_Attachment where 1=1"+
				" and enterorg = "+ Common.sqlChar(getEnterOrg())+
				" and ownership = "+ Common.sqlChar(getOwnership())+
				" and differencekind = "+ Common.sqlChar(getDifferenceKind())+
				" and propertyno = "+ Common.sqlChar(getPropertyNo_Add())+
				" and serialno = "+ Common.sqlChar(getSerialNo_Add())+
				" and serialno1 = "+ Common.sqlChar(getSerialNo1_064());

	try {
		LogBean.outputLogDebug(sql);
		
		ResultSet rs = db.querySQL(sql);
		if (rs.next()){
			obj.setEnterOrg(rs.getString("enterOrg"));
			obj.setOwnership(rs.getString("ownership"));
			obj.setDifferenceKind(rs.getString("differencekind"));			
			obj.setPropertyNo(rs.getString("propertyNo"));
			obj.setSerialNo(rs.getString("serialNo"));
			obj.setSerialNo1_064(rs.getString("serialNo1"));
			obj.setOwnership1(rs.getString("ownership1"));
			obj.setOwner(rs.getString("owner"));
			obj.setManageOrg(rs.getString("manageOrg"));
			obj.setState_064(rs.getString("state"));
			obj.setPurpose(rs.getString("purpose"));
			obj.setUseArea_064(rs.getString("useArea"));
			obj.setMergeUseSign(rs.getString("mergeUseSign"));
			obj.setNotes1_064(rs.getString("notes1"));
			obj.setNotes_064(rs.getString("notes"));
			obj.setEditID(rs.getString("editID"));
			obj.setEditDate(rs.getString("editDate"));
			obj.setEditTime(rs.getString("editTime"));

			
			obj.setManageOrgName(getEnterOrgNameFromDB(rs.getString("manageOrg")));
		}
		
		setStateQueryOneSuccess();
	} catch (Exception e) {
		LogBean.outputLogError("Exception : "+e.getMessage()+"\n"+sql);
	} finally {
		db.closeAll();
	}
	return obj;
}


//依查詢欄位查詢多筆資料
public ArrayList queryAll() throws Exception{
	Database db = new Database();
	ArrayList objList=new ArrayList();
	
	String sql="select "+
					" a.serialNo1, a.ownership1, a.owner, a.manageOrg, a.purpose, a.useArea,"+
					" (select codename from sysca_code where codekindid='OWN' and codeid=a.ownership1) as ownership1Name, "+
					" (select codename from sysca_code where codekindid='PUR' and codeid=a.purpose) as purposeName, "+
					" (select organsname from sysca_organ where 1=1 and organID = a.manageOrg) as manageOrgName"+					
				" from UNTLA_Attachment a where 1=1"+
					" and a.enterorg = " + Common.sqlChar(this.getEnterOrg()) +
					" and a.ownership = " + Common.sqlChar(this.getOwnership()) +
					" and a.differencekind = " + Common.sqlChar(this.getDifferenceKind()) +					
					" and a.propertyNo = " + Common.sqlChar(this.getPropertyNo_Add()) +
					" and a.serialNo = " + Common.sqlChar(this.getSerialNo_Add());
	
	sql+=" order by a.enterOrg, a.ownership, a.differencekind, a.propertyNo, a.serialNo, a.serialNo1";

	try {
		LogBean.outputLogDebug(sql);
		
		ResultSet rs = db.querySQL(sql,true);
		processCurrentPageAttribute(rs);			
		if (getTotalRecord() > 0) {
			int count = getRecordStart();
			int end = getRecordEnd();
			do {
				if (count > end)
					break;
			String rowArray[]=new String[6];
			
			rowArray[0]=Common.get(rs.getString("serialNo1")); 
			rowArray[1]=Common.get(rs.getString("ownership1Name"));
			rowArray[2]=Common.get(rs.getString("owner"));
			rowArray[3]=Common.get(rs.getString("manageOrgName"));
			rowArray[4]=Common.get(rs.getString("purposeName"));
			rowArray[5]=Common.get(rs.getString("useArea"));
			
			objList.add(rowArray);
			count++;
			} while (rs.next());
		}
		setStateQueryAllSuccess();
	} catch (Exception e) {
		LogBean.outputLogError("Exception : "+e.getMessage()+"\n"+sql);
	} finally {
		db.closeAll();
	}
	return objList;
}

public String getHoldAreaFromUntla_Land(){
	String returnStr;
	
	String sql = "select holdarea from Untla_Land" + 
				" where 1=1" +
				" and enterOrg = " + Common.sqlChar(this.getEnterOrg()) +
				" and ownership = " + Common.sqlChar(this.getOwnership()) +
				" and differencekind = " + Common.sqlChar(this.getDifferenceKind()) +				
				" and propertyNo = " + Common.sqlChar(this.getPropertyNo()) +
				" and serialNo = " + Common.sqlChar(this.getSerialNo()) ;
	
	returnStr = getNameData("holdarea", sql);
	return returnStr;
	
}

}


