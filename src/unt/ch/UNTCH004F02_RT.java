package unt.ch;

import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import util.Common;
import util.Database;
import util.NewDateUtil;
import util.SuperBean2;
import util.NewDateUtil.DateFormat;
import TDlib_Simple.com.src.SQLCreator;

public class UNTCH004F02_RT extends SuperBean2{

	public UNTCH004F02_RT() {
		
	}
	
	public UNTCH004F02_RT(String caller) {
		this.setCaller(caller);
	}
	
	private String caller;
	public String getCaller() { return checkGet(caller); }
	public void setCaller(String caller) { this.caller = checkSet(caller); }
	 
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
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
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
		map.put("otherPropertyUnit", getOtherPropertyUnit());
		map.put("PropertyName1", getPropertyName1());
		map.put("WriteDate", getWriteDate());
		map.put("WriteUnit", getWriteUnit());
		map.put("ProofYear", getProofYear());
		map.put("ProofDoc", getProofDoc());
		map.put("ProofNo", getProofNo());
		map.put("ManageNo", getManageNo());
		map.put("SummonsNo", getSummonsNo());
		map.put("Cause", getCause());
		map.put("Cause1", getCause1());
		map.put("ReduceDate", ut._transToCE_Year(getReduceDate()));
		map.put("NewEnterOrg", getNewEnterOrg());
		map.put("TransferDate", ut._transToCE_Year(getTransferDate()));
		map.put("ApproveOrg", getApproveOrg());
		map.put("ApproveDate", ut._transToCE_Year(getApproveDate()));
		map.put("ApproveDoc", getApproveDoc());
		map.put("BookNotes", getBookNotes());
		map.put("Verify", getVerify());
		map.put("PropertyKind", getPropertyKind());
		map.put("FundType", getFundType());
		map.put("SourceDoc", getSourceDoc());
		map.put("BuyDate", ut._transToCE_Year(getBuyDate()));
		map.put("sourceDate", ut._transToCE_Year(sourceDate));
		map.put("Meat", getMeat());
		map.put("BookValue", getBookValue());
		map.put("NetValue", getNetValue());
		map.put("MoveDate", ut._transToCE_Year(getMoveDate()));
		map.put("KeepUnit", getKeepUnit());
		map.put("Keeper", getKeeper());
		map.put("UseUnit", getUseUnit());
		map.put("UserNo", getUserNo());
		map.put("UserNote", getUserNote());
		map.put("Place1", getPlace1());
		map.put("Place", getPlace());
		map.put("OldPropertyNo", getOldPropertyNo());
		map.put("OldSerialNo", getOldSerialNo());
		map.put("NewEnterOrgReceive", getNewEnterOrgReceive());
		map.put("Notes", getNotes());
		map.put("EditID", getEditID());
		map.put("EditDate", ut._transToCE_Year(getEditDate()));
		map.put("EditTime", NewDateUtil.getNow(DateFormat.HHMMSS));
		map.put("summonsDate", getSummonsDate());
		map.put("preenterdate", ut._transToCE_Year(this.getPreenterdate()));
		
		
		if ("UNTCH004F02".equals(this.getCaller())) {
			map.put("useYear", this.getUseYear());
			map.put("useMonth", this.getUseMonth());
			map.put("otherLimitYear", this.getOtherLimitYear());
			
			
			map.put("oldDeprMethod", this.getOldDeprMethod());
			map.put("oldBuildFeeCB", this.getOldBuildFeeCB());
			map.put("oldDeprUnitCB", this.getOldDeprUnitCB());
			map.put("oldDeprPark", this.getOldDeprPark());
			map.put("oldDeprUnit", this.getOldDeprUnit());
			map.put("oldDeprUnit1", this.getOldDeprUnit1());
			map.put("oldDeprAccounts", this.getOldDeprAccounts());
			map.put("oldScrapValue", this.getOldScrapValue());
			map.put("oldDeprAmount", this.getOldDeprAmount());
			map.put("oldAccumDepr", this.getOldAccumDepr());
			map.put("oldApportionMonth", this.getOldApportionMonth());
			map.put("oldMonthDepr", this.getOldMonthDepr());
			map.put("newDeprMethod", this.getNewDeprMethod());
			map.put("newBuildFeeCB", this.getNewBuildFeeCB());
			map.put("newDeprUnitCB", this.getNewDeprUnitCB());
			map.put("newDeprPark", this.getNewDeprPark());
			map.put("newDeprUnit", this.getNewDeprUnit());
			map.put("newDeprUnit1", this.getNewDeprUnit1());
			map.put("newDeprAccounts", this.getNewDeprAccounts());
			map.put("newScrapValue", this.getNewScrapValue());
			map.put("newDeprAmount", this.getNewDeprAmount());
			map.put("newAccumDepr", this.getNewAccumDepr());
			map.put("newApportionMonth", this.getNewApportionMonth());
			map.put("newMonthDepr", this.getNewMonthDepr());
			
		}
		
