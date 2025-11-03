/*
*<br>程式目的：園區別代碼維護
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

public class SYSCA017F extends QueryBean{

	private String enterOrg;
	private String enterOrgName;
	private String deprParkNo;
	private String deprParkName;
	private String notes;
	private String editID;
	private String editDate;
	private String editTime;
	private String q_enterOrg;
	private String q_enterOrgName;
	private String q_deprParkNo;
	private String q_deprParkName;
	
	public String getEnterOrg() {return checkGet(enterOrg);}
	public void setEnterOrg(String enterOrg) {this.enterOrg = checkSet(enterOrg);}
	public String getEnterOrgName() {return checkGet(enterOrgName);}
	public void setEnterOrgName(String enterOrgName) {this.enterOrgName = checkSet(enterOrgName);}
	public String getDeprParkNo() {return checkGet(deprParkNo);}
	public void setDeprParkNo(String deprParkNo) {this.deprParkNo = checkSet(deprParkNo);}
	public String getDeprParkName() {return checkGet(deprParkName);}
	public void setDeprParkName(String deprParkName) {this.deprParkName = checkSet(deprParkName);}
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
	public String getQ_deprParkNo() {return checkGet(q_deprParkNo);}
	public void setQ_deprParkNo(String q_deprParkNo) {this.q_deprParkNo = checkSet(q_deprParkNo);}
	public String getQ_deprParkName() {return checkGet(q_deprParkName);}
	public void setQ_deprParkName(String q_deprParkName) {this.q_deprParkName = checkSet(q_deprParkName);}
	

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
	 	checkSQLArray[0][0]=" select count(*) as checkResult from SYSCA_DEPRPARK a where 1=1 " +
						" and a.enterOrg = " + Common.sqlChar(getEnterOrg()) +
						" and deprParkNo = " + Common.sqlChar(getDeprParkNo());
		checkSQLArray[0][1]=">";
		checkSQLArray[0][2]="0";
		checkSQLArray[0][3]="該筆資料己重複，請重新輸入！";
		return checkSQLArray;
	}
	
	protected String[] getInsertSQL(){
		String[] execSQLArray = new String[1];
		execSQLArray[0] = "insert into SYSCA_DEPRPARK(" +
							" enterOrg," +
							" deprParkNo," +
							" deprParkName," +
							" notes," +
							" editID," +
							" editDate," +
							" editTime" +
						")Values(" +
							Common.sqlChar(getEnterOrg()) + "," +
							Common.sqlChar(getDeprParkNo()) + "," +
							Common.sqlChar(getDeprParkName()) + "," +
							Common.sqlChar(getNotes()) + "," +
							Common.sqlChar(getEditID()) + "," +
							Common.sqlChar(getEditDate()) + "," +
							Common.sqlChar(getEditTime()) +
						")";
		return execSQLArray;
	}
	
	protected String[] getUpdateSQL(){
		String[] execSQLArray = new String[1];
		execSQLArray[0] = "update SYSCA_DEPRPARK set" +
							" enterOrg = " + Common.sqlChar(getEnterOrg()) + "," +
							" deprParkNo = " + Common.sqlChar(getDeprParkNo()) + "," +
							" deprParkName = " + Common.sqlChar(getDeprParkName()) + "," +
							" notes = " + Common.sqlChar(getNotes()) + "," +
							" editID = " + Common.sqlChar(getEditID()) + "," +
							" editDate = " + Common.sqlChar(getEditDate()) + "," +
							" editTime = " + Common.sqlChar(getEditTime()) +
						" where enterOrg = " + Common.sqlChar(getEnterOrg()) +
							" and deprParkNo = " + Common.sqlChar(getDeprParkNo()) +
						"";
		return execSQLArray;
	}
	
	
	protected String[] getDeleteSQL(){
		String[] execSQLArray = new String[1];
		execSQLArray[0] = "delete from SYSCA_DEPRPARK " +							
						" where enterOrg = " + Common.sqlChar(getEnterOrg()) +
							" and deprParkNo = " + Common.sqlChar(getDeprParkNo()) +
						"";
		return execSQLArray;
	}
	
	public SYSCA017F queryOne() throws Exception{
		Database db = new Database();
		SYSCA017F obj = this;
		try {
			String sql=" select" +
							" a.enterOrg," +
							" (select organsname from SYSCA_ORGAN z where z.organid = a.enterOrg) as enterOrgName," +
							" a.deprParkNo," +
							" a.deprParkName," +
							" a.notes," +
							" a.editID," +
							" a.editDate," +
							" a.editTime" +
					" from SYSCA_DEPRPARK a where 1=1 " +
						" and a.enterOrg = " + Common.sqlChar(getEnterOrg()) +
						" and a.deprParkNo = " + Common.sqlChar(getDeprParkNo());
	
			ResultSet rs = db.querySQL(sql);
			if (rs.next()){
				obj.setEnterOrg(rs.getString("enterOrg"));
				obj.setEnterOrgName(rs.getString("enterOrgName"));
				obj.setDeprParkNo(rs.getString("deprParkNo"));
				obj.setDeprParkName(rs.getString("deprParkName"));
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
					" a.deprParkNo," +
					" a.deprParkName" +
					" from SYSCA_DEPRPARK a" +
					" where 1=1";
		
			
				if (!"".equals(getQ_enterOrg())) 
					sql+=" and a.enterOrg = " + Common.sqlChar(getQ_enterOrg()) ;
				if (!"".equals(getQ_deprParkNo())) 
					sql+=" and a.deprParkNo = " + Common.sqlChar(getQ_deprParkNo()) ;
				if (!"".equals(getQ_deprParkName())) 
					sql+=" and a.deprParkName like  " + Common.sqlChar("%" + getQ_deprParkName() + "%") ;
				
				if ("Y".equals(getIsAdminManager())){
					
				}else{
					sql+=" and enterorg = " + Common.sqlChar(getOrganID()) ;
				}
			
			ResultSet rs = db.querySQL(sql+ " order by a.enterOrg, a.deprParkNo, a.deprParkName");
			while (rs.next()){
				counter++;
				String rowArray[]=new String[3];
				rowArray[0]=rs.getString("enterOrg");  
				rowArray[1]=rs.getString("deprParkNo"); 
				rowArray[2]=rs.getString("deprParkName"); 
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


