
/*
*<br>程式目的：土地主檔資料維護－公告現值
*<br>程式代號：untla006f
*<br>程式日期：0940717
*<br>程式作者：griffin
*<br>--------------------------------------------------------
*<br>修改作者　　修改日期　　　修改目的
*<br>clive.chang 0941219	Debug & Modify for Testing and autual running..
*<br>--------------------------------------------------------
*/

package unt.la;

import java.io.File;
import java.sql.ResultSet;
import java.util.ArrayList;
import util.*;

public class UNTLA007F extends UNTLA001Q{

String enterOrg;
String ownership;
String propertyNo;
String serialNo;
String serialNo1;
String viewDescribe;
String viewDate;
String viewPicture;
String viewPicture1;
String viewPicture2;
String notes;
String filestoreLocation;

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
public String getViewDescribe(){ return checkGet(viewDescribe); }
public void setViewDescribe(String s){ viewDescribe=checkSet(s); }
public String getViewDate(){ return checkGet(viewDate); }
public void setViewDate(String s){ viewDate=checkSet(s); }
public String getViewPicture(){ return checkGet(viewPicture); }
public void setViewPicture(String s){ viewPicture=checkSet(s); }
public String getViewPicture1(){ return checkGet(viewPicture1); }
public void setViewPicture1(String s){ viewPicture1=checkSet(s); }
public String getViewPicture2(){ return checkGet(viewPicture2); }
public void setViewPicture2(String s){ viewPicture2=checkSet(s); }
public String getNotes(){ return checkGet(notes); }
public void setNotes(String s){ notes=checkSet(s); }
public String getFilestoreLocation(){ return checkGet(filestoreLocation); }
public void setFilestoreLocation(String s){ filestoreLocation=checkSet(s); }

String caseNo;
public String getCaseNo(){ return checkGet(caseNo); }
public void setCaseNo(String s){ caseNo=checkSet(s); }
//傳回執行insert前之檢查sql
protected String[][] getInsertCheckSQL(){
	//取得勘查次序
	Database db = new Database();
	ResultSet rs;	
	String sql="select (max(serialNo1) + 1) as serialNo1 from UNTLA_ViewScene " +
		" where enterOrg = " + Common.sqlChar(enterOrg) +
		" and ownership = " + Common.sqlChar(ownership) +
		" and propertyNo = " + Common.sqlChar(propertyNo) + 
		" and serialNo = " + Common.sqlChar(serialNo) + 
		"";		
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
	String[][] checkSQLArray = new String[1][4];
 	checkSQLArray[0][0]=" select count(*) as checkResult from UNTLA_ViewScene where 1=1 " + 
		" and enterOrg = " + Common.sqlChar(enterOrg) + 
		" and ownership = " + Common.sqlChar(ownership) + 
		" and propertyNo = " + Common.sqlChar(propertyNo) + 
		" and serialNo = " + Common.sqlChar(serialNo) + 
		" and serialNo1 = " + Common.sqlChar(serialNo1) + 
		"";
	checkSQLArray[0][1]=">";
	checkSQLArray[0][2]="0";
	checkSQLArray[0][3]="該筆資料己重複，請重新輸入！";
	
	return checkSQLArray;	
}


//傳回insert sql
protected String[] getInsertSQL(){
	String[] execSQLArray = new String[1];
	execSQLArray[0]=" insert into UNTLA_ViewScene(" +
			" enterOrg,"+
			" ownership,"+
			" propertyNo,"+
			" serialNo,"+
			" serialNo1,"+
			" viewDescribe,"+
			" viewDate,"+
			" viewPicture,"+
			" viewPicture1,"+
			" viewPicture2,"+			
			" notes,"+
			" editID,"+
			" editDate,"+
			" editTime"+
		" )Values(" +
			Common.sqlChar(enterOrg) + "," +
			Common.sqlChar(ownership) + "," +
			Common.sqlChar(propertyNo) + "," +
			Common.sqlChar(serialNo) + "," +
			Common.sqlChar(serialNo1) + "," +
			Common.sqlChar(viewDescribe) + "," +			
			Common.sqlChar(viewDate) + "," +
			Common.sqlChar(viewPicture) + "," +
			Common.sqlChar(viewPicture1) + "," +
			Common.sqlChar(viewPicture2) + "," +
			Common.sqlChar(notes) + "," +
			Common.sqlChar(getEditID()) + "," +
			Common.sqlChar(getEditDate()) + "," +
			Common.sqlChar(getEditTime()) + ")" ;
	return execSQLArray;
}


//傳回update sql
protected String[] getUpdateSQL(){
	String[] execSQLArray = new String[1];
	execSQLArray[0]=" update UNTLA_ViewScene set " +
			" viewDescribe = " + Common.sqlChar(viewDescribe) + "," +
			" viewDate = " + Common.sqlChar(viewDate) + "," +
			" viewPicture = " + Common.sqlChar(viewPicture) + "," +
			" viewPicture1 = " + Common.sqlChar(viewPicture1) + "," +
			" viewPicture2 = " + Common.sqlChar(viewPicture2) + "," +			
			" notes = " + Common.sqlChar(notes) + "," +
			" editID = " + Common.sqlChar(getEditID()) + "," +
			" editDate = " + Common.sqlChar(getEditDate()) + "," +
			" editTime = " + Common.sqlChar(getEditTime()) + 
		" where 1=1 " + 
			" and enterOrg = " + Common.sqlChar(enterOrg) +
			" and ownership = " + Common.sqlChar(ownership) +
			" and propertyNo = " + Common.sqlChar(propertyNo) +
			" and serialNo = " + Common.sqlChar(serialNo) +
			" and serialNo1 = " + Common.sqlChar(serialNo1) +
			"";
	return execSQLArray;
}


//傳回delete sql
protected String[] getDeleteSQL(){
	String[] execSQLArray = new String[1];
	execSQLArray[0]=" delete UNTLA_ViewScene where 1=1 " +
			" and enterOrg = " + Common.sqlChar(enterOrg) +
			" and ownership = " + Common.sqlChar(ownership) +
			" and propertyNo = " + Common.sqlChar(propertyNo) +
			" and serialNo = " + Common.sqlChar(serialNo) +
			" and serialNo1 = " + Common.sqlChar(serialNo1) +
			"";
	//刪除檔案
	String[] arrFileName=null;
	if (!"".equals(Common.get(viewPicture))) {
		arrFileName=viewPicture.split(":;:");
		if (arrFileName.length>1) Common.RemoveDirectory(new File(filestoreLocation+File.separator+arrFileName[0]));
	}
	if (!"".equals(Common.get(viewPicture1))) {
		arrFileName=viewPicture1.split(":;:");
		if (arrFileName.length>1) Common.RemoveDirectory(new File(filestoreLocation+File.separator+arrFileName[0]));
	}	
	if (!"".equals(Common.get(viewPicture2))) {
		arrFileName=viewPicture2.split(":;:");
		if (arrFileName.length>1) Common.RemoveDirectory(new File(filestoreLocation+File.separator+arrFileName[0]));
	}	
	return execSQLArray;
}


/**
 * <br>
 * <br>目的：依主鍵查詢單一資料
 * <br>參數：無
 * <br>傳回：傳回本物件
*/

public UNTLA007F  queryOne() throws Exception{
	Database db = new Database();
	UNTLA007F obj = this;
	try {
		String sql=" select a.enterOrg, a.ownership, a.propertyNo, a.serialNo,a.serialNo1, a.viewDate, a.viewDescribe, a.viewPicture,  a.viewPicture1, a.viewPicture2, a.notes, a.editID, a.editDate, a.editTime  "+
			" from UNTLA_ViewScene a where 1=1 " +
			" and a.enterOrg = " + Common.sqlChar(enterOrg) +
			" and a.ownership = " + Common.sqlChar(ownership) +
			" and a.propertyNo = " + Common.sqlChar(propertyNo) +
			" and a.serialNo = " + Common.sqlChar(serialNo) +
			" and serialNo1 = " + Common.sqlChar(serialNo1) +
			"";
		ResultSet rs = db.querySQL(sql);
		if (rs.next()){
			obj.setEnterOrg(rs.getString("enterOrg"));
			obj.setOwnership(rs.getString("ownership"));
			obj.setPropertyNo(rs.getString("propertyNo"));
			obj.setSerialNo(rs.getString("serialNo"));
			obj.setSerialNo1(rs.getString("serialNo1"));
			obj.setViewDate(rs.getString("viewDate"));
			obj.setViewDescribe(rs.getString("viewDescribe"));
			obj.setViewPicture(rs.getString("viewPicture"));
			obj.setViewPicture1(rs.getString("viewPicture1"));
			obj.setViewPicture2(rs.getString("viewPicture2"));			
			obj.setNotes(rs.getString("notes"));
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
	int counter=0;
	try {
		String sql=" select a.enterOrg, a.ownership, a.propertyNo, a.serialNo, a.serialNo1, a.viewDate, a.viewDescribe "+
			" from UNTLA_ViewScene a where 1=1 "+ 
			" and a.enterOrg = " + Common.sqlChar(enterOrg) +
			" and a.ownership = " + Common.sqlChar(ownership) +
			" and a.propertyNo = " + Common.sqlChar(propertyNo) +
			" and a.serialNo = " + Common.sqlChar(serialNo) ;			
		ResultSet rs = db.querySQL(sql);
		while (rs.next()){
			counter++;
			String rowArray[]=new String[7];
			rowArray[0]=rs.getString("enterOrg"); 
			rowArray[1]=rs.getString("ownership"); 
			rowArray[2]=rs.getString("propertyNo"); 
			rowArray[3]=rs.getString("serialNo"); 
			rowArray[4]=rs.getString("serialNo1"); 			
			rowArray[5]=rs.getString("viewDescribe"); 
			rowArray[6]=rs.getString("viewDate"); 		
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

}

