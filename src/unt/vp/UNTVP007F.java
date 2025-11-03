/*
*<br>程式目的：有價證券增減值作業－增加股份
*<br>程式代號：untvp007f
*<br>程式日期：0941014
*<br>程式作者：cherry
*<br>--------------------------------------------------------
*<br>修改作者　　修改日期　　　修改目的
*<br>--------------------------------------------------------
*/

package unt.vp;

import java.sql.ResultSet;
import java.util.ArrayList;
import util.*;

public class UNTVP007F extends UNTVP006Q{


String enterOrg;
String enterOrgName;
String ownership;
String caseNo;
String propertyNo;
String propertyNoName;
String serialNo;
String adjustType;
String serialNo1;
String oldSerialNo1;
String oldBookUA;
String oldBookSheet;
String oldBookAmount;
String oldBookUV;
String oldBookValue;
//String adjustBookUA;
//String adjustBookSheet;
String adjustBookAmount;
String adjustBookUV;
String adjustBookValue;
String adjustProofS;
String adjustProofE;
String newBookUA;
String newBookSheet;
String newBookAmount;
String newBookUV;
String newBookValue;
String newProofS;
String newProofE;
String Notes;

String q_enterOrg;
String q_ownership;
String q_caseNo;
String q_propertyNo;
String q_serialNo;

String verify;
String serialNo1Share;
String serialNo1AdjustDetail;
String adjustBookSheet1;
String adjustBookSheet2;
String adjustBookAmount1;
String adjustBookAmount2;
String adjustBookValue1;
String adjustBookValue2;

String adjustBookSheetProof;
String adjustBookAmountProof;
String adjustBookValueProof;
String newBookSheetProof;
String newBookAmountProof;
String newBookValueProof;

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
public String getPropertyNoName(){ return checkGet(propertyNoName); }
public void setPropertyNoName(String s){ propertyNoName=checkSet(s); }
public String getSerialNo(){ return checkGet(serialNo); }
public void setSerialNo(String s){ serialNo=checkSet(s); }
public String getAdjustType(){ return checkGet(adjustType); }
public void setAdjustType(String s){ adjustType=checkSet(s); }
public String getSerialNo1(){ return checkGet(serialNo1); }
public void setSerialNo1(String s){ serialNo1=checkSet(s); }
public String getOldSerialNo1(){ return checkGet(oldSerialNo1); }
public void setOldSerialNo1(String s){ oldSerialNo1=checkSet(s); }
public String getOldBookUA(){ return checkGet(oldBookUA); }
public void setOldBookUA(String s){ oldBookUA=checkSet(s); }
public String getOldBookSheet(){ return checkGet(oldBookSheet); }
public void setOldBookSheet(String s){ oldBookSheet=checkSet(s); }
public String getOldBookAmount(){ return checkGet(oldBookAmount); }
public void setOldBookAmount(String s){ oldBookAmount=checkSet(s); }
public String getOldBookUV(){ return checkGet(oldBookUV); }
public void setOldBookUV(String s){ oldBookUV=checkSet(s); }
public String getOldBookValue(){ return checkGet(oldBookValue); }
public void setOldBookValue(String s){ oldBookValue=checkSet(s); }
//public String getAdjustBookUA(){ return checkGet(adjustBookUA); }
//public void setAdjustBookUA(String s){ adjustBookUA=checkSet(s); }
//public String getAdjustBookSheet(){ return checkGet(adjustBookSheet); }
//public void setAdjustBookSheet(String s){ adjustBookSheet=checkSet(s); }
public String getAdjustBookAmount(){ return checkGet(adjustBookAmount); }
public void setAdjustBookAmount(String s){ adjustBookAmount=checkSet(s); }
public String getAdjustBookUV(){ return checkGet(adjustBookUV); }
public void setAdjustBookUV(String s){ adjustBookUV=checkSet(s); }
public String getAdjustBookValue(){ return checkGet(adjustBookValue); }
public void setAdjustBookValue(String s){ adjustBookValue=checkSet(s); }
public String getAdjustProofS(){ return checkGet(adjustProofS); }
public void setAdjustProofS(String s){ adjustProofS=checkSet(s); }
public String getAdjustProofE(){ return checkGet(adjustProofE); }
public void setAdjustProofE(String s){ adjustProofE=checkSet(s); }
public String getNewBookUA(){ return checkGet(newBookUA); }
public void setNewBookUA(String s){ newBookUA=checkSet(s); }
public String getNewBookSheet(){ return checkGet(newBookSheet); }
public void setNewBookSheet(String s){ newBookSheet=checkSet(s); }
public String getNewBookAmount(){ return checkGet(newBookAmount); }
public void setNewBookAmount(String s){ newBookAmount=checkSet(s); }
public String getNewBookUV(){ return checkGet(newBookUV); }
public void setNewBookUV(String s){ newBookUV=checkSet(s); }
public String getNewBookValue(){ return checkGet(newBookValue); }
public void setNewBookValue(String s){ newBookValue=checkSet(s); }
public String getNewProofS(){ return checkGet(newProofS); }
public void setNewProofS(String s){ newProofS=checkSet(s); }
public String getNewProofE(){ return checkGet(newProofE); }
public void setNewProofE(String s){ newProofE=checkSet(s); }
public String getNotes(){ return checkGet(Notes); }
public void setNotes(String s){ Notes=checkSet(s); }

public String getQ_enterOrg(){ return checkGet(q_enterOrg); }
public void setQ_enterOrg(String s){ q_enterOrg=checkSet(s); }
public String getQ_ownership(){ return checkGet(q_ownership); }
public void setQ_ownership(String s){ q_ownership=checkSet(s); }
public String getQ_caseNo(){ return checkGet(q_caseNo); }
public void setQ_caseNo(String s){ q_caseNo=checkSet(s); }
public String getQ_propertyNo(){ return checkGet(q_propertyNo); }
public void setQ_propertyNo(String s){ q_propertyNo=checkSet(s); }
public String getQ_serialNo(){ return checkGet(q_serialNo); }
public void setQ_serialNo(String s){ q_serialNo=checkSet(s); }

public String getVerify(){ return checkGet(verify); }
public void setVerify(String s){ verify=checkSet(s); }
public String getSerialNo1Share(){ return checkGet(serialNo1Share); }
public void setSerialNo1Share(String s){ serialNo1Share=checkSet(s); }
public String getSerialNo1AdjustDetail(){ return checkGet(serialNo1AdjustDetail); }
public void setSerialNo1AdjustDetail(String s){ serialNo1AdjustDetail=checkSet(s); }
public String getAdjustBookSheet1(){ return checkGet(adjustBookSheet1); }
public void setAdjustBookSheet1(String s){ adjustBookSheet1=checkSet(s); }
public String getAdjustBookAmount1(){ return checkGet(adjustBookAmount1); }
public void setAdjustBookAmount1(String s){ adjustBookAmount1=checkSet(s); }
public String getAdjustBookValue1(){ return checkGet(adjustBookValue1); }
public void setAdjustBookValue1(String s){ adjustBookValue1=checkSet(s); }
public String getAdjustBookSheet2(){ return checkGet(adjustBookSheet2); }
public void setAdjustBookSheet2(String s){ adjustBookSheet2=checkSet(s); }
public String getAdjustBookAmount2(){ return checkGet(adjustBookAmount2); }
public void setAdjustBookAmount2(String s){ adjustBookAmount2=checkSet(s); }
public String getAdjustBookValue2(){ return checkGet(adjustBookValue2); }
public void setAdjustBookValue2(String s){ adjustBookValue2=checkSet(s); }

public String getAdjustBookSheetProof(){ return checkGet(adjustBookSheetProof); }
public void setAdjustBookSheetProof(String s){ adjustBookSheetProof=checkSet(s); }
public String getAdjustBookAmountProof(){ return checkGet(adjustBookAmountProof); }
public void setAdjustBookAmountProof(String s){ adjustBookAmountProof=checkSet(s); }
public String getAdjustBookValueProof(){ return checkGet(adjustBookValueProof); }
public void setAdjustBookValueProof(String s){ adjustBookValueProof=checkSet(s); }
public String getNewBookSheetProof(){ return checkGet(newBookSheetProof); }
public void setNewBookSheetProof(String s){ newBookSheetProof=checkSet(s); }
public String getNewBookAmountProof(){ return checkGet(newBookAmountProof); }
public void setNewBookAmountProof(String s){ newBookAmountProof=checkSet(s); }
public String getNewBookValueProof(){ return checkGet(newBookValueProof); }
public void setNewBookValueProof(String s){ newBookValueProof=checkSet(s); }

//傳回執行insert前之檢查sql
protected String[][] getInsertCheckSQL(){
	//取得股份資料最大的股份次序
	Database db = new Database();
	ResultSet rs;	
	String sql="select nvl(max(serialNo1),0)+1 as serialNo1 from UNTVP_Share " +
		" where enterOrg = " + Common.sqlChar(enterOrg) + 
		" and ownership = " + Common.sqlChar(ownership) + 
		" and propertyNo = " + Common.sqlChar(propertyNo) + 
		" and serialNo = " + Common.sqlChar(serialNo) + 
		"";	
	try {		
		rs = db.querySQL(sql);
		if (rs.next()){
		    setSerialNo1Share(Common.formatFrontZero(rs.getString("serialNo1"),3));
		} else {
			setSerialNo1Share("001");
		}

		//取得增減值單明細檔最大的股份次序
		sql="select nvl(max(serialNo1),0)+1 as serialNo1 from UNTVP_AdjustDetail " +
			" where enterOrg = " + Common.sqlChar(enterOrg) + 
			" and ownership = " + Common.sqlChar(ownership) +
			" and propertyNo = " + Common.sqlChar(propertyNo) + 
			" and serialNo = " + Common.sqlChar(serialNo) + 
			"";		
		rs = db.querySQL(sql);
		if (rs.next()){
		    setSerialNo1AdjustDetail(Common.formatFrontZero(rs.getString("serialNo1"),3));
		} else {
			setSerialNo1AdjustDetail("001");
		}
	} catch (Exception e) {
		e.printStackTrace();
	}
	//	取得股份次序
	if (Integer.parseInt(getSerialNo1AdjustDetail()) > Integer.parseInt(getSerialNo1Share())){
		setSerialNo1(getSerialNo1AdjustDetail());
	} else setSerialNo1(getSerialNo1Share());

	//取得增減別為1的UNTVP_AdjustDetail.adjustBookSheet,adjustBookAmount,adjustBookValue之合計
	sql="select nvl(sum(adjustBookSheet),0) as adjustBookSheet1, nvl(sum(adjustBookAmount),0) as adjustBookAmount1, nvl(sum(adjustBookValue),0) as adjustBookValue1 from UNTVP_AdjustDetail " +
		" where 1=1 and adjustType = '1' " +
		" and enterOrg = " + Common.sqlChar(enterOrg) + 
		" and ownership = " + Common.sqlChar(ownership) + 
		" and caseNo = " + Common.sqlChar(caseNo) + 
		" and propertyNo = " + Common.sqlChar(propertyNo) + 
		" and serialNo = " + Common.sqlChar(serialNo) + 
		"";		
	try {		
		rs = db.querySQL(sql);
		if (rs.next()){
		    //setAdjustBookSheet1(Integer.parseInt(rs.getString("adjustBookSheet1"))+Integer.parseInt(adjustBookSheet)+"");
		    setAdjustBookAmount1(Long.parseLong(rs.getString("adjustBookAmount1"))+Long.parseLong(adjustBookAmount)+"");
		    setAdjustBookValue1(Long.parseLong(rs.getString("adjustBookValue1"))+Long.parseLong(adjustBookValue)+"");
		} else {
		    //setAdjustBookSheet1(adjustBookSheet);
		    setAdjustBookAmount1(adjustBookAmount);
		    setAdjustBookValue1(adjustBookValue);
		}
	} catch (Exception e) {
		e.printStackTrace();
	}
	//取得增減別為2的UNTVP_AdjustDetail.adjustBookSheet,adjustBookAmount,adjustBookValue之合計
	sql="select nvl(sum(adjustBookSheet),0) as adjustBookSheet2, nvl(sum(adjustBookAmount),0) as adjustBookAmount2, nvl(sum(adjustBookValue),0) as adjustBookValue2 from UNTVP_AdjustDetail " +
		" where 1=1 and adjustType = '2' " +
		" and enterOrg = " + Common.sqlChar(enterOrg) + 
		" and ownership = " + Common.sqlChar(ownership) + 
		" and caseNo = " + Common.sqlChar(caseNo) + 
		" and propertyNo = " + Common.sqlChar(propertyNo) + 
		" and serialNo = " + Common.sqlChar(serialNo) + 
		"";		
	try {		
		rs = db.querySQL(sql);
		if (rs.next()){
		    setAdjustBookSheet2(rs.getString("adjustBookSheet2"));
		    setAdjustBookAmount2(rs.getString("adjustBookAmount2"));
		    setAdjustBookValue2(rs.getString("adjustBookValue2"));
		} else {
		    setAdjustBookSheet2("0");
		    setAdjustBookAmount2("0");
		    setAdjustBookValue2("0");
		}
	} catch (Exception e) {
		e.printStackTrace();
	}
	//取得UNTVP_AdjustProof.oldBookSheet,UNTVP_AdjustProof.oldBookAmount,UNTVP_AdjustProof.oldBookValue資料
	//並計算增減總張數,增減總股數,增減總價,新總張數,新總股數,新總價
	sql="select nvl(oldBookSheet,0) as oldBookSheet, nvl(oldBookAmount,0) as oldBookAmount, nvl(oldBookValue,0) as oldBookValue from UNTVP_AdjustProof " +
		" where 1=1 " +
		" and enterOrg = " + Common.sqlChar(enterOrg) + 
		" and ownership = " + Common.sqlChar(ownership) + 
		" and caseNo = " + Common.sqlChar(caseNo) +
		" and propertyNo = " + Common.sqlChar(propertyNo) + 
		" and serialNo = " + Common.sqlChar(serialNo) + 
		"";		
	try {		
		rs = db.querySQL(sql);
		if (rs.next()){
			//setAdjustBookSheetProof(Integer.parseInt(getAdjustBookSheet1())-Integer.parseInt(getAdjustBookSheet2())+"");
			setAdjustBookAmountProof(Long.parseLong(getAdjustBookAmount1())-Long.parseLong(getAdjustBookAmount2())+"");
			setAdjustBookValueProof(Long.parseLong(getAdjustBookValue1())-Long.parseLong(getAdjustBookValue2())+"");
			//setNewBookSheetProof(Integer.parseInt(rs.getString("oldBookSheet"))+Integer.parseInt(getAdjustBookSheetProof())+"");
			setNewBookAmountProof(Long.parseLong(rs.getString("oldBookAmount"))+Long.parseLong(getAdjustBookAmountProof())+"");
			setNewBookValueProof(Long.parseLong(rs.getString("oldBookValue"))+Long.parseLong(getAdjustBookValueProof())+"");
		} else {
		   // setAdjustBookSheetProof("0");
		    setAdjustBookAmountProof("0");
		    setAdjustBookValueProof("0");
			setNewBookSheetProof(rs.getString("oldBookSheet"));
			setNewBookAmountProof(rs.getString("oldBookAmount"));
			setNewBookValueProof(rs.getString("oldBookValue"));
		}
	} catch (Exception e) {
		e.printStackTrace();
	} finally {
		db.closeAll();
	}
	
	String[][] checkSQLArray = new String[1][4];
 	checkSQLArray[0][0]=" select count(*) as checkResult from UNTVP_AdjustDetail where 1=1 " + 
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
	String[] execSQLArray = new String[2];
	execSQLArray[0]=" insert into UNTVP_AdjustDetail(" +
			" enterOrg,"+
			" ownership,"+
			" caseNo,"+
			" propertyNo,"+
			" serialNo,"+
			" adjustType,"+
			" serialNo1,"+
			" oldBookUA,"+
			" oldBookSheet,"+
			" oldBookAmount,"+
			" oldBookUV,"+
			" oldBookValue,"+
			//" adjustBookUA,"+
			//" adjustBookSheet,"+
			" adjustBookAmount,"+
			" adjustBookUV,"+
			" adjustBookValue,"+
			" adjustProofS,"+
			" adjustProofE,"+
			" newBookUA,"+
			" newBookSheet,"+
			" newBookAmount,"+
			" newBookUV,"+
			" newBookValue,"+
			" newProofS,"+
			" newProofE,"+
			" Notes,"+
			" editID,"+
			" editDate,"+
			" editTime"+
		" )Values(" +
			Common.sqlChar(enterOrg) + "," +
			Common.sqlChar(ownership) + "," +
			Common.sqlChar(caseNo) + "," +
			Common.sqlChar(propertyNo) + "," +
			Common.sqlChar(serialNo) + "," +
			Common.sqlChar(adjustType) + "," +
			Common.sqlChar(serialNo1) + "," +
			Common.sqlChar(oldBookUA) + "," +
			Common.sqlChar(oldBookSheet) + "," +
			Common.sqlChar(oldBookAmount) + "," +
			Common.sqlChar(oldBookUV) + "," +
			Common.sqlChar(oldBookValue) + "," +
			//Common.sqlChar(adjustBookUA) + "," +
			//Common.sqlChar(adjustBookSheet) + "," +
			Common.sqlChar(adjustBookAmount) + "," +
			Common.sqlChar(adjustBookUV) + "," +
			Common.sqlChar(adjustBookValue) + "," +
			Common.sqlChar(adjustProofS) + "," +
			Common.sqlChar(adjustProofE) + "," +
			Common.sqlChar(newBookUA) + "," +
			Common.sqlChar(newBookSheet) + "," +
			Common.sqlChar(newBookAmount) + "," +
			Common.sqlChar(newBookUV) + "," +
			Common.sqlChar(newBookValue) + "," +
			Common.sqlChar(newProofS) + "," +
			Common.sqlChar(newProofE) + "," +
			Common.sqlChar(Notes) + "," +
			Common.sqlChar(getEditID()) + "," +
			Common.sqlChar(getEditDate()) + "," +
			Common.sqlChar(getEditTime()) + ")" ;
	
	execSQLArray[1]=" update UNTVP_AdjustProof set " +
					//" adjustBookSheet = " + Common.sqlChar(getAdjustBookSheetProof()) + "," +
					" adjustBookAmount = " + Common.sqlChar(getAdjustBookAmountProof()) + "," +
					" adjustBookValue = " + Common.sqlChar(getAdjustBookValueProof()) + "," +
					//" newBookSheet = " + Common.sqlChar(getNewBookSheetProof()) + "," +
					" newBookAmount = " + Common.sqlChar(getNewBookAmountProof()) + "," +
					" newBookValue = " + Common.sqlChar(getNewBookValueProof()) +
				" where 1=1 " + 
					" and enterOrg = " + Common.sqlChar(enterOrg) +
					" and ownership = " + Common.sqlChar(ownership) +
					" and caseNo = " + Common.sqlChar(caseNo) +
					" and propertyNo = " + Common.sqlChar(propertyNo) +
					" and serialNo = " + Common.sqlChar(serialNo) +
					"";	
	return execSQLArray;
}

//傳回執行update之條件
protected void getUpdateQuerySQL(){
	Database db = new Database();
	ResultSet rs;	
	//取得增減別為1的UNTVP_AdjustDetail.adjustBookSheet,adjustBookAmount,adjustBookValue之合計
	String sql="select nvl(sum(adjustBookSheet),0) as adjustBookSheet1, nvl(sum(adjustBookAmount),0) as adjustBookAmount1, nvl(sum(adjustBookValue),0) as adjustBookValue1 from UNTVP_AdjustDetail " +
		" where 1=1 and adjustType = '1' " +
		" and enterOrg = " + Common.sqlChar(enterOrg) + 
		" and ownership = " + Common.sqlChar(ownership) + 
		" and caseNo = " + Common.sqlChar(caseNo) + 
		" and propertyNo = " + Common.sqlChar(propertyNo) + 
		" and serialNo = " + Common.sqlChar(serialNo) + 
		" and serialNo1 != " + Common.sqlChar(serialNo1) + 
		"";		
	try {		
		rs = db.querySQL(sql);
		if (rs.next()){
		   // setAdjustBookSheet1(Integer.parseInt(rs.getString("adjustBookSheet1"))+Integer.parseInt(adjustBookSheet)+"");
		    setAdjustBookAmount1(Long.parseLong(rs.getString("adjustBookAmount1"))+Long.parseLong(adjustBookAmount)+"");
		    setAdjustBookValue1(Long.parseLong(rs.getString("adjustBookValue1"))+Long.parseLong(adjustBookValue)+"");
		} else {
		    //setAdjustBookSheet1(adjustBookSheet);
		    setAdjustBookAmount1(adjustBookAmount);
		    setAdjustBookValue1(adjustBookValue);
		}
	} catch (Exception e) {
		e.printStackTrace();
	}
	//取得增減別為2的UNTVP_AdjustDetail.adjustBookSheet,adjustBookAmount,adjustBookValue之合計
	sql="select nvl(sum(adjustBookSheet),0) as adjustBookSheet2, nvl(sum(adjustBookAmount),0) as adjustBookAmount2, nvl(sum(adjustBookValue),0) as adjustBookValue2 from UNTVP_AdjustDetail " +
		" where 1=1 and adjustType = '2' " +
		" and enterOrg = " + Common.sqlChar(enterOrg) + 
		" and ownership = " + Common.sqlChar(ownership) + 
		" and caseNo = " + Common.sqlChar(caseNo) + 
		" and propertyNo = " + Common.sqlChar(propertyNo) + 
		" and serialNo = " + Common.sqlChar(serialNo) + 
		"";		
	try {		
		rs = db.querySQL(sql);
		if (rs.next()){
		    //setAdjustBookSheet2(rs.getString("adjustBookSheet2"));
		    setAdjustBookAmount2(rs.getString("adjustBookAmount2"));
		    setAdjustBookValue2(rs.getString("adjustBookValue2"));
		} else {
		    //setAdjustBookSheet2("0");
		    setAdjustBookAmount2("0");
		    setAdjustBookValue2("0");
		}
	} catch (Exception e) {
		e.printStackTrace();
	}
	//取得UNTVP_AdjustProof.oldBookSheet,UNTVP_AdjustProof.oldBookAmount,UNTVP_AdjustProof.oldBookValue資料
	//並計算增減總張數,增減總股數,增減總價,新總張數,新總股數,新總價
	sql="select nvl(oldBookSheet,0) as oldBookSheet, nvl(oldBookAmount,0) as oldBookAmount, nvl(oldBookValue,0) as oldBookValue from UNTVP_AdjustProof " +
		" where 1=1 " +
		" and enterOrg = " + Common.sqlChar(enterOrg) + 
		" and ownership = " + Common.sqlChar(ownership) + 
		" and caseNo = " + Common.sqlChar(caseNo) +
		" and propertyNo = " + Common.sqlChar(propertyNo) + 
		" and serialNo = " + Common.sqlChar(serialNo) + 
		"";		
	try {		
		rs = db.querySQL(sql);
		if (rs.next()){
			setAdjustBookSheetProof(Integer.parseInt(getAdjustBookSheet1())-Integer.parseInt(getAdjustBookSheet2())+"");
			setAdjustBookAmountProof(Long.parseLong(getAdjustBookAmount1())-Long.parseLong(getAdjustBookAmount2())+"");
			setAdjustBookValueProof(Long.parseLong(getAdjustBookValue1())-Long.parseLong(getAdjustBookValue2())+"");
			setNewBookSheetProof(Integer.parseInt(rs.getString("oldBookSheet"))+Integer.parseInt(getAdjustBookSheetProof())+"");
			setNewBookAmountProof(Long.parseLong(rs.getString("oldBookAmount"))+Long.parseLong(getAdjustBookAmountProof())+"");
			setNewBookValueProof(Long.parseLong(rs.getString("oldBookValue"))+Long.parseLong(getAdjustBookValueProof())+"");
		} else {
		    setAdjustBookSheetProof("0");
		    setAdjustBookAmountProof("0");
		    setAdjustBookValueProof("0");
			setNewBookSheetProof(rs.getString("oldBookSheet"));
			setNewBookAmountProof(rs.getString("oldBookAmount"));
			setNewBookValueProof(rs.getString("oldBookValue"));
		}
	} catch (Exception e) {
		e.printStackTrace();
	} finally {
		db.closeAll();
	}
}

//傳回update sql
protected String[] getUpdateSQL(){
	getUpdateQuerySQL();
	String[] execSQLArray = new String[2];
	execSQLArray[0]=" update UNTVP_AdjustDetail set " +
			" adjustType = " + Common.sqlChar(adjustType) + "," +
			" oldBookUA = " + Common.sqlChar(oldBookUA) + "," +
			" oldBookSheet = " + Common.sqlChar(oldBookSheet) + "," +
			" oldBookAmount = " + Common.sqlChar(oldBookAmount) + "," +
			" oldBookUV = " + Common.sqlChar(oldBookUV) + "," +
			" oldBookValue = " + Common.sqlChar(oldBookValue) + "," +
			//" adjustBookUA = " + Common.sqlChar(adjustBookUA) + "," +
			//" adjustBookSheet = " + Common.sqlChar(adjustBookSheet) + "," +
			" adjustBookAmount = " + Common.sqlChar(adjustBookAmount) + "," +
			" adjustBookUV = " + Common.sqlChar(adjustBookUV) + "," +
			" adjustBookValue = " + Common.sqlChar(adjustBookValue) + "," +
			" adjustProofS = " + Common.sqlChar(adjustProofS) + "," +
			" adjustProofE = " + Common.sqlChar(adjustProofE) + "," +
			" newBookUA = " + Common.sqlChar(newBookUA) + "," +
			" newBookSheet = " + Common.sqlChar(newBookSheet) + "," +
			" newBookAmount = " + Common.sqlChar(newBookAmount) + "," +
			" newBookUV = " + Common.sqlChar(newBookUV) + "," +
			" newBookValue = " + Common.sqlChar(newBookValue) + "," +
			" newProofS = " + Common.sqlChar(newProofS) + "," +
			" newProofE = " + Common.sqlChar(newProofE) + "," +
			" Notes = " + Common.sqlChar(Notes) + "," +
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
	execSQLArray[1]=" update UNTVP_AdjustProof set " +
					//" adjustBookSheet = " + Common.sqlChar(getAdjustBookSheetProof()) + "," +
					" adjustBookAmount = " + Common.sqlChar(getAdjustBookAmountProof()) + "," +
					" adjustBookValue = " + Common.sqlChar(getAdjustBookValueProof()) + "," +
					" newBookSheet = " + Common.sqlChar(getNewBookSheetProof()) + "," +
					" newBookAmount = " + Common.sqlChar(getNewBookAmountProof()) + "," +
					" newBookValue = " + Common.sqlChar(getNewBookValueProof()) +
				" where 1=1 " + 
					" and enterOrg = " + Common.sqlChar(enterOrg) +
					" and ownership = " + Common.sqlChar(ownership) +
					" and caseNo = " + Common.sqlChar(caseNo) +
					" and propertyNo = " + Common.sqlChar(propertyNo) +
					" and serialNo = " + Common.sqlChar(serialNo) +
					"";	
	return execSQLArray;
}

//傳回執行delete之條件
protected void getDeleteQuerySQL(){
	Database db = new Database();
	ResultSet rs;	
	//取得增減別為1的UNTVP_AdjustDetail.adjustBookSheet,adjustBookAmount,adjustBookValue之合計
	String sql="select nvl(sum(adjustBookSheet),0) as adjustBookSheet1, nvl(sum(adjustBookAmount),0) as adjustBookAmount1, nvl(sum(adjustBookValue),0) as adjustBookValue1 from UNTVP_AdjustDetail " +
		" where 1=1 and adjustType = '1' " +
		" and enterOrg = " + Common.sqlChar(enterOrg) + 
		" and ownership = " + Common.sqlChar(ownership) + 
		" and caseNo = " + Common.sqlChar(caseNo) + 
		" and propertyNo = " + Common.sqlChar(propertyNo) + 
		" and serialNo = " + Common.sqlChar(serialNo) + 
		" and serialNo1 != " + Common.sqlChar(serialNo1) + 
		"";	
	try {		
		rs = db.querySQL(sql);
		if (rs.next()){
		    //setAdjustBookSheet1(rs.getString("adjustBookSheet1"));
		    setAdjustBookAmount1(rs.getString("adjustBookAmount1"));
		    setAdjustBookValue1(rs.getString("adjustBookValue1"));
		} else {
		    //setAdjustBookSheet1("0");
		    setAdjustBookAmount1("0");
		    setAdjustBookValue1("0");
		}
	} catch (Exception e) {
		e.printStackTrace();
	}
	//取得增減別為2的UNTVP_AdjustDetail.adjustBookSheet,adjustBookAmount,adjustBookValue之合計
	sql="select nvl(sum(adjustBookSheet),0) as adjustBookSheet2, nvl(sum(adjustBookAmount),0) as adjustBookAmount2, nvl(sum(adjustBookValue),0) as adjustBookValue2 from UNTVP_AdjustDetail " +
		" where 1=1 and adjustType = '2' " +
		" and enterOrg = " + Common.sqlChar(enterOrg) + 
		" and ownership = " + Common.sqlChar(ownership) + 
		" and caseNo = " + Common.sqlChar(caseNo) + 
		" and propertyNo = " + Common.sqlChar(propertyNo) + 
		" and serialNo = " + Common.sqlChar(serialNo) + 
		"";		
	try {		
		rs = db.querySQL(sql);
		if (rs.next()){
		    setAdjustBookSheet2(rs.getString("adjustBookSheet2"));
		    setAdjustBookAmount2(rs.getString("adjustBookAmount2"));
		    setAdjustBookValue2(rs.getString("adjustBookValue2"));
		} else {
		    setAdjustBookSheet2("0");
		    setAdjustBookAmount2("0");
		    setAdjustBookValue2("0");
		}
	} catch (Exception e) {
		e.printStackTrace();
	}
	//取得UNTVP_AdjustProof.oldBookSheet,UNTVP_AdjustProof.oldBookAmount,UNTVP_AdjustProof.oldBookValue資料
	//並計算增減總張數,增減總股數,增減總價,新總張數,新總股數,新總價
	sql="select nvl(oldBookSheet,0) as oldBookSheet, nvl(oldBookAmount,0) as oldBookAmount, nvl(oldBookValue,0) as oldBookValue from UNTVP_AdjustProof " +
		" where 1=1 " +
		" and enterOrg = " + Common.sqlChar(enterOrg) + 
		" and ownership = " + Common.sqlChar(ownership) + 
		" and caseNo = " + Common.sqlChar(caseNo) +
		" and propertyNo = " + Common.sqlChar(propertyNo) + 
		" and serialNo = " + Common.sqlChar(serialNo) + 
		"";	
	//System.out.println(sql);
	try {		
		rs = db.querySQL(sql);
		if (rs.next()){
			//setAdjustBookSheetProof(Integer.parseInt(getAdjustBookSheet1())-Integer.parseInt(getAdjustBookSheet2())+"");
			setAdjustBookAmountProof(Long.parseLong(getAdjustBookAmount1())-Long.parseLong(getAdjustBookAmount2())+"");
			setAdjustBookValueProof(Long.parseLong(getAdjustBookValue1())-Long.parseLong(getAdjustBookValue2())+"");
			setNewBookSheetProof(rs.getString("oldBookSheet"));
			setNewBookAmountProof(Long.parseLong(rs.getString("oldBookAmount"))+Long.parseLong(getAdjustBookAmountProof())+"");
			setNewBookValueProof(Long.parseLong(rs.getString("oldBookValue"))+Long.parseLong(getAdjustBookValueProof())+"");
		} else {
		    //setAdjustBookSheetProof("0");
		    setAdjustBookAmountProof("0");
		    setAdjustBookValueProof("0");
			setNewBookSheetProof(rs.getString("oldBookSheet"));
			setNewBookAmountProof(rs.getString("oldBookAmount"));
			setNewBookValueProof(rs.getString("oldBookValue"));
		}
	} catch (Exception e) {
		e.printStackTrace();
	} finally {
		db.closeAll();
	}
}

//傳回delete sql
protected String[] getDeleteSQL(){
	getDeleteQuerySQL();
	String[] execSQLArray = new String[2];
	execSQLArray[0]=" delete UNTVP_AdjustDetail where 1=1 " +
			" and enterOrg = " + Common.sqlChar(enterOrg) +
			" and ownership = " + Common.sqlChar(ownership) +
			" and caseNo = " + Common.sqlChar(caseNo) +
			" and propertyNo = " + Common.sqlChar(propertyNo) +
			" and serialNo = " + Common.sqlChar(serialNo) +
			" and serialNo1 = " + Common.sqlChar(serialNo1) +
			"";
	execSQLArray[1]=" update UNTVP_AdjustProof set " +
					//" adjustBookSheet = " + Common.sqlChar(getAdjustBookSheetProof()) + "," +
					" adjustBookAmount = " + Common.sqlChar(getAdjustBookAmountProof()) + "," +
					" adjustBookValue = " + Common.sqlChar(getAdjustBookValueProof()) + "," +
					" newBookSheet = " + Common.sqlChar(getNewBookSheetProof()) + "," +
					" newBookAmount = " + Common.sqlChar(getNewBookAmountProof()) + "," +
					" newBookValue = " + Common.sqlChar(getNewBookValueProof()) +
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

public UNTVP007F  queryOne() throws Exception{
	Database db = new Database();
	UNTVP007F obj = this;
	try {
		String sql=" select a.enterOrg, a.ownership, a.caseNo, a.propertyNo, a.serialNo, a.adjustType, a.serialNo1, a.oldBookUA, a.oldBookSheet, a.oldBookAmount, a.oldBookUV, a.oldBookValue, a.adjustBookUA, a.adjustBookSheet, a.adjustBookAmount, a.adjustBookUV, a.adjustBookValue, a.adjustProofS, a.adjustProofE, a.newBookUA, a.newBookSheet, a.newBookAmount, a.newBookUV, a.newBookValue, a.newProofS, a.newProofE, a.Notes, a.editID, a.editDate, a.editTime, " +
					" oldSerialNo1 "+
					" from UNTVP_AdjustDetail a where 1=1 " +
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
			obj.setOwnership(rs.getString("ownership"));
			obj.setCaseNo(rs.getString("caseNo"));
			obj.setPropertyNo(rs.getString("propertyNo"));
			obj.setSerialNo(rs.getString("serialNo"));
			obj.setAdjustType(rs.getString("adjustType"));
			obj.setSerialNo1(rs.getString("serialNo1"));
			obj.setOldSerialNo1(rs.getString("oldSerialNo1"));
			obj.setOldBookUA(rs.getString("oldBookUA"));
			obj.setOldBookSheet(rs.getString("oldBookSheet"));
			obj.setOldBookAmount(rs.getString("oldBookAmount"));
			obj.setOldBookUV(rs.getString("oldBookUV"));
			obj.setOldBookValue(rs.getString("oldBookValue"));
			//obj.setAdjustBookUA(rs.getString("adjustBookUA"));
			//obj.setAdjustBookSheet(rs.getString("adjustBookSheet"));
			obj.setAdjustBookAmount(rs.getString("adjustBookAmount"));
			obj.setAdjustBookUV(rs.getString("adjustBookUV"));
			obj.setAdjustBookValue(rs.getString("adjustBookValue"));
			obj.setAdjustProofS(rs.getString("adjustProofS"));
			obj.setAdjustProofE(rs.getString("adjustProofE"));
			obj.setNewBookUA(rs.getString("newBookUA"));
			obj.setNewBookSheet(rs.getString("newBookSheet"));
			obj.setNewBookAmount(rs.getString("newBookAmount"));
			obj.setNewBookUV(rs.getString("newBookUV"));
			obj.setNewBookValue(rs.getString("newBookValue"));
			obj.setNewProofS(rs.getString("newProofS"));
			obj.setNewProofE(rs.getString("newProofE"));
			obj.setNotes(rs.getString("Notes"));
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
	UNTVP007F obj = this;
	Database db = new Database();
	ArrayList objList=new ArrayList();
	int counter=0;
	try {
		String sql=" select a.enterOrg, a.ownership, a.caseNo, a.propertyNo, a.serialNo, a.serialNo1, a.adjustBookUA, a.adjustBookSheet, a.adjustBookAmount, a.adjustBookUV, a.adjustBookValue, b.verify "+
			" from UNTVP_AdjustDetail a, UNTVP_AdjustProof b where 1=1 and a.adjustType = '1' " +
			" and a.enterOrg=b.enterOrg and a.ownership=b.ownership and a.caseNo=b.caseNo and a.propertyNo=b.propertyNo and a.serialNo=b.serialNo "; 
			if (!"".equals(getEnterOrg()) && !"".equals(getOwnership()) && !"".equals(getCaseNo()) && !"".equals(getPropertyNo()) && !"".equals(getSerialNo())) {				
				sql+=" and a.enterOrg = " + Common.sqlChar(getEnterOrg()) ;
				sql+=" and a.ownership = " + Common.sqlChar(getOwnership()) ;
				sql+=" and a.caseNo = " + Common.sqlChar(getCaseNo()) ;
				sql+=" and a.propertyNo = " + Common.sqlChar(getPropertyNo()) ;
				sql+=" and a.serialNo = " + Common.sqlChar(getSerialNo()) ;
			} else {
				setErrorMsg("對不起! 請重新回到增減值單執行查詢,再返回增加股份頁籤");				
			}
		ResultSet rs = db.querySQL(sql);
		//System.out.println(sql);
		while (rs.next()){
			counter++;
			String rowArray[]=new String[9];
			rowArray[0]=rs.getString("enterOrg"); 
			rowArray[1]=rs.getString("ownership"); 
			rowArray[2]=rs.getString("caseNo"); 
			rowArray[3]=rs.getString("propertyNo"); 
			rowArray[4]=rs.getString("serialNo"); 
			rowArray[5]=rs.getString("serialNo1"); 
			//rowArray[6]=rs.getString("adjustBookUA"); 
			//rowArray[7]=rs.getString("adjustBookSheet"); 
			rowArray[6]=rs.getString("adjustBookAmount"); 
			rowArray[7]=rs.getString("adjustBookUV"); 
			rowArray[8]=rs.getString("adjustBookValue"); 
			obj.setVerify(verify);
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


