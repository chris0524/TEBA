/*
*<br>程式目的：建物主檔資料維護--附屬建物
*<br>程式代號：untbu005f
*<br>程式日期：0940919
*<br>程式作者：clive.chang
*<br>--------------------------------------------------------
*<br>修改作者　　修改日期　　　修改目的
*<br>--------------------------------------------------------
*/

package unt.bu;

import java.sql.ResultSet;
import java.util.ArrayList;

import util.Common;
import unt.ch.UNTCH_Tools;
import util.*;

public class UNTBU005F extends UNTBU001Q{

String enterOrg;
String ownership;
String propertyNo;
String serialNo;
String serialNo1;
String attachment;
String purpose;
String stuff;
String area;
String notes;


public String getEnterOrg(){ return checkGet(enterOrg); }
public void setEnterOrg(String s){ enterOrg=checkSet(s); }
public String getOwnership(){ return checkGet(ownership); }
public void setOwnership(String s){ ownership=checkSet(s); }
public String getPropertyNo(){ return checkGet(propertyNo); }
public void setPropertyNo(String s){ propertyNo=checkSet(s); }
public String getSerialNo(){ return checkGet(serialNo); }
public void setSerialNo(String s){ serialNo=checkSet(s); }
public String getSerialNo1(){ return checkGet(serialNo1); }
public void setSerialNo1(String s){ serialNo1=checkSet(s); }
public String getAttachment(){ return checkGet(attachment); }
public void setAttachment(String s){ attachment=checkSet(s); }
public String getPurpose(){ return checkGet(purpose); }
public void setPurpose(String s){ purpose=checkSet(s); }
public String getStuff(){ return checkGet(stuff); }
public void setStuff(String s){ stuff=checkSet(s); }
public String getArea(){ return checkGet(area); }
public void setArea(String s){ area=checkSet(s); }
public String getNotes(){ return checkGet(notes); }
public void setNotes(String s){ notes=checkSet(s); }


String differenceKind;
public String getDifferenceKind() {return checkGet(differenceKind);}
public void setDifferenceKind(String differenceKind) {this.differenceKind = checkSet(differenceKind);}


//String q_enterOrg;
//String q_enterOrgName;
//String q_ownership;
//String q_propertyNo;
//String q_serialNo;
//String q_caseNoS;
//String q_dataState;
//
//public String getQ_enterOrg(){ return checkGet(q_enterOrg); }
//public void setQ_enterOrg(String s){ q_enterOrg=checkSet(s); }
//public String getQ_enterOrgName(){ return checkGet(q_enterOrgName); }
//public void setQ_enterOrgName(String s){ q_enterOrgName=checkSet(s); }
//public String getQ_ownership(){ return checkGet(q_ownership); }
//public void setQ_ownership(String s){ q_ownership=checkSet(s); }
//public String getQ_propertyNo(){ return checkGet(q_propertyNo); }
//public void setQ_propertyNo(String s){ q_propertyNo=checkSet(s); }
//public String getQ_serialNo(){ return checkGet(q_serialNo); }
//public void setQ_serialNo(String s){ q_serialNo=checkSet(s); }
//public String getQ_caseNoS(){ return checkGet(q_caseNoS); }
//public void setQ_caseNoS(String s){ q_caseNoS=checkSet(s); }
//public String getQ_dataState(){ return checkGet(q_dataState); }
//public void setQ_dataState(String s){ q_dataState=checkSet(s); }

//傳回執行insert前之檢查sql
protected String[][] getInsertCheckSQL(){
	//取得自動編號
	Database db = new Database();
	ResultSet rs;	
	String sql="select max(serialNo1)+1 as serialNo1 from UNTBU_Attachment1 " +
		" where enterOrg = " + Common.sqlChar(enterOrg) +
		" and ownership = " + Common.sqlChar(ownership) +
		" and propertyNo = " + Common.sqlChar(propertyNo) +
		" and serialNo = " + Common.sqlChar(serialNo) +
		" and differenceKind = " + Common.sqlChar(differenceKind);	
	try {		
		rs = db.querySQL(sql);
		if (rs.next()){
			if (rs.getString("serialNo1")==null)
				setSerialNo1("1");
			else
				setSerialNo1(rs.getString("serialNo1"));
		}
	} catch (Exception e) {
		e.printStackTrace();
	} finally {
		db.closeAll();
	}	
	String[][] checkSQLArray = new String[1][4];
 	checkSQLArray[0][0]=" select count(*) as checkResult from UNTBU_Attachment1 where 1=1 " + 
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
	String[] execSQLArray = new String[1];
	execSQLArray[0]=" insert into UNTBU_Attachment1(" +
			" enterOrg,"+
			" ownership,"+
			" propertyNo,"+
			" serialNo,"+
			" serialNo1,"+
			" attachment,"+
			" purpose,"+
			" stuff,"+
			" area,"+
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
			Common.sqlChar(attachment) + "," +
			Common.sqlChar(purpose) + "," +
			Common.sqlChar(stuff) + "," +
			Common.sqlChar(area) + "," +
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
	execSQLArray[0]=" update UNTBU_Attachment1 set " +
			" attachment = " + Common.sqlChar(attachment) + "," +
			" purpose = " + Common.sqlChar(purpose) + "," +
			" stuff = " + Common.sqlChar(stuff) + "," +
			" area = " + Common.sqlChar(area) + "," +
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
	execSQLArray[0]=" delete UNTBU_Attachment1 where 1=1 " +
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

public UNTBU005F  queryOne() throws Exception{
	Database db = new Database();
	UNTBU005F obj = this;
	try {
		String sql=" select a.enterOrg, a.ownership, a.propertyNo, a.serialNo, a.serialNo1, a.attachment, a.purpose, a.stuff, a.area, a.notes, a.editID, a.editDate, a.editTime, a.differenceKind  "+
			" from UNTBU_Attachment1 a where 1=1 " +
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
			obj.setAttachment(rs.getString("attachment"));
			obj.setPurpose(rs.getString("purpose"));
			obj.setStuff(rs.getString("stuff"));
			obj.setArea(rs.getString("area"));
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
		String sql=" select a.enterOrg, a.ownership, a.propertyNo, a.serialNo, a.serialNo1, " +
			" a.attachment, b.codeName as attachmentName, a.purpose, a.stuff, c.codeName as stuffName, a.area "+
			" from UNTBU_Attachment1 a left join SYSCA_Code c on a.stuff=c.codeID and c.codeKindID='STU', SYSCA_Code b where 1=1 " +
			" and a.attachment=b.codeID and b.codeKindID='FLC' " +
			"";
			if (!"".equals(getEnterOrg()))
				sql+=" and a.enterOrg = " + Common.sqlChar(getEnterOrg()) ;
			if (!"".equals(getOwnership()))
				sql+=" and a.ownership = " + Common.sqlChar(getOwnership()) ;
			if (!"".equals(getPropertyNo()))
				sql+=" and a.propertyNo = " + Common.sqlChar(getPropertyNo()) ;
			if (!"".equals(getSerialNo()))
				sql+=" and a.serialNo = " + Common.sqlChar(getSerialNo()) ;
				
		ResultSet rs = db.querySQL(sql + " order by serialNo1",true);
		processCurrentPageAttribute(rs);			
		if (getTotalRecord() > 0) {
			int count = getRecordStart();
			int end = getRecordEnd();
			do {
				if (count > end)
					break;
			String rowArray[]=new String[9];
			rowArray[0]=rs.getString("enterOrg"); 
			rowArray[1]=rs.getString("ownership"); 
			rowArray[2]=rs.getString("propertyNo"); 
			rowArray[3]=rs.getString("serialNo"); 
			rowArray[4]=rs.getString("serialNo1"); 
			rowArray[5]=rs.getString("attachmentName"); 
			rowArray[6]=rs.getString("purpose"); 
			rowArray[7]=rs.getString("stuffName"); 
			rowArray[8]=rs.getString("area"); 
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