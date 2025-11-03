package unt.dp;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Map;

import unt.ch.UNTCH_Tools;
import util.Common;
import util.Database;
import util.Datetime;
import util.TCGHCommon;
import TDlib_Simple.tools.src.StringTools;

public class UNTDP002F extends UNTDP002Q{

	private String enterOrg;
	private String ownership;
	private String differenceKind;
	private String propertyNo;
	private String serialNo;
	private String propertyNoName;
	private String lotNo;
	private String serialNo1;
	private String deprYM;
	private String deprFrequency;
	private String verify;
	private String propertyType;
	private String propertyKind;
	private String fundType;
	private String valuable;
	private String deprMethod;
	private String buildFeeCB;
	private String deprUnitCB;
	private String deprPark;
	private String deprUnit;
	private String deprUnit1;
	private String deprAccounts;
	private String deprPercent;
	private String originalBV;
	private String bookValue;
	private String scrapValue;
	private String deprAmount;
	private String apportionMonth;
	private String oldNetValue;
	private String oldAccumDepr;
	private String addAccumDepr;
	private String reduceAccumDepr;
	private String monthDepr1;
	private String monthDepr2;
	private String monthDepr;
	private String newAccumDepr;
	private String newNetValue;
	private String scaledBookValue;
	private String scaledOldNetValue;
	private String scaledOldAccumDepr;
	private String scaledAddAccumDepr;
	private String scaledReduceAccumDepr;
	private String scaledMonthDepr1;
	private String scaledMonthDepr2;
	private String scaledMonthDepr;
	private String scaledNewAccumDepr;
	private String scaledNewNetValue;
	private String buyDate;
	private String enterDate;
	private String sourceDate;
	private String propertyName1;
	private String limitYear;
	private String bookAmount;
	private String keepUnit;
	private String keeper;
	private String escrowOriLimitMonth;
	private String escrowOriValue;
	private String escrowOriAccumDepr;
	private String engineeringNo;
	private String notes;
	private String editID;
	private String editDate;
	private String editTime;
	private String ownershipName;
	private String differenceKindName;
	private String recalCulated;

