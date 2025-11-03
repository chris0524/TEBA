/*
*<br>程式目的：營建工程管理
*<br>程式代號：
*<br>程式日期：
*<br>程式作者：
*<br>--------------------------------------------------------
*<br>修改作者　　修改日期　　　修改目的
*<br>--------------------------------------------------------
*/

package unt.eg;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import util.Common;
import util.Database;
import util.Datetime;
import TDlib_Simple.com.src.DBServerTools;
import TDlib_Simple.com.src.SQLCreator;
import TDlib_Simple.tools.src.MathTools;
import TDlib_Simple.tools.src.StringTools;

public class UNTEG001F extends UNTEG001Q{


	protected String[][] getInsertCheckSQL(){
		String[][] checkSQLArray = new String[1][4];
	 	checkSQLArray[0][0]=" select count(*) as checkresult from UNTEG_EngineeringCase where 1=1 " + 
			" and enterorg = " + Common.sqlChar(enterOrg) + 
			" and engineeringNo = " + Common.sqlChar(engineeringNo) +
			"";
		checkSQLArray[0][1]=">";
		checkSQLArray[0][2]="0";
		checkSQLArray[0][3]="該筆資料己重複，請重新輸入！";
		return checkSQLArray;	
	}
	
	protected String[] getInsertSQL(){
		String[] execSQLArray = new String[1];
		execSQLArray[0] = new SQLCreator()._obtainSQLforInsert("UNTEG_EngineeringCase", getPKMap(), getRecordMap());
		return execSQLArray;
	}
	
	protected String[] getUpdateSQL(){
		String[] execSQLArray = new String[1];
		execSQLArray[0] = new SQLCreator()._obtainSQLforUpdate("UNTEG_EngineeringCase", getPKMap(), getRecordMap());
		return execSQLArray;
	}
	
	protected String[][] getDeleteCheckSQL(){
		String[][] checkSQLArray = new String[1][4];
	 	checkSQLArray[0][0] = "select sum(checkresult) as checkresult from" +
	 							"(" +
	 							" select count(*) as checkresult from UNTLA_LAND where 1=1 " + 
								" and enterorg = " + Common.sqlChar(enterOrg) + 
								" and engineeringNo = " + Common.sqlChar(engineeringNo) +
								" union" +
								" select count(*) as checkresult from UNTBU_BUILDING where 1=1 " + 
								" and enterorg = " + Common.sqlChar(enterOrg) + 
								" and engineeringNo = " + Common.sqlChar(engineeringNo) +
								" union" +
								" select count(*) as checkresult from UNTRF_ATTACHMENT where 1=1 " + 
								" and enterorg = " + Common.sqlChar(enterOrg) + 
								" and engineeringNo = " + Common.sqlChar(engineeringNo) +
								" union" +
								" select count(*) as checkresult from UNTMP_MOVABLE where 1=1 " + 
								" and enterorg = " + Common.sqlChar(enterOrg) + 
								" and engineeringNo = " + Common.sqlChar(engineeringNo) +
								" union" +
								" select count(*) as checkresult from UNTVP_ADDPROOF where 1=1 " + 
								" and enterorg = " + Common.sqlChar(enterOrg) + 
								" and engineeringNo = " + Common.sqlChar(engineeringNo) +
								" union" +
								" select count(*) as checkresult from UNTRT_ADDPROOF where 1=1 " + 
								" and enterorg = " + Common.sqlChar(enterOrg) + 
								" and engineeringNo = " + Common.sqlChar(engineeringNo) +
								") a";
		checkSQLArray[0][1]=">";
		checkSQLArray[0][2]="0";
		checkSQLArray[0][3]="該筆已做過增加單，不可刪除！";
		return checkSQLArray;	
	}
	
	protected String[] getDeleteSQL(){
		String[] execSQLArray = new String[1];
		execSQLArray[0] = new SQLCreator()._obtainSQLforDelete("UNTEG_EngineeringCase", getPKMap(), getRecordMap());
		return execSQLArray;
	}
	
