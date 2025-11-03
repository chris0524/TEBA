
/*
*<br>程式目的：廠商資料維護
*<br>程式代號：untba002f
*<br>程式日期：0940626
*<br>程式作者：griffin
*<br>--------------------------------------------------------
*<br>修改作者　　修改日期　　　修改目的
*<br>--------------------------------------------------------
*/

package unt.ba;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Iterator;

import util.*;

public class UNTBA002F extends SuperBean{


String enterOrg;
String enterOrgName;
String docNo;
String docName;
String notes;

String q_enterOrg;
String q_enterOrgName;
String q_docNo;
String q_docName;
String state2 ;

public String getState2(){ return checkGet(state2); }
public void setState2(String s){ state2=checkSet(s); }

public String getEnterOrg(){ return checkGet(enterOrg); }
public void setEnterOrg(String s){ enterOrg=checkSet(s); }
public String getEnterOrgName(){ return checkGet(enterOrgName); }
public void setEnterOrgName(String s){ enterOrgName=checkSet(s); }
public String getDocNo(){ return checkGet(docNo); }
public void setDocNo(String s){ docNo=checkSet(s); }
public String getDocName(){ return checkGet(docName); }
public void setDocName(String s){ docName=checkSet(s); }
public String getNotes(){ return checkGet(notes); }
public void setNotes(String s){ notes=checkSet(s); }

public String getQ_docNo(){ return checkGet(q_docNo); }
public void setQ_docNo(String s){ q_docNo=checkSet(s); }
public String getQ_docName(){ return checkGet(q_docName); }
public void setQ_docName(String s){ q_docName=checkSet(s); }

public String getQ_enterOrg() {return checkGet(q_enterOrg);}
public void setQ_enterOrg(String q_enterOrg) {this.q_enterOrg = checkSet(q_enterOrg);}
public String getQ_enterOrgName() {return checkGet(q_enterOrgName);}
public void setQ_enterOrgName(String q_enterOrgName) {this.q_enterOrgName = checkSet(q_enterOrgName);}


//傳回執行insert前之檢查sql
protected String[][] getInsertCheckSQL(){
	String[][] checkSQLArray = new String[1][4];
 	checkSQLArray[0][0]=" select count(*) as checkResult from UNTMP_Doc where 1=1 " + 
		" and enterOrg = " + Common.sqlChar(enterOrg) + 
		" and docNo = " + Common.sqlChar(docNo) + 
		"";
	checkSQLArray[0][1]=">";
	checkSQLArray[0][2]="0";
	checkSQLArray[0][3]="該筆資料己重複，請重新輸入！";
	return checkSQLArray;
}


//傳回insert sql
protected String[] getInsertSQL(){

	String[] execSQLArray = new String[1];
	execSQLArray[0]=" insert into UNTMP_Doc(" +
			" enterOrg,"+
			" docNo,"+
			" docName,"+
			" notes,"+
			" editID,"+
			" editDate,"+
			" editTime"+
		" )Values(" +
			Common.sqlChar(enterOrg) + "," +
			Common.sqlChar(docNo) + "," +
			Common.sqlChar(docName) + "," +
			Common.sqlChar(notes) + "," +
			Common.sqlChar(getEditID()) + "," +
			Common.sqlChar(getEditDate()) + "," +
			Common.sqlChar(getEditTime()) + ")" ;
	return execSQLArray;
}

//批次新增  sql
public void getInsertSQL2(){
	Database db = new Database();
	String[] execSQLArray = new String[1];
	execSQLArray[0]=" insert into UNTMP_Doc(enterOrg,docNo,docName,notes)"
				   +" select "+Common.sqlChar(enterOrg)+",docNo,docName,notes"
				   +" from UNTMP_Doc_Temp "
				   +" where ("+Common.sqlChar(enterOrg)+") not in (select enterOrg from UNTMP_Doc) "
				   +" and (docNo) not in (select docNo from UNTMP_Doc) ";
	try {
		db.exeSQL(execSQLArray[0]) ;
	} catch (Exception e) {
		e.printStackTrace();
	}
	setStateInsertSuccess();
	setErrorMsg("新增完成");
	//setState2("false");
	//return execSQLArray;
	//System.out.println("-execSQLArray[1]-"+execSQLArray[0]) ;
	
}


//傳回update sql
protected String[] getUpdateSQL(){
	String[] execSQLArray = new String[1];
	execSQLArray[0]=" update UNTMP_Doc set " +
			" docName = " + Common.sqlChar(docName) + "," +
			" notes = " + Common.sqlChar(notes) + "," +
			" editID = " + Common.sqlChar(getEditID()) + "," +
			" editDate = " + Common.sqlChar(getEditDate()) + "," +
			" editTime = " + Common.sqlChar(getEditTime()) + 
		" where 1=1 " + 
			" and enterOrg = " + Common.sqlChar(enterOrg) +
			" and docNo = " + Common.sqlChar(docNo) +
			"";
	return execSQLArray;
}


//傳回delete sql
protected String[] getDeleteSQL(){
	String[] execSQLArray = new String[1];
	execSQLArray[0]=" delete UNTMP_Doc where 1=1 " +
			" and enterOrg = " + Common.sqlChar(enterOrg) +
			" and docNo = " + Common.sqlChar(docNo) +
			"";
	return execSQLArray;
}


/**
 * <br>
 * <br>目的：依主鍵查詢單一資料
 * <br>參數：無
 * <br>傳回：傳回本物件
*/

public UNTBA002F  queryOne() throws Exception{
	Database db = new Database();
	UNTBA002F obj = this;
	try {
		String sql=" select a.enterOrg, a.docNo, a.docName, a.notes, a.editID, a.editDate, a.editTime, "+
				"(select z.organsname from SYSCA_ORGAN z where z.organid = a.enterOrg) as enterOrgName" +
			" from UNTMP_Doc a where 1=1 " +
			" and a.enterOrg = " + Common.sqlChar(enterOrg) +
			" and a.docNo = " + Common.sqlChar(docNo) +
			"";
		ResultSet rs = db.querySQL(sql);
		if (rs.next()){
			obj.setEnterOrg(rs.getString("enterOrg"));
			obj.setEnterOrgName(rs.getString("enterOrgName"));
			obj.setDocNo(rs.getString("docNo"));
			obj.setDocName(rs.getString("docName"));
			obj.setNotes(rs.getString("notes"));
			obj.setEditID(rs.getString("editID"));
			obj.setEditDate(rs.getString("editDate"));
			obj.setEditTime(rs.getString("editTime"));
		}
		rs.close();
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
		String sql = " select a.enterOrg, a.docNo, a.docName, a.notes, a.editID, a.editDate, a.editTime "+
					 " from UNTMP_Doc a where 1=1 and a.enterOrg = " + Common.sqlChar(getEnterOrg());  
			if (!"".equals(getQ_docNo()))
				sql+=" and a.docNo like '" + getQ_docNo() + "%' ";
			if (!"".equals(getQ_docName()))
				sql+=" and a.docName like " + Common.sqlChar("%"+getQ_docName()+"%") ;
			
		sql += " order by a.docNo ";
		ResultSet rs = db.querySQL(sql);
		while (rs.next()){
			String rowArray[]=new String[6];
			rowArray[0]=rs.getString("enterOrg"); 
			rowArray[1]=rs.getString("docNo");
			rowArray[2]=rs.getString("docName");
			rowArray[3]=Common.get(rs.getString("notes"));
			rowArray[4]=Common.get(rs.getString("editID")); 
			rowArray[5]=Common.get(rs.getString("editDate")); 
			objList.add(rowArray);
		}
		rs.close();
		setStateQueryAllSuccess();
	} catch (Exception e) {
		e.printStackTrace();
	} finally {
		db.closeAll();
	}
	return objList;
}

//取得文號
public String getDefaultProofDoc(String enterOrg,String docNo){
	Database db = new Database();
	ResultSet rs;	
	String docName ="" ;
	String sql="select docname from UNTMP_DOC " +
		" where enterorg = " + Common.sqlChar(enterOrg) +
		"   and docno=" + Common.sqlChar(docNo) +
		"";		

	try {		
		rs = db.querySQL(sql);
		if (rs.next()){
		    docName = rs.getString("docName");
		}
		rs.getStatement().close();
		rs.close();
	} catch (Exception e) {
		e.printStackTrace();
	} finally {
		db.closeAll();
	}
	return docName;
}
}
