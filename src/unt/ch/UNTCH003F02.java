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

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.ArrayList;

import util.Common;
import util.Database;
import util.Datetime;
import TDlib_Simple.tools.src.MathTools;

public class UNTCH003F02 extends UNTCH003Q{

	//====================================================================
	@Override
	protected util.SuperBean2[] _checkPropertyNoType(){
		
		util.SuperBean2[] sb = new util.SuperBean2[2];
		
		String checkStr = new unt.ch.UNTCH_Tools()._checkPropertyNoType(this.getPropertyNo());

		if(_table_LA.equals(checkStr)){				sb[0] = new unt.la.UNTLA019F();
		}else if(_table_BU.equals(checkStr)){		sb[0] = new unt.bu.UNTBU022F();
		}else if(_table_RF.equals(checkStr)){		sb[0] = new unt.rf.UNTRF015F();
		}else if(_table_MP.equals(checkStr)){		sb[0] = new unt.mp.UNTMP026F();
		}else if(_table_RT.equals(checkStr)){		sb[0] = new unt.rt.UNTRT008F(this.getClass().getSimpleName());
		}else if(_table_VP.equals(checkStr)){		sb[0] = new unt.vp.UNTVP006F();
		}
		
		return sb;
	}
	
	//====================================================================		
	//預設值	
	@Override
	protected void _setDefaultValue(){		
		String signNo = null;
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
			
			this.setOldArea(this.getOldLaArea());
			this.setOldHoldNume(this.getOldLaHoldNume());
			this.setOldHoldDeno(this.getOldLaHoldDeno());
			this.setOldHoldArea(this.getOldLaHoldArea());
			
			this.setNewArea(this.getNewLaArea());
			this.setNewHoldNume(this.getNewLaHoldNume());
			this.setNewHoldDeno(this.getNewLaHoldDeno());
			this.setNewHoldArea(this.getNewLaHoldArea());
			
			this.setAdjustArea(this.getAdjustLaArea());
			this.setAdjustHoldArea(this.getAdjustLaHoldArea());
			
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
			
			this.setOldArea(this.getOldBuArea());
			this.setOldHoldNume(this.getOldBuHoldNume());
			this.setOldHoldDeno(this.getOldBuHoldDeno());
			this.setOldHoldArea(this.getOldBuHoldArea());
			
			this.setNewArea(this.getNewBuArea());
			this.setNewHoldNume(this.getNewBuHoldNume());
			this.setNewHoldDeno(this.getNewBuHoldDeno());
			this.setNewHoldArea(this.getNewBuHoldArea());
			
			this.setAdjustArea(this.getAdjustBuArea());
			this.setAdjustHoldArea(this.getAdjustBuHoldArea());

			this.setAdjustCArea(this.getAdjustBuCArea());
			this.setAdjustSArea(this.getAdjustBuSArea());
			this.setOldCArea(this.getOldBuCArea());
			this.setOldSArea(this.getOldBuSArea());
			this.setNewCArea(this.getNewBuCArea());
			this.setNewSArea(this.getNewBuSArea());
			
		}
		
		if(this.getAdjustBookUnit().contains("-")){		this.setAdjustType("2");
		}else{											this.setAdjustType("1");				
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
		
		this.setSerialNoS(getSerialNo());
		this.setTaxCredit("N");
		this.setGrass("N");
		this.setVerify("N");
		this.setClosing("N");
		
		MathTools mt = new MathTools();
		String oldBookUnit = "0";
		if("".equals(Common.get(oldBookValue)) || "".equals(Common.get(oldHoldArea)) || "0".equals(Common.get(oldBookValue)) || "0".equals(Common.get(oldHoldArea))){
		}else{
			if(!(BigDecimal.ZERO.compareTo(new BigDecimal(oldHoldArea)) == 0)){
				oldBookUnit = mt._division_withBigDecimal(oldBookValue, oldHoldArea, 0);	
			}
		}
		this.setOldBookUnit(oldBookUnit);
	
		String oldNetUnit = "0";
		if("".equals(Common.get(oldNetValue)) || "".equals(Common.get(oldHoldArea)) || "0".equals(Common.get(oldBookValue)) || "0".equals(Common.get(oldHoldArea))){
		}else{
			if(!(BigDecimal.ZERO.compareTo(new BigDecimal(oldHoldArea)) == 0)){
				oldNetUnit = mt._division_withBigDecimal(oldNetValue, oldHoldArea, 0);
			}
		}
		this.setOldNetUnit(oldNetUnit);
		
		String newBookUnit = "0";
		if("".equals(Common.get(newBookValue)) || "".equals(Common.get(newHoldArea)) || "0".equals(Common.get(newBookValue)) || "0".equals(Common.get(newHoldArea))){
		}else{
			if(!(BigDecimal.ZERO.compareTo(new BigDecimal(newHoldArea)) == 0)){
				newBookUnit = mt._division_withBigDecimal(newBookValue, newHoldArea, 0);
			}
		}
		this.setNewBookUnit(newBookUnit);

		String newNetUnit = "0";
		if("".equals(Common.get(newNetValue)) || "".equals(Common.get(newHoldArea)) || "0".equals(Common.get(newNetValue)) || "0".equals(Common.get(newHoldArea))){
		}else{
			if(!(BigDecimal.ZERO.compareTo(new BigDecimal(newHoldArea)) == 0)){
				newNetUnit = mt._division_withBigDecimal(newNetValue, newHoldArea, 0);
			}
		}
		this.setNewNetUnit(newNetUnit);

		String adjustBookUnit = "0";
		if("".equals(Common.get(newBookValue)) || "".equals(Common.get(oldBookValue))){
		}else{
			adjustBookUnit = mt._subtraction_withBigDecimal(newBookValue, oldBookValue);
		}
		if(adjustBookUnit.contains("-")){	this.setAddBookUnit("0");
											this.setReduceBookUnit(adjustBookUnit);	
		}else{								this.setAddBookUnit(adjustBookUnit);	
											this.setReduceBookUnit("0");
		}

		String adjustNetUnit = "0";
		if("".equals(Common.get(newNetValue)) || "".equals(Common.get(oldNetValue))){
		}else{
			adjustNetUnit = mt._subtraction_withBigDecimal(newNetValue, oldNetValue);
		}
		if(adjustNetUnit.contains("-")){	this.setAddNetUnit("0");	
											this.setReduceNetUnit(adjustNetUnit);	
		}else{								this.setAddNetUnit(adjustNetUnit);	
											this.setReduceNetUnit("0");
		}
		
		this.setProofYear(ut._queryData("proofyear")._from("UNTLA_ADJUSTPROOF")._with(" and enterorg = '" + getEnterOrg() + "' and ownership = '" + getOwnership() + "' and differencekind = '" + getDifferenceKind() + "' and caseno = '" + getCaseNo() + "'")._toString());
		this.setProofDoc(ut._queryData("proofdoc")._from("UNTLA_ADJUSTPROOF")._with(" and enterorg = '" + getEnterOrg() + "' and ownership = '" + getOwnership() + "' and differencekind = '" + getDifferenceKind() + "' and caseno = '" + getCaseNo() + "'")._toString());
		this.setProofNo(ut._queryData("proofno")._from("UNTLA_ADJUSTPROOF")._with(" and enterorg = '" + getEnterOrg() + "' and ownership = '" + getOwnership() + "' and differencekind = '" + getDifferenceKind() + "' and caseno = '" + getCaseNo() + "'")._toString());
		this.setSummonsDate(ut._queryData("summonsdate")._from("UNTLA_ADJUSTPROOF")._with(" and enterorg = '" + getEnterOrg() + "' and ownership = '" + getOwnership() + "' and differencekind = '" + getDifferenceKind() + "' and caseno = '" + getCaseNo() + "'")._toString());

		if("".equals(Common.get(this.getCaseSerialNo()))){			
			DataStructor ds = new DataStructor();
			ds.enterOrg = this.getEnterOrg();
			ds.ownership = this.getOwnership();
			ds.caseNo = this.getCaseNo();
			ds.differenceKind = this.getDifferenceKind();
			
			String[] tables = new String[]{
					"UNTLA_ADJUSTDETAIL",
					"UNTBU_ADJUSTDETAIL",
					"UNTRF_ADJUSTDETAIL",
					"UNTMP_ADJUSTDETAIL",
					"UNTVP_ADJUSTPROOF",
					"UNTRT_ADJUSTPROOF"
					};
	
			this.setCaseSerialNo(ut._getNewCaseSerialNo(ds,tables));			
		}
	}
		
