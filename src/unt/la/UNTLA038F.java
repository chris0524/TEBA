/*
*<br>程式目的：土地合併分割作業－分割增加單管理資料
*<br>程式代號：untla038f
*<br>程式日期：0941005
*<br>程式作者：carey
*<br>--------------------------------------------------------
*<br>修改作者　　修改日期　　　修改目的
*<br>--------------------------------------------------------
*/

package unt.la;

import java.sql.ResultSet;
import java.util.ArrayList;

import util.Common;
import util.*;

public class UNTLA038F extends UNTLA027Q{


String enterOrg;
String enterOrgName;
String ownership;
String propertyNo;
String propertyName;
String serialNo;
String serialNo1;
String serialNo_old;
String serialNo1_old;
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
public String getSerialNo_old(){ return checkGet(serialNo_old); }
public void setSerialNo_old(String s){ serialNo_old=checkSet(s); }
public String getSerialNo1_old(){ return checkGet(serialNo1_old); }
public void setSerialNo1_old(String s){ serialNo1_old=checkSet(s); }
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

String fakeDivision;
String caseKind;
String holdDateS;
String holdDateE;
String userfeeDateS;
String userfeeDateE;
String dimArea;
String disType;
String disArea1;
String disArea2;
String disDateS;
String disDateE;
String checkSignno;
String signNo1;
String signNo2;
String signNo3;
String signNo4;
String signNo5;
public String getFakeDivision(){ return checkGet(fakeDivision); }
public void setFakeDivision(String s){ fakeDivision=checkSet(s); }
public String getCaseKind(){ return checkGet(caseKind); }
public void setCaseKind(String s){ caseKind=checkSet(s); }
public String getHoldDateS(){ return checkGet(holdDateS); }
public void setHoldDateS(String s){ holdDateS=checkSet(s); }
public String getHoldDateE(){ return checkGet(holdDateE); }
public void setHoldDateE(String s){ holdDateE=checkSet(s); }
public String getUserfeeDateS(){ return checkGet(userfeeDateS); }
public void setUserfeeDateS(String s){ userfeeDateS=checkSet(s); }
public String getUserfeeDateE(){ return checkGet(userfeeDateE); }
public void setUserfeeDateE(String s){ userfeeDateE=checkSet(s); }
public String getDimArea(){ return checkGet(dimArea); }
public void setDimArea(String s){ dimArea=checkSet(s); }
public String getDisType(){ return checkGet(disType); }
public void setDisType(String s){ disType=checkSet(s); }
public String getDisArea1(){ return checkGet(disArea1); }
public void setDisArea1(String s){ disArea1=checkSet(s); }
public String getDisArea2(){ return checkGet(disArea2); }
public void setDisArea2(String s){ disArea2=checkSet(s); }
public String getDisDateS(){ return checkGet(disDateS); }
public void setDisDateS(String s){ disDateS=checkSet(s); }
public String getDisDateE(){ return checkGet(disDateE); }
public void setDisDateE(String s){ disDateE=checkSet(s); }
public String getCheckSignno(){ return checkGet(checkSignno); }
public void setCheckSignno(String s){ checkSignno=checkSet(s); }
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

String addCaseNo1;
public String getAddCaseNo1(){ return checkGet(addCaseNo1); }
public void setAddCaseNo1(String s){ addCaseNo1=checkSet(s); }

//	傳回執行insert前之檢查sql
protected String[][] getInsertCheckSQL(){
	String[][] checkSQLArray = new String[1][4];
 	checkSQLArray[0][0]=" select count(*) as checkResult from UNTLA_Manage where 1=1 " + 
		" and enterOrg = " + Common.sqlChar(enterOrg) + 
		" and ownership = " + Common.sqlChar(ownership) + 
		" and propertyNo = " + Common.sqlChar(propertyNo) + 
		" and serialNo = " + Common.sqlChar(serialNo) +  
		" and serialNo1 = " + Common.sqlChar(serialNo1) + 
		"";
//System.out.println(checkSQLArray[0][0]); 	
	checkSQLArray[0][1]=">";
	checkSQLArray[0][2]="0";
	checkSQLArray[0][3]="該筆資料己重複，請重新輸入！";
	return checkSQLArray;
}


//	傳回insert sql
protected String[] getInsertSQL(){
	//取得管理次序
	Database db = new Database();
	ResultSet rs;
	String sql;
	try {	
		String sql_k="select a.serialNo1 from UNTLA_Manage a, UNTLA_ReduceDetail b " +
			" where a.enterorg=b.enterorg and a.ownership=b.ownership" +
			" and a.propertyno=b.propertyno and a.serialno=b.serialno" +
			" and a.enterOrg = " + Common.sqlChar(enterOrg) + 
			" and a.ownership = " + Common.sqlChar(ownership) + 
			" and a.propertyNo = " + Common.sqlChar(propertyNo) + 
			" and b.caseNo = " + Common.sqlChar(addCaseNo1) + 
			" and a.fakeDivision = " + Common.sqlChar(fakeDivision) + 
			"";	
//System.out.println(sql_k);	
		ResultSet rs_k = db.querySQL(sql_k);

			if (rs_k.next()){
				   int j = Integer.parseInt(rs_k.getString("serialNo1"))+1;
				   setSerialNo1(Integer.toString(j));
			}else{
				sql="select (max(a.serialNo1) + 1) as serialNo1 from UNTLA_Manage a, UNTLA_ReduceDetail b " +
					" where a.enterorg=b.enterorg and a.ownership=b.ownership" +
					" and a.propertyno=b.propertyno and a.serialno=b.serialno" +
					" and a.enterOrg = " + Common.sqlChar(enterOrg) + 
					" and a.ownership = " + Common.sqlChar(ownership) + 
					" and a.propertyNo = " + Common.sqlChar(propertyNo) + 
					" and b.caseNo = " + Common.sqlChar(addCaseNo1) + 
					"";	
				
				rs = db.querySQL(sql);
				if (rs.next()){
					int j = Integer.parseInt(rs.getString("serialNo1")) + 1;
				    setSerialNo1(Integer.toString(j));
				}else{
					setSerialNo1("0000001");
				}
			}		

		
		
	} catch (Exception e) {
		e.printStackTrace();
	} finally {
		db.closeAll();
	} 
			
	
		
	

//System.out.println(serialNo1);	
	String[] execSQLArray = new String[3];
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
			" fakeDivision,"+
			" caseKind,"+
			" caseNo,"+
			" holdDateS,"+
			" holdDateE,"+
			" userfeeDateS,"+
			" userfeeDateE,"+
			" dimArea,"+
			" disType,"+
			" disArea1,"+
			" disArea2,"+
			" disDateS,"+
			" disDateE,"+
			" isDefault,"+
			" editID,"+
			" editDate,"+
			" editTime"+
		" )Values(" +
			Common.sqlChar(Common.get(enterOrg)) + "," +
			Common.sqlChar(Common.get(ownership)) + "," +
			Common.sqlChar(Common.get(propertyNo)) + "," +
			Common.sqlChar(Common.get(serialNo)) + "," +
			Common.sqlChar(Common.get(serialNo1)) + "," +
			Common.sqlChar(Common.get(useUnit)) + "," +
			Common.sqlChar(Common.get(useUnit1)) + "," +
			Common.sqlChar(Common.get(useRelation)) + "," +
			Common.sqlChar(Common.get(useDateS)) + "," +
			Common.sqlChar(Common.get(useDateE)) + "," +
			Common.sqlChar(Common.get(useArea)) + "," +
			Common.sqlChar(Common.get(notes1)) + "," +
			Common.sqlChar(Common.get(notes)) + "," +
			Common.sqlChar(Common.get(fakeDivision)) + "," +
			Common.sqlChar(Common.get(caseKind)) + "," +
			Common.sqlChar(Common.get(caseNo)) + "," +
			Common.sqlChar(Common.get(holdDateS)) + "," +
			Common.sqlChar(Common.get(holdDateE)) + "," +
			Common.sqlChar(Common.get(userfeeDateS)) + "," +
			Common.sqlChar(Common.get(userfeeDateE)) + "," +
			Common.sqlChar(Common.get(dimArea)) + "," +
			Common.sqlChar(Common.get(disType)) + "," +
			Common.sqlChar(Common.get(useArea)) + "," +
			Common.sqlChar(Common.get(disArea2)) + "," +
			Common.sqlChar(Common.get(disDateS)) + "," +
			Common.sqlChar(Common.get(disDateE)) + "," +
			Common.sqlChar("1") + "," +
			Common.sqlChar(getEditID()) + "," +
			Common.sqlChar(getEditDate()) + "," +
			Common.sqlChar(getEditTime()) + ")" ;
//System.out.println(execSQLArray[0].toString());
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
		//取得該筆資料人檔新增
		ResultSet rs_p;	
		String sql_p=" select  a.enterOrg, a.ownership, a.propertyNo, a.applytype, a.applyid, a.applyname," +
				" a.hometel, a.mobile, a.liveadd4,a. payeryn, a.registryyn, a.notes, a.seq  "+
			" from UNTLA_Person a " +
			" where 1=1 " +
			" and a.enterOrg = " + Common.sqlChar(enterOrg) + 
			" and a.ownership = " + Common.sqlChar(ownership) + 
			" and a.propertyNo = " + Common.sqlChar(propertyNo) + 
			" and a.serialNo = " + Common.sqlChar(serialNo_old) + 
			" and a.serialNo1 = " + Common.sqlChar(serialNo1_old) +
			"";		
//System.out.println(sql_p);
		
//		try {			
			rs_p = db1.querySQL(sql_p);
			if (rs_p.next()){
				execSQLArray[2]=" insert into UNTLA_Person(" +
				" enterOrg,"+
				" ownership,"+
				" propertyNo,"+
				" serialNo,"+
				" serialNo1,"+
				" applytype,"+
				" applyid,"+
				" applyname,"+
				" hometel,"+
				" mobile,"+
				" liveadd4,"+
				" payeryn,"+
				" registryyn,"+
				" notes,"+
				" seq,"+
				" editID,"+
				" editDate,"+
				" editTime"+
			" )Values(" +
				Common.sqlChar(enterOrg) + "," +
				Common.sqlChar(ownership) + "," +
				Common.sqlChar(propertyNo) + "," +
				Common.sqlChar(serialNo) + "," +
				Common.sqlChar(serialNo1) + "," +
				Common.sqlChar(Common.get(rs_p.getString("applytype"))) + "," +
				Common.sqlChar(Common.get(rs_p.getString("applyid"))) + "," +
				Common.sqlChar(Common.get(rs_p.getString("applyname"))) + "," +
				Common.sqlChar(Common.get(rs_p.getString("hometel"))) + "," +
				Common.sqlChar(Common.get(rs_p.getString("mobile"))) + "," +
				Common.sqlChar(Common.get(rs_p.getString("liveadd4"))) + "," +
				Common.sqlChar(Common.get(rs_p.getString("payeryn"))) + "," +
				Common.sqlChar(Common.get(rs_p.getString("registryyn"))) + "," +
				Common.sqlChar(Common.get(rs_p.getString("notes"))) + "," +
				Common.sqlChar(Common.get(rs_p.getString("seq"))) + "," +
				Common.sqlChar(getEditID()) + "," +
				Common.sqlChar(getEditDate()) + "," +
				Common.sqlChar(getEditTime()) + ")" ;
				
			}else{
				execSQLArray[2]=" insert into UNTLA_Person(" +
					" enterOrg,"+
					" ownership,"+
					" propertyNo,"+
					" serialNo,"+
					" serialNo1,"+
					" applytype,"+
					" applyid,"+
					" applyname,"+
					" hometel,"+
					" mobile,"+
					" liveadd4,"+
					" payeryn,"+
					" registryyn,"+
					" notes,"+
					" seq,"+
					" editID,"+
					" editDate,"+
					" editTime"+
				" )Values(" +
					Common.sqlChar(enterOrg) + "," +
					Common.sqlChar(ownership) + "," +
					Common.sqlChar(propertyNo) + "," +
					Common.sqlChar(serialNo) + "," +
					Common.sqlChar(serialNo1) + "," +
					Common.sqlChar("") + "," +
					Common.sqlChar("0000000000") + "," +
					Common.sqlChar("姓名不詳") + "," +
					Common.sqlChar("") + "," +
					Common.sqlChar("") + "," +
					Common.sqlChar("") + "," +
					Common.sqlChar("Y") + "," +
					Common.sqlChar("") + "," +
					Common.sqlChar("") + "," +
					Common.sqlChar("01") + "," +
					Common.sqlChar(getEditID()) + "," +
					Common.sqlChar(getEditDate()) + "," +
					Common.sqlChar(getEditTime()) + ")" ;
			}
		
/*		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.closeAll();
		} 
		
*/
	} catch (Exception e) {
		e.printStackTrace();
	} finally {
		db1.closeAll();
	}
	
