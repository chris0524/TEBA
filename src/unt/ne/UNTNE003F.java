/*
*<br>程式目的：非消耗品主檔資料維護－批號明細
*<br>程式代號：untne003f
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
import util.Datetime;
import util.TCGHCommon;

public class UNTNE003F extends UNTNE001Q{


String enterOrg;
String enterOrgName;
String ownership;
String propertyNo;
String propertyNoName;
String lotNo;
String serialNo;
String propertyUnit;
String material;
String limitYear;
String dataState;
String reduceDate;
String reduceCause;
String reduceCause1;
String verify;
String bookAmount;
String bookValue;
String licensePlate;
String purpose;
String originalMoveDate;
String originalKeepUnit;
String originalKeeper;
String originalUseUnit;
String originalUser;
String originalPlace;
String moveDate;
String keepUnit;
String keeper;
String useUnit;
String userNo;
String place;
String notes1;
String notes;
String oldPropertyNo;
String oldSerialNo;
String otherPropertyUnit;
String otherMaterial;
String otherLimitYear;

String caseNo;
String initDtl;
String serialNoS;
String serialNoE;
String buyDate;

String setMoveDate;
String setKeepUnit;
String setKeeper;
String setUseUnit;
String setUser;
String setPlace;
String checkEnterOrg;
String enterDate;
String place1Name;
String place1;
String userNote;
String originalUserNote;
String originalPlace1;
String originalPlace1Name;
String differenceKind;


public String getSetMoveDate(){ return checkGet(setMoveDate); }
public void setSetMoveDate(String s){ setMoveDate=checkSet(s); }
public String getSetKeepUnit(){ return checkGet(setKeepUnit); }
public void setSetKeepUnit(String s){ setKeepUnit=checkSet(s); }
public String getSetKeeper(){ return checkGet(setKeeper); }
public void setSetKeeper(String s){ setKeeper=checkSet(s); }
public String getSetUseUnit(){ return checkGet(setUseUnit); }
public void setSetUseUnit(String s){ setUseUnit=checkSet(s); }
public String getSetUser(){ return checkGet(setUser); }
public void setSetUser(String s){ setUser=checkSet(s); }
public String getSetPlace(){ return checkGet(setPlace); }
public void setSetPlace(String s){ setPlace=checkSet(s); }
public String getCheckEnterOrg(){ return checkGet(checkEnterOrg); }
public void setCheckEnterOrg(String s){ checkEnterOrg=checkSet(s); }

public String getCaseNo(){ return checkGet(caseNo); }
public void setCaseNo(String s){ caseNo=checkSet(s); }
public String getInitDtl(){ return checkGet(initDtl); }
public void setInitDtl(String s){ initDtl=checkSet(s); }
public String getSerialNoS(){ return checkGet(serialNoS); }
public void setSerialNoS(String s){ serialNoS=checkSet(s); }
public String getSerialNoE(){ return checkGet(serialNoE); }
public void setSerialNoE(String s){ serialNoE=checkSet(s); }
public String getBuyDate(){ return checkGet(buyDate); }
public void setBuyDate(String s){ buyDate=checkSet(s); }

public String getEnterOrg(){ return checkGet(enterOrg); }
public void setEnterOrg(String s){ enterOrg=checkSet(s); }
public String getEnterOrgName(){ return checkGet(enterOrgName); }
public void setEnterOrgName(String s){ enterOrgName=checkSet(s); }
public String getOwnership(){ return checkGet(ownership); }
public void setOwnership(String s){ ownership=checkSet(s); }
public String getPropertyNo(){ return checkGet(propertyNo); }
public void setPropertyNo(String s){ propertyNo=checkSet(s); }
public String getPropertyNoName(){ return checkGet(propertyNoName); }
public void setPropertyNoName(String s){ propertyNoName=checkSet(s); }
public String getLotNo(){ return checkGet(lotNo); }
public void setLotNo(String s){ lotNo=checkSet(s); }
public String getSerialNo(){ return checkGet(serialNo); }
public void setSerialNo(String s){ serialNo=checkSet(s); }
public String getPropertyUnit(){ return checkGet(propertyUnit); }
public void setPropertyUnit(String s){ propertyUnit=checkSet(s); }
public String getMaterial(){ return checkGet(material); }
public void setMaterial(String s){ material=checkSet(s); }
public String getLimitYear(){ return checkGet(limitYear); }
public void setLimitYear(String s){ limitYear=checkSet(s); }
public String getDataState(){ return checkGet(dataState); }
public void setDataState(String s){ dataState=checkSet(s); }
public String getReduceDate(){ return checkGet(reduceDate); }
public void setReduceDate(String s){ reduceDate=checkSet(s); }
public String getReduceCause(){ return checkGet(reduceCause); }
public void setReduceCause(String s){ reduceCause=checkSet(s); }
public String getReduceCause1(){ return checkGet(reduceCause1); }
public void setReduceCause1(String s){ reduceCause1=checkSet(s); }
public String getVerify(){ return checkGet(verify); }
public void setVerify(String s){ verify=checkSet(s); }
public String getBookAmount(){ return checkGet(bookAmount); }
public void setBookAmount(String s){ bookAmount=checkSet(s); }
public String getBookValue(){ return checkGet(bookValue); }
public void setBookValue(String s){ bookValue=checkSet(s); }
public String getLicensePlate(){ return checkGet(licensePlate); }
public void setLicensePlate(String s){ licensePlate=checkSet(s); }
public String getPurpose(){ return checkGet(purpose); }
public void setPurpose(String s){ purpose=checkSet(s); }
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
public String getMoveDate(){ return checkGet(moveDate); }
public void setMoveDate(String s){ moveDate=checkSet(s); }
public String getKeepUnit(){ return checkGet(keepUnit); }
public void setKeepUnit(String s){ keepUnit=checkSet(s); }
public String getKeeper(){ return checkGet(keeper); }
public void setKeeper(String s){ keeper=checkSet(s); }
public String getUseUnit(){ return checkGet(useUnit); }
public void setUseUnit(String s){ useUnit=checkSet(s); }
public String getUserNo(){ return checkGet(userNo); }
public void setUserNo(String s){ userNo=checkSet(s); }
public String getPlace(){ return checkGet(place); }
public void setPlace(String s){ place=checkSet(s); }
public String getNotes1(){ return checkGet(notes1); }
public void setNotes1(String s){ notes1=checkSet(s); }
public String getNotes(){ return checkGet(notes); }
public void setNotes(String s){ notes=checkSet(s); }
public String getOldPropertyNo(){ return checkGet(oldPropertyNo); }
public void setOldPropertyNo(String s){ oldPropertyNo=checkSet(s); }
public String getOldSerialNo(){ return checkGet(oldSerialNo); }
public void setOldSerialNo(String s){ oldSerialNo=checkSet(s); }
public String getOtherPropertyUnit(){ return checkGet(otherPropertyUnit); }
public void setOtherPropertyUnit(String s){ otherPropertyUnit=checkSet(s); }
public String getOtherMaterial(){ return checkGet(otherMaterial); }
public void setOtherMaterial(String s){ otherMaterial=checkSet(s); }
public String getOtherLimitYear(){ return checkGet(otherLimitYear); }
public void setOtherLimitYear(String s){ otherLimitYear=checkSet(s); }
public String getEnterDate(){ return checkGet(enterDate); }
public void setEnterDate(String s){ enterDate=checkSet(s); }
public String getPlace1Name() {return checkGet(place1Name);}
public void setPlace1Name(String s) {this.place1Name = checkSet(s);}
public String getPlace1() {return checkGet(place1);}
public void setPlace1(String s) {this.place1 = checkSet(s);}
public String getUserNote() {return checkGet(userNote);}
public void setUserNote(String s) {this.userNote = checkSet(s);}
public String getOriginalUserNote() {return checkGet(originalUserNote);}
public void setOriginalUserNote(String s) {this.originalUserNote = checkSet(s);}
public String getOriginalPlace1() {return checkGet(originalPlace1);}
public void setOriginalPlace1(String s) {this.originalPlace1 = checkSet(s);}
public String getOriginalPlace1Name() {return checkGet(originalPlace1Name);}
public void setOriginalPlace1Name(String s) {this.originalPlace1Name = checkSet(s);}
public String getDifferenceKind() {return checkGet(this.differenceKind);}
public void setDifferenceKind(String s) {differenceKind = checkSet(s);}


String batchInsertFlag;

public String getBatchInsertFlag(){ return checkGet(batchInsertFlag); }
public void setBatchInsertFlag(String s){ batchInsertFlag=checkSet(s); }

String originalAmount;
String originalBV;
public String getOriginalAmount(){ return checkGet(originalAmount); }
public void setOriginalAmount(String s){ originalAmount=checkSet(s); }
public String getOriginalBV(){ return checkGet(originalBV); }
public void setOriginalBV(String s){ originalBV=checkSet(s); }

//2007/10/31
String barCode;

public String getBarCode(){ return checkGet(barCode); }
public void setBarCode(String s){ barCode=checkSet(s); }

String originalUseBureau;
public String getOriginalUseBureau(){ return checkGet(originalUseBureau); }
public void setOriginalUseBureau(String s){ originalUseBureau=checkSet(s); }

String originalKeepBureau;
public String getOriginalKeepBureau(){ return checkGet(originalKeepBureau); }
public void setOriginalKeepBureau(String s){ originalKeepBureau=checkSet(s); }

private String propertyName1;
public String getPropertyName1() {return checkGet(propertyName1);}
public void setPropertyName1(String propertyName1) {this.propertyName1 = checkSet(propertyName1);}


//傳回update sql
protected String[] getUpdateSQL(){
	UNTCH_Tools ul = new UNTCH_Tools();
	String[] execSQLArray = new String[1];
	execSQLArray[0]=" update UNTNE_NonexpDetail set " +
			" lotNo = " + Common.sqlChar(this.getLotNo()) + "," +
			" dataState = " + Common.sqlChar(this.getDataState()) + "," +
			" reduceDate = " + Common.sqlChar(ul._transToCE_Year(this.getReduceDate())) + "," +
			" reduceCause = " + Common.sqlChar(this.getReduceCause()) + "," +
			" reduceCause1 = " + Common.sqlChar(this.getReduceCause1()) + "," +
			" verify = " + Common.sqlChar(this.getVerify()) + "," +
			" bookAmount = " + Common.sqlChar(this.getBookAmount()) + "," +
			" bookValue = " + Common.sqlChar(this.getBookValue()) + "," +
			" licensePlate = " + Common.sqlChar(this.getLicensePlate()) + "," +
			" purpose = " + Common.sqlChar(this.getPurpose()) + "," +
			" originalMoveDate = " + Common.sqlChar(this.getOriginalMoveDate()) + "," +
			" originalKeepUnit = " + Common.sqlChar(this.getOriginalKeepUnit()) + "," +
			" originalKeeper = " + Common.sqlChar(this.getOriginalKeeper()) + "," +
			" originalUseUnit = " + Common.sqlChar(this.getOriginalUseUnit()) + "," +
			" originalUser = " + Common.sqlChar(this.getOriginalUser()) + "," +
			" originalPlace = " + Common.sqlChar(this.getOriginalPlace()) + "," +
			" moveDate = " + Common.sqlChar(this.getMoveDate()) + "," +
			" keepUnit = " + Common.sqlChar(this.getKeepUnit()) + "," +
			" keeper = " + Common.sqlChar(this.getKeeper()) + "," +
			" useUnit = " + Common.sqlChar(this.getUseUnit()) + "," +
			" userNo = " + Common.sqlChar(this.getUserNo()) + "," +
			" place = " + Common.sqlChar(this.getPlace()) + "," +
			" notes1 = " + Common.sqlChar(this.getNotes1()) + "," +
			" notes = " + Common.sqlChar(this.getNotes()) + "," +
			" oldPropertyNo = " + Common.sqlChar(this.getOldPropertyNo()) + "," +
			" oldSerialNo = " + Common.sqlChar(this.getOldSerialNo()) + "," +
			" editID = " + Common.sqlChar(getEditID()) + "," +
			" editDate = " + Common.sqlChar(ul._transToCE_Year(getEditDate())) + "," +
			" editTime = " + Common.sqlChar(getEditTime()) + "," +
			" differenceKind = " + Common.sqlChar(this.getDifferenceKind())+"," +
			" originalUserNote = " + Common.sqlChar(this.getOriginalUserNote())+"," +
			" originalPlace1 = " + Common.sqlChar(this.getOriginalPlace1())+"," +
			" place1 = " + Common.sqlChar(this.getPlace1())+"," +
			" barCode = "+ Common.sqlChar(this.getBarCode()) + "," +
			" propertyname1 = "+ Common.sqlChar(this.getPropertyName1()) +
		" where 1=1 " + 
			" and enterOrg = " + Common.sqlChar(enterOrg) +
			" and ownership = " + Common.sqlChar(ownership) +
			" and propertyNo = " + Common.sqlChar(propertyNo) +
			" and serialNo = " + Common.sqlChar(serialNo) +
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

public UNTNE003F  queryOne() throws Exception{
	UNTCH_Tools ul = new UNTCH_Tools();
	Database db = new Database();
	 Map<String,String> placeMap = TCGHCommon.getSysca_PlaceCode(this.getQ_enterOrg());			//存置地點名稱
	UNTNE003F obj = this;
	try {
		String sql=" select a.enterOrg, b.organSName as enterOrgName, a.ownership, a.propertyNo,a.differenceKind, " +
					" a.lotNo, a.serialNo, a.dataState,a.enterDate, " +
					" c.propertyName, c.propertyUnit, c.material, c.limitYear, " +
					" a.reduceDate, a.reduceCause, a.reduceCause1, a.verify, " +
					" a.bookAmount, a.bookValue, a.licensePlate, a.purpose, a.originalMoveDate, " +
					" a.originalKeepUnit, a.originalKeeper, a.originalUseUnit, a.originalUser,a.originalUserNote,a.originalPlace1, " +
					" a.originalPlace, a.moveDate, a.keepUnit, a.keeper, a.useUnit, a.userNo,a.place1, " +
					" a.place,a.usernote, " +
					" a.notes1, a.notes, a.oldPropertyNo, a.oldSerialNo, a.editID, a.editDate, " +
					" a.editTime, d.caseNo, d.otherPropertyUnit, d.otherMaterial, ISNULL(d.otherLimitYear,0) as otherLimitYear,d.originallimityear, " +
					" a.originalAmount, a.originalBV, d.buydate"+
					" ,a.barcode, a.propertyname1 " +
					" from UNTNE_NonexpDetail a, SYSCA_Organ b, SYSPK_PropertyCode2 c, UNTNE_Nonexp d where 1=1 " +
					" and a.enterOrg=b.organID " +
					" and a.enterOrg = c.enterOrg " +
					" and a.propertyNo=c.propertyNo" +
					" and a.lotNo=d.lotNo and a.enterOrg=d.enterOrg and a.ownership=d.ownership and a.propertyNo=d.propertyNo " +
					" and a.enterOrg = " + Common.sqlChar(enterOrg) +
					" and a.ownership = " + Common.sqlChar(ownership) +
					" and a.propertyNo = " + Common.sqlChar(propertyNo) +
					" and a.lotNo = " + Common.sqlChar(lotNo) +
					" and a.serialNo = " + Common.sqlChar(serialNo) +
					" and a.differenceKind = " + Common.sqlChar(differenceKind) +
					"";
		//System.out.println("============ "+sql);
		ResultSet rs = db.querySQL(sql);
		
		if (rs.next()){
			obj.setEnterOrg(rs.getString("enterOrg"));
			obj.setEnterOrgName(rs.getString("enterOrgName"));
			obj.setOwnership(rs.getString("ownership"));
			obj.setPropertyNo(rs.getString("propertyNo"));
			obj.setPropertyNoName(rs.getString("propertyName"));
			obj.setLotNo(rs.getString("lotNo"));
			obj.setSerialNo(rs.getString("serialNo"));
			obj.setPropertyUnit(rs.getString("propertyUnit"));
			obj.setMaterial(rs.getString("material"));
			obj.setLimitYear(rs.getString("originallimityear"));
			obj.setOtherPropertyUnit(rs.getString("otherPropertyUnit"));
			obj.setOtherMaterial(rs.getString("otherMaterial"));
			obj.setOtherLimitYear((Integer.parseInt(rs.getString("otherLimitYear"))==0)?"":rs.getString("otherLimitYear"));
			obj.setDataState(rs.getString("dataState"));
			obj.setCheckDataState(rs.getString("dataState"));
			obj.setReduceDate(ul._transToROC_Year(rs.getString("reduceDate")));
			obj.setReduceCause(rs.getString("reduceCause"));
			obj.setReduceCause1(rs.getString("reduceCause1"));
			obj.setVerify(rs.getString("verify"));
			obj.setBookAmount(rs.getString("bookAmount"));
			obj.setBookValue(rs.getString("bookValue"));
			obj.setLicensePlate(rs.getString("licensePlate"));
			obj.setPurpose(rs.getString("purpose"));
			obj.setOriginalMoveDate(ul._transToROC_Year(rs.getString("originalMoveDate")));
			obj.setOriginalKeepUnit(rs.getString("originalKeepUnit"));
			obj.setOriginalKeeper(rs.getString("originalKeeper"));
			obj.setOriginalUseUnit(rs.getString("originalUseUnit"));
			obj.setOriginalUser(rs.getString("originalUser"));
			obj.setOriginalPlace(rs.getString("originalPlace"));
			obj.setMoveDate(ul._transToROC_Year(rs.getString("moveDate")));
			obj.setKeepUnit(rs.getString("keepUnit"));
			obj.setKeeper(rs.getString("keeper"));
			obj.setUseUnit(rs.getString("useUnit"));
			obj.setUserNo(rs.getString("userNo"));
			obj.setPlace(rs.getString("place"));
			obj.setNotes1(rs.getString("notes1"));
			obj.setNotes(rs.getString("notes"));
			obj.setOldPropertyNo(rs.getString("oldPropertyNo"));
			obj.setOldSerialNo(rs.getString("oldSerialNo"));
			obj.setEditID(rs.getString("editID"));
			obj.setEditDate(ul._transToROC_Year(rs.getString("editDate")));
			obj.setEditTime(rs.getString("editTime"));
			obj.setCaseNo(rs.getString("caseNo"));
			obj.setOriginalAmount(rs.getString("originalAmount"));
			obj.setOriginalBV(rs.getString("originalBV"));
			obj.setBarCode(rs.getString("barCode"));
			obj.setDifferenceKind(rs.getString("differenceKind"));
			obj.setOriginalUserNote(rs.getString("originalUserNote"));
			obj.setOriginalPlace1(rs.getString("originalPlace1"));
			obj.setPlace1(rs.getString("place1"));
			obj.setEnterDate(ul._transToROC_Year(rs.getString("enterDate")));
			obj.setOriginalPlace1Name(ul.getPlace1Name(rs.getString("enterorg"),(rs.getString("originalPlace1"))));
			obj.setPlace1Name(ul.getPlace1Name(rs.getString("enterorg"),(rs.getString("Place1"))));
			obj.setUserNote(rs.getString("usernote"));			
			obj.setPropertyName1(rs.getString("propertyname1"));
			obj.setBuyDate(ul._transToROC_Year(rs.getString("buydate")));
		}else{
			obj.setSerialNo("");				
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
	 Map<String,String> placeMap = TCGHCommon.getSysca_PlaceCode(this.getQ_enterOrg());			//存置地點名稱
	UNTCH_Tools ul = new UNTCH_Tools();
	Database db = new Database();
	ArrayList objList=new ArrayList();
	int counter=0;
	Map<String,String> differencekindMap = TCGHCommon.getSysca_Code("DFK"); 					//財產區分別名稱
	try {
		String sql= " select a.enterOrg, b.organSName, a.ownership, a.propertyNo,a.differencekind,a.caseNo, " +"\n"+
					" (select x.propertyName from SYSPK_PropertyCode2 x where x.propertyno = a.propertyno and x.enterorg = a.enterorg) as propertyName, "+"\n"+
					" a.lotNo, a.serialNo, a.bookValue," +"\n"+
					" (select z.codeName from sysca_code z where 1=1 and (z.codeKindID='owa' or z.codeKindID='OWA') and a.ownership=z.codeID) as ownershipName, " +"\n"+
					" (case a.dataState when '1' then '現存' when '2' then '已減損' else '' end) dataState, a.verify, a.place,a.place1, " +"\n"+
					" (select x.unitName from UNTMP_KeepUnit x where a.enterOrg=x.enterOrg and a.keepunit=x.unitno) as keepUnitName, " +"\n"+
					" (select y.keeperName from UNTMP_Keeper y where a.enterOrg=y.enterOrg  and a.keeper=y.keeperNo) as keeperName " +"\n"+
					" from UNTNE_NonexpDetail a, SYSCA_Organ b, UNTNE_AddProof d, UNTNE_Nonexp e " +"\n"+
					" where a.enterOrg=d.enterOrg and a.caseNo=d.caseNo " +"\n"+
					" and a.ownership=e.ownership and a.enterOrg=e.enterOrg and e.propertyNo=a.propertyNo and e.lotNo=a.lotNo and e.differencekind=a.differencekind " +"\n"+
					" and a.enterOrg = b.organID and d.caseNo=e.caseNo ";
		if (!"".equals(getEnterOrg())) {
				sql +=" and a.enterOrg = " + Common.sqlChar(enterOrg);
		}
		if (!"".equals(getOwnership())) {
				sql +=" and a.ownership = " + Common.sqlChar(ownership);
		}
		if (!"".equals(getPropertyNo())) {
				sql +=" and a.propertyNo = " + Common.sqlChar(propertyNo);
		}
		if (!"".equals(getLotNo())) {
				sql +=" and a.lotNo = " + Common.sqlChar(lotNo);
		}
		if (!"".equals(getDifferenceKind())) {
				sql +=" and a.differencekind = " + Common.sqlChar(differenceKind);
		}
			if (!"".equals(getQ_enterOrg())) 
				sql+=" and a.enterOrg = " + Common.sqlChar(getQ_enterOrg()) ;
			if (!"".equals(getQ_caseNoS()))
				sql+=" and d.caseNo >= " + Common.sqlChar(Common.formatRearString(getQ_caseNoS(),10,'0'));		
			if (!"".equals(getQ_caseNoE()))
				sql+=" and d.caseNo<=" + Common.sqlChar(Common.formatRearString(getQ_caseNoE(),10, '9'));
			if (!"".equals(getQ_ownership()))
				sql+=" and a.ownership = " + Common.sqlChar(getQ_ownership()) ;  	    
			if (!"".equals(getQ_enterDateS()))
				sql+=" and e.enterDate >= " + Common.sqlChar(ul._transToCE_Year(getQ_enterDateS()));
			if (!"".equals(getQ_enterDateE()))
				sql+=" and e.enterDate <= " + Common.sqlChar(ul._transToCE_Year(getQ_enterDateE()));
			if (!"".equals(getQ_proofDoc()))
				sql+=" and d.proofDoc like '%" + getQ_proofDoc() + "%'" ;
			if (!"".equals(getQ_proofNoS())) 
				sql+=" and d.proofNo >= " + Common.sqlChar(getQ_proofNoS());		
			if (!"".equals(getQ_proofNoE())) 
				sql+=" and d.proofNo <= " + Common.sqlChar(getQ_proofNoE());		 
//			if (!"".equals(getQ_writeDateS()))
//				sql+=" and d.writeDate >= " + Common.sqlChar(getQ_writeDateS());		
//			if (!"".equals(getQ_writeDateE()))
//				sql+=" and d.writeDate<=" + Common.sqlChar(getQ_writeDateE());   	    
			if (!"".equals(getQ_verify()))
				sql+=" and a.verify  = " + Common.sqlChar(getQ_verify()) ;
			if (!"".equals(getQ_cause()))
				sql+=" and e.cause = " + Common.sqlChar(getQ_cause()) ;			
			if (!"".equals(getQ_propertyNoS()))
				sql+=" and a.propertyNo >= " + Common.sqlChar(getQ_propertyNoS());		
			if (!"".equals(getQ_propertyNoE()))
				sql+=" and a.propertyNo<=" + Common.sqlChar(getQ_propertyNoE());						
			if (!"".equals(getQ_lotNo()))
				sql+=" and a.lotNo = " + Common.sqlChar(Common.formatFrontZero(getQ_lotNo(),7)) ;		
			if (!"".equals(getQ_serialNoS()))
				sql+=" and a.serialNo >= " + Common.sqlChar(getQ_serialNoS());		
			if (!"".equals(getQ_serialNoE()))
				sql+=" and a.serialNo <=" + Common.sqlChar(getQ_serialNoE());			
			if (!"".equals(getQ_dataState()))
				sql+=" and a.dataState = " + Common.sqlChar(getQ_dataState()) ;
			if (!"".equals(getQ_propertyKind()))
				sql+=" and e.propertyKind = " + Common.sqlChar(getQ_propertyKind()) ;
			if (!"".equals(getQ_fundType()))
				sql+=" and e.fundType = " + Common.sqlChar(getQ_fundType()) ;
			if (!"".equals(getQ_valuable()))
				sql+=" and e.valuable = " + Common.sqlChar(getQ_valuable()) ;	    
			if (!"".equals(getQ_keepUnit()))
				sql+=" and a.keepUnit = " + Common.sqlChar(getQ_keepUnit()) ;	    
			if (!"".equals(getQ_keeper()))
				sql+=" and a.keeper = " + Common.sqlChar(getQ_keeper()) ;	    
			if (!"".equals(getQ_useUnit()))
				sql+=" and a.useUnit = " + Common.sqlChar(getQ_useUnit()) ;	    
			if (!"".equals(getQ_userNo()))
				sql+=" and a.userNo = " + Common.sqlChar(getQ_userNo()) ;	   
			if (!"".equals(getQ_buyDateS()))
				sql+=" and e.buyDate >= " + Common.sqlChar(ul._transToCE_Year(getQ_buyDateS()));
			if (!"".equals(getQ_buyDateE()))
				sql+=" and e.buyDate <= " + Common.sqlChar(ul._transToCE_Year(getQ_buyDateE()));
			if (!"".equals(getQ_oldSerialNoS()))
				sql+=" and a.oldserialno >= " + Common.sqlChar(Common.formatFrontZero(getQ_oldSerialNoS(),7));		
			if (!"".equals(getQ_oldSerialNoE()))
				sql+=" and a.oldserialno <=" + Common.sqlChar(Common.formatFrontZero(getQ_oldSerialNoE(),7));
			if (!"".equals(getQ_useUnit()))
				sql+=" and a.differencekind = " + Common.sqlChar(getQ_differenceKind()) ;
			if(!"".equals(getQ_userNote()))
				sql+=" and a.userNote like " + Common.sqlChar("%" + getQ_userNote() + "%");
			if(!"".equals(getQ_place()))
				sql+=" and a.place like " + Common.sqlChar("%" + getQ_place() + "%");
			if(!"".equals(getQ_place1()))
				sql+=" and a.place1 like " + Common.sqlChar("%" + getQ_place1() + "%");
			if (!"".equals(getQ_proofYear()))
				sql+=" and d.proofyear = " + Common.sqlChar(getQ_proofYear()) ;
			if(!"".equals(getQ_notes())) {
				sql += " and replace(a.notes, '-', space(0)) like " + Common.sqlChar("%" + getQ_notes().replace("-", "") + "%");
			}
			if ("1".equals(this.getRoleid())){
				
				sql += " and a.keeper = " + Common.sqlChar(this.getKeeperno());
				
			}else if ("2".equals(this.getRoleid())){
				
				sql += " and a.keepunit = " + Common.sqlChar(this.getUnitID());
									
			}else if ("3".equals(this.getRoleid())){		
				
			}
			if("Y".equals(getIsAdminManager())){
				
			}else{
				sql += " and a.enterorg = '" + this.getOrganID() + "'";
			}
		sql+=" order by a.enterOrg, a.ownership, a.propertyNo, a.serialNo, a.dataState ";
		
//System.out.println("-- untne003f queryAll --\n"+sql);

		ResultSet rs = db.querySQL(sql.toString(),true);
		processCurrentPageAttribute(rs);			
		if (getTotalRecord() > 0) {
			int count = getRecordStart();
			int end = getRecordEnd();
			do {
				if (count > end)
					break;
			String rowArray[]=new String[17];
			rowArray[0]=rs.getString("enterOrg"); 
			rowArray[1]=differencekindMap.get(rs.getString("differencekind"));
			rowArray[2]=rs.getString("organSName"); 
			rowArray[3]=rs.getString("ownership"); 
			rowArray[4]=rs.getString("ownershipName"); 
			rowArray[5]=rs.getString("propertyNo"); 
			rowArray[6]=rs.getString("serialNo"); 
			rowArray[7]=rs.getString("propertyName"); 
			rowArray[8]=rs.getString("keepUnitName");
			rowArray[9]=rs.getString("keeperName");
			rowArray[10]=checkGet(placeMap.get(rs.getString("place1")));
			rowArray[11]=rs.getString("dataState"); 
			rowArray[12]=rs.getString("bookValue"); 
			rowArray[13]=rs.getString("verify");
			rowArray[14]=rs.getString("lotNo");
			rowArray[15]=rs.getString("differencekind");
			rowArray[16]=rs.getString("caseNo");
			
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

//全部批次設定
public void batchSetButAll()throws Exception{
	UNTCH_Tools ul = new UNTCH_Tools();
	Database db = new Database();
	try {    
	    //int i = 0;
	    int counter = 0;
		String rowArray[]=new String[10];
		java.util.Iterator it= queryAll().iterator();
		String[] execSQLArray;
		StringBuffer sbSQL = new StringBuffer("");
		//String strSQL = "";
		while(it.hasNext()){
		    counter++;
			rowArray= (String[])it.next();
			if(rowArray[12].equals("N")){
				enterOrg = rowArray[0];
				ownership= rowArray[3];
				propertyNo=rowArray[5];
				serialNo   = rowArray[6];
				differenceKind   = rowArray[15];
				setEditID(getUserID());
				setEditDate(Datetime.getYYYMMDD());
				setEditTime(Datetime.getHHMMSS());
				if(enterOrg.equals(checkEnterOrg)){
					sbSQL.append("update UNTNE_NonexpDetail set ").append(
                				" originalMoveDate = " ).append( Common.sqlChar(this.getSetMoveDate()) ).append( "," ).append(
								" originalPlace = " ).append( Common.sqlChar(this.getSetPlace()) ).append( "," ).append(
								" originalKeepUnit = " ).append( Common.sqlChar(this.getSetKeepUnit()) ).append( "," ).append(
								" originalKeeper = " ).append( Common.sqlChar(this.getSetKeeper()) ).append( "," ).append(
								" originalUseUnit = " ).append( Common.sqlChar(this.getSetUseUnit()) ).append( "," ).append(
								" originalUser = " ).append( Common.sqlChar(this.getSetUser()) ).append( "," ).append(
								" moveDate = " ).append( Common.sqlChar(ul._transToCE_Year(this.getSetMoveDate())) ).append( "," ).append(
								" place = " ).append( Common.sqlChar(this.getSetPlace()) ).append( "," ).append(
								" keepUnit = " ).append( Common.sqlChar(this.getSetKeepUnit()) ).append( "," ).append(
								" keeper = " ).append( Common.sqlChar(this.getSetKeeper()) ).append( "," ).append(
								" useUnit = " ).append( Common.sqlChar(this.getSetUseUnit()) ).append( "," ).append(
								" userNo = " ).append( Common.sqlChar(this.getSetUser()) ).append( "," ).append(
								" editID = " ).append( Common.sqlChar(getEditID()) ).append( "," ).append(
								" editDate = " ).append( Common.sqlChar(ul._transToCE_Year(getEditDate())) ).append( "," ).append(
								" editTime = " ).append( Common.sqlChar(getEditTime()) ).append( 	                
								" where 1=1 " ).append( 
								" and enterOrg = " ).append( Common.sqlChar(enterOrg) ).append(
								" and ownership = " ).append( Common.sqlChar(ownership) ).append(
								" and propertyNo = " ).append( Common.sqlChar(propertyNo) ).append(
								" and serialNo = " ).append( Common.sqlChar(serialNo) ).append(
								" and differenceKind = " ).append( Common.sqlChar(differenceKind) ).append(
								":;:");	
				}
			}else{
				sbSQL.append("");
			}
		}
		if (counter>0) {
			execSQLArray = sbSQL.toString().split(":;:");
			db.excuteSQL(execSQLArray);
		}
		setStateUpdateSuccess();
		setErrorMsg("修改完成");	
	} catch (Exception e) {
		e.printStackTrace();
	} finally {
		db.closeAll();
	}		
}

/**
 * <br>
 * <br>目的：將非消耗品明細列出，做批次修改的選項
 * <br>參數：無
 * <br>傳回：傳回String
*/
public String getDetailCodeList() throws Exception{
	Map<String,String> placeMap = TCGHCommon.getSysca_PlaceCode(getEnterOrg());			//存置地點名稱
	Database db = new Database();
	StringBuffer sbHTML = new StringBuffer();
	sbHTML.append("");
	int counter=0;
	String selectDetail = "";
	try {
		String sql = " select a.serialNo,a.enterorg, a.originalMoveDate, a.originalKeepUnit, a.originalKeeper, a.originalUseUnit, a.originalUser, a.originalPlace,a.keepunit,  " +
					 " a.usernote,a.place,a.place1 " +
					 " from UNTNE_NonexpDetail a " +
					 " where 1=1  " +
					 " and a.enterOrg = " + Common.sqlChar(getEnterOrg()) +
					 " and a.ownership = " + Common.sqlChar(getOwnership()) +
					 " and a.propertyNo = " + Common.sqlChar(getPropertyNo()) +
					 " and a.differenceKind = " + Common.sqlChar(getDifferenceKind()) +
					 " and a.lotNo = " + Common.sqlChar(getLotNo()) +
					 " order by serialNo ";
		ResultSet rs = db.querySQL(sql);
		//System.out.println("-------------" + sql);
		sbHTML.append("<table class='queryTable'  border='1'>\n");
		sbHTML.append("<tr><td class='TDLable'><input type=checkbox name=toggleAll onclick=\"ToggleAll(this, document.form1, 'strKeys');\"></td>\n");			
		sbHTML.append("<td class='TDLable' align='center'>財產分號</td>\n");
//		sbHTML.append("<td class='TDLable' align='center'>移動日期</td>\n");
//		sbHTML.append("<td class='TDLable' align='center'>保管處別</td>\n");
		sbHTML.append("<td class='TDLable' align='center'>保管單位</td>\n");
		sbHTML.append("<td class='TDLable' align='center'>保管人</td>\n");
//		sbHTML.append("<td class='TDLable' align='center'>使用處別</td>\n");
		sbHTML.append("<td class='TDLable' align='center'>使用單位</td>\n");
		sbHTML.append("<td class='TDLable' align='center'>使用人</td>\n");
		sbHTML.append("<td class='TDLable' align='center'>使用註記</td>\n");
		sbHTML.append("<td class='TDLable' align='center'>存置地點</td>\n");
		sbHTML.append("<td class='TDLable' align='center'>存置地點說明</td>\n");
		sbHTML.append("</tr>\n");		
		while (rs.next()) {
			counter++;			
			sbHTML.append("<tr>\n");
			sbHTML.append("<td class='queryTDInput'><input type=\"checkbox\" name=\"strKeys\" value=\"" ).append( rs.getString("serialNo") ).append( "\" onClick=\"Toggle(this,document.form1,'strKeys');\" /></td>\n");
			sbHTML.append("<td class='queryTDInput'>[<input type='text' class='field_PRO' size='7' maxlength='7' name='serialNo_" ).append( rs.getString("serialNo")).append( "' value='" ).append( Common.get(rs.getString("serialNo")) ).append( "' readOnly=true>]</td>\n");
//			sbHTML.append("<td class='queryTDInput'><input type='text' class='field' size='7' maxlength='7' name='moveDate_" ).append( rs.getString("serialNo")).append( "' value='" ).append( Common.get(rs.getString("originalMoveDate")) ).append( "'></td>\n");
			
			sbHTML.append("<td class='queryTDInput'>\n" ).append(
			  "<input type='text' class='field' size='5' name='KeepUnitQuickly_").append( rs.getString("serialNo")).append("' value='").append(Common.checkGet(rs.getString("originalKeepUnit"))).append("' onChange='form1.useUnit_").append(rs.getString("serialNo")).append(".value = this.value").append(";form1.keepUnit_").append(rs.getString("serialNo")).append(".value = this.value").append(";form1.UseUnitQuickly_").append(rs.getString("serialNo")).append(".value = this.value").append(";'>" ).append(					
			  "<select class='field' type='select' name='keepUnit_").append( rs.getString("serialNo")).append("' onChange='form1.useUnit_").append(rs.getString("serialNo")).append(".value = this.value").append(";form1.KeepUnitQuickly_").append(rs.getString("serialNo")).append(".value = this.value").append(";form1.UseUnitQuickly_").append(rs.getString("serialNo")).append(".value = this.value").append(";' >\n" ).append(
			  "<script>changeKeepUnit(form1.enterOrg").append(", form1.keepUnit_").append(rs.getString("serialNo")).append(", '").append(rs.getString("originalKeepUnit")).append("');</script>\n").append(
			  "</select>").append(
			  "<input class='field_Q' type='button' name='btn_q_keepUnit_").append(rs.getString("serialNo")).append("' onclick=\"popUnitNo('").append(rs.getString("enterorg")).append("','keepUnit_").append(rs.getString("serialNo")).append("','useUnit_").append(rs.getString("serialNo")).append("')\" value='...' title='單位輔助視窗'>").append(
			  "</td>\n");
			sbHTML.append("<td class='queryTDInput'>\n" ).append(
			  "<input type='text' class='field' size='5' name='KeeperQuickly_").append( rs.getString("serialNo")).append("' value='").append(Common.checkGet(rs.getString("originalKeeper"))).append("' onChange='form1.user_").append(rs.getString("serialNo")).append(".value = this.value").append(";form1.keeper_").append(rs.getString("serialNo")).append(".value = this.value").append(";form1.UserQuickly_").append(rs.getString("serialNo")).append(".value = this.value").append(";'>" ).append(					
			  "<select class='field' type='select' name='keeper_").append( rs.getString("serialNo")).append("'  onChange='form1.user_").append(rs.getString("serialNo")).append(".value = this.value").append(";form1.KeeperQuickly_").append(rs.getString("serialNo")).append(".value = this.value").append(";form1.UserQuickly_").append(rs.getString("serialNo")).append(".value = this.value").append(";'>\n" ).append(
			  "<script>getKeeper(form1.enterOrg, form1.keeper_").append( rs.getString("serialNo")).append(",'").append( rs.getString("originalKeeper")).append("', '").append(rs.getString("originalKeeper")).append("');</script>\n").append(
			  "</select>").append(
			  "<input class='field_Q' type='button' name='btn_q_keeper_").append(rs.getString("serialNo")).append("' onclick=\"popUnitMan('").append(rs.getString("enterorg")).append("','keeper_").append(rs.getString("serialNo")).append("','user_").append(rs.getString("serialNo")).append("')\" value='...' title='人員輔助視窗'>").append(
			  "</td>\n");

			sbHTML.append("<td class='queryTDInput'>\n" ).append(
			  "<input type='text' class='field' size='5' name='UseUnitQuickly_").append( rs.getString("serialNo")).append("' value='").append(Common.checkGet(rs.getString("originalUseUnit"))).append("' onChange='form1.useUnit_").append(rs.getString("serialNo")).append(".value = this.value").append(";'>" ).append(
			  "<select class='field' type='select' name='useUnit_").append( rs.getString("serialNo")).append("' onChange='form1.UseUnitQuickly_").append(rs.getString("serialNo")).append(".value = this.value").append(";'>\n" ).append(
			  "<script>changeKeepUnit(form1.enterOrg").append(", form1.useUnit_").append(rs.getString("serialNo")).append(", '").append(rs.getString("originalUseUnit")).append("');</script>\n").append(
			  "</select>").append(
			  "<input class='field_Q' type='button' name='btn_q_useUnit_").append(rs.getString("serialNo")).append("' onclick=\"popUnitNo('").append(rs.getString("enterorg")).append("','useUnit_").append(rs.getString("serialNo")).append("','keepUnit_").append(rs.getString("serialNo")).append("','keepUnit_").append(rs.getString("serialNo")).append("')\" value='...' title='單位輔助視窗'>").append(
			  "</td>\n");
			sbHTML.append("<td class='queryTDInput'>\n" ).append(
			  "<input type='text' class='field' size='5' name='UserQuickly_").append( rs.getString("serialNo")).append("' value='").append(Common.checkGet(rs.getString("originalUser"))).append("' onChange='form1.user_").append(rs.getString("serialNo")).append(".value = this.value").append(";'>" ).append(
			  "<select class='field' type='select' name='user_").append( rs.getString("serialNo")).append("'  onChange='form1.UserQuickly_").append(rs.getString("serialNo")).append(".value = this.value").append(";' >\n").append(
			  "<script>getKeeper(form1.enterOrg, form1.user_").append( rs.getString("serialNo")).append(",'").append( rs.getString("originalUser")).append("', '").append(rs.getString("originalUser")).append("');</script>\n").append(
			  "</select>").append(
			  "<input class='field_Q' type='button' name='btn_q_user_").append(rs.getString("serialNo")).append("' onclick=\"popUnitMan('").append(rs.getString("enterorg")).append("','user_").append(rs.getString("serialNo")).append("','keeper_").append(rs.getString("serialNo")).append("')\" value='...' title='人員輔助視窗'>").append(
			  "</td>\n");
			sbHTML.append("<td class='queryTDInput'><input type='text' class='field' size='15' maxlength='15' name='userNote_").append( rs.getString("serialNo")).append("' value='").append(Common.get(rs.getString("usernote"))).append("'></td>\n");
			sbHTML.append("<td class='queryTDInput'>").append(
			  "<input class='field_Q' type='text' name='place1_").append( rs.getString("serialNo")).append("' size='5' maxlength='5' value='").append(Common.get(rs.getString("place1"))).append("' onChange=\"queryPlaceName('enterOrg','place1_").append( rs.getString("serialNo")).append("');").append("\" >").append(
			  "&nbsp;<input class='field_Q' type='button' name='btn_q_place_").append(rs.getString("serialNo")).append("' onclick=\"popPlace('").append(rs.getString("enterorg")).append("','place1_").append(rs.getString("serialNo")).append("','place1_").append(rs.getString("serialNo")).append("Name").append("')\" value='...' title='存置地點輔助視窗'>").append(
			  "&nbsp;[<input class='field_RO' type='text' name='place1_").append(rs.getString("serialNo")).append("Name").append("' size='20' maxlength='20' value='").append(Common.get(placeMap.get(rs.getString("place1")))).append("'>]").append(
			  "</td>\n");

			sbHTML.append("<td class='queryTDInput'><input type='text' class='field' size='15' maxlength='15' name='place_").append(rs.getString("serialNo")).append("' value='").append(Common.get(rs.getString("place"))).append("'></td>\n");
			sbHTML.append("</tr>\n");
		}
		sbHTML.append("</table>\n");
	} catch (Exception e) {
		e.printStackTrace();
	} finally {
		db.closeAll();
	}
	return sbHTML.toString();
}

public void insertProcess(String strSQL) {
	Database db = new Database();
	String[] arrSQL = strSQL.split(":;:");
    try {
		if (!beforeExecCheck(getInsertCheckSQL())){			
			setBatchInsertFlag("E");
		} else {
			db.excuteSQL(arrSQL);
			setBatchInsertFlag("Y");
			setStateInsertSuccess();
		}
	} catch (Exception e) {
		e.printStackTrace();
	} finally {
		db.closeAll();
	}
}

}


