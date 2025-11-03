/*
*<br>程式目的：公用財產異動計畫資料維護
*<br>程式代號：UNTGR002F
*<br>程式日期：0950302
*<br>程式作者：Cherry
*<br>--------------------------------------------------------
*<br>修改作者　　修改日期　　　修改目的
*<br>--------------------------------------------------------
*/

package unt.gr;

import java.sql.ResultSet;
import java.util.ArrayList;
import util.*;

public class UNTGR002F extends SuperBean{


String enterOrg;
String enterOrgName;
String planYear;
String dataType;
String dataUnit;
String addAmount;
String addValue;
String reduceAmount;
String reduceValue;
String notes;

String q_enterOrg;
String q_enterOrgName;
String q_planYear;

public String getEnterOrg(){ return checkGet(enterOrg); }
public void setEnterOrg(String s){ enterOrg=checkSet(s); }
public String getEnterOrgName(){ return checkGet(enterOrgName); }
public void setEnterOrgName(String s){ enterOrgName=checkSet(s); }
public String getPlanYear(){ return checkGet(planYear); }
public void setPlanYear(String s){ planYear=checkSet(s); }
public String getDataType(){ return checkGet(dataType); }
public void setDataType(String s){ dataType=checkSet(s); }
public String getDataUnit(){ return checkGet(dataUnit); }
public void setDataUnit(String s){ dataUnit=checkSet(s); }
public String getAddAmount(){ return checkGet(addAmount); }
public void setAddAmount(String s){ addAmount=checkSet(s); }
public String getAddValue(){ return checkGet(addValue); }
public void setAddValue(String s){ addValue=checkSet(s); }
public String getReduceAmount(){ return checkGet(reduceAmount); }
public void setReduceAmount(String s){ reduceAmount=checkSet(s); }
public String getReduceValue(){ return checkGet(reduceValue); }
public void setReduceValue(String s){ reduceValue=checkSet(s); }
public String getNotes(){ return checkGet(notes); }
public void setNotes(String s){ notes=checkSet(s); }

public String getQ_enterOrg(){ return checkGet(q_enterOrg); }
public void setQ_enterOrg(String s){ q_enterOrg=checkSet(s); }
public String getQ_enterOrgName(){ return checkGet(q_enterOrgName); }
public void setQ_enterOrgName(String s){ q_enterOrgName=checkSet(s); }
public String getQ_planYear(){ return checkGet(q_planYear); }
public void setQ_planYear(String s){ q_planYear=checkSet(s); }

String isOrganManager;
String isAdminManager;
String organID;    
public String getOrganID() { return checkGet(organID); }
public void setOrganID(String s) { organID = checkSet(s); }
public String getIsOrganManager() { return checkGet(isOrganManager); }
public void setIsOrganManager(String s) { isOrganManager = checkSet(s); }
public String getIsAdminManager() { return checkGet(isAdminManager); }
public void setIsAdminManager(String s) { isAdminManager = checkSet(s); }    

//傳回執行insert前之檢查sql
protected String[][] getInsertCheckSQL(){
	String[][] checkSQLArray = new String[1][4];
 	checkSQLArray[0][0]=" select count(*) as checkResult from UNTGR_Plan where 1=1 " + 
		" and enterOrg = " + Common.sqlChar(enterOrg) + 
		" and planYear = " + Common.sqlChar(planYear) + 
		" and dataType = " + Common.sqlChar(dataType) + 
		"";
	checkSQLArray[0][1]=">";
	checkSQLArray[0][2]="0";
	checkSQLArray[0][3]="該筆資料己重複，請重新輸入！";
	return checkSQLArray;
}


//傳回insert sql
protected String[] getInsertSQL(){
	String[] execSQLArray = new String[1];
	execSQLArray[0]=" insert into UNTGR_Plan(" +
			" enterOrg,"+
			" planYear,"+
			" dataType,"+
			" dataUnit,"+
			" addAmount,"+
			" addValue,"+
			" reduceAmount,"+
			" reduceValue,"+
			" notes,"+
			" editID,"+
			" editDate,"+
			" editTime"+
		" )Values(" +
			Common.sqlChar(enterOrg) + "," +
			Common.sqlChar(planYear) + "," +
			Common.sqlChar(dataType) + "," +
			Common.sqlChar(dataUnit) + "," +
			Common.sqlChar(addAmount) + "," +
			Common.sqlChar(addValue) + "," +
			Common.sqlChar(reduceAmount) + "," +
			Common.sqlChar(reduceValue) + "," +
			Common.sqlChar(notes) + "," +
			Common.sqlChar(getEditID()) + "," +
			Common.sqlChar(getEditDate()) + "," +
			Common.sqlChar(getEditTime()) + ")" ;
	return execSQLArray;
}


//傳回update sql
protected String[] getUpdateSQL(){
	String[] execSQLArray = new String[1];
	execSQLArray[0]=" update UNTGR_Plan set " +
			" dataUnit = " + Common.sqlChar(dataUnit) + "," +
			" addAmount = " + Common.sqlChar(addAmount) + "," +
			" addValue = " + Common.sqlChar(addValue) + "," +
			" reduceAmount = " + Common.sqlChar(reduceAmount) + "," +
			" reduceValue = " + Common.sqlChar(reduceValue) + "," +
			" notes = " + Common.sqlChar(notes) + "," +
			" editID = " + Common.sqlChar(getEditID()) + "," +
			" editDate = " + Common.sqlChar(getEditDate()) + "," +
			" editTime = " + Common.sqlChar(getEditTime()) + 
		" where 1=1 " + 
			" and enterOrg = " + Common.sqlChar(enterOrg) +
			" and planYear = " + Common.sqlChar(planYear) +
			" and dataType = " + Common.sqlChar(dataType) +
			"";
	return execSQLArray;
}


//傳回delete sql
protected String[] getDeleteSQL(){
	String[] execSQLArray = new String[1];
	execSQLArray[0]=" delete UNTGR_Plan where 1=1 " +
			" and enterOrg = " + Common.sqlChar(enterOrg) +
			" and planYear = " + Common.sqlChar(planYear) +
			" and dataType = " + Common.sqlChar(dataType) +
			"";
	return execSQLArray;
}


/**
 * <br>
 * <br>目的：依主鍵查詢單一資料
 * <br>參數：無
 * <br>傳回：傳回本物件
*/

public UNTGR002F  queryOne() throws Exception{
	Database db = new Database();
	UNTGR002F obj = this;
	try {
		String sql=" select b.organSName, a.enterOrg, a.planYear, a.dataType, a.dataUnit, a.addAmount, a.addValue, a.reduceAmount, a.reduceValue, a.notes, a.editID, a.editDate, a.editTime  "+
			" from UNTGR_Plan a, SYSCA_Organ b where 1=1 " +
			" and a.enterOrg = " + Common.sqlChar(enterOrg) +
			" and a.planYear = " + Common.sqlChar(planYear) +
			" and a.dataType = " + Common.sqlChar(dataType) +
			" and b.organID = a.enterOrg" +
			"";
		ResultSet rs = db.querySQL(sql);
		if (rs.next()){
			obj.setEnterOrg(rs.getString("enterOrg"));
			obj.setEnterOrgName(rs.getString("organSName"));
			obj.setPlanYear(rs.getString("planYear"));
			obj.setDataType(rs.getString("dataType"));
			obj.setDataUnit(rs.getString("dataUnit"));
			obj.setAddAmount(rs.getString("addAmount"));
			obj.setAddValue(rs.getString("addValue"));
			obj.setReduceAmount(rs.getString("reduceAmount"));
			obj.setReduceValue(rs.getString("reduceValue"));
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
		String sql=" select b.organSName, a.enterOrg, a.planYear, " +
				" a.dataType, " +
				" decode( a.dataType,'1','土地購置','2','房屋建築','3','其他建築','4','機械設備','5','交通及運輸設備','6','資訊設備','7','其他設備','8','投資及其他','')as dataTypeName, " +
				" a.dataUnit, a.addAmount, a.addValue, a.reduceAmount, a.reduceValue "+
				" from UNTGR_Plan a, SYSCA_Organ b where 1=1 "+
				" and b.organID = a.enterOrg" +
				""; 
			if (!"".equals(getQ_enterOrg())) {
				sql+=" and a.enterOrg = " + Common.sqlChar(getQ_enterOrg()) ;
			} else {
				if (!getIsAdminManager().equalsIgnoreCase("Y")) {
					if ("Y".equals(getIsOrganManager())) {					
						//sql += " and a.enterOrg like '" + getOrganID().substring(0,5) + "%' ";
						sql += " and( a.enterOrg = " + Common.sqlChar(getOrganID()) ;
						sql += " or organID in (select organID from SYSCA_Organ where organSuperior='"+ Common.sqlChar(getOrganID())+"'))";
					} else {
						sql+=" and a.enterOrg = " + Common.sqlChar(getOrganID()) ;
					}
				}
			}
			//System.out.println("==sql==" + sql);
			if (!"".equals(getQ_planYear()))
				sql+=" and a.planYear = " + Common.sqlChar(getQ_planYear()) ;
		ResultSet rs = db.querySQL(sql);
		while (rs.next()){
			counter++;
			String rowArray[]=new String[9];
			rowArray[0]=rs.getString("organSName");
			rowArray[1]=rs.getString("enterOrg"); 
			rowArray[2]=rs.getString("planYear"); 
			rowArray[3]=rs.getString("dataType"); 
			rowArray[4]=rs.getString("dataTypeName");
			rowArray[5]=rs.getString("addAmount"); 
			rowArray[6]=rs.getString("addValue"); 
			rowArray[7]=rs.getString("reduceAmount"); 
			rowArray[8]=rs.getString("reduceValue");
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


