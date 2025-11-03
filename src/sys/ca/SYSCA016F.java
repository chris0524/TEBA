/*
*<br>程式目的：折舊單位代碼維護
*<br>程式代號：
*<br>程式日期：
*<br>程式作者：
*<br>--------------------------------------------------------
*<br>修改作者　　修改日期　　　修改目的
*<br>--------------------------------------------------------
*/

package sys.ca;

import java.sql.ResultSet;
import java.util.ArrayList;

import util.*;

public class SYSCA016F extends QueryBean{

private String enterOrg;
private String enterOrgName;
private String deprUnitNo;
private String deprUnitName;
private String untdp019rNotes;
private String notes;
private String editID;
private String editDate;
private String editTime;

private String q_enterOrg;
private String q_enterOrgName;
private String q_deprUnitNo;
private String q_deprUnitName;

public String getEnterOrg() {return checkGet(enterOrg);}
public void setEnterOrg(String enterOrg) {this.enterOrg = checkSet(enterOrg);}
public String getDeprUnitNo() {return checkGet(deprUnitNo);}
public void setDeprUnitNo(String deprUnitNo) {this.deprUnitNo = checkSet(deprUnitNo);}
public String getDeprUnitName() {return checkGet(deprUnitName);}
public void setDeprUnitName(String deprUnitName) {this.deprUnitName = checkSet(deprUnitName);}
public String getUntdp019rNotes() {return checkGet(untdp019rNotes);}
public void setUntdp019rNotes(String untdp019rNotes) {this.untdp019rNotes = checkSet(untdp019rNotes);}
public String getNotes() {return checkGet(notes);}
public void setNotes(String notes) {this.notes = checkSet(notes);}
public String getEditID() {return checkGet(editID);}
public void setEditID(String editID) {this.editID = checkSet(editID);}
public String getEditDate() {return checkGet(editDate);}
public void setEditDate(String editDate) {this.editDate = checkSet(editDate);}
public String getEditTime() {return checkGet(editTime);}
public void setEditTime(String editTime) {this.editTime = checkSet(editTime);}
public String getQ_enterOrg() {return checkGet(q_enterOrg);}
public void setQ_enterOrg(String q_enterOrg) {this.q_enterOrg = checkSet(q_enterOrg);}
public String getQ_deprUnitNo() {return checkGet(q_deprUnitNo);}
public void setQ_deprUnitNo(String q_deprUnitNo) {this.q_deprUnitNo = checkSet(q_deprUnitNo);}
public String getQ_deprUnitName() {return checkGet(q_deprUnitName);}
public void setQ_deprUnitName(String q_deprUnitName) {this.q_deprUnitName = checkSet(q_deprUnitName);}

public String getEnterOrgName() {return checkGet(enterOrgName);}
public void setEnterOrgName(String enterOrgName) {this.enterOrgName = checkSet(enterOrgName);}
public String getQ_enterOrgName() {return checkGet(q_enterOrgName);}
public void setQ_enterOrgName(String q_enterOrgName) {this.q_enterOrgName = checkSet(q_enterOrgName);}

String roleid;
String organID;
public String getRoleid() {return checkGet(roleid);}
public void setRoleid(String roleid) {this.roleid = checkSet(roleid);}
public String getOrganID() {return checkGet(organID);}
public void setOrganID(String organID) {this.organID = checkSet(organID);}

private String isAdminManager;
public String getIsAdminManager() {return checkGet(isAdminManager);}
public void setIsAdminManager(String isAdminManager) {this.isAdminManager = checkSet(isAdminManager);}

//傳回執行insert前之檢查sql
protected String[][] getInsertCheckSQL(){
	String[][] checkSQLArray = new String[1][4];
 	checkSQLArray[0][0]=" select count(*) as checkResult from SYSCA_DeprUnit where 1=1 " + 
		" and enterOrg = " + Common.sqlChar(enterOrg) +
		" and deprUnitNo = " + Common.sqlChar(deprUnitNo) + 
		"";
	checkSQLArray[0][1]=">";
	checkSQLArray[0][2]="0";
	checkSQLArray[0][3]="該筆資料己重複，請重新輸入！";
	return checkSQLArray;
}


//傳回insert sql
protected String[] getInsertSQL(){
	String[] execSQLArray = new String[1];
	execSQLArray[0]=" insert into SYSCA_DeprUnit(" +
			" enterOrg," +
			" deprUnitNo," +
			" deprUnitName," +
			" untdp019rNotes," +
			" notes," +
			" editID," +
			" editDate," +
			" editTime" +
		" )Values(" +
			Common.sqlChar(enterOrg) + "," +
			Common.sqlChar(deprUnitNo) + "," +
			Common.sqlChar(deprUnitName) + "," +
			Common.sqlChar(untdp019rNotes) + "," +
			Common.sqlChar(notes) + "," +
			Common.sqlChar(getEditID()) + "," +
			Common.sqlChar(getEditDate()) + "," +
			Common.sqlChar(getEditTime()) + ")" ;
	//System.out.println("========="+enterOrg);
	return execSQLArray;
}

//傳回update sql
protected String[] getUpdateSQL(){
	String[] execSQLArray = new String[1];
	execSQLArray[0]=" update SYSCA_DeprUnit set " +
			" enterOrg = " + Common.sqlChar(enterOrg) + "," +
			" deprUnitNo = " + Common.sqlChar(deprUnitNo) + "," +
			" deprUnitName = " + Common.sqlChar(deprUnitName) + "," +
			" untdp019rNotes = " + Common.sqlChar(untdp019rNotes) + "," +
			" notes = " + Common.sqlChar(notes) + "," +
			" editID = " + Common.sqlChar(getEditID()) + "," +
			" editDate = " + Common.sqlChar(getEditDate()) + "," +
			" editTime = " + Common.sqlChar(getEditTime()) + 
		" where 1=1 " + 
			" and enterOrg = " + Common.sqlChar(enterOrg) +
			" and deprUnitNo = " + Common.sqlChar(deprUnitNo) + 
			"";
	return execSQLArray;
}


//傳回delete sql
protected String[] getDeleteSQL(){
	String[] execSQLArray = new String[1];
	execSQLArray[0]=" delete SYSCA_DeprUnit where 1=1 " +
			" and enterOrg = " + Common.sqlChar(enterOrg) +
			" and deprUnitNo = " + Common.sqlChar(deprUnitNo) + 
			"";
	return execSQLArray;
}


/**
 * <br>
 * <br>目的：依主鍵查詢單一資料
 * <br>參數：無
 * <br>傳回：傳回本物件
*/

public SYSCA016F  queryOne() throws Exception{
	Database db = new Database();
	SYSCA016F obj = this;
	try {
		String sql=" select a.enterorg, (select z.organsname from sysca_organ z where z.organid = a.enterorg) as enterorgname,  "+
				" a.deprUnitNo, a.deprUnitName, a.untdp019rNotes, a.Notes, a.editID, a.editDate, a.editTime" +
			" from SYSCA_DeprUnit a where 1=1 " +
				" and a.enterOrg = " + Common.sqlChar(enterOrg) +
				" and a.deprUnitNo = " + Common.sqlChar(deprUnitNo) + 
			"";
		//System.out.println("======queryOne======"+sql);
		ResultSet rs = db.querySQL(sql);
		if (rs.next()){
			obj.setEnterOrg(rs.getString("enterOrg"));
			obj.setEnterOrgName(rs.getString("enterorgname"));
			obj.setDeprUnitNo(rs.getString("deprUnitNo"));
			obj.setDeprUnitName(rs.getString("deprUnitName"));
			obj.setUntdp019rNotes(rs.getString("untdp019rNotes"));
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
	try {
		String sql=" select a.enterOrg, a.deprUnitNo, a.deprUnitName "+
			" from SYSCA_DeprUnit a where 1=1";
		
		if (!"".equals(getQ_enterOrg()))
			sql+=" and a.enterorg = " + Common.sqlChar(getQ_enterOrg()) ;
		if (!"".equals(getQ_deprUnitNo()))
			sql+=" and a.deprUnitNo = " + Common.sqlChar(getQ_deprUnitNo()) ;
		if (!"".equals(getQ_deprUnitName()))
			sql+=" and a.deprUnitName like " + Common.sqlChar("%"+getQ_deprUnitName()+"%") ;
		
		
		if ("Y".equals(getIsAdminManager())){
			
		}else{
			sql+=" and enterorg = " + Common.sqlChar(getOrganID()) ;
		}
		
		sql+=" order by a.enterOrg, a.deprUnitNo ";
		
		//System.out.println("======queryAll======"+sql);
		
		ResultSet rs = db.querySQL(sql,true);
		processCurrentPageAttribute(rs);
		if (getTotalRecord() > 0) {
			int count = getRecordStart();
			int end = getRecordEnd();
			do {
				if (count > end) break;
			String rowArray[]=new String[3];
			rowArray[0]=Common.get(rs.getString("enterOrg")); 
			rowArray[1]=Common.get(rs.getString("deprUnitNo")); 
			rowArray[2]=Common.get(rs.getString("deprUnitName")); 
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


