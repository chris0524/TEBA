/*
*<br>程式目的：會計科目代碼維護
*<br>程式代號：
*<br>程式日期：
*<br>程式作者：
*<br>--------------------------------------------------------
*<br>修改作者　　修改日期　　　修改目的
*<br>--------------------------------------------------------
*/

package sys.ca;

import java.sql.ResultSet;
import java.util.ArrayList;

import util.*;

public class SYSCA018F extends QueryBean{

	private String enterOrg;
	private String enterOrgName;
	private String deprAccountsNo;
	private String deprAccountsName;
	private String notes;
	private String editID;
	private String editDate;
	private String editTime;
	private String q_enterOrg;
	private String q_enterOrgName;
	private String q_deprAccountsNo;
	private String q_deprAccountsName;
	
	public String getEnterOrg() {return checkGet(enterOrg);}
	public void setEnterOrg(String enterOrg) {this.enterOrg = checkSet(enterOrg);}
	public String getEnterOrgName() {return checkGet(enterOrgName);}
	public void setEnterOrgName(String enterOrgName) {this.enterOrgName = checkSet(enterOrgName);}
	public String getDeprAccountsNo() {return checkGet(deprAccountsNo);}
	public void setDeprAccountsNo(String deprAccountsNo) {this.deprAccountsNo = checkSet(deprAccountsNo);}
	public String getDeprAccountsName() {return checkGet(deprAccountsName);}
	public void setDeprAccountsName(String deprAccountsName) {this.deprAccountsName = checkSet(deprAccountsName);}
	public String getNotes() {return checkGet(notes);}
	public void setNotes(String notes) {this.notes = checkSet(notes);}
	public String getEditID() {return checkGet(editID);}
	public void setEditID(String editID) {this.editID = checkSet(editID);}
	public String getEditDate() {return checkGet(editDate);}
	public void setEditDate(String editDate) {this.editDate = checkSet(editDate);}
	public String getEditTime() {return checkGet(editTime);}
	public void setEditTime(String editTime) {this.editTime = checkSet(editTime);}
	public String getQ_enterOrg() {return checkGet(q_enterOrg);}
	public void setQ_enterOrg(String q_enterOrg) {this.q_enterOrg = checkSet(q_enterOrg);}
	public String getQ_enterOrgName() {return checkGet(q_enterOrgName);}
	public void setQ_enterOrgName(String q_enterOrgName) {this.q_enterOrgName = checkSet(q_enterOrgName);}
	public String getQ_deprAccountsNo() {return checkGet(q_deprAccountsNo);}
	public void setQ_deprAccountsNo(String q_deprAccountsNo) {this.q_deprAccountsNo = checkSet(q_deprAccountsNo);}
	public String getQ_deprAccountsName() {return checkGet(q_deprAccountsName);}
	public void setQ_deprAccountsName(String q_deprAccountsName) {this.q_deprAccountsName = checkSet(q_deprAccountsName);}
	
	private String roleid;
	private String organID;
	public String getRoleid() {return checkGet(roleid);}
	public void setRoleid(String roleid) {this.roleid = checkSet(roleid);}
	public String getOrganID() {return checkGet(organID);}
	public void setOrganID(String organID) {this.organID = checkSet(organID);}
	
	private String isAdminManager;
	public String getIsAdminManager() {return checkGet(isAdminManager);}
	public void setIsAdminManager(String isAdminManager) {this.isAdminManager = checkSet(isAdminManager);}
	
	protected String[][] getInsertCheckSQL(){		
		String[][] checkSQLArray = new String[1][4];
	 	checkSQLArray[0][0]=" select count(*) as checkResult from SYSCA_DEPRACCOUNTS a where 1=1 " +
						" and a.enterOrg = " + Common.sqlChar(getEnterOrg()) +
						" and deprAccountsNo = " + Common.sqlChar(getDeprAccountsNo());
		checkSQLArray[0][1]=">";
		checkSQLArray[0][2]="0";
		checkSQLArray[0][3]="該筆資料己重複，請重新輸入！";
		return checkSQLArray;
	}
	
