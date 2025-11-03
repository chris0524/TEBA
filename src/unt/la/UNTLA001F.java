/*
*<br>程式目的：土地主檔資料維護－增加單資料
*<br>程式代號：untla001f
*<br>程式日期：0940818
*<br>程式作者：novia
*<br>--------------------------------------------------------
*<br>修改作者　　修改日期　　　修改目的
*<br>clive.chang 0941219	Debug & Modify for Testing and autual running..
*<br>--------------------------------------------------------
*/

package unt.la;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import unt.ch.UNTCH_Tools;
import util.Common;
import util.Database;
import util.MaxClosingYM;

public class UNTLA001F extends UNTLA001Q{

	private String summonsDate;
	public String getSummonsDate() {return checkGet(summonsDate);}
	public void setSummonsDate(String summonsDate) {this.summonsDate = checkSet(summonsDate);}
	private String causeName;
	public String getCauseName() {return checkGet(causeName);}
	public void setCauseName(String causeName) {this.causeName = checkSet(causeName);}
	
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
	String mergeDivision;
	String verify;
	String notes;
	String editID;
	String editDate;
	String oldVerify;
	String propertyNo;
	String serialNo;
	String verifyError;
	
	public String getPropertyNo(){ return checkGet(propertyNo); }
	public void setPropertyNo(String s){ propertyNo=checkSet(s); }
	public String getSerialNo(){ return checkGet(serialNo); }
	public void setSerialNo(String s){ serialNo=checkSet(s); }
	public String getVerifyError(){ return checkGet(verifyError); }
	public void setVerifyError(String s){ verifyError=checkSet(s); }
	
	public String getEnterOrg(){ return checkGet(enterOrg); }
	public void setEnterOrg(String s){ enterOrg=checkSet(s); }
	public String getEnterOrgName(){return checkGet(enterOrgName);}
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
	public String getMergeDivision(){ return checkGet(mergeDivision); }
	public void setMergeDivision(String s){ mergeDivision=checkSet(s); }
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
	
	private String differenceKind;
	public String getDifferenceKind() {	return checkGet(differenceKind);}
	public void setDifferenceKind(String differenceKind) {	this.differenceKind = checkSet(differenceKind);}
	
	private String engineeringNo;
	private String proofYear;
	public String getEngineeringNo() {return checkGet(engineeringNo);}
	public void setEngineeringNo(String engineeringNo) {this.engineeringNo = checkSet(engineeringNo);}
	public String getProofYear() {return checkGet(proofYear);}
	public void setProofYear(String proofYear) {this.proofYear = checkSet(proofYear);}
	
	private String originalPlace1Name;
	public String getOriginalPlace1Name() {return checkGet(originalPlace1Name);}
	public void setOriginalPlace1Name(String originalPlace1Name) {this.originalPlace1Name = checkSet(originalPlace1Name);}
	
	private String engineeringNoName;
	public String getEngineeringNoName() {return checkGet(engineeringNoName);}
	public void setEngineeringNoName(String engineeringNoName) {this.engineeringNoName = checkSet(engineeringNoName);}


	//傳回執行insert前之檢查sql
	protected String[][] getInsertCheckSQL(){
		setProofNo(MaxClosingYM.getMaxProofNo("UNTLA_ADDPROOF",enterOrg,ownership,this.getProofYear()));
		
		String[][] checkSQLArray = new String[1][4];
	 	checkSQLArray[0][0]=" select count(*) as checkresult from UNTLA_ADDPROOF where 1=1 " + 
			" and enterorg = " + Common.sqlChar(enterOrg) + 
			" and ownership = " + Common.sqlChar(ownership) + 
			" and caseno = " + Common.sqlChar(caseNo) + 
			" and differencekind = " + Common.sqlChar(differenceKind) +
			"";
		checkSQLArray[0][1]=">";
		checkSQLArray[0][2]="0";
		checkSQLArray[0][3]="該筆資料己重複，請重新輸入！";
		return checkSQLArray;
	}


