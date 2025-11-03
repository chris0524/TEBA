/*
*<br>程式目的：土地合併分割重測重劃作業－增加單管理資料
*<br>程式代號：untla062f
*<br>程式日期：
*<br>程式作者：Yuan-Ren Jheng
*<br>--------------------------------------------------------
*<br>修改作者　　修改日期　　　修改目的
*<br>--------------------------------------------------------
*/

package unt.la;

import java.sql.ResultSet;
import java.util.ArrayList;

import unt.ch.UNTCH_Tools;
import util.Common;
import util.*;

public class UNTLA062F extends UNTLA054Q{
	
	String enterOrg;
	String ownership;
	String differenceKind;
	String propertyNo;
	String serialNo;
	String serialNo1_062;
	String useUnit;
	String useUnit1;
	String useRelation;
	String useDateS;
	String useDateE;
	String useArea_062;
	String notes1_062;
	String notes_062;
	String editID;
	String editDate;
	String editTime;

	String enterOrgName;
	String useUnitName;
	
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
	public String getSerialNo1_062() {return checkGet(serialNo1_062);}
	public void setSerialNo1_062(String serialNo1_062) {this.serialNo1_062 = checkSet(serialNo1_062);}
	public String getUseUnit() {return checkGet(useUnit);}
	public void setUseUnit(String useUnit) {this.useUnit = checkSet(useUnit);}
	public String getUseUnit1() {return checkGet(useUnit1);}
	public void setUseUnit1(String useUnit1) {this.useUnit1 = checkSet(useUnit1);}
	public String getUseRelation() {return checkGet(useRelation);}
	public void setUseRelation(String useRelation) {this.useRelation = checkSet(useRelation);}
	public String getUseDateS() {return checkGet(useDateS);}
	public void setUseDateS(String useDateS) {this.useDateS = checkSet(useDateS);}
	public String getUseDateE() {return checkGet(useDateE);}
	public void setUseDateE(String useDateE) {this.useDateE = checkSet(useDateE);}
	public String getUseArea_062() {return checkGet(useArea_062);}
	public void setUseArea_062(String useArea_062) {this.useArea_062 = checkSet(useArea_062);}
	public String getNotes1_062() {return checkGet(notes1_062);}
	public void setNotes1_062(String notes1_062) {this.notes1_062 = checkSet(notes1_062);}
	public String getNotes_062() {return checkGet(notes_062);}
	public void setNotes_062(String notes_062) {this.notes_062 = checkSet(notes_062);}
	public String getEditID() {return checkGet(editID);}
	public void setEditID(String editID) {this.editID = checkSet(editID);}
	public String getEditDate() {return checkGet(editDate);}
	public void setEditDate(String editDate) {this.editDate = checkSet(editDate);}
	public String getEditTime() {return checkGet(editTime);}
	public void setEditTime(String editTime) {this.editTime = checkSet(editTime);}

	public String getEnterOrgName() {return checkGet(enterOrgName);}
	public void setEnterOrgName(String enterOrgName) {this.enterOrgName = checkSet(enterOrgName);}
	public String getUseUnitName() {return checkGet(useUnitName);}
	public void setUseUnitName(String useUnitName) {this.useUnitName = checkSet(useUnitName);}
	
	
	public void setSQLBeanValue(){
		UNTCH_Tools ut = new UNTCH_Tools();
		sqlbean.setTableName("UNTLA_Manage");
		
		sqlbean.primarykeyMap.clear();
		sqlbean.columnMap.clear();
		
		sqlbean.primarykeyMap.put("enterOrg", 		this.getEnterOrg());
		sqlbean.primarykeyMap.put("ownership",		this.getOwnership());
		sqlbean.primarykeyMap.put("differencekind",	this.getDifferenceKind());
		sqlbean.primarykeyMap.put("propertyNo",		this.getPropertyNo_Add());
		sqlbean.primarykeyMap.put("serialNo",		this.getSerialNo_Add());
		sqlbean.primarykeyMap.put("serialNo1",		this.getSerialNo1_062());
		
		sqlbean.columnMap.put("enterOrg", 		this.getEnterOrg());
		sqlbean.columnMap.put("ownership",		this.getOwnership());
		sqlbean.columnMap.put("differencekind",	this.getDifferenceKind());
		sqlbean.columnMap.put("propertyNo",		this.getPropertyNo_Add());
		sqlbean.columnMap.put("serialNo",		this.getSerialNo_Add());
		sqlbean.columnMap.put("serialNo1",		this.getSerialNo1_062());
		sqlbean.columnMap.put("useUnit",		this.getUseUnit());
		sqlbean.columnMap.put("useUnit1",		this.getUseUnit1());
		sqlbean.columnMap.put("useRelation",	this.getUseRelation());
		sqlbean.columnMap.put("useDateS",		ut._transToCE_Year(this.getUseDateS()));
		sqlbean.columnMap.put("useDateE",		ut._transToCE_Year(this.getUseDateE()));
		sqlbean.columnMap.put("useArea",		this.getUseArea_062());
		sqlbean.columnMap.put("notes1",			this.getNotes1_062());
		sqlbean.columnMap.put("notes",			this.getNotes_062());
		sqlbean.columnMap.put("editID",			this.getEditID());
		sqlbean.columnMap.put("editDate",		this.getEditDate());
		sqlbean.columnMap.put("editTime",		this.getEditTime());
	}

