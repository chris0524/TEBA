/*
*<br>程式目的：權利減少作業-減損單資料
*<br>程式代號：untrt005f
*<br>程式日期：0941004
*<br>程式作者：cherry
*<br>--------------------------------------------------------
*<br>修改作者　　修改日期　　　修改目的
*<br>--------------------------------------------------------
*/

package unt.rt;

import java.sql.ResultSet;
import java.util.ArrayList;
import util.*;

public class UNTRT005F extends UNTRT005Q{


String enterOrg;
String enterOrgName;
String ownership;
String caseNo;
String caseName;
String propertyNo;
String serialNo;
String propertyName;
String propertyName1;
String writeDate;
String writeUnit;
String proofDoc;
String proofNo;
String manageNo;
String summonsNo;
String cause;
String causeName;
String cause1;
String reduceDate;
String bookNotes;
String verify;
String propertyKind;
String fundType;
String buyDate;
String sourceKind;
String meat;
String proofDoc1;
String holdNume1;
String holdDeno2;
String oldBookValue;
String reduceBookValue;
String newBookValue;
String registerCause;
String registerDate;
String setPeriod;
String commonObligee;
String setPerson;
String payDate;
String interest;
String rent;
String notes1;
String notes;
String oldPropertyNo;
String oldSerialNo;
String checkEnterOrg;

public String getEnterOrg(){ return checkGet(enterOrg); }
public void setEnterOrg(String s){ enterOrg=checkSet(s); }
public String getEnterOrgName(){ return checkGet(enterOrgName); }
public void setEnterOrgName(String s){ enterOrgName=checkSet(s); }
public String getOwnership(){ return checkGet(ownership); }
public void setOwnership(String s){ ownership=checkSet(s); }
public String getCaseNo(){ return checkGet(caseNo); }
public void setCaseNo(String s){ caseNo=checkSet(s); }
public String getCaseName(){ return checkGet(caseName); }
public void setCaseName(String s){ caseName=checkSet(s); }
public String getPropertyNo(){ return checkGet(propertyNo); }
public void setPropertyNo(String s){ propertyNo=checkSet(s); }
public String getSerialNo(){ return checkGet(serialNo); }
public void setSerialNo(String s){ serialNo=checkSet(s); }
public String getPropertyName(){ return checkGet(propertyName); }
public void setPropertyName(String s){ propertyName=checkSet(s); }
public String getPropertyName1(){ return checkGet(propertyName1); }
public void setPropertyName1(String s){ propertyName1=checkSet(s); }
public String getWriteDate(){ return checkGet(writeDate); }
public void setWriteDate(String s){ writeDate=checkSet(s); }
public String getWriteUnit(){ return checkGet(writeUnit); }
public void setWriteUnit(String s){ writeUnit=checkSet(s); }
public String getProofDoc(){ return checkGet(proofDoc); }
public void setProofDoc(String s){ proofDoc=checkSet(s); }
public String getProofNo(){ return checkGet(proofNo); }
public void setProofNo(String s){ proofNo=checkSet(s); }
public String getManageNo(){ return checkGet(manageNo); }
public void setManageNo(String s){ manageNo=checkSet(s); }
public String getSummonsNo(){ return checkGet(summonsNo); }
public void setSummonsNo(String s){ summonsNo=checkSet(s); }
public String getCause(){ return checkGet(cause); }
public void setCause(String s){ cause=checkSet(s); }
public String getCauseName(){ return checkGet(causeName); }
public void setCauseName(String s){ causeName=checkSet(s); }
public String getCause1(){ return checkGet(cause1); }
public void setCause1(String s){ cause1=checkSet(s); }
public String getReduceDate(){ return checkGet(reduceDate); }
public void setReduceDate(String s){ reduceDate=checkSet(s); }
public String getBookNotes(){ return checkGet(bookNotes); }
public void setBookNotes(String s){ bookNotes=checkSet(s); }
public String getVerify(){ return checkGet(verify); }
public void setVerify(String s){ verify=checkSet(s); }
public String getPropertyKind(){ return checkGet(propertyKind); }
public void setPropertyKind(String s){ propertyKind=checkSet(s); }
public String getFundType(){ return checkGet(fundType); }
public void setFundType(String s){ fundType=checkSet(s); }
public String getBuyDate(){ return checkGet(buyDate); }
public void setBuyDate(String s){ buyDate=checkSet(s); }
public String getSourceKind(){ return checkGet(sourceKind); }
public void setSourceKind(String s){ sourceKind=checkSet(s); }
public String getMeat(){ return checkGet(meat); }
public void setMeat(String s){ meat=checkSet(s); }
public String getProofDoc1(){ return checkGet(proofDoc1); }
public void setProofDoc1(String s){ proofDoc1=checkSet(s); }
public String getHoldNume1(){ return checkGet(holdNume1); }
public void setHoldNume1(String s){ holdNume1=checkSet(s); }
public String getHoldDeno2(){ return checkGet(holdDeno2); }
public void setHoldDeno2(String s){ holdDeno2=checkSet(s); }
public String getOldBookValue(){ return checkGet(oldBookValue); }
public void setOldBookValue(String s){ oldBookValue=checkSet(s); }
public String getReduceBookValue(){ return checkGet(reduceBookValue); }
public void setReduceBookValue(String s){ reduceBookValue=checkSet(s); }
public String getNewBookValue(){ return checkGet(newBookValue); }
public void setNewBookValue(String s){ newBookValue=checkSet(s); }
public String getRegisterCause(){ return checkGet(registerCause); }
public void setRegisterCause(String s){ registerCause=checkSet(s); }
public String getRegisterDate(){ return checkGet(registerDate); }
public void setRegisterDate(String s){ registerDate=checkSet(s); }
public String getSetPeriod(){ return checkGet(setPeriod); }
public void setSetPeriod(String s){ setPeriod=checkSet(s); }
public String getCommonObligee(){ return checkGet(commonObligee); }
public void setCommonObligee(String s){ commonObligee=checkSet(s); }
public String getSetPerson(){ return checkGet(setPerson); }
public void setSetPerson(String s){ setPerson=checkSet(s); }
public String getPayDate(){ return checkGet(payDate); }
public void setPayDate(String s){ payDate=checkSet(s); }
public String getInterest(){ return checkGet(interest); }
public void setInterest(String s){ interest=checkSet(s); }
public String getRent(){ return checkGet(rent); }
public void setRent(String s){ rent=checkSet(s); }
public String getNotes1(){ return checkGet(notes1); }
public void setNotes1(String s){ notes1=checkSet(s); }
public String getNotes(){ return checkGet(notes); }
public void setNotes(String s){ notes=checkSet(s); }
public String getOldPropertyNo(){ return checkGet(oldPropertyNo); }
public void setOldPropertyNo(String s){ oldPropertyNo=checkSet(s); }
public String getOldSerialNo(){ return checkGet(oldSerialNo); }
public void setOldSerialNo(String s){ oldSerialNo=checkSet(s); }
public String getCheckEnterOrg(){ return checkGet(checkEnterOrg); }
public void setCheckEnterOrg(String s){ checkEnterOrg=checkSet(s); }

String verifyError;

public String getVerifyError(){ return checkGet(verifyError); }
public void setVerifyError(String s){ verifyError=checkSet(s); }

String closing;

public String getClosing(){ return checkGet(closing); }
public void setClosing(String s){ closing=checkSet(s); }

//傳回執行insert前之檢查sql
protected String[][] getInsertCheckSQL(){
	//取得電腦單號
	Database dbC = new Database();
	ResultSet rsC;	
	String sqlC="select substr(max(substr(caseNo,8,3))+1001,2,3) as caseNo from UNTRT_ReduceProof " +
		" where enterOrg = " + Common.sqlChar(enterOrg) +
		" and ownership = " + Common.sqlChar(ownership) +
		" and substr(caseNo,1,7) = " + Common.sqlChar(Datetime.getYYYMMDD()) + 
		"";		
	try {		
		rsC = dbC.querySQL(sqlC);
		if (rsC.next()){
			if (rsC.getString("caseNo")==null)
				setCaseNo(Datetime.getYYYMMDD()+"001");
			else
			    setCaseNo(Datetime.getYYYMMDD()+rsC.getString("caseNo"));
		} else {
			setCaseNo(Datetime.getYYYMMDD()+"001");
		}
	} catch (Exception e) {
		e.printStackTrace();
	} finally {
		dbC.closeAll();
	}
	//取得減損單編號－號
	setProofNo(MaxClosingYM.getMaxProofNo("UNTRT_ReduceProof",enterOrg,ownership));
	//===================
	String[][] checkSQLArray = new String[1][4];
 	checkSQLArray[0][0]=" select count(*) as checkResult from UNTRT_ReduceProof where 1=1 and verify = 'N' " + 
		" and enterOrg = " + Common.sqlChar(enterOrg) + 
		" and ownership = " + Common.sqlChar(ownership) + 
		" and propertyNo = " + Common.sqlChar(propertyNo) + 
		" and serialNo = " + Common.sqlChar(serialNo) + 
		"";
	checkSQLArray[0][1]=">";
	checkSQLArray[0][2]="0";
	checkSQLArray[0][3]="該筆資料己重複，請重新輸入！";
	return checkSQLArray;
}


//傳回insert sql
protected String[] getInsertSQL(){
	String[] execSQLArray = new String[1];
	execSQLArray[0]=" insert into UNTRT_ReduceProof(" +
			" enterOrg,"+
			" ownership,"+
			" caseNo,"+
			" caseName,"+
			" propertyNo,"+
			" serialNo,"+
			" propertyName1,"+
			" writeDate,"+
			" writeUnit,"+
			" proofDoc,"+
			" proofNo,"+
			" manageNo,"+
			" summonsNo,"+
			" cause,"+
			" cause1,"+
			" reduceDate,"+
			" bookNotes,"+
			" verify,"+
			" propertyKind,"+
			" fundType,"+
			" buyDate,"+
			" sourceKind,"+
			" meat,"+
			" proofDoc1,"+
			" holdNume1,"+
			" holdDeno2,"+
			" oldBookValue,"+
			" reduceBookValue,"+
			" newBookValue,"+
			" registerCause,"+
			" registerDate,"+
			" setPeriod,"+
			" commonObligee,"+
			" setPerson,"+
			" payDate,"+
			" interest,"+
			" rent,"+
			" notes1,"+
			" notes,"+
			" oldPropertyNo,"+
			" oldSerialNo,"+
			" closing,"+
			" editID,"+
			" editDate,"+
			" editTime"+
		" )Values(" +
			Common.sqlChar(enterOrg) + "," +
			Common.sqlChar(ownership) + "," +
			Common.sqlChar(caseNo) + "," +
			Common.sqlChar(caseName) + "," +
			Common.sqlChar(propertyNo) + "," +
			Common.sqlChar(serialNo) + "," +
			Common.sqlChar(propertyName1) + "," +
			Common.sqlChar(writeDate) + "," +
			Common.sqlChar(writeUnit) + "," +
			Common.sqlChar(proofDoc) + "," +
			Common.sqlChar(proofNo) + "," +
			Common.sqlChar(manageNo) + "," +
			Common.sqlChar(summonsNo) + "," +
			Common.sqlChar(cause) + "," +
			Common.sqlChar(cause1) + "," +
			Common.sqlChar(reduceDate) + "," +
			Common.sqlChar(bookNotes) + "," +
			Common.sqlChar(verify) + "," +
			Common.sqlChar(propertyKind) + "," +
			Common.sqlChar(fundType) + "," +
			Common.sqlChar(buyDate) + "," +
			Common.sqlChar(sourceKind) + "," +
			Common.sqlChar(meat) + "," +
			Common.sqlChar(proofDoc1) + "," +
			Common.sqlChar(holdNume1) + "," +
			Common.sqlChar(holdDeno2) + "," +
			Common.sqlChar(oldBookValue) + "," +
			Common.sqlChar(reduceBookValue) + "," +
			Common.sqlChar(newBookValue) + "," +
			Common.sqlChar(registerCause) + "," +
			Common.sqlChar(registerDate) + "," +
			Common.sqlChar(setPeriod) + "," +
			Common.sqlChar(commonObligee) + "," +
			Common.sqlChar(setPerson) + "," +
			Common.sqlChar(payDate) + "," +
			Common.sqlChar(interest) + "," +
			Common.sqlChar(rent) + "," +
			Common.sqlChar(notes1) + "," +
			Common.sqlChar(notes) + "," +
			Common.sqlChar(oldPropertyNo) + "," +
			Common.sqlChar(oldSerialNo) + "," +
			Common.sqlChar(closing) + "," +
			Common.sqlChar(getEditID()) + "," +
			Common.sqlChar(getEditDate()) + "," +
			Common.sqlChar(getEditTime()) + ")" ;
	return execSQLArray;
}


//傳回update sql
public void update(){
    Database db = new Database();    
    String strSQL = "";
    //ResultSet rs = null;
	String[] execSQLArray;
	setEditDate(Datetime.getYYYMMDD());
	setEditTime(Datetime.getHHMMSS());	
	try {
		strSQL +=" update UNTRT_ReduceProof set " +
			" caseName = " + Common.sqlChar(caseName) + "," +
			" propertyName1 = " + Common.sqlChar(propertyName1) + "," +
			" writeDate = " + Common.sqlChar(writeDate) + "," +
			" writeUnit = " + Common.sqlChar(writeUnit) + "," +
			" proofDoc = " + Common.sqlChar(proofDoc) + "," +
			" proofNo = " + Common.sqlChar(proofNo) + "," +
			" manageNo = " + Common.sqlChar(manageNo) + "," +
			" summonsNo = " + Common.sqlChar(summonsNo) + "," +
			" cause = " + Common.sqlChar(cause) + "," +
			" cause1 = " + Common.sqlChar(cause1) + "," +
			" reduceDate = " + Common.sqlChar(reduceDate) + "," +
			" bookNotes = " + Common.sqlChar(bookNotes) + "," +
			" verify = " + Common.sqlChar(verify) + "," +
			" propertyKind = " + Common.sqlChar(propertyKind) + "," +
			" fundType = " + Common.sqlChar(fundType) + "," +
			" buyDate = " + Common.sqlChar(buyDate) + "," +
			" sourceKind = " + Common.sqlChar(sourceKind) + "," +
			" meat = " + Common.sqlChar(meat) + "," +
			" proofDoc1 = " + Common.sqlChar(proofDoc1) + "," +
			" holdNume1 = " + Common.sqlChar(holdNume1) + "," +
			" holdDeno2 = " + Common.sqlChar(holdDeno2) + "," +
			" oldBookValue = " + Common.sqlChar(oldBookValue) + "," +
			" reduceBookValue = " + Common.sqlChar(reduceBookValue) + "," +
			" newBookValue = " + Common.sqlChar(newBookValue) + "," +
			" registerCause = " + Common.sqlChar(registerCause) + "," +
			" registerDate = " + Common.sqlChar(registerDate) + "," +
			" setPeriod = " + Common.sqlChar(setPeriod) + "," +
			" commonObligee = " + Common.sqlChar(commonObligee) + "," +
			" setPerson = " + Common.sqlChar(setPerson) + "," +
			" payDate = " + Common.sqlChar(payDate) + "," +
			" interest = " + Common.sqlChar(interest) + "," +
			" rent = " + Common.sqlChar(rent) + "," +
			" notes1 = " + Common.sqlChar(notes1) + "," +
			" notes = " + Common.sqlChar(notes) + "," +
			" oldPropertyNo = " + Common.sqlChar(oldPropertyNo) + "," +
			" oldSerialNo = " + Common.sqlChar(oldSerialNo) + "," +
			" closing = " + Common.sqlChar(closing) + "," +
			" editID = " + Common.sqlChar(getEditID()) + "," +
			" editDate = " + Common.sqlChar(getEditDate()) + "," +
			" editTime = " + Common.sqlChar(getEditTime()) + 
		" where 1=1 " + 
			" and enterOrg = " + Common.sqlChar(enterOrg) +
			" and ownership = " + Common.sqlChar(ownership) +
			" and caseNo = " + Common.sqlChar(caseNo) +
			" and propertyNo = " + Common.sqlChar(propertyNo) +
			" and serialNo = " + Common.sqlChar(serialNo) +
			":;:";
	    strSQL += checkVerify();
		if (!"Y".equals(getVerifyError())) {
			execSQLArray = strSQL.split(":;:");
			db.excuteSQL(execSQLArray);
			setStateUpdateSuccess();
			setErrorMsg("修改完成");				
		} else {			   
	       setStateUpdateSuccess();
	       queryOne();
		}
	} catch (Exception e) {
		e.printStackTrace();
	} finally {
		db.closeAll();
	}	
}


//傳回delete sql
protected String[] getDeleteSQL(){
	String[] execSQLArray = new String[2];
	execSQLArray[0]=" delete UNTRT_ReduceProof where 1=1 " +
			" and enterOrg = " + Common.sqlChar(enterOrg) +
			" and ownership = " + Common.sqlChar(ownership) +
			" and caseNo = " + Common.sqlChar(caseNo) +
			" and propertyNo = " + Common.sqlChar(propertyNo) +
			" and serialNo = " + Common.sqlChar(serialNo) +
			"";
	execSQLArray[1]=" delete UNTRT_ReduceDetail where 1=1 " +
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

public UNTRT005F  queryOne() throws Exception{
	Database db = new Database();
	UNTRT005F obj = this;
	try {
		String sql=" select a.enterOrg, a.ownership, a.caseNo, a.caseName, a.propertyNo, " +"\n"+
					" a.serialNo, a.propertyName1, a.writeDate, a.writeUnit, a.proofDoc, " +"\n"+
					" a.proofNo, a.manageNo, a.summonsNo, a.cause, a.cause1, a.reduceDate, a.bookNotes, " +"\n"+
					" a.verify, a.propertyKind, a.fundType, a.buyDate, a.sourceKind, a.meat, a.proofDoc1, " +"\n"+
					" a.holdNume1, a.holdDeno2, a.oldBookValue, a.reduceBookValue, a.newBookValue, a.registerCause, " +"\n"+
					" a.registerDate, a.setPeriod, a.commonObligee, a.setPerson, a.payDate, a.interest, a.rent, " +"\n"+
					" a.notes1, a.notes, a.oldPropertyNo, a.oldSerialNo, a.editID, a.editDate, a.editTime,  " +"\n"+
					" c.organSName, (select x.propertyName from SYSPK_PropertyCode x where a.propertyNo = x.propertyNo ) as propertyName, " +"\n"+
					" d.codeName, a.closing "+"\n"+
					" from UNTRT_ReduceProof a, SYSCA_Organ c,SYSCA_Code d where 1=1 " +"\n"+
					" and a.enterOrg = c.organID and a.cause = d.codeID and d.codeKindID = 'CAC' "+ "\n"+
					" and a.enterOrg = " + Common.sqlChar(enterOrg) +"\n"+
					" and a.ownership = " + Common.sqlChar(ownership) +"\n"+
					" and a.caseNo = " + Common.sqlChar(caseNo) +"\n"+
					" and a.propertyNo = " + Common.sqlChar(propertyNo) +"\n"+
					" and a.serialNo = " + Common.sqlChar(serialNo) +"\n"+
					"";
//System.out.println("-- untrt005f queryOne --\n"+sql);
		ResultSet rs = db.querySQL(sql);
		if (rs.next()){
			obj.setEnterOrg(rs.getString("enterOrg"));
			obj.setEnterOrgName(rs.getString("organSName"));
			obj.setOwnership(rs.getString("ownership"));
			obj.setCaseNo(rs.getString("caseNo"));
			obj.setCaseName(rs.getString("caseName"));
			obj.setPropertyNo(rs.getString("propertyNo"));
			obj.setSerialNo(rs.getString("serialNo"));
			obj.setPropertyNoName(rs.getString("propertyName"));
			obj.setPropertyName1(rs.getString("propertyName1"));
			obj.setWriteDate(rs.getString("writeDate"));
			obj.setWriteUnit(rs.getString("writeUnit"));
			obj.setProofDoc(rs.getString("proofDoc"));
			obj.setProofNo(rs.getString("proofNo"));
			obj.setManageNo(rs.getString("manageNo"));
			obj.setSummonsNo(rs.getString("summonsNo"));
			obj.setCause(rs.getString("cause"));
			obj.setCause1(rs.getString("cause1"));
			obj.setReduceDate(rs.getString("reduceDate"));
			obj.setBookNotes(rs.getString("bookNotes"));
			obj.setVerify(rs.getString("verify"));
			obj.setPropertyKind(rs.getString("propertyKind"));
			obj.setFundType(rs.getString("fundType"));
			obj.setBuyDate(rs.getString("buyDate"));
			obj.setSourceKind(rs.getString("sourceKind"));
			obj.setMeat(rs.getString("meat"));
			obj.setProofDoc1(rs.getString("proofDoc1"));
			obj.setHoldNume1(rs.getString("holdNume1"));
			obj.setHoldDeno2(rs.getString("holdDeno2"));
			obj.setOldBookValue(rs.getString("oldBookValue"));
			obj.setReduceBookValue(rs.getString("reduceBookValue"));
			obj.setNewBookValue(rs.getString("newBookValue"));
			obj.setRegisterCause(rs.getString("registerCause"));
			obj.setRegisterDate(rs.getString("registerDate"));
			obj.setSetPeriod(rs.getString("setPeriod"));
			obj.setCommonObligee(rs.getString("commonObligee"));
			obj.setSetPerson(rs.getString("setPerson"));
			obj.setPayDate(rs.getString("payDate"));
			obj.setInterest(rs.getString("interest"));
			obj.setRent(rs.getString("rent"));
			obj.setNotes1(rs.getString("notes1"));
			obj.setNotes(rs.getString("notes"));
			obj.setOldPropertyNo(rs.getString("oldPropertyNo"));
			obj.setOldSerialNo(rs.getString("oldSerialNo"));
			obj.setClosing(rs.getString("closing"));
			obj.setEditID(rs.getString("editID"));
			obj.setEditDate(rs.getString("editDate"));
			obj.setEditTime(rs.getString("editTime"));
			obj.setOldVerify(rs.getString("verify"));
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
		String sql=" select distinct a.enterOrg, a.caseNo, a.caseName, a.propertyNo, a.serialNo, " +"\n"+
					" (select x.propertyName from SYSPK_PropertyCode x where a.propertyNo = x.propertyNo ) as propertyName, " +"\n"+
					" a.cause, a.reduceDate, decode(a.verify,'Y','是','否') verify, a.oldBookValue, a.reduceBookValue," +"\n"+
					" a.newBookValue, c.organSName, d.codeName, a.cause, a.cause1," +"\n"+
					" a.ownership ,(select x.codeName from sysca_code x where a.ownership = x.codeid and x.codekindid = 'OWA') as ownershipName," +"\n"+
					" a.propertyKind ,(select x.codeName from sysca_code x where a.propertyKind = x.codeid and x.codekindid = 'PKD') as propertyKindName" +"\n"+
					" from UNTRT_ReduceProof a, SYSCA_Organ c,SYSCA_Code d,UNTRT_ReduceDetail e where 1=1 " +"\n"+
					" and a.enterOrg = c.organID and a.cause = d.codeID and d.codeKindID = 'CAC' " +"\n"+
					" and a.enterOrg=e.enterOrg(+) and a.ownership=e.ownership(+) and a.caseNo=e.caseNo(+) and a.propertyNo=e.propertyNo(+) and a.serialNo=e.serialNo(+)" +"\n"+
					"";
		if ("reduceDetail".equals(getQuerySelect()) || "".equals(getQuerySelect())) {
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
				sql+=" and a.caseName  like " + Common.sqlChar(getQ_caseName()+"%") ;
			if (!"".equals(getQ_propertyNoS()))
				sql+=" and a.propertyNo >= " + Common.sqlChar(getQ_propertyNoS());		
			if (!"".equals(getQ_propertyNoE()))
				sql+=" and a.propertyNo<=" + Common.sqlChar(getQ_propertyNoE());						
			if (!"".equals(getQ_serialNoS()))
				sql+=" and a.serialNo >= " + Common.sqlChar(getQ_serialNoS());		
			if (!"".equals(getQ_serialNoE()))
				sql+=" and a.serialNo <= " + Common.sqlChar(getQ_serialNoE());			
			if (!"".equals(getQ_cause()))
				sql+=" and a.cause = " + Common.sqlChar(getQ_cause()) ;
			if (!"".equals(getQ_writeDateS()))
				sql+=" and a.writeDate >= " + Common.sqlChar(new Datetime().changeTaiwanYYMMDD(getQ_writeDateS(),"2"));		
			if (!"".equals(getQ_writeDateE()))
				sql+=" and a.writeDate<=" + Common.sqlChar(new Datetime().changeTaiwanYYMMDD(getQ_writeDateE(),"2"));   	    
			if (!"".equals(getQ_proofDoc()))
				sql+=" and a.proofDoc like " + Common.sqlChar(getQ_proofDoc()+"%") ;
			if (!"".equals(getQ_proofNoS())) 
				sql+=" and a.proofNo >= " + Common.sqlChar(getQ_proofNoS());		
			if (!"".equals(getQ_proofNoE())) 
				sql+=" and a.proofNo <= " + Common.sqlChar(getQ_proofNoE());		 
			if (!"".equals(getQ_reduceDateS()))
				sql+=" and a.reduceDate >= " + Common.sqlChar(getQ_reduceDateS()) ;
			if (!"".equals(getQ_reduceDateE()))
				sql+=" and a.reduceDate <= " + Common.sqlChar(getQ_reduceDateE()) ;   
			if (!"".equals(getQ_verify()))
				sql+=" and a.verify = " + Common.sqlChar(getQ_verify()) ;
			if (!"".equals(getQ_propertyKind()))
				sql+=" and a.propertyKind = " + Common.sqlChar(getQ_propertyKind()) ;
			if (!"".equals(getQ_fundType()))
				sql+=" and a.fundType = " + Common.sqlChar(getQ_fundType()) ;

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
			
		}

		ResultSet rs = db.querySQL(sql);
		while (rs.next()){
			counter++;
			String rowArray[]=new String[21];
			rowArray[0]=rs.getString("organSName"); 
			rowArray[1]=rs.getString("ownershipName"); 
			rowArray[2]=rs.getString("caseNo"); 
			rowArray[3]=rs.getString("caseName"); 
			rowArray[4]=rs.getString("propertyNo"); 
			rowArray[5]=rs.getString("serialNo"); 
			rowArray[6]=rs.getString("propertyName"); 
			rowArray[7]=rs.getString("codeName"); 
			rowArray[8]=rs.getString("reduceDate"); 
			rowArray[9]=rs.getString("verify"); 
			rowArray[10]=rs.getString("propertyKind"); 
			rowArray[11]=rs.getString("oldBookValue"); 
			rowArray[12]=rs.getString("reduceBookValue"); 
			rowArray[13]=rs.getString("newBookValue"); 
			rowArray[14]=rs.getString("enterOrg"); 
			rowArray[15]=rs.getString("ownership"); 
			rowArray[16]=rs.getString("caseNo"); 
			rowArray[17]=rs.getString("propertyNo"); 
			rowArray[18]=rs.getString("serialNo"); 
			rowArray[19]=rs.getString("cause"); 
			rowArray[20]=rs.getString("cause1"); 
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
//全部入帳
public void approveAll()throws Exception{	
	Database db = new Database();
	try {    
	    int i = 0,counter = 0;
		String rowArray[]=new String[21];
		java.util.Iterator it= queryAll().iterator();
		String[] execSQLArray;
		String strSQL = "";
		while(it.hasNext()){
		    counter++;
			rowArray= (String[])it.next();
			if(rowArray[9].equals("否")){
				i++;
				setVerify("Y");
				setOldVerify("N");
				enterOrg= rowArray[14];
				ownership= rowArray[15];
				caseNo= rowArray[16];
				propertyNo=rowArray[17];
				serialNo= rowArray[18];
				newBookValue= rowArray[13];
				cause= rowArray[19];
				cause1= rowArray[20];
				setReduceDate(rowArray[8]==null || "".equals(rowArray[8]) ? Datetime.getYYYMMDD():rowArray[8]);
				setEditID(getUserID());
				setEditDate(Datetime.getYYYMMDD());
				setEditTime(Datetime.getHHMMSS());	
				if(enterOrg.equals(checkEnterOrg)){
					strSQL += "update UNTRT_ReduceProof set "+
							" verify = 'Y'," +
							" reduceDate = " + Common.sqlChar(getReduceDate()) + "," +
							" editID = " + Common.sqlChar(getEditID()) + "," +
							" editDate = " + Common.sqlChar(getEditDate()) + "," +
							" editTime = " + Common.sqlChar(getEditTime()) + 	                
							" where 1=1 " + 
							" and enterOrg = " + Common.sqlChar(enterOrg) +
							" and ownership = " + Common.sqlChar(ownership) +
							" and caseNo = " + Common.sqlChar(caseNo) +
							" and propertyNo = " + Common.sqlChar(propertyNo) +
							" and serialNo = " + Common.sqlChar(serialNo) +
							":;:";								
					strSQL+= checkVerify() + "";
					if (!super.beforeExecCheck(this.getUpdateCheckSQL())){
				           setVerifyError("Y");
				           //setStateUpdateError();
				           queryOne();
				           break;
					}
					if ("Y".equals(getVerifyError())) {
					    break;
					}
				}
			}
			if (counter==getListLimit()){
				setErrorMsg(getListLimitError());
				break;
			}						
		}
		if (i>0) {
			if (!"Y".equals(getVerifyError())) {
				execSQLArray = strSQL.split(":;:");
				db.excuteSQL(execSQLArray);
				setStateUpdateSuccess();
				setErrorMsg("全部入帳完成");				
			} else {			   
	           setStateUpdateSuccess();
	           queryOne();
			}
		}			
	} catch (Exception e) {
		e.printStackTrace();
	} finally {
		db.closeAll();
	}		
}

//入帳設定
protected String checkVerify(){
    String sql="";
    setEditTime(Datetime.getHHMMSS());	
	//將「入帳」由「N:未核章」改為「Y:已核章」
	if(getVerify().equals("Y")){
		if(!propertyNo.substring(0,3).equals("801") && !propertyNo.substring(0,3).equals("806")){		
			if(Integer.parseInt(View.getLookupField("select count(*) from UNTRT_ReduceDetail where 1=1 and enterOrg = " + Common.sqlChar(enterOrg) +" and ownership = " + Common.sqlChar(ownership) +" and caseNo = " + Common.sqlChar(caseNo) +" and propertyNo = " + Common.sqlChar(propertyNo) +" and serialNo = " + Common.sqlChar(serialNo)))<1){
	            setVerifyError("Y");
	            setErrorMsg("該筆減損單之減損單明細資料標籤要有資料才能做此入帳設定！");
			}
		}
		if(Integer.parseInt(MaxClosingYM.getMaxClosingYM(enterOrg)) < Integer.parseInt(getReduceDate().substring(0,5))){
            sql+= "update UNTRT_AddProof set"+
				" bookValue = " + Common.sqlChar(newBookValue) + ",";
	            if(newBookValue.equals("0")){
	            	sql+=" dataState = '2',";
	            }
			sql+=" editID = " + Common.sqlChar(getEditID()) + "," +
				" editDate = " + Common.sqlChar(getEditDate()) + "," +
				" editTime = " + Common.sqlChar(getEditTime()) + 	                
				" where 1=1 " + 
				" and enterOrg = " + Common.sqlChar(enterOrg) +
				" and ownership = " + Common.sqlChar(ownership) +
				" and propertyNo = " + Common.sqlChar(propertyNo) +
				" and serialNo = " + Common.sqlChar(serialNo) +
				":;:";
            sql+= getUpdateReduceDetail();
       }else{
       		setVerifyError("Y");
       		setStateUpdateError();
       		setErrorMsg("入帳設定有錯，減損年月必須大於月結年月！");
       }
	}
	return sql;
}

protected String getUpdateReduceDetail(){
	Database db = new Database();
	StringBuffer sbSQL = new StringBuffer("");
	try{
		String sql=" select a.enterOrg, a.ownership, a.caseNo, a.propertyNo, a.serialNo, b.serialNo1 " +
					" from untrt_ReduceProof a, untrt_ReduceDetail b " +
					" where a.enterOrg=b.enterOrg and a.ownership=b.ownership and a.caseNo=b.caseNo and a.propertyNo=b.propertyNo and a.serialNo=b.serialNo " +
					" and a.enterOrg = " + Common.sqlChar(enterOrg) +
					" and a.ownership = " + Common.sqlChar(ownership) +
					" and a.caseNo = " + Common.sqlChar(caseNo) +
					" and a.propertyNo = " + Common.sqlChar(propertyNo) +
					" and a.serialNo = " + Common.sqlChar(serialNo) +
					"";
		ResultSet rs = db.querySQL(sql);
		while (rs.next()){
			sbSQL.append("update UNTRT_AddDetail set" ).append(
					" dataState = '2'," ).append(
					" reduceDate = " ).append( Common.sqlChar(getReduceDate()) ).append( "," ).append(
					" reduceCause = " ).append( Common.sqlChar(cause) ).append( "," ).append(
					" reduceCause1 = " ).append( Common.sqlChar(cause1) ).append( "," ).append(
					" bookValue = 0, " ).append(
					" editID = " ).append( Common.sqlChar(getEditID()) ).append( "," ).append(
					" editDate = " ).append( Common.sqlChar(getEditDate()) ).append( "," ).append(
					" editTime = " ).append( Common.sqlChar(getEditTime()) ).append( 	                
					" where 1=1 " ).append( 
					" and enterOrg = " ).append( Common.sqlChar(rs.getString("enterOrg")) ).append(
					" and ownership = " ).append( Common.sqlChar(rs.getString("ownership")) ).append(
					" and propertyNo = " ).append( Common.sqlChar(rs.getString("propertyNo")) ).append(
					" and serialNo = " ).append( Common.sqlChar(rs.getString("serialNo")) ).append(
					" and serialNo1 = " ).append( Common.sqlChar(rs.getString("serialNo1")) ).append(
					":;:" );
		}
	} catch (Exception e) {
		e.printStackTrace();
	} finally {
		db.closeAll();
	}		
	return sbSQL.toString();
}


//回復入帳設定
public void approveRe()throws Exception{	
	Database db = new Database();
	String enterOrgQuery = "";
	String ownershipQuery = "";
	String caseNoQuery = "";
	String propertyNoQuery = "";
	String serialNo1Query = "";
	String serialNoQuery = "";
	String reduceSql = "",reduceCount = "";
	String adjustSql = "",adjustCount = "";
	String adjustOriginal = "",reduceMax = "",reduceMaxSql = "";
	String adjustMax = "",adjustMaxSql = "";
	int count = 0;
	try {    
		String[] execSQLArray;
		String strSQL = "";
		String sql ="select a.caseNo, b.enterOrg, b.ownership, b.propertyNo, b.serialNo, b.serialNo1, " +
					" a.editDate, a.editTime, a.reduceDate, b.bookValue, " +
					" a.oldBookValue as adjustBookValue " +
					" from untrt_reduceProof a, untrt_reduceDetail b " +
					" where 1=1 " +
					" and a.enterOrg = b.enterOrg and a.ownership = b.ownership and a.caseNo = b.caseNo and a.propertyNo = b.propertyNo and a.serialNo = b.serialNo " +
	    			" and a.enterOrg = " + Common.sqlChar(enterOrg) +
	    			" and a.ownership = " + Common.sqlChar(ownership) +
	    			" and a.caseNo = " + Common.sqlChar(caseNo) +
	    			" and a.propertyNo = " + Common.sqlChar(propertyNo) +
	    			" and a.serialNo = " + Common.sqlChar(serialNo) +
					" order by b.enterOrg, b.ownership, b.propertyNo, b.serialNo, b.serialNo1 " +
					"" ;
		ResultSet rs = db.querySQL(sql);
		while (rs.next()){
			count++;
			enterOrgQuery = rs.getString("enterOrg");
			ownershipQuery = rs.getString("ownership");
			caseNoQuery = rs.getString("caseNo");
			propertyNoQuery = rs.getString("propertyNo");
			serialNoQuery = rs.getString("serialNo");
			serialNo1Query = rs.getString("serialNo1");
			//該增減值單之明細資料,存在未入帳的「減損單明細檔UNTRT_ReduceDetail」資料，則提示使用者
			reduceSql = "select count(*) count from untrt_reduceProof a " +
						" where a.enterOrg="+Common.sqlChar(enterOrgQuery)+" and a.ownership="+Common.sqlChar(ownershipQuery)+" and a.propertyNo="+Common.sqlChar(propertyNoQuery)+" and a.serialNo="+Common.sqlChar(serialNoQuery)+" and a.verify='N'" ;
			reduceCount = MaxClosingYM.getCountValues(reduceSql);
			
			//該增減值單之明細資料,存在未入帳的「增減值單明細檔UNTRT_AdjustDetail」資料，則提示使用者
			adjustSql = "select count(*) count from untrt_adjustProof a " +
						" where a.enterOrg="+Common.sqlChar(enterOrgQuery)+" and a.ownership="+Common.sqlChar(ownershipQuery)+" and a.propertyNo="+Common.sqlChar(propertyNoQuery)+" and a.serialNo="+Common.sqlChar(serialNoQuery)+" and a.verify='N'" ;
			adjustCount = MaxClosingYM.getCountValues(adjustSql);
			
			//------------------------------------------------------------------------------------------------
			adjustOriginal = rs.getString("editDate")+rs.getString("editTime")+rs.getString("reduceDate");
			//該增減值單之(異動日期+異動時間+減損日期)≦增減值單明細資料之其他增減值單之最大的(異動日期+異動時間+減損日期)則提示使用者
			adjustMaxSql = "select count(*) count from untrt_adjustProof a " +
							" where a.enterOrg="+Common.sqlChar(enterOrgQuery)+" and a.ownership="+Common.sqlChar(ownershipQuery)+" and a.propertyNo="+Common.sqlChar(propertyNoQuery)+" and a.serialNo="+Common.sqlChar(serialNoQuery)+" and a.verify='Y' " +
							" and '" + adjustOriginal + "'<=(a.editDate || a.editTime || a.adjustDate) " ;
			adjustMax = MaxClosingYM.getCountValues(adjustMaxSql);

			//該增減值單之(異動日期+異動時間+減損日期)≦增減值單明細資料之其他減損單之最大的(異動日期+異動時間+減損日期)則提示使用者
			reduceMaxSql = "select count(*) count from untrt_reduceProof a " +
							" where a.enterOrg="+Common.sqlChar(enterOrgQuery)+" and a.ownership="+Common.sqlChar(ownershipQuery)+" and a.propertyNo="+Common.sqlChar(propertyNoQuery)+" and a.serialNo="+Common.sqlChar(serialNoQuery)+" and a.verify='Y' " +
							" and '" + adjustOriginal + "'<=(a.editDate || a.editTime || a.reduceDate) " +
							" and a.caseNo!="+Common.sqlChar(caseNoQuery) ;
			reduceMax = MaxClosingYM.getCountValues(reduceMaxSql);
			//------------------------------------------------------------------------------------------------
			if(closing.equals("N") && verify.equals("Y") && reduceCount.equals("0") && adjustCount.equals("0") && reduceMax.equals("0") && adjustMax.equals("0")){
				//依據該增減值單「入帳機關enterOrg＋權屬ownership＋電腦單號caseNo」設定
				if(count==1){
					strSQL += "update untrt_ReduceProof set verify ='N' " +
							" where enterOrg=" + Common.sqlChar(enterOrgQuery) +
							" and ownership=" + Common.sqlChar(ownershipQuery) +
							" and caseNo=" + Common.sqlChar(caseNoQuery) +
			    			" and propertyNo = " + Common.sqlChar(propertyNo) +
			    			" and serialNo = " + Common.sqlChar(serialNo) +
							":;:";				
				}
				
				//依據增減值單明細「入帳機關enterOrg＋權屬ownership＋財產編號propertyNo＋財產分號serialNo」設定
				strSQL +="update untrt_AddProof set " +
						" bookValue = " + rs.getString("adjustBookValue") + "," +
						" dataState = '1' " +
						" where enterOrg=" + Common.sqlChar(enterOrgQuery) +
						" and ownership=" + Common.sqlChar(ownershipQuery) +
						" and propertyNo=" + Common.sqlChar(propertyNoQuery) +
						" and serialNo=" + Common.sqlChar(rs.getString("serialNo")) +
						":;:";

				//依據增減值單明細「入帳機關enterOrg＋權屬ownership＋財產編號propertyNo＋財產分號serialNo」設定
				strSQL +="update untrt_AddDetail set " +
						" bookValue = " + rs.getString("bookValue") + "," +
						" dataState ='1', " +
						" reduceDate =null, " +
						" reduceCause =null, " +
						" reduceCause1 =null " +
						" where enterOrg=" + Common.sqlChar(enterOrgQuery) +
						" and ownership=" + Common.sqlChar(ownershipQuery) +
						" and propertyNo=" + Common.sqlChar(propertyNoQuery) +
						" and serialNo=" + Common.sqlChar(rs.getString("serialNo")) +
						" and serialNo1=" + Common.sqlChar(serialNo1Query) +
						":;:";
				//----------------------------------------
			}else{
				setVerifyError("Y");
				if(closing.equals("Y")){
					setErrorMsg("已月結的資料無法回復入帳，請先取消月結，再回此作業回復入帳！");
				}else if(verify.equals("N")){
					setErrorMsg("尚未入帳，請直接修改資料即可！");
				}else if(!reduceCount.equals("0")){
					setErrorMsg("減損作業存在未入帳的資料，無法回復入帳！");
				}else if(!adjustCount.equals("0")){
					setErrorMsg("增減值作業存在未入帳的資料，無法回復入帳！");
				}else if(!adjustMax.equals("0")){
					setErrorMsg("並非最後一筆入帳(增減值)的資料，無法回復入帳！");
				}else if(!reduceMax.equals("0")){
					setErrorMsg("並非最後一筆入帳(減損)的資料，無法回復入帳！");
				}
			}
			if ("Y".equals(getVerifyError())) {
			    break;
			}
		}
		//System.out.println("回復："+strSQL);
		if (!"Y".equals(getVerifyError())) {
			execSQLArray = strSQL.split(":;:");
			db.excuteSQL(execSQLArray);
			setStateUpdateSuccess();
			setErrorMsg("回復入帳完成");	
			queryOne();
		} else {			   
         setStateUpdateSuccess();
         queryOne();
		}
	} catch (Exception e) {
		e.printStackTrace();
	} finally {
		db.closeAll();
	}		
}

}


