/*
*<br>程式目的：存置地點代碼維護
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

public class SYSCA019F extends QueryBean{

	private String enterOrg;
	private String enterOrgName;
	private String placeNo;
	private String placeName;
	private String notes;
	private String editID;
	private String editDate;
	private String editTime;
	private String q_enterOrg;
	private String q_enterOrgName;
	private String q_placeNo;
	private String q_placeName;
	private String isStop;
	private String q_isStop;
	
	
	public String getIsStop() {return checkGet(isStop);}
	public void setIsStop(String isStop) {this.isStop = checkGet(isStop);}
	public String getQ_isStop() {return checkGet(q_isStop);}
	public void setQ_isStop(String q_isStop) {this.q_isStop = checkSet(q_isStop);}
	public String getEnterOrg() {return checkGet(enterOrg);}
	public void setEnterOrg(String enterOrg) {this.enterOrg = checkSet(enterOrg);}
	public String getEnterOrgName() {return checkGet(enterOrgName);}
	public void setEnterOrgName(String enterOrgName) {this.enterOrgName = checkSet(enterOrgName);}
	public String getPlaceNo() {return checkGet(placeNo);}
	public void setPlaceNo(String placeNo) {this.placeNo = checkSet(placeNo);}
	public String getPlaceName() {return checkGet(placeName);}
	public void setPlaceName(String placeName) {this.placeName = checkSet(placeName);}
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
	public String getQ_placeNo() {return checkGet(q_placeNo);}
	public void setQ_placeNo(String q_placeNo) {this.q_placeNo = checkSet(q_placeNo);}
	public String getQ_placeName() {return checkGet(q_placeName);}
	public void setQ_placeName(String q_placeName) {this.q_placeName = checkSet(q_placeName);}

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
	 	checkSQLArray[0][0]=" select count(*) as checkResult from SYSCA_PLACE a where 1=1 " +
						" and a.enterOrg = " + Common.sqlChar(getEnterOrg()) +
						" and placeNo = " + Common.sqlChar(getPlaceNo());
		checkSQLArray[0][1]=">";
		checkSQLArray[0][2]="0";
		checkSQLArray[0][3]="該筆資料己重複，請重新輸入！";
		return checkSQLArray;
	}
	
	protected String[] getInsertSQL(){
		String[] execSQLArray = new String[1];
		execSQLArray[0] = "insert into SYSCA_PLACE(" +
							" enterOrg," +
							" placeNo," +
							" placeName," +
							" notes," +
							" editID," +
							" editDate," +
							" editTime," +
							" isstop"+
						")Values(" +
							Common.sqlChar(getEnterOrg()) + "," +
							Common.sqlChar(getPlaceNo()) + "," +
							Common.sqlChar(getPlaceName()) + "," +
							Common.sqlChar(getNotes()) + "," +
							Common.sqlChar(getEditID()) + "," +
							Common.sqlChar(getEditDate()) + "," +
							Common.sqlChar(getEditTime()) + "," +
							Common.sqlChar(isStop) + 
						")";
		return execSQLArray;
	}
	
	
	protected String[] getUpdateSQL(){
		String[] execSQLArray = new String[1];
		execSQLArray[0] = "update SYSCA_PLACE set" +
							" enterOrg = " + Common.sqlChar(getEnterOrg()) + "," +
							" placeNo = " + Common.sqlChar(getPlaceNo()) + "," +
							" placeName = " + Common.sqlChar(getPlaceName()) + "," +
							" notes = " + Common.sqlChar(getNotes()) + "," +
							" editID = " + Common.sqlChar(getEditID()) + "," +
							" editDate = " + Common.sqlChar(getEditDate()) + "," +
							" editTime = " + Common.sqlChar(getEditTime()) + "," +
							" isstop = " + Common.sqlChar(isStop) + 
						" where enterOrg = " + Common.sqlChar(getEnterOrg()) +
							" and placeNo = " + Common.sqlChar(getPlaceNo()) +
						"";
		return execSQLArray;
	}
	
	
	protected String[] getDeleteSQL(){
		String[] execSQLArray = new String[1];
		execSQLArray[0] = "delete from SYSCA_PLACE " +							
						" where enterOrg = " + Common.sqlChar(getEnterOrg()) +
							" and placeNo = " + Common.sqlChar(getPlaceNo()) +
						"";
		return execSQLArray;
	}
	
	public SYSCA019F queryOne() throws Exception{
		Database db = new Database();
		SYSCA019F obj = this;
		try {
			String sql=" select" +
							" a.enterOrg," +
							" (select organsname from SYSCA_ORGAN z where z.organid = a.enterOrg) as enterOrgName," +
							" a.placeNo," +
							" a.placeName," +
							" a.notes," +
							" a.editID," +
							" a.editDate," +
							" a.editTime," +
							" a.isstop" +
					" from SYSCA_PLACE a where 1=1 " +
						" and a.enterOrg = " + Common.sqlChar(getEnterOrg()) +
						" and a.placeNo = " + Common.sqlChar(getPlaceNo());
	
			ResultSet rs = db.querySQL(sql);
			if (rs.next()){
				obj.setEnterOrg(rs.getString("enterOrg"));
				obj.setEnterOrgName(rs.getString("enterOrgName"));
				obj.setPlaceNo(rs.getString("placeNo"));
				obj.setPlaceName(rs.getString("placeName"));
				obj.setNotes(rs.getString("notes"));
				obj.setEditID(rs.getString("editID"));
				obj.setEditDate(rs.getString("editDate"));
				obj.setEditTime(rs.getString("editTime"));
				obj.setIsStop(rs.getString("isStop"));
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
					" a.placeNo," +
					" (case when isstop ='Y' then a.placename + '(已停用)' else a.placename end) as placeName" +
					" from SYSCA_PLACE a" +
					" where 1=1";
		
			
				if (!"".equals(getQ_enterOrg())) 
					sql+=" and a.enterOrg = " + Common.sqlChar(getQ_enterOrg()) ;
				if (!"".equals(getQ_placeNo())) 
					sql+=" and a.placeNo = " + Common.sqlChar(getQ_placeNo()) ;
				if (!"".equals(getQ_placeName())) 
					sql+=" and a.placeName like  " + Common.sqlChar("%" + getQ_placeName() + "%") ;
	            if (!"".equals(getQ_isStop()))
	                sql+=" and a.isstop = " + Common.sqlChar(getQ_isStop()) ;
				if ("Y".equals(getIsAdminManager())){
					
				}else{
					sql+=" and a.enterorg = " + Common.sqlChar(getOrganID()) ;
				}
			
			ResultSet rs = db.querySQL(sql+ " order by a.enterOrg, a.placeNo, a.placeName");
			while (rs.next()){
				counter++;
				String rowArray[]=new String[3];
				rowArray[0]=rs.getString("enterOrg");  
				rowArray[1]=rs.getString("placeNo"); 
				rowArray[2]=rs.getString("placeName"); 
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
