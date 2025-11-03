
/*
*<br>程式目的：使用者資料權限管理
*<br>程式代號：sysap001f
*<br>程式日期：0950321
*<br>程式作者：clive.chang
*<br>--------------------------------------------------------
*<br>修改作者　　修改日期　　　修改目的
*<br>--------------------------------------------------------
*/

package sys.ap;

import java.sql.ResultSet;
import java.util.ArrayList;

import util.Common;
import util.Database;
import util.Datetime;
import util.QueryBean;
import TDlib_Simple.com.src.DBServerTools;


public class SYSAP001F extends QueryBean{


String empID;
String empPWD;
String empName;
String groupID;
String organID;
String organID2;
String organIDName;
String unitID;
String empTitle;
String empTel;
String empFax;
String empEmail;
String takeJobDate;
String isStop;
String isOrganManager;
String isAdminManager;
String managerName;
String managerTitle;
String managerTel;
String memo;
String roleid;
String keeperno;


String q_empID;
String q_empName;
String q_organID;
String q_organIDName;
String q_groupID;
String q_unitID;
String q_isStop;
String q_isAdminManager;

public String getEmpID(){ return checkGet(empID); }
public void setEmpID(String s){ empID=checkSet(s); }
public String getEmpPWD(){ return checkGet(empPWD); }
public void setEmpPWD(String s){ empPWD=checkSet(s); }
public String getEmpName(){ return checkGet(empName); }
public void setEmpName(String s){ empName=checkSet(s); }
public String getGroupID(){ return checkGet(groupID); }
public void setGroupID(String s){ groupID=checkSet(s); }
public String getOrganID(){ return checkGet(organID); }
public void setOrganID(String s){ organID=checkSet(s); }
public String getOrganID2(){ return checkGet(organID2); }
public void setOrganID2(String s){ organID2=checkSet(s); }
public String getOrganIDName(){ return checkGet(organIDName); }
public void setOrganIDName(String s){ organIDName=checkSet(s); }
public String getUnitID(){ return checkGet(unitID); }
public void setUnitID(String s){ unitID=checkSet(s); }
public String getEmpTitle(){ return checkGet(empTitle); }
public void setEmpTitle(String s){ empTitle=checkSet(s); }
public String getEmpTel(){ return checkGet(empTel); }
public void setEmpTel(String s){ empTel=checkSet(s); }
public String getEmpFax(){ return checkGet(empFax); }
public void setEmpFax(String s){ empFax=checkSet(s); }
public String getEmpEmail(){ return checkGet(empEmail); }
public void setEmpEmail(String s){ empEmail=checkSet(s); }
public String getTakeJobDate(){ return checkGet(takeJobDate); }
public void setTakeJobDate(String s){ takeJobDate=checkSet(s); }
public String getIsStop(){ return checkGet(isStop); }
public void setIsStop(String s){ isStop=checkSet(s); }
public String getIsOrganManager(){ return checkGet(isOrganManager); }
public void setIsOrganManager(String s){ isOrganManager=checkSet(s); }
public String getIsAdminManager(){ return checkGet(isAdminManager); }
public void setIsAdminManager(String s){ isAdminManager=checkSet(s); }
public String getManagerName(){ return checkGet(managerName); }
public void setManagerName(String s){ managerName=checkSet(s); }
public String getManagerTitle(){ return checkGet(managerTitle); }
public void setManagerTitle(String s){ managerTitle=checkSet(s); }
public String getManagerTel(){ return checkGet(managerTel); }
public void setManagerTel(String s){ managerTel=checkSet(s); }
public String getMemo(){ return checkGet(memo); }
public void setMemo(String s){ memo=checkSet(s); }
public String getRoleid() {return checkGet(roleid);}
public void setRoleid(String roleid) {this.roleid = checkSet(roleid);}
public String getKeeperno() {return checkGet(keeperno);}
public void setKeeperno(String keeperno) {this.keeperno = checkSet(keeperno);}
public String getQ_empID(){ return checkGet(q_empID); }
public void setQ_empID(String s){ q_empID=checkSet(s); }
public String getQ_empName(){ return checkGet(q_empName); }
public void setQ_empName(String s){ q_empName=checkSet(s); }
public String getQ_organID(){ return checkGet(q_organID); }
public void setQ_organID(String s){ q_organID=checkSet(s); }
public String getQ_organIDName(){ return checkGet(q_organIDName); }
public void setQ_organIDName(String s){ q_organIDName=checkSet(s); }
public String getQ_groupID(){ return checkGet(q_groupID); }
public void setQ_groupID(String s){ q_groupID=checkSet(s); }
public String getQ_isAdminManager(){ return checkGet(q_isAdminManager); }
public void setQ_isAdminManager(String s){ q_isAdminManager=checkSet(s); }
public String getQ_isStop(){ return checkGet(q_isStop); }
public void setQ_isStop(String s){ q_isStop=checkSet(s); }
public String getQ_unitID(){ return checkGet(q_unitID); }
public void setQ_unitID(String s){ q_unitID=checkSet(s); }


String userRoleid;
String userOrganID;
public String getUserRoleid() {return checkGet(userRoleid);}
public void setUserRoleid(String userRoleid) {this.userRoleid = checkSet(userRoleid);}
public String getUserOrganID() {return checkGet(userOrganID);}
public void setUserOrganID(String userOrganID) {this.userOrganID = checkSet(userOrganID);}

private String userIsAdminManager;
public String getUserIsAdminManager() {return checkGet(userIsAdminManager);}
public void setUserIsAdminManager(String userIsAdminManager) {this.userIsAdminManager = checkSet(userIsAdminManager);}

String q_roleid;
public String getQ_roleid() {return checkGet(q_roleid);}
public void setQ_roleid(String q_roleid) {this.q_roleid = checkSet(q_roleid);}

public void insert() throws Exception{
	Database db = new Database();	
	try {			
		if (!beforeExecCheck(getInsertCheckSQL())){
			setStateInsertError();
		}else{
			setEditID(getUserID());
			setEditDate(Datetime.getYYYMMDD());
			setEditTime(Datetime.getHHMMSS());	
			db.excuteSQL_NoChange(getInsertSQL());	//與SuperBean只差在這裡
			setStateInsertSuccess();
			setErrorMsg("新增完成");				
		}
	} catch (Exception e) {
		setErrorMsg("新增失敗");
		e.printStackTrace();
	} finally {
		db.closeAll();
	}			
}

public void update() throws Exception{
	Database db = new Database();
	try {
		if (!beforeExecCheck(getUpdateCheckSQL())){
			setStateUpdateError();
		}else{
			setEditID(getUserID());			
			setEditDate(Datetime.getYYYMMDD());
			setEditTime(Datetime.getHHMMSS());					
			db.excuteSQL_NoChange(getUpdateSQL()); //與SuperBean只差在這裡		
			setStateUpdateSuccess();
			setErrorMsg("修改完成");
		}
	} catch (Exception e) {
		setErrorMsg("修改失敗");
		e.printStackTrace();
	} finally {
		db.closeAll();
	}			
}	

//傳回執行insert前之檢查sql
protected String[][] getInsertCheckSQL(){
    String[][] checkSQLArray = new String[1][4];
    checkSQLArray[0][0]=" select count(*) as checkresult from SYSAP_EMP where 1=1 " + 
        " and empID = " + Common.sqlChar(empID) + 
        " and organID = " + Common.sqlChar(organID) + "";
    checkSQLArray[0][1]=">";
    checkSQLArray[0][2]="0";
    checkSQLArray[0][3]="該筆資料己重複，請重新輸入！";
    return checkSQLArray;
}


//傳回insert sql
protected String[] getInsertSQL(){
	if ("".equals(getEmpPWD()))	setEmpPWD(this.getEmpID());
	String str[] =groupID.split(";");
    String[] execSQLArray = new String[1];
    execSQLArray[0]=" insert into SYSAP_EMP(" +
    		" empid,"+
    		" emppwd,"+
    		" empname,"+
    		" groupid,"+
    		" organid,"+
    		" unitid,"+
    		" emptitle,"+
    		" emptel,"+
    		" empfax,"+
    		" empemail,"+
    		" takejobdate,"+
    		" isstop,"+
    		" isorganmanager,"+
    		" isadminmanager,"+
    		" managername,"+
    		" managertitle,"+
    		" managertel,"+
    		" memo,"+
    		" editid,"+
    		" editdate,"+
    		" edittime,"+
    		" roleid," +
    		" keeperno" +
        " )Values(" +
            Common.sqlChar(empID) + "," +
            Common.sqlChar(empPWD) + "," +
            "N" +	//在 SQL Server 中處理 Unicode 字串常數時，必需為所有的 Unicode 字串加上前置詞 N
            Common.sqlChar(empName) + "," +
            Common.sqlChar(str[0]) + "," +
            Common.sqlChar(organID) + "," +
            Common.sqlChar(unitID) + "," +
            Common.sqlChar(empTitle) + "," +
            Common.sqlChar(empTel) + "," +
            Common.sqlChar(empFax) + "," +
            Common.sqlChar(empEmail) + "," +
            Common.sqlChar(takeJobDate) + "," +
            Common.sqlChar(isStop) + "," +
            Common.sqlChar(isOrganManager) + "," +
            Common.sqlChar(isAdminManager) + "," +
            "N" +	//在 SQL Server 中處理 Unicode 字串常數時，必需為所有的 Unicode 字串加上前置詞 N
            Common.sqlChar(managerName) + "," +
            Common.sqlChar(managerTitle) + "," +
            Common.sqlChar(managerTel) + "," +
            Common.sqlChar(memo) + "," +
            Common.sqlChar(getEditID()) + "," +
            Common.sqlChar(getEditDate()) + "," +
            Common.sqlChar(getEditTime()) + "," +
    		Common.sqlChar(getRoleid()) + "," +		
            Common.sqlChar(getKeeperno()) + ")" ;
    return execSQLArray;
}


//傳回update sql
protected String[] getUpdateSQL(){
	String[] execSQLArray = new String[1];
	String str[] =groupID.split(";");
    execSQLArray[0]=" update SYSAP_EMP set " +
    		" empname = "+ "N" + Common.sqlChar(empName) + "," +
    		" groupid = " + Common.sqlChar(str[0]) + "," +
    		" organid = " + Common.sqlChar(organID) + "," +
    		" unitid = " + Common.sqlChar(unitID) + "," +
    		" emptitle = " + Common.sqlChar(empTitle) + "," +
    		" emptel = " + Common.sqlChar(empTel) + "," +
    		" empfax = " + Common.sqlChar(empFax) + "," +
    		" empemail = " + Common.sqlChar(empEmail) + "," +
    		" emppwd = " + Common.sqlChar(empPWD) + "," +
    		" takejobdate = " + Common.sqlChar(takeJobDate) + "," +
    		" isstop = " + Common.sqlChar(isStop) + "," +
    		" isorganmanager = " + Common.sqlChar(isOrganManager) + "," +
    		" isadminmanager = " + Common.sqlChar(isAdminManager) + "," +
    		" managername = "+ "N" + Common.sqlChar(managerName) + "," +
    		" managertitle = " + Common.sqlChar(managerTitle) + "," +
    		" managertel = " + Common.sqlChar(managerTel) + "," +
    		" memo = " + Common.sqlChar(memo) + "," +
    		" editid = " + Common.sqlChar(getEditID()) + "," +
    		" editdate = " + Common.sqlChar(getEditDate()) + "," +
    		" edittime = " + Common.sqlChar(getEditTime()) +  "," +
    		" roleid = " + Common.sqlChar(getRoleid()) +  "," +
    		" keeperno = " + Common.sqlChar(getKeeperno()) +
        " where 1=1 " + 
            " and empid = " + Common.sqlChar(empID) +
            " and organid = " + Common.sqlChar(organID2) +
            "";
    return execSQLArray;
}


//傳回update personal sql
protected String[] getUpdatePersonalSQL(){
  String[] execSQLArray = new String[1];
  execSQLArray[0]=" update SYSAP_EMP set " +
		  " empname = "+ "N" + Common.sqlChar(empName) + "," +
		  " unitid = " + Common.sqlChar(unitID) + "," +
		  " emptitle = " + Common.sqlChar(empTitle) + "," +
		  " emptel = " + Common.sqlChar(empTel) + "," +
		  " empfax = " + Common.sqlChar(empFax) + "," +
		  " empemail = " + Common.sqlChar(empEmail) + "," +
		  " emppwd = " + Common.sqlChar(empPWD) + "," +          
		  " takejobdate = " + Common.sqlChar(takeJobDate) + "," +
		  " managername = "+ "N" + Common.sqlChar(managerName) + "," +
		  " managertitle = " + Common.sqlChar(managerTitle) + "," +
		  " managertel = " + Common.sqlChar(managerTel) + "," +
		  " memo = " + Common.sqlChar(memo) + "," +
		  " editid = " + Common.sqlChar(getEditID()) + "," +
		  " editdate = " + Common.sqlChar(getEditDate()) + "," +
		  " edittime = " + Common.sqlChar(getEditTime()) + "," +
		  //" roleid = " + Common.sqlChar(getRoleid()) + "," +
		  " keeperno = " + Common.sqlChar(getKeeperno()) + 
      " where 1=1 " + 
          " and empid = " + Common.sqlChar(empID) +
          " and organid = " + Common.sqlChar(organID2) +
          "";
  return execSQLArray;
}

public void updatePersonal(){
	Database db = new Database();
	try {
		if (!beforeExecCheck(getUpdateCheckSQL())){
			setStateUpdateError();
		}else{
			setEditID(getEditID());
			setEditDate(Datetime.getYYYMMDD());
			setEditTime(Datetime.getHHMMSS());			
			db.excuteSQL_NoChange(getUpdatePersonalSQL());		
			setStateUpdateSuccess();
			setErrorMsg("修改完成");			
		}	
	} catch (Exception e) {
		e.printStackTrace();
	} finally {
		db.closeAll();
	}			
}	


//傳回delete sql
protected String[] getDeleteSQL(){
    String[] execSQLArray = new String[1];
    execSQLArray[0]=" delete SYSAP_EMP where 1=1 " +
            " and empid = " + Common.sqlChar(empID) +
            " and organID = " + Common.sqlChar(organID) +
            "";
    return execSQLArray;
}


/**
 * <br>
 * <br>目的：依主鍵查詢單一資料
 * <br>參數：無
 * <br>傳回：傳回本物件
*/

public SYSAP001F  queryOne() throws Exception{
    Database db = new Database();
    SYSAP001F obj = this;
    try {
    	String sql = "";
    	if (q_organID != null) {
    		 sql=" select a.empid, a.emppwd, a.empname, a.groupid, a.organid, a.unitid, a.emptitle, a.emptel, a.empfax, a.empemail, a.takejobdate, a.isstop, a.isorganmanager, a.isadminmanager, a.managername, a.managertitle, a.managertel, a.memo, a.editid, a.editdate, a.edittime, b.organsname, a.roleid, a.keeperno "+
    				" from SYSAP_EMP a, SYSCA_ORGAN b where 1=1 " +
    				" and a.empid = " + Common.sqlChar(empID) +
    				" and a.organid = " + Common.sqlChar(q_organID) +
    				" and b.organid = a.organid " +
    				"";
    	} else {
    		 sql=" select a.empid, a.emppwd, a.empname, a.groupid, a.organid, a.unitid, a.emptitle, a.emptel, a.empfax, a.empemail, a.takejobdate, a.isstop, a.isorganmanager, a.isadminmanager, a.managername, a.managertitle, a.managertel, a.memo, a.editid, a.editdate, a.edittime, b.organsname, a.roleid, a.keeperno "+
    				" from SYSAP_EMP a, SYSCA_ORGAN b where 1=1 " +
    				" and a.empid = " + Common.sqlChar(empID) +
    				" and a.organid = " + Common.sqlChar(userOrganID) +
    				" and b.organid = a.organid " +
    				"";
    	}
        ResultSet rs = db.querySQL(sql);
        //System.out.println("one: " +sql);
        if (rs.next()){
            obj.setEmpID(rs.getString("empID"));
            obj.setEmpPWD(rs.getString("empPWD"));
            obj.setEmpName(rs.getString("empName"));
            obj.setGroupID(rs.getString("groupID")+";"+rs.getString("organID"));
            obj.setOrganID(rs.getString("organID"));
            obj.setUnitID(rs.getString("unitID"));
            obj.setEmpTitle(rs.getString("empTitle"));
            obj.setEmpTel(rs.getString("empTel"));
            obj.setEmpFax(rs.getString("empFax"));
            obj.setEmpEmail(rs.getString("empEmail"));
            obj.setTakeJobDate(rs.getString("takeJobDate"));
            obj.setIsStop(rs.getString("isStop"));
            obj.setIsOrganManager(rs.getString("isOrganManager"));
            obj.setIsAdminManager(rs.getString("isAdminManager"));
            obj.setManagerName(rs.getString("managerName"));
            obj.setManagerTitle(rs.getString("managerTitle"));
            obj.setManagerTel(rs.getString("managerTel"));
            obj.setMemo(rs.getString("memo"));
            obj.setEditID(rs.getString("editID"));
            obj.setEditDate(rs.getString("editDate"));
            obj.setEditTime(rs.getString("editTime"));
            obj.setOrganIDName(rs.getString("organSName"));
            obj.setRoleid(rs.getString("roleid"));
            obj.setKeeperno(rs.getString("keeperno"));
        }
        setStateQueryOneSuccess();
    } catch (Exception e) {
        e.printStackTrace();
    } finally {
        db.closeAll();
    }
    return obj;
}

/**
 * <br>
 * <br>目的：依查詢欄位查詢多筆資料
 * <br>參數：無
 * <br>傳回：傳回ArrayList
*/

public ArrayList queryAll() throws Exception{
    Database db = new Database();
    ArrayList objList=new ArrayList();
    try {
        String sql=" select a.empid, a.empname, a.emptitle, a.emptel,(select organaname from SYSCA_ORGAN b where a.organid=b.organid) organidname"+
            " from SYSAP_EMP a where 1=1 "; 
            if (!"".equals(getQ_empID()))
                sql+=" and a.empid = " + Common.sqlChar(getQ_empID()) ;
            if (!"".equals(getQ_groupID()))
                sql+=" and a.groupid = " + Common.sqlChar(getQ_groupID()) ;            
            if (!"".equals(getQ_empName()))
                sql+=" and a.empname like N'%" + getQ_empName() +"%' ";
            if (!"".equals(getQ_organID()))
                sql+=" and a.organid = " + Common.sqlChar(getQ_organID()) ;
            if (!"".equals(getQ_isStop()))
                sql+=" and a.isstop = " + Common.sqlChar(getQ_isStop()) ;
            if (!"".equals(getQ_isAdminManager()))
                sql+=" and a.isadminmanager = " + Common.sqlChar(getQ_isAdminManager()) ;
            if (!"".equals(getQ_unitID()))
                sql+=" and a.unitid like '%" + getQ_unitID() + "%' ";      
            if (!"".equals(getQ_roleid()))
                sql+=" and a.roleid = '" + getQ_roleid() + "' ";
            
            if("Y".equals(this.getUserIsAdminManager())){
            	
            }else{
            	sql+=" and a.organid = " + Common.sqlChar(getUserOrganID()) ;                	
            }
            
            
        ResultSet rs = db.getScrollStatement().executeQuery(sql+" order by a.organid, a.empid ");
		processCurrentPageAttribute(rs);
		//System.out.println("All: " +sql);
		if (getTotalRecord() > 0) {
			int count = getRecordStart();
			int end = getRecordEnd();
			do {
				if (count > end)
					break;                
	            String rowArray[]=new String[5];
	            rowArray[0]=rs.getString("organIDName");
	            rowArray[1]=rs.getString("empID"); 
	            rowArray[2]=rs.getString("empName"); 
	            rowArray[3]=rs.getString("empTitle"); 
	            rowArray[4]=rs.getString("empTel"); 
	            
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

	public boolean checkAUTH(String groupID, String progID){
		String sql = "select auth from SYSAP_AUTHORITY " +
						"where groupid = '" + groupID + "' " + 
						"and progid = '" + progID + "'";
		DBServerTools dbt = new DBServerTools();
		dbt._setDatabase(new Database());
		if("M".equals(dbt._execSQL_asString(sql))){
			return true;
		}else{
			return false;
		}
	}

	
	public String getOption(String sql, String selectedOne, String organID) {
        String rtnStr = "<option value=''>請選擇</option>";
        Database db = new Database();
        try {
        	
        	boolean isAdminManager = false;
        	if("Y".equals(this.getUserIsAdminManager())){
        		isAdminManager = true;
        	}
        	
        	ResultSet rs = null;
        	if(isAdminManager){		
        		rs = db.querySQL(sql + " order by groupID");
        	
        	}else{			
        		rs = db.querySQL(sql + " and organid = '" + organID + "' order by groupID");
        	
        	}
        	
            while (rs.next()) {
                String id = rs.getString(1);
                String organid = rs.getString(2);
                String name = rs.getString(3);
                
       
                if(isAdminManager){
                	rtnStr = rtnStr + "<option value='" + id + "' ";

	                if (selectedOne!= null && selectedOne.equals(id) && organID.equals(organid)) {
	                    rtnStr = rtnStr + " selected ";
	                }
	                rtnStr = rtnStr + ">" + organid + "：" + name + "</option>";
	                
                }else{
	                rtnStr = rtnStr + "<option value='" + id + "' ";
	                if (selectedOne!= null && selectedOne.equals(id)) {
	                    rtnStr = rtnStr + " selected ";
	                }
	                rtnStr = rtnStr + ">" + name + "</option>";

                }
            }
        } catch (Exception ex) {
            rtnStr = "<option value=''>查詢錯誤</option>";
            ex.printStackTrace();
        } finally {
 			db.closeAll();
        }        
        return rtnStr;
    }
	
	
}
