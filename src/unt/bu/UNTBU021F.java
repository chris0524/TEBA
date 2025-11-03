package unt.bu;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import unt.ch.UNTCH_Tools;
import util.Common;
import util.Database;
import util.MaxClosingYM;
import util.getTableFile;
import TDlib_Simple.com.src.SQLCreator;

/**
 * <br/>程式目的：建物增減值作業－增減值單資料
 * <br/>程式代號：UNTBU021F
 * <br/>程式日期：094010904
 * <br/>程式作者：judy
 * <br/>--------------------------------------------------------
 * <br/>修改作者　　     修改日期　　　			修改目的
 * <br/>
 * <br/>--------------------------------------------------------
 */

public class UNTBU021F extends UNTBU021Q{

	/**
	 * 如果檔案有維護到的欄位，要來這裡下變數設定
	 */
	
	String strAdjustDate;
	
	
	private String enterOrg;
	private String caseNo;
	private String ownership;
	private String differenceKind;
	private String engineeringNo;
	private String caseName;
	private String writeDate;
	private String writeUnit;
	private String proofYear;
	private String proofDoc;
	private String proofNo;
	private String manageNo;
	private String summonsNo;
	private String adjustDate;
	private String approveOrg;
	private String approveDate;
	private String approveDoc;
	private String verify;
	private String notes;
	private String editID;
	private String editDate;
	private String editTime;
	
	private String enterOrgName;
	private String engineeringNoName;
	
	
	/**
	 * 如果檔案有維護到的欄位，要來這裡設定
	 */
	public String getEnterOrg(){ return checkGet(enterOrg); }
	public void setEnterOrg(String s){ enterOrg=checkSet(s); }
	public String getEnterOrgName(){ return checkGet(enterOrgName); }
	public void setEnterOrgName(String s){ enterOrgName=checkSet(s); }
	public String getOwnership(){ return checkGet(ownership); }
	public void setOwnership(String s){ ownership=checkSet(s); }
	public String getCaseNo(){ return checkGet(caseNo); }
	public void setCaseNo(String s){ caseNo=checkSet(s); }
	public String getCaseName(){ return checkGet(caseName); }
	public void setCaseName(String s){ caseName=checkSet(s); }
	public String getWriteDate(){ return checkGet(writeDate); }
	public void setWriteDate(String s){ writeDate=checkSet(s); }
	public String getWriteUnit(){ return checkGet(writeUnit); }
	public void setWriteUnit(String s){ writeUnit=checkSet(s); }
	public String getProofDoc(){ return checkGet(proofDoc); }
	public void setProofDoc(String s){ proofDoc=checkSet(s); }
	public String getProofNo(){ return checkGet(proofNo); }
	public void setProofNo(String s){ proofNo=checkSet(s); }
	public String getManageNo(){ return checkGet(manageNo); }
	public void setManageNo(String s){ manageNo=checkSet(s); }
	public String getSummonsNo(){ return checkGet(summonsNo); }
	public void setSummonsNo(String s){ summonsNo=checkSet(s); }
	public String getAdjustDate(){ return checkGet(adjustDate); }
	public void setAdjustDate(String s){ adjustDate=checkSet(s); }
	public String getApproveOrg(){ return checkGet(approveOrg); }
	public void setApproveOrg(String s){ approveOrg=checkSet(s); }
	public String getApproveDate(){ return checkGet(approveDate); }
	public void setApproveDate(String s){ approveDate=checkSet(s); }
	public String getApproveDoc(){ return checkGet(approveDoc); }
	public void setApproveDoc(String s){ approveDoc=checkSet(s); }
	public String getVerify(){ return checkGet(verify); }
	public void setVerify(String s){ verify=checkSet(s); }
	public String getNotes(){ return checkGet(notes); }
	public void setNotes(String s){ notes=checkSet(s); }
	
