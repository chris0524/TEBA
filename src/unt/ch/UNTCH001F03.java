package unt.ch;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import util.Common;
import util.Database;
import util.Datetime;
import util.SuperBean2;
import TDlib_Simple.com.src.DBServerTools;
import TDlib_Simple.com.src.SQLCreator;

public class UNTCH001F03 extends UNTCH001Q{
	
	public void insert() throws Exception{
		Database db = new Database();
		
		try {
			if (!beforeExecCheck(getInsertCheckSQL())){
				setStateInsertError();
				throw new Exception(getErrorMsg());
			}else{
//				setEditID(getUserID());
				setEditDate(Datetime.getYYYMMDD());
				setEditTime(Datetime.getHHMMSS());	
				
				db.excuteSQL(getInsertSQL());		
				
				setStateInsertSuccess();
				setErrorMsg("新增完成");
			}
		} catch (Exception e) {
			throw e;
		} finally {
			db.closeAll();
		}			
	}

	private String getNewSerialNo1(){
		DBServerTools dbt = new DBServerTools();
		dbt._setDatabase(new Database());
		
		String sql="select case when max(serialNo1) is null then 1 else (max(serialNo1) + 1) end as serialNo1 from UNTCH_DEPRPERCENT " +
				" where enterOrg = " + Common.sqlChar(enterOrg) + 
				" and ownership = " + Common.sqlChar(ownership) + 
				" and propertyNo = " + Common.sqlChar(propertyNo) + 
				" and differenceKind = " + Common.sqlChar(differenceKind) +
				" and serialNo = " + Common.sqlChar(serialNo);
		
		return dbt._execSQL_asString(sql);
	}
	
