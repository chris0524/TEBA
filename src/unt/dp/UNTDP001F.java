package unt.dp;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import unt.ch.UNTCH_Tools;
import unt.dp.util.UNTDPUtil;
import util.Common;
import util.Database;
import util.Datetime;
import util.NewDateUtil;
import util.SuperBean;
import util.SuperBean2;
import util.View;
import TDlib_Simple.tools.src.StringTools;


/**
 * <br/>程式目的：財產每月折舊計算
 * <br/>程式代號：UNTDP001F
 * <br/>程式日期：
 * <br/>程式作者：Kang Da
 * <br/>--------------------------------------------------------
 * <br/>修改作者　　     修改日期　　　			修改目的
 * <br/>
 * <br/>--------------------------------------------------------
 */
public class UNTDP001F extends SuperBean {

	private Logger logger = Logger.getLogger(this.getClass());
	
	/** 是否使用批次新增處理  , 效能較優  但不易debug**/
	private boolean BATCH_INSET_FLAG = true; // 正式機應為 true
	/** 是否逐筆列印 values **/
	private boolean PRINT_VALUES = false; // 正式機應為 false	
	
	/** tmp開頭 為每次處理的財產主鍵, 用來記錄目前處理到哪 別拿去做其他用途 **/
	private String tmpEnterorg;
	private String tmpOwnership;
	private String tmpDifferencekind;
	private String tmpPropertyno;
	private String tmpSerialno;
	private String tmpReachedYearCalMethod; // 已達年限財產折舊方法:1.不補提 2. 自動提列
	public String getTmpEnterorg() { return checkGet(tmpEnterorg); }
	public void setTmpEnterorg(String tmpEnterorg) { this.tmpEnterorg = checkSet(tmpEnterorg); }
	public String getTmpOwnership() { return checkGet(tmpOwnership); }
	public void setTmpOwnership(String tmpOwnership) { this.tmpOwnership = checkSet(tmpOwnership); }
	public String getTmpDifferencekind() { return checkGet(tmpDifferencekind); }
	public void setTmpDifferencekind(String tmpDifferencekind) { this.tmpDifferencekind = checkSet(tmpDifferencekind); }
	public String getTmpPropertyno() { return checkGet(tmpPropertyno); }
	public void setTmpPropertyno(String tmpPropertyno) { this.tmpPropertyno = checkSet(tmpPropertyno); }
	public String getTmpSerialno() { return checkGet(tmpSerialno); }
	public void setTmpSerialno(String tmpSerialno) { this.tmpSerialno = checkSet(tmpSerialno); }
	public String getTmpReachedYearCalMethod() { return checkGet(tmpReachedYearCalMethod); }
	public void setTmpReachedYearCalMethod(String tmpReachedYearCalMethod) { this.tmpReachedYearCalMethod = checkSet(tmpReachedYearCalMethod); }

	private StringBuilder tmpValues = new StringBuilder();
	private void addTmpVal(Object val) {
		if (tmpValues.length() > 0) {
			tmpValues.append(", ");	
		}
		
		if (val instanceof String) {
			tmpValues.append("'").append(val).append("'");
		} else if (val instanceof Long) {
			tmpValues.append(val);
		} else {
			tmpValues.append(val);
		}
	}
	private String getTmpValues() {
		return tmpValues.toString();
	}
	
	private void clearTmpValues() {
		tmpValues.setLength(0);
	}
	
	
	public String getCurrentInfo() {
		return tmpEnterorg + " " + tmpOwnership + " " + tmpDifferencekind + " " + tmpPropertyno + " " + tmpSerialno;
	}

	//#region fields 
	private String enterOrg;
	private String deprYM;
	private String propertyType;
	private String propertyTypeTemp;
	private String differenceKind;
	private String buyDate;
	private String enterDate;
	private String ownership;
	private String serialNo;
	private String propertyNo;
	private String serialNo1;
	private String deprFrequency;
	private String deprFrequency1;
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
	private String accumDepr;
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
	private String oldpropertyno;
	private String oldserialno;
	private String oldlotno;
	private String oldmonthdepr;
	private String oldmonthdepr1;
	private String apportionEndYM;
	private String verify;
	private String lotNo;
	private String nodeprset;
	//#end 
	
	/** 建物處理筆數 **/
	private int BU_ProcessCNT = 0;
	/** 建物忽略筆數 **/
	private int BU_IgnoreCNT = 0;
	/** 建物成功筆數 **/
	private int BU_SuccessCNT = 0;
	
	private int RF_ProcessCNT = 0;
	private int RF_IgnoreCNT = 0;
	private int RF_SuccessCNT = 0;
	
	private int MP_ProcessCNT = 0;
	private int MP_IgnoreCNT = 0;
	private int MP_SuccessCNT = 0;
	
	private int RT_ProcessCNT = 0;
	private int RT_IgnoreCNT = 0;
	private int RT_SuccessCNT = 0;
	
	/**
	 * 
	 * @param type	: BU | RF | MP | RT
	 * @param action: P 總計量 | I 忽略 | S 成功
	 */
	private void doCount(String type, String action) {
		if ("BU".equals(type)) {
			if ("P".equals(action)) {
				BU_ProcessCNT++;
			} else if ("I".equals(action)) {
				BU_IgnoreCNT++;
			} else if ("S".equals(action)) {
				BU_SuccessCNT++;
			}
		} else if ("RF".equals(type)) {
			if ("P".equals(action)) {
				RF_ProcessCNT++;
			} else if ("I".equals(action)) {
				RF_IgnoreCNT++;
			} else if ("S".equals(action)) {
				RF_SuccessCNT++;
			}
		} else if ("MP".equals(type)) {
			if ("P".equals(action)) {
				MP_ProcessCNT++;
			} else if ("I".equals(action)) {
				MP_IgnoreCNT++;
			} else if ("S".equals(action)) {
				MP_SuccessCNT++;
			}
		} else if ("RT".equals(type)) {
			if ("P".equals(action)) {
				RT_ProcessCNT++;
			} else if ("I".equals(action)) {
				RT_IgnoreCNT++;
			} else if ("S".equals(action)) {
				RT_SuccessCNT++;
			}
		}
	}
	
	/**
	 * 
	 * @param type	: BU | RF | MP | RT	| other:All
	 * @return
	 */
	public String getCNTInfo(String type) {
		if ("BU".equals(type)) {
			return "建物共處理" + BU_ProcessCNT + "筆,成功共" + BU_SuccessCNT + "筆,共忽略" + BU_IgnoreCNT + "筆";
		} else if ("RF".equals(type)) {
			return "土改共處理" + RF_ProcessCNT + "筆,成功共" + RF_SuccessCNT + "筆,共忽略" + RF_IgnoreCNT + "筆";
		} else if ("MP".equals(type)) {
			return "動產共處理" + MP_ProcessCNT + "筆,成功共" + MP_SuccessCNT + "筆,共忽略" + MP_IgnoreCNT + "筆";
		} else if ("RT".equals(type)) {
			return "權利共處理" + RT_ProcessCNT + "筆,成功共" + RT_SuccessCNT + "筆,共忽略" + RT_IgnoreCNT + "筆";
		} else {
			
			int tot_PCNT = BU_ProcessCNT + RF_ProcessCNT + MP_ProcessCNT + RT_ProcessCNT;
			int tot_SCNT = BU_SuccessCNT + RF_SuccessCNT + MP_SuccessCNT + RT_SuccessCNT;
			int tot_ICNT = BU_IgnoreCNT + RF_IgnoreCNT + MP_IgnoreCNT + RT_IgnoreCNT;
			
			StringBuilder msg = new StringBuilder();
			msg.append("建物共處理" + BU_ProcessCNT + "筆,成功共" + BU_SuccessCNT + "筆,共忽略" + BU_IgnoreCNT + "筆").append("\\n");
			msg.append("土改共處理" + RF_ProcessCNT + "筆,成功共" + RF_SuccessCNT + "筆,共忽略" + RF_IgnoreCNT + "筆").append("\\n");
			msg.append("動產共處理" + MP_ProcessCNT + "筆,成功共" + MP_SuccessCNT + "筆,共忽略" + MP_IgnoreCNT + "筆").append("\\n");
			msg.append("權利共處理" + RT_ProcessCNT + "筆,成功共" + RT_SuccessCNT + "筆,共忽略" + RT_IgnoreCNT + "筆").append("\\n");
			msg.append("共處理" + tot_PCNT + "筆,成功共" + tot_SCNT + "筆,共忽略" + tot_ICNT + "筆").append("\\n");
			
			return msg.toString();
		}
	}
	
	//#region getter & setter 
	public String getBuyDate() {	return checkGet(buyDate);}
	public void setBuyDate(String s) {	this.buyDate = checkSet(s);}
	public String getEnterDate() {	return checkGet(enterDate);}
	public void setEnterDate(String s) {	this.enterDate = checkSet(s);}
	public String getEnterOrg(){ return checkGet(enterOrg); }
	public void setEnterOrg(String s){ enterOrg=checkSet(s); }
	public String getDeprYM(){ return checkGet(deprYM); }
	public void setDeprYM(String s){ deprYM=checkSet(s); }
	public String getPropertyType(){ return checkGet(propertyType); }
	public void setPropertyType(String s){ propertyType=checkSet(s); }
	public String getDifferenceKind(){ return checkGet(differenceKind); }
	public void setDifferenceKind(String s){ differenceKind=checkSet(s); }
	public String getOwnership() {	return checkGet(ownership);}
	public void setOwnership(String s) {	this.ownership = checkSet(s);}
	public String getSerialNo() {	return checkGet(serialNo);}
	public void setSerialNo(String s) {	this.serialNo = checkSet(s);}
	public String getPropertyNo() {	return checkGet(propertyNo);}
	public void setPropertyNo(String s) {	this.propertyNo = checkSet(s);}
	public String getSerialNo1() {	return checkGet(serialNo1);}
	public void setSerialNo1(String s) {	this.serialNo1 = checkSet(s);}
	public String getDeprFrequency() {	return checkGet(deprFrequency);}
	public void setDeprFrequency(String s) {	this.deprFrequency = checkSet(s);}
	public String getDeprFrequency1() {	return checkGet(deprFrequency1);}
	public void setDeprFrequency1(String s) {	this.deprFrequency1 = checkSet(s);}
	public String getPropertyKind() {	return checkGet(propertyKind);}
	public void setPropertyKind(String s) {	this.propertyKind = checkSet(s);}
	public String getFundType() {	return checkGet(fundType);}
	public void setFundType(String s) {	this.fundType = checkSet(s);}
	public String getValuable() {	return checkGet(valuable);}
	public void setValuable(String s) {	this.valuable = checkSet(s);}
	public String getDeprMethod() {	return checkGet(deprMethod);}
	public void setDeprMethod(String s) {	this.deprMethod = checkSet(s);}
	public String getBuildFeeCB() {	return checkGet(buildFeeCB);}
	public void setBuildFeeCB(String s) {	this.buildFeeCB = checkSet(s);}
	public String getDeprUnitCB() {	return checkGet(deprUnitCB);}
	public void setDeprUnitCB(String s) {	this.deprUnitCB = checkSet(s);}
	public String getDeprPark() {	return checkGet(deprPark);}
	public void setDeprPark(String s) {	this.deprPark = checkSet(s);}
	public String getDeprUnit() {	return checkGet(deprUnit);}
	public void setDeprUnit(String s) {	this.deprUnit = checkSet(s);}
	public String getDeprUnit1() {	return checkGet(deprUnit1);}
	public void setDeprUnit1(String s) {	this.deprUnit1 = checkSet(s);}
	public String getDeprAccounts() {	return checkGet(deprAccounts);}
	public void setDeprAccounts(String s) {	this.deprAccounts = checkSet(s);}
	public String getDeprPercent() {return checkGet(deprPercent);}
	public void setDeprPercent(String s) {	this.deprPercent = checkSet(s);}
	public String getOriginalBV() {return checkGet(originalBV);}
	public void setOriginalBV(String s) {	this.originalBV = checkSet(s);}
	public String getBookValue() {	return checkGet(bookValue);}
	public void setBookValue(String s) {	this.bookValue = checkSet(s);}
	public String getScrapValue() {	return checkGet(scrapValue);}
	public void setScrapValue(String s) {	this.scrapValue = checkSet(s);}
	public String getDeprAmount() {	return checkGet(deprAmount);}
	public void setDeprAmount(String s) {	this.deprAmount = checkSet(s);}
	public String getApportionMonth() {	return checkGet(apportionMonth);}
	public void setApportionMonth(String s) {	this.apportionMonth = checkSet(s);}
	public String getOldNetValue() {	return checkGet(oldNetValue);}
	public void setOldNetValue(String s) {	this.oldNetValue = checkSet(s);}
	public String getAccumDepr() {	return checkGet(accumDepr);}
	public void setAccumDepr(String s) {	this.accumDepr = checkSet(s);}
	public String getAddAccumDepr() {	return checkGet(addAccumDepr);}
	public void setAddAccumDepr(String s) {	this.addAccumDepr = checkSet(s);}
	public String getReduceAccumDepr() {	return checkGet(reduceAccumDepr);}
	public void setReduceAccumDepr(String s) {	this.reduceAccumDepr = checkSet(s);}
	public String getMonthDepr1() {	return checkGet(monthDepr1);}
	public void setMonthDepr1(String s) {	this.monthDepr1 = checkSet(s);}
	public String getMonthDepr2() {	return checkGet(monthDepr2);}
	public void setMonthDepr2(String s) {	this.monthDepr2 = checkSet(s);}
	public String getMonthDepr() {	return checkGet(monthDepr);}
	public void setMonthDepr(String s) {	this.monthDepr = checkSet(s);}
	public String getNewAccumDepr() {	return checkGet(newAccumDepr);}
	public void setNewAccumDepr(String s) {	this.newAccumDepr = checkSet(s);}
	public String getNewNetValue() {return checkGet(newNetValue);}
	public void setNewNetValue(String s) {	this.newNetValue = checkSet(s);}
	public String getScaledBookValue() {	return checkGet(scaledBookValue);}
	public void setScaledBookValue(String s) {	this.scaledBookValue = checkSet(s);}
	public String getScaledOldNetValue() {	return checkGet(scaledOldNetValue);}
	public void setScaledOldNetValue(String s) {	this.scaledOldNetValue = checkSet(s);}
	public String getScaledOldAccumDepr() {	return checkGet(scaledOldAccumDepr);}
	public void setScaledOldAccumDepr(String s) {	this.scaledOldAccumDepr = checkSet(s);}
	public String getScaledAddAccumDepr() {	return checkGet(scaledAddAccumDepr);}
	public void setScaledAddAccumDepr(String s) {	this.scaledAddAccumDepr = checkSet(s);}
	public String getScaledReduceAccumDepr() {	return checkGet(scaledReduceAccumDepr);}
	public void setScaledReduceAccumDepr(String s) {	this.scaledReduceAccumDepr = checkSet(s);}
	public String getScaledMonthDepr1() {	return checkGet(scaledMonthDepr1);}
	public void setScaledMonthDepr1(String s) {	this.scaledMonthDepr1 = checkSet(s);}
	public String getScaledMonthDepr2() {	return checkGet(scaledMonthDepr2);}
	public void setScaledMonthDepr2(String s) {	this.scaledMonthDepr2 = checkSet(s);}
	public String getScaledMonthDepr() {	return checkGet(scaledMonthDepr);}
	public void setScaledMonthDepr(String s) {	this.scaledMonthDepr = checkSet(s);}
	public String getScaledNewAccumDepr() {	return checkGet(scaledNewAccumDepr);}
	public void setScaledNewAccumDepr(String s) {	this.scaledNewAccumDepr = checkSet(s);}
	public String getScaledNewNetValue() {	return checkGet(scaledNewNetValue);}
	public void setScaledNewNetValue(String s) {	this.scaledNewNetValue = checkSet(s);}
	public String getSourceDate() {	return checkGet(sourceDate);}
	public void setSourceDate(String s) {	this.sourceDate = checkSet(s);}
	public String getPropertyName1() {	return checkGet(propertyName1);}
	public void setPropertyName1(String s) {	this.propertyName1 = checkSet(s);}
	public String getLimitYear() {	return checkGet(limitYear);}
	public void setLimitYear(String s) {	this.limitYear = checkSet(s);}
	public String getBookAmount() {	return checkGet(bookAmount);}
	public void setBookAmount(String s) {	this.bookAmount = checkSet(s);}
	public String getKeepUnit() {	return checkGet(keepUnit);}
	public void setKeepUnit(String s) {	this.keepUnit = checkSet(s);}
	public String getKeeper() {	return checkGet(keeper);}
	public void setKeeper(String s) {	this.keeper = checkSet(s);}
	public String getEscrowOriLimitMonth() {	return checkGet(escrowOriLimitMonth);}
	public void setEscrowOriLimitMonth(String s) {	this.escrowOriLimitMonth = checkSet(s);}
	public String getEscrowOriValue() {	return checkGet(escrowOriValue);}
	public void setEscrowOriValue(String s) {	this.escrowOriValue = checkSet(s);}
	public String getEscrowOriAccumDepr() {	return checkGet(escrowOriAccumDepr);}
	public void setEscrowOriAccumDepr(String s) {	this.escrowOriAccumDepr = checkSet(s);}
	public String getEngineeringNo() {	return checkGet(engineeringNo);}
	public void setEngineeringNo(String s) {	this.engineeringNo = checkSet(s);}
	public String getNotes() {	return checkGet(notes);}
	public void setNotes(String s) {	this.notes = checkSet(s);}
	public String getEditID() {	return checkGet(editID);}
	public void setEditID(String s) {	this.editID = checkSet(s);}
	public String getEditDate() {	return checkGet(editDate);}
	public void setEditDate(String s) {	this.editDate = checkSet(s);}
	public String getEditTime() {	return checkGet(editTime);}
	public void setEditTime(String s) {	this.editTime = checkSet(s);}

	public String getOldpropertyno() {	return checkGet(oldpropertyno);}
	public void setOldpropertyno(String s) {	this.oldpropertyno = checkSet(s);}
	public String getOldserialno() {	return checkGet(oldserialno);}
	public void setOldserialno(String s) {	this.oldserialno = checkSet(s);}
	public String getOldlotno() {	return checkGet(oldlotno);}
	public void setOldlotno(String s) {	this.oldlotno = checkSet(s);}
	public String getOldmonthdepr() {	return checkGet(oldmonthdepr);}
	public void setOldmonthdepr(String s) {	this.oldmonthdepr = checkSet(s);}
	public String getOldmonthdepr1() {	return checkGet(oldmonthdepr1);}
	public void setOldmonthdepr1(String s) {	this.oldmonthdepr1 = checkSet(s);}

	public String getApportionEndYM() {	return checkGet(apportionEndYM);}
	public void setApportionEndYM(String s) {	this.apportionEndYM = checkSet(s);}
	public String getVerify() {	return checkGet(verify);}
	public void setVerify(String s) {	this.verify = checkSet(s);}
	public String getLotNo() {	return checkGet(lotNo);}
	public void setLotNo(String s) {	this.lotNo = checkSet(s);}
	public String getNodeprset() { return checkGet(nodeprset); }
	public void setNodeprset(String nodeprset) { this.nodeprset = checkSet(nodeprset); }
	public String getPropertyTypeTemp() {	return checkGet(propertyTypeTemp);}
	public void setPropertyTypeTemp(String s) {	this.propertyTypeTemp = checkSet(s);}

	//#end 
	
	private Database insertDB = null;
	private Connection insertConn = null;
	private PreparedStatement insertStatment = null;
	
	private Database getInsertDB() {
		if (this.insertDB == null) {
			this.insertDB = new Database();
		}
		return this.insertDB;
	}
	
	private Connection getConn(boolean autoCommit) throws Exception {
		if (this.insertConn == null) {
			this.insertConn = this.getInsertDB().getConnection();
			this.insertConn.setAutoCommit(autoCommit);
		}
			
		return this.insertConn;
	}
	
	private PreparedStatement getInsertPreparedStatment() throws Exception {
		
		if (this.insertStatment == null) {
			Connection conn = this.getConn(!BATCH_INSET_FLAG); // 若為batch inset 模式 則不可auto commit, 反之逐筆(debug) 則需要auto commit
			
			StringBuilder insertSQL = new StringBuilder();
			
			insertSQL.append(" insert into UNTDP_MONTHDEPR (")
					 .append(" enterorg, ownership, differencekind, propertyno, serialno,")	// 1~5
					 .append(" lotno, serialno1, deprym, deprfrequency, verify,")			// 6~10
					 .append(" propertytype, propertykind, fundtype, valuable, deprmethod,")// 11~15
					 .append(" buildfeecb, deprunitcb, deprpark, deprunit, deprunit1,")		// 16~20
					 .append(" depraccounts, deprpercent, originalbv, bookvalue, scrapvalue, depramount,")// 21~26
					 .append(" apportionmonth, oldnetvalue, oldaccumdepr, addaccumdepr, reduceaccumdepr,")	// 27~31
					 .append(" monthdepr1, monthdepr2, monthdepr, newaccumdepr, newnetvalue,")	// 32~36
					 .append(" scaledbookvalue, scaledoldnetvalue, scaledoldaccumdepr, scaledaddaccumdepr, scaledreduceaccumdepr,")	// 37~41
					 .append(" scaledmonthdepr1, scaledmonthdepr2, scaledmonthdepr, scalednewaccumdepr, scalednewnetvalue,") // 42~46
					 .append(" buydate, enterdate, sourcedate, propertyname1, limityear,")	// 47~51
					 .append(" bookamount, keepunit, keeper, escroworilimitmonth, escroworivalue,")	// 52~56
					 .append(" escroworiaccumdepr, engineeringno, notes, editid, editdate,")// 57~61
					 .append(" edittime, oldpropertyno, oldserialno, oldlotno, oldmonthdepr,")//62~66
					 .append(" oldmonthdepr1 ,nodeprset") //67~
					 .append(") values ")
					 .append("(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)")
					 .append("");
			this.insertStatment = conn.prepareStatement(insertSQL.toString());
		}
		return this.insertStatment;
	}
	
