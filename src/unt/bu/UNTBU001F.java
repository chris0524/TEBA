package unt.bu;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import unt.ch.UNTCH_Tools;
import util.Common;
import util.Database;
import util.MaxClosingYM;

/*
*<br>程式目的：建物主檔資料維護－增加單資料
*<br>程式代號：UNTBU001F
*<br>程式日期：0940915
*<br>程式作者：clive.chang
*<br>--------------------------------------------------------
*<br>修改作者　　修改日期　　　修改目的
*<br>--------------------------------------------------------
*/

public class UNTBU001F extends UNTBU001Q{

	private String summonsDate;
	public String getSummonsDate() {return checkGet(summonsDate);}
	public void setSummonsDate(String summonsDate) {this.summonsDate = checkSet(summonsDate);}
	String enterOrg;
	String enterOrgName;
	String ownership;
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
	String propertyNo;
	String serialNo;
	String verifyError;
	String closing;
	
	public String getPropertyNo(){ return checkGet(propertyNo); }
	public void setPropertyNo(String s){ propertyNo=checkSet(s); }
	public String getSerialNo(){ return checkGet(serialNo); }
	public void setSerialNo(String s){ serialNo=checkSet(s); }
	public String getEnterOrg(){ return checkGet(enterOrg); }
	public void setEnterOrg(String s){ enterOrg=checkSet(s); }
	public String getEnterOrgName(){ return checkGet(enterOrgName); }
	public void setEnterOrgName(String s){ enterOrgName=checkSet(s); }
	public String getOwnership(){ return checkGet(ownership); }
	public void setOwnership(String s){ ownership=checkSet(s); }
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
	
	public String getClosing() {
		return closing;
	}
	public void setClosing(String closing) {
		this.closing = closing;
	}
	public String getVerifyError(){ return checkGet(verifyError); }
	public void setVerifyError(String s){ verifyError=checkSet(s); }
	
	String differenceKind;
	public String getDifferenceKind() {return checkGet(differenceKind);}
	public void setDifferenceKind(String differenceKind) {this.differenceKind = checkSet(differenceKind);}
	
	String engineeringNo;
	String proofYear;
	public String getEngineeringNo() {return checkGet(engineeringNo);}
	public void setEngineeringNo(String engineeringNo) {this.engineeringNo = checkSet(engineeringNo);}
	public String getProofYear() {return checkGet(proofYear);}
	public void setProofYear(String proofYear) {this.proofYear = checkSet(proofYear);}
	
	
	//傳回執行insert前之檢查sql
	protected String[][] getInsertCheckSQL(){
		
		setProofNo(MaxClosingYM.getMaxProofNo("UNTBU_AddProof",enterOrg,ownership,this.getProofYear()));
		
		String[][] checkSQLArray = new String[1][4];
	 	checkSQLArray[0][0]=" select count(*) as checkResult from UNTBU_AddProof where 1=1 " + 
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
		execSQLArray[0]=" insert into UNTBU_ADDPROOF(" +
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
				" engineeringno,"+
				" proofyear,"+
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
				Common.sqlChar(ut._transToCE_Year(getSummonsDate())) + ")";
		return execSQLArray;
	}
	
	private String queryOldVerify() {
		Database db = null;
		try {
			db = new Database();
			return db.getLookupField("select verify from UNTBU_ADDPROOF where enterorg=" + Common.sqlChar(this.getEnterOrg()) + " and caseno = " + Common.sqlChar(this.getCaseNo()));
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
			tmpchk[0] = " select count(*) as checkResult from UNTBU_BUILDING a "
					  + " where a.enterorg = " + Common.sqlChar(this.getEnterOrg()) 
					  + " and a.caseno = " + Common.sqlChar(this.getCaseNo()) 
					  + " and ( "
					  + " exists(select 1 from UNTBU_ADJUSTDETAIL b where a.enterorg=b.enterorg and a.ownership=b.ownership and a.differencekind=b.differencekind and a.propertyno=b.propertyno and a.serialno=b.serialno)"
					  + " or exists(select 1 from UNTBU_REDUCEDETAIL b where a.enterorg=b.enterorg and a.ownership=b.ownership and a.differencekind=b.differencekind and a.propertyno=b.propertyno and a.serialno=b.serialno) "
					  + " or exists(select 1 from UNTMP_MOVEDETAIL b where a.enterorg=b.enterorg and a.ownership=b.ownership and a.differencekind=b.differencekind and a.propertyno=b.propertyno and a.serialno=b.serialno) "
					  + ") ";
			tmpchk[1] = "!=";
			tmpchk[2] = "0";
			tmpchk[3] = "建物資料已做過增減值或減損作業或移動作業，請先刪除增減值或減損作業或移動作業之資料再回此作業取消入帳!";
			sqls.add(tmpchk);
		}
		
		return sqls.toArray(new String[sqls.size()][4]);
	}

