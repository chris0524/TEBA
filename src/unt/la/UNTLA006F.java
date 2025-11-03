
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

import java.sql.ResultSet;
import java.util.ArrayList;

import unt.ch.UNTCH_Tools;
import util.*;

public class UNTLA006F extends UNTLA001Q{

String enterOrg;
String ownership;
String caseNo;
String propertyNo;
String serialNo;
String bulletinDate;
String priceUnit;
String suitDateS;
String suitDateE;
String notes;

public String getEnterOrg(){ return checkGet(enterOrg); }
public void setEnterOrg(String s){ enterOrg=checkSet(s); }
public String getOwnership(){ return checkGet(ownership); }
public void setOwnership(String s){ ownership=checkSet(s); }
public String getCaseNo(){ return checkGet(caseNo); }
public void setCaseNo(String s){ caseNo=checkSet(s); }
public String getPropertyNo(){ return checkGet(propertyNo); }
public void setPropertyNo(String s){ propertyNo=checkSet(s); }
public String getSerialNo(){ return checkGet(serialNo); }
public void setSerialNo(String s){ serialNo=checkSet(s); }
public String getBulletinDate(){ return checkGet(bulletinDate); }
public void setBulletinDate(String s){ bulletinDate=checkSet(s); }
public String getPriceUnit(){ return checkGet(priceUnit); }
public void setPriceUnit(String s){ priceUnit=checkSet(s); }
public String getSuitDateS(){ return checkGet(suitDateS); }
public void setSuitDateS(String s){ suitDateS=checkSet(s); }
public String getSuitDateE(){ return checkGet(suitDateE); }
public void setSuitDateE(String s){ suitDateE=checkSet(s); }
public String getNotes(){ return checkGet(notes); }
public void setNotes(String s){ notes=checkSet(s); }

String differenceKind;
public String getDifferenceKind() {return checkGet(differenceKind);}
public void setDifferenceKind(String differenceKind) {this.differenceKind = checkSet(differenceKind);}



//傳回執行insert前之檢查sql
protected String[][] getInsertCheckSQL(){
	String[][] checkSQLArray = new String[2][4];
 	checkSQLArray[0][0]=" select count(*) as checkResult from UNTLA_Price where 1=1 " + 
		" and enterOrg = " + Common.sqlChar(enterOrg) + 
		" and ownership = " + Common.sqlChar(ownership) + 
		" and propertyNo = " + Common.sqlChar(propertyNo) + 
		" and serialNo = " + Common.sqlChar(serialNo) + 
		" and bulletinDate = " + Common.sqlChar(new UNTCH_Tools()._transToCE_Year(bulletinDate)) + 
		" and differenceKind = " + Common.sqlChar(differenceKind) +		
		"";
	checkSQLArray[0][1]=">";
	checkSQLArray[0][2]="0";
	checkSQLArray[0][3]="該筆資料己重複，請重新輸入！";
	
	//公告年月檢查
 	checkSQLArray[1][0]=" select count(*) as checkResult from UNTLA_BulletinDate where 1=1 " + 
		" and bulletinKind = '2'"+  
		" and bulletinDate = " + Common.sqlChar(new UNTCH_Tools()._transToCE_Year(bulletinDate)) +  	
		""; 	
	checkSQLArray[1][1]="<=";
	checkSQLArray[1][2]="0";
	checkSQLArray[1][3]="公告年月錯誤，請重新輸入！";	
	return checkSQLArray;	
	
	
}


//傳回insert sql
protected String[] getInsertSQL(){
	//取得適用期間
	Database db = new Database();
	ResultSet rs;	
	String sql="select suitDateS,suitDateE from UNTLA_BulletinDate " +
		" where bulletinKind = '2'"+  
		" and bulletinDate = " + Common.sqlChar(new UNTCH_Tools()._transToCE_Year(bulletinDate)) +  	
		"";		
	try {		
		rs = db.querySQL(sql);
		if (rs.next()){
		    setSuitDateS(new UNTCH_Tools()._transToROC_Year(rs.getString("suitDateS")));
		    setSuitDateE(new UNTCH_Tools()._transToROC_Year(rs.getString("suitDateE")));
		}
	} catch (Exception e) {
		e.printStackTrace();
	} finally {
		db.closeAll();
	}      
	String[] execSQLArray = new String[1];
	execSQLArray[0]=" insert into UNTLA_Price(" +
			" enterOrg,"+
			" ownership,"+
			" propertyNo,"+
			" serialNo,"+
			" bulletinDate,"+
			" priceUnit,"+
			" suitDateS,"+
			" suitDateE,"+
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
			Common.sqlChar(new UNTCH_Tools()._transToCE_Year(bulletinDate)) + "," +
			Common.sqlChar(priceUnit) + "," +
			Common.sqlChar(new UNTCH_Tools()._transToCE_Year(suitDateS)) + "," +
			Common.sqlChar(new UNTCH_Tools()._transToCE_Year(suitDateE)) + "," +
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
	execSQLArray[0]=" update UNTLA_Price set " +
			" priceUnit = " + Common.sqlChar(priceUnit) + "," +
			" suitDateS = " + Common.sqlChar(new UNTCH_Tools()._transToCE_Year(suitDateS)) + "," +
			" suitDateE = " + Common.sqlChar(new UNTCH_Tools()._transToCE_Year(suitDateE)) + "," +
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
			" and bulletinDate = " + Common.sqlChar(new UNTCH_Tools()._transToCE_Year(bulletinDate)) +
			"";
	return execSQLArray;
}


//傳回delete sql
protected String[] getDeleteSQL(){
	String[] execSQLArray = new String[1];
	execSQLArray[0]=" delete UNTLA_Price where 1=1 " +
			" and enterOrg = " + Common.sqlChar(enterOrg) +
			" and ownership = " + Common.sqlChar(ownership) +
			" and propertyNo = " + Common.sqlChar(propertyNo) +
			" and serialNo = " + Common.sqlChar(serialNo) +
			" and differenceKind = " + Common.sqlChar(differenceKind) +
			" and bulletinDate = " + Common.sqlChar(new UNTCH_Tools()._transToCE_Year(bulletinDate)) +
			"";
	return execSQLArray;
}


/**
 * <br>
 * <br>目的：依主鍵查詢單一資料
 * <br>參數：無
 * <br>傳回：傳回本物件
*/

public UNTLA006F  queryOne() throws Exception{
	Database db = new Database();
	UNTLA006F obj = this;
	try {
		String sql=" select a.enterOrg, a.ownership, a.propertyNo, a.serialNo, a.bulletinDate, a.priceUnit, a.suitDateS, a.suitDateE, a.notes, a.editID, a.editDate, a.editTime, a.differenceKind "+
			" from UNTLA_Price a where 1=1 " +
			" and a.enterOrg = " + Common.sqlChar(enterOrg) +
			" and a.ownership = " + Common.sqlChar(ownership) +
			" and a.propertyNo = " + Common.sqlChar(propertyNo) +
			" and a.serialNo = " + Common.sqlChar(serialNo) +
			" and differenceKind = " + Common.sqlChar(differenceKind) +
			" and a.bulletinDate = " + Common.sqlChar(new UNTCH_Tools()._transToCE_Year(bulletinDate)) +
			"";
		ResultSet rs = db.querySQL(sql);
		if (rs.next()){
			obj.setEnterOrg(rs.getString("enterOrg"));
			obj.setOwnership(rs.getString("ownership"));
			obj.setPropertyNo(rs.getString("propertyNo"));
			obj.setSerialNo(rs.getString("serialNo"));
			obj.setBulletinDate(new UNTCH_Tools()._transToROC_Year(rs.getString("bulletinDate")));
			obj.setPriceUnit(rs.getString("priceUnit"));
			obj.setSuitDateS(new UNTCH_Tools()._transToROC_Year(rs.getString("suitDateS")));
			obj.setSuitDateE(new UNTCH_Tools()._transToROC_Year(rs.getString("suitDateE")));
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
		String sql=" select a.enterOrg, a.ownership, a.propertyNo, a.serialNo, a.bulletinDate, a.priceUnit, a.suitDateS, a.suitDateE "+
			" from UNTLA_Price a where 1=1 "+ 
			" and a.enterOrg = " + Common.sqlChar(enterOrg) +
			" and a.ownership = " + Common.sqlChar(ownership) +
			" and a.propertyNo = " + Common.sqlChar(propertyNo) +
			" and a.serialNo = " + Common.sqlChar(serialNo) +
			" and a.differenceKind = " + Common.sqlChar(differenceKind) ;
		UNTCH_Tools ul = new UNTCH_Tools();
		ResultSet rs = db.querySQL(sql,true);
		processCurrentPageAttribute(rs);			
		if (getTotalRecord() > 0) {
			int count = getRecordStart();
			int end = getRecordEnd();
			do {
				if (count > end)
					break;
			String rowArray[]=new String[8];
			rowArray[0]=rs.getString("enterOrg"); 
			rowArray[1]=rs.getString("ownership"); 
			rowArray[2]=rs.getString("propertyNo"); 
			rowArray[3]=rs.getString("serialNo"); 
			rowArray[4]=ul._transToROC_Year(rs.getString("bulletinDate")); 
			rowArray[5]=rs.getString("priceUnit"); 
			rowArray[6]=ul._transToROC_Year(rs.getString("suitDateS")); 
			rowArray[7]=ul._transToROC_Year(rs.getString("suitDateE")); 
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

