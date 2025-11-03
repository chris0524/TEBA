/*
*<br>程式目的：行政院函知財產編號
*<br>程式代號：syspk002f
*<br>程式日期：0950222
*<br>程式作者：clive.chang
*<br>--------------------------------------------------------
*<br>修改作者　　修改日期　　　修改目的
*<br>--------------------------------------------------------
*/

package sys.pk;

import java.sql.ResultSet;
import java.util.ArrayList;
import util.*;

public class SYSPK002F extends SuperBean{


String noticeNo;
String propertyNo;
String propertyName;
String editKind;
String propertyUnit;
String material;
String limitYear;
String memo;
String verify;


String q_noticeNo;
String q_noticeNoE;
String q_highestLevelDate;
String q_highestLevelDateE;
String q_cityPublishDate;
String q_cityPublishDateE;
String q_verify;

public String getVerify(){ return checkGet(verify); }
public void setVerify(String s){ verify=checkSet(s); }
public String getNoticeNo(){ return checkGet(noticeNo); }
public void setNoticeNo(String s){ noticeNo=checkSet(s); }
public String getPropertyNo(){ return checkGet(propertyNo); }
public void setPropertyNo(String s){ propertyNo=checkSet(s); }
public String getPropertyName(){ return checkGet(propertyName); }
public void setPropertyName(String s){ propertyName=checkSet(s); }
public String getEditKind(){ return checkGet(editKind); }
public void setEditKind(String s){ editKind=checkSet(s); }
public String getPropertyUnit(){ return checkGet(propertyUnit); }
public void setPropertyUnit(String s){ propertyUnit=checkSet(s); }
public String getMaterial(){ return checkGet(material); }
public void setMaterial(String s){ material=checkSet(s); }
public String getLimitYear(){ return checkGet(limitYear); }
public void setLimitYear(String s){ limitYear=checkSet(s); }
public String getMemo(){ return checkGet(memo); }
public void setMemo(String s){ memo=checkSet(s); }


public String getQ_noticeNo(){ return checkGet(q_noticeNo); }
public void setQ_noticeNo(String s){ q_noticeNo=checkSet(s); }
public String getQ_noticeNoE(){ return checkGet(q_noticeNoE); }
public void setQ_noticeNoE(String s){ q_noticeNoE=checkSet(s); }
public String getQ_highestLevelDate(){ return checkGet(q_highestLevelDate); }
public void setQ_highestLevelDate(String s){ q_highestLevelDate=checkSet(s); }
public String getQ_highestLevelDateE(){ return checkGet(q_highestLevelDateE); }
public void setQ_highestLevelDateE(String s){ q_highestLevelDateE=checkSet(s); }
public String getQ_cityPublishDate(){ return checkGet(q_cityPublishDate); }
public void setQ_cityPublishDate(String s){ q_cityPublishDate=checkSet(s); }
public String getQ_cityPublishDateE(){ return checkGet(q_cityPublishDateE); }
public void setQ_cityPublishDateE(String s){ q_cityPublishDateE=checkSet(s); }
public String getQ_verify(){ return checkGet(q_verify); }
public void setQ_verify(String s){ q_verify=checkSet(s); }

//傳回執行insert前之檢查sql
protected String[][] getInsertCheckSQL(){
	String[][] checkSQLArray = new String[1][4];
 	checkSQLArray[0][0]=" select count(*) as checkResult from SYSPK_noticeProperty where 1=1 " + 
		" and noticeNo = " + Common.sqlChar(noticeNo) + 
		" and propertyNo = " + Common.sqlChar(propertyNo) + 
		"";
	checkSQLArray[0][1]=">";
	checkSQLArray[0][2]="0";
	checkSQLArray[0][3]="該筆資料己重複，請重新輸入！";
	return checkSQLArray;
}


//傳回insert sql
protected String[] getInsertSQL(){
	String[] execSQLArray = new String[1];
	execSQLArray[0]=" insert into SYSPK_noticeProperty(" +
			" noticeNo,"+
			" propertyNo,"+
			" propertyName,"+
			" editKind,"+
			" propertyUnit,"+
			" material,"+
			" limitYear,"+
			" memo,"+
			" editID,"+
			" editDate,"+
			" editTime"+
		" )Values(" +
			Common.sqlChar(noticeNo) + "," +
			Common.sqlChar(propertyNo) + "," +
			Common.sqlChar(propertyName) + "," +
			Common.sqlChar(editKind) + "," +
			Common.sqlChar(propertyUnit) + "," +
			Common.sqlChar(material) + "," +
			Common.sqlChar(limitYear) + "," +
			Common.sqlChar(memo) + "," +
			Common.sqlChar(getEditID()) + "," +
			Common.sqlChar(getEditDate()) + "," +
			Common.sqlChar(getEditTime()) + ")" ;
	return execSQLArray;
}


//傳回update sql
protected String[] getUpdateSQL(){
	String[] execSQLArray = new String[1];
	execSQLArray[0]=" update SYSPK_noticeProperty set " +
			" propertyName = " + Common.sqlChar(propertyName) + "," +
			" editKind = " + Common.sqlChar(editKind) + "," +
			" propertyUnit = " + Common.sqlChar(propertyUnit) + "," +
			" material = " + Common.sqlChar(material) + "," +
			" limitYear = " + Common.sqlChar(limitYear) + "," +
			" memo = " + Common.sqlChar(memo) + "," +
			" editID = " + Common.sqlChar(getEditID()) + "," +
			" editDate = " + Common.sqlChar(getEditDate()) + "," +
			" editTime = " + Common.sqlChar(getEditTime()) + 
		" where 1=1 " + 
			" and noticeNo = " + Common.sqlChar(noticeNo) +
			" and propertyNo = " + Common.sqlChar(propertyNo) +
			"";
	return execSQLArray;
}


//傳回delete sql
protected String[] getDeleteSQL(){
	String[] execSQLArray = new String[1];
	execSQLArray[0]=" delete SYSPK_noticeProperty where 1=1 " +
			" and noticeNo = " + Common.sqlChar(noticeNo) +
			" and propertyNo = " + Common.sqlChar(propertyNo) +
			"";
	return execSQLArray;
}


/**
 * <br>
 * <br>目的：依主鍵查詢單一資料
 * <br>參數：無
 * <br>傳回：傳回本物件
*/

public SYSPK002F  queryOne() throws Exception{
	Database db = new Database();
	SYSPK002F obj = this;
	try {
		String sql=" select a.noticeNo, a.propertyNo, a.propertyName, a.editKind, a.propertyUnit, a.material, a.limitYear, a.memo, a.editID, a.editDate, a.editTime  "+
			" from SYSPK_noticeProperty a where 1=1 " +
			" and a.noticeNo = " + Common.sqlChar(noticeNo) +
			" and a.propertyNo = " + Common.sqlChar(propertyNo) +
			"";
		ResultSet rs = db.querySQL(sql);
		if (rs.next()){
			obj.setNoticeNo(rs.getString("noticeNo"));
			obj.setPropertyNo(rs.getString("propertyNo"));
			obj.setPropertyName(rs.getString("propertyName"));
			obj.setEditKind(rs.getString("editKind"));
			obj.setPropertyUnit(rs.getString("propertyUnit"));
			obj.setMaterial(rs.getString("material"));
			obj.setLimitYear(rs.getString("limitYear"));
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
		String sql=" select a.noticeNo, a.propertyNo, a.propertyName, decode(a.editKind,'N','新增','U','修改','D','刪除','') editKind, a.propertyUnit, a.limitYear "+
			" from SYSPK_noticeProperty a where a.noticeNo="+Common.sqlChar(noticeNo); 
		ResultSet rs = db.querySQL(sql+" order by propertyNo");
		while (rs.next()){
			String rowArray[]=new String[6];
			rowArray[0]=rs.getString("noticeNo"); 
			rowArray[1]=rs.getString("propertyNo"); 
			rowArray[2]=rs.getString("propertyName"); 
			rowArray[3]=rs.getString("editKind"); 
			rowArray[4]=rs.getString("propertyUnit"); 
			rowArray[5]=rs.getString("limitYear"); 
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


