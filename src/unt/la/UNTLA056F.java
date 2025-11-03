/*
*<br>程式目的：土地合併分割重測重劃作業－減損單明細
*<br>程式代號：UNTLA056F
*<br>程式日期：
*<br>程式作者：Yuan-Ren Jheng
*<br>--------------------------------------------------------
*<br>修改作者　　修改日期　　　修改目的
*<br>--------------------------------------------------------
*/

package unt.la;

import java.sql.ResultSet;
import java.util.ArrayList;

import util.Common;
import unt.ch.DataStructor;
import unt.ch.UNTCH_Tools;
import util.Database;

public class UNTLA056F extends UNTLA054Q{

	String enterOrg;
	String ownership;
	String caseNo;
	String differenceKind;
	String propertyNo;
	String serialNo;
	String property_ReduceName;
	String propertyName1;
	String signNo;
	String signNo1;
	String signNo2;
	String signNo3;
	String signNo4;
	String signNo5;
	String cause;
	String cause1;
	String reduceDate;
	String newEnterOrg;
	String transferDate;
	String verify;
	String propertyKind;
	String fundType;
	String valuable;
	String taxCredit;
	String area;
	String useState1;
	String holdNume;
	String holdDeno;
	String holdArea;
	String bookNotes;
	String accountingTitle;
	String bookUnit;
	String bookValue;
	String netUnit;
	String netValue;
	String useSeparate;
	String useKind;
	String proofDoc;
	String field;
	String sourceDate;
	String bulletinDate;
	String valueUnit;
	String notes;
	String oldPropertyNo;
	String oldSerialNo;
	String mergeDivision;
	String mergeDivisionBatch;
	String editID;
	String editDate;
	String editTime;
	String enterOrgName;
	String newEnterOrgName;
	