	//傳回insert sql
	protected String[] getInsertSQL(){
		String[] execSQLArray = new String[1];
		UNTCH_Tools ul = new UNTCH_Tools();
		
		execSQLArray[0]=" insert into UNTLA_ADDPROOF(" +
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
				" mergedivision,"+
				" verify,"+
				" notes,"+
				" editid,"+
				" editdate,"+
				" edittime,"+
				" differencekind," +
				" engineeringno," +
				" proofyear," +
				" summonsdate" +
			" )Values(" +
				Common.sqlChar(enterOrg) + "," +
				Common.sqlChar(ownership) + "," +
				Common.sqlChar(caseNo) + "," +
				Common.sqlChar(caseName) + "," +
				Common.sqlChar(ul._transToCE_Year(writeDate)) + "," +
				Common.sqlChar(writeUnit) + "," +
				Common.sqlChar(proofDoc) + "," +
				Common.sqlChar(proofNo) + "," +
				Common.sqlChar(manageNo) + "," +
				Common.sqlChar(summonsNo) + "," +
				Common.sqlChar(ul._transToCE_Year(enterDate)) + "," +
				Common.sqlChar(mergeDivision) + "," +
				Common.sqlChar("N") + "," +
				Common.sqlChar(notes) + "," +
				Common.sqlChar(getEditID()) + "," +
				Common.sqlChar(ul._transToCE_Year(getEditDate())) + "," +
				Common.sqlChar(getEditTime()) + "," +
				Common.sqlChar(getDifferenceKind()) + "," +
				Common.sqlChar(getEngineeringNo()) + "," +
				Common.sqlChar(getProofYear()) + "," +
				Common.sqlChar(ul._transToCE_Year(getSummonsDate())) + ")" ;
		
		return execSQLArray;
	}

	//檢查update時，proofyear是否有改變
	private boolean checkProofyearChanged(String enterorg, String caseno, String newproofyear){
		boolean isChanged = false;
		String oldproofyear ="";
		ResultSet rs;
		Database db = new Database();
		try{
			String sql = "select proofyear from UNTLA_ADDPROOF where enterorg=" + Common.sqlChar(enterorg) +
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
			return db.getLookupField("select verify from UNTLA_ADDPROOF where enterorg=" + Common.sqlChar(this.getEnterOrg()) + " and caseno = " + Common.sqlChar(this.getCaseNo()));
		} finally {
			if (db != null) {
				db.closeAll();
			}
		}
	}

	@Override
	public String[][] getUpdateCheckSQLBeforeAction(){
		List<String[]> sqls = new ArrayList<String[]>();
		
		String oldVerify = this.queryOldVerify();
		
		if ("Y".equals(oldVerify) && "N".equals(this.getVerify())) {
			// 回復入帳時檢查 不可存在於 增減值單/減損單
			String[] tmpchk = new String[4];
			tmpchk[0] = " select count(*) as checkResult from UNTLA_LAND a "
					  + " where a.enterorg = " + Common.sqlChar(this.getEnterOrg()) 
					  + " and a.caseno = " + Common.sqlChar(this.getCaseNo()) 
					  + " and ( "
					  + " exists(select 1 from UNTLA_ADJUSTDETAIL b where a.enterorg=b.enterorg and a.ownership=b.ownership and a.differencekind=b.differencekind and a.propertyno=b.propertyno and a.serialno=b.serialno)"
					  + " or exists(select 1 from UNTLA_REDUCEDETAIL b where a.enterorg=b.enterorg and a.ownership=b.ownership and a.differencekind=b.differencekind and a.propertyno=b.propertyno and a.serialno=b.serialno) "
					  + " or exists(select 1 from UNTMP_MOVEDETAIL b where a.enterorg=b.enterorg and a.ownership=b.ownership and a.differencekind=b.differencekind and a.propertyno=b.propertyno and a.serialno=b.serialno) "
					  + ")";
			tmpchk[1] = "!=";
			tmpchk[2] = "0";
			tmpchk[3] = "土地資料已做過增減值或減損作業或移動作業，請先刪除增減值或減損作業或移動作業之資料再回此作業取消入帳!";
			
			sqls.add(tmpchk);
		}
		
		return sqls.toArray(new String[sqls.size()][4]);
	}

	@Override
	protected String[][] getUpdateCheckSQL() {
		return super.getUpdateCheckSQL();
	}
	
