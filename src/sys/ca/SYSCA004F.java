/*
*<br>程式目的：保管使用人資料
*<br>程式代號：sysca004f
*<br>程式日期：0950321
*<br>程式作者：clive.chang
*<br>--------------------------------------------------------
*<br>修改作者　　修改日期　　　修改目的
*<br>--------------------------------------------------------
*/

package sys.ca;

import java.sql.ResultSet;
import java.util.ArrayList;

import util.*;

public class SYSCA004F extends SuperBean{

	String isOrganManager;
	String isAdminManager;
	public String getIsOrganManager() { return checkGet(isOrganManager); }
	public void setIsOrganManager(String s) { isOrganManager = checkSet(s); }
	public String getIsAdminManager() { return checkGet(isAdminManager); }
	public void setIsAdminManager(String s) { isAdminManager = checkSet(s); }   
	
private String enterOrg;
private String enterOrgName;
private String keeperNo;
private String keeperName;
private String incumbencyYN;
private String notes;
private String editID;
private String editDate;
private String editTime;

public String getEnterOrg() {return checkGet(enterOrg);}
public void setEnterOrg(String enterOrg) {this.enterOrg = checkSet(enterOrg);}
public String getEnterOrgName() {return checkGet(enterOrgName);}
public void setEnterOrgName(String enterOrgName) {this.enterOrgName = checkSet(enterOrgName);}
public String getKeeperNo() {return checkGet(keeperNo);}
public void setKeeperNo(String keeperNo) {this.keeperNo = checkSet(keeperNo);}
public String getKeeperName() {return checkGet(keeperName);}
public void setKeeperName(String keeperName) {this.keeperName = checkSet(keeperName);}
public String getIncumbencyYN() {return checkGet(incumbencyYN);}
public void setIncumbencyYN(String incumbencyYN) {this.incumbencyYN = checkSet(incumbencyYN);}
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
private String q_keeperNo;
private String q_keeperName;
private String q_incumbencyYN;

public String getQ_enterOrg() {return checkGet(q_enterOrg);}
public void setQ_enterOrg(String q_enterOrg) {this.q_enterOrg = checkSet(q_enterOrg);}
public String getQ_enterOrgName() {return checkGet(q_enterOrgName);}
public void setQ_enterOrgName(String q_enterOrgName) {this.q_enterOrgName = checkSet(q_enterOrgName);}
public String getQ_keeperNo() {return checkGet(q_keeperNo);}
public void setQ_keeperNo(String q_keeperNo) {this.q_keeperNo = checkSet(q_keeperNo);}
public String getQ_keeperName() {return checkGet(q_keeperName);}
public void setQ_keeperName(String q_keeperName) {this.q_keeperName = checkSet(q_keeperName);}
public String getQ_incumbencyYN() {return checkGet(q_incumbencyYN);}
public void setQ_incumbencyYN(String q_incumbencyYN) {this.q_incumbencyYN = checkSet(q_incumbencyYN);}


private String roleid;
private String organID;
public String getRoleid() {return checkGet(roleid);}
public void setRoleid(String roleid) {this.roleid = checkSet(roleid);}
public String getOrganID() {return checkGet(organID);}
public void setOrganID(String organID) {this.organID = checkSet(organID);}

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
 	checkSQLArray[0][0]=" select count(*) as checkresult from UNTMP_KEEPER where 1=1 " + 
		" and enterorg = " + Common.sqlChar(enterOrg) + 
		" and keeperno = " + Common.sqlChar(keeperNo) + 
		"";
	checkSQLArray[0][1]=">";
	checkSQLArray[0][2]="0";
	checkSQLArray[0][3]="該筆資料己重複，請重新輸入！";
	return checkSQLArray;
}


//傳回insert sql
protected String[] getInsertSQL(){
	String[] execSQLArray = new String[1];
	execSQLArray[0]=" insert into UNTMP_KEEPER(" +
			" enterorg," +
			" keeperno," +
			" keepername," +
			" incumbencyyn," +
			" notes," +
			" editid," +
			" editdate," +
			" edittime" +
		" )Values(" +
			Common.sqlChar(enterOrg) + "," +
			Common.sqlChar(keeperNo) + "," +
            "N" +	//在 SQL Server 中處理 Unicode 字串常數時，必需為所有的 Unicode 字串加上前置詞 N
			Common.sqlChar(keeperName) + "," +
			Common.sqlChar(incumbencyYN) + "," +
			Common.sqlChar(notes) + "," +
			Common.sqlChar(editID) + "," +
			Common.sqlChar(editDate) + "," +
			Common.sqlChar(editTime) + ")";
	return execSQLArray;
}

