package unt.la;

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
 * <br/>程式目的：土地增減值作業－增減值單資料
 * <br/>程式代號：UNTLA018F
 * <br/>程式日期：0940909
 * <br/>程式作者：judy
 * <br/>--------------------------------------------------------
 * <br/>修改作者　　     修改日期　　　			修改目的
 * <br/>
 * <br/>--------------------------------------------------------
 */

public class UNTLA018F extends UNTLA018Q{

	/**
	 * 如果檔案有維護到的欄位，要來這裡下變數設定
	 */
	String enterOrg;
	String enterOrgName;
	String ownership;
	String caseNo;
	String caseName;
	String writeDate;
	String writeUnit;
	String proofDoc;
	String proofNo;
	String manageNo;
	String summonsNo;
	String adjustDate;
	String approveOrg;
	String approveDate;
	String approveDoc;
	String verify;
	String notes;
	
	String strAdjustDate;
	
	
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
	
	String verifyError;
	
	public String getVerifyError(){ return checkGet(verifyError); }
	public void setVerifyError(String s){ verifyError=checkSet(s); }
	
	String closing;
	public String getClosing(){ return checkGet(closing); }
	public void setClosing(String s){ closing=checkSet(s); }
	
	
	private String differenceKind;
	private String engineeringNo;
	private String differenceKindName;
	private String engineeringNoName;
	private String proofYear;
	private String editID;
	private String editDate;
	private String editTime;
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
	public String getDifferenceKindName() {return checkGet(differenceKindName);}
	public void setDifferenceKindName(String differenceKindName) {this.differenceKindName = checkSet(differenceKindName);}
	public String getEngineeringNoName() {return checkGet(engineeringNoName);}
	public void setEngineeringNoName(String engineeringNoName) {this.engineeringNoName = checkSet(engineeringNoName);}
	
	private String reVerify;
	public String getReVerify() {return reVerify;}
	public void setReVerify(String reVerify) {this.reVerify = reVerify;}
	
	private String summonsDate;
	public String getSummonsDate() {return checkGet(summonsDate);}
	public void setSummonsDate(String summonsDate) {this.summonsDate = checkSet(summonsDate);}	
	
	private String bookvalueFlag;
	public String getBookvalueFlag() { return checkGet(bookvalueFlag); }
	public void setBookvalueFlag(String bookvalueFlag) { this.bookvalueFlag = checkSet(bookvalueFlag); }
	

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
			proofno = MaxClosingYM.getMaxProofNo("UNTLA_ADJUSTPROOF",enterOrg,ownership,this.getProofYear());
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
			String sql = "select proofyear from UNTLA_ADJUSTPROOF where enterorg=" + Common.sqlChar(enterorg) +
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
	
	//傳回執行insert前之檢查sql
	protected String[][] getInsertCheckSQL(){
		setProofNo(MaxClosingYM.getMaxProofNo("UNTLA_ADJUSTPROOF",enterOrg,ownership,this.getProofYear()));
		
		String[][] checkSQLArray = new String[1][4];
	 	checkSQLArray[0][0]=" select count(*) as checkResult from UNTLA_AdjustProof where 1=1 " + 
			" and enterOrg = " + Common.sqlChar(enterOrg) + 
			" and ownership = " + Common.sqlChar(ownership) + 
			" and caseNo = " + Common.sqlChar(caseNo) +
			" and differenceKind = " + Common.sqlChar(differenceKind) + 
			"";
		checkSQLArray[0][1]=">";
		checkSQLArray[0][2]="0";
		checkSQLArray[0][3]="該筆資料已重複，請重新輸入！";
		return checkSQLArray;
	}


	//傳回insert sql
	protected String[] getInsertSQL(){
	//	setProofNo(MaxClosingYM.getMaxProofNo("UNTLA_AdjustProof",enterOrg,ownership));
		String[] execSQLArray = new String[1];
		execSQLArray[0] = new SQLCreator()._obtainSQLforInsert("UNTLA_ADJUSTPROOF", getPKMap(), getRecordMap());
		return execSQLArray;
	}