	String caseSerialNo;
	String engineeringNo;
	String engineeringNoName;
	String buyDate;
	String keepUnit;
	String keeper;
	String useUnit;
	String userNo;
	String userNote;
	String place1;
	String place1Name;
	String place;
	public String getEnterOrg() {return checkGet(enterOrg);}
	public void setEnterOrg(String enterOrg) {this.enterOrg = checkSet(enterOrg);}
	public String getOwnership() {return checkGet(ownership);}
	public void setOwnership(String ownership) {this.ownership = checkSet(ownership);}
	public String getCaseNo() {return checkGet(caseNo);}
	public void setCaseNo(String caseNo) {this.caseNo = checkSet(caseNo);}
	public String getDifferenceKind() {return checkGet(differenceKind);}
	public void setDifferenceKind(String differenceKind) {this.differenceKind = checkSet(differenceKind);}
	public String getPropertyNo() {return checkGet(propertyNo);}
	public void setPropertyNo(String propertyNo) {this.propertyNo = checkSet(propertyNo);}
	public String getSerialNo() {return checkGet(serialNo);}
	public void setSerialNo(String serialNo) {this.serialNo = checkSet(serialNo);}
	public String getProperty_ReduceName() {return checkGet(property_ReduceName);}
	public void setProperty_ReduceName(String property_ReduceName) {this.property_ReduceName = checkSet(property_ReduceName);}
	public String getPropertyName1() {return checkGet(propertyName1);}
	public void setPropertyName1(String propertyName1) {this.propertyName1 = checkSet(propertyName1);}
	public String getSignNo() {return checkGet(signNo);}
	public void setSignNo(String signNo) {this.signNo = checkSet(signNo);}	
	public String getSignNo1() {return checkGet(signNo1);}
	public void setSignNo1(String signNo1) {this.signNo1 = checkSet(signNo1);}
	public String getSignNo2() {return checkGet(signNo2);}
	public void setSignNo2(String signNo2) {this.signNo2 = checkSet(signNo2);}
	public String getSignNo3() {return checkGet(signNo3);}
	public void setSignNo3(String signNo3) {this.signNo3 = checkSet(signNo3);}
	public String getSignNo4() {return checkGet(signNo4);}
	public void setSignNo4(String signNo4) {this.signNo4 = checkSet(signNo4);}
	public String getSignNo5() {return checkGet(signNo5);}
	public void setSignNo5(String signNo5) {this.signNo5 = checkSet(signNo5);}
	public String getCause() {return checkGet(cause);}
	public void setCause(String cause) {this.cause = checkSet(cause);}
	public String getCause1() {return checkGet(cause1);}
	public void setCause1(String cause1) {this.cause1 = checkSet(cause1);}
	public String getReduceDate() {return checkGet(reduceDate);}
	public void setReduceDate(String reduceDate) {this.reduceDate = checkSet(reduceDate);}
	public String getNewEnterOrg() {return checkGet(newEnterOrg);}
	public void setNewEnterOrg(String newEnterOrg) {this.newEnterOrg = checkSet(newEnterOrg);}
	public String getTransferDate() {return checkGet(transferDate);}
	public void setTransferDate(String transferDate) {this.transferDate = checkSet(transferDate);}
	public String getVerify() {return checkGet(verify);}
	public void setVerify(String verify) {this.verify = checkSet(verify);}
	public String getPropertyKind() {return checkGet(propertyKind);}
	public void setPropertyKind(String propertyKind) {this.propertyKind = checkSet(propertyKind);}
	public String getFundType() {return checkGet(fundType);}
	public void setFundType(String fundType) {this.fundType = checkSet(fundType);}
	public String getValuable() {return checkGet(valuable);}
	public void setValuable(String valuable) {this.valuable = checkSet(valuable);}
	public String getTaxCredit() {return checkGet(taxCredit);}
	public void setTaxCredit(String taxCredit) {this.taxCredit = checkSet(taxCredit);}
	public String getArea() {return checkGet(area);}
	public void setArea(String area) {this.area = checkSet(area);}
	public String getUseState1() {return checkGet(useState1);}
	public void setUseState1(String useState1) {this.useState1 = checkSet(useState1);}
	public String getHoldNume() {return checkGet(holdNume);}
	public void setHoldNume(String holdNume) {this.holdNume = checkSet(holdNume);}
	public String getHoldDeno() {return checkGet(holdDeno);}
	public void setHoldDeno(String holdDeno) {this.holdDeno = checkSet(holdDeno);}
	public String getHoldArea() {return checkGet(holdArea);}
	public void setHoldArea(String holdArea) {this.holdArea = checkSet(holdArea);}
	public String getBookNotes() {return checkGet(bookNotes);}
	public void setBookNotes(String bookNotes) {this.bookNotes = checkSet(bookNotes);}
	public String getAccountingTitle() {return checkGet(accountingTitle);}
	public void setAccountingTitle(String accountingTitle) {this.accountingTitle = checkSet(accountingTitle);}
	public String getBookUnit() {return checkGet(bookUnit);}
	public void setBookUnit(String bookUnit) {this.bookUnit = checkSet(bookUnit);}
	public String getBookValue() {return checkGet(bookValue);}
	public void setBookValue(String bookValue) {this.bookValue = checkSet(bookValue);}
	public String getNetUnit() {return checkGet(netUnit);}
	public void setNetUnit(String netUnit) {this.netUnit = checkSet(netUnit);}
	public String getNetValue() {return checkGet(netValue);}
	public void setNetValue(String netValue) {this.netValue = checkSet(netValue);}
	public String getUseSeparate() {return checkGet(useSeparate);}
	public void setUseSeparate(String useSeparate) {this.useSeparate = checkSet(useSeparate);}
	public String getUseKind() {return checkGet(useKind);}
	public void setUseKind(String useKind) {this.useKind = checkSet(useKind);}
	public String getProofDoc() {return checkGet(proofDoc);}
	public void setProofDoc(String proofDoc) {this.proofDoc = checkSet(proofDoc);}
	public String getField() {return checkGet(field);}
	public void setField(String field) {this.field = checkSet(field);}
	public String getSourceDate() {return checkGet(sourceDate);}
	public void setSourceDate(String sourceDate) {this.sourceDate = checkSet(sourceDate);}
	public String getBulletinDate() {return checkGet(bulletinDate);}
	public void setBulletinDate(String bulletinDate) {this.bulletinDate = checkSet(bulletinDate);}
	public String getValueUnit() {return checkGet(valueUnit);}
	public void setValueUnit(String valueUnit) {this.valueUnit = checkSet(valueUnit);}
	public String getNotes() {return checkGet(notes);}
	public void setNotes(String notes) {this.notes = checkSet(notes);}
	public String getOldPropertyNo() {return checkGet(oldPropertyNo);}
	public void setOldPropertyNo(String oldPropertyNo) {this.oldPropertyNo = checkSet(oldPropertyNo);}
	public String getOldSerialNo() {return checkGet(oldSerialNo);}
	public void setOldSerialNo(String oldSerialNo) {this.oldSerialNo = checkSet(oldSerialNo);}
	public String getMergeDivision() {return checkGet(mergeDivision);}
	public void setMergeDivision(String mergeDivision) {this.mergeDivision = checkSet(mergeDivision);}
	public String getMergeDivisionBatch() {return checkGet(mergeDivisionBatch);}
	public void setMergeDivisionBatch(String mergeDivisionBatch) {this.mergeDivisionBatch = checkSet(mergeDivisionBatch);}
	public String getEditID() {return checkGet(editID);}
	public void setEditID(String editID) {this.editID = checkSet(editID);}
	public String getEditDate() {return checkGet(editDate);}
	public void setEditDate(String editDate) {this.editDate = checkSet(editDate);}
	public String getEditTime() {return checkGet(editTime);}
	public void setEditTime(String editTime) {this.editTime = checkSet(editTime);}
	public String getEnterOrgName() {return checkGet(enterOrgName);}
	public void setEnterOrgName(String enterOrgName) {this.enterOrgName = checkSet(enterOrgName);}
	public String getNewEnterOrgName() {return checkGet(newEnterOrgName);}
	public void setNewEnterOrgName(String newEnterOrgName) {this.newEnterOrgName = checkSet(newEnterOrgName);}
	public String getCaseSerialNo() {return checkGet(caseSerialNo);}
	public void setCaseSerialNo(String caseSerialNo) {this.caseSerialNo = checkSet(caseSerialNo);}
	public String getEngineeringNo() {return checkGet(engineeringNo);}
	public void setEngineeringNo(String engineeringNo) {this.engineeringNo = checkSet(engineeringNo);}
	public String getEngineeringNoName() {return checkGet(engineeringNoName);}
	public void setEngineeringNoName(String engineeringNoName) {this.engineeringNoName = checkSet(engineeringNoName);}
	public String getBuyDate() {return checkGet(buyDate);}
	public void setBuyDate(String buyDate) {this.buyDate = checkSet(buyDate);}
	public String getKeepUnit() {return checkGet(keepUnit);}
	public void setKeepUnit(String keepUnit) {this.keepUnit = checkSet(keepUnit);}
	public String getKeeper() {return checkGet(keeper);}
	public void setKeeper(String keeper) {this.keeper = checkSet(keeper);}
	public String getUseUnit() {return checkGet(useUnit);}
	public void setUseUnit(String useUnit) {this.useUnit = checkSet(useUnit);}
	public String getUserNo() {return checkGet(userNo);}
	public void setUserNo(String userNo) {this.userNo = checkSet(userNo);}
	public String getUserNote() {return checkGet(userNote);}
	public void setUserNote(String userNote) {this.userNote = checkSet(userNote);}
	public String getPlace1() {return checkGet(place1);}
	public void setPlace1(String place1) {this.place1 = checkSet(place1);}
	public String getPlace1Name() {return checkGet(place1Name);}
	public void setPlace1Name(String place1Name) {this.place1Name = checkSet(place1Name);}
	public String getPlace() {return checkGet(place);}
	public void setPlace(String place) {this.place = checkSet(place);}
	
