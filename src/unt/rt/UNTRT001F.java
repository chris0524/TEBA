package unt.rt;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import unt.ch.UNTCH001Q;
import unt.ch.UNTCH_Tools;
import util.Common;
import util.Database;
import util.Datetime;
import util.SuperBean2;
import TDlib_Simple.com.src.DBServerTools;
import TDlib_Simple.com.src.SQLCreator;

/**
 *<br>程式目的：權利主檔資料維護-增加單資料
 *<br>程式代號：untrt001f
 *<br>程式日期：0940929
 *<br>程式作者：cherry
 *<br>--------------------------------------------------------
 *<br>修改作者　　修改日期　　　修改目的
 *<br>--------------------------------------------------------
 */
public class UNTRT001F extends UNTCH001Q {

	/**
	 * 僅提供 useBean , 請勿使用此建構子
	 */
	public UNTRT001F() {

	}
	
	public UNTRT001F(String caller) {
		this.caller = caller;
	}
	
	/** 呼叫此程式執行 insert or update 的呼叫者**/
	private String caller;
	public String getCaller() { return checkGet(caller); }
	public void setCaller(String caller) { this.caller = checkSet(caller); }

	@Override
	public void insert() throws Exception{
		Database db = new Database();
		try {			
			if (!beforeExecCheck(getInsertCheckSQL())){
				setStateInsertError();
				throw new Exception(getErrorMsg());
			}else{
				setEditDate(Datetime.getYYYMMDD());
				setEditTime(Datetime.getHHMMSS());	
				db.excuteSQL_UnicodePrefix(getInsertSQL());		
				setStateInsertSuccess();
				setErrorMsg("新增完成");
			}

		} catch (Exception e) {
			throw e;
		} finally {
			db.closeAll();
		}			
	}
	
	@Override
	public void update() throws Exception{
		Database db = new Database();
		String tempSql[] = new String[1];
		try {			
			if (!beforeExecCheck(getUpdateCheckSQL())){
				setStateUpdateError();
				throw new Exception(getErrorMsg());
			}else{
				db.excuteSQL_UnicodePrefix(getUpdateSQL());
				setStateUpdateSuccess();
				setErrorMsg("修改完成");
			}

		} catch (Exception e) {
			throw e;
		} finally {
			db.closeAll();
		}			
	}
	
