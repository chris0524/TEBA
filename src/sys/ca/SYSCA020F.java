/*
*<br>程式目的：部門別單位代碼維護
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
import java.util.HashMap;
import java.util.Map;

import unt.ch.UNTCH_Tools;
import util.Common;
import util.Database;
import util.QueryBean;
import TDlib_Simple.com.src.SQLCreator;

public class SYSCA020F extends QueryBean{
	
	protected String[][] getInsertCheckSQL(){		
		String[][] checkSQLArray = new String[1][4];
	 	
		checkSQLArray[0][0]=" select count(*) as checkresult from SYSCA_DEPRUNIT1 where 1=1 " + 
				" and enterorg = " + Common.sqlChar(enterOrg) + 
				" and deprunit1no = " + Common.sqlChar(deprUnit1No) + 
				"";
		checkSQLArray[0][1]=">";
		checkSQLArray[0][2]="0";
		checkSQLArray[0][3]="該筆資料己重複，請重新輸入！";
			
		return checkSQLArray;
	}
	
	protected String[] getInsertSQL(){
		String[] execSQLArray = new String[1];
		execSQLArray[0] = new SQLCreator()._obtainSQLforInsert("SYSCA_DEPRUNIT1", getPKMap(), getRecordMap());
		return execSQLArray;
	}
	
	protected String[] getUpdateSQL(){
		String[] execSQLArray = new String[1];
		execSQLArray[0] = new SQLCreator()._obtainSQLforUpdate("SYSCA_DEPRUNIT1", getPKMap(), getRecordMap());
		return execSQLArray;
	}
	
	protected String[] getDeleteSQL(){
		String[] execSQLArray = new String[1];
		execSQLArray[0] = new SQLCreator()._obtainSQLforDelete("SYSCA_DEPRUNIT1", getPKMap(), getRecordMap());
		return execSQLArray;
	}
	
	public SYSCA020F queryOne() throws Exception{
		Database db = new Database();
		SYSCA020F obj = this;
		try {
			String sql = "select * from SYSCA_DEPRUNIT1" +
							" where 1=1" +
							" and enterorg = " + Common.sqlChar(enterOrg) +
							" and deprunit1no = " + Common.sqlChar(deprUnit1No) +
							"";
	
			ResultSet rs = db.querySQL(sql);
			UNTCH_Tools ut = new UNTCH_Tools();
			if (rs.next()){
				obj.setEnterOrg(rs.getString("EnterOrg"));
				obj.setDeprUnit1No(rs.getString("DeprUnit1No"));
				obj.setDeprUnit1Name(rs.getString("DeprUnit1Name"));
				obj.setUntdp019rNotes(rs.getString("Untdp019rNotes"));
				obj.setNotes(rs.getString("Notes"));
				obj.setEditID(rs.getString("EditID"));
				obj.setEditDate(rs.getString("EditDate"));
				obj.setEditTime(rs.getString("EditTime"));
				
				obj.setEnterOrgName(ut._getOrganSName(getEnterOrg()));				
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
			String sql = "select " +
						" enterOrg," +
						" deprUnit1No,"+
						" deprUnit1Name"+
						" from SYSCA_DEPRUNIT1" +
						" where 1=1";	
			
				if (!"".equals(getQ_enterOrg())){ 
					sql+=" and enterOrg = " + Common.sqlChar(getQ_enterOrg());
				}
				if (!"".equals(getQ_deprUnit1No())){ 
					sql+=" and deprUnit1No = " + Common.sqlChar(getQ_deprUnit1No());
				}
				if (!"".equals(getQ_deprUnit1Name())){ 
					sql+=" and deprUnit1Name like " + Common.sqlChar("%"+getQ_deprUnit1Name()+"%");
				}
								
				if ("Y".equals(getIsAdminManager())){
					
				}else{
					sql+=" and enterorg = " + Common.sqlChar(getOrganID()) ;
				}
				
				
			ResultSet rs = db.querySQL(sql+ " order by enterOrg, deprUnit1No");
			while (rs.next()){
				counter++;
				String rowArray[]=new String[3];
				rowArray[0] = rs.getString("enterOrg");  
				rowArray[1] = rs.getString("deprUnit1No"); 
				rowArray[2] = rs.getString("deprUnit1Name"); 		
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
	
		private Map getPKMap(){
			Map map = new HashMap();
			
			map.put("EnterOrg", getEnterOrg());
			map.put("DeprUnit1No", getDeprUnit1No());
			
			return map;
		}
		
		private Map getRecordMap(){
			Map map = new HashMap();
			
			map.put("EnterOrg", getEnterOrg());
			map.put("DeprUnit1No", getDeprUnit1No());
			map.put("DeprUnit1Name", getDeprUnit1Name());
			map.put("Untdp019rNotes", getUntdp019rNotes());
			map.put("Notes", getNotes());
			map.put("EditID", getEditID());
			map.put("EditDate", getEditDate());
			map.put("EditTime", getEditTime());
			
			return map;
		}
	
	private String enterOrg;
	private String enterOrgName;
	private String deprUnit1No;
	private String deprUnit1Name;
	private String untdp019rNotes;
	private String notes;
	private String editID;
	private String editDate;
	private String editTime;
	public String getEnterOrg() {return checkGet(enterOrg);}
	public void setEnterOrg(String enterOrg) {this.enterOrg = checkSet(enterOrg);}
	public String getEnterOrgName() {return checkGet(enterOrgName);}
	public void setEnterOrgName(String enterOrgName) {this.enterOrgName = checkSet(enterOrgName);}
	public String getDeprUnit1No() {return checkGet(deprUnit1No);}
	public void setDeprUnit1No(String deprUnit1No) {this.deprUnit1No = checkSet(deprUnit1No);}
	public String getDeprUnit1Name() {return checkGet(deprUnit1Name);}
	public void setDeprUnit1Name(String deprUnit1Name) {this.deprUnit1Name = checkSet(deprUnit1Name);}
	public String getUntdp019rNotes() {return checkGet(untdp019rNotes);}
	public void setUntdp019rNotes(String untdp019rNotes) {this.untdp019rNotes = checkSet(untdp019rNotes);}
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
	private String q_deprUnit1No;
	private String q_deprUnit1Name;
	public String getQ_enterOrg() {return checkGet(q_enterOrg);}
	public void setQ_enterOrg(String q_enterOrg) {this.q_enterOrg = checkSet(q_enterOrg);}
	public String getQ_enterOrgName() {return checkGet(q_enterOrgName);}
	public void setQ_enterOrgName(String q_enterOrgName) {this.q_enterOrgName = checkSet(q_enterOrgName);}
	public String getQ_deprUnit1No() {return checkGet(q_deprUnit1No);}
	public void setQ_deprUnit1No(String q_deprUnit1No) {this.q_deprUnit1No = checkSet(q_deprUnit1No);}
	public String getQ_deprUnit1Name() {return checkGet(q_deprUnit1Name);}
	public void setQ_deprUnit1Name(String q_deprUnit1Name) {this.q_deprUnit1Name = checkSet(q_deprUnit1Name);}
	
	private String roleid;
	private String organID;
	public String getRoleid() {return checkGet(roleid);}
	public void setRoleid(String roleid) {this.roleid = checkSet(roleid);}
	public String getOrganID() {return checkGet(organID);}
	public void setOrganID(String organID) {this.organID = checkSet(organID);}
	
	private String isAdminManager;
	public String getIsAdminManager() {return checkGet(isAdminManager);}
	public void setIsAdminManager(String isAdminManager) {this.isAdminManager = checkSet(isAdminManager);}
}