	String reduceCause;
	String reduceCause1;	
	public String getReduceCause() {return checkGet(reduceCause);}
	public void setReduceCause(String reduceCause) {this.reduceCause = checkSet(reduceCause);}
	public String getReduceCause1() {return checkGet(reduceCause1);}
	public void setReduceCause1(String reduceCause1) {this.reduceCause1 = checkSet(reduceCause1);}
	
	
	public void setSQLBeanValue(){
		UNTCH_Tools ut = new UNTCH_Tools();
		
		sqlbean.setTableName("UNTLA_ReduceDetail");
		
		sqlbean.primarykeyMap.clear();
		sqlbean.columnMap.clear();
		
		sqlbean.primarykeyMap.put("enterOrg", 		this.getEnterOrg());
		sqlbean.primarykeyMap.put("ownership", 		this.getOwnership());
		sqlbean.primarykeyMap.put("caseNo",			this.getCaseNo());
		sqlbean.primarykeyMap.put("differencekind",	this.getDifferenceKind());
		sqlbean.primarykeyMap.put("propertyNo",		this.getPropertyNo());
		sqlbean.primarykeyMap.put("serialNo",		this.getSerialNo());
		
		sqlbean.columnMap.put("enterOrg",				this.getEnterOrg());
		sqlbean.columnMap.put("ownership",				this.getOwnership());
		sqlbean.columnMap.put("caseNo",					this.getCaseNo());
		sqlbean.columnMap.put("differencekind",			this.getDifferenceKind());
		sqlbean.columnMap.put("propertyNo",				this.getPropertyNo());
		sqlbean.columnMap.put("serialNo",				this.getSerialNo());
		sqlbean.columnMap.put("propertyName1",			this.getPropertyName1());
		sqlbean.columnMap.put("signNo",					this.getSignNo());
		sqlbean.columnMap.put("cause",					this.getReduceCause());
		sqlbean.columnMap.put("cause1",					this.getReduceCause1());
		sqlbean.columnMap.put("reduceDate",				ut._transToCE_Year(this.getReduceDate()));
		sqlbean.columnMap.put("newEnterOrg",			this.getNewEnterOrg());
		sqlbean.columnMap.put("transferDate",			ut._transToCE_Year(this.getTransferDate()));
		sqlbean.columnMap.put("verify",					this.getVerify());
		sqlbean.columnMap.put("propertyKind",			this.getPropertyKind());
		sqlbean.columnMap.put("fundType",				this.getFundType());
		sqlbean.columnMap.put("valuable",				this.getValuable());
		sqlbean.columnMap.put("taxCredit",				this.getTaxCredit());
		sqlbean.columnMap.put("area",					this.getArea());
		sqlbean.columnMap.put("useState1",				this.getUseState1());
		sqlbean.columnMap.put("holdNume",				this.getHoldNume());
		sqlbean.columnMap.put("holdDeno",				this.getHoldDeno());
		sqlbean.columnMap.put("holdArea",				this.getHoldArea());
		sqlbean.columnMap.put("bookNotes",				this.getBookNotes());
		sqlbean.columnMap.put("accountingTitle",		this.getAccountingTitle());
		sqlbean.columnMap.put("bookUnit",				this.getBookUnit());
		sqlbean.columnMap.put("bookValue",				this.getBookValue());
		sqlbean.columnMap.put("netUnit",				this.getNetUnit());
		sqlbean.columnMap.put("netValue",				this.getNetValue());
		sqlbean.columnMap.put("useSeparate",			this.getUseSeparate());
		sqlbean.columnMap.put("useKind",				this.getUseKind());
		sqlbean.columnMap.put("proofDoc",				this.getProofDoc());
		sqlbean.columnMap.put("field",					this.getField());
		sqlbean.columnMap.put("sourceDate",				ut._transToCE_Year(this.getSourceDate()));
		sqlbean.columnMap.put("bulletinDate",			ut._transToCE_Year(this.getBulletinDate()));
		sqlbean.columnMap.put("valueUnit",				this.getValueUnit());
		sqlbean.columnMap.put("notes",					this.getNotes());
		sqlbean.columnMap.put("oldPropertyNo",			this.getOldPropertyNo());
		sqlbean.columnMap.put("oldSerialNo",			this.getOldSerialNo());
		sqlbean.columnMap.put("mergeDivision",			this.getCaseNo_Merge());
		sqlbean.columnMap.put("mergeDivisionBatch",		this.getMergeDivisionBatch());
		sqlbean.columnMap.put("editID",					this.getEditID());
		sqlbean.columnMap.put("editDate",				ut._transToCE_Year(this.getEditDate()));
		sqlbean.columnMap.put("editTime",				this.getEditTime());
		sqlbean.columnMap.put("buyDate",				ut._transToCE_Year(this.getBuyDate()));
		sqlbean.columnMap.put("KeepUnit",				this.getKeepUnit());
		sqlbean.columnMap.put("Keeper",					this.getKeeper());
		sqlbean.columnMap.put("UseUnit",				this.getUseUnit());
		sqlbean.columnMap.put("UserNo",					this.getUserNo());
		sqlbean.columnMap.put("UserNote",				this.getUserNote());
		sqlbean.columnMap.put("Place1",					this.getPlace1());
		sqlbean.columnMap.put("Place",					this.getPlace());
		sqlbean.columnMap.put("engineeringNo",			this.getEngineeringNo());
		sqlbean.columnMap.put("caseSerialNo",			this.getCaseSerialNo());
	}

