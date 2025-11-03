/*
*<br>程式目的：動產資料維護-增加單資料
*<br>程式代號：untmp001f
*<br>程式日期：0940928
*<br>程式作者：clive.chang
*<br>--------------------------------------------------------
*<br>修改作者　　修改日期　　　修改目的
*<br>--------------------------------------------------------
*/

package unt.mp;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import unt.ch.UNTCH_Tools;
import util.Common;
import util.Database;
import util.Datetime;
import util.MaxClosingYM;
import util.TCGHCommon;

public class UNTMP001F extends UNTMP001Q{

	private String summonsDate;
	public String getSummonsDate() {return checkGet(summonsDate);}
	public void setSummonsDate(String summonsDate) {this.summonsDate = checkSet(summonsDate);}
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
	String enterDate;
	String verify;
	String notes;
	String editID;
	String editDate;
	String oldVerify;
	String checkEnterOrg;
	
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
	public String getEnterDate(){ return checkGet(enterDate); }
	public void setEnterDate(String s){ enterDate=checkSet(s); }
	public String getVerify(){ return checkGet(verify); }
	public void setVerify(String s){ verify=checkSet(s); }
	public String getNotes(){ return checkGet(notes); }
	public void setNotes(String s){ notes=checkSet(s); }
	public String getEditID(){ return checkGet(editID); }
	public void setEditID(String s){ editID=checkSet(s); }
	public String getEditDate(){ return checkGet(editDate); }
	public void setEditDate(String s){ editDate=checkSet(s); }
	public String getOldVerify(){ return checkGet(oldVerify); }
	public void setOldVerify(String s){ oldVerify=checkSet(s); }
	public String getCheckEnterOrg(){ return checkGet(checkEnterOrg); }
	public void setCheckEnterOrg(String s){ checkEnterOrg=checkSet(s); }
	
	String verifyError;
	String approve="";
	
	public String getVerifyError(){ return checkGet(verifyError); }
	public void setVerifyError(String s){ verifyError=checkSet(s); }
	public String getApprove(){ return checkGet(approve); }
	public void setApprove(String s){ approve=checkSet(s); }
	
	
	String differenceKind;
	public String getDifferenceKind() {return checkGet(differenceKind);}
	public void setDifferenceKind(String differenceKind) {this.differenceKind = checkSet(differenceKind);}
	
	String proofYear;
	public String getProofYear() {return checkGet(proofYear);}
	public void setProofYear(String proofYear) {this.proofYear = checkSet(proofYear);}
	
	private String engineeringNo;
	public String getEngineeringNo() {return checkGet(engineeringNo);}
	public void setEngineeringNo(String engineeringNo) {this.engineeringNo = checkSet(engineeringNo);}


	//傳回執行insert前之檢查sql
	protected String[][] getInsertCheckSQL(){
		
		//取得增加單編號－號
		setProofNo(MaxClosingYM.getMaxProofNo("UNTMP_AddProof",enterOrg,ownership,this.getProofYear()));
		//===================
		String[][] checkSQLArray = new String[1][4];
	 	checkSQLArray[0][0]=" select count(*) as checkResult from UNTMP_AddProof where 1=1 " + 
			" and enterOrg = " + Common.sqlChar(enterOrg) + 
			" and ownership = " + Common.sqlChar(ownership) + 
			" and caseNo = " + Common.sqlChar(caseNo) +
			" and differenceKind = " + Common.sqlChar(differenceKind) + 
			"";
		checkSQLArray[0][1]=">";
		checkSQLArray[0][2]="0";
		checkSQLArray[0][3]="該筆資料己重複，請重新輸入！";
		return checkSQLArray;
	}


