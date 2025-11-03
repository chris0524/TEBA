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
import java.util.HashMap;
import java.util.Map;

import util.Common;
import util.Database;
import util.Datetime;
import util.MaxClosingYM;
import util.SuperBean2;
import TDlib_Simple.com.src.DBServerTools;
import TDlib_Simple.com.src.SQLCreator;

public class UNTCH002F01 extends UNTCH002Q{

	public boolean checkUpdateError(){
		boolean result = true;
		UNTCH_Tools ut = new UNTCH_Tools();
		if ("Y".equals(getVerify())) {
			if(!checkClosingYM("verify")){
				setErrorMsg("入帳日期需為最大月結年月＋１");
				result = false;
			}
			if (!checkNewestMovedate(ut._transToCE_Year(getMoveDate()))) {
				setErrorMsg("入帳日期需為最新一筆資料");
				result = false;
			}
		}
		return result;
	}
	
	public boolean checkReVerifyError(){
		boolean result = true;
		UNTCH_Tools ut = new UNTCH_Tools();
		if(!checkClosingYM("reverify")){
			setErrorMsg("當月已月結不可回復入帳");
			result = false;
		}
		if(!checkExistsNotVerify()){
			setErrorMsg("單據內有物品存在於尚未入帳之移動單內，無法回復入帳");
			result = false;
		}
		if (!checkNewestMovedate(ut._transToCE_Year(getMoveDateTemp()))) {
			setErrorMsg("非最後一筆入帳的資料，無法回復入帳");
			result = false;
		}
		return result;
	}
	