	public UNTLA054Q_data getParameterData(){
		qbean.tableName="UNTLA_ReduceDetail";
		qbean.caseNo=this.getCaseNo_Reduce();
		qbean.enterOrg=this.getEnterOrg();
		qbean.ownership=this.getOwnership();
		qbean.differenceKind=this.getDifferenceKind();
		qbean.propertyNo=this.getPropertyNo_Reduce();
		qbean.serialNo=this.getSerialNo_Reduce();
		return qbean;
	}

	//清除畫面所有欄位資料用
	public void clearAllDataForView(){
		this.setProperty_ReduceName("");		
		this.setPropertyName1("");
		this.setSignNo("");
		this.setSignNo1("");
		this.setSignNo2("");
		this.setSignNo3("");
		this.setSignNo4("");
		this.setSignNo5("");
		this.setCause("");
		this.setCause1("");
		this.setReduceDate("");
		this.setNewEnterOrg("");
		this.setTransferDate("");
		this.setPropertyKind("");
		this.setFundType("");
		this.setValuable("");
		this.setTaxCredit("");
		this.setArea("");
		this.setUseState1("");
		this.setHoldNume("");
		this.setHoldDeno("");
		this.setHoldArea("");
		this.setBookNotes("");
		this.setAccountingTitle("");
		this.setBookUnit("");
		this.setBookValue("");
		this.setNetUnit("");
		this.setNetValue("");
		this.setUseSeparate("");
		this.setUseKind("");
		this.setProofDoc("");
		this.setField("");
		this.setSourceDate("");
		this.setBulletinDate("");
		this.setValueUnit("");
		this.setNotes("");
		this.setOldPropertyNo("");
		this.setOldSerialNo("");
		this.setEditID("");
		this.setEditDate("");
		this.setEditTime("");
		this.setMergeDivision("");
		this.setMergeDivisionBatch("");
	}

