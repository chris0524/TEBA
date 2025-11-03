package unt.vp;

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
 * <br/>程式目的：有價證券增減值作業－增減值單資料
 * <br/>程式代號：untvp006f
 * <br/>程式日期：0941013
 * <br/>程式作者：cherry
 * <br/>--------------------------------------------------------
 * <br/>修改作者　　     修改日期　　　			修改目的
 * <br/>
 * <br/>--------------------------------------------------------
 */
public class UNTVP006F extends UNTVP006Q{
	
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
	private String sourceDate;
	private String buyDate;
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
	private String securityName;
	private String oldBookAmount;
	private String oldBookValue;
	private String addBookAmount;
	private String addBookValue;
	private String reduceBookAmount;
	private String reduceBookValue;
	private String newBookAmount;
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
	private String newBookSheet;
	private String closing1ym;
	
	public String getNewBookSheet() {return checkGet(newBookSheet);}
	public void setNewBookSheet(String newBookSheet) {this.newBookSheet = checkSet(newBookSheet);}
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
	public String getSourceDate() {return checkGet(sourceDate);}
	public void setSourceDate(String sourceDate) {this.sourceDate = checkSet(sourceDate);}
	public String getBuyDate() {return checkGet(buyDate);}
	public void setBuyDate(String buyDate) {this.buyDate = checkSet(buyDate);}
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
	public String getSecurityName() {return checkGet(securityName);}
	public void setSecurityName(String securityName) {this.securityName = checkSet(securityName);}
	public String getOldBookAmount() {return checkGet(oldBookAmount);}
	public void setOldBookAmount(String oldBookAmount) {this.oldBookAmount = checkSet(oldBookAmount);}
	public String getOldBookValue() {return checkGet(oldBookValue);}
	public void setOldBookValue(String oldBookValue) {this.oldBookValue = checkSet(oldBookValue);}
	public String getAddBookAmount() {return checkGet(addBookAmount);}
	public void setAddBookAmount(String addBookAmount) {this.addBookAmount = checkSet(addBookAmount);}
	public String getAddBookValue() {return checkGet(addBookValue);}
	public void setAddBookValue(String addBookValue) {this.addBookValue = checkSet(addBookValue);}
	public String getReduceBookAmount() {return checkGet(reduceBookAmount);}
	public void setReduceBookAmount(String reduceBookAmount) {this.reduceBookAmount = checkSet(reduceBookAmount);}
	public String getReduceBookValue() {return checkGet(reduceBookValue);}
	public void setReduceBookValue(String reduceBookValue) {this.reduceBookValue = checkSet(reduceBookValue);}
	public String getNewBookAmount() {return checkGet(newBookAmount);}
	public void setNewBookAmount(String newBookAmount) {this.newBookAmount = checkSet(newBookAmount);}
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
	
		
	String isOrganManager;
	String isAdminManager;
	String organID;    
	public String getOrganID() { return checkGet(organID); }
	public void setOrganID(String s) { organID = checkSet(s); }
	public String getIsOrganManager() { return checkGet(isOrganManager); }
	public void setIsOrganManager(String s) { isOrganManager = checkSet(s); }
	public String getIsAdminManager() { return checkGet(isAdminManager); }
	public void setIsAdminManager(String s) { isAdminManager = checkSet(s); }    
	
	String verifyError;	
	public String getVerifyError(){ return checkGet(verifyError); }
	public void setVerifyError(String s){ verifyError=checkSet(s); }
	
	String closing;	
	public String getClosing(){ return checkGet(closing); }
	public void setClosing(String s){ closing=checkSet(s); }
	
	String oldVerify;
	String checkEnterOrg;
	public String getCheckEnterOrg() {return checkGet(checkEnterOrg);}
	public void setCheckEnterOrg(String checkEnterOrg) {this.checkEnterOrg = checkSet(checkEnterOrg);}
	public String getOldVerify() {return checkGet(oldVerify);}
	public void setOldVerify(String oldVerify) {this.oldVerify = checkSet(oldVerify);}
	
	private String reVerify;
	public String getReVerify() {return reVerify;}
	public void setReVerify(String reVerify) {this.reVerify = reVerify;}
	private String summonsDate;
	public String getSummonsDate() {return checkGet(summonsDate);}
	public void setSummonsDate(String summonsDate) {this.summonsDate = checkSet(summonsDate);}
	public String getClosing1ym() {return checkGet(closing1ym);}
	public void setClosing1ym(String closing1ym) {this.closing1ym = checkSet(closing1ym);}
		
	
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
	