	public String getEnterOrg() {return checkGet(enterOrg);}
	public void setEnterOrg(String enterOrg) {this.enterOrg = checkSet(enterOrg);	}
	public String getOwnership() {return checkGet(ownership);	}
	public void setOwnership(String ownership) {this.ownership = checkSet(ownership);	}
	public String getDifferenceKind() {	return checkGet(differenceKind);}
	public void setDifferenceKind(String differenceKind) {this.differenceKind = checkSet(differenceKind);	}
	public String getPropertyNo() {	return checkGet(propertyNo);}
	public void setPropertyNo(String propertyNo) {this.propertyNo = checkSet(propertyNo);	}
	public String getSerialNo() {return checkGet(serialNo);}
	public void setSerialNo(String serialNo) {this.serialNo = checkSet(serialNo);	}
	public String getPropertyNoName() {	return checkGet(propertyNoName);	}
	public void setPropertyNoName(String propertyNoName) {this.propertyNoName = checkSet(propertyNoName);	}
	public String getLotNo() {return checkGet(lotNo);}
	public void setLotNo(String lotNo) {this.lotNo = checkSet(lotNo);	}
	public String getSerialNo1() {return checkGet(serialNo1);	}
	public void setSerialNo1(String serialNo1) {this.serialNo1 = checkSet(serialNo1);	}
	public String getDeprYM() {	return checkGet(deprYM);}
	public void setDeprYM(String deprYM) {this.deprYM = checkSet(deprYM);}
	public String getDeprFrequency() {return checkGet(deprFrequency);}
	public void setDeprFrequency(String deprFrequency) {this.deprFrequency = checkSet(deprFrequency);	}
	public String getVerify() {return checkGet(verify);}
	public void setVerify(String verify) {this.verify = checkSet(verify);	}
	public String getPropertyType() {return checkGet(propertyType);}
	public void setPropertyType(String propertyType) {	this.propertyType = checkSet(propertyType);}
	public String getPropertyKind() {return checkGet(propertyKind);}
	public void setPropertyKind(String propertyKind) {this.propertyKind = checkSet(propertyKind);}
	public String getFundType() {return checkGet(fundType);}
	public void setFundType(String fundType) {this.fundType = checkSet(fundType);}
	public String getValuable() {return checkGet(valuable);}
	public void setValuable(String valuable) {this.valuable = checkSet(valuable);	}
	public String getDeprMethod() {	return checkGet(deprMethod);}
	public void setDeprMethod(String deprMethod) {this.deprMethod = checkSet(deprMethod);}
	public String getBuildFeeCB() {	return checkGet(buildFeeCB);}
	public void setBuildFeeCB(String buildFeeCB) {this.buildFeeCB = checkSet(buildFeeCB);	}
	public String getDeprUnitCB() {return checkGet(deprUnitCB);}
	public void setDeprUnitCB(String deprUnitCB) {this.deprUnitCB = checkSet(deprUnitCB);	}
	public String getDeprPark() {return checkGet(deprPark);}
	public void setDeprPark(String deprPark) {this.deprPark = checkSet(deprPark);	}
	public String getDeprUnit() {return checkGet(deprUnit);}
	public void setDeprUnit(String deprUnit) {this.deprUnit = checkSet(deprUnit);}
	public String getDeprUnit1() {return checkGet(deprUnit1);	}
	public void setDeprUnit1(String deprUnit1) {this.deprUnit1 = checkSet(deprUnit1);}
	public String getDeprAccounts() {return checkGet(deprAccounts);}
	public void setDeprAccounts(String deprAccounts) {this.deprAccounts = checkSet(deprAccounts);	}
	public String getDeprPercent() {return checkGet(deprPercent);	}
	public void setDeprPercent(String deprPercent) {this.deprPercent = checkSet(deprPercent);	}
	public String getOriginalBV() {	return checkGet(originalBV);}
	public void setOriginalBV(String originalBV) {this.originalBV = checkSet(originalBV);	}
	public String getBookValue() {	return checkGet(bookValue);}
	public void setBookValue(String bookValue) {this.bookValue = checkSet(bookValue);	}
	public String getScrapValue() {return checkGet(scrapValue);}
	public void setScrapValue(String scrapValue) {this.scrapValue = checkSet(scrapValue);	}
	public String getDeprAmount() {	return checkGet(deprAmount);}
	public void setDeprAmount(String deprAmount) {this.deprAmount = checkSet(deprAmount);}
	public String getApportionMonth() {return checkGet(apportionMonth);}
	public void setApportionMonth(String apportionMonth) {this.apportionMonth = checkSet(apportionMonth);	}
	public String getOldNetValue() {return checkGet(oldNetValue);	}
	public void setOldNetValue(String oldNetValue) {this.oldNetValue = checkSet(oldNetValue);	}
	public String getOldAccumDepr() {return checkGet(oldAccumDepr);}
	public void setOldAccumDepr(String oldAccumDepr) {this.oldAccumDepr = checkSet(oldAccumDepr);}
	public String getAddAccumDepr() {return checkGet(addAccumDepr);}
	public void setAddAccumDepr(String addAccumDepr) {this.addAccumDepr = checkSet(addAccumDepr);	}
	public String getReduceAccumDepr() {return checkGet(reduceAccumDepr);	}
	public void setReduceAccumDepr(String reduceAccumDepr) {this.reduceAccumDepr = checkSet(reduceAccumDepr);	}
	public String getMonthDepr1() {	return checkGet(monthDepr1);	}
	public void setMonthDepr1(String monthDepr1) {this.monthDepr1 = checkSet(monthDepr1);	}
	public String getMonthDepr2() {return checkGet(monthDepr2);}
	public void setMonthDepr2(String monthDepr2) {this.monthDepr2 = checkSet(monthDepr2);	}
	public String getMonthDepr() {return checkGet(monthDepr);	}
	public void setMonthDepr(String monthDepr) {this.monthDepr = checkSet(monthDepr);	}
	public String getNewAccumDepr() {return checkGet(newAccumDepr);}
	public void setNewAccumDepr(String newAccumDepr) {this.newAccumDepr = checkSet(newAccumDepr);}
	public String getNewNetValue() {return checkGet(newNetValue);	}
	public void setNewNetValue(String newNetValue) {this.newNetValue = checkSet(newNetValue);	}
	public String getScaledBookValue() {return checkGet(scaledBookValue);	}
	public void setScaledBookValue(String scaledBookValue) {this.scaledBookValue = checkSet(scaledBookValue);	}
	public String getScaledOldNetValue() {return checkGet(scaledOldNetValue);	}
	public void setScaledOldNetValue(String scaledOldNetValue) {this.scaledOldNetValue = checkSet(scaledOldNetValue);	}
	public String getScaledOldAccumDepr() {return checkGet(scaledOldAccumDepr);}
	public void setScaledOldAccumDepr(String scaledOldAccumDepr) {this.scaledOldAccumDepr = checkSet(scaledOldAccumDepr);	}
	public String getScaledAddAccumDepr() {return checkGet(scaledAddAccumDepr);}
	public void setScaledAddAccumDepr(String scaledAddAccumDepr) {this.scaledAddAccumDepr = checkSet(scaledAddAccumDepr);	}
	public String getScaledReduceAccumDepr() {return checkGet(scaledReduceAccumDepr);	}
	public void setScaledReduceAccumDepr(String scaledReduceAccumDepr) {this.scaledReduceAccumDepr = checkSet(scaledReduceAccumDepr);	}
	public String getScaledMonthDepr1() {return checkGet(scaledMonthDepr1);}
	public void setScaledMonthDepr1(String scaledMonthDepr1) {this.scaledMonthDepr1 = checkSet(scaledMonthDepr1);	}
	public String getScaledMonthDepr2() {return checkGet(scaledMonthDepr2);	}
	public void setScaledMonthDepr2(String scaledMonthDepr2) {this.scaledMonthDepr2 = checkSet(scaledMonthDepr2);	}
	public String getScaledMonthDepr() {return checkGet(scaledMonthDepr);	}
	public void setScaledMonthDepr(String scaledMonthDepr) {this.scaledMonthDepr = checkSet(scaledMonthDepr);	}
	public String getScaledNewAccumDepr() {	return checkGet(scaledNewAccumDepr);}
	public void setScaledNewAccumDepr(String scaledNewAccumDepr) {this.scaledNewAccumDepr = checkSet(scaledNewAccumDepr);	}
	public String getScaledNewNetValue() {return checkGet(scaledNewNetValue);	}
	public void setScaledNewNetValue(String scaledNewNetValue) {this.scaledNewNetValue = checkSet(scaledNewNetValue);	}
	public String getBuyDate() {return checkGet(buyDate);	}
	public void setBuyDate(String buyDate) {this.buyDate = checkSet(buyDate);	}
	public String getEnterDate() {return checkGet(enterDate);}
	public void setEnterDate(String enterDate) {this.enterDate = checkSet(enterDate);	}
	public String getSourceDate() {	return checkGet(sourceDate);	}
	public void setSourceDate(String sourceDate) {this.sourceDate = checkSet(sourceDate);	}
	public String getPropertyName1() {return checkGet(propertyName1);	}
	public void setPropertyName1(String propertyName1) {this.propertyName1 = checkSet(propertyName1);	}
	public String getLimitYear() {return checkGet(limitYear);	}
	public void setLimitYear(String limitYear) {this.limitYear = checkSet(limitYear);	}
	public String getBookAmount() {return checkGet(bookAmount);}
	public void setBookAmount(String bookAmount) {this.bookAmount = checkSet(bookAmount);	}
	public String getKeepUnit() {return checkGet(keepUnit);}
	public void setKeepUnit(String keepUnit) {	this.keepUnit = checkSet(keepUnit);	}
	public String getKeeper() {	return checkGet(keeper);	}
	public void setKeeper(String keeper) {this.keeper = checkSet(keeper);	}
	public String getEscrowOriLimitMonth() {return checkGet(escrowOriLimitMonth);	}
	public void setEscrowOriLimitMonth(String escrowOriLimitMonth) {this.escrowOriLimitMonth = checkSet(escrowOriLimitMonth);	}
	public String getEscrowOriValue() {return checkGet(escrowOriValue);	}
	public void setEscrowOriValue(String escrowOriValue) {this.escrowOriValue = checkSet(escrowOriValue);	}
	public String getEscrowOriAccumDepr() {return checkGet(escrowOriAccumDepr);}
	public void setEscrowOriAccumDepr(String escrowOriAccumDepr) {this.escrowOriAccumDepr = checkSet(escrowOriAccumDepr);	}
	public String getEngineeringNo() {return checkGet(engineeringNo);}
	public void setEngineeringNo(String engineeringNo) {this.engineeringNo = checkSet(engineeringNo);	}
	public String getNotes() {return checkGet(notes);	}
	public void setNotes(String notes) {this.notes = checkSet(notes);	}
	public String getEditID() {	return checkGet(editID);}
	public void setEditID(String editID) {this.editID = checkSet(editID);}
	public String getEditDate() {return checkGet(editDate);}
	public void setEditDate(String editDate) {this.editDate = checkSet(editDate);	}
	public String getEditTime() {return checkGet(editTime);}
	public void setEditTime(String editTime) {this.editTime = checkSet(editTime);	}
	public String getOwnershipName() {	return checkGet(ownershipName);}
	public void setOwnershipName(String ownershipName) {this.ownershipName = checkSet(ownershipName);	}
	public String getDifferenceKindName() {	return checkGet(differenceKindName);}
	public void setDifferenceKindName(String differenceKindName) {this.differenceKindName = checkSet(differenceKindName);	}
	public String getRecalCulated() {	return checkGet(recalCulated);}
	public void setRecalCulated(String recalCulated) {this.recalCulated = checkSet(recalCulated);	}
	
	
	//傳回update sql
	public void update(){
		
	    Database db = new Database();    
	    String strSQL = "";
		String[] execSQLArray;
		setEditDate(Datetime.getYYYMMDD());
		setEditTime(Datetime.getHHMMSS());

		try {
			if(getDeprUnitCB().equals("Y")){
			strSQL +=" update UNTDP_MONTHDEPR set " +
					" monthdepr1 = " + Common.sqlChar(monthDepr1) + "," +
					" monthdepr2 = " + Common.sqlChar(monthDepr2) + "," +
					" monthdepr = " + Common.sqlChar(monthDepr) + "," +
					" newaccumdepr = " + Common.sqlChar(newAccumDepr) + "," +
					" newnetvalue = " + Common.sqlChar(newNetValue) + "," +
					" scaledmonthdepr1 = " + Common.sqlChar(scaledMonthDepr1) + "," +
					" scaledmonthdepr2 = " + Common.sqlChar(scaledMonthDepr2) + "," +
					" notes = " + Common.sqlChar(notes) + "," +
					" recalculated = 'Y', " + // 調整過一律設定成為Y, 入帳時 此欄位為Y 且monthdepr != oldmonthdepr 則重算
					" editid = " + Common.sqlChar(getEditID()) + "," +
					" editdate = " + Common.sqlChar(new UNTCH_Tools()._transToCE_Year(getEditDate())) + "," +
					" edittime = " + Common.sqlChar(getEditTime()) + 
				    " where 1=1 " + 
				    " and enterorg = " + Common.sqlChar(enterOrg) +
				    " and ownership = " + Common.sqlChar(ownership) +
				    " and differencekind = " + Common.sqlChar(differenceKind) +
				    " and propertyno = " + Common.sqlChar(propertyNo) +
				    " and serialno = " + Common.sqlChar(serialNo) +
				    " and serialno1 = " + Common.sqlChar(serialNo1) +
					"";}
			else if (getDeprUnitCB().equals("N")){
				strSQL +=" update UNTDP_MONTHDEPR set " +
				" monthdepr1 = " + Common.sqlChar(monthDepr1) + "," +
				" monthdepr2 = " + Common.sqlChar(monthDepr2) + "," +
				" monthdepr = " + Common.sqlChar(monthDepr) + "," +
				" newaccumdepr = " + Common.sqlChar(newAccumDepr) + "," +
				" newnetvalue = " + Common.sqlChar(newNetValue) + "," +
				" notes = " + Common.sqlChar(notes) + "," +
				" recalculated = 'Y'," + // 調整過一律設定成為Y, 入帳時 此欄位為Y 且monthdepr != oldmonthdepr 則重算
				" editid = " + Common.sqlChar(getEditID()) + "," +
				" editdate = " + Common.sqlChar(new UNTCH_Tools()._transToCE_Year(getEditDate())) + "," +
				" edittime = " + Common.sqlChar(getEditTime()) + 
			    " where 1=1 " + 
			    " and enterorg = " + Common.sqlChar(enterOrg) +
			    " and ownership = " + Common.sqlChar(ownership) +
			    " and differencekind = " + Common.sqlChar(differenceKind) +
			    " and propertyno = " + Common.sqlChar(propertyNo) +
			    " and serialno = " + Common.sqlChar(serialNo) +
			    " and serialno1 = " + Common.sqlChar(serialNo1) +
				"";}
				db.excuteSQL(strSQL);
				setStateUpdateSuccess();
				setErrorMsg("修改完成");				
			 			   
		       setStateUpdateSuccess();
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.closeAll();
		}	
	}

//	傳回delete sql
	protected String[] getDeleteSQL(){
		String[] execSQLArray = new String[1];
		execSQLArray[0]=" delete UNTDP_MONTHDEPR where 1=1 " +
		                " and enterorg = " + Common.sqlChar(enterOrg) +
					    " and ownership = " + Common.sqlChar(ownership) +
					    " and differencekind = " + Common.sqlChar(differenceKind) +
					    " and propertyno = " + Common.sqlChar(propertyNo) +
					    " and serialno = " + Common.sqlChar(serialNo) +
					    " and serialno1 = " + Common.sqlChar(serialNo1) +
				        "";
	//System.out.println(execSQLArray[0].toString());	
		return execSQLArray;
	}


