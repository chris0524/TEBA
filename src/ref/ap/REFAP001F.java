/*
*<br>程式目的：市議員資料維護
*<br>程式代號：refap001f
*<br>程式日期：0950521
*<br>程式作者：clive.chang
*<br>--------------------------------------------------------
*<br>修改作者　　修改日期　　　修改目的
*<br>--------------------------------------------------------
*/

package ref.ap;

import java.sql.ResultSet;
import java.util.ArrayList;
import util.*;

public class REFAP001F extends QueryBean{


String seqID;
String councilmanName;
String isNow;
String memo;

String q_seqID;
String q_councilmanName;
String q_isNow;
String q_memo;

public String getSeqID(){ return checkGet(seqID); }
public void setSeqID(String s){ seqID=checkSet(s); }
public String getCouncilmanName(){ return checkGet(councilmanName); }
public void setCouncilmanName(String s){ councilmanName=checkSet(s); }
public String getIsNow(){ return checkGet(isNow); }
public void setIsNow(String s){ isNow=checkSet(s); }
public String getMemo(){ return checkGet(memo); }
public void setMemo(String s){ memo=checkSet(s); }

public String getQ_seqID(){ return checkGet(q_seqID); }
public void setQ_seqID(String s){ q_seqID=checkSet(s); }
public String getQ_councilmanName(){ return checkGet(q_councilmanName); }
public void setQ_councilmanName(String s){ q_councilmanName=checkSet(s); }
public String getQ_isNow(){ return checkGet(q_isNow); }
public void setQ_isNow(String s){ q_isNow=checkSet(s); }
public String getQ_memo(){ return checkGet(q_memo); }
public void setQ_memo(String s){ q_memo=checkSet(s); }


//傳回執行insert前之檢查sql
protected String[][] getInsertCheckSQL(){
	String[][] checkSQLArray = new String[1][4];
	if (!"".equals(getSeqID())) {
	 	checkSQLArray[0][0]=" select count(*) as checkResult from REF_MAN where 1=1 " + 
			" and seqID = " + Common.sqlChar(seqID) + 
			"";
		checkSQLArray[0][1]=">";
		checkSQLArray[0][2]="0";
		checkSQLArray[0][3]="該筆資料己重複，請重新輸入！";
	} else {
	 	checkSQLArray[0][0]=" select 222 as checkResult from dual ";
		checkSQLArray[0][1]=">";
		checkSQLArray[0][2]="0";
		checkSQLArray[0][3]="對不起，議員編號不能為空白！";		
	}
	return checkSQLArray;
}


//傳回insert sql
protected String[] getInsertSQL(){
	String[] execSQLArray = new String[1];
	execSQLArray[0]=" insert into REF_MAN(" +
			" seqID,"+
			" councilmanName,"+
			" isNow,"+
			" memo,"+
			" editID,"+
			" editDate,"+
			" editTime"+
		" )Values(" +
			Common.sqlChar(seqID) + "," +
			Common.sqlChar(councilmanName) + "," +
			Common.sqlChar(isNow) + "," +
			Common.sqlChar(memo) + "," +
			Common.sqlChar(getEditID()) + "," +
			Common.sqlChar(getEditDate()) + "," +
			Common.sqlChar(getEditTime()) + ")" ;
	return execSQLArray;
}


//傳回update sql
protected String[] getUpdateSQL(){
	String[] execSQLArray = new String[1];
	execSQLArray[0]=" update REF_MAN set " +
			" councilmanName = " + Common.sqlChar(councilmanName) + "," +
			" isNow = " + Common.sqlChar(isNow) + "," +
			" memo = " + Common.sqlChar(memo) + "," +
			" editID = " + Common.sqlChar(getEditID()) + "," +
			" editDate = " + Common.sqlChar(getEditDate()) + "," +
			" editTime = " + Common.sqlChar(getEditTime()) + 
		" where 1=1 " + 
			" and seqID = " + Common.sqlChar(seqID) +
			"";
	return execSQLArray;
}


//傳回delete sql
protected String[] getDeleteSQL(){
	String[] execSQLArray = new String[1];
	execSQLArray[0]=" delete REF_MAN where 1=1 " +
			" and seqID = " + Common.sqlChar(seqID) +
			"";
	return execSQLArray;
}


/**
 * <br>
 * <br>目的：依主鍵查詢單一資料
 * <br>參數：無
 * <br>傳回：傳回本物件
*/

public REFAP001F  queryOne() throws Exception{
	Database db = new Database();
	REFAP001F obj = this;
	try {
		String sql=" select a.seqID, a.councilmanName, a.isNow, a.memo, a.editID, a.editDate, a.editTime  "+
			" from REF_MAN a where 1=1 " +
			" and a.seqID = " + Common.sqlChar(seqID) +
			"";
		ResultSet rs = db.querySQL(sql);
		if (rs.next()){
			obj.setSeqID(rs.getString("seqID"));
			obj.setCouncilmanName(rs.getString("councilmanName"));
			obj.setIsNow(rs.getString("isNow"));
			obj.setMemo(rs.getString("memo"));
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
		String sql=" select a.seqID, a.councilmanName, a.isNow, a.memo, a.editID, a.editDate "+
			" from REF_MAN a where 1=1 "; 
			if (!"".equals(getQ_seqID()))
				sql+=" and a.seqID = " + Common.sqlChar(getQ_seqID()) ;
			if (!"".equals(getQ_councilmanName()))
				sql+=" and a.councilmanName like " + Common.sqlChar("%"+getQ_councilmanName()+"%") ;
			if (!"".equals(getQ_isNow()))
				sql+=" and a.isNow = " + Common.sqlChar(getQ_isNow()) ;
			if (!"".equals(getQ_memo()))
				sql+=" and a.memo like " + Common.sqlChar("%"+getQ_memo()+"%") ;
		ResultSet rs = db.querySQL(sql+" order by a.seqID ",true);
		processCurrentPageAttribute(rs);
		if (getTotalRecord() > 0) {
			int count = getRecordStart();
			int end = getRecordEnd();
			do {
				if (count > end)
					break;
				String rowArray[]=new String[7];
				rowArray[0]=Integer.toString(count); 
				rowArray[1]=Common.get(rs.getString("seqID")); 
				rowArray[2]=Common.get(rs.getString("councilmanName"));
				rowArray[3]=Common.get(rs.getString("isNow"));				
				rowArray[4]=Common.get(rs.getString("memo")); 
				rowArray[5]=Common.get(rs.getString("editID"));
				rowArray[6]=Common.get(rs.getString("editDate"));				
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


