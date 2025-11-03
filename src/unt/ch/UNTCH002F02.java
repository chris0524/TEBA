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
import util.View;
import TDlib_Simple.com.src.DBServerTools;
import TDlib_Simple.com.src.SQLCreator;

public class UNTCH002F02 extends UNTCH002Q{

	protected String[][] getInsertCheckSQL(){
		String[][] checkSQLArray = new String[3][4];
	 	checkSQLArray[0][0]=" select count(*) as checkResult from UNTMP_MoveDetail where 1=1 " + 
			" and enterOrg = " + Common.sqlChar(enterOrg) + 
			" and ownership = " + Common.sqlChar(ownership) + 
			" and differenceKind = " + Common.sqlChar(differenceKind) + 
			" and propertyNo = " + Common.sqlChar(propertyNo) + 
			" and serialNo = " + Common.sqlChar(serialNo) + 
			" and verify = 'N' " + 
			"";
	 	//System.out.println(checkSQLArray[0][0]);
		checkSQLArray[0][1]=">";
		checkSQLArray[0][2]="0";
		checkSQLArray[0][3]="保管使用異動作業存在未審核的資料，請重新輸入！！";
		
		//檢查同一筆「入帳機關+權屬+財產編號+財產分號」是否減少作業已存在未審核的資料
		checkSQLArray[1][0]=" select count(*) as checkResult from UNTMP_ReduceDetail where 1=1 " + 
			" and enterOrg = " + Common.sqlChar(enterOrg) + 
			" and ownership = " + Common.sqlChar(ownership) + 
			" and differencekind = " + Common.sqlChar(differenceKind) +
			" and propertyNo = " + Common.sqlChar(propertyNo) + 
			" and serialNo = " + Common.sqlChar(serialNo) +
			" and verify = 'N' " + 
			"";
		checkSQLArray[1][1]=">";
		checkSQLArray[1][2]="0";
		checkSQLArray[1][3]="財產減少作業存在未審核的資料，請重新輸入！";

		//檢查同一筆「入帳機關+權屬+財產編號+財產分號」是否增減值作業已存在未審核的資料
		checkSQLArray[2][0]=" select count(*) as checkResult from UNTMP_AdjustDetail where 1=1 " + 
			" and enterOrg = " + Common.sqlChar(enterOrg) + 
			" and ownership = " + Common.sqlChar(ownership) + 
			" and differencekind = " + Common.sqlChar(differenceKind) +
			" and propertyNo = " + Common.sqlChar(propertyNo) + 
			" and serialNo = " + Common.sqlChar(serialNo) +
			" and verify = 'N' " + 
			"";
		checkSQLArray[2][1]=">";
		checkSQLArray[2][2]="0";
		checkSQLArray[2][3]="財產增減值作業存在未審核的資料，請重新輸入！";
		
		return checkSQLArray;
	}
	
	protected String[] getInsertSQL(){
		obtainNewCaseSeriallNo();
		
		calUseYearAndUseMonth();
		
		obtainSignNo();
		
		String[] execSQLArray = new String[1];
		execSQLArray[0] = new SQLCreator()._obtainSQLforInsert("UNTMP_MOVEDETAIL", getPKMap(), getRecordMap());
		return execSQLArray;
	}
	
	protected String[] getUpdateSQL(){
		
		obtainSignNo();
		
		String[] execSQLArray = new String[1];
		execSQLArray[0] = new SQLCreator()._obtainSQLforUpdate("UNTMP_MOVEDETAIL", getPKMap(), getRecordMap());
		return execSQLArray;
	}
	
	protected String[] getDeleteSQL(){
		String[] execSQLArray = new String[1];
		execSQLArray[0] = new SQLCreator()._obtainSQLforDelete("UNTMP_MOVEDETAIL", getPKMap(), getRecordMap());
		return execSQLArray;
	}
	
