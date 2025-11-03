/*
*<br>程式目的：
*<br>程式代號：
*<br>程式日期：
*<br>程式作者：
*<br>--------------------------------------------------------
*<br>修改作者　　修改日期　　　修改目的
*<br>--------------------------------------------------------
*/

package unt.ch;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import unt.rt.UNTRT008F;
import util.Common;
import util.Database;
import util.Datetime;
import util.SuperBean2;
import TDlib_Simple.com.src.DBServerTools;

public class UNTCH003F01 extends UNTCH003Q{

	/**
	 * 以土地增減值單 為主
	 * @return
	 */
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
	 * 入帳檢查, 是否為入帳判斷做在jsp
	 */
	public boolean checkUpdateError(){
		boolean result = true;
//		ArrayList objList = checkOverLimitYear();
//		
//		 if (!objList.isEmpty()) {
//				result = false;
//				StringBuilder errorMsg = new StringBuilder(); 
//				for (int i = 0; i < objList.size(); i++) {
//					String propertyno = (((String[])objList.get(i))[0]);
//					String serialno = (((String[])objList.get(i))[1]);
//
//					errorMsg.append("請注意!  財產編號：" + propertyno + "\\n財產分號：" + serialno + "\\n");
//					errorMsg.append("攤提年限截止年月！" + "\\n\\n");				
//					
//				}
//				
//				setErrorMsg(errorMsg.toString());
//			}
		 
		if(checkClosingYMfromUNTAC_CLOSINGPT(getAdjustDate(),getEnterOrg(),getDifferenceKind())){
			setErrorMsg("入帳日期需為最大月結年月＋１");
			result = false;
		} 
		
		UNTCH_Tools uctls = new UNTCH_Tools();
		SuperBean2 sb = null;
		List list = _getBeans_asList();
		
		if (result) {
			for (Object o : list) {
				sb = (SuperBean2)o;
				_setDefaultValue();
				uctls._setParameter(this,sb);
				if (!beforeExecCheck(sb.getUpdateCheckSQLBeforeAction())) {
					result = false;
					break;
				}				
			}
		}
		
		if (result) {
			list = this._getBeansExtend_asList();
			for (Object o : list) {
				sb = (SuperBean2)o;
				_setDefaultValue();
				uctls._setParameter(this,sb);
				if (!beforeExecCheck(sb.getUpdateCheckSQLBeforeAction())) {
					result = false;
					break;
				}				
			}
		}
		
		return result;
	}
	
	public boolean checReVerifyError(){
		boolean result = true;
		if(checkCanNotReVerify(getAdjustDateTemp(),getEnterOrg(),getDifferenceKind())){
			setErrorMsg("當月已月結不可取消入帳");
			result = false;
		} else if (checkMonthDepr3()) {
			setErrorMsg("無法取消入帳，因本月折舊已入帳");
			result = false;
		} else if ("N".equals(this.queryOldVerify())) {
			setErrorMsg("無法取消入帳，因此增減值單已回復入帳");
			result = false;
		}
		
		UNTCH_Tools uctls = new UNTCH_Tools();
		SuperBean2 sb = null;
		List list = _getBeans_asList();
		for (Object o : list) {
			sb = (SuperBean2)o;
			_setDefaultValue();
			uctls._setParameter(this,sb);
			if (!beforeExecCheck(sb.getUpdateCheckSQLBeforeAction())) {
				result = false;
				break;
			}				
		}
		
		list = this._getBeansExtend_asList();
		for (Object o : list) {
			sb = (SuperBean2)o;
			_setDefaultValue();
			uctls._setParameter(this,sb);
			if (!beforeExecCheck(sb.getUpdateCheckSQLBeforeAction())) {
				result = false;
				break;
			}				
		}
		
		return result;
	}	
	
	private ArrayList checkOverLimitYear(){
		
		ArrayList objList = new ArrayList();	
		Database db = null;
		ResultSet rs = null;
		StringBuilder sql = new StringBuilder();
		
		sql.append(" select propertyno , serialno from [UNTMP_ADJUSTDETAIL] where 1=1 and overlimityear is not null " );
		sql.append(" and enterorg = ").append( Common.sqlChar(getEnterOrg())); 
		sql.append(" and caseno = " ).append( Common.sqlChar(getCaseNo())) ;
		sql.append(" and ownership = " ).append( Common.sqlChar(getOwnership())) ;
		sql.append(" and differencekind = " ).append( Common.sqlChar(getDifferenceKind()));
		sql.append(" union" );
		sql.append(" select propertyno , serialno from [UNTBU_ADJUSTDETAIL] where 1=1 and overlimityear is not null" );
		sql.append(" and enterorg = ").append( Common.sqlChar(getEnterOrg())); 
		sql.append(" and caseno = " ).append( Common.sqlChar(getCaseNo())) ;
		sql.append(" and ownership = " ).append( Common.sqlChar(getOwnership())) ;
		sql.append(" and differencekind = " ).append( Common.sqlChar(getDifferenceKind()));		
		sql.append(" union" );
		sql.append(" select propertyno , serialno from [UNTRF_ADJUSTDETAIL] where 1=1 and overlimityear is not null" );
		sql.append(" and enterorg = ").append( Common.sqlChar(getEnterOrg())); 
		sql.append(" and caseno = " ).append( Common.sqlChar(getCaseNo())) ;
		sql.append(" and ownership = " ).append( Common.sqlChar(getOwnership())) ;
		sql.append(" and differencekind = " ).append( Common.sqlChar(getDifferenceKind()));
		sql.append(" union" );
		sql.append(" select propertyno , serialno from [UNTRT_ADJUSTPROOF] where 1=1 and overlimityear is not null" );
		sql.append(" and enterorg = ").append( Common.sqlChar(getEnterOrg())); 
		sql.append(" and caseno = " ).append( Common.sqlChar(getCaseNo())) ;
		sql.append(" and ownership = " ).append( Common.sqlChar(getOwnership())) ;
		sql.append(" and differencekind = " ).append( Common.sqlChar(getDifferenceKind()));

		try{
			db = new Database();
			rs = db.querySQL_NoChange(sql.toString());
			
			while(rs.next()){
				String rowArray[] = new String[2];
				rowArray[0] = rs.getString("propertyno");
				rowArray[1] = rs.getString("serialno");
				
				objList.add(rowArray);
			}
			rs.close();
		}catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.closeAll();
			db = null;
			rs = null;
			sql = null;
		}
		