	return execSQLArray;
}


//	傳回update sql
protected String[] getUpdateSQL(){
	String[] execSQLArray = new String[2];
	execSQLArray[0]=" update UNTLA_Manage set " +
			" useUnit = " + Common.sqlChar(Common.get(useUnit)) + "," +
			" useUnit1 = " + Common.sqlChar(Common.get(useUnit1)) + "," +
			" useRelation = " + Common.sqlChar(Common.get(useRelation)) + "," +
			" useDateS = " + Common.sqlChar(Common.get(useDateS)) + "," +
			" useDateE = " + Common.sqlChar(Common.get(useDateE)) + "," +
			" useArea = " + Common.sqlChar(Common.get(useArea)) + "," +
			" disArea1 = " + Common.sqlChar(Common.get(useArea)) + "," +
			" fakeDivision = " + Common.sqlChar(Common.get(fakeDivision)) + "," +
			" notes1 = " + Common.sqlChar(Common.get(notes1)) + "," +
			" notes = " + Common.sqlChar(Common.get(notes)) + "," +
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


//	傳回delete sql
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

public UNTLA038F  queryOne() throws Exception{
	Database db = new Database();
	UNTLA038F obj = this;
	try {
		String sql=" select d.signno, a.fakeDivision, b.organSName, c.propertyName, a.enterOrg, a.ownership, a.propertyNo, a.serialNo, a.serialNo1, a.useUnit, a.useUnit1, a.useRelation, a.useDateS, a.useDateE, a.useArea, a.notes1, a.fakeDivision, a.ApproveYN, a.caseKind, a.caseNo, " +
			" a.caseKind, a.holdDateS, a.holdDateE, a.userfeeDateS, a.userfeeDateE, a.dimArea, a.disType, a.disArea1, a.disArea2, a.disDateS, a.disDateE," +
			" a.notes, a.editID, a.editDate, a.editTime "+
			" from UNTLA_Manage a, SYSCA_Organ b, SYSPK_PropertyCode c, UNTLA_land d where 1=1 " +
			" and a.enterOrg = d.enterOrg and a.ownership = d.ownership and a.propertyNo = d.propertyNo and a.serialNo = d.serialNo " +
			" and a.enterOrg = " + Common.sqlChar(enterOrg) +
			" and a.ownership = " + Common.sqlChar(ownership) +
			" and a.propertyNo = " + Common.sqlChar(propertyNo) +
			" and a.serialNo = " + Common.sqlChar(serialNo) +
			" and a.serialNo1 = " + Common.sqlChar(serialNo1) +
			" and b.organID(+) = a.useUnit " +
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
			obj.setFakeDivision(Common.get(rs.getString("fakeDivision")));
			obj.setSignNo1(rs.getString("signNo").substring(0,1)+"000000");
			obj.setSignNo2(rs.getString("signNo").substring(0,3)+"0000");
			obj.setSignNo3(rs.getString("signNo").substring(0,7));
			obj.setSignNo4(rs.getString("signNo").substring(7,11));
			obj.setSignNo5(rs.getString("signNo").substring(11));		
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
			" (select b.organSName from SYSCA_Organ b where a.useUnit = b.organID) as organSName," +
			" a.fakeDivision, a.enterOrg, a.ownership, a.propertyNo, a.serialNo, a.serialNo1, a.useUnit, a.useUnit1,c.codeName useRelation, a.useDateS, a.useDateE, a.useArea, a.caseNo, " +
			" d.signNo, "+
			" from UNTLA_Manage a,SYSCA_Code c, UNTLA_land d "+
			" where 1=1 "+
			"   and c.codeKindID='URE' "+
			"   and a.useRelation = c.codeID " +
			"   and a.enterOrg = d.enterOrg and a.ownership = d.ownership and a.propertyNo = d.propertyNo and a.serialNo = d.serialNo "+			
			"   and a.enterOrg = " + Common.sqlChar(enterOrg) +
			"   and a.ownership = " + Common.sqlChar(ownership) +
			"   and a.propertyNo = " + Common.sqlChar(propertyNo) +
			"   and a.serialNo = " + Common.sqlChar(serialNo) ;	
				
		ResultSet rs = db.querySQL(sql);
		while (rs.next()){
			counter++;
			
			String rowArray[]=new String[12];
			rowArray[0]=rs.getString("enterOrg"); 
			rowArray[1]=rs.getString("ownership"); 
			rowArray[2]=rs.getString("propertyNo"); 
			rowArray[3]=rs.getString("serialNo"); 
			rowArray[4]=rs.getString("serialNo1"); 
			rowArray[5]=getSignDescName(rs.getString("signNo").substring(0,7)) + rs.getString("signNo").substring(7,11) + '-' + rs.getString("signNo").substring(11) + " "+Common.get(rs.getString("fakeDivision")); 
			rowArray[6]=rs.getString("organSName");	
			//rowArray[7]=rs.getString("useUnit"); 
			//rowArray[8]=rs.getString("useUnit1"); 
			rowArray[7]=rs.getString("useRelation"); 
			rowArray[8]=rs.getString("useDateS"); 
			rowArray[9]=rs.getString("useDateE"); 
			rowArray[10]=rs.getString("useArea"); 
			rowArray[11]=rs.getString("caseNo"); 
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


private String getSignDescName(String signNo){
	Database db = null;
	ResultSet rs = null;
	String sql = null;
	String result = null;
	try{
		sql = "select signdesc from SYSCA_SIGN where" +
				" signNo = " + Common.sqlChar(signNo);
		
		db = new Database();
		rs = db.querySQL(sql);
		if(rs.next()){				
			result = rs.getString("signdesc");
		}
		rs.close();
	}catch(Exception e){
//		System.out.println("getSignDescName Exception => " + e.getMessage());
	}finally{
		db.closeAll();
	}
	return result;
}

}