	/**
	 * <br>
	 * <br>目的：依主鍵查詢單一資料
	 * <br>參數：無
	 * <br>傳回：傳回本物件
	*/

	public UNTDP002F  queryOne() throws Exception{
		Database db = new Database();
		Map<String,String> differencekindMap = TCGHCommon.getSysca_Code("DFK"); 					//財產區分別名稱
		Map<String,String> deprparkMap = TCGHCommon.getSysca_DeprPark(this.getQ_enterOrg());		//園區別名稱
		Map<String,String> deprunitMap = TCGHCommon.getSysca_DeprUnit(this.getQ_enterOrg());		//部門別名稱
		Map<String,String> deprunit1Map = TCGHCommon.getSysca_DeprUnit1(this.getQ_enterOrg());		//部門別單位名稱
		Map KeepUnitMap = TCGHCommon.getUntmp_KeepUnitCode(this.getQ_enterOrg()); //保管單位、使用單位
		Map KeeperMap = TCGHCommon.getUntmp_KeeperCode(this.getQ_enterOrg()); //保管人、使用人
		UNTDP002F obj = this;
		try {
			String sql=" select a.enterOrg,a.ownership,a.differenceKind,a.propertyNo,a.serialNo,a.lotNo,a.serialNo1,a.deprYM,0 as deprFrequency," +
					   " a.verify as verify,a.propertyType,a.propertyKind,a.fundType,a.valuable,a.buildFeeCB,a.deprUnitCB," +
					   " a.deprPercent,a.originalBV,a.bookValue,a.scrapValue,a.deprAmount,a.apportionMonth," +
					   " a.oldNetValue,a.oldAccumDepr,a.addAccumDepr,a.reduceAccumDepr,a.monthDepr1,a.monthDepr2,a.monthDepr,a.newAccumDepr," +
					   " a.newNetValue,a.scaledBookValue,a.scaledOldNetValue,a.scaledOldAccumDepr,a.scaledAddAccumDepr,a.scaledReduceAccumDepr," +
					   " a.scaledMonthDepr1,a.scaledMonthDepr2,a.scaledMonthDepr,a.scaledNewAccumDepr,a.scaledNewNetValue,a.buyDate,a.enterDate," +
					   " a.sourceDate,a.propertyName1,a.limitYear,a.bookAmount,a.keepUnit,a.keeper,a.escrowOriLimitMonth,a.escrowOriValue," +
					   " (select propertyname from SYSPK_PROPERTYCODE z where z.enterorg  in (a.enterorg,'000000000A') and z.propertyno = a.propertyNo) as propertyNoName," +
					   " (select codename from sysca_code x where x.codekindid = 'OWA' and x.codeid = a.ownership ) as ownershipName,"+
					   " (select codename from sysca_code x where x.codekindid = 'DEP' and x.codeid = a.deprMethod ) as deprMethod,"+
					   " a.deprPark,"+
					   " a.deprUnit,"+
					   " a.deprUnit1,"+
					   " (select  depraccountsname from SYSCA_DEPRACCOUNTS x where x.enterorg=a.enterorg and x.depraccountsno=a.deprAccounts) as deprAccounts,"+
					   " a.escrowOriAccumDepr,a.engineeringNo,a.notes,a.recalculated,a.editID,a.editDate,a.editTime "+
					   " from UNTDP_MONTHDEPR a where 1=1 " +
					   " and a.enterOrg = " + Common.sqlChar(enterOrg) +
					   " and a.ownership = " + Common.sqlChar(ownership) +
					   " and a.differenceKind = " + Common.sqlChar(differenceKind) +
					   " and a.propertyNo = " + Common.sqlChar(propertyNo) +
					   " and a.serialNo = " + Common.sqlChar(serialNo) +
					   " and a.serialNo1 = " + Common.sqlChar(serialNo1) +
					   "";
	//System.out.println("-- untmp002f queryOne -- "+sql);
			UNTCH_Tools ul = new UNTCH_Tools();
			ResultSet rs = db.querySQL(sql);
			if (rs.next()){
				obj.setEnterOrg(rs.getString("enterOrg"));
				obj.setOwnership(rs.getString("ownership"));
				obj.setDifferenceKind(rs.getString("differenceKind"));
				obj.setPropertyNo(rs.getString("propertyNo"));
				obj.setSerialNo(rs.getString("serialNo"));
				obj.setLotNo(rs.getString("lotNo"));
				obj.setSerialNo1(rs.getString("serialNo1"));
				obj.setDeprYM(ul._transToROC_Year(rs.getString("deprYM")));
				obj.setDeprFrequency(rs.getString("deprFrequency"));
				obj.setVerify(rs.getString("verify"));
				obj.setPropertyType(rs.getString("propertyType"));
				obj.setPropertyKind(rs.getString("propertyKind"));
				obj.setFundType(rs.getString("fundType"));
				obj.setValuable(rs.getString("valuable"));
				obj.setDeprMethod(rs.getString("deprMethod"));
				obj.setBuildFeeCB(rs.getString("buildFeeCB"));
				obj.setDeprUnitCB(rs.getString("deprUnitCB"));
				obj.setDeprPark(checkGet(deprparkMap.get(rs.getString("deprpark"))));
				obj.setDeprUnit(checkGet(deprunitMap.get(rs.getString("deprunit"))));
				obj.setDeprUnit1(checkGet(deprunit1Map.get(rs.getString("deprunit1"))));
				obj.setDeprAccounts(rs.getString("deprAccounts"));
				obj.setDeprPercent(rs.getString("deprPercent"));
				obj.setOriginalBV(rs.getString("originalBV"));
				obj.setBookValue(rs.getString("bookValue"));
				obj.setScrapValue(rs.getString("scrapValue"));
				obj.setDeprAmount(rs.getString("deprAmount"));
				obj.setApportionMonth(rs.getString("apportionMonth"));
				obj.setOldNetValue(rs.getString("oldNetValue"));
				obj.setOldAccumDepr(rs.getString("oldAccumDepr"));
				obj.setAddAccumDepr(rs.getString("addAccumDepr"));
				obj.setReduceAccumDepr(rs.getString("reduceAccumDepr"));
				obj.setMonthDepr1(rs.getString("monthDepr1"));
				obj.setMonthDepr2(rs.getString("monthDepr2"));
				obj.setMonthDepr(rs.getString("monthDepr"));
				obj.setNewAccumDepr(rs.getString("newAccumDepr"));
				obj.setNewNetValue(rs.getString("newNetValue"));
				obj.setScaledBookValue(rs.getString("scaledBookValue"));
				obj.setScaledOldNetValue(rs.getString("scaledOldNetValue"));
				obj.setScaledOldAccumDepr(rs.getString("scaledOldAccumDepr"));
				obj.setScaledAddAccumDepr(rs.getString("scaledAddAccumDepr"));
				obj.setScaledReduceAccumDepr(rs.getString("scaledReduceAccumDepr"));
				obj.setScaledMonthDepr1(rs.getString("scaledMonthDepr1"));
				obj.setScaledMonthDepr2(rs.getString("scaledMonthDepr2"));
				obj.setScaledMonthDepr(rs.getString("scaledMonthDepr"));
				obj.setScaledNewAccumDepr(rs.getString("scaledNewAccumDepr"));
				obj.setScaledNewNetValue(rs.getString("scaledNewNetValue"));
				obj.setBuyDate(ul._transToROC_Year(rs.getString("buyDate")));
				obj.setEnterDate(ul._transToROC_Year(rs.getString("enterDate")));
				obj.setSourceDate(ul._transToROC_Year(rs.getString("sourceDate")));
				obj.setPropertyName1(rs.getString("propertyName1"));
				obj.setLimitYear(Common.formatOtherLimityear(1, rs.getString("limityear")));
				obj.setBookAmount(rs.getString("bookAmount"));
				obj.setKeepUnit((String)KeepUnitMap.get(keepUnit));
				obj.setKeeper((String)KeeperMap.get(keeper));
				obj.setEscrowOriLimitMonth(rs.getString("escrowOriLimitMonth"));
				obj.setEscrowOriValue(rs.getString("escrowOriValue"));
				obj.setEscrowOriAccumDepr(rs.getString("escrowOriAccumDepr"));
				obj.setEngineeringNo(rs.getString("engineeringNo"));
				obj.setNotes(rs.getString("notes"));
				obj.setEditID(rs.getString("editID"));
				obj.setEditDate(rs.getString("editDate"));
				obj.setEditTime(rs.getString("editTime"));
				obj.setPropertyNoName(rs.getString("PropertyNoname"));
				obj.setOwnershipName(rs.getString("ownershipName"));
				obj.setDifferenceKindName(differencekindMap.get(rs.getString("differencekind")));
				obj.setRecalCulated(rs.getString("recalCulated"));
				
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
		StringTools st = new StringTools();
		Database db = new Database();
		ArrayList objList=new ArrayList();
		
		int counter=0;
		try {
			String sql=" select a.deprym,a.enterorg,a.ownership,a.differenceKind,a.propertyno, a.serialno,a.serialNo1, a.scrapvalue, a.limityear, a.buydate, a.enterdate,a.originalbv, " +
			" (select propertyname from SYSPK_PROPERTYCODE z where z.enterorg  in (a.enterorg,'000000000A') and z.propertyno = a.propertyNo) as propertyNoName," +
					   " case when deprunitcb = 'Y' then a.scaledbookvalue else a.bookvalue end bookvalue," +
					   " case when deprunitcb = 'Y' then a.scaledoldnetvalue else a.oldnetvalue end oldnetvalue," +
					   " case when deprunitcb = 'Y' then a.scaledmonthdepr else a.monthdepr end monthdepr," +
					   " case when deprunitcb = 'Y' then a.scalednewnetvalue else a.newnetvalue end newnetvalue,a.verify"+
				       " from UNTDP_MONTHDEPR a where 1=1 "; 
			if (!"".equals(getQ_enterOrg())) {
				sql +=" and a.enterOrg = " + Common.sqlChar(getQ_enterOrg()) ;
			}
			if (!"".equals(getQ_deprYM())) {
				sql +=" and a.deprym = " + Common.sqlChar(ul._transToCE_Year(getQ_deprYM())) ;
			}else{
				//若無輸入年月時，則條件帶最大年月+1
				String sql2 = "select closing1ym from UNTAC_CLOSINGPT where 1=1" +
						" and enterorg = " + Common.sqlChar(getQ_enterOrg()) +" ";
				if (!"".equals(getQ_differenceKind())) {
					sql2+=" and differencekind = " + Common.sqlChar(getQ_differenceKind());
				}  
				String closing1ym = queryDatafromDatabase(sql2);
				
				String yyyy = closing1ym.substring(0,4);
				String mm = closing1ym.substring(4);
				
				int i_mm = Integer.parseInt(mm);
				String tempStr = null;
				
				i_mm++;
				if(i_mm > 12){
					tempStr = Common.formatFrontZero(String.valueOf(Integer.parseInt(yyyy) + 1),4) + Common.formatFrontZero(String.valueOf(i_mm - 12),2);
				}else{
					tempStr = yyyy + Common.formatFrontZero(String.valueOf(i_mm),2);
				}
				//若無輸入年月時，則條件帶最大年月+1
				sql +=" and a.deprym = " + Common.sqlChar(tempStr) ;
			}
			if (!"".equals(getQ_propertyType())) {
				sql +=" and a.propertytype = " + Common.sqlChar(getQ_propertyType()) ;
			}
			if (!"".equals(getQ_differenceKind())) {
				sql +=" and a.differenceKind = " + Common.sqlChar(getQ_differenceKind()) ;
			}
			if (!"".equals(getQ_ownership())) {
				sql +=" and a.ownership = " + Common.sqlChar(getQ_ownership()) ;
			}
			if (!"".equals(getQ_propertyNoS()))
				sql+=" and a.propertyNo>= " + Common.sqlChar(getQ_propertyNoS()) ;
			if (!"".equals(getQ_propertyNoE()))
				sql+=" and a.propertyNo <= " + Common.sqlChar(getQ_propertyNoE()) ;
			if (!"".equals(getQ_serialNoS()))
				sql+=" and a.serialNo>= " + Common.sqlChar(Common.formatFrontZero(getQ_serialNoS(),7)) ;
			if (!"".equals(getQ_serialNoE()))
				sql+=" and a.serialNo <= " + Common.sqlChar(Common.formatFrontZero(getQ_serialNoE(),7)) ;
			if (!"".equals(getQ_propertyName1())){
				sql+=" and a.propertyName1 like " + Common.sqlChar("%" + getQ_propertyName1() + "%");
			}
			if (!"".equals(getQ_deprPark())) {
				sql +=" and a.deprPark = " + Common.sqlChar(getQ_deprPark()) ;
			}
			if (!"".equals(getQ_deprUnit())) {
				sql +=" and a.deprUnit = " + Common.sqlChar(getQ_deprUnit()) ;
			}
			if (!"".equals(getQ_deprUnit1())) {
				sql +=" and a.deprUnit1 = " + Common.sqlChar(getQ_deprUnit1()) ;
			}
			if (!"".equals(getQ_buildFeeCB())) {
				sql +=" and a.buildFeeCB = " + Common.sqlChar(getQ_buildFeeCB()) ;
			}
			if (!"".equals(getQ_deprUnitCB())) {
				sql +=" and a.deprUnitCB = " + Common.sqlChar(getQ_deprUnitCB()) ;
			}
			if (!"".equals(getQ_keepUnit())) {
				sql +=" and a.keepUnit = " + Common.sqlChar(getQ_keepUnit()) ;
			}
			if (!"".equals(getQ_keeper())) {
				sql +=" and a.keeper = " + Common.sqlChar(getQ_keeper()) ;
			}
			ResultSet rs = db.querySQL(sql, true);
			processCurrentPageAttribute(rs);	
			//System.out.println(sql);
			if (getTotalRecord() > 0) {
				int count = getRecordStart();
				int end = getRecordEnd();
				do {
					if (count > end) {
						break;
					}
					counter++;
					String rowArray[]=new String[19];
					rowArray[0]=rs.getString("enterorg");
					rowArray[1]=rs.getString("ownership");
					rowArray[2]=rs.getString("differenceKind");
					rowArray[3]=rs.getString("propertyno");
					rowArray[4]=rs.getString("serialno");
					rowArray[5]=rs.getString("serialno1");
					rowArray[6]=Datetime.changeTaiwanYYMMDD(rs.getString("deprym")+"01", "1").substring(0, 5); 
					rowArray[7]=rs.getString("propertyno")+"-"+rs.getString("serialno"); 
					rowArray[8]=rs.getString("propertyNoName"); 
					rowArray[9]=rs.getString("scrapvalue"); 
					rowArray[10]= Common.formatOtherLimityear(1, rs.getString("limityear")); 
					rowArray[11]=Datetime.changeTaiwanYYMMDD(rs.getString("buydate"), "1"); 
					rowArray[12]=Datetime.changeTaiwanYYMMDD(rs.getString("enterdate"), "1"); 
					rowArray[13]=st._getMoneyFormat(rs.getString("originalbv")); 
					rowArray[14]=st._getMoneyFormat(rs.getString("bookvalue")); 
					rowArray[15]=st._getMoneyFormat(rs.getString("oldNetvalue"));
					rowArray[16]=st._getMoneyFormat(rs.getString("monthdepr"));
					rowArray[17]=st._getMoneyFormat(rs.getString("newnetvalue"));
					rowArray[18]=rs.getString("verify");
					objList.add(rowArray);
					count++;
				} while (rs.next());
			}
			//System.out.println("counter="+counter);
			setStateQueryAllSuccess();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.closeAll();
		}
		return objList;
	}
}