	private void addParameter(int paramenterIndex, Object val) throws Exception {
		if (val instanceof String) {
			this.getInsertPreparedStatment().setString(paramenterIndex, (String) val);
			this.addTmpVal(val);
		} else if (val instanceof Long) {
			this.getInsertPreparedStatment().setLong(paramenterIndex, (Long) val);
			this.addTmpVal(val);
		} else if (val instanceof Integer) {
			this.getInsertPreparedStatment().setInt(paramenterIndex, (Integer) val);
			this.addTmpVal(val);
		} else if (val instanceof Double) {
			this.getInsertPreparedStatment().setDouble(paramenterIndex, Common.getNumeric(val));
			this.addTmpVal(val);
		}
	}
	
	private void excutePreparedStatement(String CEdeprYM, long countserialno1, long countdeprfrequency, long oldAccumDeprI, long monthDeprI,
            				  long newAccumDeprI, long newNetValueI, long scaledBookValueI, long scaledOldNetValueI, 
            				  long scaledOldAccumDeprI, long scaledReduceAccumDeprI, long scaledMonthDepr1I, long scaledMonthDepr2I,
            				  long scaledMonthDeprI, long scaledNewAccumDeprI, long scaledNewNetValueI) throws Exception {
		
		PreparedStatement statement = this.getInsertPreparedStatment();
		if (statement != null) {
			this.clearTmpValues();
			// PS: addParameter 目前只有String & Long 有其他型態需要再做處理
			this.addParameter(1, this.getEnterOrg());
			this.addParameter(2, this.getOwnership());
			this.addParameter(3, this.getDifferenceKind());
			this.addParameter(4, this.getPropertyNo());
			this.addParameter(5, this.getSerialNo());
			
			this.addParameter(6, this.getLotNo());
			this.addParameter(7, countserialno1);
			this.addParameter(8, CEdeprYM);
			this.addParameter(9, 0L);
			this.addParameter(10, this.getVerify());
			
			this.addParameter(11, this.getPropertyTypeTemp());
			this.addParameter(12, this.getPropertyKind());
			this.addParameter(13, this.getFundType());
			this.addParameter(14, this.getValuable());
			this.addParameter(15, this.getDeprMethod());

			this.addParameter(16, this.getBuildFeeCB());
			this.addParameter(17, this.getDeprUnitCB());
			this.addParameter(18, this.getDeprPark());
			this.addParameter(19, this.getDeprUnit());
			this.addParameter(20, this.getDeprUnit1());

			this.addParameter(21, this.getDeprAccounts());
			this.addParameter(22, Common.getNumeric(this.getDeprPercent()));
			this.addParameter(23, Common.getLong(this.getOriginalBV()));
			this.addParameter(24, Common.getLong(this.getBookValue()));
			this.addParameter(25, Common.getLong(this.getScrapValue()));

			this.addParameter(26, Common.getLong(this.getDeprAmount()));
			this.addParameter(27, Common.getLong(this.getApportionMonth()));
			this.addParameter(28, Common.getLong(this.getOldNetValue()));
			this.addParameter(29, oldAccumDeprI);
			this.addParameter(30, Common.getLong(this.getAddAccumDepr()));

			this.addParameter(31, this.getReduceAccumDepr());
			this.addParameter(32, Common.getLong(this.getMonthDepr1()));
			this.addParameter(33, Common.getLong(this.getMonthDepr2()));
			this.addParameter(34, monthDeprI);
			this.addParameter(35, newAccumDeprI);

			this.addParameter(36, newNetValueI);
			this.addParameter(37, scaledBookValueI);
			this.addParameter(38, scaledOldNetValueI);
			this.addParameter(39, scaledOldAccumDeprI);
			this.addParameter(40, Common.getLong(this.getScaledAddAccumDepr()));

			this.addParameter(41, scaledReduceAccumDeprI);
			this.addParameter(42, scaledMonthDepr1I);
			this.addParameter(43, scaledMonthDepr2I);
			this.addParameter(44, scaledMonthDeprI);
			this.addParameter(45, scaledNewAccumDeprI);

			this.addParameter(46, scaledNewNetValueI);
			this.addParameter(47, this.getBuyDate());
			this.addParameter(48, this.getEnterDate());
			this.addParameter(49, this.getSourceDate());
			this.addParameter(50, this.getPropertyName1());

			this.addParameter(51, Common.getLong(this.getLimitYear()));
			this.addParameter(52, Common.getLong(this.getBookAmount()));
			this.addParameter(53, this.getKeepUnit());
			this.addParameter(54, this.getKeeper());
			this.addParameter(55, Common.getLong(this.getEscrowOriLimitMonth()));

			this.addParameter(56, Common.getLong(this.getEscrowOriValue()));
			this.addParameter(57, Common.getLong(this.getEscrowOriAccumDepr()));
			this.addParameter(58, this.getEngineeringNo());
			this.addParameter(59, this.getNotes());
			this.addParameter(60, this.getEditID());

			this.addParameter(61, this.getEditDate());
			this.addParameter(62, this.getEditTime());
			this.addParameter(63, this.getOldpropertyno());
			this.addParameter(64, this.getOldserialno());
			this.addParameter(65, this.getOldlotno());

			this.addParameter(66, Common.getLong(this.getOldmonthdepr()));
			this.addParameter(67, Common.getLong(this.getOldmonthdepr1()));
			this.addParameter(68, this.getNodeprset());
			if (PRINT_VALUES) {
				this.logger.info(this.getTmpValues());
			}
			
			if (BATCH_INSET_FLAG) {
				statement.addBatch();
			} else {
				statement.executeUpdate();
			}
		} else {
			throw new Exception("未偵測到Statement");
		}
	}
	
	private void insertRollBack() throws Exception {
		if (this.insertConn != null) {
			this.insertConn.rollback();
		}
	}
	
	private void batchInsertCommit() throws Exception {
		if (BATCH_INSET_FLAG) {
			if (this.insertStatment != null) {
				this.insertStatment.executeBatch();
			}
			
			if (this.insertConn != null) {
				this.insertConn.commit();
			}
		}
	}
	
	private void closeInsertConn() {
		if (this.insertStatment != null) {
			try {
				this.insertStatment.close();
			} catch (Exception e) {
				// ignore
			}
		}
		
		if (this.insertConn != null) {
			try {
				this.insertConn.close();
			} catch (Exception e) {
				// ignore
			}
		}
		
		if (this.insertDB != null) {
			this.insertDB.closeAll();
		}
	}
	
	
	/**
	 * 計算折舊
	 * @throws Exception
	 */
	public void printAddProof() {
		
		try {
			if (new SuperBean2().checkClosingYMfromUNTAC_CLOSINGPT(this.getDeprYM(), this.getEnterOrg(), this.getDifferenceKind())) {
				this.setErrorMsg("折舊年月應為月結年月+1個月!!");
			} else {
				// 查詢機關的 已達年限財產折舊方法
				this.setTmpReachedYearCalMethod(View.getLookupField("select reachedyearcalmethod from SYSCA_ORGARGU where enterorg = " + Common.sqlChar(this.getEnterOrg())));
				
				if ("110".equals(this.getDifferenceKind()) && "10401".equals(this.getDeprYM())) {
					//#region 公務類  折舊年月10501 的特殊情形  
					if ("2".equals(this.getPropertyType())) { 
						// 土改
						this.QueryRFTableForSP();
					} else if("3".equals(this.getPropertyType())) { 
						// 建物
						this.QueryBUTableForSP();
					} else if("4".equals(this.getPropertyType()) || "5".equals(this.getPropertyType()) || "6".equals(this.getPropertyType())) {
						// 動產類
						this.QueryMPTableForSP();
					} else if ("8".equals(this.getPropertyType())) { 
						// 權利
						this.QueryRTTableForSP();
					} else {
						this.QueryMPTableForSP();
						this.QueryBUTableForSP();
						this.QueryRFTableForSP();
						this.QueryRTTableForSP();
					}
					//#end 
				} else {
					//#region 一般情形   根據所選財產大類做折舊   
					if ("2".equals(this.getPropertyType())) { 
						// 土改
						this.QueryRFTable();
					} else if("3".equals(this.getPropertyType())) { 
						// 建物
						this.QueryBUTable();
					} else if("4".equals(this.getPropertyType()) || "5".equals(this.getPropertyType()) || "6".equals(this.getPropertyType())) {
						// 動產類
						this.QueryMPTable();
					} else if ("8".equals(this.getPropertyType())) { 
						// 權利
						this.QueryRTTable();
					} else {
						this.QueryMPTable();
						this.QueryBUTable();
						this.QueryRFTable();
						this.QueryRTTable();
					}
					//#end 
				}
				this.setErrorMsg("折舊計算成功！\\n" + this.getCNTInfo("all"));
				
			}
		} catch (Exception e) {
			this.setErrorMsg("折舊計算發生錯誤:" + this.getCurrentInfo());
			this.logger.error("折舊計算發生錯誤:" + this.getCurrentInfo(), e);
			//throw e;
		} finally {
			this.closeInsertConn();
		}
	}
	
	/**
	 *  土改折舊 查詢SQL  TODO 非公務類的可共用此隻取得SQL
	 * @param deprYMtoCE    : 折舊年月轉西元   ex:折舊年月10409  -> 201509
	 * @return
	 */
	private String getQueryRFSQL(String deprYMtoCE) {
		StringBuilder sql = new StringBuilder();
		sql.append(" select a.enterorg ,a.ownership ,a.differencekind ,a.propertyno ,a.serialno ")
		   .append(",(select max(isnull(serialno1,0))+1 from UNTDP_MONTHDEPR m where a.enterorg=m.enterorg  and a.ownership=m.ownership and a.differencekind=m.differencekind and a.propertyno=m.propertyno and a.serialno=m.serialno) as serialNo1")
		   .append(", 0 as deprFrequency")
		   .append(",a.propertykind ,a.fundtype ,a.valuable ,a.deprmethod,a.buildfeecb,a.deprunitcb,a.deprpark,a.deprunit,a.deprunit1")
		   .append(",a.depraccounts,isnull(a.originalbv,0) as originalbv,isnull(a.bookvalue,0) as bookvalue,isnull(a.scrapvalue,0) as scrapvalue,isnull(a.depramount,0) as depramount,isnull(a.apportionmonth,0) as apportionmonth")
		   .append(",isnull(a.netvalue,0) as netvalue,isnull(a.accumdepr,0) as accumDepr,a.apportionendym")
		   .append(",isnull((select sum(isnull(b.reduceaccumdepr,0))   from UNTRF_ATTACHMENT a, UNTRF_ADJUSTDETAIL b where b.verify = 'Y'  and b.adjustdate="+deprYMtoCE+" and a.enterorg = b.enterorg and a.ownership = b.ownership and a.differencekind = b.differencekind and a.propertyno = b.propertyno and a.serialno = b.serialno) +(select isnull(b.oldaccumdepr,0)  from UNTRF_ATTACHMENT a, UNTRF_REDUCEDETAIL b where b.verify = 'Y'   and b.reducedate=" + deprYMtoCE + " and a.enterorg = b.enterorg   and a.ownership = b.ownership   and a.differencekind = b.differencekind   and a.propertyno = b.propertyno  and a.serialno = b.serialno),0) as reduceAccumDepr")
		   .append(",isnull(a.monthdepr1,0) as monthdepr1,isnull(a.monthdepr,0) as monthdepr,a.buydate,a.enterdate,a.sourcedate,a.propertyname1")
		   .append(",isnull(a.otherlimityear, (a.originallimityear * 12))  as limitYear")
		   .append(",a.keepunit,a.keeper,a.originalapportionmonth,isnull(a.escroworivalue,0) as escroworivalue,isnull(a.escroworiaccumdepr,0) as escroworiaccumdepr,a.engineeringno,a.notes,a.editid,a.editdate,a.edittime")
		   .append(",a.oldpropertyno,a.oldserialno,a.oldlotno,a.nodeprset")
		   .append(" from UNTRF_ATTACHMENT a where 1=1")
		   .append(" and a.verify='Y' ")
		   .append(" and a.datastate='1' ")
		   .append(" and a.enterdate is not null ")
		   .append(" and a.valuable ='N' ")
		   .append("");
		
		if (!"".equals(this.getEnterOrg())) {
			sql.append(" and a.enterorg = ").append(Common.sqlChar(this.getEnterOrg()));
		}
		
		if (!"".equals(this.getDifferenceKind())) {
			sql.append(" and a.differencekind = ").append(Common.sqlChar(this.getDifferenceKind()));
		}
		return sql.toString();
	}
	
	/**
	 * 取得需要折舊的建物的查詢SQL   TODO 可與 非公務類的折舊SQL組成 一同共用  減少維護成本
	 * @param deprYMtoCE    : 折舊年月轉西元   ex:折舊年月10409  -> 201509
	 * @return  
	 */
	private String getQueryBUSQL(String deprYMtoCE) {
		StringBuilder sql = new StringBuilder();
		sql.append(" select a.enterorg ,a.ownership ,a.differencekind ,a.propertyno ,a.serialno ")
		   .append(",(select max(isnull(serialno1,0))+1 from UNTDP_MONTHDEPR m where a.enterorg=m.enterorg  and a.ownership=m.ownership and a.differencekind=m.differencekind and a.propertyno=m.propertyno and a.serialno=m.serialno) as serialNo1")
		   .append(",0  as deprFrequency")
		   .append(",a.propertykind ,a.fundtype ,a.valuable ,a.deprmethod,a.buildfeecb,a.deprunitcb,a.deprpark,a.deprunit,a.deprunit1")
		   .append(",a.depraccounts,isnull(a.originalbv,0) as originalbv,isnull(a.bookvalue,0) as bookvalue,isnull(a.scrapvalue,0) as scrapvalue,isnull(a.depramount,0) as depramount,isnull(a.apportionmonth,0) as apportionmonth")
		   .append(",isnull(a.netvalue,0) as netvalue,isnull(a.accumdepr,0) as accumDepr,a.apportionendym")
		   .append(",isnull((select sum(isnull(b.reduceaccumdepr,0))   from UNTBU_BUILDING a, UNTBU_ADJUSTDETAIL b where b.verify = 'Y'  and b.adjustdate="+deprYMtoCE+" and a.enterorg = b.enterorg and a.ownership = b.ownership and a.differencekind = b.differencekind and a.propertyno = b.propertyno and a.serialno = b.serialno) +(select isnull(b.oldaccumdepr,0)  from UNTBU_BUILDING a, UNTBU_REDUCEDETAIL b where b.verify = 'Y'   and b.reducedate=" + deprYMtoCE + " and a.enterorg = b.enterorg   and a.ownership = b.ownership   and a.differencekind = b.differencekind   and a.propertyno = b.propertyno  and a.serialno = b.serialno),0) as reduceAccumDepr")
		   .append(",isnull(a.monthdepr1,0) as monthdepr1,isnull(a.monthdepr,0) as monthdepr,a.buydate,a.enterdate,a.sourcedate,a.propertyname1")
		   .append(",isnull(a.otherlimityear, (a.originallimityear * 12))  as limitYear")
		   .append(",a.keepunit,a.keeper,a.originalapportionmonth,isnull(a.escroworivalue,0) as escroworivalue,isnull(a.escroworiaccumdepr,0) as escroworiaccumdepr,a.engineeringno,a.notes,a.editid,a.editdate,a.edittime")
		   .append(",a.oldpropertyno,a.oldserialno,a.oldlotno,a.nodeprset")
		   .append(" from UNTBU_BUILDING a where 1=1")
		   .append(" and a.verify='Y' ")
		   .append(" and a.datastate='1' ")
		   .append(" and a.enterdate is not null ")
		   .append(" and a.valuable = 'N' ")
		   .append("");
		
		if (!"".equals(this.getEnterOrg())) {
			sql.append(" and a.enterorg = ").append(Common.sqlChar(this.getEnterOrg()));
		}
		
		if (!"".equals(this.getDifferenceKind())) {
			sql.append(" and a.differencekind = ").append(Common.sqlChar(this.getDifferenceKind()));
		}
		return sql.toString();
	}
	
	/**
	 * 查詢 動產需要折舊的 SQL  TODO  非公務類也共用此隻  減少維護成本
	 * @param deprYMtoCE    : 折舊年月轉西元   ex:折舊年月10409  -> 201509
	 * @return  
	 */
	private String getQueryMPSQL(String deprYMtoCE) {
		StringBuilder sql = new StringBuilder();
		sql.append("select b.enterorg ,b.ownership ,b.differencekind ,b.propertyno ,b.serialno ,b.lotno")
		   .append(",(select max(isnull(serialno1,0))+1 from UNTDP_MONTHDEPR m where b.enterorg=m.enterorg  and b.ownership=m.ownership and b.differencekind=m.differencekind and b.propertyno=m.propertyno and b.serialno=m.serialno) as serialNo1")
		   .append(",0  as deprFrequency")
		   .append(",a.propertykind ,a.fundtype ,a.valuable ,b.deprmethod,b.buildfeecb,b.deprunitcb,b.deprpark,b.deprunit,b.deprunit1")
		   .append(",b.depraccounts,isnull(b.originalbv,0) as originalbv,isnull(b.bookvalue,0) as bookvalue,isnull(b.scrapvalue,0) as scrapvalue,isnull(b.depramount,0) as depramount,isnull(b.apportionmonth,0) as apportionmonth")
		   .append(",isnull(b.netvalue,0) as netvalue,isnull(b.accumdepr,0) as accumDepr,b.apportionendym")
		   .append(",isnull((select sum(isnull(b.reduceaccumdepr,0))   from UNTMP_MOVABLEDETAIL a, UNTMP_ADJUSTDETAIL b where b.verify = 'Y'  and b.adjustdate="+deprYMtoCE+" and a.enterorg = b.enterorg and a.ownership = b.ownership and a.differencekind = b.differencekind and a.propertyno = b.propertyno and a.serialno = b.serialno) +(select isnull(b.oldaccumdepr,0)  from UNTMP_MOVABLEDETAIL a, UNTMP_REDUCEDETAIL b where b.verify = 'Y'   and b.reducedate="+deprYMtoCE+" and a.enterorg = b.enterorg   and a.ownership = b.ownership   and a.differencekind = b.differencekind   and a.propertyno = b.propertyno  and a.serialno = b.serialno),0) as reduceAccumDepr")
		   .append(",isnull(b.monthdepr1,0) as monthdepr1,isnull(b.monthdepr,0) as monthdepr,a.buydate,b.enterdate,a.sourcedate,b.propertyname1")
		   .append(", 1 as bookamount,isnull(b.otherlimityear,b.originallimityear)  as limitYear")
		   .append(",b.keepunit,b.keeper,b.otherlimityear,isnull(a.escroworivalue,0) as escroworivalue,isnull(a.escroworiaccumdepr,0) as escroworiaccumdepr,b.engineeringno,a.notes,a.editid,a.editdate,a.edittime")
		   .append(",b.oldpropertyno,b.oldserialno,b.oldlotno,b.nodeprset")
		   .append(" from UNTMP_MOVABLE a ")
		   .append(" left join UNTMP_MOVABLEDETAIL b on a.enterorg=b.enterorg and a.ownership=b.ownership and a.differencekind=b.differencekind and a.propertyno=b.propertyno and a.lotno=b.lotno ")
		   .append(" where 1=1 ")
		   .append(" and b.verify='Y' ")
		   .append(" and b.datastate='1' ")
		   .append(" and b.enterdate is not null ")
		   .append(" and a.valuable = 'N' ");
		
		
		if ("4".equals(this.getPropertyType())) {
			sql.append(" and substring(b.propertyno,1,1)='3' ");
		} else if ("5".equals(this.getPropertyType())) {
			sql.append(" and substring(b.propertyno,1,1)='4' ");
		} else if ("6".equals(this.getPropertyType())) {
			sql.append(" and substring(b.propertyno,1,1)='5' ");
		}
		
		if (!"".equals(this.getEnterOrg())) {
			sql.append(" and a.enterorg = ").append(Common.sqlChar(this.getEnterOrg()));
		}
		
		if (!"".equals(this.getDifferenceKind())) {
			sql.append(" and a.differencekind = ").append(Common.sqlChar(this.getDifferenceKind()));
		}
		
		sql.append(" order by b.propertyno, b.serialno ");
		return sql.toString();
	}
	
	/**
	 * 取得查詢  需要折舊的權利主檔 SQL
	 * @param deprYMtoCE    : 折舊年月轉西元   ex:折舊年月10409  -> 201509
	 * @return
	 */
	private String getQueryRTSQL(String deprYMtoCE) {
		StringBuilder querySQL = new StringBuilder();
		querySQL.append(" select a.enterorg, a.ownership, a.differencekind, a.propertyno, a.serialno, a.propertykind")
				// serialno1  折舊次序  //TODO 這個不能這樣寫吧
				.append(" ,(select max(isnull(serialno1, 0)) + 1 from UNTDP_MONTHDEPR m where a.enterorg = m.enterorg and a.ownership = m.ownership and a.differencekind = m.differencekind and a.propertyno = m.propertyno and a.serialno = m.serialno) as serialno1")
				// deprFrequency 已提折舊次數   同一筆財產同一個折舊年月以1次計算
				.append(" ,0 as deprfrequency")
				// 權利皆非珍貴財產，故權利無珍貴財產欄位
				.append(" ,'N' as valuable")
				.append(" ,a.fundtype,a.deprmethod ,a.buildfeecb ,a.deprunitcb ,a.deprpark ,a.deprunit ,a.deprunit1 ,a.depraccounts")
				.append(" ,isnull(a.originalbv, 0) as originalbv ,isnull(a.bookvalue, 0) as bookvalue")
				.append(" ,isnull(a.scrapvalue, 0) as scrapvalue, isnull(a.depramount, 0) as depramount")
				.append(" ,isnull(a.apportionmonth, 0) as apportionmonth, isnull(a.netvalue, 0) as netvalue")
				.append(" ,isnull(a.accumdepr, 0) as accumdepr ,a.apportionendym")
				//#region 本月累計折舊減少      
				.append(" ,isnull(")
				.append(" (select sum(isnull(b.reduceaccumdepr, 0)) from UNTRT_ADDPROOF a, UNTRT_ADJUSTPROOF b where b.verify = 'Y' and b.adjustdate=").append(deprYMtoCE).append(" and a.enterorg = b.enterorg and a.ownership = b.ownership and a.differencekind = b.differencekind and a.propertyno = b.propertyno and a.serialno = b.serialno)")
				.append(" + ")
				.append(" (select isnull(b.oldaccumdepr, 0) from UNTRT_ADDPROOF a, UNTRT_REDUCEPROOF b where b.verify = 'Y' and b.reducedate=").append(deprYMtoCE).append(" and a.enterorg = b.enterorg and a.ownership = b.ownership and a.differencekind = b.differencekind and a.propertyno = b.propertyno and a.serialno = b.serialno)")
				.append(" ,0) as reduceaccumdepr")
				//#end 
				.append(" ,isnull(a.monthdepr1, 0) as monthdepr1 ,isnull(a.monthdepr,0) as monthdepr")
				.append(" ,a.buydate ,a.enterdate ,a.sourcedate ,a.propertyname1")
				.append(" ,isnull(a.otherlimityear, (a.originallimityear * 12)) as limityear")
				.append(" ,a.keepunit ,a.keeper ,a.originalapportionmonth")
				.append(" ,isnull(a.escroworivalue, 0) as escroworivalue, isnull(a.escroworiaccumdepr, 0) as escroworiaccumdepr")
				.append(" ,a.engineeringno ,a.notes ,a.editid ,a.editdate ,a.edittime")
				.append(" ,a.oldpropertyno ,a.oldserialno ,a.oldlotno ,a.nodeprset, a.individualsetdepr, a.verifyym")
		        .append(" from UNTRT_ADDPROOF a where 1=1 ")
		        .append(" and a.verify = 'Y' ") //已入帳
		        .append(" and a.enterdate is not null ") //有入帳日期
		        .append(" and a.datastate='1' ") //現存財產
		        .append("");
		if (!"".equals(this.getEnterOrg())) {
			querySQL.append(" and a.enterorg = ").append(Common.sqlChar(this.getEnterOrg()));
		}
		if (!"".equals(this.getDifferenceKind())) {
			querySQL.append(" and a.differencekind = ").append(Common.sqlChar(this.getDifferenceKind()));
		}
		return querySQL.toString();
	}
	