	protected String[] getInsertSQL(){
		String[] execSQLArray = new String[1];
		execSQLArray[0] = "insert into SYSCA_DEPRACCOUNTS(" +
							" enterOrg," +
							" deprAccountsNo," +
							" deprAccountsName," +
							" notes," +
							" editID," +
							" editDate," +
							" editTime" +
						")Values(" +
							Common.sqlChar(getEnterOrg()) + "," +
							Common.sqlChar(getDeprAccountsNo()) + "," +
							Common.sqlChar(getDeprAccountsName()) + "," +
							Common.sqlChar(getNotes()) + "," +
							Common.sqlChar(getEditID()) + "," +
							Common.sqlChar(getEditDate()) + "," +
							Common.sqlChar(getEditTime()) +
						")";
		return execSQLArray;
	}
	
	
	protected String[] getUpdateSQL(){
		String[] execSQLArray = new String[1];
		execSQLArray[0] = "update SYSCA_DEPRACCOUNTS set" +
							" enterOrg = " + Common.sqlChar(getEnterOrg()) + "," +
							" deprAccountsNo = " + Common.sqlChar(getDeprAccountsNo()) + "," +
							" deprAccountsName = " + Common.sqlChar(getDeprAccountsName()) + "," +
							" notes = " + Common.sqlChar(getNotes()) + "," +
							" editID = " + Common.sqlChar(getEditID()) + "," +
							" editDate = " + Common.sqlChar(getEditDate()) + "," +
							" editTime = " + Common.sqlChar(getEditTime()) +
						" where enterOrg = " + Common.sqlChar(getEnterOrg()) +
							" and deprAccountsNo = " + Common.sqlChar(getDeprAccountsNo()) +
						"";
		return execSQLArray;
	}
	
	
	protected String[] getDeleteSQL(){
		String[] execSQLArray = new String[1];
		execSQLArray[0] = "delete from SYSCA_DEPRACCOUNTS " +							
						" where enterOrg = " + Common.sqlChar(getEnterOrg()) +
							" and deprAccountsNo = " + Common.sqlChar(getDeprAccountsNo()) +
						"";
		return execSQLArray;
	}
	
	public SYSCA018F queryOne() throws Exception{
		Database db = new Database();
		SYSCA018F obj = this;
		try {
			String sql=" select" +
							" a.enterOrg," +
							" (select organsname from SYSCA_ORGAN z where z.organid = a.enterOrg) as enterOrgName," +
							" a.deprAccountsNo," +
							" a.deprAccountsName," +
							" a.notes," +
							" a.editID," +
							" a.editDate," +
							" a.editTime" +
					" from SYSCA_DEPRACCOUNTS a where 1=1 " +
						" and a.enterOrg = " + Common.sqlChar(getEnterOrg()) +
						" and a.deprAccountsNo = " + Common.sqlChar(getDeprAccountsNo());
	
			ResultSet rs = db.querySQL(sql);
			if (rs.next()){
				obj.setEnterOrg(rs.getString("enterOrg"));
				obj.setEnterOrgName(rs.getString("enterOrgName"));
				obj.setDeprAccountsNo(rs.getString("deprAccountsNo"));
				obj.setDeprAccountsName(rs.getString("deprAccountsName"));
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
					" a.deprAccountsNo," +
					" a.deprAccountsName" +
					" from SYSCA_DEPRACCOUNTS a" +
					" where 1=1";
		
			
				if (!"".equals(getQ_enterOrg())) 
					sql+=" and a.enterOrg = " + Common.sqlChar(getQ_enterOrg()) ;
				if (!"".equals(getQ_deprAccountsNo())) 
					sql+=" and a.deprAccountsNo = " + Common.sqlChar(getQ_deprAccountsNo()) ;
				if (!"".equals(getQ_deprAccountsName())) 
					sql+=" and a.deprAccountsName like  " + Common.sqlChar("%" + getQ_deprAccountsName() + "%") ;
				
				if ("Y".equals(getIsAdminManager())){
					
				}else{
					sql+=" and a.enterorg = " + Common.sqlChar(getOrganID()) ;
				}
			
			ResultSet rs = db.querySQL(sql+ " order by a.enterOrg, a.deprAccountsNo, a.deprAccountsName");
			while (rs.next()){
				counter++;
				String rowArray[]=new String[3];
				rowArray[0]=rs.getString("enterOrg");  
				rowArray[1]=rs.getString("deprAccountsNo"); 
				rowArray[2]=rs.getString("deprAccountsName"); 
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