	//傳回執行insert前之檢查sql
	protected String[][] getInsertCheckSQL(){
		//取得分號
		Database db = new Database();
		ResultSet rs;	
		String sql="select case when max(serialNo) is null then 1 else (max(serialNo) + 1) end as serialNo from UNTRT_ADDPROOF " +
			" where enterOrg = " + Common.sqlChar(enterOrg) + 
			" and ownership = " + Common.sqlChar(ownership) + 
			" and propertyNo = " + Common.sqlChar(propertyNo) +
			" and differenceKind = " + Common.sqlChar(differenceKind) + 
			"";		
		try {		
			rs = db.querySQL(sql);
			if (rs.next()){
			    setSerialNo(Common.formatFrontZero(rs.getString("serialNo"),7));
			} else {
				setSerialNo("0000001");
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.closeAll();
		}
		//取得增加單編號
	//	setProofNo(MaxClosingYM.getMaxProofNo("UNTRT_AddProof",enterOrg,ownership));
		//===================
		String[][] checkSQLArray = new String[1][4];
	 	checkSQLArray[0][0]=" select count(*) as checkResult from UNTRT_AddProof where 1=1 " + 
			" and enterOrg = " + Common.sqlChar(enterOrg) + 
			" and ownership = " + Common.sqlChar(ownership) + 
			" and propertyNo = " + Common.sqlChar(propertyNo) + 
			" and serialNo = " + Common.sqlChar(serialNo) + 
			" and differenceKind = " + Common.sqlChar(differenceKind) + 
			"";
		checkSQLArray[0][1]=">";
		checkSQLArray[0][2]="0";
		checkSQLArray[0][3]="該筆資料己重複，請重新輸入！";
		return checkSQLArray;
	}

	/**
	 * 明細資料的新增
	 */
	protected String[] getInsertSQL(){
		String[] execSQLArray = new String[1];
		execSQLArray[0] = new SQLCreator()._obtainSQLforInsert("UNTRT_ADDPROOF", getPKMap(), getRecordMap());
		return execSQLArray;
	}
	
	private String queryOldVerify() {
		Database db = null;
		try {
			db = new Database();
			return db.getLookupField("select verify from UNTRT_ADDPROOF where enterorg=" + Common.sqlChar(this.getEnterOrg()) + " and caseno = " + Common.sqlChar(this.getCaseNo()));
		} finally {
			if (db != null) {
				db.closeAll();
			}
		}
	}
	
	/**
	 * 明細修改 or 單據入帳/回復入帳 的檢查
	 */
	@Override
	public String[][] getUpdateCheckSQLBeforeAction() {
		List<String[]> sqls = new ArrayList<String[]>();
		String[] tmpchk = new String[4];
		
		String oldVerify = this.queryOldVerify();
		if ("N".equals(oldVerify) && "Y".equals(this.getVerify())) {
		} else if ("Y".equals(oldVerify) && "N".equals(this.getVerify())) {
			// 回復入帳時檢查 不可存在於 增減值單/減損單
			tmpchk = new String[4];
			tmpchk[0] = " select count(*) as checkResult from UNTRT_ADDPROOF a "
					  + " where a.enterorg = " + Common.sqlChar(this.getEnterOrg()) 
					  + " and a.caseno = " + Common.sqlChar(this.getCaseNo()) 
					  + " and (EXISTS (select 1 from UNTRT_ADJUSTPROOF b where a.enterorg = b.enterorg and a.ownership = b.ownership and a.differencekind = b.differencekind and a.propertyno = b.propertyno and a.serialno = b.serialno)"
					  + " or EXISTS (select 1 from UNTRT_REDUCEPROOF b where a.enterorg = b.enterorg and a.ownership = b.ownership and a.differencekind = b.differencekind and a.propertyno = b.propertyno and a.serialno = b.serialno)"
					  + " or EXISTS (select 1 from UNTMP_MOVEDETAIL b where a.enterorg = b.enterorg and a.ownership = b.ownership and a.differencekind = b.differencekind and a.propertyno = b.propertyno and a.serialno = b.serialno)"
					  + " );";
			tmpchk[1] = "!=";
			tmpchk[2] = "0";
			tmpchk[3] = "權利資料已做過增減值或減損作業或移動作業，請先刪除增減值或減損作業或移動作業之資料再回此作業取消入帳!";
			sqls.add(tmpchk);
		}
		
		return sqls.toArray(new String[sqls.size()][4]);
	}
	
	protected String[][] getUpdateCheckSQL(){
		List<String[]> sqls = new ArrayList<String[]>();
		String[] tmpchk = new String[4];
		tmpchk[0] = " select datastate as checkResult from UNTRT_ADDPROOF where 1=1 " + 
					" and enterorg = " + Common.sqlChar(enterOrg) + 
					" and ownership = " + Common.sqlChar(ownership) + 
					" and propertyno = " + Common.sqlChar(propertyNo) + 
					" and serialno = " + Common.sqlChar(serialNo) +
					" and differencekind = " + Common.sqlChar(differenceKind) + 
					"";
		tmpchk[1] = "=";
		tmpchk[2] = "2";
		tmpchk[3]=  "該筆資料己減損，無法修改！";
		sqls.add(tmpchk);
		return sqls.toArray(new String[sqls.size()][4]);
	}
	
	/**
	 * 明細資料維護的修改
	 */
	protected String[] getUpdateSQL(){
		String[] execSQLArray = new String[1];
		execSQLArray[0] = new SQLCreator()._obtainSQLforUpdate("UNTRT_ADDPROOF", getPKMap(), getRecordMap());
		return execSQLArray;	
	}
	
	/**
	 * 入帳/回復入帳
	 */
	public void execVerify(Connection conn) throws Exception {
		
		Statement statement = null;
		try {
			String oldverify = this.queryOldVerify();
			if (!oldverify.equals(this.getVerify())) {
				statement = conn.createStatement();
				String sql = "update UNTRT_ADDPROOF set "+
						" verify = " + Common.sqlChar(this.getVerify()) + "," +
						" enterdate = " + Common.sqlChar(new UNTCH_Tools()._transToCE_Year(getEnterDate())) + "," +
						" editid = " + Common.sqlChar(getEditID()) + "," +
						" editdate = " + Common.sqlChar(new UNTCH_Tools()._transToCE_Year(getEditDate())) + "," +
						" edittime = " + Common.sqlChar(getEditTime()) + 	                
						" where 1=1 " + 
						" and enterorg = " + Common.sqlChar(enterOrg) +
						" and ownership = " + Common.sqlChar(ownership) +
						" and caseno = " + Common.sqlChar(caseNo) +
						" and differencekind = " + Common.sqlChar(differenceKind);
				statement.execute(sql);
			}
		} finally {
			if (statement != null) {
				try {
					statement.close();
					statement = null;
				} catch (Exception e) {
					// ignore
				}
			}
		}
	}
	
	//傳回delete sql
	protected String[] getDeleteSQL(){
		String[] execSQLArray = new String[2];
		execSQLArray[0]=" delete UNTRT_ADDPROOF where 1=1 " +
				" and enterorg = " + Common.sqlChar(enterOrg) +
				" and ownership = " + Common.sqlChar(ownership) +
				" and propertyno = " + Common.sqlChar(propertyNo) +
				" and serialno = " + Common.sqlChar(serialNo) +
				" and differencekind = " + Common.sqlChar(differenceKind) +
				"";
		execSQLArray[1]=" delete UNTRT_ADDDETAIL where 1=1 " +
				" and enterorg = " + Common.sqlChar(enterOrg) +
				" and ownership = " + Common.sqlChar(ownership) +
				" and propertyno = " + Common.sqlChar(propertyNo) +
				" and serialno = " + Common.sqlChar(serialNo) +
				" and differencekind = " + Common.sqlChar(differenceKind) +
				"";
		return execSQLArray;
	}

	protected void queryDeleteData(SuperBean2 sb){
		UNTCH_Tools ut = new UNTCH_Tools();
		Map map = queryPropertyNofrom("UNTRT_ADDPROOF",sb);					
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
	
	public UNTRT001F  queryOne() throws Exception{
		Database db = new Database();
		UNTRT001F obj = this;
		try {
			String sql=" select a.*, " +
						" (select codename from SYSCA_CODE z where codekindid = 'SKD' and z.codeid = a.sourcekind) as sourcekindName, " +
						"(select codename from SYSCA_CODE z where codekindid = 'CAA' and z.codeid = a.cause and codecon1 in (1,4)) as causeName, " +
						"(select organsname from SYSCA_ORGAN z where z.organid = a.enterorg) as enterOrgName," +
						"(select codename from SYSCA_CODE z where z.codekindid = 'OWA' and z.codeid = a.ownership) as ownershipName," +
						"(select codename from SYSCA_CODE z where z.codekindid = 'DFK' and z.codeid = a.differenceKind) as differenceKindName," +
						"(select propertyname from SYSPK_PROPERTYCODE z where z.enterorg = a.enterorg and z.propertyno = a.propertyno) as propertyNoName," +
						" (select placename from SYSCA_PLACE z where z.enterorg = a.enterorg and z.placeno = a.originalPlace1) as originalPlace1Name," +
						" (select placename from SYSCA_PLACE z where z.enterorg = a.enterorg and z.placeno = a.place1) as place1Name, a.individualsetdepr, a.verifyym" +
						" from UNTRT_AddProof a where 1=1"+"\n"+
						" and a.enterOrg = " + Common.sqlChar(enterOrg) +"\n"+
						" and a.ownership = " + Common.sqlChar(ownership) +"\n"+
						" and a.propertyNo = " + Common.sqlChar(propertyNo) +"\n"+
						" and a.serialNo = " + Common.sqlChar(serialNo) +"\n"+
						" and a.differenceKind = " + Common.sqlChar(differenceKind) +"\n"+
						"";
	
			UNTCH_Tools ul = new UNTCH_Tools();
			ResultSet rs = db.querySQL(sql);
			if (rs.next()){
				obj.setEnterOrgName(rs.getString("EnterOrgName"));
				obj.setOwnershipName(rs.getString("OwnershipName"));
				obj.setDifferenceKindName(rs.getString("DifferenceKindName"));
				obj.setPropertyNoName(rs.getString("PropertyNoName"));
				
				obj.setEnterOrg(rs.getString("EnterOrg"));
				obj.setOwnership(rs.getString("Ownership"));
				obj.setCaseNo(rs.getString("CaseNo"));
				obj.setCaseName(rs.getString("CaseName"));
				obj.setDifferenceKind(rs.getString("DifferenceKind"));
				obj.setEngineeringNo(rs.getString("EngineeringNo"));
				obj.setEngineeringNoName(ul._getEngineeringNoName(rs.getString("enterOrg"),rs.getString("engineeringNo")));
				obj.setCaseSerialNo(Common.formatFrontZero(rs.getString("CaseSerialNo"),5));
				obj.setPropertyNo(rs.getString("PropertyNo"));
				obj.setSerialNo(rs.getString("SerialNo"));
				obj.setPropertyName1(rs.getString("PropertyName1"));
				obj.setWriteDate(ul._transToROC_Year(rs.getString("WriteDate")));
				obj.setWriteUnit(rs.getString("WriteUnit"));
				obj.setProofYear(rs.getString("ProofYear"));
				obj.setProofDoc(rs.getString("ProofDoc"));
				obj.setProofNo(rs.getString("ProofNo"));
				obj.setManageNo(rs.getString("ManageNo"));
				obj.setSummonsNo(rs.getString("SummonsNo"));
				obj.setCause(rs.getString("Cause"));
				obj.setCauseName(rs.getString("CauseName"));
				obj.setCause1(rs.getString("Cause1"));
				obj.setEnterDate(ul._transToROC_Year(rs.getString("EnterDate")));
				obj.setOriginalNote(rs.getString("OriginalNote"));
				obj.setDataState(rs.getString("DataState"));
				obj.setVerify(rs.getString("Verify"));
				obj.setPropertyKind(rs.getString("PropertyKind"));
				obj.setFundType(rs.getString("FundType"));
				obj.setBuyDate(ul._transToROC_Year(rs.getString("BuyDate")));
				obj.setSourceKind(rs.getString("SourceKind"));
				obj.setSourceKindName(rs.getString("SourceKindName"));
				obj.setSourceDate(ul._transToROC_Year(rs.getString("SourceDate")));
				obj.setSourceDoc(rs.getString("SourceDoc"));
				obj.setMeat(rs.getString("Meat"));
				obj.setProofDoc1(rs.getString("ProofDoc1"));
				obj.setOriginalBV(rs.getString("OriginalBV"));
				obj.setBookValue(rs.getString("BookValue"));
				obj.setRegisterCause(rs.getString("RegisterCause"));
				obj.setRegisterDate(ul._transToROC_Year(rs.getString("RegisterDate")));
				obj.setSetPeriod(rs.getString("SetPeriod"));
				obj.setCommonObligee(rs.getString("CommonObligee"));
				obj.setSetPerson(rs.getString("SetPerson"));
				obj.setPayDate(ul._transToROC_Year(rs.getString("PayDate")));
				obj.setInterest(rs.getString("Interest"));
				obj.setRent(rs.getString("Rent"));
				obj.setNotes1(rs.getString("Notes1"));
				obj.setOriginalMoveDate(ul._transToROC_Year(rs.getString("OriginalMoveDate")));
				obj.setOriginalKeepUnit(rs.getString("OriginalKeepUnit"));
				obj.setOriginalKeeper(rs.getString("OriginalKeeper"));
				obj.setOriginalUseUnit(rs.getString("OriginalUseUnit"));
				obj.setOriginalUser(rs.getString("OriginalUser"));
				obj.setOriginalUserNote(rs.getString("OriginalUserNote"));
				obj.setOriginalPlace1(rs.getString("OriginalPlace1"));
				obj.setOriginalPlace(rs.getString("OriginalPlace"));
				obj.setMoveDate(ul._transToROC_Year(rs.getString("MoveDate")));
				obj.setKeepUnit(rs.getString("KeepUnit"));
				obj.setKeeper(rs.getString("Keeper"));
				obj.setUseUnit(rs.getString("UseUnit"));
				obj.setUserNo(rs.getString("UserNo"));
				obj.setUserNote(rs.getString("UserNote"));
				obj.setPlace1(rs.getString("Place1"));
				obj.setPlace(rs.getString("Place"));
				obj.setReduceDate(ul._transToROC_Year(rs.getString("ReduceDate")));
				obj.setReduceCause(rs.getString("ReduceCause"));
				obj.setReduceCause1(rs.getString("ReduceCause1"));
				obj.setOldPropertyNo(rs.getString("OldPropertyNo"));
				obj.setOldSerialNo(rs.getString("OldSerialNo"));
				obj.setNotes(rs.getString("Notes"));
				obj.setEditID(rs.getString("EditID"));
				obj.setEditDate(ul._transToROC_Year(rs.getString("EditDate")));
				obj.setEditTime(rs.getString("EditTime"));
	
				obj.setOriginalPlace1Name(rs.getString("Originalplace1Name"));
				obj.setPlace1Name(rs.getString("place1Name"));
				
				obj.setSummonsDate(ul._transToROC_Year(rs.getString("summonsdate")));
				
				obj.setOriginalLimitYear(Common.get(rs.getString("originallimityear")));
				obj.setOtherLimitYear(Common.get(rs.getString("otherlimityear")));
				obj.setNetValue(Common.get(rs.getString("netvalue")));
				obj.setOriginalDeprMethod(Common.get(rs.getString("originaldeprmethod")));
				obj.setOriginalBuildFeeCB(Common.get(rs.getString("originalbuildfeecb")));
				obj.setOriginalDeprUnitCB(Common.get(rs.getString("originaldeprunitcb")));
				obj.setOriginalDeprPark(Common.get(rs.getString("originaldeprpark")));
				obj.setOriginalDeprUnit(Common.get(rs.getString("originaldeprunit")));
				obj.setOriginalDeprUnit1(Common.get(rs.getString("originaldeprunit1")));
				obj.setOriginalDeprAccounts(Common.get(rs.getString("originaldepraccounts")));
				obj.setOriginalScrapValue(Common.get(rs.getString("originalscrapvalue")));
				obj.setOriginalDeprAmount(Common.get(rs.getString("originaldepramount")));
				obj.setOriginalaccumdepr1(rs.getString("originalaccumdepr1"));
				obj.setOriginalaccumdepr2(rs.getString("originalaccumdepr2"));
				obj.setOriginalAccumDepr(Common.get(rs.getString("originalaccumdepr")));
				obj.setOriginalApportionMonth(Common.get(rs.getString("originalapportionmonth")));
				obj.setOriginalMonthDepr(Common.get(rs.getString("originalmonthdepr")));
				obj.setOriginalMonthDepr1(Common.get(rs.getString("originalmonthdepr1")));
				obj.setOriginalApportionEndYM(ul._transToROC_Year(rs.getString("originalapportionendym")));
				obj.setDeprMethod(Common.get(rs.getString("deprmethod")));
				obj.setBuildFeeCB(Common.get(rs.getString("buildfeecb")));
				obj.setDeprUnitCB(Common.get(rs.getString("deprunitcb")));
				obj.setDeprPark(Common.get(rs.getString("deprpark")));
				obj.setDeprUnit(Common.get(rs.getString("deprunit")));
				obj.setDeprUnit1(Common.get(rs.getString("deprunit1")));
				obj.setDeprAccounts(Common.get(rs.getString("depraccounts")));
				obj.setScrapValue(Common.get(rs.getString("scrapvalue")));
				obj.setDeprAmount(Common.get(rs.getString("depramount")));
				obj.setAccumDepr(Common.get(rs.getString("accumdepr")));
				obj.setApportionMonth(Common.get(rs.getString("apportionmonth")));
				obj.setMonthDepr(Common.get(rs.getString("monthdepr")));
				obj.setMonthDepr1(Common.get(rs.getString("monthdepr1")));
				obj.setApportionEndYM(ul._transToROC_Year(rs.getString("apportionendym")));
				obj.setNoDeprSet(Common.get(rs.getString("nodeprset")));
				obj.setEscrowOriValue(Common.get(rs.getString("escroworivalue")));
				obj.setEscrowOriAccumDepr(Common.get(rs.getString("escroworiaccumdepr")));
				obj.setValuable(Common.get(rs.getString("valuable")));
				
				obj.setIndividualSetDepr(rs.getString("individualsetdepr"));
				obj.setVerifyYM(ul._transToROC_Year(rs.getString("verifyym")));
			}
			setStateQueryOneSuccess();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.closeAll();
		}
		return obj;
	}

	public ArrayList queryAll() throws Exception{
		ArrayList objList=new ArrayList();
		Database db = new Database();	
		
		try {
			String sql = " select distinct a.enterOrg, a.caseNo, a.propertyNo, a.serialNo, a.differenceKind,a.valuable " +
					" (select x.propertyName from SYSPK_PropertyCode x where a.propertyNo = x.propertyNo)as propertyName, " +
					" a.cause, a.enterDate, c.organSName, d.codeName, " +"\n"+
						 " a.ownership ,(select x.codeName from sysca_code x where a.ownership = x.codeid and x.codekindid = 'OWA') as ownershipName," +"\n"+
						 " case a.verify when 'Y' then '是' else '否' end as verify, "+"\n"+
						 " a.propertyKind ,(select x.codeName from sysca_code x where a.propertyKind = x.codeid and x.codekindid = 'PKD') as propertyKindName," +"\n"+
						 " case a.dataState when '1' then '現存' when '2' then '已減損' else '' end as dataState"+"\n"+
						 " from UNTRT_AddProof a " +
						 " left join SYSCA_Organ c on a.enterOrg = c.organID" +
						 " left join SYSCA_Code d on a.cause = d.codeID and d.codeKindID = 'CAA'" +
						 " left join UNTRT_AddDetail e on a.serialNo=e.serialNo and a.propertyNo=e.propertyNo and a.enterOrg=e.enterOrg and a.ownership=e.ownership and a.differenceKind=e.differenceKind" +
						 " where 1=1"+"\n"+
						 ""; 
			
				sql+=" and a.enterOrg = " + Common.sqlChar(getEnterOrg()) ;
				sql+=" and a.ownership = " + Common.sqlChar(getOwnership()) ;
				sql+=" and a.propertyNo = " + Common.sqlChar(getPropertyNo()) ;
				sql+=" and a.differenceKind=" + Common.sqlChar(getDifferenceKind());
				sql+=" and a.serialNo=" + Common.sqlChar(getSerialNo());
			

			ResultSet rs = db.querySQL(sql,true);
			processCurrentPageAttribute(rs);			
			if (getTotalRecord() > 0) {
				int count = getRecordStart();
				int end = getRecordEnd();
				do {
					if (count > end)
						break;
				String rowArray[]=new String[18];
				rowArray[0]=rs.getString("organSName"); 
				rowArray[1]=rs.getString("ownershipName"); 
				rowArray[2]=rs.getString("caseNo"); 
				rowArray[3]=rs.getString("propertyNo"); 
				rowArray[4]=rs.getString("serialNo"); 
				rowArray[5]=rs.getString("propertyName"); 
				rowArray[6]=rs.getString("codeName"); 
				rowArray[7]=rs.getString("verify"); 
				rowArray[8]=new UNTCH_Tools()._transToROC_Year(rs.getString("enterDate")); 
				rowArray[9]=rs.getString("dataState"); 
				rowArray[10]=""; 
				rowArray[11]=rs.getString("propertyKind"); 
				rowArray[12]=rs.getString("cause");
				rowArray[13]=rs.getString("enterOrg");
				rowArray[14]=rs.getString("ownership");
				rowArray[15]=rs.getString("propertyNo"); 
				rowArray[16]=rs.getString("serialNo");
				rowArray[17]=rs.getString("differenceKind"); 
				objList.add(rowArray);
				count++;
				} while (rs.next());
			}
			setStateQueryAllSuccess();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.closeAll();
		}
		return objList;
	}
	
	private Map getPKMap(){
		Map map = new HashMap();
		
		map.put("enterorg", getEnterOrg());
		map.put("ownership", getOwnership());
		map.put("caseno", getCaseNo());
		map.put("differencekind", getDifferenceKind());
		map.put("propertyno", getPropertyNo());
		map.put("serialno", getSerialNo());
		
		return map;
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private Map getRecordMap(){
		Map map = new HashMap();
		UNTCH_Tools ul = new UNTCH_Tools();
		if(!"UNTCH001F02_2".equals(this.getCaller())) {
			map.put("enterorg", getEnterOrg());
			map.put("ownership", getOwnership());
			map.put("caseno", getCaseNo());
			map.put("casename", getCaseName());
			map.put("differencekind", getDifferenceKind());
			map.put("engineeringno", getEngineeringNo());
			map.put("caseserialno", getCaseSerialNo());
			map.put("propertyno", getPropertyNo());
			map.put("serialno", getSerialNo());
			map.put("otherlimityear",getOtherLimitYear());
			map.put("originallimityear",getOriginalLimitYear());		
			map.put("writedate", ul._transToCE_Year(getWriteDate()));
			map.put("writeunit", getWriteUnit());
			map.put("proofyear", getProofYear());
			map.put("proofdoc", getProofDoc());
			map.put("proofno", getProofNo());
			map.put("manageno", getManageNo());
			map.put("summonsno", getSummonsNo());
			map.put("cause", getCause());
			map.put("cause1", getCause1());
			map.put("enterdate", ul._transToCE_Year(getEnterDate()));
			map.put("originalnote", getOriginalNote());
			map.put("datastate", getDataState());
			map.put("verify", getVerify());
			map.put("propertykind", getPropertyKind());
			map.put("fundtype", getFundType());
			map.put("buydate", ul._transToCE_Year(getBuyDate()));
			map.put("sourcekind", getSourceKind());
			map.put("sourcedate", ul._transToCE_Year(getSourceDate()));
			map.put("sourcedoc", getSourceDoc());
			map.put("meat", getMeat());
			map.put("originalbv", getOriginalBV());
			map.put("bookvalue", getBookValue());
			map.put("registercause", getRegisterCause());
			map.put("registerdate", ul._transToCE_Year(getRegisterDate()));
			map.put("setperiod", getSetPeriod());
			map.put("commonobligee", getCommonObligee());
			map.put("setperson", getSetPerson());
			map.put("paydate", ul._transToCE_Year(getPayDate()));
			map.put("interest", getInterest());
			map.put("rent", getRent());
			map.put("notes1", getNotes1());
			map.put("originalmovedate", ul._transToCE_Year(getOriginalMoveDate()));
			map.put("originalkeepunit", getOriginalKeepUnit());
			map.put("originalkeeper", getOriginalKeeper());
			map.put("originaluseunit", getOriginalUseUnit());
			map.put("originaluser", getOriginalUser());
			map.put("originalusernote", getOriginalUserNote());
			map.put("originalplace1", getOriginalPlace1());
			map.put("originalplace", getOriginalPlace());
			map.put("movedate", ul._transToCE_Year(getMoveDate()));
			map.put("keepunit", getKeepUnit());
			map.put("keeper", getKeeper());
			map.put("useunit", getUseUnit());
			map.put("userno", getUserNo());
			map.put("reducedate", ul._transToCE_Year(getReduceDate()));
			map.put("reducecause", getReduceCause());
			map.put("reducecause1", getReduceCause1());
			map.put("oldpropertyno", getOldPropertyNo());
			map.put("oldserialno", getOldSerialNo());
			map.put("summonsdate", getSummonsDate());
			map.put("otherpropertyunit", getOtherPropertyUnit());
			map.put("othermaterial", getOtherMaterial());
			map.put("place1", getPlace1());
			map.put("place", getPlace());
			map.put("usernote", getUserNote());
	
			if ("UNTCH001F02".equals(this.getCaller())) {
				long originalAccumDepr = Common.getLong(this.getOriginalAccumDepr());
				long netValue = Common.getLong(this.getNetValue());
				
				map.put("netvalue", netValue - originalAccumDepr);
				map.put("originaldeprmethod", this.getOriginalDeprMethod());
				map.put("originalbuildfeecb", this.getOriginalBuildFeeCB());
				map.put("originaldeprunitcb", this.getOriginalDeprUnitCB());
				map.put("originaldeprpark", this.getOriginalDeprPark());
				map.put("originaldeprunit", this.getOriginalDeprUnit());
				map.put("originaldeprunit1", this.getOriginalDeprUnit1());
				map.put("originaldepraccounts", this.getOriginalDeprAccounts());
				map.put("originalscrapvalue", this.getOriginalScrapValue());
				map.put("originaldepramount", this.getOriginalDeprAmount());
				map.put("originalaccumdepr1", this.getOriginalaccumdepr1());
				map.put("originalaccumdepr2", this.getOriginalaccumdepr2());
				map.put("originalaccumdepr", originalAccumDepr);
				map.put("originalapportionmonth", this.getOriginalApportionMonth());
				map.put("originalmonthdepr", this.getOriginalMonthDepr());
				map.put("originalmonthdepr1", this.getOriginalMonthDepr1());
				map.put("originalapportionendym", ul._transToCE_Year(this.getOriginalApportionEndYM()));
				map.put("deprmethod", this.getDeprMethod());
				map.put("buildfeecb", this.getBuildFeeCB());
				map.put("deprunitcb", this.getDeprUnitCB());
				map.put("deprpark", this.getDeprPark());
				map.put("deprunit", this.getDeprUnit());
				map.put("deprunit1", this.getDeprUnit1());
				map.put("depraccounts", this.getDeprAccounts());
				map.put("scrapvalue", this.getScrapValue());
				map.put("depramount", this.getDeprAmount());
				map.put("accumdepr", this.getAccumDepr());
				map.put("apportionmonth", this.getApportionMonth());
				map.put("monthdepr", this.getMonthDepr());
				map.put("monthdepr1", this.getMonthDepr1());
				map.put("apportionendym", ul._transToCE_Year(this.getApportionEndYM()));
				map.put("nodeprset", this.getNoDeprSet());
				map.put("escroworivalue", this.getEscrowOriValue());
				map.put("escroworiaccumdepr", this.getEscrowOriAccumDepr());
				map.put("valuable", this.getValuable());
				
				map.put("individualSetDepr", getIndividualSetDepr());
				map.put("verifyYM", ul._transToCE_Year(getVerifyYM()));
			}
		}

		map.put("propertyname1", getPropertyName1());
		map.put("notes", getNotes());
		map.put("editid", getEditID());
		map.put("editdate", ul._transToCE_Year(getEditDate()));
		map.put("edittime", getEditTime());
		map.put("proofdoc1", getProofDoc1());
		
		return map;
	}


	private String enterOrgName;
	private String ownershipName;
	private String differenceKindName;
	private String engineeringNoName;
	private String propertyNoName;
	
	private String enterOrg;
	private String ownership;
	private String caseNo;
	private String caseName;
	private String differenceKind;
	private String engineeringNo;
	private String caseSerialNo;
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
	private String enterDate;
	private String originalNote;
	private String dataState;
	private String verify;
	private String propertyKind;
	private String fundType;
	private String buyDate;
	private String sourceKind;
	private String sourceDate;
	private String sourceDoc;
	private String meat;
	private String proofDoc1;
	private String originalBV;
	private String bookValue;
	private String registerCause;
	private String registerDate;
	private String setPeriod;
	private String commonObligee;
	private String setPerson;
	private String payDate;
	private String interest;
	private String rent;
	private String notes1;
	private String originalMoveDate;
	private String originalKeepUnit;
	private String originalKeeper;
	private String originalUseUnit;
	private String originalUser;
	private String originalUserNote;
	private String originalPlace1;
	private String originalPlace;
	private String moveDate;
	private String keepUnit;
	private String keeper;
	private String useUnit;
	private String userNo;
	private String userNote;
	private String place1;
	private String place;
	private String reduceDate;
	private String reduceCause;
	private String reduceCause1;
	private String oldPropertyNo;
	private String oldSerialNo;
	private String notes;
	private String editID;
	private String editDate;
	private String editTime;
	private String valuable;
	
	
	public String getValuable() { return checkGet(valuable); }
	public void setValuable(String valuable) { this.valuable = checkSet(valuable); }
	public String getEnterOrgName() {return checkGet(enterOrgName);}
	public void setEnterOrgName(String enterOrgName) {this.enterOrgName = checkSet(enterOrgName);}
	public String getOwnershipName() {return checkGet(ownershipName);}
	public void setOwnershipName(String ownershipName) {this.ownershipName = checkSet(ownershipName);}
	public String getDifferenceKindName() {return checkGet(differenceKindName);}
	public void setDifferenceKindName(String differenceKindName) {this.differenceKindName = checkSet(differenceKindName);}
	public String getEngineeringNoName() {return checkGet(engineeringNoName);}
	public void setEngineeringNoName(String engineeringNoName) {this.engineeringNoName = checkSet(engineeringNoName);}
	public String getPropertyNoName() {return checkGet(propertyNoName);}
	public void setPropertyNoName(String propertyNoName) {this.propertyNoName = checkSet(propertyNoName);}
	public String getEnterOrg() {return checkGet(enterOrg);}
	public void setEnterOrg(String enterOrg) {this.enterOrg = checkSet(enterOrg);}
	public String getOwnership() {return checkGet(ownership);}
	public void setOwnership(String ownership) {this.ownership = checkSet(ownership);}
	public String getCaseNo() {return checkGet(caseNo);}
	public void setCaseNo(String caseNo) {this.caseNo = checkSet(caseNo);}
	public String getCaseName() {return checkGet(caseName);}
	public void setCaseName(String caseName) {this.caseName = checkSet(caseName);}
	public String getDifferenceKind() {return checkGet(differenceKind);}
	public void setDifferenceKind(String differenceKind) {this.differenceKind = checkSet(differenceKind);}
	public String getEngineeringNo() {return checkGet(engineeringNo);}
	public void setEngineeringNo(String engineeringNo) {this.engineeringNo = checkSet(engineeringNo);}
	public String getCaseSerialNo() {return checkGet(caseSerialNo);}
	public void setCaseSerialNo(String caseSerialNo) {this.caseSerialNo = checkSet(caseSerialNo);}
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
	public String getEnterDate() {return checkGet(enterDate);}
	public void setEnterDate(String enterDate) {this.enterDate = checkSet(enterDate);}
	public String getOriginalNote() {return checkGet(originalNote);}
	public void setOriginalNote(String originalNote) {this.originalNote = checkSet(originalNote);}
	public String getDataState() {return checkGet(dataState);}
	public void setDataState(String dataState) {this.dataState = checkSet(dataState);}
	public String getVerify() {return checkGet(verify);}
	public void setVerify(String verify) {this.verify = checkSet(verify);}
	public String getPropertyKind() {return checkGet(propertyKind);}
	public void setPropertyKind(String propertyKind) {this.propertyKind = checkSet(propertyKind);}
	public String getFundType() {return checkGet(fundType);}
	public void setFundType(String fundType) {this.fundType = checkSet(fundType);}
	public String getBuyDate() {return checkGet(buyDate);}
	public void setBuyDate(String buyDate) {this.buyDate = checkSet(buyDate);}
	public String getSourceKind() {return checkGet(sourceKind);}
	public void setSourceKind(String sourceKind) {this.sourceKind = checkSet(sourceKind);}
	public String getSourceDate() {return checkGet(sourceDate);}
	public void setSourceDate(String sourceDate) {this.sourceDate = checkSet(sourceDate);}
	public String getSourceDoc() {return checkGet(sourceDoc);}
	public void setSourceDoc(String sourceDoc) {this.sourceDoc = checkSet(sourceDoc);}
	public String getMeat() {return checkGet(meat);}
	public void setMeat(String meat) {this.meat = checkSet(meat);}
	public String getProofDoc1() {return checkGet(proofDoc1);}
	public void setProofDoc1(String proofDoc1) {this.proofDoc1 = checkSet(proofDoc1);}
	public String getOriginalBV() {return checkGet(originalBV);}
	public void setOriginalBV(String originalBV) {this.originalBV = checkSet(originalBV);}
	public String getBookValue() {return checkGet(bookValue);}
	public void setBookValue(String bookValue) {this.bookValue = checkSet(bookValue);}
	public String getRegisterCause() {return checkGet(registerCause);}
	public void setRegisterCause(String registerCause) {this.registerCause = checkSet(registerCause);}
	public String getRegisterDate() {return checkGet(registerDate);}
	public void setRegisterDate(String registerDate) {this.registerDate = checkSet(registerDate);}
	public String getSetPeriod() {return checkGet(setPeriod);}
	public void setSetPeriod(String setPeriod) {this.setPeriod = checkSet(setPeriod);}
	public String getCommonObligee() {return checkGet(commonObligee);}
	public void setCommonObligee(String commonObligee) {this.commonObligee = checkSet(commonObligee);}
	public String getSetPerson() {return checkGet(setPerson);}
	public void setSetPerson(String setPerson) {this.setPerson = checkSet(setPerson);}
	public String getPayDate() {return checkGet(payDate);}
	public void setPayDate(String payDate) {this.payDate = checkSet(payDate);}
	public String getInterest() {return checkGet(interest);}
	public void setInterest(String interest) {this.interest = checkSet(interest);}
	public String getRent() {return checkGet(rent);}
	public void setRent(String rent) {this.rent = checkSet(rent);}
	public String getNotes1() {return checkGet(notes1);}
	public void setNotes1(String notes1) {this.notes1 = checkSet(notes1);}
	public String getOriginalMoveDate() {return checkGet(originalMoveDate);}
	public void setOriginalMoveDate(String originalMoveDate) {this.originalMoveDate = checkSet(originalMoveDate);}
	public String getOriginalKeepUnit() {return checkGet(originalKeepUnit);}
	public void setOriginalKeepUnit(String originalKeepUnit) {this.originalKeepUnit = checkSet(originalKeepUnit);}
	public String getOriginalKeeper() {return checkGet(originalKeeper);}
	public void setOriginalKeeper(String originalKeeper) {this.originalKeeper = checkSet(originalKeeper);}
	public String getOriginalUseUnit() {return checkGet(originalUseUnit);}
	public void setOriginalUseUnit(String originalUseUnit) {this.originalUseUnit = checkSet(originalUseUnit);}
	public String getOriginalUser() {return checkGet(originalUser);}
	public void setOriginalUser(String originalUser) {this.originalUser = checkSet(originalUser);}
	public String getOriginalUserNote() {return checkGet(originalUserNote);}
	public void setOriginalUserNote(String originalUserNote) {this.originalUserNote = checkSet(originalUserNote);}
	public String getOriginalPlace1() {return checkGet(originalPlace1);}
	public void setOriginalPlace1(String originalPlace1) {this.originalPlace1 = checkSet(originalPlace1);}
	public String getOriginalPlace() {return checkGet(originalPlace);}
	public void setOriginalPlace(String originalPlace) {this.originalPlace = checkSet(originalPlace);}
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
	public String getReduceDate() {return checkGet(reduceDate);}
	public void setReduceDate(String reduceDate) {this.reduceDate = checkSet(reduceDate);}
	public String getReduceCause() {return checkGet(reduceCause);}
	public void setReduceCause(String reduceCause) {this.reduceCause = checkSet(reduceCause);}
	public String getReduceCause1() {return checkGet(reduceCause1);}
	public void setReduceCause1(String reduceCause1) {this.reduceCause1 = checkSet(reduceCause1);}
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
	
	String verifyError;
	public String getVerifyError(){ return checkGet(verifyError); }
	public void setVerifyError(String s){ verifyError=checkSet(s); }
	String oldVerify;
	public String getOldVerify() {return checkGet(oldVerify);}
	public void setOldVerify(String oldVerify) {this.oldVerify = checkSet(oldVerify);}
	
	
	private String sourceKindName;
	private String causeName;
	public String getSourceKindName() {return checkGet(sourceKindName);}
	public void setSourceKindName(String sourceKindName) {this.sourceKindName = checkSet(sourceKindName);}
	public String getCauseName() {return checkGet(causeName);}
	public void setCauseName(String causeName) {this.causeName = checkSet(causeName);}
	
	private String place1Name;
	private String originalPlace1Name;
	public String getPlace1Name() {return checkGet(place1Name);}
	public void setPlace1Name(String place1Name) {this.place1Name = checkSet(place1Name);}
	public String getOriginalPlace1Name() {return checkGet(originalPlace1Name);}
	public void setOriginalPlace1Name(String originalPlace1Name) {this.originalPlace1Name = checkSet(originalPlace1Name);}
	
	private String summonsDate;
	public String getSummonsDate() {return checkGet(summonsDate);}
	public void setSummonsDate(String summonsDate) {this.summonsDate = checkSet(summonsDate);}
		
	private String originalLimitYear;
	public String getOriginalLimitYear() { return checkGet(originalLimitYear); }
	public void setOriginalLimitYear(String originalLimitYear) { this.originalLimitYear = checkSet(originalLimitYear); }
	
	private String otherLimitYear;
	public String getOtherLimitYear() {return checkGet(otherLimitYear);}
	public void setOtherLimitYear(String otherLimitYear) {this.otherLimitYear = checkSet(otherLimitYear);}
	
	private String otherPropertyUnit;
	private String otherMaterial;
	public String getOtherPropertyUnit() {return checkGet(otherPropertyUnit);}	
	public void setOtherPropertyUnit(String otherPropertyUnit) {this.otherPropertyUnit = checkSet(otherPropertyUnit);}	
	public String getOtherMaterial() {return checkGet(otherMaterial);}	
	public void setOtherMaterial(String otherMaterial) {this.otherMaterial = checkSet(otherMaterial);}	
	
	private String netValue;
	private String originalDeprMethod;
	private String originalBuildFeeCB;
	private String originalDeprUnitCB;
	private String originalDeprPark;
	private String originalDeprUnit;
	private String originalDeprUnit1;
	private String originalDeprAccounts;
	private String originalScrapValue;
	private String originalDeprAmount;
	private String originalaccumdepr1;
	private String originalaccumdepr2;
	private String originalAccumDepr;
	private String originalApportionMonth;
	private String originalMonthDepr;
	private String originalMonthDepr1;
	private String originalApportionEndYM;
	
	private String deprMethod;
	private String buildFeeCB;
	private String deprUnitCB;
	private String deprPark;
	private String deprUnit;
	private String deprUnit1;
	private String deprAccounts;
	private String scrapValue;
	private String deprAmount;
	private String accumDepr;
	private String apportionMonth;
	private String monthDepr;
	private String monthDepr1;
	private String apportionEndYM;
	private String noDeprSet;
	private String escrowOriValue;
	private String escrowOriAccumDepr;
	public String getNetValue() { return checkGet(netValue); }
	public void setNetValue(String netValue) { this.netValue = checkSet(netValue); }
	public String getOriginalDeprMethod() { return checkGet(originalDeprMethod); }
	public void setOriginalDeprMethod(String originalDeprMethod) { this.originalDeprMethod = checkSet(originalDeprMethod); }
	public String getOriginalBuildFeeCB() { return checkGet(originalBuildFeeCB); }
	public void setOriginalBuildFeeCB(String originalBuildFeeCB) { this.originalBuildFeeCB = checkSet(originalBuildFeeCB); }
	public String getOriginalDeprUnitCB() { return checkGet(originalDeprUnitCB); }
	public void setOriginalDeprUnitCB(String originalDeprUnitCB) { this.originalDeprUnitCB = checkSet(originalDeprUnitCB); }
	public String getOriginalDeprPark() { return checkGet(originalDeprPark); }
	public void setOriginalDeprPark(String originalDeprPark) { this.originalDeprPark = checkSet(originalDeprPark); }
	public String getOriginalDeprUnit() { return checkGet(originalDeprUnit); }
	public void setOriginalDeprUnit(String originalDeprUnit) { this.originalDeprUnit = checkSet(originalDeprUnit); }
	public String getOriginalDeprUnit1() { return checkGet(originalDeprUnit1); }
	public void setOriginalDeprUnit1(String originalDeprUnit1) { this.originalDeprUnit1 = checkSet(originalDeprUnit1); }
	public String getOriginalDeprAccounts() { return checkGet(originalDeprAccounts); }
	public void setOriginalDeprAccounts(String originalDeprAccounts) { this.originalDeprAccounts = checkSet(originalDeprAccounts); }
	public String getOriginalScrapValue() { return checkGet(originalScrapValue); }
	public void setOriginalScrapValue(String originalScrapValue) { this.originalScrapValue = checkSet(originalScrapValue); }
	public String getOriginalDeprAmount() { return checkGet(originalDeprAmount); }
	public void setOriginalDeprAmount(String originalDeprAmount) { this.originalDeprAmount = checkSet(originalDeprAmount); }
	public String getOriginalaccumdepr1() { return checkGet(originalaccumdepr1); }
	public void setOriginalaccumdepr1(String originalaccumdepr1) { this.originalaccumdepr1 = checkSet(originalaccumdepr1); }
	public String getOriginalaccumdepr2() { return checkGet(originalaccumdepr2); }
	public void setOriginalaccumdepr2(String originalaccumdepr2) { this.originalaccumdepr2 = checkSet(originalaccumdepr2); }
	public String getOriginalAccumDepr() { return checkGet(originalAccumDepr); }
	public void setOriginalAccumDepr(String originalAccumDepr) { this.originalAccumDepr = checkSet(originalAccumDepr); }
	public String getOriginalApportionMonth() { return checkGet(originalApportionMonth); }
	public void setOriginalApportionMonth(String originalApportionMonth) { this.originalApportionMonth = checkSet(originalApportionMonth); }
	public String getOriginalMonthDepr() { return checkGet(originalMonthDepr); }
	public void setOriginalMonthDepr(String originalMonthDepr) { this.originalMonthDepr = checkSet(originalMonthDepr); }
	public String getOriginalMonthDepr1() { return checkGet(originalMonthDepr1); }
	public void setOriginalMonthDepr1(String originalMonthDepr1) { this.originalMonthDepr1 = checkSet(originalMonthDepr1); }
	public String getOriginalApportionEndYM() { return checkGet(originalApportionEndYM); }
	public void setOriginalApportionEndYM(String originalApportionEndYM) { this.originalApportionEndYM = checkSet(originalApportionEndYM); }
	public String getDeprMethod() { return checkGet(deprMethod); }
	public void setDeprMethod(String deprMethod) { this.deprMethod = checkSet(deprMethod); }
	public String getBuildFeeCB() { return checkGet(buildFeeCB); }
	public void setBuildFeeCB(String buildFeeCB) { this.buildFeeCB = checkSet(buildFeeCB); }
	public String getDeprUnitCB() { return checkGet(deprUnitCB); }
	public void setDeprUnitCB(String deprUnitCB) { this.deprUnitCB = checkSet(deprUnitCB); }
	public String getDeprPark() { return checkGet(deprPark); }
	public void setDeprPark(String deprPark) { this.deprPark = checkSet(deprPark); }
	public String getDeprUnit() { return checkGet(deprUnit); }
	public void setDeprUnit(String deprUnit) { this.deprUnit = checkSet(deprUnit); }
	public String getDeprUnit1() { return checkGet(deprUnit1); }
	public void setDeprUnit1(String deprUnit1) { this.deprUnit1 = checkSet(deprUnit1); }
	public String getDeprAccounts() { return checkGet(deprAccounts); }
	public void setDeprAccounts(String deprAccounts) { this.deprAccounts = checkSet(deprAccounts); }
	public String getScrapValue() { return checkGet(scrapValue); }
	public void setScrapValue(String scrapValue) { this.scrapValue = checkSet(scrapValue); }
	public String getDeprAmount() { return checkGet(deprAmount); }
	public void setDeprAmount(String deprAmount) { this.deprAmount = checkSet(deprAmount); }
	public String getAccumDepr() { return checkGet(accumDepr); }
	public void setAccumDepr(String accumDepr) { this.accumDepr = checkSet(accumDepr); }
	public String getApportionMonth() { return checkGet(apportionMonth); }
	public void setApportionMonth(String apportionMonth) { this.apportionMonth = checkSet(apportionMonth); }
	public String getMonthDepr() { return checkGet(monthDepr); }
	public void setMonthDepr(String monthDepr) { this.monthDepr = checkSet(monthDepr); }
	public String getMonthDepr1() { return checkGet(monthDepr1); }
	public void setMonthDepr1(String monthDepr1) { this.monthDepr1 = checkSet(monthDepr1); }
	public String getApportionEndYM() { return checkGet(apportionEndYM); }
	public void setApportionEndYM(String apportionEndYM) { this.apportionEndYM = checkSet(apportionEndYM); }
	public String getNoDeprSet() { return checkGet(noDeprSet); }
	public void setNoDeprSet(String noDeprSet) { this.noDeprSet = checkSet(noDeprSet); }
	public String getEscrowOriValue() { return checkGet(escrowOriValue); }
	public void setEscrowOriValue(String escrowOriValue) { this.escrowOriValue = checkSet(escrowOriValue); }
	public String getEscrowOriAccumDepr() { return checkGet(escrowOriAccumDepr); }
	public void setEscrowOriAccumDepr(String escrowOriAccumDepr) { this.escrowOriAccumDepr = checkSet(escrowOriAccumDepr); }
	
	// 問題單2277 加上自行設定累計折舊
	private String individualSetDepr;
	private String verifyYM;
	public String getIndividualSetDepr() { return checkGet(individualSetDepr); }
	public void setIndividualSetDepr(String individualSetDepr) { this.individualSetDepr = checkSet(individualSetDepr); }
	public String getVerifyYM() { return checkGet(verifyYM); }
	public void setVerifyYM(String verifyYM) {this.verifyYM = checkSet(verifyYM); }
	
	public String queryVerify(){
		String sql = "select verify from UNTLA_ADDPROOF" +
						" where 1=1" +
						" and enterorg = " + Common.sqlChar(this.getEnterOrg()) +
						" and ownership = " + Common.sqlChar(this.getOwnership()) +
						" and caseno = " + Common.sqlChar(this.getCaseNo()) +
						" and differenceKind = " + Common.sqlChar(this.getDifferenceKind());
		
		DBServerTools dbt = new DBServerTools();
		dbt._setDatabase(new Database());
		String result = dbt._execSQL_asString(sql);
		dbt = null;
		sql = null;
		return result;
	}
}


