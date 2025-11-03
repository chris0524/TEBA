


/*
*<br>程式目的：群組資料管理
*<br>程式代號：sysap002f
*<br>程式日期：0940701
*<br>程式作者：Dennis Chen
*<br>--------------------------------------------------------
*<br>修改作者　　修改日期　　　修改目的
*<br>--------------------------------------------------------
*/

package sys.ap;

import java.sql.ResultSet;
import java.util.ArrayList;
import util.*;

public class SYSAP002F extends SuperBean{

String organID;
String organIDName;
String groupID;
String groupName;
String memo;

String q_organID;
String q_organIDName;
String q_groupID;
String q_groupName;

public String getGroupID(){ return checkGet(groupID); }
public void setGroupID(String s){ groupID=checkSet(s); }
public String getGroupName(){ return checkGet(groupName); }
public void setGroupName(String s){ groupName=checkSet(s); }
public String getMemo(){ return checkGet(memo); }
public void setMemo(String s){ memo=checkSet(s); }

public String getQ_groupID(){ return checkGet(q_groupID); }
public void setQ_groupID(String s){ q_groupID=checkSet(s); }
public String getQ_groupName(){ return checkGet(q_groupName); }
public void setQ_groupName(String s){ q_groupName=checkSet(s); }

public String getOrganID() {return checkGet(organID);}
public void setOrganID(String organID) {this.organID = checkSet(organID);}
public String getQ_organID() {return checkGet(q_organID);}
public void setQ_organID(String q_organID) {this.q_organID = checkSet(q_organID);}
public String getOrganIDName() {return checkGet(organIDName);}
public void setOrganIDName(String organIDName) {this.organIDName = checkSet(organIDName);}
public String getQ_organIDName() {return checkGet(q_organIDName);}
public void setQ_organIDName(String q_organIDName) {this.q_organIDName = checkSet(q_organIDName);}

private String roleid;
private String userOrganID;
private String isAdminManager;
public String getRoleid() {return checkGet(roleid);}
public void setRoleid(String roleid) {this.roleid = checkSet(roleid);}
public String getUserOrganID() {return checkGet(userOrganID);}
public void setUserOrganID(String userOrganID) {this.userOrganID = checkSet(userOrganID);}
public String getIsAdminManager() {return checkGet(isAdminManager);}
public void setIsAdminManager(String isAdminManager) {this.isAdminManager = checkSet(isAdminManager);}


//傳回執行insert前之檢查sql
protected String[][] getInsertCheckSQL(){
    String[][] checkSQLArray = new String[1][4];
    checkSQLArray[0][0]=" select count(*) as checkresult from SYSAP_GROUP where 1=1 " +
    	" and organid = " + Common.sqlChar(organID) +
        " and groupid = " + Common.sqlChar(groupID) + 
        "";
    checkSQLArray[0][1]=">";
    checkSQLArray[0][2]="0";
    checkSQLArray[0][3]="該筆資料己重複，請重新輸入！";
    return checkSQLArray;
}


//傳回insert sql
protected String[] getInsertSQL(){
    String[] execSQLArray = new String[1];
    execSQLArray[0]=" insert into SYSAP_GROUP(" +
    		" organid,"+
    		" groupid,"+
            " groupname,"+
            " memo,"+
            " editid,"+
            " editdate,"+
            " edittime"+
        " )Values(" +
        	Common.sqlChar(organID) + "," +
            Common.sqlChar(groupID) + "," +
            Common.sqlChar(groupName) + "," +
            Common.sqlChar(memo) + "," +
            Common.sqlChar(getEditID()) + "," +
            Common.sqlChar(getEditDate()) + "," +
            Common.sqlChar(getEditTime()) + ")" ;
    return execSQLArray;
}


//傳回update sql
protected String[] getUpdateSQL(){
    String[] execSQLArray = new String[1];
    execSQLArray[0]=" update SYSAP_GROUP set " +
            " groupname = " + Common.sqlChar(groupName) + "," +
            " memo = " + Common.sqlChar(memo) + "," +
            " editid = " + Common.sqlChar(getEditID()) + "," +
            " editdate = " + Common.sqlChar(getEditDate()) + "," +
            " edittime = " + Common.sqlChar(getEditTime()) + 
        " where 1=1 " + 
        	" and organid = " + Common.sqlChar(organID) +
            " and groupid = " + Common.sqlChar(groupID) +
            "";
    return execSQLArray;
}


//傳回delete sql
protected String[] getDeleteSQL(){
    String[] execSQLArray = new String[2];
    execSQLArray[0]=" delete SYSAP_GROUP where 1=1 " +
    		" and organid = " + Common.sqlChar(organID) +
    		" and groupid = " + Common.sqlChar(groupID) +
            "";
    execSQLArray[1]=" delete SYSAP_AUTHORITY where 1=1 " +
    		" and organid = " + Common.sqlChar(organID) +
    		" and groupid = " + Common.sqlChar(groupID) +
		    "";
    
    return execSQLArray;
}


/**
 * <br>
 * <br>目的：依主鍵查詢單一資料
 * <br>參數：無
 * <br>傳回：傳回本物件
*/

public SYSAP002F  queryOne() throws Exception{
    Database db = new Database();
    SYSAP002F obj = this;
    try {
        String sql=" select a.organID, (select z.organsname from SYSCA_ORGAN z where z.organid = a.organid) as organname, a.groupid, a.groupname, a.memo, a.editid, a.editdate, a.edittime  "+
            " from SYSAP_GROUP a where 1=1 " +
            " and a.organid = " + Common.sqlChar(organID) +
            " and a.groupid = " + Common.sqlChar(groupID) +
            "";
        ResultSet rs = db.querySQL(sql);
        if (rs.next()){
        	obj.setOrganID(rs.getString("organID"));
        	obj.setOrganIDName(rs.getString("organname"));
            obj.setGroupID(rs.getString("groupID"));
            obj.setGroupName(rs.getString("groupName"));
            obj.setMemo(rs.getString("memo"));
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

/**
 * <br>
 * <br>目的：依查詢欄位查詢多筆資料
 * <br>參數：無
 * <br>傳回：傳回ArrayList
*/

public ArrayList queryAll() throws Exception{
    Database db = new Database();
    ArrayList objList=new ArrayList();
    int counter=0;
    try {
        String sql=" select a.organID, (select z.organsname from SYSCA_ORGAN z where z.organid = a.organid) as organname, a.groupid, a.groupname, a.memo "+
            " from SYSAP_GROUP a where 1=1 ";
            	
        
	        if (!"".equals(getQ_organID()))
	            sql+=" and a.organID = " + Common.sqlChar(getQ_organID()) ;
            if (!"".equals(getQ_groupID()))
                sql+=" and a.groupid like " + Common.sqlChar(getQ_groupID()+"%") ;
            if (!"".equals(getQ_groupName()))
                sql+=" and a.groupname like " + Common.sqlChar("%"+getQ_groupName()+"%") ;
            
            
            if("Y".equals(this.getIsAdminManager())){
            	
            }else{
	            sql += " and a.organID = " + Common.sqlChar(getUserOrganID());	            
            }
            
        ResultSet rs = db.querySQL(sql+" order by a.organID, a.groupid, a.groupname ");
        while (rs.next()){
            counter++;
            String rowArray[]=new String[5];
            rowArray[0]=rs.getString("organID"); 
            rowArray[1]=rs.getString("organname");
            rowArray[2]=rs.getString("groupID"); 
            rowArray[3]=rs.getString("groupName"); 
            rowArray[4]=rs.getString("memo"); 
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