	//傳回update sql
	protected String[] getUpdateSQL() throws Exception {
		
		if (!"".equals(this.getMergeDivision())) {
			throw new Exception("合併分割案，須至「土地合併分割作業」進行！");
		}

		String oldVerify = this.queryOldVerify();
		
		String[] execSQLArray;
		
	    UNTCH_Tools ut = new UNTCH_Tools();
	    
		StringBuffer sbSQL = new StringBuffer().append(
		" update UNTLA_ADDPROOF set " ).append(
				" casename = " ).append( Common.sqlChar(caseName) ).append( "," ).append(
				" writedate = " ).append( Common.sqlChar(ut._transToCE_Year(writeDate)) ).append( "," ).append(
				" writeunit = " ).append( Common.sqlChar(writeUnit) ).append( "," ).append(
				" proofdoc = " ).append( Common.sqlChar(proofDoc) ).append( "," ).append(
				" proofno = " ).append( Common.sqlChar(proofNo) ).append( "," ).append(
				" manageno = " ).append( Common.sqlChar(manageNo) ).append( "," ).append(
				" summonsno = " ).append( Common.sqlChar(summonsNo) ).append( "," ).append(
				" enterdate = " ).append( Common.sqlChar(ut._transToCE_Year(enterDate)) ).append( "," ).append(
				" mergedivision = " ).append( Common.sqlChar(mergeDivision) ).append( "," ).append(
				" verify = " ).append( Common.sqlChar(verify) ).append( "," ).append(
				" notes = " ).append( Common.sqlChar(notes) ).append( "," ).append(
				" editid = " ).append( Common.sqlChar(getEditID()) ).append( "," ).append(
				" editdate = " ).append( Common.sqlChar(ut._transToCE_Year(getEditDate())) ).append( "," ).append(
				" edittime = " ).append( Common.sqlChar(getEditTime()) ).append( "," ).append(
				" differencekind = " ).append( Common.sqlChar(getDifferenceKind()) ).append( "," ).append(
				" engineeringno = " ).append( Common.sqlChar(getEngineeringNo()) ).append( "," ).append(
				" proofyear = " ).append( Common.sqlChar(getProofYear()) ).append("," ).append(
				" summonsdate = " ).append( Common.sqlChar(ut._transToCE_Year(getSummonsDate())) ).append(
			" where 1=1 " ).append( 
				" and enterorg = " ).append( Common.sqlChar(enterOrg) ).append(
				" and ownership = " ).append( Common.sqlChar(ownership) ).append(
				" and caseno = " ).append( Common.sqlChar(caseNo) ).append(
				" and differencekind = " ).append( Common.sqlChar(differenceKind) ).append(
				":;:");
		
		//問題單1290補充: update時更改編號-年，重新取得編號-號
		if(this.checkProofyearChanged(getEnterOrg(), getCaseNo(), getProofYear())){
			String newproofno = MaxClosingYM.getMaxProofNo("UNTLA_ADDPROOF",enterOrg,ownership,this.getProofYear());
			sbSQL.append(" update UNTLA_ADDPROOF set proofno=").append(Common.sqlChar(newproofno)).append(
										" where 1=1 " ).append(
										" and enterorg = ").append(Common.sqlChar(enterOrg)).append(
										" and caseno = ").append(Common.sqlChar(caseNo)).append(
										":;:");
		}
		
		if (!oldVerify.equals(verify)) {
			// 入帳值異動時 在去操作土地主檔
			sbSQL.append(" update UNTLA_LAND set " ).append(
					" enterdate = " ).append( Common.sqlChar(new UNTCH_Tools()._transToCE_Year(enterDate)) ).append( "," ).append(	
					" verify = " ).append( Common.sqlChar(verify) ).append( "," ).append(
					" editid = " ).append( Common.sqlChar(getEditID()) ).append( "," ).append(
					" editdate = " ).append( Common.sqlChar(new UNTCH_Tools()._transToCE_Year(getEditDate())) ).append( "," ).append(
					" edittime = " ).append( Common.sqlChar(getEditTime()) ).append(					
				" where 1=1 " ).append( 
					" and enterorg = " ).append( Common.sqlChar(enterOrg) ).append(
					" and ownership = " ).append( Common.sqlChar(ownership) ).append(
					" and caseno = " ).append( Common.sqlChar(caseNo) ).append(
					" and differencekind = " ).append( Common.sqlChar(differenceKind) ).append(		
					":;:");
		}
		
		execSQLArray = sbSQL.toString().split(":;:");
		return execSQLArray;
	}
	
