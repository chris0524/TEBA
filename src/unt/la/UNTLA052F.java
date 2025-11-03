/*
*<br>程式目的：土地主檔資料維護－土地使用權
*<br>程式代號：untla052f
*<br>程式日期：0980702
*<br>程式作者：jerry
*<br>--------------------------------------------------------
*<br>修改作者　　修改日期　　　修改目的
*<br>--------------------------------------------------------
*/

package unt.la;

import java.sql.ResultSet;
import java.util.ArrayList;

import unt.ch.UNTCH_Tools;
import util.*;

public class UNTLA052F extends UNTLA001Q{


String enterOrg;
String ownership;
String propertyNo;
String serialNo;
String serialNo1;
String useDateS;
String useDateE;
String proofDoc;
String stuff;
String floor1;
String floor2;
String baseArea;
String useLandArea;
String buildingArea;
String useLandPrice;
String purpose;
String oldStuff;
String notes;
String caseNo;

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
public String getUseDateS(){ return checkGet(useDateS); }
public void setUseDateS(String s){ useDateS=checkSet(s); }
public String getUseDateE(){ return checkGet(useDateE); }
public void setUseDateE(String s){ useDateE=checkSet(s); }
public String getProofDoc(){ return checkGet(proofDoc); }
public void setProofDoc(String s){ proofDoc=checkSet(s); }
public String getStuff(){ return checkGet(stuff); }
public void setStuff(String s){ stuff=checkSet(s); }
public String getFloor1(){ return checkGet(floor1); }
public void setFloor1(String s){ floor1=checkSet(s); }
public String getFloor2(){ return checkGet(floor2); }
public void setFloor2(String s){ floor2=checkSet(s); }
public String getBaseArea(){ return checkGet(baseArea); }
public void setBaseArea(String s){ baseArea=checkSet(s); }
public String getUseLandArea(){ return checkGet(useLandArea); }
public void setUseLandArea(String s){ useLandArea=checkSet(s); }
public String getBuildingArea(){ return checkGet(buildingArea); }
public void setBuildingArea(String s){ buildingArea=checkSet(s); }
public String getUseLandPrice(){ return checkGet(useLandPrice); }
public void setUseLandPrice(String s){ useLandPrice=checkSet(s); }
public String getPurpose(){ return checkGet(purpose); }
public void setPurpose(String s){ purpose=checkSet(s); }
public String getOldStuff(){ return checkGet(oldStuff); }
public void setOldStuff(String s){ oldStuff=checkSet(s); }
public String getNotes(){ return checkGet(notes); }
public void setNotes(String s){ notes=checkSet(s); }
public String getCaseNo(){ return checkGet(caseNo); }
public void setCaseNo(String s){ caseNo=checkSet(s); }

String differenceKind;
public String getDifferenceKind() {return checkGet(differenceKind);}
public void setDifferenceKind(String differenceKind) {this.differenceKind = checkSet(differenceKind);}


//傳回執行insert前之檢查sql
protected String[][] getInsertCheckSQL(){
	String[][] checkSQLArray = new String[1][4];
 	checkSQLArray[0][0]=" select count(*) as checkResult from UNTLA_Use where 1=1 " + 
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

	//取得使用次序最大編號加一
	String sSQL = "select max(serialNo1) serialNo1 from UNTLA_Use where 1 = 1 " +
				" and enterOrg = "+ Common.sqlChar(enterOrg) +
				" and ownership = "+ Common.sqlChar(ownership) +
				" and propertyNo = "+ Common.sqlChar(propertyNo) +
				" and serialNo = "+ Common.sqlChar(serialNo) +				
				" and differenceKind = " + Common.sqlChar(differenceKind);
	serialNo1 = util.View.getLookupField(sSQL);
	serialNo1 = String.valueOf(Common.toInt(serialNo1) + 1);
	
	String[] execSQLArray = new String[1];
	execSQLArray[0]=" insert into UNTLA_Use(" +
			" enterOrg,"+
			" ownership,"+
			" propertyNo,"+
			" serialNo,"+
			" serialNo1,"+
			" useDateS,"+
			" useDateE,"+
			" proofDoc,"+
			" stuff,"+
			" floor1,"+
			" floor2,"+
			" baseArea,"+
			" useLandArea,"+
			" buildingArea,"+
			" useLandPrice,"+
			" purpose,"+
			" oldStuff,"+
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
			Common.sqlChar(new UNTCH_Tools()._transToCE_Year(useDateS)) + "," +
			Common.sqlChar(new UNTCH_Tools()._transToCE_Year(useDateE)) + "," +
			Common.sqlChar(proofDoc) + "," +
			Common.sqlChar(stuff) + "," +
			Common.sqlChar(floor1) + "," +
			Common.sqlChar(floor2) + "," +
			Common.sqlChar(baseArea) + "," +
			Common.sqlChar(useLandArea) + "," +
			Common.sqlChar(buildingArea) + "," +
			Common.sqlChar(useLandPrice) + "," +
			Common.sqlChar(purpose) + "," +
			Common.sqlChar(oldStuff) + "," +
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
	execSQLArray[0]=" update UNTLA_Use set " +
			" useDateS = " + Common.sqlChar(new UNTCH_Tools()._transToCE_Year(useDateS)) + "," +
			" useDateE = " + Common.sqlChar(new UNTCH_Tools()._transToCE_Year(useDateE)) + "," +
			" proofDoc = " + Common.sqlChar(proofDoc) + "," +
			" stuff = " + Common.sqlChar(stuff) + "," +
			" floor1 = " + Common.sqlChar(floor1) + "," +
			" floor2 = " + Common.sqlChar(floor2) + "," +
			" baseArea = " + Common.sqlChar(baseArea) + "," +
			" useLandArea = " + Common.sqlChar(useLandArea) + "," +
			" buildingArea = " + Common.sqlChar(buildingArea) + "," +
			" useLandPrice = " + Common.sqlChar(useLandPrice) + "," +
			" purpose = " + Common.sqlChar(purpose) + "," +
			" oldStuff = " + Common.sqlChar(oldStuff) + "," +
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
	execSQLArray[0]=" delete UNTLA_Use where 1=1 " +
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

public UNTLA052F  queryOne() throws Exception{
	Database db = new Database();
	UNTLA052F obj = this;
	try {
		String sql=" select a.enterOrg, a.ownership, a.propertyNo, a.serialNo, a.serialNo1, a.useDateS, a.useDateE, a.proofDoc, a.stuff, a.floor1, a.floor2, a.baseArea, a.useLandArea, a.buildingArea, a.useLandPrice, a.purpose, a.oldStuff, a.notes, a.editID, a.editDate, a.editTime, a.differenceKind "+
			" from UNTLA_Use a where 1=1 " +
			" and a.enterOrg = " + Common.sqlChar(enterOrg) +
			" and a.ownership = " + Common.sqlChar(ownership) +
			" and a.propertyNo = " + Common.sqlChar(propertyNo) +
			" and a.serialNo = " + Common.sqlChar(serialNo) +
			" and a.serialNo1 = " + Common.sqlChar(serialNo1) +
			" and differenceKind = " + Common.sqlChar(differenceKind) +
			"";
		ResultSet rs = db.querySQL(sql);
		if (rs.next()){
			obj.setEnterOrg(rs.getString("enterOrg"));
			obj.setOwnership(rs.getString("ownership"));
			obj.setPropertyNo(rs.getString("propertyNo"));
			obj.setSerialNo(rs.getString("serialNo"));
			obj.setSerialNo1(rs.getString("serialNo1"));
			obj.setUseDateS(new UNTCH_Tools()._transToROC_Year(rs.getString("useDateS")));
			obj.setUseDateE(new UNTCH_Tools()._transToROC_Year(rs.getString("useDateE")));
			obj.setProofDoc(rs.getString("proofDoc"));
			obj.setStuff(rs.getString("stuff"));
			obj.setFloor1(rs.getString("floor1"));
			obj.setFloor2(rs.getString("floor2"));
			obj.setBaseArea(rs.getString("baseArea"));
			obj.setUseLandArea(rs.getString("useLandArea"));
			obj.setBuildingArea(rs.getString("buildingArea"));
			obj.setUseLandPrice(rs.getString("useLandPrice"));
			obj.setPurpose(rs.getString("purpose"));
			obj.setOldStuff(rs.getString("oldStuff"));
			obj.setNotes(rs.getString("notes"));
			obj.setEditID(rs.getString("editID"));
			obj.setEditDate(new UNTCH_Tools()._transToROC_Year(rs.getString("editDate")));
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
		String sql=" select a.enterOrg, a.ownership, a.propertyNo, a.serialNo, a.serialNo1, a.useDateS, a.useDateE, a.stuff, a.baseArea, a.useLandArea, a.buildingArea, a.useLandPrice "+
			" from UNTLA_Use a where 1=1 " +
			" and a.enterOrg = " + Common.sqlChar(enterOrg) +
			" and a.ownership = " + Common.sqlChar(ownership) +
			" and a.propertyNo = " + Common.sqlChar(propertyNo) +
			" and a.serialNo = " + Common.sqlChar(serialNo) +
			" and a.differenceKind = " + Common.sqlChar(differenceKind) +
			"";
		//System.out.println("queryAll:\n"+ sql);			
		ResultSet rs = db.querySQL(sql,true);
		processCurrentPageAttribute(rs);			
		if (getTotalRecord() > 0) {
			int count = getRecordStart();
			int end = getRecordEnd();
			do {
				if (count > end)
					break;
			String rowArray[]=new String[13];
			rowArray[0]=Common.get(rs.getString("enterOrg")); 
			rowArray[1]=Common.get(rs.getString("ownership")); 
			rowArray[2]=Common.get(rs.getString("propertyNo")); 
			rowArray[3]=Common.get(rs.getString("serialNo")); 
			rowArray[4]=Common.get(rs.getString("serialNo1")); 
			rowArray[5]=Common.get(new UNTCH_Tools()._transToROC_Year(rs.getString("useDateS"))); 
			rowArray[6]=Common.get(new UNTCH_Tools()._transToROC_Year(rs.getString("useDateE"))); 
			rowArray[7]=Common.get(rs.getString("stuff")); 
			rowArray[8]=db.getLookupField("select codename from sysca_code where codekindid='STB' and codeid="+Common.sqlChar(rs.getString("stuff")));
			rowArray[9]=Common.get(rs.getString("baseArea")); 
			rowArray[10]=Common.get(rs.getString("useLandArea")); 
			rowArray[11]=Common.get(rs.getString("buildingArea")); 
			rowArray[12]=Common.get(rs.getString("useLandPrice"));

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


