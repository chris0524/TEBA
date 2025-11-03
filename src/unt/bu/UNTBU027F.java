/*
*<br>程式目的：建物所有權狀領狀紀錄
*<br>程式代號：untbu027f
*<br>程式日期：0940920
*<br>程式作者：clive.chang
*<br>--------------------------------------------------------
*<br>修改作者　　修改日期　　　修改目的
*<br>--------------------------------------------------------
*/

package unt.bu;

import java.sql.ResultSet;
import java.util.ArrayList;
import util.*;

public class UNTBU027F extends SuperBean{


String enterOrg;
String enterOrgName;
String ownership;
String propertyNo;
String signNo;
String signNo1;
String signNo2;
String signNo3;
String signNo4;
String signNo5;
String serialNo;
String serialNo1;
String drawDate;
String propertyName;
String drawName;
String drawCause;
String drawCause1;
String returnDate;
String notes;
String oldPropertyNo;
String oldSerialNo;
String dataState;
String dataStateName;

String q_enterOrg;
String q_enterOrgName;
String q_dataState;
String q_ownership;
String q_signNo1;
String q_signNo2;
String q_signNo3;
String q_signNo4;
String q_signNo5;
String q_signNo = "";
String q_drawDateS;
String q_drawDateE;
String q_drawName;
String q_drawCause;
String ownershipDate;
String proofDoc;

public String getOwnershipDate(){ return checkGet(ownershipDate); }
public void setOwnershipDate(String s){ ownershipDate=checkSet(s); }
public String getProofDoc(){ return checkGet(proofDoc); }
public void setProofDoc(String s){ proofDoc=checkSet(s); }

public String getEnterOrg(){ return checkGet(enterOrg); }
public void setEnterOrg(String s){ enterOrg=checkSet(s); }
public String getEnterOrgName(){ return checkGet(enterOrgName); }
public void setEnterOrgName(String s){ enterOrgName=checkSet(s); }
public String getOwnership(){ return checkGet(ownership); }
public void setOwnership(String s){ ownership=checkSet(s); }
public String getPropertyNo(){ return checkGet(propertyNo); }
public void setPropertyNo(String s){ propertyNo=checkSet(s); }
public String getSerialNo(){ return checkGet(serialNo); }
public void setSerialNo(String s){ serialNo=checkSet(s); }
public String getSerialNo1(){ return checkGet(serialNo1); }
public void setSerialNo1(String s){ serialNo1=checkSet(s); }
public String getDrawDate(){ return checkGet(drawDate); }
public void setDrawDate(String s){ drawDate=checkSet(s); }
public String getPropertyName(){ return checkGet(propertyName); }
public void setPropertyName(String s){ propertyName=checkSet(s); }
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
public String getSignNo1(){ return checkGet(signNo1); }
public void setSignNo1(String s){ signNo1=checkSet(s); }
public String getSignNo2(){ return checkGet(signNo2); }
public void setSignNo2(String s){ signNo2=checkSet(s); }
public String getSignNo3(){ return checkGet(signNo3); }
public void setSignNo3(String s){ signNo3=checkSet(s); }
public String getSignNo4(){ return checkGet(signNo4); }
public void setSignNo4(String s){ signNo4=checkSet(s); }
public String getSignNo5(){ return checkGet(signNo5); }
public void setSignNo5(String s){ signNo5=checkSet(s); }
public String getSignNo(){ return checkGet(signNo); }
public void setSignNo(String s){ signNo=checkSet(s); }
public String getDataState(){ return checkGet(dataState); }
public void setDataState(String s){ dataState=checkSet(s); }
public String getDataStateName(){ return checkGet(dataStateName); }
public void setDataStateName(String s){ dataStateName=checkSet(s); }

public String getQ_enterOrg(){ return checkGet(q_enterOrg); }
public void setQ_enterOrg(String s){ q_enterOrg=checkSet(s); }
public String getQ_enterOrgName(){ return checkGet(q_enterOrgName); }
public void setQ_enterOrgName(String s){ q_enterOrgName=checkSet(s); }; 
public String getQ_dataState(){ return checkGet(q_dataState); }
public void setQ_dataState(String s){ q_dataState=checkSet(s); }
public String getQ_ownership() { return checkGet(q_ownership); }
public void setQ_ownership(String s) { q_ownership=checkSet(s); }
public String getQ_signNo1(){ return checkGet(q_signNo1); }
public void setQ_signNo1(String s){ q_signNo1=checkSet(s); }
public String getQ_signNo2(){ return checkGet(q_signNo2); }
public void setQ_signNo2(String s){ q_signNo2=checkSet(s); }
public String getQ_signNo3(){ return checkGet(q_signNo3); }
public void setQ_signNo3(String s){ q_signNo3=checkSet(s); }
public String getQ_signNo4(){ return checkGet(q_signNo4); }
public void setQ_signNo4(String s){ q_signNo4=checkSet(s); }
public String getQ_signNo5(){ return checkGet(q_signNo5); }
public void setQ_signNo5(String s){ q_signNo5=checkSet(s); }


public String getQ_drawDateS(){ return checkGet(q_drawDateS); }
public void setQ_drawDateS(String s){ q_drawDateS=checkSet(s);}; 
public String getQ_drawDateE(){ return checkGet(q_drawDateE); }
public void setQ_drawDateE(String s){ q_drawDateE=checkSet(s); }; 
public String getQ_drawName() { return checkGet(q_drawName); }
public void setQ_drawName(String s) { q_drawName=checkSet(s); }
public String getQ_drawCause() { return checkGet(q_drawCause); }
public void setQ_drawCause(String s) { q_drawCause=checkSet(s); }

String isOrganManager;
String isAdminManager;
String organID;    
public String getOrganID() { return checkGet(organID); }
public void setOrganID(String s) { organID = checkSet(s); }
public String getIsOrganManager() { return checkGet(isOrganManager); }
public void setIsOrganManager(String s) { isOrganManager = checkSet(s); }
public String getIsAdminManager() { return checkGet(isAdminManager); }
public void setIsAdminManager(String s) { isAdminManager = checkSet(s); } 

//傳回執行insert前之檢查sql
protected String[][] getInsertCheckSQL(){
	String[][] checkSQLArray = new String[1][4];
 	checkSQLArray[0][0]=" select count(*) as checkResult from UNTBU_DRAWPROOF where 1=1 " + 
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

	//取得自動編號
	Database db = new Database();
	ResultSet rs;	
	String sql="select max(serialNo1)+1 as serialNo1 from UNTBU_DRAWPROOF " +
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
	//組合土地編號'
	String s1, s2, s3, s4, s5;
	s1 = getSignNo1().substring(0,1);
	s2 = getSignNo2().substring(1,3);
	s3 = getSignNo3().substring(3,7);	
	s4 = Common.formatFrontZero(getSignNo4(),5);
	s5 = Common.formatFrontZero(getSignNo5(),3);
	signNo=s1+s2+s3+s4+s5;		
	
	String[] execSQLArray = new String[1];
	execSQLArray[0]=" insert into UNTBU_DRAWPROOF(" +
			" enterOrg,"+
			" ownership,"+
			" propertyNo,"+
			//" signNo,"+
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
			//Common.sqlChar(signNo) + "," +			
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
	execSQLArray[0]=" update UNTBU_DRAWPROOF set " +
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
	execSQLArray[0]=" delete UNTBU_DRAWPROOF where 1=1 " +
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

public UNTBU027F  queryOne() throws Exception{
	Database db = new Database();
	UNTBU027F obj = this;
	try {
		String sql=" select a.enterOrg, e.organsname, a.ownership, a.propertyNo, b.propertyName, d.signNo, "+
			", a.serialNo, a.serialNo1, a.drawDate, a.drawName, a.drawCause, a.drawCause1 " + 
			", a.returnDate, a.notes, a.oldPropertyNo, a.oldSerialNo, a.editID, a.editDate, a.editTime "+
			", d.dataState, d.ownershipDate, d.proofDoc, case when d.dataState='2' then '已減損' else '現存' end as dataStateName " +
			" from UNTBU_DRAWPROOF a, SYSPK_PropertyCode b, UNTBU_Building d, SYSCA_ORGAN e where 1=1 " +			
			" and a.enterOrg=e.organid and a.propertyNo=b.propertyNo " +
			" and a.enterOrg=d.enterOrg and a.ownership=d.ownership and a.propertyNo=d.propertyNo and a.serialNo=d.serialNo "+
			" and a.enterOrg = " + Common.sqlChar(enterOrg) +
			" and a.ownership = " + Common.sqlChar(ownership) +
			" and a.propertyNo = " + Common.sqlChar(propertyNo) +
			" and a.serialNo = " + Common.sqlChar(serialNo) +
			" and a.serialNo1 = " + Common.sqlChar(serialNo1) +
			"";
		ResultSet rs = db.querySQL(sql);
		if (rs.next()){
			obj.setEnterOrg(rs.getString("enterOrg"));
			obj.setEnterOrgName(rs.getString("organsname"));
			obj.setOwnership(rs.getString("ownership"));
			obj.setPropertyNo(rs.getString("propertyNo"));	
			obj.setPropertyName(rs.getString("propertyName"));
			obj.setSerialNo(rs.getString("serialNo"));
			obj.setProofDoc(rs.getString("proofDoc"));
			obj.setOwnershipDate(rs.getString("ownershipDate"));
			obj.setSerialNo1(rs.getString("serialNo1"));
			obj.setDrawDate(rs.getString("drawDate"));
			obj.setPropertyName(rs.getString("propertyName"));
			obj.setDrawName(rs.getString("drawName"));
			obj.setDrawCause(rs.getString("drawCause"));
			obj.setDrawCause1(rs.getString("drawCause1"));
			obj.setReturnDate(rs.getString("returnDate"));
			obj.setNotes(rs.getString("notes"));
			obj.setOldPropertyNo(rs.getString("oldPropertyNo"));
			obj.setOldSerialNo(rs.getString("oldSerialNo"));
			obj.setDataState(rs.getString("dataState"));
			obj.setDataStateName(rs.getString("dataStateName"));
			obj.setEditID(rs.getString("editID"));
			obj.setEditDate(rs.getString("editDate"));
			obj.setEditTime(rs.getString("editTime"));
			
			obj.setSignNo1(rs.getString("signNo").substring(0,1)+"000000");
			obj.setSignNo2(rs.getString("signNo").substring(0,3)+"0000");
			obj.setSignNo3(rs.getString("signNo").substring(0,7));
			obj.setSignNo4(rs.getString("signNo").substring(7,12));
			obj.setSignNo5(rs.getString("signNo").substring(12,15));
			
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
		String sql=" select a.enterOrg, a.ownership, case when a.ownership='1' then '市有' else '國有' end as ownershipName, a.propertyNo, b.propertyName, e.signNo, a.serialNo, a.serialNo1, a.drawDate, a.drawName "+
			", a.returnDate, a.drawCause, d.codeName as drawCauseName, e.dataState " +
			" from UNTBU_DRAWPROOF a, SYSPK_PropertyCode b, SYSCA_CODE d, UNTBU_Building e where 1=1 "+
			" and a.propertyNo=b.propertyNo " +
			" and d.codeKindID='DCA' and d.codeID=a.drawCause "+
			" and a.enterOrg=e.enterOrg and a.ownership=e.ownership and a.propertyNo=e.propertyNo and a.serialNo=e.serialNo ";

		if ("".equals(getIsAdminManager()) || "N".equals(getIsAdminManager())) {
			if ("Y".equals(getIsOrganManager())) {
				sql += " and a.enterOrg like '" + getOrganID().substring(0,5) + "%' ";					
			} else {
				sql+=" and a.enterOrg = " + Common.sqlChar(getOrganID()) ;
			}
		}	
		if (!"".equals(getQ_ownership()))
			sql+=" and a.ownership = " + Common.sqlChar(getQ_ownership()) ;
		if (!"".equals(getQ_signNo1()))
			q_signNo=getQ_signNo1().substring(0,1)+"______";
		if (!"".equals(getQ_signNo2()))
			q_signNo=getQ_signNo2().substring(0,3)+"____";			
		if (!"".equals(getQ_signNo3())){
			if (getQ_signNo3().length()==4){
				q_signNo="E__" + getQ_signNo3();
			}else{
				q_signNo=getQ_signNo3();
			}	
		}
		if (!"".equals(getQ_signNo4())){
			setQ_signNo4(Common.formatFrontZero(getQ_signNo4(),5));
			setQ_signNo5(Common.formatFrontZero(getQ_signNo5(),3));	
			if ("".equals(q_signNo)){
				q_signNo="_______"+getQ_signNo4()+getQ_signNo5();
			}else{
				q_signNo=q_signNo+getQ_signNo4()+getQ_signNo5();				
			}
		}	

		if (!"".equals(q_signNo))
			sql+=" and e.signNo like '" + q_signNo + "%'" ;
		
		if (!"".equals(getQ_drawDateS()) && !"".equals(getQ_drawDateE())) {
			sql+=" and a.drawDate between " + Common.sqlChar(getQ_drawDateS()) + " and " + Common.sqlChar(getQ_drawDateE());		
		} else if (!"".equals(getQ_drawDateS())) {
			sql+=" and a.drawDate=" + Common.sqlChar(getQ_drawDateS());
		}
		
		if (!"".equals(getQ_drawName()))
			sql+=" and a.drawName like '%" + getQ_drawName() + "%'";
		
		if (!"".equals(getQ_drawCause()))
			sql+=" and a.drawCause=" + Common.sqlChar(getQ_drawCause());
		
		if (!"".equals(getQ_dataState()))
			sql+=" and e.dataState =" + Common.sqlChar(getQ_dataState());
		
		sql+=" order by a.enterOrg, a.ownership, e.signNo ";
			
		ResultSet rs = db.querySQL(sql);
		while (rs.next()){
			counter++;
			String rowArray[]=new String[13];
			rowArray[0]=rs.getString("ownershipName");			
			rowArray[1]=rs.getString("propertyName");			
			rowArray[2]=getSignDescName(rs.getString("signNo").substring(0,7)) + rs.getString("signNo").substring(7,12) + '-' + rs.getString("signNo").substring(12);
			rowArray[3]=rs.getString("drawCauseName");
			rowArray[4]=rs.getString("drawName");
			rowArray[5]=rs.getString("drawDate");
			rowArray[6]=rs.getString("returnDate");
			rowArray[7]=rs.getString("enterOrg"); 
			rowArray[8]=rs.getString("ownership"); 
			rowArray[9]=rs.getString("propertyNo"); 
			rowArray[10]=rs.getString("serialNo"); 
			rowArray[11]=rs.getString("serialNo1");
 
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
		System.out.println("getSignDescName Exception => " + e.getMessage());
	}finally{
		db.closeAll();
	}
	return result;
}
}