	//傳回delete sql
	protected String[] getDeleteSQL() throws Exception {
		Database db = new Database();
		ResultSet rs, rsDtl;	
		StringBuffer sbSQL = new StringBuffer();
		String sql="select enterorg, ownership, propertyno, serialno from UNTLA_LAND " +
			" where enterorg = " + Common.sqlChar(enterOrg) +
			" and ownership = " + Common.sqlChar(ownership) +
			" and caseno = " + Common.sqlChar(caseNo) +
			" and differencekind = " + Common.sqlChar(differenceKind) + 
			"";		
		try {		
			rs = db.querySQL(sql);
			while (rs.next()){
				sbSQL.append(" delete from UNTLA_LAND " ).append(
				" where enterorg = " ).append( Common.sqlChar(enterOrg) ).append(
				" and ownership = " ).append( Common.sqlChar(ownership) ).append(
				" and caseno = " ).append( Common.sqlChar(caseNo) ).append(
				" and differencekind = " ).append( Common.sqlChar(differenceKind) ).append(
				" and propertyno = " ).append( Common.sqlChar(rs.getString("propertyNo")) ).append(
				" and serialno = " ).append( Common.sqlChar(rs.getString("serialNo")) ).append(
				":;:");
							
				String sSQL="select serialno1 from UNTLA_MANAGE " +
				" where enterorg = " + Common.sqlChar(enterOrg) +
				" and ownership = " + Common.sqlChar(ownership) +
				" and differencekind = " + Common.sqlChar(differenceKind) +
				" and propertyno = " + Common.sqlChar(rs.getString("propertyNo")) +
				" and serialno = " + Common.sqlChar(rs.getString("serialNo")) +
				"";			
				rsDtl = db.querySQL(sSQL);
				while (rsDtl.next()) {
					sbSQL.append(" delete from UNTLA_PERSON " ).append(
					" where enterorg = " ).append( Common.sqlChar(enterOrg) ).append(
					" and ownership = " ).append( Common.sqlChar(ownership) ).append(
					" and propertyno = " ).append( Common.sqlChar(rs.getString("propertyNo")) ).append(
					" and serialno = " ).append( Common.sqlChar(rs.getString("serialNo")) ).append(
					" and serialno1 = " ).append( Common.sqlChar(rsDtl.getString("serialNo1")) ).append(				
					":;:");				
				}
				rsDtl.close();			
				
				sbSQL.append(" delete from UNTLA_MANAGE " ).append(
				" where enterorg = " ).append( Common.sqlChar(enterOrg) ).append(
				" and ownership = " ).append( Common.sqlChar(ownership) ).append(
				" and differencekind = " ).append( Common.sqlChar(differenceKind) ).append(
				" and propertyno = " ).append( Common.sqlChar(rs.getString("propertyNo")) ).append(
				" and serialno = " ).append( Common.sqlChar(rs.getString("serialNo")) ).append(
				":;:");
				
				sbSQL.append(" delete from UNTLA_ATTACHMENT " ).append(
				" where enterorg = " ).append( Common.sqlChar(enterOrg) ).append(
				" and ownership = " ).append( Common.sqlChar(ownership) ).append(
				" and differencekind = " ).append( Common.sqlChar(differenceKind) ).append(
				" and propertyno = " ).append( Common.sqlChar(rs.getString("propertyNo")) ).append(
				" and serialno = " ).append( Common.sqlChar(rs.getString("serialNo")) ).append(
				":;:");
				
				sbSQL.append(" delete from UNTLA_VALUE " ).append(
				" where enterorg = " ).append( Common.sqlChar(enterOrg) ).append(
				" and ownership = " ).append( Common.sqlChar(ownership) ).append(
				" and differencekind = " ).append( Common.sqlChar(differenceKind) ).append(
				" and propertyno = " ).append( Common.sqlChar(rs.getString("propertyNo")) ).append(
				" and serialno = " ).append( Common.sqlChar(rs.getString("serialNo")) ).append(
				":;:");
				
				sbSQL.append(" delete from UNTLA_PRICE " ).append(
				" where enterorg = " ).append( Common.sqlChar(enterOrg) ).append(
				" and ownership = " ).append( Common.sqlChar(ownership) ).append(
				" and differencekind = " ).append( Common.sqlChar(differenceKind) ).append(
				" and propertyno = " ).append( Common.sqlChar(rs.getString("propertyNo")) ).append(
				" and serialno = " ).append( Common.sqlChar(rs.getString("serialNo")) ).append(
				":;:");									
				
				sbSQL.append(" delete from UNTLA_VIEWSCENE " ).append(
				" where enterorg = " ).append( Common.sqlChar(enterOrg) ).append(
				" and ownership = " ).append( Common.sqlChar(ownership) ).append(
				" and propertyno = " ).append( Common.sqlChar(rs.getString("propertyNo")) ).append(
				" and serialno = " ).append( Common.sqlChar(rs.getString("serialNo")) ).append(
				":;:");
				
				sbSQL.append(" delete from UNTLA_TAX " ).append(
				" where enterorg = " ).append( Common.sqlChar(enterOrg) ).append(
				" and ownership = " ).append( Common.sqlChar(ownership) ).append(
				" and propertyno = " ).append( Common.sqlChar(rs.getString("propertyNo")) ).append(
				" and serialno = " ).append( Common.sqlChar(rs.getString("serialNo")) ).append(
				":;:");
	
				sbSQL.append(" delete from UNTLA_DRAWPROOF " ).append(
				" where enterorg = " ).append( Common.sqlChar(enterOrg) ).append(
				" and ownership = " ).append( Common.sqlChar(ownership) ).append(
				" and propertyno = " ).append( Common.sqlChar(rs.getString("propertyNo")) ).append(
				" and serialno = " ).append( Common.sqlChar(rs.getString("serialNo")) ).append(
				":;:");	
				
				sbSQL.append(" delete from UNTLA_RIGHT " ).append(
				" where enterorg = " ).append( Common.sqlChar(enterOrg) ).append(
				" and ownership = " ).append( Common.sqlChar(ownership) ).append(
				" and differencekind = " ).append( Common.sqlChar(differenceKind) ).append(
				" and propertyno = " ).append( Common.sqlChar(rs.getString("propertyNo")) ).append(
				" and serialno = " ).append( Common.sqlChar(rs.getString("serialNo")) ).append(
				":;:");	
				
				sbSQL.append(" delete from UNTLA_USE " ).append(
				" where enterorg = " ).append( Common.sqlChar(enterOrg) ).append(
				" and ownership = " ).append( Common.sqlChar(ownership) ).append(
				" and differencekind = " ).append( Common.sqlChar(differenceKind) ).append(
				" and propertyno = " ).append( Common.sqlChar(rs.getString("propertyNo")) ).append(
				" and serialno = " ).append( Common.sqlChar(rs.getString("serialNo")) ).append(
				":;:");				
			}
			rs.close();
		} finally {
			db.closeAll();
		}		
		sbSQL.append(" delete from UNTLA_ADDPROOF where 1=1 " ).append(
		" and enterorg = " ).append( Common.sqlChar(enterOrg) ).append(
		" and ownership = " ).append( Common.sqlChar(ownership) ).append(
		" and caseno = " ).append( Common.sqlChar(caseNo) ).append(
		" and differencekind = " ).append( Common.sqlChar(differenceKind) ).append(
		":;:");
		
		sbSQL.append(" update UNTLA_REDUCEPROOF set addcaseno = null where 1=1 " ).append(
		" and enterorg = " ).append( Common.sqlChar(enterOrg) ).append(
		" and ownership = " ).append( Common.sqlChar(ownership) ).append(
		" and addcaseno = " ).append( Common.sqlChar(caseNo) ).append(
		":;:");
			
		return sbSQL.toString().split(":;:");
	}