	public String queryOldVerify() {
		Database db = null;
		try {
			db = new Database();
			return db.getLookupField("select verify from UNTLA_ADJUSTPROOF where enterorg = " + Common.sqlChar(this.getEnterOrg()) + " and caseno = " + Common.sqlChar(this.getCaseNo()));
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
		String[] tmpchk = null;
		String oldVerify = this.queryOldVerify();
		
		if ("N".equals(oldVerify) && "Y".equals(this.getVerify())) {
			// 入帳
			tmpchk = new String[5];
			tmpchk[0] = " select count(case when isnull(z.adjustdate, '') != '' then 1 else null end) as checkResult from UNTLA_ADJUSTDETAIL  a "
					  + " OUTER APPLY ( " 
					  + " select top 1 b.adjustdate  from UNTLA_ADJUSTDETAIL b  "
					  + " where a.enterorg=b.enterorg and a.ownership = b.ownership and a.differencekind=b.differencekind and a.propertyno=b.propertyno and a.serialno=b.serialno  "
					  + " and verify ='Y' and b.caseno != a.caseno and b.adjustdate >= " + ut._transToCE_Year(this.getAdjustDate())
					  + " ) as z "
					  + " where a.enterorg = " + Common.sqlChar(this.getEnterOrg()) + " and a.caseno = " + Common.sqlChar(this.getCaseNo());
			tmpchk[1] = "!=";
			tmpchk[2] = "0";
			tmpchk[3] = "入帳失敗, 檢查到已有入帳的土地增減值資料其入帳日期大於等於此單據入帳日期";
			tmpchk[4] = "N";
			sqls.add(tmpchk);
			
		} else if ("Y".equals(oldVerify) && "Y".equals(this.getReVerify())) {
			// 回復入帳
			tmpchk = new String[5];
			tmpchk[0] = " select count(*) as checkResult from UNTLA_ADJUSTDETAIL  a "
					  + " inner join UNTLA_REDUCEDETAIL b  " 
					  + " on a.enterorg=b.enterorg and a.ownership=b.ownership and a.differencekind=b.differencekind  "
					  + " and a.propertyno=b.propertyno and a.serialno=b.serialno  "
					  + " where a.enterorg = " + Common.sqlChar(this.getEnterOrg()) + " and a.caseno =" + Common.sqlChar(this.getCaseNo());
			tmpchk[1] = "!=";
			tmpchk[2] = "0";
			tmpchk[3] = "回復入帳失敗, 檢查到已有土地資料於減損作業中登打,請先移除該減損單";
			tmpchk[4] = "N";
			sqls.add(tmpchk);
			
			tmpchk = new String[5];
			tmpchk[0] = " select count(case when isnull(z.adjustdate, '') != '' then 1 else null end) as checkResult from UNTLA_ADJUSTDETAIL  a "
					  + " OUTER APPLY ( " 
					  + " select top 1 b.adjustdate  from UNTLA_ADJUSTDETAIL b  "
					  + " where a.enterorg=b.enterorg and a.ownership = b.ownership and a.differencekind=b.differencekind and a.propertyno=b.propertyno and a.serialno=b.serialno  "
					  + " and verify ='Y' and b.adjustdate >= a.adjustdate and b.caseno != a.caseno" 
					  + " ) as z "
					  + " where a.enterorg = " + Common.sqlChar(this.getEnterOrg()) + " and a.caseno = " + Common.sqlChar(this.getCaseNo());
			tmpchk[1] = "!=";
			tmpchk[2] = "0";
			tmpchk[3] = "回復入帳失敗, 已有土地資料於其他較新的增減值單中入帳";
			tmpchk[4] = "N";
			sqls.add(tmpchk);
		}
		
		return sqls.toArray(new String[sqls.size()][5]);
	}

	protected String[] getUpdateSQL(){
		if(getReVerify().equals("Y")){	setVerify("N");}
		
		String[] execSQLArray = new String[2];
		execSQLArray[0] = new SQLCreator()._obtainSQLforUpdate("UNTLA_ADJUSTPROOF", getPKMap(), getRecordMap());
		execSQLArray[1] = updateTable();
		return execSQLArray;
	}


	//傳回delete sql
	protected String[] getDeleteSQL(){ 
		String[] execSQLArray = new String[2];
		//刪除 UNTLA_AdjustProof TABLE
		execSQLArray[0] = new SQLCreator()._obtainSQLforDelete("UNTLA_ADJUSTPROOF", getPKMap(), getRecordMap());
		//刪除 UNTLA_AdjustDetail TABLE
		execSQLArray[1]=" delete UNTLA_AdjustDetail where " +
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

	public UNTLA018F  queryOne() throws Exception{
		Database db = new Database();
		UNTLA018F obj = this;
		try {
			String sql=" select a.*, " +
					" (select b.organSName from SYSCA_Organ b where b.organID = a.enterOrg) as organSName " + 
					" from UNTLA_AdjustProof a where 1=1 " +
					" and a.enterOrg = " + Common.sqlChar(enterOrg) +
					" and a.ownership = " + Common.sqlChar(ownership) +
					" and a.caseNo = " + Common.sqlChar(caseNo) +
					" and a.differenceKind = " + Common.sqlChar(differenceKind) +
					" order by a.enterOrg, a.ownership, a.caseNo  ";
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
				obj.setEngineeringNoName(ul._getEngineeringNoName(rs.getString("EnterOrg"),rs.getString("EngineeringNo")).toString());
				obj.setSummonsDate(ul._transToROC_Year(rs.getString("summonsDate")));
				
				// 問題單2272 查詢有單時，須看單據裡有沒有減值後原值為0的資料
				StringBuilder sql2 = new StringBuilder();
				sql2.append(" SELECT                                            ").
				append(" (                                                      ").
				append("     SELECT COUNT(*)                                    ").
				append("     FROM UNTLA_ADJUSTDETAIL b                          ").
				append("     WHERE a.enterorg = b.enterorg                      ").
				append("           AND a.ownership = b.ownership                ").
				append("           AND a.differencekind = b.differencekind      ").
				append("           AND a.caseno = b.caseno                      ").
				append("           AND b.newbookvalue = 0                       ").
				append(" ) AS laCount,                                          ").
				append(" (                                                      ").
				append("     SELECT COUNT(*)                                    ").
				append("     FROM UNTRF_ADJUSTDETAIL b                          ").
				append("     WHERE a.enterorg = b.enterorg                      ").
				append("           AND a.ownership = b.ownership                ").
				append("           AND a.differencekind = b.differencekind      ").
				append("           AND a.caseno = b.caseno                      ").
				append("           AND b.newbookvalue = 0                       ").
				append(" ) AS rfCount,                                          ").
				append(" (                                                      ").
				append("     SELECT COUNT(*)                                    ").
				append("     FROM UNTBU_ADJUSTDETAIL b                          ").
				append("     WHERE a.enterorg = b.enterorg                      ").
				append("           AND a.ownership = b.ownership                ").
				append("           AND a.differencekind = b.differencekind      ").
				append("           AND a.caseno = b.caseno                      ").
				append("           AND b.newbookvalue = 0                       ").
				append(" ) AS buCount,                                          ").
				append(" (                                                      ").
				append("     SELECT COUNT(*)                                    ").
				append("     FROM UNTMP_ADJUSTDETAIL b                          ").
				append("     WHERE a.enterorg = b.enterorg                      ").
				append("           AND a.ownership = b.ownership                ").
				append("           AND a.differencekind = b.differencekind      ").
				append("           AND a.caseno = b.caseno                      ").
				append("           AND b.newbookvalue = 0                       ").
				append(" ) AS mpCount                                           ").
				append(" FROM UNTLA_ADJUSTPROOF a                               ").
				append(" WHERE a.caseno = ").append(Common.sqlChar(caseNo)).
				append("       AND a.enterorg = ").append(Common.sqlChar(enterOrg)).
				append("       AND a.ownership = ").append(Common.sqlChar(ownership)).
				append("       AND a.differencekind = ").append(Common.sqlChar(differenceKind));
				
				rs = db.querySQL(sql2.toString());
				if (rs.next()){ 
					int laCount = rs.getInt("laCount");
					int rfCount = rs.getInt("rfCount");
					int buCount = rs.getInt("buCount");
					int mpCount = rs.getInt("mpCount");
					
					if (laCount > 0 || rfCount > 0 || buCount > 0 || mpCount > 0) {
						obj.setBookvalueFlag("Y");
					} else {
						obj.setBookvalueFlag("N");
					}
				}
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
	 * TODO   判斷增減值日期　不得小於　上次增減值日期	 
	 * @deprecated 
	 */
	public boolean checkVerify(){
		boolean result = false;
		UNTCH_Tools ut = new UNTCH_Tools();
		/*　2008/08/21   判斷增減值日期　不得小於　上次增減值日期	 */
		String q_sql = " select count(*) as checkResult from  " + "\n"
				+ " ( " + "\n"
				+ " select a.enterorg ,a.ownership ,b.propertyno ,b.serialno " + "\n"
				+ " from UNTLA_AdjustProof a ,UNTLA_AdjustDetail b  " + "\n"
				+ " where 1=1  " + "\n"
				+ " and a.enterorg = b.enterorg and a.ownership = b.ownership and a.caseno = b.caseno " + "\n"
				+ " and a.enterorg = " + Common.sqlChar(enterOrg) + "\n"
				+ " and a.ownership = " + Common.sqlChar(ownership) + "\n"
				+ " and a.verify = 'Y' " + "\n"
				+ " and exists ( select * from UNTLA_AdjustDetail c  " + "\n"
				+ "              where 1=1 " + "\n"
				+ "              and b.enterorg = c.enterorg and b.ownership = c.ownership and b.propertyno=c.propertyno and b.serialno = c.serialno " + "\n"
				+ "              and c.caseno= " + Common.sqlChar(caseNo) + "\n"
				+ "            ) " + "\n"
				+ " group by a.enterorg ,a.ownership ,b.propertyno ,b.serialno " + "\n"
				+ " having max(a.adjustdate) > " + Common.sqlChar(ut._transToCE_Year(getAdjustDate())) + "\n"
				+ " ) a " + "\n"
				+ " ";

		System.out.println(q_sql);
		if(getVerify().equals("Y") && !getReVerify().equals("Y")){
			if(!"0".equals(getTableFile.query_value(q_sql, "checkResult", "0"))){
				setVerifyError("Y");    	   
				setStateUpdateError();
				setErrorMsg("入帳設定錯誤，入帳日期應大於上一筆入帳日期！");
				result = true;
			}
		}	
		return result;
	}


	private String updateTable(){
		String sql = "";
		String querySQL = "";

		UNTCH_Tools ut = new UNTCH_Tools();


		if(getReVerify().equals("Y")){
			sql += " update UNTLA_AdjustDetail set" +
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
					" oldarea,oldholdnume, oldholddeno, oldholdarea," +
					" oldBookValue, oldNetValue, oldbookunit" +
					" from UNTLA_ADJUSTDETAIL " +
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
					sql += " update UNTLA_LAND set" +
							" area = " + Common.sqlChar(rs.getString("oldarea")) + "," +
							" holdNume = " + Common.sqlChar(rs.getString("oldholdnume")) + "," +
							" holdDeno = " + Common.sqlChar(rs.getString("oldholddeno")) + "," +
							" holdArea = " + Common.sqlChar(rs.getString("oldholdarea")) + "," +
							" bookValue = " + Common.sqlChar(rs.getString("oldBookValue")) + "," +
							" netValue = " + Common.sqlChar(rs.getString("oldNetValue")) + "," +
							" bookUnit=" + Common.sqlChar(rs.getString("oldbookunit")) +","+
							" netUnit=" + Common.sqlChar(rs.getString("oldbookunit")) +","+
							" editID = " + Common.sqlChar(getEditID()) + "," +
							" editDate = " + Common.sqlChar(getEditDate()) + "," +
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
			}catch(Exception e){
				e.printStackTrace();
			}finally{
				db.closeAll();
			}

		}else if("Y".equals(this.getVerify())){
			sql += " update UNTLA_AdjustDetail set" +
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
					" newarea, newholdnume, newholddeno, newholdarea," +
					" newBookValue, newNetValue, newbookunit" +
					" from UNTLA_ADJUSTDETAIL " +
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
					sql += " update UNTLA_LAND set" +
							" area = " + Common.sqlChar(rs.getString("newarea")) + "," +
							" holdNume = " + Common.sqlChar(rs.getString("newholdnume")) + "," +
							" holdDeno = " + Common.sqlChar(rs.getString("newholddeno")) + "," +
							" holdArea = " + Common.sqlChar(rs.getString("newholdarea")) + "," +
							" bookValue = " + Common.sqlChar(rs.getString("newBookValue")) + "," +
							" netValue = " + Common.sqlChar(rs.getString("newNetValue")) + "," +
							" bookUnit=" + Common.sqlChar(rs.getString("newbookunit")) +","+
							" netUnit=" + Common.sqlChar(rs.getString("newbookunit")) +","+
							" editID = " + Common.sqlChar(getEditID()) + "," +
							" editDate = " + Common.sqlChar(getEditDate()) + "," +
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
			}catch(Exception e){
				e.printStackTrace();
			}finally{
				db.closeAll();
			}  


		}

		return sql;
	}

	//TODO
	//回復審核設定
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
			String sql ="select a.caseNo, a.differenceKind, b.enterOrg, b.ownership, b.propertyNo, b.serialNo," +
					" b.oldBookUnit, b.oldBookValue, b.oldArea," +
					" b.oldHoldNume, b.oldHoldDeno, b.oldHoldArea, b.cause," +
					" a.editDate, a.editTime, a.adjustDate" +
					" from untla_AdjustProof a, untla_AdjustDetail b " +
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
				//該減損單之明細資料,存在未審核的「土地減損單明細檔UNTLA_ReduceDetail」資料，則提示使用者
				reduceSql = "select count(*) count from untla_reduceDetail " +
						" where enterOrg="+Common.sqlChar(enterOrgQuery)+" and ownership="+Common.sqlChar(ownershipQuery)+" and differenceKind="+Common.sqlChar(differenceKindQuery)+" and propertyNo="+Common.sqlChar(propertyNoQuery)+" and serialNo="+Common.sqlChar(serialNoQuery)+" and verify='N'";
				reduceCount = MaxClosingYM.getCountValues(reduceSql);				
				//該減損單之明細資料,存在未審核的「土地增減值單明細檔UNTLA_AdjustDetail」資料，則提示使用者
				adjustSql = "select count(*) count from untla_adjustDetail " +
						" where enterOrg="+Common.sqlChar(enterOrgQuery)+" and ownership="+Common.sqlChar(ownershipQuery)+" and differenceKind="+Common.sqlChar(differenceKindQuery)+" and propertyNo="+Common.sqlChar(propertyNoQuery)+" and serialNo="+Common.sqlChar(serialNoQuery)+" and verify='N'";
				adjustCount = MaxClosingYM.getCountValues(adjustSql);		
				//------------------------------------------------------------------------------------------------
				reduceOriginal = rs.getString("editDate")+rs.getString("editTime")+rs.getString("adjustDate");
				//該減損單之(異動日期+異動時間+減損日期)≦減損單明細資料之其他減損單之最大的(異動日期+異動時間+減損日期)則提示使用者
				reduceMaxSql = "select count(*) count from untla_reduceDetail " +
						" where enterOrg="+Common.sqlChar(enterOrgQuery)+" and ownership="+Common.sqlChar(ownershipQuery)+" and differenceKind="+Common.sqlChar(differenceKindQuery)+" and propertyNo="+Common.sqlChar(propertyNoQuery)+" and serialNo="+Common.sqlChar(serialNoQuery)+" and verify='Y' " +
						" and '" + reduceOriginal + "'<=(editDate + editTime + reduceDate) " ;

				reduceMax = MaxClosingYM.getCountValues(reduceMaxSql);

				//該減損單之(異動日期+異動時間+減損日期)≦減損單明細資料之其他增減值單之最大的(異動日期+異動時間+減損日期)則提示使用者
				adjustMaxSql = "select count(*) count from untla_adjustDetail " +
						" where enterOrg="+Common.sqlChar(enterOrgQuery)+" and ownership="+Common.sqlChar(ownershipQuery)+" and differenceKind="+Common.sqlChar(differenceKindQuery)+" and propertyNo="+Common.sqlChar(propertyNoQuery)+" and serialNo="+Common.sqlChar(serialNoQuery)+" and verify='Y' " +
						" and '" + reduceOriginal + "'<=(editDate + editTime + adjustDate) " +
						" and caseNo!="+Common.sqlChar(caseNoQuery);

				adjustMax = MaxClosingYM.getCountValues(adjustMaxSql);	
				//------------------------------------------------------------------------------------------------			
				if(closing.equals("N") && verify.equals("Y") && reduceCount.equals("0") && adjustCount.equals("0") && reduceMax.equals("0") && adjustMax.equals("0")){

					//依據該減損單「入帳機關enterOrg＋權屬ownership＋電腦單號caseNo」設定
					strSQL += "update UNTLA_AdjustProof set verify ='N' " +
							" where enterOrg=" + Common.sqlChar(enterOrgQuery) +
							" and ownership=" + Common.sqlChar(ownershipQuery) +
							" and caseNo=" + Common.sqlChar(caseNoQuery) +
							" and differenceKind=" + Common.sqlChar(differenceKindQuery) +
							":;:";				
					strSQL += "update UNTLA_AdjustDetail set verify ='N' " +
							" where enterOrg=" + Common.sqlChar(enterOrgQuery) +
							" and ownership=" + Common.sqlChar(ownershipQuery) +
							" and caseNo=" + Common.sqlChar(caseNoQuery) +
							" and differenceKind=" + Common.sqlChar(differenceKindQuery) +
							":;:";	
					//依據減損單明細「入帳機關enterOrg＋權屬ownership＋財產編號propertyNo＋財產分號serialNo」設定	
					if(rs.getString("cause").equals("1")){
						strSQL += "update UNTLA_Land set" +
								" bookUnit=" + Common.sqlChar(rs.getString("oldBookUnit")) +","+
								" bookValue=" + Common.sqlChar(rs.getString("oldBookValue")) +
								" where enterOrg=" + Common.sqlChar(enterOrgQuery) +
								" and ownership=" + Common.sqlChar(ownershipQuery) +
								" and differenceKind=" + Common.sqlChar(differenceKindQuery) +
								" and propertyNo=" + Common.sqlChar(propertyNoQuery) +
								" and serialNo=" + Common.sqlChar(serialNoQuery) +
								":;:";				
					}else if(rs.getString("cause").equals("2")){

						String sql_1=" select adjustDate from untla_adjustDetail" +
								" where enterOrg="+Common.sqlChar(enterOrgQuery)+" and ownership="+Common.sqlChar(ownershipQuery)+" and differenceKind="+Common.sqlChar(differenceKindQuery)+" and propertyNo="+Common.sqlChar(propertyNoQuery)+" and serialNo="+Common.sqlChar(serialNoQuery)+" and verify='Y'" +
								" and caseNo!=" + Common.sqlChar(caseNoQuery) +
								" order by adjustDate";				
						ResultSet rs_1 = db.querySQL(sql_1);

						while (rs_1.next()){
							strAdjustDate=rs_1.getString("adjustDate");
						}		

						strSQL += "update UNTLA_Land set" +
								" bookUnit=" + Common.sqlChar(rs.getString("oldBookUnit")) +","+
								" bookValue=" + Common.sqlChar(rs.getString("oldBookValue")) +","+
								" appraiseDate=" + Common.sqlChar(strAdjustDate) +
								" where enterOrg=" + Common.sqlChar(enterOrgQuery) +
								" and ownership=" + Common.sqlChar(ownershipQuery) +
								" and differenceKind=" + Common.sqlChar(differenceKindQuery) +
								" and propertyNo=" + Common.sqlChar(propertyNoQuery) +
								" and serialNo=" + Common.sqlChar(serialNoQuery) +
								":;:";
					}else if(rs.getString("cause").equals("3")){
						strSQL += "update UNTLA_Land set" +
								" area=" + Common.sqlChar(rs.getString("oldArea")) +","+
								" holdNume=" + Common.sqlChar(rs.getString("oldHoldNume")) +","+
								" holdDeno=" + Common.sqlChar(rs.getString("oldHoldDeno")) +","+
								" holdArea=" + Common.sqlChar(rs.getString("oldHoldArea")) +","+
								" bookUnit=" + Common.sqlChar(rs.getString("oldBookUnit")) +","+
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

	//異動 UNTLA_Land 檔案
	//設迴圈 取 detail 的 propertyNo , serialNo , cause  where verify = 'N' 資料
	protected String updLand(){
		Database db = new Database();
		String checkDetail="";
		String sqlLand = "";
		String sql="";
		try {
			sql="select a.propertyNo, a.serialNo, a.newBookUnit, a.newBookValue, a.newArea, a.newHoldNume, a.newHoldDeno, a.newHoldArea, " +
					" a.cause " +
					" from UNTLA_AdjustDetail a where 1=1 " +
					" and enterOrg = " + Common.sqlChar(enterOrg) +
					" and ownership = " + Common.sqlChar(ownership) +
					" and caseNo = " + Common.sqlChar(caseNo) +
					" and differenceKind = " + Common.sqlChar(differenceKind) +
					"";
			ResultSet rs = db.querySQL(sql);
			while (rs.next()){
				checkDetail="Y";
				if(rs.getString("cause").equals("1")){
					sqlLand += " update UNTLA_Land set "+
							" bookUnit = " + Common.sqlChar(rs.getString("newBookUnit")) + "," +
							" bookValue = " + Common.sqlChar(rs.getString("newBookValue")) +
							" where 1=1 " + 
							" and enterOrg = " + Common.sqlChar(enterOrg) +
							" and ownership = " + Common.sqlChar(ownership) +
							" and differenceKind = " + Common.sqlChar(differenceKind) +
							" and propertyNo = " + Common.sqlChar(rs.getString("propertyNo")) +
							" and serialNo = " + Common.sqlChar(rs.getString("serialNo")) +
							":;:"; 
				}else if(rs.getString("cause").equals("2")){
					sqlLand += " update UNTLA_Land set "+
							" bookUnit = " + Common.sqlChar(rs.getString("newBookUnit")) + "," +
							" bookValue = " + Common.sqlChar(rs.getString("newBookValue")) + "," +
							" appraiseDate = " + Common.sqlChar(getAdjustDate())+
							" where 1=1 " + 
							" and enterOrg = " + Common.sqlChar(enterOrg) +
							" and ownership = " + Common.sqlChar(ownership) +
							" and differenceKind = " + Common.sqlChar(differenceKind) +
							" and propertyNo = " + Common.sqlChar(rs.getString("propertyNo")) +
							" and serialNo = " + Common.sqlChar(rs.getString("serialNo")) +
							":;:"; 
				}else{
					sqlLand += " update UNTLA_Land set "+
							" area = " + Common.sqlChar(rs.getString("newArea")) + "," +
							" holdNume = " + Common.sqlChar(rs.getString("newHoldNume")) + "," +
							" holdDeno = " + Common.sqlChar(rs.getString("newHoldDeno")) + "," +
							" holdArea = " + Common.sqlChar(rs.getString("newHoldArea")) + "," +
							" bookUnit = " + Common.sqlChar(rs.getString("newBookUnit")) + "," +
							" bookValue = " + Common.sqlChar(rs.getString("newBookValue"))+
							" where 1=1 " + 
							" and enterOrg = " + Common.sqlChar(enterOrg) +
							" and ownership = " + Common.sqlChar(ownership) +
							" and differenceKind = " + Common.sqlChar(differenceKind) +
							" and propertyNo = " + Common.sqlChar(rs.getString("propertyNo")) +
							" and serialNo = " + Common.sqlChar(rs.getString("serialNo")) +
							":;:"; 
				}
			}
			if(!checkDetail.equals("Y")){
				setVerifyError("Y");
				setErrorMsg("該筆增減值單之明細資料標籤要有資料才能做此入帳設定！");
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.closeAll();
		}
		return sqlLand;
	}
	
	public void updateManage(String enterOrg, String ownership, String caseNo, String differenceKind, String verify) {
		String querySQL = "";
		String updateSQL = "";
		Database db = null;
		ResultSet rs = null;

		try {
			db = new Database();
			querySQL = "select a.propertyNo, a.serialNo, a.adjustArea " +
					" from UNTLA_AdjustDetail a where 1=1 " +
					" and enterOrg = " + Common.sqlChar(enterOrg) +
					" and ownership = " + Common.sqlChar(ownership) +
					" and caseNo = " + Common.sqlChar(caseNo) +
					" and differenceKind = " + Common.sqlChar(differenceKind) +
					"";
			rs = db.querySQL(querySQL);
	
			while (rs.next()) {
				if ("Y".equals(verify)) {		
					updateSQL = " update UNTLA_MANAGE set" +
							" usearea = usearea + " + rs.getDouble("adjustArea") +                
							" where 1 = 1 " + 
							" and enterorg = " + Common.sqlChar(enterOrg) +
							" and ownership = " + Common.sqlChar(ownership) +
							" and differencekind = " + Common.sqlChar(differenceKind) +
							" and propertyno = " + Common.sqlChar(rs.getString("propertyNo")) +
							" and serialno = " + Common.sqlChar(rs.getString("serialNo")) +
							" and serialno1 = (select max(a.serialno1) from UNTLA_MANAGE a " +
							"                 	where a.enterorg = UNTLA_MANAGE.enterorg " +
							"                   	and a.ownership = UNTLA_MANAGE.ownership " +
							"						and a.differencekind = UNTLA_MANAGE.differencekind " +
							"						and a.propertyno = UNTLA_MANAGE.propertyno " +
							"						and a.serialno = UNTLA_MANAGE.serialno) " +
							"";
				} else {
					updateSQL = " update UNTLA_MANAGE set" +
							" usearea = usearea - " + rs.getDouble("adjustArea") +	                
							" where 1 = 1 " + 
							" and enterorg = " + Common.sqlChar(enterOrg) +
							" and ownership = " + Common.sqlChar(ownership) +
							" and differencekind = " + Common.sqlChar(differenceKind) +
							" and propertyno = " + Common.sqlChar(rs.getString("propertyNo")) +
							" and serialno = " + Common.sqlChar(rs.getString("serialNo")) +
							" and serialno1 = (select max(a.serialno1) from UNTLA_MANAGE a " +
							"                 	where a.enterorg = UNTLA_MANAGE.enterorg " +
							"                   	and a.ownership = UNTLA_MANAGE.ownership " +
							"						and a.differencekind = UNTLA_MANAGE.differencekind " +
							"						and a.propertyno = UNTLA_MANAGE.propertyno " +
							"						and a.serialno = UNTLA_MANAGE.serialno) " +
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
}