	public UNTCH002F02  queryOne() throws Exception{
		Database db = new Database();
		UNTCH002F02 obj = this;
		try {
			String sql=" select a.*, "+
						" (select placename from SYSCA_PLACE z where z.enterorg = a.enterorg and z.placeno = a.oldplace1) as oldplace1name," +
						" (select placename from SYSCA_PLACE z where z.enterorg = a.enterorg and z.placeno = a.newplace1) as newplace1name," +
						" (select organsname from SYSCA_ORGAN z where z.organid = a.enterorg) as EnterOrgName," +
						" (select material from SYSPK_PROPERTYCODE z where z.propertyno = a.propertyno and z.enterorg = '000000000A') as material," +
						" (select propertyunit from SYSPK_PROPERTYCODE z where z.propertyno = a.propertyno and z.enterorg = '000000000A') as propertyunit," +
						" (select limityear from SYSPK_PROPERTYCODE z where z.propertyno = a.propertyno and z.enterorg = '000000000A') as limityear" +
						" from UNTMP_MOVEDETAIL a where 1=1 " +
						" and a.enterOrg = " + Common.sqlChar(enterOrg) +
						" and a.ownership = " + Common.sqlChar(ownership) +
						" and a.caseNo = " + Common.sqlChar(caseNo) +
						" and a.differenceKind = " + Common.sqlChar(differenceKind) +
						" and a.propertyNo = " + Common.sqlChar(propertyNo) +
						" and a.serialNo = " + Common.sqlChar(serialNo) +
						"";
			
			ResultSet rs = db.querySQL(sql);
			UNTCH_Tools ut = new UNTCH_Tools();
			if (rs.next()){
				obj.setEnterOrg(rs.getString("EnterOrg"));
				obj.setEnterOrgName(rs.getString("EnterOrgName"));
				obj.setCaseNo(rs.getString("CaseNo"));
				obj.setOwnership(rs.getString("Ownership"));
				obj.setDifferenceKind(rs.getString("DifferenceKind"));
				obj.setCaseSerialNo(rs.getString("CaseSerialNo"));
				obj.setPropertyNo(rs.getString("PropertyNo"));
				obj.setLotNo(rs.getString("LotNo"));
				obj.setSerialNo(rs.getString("SerialNo"));
				obj.setPropertyName1(rs.getString("PropertyName1"));
				obj.setOtherPropertyUnit(rs.getString("OtherPropertyUnit"));
				obj.setOtherMaterial(rs.getString("OtherMaterial"));
				obj.setOtherLimitYear(rs.getString("OtherLimitYear"));
				obj.setPropertyUnit(rs.getString("propertyUnit"));
				obj.setMaterial(rs.getString("material"));
				obj.setLimitYear(rs.getString("limitYear"));
				obj.setBuyDate(ut._transToROC_Year(rs.getString("BuyDate")));
				obj.setVerify(rs.getString("Verify"));
				obj.setPropertyKind(rs.getString("PropertyKind"));
				obj.setFundType(rs.getString("FundType"));
				obj.setValuable(rs.getString("Valuable"));
				obj.setBookAmount(rs.getString("BookAmount"));
				obj.setBookUnit(rs.getString("BookUnit"));
				obj.setBookValue(rs.getString("BookValue"));
				obj.setNetUnit(rs.getString("NetUnit"));
				obj.setNetValue(rs.getString("NetValue"));
				obj.setNameplate(rs.getString("Nameplate"));
				obj.setSpecification(rs.getString("Specification"));
				obj.setSourceDate(ut._transToROC_Year(rs.getString("SourceDate")));
				obj.setOldMoveDate(ut._transToROC_Year(rs.getString("OldMoveDate")));
				obj.setOldKeepUnit(rs.getString("OldKeepUnit"));
				obj.setOldKeeper(rs.getString("OldKeeper"));
				obj.setOldUseUnit(rs.getString("OldUseUnit"));
				obj.setOldUserNo(rs.getString("OldUserNo"));
				obj.setOldUserNote(rs.getString("OldUserNote"));
				obj.setOldPlace1(rs.getString("OldPlace1"));
				obj.setOldPlace1Name(rs.getString("OldPlace1name"));
				obj.setOldPlace(rs.getString("OldPlace"));
				obj.setNewMoveDate(ut._transToROC_Year(rs.getString("NewMoveDate")));
				obj.setNewKeepUnit(rs.getString("NewKeepUnit"));
				obj.setNewKeeper(rs.getString("NewKeeper"));
				obj.setNewUseUnit(rs.getString("NewUseUnit"));
				obj.setNewUserNo(rs.getString("NewUserNo"));
				obj.setNewUserNote(rs.getString("NewUserNote"));
				obj.setNewPlace1(rs.getString("NewPlace1"));
				obj.setNewPlace1Name(rs.getString("NewPlace1Name"));
				obj.setNewPlace(rs.getString("NewPlace"));
				obj.setUseYear(rs.getString("UseYear"));
				obj.setUseMonth(rs.getString("UseMonth"));
				obj.setOldDeprMethod(rs.getString("OldDeprMethod"));
				obj.setOldBuildFeeCB(rs.getString("OldBuildFeeCB"));
				obj.setOldDeprUnitCB(rs.getString("OldDeprUnitCB"));
				obj.setOldDeprPark(rs.getString("OldDeprPark"));
				obj.setOldDeprUnit(rs.getString("OldDeprUnit"));
				obj.setOldDeprUnit1(rs.getString("OldDeprUnit1"));
				obj.setOldDeprAccounts(rs.getString("OldDeprAccounts"));
				obj.setOldScrapValue(rs.getString("OldScrapValue"));
				obj.setOldDeprAmount(rs.getString("OldDeprAmount"));
				obj.setOldAccumDepr(rs.getString("OldAccumDepr"));
				obj.setOldApportionMonth(rs.getString("OldApportionMonth"));
				obj.setOldMonthDepr(rs.getString("OldMonthDepr"));
				obj.setOldMonthDepr1(rs.getString("OldMonthDepr1"));
				obj.setOldApportionEndYM(ut._transToROC_Year(rs.getString("OldApportionEndYM")));
				obj.setNewDeprMethod(rs.getString("NewDeprMethod"));
				obj.setNewBuildFeeCB(rs.getString("NewBuildFeeCB"));
				obj.setNewDeprUnitCB(rs.getString("NewDeprUnitCB"));
				obj.setNewDeprPark(rs.getString("NewDeprPark"));
				obj.setNewDeprUnit(rs.getString("NewDeprUnit"));
				obj.setNewDeprUnit1(rs.getString("NewDeprUnit1"));
				obj.setNewDeprAccounts(rs.getString("NewDeprAccounts"));
				obj.setNewScrapValue(rs.getString("NewScrapValue"));
				obj.setNewDeprAmount(rs.getString("NewDeprAmount"));
				obj.setNewAccumDepr(rs.getString("NewAccumDepr"));
				obj.setNewApportionMonth(rs.getString("NewApportionMonth"));
				obj.setNewMonthDepr(rs.getString("NewMonthDepr"));
				obj.setNewMonthDepr1(rs.getString("NewMonthDepr1"));
				obj.setNewApportionEndYM(ut._transToROC_Year(rs.getString("NewApportionEndYM")));
				obj.setSignNoLA(rs.getString("SignNoLA"));
				
				if(obj.getSignNoLA().length() == 15){
					obj.setLaSignNo1(obj.getSignNoLA().substring(0,1) + "000000");
					obj.setLaSignNo2(obj.getSignNoLA().substring(0,3) + "0000");
					obj.setLaSignNo3(obj.getSignNoLA().substring(0,7));
					obj.setLaSignNo4(obj.getSignNoLA().substring(7,11));
					obj.setLaSignNo5(obj.getSignNoLA().substring(11));
				}
				obj.setAreaLA(rs.getString("AreaLA"));
				obj.setHoldNumeLA(rs.getString("HoldNumeLA"));
				obj.setHoldDenoLA(rs.getString("HoldDenoLA"));
				obj.setHoldAreaLA(rs.getString("HoldAreaLA"));
				obj.setSignNoBU(rs.getString("SignNoBU"));
				
				if(obj.getSignNoBU().length() == 15){
					obj.setBuSignNo1(obj.getSignNoBU().substring(0,1) + "000000");
					obj.setBuSignNo2(obj.getSignNoBU().substring(0,3) + "0000");
					obj.setBuSignNo3(obj.getSignNoBU().substring(0,7));
					obj.setBuSignNo4(obj.getSignNoBU().substring(7,12));
					obj.setBuSignNo5(obj.getSignNoBU().substring(12));
				}
				obj.setCAreaBU(rs.getString("CAreaBU"));
				obj.setSAreaBU(rs.getString("SAreaBU"));
				obj.setAreaBU(rs.getString("AreaBU"));
				obj.setHoldNumeBU(rs.getString("HoldNumeBU"));
				obj.setHoldDenoBU(rs.getString("HoldDenoBU"));
				obj.setHoldAreaBU(rs.getString("HoldAreaBU"));
				obj.setMeasure(rs.getString("Measure"));
				obj.setOldPropertyNo(rs.getString("OldPropertyNo"));
				obj.setOldSerialNo(rs.getString("OldSerialNo"));
				obj.setNotes(rs.getString("Notes"));
				obj.setEditID(rs.getString("EditID"));
				obj.setEditDate(ut._transToROC_Year(rs.getString("EditDate")));
				obj.setEditTime(rs.getString("EditTime"));
				obj.setPropertyNoName(ut._getPropertyNoName(rs.getString("enterOrg"), rs.getString("PropertyNo")));
				obj.setUpdatedeprcb(rs.getString("Updatedeprcb"));
			}
			setStateQueryOneSuccess();
			
			queryProofNo();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.closeAll();
		}
		return obj;
	}
		