	protected String[][] getUpdateCheckSQL(){
		String[][] checkSQLArray = new String[1][4];
	 	checkSQLArray[0][0] = "select SUM(deprpercent) as checkResult from UNTCH_DEPRPERCENT where 1=1 " + 
			" and enterOrg = " + Common.sqlChar(enterOrg) + 
			" and ownership = " + Common.sqlChar(ownership) +
			" and differenceKind = " + Common.sqlChar(differenceKind) +
			" and (propertyno) in (select propertyno from UNTBU_BUILDING where 1=1 " +
									" and enterOrg = " + Common.sqlChar(enterOrg) + 
									" and ownership = " + Common.sqlChar(ownership) +
									" and caseNo = " + Common.sqlChar(caseNo) +
									" and differenceKind = " + Common.sqlChar(differenceKind) +
									")" +
			" and (serialno) in (select serialno from UNTBU_BUILDING where 1=1 " +
									" and enterOrg = " + Common.sqlChar(enterOrg) + 
									" and ownership = " + Common.sqlChar(ownership) +
									" and caseNo = " + Common.sqlChar(caseNo) +
									" and differenceKind = " + Common.sqlChar(differenceKind) +
									")" +
			"";
		checkSQLArray[0][1]="!=";
		checkSQLArray[0][2]="100";
		checkSQLArray[0][3]="折舊比例中的分攤百分比需等於100！";
			
		return checkSQLArray;
	}	

