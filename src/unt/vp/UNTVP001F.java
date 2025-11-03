/*
*<br>程式目的：有價證券主檔資料維護-增加單資料
*<br>程式代號：untvp001f
*<br>程式日期：0940920
*<br>程式作者：cherry
*<br>--------------------------------------------------------
*<br>修改作者　　修改日期　　　修改目的
*<br>--------------------------------------------------------
*/

package unt.vp;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
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

public class UNTVP001F extends UNTCH001Q{

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
		String sql="select case when max(serialNo) is null then 1 else (max(serialNo) + 1) end as serialNo from UNTVP_AddProof " +
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
	//	setProofNo(MaxClosingYM.getMaxProofNo("UNTVP_AddProof",enterOrg,ownership));
		//===================
		String[][] checkSQLArray = new String[1][4];
	 	checkSQLArray[0][0]=" select count(*) as checkResult from UNTVP_AddProof where 1=1 " + 
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
	
	
	//傳回insert sql
	protected String[] getInsertSQL(){
		String[] execSQLArray = new String[1];
		
		execSQLArray[0] = new SQLCreator()._obtainSQLforInsert("UNTVP_ADDPROOF", getPKMap(), getRecordMap());
		
		return execSQLArray;
	}
	
	private String queryOldVerify() {
		Database db = null;
		try {
			db = new Database();
			return db.getLookupField("select verify from UNTVP_ADDPROOF where enterorg=" + Common.sqlChar(this.getEnterOrg()) + " and caseno = " + Common.sqlChar(this.getCaseNo()));
		} finally {
			if (db != null) {
				db.closeAll();
			}
		}
	}
	
	/**
	 * 單據入帳/回復入帳 的檢查
	 */
	@Override
	public String[][] getUpdateCheckSQLBeforeAction() {
		List<String[]> sqls = new ArrayList<String[]>();
		String[] tmpchk = null;
		
		String oldVerify = this.queryOldVerify();
		if ("N".equals(oldVerify) && "Y".equals(this.getVerify())) {
//			tmpchk = new String[4]; 
//			tmpchk[0] = "select count(*) as checkResult from UNTVP_SHARE where 1=1 and enterorg = " + Common.sqlChar(enterOrg) +" and ownership = " + Common.sqlChar(ownership) +" and propertyno = " + Common.sqlChar(propertyNo) +" and serialno = " + Common.sqlChar(serialNo);
//			tmpchk[1] = "<";
//			tmpchk[2] = "1";
//			tmpchk[3] = "該筆增加單之股份資料標籤要有資料才能做此入帳設定！";
//			sqls.add(tmpchk);
		} else if ("Y".equals(oldVerify) && "N".equals(this.getVerify())) {
			// 回復入帳時檢查 不可存在於 增減值單/減損單
			tmpchk = new String[4];
			tmpchk[0] = " select count(*) as checkResult from UNTVP_ADDPROOF a "
					  + " where a.enterorg = " + Common.sqlChar(this.getEnterOrg()) 
					  + " and a.caseno = " + Common.sqlChar(this.getCaseNo()) 
					  + " and (EXISTS (select 1 from UNTVP_ADJUSTPROOF b where a.enterorg = b.enterorg and a.ownership = b.ownership and a.differencekind = b.differencekind and a.propertyno = b.propertyno and a.serialno = b.serialno)"
					  + " or EXISTS (select 1 from UNTVP_REDUCEPROOF b where a.enterorg = b.enterorg and a.ownership = b.ownership and a.differencekind = b.differencekind and a.propertyno = b.propertyno and a.serialno = b.serialno)"
					  + " or EXISTS (select 1 from UNTMP_MOVEDETAIL b where a.enterorg = b.enterorg and a.ownership = b.ownership and a.differencekind = b.differencekind and a.propertyno = b.propertyno and a.serialno = b.serialno)"
					  + " );";
			tmpchk[1] = "!=";
			tmpchk[2] = "0";
			tmpchk[3] = "有價證券資料已做過增減值或減損作業或移動作業，請先刪除增減值或減損作業或移動作業之資料再回此作業取消入帳!";
			sqls.add(tmpchk);
		}
		
		return sqls.toArray(new String[sqls.size()][4]);
	}
	
