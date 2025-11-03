/*
*<br>程式目的：非消耗品主檔資料維護－批號資料
*<br>程式代號：untne002f
*<br>程式日期：0941031
*<br>程式作者：cherry
*<br>--------------------------------------------------------
*<br>修改作者　　修改日期　　　修改目的
*<br>--------------------------------------------------------
*/

package unt.ne;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Map;

import unt.ch.UNTCH_Tools;
import util.Common;
import util.Database;
import util.TCGHCommon;

public class UNTNE002F_2 extends UNTNE001Q{


String enterOrg;
String enterOrgName;
String ownership;
String caseNo;
String propertyNo;
String propertyNoName;
String lotNo;
String serialNoS;
String serialNoE;
String propertyName;
String propertyUnit;
String material;
String limitYear;
String otherLimitYear;
String propertyName1;
String cause;
String cause1;
String approveDate;
String approveDoc;
String enterDate;
String buyDate;
String dataState;
String verify;
String propertyKind;
String fundType;
String valuable;
String originalAmount;
String originalUnit;
String originalBV;
String originalNote;
String accountingTitle;
String bookAmount;
String bookValue;
String fundsSource;
String grantValue;
String articleName;
String nameplate;
String specification;
String storeNo;
String storeNoName;
String sourceKind;
String sourceDate;
String sourceDoc;
String notes;
String originalMoveDate;
String originalKeepUnit;
String originalKeeper;
String originalUseUnit;
String originalUser;
String originalPlace;
String filestoreLocation;
String checkClosing;
String initDtl;
String checkVerify;
String serialNo;
String otherPropertyUnit;
String otherMaterial;
String differenceKind;
String caseSerialNo;
String causeName;
String sourceKindName;
String keepUnit;
String keeper;
String useUnit;
String userNo;
String place;
String place1Name;
String place1;
String userNote;

public String getCheckClosing(){ return checkGet(checkClosing); }
public void setCheckClosing(String s){ checkClosing=checkSet(s); }
public String getInitDtl(){ return checkGet(initDtl); }
public void setInitDtl(String s){ initDtl=checkSet(s); }
public String getCheckVerify(){ return checkGet(checkVerify); }
public void setCheckVerify(String s){ checkVerify=checkSet(s); }
public String getSerialNo(){ return checkGet(serialNo); }
public void setSerialNo(String s){ serialNo=checkSet(s); }

String detailOriginalAmount;
String detailOriginalBV;
String detailBookAmount;
String detailBookValue;
String serialNoAttachment2;
String checkOriginalAmount;
String checkOriginalUnit;
String checkOriginalBV;
String propertyType;

public String getDetailOriginalAmount(){ return checkGet(detailOriginalAmount); }
public void setDetailOriginalAmount(String s){ detailOriginalAmount=checkSet(s); }
public String getDetailOriginalBV(){ return checkGet(detailOriginalBV); }
public void setDetailOriginalBV(String s){ detailOriginalBV=checkSet(s); }
public String getDetailBookAmount(){ return checkGet(detailBookAmount); }
public void setDetailBookAmount(String s){ detailBookAmount=checkSet(s); }
public String getDetailBookValue(){ return checkGet(detailBookValue); }
public void setDetailBookValue(String s){ detailBookValue=checkSet(s); }
public String getSerialNoAttachment2(){ return checkGet(serialNoAttachment2); }
public void setSerialNoAttachment2(String s){ serialNoAttachment2=checkSet(s); }
public String getCheckOriginalAmount(){ return checkGet(checkOriginalAmount); }
public void setCheckOriginalAmount(String s){ checkOriginalAmount=checkSet(s); }
public String getCheckOriginalUnit(){ return checkGet(checkOriginalUnit); }
public void setCheckOriginalUnit(String s){ checkOriginalUnit=checkSet(s); }
public String getCheckOriginalBV(){ return checkGet(checkOriginalBV); }
public void setCheckOriginalBV(String s){ checkOriginalBV=checkSet(s); }
public String getPropertyType(){ return checkGet(propertyType); }
public void setPropertyType(String s){ propertyType=checkSet(s); }

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
public String getLotNo(){ return checkGet(lotNo); }
public void setLotNo(String s){ lotNo=checkSet(s); }
public String getSerialNoS(){ return checkGet(serialNoS); }
public void setSerialNoS(String s){ serialNoS=checkSet(s); }
public String getSerialNoE(){ return checkGet(serialNoE); }
public void setSerialNoE(String s){ serialNoE=checkSet(s); }
public String getPropertyName(){ return checkGet(propertyName); }
public void setPropertyName(String s){ propertyName=checkSet(s); }
public String getPropertyUnit(){ return checkGet(propertyUnit); }
public void setPropertyUnit(String s){ propertyUnit=checkSet(s); }
public String getMaterial(){ return checkGet(material); }
public void setMaterial(String s){ material=checkSet(s); }
public String getLimitYear(){ return checkGet(limitYear); }
public void setLimitYear(String s){ limitYear=checkSet(s); }
public String getOtherLimitYear(){ return checkGet(otherLimitYear); }
public void setOtherLimitYear(String s){ otherLimitYear=checkSet(s); }
public String getPropertyName1(){ return checkGet(propertyName1); }
public void setPropertyName1(String s){ propertyName1=checkSet(s); }
public String getCause(){ return checkGet(cause); }
public void setCause(String s){ cause=checkSet(s); }
public String getCause1(){ return checkGet(cause1); }
public void setCause1(String s){ cause1=checkSet(s); }
public String getApproveDate(){ return checkGet(approveDate); }
public void setApproveDate(String s){ approveDate=checkSet(s); }
public String getApproveDoc(){ return checkGet(approveDoc); }
public void setApproveDoc(String s){ approveDoc=checkSet(s); }
public String getEnterDate(){ return checkGet(enterDate); }
public void setEnterDate(String s){ enterDate=checkSet(s); }
public String getBuyDate(){ return checkGet(buyDate); }
public void setBuyDate(String s){ buyDate=checkSet(s); }
public String getDataState(){ return checkGet(dataState); }
public void setDataState(String s){ dataState=checkSet(s); }
public String getVerify(){ return checkGet(verify); }
public void setVerify(String s){ verify=checkSet(s); }
public String getPropertyKind(){ return checkGet(propertyKind); }
public void setPropertyKind(String s){ propertyKind=checkSet(s); }
public String getFundType(){ return checkGet(fundType); }
public void setFundType(String s){ fundType=checkSet(s); }
public String getValuable(){ return checkGet(valuable); }
public void setValuable(String s){ valuable=checkSet(s); }
public String getOriginalAmount(){ return checkGet(originalAmount); }
public void setOriginalAmount(String s){ originalAmount=checkSet(s); }
public String getOriginalUnit(){ return checkGet(originalUnit); }
public void setOriginalUnit(String s){ originalUnit=checkSet(s); }
public String getOriginalBV(){ return checkGet(originalBV); }
public void setOriginalBV(String s){ originalBV=checkSet(s); }
public String getOriginalNote(){ return checkGet(originalNote); }
public void setOriginalNote(String s){ originalNote=checkSet(s); }
public String getAccountingTitle(){ return checkGet(accountingTitle); }
public void setAccountingTitle(String s){ accountingTitle=checkSet(s); }
public String getBookAmount(){ return checkGet(bookAmount); }
public void setBookAmount(String s){ bookAmount=checkSet(s); }
public String getBookValue(){ return checkGet(bookValue); }
public void setBookValue(String s){ bookValue=checkSet(s); }
public String getFundsSource(){ return checkGet(fundsSource); }
public void setFundsSource(String s){ fundsSource=checkSet(s); }
public String getGrantValue(){ return checkGet(grantValue); }
public void setGrantValue(String s){ grantValue=checkSet(s); }
public String getArticleName(){ return checkGet(articleName); }
public void setArticleName(String s){ articleName=checkSet(s); }
public String getNameplate(){ return checkGet(nameplate); }
public void setNameplate(String s){ nameplate=checkSet(s); }
public String getSpecification(){ return checkGet(specification); }
public void setSpecification(String s){ specification=checkSet(s); }
public String getStoreNo(){ return checkGet(storeNo); }
public void setStoreNo(String s){ storeNo=checkSet(s); }
public String getStoreNoName(){ return checkGet(storeNoName); }
public void setStoreNoName(String s){ storeNoName=checkSet(s); }
public String getSourceKind(){ return checkGet(sourceKind); }
public void setSourceKind(String s){ sourceKind=checkSet(s); }
public String getSourceDate(){ return checkGet(sourceDate); }
public void setSourceDate(String s){ sourceDate=checkSet(s); }
public String getSourceDoc(){ return checkGet(sourceDoc); }
public void setSourceDoc(String s){ sourceDoc=checkSet(s); }
public String getNotes(){ return checkGet(notes).replace("$", "\\$"); }
public void setNotes(String s){ notes=checkSet(s); }
public String getOriginalMoveDate(){ return checkGet(originalMoveDate); }
public void setOriginalMoveDate(String s){ originalMoveDate=checkSet(s); }
public String getOriginalKeepUnit(){ return checkGet(originalKeepUnit); }
public void setOriginalKeepUnit(String s){ originalKeepUnit=checkSet(s); }
public String getOriginalKeeper(){ return checkGet(originalKeeper); }
public void setOriginalKeeper(String s){ originalKeeper=checkSet(s); }
public String getOriginalUseUnit(){ return checkGet(originalUseUnit); }
public void setOriginalUseUnit(String s){ originalUseUnit=checkSet(s); }
public String getOriginalUser(){ return checkGet(originalUser); }
public void setOriginalUser(String s){ originalUser=checkSet(s); }
public String getOriginalPlace(){ return checkGet(originalPlace); }
public void setOriginalPlace(String s){ originalPlace=checkSet(s); }
public String getFilestoreLocation(){ return checkGet(filestoreLocation); }
public void setFilestoreLocation(String s){ filestoreLocation=checkSet(s); }
public String getOtherPropertyUnit(){ return checkGet(otherPropertyUnit); }
public void setOtherPropertyUnit(String s){ otherPropertyUnit=checkSet(s); }
public String getOtherMaterial(){ return checkGet(otherMaterial); }
public void setOtherMaterial(String s){ otherMaterial=checkSet(s); }
public String getDifferenceKind() {return checkGet(this.differenceKind);}
public void setDifferenceKind(String s) {differenceKind = checkSet(s);}
public String getCaseSerialNo() {return checkGet(caseSerialNo);}
public void setCaseSerialNo(String s) {this.caseSerialNo = checkSet(s);}
public String getCauseName() {return checkGet(causeName);}
public void setCauseName(String s) {this.causeName = checkSet(s);}
public String getSourceKindName() {return checkGet(sourceKindName);}
public void setSourceKindName(String s) {this.sourceKindName = checkSet(s);}
public String getKeepUnit() {return checkGet(keepUnit);}
public void setKeepUnit(String s) {this.keepUnit = checkSet(s);}
public String getKeeper() {return checkGet(keeper);}
public void setKeeper(String s) {this.keeper = checkSet(s);}
public String getUseUnit() {return checkGet(useUnit);}
public void setUseUnit(String s) {this.useUnit = checkSet(s);}
public String getUserNo() {	return checkGet(userNo);}
public void setUserNo(String s) {this.userNo = checkSet(s);}
public String getPlace() {return checkGet(place);}
public void setPlace(String s) {this.place = checkSet(s);}
public String getPlace1Name() {return checkGet(place1Name);}
public void setPlace1Name(String s) {this.place1Name = checkSet(s);}
public String getPlace1() {return checkGet(place1);}
public void setPlace1(String s) {this.place1 = checkSet(s);}
public String getUserNote() {return checkGet(userNote);}
public void setUserNote(String s) {this.userNote = checkSet(s);}

String oldPropertyNo;
String oldSerialNoS;
String oldSerialNoE;

public String getOldPropertyNo(){ return checkGet(oldPropertyNo); }
public void setOldPropertyNo(String s){ oldPropertyNo=checkSet(s); }
public String getOldSerialNoS(){ return checkGet(oldSerialNoS); }
public void setOldSerialNoS(String s){ oldSerialNoS=checkSet(s); }
public String getOldSerialNoE(){ return checkGet(oldSerialNoE); }
public void setOldSerialNoE(String s){ oldSerialNoE=checkSet(s); }

String fundsSource1;

public String getFundsSource1(){ return checkGet(fundsSource1); }
public void setFundsSource1(String s){ fundsSource1=checkSet(s); }

String trueOriginalAmount = "";
String trueOriginalUnit = "";
String trueOriginalBV = "";

String originalUseBureau;
public String getOriginalUseBureau(){ return checkGet(originalUseBureau); }
public void setOriginalUseBureau(String s){ originalUseBureau=checkSet(s); }

String originalKeepBureau;
public String getOriginalKeepBureau(){ return checkGet(originalKeepBureau); }
public void setOriginalKeepBureau(String s){ originalKeepBureau=checkSet(s); }

//傳回執行insert前之檢查sql
protected String[][] getInsertCheckSQL(){
	trueOriginalAmount = originalAmount==null || "".equals(originalAmount)?"0":originalAmount;
	trueOriginalUnit = originalUnit==null || "".equals(originalUnit)?"0":originalUnit;
	trueOriginalBV = originalBV==null || "".equals(originalBV)?"0":originalBV;
	//取得批號
	Database db = new Database();
	ResultSet rs;	
	String sql="select ISNULL(max(lotNo),0)+1 as lotNo from UNTNE_Nonexp " +
		" where enterOrg = " + Common.sqlChar(enterOrg) + 
		" and ownership = " + Common.sqlChar(ownership) + 
		" and propertyNo = " + Common.sqlChar(propertyNo) + 
		" and differenceKind = " + Common.sqlChar(differenceKind) + 
		"";		
	try {		
		rs = db.querySQL(sql);
		if (rs.next()){
		    setLotNo(Common.formatFrontZero(rs.getString("lotNo"),7));
		} else {
			setLotNo("0000001");
		}

		//取得財產分號-起,訖
		sql="select ISNULL(max(serialNo),0)+1 as serialNoS, ISNULL(max(serialNo),0)+" + originalAmount +" as serialNoE "+
			" from UNTNE_NonexpDetail " +
			" where enterOrg = " + Common.sqlChar(enterOrg) + 
			" and ownership = " + Common.sqlChar(ownership) + 
			" and propertyNo = " + Common.sqlChar(propertyNo) +
			" and differenceKind = " + Common.sqlChar(differenceKind) +
			"";		
		rs = db.querySQL(sql);
		if (rs.next()){
			setSerialNoS(Common.formatFrontZero(rs.getString("serialNoS"),7));
			if(Integer.parseInt(trueOriginalUnit)==0){
				setSerialNoE(Common.formatFrontZero(rs.getString("serialNoS"),7));
			}else{
				setSerialNoE(Common.formatFrontZero(rs.getString("serialNoE"),7));
			}
		} else {
			setSerialNoS("0000001");
			setSerialNoE("0000001");
		}

		//取得UNTNE_NenoexpDetail分號
		sql="select ISNULL(max(serialNo),0)+1 as serialNo from UNTNE_NonexpDetail " +
			" where enterOrg = " + Common.sqlChar(enterOrg) + 
			" and ownership = " + Common.sqlChar(ownership) + 
			" and propertyNo = " + Common.sqlChar(propertyNo) +
			" and differenceKind = " + Common.sqlChar(differenceKind) +
			"";		
		rs = db.querySQL(sql);
		if (rs.next()){
			setSerialNo(Common.formatFrontZero(rs.getString("serialNo"),7));
		} else {
			setSerialNo("0000001");
		}
		//取得序號
		sql="select ISNULL(max(caseSerialNo),0)+1 as caseSerialNo "+
			" from UNTNE_Nonexp " +
			" where enterOrg = " + Common.sqlChar(enterOrg) + 
			" and caseNo = " + Common.sqlChar(caseNo) +
			"";	
		rs = db.querySQL(sql);
		if (rs.next()){
			setCaseSerialNo(Common.formatFrontZero(rs.getString("caseSerialNo"),5));
		} else {
			setCaseSerialNo("00001");
		}
	} catch (Exception e) {
		e.printStackTrace();
	} finally {
		db.closeAll();
	}
	String[][] checkSQLArray = new String[1][4];
 	checkSQLArray[0][0]=" select count(*) as checkResult from UNTNE_Nonexp where 1=1 " + 
		" and enterOrg = " + Common.sqlChar(enterOrg) + 
		" and ownership = " + Common.sqlChar(ownership) + 
		" and propertyNo = " + Common.sqlChar(propertyNo) + 
		" and lotNo = " + Common.sqlChar(lotNo) +
		" and differenceKind = " + Common.sqlChar(differenceKind) +
		"";
	checkSQLArray[0][1]=">";
	checkSQLArray[0][2]="0";
	checkSQLArray[0][3]="該筆資料己重複，請重新輸入！";
	return checkSQLArray;
}

//依使用者所輸入的「原始入帳－數量」新增資料至「非消耗品主檔－批號明細UNTNE_NonexpDetail」
protected String getInsertUntneNonexpDetail(){
	UNTNE002F_2 obj = this;
	UNTCH_Tools ul = new UNTCH_Tools();
	StringBuffer sbSQL = new StringBuffer("");
	int counter=0;
	if(Integer.parseInt(trueOriginalUnit)==0){
		counter=1;
		obj.setDetailOriginalAmount(bookAmount);
		obj.setDetailOriginalBV(bookValue);
		obj.setDetailBookAmount(bookAmount);
		obj.setDetailBookValue(bookValue);
	}else{
		counter = Integer.parseInt(originalAmount);
		obj.setDetailOriginalAmount("1");
		obj.setDetailOriginalBV(originalUnit);
		obj.setDetailBookAmount("1");
		obj.setDetailBookValue(originalUnit);
	}
	while(counter > 0){
	    counter--;
	    sbSQL.append(" insert into UNTNE_NonexpDetail(" ).append(
				" enterOrg,").append(
				" ownership,").append(
				" propertyNo,").append(
				" lotNo,").append(
				" serialNo,").append(
				" dataState,").append(
				" verify,").append(
				" originalAmount,").append(
				" originalBV,").append(
				" bookAmount,").append(
				" bookValue,").append(
				" originalMoveDate,").append(
				" originalKeepUnit,").append(
				" originalKeeper,").append(
				" originalUseUnit,").append(
				" originalUser,").append(
				" originalPlace,").append(
				" originalPlace1,").append(
				" moveDate,").append(
				" keepUnit,").append(
				" keeper,").append(
				" useUnit,").append(
				" userNo,").append(
				" place,").append(
				" editID,").append(
				" editDate,").append(
				" editTime,").append(
			    " differenceKind,").append(
			    " caseNo,").append(
			    " originalUserNote,").append(
			    " userNote,").append(
			    " place1,").append(
			    " enterDate,").append(
				" barcode,").append(
				" propertyname1").append(
			" )Values(" ).append(
				Common.sqlChar(enterOrg) ).append( "," ).append(
				Common.sqlChar(ownership) ).append( "," ).append(
				Common.sqlChar(propertyNo) ).append( "," ).append(
				Common.sqlChar(lotNo) ).append( "," ).append( 
				"(select RIGHT(REPLICATE('0',7)+CAST(ISNULL(max(serialNo),0)+1 as NVARCHAR),7) from UNTNE_NonexpDetail where enterOrg = ").append(Common.sqlChar(enterOrg)).append("and ownership = " ).append(Common.sqlChar(ownership)).append("and propertyNo = " ).append(Common.sqlChar(propertyNo)).append("and differencekind = " ).append(Common.sqlChar(differenceKind)).append(")" ).append( "," ).append(
				Common.sqlChar(dataState) ).append( "," ).append(
				Common.sqlChar(verify) ).append( "," ).append(
				Common.sqlChar(obj.getDetailOriginalAmount()) ).append( "," ).append(
				Common.sqlChar(obj.getDetailOriginalBV()) ).append( "," ).append(
				Common.sqlChar(obj.getDetailBookAmount()) ).append( "," ).append(
				Common.sqlChar(obj.getDetailBookValue()) ).append( "," ).append(
				Common.sqlChar(originalMoveDate) ).append( "," ).append(
				Common.sqlChar(keepUnit) ).append( "," ).append(
				Common.sqlChar(keeper) ).append( "," ).append(
				Common.sqlChar(useUnit) ).append( "," ).append(
				Common.sqlChar(userNo) ).append( "," ).append(
				Common.sqlChar(place) ).append( "," ).append(
				Common.sqlChar(place1) ).append( "," ).append(
				Common.sqlChar(originalMoveDate) ).append( "," ).append(
				Common.sqlChar(keepUnit) ).append( "," ).append(
				Common.sqlChar(keeper) ).append( "," ).append(
				Common.sqlChar(useUnit) ).append( "," ).append(
				Common.sqlChar(userNo) ).append( "," ).append(
				Common.sqlChar(place) ).append( "," ).append(
				Common.sqlChar(getEditID()) ).append( "," ).append(
				Common.sqlChar(ul._transToCE_Year(getEditDate())) ).append( "," ).append(
				Common.sqlChar(getEditTime()) ).append( "," ).append(
				Common.sqlChar(differenceKind) ).append( "," ).append(
				Common.sqlChar(caseNo) ).append( "," ).append(
				Common.sqlChar(userNote) ).append( "," ).append(
				Common.sqlChar(userNote) ).append( "," ).append(
				Common.sqlChar(place1) ).append( "," ).append(
				Common.sqlChar(enterDate) ).append( "," ).append(
			    Common.sqlChar(differenceKind +"-" + propertyNo  + "-" + serialNo) ).append( "," ).append(
	    		Common.sqlChar(propertyName1)
				).append( ")" ).append(
				":;:");		
//System.out.println(sbSQL);
	}
	return sbSQL.toString();
}

//傳回insert sql
protected String[] getInsertSQL(){
	UNTCH_Tools ul = new UNTCH_Tools();
	String nonexpDetailSQL[];
	nonexpDetailSQL = getInsertUntneNonexpDetail().split(":;:");
	String[] execSQLArray = new String[nonexpDetailSQL.length+1];
	for(int i=1;i<=nonexpDetailSQL.length;i++){
		execSQLArray[i]= nonexpDetailSQL[i-1];
//System.out.println("--untne002f getInsertUntneNonexpDetail-- "+i+" ---\n"+execSQLArray[i]);
	}
	execSQLArray[0]=" insert into UNTNE_Nonexp(" +
			" enterOrg,"+
			" ownership,"+
			" caseNo,"+
			" propertyNo,"+
			" lotNo,"+
			" serialNoS,"+
			" serialNoE," +
			" otherPropertyUnit," +
			" otherMaterial,"+
			" otherLimitYear,"+
			" propertyName1,"+
			" cause,"+
			" cause1,"+
			" approveDate,"+
			" approveDoc,"+
			" enterDate,"+
			" buyDate,"+
			" dataState,"+
			" verify,"+
			" propertyKind,"+
			" fundType,"+
			" valuable,"+
			" originalAmount,"+
			" originalUnit,"+
			" originalBV,"+
			" originalNote,"+
			" accountingTitle,"+
			" bookAmount,"+
			" bookValue,"+
			" fundsSource,"+
			" grantValue,"+
			" articleName,"+
			" nameplate,"+
			" specification,"+
			" storeNo,"+
			" sourceKind,"+
			" sourceDate,"+
			" sourceDoc,"+
			//" picture,"+
			" notes,"+
			" fundsSource1,"+
			" editID,"+
			" editDate,"+
			" editTime,"+
			" differenceKind,"+
			" caseSerialNo "+
		" )Values(" +
			Common.sqlChar(enterOrg) + "," +
			Common.sqlChar(ownership) + "," +
			Common.sqlChar(caseNo) + "," +
			Common.sqlChar(propertyNo) + "," +
			Common.sqlChar(lotNo) + "," +
			Common.sqlChar(serialNoS) + "," +
			Common.sqlChar(serialNoE) + "," +
			Common.sqlChar(otherPropertyUnit) + "," +
			Common.sqlChar(otherMaterial) + "," +
			Common.sqlChar(otherLimitYear) + "," +
			Common.sqlChar(propertyName1) + "," +
			Common.sqlChar(cause) + "," +
			Common.sqlChar(cause1) + "," +
			Common.sqlChar(approveDate) + "," +
			Common.sqlChar(approveDoc) + "," +
			Common.sqlChar(ul._transToCE_Year(enterDate)) + "," +
			Common.sqlChar(ul._transToCE_Year(buyDate)) + "," +
			Common.sqlChar(dataState) + "," +
			Common.sqlChar(verify) + "," +
			Common.sqlChar(propertyKind) + "," +
			Common.sqlChar(fundType) + "," +
			Common.sqlChar(valuable) + "," +
			Common.sqlChar(originalAmount) + "," +
			Common.sqlChar(originalUnit) + "," +
			Common.sqlChar(originalBV) + "," +
			Common.sqlChar(originalNote) + "," +
			Common.sqlChar(accountingTitle) + "," +
			Common.sqlChar(bookAmount) + "," +
			Common.sqlChar(bookValue) + "," +
			Common.sqlChar(fundsSource) + "," +
			Common.sqlChar(grantValue) + "," +
			Common.sqlChar(articleName) + "," +
			Common.sqlChar(nameplate) + "," +
			Common.sqlChar(specification) + "," +
			Common.sqlChar(storeNo) + "," +
			Common.sqlChar(sourceKind) + "," +
			Common.sqlChar(ul._transToCE_Year(sourceDate)) + "," +
			Common.sqlChar(sourceDoc) + "," +
			//Common.sqlChar(picture) + "," +
			Common.sqlChar(notes) + "," +
			Common.sqlChar(fundsSource1) + "," +
			Common.sqlChar(getEditID()) + "," +
			Common.sqlChar(ul._transToCE_Year(getEditDate())) + "," +
			Common.sqlChar(getEditTime()) + "," +
			Common.sqlChar(differenceKind) + "," +
			Common.sqlChar(getCaseSerialNo()) + ")" ;
	return execSQLArray;
}

protected String deleteUntne_nonexpDetail(){
	String strSQL="";
	strSQL +=" delete UNTNE_NonexpDetail where 1=1 " +
			" and enterOrg = " + Common.sqlChar(enterOrg) +
			" and ownership = " + Common.sqlChar(ownership) +
			" and propertyNo = " + Common.sqlChar(propertyNo) +
			" and lotNo = " + Common.sqlChar(lotNo) +
			" and differenceKind = " + Common.sqlChar(differenceKind) +
			":;:";
	strSQL +=" delete UNTNE_Attachment2 where 1=1 " +
			" and enterOrg = " + Common.sqlChar(enterOrg) +
			" and ownership = " + Common.sqlChar(ownership) +
			" and propertyNo = " + Common.sqlChar(propertyNo) +
			" and lotNo = " + Common.sqlChar(lotNo) +
			" and differenceKind = " + Common.sqlChar(differenceKind) +
			":;:";
	return strSQL;
}

//修改「原始入帳－數量 or 原始入帳－單價 or原始入帳－總價」時
protected String getUpdateUntneNonexpDetail(){
	trueOriginalAmount = originalAmount==null || "".equals(originalAmount)?"0":originalAmount;
	trueOriginalUnit = originalUnit==null || "".equals(originalUnit)?"0":originalUnit;
	trueOriginalBV = originalBV==null || "".equals(originalBV)?"0":originalBV;
	//UNTNE002F obj = this;
	String strSQL = "";
	//新增該批號的「批號明細」
	strSQL += getInsertUntneNonexpDetail();
	//	新增該批號的「批號明細附屬設備」，資料同該批號的「批號附屬設備」，並將「資料狀態」設為「1:現存」
	strSQL += getInsertUntneAttachment1();
	return strSQL;
}

//新增該批號的「批號明細附屬設備」，資料同該批號的「批號附屬設備」，並將「資料狀態」設為「1:現存」
protected String getInsertUntneAttachment1(){
	UNTCH_Tools ul = new UNTCH_Tools();
	Database db = new Database();    
	String sql = "";
	String strSQL = "";
	ResultSet rs = null;
	//取得批號附屬設備檔	
	sql="select * from UNTNE_Attachment1 " +
		" where enterOrg = " + Common.sqlChar(enterOrg) + 
		" and ownership = " + Common.sqlChar(ownership) + 
		" and propertyNo = " + Common.sqlChar(propertyNo) + 
		" and lotNo = " + Common.sqlChar(lotNo) +
		" and differenceKind = " + Common.sqlChar(differenceKind) +
		"";		
	try {   
		rs = db.querySQL(sql);
		while (rs.next()){
			int counter=0;
			if(Integer.parseInt(trueOriginalUnit)==0){
				counter=1;
			}else{
				counter = Integer.parseInt(originalAmount);
			}
			while(counter > 0){
			    counter--;
			    strSQL+=" insert into UNTNE_Attachment2( " +
						" enterOrg,"+
						" ownership,"+
						" propertyNo,"+
						" lotNo,"+
						" serialNo,"+
						" serialNo1,"+
						" equipmentName,"+
						" buyDate,"+
						" equipmentUnit,"+
						" equipmentAmount,"+
						" unitPrice,"+
						" totalValue,"+
						" dataState,"+
						" notes,"+
						" editID,"+
						" editDate,"+
						" editTime,"+
						" differenceKind"+
					" )Values(" +
						Common.sqlChar(rs.getString("enterOrg")) + "," +
						Common.sqlChar(rs.getString("ownership")) + "," +
						Common.sqlChar(rs.getString("propertyNo")) + "," +
						Common.sqlChar(rs.getString("lotNo")) + "," +
						"(select RIGHT(REPLICATE('0',7)+CAST(ISNULL(serialNos,0)+"+counter+"as NVARCHAR),7) from untne_nonexp where enterOrg = "+Common.sqlChar(enterOrg)+" and ownership = " + Common.sqlChar(ownership) +" and propertyNo = " + Common.sqlChar(propertyNo) +" and lotNo = " + Common.sqlChar(lotNo) +")" + "," +
						Common.sqlChar(rs.getString("serialNo1")) + "," +
						Common.sqlChar(rs.getString("equipmentName")) + "," +
						Common.sqlChar(ul._transToCE_Year(rs.getString("buyDate"))) + "," +
						Common.sqlChar(rs.getString("equipmentUnit")) + "," +
						Common.sqlChar(rs.getString("equipmentAmount")) + "," +
						Common.sqlChar(rs.getString("unitPrice")) + "," +
						Common.sqlChar(rs.getString("totalValue")) + "," +
						"'" + 1 + "'" + "," +
						Common.sqlChar(rs.getString("notes")) + "," +
						Common.sqlChar(rs.getString("editID")) + "," +
						Common.sqlChar(ul._transToCE_Year(rs.getString("editDate"))) + "," +
						Common.sqlChar(rs.getString("editTime")) + "," +
						Common.sqlChar(rs.getString("differenceKind")) + ")" +
						":;:";
			}
		}
	} catch (Exception e) {
		e.printStackTrace();
	} finally {
		db.closeAll();
	}	
	return strSQL;
}

//傳回update sql
protected String[] getUpdateSQL(){
	UNTCH_Tools ul = new UNTCH_Tools();
	String serialNoSSQL = "";
	String serialNoESQL = "";
	String nonexpDetailSQL[]=null;
	String deleteNonexpDetailSQL[]=null;
	int deleteNonexpDetail = 0;
	int nonexpDetail = 0;	
	trueOriginalAmount = originalAmount==null || "".equals(originalAmount)?"0":originalAmount;
	trueOriginalUnit = originalUnit==null || "".equals(originalUnit)?"0":originalUnit;
	trueOriginalBV = originalBV==null || "".equals(originalBV)?"0":originalBV;
	checkOriginalAmount = checkOriginalAmount==null || "".equals(checkOriginalAmount)?"0":checkOriginalAmount;
	checkOriginalUnit = checkOriginalUnit==null || "".equals(checkOriginalUnit)?"0":checkOriginalUnit;
	checkOriginalBV = checkOriginalBV==null || "".equals(checkOriginalBV)?"0":checkOriginalBV;
	if(Integer.parseInt(trueOriginalAmount) != Integer.parseInt(checkOriginalAmount) || Integer.parseInt(trueOriginalUnit) != Integer.parseInt(checkOriginalUnit) || Integer.parseInt(trueOriginalBV) != Integer.parseInt(checkOriginalBV)){
		String amount="1";
		if(Integer.parseInt(trueOriginalUnit)==0){
			amount = "1";
		}else{
			amount = originalAmount;
		}
		serialNoSSQL = "(select RIGHT(REPLICATE('0',7)+CAST(ISNULL(max(serialNo),0)+1 as NVARCHAR),7) from UNTNE_NonexpDetail where enterOrg = " + Common.sqlChar(enterOrg) + "and ownership = " + Common.sqlChar(ownership) + "and propertyNo = " + Common.sqlChar(propertyNo) +")";
		serialNoESQL = "(select RIGHT(REPLICATE('0',7)+CAST(ISNULL(max(serialNo),0)+"+amount+" as NVARCHAR),7) from UNTNE_NonexpDetail where enterOrg = " + Common.sqlChar(enterOrg) + "and ownership = " + Common.sqlChar(ownership) + "and propertyNo = " + Common.sqlChar(propertyNo) +")";
		deleteNonexpDetailSQL = deleteUntne_nonexpDetail().split(":;:");
		nonexpDetailSQL = getUpdateUntneNonexpDetail().split(":;:");
	}else{
		serialNoSSQL = Common.sqlChar(serialNoS);
		serialNoESQL = Common.sqlChar(serialNoE);
	}
	deleteNonexpDetail = deleteNonexpDetailSQL==null?0:deleteNonexpDetailSQL.length;
	nonexpDetail = nonexpDetailSQL==null?0:nonexpDetailSQL.length;
	String[] execSQLArray = new String[deleteNonexpDetail+nonexpDetail+1];
	for(int i=0;i<deleteNonexpDetail;i++){
		execSQLArray[i] = deleteNonexpDetailSQL[i];
	}
	for(int i=3;i<nonexpDetail+3;i++){
		execSQLArray[i] = nonexpDetailSQL[i-3];
	}
	execSQLArray[deleteNonexpDetail]=" update UNTNE_Nonexp set " +
									" serialNoS = " + serialNoSSQL + "," +
									" serialNoE = " + serialNoESQL + "," +
									" otherPropertyUnit = " + Common.sqlChar(otherPropertyUnit) + "," +
									" otherMaterial = " + Common.sqlChar(otherMaterial) + "," +
									" otherLimitYear = " + Common.sqlChar(otherLimitYear) + "," +
									" propertyName1 = " + Common.sqlChar(propertyName1) + "," +
									" cause = " + Common.sqlChar(cause) + "," +
									" cause1 = " + Common.sqlChar(cause1) + "," +
									" approveDate = " + Common.sqlChar(approveDate) + "," +
									" approveDoc = " + Common.sqlChar(approveDoc) + "," +
									" enterDate = " + Common.sqlChar(ul._transToCE_Year(enterDate)) + "," +
									" buyDate = " + Common.sqlChar(ul._transToCE_Year(buyDate)) + "," +
									" dataState = " + Common.sqlChar(dataState) + "," +
									" verify = " + Common.sqlChar(verify) + "," +
									" propertyKind = " + Common.sqlChar(propertyKind) + "," +
									" fundType = " + Common.sqlChar(fundType) + "," +
									" valuable = " + Common.sqlChar(valuable) + "," +
									" originalAmount = " + Common.sqlChar(originalAmount) + "," +
									" originalUnit = " + Common.sqlChar(originalUnit) + "," +
									" originalBV = " + Common.sqlChar(originalBV) + "," +
									" originalNote = " + Common.sqlChar(originalNote) + "," +
									" accountingTitle = " + Common.sqlChar(accountingTitle) + "," +
									" bookAmount = " + Common.sqlChar(bookAmount) + "," +
									" bookValue = " + Common.sqlChar(bookValue) + "," +
									" fundsSource = " + Common.sqlChar(fundsSource) + "," +
									" grantValue = " + Common.sqlChar(grantValue) + "," +
									" articleName = " + Common.sqlChar(articleName) + "," +
									" nameplate = " + Common.sqlChar(nameplate) + "," +
									" specification = " + Common.sqlChar(specification) + "," +
									" storeNo = " + Common.sqlChar(storeNo) + "," +
									" sourceKind = " + Common.sqlChar(sourceKind) + "," +
									" sourceDate = " + Common.sqlChar(ul._transToCE_Year(sourceDate)) + "," +
									" sourceDoc = " + Common.sqlChar(sourceDoc) + "," +
									//" picture = " + Common.sqlChar(picture) + "," +
									" notes = " + Common.sqlChar(notes) + "," +
									" fundsSource1 = " + Common.sqlChar(fundsSource1) + "," +
									" editID = " + Common.sqlChar(getEditID()) + "," +
									" editDate = " + Common.sqlChar(ul._transToCE_Year(getEditDate())) + "," +
									" editTime = " + Common.sqlChar(getEditTime()) + "," +
									" differenceKind = " + Common.sqlChar(differenceKind) +
								" where 1=1 " + 
									" and enterOrg = " + Common.sqlChar(enterOrg) +
									" and ownership = " + Common.sqlChar(ownership) +
									" and caseNo = " + Common.sqlChar(caseNo) +
									" and propertyNo = " + Common.sqlChar(propertyNo) +
									" and lotNo = " + Common.sqlChar(lotNo) +
									" and differenceKind = " + Common.sqlChar(differenceKind) +
									"";
	
	Database db=new Database();
	try{
		String[] sourceArray={"UNTNE_DEALDETAIL", "UNTNE_MOVEDETAIL", "UNTNE_REDUCEDETAIL"};
		String sql=null;
		
		for(int i=0;i<sourceArray.length;i++){
			sql="update "+sourceArray[i].toString()+" set"+
						" specification = "+Common.sqlChar(this.getSpecification())+","+
						" nameplate = "+Common.sqlChar(this.getNameplate())+
						" where 1=1 " +
						" and enterorg = " + Common.sqlChar(enterOrg) +
						" and ownership = " + Common.sqlChar(ownership) +
						" and propertyno = " + Common.sqlChar(propertyNo) +
						" and lotno = " + Common.sqlChar(lotNo) +
						" and differencekind = " + Common.sqlChar(differenceKind) ;
			
			//db.querySQL(sql);
			db.excuteSQL(sql);
		}
		
	}catch(Exception e){
		e.printStackTrace();
	}finally{
		db.closeAll();
	}
	
	return execSQLArray;
}


//傳回delete sql
protected String[] getDeleteSQL(){
	String[] execSQLArray = new String[4];
	execSQLArray[0]=" delete UNTNE_Nonexp where 1=1 " +
			" and enterOrg = " + Common.sqlChar(enterOrg) +
			" and ownership = " + Common.sqlChar(ownership) +
			" and caseNo = " + Common.sqlChar(caseNo) +
			" and propertyNo = " + Common.sqlChar(propertyNo) +
			" and lotNo = " + Common.sqlChar(lotNo) +
			" and differenceKind = " + Common.sqlChar(differenceKind) +
			"";
	execSQLArray[1]=" delete UNTNE_NonexpDetail where 1=1 " +
			" and enterOrg = " + Common.sqlChar(enterOrg) +
			" and ownership = " + Common.sqlChar(ownership) +
			" and propertyNo = " + Common.sqlChar(propertyNo) +
			" and lotNo = " + Common.sqlChar(lotNo) +
			" and differenceKind = " + Common.sqlChar(differenceKind) +
			"";
	execSQLArray[2]=" delete UNTNE_Attachment2 where 1=1 " +
			" and enterOrg = " + Common.sqlChar(enterOrg) +
			" and ownership = " + Common.sqlChar(ownership) +
			" and propertyNo = " + Common.sqlChar(propertyNo) +
			" and lotNo = " + Common.sqlChar(lotNo) +
			" and differenceKind = " + Common.sqlChar(differenceKind) +
			"";
	execSQLArray[3]=" delete UNTNE_Attachment1 where 1=1 " +
			" and enterOrg = " + Common.sqlChar(enterOrg) +
			" and ownership = " + Common.sqlChar(ownership) +
			" and propertyNo = " + Common.sqlChar(propertyNo) +
			" and lotNo = " + Common.sqlChar(lotNo) +
			" and differenceKind = " + Common.sqlChar(differenceKind) +
			"";
	//刪除檔案
	/*
	String[] arrFileName=null;
	if (!"".equals(Common.get(picture))) {
		arrFileName=picture.split(":;:");
		if (arrFileName.length>1) Common.RemoveDirectory(new File(filestoreLocation+File.separator+arrFileName[0]));
	}
	*/
	return execSQLArray;
}


/**
 * <br>
 * <br>目的：依主鍵查詢單一資料
 * <br>參數：無
 * <br>傳回：傳回本物件
*/

public UNTNE002F_2  queryOne() throws Exception{
	UNTCH_Tools ul = new UNTCH_Tools();
	Database db = new Database();
	UNTNE002F_2 obj = this;
	try {
		String sql=" select distinct a.enterOrg, b.organSName as enterOrgName, a.ownership, a.caseNo,a.differenceKind,a.caseSerialNo, " +"\n"+
					" a.propertyNo, a.lotNo, a.serialNoS, a.serialNoE,a.otherPropertyUnit, a.otherMaterial, ISNULL(a.otherLimitYear,0) as otherLimitYear, " +"\n"+
					" c.propertyName, c.propertyType, c.propertyUnit, c.material, c.limitYear, " +"\n"+
					" a.propertyName1, a.cause, a.cause1, a.approveDate, a.approveDoc, " +"\n"+
					" (select d.storeName from UNTMP_Store d where 1=1 and a.storeNo=d.storeNo and a.enterOrg=d.enterOrg) as storeName, " +"\n"+
					" a.enterDate, a.buyDate, a.dataState, a.verify, a.propertyKind, " +"\n"+
					" a.fundType, a.valuable, a.originalAmount, a.originalUnit, a.originalBV, " +"\n"+
					" a.originalNote, a.accountingTitle, a.bookAmount, a.bookValue, a.fundsSource, " +"\n"+
					" a.grantValue, a.articleName, a.nameplate, a.specification, a.storeNo, " +"\n"+
					" a.sourceKind, a.sourceDate, a.sourceDoc, a.oldPropertyNo, a.oldSerialNoS, a.oldSerialNoE, " +"\n"+
					" a.notes, a.fundsSource1, a.editID, a.editDate, a.editTime, " +"\n"+
					" (select codename from SYSCA_CODE z where codekindid = 'SKD' and z.codeid = a.sourcekind) as sourceKindName,"+"\n"+
					" (select codename from SYSCA_CODE z where codekindid = 'CAA' and z.codeid = a.cause and codecon1 in (1,4)) as causeName,"+"\n"+
					" d.originalMoveDate, d.originalPlace, d.originalKeepUnit, d.originalKeeper, d.originalUseUnit, d.originalUser "+"\n"+
					" from UNTNE_NONEXP a" +
					" left join SYSCA_ORGAN b on a.enterOrg=b.organID" +
					" left join SYSPK_PROPERTYCODE2 c on a.enterOrg = c.enterOrg and a.propertyNo = c.propertyNo," +
					" UNTNE_NONEXPDETAIL d where 1=1 " +					
					" and a.enterorg = d.enterorg and a.ownership = d.ownership and a.propertyno = d.propertyno and a.lotno = d.lotno and a.differenceKind=d.differenceKind" +"\n"+
					" and a.propertyNo = " + Common.sqlChar(propertyNo) +"\n"+
					" and a.lotNo = " + Common.sqlChar(lotNo) +"\n"+
					" and a.enterOrg = " + Common.sqlChar(enterOrg) +"\n"+
					" and a.ownership = " + Common.sqlChar(ownership) +"\n"+
					" and a.differencekind = " + Common.sqlChar(differenceKind) +"\n"+
					"";
//System.out.println("-- untne002f queryOne --\n"+sql);
		ResultSet rs = db.querySQL(sql);
		if (rs.next()){
			obj.setEnterOrg(rs.getString("enterOrg"));
			obj.setEnterOrgName(rs.getString("enterOrgName"));
			obj.setOwnership(rs.getString("ownership"));
			obj.setCaseNo(rs.getString("caseNo"));
			obj.setPropertyNo(rs.getString("propertyNo"));
			obj.setPropertyNoName(rs.getString("propertyName"));
			obj.setLotNo(rs.getString("lotNo"));
			obj.setSerialNoS(rs.getString("serialNoS"));
			obj.setSerialNoE(rs.getString("serialNoE"));
			obj.setOtherPropertyUnit(rs.getString("otherPropertyUnit"));
			obj.setOtherMaterial(rs.getString("otherMaterial"));
			obj.setOtherLimitYear((Integer.parseInt(rs.getString("otherLimitYear"))==0)?"":rs.getString("otherLimitYear"));
			obj.setPropertyType(rs.getString("propertyType"));
			obj.setPropertyUnit(rs.getString("propertyUnit"));
			obj.setMaterial(rs.getString("material"));
			obj.setLimitYear(rs.getString("limitYear"));			
			obj.setPropertyName1(rs.getString("propertyName1"));
			obj.setCause(rs.getString("cause"));
			obj.setCause1(rs.getString("cause1"));
			obj.setApproveDate(ul._transToROC_Year(rs.getString("approveDate")));
			obj.setApproveDoc(rs.getString("approveDoc"));
			obj.setEnterDate(ul._transToROC_Year(rs.getString("enterDate")));
			obj.setBuyDate(ul._transToROC_Year(rs.getString("buyDate")));
			obj.setDataState(rs.getString("dataState"));
			obj.setVerify(rs.getString("verify"));
			obj.setPropertyKind(rs.getString("propertyKind"));
			obj.setFundType(rs.getString("fundType"));
			obj.setValuable(rs.getString("valuable"));
			obj.setOriginalAmount(rs.getString("originalAmount"));
			obj.setOriginalUnit(rs.getString("originalUnit"));
			obj.setOriginalBV(rs.getString("originalBV"));
			obj.setOriginalNote(rs.getString("originalNote"));
			obj.setAccountingTitle(rs.getString("accountingTitle"));
			obj.setBookAmount(rs.getString("bookAmount"));
			obj.setBookValue(rs.getString("bookValue"));
			obj.setFundsSource(rs.getString("fundsSource"));
			obj.setGrantValue(rs.getString("grantValue"));
			obj.setArticleName(rs.getString("articleName"));
			obj.setNameplate(rs.getString("nameplate"));
			obj.setSpecification(rs.getString("specification"));
			obj.setStoreNo(rs.getString("storeNo"));
			obj.setStoreNoName(rs.getString("storeName"));
			obj.setSourceKind(rs.getString("sourceKind"));
			obj.setSourceDate(ul._transToROC_Year(rs.getString("sourceDate")));
			obj.setSourceDoc(rs.getString("sourceDoc"));
			obj.setOldPropertyNo(rs.getString("oldPropertyNo"));
			obj.setOldSerialNoS(rs.getString("oldSerialNoS"));
			obj.setOldSerialNoE(rs.getString("oldSerialNoE"));
			obj.setNotes(rs.getString("notes"));
			obj.setFundsSource1(rs.getString("fundsSource1"));
			obj.setEditID(rs.getString("editID"));
			obj.setEditDate(ul._transToROC_Year(rs.getString("editDate")));
			obj.setEditTime(rs.getString("editTime"));
			
			obj.setOriginalMoveDate(ul._transToROC_Year(rs.getString("originalMoveDate")));
			obj.setOriginalPlace(rs.getString("originalPlace"));
			obj.setOriginalKeepUnit(rs.getString("originalKeepUnit"));
			obj.setOriginalKeeper(rs.getString("originalKeeper"));
			obj.setOriginalUseUnit(rs.getString("originalUseUnit"));
			obj.setOriginalUser(rs.getString("originalUser"));
			obj.setDifferenceKind(rs.getString("differenceKind"));
			obj.setCaseSerialNo(rs.getString("caseSerialNo"));
			obj.setSourceKindName(rs.getString("sourceKindName"));
			obj.setCauseName(rs.getString("CauseName"));
			
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
	UNTCH_Tools ul = new UNTCH_Tools();
	UNTNE002F_2 obj = this;
	Database db = new Database();
	ArrayList objList=new ArrayList();
	int counter=0;
	Map<String,String> differencekindMap = TCGHCommon.getSysca_Code("DFK"); 					//財產區分別名稱
	try {
		String sql=" select ROW_NUMBER() over ( order by datastate, propertyno, serialnos  ) as rownum, * from ( " + 
	                " select distinct b.organSName,a.enterOrg, a.ownership,a.differencekind,a.caseSerialNo,a.caseNo," +
					" (select z.codeName from sysca_code z where 1=1 and (z.codeKindID='owa' or z.codeKindID='OWA') and a.ownership=z.codeID) as ownershipName, " +
					" a.propertyNo, a.lotNo, c.codeName as cause, " +
					" (select x.propertyName from SYSPK_PropertyCode2 x where x.propertyno = a.propertyno and x.enterorg = a.enterorg) as propertyName, "+
					" (case a.dataState when '1' then '現存' when '2' then '已減損' else '' end) dataStateName, a.dataState, "+
					" (case a.propertyKind when '01' then '公務用' when '02' then '公共用' when '03' then '事業用' when '04' then '非公用' else '' end) propertyKind," +
					" a.bookAmount, a.bookValue, "+
					" a.serialNoS, a.serialNoE "+
					" from UNTNE_Nonexp a "+
					" left join UNTNE_ADDPROOF d on a.caseNo=d.caseNo and a.enterOrg=d.enterOrg" +
					" left join SYSCA_CODE c on a.cause = c.codeid  and c.codekindid='CAA'" +
					" left join SYSCA_ORGAN b on   a.enterorg = b.organid "+
					" left join UNTNE_NONEXPDETAIL f on a.enterOrg=f.enterOrg and a.ownership=f.ownership and a.propertyNo=f.propertyNo and a.lotNo=f.lotNo and a.serialNoS<=f.serialNo and a.serialNoE>=f.serialNo and a.differenceKind=f.differenceKind"+//" , SYSPK_PropertyCode2 e "+
//					" where 1=1 and a.verify='Y' " ;
					" where 1=1 " ;
					//" and a.propertyNo = e.propertyNo and a.enterOrg = e.enterOrg" +
//		if (!"".equals(getEnterOrg())) {
//			sql += " and a.enterOrg = " + Common.sqlChar(getEnterOrg());
//		}
//		if (!"".equals(getOwnership())) {
//			sql += " and a.ownership = " + Common.sqlChar(getOwnership());
//		}
//		if (!"".equals(getCaseNo())) {
//			sql += " and a.caseNo = " + Common.sqlChar(getCaseNo());
//		}
//		if (!"".equals(getDifferenceKind())) {
//			sql += " and a.differenceKind = " + Common.sqlChar(getDifferenceKind());
//		}
				
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
					if (!"".equals(getQ_caseNoS()))
						sql+=" and a.caseNo >= " + Common.sqlChar(Common.formatRearString(getQ_caseNoS(),10,'0'));		
					if (!"".equals(getQ_caseNoE()))
						sql+=" and a.caseNo<=" + Common.sqlChar(Common.formatRearString(getQ_caseNoE(),10, '9'));
					if (!"".equals(getQ_ownership()))
						sql+=" and a.ownership = " + Common.sqlChar(getQ_ownership()) ;  	    
					if (!"".equals(getQ_enterDateS()))
						sql+=" and a.enterDate >= " + Common.sqlChar(ul._transToCE_Year(getQ_enterDateS()));
					if (!"".equals(getQ_enterDateE()))
						sql+=" and a.enterDate <= " + Common.sqlChar(ul._transToCE_Year(getQ_enterDateE()));
					if (!"".equals(getQ_proofDoc()))
						sql+=" and d.proofDoc like '%" + getQ_proofDoc() + "%'" ;
					if (!"".equals(getQ_proofNoS())) 
						sql+=" and d.proofNo >= " + Common.sqlChar(getQ_proofNoS());		
					if (!"".equals(getQ_proofNoE())) 
						sql+=" and d.proofNo <= " + Common.sqlChar(getQ_proofNoE());		 
					if (!"".equals(getQ_writeDateS()))
						sql+=" and d.writeDate >= " + Common.sqlChar(ul._transToCE_Year(getQ_writeDateS()));		
					if (!"".equals(getQ_writeDateE()))
						sql+=" and d.writeDate<=" + Common.sqlChar(ul._transToCE_Year(getQ_writeDateE()));   	    
					if (!"".equals(getQ_verify()))
						sql+=" and a.verify  = " + Common.sqlChar(getQ_verify()) ;
					if (!"".equals(getQ_cause()))
						sql+=" and a.cause = " + Common.sqlChar(getQ_cause()) ;			
					if (!"".equals(getQ_propertyNoS()))
						sql+=" and a.propertyNo >= " + Common.sqlChar(getQ_propertyNoS());		
					if (!"".equals(getQ_propertyNoE()))
						sql+=" and a.propertyNo<=" + Common.sqlChar(getQ_propertyNoE());						
					if (!"".equals(getQ_lotNo()))
						sql+=" and a.lotNo = " + Common.sqlChar(Common.formatFrontZero(getQ_lotNo(),7)) ;		
					if (!"".equals(getQ_serialNoS()))
						sql+=" and f.serialNo >= " + Common.sqlChar(getQ_serialNoS());		
					if (!"".equals(getQ_serialNoE()))
						sql+=" and f.serialNo <=" + Common.sqlChar(getQ_serialNoE());			
					if (!"".equals(getQ_dataState()))
						sql+=" and a.dataState = " + Common.sqlChar(getQ_dataState()) ;
					if (!"".equals(getQ_propertyKind()))
						sql+=" and a.propertyKind = " + Common.sqlChar(getQ_propertyKind()) ;
					if (!"".equals(getQ_fundType()))
						sql+=" and a.fundType = " + Common.sqlChar(getQ_fundType()) ;
					if (!"".equals(getQ_valuable()))
						sql+=" and a.valuable = " + Common.sqlChar(getQ_valuable()) ;	    
					if (!"".equals(getQ_keepUnit()))
						sql+=" and f.keepUnit = " + Common.sqlChar(getQ_keepUnit()) ;	    
					if (!"".equals(getQ_keeper()))
						sql+=" and f.keeper = " + Common.sqlChar(getQ_keeper()) ;	    
					if (!"".equals(getQ_useUnit()))
						sql+=" and f.useUnit = " + Common.sqlChar(getQ_useUnit()) ;	    
					if (!"".equals(getQ_userNo()))
						sql+=" and f.userNo = " + Common.sqlChar(getQ_userNo()) ;	 
					if (!"".equals(getQ_buyDateS()))
						sql+=" and a.buyDate >= " + Common.sqlChar(ul._transToCE_Year(getQ_buyDateS()));
					if (!"".equals(getQ_buyDateE()))
						sql+=" and a.buyDate <= " + Common.sqlChar(ul._transToCE_Year(getQ_buyDateE()));	
					if (!"".equals(getQ_propertyName1()))
						sql+=" and a.propertyName1 like " + Common.sqlChar("%"+getQ_propertyName1()+"%") ;
					if (!"".equals(getQ_detail_propertyName1()))
						sql+=" and f.propertyName1 like " + Common.sqlChar("%"+getQ_detail_propertyName1()+"%") ;
					if (!"".equals(getQ_oldSerialNoS()))
						sql+=" and f.oldserialno >= " + Common.sqlChar(Common.formatFrontZero(getQ_oldSerialNoS(),7));		
					if (!"".equals(getQ_oldSerialNoE()))
						sql+=" and f.oldserialno <=" + Common.sqlChar(Common.formatFrontZero(getQ_oldSerialNoE(),7));
					if (!"".equals(getQ_oldlotNoS())){
						sql += " and f.oldlotNo >= " + Common.sqlChar(getQ_oldlotNoS());
					}
					if (!"".equals(getQ_oldlotNoE())){
						sql += " and f.oldlotNo <= " + Common.sqlChar(getQ_oldlotNoE());	
					}
					if (!"".equals(getQ_differenceKind()))
						sql+=" and a.differencekind = " + Common.sqlChar(getQ_differenceKind()) ;
					if(!"".equals(getQ_userNote()))
						sql+=" and f.userNote like " + Common.sqlChar("%" + getQ_userNote() + "%");
					if(!"".equals(getQ_place()))
						sql+=" and f.place like " + Common.sqlChar("%" + getQ_place() + "%");
					if(!"".equals(getQ_place1()))
						sql+=" and f.place1 like " + Common.sqlChar("%" + getQ_place1() + "%");
					if (!"".equals(getQ_proofYear()))
						sql+=" and d.proofyear = " + Common.sqlChar(getQ_proofYear()) ;
					
					if ("1".equals(this.getRoleid())){
						
						sql += " and f.keeper = " + Common.sqlChar(this.getKeeperno());
						
					}else if ("2".equals(this.getRoleid())){
						
						sql += " and f.keepunit = " + Common.sqlChar(this.getUnitID());
											
					}else if ("3".equals(this.getRoleid())){		
						
					}
					
					if (!"".equals(this.getQ_specification())) {
						sql += " and a.specification like '%" + this.getQ_specification() + "%' ";
					}
					
					if (!"".equals(this.getQ_nameplate())) {
						sql += " and a.nameplate like '%" + this.getQ_nameplate() + "%' ";
					}
					sql += " ) as wwerr ";
//System.out.println("-- untne002f queryAll -- "+sql);	
		ResultSet rs = db.querySQL(sql.toString(),true);
		processCurrentPageAttribute(rs);			
		if (getTotalRecord() > 0) {
		int count = getRecordStart();
		int end = getRecordEnd();
		do {
			if (count > end)
			break;
			String rowArray[]=new String[20];
			rowArray[0] = rs.getString("rownum");
			rowArray[1]=differencekindMap.get(rs.getString("differencekind"));
			rowArray[2]=rs.getString("caseSerialNo");
			rowArray[3]=rs.getString("propertyNo");
			rowArray[4]=rs.getString("serialNoS")+"-"+rs.getString("serialNoE");
			rowArray[5]=rs.getString("propertyName");
			rowArray[6]=rs.getString("cause"); 
			rowArray[7]=rs.getString("dataStateName"); 
			rowArray[8]=rs.getString("propertyKind"); 
			rowArray[9]=rs.getString("bookAmount");
			rowArray[10]=rs.getString("bookValue");
			rowArray[11]=rs.getString("organSName"); 		
			rowArray[12]=rs.getString("ownershipName");
			rowArray[13]=rs.getString("lotNo");
			rowArray[14]=rs.getString("enterOrg"); 		
			rowArray[15]=rs.getString("ownership"); 
			rowArray[16]=(rs.getString("differencekind"));
			rowArray[17]=(rs.getString("caseNo"));
			rowArray[18]=(rs.getString("serialNoS"));
			rowArray[19]=(rs.getString("serialNoE"));
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