	/**
	 * <b>情況零：本月不折舊資料 (原本屬於情況四)</b>
	 * <br/>折舊方法: 不必折舊  
	 * <br/>or
	 * <br/>不折舊設定 = Y
	 * @param  nodeprset  ：   不折舊設定
	 * @return
	 */
	private boolean isSituationZero(String nodeprset) {
		return "01".equals(this.getDeprMethod()) || "Y".equals(nodeprset);
	}
	
	/**
	 * 是否為 情況一：上月購入，上月入帳，本月開始提列折舊
	 * <br/>例如10305購入，10305入帳，10306開始提列折舊，提列1個月折舊
	 * @param previousDeprYM_to_YYYYMM : 畫面deprYM - 1 個月   (西元年)
	 * @param nodeprset                : 不折舊設定
	 * @return
	 */
	private boolean isSituationOne(String previousDeprYM_to_YYYYMM, String nodeprset) {
		return previousDeprYM_to_YYYYMM.equals(this.getBuyDate().substring(0, 6)) &&     // 上月購入
			   previousDeprYM_to_YYYYMM.equals(this.getEnterDate().substring(0, 6)) &&   // 上月入帳
			   "N".equals(nodeprset);                                                    // 不折舊設定 = N
	}
	
	/**
	 * 是否為情況二： 以前年月購入(不含本月)，本月入帳，本月補提折舊
	 * <br/>例如10105購入，10306入帳，10306補提10106～min(10306, apportionEndYM攤提年限截止年月(西元年))之折舊。
	 * @param ceDeprYM ： 畫面年月轉西元前六碼   ex:  ul._transToCE_Year(obj.getDeprYM()).substring(0, 6)
	 * @param nodeprset： 不折舊設定
	 * @return
	 */
	private boolean isSituationTwo(String deprYM_to_YYYYMM, String nodeprset) {
		return Integer.parseInt(this.getBuyDate().substring(0, 6)) < Integer.parseInt(deprYM_to_YYYYMM) &&  // 以前年月購入
			   this.getEnterDate().substring(0, 6).equals(deprYM_to_YYYYMM) &&                              // 本月入帳
			   "N".equals(nodeprset);                                                                       // 不折舊設定 = N
	}
	
	/**
	 * 是否為情況三：以前年月購入（不含本月及上月），以前年月入帳（不含本月），
	 * 且 (
	 * 		未逾「攤提年限截止年月」
	 *  	或  
	 *  	 ( 
	 *  	         已逾「攤提年限截止年月」 
	 *  	         且  淨值大於殘值 
	 *         且  機關設定已達年限財產折舊方法為自動提列(2) 
	 *       )
	 *   )
	 * 則提列1個月折舊(或殘值-淨值)
	 * <br/> 例如10304購入，10304入帳；10304購入，10305入帳。
	 * @param previousDeprYM_to_YYYYMM  ：    畫面deprYM - 1 個月  (西元年)
	 * @param deprYM_to_YYYYMM          ：    畫面deprym 轉西元年前6碼        ex:畫面輸入10408 -> 201508 
	 * @param nodeprset                 ：    不折舊設定
	 * @return
	 */
	private boolean isSituationThree(String previousDeprYM_to_YYYYMM, String deprYM_to_YYYYMM, String nodeprset) {
		return Integer.parseInt(this.getBuyDate().substring(0, 6)) < Integer.parseInt(previousDeprYM_to_YYYYMM)  // 以前年月購入(不含本月及上月)
			   && Integer.parseInt(this.getEnterDate().substring(0, 6)) < Integer.parseInt(deprYM_to_YYYYMM)     // 以前年月入帳(不含本月)
			   && (
					   Integer.parseInt(this.getApportionEndYM()) >= Integer.parseInt(deprYM_to_YYYYMM)          // 未逾 「攤提年限截止年月」
					   ||
					   (
							   Integer.parseInt(this.getApportionEndYM()) < Integer.parseInt(deprYM_to_YYYYMM) // 已逾「攤提年限截止年月」   
							   && Common.getLong(this.getOldNetValue()) > Common.getLong(this.getScrapValue()) // 淨值 > 殘值
							   && "2".equals(this.getTmpReachedYearCalMethod()) 							   // 已達年限財產折舊方法: 2. 自動提列
					   )
				  )
			   && "N".equals(nodeprset);
	}
	
	/**
	 * <b>情況四：本月不折舊資料</b>
	 * <br/>原值(bookValue)=殘值(scrapValue) + 累計折舊(accumDepr)
	 * <br/>or
	 * <br/>畫面.deprYM.轉西元年   >  攤提年限截止年月(西元年)(apportionEndYM) 
	 * <br/>or
	 * <br/>(購置日期(西元年)(buyDate).年月 = 畫面.deprYM.轉西元年 and 入帳日期(西元年)(enterDate).年月 =畫面.deprYM.轉西元年)
	 * @param  nodeprset  ：   不折舊設定
	 * @return
	 */
	private boolean isSituationFour(String deprYM_to_YYYYMM) {
		return // 原值(bookValue)=殘值(scrapValue) + 累計折舊(accumDepr)
			   Common.getLong(this.getBookValue()) == Common.getLong(this.getScrapValue()) + Common.getLong(this.getAccumDepr()) ||
			   // 畫面.deprYM.轉西元年   >  攤提年限截止年月(西元年)(apportionEndYM) 
			   Integer.parseInt(deprYM_to_YYYYMM) > Integer.parseInt(this.getApportionEndYM()) ||
			   (// 購置日期(西元年)(buyDate).年月 = 畫面.deprYM.轉西元年 and 入帳日期(西元年)(enterDate).年月 =畫面.deprYM.轉西元年
				Integer.parseInt(deprYM_to_YYYYMM) == Integer.parseInt(this.getBuyDate().substring(0, 6)) 
				&&
				Integer.parseInt(deprYM_to_YYYYMM) == Integer.parseInt(this.getEnterDate().substring(0, 6))
			   );
	}
	
	/**
	 * 土改折舊計算
	 * @throws Exception
	 */
	private void QueryRFTable() throws Exception {
		UNTCH_Tools ul = new UNTCH_Tools();
		UNTDP001F obj = this;
		Database db = new Database();
		Database db2 = new Database();
		ResultSet rs = null;
		StringTools st = new StringTools();
		/** 畫面deprym-1個月  yyyymm **/
		String TempDate = ul._transToCE_Year(Datetime.getDateAdd("m", -1, this.getDeprYM() + "01").substring(0, 5));
		String ROCdeprYM = getDeprYM();
		/** 畫面deprym 轉西元年前6碼        ex:畫面輸入10408 -> 201508  **/
		String deprYM_to_YYYYMM = ul._transToCE_Year(deprYM).substring(0, 6);
		try {
			String sql = " select a.enterorg ,a.ownership ,a.differencekind ,a.propertyno ,a.serialno ," +
					     " (select max(isnull(serialno1,0))+1 from UNTDP_MONTHDEPR m where a.enterorg=m.enterorg  and a.ownership=m.ownership and a.differencekind=m.differencekind and a.propertyno=m.propertyno and a.serialno=m.serialno) as serialNo1,"+
					     " 0  as deprFrequency," +			   
					     " a.propertykind ,a.fundtype ,a.valuable ,a.deprmethod,a.buildfeecb,a.deprunitcb,a.deprpark,a.deprunit,a.deprunit1," +
					     " a.depraccounts,isnull(a.originalbv,0) as originalbv,isnull(a.bookvalue,0) as bookvalue,isnull(a.scrapvalue,0) as scrapvalue,isnull(a.depramount,0) as depramount,isnull(a.apportionmonth,0) as apportionmonth," +
					     " isnull(a.netvalue,0) as netvalue,isnull(a.accumdepr,0) as accumDepr,a.apportionendym," +
					     " isnull((select sum(isnull(b.reduceaccumdepr,0))   from UNTRF_ATTACHMENT a, UNTRF_ADJUSTDETAIL b where b.verify = 'Y'  and b.adjustdate="+Common.sqlChar(ul._transToCE_Year(getDeprYM())) +" and a.enterorg = b.enterorg and a.ownership = b.ownership and a.differencekind = b.differencekind and a.propertyno = b.propertyno and a.serialno = b.serialno) +(select isnull(b.oldaccumdepr,0)  from UNTRF_ATTACHMENT a, UNTRF_REDUCEDETAIL b where b.verify = 'Y'   and b.reducedate="+Common.sqlChar(ul._transToCE_Year(getDeprYM())) +" and a.enterorg = b.enterorg   and a.ownership = b.ownership   and a.differencekind = b.differencekind   and a.propertyno = b.propertyno  and a.serialno = b.serialno),0) as reduceAccumDepr," +
					     " isnull(a.monthdepr1,0) as monthdepr1,isnull(a.monthdepr,0) as monthdepr,a.buydate,a.enterdate,a.sourcedate,a.propertyname1, " +
					     " isnull(a.otherlimityear, (a.originallimityear * 12))  as limitYear ," +
					     " a.keepunit,a.keeper,a.originalapportionmonth,isnull(a.escroworivalue,0) as escroworivalue,isnull(a.escroworiaccumdepr,0) as escroworiaccumdepr,a.engineeringno,a.notes,a.editid,a.editdate,a.edittime," +
					     " a.oldpropertyno,a.oldserialno,a.oldlotno,a.nodeprset, a.individualsetdepr, a.verifyym" +
					     " from UNTRF_ATTACHMENT a" +
					     " left join SYSPK_PROPERTYCODE p on p.enterorg in (a.enterorg,'000000000A') and a.propertyno=p.propertyno" +
					     " where 1=1 " +
					     " and a.verify='Y' " +
					     " and a.datastate='1' " +
					     " and a.valuable='N' " +
						 " and a.enterdate is not null "+
						 "";
			if (!"".equals(getEnterOrg())) {
				sql +=" and a.enterorg = " + Common.sqlChar(getEnterOrg()) ;
			}
			if (!"".equals(getDifferenceKind())) { 
				sql +=" and a.differencekind = " + Common.sqlChar(getDifferenceKind()) ;
			}
			//System.out.println("@@@@@@@@@@@@@@@@@@==>"+sql);
			rs = db.querySQL_NoChange(sql);
			while (rs.next()) {
				//if(rs.getString("buydate").substring(0, 5))
				doCount("RF", "P");
				
				this.setTmpEnterorg(checkGet(rs.getString("enterorg")));
				this.setTmpOwnership(checkGet(rs.getString("ownership")));
				this.setTmpDifferencekind(checkGet(rs.getString("differencekind")));
				this.setTmpPropertyno(checkGet(rs.getString("propertyno")));
				this.setTmpSerialno(checkGet(rs.getString("serialno")));
				
				obj.setEnterOrg(checkGet(rs.getString("enterorg")));
				obj.setOwnership(checkGet(rs.getString("ownership")));
				obj.setDifferenceKind(checkGet(rs.getString("differencekind")));
				obj.setPropertyNo(checkGet(rs.getString("propertyno")));
				obj.setSerialNo(checkGet(rs.getString("serialno")));
				obj.setSerialNo1((null==rs.getString("serialno1")?"1":rs.getString("serialno1")));
				obj.setDeprFrequency((null==rs.getString("deprFrequency")?"0":rs.getString("deprFrequency")));		
				//obj.setDeprFrequency1(rs.getString("deprFrequency1"));
				obj.setPropertyKind(checkGet(rs.getString("propertykind")));
				obj.setFundType(checkGet(rs.getString("fundtype")));
				obj.setValuable(checkGet(rs.getString("valuable")));
				obj.setDeprMethod(checkGet(rs.getString("deprmethod")));
				obj.setBuildFeeCB(checkGet(rs.getString("buildfeecb")));
				obj.setDeprUnitCB(checkGet(rs.getString("deprunitcb")));
				obj.setDeprPark(checkGet(rs.getString("deprpark")));
				obj.setDeprUnit(checkGet(rs.getString("deprunit")));
				obj.setDeprUnit1(checkGet(rs.getString("deprunit1")));
				obj.setDeprAccounts(checkGet(rs.getString("depraccounts")));
				obj.setOriginalBV(checkGet(rs.getString("originalbv")));
				obj.setBookValue(checkGet(rs.getString("bookvalue")));
				obj.setScrapValue(checkGet(rs.getString("scrapvalue")));
				obj.setDeprAmount(checkGet(rs.getString("depramount")));
				obj.setApportionMonth(checkGet(rs.getString("apportionmonth")));
				obj.setOldNetValue(checkGet(rs.getString("netvalue")));
				obj.setAccumDepr(checkGet(rs.getString("accumDepr")));
				obj.setReduceAccumDepr(checkGet(rs.getString("reduceAccumDepr")));
				obj.setMonthDepr1(rs.getString("monthdepr1"));
				obj.setMonthDepr(checkGet(rs.getString("monthdepr")));
				obj.setBuyDate(checkGet(rs.getString("buydate")));
				obj.setEnterDate(checkGet(rs.getString("enterdate")));
				obj.setSourceDate(checkGet(rs.getString("sourcedate")));
				obj.setPropertyName1(checkGet(rs.getString("propertyname1")));
				obj.setLimitYear(null==rs.getString("limitYear")?"null":rs.getString("limitYear"));
				obj.setDeprPercent("100");
				obj.setScaledAddAccumDepr("0");
				obj.setKeeper(checkGet(rs.getString("keeper")));
				obj.setKeepUnit(checkGet(rs.getString("keepunit")));
				obj.setOldpropertyno(checkGet(rs.getString("oldpropertyno")));
				obj.setOldserialno(checkGet(rs.getString("oldserialno")));
				obj.setOldlotno(checkGet(rs.getString("oldlotno")));
				obj.setOldmonthdepr(checkGet(rs.getString("monthdepr")));
				obj.setOldmonthdepr1(checkGet(rs.getString("monthdepr1")));

				if ("111".equals(checkGet(rs.getString("differencekind"))) || "113".equals(checkGet(rs.getString("differencekind")))) {
					obj.setEscrowOriLimitMonth(checkGet(rs.getString("originalapportionmonth")));
				} else {
					obj.setEscrowOriLimitMonth("null");
				}	
				obj.setEscrowOriValue(checkGet(rs.getString("escroworivalue")));
				obj.setEscrowOriAccumDepr(checkGet(rs.getString("escrowOriaccumdepr")));
				obj.setEngineeringNo(checkGet(rs.getString("engineeringno")));
				obj.setNotes(checkGet(rs.getString("notes")));
				obj.setEditID(checkGet(rs.getString("editid")));
				obj.setEditDate(checkGet(rs.getString("editdate")));
				obj.setEditTime(checkGet(rs.getString("edittime")));
				obj.setApportionEndYM(checkGet(rs.getString("apportionendym")));
				obj.setAddAccumDepr("0");
				obj.setBookAmount("1");
				obj.setPropertyTypeTemp("2");
				obj.setVerify("N");
				obj.setLotNo("");
				obj.setNodeprset(rs.getString("nodeprset"));

				String nodeprset = Common.get(rs.getString("nodeprset"));
				
				// TODO 情況一 ~ 情況四 利用method 回傳布林  不然這樣太難閱讀與debug
				if (this.isSituationZero(nodeprset)) {
					//#region 情況零 
					obj.setMonthDepr1("0");
					obj.setMonthDepr2("0");
					insertData(db2);
					doCount("RF", "S");
					//#end 
				}
				//情況一上月購入，上月入帳，本月開始提列折舊
				else if((TempDate.equals(obj.getBuyDate().substring(0, 6)) && TempDate.equals(obj.getEnterDate().substring(0, 6)) && "N".equals(checkGet(rs.getString("nodeprset")))))
				{
					if(ul._transToCE_Year(obj.getDeprYM()).substring(0, 6) == obj.getApportionEndYM()){
						obj.setMonthDepr1(checkGet(rs.getString("monthdepr1")));
					} else {
						obj.setMonthDepr1(checkGet(rs.getString("monthdepr")));
					}
					obj.setMonthDepr2("0");
					insertData(db2);
					doCount("RF", "S");
				}
				//情況二以前年月購入(不含本月)，本月入帳，本月補提折舊
				else if (Integer.parseInt(obj.getBuyDate().substring(0, 6)) < Integer.parseInt(ul._transToCE_Year(obj.getDeprYM()).substring(0, 6)) && obj.getEnterDate().substring(0, 6).equals(ul._transToCE_Year(obj.getDeprYM()).substring(0, 6)) && "N".equals(checkGet(rs.getString("nodeprset"))) && !"B".equals(rs.getString("individualsetdepr")))
				{
					// individualsetdepr為自行設定累計折舊，A為僅提列當月折舊
					if ("A".equals(rs.getString("individualsetdepr"))) {
						long leftvalue = Common.getLong(this.getOldNetValue()) - Common.getLong(this.getScrapValue()); //剩餘價值
						if (Common.getInt(deprYM_to_YYYYMM) == Common.getInt(this.getApportionEndYM())) { //當月為最後一個月
							obj.setMonthDepr1(checkGet(rs.getString("monthdepr1")));
						} else {
							if (Common.getInt(deprYM_to_YYYYMM) > Common.getInt(this.getApportionEndYM())) { //已折完
								// 已達年限自動提列折舊 : 淨值 - 殘值
								obj.setMonthDepr1(Common.get(leftvalue));
							} else { //正常折舊
								obj.setMonthDepr1(checkGet(rs.getString("monthdepr")));
							}
						}
						// 如果攤提金額大於剩餘金額，僅折剩餘金額
						if (Common.getLong(obj.getMonthDepr1()) > leftvalue) {
							obj.setMonthDepr1(Common.get(leftvalue));
						}
						
						obj.setMonthDepr2("0"); 
						insertData(db2);
						doCount("RF", "S");
					} else {
						String ROCBuyDate = ul._transToROC_Year(obj.getBuyDate().substring(0, 6));
						String ROCApportionEndYM = ul._transToROC_Year(obj.getApportionEndYM().substring(0, 6));
						int TempThisYear = Datetime.BetweenTwoMonth(ROCdeprYM.substring(0, 3) + "01", ROCdeprYM) + 1;
						int TempThisYear2 = Datetime.BetweenTwoMonth(ROCApportionEndYM.substring(0, 3) + "01", ROCApportionEndYM) + 1;
						int TempLastYear = Datetime.BetweenTwoMonth(ROCBuyDate,ROCdeprYM);
						int TempLastYesr2 = Datetime.BetweenTwoMonth(ROCBuyDate, Datetime.getDateAdd("m", -1, ROCdeprYM.substring(0, 3) + "0101").substring(0, 5));
						int TempMonthDepr1 = 0;
						int TempMonthDepr2 = 0;
						if (Integer.parseInt(ul._transToCE_Year(obj.getDeprYM()).substring(0, 6)) < Integer.parseInt(obj.getApportionEndYM()))
						{
							if (Integer.parseInt(obj.getBuyDate().substring(0, 4)) < Integer.parseInt(ul._transToCE_Year(obj.getDeprYM()).substring(0, 4))) {
								TempMonthDepr1 = Integer.parseInt(obj.getMonthDepr()) * TempThisYear;
							} else {
								TempMonthDepr1 = Integer.parseInt(obj.getMonthDepr()) * TempLastYear;
							}
							obj.setMonthDepr1(String.valueOf(TempMonthDepr1));
						} 
						else if (Integer.parseInt(ul._transToCE_Year(obj.getDeprYM()).substring(0, 6)) >= Integer.parseInt(obj.getApportionEndYM()) && Integer.parseInt(ul._transToCE_Year(obj.getDeprYM()).substring(0, 4)) == Integer.parseInt(obj.getApportionEndYM().substring(0, 4)))
						{
							if (Integer.parseInt(obj.getBuyDate().substring(0, 4)) < Integer.parseInt(ul._transToCE_Year(obj.getDeprYM()).substring(0, 4))) {
								TempMonthDepr1 = Integer.parseInt(obj.getMonthDepr()) * (TempThisYear2-1) + Integer.parseInt(obj.getMonthDepr1());
							} else {
								TempMonthDepr1 = Integer.parseInt(obj.getMonthDepr()) * (TempLastYear-1) + Integer.parseInt(obj.getMonthDepr1());
							}
							obj.setMonthDepr1(String.valueOf(TempMonthDepr1));
						}
						else
						{
							obj.setMonthDepr1("0");
						}
						//處理情況二、monthdepr2
						if (Integer.parseInt(obj.getBuyDate().substring(0, 4)) < Integer.parseInt(obj.getEnterDate().substring(0, 4))) {
							if (Integer.parseInt(ul._transToCE_Year(obj.getDeprYM()).substring(0, 4)) <= Integer.parseInt(obj.getApportionEndYM().substring(0, 4)))
							{
								TempMonthDepr2 = Integer.parseInt(obj.getMonthDepr()) * (TempLastYesr2);
								obj.setMonthDepr2(String.valueOf(TempMonthDepr2));
							} else {
								obj.setMonthDepr2(rs.getString("depramount"));
							}
						} else {
							obj.setMonthDepr2("0");
						}
						insertData(db2);
						doCount("RF", "S");
					}
				}
				//情況三以前年月購入（不含本月及上月），以前年月入帳（不含本月），且未逾「攤提年限截止年月」，提列1個月折舊
				else if (Integer.parseInt(obj.getBuyDate().substring(0, 6)) < Integer.parseInt(TempDate) 	
							&& Integer.parseInt(obj.getEnterDate().substring(0, 6)) < Integer.parseInt(ul._transToCE_Year(obj.getDeprYM()).substring(0, 6)) 
							&& 
							(
								// 截止年月 >= 折舊年月
								Integer.parseInt(obj.getApportionEndYM()) >= Integer.parseInt(ul._transToCE_Year(obj.getDeprYM()).substring(0, 6))
								||
								(
									// 截止年月 < 折舊年月
									Integer.parseInt(obj.getApportionEndYM()) < Integer.parseInt(ul._transToCE_Year(obj.getDeprYM()).substring(0, 6))
									&& 
									// 淨值 > 殘值
									Common.getLong(this.getOldNetValue()) > Common.getLong(this.getScrapValue())
									&& 
									// 已達年限財產折舊方法:1.不補提 2. 自動提列
									"2".equals(this.getTmpReachedYearCalMethod())
								)
							)
							&& "N".equals(checkGet(rs.getString("nodeprset"))))
				{
					if (Common.getInt(deprYM_to_YYYYMM) == Common.getInt(this.getApportionEndYM())) {
						obj.setMonthDepr1(checkGet(rs.getString("monthdepr1")));
					} else {
						if (Common.getInt(deprYM_to_YYYYMM) > Common.getInt(this.getApportionEndYM())) {
							// 已達年限自動提列折舊 : 淨值 - 殘值
							obj.setMonthDepr1(Common.get(Common.getLong(this.getOldNetValue()) - Common.getLong(this.getScrapValue())));
						} else {
							obj.setMonthDepr1(checkGet(rs.getString("monthdepr")));
						}
							
					}
					obj.setMonthDepr2("0");
					insertData(db2);
					doCount("RF", "S");
				} 
				// 情況四 本月不折舊資料
				else if (
						// 原值(bookValue)=殘值(scrapValue) + 累計折舊(accumDepr)
						Common.getLong(obj.getBookValue()) == Common.getLong(obj.getScrapValue()) + Common.getLong(obj.getAccumDepr()) ||
						// 攤提年限截止年月(西元年)(apportionEndYM) < 畫面.deprYM.轉西元年
						Integer.parseInt(ul._transToCE_Year(obj.getDeprYM()).substring(0, 6))>Integer.parseInt(obj.getApportionEndYM()) ||
						( //購置日期(西元年)(buyDate).年月 = 畫面.deprYM.轉西元年 and 入帳日期(西元年)(enterDate).年月 =畫面.deprYM.轉西元年
						   Integer.parseInt(ul._transToCE_Year(obj.getDeprYM()).substring(0, 6)) == Integer.parseInt(obj.getBuyDate().substring(0, 6)) 
						   &&
						   Integer.parseInt(ul._transToCE_Year(obj.getDeprYM()).substring(0, 6)) == Integer.parseInt(obj.getEnterDate().substring(0, 6)) 
						) ||
						("B".equals(rs.getString("individualsetdepr")) && obj.getEnterDate().substring(0, 6).equals(rs.getString("verifyym")))
						) 
				{
					obj.setMonthDepr1("0");
					obj.setMonthDepr2("0");
					insertData(db2);
					doCount("RF", "S");
				} else {
					doCount("RF", "I");
				}
			}
			super.setState("init");
			this.batchInsertCommit();
		} catch (Exception e) {
			this.logger.error("土改每月折舊計算發生錯誤:" + this.getCurrentInfo(), e);
			this.insertRollBack();
			throw e;
		} finally {
			db.closeAll();
			db2.closeAll();
			
		}
	}
	
