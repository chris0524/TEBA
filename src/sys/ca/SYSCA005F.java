/*
*<br>程式目的：廠商資料
*<br>程式代號：sysca005f
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

public class SYSCA005F extends QueryBean {


String enterOrg;
String enterOrgName;
String storeNo;
String storeName;
String companyID;
String tel1;
String tel2;
String seller;
String fax;
String notes;

String q_enterOrg;
String q_enterOrgName;
String q_storeNo;
String q_storeName;
String q_tel1;
String q_tel2;
String q_seller;
String q_fax;
String q_notes;

public String getEnterOrg(){ return checkGet(enterOrg); }
public void setEnterOrg(String s){ enterOrg=checkSet(s); }
public String getEnterOrgName(){ return checkGet(enterOrgName); }
public void setEnterOrgName(String s){ enterOrgName=checkSet(s); }
public String getStoreNo(){ return checkGet(storeNo); }
public void setStoreNo(String s){ storeNo=checkSet(s); }
public String getStoreName(){ return checkGet(storeName); }
public void setStoreName(String s){ storeName=checkSet(s); }
public String getCompanyID(){ return checkGet(companyID); }
public void setCompanyID(String s){ companyID=checkSet(s); }
public String getTel1(){ return checkGet(tel1); }
public void setTel1(String s){ tel1=checkSet(s); }
public String getTel2(){ return checkGet(tel2); }
public void setTel2(String s){ tel2=checkSet(s); }
public String getSeller(){ return checkGet(seller); }
public void setSeller(String s){ seller=checkSet(s); }
public String getFax(){ return checkGet(fax); }
public void setFax(String s){ fax=checkSet(s); }
public String getNotes(){ return checkGet(notes); }
public void setNotes(String s){ notes=checkSet(s); }

public String getQ_enterOrg(){ return checkGet(q_enterOrg); }
public void setQ_enterOrg(String s){ q_enterOrg=checkSet(s); }
public String getQ_enterOrgName(){ return checkGet(q_enterOrgName); }
public void setQ_enterOrgName(String s){ q_enterOrgName=checkSet(s); }
public String getQ_storeNo(){ return checkGet(q_storeNo); }
public void setQ_storeNo(String s){ q_storeNo=checkSet(s); }
public String getQ_storeName(){ return checkGet(q_storeName); }
public void setQ_storeName(String s){ q_storeName=checkSet(s); }
public String getQ_tel1(){ return checkGet(q_tel1); }
public void setQ_tel1(String s){ q_tel1=checkSet(s); }
public String getQ_tel2(){ return checkGet(q_tel2); }
public void setQ_tel2(String s){ q_tel2=checkSet(s); }
public String getQ_seller(){ return checkGet(q_seller); }
public void setQ_seller(String s){ q_seller=checkSet(s); }
public String getQ_fax(){ return checkGet(q_fax); }
public void setQ_fax(String s){ q_fax=checkSet(s); }
public String getQ_notes(){ return checkGet(q_notes); }
public void setQ_notes(String s){ q_notes=checkSet(s); }


String isOrganManager;
String isAdminManager;
String organID;    
public String getOrganID() { return checkGet(organID); }
public void setOrganID(String s) { organID = checkSet(s); }
public String getIsOrganManager() { return checkGet(isOrganManager); }
public void setIsOrganManager(String s) { isOrganManager = checkSet(s); }
public String getIsAdminManager() { return checkGet(isAdminManager); }
public void setIsAdminManager(String s) { isAdminManager = checkSet(s); }   	

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
	/**
	Database db = new Database();
	try {
		String sql = "select max(storeNo)+1 as storeNo from UNTMP_Store where enterOrg="+Common.sqlChar(getEnterOrg());
		ResultSet rs = db.querySQL(sql);
		if (rs.next() && rs.getInt(1)>0) {
			setStoreNo(Common.formatFrontZero(rs.getString(1),4));
		} else {
			setStoreNo("0001");
		}
	} catch (Exception e) {
		e.printStackTrace();
	} finally {
		db.closeAll();
	}
	**/
	
	String[][] checkSQLArray = new String[2][4];
 	checkSQLArray[0][0]=" select count(*) as checkresult from UNTMP_STORE where 1=1 " + 
		" and enterorg = " + Common.sqlChar(enterOrg) + 
		" and storeno = " + Common.sqlChar(storeNo) + 
		"";
	checkSQLArray[0][1]=">";
	checkSQLArray[0][2]="0";
	checkSQLArray[0][3]="該筆廠商代碼己重複，請重新輸入！";
	
 	checkSQLArray[1][0]=" select count(*) as checkresult from UNTMP_STORE where 1=1 " + 
		" and enterorg = " + Common.sqlChar(enterOrg) + 
		" and storename = " + Common.sqlChar(storeName) + 
		"";
	checkSQLArray[1][1]=">";
	checkSQLArray[1][2]="0";
	checkSQLArray[1][3]="該筆廠商名稱己重複，請重新輸入！";	
	return checkSQLArray;
}


