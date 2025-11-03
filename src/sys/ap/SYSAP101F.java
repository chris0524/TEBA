/*
*<br>程式目的：特有欄位對應程式
*<br>程式代號：sysap101f
*<br>程式日期：0950501
*<br>程式作者：clive.chang
*<br>--------------------------------------------------------
*<br>修改作者　　修改日期　　　修改目的
*<br>--------------------------------------------------------
*/

package sys.ap;

import java.sql.ResultSet;
import java.util.ArrayList;

import unt.ch.UNTCH_Tools;
import util.*;

public class SYSAP101F extends QueryBean{


String programID;
String programName;
String programField;

String q_programID;
String q_programName;
String q_programField;

public String getProgramID(){ return checkGet(programID).toUpperCase(); }
public void setProgramID(String s){ programID=checkSet(s).toUpperCase(); }
public String getProgramName(){ return checkGet(programName); }
public void setProgramName(String s){ programName=checkSet(s); }
public String getProgramField(){ return checkGet(programField); }
public void setProgramField(String s){ programField=checkSet(s); }

public String getQ_programID(){ return checkGet(q_programID); }
public void setQ_programID(String s){ q_programID=checkSet(s); }
public String getQ_programName(){ return checkGet(q_programName); }
public void setQ_programName(String s){ q_programName=checkSet(s); }
public String getQ_programField(){ return checkGet(q_programField); }
public void setQ_programField(String s){ q_programField=checkSet(s); }


//傳回執行insert前之檢查sql
protected String[][] getInsertCheckSQL(){
	String[][] checkSQLArray = new String[1][4];
 	checkSQLArray[0][0]=" select count(*) as checkResult from SYSAP_FloatingBar where 1=1 " + 
		" and programID = " + Common.sqlChar(programID) + 
		"";
	checkSQLArray[0][1]=">";
	checkSQLArray[0][2]="0";
	checkSQLArray[0][3]="該筆資料己重複，請重新輸入！";
	return checkSQLArray;
}


//傳回insert sql
protected String[] getInsertSQL(){
	UNTCH_Tools ut = new UNTCH_Tools();
	String[] execSQLArray = new String[1];
	execSQLArray[0]=" insert into SYSAP_FloatingBar(" +
			" programID,"+
			" programName,"+
			" programField,"+
			" editID,"+
			" editDate,"+
			" editTime"+
		" )Values(" +
			Common.sqlChar(programID) + "," +
			Common.sqlChar(programName) + "," +
			Common.sqlChar(programField) + "," +
			Common.sqlChar(getEditID()) + "," +
			Common.sqlChar(ut._transToCE_Year(getEditDate())) + "," +
			Common.sqlChar(getEditTime()) + ")" ;
	return execSQLArray;
}


//傳回update sql
protected String[] getUpdateSQL(){
	UNTCH_Tools ut = new UNTCH_Tools();
	String[] execSQLArray = new String[1];
	execSQLArray[0]=" update SYSAP_FloatingBar set " +
			" programName = " + Common.sqlChar(programName) + "," +
			" programField = " + Common.sqlChar(programField) + "," +
			" editID = " + Common.sqlChar(getEditID()) + "," +
			" editDate = " + Common.sqlChar(ut._transToCE_Year(getEditDate())) + "," +
			" editTime = " + Common.sqlChar(getEditTime()) + 
		" where 1=1 " + 
			" and programID = " + Common.sqlChar(programID) +
			"";
	return execSQLArray;
}


//傳回delete sql
protected String[] getDeleteSQL(){
	String[] execSQLArray = new String[1];
	execSQLArray[0]=" delete SYSAP_FloatingBar where 1=1 " +
			" and programID = " + Common.sqlChar(programID) +
			"";
	return execSQLArray;
}


/**
 * <br>
 * <br>目的：依主鍵查詢單一資料
 * <br>參數：無
 * <br>傳回：傳回本物件
*/

public SYSAP101F  queryOne() throws Exception{
	UNTCH_Tools ut = new UNTCH_Tools();
	Database db = new Database();
	SYSAP101F obj = this;
	try {
		String sql=" select a.programID, a.programName, a.programField, a.editID, a.editDate, a.editTime  "+
			" from SYSAP_FloatingBar a where 1=1 " +
			" and a.programID = " + Common.sqlChar(programID) +
			"";
		ResultSet rs = db.querySQL(sql);
		if (rs.next()){
			obj.setProgramID(rs.getString("programID"));
			obj.setProgramName(rs.getString("programName"));
			obj.setProgramField(rs.getString("programField"));
			obj.setEditID(rs.getString("editID"));
			obj.setEditDate(ut._transToROC_Year(rs.getString("editDate")));
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
		String sql=" select a.programID, a.programName "+
			" from SYSAP_FloatingBar a where 1=1 "; 
			if (!"".equals(getQ_programID()))
				sql+=" and a.programID = " + Common.sqlChar(getQ_programID()) ;
			if (!"".equals(getQ_programName()))
				sql+=" and a.programName like " + Common.sqlChar("%"+getQ_programName()+"%") ;
			if (!"".equals(getQ_programField()))
				sql+=" and a.programField like " + Common.sqlChar("%"+getQ_programField()+"%") ;
		ResultSet rs = db.querySQL(sql+" order by a.programName",true);
		processCurrentPageAttribute(rs);
		if (getTotalRecord() > 0) {
			int count = getRecordStart();
			int end = getRecordEnd();
			do {
				if (count > end) break;
				String rowArray[]=new String[3];
				rowArray[0]=Integer.toString(count); 
				rowArray[1]=Common.get(rs.getString("programID")); 
				rowArray[2]=Common.get(rs.getString("programName")); 
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