	/**
	 * 建物折舊計算
	 * @throws Exception
	 */
	private void QueryBUTable() throws Exception {
		UNTCH_Tools ul = new UNTCH_Tools();
		UNTDP001F obj = this;
		Database db = new Database();
		Database db2 = new Database();
		
		ResultSet rs = null;
		StringTools st = new StringTools();
		//畫面deprym-1個月
		String TempDate = ul._transToCE_Year(Datetime.getDateAdd("m", -1,getDeprYM()+"01" ).substring(0, 5));
		String ROCdeprYM = getDeprYM();
		/** 畫面deprym 轉西元年前6碼        ex:畫面輸入10408 -> 201508  **/
		String deprYM_to_YYYYMM = ul._transToCE_Year(deprYM).substring(0, 6);
		
		try {
			String sql = " select a.enterorg ,a.ownership ,a.differencekind ,a.propertyno ,a.serialno ,"+
					     " (select max(isnull(serialno1,0))+1 from UNTDP_MONTHDEPR m where a.enterorg=m.enterorg  and a.ownership=m.ownership and a.differencekind=m.differencekind and a.propertyno=m.propertyno and a.serialno=m.serialno) as serialNo1,"+
					     " 0  as deprFrequency," +			   
					     " a.propertykind ,a.fundtype ,a.valuable ,a.deprmethod,a.buildfeecb,a.deprunitcb,a.deprpark,a.deprunit,a.deprunit1," +
					     " a.depraccounts,isnull(a.originalbv,0) as originalbv,isnull(a.bookvalue,0) as bookvalue,isnull(a.scrapvalue,0) as scrapvalue,isnull(a.depramount,0) as depramount,isnull(a.apportionmonth,0) as apportionmonth," +
					     " isnull(a.netvalue,0) as netvalue,isnull(a.accumdepr,0) as accumDepr,a.apportionendym," +
					     " isnull((select sum(isnull(b.reduceaccumdepr,0))   from UNTBU_BUILDING a, UNTBU_ADJUSTDETAIL b where b.verify = 'Y'  and b.adjustdate="+Common.sqlChar(ul._transToCE_Year(getDeprYM())) +" and a.enterorg = b.enterorg and a.ownership = b.ownership and a.differencekind = b.differencekind and a.propertyno = b.propertyno and a.serialno = b.serialno) +(select isnull(b.oldaccumdepr,0)  from UNTBU_BUILDING a, UNTBU_REDUCEDETAIL b where b.verify = 'Y'   and b.reducedate="+Common.sqlChar(ul._transToCE_Year(getDeprYM())) +" and a.enterorg = b.enterorg   and a.ownership = b.ownership   and a.differencekind = b.differencekind   and a.propertyno = b.propertyno  and a.serialno = b.serialno),0) as reduceAccumDepr," +
					     " isnull(a.monthdepr1,0) as monthdepr1,isnull(a.monthdepr,0) as monthdepr,a.buydate,a.enterdate,a.sourcedate,a.propertyname1, " +
						" isnull(a.otherlimityear,(a.originallimityear) * 12)  as limitYear ," +
						" a.keepunit,a.keeper,a.originalapportionmonth,isnull(a.escroworivalue,0) as escroworivalue,isnull(a.escroworiaccumdepr,0) as escroworiaccumdepr,a.engineeringno,a.notes,a.editid,a.editdate,a.edittime," +
						" a.oldpropertyno,a.oldserialno,a.oldlotno,a.nodeprset, a.individualsetdepr, a.verifyym" +
						" from UNTBU_BUILDING a" +
						" left join SYSPK_PROPERTYCODE p on p.enterorg in (a.enterorg,'000000000A') and a.propertyno=p.propertyno" +
						" where 1=1 " +
						" and a.verify='Y' " +
						" and a.datastate='1' " +
						" and a.valuable='N' " +
						" and a.enterdate is not null "+
						"";
			if (!"".equals(getEnterOrg())) {
				sql +=" and a.enterorg = " + Common.sqlChar(getEnterOrg());
			}
			if (!"".equals(getDifferenceKind())) { 
				sql +=" and a.differencekind = " + Common.sqlChar(getDifferenceKind());
			}
			//System.out.println("11111111111111"+sql);
			rs = db.querySQL_NoChange(sql);
			while (rs.next()) {
				doCount("BU", "P");
				//if(rs.getString("buydate").substring(0, 5))
				this.setTmpEnterorg(checkGet(rs.getString("enterorg")));
				this.setTmpOwnership(checkGet(rs.getString("ownership")));
				this.setTmpDifferencekind(checkGet(rs.getString("differencekind")));
				this.setTmpPropertyno(checkGet(rs.getString("propertyno")));
				this.setTmpSerialno(checkGet(rs.getString("serialno")));
				
				obj.setEnterOrg(checkGet(rs.getString("enterorg")));
				obj.setOwnership(checkGet(rs.getString("ownership")));
				obj.setDifferenceKind(checkGet(rs.getString("differencekind")));
				obj.setPropertyNo(checkGet(rs.getString("propertyno")));
				obj.setSerialNo(checkGet(rs.getString("serialno")));
				obj.setSerialNo1((null==rs.getString("serialno1")?"1":rs.getString("serialno1")));
				obj.setDeprFrequency((null==rs.getString("deprFrequency")?"0":rs.getString("deprFrequency")));		
				//obj.setDeprFrequency1(rs.getString("deprFrequency1"));
				obj.setPropertyKind(checkGet(rs.getString("propertykind")));
				obj.setFundType(checkGet(rs.getString("fundtype")));
				obj.setValuable(checkGet(rs.getString("valuable")));
				obj.setDeprMethod(checkGet(rs.getString("deprmethod")));
				obj.setBuildFeeCB(checkGet(rs.getString("buildfeecb")));
				obj.setDeprUnitCB(checkGet(rs.getString("deprunitcb")));
				obj.setDeprPark(checkGet(rs.getString("deprpark")));
				obj.setDeprUnit(checkGet(rs.getString("deprunit")));
				obj.setDeprUnit1(checkGet(rs.getString("deprunit1")));
				obj.setDeprAccounts(checkGet(rs.getString("depraccounts")));
				obj.setOriginalBV(checkGet(rs.getString("originalbv")));
				obj.setBookValue(checkGet(rs.getString("bookvalue")));
				obj.setScrapValue(checkGet(rs.getString("scrapvalue")));
				obj.setDeprAmount(checkGet(rs.getString("depramount")));
				obj.setApportionMonth(checkGet(rs.getString("apportionmonth")));
				obj.setOldNetValue(checkGet(rs.getString("netvalue")));
				obj.setAccumDepr(checkGet(rs.getString("accumDepr")));
				obj.setReduceAccumDepr(checkGet(rs.getString("reduceAccumDepr")));
				obj.setMonthDepr1(rs.getString("monthdepr1"));
				obj.setMonthDepr(checkGet(rs.getString("monthdepr")));
				obj.setBuyDate(checkGet(rs.getString("buydate")));
				obj.setEnterDate(checkGet(rs.getString("enterdate")));
				obj.setSourceDate(checkGet(rs.getString("sourcedate")));
				obj.setPropertyName1(checkGet(rs.getString("propertyname1")));
				obj.setLimitYear(null==rs.getString("limitYear")?"null":rs.getString("limitYear"));
				obj.setDeprPercent("100");
				obj.setScaledAddAccumDepr("0");
				obj.setKeeper(checkGet(rs.getString("keeper")));
				obj.setKeepUnit(checkGet(rs.getString("keepunit")));
				obj.setOldpropertyno(checkGet(rs.getString("oldpropertyno")));
				obj.setOldserialno(checkGet(rs.getString("oldserialno")));
				obj.setOldlotno(checkGet(rs.getString("oldlotno")));
				obj.setOldmonthdepr(checkGet(rs.getString("monthdepr")));
				obj.setOldmonthdepr1(checkGet(rs.getString("monthdepr1")));
				if ("111".equals(checkGet(rs.getString("differencekind"))) || "113".equals(checkGet(rs.getString("differencekind")))) {
						obj.setEscrowOriLimitMonth(checkGet(rs.getString("originalapportionmonth")));
				} else {
					obj.setEscrowOriLimitMonth("null");
				}	
				obj.setEscrowOriValue(checkGet(rs.getString("escroworivalue")));
				obj.setEscrowOriAccumDepr(checkGet(rs.getString("escrowOriaccumdepr")));
				obj.setEngineeringNo(checkGet(rs.getString("engineeringno")));
				obj.setNotes(checkGet(rs.getString("notes")));
				obj.setEditID(checkGet(rs.getString("editid")));
				obj.setEditDate(checkGet(rs.getString("editdate")));
				obj.setEditTime(checkGet(rs.getString("edittime")));
				obj.setApportionEndYM(checkGet(rs.getString("apportionendym")));
				obj.setAddAccumDepr("0");
				obj.setBookAmount("1");
				obj.setPropertyTypeTemp("3");
				obj.setVerify("N");
				obj.setLotNo("");
				obj.setNodeprset(rs.getString("nodeprset"));
				
				String nodeprset = Common.get(rs.getString("nodeprset"));
				// TODO 情況一 ~ 情況四 利用method 回傳布林  不然這樣太難閱讀與debug
				if (this.isSituationZero(nodeprset)) {
					//#region 情況零 
					obj.setMonthDepr1("0");
					obj.setMonthDepr2("0");
					insertData(db2);
					doCount("RF", "S");
					//#end 
				}
				//情況一上月購入，上月入帳，本月開始提列折舊
				else if(TempDate.equals(obj.getBuyDate().substring(0, 6)) && TempDate.equals(obj.getEnterDate().substring(0, 6)) && "N".equals(checkGet(rs.getString("nodeprset"))))
				{
					if (ul._transToCE_Year(obj.getDeprYM()).substring(0, 6) == obj.getApportionEndYM()) {
						obj.setMonthDepr1(checkGet(rs.getString("monthdepr1")));
					} else {
						obj.setMonthDepr1(checkGet(rs.getString("monthdepr")));
					}
					obj.setMonthDepr2("0");
					this.insertData(db2);
					doCount("BU", "S");
				}
				//情況二以前年月購入(不含本月)，本月入帳，本月補提折舊
				else if(Integer.parseInt(obj.getBuyDate().substring(0, 6)) < Integer.parseInt(ul._transToCE_Year(obj.getDeprYM()).substring(0, 6)) && obj.getEnterDate().substring(0, 6).equals(ul._transToCE_Year(obj.getDeprYM()).substring(0, 6)) && "N".equals(checkGet(rs.getString("nodeprset"))) && !"B".equals(rs.getString("individualsetdepr")))
				{
					// individualsetdepr為自行設定累計折舊，A為僅提列當月折舊
					if ("A".equals(rs.getString("individualsetdepr"))) {
						long leftvalue = Common.getLong(this.getOldNetValue()) - Common.getLong(this.getScrapValue()); //剩餘價值
						if (Common.getInt(deprYM_to_YYYYMM) == Common.getInt(this.getApportionEndYM())) { //當月為最後一個月
							obj.setMonthDepr1(checkGet(rs.getString("monthdepr1")));
						} else {
							if (Common.getInt(deprYM_to_YYYYMM) > Common.getInt(this.getApportionEndYM())) { //已折完
								// 已達年限自動提列折舊 : 淨值 - 殘值
								obj.setMonthDepr1(Common.get(leftvalue));
							} else { //正常折舊
								obj.setMonthDepr1(checkGet(rs.getString("monthdepr")));
							}
						}
						// 如果攤提金額大於剩餘金額，僅折剩餘金額
						if (Common.getLong(obj.getMonthDepr1()) > leftvalue) {
							obj.setMonthDepr1(Common.get(leftvalue));
						}
						
						obj.setMonthDepr2("0"); 
						insertData(db2);
						doCount("BU", "S");
					} else {
						String ROCBuyDate = ul._transToROC_Year(obj.getBuyDate().substring(0, 6));
						String ROCApportionEndYM = ul._transToROC_Year(obj.getApportionEndYM().substring(0, 6));
						int TempThisYear = Datetime.BetweenTwoMonth(ROCdeprYM.substring(0, 3) + "01", ROCdeprYM)+1;
						int TempThisYear2 = Datetime.BetweenTwoMonth(ROCApportionEndYM.substring(0, 3) + "01", ROCApportionEndYM)+1;
						int TempLastYear = Datetime.BetweenTwoMonth(ROCBuyDate,ROCdeprYM);
						int TempLastYesr2 = Datetime.BetweenTwoMonth(ROCBuyDate, Datetime.getDateAdd("m", -1, ROCdeprYM.substring(0, 3) + "0101").substring(0, 5));
						int TempMonthDepr1=0;
						int TempMonthDepr2=0;
						if (Integer.parseInt(ul._transToCE_Year(obj.getDeprYM()).substring(0, 6)) < Integer.parseInt(obj.getApportionEndYM()))
						{
							if (Integer.parseInt(obj.getBuyDate().substring(0, 4)) < Integer.parseInt(ul._transToCE_Year(obj.getDeprYM()).substring(0, 4))) {
								TempMonthDepr1 = Integer.parseInt(obj.getMonthDepr()) * TempThisYear;
							} else {
								TempMonthDepr1 = Integer.parseInt(obj.getMonthDepr()) * TempLastYear;
							}
							obj.setMonthDepr1(String.valueOf(TempMonthDepr1));
						}
						else if (Integer.parseInt(ul._transToCE_Year(obj.getDeprYM()).substring(0, 6)) >= Integer.parseInt(obj.getApportionEndYM()) && Integer.parseInt(ul._transToCE_Year(obj.getDeprYM()).substring(0, 4))==Integer.parseInt(obj.getApportionEndYM().substring(0, 4)))
						{
							if (Integer.parseInt(obj.getBuyDate().substring(0, 4)) < Integer.parseInt(ul._transToCE_Year(obj.getDeprYM()).substring(0, 4))) {
								TempMonthDepr1 = Integer.parseInt(obj.getMonthDepr()) * (TempThisYear2-1) + Integer.parseInt(obj.getMonthDepr1());
							} else {
								TempMonthDepr1 = Integer.parseInt(obj.getMonthDepr()) * (TempLastYear-1) + Integer.parseInt(obj.getMonthDepr1());
							}
							obj.setMonthDepr1(String.valueOf(TempMonthDepr1));
						}
						else
						{
							obj.setMonthDepr1("0");
						}
						//處理情況二、monthdepr2
						if (Integer.parseInt(obj.getBuyDate().substring(0, 4)) < Integer.parseInt(obj.getEnterDate().substring(0, 4))) {
							if (Integer.parseInt(ul._transToCE_Year(obj.getDeprYM()).substring(0, 4)) <= Integer.parseInt(obj.getApportionEndYM().substring(0, 4)))
							{
								TempMonthDepr2 = Integer.parseInt(obj.getMonthDepr()) * (TempLastYesr2);
								obj.setMonthDepr2(String.valueOf(TempMonthDepr2));
							} else {
								obj.setMonthDepr2(rs.getString("depramount"));
							}
						} else {
							obj.setMonthDepr2("0");
						}
						insertData(db2);
						doCount("BU", "S");
					}
				}
				//情況三以前年月購入（不含本月及上月），以前年月入帳（不含本月），且未逾「攤提年限截止年月」，提列1個月折舊
				else if (Integer.parseInt(obj.getBuyDate().substring(0, 6)) < Integer.parseInt(TempDate) 
						&& Integer.parseInt(obj.getEnterDate().substring(0, 6)) < Integer.parseInt(ul._transToCE_Year(obj.getDeprYM()).substring(0, 6)) 
						&& 
						(
							Integer.parseInt(obj.getApportionEndYM())>=Integer.parseInt(ul._transToCE_Year(obj.getDeprYM()).substring(0, 6))
							||
							(
								// 截止年月 < 折舊年月
								Integer.parseInt(obj.getApportionEndYM()) < Integer.parseInt(ul._transToCE_Year(obj.getDeprYM()).substring(0, 6))
								&& 
								// 淨值 > 殘值
								Common.getLong(this.getOldNetValue()) > Common.getLong(this.getScrapValue())
								&& 
								// 已達年限財產折舊方法:1.不補提 2. 自動提列
								"2".equals(this.getTmpReachedYearCalMethod())		
							)
						)
						&& "N".equals(checkGet(rs.getString("nodeprset"))))
				{
					if (Common.getInt(deprYM_to_YYYYMM) == Common.getInt(this.getApportionEndYM())) {
						obj.setMonthDepr1(checkGet(rs.getString("monthdepr1")));
					} else {
						if (Common.getInt(deprYM_to_YYYYMM) > Common.getInt(this.getApportionEndYM())) {
							// 已達年限自動提列折舊 : 淨值 - 殘值
							obj.setMonthDepr1(Common.get(Common.getLong(this.getOldNetValue()) - Common.getLong(this.getScrapValue())));
						} else {
							obj.setMonthDepr1(checkGet(rs.getString("monthdepr")));
						}
					}
					obj.setMonthDepr2("0");
					insertData(db2);
					doCount("BU", "S");
				} 
				//情況四本月不折舊資料
				else if (
					// 原值(bookValue)=殘值(scrapValue) + 累計折舊(accumDepr)
					Common.getLong(obj.getBookValue()) == Common.getLong(obj.getScrapValue()) + Common.getLong(obj.getAccumDepr()) ||
					// 攤提年限截止年月(西元年)(apportionEndYM) < 畫面.deprYM.轉西元年
					Integer.parseInt(ul._transToCE_Year(obj.getDeprYM()).substring(0, 6)) > Integer.parseInt(obj.getApportionEndYM()) ||
					( //購置日期(西元年)(buyDate).年月 = 畫面.deprYM.轉西元年 and 入帳日期(西元年)(enterDate).年月 =畫面.deprYM.轉西元年
						Integer.parseInt(ul._transToCE_Year(obj.getDeprYM()).substring(0, 6)) == Integer.parseInt(obj.getBuyDate().substring(0, 6)) 
						&&
						Integer.parseInt(ul._transToCE_Year(obj.getDeprYM()).substring(0, 6)) == Integer.parseInt(obj.getEnterDate().substring(0, 6)) 
					) ||
					("B".equals(rs.getString("individualsetdepr")) && obj.getEnterDate().substring(0, 6).equals(rs.getString("verifyym")))
					) 
				{
					obj.setMonthDepr1("0");
					obj.setMonthDepr2("0");
					insertData(db2);
					doCount("BU", "S");
				} else {
					doCount("BU", "I");
				}
			}
			super.setState("init");
			this.batchInsertCommit();
		} catch (Exception e) {
			this.logger.error("建物每月折舊計算發生錯誤:" + this.getCurrentInfo(), e);
			
			this.insertRollBack();
			throw e;
		} finally {
			db.closeAll();
			db2.closeAll();
		}
	}
	
