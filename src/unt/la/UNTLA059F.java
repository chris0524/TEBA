/*
*<br>程式目的：土地合併分割重測重劃作業－增加單明細分割挑選
*<br>程式代號：untla059f
*<br>程式日期：
*<br>程式作者：Yuan-Ren Jheng
*<br>--------------------------------------------------------
*<br>修改作者　　修改日期　　　修改目的
*<br>--------------------------------------------------------
*/

package unt.la;

import java.sql.ResultSet;
import java.util.ArrayList;

import util.Common;
import util.*;

public class UNTLA059F extends UNTLA059F_2{
	
	public void beforeWork_Add(){	
		
		beforeWork_getCause_FromUNTLA_Land();
		beforeWork_getCause_From_UNTLA_Mergedivision();
		
		beforeWork_Add_dropTable_Untla_Land_Temp();
		beforeWork_Add_createTable_Untla_Land_Temp();
		beforeWork_Add_delete_Untla_Land_Temp();
		beforeWork_Add_dropTable_Untla_Land_Temp2();
		
		beforeWork_Add_dropTable_Untla_Value_Temp();
		beforeWork_Add_createTable_Untla_Value_Temp();
		beforeWork_Add_deleteFrom_Untla_Value_Temp();
		beforeWork_Add_dropTable_Untla_Value_Temp2();
		
		beforeWork_Add_dropTable_Untla_Manage_Temp();
		beforeWork_Add_createTable_Untla_Manage_Temp();
		beforeWork_Add_deleteFrom_Untla_Manage_Temp();
		beforeWork_Add_dropTable_Untla_Manage_Temp2();
		
		beforeWork_Add_dropTable_UNTLA_Attachment_Temp();
		beforeWork_Add_createTable_UNTLA_Attachment_Temp();
		beforeWork_Add_deleteFrom_UNTLA_Attachment_Temp();
		beforeWork_Add_dropTable_UNTLA_Attachment_Temp2();
		
		for(int i=1;i<=Integer.parseInt(this.getAmount());i++){
			
			beforeWork_Add_createTable_Untla_Land_Temp2_from_Untla_Land();
			beforeWork_Add_createTable_Untla_Value_Temp2_from_Untla_Value();
			beforeWork_Add_createTable_Untla_Manage_Temp2_from_Untla_Manage();		
			beforeWork_Add_createTable_UNTLA_Attachment_Temp2_from_UNTLA_Attachment();
			
			beforeWork_Add_updateArea_from_Untla_Manage_Temp2();
			beforeWork_Add_updateArea_from_Untla_Attachment_Temp2();
			
			execGetMergeDivisionData();	
			beforeWork_Add_update_Untla_Land_Temp2_forSerialNo_and_otherData(i);
			beforeWork_Add_update_Untla_Land_Temp2_forNotes(this.getSourceKind_before());
			beforeWork_Add_update_Untla_Value_Temp2_forSerialNo_from_Untla_Land_Temp2();
			beforeWork_Add_update_Untla_Manage_Temp2_forSerialNo_from_Untla_Land_Temp2();
			beforeWork_Add_update_UNTLA_Attachment_Temp2_forSerialNo_from_Untla_Land_Temp2();
			
			beforeWork_Add_insertTo_Untla_Land_Temp_from_Untla_Land_Temp2();		
			beforeWork_Add_insertTo_Untla_Value_Temp_from_Untla_Value_Temp2();
			beforeWork_Add_insertTo_Untla_Manage_Temp_from_Untla_Manage_Temp2();
			beforeWork_Add_insertTo_UNTLA_Attachment_Temp_from_UNTLA_Attachment_Temp2();
							
			
			beforeWork_Add_dropTable_Untla_Land_Temp2();		
			beforeWork_Add_dropTable_Untla_Value_Temp2();
			beforeWork_Add_dropTable_Untla_Manage_Temp2();
			beforeWork_Add_dropTable_UNTLA_Attachment_Temp2();
		}	
			
		getHoldAreaBeforeWork();
		
		extend_beforeWork_Add();
	}	