	public String getStrAdjustDate() {return checkGet(strAdjustDate);}
	public void setStrAdjustDate(String strAdjustDate) {this.strAdjustDate = checkSet(strAdjustDate);}
	public String getDifferenceKind() {return checkGet(differenceKind);}
	public void setDifferenceKind(String differenceKind) {this.differenceKind = checkSet(differenceKind);}
	public String getEngineeringNo() {return checkGet(engineeringNo);}
	public void setEngineeringNo(String engineeringNo) {this.engineeringNo = checkSet(engineeringNo);}
	public String getProofYear() {return checkGet(proofYear);}
	public void setProofYear(String proofYear) {this.proofYear = checkSet(proofYear);}
	public String getEditID() {return checkGet(editID);}
	public void setEditID(String editID) {this.editID = checkSet(editID);}
	public String getEditDate() {return checkGet(editDate);}
	public void setEditDate(String editDate) {this.editDate = checkSet(editDate);}
	public String getEditTime() {return checkGet(editTime);}
	public void setEditTime(String editTime) {this.editTime = checkSet(editTime);}
	public String getEngineeringNoName() {return checkGet(engineeringNoName);}
	public void setEngineeringNoName(String engineeringNoName) {this.engineeringNoName = checkSet(engineeringNoName);}
	
	private String reVerify;
	public String getReVerify() {return reVerify;}
	public void setReVerify(String reVerify) {this.reVerify = reVerify;}
	
	
	String verifyError;
	
	public String getVerifyError(){ return checkGet(verifyError); }
	public void setVerifyError(String s){ verifyError=checkSet(s); }
	
	String closing;
	public String getClosing(){ return checkGet(closing); }
	public void setClosing(String s){ closing=checkSet(s); }
	private String summonsDate;
	public String getSummonsDate() {return checkGet(summonsDate);}
	public void setSummonsDate(String summonsDate) {this.summonsDate = checkSet(summonsDate);}

	private Map getPKMap(){
		Map map = new HashMap();
		
		map.put("EnterOrg", getEnterOrg());
		map.put("CaseNo", getCaseNo());
		map.put("Ownership", getOwnership());
		map.put("DifferenceKind", getDifferenceKind());
		
		return map;
	}
	
	private Map getRecordMap(){
		Map map = new HashMap();
		UNTCH_Tools ul = new UNTCH_Tools();
		
		map.put("EnterOrg", getEnterOrg());
		map.put("CaseNo", getCaseNo());
		map.put("Ownership", getOwnership());
		map.put("DifferenceKind", getDifferenceKind());
		map.put("EngineeringNo", getEngineeringNo());
		map.put("CaseName", getCaseName());
		map.put("WriteDate", ul._transToCE_Year(getWriteDate()));
		map.put("WriteUnit", getWriteUnit());
		map.put("ProofYear", getProofYear());
		map.put("ProofDoc", getProofDoc());

		//問題單1290補充: update時更改編號-年，重新取得編號-號
		String proofno = getProofNo();
		if(this.checkProofyearChanged(getEnterOrg(), getCaseNo(), getProofYear())){
			proofno = MaxClosingYM.getMaxProofNo("UNTBU_ADJUSTPROOF",enterOrg,ownership,this.getProofYear());
		}
		map.put("ProofNo", proofno);
		map.put("ManageNo", getManageNo());
		map.put("SummonsNo", getSummonsNo());
		map.put("AdjustDate", ul._transToCE_Year(getAdjustDate()));
		map.put("ApproveOrg", getApproveOrg());
		map.put("ApproveDate", ul._transToCE_Year(getApproveDate()));
		map.put("ApproveDoc", getApproveDoc());
		map.put("Verify", getVerify());
		map.put("Notes", getNotes());
		map.put("EditID", getEditID());
		map.put("EditDate", ul._transToCE_Year(getEditDate()));
		map.put("EditTime", getEditTime());
		map.put("summonsDate", ul._transToCE_Year(getSummonsDate()));
		
		return map;
	}

