/*
*<br>程式目的：土地合併分割重測重劃作業－增加單明細合併挑選
*<br>程式代號：untla060f
*<br>程式日期：
*<br>程式作者：Yuan-Ren Jheng
*<br>--------------------------------------------------------
*<br>修改作者　　修改日期　　　修改目的
*<br>--------------------------------------------------------
*/

package unt.la;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Set;
import java.util.HashSet;
import java.util.Iterator;

import util.Common;
import util.*;

public class UNTLA060F extends UNTLA059F_2{

	//propertyTable
	//儲存點選多筆財產時的資料	
	public void create_propertyTable(){
		String sql="create table PROPERTYTABLE_"+this.getEditID()+"("+
						"propertyno varchar(20),"+
						"serialno varchar(10),"+
						"checkflag varchar(1)"+
					")";

		this.execSQL_ForNoResult_NoChange(sql);
	}	
	public void drop_propertyTable(){
		String sql="drop table PROPERTYTABLE_"+this.getEditID();
		
		this.execSQL_ForNoResult_NoChange(sql,true);
	}
	
	
	public void beforeWork_Add(){		
		beforeWork_060F_getSourceKind_FromUNTLA_Land();
		
		beforeWork_Add_dropTable_Untla_Land_Temp();
		beforeWork_Add_dropTable_Untla_Value_Temp();
		
		
		beforeWork_060F_CreateTable_Untla_Land_Temp();
		beforeWork_060F_CreateTable_Untla_Value_Temp();
		
		beforeWork_060F_Update_Area_Untla_Land_Temp();
		
		extend_beforeWork_Add();
	}	


		private void execValueCalculate(){
			if("Conform".equals(getSourceKind_before())){
				execValueCalculate_060F_update_Untla_Land_Temp2_for_bookValue_and_bookUnit();
				
			}else{
				execValueCalculate_060F_update_Untla_Land_Temp2_for_BookvalueAndBookunit();				
			}
		}
	
	
	public void afterWork(){	
		beforeWork_060F_getSourceKind_FromUNTLA_Land();
		
		beforeWork_060F_CreateTable_Untla_Land_Temp2();
		beforeWork_060F_CreateTable_Untla_Value_Temp2();
		
		execValueCalculate();						

		afterWork_060F_UpdateTo_Untla_Land_Temp2_forOtherData();
		afterWork_060F_update_Untla_Land_Temp2_forNotes();
		
		execGetMergeDivisionData();
		afterWork_060F_Update_Untla_Value_Temp2_from_Untla_Land_Temp();
		afterWork_060F_UpdateTo_Untla_Value_Temp2_forOtherData();
		afterWork_060F_UpdateTo_for_mergeDivisionBatch();
		afterWork_060F_updateTo_Untla_ReduceDetail();		
		
		updateNetValue_forUNTLA_LAND();
		super.updateSourceKind_forUNTLA_LAND();
		
		afterWork_060F_InsertTo_Untla_Land();
		afterWork_060F_InsertTo_Untla_Value();
		afterWork_060F_DropTable_Untla_Land_Temp2();
		afterWork_060F_DropTable_Untla_Value_Temp2();		
		drop_propertyTable();
		
		extend_afterWork();
	}
	

	public void cencelWork(){	
		afterWork_dropTable_Untla_Land_Temp();
		afterWork_dropTable_Untla_Value_Temp();
		afterWork_060F_DropTable_Untla_Land_Temp2();
		afterWork_060F_DropTable_Untla_Value_Temp2();
		drop_propertyTable();
		
		extend_cencelWork();
	}

	
// <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<	減損單	>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>	
	
//依查詢欄位查詢多筆資料
public ArrayList queryAll_Reduce() throws Exception{
	Database db = new Database();
	ArrayList objList=new ArrayList();
	int counter=0;
	
	String sql="select " +
					" a.enterorg, a.ownership, a.differencekind, a.propertyno, a.serialno, a.signno," +
					" a.holdArea, a.bookValue" +
				" from UNTLA_ReduceDetail a where 1=1" +
					" and enterorg = " + Common.sqlChar(this.getEnterOrg()) +
					" and ownership = " + Common.sqlChar(this.getOwnership()) +
					" and differencekind = " + Common.sqlChar(this.getDifferenceKind()) +
					" and caseNo = " + Common.sqlChar(this.getCaseNo_Reduce()) + 
					" and mergedivisionbatch is null" ;
	
	sql+=" order by a.enterOrg, a.ownership, a.caseNo, a.differencekind, a.signno ";

	try {
		LogBean.outputLogDebug(sql);
		
		ResultSet rs = db.querySQL(sql);
		while (rs.next()){
			counter++;
			String rowArray[]=new String[8];
			
			rowArray[0]=Common.get(rs.getString("enterOrg")); 
			rowArray[1]=Common.get(rs.getString("ownership"));
			rowArray[2]=Common.get(rs.getString("differencekind"));			
			rowArray[3]=Common.get(rs.getString("propertyNo"));
			rowArray[4]=Common.get(rs.getString("serialNo"));
			rowArray[5]=Common.get(getSignDescName(rs.getString("signNo").substring(0,7)) + rs.getString("signNo").substring(7,11) + '-' + rs.getString("signNo").substring(11));
			rowArray[6]=Common.get(rs.getString("holdArea")); 
			rowArray[7]=Common.get(rs.getString("bookValue")); 
			
			objList.add(rowArray);
			if (counter==getListLimit()){
				setErrorMsg(getListLimitError());
				break;
			}
		}
		setStateQueryAllSuccess();
	} catch (Exception e) {
		LogBean.outputLogError("Exception : "+e.getMessage()+"\n"+sql);
	} finally {
		db.closeAll();
	}
	return objList;
}
	