	public void afterWork(){
		execGetMergeDivisionData();	
		beforeWork_getCause_FromUNTLA_Land();
		execValueCalculate();
		afterWork_updateTo_Untla_ReduceDetail();		
		
		afterWork_update_Untla_Land_Temp_for_otherData();		
		afterWork_update_Untla_Value_Temp_for_otherData();
		afterWork_update_Untla_Manage_Temp_for_otherData();
		afterWork_update_Untla_Attachment_Temp_for_otherData();
		
		afterWork_insertTo_Untla_Land();
		afterWork_dropTable_Untla_Land_Temp();
		afterWork_insertTo_Untla_Value();
		afterWork_dropTable_Untla_Value_Temp();
		afterWork_insertTo_Untla_Manage();
		afterWork_dropTable_Untla_Manage_Temp();
		afterWork_insertTo_UNTLA_Attachment();
		afterWork_dropTable_UNTLA_Attachment_Temp();
		
		extend_afterWork();
		
		
	}

	
	private void execValueCalculate(){
		execValueCalculate_update_Untla_Land_Temp_for_bookValue_from_Untla_Land();

		if("02".equals(getSourceKind_Add()) || "03".equals(getSourceKind_Add()) || "11".equals(getSourceKind_Add())){
			execValueCalculate_update_Untla_Land_Temp_for_bookValue_by_NotMaxSerialNo();
			execValueCalculate_update_Untla_Land_Temp_for_bookValue_by_MaxSerialNo();
			execValueCalculate_update_Untla_Land_Temp_for_bookUnit();
		}else{
			execValueCalculate_update_Untla_Land_Temp_for_bookValue_and_bookUnit();
		}
	}
	
	
// <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<	減損單	>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>	
	
//依查詢欄位查詢多筆資料
public ArrayList queryAll_Reduce() throws Exception{
	Database db = new Database();
	ArrayList objList=new ArrayList();
	int counter=0;
	
	String sql="select " +
					" a.enterorg, a.ownership, a.differencekind, a.propertyno, a.serialno, a.signno," +
					" a.holdArea, a.bookValue " +
				" from UNTLA_ReduceDetail a where 1=1" +
					" and enterorg = " + Common.sqlChar(this.getEnterOrg()) +
					" and ownership = " + Common.sqlChar(this.getOwnership()) +
					" and differencekind = " + Common.sqlChar(this.getDifferenceKind()) +
					" and caseNo = " + Common.sqlChar(this.getCaseNo_Reduce()) + 
					" and mergedivisionbatch is null" ;
	
	sql+=" order by a.enterOrg, a.ownership, a.caseNo, a.signno ";

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


// <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<	增加單	>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>


	
public void getHoldAreaBeforeWork(){
	String sql="select holdarea from untla_ReduceDetail" +
				" where 1=1" +
					" and enterorg=" + Common.sqlChar(this.getEnterOrg()) +
					" and ownership=" + Common.sqlChar(this.getOwnership()) +
					" and caseNo=" + Common.sqlChar(this.getCaseNo_Reduce()) +
					" and differencekind=" + Common.sqlChar(this.getDifferenceKind()) +					
					" and propertyNo=" + Common.sqlChar(this.getPropertyNo_Reduce()) +
					" and serialNo=" + Common.sqlChar(this.getSerialNo_Reduce()) ;
				
	this.setBefore_holdArea(this.getNameData("holdarea", sql));
}

public void cencelWork(){	
	afterWork_dropTable_Untla_Land_Temp();
	afterWork_dropTable_Untla_Value_Temp();
	afterWork_dropTable_Untla_Manage_Temp();
	
	extend_cencelWork();
}

//依查詢欄位查詢多筆資料
public ArrayList queryAll_Add() throws Exception{
	Database db = new Database();
	ArrayList objList=new ArrayList();
	int counter=0;
	
	String sql="select " +
					" a.serialno, a.signno," +
					" a.area, a.holdnume, a.holddeno, a.holdarea"+
				" from UNTLA_LAND_"+ Common.get(this.getEditID()).replace(".", "") +" a where 1=1";
	
	sql+=" order by a.enterorg, a.ownership, a.propertyno, a.serialno ";

	try {
		LogBean.outputLogDebug(sql);
		
		ResultSet rs = db.querySQL_NoChange(sql);
		while (rs.next()){
			counter++;
			String rowArray[]=new String[10];
			
			rowArray[0]=Common.get(rs.getString("serialno")); 
			rowArray[1]=Common.get(rs.getString("signno")).substring(0,1) + "000000";
			rowArray[2]=Common.get(rs.getString("signno")).substring(0,3) + "0000";
			rowArray[3]=Common.get(rs.getString("signno")).substring(0,7);
			rowArray[4]=Common.get(rs.getString("signno")).substring(7,11);
			rowArray[5]=Common.get(rs.getString("signno")).substring(11);
			rowArray[6]=Common.get(rs.getString("area"));
			rowArray[7]=Common.get(rs.getString("holdnume"));
			rowArray[8]=Common.get(rs.getString("holddeno"));
			rowArray[9]=Common.get(rs.getString("holdarea"));
			
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

//========================================================		
protected void extend_getInsertSQL(){}
protected void extend_getUpdateSQL(){}
protected void extend_getDeleteSQL(){}
protected void extend_beforeWork_Add(){}
protected void extend_afterWork(){
	
	
	
}
protected void extend_cencelWork(){}
}