		return objList;
	}
	
	private String getCheckEnterDateCondition(){
		String result = " and enterorg = " + Common.sqlChar(getEnterOrg()) +
						" and caseno = " + Common.sqlChar(getCaseNo()) +
						" and ownership = " + Common.sqlChar(getOwnership()) +
						" and differencekind = " + Common.sqlChar(getDifferenceKind());
						
		return result;
	}
	
	//問題單967
	public void checkMonthDepr1(){
		String sql = "";
		if("Y".equals(getVerify())){
			//1.本月折舊計算後入帳前，當月增加單、增減值單、減損單入帳時應該顯示提示訊息"本月折舊已計算，入帳後請作折舊重算"
			String closing1ym = queryClosingYMfromUNTAC_CLOSINGPT(getEnterOrg(), getDifferenceKind());
			if(!"".equals(closing1ym)){
				// deprYM = 最後月結年月+1個月
				if("12".equals(closing1ym.substring(4, 6))){
					closing1ym = String.valueOf(Integer.parseInt(closing1ym)+89);	//201512+89=201601
				}else{
					closing1ym = String.valueOf(Integer.parseInt(closing1ym)+1);
				}
				sql = "select COUNT(*)" +
						  " from UNTDP_MONTHDEPR where 1=1" +
						  " and enterorg = " + Common.sqlChar(getEnterOrg()) +
						  " and differencekind = " + Common.sqlChar(getDifferenceKind()) +
						  " and verify = 'N'" +
						  " and deprym = " +  Common.sqlChar(closing1ym) +
						  "";
				try {
					Object cnt = this.queryObject(sql);
					if (cnt != null && Common.getInt(cnt) > 0) {
						setErrorMsg("本月折舊已計算，入帳後請作折舊重算");
					}
				} catch (Exception e) {
					e.printStackTrace();
					this.setErrorMsg("檢查本月有無計算折舊時發生異常，若本月已計算折舊，入帳後請重算折舊");
				}
			}
		}
	}
	
	public boolean checkMonthDepr2(){
		boolean result = true;
		String sql = "";
		if("Y".equals(getVerify())){
			//2.折舊入帳後當月增加單、增減值單、減損單應不能再入帳，並提示當月折舊已入帳
			sql = "select MAX(deprym)" +
					  " from UNTDP_MONTHDEPR where 1=1" +
					  " and enterorg = " + Common.sqlChar(getEnterOrg()) +
					  " and differencekind = " + Common.sqlChar(getDifferenceKind()) +
					  " and verify = 'Y'" +
					  "";
			
			//單據入帳時，若「該單據.入帳日期.年月(西元年)」與「最大的折舊入帳年月(西元年)」一致， 應控制不能入帳，並提示當月折舊已入帳
			try {
				Object deprym = this.queryObject(sql);
				String enterdate = Datetime.changeTaiwanYYMM(this.getAdjustDate(), "2");
				if (deprym != null && Common.get(deprym).equals(enterdate)) {
					result = false;
					setErrorMsg("無法入帳，因為本月折舊已入帳");
				}
			} catch (Exception e) {
				e.printStackTrace();
				result = false;
				setErrorMsg("無法入帳，檢查折舊月檔最後入帳年月發生錯誤");
			}
		}
		return result;
	}
	
	public boolean checkMonthDepr3(){
		boolean result = false;
		String sql = "";
		if("Y".equals(getVerify()) &&  getAdjustDateTemp().length()==7){
			// 取消入帳前，若已有當月折舊資料，加判斷是否已入帳
			String reduceYM = Datetime.getYYYYMMDDFromRocDate(getAdjustDateTemp()).substring(0, 6);
			
			sql = "select COUNT(1)" +
					  " from UNTDP_MONTHDEPR where 1=1" +
					  " and enterorg = " + Common.sqlChar(getEnterOrg()) +
					  " and deprym = " +  Common.sqlChar(reduceYM) +
					  " and differencekind = " + Common.sqlChar(getDifferenceKind()) +
					  " and verify = 'Y'" +
					  "";		
			try {
				Object cnt = this.queryObject(sql);
				if (cnt != null && Common.getInt(cnt) > 0) {
					result = true;
				}
			} catch (Exception e) {
				result = true;
				e.printStackTrace();
			}
		}
		return result;
	}
	
	/**
	 * 避免重複入帳導致帳務錯誤(e.g. 主檔帳務被扣兩次，帳值欄位變負值)
	 * @param nowVerify 入帳(傳入YN)
	 * @return
	 */
	public boolean checkProofVerifyYN(String nowVerify){
		boolean result = false;
		Database db = new Database();
		String checkSQL = "";
		String dbVerify = "";
		try {
			checkSQL = "SELECT verify FROM UNTLA_ADJUSTPROOF WHERE enterOrg = " + Common.sqlChar(this.getEnterOrg()) + 
					" and caseno = " + Common.sqlChar(this.getCaseNo());
			dbVerify = db.getLookupField(checkSQL);
			
			if(!dbVerify.equals(nowVerify)) {
				result = true;
			}else {
				if(nowVerify.equals("Y")) {
					setErrorMsg("減損單已入帳，執行失敗。");					
				}else {
					setErrorMsg("減損單未入帳，執行失敗。");
				}
			}
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			db.closeAll();
		}
		return result;
	}
	
	public boolean checkDetailCanVerify(String nowVerify){
		boolean result = false;
		Database db = new Database();
		ResultSet rs = null;
		String condition = "";
		
		if(nowVerify.equals("Y")) {
			condition = "new";
		}else if(nowVerify.equals("N")) {
			condition = "old";
		}
		
		String checkSQL = "DECLARE @enterorg AS NVARCHAR(10),@caseno AS NVARCHAR(10) " +
				"SELECT @enterorg = " + Common.sqlChar(this.getEnterOrg()) + ",@caseno = " + Common.sqlChar(this.getCaseNo()) +
				"	 " +
				"SELECT 'LA' AS property " +
				"	,COUNT(1) AS cnt " +
				"FROM UNTLA_ADJUSTDETAIL " +
				"WHERE " + condition + "bookvalue < 0 " +
				"	OR " + condition + "netvalue < 0 " +
				"	AND enterorg = @enterorg " +
				"	AND caseno = @caseno " +
				"UNION ALL " +
				"SELECT 'BU' AS property " +
				"	,COUNT(1) AS cnt " +
				"FROM UNTBU_ADJUSTDETAIL " +
				"WHERE " + condition + "bookvalue < 0 " +
				"	OR " + condition + "netvalue < 0 " +
				"	AND enterorg = @enterorg " +
				"	AND caseno = @caseno " +
				"UNION ALL " +
				"SELECT 'RF' AS property " +
				"	,COUNT(1) AS cnt " +
				"FROM UNTRF_ADJUSTDETAIL " +
				"WHERE " + condition + "bookvalue < 0 " +
				"	OR " + condition + "netvalue < 0 " +
				"	AND enterorg = @enterorg " +
				"	AND caseno = @caseno " +
				"UNION ALL " +
				"SELECT 'MP' AS property " +
				"	,COUNT(1) AS cnt " +
				"FROM UNTMP_ADJUSTDETAIL " +
				"WHERE " + condition + "bookvalue < 0 " +
				"	OR " + condition + "netvalue < 0 " +
				"	AND enterorg = @enterorg " +
				"	AND caseno = @caseno " +
				"UNION ALL " +
				"SELECT 'VP' AS property " +
				"	,COUNT(1) AS cnt " +
				"FROM UNTVP_ADJUSTPROOF " +
				"WHERE " + condition + "bookvalue < 0 " +
				"	AND enterorg = @enterorg " +
				"	AND caseno = @caseno " +
				"UNION ALL " +
				"SELECT 'RT' AS property " +
				"	,COUNT(1) AS cnt " +
				"FROM UNTRT_ADJUSTPROOF " +
				"WHERE " + condition + "bookvalue < 0 " +
				"	OR " + condition + "netvalue < 0 " +
				"	AND enterorg = @enterorg " +
				"	AND caseno = @caseno ";
		try {
			rs = db.querySQL(checkSQL);
			while(rs.next()) {
				if(!"0".equals(Common.get(rs.getString("cnt")))) {
					setErrorMsg("執行失敗，增減值單明細不可為負數金額。");
					return false;
				}
			}
			//所有財產檢查完後即可入帳
			result = true;
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			db.closeAll();
		}
		return result;
	}
	
		
	protected List _getBeans_asList(){
		List list = new ArrayList();
		
		list.add(new unt.la.UNTLA018F());
		list.add(new unt.bu.UNTBU021F());
		list.add(new unt.rf.UNTRF014F());
		list.add(new unt.mp.UNTMP025F());
		
		return list;
	}

	@Override
	protected List _getBeansExtend_asList(){
		List list = new ArrayList();
		
		list.add(new unt.vp.UNTVP006F());		
		list.add(new unt.rt.UNTRT008F(this.getClass().getSimpleName()));
		
		return list;
	}
	
	//====================================================================
	//檢查此電腦單號在指定的table中是否有資料
		public boolean checkDataCountIsZero(String enterOrg, String caseNo){
			boolean result = false;
			
			String condition = " where 1=1" +
								" and enterorg = '" + enterOrg + "'" +
								" and caseNo = '" + caseNo + "'";
			
			String sql = "select sum(count) from " +
							"(" +
								" select count(*) as count from UNTLA_ADJUSTDETAIL" + condition +
								" union" +
								" select count(*) as count from UNTBU_ADJUSTDETAIL" + condition +
								" union" +
								" select count(*) as count from UNTRF_ADJUSTDETAIL" + condition +
								" union" +
								" select count(*) as count from UNTMP_ADJUSTDETAIL" + condition +
								" union" +
								" select count(*) as count from UNTVP_ADJUSTPROOF" + condition +
								" union" +
								" select count(*) as count from UNTRT_ADJUSTPROOF" + condition +
							") a";
			
			DBServerTools dbt = new DBServerTools();
			dbt._setDatabase(new Database());
			String checkDataCount = dbt._execSQL_asString(sql);
			
			if("0".equals(checkDataCount)){
				result = true;
			}

			return result;
		}
		
	protected void _setDefaultValue(){
		this.setClosing("N");
	}
	//====================================================================	

	/**
	 * @deprecated ?
	 */
	public boolean checkVerify(){
		UNTCH_Tools uctls = new UNTCH_Tools();
		unt.la.UNTLA018F la = new unt.la.UNTLA018F();
		uctls._setParameter(this,la);
		boolean result = la.checkVerify();
		setErrorMsg(la.getErrorMsg());
		return result;			
	}
		
	public void _updateBUILDING(){
		unt.bu.UNTBU021F bu = new unt.bu.UNTBU021F();
		bu.updateBUILDING(this.getEnterOrg(), this.getOwnership(), this.getCaseNo(), this.getDifferenceKind(), this.getVerify());
	}
		
	public void _updateATTACHMENT(){
		unt.rf.UNTRF014F rf = new unt.rf.UNTRF014F();
		rf.updateATTACHMENT(this.getEnterOrg(), this.getOwnership(), this.getCaseNo(), this.getDifferenceKind(), this.getVerify());
	}
		
	public void _updateMOVABLE(){
		unt.mp.UNTMP025F mp = new unt.mp.UNTMP025F();
		mp.updateMOVABLEDETAIL(this.getEnterOrg(), this.getOwnership(), this.getCaseNo(), this.getDifferenceKind(), this.getVerify());
		mp.updateMOVABLE(this.getEnterOrg(), this.getOwnership(), this.getCaseNo(), this.getDifferenceKind());
	}
	
	public void _updateManage() {
		unt.la.UNTLA018F la = new unt.la.UNTLA018F();
		unt.bu.UNTBU021F bu = new unt.bu.UNTBU021F();
		la.updateManage(this.getEnterOrg(), this.getOwnership(), this.getCaseNo(), this.getDifferenceKind(), this.getVerify());
		bu.updateManage(this.getEnterOrg(), this.getOwnership(), this.getCaseNo(), this.getDifferenceKind(), this.getVerify());
	}
	
	/**
	 * ...ZZZZ 剩餘的折舊欄位調整
	 */
	public void updateRT() {
		UNTRT008F untrt08f = new UNTRT008F();
		untrt08f.updateRT(this.getEnterOrg(), this.getOwnership(), this.getCaseNo(), this.getDifferenceKind(), this.getVerify());
	}
	
	/**
	 * 增減值單入帳/回復入帳時調整折舊比例-分攤金額欄位<br>
	 * UNTCH_DEPRPERCENT.deprshareamount<br>
	 * action : doVerify / reVerify
	 */
	public void updateUntchDeprpercent(String action){
		String bookvalue = "";
		if ("doVerify".equals(action)) {
			bookvalue = "a.newbookvalue";
		} else if ("reVerify".equals(action)) {
			bookvalue = "a.oldbookvalue";
		} else {
			return;
		}
		
		// 全部分攤金額以比例更新(原值有變更的才更新)
		String updateSql = 
				"update b" +
				" set b.deprshareamount=round("+bookvalue+"*b.deprpercent/100,0)" +
				" from(" +
					" select enterorg,caseno,ownership,differencekind,propertyno,serialno,oldbookvalue,newbookvalue" +
					" from UNTRF_ADJUSTDETAIL" +
					" union" +
					" select enterorg,caseno,ownership,differencekind,propertyno,serialno,oldbookvalue,newbookvalue" +
					" from UNTBU_ADJUSTDETAIL" +
					" union" +
					" select enterorg,caseno,ownership,differencekind,propertyno,serialno,oldbookvalue,newbookvalue" +
					" from UNTMP_ADJUSTDETAIL" +
					" union" +
					" select enterorg,caseno,ownership,differencekind,propertyno,serialno,oldbookvalue,newbookvalue" +
					" from UNTRT_ADJUSTPROOF" +
				" )as a" +
				" inner join UNTCH_DEPRPERCENT b on a.enterorg=b.enterorg and a.ownership=b.ownership and a.differencekind=b.differencekind" +
					" and a.propertyno=b.propertyno and a.serialno=b.serialno" +
				" where a.enterorg = " + Common.sqlChar(getEnterOrg()) +
				" and a.caseno = " + Common.sqlChar(getCaseNo()) +
				" and a.oldbookvalue<>a.newbookvalue" +
				"";
		
		// 最後一比分攤金額加上原值和分攤金額總和的誤差，避免原值!=總和
		String updateSql2 = 
				"update b" +
				" set b.deprshareamount=b.deprshareamount+("+bookvalue+"-c.shareamount)" +
				" from(" +
					" select enterorg,caseno,ownership,differencekind,propertyno,serialno,oldbookvalue,newbookvalue" +
					" from UNTRF_ADJUSTDETAIL" +
					" union" +
					" select enterorg,caseno,ownership,differencekind,propertyno,serialno,oldbookvalue,newbookvalue" +
					" from UNTBU_ADJUSTDETAIL" +
					" union" +
					" select enterorg,caseno,ownership,differencekind,propertyno,serialno,oldbookvalue,newbookvalue" +
					" from UNTMP_ADJUSTDETAIL" +
					" union" +
					" select enterorg,caseno,ownership,differencekind,propertyno,serialno,oldbookvalue,newbookvalue" +
					" from UNTRT_ADJUSTPROOF" +
				" )as a" +
				" inner join UNTCH_DEPRPERCENT b on a.enterorg=b.enterorg and a.ownership=b.ownership and a.differencekind=b.differencekind" +
					" and a.propertyno=b.propertyno and a.serialno=b.serialno" +
				" outer apply(" +
					" select max(serialno1)as serialno1,sum(deprshareamount)as shareamount" +
					" from UNTCH_DEPRPERCENT" +
					" where enterorg=b.enterorg and ownership=b.ownership and differencekind=b.differencekind" +
						" and propertyno=b.propertyno and serialno=b.serialno" +
				" )as c" +
				" where a.enterorg = " + Common.sqlChar(getEnterOrg()) +
				" and a.caseno = " + Common.sqlChar(getCaseNo()) +
				" and a.oldbookvalue<>a.newbookvalue" +
				" and b.serialno1=c.serialno1" +
				"";
	
		Database db = null;
		try {
			db = new Database();
			db.excuteSQL(updateSql);
			db.excuteSQL(updateSql2);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.closeAll();
		}		
	}
		
	public ArrayList queryAll() throws Exception{
		setOldPage();
		ArrayList objList = new ArrayList();
		Database db = null;
		int counter=0;
		try{
			StringBuilder sql = new StringBuilder();
			
			sql.append("select * from (")
				.append("select distinct ").append(
					" a.enterOrg, a.ownership, a.caseNo, a.proofYear, a.proofDoc, a.proofNo, a.writeDate,").append(
					" a.verify, a.adjustDate, a.differenceKind").append(
				" from UNTLA_ADJUSTPROOF a").append(
					" left join UNTLA_AdjustDetail b on a.enterOrg=b.enterOrg and a.ownership=b.ownership and a.caseNo=b.caseNo and a.differenceKind=b.differenceKind").append(
					" left join UNTBU_AdjustDetail c on a.enterOrg=c.enterOrg and a.ownership=c.ownership and a.caseNo=c.caseNo and a.differenceKind=c.differenceKind").append(
					" left join UNTRF_AdjustDetail d on a.enterOrg=d.enterOrg and a.ownership=d.ownership and a.caseNo=d.caseNo and a.differenceKind=d.differenceKind").append(
					" left join UNTMP_AdjustDetail e on a.enterOrg=e.enterOrg and a.ownership=e.ownership and a.caseNo=e.caseNo and a.differenceKind=e.differenceKind").append(
					" left join UNTVP_ADJUSTPROOF f on a.enterorg=f.enterorg and a.ownership=f.ownership and a.caseno=f.caseno and a.differenceKind=f.differenceKind").append(
					" left join UNTRT_ADJUSTPROOF g on a.enterorg=g.enterorg and a.ownership=g.ownership and a.caseno=g.caseno and a.differenceKind=g.differenceKind").append(
				" where 1=1");
			
			//以下四個條件為新增增加單後查詢
			if (!"".equals(getI_enterOrg())) {
				sql.append(" and a.enterorg = ").append(Common.sqlChar(getI_enterOrg()));
				
			}
			if (!"".equals(getI_ownership())) {
				sql.append(" and a.ownership = ").append(Common.sqlChar(getI_ownership()));	
			}
			if (!"".equals(getI_caseNo())) {
				sql.append(" and a.caseno = ").append(Common.sqlChar(getI_caseNo()));	
			}
			if (!"".equals(getI_differenceKind())) {
				sql.append(" and a.differencekind = ").append(Common.sqlChar(getI_differenceKind()));	
			}
			//以上四個條件為新增增加單後查詢
			
			if (!"".equals(getQ_enterOrg())) {
				sql.append(" and a.enterOrg = ").append(Common.sqlChar(getQ_enterOrg()));
				
			} else {
				if (!getIsAdminManager().equalsIgnoreCase("Y")) {
					if ("Y".equals(getIsOrganManager())) {					
						sql.append(" and ( a.enterOrg = ").append(Common.sqlChar(getOrganID())).append(
								" or a.enterOrg in ( select organID from SYSCA_Organ where organSuperior = ").append(
								Common.sqlChar(getOrganID())).append(" ) ) ");
					} else {
						sql.append(" and a.enterOrg = ").append(Common.sqlChar(getOrganID()));
					}
				}
			}
			if (!"".equals(getQ_ownership())) {
				sql.append(" and a.ownership = ").append(Common.sqlChar(getQ_ownership()));	
			}
			if (!"".equals(getQ_caseNoS())){
				sql.append(" and a.caseNo >= ").append(Common.sqlChar(Common.formatRearString(getQ_caseNoS(),10,'0')));
			}
			if (!"".equals(getQ_caseNoE())){
				sql.append(" and a.caseNo <= ").append(Common.sqlChar(Common.formatRearString(getQ_caseNoE(),10, '9')));
			}
			if (!"".equals(getQ_adjustDateS())){
				sql.append(" and a.adjustdate >= ").append(Common.sqlChar(new UNTCH_Tools()._transToCE_Year(getQ_adjustDateS())));
			}
			if (!"".equals(getQ_adjustDateE())){
				sql.append(" and a.adjustdate <= ").append(Common.sqlChar(new UNTCH_Tools()._transToCE_Year(getQ_adjustDateE())));
			}
			if (!"".equals(getQ_approveOrg())){
				sql.append(" and a.approveOrg = ").append(Common.sqlChar(getQ_approveOrg()));
			}
			if (!"".equals(getQ_verify())){
				sql.append(" and a.verify = ").append(Common.sqlChar(getQ_verify()));
			}
			if (!"".equals(getQ_caseName())){
				sql.append(" and a.caseName  like '%").append(getQ_caseName()).append("%'");
			}
			if (!"".equals(getQ_engineeringNo())){
				sql.append(" and a.engineeringNo  = ").append(Common.sqlChar(getQ_engineeringNo()));
			}
			if (!"".equals(getQ_writeDateS())){
				sql.append(" and a.writeDate >= ").append(Common.sqlChar(new UNTCH_Tools()._transToCE_Year(getQ_writeDateS())));
			}
			if (!"".equals(getQ_writeDateE())){
				sql.append(" and a.writeDate <= ").append(Common.sqlChar(new UNTCH_Tools()._transToCE_Year(getQ_writeDateE())));
			}
			if (!"".equals(getQ_proofYear())){
				sql.append(" and a.proofYear = ").append(Common.sqlChar(getQ_proofYear()));
			}
			if (!"".equals(getQ_proofDoc())){
				sql.append(" and a.proofDoc like '%").append(getQ_proofDoc()).append("%'");
			}
			if (!"".equals(getQ_proofNoS())){
				sql.append(" and a.proofNo >= ").append(Common.sqlChar(getQ_proofNoS()));
			}
			if (!"".equals(getQ_proofNoE())){ 
				sql.append(" and a.proofNo <= ").append(Common.sqlChar(getQ_proofNoE()));
			}
			if (!"".equals(getQ_differenceKind())){ 
				sql.append(" and a.differenceKind = ").append(Common.sqlChar(getQ_differenceKind()));
			}
			if (!"".equals(getQ_propertyName1())){
				sql.append(" and b.propertyname1 like '%").append(getQ_propertyName1()).append("%'");
			}
			
			if ("1".equals(this.getRoleid())) {
				sql.append(" and (")
					 .append(" a.editid = ").append(Common.sqlChar(this.getUserID()))// 1185 增加能查到單的異動人員是自己的單
					 .append(" or b.keeper = ").append(Common.sqlChar(this.getUserID()))
					 .append(" or c.keeper = ").append(Common.sqlChar(this.getUserID()))
					 .append(" or d.keeper = ").append(Common.sqlChar(this.getUserID()))
					 .append(" or e.keeper = ").append(Common.sqlChar(this.getUserID()))
					 .append(" or f.keeper = ").append(Common.sqlChar(this.getUserID()))
					 .append(" or g.keeper = ").append(Common.sqlChar(this.getUserID())) 
					 .append(")");
			} else if ("2".equals(this.getRoleid())) {				
				sql.append(" and (")
				 .append(" (b.keepunit = ").append(Common.sqlChar(this.getUnitID()))
				 .append(" or c.keepunit = ").append(Common.sqlChar(this.getUnitID()))
				 .append(" or d.keepunit = ").append(Common.sqlChar(this.getUnitID()))
				 .append(" or e.keepunit = ").append(Common.sqlChar(this.getUnitID()))
				 .append(" or f.keepunit = ").append(Common.sqlChar(this.getUnitID()))
				 .append(" or g.keepunit = ").append(Common.sqlChar(this.getUnitID()))
				 .append(")")
				 .append(" or a.editid = ").append(Common.sqlChar(this.getUserID())) // 1185 增加能查到單的異動人員是自己的單
				 .append(" or (b.keeper = ").append(Common.sqlChar(this.getUserID()))
				 .append(" or c.keeper = ").append(Common.sqlChar(this.getUserID()))
				 .append(" or d.keeper = ").append(Common.sqlChar(this.getUserID()))
				 .append(" or e.keeper = ").append(Common.sqlChar(this.getUserID()))
				 .append(" or f.keeper = ").append(Common.sqlChar(this.getUserID()))
				 .append(" or g.keeper = ").append(Common.sqlChar(this.getUserID()))
				 .append("))");
			}
			
			sql.append(") a order by a.ownership, a.differencekind, a.writedate desc, cast(a.proofyear as int) desc, a.proofno desc");

			db = new Database();			
			ResultSet rs = db.querySQL(sql.toString(),true);
			UNTCH_Tools uctls = new UNTCH_Tools();
			processCurrentPageAttribute(rs);			
			if (getTotalRecord() > 0) {
				int count = getRecordStart();
				int end = getRecordEnd();
				do {
					if (count > end)
						break;
				String rowArray[]=new String[10];
				rowArray[0] = Common.get(rs.getString("enterOrg")); 
				rowArray[1] = Common.get(rs.getString("ownership")); 
				rowArray[2] = uctls._getOwnershipName(rs.getString("ownership"));
				rowArray[3] = Common.get(rs.getString("differenceKind"));
				rowArray[4] = uctls._getDifferenceKindName(rs.getString("differenceKind"));
				rowArray[5] = Common.get(rs.getString("caseNo")); 
				rowArray[6] = Common.get(rs.getString("proofYear")) + "年" +
								Common.get(rs.getString("proofDoc")) + "字第" + 
								Common.get(rs.getString("proofNo")) + "號";
				rowArray[7] = Common.get(uctls._transToROC_Year(rs.getString("writeDate"))); 
				rowArray[8] = Common.get(uctls._transToROC_Year(rs.getString("adjustDate")));  
				rowArray[9] = uctls._getYNName(rs.getString("verify"));
				objList.add(rowArray);
				count++;
				} while (rs.next());
			}
			setStateQueryAllSuccess();
			
		}catch(Exception e){
			log._execLogError(e.getMessage());
		}finally{
			db.closeAll();
		}		
		setNewPage();
		return objList;
	}

	private void setOldPage() throws Exception{
		if (!"".equals(getPageSize1())) {
			setPageSize(Integer.parseInt(getPageSize1()));
			setTotalPage(Integer.parseInt(getTotalPage1()));
			setCurrentPage(Integer.parseInt(getCurrentPage1()));
			setTotalRecord(Integer.parseInt(getTotalRecord1()));
			setRecordStart(Integer.parseInt(getRecordStart1()));
			setRecordEnd(Integer.parseInt(getRecordEnd1()));
		}
	}

	private void setNewPage() throws Exception{
		setPageSize1("" + getPageSize());
		setTotalPage1("" + getTotalPage());
		setCurrentPage1("" + getCurrentPage());
		setTotalRecord1("" + getTotalRecord());
		setRecordStart1("" + getRecordStart());
		setRecordEnd1("" + getRecordEnd());
	}
	
	/**
	 * 檢查是否為最新資料
	 * @return true 表示為 最新資料; false 表不是或是發生異常
	 */
	public boolean checkIsNewData() throws Exception {
		boolean result = false;		
		String sql = "select SUM(cnt) from (" +				
				  // 建物
				  " select COUNT(1) as cnt" +
				  " from UNTBU_ADJUSTDETAIL d" +
				  " left join UNTBU_BUILDING a" +
				  " on d.enterorg =a.enterorg  and d.ownership=a.ownership  and d.differencekind= a.differencekind  and d.propertyno=a.propertyno  and d.serialno=a.serialno" +
				  " where 1=1" +
				  " and d.enterorg = " + Common.sqlChar(getEnterOrg()) +
				  " and d.caseno = " +  Common.sqlChar(getCaseNo()) +
				  " and  ( d.oldnetvalue != a.netvalue or d.oldaccumdepr != a.accumdepr )" +
				  " union all" +
				  // 土改
				  " select COUNT(1) as cnt" +
				  " from UNTRF_ADJUSTDETAIL d" +
				  " left join UNTRF_ATTACHMENT a" +
				  " on d.enterorg =a.enterorg  and d.ownership=a.ownership  and d.differencekind= a.differencekind  and d.propertyno=a.propertyno  and d.serialno=a.serialno" +
				  " where 1=1" +
				  " and d.enterorg = " + Common.sqlChar(getEnterOrg()) +
				  " and d.caseno = " +  Common.sqlChar(getCaseNo()) +
				  " and  ( d.oldnetvalue != a.netvalue or d.oldaccumdepr != a.accumdepr )" +
				  " union all" +
				  // 動產
				  " select COUNT(1) as cnt" +
				  " from UNTMP_ADJUSTDETAIL d" +
				  " left join UNTMP_MOVABLEDETAIL a" +
				  " on d.enterorg =a.enterorg  and d.ownership=a.ownership  and d.differencekind= a.differencekind  and d.propertyno=a.propertyno  and d.serialno=a.serialno" +
				  " where 1=1" +
				  " and d.enterorg = " + Common.sqlChar(getEnterOrg()) +
				  " and d.caseno = " +  Common.sqlChar(getCaseNo()) +
				  " and  ( d.oldnetvalue != a.netvalue or d.oldaccumdepr != a.accumdepr )" +
				  " union all" +
				  // 權利
				  " select COUNT(1) as cnt" +
				  " from UNTRT_ADJUSTPROOF d" +
				  " left join UNTRT_ADDPROOF a" +
				  " on d.enterorg =a.enterorg  and d.ownership=a.ownership  and d.differencekind= a.differencekind  and d.propertyno=a.propertyno  and d.serialno=a.serialno" +
				  " where 1=1" +
				  " and d.enterorg = " + Common.sqlChar(getEnterOrg()) +
				  " and d.caseno = " +  Common.sqlChar(getCaseNo()) +
				  " and  ( d.oldnetvalue != a.netvalue or d.oldaccumdepr != a.accumdepr )" +
				  " ) as base";			
		
		try {
			Object cnt = this.queryObject(sql);
			if (Common.getInt(cnt) == 0) {
				result = true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	public void updateNewData() throws Exception{
		Database db = new Database();
		String enterorg = getEnterOrg();
		String caseno = getCaseNo();
		String differencekind = getDifferenceKind();
		
		String getlosing1ym = " DECLARE @closing1ym AS NVARCHAR(6) " +
		" SET @closing1ym = ( " +
		" SELECT closing1ym " +
		" FROM " +
		" dbo.UNTAC_CLOSINGPT " +
		" WHERE " +
		" enterorg = " + Common.sqlChar(enterorg) +
		" AND differencekind = " + Common.sqlChar(differencekind) +
		" )";
		
		String updateLaSql = " UPDATE a set " +
		" a.engineeringno = b.engineeringno , " +
		" a.propertyname1 = b.propertyname1 , " +
		" a.signno = b.signno , " +
		" a.propertykind = b.propertykind , " +
		" a.fundtype = b.fundtype , " +
		" a.valuable = b.valuable , " +
		" a.taxcredit = b.taxcredit , " +
		" a.originalbv = b.originalbv , " +
		" a.sourcedate = b.sourcedate , " +
		" a.buydate = b.buydate , " +
		" a.oldarea = b.area , " +
		" a.oldholdnume = b.holdnume , " +
		" a.oldholddeno = b.holddeno , " +
		" a.oldholdarea = b.holdarea , " +
		" a.oldbookunit = b.bookunit , " +
		" a.oldbookvalue = b.bookvalue , " +
		" a.oldnetunit = b.netunit , " +
		" a.oldnetvalue = b.netvalue , " +
		" a.newbookunit = round((isnull(b.bookvalue,0) + isnull(a.addbookvalue,0) - isnull(a.reducebookvalue,0)) / ISNULL(a.newholdarea,0),2) , " +
		" a.newbookvalue = isnull(b.bookvalue,0) + isnull(a.addbookvalue,0) - isnull(a.reducebookvalue,0) , " +
		" a.newnetvalue = isnull(b.netvalue,0) + isnull(a.addnetvalue,0) - isnull(a.reducenetvalue,0) , " +
		" a.newnetunit = round(isnull(b.netvalue,0) + isnull(a.addnetvalue,0) - isnull(a.reducenetvalue,0) / ISNULL(a.newholdarea,0),2) , " +
		" a.adjustarea = a.newarea - b.area , " +
		" a.adjustholdarea = a.newholdarea - b.holdarea , " +
		" a.changeitem = '' , " +
		" a.useseparate = b.useseparate , " +
		" a.usekind = b.usekind , " +
		" a.oldpropertyno = b.oldpropertyno , " +
		" a.oldlotno = b.oldlotno , " +
		" a.oldserialno = b.oldserialno , " +
		" a.keepunit = b.keepunit , " +
		" a.keeper = b.keeper , " +
		" a.useunit = b.useunit , " +
		" a.userno = b.userno , " +
		" a.usernote = b.usernote , " +
		" a.place1 = b.place1 , " +
		" a.place = b.place , " +
		" a.editid = " + Common.sqlChar(getEditID()) + "," +
		" a.editdate = CONVERT(varchar(100), GETDATE(), 112) ,  " +
		" a.edittime = REPLACE(CONVERT(varchar(100), GETDATE(), 108),':','') " +
		" FROM    dbo.UNTLA_ADJUSTDETAIL a " +
		" INNER JOIN dbo.UNTLA_LAND b ON b.enterorg = a.enterorg " +
		" AND b.ownership = a.ownership " +
		" AND b.differencekind = a.differencekind " +
		" AND b.propertyno = a.propertyno " +
		" AND b.serialno = a.serialno " +
		" WHERE a.enterorg = " + Common.sqlChar(enterorg) +
		" and a.caseno = " +  Common.sqlChar(caseno) + ";";
		
		//因應已達使用年限之財產做減值，調整帶入最新資料的公式
		String updateBuSql = " UPDATE a set " +
		" a.engineeringno = b.engineeringno , " +
		" a.otherpropertyunit = b.otherpropertyunit, " +
		" a.othermaterial = b.othermaterial , " +
		" a.otherlimityear = b.otherlimityear , " +
		" a.propertyname1 = b.propertyname1 , " +
		" a.signno = b.signno , " +
		" a.propertykind = b.propertykind , " +
		" a.fundtype = b.fundtype , " +
		" a.valuable = b.valuable , " +
		" a.taxcredit = b.taxcredit , " +
		" a.originalbv = b.originalbv , " +
		" a.sourcedate = b.sourcedate , " +
		" a.buydate = b.buydate , " +
		" a.oldarea = b.area , " +
		" a.oldsarea = b.sarea , " +
		" a.oldcarea = b.carea , " +
		" a.oldholdnume = b.holdnume , " +
		" a.oldholddeno = b.holddeno , " +
		" a.oldholdarea = b.holdarea , " +
		" a.oldbookvalue = b.bookvalue , " +
		" a.oldnetvalue = b.netvalue , " +
		" a.adjustholdarea = CASE WHEN ISNULL(a.newholddeno,0) = 0 THEN 0 ELSE round(a.newarea * a.newholdnume / a.newholddeno - a.oldarea,2) end," +
		" a.reduceaccumdepr = ROUND(ISNULL(b.accumdepr,0) * (ISNULL(a.reducebookvalue,0)/ISNULL(b.bookvalue,0)),0), " +
		" a.reducenetvalue = a.reducebookvalue - ROUND(ISNULL(b.accumdepr,0) * (ISNULL(a.reducebookvalue,0)/ISNULL(b.bookvalue,0)),0), " +
		" a.newcarea = ISNULL(b.carea,0) + ISNULL(a.adjustcarea,0), " +
		" a.newsarea = ISNULL(b.sarea,0) + ISNULL(a.adjustsarea,0), " +
		" a.newarea = ISNULL(b.area,0) + ISNULL(a.adjustarea,0), " +
		" a.newholdarea = CASE WHEN ISNULL(a.newholddeno,0) = 0 THEN 0 ELSE ROUND((ISNULL(b.area,0) + ISNULL(a.adjustarea,0)) * a.newholdnume / a.newholddeno,2) end,  " +
		" a.newbookvalue = isnull(b.bookvalue,0) + isnull(a.addbookvalue,0) - isnull(a.reducebookvalue,0) , " +
		" a.newnetvalue = isnull(b.netvalue,0) + isnull(a.addnetvalue,0) - (a.reducebookvalue - ROUND(ISNULL(b.accumdepr,0) * (ISNULL(a.reducebookvalue,0)/ISNULL(b.bookvalue,0)),0)) , " +
		" a.keepunit = b.keepunit , " +
		" a.keeper = b.keeper , " +
		" a.useunit = b.useunit , " +
		" a.userno = b.userno , " +
		" a.usernote = b.usernote , " +
		" a.place1 = b.place1 , " +
		" a.place = b.place , " +
		" a.updatedeprcb = CASE b.deprmethod WHEN '01' then 'N' else 'Y' end, " + 
		" a.olddeprmethod = b.deprmethod , " +
		" a.oldbuildfeecb = b.buildfeecb , " +
		" a.olddeprunitcb = b.deprunitcb , " +
		" a.olddeprpark = b.deprpark , " +
		" a.olddeprunit = b.deprunit , " +
		" a.olddeprunit1 = b.deprunit1 , " +
		" a.olddepraccounts = b.depraccounts , " +
		" a.oldscrapvalue = b.scrapvalue , " +
		" a.olddepramount = b.depramount , " +
		" a.oldaccumdepr = b.accumdepr , " +
		" a.oldapportionmonth = b.apportionmonth , " +
		" a.oldmonthdepr = b.monthdepr , " +
		" a.oldmonthdepr1 = b.monthdepr1 , " +
		" a.oldapportionendym = b.apportionendym , " +		
		" a.newdepramount = isnull(b.bookvalue,0) + isnull(a.addbookvalue,0) - isnull(a.reducebookvalue,0) - ISNULL(a.newscrapvalue,0) - (ISNULL(b.accumdepr,0) - ROUND(ISNULL(b.accumdepr,0) * (ISNULL(a.reducebookvalue,0)/ISNULL(b.bookvalue,0)),0)), " +
		" a.newaccumdepr = ISNULL(b.accumdepr,0) - ROUND(ISNULL(b.accumdepr,0) * (ISNULL(a.reducebookvalue,0)/ISNULL(b.bookvalue,0)),0), " +
		//104.11.02 非折舊財產 apportionendym 為null ,以下欄位SQL DATEADD計算會出錯 ,先以條件避開
		" a.newapportionmonth = case b.apportionendym when '' then null else (CASE WHEN (@closing1ym='201512' and a.differencekind ='110') THEN (b.apportionmonth + a.addlimityear + a.overlimityear - a.reducelimityear) WHEN DATEDIFF(mm,@closing1ym + '01',SUBSTRING(REPLACE(convert(varchar(10),DATEADD(MONTH,+ISNULL(a.addlimityear,0) +ISNULL(a.overlimityear,0) - ISNULL(a.reducelimityear,0),b.apportionendym + '01') ,120),'-',''),1,6) + '01') < 0 THEN 0 ELSE DATEDIFF(mm,@closing1ym + '01',SUBSTRING(REPLACE(convert(varchar(10),DATEADD(MONTH,+ISNULL(a.addlimityear,0) +ISNULL(a.overlimityear,0) - ISNULL(a.reducelimityear,0),b.apportionendym + '01') ,120),'-',''),1,6) + '01') END) end, " +
		" a.newmonthdepr = case b.apportionendym when '' then null else ( CASE WHEN DATEDIFF(mm,@closing1ym + '01',SUBSTRING(REPLACE(convert(varchar(10),DATEADD(MONTH,+ISNULL(a.addlimityear,0) +ISNULL(a.overlimityear,0) - ISNULL(a.reducelimityear,0),b.apportionendym + '01') ,120),'-',''),1,6) + '01') < 0 THEN 0 ELSE FLOOR((isnull(b.bookvalue,0) + isnull(a.addbookvalue,0) - isnull(a.reducebookvalue,0) - ISNULL(a.newscrapvalue,0) - (ISNULL(b.accumdepr,0) - ROUND(ISNULL(b.accumdepr,0) * (ISNULL(a.reducebookvalue,0)/ISNULL(b.bookvalue,0)),0))) / (DATEDIFF(mm,@closing1ym + '01',SUBSTRING(REPLACE(convert(varchar(10),DATEADD(MONTH,+ISNULL(a.addlimityear,0) +ISNULL(a.overlimityear,0) - ISNULL(a.reducelimityear,0),b.apportionendym + '01') ,120),'-',''),1,6)+ '01'))) end ) end, " +
		" a.newmonthdepr1 = case b.apportionendym when '' then null else ( isnull(b.bookvalue,0) + isnull(a.addbookvalue,0) - isnull(a.reducebookvalue,0) - ISNULL(a.newscrapvalue,0) - (ISNULL(b.accumdepr,0) - ROUND(ISNULL(b.accumdepr,0) * (ISNULL(a.reducebookvalue,0)/ISNULL(b.bookvalue,0)),0)) - ((FLOOR((isnull(b.bookvalue,0) + isnull(a.addbookvalue,0) - isnull(a.reducebookvalue,0) - ISNULL(a.newscrapvalue,0) - (ISNULL(b.accumdepr,0) - ROUND(ISNULL(b.accumdepr,0) * (ISNULL(a.reducebookvalue,0)/ISNULL(b.bookvalue,0)),0))) / (DATEDIFF(mm,@closing1ym + '01',SUBSTRING(REPLACE(convert(varchar(10),DATEADD(MONTH,+ISNULL(a.addlimityear,0) +ISNULL(a.overlimityear,0) - ISNULL(a.reducelimityear,0),b.apportionendym + '01') ,120),'-',''),1,6) + '01'))))*(DATEDIFF(mm,@closing1ym + '01',SUBSTRING(REPLACE(convert(varchar(10),DATEADD(MONTH,+ISNULL(a.addlimityear,0) +ISNULL(a.overlimityear,0) - ISNULL(a.reducelimityear,0),b.apportionendym + '01') ,120),'-',''),1,6) + '01')-1)) ) end, " +
		" a.newapportionendym = case b.apportionendym when '' then null else ( SUBSTRING(REPLACE(convert(varchar(10),DATEADD(MONTH,+ISNULL(a.addlimityear,0) +ISNULL(a.overlimityear,0) - ISNULL(a.reducelimityear,0),b.apportionendym + '01') ,120),'-',''),1,6) ) end, " +
		
		" a.oldpropertyno = b.oldpropertyno , " +
		" a.oldlotno = b.oldlotno , " +
		" a.oldserialno = b.oldserialno , " +
		" a.editid = " + Common.sqlChar(getEditID()) + "," +
		" a.editdate = CONVERT(varchar(100), GETDATE(), 112) ,  " +
		" a.edittime = REPLACE(CONVERT(varchar(100), GETDATE(), 108),':','') " +
		" FROM    dbo.UNTBU_ADJUSTDETAIL a " +
		" INNER JOIN dbo.UNTBU_BUILDING b ON b.enterorg = a.enterorg " +
		" AND b.ownership = a.ownership " +
		" AND b.differencekind = a.differencekind " +
		" AND b.propertyno = a.propertyno " +
		" AND b.serialno = a.serialno " +
		" WHERE a.enterorg = " + Common.sqlChar(enterorg) +
		" and a.caseno = " +  Common.sqlChar(caseno) + 
		" and a.oldscrapvalue <> a.oldnetvalue ; ";
		
		//因應已達使用年限之財產做減值，調整帶入最新資料的公式
		String updateBuSql2 = " UPDATE a set " +
		" a.engineeringno = b.engineeringno , " +
		" a.otherpropertyunit = b.otherpropertyunit, " +
		" a.othermaterial = b.othermaterial , " +
		" a.otherlimityear = b.otherlimityear , " +
		" a.propertyname1 = b.propertyname1 , " +
		" a.signno = b.signno , " +
		" a.propertykind = b.propertykind , " +
		" a.fundtype = b.fundtype , " +
		" a.valuable = b.valuable , " +
		" a.taxcredit = b.taxcredit , " +
		" a.originalbv = b.originalbv , " +
		" a.sourcedate = b.sourcedate , " +
		" a.buydate = b.buydate , " +
		" a.oldarea = b.area , " +
		" a.oldsarea = b.sarea , " +
		" a.oldcarea = b.carea , " +
		" a.oldholdnume = b.holdnume , " +
		" a.oldholddeno = b.holddeno , " +
		" a.oldholdarea = b.holdarea , " +
		" a.oldbookvalue = b.bookvalue , " +
		" a.oldnetvalue = b.netvalue , " +
		" a.adjustholdarea = CASE WHEN ISNULL(a.newholddeno,0) = 0 THEN 0 ELSE round(a.newarea * a.newholdnume / a.newholddeno - a.oldarea,2) end," +
		" a.reduceaccumdepr = a.reduceaccumdepr, " +
		" a.reducenetvalue = a.reducebookvalue - a.reduceaccumdepr, " +
		" a.newcarea = ISNULL(b.carea,0) + ISNULL(a.adjustcarea,0), " +
		" a.newsarea = ISNULL(b.sarea,0) + ISNULL(a.adjustsarea,0), " +
		" a.newarea = ISNULL(b.area,0) + ISNULL(a.adjustarea,0), " +
		" a.newholdarea = CASE WHEN ISNULL(a.newholddeno,0) = 0 THEN 0 ELSE ROUND((ISNULL(b.area,0) + ISNULL(a.adjustarea,0)) * a.newholdnume / a.newholddeno,2) end,  " +
		" a.newbookvalue = isnull(b.bookvalue,0) + isnull(a.addbookvalue,0) - isnull(a.reducebookvalue,0) , " +
		" a.newnetvalue = isnull(b.netvalue,0) + isnull(a.addnetvalue,0) - (a.reducebookvalue - a.reduceaccumdepr) , " +
		" a.keepunit = b.keepunit , " +
		" a.keeper = b.keeper , " +
		" a.useunit = b.useunit , " +
		" a.userno = b.userno , " +
		" a.usernote = b.usernote , " +
		" a.place1 = b.place1 , " +
		" a.place = b.place , " +
		" a.updatedeprcb = CASE b.deprmethod WHEN '01' then 'N' else 'Y' end, " + 
		" a.olddeprmethod = b.deprmethod , " +
		" a.oldbuildfeecb = b.buildfeecb , " +
		" a.olddeprunitcb = b.deprunitcb , " +
		" a.olddeprpark = b.deprpark , " +
		" a.olddeprunit = b.deprunit , " +
		" a.olddeprunit1 = b.deprunit1 , " +
		" a.olddepraccounts = b.depraccounts , " +
		" a.oldscrapvalue = b.scrapvalue , " +
		" a.olddepramount = b.depramount , " +
		" a.oldaccumdepr = b.accumdepr , " +
		" a.oldapportionmonth = b.apportionmonth , " +
		" a.oldmonthdepr = b.monthdepr , " +
		" a.oldmonthdepr1 = b.monthdepr1 , " +
		" a.oldapportionendym = b.apportionendym , " +		
		" a.newdepramount = isnull(b.bookvalue,0) + isnull(a.addbookvalue,0) - isnull(a.reducebookvalue,0) - ISNULL(a.newscrapvalue,0) - (ISNULL(b.accumdepr,0) - a.reduceaccumdepr), " +
		" a.newaccumdepr = ISNULL(b.accumdepr,0) - a.reduceaccumdepr, " +
		//104.11.02 非折舊財產 apportionendym 為null ,以下欄位SQL DATEADD計算會出錯 ,先以條件避開
		" a.newapportionmonth = case b.apportionendym when '' then null else (CASE WHEN (@closing1ym='201512' and a.differencekind ='110') THEN (b.apportionmonth + a.addlimityear + a.overlimityear - a.reducelimityear) WHEN DATEDIFF(mm,@closing1ym + '01',SUBSTRING(REPLACE(convert(varchar(10),DATEADD(MONTH,+ISNULL(a.addlimityear,0) +ISNULL(a.overlimityear,0) - ISNULL(a.reducelimityear,0),b.apportionendym + '01') ,120),'-',''),1,6) + '01') < 0 THEN 0 ELSE DATEDIFF(mm,@closing1ym + '01',SUBSTRING(REPLACE(convert(varchar(10),DATEADD(MONTH,+ISNULL(a.addlimityear,0) +ISNULL(a.overlimityear,0) - ISNULL(a.reducelimityear,0),b.apportionendym + '01') ,120),'-',''),1,6) + '01') END) end, " +
		" a.newmonthdepr = case b.apportionendym when '' then null else ( CASE WHEN DATEDIFF(mm,@closing1ym + '01',SUBSTRING(REPLACE(convert(varchar(10),DATEADD(MONTH,+ISNULL(a.addlimityear,0) +ISNULL(a.overlimityear,0) - ISNULL(a.reducelimityear,0),b.apportionendym + '01') ,120),'-',''),1,6) + '01') < 0 THEN 0 ELSE FLOOR((isnull(b.bookvalue,0) + isnull(a.addbookvalue,0) - isnull(a.reducebookvalue,0) - ISNULL(a.newscrapvalue,0) - (ISNULL(b.accumdepr,0) - a.reduceaccumdepr)) / (DATEDIFF(mm,@closing1ym + '01',SUBSTRING(REPLACE(convert(varchar(10),DATEADD(MONTH,+ISNULL(a.addlimityear,0) +ISNULL(a.overlimityear,0) - ISNULL(a.reducelimityear,0),b.apportionendym + '01') ,120),'-',''),1,6)+ '01'))) end ) end, " +
		" a.newmonthdepr1 = case b.apportionendym when '' then null else ( isnull(b.bookvalue,0) + isnull(a.addbookvalue,0) - isnull(a.reducebookvalue,0) - ISNULL(a.newscrapvalue,0) - (ISNULL(b.accumdepr,0) - a.reduceaccumdepr) - ((FLOOR((isnull(b.bookvalue,0) + isnull(a.addbookvalue,0) - isnull(a.reducebookvalue,0) - ISNULL(a.newscrapvalue,0) - (ISNULL(b.accumdepr,0) - a.reduceaccumdepr)) / (DATEDIFF(mm,@closing1ym + '01',SUBSTRING(REPLACE(convert(varchar(10),DATEADD(MONTH,+ISNULL(a.addlimityear,0) +ISNULL(a.overlimityear,0) - ISNULL(a.reducelimityear,0),b.apportionendym + '01') ,120),'-',''),1,6) + '01'))))*(DATEDIFF(mm,@closing1ym + '01',SUBSTRING(REPLACE(convert(varchar(10),DATEADD(MONTH,+ISNULL(a.addlimityear,0) +ISNULL(a.overlimityear,0) - ISNULL(a.reducelimityear,0),b.apportionendym + '01') ,120),'-',''),1,6) + '01')-1)) ) end, " +
		" a.newapportionendym = case b.apportionendym when '' then null else ( SUBSTRING(REPLACE(convert(varchar(10),DATEADD(MONTH,+ISNULL(a.addlimityear,0) +ISNULL(a.overlimityear,0) - ISNULL(a.reducelimityear,0),b.apportionendym + '01') ,120),'-',''),1,6) ) end, " +
		
		" a.oldpropertyno = b.oldpropertyno , " +
		" a.oldlotno = b.oldlotno , " +
		" a.oldserialno = b.oldserialno , " +
		" a.editid = " + Common.sqlChar(getEditID()) + "," +
		" a.editdate = CONVERT(varchar(100), GETDATE(), 112) ,  " +
		" a.edittime = REPLACE(CONVERT(varchar(100), GETDATE(), 108),':','') " +
		" FROM    dbo.UNTBU_ADJUSTDETAIL a " +
		" INNER JOIN dbo.UNTBU_BUILDING b ON b.enterorg = a.enterorg " +
		" AND b.ownership = a.ownership " +
		" AND b.differencekind = a.differencekind " +
		" AND b.propertyno = a.propertyno " +
		" AND b.serialno = a.serialno " +
		" WHERE a.enterorg = " + Common.sqlChar(enterorg) +
		" and a.caseno = " +  Common.sqlChar(caseno) + 
		" and a.oldscrapvalue = a.oldnetvalue ; ";
		
		//因應已達使用年限之財產做減值，調整帶入最新資料的公式
		String updateRfSql = " UPDATE a set " +
		" a.engineeringno = b.engineeringno , " +
		" a.otherpropertyunit = b.otherpropertyunit, " +
		" a.othermaterial = b.othermaterial , " +
		" a.otherlimityear = b.otherlimityear , " +
		" a.propertyname1 = b.propertyname1 , " +
		" a.propertykind = b.propertykind , " +
		" a.fundtype = b.fundtype , " +
		" a.valuable = b.valuable , " +
		" a.taxcredit = b.taxcredit , " +
		" a.originalbv = b.originalbv , " +
		" a.sourcedate = b.sourcedate , " +
		" a.buydate = b.buydate , " +
		" a.oldmeasure = b.measure , " +
		" a.oldbookvalue = b.bookvalue , " +
		" a.oldnetvalue = b.netvalue , " +
		" a.reduceaccumdepr = ROUND(ISNULL(b.accumdepr,0) * (ISNULL(a.reducebookvalue,0)/ISNULL(b.bookvalue,0)),0), " +
		" a.reducenetvalue = a.reducebookvalue - ROUND(ISNULL(b.accumdepr,0) * (ISNULL(a.reducebookvalue,0)/ISNULL(b.bookvalue,0)),0), " +
		" a.newmeasure = ISNULL(b.measure,0) + ISNULL(a.addmeasure,0) - ISNULL(a.reducemeasure,0) , " +
		" a.newbookvalue = isnull(b.bookvalue,0) + isnull(a.addbookvalue,0) - isnull(a.reducebookvalue,0) , " +
		" a.newnetvalue = isnull(b.netvalue,0) + isnull(a.addnetvalue,0) - (a.reducebookvalue - ROUND(ISNULL(b.accumdepr,0) * (ISNULL(a.reducebookvalue,0)/ISNULL(b.bookvalue,0)),0)) , " +
		" a.keepunit = b.keepunit , " +
		" a.keeper = b.keeper , " +
		" a.useunit = b.useunit , " +
		" a.userno = b.userno , " +
		" a.usernote = b.usernote , " +
		" a.place1 = b.place1 , " +
		" a.place = b.place , " +
		" a.updatedeprcb = CASE b.deprmethod WHEN '01' then 'N' else 'Y' end, " + 
		" a.olddeprmethod = b.deprmethod , " +
		" a.oldbuildfeecb = b.buildfeecb , " +
		" a.olddeprunitcb = b.deprunitcb , " +
		" a.olddeprpark = b.deprpark , " +
		" a.olddeprunit = b.deprunit , " +
		" a.olddeprunit1 = b.deprunit1 , " +
		" a.olddepraccounts = b.depraccounts , " +
		" a.oldscrapvalue = b.scrapvalue , " +
		" a.olddepramount = b.depramount , " +
		" a.oldaccumdepr = b.accumdepr , " +
		" a.oldapportionmonth = b.apportionmonth , " +
		" a.oldmonthdepr = b.monthdepr , " +
		" a.oldmonthdepr1 = b.monthdepr1 , " +
		" a.oldapportionendym = b.apportionendym , " +
		" a.newdepramount = isnull(b.bookvalue,0) + isnull(a.addbookvalue,0) - isnull(a.reducebookvalue,0) - ISNULL(a.newscrapvalue,0) - (ISNULL(b.accumdepr,0) - ROUND(ISNULL(b.accumdepr,0) * (ISNULL(a.reducebookvalue,0)/ISNULL(b.bookvalue,0)),0)), " +
		" a.newaccumdepr = ISNULL(b.accumdepr,0) - ROUND(ISNULL(b.accumdepr,0) * (ISNULL(a.reducebookvalue,0)/ISNULL(b.bookvalue,0)),0), " +
		//104.11.02 非折舊財產 apportionendym 為null ,以下欄位SQL DATEADD計算會出錯 ,先以條件避開
		" a.newapportionmonth =  case b.apportionendym when '' then null else (CASE WHEN (@closing1ym='201512' and a.differencekind ='110') THEN (b.apportionmonth + a.addlimityear + a.overlimityear - a.reducelimityear) WHEN DATEDIFF(mm,@closing1ym + '01',SUBSTRING(REPLACE(convert(varchar(10),DATEADD(MONTH,+ISNULL(a.addlimityear,0) +ISNULL(a.overlimityear,0) - ISNULL(a.reducelimityear,0),b.apportionendym + '01') ,120),'-',''),1,6) + '01') < 0 THEN 0 ELSE DATEDIFF(mm,@closing1ym + '01',SUBSTRING(REPLACE(convert(varchar(10),DATEADD(MONTH,+ISNULL(a.addlimityear,0) +ISNULL(a.overlimityear,0) - ISNULL(a.reducelimityear,0),b.apportionendym + '01') ,120),'-',''),1,6) + '01') END) end, " +
		" a.newmonthdepr =  case b.apportionendym when '' then null else ( CASE WHEN DATEDIFF(mm,@closing1ym + '01',SUBSTRING(REPLACE(convert(varchar(10),DATEADD(MONTH,+ISNULL(a.addlimityear,0) +ISNULL(a.overlimityear,0) - ISNULL(a.reducelimityear,0),b.apportionendym + '01') ,120),'-',''),1,6) + '01') < 0 THEN 0 ELSE FLOOR((isnull(b.bookvalue,0) + isnull(a.addbookvalue,0) - isnull(a.reducebookvalue,0) - ISNULL(a.newscrapvalue,0) - (ISNULL(b.accumdepr,0) - ROUND(ISNULL(b.accumdepr,0) * (ISNULL(a.reducebookvalue,0)/ISNULL(b.bookvalue,0)),0))) / (DATEDIFF(mm,@closing1ym + '01',SUBSTRING(REPLACE(convert(varchar(10),DATEADD(MONTH,+ISNULL(a.addlimityear,0) +ISNULL(a.overlimityear,0) - ISNULL(a.reducelimityear,0),b.apportionendym + '01') ,120),'-',''),1,6)+ '01'))) end ) end, " +
		" a.newmonthdepr1 =  case b.apportionendym when '' then null else ( isnull(b.bookvalue,0) + isnull(a.addbookvalue,0) - isnull(a.reducebookvalue,0) - ISNULL(a.newscrapvalue,0) - (ISNULL(b.accumdepr,0) - ROUND(ISNULL(b.accumdepr,0) * (ISNULL(a.reducebookvalue,0)/ISNULL(b.bookvalue,0)),0)) - ((FLOOR((isnull(b.bookvalue,0) + isnull(a.addbookvalue,0) - isnull(a.reducebookvalue,0) - ISNULL(a.newscrapvalue,0) - (ISNULL(b.accumdepr,0) - ROUND(ISNULL(b.accumdepr,0) * (ISNULL(a.reducebookvalue,0)/ISNULL(b.bookvalue,0)),0))) / (DATEDIFF(mm,@closing1ym + '01',SUBSTRING(REPLACE(convert(varchar(10),DATEADD(MONTH,+ISNULL(a.addlimityear,0) +ISNULL(a.overlimityear,0) - ISNULL(a.reducelimityear,0),b.apportionendym + '01') ,120),'-',''),1,6) + '01'))))*(DATEDIFF(mm,@closing1ym + '01',SUBSTRING(REPLACE(convert(varchar(10),DATEADD(MONTH,+ISNULL(a.addlimityear,0) +ISNULL(a.overlimityear,0) - ISNULL(a.reducelimityear,0),b.apportionendym + '01') ,120),'-',''),1,6) + '01')-1)) ) end, " +
		" a.newapportionendym =  case b.apportionendym when '' then null else ( SUBSTRING(REPLACE(convert(varchar(10),DATEADD(MONTH,+ISNULL(a.addlimityear,0) +ISNULL(a.overlimityear,0) - ISNULL(a.reducelimityear,0),b.apportionendym + '01') ,120),'-',''),1,6) ) end, " +
		
		" a.oldpropertyno = b.oldpropertyno , " +
		" a.oldlotno = b.oldlotno , " +
		" a.oldserialno = b.oldserialno , " +
		" a.editid = " + Common.sqlChar(getEditID()) + "," +
		" a.editdate = CONVERT(varchar(100), GETDATE(), 112) ,  " +
		" a.edittime = REPLACE(CONVERT(varchar(100), GETDATE(), 108),':','') " +
		" FROM    dbo.UNTRF_ADJUSTDETAIL a " +
		" INNER JOIN dbo.UNTRF_ATTACHMENT b ON b.enterorg = a.enterorg " +
		" AND b.ownership = a.ownership " +
		" AND b.differencekind = a.differencekind " +
		" AND b.propertyno = a.propertyno " +
		" AND b.serialno = a.serialno " +
		" WHERE a.enterorg = " + Common.sqlChar(enterorg) +
		" and a.caseno = " +  Common.sqlChar(caseno) + 
		" and a.oldscrapvalue <> a.oldnetvalue ; ";
		
		//因應已達使用年限之財產做減值，調整帶入最新資料的公式
		String updateRfSql2 = " UPDATE a set " +
		" a.engineeringno = b.engineeringno , " +
		" a.otherpropertyunit = b.otherpropertyunit, " +
		" a.othermaterial = b.othermaterial , " +
		" a.otherlimityear = b.otherlimityear , " +
		" a.propertyname1 = b.propertyname1 , " +
		" a.propertykind = b.propertykind , " +
		" a.fundtype = b.fundtype , " +
		" a.valuable = b.valuable , " +
		" a.taxcredit = b.taxcredit , " +
		" a.originalbv = b.originalbv , " +
		" a.sourcedate = b.sourcedate , " +
		" a.buydate = b.buydate , " +
		" a.oldmeasure = b.measure , " +
		" a.oldbookvalue = b.bookvalue , " +
		" a.oldnetvalue = b.netvalue , " +
		" a.reduceaccumdepr = a.reduceaccumdepr, " +
		" a.reducenetvalue = a.reducebookvalue - a.reduceaccumdepr, " +
		" a.newmeasure = ISNULL(b.measure,0) + ISNULL(a.addmeasure,0) - ISNULL(a.reducemeasure,0) , " +
		" a.newbookvalue = isnull(b.bookvalue,0) + isnull(a.addbookvalue,0) - isnull(a.reducebookvalue,0) , " +
		" a.newnetvalue = isnull(b.netvalue,0) + isnull(a.addnetvalue,0) - (a.reducebookvalue - a.reduceaccumdepr) , " +
		" a.keepunit = b.keepunit , " +
		" a.keeper = b.keeper , " +
		" a.useunit = b.useunit , " +
		" a.userno = b.userno , " +
		" a.usernote = b.usernote , " +
		" a.place1 = b.place1 , " +
		" a.place = b.place , " +
		" a.updatedeprcb = CASE b.deprmethod WHEN '01' then 'N' else 'Y' end, " + 
		" a.olddeprmethod = b.deprmethod , " +
		" a.oldbuildfeecb = b.buildfeecb , " +
		" a.olddeprunitcb = b.deprunitcb , " +
		" a.olddeprpark = b.deprpark , " +
		" a.olddeprunit = b.deprunit , " +
		" a.olddeprunit1 = b.deprunit1 , " +
		" a.olddepraccounts = b.depraccounts , " +
		" a.oldscrapvalue = b.scrapvalue , " +
		" a.olddepramount = b.depramount , " +
		" a.oldaccumdepr = b.accumdepr , " +
		" a.oldapportionmonth = b.apportionmonth , " +
		" a.oldmonthdepr = b.monthdepr , " +
		" a.oldmonthdepr1 = b.monthdepr1 , " +
		" a.oldapportionendym = b.apportionendym , " +
		" a.newdepramount = isnull(b.bookvalue,0) + isnull(a.addbookvalue,0) - isnull(a.reducebookvalue,0) - ISNULL(a.newscrapvalue,0) - (ISNULL(b.accumdepr,0) - a.reduceaccumdepr), " +
		" a.newaccumdepr = ISNULL(b.accumdepr,0) - ISNULL(a.reduceaccumdepr,0) , " +
		//104.11.02 非折舊財產 apportionendym 為null ,以下欄位SQL DATEADD計算會出錯 ,先以條件避開
		" a.newapportionmonth =  case b.apportionendym when '' then null else (CASE WHEN (@closing1ym='201512' and a.differencekind ='110') THEN (b.apportionmonth + a.addlimityear + a.overlimityear - a.reducelimityear) WHEN DATEDIFF(mm,@closing1ym + '01',SUBSTRING(REPLACE(convert(varchar(10),DATEADD(MONTH,+ISNULL(a.addlimityear,0) +ISNULL(a.overlimityear,0) - ISNULL(a.reducelimityear,0),b.apportionendym + '01') ,120),'-',''),1,6) + '01') < 0 THEN 0 ELSE DATEDIFF(mm,@closing1ym + '01',SUBSTRING(REPLACE(convert(varchar(10),DATEADD(MONTH,+ISNULL(a.addlimityear,0) +ISNULL(a.overlimityear,0) - ISNULL(a.reducelimityear,0),b.apportionendym + '01') ,120),'-',''),1,6) + '01') END) end, " +
		" a.newmonthdepr =  case b.apportionendym when '' then null else ( CASE WHEN DATEDIFF(mm,@closing1ym + '01',SUBSTRING(REPLACE(convert(varchar(10),DATEADD(MONTH,+ISNULL(a.addlimityear,0) +ISNULL(a.overlimityear,0) - ISNULL(a.reducelimityear,0),b.apportionendym + '01') ,120),'-',''),1,6) + '01') < 0 THEN 0 ELSE FLOOR((isnull(b.bookvalue,0) + isnull(a.addbookvalue,0) - isnull(a.reducebookvalue,0) - ISNULL(a.newscrapvalue,0) - (ISNULL(b.accumdepr,0) - a.reduceaccumdepr)) / (DATEDIFF(mm,@closing1ym + '01',SUBSTRING(REPLACE(convert(varchar(10),DATEADD(MONTH,+ISNULL(a.addlimityear,0) +ISNULL(a.overlimityear,0) - ISNULL(a.reducelimityear,0),b.apportionendym + '01') ,120),'-',''),1,6)+ '01'))) end ) end, " +
		" a.newmonthdepr1 =  case b.apportionendym when '' then null else ( isnull(b.bookvalue,0) + isnull(a.addbookvalue,0) - isnull(a.reducebookvalue,0) - ISNULL(a.newscrapvalue,0) - (ISNULL(b.accumdepr,0) - a.reduceaccumdepr) - ((FLOOR((isnull(b.bookvalue,0) + isnull(a.addbookvalue,0) - isnull(a.reducebookvalue,0) - ISNULL(a.newscrapvalue,0) - (ISNULL(b.accumdepr,0) - a.reduceaccumdepr)) / (DATEDIFF(mm,@closing1ym + '01',SUBSTRING(REPLACE(convert(varchar(10),DATEADD(MONTH,+ISNULL(a.addlimityear,0) +ISNULL(a.overlimityear,0) - ISNULL(a.reducelimityear,0),b.apportionendym + '01') ,120),'-',''),1,6) + '01'))))*(DATEDIFF(mm,@closing1ym + '01',SUBSTRING(REPLACE(convert(varchar(10),DATEADD(MONTH,+ISNULL(a.addlimityear,0) +ISNULL(a.overlimityear,0) - ISNULL(a.reducelimityear,0),b.apportionendym + '01') ,120),'-',''),1,6) + '01')-1)) ) end, " +
		" a.newapportionendym =  case b.apportionendym when '' then null else ( SUBSTRING(REPLACE(convert(varchar(10),DATEADD(MONTH,+ISNULL(a.addlimityear,0) +ISNULL(a.overlimityear,0) - ISNULL(a.reducelimityear,0),b.apportionendym + '01') ,120),'-',''),1,6) ) end, " +
		
		" a.oldpropertyno = b.oldpropertyno , " +
		" a.oldlotno = b.oldlotno , " +
		" a.oldserialno = b.oldserialno , " +
		" a.editid = " + Common.sqlChar(getEditID()) + "," +
		" a.editdate = CONVERT(varchar(100), GETDATE(), 112) ,  " +
		" a.edittime = REPLACE(CONVERT(varchar(100), GETDATE(), 108),':','') " +
		" FROM    dbo.UNTRF_ADJUSTDETAIL a " +
		" INNER JOIN dbo.UNTRF_ATTACHMENT b ON b.enterorg = a.enterorg " +
		" AND b.ownership = a.ownership " +
		" AND b.differencekind = a.differencekind " +
		" AND b.propertyno = a.propertyno " +
		" AND b.serialno = a.serialno " +
		" WHERE a.enterorg = " + Common.sqlChar(enterorg) +
		" and a.caseno = " +  Common.sqlChar(caseno) + 
		" and a.oldscrapvalue = a.oldnetvalue ; ";
		
		//因應已達使用年限之財產做減值，調整帶入最新資料的公式
		String updateMpSql = " UPDATE a set " +
		" a.engineeringno = b.engineeringno , " +
		" a.otherpropertyunit = c.otherpropertyunit, " +
		" a.othermaterial = c.othermaterial , " +
		" a.otherlimityear = b.otherlimityear , " +
		" a.propertyname1 = b.propertyname1 , " +
		" a.propertykind = c.propertykind , " +
		" a.fundtype = c.fundtype , " +
		" a.valuable = c.valuable , " +
		" a.sourcedate = c.sourcedate , " +
		" a.buydate = c.buydate , " +
		" a.bookamount = b.bookamount, " +
		" a.oldbookunit = b.bookvalue , " +
		" a.oldbookvalue = b.bookvalue , " +
		" a.oldnetunit = b.netvalue , " +
		" a.oldnetvalue = b.netvalue , " +
		" a.addbookunit = a.addbookvalue , " +
		" a.reducebookunit = a.reducebookvalue , " +
		" a.reduceaccumdepr = ROUND(ISNULL(b.accumdepr,0) * (ISNULL(a.reducebookvalue,0)/ISNULL(b.bookvalue,0)),0), " +
		" a.reducenetunit = a.reducebookvalue - ROUND(ISNULL(b.accumdepr,0) * (ISNULL(a.reducebookvalue,0)/ISNULL(b.bookvalue,0)),0), " +
		" a.reducenetvalue = a.reducebookvalue - ROUND(ISNULL(b.accumdepr,0) * (ISNULL(a.reducebookvalue,0)/ISNULL(b.bookvalue,0)),0), " +
		" a.newbookunit = isnull(b.bookvalue,0) + isnull(a.addbookvalue,0) - isnull(a.reducebookvalue,0) , " +
		" a.newbookvalue = isnull(b.bookvalue,0) + isnull(a.addbookvalue,0) - isnull(a.reducebookvalue,0) , " +
		" a.newnetunit = isnull(b.netvalue,0) + isnull(a.addnetvalue,0) - (a.reducebookvalue - ROUND(ISNULL(b.accumdepr,0) * (ISNULL(a.reducebookvalue,0)/ISNULL(b.bookvalue,0)),0)) , " +
		" a.newnetvalue = isnull(b.netvalue,0) + isnull(a.addnetvalue,0) - (a.reducebookvalue - ROUND(ISNULL(b.accumdepr,0) * (ISNULL(a.reducebookvalue,0)/ISNULL(b.bookvalue,0)),0)) , " +
		" a.keepunit = b.keepunit , " +
		" a.keeper = b.keeper , " +
		" a.useunit = b.useunit , " +
		" a.userno = b.userno , " +
		" a.usernote = b.usernote , " +
		" a.place1 = b.place1 , " +
		" a.place = b.place , " +
		" a.updatedeprcb = CASE b.deprmethod WHEN '01' then 'N' else 'Y' end, " + 
		" a.olddeprmethod = b.deprmethod , " +
		" a.oldbuildfeecb = b.buildfeecb , " +
		" a.olddeprunitcb = b.deprunitcb , " +
		" a.olddeprpark = b.deprpark , " +
		" a.olddeprunit = b.deprunit , " +
		" a.olddeprunit1 = b.deprunit1 , " +
		" a.olddepraccounts = b.depraccounts , " +
		" a.oldscrapvalue = b.scrapvalue , " +
		" a.olddepramount = b.depramount , " +
		" a.oldaccumdepr = b.accumdepr , " +
		" a.oldapportionmonth = b.apportionmonth , " +
		" a.oldmonthdepr = b.monthdepr , " +
		" a.oldmonthdepr1 = b.monthdepr1 , " +
		" a.oldapportionendym = b.apportionendym , " +
		" a.newdepramount = isnull(b.bookvalue,0) + isnull(a.addbookvalue,0) - isnull(a.reducebookvalue,0) - ISNULL(a.newscrapvalue,0) - (ISNULL(b.accumdepr,0) - ROUND(ISNULL(b.accumdepr,0) * (ISNULL(a.reducebookvalue,0)/ISNULL(b.bookvalue,0)),0)), " +
		" a.newaccumdepr = ISNULL(b.accumdepr,0) - ROUND(ISNULL(b.accumdepr,0) * (ISNULL(a.reducebookvalue,0)/ISNULL(b.bookvalue,0)),0), " +
		//104.11.02 非折舊財產 apportionendym 為null ,以下欄位SQL DATEADD計算會出錯 ,先以條件避開
		" a.newapportionmonth = case b.apportionendym when '' then null else (CASE WHEN (@closing1ym='201512' and a.differencekind ='110') THEN (b.apportionmonth + a.addlimityear + a.overlimityear - a.reducelimityear) WHEN DATEDIFF(mm,@closing1ym + '01',SUBSTRING(REPLACE(convert(varchar(10),DATEADD(MONTH,+ISNULL(a.addlimityear,0) +ISNULL(a.overlimityear,0) - ISNULL(a.reducelimityear,0),b.apportionendym + '01') ,120),'-',''),1,6) + '01') < 0 THEN 0 ELSE DATEDIFF(mm,@closing1ym + '01',SUBSTRING(REPLACE(convert(varchar(10),DATEADD(MONTH,+ISNULL(a.addlimityear,0) +ISNULL(a.overlimityear,0) - ISNULL(a.reducelimityear,0),b.apportionendym + '01') ,120),'-',''),1,6) + '01') END) end, " +
		" a.newmonthdepr =  case b.apportionendym when '' then null else ( CASE WHEN DATEDIFF(mm, @closing1ym + '01', SUBSTRING(REPLACE(convert(varchar(10), DATEADD(MONTH, + ISNULL(a.addlimityear, 0) +ISNULL(a.overlimityear,0) - ISNULL(a.reducelimityear, 0), b.apportionendym + '01') ,120), '-', ''), 1, 6) + '01') <= 0 THEN 0 ELSE FLOOR((isnull(b.bookvalue,0) + isnull(a.addbookvalue,0) - isnull(a.reducebookvalue,0) - ISNULL(a.newscrapvalue,0) - (ISNULL(b.accumdepr,0) - ROUND(ISNULL(b.accumdepr,0) * (ISNULL(a.reducebookvalue,0)/ISNULL(b.bookvalue,0)),0))) / (DATEDIFF(mm,@closing1ym + '01',SUBSTRING(REPLACE(convert(varchar(10),DATEADD(MONTH,+ISNULL(a.addlimityear,0) +ISNULL(a.overlimityear,0) - ISNULL(a.reducelimityear,0),b.apportionendym + '01') ,120),'-',''),1,6)+ '01'))) end ) end, " +
		" a.newmonthdepr1 = case b.apportionendym when '' then null ELSE ( case when DATEDIFF(mm, @closing1ym + '01', SUBSTRING(REPLACE(convert(VARCHAR(10), DATEADD(MONTH, + ISNULL(a.addlimityear, 0) +ISNULL(a.overlimityear,0) - ISNULL(a.reducelimityear, 0), b.apportionendym + '01'), 120), '-', ''), 1, 6) + '01') <= 0 then 0 else isnull(b.bookvalue, 0) + isnull(a.addbookvalue, 0) - isnull(a.reducebookvalue, 0) - ISNULL(a.newscrapvalue, 0) - (ISNULL(b.accumdepr, 0) - ROUND(ISNULL(b.accumdepr, 0) * (ISNULL(a.reducebookvalue, 0) / ISNULL(b.bookvalue, 0)), 0)) - ((FLOOR((isnull(b.bookvalue, 0) + isnull(a.addbookvalue, 0) - isnull(a.reducebookvalue, 0) - ISNULL(a.newscrapvalue, 0) - (ISNULL(b.accumdepr, 0) - ROUND(ISNULL(b.accumdepr, 0) * (ISNULL(a.reducebookvalue, 0) / ISNULL(b.bookvalue, 0)), 0))) / (DATEDIFF(mm, @closing1ym + '01', SUBSTRING(REPLACE(convert(VARCHAR(10), DATEADD(MONTH, + ISNULL(a.addlimityear, 0) +ISNULL(a.overlimityear,0) - ISNULL(a.reducelimityear, 0), b.apportionendym + '01'), 120), '-', ''), 1, 6) + '01')))) * (DATEDIFF(mm, @closing1ym + '01', SUBSTRING(REPLACE(convert(VARCHAR(10), DATEADD(MONTH, + ISNULL(a.addlimityear, 0) +ISNULL(a.overlimityear,0) - ISNULL(a.reducelimityear, 0), b.apportionendym + '01'), 120), '-', ''), 1, 6) + '01') - 1)) end ) end, " +
		" a.newapportionendym =  case b.apportionendym when '' then null else ( SUBSTRING(REPLACE(convert(varchar(10),DATEADD(MONTH,+ISNULL(a.addlimityear,0) +ISNULL(a.overlimityear,0) - ISNULL(a.reducelimityear,0),b.apportionendym + '01') ,120),'-',''),1,6) ) end, " +
		" a.oldpropertyno = b.oldpropertyno , " +
		" a.oldlotno = b.oldlotno , " +
		" a.oldserialno = b.oldserialno , " +
		" a.editid = " + Common.sqlChar(getEditID()) + "," +
		" a.editdate = CONVERT(varchar(100), GETDATE(), 112) ,  " +
		" a.edittime = REPLACE(CONVERT(varchar(100), GETDATE(), 108),':','') " +
		" FROM    dbo.UNTMP_ADJUSTDETAIL a " +
		" INNER JOIN dbo.UNTMP_MOVABLEDETAIL b ON b.enterorg = a.enterorg " +
		" AND b.ownership = a.ownership " +
		" AND b.differencekind = a.differencekind " +
		" AND b.propertyno = a.propertyno " +
		" AND b.serialno = a.serialno " +
		" INNER JOIN dbo.UNTMP_MOVABLE c ON c.enterorg = b.enterorg AND c.ownership = b.ownership AND c.differencekind = b.differencekind AND c.propertyno = b.propertyno AND c.lotno = b.lotno " +
		" WHERE a.enterorg = " + Common.sqlChar(enterorg) +
		" and a.caseno = " +  Common.sqlChar(caseno) + 
		" and a.oldscrapvalue <> a.oldnetvalue  ;";
		
		//因應已達使用年限之財產做減值，調整帶入最新資料的公式
		String updateMpSql2 = " UPDATE a set " +
		" a.engineeringno = b.engineeringno , " +
		" a.otherpropertyunit = c.otherpropertyunit, " +
		" a.othermaterial = c.othermaterial , " +
		" a.otherlimityear = b.otherlimityear , " +
		" a.propertyname1 = b.propertyname1 , " +
		" a.propertykind = c.propertykind , " +
		" a.fundtype = c.fundtype , " +
		" a.valuable = c.valuable , " +
		" a.sourcedate = c.sourcedate , " +
		" a.buydate = c.buydate , " +
		" a.bookamount = b.bookamount, " +
		" a.oldbookunit = b.bookvalue , " +
		" a.oldbookvalue = b.bookvalue , " +
		" a.oldnetunit = b.netvalue , " +
		" a.oldnetvalue = b.netvalue , " +
		" a.addbookunit = a.addbookvalue , " +
		" a.reducebookunit = a.reducebookvalue , " +
		" a.reduceaccumdepr = isnull(a.reducebookvalue,0) , " +
		" a.reducenetunit = a.reducebookvalue - a.reduceaccumdepr, " +
		" a.reducenetvalue = a.reducebookvalue - a.reduceaccumdepr , " +
		" a.newbookunit = isnull(b.bookvalue,0) + isnull(a.addbookvalue,0) - isnull(a.reducebookvalue,0) , " +
		" a.newbookvalue = isnull(b.bookvalue,0) + isnull(a.addbookvalue,0) - isnull(a.reducebookvalue,0) , " +
		" a.newnetunit = isnull(b.netvalue,0) + isnull(a.addnetvalue,0) - (a.reducebookvalue - a.reduceaccumdepr) , " +
		" a.newnetvalue = isnull(b.netvalue,0) + isnull(a.addnetvalue,0) - (isnull(a.reducebookvalue,0) - isnull(a.reduceaccumdepr,0)) , " +
		" a.keepunit = b.keepunit , " +
		" a.keeper = b.keeper , " +
		" a.useunit = b.useunit , " +
		" a.userno = b.userno , " +
		" a.usernote = b.usernote , " +
		" a.place1 = b.place1 , " +
		" a.place = b.place , " +
		" a.updatedeprcb = CASE b.deprmethod WHEN '01' then 'N' else 'Y' end, " + 
		" a.olddeprmethod = b.deprmethod , " +
		" a.oldbuildfeecb = b.buildfeecb , " +
		" a.olddeprunitcb = b.deprunitcb , " +
		" a.olddeprpark = b.deprpark , " +
		" a.olddeprunit = b.deprunit , " +
		" a.olddeprunit1 = b.deprunit1 , " +
		" a.olddepraccounts = b.depraccounts , " +
		" a.oldscrapvalue = b.scrapvalue , " +
		" a.olddepramount = b.depramount , " +
		" a.oldaccumdepr = b.accumdepr , " +
		" a.oldapportionmonth = b.apportionmonth , " +
		" a.oldmonthdepr = b.monthdepr , " +
		" a.oldmonthdepr1 = b.monthdepr1 , " +
		" a.oldapportionendym = b.apportionendym , " +
		" a.newdepramount = isnull(b.bookvalue,0) + isnull(a.addbookvalue,0) - isnull(a.reducebookvalue,0) - ISNULL(a.newscrapvalue,0) - (ISNULL(b.accumdepr,0) - a.reduceaccumdepr), " +
		" a.newaccumdepr = isnull(a.oldaccumdepr,0) - isnull(a.reducebookvalue,0), " +
		//104.11.02 非折舊財產 apportionendym 為null ,以下欄位SQL DATEADD計算會出錯 ,先以條件避開
		" a.newapportionmonth = case b.apportionendym when '' then null else (CASE WHEN (@closing1ym='201512' and a.differencekind ='110') THEN (b.apportionmonth + a.addlimityear + a.overlimityear - a.reducelimityear) WHEN DATEDIFF(mm,@closing1ym + '01',SUBSTRING(REPLACE(convert(varchar(10),DATEADD(MONTH,+ISNULL(a.addlimityear,0) +ISNULL(a.overlimityear,0) - ISNULL(a.reducelimityear,0), b.apportionendym + '01') ,120),'-',''),1,6) + '01') < 0 THEN 0 ELSE DATEDIFF(mm,@closing1ym + '01',SUBSTRING(REPLACE(convert(varchar(10),DATEADD(MONTH,+ISNULL(a.addlimityear,0) +ISNULL(a.overlimityear,0) - ISNULL(a.reducelimityear,0),b.apportionendym + '01') ,120),'-',''),1,6) + '01') END) end, " +
		" a.newmonthdepr =  case b.apportionendym when '' then null else ( CASE WHEN DATEDIFF(mm, @closing1ym + '01', SUBSTRING(REPLACE(convert(varchar(10), DATEADD(MONTH, + ISNULL(a.addlimityear, 0) +ISNULL(a.overlimityear,0) - ISNULL(a.reducelimityear, 0), b.apportionendym + '01') ,120), '-', ''), 1, 6) + '01') <= 0 THEN 0 ELSE FLOOR((isnull(b.bookvalue,0) + isnull(a.addbookvalue,0) - isnull(a.reducebookvalue,0) - ISNULL(a.newscrapvalue,0) - (ISNULL(b.accumdepr,0) - ROUND(ISNULL(b.accumdepr,0) * (ISNULL(a.reducebookvalue,0)/ISNULL(b.bookvalue,0)),0))) / (DATEDIFF(mm,@closing1ym + '01',SUBSTRING(REPLACE(convert(varchar(10),DATEADD(MONTH,+ISNULL(a.addlimityear,0) +ISNULL(a.overlimityear,0) - ISNULL(a.reducelimityear,0),b.apportionendym + '01') ,120),'-',''),1,6)+ '01'))) end ) end, " +
		" a.newmonthdepr1 = case b.apportionendym when '' then null ELSE ( case when DATEDIFF(mm, @closing1ym + '01', SUBSTRING(REPLACE(convert(VARCHAR(10), DATEADD(MONTH, + ISNULL(a.addlimityear, 0) +ISNULL(a.overlimityear,0) - ISNULL(a.reducelimityear, 0), b.apportionendym + '01'), 120), '-', ''), 1, 6) + '01') <= 0 then 0 else isnull(b.bookvalue, 0) + isnull(a.addbookvalue, 0) - isnull(a.reducebookvalue, 0) - ISNULL(a.newscrapvalue, 0) - (ISNULL(b.accumdepr, 0) - ROUND(ISNULL(b.accumdepr, 0) * (ISNULL(a.reducebookvalue, 0) / ISNULL(b.bookvalue, 0)), 0)) - ((FLOOR((isnull(b.bookvalue, 0) + isnull(a.addbookvalue, 0) - isnull(a.reducebookvalue, 0) - ISNULL(a.newscrapvalue, 0) - (ISNULL(b.accumdepr, 0) - ROUND(ISNULL(b.accumdepr, 0) * (ISNULL(a.reducebookvalue, 0) / ISNULL(b.bookvalue, 0)), 0))) / (DATEDIFF(mm, @closing1ym + '01', SUBSTRING(REPLACE(convert(VARCHAR(10), DATEADD(MONTH, + ISNULL(a.addlimityear, 0) +ISNULL(a.overlimityear,0) - ISNULL(a.reducelimityear, 0), b.apportionendym + '01'), 120), '-', ''), 1, 6) + '01')))) * (DATEDIFF(mm, @closing1ym + '01', SUBSTRING(REPLACE(convert(VARCHAR(10), DATEADD(MONTH, + ISNULL(a.addlimityear, 0) +ISNULL(a.overlimityear,0) - ISNULL(a.reducelimityear, 0), b.apportionendym + '01'), 120), '-', ''), 1, 6) + '01') - 1)) end ) end, " +
		" a.newapportionendym =  case b.apportionendym when '' then null else ( SUBSTRING(REPLACE(convert(varchar(10),DATEADD(MONTH,+ISNULL(a.addlimityear,0) +ISNULL(a.overlimityear,0) - ISNULL(a.reducelimityear,0),b.apportionendym + '01') ,120),'-',''),1,6) ) end, " +
		" a.oldpropertyno = b.oldpropertyno , " +
		" a.oldlotno = b.oldlotno , " +
		" a.oldserialno = b.oldserialno , " +
		" a.editid = " + Common.sqlChar(getEditID()) + "," +
		" a.editdate = CONVERT(varchar(100), GETDATE(), 112) ,  " +
		" a.edittime = REPLACE(CONVERT(varchar(100), GETDATE(), 108),':','') " +
		" FROM    dbo.UNTMP_ADJUSTDETAIL a " +
		" INNER JOIN dbo.UNTMP_MOVABLEDETAIL b ON b.enterorg = a.enterorg " +
		" AND b.ownership = a.ownership " +
		" AND b.differencekind = a.differencekind " +
		" AND b.propertyno = a.propertyno " +
		" AND b.serialno = a.serialno " +
		" INNER JOIN dbo.UNTMP_MOVABLE c ON c.enterorg = b.enterorg AND c.ownership = b.ownership AND c.differencekind = b.differencekind AND c.propertyno = b.propertyno AND c.lotno = b.lotno " +
		" WHERE a.enterorg = " + Common.sqlChar(enterorg) +
		" and a.caseno = " +  Common.sqlChar(caseno) + 
		" and a.oldscrapvalue = a.oldnetvalue  ;";
		
		
		String updateVpSql = " UPDATE a set " +
		" a.engineeringno = b.engineeringno , " +
		" a.otherpropertyunit = b.otherpropertyunit, " +
		" a.othermaterial = b.othermaterial , " +
		" a.propertyname1 = b.propertyname1 , " +
		" a.sourcedate = b.sourcedate , " +
		" a.buydate = b.buydate , " +
		" a.propertykind = b.propertykind , " +
		" a.fundtype = b.fundtype , " +
		" a.securityname = b.securityname , " +
		" a.oldbookamount = b.bookamount, " +
		" a.oldbookvalue = b.bookvalue , " +
		" a.newbookamount = ISNULL(b.bookamount,0) + ISNULL(a.addbookamount,0) - ISNULL(a.reducebookamount,0) , " +
		" a.newbookvalue = isnull(b.bookvalue,0) + isnull(a.addbookvalue,0) - isnull(a.reducebookvalue,0) , " +
		" a.keepunit = b.keepunit , " +
		" a.keeper = b.keeper , " +
		" a.useunit = b.useunit , " +
		" a.userno = b.userno , " +
		" a.usernote = b.usernote , " +
		" a.place1 = b.place1 , " +
		" a.place = b.place , " +
		" a.oldpropertyno = b.oldpropertyno , " +
		" a.oldlotno = b.oldlotno , " +
		" a.oldserialno = b.oldserialno , " +
		" a.editid = " + Common.sqlChar(getEditID()) + "," +
		" a.editdate = CONVERT(varchar(100), GETDATE(), 112) , " +
		" a.edittime = REPLACE(CONVERT(varchar(100), GETDATE(), 108),':','') " +
		" FROM    dbo.UNTVP_ADJUSTPROOF a " +
		" INNER JOIN dbo.UNTVP_ADDPROOF b ON b.enterorg = a.enterorg " +
		" AND b.ownership = a.ownership " +
		" AND b.differencekind = a.differencekind " +
		" AND b.propertyno = a.propertyno " +
		" AND b.serialno = a.serialno " +
		" WHERE a.enterorg = " + Common.sqlChar(enterorg) +
		" and a.caseno = " +  Common.sqlChar(caseno) + ";";
		
		String updateRtSql = " UPDATE a set " +
		" a.engineeringno = b.engineeringno , " +
		" a.otherpropertyunit = b.otherpropertyunit, " +
		" a.othermaterial = b.othermaterial , " +
		" a.otherlimityear = b.otherlimityear , " +
		" a.propertyname1 = b.propertyname1 , " +
		" a.propertykind = b.propertykind , " +
		" a.fundtype = b.fundtype , " +
		" a.buydate = b.buydate , " +
		" a.sourcekind = b.sourcekind , " +
		" a.sourcedate = b.sourcedate , " +
		" a.oldbookvalue = b.bookvalue , " +
		" a.newbookvalue = isnull(b.bookvalue,0) + isnull(a.addbookvalue,0) - isnull(a.reducebookvalue,0) , " +
		" a.keepunit = b.keepunit , " +
		" a.keeper = b.keeper , " +
		" a.useunit = b.useunit , " +
		" a.userno = b.userno , " +
		" a.usernote = b.usernote , " +
		" a.place1 = b.place1 , " +
		" a.place = b.place , " +
		" a.oldpropertyno = b.oldpropertyno , " +
		" a.oldlotno = b.oldlotno , " +
		" a.oldserialno = b.oldserialno , " +
		" a.editid = " + Common.sqlChar(getEditID()) + "," +
		" a.editdate = CONVERT(varchar(100), GETDATE(), 112) , " +
		" a.edittime = REPLACE(CONVERT(varchar(100), GETDATE(), 108),':','') " +
		
		" ,a.oldnetvalue = b.netvalue" + //F55
		" ,a.reduceaccumdepr = ROUND(ISNULL(b.accumdepr, 0) * (ISNULL(a.reducebookvalue, 0) / ISNULL(b.bookvalue, 0)), 0)" + //F57
		" ,a.reducenetvalue = a.reducebookvalue - ROUND(ISNULL(b.accumdepr,0) * (ISNULL(a.reducebookvalue,0)/ISNULL(b.bookvalue,0)),0) " +
		" ,a.newnetvalue = b.netvalue + a.addnetvalue - a.reducebookvalue - (ROUND(ISNULL(b.accumdepr, 0) * (ISNULL(a.reducebookvalue, 0) / ISNULL(b.bookvalue, 0)), 0))" + // F59= F55+ F56- F58
		" ,a.updatedeprcb = CASE b.deprmethod WHEN '01' then 'N' else 'Y' end " + 
		" ,a.olddeprmethod = b.deprmethod" + //F61
		" ,a.oldbuildfeecb = b.buildfeecb" + //F62
		" ,a.olddeprunitcb = b.deprunitcb" + //F63
		" ,a.olddeprpark = b.deprpark" +     //F64
		" ,a.olddeprunit = b.deprunit" + //F65
		" ,a.olddeprunit1 = b.deprunit1" +   //F66
		" ,a.olddepraccounts = b.depraccounts" + //F67
		" ,a.oldscrapvalue = b.scrapvalue" + //F68
		" ,a.olddepramount = b.depramount" + //F69
		" ,a.oldaccumdepr = b.accumdepr" + //F70
		" ,a.oldapportionmonth = b.apportionmonth" + //F71
		" ,a.oldmonthdepr = b.monthdepr" + //F72
		" ,a.oldmonthdepr1 = b.monthdepr1" + //F73
		" ,a.oldapportionendym = b.apportionendym" + //F74
		//F83
		" ,a.newdepramount = isnull(b.bookvalue,0) + isnull(a.addbookvalue,0) - isnull(a.reducebookvalue,0) - ISNULL(a.newscrapvalue,0) - (ISNULL(b.accumdepr,0) - ROUND(ISNULL(b.accumdepr,0) * (ISNULL(a.reducebookvalue,0)/ISNULL(b.bookvalue,0)),0))" +
		//F84
		" ,a.newaccumdepr = ISNULL(b.accumdepr,0) - ROUND(ISNULL(b.accumdepr,0) * (ISNULL(a.reducebookvalue,0)/ISNULL(b.bookvalue,0)),0)" + //F84
		//F85
		" ,a.newapportionmonth = case b.apportionendym when '' then null else (CASE WHEN (@closing1ym='201512' and a.differencekind ='110') THEN (b.apportionmonth + a.addlimityear + a.overlimityear - a.reducelimityear) WHEN DATEDIFF(mm,@closing1ym + '01',SUBSTRING(REPLACE(convert(varchar(10),DATEADD(MONTH,+ISNULL(a.addlimityear,0) +ISNULL(a.overlimityear,0) - ISNULL(a.reducelimityear,0),b.apportionendym + '01') ,120),'-',''),1,6) + '01') < 0 THEN 0 ELSE DATEDIFF(mm,@closing1ym + '01',SUBSTRING(REPLACE(convert(varchar(10),DATEADD(MONTH,+ISNULL(a.addlimityear,0) +ISNULL(a.overlimityear,0) - ISNULL(a.reducelimityear,0),b.apportionendym + '01') ,120),'-',''),1,6) + '01') END) end" +
		//F86
		" ,a.newmonthdepr = case b.apportionendym when '' then null else ( CASE WHEN DATEDIFF(mm,@closing1ym + '01',SUBSTRING(REPLACE(convert(varchar(10),DATEADD(MONTH,+ISNULL(a.addlimityear,0) +ISNULL(a.overlimityear,0) - ISNULL(a.reducelimityear,0),b.apportionendym + '01') ,120),'-',''),1,6) + '01') < 0 THEN 0 ELSE FLOOR((isnull(b.bookvalue,0) + isnull(a.addbookvalue,0) - isnull(a.reducebookvalue,0) - ISNULL(a.newscrapvalue,0) - (ISNULL(b.accumdepr,0) - ROUND(ISNULL(b.accumdepr,0) * (ISNULL(a.reducebookvalue,0)/ISNULL(b.bookvalue,0)),0))) / (DATEDIFF(mm,@closing1ym + '01',SUBSTRING(REPLACE(convert(varchar(10),DATEADD(MONTH,+ISNULL(a.addlimityear,0) +ISNULL(a.overlimityear,0) - ISNULL(a.reducelimityear,0),b.apportionendym + '01') ,120),'-',''),1,6)+ '01'))) end ) end" +
		//F87
		" ,a.newmonthdepr1 = case b.apportionendym when '' then null else ( isnull(b.bookvalue,0) + isnull(a.addbookvalue,0) - isnull(a.reducebookvalue,0) - ISNULL(a.newscrapvalue,0) - (ISNULL(b.accumdepr,0) - ROUND(ISNULL(b.accumdepr,0) * (ISNULL(a.reducebookvalue,0)/ISNULL(b.bookvalue,0)),0)) - ((FLOOR((isnull(b.bookvalue,0) + isnull(a.addbookvalue,0) - isnull(a.reducebookvalue,0) - ISNULL(a.newscrapvalue,0) - (ISNULL(b.accumdepr,0) - ROUND(ISNULL(b.accumdepr,0) * (ISNULL(a.reducebookvalue,0)/ISNULL(b.bookvalue,0)),0))) / (DATEDIFF(mm,@closing1ym + '01',SUBSTRING(REPLACE(convert(varchar(10),DATEADD(MONTH,+ISNULL(a.addlimityear,0) +ISNULL(a.overlimityear,0) - ISNULL(a.reducelimityear,0),b.apportionendym + '01') ,120),'-',''),1,6) + '01'))))*(DATEDIFF(mm,@closing1ym + '01',SUBSTRING(REPLACE(convert(varchar(10),DATEADD(MONTH,+ISNULL(a.addlimityear,0) +ISNULL(a.overlimityear,0) - ISNULL(a.reducelimityear,0),b.apportionendym + '01') ,120),'-',''),1,6) + '01')-1)) ) end" +
		//F88
		" ,a.newapportionendym = case b.apportionendym when '' then null else ( SUBSTRING(REPLACE(convert(varchar(10),DATEADD(MONTH,+ISNULL(a.addlimityear,0) +ISNULL(a.overlimityear,0) - ISNULL(a.reducelimityear,0),b.apportionendym + '01') ,120),'-',''),1,6) ) end" +
		" FROM    dbo.UNTRT_ADJUSTPROOF a " +
		" INNER JOIN dbo.UNTRT_ADDPROOF b ON b.enterorg = a.enterorg " +
		" AND b.ownership = a.ownership " +
		" AND b.differencekind = a.differencekind " +
		" AND b.propertyno = a.propertyno " +
		" AND b.serialno = a.serialno " +
		" WHERE a.enterorg = " + Common.sqlChar(enterorg) +
		" and a.caseno = " +  Common.sqlChar(caseno) + ";";

		db.excuteSQL(getlosing1ym + updateLaSql + updateBuSql + updateBuSql2 + updateRfSql + updateRfSql2 + updateMpSql + updateMpSql2 + updateVpSql + updateRtSql);
		this.setStateUpdateSuccess();
		setErrorMsg("修改完成，已取得最新資料");
	}
	
	//==============================================================
	
	private String caseName;
	private String writeDate;
	private String writeUnit;
	private String proofYear;
	private String proofDoc;
	private String proofNo;
	private String manageNo;
	private String summonsNo;
	private String approveOrg;
	private String approveDate;
	private String approveDoc;
	
	public String getProofYear() {return checkGet(proofYear);}
	public void setProofYear(String proofYear) {this.proofYear = checkSet(proofYear);}
	public String getStrAdjustDate() {return checkGet(strAdjustDate);}
	public void setStrAdjustDate(String strAdjustDate) {this.strAdjustDate = checkSet(strAdjustDate);}
	
	private String closing;
	private String strAdjustDate;

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
	public String getApproveOrg(){ return checkGet(approveOrg); }
	public void setApproveOrg(String s){ approveOrg=checkSet(s); }
	public String getApproveDate(){ return checkGet(approveDate); }
	public void setApproveDate(String s){ approveDate=checkSet(s); }
	public String getApproveDoc(){ return checkGet(approveDoc); }
	public void setApproveDoc(String s){ approveDoc=checkSet(s); }
	public String getClosing() {return checkGet(closing);}
	public void setClosing(String closing) {this.closing = checkSet(closing);}

	String verifyError;
	public String getVerifyError(){ return checkGet(verifyError); }
	public void setVerifyError(String s){ verifyError=checkSet(s); }
		
	private String signNo;
	private String oldCArea;
	private String oldSArea;
	private String oldArea;
	private String oldHoldNume;
	private String oldHoldDeno;
	private String oldHoldArea;
	private String adjustCArea;
	private String adjustSArea;
	private String adjustArea;
	private String adjustHoldArea;
	private String newCArea;
	private String newSArea;
	private String newArea;
	private String newHoldNume;
	private String newHoldDeno;
	private String newHoldArea;
	
	private String signNo1;
	private String signNo2;
	private String signNo3;
	private String signNo4;
	private String signNo5;
	
	private String CArea;
	private String SArea;
	private String area;
	private String holdNume;
	private String holdDeno;
	private String holdArea;
	
	public String getSignNo() {return checkGet(signNo);}
	public void setSignNo(String signNo) {this.signNo = checkSet(signNo);}
	public String getOldCArea() {return checkGet(oldCArea);}
	public void setOldCArea(String oldCArea) {this.oldCArea = checkSet(oldCArea);}
	public String getOldSArea() {return checkGet(oldSArea);}
	public void setOldSArea(String oldSArea) {this.oldSArea = checkSet(oldSArea);}
	public String getOldArea() {return checkGet(oldArea);}
	public void setOldArea(String oldArea) {this.oldArea = checkSet(oldArea);}
	public String getOldHoldNume() {return checkGet(oldHoldNume);}
	public void setOldHoldNume(String oldHoldNume) {this.oldHoldNume = checkSet(oldHoldNume);}
	public String getOldHoldDeno() {return checkGet(oldHoldDeno);}
	public void setOldHoldDeno(String oldHoldDeno) {this.oldHoldDeno = checkSet(oldHoldDeno);}
	public String getOldHoldArea() {return checkGet(oldHoldArea);}
	public void setOldHoldArea(String oldHoldArea) {this.oldHoldArea = checkSet(oldHoldArea);}
	public String getAdjustCArea() {return checkGet(adjustCArea);}
	public void setAdjustCArea(String adjustCArea) {this.adjustCArea = checkSet(adjustCArea);}
	public String getAdjustSArea() {return checkGet(adjustSArea);}
	public void setAdjustSArea(String adjustSArea) {this.adjustSArea = checkSet(adjustSArea);}
	public String getAdjustArea() {return checkGet(adjustArea);}
	public void setAdjustArea(String adjustArea) {this.adjustArea = checkSet(adjustArea);}
	public String getAdjustHoldArea() {return checkGet(adjustHoldArea);}
	public void setAdjustHoldArea(String adjustHoldArea) {this.adjustHoldArea = checkSet(adjustHoldArea);}
	public String getNewCArea() {return checkGet(newCArea);}
	public void setNewCArea(String newCArea) {this.newCArea = checkSet(newCArea);}
	public String getNewSArea() {return checkGet(newSArea);}
	public void setNewSArea(String newSArea) {this.newSArea = checkSet(newSArea);}
	public String getNewArea() {return checkGet(newArea);}
	public void setNewArea(String newArea) {this.newArea = checkSet(newArea);}
	public String getNewHoldNume() {return checkGet(newHoldNume);}
	public void setNewHoldNume(String newHoldNume) {this.newHoldNume = checkSet(newHoldNume);}
	public String getNewHoldDeno() {return checkGet(newHoldDeno);}
	public void setNewHoldDeno(String newHoldDeno) {this.newHoldDeno = checkSet(newHoldDeno);}
	public String getNewHoldArea() {return checkGet(newHoldArea);}
	public void setNewHoldArea(String newHoldArea) {this.newHoldArea = checkSet(newHoldArea);}
	public String getCArea() {return checkGet(CArea);}
	public void setCArea(String cArea) {CArea = checkSet(cArea);}
	public String getSArea() {return checkGet(SArea);}
	public void setSArea(String sArea) {SArea = checkSet(sArea);}
	public String getArea() {return checkGet(area);}
	public void setArea(String area) {this.area = checkSet(area);}
	public String getHoldNume() {return checkGet(holdNume);}
	public void setHoldNume(String holdNume) {this.holdNume = checkSet(holdNume);}
	public String getHoldDeno() {return checkGet(holdDeno);}
	public void setHoldDeno(String holdDeno) {this.holdDeno = checkSet(holdDeno);}
	public String getHoldArea() {return checkGet(holdArea);}
	public void setHoldArea(String holdArea) {this.holdArea = checkSet(holdArea);}
	public String getSignNo1() {return checkGet(signNo1);}
	public void setSignNo1(String signNo1) {this.signNo1 = checkSet(signNo1);}
	public String getSignNo2() {return checkGet(signNo2);}
	public void setSignNo2(String signNo2) {this.signNo2 = checkSet(signNo2);}
	public String getSignNo3() {return checkGet(signNo3);}
	public void setSignNo3(String signNo3) {this.signNo3 = checkSet(signNo3);}
	public String getSignNo4() {return checkGet(signNo4);}
	public void setSignNo4(String signNo4) {this.signNo4 = checkSet(signNo4);}
	public String getSignNo5() {return checkGet(signNo5);}
	public void setSignNo5(String signNo5) {this.signNo5 = checkSet(signNo5);}
	
	private String enterOrgName;
	private String engineeringNoName;
	private String propertyNoName;
	
	private String adjustType;
	private String adjustMeasure;
	private String adjustBookValue;
	
	private String oldPropertyName;
	private String oldcause;
	
	private String measure;
	private String bookValue;
	private String propertyUnit;
	 
	
	private String enterOrg;
	private String caseNo;
	private String ownership;
	private String differenceKind;
	private String engineeringNo;
	private String caseSerialNo;
	private String propertyNo;
	private String serialNo;
	private String otherLimitYear;
	private String propertyName1;
	private String cause;
	private String cause1;
	private String cause2;
	private String adjustDate;
	private String verify;
	private String propertyKind;
	private String fundType;
	private String valuable;
	private String taxCredit;
	private String originalBV;
	private String sourceDate;
	private String buyDate;
	private String oldMeasure;
	private String oldBookValue;
	private String oldNetValue;
	private String addMeasure;
	private String reduceMeasure;
	private String addBookValue;
	private String addNetValue;
	private String reduceBookValue;
	private String reduceAccumDepr;
	private String reduceNetValue;
	private String newMeasure;
	private String newBookValue;
	private String newNetValue;
	private String bookNotes;
	private String keepUnit;
	private String keeper;
	private String useUnit;
	private String userNo;
	private String userNote;
	private String place1;
	private String place;
	private String oldDeprMethod;
	private String oldBuildFeeCB;
	private String oldDeprUnitCB;
	private String oldDeprPark;
	private String oldDeprUnit;
	private String oldDeprUnit1;
	private String oldDeprAccounts;
	private String oldScrapValue;
	private String oldDeprAmount;
	private String oldAccumDepr;
	private String oldApportionMonth;
	private String oldMonthDepr;
	private String newDeprMethod;
	private String newBuildFeeCB;
	private String newDeprUnitCB;
	private String newDeprPark;
	private String newDeprUnit;
	private String newDeprUnit1;
	private String newDeprAccounts;
	private String newScrapValue;
	private String newDeprAmount;
	private String newAccumDepr;
	private String newApportionMonth;
	private String newMonthDepr;
	private String oldPropertyNo;
	private String oldSerialNo;
	private String notes;
	private String editID;
	private String editDate;
	private String editTime;
	
	
	
	public String getEnterOrgName() {return checkGet(enterOrgName);}
	public void setEnterOrgName(String enterOrgName) {this.enterOrgName = checkSet(enterOrgName);}
	public String getEngineeringNoName() {return checkGet(engineeringNoName);}
	public void setEngineeringNoName(String engineeringNoName) {this.engineeringNoName = checkSet(engineeringNoName);}
	public String getPropertyNoName() {return checkGet(propertyNoName);}
	public void setPropertyNoName(String propertyNoName) {this.propertyNoName = checkSet(propertyNoName);}
	public String getAdjustType() {return checkGet(adjustType);}
	public void setAdjustType(String adjustType) {this.adjustType = checkSet(adjustType);}
	public String getAdjustMeasure() {return checkGet(adjustMeasure);}
	public void setAdjustMeasure(String adjustMeasure) {this.adjustMeasure = checkSet(adjustMeasure);}
	public String getAdjustBookValue() {return checkGet(adjustBookValue);}
	public void setAdjustBookValue(String adjustBookValue) {this.adjustBookValue = checkSet(adjustBookValue);}
	public String getOldPropertyName() {return checkGet(oldPropertyName);}
	public void setOldPropertyName(String oldPropertyName) {this.oldPropertyName = checkSet(oldPropertyName);}
	public String getOldcause() {return checkGet(oldcause);}
	public void setOldcause(String oldcause) {this.oldcause = checkSet(oldcause);}
	public String getMeasure() {return checkGet(measure);}
	public void setMeasure(String measure) {this.measure = checkSet(measure);}
	public String getBookValue() {return checkGet(bookValue);}
	public void setBookValue(String bookValue) {this.bookValue = checkSet(bookValue);}
	public String getPropertyUnit() {return checkGet(propertyUnit);}
	public void setPropertyUnit(String propertyUnit) {this.propertyUnit = checkSet(propertyUnit);}
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
	public String getPropertyNo() {return checkGet(propertyNo);}
	public void setPropertyNo(String propertyNo) {this.propertyNo = checkSet(propertyNo);}
	public String getSerialNo() {return checkGet(serialNo);}
	public void setSerialNo(String serialNo) {this.serialNo = checkSet(serialNo);}
	public String getOtherLimitYear() {return checkGet(otherLimitYear);}
	public void setOtherLimitYear(String otherLimitYear) {this.otherLimitYear = checkSet(otherLimitYear);}
	public String getPropertyName1() {return checkGet(propertyName1);}
	public void setPropertyName1(String propertyName1) {this.propertyName1 = checkSet(propertyName1);}
	public String getCause() {return checkGet(cause);}
	public void setCause(String cause) {this.cause = checkSet(cause);}
	public String getCause1() {return checkGet(cause1);}
	public void setCause1(String cause1) {this.cause1 = checkSet(cause1);}
	public String getCause2() {return checkGet(cause2);}
	public void setCause2(String cause2) {this.cause2 = checkSet(cause2);}
	public String getAdjustDate() {return checkGet(adjustDate);}
	public void setAdjustDate(String adjustDate) {this.adjustDate = checkSet(adjustDate);}
	public String getVerify() {return checkGet(verify);}
	public void setVerify(String verify) {this.verify = checkSet(verify);}
	public String getPropertyKind() {return checkGet(propertyKind);}
	public void setPropertyKind(String propertyKind) {this.propertyKind = checkSet(propertyKind);}
	public String getFundType() {return checkGet(fundType);}
	public void setFundType(String fundType) {this.fundType = checkSet(fundType);}
	public String getValuable() {return checkGet(valuable);}
	public void setValuable(String valuable) {this.valuable = checkSet(valuable);}
	public String getTaxCredit() {return checkGet(taxCredit);}
	public void setTaxCredit(String taxCredit) {this.taxCredit = checkSet(taxCredit);}
	public String getOriginalBV() {return checkGet(originalBV);}
	public void setOriginalBV(String originalBV) {this.originalBV = checkSet(originalBV);}
	public String getSourceDate() {return checkGet(sourceDate);}
	public void setSourceDate(String sourceDate) {this.sourceDate = checkSet(sourceDate);}
	public String getBuyDate() {return checkGet(buyDate);}
	public void setBuyDate(String buyDate) {this.buyDate = checkSet(buyDate);}
	public String getOldMeasure() {return checkGet(oldMeasure);}
	public void setOldMeasure(String oldMeasure) {this.oldMeasure = checkSet(oldMeasure);}
	public String getOldBookValue() {return checkGet(oldBookValue);}
	public void setOldBookValue(String oldBookValue) {this.oldBookValue = checkSet(oldBookValue);}
	public String getOldNetValue() {return checkGet(oldNetValue);}
	public void setOldNetValue(String oldNetValue) {this.oldNetValue = checkSet(oldNetValue);}
	public String getAddMeasure() {return checkGet(addMeasure);}
	public void setAddMeasure(String addMeasure) {this.addMeasure = checkSet(addMeasure);}
	public String getReduceMeasure() {return checkGet(reduceMeasure);}
	public void setReduceMeasure(String reduceMeasure) {this.reduceMeasure = checkSet(reduceMeasure);}
	public String getAddBookValue() {return checkGet(addBookValue);}
	public void setAddBookValue(String addBookValue) {this.addBookValue = checkSet(addBookValue);}
	public String getAddNetValue() {return checkGet(addNetValue);}
	public void setAddNetValue(String addNetValue) {this.addNetValue = checkSet(addNetValue);}
	public String getReduceBookValue() {return checkGet(reduceBookValue);}
	public void setReduceBookValue(String reduceBookValue) {this.reduceBookValue = checkSet(reduceBookValue);}
	public String getReduceAccumDepr() {return checkGet(reduceAccumDepr);}
	public void setReduceAccumDepr(String reduceAccumDepr) {this.reduceAccumDepr = checkSet(reduceAccumDepr);}
	public String getReduceNetValue() {return checkGet(reduceNetValue);}
	public void setReduceNetValue(String reduceNetValue) {this.reduceNetValue = checkSet(reduceNetValue);}
	public String getNewMeasure() {return checkGet(newMeasure);}
	public void setNewMeasure(String newMeasure) {this.newMeasure = checkSet(newMeasure);}
	public String getNewBookValue() {return checkGet(newBookValue);}
	public void setNewBookValue(String newBookValue) {this.newBookValue = checkSet(newBookValue);}
	public String getNewNetValue() {return checkGet(newNetValue);}
	public void setNewNetValue(String newNetValue) {this.newNetValue = checkSet(newNetValue);}
	public String getBookNotes() {return checkGet(bookNotes);}
	public void setBookNotes(String bookNotes) {this.bookNotes = checkSet(bookNotes);}
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
	public String getOldDeprMethod() {return checkGet(oldDeprMethod);}
	public void setOldDeprMethod(String oldDeprMethod) {this.oldDeprMethod = checkSet(oldDeprMethod);}
	public String getOldBuildFeeCB() {return checkGet(oldBuildFeeCB);}
	public void setOldBuildFeeCB(String oldBuildFeeCB) {this.oldBuildFeeCB = checkSet(oldBuildFeeCB);}
	public String getOldDeprUnitCB() {return checkGet(oldDeprUnitCB);}
	public void setOldDeprUnitCB(String oldDeprUnitCB) {this.oldDeprUnitCB = checkSet(oldDeprUnitCB);}
	public String getOldDeprPark() {return checkGet(oldDeprPark);}
	public void setOldDeprPark(String oldDeprPark) {this.oldDeprPark = checkSet(oldDeprPark);}
	public String getOldDeprUnit() {return checkGet(oldDeprUnit);}
	public void setOldDeprUnit(String oldDeprUnit) {this.oldDeprUnit = checkSet(oldDeprUnit);}
	public String getOldDeprUnit1() {return checkGet(oldDeprUnit1);}
	public void setOldDeprUnit1(String oldDeprUnit1) {this.oldDeprUnit1 = checkSet(oldDeprUnit1);}
	public String getOldDeprAccounts() {return checkGet(oldDeprAccounts);}
	public void setOldDeprAccounts(String oldDeprAccounts) {this.oldDeprAccounts = checkSet(oldDeprAccounts);}
	public String getOldScrapValue() {return checkGet(oldScrapValue);}
	public void setOldScrapValue(String oldScrapValue) {this.oldScrapValue = checkSet(oldScrapValue);}
	public String getOldDeprAmount() {return checkGet(oldDeprAmount);}
	public void setOldDeprAmount(String oldDeprAmount) {this.oldDeprAmount = checkSet(oldDeprAmount);}
	public String getOldAccumDepr() {return checkGet(oldAccumDepr);}
	public void setOldAccumDepr(String oldAccumDepr) {this.oldAccumDepr = checkSet(oldAccumDepr);}
	public String getOldApportionMonth() {return checkGet(oldApportionMonth);}
	public void setOldApportionMonth(String oldApportionMonth) {this.oldApportionMonth = checkSet(oldApportionMonth);}
	public String getOldMonthDepr() {return checkGet(oldMonthDepr);}
	public void setOldMonthDepr(String oldMonthDepr) {this.oldMonthDepr = checkSet(oldMonthDepr);}
	public String getNewDeprMethod() {return checkGet(newDeprMethod);}
	public void setNewDeprMethod(String newDeprMethod) {this.newDeprMethod = checkSet(newDeprMethod);}
	public String getNewBuildFeeCB() {return checkGet(newBuildFeeCB);}
	public void setNewBuildFeeCB(String newBuildFeeCB) {this.newBuildFeeCB = checkSet(newBuildFeeCB);}
	public String getNewDeprUnitCB() {return checkGet(newDeprUnitCB);}
	public void setNewDeprUnitCB(String newDeprUnitCB) {this.newDeprUnitCB = checkSet(newDeprUnitCB);}
	public String getNewDeprPark() {return checkGet(newDeprPark);}
	public void setNewDeprPark(String newDeprPark) {this.newDeprPark = checkSet(newDeprPark);}
	public String getNewDeprUnit() {return checkGet(newDeprUnit);}
	public void setNewDeprUnit(String newDeprUnit) {this.newDeprUnit = checkSet(newDeprUnit);}
	public String getNewDeprUnit1() {return checkGet(newDeprUnit1);}
	public void setNewDeprUnit1(String newDeprUnit1) {this.newDeprUnit1 = checkSet(newDeprUnit1);}
	public String getNewDeprAccounts() {return checkGet(newDeprAccounts);}
	public void setNewDeprAccounts(String newDeprAccounts) {this.newDeprAccounts = checkSet(newDeprAccounts);}
	public String getNewScrapValue() {return checkGet(newScrapValue);}
	public void setNewScrapValue(String newScrapValue) {this.newScrapValue = checkSet(newScrapValue);}
	public String getNewDeprAmount() {return checkGet(newDeprAmount);}
	public void setNewDeprAmount(String newDeprAmount) {this.newDeprAmount = checkSet(newDeprAmount);}
	public String getNewAccumDepr() {return checkGet(newAccumDepr);}
	public void setNewAccumDepr(String newAccumDepr) {this.newAccumDepr = checkSet(newAccumDepr);}
	public String getNewApportionMonth() {return checkGet(newApportionMonth);}
	public void setNewApportionMonth(String newApportionMonth) {this.newApportionMonth = checkSet(newApportionMonth);}
	public String getNewMonthDepr() {return checkGet(newMonthDepr);}
	public void setNewMonthDepr(String newMonthDepr) {this.newMonthDepr = checkSet(newMonthDepr);}
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

	private String reVerify;
	public String getReVerify() {return reVerify;}
	public void setReVerify(String reVerify) {this.reVerify = reVerify;}
	
	private String adjustDateTemp;
	public String getAdjustDateTemp() {return checkGet(adjustDateTemp);}
	public void setAdjustDateTemp(String adjustDateTemp) {this.adjustDateTemp = checkSet(adjustDateTemp);}
	

	private String summonsDate;
	public String getSummonsDate() {return checkGet(summonsDate);}
	public void setSummonsDate(String summonsDate) {this.summonsDate = checkSet(summonsDate);}
	
	private String bookvalueFlag;
	public String getBookvalueFlag() { return checkGet(bookvalueFlag); }
	public void setBookvalueFlag(String bookvalueFlag) { this.bookvalueFlag = checkSet(bookvalueFlag); }
	
}