/*
*<br>程式目的：權利減少作業-減損單明細
*<br>程式代號：untrt006f
*<br>程式日期：0941006
*<br>程式作者：cherry
*<br>--------------------------------------------------------
*<br>修改作者　　修改日期　　　修改目的
*<br>--------------------------------------------------------
*/

package unt.rt;

import java.sql.ResultSet;
import java.util.ArrayList;
import util.*;

public class UNTRT006F extends UNTRT005Q{


String enterOrg;
String enterOrgName;
String ownership;
String caseNo;
String propertyNo;
String serialNo;
String serialNo1;
String oldSerialNo1;
String signNo;
String signName;
String setObligor;
String area;
String holdNume;
String holdDeno;
String holdArea;
String setArea;
String bookValue;
String notes;

String verify;
String checkVerify;
String initDtl;
String reduceBookValue;
String newBookValue;

public String getReduceBookValue(){ return checkGet(reduceBookValue); }
public void setReduceBookValue(String s){ reduceBookValue=checkSet(s); }
public String getNewBookValue(){ return checkGet(newBookValue); }
public void setNewBookValue(String s){ newBookValue=checkSet(s); }
public String getVerify(){ return checkGet(verify); }
public void setVerify(String s){ verify=checkSet(s); }
public String getCheckVerify(){ return checkGet(checkVerify); }
public void setCheckVerify(String s){ checkVerify=checkSet(s); }
public String getInitDtl(){ return checkGet(initDtl); }
public void setInitDtl(String s){ initDtl=checkSet(s); }

public String getEnterOrg(){ return checkGet(enterOrg); }
public void setEnterOrg(String s){ enterOrg=checkSet(s); }
public String getEnterOrgName(){ return checkGet(enterOrgName); }
public void setEnterOrgName(String s){ enterOrgName=checkSet(s); }
public String getOwnership(){ return checkGet(ownership); }
public void setOwnership(String s){ ownership=checkSet(s); }
public String getCaseNo(){ return checkGet(caseNo); }
public void setCaseNo(String s){ caseNo=checkSet(s); }
public String getPropertyNo(){ return checkGet(propertyNo); }
public void setPropertyNo(String s){ propertyNo=checkSet(s); }
public String getSerialNo(){ return checkGet(serialNo); }
public void setSerialNo(String s){ serialNo=checkSet(s); }
public String getSerialNo1(){ return checkGet(serialNo1); }
public void setSerialNo1(String s){ serialNo1=checkSet(s); }
public String getOldSerialNo1(){ return checkGet(oldSerialNo1); }
public void setOldSerialNo1(String s){ oldSerialNo1=checkSet(s); }
public String getSignNo(){ return checkGet(signNo); }
public void setSignNo(String s){ signNo=checkSet(s); }
public String getSignName(){ return checkGet(signName); }
public void setSignName(String s){ signName=checkSet(s); }
public String getSetObligor(){ return checkGet(setObligor); }
public void setSetObligor(String s){ setObligor=checkSet(s); }
public String getArea(){ return checkGet(area); }
public void setArea(String s){ area=checkSet(s); }
public String getHoldNume(){ return checkGet(holdNume); }
public void setHoldNume(String s){ holdNume=checkSet(s); }
public String getHoldDeno(){ return checkGet(holdDeno); }
public void setHoldDeno(String s){ holdDeno=checkSet(s); }
public String getHoldArea(){ return checkGet(holdArea); }
public void setHoldArea(String s){ holdArea=checkSet(s); }
public String getSetArea(){ return checkGet(setArea); }
public void setSetArea(String s){ setArea=checkSet(s); }
public String getBookValue(){ return checkGet(bookValue); }
public void setBookValue(String s){ bookValue=checkSet(s); }
public String getNotes(){ return checkGet(notes); }
public void setNotes(String s){ notes=checkSet(s); }

//傳回執行insert前之檢查sql
protected String[][] getInsertCheckSQL(){
	//取得減少帳面設定價值
	UNTRT006F obj = this;
	Database dbR = new Database();
	ResultSet rsR;	
	String sqlR="select nvl(sum(bookValue),0) as bookValue from UNTRT_ReduceDetail where 1=1" +
				" and enterOrg = " + Common.sqlChar(enterOrg) + 
				" and ownership = " + Common.sqlChar(ownership) + 
				" and caseNo = " + Common.sqlChar(caseNo) + 
				" and propertyNo = " + Common.sqlChar(propertyNo) + 
				" and serialNo = " + Common.sqlChar(serialNo) + 
				"";		
	try {		
		rsR = dbR.querySQL(sqlR);
		if (rsR.next()){
			obj.setReduceBookValue(Float.parseFloat(rsR.getString("bookValue"))+Float.parseFloat(bookValue)+"");
		} else {
			obj.setReduceBookValue(bookValue);
		}

		//取得新帳面設定價值
		ResultSet rsN;	
		String sqlN="select nvl(oldBookValue,0) as oldBookValue, nvl(newBookValue,0) as newBookValue from UNTRT_ReduceProof where 1=1" +
					" and enterOrg = " + Common.sqlChar(enterOrg) + 
					" and ownership = " + Common.sqlChar(ownership) + 
					" and caseNo = " + Common.sqlChar(caseNo) + 
					" and propertyNo = " + Common.sqlChar(propertyNo) + 
					" and serialNo = " + Common.sqlChar(serialNo) + 
					"";		
		rsN = dbR.querySQL(sqlN);
		if (rsN.next()){
			obj.setNewBookValue(Float.parseFloat(rsN.getString("oldBookValue"))-Float.parseFloat(obj.getReduceBookValue())+"");
		} else {
			obj.setNewBookValue(rsN.getString("newBookValue"));
		}
	} catch (Exception e) {
		e.printStackTrace();
	} finally {
		dbR.closeAll();
	}
	String[][] checkSQLArray = new String[1][4];
 	checkSQLArray[0][0]=" select count(*) as checkResult from UNTRT_ReduceDetail where 1=1 " + 
		" and enterOrg = " + Common.sqlChar(enterOrg) + 
		" and ownership = " + Common.sqlChar(ownership) + 
		" and caseNo = " + Common.sqlChar(caseNo) + 
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
	UNTRT006F obj = this;
	String[] execSQLArray = new String[2];
	execSQLArray[0]=" insert into UNTRT_ReduceDetail(" +
					" enterOrg,"+
					" ownership,"+
					" caseNo,"+
					" propertyNo,"+
					" serialNo,"+
					" serialNo1,"+
					" oldSerialNo1,"+
					" signNo,"+
					" setObligor,"+
					" area,"+
					" holdNume,"+
					" holdDeno,"+
					" holdArea,"+
					" setArea,"+
					" bookValue,"+
					" notes,"+
					" editID,"+
					" editDate,"+
					" editTime"+
				" )Values(" +
					Common.sqlChar(enterOrg) + "," +
					Common.sqlChar(ownership) + "," +
					Common.sqlChar(caseNo) + "," +
					Common.sqlChar(propertyNo) + "," +
					Common.sqlChar(serialNo) + "," +
					Common.sqlChar(serialNo1) + "," +
					Common.sqlChar(oldSerialNo1) + "," +
					Common.sqlChar(signNo) + "," +
					Common.sqlChar(setObligor) + "," +
					Common.sqlChar(area) + "," +
					Common.sqlChar(holdNume) + "," +
					Common.sqlChar(holdDeno) + "," +
					Common.sqlChar(holdArea) + "," +
					Common.sqlChar(setArea) + "," +
					Common.sqlChar(bookValue) + "," +
					Common.sqlChar(notes) + "," +
					Common.sqlChar(getEditID()) + "," +
					Common.sqlChar(getEditDate()) + "," +
					Common.sqlChar(getEditTime()) + ")" ;
	execSQLArray[1]=" update UNTRT_ReduceProof set " +
					" reduceBookValue = " + obj.getReduceBookValue() + ","+
					" newBookValue = " + obj.getNewBookValue() +
				" where 1=1 " + 
					" and enterOrg = " + Common.sqlChar(enterOrg) +
					" and ownership = " + Common.sqlChar(ownership) +
					" and caseNo = " + Common.sqlChar(caseNo) +
					" and propertyNo = " + Common.sqlChar(propertyNo) +
					" and serialNo = " + Common.sqlChar(serialNo) +
					"";	
	return execSQLArray;
}


//傳回update sql
protected String[] getUpdateSQL(){
	String[] execSQLArray = new String[1];
	execSQLArray[0]=" update UNTRT_ReduceDetail set " +
					" oldSerialNo1 = " + Common.sqlChar(oldSerialNo1) + "," +
					" signNo = " + Common.sqlChar(signNo) + "," +
					" setObligor = " + Common.sqlChar(setObligor) + "," +
					" area = " + Common.sqlChar(area) + "," +
					" holdNume = " + Common.sqlChar(holdNume) + "," +
					" holdDeno = " + Common.sqlChar(holdDeno) + "," +
					" holdArea = " + Common.sqlChar(holdArea) + "," +
					" setArea = " + Common.sqlChar(setArea) + "," +
					" bookValue = " + Common.sqlChar(bookValue) + "," +
					" notes = " + Common.sqlChar(notes) + "," +
					" editID = " + Common.sqlChar(getEditID()) + "," +
					" editDate = " + Common.sqlChar(getEditDate()) + "," +
					" editTime = " + Common.sqlChar(getEditTime()) + 
				" where 1=1 " + 
					" and enterOrg = " + Common.sqlChar(enterOrg) +
					" and ownership = " + Common.sqlChar(ownership) +
					" and caseNo = " + Common.sqlChar(caseNo) +
					" and propertyNo = " + Common.sqlChar(propertyNo) +
					" and serialNo = " + Common.sqlChar(serialNo) +
					" and serialNo1 = " + Common.sqlChar(serialNo1) +
					"";
	return execSQLArray;
}

//傳回執行delete前之檢查sql
protected void getDeleteQuerySQL(){
	//取得減少帳面設定價值
	UNTRT006F obj = this;
	Database dbR = new Database();
	ResultSet rsR;	
	String sqlR="select nvl(sum(bookValue),0) as bookValue from UNTRT_ReduceDetail where 1=1" +
				" and enterOrg = " + Common.sqlChar(enterOrg) + 
				" and ownership = " + Common.sqlChar(ownership) + 
				" and caseNo = " + Common.sqlChar(caseNo) + 
				" and propertyNo = " + Common.sqlChar(propertyNo) + 
				" and serialNo = " + Common.sqlChar(serialNo) + 
				" and serialNo1 != " + Common.sqlChar(serialNo1) + 
				"";	
	try {		
		rsR = dbR.querySQL(sqlR);
		if (rsR.next()){
			obj.setReduceBookValue(Float.parseFloat(rsR.getString("bookValue"))+"");
		} else {
			obj.setReduceBookValue(bookValue);
		}
	} catch (Exception e) {
		e.printStackTrace();
	} finally {
		dbR.closeAll();
	}
	//取得新帳面設定價值
	Database dbN = new Database();
	ResultSet rsN;	
	String sqlN="select nvl(oldBookValue,0) as oldBookValue, nvl(newBookValue,0) as newBookValue from UNTRT_ReduceProof where 1=1" +
				" and enterOrg = " + Common.sqlChar(enterOrg) + 
				" and ownership = " + Common.sqlChar(ownership) + 
				" and caseNo = " + Common.sqlChar(caseNo) + 
				" and propertyNo = " + Common.sqlChar(propertyNo) + 
				" and serialNo = " + Common.sqlChar(serialNo) + 
				"";		
	try {		
		rsN = dbN.querySQL(sqlN);
		if (rsN.next()){
			obj.setNewBookValue(Float.parseFloat(rsN.getString("oldBookValue"))-Float.parseFloat(obj.getReduceBookValue())+"");
		} else {
			obj.setNewBookValue(rsN.getString("newBookValue"));
		}
	} catch (Exception e) {
		e.printStackTrace();
	} finally {
		dbN.closeAll();
	}
}

//傳回delete sql
protected String[] getDeleteSQL(){
	UNTRT006F obj = this;
	getDeleteQuerySQL();
	String[] execSQLArray = new String[2];
	execSQLArray[0]=" delete UNTRT_ReduceDetail where 1=1 " +
					" and enterOrg = " + Common.sqlChar(enterOrg) +
					" and ownership = " + Common.sqlChar(ownership) +
					" and caseNo = " + Common.sqlChar(caseNo) +
					" and propertyNo = " + Common.sqlChar(propertyNo) +
					" and serialNo = " + Common.sqlChar(serialNo) +
					" and serialNo1 = " + Common.sqlChar(serialNo1) +
					"";
	execSQLArray[1]=" update UNTRT_ReduceProof set " +
					" reduceBookValue = " + obj.getReduceBookValue() + ","+
					" newBookValue = " + obj.getNewBookValue() +
				" where 1=1 " + 
					" and enterOrg = " + Common.sqlChar(enterOrg) +
					" and ownership = " + Common.sqlChar(ownership) +
					" and caseNo = " + Common.sqlChar(caseNo) +
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

public UNTRT006F  queryOne() throws Exception{
	Database db = new Database();
	UNTRT006F obj = this;
	try {
		String sql=" select a.enterOrg, a.ownership, a.caseNo, a.propertyNo, a.serialNo, a.serialNo1, " +
					" a.signNo, a.setObligor, a.area, a.holdNume, a.holdDeno, a.holdArea, a.setArea, " +
					" a.bookValue, a.notes, a.editID, a.editDate, a.editTime, a.signNo, " +
					" c.organSName, b.propertyName, d.signDesc as signName, e.verify, a.oldSerialNo1 "+
					" from UNTRT_ReduceDetail a, SYSPK_PropertyCode b, SYSCA_Organ c, SYSCA_SIGN d, UNTRT_ReduceProof e where 1=1 " +
					" and a.propertyNo = b.propertyNo and a.enterOrg = c.organID and substr(a.signNo,1,7)= d.signNo " +
					" and a.enterOrg=e.enterOrg and a.ownership=e.ownership and a.propertyNo=e.propertyNo and a.serialNo=e.serialNo and a.caseNo=e.caseNo"+ 
					" and a.enterOrg = " + Common.sqlChar(enterOrg) +
					" and a.ownership = " + Common.sqlChar(ownership) +
					" and a.caseNo = " + Common.sqlChar(caseNo) +
					" and a.propertyNo = " + Common.sqlChar(propertyNo) +
					" and a.serialNo = " + Common.sqlChar(serialNo) +
					" and a.serialNo1 = " + Common.sqlChar(serialNo1) +
					"";
		ResultSet rs = db.querySQL(sql);
		if (rs.next()){
			obj.setEnterOrg(rs.getString("enterOrg"));
			obj.setEnterOrgName(rs.getString("organSName"));
			obj.setOwnership(rs.getString("ownership"));
			obj.setCaseNo(rs.getString("caseNo"));
			obj.setPropertyNo(rs.getString("propertyNo"));
			obj.setPropertyNoName(rs.getString("propertyName"));
			obj.setSerialNo(rs.getString("serialNo"));
			obj.setSerialNo1(rs.getString("serialNo1"));
			obj.setOldSerialNo1(rs.getString("oldSerialNo1"));
			obj.setSignNo(rs.getString("signNo"));
			obj.setSignName(rs.getString("signName")+rs.getString("signNo").substring(7,11)+"-"+rs.getString("signNo").substring(11));
			obj.setSetObligor(rs.getString("setObligor"));
			obj.setArea(rs.getString("area"));
			obj.setHoldNume(rs.getString("holdNume"));
			obj.setHoldDeno(rs.getString("holdDeno"));
			obj.setHoldArea(rs.getString("holdArea"));
			obj.setSetArea(rs.getString("setArea"));
			obj.setBookValue(rs.getString("bookValue"));
			obj.setNotes(rs.getString("notes"));
			obj.setEditID(rs.getString("editID"));
			obj.setEditDate(rs.getString("editDate"));
			obj.setEditTime(rs.getString("editTime"));
			obj.setVerify(rs.getString("verify"));
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
	//UNTRT006F obj = this;
	Database db = new Database();
	ArrayList objList=new ArrayList();
	int counter=0;
	try {
		String sql=" select a.enterOrg, a.ownership, a.caseNo, a.propertyNo, a.serialNo, a.serialNo1, a.setObligor, a.bookValue, " +
					"a.signNo, b.signDesc as signName, c.verify"+
					" from UNTRT_ReduceDetail a, SYSCA_SIGN b, UNTRT_ReduceProof c where 1=1 " +
					" and substr(a.signNo,1,7)= b.signNo and a.enterOrg=c.enterOrg and a.ownership=c.ownership and a.propertyNo=c.propertyNo and a.serialNo=c.serialNo and a.caseNo=c.caseNo"; 
		if ("reduceProof".equals(getQuerySelect()) || "".equals(getQuerySelect())) {
			sql+=" and a.enterOrg = " + Common.sqlChar(getEnterOrg()) ;
			sql+=" and a.ownership = " + Common.sqlChar(getOwnership()) ;
			sql+=" and a.caseNo = " + Common.sqlChar(getCaseNo()) ;
			sql+=" and a.propertyNo = " + Common.sqlChar(getPropertyNo()) ;
			sql+=" and a.serialNo=" + Common.sqlChar(getSerialNo());
		} else {
			if (!"".equals(getQ_enterOrg())) {
				sql+=" and a.enterOrg = " + Common.sqlChar(getQ_enterOrg()) ;
			} else {
				if (!getIsAdminManager().equalsIgnoreCase("Y")) {
					if ("Y".equals(getIsOrganManager())) {					
						//sql += " and a.enterOrg like '" + getOrganID().substring(0,5) + "%' ";
						sql += " and ( a.enterOrg = "+ Common.sqlChar(getOrganID()) +" or a.enterOrg in ( select organID from SYSCA_Organ where organSuperior = "+ Common.sqlChar(getOrganID()) +" ) ) ";		
					} else {
						sql+=" and a.enterOrg = " + Common.sqlChar(getOrganID()) ;
					}
				}
			}
			if (!"".equals(getQ_ownership()))
				sql+=" and a.ownership = " + Common.sqlChar(getQ_ownership()) ;
			if (!"".equals(getQ_caseNoS()))
				sql+=" and a.caseNo >= " + Common.sqlChar(Common.formatRearString(getQ_caseNoS(),10,'0'));		
			if (!"".equals(getQ_caseNoE()))
				sql+=" and a.caseNo<=" + Common.sqlChar(Common.formatRearString(getQ_caseNoE(),10, '9'));
			if (!"".equals(getQ_caseName()))
				sql+=" and c.caseName  like " + Common.sqlChar(getQ_caseName()+"%") ;
			if (!"".equals(getQ_propertyNoS()))
				sql+=" and a.propertyNo >= " + Common.sqlChar(getQ_propertyNoS());		
			if (!"".equals(getQ_propertyNoE()))
				sql+=" and a.propertyNo<=" + Common.sqlChar(getQ_propertyNoE());						
			if (!"".equals(getQ_serialNoS()))
				sql+=" and a.serialNo >= " + Common.sqlChar(getQ_serialNoS());		
			if (!"".equals(getQ_serialNoE()))
				sql+=" and a.serialNo <= " + Common.sqlChar(getQ_serialNoE());			
			if (!"".equals(getQ_cause()))
				sql+=" and c.cause = " + Common.sqlChar(getQ_cause()) ;
			if (!"".equals(getQ_writeDateS()))
				sql+=" and c.writeDate >= " + Common.sqlChar(new Datetime().changeTaiwanYYMMDD(getQ_writeDateS(),"2"));		
			if (!"".equals(getQ_writeDateE()))
				sql+=" and c.writeDate<=" + Common.sqlChar(new Datetime().changeTaiwanYYMMDD(getQ_writeDateE(),"2"));   	    
			if (!"".equals(getQ_proofDoc()))
				sql+=" and c.proofDoc like " + Common.sqlChar(getQ_proofDoc()+"%") ;
			if (!"".equals(getQ_proofNoS())) 
				sql+=" and c.proofNo >= " + Common.sqlChar(getQ_proofNoS());		
			if (!"".equals(getQ_proofNoE())) 
				sql+=" and c.proofNo <= " + Common.sqlChar(getQ_proofNoE());		 
			if (!"".equals(getQ_reduceDateS()))
				sql+=" and c.reduceDate >= " + Common.sqlChar(getQ_reduceDateS()) ;
			if (!"".equals(getQ_reduceDateE()))
				sql+=" and c.reduceDate <= " + Common.sqlChar(getQ_reduceDateE()) ;   
			if (!"".equals(getQ_verify()))
				sql+=" and c.verify = " + Common.sqlChar(getQ_verify()) ;
			if (!"".equals(getQ_propertyKind()))
				sql+=" and c.propertyKind = " + Common.sqlChar(getQ_propertyKind()) ;
			if (!"".equals(getQ_fundType()))
				sql+=" and c.fundType = " + Common.sqlChar(getQ_fundType()) ;

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
				sql+=" and a.signNo like '" + q_signNo + "%'" ;
			
		}
		ResultSet rs = db.querySQL(sql);
		while (rs.next()){
			counter++;
			String rowArray[]=new String[9];
			rowArray[0]=rs.getString("enterOrg"); 
			rowArray[1]=rs.getString("ownership"); 
			rowArray[2]=rs.getString("caseNo"); 
			rowArray[3]=rs.getString("propertyNo"); 
			rowArray[4]=rs.getString("serialNo"); 
			rowArray[5]=rs.getString("serialNo1"); 
			rowArray[6]=rs.getString("signName")+rs.getString("signNo").substring(7,11)+"-"+rs.getString("signNo").substring(11); 
			rowArray[7]=rs.getString("setObligor"); 
			rowArray[8]=rs.getString("bookValue"); 
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


