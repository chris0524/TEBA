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

import util.Common;
import util.Database;
import util.Datetime;

public class UNTCH004F02 extends UNTCH004Q{

	//====================================================================
	@Override
	protected util.SuperBean2[] _checkPropertyNoType(){
		
		util.SuperBean2[] sb = new util.SuperBean2[2];
		
		String checkStr = new unt.ch.UNTCH_Tools()._checkPropertyNoType(this.getPropertyNo());
		
		if(_table_LA.equals(checkStr)){				sb[0] = new unt.la.UNTLA013F();
		}else if(_table_BU.equals(checkStr)){		sb[0] = new unt.bu.UNTBU016F();
		}else if(_table_RF.equals(checkStr)){		sb[0] = new unt.rf.UNTRF009F();
		}else if(_table_MP.equals(checkStr)){		sb[0] = new unt.mp.UNTMP015F();
		}else if(_table_RT.equals(checkStr)){		sb[0] = new UNTCH004F02_RT(this.getClass().getSimpleName());
		}else if(_table_VP.equals(checkStr)){		sb[0] = new UNTCH004F02_VP();
		}
		
		return sb;
	}
	
	/**
	 * 檢查是否為合併分割案
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
	
	//====================================================================
		
	//預設值
	@Override
	protected void _setDefaultValue(){		
		UNTCH_Tools ut = new UNTCH_Tools();
		
		String checkStr = ut._checkPropertyNoType(this.getPropertyNo());
		if(_table_LA.equals(checkStr)){
			if("".equals(checkGet(this.getLaSignNo3())) || "".equals(checkGet(this.getLaSignNo4())) || "".equals(checkGet(this.getLaSignNo5()))){				
			}else{
				signNo = this.getLaSignNo3().substring(0,7) + checkGet(this.getLaSignNo4()) + checkGet(this.getLaSignNo5());
			}			
			this.setSignNo(signNo);
			this.setSignNo1(this.getLaSignNo1());
			this.setSignNo2(this.getLaSignNo2());
			this.setSignNo3(this.getLaSignNo3());
			this.setSignNo4(this.getLaSignNo4());
			this.setSignNo5(this.getLaSignNo5());
			
			this.setArea(this.getLaArea());			
			this.setHoldNume(this.getLaHoldNume());
			this.setHoldDeno(this.getLaHoldDeno());
			this.setHoldArea(this.getLaHoldArea());
			
		}else if(_table_BU.equals(checkStr)){
			if("".equals(checkGet(this.getBuSignNo3())) || "".equals(checkGet(this.getBuSignNo4())) || "".equals(checkGet(this.getBuSignNo5()))){				
			}else{
				signNo = this.getBuSignNo3().substring(0,7) + checkGet(this.getBuSignNo4()) + checkGet(this.getBuSignNo5());
			}
			this.setSignNo(signNo);
			this.setSignNo1(this.getBuSignNo1());
			this.setSignNo2(this.getBuSignNo2());
			this.setSignNo3(this.getBuSignNo3());
			this.setSignNo4(this.getBuSignNo4());
			this.setSignNo5(this.getBuSignNo5());
			
			this.setCArea(this.getBuCArea());
			this.setSArea(this.getBuSArea());
			this.setArea(this.getBuArea());
			this.setHoldNume(this.getBuHoldNume());
			this.setHoldDeno(this.getBuHoldDeno());
			this.setHoldArea(this.getBuHoldArea());
			
			calUseYearAndUseMonth();
			
		}else if(_table_RF.equals(checkStr)){
			calUseYearAndUseMonth();
		}else if(_table_MP.equals(checkStr)){
			calUseYearAndUseMonth();
		} else if (_table_RT.equals(checkStr)) {
			this.calUseYearAndUseMonth();
		}
		
		if("Y".equals(getOldBuildFeeCB())){
		}else{		setOldBuildFeeCB("N");
		}
		if("Y".equals(getOldDeprUnitCB())){
		}else{		setOldDeprUnitCB("N");
		}
		if("Y".equals(getNewBuildFeeCB())){
		}else{		setNewBuildFeeCB("N");
		}
		if("Y".equals(getNewDeprUnitCB())){
		}else{		setNewDeprUnitCB("N");
		}
		this.setTaxCredit("N");
		this.setVerify("N");
		
		this.setProofYear(ut._queryData("proofyear")._from("UNTLA_REDUCEPROOF")._with(" and enterorg = '" + getEnterOrg() + "' and ownership = '" + getOwnership() + "' and differencekind = '" + getDifferenceKind() + "' and caseno = '" + getCaseNo() + "'")._toString());
		this.setProofDoc(ut._queryData("proofdoc")._from("UNTLA_REDUCEPROOF")._with(" and enterorg = '" + getEnterOrg() + "' and ownership = '" + getOwnership() + "' and differencekind = '" + getDifferenceKind() + "' and caseno = '" + getCaseNo() + "'")._toString());
		this.setProofNo(ut._queryData("proofno")._from("UNTLA_REDUCEPROOF")._with(" and enterorg = '" + getEnterOrg() + "' and ownership = '" + getOwnership() + "' and differencekind = '" + getDifferenceKind() + "' and caseno = '" + getCaseNo() + "'")._toString());
		this.setSummonsDate(ut._queryData("summonsdate")._from("UNTLA_REDUCEPROOF")._with(" and enterorg = '" + getEnterOrg() + "' and ownership = '" + getOwnership() + "' and differencekind = '" + getDifferenceKind() + "' and caseno = '" + getCaseNo() + "'")._toString());
		this.setWriteDate(ut._queryData("writeDate")._from("UNTLA_REDUCEPROOF")._with(" and enterorg = '" + getEnterOrg() + "' and ownership = '" + getOwnership() + "' and differencekind = '" + getDifferenceKind() + "' and caseno = '" + getCaseNo() + "'")._toString());
		this.setWriteUnit(ut._queryData("writeUnit")._from("UNTLA_REDUCEPROOF")._with(" and enterorg = '" + getEnterOrg() + "' and ownership = '" + getOwnership() + "' and differencekind = '" + getDifferenceKind() + "' and caseno = '" + getCaseNo() + "'")._toString());
		
		if("".equals(Common.get(this.getCaseSerialNo()))){			
			DataStructor ds = new DataStructor();
			ds.enterOrg = this.getEnterOrg();
			ds.ownership = this.getOwnership();
			ds.caseNo = this.getCaseNo();
			ds.differenceKind = this.getDifferenceKind();
			
			String[] tables = new String[]{
							"UNTLA_REDUCEDETAIL",
							"UNTBU_REDUCEDETAIL",
							"UNTRF_REDUCEDETAIL",
							"UNTMP_REDUCEDETAIL",
							"UNTVP_REDUCEPROOF",
							"UNTRT_REDUCEPROOF"
							};
			
			this.setCaseSerialNo(ut._getNewCaseSerialNo(ds,tables));			
		}
	}
	
		private void calUseYearAndUseMonth(){
			String buyDate = getBuyDate();
			if(("".equals(getUseYear()) || "".equals(getUseMonth())) && buyDate.length() == 7){
				// 問題單1626: 已使用年月計算方式為: 系統年月-購置年月
				String today = Datetime.getYYYMM();
				int months = Datetime.BetweenTwoMonth(buyDate.substring(0, 5), today);
				int useYear = months/12;
				int useMonth = months%12;
				
				this.setUseYear(String.valueOf(useYear));
				this.setUseMonth(String.valueOf(useMonth));
			}		
		}
	
	//====================================================================	
	@Override
	protected String _getMaxSerialNo(){
		String tableName = "";
		
		String checkStr = new unt.ch.UNTCH_Tools()._checkPropertyNoType(this.getPropertyNo());
		if(_table_RT.equals(checkStr)){				tableName = "UNTRT_ReduceProof";
		}else if(_table_VP.equals(checkStr)){		tableName = "UNTVP_ReduceProof";
		}
		
		String sql = "select max(serialNo) from " + tableName + 
						" where 1=1" +
						" and enterOrg = " + Common.sqlChar(this.getEnterOrg()) +
						" and ownership = " + Common.sqlChar(this.getOwnership()) +
						" and caseNo = " + Common.sqlChar(this.getCaseNo()) +
						" and propertyNo = " + Common.sqlChar(this.getPropertyNo()) ;

		return new TDlib_Simple.com.src.DBServerTools()._execSQL_asString(sql);
	}

			
	
	public void _execQueryOneforDetail(){
		util.SuperBean2 sb = _checkPropertyNoType()[0];	
		
		//=======================================
		sb.setEnterOrg(this.getEnterOrg());
		sb.setOwnership(this.getOwnership());
		sb.setCaseNo(this.getCaseNo());
		sb.setDifferenceKind(this.getDifferenceKind());
		sb.setPropertyNo(this.getPropertyNo());
		sb.setSerialNo(this.getSerialNo());
		//=======================================
			
		try{
			Object obj = sb.queryOne();	
			new UNTCH_Tools()._setParameter(obj,this);
			
			String checkStr = new unt.ch.UNTCH_Tools()._checkPropertyNoType(this.getPropertyNo());
			if(_table_LA.equals(checkStr)){			
				if("".equals(checkGet(this.getSignNo()))){				
				}else{			
					this.setLaSignNo1(this.getSignNo().substring(0,1) + "000000");
					this.setLaSignNo2(this.getSignNo().substring(0,3) + "0000");
					this.setLaSignNo3(this.getSignNo().substring(0,7));
					this.setLaSignNo4(this.getSignNo().substring(7,11));
					this.setLaSignNo5(this.getSignNo().substring(11));
							
					this.setLaArea(this.getArea());
					this.setLaHoldNume(this.getHoldNume());
					this.setLaHoldDeno(this.getHoldDeno());
					this.setLaHoldArea(this.getHoldArea());
				}
				
			}else if(_table_BU.equals(checkStr)){
				if("".equals(checkGet(this.getSignNo()))){				
				}else{
					this.setBuSignNo1(this.getSignNo().substring(0,1) + "000000");
					this.setBuSignNo2(this.getSignNo().substring(0,3) + "0000");
					this.setBuSignNo3(this.getSignNo().substring(0,7));
					this.setBuSignNo4(this.getSignNo().substring(7,12));
					this.setBuSignNo5(this.getSignNo().substring(12));
					
					this.setBuArea(this.getArea());
					this.setBuCArea(this.getCArea());
					this.setBuSArea(this.getSArea());
					this.setBuHoldNume(this.getHoldNume());
					this.setBuHoldDeno(this.getHoldDeno());
					this.setBuHoldArea(this.getHoldArea());
				}
			
			}
			this.setPlace1Name(new UNTCH_Tools().getPlace1Name(this.enterOrg, this.place1));
			
			setStateQueryOneSuccess();
			
			//#region 715
			if ("".equals(this.getQueryone_caseNo()) && !"".equals(this.getCaseNo())) {
				this.setQueryone_caseNo(this.getCaseNo());
			}
			
			if ("".equals(this.getQueryone_differenceKind()) && !"".equals(this.getDifferenceKind())) {
				this.setQueryone_differenceKind(this.getDifferenceKind());
			}
			
			if ("".equals(this.getQueryone_enterOrg()) && !"".equals(this.getEnterOrg())) {
				this.setQueryone_enterOrg(this.getEnterOrg());
			}
			
			if ("".equals(this.getQueryone_ownership()) && !"".equals(this.getOwnership())) {
				this.setQueryone_ownership(this.getOwnership());
			}
			//#end
			
			queryProofNo();
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			sb = null;
		}	
	}
		
		private void queryProofNo(){
			String sql = "select proofno from UNTLA_REDUCEPROOF where 1=1" +
							" and enterorg = " + Common.sqlChar(this.getEnterOrg()) +
							" and ownership = " + Common.sqlChar(this.getOwnership()) +
							" and differencekind = " + Common.sqlChar(this.getDifferenceKind()) +
							" and caseno = " + Common.sqlChar(this.getCaseNo());
			
			Database db = null;
			ResultSet rs = null;
			try{
				db = new Database();
				rs = db.querySQL(sql);
				if(rs.next()){
					this.setProofNo(rs.getString(1));
				}
			}catch(Exception e){
				e.printStackTrace();
			}finally{
				db.closeAll();
			}
		}
	
	/**
	 * <br>
	 * <br>目的：依查詢欄位查詢多筆資料
	 * <br>參數：無
	 * <br>傳回：傳回ArrayList
	*/
	
