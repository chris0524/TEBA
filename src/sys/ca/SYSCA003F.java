/*
*<br>程式目的：保管使用單位
*<br>程式代號：sysca003f
*<br>程式日期：0950320
*<br>程式作者：clive.chang
*<br>--------------------------------------------------------
*<br>修改作者　　修改日期　　　修改目的
*<br>--------------------------------------------------------
*/

package sys.ca;

import java.sql.ResultSet;
import java.util.ArrayList;
import util.*;

public class SYSCA003F extends SYSCA003Q {


String enterOrg;
String enterOrgName;
String unitNo;
String unitName;
String deprUnit;
String notes;
String editID;
String editDate;
String editTime;


public String getDeprUnit() {return checkGet(deprUnit);}
public void setDeprUnit(String deprUnit) {this.deprUnit = checkSet(deprUnit);}
public String getEnterOrg(){ return checkGet(enterOrg); }
public void setEnterOrg(String s){ enterOrg=checkSet(s); }
public String getEnterOrgName(){ return checkGet(enterOrgName); }
public void setEnterOrgName(String s){ enterOrgName=checkSet(s); }
public String getUnitNo(){ return checkGet(unitNo); }
public void setUnitNo(String s){ unitNo=checkSet(s); }
public String getUnitName(){ return checkGet(unitName); }
public void setUnitName(String s){ unitName=checkSet(s); }
public String getNotes(){ return checkGet(notes); }
public void setNotes(String s){ notes=checkSet(s); }
public String getEditID() {return checkGet(editID);}
public void setEditID(String editID) {this.editID = checkSet(editID);}
public String getEditDate() {return checkGet(editDate);}
public void setEditDate(String editDate) {this.editDate = checkSet(editDate);}
public String getEditTime() {return checkGet(editTime);}
public void setEditTime(String editTime) {this.editTime = checkSet(editTime);}

String roleid;
String organID;
public String getRoleid() {return checkGet(roleid);}
public void setRoleid(String roleid) {this.roleid = checkSet(roleid);}
public String getOrganID() {return checkGet(organID);}
public void setOrganID(String organID) {this.organID = checkSet(organID);}


//傳回執行insert前之檢查sql
protected String[][] getInsertCheckSQL(){
	String[][] checkSQLArray = new String[2][4];
 	checkSQLArray[0][0]=" select count(*) as checkResult from UNTMP_KeepUnit where 1=1 " + 
		" and enterOrg = " + Common.sqlChar(enterOrg) + 
		" and unitNo = " + Common.sqlChar(unitNo) + 
		"";
	checkSQLArray[0][1]=">";
	checkSQLArray[0][2]="0";
	checkSQLArray[0][3]="該筆資料己重複，請重新輸入！";
	
 	checkSQLArray[1][0]=" select count(*) as checkResult from UNTMP_KeepUnit where 1=1 " + 
		" and enterOrg = " + Common.sqlChar(enterOrg) + 
		" and unitName = " + Common.sqlChar(getUnitName()) + 
		"";
	checkSQLArray[1][1]=">";
	checkSQLArray[1][2]="0";
	checkSQLArray[1][3]="單位名稱己重複，請重新輸入！";	
	return checkSQLArray;
}


//傳回insert sql
protected String[] getInsertSQL(){
	String[] execSQLArray = new String[1];
	execSQLArray[0]=" insert into UNTMP_KeepUnit(" +
			" enterOrg," +
			" unitNo," +
			" unitName," +
			" deprUnit," +
			" notes," +
			" editID," +
			" editDate," +
			" editTime" +
		" )Values(" +
			Common.sqlChar(enterOrg) + "," +
			Common.sqlChar(unitNo) + "," +
			Common.sqlChar(unitName) + "," +
			Common.sqlChar(deprUnit) + "," +
			Common.sqlChar(notes) + "," +
			Common.sqlChar(editID) + "," +
			Common.sqlChar(editDate) + "," +
			Common.sqlChar(editTime) + ")" ;
	return execSQLArray;
}


//傳回執行insert前之檢查sql
protected String[][] getUpdateCheckSQL(){
	String[][] checkSQLArray = new String[1][4];	
 	checkSQLArray[0][0]=" select count(*) as checkResult from UNTMP_KeepUnit where 1=1 " + 
		" and enterOrg = " + Common.sqlChar(enterOrg) + 
		" and unitNo <> " + Common.sqlChar(unitNo) +		
		" and unitName = " + Common.sqlChar(getUnitName()) +
		"";
	checkSQLArray[0][1]=">";
	checkSQLArray[0][2]="0";
	checkSQLArray[0][3]="單位名稱己重複，請重新輸入！";	
	return checkSQLArray;
}


//傳回update sql
protected String[] getUpdateSQL(){
	String[] execSQLArray = new String[1];
	execSQLArray[0] = " update UNTMP_KeepUnit set " +
						" enterOrg = " + Common.sqlChar(enterOrg) + "," +
						" unitNo = " + Common.sqlChar(unitNo) + "," +
						" unitName = " + Common.sqlChar(unitName) + "," +
						" deprUnit = " + Common.sqlChar(deprUnit) + "," +
						" notes = " + Common.sqlChar(notes) + "," +
						" editID = " + Common.sqlChar(editID) + "," +
						" editDate = " + Common.sqlChar(editDate) + "," +
						" editTime = " + Common.sqlChar(editTime) + 
					  " where 1=1 " + 
					  " and enterOrg = " + Common.sqlChar(enterOrg) +
					  " and unitNo = " + Common.sqlChar(unitNo) +
					  "";
	return execSQLArray;
}

//傳回執行delect前之檢查sql
protected String[][] getDeleteCheckSQL(){
	String[][] checkSQLArray = new String[1][4];
	String checkSQL=" select count(*) as checkResult from (" + 
		" (select distinct originalKeepUnit from UNTMP_MovableDetail where enterorg="+ Common.sqlChar(enterOrg) +" and originalKeepUnit="+ Common.sqlChar(unitNo) +")"+ 
		" union(select distinct originalUseUnit from UNTMP_MovableDetail where enterorg="+ Common.sqlChar(enterOrg) +" and originalUseUnit="+ Common.sqlChar(unitNo) +")" + 	
		" union(select distinct keepUnit from UNTMP_MovableDetail where enterorg="+ Common.sqlChar(enterOrg) +" and keepUnit="+ Common.sqlChar(unitNo) +")" + 
		" union(select distinct useUnit from UNTMP_MovableDetail where enterorg="+ Common.sqlChar(enterOrg) +" and useUnit="+ Common.sqlChar(unitNo) +")" + 
		" union(select distinct oldKeepUnit from UNTMP_MoveDetail where enterorg="+ Common.sqlChar(enterOrg) +" and oldKeepUnit="+ Common.sqlChar(unitNo) +")" + 
		" union(select distinct oldUseUnit from UNTMP_MoveDetail where enterorg="+ Common.sqlChar(enterOrg) +" and oldUseUnit="+ Common.sqlChar(unitNo) +")" +
		" union(select distinct newKeepUnit from UNTMP_MoveDetail where enterorg="+ Common.sqlChar(enterOrg) +" and newKeepUnit="+ Common.sqlChar(unitNo) +")" +
		" union(select distinct newUseUnit from UNTMP_MoveDetail where enterorg="+ Common.sqlChar(enterOrg) +" and newUseUnit="+ Common.sqlChar(unitNo) +")" +
		" union(select distinct keepUnit from UNTMP_ReduceDetail where enterorg="+ Common.sqlChar(enterOrg) +" and keepUnit="+ Common.sqlChar(unitNo) +")" +
		" union(select distinct useUnit from UNTMP_ReduceDetail where enterorg="+ Common.sqlChar(enterOrg) +" and useUnit="+ Common.sqlChar(unitNo) +")" +
		" union(select distinct keepUnit from UNTMP_DealDetail where enterorg="+ Common.sqlChar(enterOrg) +" and keepUnit="+ Common.sqlChar(unitNo) +")" +
		" union(select distinct useUnit from UNTMP_DealDetail where enterorg="+ Common.sqlChar(enterOrg) +" and useUnit="+ Common.sqlChar(unitNo) +")" +
		" union(select distinct keepUnit from UNTMP_AdjustDetail where enterorg="+ Common.sqlChar(enterOrg) +" and keepUnit="+ Common.sqlChar(unitNo) +")" +
		" union(select distinct useUnit from UNTMP_AdjustDetail where enterorg="+ Common.sqlChar(enterOrg) +" and useUnit="+ Common.sqlChar(unitNo) +")" +
		" union(select distinct originalKeepUnit from UNTNE_NonexpDetail where enterorg="+ Common.sqlChar(enterOrg) +" and originalKeepUnit="+ Common.sqlChar(unitNo) +")" +
		" union(select distinct originalUseUnit from UNTNE_NonexpDetail where enterorg="+ Common.sqlChar(enterOrg) +" and originalUseUnit="+ Common.sqlChar(unitNo) +")" +
		" union(select distinct keepUnit from UNTNE_NonexpDetail where enterorg="+ Common.sqlChar(enterOrg) +" and keepUnit="+ Common.sqlChar(unitNo) +")" +
		" union(select distinct useUnit from UNTNE_NonexpDetail where enterorg="+ Common.sqlChar(enterOrg) +" and useUnit="+ Common.sqlChar(unitNo) +")" +
		" union(select distinct oldKeepUnit from UNTNE_MoveDetail where enterorg="+ Common.sqlChar(enterOrg) +" and oldKeepUnit="+ Common.sqlChar(unitNo) +")" +
		" union(select distinct oldUseUnit from UNTNE_MoveDetail where enterorg="+ Common.sqlChar(enterOrg) +" and oldUseUnit="+ Common.sqlChar(unitNo) +")" +
		" union(select distinct newKeepUnit from UNTNE_MoveDetail where enterorg="+ Common.sqlChar(enterOrg) +" and newKeepUnit="+ Common.sqlChar(unitNo) +")" +
		" union(select distinct newUseUnit from UNTNE_MoveDetail where enterorg="+ Common.sqlChar(enterOrg) +" and newUseUnit="+ Common.sqlChar(unitNo) +")" +
		" union(select distinct keepUnit from UNTNE_ReduceDetail where enterorg="+ Common.sqlChar(enterOrg) +" and keepUnit="+ Common.sqlChar(unitNo) +")" +
		" union(select distinct useUnit from UNTNE_ReduceDetail where enterorg="+ Common.sqlChar(enterOrg) +" and useUnit="+ Common.sqlChar(unitNo) +")" + 
		" union(select distinct keepUnit from UNTNE_DealDetail where enterorg="+ Common.sqlChar(enterOrg) +" and keepUnit="+ Common.sqlChar(unitNo) +")" + 
		" union(select distinct useUnit from UNTNE_DealDetail where enterorg="+ Common.sqlChar(enterOrg) +" and useUnit="+ Common.sqlChar(unitNo) +")" + 
		" union(select distinct keepUnit from UNTNE_AdjustDetail where enterorg="+ Common.sqlChar(enterOrg) +" and keepUnit="+ Common.sqlChar(unitNo) +")" + 
//		" union(select distinct useUnit from UNTNE_AdjustDetail where enterorg="+ Common.sqlChar(enterOrg) +" and useUnit="+ Common.sqlChar(unitNo) +")" + 
//		" union(select distinct originalKeepUnit from UNTSO_PCSoftware where enterorg="+ Common.sqlChar(enterOrg) +" and originalKeepUnit="+ Common.sqlChar(unitNo) +")" + 
//		" union(select distinct originalUseUnit from UNTSO_PCSoftware where enterorg="+ Common.sqlChar(enterOrg) +" and originalUseUnit="+ Common.sqlChar(unitNo) +")" + 
//		" union(select distinct keepUnit from UNTSO_PCSoftware where enterorg="+ Common.sqlChar(enterOrg) +" and keepUnit="+ Common.sqlChar(unitNo) +")" + 
//		" union(select distinct useUnit from UNTSO_PCSoftware where enterorg="+ Common.sqlChar(enterOrg) +" and useUnit="+ Common.sqlChar(unitNo) +")" + 
//		" union(select distinct keepUnit from UNTSO_SoftCare where enterorg="+ Common.sqlChar(enterOrg) +" and keepUnit="+ Common.sqlChar(unitNo) +")" + 
//		" union(select distinct useUnit from UNTSO_SoftCare where enterorg="+ Common.sqlChar(enterOrg) +" and useUnit="+ Common.sqlChar(unitNo) +")" + 
		" union(select distinct keepUnit from UNTVP_AddProof where enterorg="+ Common.sqlChar(enterOrg) +" and keepUnit="+ Common.sqlChar(unitNo) +")" + 
		" union(select distinct keepUnit from UNTVP_AdjustProof where enterorg="+ Common.sqlChar(enterOrg) +" and keepUnit="+ Common.sqlChar(unitNo) +")" + 
		" ) b " + 
		"";
	
 	checkSQLArray[0][0]=checkSQL;
	checkSQLArray[0][1]="=";
	checkSQLArray[0][2]="1";
	checkSQLArray[0][3]="該資料已使用過，不可刪除！";
	return checkSQLArray;
}

//傳回delete sql
protected String[] getDeleteSQL(){
	String[] execSQLArray = new String[1];
	execSQLArray[0]=" delete UNTMP_KeepUnit where 1=1 " +
		" and enterOrg = " + Common.sqlChar(enterOrg) +
		" and unitNo = " + Common.sqlChar(unitNo) +
		"";	
	return execSQLArray;
}


/**
 * <br>
 * <br>目的：依主鍵查詢單一資料
 * <br>參數：無
 * <br>傳回：傳回本物件
*/

public SYSCA003F  queryOne() throws Exception{
	Database db = new Database();
	SYSCA003F obj = this;
	try {
		String sql=" select a.enterOrg, (select z.organsname from SYSCA_ORGAN z where z.organid = a.enterOrg) as enterOrgName, a.unitNo, a.unitName, a.deprUnit, a.notes, a.editID, a.editDate, a.editTime" +
			" from UNTMP_KeepUnit a where 1=1 " +
			" and a.enterOrg = " + Common.sqlChar(enterOrg) +
			" and a.unitNo = " + Common.sqlChar(unitNo) +
			"";
		ResultSet rs = db.querySQL(sql + " order by a.unitNo ");
		
		if (rs.next()){
			obj.setEnterOrg(rs.getString("enterOrg"));
			obj.setEnterOrgName(rs.getString("enterOrgName"));
			obj.setUnitNo(rs.getString("unitNo"));
			obj.setUnitName(rs.getString("unitName"));
			obj.setDeprUnit(rs.getString("deprUnit"));
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
		  String sql=" select a.enterOrg, a.unitNo, a.unitName, " + 
				  		"(select z.deprUnitName from SYSCA_DeprUnit z where z.enterorg = a.enterorg and z.deprunitno = a.deprunit) as deprUnitName, " +
				  		" a.notes" +
				  		" from UNTMP_KeepUnit a where 1=1 ";
			
			 
//			if (!"".equals(getQ_enterOrg()))
//				sql+=" and a.enterorg = " + Common.sqlChar(getQ_enterOrg()) ;
			if (!"".equals(getQ_unitNo()))
				sql+=" and a.unitNo = " + Common.sqlChar(getQ_unitNo()) ;
			if (!"".equals(getQ_unitName()))
				sql+=" and a.unitName like " + Common.sqlChar("%"+getQ_unitName()+"%") ;
			if (!"".equals(getQ_deprUnit()))
				sql+=" and a.deprUnit = " + Common.sqlChar(getQ_deprUnit()) ;
			if (!"".equals(getQ_notes()))
				sql+=" and a.notes like " + Common.sqlChar("%"+getQ_notes()+"%") ;
			
			if ("Y".equals(this.getIsAdminManager())){
				if ("".equals(getQ_enterOrg())) {
					sql+=" and a.enterorg = " + Common.sqlChar(getOrganID()) ;
				} else {
					sql+=" and a.enterorg = " + Common.sqlChar(getQ_enterOrg()) ;
				}
			}else{
				sql+=" and a.enterorg = " + Common.sqlChar(getOrganID()) ;
			}
			
			
		//System.out.println("=======queryAll======="+sql);
		ResultSet rs = db.querySQL(sql);
		while (rs.next()){
			counter++;
			String rowArray[]=new String[5];
			rowArray[0]=Common.get(rs.getString("enterOrg"));	
			rowArray[1]=Common.get(rs.getString("unitNo")); 
			rowArray[2]=Common.get(rs.getString("unitName")); 
			rowArray[3]=Common.get(rs.getString("deprUnitName"));
			rowArray[4]=Common.get(rs.getString("notes")); 
			objList.add(rowArray);
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


