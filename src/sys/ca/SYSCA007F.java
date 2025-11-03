/*
*<br>程式目的：土地公告年月適用期間資料維護
*<br>程式代號：sysca007f
*<br>程式日期：0950619
*<br>程式作者：amanda
*<br>--------------------------------------------------------
*<br>修改作者　　修改日期　　　修改目的
*<br>--------------------------------------------------------
*/

package sys.ca;

import java.sql.ResultSet;
import java.util.ArrayList;
import util.*;

public class SYSCA007F extends SuperBean{


String bulletinKind;
String bulletinDate;
String suitDateS;
String suitDateE;

String q_bulletinKind;
String q_bulletinDate;

public String getBulletinKind(){ return checkGet(bulletinKind); }
public void setBulletinKind(String s){ bulletinKind=checkSet(s); }
public String getBulletinDate(){ return checkGet(bulletinDate); }
public void setBulletinDate(String s){ bulletinDate=checkSet(s); }
public String getSuitDateS(){ return checkGet(suitDateS); }
public void setSuitDateS(String s){ suitDateS=checkSet(s); }
public String getSuitDateE(){ return checkGet(suitDateE); }
public void setSuitDateE(String s){ suitDateE=checkSet(s); }

public String getQ_bulletinKind(){ return checkGet(q_bulletinKind); }
public void setQ_bulletinKind(String s){ q_bulletinKind=checkSet(s); }
public String getQ_bulletinDate(){ return checkGet(q_bulletinDate); }
public void setQ_bulletinDate(String s){ q_bulletinDate=checkSet(s); }


//傳回執行insert前之檢查sql
protected String[][] getInsertCheckSQL(){
	String[][] checkSQLArray = new String[1][4];
 	checkSQLArray[0][0]=" select count(*) as checkresult from UNTLA_BULLETINDATE where 1=1 " + 
		" and bulletinkind = " + Common.sqlChar(bulletinKind) + 
		" and bulletindate = " + Common.sqlChar(Datetime.changeTaiwanYYMM(bulletinDate,"2" )) + 
		"";
	checkSQLArray[0][1]=">";
	checkSQLArray[0][2]="0";
	checkSQLArray[0][3]="該筆資料己重複，請重新輸入！";
	return checkSQLArray;
}


//傳回insert sql
protected String[] getInsertSQL(){
	String[] execSQLArray = new String[1];
	execSQLArray[0]=" insert into UNTLA_BULLETINDATE(" +
			" bulletinkind,"+
			" bulletindate,"+
			" suitdates,"+
			" suitdatee,"+
			" editid,"+
			" editdate,"+
			" edittime"+
		" )Values(" +
			Common.sqlChar(bulletinKind) + "," +
			Common.sqlChar(Datetime.changeTaiwanYYMM(bulletinDate,"2" )) + "," +
			Common.sqlChar(Datetime.changeTaiwanYYMMDD(suitDateS,"2" )) + "," +
			Common.sqlChar(Datetime.changeTaiwanYYMMDD(suitDateE,"2" )) + "," +
			Common.sqlChar(getEditID()) + "," +
			Common.sqlChar(Datetime.changeTaiwanYYMMDD(getEditDate(),"2" )) + "," +
			Common.sqlChar(getEditTime()) + ")" ;
	return execSQLArray;
}


//傳回update sql
protected String[] getUpdateSQL(){
	String[] execSQLArray = new String[1];
	execSQLArray[0]=" update UNTLA_BULLETINDATE set " +
			" suitdates = " + Common.sqlChar(Datetime.changeTaiwanYYMMDD(suitDateS,"2" )) + "," +
			" suitdatee = " + Common.sqlChar(Datetime.changeTaiwanYYMMDD(suitDateE,"2" )) + "," +
			" editid = " + Common.sqlChar(getEditID()) + "," +
			" editdate = " + Common.sqlChar(Datetime.changeTaiwanYYMMDD(getEditDate(),"2" )) + "," +
			" edittime = " + Common.sqlChar(getEditTime()) + 
		" where 1=1 " + 
			" and bulletinkind = " + Common.sqlChar(bulletinKind) +
			" and bulletindate = " + Common.sqlChar(Datetime.changeTaiwanYYMM(bulletinDate,"2" )) +
			"";
	return execSQLArray;
}


//傳回delete sql
protected String[] getDeleteSQL(){
	String[] execSQLArray = new String[1];
	execSQLArray[0]=" delete UNTLA_BULLETINDATE where 1=1 " +
			" and bulletinkind = " + Common.sqlChar(bulletinKind) +
			" and bulletindate = " + Common.sqlChar(Datetime.changeTaiwanYYMM(bulletinDate,"2" )) +
			"";
	return execSQLArray;
}


/**
 * <br>
 * <br>目的：依主鍵查詢單一資料
 * <br>參數：無
 * <br>傳回：傳回本物件
*/

public SYSCA007F  queryOne() throws Exception{
	Database db = new Database();
	SYSCA007F obj = this;
	try {
		String sql=" select bulletinkind, bulletindate, suitdates, suitdatee, editid, editdate, edittime  "+
			" from UNTLA_BULLETINDATE where 1=1 " +
			" and bulletinkind = " + Common.sqlChar(bulletinKind) +
			" and bulletindate = " + Common.sqlChar(Datetime.changeTaiwanYYMM(bulletinDate,"2" )) +
			"";
		ResultSet rs = db.querySQL(sql);
		if (rs.next()){
			obj.setBulletinKind(rs.getString("bulletinKind"));
			obj.setBulletinDate(Datetime.changeTaiwanYYMM(rs.getString("bulletinDate"),"1" ));
			obj.setSuitDateS(Datetime.changeTaiwanYYMMDD(rs.getString("suitDateS"),"1" ));
			obj.setSuitDateE(Datetime.changeTaiwanYYMMDD(rs.getString("suitDateE"),"1" ));
			obj.setEditID(rs.getString("editID"));
			obj.setEditDate(Datetime.changeTaiwanYYMMDD(rs.getString("editDate"),"1" ));
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
		String sql=" select a.bulletinkind, a.bulletindate, a.suitdates, a.suitdatee, case a.bulletinkind when '1' then '公告地價' when '2' then '公告現值' else '' end as bulletinKindName  " +
				"from UNTLA_BULLETINDATE a where 1=1"; 
			if (!"".equals(getQ_bulletinKind()))
				sql+=" and a.bulletinkind = " + Common.sqlChar(getQ_bulletinKind()) ;
			if (!"".equals(getQ_bulletinDate()))
				sql+=" and a.bulletindate = " + Common.sqlChar(Datetime.changeTaiwanYYMM(getQ_bulletinDate(),"2" )) ;
			
		ResultSet rs = db.querySQL(sql + " order by bulletinkind, bulletindate");
		while (rs.next()){
			counter++;
			String rowArray[]=new String[5];
			rowArray[0]=Common.get(rs.getString("bulletinKind")); 
			rowArray[1]=Common.get(rs.getString("bulletinKindName"));
			rowArray[2]=Common.get(Datetime.changeTaiwanYYMM(rs.getString("bulletinDate"),"1" ));
			rowArray[3]=Common.get(Datetime.changeTaiwanYYMMDD(rs.getString("suitDateS"),"1" ));
			rowArray[4]=Common.get(Datetime.changeTaiwanYYMMDD(rs.getString("suitDateE"),"1" ));
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


