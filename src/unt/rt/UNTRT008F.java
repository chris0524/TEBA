package unt.rt;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import unt.ch.UNTCH_Tools;
import util.Common;
import util.Database;
import util.MaxClosingYM;
import util.SuperBean2;
import TDlib_Simple.com.src.SQLCreator;

/**
 * <br/>程式目的：權利增減值作業－增減值單資料
 * <br/>程式代號：UNTRT008F
 * <br/>程式日期：0941007
 * <br/>程式作者：cherry
 * <br/>--------------------------------------------------------
 * <br/>修改作者　　     修改日期　　　			修改目的
 * <br/>
 * <br/>--------------------------------------------------------
 */
public class UNTRT008F extends UNTRT008Q{
	
	public UNTRT008F() {
		
	}
	
	public UNTRT008F(String caller) {
		this.setCaller(caller);
	}
	
	private String caller;
	public String getCaller() { return checkGet(caller); }
	public void setCaller(String caller) { this.caller = checkSet(caller); }

	private String enterOrg;
	private String caseNo;
	private String ownership;
	private String differenceKind;
	private String engineeringNo;
	private String caseSerialNo;
	private String caseName;
	private String propertyNo;
	private String serialNo;
	private String propertyName1;
	private String writeDate;
	private String writeUnit;
	private String proofYear;
	private String proofDoc;
	private String proofNo;
	private String manageNo;
	private String summonsNo;
	private String cause;
	private String cause1;
	private String adjustDate;
	private String approveOrg;
	private String approveDate;
	private String approveDoc;
	private String bookNotes;
	private String verify;
	private String propertyKind;
	private String fundType;
	private String buyDate;
	private String sourceKind;
	private String sourceDate;
	private String oldBookValue;
	private String addBookValue;
	private String reduceBookValue;
	private String newBookValue;
	private String keepUnit;
	private String keeper;
	private String useUnit;
	private String userNo;
	private String userNote;
	private String place1;
	private String place;
	private String oldPropertyNo;
	private String oldSerialNo;
	private String notes;
	private String editID;
	private String editDate;
	private String editTime;
	private String checkEnterOrg;
	private String closing1ym;
	private String otherLimitYear;
	private String oldScrapValue;
	private String oldDeprAmount;
	private String oldMonthDepr;
	private String oldMonthDepr1;
	private String oldApportionMonth;
	private String oldDeprMethod;
	private String oldDeprUnitCB;
	private String newDeprMethod;
	private String newDeprPark;
	private String newDeprUnit;
	private String newDeprUnit1;
	private String newDeprAccounts;
	private String oldBuildFeeCB;