	public UNTEG001F  queryOne() throws Exception{
		Database db = new Database();
		UNTEG001F obj = this;
		try {
			String sql=" select a.*, "+
						"(select organsname from SYSCA_ORGAN z where z.organid = a.enterorg) as enterorgName" +
						" from UNTEG_EngineeringCase a where 1=1 " +
						" and a.enterorg = " + Common.sqlChar(enterOrg) +
						" and a.engineeringNo = " + Common.sqlChar(engineeringNo) +
						"";
			
			ResultSet rs = db.querySQL(sql);
			if (rs.next()){
				obj.setEnterOrg(rs.getString("EnterOrg"));
				obj.setEnterOrgName(rs.getString("EnterOrgName"));
				obj.setEngineeringNo(rs.getString("EngineeringNo"));
				obj.setEngineeringName(rs.getString("EngineeringName"));
				obj.setCreateDate(rs.getString("CreateDate"));
				obj.setNotes(rs.getString("Notes"));
				obj.setEditID(rs.getString("EditID"));
				obj.setEditDate(rs.getString("EditDate"));
				obj.setEditTime(rs.getString("EditTime"));
				obj.setBuildFee(rs.getString("buildFee"));
				obj.setUnitID(rs.getString("unitID"));
				obj.setDeprPark(rs.getString("deprPark"));
				
				obj.setEngAmount_NoVerify(queryBookValue_forAddProof_with(obj.getEnterOrg(), obj.getEngineeringNo(), "N"));
				obj.setEngAmount_Verify(queryBookValue_forAddProof_with(obj.getEnterOrg(), obj.getEngineeringNo(), "Y"));
				
				obj.setEngAmount_adjust_NoVerify(queryBookValue_forAdjustProof_with(obj.getEnterOrg(), obj.getEngineeringNo(), "N"));
				obj.setEngAmount_adjust_Verify(queryBookValue_forAdjustProof_with(obj.getEnterOrg(), obj.getEngineeringNo(), "Y"));
				
				MathTools mt = new MathTools();
				obj.setEngAmount_Total(mt._addition_withBigDecimal(obj.getEngAmount_Verify(), obj.getEngAmount_adjust_Verify()));
				
				StringTools st = new StringTools();
				obj.setEngAmount_NoVerify(st._getMoneyFormat(obj.getEngAmount_NoVerify()));
				obj.setEngAmount_Verify(st._getMoneyFormat(obj.getEngAmount_Verify()));
				obj.setEngAmount_adjust_NoVerify(st._getMoneyFormat(obj.getEngAmount_adjust_NoVerify()));
				obj.setEngAmount_adjust_Verify(st._getMoneyFormat(obj.getEngAmount_adjust_Verify()));
				obj.setEngAmount_Total(st._getMoneyFormat(obj.getEngAmount_Total()));
				obj.setBuildFee(st._getMoneyFormat(obj.getBuildFee()));
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
			String sql = " select a.enterOrg, a.engineeringNo, a.engineeringName, a.createDate" +
							" from UNTEG_EngineeringCase a" +
							" where 1=1";			
			
				if (!"".equals(getQ_engineeringNo())){
					sql += " and a.engineeringNo like " + Common.sqlChar("%" + getQ_engineeringNo() + "%");
				}
				if (!"".equals(getQ_engineeringName())){
					sql += " and a.engineeringName like " + Common.sqlChar("%" + getQ_engineeringName() + "%");
				}
				
				if("Y".equals(getIsAdminManager())){
					
				}else{
					sql += " and a.enterorg = '" + this.getOrganID() + "'";
				}
			
			sql += " order by a.enterOrg ,a.engineeringNo";
			
			MathTools mt = new MathTools();
			StringTools st = new StringTools();
			ResultSet rs = db.querySQL(sql,true);
			processCurrentPageAttribute(rs);			
			if (getTotalRecord() > 0) {
				int count = getRecordStart();
				int end = getRecordEnd();
				do {
					if (count > end)
						break;
				String rowArray[]=new String[9];
				
				rowArray[0] = rs.getString("enterOrg"); 		
				rowArray[1] = rs.getString("engineeringNo"); 		
				rowArray[2] = rs.getString("engineeringName");
				rowArray[3] = queryBookValue_forAddProof_with(rowArray[0], rowArray[1], "N");
				rowArray[4] = queryBookValue_forAddProof_with(rowArray[0], rowArray[1], "Y");
				rowArray[5] = queryBookValue_forAdjustProof_with(rowArray[0], rowArray[1], "N");
				rowArray[6] = queryBookValue_forAdjustProof_with(rowArray[0], rowArray[1], "Y");
				rowArray[7] = mt._addition_withBigDecimal(rowArray[4], rowArray[6]);
				rowArray[8] = rs.getString("createDate"); 		

				rowArray[3] = st._getMoneyFormat(rowArray[3]);
				rowArray[4] = st._getMoneyFormat(rowArray[4]);
				rowArray[5] = st._getMoneyFormat(rowArray[5]);
				rowArray[6] = st._getMoneyFormat(rowArray[6]);
				rowArray[7] = st._getMoneyFormat(rowArray[7]);
				
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
	
		private String queryBookValue_forAddProof_with(String enterOrg, String engineeringNo, String verify){
			DBServerTools dbt = new DBServerTools();
			dbt._setDatabase(new Database());
			
			String condition = getQueryCondition(enterOrg, engineeringNo, verify);
			
			String sql = "select case when sum(bookvalue) is null then 0 else sum(bookvalue) end as bookvalue from " + 
							"(" +
							" select sum(originalbv) as bookvalue from UNTLA_LAND a " + condition +
							" union" +
							" select sum(originalbv) as bookvalue from UNTBU_BUILDING a " + condition +
							" union" +
							" select sum(originalbv) as bookvalue from UNTRF_ATTACHMENT a " + condition +
							" union" +
							" select sum(originalbv) as bookvalue from UNTMP_MOVABLE a " + condition +
							" union" +
							" select sum(originalbv) as bookvalue from UNTVP_ADDPROOF a " + condition +
							" union" +
							" select sum(originalbv) as bookvalue from UNTRT_ADDPROOF a " + condition +
							") b";

			return dbt._execSQL_asString(sql);
		}
		
		private String queryBookValue_forAdjustProof_with(String enterOrg, String engineeringNo, String verify){
			DBServerTools dbt = new DBServerTools();
			dbt._setDatabase(new Database());
			
			String condition = getQueryCondition(enterOrg, engineeringNo, verify);
			
			String sql = "select case when sum(bookvalue) is null then 0 else sum(bookvalue) end as bookvalue from " + 
							"(" +
							" select sum(isnull(addbookvalue,0)) as bookvalue from UNTLA_ADJUSTDETAIL a " + condition +
							" union" +
							" select sum(isnull(addbookvalue,0)) as bookvalue from UNTBU_ADJUSTDETAIL a " + condition +
							" union" +
							" select sum(isnull(addbookvalue,0)) as bookvalue from UNTRF_ADJUSTDETAIL a " + condition +
							" union" +
							" select sum(isnull(addbookvalue,0)) as bookvalue from UNTMP_ADJUSTDETAIL a " + condition +
							" union" +
							" select sum(isnull(addbookvalue,0)) as bookvalue from UNTVP_ADJUSTPROOF a " + condition +
							" union" +
							" select sum(isnull(addbookvalue,0)) as bookvalue from UNTRT_ADJUSTPROOF a " + condition +
							") b";

			return dbt._execSQL_asString(sql);
		}
		
		
			private String getQueryCondition(String enterOrg, String engineeringNo, String verify){
				String result = " where a.enterorg = '" + enterOrg + "'" + 
								" and a.engineeringno = '" + engineeringNo + "'" + 
								" and a.verify = '" + verify + "'";
				return result;
			}
	
		private Map getPKMap(){
			Map map = new HashMap();
			
			map.put("EnterOrg", getEnterOrg());
			map.put("EngineeringNo", getEngineeringNo());
			
			return map;
		}
		
		private Map getRecordMap(){
			Map map = new HashMap();
			
			map.put("EnterOrg", getEnterOrg());
			map.put("EngineeringNo", getEngineeringNo());
			map.put("EngineeringName", getEngineeringName());
			map.put("CreateDate", getCreateDate());
			map.put("Notes", getNotes());
			map.put("EditID", getEditID());
			map.put("EditDate", getEditDate());
			map.put("EditTime", getEditTime());
			map.put("buildFee", getBuildFee());
			map.put("unitID", getUnitID());
			map.put("deprPark", getDeprPark());
			
			return map;
		}
		
		@Override
		public void insert() throws Exception{
			Database db = new Database();
			String className = this.getClass().getName();
			
			try {			
				if (!beforeExecCheck(getInsertCheckSQL())) {
					setStateInsertError();
				} else {
//					setEditID(getUserID());
					setEditDate(Datetime.getYYYMMDD());
					setEditTime(Datetime.getHHMMSS());	
					
					db.excuteSQL(getInsertSQL());		
					
					setStateInsertSuccess();
					setErrorMsg("新增完成");
					
					//使用者操作記錄
					if ("Y".equals(this.getSaveLog())) {
						Common.insertCreateRecordSql("", this.getEnterOrg(), this.getUserID(), className.substring(className.lastIndexOf('.') + 1), this.getObjPath().replaceAll("&gt;", ">") + " > > 新增" + this.getEngineeringNo());			
					}
				}

			} catch (Exception e) {
//				e.printStackTrace();
				throw e;
			} finally {
				db.closeAll();
			}			
		}
	
	private String enterOrg;
	private String enterOrgName;
	private String engineeringNo;
	private String engineeringName;
	private String createDate;
	private String notes;
	private String editID;
	private String editDate;
	private String editTime;

	public String getEnterOrg() {return checkGet(enterOrg);}
	public void setEnterOrg(String enterOrg) {this.enterOrg = checkSet(enterOrg);}
	public String getEnterOrgName() {return checkGet(enterOrgName);}
	public void setEnterOrgName(String enterOrgName) {this.enterOrgName = checkSet(enterOrgName);}
	public String getEngineeringNo() {return checkGet(engineeringNo);}
	public void setEngineeringNo(String engineeringNo) {this.engineeringNo = checkSet(engineeringNo);}
	public String getEngineeringName() {return checkGet(engineeringName);}
	public void setEngineeringName(String engineeringName) {this.engineeringName = checkSet(engineeringName);}
	public String getCreateDate() {return checkGet(createDate);}
	public void setCreateDate(String createDate) {this.createDate = checkSet(createDate);}
	public String getNotes() {return checkGet(notes);}
	public void setNotes(String notes) {this.notes = checkSet(notes);}
	public String getEditID() {return checkGet(editID);}
	public void setEditID(String editID) {this.editID = checkSet(editID);}
	public String getEditDate() {return checkGet(editDate);}
	public void setEditDate(String editDate) {this.editDate = checkSet(editDate);}
	public String getEditTime() {return checkGet(editTime);}
	public void setEditTime(String editTime) {this.editTime = checkSet(editTime);}
	
	private String engAmount_NoVerify;
	private String engAmount_Verify;
	private String engAmount_adjust_NoVerify;
	private String engAmount_adjust_Verify;
	private String engAmount_Total;
	public String getEngAmount_NoVerify() {return checkGet(engAmount_NoVerify);}
	public void setEngAmount_NoVerify(String engAmount_NoVerify) {this.engAmount_NoVerify = checkSet(engAmount_NoVerify);}
	public String getEngAmount_Verify() {return checkGet(engAmount_Verify);}
	public void setEngAmount_Verify(String engAmount_Verify) {this.engAmount_Verify = checkSet(engAmount_Verify);}
	public String getEngAmount_adjust_NoVerify() {return checkGet(engAmount_adjust_NoVerify);}
	public void setEngAmount_adjust_NoVerify(String engAmount_adjust_NoVerify) {this.engAmount_adjust_NoVerify = checkSet(engAmount_adjust_NoVerify);}
	public String getEngAmount_adjust_Verify() {return checkGet(engAmount_adjust_Verify);}
	public void setEngAmount_adjust_Verify(String engAmount_adjust_Verify) {this.engAmount_adjust_Verify = checkSet(engAmount_adjust_Verify);}
	public String getEngAmount_Total() {return checkGet(engAmount_Total);}
	public void setEngAmount_Total(String engAmount_Total) {this.engAmount_Total = checkSet(engAmount_Total);}
	private String organID; 
	private String isAdminManager;
	public String getIsAdminManager() {return checkGet(isAdminManager);}
	public void setIsAdminManager(String isAdminManager) {this.isAdminManager = checkSet(isAdminManager);}
	public String getOrganID() { return checkGet(organID); }
    public void setOrganID(String s) { organID = checkSet(s); }
    
    //依照客戶問題839，新增以下欄位
    private String buildFee;
    private String unitID;
    private String deprPark;
	public String getBuildFee() {	return checkGet(buildFee);}
	public void setBuildFee(String buildFee) {this.buildFee = checkSet(buildFee);}
	public String getUnitID() {return checkGet(unitID);}
	public void setUnitID(String unitID) {this.unitID = checkSet(unitID);}
	public String getDeprPark() {return checkGet(deprPark);}
	public void setDeprPark(String deprPark) {	this.deprPark = checkSet(deprPark);}
}