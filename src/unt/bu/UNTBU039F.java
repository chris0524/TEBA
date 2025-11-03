/*
*<br>程式目的：建物主檔資料維護－評定現值
*<br>程式代號：untbu039f
*<br>程式日期：0980624
*<br>程式作者：Timtoy.Tsai
*<br>--------------------------------------------------------
*<br>修改作者　　修改日期　　　修改目的
*<br>--------------------------------------------------------
*/

package unt.bu;

import java.sql.ResultSet;
import java.util.ArrayList;

import unt.ch.UNTCH_Tools;
import util.Common;
import util.Database;

public class UNTBU039F extends UNTBU001Q{

String enterOrg;
String ownership;
String propertyNo;
String serialNo;
String judgmentDate;
String judgmentValue;
String notes;


public String getEnterOrg(){ return checkGet(enterOrg); }
public void setEnterOrg(String s){ enterOrg=checkSet(s); }
public String getOwnership(){ return checkGet(ownership); }
public void setOwnership(String s){ ownership=checkSet(s); }
public String getPropertyNo(){ return checkGet(propertyNo); }
public void setPropertyNo(String s){ propertyNo=checkSet(s); }
public String getSerialNo(){ return checkGet(serialNo); }
public void setSerialNo(String s){ serialNo=checkSet(s); }
public String getJudgmentDate(){ return checkGet(judgmentDate); }
public void setJudgmentDate(String s){ judgmentDate=checkSet(s); }
public String getJudgmentValue(){ return checkGet(judgmentValue); }
public void setJudgmentValue(String s){ judgmentValue=checkSet(s); }
public String getNotes(){ return checkGet(notes); }
public void setNotes(String s){ notes=checkSet(s); }


String differenceKind;
public String getDifferenceKind() {return checkGet(differenceKind);}
public void setDifferenceKind(String differenceKind) {this.differenceKind = checkSet(differenceKind);}



//傳回執行insert前之檢查sql
protected String[][] getInsertCheckSQL(){
	//取得自動編號
	Database db = new Database();
	ResultSet rs;	
	String sql="select max(serialNo1)+1 as serialNo1 from UNTBU_Base " +
		" where enterOrg = " + Common.sqlChar(enterOrg) +
		" and ownership = " + Common.sqlChar(ownership) +
		" and propertyNo = " + Common.sqlChar(propertyNo)+
		" and serialNo = " + Common.sqlChar(serialNo)+
		" and differenceKind = " + Common.sqlChar(differenceKind);	

	
	String[][] checkSQLArray = new String[1][4];
 	checkSQLArray[0][0]=" select count(*) as checkResult from UNTBU_Price where 1=1 " + 
		" and enterOrg = " + Common.sqlChar(enterOrg) + 
		" and ownership = " + Common.sqlChar(ownership) + 
		" and propertyNo = " + Common.sqlChar(propertyNo) + 
		" and serialNo = " + Common.sqlChar(serialNo) + 
		" and judgmentDate = " + Common.sqlChar(new UNTCH_Tools()._transToCE_Year(judgmentDate)) +
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
	execSQLArray[0]=" insert into UNTBU_Price(" +
			" enterOrg,"+
			" ownership,"+
			" propertyNo,"+
			" serialNo,"+
			" judgmentDate,"+
			" judgmentValue,"+
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
			Common.sqlChar(new UNTCH_Tools()._transToCE_Year(judgmentDate)) + "," +
			Common.sqlChar(judgmentValue) + "," +
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
	execSQLArray[0]=" update UNTBU_Price set " +
			" judgmentValue = " + Common.sqlChar(judgmentValue) + "," +
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
			" and differenceKind = " + Common.sqlChar(differenceKind) +
			" and judgmentDate = " + Common.sqlChar(new UNTCH_Tools()._transToCE_Year(judgmentDate)) +
			"";
	return execSQLArray;
}


//傳回delete sql
protected String[] getDeleteSQL(){
	String[] execSQLArray = new String[1];
	execSQLArray[0]=" delete UNTBU_Price where 1=1 " +
			" and enterOrg = " + Common.sqlChar(enterOrg) +
			" and ownership = " + Common.sqlChar(ownership) +
			" and propertyNo = " + Common.sqlChar(propertyNo) +
			" and serialNo = " + Common.sqlChar(serialNo) +
			" and differenceKind = " + Common.sqlChar(differenceKind) +
			" and judgmentDate = " + Common.sqlChar(new UNTCH_Tools()._transToCE_Year(judgmentDate)) +
			"";
	return execSQLArray;
}


/**
 * <br>
 * <br>目的：依主鍵查詢單一資料
 * <br>參數：無
 * <br>傳回：傳回本物件
*/

public UNTBU039F  queryOne() throws Exception{
	Database db = new Database();
	UNTBU039F obj = this;
	try {
		String sql=" select a.enterOrg, a.ownership, a.propertyNo, a.serialNo, a.judgmentDate, a.judgmentValue, a.notes, a.editTime, a.editID, a.editDate, a.editTime, a.differenceKind "+
			" from UNTBU_Price a where 1=1 " +
			" and a.enterOrg = " + Common.sqlChar(enterOrg) +
			" and a.ownership = " + Common.sqlChar(ownership) +
			" and a.propertyNo = " + Common.sqlChar(propertyNo) +
			" and a.serialNo = " + Common.sqlChar(serialNo) +
			" and a.judgmentDate = " + Common.sqlChar(new UNTCH_Tools()._transToCE_Year(judgmentDate)) +
			" and a.differenceKind = " + Common.sqlChar(differenceKind) +			
			"";
		ResultSet rs = db.querySQL(sql);
		if (rs.next()){
			obj.setEnterOrg(rs.getString("enterOrg"));
			obj.setOwnership(rs.getString("ownership"));
			obj.setPropertyNo(rs.getString("propertyNo"));
			obj.setSerialNo(rs.getString("serialNo"));
			obj.setJudgmentDate(new UNTCH_Tools()._transToROC_Year(rs.getString("judgmentDate")));
			obj.setJudgmentValue(rs.getString("judgmentValue"));
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
		String sql=" select a.enterOrg, a.ownership, a.propertyNo, a.serialNo, a.judgmentDate, a.judgmentValue "+
			" from UNTBU_Price a where 1=1 "; 
		
		if (!"".equals(getEnterOrg()))
			sql+=" and a.enterOrg = " + Common.sqlChar(getEnterOrg()) ;
		if (!"".equals(getOwnership()))
			sql+=" and a.ownership = " + Common.sqlChar(getOwnership()) ;
		if (!"".equals(getPropertyNo()))
			sql+=" and a.propertyNo = " + Common.sqlChar(getPropertyNo()) ;
		if (!"".equals(getSerialNo()))
			sql+=" and a.serialNo = " + Common.sqlChar(getSerialNo()) ;
		
		ResultSet rs = db.querySQL(sql,true);
		processCurrentPageAttribute(rs);			
		if (getTotalRecord() > 0) {
			int count = getRecordStart();
			int end = getRecordEnd();
			do {
				if (count > end)
					break;
			String rowArray[]=new String[6];
			rowArray[0]=Common.get(rs.getString("enterOrg")); 
			rowArray[1]=Common.get(rs.getString("ownership")); 
			rowArray[2]=Common.get(rs.getString("propertyNo")); 
			rowArray[3]=Common.get(rs.getString("serialNo")); 
			rowArray[4]=new UNTCH_Tools()._transToROC_Year(Common.get(rs.getString("judgmentDate"))); 
			rowArray[5]=Common.get(rs.getString("judgmentValue")); 
			
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


