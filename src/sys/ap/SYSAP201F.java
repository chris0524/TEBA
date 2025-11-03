/*
*<br>程式目的：保固維護紀錄
*<br>程式代號：sysap201f
*<br>程式日期：0950915
*<br>程式作者：sam.hsueh
*<br>--------------------------------------------------------
*<br>修改作者　　修改日期　　　修改目的
*<br>--------------------------------------------------------
*/

package sys.ap;

import java.sql.ResultSet;
import java.util.ArrayList;

import util.Common;
import util.Database;
import util.QueryBean;

public class SYSAP201F extends QueryBean{


String m_date;
String m_id;
String m_sno;
String m_note;

String q_m_date;
String q_m_id;

public String getM_date(){ return checkGet(m_date); }
public void setM_date(String s){ m_date=checkSet(s); }
public String getM_id(){ return checkGet(m_id); }
public void setM_id(String s){ m_id=checkSet(s); }
public String getM_sno(){ return checkGet(m_sno); }
public void setM_sno(String s){ m_sno=checkSet(s); }
public String getM_note(){ return checkGet(m_note); }
public void setM_note(String s){ m_note=checkSet(s); }

public String getQ_m_date(){ return checkGet(q_m_date); }
public void setQ_m_date(String s){ q_m_date=checkSet(s); }
public String getQ_m_id(){ return checkGet(q_m_id); }
public void setQ_m_id(String s){ q_m_id=checkSet(s); }


//傳回執行insert前之檢查sql
protected String[][] getInsertCheckSQL(){
	String[][] checkSQLArray = new String[1][4];
 	checkSQLArray[0][0]=" select count(*) as checkResult from sysap_maintain where 1=1 " + 
 		" and m_date = " + Common.sqlChar(m_date) + 
 		" and m_id = " + Common.sqlChar(m_id) +
 		" and m_sno = " + Common.sqlChar(m_sno) +
 		" order by m_sno"+
		"";
	checkSQLArray[0][1]=">";
	checkSQLArray[0][2]="0";
	checkSQLArray[0][3]="該筆資料己重複，請重新輸入！";
	return checkSQLArray;
}


//傳回insert sql
protected String[] getInsertSQL(){
	String[] execSQLArray = new String[1];
	execSQLArray[0]=" insert into sysap_maintain(" +
			" m_date,"+
			" m_id,"+
			" m_sno,"+
			" m_note,"+
			" editID,"+
			" editDate,"+
			" editTime"+
		" )Values(" +
			Common.sqlChar(m_date) + "," +
			Common.sqlChar(m_id) + "," +
			Common.sqlChar(""+Common.formatFrontZero(m_sno,3)) + "," +
			Common.sqlChar(m_note) + "," +
			Common.sqlChar(getEditID()) + "," +
			Common.sqlChar(getEditDate()) + "," +
			Common.sqlChar(getEditTime()) + ")" ;
//	System.out.println(execSQLArray[0]);
	return execSQLArray;
}


//傳回update sql
protected String[] getUpdateSQL(){
	String[] execSQLArray = new String[1];
	execSQLArray[0]=" update sysap_maintain set " +
			" m_date = " + Common.sqlChar(m_date) + "," +
			" m_id = " + Common.sqlChar(m_id) + "," +
			" m_sno = " + Common.sqlChar(""+Common.formatFrontZero(m_sno,3)) + "," +
			" m_note = " + Common.sqlChar(m_note) + "," +
			" editID = " + Common.sqlChar(getEditID()) + "," +
			" editDate = " + Common.sqlChar(getEditDate()) + "," +
			" editTime = " + Common.sqlChar(getEditTime()) + 
		" where 1=1 " + 
		" and m_date = " + Common.sqlChar(m_date) + 
		" and m_id = " + Common.sqlChar(m_id) +
 		" and m_sno = " + Common.sqlChar(m_sno) + 
		"";
	return execSQLArray;
}


//傳回delete sql
protected String[] getDeleteSQL(){
	String[] execSQLArray = new String[1];
	execSQLArray[0]=" delete sysap_maintain where 1=1 " +
		" and m_date = " + Common.sqlChar(m_date) + 
		" and m_id = " + Common.sqlChar(m_id) +
		" and m_sno = " + Common.sqlChar(m_sno) + 
		"";
	return execSQLArray;
}


/**
 * <br>
 * <br>目的：依主鍵查詢單一資料
 * <br>參數：無
 * <br>傳回：傳回本物件
*/

public SYSAP201F  queryOne() throws Exception{
	Database db = new Database();
	SYSAP201F obj = this;
	try {
		String sql=" select a.m_date, a.m_id, a.m_sno, a.m_note, a.editID, a.editDate, a.editTime  "+
			" from sysap_maintain a where 1=1" +
			" and a.m_date = " + Common.sqlChar(m_date) + 
			" and a.m_id = " + Common.sqlChar(m_id) +
	 		" and a.m_sno = " + Common.sqlChar(m_sno) +
			"";	
		ResultSet rs = db.querySQL(sql);
		if (rs.next()){
			obj.setM_date(rs.getString("m_date"));
			obj.setM_id(rs.getString("m_id"));
			obj.setM_sno(rs.getString("m_sno"));
			obj.setM_note(rs.getString("m_note"));
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
		String sql=" select a.m_date, a.m_id, a.m_sno "+
			" from sysap_maintain a where 1=1 "; 
			if (!"".equals(getQ_m_date()))
				sql+=" and a.m_date = " + Common.sqlChar(getQ_m_date()) ;
			if (!"".equals(getQ_m_id()))
				sql+=" and a.m_id = " + Common.sqlChar(getQ_m_id()) ;
		ResultSet rs = db.querySQL(sql);
		while (rs.next()){
			counter++;
			String rowArray[]=new String[3];
			rowArray[0]=Common.get(rs.getString("m_sno")); 
			rowArray[1]=Common.get(rs.getString("m_date")); 
			rowArray[2]=Common.get(rs.getString("m_id")); 
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