		return map;
	}

	//傳回執行insert前之檢查sql
	protected String[][] getInsertCheckSQL(){
		String[][] checkSQLArray = new String[3][4];
	 	checkSQLArray[0][0]=" select count(*) as checkResult from UNTRT_REDUCEPROOF where 1=1 " + 
							" and enterorg = " + Common.sqlChar(enterOrg) + 
							" and ownership = " + Common.sqlChar(ownership) +
							" and differencekind = " + Common.sqlChar(differenceKind) +
							" and propertyno = " + Common.sqlChar(propertyNo) + 
							" and serialno = " + Common.sqlChar(serialNo) +  
							"";
		checkSQLArray[0][1]=">";
		checkSQLArray[0][2]="0";
		checkSQLArray[0][3]="該筆資料已重複，請重新輸入！";
		
	 	checkSQLArray[1][0]=" select count(*) as checkResult from UNTRT_ADDPROOF where 1=1 " + 
							" and enterorg = " + Common.sqlChar(enterOrg) + 
							" and ownership = " + Common.sqlChar(ownership) +
							" and differencekind = " + Common.sqlChar(differenceKind) +
							" and propertyno = " + Common.sqlChar(propertyNo) + 
							" and serialno = " + Common.sqlChar(serialNo) +  
							" and dataState = '1' " +
							" and verify = 'Y' " +
							"";
		checkSQLArray[1][1]="<=";
		checkSQLArray[1][2]="0";
		checkSQLArray[1][3]="權利主檔中找不到該財產編號和分號，請重新輸入！";
		
	 	checkSQLArray[2][0]=" select count(*) as checkResult from UNTRT_ADJUSTPROOF where 1=1 " + 
							" and enterorg = " + Common.sqlChar(enterOrg) + 
							" and ownership = " + Common.sqlChar(ownership) +
							" and differencekind = " + Common.sqlChar(differenceKind) +
							" and propertyno = " + Common.sqlChar(propertyNo) + 
							" and serialno = " + Common.sqlChar(serialNo) +  
							" and verify = 'N' " +
							"";
		checkSQLArray[2][1]=">";
		checkSQLArray[2][2]="0";
		checkSQLArray[2][3]="增減值作業存在未入帳的資料，請重新輸入!!";
		
		return checkSQLArray;
	}
	
	//傳回insert sql
	protected String[] getInsertSQL(){
		String[] execSQLArray = new String[1];
		execSQLArray[0] = new SQLCreator()._obtainSQLforInsert("UNTRT_ReduceProof", getPKMap(), getRecordMap());
		System.out.println("execSQLArray="+execSQLArray[0]);
		return execSQLArray;
	}


	//傳回update sql
	protected String[] getUpdateSQL(){
		String strSQL = ""; 
		strSQL += new SQLCreator()._obtainSQLforUpdate("UNTRT_ReduceProof", getPKMap(), getRecordMap()) +
				":;:";
		
		strSQL += updateTable();
		String[] execSQLArray = strSQL.split(":;:");
		
		return execSQLArray;
	}

	//傳回delete sql
	protected String[] getDeleteSQL(){
		String[] execSQLArray = new String[1];
		execSQLArray[0] = new SQLCreator()._obtainSQLforDelete("UNTRT_ReduceProof", getPKMap(), getRecordMap());
		return execSQLArray;
	}
	
	protected void queryDeleteData(SuperBean2 sb){
		UNTCH_Tools ut = new UNTCH_Tools();
		Map map = queryPropertyNofrom("UNTRT_ReduceProof",sb);					
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
	
	public UNTCH004F02_RT  queryOne() throws Exception{
		Database db = new Database();
		UNTCH004F02_RT obj = this;
		try {
			String sql=" select a.*, " +
				" (select c.organsname from SYSCA_ORGAN c where a.enterorg = c.organid)as enterOrgName, " +
				" (select e.organsname from SYSCA_ORGAN e where a.newenterorg = e.organid) as newenterOrgName, " +
				" (select d.propertyname from SYSPK_PROPERTYCODE d where a.propertyno = d.propertyno ) as propertyNoName, " +
				" (select d.propertyname from SYSPK_PROPERTYCODE d where a.oldpropertyno = d.propertyno ) as oldpropertyNoName, " +
				" (select codename from SYSCA_CODE z where codekindid = 'CAA' and z.codeid = a.cause and codecon1 in (2,4)) as causeName," +
				" sp.material as material, sp.propertyunit, sp.limityear" +
				" from UNTRT_REDUCEPROOF a "+
				" left join SYSPK_PROPERTYCODE sp on a.propertyno = sp.propertyno and sp.enterorg in (a.enterorg,'000000000A') " +
				" where 1=1 " +
				" and a.enterorg    = " + Common.sqlChar(enterOrg) +
				" and a.ownership   = " + Common.sqlChar(ownership) +
				" and a.caseno 	= " + Common.sqlChar(caseNo) +
				" and a.differencekind    = " + Common.sqlChar(differenceKind) +
				" and a.propertyno  = " + Common.sqlChar(propertyNo) +
				" and a.serialno    = " + Common.sqlChar(serialNo) +			
				" order by a.enterorg , a.ownership  "+
				"";

			ResultSet rs = db.querySQL_NoChange(sql);
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
				obj.setWriteDate(rs.getString("WriteDate"));
				obj.setWriteUnit(rs.getString("WriteUnit"));
				obj.setProofYear(rs.getString("ProofYear"));
				obj.setProofDoc(rs.getString("ProofDoc"));
				obj.setProofNo(rs.getString("ProofNo"));
				obj.setManageNo(rs.getString("ManageNo"));
				obj.setSummonsNo(rs.getString("SummonsNo"));
				obj.setCause(rs.getString("Cause"));
				obj.setCause1(rs.getString("Cause1"));
				obj.setReduceDate(ut._transToROC_Year(rs.getString("ReduceDate")));
				obj.setNewEnterOrg(rs.getString("NewEnterOrg"));
				obj.setTransferDate(ut._transToROC_Year(rs.getString("TransferDate")));
				obj.setApproveOrg(rs.getString("ApproveOrg"));
				obj.setApproveDate(ut._transToROC_Year(rs.getString("ApproveDate")));
				obj.setApproveDoc(rs.getString("ApproveDoc"));
				obj.setBookNotes(rs.getString("BookNotes"));
				obj.setVerify(rs.getString("Verify"));
				obj.setPropertyKind(rs.getString("PropertyKind"));
				obj.setFundType(rs.getString("FundType"));
				obj.setSourceDoc(rs.getString("SourceDoc"));
				obj.setBuyDate(ut._transToROC_Year(rs.getString("BuyDate")));
				obj.setSourceDate(ut._transToROC_Year(rs.getString("SourceDate")));
				obj.setMeat(rs.getString("Meat"));
				obj.setBookValue(rs.getString("BookValue"));
				obj.setNetValue(rs.getString("NetValue"));
				obj.setMoveDate(ut._transToROC_Year(rs.getString("MoveDate")));
				obj.setKeepUnit(rs.getString("KeepUnit"));
				obj.setKeeper(rs.getString("Keeper"));
				obj.setUseUnit(rs.getString("UseUnit"));
				obj.setUserNo(rs.getString("UserNo"));
				obj.setUserNote(rs.getString("UserNote"));
				obj.setPlace1(rs.getString("Place1"));
				obj.setPlace(rs.getString("Place"));
				obj.setOldPropertyNo(rs.getString("OldPropertyNo"));
				obj.setOldSerialNo(rs.getString("OldSerialNo"));
				obj.setNewEnterOrgReceive(rs.getString("NewEnterOrgReceive"));
				obj.setNotes(rs.getString("Notes"));
				obj.setEditID(rs.getString("EditID"));
				obj.setEditDate(ut._transToROC_Year(rs.getString("EditDate")));
				obj.setEditTime(rs.getString("EditTime"));
				obj.setEnterOrgName(rs.getString("EnterOrgName"));
				obj.setPropertyNoname(rs.getString("PropertyNoname"));
				obj.setCauseName(rs.getString("CauseName"));
				obj.setNewEnterOrgName(rs.getString("NewEnterOrgName"));
				obj.setOldPropertyNoName(rs.getString("OldPropertyNoName"));
				obj.setEngineeringNoName(ut._getEngineeringNoName(rs.getString("enterOrg"),rs.getString("engineeringNo")));
				obj.setSummonsDate(ut._transToROC_Year(rs.getString("summonsDate")));
				obj.setLimitYear(rs.getString("limityear"));
				obj.setOtherLimitYear(rs.getString("otherlimityear"));
				obj.setMaterial(rs.getString("material"));
				obj.setOtherMaterial(rs.getString("OtherMaterial"));
				obj.setPropertyUnit(rs.getString("PropertyUnit"));
				obj.setOtherPropertyUnit(rs.getString("OtherPropertyUnit"));
				obj.setUseYear(rs.getString("useYear"));
				obj.setUseMonth(rs.getString("useMonth"));
				
				obj.setOldDeprMethod(rs.getString("oldDeprMethod"));
				obj.setOldBuildFeeCB(rs.getString("oldBuildFeeCB"));
				obj.setOldDeprUnitCB(rs.getString("oldDeprUnitCB"));
				obj.setOldDeprPark(rs.getString("oldDeprPark"));
				obj.setOldDeprUnit(rs.getString("oldDeprUnit"));
				obj.setOldDeprUnit1(rs.getString("oldDeprUnit1"));
				obj.setOldDeprAccounts(rs.getString("oldDeprAccounts"));
				obj.setOldScrapValue(rs.getString("oldScrapValue"));
				obj.setOldDeprAmount(rs.getString("oldDeprAmount"));
				obj.setOldAccumDepr(rs.getString("oldAccumDepr"));
				obj.setOldApportionMonth(rs.getString("oldApportionMonth"));
				obj.setOldMonthDepr(rs.getString("oldMonthDepr"));
				
				obj.setNewDeprMethod(rs.getString("newDeprMethod"));
				obj.setNewBuildFeeCB(rs.getString("newBuildFeeCB"));
				obj.setNewDeprUnitCB(rs.getString("newDeprUnitCB"));
				obj.setNewDeprPark(rs.getString("newDeprPark"));
				obj.setNewDeprUnit(rs.getString("newDeprUnit"));
				obj.setNewDeprUnit1(rs.getString("newDeprUnit1"));
				obj.setNewDeprAccounts(rs.getString("newDeprAccounts"));
				obj.setNewScrapValue(rs.getString("newScrapValue"));
				obj.setNewDeprAmount(rs.getString("newDeprAmount"));
				obj.setNewAccumDepr(rs.getString("newAccumDepr"));
				obj.setNewApportionMonth(rs.getString("newApportionMonth"));
				obj.setNewMonthDepr(rs.getString("newMonthDepr"));
			}
			setStateQueryOneSuccess();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.closeAll();
		}
		return obj;
	} 
	
	protected void execVerify(){
		
		String sql = "select propertyNo, serialno from UNTRT_ReduceProof" +
						" where 1=1" +
						" and enterorg = " + Common.sqlChar(getEnterOrg()) +
						" and ownership = " + Common.sqlChar(getOwnership()) +
						" and caseNo = " + Common.sqlChar(getCaseNo()) +
						" and differenceKind = " + Common.sqlChar(getDifferenceKind());
		String adjustDatTemp = this.getReduceDate();
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
				this.setReduceDate(adjustDatTemp);
				this.update();
			}
			rs.close();
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			db.closeAll();
		}
	}
	
	//取得DB中單的入帳狀態
	protected String getDBVerify(){
		String verify = "";
		
		String sql = "select verify from UNTRT_ReduceProof "
								+ "where 1=1 "
								+ "and enterorg=" +Common.sqlChar(this.getEnterOrg())
								+ "and ownership=" +Common.sqlChar(this.getOwnership())
								+ "and differencekind=" +Common.sqlChar(this.getDifferenceKind())
								+ "and propertyno=" +Common.sqlChar(this.getPropertyNo())
								+ "and serialno=" +Common.sqlChar(this.getSerialNo())
								+ ";";
		Database db = null;
		ResultSet rs = null;
		try{
			db = new Database();
			rs = db.querySQL(sql);
			while(rs.next()){
				verify = checkGet(rs.getString("verify"));
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			db.closeAll();
		}
		return verify;
	}
	
	//入帳設定
	protected String updateTable(){
		String sql = "";
		if(getReVerify().equals("Y")){	
			UNTCH_Tools ut =new UNTCH_Tools();
			sql+= "update UNTRT_ReduceProof set"+
					" verify = " + Common.sqlChar("N") + 	                
					" where 1=1 " + 
					" and enterOrg = " + Common.sqlChar(enterOrg) +
					" and ownership = " + Common.sqlChar(ownership) +
					" and differenceKind = " + Common.sqlChar(differenceKind) +
					" and propertyNo = " + Common.sqlChar(propertyNo) +
					" and serialNo = " + Common.sqlChar(serialNo) +
					":;:";
	
			sql+= "update UNTRT_AddProof set"+
					" bookValue = " + Common.sqlChar(bookValue) + ","+
					" netValue = " + Common.sqlChar(netValue) + ","+
					// " enterDate = " + Common.sqlChar(reduceDate) + "," + ???? 搞屁 
					" reducedate = '', " +
					" reducecause = '', " + 
					" reducecause1 = '', " +  
					" editID = " + Common.sqlChar(getEditID()) + "," +
					" editDate = " + Common.sqlChar(ut._transToCE_Year(getEditDate())) + "," +
					" editTime = " + Common.sqlChar(getEditTime()) + "," +
					" datastate = " + Common.sqlChar("1") + 	                
					" where 1=1 " + 
					" and enterOrg = " + Common.sqlChar(enterOrg) +
					" and ownership = " + Common.sqlChar(ownership) +
					" and differenceKind = " + Common.sqlChar(differenceKind) +
					" and propertyNo = " + Common.sqlChar(propertyNo) +
					" and serialNo = " + Common.sqlChar(serialNo) +
					":;:";
			
		}else if("Y".equals(verify) && "N".equals(this.getDBVerify())){
			UNTCH_Tools ut =new UNTCH_Tools();
			sql+= "update UNTRT_ReduceProof set"+
					" verify = " + Common.sqlChar("Y") + 	                
					" where 1=1 " + 
					" and enterOrg = " + Common.sqlChar(enterOrg) +
					" and ownership = " + Common.sqlChar(ownership) +
					" and differenceKind = " + Common.sqlChar(differenceKind) +
					" and propertyNo = " + Common.sqlChar(propertyNo) +
					" and serialNo = " + Common.sqlChar(serialNo) +
					":;:";
			
			sql+= "update UNTRT_AddProof set"+
					" bookValue = " + Common.sqlChar("0") + ","+
					" netValue = " + Common.sqlChar("0") + ","+
					" reducedate = " + Common.sqlChar(ut._transToCE_Year(reduceDate)) + "," +
					" reducecause = " + Common.sqlChar(this.getCause()) + "," + 
					" reducecause1 = " + Common.sqlChar(this.getCause1()) + "," + 
//					" enterDate = " + Common.sqlChar(reduceDate) + "," +
					" editID = " + Common.sqlChar(getEditID()) + "," +
					" editDate = " + Common.sqlChar(ut._transToCE_Year(getEditDate())) + "," +
					" editTime = " + Common.sqlChar(getEditTime()) + "," +
					" datastate = " + Common.sqlChar("2") +               
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
	private String reduceDate;
	private String newEnterOrg;
	private String transferDate;
	private String approveOrg;
	private String approveDate;
	private String approveDoc;
	private String bookNotes;
	private String verify;
	private String propertyKind;
	private String fundType;
	private String sourceDoc;
	private String buyDate;
	private String sourceDate;
	private String meat;
	private String bookValue;
	private String netValue;
	private String moveDate;
	private String keepUnit;
	private String keeper;
	private String useUnit;
	private String userNo;
	private String userNote;
	private String place1;
	private String place;
	private String oldPropertyNo;
	private String oldSerialNo;
	private String newEnterOrgReceive;
	private String notes;
	private String editID;
	private String editDate;
	private String editTime;
	private String enterOrgName;
	private String propertyNoname;
	private String causeName;
	private String newEnterOrgName;
	private String oldPropertyNoName;
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
	public String getReduceDate() {return checkGet(reduceDate);}
	public void setReduceDate(String reduceDate) {this.reduceDate = checkSet(reduceDate);}
	public String getNewEnterOrg() {return checkGet(newEnterOrg);}
	public void setNewEnterOrg(String newEnterOrg) {this.newEnterOrg = checkSet(newEnterOrg);}
	public String getTransferDate() {return checkGet(transferDate);}
	public void setTransferDate(String transferDate) {this.transferDate = checkSet(transferDate);}
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
	public String getSourceDoc() {return checkGet(sourceDoc);}
	public void setSourceDoc(String sourceDoc) {this.sourceDoc = checkSet(sourceDoc);}
	public String getBuyDate() {return checkGet(buyDate);}
	public void setBuyDate(String buyDate) {this.buyDate = checkSet(buyDate);}	
	public String getSourceDate() {return checkGet(sourceDate);}
	public void setSourceDate(String sourceDate) {this.sourceDate = checkSet(sourceDate);}
	public String getMeat() {return checkGet(meat);}
	public void setMeat(String meat) {this.meat = checkSet(meat);}
	public String getBookValue() {return checkGet(bookValue);}
	public void setBookValue(String bookValue) {this.bookValue = checkSet(bookValue);}	
	public String getNetValue() {return checkSet(netValue);}
	public void setNetValue(String netValue) {this.netValue = checkSet(netValue);}
	public String getMoveDate() {return checkGet(moveDate);}
	public void setMoveDate(String moveDate) {this.moveDate = checkSet(moveDate);}
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
	public String getNewEnterOrgReceive() {return checkGet(newEnterOrgReceive);}
	public void setNewEnterOrgReceive(String newEnterOrgReceive) {this.newEnterOrgReceive = checkSet(newEnterOrgReceive);}
	public String getNotes() {return checkGet(notes);}
	public void setNotes(String notes) {this.notes = checkSet(notes);}
	public String getEditID() {return checkGet(editID);}
	public void setEditID(String editID) {this.editID = checkSet(editID);}
	public String getEditDate() {return checkGet(editDate);}
	public void setEditDate(String editDate) {this.editDate = checkSet(editDate);}
	public String getEditTime() {return checkGet(editTime);}
	public void setEditTime(String editTime) {this.editTime = checkSet(editTime);}
	public String getEnterOrgName() {return checkGet(enterOrgName);}
	public void setEnterOrgName(String enterOrgName) {this.enterOrgName = checkSet(enterOrgName);}
	public String getPropertyNoname() {return checkGet(propertyNoname);}
	public void setPropertyNoname(String propertyNoname) {this.propertyNoname = checkSet(propertyNoname);}
	public String getCauseName() {return checkGet(causeName);}
	public void setCauseName(String causeName) {this.causeName = checkSet(causeName);}
	public String getNewEnterOrgName() {return checkGet(newEnterOrgName);}
	public void setNewEnterOrgName(String newEnterOrgName) {this.newEnterOrgName = checkSet(newEnterOrgName);}
	public String getOldPropertyNoName() {return checkGet(oldPropertyNoName);}
	public void setOldPropertyNoName(String oldPropertyNoName) {this.oldPropertyNoName = checkSet(oldPropertyNoName);}
	
	private String engineeringNoName;
	public String getEngineeringNoName() {return checkGet(engineeringNoName);}
	public void setEngineeringNoName(String engineeringNoName) {this.engineeringNoName = checkSet(engineeringNoName);}
	
	private String reVerify;
	public String getReVerify() {return checkGet(reVerify);}
	public void setReVerify(String reVerify) {this.reVerify = checkSet(reVerify);}
	private String summonsDate;
	public String getSummonsDate() {return checkGet(summonsDate);}
	public void setSummonsDate(String summonsDate) {this.summonsDate = checkSet(summonsDate);}
	
	private String preenterdate;
	public String getPreenterdate() { return checkGet(preenterdate); }
	public void setPreenterdate(String preenterdate) { this.preenterdate = checkSet(preenterdate); }
	
	private String limitYear;
	private String otherLimitYear;
	private String material;
	private String otherMaterial;
	private String propertyUnit;
	private String otherPropertyUnit;
		
	public String getLimitYear() { return checkGet(limitYear); }
	public void setLimitYear(String limitYear) { this.limitYear = checkSet(limitYear); }
	public String getOtherLimitYear() { return checkGet(otherLimitYear); }
	public void setOtherLimitYear(String otherLimitYear) { this.otherLimitYear = checkSet(otherLimitYear); }
	public String getMaterial() { return checkGet(material); }
	public void setMaterial(String material) { this.material = checkSet(material); }
	public String getOtherMaterial() { return checkGet(otherMaterial); }
	public void setOtherMaterial(String otherMaterial) { this.otherMaterial = checkSet(otherMaterial); }
	public String getPropertyUnit() { return checkGet(propertyUnit); }
	public void setPropertyUnit(String propertyUnit) { this.propertyUnit = checkSet(propertyUnit); }
	public String getOtherPropertyUnit() { return checkGet(otherPropertyUnit); }
	public void setOtherPropertyUnit(String otherPropertyUnit) { this.otherPropertyUnit = checkSet(otherPropertyUnit); }
	
	private String useYear;
	private String useMonth;
	public String getUseYear() { return checkGet(useYear); }
	public void setUseYear(String useYear) { this.useYear = checkSet(useYear); }
	public String getUseMonth() { return checkGet(useMonth); }
	public void setUseMonth(String useMonth) { this.useMonth = checkSet(useMonth); }
	
	
	private String oldDeprMethod;
	private String oldBuildFeeCB;
	private String oldDeprUnitCB;
	private String oldDeprPark;
	private String oldDeprUnit;
	private String oldDeprUnit1;
	private String oldDeprAccounts;
	private String oldScrapValue;
	private String oldDeprAmount;
	private String oldAccumDepr;
	private String oldApportionMonth;
	private String oldMonthDepr;
	private String newDeprMethod;
	private String newBuildFeeCB;
	private String newDeprUnitCB;
	private String newDeprPark;
	private String newDeprUnit;
	private String newDeprUnit1;
	private String newDeprAccounts;
	private String newScrapValue;
	private String newDeprAmount;
	private String newAccumDepr;
	private String newApportionMonth;
	private String newMonthDepr;
	public String getOldDeprMethod() { return checkGet(oldDeprMethod); }
	public void setOldDeprMethod(String oldDeprMethod) { this.oldDeprMethod = checkSet(oldDeprMethod); }
	public String getOldBuildFeeCB() { return checkGet(oldBuildFeeCB); }
	public void setOldBuildFeeCB(String oldBuildFeeCB) { this.oldBuildFeeCB = checkSet(oldBuildFeeCB); }
	public String getOldDeprUnitCB() { return checkGet(oldDeprUnitCB); }
	public void setOldDeprUnitCB(String oldDeprUnitCB) { this.oldDeprUnitCB = checkSet(oldDeprUnitCB); }
	public String getOldDeprPark() { return checkGet(oldDeprPark); }
	public void setOldDeprPark(String oldDeprPark) { this.oldDeprPark = checkSet(oldDeprPark); }
	public String getOldDeprUnit() { return checkGet(oldDeprUnit); }
	public void setOldDeprUnit(String oldDeprUnit) { this.oldDeprUnit = checkSet(oldDeprUnit); }
	public String getOldDeprUnit1() { return checkGet(oldDeprUnit1); }
	public void setOldDeprUnit1(String oldDeprUnit1) { this.oldDeprUnit1 = checkSet(oldDeprUnit1); }
	public String getOldDeprAccounts() { return checkGet(oldDeprAccounts); }
	public void setOldDeprAccounts(String oldDeprAccounts) { this.oldDeprAccounts = checkSet(oldDeprAccounts); }
	public String getOldScrapValue() { return checkGet(oldScrapValue); }
	public void setOldScrapValue(String oldScrapValue) { this.oldScrapValue = checkSet(oldScrapValue); }
	public String getOldDeprAmount() { return checkGet(oldDeprAmount); }
	public void setOldDeprAmount(String oldDeprAmount) { this.oldDeprAmount = checkSet(oldDeprAmount); }
	public String getOldAccumDepr() { return checkGet(oldAccumDepr); }
	public void setOldAccumDepr(String oldAccumDepr) { this.oldAccumDepr = checkSet(oldAccumDepr); }
	public String getOldApportionMonth() { return checkGet(oldApportionMonth); }
	public void setOldApportionMonth(String oldApportionMonth) { this.oldApportionMonth = checkSet(oldApportionMonth); }
	public String getOldMonthDepr() { return checkGet(oldMonthDepr); }
	public void setOldMonthDepr(String oldMonthDepr) { this.oldMonthDepr = checkSet(oldMonthDepr); }
	public String getNewDeprMethod() { return checkGet(newDeprMethod); }
	public void setNewDeprMethod(String newDeprMethod) { this.newDeprMethod = checkSet(newDeprMethod); }
	public String getNewBuildFeeCB() { return checkGet(newBuildFeeCB); }
	public void setNewBuildFeeCB(String newBuildFeeCB) { this.newBuildFeeCB = checkSet(newBuildFeeCB); }
	public String getNewDeprUnitCB() { return checkGet(newDeprUnitCB); }
	public void setNewDeprUnitCB(String newDeprUnitCB) { this.newDeprUnitCB = checkSet(newDeprUnitCB); }
	public String getNewDeprPark() { return checkGet(newDeprPark); }
	public void setNewDeprPark(String newDeprPark) { this.newDeprPark = checkSet(newDeprPark); }
	public String getNewDeprUnit() { return checkGet(newDeprUnit); }
	public void setNewDeprUnit(String newDeprUnit) { this.newDeprUnit = checkSet(newDeprUnit); }
	public String getNewDeprUnit1() { return checkGet(newDeprUnit1); }
	public void setNewDeprUnit1(String newDeprUnit1) { this.newDeprUnit1 = checkSet(newDeprUnit1); }
	public String getNewDeprAccounts() { return checkGet(newDeprAccounts); }
	public void setNewDeprAccounts(String newDeprAccounts) { this.newDeprAccounts = checkSet(newDeprAccounts); }
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
	
}
