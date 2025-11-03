
/*
*<br>程式目的：機關管理作業
*<br>程式代號：sysca002f
*<br>程式日期：0940603
*<br>程式作者：griffin
*<br>--------------------------------------------------------
*<br>修改作者　　修改日期　　　修改目的
*<br>--------------------------------------------------------
*/

package sys.ca;

import java.sql.ResultSet;
import java.util.ArrayList;
import util.*;

public class SYSCA002F extends QueryBean {


String organID;
String organAName;
String organSName;
String organManager;
String isManager;
String organTel;
String organFax;
String organEmail;
String memo;
String title;
String organSuperior;
String organSuperiorName;
String organSuperior1;
String organSuperior1Name;
String organSuperior2;
String organSuperior2Name;

String q_organID;
String q_organAName;
String q_organSuperior;
String q_isManager;

public String getOrganID(){ return checkGet(organID); }
public void setOrganID(String s){ organID=checkSet(s); }
public String getOrganAName(){ return checkGet(organAName); }
public void setOrganAName(String s){ organAName=checkSet(s); }
public String getOrganSName(){ return checkGet(organSName); }
public void setOrganSName(String s){ organSName=checkSet(s); }
public String getIsManager(){ return checkGet(isManager); }
public void setIsManager(String s){ isManager=checkSet(s); }
public String getOrganManager(){ return checkGet(organManager); }
public void setOrganManager(String s){ organManager=checkSet(s); }
public String getOrganTel(){ return checkGet(organTel); }
public void setOrganTel(String s){ organTel=checkSet(s); }
public String getOrganFax(){ return checkGet(organFax); }
public void setOrganFax(String s){ organFax=checkSet(s); }
public String getOrganEmail(){ return checkGet(organEmail); }
public void setOrganEmail(String s){ organEmail=checkSet(s); }
public String getMemo(){ return checkGet(memo); }
public void setMemo(String s){ memo=checkSet(s); }
public String getTitle(){ return checkGet(title); }
public void setTitle(String s){ title=checkSet(s); }
public String getOrganSuperior(){ return checkGet(organSuperior); }
public void setOrganSuperior(String s){ organSuperior=checkSet(s); }
public String getOrganSuperiorName(){ return checkGet(organSuperiorName); }
public void setOrganSuperiorName(String s){ organSuperiorName=checkSet(s); }

public String getOrganSuperior1() { return checkGet(organSuperior1); }
public void setOrganSuperior1(String organSuperior1) {this.organSuperior1 =checkSet(organSuperior1); }
public String getOrganSuperior1Name() { return checkGet(organSuperior1Name); }
public void setOrganSuperior1Name(String organSuperior1Name) {this.organSuperior1Name =checkSet(organSuperior1Name); }
public String getOrganSuperior2() { return checkGet(organSuperior2); }
public void setOrganSuperior2(String organSuperior2) {this.organSuperior2 =checkSet(organSuperior2); }
public String getOrganSuperior2Name() { return checkGet(organSuperior2Name); }
public void setOrganSuperior2Name(String organSuperior2Name) {this.organSuperior2Name =checkSet(organSuperior2Name); }

public String getQ_organID(){ return checkGet(q_organID); }
public void setQ_organID(String s){ q_organID=checkSet(s); }
public String getQ_organAName(){ return checkGet(q_organAName); }
public void setQ_organAName(String s){ q_organAName=checkSet(s); }
public String getQ_organSuperior(){ return checkGet(q_organSuperior); }
public void setQ_organSuperior(String s){ q_organSuperior=checkSet(s); }
public String getQ_isManager(){ return checkGet(q_isManager); }
public void setQ_isManager(String s){ q_isManager=checkSet(s); }


//傳回執行insert前之檢查sql
protected String[][] getInsertCheckSQL(){
	String[][] checkSQLArray = new String[1][4];
 	checkSQLArray[0][0]=" select count(*) as checkresult from SYSCA_ORGAN where 1=1 " + 
		" and organid = " + Common.sqlChar(organID) + 
		"";
	checkSQLArray[0][1]=">";
	checkSQLArray[0][2]="0";
	checkSQLArray[0][3]="該筆資料己重複，請重新輸入！";
	return checkSQLArray;
}


//傳回insert sql
protected String[] getInsertSQL(){
	String[] execSQLArray = new String[2];
	execSQLArray[0]=" insert into SYSCA_Organ(" +
			" organid,"+
			" organaname,"+
			" organsname,"+
			" ismanager,"+
			" organmanager,"+
			" organtel,"+
			" organfax,"+
			" organemail,"+
			" memo,"+
			" title,"+
			" organsuperior,"+
			" organsuperior1,"+
			" organsuperior2,"+
			" editid,"+
			" editdate,"+
			" edittime"+			
		" )Values(" +
			Common.sqlChar(organID) + "," +
			Common.sqlChar(organAName) + "," +
			Common.sqlChar(organSName) + "," +
			Common.sqlChar(isManager) + "," +
			Common.sqlChar(organManager) + "," +
			Common.sqlChar(organTel) + "," +
			Common.sqlChar(organFax) + "," +
			Common.sqlChar(organEmail) + "," +
			Common.sqlChar(memo) + "," +
			Common.sqlChar(title) + "," +
			Common.sqlChar(organSuperior) + "," +
			Common.sqlChar(organSuperior1) + "," +
			Common.sqlChar(organSuperior2) + "," +
			Common.sqlChar(getEditID()) + "," +
			Common.sqlChar(getEditDate()) + "," +
			Common.sqlChar(getEditTime()) + ")" ;
	
	execSQLArray[1] = "insert into UNTMP_DOC( enterorg ,docno ,docname ,notes ,editid ,editdate ,edittime )" 
    				+ " (select " + Common.sqlChar(organID)
    				+ "  ,docno ,docname ,notes ,'' ,'' ,'' from UNTMP_DOC where enterorg = 'KD99999999') "
    				+ "";
	return execSQLArray;
}


//傳回update sql
protected String[] getUpdateSQL(){
	String[] execSQLArray = new String[1];
	execSQLArray[0]=" update SYSCA_ORGAN set " +
			" organaname = " + Common.sqlChar(organAName) + "," +
			" organsname = " + Common.sqlChar(organSName) + "," +
			" ismanager = " + Common.sqlChar(isManager) + "," +
			" organmanager = " + Common.sqlChar(organManager) + "," +
			" organtel = " + Common.sqlChar(organTel) + "," +
			" organfax = " + Common.sqlChar(organFax) + "," +
			" organemail = " + Common.sqlChar(organEmail) + "," +
			" memo = " + Common.sqlChar(memo) + "," +
			" title = " + Common.sqlChar(title) + "," +
			" organsuperior = " + Common.sqlChar(organSuperior) + "," +
			" organsuperior1 = " + Common.sqlChar(organSuperior1) + "," +
			" organsuperior2 = " + Common.sqlChar(organSuperior2) + "," +
			" editid = " + Common.sqlChar(getEditID()) + "," +
			" editdate = " + Common.sqlChar(getEditDate()) + "," +
			" edittime = " + Common.sqlChar(getEditTime()) +   
		" where 1=1 " + 
			" and organid = " + Common.sqlChar(organID) +
			"";
	return execSQLArray;
}


//傳回delete sql
protected String[] getDeleteSQL(){
	String[] execSQLArray = new String[1];
	execSQLArray[0]=" delete SYSCA_ORGAN where 1=1 " +
			" and organid = " + Common.sqlChar(organID) +
			"";
	return execSQLArray;
}


/**
 * <br>
 * <br>目的：依主鍵查詢單一資料
 * <br>參數：無
 * <br>傳回：傳回本物件
*/

public SYSCA002F  queryOne() throws Exception{
	Database db = new Database();
	SYSCA002F obj = this;
	try {
		String sql=" select a.organid, a.organaname, a.organsname, a.ismanager, a.organtel, a.organfax, " +
				   " a.organemail, a.memo, a.organmanager, a.title, a.editid, a.editdate, a.edittime, " +
				   " a.organSuperior, " +
				   " (select b.organaname from SYSCA_ORGAN b where 1=1  and b.organID = a.organsuperior) as organSuperiorName," +
				   " a.organSuperior1, " +
				   " (select b.organaname from SYSCA_ORGAN b where 1=1  and b.organID = a.organsuperior1) as organSuperior1Name," +
				   " a.organSuperior2, " +
				   " (select b.organaname from SYSCA_ORGAN b where 1=1  and b.organID = a.organsuperior2) as organSuperior2Name" +
				   " from SYSCA_ORGAN a where 1=1 " +
			" and organid = " + Common.sqlChar(organID) +
			"";
		ResultSet rs = db.querySQL(sql);
		//System.out.println(sql);
		if (rs.next()){
			obj.setOrganID(rs.getString("organID"));
			obj.setOrganAName(rs.getString("organAName"));
			obj.setOrganSName(rs.getString("organSName"));
			obj.setIsManager(rs.getString("isManager"));
			obj.setOrganManager(rs.getString("organManager"));
			obj.setOrganTel(rs.getString("organTel"));
			obj.setOrganFax(rs.getString("organFax"));
			obj.setOrganEmail(rs.getString("organEmail"));
			obj.setOrganSuperior(rs.getString("organSuperior"));
			obj.setOrganSuperiorName(rs.getString("organSuperiorName"));
			obj.setOrganSuperior1(rs.getString("organSuperior1"));
			obj.setOrganSuperior1Name(rs.getString("organSuperior1Name"));
			obj.setOrganSuperior2(rs.getString("organSuperior2"));
			obj.setOrganSuperior2Name(rs.getString("organSuperior2Name"));
			obj.setMemo(rs.getString("memo"));
			obj.setTitle(rs.getString("title"));
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
	try {
		String sql=" select a.organid, a.organsname, a.ismanager, a.organmanager "+
			" from SYSCA_ORGAN a where 1=1 "; 
			if (!"".equals(getQ_organID()))
				sql+=" and a.organid like '" + getQ_organID() +"%' " ;
			if (!"".equals(getQ_organAName()))
				sql+=" and a.organaname like '%" + getQ_organAName()+"%' " ;
			if(!"".equals(getQ_organSuperior())){
				sql+=" and organsuperior = '" + getQ_organSuperior() +"'";
			} 
			if(!"".equals(getQ_isManager())){
				sql+=" and ismanager = '" + getQ_isManager() +"' ";
			}
			
		ResultSet rs = db.querySQL(sql + " order by organid", true);
		//System.out.println(sql);
		processCurrentPageAttribute(rs);
		if (getTotalRecord() > 0) {
			int count = getRecordStart();
			int end = getRecordEnd();
			do {
				if (count > end)
					break;
			
				String rowArray[]=new String[4];
				rowArray[0]=rs.getString("organID"); 
				rowArray[1]=rs.getString("organSName"); 
				rowArray[2]=rs.getString("isManager"); 
				rowArray[3]=rs.getString("organManager");
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

}
