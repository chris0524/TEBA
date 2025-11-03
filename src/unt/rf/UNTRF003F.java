/*
*<br>程式目的：土地改良主檔--基地資料
*<br>程式代號：untbu006f
*<br>程式日期：0940919
*<br>程式作者：clive.chang
*<br>--------------------------------------------------------
*<br>修改作者　　修改日期　　　修改目的
*<br>--------------------------------------------------------
*/

package unt.rf;

import java.sql.ResultSet;
import java.util.ArrayList;

import unt.ch.UNTCH_Tools;
import util.*;

public class UNTRF003F extends UNTRF001F_Q{


String enterOrg;
String ownership;
String propertyNo;
String serialNo;
String serialNo1;
String signNo;
String signNo1;
String signNo2;
String signNo3;
String signNo4;
String signNo5;
String owner;
String manageOrg;
String manageOrgName;
String area;
String notes;

public String getEnterOrg(){ return checkGet(enterOrg); }
public void setEnterOrg(String s){ enterOrg=checkSet(s); }
public String getOwnership(){ return checkGet(ownership); }
public void setOwnership(String s){ ownership=checkSet(s); }
public String getPropertyNo(){ return checkGet(propertyNo); }
public void setPropertyNo(String s){ propertyNo=checkSet(s); }
public String getSerialNo(){ return checkGet(serialNo); }
public void setSerialNo(String s){ serialNo=checkSet(s); }
public String getSerialNo1(){ return checkGet(serialNo1); }
public void setSerialNo1(String s){ serialNo1=checkSet(s); }
public String getSignNo(){ return checkGet(signNo); }
public void setSignNo(String s){ signNo=checkSet(s); }
public String getOwner(){ return checkGet(owner); }
public void setOwner(String s){ owner=checkSet(s); }
public String getManageOrg(){ return checkGet(manageOrg); }
public void setManageOrg(String s){ manageOrg=checkSet(s); }
public String getManageOrgName(){ return checkGet(manageOrgName); }
public void setManageOrgName(String s){ manageOrgName=checkSet(s); }
public String getArea(){ return checkGet(area); }
public void setArea(String s){ area=checkSet(s); }
public String getNotes(){ return checkGet(notes); }
public void setNotes(String s){ notes=checkSet(s); }
public String getSignNo1(){ return checkGet(signNo1); }
public void setSignNo1(String s){ signNo1=checkSet(s); }
public String getSignNo2(){ return checkGet(signNo2); }
public void setSignNo2(String s){ signNo2=checkSet(s); }
public String getSignNo3(){ return checkGet(signNo3); }
public void setSignNo3(String s){ signNo3=checkSet(s); }
public String getSignNo4(){ return checkGet(signNo4); }
public void setSignNo4(String s){ signNo4=checkSet(Common.formatFrontZero(s,4)); }
public String getSignNo5(){ return checkGet(signNo5); }
public void setSignNo5(String s){ signNo5=checkSet(Common.formatFrontZero(s,4)); }

String differenceKind;
public String getDifferenceKind() {return checkGet(differenceKind);}
public void setDifferenceKind(String differenceKind) {this.differenceKind = checkSet(differenceKind);}


//傳回執行insert前之檢查sql
protected String[][] getInsertCheckSQL(){
	//取得自動編號
	Database db = new Database();
	ResultSet rs;	
	String sql="select max(serialNo1)+1 as serialNo1 from UNTRF_Base " +
		" where enterOrg = " + Common.sqlChar(enterOrg) +
		" and ownership = " + Common.sqlChar(ownership) +
		" and propertyNo = " + Common.sqlChar(propertyNo)+
		" and serialNo = " + Common.sqlChar(serialNo)+
		" and differenceKind = " + Common.sqlChar(differenceKind);	
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
	String[][] checkSQLArray = new String[1][4];
 	checkSQLArray[0][0]=" select count(*) as checkResult from UNTRF_Base where 1=1 " + 
		" and enterOrg = " + Common.sqlChar(enterOrg) + 
		" and ownership = " + Common.sqlChar(ownership) + 
		" and propertyNo = " + Common.sqlChar(propertyNo) + 
		" and serialNo = " + Common.sqlChar(serialNo) + 
		" and serialNo1 = " + Common.sqlChar(serialNo1) + 
		" and differenceKind = " + Common.sqlChar(differenceKind) +
		"";
	checkSQLArray[0][1]=">";
	checkSQLArray[0][2]="0";
	checkSQLArray[0][3]="該筆資料己重複，請重新輸入！";
	return checkSQLArray;
}


//傳回insert sql
protected String[] getInsertSQL(){
	String[] execSQLArray = new String[1];
	execSQLArray[0]=" insert into UNTRF_Base(" +
			" enterOrg,"+
			" ownership,"+
			" propertyNo,"+
			" serialNo,"+
			" serialNo1,"+
			" signNo,"+
			" owner,"+
			" manageOrg,"+
			" area,"+
			" notes,"+
			" editID,"+
			" editDate,"+
			" editTime,"+
			" differenceKind"+
		" )Values(" +
			Common.sqlChar(enterOrg) + "," +
			Common.sqlChar(ownership) + "," +
			Common.sqlChar(propertyNo) + "," +
			Common.sqlChar(serialNo) + "," +
			Common.sqlChar(serialNo1) + "," +
			Common.sqlChar(signNo1.substring(0,1)+signNo2.substring(1,3)+signNo3.substring(3,7)+signNo4+signNo5) + "," +
			Common.sqlChar(owner) + "," +
			Common.sqlChar(manageOrg) + "," +
			Common.sqlChar(area) + "," +
			Common.sqlChar(notes) + "," +
			Common.sqlChar(getEditID()) + "," +
			Common.sqlChar(new UNTCH_Tools()._transToCE_Year(getEditDate())) + "," +
			Common.sqlChar(getEditTime()) + "," +
			Common.sqlChar(getDifferenceKind()) + ")" ;
	return execSQLArray;
}


//傳回update sql
protected String[] getUpdateSQL(){
	String[] execSQLArray = new String[1];
	execSQLArray[0]=" update UNTRF_Base set " +
			" signNo = " + Common.sqlChar(signNo1.substring(0,1)+signNo2.substring(1,3)+signNo3.substring(3,7)+signNo4+signNo5) + "," +
			" owner = " + Common.sqlChar(owner) + "," +
			" manageOrg = " + Common.sqlChar(manageOrg) + "," +
			" area = " + Common.sqlChar(area) + "," +
			" notes = " + Common.sqlChar(notes) + "," +
			" editID = " + Common.sqlChar(getEditID()) + "," +
			" editDate = " + Common.sqlChar(new UNTCH_Tools()._transToCE_Year(getEditDate())) + "," +
			" editTime = " + Common.sqlChar(getEditTime()) + "," +
			" differenceKind = " + Common.sqlChar(getDifferenceKind()) + 
		" where 1=1 " + 
			" and enterOrg = " + Common.sqlChar(enterOrg) +
			" and ownership = " + Common.sqlChar(ownership) +
			" and propertyNo = " + Common.sqlChar(propertyNo) +
			" and serialNo = " + Common.sqlChar(serialNo) +
			" and serialNo1 = " + Common.sqlChar(serialNo1) +
			" and differenceKind = " + Common.sqlChar(differenceKind) +
			"";
	return execSQLArray;
}


//傳回delete sql
protected String[] getDeleteSQL(){
	String[] execSQLArray = new String[1];
	execSQLArray[0]=" delete UNTRF_Base where 1=1 " +
			" and enterOrg = " + Common.sqlChar(enterOrg) +
			" and ownership = " + Common.sqlChar(ownership) +
			" and propertyNo = " + Common.sqlChar(propertyNo) +
			" and serialNo = " + Common.sqlChar(serialNo) +
			" and serialNo1 = " + Common.sqlChar(serialNo1) +
			" and differenceKind = " + Common.sqlChar(differenceKind) +
			"";
	return execSQLArray;
}


/**
 * <br>
 * <br>目的：依主鍵查詢單一資料
 * <br>參數：無
 * <br>傳回：傳回本物件
*/

public UNTRF003F  queryOne() throws Exception{
	Database db = new Database();
	UNTRF003F obj = this;
	try {
		String sql=" select a.enterOrg, a.ownership, a.propertyNo, a.serialNo, a.serialNo1, a.signNo, a.owner, a.manageOrg, a.area, a.notes, a.editID, a.editDate, a.editTime, a.differenceKind,  "+
			" b.organAName as ManageOrgName"+
			" from UNTRF_Base a left join SYSCA_Organ b on a.manageOrg = b.organID where 1=1 " +
			" and a.enterOrg = " + Common.sqlChar(enterOrg) +
			" and a.ownership = " + Common.sqlChar(ownership) +
			" and a.propertyNo = " + Common.sqlChar(propertyNo) +
			" and a.serialNo = " + Common.sqlChar(serialNo) +
			" and a.serialNo1 = " + Common.sqlChar(serialNo1) +
			" and a.differenceKind = " + Common.sqlChar(differenceKind) +
			"";
		//System.out.println("===003queryOne="+sql);
		ResultSet rs = db.querySQL(sql);
		if (rs.next()){
			obj.setEnterOrg(rs.getString("enterOrg"));
			obj.setOwnership(rs.getString("ownership"));
			obj.setPropertyNo(rs.getString("propertyNo"));
			obj.setSerialNo(rs.getString("serialNo"));
			obj.setSerialNo1(rs.getString("serialNo1"));
			obj.setSignNo(rs.getString("signNo"));
			obj.setSignNo1(rs.getString("signNo").substring(0,1)+"000000");
			obj.setSignNo2(rs.getString("signNo").substring(0,3)+"0000");
			obj.setSignNo3(rs.getString("signNo").substring(0,7));
			obj.setSignNo4(rs.getString("signNo").substring(7,11));
			obj.setSignNo5(rs.getString("signNo").substring(11));			
			obj.setOwner(rs.getString("owner"));
			obj.setManageOrg(rs.getString("manageOrg"));
			obj.setManageOrgName(rs.getString("ManageOrgName"));
			obj.setArea(rs.getString("area"));
			obj.setNotes(rs.getString("notes"));
			obj.setEditID(rs.getString("editID"));
			obj.setEditDate(new UNTCH_Tools()._transToROC_Year(rs.getString("editDate")));
			obj.setEditTime(rs.getString("editTime"));
			obj.setDifferenceKind(rs.getString("differenceKind"));
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
		String sql=" select a.enterOrg, a.ownership, a.propertyNo, a.serialNo, a.serialNo1, (b.Signdesc + SubString(a.signNo,8,4) + '-' + SubString(a.signNo,12,15)) signNo, " +
			"a.owner "+
			" from UNTRF_Base a, SYSCA_Sign b where 1=1 " +
			" and substring(a.signNo,1,7) = b.signNo " +		
			""; 
			if (!"".equals(getEnterOrg()))
				sql+=" and a.enterOrg = " + Common.sqlChar(getEnterOrg()) ;
			if (!"".equals(getOwnership()))
				sql+=" and a.ownership = " + Common.sqlChar(getOwnership()) ;
			if (!"".equals(getPropertyNo()))
				sql+=" and a.propertyNo = " + Common.sqlChar(getPropertyNo()) ;
			if (!"".equals(getSerialNo()))
				sql+=" and a.serialNo = " + Common.sqlChar(getSerialNo()) ;		
			
		ResultSet rs = db.querySQL(sql,true);
		processCurrentPageAttribute(rs);			
		if (getTotalRecord() > 0) {
			int count = getRecordStart();
			int end = getRecordEnd();
			do {
				if (count > end)
					break;
			String rowArray[]=new String[7];
			rowArray[0]=rs.getString("enterOrg"); 
			rowArray[1]=rs.getString("ownership"); 
			rowArray[2]=rs.getString("propertyNo"); 
			rowArray[3]=rs.getString("serialNo"); 
			rowArray[4]=rs.getString("serialNo1"); 
			rowArray[5]=rs.getString("signNo"); 
			rowArray[6]=rs.getString("owner"); 
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