	private Map getRecordMap(){
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
		map.put("SourceDate", ut._transToCE_Year(getSourceDate()));
		map.put("BuyDate", ut._transToCE_Year(getBuyDate()));
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
		map.put("SecurityName", getSecurityName());
		map.put("OldBookAmount", getOldBookAmount());
		map.put("OldBookValue", getOldBookValue());
		map.put("AddBookAmount", getAddBookAmount());
		map.put("AddBookValue", getAddBookValue());
		map.put("ReduceBookAmount", getReduceBookAmount());
		map.put("ReduceBookValue", getReduceBookValue());
		map.put("NewBookAmount", getNewBookAmount());
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
		map.put("summonsDate", getSummonsDate());
		
		return map;
	}
	
	@Override
	protected String[][] getInsertCheckSQL() {
		List<String[]> sqls = new ArrayList<String[]>();
		
		String[] tmpchk = new String[5];
		tmpchk[0] = " select count(*) as checkResult from UNTVP_ADJUSTPROOF where 1=1 " +
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
		tmpchk[0] = " select count(*) as checkResult from UNTVP_REDUCEPROOF where 1=1 " + 
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
		//	setProofNo(MaxClosingYM.getMaxProofNo("UNTVP_AdjustProof",enterOrg,ownership));
		String[] execSQLArray = new String[1];
		execSQLArray[0] = new SQLCreator()._obtainSQLforInsert("UNTVP_ADJUSTPROOF", getPKMap(), getRecordMap());
		return execSQLArray;
	}

	public String queryOldVerify() {
		Database db = null;
		try {
			db = new Database();
			return db.getLookupField("select verify from UNTVP_ADJUSTPROOF where enterorg = " + Common.sqlChar(this.getEnterOrg()) + " and caseno = " + Common.sqlChar(this.getCaseNo()));
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
			tmpchk[0] = " select count(case when isnull(z.adjustdate, '') != '' then 1 else null end) as checkResult from UNTVP_ADJUSTPROOF  a "
					  + " OUTER APPLY ( " 
					  + " select top 1 b.adjustdate  from UNTVP_ADJUSTPROOF b  "
					  + " where a.enterorg=b.enterorg and a.ownership = b.ownership and a.differencekind=b.differencekind and a.propertyno=b.propertyno and a.serialno=b.serialno  "
					  + " and verify ='Y' and b.caseno != a.caseno and b.adjustdate >= "  + ut._transToCE_Year(this.getAdjustDate())
					  + " ) as z "
					  + " where a.enterorg = " + Common.sqlChar(this.getEnterOrg()) + " and a.caseno = " + Common.sqlChar(this.getCaseNo());
			tmpchk[1] = "!=";
			tmpchk[2] = "0";
			tmpchk[3] = "入帳失敗, 檢查到已有入帳的有價證券增減值資料其入帳日期大於等於此單據入帳日期";
			tmpchk[4] = "N";
			sqls.add(tmpchk);
			
		} else if ("Y".equals(oldVerify) && "Y".equals(this.getReVerify())) {
			// 回復入帳
			tmpchk = new String[5];
			tmpchk[0] = " select count(*) as checkResult from UNTVP_ADJUSTPROOF  a "
					  + " inner join UNTVP_REDUCEPROOF b  " 
					  + " on a.enterorg=b.enterorg and a.ownership=b.ownership and a.differencekind=b.differencekind  "
					  + " and a.propertyno=b.propertyno and a.serialno=b.serialno  "
					  + " where a.enterorg = " + Common.sqlChar(this.getEnterOrg()) + " and a.caseno =" + Common.sqlChar(this.getCaseNo());
			tmpchk[1] = "!=";
			tmpchk[2] = "0";
			tmpchk[3] = "回復入帳失敗, 檢查到已有有價證券資料於減損作業中登打,請先移除該減損單";
			tmpchk[4] = "N";
			sqls.add(tmpchk);
			
			tmpchk = new String[5];
			tmpchk[0] = " select count(case when isnull(z.adjustdate, '') != '' then 1 else null end) as checkResult from UNTVP_ADJUSTPROOF  a "
					  + " OUTER APPLY ( " 
					  + " select top 1 b.adjustdate  from UNTVP_ADJUSTPROOF b  "
					  + " where a.enterorg=b.enterorg and a.ownership = b.ownership and a.differencekind=b.differencekind and a.propertyno=b.propertyno and a.serialno=b.serialno  "
					  + " and verify ='Y' and b.adjustdate >= a.adjustdate and b.caseno != a.caseno" 
					  + " ) as z "
					  + " where a.enterorg = " + Common.sqlChar(this.getEnterOrg()) + " and a.caseno = " + Common.sqlChar(this.getCaseNo());
			tmpchk[1] = "!=";
			tmpchk[2] = "0";
			tmpchk[3] = "回復入帳失敗, 已有有價證券資料於其他較新的增減值單中入帳";
			tmpchk[4] = "N";
			sqls.add(tmpchk);
		}
		
		return sqls.toArray(new String[sqls.size()][5]);
	}
	
