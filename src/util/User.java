/*
*<br>程式目的：登入及記錄使用者session資訊
*<br>程式代號：User
*<br>撰寫日期：93/12/01
*<br>程式作者：griffin
*<br>--------------------------------------------------------
*<br>修改作者　　修改日期　　　修改目的
*<br>--------------------------------------------------------
*<br>
*/
package util;

import java.sql.ResultSet;
import sys.ap.SYSAP003F_Permission;

public class User {

	//bena field , get and set method---------------------------------
	String userID;
	String userName;
	String password;
	String organID;
	String organName;
	String unitID;
	String unitName;
	String groupID;
	String keeperno;
	
	String isOrganManager;
	String isAdminManager;	
	String isManager;
	String cannotVerify;
	
	SYSAP003F_Permission permission[];

	public String getUserID(){ return Common.get(userID); }
	public void setUserID(String s){ userID=Common.set(s); }

	public String getUserName(){ return Common.get(userName); }
	public void setUserName(String s){ userName=Common.set(s); }	
	
	public String getPassword(){ return Common.get(password); }
	public void setPassword(String s){ password=Common.set(s); }

	public String getOrganID(){ return Common.get(organID); }
	public void setOrganID(String s){ organID=Common.set(s); }
	
	public String getOrganName(){ return Common.get(organName); }
	public void setOrganName(String s){ organName=Common.set(s); }

	public String getUnitID(){ return Common.get(unitID); }
	public void setUnitID(String s){ unitID=Common.set(s); }
	
	public String getUnitName(){ return Common.get(unitName); }
	public void setUnitName(String s){ unitName=Common.set(s); }	
	
	public String getIsOrganManager(){ return Common.get(isOrganManager); }
	public void setIsOrganManager(String s){ isOrganManager=Common.set(s); }
	
	public String getIsAdminManager(){ return Common.get(isAdminManager); }
	public void setIsAdminManager(String s){ isAdminManager=Common.set(s); }
	
	public String getGroupID(){ return Common.get(groupID); }
	public void setGroupID(String s){ groupID=Common.set(s); }
	
	public String getIsManager(){ return Common.get(isManager); }
	public void setIsManager(String s){ isManager=Common.set(s); }	
	
	public String getCannotVerify(){ return Common.get(cannotVerify); }
	public void setCannotVerify(String s){ cannotVerify=Common.set(s); }	
	
	public SYSAP003F_Permission[] getPermission(){ return permission;}
	public void setPermission(SYSAP003F_Permission p[]){ permission=p;}
	
	public String getKeeperno() {return (keeperno);}
	public void setKeeperno(String keeperno) {this.keeperno = (keeperno);}