	/**
	 * 動產折舊計算
	 * @throws Exception
	 */
	private void QueryMPTable() throws Exception {
		UNTCH_Tools ul = new UNTCH_Tools();
		UNTDP001F obj = this;
		Database db = new Database();
		Database db2 = new Database();
		ResultSet rs = null;
		
		StringTools st = new StringTools();
		//畫面deprym-1個月
		String TempDate=ul._transToCE_Year(Datetime.getDateAdd("m", -1,getDeprYM()+"01" ).substring(0, 5));
		String ROCdeprYM = getDeprYM();
		/** 畫面deprym 轉西元年前6碼        ex:畫面輸入10408 -> 201508  **/
		String deprYM_to_YYYYMM = ul._transToCE_Year(deprYM).substring(0, 6);
		try{
			String sql = " select b.enterorg ,b.ownership ,b.differencekind ,b.propertyno ,b.serialno ,b.lotno,"+
						 " (select max(isnull(serialno1,0))+1 from UNTDP_MONTHDEPR m where b.enterorg=m.enterorg  and b.ownership=m.ownership and b.differencekind=m.differencekind and b.propertyno=m.propertyno and b.serialno=m.serialno) as serialNo1,"+
						 " 0  as deprFrequency," +			   
						 " a.propertykind ,a.fundtype ,a.valuable ,b.deprmethod,b.buildfeecb,b.deprunitcb,b.deprpark,b.deprunit,b.deprunit1," +
						 " b.depraccounts,isnull(b.originalbv,0) as originalbv,isnull(b.bookvalue,0) as bookvalue,isnull(b.scrapvalue,0) as scrapvalue,isnull(b.depramount,0) as depramount,isnull(b.apportionmonth,0) as apportionmonth," +
						 " isnull(b.netvalue,0) as netvalue,isnull(b.accumdepr,0) as accumDepr,b.apportionendym," +
						 " isnull((select sum(isnull(b.reduceaccumdepr,0))   from UNTMP_MOVABLEDETAIL a, UNTMP_ADJUSTDETAIL b where b.verify = 'Y'  and b.adjustdate="+Common.sqlChar(ul._transToCE_Year(getDeprYM())) +" and a.enterorg = b.enterorg and a.ownership = b.ownership and a.differencekind = b.differencekind and a.propertyno = b.propertyno and a.serialno = b.serialno) +(select isnull(b.oldaccumdepr,0)  from UNTMP_MOVABLEDETAIL a, UNTMP_REDUCEDETAIL b where b.verify = 'Y'   and b.reducedate="+Common.sqlChar(ul._transToCE_Year(getDeprYM())) +" and a.enterorg = b.enterorg   and a.ownership = b.ownership   and a.differencekind = b.differencekind   and a.propertyno = b.propertyno  and a.serialno = b.serialno),0) as reduceAccumDepr," +
						 " isnull(b.monthdepr1,0) as monthdepr1,isnull(b.monthdepr,0) as monthdepr,a.buydate,b.enterdate,a.sourcedate,b.propertyname1, " +
						 " 1 as bookamount,isnull(b.otherlimityear,b.originallimityear)  as limitYear ," +
						 " b.keepunit,b.keeper,b.otherlimityear,isnull(a.escroworivalue,0) as escroworivalue,isnull(a.escroworiaccumdepr,0) as escroworiaccumdepr,b.engineeringno,a.notes,a.editid,a.editdate,a.edittime," +
						 " b.oldpropertyno,b.oldserialno,b.oldlotno,b.nodeprset,b.originalapportionmonth, b.individualsetdepr, b.verifyym" +
						 " from UNTMP_MOVABLE a" +
						 " left join UNTMP_MOVABLEDETAIL b on a.enterorg=b.enterorg and a.ownership=b.ownership and a.differencekind=b.differencekind and a.propertyno=b.propertyno and a.lotno=b.lotno " +
						 " left join SYSPK_PROPERTYCODE p on p.enterorg in (a.enterorg,'000000000A') and a.propertyno=p.propertyno" +
						 " where 1=1 " ;

			if (!"".equals(getEnterOrg())) {
				sql += " and a.enterorg = " + Common.sqlChar(getEnterOrg()) ;
			}
			
			if (!"".equals(getDifferenceKind())) { 
				sql += " and a.differencekind = " + Common.sqlChar(getDifferenceKind()) ;
			}

			sql += " and b.verify='Y' " +
				   " and b.datastate='1' " +
				   " and a.valuable='N' " +
				   " and b.enterdate is not null ";

			if (getPropertyType().equals("4")) {
				sql+=" and substring(b.propertyno,1,1)='3' ";
			} else if (getPropertyType().equals("5")) {
				sql+=" and substring(b.propertyno,1,1)='4' ";
			} else if (getPropertyType().equals("6")) {
				sql+=" and substring(b.propertyno,1,1)='5' ";
			}

			sql += "order by b.propertyno, b.serialno";

			//	System.out.println(sql);
			rs = db.querySQL_NoChange(sql);
			while (rs.next()) {
				//if(rs.getString("buydate").substring(0, 5))
				doCount("MP", "P");
				
				this.setTmpEnterorg(checkGet(rs.getString("enterorg")));
				this.setTmpOwnership(checkGet(rs.getString("ownership")));
				this.setTmpDifferencekind(checkGet(rs.getString("differencekind")));
				this.setTmpPropertyno(checkGet(rs.getString("propertyno")));
				this.setTmpSerialno(checkGet(rs.getString("serialno")));
				
				obj.setEnterOrg(checkGet(rs.getString("enterorg")));
				obj.setOwnership(checkGet(rs.getString("ownership")));
				obj.setDifferenceKind(checkGet(rs.getString("differencekind")));
				obj.setPropertyNo(checkGet(rs.getString("propertyno")));
				obj.setSerialNo(checkGet(rs.getString("serialno")));
				obj.setSerialNo1((null==rs.getString("serialno1")?"1":rs.getString("serialno1")));
				obj.setDeprFrequency((null==rs.getString("deprFrequency")?"0":rs.getString("deprFrequency")));		
				//obj.setDeprFrequency1(rs.getString("deprFrequency1"));
				obj.setPropertyKind(checkGet(rs.getString("propertykind")));
				obj.setFundType(checkGet(rs.getString("fundtype")));
				obj.setValuable(checkGet(rs.getString("valuable")));
				obj.setDeprMethod(checkGet(rs.getString("deprmethod")));
				obj.setBuildFeeCB(checkGet(rs.getString("buildfeecb")));
				obj.setDeprUnitCB(checkGet(rs.getString("deprunitcb")));
				obj.setDeprPark(checkGet(rs.getString("deprpark")));
				obj.setDeprUnit(checkGet(rs.getString("deprunit")));
				obj.setDeprUnit1(checkGet(rs.getString("deprunit1")));
				obj.setDeprAccounts(checkGet(rs.getString("depraccounts")));
				obj.setOriginalBV(checkGet(rs.getString("originalbv")));
				obj.setBookValue(checkGet(rs.getString("bookvalue")));
				obj.setScrapValue(checkGet(rs.getString("scrapvalue")));
				obj.setDeprAmount(checkGet(rs.getString("depramount")));
				obj.setApportionMonth(checkGet(rs.getString("apportionmonth")));
				obj.setOldNetValue(checkGet(rs.getString("netvalue")));
				obj.setAccumDepr(checkGet(rs.getString("accumDepr")));
				obj.setReduceAccumDepr(checkGet(rs.getString("reduceAccumDepr")));
				obj.setMonthDepr1(rs.getString("monthdepr1"));
				obj.setMonthDepr(checkGet(rs.getString("monthdepr")));
				obj.setBuyDate(checkGet(rs.getString("buydate")));
				obj.setEnterDate(checkGet(rs.getString("enterdate")));
				obj.setSourceDate(checkGet(rs.getString("sourcedate")));
				obj.setPropertyName1(checkGet(rs.getString("propertyname1")));
				obj.setLimitYear(null==rs.getString("limitYear")?"null":rs.getString("limitYear"));
				obj.setDeprPercent("100");
				obj.setScaledAddAccumDepr("0");
				obj.setKeeper(checkGet(rs.getString("keeper")));
				obj.setKeepUnit(checkGet(rs.getString("keepunit")));
				if ("111".equals(checkGet(rs.getString("differencekind"))) || "113".equals(checkGet(rs.getString("differencekind")))) {
					obj.setEscrowOriLimitMonth(checkGet(rs.getString("originalapportionmonth")));
				} else {
					obj.setEscrowOriLimitMonth("null");
				}	
				obj.setEscrowOriValue(checkGet(rs.getString("escroworivalue")));
				obj.setEscrowOriAccumDepr(checkGet(rs.getString("escrowOriaccumdepr")));
				obj.setEngineeringNo(checkGet(rs.getString("engineeringno")));
				obj.setNotes(checkGet(rs.getString("notes")));
				obj.setEditID(checkGet(rs.getString("editid")));
				obj.setEditDate(checkGet(rs.getString("editdate")));
				obj.setEditTime(checkGet(rs.getString("edittime")));
				obj.setApportionEndYM(checkGet(rs.getString("apportionendym")));
				obj.setAddAccumDepr("0");
				obj.setBookAmount(checkGet(rs.getString("bookamount")));
				if (checkGet(rs.getString("propertyno")).substring(0, 1).equals("3")) {
					obj.setPropertyTypeTemp("4");
				} else if (checkGet(rs.getString("propertyno")).substring(0, 1).equals("4")) {
					obj.setPropertyTypeTemp("5");
				} else if (checkGet(rs.getString("propertyno")).substring(0, 1).equals("5")) {
					obj.setPropertyTypeTemp("6");
				}
				obj.setVerify("N");
				obj.setLotNo(checkGet(rs.getString("lotno")));
				obj.setNodeprset(rs.getString("nodeprset"));
				obj.setOldpropertyno(checkGet(rs.getString("oldpropertyno")));
				obj.setOldserialno(checkGet(rs.getString("oldserialno")));
				obj.setOldlotno(checkGet(rs.getString("oldlotno")));
				obj.setOldmonthdepr(checkGet(rs.getString("monthdepr")));
				obj.setOldmonthdepr1(checkGet(rs.getString("monthdepr1")));

				String nodeprset = Common.get(rs.getString("nodeprset"));
				
				// TODO 情況一 ~ 情況四 利用method 回傳布林  不然這樣太難閱讀與debug
				if (this.isSituationZero(nodeprset)) {
					//#region 情況零 
					obj.setMonthDepr1("0");
					obj.setMonthDepr2("0");
					insertData(db2);
					doCount("RF", "S");
					//#end 
				}
				
				
				//情況一上月購入，上月入帳，本月開始提列折舊
				else if (TempDate.equals(obj.getBuyDate().substring(0, 6)) && TempDate.equals(obj.getEnterDate().substring(0, 6)) && "N".equals(checkGet(rs.getString("nodeprset"))))
				{
					if (ul._transToCE_Year(obj.getDeprYM()).substring(0, 6) == obj.getApportionEndYM()) {
						obj.setMonthDepr1(checkGet(rs.getString("monthdepr1")));
					} else {
						obj.setMonthDepr1(checkGet(rs.getString("monthdepr")));
					}
					obj.setMonthDepr2("0");
					insertData(db2);
					doCount("MP", "S");
				}
				//情況二以前年月購入(不含本月)，本月入帳，本月補提折舊
				else if (Integer.parseInt(obj.getBuyDate().substring(0, 6)) < Integer.parseInt(ul._transToCE_Year(obj.getDeprYM()).substring(0, 6)) && obj.getEnterDate().substring(0, 6).equals(ul._transToCE_Year(obj.getDeprYM()).substring(0, 6)) && "N".equals(checkGet(rs.getString("nodeprset"))) && !"B".equals(rs.getString("individualsetdepr")))
				{
					// individualsetdepr為自行設定累計折舊，A為僅提列當月折舊
					if ("A".equals(rs.getString("individualsetdepr"))) {
						long leftvalue = Common.getLong(this.getOldNetValue()) - Common.getLong(this.getScrapValue()); //剩餘價值
						if (Common.getInt(deprYM_to_YYYYMM) == Common.getInt(this.getApportionEndYM())) { //當月為最後一個月
							obj.setMonthDepr1(checkGet(rs.getString("monthdepr1")));
						} else {
							if (Common.getInt(deprYM_to_YYYYMM) > Common.getInt(this.getApportionEndYM())) { //已折完
								// 已達年限自動提列折舊 : 淨值 - 殘值
								obj.setMonthDepr1(Common.get(leftvalue));
							} else { //正常折舊
								obj.setMonthDepr1(checkGet(rs.getString("monthdepr")));
							}
						}
						// 如果攤提金額大於剩餘金額，僅折剩餘金額
						if (Common.getLong(obj.getMonthDepr1()) > leftvalue) {
							obj.setMonthDepr1(Common.get(leftvalue));
						}
						
						obj.setMonthDepr2("0"); 
						insertData(db2);
						doCount("MP", "S");
					} else {
						//#region 本月折舊(以前年度)  monthDepr2  
						String ROCBuyDate = ul._transToROC_Year(obj.getBuyDate().substring(0, 6));
						String ROCApportionEndYM = ul._transToROC_Year(obj.getApportionEndYM().substring(0, 6));
						int TempThisYear = Datetime.BetweenTwoMonth(ROCdeprYM.substring(0, 3) + "01", ROCdeprYM) + 1;
						int TempThisYear2 = Datetime.BetweenTwoMonth(ROCApportionEndYM.substring(0, 3) + "01", ROCApportionEndYM) + 1;
						int TempLastYear = Datetime.BetweenTwoMonth(ROCBuyDate,ROCdeprYM);
						int TempLastYesr2 = Datetime.BetweenTwoMonth(ROCBuyDate, Datetime.getDateAdd("m", -1, ROCdeprYM.substring(0, 3) + "0101").substring(0, 5));
						int TempMonthDepr1 = 0;
						int TempMonthDepr2 = 0;
						if (Integer.parseInt(ul._transToCE_Year(obj.getDeprYM()).substring(0, 6)) < Integer.parseInt(obj.getApportionEndYM()))
						{
							if (Integer.parseInt(obj.getBuyDate().substring(0, 4)) < Integer.parseInt(ul._transToCE_Year(obj.getDeprYM()).substring(0, 4))) {
								TempMonthDepr1 = Integer.parseInt(obj.getMonthDepr()) * TempThisYear;
							} else {
								TempMonthDepr1 = Integer.parseInt(obj.getMonthDepr()) * TempLastYear;
							}
							obj.setMonthDepr1(String.valueOf(TempMonthDepr1));
						}
						else if (Integer.parseInt(ul._transToCE_Year(obj.getDeprYM()).substring(0, 6)) >= Integer.parseInt(obj.getApportionEndYM()) && Integer.parseInt(ul._transToCE_Year(obj.getDeprYM()).substring(0, 4)) == Integer.parseInt(obj.getApportionEndYM().substring(0, 4)))
						{
							if (Integer.parseInt(obj.getBuyDate().substring(0, 4)) < Integer.parseInt(ul._transToCE_Year(obj.getDeprYM()).substring(0, 4))) {
								TempMonthDepr1 = Integer.parseInt(obj.getMonthDepr()) * (TempThisYear2 - 1) + Integer.parseInt(obj.getMonthDepr1());
							} else {
								TempMonthDepr1 = Integer.parseInt(obj.getMonthDepr()) * (TempLastYear - 1) + Integer.parseInt(obj.getMonthDepr1());
							}
							obj.setMonthDepr1(String.valueOf(TempMonthDepr1));
						}
						else
						{
							obj.setMonthDepr1("0");
						}			
						//處理情況二、monthdepr2
						if (Integer.parseInt(obj.getBuyDate().substring(0, 4)) < Integer.parseInt(obj.getEnterDate().substring(0, 4))) {
							if (Integer.parseInt(ul._transToCE_Year(obj.getDeprYM()).substring(0, 4)) <= Integer.parseInt(obj.getApportionEndYM().substring(0, 4)))
							{					
								TempMonthDepr2 = Integer.parseInt(obj.getMonthDepr()) * (TempLastYesr2);
								obj.setMonthDepr2(String.valueOf(TempMonthDepr2));
							} else {
								obj.setMonthDepr2(rs.getString("depramount"));
							}
						} else {
							obj.setMonthDepr2("0");
						}
						insertData(db2);
						doCount("MP", "S");
					}
				}
				//情況三以前年月購入（不含本月及上月），以前年月入帳（不含本月），且未逾「攤提年限截止年月」，提列1個月折舊
				else if (Integer.parseInt(obj.getBuyDate().substring(0, 6)) < Integer.parseInt(TempDate) 
						&& Integer.parseInt(obj.getEnterDate().substring(0, 6)) < Integer.parseInt(ul._transToCE_Year(obj.getDeprYM()).substring(0, 6)) 
						&& 
						(
							Integer.parseInt(obj.getApportionEndYM()) >= Integer.parseInt(ul._transToCE_Year(obj.getDeprYM()).substring(0, 6))
							||
							(
								// 截止年月 < 折舊年月
								Integer.parseInt(obj.getApportionEndYM()) < Integer.parseInt(ul._transToCE_Year(obj.getDeprYM()).substring(0, 6))
								&& 
								// 淨值 > 殘值
								Common.getLong(this.getOldNetValue()) > Common.getLong(this.getScrapValue())
								&& 
								// 已達年限財產折舊方法:1.不補提 2. 自動提列
								"2".equals(this.getTmpReachedYearCalMethod())
							)
						)
						&& "N".equals(checkGet(rs.getString("nodeprset"))))
				{
					if (Common.getInt(deprYM_to_YYYYMM) == Common.getInt(this.getApportionEndYM())) {
						obj.setMonthDepr1(checkGet(rs.getString("monthdepr1")));
					} else {
						if (Common.getInt(deprYM_to_YYYYMM) > Common.getInt(this.getApportionEndYM())) {
							// 已達年限自動提列折舊 : 淨值 - 殘值
							obj.setMonthDepr1(Common.get(Common.getLong(this.getOldNetValue()) - Common.getLong(this.getScrapValue())));
						} else {
							obj.setMonthDepr1(checkGet(rs.getString("monthdepr")));
						}
					}
					obj.setMonthDepr2("0");
					insertData(db2);
					doCount("MP", "S");
				} 
				//情況四本月不折舊資料
				else if (
					// 目前折舊－折舊方法(deprMethod)= '01' 
					"01".equals(obj.getDeprMethod()) ||
					// 原值(bookValue)=殘值(scrapValue) + 累計折舊(accumDepr)
					Common.getLong(obj.getBookValue()) == Common.getLong(obj.getScrapValue()) + Common.getLong(obj.getAccumDepr()) ||
					// 不折舊設定(noDeprSet)= 'Y'
					"Y".equals(checkGet(rs.getString("nodeprset"))) ||
					// 攤提年限截止年月(西元年)(apportionEndYM) < 畫面.deprYM.轉西元年
					Integer.parseInt(ul._transToCE_Year(obj.getDeprYM()).substring(0, 6)) > Integer.parseInt(obj.getApportionEndYM()) ||
					(//購置日期(西元年)(buyDate).年月 = 畫面.deprYM.轉西元年 and 入帳日期(西元年)(enterDate).年月 =畫面.deprYM.轉西元年
						Integer.parseInt(ul._transToCE_Year(obj.getDeprYM()).substring(0, 6)) == Integer.parseInt(obj.getBuyDate().substring(0, 6)) 
						&&
						Integer.parseInt(ul._transToCE_Year(obj.getDeprYM()).substring(0, 6)) == Integer.parseInt(obj.getEnterDate().substring(0, 6)) 
					) ||
					("B".equals(rs.getString("individualsetdepr")) && obj.getEnterDate().substring(0, 6).equals(rs.getString("verifyym")))
					) 
				{
					obj.setMonthDepr1("0");
					obj.setMonthDepr2("0");
					insertData(db2);
					doCount("MP", "S");
				} else {
					doCount("MP", "I");
				}
			}
			super.setState("init");
			this.batchInsertCommit();
		} catch (Exception e) {
			this.logger.error("動產每月折舊計算發生錯誤:" + this.getCurrentInfo(), e);
			this.insertRollBack();
			throw e;
		} finally {
			db.closeAll();
			db2.closeAll();
			
		}
	}
	