	public String getEnterOrg() {return checkGet(enterOrg);}
	public void setEnterOrg(String enterOrg) {this.enterOrg = checkSet(enterOrg);}
	public String getCaseNo() {return checkGet(caseNo);}
	public void setCaseNo(String caseNo) {this.caseNo = checkSet(caseNo);}
	public String getOwnership() {return checkGet(ownership);}
	public void setOwnership(String ownership) {this.ownership = checkSet(ownership);}
	public String getDifferenceKind() {return checkGet(differenceKind);}
	public void setDifferenceKind(String differenceKind) {this.differenceKind = checkSet(differenceKind);}
	public String getEngineeringNo() {return checkGet(engineeringNo);}
	public void setEngineeringNo(String engineeringNo) {this.engineeringNo = checkSet(engineeringNo);}
	public String getCaseSerialNo() {return checkGet(caseSerialNo);}
	public void setCaseSerialNo(String caseSerialNo) {this.caseSerialNo = checkSet(caseSerialNo);}
	public String getCaseName() {return checkGet(caseName);}
	public void setCaseName(String caseName) {this.caseName = checkSet(caseName);}
	public String getPropertyNo() {return checkGet(propertyNo);}
	public void setPropertyNo(String propertyNo) {this.propertyNo = checkSet(propertyNo);}
	public String getSerialNo() {return checkGet(serialNo);}
	public void setSerialNo(String serialNo) {this.serialNo = checkSet(serialNo);}
	public String getPropertyName1() {return checkGet(propertyName1);}
	public void setPropertyName1(String propertyName1) {this.propertyName1 = checkSet(propertyName1);}
	public String getWriteDate() {return checkGet(writeDate);}
	public void setWriteDate(String writeDate) {this.writeDate = checkSet(writeDate);}
	public String getWriteUnit() {return checkGet(writeUnit);}
	public void setWriteUnit(String writeUnit) {this.writeUnit = checkSet(writeUnit);}
	public String getProofYear() {return checkGet(proofYear);}
	public void setProofYear(String proofYear) {this.proofYear = checkSet(proofYear);}
	public String getProofDoc() {return checkGet(proofDoc);}
	public void setProofDoc(String proofDoc) {this.proofDoc = checkSet(proofDoc);}
	public String getProofNo() {return checkGet(proofNo);}
	public void setProofNo(String proofNo) {this.proofNo = checkSet(proofNo);}
	public String getManageNo() {return checkGet(manageNo);}
	public void setManageNo(String manageNo) {this.manageNo = checkSet(manageNo);}
	public String getSummonsNo() {return checkGet(summonsNo);}
	public void setSummonsNo(String summonsNo) {this.summonsNo = checkSet(summonsNo);}
	public String getCause() {return checkGet(cause);}
	public void setCause(String cause) {this.cause = checkSet(cause);}
	public String getCause1() {return checkGet(cause1);}
	public void setCause1(String cause1) {this.cause1 = checkSet(cause1);}
	public String getAdjustDate() {return checkGet(adjustDate);}
	public void setAdjustDate(String adjustDate) {this.adjustDate = checkSet(adjustDate);}
	public String getApproveOrg() {return checkGet(approveOrg);}
	public void setApproveOrg(String approveOrg) {this.approveOrg = checkSet(approveOrg);}
	public String getApproveDate() {return checkGet(approveDate);}
	public void setApproveDate(String approveDate) {this.approveDate = checkSet(approveDate);}
	public String getApproveDoc() {return checkGet(approveDoc);}
	public void setApproveDoc(String approveDoc) {this.approveDoc = checkSet(approveDoc);}
	public String getBookNotes() {return checkGet(bookNotes);}
	public void setBookNotes(String bookNotes) {this.bookNotes = checkSet(bookNotes);}
	public String getVerify() {return checkGet(verify);}
	public void setVerify(String verify) {this.verify = checkSet(verify);}
	public String getPropertyKind() {return checkGet(propertyKind);}
	public void setPropertyKind(String propertyKind) {this.propertyKind = checkSet(propertyKind);}
	public String getFundType() {return checkGet(fundType);}
	public void setFundType(String fundType) {this.fundType = checkSet(fundType);}
	public String getBuyDate() {return checkGet(buyDate);}
	public void setBuyDate(String buyDate) {this.buyDate = checkSet(buyDate);}
	public String getSourceKind() {return checkGet(sourceKind);}
	public void setSourceKind(String sourceKind) {this.sourceKind = checkSet(sourceKind);}
	public String getSourceDate() {return checkGet(sourceDate);}
	public void setSourceDate(String sourceDate) {this.sourceDate = checkSet(sourceDate);}
	public String getOldBookValue() {return checkGet(oldBookValue);}
	public void setOldBookValue(String oldBookValue) {this.oldBookValue = checkSet(oldBookValue);}
	public String getAddBookValue() {return checkGet(addBookValue);}
	public void setAddBookValue(String addBookValue) {this.addBookValue = checkSet(addBookValue);}
	public String getReduceBookValue() {return checkGet(reduceBookValue);}
	public void setReduceBookValue(String reduceBookValue) {this.reduceBookValue = checkSet(reduceBookValue);}
	public String getNewBookValue() {return checkGet(newBookValue);}
	public void setNewBookValue(String newBookValue) {this.newBookValue = checkSet(newBookValue);}
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
	public String getPlace() {return checkGet(place);}
	public void setPlace(String place) {this.place = checkSet(place);}
	public String getOldPropertyNo() {return checkGet(oldPropertyNo);}
	public void setOldPropertyNo(String oldPropertyNo) {this.oldPropertyNo = checkSet(oldPropertyNo);}
	public String getOldSerialNo() {return checkGet(oldSerialNo);}
	public void setOldSerialNo(String oldSerialNo) {this.oldSerialNo = checkSet(oldSerialNo);}
	public String getNotes() {return checkGet(notes);}
	public void setNotes(String notes) {this.notes = checkSet(notes);}
	public String getEditID() {return checkGet(editID);}
	public void setEditID(String editID) {this.editID = checkSet(editID);}
	public String getEditDate() {return checkGet(editDate);}
	public void setEditDate(String editDate) {this.editDate = checkSet(editDate);}
	public String getEditTime() {return checkGet(editTime);}
	public void setEditTime(String editTime) {this.editTime = checkSet(editTime);}
	public String getCheckEnterOrg() {return checkGet(checkEnterOrg);}
	public void setCheckEnterOrg(String checkEnterOrg) {this.checkEnterOrg = checkSet(checkEnterOrg);}
	public String getOtherLimitYear() { return checkGet(otherLimitYear); }
	public void setOtherLimitYear(String otherlimityear) { this.otherLimitYear = checkSet(otherlimityear); }
	public String getOldScrapValue() { return checkGet(oldScrapValue); }
	public void setOldScrapValue(String oldScrapValue) { this.oldScrapValue = checkSet(oldScrapValue); }
	public String getOldDeprAmount() { return checkGet(oldDeprAmount); }
	public void setOldDeprAmount(String oldDeprAmount) { this.oldDeprAmount = checkSet(oldDeprAmount); }
	public String getOldMonthDepr() { return checkGet(oldMonthDepr); }
	public void setOldMonthDepr(String oldMonthDepr) { this.oldMonthDepr = checkSet(oldMonthDepr); }
	public String getOldMonthDepr1() { return checkGet(oldMonthDepr1); }
	public void setOldMonthDepr1(String oldMonthDepr1) { this.oldMonthDepr1 = checkSet(oldMonthDepr1); }
	public String getOldApportionMonth() { return checkGet(oldApportionMonth); }
	public void setOldApportionMonth(String oldApportionMonth) { this.oldApportionMonth = checkSet(oldApportionMonth); }
	public String getOldDeprMethod() { return checkGet(oldDeprMethod); }
	public void setOldDeprMethod(String oldDeprMethod) { this.oldDeprMethod = checkSet(oldDeprMethod); }
	public String getOldDeprUnitCB() { return checkGet(oldDeprUnitCB); }
	public void setOldDeprUnitCB(String oldDeprUnitCB) { this.oldDeprUnitCB = checkSet(oldDeprUnitCB); }
	public String getNewDeprMethod() { return checkGet(newDeprMethod);}
	public void setNewDeprMethod(String newDeprMethod) { this.newDeprMethod = checkSet(newDeprMethod); }
	public String getNewDeprPark() { return checkGet(newDeprPark); }
	public void setNewDeprPark(String newDeprPark) { this.newDeprPark = checkSet(newDeprPark); }
	public String getNewDeprUnit() { return checkGet(newDeprUnit);}
	public void setNewDeprUnit(String newDeprUnit) { this.newDeprUnit = checkSet(newDeprUnit); }
	public String getNewDeprUnit1() { return checkGet(newDeprUnit1); }
	public void setNewDeprUnit1(String newDeprUnit1) { this.newDeprUnit1 = checkSet(newDeprUnit1); }
	public String getNewDeprAccounts() { return checkGet(newDeprAccounts); }
	public void setNewDeprAccounts(String newDeprAccounts) { this.newDeprAccounts = checkSet(newDeprAccounts); }
	public String getOldBuildFeeCB() { return checkGet(oldBuildFeeCB); }
	public void setOldBuildFeeCB(String oldBuildFeeCB) { this.oldBuildFeeCB = checkSet(oldBuildFeeCB); }
	
	String verifyError;
	public String getVerifyError(){ return checkGet(verifyError); }
	public void setVerifyError(String s){ verifyError=checkSet(s); }

	String closing;
	public String getClosing(){ return checkGet(closing); }
	public void setClosing(String s){ closing=checkSet(s); }


	private String reVerify;
	public String getReVerify() {return reVerify;}
	public void setReVerify(String reVerify) {this.reVerify = reVerify;}
	private String summonsDate;
	public String getSummonsDate() {return checkGet(summonsDate);}
	public void setSummonsDate(String summonsDate) {this.summonsDate = checkSet(summonsDate);}
	public String getClosing1ym() {return checkGet(closing1ym);}
	public void setClosing1ym(String closing1ym) {this.closing1ym = checkSet(closing1ym);}
	
	private String addLimitYear;
	private String overLimitYear;
	private String reduceLimitYear;
	private String oldNetValue;
	private String addNetValue;
	private String reduceAccumDepr;
	private String reduceNetValue;
	private String newNetValue;
	private String oldAccumDepr;
	private String oldApportionEndYM;
	private String newScrapValue;
	private String newDeprAmount;
	private String newAccumDepr;
	private String newApportionMonth;
	private String newMonthDepr;
	private String newMonthDepr1;
	private String newApportionEndYM;
	private String newBuildFeeCB;
	private String newDeprUnitCB;
	
