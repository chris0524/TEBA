package unt.ch;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import TDlib_Simple.com.src.DBServerTools;
import TDlib_Simple.tools.src.ReflectTools;
import TDlib_Simple.tools.src.StringTools;
import util.Common;
import util.Database;
import util.Datetime;
import util.TCGHCommon;

/**
 * <br/>程式目的：財產增加單及主檔維護_共同基本資料
 * <br/>程式代號：UNTCH001F02
 * <br/>程式日期：
 * <br/>程式作者：Kang Da
 * <br/>--------------------------------------------------------
 * <br/>修改作者　　     修改日期　　　			修改目的
 * <br/>
 * <br/>--------------------------------------------------------
 */
public class UNTCH001F02 extends UNTCH001Q {

	/**
	 * 檢查是否為合併分割案
	 */
	public boolean isMerge() {
		boolean chkResult = true;
		Database db = null;
		ResultSet rs = null;
		try {
			db = new Database();
			rs = db.querySQL_NoChange("select count(1) as cnt  from UNTLA_MERGEDIVISION where  enterorg = " + Common.sqlChar(this.getEnterOrg()) + " and divisionadd = " + Common.sqlChar(this.getCaseNo()));
			
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
	
	public void updateSerialNoforUNTMP_Movable(){
		String sql = "";
		String serialNoS = "";
		String serialNoE = "";

		Database db = null;
		ResultSet rs = null;
		
		sql = "select right ('0000000' + cast (min (serialno) as varchar), 7) from UNTMP_MOVABLEDETAIL" +
				" where 1=1 " +
				" and enterOrg = " + Common.sqlChar(enterOrg) +
				" and ownership = " + Common.sqlChar(ownership) +
				" and propertyNo = " + Common.sqlChar(propertyNo) +
				" and caseNo = " + Common.sqlChar(caseNo) +
				" and lotNo = " + Common.sqlChar(lotNo) +
				" and differenceKind = " + Common.sqlChar(differenceKind);
		
		try{
			db = new Database();
			rs = db.querySQL(sql);
			if(rs.next()){
				serialNoS = rs.getString(1); 
			}
			rs.close();
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			db.closeAll();
		}
		
		sql = "select right ('0000000' + cast (max (serialno) as varchar), 7) from UNTMP_MOVABLEDETAIL" +
				" where 1=1 " +
				" and enterOrg = " + Common.sqlChar(enterOrg) +
				" and ownership = " + Common.sqlChar(ownership) +
				" and propertyNo = " + Common.sqlChar(propertyNo) +
				" and caseNo = " + Common.sqlChar(caseNo) +
				" and lotNo = " + Common.sqlChar(lotNo) +
				" and differenceKind = " + Common.sqlChar(differenceKind);
		
		try{
			db = new Database();
			rs = db.querySQL(sql);
			if(rs.next()){
				serialNoE = rs.getString(1); 
			}
			rs.close();
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			db.closeAll();
		}
		
		sql = "update UNTMP_MOVABLE set" +
				" serialnos = " + Common.sqlChar(serialNoS) + "," +
				" serialnoe = " + Common.sqlChar(serialNoE) + 
				" where 1=1 " +
				" and enterorg = " + Common.sqlChar(enterOrg) +
				" and ownership = " + Common.sqlChar(ownership) +
				" and propertyno = " + Common.sqlChar(propertyNo) +
				" and caseno = " + Common.sqlChar(caseNo) +
				" and lotno = " + Common.sqlChar(lotNo) +
				" and differencekind = " + Common.sqlChar(differenceKind);
		
		try{
			db = new Database();
			db.excuteSQL(sql);
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			db.closeAll();
		}
		
		sql = null;
		serialNoS = null;
		serialNoE = null;
		db = null;
		rs = null;
	}
	
	//====================================================================
	@Override
	protected util.SuperBean2[] _checkPropertyNoType(){
		
		util.SuperBean2[] sb = new util.SuperBean2[2];
		
		String checkStr = new unt.ch.UNTCH_Tools()._checkPropertyNoType(this.getPropertyNo());
		if(_table_LA.equals(checkStr)){				sb[0] = new unt.la.UNTLA002F();
		}else if(_table_BU.equals(checkStr)){		sb[0] = new unt.bu.UNTBU002F(this.getClass().getSimpleName());
		}else if(_table_RF.equals(checkStr)){		sb[0] = new unt.rf.UNTRF002F(this.getClass().getSimpleName());
		}else if(_table_MP.equals(checkStr)){		sb[0] = new unt.mp.UNTMP002F(this.getClass().getSimpleName());
		}else if(_table_RT.equals(checkStr)){		sb[0] = new unt.rt.UNTRT001F(this.getClass().getSimpleName());
		}else if(_table_VP.equals(checkStr)){		sb[0] = new unt.vp.UNTVP001F();
		}
		
		checkStr = null;
		
		return sb;
	}
	
	//====================================================================		
	//預設值		
	@Override
	protected void _setDefaultValue(){	
		String signNo = null;
		String originalArea = null;
		String originalHoldNume = null;
		String originalHoldDeno = null;
		String originalHoldArea = null;
		
		UNTCH_Tools ut = new UNTCH_Tools();

		String checkStr = ut._checkPropertyNoType(this.getPropertyNo());
		if(_table_LA.equals(checkStr)){
			if("".equals(checkGet(this.getLaSignNo3())) || "".equals(checkGet(this.getLaSignNo4())) || "".equals(checkGet(this.getLaSignNo5()))){				
			}else{
				signNo = this.getLaSignNo3() + checkGet(this.getLaSignNo4()) + checkGet(this.getLaSignNo5());
			}			
			originalArea = this.getOriginalAreaLa();
			originalHoldNume = this.getOriginalHoldNumeLa();
			originalHoldDeno = this.getOriginalHoldDenoLa();
			originalHoldArea = this.getOriginalHoldAreaLa();
			
		}else if(_table_BU.equals(checkStr)){	

			if("".equals(checkGet(this.getBuSignNo3())) || "".equals(checkGet(this.getBuSignNo4())) || "".equals(checkGet(this.getBuSignNo5()))){				
			}else{
				signNo = this.getBuSignNo3() + checkGet(this.getBuSignNo4()) + checkGet(this.getBuSignNo5());
			}
			
			originalArea = this.getOriginalAreaBu();
			originalHoldNume = this.getOriginalHoldNumeBu();
			originalHoldDeno = this.getOriginalHoldDenoBu();
			originalHoldArea = this.getOriginalHoldAreaBu();

		}else if(_table_MP.equals(checkStr)){
			
		}else if(_table_RT.equals(checkStr)){
			if("".equals(checkGet(this.getLaSignNo3())) || "".equals(checkGet(this.getLaSignNo4())) || "".equals(checkGet(this.getLaSignNo5()))){				
			}else{
				signNo = this.getLaSignNo3() + checkGet(this.getLaSignNo4()) + checkGet(this.getLaSignNo5());
			}			
			originalArea = this.getOriginalAreaLa();
			originalHoldNume = this.getOriginalHoldNumeLa();
			originalHoldDeno = this.getOriginalHoldDenoLa();
			originalHoldArea = this.getOriginalHoldAreaLa();
		}
		
		if(!_table_LA.equals(checkStr)){	//非土地類設定originalLimitYear、otherLimitYear與折舊部分欄位
			if(getOriginalLimitYear().equals("")){ //沒輸入originalLimitYear, 就填入財編limitYear值, 並計算其他欄位
				String limitYear = ut._queryData("limityear")._from("SYSPK_PROPERTYCODE")._with(" and enterorg in ('" + getEnterOrg() + "' , '000000000A' ) and propertyno = '" + getPropertyNo() + "' ")._toString();
				if(!"".equals(getOtherLimitYear()) && !"0".equals(getOtherLimitYear())) {
					String originalLimitYear = String.valueOf(Math.ceil((Double.valueOf(getOtherLimitYear()) / 12)));
					originalLimitYear = originalLimitYear.substring(0,originalLimitYear.indexOf("."));
					this.setOriginalLimitYear(originalLimitYear);					
				}else {
					if("".equals(limitYear)){ limitYear = "0"; }
					this.setOriginalLimitYear(limitYear);
					this.setOtherLimitYear(String.valueOf(Integer.parseInt(limitYear)*12));
				}
				//折舊方法不是'01：不必折舊' 且為BU RF MP, 就處理折舊欄位
				if(!this.getOriginalDeprMethod().equals("01") && (_table_BU.equals(checkStr) || _table_RF.equals(checkStr) || _table_MP.equals(checkStr)) ){
					//攤提壽月應該與使用年限一致(otherlimityear)
					 this.setOriginalApportionMonth(getOtherLimitYear());
					 this.setApportionMonth(getOtherLimitYear());
					//月提折舊金額=應攤提折舊總額/攤提壽月 取整數
					 int originalMonthDepr = Integer.valueOf(this.getOriginalDeprAmount()) / Integer.valueOf(this.getOriginalApportionMonth());
					 int originalMonthDepr1 = Integer.valueOf(this.getOriginalDeprAmount()) - originalMonthDepr * (Integer.valueOf(this.getOriginalApportionMonth())-1);
					 this.setOriginalMonthDepr(String.valueOf(originalMonthDepr));
					 this.setOriginalMonthDepr1(String.valueOf(originalMonthDepr1));
					 this.setMonthDepr(this.getOriginalMonthDepr());
					 this.setMonthDepr1(this.getOriginalMonthDepr1());
					//攤提年限截止年月 = 購置日期 +  原使用年限
					 String year = String.valueOf(  Integer.valueOf(this.getBuyDate().substring(0,3))+Integer.valueOf(this.getOriginalLimitYear())  ); 
					 String originalApportionEndYM =year+this.getBuyDate().substring(3,5);
					 this.setOriginalApportionEndYM(originalApportionEndYM);
					 this.setApportionEndYM(originalApportionEndYM);					 
				 }
			}			
		}
		
		this.setDeprMethod(this.getOriginalDeprMethod());
		this.setMergeDivisionBatch("0");
		this.setPicture(picture);
		this.setDeprUnit(originalDeprUnit);
		this.setDeprUnit1(originalDeprUnit1);
		this.setDeprAccounts(originalDeprAccounts);
		this.setSignNo(signNo);
		this.setOriginalArea(originalArea);
		this.setOriginalHoldNume(originalHoldNume);
		this.setOriginalHoldDeno(originalHoldDeno);
		this.setOriginalHoldArea(originalHoldArea);
		this.setCArea(getOriginalCArea());
		this.setSArea(getOriginalSArea());
		this.setArea(originalArea);
		this.setHoldNume(originalHoldNume);
		this.setHoldDeno(originalHoldDeno);
		this.setHoldArea(originalHoldArea);
		
		this.setSerialNoS(getSerialNo());
		this.setTaxCredit("N");
		
		this.setNonProof("N");
		this.setUseEndYM("0");
		this.setPermitReduceDate("0");
		this.setIsAccumDepr("0");
		this.setMeasure(this.getOriginalMeasure());
		
		this.setVerify("N");
		
		this.setHoldNume1("1");
		this.setHoldDeno2("1");
		this.setSetArea(originalArea);
		this.setSetObligor("N");
		this.setOriginalUV("0");
		this.setBookUnitValue("0");
		this.setBookUnitAmount("0");
		
		this.setBookAmount(this.getOriginalAmount());
		this.setBookUnit(this.getOriginalUnit());
		this.setBookValue(this.getOriginalBV());
		this.setNetUnit(this.getOriginalUnit());
		this.setNetValue(this.getOriginalBV());
		
		this.setScrapValue(originalScrapValue);
		this.setDeprAmount(originalDeprAmount);
		this.setAccumDepr(originalAccumDepr);
		this.setApportionMonth(originalApportionMonth);
		this.setMonthDepr(originalMonthDepr);
		this.setMonthDepr1(originalMonthDepr1);
		this.setApportionEndYM(originalApportionEndYM);
		
		if("Y".equals(getOriginalBuildFeeCB())){
		}else{		setOriginalBuildFeeCB("N");
		}
		if("Y".equals(getOriginalDeprUnitCB())){
		}else{		setOriginalDeprUnitCB("N");
		}
		if("Y".equals(getBuildFeeCB())){
		}else{		setBuildFeeCB("N");
		}
		if("Y".equals(getDeprUnitCB())){
		}else{		setDeprUnitCB("N");
		}
		if("Y".equals(getNoDeprSet())){
		}else{		setNoDeprSet("N");
		}
		if("Y".equals(getSelfdepr())){
		}else{		setSelfdepr("N");
		}
		this.setBuildFeeCB(getOriginalBuildFeeCB());
		this.setDeprUnitCB(getOriginalDeprUnitCB());
		this.setMoveDate(getOriginalMoveDate());
		this.setKeepUnit(getOriginalKeepUnit());
		this.setKeeper(getOriginalKeeper());
		this.setUseUnit(getOriginalUseUnit());
		this.setUserNo(this.getOriginalUser());
		this.setUserNote(getOriginalUserNote());
		this.setPlace1(getOriginalPlace1());
		this.setPlace(getOriginalPlace());
		this.setApportionEndYM(getOriginalApportionEndYM());
		this.setWriteUnit(ut._queryData("writeunit")._from("UNTLA_ADDPROOF")._with(" and enterorg = '" + getEnterOrg() + "' and ownership = '" + getOwnership() + "' and differencekind = '" + getDifferenceKind() + "' and caseno = '" + getCaseNo() + "'")._toString());
		this.setWriteDate(ut._transToROC_Year(ut._queryData("writedate")._from("UNTLA_ADDPROOF")._with(" and enterorg = '" + getEnterOrg() + "' and ownership = '" + getOwnership() + "' and differencekind = '" + getDifferenceKind() + "' and caseno = '" + getCaseNo() + "'")._toString()));
		this.setProofYear(ut._queryData("proofyear")._from("UNTLA_ADDPROOF")._with(" and enterorg = '" + getEnterOrg() + "' and ownership = '" + getOwnership() + "' and differencekind = '" + getDifferenceKind() + "' and caseno = '" + getCaseNo() + "'")._toString());
		this.setProofDoc(ut._queryData("proofdoc")._from("UNTLA_ADDPROOF")._with(" and enterorg = '" + getEnterOrg() + "' and ownership = '" + getOwnership() + "' and differencekind = '" + getDifferenceKind() + "' and caseno = '" + getCaseNo() + "'")._toString());
		this.setProofNo(ut._queryData("proofno")._from("UNTLA_ADDPROOF")._with(" and enterorg = '" + getEnterOrg() + "' and ownership = '" + getOwnership() + "' and differencekind = '" + getDifferenceKind() + "' and caseno = '" + getCaseNo() + "'")._toString());
		this.setSummonsDate(ut._queryData("summonsdate")._from("UNTLA_ADDPROOF")._with(" and enterorg = '" + getEnterOrg() + "' and ownership = '" + getOwnership() + "' and differencekind = '" + getDifferenceKind() + "' and caseno = '" + getCaseNo() + "'")._toString());
		
		if("".equals(Common.get(this.getCaseSerialNo()))){			
			DataStructor ds = new DataStructor();
			ds.enterOrg = this.getEnterOrg();
			ds.ownership = this.getOwnership();
			ds.caseNo = this.getCaseNo();
			ds.differenceKind = this.getDifferenceKind();
			
			String[] tables = new String[]{
							"UNTLA_LAND",
							"UNTBU_BUILDING",
							"UNTRF_ATTACHMENT",
							"UNTMP_MOVABLE",
							"UNTVP_ADDPROOF",
							"UNTRT_ADDPROOF"
							};
			
			this.setCaseSerialNo(ut._getNewCaseSerialNo(ds,tables));			
		}
		
		signNo = null;
		originalArea = null;
		originalHoldNume = null;
		originalHoldDeno = null;
		originalHoldArea = null;		
		ut = null;
		checkStr = null;
	}
	
	
	//====================================================================	
		@Override
		protected String _getMaxSerialNo(){
			String tableName = "";
			
			String checkStr = new unt.ch.UNTCH_Tools()._checkPropertyNoType(this.getPropertyNo());
			if(_table_RT.equals(checkStr)){				tableName = "UNTRT_AddProof";
			}else if(_table_VP.equals(checkStr)){		tableName = "UNTVP_AddProof";
			}
			
			String sql = "select max(serialNo) from " + tableName + 
							" where 1=1" +
							" and enterOrg = " + Common.sqlChar(this.getEnterOrg()) +
							" and ownership = " + Common.sqlChar(this.getOwnership()) +
							" and caseNo = " + Common.sqlChar(this.getCaseNo()) +
							" and differenceKind = " + Common.sqlChar(this.getDifferenceKind()) +
							" and propertyNo = " + Common.sqlChar(this.getPropertyNo()) ;
	
			tableName = null;			
			checkStr = null;
			
			return new TDlib_Simple.com.src.DBServerTools()._execSQL_asString(sql);
		}
	
	//====================================================================	

	public UNTCH001F02 _execQueryOneforDetail(){
		
		ReflectTools rf = new ReflectTools();
		StringTools st = new StringTools();
		List fields = rf._getDeclaredFields_asList_fromObj(this);
		
		fields.remove("enterOrg");
		fields.remove("enterOrgName");
		fields.remove("ownership");
		fields.remove("ownershipName");
		fields.remove("caseNo");
		fields.remove("differenceKind");
		fields.remove("differenceKindName");
		fields.remove("engineeringNo");
		fields.remove("engineeringNoName");
		fields.remove("propertyNo");
		fields.remove("serialNo");
		fields.remove("serialNoS");
		fields.remove("serialNoE");
		fields.remove("lotNo");
		
		String methodName = "";
		for(Object o : fields){
			methodName = (String)o;
			methodName = st._HeadStringToUpper(methodName);
			rf._invokeSetMethod_fromObj(this, "set" + methodName, new Class[]{String.class}, new Object[]{""});					
		}
		
		//=======================================
		
		util.SuperBean2 sb = _checkPropertyNoType()[0];	
		
		//=======================================
		sb.setEnterOrg(this.getEnterOrg());
		sb.setOwnership(this.getOwnership());
		sb.setCaseNo(this.getCaseNo());
		sb.setDifferenceKind(this.getDifferenceKind());
		sb.setPropertyNo(this.getPropertyNo());
		sb.setSerialNo(this.getSerialNo());
		sb.setSerialNoS(this.getSerialNoS());
		sb.setSerialNoE(this.getSerialNoE());
		sb.setLotNo(this.getLotNo());
		//=======================================
			
		try{
			Object obj = sb.queryOne();
			UNTCH_Tools ul = new UNTCH_Tools();
			ul._setParameter(obj,this);

			
			String checkStr = ul._checkPropertyNoType(this.getPropertyNo());
				
			this.setPropertyNoName(ul._getPropertyNoName(this.getEnterOrg(), this.getPropertyNo()));
			
			if(_table_LA.equals(checkStr)){				
				this.setLaSignNo1(this.getSignNo1());
				this.setLaSignNo2(this.getSignNo2());
				this.setLaSignNo3(this.getSignNo3());
				this.setLaSignNo4(this.getSignNo4());
				this.setLaSignNo5(this.getSignNo5());
				
				this.setOriginalAreaLa(this.getOriginalArea());
				this.setOriginalHoldNumeLa(this.getOriginalHoldNume());
				this.setOriginalHoldDenoLa(this.getOriginalHoldDeno());
				this.setOriginalHoldAreaLa(this.getOriginalHoldArea());
				
				this.setOriginalDeprMethod("01");
				this.setOriginalAmount("1");
				
				this.setPropertyUnit("平方公尺");
				
			}else if(_table_BU.equals(checkStr)){
				this.setBuSignNo1(this.getSignNo1());
				this.setBuSignNo2(this.getSignNo2());
				this.setBuSignNo3(this.getSignNo3());				
				this.setBuSignNo4(this.getSignNo4());
				this.setBuSignNo5(this.getSignNo5());
				
				this.setOriginalAreaBu(this.getOriginalArea());
				this.setOriginalHoldNumeBu(this.getOriginalHoldNume());
				this.setOriginalHoldDenoBu(this.getOriginalHoldDeno());
				this.setOriginalHoldAreaBu(this.getOriginalHoldArea());
				this.setOriginalAmount("1");
				
				this.setPropertyUnit("平方公尺");
				
			}else if(_table_RF.equals(checkStr)){
				this.setPropertyUnitRf(this.getPropertyUnit());
				
				this.setPropertyUnit(ul._getPropertyUnit(this.getPropertyNo()));
				
			}else if(_table_MP.equals(checkStr)){
				this.setPropertyUnit(ul._getPropertyUnit(this.getPropertyNo()));
				// 增加單基本資料頁顯示批的財產別名
				this.setPropertyName1(this.getPropertyName1_movable());
				
			}else if(_table_RT.equals(checkStr)){
							
				this.setLaSignNo1(this.getSignNo1());
				this.setLaSignNo2(this.getSignNo2());
				this.setLaSignNo3(this.getSignNo3());
				this.setLaSignNo4(this.getSignNo4());
				this.setLaSignNo5(this.getSignNo5());
				
				if ("".equals(this.getOriginalDeprMethod())) {
					this.setOriginalDeprMethod("01");
				}
				this.setOriginalAmount("1");
				
				this.setPropertyUnit(ul._getPropertyUnit(this.getPropertyNo()));
			}else if(_table_VP.equals(checkStr)){
				this.setOriginalDeprMethod("01");
				
				this.setPropertyUnit(ul._getPropertyUnit(this.getPropertyNo()));
				
			}
		
			this.setMaterial(ul._getMaterial(this.getPropertyNo()));			
			//this.setLimitYear(ul._getLimitYear(this.getPropertyNo()));
			
			this.setCaseSerialNo(Common.formatFrontZero(this.getCaseSerialNo(), 5));
			//this.setDeprPark(this.getOriginalDeprPark());
            this.setPicture(this.getPicture());
			
			setStateQueryOneSuccess();
			
			ul = null;
			
			queryProofNo();
			
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			sb = null;
			rf = null;
			st = null;
			fields = null;
		}	
		return this;
	}
	
		private void queryProofNo(){
			String sql = "select proofyear, proofdoc, proofno from UNTLA_ADDPROOF where 1=1" +
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
					this.setProofYear(rs.getString("proofyear")+"年 ");
					this.setProofDoc(rs.getString("proofdoc")+"字 ");
					this.setProofNo(rs.getString("proofno"));
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
		Database db = null;
		ArrayList objList = null;
		
		try {
			StringBuffer sbSQL = new StringBuffer();
			
			sbSQL.append("select ROW_NUMBER() over (order by datastate,propertyno, serialNo) as rownum,* from (").append(								
							"select").append(
								" 'LA' as type, ").append(	
								" a.enterOrg, ").append(
								" a.ownership, ").append(
								" a.caseNo, ").append(
								" a.caseSerialNo, ").append(
								" a.propertyNo, ").append(
								" a.differenceKind, ").append(
								" a.serialNo, ").append(					
								" null as serialNoE,").append(
								" null as lotNo, ").append(
								" a.sourceDate, ").append(
								" a.signNo as signNoLa, ").append(
								" null as signNoBu, ").append(
								" null as specification, ").append(
								" null as nameplate, ").append(
								" null as bookAmount, ").append(
								" null as Unit, ").append(
								" a.bookUnit, ").append(
								" a.bookValue, ").append(
								" a.datastate,").append(
								" a.propertyname1,").append(
								//" usernote,").append(
								" null as limityear,").append(
								" null as originallimityear,").append(
								" a.engineeringNo").append(
						" from UNTLA_Land a").append(
								" left join UNTLA_ADDPROOF b on a.enterorg=b.enterorg and a.caseno=b.caseno").append(
								" where 1=1").append(
								getQueryCondition("LA")).append(	
					" union ").append(								
						" select").append(	
								" 'BU' as type, ").append(	
								" a.enterOrg, ").append(
								" a.ownership, ").append(
								" a.caseNo, ").append(
								" a.caseSerialNo, ").append(
								" a.propertyNo, ").append(
								" a.differenceKind, ").append(
								" a.serialNo, ").append(
								" null as serialNoE, ").append(
								" null as lotNo, ").append(
								" a.sourceDate, ").append(
								" null as signNoLa, ").append(
								" a.signNo as signNoBu, ").append(
								" null as specification, ").append(									
								" null as nameplate, ").append(
								" null as bookAmount,").append(
								" null as Unit, ").append(
								" null as bookUnit, ").append(
								" a.bookValue, ").append(
								" a.datastate,").append(
								" a.propertyname1,").append(
								//" usernote,").append(
								" (select z.limityear from SYSPK_PROPERTYCODE z where z.propertyno = a.propertyno and z.enterorg = '000000000A') as limityear,").append(
								" a.originallimityear,").append(
								" a.engineeringNo").append(
							" from UNTBU_Building a").append(
								" left join UNTBU_ADDPROOF b on a.enterorg=b.enterorg and a.caseno=b.caseno").append(
								" where 1=1").append(
								getQueryCondition("BU")).append(
					" union ").append(
						" select").append(	
								" 'RF' as type, ").append(	
								" a.enterOrg, ").append(
								" a.ownership, ").append(
								" a.caseNo, ").append(
								" a.caseSerialNo, ").append(
								" a.propertyNo, ").append(
								" a.differenceKind, ").append(
								" a.serialNo, ").append(
								" null as serialNoE, ").append(
								" null as lotNo, ").append(
								" a.sourceDate, ").append(
								" null as signNoLa, ").append(
								" null as signNoBu, ").append(
								" null as specification, ").append(
								" null as nameplate, ").append(
								" null as bookAmount,").append(
								" null as Unit, ").append(
								" null as bookUnit, ").append(
								" a.bookValue, ").append(
								" a.datastate,").append(
								" a.propertyname1,").append(
								//" usernote,").append(
								" (select z.limityear from SYSPK_PROPERTYCODE z where z.propertyno = a.propertyno and z.enterorg = '000000000A') as limityear,").append(
								" a.originallimityear,").append(
								" a.engineeringNo").append(
							" from UNTRF_Attachment a").append(
								" left join UNTRF_ADDPROOF b on a.enterorg=b.enterorg and a.caseno=b.caseno").append(
								" where 1=1").append(
								getQueryCondition("RF")).append(
					" union ").append(
						" select").append(		
								" 'MP' as type, ").append(	
								" a.enterOrg, ").append(
								" a.ownership, ").append(
								" a.caseNo, ").append(
								" a.caseSerialNo, ").append(
								" a.propertyNo, ").append(
								" a.differenceKind, ").append(
								" a.serialNoS as serialNo, ").append(
								" a.serialNoE, ").append(
								" a.lotNo, ").append(
								" a.sourceDate, ").append(
								" null as signNoLa, ").append(
								" null as signNoBu, ").append(
								" a.specification, ").append(
								" a.nameplate, ").append(
								" a.bookAmount,").append(
								" null as Unit, ").append(
								" null as bookUnit, ").append(
								" a.bookValue, ").append(
								" a.datastate,").append(
								" a.propertyname1,").append(
								//" z.usernote,").append(
								" (select z.limityear from SYSPK_PROPERTYCODE z where z.propertyno = a.propertyno and z.enterorg = '000000000A') as limityear,").append(
								" z.originallimityear,").append(	
								" a.engineeringNo").append(	
							" from UNTMP_Movable a ").append(
								" left join UNTMP_MOVABLEDETAIL z").append(
								" on a.enterorg = z.enterorg").append(
								" and a.ownership = z.ownership").append(
								" and a.differencekind = z.differencekind").append(
								" and a.lotno = z.lotno").append(
//								" and a.caseno = z.caseno").append(
								" and a.propertyno = z.propertyno").append(
								" left join UNTMP_ADDPROOF b on z.enterorg=b.enterorg and z.caseno=b.caseno").append(
							" where 1=1").append(
								getQueryCondition("MP")).append(
					" union ").append(
						" select").append(	
								" 'VP' as type, ").append(	
								" a.enterOrg, ").append(
								" a.ownership, ").append(
								" a.caseNo, ").append(
								" a.caseSerialNo, ").append(
								" a.propertyNo, ").append(
								" a.differenceKind, ").append(
								" a.serialNo as serialNo, ").append(
								" null as serialNoE, ").append(
								" null as lotNo, ").append(
								" sourceDate, ").append(
								" null as signNoLa, ").append(
								" null as signNoBu, ").append(
								" null as specification, ").append(
								" null as nameplate, ").append(
								" a.bookAmount,").append(
								" null as Unit, ").append(
								" null as bookUnit, ").append(
								" a.bookValue, ").append(
								" a.datastate,").append(
								" a.propertyname1,").append(
								//" usernote,").append(
								" null as limityear,").append(
								" null as originallimityear,").append(
								" a.engineeringNo").append(
							" from UNTVP_Addproof a where 1=1").append(
								getQueryCondition("VP")).append(
					" union ").append(
						" select").append(	
								" 'RT' as type, ").append(	
								" a.enterOrg, ").append(
								" a.ownership, ").append(
								" a.caseNo, ").append(
								" a.caseSerialNo, ").append(
								" a.propertyNo, ").append(
								" a.differenceKind, ").append(
								" a.serialNo as serialNo, ").append(
								" null as serialNoE, ").append(
								" null as lotNo, ").append(
								" a.sourceDate , ").append(
								" null as signNoLa, ").append(
								" null as signNoBu, ").append(
								" null as specification, ").append(
								" null as nameplate, ").append(
								" null as bookAmount,").append(
								" null as Unit, ").append(									
								" a.originalBV as bookUnit, ").append(
								" a.bookValue, ").append(
								" a.datastate,").append(
								" a.propertyname1,").append(
								//" usernote,").append(
								" null as limityear,").append(
								" a.originallimityear,").append(
								" a.engineeringNo").append(
							" from UNTRT_AddPROOF a where 1=1").append(
								getQueryCondition("RT")).append(
							") a order by caseserialno ");
		
			db = new Database();
			objList = new ArrayList();
			ResultSet rs = db.querySQL(sbSQL.toString(),true);
			
			UNTCH_Tools uctls = new UNTCH_Tools();
			StringTools st = new StringTools();
			// 問題單1386複測
			if (getMainPage1() != 0) {
				setCurrentPage(getMainPage1());
			}
			processCurrentPageAttribute(rs);			
			if (getTotalRecord() > 0) {
				int count = getRecordStart();
				int end = getRecordEnd();
				do {
					if (count > end)
						break;
					int fieldIndex = 0;
					String rowArray[]=new String[("true".equals(this.getIsAddProof()) ? 18 : 19)];
					if (!"true".equals(this.getIsAddProof())) {
						rowArray[fieldIndex++] = checkGet(rs.getString("rownum"));
					}
					String enterOrg = Common.get(rs.getString("enterOrg")); 
					rowArray[fieldIndex++] = enterOrg;
					rowArray[fieldIndex++] = checkGet(rs.getString("ownership"));
					rowArray[fieldIndex++] = checkGet(rs.getString("caseNo"));
					rowArray[fieldIndex++] = checkGet(rs.getString("differenceKind"));
					rowArray[fieldIndex++] = checkGet(rs.getString("caseSerialNo"));
					rowArray[fieldIndex++] = uctls._getDifferenceKindName(rs.getString("differenceKind"));
					rowArray[fieldIndex++] = checkGet(rs.getString("propertyNo")); 
					String serialNo = checkGet(rs.getString("serialNo"));
					rowArray[fieldIndex++] = serialNo;
					String serialNoE = checkGet(rs.getString("serialNoE"));
					String type = Common.get(rs.getString("type"));
					if((!"".equals(serialNo) && !"".equals(serialNoE)) && !checkGet(serialNo).equals(serialNoE)){
						rowArray[fieldIndex++] =  serialNo +"-"+ serialNoE;
					}else{
						if("MP".equals(type)){
							rowArray[fieldIndex++] = serialNoE;
						}else{
							rowArray[fieldIndex++] = serialNo;
						}
					}
					rowArray[fieldIndex++] = checkGet(rs.getString("lotNo"));
					rowArray[fieldIndex++] = uctls._getPropertyNoName(enterOrg, rs.getString("propertyNo"));
					rowArray[fieldIndex++] = checkGet(uctls._transToROC_Year(rs.getString("sourceDate")));
					
					String signNoLa = Common.get(rs.getString("signNoLa"));
					String signNoBu = Common.get(rs.getString("signNoBu"));
					String specification = Common.get(rs.getString("specification"));
					String nameplate = Common.get(rs.getString("nameplate"));
					
					if (TCGHCommon.getSYSCODEName("01", "ETO").equals(this.getEnterOrg()) && ("RT".equals(type) || "VP".equals(type))) {
						// 客戶 625  若是科技部  且為 權利股票 就塞 財產別名
						rowArray[fieldIndex++] = Common.get(rs.getString("propertyname1"));
					} else if ( "LA".equals(type) && signNoLa.length() >= 15 ) {
						// 土地標示				
						rowArray[fieldIndex++] = uctls._getSignNoName(signNoLa.substring(0,7)) + signNoLa.substring(7,11) + "-" + signNoLa.substring(11) + "地號";
					} else if ( "BU".equals(type) && signNoBu.length() >= 15 ) {
						// 建物標示				
						rowArray[fieldIndex++] = uctls._getSignNoName(signNoBu.substring(0,7)) + signNoBu.substring(7,12) + "-" + signNoBu.substring(12) + "建號";						
					}  else if ( "RF".equals(type) ) {
						// 地改財產別名										
						rowArray[fieldIndex++] = Common.get(rs.getString("propertyname1"));						
					} else if(false == ("".equals(specification) && "".equals(nameplate))) {
						// 形式或廠牌				
						rowArray[fieldIndex++] = specification + ((!"".equals(specification) && !"".equals(nameplate)) ? "/" : "") + nameplate;
					} else {
						rowArray[fieldIndex++] = "";
					}
					rowArray[fieldIndex++] = st._getMoneyFormat(rs.getString("bookValue"));
					rowArray[fieldIndex++] = Common.get(rs.getString("originallimityear"));
					rowArray[fieldIndex++] = uctls._getDataStateName(rs.getString("datastate"));				
					rowArray[fieldIndex++] = uctls._getOrganSName(rs.getString("enterOrg"));
					rowArray[fieldIndex++] = Common.get(rs.getString("engineeringno"));
				
			
				objList.add(rowArray);
				count++;
				} while (rs.next());
			}
			setStateQueryAllSuccess();		
			
			rs.close();
			
			sbSQL.setLength(0);
			sbSQL = null;			
			rs = null;
			uctls = null;
			st = null;
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.closeAll();
			db = null;
		}
		return objList;
	}
	

	
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
			if (!"".equals(getQ_differenceKind_detail())) {
				result.append(" and a.differenceKind = ").append(Common.sqlChar(getQ_differenceKind_detail()));
			}
			if (!"".equals(getQ_propertyNoS())){
				result.append(" and a.propertyNo >= ").append(Common.sqlChar(getQ_propertyNoS()));
			}
			if (!"".equals(getQ_propertyNoE())){
				result.append(" and a.propertyNo <= ").append(Common.sqlChar(getQ_propertyNoE()));
			}
			if (!"".equals(getQ_serialNoS())){
				if("MP".equals(table)){
					result.append(" and z.serialNo >= ").append(Common.sqlChar(getQ_serialNoS()));
				}else{
					result.append(" and a.serialNo >= ").append(Common.sqlChar(getQ_serialNoS()));	
				}				
			}
			if (!"".equals(getQ_serialNoE())){
				if("MP".equals(table)){
					result.append(" and z.serialNo <= ").append(Common.sqlChar(getQ_serialNoE()));
				}else{
					result.append(" and a.serialNo <= ").append(Common.sqlChar(getQ_serialNoE()));	
				}
				
			}
			
			if (!"".equals(getQ_sourceKind())) {
				result.append(" and a.sourceKind = ").append(Common.sqlChar(getQ_sourceKind()));
			}
			
			if (!"".equals(getQ_oldPropertyNoS())){
				result.append(" and a.oldpropertyNo >= ").append(Common.sqlChar(getQ_oldPropertyNoS()));
			}
			if (!"".equals(getQ_oldPropertyNoE())){
				result.append(" and a.oldpropertyNo <= ").append(Common.sqlChar(getQ_oldPropertyNoE()));
			}
			if (!"".equals(getQ_oldSerialNoS())){
				if("MP".equals(table)){
					result.append(" and z.oldSerialNo >= ").append(Common.sqlChar(getQ_oldSerialNoS()));
				}else{
					result.append(" and a.oldSerialNo >= ").append(Common.sqlChar(getQ_oldSerialNoS()));	
				}				
			}
			if (!"".equals(getQ_oldSerialNoE())){
				if("MP".equals(table)){
					result.append(" and z.oldSerialNo <= ").append(Common.sqlChar(getQ_oldSerialNoE()));
				}else{
					result.append(" and a.oldSerialNo <= ").append(Common.sqlChar(getQ_oldSerialNoE()));	
				}
				
			}
			if (!"".equals(getQ_oldlotNoS())){
				result.append(" and a.oldlotNo >= ").append(Common.sqlChar(getQ_oldlotNoS()));
			}
			if (!"".equals(getQ_oldlotNoE())){
				result.append(" and a.oldlotNo <= ").append(Common.sqlChar(getQ_oldlotNoE()));	
			}
			if (!"".equals(getQ_propertyKind())){
				result.append(" and a.propertyKind = ").append(Common.sqlChar(getQ_propertyKind()));
			}
			if (!"".equals(getQ_valuable())){
				if(!"VP".equals(table) && !"RT".equals(table) ){
					result.append(" and a.valuable = ").append(Common.sqlChar(getQ_valuable()));
				}else{
					if("Y".equals(getQ_valuable())){
						result.append(" and 1 != 1");
					}
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
			if (!"".equals(getQ_propertyName1())){
				result.append(" and a.propertyName1 like ").append(Common.sqlChar("%" + getQ_propertyName1() + "%"));
			}
			
			if (!"".equals(getQ_detail_propertyName1())){
				if("MP".equals(table)){
					result.append(" and z.propertyName1 like ").append(Common.sqlChar("%" + getQ_detail_propertyName1() + "%"));
				}else{
					result.append(" and 1 != 1");
				}
			}
			
			if (!"".equals(getQ_enterDateS())){
				result.append(" and a.enterDate >= ").append(Common.sqlChar(new Datetime().changeTaiwanYYMMDD(getQ_enterDateS(),"2")));
			}
			if (!"".equals(getQ_enterDateE())){
				result.append(" and a.enterDate <= ").append(Common.sqlChar(new Datetime().changeTaiwanYYMMDD(getQ_enterDateE(),"2")));
			}
			
			if (!"".equals(getQ_sourceDateS())){
				result.append(" and a.sourceDate >= ").append(Common.sqlChar(new Datetime().changeTaiwanYYMMDD(getQ_sourceDateS(),"2")));
			}
			if (!"".equals(getQ_sourceDateE())){
				result.append(" and a.sourceDate <= ").append(Common.sqlChar(new Datetime().changeTaiwanYYMMDD(getQ_sourceDateE(),"2")));
			}
			
			if (!"".equals(getQ_keepUnit())){
				if("MP".equals(table)){
					result.append(" and z.keepUnit = ").append(Common.sqlChar(getQ_keepUnit()));
				}else{
					result.append(" and a.keepUnit = ").append(Common.sqlChar(getQ_keepUnit()));
				}
			}
			if (!"".equals(getQ_keeper())){
				if("MP".equals(table)){
					result.append(" and z.keeper = ").append(Common.sqlChar(getQ_keeper()));
				}else{
					result.append(" and a.keeper = ").append(Common.sqlChar(getQ_keeper()));
				}
			}
			if (!"".equals(getQ_useUnit())){
				if("MP".equals(table)){
					result.append(" and z.useUnit = ").append(Common.sqlChar(getQ_useUnit()));
				}else{
					result.append(" and a.useUnit = ").append(Common.sqlChar(getQ_useUnit()));
				}
			}
			if (!"".equals(getQ_userNo())){
				if("MP".equals(table)){
					result.append(" and z.userNo = ").append(Common.sqlChar(getQ_userNo()));
				}else{
					result.append(" and a.userNo = ").append(Common.sqlChar(getQ_userNo()));
				}
			}
			if (!"".equals(getQ_originalUserNote())){
				if("MP".equals(table)){
					result.append(" and z.originalUserNote like ").append(Common.sqlChar("%" + getQ_originalUserNote() + "%"));
				}else{
					result.append(" and a.originalUserNote like ").append(Common.sqlChar("%" + getQ_originalUserNote() + "%"));
				}
			}
			if (!"".equals(getQ_doorPlate4())){
				if("BU".equals(table)){
					result.append(" and a.doorPlate4 = ").append(Common.sqlChar(getQ_doorPlate4()));
				}
			}
			if (!"".equals(getQ_place1())){
				if("MP".equals(table)){
					result.append(" and z.place1 = ").append(Common.sqlChar(getQ_place1()));
				}else{
					result.append(" and a.place1 = ").append(Common.sqlChar(getQ_place1()));
				}
			}
			if (!"".equals(getQ_place())){
				if("MP".equals(table)){
					result.append(" and z.place like ").append(Common.sqlChar("%" + getQ_place() + "%"));
				}else{
					result.append(" and a.place like ").append(Common.sqlChar("%" + getQ_place() + "%"));
				}
			}
			if (!"".equals(getQ_dataState())){
				if("MP".equals(table)){
					result.append(" and a.dataState = ").append(Common.sqlChar(getQ_dataState()));
				}else{
					result.append(" and a.dataState = ").append(Common.sqlChar(getQ_dataState()));
				}
			}
			
			if (!"".equals(getQ_fundType())){
					result.append(" and a.fundType = ").append(Common.sqlChar(getQ_fundType()));
			}
			
			if ("Y".equals(getQ_originalBuildFeeCB())){
				if(!"LA".equals(table) && !"VP".equals(table) && !"RT".equals(table) ){
					result.append(" and a.originalBuildFeeCB = ").append(Common.sqlChar(getQ_originalBuildFeeCB()));
				}else{
					result.append(" and 1 != 1");
				}
			}
			if ("Y".equals(getQ_originalDeprUnitCB())){
				if(!"LA".equals(table) && !"VP".equals(table) && !"RT".equals(table) ){
					if("MP".equals(table)){
						result.append(" and z.originalDeprUnitCB = ").append(Common.sqlChar(getQ_originalDeprUnitCB()));
					} else {
						result.append(" and a.originalDeprUnitCB = ").append(Common.sqlChar(getQ_originalDeprUnitCB()));
					}
				}else{
					result.append(" and 1 != 1");
				}
			}
			if (!"".equals(getQ_otherlimityear())){
				if("MP".equals(table)){
					result.append(" and z.otherlimityear/12 = ").append(Common.sqlChar(getQ_otherlimityear()));
				} else if("BU".equals(table) || "RF".equals(table)){
					result.append(" and a.otherlimityear/12 = ").append(Common.sqlChar(getQ_otherlimityear()));
				}
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
					result.append(" and signno like '").append(q_signLaNo).append("%'");
					
					if (!"".equals(getQ_signLaNo4S())){
						result.append(" and substring(signno,8,8) >= '").append(getQ_signLaNo4S()).append(getQ_signLaNo5S()).append("'");				
					}	
					if (!"".equals(getQ_signLaNo4E())){
						result.append(" and substring(signno,8,8) <= '").append(getQ_signLaNo4E()).append(getQ_signLaNo5E()).append("'");				
					}
					
					
				}else if (!"".equals(q_signBuNo) && "BU".equals(table)){
					result.append(" and signno like '").append(q_signBuNo).append("%'");
					
					if (!"".equals(getQ_signBuNo4S())){
						result.append(" and substring(signno,8,8) >= '").append(getQ_signBuNo4S()).append(getQ_signBuNo5S()).append("'");				
					}	
					if (!"".equals(getQ_signBuNo4E())){
						result.append(" and substring(signno,8,8) <= '").append(getQ_signBuNo4E()).append(getQ_signBuNo5E()).append("'");				
					}					
					
				}else{
					result.append(" and 1 != 1");
				}
				
			}
			
			if (!"".equals(this.getQ_notes())) {
				result.append(" and replace(a.Notes, '-', space(0)) like '%" + getQ_notes().replace("-", "") +"%'");
			}
			
			if ("1".equals(this.getRoleid())){
				if("MP".equals(table)){
					result.append(" and  (z.keeper = ").append(Common.sqlChar(this.getKeeperno()));
					result.append(" or b.editid = ").append(Common.sqlChar(this.getUserID()));
					result.append(" )");
				}else if("LA".equals(table) || "BU".equals(table) || "RF".equals(table) ){
					result.append(" and (a.keeper = ").append(Common.sqlChar(this.getKeeperno()));
					result.append(" or b.editid = ").append(Common.sqlChar(this.getUserID()));
					result.append(" )");
				}else{
					result.append(" and (a.keeper = ").append(Common.sqlChar(this.getKeeperno()));
					result.append(" or a.editid = ").append(Common.sqlChar(this.getUserID()));
					result.append(" )");
				}			
				
			}else if ("2".equals(this.getRoleid())){
				if("MP".equals(table)){
//					result.append(" and (z.keepunit = ").append(Common.sqlChar(this.getUnitID()));
//					result.append(" and ( z.keeper = ").append(Common.sqlChar(this.getKeeperno()));
//					result.append(" or b.editid = ").append(Common.sqlChar(this.getUserID()));
//					result.append(" )");
				}else if("LA".equals(table) || "BU".equals(table) || "RF".equals(table) ){
//					result.append(" and (a.keepunit = ").append(Common.sqlChar(this.getUnitID()));
					result.append(" and ( a.keeper = ").append(Common.sqlChar(this.getKeeperno()));
					result.append(" or b.editid = ").append(Common.sqlChar(this.getUserID()));
					result.append(" )");
				}else{
//					result.append(" and (a.keepunit = ").append(Common.sqlChar(this.getUnitID()));
					result.append(" and ( a.keeper = ").append(Common.sqlChar(this.getKeeperno()));
					result.append(" or a.editid = ").append(Common.sqlChar(this.getUserID()));
					result.append(" )");
				}				
				
			}else if ("3".equals(this.getRoleid())){
								
			}
			
			
			if (!"".equals(this.getQ_verify2())) {
				result.append(" and a.verify = ").append(Common.sqlChar(this.getQ_verify2()));
			}
			
//			if("Y".equals(getIsAdminManager())){
//				
//			}else{
//				result += " and a.enterorg = '" + this.getOrganID() + "'";
//			}
			
			if (!"".equals(this.getQ_specification())) {
				if ("MP".equals(table)) {
					result.append(" and a.specification like '%").append(this.getQ_specification()).append("%' ");
				} else {
					result.append(" and 1!=1 ");
				}
			}
			
			if (!"".equals(this.getQ_nameplate())) {
				if ("MP".equals(table)) {
					result.append(" and a.nameplate like '%").append(this.getQ_nameplate()).append("%' ");
				} else {
					result.append(" and 1!=1 ");
				}
			}
			
			return result.toString();
		}
		
	//============================================================

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
		
	//============================================================
		
	private String mergeDivision;
	private String mergeDivisionBatch;
	public String getMergeDivision() {return checkGet(mergeDivision);}
	public void setMergeDivision(String mergeDivision) {this.mergeDivision = checkSet(mergeDivision);}
	public String getMergeDivisionBatch() {return checkGet(mergeDivisionBatch);}
	public void setMergeDivisionBatch(String mergeDivisionBatch) {this.mergeDivisionBatch = checkSet(mergeDivisionBatch);}
	
	private String enterOrgName;
	public String getEnterOrgName() {return checkGet(enterOrgName);}
	public void setEnterOrgName(String enterOrgName) {this.enterOrgName = checkSet(enterOrgName);}
	
	private String engineering;
	private String engineeringName;
	private String propertyNoName;
	private String material;
	private String limitYear;
	private String specification;
	private String nameplate;
	private String laSignNo1;
	private String laSignNo2;
	private String laSignNo3;
	private String laSignNo4;
	private String laSignNo5;
	private String buSignNo1;
	private String buSignNo2;
	private String buSignNo3;
	private String buSignNo4;
	private String buSignNo5;
	private String propertyUnit;
	private String propertyUnitRf;
	private String propertyUnitCom;
	private String bookUnit;
	private String ifIsCommon;
	private String ifIsRate;
	private String suburb;
	private String accountNo;
	private String apportionYear;
	private String placeName;
	private String placeNote;
	private String orderNo;
	private String workNo;
	private String workNoName;
	private String otherMaterial;
	private String otherPropertyUnit;
	private String securityName;
	private String originalAccountNo;
	private String originalUstNote;
	private String manageOrgName;
	private String originalMeasure;
	private String originalAreaLa;
	private String originalAreaBu;
	private String originalHoldNumeLa;
	private String originalHoldNumeBu;
	private String originalHoldDenoLa;
	private String originalHoldDenoBu;
	private String originalHoldAreaLa;
	private String originalHoldAreaBu;
	private String useEndYM;
	private String permitReduceDate;
	private String isAccumDepr;
	private String originalUnit;
	
	private String serialNoS;
	private String serialNoE;
	private String originalAmount;
	private String bookAmount;
	
	private String measure;
	private String lotNo;		

	public String getEngineering() {return checkGet(engineering);}
	public void setEngineering(String engineering) {this.engineering = checkSet(engineering);}
	public String getEngineeringName() {return checkGet(engineeringName);}
	public void setEngineeringName(String engineeringName) {this.engineeringName = checkSet(engineeringName);}
	public String getPropertyNoName() {return checkGet(propertyNoName);}
	public void setPropertyNoName(String propertyNoName) {this.propertyNoName = checkSet(propertyNoName);}
	public String getMaterial() {return checkGet(material);}
	public void setMaterial(String material) {this.material = checkSet(material);}
	public String getLimitYear() {return checkGet(limitYear);}
	public void setLimitYear(String limitYear) {this.limitYear = checkSet(limitYear);}
	public String getSpecification() {return checkGet(specification);}
	public void setSpecification(String specification) {this.specification = checkSet(specification);}
	public String getNameplate() {return checkGet(nameplate);}
	public void setNameplate(String nameplate) {this.nameplate = checkSet(nameplate);}
	public String getLaSignNo1() {return checkGet(laSignNo1);}
	public void setLaSignNo1(String laSignNo1) {this.laSignNo1 = checkSet(laSignNo1);}
	public String getLaSignNo2() {return checkGet(laSignNo2);}
	public void setLaSignNo2(String laSignNo2) {this.laSignNo2 = checkSet(laSignNo2);}
	public String getLaSignNo3() {return checkGet(laSignNo3);}
	public void setLaSignNo3(String laSignNo3) {this.laSignNo3 = checkSet(laSignNo3);}
	public String getLaSignNo4() {return checkGet(laSignNo4);}
	public void setLaSignNo4(String laSignNo4) {this.laSignNo4 = checkSet(laSignNo4);}
	public String getLaSignNo5() {return checkGet(laSignNo5);}
	public void setLaSignNo5(String laSignNo5) {this.laSignNo5 = checkSet(laSignNo5);}
	public String getBuSignNo1() {return checkGet(buSignNo1);}
	public void setBuSignNo1(String buSignNo1) {this.buSignNo1 = checkSet(buSignNo1);}
	public String getBuSignNo2() {return checkGet(buSignNo2);}
	public void setBuSignNo2(String buSignNo2) {this.buSignNo2 = checkSet(buSignNo2);}
	public String getBuSignNo3() {return checkGet(buSignNo3);}
	public void setBuSignNo3(String buSignNo3) {this.buSignNo3 = checkSet(buSignNo3);}
	public String getBuSignNo4() {return checkGet(buSignNo4);}
	public void setBuSignNo4(String buSignNo4) {this.buSignNo4 = checkSet(buSignNo4);}
	public String getBuSignNo5() {return checkGet(buSignNo5);}
	public void setBuSignNo5(String buSignNo5) {this.buSignNo5 = checkSet(buSignNo5);}
	public String getPropertyUnit() {return checkGet(propertyUnit);}
	public void setPropertyUnit(String propertyUnit) {this.propertyUnit = checkSet(propertyUnit);}
	public String getPropertyUnitRf() {return checkGet(propertyUnitRf);}
	public void setPropertyUnitRf(String propertyUnitRf) {this.propertyUnitRf = checkSet(propertyUnitRf);}
	public String getPropertyUnitCom() {return checkGet(propertyUnitCom);}
	public void setPropertyUnitCom(String propertyUnitCom) {this.propertyUnitCom = checkSet(propertyUnitCom);}	
	public String getBookUnit() {return checkGet(bookUnit);}
	public void setBookUnit(String bookUnit) {this.bookUnit = checkSet(bookUnit);}
	public String getIfIsCommon() {return checkGet(ifIsCommon);}
	public void setIfIsCommon(String ifIsCommon) {this.ifIsCommon = checkSet(ifIsCommon);}
	public String getIfIsRate() {return checkGet(ifIsRate);}
	public void setIfIsRate(String ifIsRate) {this.ifIsRate = checkSet(ifIsRate);}
	public String getSuburb() {return checkGet(suburb);}
	public void setSuburb(String suburb) {this.suburb = checkSet(suburb);}
	public String getAccountNo() {return checkGet(accountNo);}
	public void setAccountNo(String accountNo) {this.accountNo = checkSet(accountNo);}
	public String getApportionYear() {return checkGet(apportionYear);}
	public void setApportionYear(String apportionYear) {this.apportionYear = checkSet(apportionYear);}
	public String getPlaceName() {return checkGet(placeName);}
	public void setPlaceName(String placeName) {this.placeName = checkSet(placeName);}
	public String getPlaceNote() {return checkGet(placeNote);}
	public void setPlaceNote(String placeNote) {this.placeNote = checkSet(placeNote);}
	public String getOrderNo() {return checkGet(orderNo);}
	public void setOrderNo(String orderNo) {this.orderNo = checkSet(orderNo);}
	public String getWorkNo() {return checkGet(workNo);}
	public void setWorkNo(String workNo) {this.workNo = checkSet(workNo);}
	public String getWorkNoName() {return checkGet(workNoName);}
	public void setWorkNoName(String workNoName) {this.workNoName = checkSet(workNoName);}
	public String getOtherMaterial() {return checkGet(otherMaterial);}
	public void setOtherMaterial(String otherMaterial) {this.otherMaterial = checkSet(otherMaterial);}
	public String getOtherPropertyUnit() {return checkGet(otherPropertyUnit);}
	public void setOtherPropertyUnit(String otherPropertyUnit) {this.otherPropertyUnit = checkSet(otherPropertyUnit);}
	public String getSecurityName() {return checkGet(securityName);}
	public void setSecurityName(String securityName) {this.securityName = checkSet(securityName);}
	public String getOriginalAccountNo() {return checkGet(originalAccountNo);}
	public void setOriginalAccountNo(String originalAccountNo) {this.originalAccountNo = checkSet(originalAccountNo);}
	public String getOriginalUstNote() {return checkGet(originalUstNote);}
	public void setOriginalUstNote(String originalUstNote) {this.originalUstNote = checkSet(originalUstNote);}
	public String getManageOrgName() {return checkGet(manageOrgName);}
	public void setManageOrgName(String manageOrgName) {this.manageOrgName = checkSet(manageOrgName);}
	public String getOriginalMeasure() {return checkGet(originalMeasure);}
	public void setOriginalMeasure(String originalMeasure) {this.originalMeasure = checkSet(originalMeasure);}
	public String getOriginalAreaLa() {return checkGet(originalAreaLa);}
	public void setOriginalAreaLa(String originalAreaLa) {this.originalAreaLa = checkSet(originalAreaLa);}
	public String getOriginalAreaBu() {return checkGet(originalAreaBu);}
	public void setOriginalAreaBu(String originalAreaBu) {this.originalAreaBu = checkSet(originalAreaBu);}
	public String getOriginalHoldNumeLa() {return checkGet(originalHoldNumeLa);}
	public void setOriginalHoldNumeLa(String originalHoldNumeLa) {this.originalHoldNumeLa = checkSet(originalHoldNumeLa);}
	public String getOriginalHoldNumeBu() {return checkGet(originalHoldNumeBu);}
	public void setOriginalHoldNumeBu(String originalHoldNumeBu) {this.originalHoldNumeBu = checkSet(originalHoldNumeBu);}
	public String getOriginalHoldDenoLa() {return checkGet(originalHoldDenoLa);}
	public void setOriginalHoldDenoLa(String originalHoldDenoLa) {this.originalHoldDenoLa = checkSet(originalHoldDenoLa);}
	public String getOriginalHoldDenoBu() {return checkGet(originalHoldDenoBu);}
	public void setOriginalHoldDenoBu(String originalHoldDenoBu) {this.originalHoldDenoBu = checkSet(originalHoldDenoBu);}
	public String getOriginalHoldAreaLa() {return checkGet(originalHoldAreaLa);}
	public void setOriginalHoldAreaLa(String originalHoldAreaLa) {this.originalHoldAreaLa = checkSet(originalHoldAreaLa);}
	public String getOriginalHoldAreaBu() {return checkGet(originalHoldAreaBu);}
	public void setOriginalHoldAreaBu(String originalHoldAreaBu) {this.originalHoldAreaBu = checkSet(originalHoldAreaBu);}
	public String getUseEndYM() {return checkGet(useEndYM);}
	public void setUseEndYM(String useEndYM) {this.useEndYM = checkSet(useEndYM);}
	public String getPermitReduceDate() {return checkGet(permitReduceDate);}
	public void setPermitReduceDate(String permitReduceDate) {this.permitReduceDate = checkSet(permitReduceDate);}
	public String getIsAccumDepr() {return checkGet(isAccumDepr);}
	public void setIsAccumDepr(String isAccumDepr) {this.isAccumDepr = checkSet(isAccumDepr);}
	public String getOriginalUnit() {return checkGet(originalUnit);}
	public void setOriginalUnit(String originalUnit) {this.originalUnit = checkSet(originalUnit);}
	public String getSerialNoS() {return checkGet(serialNoS);}
	public void setSerialNoS(String serialNoS) {this.serialNoS = checkSet(serialNoS);}
	public String getSerialNoE() {return checkGet(serialNoE);}
	public void setSerialNoE(String serialNoE) {this.serialNoE = checkSet(serialNoE);}
	public String getOriginalAmount() {return checkGet(originalAmount);}
	public void setOriginalAmount(String originalAmount) {this.originalAmount = checkSet(originalAmount);}
	public String getBookAmount() {return checkGet(bookAmount);}
	public void setBookAmount(String bookAmount) {this.bookAmount = checkSet(bookAmount);}
	public String getMeasure() {return checkGet(measure);}
	public void setMeasure(String measure) {this.measure = checkSet(measure);}
	public String getLotNo() {return checkGet(lotNo);}
	public void setLotNo(String lotNo) {this.lotNo = checkSet(lotNo);}
	
	//=============================================================

	private String propertyName;
	private String doorplate;
	private String originalBasis;
	private String originalDate;
	private String useSeparate;
	private String useKind;
	private String oriUseSeparate;
	private String oriUseKind;
	private String field;
	private String landRule;
	private String oldOwner;
	private String useState1;
	private String notes1;


	private String useUnitName;
	private String useUnit1;
	
	
	private String approveDate;
	private String approveDoc;
	private String grantValue;
	private String articleName;
	private String storeNo;
	private String storeNoName;
	private String picture;

	private String checkClosing;
	private String checkVerify;
	private String newScrapValue;

	private String checkOriginalAmount;
	private String checkOriginalUnit;
	private String checkOriginalBV;
	private String checkDeprMethod;
	private String serialNoAttachment2;

	private String filestoreLocation;
	private String detailOriginalAmount;
	private String detailOriginalBV;
	private String detailBookAmount;
	private String detailBookValue;
	private String oldSerialNoS;
	private String oldSerialNoE;

	private String trueOriginalAmount = "";
	private String trueOriginalUnit = "";
	private String trueOriginalBV = "";
	private String computerType;

	private String useState;
	private String oldPropertyName;
	private String damageDate;
	private String damageExpire;
	private String damageMark;
	private String apportionEndYM;
	private String accumDeprYM;
	private String propertyType;
	private String doorPlate3;
	private String initDtl;
	private String originalUseBureau;
	private String originalKeepBureau;
	private String originalUseUnit1;
	private String keepUnit1;
	

	private String sourceKindName;
	private String meat;
	private String proofDoc1;
	private String holdNume1;
	private String holdDeno2;
	private String registerCause;
	private String registerCauseName;
	private String registerDate;
	private String setPeriod;
	private String commonObligee;
	private String setPerson;
	private String payDate;
	private String interest;
	private String rent;
	private String checkEnterOrg;
	private String verifyError;
	private String caseName;
	private String writeDate;
	private String writeUnit;
	private String manageNo;
	private String summonsNo;
	private String causeName;
	private String fundTypeName;
	private String securityMeat;
	private String securityAddr;
	private String securityItem;
	private String securityTime;
	private String securityOrg;
	private String securityOrgName;
	private String securityDoc;
	private String originalSheet;
	private String bookSheet;
	private String capitalStock;
	private String checkDataState;
	private String oldVerify;


	private String setArea;
	private String registerNo1;
	private String rightKind;
	private String registerNo2;
	private String setRightScope;
	private String setObligor;
	private String landPurpose;
	private String doorPlate;
	private String buildingArea;
	private String buildingOwner;
	private String buildingPurpose;
	
	private String serialNo1;
	private String oldSerialNo1;
	private String originalUA;
	private String originalUV;
	private String originalProofS;
	private String originalProofE;
	private String bookUnitAmount;
	private String bookUnitValue;
	private String proofS;
	private String proofE;
	
	

	public String getInitDtl(){ return checkGet(initDtl); }
	public void setInitDtl(String s){ initDtl=checkSet(s); }
	public String getDamageDate(){ return checkGet(damageDate); }
	public void setDamageDate(String s){ damageDate=checkSet(s); }
	public String getDamageExpire(){ return checkGet(damageExpire); }
	public void setDamageExpire(String s){ damageExpire=checkSet(s); }
	public String getDamageMark(){ if (checkGet(damageMark).equals("1")) return "checked"; else return checkGet(damageMark); }
	public void setDamageMark(String s){ damageMark=checkSet(s); }
	public String getApportionEndYM(){ return checkGet(apportionEndYM); }
	public void setApportionEndYM(String s){ apportionEndYM=checkSet(s); }
	public String getAccumDeprYM(){ return checkGet(accumDeprYM); }
	public void setAccumDeprYM(String s){ accumDeprYM=checkSet(s); }
	public String getPropertyType(){ return checkGet(propertyType); }
	public void setPropertyType(String s){ propertyType=checkSet(s); }
	public String getDoorPlate3(){ return checkGet(doorPlate3); }
	public void setDoorPlate3(String s){ doorPlate3=checkSet(s); }
	public String getUseState(){ return checkGet(useState); }
	public void setUseState(String s){ useState=checkSet(s); }
	public String getOldPropertyName(){ return checkGet(oldPropertyName); }
	public void setOldPropertyName(String s){ oldPropertyName=checkSet(s); }
	public String getOriginalUseBureau(){ return checkGet(originalUseBureau); }
	public void setOriginalUseBureau(String s){ originalUseBureau=checkSet(s); }
	public String getOriginalKeepBureau(){ return checkGet(originalKeepBureau); }
	public void setOriginalKeepBureau(String s){ originalKeepBureau=checkSet(s); }
	public String getOriginalUseUnit1() {return originalUseUnit1;}
	public void setOriginalUseUnit1(String originalUseUnit1) {this.originalUseUnit1 = originalUseUnit1;}
	public String getKeepUnit1() {return keepUnit1;}
	public void setKeepUnit1(String keepUnit1) {this.keepUnit1 = keepUnit1;}
	

	public String getPropertyName() {return checkGet(propertyName);}
	public void setPropertyName(String propertyName) {this.propertyName = checkSet(propertyName);}
	public String getDoorplate() {return checkGet(doorplate);}
	public void setDoorplate(String doorplate) {this.doorplate = checkSet(doorplate);}
	public String getOriginalBasis() {return checkGet(originalBasis);}
	public void setOriginalBasis(String originalBasis) {this.originalBasis = checkSet(originalBasis);}
	public String getOriginalDate() {return checkGet(originalDate);}
	public void setOriginalDate(String originalDate) {this.originalDate = checkSet(originalDate);}
	public String getUseSeparate() {return checkGet(useSeparate);}
	public void setUseSeparate(String useSeparate) {this.useSeparate = checkSet(useSeparate);}
	public String getUseKind() {return checkGet(useKind);}
	public void setUseKind(String useKind) {this.useKind = checkSet(useKind);}
	public String getOriUseSeparate() {return checkGet(oriUseSeparate);}
	public void setOriUseSeparate(String oriUseSeparate) {this.oriUseSeparate = checkSet(oriUseSeparate);}
	public String getOriUseKind() {return checkGet(oriUseKind);}
	public void setOriUseKind(String oriUseKind) {this.oriUseKind = checkSet(oriUseKind);}
	public String getField() {return checkGet(field);}
	public void setField(String field) {this.field = checkSet(field);}
	public String getLandRule() {return checkGet(landRule);}
	public void setLandRule(String landRule) {this.landRule = checkSet(landRule);}
	public String getOldOwner() {return checkGet(oldOwner);}
	public void setOldOwner(String oldOwner) {this.oldOwner = checkSet(oldOwner);}
	public String getUseState1() {return checkGet(useState1);}
	public void setUseState1(String useState1) {this.useState1 = checkSet(useState1);}
	public String getNotes1() {return checkGet(notes1);}
	public void setNotes1(String notes1) {this.notes1 = checkSet(notes1);}
	public String getUseUnitName() {return checkGet(useUnitName);}
	public void setUseUnitName(String useUnitName) {this.useUnitName = checkSet(useUnitName);}
	public String getUseUnit1() {return checkGet(useUnit1);}
	public void setUseUnit1(String useUnit1) {this.useUnit1 = checkSet(useUnit1);}
	public String getApproveDate() {return checkGet(approveDate);}
	public void setApproveDate(String approveDate) {this.approveDate = checkSet(approveDate);}
	public String getApproveDoc() {return checkGet(approveDoc);}
	public void setApproveDoc(String approveDoc) {this.approveDoc = checkSet(approveDoc);}
	public String getGrantValue() {return checkGet(grantValue);}
	public void setGrantValue(String grantValue) {this.grantValue = checkSet(grantValue);}
	public String getArticleName() {return checkGet(articleName);}
	public void setArticleName(String articleName) {this.articleName = checkSet(articleName);}
	public String getStoreNo() {return checkGet(storeNo);}
	public void setStoreNo(String storeNo) {this.storeNo = checkSet(storeNo);}
	public String getStoreNoName() {return checkGet(storeNoName);}
	public void setStoreNoName(String storeNoName) {this.storeNoName = checkSet(storeNoName);}
	public String getPicture() {return checkGet(picture);}
	public void setPicture(String picture) {this.picture = checkSet(picture);}
	public String getCheckClosing() {return checkGet(checkClosing);}
	public void setCheckClosing(String checkClosing) {this.checkClosing = checkSet(checkClosing);}
	public String getCheckVerify() {return checkGet(checkVerify);}
	public void setCheckVerify(String checkVerify) {this.checkVerify = checkSet(checkVerify);}
	public String getNewScrapValue() {return checkGet(newScrapValue);}
	public void setNewScrapValue(String newScrapValue) {this.newScrapValue = checkSet(newScrapValue);}
	public String getCheckOriginalAmount() {return checkGet(checkOriginalAmount);}
	public void setCheckOriginalAmount(String checkOriginalAmount) {this.checkOriginalAmount = checkSet(checkOriginalAmount);}
	public String getCheckOriginalUnit() {return checkGet(checkOriginalUnit);}
	public void setCheckOriginalUnit(String checkOriginalUnit) {this.checkOriginalUnit = checkSet(checkOriginalUnit);}
	public String getCheckOriginalBV() {return checkGet(checkOriginalBV);}
	public void setCheckOriginalBV(String checkOriginalBV) {this.checkOriginalBV = checkSet(checkOriginalBV);}
	public String getCheckDeprMethod() {return checkGet(checkDeprMethod);}
	public void setCheckDeprMethod(String checkDeprMethod) {this.checkDeprMethod = checkSet(checkDeprMethod);}
	public String getSerialNoAttachment2() {return checkGet(serialNoAttachment2);}
	public void setSerialNoAttachment2(String serialNoAttachment2) {this.serialNoAttachment2 = checkSet(serialNoAttachment2);}
	public String getFilestoreLocation() {return checkGet(filestoreLocation);}
	public void setFilestoreLocation(String filestoreLocation) {this.filestoreLocation = checkSet(filestoreLocation);}
	public String getDetailOriginalAmount() {return checkGet(detailOriginalAmount);}
	public void setDetailOriginalAmount(String detailOriginalAmount) {this.detailOriginalAmount = checkSet(detailOriginalAmount);}
	public String getDetailOriginalBV() {return checkGet(detailOriginalBV);}
	public void setDetailOriginalBV(String detailOriginalBV) {this.detailOriginalBV = checkSet(detailOriginalBV);}
	public String getDetailBookAmount() {return checkGet(detailBookAmount);}
	public void setDetailBookAmount(String detailBookAmount) {this.detailBookAmount = checkSet(detailBookAmount);}
	public String getDetailBookValue() {return checkGet(detailBookValue);}
	public void setDetailBookValue(String detailBookValue) {this.detailBookValue = checkSet(detailBookValue);}
	public String getOldSerialNoS() {return checkGet(oldSerialNoS);}
	public void setOldSerialNoS(String oldSerialNoS) {this.oldSerialNoS = checkSet(oldSerialNoS);}
	public String getOldSerialNoE() {return checkGet(oldSerialNoE);}
	public void setOldSerialNoE(String oldSerialNoE) {this.oldSerialNoE = checkSet(oldSerialNoE);}
	public String getTrueOriginalAmount() {return checkGet(trueOriginalAmount);}
	public void setTrueOriginalAmount(String trueOriginalAmount) {this.trueOriginalAmount = checkSet(trueOriginalAmount);}
	public String getTrueOriginalUnit() {return checkGet(trueOriginalUnit);}
	public void setTrueOriginalUnit(String trueOriginalUnit) {this.trueOriginalUnit = checkSet(trueOriginalUnit);}
	public String getTrueOriginalBV() {return checkGet(trueOriginalBV);}
	public void setTrueOriginalBV(String trueOriginalBV) {this.trueOriginalBV = checkSet(trueOriginalBV);}
	public String getComputerType() {return checkGet(computerType);}
	public void setComputerType(String computerType) {this.computerType = checkSet(computerType);}


	public String getSourceKindName() {return checkGet(sourceKindName);}
	public void setSourceKindName(String sourceKindName) {this.sourceKindName = checkSet(sourceKindName);}
	public String getMeat() {return checkGet(meat);}
	public void setMeat(String meat) {this.meat = checkSet(meat);}
	public String getProofDoc1() {return checkGet(proofDoc1);}
	public void setProofDoc1(String proofDoc1) {this.proofDoc1 = checkSet(proofDoc1);}
	public String getHoldNume1() {return checkGet(holdNume1);}
	public void setHoldNume1(String holdNume1) {this.holdNume1 = checkSet(holdNume1);}
	public String getHoldDeno2() {return checkGet(holdDeno2);}
	public void setHoldDeno2(String holdDeno2) {this.holdDeno2 = checkSet(holdDeno2);}
	public String getRegisterCause() {return checkGet(registerCause);}
	public void setRegisterCause(String registerCause) {this.registerCause = checkSet(registerCause);}
	public String getRegisterCauseName() {return checkGet(registerCauseName);}
	public void setRegisterCauseName(String registerCauseName) {this.registerCauseName = checkSet(registerCauseName);}
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
	public String getCheckEnterOrg() {return checkGet(checkEnterOrg);}
	public void setCheckEnterOrg(String checkEnterOrg) {this.checkEnterOrg = checkSet(checkEnterOrg);}
	public String getVerifyError() {return checkGet(verifyError);}
	public void setVerifyError(String verifyError) {this.verifyError = checkSet(verifyError);}
	public String getCaseName() {return checkGet(caseName);}
	public void setCaseName(String caseName) {this.caseName = checkSet(caseName);}
	public String getWriteDate() {return checkGet(writeDate);}
	public void setWriteDate(String writeDate) {this.writeDate = checkSet(writeDate);}
	public String getWriteUnit() {return checkGet(writeUnit);}
	public void setWriteUnit(String writeUnit) {this.writeUnit = checkSet(writeUnit);}
	public String getManageNo() {return checkGet(manageNo);}
	public void setManageNo(String manageNo) {this.manageNo = checkSet(manageNo);}
	public String getSummonsNo() {return checkGet(summonsNo);}
	public void setSummonsNo(String summonsNo) {this.summonsNo = checkSet(summonsNo);}
	public String getCauseName() {return checkGet(causeName);}
	public void setCauseName(String causeName) {this.causeName = checkSet(causeName);}
	public String getFundTypeName() {return checkGet(fundTypeName);}
	public void setFundTypeName(String fundTypeName) {this.fundTypeName = checkSet(fundTypeName);}
	public String getSecurityMeat() {return checkGet(securityMeat);}
	public void setSecurityMeat(String securityMeat) {this.securityMeat = checkSet(securityMeat);}
	public String getSecurityAddr() {return checkGet(securityAddr);}
	public void setSecurityAddr(String securityAddr) {this.securityAddr = checkSet(securityAddr);}
	public String getSecurityItem() {return checkGet(securityItem);}
	public void setSecurityItem(String securityItem) {this.securityItem = checkSet(securityItem);}
	public String getSecurityTime() {return checkGet(securityTime);}
	public void setSecurityTime(String securityTime) {this.securityTime = checkSet(securityTime);}
	public String getSecurityOrg() {return checkGet(securityOrg);}
	public void setSecurityOrg(String securityOrg) {this.securityOrg = checkSet(securityOrg);}
	public String getSecurityOrgName() {return checkGet(securityOrgName);}
	public void setSecurityOrgName(String securityOrgName) {this.securityOrgName = checkSet(securityOrgName);}
	public String getSecurityDoc() {return checkGet(securityDoc);}
	public void setSecurityDoc(String securityDoc) {this.securityDoc = checkSet(securityDoc);}
	public String getOriginalSheet() {return checkGet(originalSheet);}
	public void setOriginalSheet(String originalSheet) {this.originalSheet = checkSet(originalSheet);}
	public String getBookSheet() {return checkGet(bookSheet);}
	public void setBookSheet(String bookSheet) {this.bookSheet = checkSet(bookSheet);}
	public String getCapitalStock() {return checkGet(capitalStock);}
	public void setCapitalStock(String capitalStock) {this.capitalStock = checkSet(capitalStock);}
	public String getCheckDataState() {return checkGet(checkDataState);}
	public void setCheckDataState(String checkDataState) {this.checkDataState = checkSet(checkDataState);}
	public String getOldVerify() {return checkGet(oldVerify);}
	public void setOldVerify(String oldVerify) {this.oldVerify = checkSet(oldVerify);}


	public String getSetArea() {return checkGet(setArea);}
	public void setSetArea(String setArea) {this.setArea = checkSet(setArea);}
	public String getRegisterNo1() {return checkGet(registerNo1);}
	public void setRegisterNo1(String registerNo1) {this.registerNo1 = checkSet(registerNo1);}
	public String getRightKind() {return checkGet(rightKind);}
	public void setRightKind(String rightKind) {this.rightKind = checkSet(rightKind);}
	public String getRegisterNo2() {return checkGet(registerNo2);}
	public void setRegisterNo2(String registerNo2) {this.registerNo2 = checkSet(registerNo2);}
	public String getSetRightScope() {return checkGet(setRightScope);}
	public void setSetRightScope(String setRightScope) {this.setRightScope = checkSet(setRightScope);}
	public String getSetObligor() {return checkGet(setObligor);}
	public void setSetObligor(String setObligor) {this.setObligor = checkSet(setObligor);}
	public String getLandPurpose() {return checkGet(landPurpose);}
	public void setLandPurpose(String landPurpose) {this.landPurpose = checkSet(landPurpose);}
	public String getDoorPlate() {return checkGet(doorPlate);}
	public void setDoorPlate(String doorPlate) {this.doorPlate = checkSet(doorPlate);}
	public String getBuildingArea() {return checkGet(buildingArea);}
	public void setBuildingArea(String buildingArea) {this.buildingArea = checkSet(buildingArea);}
	public String getBuildingOwner() {return checkGet(buildingOwner);}
	public void setBuildingOwner(String buildingOwner) {this.buildingOwner = checkSet(buildingOwner);}
	public String getBuildingPurpose() {return checkGet(buildingPurpose);}
	public void setBuildingPurpose(String buildingPurpose) {this.buildingPurpose = checkSet(buildingPurpose);}
	public String getIsAdminManager() {return checkGet(isAdminManager);}
	public void setIsAdminManager(String isAdminManager) {this.isAdminManager = checkSet(isAdminManager);}
	public String getIsOrganManager() {return checkGet(isOrganManager);}
	public void setIsOrganManager(String isOrganManager) {this.isOrganManager = checkSet(isOrganManager);}
	public String getOrganID() {return checkGet(organID);}
	public void setOrganID(String organID) {this.organID = checkSet(organID);}
	public String getSerialNo1() {return checkGet(serialNo1);}
	public void setSerialNo1(String serialNo1) {this.serialNo1 = checkSet(serialNo1);}
	public String getOldSerialNo1() {return checkGet(oldSerialNo1);}
	public void setOldSerialNo1(String oldSerialNo1) {this.oldSerialNo1 = checkSet(oldSerialNo1);}
	public String getOriginalUA() {return checkGet(originalUA);}
	public void setOriginalUA(String originalUA) {this.originalUA = checkSet(originalUA);}
	public String getOriginalUV() {return checkGet(originalUV);}
	public void setOriginalUV(String originalUV) {this.originalUV = checkSet(originalUV);}
	public String getOriginalProofS() {return checkGet(originalProofS);}
	public void setOriginalProofS(String originalProofS) {this.originalProofS = checkSet(originalProofS);}
	public String getOriginalProofE() {return checkGet(originalProofE);}
	public void setOriginalProofE(String originalProofE) {this.originalProofE = checkSet(originalProofE);}
	public String getBookUnitAmount() {return checkGet(bookUnitAmount);}
	public void setBookUnitAmount(String bookUnitAmount) {this.bookUnitAmount = checkSet(bookUnitAmount);}
	public String getBookUnitValue() {return checkGet(bookUnitValue);}
	public void setBookUnitValue(String bookUnitValue) {this.bookUnitValue = checkSet(bookUnitValue);}
	public String getProofS() {return checkGet(proofS);}
	public void setProofS(String proofS) {this.proofS = checkSet(proofS);}
	public String getProofE() {return checkGet(proofE);}
	public void setProofE(String proofE) {this.proofE = checkSet(proofE);}

	String signNo1;
	String signNo2;
	String signNo3;
	String signNo4;
	String signNo5;

	public String getSignNo1(){ return checkGet(signNo1); }
	public void setSignNo1(String s){ signNo1=checkSet(s); }
	public String getSignNo2(){ return checkGet(signNo2); }
	public void setSignNo2(String s){ signNo2=checkSet(s); }
	public String getSignNo3(){ return checkGet(signNo3); }
	public void setSignNo3(String s){ signNo3=checkSet(s); }
	public String getSignNo4(){ return checkGet(signNo4); }
	public void setSignNo4(String s){ signNo4=checkSet(s); }
	public String getSignNo5(){ return checkGet(signNo5); }
	public void setSignNo5(String s){ signNo5=checkSet(s); }
	
	private String netUnit;
	public String getNetUnit() {return checkGet(netUnit);}
	public void setNetUnit(String netUnit) {this.netUnit = checkSet(netUnit);}
	
	
	private String enterOrg;
	private String ownership;
	private String caseNo;
	private String differenceKind;
	private String engineeringNo;
	private String engineeringNoName;
	private String caseSerialNo;
	private String propertyNo;
	private String serialNo;
	private String otherLimitYear;
	private String propertyName1;
	private String propertyName1_movable;
	private String signNo;
	private String doorPlate1;
	private String doorPlate2;
	private String doorPlate4;
	private String doorPlatevillage1;
	private String doorPlatevillage2;
	private String doorplateRd1;
	private String doorplateRd2;
	private String doorplateSec;
	private String doorplateLn;
	private String doorplateAly;
	private String doorplateNo;
	private String doorplateFloor1;
	private String doorplateFloor2;
	private String buildStyle;
	private String cause;
	private String cause1;
	private String enterDate;
	private String dataState;
	private String reduceDate;
	private String reduceCause;
	private String reduceCause1;
	private String verify;
	private String propertyKind;
	private String fundType;
	private String valuable;
	private String taxCredit;
	private String originalCArea;
	private String originalSArea;
	private String originalArea;
	private String originalHoldNume;
	private String originalHoldDeno;
	private String originalHoldArea;
	private String CArea;
	private String SArea;
	private String area;
	private String holdNume;
	private String holdDeno;
	private String holdArea;
	private String originalBV;
	private String originalNote;
	private String accountingTitle;
	private String bookValue;
	private String netValue;
	private String fundsSource;
	private String fundsSource1;
	private String ownershipDate;
	private String ownershipCause;
	private String nonProof;
	private String ownershipNote;
	private String buildDate;
	private String floor1;
	private String floor2;
	private String stuff;
	private String buyDate;
	private String sourceKind;
	private String sourceDate;
	private String sourceDoc;
	private String manageOrg;
	
	private String originalBuildFeeCB;
	private String originalDeprUnitCB;
	private String originalDeprPark;
	private String originalDeprUnit;
	private String originalDeprAccounts;
	private String originalScrapValue;
	private String originalDeprAmount;
	private String originalaccumdepr1;	// 本年度累計折舊
	private String originalaccumdepr2;	// 以前年度累計折舊
	private String originalAccumDepr;
	private String originalApportionMonth;
	private String originalMonthDepr;
	private String deprMethod;
	private String buildFeeCB;
	private String deprUnitCB;
	private String deprPark;
	private String deprUnit;
	private String deprAccounts;
	private String scrapValue;
	private String deprAmount;
	private String accumDepr;
	private String apportionMonth;
	private String monthDepr;
	private String noDeprSet;
	private String appraiseDate;
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
	private String useLicense;
	private String escrowOriValue;
	private String escrowOriAccumDepr;
	private String oldPropertyNo;
	private String oldSerialNo;
	private String notes;
	private String editID;
	private String editDate;
	private String editTime;
	public String getEnterOrg() {return checkGet(enterOrg);}
	public void setEnterOrg(String enterOrg) {this.enterOrg = checkSet(enterOrg);}
	public String getOwnership() {return checkGet(ownership);}
	public void setOwnership(String ownership) {this.ownership = checkSet(ownership);}
	public String getCaseNo() {return checkGet(caseNo);}
	public void setCaseNo(String caseNo) {this.caseNo = checkSet(caseNo);}
	public String getDifferenceKind() {return checkGet(differenceKind);}
	public void setDifferenceKind(String differenceKind) {this.differenceKind = checkSet(differenceKind);}
	public String getEngineeringNo() {return checkGet(engineeringNo);}
	public void setEngineeringNo(String engineeringNo) {this.engineeringNo = checkSet(engineeringNo);}
	public String getEngineeringNoName() {return checkGet(engineeringNoName);}
	public void setEngineeringNoName(String engineeringNoName) {this.engineeringNoName = checkSet(engineeringNoName);}
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
	public String getPropertyName1_movable() {return checkGet(propertyName1_movable);}
	public void setPropertyName1_movable(String propertyName1_movable) {this.propertyName1_movable = checkSet(propertyName1_movable);}
	public String getSignNo() {return checkGet(signNo);}
	public void setSignNo(String signNo) {this.signNo = checkSet(signNo);}
	public String getDoorPlate1() {return checkGet(doorPlate1);}
	public void setDoorPlate1(String doorPlate1) {this.doorPlate1 = checkSet(doorPlate1);}
	public String getDoorPlate2() {return checkGet(doorPlate2);}
	public void setDoorPlate2(String doorPlate2) {this.doorPlate2 = checkSet(doorPlate2);}
	public String getDoorPlate4() {return checkGet(doorPlate4);}
	public void setDoorPlate4(String doorPlate4) {this.doorPlate4 = checkSet(doorPlate4);}
	public String getDoorPlatevillage1() {return checkGet(doorPlatevillage1);}
	public void setDoorPlatevillage1(String doorPlatevillage1) {this.doorPlatevillage1 = checkSet(doorPlatevillage1);}
	public String getDoorPlatevillage2() {return checkGet(doorPlatevillage2);}
	public void setDoorPlatevillage2(String doorPlatevillage2) {this.doorPlatevillage2 = checkSet(doorPlatevillage2);}
	public String getDoorplateRd1() {return checkGet(doorplateRd1);}
	public void setDoorplateRd1(String doorplateRd1) {this.doorplateRd1 = checkSet(doorplateRd1);}
	public String getDoorplateRd2() {return checkGet(doorplateRd2);}
	public void setDoorplateRd2(String doorplateRd2) {this.doorplateRd2 = checkSet(doorplateRd2);}
	public String getDoorplateSec() {return checkGet(doorplateSec);}
	public void setDoorplateSec(String doorplateSec) {this.doorplateSec = checkSet(doorplateSec);}
	public String getDoorplateLn() {return checkGet(doorplateLn);}
	public void setDoorplateLn(String doorplateLn) {this.doorplateLn = checkSet(doorplateLn);}
	public String getDoorplateAly() {return checkGet(doorplateAly);}
	public void setDoorplateAly(String doorplateAly) {this.doorplateAly = checkSet(doorplateAly);}
	public String getDoorplateNo() {return checkGet(doorplateNo);}
	public void setDoorplateNo(String doorplateNo) {this.doorplateNo = checkSet(doorplateNo);}
	public String getDoorplateFloor1() {return checkGet(doorplateFloor1);}
	public void setDoorplateFloor1(String doorplateFloor1) {this.doorplateFloor1 = checkSet(doorplateFloor1);}
	public String getDoorplateFloor2() {return checkGet(doorplateFloor2);}
	public void setDoorplateFloor2(String doorplateFloor2) {this.doorplateFloor2 = checkSet(doorplateFloor2);}
	public String getBuildStyle() {return checkGet(buildStyle);}
	public void setBuildStyle(String buildStyle) {this.buildStyle = checkSet(buildStyle);}
	public String getCause() {return checkGet(cause);}
	public void setCause(String cause) {this.cause = checkSet(cause);}
	public String getCause1() {return checkGet(cause1);}
	public void setCause1(String cause1) {this.cause1 = checkSet(cause1);}
	public String getEnterDate() {return checkGet(enterDate);}
	public void setEnterDate(String enterDate) {this.enterDate = checkSet(enterDate);}
	public String getDataState() {return checkGet(dataState);}
	public void setDataState(String dataState) {this.dataState = checkSet(dataState);}
	public String getReduceDate() {return checkGet(reduceDate);}
	public void setReduceDate(String reduceDate) {this.reduceDate = checkSet(reduceDate);}
	public String getReduceCause() {return checkGet(reduceCause);}
	public void setReduceCause(String reduceCause) {this.reduceCause = checkSet(reduceCause);}
	public String getReduceCause1() {return checkGet(reduceCause1);}
	public void setReduceCause1(String reduceCause1) {this.reduceCause1 = checkSet(reduceCause1);}
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
	public String getOriginalCArea() {return checkGet(originalCArea);}
	public void setOriginalCArea(String originalCArea) {this.originalCArea = checkSet(originalCArea);}
	public String getOriginalSArea() {return checkGet(originalSArea);}
	public void setOriginalSArea(String originalSArea) {this.originalSArea = checkSet(originalSArea);}
	public String getOriginalArea() {return checkGet(originalArea);}
	public void setOriginalArea(String originalArea) {this.originalArea = checkSet(originalArea);}
	public String getOriginalHoldNume() {return checkGet(originalHoldNume);}
	public void setOriginalHoldNume(String originalHoldNume) {this.originalHoldNume = checkSet(originalHoldNume);}
	public String getOriginalHoldDeno() {return checkGet(originalHoldDeno);}
	public void setOriginalHoldDeno(String originalHoldDeno) {this.originalHoldDeno = checkSet(originalHoldDeno);}
	public String getOriginalHoldArea() {return checkGet(originalHoldArea);}
	public void setOriginalHoldArea(String originalHoldArea) {this.originalHoldArea = checkSet(originalHoldArea);}
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
	public String getOriginalBV() {return checkGet(originalBV);}
	public void setOriginalBV(String originalBV) {this.originalBV = checkSet(originalBV);}
	public String getOriginalNote() {return checkGet(originalNote);}
	public void setOriginalNote(String originalNote) {this.originalNote = checkSet(originalNote);}
	public String getAccountingTitle() {return checkGet(accountingTitle);}
	public void setAccountingTitle(String accountingTitle) {this.accountingTitle = checkSet(accountingTitle);}
	public String getBookValue() {return checkGet(bookValue);}
	public void setBookValue(String bookValue) {this.bookValue = checkSet(bookValue);}
	public String getNetValue() {return checkGet(netValue);}
	public void setNetValue(String netValue) {this.netValue = checkSet(netValue);}
	public String getFundsSource() {return checkGet(fundsSource);}
	public void setFundsSource(String fundsSource) {this.fundsSource = checkSet(fundsSource);}
	public String getFundsSource1() {return checkGet(fundsSource1);}
	public void setFundsSource1(String fundsSource1) {this.fundsSource1 = checkSet(fundsSource1);}
	public String getOwnershipDate() {return checkGet(ownershipDate);}
	public void setOwnershipDate(String ownershipDate) {this.ownershipDate = checkSet(ownershipDate);}
	public String getOwnershipCause() {return checkGet(ownershipCause);}
	public void setOwnershipCause(String ownershipCause) {this.ownershipCause = checkSet(ownershipCause);}
	public String getNonProof() {return checkGet(nonProof);}
	public void setNonProof(String nonProof) {this.nonProof = checkSet(nonProof);}
	public String getOwnershipNote() {return checkGet(ownershipNote);}
	public void setOwnershipNote(String ownershipNote) {this.ownershipNote = checkSet(ownershipNote);}
	public String getBuildDate() {return checkGet(buildDate);}
	public void setBuildDate(String buildDate) {this.buildDate = checkSet(buildDate);}
	public String getFloor1() {return checkGet(floor1);}
	public void setFloor1(String floor1) {this.floor1 = checkSet(floor1);}
	public String getFloor2() {return checkGet(floor2);}
	public void setFloor2(String floor2) {this.floor2 = checkSet(floor2);}
	public String getStuff() {return checkGet(stuff);}
	public void setStuff(String stuff) {this.stuff = checkSet(stuff);}
	public String getBuyDate() {return checkGet(buyDate);}
	public void setBuyDate(String buyDate) {this.buyDate = checkSet(buyDate);}
	public String getSourceKind() {return checkGet(sourceKind);}
	public void setSourceKind(String sourceKind) {this.sourceKind = checkSet(sourceKind);}
	public String getSourceDate() {return checkGet(sourceDate);}
	public void setSourceDate(String sourceDate) {this.sourceDate = checkSet(sourceDate);}
	public String getSourceDoc() {return checkGet(sourceDoc);}
	public void setSourceDoc(String sourceDoc) {this.sourceDoc = checkSet(sourceDoc);}
	public String getManageOrg() {return checkGet(manageOrg);}
	public void setManageOrg(String manageOrg) {this.manageOrg = checkSet(manageOrg);}
	public String getOriginalBuildFeeCB() {return checkGet(originalBuildFeeCB);}
	public void setOriginalBuildFeeCB(String originalBuildFeeCB) {this.originalBuildFeeCB = checkSet(originalBuildFeeCB);}
	public String getOriginalDeprUnitCB() {return checkGet(originalDeprUnitCB);}
	public void setOriginalDeprUnitCB(String originalDeprUnitCB) {this.originalDeprUnitCB = checkSet(originalDeprUnitCB);}
	public String getOriginalDeprPark() {return checkGet(originalDeprPark);}
	public void setOriginalDeprPark(String originalDeprPark) {this.originalDeprPark = checkSet(originalDeprPark);}
	public String getOriginalDeprUnit() {return checkGet(originalDeprUnit);}
	public void setOriginalDeprUnit(String originalDeprUnit) {this.originalDeprUnit = checkSet(originalDeprUnit);}
	public String getOriginalDeprAccounts() {return checkGet(originalDeprAccounts);}
	public void setOriginalDeprAccounts(String originalDeprAccounts) {this.originalDeprAccounts = checkSet(originalDeprAccounts);}
	public String getOriginalScrapValue() {return checkGet(originalScrapValue);}
	public void setOriginalScrapValue(String originalScrapValue) {this.originalScrapValue = checkSet(originalScrapValue);}
	public String getOriginalDeprAmount() {return checkGet(originalDeprAmount);}
	public void setOriginalDeprAmount(String originalDeprAmount) {this.originalDeprAmount = checkSet(originalDeprAmount);}
	public String getOriginalaccumdepr1() { return checkGet(originalaccumdepr1); }
	public void setOriginalaccumdepr1(String originalaccumdepr1) { this.originalaccumdepr1 = checkSet(originalaccumdepr1); }
	public String getOriginalaccumdepr2() { return checkGet(originalaccumdepr2); }
	public void setOriginalaccumdepr2(String originalaccumdepr2) { this.originalaccumdepr2 = checkSet(originalaccumdepr2); }
	public String getOriginalAccumDepr() {return checkGet(originalAccumDepr);}
	public void setOriginalAccumDepr(String originalAccumDepr) {this.originalAccumDepr = checkSet(originalAccumDepr);}
	public String getOriginalApportionMonth() {return checkGet(originalApportionMonth);}
	public void setOriginalApportionMonth(String originalApportionMonth) {this.originalApportionMonth = checkSet(originalApportionMonth);}
	public String getOriginalMonthDepr() {return checkGet(originalMonthDepr);}
	public void setOriginalMonthDepr(String originalMonthDepr) {this.originalMonthDepr = checkSet(originalMonthDepr);}
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
	public String getNoDeprSet() {return checkGet(noDeprSet);}
	public void setNoDeprSet(String noDeprSet) {this.noDeprSet = checkSet(noDeprSet);}
	public String getAppraiseDate() {return checkGet(appraiseDate);}
	public void setAppraiseDate(String appraiseDate) {this.appraiseDate = checkSet(appraiseDate);}
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
	public String getUseLicense() {return checkGet(useLicense);}
	public void setUseLicense(String useLicense) {this.useLicense = checkSet(useLicense);}
	public String getEscrowOriValue() {return checkGet(escrowOriValue);}
	public void setEscrowOriValue(String escrowOriValue) {this.escrowOriValue = checkSet(escrowOriValue);}
	public String getEscrowOriAccumDepr() {return checkGet(escrowOriAccumDepr);}
	public void setEscrowOriAccumDepr(String escrowOriAccumDepr) {this.escrowOriAccumDepr = checkSet(escrowOriAccumDepr);}
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
	
	private String originalDeprMethod;
	public String getOriginalDeprMethod() {return checkGet(originalDeprMethod);}
	public void setOriginalDeprMethod(String originalDeprMethod) {this.originalDeprMethod = checkSet(originalDeprMethod);}	
	private String originalPlace1Name;
	public String getOriginalPlace1Name() {return checkGet(originalPlace1Name);}
	public void setOriginalPlace1Name(String originalPlace1Name) {this.originalPlace1Name = checkSet(originalPlace1Name);}

	private String originalDeprUnit1;
	private String deprUnit1;	
	public String getOriginalDeprUnit1() {return checkGet(originalDeprUnit1);}
	public void setOriginalDeprUnit1(String originalDeprUnit1) {this.originalDeprUnit1 = checkSet(originalDeprUnit1);}
	public String getDeprUnit1() {return checkGet(deprUnit1);}
	public void setDeprUnit1(String deprUnit1) {this.deprUnit1 = checkSet(deprUnit1);}
	
	private String originalApportionEndYM;
	public String getOriginalApportionEndYM() {return checkGet(originalApportionEndYM);}
	public void setOriginalApportionEndYM(String originalApportionEndYM) {this.originalApportionEndYM = checkSet(originalApportionEndYM);}	
	
	private String capital;
	private String licensePlate;
	private String purpose;
	public String getCapital() {return checkGet(capital);}
	public void setCapital(String capital) {this.capital = checkSet(capital);}
	public String getLicensePlate() {return checkGet(licensePlate);}
	public void setLicensePlate(String licensePlate) {this.licensePlate = checkSet(licensePlate);}
	public String getPurpose() {return checkGet(purpose);}
	public void setPurpose(String purpose) {this.purpose = checkSet(purpose);}	
	
	private String originalMonthDepr1;
	public String getOriginalMonthDepr1() {return checkGet(originalMonthDepr1);}
	public void setOriginalMonthDepr1(String originalMonthDepr1) {this.originalMonthDepr1 = checkSet(originalMonthDepr1);}
	private String monthDepr1;
	public String getMonthDepr1() {return checkGet(monthDepr1);}
	public void setMonthDepr1(String monthDepr1) {this.monthDepr1 = checkSet(monthDepr1);}	
	
	String proofYear;
	String proofDoc;
	String proofNo;
	String summonsDate;
	public String getProofDoc(){ return checkGet(proofDoc); }
	public void setProofDoc(String s){ proofDoc=checkSet(s); }
	public String getProofNo(){ return checkGet(proofNo); }
	public void setProofNo(String s){ proofNo=checkSet(s); }	
	public String getSummonsDate() {return checkGet(summonsDate);}
	public void setSummonsDate(String summonsDate) {this.summonsDate = checkSet(summonsDate);}	
	public String getProofYear() {return checkGet(proofYear);}
	public void setProofYear(String proofYear) {this.proofYear = checkSet(proofYear);}
	
	String originalLimitYear;
	public String getOriginalLimitYear() {return checkGet(originalLimitYear);}
	public void setOriginalLimitYear(String originalLimitYear) {this.originalLimitYear = checkSet(originalLimitYear);}	
	
	private String RTLimitYear;
	public String getRTLimitYear() { return checkGet(RTLimitYear); }
	public void setRTLimitYear(String rTLimitYear) { RTLimitYear = checkSet(rTLimitYear); }
	
	// 問題單2277 加上自行設定累計折舊
	private String individualSetDepr;
	private String verifyYM;
	public String getIndividualSetDepr() { return checkGet(individualSetDepr); }
	public void setIndividualSetDepr(String individualSetDepr) { this.individualSetDepr = checkSet(individualSetDepr); }
	public String getVerifyYM() { return checkGet(verifyYM); }
	public void setVerifyYM(String verifyYM) {this.verifyYM = checkSet(verifyYM); }
	
	//問題單2314 加上個別填寫折舊資料
	private String selfdepr;
	public String getSelfdepr() { return checkGet(selfdepr); }
	public void setSelfdepr(String selfdepr) { this.selfdepr = checkSet(selfdepr); }
}