	/**
	 * 計算 權利
	 */
	private void QueryRTTable() throws Exception {
		
		UNTCH_Tools ul = new UNTCH_Tools();
		Database db = new Database();
		Database db2 = new Database();
		ResultSet rs = null;
		
		/** 畫面 deprYM  省掉每次都要呼叫 this.getDeprYM() **/
		String deprYM = this.getDeprYM();
		/** 畫面deprym -1 個月   轉西元年  ex:畫面輸入10408 ->  201507 **/
		String previousDeprYM_to_YYYYMM = ul._transToCE_Year(Datetime.getDateAdd("m", -1, deprYM + "01").substring(0, 5));
		/** 畫面deprym 轉西元年前6碼        ex:畫面輸入10408 -> 201508  **/
		String deprYM_to_YYYYMM = ul._transToCE_Year(deprYM).substring(0, 6);
		/** 畫面deprYM 取 其西元年     ex:畫面輸入 10408   ->  2015  **/
		String deprYM_to_YYYY = ul._transToCE_Year(deprYM).substring(0, 4);
		
		try {
			
			String sql = this.getQueryRTSQL(deprYM_to_YYYYMM);
			rs = db.querySQL_NoChange(sql);
			while (rs.next()) {
				String nodeprset = Common.get(rs.getString("nodeprset"));
				//#region 將此筆資料設給this ...什麼鬼寫法=口=  
				doCount("RT", "P");
				
				this.setTmpEnterorg(checkGet(rs.getString("enterorg")));
				this.setTmpOwnership(checkGet(rs.getString("ownership")));
				this.setTmpDifferencekind(checkGet(rs.getString("differencekind")));
				this.setTmpPropertyno(checkGet(rs.getString("propertyno")));
				this.setTmpSerialno(checkGet(rs.getString("serialno")));
				
				this.setEnterOrg(Common.get(rs.getString("enterorg")));
				this.setOwnership(Common.get(rs.getString("ownership")));
				this.setDifferenceKind(Common.get(rs.getString("differencekind")));
				this.setPropertyNo(Common.get(rs.getString("propertyno")));
				this.setSerialNo(Common.get(rs.getString("serialno")));
				this.setSerialNo1(null == rs.getString("serialno1") ? "1" : rs.getString("serialno1"));
				this.setDeprFrequency(null == rs.getString("deprfrequency") ? "0" : rs.getString("deprfrequency"));		
				this.setPropertyKind(Common.get(rs.getString("propertykind")));
				this.setFundType(Common.get(rs.getString("fundtype")));
				this.setValuable(Common.get(rs.getString("valuable")));
				this.setDeprMethod(Common.get(rs.getString("deprmethod")));
				this.setBuildFeeCB(Common.get(rs.getString("buildfeecb")));
				this.setDeprUnitCB(Common.get(rs.getString("deprunitcb")));
				this.setDeprPark(Common.get(rs.getString("deprpark")));
				this.setDeprUnit(Common.get(rs.getString("deprunit")));
				this.setDeprUnit1(Common.get(rs.getString("deprunit1")));
				this.setDeprAccounts(Common.get(rs.getString("depraccounts")));
				this.setOriginalBV(Common.get(rs.getString("originalbv")));
				this.setBookValue(Common.get(rs.getString("bookvalue")));
				this.setScrapValue(Common.get(rs.getString("scrapvalue")));
				this.setDeprAmount(Common.get(rs.getString("depramount")));
				this.setApportionMonth(Common.get(rs.getString("apportionmonth")));
				this.setOldNetValue(Common.get(rs.getString("netvalue")));
				this.setAccumDepr(Common.get(rs.getString("accumDepr")));
				this.setReduceAccumDepr(Common.get(rs.getString("reduceaccumdepr")));
				this.setMonthDepr1(rs.getString("monthdepr1"));
				this.setMonthDepr(Common.get(rs.getString("monthdepr")));
				this.setBuyDate(Common.get(rs.getString("buydate")));
				this.setEnterDate(Common.get(rs.getString("enterdate")));
				this.setSourceDate(Common.get(rs.getString("sourcedate")));
				this.setPropertyName1(Common.get(rs.getString("propertyname1")));
				this.setLimitYear(null == rs.getString("limitYear") ? "null" : rs.getString("limitYear"));
				this.setDeprPercent("100");
				this.setScaledAddAccumDepr("0");
				this.setKeeper(Common.get(rs.getString("keeper")));
				this.setKeepUnit(Common.get(rs.getString("keepunit")));
				this.setOldpropertyno(Common.get(rs.getString("oldpropertyno")));
				this.setOldserialno(Common.get(rs.getString("oldserialno")));
				this.setOldlotno(Common.get(rs.getString("oldlotno")));
				this.setOldmonthdepr(Common.get(rs.getString("monthdepr")));
				this.setOldmonthdepr1(Common.get(rs.getString("monthdepr1")));

				if ("111".equals(checkGet(rs.getString("differencekind"))) || "113".equals(checkGet(rs.getString("differencekind")))) {
					this.setEscrowOriLimitMonth(Common.get(rs.getString("originalapportionmonth")));
				} else {
					this.setEscrowOriLimitMonth("null");
				}	
				this.setEscrowOriValue(Common.get(rs.getString("escroworivalue")));
				this.setEscrowOriAccumDepr(Common.get(rs.getString("escrowOriaccumdepr")));
				this.setEngineeringNo(Common.get(rs.getString("engineeringno")));
				this.setNotes(Common.get(rs.getString("notes")));
				this.setEditID(Common.get(rs.getString("editid")));
				this.setEditDate(Common.get(rs.getString("editdate")));
				this.setEditTime(Common.get(rs.getString("edittime")));
				this.setApportionEndYM(Common.get(rs.getString("apportionendym")));
				this.setAddAccumDepr("0");
				this.setBookAmount("1");
				this.setPropertyTypeTemp("8");
				this.setVerify("N");
				this.setLotNo("");
				this.setNodeprset(rs.getString("nodeprset"));
				//#end 

				if (this.isSituationZero(nodeprset)) {
					//#region 情況零：折舊方法為 01  或 不折舊設定為 Y  原本屬於情況四, 獨立出來後放在最前面 而其他情況四移至情況三之後                   
					this.setMonthDepr1("0");
					this.setMonthDepr2("0");
					insertData(db2);
					doCount("RT", "S");
					//#end 
				} else if (this.isSituationOne(previousDeprYM_to_YYYYMM, nodeprset)) {
					//#region 情況一：上月購入，上月入帳，本月開始提列折舊               
					if (Common.getInt(deprYM_to_YYYYMM) == Common.getInt(this.getApportionEndYM())) {
						this.setMonthDepr1(checkGet(rs.getString("monthdepr1"))); // 最後一個月
					} else {
						this.setMonthDepr1(checkGet(rs.getString("monthdepr")));
					}
					this.setMonthDepr2("0");
					insertData(db2);
					doCount("RT", "S");
					//#end 
				} else if (this.isSituationTwo(deprYM_to_YYYYMM, nodeprset) && !"B".equals(rs.getString("individualsetdepr"))) {
					//情況二：以前年月購入(不含本月)，本月入帳，本月補提折舊
					// individualsetdepr為自行設定累計折舊，A為僅提列當月折舊
					if ("A".equals(rs.getString("individualsetdepr"))) {
						long leftvalue = Common.getLong(this.getOldNetValue()) - Common.getLong(this.getScrapValue()); //剩餘價值
						if (Common.getInt(deprYM_to_YYYYMM) == Common.getInt(this.getApportionEndYM())) { //當月為最後一個月
							this.setMonthDepr1(checkGet(rs.getString("monthdepr1")));
						} else {
							if (Common.getInt(deprYM_to_YYYYMM) > Common.getInt(this.getApportionEndYM())) { //已折完
								// 已達年限自動提列折舊 : 淨值 - 殘值
								this.setMonthDepr1(Common.get(leftvalue));
							} else { //正常折舊
								this.setMonthDepr1(checkGet(rs.getString("monthdepr")));
							}
						}
						// 如果攤提金額大於剩餘金額，僅折剩餘金額
						if (Common.getLong(this.getMonthDepr1()) > leftvalue) {
							this.setMonthDepr1(Common.get(leftvalue));
						}
						
						this.setMonthDepr2("0"); 
						insertData(db2);
						doCount("RT", "S");
					} else {
						//#region 前置參數處理  
						String ROCBuyDate = ul._transToROC_Year(this.getBuyDate().substring(0, 6));
						String ROCApportionEndYM = ul._transToROC_Year(this.getApportionEndYM().substring(0, 6));
						int TempThisYear = Datetime.BetweenTwoMonth(deprYM.substring(0, 3) + "01", deprYM) + 1;
						int TempThisYear2 = Datetime.BetweenTwoMonth(ROCApportionEndYM.substring(0, 3) + "01", ROCApportionEndYM) + 1;
						int TempLastYear = Datetime.BetweenTwoMonth(ROCBuyDate, deprYM);
						int TempLastYesr2 = Datetime.BetweenTwoMonth(ROCBuyDate, Datetime.getDateAdd("m", -1, deprYM.substring(0, 3) + "0101").substring(0, 5));
						int TempMonthDepr1 = 0;
						int TempMonthDepr2 = 0;
						//#end 
						//#region 處理  MonthDepr1  
						if (Integer.parseInt(deprYM_to_YYYYMM) < Integer.parseInt(this.getApportionEndYM())) {
							if (Integer.parseInt(this.getBuyDate().substring(0, 4)) < Integer.parseInt(ul._transToCE_Year(deprYM).substring(0, 4))) {
								// 以前年度購入
								TempMonthDepr1 = Integer.parseInt(this.getMonthDepr()) * TempThisYear;
							} else {
								TempMonthDepr1 = Integer.parseInt(this.getMonthDepr()) * TempLastYear;
							}
							this.setMonthDepr1(String.valueOf(TempMonthDepr1));
						} else if (Integer.parseInt(deprYM_to_YYYYMM) >= Integer.parseInt(this.getApportionEndYM()) && 
								   Integer.parseInt(deprYM_to_YYYY) == Integer.parseInt(this.getApportionEndYM().substring(0, 4))) {
							// 畫面deprYM >= 折舊截止年月    且    畫面deprYM年度 == 折舊截止年月的年
							if (Integer.parseInt(this.getBuyDate().substring(0, 4)) < Integer.parseInt(deprYM_to_YYYY)) {
								// 以前年度購入
								TempMonthDepr1 = Integer.parseInt(this.getMonthDepr()) * (TempThisYear2 - 1) + Integer.parseInt(this.getMonthDepr1());
							} else {
								// 非以前年度購入
								TempMonthDepr1 = Integer.parseInt(this.getMonthDepr()) * (TempLastYear-1) + Integer.parseInt(this.getMonthDepr1());
							}
							this.setMonthDepr1(String.valueOf(TempMonthDepr1));
						} else {
							this.setMonthDepr1("0");
						}
						//#end 
						//#region 處理  monthdepr2  
						if (Integer.parseInt(this.getBuyDate().substring(0, 4)) < Integer.parseInt(this.getEnterDate().substring(0, 4))) {
							// 非購入年 入帳
							if (Integer.parseInt(deprYM_to_YYYY) <= Integer.parseInt(this.getApportionEndYM().substring(0, 4))) {
								TempMonthDepr2 = Integer.parseInt(this.getMonthDepr()) * (TempLastYesr2);
								this.setMonthDepr2(String.valueOf(TempMonthDepr2));
							} else {
								this.setMonthDepr2(rs.getString("depramount"));
							}
						} else {
							this.setMonthDepr2("0");
						}
						//#end 
						insertData(db2);
						doCount("RT", "S");
					}
				} else if (this.isSituationThree(previousDeprYM_to_YYYYMM, deprYM_to_YYYYMM, nodeprset)) {
					//#region 情況三以前年月購入（不含本月及上月），以前年月入帳（不含本月），且未逾「攤提年限截止年月」，提列1個月折舊    
					if (Common.getInt(deprYM_to_YYYYMM) == Common.getInt(this.getApportionEndYM())) { // 最後一個月
						this.setMonthDepr1(Common.get(rs.getString("monthdepr1")));
					} else {
						if (Common.getInt(deprYM_to_YYYYMM) > Common.getInt(this.getApportionEndYM())) {
							// 已達年限自動提列折舊 : 淨值 - 殘值
							this.setMonthDepr1(Common.get(Common.getLong(this.getOldNetValue()) - Common.getLong(this.getScrapValue())));
						} else {
							this.setMonthDepr1(checkGet(rs.getString("monthdepr")));
						}
					}
					this.setMonthDepr2("0");
					insertData(db2);
					doCount("RT", "S");
					//#end
				} else if (this.isSituationFour(deprYM_to_YYYYMM) || ("B".equals(rs.getString("individualsetdepr")) && deprYM_to_YYYYMM.equals(rs.getString("verifyym")))) {
					//#region 情況四：本月不折舊資料                   
					this.setMonthDepr1("0");
					this.setMonthDepr2("0");
					insertData(db2);
					doCount("RT", "S");
					//#end 
				} else {
					doCount("RT", "I");
				}
			}
			super.setState("init");
			this.batchInsertCommit();
		} catch (Exception e) {
			this.logger.error("權利折舊計算時發生錯誤:" + this.getCurrentInfo(), e);
			this.insertRollBack();
			throw e;
		} finally {
			//#region clear all
			if (rs != null) {
				try {
					rs.close();
				} catch (Exception e) {
					//ignore
				}
			}
			
			if (db != null) {
				db.closeAll();
			}
			
			if (db2 != null) {
				db2.closeAll();
			}
			
			
			//#end 
		}
	}
	

	/**
	 * 將結果新增至每月折舊檔
	 * @param db2
	 * @throws Exception
	 */
	@SuppressWarnings("unused")
	private void insertData(Database db2) throws Exception {

		
		String insertsql = "";
		try {
			UNTDP001F obj = this;
			UNTCH_Tools ul = new UNTCH_Tools();
			ResultSet rs;
			ResultSet rs2;
			String CEdeprYM = ul._transToCE_Year(getDeprYM());
			String lastMonthYYYYMM = NewDateUtil.plusDate(CEdeprYM, "m", -1, NewDateUtil.DateFormat.YYYYMM);
			
			//計算上月累計折舊oldAccumDepr    =  目前折舊－累計折舊(財產主檔)    +   減少累計折舊(財產主檔)   -  本月累計折舊增加
			long oldAccumDeprI = Long.parseLong(obj.getAccumDepr()) + Long.parseLong(obj.getReduceAccumDepr()) - Long.parseLong(obj.getAddAccumDepr());
			
			//計算本月折舊monthDepr   =  本月折舊(本年度)  +  本月折舊(以前年度)
			long monthDeprI = Long.parseLong(obj.getMonthDepr1()) + Long.parseLong(obj.getMonthDepr2());
	
			//計算本期累計折舊newAccumDepr
			long newAccumDeprI = Long.parseLong(obj.getAccumDepr()) + monthDeprI;
			
			//計算折舊後淨值
			long newNetValueI = Long.parseLong(obj.getOldNetValue()) - monthDeprI;
			
			//取得原值 (1080801 問題單1840)
			long BookValue = Long.parseLong(obj.getBookValue());
			
			long scaledBookValueI = 0;//依比例－原值
			long scaledOldNetValueI = 0;//依比例－折舊前淨值
			long scaledOldAccumDeprI = 0;//依比例－上月累計折舊
			long scaledReduceAccumDeprI = 0;//依比例－本月累計折舊減少
			long scaledMonthDepr1I = 0;//依比例－本月折舊(本年度)
			long scaledMonthDepr2I = 0;//依比例－本月折舊(以前年度)
			long scaledMonthDeprI = 0;//依比例－本月折舊
			long scaledNewAccumDeprI=0;//依比例－本期累計折舊
			long scaledNewNetValueI=0;//依比例－折舊後淨值
			long countserialno1=0;
			long countdeprfrequency=0;
			
			//1080801 問題單1840 折舊計算當處理出現新淨值(newNetValue)小於0(累計折舊newAccumDepr大於原值bookValue)，請顯示錯誤訊息，並停止折舊計算(還原未計算前)
			if (newNetValueI >= 0 && BookValue - newAccumDeprI >= 0) {
				if (obj.getDeprUnitCB().equals("Y")) {// 依比例
					
					// 是否為最後一次折舊
					boolean isLastDepr = false;
					if (Common.getInt(this.getApportionEndYM()) <= Common.getInt(ul._transToCE_Year(this.deprYM).substring(0, 6))) {
						isLastDepr = true;
					}
					
					// 取得比例分攤檔
					String QueryPercentSql = " select "
							+ "(select count(1) from UNTCH_DEPRPERCENT b "
							+ "where a.enterorg=b.enterorg "
							+ "and a.ownership = b.ownership "
							+ "and a.differencekind = b.differencekind "
							+ "and a.propertyno=b.propertyno "
							+ "and a.serialno=b.serialno "
							+ "and a.ishistory = 'N') as cnt,"
								+ "deprpercent,"
								+ "deprpark,"
								+ "deprunit,"
								+ "deprunit1,"
								+ "depraccounts, "
								+ "deprshareamount "
								+ "from UNTCH_DEPRPERCENT a "
								+ "where ishistory='N' and " +
								 " enterorg=" + Common.sqlChar(obj.getEnterOrg()) + " and " +
								 " ownership=" + Common.sqlChar(obj.getOwnership()) + " and " +
								 " differencekind=" + Common.sqlChar(obj.getDifferenceKind()) + " and " +
								 " propertyno=" + Common.sqlChar(obj.getPropertyNo()) + " and " +
								 " serialno=" + Common.sqlChar(obj.getSerialNo()) +
								 "";
					rs = db2.querySQL_NoChange(QueryPercentSql);
					
					long scaledBookValueI_TOT = 0;
					long scaledOldNetValueI_TOT = 0;
					long scaledOldAccumDeprI_TOT = 0;
					long scaledReduceAccumDeprI_TOT = 0;
					long scaledMonthDepr1I_TOT = 0;
					long scaledMonthDepr2I_TOT = 0;
					long scaledMonthDeprI_TOT = 0;
					long i = 0;
					List<String> sqllist = new ArrayList<String>();
		
					while (rs.next()) {
						i++;
						countserialno1 = i+Long.parseLong(obj.getSerialNo1());
						countdeprfrequency = 0;
						obj.setDeprPercent(checkGet(rs.getString("deprpercent")));
						obj.setDeprPark(Common.get(rs.getString("deprpark")));
						obj.setDeprUnit(checkGet(rs.getString("deprunit")));
						obj.setDeprUnit1(checkGet(rs.getString("deprunit1")));
						obj.setDeprAccounts(checkGet(rs.getString("depraccounts")));
						double Percent = rs.getDouble("deprpercent") / 100;
						
						//#region 此處欄位不管是否為最後一筆分攤，算法都一樣
						scaledBookValueI = Common.getLong(rs.getString("deprshareamount"));
						scaledOldAccumDeprI = UNTDPUtil.getLastScalednewAccumDepr(obj.getEnterOrg(), obj.getOwnership(), obj.getDifferenceKind(), obj.getPropertyNo(), obj.getSerialNo(), obj.getDeprPark(), obj.getDeprUnit(), obj.getDeprUnit1(), obj.getDeprAccounts());
						scaledOldNetValueI = scaledBookValueI - scaledOldAccumDeprI;
						//#end
						
						// 分攤的最後一筆
						if (i == rs.getInt("cnt")) {
							scaledReduceAccumDeprI = Long.parseLong(obj.getReduceAccumDepr()) - scaledReduceAccumDeprI_TOT;
							scaledMonthDepr1I = Long.parseLong(obj.getMonthDepr1()) - scaledMonthDepr1I_TOT;
							scaledMonthDepr2I = Long.parseLong(obj.getMonthDepr2()) - scaledMonthDepr2I_TOT;
							scaledMonthDeprI = scaledMonthDepr1I + scaledMonthDepr2I;
							scaledNewAccumDeprI = scaledOldAccumDeprI + Long.parseLong(obj.getScaledAddAccumDepr()) - scaledReduceAccumDeprI + scaledMonthDeprI;
							scaledNewNetValueI = scaledOldNetValueI-scaledMonthDeprI;
							
							// 如果是最後一次攤提, 需要檢查 總累折是否與依比例計算的原值相等, 若不相等則要做相對應的調整 
							if (isLastDepr) {
								long diffVal = scaledBookValueI - scaledNewAccumDeprI; // 取得bv 與 accumdepr的差額
								if (diffVal != 0 && scaledMonthDeprI > 0) { // 如果有提列金額 且 差額不等於 0 , 修正月提金額
									// 修正月提金額
									scaledMonthDeprI = scaledMonthDeprI + diffVal;
									
									// 重新算 累折
									scaledNewAccumDeprI = scaledOldAccumDeprI + Long.parseLong(obj.getScaledAddAccumDepr()) - scaledReduceAccumDeprI + scaledMonthDeprI;
									
									// 折舊後淨值直接給0
									scaledNewNetValueI = 0;
								}
							}
						} else {
							scaledReduceAccumDeprI = BigDecimal.valueOf(Double.parseDouble(obj.getReduceAccumDepr()) * Percent).setScale(0, RoundingMode.FLOOR).longValue();
							scaledMonthDepr1I = BigDecimal.valueOf(scaledOldNetValueI / Double.parseDouble((obj.getApportionMonth()).equals("0")?"1":obj.getApportionMonth())).setScale(0, RoundingMode.FLOOR).longValue();
							scaledMonthDepr2I = BigDecimal.valueOf(Double.parseDouble(obj.getMonthDepr2()) * Percent).setScale(0, RoundingMode.FLOOR).longValue();
							scaledMonthDeprI = scaledMonthDepr1I + scaledMonthDepr2I;
							scaledNewAccumDeprI = scaledOldAccumDeprI + Long.parseLong(obj.getScaledAddAccumDepr()) - scaledReduceAccumDeprI + scaledMonthDeprI;
							scaledNewNetValueI = scaledOldNetValueI - scaledMonthDeprI;
							// 如果是最後一次攤提, 需要檢查 總累折是否與依比例計算的原值相等, 若不相等則要做相對應的調整 
							if (isLastDepr) {
								long diffVal = scaledBookValueI - scaledNewAccumDeprI; // 取得bv 與 accumdepr的差額
								if (diffVal != 0 && scaledMonthDeprI > 0) { // 如果有提列金額 且 差額不等於 0 , 修正月提金額
									// 修正月提金額
									scaledMonthDeprI = scaledMonthDeprI + diffVal;
									
									// 重新算 累折
									scaledNewAccumDeprI = scaledOldAccumDeprI + Long.parseLong(obj.getScaledAddAccumDepr()) - scaledReduceAccumDeprI + scaledMonthDeprI;
									
									// 折舊後淨值直接給0
									scaledNewNetValueI = 0;
								}
							}
		
							scaledBookValueI_TOT += scaledBookValueI;
							scaledOldNetValueI_TOT += scaledOldNetValueI;
							scaledOldAccumDeprI_TOT += scaledOldAccumDeprI;
							scaledReduceAccumDeprI_TOT += scaledReduceAccumDeprI;
							scaledMonthDepr1I_TOT += scaledMonthDepr1I;
							scaledMonthDepr2I_TOT += scaledMonthDepr2I;
							scaledMonthDeprI_TOT +=scaledMonthDeprI;
						}
		
						
						this.excutePreparedStatement(CEdeprYM,countserialno1,countdeprfrequency,oldAccumDeprI,monthDeprI,newAccumDeprI,newNetValueI,
			                                  		scaledBookValueI,scaledOldNetValueI,scaledOldAccumDeprI,scaledReduceAccumDeprI,
			                                  		scaledMonthDepr1I,scaledMonthDepr2I,scaledMonthDeprI,scaledNewAccumDeprI,scaledNewNetValueI);
					}
		
				} else {
					// 非依比例
					countserialno1 = 1 + Long.parseLong(obj.getSerialNo1());
					countdeprfrequency = 0;
					this.excutePreparedStatement(CEdeprYM,countserialno1,countdeprfrequency,oldAccumDeprI,monthDeprI,newAccumDeprI,newNetValueI,
	                  							scaledBookValueI,scaledOldNetValueI,scaledOldAccumDeprI,scaledReduceAccumDeprI,
	                  							scaledMonthDepr1I,scaledMonthDepr2I,scaledMonthDeprI,scaledNewAccumDeprI,scaledNewNetValueI);
				}
				
			} else {
				//1080801 問題單1840
				if (newNetValueI < 0 && BookValue - newAccumDeprI >= 0) {
					throw new Exception("新淨值 - 每月折舊 < 0");
				} else if (BookValue - newAccumDeprI < 0 && newNetValueI >= 0) {
					throw new Exception("原值 - 累計折舊 < 0");
				} else {
					throw new Exception("新淨值 - 每月折舊 < 0  且  原值 - 累計折舊 < 0 ");
				}
			}
			
		} catch (Exception e) {
			//為節省效能將Exception隱藏，Debug時請自行打開
			//throw e;
			throw new Exception(e.getMessage());
		}
	}
	
	
	
