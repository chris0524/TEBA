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

import util.Common;
import util.Database;
import TDlib_Simple.com.src.DBServerTools;

public class UNTCH001F10 extends UNTCH001Q{

	public boolean checkUpdateError(){
		boolean result = true;
		if("Y".equals(getVerify()) && isBigThenEnterDate()){
			result = false;
			setErrorMsg("入帳日期不可小於 購置日期、財產取得日期或建築日期");
		}else if("Y".equals(getVerify()) && checkClosingYMfromUNTAC_CLOSINGPT(getEnterDate(),getEnterOrg(),getDifferenceKind())){
			result = false;
			setErrorMsg("入帳日期需為最大月結年月＋１");
		}else if("N".equals(getVerify()) && checkCanNotReVerify(getEnterDateTemp(),getEnterOrg(),getDifferenceKind())){
			result = false;
			setErrorMsg("當月已月結不可取消入帳");
		}
		return result;
	}
	
	@Override
	protected List _getBeans_asList(){
		List list = new ArrayList();
		
		list.add(new unt.la.UNTLA001F());
		list.add(new unt.bu.UNTBU001F());
		list.add(new unt.rf.UNTRF001F());
		list.add(new unt.mp.UNTMP001F());
		
		return list;
	}
	
	@Override
	protected List _getBeansExtend_asList(){
		List list = new ArrayList();
		
		list.add(new unt.vp.UNTVP001F());		
		list.add(new unt.rt.UNTRT001F(this.getClass().getSimpleName()));
		
		return list;
	}

	//====================================================================
	@Override
	protected void _setDefaultValue(){
		this.setClosing("N");
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
							" select count(*) as count from UNTLA_LAND" + condition +
							" union" +
							" select count(*) as count from UNTBU_BUILDING" + condition +
							" union" +
							" select count(*) as count from UNTRF_ATTACHMENT" + condition +
							" union" +
							" select count(*) as count from UNTMP_MOVABLE" + condition +
							" union" +
							" select count(*) as count from UNTVP_ADDPROOF" + condition +
							" union" +
							" select count(*) as count from UNTRT_ADDPROOF" + condition +
						") a";
		
		DBServerTools dbt = new DBServerTools();
		dbt._setDatabase(new Database());
		String checkDataCount = dbt._execSQL_asString(sql);
		
		if("0".equals(checkDataCount)){
			result = true;
		}

		return result;
	}
	
	/**
	 * <br>
	 * <br>目的：依查詢欄位查詢多筆資料
	 * <br>參數：無
	 * <br>傳回：傳回ArrayList
	*/
	