	//檢查update時，proofyear是否有改變
	private boolean checkProofyearChanged(String enterorg, String caseno, String newproofyear){
		boolean isChanged = false;
		String oldproofyear ="";
		ResultSet rs;
		Database db = new Database();
		try{
			String sql = "select proofyear from UNTBU_ADJUSTPROOF where enterorg=" + Common.sqlChar(enterorg) +
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
		setProofNo(MaxClosingYM.getMaxProofNo("UNTBU_AdjustProof",enterOrg,ownership,this.getProofYear()));
		String[] execSQLArray = new String[1];
		execSQLArray[0] = new SQLCreator()._obtainSQLforInsert("UNTBU_ADJUSTPROOF", getPKMap(), getRecordMap());
		return execSQLArray;
	}

	public String queryOldVerify() {
		Database db = null;
		try {
			db = new Database();
			return db.getLookupField("select verify from UNTBU_ADJUSTPROOF where enterorg = " + Common.sqlChar(this.getEnterOrg()) + " and caseno = " + Common.sqlChar(this.getCaseNo()));
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
			tmpchk[0] = " select count(case when isnull(z.adjustdate, '') != '' then 1 else null end) as checkResult from UNTBU_ADJUSTDETAIL  a "
					  + " OUTER APPLY ( " 
					  + " select top 1 b.adjustdate  from UNTBU_ADJUSTDETAIL b  "
					  + " where a.enterorg=b.enterorg and a.ownership = b.ownership and a.differencekind=b.differencekind and a.propertyno=b.propertyno and a.serialno=b.serialno  "
					  + " and verify ='Y' and b.caseno != a.caseno and b.adjustdate >= " + ut._transToCE_Year(this.getAdjustDate())
					  + " ) as z "
					  + " where a.enterorg = " + Common.sqlChar(this.getEnterOrg()) + " and a.caseno = " + Common.sqlChar(this.getCaseNo());
			tmpchk[1] = "!=";
			tmpchk[2] = "0";
			tmpchk[3] = "入帳失敗, 檢查到已有入帳的建物增減值資料其入帳日期大於等於此單據入帳日期";
			tmpchk[4] = "N";
			sqls.add(tmpchk);
		} else if ("Y".equals(oldVerify) && "Y".equals(this.getReVerify())) {
			// 回復入帳
			tmpchk = new String[5];
			tmpchk[0] = " select count(*) as checkResult from UNTBU_ADJUSTDETAIL  a "
					  + " inner join UNTBU_REDUCEDETAIL b  " 
					  + " on a.enterorg=b.enterorg and a.ownership=b.ownership and a.differencekind=b.differencekind  "
					  + " and a.propertyno=b.propertyno and a.serialno=b.serialno  "
					  + " where a.enterorg = " + Common.sqlChar(this.getEnterOrg()) + " and a.caseno =" + Common.sqlChar(this.getCaseNo());
			tmpchk[1] = "!=";
			tmpchk[2] = "0";
			tmpchk[3] = "回復入帳失敗, 檢查到已有建物資料於減損作業中登打,請先移除該減損單";
			tmpchk[4] = "N";
			sqls.add(tmpchk);
			
			tmpchk = new String[5];
			tmpchk[0] = " select count(case when isnull(z.adjustdate, '') != '' then 1 else null end) as checkResult from UNTBU_ADJUSTDETAIL  a "
					  + " OUTER APPLY ( " 
					  + " select top 1 b.adjustdate  from UNTBU_ADJUSTDETAIL b  "
					  + " where a.enterorg=b.enterorg and a.ownership = b.ownership and a.differencekind=b.differencekind and a.propertyno=b.propertyno and a.serialno=b.serialno  "
					  + " and verify ='Y' and b.adjustdate >= a.adjustdate and b.caseno != a.caseno" 
					  + " ) as z "
					  + " where a.enterorg = " + Common.sqlChar(this.getEnterOrg()) + " and a.caseno = " + Common.sqlChar(this.getCaseNo());
			tmpchk[1] = "!=";
			tmpchk[2] = "0";
			tmpchk[3] = "回復入帳失敗, 已有建物資料於其他較新的增減值單中入帳";
			tmpchk[4] = "N";
			sqls.add(tmpchk);
		}
		
		return sqls.toArray(new String[sqls.size()][5]);
	}
	
	protected String[] getUpdateSQL() throws Exception {
		if(getReVerify().equals("Y")){	setVerify("N");}
		
		String[] execSQLArray = new String[2];
		execSQLArray[0] = new SQLCreator()._obtainSQLforUpdate("UNTBU_ADJUSTPROOF", getPKMap(), getRecordMap());
		execSQLArray[1] = updateTable();	
		return execSQLArray;
	}


	//傳回delete sql
	protected String[] getDeleteSQL(){ 
		String[] execSQLArray = new String[2];
		//刪除 UNTBU_AdjustProof TABLE
		execSQLArray[0] = new SQLCreator()._obtainSQLforDelete("UNTBU_ADJUSTPROOF", getPKMap(), getRecordMap());

		//刪除 UNTBU_AdjustDetail TABLE
		execSQLArray[1]=" delete UNTBU_AdjustDetail where " +
				" enterOrg = " + Common.sqlChar(enterOrg) +
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
	public UNTBU021F  queryOne() throws Exception{
		Database db = new Database();
		UNTBU021F obj = this;
		try {
			String sql=" select a.*, "+
					" (select b.organSName from SYSCA_Organ b where b.organID = a.enterOrg) as organSName " +
					" from UNTBU_AdjustProof a where 1=1 " +
					" and a.enterOrg = " + Common.sqlChar(enterOrg) +
					" and a.ownership = " + Common.sqlChar(ownership) +
					" and a.caseNo = " + Common.sqlChar(caseNo) +
					" and a.differenceKind = " + Common.sqlChar(differenceKind) +
					" order by a.enterOrg, a.ownership, a.caseNo ";
			ResultSet rs = db.querySQL(sql);
			UNTCH_Tools ul = new UNTCH_Tools();
			if (rs.next()){ 
				obj.setEnterOrg(rs.getString("EnterOrg"));
				obj.setCaseNo(rs.getString("CaseNo"));
				obj.setOwnership(rs.getString("Ownership"));
				obj.setDifferenceKind(rs.getString("DifferenceKind"));
				obj.setEngineeringNo(rs.getString("EngineeringNo"));
				obj.setCaseName(rs.getString("CaseName"));
				obj.setWriteDate(ul._transToROC_Year(rs.getString("WriteDate")));
				obj.setWriteUnit(rs.getString("WriteUnit"));
				obj.setProofYear(rs.getString("ProofYear"));
				obj.setProofDoc(rs.getString("ProofDoc"));
				obj.setProofNo(rs.getString("ProofNo"));
				obj.setManageNo(rs.getString("ManageNo"));
				obj.setSummonsNo(rs.getString("SummonsNo"));
				obj.setAdjustDate(ul._transToROC_Year(rs.getString("AdjustDate")));
				obj.setApproveOrg(rs.getString("ApproveOrg"));
				obj.setApproveDate(ul._transToROC_Year(rs.getString("ApproveDate")));
				obj.setApproveDoc(rs.getString("ApproveDoc"));
				obj.setVerify(rs.getString("Verify"));
				obj.setNotes(rs.getString("Notes"));
				obj.setEditID(rs.getString("EditID"));
				obj.setEditDate(ul._transToROC_Year(rs.getString("EditDate")));
				obj.setEditTime(rs.getString("EditTime"));

				obj.setEnterOrgName(rs.getString("organSName"));
				obj.setEngineeringNoName(ul._getEngineeringNoName(rs.getString("enterOrg"),rs.getString("engineeringNo")));
				obj.setSummonsDate(ul._transToROC_Year(rs.getString("summonsDate")));
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
	 * TODO   判斷增減值日期　不得小於　上次增減值日期	 沒在呼叫...
	 * @deprecated
	 */
	protected void checkVerify(){
		UNTCH_Tools ut = new UNTCH_Tools();	
		/*　2008/08/21   判斷增減值日期　不得小於　上次增減值日期	 */
		String q_sql = " select count(*) as checkResult from  " + "\n"
				+ " ( " + "\n"
				+ " select a.enterorg ,a.ownership ,b.propertyno ,b.serialno " + "\n"
				+ " from untbu_adjustproof a ,untbu_AdjustDetail b  " + "\n"
				+ " where 1=1  " + "\n"
				+ " and a.enterorg = b.enterorg and a.ownership = b.ownership and a.caseno = b.caseno " + "\n"
				+ " and a.enterorg = " + Common.sqlChar(enterOrg) + "\n"
				+ " and a.ownership = " + Common.sqlChar(ownership) + "\n"
				+ " and a.verify = 'Y' " + "\n"
				+ " and exists ( select * from untbu_AdjustDetail c  " + "\n"
				+ "              where 1=1 " + "\n"
				+ "              and b.enterorg = c.enterorg and b.ownership = c.ownership and b.propertyno=c.propertyno and b.serialno = c.serialno " + "\n"
				+ "              and c.caseno= " + Common.sqlChar(caseNo) + "\n"
				+ "            ) " + "\n"
				+ " group by a.enterorg ,a.ownership ,b.propertyno ,b.serialno " + "\n"
				+ " having max(a.adjustdate) > " + Common.sqlChar(ut._transToCE_Year(getAdjustDate())) + "\n"
				+ " ) a " + "\n"
				+ " ";

		//System.out.println(q_sql);
		if(getVerify().equals("Y") && !getReVerify().equals("Y")){
			if(!"0".equals(getTableFile.query_value(q_sql, "checkResult", "0"))){
				setVerifyError("Y");    	   
				setStateUpdateError();
				setErrorMsg("入帳設定錯誤，入帳日期應大於上一筆入帳日期！");
			}
		}

	}

	/**
	 * 取得 建物增減值明細 與 建物主檔 入帳/回復入帳的sql 
	 * 106.01.05 哲賢 內部catch 可能導致 建物主檔 沒辦法正確 增減值/回復 因此調整為直接拋出exception
	 * @return
	 * @throws Exception
	 */
	private String updateTable() throws Exception {
		String sql = "";
		String querySQL = "";

		UNTCH_Tools ut = new UNTCH_Tools();
		if(getReVerify().equals("Y")){
			sql +=   " update UNTBU_AdjustDetail set"+
					" verify = " + Common.sqlChar("N") + "," +
					" adjustDate = " + Common.sqlChar(ut._transToCE_Year(getAdjustDate())) + "," +
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
					" oldarea, oldcarea, oldsarea, oldholdnume, oldholddeno, oldholdarea," +
					" oldBookValue, oldNetValue" +
					" from UNTBU_ADJUSTDETAIL " +
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
							" area = " + Common.sqlChar(rs.getString("oldArea")) + "," +
							" Carea = " + Common.sqlChar(rs.getString("oldCarea")) + "," +
							" Sarea = " + Common.sqlChar(rs.getString("oldSarea")) + "," +
							" holdNume = " + Common.sqlChar(rs.getString("oldholdnume")) + "," +
							" holdDeno = " + Common.sqlChar(rs.getString("oldholddeno")) + "," +
							" holdArea = " + Common.sqlChar(rs.getString("oldholdarea")) + "," +
							" bookValue = " + Common.sqlChar(rs.getString("oldBookValue")) + "," +
							" netValue = " + Common.sqlChar(rs.getString("oldNetValue")) + "," +
							" editID = " + Common.sqlChar(getEditID()) + "," +
							" editDate = " + Common.sqlChar(ut._transToCE_Year(getEditDate())) + "," +
							" editTime = " + Common.sqlChar(getEditTime()) + 	                
							" where 1=1 " + 
							" and enterOrg = " + Common.sqlChar(enterOrg) +
							" and ownership = " + Common.sqlChar(ownership) +
							//			        	  " and caseNo = " + Common.sqlChar(caseNo) +
							" and differenceKind = " + Common.sqlChar(differenceKind) +
							" and propertyno = " + Common.sqlChar(rs.getString("propertyNo")) +
							" and serialno = " + Common.sqlChar(rs.getString("serialNo")) +
							"";
				}

			}finally{
				db.closeAll();
			}

		}else if("Y".equals(this.getVerify())){
			sql +=   " update UNTBU_AdjustDetail set"+
					" verify = " + Common.sqlChar(this.getVerify()) + "," +
					" adjustDate = " + Common.sqlChar(ut._transToCE_Year(getAdjustDate())) + "," +
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
					" newarea ,newcarea, newsarea, newholdnume, newholddeno, newholdarea," +
					" newBookValue, newNetValue" +
					" from UNTBU_ADJUSTDETAIL " +
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
							" area = " + Common.sqlChar(rs.getString("newarea")) + "," +
							" Carea = " + Common.sqlChar(rs.getString("newCarea")) + "," +
							" Sarea = " + Common.sqlChar(rs.getString("newSarea")) + "," +
							" holdNume = " + Common.sqlChar(rs.getString("newholdnume")) + "," +
							" holdDeno = " + Common.sqlChar(rs.getString("newholddeno")) + "," +
							" holdArea = " + Common.sqlChar(rs.getString("newholdarea")) + "," +
							" bookValue = " + Common.sqlChar(rs.getString("newBookValue")) + "," +
							" netValue = " + Common.sqlChar(rs.getString("newNetValue")) + "," +
							" editID = " + Common.sqlChar(getEditID()) + "," +
							" editDate = " + Common.sqlChar(ut._transToCE_Year(getEditDate())) + "," +
							" editTime = " + Common.sqlChar(getEditTime()) + 	                
							" where 1=1 " + 
							" and enterOrg = " + Common.sqlChar(enterOrg) +
							" and ownership = " + Common.sqlChar(ownership) +
							//			        	  " and caseNo = " + Common.sqlChar(caseNo) +
							" and differenceKind = " + Common.sqlChar(differenceKind) +
							" and propertyno = " + Common.sqlChar(rs.getString("propertyNo")) +
							" and serialno = " + Common.sqlChar(rs.getString("serialNo")) +
							"";				
				}

			}finally{
				db.closeAll();
			}  
		}

		return sql;
	}

	/**
	 * 更新建物主檔的折舊欄位 
	 * TODO .. exception 自行catch後放掉
	 */
	public void updateBUILDING(String enterOrg, String ownership, String caseNo, String differenceKind, String verify){
		if("updateError".equals(this.getState())){

		}else{
			Database db = new Database();
			try{
				String querySQL = "select *,otherlimityear + isnull(addlimityear,0) + isnull(overlimityear,0) - isnull(reducelimityear,0) as newotherlimityear from UNTBU_ADJUSTDETAIL " +
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

						querySQL = "update UNTBU_BUILDING set"
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

						querySQL = "update UNTBU_BUILDING set"
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
	
	public void updateManage(String enterOrg, String ownership, String caseNo, String differenceKind, String verify) {
		String querySQL = "";
		String updateSQL = "";
		Database db = null;
		ResultSet rs = null;

		try {
			db = new Database();
			querySQL = "select a.propertyNo, a.serialNo, a.adjustArea " +
					" from UNTBU_AdjustDetail a where 1 = 1 " +
					" and enterOrg = " + Common.sqlChar(enterOrg) +
					" and ownership = " + Common.sqlChar(ownership) +
					" and caseNo = " + Common.sqlChar(caseNo) +
					" and differenceKind = " + Common.sqlChar(differenceKind) +
					"";
			rs = db.querySQL(querySQL);
	
			while (rs.next()) {
				if ("Y".equals(verify)) {		
					updateSQL = " update UNTBU_MANAGE set" +
							" usearea = usearea + " + rs.getDouble("adjustArea") +                
							" where 1 = 1 " + 
							" and enterorg = " + Common.sqlChar(enterOrg) +
							" and ownership = " + Common.sqlChar(ownership) +
							" and differencekind = " + Common.sqlChar(differenceKind) +
							" and propertyno = " + Common.sqlChar(rs.getString("propertyNo")) +
							" and serialno = " + Common.sqlChar(rs.getString("serialNo")) +
							" and serialno1 = (select max(a.serialno1) from UNTBU_MANAGE a " +
							"                 	where a.enterorg = UNTBU_MANAGE.enterorg " +
							"                   	and a.ownership = UNTBU_MANAGE.ownership " +
							"						and a.differencekind = UNTBU_MANAGE.differencekind " +
							"						and a.propertyno = UNTBU_MANAGE.propertyno " +
							"						and a.serialno = UNTBU_MANAGE.serialno) " +
							"";
				} else {
					updateSQL = " update UNTBU_MANAGE set" +
							" usearea = usearea - " + rs.getDouble("adjustArea") +	                
							" where 1 = 1 " + 
							" and enterorg = " + Common.sqlChar(enterOrg) +
							" and ownership = " + Common.sqlChar(ownership) +
							" and differencekind = " + Common.sqlChar(differenceKind) +
							" and propertyno = " + Common.sqlChar(rs.getString("propertyNo")) +
							" and serialno = " + Common.sqlChar(rs.getString("serialNo")) +
							" and serialno1 = (select max(a.serialno1) from UNTBU_MANAGE a " +
							"                 	where a.enterorg = UNTBU_MANAGE.enterorg " +
							"                   	and a.ownership = UNTBU_MANAGE.ownership " +
							"						and a.differencekind = UNTBU_MANAGE.differencekind " +
							"						and a.propertyno = UNTBU_MANAGE.propertyno " +
							"						and a.serialno = UNTBU_MANAGE.serialno) " +
							"";
				}
				db.excuteSQL(updateSQL);
			}
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			db.closeAll();
		}  
	}

	/**
	 * @deprecated untbu021f 已調用 此method 應也隨著測試完成後刪除
	 */
	public void approveRe()throws Exception{	
		Database db = new Database();
		String enterOrgQuery = "";
		String ownershipQuery = "";
		String caseNoQuery = "";
		String differenceKindQuery = "";
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
			String sql ="select a.caseNo, b.enterOrg, b.ownership, b.propertyNo, b.serialNo, a.differenceKind," +
					" b.oldBookValue, b.oldCArea, b.oldSArea, b.oldArea," +
					" b.oldHoldNume, b.oldHoldDeno, b.oldHoldArea, b.cause," +
					" a.editDate, a.editTime, a.adjustDate" +
					" from untbu_AdjustProof a, untbu_AdjustDetail b " +
					" where 1=1 " +
					" and a.enterOrg = b.enterOrg and a.ownership = b.ownership and a.caseNo = b.caseNo" +
					" and a.enterOrg = " + Common.sqlChar(enterOrg) +
					" and a.ownership = " + Common.sqlChar(ownership) +
					" and a.caseNo = " + Common.sqlChar(caseNo) +
					" and a.differenceKind = " + Common.sqlChar(differenceKind) +	    			
					" order by b.enterOrg, b.ownership, b.propertyNo, b.serialNo" +
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
				//該減損單之明細資料,存在未審核的「建物減損單明細檔UNTBU_ReduceDetail」資料，則提示使用者
				reduceSql = "select count(*) count from untbu_reduceDetail " +
						" where enterOrg="+Common.sqlChar(enterOrgQuery)+" and ownership="+Common.sqlChar(ownershipQuery)+" and differenceKind="+Common.sqlChar(differenceKindQuery)+" and propertyNo="+Common.sqlChar(propertyNoQuery)+" and serialNo="+Common.sqlChar(serialNoQuery)+" and verify='N'";
				reduceCount = MaxClosingYM.getCountValues(reduceSql);

				//該減損單之明細資料,存在未審核的「建物增減值單明細檔UNTBU_AdjustDetail」資料，則提示使用者
				adjustSql = "select count(*) count from untbu_adjustDetail " +
						" where enterOrg="+Common.sqlChar(enterOrgQuery)+" and ownership="+Common.sqlChar(ownershipQuery)+" and differenceKind="+Common.sqlChar(differenceKindQuery)+" and propertyNo="+Common.sqlChar(propertyNoQuery)+" and serialNo="+Common.sqlChar(serialNoQuery)+" and verify='N'";
				adjustCount = MaxClosingYM.getCountValues(adjustSql);

				//------------------------------------------------------------------------------------------------
				reduceOriginal = rs.getString("editDate")+rs.getString("editTime")+rs.getString("adjustDate");
				//該減損單之(異動日期+異動時間+減損日期)≦減損單明細資料之其他減損單之最大的(異動日期+異動時間+減損日期)則提示使用者
				reduceMaxSql = "select count(*) count from untbu_reduceDetail " +
						" where enterOrg="+Common.sqlChar(enterOrgQuery)+" and ownership="+Common.sqlChar(ownershipQuery)+" and differenceKind="+Common.sqlChar(differenceKindQuery)+" and propertyNo="+Common.sqlChar(propertyNoQuery)+" and serialNo="+Common.sqlChar(serialNoQuery)+" and verify='Y' " +
						" and '" + reduceOriginal + "'<=(editDate + editTime + reduceDate) " ;

				reduceMax = MaxClosingYM.getCountValues(reduceMaxSql);

				//該減損單之(異動日期+異動時間+減損日期)≦減損單明細資料之其他增減值單之最大的(異動日期+異動時間+減損日期)則提示使用者
				adjustMaxSql = "select count(*) count from untbu_adjustDetail " +
						" where enterOrg="+Common.sqlChar(enterOrgQuery)+" and ownership="+Common.sqlChar(ownershipQuery)+" and differenceKind="+Common.sqlChar(differenceKindQuery)+" and propertyNo="+Common.sqlChar(propertyNoQuery)+" and serialNo="+Common.sqlChar(serialNoQuery)+" and verify='Y' " +
						" and '" + reduceOriginal + "'<=(editDate + editTime + adjustDate) " +
						" and caseNo!="+Common.sqlChar(caseNoQuery);			
				adjustMax = MaxClosingYM.getCountValues(adjustMaxSql);		
				//------------------------------------------------------------------------------------------------			
				if(closing.equals("N") && verify.equals("Y") && reduceCount.equals("0") && adjustCount.equals("0") && reduceMax.equals("0") && adjustMax.equals("0")){				
					//依據該減損單「入帳機關enterOrg＋權屬ownership＋電腦單號caseNo」設定
					strSQL += "update UNTBU_AdjustProof set verify ='N' " +
							" where enterOrg=" + Common.sqlChar(enterOrgQuery) +
							" and ownership=" + Common.sqlChar(ownershipQuery) +
							" and caseNo=" + Common.sqlChar(caseNoQuery) +
							" and differenceKind=" + Common.sqlChar(differenceKindQuery) +
							":;:";				
					strSQL += "update UNTBU_AdjustDetail set verify ='N' " +
							" where enterOrg=" + Common.sqlChar(enterOrgQuery) +
							" and ownership=" + Common.sqlChar(ownershipQuery) +
							" and caseNo=" + Common.sqlChar(caseNoQuery) +
							" and differenceKind=" + Common.sqlChar(differenceKindQuery) +
							":;:";	
					//依據減損單明細「入帳機關enterOrg＋權屬ownership＋財產編號propertyNo＋財產分號serialNo」設定	
					if(rs.getString("cause").equals("1")){

						String sql_1=" select adjustDate from untbu_adjustDetail" +
								" where enterOrg="+Common.sqlChar(enterOrgQuery)+" and ownership="+Common.sqlChar(ownershipQuery)+" and differenceKind="+Common.sqlChar(differenceKindQuery)+" and propertyNo="+Common.sqlChar(propertyNoQuery)+" and serialNo="+Common.sqlChar(serialNoQuery)+" and verify='Y'" +
								" and caseNo!=" + Common.sqlChar(caseNoQuery) +
								" order by adjustDate";				
						ResultSet rs_1 = db.querySQL(sql_1);

						while (rs_1.next()){
							strAdjustDate=rs_1.getString("adjustDate");
						}

						strSQL += "update UNTBU_Building set" +
								" bookValue=" + Common.sqlChar(rs.getString("oldBookValue")) +","+
								" appraiseDate=" + Common.sqlChar(strAdjustDate) +
								" where enterOrg=" + Common.sqlChar(enterOrgQuery) +
								" and ownership=" + Common.sqlChar(ownershipQuery) +
								" and differenceKind=" + Common.sqlChar(differenceKindQuery) +
								" and propertyNo=" + Common.sqlChar(propertyNoQuery) +
								" and serialNo=" + Common.sqlChar(serialNoQuery) +
								":;:";				
					}else {
						strSQL += "update UNTBU_Building set" +
								" CArea=" + Common.sqlChar(rs.getString("oldCArea")) +","+
								" SArea=" + Common.sqlChar(rs.getString("oldSArea")) +","+
								" area=" + Common.sqlChar(rs.getString("oldArea")) +","+
								" holdNume=" + Common.sqlChar(rs.getString("oldHoldNume")) +","+
								" holdDeno=" + Common.sqlChar(rs.getString("oldHoldDeno")) +","+
								" holdArea=" + Common.sqlChar(rs.getString("oldHoldArea")) +","+
								" bookValue=" + Common.sqlChar(rs.getString("oldBookValue")) +
								" where enterOrg=" + Common.sqlChar(enterOrgQuery) +
								" and ownership=" + Common.sqlChar(ownershipQuery) +
								" and differenceKind=" + Common.sqlChar(differenceKindQuery) +
								" and propertyNo=" + Common.sqlChar(propertyNoQuery) +
								" and serialNo=" + Common.sqlChar(serialNoQuery) +
								":;:";
					}							

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
					}else if(!reduceMax.equals("0")){				
						setErrorMsg("並非最後一筆入帳(減損)的資料，無法回復入帳！");
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