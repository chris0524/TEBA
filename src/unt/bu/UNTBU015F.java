
/*
*<br>程式目的：建物減少作業－減損單資料
*<br>程式代號：untbu015f
*<br>程式日期：0940929
*<br>程式作者：judy
*<br>--------------------------------------------------------
*<br>修改作者　　修改日期　　　修改目的
*<br>--------------------------------------------------------
*/


package unt.bu;

import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Map;

import unt.ch.UNTCH_Tools;
import util.Common;
import util.Database;
import util.MaxClosingYM;
import util.NewDateUtil;
import util.NewDateUtil.DateFormat;
import TDlib_Simple.com.src.SQLCreator;

public class UNTBU015F extends UNTBU015Q{

	private String cause;
	private String cause1;
	private String causeName;
	public String getCause() {return checkGet(cause);}
	public void setCause(String cause) {this.cause = checkSet(cause);}
	public String getCause1() {return checkGet(cause1);}
	public void setCause1(String cause1) {this.cause1 = checkSet(cause1);}
	public String getCauseName() {return checkGet(causeName);}
	public void setCauseName(String causeName) {this.causeName = checkSet(causeName);}
	
	private String enterOrg;
	private String caseNo;
	private String ownership;
	private String differenceKind;
	private String caseName;
	private String writeDate;
	private String writeUnit;
	private String proofYear;
	private String proofDoc;
	private String proofNo;
	private String manageNo;
	private String summonsNo;
	private String unusableCause;
	private String demolishDate;
	private String reduceDate;
	private String approveOrg;
	private String approveDate;
	private String approveDoc;
	private String verify;
	private String notes;
	private String editID;
	private String editDate;
	private String editTime;
	private String enterOrgName;
	private String differenceKindName;
	public String getEnterOrg() {return checkGet(enterOrg);}
	public void setEnterOrg(String enterOrg) {this.enterOrg = checkSet(enterOrg);}
	public String getCaseNo() {return checkGet(caseNo);}
	public void setCaseNo(String caseNo) {this.caseNo = checkSet(caseNo);}
	public String getOwnership() {return checkGet(ownership);}
	public void setOwnership(String ownership) {this.ownership = checkSet(ownership);}
	public String getDifferenceKind() {return checkGet(differenceKind);}
	public void setDifferenceKind(String differenceKind) {this.differenceKind = checkSet(differenceKind);}
	public String getCaseName() {return checkGet(caseName);}
	public void setCaseName(String caseName) {this.caseName = checkSet(caseName);}
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
	public String getUnusableCause() {return checkGet(unusableCause);}
	public void setUnusableCause(String unusableCause) {this.unusableCause = checkSet(unusableCause);}
	public String getDemolishDate() {return checkGet(demolishDate);}
	public void setDemolishDate(String demolishDate) {this.demolishDate = checkSet(demolishDate);}
	public String getReduceDate() {return checkGet(reduceDate);}
	public void setReduceDate(String reduceDate) {this.reduceDate = checkSet(reduceDate);}
	public String getApproveOrg() {return checkGet(approveOrg);}
	public void setApproveOrg(String approveOrg) {this.approveOrg = checkSet(approveOrg);}
	public String getApproveDate() {return checkGet(approveDate);}
	public void setApproveDate(String approveDate) {this.approveDate = checkSet(approveDate);}
	public String getApproveDoc() {return checkGet(approveDoc);}
	public void setApproveDoc(String approveDoc) {this.approveDoc = checkSet(approveDoc);}
	public String getVerify() {return checkGet(verify);}
	public void setVerify(String verify) {this.verify = checkSet(verify);}
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
	public String getDifferenceKindName() {return checkGet(differenceKindName);}
	public void setDifferenceKindName(String differenceKindName) {this.differenceKindName = checkSet(differenceKindName);}

	private String verifyError;
	public String getVerifyError(){ return checkGet(verifyError); }
	public void setVerifyError(String s){ verifyError=checkSet(s); }
	
	private String reVerify;
	public String getReVerify() {return checkGet(reVerify);}
	public void setReVerify(String reVerify) {this.reVerify = checkSet(reVerify);}
	private String summonsDate;
	public String getSummonsDate() {return checkGet(summonsDate);}
	public void setSummonsDate(String summonsDate) {this.summonsDate = checkSet(summonsDate);}
		