	public String getAddLimitYear() { return checkGet(addLimitYear); }
	public void setAddLimitYear(String addLimitYear) { this.addLimitYear = checkSet(addLimitYear); }
	public String getOverLimitYear() {return checkGet(overLimitYear);}
	public void setOverLimitYear(String overLimitYear) {this.overLimitYear = checkGet(overLimitYear);}
	public String getReduceLimitYear() { return checkGet(reduceLimitYear); }
	public void setReduceLimitYear(String reduceLimitYear) { this.reduceLimitYear = checkSet(reduceLimitYear); }
	public String getOldNetValue() { return checkGet(oldNetValue); }
	public void setOldNetValue(String oldNetValue) { this.oldNetValue = checkSet(oldNetValue); }
	public String getAddNetValue() { return checkGet(addNetValue); }
	public void setAddNetValue(String addNetValue) { this.addNetValue = checkSet(addNetValue); }
	public String getReduceAccumDepr() { return checkGet(reduceAccumDepr); }
	public void setReduceAccumDepr(String reduceAccumDepr) { this.reduceAccumDepr = checkSet(reduceAccumDepr); }
	public String getReduceNetValue() { return checkGet(reduceNetValue); }
	public void setReduceNetValue(String reduceNetValue) { this.reduceNetValue = checkSet(reduceNetValue); }
	public String getNewNetValue() { return checkGet(newNetValue); }
	public void setNewNetValue(String newNetValue) { this.newNetValue = checkSet(newNetValue); }
	public String getOldAccumDepr() { return checkGet(oldAccumDepr); }
	public void setOldAccumDepr(String oldAccumDepr) { this.oldAccumDepr = checkSet(oldAccumDepr); }
	public String getOldApportionEndYM() { return checkGet(oldApportionEndYM); }
	public void setOldApportionEndYM(String oldApportionEndYM) { this.oldApportionEndYM = checkSet(oldApportionEndYM); }
	public String getNewScrapValue() { return checkGet(newScrapValue); }
	public void setNewScrapValue(String newScrapValue) { this.newScrapValue = checkSet(newScrapValue); }
	public String getNewDeprAmount() { return checkGet(newDeprAmount); }
	public void setNewDeprAmount(String newDeprAmount) { this.newDeprAmount = checkSet(newDeprAmount); }
	public String getNewAccumDepr() { return checkGet(newAccumDepr); }
	public void setNewAccumDepr(String newAccumDepr) { this.newAccumDepr = checkSet(newAccumDepr); }
	public String getNewApportionMonth() { return checkGet(newApportionMonth); }
	public void setNewApportionMonth(String newApportionMonth) { this.newApportionMonth = checkSet(newApportionMonth); }
	public String getNewMonthDepr() { return checkGet(newMonthDepr); }
	public void setNewMonthDepr(String newMonthDepr) { this.newMonthDepr = checkSet(newMonthDepr); }
	public String getNewMonthDepr1() { return checkGet(newMonthDepr1); }
	public void setNewMonthDepr1(String newMonthDepr1) { this.newMonthDepr1 = checkSet(newMonthDepr1); }
	public String getNewApportionEndYM() { return checkGet(newApportionEndYM); }
	public void setNewApportionEndYM(String newApportionEndYM) { this.newApportionEndYM = checkSet(newApportionEndYM); }
	public String getNewBuildFeeCB() { return checkGet(newBuildFeeCB); }
	public void setNewBuildFeeCB(String newBuildFeeCB) { this.newBuildFeeCB = checkSet(newBuildFeeCB); }
	public String getNewDeprUnitCB() { return checkGet(newDeprUnitCB); }
	public void setNewDeprUnitCB(String newDeprUnitCB) { this.newDeprUnitCB = checkSet(newDeprUnitCB); }

