/*
*<br>程式目的：月結作業
*<br>程式代號：UNTGR001F
*<br>程式日期：0950302
*<br>程式作者：Cherry
*<br>--------------------------------------------------------
*<br>修改作者　　修改日期　　　修改目的
*<br>--------------------------------------------------------
*/

package unt.gr;

import java.sql.ResultSet;
import java.util.ArrayList;
import util.*;

public class UNTGR001F extends SuperBean{


String enterOrg;
String enterOrgName;
String closingYM;
String YM;

String q_enterOrg;
String q_enterOrgName;
String q_closingYM;

public String getEnterOrg(){ return checkGet(enterOrg); }
public void setEnterOrg(String s){ enterOrg=checkSet(s); }
public String getEnterOrgName(){ return checkGet(enterOrgName); }
public void setEnterOrgName(String s){ enterOrgName=checkSet(s); }
public String getClosingYM(){ return checkGet(closingYM); }
public void setClosingYM(String s){ closingYM=checkSet(s); }
public String getYM(){ return checkGet(YM); }
public void setYM(String s){ YM=checkSet(s); }

public String getQ_enterOrg(){ return checkGet(q_enterOrg); }
public void setQ_enterOrg(String s){ q_enterOrg=checkSet(s); }
public String getQ_enterOrgName(){ return checkGet(q_enterOrgName); }
public void setQ_enterOrgName(String s){ q_enterOrgName=checkSet(s); }
public String getQ_closingYM(){ return checkGet(q_closingYM); }
public void setQ_closingYM(String s){ q_closingYM=checkSet(s); }

String isOrganManager;
String isAdminManager;
String organID;    
public String getOrganID() { return checkGet(organID); }
public void setOrganID(String s) { organID = checkSet(s); }
public String getIsOrganManager() { return checkGet(isOrganManager); }
public void setIsOrganManager(String s) { isOrganManager = checkSet(s); }
public String getIsAdminManager() { return checkGet(isAdminManager); }
public void setIsAdminManager(String s) { isAdminManager = checkSet(s); }    

String make;

public String getMake() { return checkGet(make); }
public void setMake(String s) { make = checkSet(s); }    

//傳回執行insert前之檢查sql
protected String[][] getInsertCheckSQL(){
	String[][] checkSQLArray = new String[3][4];
 	checkSQLArray[0][0]=" select count(*) as checkResult from UNTGR_Closing where 1=1 " + 
						" and enterOrg = " + Common.sqlChar(enterOrg) + 
						" and closingYM = " + Common.sqlChar(closingYM) + 
						"";
	checkSQLArray[0][1]=">";
	checkSQLArray[0][2]="0";
	checkSQLArray[0][3]="該筆資料己重複，請重新輸入！";
	
	checkSQLArray[1][0]=" select max(closingYM) as checkResult from UNTGR_Closing where 1=1 " +
						" and enterOrg = " + Common.sqlChar(enterOrg) + 
						"";
	checkSQLArray[1][1]=">=";
	checkSQLArray[1][2]=closingYM;
	checkSQLArray[1][3]="「月結年月」必須大於入帳機關的「最大的月結年月」!";

	checkSQLArray[2][0]=" select decode(max(closingYM),'00000','"+YM+"',max(closingYM)) as checkResult from UNTGR_Closing where 1=1 " +
						" and enterOrg = " + Common.sqlChar(enterOrg) + 
						"";
	checkSQLArray[2][1]="!=";
	checkSQLArray[2][2]=YM;
	checkSQLArray[2][3]="「月結年月」必須為最後一次「月結年月」+1個月!";

	return checkSQLArray;
}

//傳回insert sql
public void insert(){
	getInsertCheckSQL();
    Database db = new Database();    
    String strSQL = "";
	String[] execSQLArray;
	try{
		if (!beforeExecCheck(getInsertCheckSQL())){
			setStateInsertError();
		}else{
		  	if(Integer.parseInt(View.getLookupField("select count(*) from UNTGR_Closing where 1=1 and enterOrg =" + Common.sqlChar(enterOrg)+" and closingYM!='00000'"))<1){
		  		setMake("<=");
		  	}else{
		  		setMake("=");
		  	}
			setEditID(getUserID());
			setEditDate(Datetime.getYYYMMDD());
			setEditTime(Datetime.getHHMMSS());	
			//新增
			//月結紀錄檔
			strSQL+=" insert into UNTGR_Closing(" +
					" enterOrg,"+
					" closingYM,"+
					" editID,"+
					" editDate,"+
					" editTime"+
				" )Values(" +
					Common.sqlChar(enterOrg) + "," +
					Common.sqlChar(closingYM) + "," +
					Common.sqlChar(getEditID()) + "," +
					Common.sqlChar(getEditDate()) + "," +
					Common.sqlChar(getEditTime()) + ")" +
				":;:" ;
			//修改=====================================
			//============ 土地  =================================
			//土地增加單 UNTLA_AddProof
			strSQL+=updateTable("UNTLA_AddProof","enterDate");
			//土地主檔 UNTLA_Land
			strSQL+=updateTable("UNTLA_Land","enterDate");
			//土地合併分割案件檔 UNTLA_MergeDivision
			strSQL+=updateTable("UNTLA_MergeDivision","enterDate");
			//土地減損單UNTLA_ReduceProof
			strSQL+=updateTable("UNTLA_ReduceProof","reduceDate");
			//土地減損單明細檔UNTLA_ReduceDetail
			strSQL+=updateTable("UNTLA_ReduceDetail","reduceDate");
			//土地增減值單UNTLA_AdjustProof
			strSQL+=updateTable("UNTLA_AdjustProof","adjustDate");
			//土地增減值單明細檔UNTLA_AdjustDetail
			strSQL+=updateTable("UNTLA_AdjustDetail","adjustDate");
			//============ 建物 =================================
			//建物增加單 UNTBU_AddProof
			strSQL+=updateTable("UNTBU_AddProof","enterDate");
			//建物主檔 UNTBU_Building
			strSQL+=updateTable("UNTBU_Building","enterDate");
			//建物減損單UNTBU_ReduceProof
			strSQL+=updateTable("UNTBU_ReduceProof","reduceDate");
			//建物減損單明細檔UNTBU_ReduceDetail
			strSQL+=updateTable("UNTBU_ReduceDetail","reduceDate");
			//建物增減值單UNTBU_AdjustProof
			strSQL+=updateTable("UNTBU_AdjustProof","adjustDate");
			//建物增減值單明細檔UNTBU_AdjustDetail
			strSQL+=updateTable("UNTBU_AdjustDetail","adjustDate");
			//============ 土地改良物 =================================
			//土地改良物增加單 UNTRF_AddProof
			strSQL+=updateTable("UNTRF_AddProof","enterDate");
			//土地改良物主檔 UNTRF_Attachment
			strSQL+=updateTable("UNTRF_Attachment","enterDate");
			//土地改良物減損單UNTRF_ReduceProof
			strSQL+=updateTable("UNTRF_ReduceProof","reduceDate");
			//土地改良物減損單明細檔UNTRF_ReduceDetail
			strSQL+=updateTable("UNTRF_ReduceDetail","reduceDate");
			//土地改良物增減值單UNTRF_AdjustProof
			strSQL+=updateTable("UNTRF_AdjustProof","adjustDate");
			//土地改良物增減值單明細檔UNTRF_AdjustDetail
			strSQL+=updateTable("UNTRF_AdjustDetail","adjustDate");
			//============ 動產 =================================
			//動產增加單 UNTMP_AddProof
			strSQL+=updateTable("UNTMP_AddProof","enterDate");
			//動產主檔－批號資料 UNTMP_Movable
			strSQL+=updateTable("UNTMP_Movable","enterDate");
			//動產主檔－批號明細 UNTMP_MovableDetail
			strSQL+=updateDetailTable("UNTMP_Movable","UNTMP_MovableDetail");
			//動產減損單UNTMP_ReduceProof
			strSQL+=updateTable("UNTMP_ReduceProof","reduceDate");
			//動產減損單明細檔UNTMP_ReduceDetail
			strSQL+=updateTable("UNTMP_ReduceDetail","reduceDate");
			//動產增減值單UNTMP_AdjustProof
			strSQL+=updateTable("UNTMP_AdjustProof","adjustDate");
			//動產增減值單明細檔UNTMP_AdjustDetail
			strSQL+=updateTable("UNTMP_AdjustDetail","adjustDate");
			//動產移動單UNTMP_MoveProof
			strSQL+=updateTable("UNTMP_MoveProof","moveDate");
			//動產移動單明細檔UNTMP_MoveDetail
			strSQL+=updateTable("UNTMP_MoveDetail","newMoveDate");
			//============ 有價證券 =================================
			//有價證券增加單 UNTVP_AddProof
			strSQL+=updateTable("UNTVP_AddProof","enterDate");
			//有價證券股份檔 UNTVP_Share
			strSQL+=updateTable("UNTVP_Share","enterDate");
			//有價證券增減值單UNTVP_AdjustProof
			strSQL+=updateTable("UNTVP_AdjustProof","adjustDate");
			//============ 權利 =================================
			//權利增加單 UNTRT_AddProof
			strSQL+=updateTable("UNTRT_AddProof","enterDate");
			//權利標的檔 UNTRT_AddDetail
			strSQL+=updateTable("UNTRT_AddDetail","enterDate");
			//權利減損單UNTRT_ReduceProof
			strSQL+=updateTable("UNTRT_ReduceProof","reduceDate");
			//權利增減值單UNTRT_AdjustProof
			strSQL+=updateTable("UNTRT_AdjustProof","adjustDate");
			//========================================
			//System.out.println("新增SQL : "+strSQL);
			execSQLArray = strSQL.split(":;:");
			db.excuteSQL(execSQLArray);
			setStateInsertSuccess();
			setErrorMsg("新增完成");				
		}
	} catch (Exception e) {
		e.printStackTrace();
	} finally {
		db.closeAll();
	}	
}
//修改 主檔 Table
protected String updateTable(String table,String dateName){
	String updateSQL=" update " + table + " set " +
			" closing = 'Y' "+
		" where 1=1 " + 
			" and enterOrg = " + Common.sqlChar(enterOrg) +
			" and verify = 'Y' " +
			" and substr("+dateName+",1,5) " + getMake() + Common.sqlChar(closingYM) +
			":;:";
	return updateSQL;
}
//修改明細 Table
protected String updateDetailTable(String table, String detailTable){
	String updateSQL=" update " + detailTable + " set " +
			" closing = 'Y' "+
		" where 1=1 " +
			" and " +
			" (enterOrg, ownership, propertyNo, lotNo) " +
			" in " +
			" (select enterOrg, ownership, propertyNo, lotNo from " + table + 
			" where 1=1 " +
			" and enterOrg = " + Common.sqlChar(enterOrg) +
			" and verify = 'Y' " +
			" and substr(enterDate,1,5) " + getMake() + Common.sqlChar(closingYM) +
			")" + 
			":;:";
	return updateSQL;
}

/*
//傳回update sql
protected String[] getUpdateSQL(){
	String[] execSQLArray = new String[1];
	execSQLArray[0]=" update UNTGR_Closing set " +
			" editID = " + Common.sqlChar(getEditID()) + "," +
			" editDate = " + Common.sqlChar(getEditDate()) + "," +
			" editTime = " + Common.sqlChar(getEditTime()) + 
		" where 1=1 " + 
			" and enterOrg = " + Common.sqlChar(enterOrg) +
			" and closingYM = " + Common.sqlChar(closingYM) +
			"";
	return execSQLArray;
}


//傳回delete sql
protected String[] getDeleteSQL(){
	String[] execSQLArray = new String[1];
	execSQLArray[0]=" delete UNTGR_Closing where 1=1 " +
			" and enterOrg = " + Common.sqlChar(enterOrg) +
			" and closingYM = " + Common.sqlChar(closingYM) +
			"";
	return execSQLArray;
}
*/

/**
 * <br>
 * <br>目的：依主鍵查詢單一資料
 * <br>參數：無
 * <br>傳回：傳回本物件
*/

public UNTGR001F  queryOne() throws Exception{
	Database db = new Database();
	UNTGR001F obj = this;
	try {
		String sql=" select b.organSName, a.enterOrg, a.closingYM, a.editID, a.editDate, a.editTime  "+
			" from UNTGR_Closing a, SYSCA_Organ b where 1=1 " +
			" and a.enterOrg = " + Common.sqlChar(enterOrg) +
			" and a.closingYM = " + Common.sqlChar(closingYM) +
			" and b.organID = a.enterOrg" +
			"";
		ResultSet rs = db.querySQL(sql);
		if (rs.next()){
			obj.setEnterOrg(rs.getString("enterOrg"));
			obj.setEnterOrgName(rs.getString("organSName"));
			obj.setClosingYM(rs.getString("closingYM"));
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
		String sql=" select b.organSName, a.enterOrg, a.closingYM "+
			" from UNTGR_Closing a, SYSCA_Organ b where 1=1 " +
			" and b.organID = a.enterOrg" +
			""; 
			if (!"".equals(getQ_enterOrg())) {
				sql+=" and a.enterOrg = " + Common.sqlChar(getQ_enterOrg()) ;
			} else {
				if (!getIsAdminManager().equalsIgnoreCase("Y")) {
					if ("Y".equals(getIsOrganManager())) {					
						sql += " and a.enterOrg like '" + getOrganID().substring(0,5) + "%' ";					
					} else {
						sql+=" and a.enterOrg = " + Common.sqlChar(getOrganID()) ;
					}
				}
			}
			if (!"".equals(getQ_closingYM()))
				sql+=" and a.closingYM = " + Common.sqlChar(getQ_closingYM()) ;
		ResultSet rs = db.querySQL(sql);
		while (rs.next()){
			counter++;
			String rowArray[]=new String[3];
			rowArray[0]=rs.getString("organSName");
			rowArray[1]=rs.getString("enterOrg"); 
			rowArray[2]=rs.getString("closingYM"); 
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