	private String preenterdate;
	public String getPreenterdate() { return checkGet(preenterdate); }
	public void setPreenterdate(String preenterdate) { this.preenterdate = checkSet(preenterdate); }
	
	private String addcaseno;
	public String getAddcaseno() { return checkGet(addcaseno); }
	public void setAddcaseno(String addcaseno) { this.addcaseno = checkSet(addcaseno); }
	
	private Map getPKMap(){
		Map map = new HashMap();
		
		map.put("EnterOrg", getEnterOrg());
		map.put("CaseNo", getCaseNo());
		map.put("Ownership", getOwnership());
		// 客戶717 有明細前是可以異動的  不能當PK
		//map.put("DifferenceKind", getDifferenceKind());
		
		return map;
	}
	
	private Map getRecordMap(){
		Map map = new HashMap();
		UNTCH_Tools ut = new UNTCH_Tools();
		
		map.put("EnterOrg", getEnterOrg());
		map.put("CaseNo", getCaseNo());
		map.put("Ownership", getOwnership());
		map.put("DifferenceKind", getDifferenceKind());
		map.put("CaseName", getCaseName());
		map.put("WriteDate", ut._transToCE_Year(getWriteDate()));
		map.put("WriteUnit", getWriteUnit());
		map.put("ProofYear", getProofYear());
		map.put("ProofDoc", getProofDoc());

		//問題單1290補充: update時更改編號-年，重新取得編號-號
		String proofno = getProofNo();
		if(this.checkProofyearChanged(getEnterOrg(), getCaseNo(), getProofYear())){
			proofno = MaxClosingYM.getMaxProofNo("UNTBU_REDUCEPROOF",enterOrg,ownership,this.getProofYear());
		}
		map.put("ProofNo", proofno);
		map.put("ManageNo", getManageNo());
		map.put("SummonsNo", getSummonsNo());
		map.put("UnusableCause", getUnusableCause());
		map.put("DemolishDate", ut._transToCE_Year(getDemolishDate()));
		map.put("ReduceDate", ut._transToCE_Year(getReduceDate()));
		map.put("ApproveOrg", getApproveOrg());
		map.put("ApproveDate", ut._transToCE_Year(getApproveDate()));
		map.put("ApproveDoc", getApproveDoc());
		map.put("Verify", getVerify());
		map.put("Notes", getNotes());
		map.put("EditID", getEditID());
		map.put("EditDate", ut._transToCE_Year(getEditDate()));
		map.put("EditTime", NewDateUtil.getNow(DateFormat.HHMMSS));
		map.put("Cause", getCause());
		map.put("Cause1", getCause1());
		map.put("summonsDate", ut._transToCE_Year(getSummonsDate()));
		map.put("preenterdate", ut._transToCE_Year(this.getPreenterdate()));
		
		return map;
	}

//檢查update時，proofyear是否有改變
private boolean checkProofyearChanged(String enterorg, String caseno, String newproofyear){
	boolean isChanged = false;
	String oldproofyear ="";
	ResultSet rs;
	Database db = new Database();
	try{
		String sql = "select proofyear from UNTBU_REDUCEPROOF where enterorg=" + Common.sqlChar(enterorg) +
								" and caseno=" + Common.sqlChar(caseno);
		rs = db.querySQL(sql);
		if(rs.next()){
			oldproofyear = rs.getString(1);
			if(!newproofyear.equals(oldproofyear)){
				isChanged =true;
			}
		}else{
			System.out.println("查無此單!");
		}
	}catch(Exception e){
		e.printStackTrace();
	}finally{
		db.closeAll();
	}
	return isChanged;
}

//傳回insert sql
protected String[] getInsertSQL(){
	setProofNo(MaxClosingYM.getMaxProofNo("UNTBU_ReduceProof",enterOrg,ownership,this.getProofYear()));
	String[] execSQLArray = new String[1];
	execSQLArray[0] = new SQLCreator()._obtainSQLforInsert("UNTBU_ReduceProof", getPKMap(), getRecordMap());
	return execSQLArray;
}


protected String[] getUpdateSQL(){
	if(getReVerify().equals("Y")){	setVerify("N");}
	
	String[] execSQLArray = new String[2];
	execSQLArray[0] = new SQLCreator()._obtainSQLforUpdate("UNTBU_ReduceProof", getPKMap(), getRecordMap());
	execSQLArray[1] = updateTable();
	return execSQLArray;
}

//取得DB中單的入帳狀態
protected String getDBVerify(){
	String verify = "";
	
	String sql = "select verify from UNTBU_ReduceProof "
							+ "where 1=1 "
							+ "and enterorg= '" +this.getEnterOrg() + "' "
							+ "and caseno= '" +this.getCaseNo() + "' "
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
	
	private String updateTable(){
		String sql = "";
		String querySQL = "";
		
		UNTCH_Tools ut = new UNTCH_Tools();
		if(getReVerify().equals("Y")){
			sql +=   " update UNTBU_ReduceDETAIL set"+
			        " verify = " + Common.sqlChar("N") + "," +
			        " reduceDate = " + Common.sqlChar(ut._transToCE_Year(getReduceDate())) + "," +
					" editID = " + Common.sqlChar(getEditID()) + "," +
					" editDate = " + Common.sqlChar(ut._transToCE_Year(getEditDate())) + "," +
					" editTime = " + Common.sqlChar(getEditTime()) + 	                
					" where 1=1 " + 
					" and enterOrg = " + Common.sqlChar(enterOrg) +
					" and ownership = " + Common.sqlChar(ownership) +
					" and caseNo = " + Common.sqlChar(caseNo) +
					" and differenceKind = " + Common.sqlChar(differenceKind) +
					""; 
			
			querySQL = "select propertyno, serialno," +
							" carea, sarea, holdnume, holddeno, holdarea," +
							" BookValue, NetValue" +
						" from UNTBU_ReduceDETAIL " +
						" where 1=1" +
							" and enterOrg = " + Common.sqlChar(enterOrg) +
							" and ownership = " + Common.sqlChar(ownership) +
							" and caseNo = " + Common.sqlChar(caseNo) +
							" and differenceKind = " + Common.sqlChar(differenceKind);
			Database db = null;
			ResultSet rs = null;
			try{
				db = new Database();
				rs = db.querySQL(querySQL);
				while(rs.next()){
					sql += " update UNTBU_BUILDING set" +
				        	  " Carea = " + Common.sqlChar(rs.getString("Carea")) + "," +
				        	  " Sarea = " + Common.sqlChar(rs.getString("Sarea")) + "," +
				        	  " holdNume = " + Common.sqlChar(rs.getString("holdnume")) + "," +
				        	  " holdDeno = " + Common.sqlChar(rs.getString("holddeno")) + "," +
				        	  " holdArea = " + Common.sqlChar(rs.getString("holdarea")) + "," +
				        	  " bookValue = " + Common.sqlChar(rs.getString("BookValue")) + "," +
				        	  " netValue = " + Common.sqlChar(rs.getString("NetValue")) + "," +
				        	  " editID = " + Common.sqlChar(getEditID()) + "," +
				        	  " editDate = " + Common.sqlChar(ut._transToCE_Year(getEditDate())) + "," +
				        	  " editTime = " + Common.sqlChar(getEditTime()) + "," +
						      " datastate = " + Common.sqlChar("1") + "," + 
						      " reducedate = NULL " +
				        	  " where 1=1 " + 
				        	  " and enterOrg = " + Common.sqlChar(enterOrg) +
				        	  " and ownership = " + Common.sqlChar(ownership) +
				        	  " and differenceKind = " + Common.sqlChar(differenceKind) +
				        	  " and propertyno = " + Common.sqlChar(rs.getString("propertyNo")) +
				        	  " and serialno = " + Common.sqlChar(rs.getString("serialNo")) +
				        	  "";
				}
			}catch(Exception e){
				e.printStackTrace();
			}finally{
				db.closeAll();
			}
		}else if("Y".equals(verify) && "N".equals(this.getDBVerify())){
			sql +=   " update UNTBU_ReduceDetail set"+
			        " verify = " + Common.sqlChar(getVerify()) + "," +
			        " reduceDate = " + Common.sqlChar(ut._transToCE_Year(getReduceDate())) + "," +
					" editID = " + Common.sqlChar(getEditID()) + "," +
					" editDate = " + Common.sqlChar(ut._transToCE_Year(getEditDate())) + "," +
					" editTime = " + Common.sqlChar(getEditTime()) + 	                
					" where 1=1 " + 
					" and enterOrg = " + Common.sqlChar(enterOrg) +
					" and ownership = " + Common.sqlChar(ownership) +
					" and caseNo = " + Common.sqlChar(caseNo) +
					" and differenceKind = " + Common.sqlChar(differenceKind) +
					""; 
			
			querySQL = "select propertyno, serialno" +							
						" from UNTBU_ReduceDETAIL " +
						" where 1=1" +
							" and enterOrg = " + Common.sqlChar(enterOrg) +
							" and ownership = " + Common.sqlChar(ownership) +
							" and caseNo = " + Common.sqlChar(caseNo) +
							" and differenceKind = " + Common.sqlChar(differenceKind);
			
			Database db = null;
			ResultSet rs = null;
			try{
				db = new Database();
				rs = db.querySQL(querySQL);
				while(rs.next()){
					sql += " update UNTBU_BUILDING set" +
							  " Carea = " + Common.sqlChar("0") + "," +
				        	  " Sarea = " + Common.sqlChar("0") + "," +
				        	  " holdNume = " + Common.sqlChar("0") + "," +
				        	  " holdDeno = " + Common.sqlChar("0") + "," +
				        	  " holdArea = " + Common.sqlChar("0") + "," +
				        	  " bookValue = " + Common.sqlChar("0") + "," +
				        	  " netValue = " + Common.sqlChar("0") + "," +
				        	  " reduceDate = " + Common.sqlChar(ut._transToCE_Year(getReduceDate())) + "," +
				        	  " editID = " + Common.sqlChar(getEditID()) + "," +
				        	  " editDate = " + Common.sqlChar(ut._transToCE_Year(getEditDate())) + "," +
				        	  " editTime = " + Common.sqlChar(getEditTime()) + "," +
						      " datastate = " + Common.sqlChar("2") + 	     	 	                
				        	  " where 1=1 " + 
				        	  " and enterOrg = " + Common.sqlChar(enterOrg) +
				        	  " and ownership = " + Common.sqlChar(ownership) +
				        	  " and differenceKind = " + Common.sqlChar(differenceKind) +
				        	  " and propertyno = " + Common.sqlChar(rs.getString("propertyNo")) +
				        	  " and serialno = " + Common.sqlChar(rs.getString("serialNo")) +
				        	  "";
				}
			}catch(Exception e){
				e.printStackTrace();
			}finally{
				db.closeAll();
			}  
			
		}
		
		return sql;
	}

//傳回delete sql
protected String[] getDeleteSQL(){ 
		String[] execSQLArray = new String[2];
		//刪除 UNTBU_ReduceProof TABLE
		execSQLArray[0] = new SQLCreator()._obtainSQLforDelete("UNTBU_ReduceProof", getPKMap(), getRecordMap());
				
		//刪除 UNTBU_ReduceDetail TABLE
		execSQLArray[1]=" delete UNTBU_ReduceDetail where 1=1 " +
				" and enterOrg = " + Common.sqlChar(enterOrg) +
				" and ownership = " + Common.sqlChar(ownership) +
				" and caseNo = " + Common.sqlChar(caseNo) +
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

public UNTBU015F  queryOne() throws Exception{   
	Database db = new Database();
	UNTCH_Tools ut = new UNTCH_Tools();
	UNTBU015F obj = this;	
	try {    
		String sql=" select a.enterOrg, b.organSName, a.ownership, a.caseNo, a.caseName, a.writeDate, a.writeUnit, a.proofDoc, a.proofNo, a.manageNo, a.summonsNo,a.unusableCause,a.demolishDate, a.reduceDate, a.approveOrg, a.approveDate, a.approveDoc, a.verify, a.notes, a.editID, a.editDate, a.editTime, a.editID, a.editDate, a.editTime, a.closing, a.summonsDate, a.cause, " +
			" (select codename from SYSCA_CODE z where z.codekindid = 'CAA' and z.codecon1 in (2,4) and z.codeid = a.cause) as causeName "+
			" from UNTBU_ReduceProof a, SYSCA_Organ b where 1=1 " +
			" and a.enterOrg = " + Common.sqlChar(enterOrg) +
			" and a.ownership = " + Common.sqlChar(ownership) +
			" and a.caseNo = " + Common.sqlChar(caseNo) +
			" and a.differenceKind = " + Common.sqlChar(differenceKind) +
			" and b.organID = a.enterOrg" +
			" order by a.enterOrg, a.ownership, a.caseNo ";
		ResultSet rs = db.querySQL(sql);
		if (rs.next()){
			obj.setEnterOrg(rs.getString("EnterOrg"));
			obj.setCaseNo(rs.getString("CaseNo"));
			obj.setOwnership(rs.getString("Ownership"));
			obj.setDifferenceKind(rs.getString("DifferenceKind"));
			obj.setCaseName(rs.getString("CaseName"));
			obj.setWriteDate(ut._transToROC_Year(rs.getString("WriteDate")));
			obj.setWriteUnit(rs.getString("WriteUnit"));
			obj.setProofYear(rs.getString("ProofYear"));
			obj.setProofDoc(rs.getString("ProofDoc"));
			obj.setProofNo(rs.getString("ProofNo"));
			obj.setManageNo(rs.getString("ManageNo"));
			obj.setSummonsNo(rs.getString("SummonsNo"));
			obj.setUnusableCause(rs.getString("UnusableCause"));
			obj.setDemolishDate(ut._transToROC_Year(rs.getString("DemolishDate")));
			obj.setReduceDate(ut._transToROC_Year(rs.getString("ReduceDate")));
			obj.setApproveOrg(rs.getString("ApproveOrg"));
			obj.setApproveDate(ut._transToROC_Year(rs.getString("ApproveDate")));
			obj.setApproveDoc(rs.getString("ApproveDoc"));
			obj.setVerify(rs.getString("Verify"));
			obj.setNotes(rs.getString("Notes"));
			obj.setEditID(rs.getString("EditID"));
			obj.setEditDate(ut._transToROC_Year(rs.getString("EditDate")));
			obj.setEditTime(rs.getString("EditTime"));
			obj.setCause(rs.getString("cause"));
			obj.setCause1(rs.getString("cause1"));
			obj.setCauseName(rs.getString("causeName"));
			obj.setEnterOrgName(rs.getString("organSName"));
			obj.setSummonsDate(ut._transToROC_Year(rs.getString("summonsDate")));
			obj.setAddcaseno(rs.getString("addcaseno"));
		} 
		setStateQueryOneSuccess();
	} catch (Exception e) {
		e.printStackTrace();
	} finally {
		db.closeAll();
	}
	return obj;
}




//審核設定
protected String checkVerify(){
    String sql="";
	//1.check是否可以更改
	//1.1減損日期之年月不可小於等於最大的月結年月
//	if(Integer.parseInt(getReduceDate().substring(0,5)) > Integer.parseInt(getMaxClosingYM())){
//		//更改 UNTBU_ReduceDetail 檔案資料
//		sql = "update UNTBU_ReduceDetail set"+
//	        " verify = 'Y'," +
//	        " reduceDate = " + Common.sqlChar(getReduceDate()) + "," +
//			" editID = " + Common.sqlChar(getEditID()) + "," +
//			" editDate = " + Common.sqlChar(getEditDate()) + "," +
//			" editTime = " + Common.sqlChar(getEditTime()) + 	                
//			" where 1=1 " + 
//			" and enterOrg = " + Common.sqlChar(enterOrg) +
//			" and ownership = " + Common.sqlChar(ownership) +
//			" and caseNo = " + Common.sqlChar(caseNo) +
//			":;:"; 
//	}else{
//	    setVerifyError("Y");    	   
//		setStateUpdateError();
//        setErrorMsg("入帳設定有錯，減損日期之年月必須大於月結年月！");
//	}
	return sql;
}

//回復審核設定
public void approveRe()throws Exception{	
	Database db = new Database();
	String enterOrgQuery = "";
	String ownershipQuery = "";
	String caseNoQuery = "";
	String propertyNoQuery = "";
	String serialNoQuery = "";
	String reduceSql = "",reduceCount = "";
	String adjustSql = "",adjustCount = "";
	String reduceOriginal = "",reduceMax = "",reduceMaxSql = "";
	String adjustMax = "",adjustMaxSql = "";
	int count = 0;
	try {
		String[] execSQLArray;
		String strSQL = "";
		String sql ="select a.caseNo, b.enterOrg, b.ownership, b.propertyNo, b.serialNo, b.holdNume, b.holdArea, b.bookValue," +
					" a.editDate, a.editTime, a.reduceDate" +
					" from untbu_reduceProof a, untbu_reduceDetail b " +
					" where 1=1 " +
					" and a.enterOrg = b.enterOrg and a.ownership = b.ownership and a.caseNo = b.caseNo" +
	    			" and a.enterOrg = " + Common.sqlChar(enterOrg) +
	    			" and a.ownership = " + Common.sqlChar(ownership) +
	    			" and a.caseNo = " + Common.sqlChar(caseNo) +
					" order by b.enterOrg, b.ownership, b.propertyNo, b.serialNo" +
					"" ;
		
		ResultSet rs = db.querySQL(sql);
		while (rs.next()){
			count++;
			enterOrgQuery = rs.getString("enterOrg");
			ownershipQuery = rs.getString("ownership");
			caseNoQuery = rs.getString("caseNo");
			propertyNoQuery = rs.getString("propertyNo");
			serialNoQuery = rs.getString("serialNo");
			//該減損單之明細資料,存在未審核的「土地減損單明細檔UNTMP_ReduceDetail」資料，則提示使用者
			reduceSql = "select count(*) count from untbu_reduceDetail " +
						" where enterOrg="+Common.sqlChar(enterOrgQuery)+" and ownership="+Common.sqlChar(ownershipQuery)+" and propertyNo="+Common.sqlChar(propertyNoQuery)+" and serialNo="+Common.sqlChar(serialNoQuery)+" and verify='N'";
			reduceCount = MaxClosingYM.getCountValues(reduceSql);
			
			//該減損單之明細資料,存在未審核的「土地增減值單明細檔UNTMP_AdjustDetail」資料，則提示使用者
			adjustSql = "select count(*) count from untbu_adjustDetail " +
						" where enterOrg="+Common.sqlChar(enterOrgQuery)+" and ownership="+Common.sqlChar(ownershipQuery)+" and propertyNo="+Common.sqlChar(propertyNoQuery)+" and serialNo="+Common.sqlChar(serialNoQuery)+" and verify='N'";
			adjustCount = MaxClosingYM.getCountValues(adjustSql);
			
			//------------------------------------------------------------------------------------------------
			reduceOriginal = rs.getString("editDate")+rs.getString("editTime")+rs.getString("reduceDate");
			//該減損單之(異動日期+異動時間+減損日期)≦減損單明細資料之其他減損單之最大的(異動日期+異動時間+減損日期)則提示使用者
			reduceMaxSql = "select count(*) count from untbu_reduceDetail " +
							" where enterOrg="+Common.sqlChar(enterOrgQuery)+" and ownership="+Common.sqlChar(ownershipQuery)+" and propertyNo="+Common.sqlChar(propertyNoQuery)+" and serialNo="+Common.sqlChar(serialNoQuery)+" and verify='Y' " +
							" and '" + reduceOriginal + "'<=(editDate || editTime || reduceDate) " +
							" and caseNo!="+Common.sqlChar(caseNoQuery);
			reduceMax = MaxClosingYM.getCountValues(reduceMaxSql);
			
			//該減損單之(異動日期+異動時間+減損日期)≦減損單明細資料之其他增減值單之最大的(異動日期+異動時間+減損日期)則提示使用者
			adjustMaxSql = "select count(*) count from untbu_adjustDetail " +
							" where enterOrg="+Common.sqlChar(enterOrgQuery)+" and ownership="+Common.sqlChar(ownershipQuery)+" and propertyNo="+Common.sqlChar(propertyNoQuery)+" and serialNo="+Common.sqlChar(serialNoQuery)+" and verify='Y' " +
							" and '" + reduceOriginal + "'<=(editDate || editTime || adjustDate) " ;
			adjustMax = MaxClosingYM.getCountValues(adjustMaxSql);
			
			//------------------------------------------------------------------------------------------------			
			if(verify.equals("Y") && reduceCount.equals("0") && adjustCount.equals("0") && reduceMax.equals("0") && adjustMax.equals("0")){				
				//依據該減損單「入帳機關enterOrg＋權屬ownership＋電腦單號caseNo」設定
				strSQL += "update UNTBU_ReduceProof set verify ='N' " +
						" where enterOrg=" + Common.sqlChar(enterOrgQuery) +
						" and ownership=" + Common.sqlChar(ownershipQuery) +
						" and caseNo=" + Common.sqlChar(caseNoQuery) +
						":;:";				
				strSQL += "update UNTBU_ReduceDetail set verify ='N' " +
						" where enterOrg=" + Common.sqlChar(enterOrgQuery) +
						" and ownership=" + Common.sqlChar(ownershipQuery) +
						" and caseNo=" + Common.sqlChar(caseNoQuery) +
						":;:";				
			
				//依據減損單明細「入帳機關enterOrg＋權屬ownership＋財產編號propertyNo＋財產分號serialNo」設定	
					strSQL += "update UNTBU_Building set" +
							" dataState='1'," +
							" reduceDate=null," +
							" reduceCause=null," +
							" reduceCause1=null," +
							" holdNume=" + Common.sqlChar(rs.getString("holdNume")) +","+
							" holdArea=" + Common.sqlChar(rs.getString("holdArea")) +","+
							" bookValue=" + Common.sqlChar(rs.getString("bookValue")) +
							" where enterOrg=" + Common.sqlChar(enterOrgQuery) +
							" and ownership=" + Common.sqlChar(ownershipQuery) +
							" and propertyNo=" + Common.sqlChar(propertyNoQuery) +
							" and serialNo=" + Common.sqlChar(serialNoQuery) +
							":;:";
								
				//----------------------------------------
			}else{
				setVerifyError("Y");
				if(verify.equals("N")){
					setErrorMsg("尚未入帳，請直接修改資料即可！");
				}else if(!reduceCount.equals("0")){
					setErrorMsg("減損作業存在未入帳的資料，無法回復入帳！");
				}else if(!adjustCount.equals("0")){
					setErrorMsg("增減值作業存在未入帳的資料，無法回復入帳！");
				}
/*				else if(!reduceMax.equals("0")){
					setErrorMsg("並非最後一筆審核入帳(減損)的資料，無法回復審核入帳！");
				}else if(!adjustMax.equals("0")){
					setErrorMsg("並非最後一筆審核入帳(增減值)的資料，無法回復審核入帳！");
				}
*/				
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

//異動 UNTBU_Building 檔案
protected String updLand(){
	String sqlLand="";
	String sql="";
	String checkDetail="";
	Database db = new Database();
//	更改 UNTBU_Building 檔案資料
	try {
	    sql="select a.cause, a.cause1, a.propertyNo, a.serialNo " +
	    	"from UNTBU_ReduceDetail a where 1=1 " +
			" and enterOrg = " + Common.sqlChar(enterOrg) +
			" and ownership = " + Common.sqlChar(ownership) +
			" and caseNo = " + Common.sqlChar(caseNo) +
	    	"";
		    ResultSet rs = db.querySQL(sql);
			while (rs.next()){
			    checkDetail="Y";
				sqlLand += " update UNTBU_Building set "+
					    " dataState = '2'," +
					    " reduceDate = " + Common.sqlChar(getReduceDate()) + "," +
						" reduceCause = " + Common.sqlChar(rs.getString("cause")) + "," +
						" reduceCause1 = " + Common.sqlChar(rs.getString("cause1")) + "," +
						" holdNume = 0 , " +
						" holdArea = 0 , " +
						" bookValue = 0  " +
						" where 1=1 " + 
						" and enterOrg = " + Common.sqlChar(enterOrg) +
						" and ownership = " + Common.sqlChar(ownership) +
						" and propertyNo = " + Common.sqlChar(rs.getString("propertyNo")) +
						" and serialNo = " + Common.sqlChar(rs.getString("serialNo")) +
						":;:"; 
			}
	        if(!checkDetail.equals("Y")){
	            setVerifyError("Y");
	            setErrorMsg("該筆減損單之明細資料標籤要有資料才能做此入帳設定！");
	        }
	} catch (Exception e) {
		e.printStackTrace();
	} finally {
		db.closeAll();
	}

	return sqlLand;
}

protected String updDetail(){
    String sql="";	
    //更改 UNTBU_ReduceDetail 檔案資料
    sql = "update UNTBU_ReduceDetail set"+
	        " reduceDate = " + Common.sqlChar(getReduceDate()) +
			" where 1=1 " + 
			" and enterOrg = " + Common.sqlChar(enterOrg) +
			" and ownership = " + Common.sqlChar(ownership) +
			" and caseNo = " + Common.sqlChar(caseNo) +
			":;:"; 
	return sql;
}


}
