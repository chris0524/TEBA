package sys.ca;

import java.sql.ResultSet;
import java.util.ArrayList;

import util.*;

public class SYSCA015F extends QueryBean{
	private String enterOrg;
	private String enterOrgName;
	private String deprUnitNo;
	private String deprUnitNoName;
	private String deprAccountsNo;
	private String deprAccountsNoName;
	private String notes;
	private String editID;
	private String editDate;
	private String editTime;

	public String getEnterOrg() {return checkGet(enterOrg);}
	public void setEnterOrg(String enterOrg) {this.enterOrg = checkSet(enterOrg);}
	public String getEnterOrgName() {return checkGet(enterOrgName);}
	public void setEnterOrgName(String enterOrgName) {this.enterOrgName = checkSet(enterOrgName);}
	public String getDeprUnitNo() {return checkGet(deprUnitNo);}
	public void setDeprUnitNo(String deprUnitNo) {this.deprUnitNo = checkSet(deprUnitNo);}
	public String getDeprUnitNoName() {return checkGet(deprUnitNoName);}
	public void setDeprUnitNoName(String deprUnitNoName) {this.deprUnitNoName = checkSet(deprUnitNoName);}
	public String getDeprAccountsNo() {return checkGet(deprAccountsNo);}
	public void setDeprAccountsNo(String deprAccountsNo) {this.deprAccountsNo = checkSet(deprAccountsNo);}
	public String getDeprAccountsNoName() {return checkGet(deprAccountsNoName);}
	public void setDeprAccountsNoName(String deprAccountsNoName) {this.deprAccountsNoName = checkSet(deprAccountsNoName);}
	public String getNotes() {return checkGet(notes);}
	public void setNotes(String notes) {this.notes = checkSet(notes);}
	public String getEditID() {return checkGet(editID);}
	public void setEditID(String editID) {this.editID = checkSet(editID);}
	public String getEditDate() {return checkGet(editDate);}
	public void setEditDate(String editDate) {this.editDate = checkSet(editDate);}
	public String getEditTime() {return checkGet(editTime);}
	public void setEditTime(String editTime) {this.editTime = checkSet(editTime);}
	
	private String q_enterOrg;
	private String q_enterOrgName;
	private String q_deprUnitNo;
	private String q_deprUnitNoName;
	private String q_deprAccountsNo;
	private String q_deprAccountsNoName;
	
	public String getQ_enterOrg() {return checkGet(q_enterOrg);}
	public void setQ_enterOrg(String q_enterOrg) {this.q_enterOrg = checkSet(q_enterOrg);}
	public String getQ_enterOrgName() {return checkGet(q_enterOrgName);}
	public void setQ_enterOrgName(String q_enterOrgName) {this.q_enterOrgName = checkSet(q_enterOrgName);}
	public String getQ_deprUnitNo() {return checkGet(q_deprUnitNo);}
	public void setQ_deprUnitNo(String q_deprUnitNo) {this.q_deprUnitNo = checkSet(q_deprUnitNo);}
	public String getQ_deprUnitNoName() {return checkGet(q_deprUnitNoName);}
	public void setQ_deprUnitNoName(String q_deprUnitNoName) {this.q_deprUnitNoName = checkSet(q_deprUnitNoName);}
	public String getQ_deprAccountsNo() {return checkGet(q_deprAccountsNo);}
	public void setQ_deprAccountsNo(String q_deprAccountsNo) {this.q_deprAccountsNo = checkSet(q_deprAccountsNo);}
	public String getQ_deprAccountsNoName() {return checkGet(q_deprAccountsNoName);}
	public void setQ_deprAccountsNoName(String q_deprAccountsNoName) {this.q_deprAccountsNoName = checkSet(q_deprAccountsNoName);}
	
	String roleid;
	String organID;
	public String getRoleid() {return checkGet(roleid);}
	public void setRoleid(String roleid) {this.roleid = checkSet(roleid);}
	public String getOrganID() {return checkGet(organID);}
	public void setOrganID(String organID) {this.organID = checkSet(organID);}

	private String isAdminManager;
	public String getIsAdminManager() {return checkGet(isAdminManager);}
	public void setIsAdminManager(String isAdminManager) {this.isAdminManager = checkSet(isAdminManager);}
	
	protected String[][] getInsertCheckSQL(){		
		String[][] checkSQLArray = new String[1][4];
	 	checkSQLArray[0][0]=" select count(*) as checkResult from SYSCA_DEPRUNITACCOUNTS a where 1=1 " +
						" and a.enterOrg = " + Common.sqlChar(getEnterOrg()) +
						" and a.deprUnitNo = " + Common.sqlChar(getDeprUnitNo()) +
						" and a.deprAccountsNo = " + Common.sqlChar(getDeprAccountsNo());
		checkSQLArray[0][1]=">";
		checkSQLArray[0][2]="0";
		checkSQLArray[0][3]="該筆資料己重複，請重新輸入！";
		return checkSQLArray;
	}
	
