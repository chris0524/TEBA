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

public class UNTCH004F02_VP extends SuperBean2{

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
		map.put("WriteDate", getWriteDate());
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
		map.put("NewEnterOrg", getNewEnterOrg());
		map.put("TransferDate", ut._transToCE_Year(getTransferDate()));
		map.put("ApproveOrg", getApproveOrg());
		map.put("ApproveDate", ut._transToCE_Year(getApproveDate()));
		map.put("ApproveDoc", getApproveDoc());
		map.put("BookNotes", getBookNotes());
		map.put("Verify", getVerify());
		map.put("PropertyKind", getPropertyKind());
		map.put("FundType", getFundType());
		map.put("SecurityName", getSecurityName());
		map.put("BookAmount", getBookAmount());
		map.put("BookValue", getBookValue());
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
		map.put("summonsDate", ut._transToCE_Year(getSummonsDate()));
		map.put("cause2", this.getCause2());
		map.put("returnPlace", this.getReturnPlace());
		map.put("preenterdate", ut._transToCE_Year(this.getPreenterdate()));
		
		return map;
	}

	//傳回執行insert前之檢查sql
	protected String[][] getInsertCheckSQL(){
		String[][] checkSQLArray = new String[3][4];
	 	checkSQLArray[0][0]=" select count(*) as checkResult from UNTVP_REDUCEPROOF where 1=1 " + 
							" and enterorg = " + Common.sqlChar(enterOrg) + 
							" and ownership = " + Common.sqlChar(ownership) +
							" and differencekind = " + Common.sqlChar(differenceKind) +
							" and propertyno = " + Common.sqlChar(propertyNo) + 
							" and serialno = " + Common.sqlChar(serialNo) +  
							"";
		checkSQLArray[0][1]=">";
		checkSQLArray[0][2]="0";
		checkSQLArray[0][3]="該筆資料已重複，請重新輸入！";
		
	 	checkSQLArray[1][0]=" select count(*) as checkResult from UNTVP_ADDPROOF where 1=1 " + 
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
		checkSQLArray[1][3]="有價證券主檔中找不到該財產編號和分號，請重新輸入！";
		
	 	checkSQLArray[2][0]=" select count(*) as checkResult from UNTVP_ADJUSTPROOF where 1=1 " + 
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
		execSQLArray[0] = new SQLCreator()._obtainSQLforInsert("UNTVP_ReduceProof", getPKMap(), getRecordMap());
		return execSQLArray;
	}


	//傳回update sql
	protected String[] getUpdateSQL(){
		String strSQL = ""; 
		strSQL += new SQLCreator()._obtainSQLforUpdate("UNTVP_ReduceProof", getPKMap(), getRecordMap()) +
					":;:";
		
		strSQL += updateTable();
		String[] execSQLArray = strSQL.split(":;:");
		
		return execSQLArray;
	}

	//傳回delete sql
	protected String[] getDeleteSQL(){
		String[] execSQLArray = new String[1];
		execSQLArray[0] = new SQLCreator()._obtainSQLforDelete("UNTVP_ReduceProof", getPKMap(), getRecordMap());
		return execSQLArray;
	}
	
	protected void queryDeleteData(SuperBean2 sb){
		UNTCH_Tools ut = new UNTCH_Tools();
		Map map = queryPropertyNofrom("UNTVP_ReduceProof",sb);					
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
	
	
	public UNTCH004F02_VP  queryOne() throws Exception{
		Database db = new Database();
		UNTCH004F02_VP obj = this;
		try {
			String sql=" select a.*, " +
				" c.organSName as enterOrgName, " +
				" (select d.propertyName from SYSPK_PROPERTYCODE d where a.propertyNo = d.propertyNo ) as propertyNoName, " +
				" (select codename from SYSCA_CODE z where codekindid = 'CAA' and z.codeid = a.cause and codecon1 in (2,4)) as causeName" +
				" from UNTVP_ReduceProof a, SYSCA_ORGAN c "+
				" where 1=1 " +
				" and a.enterOrg    = " + Common.sqlChar(enterOrg) +
				" and a.ownership   = " + Common.sqlChar(ownership) +
				" and a.caseNo 	= " + Common.sqlChar(caseNo) +
				" and a.differenceKind    = " + Common.sqlChar(differenceKind) +
				" and a.propertyNo  = " + Common.sqlChar(propertyNo) +
				" and a.serialNo    = " + Common.sqlChar(serialNo) +			
				" and a.enterOrg	= c.organID " +
				" order by a.enterOrg , a.ownership  "+
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
				obj.setWriteDate(rs.getString("WriteDate"));
				obj.setWriteUnit(rs.getString("WriteUnit"));
				obj.setProofYear(rs.getString("ProofYear"));
				obj.setProofDoc(rs.getString("ProofDoc"));
				obj.setProofNo(rs.getString("ProofNo"));
				obj.setManageNo(rs.getString("ManageNo"));
				obj.setSummonsNo(rs.getString("SummonsNo"));
				obj.setSourceDate(ut._transToROC_Year(rs.getString("SourceDate")));
				obj.setBuyDate(ut._transToROC_Year(rs.getString("BuyDate")));
				obj.setCause(rs.getString("Cause"));
				obj.setCauseName(rs.getString("CauseName"));
				obj.setCause1(rs.getString("Cause1"));
				obj.setAdjustDate(ut._transToROC_Year(rs.getString("AdjustDate")));
				obj.setNewEnterOrg(rs.getString("NewEnterOrg"));
				obj.setTransferDate(ut._transToROC_Year(rs.getString("TransferDate")));
				obj.setApproveOrg(rs.getString("ApproveOrg"));
				obj.setApproveDate(ut._transToROC_Year(rs.getString("ApproveDate")));
				obj.setApproveDoc(rs.getString("ApproveDoc"));
				obj.setBookNotes(rs.getString("BookNotes"));
				obj.setVerify(rs.getString("Verify"));
				obj.setPropertyKind(rs.getString("PropertyKind"));
				obj.setFundType(rs.getString("FundType"));
				obj.setSecurityName(rs.getString("SecurityName"));
				obj.setBookAmount(rs.getString("BookAmount"));
				obj.setBookValue(rs.getString("BookValue"));
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
				obj.setPropertyNoName(rs.getString("PropertyNoName"));
				obj.setEngineeringNoName(ut._getEngineeringNoName(rs.getString("enterOrg"),rs.getString("engineeringNo")));
				obj.setSummonsDate(ut._transToROC_Year(rs.getString("summonsDate")));
				obj.setCause2(rs.getString("cause2"));
				obj.setReturnPlace(rs.getString("returnPlace"));
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
		
		String sql = "select propertyNo, serialno from UNTVP_ReduceProof" +
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
	
	//取得DB中單的入帳狀態
		protected String getDBVerify(){
			String verify = "";
			
			String sql = "select verify from UNTVP_ReduceProof "
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
		
	    String sql="";
	    if(getReVerify().equals("Y")){	
	    	UNTCH_Tools ut = new UNTCH_Tools();		
	    	 sql+= "update UNTVP_ReduceProof set"+
		         	    " verify = " + Common.sqlChar("N") + 	                
						" where 1=1 " + 
						" and enterOrg = " + Common.sqlChar(enterOrg) +
						" and ownership = " + Common.sqlChar(ownership) +
						" and differenceKind = " + Common.sqlChar(differenceKind) +
						" and propertyNo = " + Common.sqlChar(propertyNo) +
						" and serialNo = " + Common.sqlChar(serialNo) +
						":;:";
	    	 
	        sql+= "update UNTVP_AddProof set"+
	        		" bookAmount = " + Common.sqlChar(bookAmount) + ","+
					" bookValue = " + Common.sqlChar(bookValue) + ","+
					" reduceDate = " + Common.sqlChar(reduceDate) + "," +
					" editID = " + Common.sqlChar(getEditID()) + "," +
					" editDate = " + Common.sqlChar(ut._transToCE_Year(getEditDate())) + "," +
					" editTime = " + Common.sqlChar(NewDateUtil.getNow(DateFormat.HHMMSS)) + "," +
	        	    " datastate = " + Common.sqlChar("1") + 	                
					" where 1=1 " + 
					" and enterOrg = " + Common.sqlChar(enterOrg) +
					" and ownership = " + Common.sqlChar(ownership) +
					" and differenceKind = " + Common.sqlChar(differenceKind) +
					" and propertyNo = " + Common.sqlChar(propertyNo) +
					" and serialNo = " + Common.sqlChar(serialNo) +
					":;:";
		
	    }else if("Y".equals(verify) && "N".equals(this.getDBVerify())){
			UNTCH_Tools ut = new UNTCH_Tools();
			sql+= "update UNTVP_ReduceProof set"+
	         	    " verify = " + Common.sqlChar("Y") + 	                
					" where 1=1 " + 
					" and enterOrg = " + Common.sqlChar(enterOrg) +
					" and ownership = " + Common.sqlChar(ownership) +
					" and differenceKind = " + Common.sqlChar(differenceKind) +
					" and propertyNo = " + Common.sqlChar(propertyNo) +
					" and serialNo = " + Common.sqlChar(serialNo) +
					":;:";
			
					  
			sql+= "update UNTVP_AddProof set"+
					" bookAmount = " + Common.sqlChar("0") + ","+
					" bookValue = " + Common.sqlChar("0") + ","+
					" reduceDate = " + Common.sqlChar(ut._transToCE_Year(reduceDate)) + "," +
					" editID = " + Common.sqlChar(getEditID()) + "," +
					" editDate = " + Common.sqlChar(ut._transToCE_Year(getEditDate())) + "," +
					" editTime = " + Common.sqlChar(NewDateUtil.getNow(DateFormat.HHMMSS)) + "," +
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
	private String propertyNoName;
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
	private String causeName;
	private String cause1;
	private String adjustDate;
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
	private String securityName;
	private String bookAmount;
	private String bookValue;
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
	public String getPropertyNoName() {return checkGet(propertyNoName);}
	public void setPropertyNoName(String propertyNoName) {this.propertyNoName = checkSet(propertyNoName);}
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
	public String getCauseName() {return checkGet(causeName);}
	public void setCauseName(String causeName) {this.causeName = checkSet(causeName);}
	public String getCause1() {return checkGet(cause1);}
	public void setCause1(String cause1) {this.cause1 = checkSet(cause1);}
	public String getAdjustDate() {return checkGet(adjustDate);}
	public void setAdjustDate(String adjustDate) {this.adjustDate = checkSet(adjustDate);}
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
	public String getSecurityName() {return checkGet(securityName);}
	public void setSecurityName(String securityName) {this.securityName = checkSet(securityName);}
	public String getBookAmount() {return checkGet(bookAmount);}
	public void setBookAmount(String bookAmount) {this.bookAmount = checkSet(bookAmount);}
	public String getBookValue() {return checkGet(bookValue);}
	public void setBookValue(String bookValue) {this.bookValue = checkSet(bookValue);}
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
	
	/** 報損報廢原因 **/
	public String cause2;
	/** get {@link #cause2} **/
	public String getCause2() { return checkGet(cause2); }
	/** set {@link #cause2} **/
	public void setCause2(String cause2) { this.cause2 = checkSet(cause2); }

	/** 繳存地點 **/
	public String returnPlace;
	/** get {@link #returnPlace} **/
	public String getReturnPlace() { return checkGet(returnPlace); }
	/** set {@link #returnPlace} **/
	public void setReturnPlace(String returnPlace) { this.returnPlace = checkSet(returnPlace); }
	
		
}