	// 檢查月結年月
	private boolean checkClosingYM(String type){
		boolean result = true;
		ResultSet rs;
		Database db = new Database();
		try{
			// 取得全部明細的區分別
			String sql = "select distinct differencekind from UNTMP_MOVEDETAIL"
					+ " where 1=1"
						+ " and enterorg=" + Common.sqlChar(getEnterOrg())
						+ " and caseno=" + Common.sqlChar(getCaseNo())
					+ "";
			rs = db.querySQL(sql);
			while(rs.next()){
				String differencekind = rs.getString(1);
				if ("verify".equals(type)) {
					if(new SuperBean2().checkClosingYMfromUNTAC_CLOSINGPT(getMoveDate(),getEnterOrg(),differencekind)){
						result = false;
						break;
					}
				} else if ("reverify".equals(type)) {
					if(new SuperBean2().checkCanNotReVerify(getMoveDateTemp(),getEnterOrg(),differencekind)){
						result = false;
						break;
					}
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			db.closeAll();
		}
		return result;
	}
	
	// 檢查是否為最後一筆移動資料
	private boolean checkNewestMovedate(String movedate){
		boolean result = false;
		ResultSet rs;
		Database db = new Database();
		try{
			String sql = "select count(1) from UNTMP_MOVEDETAIL a"
					+ " where 1=1"
						+ " and enterorg=" + Common.sqlChar(getEnterOrg())
						+ " and caseno=" + Common.sqlChar(getCaseNo())
						+ " and exists ("
							+ " select 1 from UNTMP_MOVEDETAIL"
							+ " where enterorg=a.enterorg and ownership=a.ownership and differencekind=a.differencekind "
								+ " and propertyno=a.propertyno and serialno=a.serialno"
								+ " and caseno<>a.caseno"
								+ " and newmovedate>="  + Common.sqlChar(movedate)
						+ ")"
					+ "";
			rs = db.querySQL(sql);
			if(rs.next()){
				if (rs.getInt(1) == 0) {
					result = true;
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			db.closeAll();
		}
		return result;
	}
	
	// 檢查是否有未入帳的移動資料
	private boolean checkExistsNotVerify(){
		boolean result = false;
		ResultSet rs;
		Database db = new Database();
		try{
			String sql = "select count(1) from UNTMP_MOVEDETAIL a"
					+ " where 1=1"
						+ " and enterorg=" + Common.sqlChar(getEnterOrg())
						+ " and caseno=" + Common.sqlChar(getCaseNo())
						+ " and exists ("
							+ " select 1 from UNTMP_MOVEDETAIL"
							+ " where enterorg=a.enterorg and ownership=a.ownership and differencekind=a.differencekind "
								+ " and propertyno=a.propertyno and serialno=a.serialno"
								+ " and verify='N'"
						+ ")"
					+ "";
			rs = db.querySQL(sql);
			if(rs.next()){
				if (rs.getInt(1) == 0) {
					result = true;
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			db.closeAll();
		}
		return result;
	}
	
	protected String[] getInsertSQL(){
		setProofNo(MaxClosingYM.getMaxProofNo("UNTMP_MOVEPROOF",enterOrg,ownership,this.getProofYear()));
		
		this.setCaseNo(getNewCaseNo("UNTMP_MOVEPROOF"));
		
		String[] execSQLArray = new String[1];
		execSQLArray[0] = new SQLCreator()._obtainSQLforInsert("UNTMP_MOVEPROOF", getPKMap(), getRecordMap());
		return execSQLArray;
	}
	
	protected String[] getUpdateSQL(){
		String strSQL = ""; 
		strSQL += new SQLCreator()._obtainSQLforUpdate("UNTMP_MOVEPROOF", getPKMap(), getRecordMap()) +
				":;:";
		
		strSQL += updateTable();
		String[] execSQLArray = strSQL.split(":;:");
		
		return execSQLArray;
	}
	
	protected String[] getDeleteSQL(){
		String[] execSQLArray = new String[2];
		execSQLArray[0] = new SQLCreator()._obtainSQLforDelete("UNTMP_MOVEPROOF", getPKMap(), getRecordMap());
		
		execSQLArray[1] = "delete from UNTMP_MOVEDETAIL" +
							" where 1=1" +
							" and enterorg = '" + getEnterOrg() + "'" +
							" and ownership = '" + getOwnership() + "'" +
							" and caseno = '" + getCaseNo() + "'";
		
		return execSQLArray;
	}
	
	public UNTCH002F01  queryOne() throws Exception{
		Database db = new Database();
		UNTCH002F01 obj = this;
		try {
			String sql=" select a.*, "+
						" (select organsname from SYSCA_ORGAN z where z.organid = a.enterorg) as EnterOrgName" +
						" from UNTMP_MoveProof a where 1=1 " +
						" and a.enterOrg = " + Common.sqlChar(enterOrg) +
						" and a.ownership = " + Common.sqlChar(ownership) +
						" and a.caseNo = " + Common.sqlChar(caseNo) +
						"";
			
			ResultSet rs = db.querySQL(sql);
			UNTCH_Tools ut = new UNTCH_Tools();
			if (rs.next()){
				obj.setEnterOrg(rs.getString("EnterOrg"));
				obj.setEnterOrgName(rs.getString("EnterOrgName"));
				obj.setCaseNo(rs.getString("CaseNo"));
				obj.setOwnership(rs.getString("Ownership"));
				obj.setCaseName(rs.getString("CaseName"));
				obj.setWriteDate(ut._transToROC_Year(rs.getString("WriteDate")));
				obj.setWriteUnit(rs.getString("WriteUnit"));
				obj.setProofYear(rs.getString("ProofYear"));
				obj.setProofDoc(rs.getString("ProofDoc"));
				obj.setProofNo(rs.getString("ProofNo"));
				obj.setMoveDate(ut._transToROC_Year(rs.getString("MoveDate")));
				obj.setVerify(rs.getString("Verify"));
				obj.setNotes(rs.getString("Notes"));
				obj.setEditID(rs.getString("EditID"));
				obj.setEditDate(ut._transToROC_Year(rs.getString("EditDate")));
				obj.setEditTime(rs.getString("EditTime"));
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
		setOldPage();
		Database db = new Database();
		ArrayList objList=new ArrayList();

		try {
			StringBuilder sql = new StringBuilder();
			
			sql.append("select distinct * from ( select a.enterOrg, a.ownership, a.caseno," +
						" cast(a.proofyear as int) as proofYear, a.proofno, " +
						" a.proofYear + '年' + a.proofDoc + '字第' + a.proofNo + '號' as proofdata, " +
						" a.writeDate, a.moveDate, a.verify, " +
						" (select z.codeName from sysca_code z where 1=1 and (z.codeKindID='owa' or z.codeKindID='OWA') and a.ownership=z.codeID) as ownershipName, " +
						" (case a.verify when 'Y' then '是' else '否' end) as verifyName "+
						" from UNTMP_MoveProof a" +
						" left join UNTMP_MoveDetail b on a.enterorg = b.enterorg and a.caseno = b.caseno and a.ownership = b.ownership" +
						" where 1=1 ");	

			//以下四個條件為新增移動單後查詢
			if (!"".equals(getI_enterOrg())) {
				sql.append( " and a.enterorg = ").append(Common.sqlChar(getI_enterOrg()));
			}
			if (!"".equals(getI_ownership())) {
				sql.append( " and a.ownership = ").append(Common.sqlChar(getI_ownership()));	
			}
			if (!"".equals(getI_caseNo())) {
				sql.append( " and a.caseno = ").append(Common.sqlChar(getI_caseNo()));	
			}
			if (!"".equals(getI_differenceKind())) {
				sql.append( " and a.differencekind = ").append(Common.sqlChar(getI_differenceKind()));	
			}
			//以上四個條件為新增移動單後查詢
			
			
			if (!"".equals(getQ_enterOrg())) {
				sql.append(" and a.enterOrg = ").append(Common.sqlChar(getQ_enterOrg()));
			} else {
				if (!getIsAdminManager().equalsIgnoreCase("Y")) {
					if ("Y".equals(getIsOrganManager())) {					
						sql.append( " and a.enterOrg like '").append(getOrganID().substring(0,5)).append("%' ");					
					} else {
						sql.append(" and a.enterOrg = ").append(Common.sqlChar(getOrganID())) ;
					}
				}
			}
			if (!"".equals(getQ_ownership()))
				sql.append(" and a.ownership = ").append(Common.sqlChar(getQ_ownership())) ;
			if (!"".equals(getQ_caseNoS()))
				sql.append(" and a.caseNo >= rpad(").append(Common.sqlChar(getQ_caseNoS())).append(",10,'0')");
			if (!"".equals(getQ_caseNoE()))
				sql.append(" and a.caseNo <= rpad(").append(Common.sqlChar(getQ_caseNoE())).append(",10,'9')");
			if (!"".equals(getQ_caseName()))
				sql.append(" and a.caseName like ").append(Common.sqlChar("%"+getQ_caseName()+"%"));
			if (!"".equals(getQ_writeDateS()))
				sql.append(" and a.writeDate >= ").append(Common.sqlChar(new UNTCH_Tools()._transToCE_Year(getQ_writeDateS()))) ;
			if (!"".equals(getQ_writeDateE()))
				sql.append(" and a.writeDate <= ").append(Common.sqlChar(new UNTCH_Tools()._transToCE_Year(getQ_writeDateE()))) ;
			if (!"".equals(getQ_moveDateS()))
				sql.append(" and a.moveDate >= ").append(Common.sqlChar(new UNTCH_Tools()._transToCE_Year(getQ_moveDateS()))) ;
			if (!"".equals(getQ_moveDateE()))
				sql.append(" and a.moveDate <= ").append(Common.sqlChar(new UNTCH_Tools()._transToCE_Year(getQ_moveDateE()))) ;
			if (!"".equals(getQ_proofYear()))
				sql.append(" and a.proofYear = ").append(Common.sqlChar(getQ_proofYear()));
			if (!"".equals(getQ_proofDoc()))
				sql.append(" and a.proofDoc like ").append(Common.sqlChar("%"+getQ_proofDoc()+"%")) ;
			if (!"".equals(getQ_proofNoS()))
				sql.append(" and a.proofNo >= ").append(Common.sqlChar(getQ_proofNoS())) ;
			if (!"".equals(getQ_proofNoE()))
				sql.append(" and a.proofNo <= ").append(Common.sqlChar(getQ_proofNoE())) ;
			if (!"".equals(getQ_verify()))
				sql.append(" and a.verify = ").append(Common.sqlChar(getQ_verify())) ;
			if (!"".equals(getQ_propertyNoS()))
				sql.append(" and b.propertyNo >= ").append(Common.sqlChar(getQ_propertyNoS())) ;
			if (!"".equals(getQ_propertyNoE()))
				sql.append(" and b.propertyNo <= ").append(Common.sqlChar(getQ_propertyNoE())) ;
			if (!"".equals(getQ_serialNoS()))
				sql.append(" and b.serialNo >= ").append(Common.sqlChar(getQ_serialNoS())) ;
			if (!"".equals(getQ_serialNoE()))
				sql.append(" and b.serialNo <= ").append(Common.sqlChar(getQ_serialNoE())) ;
			if (!"".equals(getQ_propertyKind()))
				sql.append(" and b.propertyKind = ").append(Common.sqlChar(getQ_propertyKind()));
			if (!"".equals(getQ_fundType()))
				sql.append(" and b.fundType = ").append(Common.sqlChar(getQ_fundType())) ;
			if (!"".equals(getQ_valuable()))
				sql.append(" and b.valuable = ").append(Common.sqlChar(getQ_valuable())) ;
			if (!"".equals(getQ_oldKeepUnit()))
				sql.append(" and b.oldKeepUnit = ").append(Common.sqlChar(getQ_oldKeepUnit())) ;
			if (!"".equals(getQ_oldKeeper()))
				sql.append(" and b.oldKeeper = ").append(Common.sqlChar(getQ_oldKeeper())) ;
			if (!"".equals(getQ_oldUseUnit()))
				sql.append(" and b.oldUseUnit = ").append(Common.sqlChar(getQ_oldUseUnit())) ;
			if (!"".equals(getQ_oldUserNo()))
				sql.append(" and b.oldUserNo = ").append(Common.sqlChar(getQ_oldUserNo())) ;
			if (!"".equals(getQ_newKeepUnit()))
				sql.append(" and b.newKeepUnit = ").append(Common.sqlChar(getQ_newKeepUnit())) ;
			if (!"".equals(getQ_newKeeper()))
				sql.append(" and b.newKeeper = ").append(Common.sqlChar(getQ_newKeeper())) ;
			if (!"".equals(getQ_newUseUnit()))
				sql.append(" and b.newUseUnit = ").append(Common.sqlChar(getQ_newUseUnit())) ;
			if (!"".equals(getQ_newUserNo()))
				sql.append(" and b.newUserNo = ").append(Common.sqlChar(getQ_newUserNo())) ;
			
			// 706  保管人 僅能查詢未入帳保管人 or 入帳後的新保管人是自己的移動單  
			if ("1".equals(this.getQ_userRole())) {
				sql.append(" and (")
				   .append(" (isnull(a.verify, 'N') ='Y' and b.newkeeper = ").append(Common.sqlChar(this.getQ_keeperno())).append(")")
				   .append(" or (isnull(a.verify, 'N')  = 'N' and b.oldkeeper = ").append(Common.sqlChar(this.getQ_keeperno())).append(")")
				   .append(" or (b.caseno is null)") // 未新增過明細情況下 就不鎖保管人了  因為現行設計沒辦法鎖
				   .append(" or (a.editid = ").append(Common.sqlChar(this.getUserID())).append(")") // 1185 增加能查到單的異動人員是自己的移動單
				   .append(")");
			} else if ("2".equals(this.getQ_userRole())) {
				sql.append(" and (")
				   .append(" (isnull(a.verify, 'N') ='Y' and b.oldkeepunit = ").append(Common.sqlChar(this.getQ_unitID())).append(")")
				   .append(" or (isnull(a.verify, 'N') = 'N' and b.newkeepunit = ").append(Common.sqlChar(this.getQ_unitID())).append(")")
				   .append(" or (b.caseno is null)")// 未新增過明細情況下 就不鎖保管人了  因為現行設計沒辦法鎖
				   .append(" or (a.editid = ").append(Common.sqlChar(this.getUserID())).append(")") // 1185 增加能查到單的異動人員是自己的移動單
				   .append(")");
			}
			
			sql.append(") a order by a.ownership, a.writedate desc, proofYear desc, a.proofno desc");
			

			ResultSet rs = db.querySQL(sql.toString(),true);
			UNTCH_Tools ut = new UNTCH_Tools();
			processCurrentPageAttribute(rs);			
			if (getTotalRecord() > 0) {
				int count = getRecordStart();
				int end = getRecordEnd();
				do {
					if (count > end)
						break;
				String rowArray[]=new String[8];
				rowArray[0]=rs.getString("enterOrg"); 
				rowArray[1]=rs.getString("ownership");
				rowArray[2]=rs.getString("ownershipName");
				rowArray[3]=rs.getString("proofdata");
				rowArray[4]=rs.getString("caseNo"); 
				rowArray[5]=ut._transToROC_Year(rs.getString("writeDate")); 
				rowArray[6]=ut._transToROC_Year(rs.getString("moveDate")); 
				rowArray[7]=rs.getString("verifyName"); 
				objList.add(rowArray);
				count++;
				} while (rs.next());
			}
			setStateQueryAllSuccess();
			
			rs.close();
			sql.setLength(0);
			sql = null;
			ut = null;
		} catch (Exception e) {
			e.printStackTrace();
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
	//===========================================================
	//取得DB中單的入帳狀態
		protected String getDBVerify(){
			String verify = "";
			
			String sql = "select verify from UNTMP_MOVEPROOF "
									+ "where 1=1 "
									+ "and enterorg= '" +this.getEnterOrg() + "' "
									+ "and caseno= '" +this.getCaseNo() + "' "
									+ ";";
			Database db = null;
			ResultSet rs = null;
			try{
				db = new Database();
				rs = db.querySQL(sql);
				while(rs.next()){
					verify = checkGet(rs.getString("verify"));
				}
			}catch(Exception e){
				e.printStackTrace();
			}finally{
				db.closeAll();
			}
			return verify;
		}
	
		protected String updateTable(){
			StringBuilder sql = new StringBuilder();
			if(getReVerify().equals("Y")){	
				//#region 回復入帳     
				UNTCH_Tools ut =new UNTCH_Tools();
				sql.append("update UNTMP_MOVEPROOF set").append(
						" verify = " + Common.sqlChar("N")).append( 						
						" where 1=1 ").append(
						" and enterOrg = " + Common.sqlChar(enterOrg)).append(
						" and ownership = " + Common.sqlChar(ownership)).append(
						" and caseNo = " + Common.sqlChar(caseNo)).append(
						":;:");
				
				sql.append("update UNTMP_MOVEDETAIL set").append(
						" verify = " + Common.sqlChar("N")).append( 	 
						", newmovedate = ''").append(
						" where 1=1 ").append(
						" and enterOrg = " + Common.sqlChar(enterOrg)).append(
						" and ownership = " + Common.sqlChar(ownership)).append(
						" and caseNo = " + Common.sqlChar(caseNo)).append(
						":;:");
				
				StringBuilder qSql = new StringBuilder();
				qSql.append("select").append(
							" differenceKind, propertyno, serialno, propertyname1, ").append(
							" oldKeepUnit, oldKeeper, oldUseUnit, oldUserNo,").append(
							" oldUserNote, oldPlace1, oldPlace,").append(
							" oldbuildfeecb, olddeprunitcb, olddeprpark, olddeprunit, olddeprunit1,").append(
							" olddepraccounts, oldmovedate, updatedeprcb").append(
							" from UNTMP_MOVEDETAIL").append(
							" where 1=1 ").append(
							" and enterOrg = ").append(Common.sqlChar(enterOrg)).append(
							" and ownership = ").append(Common.sqlChar(ownership)).append(
							" and caseNo = ").append(Common.sqlChar(caseNo));
				
				Database db = null;
				ResultSet rs = null;
				try{
					db = new Database();
					rs = db.querySQL(qSql.toString());
					while(rs.next()){
						String updatePropertyName1 = checkPropertyName1(rs.getString("propertyNo"), rs.getString("propertyName1"));
						
						sql.append(checkPropertyNo(rs.getString("propertyNo"))).append(
									" keepUnit = ").append(Common.sqlChar(checkGet(rs.getString("oldKeepUnit")))).append(",").append(
									" keeper = ").append(Common.sqlChar(checkGet(rs.getString("oldKeeper")))).append(",").append(
									" useUnit = ").append(Common.sqlChar(checkGet(rs.getString("oldUseUnit")))).append(",").append(
									" userNo = ").append(Common.sqlChar(checkGet(rs.getString("oldUserNo")))).append(",").append(
									" userNote = ").append(Common.sqlChar(checkGet(rs.getString("oldUserNote")))).append(",").append(
									" place1 = ").append(Common.sqlChar(checkGet(rs.getString("oldPlace1")))).append(",").append(
									" place = ").append(Common.sqlChar(checkGet(rs.getString("oldPlace")))).append(",").append(
									" movedate = ").append(Common.sqlChar(checkGet(rs.getString("oldmovedate")))).append(",").append(
									" editID = ").append(Common.sqlChar(getEditID()) + ",").append(
									" editDate = ").append(Common.sqlChar(ut._transToCE_Year(getEditDate()))).append(",").append(
									" editTime = ").append(Common.sqlChar(getEditTime())).append(
									updatePropertyName1);
						if ("Y".equals(Common.get(rs.getString("updatedeprcb")))) {
							sql.append(checkPropertyNo_forDeprMethod(rs.getString("propertyno"), rs, false));
						}
						sql.append(" where 1=1 ").append(
									" and enterOrg = ").append(Common.sqlChar(enterOrg)).append(
									" and ownership = ").append(Common.sqlChar(ownership)).append(
									" and differenceKind = ").append(Common.sqlChar(checkGet(rs.getString("differenceKind")))).append(
									" and propertyNo = ").append(Common.sqlChar(checkGet(rs.getString("propertyNo")))).append(
									" and serialNo = ").append(Common.sqlChar(checkGet(rs.getString("serialNo")))).append(
									":;:");
						
					}
					rs.close();
					
				}catch(Exception e){
					e.printStackTrace();
				}finally{
					db.closeAll();
				}
				//#end 
			}else if("Y".equals(verify) && "N".equals(this.getDBVerify())){
				//#region 若入帳無法 修改按鈕是鎖定, 所以此處應該僅for 入帳   
				UNTCH_Tools ut =new UNTCH_Tools();
				sql.append("update UNTMP_MOVEPROOF set").append(
						" verify = ").append(Common.sqlChar(verify)).append(                
						" where 1=1 ").append(
						" and enterOrg = ").append(Common.sqlChar(enterOrg)).append(
						" and ownership = ").append(Common.sqlChar(ownership)).append(
						" and caseNo = ").append(Common.sqlChar(caseNo)).append(
						":;:");
				
				sql.append("update UNTMP_MOVEDETAIL set").append(
						" verify = ").append(Common.sqlChar(verify)).append(
						", newmovedate = ").append(Common.sqlChar(ut._transToCE_Year(getMoveDate()))).append( 
						" where 1=1 ").append(
						" and enterOrg = ").append(Common.sqlChar(enterOrg)).append(
						" and ownership = ").append(Common.sqlChar(ownership)).append(
						" and caseNo = ").append(Common.sqlChar(caseNo)).append(
						":;:");
				
				String qSql = "select" +
							" differenceKind, propertyno, serialno, propertyName1," +
							" newKeepUnit, newKeeper, newUseUnit, newUserNo," +
							" newUserNote, newPlace1, newPlace," +
							" newbuildfeecb, newdeprunitcb, newdeprpark, newdeprunit, newdeprunit1," +
							" newdepraccounts, newmovedate, updatedeprcb" +
							" from UNTMP_MOVEDETAIL" +
							" where 1=1 " + 
							" and enterOrg = " + Common.sqlChar(enterOrg) +
							" and ownership = " + Common.sqlChar(ownership) +
							" and caseNo = " + Common.sqlChar(caseNo);
				
				Database db = null;
				ResultSet rs = null;
				try{
					db = new Database();
					rs = db.querySQL(qSql);
					while(rs.next()){
						String updatePropertyName1 = checkPropertyName1(rs.getString("propertyNo"), rs.getString("propertyName1"));
						
						sql.append(checkPropertyNo(rs.getString("propertyNo"))).append(
								" keepUnit = ").append(Common.sqlChar(checkGet(rs.getString("newKeepUnit")))).append(",").append(
								" keeper = ").append(Common.sqlChar(checkGet(rs.getString("newKeeper")))).append(",").append(
								" useUnit = ").append(Common.sqlChar(checkGet(rs.getString("newUseUnit")))).append(",").append(
								" userNo = ").append(Common.sqlChar(checkGet(rs.getString("newUserNo")))).append(",").append(
								" userNote = ").append(Common.sqlChar(checkGet(rs.getString("newUserNote")))).append(",").append(
								" place1 = ").append(Common.sqlChar(checkGet(rs.getString("newPlace1")))).append(",").append(
								" place = ").append(Common.sqlChar(checkGet(rs.getString("newPlace")))).append(",").append(
								" movedate = ").append(Common.sqlChar(ut._transToCE_Year(getMoveDate()))).append(",").append(
								" editID = ").append(Common.sqlChar(getEditID())).append(",").append(
								" editDate = ").append(Common.sqlChar(ut._transToCE_Year(getEditDate()))).append(",").append(
								" editTime = ").append(Common.sqlChar(getEditTime())).append(
								updatePropertyName1);
						if ("Y".equals(Common.get(rs.getString("updatedeprcb")))) {
							sql.append(checkPropertyNo_forDeprMethod(rs.getString("propertyno"), rs, true));
						}
						sql.append(" where 1=1 ").append(
								" and enterOrg = ").append(Common.sqlChar(enterOrg)).append(
								" and ownership = ").append(Common.sqlChar(ownership)).append(
								" and differenceKind = ").append(Common.sqlChar(checkGet(rs.getString("differenceKind")))).append(
								" and propertyNo = ").append(Common.sqlChar(checkGet(rs.getString("propertyNo")))).append(
								" and serialNo = ").append(Common.sqlChar(checkGet(rs.getString("serialNo")))).append(
								":;:");
//						System.out.println("sql = " + sql);
					}
					rs.close();
					
				}catch(Exception e){
					e.printStackTrace();
				}finally{
					db.closeAll();
				}
				//#end 
			}
			return sql.toString();
		}
	
			private String checkPropertyNo(String propertyNo){
				String result = "";
				String checkPropertyNo1 = propertyNo.substring(0,1);
				String checkPropertyNo3 = propertyNo.substring(0,3);
				
				if("111".equals(checkPropertyNo3)){				
					result = "update UNTRF_ATTACHMENT set";
				}else if("1".equals(checkPropertyNo1)){
					result = "update UNTLA_LAND set";
				}else if("2".equals(checkPropertyNo1)){
					result = "update UNTBU_BUILDING set";
				}else if("3".equals(checkPropertyNo1) || "4".equals(checkPropertyNo1) || "5".equals(checkPropertyNo1)){
					result = "update UNTMP_MOVABLEDETAIL set";
				}else if("8".equals(checkPropertyNo1)){
					result = "update UNTRT_ADDPROOF set";
				}else if("9".equals(checkPropertyNo1)){
					result = "update UNTVP_ADDPROOF set";
				}
				return result;
			}
			
			private String checkPropertyName1(String propertyNo, String propertyName1){
				String result = "";
				String checkPropertyNo1 = propertyNo.substring(0,1);
				
				if("3".equals(checkPropertyNo1) || "4".equals(checkPropertyNo1) || "5".equals(checkPropertyNo1)){
					result = " ,propertyname1 = " + Common.sqlChar(propertyName1);
				}
				return result;
			}
			
			private String checkPropertyNo_forDeprMethod(String propertyNo, ResultSet rs, boolean isVerify){
				StringBuilder result = new StringBuilder();
				String checkPropertyNo1 = propertyNo.substring(0,1);
				String checkPropertyNo3 = propertyNo.substring(0,3);
				
				if("111".equals(checkPropertyNo3) || 
				   "2".equals(checkPropertyNo1) || "3".equals(checkPropertyNo1) || "4".equals(checkPropertyNo1) || 
				   "5".equals(checkPropertyNo1) || "8".equals(checkPropertyNo1)) {				
					
					try{
						if(isVerify){
							result.append(",").append(
										" buildfeecb = ").append(Common.sqlChar(checkGet(rs.getString("newbuildfeecb")))).append(",").append(
										" deprunitcb = ").append(Common.sqlChar(checkGet(rs.getString("newdeprunitcb")))).append(",").append(
										" deprpark = ").append(Common.sqlChar(checkGet(rs.getString("newdeprpark")))).append(",").append(
										" deprunit = ").append(Common.sqlChar(checkGet(rs.getString("newdeprunit")))).append(",").append(
										" deprunit1 = ").append(Common.sqlChar(checkGet(rs.getString("newdeprunit1")))).append(",").append(
										" depraccounts = ").append(Common.sqlChar(checkGet(rs.getString("newdepraccounts"))));
						}else{
							result.append(",").append(
										" buildfeecb = ").append(Common.sqlChar(checkGet(rs.getString("oldbuildfeecb")))).append(",").append(
										" deprunitcb = ").append(Common.sqlChar(checkGet(rs.getString("olddeprunitcb")))).append(",").append(
										" deprpark = ").append(Common.sqlChar(checkGet(rs.getString("olddeprpark")))).append(",").append(
										" deprunit = ").append(Common.sqlChar(checkGet(rs.getString("olddeprunit")))).append(",").append(
										" deprunit1 = ").append(Common.sqlChar(checkGet(rs.getString("olddeprunit1")))).append(",").append(
										" depraccounts = ").append(Common.sqlChar(checkGet(rs.getString("olddepraccounts"))));
						}
					}catch(Exception e){
						
					}
				}
				return result.toString();
			}
	//===========================================================
	
	//檢查此電腦單號在指定的table中是否有資料
		public boolean checkDataCountIsZero(String enterOrg, String caseNo){
			boolean result = false;
			
			String sql = " select count(*) as count from UNTMP_MOVEDETAIL" +
						" where 1=1" +
						" and enterorg = '" + enterOrg + "'" +
						" and caseNo = '" + caseNo + "'";
			
			DBServerTools dbt = new DBServerTools();
			dbt._setDatabase(new Database());
			String checkDataCount = dbt._execSQL_asString(sql);
			
			if("0".equals(checkDataCount)){
				result = true;
			}

			return result;
		}
	
	//檢查update時，proofyear是否有改變
	private boolean checkProofyearChanged(String enterorg, String caseno, String newproofyear){
		boolean isChanged = false;
		String oldproofyear ="";
		ResultSet rs;
		Database db = new Database();
		try{
			String sql = "select proofyear from UNTMP_MOVEPROOF where enterorg=" + Common.sqlChar(enterorg) +
									" and caseno=" + Common.sqlChar(caseno);
			rs = db.querySQL(sql);
			if(rs.next()){
				oldproofyear = rs.getString(1);
				if(!newproofyear.equals(oldproofyear)){
					isChanged =true;
				}
			}else{
				System.out.println("查無此單!");
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			db.closeAll();
		}
		return isChanged;
	}
		
	private Map getPKMap(){
		Map map = new HashMap();
		
		map.put("enterOrg", getEnterOrg());
		map.put("ownership", getOwnership());
		map.put("caseNo", getCaseNo());
		
		return map;
	}
	
	private Map getRecordMap(){
		Map map = new HashMap();
		UNTCH_Tools ut = new UNTCH_Tools();
		
		map.put("EnterOrg", getEnterOrg());
		map.put("CaseNo", getCaseNo());
		map.put("Ownership", getOwnership());
		map.put("CaseName", getCaseName());
		map.put("WriteDate", ut._transToCE_Year(getWriteDate()));
		map.put("WriteUnit", getWriteUnit());
		map.put("ProofYear", getProofYear());
		map.put("ProofDoc", getProofDoc());
		
		//問題單1290補充: update時更改編號-年，重新取得編號-號
		String proofno = getProofNo();
		if(this.checkProofyearChanged(getEnterOrg(), getCaseNo(), getProofYear())){
			proofno = MaxClosingYM.getMaxProofNo("UNTMP_MOVEPROOF",enterOrg,ownership,this.getProofYear());
		}
		map.put("ProofNo", proofno);
		map.put("MoveDate", ut._transToCE_Year(getMoveDate()));
		map.put("Verify", getVerify());
		map.put("Notes", getNotes());
		map.put("EditID", getEditID());
		map.put("EditDate", ut._transToCE_Year(getEditDate()));
		map.put("EditTime", getEditTime());
		
		return map;
	}
	
	private String enterOrg;
	private String enterOrgName;
	private String caseNo;
	private String ownership;
	private String caseName;
	private String writeDate;
	private String writeUnit;
	private String proofYear;
	private String proofDoc;
	private String proofNo;
	private String moveDate;
	private String verify;
	private String notes;
	private String editID;
	private String editDate;
	private String editTime;
	public String getEnterOrg() {return checkGet(enterOrg);}
	public void setEnterOrg(String enterOrg) {this.enterOrg = checkSet(enterOrg);}
	public String getEnterOrgName() {return checkGet(enterOrgName);}
	public void setEnterOrgName(String enterOrgName) {this.enterOrgName = checkSet(enterOrgName);}
	public String getCaseNo() {return checkGet(caseNo);}
	public void setCaseNo(String caseNo) {this.caseNo = checkSet(caseNo);}
	public String getOwnership() {return checkGet(ownership);}
	public void setOwnership(String ownership) {this.ownership = checkSet(ownership);}
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
	public String getMoveDate() {return checkGet(moveDate);}
	public void setMoveDate(String moveDate) {this.moveDate = checkSet(moveDate);}
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
	
	private String reVerify;
	public String getReVerify() {return checkGet(reVerify);}
	public void setReVerify(String reVerify) {this.reVerify = checkSet(reVerify);}

	private String moveDateTemp;
	public String getMoveDateTemp() {return checkGet(moveDateTemp);}
	public void setMoveDateTemp(String moveDateTemp) {this.moveDateTemp = checkSet(moveDateTemp);}
	
	private String q_userRole, q_keeperno, q_unitID;
	public String getQ_userRole() { return checkGet(q_userRole); }
	public void setQ_userRole(String q_userRole) { this.q_userRole = checkSet(q_userRole); }
	public String getQ_keeperno() { return checkGet(q_keeperno); }
	public void setQ_keeperno(String q_keeperno) { this.q_keeperno = checkSet(q_keeperno); }
	public String getQ_unitID() { return checkGet(q_unitID); }
	public void setQ_unitID(String q_unitID) { this.q_unitID = checkSet(q_unitID); }
	
	/** 切換tab至 UTCH002F02時 紀錄當前的頁數 **/
	private String UNTCH002F01_currentPage;
	public String getUNTCH002F01_currentPage() { return checkGet(UNTCH002F01_currentPage); }
	public void setUNTCH002F01_currentPage(String uNTCH002F01_currentPage) { UNTCH002F01_currentPage = checkSet(uNTCH002F01_currentPage); }
	
	protected String getNewCaseNo(String table){
		String result = "";
		String sql="select case when max(caseno) is null then '' else (max(caseno) + 1) end as caseno from " + table +
			" where enterorg = " + Common.sqlChar(this.getEnterOrg()) +
			" and caseno like " + Common.sqlChar(Datetime.getYYY() + "%") +
			"";
		DBServerTools dbt = new DBServerTools();
		dbt._setDatabase(new Database());
		result = dbt._execSQL_asString(sql);

		if("".equals(Common.get(result))){
			result = Datetime.getYYY() + "0000001";
		}else{
			result = Datetime.getYYY() + Common.formatFrontZero(result.substring(3),7);
		}			
		return result;
	}
	
}