//傳回insert sql
protected String[] getInsertSQL(){
	String[] execSQLArray = new String[1];
	execSQLArray[0]=" insert into UNTMP_STORE(" +
			" enterorg,"+
			" storeno,"+
			" storename,"+
			" companyid,"+
			" tel1,"+
			" tel2,"+
			" seller,"+
			" fax,"+
			" notes,"+
			" editid,"+
			" editdate,"+
			" edittime"+
		" )Values(" +
			Common.sqlChar(enterOrg) + "," +
			Common.sqlChar(storeNo) + "," +
			"N" + Common.sqlChar(storeName) + "," +
			Common.sqlChar(companyID) + "," +
			Common.sqlChar(tel1) + "," +
			Common.sqlChar(tel2) + "," +
			Common.sqlChar(seller) + "," +
			Common.sqlChar(fax) + "," +
			Common.sqlChar(notes) + "," +
			Common.sqlChar(getEditID()) + "," +
			Common.sqlChar(getEditDate()) + "," +
			Common.sqlChar(getEditTime()) + ")" ;
	return execSQLArray;
}


//傳回執行insert前之檢查sql
protected String[][] getUpdateCheckSQL(){
	String[][] checkSQLArray = new String[1][4];
 	checkSQLArray[0][0]=" select count(*) as checkResult from UNTMP_STORE where 1=1 " + 
		" and enterorg = " + Common.sqlChar(enterOrg) + 
		" and storeno <> " + Common.sqlChar(storeNo) +		
		" and storename = " + Common.sqlChar(storeName) + 
		"";
	checkSQLArray[0][1]=">";
	checkSQLArray[0][2]="0";
	checkSQLArray[0][3]="該筆廠商名稱己重複，請重新輸入！";	
	return checkSQLArray;
}

//傳回update sql
protected String[] getUpdateSQL(){
	String[] execSQLArray = new String[1];
	execSQLArray[0]=" update UNTMP_STORE set " +
			" storename = " + "N" +  Common.sqlChar(storeName) + "," +
			" companyid = " + Common.sqlChar(companyID) + "," +
			" tel1 = " + Common.sqlChar(tel1) + "," +
			" tel2 = " + Common.sqlChar(tel2) + "," +
			" seller = " + Common.sqlChar(seller) + "," +
			" fax = " + Common.sqlChar(fax) + "," +
			" notes = " + Common.sqlChar(notes) + "," +
			" editid = " + Common.sqlChar(getEditID()) + "," +
			" editdate = " + Common.sqlChar(getEditDate()) + "," +
			" edittime = " + Common.sqlChar(getEditTime()) + 
		" where 1=1 " + 
			" and enterorg = " + Common.sqlChar(enterOrg) +
			" and storeno = " + Common.sqlChar(storeNo) +
			"";
	return execSQLArray;
}


//傳回delete sql
protected String[] getDeleteSQL(){
	String[] execSQLArray = new String[1];
	execSQLArray[0]=" delete UNTMP_STORE where 1=1 " +
			" and enterorg = " + Common.sqlChar(enterOrg) +
			" and storeno = " + Common.sqlChar(storeNo) +
			"";
	return execSQLArray;
}


/**
 * <br>
 * <br>目的：依主鍵查詢單一資料
 * <br>參數：無
 * <br>傳回：傳回本物件
*/

