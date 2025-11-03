/*
*<br>程式目的：保管使用人之單位變更
*<br>程式代號：untdu100r
*<br>程式日期：0981210
*<br>程式作者：Timtoy.Tsai
*<br>--------------------------------------------------------
*<br>修改作者　　修改日期　　　修改目的
*<br>--------------------------------------------------------
*/

package unt.du;

import java.sql.ResultSet;
import java.util.ArrayList;
import util.*;

public class UNTDU100R extends SuperBean {

String enterOrg;
String enterOrgName;

String originalKeepBureau;
String originalKeepUnit;
String originalKeeper;

String newKeepBureau;
String newKeepUnit;
String newKeeper;

String n1;
String n2;
String n3;
String n4;

public String getEnterOrg(){ return checkGet(enterOrg); }
public void setEnterOrg(String s){ enterOrg=checkSet(s); }
public String getEnterOrgName(){ return checkGet(enterOrgName); }
public void setEnterOrgName(String s){ enterOrgName=checkSet(s); }

public String getOriginalKeepBureau(){ return checkGet(originalKeepBureau); }
public void setOriginalKeepBureau(String s){ originalKeepBureau=checkSet(s); }
public String getOriginalKeepUnit(){ return checkGet(originalKeepUnit); }
public void setOriginalKeepUnit(String s){ originalKeepUnit=checkSet(s); }
public String getOriginalKeeper(){ return checkGet(originalKeeper); }
public void setOriginalKeeper(String s){ originalKeeper=checkSet(s); }

public String getNewKeepBureau(){ return checkGet(newKeepBureau); }
public void setNewKeepBureau(String s){ newKeepBureau=checkSet(s); }
public String getNewKeepUnit(){ return checkGet(newKeepUnit); }
public void setNewKeepUnit(String s){ newKeepUnit=checkSet(s); }
public String getNewKeeper(){ return checkGet(newKeeper); }
public void setNewKeeper(String s){ newKeeper=checkSet(s); }

public String getN1(){ return checkGet(n1); }
public void setN1(String s){ n1=checkSet(s); }
public String getN2(){ return checkGet(n2); }
public void setN2(String s){ n2=checkSet(s); }
public String getN3(){ return checkGet(n3); }
public void setN3(String s){ n3=checkSet(s); }
public String getN4(){ return checkGet(n4); }
public void setN4(String s){ n4=checkSet(s); }

//仿流水號效果
String editID_temp; 
public String getEditID_temp(){ return checkGet(editID_temp); }
public void setEditID_temp(String s){ editID_temp=checkSet(s); }
String editDate_temp; 
public String getEditDate_temp(){ return checkGet(editDate_temp); }
public void setEditDate_temp(String s){ editDate_temp=checkSet(s); }
String editTime_temp;
public String getEditTime_temp(){ return checkGet(editTime_temp); }
public void setEditTime_temp(String s){ editTime_temp=checkSet(s); }


public void change(){
	Database db = new Database();
	ResultSet rs;
	try {
		rs = db.querySQL(checkedSQL());
		if(rs.next()){
			if("0".equals(rs.getString("resultCount"))){
				db.exeSQL(update_tables());
				setStateUpdateSuccess();
				setErrorMsg("變更成功！");	
			}else{
				setStateUpdateError();
				setErrorMsg("注意：新單位("+newKeepUnit+")已存在該保管人("+originalKeeper+")資料，無法進行變更！");
			}
		}
	} catch (Exception e) {
		System.out.println("----------------- untdu100r Exception -----------------");
		e.printStackTrace();
	} finally {
		db.closeAll();
	} 
}

//檢核新單位是否已存在該保管人代碼資料
public String checkedSQL(){
	String sql = "select count(*) as resultCount from UNTMP_Keeper where 1=1 " +"\n"+
				" and enterorg= " +Common.sqlChar(Common.get(enterOrg))+"\n"+
				" and unitNo= " +Common.sqlChar(Common.get(newKeepUnit))+"\n"+
				" and keeperNo= "+Common.sqlChar(Common.get(originalKeeper));
//System.out.println("---- untdu100r checkedSQL ----\n"+sql);
	return sql;
}

//保管使用人之單位變更前先檢查各table筆數
public void sql_2(){
}

//保管使用人之單位變更
public String[] update_tables(){
	String[] execSQLArray = new String[2+35];
	
	setEditID_temp(getUserID());
	setEditDate_temp(Datetime.getYYYMMDD());
	setEditTime_temp(Datetime.getHHMMSS());
	setN1(Common.get(enterOrg));
	setN2(Common.get(originalKeepUnit));
	setN3(Common.get(originalKeeper));
	setN4(Common.get(newKeepUnit));
	
	execSQLArray[0] = " insert into UNTDU_Keeper(" +"\n"+
			" enterOrg, " +"\n"+
			" oldKeepUnit, " +"\n"+
			" oldKeeper, " +"\n"+
			" newKeepUnit, " +"\n"+
			" editID,editDate,editTime " +"\n"+
		" )values( " +"\n"+
			Common.sqlChar(n1) + "," +"\n"+
			Common.sqlChar(n2) + "," +"\n"+
			Common.sqlChar(n3) + "," +"\n"+
			Common.sqlChar(n4) + "," +"\n"+
			Common.sqlChar(editID_temp) + "," +"\n"+
			Common.sqlChar(editDate_temp) + "," +"\n"+
			Common.sqlChar(editTime_temp) + ")"+
			"";

	
	execSQLArray[1] = " update UNTDU_Keeper a set "+"\n"+
	       " a.organAName = " + "(select b.organAName from SYSCA_Organ b where b.organID =a.enterOrg)," +"\n"+
	       " a.oldUnitName   = " + " (select c.unitName   from UNTMP_KeepUnit c where c.enterOrg=a.enterOrg and c.unitNo=a.oldKeepUnit)," +"\n"+
	       " a.oldKeeperName = " + " (select d.keeperName from UNTMP_Keeper   d where d.enterOrg=a.enterOrg and d.unitNo=a.oldKeepUnit and d.keeperNo=a.oldKeeper)," +"\n"+
	       " a.newUnitName   = " + " (select e.unitName   from UNTMP_KeepUnit e where e.enterOrg=a.enterOrg and e.unitNo=a.newKeepUnit)" +"\n"+
		" where 1=1 " + 
		   " and a.enterOrg = " + Common.sqlChar(n1) +"\n"+
		   " and a.oldKeepUnit = " + Common.sqlChar(n2) +"\n"+
		   " and a.oldKeeper   = " + Common.sqlChar(n3) +"\n"+
		   " and a.newKeepUnit = " + Common.sqlChar(n4) +"\n"+
		   " and a.editID      = " + Common.sqlChar(editID_temp) +"\n"+
		   " and a.editDate    = " + Common.sqlChar(editDate_temp) +"\n"+
		   " and a.editTime    = " + Common.sqlChar(editTime_temp) +"\n"+
		   "";
	
	String[] arrayTables_update_unitNo = {"UNTMP_Keeper"};
		execSQLArray[2] ="update "+arrayTables_update_unitNo[0]+" set unitNo = "+Common.sqlChar(n4)+" where enterorg = "+Common.sqlChar(n1)+" and unitNo = "+Common.sqlChar(n2)+" and keeperNo = "+Common.sqlChar(n3)+" ";
	
	String[] arrayTables_update_originalKeepUnit = {"UNTMP_MovableDetail","UNTNE_NonexpDetail","UNTSO_PCSoftware"};
	for(int i=1 ; i <= arrayTables_update_originalKeepUnit.length ; i++){
		execSQLArray[2+i] ="update "+arrayTables_update_originalKeepUnit[i-1]+" set originalKeepUnit = "+Common.sqlChar(n4)+" where enterorg = "+Common.sqlChar(n1)+" and originalKeepUnit = "+Common.sqlChar(n2)+" and originalKeeper = "+Common.sqlChar(n3);
	}
	
	String[] arrayTables_update_originalUseUnit = {"UNTMP_MovableDetail","UNTNE_NonexpDetail","UNTSO_PCSoftware"};
	for(int i=1 ; i <= arrayTables_update_originalUseUnit.length ; i++){
		execSQLArray[2+3+i] ="update "+arrayTables_update_originalUseUnit[i-1]+" set originalUseUnit = "+Common.sqlChar(n4)+" where enterorg = "+Common.sqlChar(n1)+" and originalUseUnit = "+Common.sqlChar(n2)+" and originalUser  = "+Common.sqlChar(n3);
	}
	
	String[] arrayTables_update_oldUseUnit = {"UNTMP_MoveDetail","UNTNE_MoveDetail"};
	for(int i=1 ; i <= arrayTables_update_oldUseUnit.length ; i++){
		execSQLArray[2+6+i] ="update "+arrayTables_update_oldUseUnit[i-1]+" set oldUseUnit = "+Common.sqlChar(n4)+" where enterorg = "+Common.sqlChar(n1)+" and oldUseUnit = "+Common.sqlChar(n2)+" and oldUserNo = "+Common.sqlChar(n3);
	}
	
	String[] arrayTables_update_useUnit = {"UNTMP_MovableDetail","UNTMP_AdjustDetail","UNTMP_ReduceDetail","UNTMP_DealDetail","UNTNE_NonexpDetail",
										   "UNTNE_AdjustDetail","UNTNE_ReduceDetail","UNTNE_DealDetail","UNTSO_PCSoftware","UNTSO_SoftCare"};
	for(int i=1 ; i <= arrayTables_update_useUnit.length ; i++){
		execSQLArray[2+8+i] ="update "+arrayTables_update_useUnit[i-1]+" set useUnit = "+Common.sqlChar(n4)+" where enterorg = "+Common.sqlChar(n1)+" and useUnit = "+Common.sqlChar(n2)+" and userNo = "+Common.sqlChar(n3);
	}
	
	String[] arrayTables_update_newUseUnit = {"UNTMP_MoveDetail","UNTNE_MoveDetail"};
	for(int i=1 ; i <= arrayTables_update_newUseUnit.length ; i++){
		execSQLArray[2+18+i] ="update "+arrayTables_update_newUseUnit[i-1]+" set newUseUnit = "+Common.sqlChar(n4)+" where enterorg = "+Common.sqlChar(n1)+" and newUseUnit = "+Common.sqlChar(n2)+" and newUserNo = "+Common.sqlChar(n3);
	}
	
	String[] arrayTables_update_oldKeepUnit = {"UNTMP_MoveDetail","UNTNE_MoveDetail"};
	for(int i=1 ; i <= arrayTables_update_oldKeepUnit.length ; i++){
		execSQLArray[2+20+i] ="update "+arrayTables_update_oldKeepUnit[i-1]+" set oldKeepUnit = "+Common.sqlChar(n4)+" where enterorg = "+Common.sqlChar(n1)+" and oldKeepUnit = "+Common.sqlChar(n2)+" and oldKeeper = "+Common.sqlChar(n3);
	}
	
	String[] arrayTables_update_keepUnit = {"UNTMP_MovableDetail","UNTMP_AdjustDetail","UNTMP_ReduceDetail","UNTMP_DealDetail","UNTNE_NonexpDetail",
			   								"UNTNE_AdjustDetail","UNTNE_ReduceDetail","UNTNE_DealDetail","UNTSO_PCSoftware","UNTSO_SoftCare"};
	for(int i=1 ; i <= arrayTables_update_keepUnit.length ; i++){
		execSQLArray[2+22+i] ="update "+arrayTables_update_keepUnit[i-1]+" set keepUnit = "+Common.sqlChar(n4)+" where enterorg = "+Common.sqlChar(n1)+" and keepUnit = "+Common.sqlChar(n2)+" and keeper = "+Common.sqlChar(n3);
	}
	
	String[] arrayTables_update_newKeepUnit = {"UNTMP_MoveDetail","UNTNE_MoveDetail"};
	for(int i=1 ; i <= arrayTables_update_newKeepUnit.length ; i++){
		execSQLArray[2+32+i] ="update "+arrayTables_update_newKeepUnit[i-1]+" set newKeepUnit = "+Common.sqlChar(n4)+" where enterorg = "+Common.sqlChar(n1)+" and newKeepUnit = "+Common.sqlChar(n2)+" and newKeeper = "+Common.sqlChar(n3);
	}		
	
//	System.out.println("--- untdu100r Start -----------------------------------");
//	for(int z=0; z<execSQLArray.length; z++){
//		System.out.println(execSQLArray[z]);
//	}
//	System.out.println("--- untdu100r END -------------------------------------");
	
	return execSQLArray;
}

}