	protected String[] getInsertSQL(){
		String[] execSQLArray = new String[1];
		execSQLArray[0] = "insert into SYSCA_DEPRUNITACCOUNTS(" +
							" enterOrg," +
							" deprUnitNo," +
							" deprAccountsNo," +
							" notes," +
							" editID," +
							" editDate," +
							" editTime" +
						")Values(" +
							Common.sqlChar(getEnterOrg()) + "," +
							Common.sqlChar(getDeprUnitNo()) + "," +
							Common.sqlChar(getDeprAccountsNo()) + "," +
							Common.sqlChar(getNotes()) + "," +
							Common.sqlChar(getEditID()) + "," +
							Common.sqlChar(getEditDate()) + "," +
							Common.sqlChar(getEditTime()) +
						")";
		return execSQLArray;
	}
	
	
	protected String[] getUpdateSQL(){
		String[] execSQLArray = new String[1];
		execSQLArray[0] = "update SYSCA_DEPRUNITACCOUNTS set" +
							" enterOrg = " + Common.sqlChar(getEnterOrg()) + "," +
							" deprUnitNo = " + Common.sqlChar(getDeprUnitNo()) + "," +
							" deprAccountsNo = " + Common.sqlChar(getDeprAccountsNo()) + "," +
							" notes = " + Common.sqlChar(getNotes()) + "," +
							" editID = " + Common.sqlChar(getEditID()) + "," +
							" editDate = " + Common.sqlChar(getEditDate()) + "," +
							" editTime = " + Common.sqlChar(getEditTime()) +
						" where enterOrg = " + Common.sqlChar(getEnterOrg()) +
							" and deprUnitNo = " + Common.sqlChar(getDeprUnitNo()) +
							" and deprAccountsNo = " + Common.sqlChar(getDeprAccountsNo()) +
						"";
		return execSQLArray;
	}
	
	
	protected String[] getDeleteSQL(){
		String[] execSQLArray = new String[1];
		execSQLArray[0] = "delete from SYSCA_DEPRUNITACCOUNTS " +							
						" where enterOrg = " + Common.sqlChar(getEnterOrg()) +
							" and deprUnitNo = " + Common.sqlChar(getDeprUnitNo()) +
							" and deprAccountsNo = " + Common.sqlChar(getDeprAccountsNo()) +
						"";
		return execSQLArray;
	}
	
	public SYSCA015F queryOne() throws Exception{
		Database db = new Database();
		SYSCA015F obj = this;
		try {
			String sql=" select" +
							" a.enterOrg," +
							" (select organsname from SYSCA_ORGAN z where z.organid = a.enterOrg) as enterOrgName," +
							" a.deprUnitNo," +
							" (select deprUnitName from SYSCA_DEPRUNIT z where z.enterOrg = a.enterOrg and z.deprUnitNo = a.deprUnitNo) as deprUnitNoName," +
							" a.deprAccountsNo," +
							" (select deprAccountsName from SYSCA_DEPRACCOUNTS z where z.enterOrg = a.enterOrg and z.deprAccountsNo = a.deprAccountsNo) as deprAccountsNoName," +
							" a.notes," +
							" a.editID," +
							" a.editDate," +
							" a.editTime" +
					" from SYSCA_DEPRUNITACCOUNTS a where 1=1 " +
						" and a.enterOrg = " + Common.sqlChar(getEnterOrg()) +
						" and a.deprUnitNo = " + Common.sqlChar(getDeprUnitNo()) +
						" and a.deprAccountsNo = " + Common.sqlChar(getDeprAccountsNo());
	
			ResultSet rs = db.querySQL(sql);
			if (rs.next()){
				obj.setEnterOrg(rs.getString("enterOrg"));
				obj.setEnterOrgName(rs.getString("enterOrgName"));
				obj.setDeprUnitNo(rs.getString("deprUnitNo"));
				obj.setDeprUnitNoName(rs.getString("deprUnitNoName"));
				obj.setDeprAccountsNo(rs.getString("deprAccountsNo"));
				obj.setDeprAccountsNoName(rs.getString("deprAccountsNoName"));
				obj.setNotes(rs.getString("notes"));
				obj.setEditID(rs.getString("editID"));
				obj.setEditDate(rs.getString("editDate"));
				obj.setEditTime(rs.getString("editTime"));
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
		int counter=0;
		try {
			String sql=" select" +
					" a.enterOrg," +
					" a.deprUnitNo," +
					" (select deprUnitName from SYSCA_DEPRUNIT z where z.enterOrg = a.enterOrg and z.deprUnitNo = a.deprUnitNo) as deprUnitNoName," +
					" a.deprAccountsNo," +
					" (select deprAccountsName from SYSCA_DEPRACCOUNTS z where z.enterOrg = a.enterOrg and z.deprAccountsNo = a.deprAccountsNo) as deprAccountsNoName" +
					" from SYSCA_DEPRUNITACCOUNTS a" +
					" where 1=1";
		
			
				if (!"".equals(getQ_enterOrg())) 
					sql+=" and a.enterOrg = " + Common.sqlChar(getQ_enterOrg()) ;
				if (!"".equals(getQ_deprUnitNo())) 
					sql+=" and a.deprUnitNo = " + Common.sqlChar(getQ_deprUnitNo()) ;
				if (!"".equals(getQ_deprAccountsNo())) 
					sql+=" and a.deprAccountsNo = " + Common.sqlChar(getQ_deprAccountsNo()) ;
				
				if ("Y".equals(getIsAdminManager())){
					
				}else{
					sql+=" and a.enterorg = " + Common.sqlChar(getOrganID()) ;
				}
			
			ResultSet rs = db.querySQL(sql+ " order by a.enterOrg, a.deprUnitNo, a.deprAccountsNo");
			while (rs.next()){
				counter++;
				String rowArray[]=new String[5];
				rowArray[0]=rs.getString("enterOrg"); 
				rowArray[1]=rs.getString("deprUnitNo"); 
				rowArray[2]=rs.getString("deprAccountsNo"); 
				rowArray[3]=rs.getString("deprUnitNoName"); 
				rowArray[4]=rs.getString("deprAccountsNoName"); 
				objList.add(rowArray);
				if (counter==getListLimit()){
					setErrorMsg(getListLimitError());
					break;
				}
			}
			setStateQueryAllSuccess();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.closeAll();
		}
		return objList;
	}
}