	private Map getPKMap(){
		Map map = new HashMap();
		
		map.put("EnterOrg", getEnterOrg());
		map.put("CaseNo", getCaseNo());
		map.put("Ownership", getOwnership());
		map.put("DifferenceKind", getDifferenceKind());
		map.put("PropertyNo", getPropertyNo());
		map.put("SerialNo", getSerialNo());
		
		return map;
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private Map getRecordMap(String action){
		Map map = new HashMap();
		UNTCH_Tools ut = new UNTCH_Tools();
		
		map.put("EnterOrg", getEnterOrg());
		map.put("CaseNo", getCaseNo());
		map.put("Ownership", getOwnership());
		map.put("DifferenceKind", getDifferenceKind());
		map.put("EngineeringNo", getEngineeringNo());
		map.put("CaseSerialNo", getCaseSerialNo());
		map.put("CaseName", getCaseName());
		map.put("PropertyNo", getPropertyNo());
		map.put("SerialNo", getSerialNo());
		map.put("PropertyName1", getPropertyName1());
		map.put("WriteDate", ut._transToCE_Year(getWriteDate()));
		map.put("WriteUnit", getWriteUnit());
		map.put("ProofYear", getProofYear());
		map.put("ProofDoc", getProofDoc());
		map.put("ProofNo", getProofNo());
		map.put("ManageNo", getManageNo());
		map.put("SummonsNo", getSummonsNo());
		map.put("Cause", getCause());
		map.put("Cause1", getCause1());
		map.put("AdjustDate", ut._transToCE_Year(getAdjustDate()));
		map.put("ApproveOrg", getApproveOrg());
		map.put("ApproveDate", ut._transToCE_Year(getApproveDate()));
		map.put("ApproveDoc", getApproveDoc());
		map.put("BookNotes", getBookNotes());
		map.put("Verify", getVerify());
		map.put("PropertyKind", getPropertyKind());
		map.put("FundType", getFundType());
		map.put("BuyDate", ut._transToCE_Year(getBuyDate()));
		map.put("SourceKind", getSourceKind());
		map.put("SourceDate", ut._transToCE_Year(getSourceDate()));
		map.put("OldBookValue", getOldBookValue());
		map.put("AddBookValue", getAddBookValue());
		map.put("ReduceBookValue", getReduceBookValue());
		map.put("NewBookValue", getNewBookValue());
		map.put("KeepUnit", getKeepUnit());
		map.put("Keeper", getKeeper());
		map.put("UseUnit", getUseUnit());
		map.put("UserNo", getUserNo());
		map.put("UserNote", getUserNote());
		map.put("Place1", getPlace1());
		map.put("Place", getPlace());
		map.put("OldPropertyNo", getOldPropertyNo());
		map.put("OldSerialNo", getOldSerialNo());
		map.put("Notes", getNotes());
		map.put("EditID", getEditID());
		map.put("EditDate", ut._transToCE_Year(getEditDate()));
		map.put("EditTime", getEditTime());
		map.put("summonsDate", ut._transToCE_Year(getSummonsDate()));
		map.put("otherlimityear", getOtherLimitYear());
		
		if ("UNTCH003F02".equals(this.getCaller())) {
			// 增減值單明細資料 更新
			map.put("addLimitYear", this.getAddLimitYear());
			map.put("OverLimitYear", this.getOverLimitYear());
			map.put("reduceLimitYear", this.getReduceLimitYear());
			map.put("oldNetValue", this.getOldNetValue());
			map.put("addNetValue", this.getAddNetValue());
			map.put("reduceAccumDepr", this.getReduceAccumDepr());
			map.put("reduceNetValue", this.getReduceNetValue());
			map.put("newNetValue", this.getNewNetValue());
			map.put("oldAccumDepr", this.getOldAccumDepr());
			map.put("oldApportionEndYM", "".equals(this.getOldApportionEndYM()) ? "" : ut._transToCE_Year(this.getOldApportionEndYM()+"01").substring(0,6));
			map.put("newScrapValue", this.getNewScrapValue());
			map.put("newDeprAmount", this.getNewDeprAmount());
			map.put("newAccumDepr", this.getNewAccumDepr());
			map.put("newApportionMonth", this.getNewApportionMonth());
			map.put("newMonthDepr", this.getNewMonthDepr());
			map.put("newMonthDepr1", this.getNewMonthDepr1());
			map.put("newApportionEndYM", "".equals(this.getNewApportionEndYM()) ? "" : ut._transToCE_Year(this.getNewApportionEndYM()+"01").substring(0,6));
			map.put("OldScrapValue", getOldScrapValue());
			map.put("OldMonthDepr", getOldMonthDepr());
			map.put("OldDeprAmount", getOldDeprAmount());
			map.put("OldMonthDepr1", getOldMonthDepr1());
			map.put("OldApportionMonth", getOldApportionMonth());
			map.put("OldDeprMethod", getOldDeprMethod());
			map.put("OldDeprUnitCB", getOldDeprUnitCB());
			map.put("newDeprMethod", getNewDeprMethod());
			map.put("newDeprPark", getNewDeprPark());
			map.put("newDeprUnit", getNewDeprUnit());
			map.put("newDeprUnit1", getNewDeprUnit1());
			map.put("newDeprAccounts", getNewDeprAccounts());
			map.put("newBuildFeeCB", getNewBuildFeeCB());
			map.put("newDeprUnitCB", getNewDeprUnitCB());
		}
		
		return map;
	}
	
	@Override
	protected String[][] getInsertCheckSQL() {
		List<String[]> sqls = new ArrayList<String[]>();
		
		String[] tmpchk = new String[5];
		tmpchk[0] = " select count(*) as checkResult from UNTRT_ADJUSTPROOF where 1=1 " +
					" and enterorg = " + Common.sqlChar(this.getEnterOrg()) +
					" and ownership = " + Common.sqlChar(this.getOwnership()) + 
					" and differencekind = " + Common.sqlChar(this.getDifferenceKind()) + 
					" and propertyno = " + Common.sqlChar(this.getPropertyNo()) + 
					" and serialno = " + Common.sqlChar(this.getSerialNo()) + 
					" and verify = 'N' ";
		tmpchk[1] = ">";
		tmpchk[2] = "0";
		tmpchk[3] = "該筆資料已重複存在於其他未入帳增減值單，請重新輸入";
		tmpchk[4] = "N";
		sqls.add(tmpchk);
		
		tmpchk = new String[5];
		tmpchk[0] = " select count(*) as checkResult from UNTRT_REDUCEPROOF where 1=1 " + 
					" and enterorg = " + Common.sqlChar(this.getEnterOrg()) + 
					" and ownership = " + Common.sqlChar(this.getOwnership()) + 
					" and differencekind = " + Common.sqlChar(this.getDifferenceKind()) + 
					" and propertyno = " + Common.sqlChar(this.getPropertyNo()) + 
					" and serialno = " + Common.sqlChar(this.getSerialNo()) + 
					" and verify = 'N' ";
		tmpchk[1] = ">";
		tmpchk[2] = "0";
		tmpchk[3] = "該筆資料已重複存在於未入帳減損單，請重新輸入";
		tmpchk[4] = "N";
		sqls.add(tmpchk);
		
		return sqls.toArray(new String[sqls.size()][5]);
	}
	
	//傳回insert sql
	protected String[] getInsertSQL(){
		//取得增減值單編號
		//	setProofNo(MaxClosingYM.getMaxProofNo("UNTRT_ADJUSTPROOF",enterOrg,ownership));
		String[] execSQLArray = new String[1];
		execSQLArray[0] = new SQLCreator()._obtainSQLforInsert("UNTRT_ADJUSTPROOF", getPKMap(), getRecordMap("insert"));
		return execSQLArray;
	}

	public String queryOldVerify() {
		Database db = null;
		try {
			db = new Database();
			return db.getLookupField("select verify from UNTRT_ADJUSTPROOF where enterorg = " + Common.sqlChar(this.getEnterOrg()) + " and caseno = " + Common.sqlChar(this.getCaseNo()));
		} finally {
			db.closeAll();
		}
	}
	
	/**
	 * 暫時只供入帳檢查
	 */
	@Override
	public String[][] getUpdateCheckSQLBeforeAction() {
		
		UNTCH_Tools ut = new UNTCH_Tools();
		List<String[]> sqls = new ArrayList<String[]>();
		String oldVerify = this.queryOldVerify();
		String[] tmpchk = null;
		
		if ("N".equals(oldVerify) && "Y".equals(this.getVerify())) {
			// 入帳
			tmpchk = new String[5];
			tmpchk[0] = " select count(case when isnull(z.adjustdate, '') != '' then 1 else null end) as checkResult from UNTRT_ADJUSTPROOF  a "
					  + " OUTER APPLY ( " 
					  + " select top 1 b.adjustdate  from UNTRT_ADJUSTPROOF b  "
					  + " where a.enterorg=b.enterorg and a.ownership = b.ownership and a.differencekind=b.differencekind and a.propertyno=b.propertyno and a.serialno=b.serialno  "
					  + " and verify ='Y' and b.caseno != a.caseno and b.adjustdate >= " + ut._transToCE_Year(this.getAdjustDate())
					  + " ) as z "
					  + " where a.enterorg = " + Common.sqlChar(this.getEnterOrg()) + " and a.caseno = " + Common.sqlChar(this.getCaseNo());
			tmpchk[1] = "!=";
			tmpchk[2] = "0";
			tmpchk[3] = "入帳失敗, 檢查到已有入帳的權利增減值資料其入帳日期大於等於此單據入帳日期";
			tmpchk[4] = "N";
			sqls.add(tmpchk);
			
		} else if ("Y".equals(oldVerify) && "Y".equals(this.getReVerify())) {
			// 回復入帳
			tmpchk = new String[5];
			tmpchk[0] = " select count(*) as checkResult from UNTRT_ADJUSTPROOF  a "
					  + " inner join UNTRT_REDUCEPROOF b  " 
					  + " on a.enterorg=b.enterorg and a.ownership=b.ownership and a.differencekind=b.differencekind  "
					  + " and a.propertyno=b.propertyno and a.serialno=b.serialno  "
					  + " where a.enterorg = " + Common.sqlChar(this.getEnterOrg()) + " and a.caseno =" + Common.sqlChar(this.getCaseNo());
			tmpchk[1] = "!=";
			tmpchk[2] = "0";
			tmpchk[3] = "回復入帳失敗, 檢查到已有權利資料於減損作業中登打,請先移除該減損單";
			tmpchk[4] = "N";
			sqls.add(tmpchk);
			
			tmpchk = new String[5];
			tmpchk[0] = " select count(case when isnull(z.adjustdate, '') != '' then 1 else null end) as checkResult from UNTRT_ADJUSTPROOF  a "
					  + " OUTER APPLY ( " 
					  + " select top 1 b.adjustdate  from UNTRT_ADJUSTPROOF b  "
					  + " where a.enterorg=b.enterorg and a.ownership = b.ownership and a.differencekind=b.differencekind and a.propertyno=b.propertyno and a.serialno=b.serialno  "
					  + " and verify ='Y' and b.adjustdate >= a.adjustdate and b.caseno != a.caseno" 
					  + " ) as z "
					  + " where a.enterorg = " + Common.sqlChar(this.getEnterOrg()) + " and a.caseno = " + Common.sqlChar(this.getCaseNo());
			tmpchk[1] = "!=";
			tmpchk[2] = "0";
			tmpchk[3] = "回復入帳失敗, 已有權利資料於其他較新的增減值單中入帳";
			tmpchk[4] = "N";
			sqls.add(tmpchk);
		}
		
		return sqls.toArray(new String[sqls.size()][5]);
	}

	//傳回update sql
	protected String[] getUpdateSQL(){
		String strSQL = "";
		strSQL += new SQLCreator()._obtainSQLforUpdate("UNTRT_ADJUSTPROOF", getPKMap(), getRecordMap("update")) + ":;:";
		strSQL += updateTable();
		String[] execSQLArray = strSQL.split(":;:");
		return execSQLArray;
	}

	/**
	 * TODO  exception catched and print
	 */
	protected void execVerify(){

		String sql = "select propertyNo, serialno from UNTRT_AdjustProof" +
				" where 1=1" +
				" and enterorg = " + Common.sqlChar(getEnterOrg()) +
				" and ownership = " + Common.sqlChar(getOwnership()) +
				" and caseNo = " + Common.sqlChar(getCaseNo()) +
				" and differenceKind = " + Common.sqlChar(getDifferenceKind());
		String adjustDatTemp = this.getAdjustDate();
		String verifyTemp = this.getVerify();
		Database db = null;
		ResultSet rs = null;
		try{
			db = new Database();
			rs = db.querySQL(sql);
			while(rs.next()){
				this.setPropertyNo(rs.getString("propertyNo"));
				this.setSerialNo(rs.getString("serialNo"));

				this.queryOne();
				this.setVerify(verifyTemp);
				this.setAdjustDate(adjustDatTemp);
				this.update();
			}
			rs.close();
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			db.closeAll();
		}
	}


	//傳回delete sql
	protected String[] getDeleteSQL(){
		String[] execSQLArray = new String[1];
		execSQLArray[0] = new SQLCreator()._obtainSQLforDelete("UNTRT_ADJUSTPROOF", getPKMap(), getRecordMap("delete"));
		return execSQLArray;
	}

	protected void queryDeleteData(SuperBean2 sb){
		UNTCH_Tools ut = new UNTCH_Tools();
		Map map = queryPropertyNofrom("UNTRT_ADJUSTPROOF",sb);					
		Iterator iter = map.keySet().iterator();
		while(iter.hasNext()){
			String propertyNoStr = iter.next().toString();
			_setDefaultValue();
			ut._setParameter(sb,this);
			this.setPropertyNo(propertyNoStr);
			this.setSerialNo(map.get(propertyNoStr).toString());
			try {
				delete();
			} catch (Exception e) {
				e.printStackTrace();
			}								
		}
	}

	/**
	 * <br>
	 * <br>目的：依主鍵查詢單一資料
	 * <br>參數：無
	 * <br>傳回：傳回本物件
	 */
	public UNTRT008F  queryOne() throws Exception{
		Database db = new Database();
		UNTRT008F obj = this;
		try {
			String sql=" select a.*," +
					" b.closing1ym " +
					" from UNTRT_AdjustProof a inner join UNTAC_CLOSINGPT b on a.enterorg = b.enterorg and a.differencekind = b.differencekind where 1=1 " +
					" and a.enterOrg = " + Common.sqlChar(enterOrg) +
					" and a.ownership = " + Common.sqlChar(ownership) +
					" and a.caseNo = " + Common.sqlChar(caseNo) +
					" and a.differenceKind = " + Common.sqlChar(differenceKind) +
					" and a.propertyNo = " + Common.sqlChar(propertyNo) +
					" and a.serialNo = " + Common.sqlChar(serialNo) +
					"";
			ResultSet rs = db.querySQL(sql);
			UNTCH_Tools ut = new UNTCH_Tools();
			if (rs.next()){
				obj.setEnterOrg(rs.getString("EnterOrg"));
				obj.setCaseNo(rs.getString("CaseNo"));
				obj.setOwnership(rs.getString("Ownership"));
				obj.setDifferenceKind(rs.getString("DifferenceKind"));
				obj.setEngineeringNo(rs.getString("EngineeringNo"));
				obj.setCaseSerialNo(rs.getString("CaseSerialNo"));
				obj.setCaseName(rs.getString("CaseName"));
				obj.setPropertyNo(rs.getString("PropertyNo"));
				obj.setSerialNo(rs.getString("SerialNo"));
				obj.setPropertyName1(rs.getString("PropertyName1"));
				obj.setWriteDate(ut._transToROC_Year(rs.getString("WriteDate")));
				obj.setWriteUnit(rs.getString("WriteUnit"));
				obj.setProofYear(rs.getString("ProofYear"));
				obj.setProofDoc(rs.getString("ProofDoc"));
				obj.setProofNo(rs.getString("ProofNo"));
				obj.setManageNo(rs.getString("ManageNo"));
				obj.setSummonsNo(rs.getString("SummonsNo"));
				obj.setCause(rs.getString("Cause"));
				obj.setCause1(rs.getString("Cause1"));
				obj.setAdjustDate(ut._transToROC_Year(rs.getString("AdjustDate")));
				obj.setApproveOrg(rs.getString("ApproveOrg"));
				obj.setApproveDate(ut._transToROC_Year(rs.getString("ApproveDate")));
				obj.setApproveDoc(rs.getString("ApproveDoc"));
				obj.setBookNotes(rs.getString("BookNotes"));
				obj.setVerify(rs.getString("Verify"));
				obj.setPropertyKind(rs.getString("PropertyKind"));
				obj.setFundType(rs.getString("FundType"));
				obj.setBuyDate(ut._transToROC_Year(rs.getString("BuyDate")));
				obj.setSourceKind(rs.getString("SourceKind"));
				obj.setSourceDate(ut._transToROC_Year(rs.getString("SourceDate")));
				obj.setOldBookValue(rs.getString("OldBookValue"));
				obj.setAddBookValue(rs.getString("AddBookValue"));
				obj.setReduceBookValue(rs.getString("ReduceBookValue"));
				obj.setNewBookValue(rs.getString("NewBookValue"));
				obj.setKeepUnit(rs.getString("KeepUnit"));
				obj.setKeeper(rs.getString("Keeper"));
				obj.setUseUnit(rs.getString("UseUnit"));
				obj.setUserNo(rs.getString("UserNo"));
				obj.setUserNote(rs.getString("UserNote"));
				obj.setPlace1(rs.getString("Place1"));
				obj.setPlace(rs.getString("Place"));
				obj.setOldPropertyNo(rs.getString("OldPropertyNo"));
				obj.setOldSerialNo(rs.getString("OldSerialNo"));
				obj.setNotes(rs.getString("Notes"));
				obj.setEditID(rs.getString("EditID"));
				obj.setEditDate(ut._transToROC_Year(rs.getString("EditDate")));
				obj.setEditTime(rs.getString("EditTime"));
				obj.setSummonsDate(ut._transToROC_Year(rs.getString("summonsDate")));
				obj.setClosing1ym(rs.getString("closing1ym"));
				obj.setAddLimitYear(rs.getString("AddLimitYear"));
				obj.setOverLimitYear(rs.getString("OverLimitYear"));
				obj.setReduceLimitYear(rs.getString("ReduceLimitYear"));
				obj.setOldNetValue(rs.getString("OldNetValue"));
				obj.setAddNetValue(rs.getString("AddNetValue"));
				obj.setReduceAccumDepr(rs.getString("ReduceAccumDepr"));
				obj.setReduceNetValue(rs.getString("ReduceNetValue"));
				obj.setNewNetValue(rs.getString("NewNetValue"));
				obj.setOldAccumDepr(rs.getString("OldAccumDepr"));
				obj.setOldApportionEndYM(ut._transToROC_Year(rs.getString("OldApportionEndYM")));
				obj.setNewScrapValue(rs.getString("NewScrapValue"));
				obj.setNewDeprAmount(rs.getString("NewDeprAmount"));
				obj.setNewAccumDepr(rs.getString("NewAccumDepr"));
				obj.setNewApportionMonth(rs.getString("NewApportionMonth"));
				obj.setNewMonthDepr(rs.getString("NewMonthDepr"));
				obj.setNewMonthDepr1(rs.getString("NewMonthDepr1"));
				obj.setNewApportionEndYM(ut._transToROC_Year(rs.getString("NewApportionEndYM")));
				obj.setOtherLimitYear(rs.getString("otherlimityear"));
				obj.setOldScrapValue(rs.getString("OldScrapValue"));
				obj.setOldDeprAmount(rs.getString("Olddepramount"));
				obj.setOldMonthDepr(rs.getString("OldMonthDepr"));
				obj.setOldMonthDepr1(rs.getString("OldMonthDepr"));
				obj.setOldApportionMonth(rs.getString("OldApportionMonth"));
				obj.setOldDeprMethod(rs.getString("oldDeprMethod"));
				obj.setNewDeprMethod(rs.getString("newDeprMethod"));
				obj.setOldDeprUnitCB(rs.getString("oldDeprUnitCB"));
				obj.setNewBuildFeeCB(rs.getString("newBuildFeeCB"));
				obj.setNewDeprUnitCB(rs.getString("newDeprUnitCB"));
				obj.setOldBuildFeeCB(rs.getString("oldBuildFeeCB"));

			}
			setStateQueryOneSuccess();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.closeAll();
		}
		return obj;
	}

	//入帳設定
	protected String updateTable(){
		String sql="";

		//將「入帳」由「N:未核章」改為「Y:已核章」
		if(getVerify().equals("Y")){		
			UNTCH_Tools ut =new UNTCH_Tools();
			sql+= "update UNTRT_AddProof set"+
					" bookValue = " + Common.sqlChar(newBookValue) + ","+
					" editID = " + Common.sqlChar(getEditID()) + "," +
					" editDate = " + Common.sqlChar(ut._transToCE_Year(getEditDate())) + "," +
					" editTime = " + Common.sqlChar(getEditTime()) + 	                
					" where 1=1 " + 
					" and enterOrg = " + Common.sqlChar(enterOrg) +
					" and ownership = " + Common.sqlChar(ownership) +
					" and differenceKind = " + Common.sqlChar(differenceKind) +
					" and propertyNo = " + Common.sqlChar(propertyNo) +
					" and serialNo = " + Common.sqlChar(serialNo) +
					":;:";

		}else if(getVerify().equals("N")){
			UNTCH_Tools ut =new UNTCH_Tools();
			sql+= "update UNTRT_AddProof set"+
					" bookValue = " + Common.sqlChar(oldBookValue) + ","+
					" editID = " + Common.sqlChar(getEditID()) + "," +
					" editDate = " + Common.sqlChar(ut._transToCE_Year(getEditDate())) + "," +
					" editTime = " + Common.sqlChar(getEditTime()) + 	                
					" where 1=1 " + 
					" and enterOrg = " + Common.sqlChar(enterOrg) +
					" and ownership = " + Common.sqlChar(ownership) +
					" and differenceKind = " + Common.sqlChar(differenceKind) +
					" and propertyNo = " + Common.sqlChar(propertyNo) +
					" and serialNo = " + Common.sqlChar(serialNo) +
					":;:";
		}
		return sql;
	}

	/**
	 * 增減值入帳 折舊欄位更新
	 */
	public void updateRT(String enterOrg, String ownership, String caseNo, String differenceKind, String verify) {
		if("updateError".equals(this.getState())){
			
		}else{
			Database db = new Database();
			try{
				String querySQL = "select *,otherlimityear + isnull(addlimityear,0) + isnull(overlimityear,0) - isnull(reducelimityear,0) as newotherlimityear from UNTRT_ADJUSTPROOF " +
						" where 1=1" +
							" and enterOrg = " + Common.sqlChar(enterOrg) +
							" and ownership = " + Common.sqlChar(ownership) +
							" and caseNo = " + Common.sqlChar(caseNo) +
							" and differenceKind = " + Common.sqlChar(differenceKind);
		
				String serialNo = "";
				String propertyNo = "";
				String newDeprMethod = "";
				String newBuildFeeCB = "";
				String newDeprUnitCB = "";
				String newDeprPark = "";
				String newDeprUnit = "";
				String newDeprUnit1 = "";
				String newDeprAccounts = "";
				String newScrapValue = "";
				String newDeprAmount = "";
				String newAccumDepr = "";
				String newApportionMonth = "";
				String newMonthDepr = "";
				String newMonthDepr1 = "";
				String newApportionEndYM = "";
				String newotherlimityear = "";
				
				String oldDeprMethod = "";
				String oldBuildFeeCB = "";
				String oldDeprUnitCB = "";
				String oldDeprPark = "";
				String oldDeprUnit = "";
				String oldDeprUnit1 = "";
				String oldDeprAccounts = "";
				String oldScrapValue = "";
				String oldDeprAmount = "";
				String oldAccumDepr = "";
				String oldApportionMonth = "";
				String oldMonthDepr = "";
				String oldMonthDepr1 = "";
				String oldApportionEndYM = "";
				String oldotherlimityear = "";
				
				ResultSet rs = db.querySQL(querySQL);
				while(rs.next()){
					serialNo = rs.getString("serialno");
					propertyNo = rs.getString("propertyno");
					
					if("Y".equals(verify)) {
						newDeprMethod = rs.getString("newdeprmethod");
						newBuildFeeCB = rs.getString("newbuildfeecb");
						newDeprUnitCB = rs.getString("newdeprunitcb");
						newDeprPark = rs.getString("newdeprpark");
						newDeprUnit = rs.getString("newdeprunit");
						newDeprUnit1 = rs.getString("newdeprunit1");
						newDeprAccounts = rs.getString("newdepraccounts");
						newScrapValue = rs.getString("newscrapvalue");
						newDeprAmount = rs.getString("newdepramount");
						newAccumDepr = rs.getString("newaccumdepr");
						newApportionMonth = rs.getString("newapportionmonth");
						newMonthDepr = rs.getString("newmonthdepr");
						newMonthDepr1 = rs.getString("newmonthdepr1");
						newApportionEndYM = rs.getString("newapportionendym");
						newotherlimityear = rs.getString("newotherlimityear");
						
						querySQL = "update UNTRT_ADDPROOF set"
									+ " deprmethod = " + Common.sqlChar(newDeprMethod)
									+ " ,buildfeecb = " + Common.sqlChar(newBuildFeeCB)
									+ " ,deprunitcb = " + Common.sqlChar(newDeprUnitCB)
									+ " ,deprpark = " + Common.sqlChar(newDeprPark)
									+ " ,deprunit = " + Common.sqlChar(newDeprUnit)
									+ " ,deprunit1 = " + Common.sqlChar(newDeprUnit1)
									+ " ,depraccounts = " + Common.sqlChar(newDeprAccounts)
									+ " ,scrapvalue = " + Common.getInteger(newScrapValue)
									+ " ,depramount = " + Common.getInteger(newDeprAmount)
									+ " ,accumdepr = " + Common.getInteger(newAccumDepr)
									+ " ,apportionmonth = " + Common.getInteger(newApportionMonth)
									+ " ,monthdepr = " + Common.getInteger(newMonthDepr)
									+ " ,monthdepr1 = " + Common.getInteger(newMonthDepr1)
									+ " ,apportionendym = " + Common.sqlChar(newApportionEndYM)
									+ " ,otherlimityear = " + Common.sqlChar(newotherlimityear)
									+ " where enterorg = " + Common.sqlChar(enterOrg)
									+ " and ownership = " + Common.sqlChar(ownership)
									+ " and differencekind = " + Common.sqlChar(differenceKind)
									+ " and propertyno = " + Common.sqlChar(propertyNo)
									+ " and serialno = " + Common.sqlChar(serialNo);
					} else {
						oldDeprMethod = rs.getString("olddeprmethod");
						oldBuildFeeCB = rs.getString("oldbuildfeecb");
						oldDeprUnitCB = rs.getString("olddeprunitcb");
						oldDeprPark = rs.getString("olddeprpark");
						oldDeprUnit = rs.getString("olddeprunit");
						oldDeprUnit1 = rs.getString("olddeprunit1");
						oldDeprAccounts = rs.getString("olddepraccounts");
						oldScrapValue = rs.getString("oldscrapvalue");
						oldDeprAmount = rs.getString("olddepramount");
						oldAccumDepr = rs.getString("oldaccumdepr");
						oldApportionMonth = rs.getString("oldapportionmonth");
						oldMonthDepr = rs.getString("oldmonthdepr");
						oldMonthDepr1 = rs.getString("oldmonthdepr1");
						oldApportionEndYM = rs.getString("oldapportionendym");
						oldotherlimityear = rs.getString("otherlimityear");
						
						querySQL = "update UNTRT_ADDPROOF set"
							+ " deprmethod = " + Common.sqlChar(oldDeprMethod)
							+ " ,buildfeecb = " + Common.sqlChar(oldBuildFeeCB)
							+ " ,deprunitcb = " + Common.sqlChar(oldDeprUnitCB)
							+ " ,deprpark = " + Common.sqlChar(oldDeprPark)
							+ " ,deprunit = " + Common.sqlChar(oldDeprUnit)
							+ " ,deprunit1 = " + Common.sqlChar(oldDeprUnit1)
							+ " ,depraccounts = " + Common.sqlChar(oldDeprAccounts)
							+ " ,scrapvalue = " + Common.getInteger(oldScrapValue)
							+ " ,depramount = " + Common.getInteger(oldDeprAmount)
							+ " ,accumdepr = " + Common.getInteger(oldAccumDepr)
							+ " ,apportionmonth = " + Common.getInteger(oldApportionMonth)
							+ " ,monthdepr = " + Common.getInteger(oldMonthDepr)
							+ " ,monthdepr1 = " + Common.getInteger(oldMonthDepr1)
							+ " ,apportionendym = " + Common.sqlChar(oldApportionEndYM)
							+ " ,otherlimityear = " + Common.sqlChar(oldotherlimityear)
							+ " where enterorg = " + Common.sqlChar(enterOrg)
							+ " and ownership = " + Common.sqlChar(ownership)
							+ " and differencekind = " + Common.sqlChar(differenceKind)
							+ " and propertyno = " + Common.sqlChar(propertyNo)
							+ " and serialno = " + Common.sqlChar(serialNo);
					}
					db.excuteSQL(querySQL);
				}
				
				this.setStateUpdateSuccess();
				
			}catch(Exception e){
				e.printStackTrace();
			}finally{
				db.closeAll();
			}
		}
	}

	/**
	 * @deprecated
	 */
	public void approveRe()throws Exception{	
		Database db = new Database();
		String enterOrgQuery = "";
		String ownershipQuery = "";
		String caseNoQuery = "";
		String differenceKindQuery = "";
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
			String sql ="select a.caseNo, a.differenceKind, a.enterOrg, a.ownership, a.propertyNo, a.serialNo, a.serialNo1, " +
					" a.editDate, a.editTime, a.adjustDate, a.oldBookValue, " +
					" a.oldBookValue as adjustBookValue " +
					" from untrt_adjustProof a " +
					" where 1=1 " +
					" and a.enterOrg = " + Common.sqlChar(enterOrg) +
					" and a.ownership = " + Common.sqlChar(ownership) +
					" and a.caseNo = " + Common.sqlChar(caseNo) +
					" and a.differenceKind = " + Common.sqlChar(differenceKind) +
					" and a.propertyNo = " + Common.sqlChar(propertyNo) +
					" and a.serialNo = " + Common.sqlChar(serialNo) +
					" order by a.enterOrg, a.ownership, a.propertyNo, a.serialNo, a.serialNo1 " +
					"" ;
			ResultSet rs = db.querySQL(sql);
			while (rs.next()){
				count++;
				enterOrgQuery = rs.getString("enterOrg");
				ownershipQuery = rs.getString("ownership");
				caseNoQuery = rs.getString("caseNo");
				differenceKindQuery = rs.getString("differenceKind");
				propertyNoQuery = rs.getString("propertyNo");
				serialNoQuery = rs.getString("serialNo");
				serialNo1Query = rs.getString("serialNo1");
				//該增減值單之明細資料,存在未入帳的「減損單明細檔UNTRT_ReduceDetail」資料，則提示使用者
				reduceSql = "select count(*) count from untrt_reduceProof a " +
						" where a.enterOrg="+Common.sqlChar(enterOrgQuery)+" and a.ownership="+Common.sqlChar(ownershipQuery)+" and a.differenceKind="+Common.sqlChar(differenceKindQuery)+" and a.propertyNo="+Common.sqlChar(propertyNoQuery)+" and a.serialNo="+Common.sqlChar(serialNoQuery)+" and a.verify='N'" ;
				reduceCount = MaxClosingYM.getCountValues(reduceSql);

				//該增減值單之明細資料,存在未入帳的「增減值單明細檔UNTRT_AdjustDetail」資料，則提示使用者
				adjustSql = "select count(*) count from untrt_adjustProof a " +
						" where a.enterOrg="+Common.sqlChar(enterOrgQuery)+" and a.ownership="+Common.sqlChar(ownershipQuery)+" and a.differenceKind="+Common.sqlChar(differenceKindQuery)+" and a.propertyNo="+Common.sqlChar(propertyNoQuery)+" and a.serialNo="+Common.sqlChar(serialNoQuery)+" and a.verify='N'" ;
				adjustCount = MaxClosingYM.getCountValues(adjustSql);

				//------------------------------------------------------------------------------------------------
				adjustOriginal = rs.getString("editDate")+rs.getString("editTime")+rs.getString("adjustDate");
				//該增減值單之(異動日期+異動時間+減損日期)≦增減值單明細資料之其他增減值單之最大的(異動日期+異動時間+減損日期)則提示使用者
				adjustMaxSql = "select count(*) count from untrt_adjustProof a " +
						" where a.enterOrg="+Common.sqlChar(enterOrgQuery)+" and a.ownership="+Common.sqlChar(ownershipQuery)+" and a.differenceKind="+Common.sqlChar(differenceKindQuery)+" and a.propertyNo="+Common.sqlChar(propertyNoQuery)+" and a.serialNo="+Common.sqlChar(serialNoQuery)+" and a.verify='Y' " +
						" and '" + adjustOriginal + "'<=(a.editDate || a.editTime || a.adjustDate) " +
						" and a.caseNo!="+Common.sqlChar(caseNoQuery) ;
				adjustMax = MaxClosingYM.getCountValues(adjustMaxSql);

				//該增減值單之(異動日期+異動時間+減損日期)≦增減值單明細資料之其他減損單之最大的(異動日期+異動時間+減損日期)則提示使用者
				reduceMaxSql = "select count(*) count from untrt_reduceProof a " +
						" where a.enterOrg="+Common.sqlChar(enterOrgQuery)+" and a.ownership="+Common.sqlChar(ownershipQuery)+" and a.differenceKind="+Common.sqlChar(differenceKindQuery)+" and a.propertyNo="+Common.sqlChar(propertyNoQuery)+" and a.serialNo="+Common.sqlChar(serialNoQuery)+" and a.verify='Y' " +
						" and '" + adjustOriginal + "'<=(a.editDate || a.editTime || a.reduceDate) " ;
				reduceMax = MaxClosingYM.getCountValues(reduceMaxSql);
				//------------------------------------------------------------------------------------------------
				if(closing.equals("N") && verify.equals("Y") && reduceCount.equals("0") && adjustCount.equals("0") && reduceMax.equals("0") && adjustMax.equals("0")){
					//依據該增減值單「入帳機關enterOrg＋權屬ownership＋電腦單號caseNo」設定
					if(count==1){
						strSQL += "update untrt_adjustProof set verify ='N' " +
								" where enterOrg=" + Common.sqlChar(enterOrgQuery) +
								" and ownership=" + Common.sqlChar(ownershipQuery) +
								" and caseNo=" + Common.sqlChar(caseNoQuery) +
								" and differenceKind=" + Common.sqlChar(differenceKindQuery) +
								" and propertyNo = " + Common.sqlChar(propertyNo) +
								" and serialNo = " + Common.sqlChar(serialNo) +
								":;:";				
					}

					//依據增減值單明細「入帳機關enterOrg＋權屬ownership＋財產編號propertyNo＋財產分號serialNo」設定
					strSQL +="update untrt_AddProof set " +
							" bookValue = " + rs.getString("adjustBookValue") +
							" where enterOrg=" + Common.sqlChar(enterOrgQuery) +
							" and ownership=" + Common.sqlChar(ownershipQuery) +
							" and differenceKind=" + Common.sqlChar(differenceKindQuery) +
							" and propertyNo=" + Common.sqlChar(propertyNoQuery) +
							" and serialNo=" + Common.sqlChar(rs.getString("serialNo")) +
							":;:";

					//依據增減值單明細「入帳機關enterOrg＋權屬ownership＋財產編號propertyNo＋財產分號serialNo」設定
					strSQL +="update untrt_AddDetail set " +
							" bookValue = " + rs.getString("oldBookValue") +
							" where enterOrg=" + Common.sqlChar(enterOrgQuery) +
							" and ownership=" + Common.sqlChar(ownershipQuery) +
							" and differenceKind=" + Common.sqlChar(differenceKindQuery) +
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