	/**
	 * <br>
	 * <br>目的：依主鍵查詢單一資料
	 * <br>參數：無
	 * <br>傳回：傳回本物件
	*/
	public UNTLA001F  queryOne() throws Exception{
		Database db = new Database();
		UNTLA001F obj = this;
		try {
			String sql=" select b.organsname, a.enterorg, a.ownership, a.caseno, a.casename, a.writedate, a.writeunit, a.proofdoc, a.proofno, a.manageno, a.summonsno, a.enterdate, a.mergedivision, a.verify, a.notes, a.editid, a.editdate, a.editid, a.editdate, a.edittime, a.differenceKind, a.engineeringNo, a.proofYear, a.summonsdate "+
					" from UNTLA_ADDPROOF a, SYSCA_ORGAN b, SYSPK_PROPERTYCODE c where 1=1 " +
					" and a.enterorg = " + Common.sqlChar(enterOrg) +
					" and a.caseno = " + Common.sqlChar(caseNo) +
					" and a.ownership = " + Common.sqlChar(ownership) +
					" and a.differencekind = "+ Common.sqlChar(differenceKind) +
					" and b.organid = a.enterorg" +
					"";
			
	//System.out.println("-- untla001f queryOne --\n"+sql);
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
				obj.setMergeDivision(rs.getString("mergeDivision"));
				obj.setVerify(rs.getString("verify"));
				obj.setOldVerify(rs.getString("verify"));
				obj.setNotes(rs.getString("notes"));
				obj.setEditID(rs.getString("editID"));
				obj.setEditDate(ul._transToROC_Year(rs.getString("editDate")));
				obj.setEditTime(rs.getString("editTime"));
				obj.setDifferenceKind(rs.getString("differenceKind"));
				obj.setEngineeringNo(rs.getString("engineeringNo"));
				obj.setEngineeringNoName(ul._getEngineeringNoName(rs.getString("enterOrg"),rs.getString("engineeringNo")));
				obj.setProofYear(rs.getString("proofYear"));
				
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

	//取得最大的月結年月
	protected String getMaxClosingYM(){
		Database db = new Database();
		ResultSet rs;	
		String closingYM ="" ;
		String sql="select max(closingym) as closingym from UNTGR_CLOSING " +
			" where enterorg = " + Common.sqlChar(enterOrg) +
			"";		
		try {		
			rs = db.querySQL(sql);
			if (rs.next()){
			    closingYM = rs.getString("closingYM")==null?"00000":rs.getString("closingYM");
			} else {
				closingYM = "00000";
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.closeAll();
		}
		return closingYM;
	}


	public String execVerifyCheck(){
		String sql;
		String message="";
		boolean manageFlag = false;
		boolean attachmentFlag = false;
		
		Database db = new Database();
		ResultSet rs;
		
		//入帳時，檢核該單據每筆土地資料皆必須至少有一筆「管理資料(UNTLA_Manage)」，若不符合，則不允許入帳，並提示使用者。
			sql = " select (select count(*) from UNTLA_MANAGE b where 1=1 and a.enterorg=b.enterorg and a.ownership=b.ownership and a.propertyno=b.propertyno and a.serialno=b.serialno) as count"+
					" from UNTLA_LAND a" +
			 		" where 1=1" +
			 		" and a.enterorg = " + Common.sqlChar(this.getEnterOrg()) +
			 		" and a.ownership = " + Common.sqlChar(this.getOwnership()) +
			 		" and a.caseno = " + Common.sqlChar(this.getCaseNo()) ;
			try{
				rs = db.querySQL(sql);
				while(rs.next()){
					if("0".equals(rs.getString("count"))){
						manageFlag = true;
						break;
					}
				}
			}catch(Exception e){
				e.printStackTrace();
			}
		 
		//入帳時，檢核該單據每筆土地資料皆必須至少有一筆「地上物資料(UNTLA_Attachment)」，若不符合，則不允許入帳，並提示使用者。
			sql = " select (select count(*) from UNTLA_ATTACHMENT b where 1=1 and a.enterorg=b.enterorg and a.ownership=b.ownership and a.propertyno=b.propertyno and a.serialno=b.serialno) as count"+
					" from UNTLA_LAND a" +
			 		" where 1=1" +
			 		" and a.enterorg = " + Common.sqlChar(this.getEnterOrg()) +
			 		" and a.ownership = " + Common.sqlChar(this.getOwnership()) +
			 		" and a.caseno = " + Common.sqlChar(this.getCaseNo()) ;
			
			try{
				rs = db.querySQL(sql);
				while(rs.next()){
					if("0".equals(rs.getString("count"))){
						attachmentFlag = true;
						break;
					}
				}
			}catch(Exception e){
				e.printStackTrace();
			}
			
			sql = null;
	
		 if(manageFlag && attachmentFlag){	 message = "每筆財產至少有一筆管理資料和一筆地上物資料才可入帳，請重新檢查！！";		 
		 }else if(manageFlag){				 message = "每筆財產至少有一筆管理資料才可入帳，請重新檢查！！";
		 }else if(attachmentFlag){			 message = "每筆財產至少有一筆地上物資料才可入帳，請重新檢查！！";
		 }	 
			
		 return message;
	}

}