	public ArrayList execQueryAll() throws Exception{
		Database db = new Database();
		ArrayList objList=new ArrayList();
		
		try {
			StringBuffer sbSQL = new StringBuffer();
			
			sbSQL.append("select").append(
								" a.enterorg, ").append(
								" a.ownership, ").append(
								" a.caseno, ").append(
								" a.caseSerialNo, ").append(
								" a.differenceKind, ").append(										
								" a.propertyNo, ").append(
								" a.serialNo, ").append(
								" (select codename from SYSCA_CODE z where z.codekindid = 'CAA' and z.codecon1 in (2,4) and z.codeid = a.cause) as cause, ").append(
								" a.signNo as laSignNo, ").append(
								" null as buSignNo, ").append(
								" (select keepername from UNTMP_KEEPER z where z.enterorg = a.enterorg and z.keeperno = a.keeper) as keeper, ").append(
								" (select unitname from UNTMP_KEEPUNIT z where z.enterorg = a.enterorg and z.unitno = a.keepunit) as unit,").append(
								" '1' as bookamount,").append(
								" a.bookvalue,").append(
								" a.bookNotes,").append(	
								" '' as specification,").append(
								" '' as nameplate ").append(
						" from UNTLA_REDUCEDETAIL a ").append(
						" left join UNTLA_REDUCEPROOF b on a.enterorg=b.enterorg and a.ownership=b.ownership and a.caseno=b.caseno ").append(
						" where 1=1 ").append(
								getQueryCondition("LA")).append(
						" union ").append(								
						" select").append(
								" a.enterorg, ").append(
								" a.ownership, ").append(
								" a.caseno, ").append(
								" a.caseSerialNo, ").append(
								" a.differenceKind, ").append(
								" a.propertyNo, ").append(
								" a.serialNo, ").append(
								" (select codename from SYSCA_CODE z where z.codekindid = 'CAA' and z.codecon1 in (2,4) and z.codeid = a.cause) as cause, ").append(
								" null as laSignNo, ").append(
								" a.signNo as buSignNo, ").append(
								" (select keepername from UNTMP_KEEPER z where z.enterorg = a.enterorg and z.keeperno = a.keeper) as keeper, ").append(
								" (select unitname from UNTMP_KEEPUNIT z where z.enterorg = a.enterorg and z.unitno = a.keepunit) as unit,").append(
								" '1' as bookamount,").append(
								" a.bookvalue,").append(
								" a.bookNotes,").append(
								" '' as specification,").append(
								" '' as nameplate ").append(		
							" from UNTBU_REDUCEDETAIL a ").append(
							" left join UNTBU_REDUCEPROOF b on a.enterorg=b.enterorg and a.ownership=b.ownership and a.caseno=b.caseno ").append(
							" where 1=1 ").append(		
									getQueryCondition("BU")).append(
						" union ").append(
						" select").append(
								" a.enterorg, ").append(
								" a.ownership, ").append(
								" a.caseno, ").append(
								" a.caseSerialNo, ").append(
								" a.differenceKind, ").append(
								" a.propertyNo, ").append(
								" a.serialNo, ").append(
								" (select codename from SYSCA_CODE z where z.codekindid = 'CAA' and z.codecon1 in (2,4) and z.codeid = a.cause) as cause, ").append(
								" null as laSignNo, ").append(
								" null as buSignNo, ").append(
								" (select keepername from UNTMP_KEEPER z where z.enterorg = a.enterorg and z.keeperno = a.keeper) as keeper, ").append(
								" (select unitname from UNTMP_KEEPUNIT z where z.enterorg = a.enterorg and z.unitno = a.keepunit) as unit,").append(
								" '1' as bookamount,").append(
								" a.bookvalue,").append(
								" a.bookNotes,").append(
								" '' as specification,").append(
								" '' as nameplate ").append(		
							" from UNTRF_REDUCEDETAIL a ").append(
							" left join UNTRF_REDUCEPROOF b on a.enterorg=b.enterorg and a.ownership=b.ownership and a.caseno=b.caseno ").append(
							" where 1=1 ").append(				
									getQueryCondition("RF")).append(
						" union ").append(
						" select").append(
								" a.enterorg, ").append(
								" a.ownership, ").append(
								" a.caseno, ").append(
								" a.caseSerialNo, ").append(
								" a.differenceKind, ").append(
								" a.propertyNo, ").append(
								" a.serialNo, ").append(
								" (select codename from SYSCA_CODE z where z.codekindid = 'CAA' and z.codecon1 in (2,4) and z.codeid = a.cause) as cause, ").append(
								" null as laSignNo, ").append(
								" null as buSignNo, ").append(
								" (select keepername from UNTMP_KEEPER z where z.enterorg = a.enterorg and z.keeperno = a.keeper) as keeper, ").append(
								" (select unitname from UNTMP_KEEPUNIT z where z.enterorg = a.enterorg and z.unitno = a.keepunit) as unit,").append(
								" a.adjustbookamount as amount,").append(
								" a.adjustbookvalue as bookvalue,").append(
								" a.bookNotes,").append(		
								" isnull(a.specification,'') as specification,").append(
								" isnull(a.nameplate,'') as nameplate ").append(		
							" from UNTMP_REDUCEDETAIL a ").append(
							" left join UNTMP_REDUCEPROOF b on a.enterorg=b.enterorg and a.ownership=b.ownership and a.caseno=b.caseno ").append(
							" where 1=1 ").append(			
									getQueryCondition("MP")).append(
						" union ").append(
						" select").append(
								" a.enterorg, ").append(
								" a.ownership, ").append(
								" a.caseno, ").append(
								" a.caseSerialNo, ").append(
								" a.differenceKind, ").append(
								" a.propertyNo, ").append(
								" a.serialNo, ").append(
								" (select codename from SYSCA_CODE z where z.codekindid = 'CAA' and z.codecon1 in (2,4) and z.codeid = a.cause) as cause, ").append(
								" null as laSignNo, ").append(
								" null as buSignNo, ").append(
								" (select keepername from UNTMP_KEEPER z where z.enterorg = a.enterorg and z.keeperno = a.keeper) as keeper, ").append(
								" (select unitname from UNTMP_KEEPUNIT z where z.enterorg = a.enterorg and z.unitno = a.keepunit) as unit,").append(
								" bookamount as bookamount,").append(
								" a.bookvalue,").append(
								" a.bookNotes,").append(
								" '' as specification,").append(
								" '' as nameplate ").append(		
							" from UNTVP_REDUCEPROOF a where 1=1").append(
									getQueryCondition("VP")).append(
						" union ").append(
						" select").append(
								" a.enterorg, ").append(
								" a.ownership, ").append(
								" a.caseno, ").append(
								" a.caseSerialNo, ").append(
								" a.differenceKind, ").append(
								" a.propertyNo, ").append(
								" a.serialNo, ").append(
								" (select codename from SYSCA_CODE z where z.codekindid = 'CAA' and z.codecon1 in (2,4) and z.codeid = a.cause) as cause, ").append(		
								" null as laSignNo, ").append(
								" null as buSignNo, ").append(
								" (select keepername from UNTMP_KEEPER z where z.enterorg = a.enterorg and z.keeperno = a.keeper) as keeper, ").append(
								" (select unitname from UNTMP_KEEPUNIT z where z.enterorg = a.enterorg and z.unitno = a.keepunit) as unit,").append(
								" '1' as bookamount,").append(
								" a.bookvalue,").append(
								" a.bookNotes,").append(
								" '' as specification,").append(
								" '' as nameplate ").append(		
							" from UNTRT_REDUCEPROOF a where 1=1").append(
									getQueryCondition("RT")).append(
							"");
			
			ResultSet rs = db.querySQL(sbSQL.toString(),true);
			UNTCH_Tools uctls = new UNTCH_Tools();
			processCurrentPageAttribute(rs);			
			if (getTotalRecord() > 0) {
				int count = getRecordStart();
				int end = getRecordEnd();
				do {
					if (count > end)
						break;
				String rowArray[]=new String[15];
				
				rowArray[0] = checkGet(rs.getString("enterorg"));
				rowArray[1] = checkGet(rs.getString("ownership"));
				rowArray[2] = checkGet(rs.getString("caseno"));
				rowArray[3] = checkGet(rs.getString("caseSerialNo"));
				rowArray[4] = checkGet(rs.getString("differenceKind"));				
				rowArray[5] = checkGet(rs.getString("propertyNo")); 		
				rowArray[6] = checkGet(rs.getString("serialNo"));
				
				rowArray[7] = uctls._getPropertyNoName(rs.getString("enterorg"),checkGet(rs.getString("propertyNo")));
				
				String signNoLa = rs.getString("laSignNo");
				String signNoBu = rs.getString("buSignNo");
				
				if("".equals(checkGet(signNoLa)) || signNoLa.length() < 15){				
				}else{
					rowArray[8] = uctls._getSignNoName(signNoLa.substring(0,7)) + signNoLa.substring(7,11) + "-" + signNoLa.substring(11) + "地號";
				}
				if("".equals(checkGet(signNoBu)) || signNoBu.length() < 15){				
				}else{
					rowArray[8] = uctls._getSignNoName(signNoBu.substring(0,7)) + signNoBu.substring(7,12) + "-" + signNoBu.substring(12) + "建號";
				}
				
				if("".equals(checkGet(signNoLa)) && "".equals(checkGet(signNoBu))){
					rowArray[8] = checkGet(rs.getString("specification")) + "\n" + checkGet(rs.getString("nameplate"));
				}
				
				rowArray[9] = checkGet(rs.getString("cause"));
				rowArray[10] = checkGet(rs.getString("keeper"));
				rowArray[11] = checkGet(rs.getString("unit"));
				rowArray[12] = checkGet(rs.getString("bookamount"));
				rowArray[13] = checkGet(rs.getString("bookvalue"));
				rowArray[14] = checkGet(rs.getString("bookNotes"));
				
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
	
	//==============================================================
	private String getQueryCondition(String table){
		StringBuilder result = new StringBuilder();
		
		if("detail".equals(getQuerySelect())){
			
		}else{
			if (!"".equals(getEnterOrg())) {
				result.append(" and a.enterOrg = ").append(Common.sqlChar(getEnterOrg()));
			}
			if (!"".equals(getOwnership())) {
				result.append(" and a.ownership = ").append(Common.sqlChar(getOwnership()));
			}
			if (!"".equals(getCaseNo())) {
				result.append(" and a.caseNo = ").append(Common.sqlChar(getCaseNo()));
			}
			if (!"".equals(getDifferenceKind())) {
				result.append(" and a.differenceKind = ").append(Common.sqlChar(getDifferenceKind()));
			}
		}
		
		if (!"".equals(getQ_enterOrg())) {
			result.append(" and a.enterOrg = ").append(Common.sqlChar(getQ_enterOrg()));
		}
		if (!"".equals(getQ_ownership())) {
			result.append(" and a.ownership = ").append(Common.sqlChar(getQ_ownership()));
		}
		if (!"".equals(getQ_caseNoS())) {
			result.append(" and a.caseNo >= ").append(Common.sqlChar(getQ_caseNoS()));
		}
		if (!"".equals(getQ_caseNoE())) {
			result.append(" and a.caseNo <= ").append(Common.sqlChar(getQ_caseNoE()));
		}			
		if (!"".equals(getQ_differenceKind())) {
			result.append(" and a.differenceKind = ").append(Common.sqlChar(getQ_differenceKind()));
		}
		if (!"".equals(getQ_propertyNoS())){
			result.append(" and a.propertyNo >= ").append(Common.sqlChar(getQ_propertyNoS()));
		}
		if (!"".equals(getQ_propertyNoE())){
			result.append(" and a.propertyNo <= ").append(Common.sqlChar(getQ_propertyNoE()));
		}
		if (!"".equals(getQ_serialNoS())){
				result.append(" and a.serialNo >= ").append(Common.sqlChar(getQ_serialNoS()));
		}
		if (!"".equals(getQ_serialNoE())){
			result.append(" and a.serialNo <= ").append(Common.sqlChar(getQ_serialNoE()));
		}
		if (!"".equals(getQ_approveOrg())){
			if("LA".equals(table) || "BU".equals(table) || "RF".equals(table) || "MP".equals(table) ){
				result.append(" and b.approveOrg = ").append(Common.sqlChar(getQ_approveOrg()));
			}else{
				result.append(" and a.approveOrg = ").append(Common.sqlChar(getQ_approveOrg()));
			}
		}		
		if (!"".equals(getQ_propertyName1())){
			result.append(" and a.propertyName1 like ").append(Common.sqlChar("%" + getQ_propertyName1() + "%"));
		}	
		
		if (!"".equals(getQ_keepUnit())){
			result.append(" and a.keepUnit = ").append(Common.sqlChar(getQ_keepUnit()));			
		}
		if (!"".equals(getQ_keeper())){
			result.append(" and a.keeper = ").append(Common.sqlChar(getQ_keeper()));			
		}
		if (!"".equals(getQ_useUnit())){
			result.append(" and a.useUnit = ").append(Common.sqlChar(getQ_useUnit()));			
		}
		if (!"".equals(getQ_userNo())){
			result.append(" and a.userNo = ").append(Common.sqlChar(getQ_userNo()));
		}
		if (!"".equals(getQ_userNote())){
			result.append(" and a.userNote like ").append(Common.sqlChar("%" + getQ_userNote() + "%"));
		}
		if (!"".equals(getQ_place())){
			result.append(" and a.place1 = ").append(Common.sqlChar(getQ_place()));
		}
		if (!"".equals(getQ_placeNote())){
			result.append(" and a.place like ").append(Common.sqlChar("%" + getQ_placeNote() + "%"));
		}
		if (!"".equals(getQ_propertyKind())){
			result.append(" and a.propertyKind = ").append(Common.sqlChar(getQ_propertyKind()));
		}
		if (!"".equals(getQ_valuable())){
			if(!"VP".equals(table) && !"RT".equals(table) ){
				result.append(" and a.valuable = ").append(Common.sqlChar(getQ_valuable()));
			}
		}
		if (!"".equals(getQ_taxCredit())){
			if(!"MP".equals(table) && !"VP".equals(table) && !"RT".equals(table) ){
				result.append(" and a.taxCredit = ").append(Common.sqlChar(getQ_taxCredit()));
			}
		}
		if (!"".equals(getQ_cause())){
			result.append(" and a.cause = ").append(Common.sqlChar(getQ_cause()));
		}
		
		
		String q_signLaNo = "";
			
		if (!"".equals(Common.get(getQ_signLaNo1()))){
			q_signLaNo = getQ_signLaNo1().substring(0,1)+"______";
		}
		if (!"".equals(getQ_signLaNo2())){
			q_signLaNo = getQ_signLaNo2().substring(0,3)+"____";			
		}
		if (!"".equals(getQ_signLaNo3())){
			q_signLaNo = getQ_signLaNo3();
		}

			
		String q_signBuNo = "";
		if (!"".equals(getQ_signBuNo1())){
			q_signBuNo = getQ_signBuNo1().substring(0,1)+"______";
		}
		if (!"".equals(getQ_signBuNo2())){
			q_signBuNo = getQ_signBuNo2().substring(0,3)+"____";
		}
		if (!"".equals(getQ_signBuNo3())){
			q_signBuNo = getQ_signBuNo3();
		}
		

		if (!"".equals(q_signLaNo) || !"".equals(q_signBuNo)){
			if (!"".equals(q_signLaNo) && "LA".equals(table)){
				result.append(" and a.signno like '").append(q_signLaNo).append("%'");
				
				if (!"".equals(getQ_signLaNo4S())){
					result.append(" and substring(a.signno,8,4) >= '").append(getQ_signLaNo4S()).append("'");				
				}	
				if (!"".equals(getQ_signLaNo4E())){
					result.append(" and substring(a.signno,8,4) <= '").append(getQ_signLaNo4E()).append("'");				
				}
				if (!"".equals(getQ_signLaNo5S())){
					result.append(" and substring(a.signno,12,4) >= '").append(getQ_signLaNo5S()).append("'");				
				}	
				if (!"".equals(getQ_signLaNo5E())){
					result.append(" and substring(a.signno,12,4) <= '").append(getQ_signLaNo5E()).append("'");				
				}
				
			}else if (!"".equals(q_signBuNo) && "BU".equals(table)){
				result.append(" and a.signno like '").append(q_signBuNo).append("%'");
				
				if (!"".equals(getQ_signBuNo4S())){
					result.append(" and substring(a.signno,8,5) >= '").append(getQ_signBuNo4S()).append("'");				
				}	
				if (!"".equals(getQ_signBuNo4E())){
					result.append(" and substring(a.signno,8,5) <= '").append(getQ_signBuNo4E()).append("'");				
				}
				if (!"".equals(getQ_signBuNo5S())){
					result.append(" and substring(a.signno,13,3) >= '").append(getQ_signBuNo5S()).append("'");				
				}	
				if (!"".equals(getQ_signBuNo5E())){
					result.append(" and substring(a.signno,13,3) <= '").append(getQ_signBuNo5E()).append("'");				
				}
				
			}else{
				result.append(" and 1 != 1");
			}
				
		}
		
		if ("1".equals(this.getRoleid())){
			if("LA".equals(table) || "BU".equals(table) || "RF".equals(table) || "MP".equals(table) ){
				result.append(" and (a.keeper = ").append(Common.sqlChar(this.getKeeperno()));
				result.append(" or b.editid = ").append(Common.sqlChar(this.getUserID()));
			}else{
				result.append(" and (a.keeper = ").append(Common.sqlChar(this.getKeeperno()));
				result.append(" or a.editid = ").append(Common.sqlChar(this.getUserID()));
			}
			result.append(" )");			
			
		}else if ("2".equals(this.getRoleid())){
			if("LA".equals(table) || "BU".equals(table) || "RF".equals(table) || "MP".equals(table) ){
//				result.append(" and (a.keepunit = ").append(Common.sqlChar(this.getUnitID()));
				result.append(" and ( a.keeper = ").append(Common.sqlChar(this.getKeeperno()));
				result.append(" or b.editid = ").append(Common.sqlChar(this.getUserID()));
			}else{
//				result.append(" and (a.keepunit = ").append(Common.sqlChar(this.getUnitID()));
				result.append(" and ( a.keeper = ").append(Common.sqlChar(this.getKeeperno()));
				result.append(" or a.editid = ").append(Common.sqlChar(this.getUserID()));
			}
			result.append(" )");
			
		}else if ("3".equals(this.getRoleid())){
			
		}
		
//		if("Y".equals(getIsAdminManager())){
//			
//		}else{
//			result += " and enterorg = '" + this.getOrganID() + "'";
//		}
		
		return result.toString();
	}
	//==============================================================

	
	private String bookUnit;
	private String netUnit;
	private String useSeparate;
	private String useKind;
	private String field;
	private String bulletinDate;
	private String valueUnit;
	private String useState1;
	private String mergeDivision;
	private String mergeDivisionBatch;
	
	public String getBookUnit() {return checkGet(bookUnit);}
	public void setBookUnit(String bookUnit) {this.bookUnit = checkSet(bookUnit);}
	public String getNetUnit() {return checkGet(netUnit);}
	public void setNetUnit(String netUnit) {this.netUnit = checkSet(netUnit);}
	public String getUseSeparate() {return checkGet(useSeparate);}
	public void setUseSeparate(String useSeparate) {this.useSeparate = checkSet(useSeparate);}
	public String getUseKind() {return checkGet(useKind);}
	public void setUseKind(String useKind) {this.useKind = checkSet(useKind);}
	public String getField() {return checkGet(field);}
	public void setField(String field) {this.field = checkSet(field);}
	public String getBulletinDate() {return checkGet(bulletinDate);}
	public void setBulletinDate(String bulletinDate) {this.bulletinDate = checkSet(bulletinDate);}
	public String getValueUnit() {return checkGet(valueUnit);}
	public void setValueUnit(String valueUnit) {this.valueUnit = checkSet(valueUnit);}
	public String getUseState1() {return checkGet(useState1);}
	public void setUseState1(String useState1) {this.useState1 = checkSet(useState1);}
	public String getMergeDivision() {return checkGet(mergeDivision);}
	public void setMergeDivision(String mergeDivision) {this.mergeDivision = checkSet(mergeDivision);}
	public String getMergeDivisionBatch() {return checkGet(mergeDivisionBatch);}
	public void setMergeDivisionBatch(String mergeDivisionBatch) {this.mergeDivisionBatch = checkSet(mergeDivisionBatch);}
					

	private String lotNo;
	private String otherPropertyUnit;
	private String otherMaterial;
	private String oldBookAmount;
	private String oldBookUnit;
	private String oldBookValue;
	private String oldNetUnit;
	private String oldNetValue;
	private String adjustBookAmount;
	private String adjustBookUnit;
	private String adjustBookValue;
	private String adjustAccumDepr;
	private String adjustNetUnit;
	private String adjustNetValue;
	private String newBookAmount;
	private String newBookUnit;
	private String newBookValue;
	private String newNetUnit;
	private String newNetValue;
	private String articleName;
	private String nameplate;
	private String specification;
	private String licensePlate;
	private String returnPlace;
	private String originalUnit;
	
	public String getLotNo() {return checkGet(lotNo);}
	public void setLotNo(String lotNo) {this.lotNo = checkSet(lotNo);}
	public String getOtherPropertyUnit() {return checkGet(otherPropertyUnit);}
	public void setOtherPropertyUnit(String otherPropertyUnit) {this.otherPropertyUnit = checkSet(otherPropertyUnit);}
	public String getOtherMaterial() {return checkGet(otherMaterial);}
	public void setOtherMaterial(String otherMaterial) {this.otherMaterial = checkSet(otherMaterial);}
	public String getOldBookAmount() {return checkGet(oldBookAmount);}
	public void setOldBookAmount(String oldBookAmount) {this.oldBookAmount = checkSet(oldBookAmount);}
	public String getOldBookUnit() {return checkGet(oldBookUnit);}
	public void setOldBookUnit(String oldBookUnit) {this.oldBookUnit = checkSet(oldBookUnit);}
	public String getOldBookValue() {return checkGet(oldBookValue);}
	public void setOldBookValue(String oldBookValue) {this.oldBookValue = checkSet(oldBookValue);}
	public String getOldNetUnit() {return checkGet(oldNetUnit);}
	public void setOldNetUnit(String oldNetUnit) {this.oldNetUnit = checkSet(oldNetUnit);}
	public String getOldNetValue() {return checkGet(oldNetValue);}
	public void setOldNetValue(String oldNetValue) {this.oldNetValue = checkSet(oldNetValue);}
	public String getAdjustBookAmount() {return checkGet(adjustBookAmount);}
	public void setAdjustBookAmount(String adjustBookAmount) {this.adjustBookAmount = checkSet(adjustBookAmount);}
	public String getAdjustBookUnit() {return checkGet(adjustBookUnit);}
	public void setAdjustBookUnit(String adjustBookUnit) {this.adjustBookUnit = checkSet(adjustBookUnit);}
	public String getAdjustBookValue() {return checkGet(adjustBookValue);}
	public void setAdjustBookValue(String adjustBookValue) {this.adjustBookValue = checkSet(adjustBookValue);}
	public String getAdjustAccumDepr() {return checkGet(adjustAccumDepr);}
	public void setAdjustAccumDepr(String adjustAccumDepr) {this.adjustAccumDepr = checkSet(adjustAccumDepr);}
	public String getAdjustNetUnit() {return checkGet(adjustNetUnit);}
	public void setAdjustNetUnit(String adjustNetUnit) {this.adjustNetUnit = checkSet(adjustNetUnit);}
	public String getAdjustNetValue() {return checkGet(adjustNetValue);}
	public void setAdjustNetValue(String adjustNetValue) {this.adjustNetValue = checkSet(adjustNetValue);}
	public String getNewBookAmount() {return checkGet(newBookAmount);}
	public void setNewBookAmount(String newBookAmount) {this.newBookAmount = checkSet(newBookAmount);}
	public String getNewBookUnit() {return checkGet(newBookUnit);}
	public void setNewBookUnit(String newBookUnit) {this.newBookUnit = checkSet(newBookUnit);}
	public String getNewBookValue() {return checkGet(newBookValue);}
	public void setNewBookValue(String newBookValue) {this.newBookValue = checkSet(newBookValue);}
	public String getNewNetUnit() {return checkGet(newNetUnit);}
	public void setNewNetUnit(String newNetUnit) {this.newNetUnit = checkSet(newNetUnit);}
	public String getNewNetValue() {return checkGet(newNetValue);}
	public void setNewNetValue(String newNetValue) {this.newNetValue = checkSet(newNetValue);}
	public String getArticleName() {return checkGet(articleName);}
	public void setArticleName(String articleName) {this.articleName = checkSet(articleName);}
	public String getNameplate() {return checkGet(nameplate);}
	public void setNameplate(String nameplate) {this.nameplate = checkSet(nameplate);}
	public String getSpecification() {return checkGet(specification);}
	public void setSpecification(String specification) {this.specification = checkSet(specification);}
	public String getLicensePlate() {return checkGet(licensePlate);}
	public void setLicensePlate(String licensePlate) {this.licensePlate = checkSet(licensePlate);}
	public String getReturnPlace() {return checkGet(returnPlace);}
	public void setReturnPlace(String returnPlace) {this.returnPlace = checkSet(returnPlace);}
	public String getOriginalUnit() { return checkGet(originalUnit); }
	public void setOriginalUnit(String originalUnit) { this.originalUnit = checkSet(originalUnit); }

	private String measure;
	public String getMeasure() {return checkGet(measure);}
	public void setMeasure(String measure) {this.measure = checkSet(measure);}
	
	private String enterOrgName;
	private String differenceKindName;
	private String engineeringNoName;
	private String propertyNoName;
	public String getEnterOrgName() {return checkGet(enterOrgName);}
	public void setEnterOrgName(String enterOrgName) {this.enterOrgName = checkSet(enterOrgName);}
	public String getDifferenceKindName() {return checkGet(differenceKindName);}
	public void setDifferenceKindName(String differenceKindName) {this.differenceKindName = checkSet(differenceKindName);}
	public String getEngineeringNoName() {return checkGet(engineeringNoName);}
	public void setEngineeringNoName(String engineeringNoName) {this.engineeringNoName = checkSet(engineeringNoName);}
	public String getPropertyNoName() {return checkGet(propertyNoName);}
	public void setPropertyNoName(String propertyNoName) {this.propertyNoName = checkSet(propertyNoName);}
	
	private String signNo1;
	private String signNo2;
	private String signNo3;
	private String signNo4;
	private String signNo5;
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
	
	private String LaSignNo1;
	private String LaSignNo2;
	private String LaSignNo3;
	private String LaSignNo4;
	private String LaSignNo5;
	private String BuSignNo1;
	private String BuSignNo2;
	private String BuSignNo3;
	private String BuSignNo4;
	private String BuSignNo5;
	public String getLaSignNo1() {return checkGet(LaSignNo1);}
	public void setLaSignNo1(String laSignNo1) {LaSignNo1 = checkSet(laSignNo1);}
	public String getLaSignNo2() {return checkGet(LaSignNo2);}
	public void setLaSignNo2(String laSignNo2) {LaSignNo2 = checkSet(laSignNo2);}
	public String getLaSignNo3() {return checkGet(LaSignNo3);}
	public void setLaSignNo3(String laSignNo3) {LaSignNo3 = checkSet(laSignNo3);}
	public String getLaSignNo4() {return checkGet(LaSignNo4);}
	public void setLaSignNo4(String laSignNo4) {LaSignNo4 = checkSet(laSignNo4);}
	public String getLaSignNo5() {return checkGet(LaSignNo5);}
	public void setLaSignNo5(String laSignNo5) {LaSignNo5 = checkSet(laSignNo5);}
	public String getBuSignNo1() {return checkGet(BuSignNo1);}
	public void setBuSignNo1(String buSignNo1) {BuSignNo1 = checkSet(buSignNo1);}
	public String getBuSignNo2() {return checkGet(BuSignNo2);}
	public void setBuSignNo2(String buSignNo2) {BuSignNo2 = checkSet(buSignNo2);}
	public String getBuSignNo3() {return checkGet(BuSignNo3);}
	public void setBuSignNo3(String buSignNo3) {BuSignNo3 = checkSet(buSignNo3);}
	public String getBuSignNo4() {return checkGet(BuSignNo4);}
	public void setBuSignNo4(String buSignNo4) {BuSignNo4 = checkSet(buSignNo4);}
	public String getBuSignNo5() {return checkGet(BuSignNo5);}
	public void setBuSignNo5(String buSignNo5) {BuSignNo5 = checkSet(buSignNo5);}
	
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
	private String signNo;
	private String doorPlate4;
	private String cause;
	private String cause1;
	private String reduceDate;
	private String newEnterOrg;
	private String transferDate;
	private String verify;
	private String propertyKind;
	private String fundType;
	private String valuable;
	private String taxCredit;
	private String CArea;
	private String SArea;
	private String area;
	private String holdNume;
	private String holdDeno;
	private String holdArea;
	private String bookNotes;
	private String accountingTitle;
	private String bookValue;
	private String netValue;
	private String proofDoc;
	private String buildDate;
	private String sourceDate;
	private String buyDate;
	private String useYear;
	private String useMonth;
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
	private String cause2;
	private String reduceDeal;
	private String moveDate;
	private String keepUnit;
	private String keeper;
	private String useUnit;
	private String userNo;
	private String userNote;
	private String place1;
	private String place;
	private String oldPropertyNo;
	private String oldSerialNo;
	private String newEnterOrgReceive;
	private String notes;
	private String editID;
	private String editDate;
	private String editTime;
	private String enterDate;

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
	public String getSignNo() {return checkGet(signNo);}
	public void setSignNo(String signNo) {this.signNo = checkSet(signNo);}
	public String getDoorPlate4() {return checkGet(doorPlate4);}
	public void setDoorPlate4(String doorPlate4) {this.doorPlate4 = checkSet(doorPlate4);}
	public String getCause() {return checkGet(cause);}
	public void setCause(String cause) {this.cause = checkSet(cause);}
	public String getCause1() {return checkGet(cause1);}
	public void setCause1(String cause1) {this.cause1 = checkSet(cause1);}
	public String getReduceDate() {return checkGet(reduceDate);}
	public void setReduceDate(String reduceDate) {this.reduceDate = checkSet(reduceDate);}
	public String getNewEnterOrg() {return checkGet(newEnterOrg);}
	public void setNewEnterOrg(String newEnterOrg) {this.newEnterOrg = checkSet(newEnterOrg);}
	public String getTransferDate() {return checkGet(transferDate);}
	public void setTransferDate(String transferDate) {this.transferDate = checkSet(transferDate);}
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
	public String getBookNotes() {return checkGet(bookNotes);}
	public void setBookNotes(String bookNotes) {this.bookNotes = checkSet(bookNotes);}
	public String getAccountingTitle() {return checkGet(accountingTitle);}
	public void setAccountingTitle(String accountingTitle) {this.accountingTitle = checkSet(accountingTitle);}
	public String getBookValue() {return checkGet(bookValue);}
	public void setBookValue(String bookValue) {this.bookValue = checkSet(bookValue);}
	public String getNetValue() {return checkGet(netValue);}
	public void setNetValue(String netValue) {this.netValue = checkSet(netValue);}
	public String getProofDoc() {return checkGet(proofDoc);}
	public void setProofDoc(String proofDoc) {this.proofDoc = checkSet(proofDoc);}
	public String getBuildDate() {return checkGet(buildDate);}
	public void setBuildDate(String buildDate) {this.buildDate = checkSet(buildDate);}
	public String getSourceDate() {return checkGet(sourceDate);}
	public void setSourceDate(String sourceDate) {this.sourceDate = checkSet(sourceDate);}
	public String getBuyDate() {return checkGet(buyDate);}
	public void setBuyDate(String buyDate) {this.buyDate = checkSet(buyDate);}
	public String getUseYear() {return checkGet(useYear);}
	public void setUseYear(String useYear) {this.useYear = checkSet(useYear);}
	public String getUseMonth() {return checkGet(useMonth);}
	public void setUseMonth(String useMonth) {this.useMonth = checkSet(useMonth);}
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
	public String getCause2() {return checkGet(cause2);}
	public void setCause2(String cause2) {this.cause2 = checkSet(cause2);}
	public String getReduceDeal() {return checkGet(reduceDeal);}
	public void setReduceDeal(String reduceDeal) {this.reduceDeal = checkSet(reduceDeal);}
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
	public String getOldPropertyNo() {return checkGet(oldPropertyNo);}
	public void setOldPropertyNo(String oldPropertyNo) {this.oldPropertyNo = checkSet(oldPropertyNo);}
	public String getOldSerialNo() {return checkGet(oldSerialNo);}
	public void setOldSerialNo(String oldSerialNo) {this.oldSerialNo = checkSet(oldSerialNo);}
	public String getNewEnterOrgReceive() {return checkGet(newEnterOrgReceive);}
	public void setNewEnterOrgReceive(String newEnterOrgReceive) {this.newEnterOrgReceive = checkSet(newEnterOrgReceive);}
	public String getNotes() {return checkGet(notes);}
	public void setNotes(String notes) {this.notes = checkSet(notes);}
	public String getEditID() {return checkGet(editID);}
	public void setEditID(String editID) {this.editID = checkSet(editID);}
	public String getEditDate() {return checkGet(editDate);}
	public void setEditDate(String editDate) {this.editDate = checkSet(editDate);}
	public String getEditTime() {return checkGet(editTime);}
	public void setEditTime(String editTime) {this.editTime = checkSet(editTime);}
	public String getEnterDate() {return checkGet(enterDate);}
	public void setEnterDate(String enterDate) {this.enterDate = checkSet(enterDate);}


	private String LaArea;			
	private String LaHoldNume;
	private String LaHoldDeno;
	private String LaHoldArea;
	private String BuCArea;
	private String BuSArea;
	private String BuArea;
	private String BuHoldNume;
	private String BuHoldDeno;
	private String BuHoldArea;
	public String getLaArea() {return checkGet(LaArea);}
	public void setLaArea(String laArea) {LaArea = checkSet(laArea);}
	public String getLaHoldNume() {return checkGet(LaHoldNume);}
	public void setLaHoldNume(String laHoldNume) {LaHoldNume = checkSet(laHoldNume);}
	public String getLaHoldDeno() {return checkGet(LaHoldDeno);}
	public void setLaHoldDeno(String laHoldDeno) {LaHoldDeno = checkSet(laHoldDeno);}
	public String getLaHoldArea() {return checkGet(LaHoldArea);}
	public void setLaHoldArea(String laHoldArea) {LaHoldArea = checkSet(laHoldArea);}
	public String getBuCArea() {return checkGet(BuCArea);}
	public void setBuCArea(String buCArea) {BuCArea = checkSet(buCArea);}
	public String getBuSArea() {return checkGet(BuSArea);}
	public void setBuSArea(String buSArea) {BuSArea = checkSet(buSArea);}
	public String getBuArea() {return checkGet(BuArea);}
	public void setBuArea(String buArea) {BuArea = checkSet(buArea);}
	public String getBuHoldNume() {return checkGet(BuHoldNume);}
	public void setBuHoldNume(String buHoldNume) {BuHoldNume = checkSet(buHoldNume);}
	public String getBuHoldDeno() {return checkGet(BuHoldDeno);}
	public void setBuHoldDeno(String buHoldDeno) {BuHoldDeno = checkSet(buHoldDeno);}
	public String getBuHoldArea() {return checkGet(BuHoldArea);}
	public void setBuHoldArea(String buHoldArea) {BuHoldArea = checkSet(buHoldArea);}
	
	private String newEnterOrgName;
	public String getNewEnterOrgName() {return checkGet(newEnterOrgName);}
	public void setNewEnterOrgName(String newEnterOrgName) {this.newEnterOrgName = checkSet(newEnterOrgName);}
	
	private String material;
	private String propertyUnit;
	private String limitYear;
	private String place1Name;
	public String getMaterial() {return checkGet(material);}
	public void setMaterial(String material) {this.material = checkSet(material);}
	public String getPropertyUnit() {return checkGet(propertyUnit);}
	public void setPropertyUnit(String propertyUnit) {this.propertyUnit = checkSet(propertyUnit);}
	public String getLimitYear() {return checkGet(limitYear);}
	public void setLimitYear(String limitYear) {this.limitYear = checkSet(limitYear);}
	public String getPlace1Name() {return checkGet(place1Name);}
	public void setPlace1Name(String place1Name) {this.place1Name = checkSet(place1Name);}
	
	private String causeName;
	private String securityName;
	private String bookAmount;
	public String getCauseName() {return checkGet(causeName);}
	public void setCauseName(String causeName) {this.causeName = checkSet(causeName);}
	public String getSecurityName() {return checkGet(securityName);}
	public void setSecurityName(String securityName) {this.securityName = checkSet(securityName);}
	public String getBookAmount() {return checkGet(bookAmount);}
	public void setBookAmount(String bookAmount) {this.bookAmount = checkSet(bookAmount);}	
	
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

	public String getDeprMethod() {return checkGet(deprMethod);}
	public void setDeprMethod(String deprMethod) {this.deprMethod = checkSet(deprMethod);}
	public String getBuildFeeCB() {return checkGet(buildFeeCB);}
	public void setBuildFeeCB(String buildFeeCB) {this.buildFeeCB = checkSet(buildFeeCB);}
	public String getDeprUnitCB() {return checkGet(deprUnitCB);}
	public void setDeprUnitCB(String deprUnitCB) {this.deprUnitCB = checkSet(deprUnitCB);}
	public String getDeprPark() {return checkGet(deprPark);}
	public void setDeprPark(String deprPark) {this.deprPark = checkSet(deprPark);}
	public String getDeprUnit() {return checkGet(deprUnit);}
	public void setDeprUnit(String deprUnit) {this.deprUnit = checkSet(deprUnit);}
	public String getDeprUnit1() {return checkGet(deprUnit1);}
	public void setDeprUnit1(String deprUnit1) {this.deprUnit1 = checkSet(deprUnit1);}
	public String getDeprAccounts() {return checkGet(deprAccounts);}
	public void setDeprAccounts(String deprAccounts) {this.deprAccounts = checkSet(deprAccounts);}
	public String getScrapValue() {return checkGet(scrapValue);}
	public void setScrapValue(String scrapValue) {this.scrapValue = checkSet(scrapValue);}
	public String getDeprAmount() {return checkGet(deprAmount);}
	public void setDeprAmount(String deprAmount) {this.deprAmount = checkSet(deprAmount);}
	public String getAccumDepr() {return checkGet(accumDepr);}
	public void setAccumDepr(String accumDepr) {this.accumDepr = checkSet(accumDepr);}
	public String getApportionMonth() {return checkGet(apportionMonth);}
	public void setApportionMonth(String apportionMonth) {this.apportionMonth = checkSet(apportionMonth);}
	public String getMonthDepr() {return checkGet(monthDepr);}
	public void setMonthDepr(String monthDepr) {this.monthDepr = checkSet(monthDepr);}	
	
	private String oldMonthDepr1;
	private String newMonthDepr1;
	public String getOldMonthDepr1() {return checkGet(oldMonthDepr1);}
	public void setOldMonthDepr1(String oldMonthDepr1) {this.oldMonthDepr1 = checkSet(oldMonthDepr1);}
	public String getNewMonthDepr1() {return checkGet(newMonthDepr1);}
	public void setNewMonthDepr1(String newMonthDepr1) {this.newMonthDepr1 = checkSet(newMonthDepr1);}
	
	private String proofYear;
	private String proofNo;
	public String getProofYear() {return checkGet(proofYear);}
	public void setProofYear(String proofYear) {this.proofYear = checkSet(proofYear);}
	public String getProofNo() {return checkGet(proofNo);}
	public void setProofNo(String proofNo) {this.proofNo = checkSet(proofNo);}	

	private String summonsDate;
	public String getSummonsDate() {return checkGet(summonsDate);}
	public void setSummonsDate(String summonsDate) {this.summonsDate = checkSet(summonsDate);}

	private String writeDate;
	private String writeUnit;
	public String getWriteDate(){ return checkGet(writeDate); }
	public void setWriteDate(String s){ writeDate=checkSet(s); }
	public String getWriteUnit(){ return checkGet(writeUnit); }
	public void setWriteUnit(String s){ writeUnit=checkSet(s); }
}