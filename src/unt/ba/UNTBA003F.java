
/*
*<br>程式目的：保管單位資料維護
*<br>程式代號：untba003f
*<br>程式日期：0940626
*<br>程式作者：griffin
*<br>--------------------------------------------------------
*<br>修改作者　　修改日期　　　修改目的
*<br>--------------------------------------------------------
*/

package unt.ba;

import java.sql.ResultSet;
import java.util.ArrayList;
import util.*;

public class UNTBA003F extends SuperBean{


String enterOrg;
String enterOrgName;
String unitNo;
String unitName;
String notes;

String q_unitNo;
String q_unitName;

public String getEnterOrg(){ return checkGet(enterOrg); }
public void setEnterOrg(String s){ enterOrg=checkSet(s); }
public String getEnterOrgName(){ return checkGet(enterOrgName); }
public void setEnterOrgName(String s){ enterOrgName=checkSet(s); }
public String getUnitNo(){ return checkGet(unitNo); }
public void setUnitNo(String s){ unitNo=checkSet(s); }
public String getUnitName(){ return checkGet(unitName); }
public void setUnitName(String s){ unitName=checkSet(s); }
public String getNotes(){ return checkGet(notes); }
public void setNotes(String s){ notes=checkSet(s); }

public String getQ_unitNo(){ return checkGet(q_unitNo); }
public void setQ_unitNo(String s){ q_unitNo=checkSet(s); }
public String getQ_unitName(){ return checkGet(q_unitName); }
public void setQ_unitName(String s){ q_unitName=checkSet(s); }


//傳回執行insert前之檢查sql
protected String[][] getInsertCheckSQL(){
	String[][] checkSQLArray = new String[1][4];
 	checkSQLArray[0][0]=" select count(*) as checkResult from UNTMP_KeepUnit where 1=1 " + 
		" and enterOrg = " + Common.sqlChar(enterOrg) + 
		" and unitNo = " + Common.sqlChar(unitNo) + 
		"";
	checkSQLArray[0][1]=">";
	checkSQLArray[0][2]="0";
	checkSQLArray[0][3]="該筆資料己重複，請重新輸入！";
	return checkSQLArray;
}


//傳回insert sql
protected String[] getInsertSQL(){
	String[] execSQLArray = new String[1];
	execSQLArray[0]=" insert into UNTMP_KeepUnit(" +
			" enterOrg,"+
			" unitNo,"+
			" unitName,"+
			" notes,"+
			" editID,"+
			" editDate,"+
			" editTime"+
		" )Values(" +
			Common.sqlChar(enterOrg) + "," +
			Common.sqlChar(unitNo) + "," +
			Common.sqlChar(unitName) + "," +
			Common.sqlChar(notes) + "," +
			Common.sqlChar(getEditID()) + "," +
			Common.sqlChar(getEditDate()) + "," +
			Common.sqlChar(getEditTime()) + ")" ;
	return execSQLArray;
}


//傳回update sql
protected String[] getUpdateSQL(){
	String[] execSQLArray = new String[1];
	execSQLArray[0]=" update UNTMP_KeepUnit set " +
			" unitName = " + Common.sqlChar(unitName) + "," +
			" notes = " + Common.sqlChar(notes) + "," +
			" editID = " + Common.sqlChar(getEditID()) + "," +
			" editDate = " + Common.sqlChar(getEditDate()) + "," +
			" editTime = " + Common.sqlChar(getEditTime()) + 
		" where 1=1 " + 
			" and enterOrg = " + Common.sqlChar(enterOrg) +
			" and unitNo = " + Common.sqlChar(unitNo) +
			"";
	return execSQLArray;
}


//傳回delete sql
protected String[] getDeleteSQL(){
	String[] execSQLArray = new String[1];
	execSQLArray[0]=" delete UNTMP_KeepUnit where 1=1 " +
			" and enterOrg = " + Common.sqlChar(enterOrg) +
			" and unitNo = " + Common.sqlChar(unitNo) +
			"";
	return execSQLArray;
}


/**
 * <br>
 * <br>目的：依主鍵查詢單一資料
 * <br>參數：無
 * <br>傳回：傳回本物件
*/

public UNTBA003F  queryOne() throws Exception{
	Database db = new Database();
	UNTBA003F obj = this;
	try {
		String sql=" select a.enterOrg, a.unitNo, a.unitName, a.notes, a.editID, a.editDate, a.editTime  "+
			" from UNTMP_KeepUnit a where 1=1 " +
			" and a.enterOrg = " + Common.sqlChar(enterOrg) +
			" and a.unitNo = " + Common.sqlChar(unitNo) +
			"";
		ResultSet rs = db.querySQL(sql);
		if (rs.next()){
			obj.setEnterOrg(rs.getString("enterOrg"));
			obj.setUnitNo(rs.getString("unitNo"));
			obj.setUnitName(rs.getString("unitName"));
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
		String sql=" select a.enterOrg, a.unitNo, a.unitName "+
			" from UNTMP_KeepUnit a where 1=1 and a.enterOrg = " + Common.sqlChar(getEnterOrg());  
			if (!"".equals(getQ_unitNo()))
				sql+=" and a.unitNo like '" + getQ_unitNo() + "%' ";
			if (!"".equals(getQ_unitName()))
				sql+=" and a.unitName like '" + getQ_unitName() + "%' ";
		ResultSet rs = db.querySQL(sql);
		while (rs.next()){
			counter++;
			String rowArray[]=new String[3];
			rowArray[0]=rs.getString("enterOrg"); 
			rowArray[1]=rs.getString("unitNo"); 
			rowArray[2]=rs.getString("unitName"); 
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
