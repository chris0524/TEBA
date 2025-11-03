/*
*<br>程式目的：基金與機關代碼對照資料維護
*<br>程式代號：sysca008f
*<br>程式日期：0980622
*<br>程式作者：Chu-Hung
*<br>--------------------------------------------------------
*<br>修改作者　　修改日期　　　修改目的
*<br>--------------------------------------------------------
*/

package sys.ca;

import java.sql.ResultSet;
import java.util.ArrayList;
import util.*;

public class SYSCA008F extends SuperBean{


String fundNo;
String enterOrg;
String enterOrgName;
String codename;
String codeid;
String notes;
String editID;
String editDate;
String editTime;

String q_fundNo2;
String q_enterOrg;
String q_enterOrgName;


public String getCodeid(){ return checkGet(codeid); }
public void setCodeid(String s){ codeid=checkSet(s); }
public String getCodename(){ return checkGet(codename); }
public void setCodename(String s){ codename=checkSet(s); }

public String getEnterOrgName(){ return checkGet(enterOrgName); }
public void setEnterOrgName(String s){ enterOrgName=checkSet(s); }

public String getQ_enterOrgName(){ return checkGet(q_enterOrgName); }
public void setQ_enterOrgName(String s){ q_enterOrgName=checkSet(s); }

public String getFundNo(){ return checkGet(fundNo); }
public void setFundNo(String s){ fundNo=checkSet(s); }

public String getEnterOrg(){ return checkGet(enterOrg); }
public void setEnterOrg(String s){ enterOrg=checkSet(s); }

public String getNotes(){ return checkGet(notes); }
public void setNotes(String s){ notes=checkSet(s); }
public String getEditID(){ return checkGet(editID); }
public void setEditID(String s){ editID=checkSet(s); }
public String getEditDate(){ return checkGet(editDate); }
public void setEditDate(String s){ editDate=checkSet(s); }
public String getEditTime(){ return checkGet(editTime); }
public void setEditTime(String s){ editTime=checkSet(s); }

public String getQ_fundNo2(){ return checkGet(q_fundNo2); }
public void setQ_fundNo2(String s){ q_fundNo2=checkSet(s); }

public String getQ_enterOrg(){ return checkGet(q_enterOrg); }
public void setQ_enterOrg(String s){ q_enterOrg=checkSet(s); }


//傳回執行insert前之檢查sql
protected String[][] getInsertCheckSQL(){
	String[][] checkSQLArray = new String[1][4];
 	checkSQLArray[0][0]=" select count(*) as checkResult from SYSCA_FundOrgan where 1=1 " + 
		" and fundNo = " + Common.sqlChar(fundNo) + 
		" and enterOrg = " + Common.sqlChar(enterOrg) + 
		"";
	checkSQLArray[0][1]=">";
	checkSQLArray[0][2]="0";
	checkSQLArray[0][3]="該筆資料己重複，請重新輸入！";
	//System.out.println(checkSQLArray[0][0]);
	return checkSQLArray;
}


//傳回insert sql
protected String[] getInsertSQL(){
	String[] execSQLArray = new String[1];
	execSQLArray[0]=" insert into SYSCA_FundOrgan(" +
			" fundNo,"+
			" enterOrg,"+
			" notes,"+
			" editID,"+
			" editDate,"+
			" editTime"+
		" )Values(" +
			Common.sqlChar(fundNo) + "," +
			Common.sqlChar(enterOrg) + "," +
			Common.sqlChar(notes) + "," +
			Common.sqlChar(getEditID()) + "," +
			Common.sqlChar(getEditDate()) + "," +
			Common.sqlChar(getEditTime()) + ")" ;
	//System.out.println(execSQLArray[0]);
	return execSQLArray;
}


//傳回update sql
protected String[] getUpdateSQL(){
	String[] execSQLArray = new String[1];
	execSQLArray[0]=" update SYSCA_FundOrgan set " +
			" notes = " + Common.sqlChar(notes) + "," +
			" editID = " + Common.sqlChar(editID) + "," +
			" editDate = " + Common.sqlChar(editDate) + "," +
			" editTime = " + Common.sqlChar(editTime) + 
		" where 1=1 " + 
			" and fundNo = " + Common.sqlChar(fundNo) +
			" and enterOrg = " + Common.sqlChar(enterOrg) +
			"";
	//System.out.println(execSQLArray[0]);
	return execSQLArray;
}


//傳回delete sql
protected String[] getDeleteSQL(){
	String[] execSQLArray = new String[1];
	execSQLArray[0]=" delete SYSCA_FundOrgan where 1=1 " +
			" and fundNo = " + Common.sqlChar(fundNo) +
			" and enterOrg = " + Common.sqlChar(enterOrg) +
			"";
	return execSQLArray;
}


/**
 * <br>
 * <br>目的：依主鍵查詢單一資料
 * <br>參數：無
 * <br>傳回：傳回本物件
*/

public SYSCA008F  queryOne() throws Exception{
	Database db = new Database();
	SYSCA008F obj = this;
	try {
		String sql=" select a.codeid,a.codename, b.organSName, c.notes, c.editID, c.editDate, c.editTime "+
			" from sysca_code a, SYSCA_Organ b, SYSCA_FundOrgan c " +
			" where 1=1 and IsManager='Y' " +
			" and codekindid='FUD' "+
			" and b.organid =c.enterorg "+
			" and a.codeid = c.fundno "+
			" and c.fundNo = " + Common.sqlChar(fundNo) +
			" and c.enterOrg = " + Common.sqlChar(enterOrg) +
			"";

		ResultSet rs = db.querySQL(sql);
		if (rs.next()){
			obj.setEnterOrgName(rs.getString("organSName"));
			obj.setCodename(rs.getString("codename"));
			obj.setFundNo(rs.getString("codeid"));
			obj.setCodeid(rs.getString("codeid"));
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
		String sql=" select a.codename, b.organSName, c.enterorg, c.fundno " +
		" from sysca_code a, SYSCA_Organ b, SYSCA_FundOrgan c " + 
        " where 1=1 and codekindid = 'FUD' " +
        " and IsManager = 'Y' " +
        " and b.organid = c.enterorg " +
        " and a.codeid = c.fundno " ;
		
		if (!"".equals(getQ_fundNo2()))
			sql+=" and c.fundNo = " + Common.sqlChar(getQ_fundNo2()) ;
	    if (!"".equals(getQ_enterOrg()))
			sql+=" and c.enterOrg = " + Common.sqlChar(getQ_enterOrg()) ;
		sql += " order by organID " ;
		
		//System.out.println(sql);
		ResultSet rs = db.querySQL(sql,true);
		int counter = 0;
		while (rs.next()){
			counter++;
		    String rowArray[]=new String[4];
		    rowArray[0]=Common.get(rs.getString("codename")); 
		    rowArray[1]=Common.get(rs.getString("organSName")); 
		    rowArray[2]=Common.get(rs.getString("fundno")); 
		    rowArray[3]=Common.get(rs.getString("enterorg")); 
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