	/**
	 * 產生每月折舊檔的 insert SQL
	 * @return
	 */
	private String insertSql(String CEdeprYM, long countserialno1, long countdeprfrequency, long oldAccumDeprI, long monthDeprI,
			                long newAccumDeprI, long newNetValueI, long scaledBookValueI, long scaledOldNetValueI, 
			                long scaledOldAccumDeprI, long scaledReduceAccumDeprI, long scaledMonthDepr1I, long scaledMonthDepr2I,
			                long scaledMonthDeprI, long scaledNewAccumDeprI, long scaledNewNetValueI) {
		UNTDP001F obj = this;
		String insertSql = " insert into UNTDP_MONTHDEPR ( " +
				" enterorg," +
				" ownership," +
				" differencekind," +
				" propertyno," +
				" serialno," +
				" lotno," +
				" serialno1," +
				" deprym," +
				" deprfrequency," +
				" verify," +
				" propertytype," +
				" propertykind," +
				" fundtype," +
				" valuable," +
				" deprmethod," +
				" buildfeecb," +
				" deprunitcb," +
				" deprpark," +
				" deprunit," +
				" deprunit1," +
				" depraccounts," +
				" deprpercent," +
				" originalbv," +
				" bookvalue," +
				" scrapvalue," +
				" depramount," +
				" apportionmonth," +
				" oldnetvalue," +
				" oldaccumdepr," +
				" addaccumdepr," +
				" reduceaccumdepr," +
				" monthdepr1," +
				" monthdepr2," +
				" monthdepr," +
				" newaccumdepr," +
				" newnetvalue," +
				" scaledbookvalue," +
				" scaledoldnetvalue," +
				" scaledoldaccumdepr," +
				" scaledaddaccumdepr," +
				" scaledreduceaccumdepr," +
				" scaledmonthdepr1," +
				" scaledmonthdepr2," +
				" scaledmonthdepr," +
				" scalednewaccumdepr," +
				" scalednewnetvalue," +
				" buydate," +
				" enterdate," +
				" sourcedate," +
				" propertyname1," +
				" limityear," +
				" bookamount," +
				" keepunit," +
				" keeper," +
				" escroworilimitmonth," +
				" escroworivalue," +
				" escroworiaccumdepr," +
				" engineeringno," +
				" notes," +
				" editid," +
				" editdate," +
				" edittime," +
				" oldpropertyno," +
				" oldserialno," +
				" oldlotno," +
				" oldmonthdepr," +
				" oldmonthdepr1" +
				" )values(" +
				Common.sqlChar(obj.getEnterOrg()) + ", "+
				Common.sqlChar(obj.getOwnership()) + ", "+
				Common.sqlChar(obj.getDifferenceKind()) + ", "+
				Common.sqlChar(obj.getPropertyNo()) + ", "+
				Common.sqlChar(obj.getSerialNo()) + ", "+
				Common.sqlChar(obj.getLotNo()) + ", "+
				countserialno1 + ", "+
				Common.sqlChar(CEdeprYM) + ", "+
				" 0 , "+
				Common.sqlChar(obj.getVerify()) + ", "+
				Common.sqlChar(obj.getPropertyTypeTemp()) + ", "+
				Common.sqlChar(obj.getPropertyKind()) + ", "+
				Common.sqlChar(obj.getFundType()) + ", "+
				Common.sqlChar(obj.getValuable()) + ", "+
				Common.sqlChar(obj.getDeprMethod()) + ", "+
				Common.sqlChar(obj.getBuildFeeCB()) + ", "+
				Common.sqlChar(obj.getDeprUnitCB()) + ", "+
				Common.sqlChar(obj.getDeprPark()) + ", "+
				Common.sqlChar(obj.getDeprUnit()) + ","+
				Common.sqlChar(obj.getDeprUnit1()) + ", "+
				Common.sqlChar(obj.getDeprAccounts()) + ", "+
				obj.getDeprPercent() + ", "+
				obj.getOriginalBV() + ", "+
				obj.getBookValue() + ", "+
				obj.getScrapValue() + ", "+
				obj.getDeprAmount() + ", "+
				obj.getApportionMonth() + ", "+
				obj.getOldNetValue() + ", "+					   
				oldAccumDeprI + ", "+
				obj.getAddAccumDepr() + ", "+
				obj.getReduceAccumDepr() + ", "+
				obj.getMonthDepr1() + ", "+
				obj.getMonthDepr2() + ", "+
				monthDeprI + ", "+
				newAccumDeprI + ", "+
				newNetValueI + ", "+
				scaledBookValueI + ", "+
				scaledOldNetValueI + ", "+
				scaledOldAccumDeprI + ", "+
				obj.getScaledAddAccumDepr() + ", "+
				scaledReduceAccumDeprI + ", "+
				scaledMonthDepr1I + ", "+
				scaledMonthDepr2I + ", "+
				scaledMonthDeprI + ", "+
				scaledNewAccumDeprI + ", "+
				scaledNewNetValueI + ", "+
				Common.sqlChar(obj.getBuyDate()) + ", "+
				Common.sqlChar(obj.getEnterDate()) + ", "+
				Common.sqlChar(obj.getSourceDate()) + ", "+
				Common.sqlChar(obj.getPropertyName1()) + ", "+
				obj.getLimitYear() + ", "+
				obj.getBookAmount() + ", "+
				Common.sqlChar(obj.getKeepUnit()) + ", "+
				Common.sqlChar(obj.getKeeper()) + ", "+
				obj.getEscrowOriLimitMonth() + ", "+
				obj.getEscrowOriValue() + ", "+
				obj.getEscrowOriAccumDepr() + ", "+
				Common.sqlChar(obj.getEngineeringNo()) + ", "+
				Common.sqlChar(obj.getNotes()) + ", "+
				Common.sqlChar(obj.getEditID()) + ", "+
				Common.sqlChar(obj.getEditDate()) + ", "+
				Common.sqlChar(obj.getEditTime()) + ", "+
				Common.sqlChar(obj.getOldpropertyno()) + ", "+
				Common.sqlChar(obj.getOldserialno()) + ", "+
				Common.sqlChar(obj.getOldlotno()) + ", "+
				Common.sqlChar(obj.getOldmonthdepr()) + ", "+
				Common.sqlChar(obj.getOldmonthdepr1()) + ") "+
				"";

		//System.out.println("insertSql==>"+insertSql);
		return insertSql;
	}
	
	/**
	 * 公務類_折舊年月:10501 的<b>情形一</b>： 以前年月購入(<=10412)，且已入帳，本月(10501)補提折舊
	 * @return
	 */
	private boolean isSPSituationOne(String nodeprset, String previousDeprYM_to_YYYYMM, String deprYM_to_YYYYMM) {
		return "N".equals(nodeprset) &&
				// 以前年月購入
				Integer.parseInt(this.getBuyDate().substring(0, 6)) <= Integer.parseInt(previousDeprYM_to_YYYYMM) &&
				// 已入帳
				Integer.parseInt(this.getEnterDate().substring(0, 6)) <= Integer.parseInt(deprYM_to_YYYYMM);
	}
	
	/**
	 * 公務類_折舊年月:10501的<b>情形二</b>：不必折舊但仍需寫入每月折舊檔供對帳用
	 * @return
	 */
	private boolean isSPSituationTwo(String nodeprset, String deprYM_to_YYYYMM) {
		return // 折舊方法: 不必折舊
			   "01".equals(this.getDeprMethod()) ||
			   // 不折舊設定 = Y
			   "Y".equals(nodeprset) ||
			   (// 購置日期(西元年)(buyDate).年月 = 畫面.deprYM.轉西元年 and 入帳日期(西元年)(enterDate).年月 =畫面.deprYM.轉西元年
				Integer.parseInt(deprYM_to_YYYYMM) == Integer.parseInt(this.getBuyDate().substring(0, 6)) 
				&&
				Integer.parseInt(deprYM_to_YYYYMM) == Integer.parseInt(this.getEnterDate().substring(0, 6))
			   );
	}
	
	/**
	 * 公務類_土改_折舊年月:10501 : 公務類土改第一次算折舊的特殊折舊計算(會需要補提折舊)
	 */
	private void QueryRFTableForSP() throws Exception {
		
		UNTCH_Tools ul = new UNTCH_Tools();
		ResultSet rs = null;
		Database db = null;
		Database db2 = null;
		
		/** 畫面 deprYM  省掉每次都要呼叫 this.getDeprYM() **/
		String deprYM = this.getDeprYM();
		/** 畫面deprym -1 個月   轉西元年  ex:畫面輸入10408 ->  201507 **/
		String previousDeprYM_to_YYYYMM = ul._transToCE_Year(Datetime.getDateAdd("m", -1, deprYM + "01").substring(0, 5));
		/** 畫面deprym 轉西元年前6碼        ex:畫面輸入10408 -> 201508  **/
		String deprYM_to_YYYYMM = ul._transToCE_Year(deprYM).substring(0, 6);
		
		try {
			db = new Database();
			db2 = new Database();
			
			rs = db.querySQL_NoChange(this.getQueryRFSQL(deprYM_to_YYYYMM));
			while (rs.next()) {
				doCount("RF", "P");
				//#region set Field to this  
				this.setTmpEnterorg(checkGet(rs.getString("enterorg")));
				this.setTmpOwnership(checkGet(rs.getString("ownership")));
				this.setTmpDifferencekind(checkGet(rs.getString("differencekind")));
				this.setTmpPropertyno(checkGet(rs.getString("propertyno")));
				this.setTmpSerialno(checkGet(rs.getString("serialno")));
				
				this.setEnterOrg(checkGet(rs.getString("enterorg")));
				this.setOwnership(checkGet(rs.getString("ownership")));
				this.setDifferenceKind(checkGet(rs.getString("differencekind")));
				this.setPropertyNo(checkGet(rs.getString("propertyno")));
				this.setSerialNo(checkGet(rs.getString("serialno")));
				this.setSerialNo1((null==rs.getString("serialno1")?"1":rs.getString("serialno1")));
				this.setDeprFrequency((null==rs.getString("deprFrequency")?"0":rs.getString("deprFrequency")));		
				this.setPropertyKind(checkGet(rs.getString("propertykind")));
				this.setFundType(checkGet(rs.getString("fundtype")));
				this.setValuable(checkGet(rs.getString("valuable")));
				this.setDeprMethod(checkGet(rs.getString("deprmethod")));
				this.setBuildFeeCB(checkGet(rs.getString("buildfeecb")));
				this.setDeprUnitCB(checkGet(rs.getString("deprunitcb")));
				this.setDeprPark(checkGet(rs.getString("deprpark")));
				this.setDeprUnit(checkGet(rs.getString("deprunit")));
				this.setDeprUnit1(checkGet(rs.getString("deprunit1")));
				this.setDeprAccounts(checkGet(rs.getString("depraccounts")));
				this.setOriginalBV(checkGet(rs.getString("originalbv")));
				this.setBookValue(checkGet(rs.getString("bookvalue")));
				this.setScrapValue(checkGet(rs.getString("scrapvalue")));
				this.setDeprAmount(checkGet(rs.getString("depramount")));
				this.setApportionMonth(checkGet(rs.getString("apportionmonth")));
				this.setOldNetValue(checkGet(rs.getString("netvalue")));
				this.setAccumDepr(checkGet(rs.getString("accumDepr")));
				this.setReduceAccumDepr(checkGet(rs.getString("reduceAccumDepr")));
				this.setMonthDepr1(rs.getString("monthdepr1"));
				this.setMonthDepr(checkGet(rs.getString("monthdepr")));
				this.setBuyDate(checkGet(rs.getString("buydate")));
				this.setEnterDate(checkGet(rs.getString("enterdate")));
				this.setSourceDate(checkGet(rs.getString("sourcedate")));
				this.setPropertyName1(checkGet(rs.getString("propertyname1")));
				this.setLimitYear(null==rs.getString("limitYear")?"null":rs.getString("limitYear"));
				this.setDeprPercent("100");
				this.setScaledAddAccumDepr("0");
				this.setKeeper(checkGet(rs.getString("keeper")));
				this.setKeepUnit(checkGet(rs.getString("keepunit")));
				this.setOldpropertyno(checkGet(rs.getString("oldpropertyno")));
				this.setOldserialno(checkGet(rs.getString("oldserialno")));
				this.setOldlotno(checkGet(rs.getString("oldlotno")));
				this.setOldmonthdepr(checkGet(rs.getString("monthdepr")));
				this.setOldmonthdepr1(checkGet(rs.getString("monthdepr1")));

				if ("111".equals(checkGet(rs.getString("differencekind"))) || "113".equals(checkGet(rs.getString("differencekind")))) {
					this.setEscrowOriLimitMonth(checkGet(rs.getString("originalapportionmonth")));
				} else {
					this.setEscrowOriLimitMonth("null");
				}	
				this.setEscrowOriValue(checkGet(rs.getString("escroworivalue")));
				this.setEscrowOriAccumDepr(checkGet(rs.getString("escrowOriaccumdepr")));
				this.setEngineeringNo(checkGet(rs.getString("engineeringno")));
				this.setNotes(checkGet(rs.getString("notes")));
				this.setEditID(checkGet(rs.getString("editid")));
				this.setEditDate(checkGet(rs.getString("editdate")));
				this.setEditTime(checkGet(rs.getString("edittime")));
				this.setApportionEndYM(checkGet(rs.getString("apportionendym")));
				this.setAddAccumDepr("0");
				this.setBookAmount("1");
				this.setPropertyTypeTemp("2"); // 土改設為 2
				this.setVerify("N");
				this.setLotNo("");
				this.setNodeprset(rs.getString("nodeprset"));
				//#end 
				
				String nodeprset = Common.get(rs.getString("nodeprset"));
				if (isSPSituationTwo(nodeprset, deprYM_to_YYYYMM)) {
					// 不折舊, 但也要寫入供對帳
					this.setMonthDepr1("0");
					this.setMonthDepr2("0");
					this.insertData(db2);
					doCount("RF", "S");
				} else if (isSPSituationOne(nodeprset, previousDeprYM_to_YYYYMM, deprYM_to_YYYYMM)) {

					int deprYM_to_YYYYMM_int = Integer.parseInt(deprYM_to_YYYYMM);
					int previousDeprYM_to_YYYYMM_int = Integer.parseInt(previousDeprYM_to_YYYYMM);
					//#region 本月折舊(本年度)   monthDepr1    
					if (deprYM_to_YYYYMM_int < Integer.parseInt(this.getApportionEndYM())) {
						// 折舊年月 < 攤提年限截止年月
						this.setMonthDepr1(Common.get(rs.getString("monthdepr")));
					} else if (deprYM_to_YYYYMM_int > Integer.parseInt(this.getApportionEndYM())) {
						// 折舊年月 > 攤提年限截止年月
						this.setMonthDepr1("0");
					} else {
						// 折舊年月 = 攤提年限截止年月
						this.setMonthDepr1(Common.get(rs.getString("monthdepr1")));
					}
					//#end
					
					//#region 本月折舊(以前年度)  monthDepr2   
					if (previousDeprYM_to_YYYYMM_int < Integer.parseInt(this.getApportionEndYM())) {
						String roc_buyDate = ul._transToROC_Year(this.getBuyDate().substring(0, 6));
						int monthDiff = Datetime.BetweenTwoMonth(roc_buyDate, Datetime.getDateAdd("m", -1, deprYM.substring(0, 3) + "0101").substring(0, 5));
						if (monthDiff < 0) { // -1 : 傳入的長度不符合5    -2: roc_buyDate > 後者 ( Datetime.getDateAdd("m", -1, deprYM.substring(0, 3) + "0101").substring(0, 5)
							throw new Exception("取得以前年度計算用月數時發生資料異常,代碼:" + monthDiff);
						}
						this.setMonthDepr2(String.valueOf(Common.getInt(rs.getString("monthdepr")) * monthDiff));
					} else {
						this.setMonthDepr2(this.getDeprAmount());
					}
					//#end
					this.insertData(db2);
					doCount("RF", "S");
				} else {
					// 不符合任何資格 就忽略
					doCount("RF", "I");
				}
			}
			this.batchInsertCommit();
		} catch (Exception e) {
			this.logger.error("土改每月折舊計算(10501)發生錯誤:" + this.getCurrentInfo(), e);
			this.insertRollBack();
			throw e;
		} finally {
			//#region clear all  
			if (rs != null) {
				try {
					rs.close();
				} catch (Exception e) {
					//ignore
				}
			}
			
			if (db != null) {
				db.closeAll();
			}
			
			if (db2 != null) {
				db2.closeAll();
			}
			
			
			//#end
		}
	}
	
