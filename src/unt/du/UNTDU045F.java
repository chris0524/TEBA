/*
*<br>程式目的：保管使用人資料
*<br>程式代號：sysca004f
*<br>程式日期：0950321
*<br>程式作者：clive.chang
*<br>--------------------------------------------------------
*<br>修改作者　　修改日期　　　修改目的
*<br>--------------------------------------------------------
*/

package unt.du;

import java.sql.ResultSet;
import java.util.ArrayList;
import util.*;

public class UNTDU045F extends SuperBean {

String enterOrg;
String enterOrgName;
String unitNo;
String unitName;
String keeperNo;
String keeperName;
String notes;

public String getEnterOrg(){ return checkGet(enterOrg); }
public void setEnterOrg(String s){ enterOrg=checkSet(s); }
public String getEnterOrgName(){ return checkGet(enterOrgName); }
public void setEnterOrgName(String s){ enterOrgName=checkSet(s); }
public String getUnitNo(){ return checkGet(unitNo); }
public void setUnitNo(String s){ unitNo=checkSet(s); }
public String getUnitName(){ return checkGet(unitName); }
public void setUnitName(String s){ unitName=checkSet(s); }
public String getKeeperNo(){ return checkGet(keeperNo); }
public void setKeeperNo(String s){ keeperNo=checkSet(s); }
public String getKeeperName(){ return checkGet(keeperName); }
public void setKeeperName(String s){ keeperName=checkSet(s); }
public String getNotes(){ return checkGet(notes); }
public void setNotes(String s){ notes=checkSet(s); }

String incumbencyYN;
public String getIncumbencyYN(){ return checkGet(incumbencyYN); }
public void setIncumbencyYN(String s){ incumbencyYN=checkSet(s); }

String q_enterOrg;
String q_enterOrgName;
String q_unitNo;
public String getQ_enterOrg(){ return checkGet(q_enterOrg); }	
public void setQ_enterOrg(String s){ q_enterOrg=checkSet(s); }
public String getQ_enterOrgName(){ return checkGet(q_enterOrgName); }	
public void setQ_enterOrgName(String s){ q_enterOrgName=checkSet(s); }
public String getQ_unitNo(){ return checkGet(q_unitNo); }	
public void setQ_unitNo(String s){ q_unitNo=checkSet(s); }

//傳回update sql
protected String[] getUpdateSQL(){
	String[] execSQLArray = new String[1];
	execSQLArray[0]=" update UNTMP_Keeper set " +
			" keeperName = " + Common.sqlChar(keeperName) + "," +
			" notes = " + Common.sqlChar(notes) + "," +
			" incumbencyyn = " + Common.sqlChar(incumbencyYN) + "," +
			" editID = " + Common.sqlChar(getEditID()) + "," +
			" editDate = " + Common.sqlChar(getEditDate()) + "," +
			" editTime = " + Common.sqlChar(getEditTime()) + 
		" where 1=1 " + 
			" and enterOrg = " + Common.sqlChar(enterOrg) +
			" and unitNo = " + Common.sqlChar(unitNo) +
			" and keeperNo = " + Common.sqlChar(keeperNo) +
			"";
	return execSQLArray;
}


/**
 * <br>
 * <br>目的：依主鍵查詢單一資料
 * <br>參數：無
 * <br>傳回：傳回本物件
*/

public UNTDU045F  queryOne() throws Exception{
	Database db = new Database();
	UNTDU045F obj = this;
	try {
		String sql = " select b.enterOrg ,b.unitNo ,a.unitname ,b.keeperNo ,b.keeperName "
				   + "		 ,b.notes ,b.editID ,b.editDate ,b.editTime ,b.incumbencyyn "
				   + "		 from UNTMP_KeepUnit a ,UNTMP_Keeper b "
				   + " where 1=1 "
				   + " and a.enterorg = b.enterorg and a.unitno = b.unitno " 
				   + " and b.enterOrg = " + Common.sqlChar(enterOrg) 
				   + " and b.unitNo = " + Common.sqlChar(unitNo) 
				   + " and b.keeperNo = " + Common.sqlChar(keeperNo) 
				   + "";
		ResultSet rs = db.querySQL(sql);
		System.out.println(sql);
		
		if (rs.next()){
			obj.setUnitNo(rs.getString("unitNo"));
			obj.setUnitName(rs.getString("unitname"));
			obj.setKeeperNo(rs.getString("keeperNo"));
			obj.setKeeperName(rs.getString("keeperName"));
			obj.setNotes(rs.getString("notes"));
			obj.setEditID(rs.getString("editID"));
			obj.setEditDate(rs.getString("editDate"));
			obj.setEditTime(rs.getString("editTime"));
			obj.setIncumbencyYN(rs.getString("incumbencyyn"));
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
		String sql = " select a.enterOrg, a.unitNo, a.keeperNo, a.keeperName, a.notes " 
				   + "        ,decode(a.incumbencyyn,'Y','是','N','否') as incumbencyyn "
				   + " from UNTMP_Keeper a "
				   + " where 1=1 "
				   + " and a.enterOrg = " + Common.sqlChar(q_enterOrg)
				   + " and a.unitNo = " + Common.sqlChar(q_unitNo)
				   + "";
		
		//System.out.println(sql);
		ResultSet rs = db.querySQL(sql);
		while (rs.next()){
			counter++;
			String rowArray[]=new String[6];
			rowArray[0]=Common.get(rs.getString("enterOrg")); 
			rowArray[1]=Common.get(rs.getString("unitNo")); 
			rowArray[2]=Common.get(rs.getString("keeperNo")); 
			rowArray[3]=Common.get(rs.getString("keeperName")); 
			rowArray[4]=Common.get(rs.getString("incumbencyyn")); 
			rowArray[5]=Common.get(rs.getString("notes")); 
			objList.add(rowArray);
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