	//====================================================================
	@Override
	protected String _getMaxSerialNo(){
		String tableName = "";
		
		String checkStr = new unt.ch.UNTCH_Tools()._checkPropertyNoType(this.getPropertyNo());
		if(_table_RT.equals(checkStr)){				tableName = "UNTRT_AdjustProof";
		}else if(_table_VP.equals(checkStr)){		tableName = "UNTVP_AdjustProof";
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
			UNTCH_Tools ut = new UNTCH_Tools();
			ut._setParameter(obj,this);
			
			String checkStr = ut._checkPropertyNoType(this.getPropertyNo());
			if(_table_LA.equals(checkStr)){			
				if("".equals(checkGet(this.getSignNo()))){				
				}else{
					if (this.getSignNo().length() >= 1) {
						this.setLaSignNo1(this.getSignNo().substring(0,1) + "000000");
					}
					if (this.getSignNo().length() >= 3) {
						this.setLaSignNo2(this.getSignNo().substring(0,3) + "0000");
					}
					if (this.getSignNo().length() >= 7) {
						this.setLaSignNo3(this.getSignNo().substring(0,7));
					}
					if (this.getSignNo().length() >= 11) {
						this.setLaSignNo4(this.getSignNo().substring(7,11));
					}
					if (this.getSignNo().length() > 11) {
						this.setLaSignNo5(this.getSignNo().substring(11));
					}
				}
				
				this.setOldLaArea(this.oldArea);
				this.setOldLaHoldNume(this.oldHoldNume);
				this.setOldLaHoldDeno(this.oldHoldDeno);
				this.setOldLaHoldArea(this.oldHoldArea);
				this.setOldLaBookUnit(this.oldBookUnit);
				this.setOldLaBookValue(this.oldBookValue);
				
				this.setAdjustLaArea(this.adjustArea);
				this.setAdjustLaHoldArea(this.adjustHoldArea);
				
				this.setNewLaArea(this.newArea);
				this.setNewLaHoldNume(this.newHoldNume);
				this.setNewLaHoldDeno(this.newHoldDeno);
				this.setNewLaHoldArea(this.newHoldArea);
				this.setNewLaBookUnit(this.newBookUnit);
				this.setNewLaBookValue(this.newBookValue);
				
				
			}else if(_table_BU.equals(checkStr)){
				if("".equals(checkGet(this.getSignNo()))){
				}else{
					if (this.getSignNo().length() >= 1) {
						this.setBuSignNo1(this.getSignNo().substring(0,1) + "000000");
					}
					if (this.getSignNo().length() >= 3) {
						this.setBuSignNo2(this.getSignNo().substring(0,3) + "0000");
					}
					if (this.getSignNo().length() >= 7) {
						this.setBuSignNo3(this.getSignNo().substring(0,7));
					}
					if (this.getSignNo().length() >= 12) {
						this.setBuSignNo4(this.getSignNo().substring(7,12));
					}
					if (this.getSignNo().length() > 12) {
						this.setBuSignNo5(this.getSignNo().substring(12));
					}
				}
			
				this.setOldBuArea(this.oldArea);
				this.setOldBuCArea(this.oldCArea);
				this.setOldBuSArea(this.oldSArea);
				this.setOldBuHoldNume(this.oldHoldNume);
				this.setOldBuHoldDeno(this.oldHoldDeno);
				this.setOldBuHoldArea(this.oldHoldArea);
				this.setOldBuBookUnit(this.oldBookUnit);
				this.setOldBuBookValue(this.oldBookValue);
				
				this.setAdjustArea(this.adjustArea);
				this.setAdjustBuCArea(this.adjustCArea);
				this.setAdjustBuSArea(this.adjustSArea);
				this.setAdjustBuArea(this.adjustArea);
				this.setAdjustBuHoldArea(this.adjustHoldArea);
				
				this.setNewBuArea(this.newArea);
				this.setNewBuCArea(this.newCArea);
				this.setNewBuSArea(this.newSArea);
				this.setNewBuHoldNume(this.newHoldNume);
				this.setNewBuHoldDeno(this.newHoldDeno);
				this.setNewBuHoldArea(this.newHoldArea);
				this.setNewBuBookUnit(this.newBookUnit);
				this.setNewBuBookValue(this.newBookValue);
				
			}else if(_table_RF.equals(checkStr)){
				
			}else if(_table_MP.equals(checkStr)){
				
			}else if(_table_RT.equals(checkStr)){
				
			}else if(_table_VP.equals(checkStr)){
				
			}
			
			this.setMaterial(ut._getMaterial(this.getPropertyNo()));
			this.setPropertyUnit(ut._getPropertyUnit(this.getPropertyNo()));
			this.setLimitYear(ut._getLimitYear(this.getPropertyNo()));
			this.setPlace1Name(ut.getPlace1Name(this.enterOrg, this.place1));
			if("".equals(this.oldAccumDepr) || this.oldAccumDepr == null ){ 
				this.oldAccumDepr = "0";
			}
			
			if("".equals(this.reduceAccumDepr) || this.reduceAccumDepr == null ){ 
				this.reduceAccumDepr = "0";
			}
			
			if("".equals(this.newAccumDepr) || this.newAccumDepr == null ){ 
				this.newAccumDepr = "0";
			}
			
			setStateQueryOneSuccess();
			
			queryProofNo();
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			sb = null;
		}	
	}
	