	public ArrayList queryAll() throws Exception{
		setOldPage();
		Database db = null;
		ArrayList objList=new ArrayList();

		try {
			StringBuilder sql = new StringBuilder();
			
			sql.append("select * from (")
				.append("select distinct ").append(
						" a.enterorg, a.ownership, a.caseno, a.proofYear, a.proofDoc, a.proofNo, a.writedate,").append(
						" a.verify, a.enterdate, a.differenceKind").append(
					" from UNTLA_ADDPROOF a").append(
						" left join UNTLA_LAND b on a.enterorg=b.enterorg and a.ownership=b.ownership and a.caseno=b.caseno and a.differenceKind=b.differenceKind").append(
						" left join UNTBU_BUILDING c on a.enterorg=c.enterorg and a.ownership=c.ownership and a.caseno=c.caseno and a.differenceKind=c.differenceKind").append(
						" left join UNTRF_ATTACHMENT d on a.enterorg=d.enterorg and a.ownership=d.ownership and a.caseno=d.caseno  and a.differenceKind=d.differenceKind").append(
						" left join UNTMP_MOVABLE e on a.enterorg=e.enterorg and a.ownership=e.ownership and a.caseno=e.caseno and a.differenceKind=e.differenceKind").append(
						" left join UNTVP_ADDPROOF f on a.enterorg=f.enterorg and a.ownership=f.ownership and a.caseno=f.caseno and a.differenceKind=f.differenceKind").append(
						" left join UNTRT_ADDPROOF g on a.enterorg=g.enterorg and a.ownership=g.ownership and a.caseno=g.caseno and a.differenceKind=g.differenceKind").append(
						" left join UNTMP_MOVABLEDETAIL h on a.enterorg=h.enterorg and a.ownership=h.ownership and a.caseno=h.caseno and a.differenceKind=h.differenceKind").append(										
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
				sql.append(" and a.enterorg = ").append(Common.sqlChar(getQ_enterOrg()));
				
			} else {
				if (!getIsAdminManager().equalsIgnoreCase("Y")) {
					if ("Y".equals(getIsOrganManager())) {					
						sql.append(" and ( a.enterorg = ").append(Common.sqlChar(getOrganID())).append(
								" or a.enterorg in ( select organid from SYSCA_ORGAN where organsuperior = ").append(
								Common.sqlChar(getOrganID())).append(" ) ) ");
					} else {
						sql.append(" and a.enterorg = ").append(Common.sqlChar(getOrganID()));
					}
				}
			}
			if (!"".equals(getQ_ownership())) {
				sql.append(" and a.ownership = ").append(Common.sqlChar(getQ_ownership()));	
			}
			if (!"".equals(getQ_caseNoS())){
				sql.append(" and a.caseno >= ").append(Common.sqlChar(Common.formatRearString(getQ_caseNoS(),10,'0')));
			}
			if (!"".equals(getQ_caseNoE())){
				sql.append(" and a.caseno <= ").append(Common.sqlChar(Common.formatRearString(getQ_caseNoE(),10, '9')));
			}
			if (!"".equals(getQ_enterDateS())){
				sql.append(" and a.enterDate >= ").append(Common.sqlChar(new UNTCH_Tools()._transToCE_Year(getQ_enterDateS())));
			}
			if (!"".equals(getQ_enterDateE())){
				sql.append(" and a.enterDate <= ").append(Common.sqlChar(new UNTCH_Tools()._transToCE_Year(getQ_enterDateE())));
			}
			if (!"".equals(getQ_verify())){
				sql.append(" and a.verify = ").append(Common.sqlChar(getQ_verify()));
			}
			if (!"".equals(getQ_caseName())){
		    	sql.append(" and a.casename like '%").append(getQ_caseName()).append("%'");
			}
			if (!"".equals(getQ_writeDateS())){
				sql.append(" and a.writedate >= ").append(Common.sqlChar(new UNTCH_Tools()._transToCE_Year(getQ_writeDateS())));
			}
			if (!"".equals(getQ_writeDateE())){
				sql.append(" and a.writedate <= ").append(Common.sqlChar(new UNTCH_Tools()._transToCE_Year(getQ_writeDateE())));
			}
			if (!"".equals(getQ_proofYear())){
				sql.append(" and a.proofYear = ").append(Common.sqlChar(getQ_proofYear()));
			}
			if (!"".equals(getQ_proofDoc())){
		    	sql.append(" and a.proofdoc like '%").append(getQ_proofDoc()).append("%'");
			}
			if (!"".equals(getQ_proofNoS())){
				sql.append(" and a.proofno >= ").append(Common.sqlChar(getQ_proofNoS()));
			}
			if (!"".equals(getQ_proofNoE())){ 
				sql.append(" and a.proofno <= ").append(Common.sqlChar(getQ_proofNoE()));
			}
			if (!"".equals(getQ_differenceKind())){ 
				sql.append(" and a.differenceKind = ").append(Common.sqlChar(getQ_differenceKind()));
			}
			if (!"".equals(getQ_engineeringNo())){ 
				sql.append(" and a.engineeringNo = ").append(Common.sqlChar(getQ_engineeringNo()));
			}
			
			
			if (!"".equals(getQ_sourceKind())){ 
				sql.append(" and ( b.sourcekind = ").append(Common.sqlChar(getQ_sourceKind())+" or ");
				sql.append(" c.sourcekind = ").append(Common.sqlChar(getQ_sourceKind())+" or ");
				sql.append(" d.sourcekind = ").append(Common.sqlChar(getQ_sourceKind())+" or ");
				sql.append(" e.sourcekind = ").append(Common.sqlChar(getQ_sourceKind())+" or ");
				sql.append(" f.sourcekind = ").append(Common.sqlChar(getQ_sourceKind())+" or ");
				sql.append(" g.sourcekind = ").append(Common.sqlChar(getQ_sourceKind())+" )");
			}
			
					
			String q_signLaNoS = "";
			String q_signLaNoE = "";
			if (!"".equals(getQ_signLaNo1())){
				q_signLaNoS=getQ_signLaNo1().substring(0,1)+"______";
				q_signLaNoE=getQ_signLaNo1().substring(0,1)+"______";
			}
			if (!"".equals(getQ_signLaNo2())){
				q_signLaNoS=getQ_signLaNo2().substring(0,3)+"____";			
				q_signLaNoE=getQ_signLaNo2().substring(0,3)+"____";
			}
			if (!"".equals(getQ_signLaNo3())){
				q_signLaNoS=getQ_signLaNo3();
				q_signLaNoE=getQ_signLaNo3();
			}
				
			if (!"".equals(getQ_signLaNo4S()) && !"".equals(getQ_signLaNo5S())){
				if ("".equals(q_signLaNoS)){
					q_signLaNoS = "_______" + getQ_signLaNo4S() + getQ_signLaNo5S();
				}else{
					q_signLaNoS = q_signLaNoS + getQ_signLaNo4S() + getQ_signLaNo5S();				
				}
			}	
			if (!"".equals(getQ_signLaNo4E()) && !"".equals(getQ_signLaNo5E())){
				if ("".equals(q_signLaNoE)){
					q_signLaNoE = "_______" + getQ_signLaNo4E() + getQ_signLaNo5E();
				}else{
					q_signLaNoE = q_signLaNoE + getQ_signLaNo4E() + getQ_signLaNo5E();				
				}
			}	
			
			if (!"".equals(q_signLaNoS))
				sql.append(" and b.signno >= '").append(q_signLaNoS).append("%'");
			if (!"".equals(q_signLaNoE))
				sql.append(" and b.signno <= '").append(q_signLaNoE).append("%'");
			
			String q_signBuNoS = "";
			String q_signBuNoE = "";
			if (!"".equals(getQ_signBuNo1())){
				q_signBuNoS=getQ_signBuNo1().substring(0,1)+"______";
				q_signBuNoE=getQ_signBuNo1().substring(0,1)+"______";
			}
			if (!"".equals(getQ_signBuNo2())){
				q_signBuNoS=getQ_signBuNo2().substring(0,3)+"____";			
				q_signBuNoE=getQ_signBuNo2().substring(0,3)+"____";
			}
			if (!"".equals(getQ_signBuNo3())){
				q_signBuNoS=getQ_signBuNo3();
				q_signBuNoE=getQ_signBuNo3();
			}
				
			if (!"".equals(getQ_signBuNo4S()) && !"".equals(getQ_signBuNo5S())){
				if ("".equals(q_signBuNoS)){
					q_signBuNoS = "_______" + getQ_signBuNo4S() + getQ_signBuNo5S();
				}else{
					q_signBuNoS = q_signBuNoS + getQ_signBuNo4S() + getQ_signBuNo5S();				
				}
			}	
			if (!"".equals(getQ_signBuNo4E()) && !"".equals(getQ_signBuNo5E())){
				if ("".equals(q_signBuNoE)){
					q_signBuNoE = "_______" + getQ_signBuNo4E() + getQ_signBuNo5E();
				}else{
					q_signBuNoE = q_signBuNoE + getQ_signBuNo4E() + getQ_signBuNo5E();				
				}
			}	
			
			if (!"".equals(q_signBuNoS))
				sql.append(" and c.signno >= '").append(q_signBuNoS).append("%'");
			if (!"".equals(q_signBuNoE))
				sql.append(" and c.signno <= '").append(q_signBuNoE).append("%'");
			
			if (!"".equals(getQ_propertyKind()))
				sql.append(" and (b.propertykind = ").append(Common.sqlChar(getQ_propertyKind()))
					.append(" or c.propertykind = ").append(Common.sqlChar(getQ_propertyKind()))
					.append(" or d.propertykind = ").append(Common.sqlChar(getQ_propertyKind()))
					.append(" or e.propertykind = ").append(Common.sqlChar(getQ_propertyKind()))
					.append(" or f.propertykind = ").append(Common.sqlChar(getQ_propertyKind()))
					.append(" or g.propertykind = ").append(Common.sqlChar(getQ_propertyKind()))
					.append(")");
			if (!"".equals(getQ_fundType()))
				sql.append(" and (b.fundtype = ").append(Common.sqlChar(getQ_fundType()))
					.append(" or c.fundtype = ").append(Common.sqlChar(getQ_fundType()))
					.append(" or d.fundtype = ").append(Common.sqlChar(getQ_fundType()))
					.append(" or e.fundtype = ").append(Common.sqlChar(getQ_fundType()))
					.append(" or f.fundtype = ").append(Common.sqlChar(getQ_fundType()))
					.append(" or g.fundtype = ").append(Common.sqlChar(getQ_fundType()))
					.append(")");
			if (!"".equals(getQ_valuable()))
				sql.append(" and (b.valuable = ").append(Common.sqlChar(getQ_valuable()))
					.append(" or c.valuable = ").append(Common.sqlChar(getQ_valuable()))
					.append(" or d.valuable = ").append(Common.sqlChar(getQ_valuable()))
					.append(" or e.valuable = ").append(Common.sqlChar(getQ_valuable()))
					.append(" or f.valuable = ").append(Common.sqlChar(getQ_valuable()))
					.append(" or g.valuable = ").append(Common.sqlChar(getQ_valuable()))
					.append(")");
			if (!"".equals(getQ_taxCredit()))
				sql.append(" and (b.taxcredit = ").append(Common.sqlChar(getQ_taxCredit()))
					.append(" or c.taxcredit = ").append(Common.sqlChar(getQ_taxCredit()))
					.append(" or d.taxcredit = ").append(Common.sqlChar(getQ_taxCredit()))					
					.append(")");
			if (!"".equals(getQ_cause()))
				sql.append(" and (b.cause = ").append(Common.sqlChar(getQ_cause()))
					.append(" or c.cause = ").append(Common.sqlChar(getQ_cause()))
					.append(" or d.cause = ").append(Common.sqlChar(getQ_cause()))
					.append(" or e.cause = ").append(Common.sqlChar(getQ_cause()))
					.append(" or f.cause = ").append(Common.sqlChar(getQ_cause()))
					.append(" or g.cause = ").append(Common.sqlChar(getQ_cause()))
					.append(")");
			if (!"".equals(getQ_propertyName1()))
				sql.append(" and (b.propertyname1 = ").append(Common.sqlChar(getQ_propertyName1()))
					.append(" or c.propertyname1 = ").append(Common.sqlChar(getQ_propertyName1()))
					.append(" or d.propertyname1 = ").append(Common.sqlChar(getQ_propertyName1()))
					.append(" or e.propertyname1 = ").append(Common.sqlChar(getQ_propertyName1()))
					.append(")");
			if (!"".equals(getQ_keepUnit()))
				sql.append(" and (b.keepunit = ").append(Common.sqlChar(getQ_keepUnit()))
					.append(" or c.keepunit = ").append(Common.sqlChar(getQ_keepUnit()))
					.append(" or e.keepunit = ").append(Common.sqlChar(getQ_keepUnit()))
					.append(" or f.keepunit = ").append(Common.sqlChar(getQ_keepUnit()))
					.append(" or g.keepunit = ").append(Common.sqlChar(getQ_keepUnit()))
					.append(" or h.keepunit = ").append(Common.sqlChar(getQ_keepUnit()))
					.append(")");
			if (!"".equals(getQ_keeper()))
				sql.append(" and (b.keeper = ").append(Common.sqlChar(getQ_keeper()))
					.append(" or c.keeper = ").append(Common.sqlChar(getQ_keeper()))
					.append(" or e.keeper = ").append(Common.sqlChar(getQ_keeper()))
					.append(" or f.keeper = ").append(Common.sqlChar(getQ_keeper()))
					.append(" or g.keeper = ").append(Common.sqlChar(getQ_keeper()))
					.append(" or h.keeper = ").append(Common.sqlChar(getQ_keeper()))
					.append(")");
			if (!"".equals(getQ_useUnit()))
				sql.append(" and (b.useunit = ").append(Common.sqlChar(getQ_useUnit()))
					.append(" or c.useunit = ").append(Common.sqlChar(getQ_useUnit()))
					.append(" or e.useunit = ").append(Common.sqlChar(getQ_useUnit()))
					.append(" or f.useunit = ").append(Common.sqlChar(getQ_useUnit()))
					.append(" or g.useunit = ").append(Common.sqlChar(getQ_useUnit()))
					.append(" or h.useunit = ").append(Common.sqlChar(getQ_useUnit()))
					.append(")");
			if (!"".equals(getQ_userNo()))
				sql.append(" and (b.userno = ").append(Common.sqlChar(getQ_userNo()))
					.append(" or c.userno = ").append(Common.sqlChar(getQ_userNo()))
					.append(" or e.userno = ").append(Common.sqlChar(getQ_userNo()))
					.append(" or f.userno = ").append(Common.sqlChar(getQ_userNo()))
					.append(" or g.userno = ").append(Common.sqlChar(getQ_userNo()))
					.append(" or h.userno = ").append(Common.sqlChar(getQ_userNo()))
					.append(")");
			
			
			if (!"".equals(getQ_dataState()))
				sql.append(" and (b.datastate = ").append(Common.sqlChar(getQ_dataState()))
					.append(" or c.datastate = ").append(Common.sqlChar(getQ_dataState()))
					.append(" or d.datastate = ").append(Common.sqlChar(getQ_dataState()))
					.append(" or e.datastate = ").append(Common.sqlChar(getQ_dataState()))
					.append(" or f.datastate = ").append(Common.sqlChar(getQ_dataState()))
					.append(" or g.datastate = ").append(Common.sqlChar(getQ_dataState()))
					.append(")");

			if (!"".equals(getQ_originalUserNote()))
				sql.append(" and (b.originalusernote = ").append(Common.sqlChar(getQ_originalUserNote()))
					.append(" or c.originalusernote = ").append(Common.sqlChar(getQ_originalUserNote()))
					.append(" or e.originalusernote = ").append(Common.sqlChar(getQ_originalUserNote()))
					.append(" or f.originalusernote = ").append(Common.sqlChar(getQ_originalUserNote()))
					.append(" or g.originalusernote = ").append(Common.sqlChar(getQ_originalUserNote()))
					.append(" or h.originalusernote = ").append(Common.sqlChar(getQ_originalUserNote()))
					.append(")");
			
			if (!"".equals(getQ_originalBuildFeeCB()))
				sql.append(" and (c.originalBuildFeeCB = ").append(Common.sqlChar(getQ_originalBuildFeeCB()))
					.append(" or d.originalBuildFeeCB = ").append(Common.sqlChar(getQ_originalBuildFeeCB()))
					.append(" or e.originalBuildFeeCB = ").append(Common.sqlChar(getQ_originalBuildFeeCB()))
					.append(")");
		
			if (!"".equals(getQ_originalDeprUnitCB()))
				sql.append(" and (c.originalDeprUnitCB = ").append(Common.sqlChar(getQ_originalDeprUnitCB()))
					.append(" or d.originalDeprUnitCB = ").append(Common.sqlChar(getQ_originalDeprUnitCB()))
					.append(" or e.originalDeprUnitCB = ").append(Common.sqlChar(getQ_originalDeprUnitCB()))
					.append(")");
		
			if (!"".equals(getQ_doorPlate4()))
				sql.append(" and c.q_doorPlate4 = ").append(Common.sqlChar(getQ_doorPlate4()));
				
			
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
				String rowArray[]=new String[12];
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
				rowArray[8] = Common.get(rs.getString("verify"));
				rowArray[9] = uctls._getYNName(rs.getString("verify"));			
				rowArray[10] = Common.get(uctls._transToROC_Year(rs.getString("enterDate"))); 
				rowArray[11] = ""; 

				objList.add(rowArray);
				count++;
				} while (rs.next());
			}
			setStateQueryAllSuccess();
		} catch (Exception e) {
			log._execLogError(e.getMessage());
		} finally {
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
	//==============================================================
	
	public boolean isBigThenEnterDate(){
		boolean result = false;
		result = checkBuyDate_sourceDate_buildDate();		
		return result;
	}
	
		private boolean checkBuyDate_sourceDate_buildDate(){
			boolean result = false;
			String sql = "select " +
							" max(buydate) as buydate," +
							" max(sourcedate) as sourcedate," +
							" max(builddate) as builddate" +
							" from (" +
							" select case when buydate is null then 0 else buydate end as buydate, case when sourcedate is null then 0 else sourcedate end as sourcedate, null as builddate from UNTLA_LAND where 1=1" + getCheckEnterDateCondition() +
							" union" +
							" select case when buydate is null then 0 else buydate end as buydate, case when sourcedate is null then 0 else sourcedate end as sourcedate, builddate from UNTBU_BUILDING where 1=1" + getCheckEnterDateCondition() +
							" union" +
							" select case when buydate is null then 0 else buydate end as buydate, case when sourcedate is null then 0 else sourcedate end as sourcedate, null as builddate from UNTRF_ATTACHMENT where 1=1" + getCheckEnterDateCondition() +
							" union" +
							" select case when buydate is null then 0 else buydate end as buydate, case when sourcedate is null then 0 else sourcedate end as sourcedate, null as builddate from UNTMP_MOVABLE where 1=1" + getCheckEnterDateCondition() +
							" union" +
							" select case when buydate is null then 0 else buydate end as buydate, case when sourcedate is null then 0 else sourcedate end as sourcedate, null as builddate from UNTRT_ADDPROOF where 1=1" + getCheckEnterDateCondition() +
							" union" +
							" select case when buydate is null then 0 else buydate end as buydate, case when sourcedate is null then 0 else sourcedate end as sourcedate, null as builddate from UNTVP_ADDPROOF where 1=1" + getCheckEnterDateCondition() +
						") a";
			
			Database db = null;
			ResultSet rs = null;
			int buydate = 0;
			int sourcedate = 0;
			int builddate = 0;
			try{
				db = new Database();
				rs = db.querySQL(sql);
				while(rs.next()){
					buydate = rs.getInt("buydate");
					sourcedate = rs.getInt("sourcedate");
					builddate = rs.getInt("builddate");
				}
				rs.close();
			}catch(Exception e){
				e.printStackTrace();
			}finally{
				db.closeAll();
			}
			
			String checkDate = getEnterDate();
			int maxdate = Integer.parseInt(new UNTCH_Tools()._transToCE_Year(checkDate));
			
			if(maxdate < builddate || maxdate < buydate || maxdate < sourcedate){	result = true;}
			
			return result;
		}
	
			private String getCheckEnterDateCondition(){
				String result = " and enterorg = " + Common.sqlChar(getEnterOrg()) +
								" and caseno = " + Common.sqlChar(getCaseNo()) +
								" and ownership = " + Common.sqlChar(getOwnership()) +
								" and differencekind = " + Common.sqlChar(getDifferenceKind());
								
				return result;
			}
	
		
	//==============================================================
		
	

	//==============================================================	
	String enterOrg;
	String enterOrgName;
	String ownership;
	String caseNo;
	String caseName;
	String writeDate;
	String writeUnit;
	String manageNo;
	String summonsNo;
	String enterDate;
	String verify;
	String closing;
	String notes;
	String editID;
	String editDate;
	String oldVerify;
	String verifyError;
	String engineeringNo;
	String engineeringNoName;
	

	
	public String getEnterOrg(){ return checkGet(enterOrg); }
	public void setEnterOrg(String s){ enterOrg=checkSet(s); }
	public String getEnterOrgName(){ return checkGet(enterOrgName); }
	public void setEnterOrgName(String s){ enterOrgName=checkSet(s); }
	public String getOwnership(){ return checkGet(ownership); }
	public void setOwnership(String s){ ownership=checkSet(s); }
	public String getCaseNo() {return checkGet(caseNo);}
	public void setCaseNo(String caseNo) {this.caseNo = checkSet(caseNo);}
	public String getCaseName(){ return checkGet(caseName); }
	public void setCaseName(String s){ caseName=checkSet(s); }
	public String getWriteDate(){ return checkGet(writeDate); }
	public void setWriteDate(String s){ writeDate=checkSet(s); }
	public String getWriteUnit(){ return checkGet(writeUnit); }
	public void setWriteUnit(String s){ writeUnit=checkSet(s); }
	public String getManageNo(){ return checkGet(manageNo); }
	public void setManageNo(String s){ manageNo=checkSet(s); }
	public String getSummonsNo(){ return checkGet(summonsNo); }
	public void setSummonsNo(String s){ summonsNo=checkSet(s); }
	public String getEnterDate(){ return checkGet(enterDate); }
	public void setEnterDate(String s){ enterDate=checkSet(s); }
	public String getVerify(){ return checkGet(verify); }
	public void setVerify(String s){ verify=checkSet(s); }
	public String getClosing(){ return checkGet(closing); }
	public void setClosing(String s){ closing=checkSet(s); }
	public String getNotes(){ return checkGet(notes); }
	public void setNotes(String s){ notes=checkSet(s); }
	public String getEditID(){ return checkGet(editID); }
	public void setEditID(String s){ editID=checkSet(s); }
	public String getEditDate(){ return checkGet(editDate); }
	public void setEditDate(String s){ editDate=checkSet(s); }
	public String getOldVerify(){ return checkGet(oldVerify); }
	public void setOldVerify(String s){ oldVerify=checkSet(s); }
	
	public String getVerifyError(){ return checkGet(verifyError); }
	public void setVerifyError(String s){ verifyError=checkSet(s); }
	public String getEngineeringNo() {return checkGet(engineeringNo);}
	public void setEngineeringNo(String engineeringNo) {this.engineeringNo = checkSet(engineeringNo);}
	public String getEngineeringNoName() {return checkGet(engineeringNoName);}
	public void setEngineeringNoName(String engineeringNoName) {this.engineeringNoName = checkSet(engineeringNoName);}
	
	String closingYM;
	public String getClosingYM() {return checkGet(closingYM);}
	public void setClosingYM(String closingYM) {this.closingYM = checkSet(closingYM);}
	
	private String differenceKind;
	public String getDifferenceKind() {	return checkGet(differenceKind);}
	public void setDifferenceKind(String differenceKind) {	this.differenceKind = checkSet(differenceKind);}
	
	private String propertyNo;
	private String serialNo;
	public String getPropertyNo() {return checkGet(propertyNo);}
	public void setPropertyNo(String propertyNo) {this.propertyNo = checkSet(propertyNo);}
	public String getSerialNo() {return checkGet(serialNo);}
	public void setSerialNo(String serialNo) {this.serialNo = checkSet(serialNo);}

	private String enterDateTemp;
	public String getEnterDateTemp() {return checkGet(enterDateTemp);}
	public void setEnterDateTemp(String enterDateTemp) {this.enterDateTemp = checkSet(enterDateTemp);}	

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
	
}