	public UNTLA054Q_data getParameterData(){		
		qbean.tableName="UNTLA_Manage";
		qbean.caseNo=this.getCaseNo_Add();
		qbean.enterOrg=this.getEnterOrg();
		qbean.ownership=this.getOwnership();
		qbean.differenceKind=this.getDifferenceKind();		
		qbean.propertyNo=this.getPropertyNo_Add();
		qbean.serialNo=this.getSerialNo_Add();
		qbean.serialNo1=this.getSerialNo1_062();
		return qbean;
	}
	
	//清除畫面所有欄位資料用
	public void clearAllDataForView(){		
		setUseUnit("");
		setUseUnit1("");
		setUseRelation("");
		setUseDateS("");
		setUseDateE("");
		setUseArea_062("");
		setNotes1_062(""); 
		setNotes_062("");
		setEditID("");
		setEditDate("");
		setEditTime("");
		setUseUnitName("");
	}
	
//傳回insert sql
protected String[] getInsertSQL(){
	String[] execSQLArray = new String[1];
	
	setSerialNo1_062(getNewSerialNo1FromDB(getParameterData()));
	
	setSQLBeanValue();
	execSQLArray[0]=sqlbean.getSQLMethod_Insert(sqlbean);
	
	LogBean.outputLogDebug(execSQLArray[0]);
	
	//extend_getInsertSQL();
	
	return execSQLArray;
}	

//傳回update sql
protected String[] getUpdateSQL(){
	String[] execSQLArray = new String[1];
		
	setSQLBeanValue();
	execSQLArray[0]=sqlbean.getSQLMethod_Update(sqlbean);
	
	LogBean.outputLogDebug(execSQLArray[0]);
	
	//extend_getUpdateSQL();
	
	return execSQLArray;
}	
	
	


//傳回delete sql
protected String[] getDeleteSQL(){
	String[] execSQLArray = new String[1];
	
	setSQLBeanValue();
	execSQLArray[0]=sqlbean.getSQLMethod_Delete(sqlbean);
		
	LogBean.outputLogDebug(execSQLArray[0]);
		
	//extend_getDeleteSQL();
	
	return execSQLArray;
}
	


//依主鍵查詢單一資料
public UNTLA062F  queryOne() throws Exception{
	Database db = new Database();
	UNTLA062F obj = this;
	
	String sql="select * from UNTLA_Manage where 1=1"+
				" and enterorg = "+ Common.sqlChar(getEnterOrg())+
				" and ownership = "+ Common.sqlChar(getOwnership())+
				" and differencekind = "+ Common.sqlChar(getDifferenceKind())+				
				" and propertyno = "+ Common.sqlChar(getPropertyNo_Add())+
				" and serialno = "+ Common.sqlChar(getSerialNo_Add())+
				" and serialno1 = "+ Common.sqlChar(getSerialNo1_062());

	try {
		LogBean.outputLogDebug(sql);
		
		ResultSet rs = db.querySQL(sql);
		UNTCH_Tools ut = new UNTCH_Tools();
		if (rs.next()){
			obj.setSerialNo1_062(rs.getString("serialNo1"));
			obj.setUseUnit(rs.getString("useUnit"));
			obj.setUseUnit1(rs.getString("useUnit1"));
			obj.setUseRelation(rs.getString("useRelation"));
			obj.setUseDateS(ut._transToROC_Year(rs.getString("useDateS")));
			obj.setUseDateE(ut._transToROC_Year(rs.getString("useDateE")));
			obj.setUseArea_062(rs.getString("useArea"));
			obj.setNotes1_062(rs.getString("notes1"));
			obj.setNotes_062(rs.getString("notes"));
			obj.setEditID(rs.getString("editID"));
			obj.setEditDate(rs.getString("editDate"));
			obj.setEditTime(rs.getString("editTime"));		
			
			obj.setUseUnitName(getEnterOrgNameFromDB(rs.getString("useUnit")));
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
					" a.serialNo1, a.useUnit, a.useUnit1, a.useRelation, a.useDateS,"+
					" (select organsname from sysca_organ where 1=1 and organID = a.useUnit) as useUnitName, "+
					" (select codeName from SYSCA_Code where codeKindID='URE' and codeID = a.useRelation) as useRelationName,"+
					" a.useDateE, a.useArea"+
				" from UNTLA_Manage a where 1=1"+
					" and a.enterorg = " + Common.sqlChar(this.getEnterOrg()) +
					" and a.ownership = " + Common.sqlChar(this.getOwnership()) +
					" and a.differencekind = " + Common.sqlChar(this.getDifferenceKind()) +
					" and a.propertyNo = " + Common.sqlChar(this.getPropertyNo_Add()) +
					" and a.serialNo = " + Common.sqlChar(this.getSerialNo_Add());
	
	sql+=" order by a.serialno1, a.useDateS, a.useDateE ";

	try {
		LogBean.outputLogDebug(sql);
		UNTCH_Tools ul = new UNTCH_Tools();
		ResultSet rs = db.querySQL(sql,true);
		processCurrentPageAttribute(rs);			
		if (getTotalRecord() > 0) {
			int count = getRecordStart();
			int end = getRecordEnd();
			do {
				if (count > end)
					break;
			String rowArray[]=new String[7];
			
			rowArray[0]=Common.get(rs.getString("serialNo1")); 
			rowArray[1]=Common.get(rs.getString("useUnitName"));
			rowArray[2]=Common.get(rs.getString("useUnit1"));
			rowArray[3]=Common.get(rs.getString("useRelationName")); 
			rowArray[4]=Common.get(ul._transToROC_Year(rs.getString("useDateS"))); 
			rowArray[5]=Common.get(ul._transToROC_Year(rs.getString("useDateE"))); 
			rowArray[6]=Common.get(rs.getString("useArea"));
			
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

public String execCheckPropertyKind(){
	String sql="select propertykind from untla_land" +
				" where 1=1" + 
					" and enterOrg = " + Common.sqlChar(this.getEnterOrg()) + 
					" and ownership = " + Common.sqlChar(this.getOwnership()) +
					" and differencekind = " + Common.sqlChar(this.getDifferenceKind()) +
					" and caseNo = " + Common.sqlChar(this.getCaseNo_Add()) +
					" and propertyNo = " + Common.sqlChar(this.getPropertyNo_Add()) +
					" and serialNo = " + Common.sqlChar(this.getSerialNo_Add()) ;
	String returnStr = this.getNameData("propertykind", sql);
	return returnStr;
}
	

public String execCheckArea(){
	String sql="select" +
				" case" +
					" when useArea > Area" +
					" then 'BigThen'" +
					" else 'No'" +
				" end as RESULTSTR" +
		" from (" +
			" select" +
			" (select sum(a.useArea) from untla_manage a where 1=1 " +
				" and a.enterorg=b.enterorg" +
				" and a.ownership=b.ownership" +
				" and a.differencekind=b.differencekind" +
				" and a.propertyno=b.propertyno" +
				" and a.serialno=b.serialno " +
//				" and (case a.useDateS when null then 00000000 else a.useDateS end) <= "+Datetime.getYYYYMMDD() +
//				" and (case a.useDateE when null then 99999999 else a.useDateE end) >= "+Datetime.getYYYYMMDD() +
//				" and ((" +
//							" b.propertyKind  = '04' " +
//							" and a.isdefault = '1'"+
//						") or (" +
//							" b.propertyKind  != '04' " +
//							" and (case a.useDateS when null then '0000000' else a.useDateS end) <= " + Common.sqlChar(Datetime.getYYYMMDD()) +
//							" and (case a.useDateE when null then '9999999' else a.useDateE end) >= " + Common.sqlChar(Datetime.getYYYMMDD()) + 
//						"))" +
			" ) as useArea, b.area as Area" +
			" from untla_land b" +
			" where 1=1" +
				" and b.enterOrg = " + Common.sqlChar(this.getEnterOrg()) + 
				" and b.ownership = " + Common.sqlChar(this.getOwnership()) +
				" and b.differencekind = " + Common.sqlChar(this.getDifferenceKind()) +
				" and b.propertyNo = " + Common.sqlChar(this.getPropertyNo_Add()) +
				" and b.serialNo = " + Common.sqlChar(this.getSerialNo_Add()) +
		" ) a";
	
	String returnStr;
	if("BigThen".equals(this.getNameData("RESULTSTR", sql))){	returnStr="該筆土地使用面積大於該筆土地總面積，請留意！！";
	}else{														returnStr="";
	}
	return returnStr;	
}
	
public String getHoldAreaFromUntla_Land(){
	String returnStr;
	
	String sql = "select holdarea from Untla_Land" + 
				" where 1=1" +
				" and enterOrg = " + Common.sqlChar(this.getEnterOrg()) +
				" and ownership = " + Common.sqlChar(this.getOwnership()) +
				" and propertyNo = " + Common.sqlChar(this.getPropertyNo()) +
				" and serialNo = " + Common.sqlChar(this.getSerialNo()) ;
	
	returnStr = getNameData("holdarea", sql);
	return returnStr;
	
}

}