	//檢查update時，proofyear是否有改變
	private boolean checkProofyearChanged(String enterorg, String caseno, String newproofyear){
		boolean isChanged = false;
		String oldproofyear ="";
		ResultSet rs;
		Database db = new Database();
		try{
			String sql = "select proofyear from UNTBU_ADDPROOF where enterorg=" + Common.sqlChar(enterorg) +
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

	//傳回update sql
	protected String[] getUpdateSQL() throws Exception {
		
		StringBuffer sbSQL = new StringBuffer();
		UNTCH_Tools ut = new UNTCH_Tools();
		String[] execSQLArray;
		sbSQL.append(" update UNTBU_ADDPROOF set " ).append(
				" casename = " ).append( Common.sqlChar(caseName) ).append( "," ).append(
				" writedate = " ).append( Common.sqlChar(ut._transToCE_Year(writeDate)) ).append( "," ).append(
				" writeunit = " ).append( Common.sqlChar(writeUnit) ).append( "," ).append(
				" proofdoc = " ).append( Common.sqlChar(proofDoc) ).append( "," ).append(
				" proofno = " ).append( Common.sqlChar(proofNo) ).append( "," ).append(
				" manageno = " ).append( Common.sqlChar(manageNo) ).append( "," ).append(
				" summonsno = " ).append( Common.sqlChar(summonsNo) ).append( "," ).append(
				" enterdate = " ).append( Common.sqlChar(ut._transToCE_Year(enterDate)) ).append( "," ).append(
				" verify = " ).append( Common.sqlChar(verify) ).append( "," ).append(
				" notes = " ).append( Common.sqlChar(notes) ).append( "," ).append(
				" editid = " ).append( Common.sqlChar(getEditID()) ).append( "," ).append(
				" editdate = " ).append( Common.sqlChar(ut._transToCE_Year(getEditDate())) ).append( "," ).append(
				" edittime = " ).append( Common.sqlChar(getEditTime()) ).append( "," ).append(
				" differencekind = " ).append( Common.sqlChar(getDifferenceKind()) ).append( "," ).append(
				" engineeringno = " ).append( Common.sqlChar(getEngineeringNo()) ).append( "," ).append(
				" proofyear = " ).append( Common.sqlChar(getProofYear()) ).append( "," ).append(
				" summonsdate = " ).append( Common.sqlChar(ut._transToCE_Year(getSummonsDate())) ).append(
			" where 1=1 " ).append( 
				" and enterorg = " ).append( Common.sqlChar(enterOrg) ).append(
				" and ownership = " ).append( Common.sqlChar(ownership) ).append(
				" and caseno = " ).append( Common.sqlChar(caseNo) ).append(
				" and differencekind = " ).append( Common.sqlChar(differenceKind) ).append(
				":;:");
		
		//問題單1290補充: update時更改編號-年，重新取得編號-號
		if(this.checkProofyearChanged(getEnterOrg(), getCaseNo(), getProofYear())){
			String newproofno = MaxClosingYM.getMaxProofNo("UNTBU_ADDPROOF",enterOrg,ownership,this.getProofYear());
			sbSQL.append(" update UNTBU_ADDPROOF set proofno=").append(Common.sqlChar(newproofno)).append(
										" where 1=1 " ).append(
										" and enterorg = ").append(Common.sqlChar(enterOrg)).append(
										" and caseno = ").append(Common.sqlChar(caseNo)).append(
										":;:");
		}
		
		String oldVerify = this.queryOldVerify();
		
		if (!oldVerify.equals(this.getVerify())) {
			sbSQL.append(" update UNTBU_BUILDING set " ).append(
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
		StringBuffer sbSQL = new StringBuffer();
		Database db = new Database();
		ResultSet rs;	
		String sql="select enterOrg, ownership, propertyNo, serialNo, differenceKind from UNTBU_Building " +
			" where enterOrg = " + Common.sqlChar(enterOrg) +
			" and ownership = " + Common.sqlChar(ownership) +
			" and caseNo = " + Common.sqlChar(caseNo) +
			" and differenceKind = " + Common.sqlChar(differenceKind) + 
			"";		
		try {		
			rs = db.querySQL(sql);
			while (rs.next()){
				sbSQL.append(" delete from UNTBU_BUILDING " ).append(
				" where enterorg = " ).append( Common.sqlChar(enterOrg) ).append(
				" and ownership = " ).append( Common.sqlChar(ownership) ).append(
				" and caseno = " ).append( Common.sqlChar(caseNo) ).append(
				" and propertyno = " ).append( Common.sqlChar(rs.getString("propertyNo")) ).append(
				" and serialno = " ).append( Common.sqlChar(rs.getString("serialNo")) ).append(
				" and differencekind = " ).append( Common.sqlChar(rs.getString("differenceKind")) ).append(
				":;:");
				
				sbSQL.append(" delete from UNTBU_MANAGE " ).append(
				" where enterorg = " ).append( Common.sqlChar(enterOrg) ).append(
				" and ownership = " ).append( Common.sqlChar(ownership) ).append(
				" and propertyno = " ).append( Common.sqlChar(rs.getString("propertyNo")) ).append(
				" and serialno = " ).append( Common.sqlChar(rs.getString("serialNo")) ).append(
				" and differencekind = " ).append( Common.sqlChar(rs.getString("differenceKind")) ).append(
				":;:");
	
				sbSQL.append(" delete from UNTBU_FLOOR " ).append(
				" where enterorg = " ).append( Common.sqlChar(enterOrg) ).append(
				" and ownership = " ).append( Common.sqlChar(ownership) ).append(
				" and propertyno = " ).append( Common.sqlChar(rs.getString("propertyNo")) ).append(
				" and serialno = " ).append( Common.sqlChar(rs.getString("serialNo")) ).append(
				" and differencekind = " ).append( Common.sqlChar(rs.getString("differenceKind")) ).append(
				":;:");
				
				sbSQL.append(" delete from UNTBU_ATTACHMENT1 " ).append(
				" where enterorg = " ).append( Common.sqlChar(enterOrg) ).append(
				" and ownership = " ).append( Common.sqlChar(ownership) ).append(
				" and propertyno = " ).append( Common.sqlChar(rs.getString("propertyNo")) ).append(
				" and serialno = " ).append( Common.sqlChar(rs.getString("serialNo")) ).append(
				" and differencekind = " ).append( Common.sqlChar(rs.getString("differenceKind")) ).append(
				":;:");
				
				sbSQL.append(" delete from UNTBU_BASE " ).append(
				" where enterorg = " ).append( Common.sqlChar(enterOrg) ).append(
				" and ownership = " ).append( Common.sqlChar(ownership) ).append(
				" and propertyno = " ).append( Common.sqlChar(rs.getString("propertyNo")) ).append(
				" and serialno = " ).append( Common.sqlChar(rs.getString("serialNo")) ).append(
				" and differencekind = " ).append( Common.sqlChar(rs.getString("differenceKind")) ).append(
				":;:");
				
				sbSQL.append(" delete from UNTBU_COMMONUSE " ).append(
				" where enterorg = " ).append( Common.sqlChar(enterOrg) ).append(
				" and ownership = " ).append( Common.sqlChar(ownership) ).append(
				" and propertyno = " ).append( Common.sqlChar(rs.getString("propertyNo")) ).append(
				" and serialno = " ).append( Common.sqlChar(rs.getString("serialNo")) ).append(
				" and differencekind = " ).append( Common.sqlChar(rs.getString("differenceKind")) ).append(
				":;:");
				
				sbSQL.append(" delete from UNTBU_ATTACHMENT2 " ).append(
				" where enterorg = " ).append( Common.sqlChar(enterOrg) ).append(
				" and ownership = " ).append( Common.sqlChar(ownership) ).append(
				" and propertyno = " ).append( Common.sqlChar(rs.getString("propertyNo")) ).append(
				" and serialno = " ).append( Common.sqlChar(rs.getString("serialNo")) ).append(
				":;:");
				
				sbSQL.append(" delete from UNTBU_VIEWSCENE " ).append(
				" where enterorg = " ).append( Common.sqlChar(enterOrg) ).append(
				" and ownership = " ).append( Common.sqlChar(ownership) ).append(
				" and propertyno = " ).append( Common.sqlChar(rs.getString("propertyNo")) ).append(
				" and serialno = " ).append( Common.sqlChar(rs.getString("serialNo")) ).append(
				":;:");
				
				sbSQL.append(" delete from UNTBU_TAX " ).append(
				" where enterorg = " ).append( Common.sqlChar(enterOrg) ).append(
				" and ownership = " ).append( Common.sqlChar(ownership) ).append(
				" and propertyno = " ).append( Common.sqlChar(rs.getString("propertyNo")) ).append(
				" and serialno = " ).append( Common.sqlChar(rs.getString("serialNo")) ).append(
				":;:");
	
				sbSQL.append(" delete from UNTBU_DRAWPROOF " ).append(
				" where enterorg = " ).append( Common.sqlChar(enterOrg) ).append(
				" and ownership = " ).append( Common.sqlChar(ownership) ).append(
				" and propertyno = " ).append( Common.sqlChar(rs.getString("propertyNo")) ).append(
				" and serialno = " ).append( Common.sqlChar(rs.getString("serialNo")) ).append(
				":;:");		
				
				sbSQL.append(" delete from UNTBU_PRICE " ).append(
				" where enterorg = " ).append( Common.sqlChar(enterOrg) ).append(
				" and ownership = " ).append( Common.sqlChar(ownership) ).append(
				" and propertyno = " ).append( Common.sqlChar(rs.getString("propertyNo")) ).append(
				" and serialno = " ).append( Common.sqlChar(rs.getString("serialNo")) ).append(
				" and differencekind = " ).append( Common.sqlChar(rs.getString("differenceKind")) ).append(
				":;:");	
			}
			rs.close();
		} finally {
			db.closeAll();
		}		
		sbSQL.append(" delete from UNTBU_ADDPROOF where 1=1 " ).append(
		" and enterorg = " ).append( Common.sqlChar(enterOrg) ).append(
		" and ownership = " ).append( Common.sqlChar(ownership) ).append(
		" and caseno = " ).append( Common.sqlChar(caseNo) ).append(
		" and differencekind = " ).append( Common.sqlChar(differenceKind) ).append(
		":;:");

		sbSQL.append(" update UNTBU_REDUCEPROOF set addcaseno = null where 1=1 " ).append(
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
	
	public UNTBU001F  queryOne() throws Exception{
		Database db = new Database();
		UNTBU001F obj = this;
		try {
			String sql=" select b.organSName, a.enterOrg, a.ownership, a.caseNo, a.caseName, a.writeDate, a.writeUnit, a.proofDoc, a.proofNo, a.manageNo, a.summonsNo, a.enterDate, a.verify, a.notes, a.editID, a.editDate, a.editID, a.editDate, a.editTime, a.differenceKind, a.engineeringNo, a.proofYear, a.summonsdate  "+
			" from UNTBU_AddProof a, SYSCA_Organ b where 1=1 " +
			" and a.enterOrg = " + Common.sqlChar(enterOrg) +
			" and a.caseNo = " + Common.sqlChar(caseNo) +
			" and a.ownership = " + Common.sqlChar(ownership) +
			" and a.differenceKind = " + Common.sqlChar(differenceKind) +
			" and a.enterOrg = b.organID ";
	//System.out.println("-- untbu001f queryOne --\n"+sql);		
			UNTCH_Tools ut = new UNTCH_Tools();
			ResultSet rs = db.querySQL(sql);
			if (rs.next()){
				obj.setEnterOrg(rs.getString("enterOrg"));
				obj.setOwnership(rs.getString("ownership"));
				obj.setEnterOrgName(rs.getString("organSName"));
				obj.setCaseNo(rs.getString("caseNo"));
				obj.setCaseName(rs.getString("caseName"));
				obj.setWriteDate(ut._transToROC_Year(rs.getString("writeDate")));
				obj.setWriteUnit(rs.getString("writeUnit"));
				obj.setProofDoc(rs.getString("proofDoc"));
				obj.setProofNo(rs.getString("proofNo"));
				obj.setManageNo(rs.getString("manageNo"));
				obj.setSummonsNo(rs.getString("summonsNo"));
				obj.setEnterDate(ut._transToROC_Year(rs.getString("enterDate")));			
				obj.setVerify(rs.getString("verify"));
				obj.setOldVerify(rs.getString("verify"));
				obj.setNotes(rs.getString("notes"));
				obj.setEditID(rs.getString("editID"));
				obj.setEditDate(ut._transToROC_Year(rs.getString("editDate")));
				obj.setEditTime(rs.getString("editTime"));
				obj.setDifferenceKind(rs.getString("differenceKind"));
				
				obj.setEngineeringNo(rs.getString("engineeringNo"));
				obj.setProofYear(rs.getString("proofYear"));			
				obj.setSummonsDate(ut._transToROC_Year(rs.getString("summonsdate")));
				
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
		String sql="select max(closingYM) as closingYM from UNTGR_Closing " +
			" where enterOrg = " + Common.sqlChar(enterOrg) +
			"";		
		try {		
			rs = db.querySQL(sql);
			if (rs.next()){
			    closingYM = rs.getString("closingYM")==null?"00000":rs.getString("closingYM");
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.closeAll();
		}
		return closingYM;
	}


	public String checkDataForVerify(){
		Database db =new Database();
		ResultSet rs;
		String sql=null;
		String result="";
		try{
			sql="select "+
				" (select count(*) from untbu_manage b where 1=1 and a.propertyno=b.propertyno and a.serialno=b.serialno) as manageCount,"+
				" (select count(*) from untbu_base c where 1=1 and a.propertyno=c.propertyno and a.serialno=c.serialno) as baseCount"+
				" from untbu_building a where 1=1"+
				" and a.caseno="+Common.sqlChar(this.getCaseNo())+
				" and a.differenceKind="+Common.sqlChar(this.getDifferenceKind())+
				" and a.enterOrg="+Common.sqlChar(this.getEnterOrg());
			
			rs=db.querySQL(sql);
			if(rs.next()){
				if(rs.getInt("manageCount")>=1){}else{	result+="manageCount";}
				if(rs.getInt("baseCount")>=1){}else{
					if(!"".equals(result)){result+="&";}
					result+="baseCount";
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			db.closeAll();
		}	
		return result;
	}
}