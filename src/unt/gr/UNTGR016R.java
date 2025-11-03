/*
*<br>程式目的：土地使用關係統計表 
*<br>程式代號：untgr016r
*<br>撰寫日期：95/05/17
*<br>程式作者：Cherry
*<br>--------------------------------------------------------
*<br>修改作者　　修改日期　　　修改目的
*<br>--------------------------------------------------------
*/

package unt.gr;

import java.sql.ResultSet;
import java.util.Vector;

import javax.swing.table.DefaultTableModel;

import util.*;

public class UNTGR016R extends SuperBean{
		
	String q_enterOrg;
	String q_enterOrgName;
	String q_ownership;
	String q_valuable;
	String q_propertyKind;
	String q_verify;
	String q_fundType;

	public String getQ_enterOrg(){ return checkGet(q_enterOrg); }
	public void setQ_enterOrg(String s){ q_enterOrg=checkSet(s); }
	public String getQ_enterOrgName(){ return checkGet(q_enterOrgName); }
	public void setQ_enterOrgName(String s){ q_enterOrgName=checkSet(s); }
	public String getQ_ownership(){ return checkGet(q_ownership); }
	public void setQ_ownership(String s){ q_ownership=checkSet(s); }
	public String getQ_valuable(){ return checkGet(q_valuable); }
	public void setQ_valuable(String s){ q_valuable=checkSet(s); }
	public String getQ_propertyKind(){ return checkGet(q_propertyKind); }
	public void setQ_propertyKind(String s){ q_propertyKind=checkSet(s); }
	public String getQ_verify(){ return checkGet(q_verify); }
	public void setQ_verify(String s){ q_verify=checkSet(s); }
	public String getQ_fundType(){ return checkGet(q_fundType); }
	public void setQ_fundType(String s){ q_fundType=checkSet(s); }
	
	String isOrganManager;
	String isAdminManager;
	String organID;    
	public String getOrganID() { return checkGet(organID); }
	public void setOrganID(String s) { organID = checkSet(s); }
	public String getIsOrganManager() { return checkGet(isOrganManager); }
	public void setIsOrganManager(String s) { isOrganManager = checkSet(s); }
	public String getIsAdminManager() { return checkGet(isAdminManager); }
	public void setIsAdminManager(String s) { isAdminManager = checkSet(s); }

	String fileName;

	public String getFileName() { return checkGet(fileName); }
	public void setFileName(String s) { fileName = checkSet(s); }

	protected String querySQL(){
		String querySQL="";
		//珍貴財產、財產性質、基金財產的查詢條件
			if (!"".equals(getQ_propertyKind())){
				querySQL+=" "+getQ_propertyKind();
			}else{
				querySQL+=" 99";
			}
			if (!"".equals(getQ_fundType())){
				querySQL+=" "+getQ_fundType();
			}else{
				querySQL+=" 99";
			}
			if (!"".equals(getQ_valuable())){
				querySQL+=" "+getQ_valuable();
			}else{
				querySQL+=" 99";
			}
	return querySQL;
	}	
		
	public DefaultTableModel getResultModel() throws Exception{
	    DefaultTableModel model = new javax.swing.table.DefaultTableModel();
	    Database db = new Database();
	    String querySQL = querySQL();
	    String todayDate = Common.MaskDate(Datetime.getYYYMMDD());
	    String ownershipName="";
	    if(q_ownership.equals("1")){
	    	ownershipName = "市有";
	    }else if(q_ownership.equals("2")){
	    	ownershipName = "國有";
	    }
	    //欄
	    int column = 4 ;
	    try{
	    	//執行SQL File檔案
	    	Common.callSQLFile(getFileName()+"\\untgr016r.sql "+getEditID()+" "+getQ_enterOrg()+" "+getQ_ownership()+querySQL);
	    	// iReport 中column的名稱----COLUMNS_1,COLUMNS_2,COLUMNS_3,COLUMNS_4
	    	String[] columns = new String[column+5];
			int k=0;
	    	for(int i=1; i<(column+1); i++){
	    		columns[k] = "COLUMNS_"+i;
		    	k++;
	    	}
	    	columns[column] = "ENTERORGNAME";
	    	columns[(column)+1] = "TITLENAME";
	    	columns[(column)+2] = "DEADLINEDATE";
	    	columns[(column)+3] = "PROPERTYKINDFUNDTYPENO";
	    	columns[(column)+4] = "PROPERTYKINDFUNDTYPENAME";
	    	//==========================================
	    	//抓取存在於UNTGR_untgr016r中的資料
	    	String execSQL= UNTGRQUERYSQL.getUntGrQuerySqlUntgr016r(getEditID());
	    	//==========================================
	    	String[] SQLName = new String[] {"item","amount","superficial","value","propertyKindFundTypeNo","propertyKindFundTypeName"};
	        ResultSet rs = db.querySQL(execSQL);
	        Vector rowData = new Vector();
	    	while(rs.next()){
	        	Object[] data = new Object[column+5];
	        	for(int i=0; i<column; i++){
	        		data[i] = rs.getString(SQLName[i]);
	        	}
	        	data[column] = Common.getORGANANAME(getQ_enterOrg());;
	        	data[column+1] = ownershipName+"土地使用關係統計表";
	        	data[column+2] = "截止日期："+todayDate;
	        	data[column+3] = rs.getString(SQLName[column]);
	        	data[column+4] = rs.getString(SQLName[column+1]);
	    		rowData.addElement(data);
	       	}
	        Object[][] data = new Object[0][0];
	        data = (Object[][])rowData.toArray(data);
	        model.setDataVector(data, columns);
	    }catch(Exception x){
	       x.printStackTrace();
	    }finally{
	        db.closeAll();
	    }
	    
	    return model;

	}
}
