/*
*<br>程式目的：部門別與部門別單位對應維護
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

import TDlib_Simple.com.src.SQLCreator;
import unt.ch.UNTCH_Tools;
import util.*;

public class SYSCA021F extends QueryBean{

	
	
	protected String[] getInsertSQL(){
		String[] execSQLArray = new String[1];
		execSQLArray[0] = new SQLCreator()._obtainSQLforInsert("SYSCA_DEPRUNITCOMPARISON", getPKMap(), getRecordMap());
		return execSQLArray;
	}
	
	protected String[] getUpdateSQL(){
		String[] execSQLArray = new String[1];
		execSQLArray[0] = new SQLCreator()._obtainSQLforUpdate("SYSCA_DEPRUNITCOMPARISON", getPKMap(), getRecordMap());
		return execSQLArray;
	}
	
	protected String[] getDeleteSQL(){
		String[] execSQLArray = new String[1];
		execSQLArray[0] = new SQLCreator()._obtainSQLforDelete("SYSCA_DEPRUNITCOMPARISON", getPKMap(), getRecordMap());
		return execSQLArray;
	}
	
	public SYSCA021F queryOne() throws Exception{
		Database db = new Database();
		SYSCA021F obj = this;
		try {
			String sql = "select * from SYSCA_DEPRUNITCOMPARISON" +
					" where 1=1" +
					" and enterorg = " + Common.sqlChar(enterOrg) +
					" and deprunitno = " + Common.sqlChar(deprUnitNo) +
					" and deprunit1no = " + Common.sqlChar(deprUnit1No) +
					"";
			ResultSet rs = db.querySQL(sql);
			UNTCH_Tools ut = new UNTCH_Tools();
			if (rs.next()){
				obj.setEnterOrg(rs.getString("EnterOrg"));
				obj.setDeprUnitNo(rs.getString("DeprUnitNo"));
				obj.setDeprUnit1No(rs.getString("DeprUnit1No"));
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
			String sql = "select" +
							" enterOrg," +
							" deprUnitNo," +
							" (select deprUnitName from SYSCA_DEPRUNIT z where z.enterorg = a.enterorg and z.deprUnitNo = a.deprUnitNo) as deprUnitNoName," +
							" deprUnit1No," +
							" (select deprUnit1Name from SYSCA_DEPRUNIT1 z where z.enterorg = a.enterorg and z.deprUnit1No = a.deprUnit1No) as deprUnit1NoName" +
							" from SYSCA_DEPRUNITCOMPARISON a" +
							" where 1=1";
			
				if (!"".equals(getQ_enterOrg())) 
					sql+=" and enterOrg = " + Common.sqlChar(getQ_enterOrg()) ;
				if (!"".equals(getQ_deprUnitNo())) 
					sql+=" and deprUnitNo = " + Common.sqlChar(getQ_deprUnitNo()) ;
				if (!"".equals(getQ_deprUnit1No())) 
					sql+=" and deprUnit1No =  " + Common.sqlChar(getQ_deprUnit1No()) ;
			
				
				
				if ("Y".equals(getIsAdminManager())){
					
				}else{
					sql+=" and enterorg = " + Common.sqlChar(getOrganID()) ;
				}
				
			ResultSet rs = db.querySQL(sql+ " order by enterOrg, deprUnitNo, deprUnit1No");
			while (rs.next()){
				counter++;
				String rowArray[]=new String[5];
				rowArray[0]=rs.getString("enterOrg");  
				rowArray[1]=rs.getString("deprUnitNo"); 
				rowArray[2]=rs.getString("deprUnit1No");
				rowArray[3]=rs.getString("deprUnitNoName"); 
				rowArray[4]=rs.getString("deprUnit1NoName");
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
			map.put("DeprUnitNo", getDeprUnitNo());
			map.put("DeprUnit1No", getDeprUnit1No());
			
			return map;
		}
		
		private Map getRecordMap(){
			Map map = new HashMap();
			
			map.put("EnterOrg", getEnterOrg());
			map.put("DeprUnitNo", getDeprUnitNo());
			map.put("DeprUnit1No", getDeprUnit1No());
			map.put("Notes", getNotes());
			map.put("EditID", getEditID());
			map.put("EditDate", getEditDate());
			map.put("EditTime", getEditTime());
			
			return map;
		}
	
	private String enterOrg;
	private String deprUnitNo;
	private String deprUnit1No;
	private String notes;
	private String editID;
	private String editDate;
	private String editTime;
	public String getEnterOrg() {return checkGet(enterOrg);}
	public void setEnterOrg(String enterOrg) {this.enterOrg = checkSet(enterOrg);}
	public String getDeprUnitNo() {return checkGet(deprUnitNo);}
	public void setDeprUnitNo(String deprUnitNo) {this.deprUnitNo = checkSet(deprUnitNo);}
	public String getDeprUnit1No() {return checkGet(deprUnit1No);}
	public void setDeprUnit1No(String deprUnit1No) {this.deprUnit1No = checkSet(deprUnit1No);}
	public String getNotes() {return checkGet(notes);}
	public void setNotes(String notes) {this.notes = checkSet(notes);}
	public String getEditID() {return checkGet(editID);}
	public void setEditID(String editID) {this.editID = checkSet(editID);}
	public String getEditDate() {return checkGet(editDate);}
	public void setEditDate(String editDate) {this.editDate = checkSet(editDate);}
	public String getEditTime() {return checkGet(editTime);}
	public void setEditTime(String editTime) {this.editTime = checkSet(editTime);}

	private String enterOrgName;
	private String deprUnitNoName;
	private String deprUnit1NoName;
	public String getEnterOrgName() {return checkGet(enterOrgName);}
	public void setEnterOrgName(String enterOrgName) {this.enterOrgName = checkSet(enterOrgName);}
	public String getDeprUnitNoName() {return checkGet(deprUnitNoName);}
	public void setDeprUnitNoName(String deprUnitNoName) {this.deprUnitNoName = checkSet(deprUnitNoName);}
	public String getDeprUnit1NoName() {return checkGet(deprUnit1NoName);}
	public void setDeprUnit1NoName(String deprUnit1NoName) {this.deprUnit1NoName = checkSet(deprUnit1NoName);}
	
	private String q_enterOrg;
	private String q_enterOrgName;
	private String q_deprUnitNo;
	private String q_deprUnitNoName;
	private String q_deprUnit1No;
	private String q_deprUnit1NoName;
	public String getQ_enterOrg() {return checkGet(q_enterOrg);}
	public void setQ_enterOrg(String q_enterOrg) {this.q_enterOrg = checkSet(q_enterOrg);}
	public String getQ_enterOrgName() {return checkGet(q_enterOrgName);}
	public void setQ_enterOrgName(String q_enterOrgName) {this.q_enterOrgName = checkSet(q_enterOrgName);}
	public String getQ_deprUnitNo() {return checkGet(q_deprUnitNo);}
	public void setQ_deprUnitNo(String q_deprUnitNo) {this.q_deprUnitNo = checkSet(q_deprUnitNo);}
	public String getQ_deprUnitNoName() {return checkGet(q_deprUnitNoName);}
	public void setQ_deprUnitNoName(String q_deprUnitNoName) {this.q_deprUnitNoName = checkSet(q_deprUnitNoName);}
	public String getQ_deprUnit1No() {return checkGet(q_deprUnit1No);}
	public void setQ_deprUnit1No(String q_deprUnit1No) {this.q_deprUnit1No = checkSet(q_deprUnit1No);}
	public String getQ_deprUnit1NoName() {return checkGet(q_deprUnit1NoName);}
	public void setQ_deprUnit1NoName(String q_deprUnit1NoName) {this.q_deprUnit1NoName = checkSet(q_deprUnit1NoName);}


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