//傳回update sql
protected String[] getUpdateSQL(){
	String[] execSQLArray = new String[1];
	execSQLArray[0]=" update UNTMP_KEEPER set " +
			" enterorg = " + Common.sqlChar(enterOrg) + "," +
			" keeperno = " + Common.sqlChar(keeperNo) + "," +
			" keepername = " + "N" + Common.sqlChar(keeperName) + "," +
			" incumbencyyn = " + Common.sqlChar(incumbencyYN) + "," +
			" notes = " + Common.sqlChar(notes) + "," +
			" editid = " + Common.sqlChar(editID) + "," +
			" editdate = " + Common.sqlChar(editDate) + "," +
			" edittime = " + Common.sqlChar(editTime) +  
		" where 1=1 " + 
			" and enterorg = " + Common.sqlChar(enterOrg) +
			" and keeperno = " + Common.sqlChar(keeperNo) +
			"";
	return execSQLArray;
}

//protected String[][] getDeleteCheckSQL(){
//	String[][] checkSQLArray = new String[1][4];
//	String checkSQL=" select count(*) as checkresult from (" + 
//			" (select distinct originalkeepunit,originalkeeper from UNTMP_MOVABLEDETAIL where enterorg="+ Common.sqlChar(enterOrg) +" and originalkeepunit="+ Common.sqlChar(unitNo) +" and originalkeeper="+ Common.sqlChar(keeperNo) +" )"+
//			" union(select distinct originaluseunit,originaluser from UNTMP_MOVABLEDETAIL where enterorg="+ Common.sqlChar(enterOrg) +" and U="+ Common.sqlChar(unitNo) +" and originaluser="+ Common.sqlChar(keeperNo) +" )"+
//			" union(select distinct keepunit,keeper from UNTMP_MOVABLEDETAIL where enterorg="+ Common.sqlChar(enterOrg) +" and keepunit="+ Common.sqlChar(unitNo) +" and keeper="+ Common.sqlChar(keeperNo) +")"+
//			" union(select distinct useunit,userno from UNTMP_MOVABLEDETAIL where enterorg="+ Common.sqlChar(enterOrg) +" and useunit="+ Common.sqlChar(unitNo) +" and userno="+ Common.sqlChar(keeperNo) +")"+
//			" union(select distinct oldkeepunit,oldkeeper from UNTMP_MOVEDETAIL where enterorg="+ Common.sqlChar(enterOrg) +" and oldkeepunit="+ Common.sqlChar(unitNo) +" and oldkeeper="+ Common.sqlChar(keeperNo) +")"+
//			" union(select distinct olduseunit,olduserno from UNTMP_MOVEDETAIL where enterorg="+ Common.sqlChar(enterOrg) +" and olduseunit="+ Common.sqlChar(unitNo) +" and olduserno="+ Common.sqlChar(keeperNo) +")"+
//			" union(select distinct newkeepunit,newkeeper from UNTMP_MOVEDETAIL where enterorg="+ Common.sqlChar(enterOrg) +" and newkeepunit="+ Common.sqlChar(unitNo) +" and newkeeper="+ Common.sqlChar(keeperNo) +")"+
//			" union(select distinct newuseunit,newuserno from UNTMP_MOVEDETAIL where enterorg="+ Common.sqlChar(enterOrg) +" and newuseunit="+ Common.sqlChar(unitNo) +" and newuserno="+ Common.sqlChar(keeperNo) +")"+
//			" union(select distinct keepunit,keeper from UNTMP_REDUCEDETAIL where enterorg="+ Common.sqlChar(enterOrg) +" and keepunit="+ Common.sqlChar(unitNo) +" and keeper="+ Common.sqlChar(keeperNo) +")"+
//			" union(select distinct useunit,userno from UNTMP_REDUCEDETAIL where enterorg="+ Common.sqlChar(enterOrg) +" and useunit="+ Common.sqlChar(unitNo) +" and userno="+ Common.sqlChar(keeperNo) +")"+
//			" union(select distinct keepunit,keeper from UNTMP_DEALDETAIL where enterorg="+ Common.sqlChar(enterOrg) +" and keepunit="+ Common.sqlChar(unitNo) +" and keeper="+ Common.sqlChar(keeperNo) +")"+
//			" union(select distinct useunit,userno from UNTMP_DEALDETAIL where enterorg="+ Common.sqlChar(enterOrg) +" and useunit="+ Common.sqlChar(unitNo) +" and userno="+ Common.sqlChar(keeperNo) +")"+
//			" union(select distinct keepunit,keeper from UNTMP_ADJUSTDETAIL where enterorg="+ Common.sqlChar(enterOrg) +" and keepunit="+ Common.sqlChar(unitNo) +" and keeper="+ Common.sqlChar(keeperNo) +")"+
//			" union(select distinct useunit,userno from UNTMP_ADJUSTDETAIL where enterorg="+ Common.sqlChar(enterOrg) +" and useunit="+ Common.sqlChar(unitNo) +" and userno="+ Common.sqlChar(keeperNo) +")"+
//			" union(select distinct originalkeepunit,originalkeeper from UNTNE_NONEXPDETAIL where enterorg="+ Common.sqlChar(enterOrg) +" and originalkeepunit="+ Common.sqlChar(unitNo) +" and originalkeeper="+ Common.sqlChar(keeperNo) +")"+
//			" union(select distinct originaluseunit,originaluser from UNTNE_NONEXPDETAIL where enterorg="+ Common.sqlChar(enterOrg) +" and originaluseunit="+ Common.sqlChar(unitNo) +" and originaluser="+ Common.sqlChar(keeperNo) +")"+
//			" union(select distinct keepunit,keeper from UNTNE_NONEXPDETAIL where enterorg="+ Common.sqlChar(enterOrg) +" and keepunit="+ Common.sqlChar(unitNo) +" and keeper="+ Common.sqlChar(keeperNo) +")"+
//			" union(select distinct useunit,userno from UNTNE_NONEXPDETAIL where enterorg="+ Common.sqlChar(enterOrg) +" and useunit="+ Common.sqlChar(unitNo) +" and userno="+ Common.sqlChar(keeperNo) +")"+
//			" union(select distinct oldkeepunit,oldkeeper from UNTNE_MOVEDETAIL where enterorg="+ Common.sqlChar(enterOrg) +" and oldkeepunit="+ Common.sqlChar(unitNo) +" and oldkeeper="+ Common.sqlChar(keeperNo) +")"+
//			" union(select distinct olduseunit,olduserno from UNTNE_MOVEDETAIL where enterorg="+ Common.sqlChar(enterOrg) +" and olduseunit="+ Common.sqlChar(unitNo) +" and olduserno="+ Common.sqlChar(keeperNo) +")"+
//			" union(select distinct newkeepunit,newkeeper from UNTNE_MOVEDETAIL where enterorg="+ Common.sqlChar(enterOrg) +" and newkeepunit="+ Common.sqlChar(unitNo) +" and newkeeper="+ Common.sqlChar(keeperNo) +")"+
//			" union(select distinct newuseunit,newuserno from UNTNE_MOVEDETAIL where enterorg="+ Common.sqlChar(enterOrg) +" and newuseunit="+ Common.sqlChar(unitNo) +" and newuserno="+ Common.sqlChar(keeperNo) +")"+
//			" union(select distinct keepunit,keeper from UNTNE_REDUCEDETAIL where enterorg="+ Common.sqlChar(enterOrg) +" and keepunit="+ Common.sqlChar(unitNo) +" and keeper="+ Common.sqlChar(keeperNo) +")"+
//			" union(select distinct useunit,userno from UNTNE_REDUCEDETAIL where enterorg="+ Common.sqlChar(enterOrg) +" and useunit="+ Common.sqlChar(unitNo) +" and userno="+ Common.sqlChar(keeperNo) +")"+
//			" union(select distinct keepunit,keeper from UNTNE_DEALDETAIL where enterorg="+ Common.sqlChar(enterOrg) +" and keepunit="+ Common.sqlChar(unitNo) +" and keeper="+ Common.sqlChar(keeperNo) +")"+
//			" union(select distinct useunit,userno from UNTNE_DEALDETAIL where enterorg="+ Common.sqlChar(enterOrg) +" and useunit="+ Common.sqlChar(unitNo) +" and userno="+ Common.sqlChar(keeperNo) +")"+
//			" union(select distinct keepunit,keeper from UNTNE_ADJUSTDETAIL where enterorg="+ Common.sqlChar(enterOrg) +" and keepunit="+ Common.sqlChar(unitNo) +" and keeper="+ Common.sqlChar(keeperNo) +")"+
//			" union(select distinct useunit,userno from UNTNE_ADJUSTDETAIL where enterorg="+ Common.sqlChar(enterOrg) +" and useunit="+ Common.sqlChar(unitNo) +" and userno="+ Common.sqlChar(keeperNo) +")"+
//			" union(select distinct originalkeepunit,originalkeeper from UNTSO_PCSOFTWARE where enterorg="+ Common.sqlChar(enterOrg) +" and originalkeepunit="+ Common.sqlChar(unitNo) +" and originalkeeper="+ Common.sqlChar(keeperNo) +")"+
//			" union(select distinct originaluseunit,originaluser from UNTSO_PCSOFTWARE where enterorg="+ Common.sqlChar(enterOrg) +" and originaluseunit="+ Common.sqlChar(unitNo) +" and originaluser="+ Common.sqlChar(keeperNo) +")"+
//			" union(select distinct keepunit,keeper from UNTSO_PCSOFTWARE where enterorg="+ Common.sqlChar(enterOrg) +" and keepunit="+ Common.sqlChar(unitNo) +" and keeper="+ Common.sqlChar(keeperNo) +")"+
//			" union(select distinct useunit,userno from UNTSO_PCSOFTWARE where enterorg="+ Common.sqlChar(enterOrg) +" and useunit="+ Common.sqlChar(unitNo) +" and userno="+ Common.sqlChar(keeperNo) +")"+
//			" union(select distinct keepunit,keeper from UNTSO_SOFTCARE where enterorg="+ Common.sqlChar(enterOrg) +" and keepunit="+ Common.sqlChar(unitNo) +" and keeper="+ Common.sqlChar(keeperNo) +")"+
//			" union(select distinct useunit,userno from UNTSO_SOFTCARE where enterorg="+ Common.sqlChar(enterOrg) +" and useunit="+ Common.sqlChar(unitNo) +" and userno="+ Common.sqlChar(keeperNo) +")"+
//			" ) b " + 
//			"";
//	checkSQLArray[0][0]=checkSQL;
//	checkSQLArray[0][1]="=";
//	checkSQLArray[0][2]="1";
//	checkSQLArray[0][3]="該資料已使用過，不可刪除！";
//	return checkSQLArray;
//}	

