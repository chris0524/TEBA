/*
*<br>程式目的：他項權利證明書領狀紀錄
*<br>程式代號：untrt012f
*<br>程式日期：0941018
*<br>程式作者：clive.chang
*<br>--------------------------------------------------------
*<br>修改作者　　修改日期　　　修改目的
*<br>--------------------------------------------------------
*/

package unt.rt;

import java.sql.ResultSet;
import java.util.ArrayList;
import util.*;

public class UNTRT012F extends SuperBean{


String enterOrg;
String enterOrgName;
String ownership;
String propertyNo;
String propertyNoName;
String serialNo;
String serialNo1;
String drawDate;
String drawName;
String drawCause;
String drawCause1;
String returnDate;
String notes;
String oldPropertyNo;
String oldSerialNo;

String q_enterOrg;
String q_ownership;
String q_propertyNo;
String q_propertyNoName;
String q_serialNoS;
String q_serialNoE;
String q_drawDateS;
String q_drawDateE;
String q_drawName;
String q_drawCause;

public String getEnterOrg(){ return checkGet(enterOrg); }
public void setEnterOrg(String s){ enterOrg=checkSet(s); }
public String getEnterOrgName(){ return checkGet(enterOrgName); }
public void setEnterOrgName(String s){ enterOrgName=checkSet(s); }
public String getOwnership(){ return checkGet(ownership); }
public void setOwnership(String s){ ownership=checkSet(s); }
public String getPropertyNo(){ return checkGet(propertyNo); }
public void setPropertyNo(String s){ propertyNo=checkSet(s); }
public String getPropertyNoName(){ return checkGet(propertyNoName); }
public void setPropertyNoName(String s){ propertyNoName=checkSet(s); }
public String getSerialNo(){ return checkGet(serialNo); }
public void setSerialNo(String s){ serialNo=checkSet(s); }
public String getSerialNo1(){ return checkGet(serialNo1); }
public void setSerialNo1(String s){ serialNo1=checkSet(s); }
public String getDrawDate(){ return checkGet(drawDate); }
public void setDrawDate(String s){ drawDate=checkSet(s); }
public String getDrawName(){ return checkGet(drawName); }
public void setDrawName(String s){ drawName=checkSet(s); }
public String getDrawCause(){ return checkGet(drawCause); }
public void setDrawCause(String s){ drawCause=checkSet(s); }
public String getDrawCause1(){ return checkGet(drawCause1); }
public void setDrawCause1(String s){ drawCause1=checkSet(s); }
public String getReturnDate(){ return checkGet(returnDate); }
public void setReturnDate(String s){ returnDate=checkSet(s); }
public String getNotes(){ return checkGet(notes); }
public void setNotes(String s){ notes=checkSet(s); }
public String getOldPropertyNo(){ return checkGet(oldPropertyNo); }
public void setOldPropertyNo(String s){ oldPropertyNo=checkSet(s); }
public String getOldSerialNo(){ return checkGet(oldSerialNo); }
public void setOldSerialNo(String s){ oldSerialNo=checkSet(s); }

public String getQ_enterOrg(){ return checkGet(q_enterOrg); }
public void setQ_enterOrg(String s){ q_enterOrg=checkSet(s); }
public String getQ_ownership() {
	if (!"".equals(checkGet(q_ownership))) return checkGet(q_ownership);
	else return "1"; 
}
public void setQ_ownership(String s){ q_ownership=checkSet(s); }
public String getQ_propertyNo(){ return checkGet(q_propertyNo); }
public void setQ_propertyNo(String s){ q_propertyNo=checkSet(s); }
public String getQ_propertyNoName(){ return checkGet(q_propertyNoName); }
public void setQ_propertyNoName(String s){ q_propertyNoName=checkSet(s); }
public String getQ_serialNoS(){ return checkGet(q_serialNoS); }
public void setQ_serialNoS(String s){ q_serialNoS=checkSet(s); }
public String getQ_serialNoE(){ return checkGet(q_serialNoE); }
public void setQ_serialNoE(String s){ q_serialNoE=checkSet(s); }
public String getQ_drawDateS(){ return checkGet(q_drawDateS); }
public void setQ_drawDateS(String s){ q_drawDateS=checkSet(s); }
public String getQ_drawDateE(){ return checkGet(q_drawDateE); }
public void setQ_drawDateE(String s){ q_drawDateE=checkSet(s); }
public String getQ_drawName(){ return checkGet(q_drawName); }
public void setQ_drawName(String s){ q_drawName=checkSet(s); }
public String getQ_drawCause(){ return checkGet(q_drawCause); }
public void setQ_drawCause(String s){ q_drawCause=checkSet(s); }


//傳回執行insert前之檢查sql
protected String[][] getInsertCheckSQL(){
	//取得自動編號
	Database db = new Database();
	ResultSet rs;	
	String sql="select max(serialNo1)+1 as serialNo1 from UNTRT_DRAWPROOF " +
		" where enterOrg = " + Common.sqlChar(enterOrg) +
		" and ownership = " + Common.sqlChar(ownership) +
		" and propertyNo = " + Common.sqlChar(propertyNo)+
		" and serialNo = " + Common.sqlChar(serialNo);	
	try {		
		rs = db.querySQL(sql);
		if (rs.next()){
			if (rs.getString("serialNo1")==null)
				setSerialNo1("1");
			else
				setSerialNo1(rs.getString("serialNo1"));
		}
	} catch (Exception e) {
		e.printStackTrace();
	} finally {
		db.closeAll();
	}		
	String[][] checkSQLArray = new String[2][4];
 	checkSQLArray[0][0]=" select count(*) as checkResult from UNTRT_DrawProof where 1=1 " + 
		" and enterOrg = " + Common.sqlChar(enterOrg) + 
		" and ownership = " + Common.sqlChar(ownership) + 
		" and propertyNo = " + Common.sqlChar(propertyNo) + 
		" and serialNo = " + Common.sqlChar(serialNo) + 
		" and serialNo1 = " + Common.sqlChar(serialNo1) + 
		"";
	checkSQLArray[0][1]=">";
	checkSQLArray[0][2]="0";
	checkSQLArray[0][3]="該筆資料己重複，請重新輸入！";

 	checkSQLArray[1][0]=" select count(*) as checkResult from UNTRT_AddProof where 1=1 " + 
		" and enterOrg = " + Common.sqlChar(enterOrg) + 
		" and ownership = " + Common.sqlChar(ownership) + 
		" and propertyNo = " + Common.sqlChar(propertyNo) + 
		" and serialNo = " + Common.sqlChar(serialNo) +  
		"";
	checkSQLArray[1][1]="<=";
	checkSQLArray[1][2]="0";
	checkSQLArray[1][3]="權利增加單中找不到該筆資料, 請重新輸入！";	
	
	return checkSQLArray;
}


//傳回insert sql
protected String[] getInsertSQL(){
	String[] execSQLArray = new String[1];
	execSQLArray[0]=" insert into UNTRT_DrawProof(" +
			" enterOrg,"+
			" ownership,"+
			" propertyNo,"+
			" serialNo,"+
			" serialNo1,"+
			" drawDate,"+
			" drawName,"+
			" drawCause,"+
			" drawCause1,"+
			" returnDate,"+
			" notes,"+
			" oldPropertyNo,"+
			" oldSerialNo,"+
			" editID,"+
			" editDate,"+
			" editTime"+
		" )Values(" +
			Common.sqlChar(enterOrg) + "," +
			Common.sqlChar(ownership) + "," +
			Common.sqlChar(propertyNo) + "," +
			Common.sqlChar(serialNo) + "," +
			Common.sqlChar(serialNo1) + "," +
			Common.sqlChar(drawDate) + "," +
			Common.sqlChar(drawName) + "," +
			Common.sqlChar(drawCause) + "," +
			Common.sqlChar(drawCause1) + "," +
			Common.sqlChar(returnDate) + "," +
			Common.sqlChar(notes) + "," +
			Common.sqlChar(oldPropertyNo) + "," +
			Common.sqlChar(oldSerialNo) + "," +
			Common.sqlChar(getEditID()) + "," +
			Common.sqlChar(getEditDate()) + "," +
			Common.sqlChar(getEditTime()) + ")" ;
	return execSQLArray;
}


//傳回update sql
protected String[] getUpdateSQL(){
	String[] execSQLArray = new String[1];
	execSQLArray[0]=" update UNTRT_DrawProof set " +
			" drawDate = " + Common.sqlChar(drawDate) + "," +
			" drawName = " + Common.sqlChar(drawName) + "," +
			" drawCause = " + Common.sqlChar(drawCause) + "," +
			" drawCause1 = " + Common.sqlChar(drawCause1) + "," +
			" returnDate = " + Common.sqlChar(returnDate) + "," +
			" notes = " + Common.sqlChar(notes) + "," +
			" oldPropertyNo = " + Common.sqlChar(oldPropertyNo) + "," +
			" oldSerialNo = " + Common.sqlChar(oldSerialNo) + "," +
			" editID = " + Common.sqlChar(getEditID()) + "," +
			" editDate = " + Common.sqlChar(getEditDate()) + "," +
			" editTime = " + Common.sqlChar(getEditTime()) + 
		" where 1=1 " + 
			" and enterOrg = " + Common.sqlChar(enterOrg) +
			" and ownership = " + Common.sqlChar(ownership) +
			" and propertyNo = " + Common.sqlChar(propertyNo) +
			" and serialNo = " + Common.sqlChar(serialNo) +
			" and serialNo1 = " + Common.sqlChar(serialNo1) +
			"";
	return execSQLArray;
}


//傳回delete sql
protected String[] getDeleteSQL(){
	String[] execSQLArray = new String[1];
	execSQLArray[0]=" delete UNTRT_DrawProof where 1=1 " +
			" and enterOrg = " + Common.sqlChar(enterOrg) +
			" and ownership = " + Common.sqlChar(ownership) +
			" and propertyNo = " + Common.sqlChar(propertyNo) +
			" and serialNo = " + Common.sqlChar(serialNo) +
			" and serialNo1 = " + Common.sqlChar(serialNo1) +
			"";
	return execSQLArray;
}


/**
 * <br>
 * <br>目的：依主鍵查詢單一資料
 * <br>參數：無
 * <br>傳回：傳回本物件
*/

public UNTRT012F  queryOne() throws Exception{
	Database db = new Database();
	UNTRT012F obj = this;
	try {
		String sql=" select a.enterOrg, b.organSName as enterOrgName, a.ownership, a.propertyNo, c.propertyName, a.serialNo, a.serialNo1, a.drawDate, a.drawName, a.drawCause, a.drawCause1, a.returnDate, a.notes, a.oldPropertyNo, a.oldSerialNo, a.editID, a.editDate, a.editTime  "+
			" from UNTRT_DrawProof a, SYSCA_Organ b, SYSPK_PropertyCode c where 1=1 " +
			" and a.enterOrg=b.organID and c.propertyNo=a.propertyNo " +
			" and a.enterOrg = " + Common.sqlChar(enterOrg) +
			" and a.ownership = " + Common.sqlChar(ownership) +
			" and a.propertyNo = " + Common.sqlChar(propertyNo) +
			" and a.serialNo = " + Common.sqlChar(serialNo) +
			" and a.serialNo1 = " + Common.sqlChar(serialNo1) +
			"";
		ResultSet rs = db.querySQL(sql);
		if (rs.next()){
			obj.setEnterOrg(rs.getString("enterOrg"));
			obj.setEnterOrgName(rs.getString("enterOrgName"));			
			obj.setOwnership(rs.getString("ownership"));
			obj.setPropertyNo(rs.getString("propertyNo"));
			obj.setPropertyNoName(rs.getString("propertyName"));
			obj.setSerialNo(rs.getString("serialNo"));
			obj.setSerialNo1(rs.getString("serialNo1"));
			obj.setDrawDate(rs.getString("drawDate"));
			obj.setDrawName(rs.getString("drawName"));
			obj.setDrawCause(rs.getString("drawCause"));
			obj.setDrawCause1(rs.getString("drawCause1"));
			obj.setReturnDate(rs.getString("returnDate"));
			obj.setNotes(rs.getString("notes"));
			obj.setOldPropertyNo(rs.getString("oldPropertyNo"));
			obj.setOldSerialNo(rs.getString("oldSerialNo"));
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
		String sql=" select a.enterOrg, a.ownership, case a.ownership when '2' then '國有' else '市有' end as ownershipName, a.propertyNo, a.serialNo, a.serialNo1, a.drawDate, a.drawName, a.drawCause, a.returnDate "+
			" from UNTRT_DrawProof a where 1=1 "; 
		
		if (!"".equals(getQ_enterOrg()))
			sql+=" and a.enterOrg = " + Common.sqlChar(getQ_enterOrg()) ;
		if (!"".equals(getQ_ownership()))
			sql+=" and a.ownership = " + Common.sqlChar(getQ_ownership()) ;
		if (!"".equals(getQ_propertyNo()))
			sql+=" and a.propertyNo = " + Common.sqlChar(getQ_propertyNo()) ;
		
		if (!"".equals(getQ_serialNoS()) && !"".equals(getQ_serialNoE()))
			sql+=" and a.serialNo between " + Common.sqlChar(Common.formatFrontZero(getQ_serialNoS(),7)) + " and " + Common.sqlChar(Common.formatFrontZero(getQ_serialNoE(),7));
		else if (!"".equals(getQ_serialNoS()))
			sql+=" and a.serialNo = " + Common.sqlChar(Common.formatFrontZero(getQ_serialNoS(),7)) ;
		
		if (!"".equals(getQ_drawDateS()) && !"".equals(getQ_drawDateE())) {
			sql+=" and a.drawDate between " + Common.sqlChar(getQ_drawDateS()) + " and " + Common.sqlChar(getQ_drawDateE());		
		} else if (!"".equals(getQ_drawDateS())) {
			sql+=" and a.drawDate=" + Common.sqlChar(getQ_drawDateS());
		}
		
		if (!"".equals(getQ_drawName()))
			sql+=" and a.drawName like '%" + getQ_drawName() + "%'";
		
		if (!"".equals(getQ_drawCause()))
			sql+=" and a.drawCause=" + Common.sqlChar(getQ_drawCause());
				
		ResultSet rs = db.querySQL(sql);
		while (rs.next()){
			counter++;
			String rowArray[]=new String[10]; 
			rowArray[0]=rs.getString("ownershipName");
			rowArray[1]=rs.getString("drawDate"); 
			rowArray[2]=rs.getString("drawName"); 
			rowArray[3]=rs.getString("drawCause"); 
			rowArray[4]=rs.getString("returnDate");
			rowArray[5]=rs.getString("enterOrg"); 
			rowArray[6]=rs.getString("ownership"); 
			rowArray[7]=rs.getString("propertyNo"); 
			rowArray[8]=rs.getString("serialNo"); 
			rowArray[9]=rs.getString("serialNo1");			
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


