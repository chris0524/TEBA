

/*
*<br>程式目的：廠商資料維護
*<br>程式代號：untba001f
*<br>程式日期：0940624
*<br>程式作者：griffin
*<br>--------------------------------------------------------
*<br>修改作者　　修改日期　　　修改目的
*<br>--------------------------------------------------------
*/

package unt.ba;

import java.sql.ResultSet;
import java.util.ArrayList;
import util.*;

public class UNTBA001F extends SuperBean{


String enterOrg;
String enterOrgName;
String storeNo;
String storeName;
String tel1;
String tel2;
String seller;
String fax;
String notes;

String q_storeNo;
String q_storeName;

public String getEnterOrg(){ return checkGet(enterOrg); }
public void setEnterOrg(String s){ enterOrg=checkSet(s); }
public String getEnterOrgName(){ return checkGet(enterOrgName); }
public void setEnterOrgName(String s){ enterOrgName=checkSet(s); }
public String getStoreNo(){ return checkGet(storeNo); }
public void setStoreNo(String s){ storeNo=checkSet(s); }
public String getStoreName(){ return checkGet(storeName); }
public void setStoreName(String s){ storeName=checkSet(s); }
public String getTel1(){ return checkGet(tel1); }
public void setTel1(String s){ tel1=checkSet(s); }
public String getTel2(){ return checkGet(tel2); }
public void setTel2(String s){ tel2=checkSet(s); }
public String getSeller(){ return checkGet(seller); }
public void setSeller(String s){ seller=checkSet(s); }
public String getFax(){ return checkGet(fax); }
public void setFax(String s){ fax=checkSet(s); }
public String getNotes(){ return checkGet(notes); }
public void setNotes(String s){ notes=checkSet(s); }

public String getQ_storeNo(){ return checkGet(q_storeNo); }
public void setQ_storeNo(String s){ q_storeNo=checkSet(s); }
public String getQ_storeName(){ return checkGet(q_storeName); }
public void setQ_storeName(String s){ q_storeName=checkSet(s); }


//傳回執行insert前之檢查sql
protected String[][] getInsertCheckSQL(){
	String[][] checkSQLArray = new String[1][4];
 	checkSQLArray[0][0]=" select count(*) as checkResult from UNTMP_Store where 1=1 " + 
		" and enterOrg = " + Common.sqlChar(enterOrg) + 
		" and storeNo = " + Common.sqlChar(storeNo) + 
		"";
	checkSQLArray[0][1]=">";
	checkSQLArray[0][2]="0";
	checkSQLArray[0][3]="該筆資料己重複，請重新輸入！";
	return checkSQLArray;
}


//傳回insert sql
protected String[] getInsertSQL(){
	String[] execSQLArray = new String[1];
	execSQLArray[0]=" insert into UNTMP_Store(" +
			" enterOrg,"+
			" storeNo,"+
			" storeName,"+
			" tel1,"+
			" tel2,"+
			" seller,"+
			" fax,"+
			" notes,"+
			" editID,"+
			" editDate,"+
			" editTime"+
		" )Values(" +
			Common.sqlChar(enterOrg) + "," +
			Common.sqlChar(storeNo) + "," +
			"N" + Common.sqlChar(storeName) + "," +
			Common.sqlChar(tel1) + "," +
			Common.sqlChar(tel2) + "," +
			Common.sqlChar(seller) + "," +
			Common.sqlChar(fax) + "," +
			Common.sqlChar(notes) + "," +
			Common.sqlChar(getEditID()) + "," +
			Common.sqlChar(getEditDate()) + "," +
			Common.sqlChar(getEditTime()) + ")" ;
	return execSQLArray;
}


//傳回update sql
protected String[] getUpdateSQL(){
	String[] execSQLArray = new String[1];
	execSQLArray[0]=" update UNTMP_Store set " +
			" storeName = " +"N"+ Common.sqlChar(storeName) + "," +
			" tel1 = " + Common.sqlChar(tel1) + "," +
			" tel2 = " + Common.sqlChar(tel2) + "," +
			" seller = " + Common.sqlChar(seller) + "," +
			" fax = " + Common.sqlChar(fax) + "," +
			" notes = " + Common.sqlChar(notes) + "," +
			" editID = " + Common.sqlChar(getEditID()) + "," +
			" editDate = " + Common.sqlChar(getEditDate()) + "," +
			" editTime = " + Common.sqlChar(getEditTime()) + 
		" where 1=1 " + 
			" and enterOrg = " + Common.sqlChar(enterOrg) +
			" and storeNo = " + Common.sqlChar(storeNo) +
			"";
	return execSQLArray;
}


//傳回delete sql
protected String[] getDeleteSQL(){
	String[] execSQLArray = new String[1];
	execSQLArray[0]=" delete UNTMP_Store where 1=1 " +
			" and enterOrg = " + Common.sqlChar(enterOrg) +
			" and storeNo = " + Common.sqlChar(storeNo) +
			"";
	return execSQLArray;
}


/**
 * <br>
 * <br>目的：依主鍵查詢單一資料
 * <br>參數：無
 * <br>傳回：傳回本物件
*/

public UNTBA001F  queryOne() throws Exception{
	Database db = new Database();
	UNTBA001F obj = this;
	try {
		String sql=" select a.enterOrg, a.storeNo, a.storeName, a.tel1, a.tel2, a.seller, a.fax, a.notes, a.editID, a.editDate, a.editTime  "+
			" from UNTMP_Store a where 1=1 " +
			" and a.enterOrg = " + Common.sqlChar(enterOrg) +
			" and a.storeNo = " + Common.sqlChar(storeNo) +
			"";
		ResultSet rs = db.querySQL(sql);
		if (rs.next()){
			obj.setEnterOrg(rs.getString("enterOrg"));
			obj.setStoreNo(rs.getString("storeNo"));
			obj.setStoreName(rs.getString("storeName"));
			obj.setTel1(rs.getString("tel1"));
			obj.setTel2(rs.getString("tel2"));
			obj.setSeller(rs.getString("seller"));
			obj.setFax(rs.getString("fax"));
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
		String sql=" select a.enterOrg, a.storeNo, a.storeName, a.tel1, a.seller "+
			" from UNTMP_Store a where 1=1 and a.enterOrg = " + Common.sqlChar(getEnterOrg());  
			if (!"".equals(getQ_storeNo()))
				sql+=" and a.storeNo like '" + getQ_storeNo() +"%' ";
			if (!"".equals(getQ_storeName()))
				sql+=" and a.storeName like '%" + getQ_storeName() + "%' ";
		
		ResultSet rs = db.querySQL(sql);
		while (rs.next()){
			counter++;
			String rowArray[]=new String[5];
			rowArray[0]=rs.getString("enterOrg"); 
			rowArray[1]=rs.getString("storeNo"); 
			rowArray[2]=rs.getString("storeName"); 
			rowArray[3]=rs.getString("tel1"); 
			rowArray[4]=rs.getString("seller"); 
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