	/**
	 * 公務類_建物_折舊年月:10501 : 公物類建物第一次算折舊的特殊折舊計算(會需要補提折舊)
	 */
	private void QueryBUTableForSP() throws Exception {
		UNTCH_Tools ul = new UNTCH_Tools();
		ResultSet rs = null;
		Database db = null;
		Database db2 = null;
		
		/** 畫面 deprYM  省掉每次都要呼叫 this.getDeprYM() **/
		String deprYM = this.getDeprYM();
		/** 畫面deprym -1 個月   轉西元年  ex:畫面輸入10408 ->  201507 **/
		String previousDeprYM_to_YYYYMM = ul._transToCE_Year(Datetime.getDateAdd("m", -1, deprYM + "01").substring(0, 5));
		/** 畫面deprym 轉西元年前6碼        ex:畫面輸入10408 -> 201508  **/
		String deprYM_to_YYYYMM = ul._transToCE_Year(deprYM).substring(0, 6);
		
		try {
			db = new Database();
			db2 = new Database();
			
			rs = db.querySQL_NoChange(this.getQueryBUSQL(deprYM_to_YYYYMM));
			while (rs.next()) {
				//#region set Field to this  
				doCount("BU", "P");
				this.setTmpEnterorg(checkGet(rs.getString("enterorg")));
				this.setTmpOwnership(checkGet(rs.getString("ownership")));
				this.setTmpDifferencekind(checkGet(rs.getString("differencekind")));
				this.setTmpPropertyno(checkGet(rs.getString("propertyno")));
				this.setTmpSerialno(checkGet(rs.getString("serialno")));
				
				this.setEnterOrg(checkGet(rs.getString("enterorg")));
				this.setOwnership(checkGet(rs.getString("ownership")));
				this.setDifferenceKind(checkGet(rs.getString("differencekind")));
				this.setPropertyNo(checkGet(rs.getString("propertyno")));
				this.setSerialNo(checkGet(rs.getString("serialno")));
				this.setSerialNo1((null==rs.getString("serialno1")?"1":rs.getString("serialno1")));
				this.setDeprFrequency((null==rs.getString("deprFrequency")?"0":rs.getString("deprFrequency")));		
				this.setPropertyKind(checkGet(rs.getString("propertykind")));
				this.setFundType(checkGet(rs.getString("fundtype")));
				this.setValuable(checkGet(rs.getString("valuable")));
				this.setDeprMethod(checkGet(rs.getString("deprmethod")));
				this.setBuildFeeCB(checkGet(rs.getString("buildfeecb")));
				this.setDeprUnitCB(checkGet(rs.getString("deprunitcb")));
				this.setDeprPark(checkGet(rs.getString("deprpark")));
				this.setDeprUnit(checkGet(rs.getString("deprunit")));
				this.setDeprUnit1(checkGet(rs.getString("deprunit1")));
				this.setDeprAccounts(checkGet(rs.getString("depraccounts")));
				this.setOriginalBV(checkGet(rs.getString("originalbv")));
				this.setBookValue(checkGet(rs.getString("bookvalue")));
				this.setScrapValue(checkGet(rs.getString("scrapvalue")));
				this.setDeprAmount(checkGet(rs.getString("depramount")));
				this.setApportionMonth(checkGet(rs.getString("apportionmonth")));
				this.setOldNetValue(checkGet(rs.getString("netvalue")));
				this.setAccumDepr(checkGet(rs.getString("accumDepr")));
				this.setReduceAccumDepr(checkGet(rs.getString("reduceAccumDepr")));
				this.setMonthDepr1(rs.getString("monthdepr1"));
				this.setMonthDepr(checkGet(rs.getString("monthdepr")));
				this.setBuyDate(checkGet(rs.getString("buydate")));
				this.setEnterDate(checkGet(rs.getString("enterdate")));
				this.setSourceDate(checkGet(rs.getString("sourcedate")));
				this.setPropertyName1(checkGet(rs.getString("propertyname1")));
				this.setLimitYear(null==rs.getString("limitYear")?"null":rs.getString("limitYear"));
				this.setDeprPercent("100");
				this.setScaledAddAccumDepr("0");
				this.setKeeper(checkGet(rs.getString("keeper")));
				this.setKeepUnit(checkGet(rs.getString("keepunit")));
				this.setOldpropertyno(checkGet(rs.getString("oldpropertyno")));
				this.setOldserialno(checkGet(rs.getString("oldserialno")));
				this.setOldlotno(checkGet(rs.getString("oldlotno")));
				this.setOldmonthdepr(checkGet(rs.getString("monthdepr")));
				this.setOldmonthdepr1(checkGet(rs.getString("monthdepr1")));

				if ("111".equals(checkGet(rs.getString("differencekind"))) || "113".equals(checkGet(rs.getString("differencekind")))) {
					this.setEscrowOriLimitMonth(checkGet(rs.getString("originalapportionmonth")));
				} else {
					this.setEscrowOriLimitMonth("null");
				}	
				this.setEscrowOriValue(checkGet(rs.getString("escroworivalue")));
				this.setEscrowOriAccumDepr(checkGet(rs.getString("escrowOriaccumdepr")));
				this.setEngineeringNo(checkGet(rs.getString("engineeringno")));
				this.setNotes(checkGet(rs.getString("notes")));
				this.setEditID(checkGet(rs.getString("editid")));
				this.setEditDate(checkGet(rs.getString("editdate")));
				this.setEditTime(checkGet(rs.getString("edittime")));
				this.setApportionEndYM(checkGet(rs.getString("apportionendym")));
				this.setAddAccumDepr("0");
				this.setBookAmount("1");
				this.setPropertyTypeTemp("3");
				this.setVerify("N");
				this.setLotNo("");
				this.setNodeprset(rs.getString("nodeprset"));
				//#end 
				
				String nodeprset = Common.get(rs.getString("nodeprset"));
				if (isSPSituationTwo(nodeprset, deprYM_to_YYYYMM)) {
					// 不折舊, 但也要寫入供對帳
					this.setMonthDepr1("0");
					this.setMonthDepr2("0");
					this.insertData(db2);
					doCount("BU", "S");
				} else if (isSPSituationOne(nodeprset, previousDeprYM_to_YYYYMM, deprYM_to_YYYYMM)) {
					int deprYM_to_YYYYMM_int = Integer.parseInt(deprYM_to_YYYYMM);
					int previousDeprYM_to_YYYYMM_int = Integer.parseInt(previousDeprYM_to_YYYYMM);
					//#region 本月折舊(本年度)   monthDepr1    
					if (deprYM_to_YYYYMM_int < Integer.parseInt(this.getApportionEndYM())) {
						// 折舊年月 < 攤提年限截止年月
						this.setMonthDepr1(Common.get(rs.getString("monthdepr")));
					} else if (deprYM_to_YYYYMM_int > Integer.parseInt(this.getApportionEndYM())) {
						// 折舊年月 > 攤提年限截止年月
						this.setMonthDepr1("0");
					} else {
						// 折舊年月 = 攤提年限截止年月
						this.setMonthDepr1(Common.get(rs.getString("monthdepr1")));
					}
					//#end
					
					//#region 本月折舊(以前年度)  monthDepr2   
					if (previousDeprYM_to_YYYYMM_int < Integer.parseInt(this.getApportionEndYM())) {
						String roc_buyDate = ul._transToROC_Year(this.getBuyDate().substring(0, 6));
						int monthDiff = Datetime.BetweenTwoMonth(roc_buyDate, Datetime.getDateAdd("m", -1, deprYM.substring(0, 3) + "0101").substring(0, 5));
						if (monthDiff < 0) { // -1 : 傳入的長度不符合5    -2: roc_buyDate > 後者 ( Datetime.getDateAdd("m", -1, deprYM.substring(0, 3) + "0101").substring(0, 5)
							throw new Exception("取得以前年度計算用月數時發生資料異常,代碼:" + monthDiff);
						}
						this.setMonthDepr2(String.valueOf(Common.getInt(rs.getString("monthdepr")) * monthDiff));
					} else {
						this.setMonthDepr2(this.getDeprAmount());
					}
					//#end
					this.insertData(db2);
					doCount("BU", "S");
				} else {
					// 不符合任何資格 就忽略
					doCount("BU", "I");
				}
			}
			this.batchInsertCommit();
		} catch (Exception e) {
			this.logger.error("建物每月折舊計算發生錯誤:" + this.getCurrentInfo(), e);
			this.insertRollBack();
			throw e;
		} finally {
			//#region clear all  
			if (rs != null) {
				try {
					rs.close();
				} catch (Exception e) {
					//ignore
				}
			}
			
			if (db != null) {
				db.closeAll();
			}
			
			if (db2 != null) {
				db2.closeAll();
			}
			
			
			//#end
		}
	}
	
	/**
	 * 公務類_動產_折舊年月:10501 ： 公務類動產第一次算折舊的特殊折舊計算(會需要補提折舊)
	 */
	private void QueryMPTableForSP() throws Exception {
		UNTCH_Tools ul = new UNTCH_Tools();
		ResultSet rs = null;
		Database db = null;
		Database db2 = null;
		
		/** 畫面 deprYM  省掉每次都要呼叫 this.getDeprYM() **/
		String deprYM = this.getDeprYM();
		/** 畫面deprym -1 個月   轉西元年  ex:畫面輸入10408 ->  201507 **/
		String previousDeprYM_to_YYYYMM = ul._transToCE_Year(Datetime.getDateAdd("m", -1, deprYM + "01").substring(0, 5));
		/** 畫面deprym 轉西元年前6碼        ex:畫面輸入10408 -> 201508  **/
		String deprYM_to_YYYYMM = ul._transToCE_Year(deprYM).substring(0, 6);
		
		try {
			db = new Database();
			db2 = new Database();
			
			rs = db.querySQL_NoChange(this.getQueryMPSQL(deprYM_to_YYYYMM));
			while (rs.next()) {
				//if(rs.getString("buydate").substring(0, 5))
				doCount("MP", "P");
				
				this.setTmpEnterorg(checkGet(rs.getString("enterorg")));
				this.setTmpOwnership(checkGet(rs.getString("ownership")));
				this.setTmpDifferencekind(checkGet(rs.getString("differencekind")));
				this.setTmpPropertyno(checkGet(rs.getString("propertyno")));
				this.setTmpSerialno(checkGet(rs.getString("serialno")));
				
				this.setEnterOrg(checkGet(rs.getString("enterorg")));
				this.setOwnership(checkGet(rs.getString("ownership")));
				this.setDifferenceKind(checkGet(rs.getString("differencekind")));
				this.setPropertyNo(checkGet(rs.getString("propertyno")));
				this.setSerialNo(checkGet(rs.getString("serialno")));
				this.setSerialNo1((null==rs.getString("serialno1")?"1":rs.getString("serialno1")));
				this.setDeprFrequency((null==rs.getString("deprFrequency")?"0":rs.getString("deprFrequency")));		
				//obj.setDeprFrequency1(rs.getString("deprFrequency1"));
				this.setPropertyKind(checkGet(rs.getString("propertykind")));
				this.setFundType(checkGet(rs.getString("fundtype")));
				this.setValuable(checkGet(rs.getString("valuable")));
				this.setDeprMethod(checkGet(rs.getString("deprmethod")));
				this.setBuildFeeCB(checkGet(rs.getString("buildfeecb")));
				this.setDeprUnitCB(checkGet(rs.getString("deprunitcb")));
				this.setDeprPark(checkGet(rs.getString("deprpark")));
				this.setDeprUnit(checkGet(rs.getString("deprunit")));
				this.setDeprUnit1(checkGet(rs.getString("deprunit1")));
				this.setDeprAccounts(checkGet(rs.getString("depraccounts")));
				this.setOriginalBV(checkGet(rs.getString("originalbv")));
				this.setBookValue(checkGet(rs.getString("bookvalue")));
				this.setScrapValue(checkGet(rs.getString("scrapvalue")));
				this.setDeprAmount(checkGet(rs.getString("depramount")));
				this.setApportionMonth(checkGet(rs.getString("apportionmonth")));
				this.setOldNetValue(checkGet(rs.getString("netvalue")));
				this.setAccumDepr(checkGet(rs.getString("accumDepr")));
				this.setReduceAccumDepr(checkGet(rs.getString("reduceAccumDepr")));
				this.setMonthDepr1(rs.getString("monthdepr1"));
				this.setMonthDepr(checkGet(rs.getString("monthdepr")));
				this.setBuyDate(checkGet(rs.getString("buydate")));
				this.setEnterDate(checkGet(rs.getString("enterdate")));
				this.setSourceDate(checkGet(rs.getString("sourcedate")));
				this.setPropertyName1(checkGet(rs.getString("propertyname1")));
				this.setLimitYear(null==rs.getString("limitYear")?"null":rs.getString("limitYear"));
				this.setDeprPercent("100");
				this.setScaledAddAccumDepr("0");
				this.setKeeper(checkGet(rs.getString("keeper")));
				this.setKeepUnit(checkGet(rs.getString("keepunit")));
				if ("111".equals(checkGet(rs.getString("differencekind"))) || "113".equals(checkGet(rs.getString("differencekind")))) {
					this.setEscrowOriLimitMonth(checkGet(rs.getString("otherlimityear")));
				} else {
					this.setEscrowOriLimitMonth("null");
				}	
				this.setEscrowOriValue(checkGet(rs.getString("escroworivalue")));
				this.setEscrowOriAccumDepr(checkGet(rs.getString("escrowOriaccumdepr")));
				this.setEngineeringNo(checkGet(rs.getString("engineeringno")));
				this.setNotes(checkGet(rs.getString("notes")));
				this.setEditID(checkGet(rs.getString("editid")));
				this.setEditDate(checkGet(rs.getString("editdate")));
				this.setEditTime(checkGet(rs.getString("edittime")));
				this.setApportionEndYM(checkGet(rs.getString("apportionendym")));
				this.setAddAccumDepr("0");
				this.setBookAmount(checkGet(rs.getString("bookamount")));
				if (checkGet(rs.getString("propertyno")).substring(0, 1).equals("3")) {
					this.setPropertyTypeTemp("4");
				} else if (checkGet(rs.getString("propertyno")).substring(0, 1).equals("4")) {
					this.setPropertyTypeTemp("5");
				} else if (checkGet(rs.getString("propertyno")).substring(0, 1).equals("5")) {
					this.setPropertyTypeTemp("6");
				}
				this.setVerify("N");
				this.setLotNo(checkGet(rs.getString("lotno")));
				this.setNodeprset(rs.getString("nodeprset"));
				this.setOldpropertyno(checkGet(rs.getString("oldpropertyno")));
				this.setOldserialno(checkGet(rs.getString("oldserialno")));
				this.setOldlotno(checkGet(rs.getString("oldlotno")));
				this.setOldmonthdepr(checkGet(rs.getString("monthdepr")));
				this.setOldmonthdepr1(checkGet(rs.getString("monthdepr1")));
				//#end 
				
				String nodeprset = Common.get(rs.getString("nodeprset"));
				if (isSPSituationTwo(nodeprset, deprYM_to_YYYYMM)) {
					// 不折舊, 但也要寫入供對帳
					this.setMonthDepr1("0");
					this.setMonthDepr2("0");
					this.insertData(db2);
					doCount("MP", "S");
				} else if (isSPSituationOne(nodeprset, previousDeprYM_to_YYYYMM, deprYM_to_YYYYMM)) {
					int deprYM_to_YYYYMM_int = Integer.parseInt(deprYM_to_YYYYMM);
					int previousDeprYM_to_YYYYMM_int = Integer.parseInt(previousDeprYM_to_YYYYMM);
					//#region 本月折舊(本年度)   monthDepr1    
					if (deprYM_to_YYYYMM_int < Integer.parseInt(this.getApportionEndYM())) {
						// 折舊年月 < 攤提年限截止年月
						this.setMonthDepr1(Common.get(rs.getString("monthdepr")));
					} else if (deprYM_to_YYYYMM_int > Integer.parseInt(this.getApportionEndYM())) {
						// 折舊年月 > 攤提年限截止年月
						this.setMonthDepr1("0");
					} else {
						// 折舊年月 = 攤提年限截止年月
						this.setMonthDepr1(Common.get(rs.getString("monthdepr1")));
					}
					//#end
					
					//#region 本月折舊(以前年度)  monthDepr2   
					if (previousDeprYM_to_YYYYMM_int < Integer.parseInt(this.getApportionEndYM())) {
						String roc_buyDate = ul._transToROC_Year(this.getBuyDate().substring(0, 6));
						int monthDiff = Datetime.BetweenTwoMonth(roc_buyDate, Datetime.getDateAdd("m", -1, deprYM.substring(0, 3) + "0101").substring(0, 5));
						if (monthDiff < 0) { // -1 : 傳入的長度不符合5    -2: roc_buyDate > 後者 ( Datetime.getDateAdd("m", -1, deprYM.substring(0, 3) + "0101").substring(0, 5)
							throw new Exception("取得以前年度計算用月數時發生資料異常,代碼:" + monthDiff);
						}
						this.setMonthDepr2(String.valueOf(Common.getInt(rs.getString("monthdepr")) * monthDiff));
					} else {
						this.setMonthDepr2(this.getDeprAmount());
					}
					//#end
					this.insertData(db2);
					doCount("MP", "S");
				} else {
					// 不符合任何資格 就忽略
					doCount("MP", "I");
				}
			}
			this.batchInsertCommit();
		} catch (Exception e) {
			this.logger.error("動產每月折舊計算(10501)發生錯誤:" + this.getCurrentInfo(), e);
			this.insertRollBack();
			throw e;
		} finally {
			//#region clear all  
			if (rs != null) {
				try {
					rs.close();
				} catch (Exception e) {
					//ignore
				}
			}
			
			if (db != null) {
				db.closeAll();
			}
			
			if (db2 != null) {
				db2.closeAll();
			}
			
			
			//#end
		}
		
	}
	
	/**
	 * 公務類_權利_折舊年月:10501 ： 公務類權利第一次算折舊的特殊折舊計算(會需要補提折舊)
	 */
	private void QueryRTTableForSP() throws Exception {
		UNTCH_Tools ul = new UNTCH_Tools();
		ResultSet rs = null;
		Database db = null;
		Database db2 = null;
		
		/** 畫面 deprYM  省掉每次都要呼叫 this.getDeprYM() **/
		String deprYM = this.getDeprYM();
		/** 畫面deprym -1 個月   轉西元年  ex:畫面輸入10408 ->  201507 **/
		String previousDeprYM_to_YYYYMM = ul._transToCE_Year(Datetime.getDateAdd("m", -1, deprYM + "01").substring(0, 5));
		/** 畫面deprym 轉西元年前6碼        ex:畫面輸入10408 -> 201508  **/
		String deprYM_to_YYYYMM = ul._transToCE_Year(deprYM).substring(0, 6);
		
		try {
			db = new Database();
			db2 = new Database();
			
			rs = db.querySQL_NoChange(this.getQueryRTSQL(deprYM_to_YYYYMM));
			while (rs.next()) {
				//#region set Field to this  
				doCount("RT", "P");
				this.setTmpEnterorg(checkGet(rs.getString("enterorg")));
				this.setTmpOwnership(checkGet(rs.getString("ownership")));
				this.setTmpDifferencekind(checkGet(rs.getString("differencekind")));
				this.setTmpPropertyno(checkGet(rs.getString("propertyno")));
				this.setTmpSerialno(checkGet(rs.getString("serialno")));
				
				this.setEnterOrg(checkGet(rs.getString("enterorg")));
				this.setOwnership(checkGet(rs.getString("ownership")));
				this.setDifferenceKind(checkGet(rs.getString("differencekind")));
				this.setPropertyNo(checkGet(rs.getString("propertyno")));
				this.setSerialNo(checkGet(rs.getString("serialno")));
				this.setSerialNo1((null==rs.getString("serialno1")?"1":rs.getString("serialno1")));
				this.setDeprFrequency((null==rs.getString("deprFrequency")?"0":rs.getString("deprFrequency")));		
				this.setPropertyKind(checkGet(rs.getString("propertykind")));
				this.setFundType(checkGet(rs.getString("fundtype")));
				this.setValuable(checkGet(rs.getString("valuable")));
				this.setDeprMethod(checkGet(rs.getString("deprmethod")));
				this.setBuildFeeCB(checkGet(rs.getString("buildfeecb")));
				this.setDeprUnitCB(checkGet(rs.getString("deprunitcb")));
				this.setDeprPark(checkGet(rs.getString("deprpark")));
				this.setDeprUnit(checkGet(rs.getString("deprunit")));
				this.setDeprUnit1(checkGet(rs.getString("deprunit1")));
				this.setDeprAccounts(checkGet(rs.getString("depraccounts")));
				this.setOriginalBV(checkGet(rs.getString("originalbv")));
				this.setBookValue(checkGet(rs.getString("bookvalue")));
				this.setScrapValue(checkGet(rs.getString("scrapvalue")));
				this.setDeprAmount(checkGet(rs.getString("depramount")));
				this.setApportionMonth(checkGet(rs.getString("apportionmonth")));
				this.setOldNetValue(checkGet(rs.getString("netvalue")));
				this.setAccumDepr(checkGet(rs.getString("accumDepr")));
				this.setReduceAccumDepr(checkGet(rs.getString("reduceAccumDepr")));
				this.setMonthDepr1(rs.getString("monthdepr1"));
				this.setMonthDepr(checkGet(rs.getString("monthdepr")));
				this.setBuyDate(checkGet(rs.getString("buydate")));
				this.setEnterDate(checkGet(rs.getString("enterdate")));
				this.setSourceDate(checkGet(rs.getString("sourcedate")));
				this.setPropertyName1(checkGet(rs.getString("propertyname1")));
				this.setLimitYear(null==rs.getString("limitYear")?"null":rs.getString("limitYear"));
				this.setDeprPercent("100");
				this.setScaledAddAccumDepr("0");
				this.setKeeper(checkGet(rs.getString("keeper")));
				this.setKeepUnit(checkGet(rs.getString("keepunit")));
				this.setOldpropertyno(checkGet(rs.getString("oldpropertyno")));
				this.setOldserialno(checkGet(rs.getString("oldserialno")));
				this.setOldlotno(checkGet(rs.getString("oldlotno")));
				this.setOldmonthdepr(checkGet(rs.getString("monthdepr")));
				this.setOldmonthdepr1(checkGet(rs.getString("monthdepr1")));

				if ("111".equals(checkGet(rs.getString("differencekind"))) || "113".equals(checkGet(rs.getString("differencekind")))) {
					this.setEscrowOriLimitMonth(checkGet(rs.getString("originalapportionmonth")));
				} else {
					this.setEscrowOriLimitMonth("null");
				}	
				this.setEscrowOriValue(checkGet(rs.getString("escroworivalue")));
				this.setEscrowOriAccumDepr(checkGet(rs.getString("escrowOriaccumdepr")));
				this.setEngineeringNo(checkGet(rs.getString("engineeringno")));
				this.setNotes(checkGet(rs.getString("notes")));
				this.setEditID(checkGet(rs.getString("editid")));
				this.setEditDate(checkGet(rs.getString("editdate")));
				this.setEditTime(checkGet(rs.getString("edittime")));
				this.setApportionEndYM(checkGet(rs.getString("apportionendym")));
				this.setAddAccumDepr("0");
				this.setBookAmount("1");
				this.setPropertyTypeTemp("8");
				this.setVerify("N");
				this.setLotNo("");
				this.setNodeprset(rs.getString("nodeprset"));
				//#end 
				
				String nodeprset = Common.get(rs.getString("nodeprset"));
				if (isSPSituationTwo(nodeprset, deprYM_to_YYYYMM)) {
					// 不折舊, 但也要寫入供對帳
					this.setMonthDepr1("0");
					this.setMonthDepr2("0");
					this.insertData(db2);
					doCount("RT", "S");
				} else if (isSPSituationOne(nodeprset, previousDeprYM_to_YYYYMM, deprYM_to_YYYYMM)) {
					int deprYM_to_YYYYMM_int = Integer.parseInt(deprYM_to_YYYYMM);
					int previousDeprYM_to_YYYYMM_int = Integer.parseInt(previousDeprYM_to_YYYYMM);
					//#region 本月折舊(本年度)   monthDepr1    
					if (deprYM_to_YYYYMM_int < Integer.parseInt(this.getApportionEndYM())) {
						// 折舊年月 < 攤提年限截止年月
						this.setMonthDepr1(Common.get(rs.getString("monthdepr")));
					} else if (deprYM_to_YYYYMM_int > Integer.parseInt(this.getApportionEndYM())) {
						// 折舊年月 > 攤提年限截止年月
						this.setMonthDepr1("0");
					} else {
						// 折舊年月 = 攤提年限截止年月
						this.setMonthDepr1(Common.get(rs.getString("monthdepr1")));
					}
					//#end
					
					//#region 本月折舊(以前年度)  monthDepr2   
					if (previousDeprYM_to_YYYYMM_int < Integer.parseInt(this.getApportionEndYM())) {
						String roc_buyDate = ul._transToROC_Year(this.getBuyDate().substring(0, 6));
						int monthDiff = Datetime.BetweenTwoMonth(roc_buyDate, Datetime.getDateAdd("m", -1, deprYM.substring(0, 3) + "0101").substring(0, 5));
						if (monthDiff < 0) { // -1 : 傳入的長度不符合5    -2: roc_buyDate > 後者 ( Datetime.getDateAdd("m", -1, deprYM.substring(0, 3) + "0101").substring(0, 5)
							throw new Exception("取得以前年度計算用月數時發生資料異常,代碼:" + monthDiff);
						}
						this.setMonthDepr2(String.valueOf(Common.getInt(rs.getString("monthdepr")) * monthDiff));
					} else {
						this.setMonthDepr2(this.getDeprAmount());
					}
					//#end
					this.insertData(db2);
					doCount("RT", "S");
				} else {
					// 不符合任何資格 就忽略
					doCount("RT", "I");
				}
			}
			this.batchInsertCommit();
		} catch (Exception e) {
			this.logger.error("權利每月折舊計算(10501)發生錯誤:" + this.getCurrentInfo(), e);
			this.insertRollBack();
			throw e;
		} finally {
			//#region clear all  
			if (rs != null) {
				try {
					rs.close();
				} catch (Exception e) {
					//ignore
				}
			}
			
			if (db != null) {
				db.closeAll();
			}
			
			if (db2 != null) {
				db2.closeAll();
			}
			
			
			//#end
		}
	}
	
	
	
}