		private void queryProofNo(){
			String sql = "select proofno from UNTMP_MOVEPROOF where 1=1" +
							" and enterorg = " + Common.sqlChar(this.getEnterOrg()) +
							" and ownership = " + Common.sqlChar(this.getOwnership()) +
							//" and differencekind = " + Common.sqlChar(this.getDifferenceKind()) +
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
	
	public ArrayList queryAll() throws Exception{
		Database db = new Database();
		ArrayList objList=new ArrayList();
		
		try {
			StringBuilder sql = new StringBuilder();
			sql.append(" select a.enterOrg, a.ownership, a.caseno, a.differenceKind, a.propertyno, a.serialno,").append(
							" (select unitname from UNTMP_KEEPUNIT z where z.enterorg = a.enterorg and z.unitno = a.newKeepUnit) as newKeepUnit,").append(
							" (select keepername from UNTMP_KEEPER z where z.enterorg = a.enterorg and z.keeperno = a.newKeeper) as newKeeper,").append(
							" (select placename from SYSCA_PLACE z where z.enterorg = a.enterorg and z.placeno = a.newplace1) as newplace1,").append(
							" a.newMoveDate, a.caseserialno").append(
							" from UNTMP_MoveDetail a").append(
							" left join UNTMP_MOVEPROOF b on a.enterorg=b.enterorg and a.caseno=b.caseno").append(
							" where 1=1 ");
							
			if("detail".equals(getQuerySelect())){
				sql.append(" and a.enterorg = ").append(Common.sqlChar(this.getQ_enterOrg())) ;
			}else{
				sql.append(" and a.enterorg = ").append(Common.sqlChar(this.getEnterOrg())).append(
						" and a.ownership = ").append(Common.sqlChar(this.getOwnership())).append(
						" and a.caseno = ").append(Common.sqlChar(this.getCaseNo()));
			}

			if (!"".equals(getQ_propertyNoS()))
				sql.append(" and a.propertyNo >= ").append(Common.sqlChar(getQ_propertyNoS())) ;
			if (!"".equals(getQ_propertyNoE()))
				sql.append(" and a.propertyNo <= ").append(Common.sqlChar(getQ_propertyNoE())) ;
			if (!"".equals(getQ_serialNoS()))
				sql.append(" and a.serialNo >= ").append(Common.sqlChar(getQ_serialNoS())) ;
			if (!"".equals(getQ_serialNoE()))
				sql.append(" and a.serialNo <= ").append(Common.sqlChar(getQ_serialNoE())) ;
			if (!"".equals(getQ_propertyKind()))
				sql.append(" and a.propertyKind = ").append(Common.sqlChar(getQ_propertyKind()));
			if (!"".equals(getQ_fundType()))
				sql.append(" and a.fundType = ").append(Common.sqlChar(getQ_fundType()));
			if (!"".equals(getQ_valuable()))
				sql.append(" and a.valuable = ").append(Common.sqlChar(getQ_valuable()));
			
			if (!"".equals(getQ_oldKeepUnit()))
				sql.append(" and a.oldKeepUnit = ").append(Common.sqlChar(getQ_oldKeepUnit())) ;
			if (!"".equals(getQ_oldUseUnit()))
				sql.append(" and a.oldUseUnit = ").append(Common.sqlChar(getQ_oldUseUnit())) ;
			if (!"".equals(getQ_oldKeeper()))
				sql.append(" and a.oldKeeper = ").append(Common.sqlChar(getQ_oldKeeper())) ;
			if (!"".equals(getQ_oldUserNo()))
				sql.append(" and a.oldUserNo = ").append(Common.sqlChar(getQ_oldUserNo())) ;
			if (!"".equals(getQ_oldUserNote()))
				sql.append(" and a.oldUserNote like ").append(Common.sqlChar("%"+getQ_oldUserNote()+"%")) ;
			if (!"".equals(getQ_oldPlace()))
				sql.append(" and a.oldPlace1 = ").append(Common.sqlChar(getQ_oldPlace())) ;
			if (!"".equals(getQ_oldPlaceNote()))
				sql.append(" and a.oldPlace like ").append(Common.sqlChar("%"+getQ_oldPlaceNote()+"%")) ;
			
			if (!"".equals(getQ_newKeepUnit()))
				sql.append(" and a.newKeepUnit = ").append(Common.sqlChar(getQ_newKeepUnit())) ;
			if (!"".equals(getQ_newUseUnit()))
				sql.append(" and a.newUseUnit = ").append(Common.sqlChar(getQ_newUseUnit())) ;
			if (!"".equals(getQ_newKeeper()))
				sql.append(" and a.newKeeper = ").append(Common.sqlChar(getQ_newKeeper())) ;
			if (!"".equals(getQ_newUserNo()))
				sql.append(" and a.newUserNo = ").append(Common.sqlChar(getQ_newUserNo())) ;
			if (!"".equals(getQ_newUserNote()))
				sql.append(" and a.newUserNote like ").append(Common.sqlChar("%"+getQ_newUserNote()+"%")) ;
			if (!"".equals(getQ_newPlace()))
				sql.append(" and a.newPlace1 = ").append(Common.sqlChar(getQ_newPlace())) ;
			if (!"".equals(getQ_newPlaceNote()))
				sql.append(" and a.newPlace = ").append(Common.sqlChar(getQ_newPlaceNote())) ;
			
			
			if (!"".equals(getQ_differenceKind()))
				sql.append(" and a.differenceKind = ").append(Common.sqlChar(getQ_differenceKind())) ;
			if (!"".equals(getQ_propertyName1()))
				sql.append(" and a.propertyName1 like ").append(Common.sqlChar("%"+getQ_propertyName1()+"%")) ;
			
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
				if (!"".equals(q_signLaNo)){
					sql.append(" and a.signnoLA like '").append(q_signLaNo).append("%'");
					
					if (!"".equals(getQ_signLaNo4S())){
						sql.append(" and substring(a.signnoLA,8,8) >= '").append(getQ_signLaNo4S()).append(getQ_signLaNo5S()).append("'");				
					}	
					if (!"".equals(getQ_signLaNo4E())){
						sql.append(" and substring(a.signnoLA,8,8) <= '").append(getQ_signLaNo4E()).append(getQ_signLaNo5E()).append("'");				
					}
					
					
				}else if (!"".equals(q_signBuNo)){
					sql.append(" and a.signnoBU like '").append(q_signBuNo).append("%'");
					
					if (!"".equals(getQ_signBuNo4S())){
						sql.append(" and substring(a.signnoBU,8,8) >= '").append(getQ_signBuNo4S()).append(getQ_signBuNo5S()).append("'");				
					}	
					if (!"".equals(getQ_signBuNo4E())){
						sql.append(" and substring(a.signnoBU,8,8) <= '").append(getQ_signBuNo4E()).append(getQ_signBuNo5E()).append("'");				
					}					
				}
				
			}

			// 706  保管人 僅能查詢未入帳保管人 or 入帳後的新保管人是自己的移動單
			if ("1".equals(this.getQ_userRole())) {
				sql.append(" and (")
				.append(" (isnull(a.verify,'N') = 'Y' and a.newkeeper = ").append(Common.sqlChar(this.getQ_keeperno())).append(")")
				.append(" or (isnull(a.verify,'N') = 'N' and a.oldkeeper = ").append(Common.sqlChar(this.getQ_keeperno())).append(")")
				.append(" or (b.editid = ").append(Common.sqlChar(this.getUserID())).append(")") // 1185 增加能查到異動人員是自己的移動單
				.append(")");
			} else if ("2".equals(this.getQ_userRole())) {
				sql.append(" and (")
				.append(" (isnull(a.verify,'N') = 'Y' and a.oldkeepunit = ").append(Common.sqlChar(this.getQ_unitID())).append(")")
				.append(" or (isnull(a.verify,'N') = 'N' and a.newkeepunit = ").append(Common.sqlChar(this.getQ_unitID())).append(")")
				.append(" or (b.editid = ").append(Common.sqlChar(this.getUserID())).append(")") // 1185 增加能查到異動人員是自己的移動單
				.append(")");
			}
			
//			if("Y".equals(getIsAdminManager())){
//				
//			}else{
//				sql.append(" and enterorg = '").append(this.getOrganID()).append("'";
//			}
			sql.append(" order by a.enterOrg, a.ownership, a.caseNo, a.caseSerialNo desc");
//			System.out.println("sql = " + sql);
			ResultSet rs = db.querySQL(sql.toString() ,true);
			UNTCH_Tools ut = new UNTCH_Tools();
			processCurrentPageAttribute(rs);			
			if (getTotalRecord() > 0) {
				int count = getRecordStart();
				int end = getRecordEnd();
				do {
					if (count > end)
						break;
				String rowArray[]=new String[12];
				rowArray[0]=rs.getString("enterOrg"); 
				rowArray[1]=rs.getString("ownership");
				rowArray[2]=rs.getString("caseNo"); 
				rowArray[3]=rs.getString("caseSerialNo");
				rowArray[4]=rs.getString("differenceKind");				
				rowArray[5]=rs.getString("propertyNo"); 
				rowArray[6]=ut._getPropertyNoName(rs.getString("enterOrg"), rs.getString("propertyNo"));
				rowArray[7]=rs.getString("serialNo");
				rowArray[8]=ut._transToROC_Year(rs.getString("newMoveDate")); 
				rowArray[9]=rs.getString("newKeepUnit"); 
				rowArray[10]=rs.getString("newKeeper");
				rowArray[11]=rs.getString("newPlace1");
				
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
	
	//===========================================================
		private void obtainNewCaseSeriallNo(){
			DataStructor ds = new DataStructor();
			ds.enterOrg = this.getEnterOrg();
			ds.ownership = this.getOwnership();
			ds.caseNo = this.getCaseNo();
			ds.differenceKind = this.getDifferenceKind();
			
			String[] tables = new String[]{
							"UNTMP_MOVEDETAIL"
							};
			
			this.setCaseSerialNo(_getNewCaseSerialNo(ds,tables));			
		}
			
			private String _getNewCaseSerialNo(DataStructor ds, String[] tables){
				String sql = "select case when max(caseSerialNo) is null then '1' else (max(caseSerialNo) + 1) end from " + tables[0] + 
								" where enterorg = " + Common.sqlChar(ds.enterOrg) +
//								" and ownership=" + Common.sqlChar(ds.ownership) +
								" and caseNo=" + Common.sqlChar(ds.caseNo) +
//								" and differenceKind=" + Common.sqlChar(ds.differenceKind) +
								"";						
				DBServerTools dbt = new DBServerTools();
				dbt._setDatabase(new Database());
				return dbt._execSQL_asString(sql);		
			}
		
	
		private void calUseYearAndUseMonth(){
			String check1 = this.getPropertyNo().substring(0,1);
			String check3 = this.getPropertyNo().substring(0,3);
						
			if(check3.equals("111") || check1.equals("2") || check1.equals("3") || check1.equals("4") || check1.equals("5")){				
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
		}
	
		private void obtainSignNo(){
			if("".equals(this.getLaSignNo3()) || "".equals(this.getLaSignNo4()) || "".equals(this.getLaSignNo5())){
			}else{
				this.setSignNoLA(this.getLaSignNo3() + this.getLaSignNo4() + this.getLaSignNo5());
			}
			
			if("".equals(this.getBuSignNo3()) || "".equals(this.getBuSignNo4()) || "".equals(this.getBuSignNo5())){
			}else{
				this.setSignNoBU(this.getBuSignNo3() + this.getBuSignNo4() + this.getBuSignNo5());
			}
		}
		
		
	private Map getPKMap(){
		Map map = new HashMap();
		
		map.put("enterOrg", getEnterOrg());
		map.put("ownership", getOwnership());
		map.put("caseNo", getCaseNo());
		map.put("differenceKind", getDifferenceKind());
		map.put("propertyNo", getPropertyNo());
		map.put("serialNo", getSerialNo());
		
		return map;
	}
	
	private Map getRecordMap(){
		Map map = new HashMap();
		UNTCH_Tools ut = new UNTCH_Tools();
		
		map.put("EnterOrg", getEnterOrg());
		map.put("CaseNo", getCaseNo());
		map.put("Ownership", getOwnership());
		map.put("DifferenceKind", getDifferenceKind());
		map.put("CaseSerialNo", getCaseSerialNo());
		map.put("PropertyNo", getPropertyNo());
		map.put("LotNo", getLotNo());
		map.put("SerialNo", getSerialNo());
		map.put("PropertyName1", getPropertyName1());
		map.put("OtherPropertyUnit", getOtherPropertyUnit());
		map.put("OtherMaterial", getOtherMaterial());
		map.put("OtherLimitYear", getOtherLimitYear());
		map.put("BuyDate", ut._transToCE_Year(getBuyDate()));
		map.put("Verify", getVerify());
		map.put("PropertyKind", getPropertyKind());
		map.put("FundType", getFundType());		
		map.put("Valuable", getValuable());
		map.put("BookAmount", getBookAmount());
		map.put("BookUnit", getBookUnit());
		map.put("BookValue", getBookValue());
		map.put("NetUnit", getNetUnit());
		map.put("NetValue", getNetValue());
		map.put("Nameplate", getNameplate());
		map.put("Specification", getSpecification());
		map.put("SourceDate", ut._transToCE_Year(getSourceDate()));
		map.put("OldMoveDate", ut._transToCE_Year(getOldMoveDate()));
		map.put("OldKeepUnit", getOldKeepUnit());
		map.put("OldKeeper", getOldKeeper());
		map.put("OldUseUnit", getOldUseUnit());
		map.put("OldUserNo", getOldUserNo());
		map.put("OldUserNote", getOldUserNote());
		map.put("OldPlace1", getOldPlace1());
		map.put("OldPlace", getOldPlace());
		map.put("NewMoveDate", ut._transToCE_Year(getNewMoveDate()));
		map.put("NewKeepUnit", getNewKeepUnit());
		map.put("NewKeeper", getNewKeeper());
		map.put("NewUseUnit", getNewUseUnit());
		map.put("NewUserNo", getNewUserNo());
		map.put("NewUserNote", getNewUserNote());
		map.put("NewPlace1", getNewPlace1());
		map.put("NewPlace", getNewPlace());
		map.put("UseYear", getUseYear());
		map.put("UseMonth", getUseMonth());
		map.put("OldDeprMethod", getOldDeprMethod());
		map.put("OldBuildFeeCB", ("Y".equals(getOldBuildFeeCB())?"Y":"N"));
		map.put("OldDeprUnitCB", ("Y".equals(getOldDeprUnitCB())?"Y":"N"));
		map.put("OldDeprPark", getOldDeprPark());
		map.put("OldDeprUnit", getOldDeprUnit());
		map.put("OldDeprUnit1", getOldDeprUnit1());
		map.put("OldDeprAccounts", getOldDeprAccounts());
		map.put("OldScrapValue", getOldScrapValue());
		map.put("OldDeprAmount", getOldDeprAmount());
		map.put("OldAccumDepr", getOldAccumDepr());
		map.put("OldApportionMonth", getOldApportionMonth());
		map.put("OldMonthDepr", getOldMonthDepr());
		map.put("OldMonthDepr1", getOldMonthDepr1());
		map.put("OldApportionEndYM", ut._transToCE_Year(getOldApportionEndYM()));
		map.put("NewDeprMethod", getNewDeprMethod());
		map.put("NewBuildFeeCB", ("Y".equals(getNewBuildFeeCB())?"Y":"N"));
		map.put("NewDeprUnitCB", ("Y".equals(getNewDeprUnitCB())?"Y":"N"));
		map.put("NewDeprPark", getNewDeprPark());
		map.put("NewDeprUnit", getNewDeprUnit());
		map.put("NewDeprUnit1", getNewDeprUnit1());
		map.put("NewDeprAccounts", getNewDeprAccounts());
		map.put("NewScrapValue", getNewScrapValue());
		map.put("NewDeprAmount", getNewDeprAmount());
		map.put("NewAccumDepr", getNewAccumDepr());
		map.put("NewApportionMonth", getNewApportionMonth());
		map.put("NewMonthDepr", getNewMonthDepr());
		map.put("NewMonthDepr1", getNewMonthDepr1());
		map.put("NewApportionEndYM", ut._transToCE_Year(getNewApportionEndYM()));
		map.put("SignNoLA", getSignNoLA());
		map.put("AreaLA", getAreaLA());
		map.put("HoldNumeLA", getHoldNumeLA());
		map.put("HoldDenoLA", getHoldDenoLA());
		map.put("HoldAreaLA", getHoldAreaLA());
		map.put("SignNoBU", getSignNoBU());
		map.put("CAreaBU", getCAreaBU());
		map.put("SAreaBU", getSAreaBU());
		map.put("AreaBU", getAreaBU());
		map.put("HoldNumeBU", getHoldNumeBU());
		map.put("HoldDenoBU", getHoldDenoBU());
		map.put("HoldAreaBU", getHoldAreaBU());
		map.put("Measure", getMeasure());
		map.put("OldPropertyNo", getOldPropertyNo());
		map.put("OldSerialNo", getOldSerialNo());
		map.put("Notes", getNotes());
		map.put("EditID", getEditID());
		map.put("EditDate", ut._transToCE_Year(getEditDate()));
		map.put("EditTime", getEditTime());
		map.put("Updatedeprcb", getUpdatedeprcb());
		
		return map;
	}
	
    //下拉選項
	public String _getDifferenceKindHTML(String className, String inputName, String inputValue, String codeID){
    	String result = "";
    	result = "<select class=\"" + className + "\" type=\"select\" name=\"" + inputName + "\" onchange=\"changeDifferenceKind();\">" +
					View.getOption("select codeID, codeName from SYSCA_Code where codeKindID='" + codeID + "' order by codeID", inputValue) +
    				"</select>";
    	
    	return result;
    }
	
	@Override
	public void insert() throws Exception {
		try {
			super.insert();
		} catch (Exception e) {
			this.setErrorMsg("新增失敗!" + e.getMessage());
		}
	}
	
	private String enterOrg;
	private String caseNo;
	private String ownership;
	private String differenceKind;
	private String caseSerialNo;
	private String propertyNo;
	private String lotNo;
	private String serialNo;
	private String propertyName1;
	private String otherPropertyUnit;
	private String otherMaterial;
	private String otherLimitYear;
	private String buyDate;
	private String verify;
	private String propertyKind;
	private String fundType;
	private String valuable;
	private String bookAmount;
	private String bookUnit;
	private String bookValue;
	private String netUnit;
	private String netValue;
	private String nameplate;
	private String specification;
	private String sourceDate;
	private String oldMoveDate;
	private String oldKeepUnit;
	private String oldKeeper;
	private String oldUseUnit;
	private String oldUserNo;
	private String oldUserNote;
	private String oldPlace1;
	private String oldPlace;
	private String newMoveDate;
	private String newKeepUnit;
	private String newKeeper;
	private String newUseUnit;
	private String newUserNo;
	private String newUserNote;
	private String newPlace1;
	private String newPlace;
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
	private String oldMonthDepr1;
	private String oldApportionEndYM;
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
	private String newMonthDepr1;
	private String newApportionEndYM;
	private String signNoLA;
	private String areaLA;
	private String holdNumeLA;
	private String holdDenoLA;
	private String holdAreaLA;
	private String signNoBU;
	private String CAreaBU;
	private String SAreaBU;
	private String areaBU;
	private String holdNumeBU;
	private String holdDenoBU;
	private String holdAreaBU;
	private String measure;
	private String oldPropertyNo;
	private String oldSerialNo;
	private String notes;
	private String editID;
	private String editDate;
	private String editTime;
	private String updatedeprcb;
	public String getEnterOrg() {return checkGet(enterOrg);}
	public void setEnterOrg(String enterOrg) {this.enterOrg = checkSet(enterOrg);}
	public String getCaseNo() {return checkGet(caseNo);}
	public void setCaseNo(String caseNo) {this.caseNo = checkSet(caseNo);}
	public String getOwnership() {return checkGet(ownership);}
	public void setOwnership(String ownership) {this.ownership = checkSet(ownership);}
	public String getDifferenceKind() {return checkGet(differenceKind);}
	public void setDifferenceKind(String differenceKind) {this.differenceKind = checkSet(differenceKind);}
	public String getCaseSerialNo() {return checkGet(caseSerialNo);}
	public void setCaseSerialNo(String caseSerialNo) {this.caseSerialNo = checkSet(caseSerialNo);}
	public String getPropertyNo() {return checkGet(propertyNo);}
	public void setPropertyNo(String propertyNo) {this.propertyNo = checkSet(propertyNo);}
	public String getLotNo() {return checkGet(lotNo);}
	public void setLotNo(String lotNo) {this.lotNo = checkSet(lotNo);}
	public String getSerialNo() {return checkGet(serialNo);}
	public void setSerialNo(String serialNo) {this.serialNo = checkSet(serialNo);}
	public String getPropertyName1() {return checkGet(propertyName1);}
	public void setPropertyName1(String propertyName1) {this.propertyName1 = checkSet(propertyName1);}
	public String getOtherPropertyUnit() {return checkGet(otherPropertyUnit);}
	public void setOtherPropertyUnit(String otherPropertyUnit) {this.otherPropertyUnit = checkSet(otherPropertyUnit);}
	public String getOtherMaterial() {return checkGet(otherMaterial);}
	public void setOtherMaterial(String otherMaterial) {this.otherMaterial = checkSet(otherMaterial);}
	public String getOtherLimitYear() {return checkGet(otherLimitYear);}
	public void setOtherLimitYear(String otherLimitYear) {this.otherLimitYear = checkSet(otherLimitYear);}
	public String getBuyDate() {return checkGet(buyDate);}
	public void setBuyDate(String buyDate) {this.buyDate = checkSet(buyDate);}
	public String getVerify() {return checkGet(verify);}
	public void setVerify(String verify) {this.verify = checkSet(verify);}
	public String getPropertyKind() {return checkGet(propertyKind);}
	public void setPropertyKind(String propertyKind) {this.propertyKind = checkSet(propertyKind);}
	public String getFundType() {return checkGet(fundType);}
	public void setFundType(String fundType) {this.fundType = checkSet(fundType);}
	public String getValuable() {return checkGet(valuable);}
	public void setValuable(String valuable) {this.valuable = checkSet(valuable);}
	public String getBookAmount() {return checkGet(bookAmount);}
	public void setBookAmount(String bookAmount) {this.bookAmount = checkSet(bookAmount);}
	public String getBookUnit() {return checkGet(bookUnit);}
	public void setBookUnit(String bookUnit) {this.bookUnit = checkSet(bookUnit);}
	public String getBookValue() {return checkGet(bookValue);}
	public void setBookValue(String bookValue) {this.bookValue = checkSet(bookValue);}
	public String getNetUnit() {return checkGet(netUnit);}
	public void setNetUnit(String netUnit) {this.netUnit = checkSet(netUnit);}
	public String getNetValue() {return checkGet(netValue);}
	public void setNetValue(String netValue) {this.netValue = checkSet(netValue);}
	public String getNameplate() {return checkGet(nameplate);}
	public void setNameplate(String nameplate) {this.nameplate = checkSet(nameplate);}
	public String getSpecification() {return checkGet(specification);}
	public void setSpecification(String specification) {this.specification = checkSet(specification);}
	public String getSourceDate() {return checkGet(sourceDate);}
	public void setSourceDate(String sourceDate) {this.sourceDate = checkSet(sourceDate);}
	public String getOldMoveDate() {return checkGet(oldMoveDate);}
	public void setOldMoveDate(String oldMoveDate) {this.oldMoveDate = checkSet(oldMoveDate);}
	public String getOldKeepUnit() {return checkGet(oldKeepUnit);}
	public void setOldKeepUnit(String oldKeepUnit) {this.oldKeepUnit = checkSet(oldKeepUnit);}
	public String getOldKeeper() {return checkGet(oldKeeper);}
	public void setOldKeeper(String oldKeeper) {this.oldKeeper = checkSet(oldKeeper);}
	public String getOldUseUnit() {return checkGet(oldUseUnit);}
	public void setOldUseUnit(String oldUseUnit) {this.oldUseUnit = checkSet(oldUseUnit);}
	public String getOldUserNo() {return checkGet(oldUserNo);}
	public void setOldUserNo(String oldUserNo) {this.oldUserNo = checkSet(oldUserNo);}
	public String getOldUserNote() {return checkGet(oldUserNote);}
	public void setOldUserNote(String oldUserNote) {this.oldUserNote = checkSet(oldUserNote);}
	public String getOldPlace1() {return checkGet(oldPlace1);}
	public void setOldPlace1(String oldPlace1) {this.oldPlace1 = checkSet(oldPlace1);}
	public String getOldPlace() {return checkGet(oldPlace);}
	public void setOldPlace(String oldPlace) {this.oldPlace = checkSet(oldPlace);}
	public String getNewMoveDate() {return checkGet(newMoveDate);}
	public void setNewMoveDate(String newMoveDate) {this.newMoveDate = checkSet(newMoveDate);}
	public String getNewKeepUnit() {return checkGet(newKeepUnit);}
	public void setNewKeepUnit(String newKeepUnit) {this.newKeepUnit = checkSet(newKeepUnit);}
	public String getNewKeeper() {return checkGet(newKeeper);}
	public void setNewKeeper(String newKeeper) {this.newKeeper = checkSet(newKeeper);}
	public String getNewUseUnit() {return checkGet(newUseUnit);}
	public void setNewUseUnit(String newUseUnit) {this.newUseUnit = checkSet(newUseUnit);}
	public String getNewUserNo() {return checkGet(newUserNo);}
	public void setNewUserNo(String newUserNo) {this.newUserNo = checkSet(newUserNo);}
	public String getNewUserNote() {return checkGet(newUserNote);}
	public void setNewUserNote(String newUserNote) {this.newUserNote = checkSet(newUserNote);}
	public String getNewPlace1() {return checkGet(newPlace1);}
	public void setNewPlace1(String newPlace1) {this.newPlace1 = checkSet(newPlace1);}
	public String getNewPlace() {return checkGet(newPlace);}
	public void setNewPlace(String newPlace) {this.newPlace = checkSet(newPlace);}
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
	public String getOldMonthDepr1() {return checkGet(oldMonthDepr1);}
	public void setOldMonthDepr1(String oldMonthDepr1) {this.oldMonthDepr1 = checkSet(oldMonthDepr1);}
	public String getOldApportionEndYM() {return checkGet(oldApportionEndYM);}
	public void setOldApportionEndYM(String oldApportionEndYM) {this.oldApportionEndYM = checkSet(oldApportionEndYM);}
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
	public String getNewMonthDepr1() {return checkGet(newMonthDepr1);}
	public void setNewMonthDepr1(String newMonthDepr1) {this.newMonthDepr1 = checkSet(newMonthDepr1);}
	public String getNewApportionEndYM() {return checkGet(newApportionEndYM);}
	public void setNewApportionEndYM(String newApportionEndYM) {this.newApportionEndYM = checkSet(newApportionEndYM);}
	public String getSignNoLA() {return checkGet(signNoLA);}
	public void setSignNoLA(String signNoLA) {this.signNoLA = checkSet(signNoLA);}
	public String getAreaLA() {return checkGet(areaLA);}
	public void setAreaLA(String areaLA) {this.areaLA = checkSet(areaLA);}
	public String getHoldNumeLA() {return checkGet(holdNumeLA);}
	public void setHoldNumeLA(String holdNumeLA) {this.holdNumeLA = checkSet(holdNumeLA);}
	public String getHoldDenoLA() {return checkGet(holdDenoLA);}
	public void setHoldDenoLA(String holdDenoLA) {this.holdDenoLA = checkSet(holdDenoLA);}
	public String getHoldAreaLA() {return checkGet(holdAreaLA);}
	public void setHoldAreaLA(String holdAreaLA) {this.holdAreaLA = checkSet(holdAreaLA);}
	public String getSignNoBU() {return checkGet(signNoBU);}
	public void setSignNoBU(String signNoBU) {this.signNoBU = checkSet(signNoBU);}
	public String getCAreaBU() {return checkGet(CAreaBU);}
	public void setCAreaBU(String cAreaBU) {CAreaBU = checkSet(cAreaBU);}
	public String getSAreaBU() {return checkGet(SAreaBU);}
	public void setSAreaBU(String sAreaBU) {SAreaBU = checkSet(sAreaBU);}
	public String getAreaBU() {return checkGet(areaBU);}
	public void setAreaBU(String areaBU) {this.areaBU = checkSet(areaBU);}
	public String getHoldNumeBU() {return checkGet(holdNumeBU);}
	public void setHoldNumeBU(String holdNumeBU) {this.holdNumeBU = checkSet(holdNumeBU);}
	public String getHoldDenoBU() {return checkGet(holdDenoBU);}
	public void setHoldDenoBU(String holdDenoBU) {this.holdDenoBU = checkSet(holdDenoBU);}
	public String getHoldAreaBU() {return checkGet(holdAreaBU);}
	public void setHoldAreaBU(String holdAreaBU) {this.holdAreaBU = checkSet(holdAreaBU);}
	public String getMeasure() {return checkGet(measure);}
	public void setMeasure(String measure) {this.measure = checkSet(measure);}
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
	public String getUpdatedeprcb() {return checkGet(updatedeprcb);}
	public void setUpdatedeprcb(String updatedeprcb) {this.updatedeprcb = checkSet(updatedeprcb);}
	
	private String propertyNoName;
	private String oldPlace1Name;
	private String newPlace1Name;
	public String getPropertyNoName() {return checkGet(propertyNoName);}
	public void setPropertyNoName(String propertyNoName) {this.propertyNoName = checkSet(propertyNoName);}
	public String getOldPlace1Name() {return checkGet(oldPlace1Name);}
	public void setOldPlace1Name(String oldPlace1Name) {this.oldPlace1Name = checkSet(oldPlace1Name);}
	public String getNewPlace1Name() {return checkGet(newPlace1Name);}
	public void setNewPlace1Name(String newPlace1Name) {this.newPlace1Name = checkSet(newPlace1Name);}	
	
	
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
	
	private String enterOrgName;
	public String getEnterOrgName() {return checkGet(enterOrgName);}
	public void setEnterOrgName(String enterOrgName) {this.enterOrgName = checkSet(enterOrgName);}
	
	private String roleid;
	public String getRoleid() {return checkGet(roleid);}
	public void setRoleid(String roleid) {this.roleid = checkSet(roleid);}
	
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
	
	private String propertyUnit;
	private String material;
	private String limitYear;
	public String getPropertyUnit() {return checkGet(propertyUnit);}
	public void setPropertyUnit(String propertyUnit) {this.propertyUnit = checkSet(propertyUnit);}
	public String getMaterial() {return checkGet(material);}
	public void setMaterial(String material) {this.material = checkSet(material);}
	public String getLimitYear() {return checkGet(limitYear);}
	public void setLimitYear(String limitYear) {this.limitYear = checkSet(limitYear);}
	
	/** 切換tab至 UTCH002F02時 , UNTCH002F01紀錄的當前頁數 **/
	private String UNTCH002F01_currentPage;
	public String getUNTCH002F01_currentPage() { return checkGet(UNTCH002F01_currentPage); }
	public void setUNTCH002F01_currentPage(String uNTCH002F01_currentPage) { UNTCH002F01_currentPage = checkSet(uNTCH002F01_currentPage); }

	private String PREPAGE_currentPage;
	public String getPREPAGE_currentPage() { return checkGet(PREPAGE_currentPage); }
	public void setPREPAGE_currentPage(String PREPAGE_currentPage) {
		this.PREPAGE_currentPage = checkSet(PREPAGE_currentPage);
	}
	
	private String q_userRole;
	private String q_keeperno;
	private String q_unitID;
	public String getQ_userRole() { return checkGet(q_userRole); }
	public void setQ_userRole(String q_userRole) { this.q_userRole = checkSet(q_userRole); }
	public String getQ_keeperno() { return checkGet(q_keeperno); }
	public void setQ_keeperno(String q_keeperno) { this.q_keeperno = checkSet(q_keeperno); }
	public String getQ_unitID() { return checkGet(q_unitID); }
	public void setQ_unitID(String q_unitID) { this.q_unitID = checkSet(q_unitID); }
	
}