	//儲存切換不同頁面時所需要的訊息
	public void setTransData_MainToTran(){
		this.setCaseNo(this.getCaseNo_Reduce());
		this.setPropertyNo(this.getPropertyNo_Reduce());
		this.setSerialNo(this.getSerialNo_Reduce());
	}
	
	//儲存切換不同頁面時所需要的訊息
	public void setTransData_TranToMain(){
		this.setCaseNo_Reduce(this.getCaseNo());
		this.setPropertyNo_Reduce(this.getPropertyNo());
		this.setSerialNo_Reduce(this.getSerialNo());
	}
	
//傳回執行insert前之檢查sql
protected String[][] getInsertCheckSQL(){
	String[][] checkSQLArray = new String[2][4];
	
	setTransData_MainToTran();
	
	checkSQLArray[0][0]=" select count(*) as checkResult from UNTLA_ReduceDetail where 1=1 " + 
		" and enterOrg = " + Common.sqlChar(this.getEnterOrg()) + 
		" and ownership = " + Common.sqlChar(this.getOwnership()) + 
		" and differencekind = " + Common.sqlChar(this.getDifferenceKind()) +
		" and propertyNo = " + Common.sqlChar(this.getPropertyNo()) + 
		" and serialNo = " + Common.sqlChar(this.getSerialNo()) + 
		"";
	checkSQLArray[0][1]=">";
	checkSQLArray[0][2]="0";
	checkSQLArray[0][3]="該筆資料己重複，請重新輸入！";
	
	LogBean.outputLogDebug(checkSQLArray[0][0]);
	
	checkSQLArray[1][0]=" select count(*) as checkResult from UNTLA_AdjustDetail where 1=1 " + 
		" and enterOrg = " + Common.sqlChar(this.getEnterOrg()) + 
		" and ownership = " + Common.sqlChar(this.getOwnership()) +
		" and differencekind = " + Common.sqlChar(this.getDifferenceKind()) +
		" and propertyNo = " + Common.sqlChar(this.getPropertyNo()) + 
		" and serialNo = " + Common.sqlChar(this.getSerialNo()) +
		" and verify = 'N' " + 
		"";
	checkSQLArray[1][1]=">";
	checkSQLArray[1][2]="0";
	checkSQLArray[1][3]="土地增減值作業存在未入帳的資料，請重新輸入！！";

	LogBean.outputLogDebug(checkSQLArray[1][0]);
	
	return checkSQLArray;
}


//傳回insert sql
protected String[] getInsertSQL(){
	String[] execSQLArray = new String[1];
	
	if("".equals(Common.get(this.getCaseSerialNo()))){			
		DataStructor ds = new DataStructor();
		ds.enterOrg = this.getEnterOrg();
		ds.ownership = this.getOwnership();
		ds.caseNo = this.getCaseNo();
		ds.differenceKind = this.getDifferenceKind();
		
		String[] tables = new String[]{"UNTLA_REDUCEDETAIL"};
		
		this.setCaseSerialNo(new UNTCH_Tools()._getNewCaseSerialNo(ds,tables));			
	}
	
	setSQLBeanValue();
	execSQLArray[0]=sqlbean.getSQLMethod_Insert(sqlbean);	
	LogBean.outputLogDebug(execSQLArray[0]);
	
	extend_getInsertSQL();
	
	return execSQLArray;
}

//傳回update sql
protected String[] getUpdateSQL(){
	String[] execSQLArray = new String[1];
	
	setTransData_MainToTran();
	
	setSQLBeanValue();
	execSQLArray[0]=sqlbean.getSQLMethod_Update(sqlbean);	
	LogBean.outputLogDebug(execSQLArray[0]);

	extend_getUpdateSQL();
	
	return execSQLArray;
}


//傳回delete sql
protected String[] getDeleteSQL(){
	String[] execSQLArray = new String[1];

	setTransData_MainToTran();
	
	setSQLBeanValue();
	execSQLArray[0]=sqlbean.getSQLMethod_Delete(sqlbean);	
	LogBean.outputLogDebug(execSQLArray[0]);
	
	extend_getDeleteSQL();
	
	return execSQLArray;
}


//依主鍵查詢單一資料
public UNTLA056F  queryOne() throws Exception{
	Database db = new Database();
	UNTLA056F obj = this;
	String sql="select a.*," +
				" (select placename from SYSCA_PLACE z where z.enterorg = a.enterorg and z.placeno = a.place1) as place1Name" +
				" from UNTLA_ReduceDetail a where 1=1"+
				" and enterorg = "+ Common.sqlChar(getEnterOrg())+
				" and ownership = "+ Common.sqlChar(getOwnership())+
				" and differencekind = "+ Common.sqlChar(getDifferenceKind())+
				" and caseno = "+ Common.sqlChar(getCaseNo_Reduce())+
				" and propertyno = "+ Common.sqlChar(getPropertyNo())+
				" and serialno = "+ Common.sqlChar(getSerialNo());

	try {
		LogBean.outputLogDebug(sql);
		UNTCH_Tools ut = new UNTCH_Tools();
		ResultSet rs = db.querySQL(sql);
		if (rs.next()){
			obj.setEnterOrg(rs.getString("enterOrg"));
			obj.setOwnership(rs.getString("ownership"));
			obj.setCaseNo(rs.getString("caseNo"));
			obj.setCaseNo_Reduce(rs.getString("caseNo"));
			obj.setDifferenceKind(rs.getString("differencekind"));
			obj.setPropertyNo(rs.getString("propertyNo"));
			obj.setPropertyNo_Reduce(rs.getString("propertyNo"));
			obj.setSerialNo(rs.getString("serialNo"));
			obj.setSerialNo_Reduce(rs.getString("serialNo"));
			obj.setPropertyName1(rs.getString("propertyName1"));
			obj.setSignNo(rs.getString("signNo"));
			obj.setCause(rs.getString("cause"));
			obj.setCause1(rs.getString("cause1"));
			obj.setReduceCause(rs.getString("cause"));
			obj.setReduceCause1(rs.getString("cause1"));
			obj.setReduceDate(ut._transToROC_Year(rs.getString("reduceDate")));
			obj.setNewEnterOrg(rs.getString("newEnterOrg"));
			obj.setTransferDate(ut._transToROC_Year(rs.getString("transferDate")));
			obj.setVerify(rs.getString("verify"));
			obj.setPropertyKind(rs.getString("propertyKind"));
			obj.setFundType(rs.getString("fundType"));
			obj.setValuable(rs.getString("valuable"));
			obj.setTaxCredit(rs.getString("taxCredit"));
			obj.setArea(rs.getString("area"));
			obj.setUseState1(rs.getString("useState1"));
			obj.setHoldNume(rs.getString("holdNume"));
			obj.setHoldDeno(rs.getString("holdDeno"));
			obj.setHoldArea(rs.getString("holdArea"));
			obj.setBookNotes(rs.getString("bookNotes"));
			obj.setAccountingTitle(rs.getString("accountingTitle"));
			obj.setBookUnit(rs.getString("bookUnit"));
			obj.setBookValue(rs.getString("bookValue"));
			obj.setNetUnit(rs.getString("netUnit"));
			obj.setNetValue(rs.getString("netValue"));
			obj.setUseSeparate(rs.getString("useSeparate"));
			obj.setUseKind(rs.getString("useKind"));
			obj.setProofDoc(rs.getString("proofDoc"));
			obj.setField(rs.getString("field"));
			obj.setSourceDate(ut._transToROC_Year(rs.getString("sourceDate")));
			obj.setBulletinDate(ut._transToROC_Year(rs.getString("bulletinDate")));
			obj.setValueUnit(rs.getString("valueUnit"));
			obj.setNotes(rs.getString("notes"));
			obj.setOldPropertyNo(rs.getString("oldPropertyNo"));
			obj.setOldSerialNo(rs.getString("oldSerialNo"));
			obj.setMergeDivision(rs.getString("mergeDivision"));
			obj.setMergeDivisionBatch(rs.getString("mergeDivisionBatch"));
			obj.setEditID(rs.getString("editID"));
			obj.setEditDate(ut._transToROC_Year(rs.getString("editDate")));
			obj.setEditTime(rs.getString("editTime"));
			obj.setPlace1(rs.getString("place1"));
			obj.setEnterOrgName(getEnterOrgNameFromDB(getParameterData()));
			obj.setProperty_ReduceName(getPropertyNoNameFromDB(getParameterData()));

			if((!"".equals(obj.getSignNo())) && (obj.getSignNo().length() == 15)){
				obj.setSignNo1(obj.getSignNo().substring(0,1)+"000000");
				obj.setSignNo2(obj.getSignNo().substring(0,3)+"0000");
				obj.setSignNo3(obj.getSignNo().substring(0,7));
				obj.setSignNo4(obj.getSignNo().substring(7,11));
				obj.setSignNo5(obj.getSignNo().substring(11));
			}

			obj.setEngineeringNoName(ut._getEngineeringNoName(rs.getString("enterOrg"),rs.getString("engineeringNo")));
			obj.setPlace1Name(rs.getString("place1Name"));
			
			setTransData_TranToMain();		
		}
		
		setStateQueryOneSuccess();
	} catch (Exception e) {
		LogBean.outputLogError("Exception : "+e.getMessage()+"\n"+sql);
		
	} finally {
		db.closeAll();
	}
	return obj;
}

//依查詢欄位查詢多筆資料
public ArrayList queryAll() throws Exception{
	Database db = new Database();
	ArrayList objList=new ArrayList();
	
	String sql="select "+
					" a.caseSerialNo, a.differencekind," +
					" (select codename from SYSCA_CODE z where z.codekindid = 'DFK' and z.codeid = a.differencekind) as differencekindName," +
					" a.enterorg, a.ownership, a.caseNo, a.propertyNo, a.serialNo, a.signno, " +
					" (select z.organsname from sysca_organ z where 1=1 and z.organID = a.enterOrg) as enterOrgName," +
					" (select z.codeName from sysca_code z where 1=1 and (z.codeKindID = 'owa' or z.codeKindID = 'OWA') and z.codeid = a.ownership) as ownershipName,"+
					" (select z.codeName from SYSCA_Code z where 1=1 and z.codeKindID='CAA' and z.codeid=a.cause) as causeName,"+
					" (select z.codeName from SYSCA_Code z where 1=1 and z.codeKindID='PKD' and z.codeid=a.propertykind) as propertykindName, "+
					" (case a.useState1 when '01' then '空置' when '02' then '建物' when '03' then '農作' when '04' then '其他' end) as useState1, a.holdarea, a.netvalue"+
				" from UNTLA_ReduceDetail a where 1=1";
	
	if("".equals(Common.checkGet(this.getOwnership())) || "".equals(Common.checkGet(this.getDifferenceKind()))){
		sql += " and a.enterorg = " + Common.sqlChar(this.getEnterOrg()) +
				" and a.mergedivision = " + Common.sqlChar(this.getCaseNo_Merge());
				
	}else{
		sql += " and a.enterorg = " + Common.sqlChar(this.getEnterOrg()) +
				" and a.ownership = " + Common.sqlChar(this.getOwnership()) +
				" and a.caseno = " + Common.sqlChar(this.getCaseNo_Reduce()) +
				" and a.differencekind = " + Common.sqlChar(this.getDifferenceKind());
		
	}
	
					
	
		sql+=" order by enterOrg, ownership, caseNo, differencekind, propertyNo, serialNo ";

	try {
		LogBean.outputLogDebug(sql);
		
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
			rowArray[1]=Common.get(rs.getString("caseNo"));
			rowArray[2]=Common.get(rs.getString("caseSerialNo"));
			rowArray[3]=Common.get(rs.getString("ownership"));
			rowArray[4]=Common.get(rs.getString("ownershipName"));
			rowArray[5]=Common.get(rs.getString("differencekind"));
			rowArray[6]=Common.get(rs.getString("propertyNo"));
			rowArray[7]=Common.get(rs.getString("serialNo")); 
			rowArray[8]=Common.get(getSignDescName(rs.getString("signNo").substring(0,7)) + rs.getString("signNo").substring(7,11) + '-' + rs.getString("signNo").substring(11));
			rowArray[9]=Common.get(rs.getString("causeName"));
			rowArray[10]=Common.get(rs.getString("useState1"));
			rowArray[11]=Common.get(rs.getString("holdArea"));
			rowArray[12]=Common.get(rs.getString("netValue"));
			
			objList.add(rowArray);
			count++;
			} while (rs.next());
		}
		setStateQueryAllSuccess();
	} catch (Exception e) {
		LogBean.outputLogError("Exception : "+e.getMessage()+"\n"+sql);
		
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

public void execUpdateto_Untla_ReduceDetail_byBatch(){
	String sql="update UNTLA_REDUCEDETAIL set mergedivision = " + Common.sqlChar(this.getCaseNo_Merge()) + 
				" where caseno = " + Common.sqlChar(this.getCaseNo_Reduce());
	
	this.execSQL_ForNoResult(sql);
}

}