	/**
	 * 明細修改的 檢查
	 */
	protected String[][] getUpdateCheckSQL(){
		List<String[]> sqls = new ArrayList<String[]>();
		String[] tmpchk = new String[4];
		tmpchk[0] = " select datastate as checkResult from UNTVP_ADDPROOF where 1=1 " + 
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
	 * 明細修改
	 */
	@Override
	protected String[] getUpdateSQL() throws Exception {
		
		List<String> sqls = new ArrayList<String>();
		
		sqls.add(new SQLCreator()._obtainSQLforUpdate("UNTVP_ADDPROOF", getPKMap(), getRecordMap()));
		
		sqls.add("update UNTVP_SHARE set"+
		        " editid = " + Common.sqlChar(getEditID()) + "," +
				" editdate = " + Common.sqlChar(new UNTCH_Tools()._transToCE_Year(getEditDate())) + "," +
				" edittime = " + Common.sqlChar(getEditTime()) +         
				" where 1=1 " + 
				" and enterorg = " + Common.sqlChar(enterOrg) +
				" and ownership = " + Common.sqlChar(ownership) +
				" and propertyno = " + Common.sqlChar(propertyNo) +
				" and serialno = " + Common.sqlChar(serialNo) +
				" and differencekind = " + Common.sqlChar(differenceKind)
				);
		
		return sqls.toArray(new String[sqls.size()]); 
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
				String sql = "update UNTVP_ADDPROOF set "+
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
	
	//傳回執行Delete前之檢查sql
	protected String[][] getDeleteCheckSQL(){
		String[][] checkSQLArray = new String[2][4];
	 	checkSQLArray[0][0]=" select (case verify when 'Y' then '1' when 'N' then '2' end) as checkResult from UNTVP_AddProof where 1=1 " + 
			" and enterOrg = " + Common.sqlChar(enterOrg) + 
			" and ownership = " + Common.sqlChar(ownership) + 
			" and propertyNo = " + Common.sqlChar(propertyNo) + 
			" and serialNo = " + Common.sqlChar(serialNo) +
			" and differenceKind = " + Common.sqlChar(differenceKind) + 
			"";
		checkSQLArray[0][1]="=";
		checkSQLArray[0][2]="1";
		checkSQLArray[0][3]="該筆資料己入帳，無法刪除！";
	
	 	checkSQLArray[1][0]=" select dataState as checkResult from UNTVP_AddProof where 1=1 " + 
		" and enterOrg = " + Common.sqlChar(enterOrg) + 
		" and ownership = " + Common.sqlChar(ownership) + 
		" and propertyNo = " + Common.sqlChar(propertyNo) + 
		" and serialNo = " + Common.sqlChar(serialNo) + 
		" and differenceKind = " + Common.sqlChar(differenceKind) + 
		"";
	 	checkSQLArray[1][1]="=";
	 	checkSQLArray[1][2]="2";
	 	checkSQLArray[1][3]="該筆資料己減損，無法刪除！";
	
		return checkSQLArray;
	}

	//傳回delete sql
	protected String[] getDeleteSQL(){
		String[] execSQLArray = new String[3];
		execSQLArray[0]=" delete UNTVP_ADDPROOF where 1=1 " +
				" and enterorg = " + Common.sqlChar(enterOrg) +
				" and ownership = " + Common.sqlChar(ownership) +
				" and propertyno = " + Common.sqlChar(propertyNo) +
				" and serialno = " + Common.sqlChar(serialNo) +
				" and differencekind = " + Common.sqlChar(differenceKind) + 
				"";
		execSQLArray[1]=" delete UNTVP_SHARE where 1=1 " +
				" and enterorg = " + Common.sqlChar(enterOrg) +
				" and ownership = " + Common.sqlChar(ownership) +
				" and propertyno = " + Common.sqlChar(propertyNo) +
				" and serialno = " + Common.sqlChar(serialNo) +
				" and differencekind = " + Common.sqlChar(differenceKind) + 
				"";
		execSQLArray[2]=" delete UNTVP_CAPITAL where 1=1 " +
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
		Map map = queryPropertyNofrom("UNTVP_ADDPROOF",sb);					
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
	public UNTVP001F  queryOne() throws Exception{
		Database db = new Database();
		UNTVP001F obj = this;
		try {
			String sql=" select a.*, "+
						" (select codename from SYSCA_CODE z where codekindid = 'SKD' and z.codeid = a.sourcekind) as sourcekindName, " +
						" (select codename from SYSCA_CODE z where codekindid = 'CAA' and z.codeid = a.cause and codecon1 in (1,4)) as causeName, " +
						" (select organsname from SYSCA_ORGAN z where z.organid = a.enterorg) as enterOrgName," +
						" (select codename from SYSCA_CODE z where z.codekindid = 'OWA' and z.codeid = a.ownership) as ownershipName," +
						" (select codename from SYSCA_CODE z where z.codekindid = 'DFK' and z.codeid = a.differenceKind) as differenceKindName," +
						" (select propertyname from SYSPK_PROPERTYCODE z where z.enterorg = a.enterorg and z.propertyno = a.propertyno) as propertyNoName," +
						" (select placename from SYSCA_PLACE z where z.enterorg = a.enterorg and z.placeno = a.originalPlace1) as originalPlace1Name," +
						" (select placename from SYSCA_PLACE z where z.enterorg = a.enterorg and z.placeno = a.place1) as place1Name" +
						" from UNTVP_AddProof a where 1=1"+
						" and a.enterOrg = " + Common.sqlChar(enterOrg) +
						" and a.ownership = " + Common.sqlChar(ownership) +
						" and a.propertyNo = " + Common.sqlChar(propertyNo) +
						" and a.differenceKind = " + Common.sqlChar(differenceKind) +					
						" and a.serialNo = " + Common.sqlChar(serialNo) +
						"";
			UNTCH_Tools ul = new UNTCH_Tools();
			ResultSet rs = db.querySQL(sql);
			if (rs.next()){
				obj.setOldVerify(rs.getString("verify"));
				
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
				obj.setSourceKind(rs.getString("SourceKind"));
				obj.setSourceKindName(rs.getString("SourceKindName"));
				obj.setSourceDate(ul._transToROC_Year(rs.getString("SourceDate")));
				obj.setSourceDoc(rs.getString("SourceDoc"));
				obj.setBuyDate(ul._transToROC_Year(rs.getString("BuyDate")));
				obj.setCause(rs.getString("Cause"));
				obj.setCauseName(rs.getString("CauseName"));
				obj.setCause1(rs.getString("Cause1"));
				obj.setEnterDate(ul._transToROC_Year(rs.getString("EnterDate")));
				obj.setOriginalNote(rs.getString("OriginalNote"));
				obj.setDataState(rs.getString("DataState"));
				obj.setVerify(rs.getString("Verify"));
				obj.setPropertyKind(rs.getString("PropertyKind"));
				obj.setFundType(rs.getString("FundType"));
				obj.setAccountingTitle(rs.getString("AccountingTitle"));
				obj.setSecurityMeat(rs.getString("SecurityMeat"));
				obj.setSecurityName(rs.getString("SecurityName"));
				obj.setSecurityAddr(rs.getString("SecurityAddr"));
				obj.setSecurityItem(rs.getString("SecurityItem"));
				obj.setSecurityTime(rs.getString("SecurityTime"));
				obj.setSecurityOrg(rs.getString("SecurityOrg"));
				obj.setSecurityDoc(rs.getString("SecurityDoc"));
				obj.setCapitalStock(rs.getString("CapitalStock"));
				obj.setCapital(rs.getString("Capital"));
				obj.setOriginalAmount(rs.getString("OriginalAmount"));
				obj.setOriginalBV(rs.getString("OriginalBV"));
				obj.setBookAmount(rs.getString("BookAmount"));
				obj.setBookValue(rs.getString("BookValue"));
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
	
				obj.setEnterOrgName(rs.getString("EnterOrgName"));
				obj.setOwnershipName(rs.getString("OwnershipName"));
				obj.setDifferenceKindName(rs.getString("DifferenceKindName"));
				obj.setPropertyNoName(rs.getString("PropertyNoName"));			
				
				obj.setOriginalPlace1Name(rs.getString("Originalplace1Name"));
				obj.setPlace1Name(rs.getString("place1Name"));
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

	public ArrayList queryAll() throws Exception{
		ArrayList objList=new ArrayList();
		Database db = new Database();	
		
		try {
			String sql=" select a.*,"+
						"(select codename from SYSCA_CODE z where z.codekindid = 'DFK' and z.codeid = a.differenceKind) as differenceKindName," +
						"(select codename from SYSCA_CODE z where z.codekindid = 'OWA' and z.codeid = a.ownership) as ownershipName," +
						"(select codename from SYSCA_CODE z where z.codekindid = 'CAA' and z.codeid = a.cause) as causeName," +
						"case datastate when '1' then '現存' when '2' then '減損' end as datastateName," +
						"case verify when 'Y' then '是' else '否' end as verifyName" +
						" from UNTVP_AddProof a where 1=1"; 
			
				sql+=" and a.enterOrg = " + Common.sqlChar(getEnterOrg()) ;
				sql+=" and a.ownership = " + Common.sqlChar(getOwnership()) ;
				sql+=" and a.propertyNo=" + Common.sqlChar(getPropertyNo());
				sql+=" and a.differenceKind=" + Common.sqlChar(getDifferenceKind());
				sql+=" and a.serialNo=" + Common.sqlChar(getSerialNo());
			
			ResultSet rs = db.querySQL(sql,true);
			UNTCH_Tools ul = new UNTCH_Tools();
			processCurrentPageAttribute(rs);			
			if (getTotalRecord() > 0) {
				int count = getRecordStart();
				int end = getRecordEnd();
				do {
					if (count > end)
						break;
				String rowArray[]=new String[14];
				rowArray[0]=rs.getString("enterOrg");
				rowArray[1]=rs.getString("ownership");
				rowArray[2]=rs.getString("caseNo");
				rowArray[3]=rs.getString("differenceKind");
				rowArray[4]=rs.getString("propertyNo");
				rowArray[5]=rs.getString("serialNo");			
				rowArray[6]=rs.getString("differenceKindName");
				rowArray[7]=rs.getString("ownershipName");
				rowArray[8]=rs.getString("causeName");
				rowArray[9]=rs.getString("datastateName");
				rowArray[10]=rs.getString("verifyName");
				rowArray[11]=rs.getString("securityName");
				rowArray[12]=rs.getString("bookAmount");
				rowArray[13]=rs.getString("bookValue");
				
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

	private Map getRecordMap(){
		Map map = new HashMap();
		
		UNTCH_Tools ul = new UNTCH_Tools();
				
		map.put("enterorg", getEnterOrg());
		map.put("ownership", getOwnership());
		map.put("caseno", getCaseNo());
		map.put("casename", getCaseName());
		map.put("differencekind", getDifferenceKind());
		map.put("engineeringno", getEngineeringNo());
		map.put("caseserialno", getCaseSerialNo());
		map.put("propertyno", getPropertyNo());
		map.put("serialno", getSerialNo());
		map.put("propertyname1", getPropertyName1());
		map.put("writedate", ul._transToCE_Year(getWriteDate()));
		map.put("writeunit", getWriteUnit());
		map.put("manageno", getManageNo());
		map.put("summonsno", getSummonsNo());
		map.put("sourcekind", getSourceKind());
		map.put("sourcedate", ul._transToCE_Year(getSourceDate()));
		map.put("sourcedoc", getSourceDoc());
		map.put("buydate", ul._transToCE_Year(getBuyDate()));
		map.put("cause", getCause());
		map.put("cause1", getCause1());
		map.put("enterdate", ul._transToCE_Year(getEnterDate()));
		map.put("originalnote", getOriginalNote());
		map.put("datastate", getDataState());
		map.put("verify", getVerify());
		map.put("propertykind", getPropertyKind());
		map.put("fundtype", getFundType());
		map.put("accountingtitle", getAccountingTitle());
		map.put("securitymeat", getSecurityMeat());
		map.put("securityname", getSecurityName());
		map.put("securityaddr", getSecurityAddr());
		map.put("securityitem", getSecurityItem());
		map.put("securitytime", getSecurityTime());
		map.put("securityorg", getSecurityOrg());
		map.put("securitydoc", getSecurityDoc());
		map.put("capitalstock", getCapitalStock());
		map.put("capital", getCapital());
		map.put("originalamount", getOriginalAmount());
		map.put("originalbv", getOriginalBV());
		map.put("bookamount", getBookAmount());
		map.put("bookvalue", getBookValue());
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
		map.put("usernote", getUserNote());
		map.put("place1", getPlace1());
		map.put("place", getPlace());
		map.put("reducedate", ul._transToCE_Year(getReduceDate()));
		map.put("reducecause", getReduceCause());
		map.put("reducecause1", getReduceCause1());
		map.put("oldpropertyno", getOldPropertyNo());
		map.put("oldserialno", getOldSerialNo());
		map.put("notes", getNotes());
		map.put("editid", getEditID());
		map.put("editdate", ul._transToCE_Year(getEditDate()));
		map.put("edittime", getEditTime());
		map.put("otherpropertyunit", getOtherPropertyUnit());
		map.put("othermaterial", getOtherMaterial());
		map.put("proofyear", getProofYear());
		map.put("proofdoc", getProofDoc());
		map.put("proofno", getProofNo());
		
		
		// UNTCH001F02 summonsdate已經事先轉為西元年，而不知道有其他共用元件，避免發生轉換格式錯誤情形
		if(Common.get(this.getSummonsDate()).length() == 8){   
			map.put("summonsdate", this.getSummonsDate());
		} else {
			map.put("summonsdate", ul._transToCE_Year(this.getSummonsDate()));
		}
		return map;
	}


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
	private String sourceKind;
	private String sourceDate;
	private String sourceDoc;
	private String buyDate;
	private String cause;
	private String cause1;
	private String enterDate;
	private String originalNote;
	private String dataState;
	private String verify;
	private String propertyKind;
	private String fundType;
	private String accountingTitle;
	private String securityMeat;
	private String securityName;
	private String securityAddr;
	private String securityItem;
	private String securityTime;
	private String securityOrg;
	private String securityDoc;
	private String capitalStock;
	private String capital;
	private String originalAmount;
	private String originalBV;
	private String bookAmount;
	private String bookValue;
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
	private String enterOrgName;
	private String ownershipName;
	private String differenceKindName;
	private String engineeringNoName;
	private String propertyNoName;
	
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
	public String getSourceKind() {return checkGet(sourceKind);}
	public void setSourceKind(String sourceKind) {this.sourceKind = checkSet(sourceKind);}
	public String getSourceDate() {return checkGet(sourceDate);}
	public void setSourceDate(String sourceDate) {this.sourceDate = checkSet(sourceDate);}
	public String getSourceDoc() {return checkGet(sourceDoc);}
	public void setSourceDoc(String sourceDoc) {this.sourceDoc = checkSet(sourceDoc);}
	public String getBuyDate() {return checkGet(buyDate);}
	public void setBuyDate(String buyDate) {this.buyDate = checkSet(buyDate);}
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
	public String getAccountingTitle() {return checkGet(accountingTitle);}
	public void setAccountingTitle(String accountingTitle) {this.accountingTitle = checkSet(accountingTitle);}
	public String getSecurityMeat() {return checkGet(securityMeat);}
	public void setSecurityMeat(String securityMeat) {this.securityMeat = checkSet(securityMeat);}
	public String getSecurityName() {return checkGet(securityName);}
	public void setSecurityName(String securityName) {this.securityName = checkSet(securityName);}
	public String getSecurityAddr() {return checkGet(securityAddr);}
	public void setSecurityAddr(String securityAddr) {this.securityAddr = checkSet(securityAddr);}
	public String getSecurityItem() {return checkGet(securityItem);}
	public void setSecurityItem(String securityItem) {this.securityItem = checkSet(securityItem);}
	public String getSecurityTime() {return checkGet(securityTime);}
	public void setSecurityTime(String securityTime) {this.securityTime = checkSet(securityTime);}
	public String getSecurityOrg() {return checkGet(securityOrg);}
	public void setSecurityOrg(String securityOrg) {this.securityOrg = checkSet(securityOrg);}
	public String getSecurityDoc() {return checkGet(securityDoc);}
	public void setSecurityDoc(String securityDoc) {this.securityDoc = checkSet(securityDoc);}
	public String getCapitalStock() {return checkGet(capitalStock);}
	public void setCapitalStock(String capitalStock) {this.capitalStock = checkSet(capitalStock);}
	public String getCapital() {return checkGet(capital);}
	public void setCapital(String capital) {this.capital = checkSet(capital);}
	public String getOriginalAmount() {return checkGet(originalAmount);}
	public void setOriginalAmount(String originalAmount) {this.originalAmount = checkSet(originalAmount);}
	public String getOriginalBV() {return checkGet(originalBV);}
	public void setOriginalBV(String originalBV) {this.originalBV = checkSet(originalBV);}
	public String getBookAmount() {return checkGet(bookAmount);}
	public void setBookAmount(String bookAmount) {this.bookAmount = checkSet(bookAmount);}
	public String getBookValue() {return checkGet(bookValue);}
	public void setBookValue(String bookValue) {this.bookValue = checkSet(bookValue);}
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
	
	private String verifyError;
	public String getVerifyError() {return checkGet(verifyError);}
	public void setVerifyError(String verifyError) {this.verifyError = checkSet(verifyError);}
	private String oldVerify;
	public String getOldVerify() {return checkGet(oldVerify);}
	public void setOldVerify(String oldVerify) {this.oldVerify = checkSet(oldVerify);}
	private String securityOrgName;
	public String getSecurityOrgName() {return checkGet(securityOrgName);}
	public void setSecurityOrgName(String securityOrgName) {this.securityOrgName = checkSet(securityOrgName);}
	private String place1Name;
	private String originalPlace1Name;
	public String getPlace1Name() {return checkGet(place1Name);}
	public void setPlace1Name(String place1Name) {this.place1Name = checkSet(place1Name);}
	public String getOriginalPlace1Name() {return checkGet(originalPlace1Name);}
	public void setOriginalPlace1Name(String originalPlace1Name) {this.originalPlace1Name = checkSet(originalPlace1Name);}
	
	private String sourceKindName;
	private String causeName;
	public String getSourceKindName() {return checkGet(sourceKindName);}
	public void setSourceKindName(String sourceKindName) {this.sourceKindName = checkSet(sourceKindName);}
	public String getCauseName() {return checkGet(causeName);}
	public void setCauseName(String causeName) {this.causeName = checkSet(causeName);}
	
	private String summonsDate;
	public String getSummonsDate() {return checkGet(summonsDate);}
	public void setSummonsDate(String summonsDate) {this.summonsDate = checkSet(summonsDate);}
	
	private String otherPropertyUnit;
	private String otherMaterial;
	public String getOtherPropertyUnit() {return checkGet(otherPropertyUnit);}	
	public void setOtherPropertyUnit(String otherPropertyUnit) {this.otherPropertyUnit = checkSet(otherPropertyUnit);}	
	public String getOtherMaterial() {return checkGet(otherMaterial);}	
	public void setOtherMaterial(String otherMaterial) {this.otherMaterial = checkSet(otherMaterial);}	

}


