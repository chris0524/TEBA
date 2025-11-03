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

import TDlib_Simple.com.src.DBServerTools;
import util.Common;
import util.Database;
import util.Datetime;
import util.MaxClosingYM;

public class UNTCH004F01 extends UNTCH004Q{

	public boolean checkIfCanPrint(){
		boolean result = true;

		if("1".equals(this.getRoleid())){
			String sql = "select printreduceproof from SYSCA_ORGARGU" +
							" where 1=1" +
							" and enterorg = " + Common.sqlChar(this.getEnterOrg());
			DBServerTools dbt = new DBServerTools();
			dbt._setDatabase(new Database());

			String canPrint = dbt._execSQL_asString(sql);
			if("Y".equals(canPrint)){
			}else{
				result = false;
			}
		}
		return result;
	}
	
	public boolean checkUpdateError(){
		boolean result = true;
		
		if (result) {
			if(checkClosingYMfromUNTAC_CLOSINGPT(getReduceDate(),getEnterOrg(),getDifferenceKind())){
				setErrorMsg("入帳日期需為最大月結年月＋１");
				result = false;
			}
		}
		
		if (result) {
			if (checkClosingYMfromUNTAC_CLOSINGPT(this.getPreenterdate(), this.getEnterOrg(), this.getDifferenceKind())) {
				setErrorMsg("預計移撥入帳日期需為最大月結年月＋１");
				result = false;
			}
			
		}
		
		return result;
	}
	