//傳回delete sql
protected String[] getDeleteSQL(){
	String[] execSQLArray = new String[1];
	execSQLArray[0]=" delete UNTMP_Keeper where 1=1 " +
			" and enterOrg = " + Common.sqlChar(enterOrg) +
			" and keeperNo = " + Common.sqlChar(keeperNo) +
			"";
	System.out.println(execSQLArray[0]);
	return execSQLArray;
}


/**
 * <br>
 * <br>目的：依主鍵查詢單一資料
 * <br>參數：無
 * <br>傳回：傳回本物件
*/

public SYSCA004F  queryOne() throws Exception{
	Database db = new Database();
	SYSCA004F obj = this;
	try {
		String sql = " select "+
				" a.enterOrg," +
				" (select z.organsname from SYSCA_ORGAN z where z.organid = a.enterOrg) as enterOrgName," +
				" a.keeperNo, a.keeperName, a.incumbencyYN, a.notes, a.editID," +
				" a.editDate, a.editTime" +
			" from UNTMP_KEEPER a where 1=1 " +
				" and a.enterorg = " + Common.sqlChar(enterOrg) +
				" and a.keeperno = " + Common.sqlChar(keeperNo) +
				"";
		ResultSet rs = db.querySQL(sql);
		if (rs.next()){
			obj.setEnterOrg(rs.getString("enterOrg"));
			obj.setEnterOrgName(rs.getString("enterOrgName"));
			obj.setKeeperNo(rs.getString("keeperNo"));
			obj.setKeeperName(rs.getString("keeperName"));
			obj.setIncumbencyYN(rs.getString("incumbencyYN"));
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
		String sql=" select a.enterorg, a.keeperno, a.keepername, a.notes ,(case a.incumbencyyn when 'Y' then '是' else '否' end) as incumbencyyn "+
						" from UNTMP_KEEPER a where 1=1" +
						"";		
		
//		if (!"".equals(getQ_enterOrg()))
//			sql+=" and a.enterorg = " + Common.sqlChar(getQ_enterOrg()) ;
		if (!"".equals(getQ_keeperNo()))
			sql+=" and a.keeperno = " + Common.sqlChar(getQ_keeperNo()) ;
		if (!"".equals(getQ_keeperName()))
			sql+=" and a.keepername like N" + Common.sqlChar("%" + getQ_keeperName() + "%") ;
		if (!"".equals(getQ_incumbencyYN()))
			sql+=" and a.incumbencyyn = " + Common.sqlChar(getQ_incumbencyYN()) ;
		
		if ("Y".equals(this.getIsAdminManager())){
			if ("".equals(getQ_enterOrg())) {
				sql+=" and a.enterorg = " + Common.sqlChar(getOrganID()) ;
			} else {
				sql+=" and a.enterorg = " + Common.sqlChar(getQ_enterOrg()) ;
			}
		}else{
			sql+=" and a.enterorg = " + Common.sqlChar(getOrganID()) ;
		}
		
		
		ResultSet rs = db.querySQL_NoChange(sql);
		while (rs.next()){
			counter++;
			String rowArray[]=new String[5];
			rowArray[0]=Common.get(rs.getString("enterOrg")); 
			rowArray[1]=Common.get(rs.getString("keeperNo")); 
			rowArray[2]=Common.get(rs.getString("keeperName")); 
			rowArray[3]=Common.get(rs.getString("incumbencyyn")); 
			rowArray[4]=Common.get(rs.getString("notes")); 
			objList.add(rowArray);
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