	//傳回insert sql
	protected String[] getInsertSQL(){
		String[] execSQLArray = new String[1];
		UNTCH_Tools ut = new UNTCH_Tools();
		execSQLArray[0]=" insert into UNTMP_ADDPROOF(" +
				" enterorg,"+
				" ownership,"+
				" caseno,"+
				" casename,"+
				" writedate,"+
				" writeunit,"+
				" proofdoc,"+
				" proofno,"+
				" manageno,"+
				" summonsno,"+
				" enterdate,"+
				" verify,"+
				" notes,"+
				" editid,"+
				" editdate,"+
				" edittime,"+
				" differencekind,"+
				" engineeringno," +
				" proofyear," +
				" summonsdate" +
			" )Values(" +
				Common.sqlChar(enterOrg) + "," +
				Common.sqlChar(ownership) + "," +
				Common.sqlChar(caseNo) + "," +
				Common.sqlChar(caseName) + "," +
				Common.sqlChar(ut._transToCE_Year(writeDate)) + "," +
				Common.sqlChar(writeUnit) + "," +
				Common.sqlChar(proofDoc) + "," +
				Common.sqlChar(proofNo) + "," +
				Common.sqlChar(manageNo) + "," +
				Common.sqlChar(summonsNo) + "," +
				Common.sqlChar(ut._transToCE_Year(enterDate)) + "," +
				Common.sqlChar(verify) + "," +
				Common.sqlChar(notes) + "," +
				Common.sqlChar(getEditID()) + "," +
				Common.sqlChar(ut._transToCE_Year(getEditDate())) + "," +
				Common.sqlChar(getEditTime()) + "," +
				Common.sqlChar(getDifferenceKind()) + "," +
				Common.sqlChar(getEngineeringNo()) + "," +
				Common.sqlChar(getProofYear()) + "," +
				Common.sqlChar(ut._transToCE_Year(getSummonsDate())) + ")" ;
		return execSQLArray;
	}