	/**
	 * 檢查是否為合併分割案
	 * @return
	 */
	public boolean isMerge() {
		boolean chkResult = true;
		Database db = null;
		ResultSet rs = null;
		try {
			db = new Database();
			rs = db.querySQL_NoChange("select count(1) as cnt  from UNTLA_MERGEDIVISION where  enterorg = " + Common.sqlChar(this.getEnterOrg()) + " and divisionreduce = " + Common.sqlChar(this.getCaseNo()));
			
			if (rs.next()) {
				int chk = rs.getInt("cnt");
				if (chk == 0) {
					chkResult = false;
				}
			} else {
				chkResult = false;
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (rs != null) {
				try {
					rs.close();
					rs = null;
				} catch (Exception e) {
					// ignore
				}
			}
		}
		return chkResult;
	}
	
	public boolean checReVerifyError(){
		boolean result = true;
		if (!"".equals(this.getOldpreenterdate())) {
			setErrorMsg("減損單於移撥日期當月月結後已自動入帳，若要回復，請執行月結回復");
			return false;
		} else if(checkCanNotReVerify(getReduceDateTemp(),getEnterOrg(),getDifferenceKind())){
			setErrorMsg("當月已月結不可取消入帳");
			result = false;
		} else if (checkMonthDepr3()) {
			setErrorMsg("無法取消入帳，因本月折舊已入帳");
			result = false;
		}
		return result;
	}
	/** 檢查動產圖書(503)是否有未入帳之資料存在於其他單據(增減值、減損)*/
	public boolean check503ExistOtherProof() {
		boolean result = true;
		String sql = "with x as ("
				+ " select enterorg,caseno,ownership,differencekind,propertyno,serialno from UNTMP_REDUCEDETAIL"
				+ " where enterorg = " + Common.sqlChar(getEnterOrg())
				+ " and caseno = " + Common.sqlChar(getCaseNo())
				+ " and propertyno like '503%' "
				+ " )"
				+ " select COUNT(1) from UNTMP_REDUCEDETAIL d,x"
				+ " where d.enterorg = x.enterorg"
				+ " and d.ownership = x.ownership"
				+ " and d.differencekind = x.differencekind"
				+ " and d.propertyno = x.propertyno"
				+ " and d.serialno = x.serialno"
				+ " and d.caseno != x.caseno"
				+ " and d.verify = 'N' ";
		
		String sql2 = "with x as ("
				+ " select enterorg,caseno,ownership,differencekind,propertyno,serialno from UNTMP_REDUCEDETAIL"
				+ " where enterorg = " + Common.sqlChar(getEnterOrg())
				+ " and caseno = " + Common.sqlChar(getCaseNo())
				+ " and propertyno like '503%' "
				+ " )"
				+ " select COUNT(1) from UNTMP_ADJUSTDETAIL d,x"
				+ " where d.enterorg = x.enterorg"
				+ " and d.ownership = x.ownership"
				+ " and d.differencekind = x.differencekind"
				+ " and d.propertyno = x.propertyno"
				+ " and d.serialno = x.serialno"
				+ " and d.verify = 'N' ";
		
		if(!"0".equals(queryDatafromDatabase(sql))){
			result = false;
			setErrorMsg("減損作業已有未入帳資料，無法回復入帳");
		}else if(!"0".equals(queryDatafromDatabase(sql2))) {
			result = false;
			setErrorMsg("增減值作業已有未入帳資料，無法回復入帳");
		}
		
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
				
				//取出的筆數大於0，表示「本月折舊計算後入帳前」
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
				String enterdate = Datetime.changeTaiwanYYMM(getReduceDate(), "2");
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
		if("Y".equals(getVerify()) &&  getReduceDateTemp().length()==7){
			// 取消入帳前，若已有當月折舊資料，加判斷是否已入帳
			String reduceYM = Datetime.getYYYYMMDDFromRocDate(getReduceDateTemp()).substring(0, 6);
			
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
	
	/** 非最新資料 return true **/
	public boolean checkNewData(){
		boolean result = false;		
		String sql = "select SUM(cnt) from (" +				
				  // 建物
				  " select COUNT(1) as cnt" +
				  " from UNTBU_REDUCEDETAIL d" +
				  " left join UNTBU_BUILDING a" +
				  " on d.enterorg =a.enterorg  and d.ownership=a.ownership  and d.differencekind= a.differencekind  and d.propertyno=a.propertyno  and d.serialno=a.serialno" +
				  " where 1=1" +
				  " and d.enterorg = " + Common.sqlChar(getEnterOrg()) +
				  " and d.caseno = " +  Common.sqlChar(getCaseNo()) +
				  " and  ( d.netvalue != a.netvalue or d.oldaccumdepr != a.accumdepr )" +
				  " union all" +
				  // 土改
				  " select COUNT(1) as cnt" +
				  " from UNTRF_REDUCEDETAIL d" +
				  " left join UNTRF_ATTACHMENT a" +
				  " on d.enterorg =a.enterorg  and d.ownership=a.ownership  and d.differencekind= a.differencekind  and d.propertyno=a.propertyno  and d.serialno=a.serialno" +
				  " where 1=1" +
				  " and d.enterorg = " + Common.sqlChar(getEnterOrg()) +
				  " and d.caseno = " +  Common.sqlChar(getCaseNo()) +
				  " and  ( d.netvalue != a.netvalue or d.oldaccumdepr != a.accumdepr )" +
				  " union all" +
				  // 動產
				  " select COUNT(1) as cnt" +
				  " from UNTMP_REDUCEDETAIL d" +
				  " left join UNTMP_MOVABLEDETAIL a" +
				  " on d.enterorg =a.enterorg  and d.ownership=a.ownership  and d.differencekind= a.differencekind  and d.propertyno=a.propertyno  and d.serialno=a.serialno" +
				  " where 1=1" +
				  " and d.enterorg = " + Common.sqlChar(getEnterOrg()) +
				  " and d.caseno = " +  Common.sqlChar(getCaseNo()) +
				  " and  ( d.oldnetvalue != a.netvalue or d.oldaccumdepr != a.accumdepr )" +
				  " union all" +
				  // 權利
				  " select COUNT(1) as cnt" +
				  " from UNTRT_REDUCEPROOF d" +
				  " left join UNTRT_ADDPROOF a" +
				  " on d.enterorg =a.enterorg  and d.ownership=a.ownership  and d.differencekind= a.differencekind  and d.propertyno=a.propertyno  and d.serialno=a.serialno" +
				  " where 1=1" +
				  " and d.enterorg = " + Common.sqlChar(getEnterOrg()) +
				  " and d.caseno = " +  Common.sqlChar(getCaseNo()) +
				  " and  ( d.netvalue != a.netvalue or d.oldaccumdepr != a.accumdepr )" +
				  " ) as base";	
		
		try {
			Object cnt = this.queryObject(sql);
			if (Common.getInt(cnt) != 0) {
				result = true;
			}
		} catch (Exception e) {
			result = true;
			e.printStackTrace();
		}
		
		return result;
	}
	
	public boolean checkNewDataForVerify(){
		boolean result = true;
		if("Y".equals(getVerify()) && checkNewData()){
			result = false;
			setErrorMsg("減損明細資料非最新帳面淨值，請先執行[帶入最新資料]");
		}
		return result;
	}
	
	/**
	 * 主檔減去減損單adjust欄位若為負值則不允許入帳，因減損單僅有動產才會做增減（503財編關係），故僅檢核動產
	 * @return
	 */
	public boolean checkDetailCanVerify(String verifyYN){
		boolean result = false;
		Database db = new Database();
		String checkSQL = "";
		String condition = "";
		
		//入帳做減，恢復入帳做增
		if(verifyYN.equals("Y")) {
			condition = " - ";
		}else {
			condition = " + ";
		}
		
		try {
			checkSQL = "	SELECT COUNT(1) AS cnt " +
					"	FROM UNTMP_REDUCEDETAIL a " +
					"	INNER JOIN UNTMP_MOVABLEDETAIL b " +
					"   ON a.enterorg = b.enterorg " +
					"	AND a.ownership = b.ownership " +
					"	AND a.differencekind = b.differencekind " +
					"	AND a.propertyno = b.propertyno " +
					"	AND a.serialno = b.serialno " +
					"	WHERE 1 = 1 " +
					"		AND ( " +
					"			b.bookvalue " + condition + " a.adjustbookvalue < 0 " +
					"			OR b.netvalue " + condition + " a.adjustnetvalue < 0 " +
					"			OR b.bookamount " + condition + " a.adjustbookamount < 0 " +
					"			) " +
					"		AND a.enterorg = " + Common.sqlChar(this.getEnterOrg()) +
					"		AND a.caseno = " + Common.sqlChar(this.getCaseNo());	
			
			if(db.getLookupField(checkSQL).equals("0")) {
				result = true;
			}else {
				setErrorMsg("執行失敗，減損單明細不可為負數金額。");
			}
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			db.closeAll();
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
			checkSQL = "SELECT verify FROM UNTLA_REDUCEPROOF WHERE enterOrg = " + Common.sqlChar(this.getEnterOrg()) + 
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
	
	public void updateNewData() throws Exception{
		try{
			Database db = new Database();			
			// 建物
			String sqlbu = "update d" +	
					  " set d.netvalue=a.netvalue, d.oldaccumdepr=a.accumdepr" +
					  " from UNTBU_REDUCEDETAIL d, UNTBU_BUILDING a" +
					  " where 1=1" +
					  " and d.enterorg =a.enterorg  and d.ownership=a.ownership  and d.differencekind= a.differencekind  and d.propertyno=a.propertyno  and d.serialno=a.serialno" +
					  " and d.enterorg = " + Common.sqlChar(getEnterOrg()) +
					  " and d.caseno = " +  Common.sqlChar(getCaseNo()) +
					  " and  ( d.netvalue != a.netvalue or d.oldaccumdepr != a.accumdepr )" +
					  " ;";
		    // 土改
			String sqlrf = " update d" +
					  " set d.netvalue=a.netvalue, d.oldaccumdepr=a.accumdepr" +
					  " from UNTRF_REDUCEDETAIL d, UNTRF_ATTACHMENT a" +
					  " where 1=1" +
					  " and d.enterorg =a.enterorg  and d.ownership=a.ownership  and d.differencekind= a.differencekind  and d.propertyno=a.propertyno  and d.serialno=a.serialno" +
					  " and d.enterorg = " + Common.sqlChar(getEnterOrg()) +
					  " and d.caseno = " +  Common.sqlChar(getCaseNo()) +
					  " and  ( d.netvalue != a.netvalue or d.oldaccumdepr != a.accumdepr )" +
					  " ;";
			// 動產
			String sqlmp = " update d" +
					  " set d.oldbookunit=a.bookvalue, d.oldbookvalue=a.bookvalue" +
					  " ,d.oldnetunit=a.netvalue, d.oldnetvalue=a.netvalue" +
					  " ,d.oldaccumdepr=a.accumdepr" +
					  " ,d.adjustbookunit=a.bookvalue, d.adjustbookvalue=a.bookvalue" +
					  " ,d.adjustnetunit=a.netvalue, d.adjustnetvalue=a.netvalue" +
					  " ,d.adjustaccumdepr=a.accumdepr" +
					  " from UNTMP_REDUCEDETAIL d, UNTMP_MOVABLEDETAIL a" +
					  " where 1=1" +
					  " and d.enterorg=a.enterorg  and d.ownership=a.ownership  and d.differencekind=a.differencekind  and d.propertyno=a.propertyno  and d.serialno=a.serialno" +
					  " and d.enterorg = " + Common.sqlChar(getEnterOrg()) +
					  " and d.caseno = " +  Common.sqlChar(getCaseNo()) +
					  " and  ( d.oldnetvalue != a.netvalue or d.oldaccumdepr != a.accumdepr )" +
					  " ;";
		    // 權利
			String sqlrt = " update d" +
					  " set d.netvalue=a.netvalue, d.oldaccumdepr=a.accumdepr" +
					  " from UNTRT_REDUCEPROOF d, UNTRT_ADDPROOF a" +
					  " where 1=1" +
					  " and d.enterorg =a.enterorg  and d.ownership=a.ownership  and d.differencekind= a.differencekind  and d.propertyno=a.propertyno  and d.serialno=a.serialno" +
					  " and d.enterorg = " + Common.sqlChar(getEnterOrg()) +
					  " and d.caseno = " +  Common.sqlChar(getCaseNo()) +
					  " and  ( d.netvalue != a.netvalue or d.oldaccumdepr != a.accumdepr )" +
					  " ;";	
			
			// 問題單1628: 當土改、建物、動產、權利減損單執行[帶入最新資料]時，請更新減損單明細中的已使用年月(useyear年usemonth月)
			String sqluse = " update UNTBU_REDUCEDETAIL set useyear=DATEDIFF(M,buydate,getdate())/12, usemonth=DATEDIFF(M,buydate,getdate())%12"
						+ " where enterorg=" + Common.sqlChar(getEnterOrg()) + " and caseno=" +  Common.sqlChar(getCaseNo()) + " and verify='N';"
						+ " update UNTRF_REDUCEDETAIL set useyear=DATEDIFF(M,buydate,getdate())/12, usemonth=DATEDIFF(M,buydate,getdate())%12"
						+ " where enterorg=" + Common.sqlChar(getEnterOrg()) + " and caseno=" +  Common.sqlChar(getCaseNo()) + " and verify='N';"
						+ " update UNTMP_REDUCEDETAIL set useyear=DATEDIFF(M,buydate,getdate())/12, usemonth=DATEDIFF(M,buydate,getdate())%12"
						+ " where enterorg=" + Common.sqlChar(getEnterOrg()) + " and caseno=" +  Common.sqlChar(getCaseNo()) + " and verify='N';"
						+ " update UNTRT_REDUCEPROOF set useyear=DATEDIFF(M,buydate,getdate())/12, usemonth=DATEDIFF(M,buydate,getdate())%12"
						+ " where enterorg=" + Common.sqlChar(getEnterOrg()) + " and caseno=" +  Common.sqlChar(getCaseNo()) + " and verify='N';"
						+ "";
			
			db.excuteSQL(sqlbu + sqlrf + sqlmp + sqlrt + sqluse);
			setStateUpdateSuccess();
			setErrorMsg("帶入最新資料完成");
		}catch(Exception e){
			setStateUpdateError();
			setErrorMsg("帶入最新資料失敗!若問題持續,請洽詢系統管理者或相關承辦人員！");
		}
	}
	
	/** 針對503做檢查，檢查是否為最後一筆入帳資料 */
	public boolean check503isNewest(String type) throws Exception{
		boolean result = true;
		String reducedate = "verify".equals(type)?getReduceDate():getReduceDateTemp();
		
		String sql = "with x as ("
				+ " select enterorg,caseno,ownership,differencekind,propertyno,serialno from UNTMP_REDUCEDETAIL"
				+ " where enterorg = " + Common.sqlChar(getEnterOrg())
				+ " and caseno = " + Common.sqlChar(getCaseNo())
				+ " and propertyno like '503%' "
				+ " )"
				+ " select COUNT(1) from UNTMP_REDUCEDETAIL d,x"
				+ " where d.enterorg = x.enterorg"
				+ " and d.ownership = x.ownership"
				+ " and d.differencekind = x.differencekind"
				+ " and d.propertyno = x.propertyno"
				+ " and d.serialno = x.serialno"
				+ " and d.caseno != x.caseno"
				+ " and d.reducedate >= " + Common.sqlChar(new UNTCH_Tools()._transToCE_Year(reducedate));
		
		if(!"0".equals(queryDatafromDatabase(sql))){
			result = false;
			setErrorMsg("並非最後一筆入帳資料，已有財產之減損入帳日期大於此單");
		}
		
		return result;
	}
	
	protected List _getBeans_asList(){
		List list = new ArrayList();
		
		list.add(new unt.la.UNTLA012F());
		list.add(new unt.bu.UNTBU015F());
		list.add(new unt.rf.UNTRF008F());
		list.add(new unt.mp.UNTMP014F());
		
		return list;
	}
	
	@Override
	protected List _getBeansExtend_asList(){
		List list = new ArrayList();
		
		list.add(new UNTCH004F02_RT(this.getClass().getSimpleName()));		
		list.add(new UNTCH004F02_VP());
		
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
								" select count(*) as count from UNTLA_REDUCEDETAIL" + condition +
								" union" +
								" select count(*) as count from UNTBU_REDUCEDETAIL" + condition +
								" union" +
								" select count(*) as count from UNTRF_REDUCEDETAIL" + condition +
								" union" +
								" select count(*) as count from UNTMP_REDUCEDETAIL" + condition +
								" union" +
								" select count(*) as count from UNTVP_REDUCEPROOF" + condition +
								" union" +
								" select count(*) as count from UNTRT_REDUCEPROOF" + condition +
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
	
	// 入帳後更新動產主檔
	public void _updateMOVABLE(String type){
		unt.mp.UNTMP014F mp = new unt.mp.UNTMP014F();
		mp.updateMOVABLE(this.getEnterOrg(), this.getOwnership(), this.getCaseNo(), this.getDifferenceKind(), type);
	}
	
	// 入帳後更新動產明細
	public void _updateMOVABLEDETAIL(String type){
		unt.mp.UNTMP014F mp = new unt.mp.UNTMP014F();
		mp.updateMOVABLEDETAIL(this.getEnterOrg(), this.getOwnership(), this.getCaseNo(), this.getDifferenceKind(), type);
	}
	
	public ArrayList queryAll() throws Exception{
		setOldPage();
		ArrayList objList = new ArrayList();
		Database db = null;
		
		try{
			StringBuilder sql = new StringBuilder();
			
			sql.append("select * from (")
				.append("select distinct ").append(
					" a.enterOrg, a.ownership, a.caseNo, a.differenceKind, a.proofYear, a.proofDoc, a.proofNo, a.writeDate,").append(
					" a.verify, a.reduceDate").append(
				" from UNTLA_REDUCEPROOF a").append(
					" left join UNTLA_ReduceDetail b on a.enterOrg=b.enterOrg and a.ownership=b.ownership and a.caseNo=b.caseNo").append(
					" left join UNTBU_ReduceDetail c on a.enterOrg=c.enterOrg and a.ownership=c.ownership and a.caseNo=c.caseNo").append(
					" left join UNTRF_ReduceDetail d on a.enterOrg=d.enterOrg and a.ownership=d.ownership and a.caseNo=d.caseNo").append(
					" left join UNTMP_ReduceDetail e on a.enterOrg=e.enterOrg and a.ownership=e.ownership and a.caseNo=e.caseNo").append(
					" left join UNTVP_REDUCEPROOF f on a.enterorg=f.enterorg and a.ownership=f.ownership and a.caseno=f.caseno").append(
					" left join UNTRT_REDUCEPROOF g on a.enterorg=g.enterorg and a.ownership=g.ownership and a.caseno=g.caseno").append(
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
			if (!"".equals(getQ_reduceDateS())){
				sql.append(" and a.reduceDate >= ").append(Common.sqlChar(new UNTCH_Tools()._transToCE_Year(getQ_reduceDateS())));
			}
			if (!"".equals(getQ_reduceDateE())){
				sql.append(" and a.reduceDate <= ").append(Common.sqlChar(new UNTCH_Tools()._transToCE_Year(getQ_reduceDateE())));
			}
			if (!"".equals(getQ_approveOrg())){
				sql.append(" and a.approveOrg = ").append(Common.sqlChar(getQ_approveOrg()));
			}
			if (!"".equals(getQ_verify())){
				sql.append(" and a.verify = ").append(Common.sqlChar(getQ_verify()));
			}
			if (!"".equals(getQ_engineeringNo())){
				sql.append(" and b.engineeringNo = ").append(Common.sqlChar(getQ_engineeringNo()));
			}
			if (!"".equals(getQ_caseName())){
				sql.append(" and a.caseName  like '%").append(getQ_caseName()).append("%'");
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
				sql.append(" and a.differencekind=").append(Common.sqlChar(getQ_differenceKind()));
			}
			
			if (!"".equals(getQ_propertyName1())){ 
				sql.append(" and a.propertyName1 like ").append(Common.sqlChar("%"+getQ_propertyName1()+"%"));
			}
			
			if ("1".equals(this.getRoleid())) {
				sql.append(" and (")
					 .append(" a.editid = ").append(Common.sqlChar(this.getUserID())) // 1185 增加能查到單的異動人員是自己的單
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
				rowArray[0] = rs.getString("enterOrg"); 
				rowArray[1] = rs.getString("ownership"); 
				rowArray[2] = uctls._getOwnershipName(rs.getString("ownership"));
				rowArray[3] = Common.get(rs.getString("differenceKind"));
				rowArray[4] = uctls._getDifferenceKindName(rs.getString("differenceKind"));
				rowArray[5] = rs.getString("caseNo"); 
				rowArray[6] = Common.get(rs.getString("proofYear")) + "年" +
								Common.get(rs.getString("proofDoc")) + "字第" + 
								Common.get(rs.getString("proofNo")) + "號";
				rowArray[7] = Common.get(uctls._transToROC_Year(rs.getString("writeDate"))); 
				rowArray[8] = Common.get(uctls._transToROC_Year(rs.getString("reduceDate")));
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
	
	//問題單2299 檢核入帳之減損單是否做過公務轉基金
	public boolean checkTransfer(String func) throws Exception {
		Database db = null;
		StringBuilder sql = new StringBuilder();
		String proofyear = "";
		String proofdoc = "";
		String proofno = "";
		boolean result = true;
		
		try {
			if (!"".equals(this.getAddcaseno())) {
				sql.append(" select * from UNTLA_ADDPROOF where enterorg = ").append(Common.sqlChar(this.getEnterOrg()))
				   .append(" and caseno = ").append(Common.sqlChar(this.getAddcaseno()));
				
				db = new Database();
				ResultSet rs = db.querySQL_NoChange(sql.toString());
				
				while(rs.next()) {
					proofyear = rs.getString("proofyear");
					proofdoc = rs.getString("proofdoc");
					proofno = rs.getString("proofno");
					break;
				}
				if (func == "transfer") {
					this.setErrorMsg("此減損單已執行過公務類轉作業基金，增加單單號:" + proofyear + "年" + proofdoc + "字 第" + proofno + "號" + "，若要重新執行公務轉基金，請先將此增加單刪除。");
				} else if (func == "approveRe") {
					this.setErrorMsg("此減損單已執行過公務類轉作業基金，增加單單號:" + proofyear + "年" + proofdoc + "字 第" + proofno + "號" + "，若要恢復入帳，請先將此增加單刪除。");
				}
				
				result = false;
			} else if (!"110".equals(this.getDifferenceKind())) {
				this.setErrorMsg("此減損單物品區分別非公務類，不可執行公務類轉作業基金。");
				result = false;
			}
		} catch(Exception e) {
			log._execLogError(e.getMessage());
		}
		return result;
	}
	
	//問題單2299 執行公務轉基金
	public void transfer(String table) throws Exception {
		try {
			if (checkTransfer("transfer")) {
				String newCaseno = transferAddProof(table);
				transferAddDetails(table, newCaseno);
				transferAttachment(newCaseno);
				
				this.setErrorMsg("公務轉基金成功");
			}
		} catch(Exception e) {
			log._execLogError(e.getMessage());
			this.setErrorMsg("公務轉基金失敗，請洽系統工程師。");
		}
	}
	
	//問題單2299 公務轉基金 新增增加單
	private String transferAddProof(String table) throws Exception {
		Database db = null;
		StringBuilder sql = new StringBuilder();
		String differencekind = "120";
		
		try {
			String addCaseno = this.getNewCaseNo(table);  //新單據編號
			String proofNo = MaxClosingYM.getMaxProofNo("UNTLA_ADDPROOF", this.getEnterOrg(), this.getOwnership(), Datetime.getYYY());
			String notes =  Datetime.getYYYMMDD() + "由公務類轉為作業基金";
			db = new Database();
			
			sql.append(" insert into UNTLA_ADDPROOF (enterorg, ownership, caseno, differencekind, writedate, writeunit, proofyear,")
			   .append(" proofdoc, proofno, verify, notes, editid, editdate, edittime, reducecaseno) ")
			   .append(" VALUES( ").append(Common.sqlChar(this.getEnterOrg())).append(", ")
			   .append(Common.sqlChar(this.getOwnership())).append(", ")
			   .append(Common.sqlChar(addCaseno)).append(", ")
			   .append(Common.sqlChar(differencekind)).append(", ")
			   .append(Common.sqlChar(Datetime.getYYYYMMDD())).append(", ")
			   .append(Common.sqlChar(this.getWriteUnit())).append(", ")
			   .append(Common.sqlChar(Datetime.getYYY()))
			   .append(", '財增', ")
			   .append(Common.sqlChar(proofNo))
			   .append(", 'N', ")
			   .append(Common.sqlChar(notes)).append(", ")
			   .append(Common.sqlChar(this.getUserID())).append(", ")
			   .append(Common.sqlChar(Datetime.getYYYYMMDD())).append(", ")
			   .append(Common.sqlChar(Datetime.getHHMMSS())).append(", ")
			   .append(Common.sqlChar(this.getCaseNo())).append(" ); ");
			sql.append(" insert into UNTRF_ADDPROOF (enterorg, ownership, caseno, differencekind, writedate, writeunit, proofyear,")
			   .append(" proofdoc, proofno, verify, notes, editid, editdate, edittime, reducecaseno) ")
			   .append(" VALUES( ").append(Common.sqlChar(this.getEnterOrg())).append(", ")
			   .append(Common.sqlChar(this.getOwnership())).append(", ")
			   .append(Common.sqlChar(addCaseno)).append(", ")
			   .append(Common.sqlChar(differencekind)).append(", ")
			   .append(Common.sqlChar(Datetime.getYYYYMMDD())).append(", ")
			   .append(Common.sqlChar(this.getWriteUnit())).append(", ")
			   .append(Common.sqlChar(Datetime.getYYY()))
			   .append(", '財增', ")
			   .append(Common.sqlChar(proofNo))
			   .append(", 'N', ").append(Common.sqlChar(notes)).append(", ")
			   .append(Common.sqlChar(this.getUserID())).append(", ")
			   .append(Common.sqlChar(Datetime.getYYYYMMDD())).append(", ")
			   .append(Common.sqlChar(Datetime.getHHMMSS())).append(", ")
			   .append(Common.sqlChar(this.getCaseNo())).append(" ); ");
			sql.append(" insert into UNTBU_ADDPROOF (enterorg, ownership, caseno, differencekind, writedate, writeunit, proofyear,")
			   .append(" proofdoc, proofno, verify, notes, editid, editdate, edittime, reducecaseno) ")
			   .append(" VALUES( ").append(Common.sqlChar(this.getEnterOrg())).append(", ")
			   .append(Common.sqlChar(this.getOwnership())).append(", ")
			   .append(Common.sqlChar(addCaseno)).append(", ")
			   .append(Common.sqlChar(differencekind)).append(", ")
			   .append(Common.sqlChar(Datetime.getYYYYMMDD())).append(", ")
			   .append(Common.sqlChar(this.getWriteUnit())).append(", ")
			   .append(Common.sqlChar(Datetime.getYYY()))
			   .append(", '財增', ")
			   .append(Common.sqlChar(proofNo))
			   .append(", 'N', ")
			   .append(Common.sqlChar(notes)).append(", ")
			   .append(Common.sqlChar(this.getUserID())).append(", ")
			   .append(Common.sqlChar(Datetime.getYYYYMMDD())).append(", ")
			   .append(Common.sqlChar(Datetime.getHHMMSS())).append(", ")
			   .append(Common.sqlChar(this.getCaseNo())).append(" ); ");
			sql.append(" insert into UNTMP_ADDPROOF (enterorg, ownership, caseno, differencekind, writedate, writeunit, proofyear,")
			   .append(" proofdoc, proofno, verify, notes, editid, editdate, edittime, reducecaseno) ")
			   .append(" VALUES( ").append(Common.sqlChar(this.getEnterOrg())).append(", ")
			   .append(Common.sqlChar(this.getOwnership())).append(", ")
			   .append(Common.sqlChar(addCaseno)).append(", ")
			   .append(Common.sqlChar(differencekind)).append(", ")
			   .append(Common.sqlChar(Datetime.getYYYYMMDD())).append(", ")
			   .append(Common.sqlChar(this.getWriteUnit())).append(", ")
			   .append(Common.sqlChar(Datetime.getYYY()))
			   .append(", '財增', ")
			   .append(Common.sqlChar(proofNo))
			   .append(", 'N', ")
			   .append(Common.sqlChar(notes)).append(", ")
			   .append(Common.sqlChar(this.getUserID())).append(", ")
			   .append(Common.sqlChar(Datetime.getYYYYMMDD())).append(", ")
			   .append(Common.sqlChar(Datetime.getHHMMSS())).append(", ")
			   .append(Common.sqlChar(this.getCaseNo())).append(" ); ");
			sql.append(" update UNTLA_REDUCEPROOF set addcaseno = ").append(Common.sqlChar(addCaseno))
			   .append(" , editid = ").append(Common.sqlChar(this.getUserID()))
			   .append(" , editdate = ").append(Common.sqlChar(Datetime.getYYYYMMDD()))
			   .append(" , edittime = ").append(Common.sqlChar(Datetime.getHHMMSS()))
			   .append(" where enterorg = ").append(Common.sqlChar(this.getEnterOrg()))
			   .append(" and caseno = ").append(Common.sqlChar(this.getCaseNo())).append(" ; ");
			sql.append(" update UNTRF_REDUCEPROOF set addcaseno = ").append(Common.sqlChar(addCaseno))
			   .append(" , editid = ").append(Common.sqlChar(this.getUserID()))
			   .append(" , editdate = ").append(Common.sqlChar(Datetime.getYYYYMMDD()))
			   .append(" , edittime = ").append(Common.sqlChar(Datetime.getHHMMSS()))
			   .append(" where enterorg = ").append(Common.sqlChar(this.getEnterOrg()))
			   .append(" and caseno = ").append(Common.sqlChar(this.getCaseNo())).append(" ; ");
			sql.append(" update UNTBU_REDUCEPROOF set addcaseno = ").append(Common.sqlChar(addCaseno))
			   .append(" , editid = ").append(Common.sqlChar(this.getUserID()))
			   .append(" , editdate = ").append(Common.sqlChar(Datetime.getYYYYMMDD()))
			   .append(" , edittime = ").append(Common.sqlChar(Datetime.getHHMMSS()))
			   .append(" where enterorg = ").append(Common.sqlChar(this.getEnterOrg()))
			   .append(" and caseno = ").append(Common.sqlChar(this.getCaseNo())).append(" ; ");
			sql.append(" update UNTMP_REDUCEPROOF set addcaseno = ").append(Common.sqlChar(addCaseno))
			   .append(", editid = ").append(Common.sqlChar(this.getUserID()))
			   .append(" , editdate = ").append(Common.sqlChar(Datetime.getYYYYMMDD()))
			   .append(", edittime = ").append(Common.sqlChar(Datetime.getHHMMSS()))
			   .append(" where enterorg = ").append(Common.sqlChar(this.getEnterOrg()))
			   .append(" and caseno = ").append(Common.sqlChar(this.getCaseNo())).append(" ; ");
			   
			db.exeSQL_NoChange(sql.toString());
			
			return addCaseno;
		} catch(Exception e) {
			log._execLogError(e.getMessage());
			return null;
		} finally {
			db.closeAll();
		}		
	}
	
	private void transferAddDetails(String table, String addCaseno) {
		//問題單2299 公務轉基金 新增明細
		Database db = null;
		StringBuilder sql = new StringBuilder();
		String differencekind = "120";
		String sourceKind = "32"; //財產來源預設 32 作價撥充
		
		try {
			String reduceCaseNo = this.getCaseNo();   //減損單據編號
			String addProofNo = MaxClosingYM.getMaxProofNo("UNTVP_ADDPROOF", this.getEnterOrg(), this.getOwnership(), Datetime.getYYY());
			String notes =  Datetime.getYYYMMDD() + "由公務類轉為作業基金";
			db = new Database();
			
			//土地
			sql.append(" DECLARE rs CURSOR                                                                                               ")
				.append(" FOR SELECT a.enterorg,                                                                                          ")
				.append("            a.differencekind,                                                                                    ")
				.append("            a.ownership,                                                                                         ")
				.append("            a.propertyno,                                                                                        ")
				.append("            a.serialno                                                                                           ")
				.append("     FROM UNTLA_LAND a                                                                                           ")
				.append("          JOIN UNTLA_REDUCEDETAIL b ON a.enterorg = b.enterorg                                                   ")
				.append("                                       AND a.differencekind = b.differencekind                                   ")
				.append("                                       AND a.ownership = b.ownership                                             ")
				.append("                                       AND a.propertyno = b.propertyno                                           ")
				.append("                                       AND a.serialno = b.serialno                                               ")
				.append("     WHERE a.enterorg = ").append(Common.sqlChar(this.getEnterOrg()))
				.append("           AND b.caseno = ").append(Common.sqlChar(reduceCaseNo))
				.append("     ORDER BY a.enterorg,                                                                                        ")
				.append("              a.ownership,                                                                                       ")
				.append("              a.differencekind,                                                                                  ")
				.append("              a.propertyno,                                                                                      ")
				.append("              a.serialno;                                                                                        ")
				.append(" DECLARE @enterorg NVARCHAR(11);                                                                                 ")
				.append(" DECLARE @differentkind NVARCHAR(3);                                                                             ")
				.append(" DECLARE @ownership NVARCHAR(1);                                                                                 ")
				.append(" DECLARE @propertyno NVARCHAR(11);                                                                               ")
				.append(" DECLARE @serialno NVARCHAR(7);                                                                                  ")
				.append(" DECLARE @caseserialno NVARCHAR(10)= 1;                                                                          ")
				.append(" OPEN rs;                                                                                                        ")
				.append(" FETCH NEXT FROM rs INTO @enterorg, @differentkind, @ownership, @propertyno, @serialno;                          ")
				.append(" WHILE @@Fetch_Status = 0                                                                                        ")
				.append("     BEGIN                                                                                                       ")
				.append("         INSERT INTO UNTLA_LAND                                                                                  ")
				.append("         (enterorg,                                                                                              ")
				.append("          ownership,                                                                                             ")
				.append("          caseno,                                                                                                ")
				.append("          differencekind,                                                                                        ")
				.append("          caseserialno,                                                                                          ")
				.append("          propertyno,                                                                                            ")
				.append("          serialno,                                                                                              ")
				.append("          propertyname1,                                                                                         ")
				.append("          signno,                                                                                                ")
				.append("          doorplate,                                                                                             ")
				.append("          datastate,                                                                                             ")
				.append("          verify,                                                                                                ")
				.append("          propertykind,                                                                                          ")
				.append("          fundtype,                                                                                              ")
				.append("          valuable,                                                                                              ")
				.append("          taxcredit,                                                                                             ")
				.append("          originalarea,                                                                                          ")
				.append("          originalholdnume,                                                                                      ")
				.append("          originalholddeno,                                                                                      ")
				.append("          originalholdarea,                                                                                      ")
				.append("          area,                                                                                                  ")
				.append("          holdnume,                                                                                              ")
				.append("          holddeno,                                                                                              ")
				.append("          holdarea,                                                                                              ")
				.append("          originalbasis,                                                                                         ")
				.append("          originaldate,                                                                                          ")
				.append("          originalunit,                                                                                          ")
				.append("          originalbv,                                                                                            ")
				.append("          originalnote,                                                                                          ")
				.append("          accountingtitle,                                                                                       ")
				.append("          bookunit,                                                                                              ")
				.append("          bookvalue,                                                                                             ")
				.append("          netunit,                                                                                               ")
				.append("          netvalue,                                                                                              ")
				.append("          fundssource,                                                                                           ")
				.append("          fundssource1,                                                                                          ")
				.append("          useseparate,                                                                                           ")
				.append("          usekind,                                                                                               ")
				.append("          oriuseseparate,                                                                                        ")
				.append("          oriusekind,                                                                                            ")
				.append("          ownershipdate,                                                                                         ")
				.append("          ownershipcause,                                                                                        ")
				.append("          nonproof,                                                                                              ")
				.append("          proofdoc,                                                                                              ")
				.append("          ownershipnote,                                                                                         ")
				.append("          field,                                                                                                 ")
				.append("          landrule,                                                                                              ")
				.append("          buydate,                                                                                               ")
				.append("          sourcekind,                                                                                            ")
				.append("          sourcedate,                                                                                            ")
				.append("          sourcedoc,                                                                                             ")
				.append("          oldowner,                                                                                              ")
				.append("          manageorg,                                                                                             ")
				.append("          usestate,                                                                                              ")
				.append("          appraisedate,                                                                                          ")
				.append("          notes1,                                                                                                ")
				.append("          usestate1,                                                                                             ")
				.append("          oldpropertyno,                                                                                         ")
				.append("          oldserialno,                                                                                           ")
				.append("          originalkeepunit,                                                                                      ")
				.append("          originalkeeper,                                                                                        ")
				.append("          originaluseunit,                                                                                       ")
				.append("          originaluser,                                                                                          ")
				.append("          originalusernote,                                                                                      ")
				.append("          originalplace1,                                                                                        ")
				.append("          originalplace,                                                                                         ")
				.append("          keepunit,                                                                                              ")
				.append("          keeper,                                                                                                ")
				.append("          useunit,                                                                                               ")
				.append("          userno,                                                                                                ")
				.append("          usernote,                                                                                              ")
				.append("          place1,                                                                                                ")
				.append("          place,                                                                                                 ")
				.append("          notes,                                                                                                 ")
				.append("          editid,                                                                                                ")
				.append("          editdate,                                                                                              ")
				.append("          edittime,                                                                                              ")
				.append("          picture,                                                                                               ")
				.append("          oldlotno                                                                                               ")
				.append("         )                                                                                                       ")
				.append("                SELECT a.enterorg,                                                                               ")
				.append("                       a.ownership,                                                                              ")
				.append(Common.sqlChar(addCaseno)).append(",")
				.append(Common.sqlChar(differencekind)).append(",")
				.append("                       @caseserialno AS caseserialno,                                                            ")
				.append("                       a.propertyno,                                                                             ")
				.append("                (                                                                                                ")
				.append("                    SELECT CASE                                                                                  ")
				.append("                               WHEN MAX(d.serialno) IS NULL                                                      ")
				.append("                               THEN RIGHT(REPLICATE('0', 7) + CAST('1' AS NVARCHAR), 7)                          ")
				.append("                               ELSE RIGHT(REPLICATE('0', 7) + CAST(MAX(d.serialno) + 1 AS NVARCHAR), 7)          ")
				.append("                           END                                                                                   ")
				.append("                    FROM UNTLA_LAND d                                                                            ")
				.append("                    WHERE a.enterorg = d.enterorg                                                                ")
				.append("                          AND a.ownership = d.ownership                                                          ")
				.append("                          AND d.differencekind = ").append(Common.sqlChar(differencekind))
				.append("                          AND a.propertyno = d.propertyno                                                        ")
				.append("                ) AS serialno,                                                                                   ")
				.append("                       a.propertyname1,                                                                          ")
				.append("                       a.signno,                                                                                 ")
				.append("                       a.doorplate,                                                                              ")
				.append("                       1,                                                                                        ")
				.append("                       'N',                                                                                      ")
				.append("                       a.propertykind,                                                                           ")
				.append("                       a.fundtype,                                                                               ")
				.append("                       a.valuable,                                                                               ")
				.append("                       a.taxcredit,                                                                              ")
				.append("                       a.originalarea,                                                                           ")
				.append("                       a.originalholdnume,                                                                       ")
				.append("                       a.originalholddeno,                                                                       ")
				.append("                       a.originalholdarea,                                                                       ")
				.append("                       b.area,                                                                                   ")
				.append("                       b.holdnume,                                                                               ")
				.append("                       b.holddeno,                                                                               ")
				.append("                       b.holdarea,                                                                               ")
				.append("                       a.originalbasis,                                                                          ")
				.append("                       a.originaldate,                                                                           ")
				.append("                       a.originalunit,                                                                           ")
				.append("                       a.originalbv,                                                                             ")
				.append("                       a.originalnote,                                                                           ")
				.append("                       a.accountingtitle,                                                                        ")
				.append("                       b.bookunit,                                                                               ")
				.append("                       b.bookvalue,                                                                              ")
				.append("                       b.netunit,                                                                                ")
				.append("                       b.netvalue,                                                                               ")
				.append("                       a.fundssource,                                                                            ")
				.append("                       a.fundssource1,                                                                           ")
				.append("                       a.useseparate,                                                                            ")
				.append("                       a.usekind,                                                                                ")
				.append("                       a.oriuseseparate,                                                                         ")
				.append("                       a.oriusekind,                                                                             ")
				.append("                       a.ownershipdate,                                                                          ")
				.append("                       a.ownershipcause,                                                                         ")
				.append("                       a.nonproof,                                                                               ")
				.append("                       a.proofdoc,                                                                               ")
				.append("                       a.ownershipnote,                                                                          ")
				.append("                       a.field,                                                                                  ")
				.append("                       a.landrule,                                                                               ")
				.append("                       a.buydate,                                                                                ")
				.append(Common.sqlChar(sourceKind)).append(", ")
				.append("                       b.reducedate,                                                                             ")
				.append("                       a.sourcedoc,                                                                              ")
				.append("                       a.oldowner,                                                                               ")
				.append("                       a.manageorg,                                                                              ")
				.append("                       a.usestate,                                                                               ")
				.append("                       a.appraisedate,                                                                           ")
				.append("                       a.notes1,                                                                                 ")
				.append("                       a.usestate1,                                                                              ")
				.append("                       a.propertyno,                                                                             ")
				.append("                       a.serialno,                                                                               ")
				.append("                       a.keepunit,                                                                       ")
				.append("                       a.keeper,                                                                         ")
				.append("                       a.useunit,                                                                        ")
				.append("                       a.userno,                                                                           ")
				.append("                       a.usernote,                                                                       ")
				.append("                       a.place1,                                                                         ")
				.append("                       a.place,                                                                          ")
				.append("                       a.keepunit,                                                                               ")
				.append("                       a.keeper,                                                                                 ")
				.append("                       a.useunit,                                                                                ")
				.append("                       a.userno,                                                                                 ")
				.append("                       a.usernote,                                                                               ")
				.append("                       a.place1,                                                                                 ")
				.append("                       a.place,                                                                                  ")
				.append("                       a.notes,                                                                                  ")
				.append(Common.sqlChar(this.getUserID())).append(",")
				.append(Common.sqlChar(Datetime.getYYYYMMDD())).append(",")
				.append(Common.sqlChar(Datetime.getHHMMSS())).append(",")
				.append("                       a.picture,                                                                                ")
				.append("                       a.oldlotno                                                                                ")
				.append("                FROM UNTLA_LAND a                                                                                ")
				.append("                     JOIN UNTLA_REDUCEDETAIL b ON a.enterorg = b.enterorg                                        ")
				.append("                                                  AND a.ownership = b.ownership                                  ")
				.append("                                                  AND a.differencekind = b.differencekind                        ")
				.append("                                                  AND a.propertyno = b.propertyno                                ")
				.append("                                                  AND a.serialno = b.serialno                                    ")
				.append("                WHERE b.caseno = ").append(Common.sqlChar(reduceCaseNo))
				.append("                      AND a.enterorg = @enterorg                                                                 ")
				.append("                      AND a.differencekind = @differentkind                                                      ")
				.append("                      AND a.ownership = @ownership                                                               ")
				.append("                      AND a.propertyno = @propertyno                                                             ")
				.append("                      AND a.serialno = @serialno;                                                                ")
				.append("         SELECT @caseserialno = @caseserialno + 1;                                                               ")
				.append("         FETCH NEXT FROM rs INTO @enterorg, @differentkind, @ownership, @propertyno, @serialno;                  ")
				.append("     END;                                                                                                        ")
				.append(" CLOSE rs;                                                                                     				  ")
				.append(" DEALLOCATE rs;                                                                                				  ");
			   
			//土改
			sql.append(" DECLARE rs CURSOR                                                                                         ")
				.append(" FOR SELECT a.enterorg,                                                                                    ")
				.append("            a.differencekind,                                                                              ")
				.append("            a.ownership,                                                                                   ")
				.append("            a.propertyno,                                                                                  ")
				.append("            a.serialno                                                                                     ")
				.append("     FROM UNTRF_ATTACHMENT a                                                                               ")
				.append("          JOIN UNTRF_REDUCEDETAIL b ON a.enterorg = b.enterorg                                             ")
				.append("                                       AND a.differencekind = b.differencekind                             ")
				.append("                                       AND a.ownership = b.ownership                                       ")
				.append("                                       AND a.propertyno = b.propertyno                                     ")
				.append("                                       AND a.serialno = b.serialno                                         ")
				.append("     WHERE a.enterorg = ").append(Common.sqlChar(this.getEnterOrg()))
				.append("           AND b.caseno = ").append(Common.sqlChar(reduceCaseNo))
				.append("     ORDER BY a.enterorg,                                                                                  ")
				.append("              a.ownership,                                                                                 ")
				.append("              a.differencekind,                                                                            ")
				.append("              a.propertyno,                                                                                ")
				.append("              a.serialno;                                                                                  ")
				.append(" OPEN rs;                                                                                                  ")
				.append(" FETCH NEXT FROM rs INTO @enterorg, @differentkind, @ownership, @propertyno, @serialno;                    ")
				.append(" WHILE @@Fetch_Status = 0                                                                                  ")
				.append("     BEGIN                                                                                                 ")
				.append("         INSERT INTO UNTRF_ATTACHMENT                                                                      ")
				.append("         (enterorg,                                                                                        ")
				.append("          ownership,                                                                                       ")
				.append("          caseno,                                                                                          ")
				.append("          differencekind,                                                                                  ")
				.append("          caseserialno,                                                                                    ")
				.append("          propertyno,                                                                                      ")
				.append("          serialno,                                                                                        ")
				.append("          otherlimityear,                                                                                  ")
				.append("          propertyname1,                                                                                   ")
				.append("          datastate,                                                                                       ")
				.append("          verify,                                                                                          ")
				.append("          propertykind,                                                                                    ")
				.append("          fundtype,                                                                                        ")
				.append("          valuable,                                                                                        ")
				.append("          taxcredit,                                                                                       ")
				.append("          originalmeasure,                                                                                 ")
				.append("          measure,                                                                                         ")
				.append("          originalbv,                                                                                      ")
				.append("          originalnote,                                                                                    ")
				.append("          accountingtitle,                                                                                 ")
				.append("          bookvalue,                                                                                       ")
				.append("          netvalue,                                                                                        ")
				.append("          fundssource,                                                                                     ")
				.append("          fundssource1,                                                                                    ")
				.append("          builddate,                                                                                       ")
				.append("          buydate,                                                                                         ")
				.append("          sourcekind,                                                                                      ")
				.append("          sourcedate,                                                                                      ")
				.append("          sourcedoc,                                                                                       ")
				.append("          originaldeprmethod,                                                                              ")
				.append("          originalbuildfeecb,                                                                              ")
				.append("          originaldeprunitcb,                                                                              ")
				.append("          originaldeprpark,                                                                                ")
				.append("          originaldeprunit,                                                                                ")
				.append("          originaldeprunit1,                                                                               ")
				.append("          originaldepraccounts,                                                                            ")
				.append("          originalscrapvalue,                                                                              ")
				.append("          originaldepramount,                                                                              ")
				.append("          originalaccumdepr,                                                                               ")
				.append("          originalapportionmonth,                                                                          ")
				.append("          originalmonthdepr,                                                                               ")
				.append("          originalmonthdepr1,                                                                              ")
				.append("          originalapportionendym,                                                                          ")
				.append("          deprmethod,                                                                                      ")
				.append("          buildfeecb,                                                                                      ")
				.append("          deprunitcb,                                                                                      ")
				.append("          deprpark,                                                                                        ")
				.append("          deprunit,                                                                                        ")
				.append("          deprunit1,                                                                                       ")
				.append("          depraccounts,                                                                                    ")
				.append("          scrapvalue,                                                                                      ")
				.append("          depramount,                                                                                      ")
				.append("          accumdepr,                                                                                       ")
				.append("          apportionmonth,                                                                                  ")
				.append("          monthdepr,                                                                                       ")
				.append("          monthdepr1,                                                                                      ")
				.append("          apportionendym,                                                                                  ")
				.append("          nodeprset,                                                                                       ")
				.append("          appraisedate,                                                                                    ")
				.append("          originalkeepunit,                                                                                ")
				.append("          originalkeeper,                                                                                  ")
				.append("          originaluseunit,                                                                                 ")
				.append("          originaluser,                                                                                    ")
				.append("          originalusernote,                                                                                ")
				.append("          originalplace1,                                                                                  ")
				.append("          originalplace,                                                                                   ")
				.append("          keepunit,                                                                                        ")
				.append("          keeper,                                                                                          ")
				.append("          useunit,                                                                                         ")
				.append("          userno,                                                                                          ")
				.append("          usernote,                                                                                        ")
				.append("          place1,                                                                                          ")
				.append("          place,                                                                                           ")
				.append("          escroworivalue,                                                                                  ")
				.append("          escroworiaccumdepr,                                                                              ")
				.append("          oldpropertyno,                                                                                   ")
				.append("          oldserialno,                                                                                     ")
				.append("          notes,                                                                                           ")
				.append("          editid,                                                                                          ")
				.append("          editdate,                                                                                        ")
				.append("          edittime,                                                                                        ")
				.append("          picture,                                                                                         ")
				.append("          originallimityear,                                                                               ")
				.append("          oldlotno,                                                                                        ")
				.append("          otherpropertyunit,                                                                               ")
				.append("          othermaterial,                                                                                   ")
				.append("          originalaccumdepr1,                                                                              ")
				.append("          originalaccumdepr2                                                                               ")
				.append("         )                                                                                                 ")
				.append("                SELECT a.enterorg,                                                                         ")
				.append("                       a.ownership,                                                                        ")
				.append(Common.sqlChar(addCaseno)).append(",")
				.append(Common.sqlChar(differencekind)).append(",")
				.append("                       @caseserialno AS caseserialno,                                                      ")
				.append("                       a.propertyno,                                                                       ")
				.append("                (                                                                                          ")
				.append("                    SELECT CASE                                                                            ")
				.append("                               WHEN MAX(d.serialno) IS NULL                                                ")
				.append("                               THEN RIGHT(REPLICATE('0', 7) + CAST('1' AS NVARCHAR), 7)                    ")
				.append("                               ELSE RIGHT(REPLICATE('0', 7) + CAST(MAX(d.serialno) + 1 AS NVARCHAR), 7)    ")
				.append("                           END                                                                             ")
				.append("                    FROM UNTRF_ATTACHMENT d                                                                ")
				.append("                    WHERE a.enterorg = d.enterorg                                                          ")
				.append("                          AND a.ownership = d.ownership                                                    ")
				.append("                          AND d.differencekind = ").append(Common.sqlChar(differencekind))
				.append("                          AND a.propertyno = d.propertyno                                                  ")
				.append("                ) AS serialno,                                                                             ")
				.append("                       a.otherlimityear,                                                                   ")
				.append("                       a.propertyname1,                                                                    ")
				.append("                       1,                                                                                  ")
				.append("                       'N',                                                                                ")
				.append("                       a.propertykind,                                                                     ")
				.append("                       a.fundtype,                                                                         ")
				.append("                       a.valuable,                                                                         ")
				.append("                       a.taxcredit,                                                                        ")
				.append("                       a.originalmeasure,                                                                  ")
				.append("                       a.measure,                                                                          ")
				.append("                       a.originalbv,                                                                       ")
				.append("                       a.originalnote,                                                                     ")
				.append("                       a.accountingtitle,                                                                  ")
				.append("                       b.bookvalue,                                                                        ")
				.append("                       b.netvalue,                                                                         ")
				.append("                       a.fundssource,                                                                      ")
				.append("                       a.fundssource1,                                                                     ")
				.append("                       a.builddate,                                                                        ")
				.append("                       a.buydate,                                                                          ")
				.append(Common.sqlChar(sourceKind)).append(", ")
				.append("                       b.reducedate,                                                                             ")
				.append("                       a.sourcedoc,                                                                        ")
				.append("                       b.olddeprmethod,                                                               		")
				.append("                       b.oldbuildfeecb,                                                               		")
				.append("                       b.olddeprunitcb,                                                               		")
				.append("                       b.olddeprpark,                                                                 		")
				.append("                       b.olddeprunit,                                                                 		")
				.append("                       b.olddeprunit1,                                                                		")
				.append("                       b.olddepraccounts,                                                             		")
				.append("                       b.oldscrapvalue,                                                               		")
				.append("                       b.olddepramount,                                                               		")
				.append("                       b.oldaccumdepr,                                                                     ")
				.append("                       b.oldapportionmonth,                                                           		")
				.append("                       b.oldmonthdepr,                                                                		")
				.append("                       b.oldmonthdepr1,                                                               		")
				.append("                       b.oldapportionendym,                                                           		")
				.append("                       b.olddeprmethod,                                                                    ")
				.append("                       b.oldbuildfeecb,                                                                    ")
				.append("                       b.olddeprunitcb,                                                                    ")
				.append("                       b.olddeprpark,                                                                      ")
				.append("                       b.olddeprunit,                                                                      ")
				.append("                       b.olddeprunit1,                                                                     ")
				.append("                       b.olddepraccounts,                                                                  ")
				.append("                       b.oldscrapvalue,                                                                    ")
				.append("                       b.olddepramount,                                                                    ")
				.append("                       0 as accumdepr,                                                                     ")
				.append("                       b.oldapportionmonth,                                                                ")
				.append("                       b.oldmonthdepr,                                                                     ")
				.append("                       b.oldmonthdepr1,                                                                    ")
				.append("                       b.oldapportionendym,                                                                ")
				.append("                       a.nodeprset,                                                                        ")
				.append("                       a.appraisedate,                                                                     ")
				.append("                       a.keepunit,                                                                 ")
				.append("                       a.keeper,                                                                   ")
				.append("                       a.useunit,                                                                  ")
				.append("                       a.userno,                                                                     ")
				.append("                       a.usernote,                                                                 ")
				.append("                       a.place1,                                                                   ")
				.append("                       a.place,                                                                    ")
				.append("                       a.keepunit,                                                                         ")
				.append("                       a.keeper,                                                                           ")
				.append("                       a.useunit,                                                                          ")
				.append("                       a.userno,                                                                           ")
				.append("                       a.usernote,                                                                         ")
				.append("                       a.place1,                                                                           ")
				.append("                       a.place,                                                                            ")
				.append("                       a.escroworivalue,                                                                   ")
				.append("                       a.escroworiaccumdepr,                                                               ")
				.append("                       a.propertyno,                                                                       ")
				.append("                       a.serialno,                                                                         ")
				.append("                       a.notes,                                                                            ")
				.append(Common.sqlChar(this.getUserID())).append(",")
				.append(Common.sqlChar(Datetime.getYYYYMMDD())).append(",")
				.append(Common.sqlChar(Datetime.getHHMMSS())).append(",")
				.append("                       a.picture,                                                                          ")
				.append("                       a.originallimityear,                                                                ")
				.append("                       a.oldlotno,                                                                         ")
				.append("                       a.otherpropertyunit,                                                                ")
				.append("                       a.othermaterial,                                                                    ")
				.append("                       b.oldaccumdepr,                                                               		")
				.append("                       0                                                                					")
				.append("                FROM UNTRF_ATTACHMENT a                                                                    ")
				.append("                     JOIN UNTRF_REDUCEDETAIL b ON a.enterorg = b.enterorg                                  ")
				.append("                                                  AND a.ownership = b.ownership                            ")
				.append("                                                  AND a.differencekind = b.differencekind                  ")
				.append("                                                  AND a.propertyno = b.propertyno                          ")
				.append("                                                  AND a.serialno = b.serialno                              ")
				.append("                WHERE b.caseno = ").append(Common.sqlChar(reduceCaseNo))
				.append("                      AND a.enterorg = @enterorg                                                           ")
				.append("                      AND a.differencekind = @differentkind                                                ")
				.append("                      AND a.ownership = @ownership                                                         ")
				.append("                      AND a.propertyno = @propertyno                                                       ")
				.append("                      AND a.serialno = @serialno;                                                          ")
				.append("         SELECT @caseserialno = @caseserialno + 1;                                                         ")
				.append("         FETCH NEXT FROM rs INTO @enterorg, @differentkind, @ownership, @propertyno, @serialno;            ")
				.append("     END;                                                                                                  ")
				.append(" CLOSE rs;                                                                                                 ")
				.append(" DEALLOCATE rs;                                                                                            ");
			
			//建物
			sql.append(" DECLARE rs CURSOR                                                                                        ")
				.append(" FOR SELECT a.enterorg,                                                                                   ")
				.append("            a.differencekind,                                                                             ")
				.append("            a.ownership,                                                                                  ")
				.append("            a.propertyno,                                                                                 ")
				.append("            a.serialno                                                                                    ")
				.append("     FROM UNTBU_BUILDING a                                                                                ")
				.append("          JOIN UNTBU_REDUCEDETAIL b ON a.enterorg = b.enterorg                                            ")
				.append("                                       AND a.differencekind = b.differencekind                            ")
				.append("                                       AND a.ownership = b.ownership                                      ")
				.append("                                       AND a.propertyno = b.propertyno                                    ")
				.append("                                       AND a.serialno = b.serialno                                        ")
				.append("     WHERE a.enterorg = ").append(Common.sqlChar(this.getEnterOrg()))
				.append("           AND b.caseno = ").append(Common.sqlChar(reduceCaseNo))
				.append("     ORDER BY a.enterorg,                                                                                 ")
				.append("              a.ownership,                                                                                ")
				.append("              a.differencekind,                                                                           ")
				.append("              a.propertyno,                                                                               ")
				.append("              a.serialno;                                                                                 ")
				.append(" OPEN rs;                                                                                                 ")
				.append(" FETCH NEXT FROM rs INTO @enterorg, @differentkind, @ownership, @propertyno, @serialno;                   ")
				.append(" WHILE @@Fetch_Status = 0                                                                                 ")
				.append("     BEGIN                                                                                                ")
				.append("         INSERT INTO UNTBU_BUILDING                                                                       ")
				.append("         (enterorg,                                                                                       ")
				.append("          ownership,                                                                                      ")
				.append("          caseno,                                                                                         ")
				.append("          differencekind,                                                                                 ")
				.append("          caseserialno,                                                                                   ")
				.append("          propertyno,                                                                                     ")
				.append("          serialno,                                                                                       ")
				.append("          otherlimityear,                                                                                 ")
				.append("          propertyname1,                                                                                  ")
				.append("          signno,                                                                                         ")
				.append("          doorplate1,                                                                                     ")
				.append("          doorplate2,                                                                                     ")
				.append("          doorplate4,                                                                                     ")
				.append("          doorplatevillage1,                                                                              ")
				.append("          doorplatevillage2,                                                                              ")
				.append("          doorplaterd1,                                                                                   ")
				.append("          doorplaterd2,                                                                                   ")
				.append("          doorplatesec,                                                                                   ")
				.append("          doorplateln,                                                                                    ")
				.append("          doorplatealy,                                                                                   ")
				.append("          doorplateno,                                                                                    ")
				.append("          doorplatefloor1,                                                                                ")
				.append("          doorplatefloor2,                                                                                ")
				.append("          buildstyle,                                                                                     ")
				.append("          datastate,                                                                                      ")
				.append("          verify,                                                                                         ")
				.append("          propertykind,                                                                                   ")
				.append("          fundtype,                                                                                       ")
				.append("          valuable,                                                                                       ")
				.append("          taxcredit,                                                                                      ")
				.append("          originalcarea,                                                                                  ")
				.append("          originalsarea,                                                                                  ")
				.append("          originalarea,                                                                                   ")
				.append("          originalholdnume,                                                                               ")
				.append("          originalholddeno,                                                                               ")
				.append("          originalholdarea,                                                                               ")
				.append("          carea,                                                                                          ")
				.append("          sarea,                                                                                          ")
				.append("          area,                                                                                           ")
				.append("          holdnume,                                                                                       ")
				.append("          holddeno,                                                                                       ")
				.append("          holdarea,                                                                                       ")
				.append("          originalbv,                                                                                     ")
				.append("          originalnote,                                                                                   ")
				.append("          accountingtitle,                                                                                ")
				.append("          bookvalue,                                                                                      ")
				.append("          netvalue,                                                                                       ")
				.append("          fundssource,                                                                                    ")
				.append("          fundssource1,                                                                                   ")
				.append("          ownershipdate,                                                                                  ")
				.append("          ownershipcause,                                                                                 ")
				.append("          nonproof,                                                                                       ")
				.append("          proofdoc,                                                                                       ")
				.append("          ownershipnote,                                                                                  ")
				.append("          builddate,                                                                                      ")
				.append("          floor1,                                                                                         ")
				.append("          floor2,                                                                                         ")
				.append("          stuff,                                                                                          ")
				.append("          buydate,                                                                                        ")
				.append("          sourcekind,                                                                                     ")
				.append("          sourcedate,                                                                                     ")
				.append("          sourcedoc,                                                                                      ")
				.append("          manageorg,                                                                                      ")
				.append("          originaldeprmethod,                                                                             ")
				.append("          originalbuildfeecb,                                                                             ")
				.append("          originaldeprunitcb,                                                                             ")
				.append("          originaldeprpark,                                                                               ")
				.append("          originaldeprunit,                                                                               ")
				.append("          originaldeprunit1,                                                                              ")
				.append("          originaldepraccounts,                                                                           ")
				.append("          originalscrapvalue,                                                                             ")
				.append("          originaldepramount,                                                                             ")
				.append("          originalaccumdepr,                                                                              ")
				.append("          originalapportionmonth,                                                                         ")
				.append("          originalmonthdepr,                                                                              ")
				.append("          originalmonthdepr1,                                                                             ")
				.append("          originalapportionendym,                                                                         ")
				.append("          deprmethod,                                                                                     ")
				.append("          buildfeecb,                                                                                     ")
				.append("          deprunitcb,                                                                                     ")
				.append("          deprpark,                                                                                       ")
				.append("          deprunit,                                                                                       ")
				.append("          deprunit1,                                                                                      ")
				.append("          depraccounts,                                                                                   ")
				.append("          scrapvalue,                                                                                     ")
				.append("          depramount,                                                                                     ")
				.append("          accumdepr,                                                                                      ")
				.append("          apportionmonth,                                                                                 ")
				.append("          monthdepr,                                                                                      ")
				.append("          monthdepr1,                                                                                     ")
				.append("          apportionendym,                                                                                 ")
				.append("          nodeprset,                                                                                      ")
				.append("          appraisedate,                                                                                   ")
				.append("          originalkeepunit,                                                                               ")
				.append("          originalkeeper,                                                                                 ")
				.append("          originaluseunit,                                                                                ")
				.append("          originaluser,                                                                                   ")
				.append("          originalusernote,                                                                               ")
				.append("          originalplace1,                                                                                 ")
				.append("          originalplace,                                                                                  ")
				.append("          keepunit,                                                                                       ")
				.append("          keeper,                                                                                         ")
				.append("          useunit,                                                                                        ")
				.append("          userno,                                                                                         ")
				.append("          usernote,                                                                                       ")
				.append("          place1,                                                                                         ")
				.append("          place,                                                                                          ")
				.append("          uselicense,                                                                                     ")
				.append("          escroworivalue,                                                                                 ")
				.append("          escroworiaccumdepr,                                                                             ")
				.append("          oldpropertyno,                                                                                  ")
				.append("          oldserialno,                                                                                    ")
				.append("          notes,                                                                                          ")
				.append("          editid,                                                                                         ")
				.append("          editdate,                                                                                       ")
				.append("          edittime,                                                                                       ")
				.append("          picture,                                                                                        ")
				.append("          originallimityear,                                                                              ")
				.append("          oldlotno,                                                                                       ")
				.append("          otherpropertyunit,                                                                              ")
				.append("          othermaterial,                                                                                  ")
				.append("          originalaccumdepr1,                                                                             ")
				.append("          originalaccumdepr2                                                                              ")
				.append("         )                                                                                                ")
				.append("                SELECT a.enterorg,                                                                        ")
				.append("                       a.ownership,                                                                       ")
				.append(Common.sqlChar(addCaseno)).append(",")
				.append(Common.sqlChar(differencekind)).append(",")
				.append("                       @caseserialno AS caseserialno,                                                     ")
				.append("                       a.propertyno,                                                                      ")
				.append("                (                                                                                         ")
				.append("                    SELECT CASE                                                                           ")
				.append("                               WHEN MAX(d.serialno) IS NULL                                               ")
				.append("                               THEN RIGHT(REPLICATE('0', 7) + CAST('1' AS NVARCHAR), 7)                   ")
				.append("                               ELSE RIGHT(REPLICATE('0', 7) + CAST(MAX(d.serialno) + 1 AS NVARCHAR), 7)   ")
				.append("                           END                                                                            ")
				.append("                    FROM UNTBU_BUILDING d                                                                 ")
				.append("                    WHERE a.enterorg = d.enterorg                                                         ")
				.append("                          AND a.ownership = d.ownership                                                   ")
				.append("                          AND d.differencekind = ").append(Common.sqlChar(differencekind))
				.append("                          AND a.propertyno = d.propertyno                                                 ")
				.append("                ) AS serialno,                                                                            ")
				.append("                       a.otherlimityear,                                                                  ")
				.append("                       a.propertyname1,                                                                   ")
				.append("                       a.signno,                                                                          ")
				.append("                       a.doorplate1,                                                                      ")
				.append("                       a.doorplate2,                                                                      ")
				.append("                       a.doorplate4,                                                                      ")
				.append("                       a.doorplatevillage1,                                                               ")
				.append("                       a.doorplatevillage2,                                                               ")
				.append("                       a.doorplaterd1,                                                                    ")
				.append("                       a.doorplaterd2,                                                                    ")
				.append("                       a.doorplatesec,                                                                    ")
				.append("                       a.doorplateln,                                                                     ")
				.append("                       a.doorplatealy,                                                                    ")
				.append("                       a.doorplateno,                                                                     ")
				.append("                       a.doorplatefloor1,                                                                 ")
				.append("                       a.doorplatefloor2,                                                                 ")
				.append("                       a.buildstyle,                                                                      ")
				.append("                       1,                                                                                 ")
				.append("                       'N',                                                                               ")
				.append("                       a.propertykind,                                                                    ")
				.append("                       a.fundtype,                                                                        ")
				.append("                       a.valuable,                                                                        ")
				.append("                       a.taxcredit,                                                                       ")
				.append("                       a.originalcarea,                                                                   ")
				.append("                       a.originalsarea,                                                                   ")
				.append("                       a.originalarea,                                                                    ")
				.append("                       a.originalholdnume,                                                                ")
				.append("                       a.originalholddeno,                                                                ")
				.append("                       a.originalholdarea,                                                                ")
				.append("                       b.carea,                                                                           ")
				.append("                       b.sarea,                                                                           ")
				.append("                       b.area,                                                                            ")
				.append("                       b.holdnume,                                                                        ")
				.append("                       b.holddeno,                                                                        ")
				.append("                       b.holdarea,                                                                        ")
				.append("                       a.originalbv,                                                                      ")
				.append("                       a.originalnote,                                                                    ")
				.append("                       a.accountingtitle,                                                                 ")
				.append("                       b.bookvalue,                                                                       ")
				.append("                       b.netvalue,                                                                        ")
				.append("                       a.fundssource,                                                                     ")
				.append("                       a.fundssource1,                                                                    ")
				.append("                       a.ownershipdate,                                                                   ")
				.append("                       a.ownershipcause,                                                                  ")
				.append("                       a.nonproof,                                                                        ")
				.append("                       a.proofdoc,                                                                        ")
				.append("                       a.ownershipnote,                                                                   ")
				.append("                       a.builddate,                                                                       ")
				.append("                       a.floor1,                                                                          ")
				.append("                       a.floor2,                                                                          ")
				.append("                       a.stuff,                                                                           ")
				.append("                       a.buydate,                                                                         ")
				.append(Common.sqlChar(sourceKind)).append(", ")
				.append("                       b.reducedate,                                                                             ")
				.append("                       a.sourcedoc,                                                                       ")
				.append("                       a.manageorg,                                                                       ")
				.append("                       b.olddeprmethod,                                                              	   ")
				.append("                       b.oldbuildfeecb,                                                              	   ")
				.append("                       b.olddeprunitcb,                                                              	   ")
				.append("                       b.olddeprpark,                                                                	   ")
				.append("                       b.olddeprunit,                                                                	   ")
				.append("                       b.olddeprunit1,                                                               	   ")
				.append("                       b.olddepraccounts,                                                            	   ")
				.append("                       b.oldscrapvalue,                                                              	   ")
				.append("                       b.olddepramount,                                                              	   ")
				.append("                       b.oldaccumdepr,                                                                    ")
				.append("                       b.oldapportionmonth,                                                          	   ")
				.append("                       b.oldmonthdepr,                                                               	   ")
				.append("                       b.oldmonthdepr1,                                                            	   ")
				.append("                       b.oldapportionendym,                                                     		   ")
				.append("                       b.olddeprmethod,                                                                   ")
				.append("                       b.oldbuildfeecb,                                                                   ")
				.append("                       b.olddeprunitcb,                                                                   ")
				.append("                       b.olddeprpark,                                                                     ")
				.append("                       b.olddeprunit,                                                                     ")
				.append("                       b.olddeprunit1,                                                                    ")
				.append("                       b.olddepraccounts,                                                                 ")
				.append("                       b.oldscrapvalue,                                                                   ")
				.append("                       b.olddepramount,                                                                   ")
				.append("                       0 as accumdepr,                                                                    ")
				.append("                       b.oldapportionmonth,                                                               ")
				.append("                       b.oldmonthdepr,                                                                    ")
				.append("                       b.oldmonthdepr1,                                                                   ")
				.append("                       b.oldapportionendym,                                                               ")
				.append("                       a.nodeprset,                                                                       ")
				.append("                       a.appraisedate,                                                                    ")
				.append("                       a.keepunit,                                                                ")
				.append("                       a.keeper,                                                                  ")
				.append("                       a.useunit,                                                                 ")
				.append("                       a.userno,                                                                    ")
				.append("                       a.usernote,                                                                ")
				.append("                       a.place1,                                                                  ")
				.append("                       a.place,                                                                   ")
				.append("                       a.keepunit,                                                                        ")
				.append("                       a.keeper,                                                                          ")
				.append("                       a.useunit,                                                                         ")
				.append("                       a.userno,                                                                          ")
				.append("                       a.usernote,                                                                        ")
				.append("                       a.place1,                                                                          ")
				.append("                       a.place,                                                                           ")
				.append("                       a.uselicense,                                                                      ")
				.append("                       a.escroworivalue,                                                                  ")
				.append("                       a.escroworiaccumdepr,                                                              ")
				.append("                       a.propertyno,                                                                      ")
				.append("                       a.serialno,                                                                        ")
				.append("                       a.notes,                                                                           ")
				.append(Common.sqlChar(this.getUserID())).append(",")
				.append(Common.sqlChar(Datetime.getYYYYMMDD())).append(",")
				.append(Common.sqlChar(Datetime.getHHMMSS())).append(",")
				.append("                       a.picture,                                                                         ")
				.append("                       a.originallimityear,                                                               ")
				.append("                       a.oldlotno,                                                                        ")
				.append("                       a.otherpropertyunit,                                                               ")
				.append("                       a.othermaterial,                                                                   ")
				.append("                       b.oldaccumdepr,                                                                    ")
				.append("                       0                                                                                  ")
				.append("                FROM UNTBU_BUILDING a                                                                     ")
				.append("                     JOIN UNTBU_REDUCEDETAIL b ON a.enterorg = b.enterorg                                 ")
				.append("                                                  AND a.ownership = b.ownership                           ")
				.append("                                                  AND a.differencekind = b.differencekind                 ")
				.append("                                                  AND a.propertyno = b.propertyno                         ")
				.append("                                                  AND a.serialno = b.serialno                             ")
				.append("                WHERE b.caseno = ").append(Common.sqlChar(reduceCaseNo))
				.append("                      AND a.enterorg = @enterorg                                                          ")
				.append("                      AND a.differencekind = @differentkind                                               ")
				.append("                      AND a.ownership = @ownership                                                        ")
				.append("                      AND a.propertyno = @propertyno                                                      ")
				.append("                      AND a.serialno = @serialno;                                                         ")
				.append("         SELECT @caseserialno = @caseserialno + 1;                                                        ")
				.append("         FETCH NEXT FROM rs INTO @enterorg, @differentkind, @ownership, @propertyno, @serialno;           ")
				.append("     END;                                                                                                 ")
				.append(" CLOSE rs;                                                                                                ")
				.append(" DEALLOCATE rs;                                                                                           ");
			
			//動產-明細
			sql.append(" DECLARE rs CURSOR                                                                                             ")
				.append(" FOR SELECT a.enterorg,                                                                                        ")
				.append("            a.differencekind,                                                                                  ")
				.append("            a.ownership,                                                                                       ")
				.append("            a.propertyno,                                                                                      ")
				.append("            a.serialno                                                                                         ")
				.append("     FROM UNTMP_MOVABLEDETAIL a                                                                                ")
				.append("          JOIN UNTMP_REDUCEDETAIL b ON a.enterorg = b.enterorg                                                 ")
				.append("                                       AND a.differencekind = b.differencekind                                 ")
				.append("                                       AND a.ownership = b.ownership                                           ")
				.append("                                       AND a.propertyno = b.propertyno                                         ")
				.append("                                       AND a.serialno = b.serialno                                             ")
				.append("     WHERE a.enterorg = ").append(Common.sqlChar(this.getEnterOrg()))
				.append("           AND b.caseno = ").append(Common.sqlChar(reduceCaseNo))
				.append("     ORDER BY a.enterorg,                                                                                      ")
				.append("              a.ownership,                                                                                     ")
				.append("              a.differencekind,                                                                                ")
				.append("              a.propertyno,                                                                                    ")
				.append("              a.serialno;                                                                                      ")
				.append(" OPEN rs;                                                                                                      ")
				.append(" FETCH NEXT FROM rs INTO @enterorg, @differentkind, @ownership, @propertyno, @serialno;                        ")
				.append(" WHILE @@Fetch_Status = 0                                                                                      ")
				.append("     BEGIN                                                                                                     ")
				.append("         INSERT INTO UNTMP_MOVABLEDETAIL                                                                       ")
				.append("         (enterorg,                                                                                            ")
				.append("          ownership,                                                                                           ")
				.append("          caseno,                                                                                              ")
				.append("          differencekind,                                                                                      ")
				.append("          propertyno,                                                                                          ")
				.append("          lotno,                                                                                               ")
				.append("          serialno,                                                                                            ")
				.append("          datastate,                                                                                           ")
				.append("          verify,                                                                                              ")
				.append("          originalamount,                                                                                      ")
				.append("          originalbv,                                                                                          ")
				.append("          bookamount,                                                                                          ")
				.append("          bookvalue,                                                                                           ")
				.append("          netvalue,                                                                                            ")
				.append("          licenseplate,                                                                                        ")
				.append("          purpose,                                                                                             ")
				.append("          originalkeepunit,                                                                                    ")
				.append("          originalkeeper,                                                                                      ")
				.append("          originaluseunit,                                                                                     ")
				.append("          originaluser,                                                                                        ")
				.append("          originalusernote,                                                                                    ")
				.append("          originalplace1,                                                                                      ")
				.append("          originalplace,                                                                                       ")
				.append("          keepunit,                                                                                            ")
				.append("          keeper,                                                                                              ")
				.append("          useunit,                                                                                             ")
				.append("          userno,                                                                                              ")
				.append("          usernote,                                                                                            ")
				.append("          place1,                                                                                              ")
				.append("          place,                                                                                               ")
				.append("          originaldeprmethod,                                                                                  ")
				.append("          originalbuildfeecb,                                                                                  ")
				.append("          originaldeprunitcb,                                                                                  ")
				.append("          originaldeprpark,                                                                                    ")
				.append("          originaldeprunit,                                                                                    ")
				.append("          originaldeprunit1,                                                                                   ")
				.append("          originaldepraccounts,                                                                                ")
				.append("          originalscrapvalue,                                                                                  ")
				.append("          originaldepramount,                                                                                  ")
				.append("          originalaccumdepr,                                                                                   ")
				.append("          originalapportionmonth,                                                                              ")
				.append("          originalmonthdepr,                                                                                   ")
				.append("          originalmonthdepr1,                                                                                  ")
				.append("          originalapportionendym,                                                                              ")
				.append("          deprmethod,                                                                                          ")
				.append("          buildfeecb,                                                                                          ")
				.append("          deprunitcb,                                                                                          ")
				.append("          deprpark,                                                                                            ")
				.append("          deprunit,                                                                                            ")
				.append("          deprunit1,                                                                                           ")
				.append("          depraccounts,                                                                                        ")
				.append("          scrapvalue,                                                                                          ")
				.append("          depramount,                                                                                          ")
				.append("          accumdepr,                                                                                           ")
				.append("          apportionmonth,                                                                                      ")
				.append("          monthdepr,                                                                                           ")
				.append("          monthdepr1,                                                                                          ")
				.append("          apportionendym,                                                                                      ")
				.append("          nodeprset,                                                                                           ")
				.append("          notes1,                                                                                              ")
				.append("          barcode,                                                                                             ")
				.append("          oldpropertyno,                                                                                       ")
				.append("          oldserialno,                                                                                         ")
				.append("          notes,                                                                                               ")
				.append("          editid,                                                                                              ")
				.append("          editdate,                                                                                            ")
				.append("          edittime,                                                                                            ")
				.append("          originallimityear,                                                                                   ")
				.append("          otherlimityear,                                                                                      ")
				.append("          oldlotno,                                                                                            ")
				.append("          propertyname1,                                                                                       ")
				.append("          originalaccumdepr1,                                                                                  ")
				.append("          originalaccumdepr2                                                                                   ")
				.append("         )                                                                                                     ")
				.append("                SELECT a.enterorg,                                                                             ")
				.append("                       a.ownership,                                                                            ")
				.append(Common.sqlChar(addCaseno)).append(",")
				.append(Common.sqlChar(differencekind)).append(",")
				.append("                       a.propertyno,                                                                           ")
				.append("                (                                                                                              ")
				.append("                    SELECT CASE                                                                                ")
				.append("                               WHEN MAX(d.lotno) IS NULL                                                       ")
				.append("                               THEN RIGHT(REPLICATE('0', 7) + CAST('1' AS NVARCHAR), 7)                        ")
				.append("                               ELSE RIGHT(REPLICATE('0', 7) + CAST(MAX(d.lotno) + 1 AS NVARCHAR), 7)           ")
				.append("                           END                                                                                 ")
				.append("                    FROM UNTMP_MOVABLEDETAIL d                                                                 ")
				.append("                    WHERE a.enterorg = d.enterorg                                                              ")
				.append("                          AND a.ownership = d.ownership                                                        ")
				.append("                          AND d.differencekind = ").append(Common.sqlChar(differencekind))
				.append("                          AND a.propertyno = d.propertyno                                                      ")
				.append("                ) AS lotno,                                                                                    ")
				.append("                (                                                                                              ")
				.append("                    SELECT CASE                                                                                ")
				.append("                               WHEN MAX(d.serialno) IS NULL                                                    ")
				.append("                               THEN RIGHT(REPLICATE('0', 7) + CAST('1' AS NVARCHAR), 7)                        ")
				.append("                               ELSE RIGHT(REPLICATE('0', 7) + CAST(MAX(d.serialno) + 1 AS NVARCHAR), 7)        ")
				.append("                           END                                                                                 ")
				.append("                    FROM UNTMP_MOVABLEDETAIL d                                                                 ")
				.append("                    WHERE a.enterorg = d.enterorg                                                              ")
				.append("                          AND a.ownership = d.ownership                                                        ")
				.append("                          AND d.differencekind = ").append(Common.sqlChar(differencekind))
				.append("                          AND a.propertyno = d.propertyno                                                      ")
				.append("                ) AS serialno,                                                                                 ")
				.append("                       1,                                                                                      ")
				.append("                       'N',                                                                                    ")
				.append("                       a.originalamount,                                                                       ")
				.append("                       b.oldbookvalue,                                                                           ")
				.append("                       1,                                                                                      ")
				.append("                       b.oldbookvalue,                                                                         ")
				.append("                       b.oldnetvalue,                                                                          ")
				.append("                       a.licenseplate,                                                                         ")
				.append("                       a.purpose,                                                                              ")
				.append("                       a.keepunit,                                                                     ")
				.append("                       a.keeper,                                                                       ")
				.append("                       a.useunit,                                                                      ")
				.append("                       a.userno,                                                                         ")
				.append("                       a.usernote,                                                                     ")
				.append("                       a.place1,                                                                       ")
				.append("                       a.place,                                                                        ")
				.append("                       a.keepunit,                                                                             ")
				.append("                       a.keeper,                                                                               ")
				.append("                       a.useunit,                                                                              ")
				.append("                       a.userno,                                                                               ")
				.append("                       a.usernote,                                                                             ")
				.append("                       a.place1,                                                                               ")
				.append("                       a.place,                                                                                ")
				.append("                       b.olddeprmethod,                                                                   		")
				.append("                       b.oldbuildfeecb,                                                                   		")
				.append("                       b.olddeprunitcb,                                                                   		")
				.append("                       b.olddeprpark,                                                                     		")
				.append("                       b.olddeprunit,                                                                     		")
				.append("                       b.olddeprunit1,                                                                    		")
				.append("                       b.olddepraccounts,                                                                 		")
				.append("                       b.oldscrapvalue,                                                                   		")
				.append("                       b.olddepramount,                                                                   		")
				.append("                       b.oldaccumdepr,                                                                         ")
				.append("                       b.oldapportionmonth,                                                               		")
				.append("                       b.oldmonthdepr,                                                                    		")
				.append("                       b.oldmonthdepr1,                                                                     	")
				.append("                       b.oldapportionendym,                                                            	    ")
				.append("                       b.olddeprmethod,                                                                        ")
				.append("                       b.oldbuildfeecb,                                                                        ")
				.append("                       b.olddeprunitcb,                                                                        ")
				.append("                       b.olddeprpark,                                                                          ")
				.append("                       b.olddeprunit,                                                                          ")
				.append("                       b.olddeprunit1,                                                                         ")
				.append("                       b.olddepraccounts,                                                                      ")
				.append("                       b.oldscrapvalue,                                                                        ")
				.append("                       b.olddepramount,                                                                        ")
				.append("                       0 as accumdepr,                                                                         ")
				.append("                       b.oldapportionmonth,                                                                    ")
				.append("                       b.oldmonthdepr,                                                                         ")
				.append("                       b.oldmonthdepr1,                                                                        ")
				.append("                       b.oldapportionendym,                                                                    ")
				.append("                       a.nodeprset,                                                                            ")
				.append("                       a.notes1,                                                                               ")
				.append("                       a.barcode,                                                                              ")
				.append("                       a.propertyno,                                                                           ")
				.append("                       a.serialno,                                                                             ")
				.append("                       a.notes,                                                                                ")
				.append(Common.sqlChar(this.getUserID())).append(",")
				.append(Common.sqlChar(Datetime.getYYYYMMDD())).append(",")
				.append(Common.sqlChar(Datetime.getHHMMSS())).append(",")
				.append("                       a.originallimityear,                                                                    ")
				.append("                       a.otherlimityear,                                                                       ")
				.append("                       a.lotno,                                                                                ")
				.append("                       a.propertyname1,                                                                        ")
				.append("                       b.oldaccumdepr,                                                                         ")
				.append("                       0                                                                                       ")
				.append("                FROM UNTMP_MOVABLEDETAIL a                                                                     ")
				.append("                     JOIN UNTMP_REDUCEDETAIL b ON a.enterorg = b.enterorg                                      ")
				.append("                                                  AND a.ownership = b.ownership                                ")
				.append("                                                  AND a.differencekind = b.differencekind                      ")
				.append("                                                  AND a.propertyno = b.propertyno                              ")
				.append("                                                  AND a.serialno = b.serialno                                  ")
				.append("                WHERE b.caseno = ").append(Common.sqlChar(reduceCaseNo))
				.append("                      AND a.enterorg = @enterorg                                                               ")
				.append("                      AND a.differencekind = @differentkind                                                    ")
				.append("                      AND a.ownership = @ownership                                                             ")
				.append("                      AND a.propertyno = @propertyno                                                           ")
				.append("                      AND a.serialno = @serialno;                                                              ")
				.append("         FETCH NEXT FROM rs INTO @enterorg, @differentkind, @ownership, @propertyno, @serialno;                ")
				.append("     END;                                                                                                      ")
				.append(" CLOSE rs;                                                                                                     ")
				.append(" DEALLOCATE rs;                                                                                                ");
			
			//動產-批
			sql.append(" DECLARE rs CURSOR                                                                                             ")
				.append(" FOR SELECT a.enterorg,                                                                                        ")
				.append("            a.differencekind,                                                                                  ")
				.append("            a.ownership,                                                                                       ")
				.append("            a.propertyno,                                                                                      ")
				.append("            a.serialno                                                                                         ")
				.append("     FROM UNTMP_MOVABLEDETAIL a                                                                                ")
				.append("          JOIN UNTMP_REDUCEDETAIL b ON a.enterorg = b.enterorg                                                 ")
				.append("                                       AND a.differencekind = b.differencekind                                 ")
				.append("                                       AND a.ownership = b.ownership                                           ")
				.append("                                       AND a.propertyno = b.propertyno                                         ")
				.append("                                       AND a.serialno = b.serialno                                             ")
				.append("     WHERE a.enterorg = ").append(Common.sqlChar(this.getEnterOrg()))
				.append("           AND b.caseno = ").append(Common.sqlChar(reduceCaseNo))
				.append("     ORDER BY a.enterorg,                                                                                      ")
				.append("              a.ownership,                                                                                     ")
				.append("              a.differencekind,                                                                                ")
				.append("              a.propertyno,                                                                                    ")
				.append("              a.serialno;                                                                                      ")
				.append(" OPEN rs;                                                                                                      ")
				.append(" FETCH NEXT FROM rs INTO @enterorg, @differentkind, @ownership, @propertyno, @serialno;                        ")
				.append(" WHILE @@Fetch_Status = 0                                                                                      ")
				.append("     BEGIN                                                                                                     ")
				.append("         INSERT INTO UNTMP_MOVABLE                                                                             ")
				.append("         (enterorg,                                                                                            ")
				.append("          ownership,                                                                                           ")
				.append("          caseno,                                                                                              ")
				.append("          differencekind,                                                                                      ")
				.append("          caseserialno,                                                                                        ")
				.append("          propertyno,                                                                                          ")
				.append("          lotno,                                                                                               ")
				.append("          serialnos,                                                                                           ")
				.append("          serialnoe,                                                                                           ")
				.append("          otherpropertyunit,                                                                                   ")
				.append("          othermaterial,                                                                                       ")
				.append("          otherlimityear,                                                                                      ")
				.append("          propertyname1,                                                                                       ")
				.append("          approvedate,                                                                                         ")
				.append("          approvedoc,                                                                                          ")
				.append("          buydate,                                                                                             ")
				.append("          datastate,                                                                                           ")
				.append("          verify,                                                                                              ")
				.append("          propertykind,                                                                                        ")
				.append("          fundtype,                                                                                            ")
				.append("          valuable,                                                                                            ")
				.append("          originalamount,                                                                                      ")
				.append("          originalunit,                                                                                        ")
				.append("          originalbv,                                                                                          ")
				.append("          originalnote,                                                                                        ")
				.append("          accountingtitle,                                                                                     ")
				.append("          bookamount,                                                                                          ")
				.append("          bookvalue,                                                                                           ")
				.append("          netvalue,                                                                                            ")
				.append("          fundssource,                                                                                         ")
				.append("          fundssource1,                                                                                        ")
				.append("          grantvalue,                                                                                          ")
				.append("          articlename,                                                                                         ")
				.append("          nameplate,                                                                                           ")
				.append("          specification,                                                                                       ")
				.append("          storeno,                                                                                             ")
				.append("          sourcekind,                                                                                          ")
				.append("          sourcedate,                                                                                          ")
				.append("          sourcedoc,                                                                                           ")
				.append("          originaldeprmethod,                                                                                  ")
				.append("          originalbuildfeecb,                                                                                  ")
				.append("          originaldeprunitcb,                                                                                  ")
				.append("          originaldeprpark,                                                                                    ")
				.append("          originaldeprunit,                                                                                    ")
				.append("          originaldeprunit1,                                                                                   ")
				.append("          originaldepraccounts,                                                                                ")
				.append("          originalscrapvalue,                                                                                  ")
				.append("          originaldepramount,                                                                                  ")
				.append("          originalaccumdepr,                                                                                   ")
				.append("          originalapportionmonth,                                                                              ")
				.append("          originalmonthdepr,                                                                                   ")
				.append("          originalmonthdepr1,                                                                                  ")
				.append("          originalapportionendym,                                                                              ")
				.append("          escroworivalue,                                                                                      ")
				.append("          escroworiaccumdepr,                                                                                  ")
				.append("          oldpropertyno,                                                                                       ")
				.append("          oldserialnos,                                                                                        ")
				.append("          oldserialnoe,                                                                                        ")
				.append("          notes,                                                                                               ")
				.append("          editid,                                                                                              ")
				.append("          editdate,                                                                                            ")
				.append("          edittime,                                                                                            ")
				.append("          picture,                                                                                             ")
				.append("          originallimityear,                                                                                   ")
				.append("          oldlotno,                                                                                            ")
				.append("          bookamount1,                                                                                         ")
				.append("          originalaccumdepr1,                                                                                  ")
				.append("          originalaccumdepr2                                                                                   ")
				.append("         )                                                                                                     ")
				.append("                SELECT a.enterorg,                                                                             ")
				.append("                       a.ownership,                                                                            ")
				.append(Common.sqlChar(addCaseno)).append(",")
				.append(Common.sqlChar(differencekind)).append(",")
				.append("                       @caseserialno AS caseserialno,                                                          ")
				.append("                       a.propertyno,                                                                           ")
				.append("                (                                                                                              ")
				.append("                    SELECT CASE                                                                                ")
				.append("                               WHEN MAX(d.lotno) IS NULL                                                       ")
				.append("                               THEN RIGHT(REPLICATE('0', 7) + CAST('1' AS NVARCHAR), 7)                        ")
				.append("                               ELSE RIGHT(REPLICATE('0', 7) + CAST(MAX(d.lotno) + 1 AS NVARCHAR), 7)           ")
				.append("                           END                                                                                 ")
				.append("                    FROM UNTMP_MOVABLE d                                                                 ")
				.append("                    WHERE a.enterorg = d.enterorg                                                              ")
				.append("                          AND a.ownership = d.ownership                                                        ")
				.append("                          AND d.differencekind = ").append(Common.sqlChar(differencekind))
				.append("                          AND a.propertyno = d.propertyno                                                      ")
				.append("                ) AS lotno,                                                                                    ")
				.append("                (                                                                                              ")
				.append("                    SELECT CASE                                                                                ")
				.append("                               WHEN MAX(d.serialnoe) IS NULL                                                    ")
				.append("                               THEN RIGHT(REPLICATE('0', 7) + CAST('1' AS NVARCHAR), 7)                        ")
				.append("                               ELSE RIGHT(REPLICATE('0', 7) + CAST(MAX(d.serialnoe) + 1 AS NVARCHAR), 7)        ")
				.append("                           END                                                                                 ")
				.append("                    FROM UNTMP_MOVABLE d                                                                 ")
				.append("                    WHERE a.enterorg = d.enterorg                                                              ")
				.append("                          AND a.ownership = d.ownership                                                        ")
				.append("                          AND d.differencekind = ").append(Common.sqlChar(differencekind))
				.append("                          AND a.propertyno = d.propertyno                                                      ")
				.append("                ) AS serialnos,                                                                                ")
				.append("                (                                                                                              ")
				.append("                    SELECT CASE                                                                                ")
				.append("                               WHEN MAX(d.serialnoe) IS NULL                                                    ")
				.append("                               THEN RIGHT(REPLICATE('0', 7) + CAST('1' AS NVARCHAR), 7)                        ")
				.append("                               ELSE RIGHT(REPLICATE('0', 7) + CAST(MAX(d.serialnoe) + 1 AS NVARCHAR), 7)        ")
				.append("                           END                                                                                 ")
				.append("                    FROM UNTMP_MOVABLE d                                                                 ")
				.append("                    WHERE a.enterorg = d.enterorg                                                              ")
				.append("                          AND a.ownership = d.ownership                                                        ")
				.append("                          AND d.differencekind = ").append(Common.sqlChar(differencekind))
				.append("                          AND a.propertyno = d.propertyno                                                      ")
				.append("                ) AS serialnoe,                                                                                ")
				.append("                       c.otherpropertyunit,                                                                    ")
				.append("                       c.othermaterial,                                                                        ")
				.append("                       a.otherlimityear,                                                                       ")
				.append("                       a.propertyname1,                                                                        ")
				.append("                       c.approvedate,                                                                          ")
				.append("                       c.approvedoc,                                                                           ")
				.append("                       c.buydate,                                                                              ")
				.append("                       1,                                                                                      ")
				.append("                       'N',                                                                                    ")
				.append("                       c.propertykind,                                                                         ")
				.append("                       c.fundtype,                                                                             ")
				.append("                       c.valuable,                                                                             ")
				.append("                       1,                                                                                      ")
				.append("                       c.originalunit,                                                                         ")
				.append("                       c.originalunit,                                                                         ")
				.append("                       c.originalnote,                                                                         ")
				.append("                       c.accountingtitle,                                                                      ")
				.append("                       1,                                                                                      ")
				.append("                       b.oldbookvalue,                                                                         ")
				.append("                       b.oldnetvalue,                                                                          ")
				.append("                       c.fundssource,                                                                          ")
				.append("                       c.fundssource1,                                                                         ")
				.append("                       c.grantvalue,                                                                           ")
				.append("                       c.articlename,                                                                          ")
				.append("                       c.nameplate,                                                                            ")
				.append("                       c.specification,                                                                        ")
				.append("                       c.storeno,                                                                              ")
				.append(Common.sqlChar(sourceKind)).append(", ")
				.append("                       b.reducedate,                                                                             ")
				.append("                       c.sourcedoc,                                                                            ")
				.append("                       b.olddeprmethod,                                                                        ")
				.append("                       b.oldbuildfeecb,                                                                        ")
				.append("                       b.olddeprunitcb,                                                                        ")
				.append("                       b.olddeprpark,                                                                          ")
				.append("                       b.olddeprunit,                                                                          ")
				.append("                       b.olddeprunit1,                                                                         ")
				.append("                       b.olddepraccounts,                                                                      ")
				.append("                       b.oldscrapvalue,                                                                        ")
				.append("                       b.olddepramount,                                                                        ")
				.append("                       b.oldaccumdepr,                                                                         ")
				.append("                       b.oldapportionmonth,                                                                    ")
				.append("                       b.oldmonthdepr,                                                                         ")
				.append("                       b.oldmonthdepr1,                                                                        ")
				.append("                       b.oldapportionendym,                                                                    ")
				.append("                       c.escroworivalue,                                                                       ")
				.append("                       c.escroworiaccumdepr,                                                                   ")
				.append("                       a.propertyno,                                                                           ")
				.append("                       a.serialno,                                                                             ")
				.append("                       a.serialno,                                                                             ")
				.append("                       a.notes,                                                                                ")
				.append(Common.sqlChar(this.getUserID())).append(",")
				.append(Common.sqlChar(Datetime.getYYYYMMDD())).append(",")
				.append(Common.sqlChar(Datetime.getHHMMSS())).append(",")
				.append("                       c.picture,                                                                              ")
				.append("                       a.originallimityear,                                                                    ")
				.append("                       a.lotno,                                                                                ")
				.append("                       1,                                                                                      ")
				.append("                       b.oldaccumdepr,                                                                         ")
				.append("                       0                                                                                       ")
				.append("                FROM UNTMP_MOVABLEDETAIL a                                                                     ")
				.append("                     JOIN UNTMP_REDUCEDETAIL b ON a.enterorg = b.enterorg                                      ")
				.append("                                                  AND a.ownership = b.ownership                                ")
				.append("                                                  AND a.differencekind = b.differencekind                      ")
				.append("                                                  AND a.propertyno = b.propertyno                              ")
				.append("                                                  AND a.serialno = b.serialno                                  ")
				.append("                     JOIN UNTMP_MOVABLE c ON a.enterorg = c.enterorg                                           ")
				.append("                                             AND a.ownership = c.ownership                                     ")
				.append("                                             AND a.differencekind = c.differencekind                           ")
				.append("                                             AND a.propertyno = c.propertyno                                   ")
				.append("                                             AND a.lotno = c.lotno                                             ")
				.append("                WHERE b.caseno = ").append(Common.sqlChar(reduceCaseNo))
				.append("                      AND a.enterorg = @enterorg                                                               ")
				.append("                      AND a.differencekind = @differentkind                                                    ")
				.append("                      AND a.ownership = @ownership                                                             ")
				.append("                      AND a.propertyno = @propertyno                                                           ")
				.append("                      AND a.serialno = @serialno;                                                              ")
				.append("         SELECT @caseserialno = @caseserialno + 1;                                                             ")
				.append("         FETCH NEXT FROM rs INTO @enterorg, @differentkind, @ownership, @propertyno, @serialno;                ")
				.append("     END;                                                                                                      ")
				.append(" CLOSE rs;                                                                                                     ")
				.append(" DEALLOCATE rs;                                                                                                ");
			
			//權利
			sql.append(" DECLARE rs CURSOR                                                                                       ")
				.append(" FOR SELECT a.enterorg,                                                                                  ")
				.append("            a.differencekind,                                                                            ")
				.append("            a.ownership,                                                                                 ")
				.append("            a.propertyno,                                                                                ")
				.append("            a.serialno                                                                                   ")
				.append("     FROM UNTVP_ADDPROOF a                                                                               ")
				.append("          JOIN UNTVP_REDUCEPROOF b ON a.enterorg = b.enterorg                                            ")
				.append("                                      AND a.differencekind = b.differencekind                            ")
				.append("                                      AND a.ownership = b.ownership                                      ")
				.append("                                      AND a.propertyno = b.propertyno                                    ")
				.append("                                      AND a.serialno = b.serialno                                        ")
				.append("     WHERE a.enterorg = ").append(Common.sqlChar(this.getEnterOrg()))
				.append("           AND b.caseno = ").append(Common.sqlChar(reduceCaseNo))
				.append("     ORDER BY a.enterorg,                                                                                ")
				.append("              a.ownership,                                                                               ")
				.append("              a.differencekind,                                                                          ")
				.append("              a.propertyno,                                                                              ")
				.append("              a.serialno;                                                                                ")
				.append(" OPEN rs;                                                                                                ")
				.append(" FETCH NEXT FROM rs INTO @enterorg, @differentkind, @ownership, @propertyno, @serialno;                  ")
				.append(" WHILE @@Fetch_Status = 0                                                                                ")
				.append("     BEGIN                                                                                               ")
				.append("         INSERT INTO UNTVP_ADDPROOF                                                                      ")
				.append("         (enterorg,                                                                                      ")
				.append("          ownership,                                                                                     ")
				.append("          caseno,                                                                                        ")
				.append("          casename,                                                                                      ")
				.append("          differencekind,                                                                                ")
				.append("          caseserialno,                                                                                  ")
				.append("          propertyno,                                                                                    ")
				.append("          serialno,                                                                                      ")
				.append("          propertyname1,                                                                                 ")
				.append("          writedate,                                                                                     ")
				.append("          writeunit,                                                                                     ")
				.append("          proofyear,                                                                                     ")
				.append("          proofdoc,                                                                                      ")
				.append("          proofno,                                                                                       ")
				.append("          manageno,                                                                                      ")
				.append("          summonsno,                                                                                     ")
				.append("          sourcekind,                                                                                    ")
				.append("          sourcedate,                                                                                    ")
				.append("          sourcedoc,                                                                                     ")
				.append("          buydate,                                                                                       ")
				.append("          originalnote,                                                                                  ")
				.append("          datastate,                                                                                     ")
				.append("          verify,                                                                                        ")
				.append("          propertykind,                                                                                  ")
				.append("          fundtype,                                                                                      ")
				.append("          accountingtitle,                                                                               ")
				.append("          securitymeat,                                                                                  ")
				.append("          securityname,                                                                                  ")
				.append("          securityaddr,                                                                                  ")
				.append("          securityitem,                                                                                  ")
				.append("          securitytime,                                                                                  ")
				.append("          securityorg,                                                                                   ")
				.append("          securitydoc,                                                                                   ")
				.append("          capitalstock,                                                                                  ")
				.append("          capital,                                                                                       ")
				.append("          originalamount,                                                                                ")
				.append("          originalbv,                                                                                    ")
				.append("          bookamount,                                                                                    ")
				.append("          bookvalue,                                                                                     ")
				.append("          originalkeepunit,                                                                              ")
				.append("          originalkeeper,                                                                                ")
				.append("          originaluseunit,                                                                               ")
				.append("          originaluser,                                                                                  ")
				.append("          originalusernote,                                                                              ")
				.append("          originalplace1,                                                                                ")
				.append("          originalplace,                                                                                 ")
				.append("          keepunit,                                                                                      ")
				.append("          keeper,                                                                                        ")
				.append("          useunit,                                                                                       ")
				.append("          userno,                                                                                        ")
				.append("          usernote,                                                                                      ")
				.append("          place1,                                                                                        ")
				.append("          place,                                                                                         ")
				.append("          oldpropertyno,                                                                                 ")
				.append("          oldserialno,                                                                                   ")
				.append("          notes,                                                                                         ")
				.append("          editid,                                                                                        ")
				.append("          editdate,                                                                                      ")
				.append("          edittime,                                                                                      ")
				.append("          summonsdate,                                                                                   ")
				.append("          oldlotno,                                                                                      ")
				.append("          otherpropertyunit,                                                                             ")
				.append("          othermaterial,                                                                                 ")
				.append("          reducecaseno                                                                                   ")
				.append("         )                                                                                               ")
				.append("                SELECT a.enterorg,                                                                       ")
				.append("                       a.ownership,                                                                      ")
				.append(Common.sqlChar(addCaseno)).append(",")
				.append("                       a.casename,                                                                       ")
				.append(Common.sqlChar(differencekind)).append(",")
				.append("                       @caseserialno AS caseserialno,                                                    ")
				.append("                       a.propertyno,                                                                     ")
				.append("                (                                                                                        ")
				.append("                    SELECT CASE                                                                          ")
				.append("                               WHEN MAX(d.serialno) IS NULL                                              ")
				.append("                               THEN RIGHT(REPLICATE('0', 7) + CAST('1' AS NVARCHAR), 7)                  ")
				.append("                               ELSE RIGHT(REPLICATE('0', 7) + CAST(MAX(d.serialno) + 1 AS NVARCHAR), 7)  ")
				.append("                           END                                                                           ")
				.append("                    FROM UNTVP_ADDPROOF d                                                                ")
				.append("                    WHERE a.enterorg = d.enterorg                                                        ")
				.append("                          AND a.ownership = d.ownership                                                  ")
				.append("                          AND d.differencekind = ").append(Common.sqlChar(differencekind))
				.append("                          AND a.propertyno = d.propertyno                                                ")
				.append("                ) AS serialno,                                                                           ")
				.append("                       a.propertyname1,                                                                  ")
				.append("                       a.writedate,                                                                      ")
				.append("                       a.writeunit,                                                                      ")
				.append(Common.sqlChar(Datetime.getYYY())).append(", ")
				.append("                       '財產增加',                                                                      										  ")
				.append(Common.sqlChar(addProofNo)).append(", ")
				.append("                       a.manageno,                                                                       ")
				.append("                       a.summonsno,                                                                      ")
				.append(Common.sqlChar(sourceKind)).append(", ")
				.append("                       b.adjustdate,                                                                     ")
				.append("                       a.sourcedoc,                                                                      ")
				.append("                       a.buydate,                                                                        ")
				.append("                       a.originalnote,                                                                   ")
				.append("                       1,                                                                                ")
				.append("                       'N',                                                                              ")
				.append("                       a.propertykind,                                                                   ")
				.append("                       a.fundtype,                                                                       ")
				.append("                       a.accountingtitle,                                                                ")
				.append("                       a.securitymeat,                                                                   ")
				.append("                       a.securityname,                                                                   ")
				.append("                       a.securityaddr,                                                                   ")
				.append("                       a.securityitem,                                                                   ")
				.append("                       a.securitytime,                                                                   ")
				.append("                       a.securityorg,                                                                    ")
				.append("                       a.securitydoc,                                                                    ")
				.append("                       a.capitalstock,                                                                   ")
				.append("                       a.capital,                                                                        ")
				.append("                       1,                                                                                ")
				.append("                       b.bookvalue,                                                                      ")
				.append("                       1,                                                                                ")
				.append("                       b.bookvalue,                                                                      ")
				.append("                       a.keepunit,                                                               ")
				.append("                       a.keeper,                                                                 ")
				.append("                       a.useunit,                                                                ")
				.append("                       a.userno,                                                                   ")
				.append("                       a.usernote,                                                               ")
				.append("                       a.place1,                                                                 ")
				.append("                       a.place,                                                                  ")
				.append("                       a.keepunit,                                                                       ")
				.append("                       a.keeper,                                                                         ")
				.append("                       a.useunit,                                                                        ")
				.append("                       a.userno,                                                                         ")
				.append("                       a.usernote,                                                                       ")
				.append("                       a.place1,                                                                         ")
				.append("                       a.place,                                                                          ")
				.append("                       a.propertyno,                                                                     ")
				.append("                       a.serialno,                                                                       ")
				.append(Common.sqlChar(notes)).append(", ")
				.append(Common.sqlChar(this.getUserID())).append(",")
				.append(Common.sqlChar(Datetime.getYYYYMMDD())).append(",")
				.append(Common.sqlChar(Datetime.getHHMMSS())).append(",")
				.append("                       a.summonsdate,                                                                    ")
				.append("                       a.oldlotno,                                                                       ")
				.append("                       a.otherpropertyunit,                                                              ")
				.append("                       a.othermaterial,                                                                  ")
				.append(Common.sqlChar(reduceCaseNo))
				.append("                FROM UNTVP_ADDPROOF a                                                                    ")
				.append("                     JOIN UNTVP_REDUCEPROOF b ON a.enterorg = b.enterorg                                 ")
				.append("                                                 AND a.ownership = b.ownership                           ")
				.append("                                                 AND a.differencekind = b.differencekind                 ")
				.append("                                                 AND a.propertyno = b.propertyno                         ")
				.append("                                                 AND a.serialno = b.serialno                             ")
				.append("                WHERE b.caseno = ").append(Common.sqlChar(reduceCaseNo))
				.append("                      AND a.enterorg = @enterorg                                                         ")
				.append("                      AND a.differencekind = @differentkind                                              ")
				.append("                      AND a.ownership = @ownership                                                       ")
				.append("                      AND a.propertyno = @propertyno                                                     ")
				.append("                      AND a.serialno = @serialno;                                                        ")
				.append("         SELECT @caseserialno = @caseserialno + 1;                                                       ")
				.append("         FETCH NEXT FROM rs INTO @enterorg, @differentkind, @ownership, @propertyno, @serialno;          ")
				.append("     END;                                                                                                ")
				.append(" CLOSE rs;                                                                                               ")
				.append(" DEALLOCATE rs;                                                                                          ");
			
			//證券
			sql.append(" DECLARE rs CURSOR                                                                                     ")
				.append(" FOR SELECT a.enterorg,                                                                                ")
				.append("            a.differencekind,                                                                          ")
				.append("            a.ownership,                                                                               ")
				.append("            a.propertyno,                                                                              ")
				.append("            a.serialno                                                                                 ")
				.append("     FROM UNTRT_ADDPROOF a                                                                             ")
				.append("          JOIN UNTRT_REDUCEPROOF b ON a.enterorg = b.enterorg                                          ")
				.append("                                      AND a.differencekind = b.differencekind                          ")
				.append("                                      AND a.ownership = b.ownership                                    ")
				.append("                                      AND a.propertyno = b.propertyno                                  ")
				.append("                                      AND a.serialno = b.serialno                                      ")
				.append("     WHERE a.enterorg = ").append(Common.sqlChar(this.getEnterOrg()))
				.append("           AND b.caseno = ").append(Common.sqlChar(reduceCaseNo))
				.append("     ORDER BY a.enterorg,                                                                              ")
				.append("              a.ownership,                                                                             ")
				.append("              a.differencekind,                                                                        ")
				.append("              a.propertyno,                                                                            ")
				.append("              a.serialno;                                                                              ")
				.append(" OPEN rs;                                                                                              ")
				.append(" FETCH NEXT FROM rs INTO @enterorg, @differentkind, @ownership, @propertyno, @serialno;                ")
				.append(" WHILE @@Fetch_Status = 0                                                                              ")
				.append("     BEGIN                                                                                             ")
				.append("         INSERT INTO UNTRT_ADDPROOF                                                                    ")
				.append("         (enterorg,                                                                                    ")
				.append("          ownership,                                                                                   ")
				.append("          caseno,                                                                                      ")
				.append("          casename,                                                                                    ")
				.append("          differencekind,                                                                              ")
				.append("          caseserialno,                                                                                ")
				.append("          propertyno,                                                                                  ")
				.append("          serialno,                                                                                    ")
				.append("          propertyname1,                                                                               ")
				.append("          writedate,                                                                                   ")
				.append("          writeunit,                                                                                   ")
				.append("          proofyear,                                                                                   ")
				.append("          proofdoc,                                                                                    ")
				.append("          proofno,                                                                                     ")
				.append("          manageno,                                                                                    ")
				.append("          summonsno,                                                                                   ")
				.append("          originalnote,                                                                                ")
				.append("          datastate,                                                                                   ")
				.append("          verify,                                                                                      ")
				.append("          propertykind,                                                                                ")
				.append("          fundtype,                                                                                    ")
				.append("          buydate,                                                                                     ")
				.append("          sourcekind,                                                                                  ")
				.append("          sourcedate,                                                                                  ")
				.append("          sourcedoc,                                                                                   ")
				.append("          meat,                                                                                        ")
				.append("          proofdoc1,                                                                                   ")
				.append("          originalbv,                                                                                  ")
				.append("          bookvalue,                                                                                   ")
				.append("          registercause,                                                                               ")
				.append("          registerdate,                                                                                ")
				.append("          setperiod,                                                                                   ")
				.append("          commonobligee,                                                                               ")
				.append("          setperson,                                                                                   ")
				.append("          paydate,                                                                                     ")
				.append("          interest,                                                                                    ")
				.append("          rent,                                                                                        ")
				.append("          notes1,                                                                                      ")
				.append("          originalkeepunit,                                                                            ")
				.append("          originalkeeper,                                                                              ")
				.append("          originaluseunit,                                                                             ")
				.append("          originaluser,                                                                                ")
				.append("          originalusernote,                                                                            ")
				.append("          originalplace1,                                                                              ")
				.append("          originalplace,                                                                               ")
				.append("          keepunit,                                                                                    ")
				.append("          keeper,                                                                                      ")
				.append("          useunit,                                                                                     ")
				.append("          userno,                                                                                      ")
				.append("          usernote,                                                                                    ")
				.append("          place1,                                                                                      ")
				.append("          place,                                                                                       ")
				.append("          oldpropertyno,                                                                               ")
				.append("          oldserialno,                                                                                 ")
				.append("          notes,                                                                                       ")
				.append("          editid,                                                                                      ")
				.append("          editdate,                                                                                    ")
				.append("          edittime,                                                                                    ")
				.append("          summonsdate,                                                                                 ")
				.append("          otherlimityear,                                                                              ")
				.append("          originallimityear,                                                                           ")
				.append("          oldlotno,                                                                                    ")
				.append("          otherpropertyunit,                                                                           ")
				.append("          othermaterial,                                                                               ")
				.append("          netvalue,                                                                                    ")
				.append("          originaldeprmethod,                                                                          ")
				.append("          originalbuildfeecb,                                                                          ")
				.append("          originaldeprunitcb,                                                                          ")
				.append("          originaldeprpark,                                                                            ")
				.append("          originaldeprunit,                                                                            ")
				.append("          originaldeprunit1,                                                                           ")
				.append("          originaldepraccounts,                                                                        ")
				.append("          originalscrapvalue,                                                                          ")
				.append("          originaldepramount,                                                                          ")
				.append("          originalaccumdepr,                                                                           ")
				.append("          originalapportionmonth,                                                                      ")
				.append("          originalmonthdepr,                                                                           ")
				.append("          originalmonthdepr1,                                                                          ")
				.append("          originalapportionendym,                                                                      ")
				.append("          deprmethod,                                                                                  ")
				.append("          buildfeecb,                                                                                  ")
				.append("          deprunitcb,                                                                                  ")
				.append("          deprpark,                                                                                    ")
				.append("          deprunit,                                                                                    ")
				.append("          deprunit1,                                                                                   ")
				.append("          depraccounts,                                                                                ")
				.append("          scrapvalue,                                                                                  ")
				.append("          depramount,                                                                                  ")
				.append("          accumdepr,                                                                                   ")
				.append("          apportionmonth,                                                                              ")
				.append("          monthdepr,                                                                                   ")
				.append("          monthdepr1,                                                                                  ")
				.append("          apportionendym,                                                                              ")
				.append("          nodeprset,                                                                                   ")
				.append("          escroworivalue,                                                                              ")
				.append("          escroworiaccumdepr,                                                                          ")
				.append("          originalaccumdepr1,                                                                          ")
				.append("          originalaccumdepr2,                                                                          ")
				.append("          valuable,                                                                                    ")
				.append("          reducecaseno                                                                                 ")
				.append("         )                                                                                             ")
				.append("                SELECT a.enterorg,                                                                     ")
				.append("                       a.ownership,                                                                    ")
				.append(Common.sqlChar(addCaseno)).append(",")
				.append("                       a.casename,                                                                       ")
				.append(Common.sqlChar(differencekind)).append(",")
				.append("                       @caseserialno AS caseserialno,                                                  ")
				.append("                       a.propertyno,                                                                   ")
				.append("                (                                                                                      ")
				.append("                    SELECT CASE                                                                        ")
				.append("                               WHEN MAX(d.serialno) IS NULL                                            ")
				.append("                               THEN RIGHT(REPLICATE('0', 7) + CAST('1' AS NVARCHAR), 7)                ")
				.append("                               ELSE RIGHT(REPLICATE('0', 7) + CAST(MAX(d.serialno) + 1 AS NVARCHAR), 7)")
				.append("                           END                                                                         ")
				.append("                    FROM UNTVP_ADDPROOF d                                                              ")
				.append("                    WHERE a.enterorg = d.enterorg                                                      ")
				.append("                          AND a.ownership = d.ownership                                                ")
				.append("                          AND d.differencekind = ").append(Common.sqlChar(differencekind))
				.append("                          AND a.propertyno = d.propertyno                                              ")
				.append("                ) AS serialno,                                                                         ")
				.append("                       a.propertyname1,                                                                ")
				.append("                       a.writedate,                                                                    ")
				.append("                       a.writeunit,                                                                    ")
				.append(Common.sqlChar(Datetime.getYYY())).append(", ")
				.append("                       '財產增加',                                                                      										")
				.append(Common.sqlChar(addProofNo)).append(", ")
				.append("                       a.manageno,                                                                     ")
				.append("                       a.summonsno,                                                                    ")
				.append("                       a.originalnote,                                                                 ")
				.append("                       1,                                                                              ")
				.append("                       'N',                                                                            ")
				.append("                       a.propertykind,                                                                 ")
				.append("                       a.fundtype,                                                                     ")
				.append("                       a.buydate,                                                                      ")
				.append(Common.sqlChar(sourceKind)).append(", ")
				.append("                       b.reducedate,                                                                             ")
				.append("                       a.sourcedoc,                                                                    ")
				.append("                       a.meat,                                                                         ")
				.append("                       a.proofdoc1,                                                                    ")
				.append("                       b.bookvalue,                                                                    ")
				.append("                       b.bookvalue,                                                                    ")
				.append("                       a.registercause,                                                                ")
				.append("                       a.registerdate,                                                                 ")
				.append("                       a.setperiod,                                                                    ")
				.append("                       a.commonobligee,                                                                ")
				.append("                       a.setperson,                                                                    ")
				.append("                       a.paydate,                                                                      ")
				.append("                       a.interest,                                                                     ")
				.append("                       a.rent,                                                                         ")
				.append("                       a.notes1,                                                                       ")
				.append("                       a.keepunit,                                                             ")
				.append("                       a.keeper,                                                               ")
				.append("                       a.useunit,                                                              ")
				.append("                       a.userno,                                                                 ")
				.append("                       a.usernote,                                                             ")
				.append("                       a.place1,                                                               ")
				.append("                       a.place,                                                                ")
				.append("                       a.keepunit,                                                                     ")
				.append("                       a.keeper,                                                                       ")
				.append("                       a.useunit,                                                                      ")
				.append("                       a.userno,                                                                       ")
				.append("                       a.usernote,                                                                     ")
				.append("                       a.place1,                                                                       ")
				.append("                       a.place,                                                                        ")
				.append("                       a.propertyno,                                                                   ")
				.append("                       a.serialno,                                                                     ")
				.append(Common.sqlChar(notes)).append(", ")
				.append(Common.sqlChar(this.getUserID())).append(",")
				.append(Common.sqlChar(Datetime.getYYYYMMDD())).append(",")
				.append(Common.sqlChar(Datetime.getHHMMSS())).append(",")
				.append("                       a.summonsdate,                                                                  ")
				.append("                       a.otherlimityear,                                                               ")
				.append("                       a.originallimityear,                                                            ")
				.append("                       a.oldlotno,                                                                     ")
				.append("                       a.otherpropertyunit,                                                            ")
				.append("                       a.othermaterial,                                                                ")
				.append("                       b.netvalue,                                                                     ")
				.append("                       b.olddeprmethod,                                                                ")
				.append("                       b.oldbuildfeecb,                                                                ")
				.append("                       b.olddeprunitcb,                                                                ")
				.append("                       b.olddeprpark,                                                                  ")
				.append("                       b.olddeprunit,                                                                  ")
				.append("                       b.olddeprunit1,                                                                 ")
				.append("                       b.olddepraccounts,                                                              ")
				.append("                       b.oldscrapvalue,                                                                ")
				.append("                       b.olddepramount,                                                                ")
				.append("                       b.oldaccumdepr,                                                                 ")
				.append("                       b.oldapportionmonth,                                                            ")
				.append("                       b.oldmonthdepr,                                                                 ")
				.append("                       b.oldmonthdepr1,                                                                ")
				.append("                       b.oldapportionendym,                                                            ")
				.append("                       b.olddeprmethod,                                                                ")
				.append("                       b.oldbuildfeecb,                                                                ")
				.append("                       b.olddeprunitcb,                                                                ")
				.append("                       b.olddeprpark,                                                                  ")
				.append("                       b.olddeprunit,                                                                  ")
				.append("                       b.olddeprunit1,                                                                 ")
				.append("                       b.olddepraccounts,                                                              ")
				.append("                       b.oldscrapvalue,                                                                ")
				.append("                       b.olddepramount,                                                                ")
				.append("                       b.oldaccumdepr,                                                                 ")
				.append("                       b.oldapportionmonth,                                                            ")
				.append("                       b.oldmonthdepr,                                                                 ")
				.append("                       b.oldmonthdepr1,                                                                ")
				.append("                       b.oldapportionendym,                                                            ")
				.append("                       a.nodeprset,                                                                    ")
				.append("                       a.escroworivalue,                                                               ")
				.append("                       a.escroworiaccumdepr,                                                           ")
				.append("                       b.oldaccumdepr,                                                                 ")
				.append("                       0,                                                                              ")
				.append("                       a.valuable,                                                                     ")
				.append(Common.sqlChar(reduceCaseNo))
				.append("                FROM UNTRT_ADDPROOF a                                                                  ")
				.append("                     JOIN UNTRT_REDUCEPROOF b ON a.enterorg = b.enterorg                               ")
				.append("                                                 AND a.ownership = b.ownership                         ")
				.append("                                                 AND a.differencekind = b.differencekind               ")
				.append("                                                 AND a.propertyno = b.propertyno                       ")
				.append("                                                 AND a.serialno = b.serialno                           ")
				.append("                WHERE b.caseno = ").append(Common.sqlChar(reduceCaseNo))
				.append("                      AND a.enterorg = @enterorg                                                       ")
				.append("                      AND a.differencekind = @differentkind                                            ")
				.append("                      AND a.ownership = @ownership                                                     ")
				.append("                      AND a.propertyno = @propertyno                                                   ")
				.append("                      AND a.serialno = @serialno;                                                      ")
				.append("         SELECT @caseserialno = @caseserialno + 1;                                                     ")
				.append("         FETCH NEXT FROM rs INTO @enterorg, @differentkind, @ownership, @propertyno, @serialno;        ")
				.append("     END;                                                                                              ")
				.append(" CLOSE rs;                                                                                             ")
				.append(" DEALLOCATE rs;                                                                                        ");
			
			if (sql.length() != 0) {
				db.exeSQL_NoChange(sql.toString());				
			}
		} catch(Exception e) {
			log._execLogError(e.getMessage());
		} finally {
			db.closeAll();
		}	
	}
	
	//問題單2299 公務轉基金 新增附屬資料
	private void transferAttachment(String addCaseno) throws Exception {
		Database db = null;
		StringBuilder sql = new StringBuilder();
		StringBuilder sql2 = new StringBuilder();
		String oldDifferencekind = "110";
		String newDifferencekind = "120";
		
		try {
			db = new Database();
			sql.append(" select * from (")
			   .append(" select enterorg, caseno, ownership, differencekind, propertyno, serialno, oldserialno, '' as lotno, oldlotno from UNTLA_LAND ")
			   .append(" union all")
			   .append(" select enterorg, caseno, ownership, differencekind, propertyno, serialno, oldserialno, '' as lotno, oldlotno from UNTRF_ATTACHMENT ")
			   .append(" union all")
			   .append(" select enterorg, caseno, ownership, differencekind, propertyno, serialno, oldserialno, '' as lotno, oldlotno from UNTBU_BUILDING ")
			   .append(" union all")
			   .append(" select enterorg, caseno, ownership, differencekind, propertyno, serialno, oldserialno, lotno, oldlotno from UNTMP_MOVABLEDETAIL ")
			   .append(" union all")
			   .append(" select enterorg, caseno, ownership, differencekind, propertyno, serialno, oldserialno, '' as lotno, oldlotno from UNTRT_ADDPROOF ")
			   .append(" union all")
			   .append(" select enterorg, caseno, ownership, differencekind, propertyno, serialno, oldserialno, '' as lotno, oldlotno from UNTVP_ADDPROOF) x ")
			   .append(" where x.caseno = ").append(Common.sqlChar(addCaseno)).append(" and x.enterorg = ").append(Common.sqlChar(this.getEnterOrg()));
			
			ResultSet rs = db.querySQL_NoChange(sql.toString());
			
			while(rs.next()) {
				//土地
				if ("1".equals(rs.getString("propertyno").substring(0, 1))) {
					//土地管理檔
					sql2.append(" INSERT INTO UNTLA_MANAGE ")
						.append(" SELECT enterorg,              							")
						.append("        ownership,             							")
						.append(Common.sqlChar(newDifferencekind)).append(", ")
						.append("        propertyno,            							")
						.append(Common.sqlChar(rs.getString("serialno"))).append(", ")
						.append("        serialno1,             							")
						.append("        useunit,               							")
						.append("        useunit1,              							")
						.append("        userelation,           							")
						.append("        usedates,              							")
						.append("        usedatee,              							")
						.append("        usearea,               							")
						.append("        notes1,                							")
						.append("        notes,                 							")
						.append(Common.sqlChar(this.getUserID())).append(",")
						.append(Common.sqlChar(Datetime.getYYYYMMDD())).append(",")
						.append(Common.sqlChar(Datetime.getHHMMSS()))
						.append(" FROM UNTLA_MANAGE             							")
						.append(" WHERE enterorg = ").append(Common.sqlChar(rs.getString("enterorg")))
						.append("       AND ownership = ").append(Common.sqlChar(rs.getString("ownership")))
						.append("       AND differencekind = ").append(Common.sqlChar(oldDifferencekind))
						.append("       AND propertyno = ").append(Common.sqlChar(rs.getString("propertyno")))
						.append("       AND serialno = ").append(Common.sqlChar(rs.getString("oldserialno"))).append(";");
					
					//土地地上物檔
					sql2.append(" INSERT INTO UNTLA_ATTACHMENT ")
						.append(" SELECT enterorg,                               ")
						.append("        ownership,                              ")
						.append(Common.sqlChar(newDifferencekind)).append(", ")
						.append("        propertyno,            				 ")
						.append(Common.sqlChar(rs.getString("serialno"))).append(", ")
						.append("        serialno1,                              ")
						.append("        ownership1,                             ")
						.append("        owner,                                  ")
						.append("        manageorg,                              ")
						.append("        state,                                  ")
						.append("        purpose,                                ")
						.append("        usearea,                                ")
						.append("        mergeusesign,                           ")
						.append("        notes1,                                 ")
						.append("        notes,                                  ")
						.append(Common.sqlChar(this.getUserID())).append(",")
						.append(Common.sqlChar(Datetime.getYYYYMMDD())).append(",")
						.append(Common.sqlChar(Datetime.getHHMMSS()))
						.append(" FROM UNTLA_ATTACHMENT                          ")
						.append(" WHERE enterorg = ").append(Common.sqlChar(rs.getString("enterorg")))
						.append("       AND ownership = ").append(Common.sqlChar(rs.getString("ownership")))
						.append("       AND differencekind = ").append(Common.sqlChar(oldDifferencekind))
						.append("       AND propertyno = ").append(Common.sqlChar(rs.getString("propertyno")))
						.append("       AND serialno = ").append(Common.sqlChar(rs.getString("oldserialno"))).append(";");
					
					//土地公告地價檔
					sql2.append(" INSERT INTO UNTLA_VALUE ")
						.append(" SELECT enterorg,                       ")
						.append("        ownership,                      ")
						.append(Common.sqlChar(newDifferencekind)).append(", ")
						.append("        propertyno,            		 ")
						.append(Common.sqlChar(rs.getString("serialno"))).append(", ")
						.append("        bulletindate,                   ")
						.append("        valueunit,                      ")
						.append("        suitdates,                      ")
						.append("        suitdatee,                      ")
						.append("        notes,                          ")
						.append(Common.sqlChar(this.getUserID())).append(",")
						.append(Common.sqlChar(Datetime.getYYYYMMDD())).append(",")
						.append(Common.sqlChar(Datetime.getHHMMSS()))
						.append(" FROM UNTLA_VALUE                       ")
						.append(" WHERE enterorg = ").append(Common.sqlChar(rs.getString("enterorg")))
						.append("       AND ownership = ").append(Common.sqlChar(rs.getString("ownership")))
						.append("       AND differencekind = ").append(Common.sqlChar(oldDifferencekind))
						.append("       AND propertyno = ").append(Common.sqlChar(rs.getString("propertyno")))
						.append("       AND serialno = ").append(Common.sqlChar(rs.getString("oldserialno"))).append(";");
					
					//土地公告現值檔
					sql2.append(" INSERT INTO UNTLA_PRICE ")
						.append(" SELECT enterorg,                           ")
						.append("        ownership,                          ")
						.append(Common.sqlChar(newDifferencekind)).append(", ")
						.append("        propertyno,            		 	 ")
						.append(Common.sqlChar(rs.getString("serialno"))).append(", ")
						.append("        bulletindate,                       ")
						.append("        priceunit,                          ")
						.append("        suitdates,                          ")
						.append("        suitdatee,                          ")
						.append("        notes,                              ")
						.append(Common.sqlChar(this.getUserID())).append(",")
						.append(Common.sqlChar(Datetime.getYYYYMMDD())).append(",")
						.append(Common.sqlChar(Datetime.getHHMMSS()))
						.append(" FROM UNTLA_PRICE                           ")
						.append(" WHERE enterorg = ").append(Common.sqlChar(rs.getString("enterorg")))
						.append("       AND ownership = ").append(Common.sqlChar(rs.getString("ownership")))
						.append("       AND differencekind = ").append(Common.sqlChar(oldDifferencekind))
						.append("       AND propertyno = ").append(Common.sqlChar(rs.getString("propertyno")))
						.append("       AND serialno = ").append(Common.sqlChar(rs.getString("oldserialno"))).append(";");
					
					//土地他項權利檔
					sql2.append(" INSERT INTO UNTLA_RIGHT ")
						.append("SELECT enterorg,                                   ")
						.append("       ownership,                                  ")
						.append(Common.sqlChar(newDifferencekind)).append(", ")
						.append("        propertyno,            		 			")
						.append(Common.sqlChar(rs.getString("serialno"))).append(", ")
						.append("       serialno1,                                  ")
						.append("       rightno,                                    ")
						.append("       rightvalue,                                 ")
						.append("       rightdates,                                 ")
						.append("       rightdatee,                                 ")
						.append("       holdnume,                                   ")
						.append("       holddeno,                                   ")
						.append("       proofdoc,                                   ")
						.append("       obligor,                                    ")
						.append("       obligee,                                    ")
						.append("       notes,                                      ")
						.append(Common.sqlChar(this.getUserID())).append(",")
						.append(Common.sqlChar(Datetime.getYYYYMMDD())).append(",")
						.append(Common.sqlChar(Datetime.getHHMMSS()))
						.append("FROM UNTLA_RIGHT                                   ")
						.append(" WHERE enterorg = ").append(Common.sqlChar(rs.getString("enterorg")))
						.append("       AND ownership = ").append(Common.sqlChar(rs.getString("ownership")))
						.append("       AND differencekind = ").append(Common.sqlChar(oldDifferencekind))
						.append("       AND propertyno = ").append(Common.sqlChar(rs.getString("propertyno")))
						.append("       AND serialno = ").append(Common.sqlChar(rs.getString("oldserialno"))).append(";");
						
					//土地使用權檔
					sql2.append(" INSERT INTO UNTLA_USE ")
						.append(" SELECT enterorg,                               ")
						.append("        ownership,                              ")
						.append(Common.sqlChar(newDifferencekind)).append(", ")
						.append("        propertyno,            		 		 ")
						.append(Common.sqlChar(rs.getString("serialno"))).append(", ")
						.append("        serialno1,                              ")
						.append("        usedates,                               ")
						.append("        usedatee,                               ")
						.append("        proofdoc,                               ")
						.append("        stuff,                                  ")
						.append("        floor1,                                 ")
						.append("        floor2,                                 ")
						.append("        basearea,                               ")
						.append("        uselandarea,                            ")
						.append("        buildingarea,                           ")
						.append("        uselandprice,                           ")
						.append("        purpose,                                ")
						.append("        oldstuff,                               ")
						.append("        notes,                                  ")
						.append(Common.sqlChar(this.getUserID())).append(",")
						.append(Common.sqlChar(Datetime.getYYYYMMDD())).append(",")
						.append(Common.sqlChar(Datetime.getHHMMSS()))
						.append(" FROM UNTLA_USE                                 ")
						.append(" WHERE enterorg = ").append(Common.sqlChar(rs.getString("enterorg")))
						.append("       AND ownership = ").append(Common.sqlChar(rs.getString("ownership")))
						.append("       AND differencekind = ").append(Common.sqlChar(oldDifferencekind))
						.append("       AND propertyno = ").append(Common.sqlChar(rs.getString("propertyno")))
						.append("       AND serialno = ").append(Common.sqlChar(rs.getString("oldserialno"))).append(";");
				}
				
				//土改
				if ("111".equals(rs.getString("propertyno").substring(0, 1))) {
					//土地改良物基地檔
					sql2.append(" INSERT INTO UNTRF_BASE                           ")
						.append("        SELECT enterorg,                          ")
						.append("               ownership,                         ")
						.append(Common.sqlChar(newDifferencekind)).append(", ")
						.append("        		propertyno,            		 	   ")
						.append(Common.sqlChar(rs.getString("serialno"))).append(", ")
						.append("               serialno1,                         ")
						.append("               signno,                            ")
						.append("               owner,                             ")
						.append("               manageorg,                         ")
						.append("               area,                              ")
						.append("               notes,                             ")
						.append(Common.sqlChar(this.getUserID())).append(",")
						.append(Common.sqlChar(Datetime.getYYYYMMDD())).append(",")
						.append(Common.sqlChar(Datetime.getHHMMSS()))
						.append("        FROM UNTRF_BASE                           ")
						.append(" 		WHERE enterorg = ").append(Common.sqlChar(rs.getString("enterorg")))
						.append("       	AND ownership = ").append(Common.sqlChar(rs.getString("ownership")))
						.append("       	AND differencekind = ").append(Common.sqlChar(oldDifferencekind))
						.append("       	AND propertyno = ").append(Common.sqlChar(rs.getString("propertyno")))
						.append("       	AND serialno = ").append(Common.sqlChar(rs.getString("oldserialno"))).append(";");
					
					//土地改良物管理檔
					sql2.append(" INSERT INTO UNTRF_MANAGE                                ")
						.append("        SELECT enterorg,                                 ")
						.append("               ownership,                                ")
						.append(Common.sqlChar(newDifferencekind)).append(", ")
						.append("        		propertyno,            		 		      ")
						.append(Common.sqlChar(rs.getString("serialno"))).append(", ")
						.append("               serialno1,                                ")
						.append("               useunit,                                  ")
						.append("               useunit1,                                 ")
						.append("               userelation,                              ")
						.append("               usedates,                                 ")
						.append("               usedatee,                                 ")
						.append("               measure,                                  ")
						.append("               notes1,                                   ")
						.append("               notes,                                    ")
						.append(Common.sqlChar(this.getUserID())).append(",")
						.append(Common.sqlChar(Datetime.getYYYYMMDD())).append(",")
						.append(Common.sqlChar(Datetime.getHHMMSS()))
						.append("        FROM UNTRF_MANAGE                                ")
						.append(" 		WHERE enterorg = ").append(Common.sqlChar(rs.getString("enterorg")))
						.append("       	AND ownership = ").append(Common.sqlChar(rs.getString("ownership")))
						.append("       	AND differencekind = ").append(Common.sqlChar(oldDifferencekind))
						.append("       	AND propertyno = ").append(Common.sqlChar(rs.getString("propertyno")))
						.append("       	AND serialno = ").append(Common.sqlChar(rs.getString("oldserialno"))).append(";");
				}
				
				//建物
				if ("2".equals(rs.getString("propertyno").substring(0, 1))) {
					//建物管理檔
					sql2.append(" INSERT INTO UNTBU_MANAGE                                 ")
						.append("        SELECT enterorg,                                  ")
						.append("               ownership,                                 ")
						.append(Common.sqlChar(newDifferencekind)).append(", ")
						.append("        		propertyno,            		 		       ")
						.append(Common.sqlChar(rs.getString("serialno"))).append(", ")
						.append("               serialno1,                                 ")
						.append("               useunit,                                   ")
						.append("               useunit1,                                  ")
						.append("               userelation,                               ")
						.append("               usedates,                                  ")
						.append("               usedatee,                                  ")
						.append("               usearea,                                   ")
						.append("               notes1,                                    ")
						.append("               notes,                                     ")
						.append(Common.sqlChar(this.getUserID())).append(",")
						.append(Common.sqlChar(Datetime.getYYYYMMDD())).append(",")
						.append(Common.sqlChar(Datetime.getHHMMSS()))
						.append("        FROM UNTBU_MANAGE                                 ")
						.append(" 		WHERE enterorg = ").append(Common.sqlChar(rs.getString("enterorg")))
						.append("       	AND ownership = ").append(Common.sqlChar(rs.getString("ownership")))
						.append("       	AND differencekind = ").append(Common.sqlChar(oldDifferencekind))
						.append("       	AND propertyno = ").append(Common.sqlChar(rs.getString("propertyno")))
						.append("       	AND serialno = ").append(Common.sqlChar(rs.getString("oldserialno"))).append(";");
					
					//建物樓層檔
					sql2.append(" INSERT INTO UNTBU_FLOOR                                 ")
						.append("        SELECT enterorg,                                 ")
						.append("               ownership,                                ")
						.append(Common.sqlChar(newDifferencekind)).append(", ")
						.append("        		propertyno,            		 		      ")
						.append(Common.sqlChar(rs.getString("serialno"))).append(", ")
						.append("               serialno1,                                ")
						.append("               floor,                                    ")
						.append("               purpose,                                  ")
						.append("               area,                                     ")
						.append("               notes,                                    ")
						.append(Common.sqlChar(this.getUserID())).append(",")
						.append(Common.sqlChar(Datetime.getYYYYMMDD())).append(",")
						.append(Common.sqlChar(Datetime.getHHMMSS()))
						.append("        FROM UNTBU_FLOOR                                 ")
						.append(" 		WHERE enterorg = ").append(Common.sqlChar(rs.getString("enterorg")))
						.append("       	AND ownership = ").append(Common.sqlChar(rs.getString("ownership")))
						.append("       	AND differencekind = ").append(Common.sqlChar(oldDifferencekind))
						.append("       	AND propertyno = ").append(Common.sqlChar(rs.getString("propertyno")))
						.append("       	AND serialno = ").append(Common.sqlChar(rs.getString("oldserialno"))).append(";");
					
					//建物附屬建物檔
					sql2.append(" INSERT INTO UNTBU_ATTACHMENT1                              ")
						.append("        SELECT enterorg,                                    ")
						.append("               ownership,                                   ")
						.append(Common.sqlChar(newDifferencekind)).append(", ")
						.append("        		propertyno,            		 		         ")
						.append(Common.sqlChar(rs.getString("serialno"))).append(", ")
						.append("               serialno1,                                   ")
						.append("               attachment,                                  ")
						.append("               purpose,                                     ")
						.append("               stuff,                                       ")
						.append("               area,                                        ")
						.append("               notes,                                       ")
						.append(Common.sqlChar(this.getUserID())).append(",")
						.append(Common.sqlChar(Datetime.getYYYYMMDD())).append(",")
						.append(Common.sqlChar(Datetime.getHHMMSS()))
						.append("        FROM UNTBU_ATTACHMENT1                              ")
						.append(" 		WHERE enterorg = ").append(Common.sqlChar(rs.getString("enterorg")))
						.append("       	AND ownership = ").append(Common.sqlChar(rs.getString("ownership")))
						.append("       	AND differencekind = ").append(Common.sqlChar(oldDifferencekind))
						.append("       	AND propertyno = ").append(Common.sqlChar(rs.getString("propertyno")))
						.append("       	AND serialno = ").append(Common.sqlChar(rs.getString("oldserialno"))).append(";");
					
					//建物基地檔
					sql2.append(" INSERT INTO UNTBU_BASE                                  ")
						.append("        SELECT enterorg,                                 ")
						.append("               ownership,                                ")
						.append(Common.sqlChar(newDifferencekind)).append(", ")
						.append("        		propertyno,            		 		         ")
						.append(Common.sqlChar(rs.getString("serialno"))).append(", ")
						.append("               serialno1,                                ")
						.append("               signno,                                   ")
						.append("               ownership1,                               ")
						.append("               owner,                                    ")
						.append("               manageorg,                                ")
						.append("               area,                                     ")
						.append("               holdnume,                                 ")
						.append("               holddeno,                                 ")
						.append("               holdarea,                                 ")
						.append("               useseparate,                              ")
						.append("               usekind,                                  ")
						.append("               field,                                    ")
						.append("               landrule,                                 ")
						.append("               notes1,                                   ")
						.append("               notes,                                    ")
						.append(Common.sqlChar(this.getUserID())).append(",")
						.append(Common.sqlChar(Datetime.getYYYYMMDD())).append(",")
						.append(Common.sqlChar(Datetime.getHHMMSS()))
						.append("        FROM UNTBU_BASE                                  ")
						.append(" 		WHERE enterorg = ").append(Common.sqlChar(rs.getString("enterorg")))
						.append("       	AND ownership = ").append(Common.sqlChar(rs.getString("ownership")))
						.append("       	AND differencekind = ").append(Common.sqlChar(oldDifferencekind))
						.append("       	AND propertyno = ").append(Common.sqlChar(rs.getString("propertyno")))
						.append("       	AND serialno = ").append(Common.sqlChar(rs.getString("oldserialno"))).append(";");
					
					//建物共同使用檔
					sql2.append(" INSERT INTO UNTBU_COMMONUSE                             ")
						.append("        SELECT enterorg,                                 ")
						.append("               ownership,                                ")
						.append(Common.sqlChar(newDifferencekind)).append(", ")
						.append("        		propertyno,            		 		         ")
						.append(Common.sqlChar(rs.getString("serialno"))).append(", ")
						.append("               serialno1,                                ")
						.append("               signno,                                   ")
						.append("               area,                                     ")
						.append("               holdnume,                                 ")
						.append("               holddeno,                                 ")
						.append("               holdarea,                                 ")
						.append("               notes,                                    ")
						.append(Common.sqlChar(this.getUserID())).append(",")
						.append(Common.sqlChar(Datetime.getYYYYMMDD())).append(",")
						.append(Common.sqlChar(Datetime.getHHMMSS()))
						.append("        FROM UNTBU_COMMONUSE                             ")
						.append(" 		WHERE enterorg = ").append(Common.sqlChar(rs.getString("enterorg")))
						.append("       	AND ownership = ").append(Common.sqlChar(rs.getString("ownership")))
						.append("       	AND differencekind = ").append(Common.sqlChar(oldDifferencekind))
						.append("       	AND propertyno = ").append(Common.sqlChar(rs.getString("propertyno")))
						.append("       	AND serialno = ").append(Common.sqlChar(rs.getString("oldserialno"))).append(";");
					
					//建物評定現值檔
					sql2.append(" INSERT INTO UNTBU_PRICE                            ")
						.append("        SELECT enterorg,                            ")
						.append("               ownership,                           ")
						.append(Common.sqlChar(newDifferencekind)).append(", ")
						.append("        		propertyno,            		 		 ")
						.append(Common.sqlChar(rs.getString("serialno"))).append(", ")
						.append("               judgmentdate,                        ")
						.append("               judgmentvalue,                       ")
						.append("               notes,                               ")
						.append(Common.sqlChar(this.getUserID())).append(",")
						.append(Common.sqlChar(Datetime.getYYYYMMDD())).append(",")
						.append(Common.sqlChar(Datetime.getHHMMSS()))
						.append("        FROM UNTBU_PRICE                            ")
						.append(" 		WHERE enterorg = ").append(Common.sqlChar(rs.getString("enterorg")))
						.append("       	AND ownership = ").append(Common.sqlChar(rs.getString("ownership")))
						.append("       	AND differencekind = ").append(Common.sqlChar(oldDifferencekind))
						.append("       	AND propertyno = ").append(Common.sqlChar(rs.getString("propertyno")))
						.append("       	AND serialno = ").append(Common.sqlChar(rs.getString("oldserialno"))).append(";");
					
				}
				
				//動產
				if ("3".equals(rs.getString("propertyno").substring(0, 1)) || "4".equals(rs.getString("propertyno").substring(0, 1)) || "5".equals(rs.getString("propertyno").substring(0, 1))) {
					//動產批號附屬設備檔
					sql2.append(" INSERT INTO UNTMP_ATTACHMENT1                          ")
						.append("        SELECT enterorg,                                ")
						.append("               ownership,                               ")
						.append(Common.sqlChar(newDifferencekind)).append(", ")
						.append("        		propertyno,            		 		     ")
						.append(Common.sqlChar(rs.getString("lotno"))).append(", ")
						.append("               serialno1,                               ")
						.append("               equipmentname,                           ")
						.append("               buydate,                                 ")
						.append("               equipmentunit,                           ")
						.append("               equipmentamount,                         ")
						.append("               unitprice,                               ")
						.append("               totalvalue,                              ")
						.append("               sourcekind,                              ")
						.append("               nameplate,                               ")
						.append("               specification,                           ")
						.append("               notes,                                   ")
						.append(Common.sqlChar(this.getUserID())).append(",")
						.append(Common.sqlChar(Datetime.getYYYYMMDD())).append(",")
						.append(Common.sqlChar(Datetime.getHHMMSS()))
						.append("        FROM UNTMP_ATTACHMENT1                          ")
						.append(" 		WHERE enterorg = ").append(Common.sqlChar(rs.getString("enterorg")))
						.append("       	AND ownership = ").append(Common.sqlChar(rs.getString("ownership")))
						.append("       	AND differencekind = ").append(Common.sqlChar(oldDifferencekind))
						.append("       	AND propertyno = ").append(Common.sqlChar(rs.getString("propertyno")))
						.append("       	AND lotno = ").append(Common.sqlChar(rs.getString("oldlotno"))).append(";");
					
					//動產批號明細附屬設備檔
					sql2.append(" INSERT INTO UNTMP_ATTACHMENT2                           ")
						.append("        SELECT enterorg,                                 ")
						.append("               ownership,                                ")
						.append(Common.sqlChar(newDifferencekind)).append(", ")
						.append("        		propertyno,            		 		      ")
						.append(Common.sqlChar(rs.getString("lotno"))).append(", ")
						.append(Common.sqlChar(rs.getString("serialno"))).append(", ")
						.append("               serialno1,                                ")
						.append("               equipmentname,                            ")
						.append("               buydate,                                  ")
						.append("               equipmentunit,                            ")
						.append("               equipmentamount,                          ")
						.append("               unitprice,                                ")
						.append("               totalvalue,                               ")
						.append("               datastate,                                ")
						.append("               sourcekind,                               ")
						.append("               nameplate,                                ")
						.append("               specification,                            ")
						.append("               notes,                                    ")
						.append(Common.sqlChar(this.getUserID())).append(",")
						.append(Common.sqlChar(Datetime.getYYYYMMDD())).append(",")
						.append(Common.sqlChar(Datetime.getHHMMSS()))
						.append("        FROM UNTMP_ATTACHMENT2                           ")
						.append(" 		WHERE enterorg = ").append(Common.sqlChar(rs.getString("enterorg")))
						.append("       	AND ownership = ").append(Common.sqlChar(rs.getString("ownership")))
						.append("       	AND differencekind = ").append(Common.sqlChar(oldDifferencekind))
						.append("       	AND propertyno = ").append(Common.sqlChar(rs.getString("propertyno")))
						.append("       	AND serialno = ").append(Common.sqlChar(rs.getString("oldserialno"))).append(";");
					
				}
				
				//暫無權利附屬table
//				if ("8".equals(rs.getString("propertyno").substring(0, 1))) {
//					
//				}
				
				//證券
				if ("9".equals(rs.getString("propertyno").substring(0, 1))) {
					//有價證券股份檔
					sql2.append(" INSERT INTO UNTVP_SHARE                          ")
						.append("        SELECT enterorg,                          ")
						.append("               ownership,                         ")
						.append(Common.sqlChar(newDifferencekind)).append(", ")
						.append("        		propertyno,            		 	   ")
						.append(Common.sqlChar(rs.getString("serialno"))).append(", ")
						.append("               serialno1,                         ")
						.append("               bookamount,                        ")
						.append("               bookunitvalue,                     ")
						.append("               bookvalue,                         ")
						.append("               proofs,                            ")
						.append("               proofe,                            ")
						.append("               notes,                             ")
						.append(Common.sqlChar(this.getUserID())).append(",")
						.append(Common.sqlChar(Datetime.getYYYYMMDD())).append(",")
						.append(Common.sqlChar(Datetime.getHHMMSS()))
						.append("        FROM UNTVP_SHARE                          ")
						.append(" 		WHERE enterorg = ").append(Common.sqlChar(rs.getString("enterorg")))
						.append("       	AND ownership = ").append(Common.sqlChar(rs.getString("ownership")))
						.append("       	AND differencekind = ").append(Common.sqlChar(oldDifferencekind))
						.append("       	AND propertyno = ").append(Common.sqlChar(rs.getString("propertyno")))
						.append("       	AND serialno = ").append(Common.sqlChar(rs.getString("oldserialno"))).append(";");
					
					//有價證券資本額更動記錄檔
					sql2.append(" INSERT INTO UNTVP_CAPITAL                               ")
						.append("        SELECT enterorg,                                 ")
						.append("               ownership,                                ")
						.append(Common.sqlChar(newDifferencekind)).append(", ")
						.append("        		propertyno,            		 	   		  ")
						.append(Common.sqlChar(rs.getString("serialno"))).append(", ")
						.append("               serialno1,                                ")
						.append("               changedate,                               ")
						.append("               changecase,                               ")
						.append("               changepirce,                              ")
						.append("               notes1,                                   ")
						.append("               notes,                                    ")
						.append(Common.sqlChar(this.getUserID())).append(",")
						.append(Common.sqlChar(Datetime.getYYYYMMDD())).append(",")
						.append(Common.sqlChar(Datetime.getHHMMSS()))
						.append("        FROM UNTVP_CAPITAL                               ")
						.append(" 		WHERE enterorg = ").append(Common.sqlChar(rs.getString("enterorg")))
						.append("       	AND ownership = ").append(Common.sqlChar(rs.getString("ownership")))
						.append("       	AND differencekind = ").append(Common.sqlChar(oldDifferencekind))
						.append("       	AND propertyno = ").append(Common.sqlChar(rs.getString("propertyno")))
						.append("       	AND serialno = ").append(Common.sqlChar(rs.getString("oldserialno"))).append(";");
				}
			}
			if (sql2.length() != 0) {
				db.exeSQL_NoChange(sql2.toString());				
			}
		} catch(Exception e) {
			log._execLogError(e.getMessage());
		} finally {
			db.closeAll();
		}		
	}
	
	//================================================================
	
	String mergeDivision;
	public String getMergeDivision(){ return checkGet(mergeDivision); }
	public void setMergeDivision(String s){ mergeDivision=checkSet(s); }
	
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

	int sumBookValue ;
	int sumBookAmount ;
	String changeNo="";

	
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
	String unusableCause;
	String demolishDate;
	String reduceDate;
	String approveOrg;
	String approveDate;
	String approveDoc;
	String verify;
	String notes;
	String verifyError;

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
	public String getUnusableCause(){ return checkGet(unusableCause); }
	public void setUnusableCause(String s){ unusableCause=checkSet(s); }
	public String getDemolishDate(){ return checkGet(demolishDate); }
	public void setDemolishDate(String s){ demolishDate=checkSet(s); }
	public String getReduceDate(){ return checkGet(reduceDate); }
	public void setReduceDate(String s){ reduceDate=checkSet(s); }
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

	public String getVerifyError(){ return checkGet(verifyError); }
	public void setVerifyError(String s){ verifyError=checkSet(s); }

	String closing;
	public String getClosing(){ return checkGet(closing); }
	public void setClosing(String s){ closing=checkSet(s); }


	String checkFlag;
	public String getCheckFlag() {return checkGet(checkFlag);}
	public void setCheckFlag(String checkFlag) {this.checkFlag = checkSet(checkFlag);}
	
	
	private String differenceKind;
	private String proofYear;
	private String editID;
	private String editDate;
	private String editTime;
	public String getDifferenceKind() {return checkGet(differenceKind);}
	public void setDifferenceKind(String differenceKind) {this.differenceKind = checkSet(differenceKind);}
	public String getProofYear() {return checkGet(proofYear);}
	public void setProofYear(String proofYear) {this.proofYear = checkSet(proofYear);}
	public String getEditID() {return checkGet(editID);}
	public void setEditID(String editID) {this.editID = checkSet(editID);}
	public String getEditDate() {return checkGet(editDate);}
	public void setEditDate(String editDate) {this.editDate = checkSet(editDate);}
	public String getEditTime() {return checkGet(editTime);}
	public void setEditTime(String editTime) {this.editTime = checkSet(editTime);}
	
	private String reVerify;
	public String getReVerify() {return checkGet(reVerify);}
	public void setReVerify(String reVerify) {this.reVerify = checkSet(reVerify);}
	
	private String reduceDateTemp;
	public String getReduceDateTemp() {return checkGet(reduceDateTemp);}
	public void setReduceDateTemp(String reduceDateTemp) {this.reduceDateTemp = checkSet(reduceDateTemp);}
	
	private String cause;
	private String cause1;
	private String causeName;
	public String getCause() {return checkGet(cause);}
	public void setCause(String cause) {this.cause = checkSet(cause);}
	public String getCause1() {return checkGet(cause1);}
	public void setCause1(String cause1) {this.cause1 = checkSet(cause1);}
	public String getCauseName() {return checkGet(causeName);}
	public void setCauseName(String causeName) {this.causeName = checkSet(causeName);}
	

	private String summonsDate;
	public String getSummonsDate() {return checkGet(summonsDate);}
	public void setSummonsDate(String summonsDate) {this.summonsDate = checkSet(summonsDate);}	
	
	private String preenterdate;
	public String getPreenterdate() { return checkGet(preenterdate); }
	public void setPreenterdate(String preenterdate) { this.preenterdate = checkSet(preenterdate); }
	private String oldpreenterdate;
	public String getOldpreenterdate() { return checkGet(oldpreenterdate); }
	public void setOldpreenterdate(String oldpreenterdate) { this.oldpreenterdate = checkSet(oldpreenterdate); }
	
	private String addcaseno;
	public String getAddcaseno() { return checkGet(addcaseno); }
	public void setAddcaseno(String addcaseno) { this.addcaseno = checkSet(addcaseno); }
}