	//傳回update sql
	protected String[] getUpdateSQL(){
		//取得增減值單編號
		//	setProofNo(MaxClosingYM.getMaxProofNo("UNTVP_AdjustProof",enterOrg,ownership));
		String strSQL = "";
		strSQL += new SQLCreator()._obtainSQLforUpdate("UNTVP_ADJUSTPROOF", getPKMap(), getRecordMap()) + ":;:";

		strSQL += updateTable();

		String[] execSQLArray = strSQL.split(":;:");
		return execSQLArray;
	}

	/**
	 * exception catched and print 
	 */
	protected void execVerify(){

		String sql = "select propertyNo, serialno from UNTVP_AdjustProof" +
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
		execSQLArray[0] = new SQLCreator()._obtainSQLforDelete("UNTVP_ADJUSTPROOF", getPKMap(), getRecordMap());
		return execSQLArray;
	}

	protected void queryDeleteData(SuperBean2 sb){
		UNTCH_Tools ut = new UNTCH_Tools();
		Map map = queryPropertyNofrom("UNTVP_ADJUSTPROOF",sb);					
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
	public UNTVP006F  queryOne() throws Exception{
		Database db = new Database();
		UNTVP006F obj = this;
		try {
			String sql=" select a.*, " +
					" b.closing1ym " +
					" from UNTVP_AdjustProof a inner join UNTAC_CLOSINGPT b on a.enterorg = b.enterorg and a.differencekind = b.differencekind where 1=1 " +
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
				obj.setSourceDate(ut._transToROC_Year(rs.getString("SourceDate")));
				obj.setBuyDate(ut._transToROC_Year(rs.getString("BuyDate")));
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
				obj.setSecurityName(rs.getString("SecurityName"));
				obj.setOldBookAmount(rs.getString("OldBookAmount"));
				obj.setOldBookValue(rs.getString("OldBookValue"));
				obj.setAddBookAmount(rs.getString("AddBookAmount"));
				obj.setAddBookValue(rs.getString("AddBookValue"));
				obj.setReduceBookAmount(rs.getString("ReduceBookAmount"));
				obj.setReduceBookValue(rs.getString("ReduceBookValue"));
				obj.setNewBookAmount(rs.getString("NewBookAmount"));
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
				obj.setSummonsDate(rs.getString("summonsDate"));
				obj.setClosing1ym(rs.getString("closing1ym"));
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
			UNTCH_Tools ut = new UNTCH_Tools();

			sql = "update UNTVP_AdjustProof set"+
					" verify = 'Y'," +
					" adjustDate = " + Common.sqlChar(ut._transToCE_Year(getAdjustDate())) + "," +
					" editID = " + Common.sqlChar(getEditID()) + "," +
					" editDate = " + Common.sqlChar(ut._transToCE_Year(getEditDate())) + "," +
					" editTime = " + Common.sqlChar(getEditTime()) + 	                
					" where 1=1 " + 
					" and enterOrg = " + Common.sqlChar(enterOrg) +
					" and ownership = " + Common.sqlChar(ownership) +
					" and caseNo = " + Common.sqlChar(caseNo) +
					" and differenceKind = " + Common.sqlChar(differenceKind) +
					" and propertyNo = " + Common.sqlChar(propertyNo) +
					" and serialNo = " + Common.sqlChar(serialNo) +
					":;:";

			sql+= "update UNTVP_AddProof set"+
					" bookAmount = " + Common.sqlChar(newBookAmount) + ","+
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

			//將「入帳」由「Y:已核章」改為「N:未核章」
		}else if(getVerify().equals("N")){
			UNTCH_Tools ut = new UNTCH_Tools();

			sql = "update UNTVP_AdjustProof set"+
					" verify = 'N'," +
					" editID = " + Common.sqlChar(getEditID()) + "," +
					" editDate = " + Common.sqlChar(ut._transToCE_Year(getEditDate())) + "," +
					" editTime = " + Common.sqlChar(getEditTime()) +         
					" where 1=1 " + 
					" and enterOrg = " + Common.sqlChar(enterOrg) +
					" and ownership = " + Common.sqlChar(ownership) +
					" and caseNo = " + Common.sqlChar(caseNo) +
					" and differenceKind = " + Common.sqlChar(differenceKind) +
					" and propertyNo = " + Common.sqlChar(propertyNo) +
					" and serialNo = " + Common.sqlChar(serialNo) +
					":;:"; 

			sql+= "update UNTVP_AddProof set"+
					" bookAmount = " + Common.sqlChar(oldBookAmount) + ","+
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
	 * 回復入帳設定
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
		String adjustSql = "",adjustCount = "";
		String adjustOriginal = "";
		String adjustMax = "",adjustMaxSql = "";
		int count = 0;
		try {    
			String[] execSQLArray;
			String strSQL = "";
			String sql ="select a.caseNo, a.differenceKind, b.enterOrg, b.ownership, b.propertyNo, b.serialNo, b.serialNo1, " +
					" a.editDate, a.editTime, a.adjustDate, b.adjustType, " +
					" a.oldBookSheet, a.oldBookAmount, a.oldBookValue, " +
					" b.oldBookUA, b.oldBookSheet as oldBookSheetDetail, b.oldBookAmount as oldBookAmountDetail, " +
					" b.oldBookUV, b.oldBookValue as oldBookValueDetail, b.oldProofS, b.oldProofE " +
					" from untvp_adjustProof a, untvp_adjustDetail b " +
					" where 1=1 " +
					" and a.enterOrg = b.enterOrg and a.ownership = b.ownership and a.caseNo = b.caseNo and a.propertyNo = b.propertyNo and a.serialNo = b.serialNo " +
					" and a.enterOrg = " + Common.sqlChar(enterOrg) +
					" and a.ownership = " + Common.sqlChar(ownership) +
					" and a.caseNo = " + Common.sqlChar(caseNo) +
					" and a.differenceKind = " + Common.sqlChar(differenceKind) +
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
				differenceKindQuery = rs.getString("differenceKind");
				propertyNoQuery = rs.getString("propertyNo");
				serialNoQuery = rs.getString("serialNo");
				serialNo1Query = rs.getString("serialNo1");

				//該增減值單之明細資料,存在未入帳的「增減值單明細檔UNTVP_AdjustDetail」資料，則提示使用者
				adjustSql = "select count(*) count from untvp_adjustProof a " +
						" where a.enterOrg="+Common.sqlChar(enterOrgQuery)+" and a.ownership="+Common.sqlChar(ownershipQuery)+" and a.differenceKind="+Common.sqlChar(differenceKindQuery)+" and a.propertyNo="+Common.sqlChar(propertyNoQuery)+" and a.serialNo="+Common.sqlChar(serialNoQuery)+" and a.verify='N'" ;
				adjustCount = MaxClosingYM.getCountValues(adjustSql);
				//------------------------------------------------------------------------------------------------
				adjustOriginal = rs.getString("editDate")+rs.getString("editTime")+rs.getString("adjustDate");
				//該增減值單之(異動日期+異動時間+減損日期)≦增減值單明細資料之其他增減值單之最大的(異動日期+異動時間+減損日期)則提示使用者
				adjustMaxSql = "select count(*) count from untvp_adjustProof a " +
						" where a.enterOrg="+Common.sqlChar(enterOrgQuery)+" and a.ownership="+Common.sqlChar(ownershipQuery)+" and a.differenceKind="+Common.sqlChar(differenceKindQuery)+" and a.propertyNo="+Common.sqlChar(propertyNoQuery)+" and a.serialNo="+Common.sqlChar(serialNoQuery)+" and a.verify='Y' " +
						" and '" + adjustOriginal + "'<=(a.editDate || a.editTime || a.adjustDate) " +
						" and a.caseNo!="+Common.sqlChar(caseNoQuery) ;
				adjustMax = MaxClosingYM.getCountValues(adjustMaxSql);
				//------------------------------------------------------------------------------------------------
				if(closing.equals("N") && verify.equals("Y") && adjustCount.equals("0") && adjustMax.equals("0")){
					//依據該增減值單「入帳機關enterOrg＋權屬ownership＋電腦單號caseNo」設定
					if(count==1){
						strSQL += "update untvp_adjustProof set verify ='N' " +
								" where enterOrg=" + Common.sqlChar(enterOrgQuery) +
								" and ownership=" + Common.sqlChar(ownershipQuery) +
								" and caseNo=" + Common.sqlChar(caseNoQuery) +
								" and differenceKind=" + Common.sqlChar(differenceKindQuery) +
								" and propertyNo = " + Common.sqlChar(propertyNo) +
								" and serialNo = " + Common.sqlChar(serialNo) +
								":;:";				
					}

					//依據該增減值單「入帳機關enterOrg＋權屬ownership＋財產編號propertyNo＋財產分號serialNo」設定
					strSQL +="update untvp_AddProof set " +
							" bookSheet = " + rs.getString("oldBookSheet") + "," +
							" bookAmount = " + rs.getString("oldBookAmount") + "," +
							" bookValue = " + rs.getString("oldBookValue") + "," +
							" dataState = '1' " +
							" where enterOrg=" + Common.sqlChar(enterOrgQuery) +
							" and ownership=" + Common.sqlChar(ownershipQuery) +
							" and differenceKind=" + Common.sqlChar(differenceKindQuery) +
							" and propertyNo=" + Common.sqlChar(propertyNoQuery) +
							" and serialNo=" + Common.sqlChar(rs.getString("serialNo")) +
							":;:";
					//依據該增減值單明細中「增減別adjustType」為
					//「1:增加」之「入帳機關enterOrg＋權屬ownership＋財產編號propertyNo＋財產分號serialNo＋股份次序serialNo1」刪除「UNTVP_Share有價證券股份檔」資料
					if("1".equals(rs.getString("adjustType"))){
						strSQL += "delete UNTVP_Share " +
								" where enterOrg=" + Common.sqlChar(enterOrgQuery) +
								" and ownership=" + Common.sqlChar(ownershipQuery) +
								" and differenceKind=" + Common.sqlChar(differenceKindQuery) +
								" and propertyNo=" + Common.sqlChar(propertyNoQuery) +
								" and serialNo=" + Common.sqlChar(rs.getString("serialNo")) +
								" and serialNo1=" + Common.sqlChar(serialNo1Query) + 
								":;:";
						//依據該增減值單明細中「增減別adjustType」為
						//「2:減少」之「入帳機關enterOrg＋權屬ownership＋財產編號propertyNo＋財產分號serialNo＋股份次序serialNo1」更新「UNTVP_Share有價證券股份檔」資料。
					}else if("2".equals(rs.getString("adjustType"))){
						strSQL += "update UNTVP_Share set " +
								" dataState = '1', " +
								" reduceDate = null, " +
								" reduceCause = null, " +
								" reduceCause1 = null, " +
								//" bookUnitAmount = " + rs.getString("oldBookUA") + "," +
								//" bookSheet = " + rs.getString("oldBookSheetDetail") + "," +
								" bookAmount = " + rs.getString("oldBookAmountDetail") + "," +
								" bookUnitValue = " + rs.getString("oldBookUV") + "," +
								" bookValue = " + rs.getString("oldBookValueDetail") + "," +
								" proofS = " + Common.sqlChar(rs.getString("oldProofS")) + "," +
								" proofE = " + Common.sqlChar(rs.getString("oldProofE")) +
								" where enterOrg=" + Common.sqlChar(enterOrgQuery) +
								" and ownership=" + Common.sqlChar(ownershipQuery) +
								" and differenceKind=" + Common.sqlChar(differenceKindQuery) +
								" and propertyNo=" + Common.sqlChar(propertyNoQuery) +
								" and serialNo=" + Common.sqlChar(rs.getString("serialNo")) +
								" and serialNo1=" + Common.sqlChar(serialNo1Query) + 
								":;:";
					}
					//----------------------------------------
				}else{
					setVerifyError("Y");
					if(closing.equals("Y")){
						setErrorMsg("已月結的資料無法回復入帳，請先取消月結，再回此作業回復入帳！");
					}else if(verify.equals("N")){
						setErrorMsg("尚未入帳，請直接修改資料即可！");
					}else if(!adjustCount.equals("0")){
						setErrorMsg("增減值作業存在未入帳的資料，無法回復入帳！");
					}else if(!adjustMax.equals("0")){
						setErrorMsg("並非最後一筆入帳(增減值)的資料，無法回復入帳！");
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