		private void queryProofNo(){
			String sql = "select proofno from UNTLA_ADJUSTPROOF where 1=1" +
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
								" a.differenceKind, ").append(
								" a.caseSerialNo, ").append(
								" a.propertyNo, ").append(
								" a.serialNo, ").append(
								" null as lotNo, ").append(
								" (select codename from SYSCA_CODE z where z.codekindid = 'AJC' and z.codeid = a.cause) as cause, ").append(
								" a.signNo as laSignNo, ").append(
								" null as buSignNo, ").append(
								" a.addbookvalue,").append(
								" a.reducebookvalue").append(
						" from UNTLA_ADJUSTDETAIL a").append(
						" left join UNTLA_ADJUSTPROOF b on a.enterorg=b.enterorg and a.ownership=b.ownership and a.caseno=b.caseno ").append(
						" where 1=1 ").append(		
							getQueryCondition("LA")).append(
						" union ").append(								
						" select").append(
								" a.enterorg, ").append(
								" a.ownership, ").append(
								" a.caseno, ").append(
								" a.differenceKind, ").append(
								" a.caseSerialNo, ").append(
								" a.propertyNo, ").append(
								" a.serialNo, ").append(
								" null as lotNo, ").append(
								" (select codename from SYSCA_CODE z where z.codekindid = 'AJC' and z.codeid = a.cause) as cause, ").append(
								" null as laSignNo, ").append(
								" a.signNo as buSignNo, ").append(
								" a.addbookvalue,").append(
								" a.reducebookvalue").append(
							" from UNTBU_ADJUSTDETAIL a").append(
							" left join UNTBU_ADJUSTPROOF b on a.enterorg=b.enterorg and a.ownership=b.ownership and a.caseno=b.caseno ").append(
							" where 1=1 ").append(	
								getQueryCondition("BU")).append(
						" union ").append(
						" select").append(
								" a.enterorg, ").append(
								" a.ownership, ").append(
								" a.caseno, ").append(
								" a.differenceKind, ").append(
								" a.caseSerialNo, ").append(
								" a.propertyNo, ").append(
								" a.serialNo, ").append(
								" null as lotNo, ").append(
								" (select codename from SYSCA_CODE z where z.codekindid = 'AJC' and z.codeid = a.cause) as cause, ").append(
								" null as laSignNo, ").append(
								" null as buSignNo, ").append(
								" a.addbookvalue,").append(
								" a.reducebookvalue").append(
							" from UNTRF_ADJUSTDETAIL a").append(
							" left join UNTRF_ADJUSTPROOF b on a.enterorg=b.enterorg and a.ownership=b.ownership and a.caseno=b.caseno ").append(
							" where 1=1 ").append(		
								getQueryCondition("RF")).append(
						" union ").append(
						" select").append(
								" a.enterorg, ").append(
								" a.ownership, ").append(
								" a.caseno, ").append(
								" a.differenceKind, ").append(
								" a.caseSerialNo, ").append(
								" a.propertyNo, ").append(
								" a.serialNo, ").append(
								" a.lotNo, ").append(
								" (select codename from SYSCA_CODE z where z.codekindid = 'AJC' and z.codeid = a.cause) as cause, ").append(
								" null as laSignNo, ").append(
								" null as buSignNo, ").append(
								" a.addbookvalue,").append(
								" a.reducebookvalue").append(
							" from UNTMP_ADJUSTDETAIL a").append(
							" left join UNTMP_ADJUSTPROOF b on a.enterorg=b.enterorg and a.ownership=b.ownership and a.caseno=b.caseno ").append(
							" where 1=1 ").append(		
								getQueryCondition("MP")).append(
						" union ").append(
						" select").append(
								" a.enterorg, ").append(
								" a.ownership, ").append(
								" a.caseno, ").append(
								" a.differenceKind, ").append(
								" a.caseSerialNo, ").append(
								" a.propertyNo, ").append(
								" a.serialNo, ").append(
								" null as lotNo, ").append(
								" (select codename from SYSCA_CODE z where z.codekindid = 'AJC' and z.codeid = a.cause) as cause, ").append(
								" null as laSignNo, ").append(
								" null as buSignNo, ").append(
								" a.addbookvalue,").append(
								" a.reducebookvalue").append(
							" from UNTVP_ADJUSTPROOF a where 1=1").append(
								getQueryCondition("VP")).append(
						" union ").append(
						" select").append(
								" a.enterorg, ").append(
								" a.ownership, ").append(
								" a.caseno, ").append(
								" a.differenceKind, ").append(
								" a.caseSerialNo, ").append(
								" a.propertyNo, ").append(
								" a.serialNo, ").append(
								" null as lotNo, ").append(
								" (select codename from SYSCA_CODE z where z.codekindid = 'AJC' and z.codeid = a.cause) as cause, ").append(
								" null as laSignNo, ").append(
								" null as buSignNo, ").append(
								" a.addbookvalue,").append(
								" a.reducebookvalue").append(
							" from UNTRT_ADJUSTPROOF a where 1=1").append(
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
				String rowArray[]=new String[13];
				
				rowArray[0] = checkGet(rs.getString("enterorg"));
				rowArray[1] = checkGet(rs.getString("ownership"));
				rowArray[2] = checkGet(rs.getString("caseno"));
				rowArray[3] = checkGet(rs.getString("differenceKind"));
				rowArray[4] = checkGet(rs.getString("caseSerialNo"));				
				rowArray[5] = checkGet(rs.getString("propertyNo")); 		
				rowArray[6] = checkGet(rs.getString("serialNo"));
				rowArray[7] = checkGet(rs.getString("lotNo"));
				
				rowArray[8] = uctls._getPropertyNoName(checkGet(rs.getString("propertyNo")));
				
				String signNoLa = rs.getString("laSignNo");
				String signNoBu = rs.getString("buSignNo");
				
				rowArray[9] = "";
				if("".equals(checkGet(signNoLa)) || signNoLa.length() < 15){				
				}else{
					rowArray[9] = uctls._getSignNoName(signNoLa.substring(0,7)) + signNoLa.substring(7,11) + "-" + signNoLa.substring(11) + "地號";
				}
				if("".equals(checkGet(signNoBu)) || signNoBu.length() < 15){				
				}else{
					rowArray[9] += uctls._getSignNoName(signNoBu.substring(0,7)) + signNoBu.substring(7,12) + "-" + signNoBu.substring(12) + "建號";
				}
				
				rowArray[10] = checkGet(rs.getString("cause"));
				rowArray[11] = checkGet(rs.getString("addbookvalue"));
				rowArray[12] = checkGet(rs.getString("reducebookvalue"));
				
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
		
//		if (!"".equals(getQ_approveOrg())){
//			result.append(" and approveOrg = ").append(Common.sqlChar(getQ_approveOrg()));
//		}
		
		
		
		if (!"".equals(getQ_adjustDateS())){
			result.append(" and a.adjustDate >= ").append(Common.sqlChar(Datetime.changeTaiwanYYMMDD(getQ_adjustDateS(), "2")));
		}
		if (!"".equals(getQ_adjustDateE())){
			result.append(" and a.adjustDate <= ").append(Common.sqlChar(Datetime.changeTaiwanYYMMDD(getQ_adjustDateE(), "2")));
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
		if (!"".equals(getQ_fundType())){
			result.append(" and a.fundType = ").append(Common.sqlChar(getQ_fundType()));
		}
		
		if (!"".equals(getQ_valuable())){
			if(!"VP".equals(table) && !"RT".equals(table)){
				result.append(" and a.valuable = ").append(Common.sqlChar(getQ_valuable()));
			}
		}
		if (!"".equals(getQ_taxCredit())){
			if(!"MP".equals(table) && !"VP".equals(table) && !"RT".equals(table)){
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
			result.append(" and (a.keeper = ").append(Common.sqlChar(this.getKeeperno()));
			if("LA".equals(table) || "BU".equals(table) || "RF".equals(table) || "MP".equals(table) ){
				result.append(" or b.editid = ").append(Common.sqlChar(this.getUserID()));
			}else{
				result.append(" or a.editid = ").append(Common.sqlChar(this.getUserID()));
			}
			result.append(" )");			
			
		}else if ("2".equals(this.getRoleid())){
//			result.append(" and (a.keepunit = ").append(Common.sqlChar(this.getUnitID()));
//			result.append(" and ( a.keeper = ").append(Common.sqlChar(this.getKeeperno()));
//			if("LA".equals(table) || "BU".equals(table) || "RF".equals(table) || "MP".equals(table) ){
//				result.append(" or b.editid = ").append(Common.sqlChar(this.getUserID()));
//			}else{
//				result.append(" or a.editid = ").append(Common.sqlChar(this.getUserID()));
//			}
//			result.append(" )");
			
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
	
	String grass;
	public String getGrass(){ return checkGet(grass); }
	public void setGrass(String s){ grass=checkSet(s); }
	
	String oldCArea;
	String oldSArea;
	String newCArea;
	String newSArea;
	String adjustCArea;
	String adjustSArea;
	String CArea;
	String SArea;
	String area;
	String holdNume;
	String holdDeno;
	String holdArea;
	
	String oldLaArea;
	String oldLaHoldNume;
	String oldLaHoldDeno;
	String oldLaHoldArea;
	String oldBuCArea;
	String oldBuSArea;
	String oldBuArea;
	String oldBuHoldNume;
	String oldBuHoldDeno;
	String oldBuHoldArea;
	
	String newLaArea;
	String newLaHoldNume;
	String newLaHoldDeno;
	String newLaHoldArea;
	String newBuCArea;
	String newBuSArea;
	String newBuArea;
	String newBuHoldNume;
	String newBuHoldDeno;
	String newBuHoldArea;
	
	String adjustLaArea;
	String adjustLaHoldArea;
	String adjustBuCArea;
	String adjustBuSArea;
	String adjustBuArea;
	String adjustBuHoldArea;
	
	String laArea;
	String laHoldNume;
	String laHoldDeno;
	String laHoldArea;
	
	String buCArea;
	String buSArea;
	String buArea;
	String buHoldNume;
	String buHoldDeno;
	String buHoldArea;
	
	public String getCArea(){ return checkGet(CArea); }
	public void setCArea(String s){ CArea=checkSet(s); }
	public String getSArea(){ return checkGet(SArea); }
	public void setSArea(String s){ SArea=checkSet(s); }
	public String getArea(){ return checkGet(area); }
	public void setArea(String s){ area=checkSet(s); }
	public String getHoldNume(){ return checkGet(holdNume); }
	public void setHoldNume(String s){ holdNume=checkSet(s); }
	public String getHoldDeno(){ return checkGet(holdDeno); }
	public void setHoldDeno(String s){ holdDeno=checkSet(s); }
	public String getHoldArea(){ return checkGet(holdArea); }
	public void setHoldArea(String s){ holdArea=checkSet(s); }
	
	public String getOldCArea(){ return checkGet(oldCArea); }
	public void setOldCArea(String s){ oldCArea=checkSet(s); }
	public String getOldSArea(){ return checkGet(oldSArea); }
	public void setOldSArea(String s){ oldSArea=checkSet(s); }
	
	public String getNewCArea(){ return checkGet(newCArea); }
	public void setNewCArea(String s){ newCArea=checkSet(s); }
	public String getNewSArea(){ return checkGet(newSArea); }
	public void setNewSArea(String s){ newSArea=checkSet(s); }
	
	public String getAdjustCArea(){ return checkGet(adjustCArea); }
	public void setAdjustCArea(String s){ adjustCArea=checkSet(s); }
	public String getAdjustSArea(){ return checkGet(adjustSArea); }
	public void setAdjustSArea(String s){ adjustSArea=checkSet(s); }
	
	String cause2;
	String oldMeasure;
	String newMeasure;
	String adjustMeasure;
	String oldcause;
	String measure;
	public String getMeasure(){ return checkGet(measure); }
	public void setMeasure(String s){ measure=checkSet(s); }
	public String getCause2(){ return checkGet(cause2); }
	public void setCause2(String s){ cause2=checkSet(s); }
	public String getOldMeasure(){ return checkGet(oldMeasure); }
	public void setOldMeasure(String s){ oldMeasure=checkSet(s); }
	public String getNewMeasure(){ return checkGet(newMeasure); }
	public void setNewMeasure(String s){ newMeasure=checkSet(s); }
	public String getAdjustMeasure(){ return checkGet(adjustMeasure); }
	public void setAdjustMeasure(String s){ adjustMeasure=checkSet(s); }
	public String getOldcause(){ return checkGet(oldcause); }
	public void setOldcause(String s){ oldcause=checkSet(s); }
	
	String tOriginalUnit;
	String propertyName;
	String oldLaBookUnit;
	String oldLaBookValue;
	String newLaBookUnit;
	String newLaBookValue;
	String adjustLaBookUnit;
	String adjustLaBookValue;
	
	String oldBuBookUnit;
	String oldBuBookValue;
	String newBuBookUnit;
	String newBuBookValue;
	String adjustBuBookUnit;
	String adjustBuBookValue;
	
	public String getMaterial(){ return checkGet(material); }
	public void setMaterial(String s){ material=checkSet(s); }
	public String getOtherMaterial(){ return checkGet(otherMaterial); }
	public void setOtherMaterial(String s){ otherMaterial=checkSet(s); }
	public String getPropertyUnit(){ return checkGet(propertyUnit); }
	public void setPropertyUnit(String s){ propertyUnit=checkSet(s); }
	public String getOtherPropertyUnit(){ return checkGet(otherPropertyUnit); }
	public void setOtherPropertyUnit(String s){ otherPropertyUnit=checkSet(s); }
	public String getLimitYear(){ return checkGet(limitYear); }
	public void setLimitYear(String s){ limitYear=checkSet(s); }
	public String getOtherLimitYear(){ return checkGet(otherLimitYear); }
	public void setOtherLimitYear(String s){ otherLimitYear=checkSet(s); }
	
	public String getTOriginalUnit(){ return checkGet(tOriginalUnit); }
	public void setTOriginalUnit(String s){ tOriginalUnit=checkSet(s); }
	public String getBookValue(){ return checkGet(bookValue); }
	public void setBookValue(String s){ bookValue=checkSet(s); }
	public String getPropertyName(){ return checkGet(propertyName); }
	public void setPropertyName(String s){ propertyName=checkSet(s); }
	public String getLotNo(){ return checkGet(lotNo); }
	public void setLotNo(String s){ lotNo=checkSet(s); }
	public String getBookAmount(){ return checkGet(bookAmount); }
	public void setBookAmount(String s){ bookAmount=checkSet(s); }
	public String getAdjustType(){ return checkGet(adjustType); }
	public void setAdjustType(String s){ adjustType=checkSet(s); }
	public String getKeepUnitName(){ return checkGet(keepUnitName); }
	public void setKeepUnitName(String s){ keepUnitName=checkSet(s); }
	public String getKeeperName(){ return checkGet(keeperName); }
	public void setKeeperName(String s){ keeperName=checkSet(s); }
	public String getUseUnitName(){ return checkGet(useUnitName); }
	public void setUseUnitName(String s){ useUnitName=checkSet(s); }
	public String getUserName(){ return checkGet(userName); }
	public void setUserName(String s){ userName=checkSet(s); }
	public String getOldLaBookUnit() {return checkGet(oldLaBookUnit);}
	public void setOldLaBookUnit(String oldLaBookUnit) {this.oldLaBookUnit = checkSet(oldLaBookUnit);}
	public String getOldLaBookValue() {return checkGet(oldLaBookValue);}
	public void setOldLaBookValue(String oldLaBookValue) {this.oldLaBookValue = checkSet(oldLaBookValue);}
	public String getNewLaBookUnit() {return checkGet(newLaBookUnit);}
	public void setNewLaBookUnit(String newLaBookUnit) {this.newLaBookUnit = checkSet(newLaBookUnit);}
	public String getNewLaBookValue() {return checkGet(newLaBookValue);}
	public void setNewLaBookValue(String newLaBookValue) {this.newLaBookValue = checkSet(newLaBookValue);}
	public String getAdjustLaBookUnit() {return checkGet(adjustLaBookUnit);}
	public void setAdjustLaBookUnit(String adjustLaBookUnit) {this.adjustLaBookUnit = checkSet(adjustLaBookUnit);}
	public String getAdjustLaBookValue() {return checkGet(adjustLaBookValue);}
	public void setAdjustLaBookValue(String adjustLaBookValue) {this.adjustLaBookValue = checkSet(adjustLaBookValue);}
	public String getOldBuBookUnit() {return checkGet(oldBuBookUnit);}
	public void setOldBuBookUnit(String oldBuBookUnit) {this.oldBuBookUnit = checkSet(oldBuBookUnit);}
	public String getOldBuBookValue() {return checkGet(oldBuBookValue);}
	public void setOldBuBookValue(String oldBuBookValue) {this.oldBuBookValue = checkSet(oldBuBookValue);}
	public String getNewBuBookUnit() {return checkGet(newBuBookUnit);}
	public void setNewBuBookUnit(String newBuBookUnit) {this.newBuBookUnit = checkSet(newBuBookUnit);}
	public String getNewBuBookValue() {return checkGet(newBuBookValue);}
	public void setNewBuBookValue(String newBuBookValue) {this.newBuBookValue = checkSet(newBuBookValue);}
	public String getAdjustBuBookUnit() {return checkGet(adjustBuBookUnit);}
	public void setAdjustBuBookUnit(String adjustBuBookUnit) {this.adjustBuBookUnit = checkSet(adjustBuBookUnit);}
	public String getAdjustBuBookValue() {return checkGet(adjustBuBookValue);}
	public void setAdjustBuBookValue(String adjustBuBookValue) {this.adjustBuBookValue = checkSet(adjustBuBookValue);}
	
	String ownershipQuery;
	
	public String getOwnershipQuery(){ return checkGet(ownershipQuery); }
	public void setOwnershipQuery(String s){ ownershipQuery=checkSet(s); }
	
	String closing;
	public String getClosing(){ return checkGet(closing); }
	public void setClosing(String s){ closing=checkSet(s); }
	
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
	
	
	public String getOldLaArea() {return checkGet(oldLaArea);}
	public void setOldLaArea(String oldLaArea) {this.oldLaArea = checkSet(oldLaArea);}
	public String getOldLaHoldNume() {return checkGet(oldLaHoldNume);}
	public void setOldLaHoldNume(String oldLaHoldNume) {this.oldLaHoldNume = checkSet(oldLaHoldNume);}
	public String getOldLaHoldDeno() {return checkGet(oldLaHoldDeno);}
	public void setOldLaHoldDeno(String oldLaHoldDeno) {this.oldLaHoldDeno = checkSet(oldLaHoldDeno);}
	public String getOldLaHoldArea() {return checkGet(oldLaHoldArea);}
	public void setOldLaHoldArea(String oldLaHoldArea) {this.oldLaHoldArea = checkSet(oldLaHoldArea);}
	public String getOldBuCArea() {return checkGet(oldBuCArea);}
	public void setOldBuCArea(String oldBuCArea) {this.oldBuCArea = checkSet(oldBuCArea);}
	public String getOldBuSArea() {return checkGet(oldBuSArea);}
	public void setOldBuSArea(String oldBuSArea) {this.oldBuSArea = checkSet(oldBuSArea);}
	public String getOldBuArea() {return checkGet(oldBuArea);}
	public void setOldBuArea(String oldBuArea) {this.oldBuArea = checkSet(oldBuArea);}
	public String getOldBuHoldNume() {return checkGet(oldBuHoldNume);}
	public void setOldBuHoldNume(String oldBuHoldNume) {this.oldBuHoldNume = checkSet(oldBuHoldNume);}
	public String getOldBuHoldDeno() {return checkGet(oldBuHoldDeno);}
	public void setOldBuHoldDeno(String oldBuHoldDeno) {this.oldBuHoldDeno = checkSet(oldBuHoldDeno);}
	public String getOldBuHoldArea() {return checkGet(oldBuHoldArea);}
	public void setOldBuHoldArea(String oldBuHoldArea) {this.oldBuHoldArea = checkSet(oldBuHoldArea);}
	public String getNewLaArea() {return checkGet(newLaArea);}
	public void setNewLaArea(String newLaArea) {this.newLaArea = checkSet(newLaArea);}
	public String getNewLaHoldNume() {return checkGet(newLaHoldNume);}
	public void setNewLaHoldNume(String newLaHoldNume) {this.newLaHoldNume = checkSet(newLaHoldNume);}
	public String getNewLaHoldDeno() {return checkGet(newLaHoldDeno);}
	public void setNewLaHoldDeno(String newLaHoldDeno) {this.newLaHoldDeno = checkSet(newLaHoldDeno);}
	public String getNewLaHoldArea() {return checkGet(newLaHoldArea);}
	public void setNewLaHoldArea(String newLaHoldArea) {this.newLaHoldArea = checkSet(newLaHoldArea);}
	public String getNewBuCArea() {return checkGet(newBuCArea);}
	public void setNewBuCArea(String newBuCArea) {this.newBuCArea = checkSet(newBuCArea);}
	public String getNewBuSArea() {return checkGet(newBuSArea);}
	public void setNewBuSArea(String newBuSArea) {this.newBuSArea = checkSet(newBuSArea);}
	public String getNewBuArea() {return checkGet(newBuArea);}
	public void setNewBuArea(String newBuArea) {this.newBuArea = checkSet(newBuArea);}
	public String getNewBuHoldNume() {return checkGet(newBuHoldNume);}
	public void setNewBuHoldNume(String newBuHoldNume) {this.newBuHoldNume = checkSet(newBuHoldNume);}
	public String getNewBuHoldDeno() {return checkGet(newBuHoldDeno);}
	public void setNewBuHoldDeno(String newBuHoldDeno) {this.newBuHoldDeno = checkSet(newBuHoldDeno);}
	public String getNewBuHoldArea() {return checkGet(newBuHoldArea);}
	public void setNewBuHoldArea(String newBuHoldArea) {this.newBuHoldArea = checkSet(newBuHoldArea);}
	public String getAdjustLaArea() {return checkGet(adjustLaArea);}
	public void setAdjustLaArea(String adjustLaArea) {this.adjustLaArea = checkSet(adjustLaArea);}
	public String getAdjustLaHoldArea() {return checkGet(adjustLaHoldArea);}
	public void setAdjustLaHoldArea(String adjustLaHoldArea) {this.adjustLaHoldArea = checkSet(adjustLaHoldArea);}
	public String getAdjustBuCArea() {return checkGet(adjustBuCArea);}
	public void setAdjustBuCArea(String adjustBuCArea) {this.adjustBuCArea = checkSet(adjustBuCArea);}
	public String getAdjustBuSArea() {return checkGet(adjustBuSArea);}
	public void setAdjustBuSArea(String adjustBuSArea) {this.adjustBuSArea = checkSet(adjustBuSArea);}
	public String getAdjustBuArea() {return checkGet(adjustBuArea);}
	public void setAdjustBuArea(String adjustBuArea) {this.adjustBuArea = checkSet(adjustBuArea);}
	public String getAdjustBuHoldArea() {return checkGet(adjustBuHoldArea);}
	public void setAdjustBuHoldArea(String adjustBuHoldArea) {this.adjustBuHoldArea = checkSet(adjustBuHoldArea);}
	public String getLaArea() {return checkGet(laArea);}
	public void setLaArea(String laArea) {this.laArea = checkSet(laArea);}
	public String getLaHoldNume() {return checkGet(laHoldNume);}
	public void setLaHoldNume(String laHoldNume) {this.laHoldNume = checkSet(laHoldNume);}
	public String getLaHoldDeno() {return checkGet(laHoldDeno);}
	public void setLaHoldDeno(String laHoldDeno) {this.laHoldDeno = checkSet(laHoldDeno);}
	public String getLaHoldArea() {return checkGet(laHoldArea);}
	public void setLaHoldArea(String laHoldArea) {this.laHoldArea = checkSet(laHoldArea);}
	public String getBuCArea() {return checkGet(buCArea);}
	public void setBuCArea(String buCArea) {this.buCArea = checkSet(buCArea);}
	public String getBuSArea() {return checkGet(buSArea);}
	public void setBuSArea(String buSArea) {this.buSArea = checkSet(buSArea);}
	public String getBuArea() {return checkGet(buArea);}
	public void setBuArea(String buArea) {this.buArea = checkSet(buArea);}
	public String getBuHoldNume() {return checkGet(buHoldNume);}
	public void setBuHoldNume(String buHoldNume) {this.buHoldNume = checkSet(buHoldNume);}
	public String getBuHoldDeno() {return checkGet(buHoldDeno);}
	public void setBuHoldDeno(String buHoldDeno) {this.buHoldDeno = checkSet(buHoldDeno);}
	public String getBuHoldArea() {return checkGet(buHoldArea);}
	public void setBuHoldArea(String buHoldArea) {this.buHoldArea = checkSet(buHoldArea);}
	
	private String originalKeepUnit;
	private String originalKeeper;
	private String originalUseUnit;
	private String originalUser;
	private String originalMoveDate;
	private String placeName;
	private String placeNote;
	
	public String getOriginalKeepUnit() {return checkGet(originalKeepUnit);}
	public void setOriginalKeepUnit(String originalKeepUnit) {this.originalKeepUnit = checkSet(originalKeepUnit);}
	public String getOriginalKeeper() {return checkGet(originalKeeper);}
	public void setOriginalKeeper(String originalKeeper) {this.originalKeeper = checkSet(originalKeeper);}
	public String getOriginalUseUnit() {return checkGet(originalUseUnit);}
	public void setOriginalUseUnit(String originalUseUnit) {this.originalUseUnit = checkSet(originalUseUnit);}
	public String getOriginalUser() {return checkGet(originalUser);}
	public void setOriginalUser(String originalUser) {this.originalUser = checkSet(originalUser);}
	public String getOriginalMoveDate() {return checkGet(originalMoveDate);}
	public void setOriginalMoveDate(String originalMoveDate) {this.originalMoveDate = checkSet(originalMoveDate);}
	public String getPlaceName() {return checkGet(placeName);}
	public void setPlaceName(String placeName) {this.placeName = checkSet(placeName);}
	public String getPlaceNote() {return checkGet(placeNote);}
	public void setPlaceNote(String placeNote) {this.placeNote = checkSet(placeNote);}
	
	private String signNo;
	private String bulletinDate;
	private String taxCredit;
	private String originalBV;
	private String oldArea;
	private String oldHoldNume;
	private String oldHoldDeno;
	private String oldHoldArea;
	private String newArea;
	private String newHoldNume;
	private String newHoldDeno;
	private String newHoldArea;
	private String adjustArea;
	private String adjustHoldArea;
	private String changeItem;
	private String notes1;
	private String useSeparate;
	private String useKind;
	private String signNo1;
	private String signNo2;
	private String signNo3;
	private String signNo4;
	private String signNo5;
	private String oldPropertyName;
	
	public String getCaseSerialNo() {return checkGet(caseSerialNo);}
	public void setCaseSerialNo(String caseSerialNo) {this.caseSerialNo = checkSet(caseSerialNo);}
	public String getPropertyNo() {return checkGet(propertyNo);}
	public void setPropertyNo(String propertyNo) {this.propertyNo = checkSet(propertyNo);}
	public String getSerialNo() {return checkGet(serialNo);}
	public void setSerialNo(String serialNo) {this.serialNo = checkSet(serialNo);}
	public String getPropertyName1() {return checkGet(propertyName1);}
	public void setPropertyName1(String propertyName1) {this.propertyName1 = checkSet(propertyName1);}
	public String getSignNo() {return checkGet(signNo);}
	public void setSignNo(String signNo) {this.signNo = checkSet(signNo);}
	public String getCause() {return checkGet(cause);}
	public void setCause(String cause) {this.cause = checkSet(cause);}
	public String getCause1() {return checkGet(cause1);}
	public void setCause1(String cause1) {this.cause1 = checkSet(cause1);}
	public String getBulletinDate() {return checkGet(bulletinDate);}
	public void setBulletinDate(String bulletinDate) {this.bulletinDate = checkSet(bulletinDate);}
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
	public String getOldArea() {return checkGet(oldArea);}
	public void setOldArea(String oldArea) {this.oldArea = checkSet(oldArea);}
	public String getOldHoldNume() {return checkGet(oldHoldNume);}
	public void setOldHoldNume(String oldHoldNume) {this.oldHoldNume = checkSet(oldHoldNume);}
	public String getOldHoldDeno() {return checkGet(oldHoldDeno);}
	public void setOldHoldDeno(String oldHoldDeno) {this.oldHoldDeno = checkSet(oldHoldDeno);}
	public String getOldHoldArea() {return checkGet(oldHoldArea);}
	public void setOldHoldArea(String oldHoldArea) {this.oldHoldArea = checkSet(oldHoldArea);}
	public String getOldBookUnit() {return checkGet(oldBookUnit);}
	public void setOldBookUnit(String oldBookUnit) {this.oldBookUnit = checkSet(oldBookUnit);}
	public String getOldBookValue() {return checkGet(oldBookValue);}
	public void setOldBookValue(String oldBookValue) {this.oldBookValue = checkSet(oldBookValue);}
	public String getOldNetUnit() {return checkGet(oldNetUnit);}
	public void setOldNetUnit(String oldNetUnit) {this.oldNetUnit = checkSet(oldNetUnit);}
	public String getOldNetValue() {return checkGet(oldNetValue);}
	public void setOldNetValue(String oldNetValue) {this.oldNetValue = checkSet(oldNetValue);}
	public String getNewArea() {return checkGet(newArea);}
	public void setNewArea(String newArea) {this.newArea = checkSet(newArea);}
	public String getNewHoldNume() {return checkGet(newHoldNume);}
	public void setNewHoldNume(String newHoldNume) {this.newHoldNume = checkSet(newHoldNume);}
	public String getNewHoldDeno() {return checkGet(newHoldDeno);}
	public void setNewHoldDeno(String newHoldDeno) {this.newHoldDeno = checkSet(newHoldDeno);}
	public String getNewHoldArea() {return checkGet(newHoldArea);}
	public void setNewHoldArea(String newHoldArea) {this.newHoldArea = checkSet(newHoldArea);}
	public String getNewBookUnit() {return checkGet(newBookUnit);}
	public void setNewBookUnit(String newBookUnit) {this.newBookUnit = checkSet(newBookUnit);}
	public String getNewBookValue() {return checkGet(newBookValue);}
	public void setNewBookValue(String newBookValue) {this.newBookValue = checkSet(newBookValue);}
	public String getNewNetUnit() {return checkGet(newNetUnit);}
	public void setNewNetUnit(String newNetUnit) {this.newNetUnit = checkSet(newNetUnit);}
	public String getNewNetValue() {return checkGet(newNetValue);}
	public void setNewNetValue(String newNetValue) {this.newNetValue = checkSet(newNetValue);}
	public String getAdjustArea() {return checkGet(adjustArea);}
	public void setAdjustArea(String adjustArea) {this.adjustArea = checkSet(adjustArea);}
	public String getAdjustHoldArea() {return checkGet(adjustHoldArea);}
	public void setAdjustHoldArea(String adjustHoldArea) {this.adjustHoldArea = checkSet(adjustHoldArea);}
	public String getAddBookUnit() {return checkGet(addBookUnit);}
	public void setAddBookUnit(String addBookUnit) {this.addBookUnit = checkSet(addBookUnit);}
	public String getAddBookValue() {return checkGet(addBookValue);}
	public void setAddBookValue(String addBookValue) {this.addBookValue = checkSet(addBookValue);}
	public String getAddNetUnit() {return checkGet(addNetUnit);}
	public void setAddNetUnit(String addNetUnit) {this.addNetUnit = checkSet(addNetUnit);}
	public String getAddNetValue() {return checkGet(addNetValue);}
	public void setAddNetValue(String addNetValue) {this.addNetValue = checkSet(addNetValue);}
	public String getAddLimitYear() {return checkGet(addLimitYear);}
	public void setAddLimitYear(String addLimitYear) {this.addLimitYear = checkSet(addLimitYear);}
	public String getOverLimitYear() { return checkGet(overLimitYear);}
	public void setOverLimitYear(String overLimitYear) {this.overLimitYear = checkGet(overLimitYear);}
	public String getOverLimitYear2() { return checkGet(overLimitYear2);}
	public void setOverLimitYear2(String overLimitYear2) {this.overLimitYear2 = checkGet(overLimitYear2);}
	public String getReduceBookUnit() {return checkGet(reduceBookUnit);}
	public void setReduceBookUnit(String reduceBookUnit) {this.reduceBookUnit = checkSet(reduceBookUnit);}
	public String getReduceBookValue() {return checkGet(reduceBookValue);}
	public void setReduceBookValue(String reduceBookValue) {this.reduceBookValue = checkSet(reduceBookValue);}
	public String getReduceNetUnit() {return checkGet(reduceNetUnit);}
	public void setReduceNetUnit(String reduceNetUnit) {this.reduceNetUnit = checkSet(reduceNetUnit);}
	public String getReduceNetValue() {return checkGet(reduceNetValue);}
	public void setReduceNetValue(String reduceNetValue) {this.reduceNetValue = checkSet(reduceNetValue);}
	public String getReduceLimitYear() {return checkGet(reduceLimitYear);}
	public void setReduceLimitYear(String reduceLimitYear) {this.reduceLimitYear = checkSet(reduceLimitYear);}
	public String getBookNotes() {return checkGet(bookNotes);}
	public void setBookNotes(String bookNotes) {this.bookNotes = checkSet(bookNotes);}
	public String getChangeItem() {return checkGet(changeItem);}
	public void setChangeItem(String changeItem) {this.changeItem = checkSet(changeItem);}
	public String getNotes1() {return checkGet(notes1);}
	public void setNotes1(String notes1) {this.notes1 = checkSet(notes1);}
	public String getUseSeparate() {return checkGet(useSeparate);}
	public void setUseSeparate(String useSeparate) {this.useSeparate = checkSet(useSeparate);}
	public String getUseKind() {return checkGet(useKind);}
	public void setUseKind(String useKind) {this.useKind = checkSet(useKind);}
	public String getOldPropertyNo() {return checkGet(oldPropertyNo);}
	public void setOldPropertyNo(String oldPropertyNo) {this.oldPropertyNo = checkSet(oldPropertyNo);}
	public String getOldSerialNo() {return checkGet(oldSerialNo);}
	public void setOldSerialNo(String oldSerialNo) {this.oldSerialNo = checkSet(oldSerialNo);}
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
	public String getPropertyNoName() {return checkGet(propertyNoName);}
	public void setPropertyNoName(String propertyNoName) {this.propertyNoName = checkSet(propertyNoName);}
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
	public String getAdjustBookUnit() {return checkGet(adjustBookUnit);}
	public void setAdjustBookUnit(String adjustBookUnit) {this.adjustBookUnit = checkSet(adjustBookUnit);}
	public String getAdjustBookValue() {return checkGet(adjustBookValue);}
	public void setAdjustBookValue(String adjustBookValue) {this.adjustBookValue = checkSet(adjustBookValue);}
	public String getOldPropertyName() {return checkGet(oldPropertyName);}
	public void setOldPropertyName(String oldPropertyName) {this.oldPropertyName = checkSet(oldPropertyName);}
				
	
	private String strAdjustDate;
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
	
	public String getStrAdjustDate() {return checkGet(strAdjustDate);}
	public void setStrAdjustDate(String strAdjustDate) {this.strAdjustDate = checkSet(strAdjustDate);}
	public String getEnterOrg() {return checkGet(enterOrg);}
	public void setEnterOrg(String enterOrg) {this.enterOrg = checkSet(enterOrg);}
	public String getEnterOrgName() {return checkGet(enterOrgName);}
	public void setEnterOrgName(String enterOrgName) {this.enterOrgName = checkSet(enterOrgName);}
	public String getCaseNo() {return checkGet(caseNo);}
	public void setCaseNo(String caseNo) {this.caseNo = checkSet(caseNo);}
	public String getOwnership() {return checkGet(ownership);}
	public void setOwnership(String ownership) {this.ownership = checkSet(ownership);}
	public String getDifferenceKind() {return checkGet(differenceKind);}
	public void setDifferenceKind(String differenceKind) {this.differenceKind = checkSet(differenceKind);}
	public String getEngineeringNo() {return checkGet(engineeringNo);}
	public void setEngineeringNo(String engineeringNo) {this.engineeringNo = checkSet(engineeringNo);}
	public String getEngineeringNoName() {return checkGet(engineeringNoName);}
	public void setEngineeringNoName(String engineeringNoName) {this.engineeringNoName = checkSet(engineeringNoName);}
	public String getCaseName() {return checkGet(caseName);}
	public void setCaseName(String caseName) {this.caseName = checkSet(caseName);}
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
	public String getAdjustDate() {return checkGet(adjustDate);}
	public void setAdjustDate(String adjustDate) {this.adjustDate = checkSet(adjustDate);}
	public String getApproveOrg() {return checkGet(approveOrg);}
	public void setApproveOrg(String approveOrg) {this.approveOrg = checkSet(approveOrg);}
	public String getApproveDate() {return checkGet(approveDate);}
	public void setApproveDate(String approveDate) {this.approveDate = checkSet(approveDate);}
	public String getApproveDoc() {return checkGet(approveDoc);}
	public void setApproveDoc(String approveDoc) {this.approveDoc = checkSet(approveDoc);}
	public String getVerify() {return checkGet(verify);}
	public void setVerify(String verify) {this.verify = checkSet(verify);}
	public String getNotes() {return checkGet(notes);}
	public void setNotes(String notes) {this.notes = checkSet(notes);}
	public String getEditID() {return checkGet(editID);}
	public void setEditID(String editID) {this.editID = checkSet(editID);}
	public String getEditDate() {return checkGet(editDate);}
	public void setEditDate(String editDate) {this.editDate = checkSet(editDate);}
	public String getEditTime() {return checkGet(editTime);}
	public void setEditTime(String editTime) {this.editTime = checkSet(editTime);}

	private String enterOrgName;
	private String propertyNoName;
	private String engineeringNoName;

	private String adjustType;
	private String adjustBookUnit;
	private String adjustBookValue;

	private String keepUnitName;
	private String keeperName;
	private String useUnitName;
	private String userName;

	private String bookValue;
	private String material;
	private String propertyUnit;
	private String limitYear;

	private String enterOrg;
	private String caseNo;
	private String ownership;
	private String differenceKind;
	private String engineeringNo;
	private String caseSerialNo;
	private String propertyNo;
	private String lotNo;
	private String serialNo;
	private String otherPropertyUnit;
	private String otherMaterial;
	private String otherLimitYear;
	private String propertyName1;
	private String cause;
	private String cause1;
	private String adjustDate;
	private String verify;
	private String propertyKind;
	private String fundType;
	private String valuable;
	private String sourceDate;
	private String buyDate;
	private String bookAmount;
	private String oldBookUnit;
	private String oldBookValue;
	private String oldNetUnit;
	private String oldNetValue;
	private String addBookUnit;
	private String addBookValue;
	private String addNetUnit;
	private String addNetValue;
	private String addLimitYear;
	private String overLimitYear;
	private String overLimitYear2;
	private String reduceBookUnit;
	private String reduceBookValue;
	private String reduceAccumDepr;
	private String reduceNetUnit;
	private String reduceNetValue;
	private String reduceLimitYear;
	private String newBookUnit;
	private String newBookValue;
	private String newNetUnit;
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
	private String newOtherLimitYear;
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
	
	public String gettOriginalUnit() {return checkGet(tOriginalUnit);}
	public void settOriginalUnit(String tOriginalUnit) {this.tOriginalUnit = checkSet(tOriginalUnit);}
	public String getReduceAccumDepr() {return checkGet(reduceAccumDepr);}
	public void setReduceAccumDepr(String reduceAccumDepr) {this.reduceAccumDepr = checkSet(reduceAccumDepr);}
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
	public String getNewOtherLimitYear() {return checkGet(newOtherLimitYear);}
	public void setNewOtherLimitYear(String newOtherLimitYear) {this.newOtherLimitYear = checkSet(newOtherLimitYear);}
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
	
	private String sourceKind;
	private String checkEnterOrg;
	private String securityName;
	private String oldBookAmount;
	private String addBookAmount;
	private String reduceBookAmount;
	private String newBookAmount;
	private String newBookSheet;
	public String getSourceKind() {return checkGet(sourceKind);}
	public void setSourceKind(String sourceKind) {this.sourceKind = checkSet(sourceKind);}
	public String getCheckEnterOrg() {return checkGet(checkEnterOrg);}
	public void setCheckEnterOrg(String checkEnterOrg) {this.checkEnterOrg = checkSet(checkEnterOrg);}
	public String getSecurityName() {return checkGet(securityName);}
	public void setSecurityName(String securityName) {this.securityName = checkSet(securityName);}
	public String getOldBookAmount() {return checkGet(oldBookAmount);}
	public void setOldBookAmount(String oldBookAmount) {this.oldBookAmount = checkSet(oldBookAmount);}
	public String getAddBookAmount() {return checkGet(addBookAmount);}
	public void setAddBookAmount(String addBookAmount) {this.addBookAmount = checkSet(addBookAmount);}
	public String getReduceBookAmount() {return checkGet(reduceBookAmount);}
	public void setReduceBookAmount(String reduceBookAmount) {this.reduceBookAmount = checkSet(reduceBookAmount);}
	public String getNewBookAmount() {return checkGet(newBookAmount);}
	public void setNewBookAmount(String newBookAmount) {this.newBookAmount = checkSet(newBookAmount);}
	public String getNewBookSheet() {return checkGet(newBookSheet);}
	public void setNewBookSheet(String newBookSheet) {this.newBookSheet = checkSet(newBookSheet);}	
	
	private String place1Name;
	public String getPlace1Name() {return checkGet(place1Name);}
	public void setPlace1Name(String place1Name) {this.place1Name = checkSet(place1Name);}
	
	private String oldApportionEndYM;
	private String newApportionEndYM;
	public String getOldApportionEndYM() {return checkGet(oldApportionEndYM);}
	public void setOldApportionEndYM(String oldApportionEndYM) {this.oldApportionEndYM = checkSet(oldApportionEndYM);}
	public String getNewApportionEndYM() {return checkGet(newApportionEndYM);}
	public void setNewApportionEndYM(String newApportionEndYM) {this.newApportionEndYM = checkSet(newApportionEndYM);}
	
	private String addMeasure;
	private String reduceMeasure;
	public String getAddMeasure() {return checkGet(addMeasure);}
	public void setAddMeasure(String addMeasure) {this.addMeasure = checkSet(addMeasure);}
	public String getReduceMeasure() {return checkGet(reduceMeasure);}
	public void setReduceMeasure(String reduceMeasure) {this.reduceMeasure = checkSet(reduceMeasure);}
	
	private String netValue;
	private String deprMethod;
	public String getNetValue() {return checkGet(netValue);}
	public void setNetValue(String netValue) {this.netValue = checkSet(netValue);}
	public String getDeprMethod() {return checkGet(deprMethod);}
	public void setDeprMethod(String deprMethod) {this.deprMethod = checkSet(deprMethod);}
	
	private String oldMonthDepr1;
	private String newMonthDepr1;
	public String getOldMonthDepr1() {return checkGet(oldMonthDepr1);}
	public void setOldMonthDepr1(String oldMonthDepr1) {this.oldMonthDepr1 = checkSet(oldMonthDepr1);}
	public String getNewMonthDepr1() {return checkGet(newMonthDepr1);}
	public void setNewMonthDepr1(String newMonthDepr1) {this.newMonthDepr1 = checkSet(newMonthDepr1);}

	private String summonsDate;
	public String getSummonsDate() {return checkGet(summonsDate);}
	public void setSummonsDate(String summonsDate) {this.summonsDate = checkSet(summonsDate);}
	
	
	private String originalDeprMethod;
	private String originalBuildFeeCB;
	private String originalDeprUnitCB;
	private String originalDeprPark;
	private String originalDeprUnit;
	private String originalDeprUnit1;
	private String originalDeprAccounts;
	private String originalScrapValue;
	private String originalDeprAmount;
	private String originalAccumDepr;
	private String originalApportionMonth;
	private String originalMonthDepr;
	private String originalMonthDepr1;
	private String originalApportionEndYM;
	
	private String buildFeeCB;
	private String deprUnitCB;
	private String deprPark;
	private String deprUnit;
	private String deprUnit1;
	private String deprAccounts;
	private String scrapValue;
	private String deprAmount;
	private String apportionMonth;
	private String monthDepr;
	private String monthDepr1;
	private String apportionEndYM;
	
	public String getOriginalDeprMethod() {return checkGet(originalDeprMethod);}
	public void setOriginalDeprMethod(String originalDeprMethod) {this.originalDeprMethod = checkSet(originalDeprMethod);}
	public String getOriginalBuildFeeCB() {return checkGet(originalBuildFeeCB);}
	public void setOriginalBuildFeeCB(String originalBuildFeeCB) {this.originalBuildFeeCB = checkSet(originalBuildFeeCB);}
	public String getOriginalDeprUnitCB() {return checkGet(originalDeprUnitCB);}
	public void setOriginalDeprUnitCB(String originalDeprUnitCB) {this.originalDeprUnitCB = checkSet(originalDeprUnitCB);}
	public String getOriginalDeprPark() {return checkGet(originalDeprPark);}
	public void setOriginalDeprPark(String originalDeprPark) {this.originalDeprPark = checkSet(originalDeprPark);}
	public String getOriginalDeprUnit() {return checkGet(originalDeprUnit);}
	public void setOriginalDeprUnit(String originalDeprUnit) {this.originalDeprUnit = checkSet(originalDeprUnit);}
	public String getOriginalDeprUnit1() {return checkGet(originalDeprUnit1);}
	public void setOriginalDeprUnit1(String originalDeprUnit1) {this.originalDeprUnit1 = checkSet(originalDeprUnit1);}
	public String getOriginalDeprAccounts() {return checkGet(originalDeprAccounts);}
	public void setOriginalDeprAccounts(String originalDeprAccounts) {this.originalDeprAccounts = checkSet(originalDeprAccounts);}
	public String getOriginalScrapValue() {return checkGet(originalScrapValue);}
	public void setOriginalScrapValue(String originalScrapValue) {this.originalScrapValue = checkSet(originalScrapValue);}
	public String getOriginalDeprAmount() {return checkGet(originalDeprAmount);}
	public void setOriginalDeprAmount(String originalDeprAmount) {this.originalDeprAmount = checkSet(originalDeprAmount);}
	public String getOriginalAccumDepr() {return checkGet(originalAccumDepr);}
	public void setOriginalAccumDepr(String originalAccumDepr) {this.originalAccumDepr = checkSet(originalAccumDepr);}
	public String getOriginalApportionMonth() {return checkGet(originalApportionMonth);}
	public void setOriginalApportionMonth(String originalApportionMonth) {this.originalApportionMonth = checkSet(originalApportionMonth);}
	public String getOriginalMonthDepr() {return checkGet(originalMonthDepr);}
	public void setOriginalMonthDepr(String originalMonthDepr) {this.originalMonthDepr = checkSet(originalMonthDepr);}
	public String getOriginalMonthDepr1() {return checkGet(originalMonthDepr1);}
	public void setOriginalMonthDepr1(String originalMonthDepr1) {this.originalMonthDepr1 = checkSet(originalMonthDepr1);}
	public String getOriginalApportionEndYM() {return checkGet(originalApportionEndYM);}
	public void setOriginalApportionEndYM(String originalApportionEndYM) {this.originalApportionEndYM = checkSet(originalApportionEndYM);}
	
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
	public String getApportionMonth() {return checkGet(apportionMonth);}
	public void setApportionMonth(String apportionMonth) {this.apportionMonth = checkSet(apportionMonth);}
	public String getMonthDepr() {return checkGet(monthDepr);}
	public void setMonthDepr(String monthDepr) {this.monthDepr = checkSet(monthDepr);}
	public String getMonthDepr1() {return checkGet(monthDepr1);}
	public void setMonthDepr1(String monthDepr1) {this.monthDepr1 = checkSet(monthDepr1);}
	public String getApportionEndYM() {return checkGet(apportionEndYM);}
	public void setApportionEndYM(String apportionEndYM) {this.apportionEndYM = checkSet(apportionEndYM);}
	
	private String accumDepr;
	public String getAccumDepr() {return checkGet(accumDepr);}
	public void setAccumDepr(String accumDepr) {this.accumDepr = checkSet(accumDepr);}
	
	private String closing1ym;
	public String getClosing1ym() {return checkGet(closing1ym);}
	public void setClosing1ym(String closing1ym) {this.closing1ym = checkSet(closing1ym);}
	
}