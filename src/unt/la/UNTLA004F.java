/*
*<br>程式目的：土地主檔資料維護－地上物
*<br>程式代號：untla004f
*<br>程式日期：0940909
*<br>程式作者：novia
*<br>--------------------------------------------------------
*<br>修改作者　　修改日期　　　修改目的
*<br>clive.chang 0941219	Debug & Modify for Testing and autual running..
*<br>--------------------------------------------------------
*/

package unt.la;

import java.sql.ResultSet;
import java.util.ArrayList;

import util.Common;
import unt.ch.UNTCH_Tools;
import util.*;
import TDlib_Simple.tools.src.StringTools;;

public class UNTLA004F extends UNTLA001Q{

String enterOrg;
String enterOrgName;
String ownership;
String caseNo;
String propertyNo;
String propertyName;
String serialNo;
String serialNo1;
String ownership1;
String owner;
String manageOrg;
String manageOrgName;
String state004;
String purpose;
String useArea004;
String mergeUseSign;
String notes1;
String notes;

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
public String getPropertyName(){ return checkGet(propertyName); }
public void setPropertyName(String s){ propertyName=checkSet(s); }
public String getSerialNo(){ return checkGet(serialNo); }
public void setSerialNo(String s){ serialNo=checkSet(s); }
public String getSerialNo1(){ return checkGet(serialNo1); }
public void setSerialNo1(String s){ serialNo1=checkSet(s); }
public String getOwnership1(){ return checkGet(ownership1); }
public void setOwnership1(String s){ ownership1=checkSet(s); }
public String getOwner(){ return checkGet(owner); }
public void setOwner(String s){ owner=checkSet(s); }
public String getManageOrg(){ return checkGet(manageOrg); }
public void setManageOrgName(String s){ manageOrgName=checkSet(s); }
public String getManageOrgName(){ return checkGet(manageOrgName); }
public void setManageOrg(String s){ manageOrg=checkSet(s); }
public String getState004(){ return checkGet(state004); }
public void setState004(String s){ state004=checkSet(s); }
public String getPurpose(){ return checkGet(purpose); }
public void setPurpose(String s){ purpose=checkSet(s); }
public String getUseArea004(){ return checkGet(useArea004); }
public void setUseArea004(String s){ useArea004=checkSet(s); }
public String getMergeUseSign(){ return checkGet(mergeUseSign); }
public void setMergeUseSign(String s){ mergeUseSign=checkSet(s); }
public String getNotes1(){ return checkGet(notes1); }
public void setNotes1(String s){ notes1=checkSet(s); }
public String getNotes(){ return checkGet(notes); }
public void setNotes(String s){ notes=checkSet(s); }

String differenceKind;
public String getDifferenceKind() {return checkGet(differenceKind);}
public void setDifferenceKind(String differenceKind) {this.differenceKind = checkSet(differenceKind);}


//傳回執行insert前之檢查sql
protected String[][] getInsertCheckSQL(){ 
	String[][] checkSQLArray = new String[1][4];
 	checkSQLArray[0][0]=" select count(*) as checkResult from UNTLA_Attachment where 1=1 " + 
		" and enterOrg = " + Common.sqlChar(enterOrg) + 
		" and ownership = " + Common.sqlChar(ownership) + 
		" and propertyNo = " + Common.sqlChar(propertyNo) + 
		" and serialNo = " + Common.sqlChar(serialNo) + 
		" and serialNo1 = " + Common.sqlChar(serialNo1) +
		" and differenceKind = " + Common.sqlChar(differenceKind) + 
		"";
	checkSQLArray[0][1]=">";
	checkSQLArray[0][2]="0";
	checkSQLArray[0][3]="該筆資料己重複，請重新輸入！";
	return checkSQLArray;
}

//傳回insert sql
protected String[] getInsertSQL(){
	//取得自動編號
	Database db = new Database();
	ResultSet rs;	
	String sql="select (max(serialNo1) + 1) as serialNo1 from UNTLA_Attachment " +
		" where enterOrg = " + Common.sqlChar(enterOrg) +
		" and ownership = " + Common.sqlChar(ownership) +
		" and propertyNo = " + Common.sqlChar(propertyNo) +
		" and serialNo = " + Common.sqlChar(serialNo) +
		" and differenceKind = " + Common.sqlChar(differenceKind) ;

	try {		
		rs = db.querySQL(sql);
		if (rs.next()){
			if("".equals(checkGet(rs.getString("serialNo1")))){
				setSerialNo1("1");
			}else{
				setSerialNo1(rs.getString("serialNo1"));
			}
		}else{
			setSerialNo1("1");
		}
	} catch (Exception e) {
		e.printStackTrace();
	} finally {
		db.closeAll();
	}
	
	String[] execSQLArray = new String[1];
	execSQLArray[0]=" insert into UNTLA_Attachment(" +
			" enterOrg,"+
			" ownership,"+
			" propertyNo,"+
			" serialNo,"+
			" serialNo1,"+
			" ownership1,"+
			" owner,"+
			" manageOrg,"+
			" state,"+
			" purpose,"+
			" useArea,"+
			" mergeUseSign,"+
			" notes1,"+
			" notes,"+
			" editID,"+
			" editDate,"+
			" editTime,"+
			" differenceKind"+
		" )Values(" +
			Common.sqlChar(enterOrg) + "," +
			Common.sqlChar(ownership) + "," +
			Common.sqlChar(propertyNo) + "," +
			Common.sqlChar(serialNo) + "," +
			Common.sqlChar(serialNo1) + "," +
			Common.sqlChar(ownership1) + "," +
			Common.sqlChar(owner) + "," +
			Common.sqlChar(manageOrg) + "," +
			Common.sqlChar(state004) + "," +
			Common.sqlChar(purpose) + "," +
			Common.sqlChar(useArea004) + "," +
			Common.sqlChar(mergeUseSign) + "," +
			Common.sqlChar(notes1) + "," +
			Common.sqlChar(notes) + "," +
			Common.sqlChar(getEditID()) + "," +
			Common.sqlChar(new UNTCH_Tools()._transToCE_Year(getEditDate())) + "," +
			Common.sqlChar(getEditTime()) + "," +
			Common.sqlChar(getDifferenceKind()) + ")" ;
	return execSQLArray;
}


//傳回update sql
protected String[] getUpdateSQL(){
	String[] execSQLArray = new String[1];
	execSQLArray[0]=" update UNTLA_Attachment set " +
			" ownership1 = " + Common.sqlChar(ownership1) + "," +
			" owner = " + Common.sqlChar(owner) + "," +
			" manageOrg = " + Common.sqlChar(manageOrg) + "," +
			" state = " + Common.sqlChar(state004) + "," +
			" purpose = " + Common.sqlChar(purpose) + "," +
			" useArea = " + Common.sqlChar(useArea004) + "," +
			" mergeUseSign = " + Common.sqlChar(mergeUseSign) + "," +
			" notes1 = " + Common.sqlChar(notes1) + "," +
			" notes = " + Common.sqlChar(notes) + "," +
			" editID = " + Common.sqlChar(getEditID()) + "," +
			" editDate = " + Common.sqlChar(new UNTCH_Tools()._transToCE_Year(getEditDate())) + "," +
			" editTime = " + Common.sqlChar(getEditTime()) + "," +
			" differenceKind = " + Common.sqlChar(getDifferenceKind()) + 
		" where 1=1 " + 
			" and enterOrg = " + Common.sqlChar(enterOrg) +
			" and ownership = " + Common.sqlChar(ownership) +
			" and propertyNo = " + Common.sqlChar(propertyNo) +
			" and serialNo = " + Common.sqlChar(serialNo) +
			" and serialNo1 = " + Common.sqlChar(serialNo1) +
			" and differenceKind = " + Common.sqlChar(differenceKind) +
			"";
	return execSQLArray;
}


//傳回delete sql
protected String[] getDeleteSQL(){
	String[] execSQLArray = new String[1];
	execSQLArray[0]=" delete UNTLA_Attachment where 1=1 " +
			" and enterOrg = " + Common.sqlChar(enterOrg) +
			" and ownership = " + Common.sqlChar(ownership) +
			" and propertyNo = " + Common.sqlChar(propertyNo) +
			" and serialNo = " + Common.sqlChar(serialNo) +
			" and serialNo1 = " + Common.sqlChar(serialNo1) +
			" and differenceKind = " + Common.sqlChar(differenceKind) +
			"";
	return execSQLArray;
}


/**
 * <br>
 * <br>目的：依主鍵查詢單一資料
 * <br>參數：無
 * <br>傳回：傳回本物件
*/

public UNTLA004F  queryOne() throws Exception{
	Database db = new Database();
	UNTLA004F obj = this;
	try {
		String sql=" select a.enterOrg, a.ownership, a.propertyNo, a.serialNo, a.serialNo1, a.ownership1, a.owner, a.manageOrg, a.state, a.purpose, a.useArea, a.mergeUseSign, a.notes1, a.notes, a.editID, a.editDate, a.editTime, b.organSName, a.differenceKind  "+
			" from UNTLA_Attachment a left join SYSCA_Organ b on b.organID = a.manageOrg where 1=1 " +
			" and a.enterOrg = " + Common.sqlChar(enterOrg) +
			" and a.ownership = " + Common.sqlChar(ownership) +
			" and a.propertyNo = " + Common.sqlChar(propertyNo) +
			" and a.serialNo = " + Common.sqlChar(serialNo) +
			" and a.serialNo1 = " + Common.sqlChar(serialNo1) +
			" and differenceKind = " + Common.sqlChar(differenceKind) +
			"";
		ResultSet rs = db.querySQL(sql);
		if (rs.next()){
			obj.setEnterOrg(rs.getString("enterOrg"));
			obj.setOwnership(rs.getString("ownership"));
			obj.setPropertyNo(rs.getString("propertyNo"));
			obj.setSerialNo(rs.getString("serialNo"));
			obj.setSerialNo1(rs.getString("serialNo1"));
			obj.setOwnership1(rs.getString("ownership1"));
			obj.setOwner(rs.getString("owner"));
			obj.setManageOrg(rs.getString("manageOrg"));
			obj.setManageOrgName(rs.getString("organSName"));
			obj.setState004(rs.getString("state"));
			obj.setPurpose(rs.getString("purpose"));
			obj.setUseArea004(rs.getString("useArea"));
			obj.setMergeUseSign(rs.getString("mergeUseSign"));
			obj.setNotes1(rs.getString("notes1"));
			obj.setNotes(rs.getString("notes"));
			obj.setEditID(rs.getString("editID"));
			obj.setEditDate(new UNTCH_Tools()._transToROC_Year(rs.getString("editDate")));
			obj.setEditTime(rs.getString("editTime"));
			obj.setDifferenceKind(rs.getString("differenceKind"));
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
		String sql=" select a.enterOrg, a.ownership, a.propertyNo, a.serialNo, a.serialNo1, a.owner, " + 
			" (select c.codeName from SYSCA_Code c where c.codeKindID = 'OWN' and c.codeID = a.ownership1) as ownership1, " +
			" (select b.organSName from SYSCA_Organ b where b.organID = a.manageOrg) as manageOrg," +
			" (select d.codeName from SYSCA_Code d where d.codeKindID = 'PUR' and d.codeID = a.purpose) as purpose," +
			" a.useArea "+
			" from UNTLA_Attachment a where 1=1 " +
			" and a.enterOrg = " + Common.sqlChar(enterOrg) +
			" and a.ownership = " + Common.sqlChar(ownership) +
			" and a.propertyNo = " + Common.sqlChar(propertyNo) +
			" and a.serialNo = " + Common.sqlChar(serialNo) +
			" and a.differenceKind = " + Common.sqlChar(differenceKind) ;
			
		StringTools st = new StringTools();
		ResultSet rs = db.querySQL(sql,true);
		processCurrentPageAttribute(rs);			
		if (getTotalRecord() > 0) {
			int count = getRecordStart();
			int end = getRecordEnd();
			do {
				if (count > end)
					break;
			String rowArray[]=new String[10];
			rowArray[0]=rs.getString("enterOrg"); 
			rowArray[1]=rs.getString("ownership"); 
			rowArray[2]=rs.getString("propertyNo"); 
			rowArray[3]=rs.getString("serialNo"); 
			rowArray[4]=rs.getString("serialNo1"); 		
			rowArray[5]=rs.getString("ownership1");
			rowArray[6]=rs.getString("owner"); 
			rowArray[7]=rs.getString("manageOrg"); 
			rowArray[8]=rs.getString("purpose"); 
			rowArray[9]=st._getDotFormat(rs.getString("useArea"),2); 
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

public String getHoldAreaFromUntla_Land(){
	UNTLA054_ServerBean serBean = new UNTLA054_ServerBean();
	String returnStr;
	
	String sql = "select holdarea from Untla_Land" + 
				" where 1=1" +
				" and enterOrg = " + Common.sqlChar(this.getEnterOrg()) +
				" and ownership = " + Common.sqlChar(this.getOwnership()) +
				" and propertyNo = " + Common.sqlChar(this.getPropertyNo()) +
				" and serialNo = " + Common.sqlChar(this.getSerialNo()) +
				" and differenceKind = " + Common.sqlChar(this.getDifferenceKind()) ;
	
	returnStr = serBean.getNameData("HOLDAREA", sql);
	return returnStr;
	
}
}