public SYSCA005F  queryOne() throws Exception{
	Database db = new Database();
	SYSCA005F obj = this;
	try {
		String sql=" select a.enterorg, b.organsname, a.storeno, a.storename, a.companyid, a.tel1, a.tel2, a.seller, a.fax, a.notes, a.editid, a.editdate, a.edittime  "+
			" from UNTMP_STORE a, SYSCA_ORGAN b where a.enterorg=b.organid " +
			" and a.enterorg = " + Common.sqlChar(enterOrg) +
			" and a.storeno = " + Common.sqlChar(storeNo) +
			"";
		ResultSet rs = db.querySQL(sql);
		if (rs.next()){
			obj.setEnterOrg(rs.getString("enterOrg"));
			obj.setEnterOrgName(rs.getString("organSName"));
			obj.setStoreNo(rs.getString("storeNo"));
			obj.setStoreName(rs.getString("storeName"));
			obj.setCompanyID(rs.getString("companyID"));
			obj.setTel1(rs.getString("tel1"));
			obj.setTel2(rs.getString("tel2"));
			obj.setSeller(rs.getString("seller"));
			obj.setFax(rs.getString("fax"));
			obj.setNotes(rs.getString("notes"));
			obj.setEditID(rs.getString("editID"));
			obj.setEditDate(rs.getString("editDate"));
			obj.setEditTime(rs.getString("editTime"));
		}
		rs.getStatement().close();
		rs.close();
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
		String sql=" select a.enterorg, a.storeno, a.storename, a.tel1, a.seller, a.fax "+
			" from UNTMP_STORE a where 1=1 "; 
			if (!"".equals(getQ_enterOrg())) {
				sql+=" and a.enterorg = " + Common.sqlChar(getQ_enterOrg()) ;
			} else {
				if (!getIsAdminManager().equalsIgnoreCase("Y")) {
					if ("Y".equals(getIsOrganManager())) {					
						sql += " and a.enterorg like '" + getOrganID().substring(0,5) + "%' ";					
					} else {
						sql+=" and a.enterorg = " + Common.sqlChar(getOrganID()) ;
					}
				}
			}
			if (!"".equals(getQ_storeNo()))
				sql+=" and a.storeno = " + Common.sqlChar(getQ_storeNo()) ;
			if (!"".equals(getQ_storeName()))
				sql+=" and a.storename like " + Common.sqlChar("%"+getQ_storeName()+"%") ;
			if (!"".equals(getQ_tel1()))
				sql+=" and a.tel1 like " + Common.sqlChar("%"+getQ_tel1()+"%") ;
			if (!"".equals(getQ_tel2()))
				sql+=" and a.tel2 like " + Common.sqlChar("%"+getQ_tel2()+"%") ;
			if (!"".equals(getQ_seller()))
				sql+=" and a.seller like " + Common.sqlChar("%"+getQ_seller()+"%") ;
			if (!"".equals(getQ_fax()))
				sql+=" and a.fax like " + Common.sqlChar("%"+getQ_fax()+"%") ;
			if (!"".equals(getQ_notes()))
				sql+=" and a.notes like " + Common.sqlChar("%"+getQ_notes()+"%") ;
		ResultSet rs = db.querySQL(sql+" order by storeno ", true);
		processCurrentPageAttribute(rs);
		if (getTotalRecord() > 0) {
			int count = getRecordStart();
			int end = getRecordEnd();
			do {
				if (count > end)
					break;
				String rowArray[]=new String[6];
				rowArray[0]=Common.get(rs.getString("enterOrg")); 
				rowArray[1]=Common.get(rs.getString("storeNo")); 
				rowArray[2]=Common.get(rs.getString("storeName")); 
				rowArray[3]=Common.get(rs.getString("tel1")); 
				rowArray[4]=Common.get(rs.getString("seller")); 
				rowArray[5]=Common.get(rs.getString("fax")); 
				objList.add(rowArray);
				count++;			
			} while (rs.next());
		}
		rs.getStatement().close();
		rs.close();		
		setStateQueryAllSuccess();
	} catch (Exception e) {
		e.printStackTrace();
	} finally {
		db.closeAll();
	}
	return objList;
}

}