	private String roleid;
	public String getRoleid() {return (roleid);}
	public void setRoleid(String roleid) {this.roleid = (roleid);}
	/**
  	 * <br>
  	 * <br>目的：檢查使用者ID及密碼
  	 * <br>參數：無
  	 * <br>傳回：傳回本物件
  	*/
	public User checkUser() throws Exception{
		Database db = new Database();
		User obj = this;
		try {
			String sql=" select a.empID, a.empPWD, a.empName, a.groupID, a.organID, a.unitID, a.isOrganManager, a.isAdminManager, a.isManager, b.organAName, a.cannotVerify, a.rowid "+
					" from SYSAP_Emp a, SYSCA_Organ b " +
				" where a.empID = " + Common.sqlChar(userID) +
					" and a.empPWD = " + Common.sqlChar(password) +
					" and a.isStop <> 'Y' " +
					" and b.organID = a.organID ";
			
			ResultSet rs = db.querySQL(sql);
			if (rs.next()){												
				obj.setUserID(rs.getString("empID"));
				obj.setPassword(rs.getString("empPWD"));
				obj.setUserName(rs.getString("empName"));
				obj.setOrganID(rs.getString("organID"));
				obj.setOrganName(rs.getString("organAName"));
				obj.setUnitID(rs.getString("unitID"));
				obj.setUnitName(rs.getString("unitID"));
				obj.setGroupID(rs.getString("groupID"));
				obj.setIsOrganManager(rs.getString("isOrganManager"));
				obj.setIsAdminManager(rs.getString("isAdminManager"));
				obj.setIsManager(rs.getString("isManage"));
				obj.setCannotVerify(rs.getString("cannotVerify"));
//				obj.setPermission(getPermission(rs.getString("groupID")));
				
				obj.setRoleid(rs.getString("roleid"));
				obj.setKeeperno(rs.getString("keeperNo"));
			}else{
				obj.setUserID("");
				obj.setPassword("");
				obj.setUserName("");
				obj.setOrganID("");
				obj.setOrganName("");
				obj.setUnitID("");
				obj.setUnitName("");
				obj.setGroupID("");
				obj.setIsOrganManager("");
				obj.setIsAdminManager("");
				obj.setIsManager("");	
				obj.setCannotVerify("");
				obj.setRoleid("");
				obj.setKeeperno("");
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.closeAll();
		}			
		return obj;
	}
	
	
  	/**
  	 * <br>
  	 * <br>目的：檢查使用者ID及密碼
  	 * <br>參數：無
  	 * <br>傳回：傳回本物件
  	*/
	public static User getVerifiedUser(String userID, String password,String organid) throws Exception{
		Database db = new Database();
		try {
			String sql=" select a.empid, a.emppwd, a.empname, a.groupid, a.organid, a.unitid, a.isorganmanager, a.isadminmanager, b.organaname, a.roleid, a.keeperNo,"+
						"(select unitName from UNTMP_KEEPUNIT z where z.unitno = a.unitid and z.enterorg = a.organid) as unitidName" +
					" from SYSAP_EMP a, SYSCA_ORGAN b " +
				" where a.empid = " + Common.sqlChar(userID) +
					" and a.emppwd = " + Common.sqlChar(password) +
					" and a.organid = " + Common.sqlChar(organid) +
					" and a.isstop <> 'Y' " +
					" and b.organid = a.organid ";
			// System.out.println("organid="+organid);
			ResultSet rs = db.querySQL(sql);
			if (rs.next()){			
				User obj = new User();				
				obj.setUserID(Common.checkGet(rs.getString("empID")));
				obj.setPassword(Common.checkGet(rs.getString("empPWD")));
				obj.setUserName(Common.checkGet(rs.getString("empName")));
				obj.setOrganID(Common.checkGet(rs.getString("organID")));
				obj.setOrganName(Common.checkGet(rs.getString("organAName")));
				obj.setUnitID(Common.checkGet(rs.getString("unitID")));
				obj.setUnitName(Common.checkGet(rs.getString("unitidName")));
				obj.setGroupID(Common.checkGet(rs.getString("groupID")));
				obj.setIsOrganManager(Common.checkGet(rs.getString("isOrganManager")));
				obj.setIsAdminManager(Common.checkGet(rs.getString("isAdminManager")));
				if (obj.getIsAdminManager().equals("Y") && obj.getIsManager().equals("Y")) {
				} else {
					obj.setPermission(obj.getPermission(Common.checkGet(rs.getString("groupID"))));
				}
				obj.setRoleid(Common.checkGet(rs.getString("roleid")));
				obj.setKeeperno(Common.checkGet(rs.getString("keeperNo")));
				
				return obj;
			}						
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.closeAll();
		}			
		return null;
	}	
	
  	/**
  	 * <br>
  	 * <br>目的：變更密碼
  	 * <br>參數：無
  	 * <br>傳回：無
  	*/
	public void updatePWD(){
		Database db = new Database();
		try {
			String sql = " update SYSAP_Emp set empPWD = " + Common.sqlChar(password) +
							" where empID = " + Common.sqlChar(userID) ;
			String[] sqlArray = {new String(sql)};	
			db.excuteSQL(sqlArray);	
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.closeAll();
		}		
	}	
	
	
  	/**
  	 * <br>
  	 * <br>目的：依groupID取得權限
  	 * <br>參數：groupID
  	 * <br>傳回：permission[]
  	*/
	public SYSAP003F_Permission[] getPermission(String groupID){
		sys.ap.SYSAP003F_AUTH auth = new sys.ap.SYSAP003F_AUTH();
		return auth.getPermission(groupID);
	}
	
	public boolean checkProgPermission(User u,String progID){
		SYSAP003F_Permission p[]=u.getPermission();
		for (int i=0; i<p.length; i++){
			if (progID.equals(p[i].getProgramID())){
				return true;
			}
		}
		return false;
	}
	
	/**
  	 * <br>
  	 * <br>目的：以使用者ID取得物件
  	 * <br>參數：無
  	 * <br>傳回：傳回本物件
  	*/
	public static User getUser(String userID,String organid){
		Database db = new Database();
		User user = null;
		try {
			String sql=" select a.empid, a.emppwd, a.empname, a.groupid, a.organid, a.unitid, a.isorganmanager, a.isadminmanager, b.organaname, a.roleid, a.keeperNo, "+
						"(select unitName from UNTMP_KEEPUNIT z where z.unitno = a.unitid and z.enterorg = a.organid) as unitidName" +
					" from SYSAP_EMP a, SYSCA_ORGAN b " +
				" where a.empid = " + Common.sqlChar(userID) +
					" and a.organid = " + Common.sqlChar(organid) +
					" and a.isstop <> 'Y' " +
					" and b.organid = a.organid ";
			
			ResultSet rs = db.querySQL(sql);
			System.out.println("user 250 sql:"+ sql);
			if (rs.next()){
				user = new User();
				user.setUserID(Common.checkGet(rs.getString("empID")));
				user.setPassword(Common.checkGet(rs.getString("empPWD")));
				user.setUserName(Common.checkGet(rs.getString("empName")));
				user.setOrganID(Common.checkGet(rs.getString("organID")));
				user.setOrganName(Common.checkGet(rs.getString("organAName")));
				user.setUnitID(Common.checkGet(rs.getString("unitID")));
				user.setUnitName(Common.checkGet(rs.getString("unitidName")));
				user.setGroupID(Common.checkGet(rs.getString("groupID")));
				user.setIsOrganManager(Common.checkGet(rs.getString("isOrganManager")));
				user.setIsAdminManager(Common.checkGet(rs.getString("isAdminManager")));
				if (user.getIsAdminManager().equals("Y") && user.getIsManager().equals("Y")) {
				} else {
					user.setPermission(user.getPermission(Common.checkGet(rs.getString("groupID"))));
				}
				user.setRoleid(Common.checkGet(rs.getString("roleid")));
				user.setKeeperno(Common.checkGet(rs.getString("keeperNo")));
			}						
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			db.closeAll();
		}			
		return user;
	}

}