	private String getSignDescName(String signNo){
		Database db = null;
		ResultSet rs = null;
		String sql = null;
		String result = null;
		try{
			sql = "select signdesc from SYSCA_SIGN where" +
					" signNo = " + Common.sqlChar(signNo);
			
			db = new Database();
			rs = db.querySQL(sql);
			if(rs.next()){				
				result = rs.getString("signdesc");
			}
			rs.close();
		}catch(Exception e){
			System.out.println("getSignDescName Exception => " + e.getMessage());
		}finally{
			db.closeAll();
		}
		return result;
	}

//依查詢欄位查詢多筆資料
public ArrayList queryAll_Mid() throws Exception{
	beforeWork_060F_getSourceKind_FromUNTLA_Land();
	
	Database db = new Database();
	ArrayList objList=new ArrayList();
	int counter=0;
	
	String sql="select " +
					" a.enterorg, a.ownership, a.propertyno, a.serialno, a.signno," +
					" a.holdArea, a.bookValue, a.differencekind" +
				" from UNTLA_ReduceDetail a where 1=1" +
					" and enterorg = " + Common.sqlChar(this.getEnterOrg()) +
					" and ownership = " + Common.sqlChar(this.getOwnership()) +
					" and caseNo = " + Common.sqlChar(this.getCaseNo_Reduce()) +
					" and differencekind = " + Common.sqlChar(this.getDifferenceKind()) +					
					" and mergedivisionbatch is null" +
					" and (propertyno) in (select distinct propertyno from propertyTable_"+this.getEditID()+")" +
					" and (serialno) in (select distinct serialno from propertyTable_"+this.getEditID()+")";
	
	sql+=" order by a.enterOrg, a.ownership, a.caseNo, a.signno ";

	try {
		LogBean.outputLogDebug(sql);
		
		ResultSet rs = db.querySQL(sql);
		while (rs.next()){
			counter++;
			String rowArray[]=new String[9];
			
			rowArray[0]=Common.get(rs.getString("enterOrg")); 
			rowArray[1]=Common.get(rs.getString("ownership"));
			rowArray[2]=Common.get(rs.getString("differencekind"));
			rowArray[3]=Common.get(rs.getString("propertyNo"));
			rowArray[4]=Common.get(rs.getString("serialNo"));
			rowArray[5]=Common.get(getSignDescName(rs.getString("signNo").substring(0,7)) + rs.getString("signNo").substring(7,11) + '-' + rs.getString("signNo").substring(11));
			rowArray[6]=Common.get(rs.getString("holdArea")); 
			rowArray[7]=Common.get(rs.getString("bookValue")); 
			rowArray[8]=Common.get(rs.getString("signno"));
			
			objList.add(rowArray);
			if (counter==getListLimit()){
				setErrorMsg(getListLimitError());
				break;
			}
		}
		setStateQueryAllSuccess();
	} catch (Exception e) {
		LogBean.outputLogError("Exception : "+e.getMessage()+"\n"+sql);
	} finally {
		db.closeAll();
	}
	return objList;
}

// <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<	增加單	>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>

	
	
public void getHoldAreaBeforeWork(){
	String sql="select sum(holdarea) as holdarea from UNTLA_REDUCEDETAIL" +
				" where 1=1" +
					" and enterorg=" + Common.sqlChar(this.getEnterOrg()) +
					" and ownership=" + Common.sqlChar(this.getOwnership()) +
					" and caseno=" + Common.sqlChar(this.getCaseNo_Reduce()) +
					" and differencekind=" + Common.sqlChar(this.getDifferenceKind()) +
					" and (propertyno) in (select distinct propertyno from propertyTable_"+this.getEditID()+")" +
					" and (serialno) in (select distinct serialno from propertyTable_"+this.getEditID()+")";
	
	this.setBefore_holdArea(this.getNameData("holdarea", sql));
}
	


//依查詢欄位查詢多筆資料
public ArrayList queryAll_Add() throws Exception{
	Database db = new Database();
	ArrayList objList=new ArrayList();
	int counter=0;
	
	String sql="select " +
					" a.propertyno, a.serialno, a.signno," +
					" a.area" +
				" from UNTLA_Land_"+this.getEditID()+" a where 1=1 " +
					" and a.propertyNo = '" + strKeySetMid[0].split(",")[3] + "'" +
					" and a.serialNo = '" + strKeySetMid[0].split(",")[4] + "'";
					
	sql+=" order by a.enterorg, a.ownership, a.propertyno, a.serialno ";

	try {

		LogBean.outputLogDebug(sql);

		ResultSet rs = db.querySQL(sql);
		if (rs.next()){
			counter++;
			String rowArray[]=new String[10];
			
			rowArray[0]=Common.get(rs.getString("serialno")); 
			rowArray[1]=Common.get(rs.getString("signno")).substring(0,1) + "000000";
			rowArray[2]=Common.get(rs.getString("signno")).substring(0,3) + "0000";
			rowArray[3]=Common.get(rs.getString("signno")).substring(0,7);
			rowArray[4]=Common.get(rs.getString("signno")).substring(8,11);
			rowArray[5]=Common.get(rs.getString("signno")).substring(11);
			rowArray[6]=Common.get(rs.getString("area"));
			rowArray[7]=Common.get("1");
			rowArray[8]=Common.get("1");
			rowArray[9]=Common.get(rs.getString("area"));
			
			objList.add(rowArray);
		}
		setStateQueryAllSuccess();
		
		getHoldAreaBeforeWork();
		
	} catch (Exception e) {
		LogBean.outputLogError("Exception : "+e.getMessage()+"\n"+sql);
	} finally {
		db.closeAll();
	}
	
	return objList;
}

}


