/*
*<br>程式目的：保管使用單位處別資料維護
*<br>程式代號：sysca010f
*<br>程式日期：0980701
*<br>程式作者：Timtoy.Tsai
*<br>--------------------------------------------------------
*<br>修改作者　　修改日期　　　修改目的
*<br>--------------------------------------------------------
*/

package sys.ca;

import java.sql.ResultSet;
import java.util.ArrayList;
import util.*;

public class SYSCA010F extends QueryBean{


String enterOrg;
String bureau;
String bureauName;
String notes;

String q_enterOrg;
String q_bureau;
String q_bureauName;

public String getEnterOrg(){ return checkGet(enterOrg); }
public void setEnterOrg(String s){ enterOrg=checkSet(s); }
public String getBureau(){ return checkGet(bureau); }
public void setBureau(String s){ bureau=checkSet(s); }
public String getBureauName(){ return checkGet(bureauName); }
public void setBureauName(String s){ bureauName=checkSet(s); }
public String getNotes(){ return checkGet(notes); }
public void setNotes(String s){ notes=checkSet(s); }

public String getQ_enterOrg(){ return checkGet(q_enterOrg); }
public void setQ_enterOrg(String s){ q_enterOrg=checkSet(s); }
public String getQ_bureau(){ return checkGet(q_bureau); }
public void setQ_bureau(String s){ q_bureau=checkSet(s); }
public String getQ_bureauName(){ return checkGet(q_bureauName); }
public void setQ_bureauName(String s){ q_bureauName=checkSet(s); }


//傳回執行insert前之檢查sql
protected String[][] getInsertCheckSQL(){
	String[][] checkSQLArray = new String[1][4];
 	checkSQLArray[0][0]=" select count(*) as checkResult from UNTMP_BUREAU where 1=1 " + 
		" and enterOrg = " + Common.sqlChar(enterOrg) + 
		" and bureau = " + Common.sqlChar(bureau) + 
		"";
	checkSQLArray[0][1]=">";
	checkSQLArray[0][2]="0";
	checkSQLArray[0][3]="該筆資料己重複，請重新輸入！";
	return checkSQLArray;
}


//傳回insert sql
protected String[] getInsertSQL(){
	String[] execSQLArray = new String[1];
	execSQLArray[0]=" insert into UNTMP_BUREAU(" +
			" enterOrg,"+
			" bureau,"+
			" bureauName,"+
			" notes,"+
			" editID,"+
			" editDate,"+
			" editTime"+
		" )Values(" +
			Common.sqlChar(enterOrg) + "," +
			Common.sqlChar(bureau) + "," +
			Common.sqlChar(bureauName) + "," +
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
	execSQLArray[0]=" update UNTMP_BUREAU set " +
			" bureauName = " + Common.sqlChar(bureauName) + "," +
			" notes = " + Common.sqlChar(notes) + "," +
			" editID = " + Common.sqlChar(getEditID()) + "," +
			" editDate = " + Common.sqlChar(getEditDate()) + "," +
			" editTime = " + Common.sqlChar(getEditTime()) + 
		" where 1=1 " + 
			" and enterOrg = " + Common.sqlChar(enterOrg) +
			" and bureau = " + Common.sqlChar(bureau) +
			"";
	return execSQLArray;
}


//傳回delete sql
protected String[] getDeleteSQL(){
	String[] execSQLArray = new String[1];
	execSQLArray[0]=" delete UNTMP_BUREAU where 1=1 " +
			" and enterOrg = " + Common.sqlChar(enterOrg) +
			" and bureau = " + Common.sqlChar(bureau) +
			"";
	return execSQLArray;
}


/**
 * <br>
 * <br>目的：依主鍵查詢單一資料
 * <br>參數：無
 * <br>傳回：傳回本物件
*/

public SYSCA010F  queryOne() throws Exception{
	Database db = new Database();
	SYSCA010F obj = this;
	try {
		String sql=" select a.enterOrg, a.bureau, a.bureauName, a.notes, a.editID, a.editDate, a.editTime  "+
			" from UNTMP_BUREAU a where 1=1 " +
			" and a.enterOrg = " + Common.sqlChar(enterOrg) +
			" and a.bureau = " + Common.sqlChar(bureau) +
			"";
		//System.out.println("======queryOne======"+sql);
		ResultSet rs = db.querySQL(sql);
		if (rs.next()){
			obj.setEnterOrg(rs.getString("enterOrg"));
			obj.setBureau(rs.getString("bureau"));
			obj.setBureauName(rs.getString("bureauName"));
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
		String sql=" select a.enterOrg, a.bureau, a.bureauName "+
			" from UNTMP_BUREAU a where 1=1 and a.enterOrg = " + Common.sqlChar(enterOrg) ; 
//			if (!"".equals(getQ_enterOrg()))
//				sql+=" and a.enterOrg = " + Common.sqlChar(getQ_enterOrg()) ;
			if (!"".equals(getQ_bureau()))
				sql+=" and a.bureau like " + Common.sqlChar("%"+getQ_bureau()) ;
			if (!"".equals(getQ_bureauName()))
				sql+=" and a.bureauName like " + Common.sqlChar("%"+getQ_bureauName()+"%") ;
		sql+=" order by a.enterOrg, a.bureau ";
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
			rowArray[1]=Common.get(rs.getString("bureau")); 
			rowArray[2]=Common.get(rs.getString("bureauName")); 
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


