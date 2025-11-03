/*
*<br>程式目的：動產減少作業－減損單資料
*<br>程式代號：untmp014f
*<br>程式日期：0941017
*<br>程式作者：judy
*<br>--------------------------------------------------------
*<br>修改作者　　修改日期　　　修改目的
*<br>--------------------------------------------------------
*/
package unt.mp;

import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

import unt.ch.UNTCH_Tools;
import util.Common;
import util.Database;
import util.MaxClosingYM;
import util.NewDateUtil;
import util.NewDateUtil.DateFormat;
import TDlib_Simple.com.src.DBServerTools;
import TDlib_Simple.com.src.SQLCreator;

public class UNTMP014F extends UNTMP014Q{

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
	private String reduceDate;
	private String approveOrg;
	private String approveDate;
	private String approveDoc;
	private String verify;
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
	
	private String verifyError;
	public String getVerifyError(){ return checkGet(verifyError); }
	public void setVerifyError(String s){ verifyError=checkSet(s); }

	String checkEnterOrg;
	String checkOldEnterOrg;
	String checkOwnership;
	String checkOldOwnership;
	String checkPropertyNo;
	String checkOldPropertyNo;
	String checkLotNo;
	String checkOldLotNo;
	
	public String getCheckEnterOrg(){ return checkGet(checkEnterOrg); }
	public void setCheckEnterOrg(String s){ checkEnterOrg=checkSet(s); }
	public String getCheckOldEnterOrg(){ return checkGet(checkOldEnterOrg); }
	public void setCheckOldEnterOrg(String s){ checkOldEnterOrg=checkSet(s); }
	public String getCheckOwnership(){ return checkGet(checkOwnership); }
	public void setCheckOwnership(String s){ checkOwnership=checkSet(s); }
	public String getCheckOldOwnership(){ return checkGet(checkOldOwnership); }
	public void setCheckOldOwnership(String s){ checkOldOwnership=checkSet(s); }
	public String getCheckPropertyNo(){ return checkGet(checkPropertyNo); }
	public void setCheckPropertyNo(String s){ checkPropertyNo=checkSet(s); }
	public String getCheckOldPropertyNo(){ return checkGet(checkOldPropertyNo); }
	public void setCheckOldPropertyNo(String s){ checkOldPropertyNo=checkSet(s); }
	public String getCheckLotNo(){ return checkGet(checkLotNo); }
	public void setCheckLotNo(String s){ checkLotNo=checkSet(s); }
	public String getCheckOldLotNo(){ return checkGet(checkOldLotNo); }
	public void setCheckOldLotNo(String s){ checkOldLotNo=checkSet(s); }

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
	
	int sumBookValue ;
	int sumBookAmount ;
	Hashtable h = new Hashtable();
	String changeNo="";
	
	String checkFlag;
	public String getCheckFlag() {return checkGet(checkFlag);}
	public void setCheckFlag(String checkFlag) {this.checkFlag = checkSet(checkFlag);}

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
			proofno = MaxClosingYM.getMaxProofNo("UNTMP_REDUCEPROOF",enterOrg,ownership,this.getProofYear());
		}
		map.put("ProofNo", proofno);
		map.put("ManageNo", getManageNo());
		map.put("SummonsNo", getSummonsNo());
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
		String sql = "select proofyear from UNTMP_REDUCEPROOF where enterorg=" + Common.sqlChar(enterorg) +
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
	//取得增減值單編號－號
	setProofNo(MaxClosingYM.getMaxProofNo("UNTMP_ReduceProof",enterOrg,ownership,this.getProofYear()));
	//===================
	String[] execSQLArray = new String[1];
	execSQLArray[0] = new SQLCreator()._obtainSQLforInsert("UNTMP_ReduceProof", getPKMap(), getRecordMap());
	return execSQLArray;
}

protected String[] getUpdateSQL(){
	if(getReVerify().equals("Y")){	setVerify("N");}
	
	String sql = "";
	sql += new SQLCreator()._obtainSQLforUpdate("UNTMP_ReduceProof", getPKMap(), getRecordMap()) +
			":;:";
	sql += updateTable();
	
	String[] execSQLArray = sql.split(":;:");
	
	
	return execSQLArray;
}