	//檢查update時，proofyear是否有改變
	private boolean checkProofyearChanged(String enterorg, String caseno, String newproofyear){
		boolean isChanged = false;
		String oldproofyear ="";
		ResultSet rs;
		Database db = new Database();
		try{
			String sql = "select proofyear from UNTMP_ADDPROOF where enterorg=" + Common.sqlChar(enterorg) +
									" and caseno=" + Common.sqlChar(caseno);
			rs = db.querySQL(sql);
			if(rs.next()){
				oldproofyear = rs.getString(1);
				if(!newproofyear.equals(oldproofyear)){
					isChanged =true;
				}
			}else{
				System.out.println("查無此增加單!");
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			db.closeAll();
		}
		return isChanged;
	}

	private String queryOldVerify() {
		Database db = null;
		try {
			db = new Database();
			return db.getLookupField("select verify from UNTMP_ADDPROOF where enterorg=" + Common.sqlChar(this.getEnterOrg()) + " and caseno = " + Common.sqlChar(this.getCaseNo()));
		} finally {
			if (db != null) {
				db.closeAll();
			}
		}
	}
	
	public String[][] getUpdateCheckSQLBeforeAction(){
		
		List<String[]> sqls = new ArrayList<String[]>();
		
		String oldVerify = this.queryOldVerify();
		
		if ("Y".equals(oldVerify) && "N".equals(this.getVerify())) {
			// 回復入帳時檢查 不可存在於 增減值單/減損單
			String[] tmpchk = new String[4];
			tmpchk[0] = " select count(*) as checkResult from UNTMP_MOVABLEDETAIL a "
					  + " where a.enterorg = " + Common.sqlChar(this.getEnterOrg()) 
					  + " and a.caseno = " + Common.sqlChar(this.getCaseNo()) 
					  + " and (EXISTS (select 1 from UNTMP_ADJUSTDETAIL b where a.enterorg = b.enterorg and a.ownership = b.ownership and a.differencekind = b.differencekind and a.propertyno = b.propertyno and a.serialno = b.serialno)"
					  + " or EXISTS (select 1 from UNTMP_REDUCEDETAIL b where a.enterorg = b.enterorg and a.ownership = b.ownership and a.differencekind = b.differencekind and a.propertyno = b.propertyno and a.serialno = b.serialno)"
					  + " or EXISTS (select 1 from UNTMP_MOVEDETAIL b where a.enterorg = b.enterorg and a.ownership = b.ownership and a.differencekind = b.differencekind and a.propertyno = b.propertyno and a.serialno = b.serialno)"
					  + " or EXISTS (select 1 from UNTMP_DEALDETAIL b where a.enterorg = b.enterorg and a.ownership = b.ownership and a.differencekind = b.differencekind and a.propertyno = b.propertyno and a.serialno = b.serialno)"
					  + " );";
			tmpchk[1] = "!=";
			tmpchk[2] = "0";
			tmpchk[3] = "動產資料已做過增減值或減損或移動或廢品處理作業，請先刪除增減值或減損或移動或廢品處理作業之資料再回此作業取消入帳！";
			sqls.add(tmpchk);
		}
		
		return sqls.toArray(new String[sqls.size()][4]);
	}

	//傳回執行update前之檢查sql
	protected String[][] getUpdateCheckSQL(){
		return super.getUpdateCheckSQL();
	}

	@Override
	protected String[] getUpdateSQL() throws Exception {
		
		setEditDate(Datetime.getYYYMMDD());
		setEditTime(Datetime.getHHMMSS());	
		
		List<String> sqls = new ArrayList<String>();
		UNTCH_Tools ut = new UNTCH_Tools();
		
		sqls.add(" update UNTMP_ADDPROOF set " +
				" casename = " + Common.sqlChar(caseName) + "," +
				" writedate = " + Common.sqlChar(ut._transToCE_Year(writeDate)) + "," +
				" writeunit = " + Common.sqlChar(writeUnit) + "," +
				" proofdoc = " + Common.sqlChar(proofDoc) + "," +
				" proofno = " + Common.sqlChar(proofNo) + "," +
				" manageno = " + Common.sqlChar(manageNo) + "," +
				" summonsno = " + Common.sqlChar(summonsNo) + "," +
				" enterdate = " + Common.sqlChar(ut._transToCE_Year(enterDate)) + "," +
				" verify = " + Common.sqlChar(verify) + "," +
				" notes = " + Common.sqlChar(notes) + "," +
				" editid = " + Common.sqlChar(getEditID()) + "," +
				" editdate = " + Common.sqlChar(ut._transToCE_Year(getEditDate())) + "," +
				" edittime = " + Common.sqlChar(getEditTime()) + "," +
				" proofyear = " + Common.sqlChar(getProofYear()) + "," +
				" differencekind = " + Common.sqlChar(getDifferenceKind()) + "," +
				" summonsdate = " + Common.sqlChar(ut._transToCE_Year(getSummonsDate())) + 
				" where 1=1 " + 
				" and enterorg = " + Common.sqlChar(enterOrg) +
				" and ownership = " + Common.sqlChar(ownership) +
				" and caseno = " + Common.sqlChar(caseNo) +
				" and differencekind = " + Common.sqlChar(differenceKind)
				);
		
		//問題單1290補充: update時更改編號-年，重新取得編號-號
		if (this.checkProofyearChanged(getEnterOrg(), getCaseNo(), getProofYear())) {
			String newproofno = MaxClosingYM.getMaxProofNo("UNTMP_ADDPROOF",enterOrg,ownership,this.getProofYear());
			
			sqls.add(" update UNTMP_ADDPROOF set proofno=" + Common.sqlChar(newproofno) +
					"  where 1=1 and enterorg = " + Common.sqlChar(enterOrg) +
					" and caseno = " + Common.sqlChar(caseNo)
					);
		}
		
		String oldVerify = this.queryOldVerify();
		if (!oldVerify.equals(this.getVerify())) {
			sqls.add(" update UNTMP_MOVABLE set " +
					 " verify = " + Common.sqlChar(this.getVerify()) + "," +
					 " enterdate = " + Common.sqlChar(new UNTCH_Tools()._transToCE_Year(getEnterDate())) + "," +
					 " editid = " + Common.sqlChar(getEditID()) + "," +
					 " editdate = " + Common.sqlChar(new UNTCH_Tools()._transToCE_Year(getEditDate())) + "," +
					 " edittime = " + Common.sqlChar(getEditTime()) + 	                
					" where 1=1 " + 
					" and enterorg = " + Common.sqlChar(enterOrg) +
					" and ownership = " + Common.sqlChar(ownership) +
					" and caseno = " + Common.sqlChar(caseNo) +		
					" and differencekind = " + Common.sqlChar(differenceKind)
					);

			sqls.add(" update UNTMP_MOVABLEDETAIL set" + 
					" verify = " + Common.sqlChar(this.getVerify()) + "," +
					 " enterdate = " + Common.sqlChar(new UNTCH_Tools()._transToCE_Year(getEnterDate())) + "," +
					 " editid = " + Common.sqlChar(getEditID()) + "," +
					 " editdate = " + Common.sqlChar(new UNTCH_Tools()._transToCE_Year(getEditDate())) + "," +
					 " edittime = " + Common.sqlChar(getEditTime()) + 	     
					" where 1=1 " + 
					" and enterorg = " + Common.sqlChar(enterOrg) +
					" and ownership = " + Common.sqlChar(ownership) +
					" and caseno = " + Common.sqlChar(caseNo) +		
					" and differencekind = " + Common.sqlChar(differenceKind)
					);
		}
		
		return sqls.toArray(new String[sqls.size()]);
	}


	//傳回delete sql
	protected String[] getDeleteSQL() throws Exception {
		String[] execSQLArray = null;
		String strSQLArray = ""; 
	
		Database db = new Database();
		ResultSet rs;	
		String sql="select enterOrg, ownership, propertyNo, lotNo from UNTMP_Movable " +
			" where enterOrg = " + Common.sqlChar(enterOrg) +
			" and ownership = " + Common.sqlChar(ownership) +
			" and caseNo = " + Common.sqlChar(caseNo) + 
			" and differenceKind = " + Common.sqlChar(differenceKind) +
			"";		
		try {		
			rs = db.querySQL(sql);
			while (rs.next()){
				strSQLArray += " delete from UNTMP_MOVABLE " +
				" where enterorg = " + Common.sqlChar(enterOrg) +
				" and ownership = " + Common.sqlChar(ownership) +
				" and caseno = " + Common.sqlChar(caseNo) +
				" and differencekind = " + Common.sqlChar(differenceKind) +
				" and propertyno = " + Common.sqlChar(rs.getString("propertyNo")) +
				" and lotno = " + Common.sqlChar(rs.getString("lotNo")) +
				":;:";
									
				strSQLArray += " delete from UNTMP_MOVABLEDETAIL " +
				" where enterorg = " + Common.sqlChar(enterOrg) +
				" and ownership = " + Common.sqlChar(ownership) +
				" and differencekind = " + Common.sqlChar(differenceKind) +
				" and propertyno = " + Common.sqlChar(rs.getString("propertyNo")) +
				" and lotno = " + Common.sqlChar(rs.getString("lotNo")) +
				":;:";
	
				strSQLArray += " delete from UNTMP_ATTACHMENT1 " +
				" where enterorg = " + Common.sqlChar(enterOrg) +
				" and ownership = " + Common.sqlChar(ownership) +
				" and differencekind = " + Common.sqlChar(differenceKind) +
				" and propertyno = " + Common.sqlChar(rs.getString("propertyNo")) +
				" and lotno = " + Common.sqlChar(rs.getString("lotNo")) +
				":;:";
	
				strSQLArray += " delete from UNTMP_ATTACHMENT2 " +
				" where enterorg = " + Common.sqlChar(enterOrg) +
				" and ownership = " + Common.sqlChar(ownership) +
				" and differencekind = " + Common.sqlChar(differenceKind) +
				" and propertyno = " + Common.sqlChar(rs.getString("propertyNo")) +
				" and lotno = " + Common.sqlChar(rs.getString("lotNo")) +
				":;:";			
			}
			rs.close();		
		} finally {
			db.closeAll();
		}		
		strSQLArray +=" delete from UNTMP_ADDPROOF where 1=1 " +
		" and enterorg = " + Common.sqlChar(enterOrg) +
		" and ownership = " + Common.sqlChar(ownership) +
		" and caseno = " + Common.sqlChar(caseNo) +
		" and differencekind = " + Common.sqlChar(differenceKind) +
		":;:";
		
		strSQLArray += " update UNTMP_REDUCEPROOF set addcaseno = null where 1=1 " +
		" and enterorg = " + Common.sqlChar(enterOrg) +
		" and ownership = " + Common.sqlChar(ownership) +
		" and addcaseno = " + Common.sqlChar(caseNo) +
		":;:";
		
		execSQLArray = strSQLArray.split(":;:");	
		return execSQLArray;
	}


	/**
	 * <br>
	 * <br>目的：依主鍵查詢單一資料
	 * <br>參數：無
	 * <br>傳回：傳回本物件
	*/
	
	public UNTMP001F  queryOne() throws Exception{
		Database db = new Database();
		UNTMP001F obj = this;
		try {
			String sql=" select b.organSName, a.enterOrg, a.ownership, a.caseNo, a.caseName, a.writeDate, a.writeUnit, a.proofDoc, a.proofNo, a.manageNo, a.summonsNo, a.enterDate, a.verify, a.notes, a.editID, a.editDate, a.editID, a.editDate, a.editTime, a.select b.organsname, a.enterorg, a.ownership, a.caseno, a.casename, a.writedate, a.writeunit, a.proofdoc, a.proofno, a.manageno, a.summonsno, a.enterdate, a.verify, a.notes, a.editid, a.editdate, a.editid, a.editdate, a.edittime, a.summonsdate from UNTMP_ADDPROOF a, SYSCA_ORGAN b where 1=1 and a.enterorg = " + Common.sqlChar(TCGHCommon.getSYSCODEName("01", "ETO")) + "and a.caseno = '1030000002' and a.ownership = '1' and a.differencekind = '110' and b.organid = a.enterorg   "+
			" from UNTMP_AddProof a, SYSCA_Organ b where 1=1 " +
			" and a.enterOrg = " + Common.sqlChar(enterOrg) +
			" and a.caseNo = " + Common.sqlChar(caseNo) +
			" and a.ownership = " + Common.sqlChar(ownership) +
			" and a.differenceKind = " + Common.sqlChar(differenceKind) +
			" and b.organID = a.enterOrg" +
			"";
	//System.out.println("-- untmp001f queryOne -- "+sql);
			UNTCH_Tools ul = new UNTCH_Tools();
			ResultSet rs = db.querySQL(sql);
			if (rs.next()){
				obj.setEnterOrg(rs.getString("enterOrg"));
				obj.setOwnership(rs.getString("ownership"));
				obj.setEnterOrgName(rs.getString("organSName"));
				obj.setCaseNo(rs.getString("caseNo"));
				obj.setCaseName(rs.getString("caseName"));
				obj.setWriteDate(ul._transToROC_Year(rs.getString("writeDate")));
				obj.setWriteUnit(rs.getString("writeUnit"));
				obj.setProofDoc(rs.getString("proofDoc"));
				obj.setProofNo(rs.getString("proofNo"));
				obj.setManageNo(rs.getString("manageNo"));
				obj.setSummonsNo(rs.getString("summonsNo"));
				obj.setEnterDate(ul._transToROC_Year(rs.getString("enterDate")));			
				obj.setVerify(rs.getString("verify"));
				obj.setOldVerify(rs.getString("verify"));
				obj.setNotes(rs.getString("notes"));
				obj.setEditID(rs.getString("editID"));
				obj.setEditDate(ul._transToROC_Year(rs.getString("editDate")));
				obj.setEditTime(rs.getString("editTime"));
				obj.setDifferenceKind(rs.getString("differenceKind"));
				obj.setSummonsDate(ul._transToROC_Year(rs.getString("summonsdate")));
			}
			setStateQueryOneSuccess();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.closeAll();
		}
		return obj;
	}

}


