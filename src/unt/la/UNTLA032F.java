/*
*<br>程式目的：土地合併分割作業－合併增加單管理資料
*<br>程式代號：untla032f
*<br>程式日期：0941003
*<br>程式作者：carey
*<br>--------------------------------------------------------
*<br>修改作者　　修改日期　　　修改目的
*<br>--------------------------------------------------------
*/

package unt.la;

import java.sql.ResultSet;
import java.util.ArrayList;
import util.*;

public class UNTLA032F extends UNTLA027Q{


String enterOrg;
String enterOrgName;
String ownership;
String propertyNo;
String propertyName;
String serialNo;
String serialNo1;
String useUnit;
String useUnitName;
String useUnit1;
String useRelation;
String useDateS;
String useDateE;
String useArea;
String notes1;
String caseNo;
String notes;

String caseName;
String mergeDivision;
String mergeReduce;
String mergeAdd;
String divisionReduce;
String divisionAdd;
String mergeDivisionDate;
String cause;
String cause1;
String approveOrg;
String approveDate;
String approveDoc;
String propertyKind;

public String getPropertyKind(){ return checkGet(propertyKind); }
public void setPropertyKind(String s){ propertyKind=checkSet(s); }
public String getEnterOrg(){ return checkGet(enterOrg); }
public void setEnterOrg(String s){ enterOrg=checkSet(s); }
public String getEnterOrgName(){ return checkGet(enterOrgName); }
public void setEnterOrgName(String s){ enterOrgName=checkSet(s); }
public String getOwnership(){ return checkGet(ownership); }
public void setOwnership(String s){ ownership=checkSet(s); }
public String getPropertyNo(){ return checkGet(propertyNo); }
public void setPropertyNo(String s){ propertyNo=checkSet(s); }
public String getPropertyName(){ return checkGet(propertyName); }
public void setPropertyName(String s){ propertyName=checkSet(s); }
public String getSerialNo(){ return checkGet(serialNo); }
public void setSerialNo(String s){ serialNo=checkSet(s); }
public String getSerialNo1(){ return checkGet(serialNo1); }
public void setSerialNo1(String s){ serialNo1=checkSet(s); }
public String getUseUnit(){ return checkGet(useUnit); }
public void setUseUnit(String s){ useUnit=checkSet(s); }
public String getUseUnitName(){ return checkGet(useUnitName); }
public void setUseUnitName(String s){ useUnitName=checkSet(s); }
public String getUseUnit1(){ return checkGet(useUnit1); }
public void setUseUnit1(String s){ useUnit1=checkSet(s); }
public String getUseRelation(){ return checkGet(useRelation); }
public void setUseRelation(String s){ useRelation=checkSet(s); }
public String getUseDateS(){ return checkGet(useDateS); }
public void setUseDateS(String s){ useDateS=checkSet(s); }
public String getUseDateE(){ return checkGet(useDateE); }
public void setUseDateE(String s){ useDateE=checkSet(s); }
public String getUseArea(){ return checkGet(useArea); }
public void setUseArea(String s){ useArea=checkSet(s); }
public String getNotes1(){ return checkGet(notes1); }
public void setNotes1(String s){ notes1=checkSet(s); }
public String getCaseNo(){ return checkGet(caseNo); }
public void setCaseNo(String s){ caseNo=checkSet(s); }
public String getNotes(){ return checkGet(notes); }
public void setNotes(String s){ notes=checkSet(s); }

public String getCaseName(){ return checkGet(caseName); }
public void setCaseName(String s){ caseName=checkSet(s); }
public String getMergeDivision(){ return checkGet(mergeDivision); }
public void setMergeDivision(String s){ mergeDivision=checkSet(s); }
public String getMergeReduce(){ return checkGet(mergeReduce); }
public void setMergeReduce(String s){ mergeReduce=checkSet(s); }
public String getMergeAdd(){ return checkGet(mergeAdd); }
public void setMergeAdd(String s){ mergeAdd=checkSet(s); }
public String getDivisionReduce(){ return checkGet(divisionReduce); }
public void setDivisionReduce(String s){ divisionReduce=checkSet(s); }
public String getDivisionAdd(){ return checkGet(divisionAdd); }
public void setDivisionAdd(String s){ divisionAdd=checkSet(s); }
public String getMergeDivisionDate(){ return checkGet(mergeDivisionDate); }
public void setMergeDivisionDate(String s){ mergeDivisionDate=checkSet(s); }
public String getCause(){ return checkGet(cause); }
public void setCause(String s){ cause=checkSet(s); }
public String getCause1(){ return checkGet(cause1); }
public void setCause1(String s){ cause1=checkSet(s); }
public String getApproveOrg(){ return checkGet(approveOrg); }
public void setApproveOrg(String s){ approveOrg=checkSet(s); }
public String getApproveDate(){ return checkGet(approveDate); }
public void setApproveDate(String s){ approveDate=checkSet(s); }
public String getApproveDoc(){ return checkGet(approveDoc); }
public void setApproveDoc(String s){ approveDoc=checkSet(s); }

String enterDate;

public String getEnterDate(){ return checkGet(enterDate); }
public void setEnterDate(String s){ enterDate=checkSet(s); }

//傳回執行insert前之檢查sql
protected String[][] getInsertCheckSQL(){
	String[][] checkSQLArray = new String[1][4];
 	checkSQLArray[0][0]=" select count(*) as checkResult from UNTLA_Manage where 1=1 " + 
		" and enterOrg = " + Common.sqlChar(enterOrg) + 
		" and ownership = " + Common.sqlChar(ownership) + 
		" and propertyNo = " + Common.sqlChar(propertyNo) + 
		" and serialNo = " + Common.sqlChar(serialNo) + 
		" and serialNo1 = " + Common.sqlChar(serialNo1) + 
		"";
	checkSQLArray[0][1]=">";
	checkSQLArray[0][2]="0";
	checkSQLArray[0][3]="該筆資料己重複，請重新輸入！";
	return checkSQLArray;
}


//傳回insert sql
protected String[] getInsertSQL(){
	//取得管理次序
	Database db = new Database();
	ResultSet rs;	
	String sql="select (max(serialNo1) + 1) as serialNo1 from UNTLA_Manage " +
		" where enterOrg = " + Common.sqlChar(enterOrg) + 
		" and ownership = " + Common.sqlChar(ownership) + 
		" and propertyNo = " + Common.sqlChar(propertyNo) + 
		" and serialNo = " + Common.sqlChar(serialNo) + 
		"";		
	try {		
		rs = db.querySQL(sql);
		if (rs.next()){
			if("".equals(checkGet(rs.getString("serialNo1")))){
				setSerialNo1("1");
			}else{
				setSerialNo1(rs.getString("serialNo1"));
			}
		} else {
			setSerialNo1("1");   
		}
	} catch (Exception e) {
		e.printStackTrace();
	} finally {
		db.closeAll();
	}    
	String[] execSQLArray = new String[2];
	execSQLArray[0]=" insert into UNTLA_Manage(" +
			" enterOrg,"+
			" ownership,"+
			" propertyNo,"+
			" serialNo,"+
			" serialNo1,"+
			" useUnit,"+
			" useUnit1,"+
			" useRelation,"+
			" useDateS,"+
			" useDateE,"+
			" useArea,"+
			" notes1,"+
			" notes,"+
			" editID,"+
			" editDate,"+
			" editTime"+
		" )Values(" +
			Common.sqlChar(enterOrg) + "," +
			Common.sqlChar(ownership) + "," +
			Common.sqlChar(propertyNo) + "," +
			Common.sqlChar(serialNo) + "," +
			Common.sqlChar(serialNo1) + "," +
			Common.sqlChar(useUnit) + "," +
			Common.sqlChar(useUnit1) + "," +
			Common.sqlChar(useRelation) + "," +
			Common.sqlChar(useDateS) + "," +
			Common.sqlChar(useDateE) + "," +
			Common.sqlChar(useArea) + "," +
			Common.sqlChar(notes1) + "," +
			Common.sqlChar(notes) + "," +
			Common.sqlChar(getEditID()) + "," +
			Common.sqlChar(getEditDate()) + "," +
			Common.sqlChar(getEditTime()) + ")" ;

	//取得使用情形
	//依據「入帳機關＋權屬＋財產編號＋財產分號」判斷該筆資料之「土地管理檔UNTLA_Manage」的「使用關係useRelation」欄位回寫「土地主檔UNTLA_Land」之「使用情形useState」欄位
	//規則：	租用＞占用＞委託經營＞借用＞自用＞空地(空置)＞其他(待處理)
	Database db1 = new Database();
	ResultSet rs1;
	String strState = "";
	String strUseState = "";
	String[] arrUseState;
	int[] arrCheck = {0,0,0,0,0,0,0};
	int i=0;
	
	String sql1="select useRelation from UNTLA_Manage " +
		" where enterOrg = " + Common.sqlChar(enterOrg) +
		" and ownership = " + Common.sqlChar(ownership) +
		" and propertyNo = " + Common.sqlChar(propertyNo) +
		" and serialNo = " + Common.sqlChar(serialNo);
	try {		
		rs1 = db1.querySQL(sql1);
		strUseState = getUseRelation();		
		while (rs1.next()){
			strUseState += "," + rs1.getString("useRelation");
		}
		arrUseState = strUseState.split(",");
		for (i=0; i < arrUseState.length; i++) {
			if (arrUseState[i].equals("01") || arrUseState[i].equals("52") || arrUseState[i].equals("53") ) {//出租
				arrCheck[0] = 1;				
			} else if (arrUseState[i].equals("03")) {//占用
				arrCheck[1] = 1;				
			} else if (arrUseState[i].equals("10")) {//委託經營
				arrCheck[2] = 1;				
			} else if (arrUseState[i].equals("02")) {//借用
				arrCheck[3] = 1;				
			} else if (arrUseState[i].equals("04")) {//自用
				arrCheck[4] = 1;				
			} else if (arrUseState[i].equals("50")) {//空地(空置)
				arrCheck[5] = 1;				
			} else {//其他(待處理)
				arrCheck[6] = 1;				
			}
		}
		for (i=0; i < arrCheck.length; i++) {
			if (arrCheck[i]==1) {
				if (i==0) {
					strState = "01";//出租
					break;
				} else if (i==1) {
					strState = "03";//占用
					break;
				} else if (i==2) {
					strState = "10";//委託經營
					break;
				} else if (i==3) {
					strState = "02";//借用
					break;
				} else if (i==4) {
					strState = "04";//自用
					break;
				} else if (i==5) {
					strState = "50";//空地
					break;
				} else if (i==6) {
					strState = "99";//其他
					break;
				}
			}
		}
		if (!"".equals(strState)) {
			//更新土地主檔使用關係
		    execSQLArray[1]=" update UNTLA_Land set useState='" + strState + "'" +
			" where enterOrg = " + Common.sqlChar(enterOrg) +
			" and ownership = " + Common.sqlChar(ownership) +
			" and propertyNo = " + Common.sqlChar(propertyNo) +
			" and serialNo = " + Common.sqlChar(serialNo);			
		}
	} catch (Exception e) {
		e.printStackTrace();
	} finally {
		db1.closeAll();
	}
	
	return execSQLArray;
}


//傳回update sql
protected String[] getUpdateSQL(){
	String[] execSQLArray = new String[2];
	execSQLArray[0]=" update UNTLA_Manage set " +
			" useUnit = " + Common.sqlChar(useUnit) + "," +
			" useUnit1 = " + Common.sqlChar(useUnit1) + "," +
			" useRelation = " + Common.sqlChar(useRelation) + "," +
			" useDateS = " + Common.sqlChar(useDateS) + "," +
			" useDateE = " + Common.sqlChar(useDateE) + "," +
			" useArea = " + Common.sqlChar(useArea) + "," +
			" notes1 = " + Common.sqlChar(notes1) + "," +
			" notes = " + Common.sqlChar(notes) + "," +
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

	//取得使用情形
	//依據「入帳機關＋權屬＋財產編號＋財產分號」判斷該筆資料之「土地管理檔UNTLA_Manage」的「使用關係useRelation」欄位回寫「土地主檔UNTLA_Land」之「使用情形useState」欄位
	//規則：	租用＞占用＞委託經營＞借用＞自用＞空地(空置)＞其他(待處理)
	Database db1 = new Database();
	ResultSet rs1;
	String strState = "";
	String strUseState = "";
	String[] arrUseState;
	int[] arrCheck = {0,0,0,0,0,0,0};
	int i=0;
	
	String sql1="select useRelation from UNTLA_Manage " +
		" where enterOrg = " + Common.sqlChar(enterOrg) +
		" and ownership = " + Common.sqlChar(ownership) +
		" and propertyNo = " + Common.sqlChar(propertyNo) +
		" and serialNo = " + Common.sqlChar(serialNo) +
		" and serialNo1 <> " + Common.sqlChar(serialNo1);
	try {		
		rs1 = db1.querySQL(sql1);
		strUseState = getUseRelation();		
		while (rs1.next()){
			strUseState += "," + rs1.getString("useRelation");
		}
		arrUseState = strUseState.split(",");
		for (i=0; i < arrUseState.length; i++) {
			if (arrUseState[i].equals("01") || arrUseState[i].equals("52") || arrUseState[i].equals("53") ) {//出租
				arrCheck[0] = 1;				
			} else if (arrUseState[i].equals("03")) {//占用
				arrCheck[1] = 1;				
			} else if (arrUseState[i].equals("10")) {//委託經營
				arrCheck[2] = 1;				
			} else if (arrUseState[i].equals("02")) {//借用
				arrCheck[3] = 1;				
			} else if (arrUseState[i].equals("04")) {//自用
				arrCheck[4] = 1;				
			} else if (arrUseState[i].equals("50")) {//空地(空置)
				arrCheck[5] = 1;				
			} else {//其他(待處理)
				arrCheck[6] = 1;				
			}
		}
		for (i=0; i < arrCheck.length; i++) {
			if (arrCheck[i]==1) {
				if (i==0) {
					strState = "01";//出租
					break;
				} else if (i==1) {
					strState = "03";//占用
					break;
				} else if (i==2) {
					strState = "10";//委託經營
					break;
				} else if (i==3) {
					strState = "02";//借用
					break;
				} else if (i==4) {
					strState = "04";//自用
					break;
				} else if (i==5) {
					strState = "50";//空地
					break;
				} else if (i==6) {
					strState = "99";//其他
					break;
				}
			}
		}
		if (!"".equals(strState)) {
		    //更新土地主檔使用關係
		    execSQLArray[1]=" update UNTLA_Land set useState='" + strState + "'" +
			" where enterOrg = " + Common.sqlChar(enterOrg) +
			" and ownership = " + Common.sqlChar(ownership) +
			" and propertyNo = " + Common.sqlChar(propertyNo) +
			" and serialNo = " + Common.sqlChar(serialNo);			
		}
	} catch (Exception e) {
		e.printStackTrace();
	} finally {
		db1.closeAll();
	}
	
	return execSQLArray;
}


//傳回delete sql
protected String[] getDeleteSQL(){
	String[] execSQLArray = new String[3];
	
	//刪除土地管理檔
	execSQLArray[0]=" delete UNTLA_Manage where 1=1 " +
			" and enterOrg = " + Common.sqlChar(enterOrg) +
			" and ownership = " + Common.sqlChar(ownership) +
			" and propertyNo = " + Common.sqlChar(propertyNo) +
			" and serialNo = " + Common.sqlChar(serialNo) +
			" and serialNo1 = " + Common.sqlChar(serialNo1) +
			"";
	
	//刪除土地使用人資料檔
	execSQLArray[1]=" delete UNTLA_Person where 1=1 " +
			" and enterOrg = " + Common.sqlChar(enterOrg) +
			" and ownership = " + Common.sqlChar(ownership) +
			" and propertyNo = " + Common.sqlChar(propertyNo) +
			" and serialNo = " + Common.sqlChar(serialNo) +
			" and serialNo1 = " + Common.sqlChar(serialNo1) +
			"";
	
	//取得使用情形
	//依據「入帳機關＋權屬＋財產編號＋財產分號」判斷該筆資料之「土地管理檔UNTLA_Manage」的「使用關係useRelation」欄位回寫「土地主檔UNTLA_Land」之「使用情形useState」欄位
	//規則：	租用＞占用＞委託經營＞借用＞自用＞空地(空置)＞其他(待處理)
	Database db1 = new Database();
	ResultSet rs1;
	String strState = "";
	String strUseState = "";
	String[] arrUseState;
	int[] arrCheck = {0,0,0,0,0,0,0};
	int i=0,j=0;
	
	String sql1="select useRelation from UNTLA_Manage " +
		" where enterOrg = " + Common.sqlChar(enterOrg) +
		" and ownership = " + Common.sqlChar(ownership) +
		" and propertyNo = " + Common.sqlChar(propertyNo) +
		" and serialNo = " + Common.sqlChar(serialNo) +
		" and serialNo1 <> " + Common.sqlChar(serialNo1);
	try {		
		rs1 = db1.querySQL(sql1);
		strUseState = "";		
		while (rs1.next()){
			strUseState += rs1.getString("useRelation") + ",";		
		}
		if (!"".equals(strUseState)) {		
			arrUseState = strUseState.split(",");
			for (i=0; i < arrUseState.length; i++) {
				if (arrUseState[i].equals("01") || arrUseState[i].equals("52") || arrUseState[i].equals("53") ) {//出租
					arrCheck[0] = 1;				
				} else if (arrUseState[i].equals("03")) {//占用
					arrCheck[1] = 1;				
				} else if (arrUseState[i].equals("10")) {//委託經營
					arrCheck[2] = 1;				
				} else if (arrUseState[i].equals("02")) {//借用
					arrCheck[3] = 1;				
				} else if (arrUseState[i].equals("04")) {//自用
					arrCheck[4] = 1;				
				} else if (arrUseState[i].equals("50")) {//空地(空置)
					arrCheck[5] = 1;				
				} else {//其他(待處理)
					arrCheck[6] = 1;				
				}
			}
		}
		for (i=0; i < arrCheck.length; i++) {
			if (arrCheck[i]==1) {
				if (i==0) {
					strState = "01";//出租
					break;
				} else if (i==1) {
					strState = "03";//占用
					break;
				} else if (i==2) {
					strState = "10";//委託經營
					break;
				} else if (i==3) {
					strState = "02";//借用
					break;
				} else if (i==4) {
					strState = "04";//自用
					break;
				} else if (i==5) {
					strState = "50";//空地
					break;
				} else if (i==6) {
					strState = "99";//其他
					break;
				}
			}
		}
		//無管理資料時
		for (i=0; i < arrCheck.length; i++) {
			if (arrCheck[i]==0) j++;
		}
		if (j==7){strState = "99";}
		
	} catch (Exception e) {
		e.printStackTrace();
	} finally {
		db1.closeAll();
	}
	
	//更新土地主檔使用關係
	execSQLArray[2] = " update UNTLA_Land set useState='" + strState + "' where 1=1 " +
			" and enterOrg = " + Common.sqlChar(enterOrg) +
			" and ownership = " + Common.sqlChar(ownership) +
			" and propertyNo = " + Common.sqlChar(propertyNo) +
			" and serialNo = " + Common.sqlChar(serialNo) +
			"";	

	return execSQLArray;
}


/**
 * <br>
 * <br>目的：依主鍵查詢單一資料
 * <br>參數：無
 * <br>傳回：傳回本物件
*/

public UNTLA032F  queryOne() throws Exception{
	Database db = new Database();
	UNTLA032F obj = this;
	try {
		String sql=" select " +
			" (select b.organSName from SYSCA_Organ b where b.organID = a.useUnit) as organSName," +
			" c.propertyName,a.enterOrg, a.ownership, a.propertyNo, a.serialNo, a.serialNo1, a.useUnit, a.useUnit1, a.useRelation, a.useDateS, a.useDateE, a.useArea, a.notes1, a.fakeDivision, a.ApproveYN, a.caseKind, a.caseNo, a.notes, a.editID, a.editDate, a.editTime  "+
			" from UNTLA_Manage a, SYSPK_PropertyCode c where 1=1 " +
			" and a.enterOrg = " + Common.sqlChar(enterOrg) +
			" and a.ownership = " + Common.sqlChar(ownership) +
			" and a.propertyNo = " + Common.sqlChar(propertyNo) +
			" and a.serialNo = " + Common.sqlChar(serialNo) +
			" and a.serialNo1 = " + Common.sqlChar(serialNo1) +
			" and a.propertyNo = c.propertyNo" +			
			"";
		ResultSet rs = db.querySQL(sql);
		if (rs.next()){
			obj.setEnterOrg(rs.getString("enterOrg"));
			obj.setOwnership(rs.getString("ownership"));
			obj.setPropertyNo(rs.getString("propertyNo"));
			obj.setPropertyName(rs.getString("propertyName"));
			obj.setSerialNo(rs.getString("serialNo"));
			obj.setSerialNo1(rs.getString("serialNo1"));
			obj.setUseUnit(rs.getString("useUnit"));
			obj.setUseUnitName(rs.getString("organSName"));
			obj.setUseUnit1(rs.getString("useUnit1"));
			obj.setUseRelation(rs.getString("useRelation"));
			obj.setUseDateS(rs.getString("useDateS"));
			obj.setUseDateE(rs.getString("useDateE"));
			obj.setUseArea(rs.getString("useArea"));
			obj.setNotes1(rs.getString("notes1"));
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
		String sql=" select " +
			" (select b.organSName from SYSCA_Organ b where a.useUnit = b.organID)as organSName," + 
			" a.enterOrg, a.ownership, a.propertyNo, a.serialNo, a.serialNo1, a.useUnit, a.useUnit1,c.codeName useRelation, a.useDateS, a.useDateE, a.useArea, a.caseNo "+
			" from UNTLA_Manage a, SYSCA_Code c "+
			" where 1=1 "+
			"   and c.codeKindID='URE' "+
			"   and a.useRelation = c.codeID "+			
			"   and a.enterOrg = " + Common.sqlChar(enterOrg) +
			"   and a.ownership = " + Common.sqlChar(ownership) +
			"   and a.propertyNo = " + Common.sqlChar(propertyNo) +
			"   and a.serialNo = " + Common.sqlChar(serialNo) ;			
		ResultSet rs = db.querySQL(sql);
		while (rs.next()){
			counter++;
			String rowArray[]=new String[13];
			rowArray[0]=rs.getString("enterOrg"); 
			rowArray[1]=rs.getString("ownership"); 
			rowArray[2]=rs.getString("propertyNo"); 
			rowArray[3]=rs.getString("serialNo"); 
			rowArray[4]=rs.getString("serialNo1"); 
			rowArray[5]=rs.getString("organSName");	
			rowArray[6]=rs.getString("useUnit"); 
			rowArray[7]=rs.getString("useUnit1"); 
			rowArray[8]=rs.getString("useRelation"); 
			rowArray[9]=rs.getString("useDateS"); 
			rowArray[10]=rs.getString("useDateE"); 
			rowArray[11]=rs.getString("useArea"); 
			rowArray[12]=rs.getString("caseNo"); 
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