//取得DB中單的入帳狀態
protected String getDBVerify(){
	String verify = "";
	
	String sql = "select verify from UNTMP_ReduceProof "
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
			
			sql = " update UNTMP_ReduceDetail set" +
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
			    	  ":;:";
			
//			querySQL = "select propertyno, serialno, lotNo," +
//							" oldBookValue, oldNetValue, adjustBookAmount, adjustBookValue" +
//						" from UNTMP_ReduceDetail " +
//						" where 1=1" +
//							" and enterOrg = " + Common.sqlChar(enterOrg) +
//							" and ownership = " + Common.sqlChar(ownership) +
//							" and caseNo = " + Common.sqlChar(caseNo) +
//							" and differenceKind = " + Common.sqlChar(differenceKind);
//			
//			Database db = null;
//			ResultSet rs = null;
//			try{
//				db = new Database();
//				rs = db.querySQL(querySQL);
//				while(rs.next()){
//					sql += " update UNTMP_MOVABLEDETAIL set" +
//				        	  " bookValue = " + Common.sqlChar(rs.getString("oldBookValue")) + "," +
//				        	  " netValue = " + Common.sqlChar(rs.getString("oldNetValue")) + "," +
//				        	  " editID = " + Common.sqlChar(getEditID()) + "," +
//				        	  " editDate = " + Common.sqlChar(getEditDate()) + "," +
//				        	  " editTime = " + Common.sqlChar(getEditTime()) + "," +
//				        	  " datastate = " + Common.sqlChar("1") +
//				        	  " where 1=1 " + 
//				        	  " and enterOrg = " + Common.sqlChar(enterOrg) +
//				        	  " and ownership = " + Common.sqlChar(ownership) +
////				        	  " and caseNo = " + Common.sqlChar(caseNo) +
//				        	  " and differenceKind = " + Common.sqlChar(differenceKind) +
//				        	  " and propertyno = " + Common.sqlChar(rs.getString("propertyNo")) +
//				        	  " and serialno = " + Common.sqlChar(rs.getString("serialNo")) +
//				        	  ":;:";
//					
//					sql += "update UNTMP_MOVABLE set" +
////							" datastate = '1'," +
//							" bookamount = bookamount + " + rs.getString("adjustbookamount") + "," +
//							" bookvalue = bookvalue + " + rs.getString("adjustbookvalue") +
//							" where 1=1 " + 
//							" and enterorg = " + Common.sqlChar(enterOrg) +
//							" and ownership = " + Common.sqlChar(ownership) +
//							" and differencekind = " + Common.sqlChar(differenceKind) +
//							" and lotno = " + Common.sqlChar(rs.getString("lotNo")) +
//							" and propertyno = " + Common.sqlChar(rs.getString("propertyNo")) +
//							":;:";
//				}
//
//				
//			}catch(Exception e){
//				e.printStackTrace();
//			}finally{
//				db.closeAll();
//			}	
		}else if("Y".equals(verify) && "N".equals(this.getDBVerify())){
			sql = " update UNTMP_ReduceDetail set" +
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
			    	  ":;:";  
				
//			querySQL = "select propertyno, serialno, lotNo," +
//						" newbookvalue, newnetvalue, adjustBookAmount, adjustBookValue" +						
//						" from UNTMP_ReduceDetail " +
//						" where 1=1" +
//							" and enterOrg = " + Common.sqlChar(enterOrg) +
//							" and ownership = " + Common.sqlChar(ownership) +
//							" and caseNo = " + Common.sqlChar(caseNo) +
//							" and differenceKind = " + Common.sqlChar(differenceKind);
//			
//			Database db = null;
//			ResultSet rs = null;
//			try{
//				db = new Database();
//				rs = db.querySQL(querySQL);
//				while(rs.next()){
//					sql += " update UNTMP_MOVABLEDETAIL set" +
//				        	  " bookValue = " + Common.sqlChar(rs.getString("newbookvalue")) + "," +
//				        	  " netValue = " + Common.sqlChar(rs.getString("newnetvalue")) + "," +
//				        	  " editID = " + Common.sqlChar(getEditID()) + "," +
//				        	  " editDate = " + Common.sqlChar(getEditDate()) + "," +
//				        	  " editTime = " + Common.sqlChar(getEditTime()) + "," +
//				        	  " datastate = " + Common.sqlChar(("Y".equals(getVerify())?"2":"1")) + 	                
//				        	  " where 1=1 " + 
//				        	  " and enterOrg = " + Common.sqlChar(enterOrg) +
//				        	  " and ownership = " + Common.sqlChar(ownership) +
////				        	  " and caseNo = " + Common.sqlChar(caseNo) +
//				        	  " and differenceKind = " + Common.sqlChar(differenceKind) +
//				        	  " and propertyno = " + Common.sqlChar(rs.getString("propertyNo")) +
//				        	  " and serialno = " + Common.sqlChar(rs.getString("serialNo")) +
//				        	  ":;:";
//					
//
////					sql += "update UNTMP_MOVABLE set" +
////							" datastate = case bookamount when 1 then '2' else '1' end," +
////							" bookamount = bookamount - " + rs.getString("adjustbookamount") + "," +
////							" bookvalue = bookvalue - " + rs.getString("adjustbookvalue") +
////							" where 1=1 " + 
////							" and enterorg = " + Common.sqlChar(enterOrg) +
////							" and ownership = " + Common.sqlChar(ownership) +
////							" and differencekind = " + Common.sqlChar(differenceKind) +
////							" and lotno = " + Common.sqlChar(rs.getString("lotNo")) +
////							" and propertyno = " + Common.sqlChar(rs.getString("propertyNo")) +
////							":;:";
//					
//				}
//				
//			}catch(Exception e){
//				e.printStackTrace();
//			}finally{
//				db.closeAll();
//			}  
			
		}
		
		return sql;
	}
	
	public void updateMOVABLE(String enterOrg, String ownership, String caseNo, String differenceKind, String type){
		if("updateError".equals(this.getState())){
			
		}else{
			Database db = new Database();
			try{
				// 1040626 UNTMP_REDUCEDETAIL 的 lotno 與 動產主檔&明細的lotno 對應不起來
				String querySQL = "select ("
						+ "select lotno from UNTMP_MOVABLEDETAIL z where z.enterorg = a.enterorg and z.ownership = a.ownership and z.differencekind = a.differencekind and z.propertyno = a.propertyno and z.serialno = a.serialno) as lotno, "
						+ "propertyno, serialno, adjustbookunit, adjustbookvalue, "
						+ "(select sum(case datastate when '2' then 0 else 1 end) from UNTMP_MOVABLEDETAIL z where z.enterorg = a.enterorg and z.ownership = a.ownership and z.differencekind = a.differencekind and z.propertyno = a.propertyno and z.lotno = a.lotno) as newbookamount "
						+ "from UNTMP_REDUCEDETAIL a " +
						" where 1=1" +
							" and enterOrg = " + Common.sqlChar(enterOrg) +
							" and ownership = " + Common.sqlChar(ownership) +
							" and caseNo = " + Common.sqlChar(caseNo) +
							" and differenceKind = " + Common.sqlChar(differenceKind);
	
				String lotNo = "";
				String propertyNo = "";
				String serialNo = "";
				String adjustBookUnit = "";
				String adjustBookValue = "";
				Double newBookAmount = 0.00;
				ResultSet rs = db.querySQL(querySQL);
				while(rs.next()){
					lotNo = rs.getString("lotno");
					propertyNo = rs.getString("propertyno");
					serialNo = rs.getString("serialno");
					newBookAmount = rs.getDouble("newbookamount");
					adjustBookUnit = rs.getString("adjustbookunit");
					adjustBookValue = rs.getString("adjustbookValue");
					querySQL = "update UNTMP_MOVABLE set" ;
					if("update".equals(type)){
						querySQL +=" bookamount = (select sum(a.bookamount) - 1"+" from UNTMP_MOVABLE a where 1=1";
					}else{
						querySQL +=" bookamount = (select sum(a.bookamount) + 1"+" from UNTMP_MOVABLE a where 1=1";
					}
					querySQL +=" and a.enterorg = " + Common.sqlChar(enterOrg) +
							   " and a.ownership = " + Common.sqlChar(ownership) +
							   " and a.propertyno = " + Common.sqlChar(propertyNo) +
							   " and a.differencekind = " + Common.sqlChar(differenceKind) +
							   " and a.lotno = " + Common.sqlChar(lotNo)+
							   ")," ;
					if("update".equals(type)){
						querySQL +=" bookvalue = (select sum(a.bookvalue) - ("+adjustBookValue+") from UNTMP_MOVABLE a where 1=1";
					}else{
						querySQL +=" bookvalue = (select sum(a.bookvalue) + ("+adjustBookValue+") from UNTMP_MOVABLE a where 1=1";
					}
					querySQL +=" and a.enterorg = " + Common.sqlChar(enterOrg) +
							   " and a.ownership = " + Common.sqlChar(ownership) +
							   " and a.propertyno = " + Common.sqlChar(propertyNo) +
							   " and a.differencekind = " + Common.sqlChar(differenceKind) +
							   " and a.lotno = " + Common.sqlChar(lotNo)+
							   ")," ;
					if("update".equals(type)){
						querySQL += " netvalue = (select sum(a.netvalue) - ("+adjustBookValue+") from UNTMP_MOVABLE a where 1=1" ;
					} else {
						querySQL += " netvalue = (select sum(a.netvalue) + ("+adjustBookValue+") from UNTMP_MOVABLE a where 1=1" ;
					}
					querySQL += " and a.enterorg = " + Common.sqlChar(enterOrg) +
								" and a.ownership = " + Common.sqlChar(ownership) +
								" and a.propertyno = " + Common.sqlChar(propertyNo) +
								" and a.differencekind = " + Common.sqlChar(differenceKind) +
								" and a.lotno = " + Common.sqlChar(lotNo) +
								")" ;
				if(newBookAmount == 0.00 && "update".equals(type)){				
					querySQL += " ,datastate = '2' ";
				}else{
					querySQL += " ,datastate = '1' ";
				}
				querySQL += " where 1=1 " + 
							" and enterorg = " + Common.sqlChar(enterOrg) +
							" and ownership = " + Common.sqlChar(ownership) +
							" and propertyno = " + Common.sqlChar(propertyNo) +
							" and differencekind = " + Common.sqlChar(differenceKind) +
							" and lotno = " + Common.sqlChar(lotNo);
			
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
	
	public void updateMOVABLEDETAIL(String enterOrg, String ownership, String caseNo, String differenceKind, String type){
		if("updateError".equals(this.getState())){
			
		}else{
			Database db = new Database();
			try{
				String querySQL = "select b.lotno, b.propertyno, b.serialno, b.adjustbookamount, b.adjustbookvalue, b.adjustnetvalue, b.newbookamount, b.cause, b.cause1, a.reducedate  from UNTMP_REDUCEPROOF a, UNTMP_REDUCEDETAIL b " +
							" where 1=1" +
							" and a.enterorg = b.enterorg " +
							" and a.ownership = b.ownership " +
							" and a.caseno =  b.caseno " +
							" and a.differencekind = b.differencekind " +
							" and b.enterorg = " + Common.sqlChar(enterOrg) +
							" and b.ownership = " + Common.sqlChar(ownership) +
							" and b.caseno = " + Common.sqlChar(caseNo) +
							" and b.differencekind = " + Common.sqlChar(differenceKind);
	
				String propertyNo = "";
				String serialNo = "";
				String adjustBookAmount = "";
				String adjustBookValue = "";
				String adjustNetValue = "";
				Double newBookAmount = 0.00;
				String cause = "";
				String cause1 = "";
				String reduceDate = "";
				ResultSet rs = db.querySQL(querySQL);
				while(rs.next()){
					propertyNo = rs.getString("propertyno");
					serialNo = rs.getString("serialno");
					newBookAmount = rs.getDouble("newbookamount");
					adjustBookAmount = rs.getString("adjustbookamount");
					adjustBookValue = rs.getString("adjustbookvalue");
					adjustNetValue = rs.getString("adjustnetvalue");
					cause = Common.get(rs.getString("cause"));
					cause1 = Common.get(rs.getString("cause1"));
					reduceDate = rs.getString("reducedate");
					querySQL = "update UNTMP_MOVABLEDETAIL set";
					if("update".equals(type)){
						// 加上括弧，避免錯誤資料 (e.g. adjustNetValue 為-1 => 若無加空格會括弧會產生 --1 => --為SQL註解符號，導致後續修改、條件都會失效 )
						querySQL += " bookamount = bookamount - ("+adjustBookAmount+"),"+
								" bookvalue = bookvalue - ("+adjustBookValue+"),"+
								" netvalue = netvalue - ("+adjustNetValue+"),"+
								" reducedate = " + Common.sqlChar(reduceDate)+", "+
								" reducecause = " + Common.sqlChar(cause)+", "+
								" reducecause1 = " + Common.sqlChar(cause1)+", ";
					}else{
						querySQL += " bookamount = bookamount + "+adjustBookAmount+","+
								" bookvalue = bookvalue + "+adjustBookValue+","+
								" netvalue = netvalue + "+adjustNetValue+","+
								" reducedate = null, " +
								" reducecause = null, " +
								" reducecause1 = null, " ;
					}
					
					if(newBookAmount == 0.00 && "update".equals(type) ){				
						querySQL += " datastate = '2' ";
					}else{
						querySQL += " datastate = '1' ";
					}
					
				querySQL += " where 1=1 " + 
							" and enterorg = " + Common.sqlChar(enterOrg) +
							" and ownership = " + Common.sqlChar(ownership) +
							" and differencekind = " + Common.sqlChar(differenceKind) +
							" and propertyno = " + Common.sqlChar(propertyNo) +
							" and serialno = " + Common.sqlChar(serialNo) ;
 			
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
	

//傳回delete sql
protected String[] getDeleteSQL(){ 
		String[] execSQLArray = new String[2];
		//刪除 UNTMP_ReduceProof TABLE
		execSQLArray[0] = new SQLCreator()._obtainSQLforDelete("UNTMP_ReduceProof", getPKMap(), getRecordMap());
				
		//刪除 UNTMP_ReduceDetail TABLE
		execSQLArray[1]=" delete UNTMP_ReduceDetail where 1=1 " +
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

public UNTMP014F  queryOne() throws Exception{
	Database db = new Database();
	UNTCH_Tools ut = new UNTCH_Tools();
	UNTMP014F obj = this;	
	try {
		String sql=" select a.enterOrg, b.organSName, a.ownership, a.caseNo, a.caseName, a.writeDate, a.writeUnit, a.proofDoc, a.proofNo, a.manageNo, a.summonsNo, a.reduceDate, a.approveOrg, a.approveDate, a.approveDoc, a.verify, a.notes, a.closing, a.editID, a.editDate, a.editTime, a.summonsDate, a.cause, " +
					" (select codename from SYSCA_CODE z where z.codekindid = 'CAA' and z.codecon1 in (2,4) and z.codeid = a.cause) as causeName "+
					" from UNTMP_ReduceProof a, SYSCA_Organ b where 1=1 " +
					" and a.enterOrg = " + Common.sqlChar(enterOrg) +
					" and a.ownership = " + Common.sqlChar(ownership) +
					" and a.caseNo = " + Common.sqlChar(caseNo) +
					" and a.differenceKind = " + Common.sqlChar(differenceKind) +
					" and b.organID = a.enterOrg" +
					" order by a.enterOrg, a.ownership, a.caseNo  ";
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

//入帳設定
protected String checkVerify(){
    String sql="";
	//1.check是否可以更改
	//1.1減損日期之年月不可小於等於最大的月結年月
	if(Integer.parseInt(getReduceDate().substring(0,5)) > Integer.parseInt(MaxClosingYM.getMaxClosingYM(enterOrg))){
		//更改 UNTMP_ReduceDetail 檔案資料
		sql = "update UNTMP_ReduceDetail set"+
		        " verify = 'Y'," +
		        " reduceDate = " + Common.sqlChar(getReduceDate()) + "," +
				" editID = " + Common.sqlChar(getEditID()) + "," +
				" editDate = " + Common.sqlChar(getEditDate()) + "," +
				" editTime = " + Common.sqlChar(getEditTime()) + 	                
				" where 1=1 " + 
				" and enterOrg = " + Common.sqlChar(enterOrg) +
				" and ownership = " + Common.sqlChar(ownership) +
				" and caseNo = " + Common.sqlChar(caseNo) +
				":;:"; 
		}else{
		    setVerifyError("Y");
			setStateUpdateError();
	        setErrorMsg("入帳設定有錯，減損日期之年月必須大於月結年月！");
		}
	return sql;
}

//異動 UNTMP_MovableDetail 檔案
protected String updMovableDetail(String select){
	Database db = new Database();
	String sqlMovable = "";
	String checkDetail="";
	//1.更新 movable.bookvalue = bookvalue + adjustdetail.adjustbookvalue
	//2.更新 movabledetail.bookvalue = adjustdetail.newbookvalue
	try {
		String sql =" select b.enterOrg, b.ownership, b.propertyNo, b.serialNo, "+
					" a.bookAmount, b.adjustBookAmount, a.bookValue, b.adjustBookValue, " +
					" b.newBookAmount, b.cause, b.cause1, " +
					" b.submitCityGov, c.approveDate, c.approveDoc "+
					" from UNTMP_MovableDetail a, UNTMP_ReduceDetail b, UNTMP_ReduceProof c "+
					" where 1=1 " +
					" and b.enterOrg=c.enterOrg and b.ownership=c.ownership and b.caseNo=c.caseNo " +
					" and a.enterOrg=b.enterOrg and a.ownership=b.ownership and a.propertyNo=b.propertyNo and a.serialNo=b.serialNo "+
					" and b.enterOrg = " + Common.sqlChar(enterOrg) +
					" and b.ownership = " + Common.sqlChar(ownership) +
					" and b.caseNo = " + Common.sqlChar(caseNo) +
					" order by a.enterOrg, a.ownership, a.propertyNo, a.serialNo "+
					"" ;
		ResultSet rs = db.querySQL(sql);
		while (rs.next()){
		    checkDetail="Y";
		    if(select.equals("S")){
			    if("Y".equals(rs.getString("submitCityGov")) && (((getApproveDate()==null || "".equals(getApproveDate()))) || ((getApproveDoc()==null || "".equals(getApproveDoc()))))){
			    	setVerifyError("Y");
		            setErrorMsg("若需否呈報本府核定欄位為「是」，則核准日期、核准文號需輸入資料後才可做減帳處理！");
			    	break;
			    }
		    }else if(select.equals("A")){
			    if("Y".equals(rs.getString("submitCityGov")) && ((("".equals(rs.getString("approveDate")) || rs.getString("approveDate")==null)) || (("".equals(rs.getString("approveDoc")) || rs.getString("approveDoc")==null)))){
			    	setVerifyError("Y");
		            setErrorMsg("若需否呈報本府核定欄位為「是」，則核准日期、核准文號需輸入資料後才可做減帳處理！");
			    	break;
			    }
		    }
		    if(rs.getString("newBookAmount").equals("0")){
		    	sqlMovable += " update UNTMP_MovableDetail set " +
	    		  			  " dataState = '2', " +
	    		  			  " reduceDate = " + Common.sqlChar(getReduceDate()) + "," +
	    		  			  " reduceCause = " + Common.sqlChar(rs.getString("cause")) + "," +
	    		  			  " reduceCause1 = " + Common.sqlChar(rs.getString("cause1")) + "," +
	    		  			  " bookAmount =  "  + Common.sqlInt((Integer.parseInt(rs.getString("bookAmount"))-Integer.parseInt(rs.getString("adjustBookAmount")))+"") + "," +
				    		  " bookValue =  "  + Common.sqlInt((Integer.parseInt(rs.getString("bookValue"))-Integer.parseInt(rs.getString("adjustBookValue")))+"") +
							  " where 1=1 " + 
							  " and enterOrg = " + Common.sqlChar(rs.getString("enterOrg")) +
							  " and ownership = " + Common.sqlChar(rs.getString("ownership")) +
							  " and propertyNo = " + Common.sqlChar(rs.getString("propertyNo")) +
							  " and serialNo = " + Common.sqlChar(rs.getString("serialNo")) +
							  ":;:" ;
		    }else{
		    	sqlMovable += " update UNTMP_MovableDetail set " +
				    		  " bookAmount =  "  + Common.sqlInt((Integer.parseInt(rs.getString("bookAmount"))-Integer.parseInt(rs.getString("adjustBookAmount")))+"") + "," +
				    		  " bookValue =  "  + Common.sqlInt((Integer.parseInt(rs.getString("bookValue"))-Integer.parseInt(rs.getString("adjustBookValue")))+"") +
							  " where 1=1 " + 
							  " and enterOrg = " + Common.sqlChar(rs.getString("enterOrg")) +
							  " and ownership = " + Common.sqlChar(rs.getString("ownership")) +
							  " and propertyNo = " + Common.sqlChar(rs.getString("propertyNo")) +
							  " and serialNo = " + Common.sqlChar(rs.getString("serialNo")) +
							  ":;:" ;
		    }
		}
        if(!checkDetail.equals("Y")){
            setVerifyError("Y");
            setErrorMsg("該筆減損單之明細資料標籤要有資料才能入帳！");
        }
	} catch (Exception e) {
		e.printStackTrace();
	} finally {
		db.closeAll();
	}
	return sqlMovable;
}

//異動 UNTMP_Movable 檔案
protected String updMovable(){
	Database db = new Database();
	String sqlMovable = "";
	UNTMP014F obj = this;
	int count=0;
	try {
		String sql =" select b.enterOrg, b.ownership, a.lotNo, b.propertyNo, b.serialNo, "+
					" a.bookAmount, b.adjustBookAmount, a.bookValue, b.adjustBookValue "+
					" from UNTMP_Movable a, UNTMP_ReduceDetail b "+
					" where 1=1 " +
					" and a.enterOrg=b.enterOrg and a.ownership=b.ownership and a.propertyNo=b.propertyNo and a.lotNo=b.lotNo "+
					" and b.enterOrg = " + Common.sqlChar(enterOrg) +
					" and b.ownership = " + Common.sqlChar(ownership) +
					" and b.caseNo = " + Common.sqlChar(caseNo) +
					" order by a.enterOrg, a.ownership, a.propertyNo, a.lotNo, b.serialNo "+
					"" ;
		ResultSet rs = db.querySQL(sql);
		while (rs.next()){
		    obj.setCheckEnterOrg(rs.getString("enterOrg"));
		    obj.setCheckOwnership(rs.getString("ownership"));
		    obj.setCheckLotNo(rs.getString("lotNo"));
		    obj.setCheckPropertyNo(rs.getString("propertyNo"));
			//=======
			changeNo = rs.getString("enterOrg")+rs.getString("ownership")+rs.getString("propertyNo")+rs.getString("lotNo");
			Integer I = (Integer) h.get(changeNo+"_sumBookAmount");
			if (I!=null) {
				if(!(obj.getCheckEnterOrg()).equals(obj.getCheckOldEnterOrg()) || !(obj.getCheckOwnership()).equals(obj.getCheckOldOwnership()) || !(obj.getCheckPropertyNo()).equals(obj.getCheckOldPropertyNo()) || !(obj.getCheckLotNo()).equals(obj.getCheckOldLotNo())){
					h.put(changeNo+"_sumBookAmount", new Integer(((Integer) h.get(changeNo+"_sumBookAmount")).intValue()));
					h.put(changeNo+"_sumBookValue", new Integer(((Integer) h.get(changeNo+"_sumBookValue")).intValue()));
				}else{
					h.put(changeNo+"_sumBookAmount", new Integer(sumBookAmount));
					h.put(changeNo+"_sumBookValue", new Integer(sumBookValue));
				}
			} else {
				h.put(changeNo+"_sumBookAmount", new Integer(rs.getInt("BookAmount")));
				h.put(changeNo+"_sumBookValue", new Integer(rs.getInt("BookValue")));
			}    
			//=========		    
		    if(!(obj.getCheckEnterOrg()).equals(obj.getCheckOldEnterOrg()) || !(obj.getCheckOwnership()).equals(obj.getCheckOldOwnership()) || !(obj.getCheckPropertyNo()).equals(obj.getCheckOldPropertyNo()) || !(obj.getCheckLotNo()).equals(obj.getCheckOldLotNo())){
		    	sumBookAmount=((Integer) h.get(changeNo+"_sumBookAmount")).intValue();
		        sumBookValue=((Integer) h.get(changeNo+"_sumBookValue")).intValue();
		    }   
		        sumBookAmount -= rs.getInt("adjustBookAmount");
		        sumBookValue -= rs.getInt("adjustBookValue");
		    //=========    
	    	obj.setCheckOldEnterOrg(obj.getCheckEnterOrg());
	    	obj.setCheckOldOwnership(obj.getCheckOwnership());
	    	obj.setCheckOldLotNo(obj.getCheckLotNo());
	    	obj.setCheckOldPropertyNo(obj.getCheckPropertyNo());
	    	if(sumBookAmount==0){
		        sqlMovable += " update UNTMP_Movable set " +
		        			  " dataState='2'," +
			  				  " bookAmount =  "  + Common.sqlInt(sumBookAmount+"") + "," +
				    		  " bookValue =  "  + Common.sqlInt(sumBookValue+"") +
							  " where 1=1 " + 
							  " and enterOrg = " + Common.sqlChar(rs.getString("enterOrg")) +
							  " and ownership = " + Common.sqlChar(rs.getString("ownership")) +
							  " and propertyNo = " + Common.sqlChar(rs.getString("propertyNo")) +
							  " and lotNo = " + Common.sqlChar(rs.getString("lotNo")) +
							  ":;:" ;
	    	}else{
		        sqlMovable += " update UNTMP_Movable set " +
	  		  				  " bookAmount =  "  + Common.sqlInt(sumBookAmount+"") + "," +
				    		  " bookValue =  "  + Common.sqlInt(sumBookValue+"") +
							  " where 1=1 " + 
							  " and enterOrg = " + Common.sqlChar(rs.getString("enterOrg")) +
							  " and ownership = " + Common.sqlChar(rs.getString("ownership")) +
							  " and propertyNo = " + Common.sqlChar(rs.getString("propertyNo")) +
							  " and lotNo = " + Common.sqlChar(rs.getString("lotNo")) +
							  ":;:" ;
	    	}
	    	h.put(changeNo+"_sumBookAmount", new Integer(sumBookAmount));
			h.put(changeNo+"_sumBookValue", new Integer(sumBookValue));
	    	count++;
		}
	} catch (Exception e) {
		e.printStackTrace();
	} finally {
		db.closeAll();
	}
	return sqlMovable;
}

//回復入帳設定
public void approveRe()throws Exception{	
	Database db = new Database();
	String enterOrgQuery = "";
	String ownershipQuery = "";
	String caseNoQuery = "";
	String propertyNoQuery = "";
	String serialNoQuery = "";
	String reduceSql = "",reduceCount = "";
	String adjustSql = "",adjustCount = "";
	String moveSql = "",moveCount = "";
	String reduceOriginal = "",reduceMax = "",reduceMaxSql = "";
	String adjustMax = "",adjustMaxSql = "";
	String moveMax = "",moveMaxSql = "";
	int count = 0;
	try {    
		String[] execSQLArray;
		String strSQL = "";
		String sql ="select a.caseNo, b.enterOrg, b.ownership, b.propertyNo, b.serialNo, b.lotNo, " +
					" a.editDate, a.editTime, a.reduceDate, b.adjustBookAmount, b.adjustBookValue " +
					" from untmp_reduceProof a, untmp_reduceDetail b " +
					" where 1=1 " +
					" and a.enterOrg = b.enterOrg and a.ownership = b.ownership and a.caseNo = b.caseNo" +
	    			" and a.enterOrg = " + Common.sqlChar(enterOrg) +
	    			" and a.ownership = " + Common.sqlChar(ownership) +
	    			" and a.caseNo = " + Common.sqlChar(caseNo) +
					" order by b.enterOrg, b.ownership, b.propertyNo, b.lotNo, b.serialNo" +
					"" ;
		ResultSet rs = db.querySQL(sql);
		while (rs.next()){
			count++;
			enterOrgQuery = rs.getString("enterOrg");
			ownershipQuery = rs.getString("ownership");
			caseNoQuery = rs.getString("caseNo");
			propertyNoQuery = rs.getString("propertyNo");
			serialNoQuery = rs.getString("serialNo");
			//該減損單之明細資料,存在未入帳的「動產減損單明細檔UNTMP_ReduceDetail」資料，則提示使用者
			reduceSql = "select count(*) count from untmp_reduceDetail " +
						" where enterOrg="+Common.sqlChar(enterOrgQuery)+" and ownership="+Common.sqlChar(ownershipQuery)+" and propertyNo="+Common.sqlChar(propertyNoQuery)+" and serialNo="+Common.sqlChar(serialNoQuery)+" and verify='N'";
			reduceCount = MaxClosingYM.getCountValues(reduceSql);
			
			//該減損單之明細資料,存在未入帳的「動產增減值單明細檔UNTMP_AdjustDetail」資料，則提示使用者
			adjustSql = "select count(*) count from untmp_adjustDetail " +
						" where enterOrg="+Common.sqlChar(enterOrgQuery)+" and ownership="+Common.sqlChar(ownershipQuery)+" and propertyNo="+Common.sqlChar(propertyNoQuery)+" and serialNo="+Common.sqlChar(serialNoQuery)+" and verify='N'";
			adjustCount = MaxClosingYM.getCountValues(adjustSql);
			
			//該減損單之明細資料,存在未入帳的「動產移動單明細檔UNTMP_MoveDetail」資料，則提示使用者
			moveSql = "select count(*) count from untmp_moveDetail " +
					" where enterOrg="+Common.sqlChar(enterOrgQuery)+" and ownership="+Common.sqlChar(ownershipQuery)+" and propertyNo="+Common.sqlChar(propertyNoQuery)+" and serialNo="+Common.sqlChar(serialNoQuery)+" and verify='N'";
			moveCount = MaxClosingYM.getCountValues(moveSql);
			//------------------------------------------------------------------------------------------------
			reduceOriginal = rs.getString("editDate")+rs.getString("editTime")+rs.getString("reduceDate");
			//該減損單之(異動日期+異動時間+減損日期)≦減損單明細資料之其他減損單之最大的(異動日期+異動時間+減損日期)則提示使用者
			reduceMaxSql = "select count(*) count from untmp_reduceDetail " +
							" where enterOrg="+Common.sqlChar(enterOrgQuery)+" and ownership="+Common.sqlChar(ownershipQuery)+" and propertyNo="+Common.sqlChar(propertyNoQuery)+" and serialNo="+Common.sqlChar(serialNoQuery)+" and verify='Y' " +
							" and '" + reduceOriginal + "'<=(editDate || editTime || reduceDate) " +
							" and caseNo!="+Common.sqlChar(caseNoQuery);
			reduceMax = MaxClosingYM.getCountValues(reduceMaxSql);
			
			//該減損單之(異動日期+異動時間+減損日期)≦減損單明細資料之其他增減值單之最大的(異動日期+異動時間+減損日期)則提示使用者
			adjustMaxSql = "select count(*) count from untmp_adjustDetail " +
							" where enterOrg="+Common.sqlChar(enterOrgQuery)+" and ownership="+Common.sqlChar(ownershipQuery)+" and propertyNo="+Common.sqlChar(propertyNoQuery)+" and serialNo="+Common.sqlChar(serialNoQuery)+" and verify='Y' " +
							" and '" + reduceOriginal + "'<=(editDate || editTime || adjustDate) " ;
			adjustMax = MaxClosingYM.getCountValues(adjustMaxSql);
			
			//該減損單之(異動日期+異動時間+減損日期)≦減損單明細資料之其他移動單之最大的(異動日期+異動時間+減損日期)則提示使用者
			moveMaxSql = "select count(*) count from untmp_moveProof a, untmp_moveDetail b " +
						" where 1=1 and a.caseNo=b.caseNo and a.enterOrg=b.enterOrg and a.ownership=b.ownership " +
						" and b.enterOrg="+Common.sqlChar(enterOrgQuery)+" and b.ownership="+Common.sqlChar(ownershipQuery)+" and b.propertyNo="+Common.sqlChar(propertyNoQuery)+" and b.serialNo="+Common.sqlChar(serialNoQuery)+" and a.verify='Y' " +
						" and '" + reduceOriginal + "'<=(b.editDate || b.editTime || a.moveDate) " ;
			moveMax = MaxClosingYM.getCountValues(moveMaxSql);
			//System.out.println(moveMaxSql);
			//------------------------------------------------------------------------------------------------
			if(verify.equals("Y") && reduceCount.equals("0") && adjustCount.equals("0") && moveCount.equals("0") && reduceMax.equals("0") && adjustMax.equals("0") && moveMax.equals("0")){
				//依據該減損單「入帳機關enterOrg＋權屬ownership＋電腦單號caseNo」設定
				if(count==1){
					strSQL += "update untmp_reduceProof set verify ='N' " +
							" where enterOrg=" + Common.sqlChar(enterOrgQuery) +
							" and ownership=" + Common.sqlChar(ownershipQuery) +
							" and caseNo=" + Common.sqlChar(caseNoQuery) +
							":;:";				
					strSQL += "update untmp_reduceDetail set verify ='N' " +
							" where enterOrg=" + Common.sqlChar(enterOrgQuery) +
							" and ownership=" + Common.sqlChar(ownershipQuery) +
							" and caseNo=" + Common.sqlChar(caseNoQuery) +
							":;:";				
				}
				
				//依據減損單明細「入帳機關enterOrg＋權屬ownership＋財產編號propertyNo＋財產批號lotNo」設定
				strSQL +="update untmp_movable set bookAmount = (bookAmount+" + rs.getInt("adjustBookAmount") + ")," +
						" bookValue = (bookValue+" + rs.getInt("adjustBookValue") + ")," +
						" dataState ='1' " +
						" where enterOrg=" + Common.sqlChar(enterOrgQuery) +
						" and ownership=" + Common.sqlChar(ownershipQuery) +
						" and propertyNo=" + Common.sqlChar(propertyNoQuery) +
						" and lotNo=" + Common.sqlChar(rs.getString("lotNo")) +
						":;:";

				//依據減損單明細「入帳機關enterOrg＋權屬ownership＋財產編號propertyNo＋財產分號serialNo」設定
				strSQL +="update untmp_movableDetail set bookAmount = (bookAmount+" + rs.getInt("adjustBookAmount") + ")," +
						" bookValue = (bookValue+" + rs.getInt("adjustBookValue") + ")," +
						" dataState ='1', " +
						" reduceDate =null, " +
						" reduceCause =null, " +
						" reduceCause1 =null " +
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
				}else if(!moveCount.equals("0")){
					setErrorMsg("保管使用異動作業存在未入帳的資料，無法回復入帳！");
				}else if(!reduceMax.equals("0")){
					setErrorMsg("並非最後一筆入帳(減損)的資料，無法回復入帳！");
				}else if(!adjustMax.equals("0")){
					setErrorMsg("並非最後一筆入帳(增減值)的資料，無法回復入帳！");
				}else if(!moveMax.equals("0")){
					setErrorMsg("並非最後一筆入帳(移動)的資料，無法回復入帳！");
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