	public String queryOriginalbv(){
		Database db = new Database();
		String propertyno = Common.get(propertyNo);
		String checkStr1 = propertyno.substring(0,1);
		String checkStr3 = propertyno.substring(0,3);
		String mainTable = "";
		
		if("2".equals(checkStr1)){				mainTable = "UNTBU_BUILDING";
		}else if("111".equals(checkStr3)){		mainTable = "UNTRF_ATTACHMENT";
		}else if("1".equals(checkStr1)){		mainTable = "UNTLA_LAND";
		}else if("3".equals(checkStr1) 
				|| "4".equals(checkStr1) 
				|| "5".equals(checkStr1) 
				|| "6".equals(checkStr1)){		
												mainTable = "UNTMP_MOVABLEDETAIL";
		}else if("8".equals(checkStr1)){		mainTable = "UNTRT_ADDPROOF";
		}else if("9".equals(checkStr1)){		mainTable = "UNTVP_ADDPROOF";
		}
		
		String sql = "select a.originalbv,a.deprpark from " + mainTable + " a" +
				" where 1 = 1" + 
				" and a.enterorg = " + Common.sqlChar(enterOrg) +
				" and a.ownership = " + Common.sqlChar(ownership) +
				" and a.differencekind = "+ Common.sqlChar(differenceKind) +
				" and a.propertyno = "+ Common.sqlChar(propertyNo) +
				" and a.serialno = "+ Common.sqlChar(serialNo);
		ResultSet rs = null;
		String originalbv = "";
		String deprpark = "";
		try{
			rs = db.querySQL_NoChange(sql);
			if(rs.next()){
				originalbv = Common.get(rs.getString("originalbv"));
				deprpark = Common.get(rs.getString("deprpark"));
				this.setOriginalbv(originalbv);
				this.setDeprPark(deprpark);
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally {
			if(rs != null){
				rs = null;
			}
			db.closeAll();
		}
		
		return originalbv;
	}
	
	protected String[][] getInsertCheckSQL(){
		String[][] checkSQLArray = new String[1][4];
		if("N".equals(Common.get(this.getIsHistory()))) {
		 	checkSQLArray[0][0]=" SELECT SUM(isnull(deprshareamount,0)) + " + this.getDeprShareamount() + " as 'checkResult'" +
		 		" FROM UNTCH_DEPRPERCENT a where 1=1  " + 
				" and enterOrg = " + Common.sqlChar(enterOrg) + 
				" and ownership = " + Common.sqlChar(ownership) + 
				" and differenceKind = " + Common.sqlChar(differenceKind) + 
				" and propertyNo = " + Common.sqlChar(propertyNo) + 
				" and serialNo = " + Common.sqlChar(serialNo) +
				" and (ishistory = 'N' or ishistory is null)";
		 	//System.out.println(checkSQLArray[0][0]);
			checkSQLArray[0][1]=">";
			checkSQLArray[0][2]= originalbv ;
			checkSQLArray[0][3]="新增失敗，分攤總金額大於總價，請確認分攤金額。";
		}
			return checkSQLArray;
	}
	
	protected String[][] getUpdateCheckSQL(){
		String[][] checkSQLArray = new String[1][4];
		if("N".equals(Common.get(this.getIsHistory()))) {
		 	checkSQLArray[0][0]=" SELECT SUM(isnull(deprshareamount,0)) + " + this.getDeprShareamount() + " as 'checkResult'" +
		 		" FROM UNTCH_DEPRPERCENT a where 1=1  " + 
				" and enterOrg = " + Common.sqlChar(enterOrg) + 
				" and ownership = " + Common.sqlChar(ownership) + 
				" and differenceKind = " + Common.sqlChar(differenceKind) + 
				" and propertyNo = " + Common.sqlChar(propertyNo) + 
				" and serialNo = " + Common.sqlChar(serialNo) +
				" and serialNo1 = " + Common.sqlChar(serialNo1) +
				" and (ishistory = 'N' or ishistory is null)";
		 	//System.out.println(checkSQLArray[0][0]);
			checkSQLArray[0][1]=">";
			checkSQLArray[0][2]= originalbv ;
			checkSQLArray[0][3]="修改失敗，分攤總金額大於總價，請確認分攤金額。";
		}
			return checkSQLArray;
	}
	
	protected String[] getInsertSQL(){
		String[] execSQLArray = new String[1];		
		this.setSerialNo1(this.getNewSerialNo1());
		execSQLArray[0] = new SQLCreator()._obtainSQLforInsert("UNTCH_DEPRPERCENT", getPKMap(), getRecordMap());		
		return execSQLArray;
	}
	
	protected String[] getUpdateSQL(){
		String[] execSQLArray = new String[1];
		execSQLArray[0] = new SQLCreator()._obtainSQLforUpdate("UNTCH_DEPRPERCENT", getPKMap(), getRecordMap());		
		return execSQLArray;
	}
	
	protected String[] getDeleteSQL(){
		String[] execSQLArray = new String[1];		
		execSQLArray[0] = new SQLCreator()._obtainSQLforDelete("UNTCH_DEPRPERCENT", getPKMap(), getRecordMap());		
		return execSQLArray;
	}
	
	
	public UNTCH001F03 queryOne() throws Exception{
		Database db = new Database();
		UNTCH001F03 obj = this;
		try {
			String sql=" select a.* "+
						" ,(select organaname from SYSCA_ORGAN z where z.organid = a.enterorg) as enterOrgName" +
						" ,(select codename from SYSCA_CODE z where z.codekindid = 'OWA' and z.codeid = a.ownership) as ownershipName" +
						" ,(select codename from SYSCA_CODE z where z.codekindid = 'DFK' and z.codeid = a.ownership) as differenceKindName" +
						" ,(select propertyname from SYSPK_PROPERTYCODE z where z.enterorg = a.enterorg and z.propertyno = a.propertyNo) as propertyNoName" +						
						" from UNTCH_DeprPercent a "
						+ " where 1=1 " +
						" and a.enterorg = " + Common.sqlChar(enterOrg) +
						" and a.ownership = " + Common.sqlChar(ownership) +
						" and a.differencekind = "+ Common.sqlChar(differenceKind) +
						" and a.propertyNo = "+ Common.sqlChar(propertyNo) +
						" and a.serialNo = "+ Common.sqlChar(serialNo) +
						" and a.serialNo1 = "+ Common.sqlChar(serialNo1) +
						"";

			ResultSet rs = db.querySQL(sql,true);
			if (rs.next()){
				obj.setEnterOrg(rs.getString("EnterOrg"));
				obj.setOwnership(rs.getString("Ownership"));
				obj.setDifferenceKind(rs.getString("DifferenceKind"));
				obj.setPropertyNo(rs.getString("PropertyNo"));
				obj.setSerialNo(rs.getString("SerialNo"));
				obj.setSerialNo1(rs.getString("SerialNo1"));
				obj.setDeprPark(rs.getString("DeprPark"));
				obj.setDeprUnit(rs.getString("DeprUnit"));
				obj.setDeprAccounts(rs.getString("DeprAccounts"));
				obj.setDeprPercent(rs.getString("DeprPercent"));
				obj.setIsHistory(rs.getString("IsHistory"));
				obj.setNotes(rs.getString("Notes"));
				obj.setEditID(rs.getString("EditID"));
				obj.setEditDate(rs.getString("EditDate"));
				obj.setEditTime(rs.getString("EditTime"));
	
				obj.setEnterOrgName(rs.getString("EnterOrgName"));
				obj.setOwnershipName(rs.getString("OwnershipName"));
				obj.setDifferenceKindName(rs.getString("DifferenceKindName"));
				obj.setPropertyNoName(rs.getString("PropertyNoname"));
				
				obj.setDeprUnit1(rs.getString("deprUnit1"));
				obj.setDeprShareamount(rs.getString("deprshareamount"));
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
		Database db = new Database();
		ArrayList objList=new ArrayList();
		
		try {
			String sql = " select " +
							"enterOrg, " +
							"ownership, " +
							"differenceKind, " +
							"propertyNo, " +
							"serialNo, " +
							"serialNo1, " +
							"(select deprparkname from SYSCA_DEPRPARK z where z.enterorg = a.enterorg and z.deprparkno = a.deprPark) as deprParkName," +
							"(select deprunitname from SYSCA_DEPRUNIT z where z.enterorg = a.enterorg and z.deprunitno = a.deprUnit) as deprUnitName," +
							"(select depraccountsname from SYSCA_DEPRACCOUNTS z where z.enterorg = a.enterorg and z.depraccountsno = a.deprAccounts) as deprAccountsName," +
							"deprPercent, " +
							"(case isHistory when 'Y' then '是' else '否' end) as isHistory " +
					   " from UNTCH_DeprPercent a " +
					   " where 1=1" +
							" and a.enterorg = " + Common.sqlChar(enterOrg) +
							" and a.ownership = " + Common.sqlChar(ownership) +
							" and a.differencekind = "+ Common.sqlChar(differenceKind) +
							" and a.propertyNo = "+ Common.sqlChar(propertyNo) +
							" and a.serialNo = "+ Common.sqlChar(serialNo);
			

			ResultSet rs = db.querySQL(sql+ " order by enterorg, ownership, differencekind, propertyNo, serialNo, serialNo1",true);
			processCurrentPageAttribute(rs);			
			if (getTotalRecord() > 0) {
				int count = getRecordStart();
				int end = getRecordEnd();
				do {
					if (count > end)
						break;
				String rowArray[]=new String[11];
				rowArray[0] = checkGet(rs.getString("enterOrg"));
				rowArray[1] = checkGet(rs.getString("ownership"));
				rowArray[2] = checkGet(rs.getString("differenceKind"));
				rowArray[3] = checkGet(rs.getString("propertyNo"));
				rowArray[4] = checkGet(rs.getString("serialNo"));
				rowArray[5] = checkGet(rs.getString("serialNo1"));
				rowArray[6] = checkGet(rs.getString("deprParkName"));
				rowArray[7] = checkGet(rs.getString("deprUnitName"));
				rowArray[8] = checkGet(rs.getString("deprAccountsName"));
				rowArray[9] = checkGet(rs.getString("deprPercent"));
				rowArray[10] = checkGet(rs.getString("isHistory"));
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
	
	public String querySumDeprShareAmount(){
		String condition = " and enterorg = " + Common.sqlChar(getEnterOrg()) + 
						" and ownership = " + Common.sqlChar(getOwnership()) + 
						" and differencekind = " + Common.sqlChar(getDifferenceKind()) +
						" and propertyno = " + Common.sqlChar(getPropertyNo()) +
						" and serialno = " + Common.sqlChar(getSerialNo()) +
						" and (ishistory = 'N' or ishistory is null)";
		
		return new UNTCH_Tools()._queryData("SUM(deprshareamount)")._from("UNTCH_DEPRPERCENT")._with(condition)._toString();
	}
	
	private String enterOrg;
	private String ownership;
	private String differenceKind;
	private String propertyNo;
	private String serialNo;
	private String serialNo1;
	private String deprPark;
	private String deprUnit;
	private String deprAccounts;
	private String deprPercent;
	private String isHistory;
	private String notes;
	private String editID;
	private String editDate;
	private String editTime;
	public String getEnterOrg() {return checkGet(enterOrg);}
	public void setEnterOrg(String enterOrg) {this.enterOrg = checkSet(enterOrg);}
	public String getOwnership() {return checkGet(ownership);}
	public void setOwnership(String ownership) {this.ownership = checkSet(ownership);}
	public String getDifferenceKind() {return checkGet(differenceKind);}
	public void setDifferenceKind(String differenceKind) {this.differenceKind = checkSet(differenceKind);}
	public String getPropertyNo() {return checkGet(propertyNo);}
	public void setPropertyNo(String propertyNo) {this.propertyNo = checkSet(propertyNo);}
	public String getSerialNo() {return checkGet(serialNo);}
	public void setSerialNo(String serialNo) {this.serialNo = checkSet(serialNo);}
	public String getSerialNo1() {return checkGet(serialNo1);}
	public void setSerialNo1(String serialNo1) {this.serialNo1 = checkSet(serialNo1);}
	public String getDeprPark() {return checkGet(deprPark);}
	public void setDeprPark(String deprPark) {this.deprPark = checkSet(deprPark);}
	public String getDeprUnit() {return checkGet(deprUnit);}
	public void setDeprUnit(String deprUnit) {this.deprUnit = checkSet(deprUnit);}
	public String getDeprAccounts() {return checkGet(deprAccounts);}
	public void setDeprAccounts(String deprAccounts) {this.deprAccounts = checkSet(deprAccounts);}
	public String getDeprPercent() {return checkGet(deprPercent);}
	public void setDeprPercent(String deprPercent) {this.deprPercent = checkSet(deprPercent);}
	public String getIsHistory() {return checkGet(isHistory);}
	public void setIsHistory(String isHistory) {this.isHistory = checkSet(isHistory);}
	public String getNotes() {return checkGet(notes);}
	public void setNotes(String notes) {this.notes = checkSet(notes);}
	public String getEditID() {return checkGet(editID);}
	public void setEditID(String editID) {this.editID = checkSet(editID);}
	public String getEditDate() {return checkGet(editDate);}
	public void setEditDate(String editDate) {this.editDate = checkSet(editDate);}
	public String getEditTime() {return checkGet(editTime);}
	public void setEditTime(String editTime) {this.editTime = checkSet(editTime);}
	
	private String enterOrgName;
	private String ownershipName;
	private String differenceKindName;
	private String propertyNoName;
	private String deprParkName;
	private String deprUnitName;
	private String deprAccountsName;	
	public String getEnterOrgName() {return checkGet(enterOrgName);}
	public String getOwnershipName() {return checkGet(ownershipName);}
	public String getDifferenceKindName() {return checkGet(differenceKindName);}
	public String getPropertyNoName() {return checkGet(propertyNoName);}
	public String getDeprParkName() {return checkGet(deprParkName);}
	public String getDeprUnitName() {return checkGet(deprUnitName);}
	public String getDeprAccountsName() {return checkGet(deprAccountsName);}
	public void setEnterOrgName(String enterOrgName) {this.enterOrgName = checkSet(enterOrgName);}
	public void setOwnershipName(String ownershipName) {this.ownershipName = checkSet(ownershipName);}
	public void setDifferenceKindName(String differenceKindName) {this.differenceKindName = checkSet(differenceKindName);}
	public void setPropertyNoName(String propertyNoName) {this.propertyNoName = checkSet(propertyNoName);}
	public void setDeprParkName(String deprParkName) {this.deprParkName = checkSet(deprParkName);}
	public void setDeprUnitName(String deprUnitName) {this.deprUnitName = checkSet(deprUnitName);}
	public void setDeprAccountsName(String deprAccountsName) {this.deprAccountsName = checkSet(deprAccountsName);}

	private String verify;
	private String originalDeprMethod;
	private String originalDeprUnitCB;
	public String getVerify() {return checkGet(verify);}
	public void setVerify(String verify) {this.verify = checkSet(verify);}
	public String getOriginalDeprMethod() {return checkGet(originalDeprMethod);}
	public void setOriginalDeprMethod(String originalDeprMethod) {this.originalDeprMethod = checkSet(originalDeprMethod);}
	public String getOriginalDeprUnitCB() {return checkGet(originalDeprUnitCB);}
	public void setOriginalDeprUnitCB(String originalDeprUnitCB) {this.originalDeprUnitCB = checkSet(originalDeprUnitCB);}
	
	private String lotNo;
	private String caseNo;
	public String getLotNo() {return checkGet(lotNo);}
	public void setLotNo(String lotNo) {this.lotNo = checkSet(lotNo);}
	public String getCaseNo() {return checkGet(caseNo);}
	public void setCaseNo(String caseNo) {this.caseNo = checkSet(caseNo);}
	
	private String deprUnit1;	
	public String getDeprUnit1() {return checkGet(deprUnit1);}
	public void setDeprUnit1(String deprUnit1) {this.deprUnit1 = checkSet(deprUnit1);}
	
	private String originalbv;
	private String deprShareamount;
	public String getOriginalbv() { return checkGet(originalbv); }
	public void setOriginalbv(String originalbv) { this.originalbv = checkSet(originalbv); }
	public String getDeprShareamount() { return checkGet(deprShareamount); }
	public void setDeprShareamount(String deprShareamount) { this.deprShareamount = checkSet(deprShareamount); }

	private Map getPKMap(){
		Map map = new HashMap();
		
		map.put("EnterOrg", getEnterOrg());
		map.put("Ownership", getOwnership());
		map.put("DifferenceKind", getDifferenceKind());
		map.put("PropertyNo", getPropertyNo());
		map.put("SerialNo", getSerialNo());
		map.put("SerialNo1", getSerialNo1());
		
		return map;
	}
		
		private Map getRecordMap(){
			Map map = new HashMap();
			
			map.put("EnterOrg", getEnterOrg());
			map.put("Ownership", getOwnership());
			map.put("DifferenceKind", getDifferenceKind());
			map.put("PropertyNo", getPropertyNo());
			map.put("SerialNo", getSerialNo());
			map.put("SerialNo1", getSerialNo1());
			map.put("DeprPark", getDeprPark());
			map.put("DeprUnit", getDeprUnit());
			map.put("DeprAccounts", getDeprAccounts());
			map.put("DeprPercent", getDeprPercent());
			map.put("IsHistory", getIsHistory());
			map.put("Notes", getNotes());
			map.put("EditID", getEditID());
			map.put("EditDate", getEditDate());
			map.put("EditTime", getEditTime());
			map.put("DeprUnit1", getDeprUnit1());	
			map.put("deprshareamount", getDeprShareamount());
			
			return map;
		}
	
}