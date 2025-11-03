/*
*<br>程式目的：市有財產目錄(普通公務機關用) 
*<br>程式代號：UNTGR010R
*<br>撰寫日期：95/05/15
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

public class UNTGR010R extends SuperBean{
		
	String q_enterOrg;
	String q_enterOrgName;
	String q_ownership;
	String q_propertyKind1;
	String q_propertyKind;
	String q_fundType;
	String q_valueable;
	String q_reportDate;
	String q_reportType;
	String q_reportYear;
	String q_enterDateS;
	String q_enterDateE;
	String q_isorganmanager;
	String isorganmanagerT;
	
	public String getIsorganmanagerT(){ return checkGet(isorganmanagerT); }
	public void setIsorganmanagerT(String s){ isorganmanagerT=checkSet(s); }	
	public String getQ_isorganmanager(){ return checkGet(q_isorganmanager); }
	public void setQ_isorganmanager(String s){ q_isorganmanager=checkSet(s); }	
	public String getQ_enterOrg(){ return checkGet(q_enterOrg); }
	public void setQ_enterOrg(String s){ q_enterOrg=checkSet(s); }
	public String getQ_enterOrgName(){ return checkGet(q_enterOrgName); }
	public void setQ_enterOrgName(String s){ q_enterOrgName=checkSet(s); }
	public String getQ_ownership(){ return checkGet(q_ownership); }
	public void setQ_ownership(String s){ q_ownership=checkSet(s); }
	public String getQ_propertyKind1(){ return checkGet(q_propertyKind1); }
	public void setQ_propertyKind1(String s){ q_propertyKind1=checkSet(s); }
	public String getQ_propertyKind(){ return checkGet(q_propertyKind); }
	public void setQ_propertyKind(String s){ q_propertyKind=checkSet(s); }
	public String getQ_fundType(){ return checkGet(q_fundType); }
	public void setQ_fundType(String s){ q_fundType=checkSet(s); }
	public String getQ_valueable(){ return checkGet(q_valueable); }
	public void setQ_valueable(String s){ q_valueable=checkSet(s); }
	public String getQ_reportDate(){ return checkGet(q_reportDate); }
	public void setQ_reportDate(String s){ q_reportDate=checkSet(s); }
	public String getQ_reportType(){ return checkGet(q_reportType); }
	public void setQ_reportType(String s){ q_reportType=checkSet(s); }
	public String getQ_reportYear(){ return checkGet(q_reportYear); }
	public void setQ_reportYear(String s){ q_reportYear=checkSet(s); }
	public String getQ_enterDateS(){ return checkGet(q_enterDateS); }
	public void setQ_enterDateS(String s){ q_enterDateS=checkSet(s); }
	public String getQ_enterDateE(){ return checkGet(q_enterDateE); }
	public void setQ_enterDateE(String s){ q_enterDateE=checkSet(s); }
	public String getQ_valuable(){ return checkGet(q_valueable); }
	public void setQ_valuable(String s){ q_valueable=checkSet(s); }
	
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

//查詢條件
protected String querySQL(){
	String querySQL="";
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
return querySQL;
}	
	
public DefaultTableModel getResultModel() throws Exception{
    DefaultTableModel model = new javax.swing.table.DefaultTableModel();
    Database db = new Database();
    String querySQL = querySQL();
    //判斷為財產或非消耗品
    String table="";
    String signName="";
	if(getQ_propertyKind1().equals("1")){
		table="UNTGR_untgr010r";
		signName="財產經管人員";
	}else if(getQ_propertyKind1().equals("2")){
		table="UNTGR_untgr010r1";
		signName="物品經管人員";
	}
    //欄
    int column = 12 ;
    try{
    	//執行SQL File檔案
    	Common.callSQLFile(getFileName()+"\\untgr010r.sql "+getEditID()+" "+getQ_enterOrg()+" "+getQ_enterDateS()+" "+getQ_enterDateE()+querySQL+" N "+getQ_ownership()+" "+table+" 99 "+getQ_propertyKind1()+" "+getIsorganmanagerT());
    	// iReport 中column的名稱----COLUMNS_1,COLUMNS_2,COLUMNS_3.......,COLUMNS_12
    	String[] columns = new String[column+5];
		int k=0;
    	for(int i=1; i<(column+1); i++){
    		columns[k] = "COLUMNS_"+i;
	    	k++;
    	}
    	columns[column] = "PROPERTYKINDFUNDTYPENAME";
    	columns[(column)+1] = "ENTERORGNAME";
    	columns[(column)+2] = "ENTERDATESE";
    	columns[(column)+3] = "PROPERTYKINDFUNDTYPENO";
    	columns[(column)+4] = "SIGN";
    	//==========================================
    	//抓取存在於UNTGR_untgr010r中的資料
    	String execSQL= Common.callSQLFileRead(getFileName()+"\\untgr010r_1.sql",2,getEditID()+":;:"+table);
    	System.out.println(execSQL);
    	//==========================================
    	String[] SQLName = new String[] {"propertyNo1","propertyNo2","propertyNo3","propertyNo4","propertyNo5","propertyName","propertyUnit","oldAmountOldArea","addAmountAddArea","reduceAmountReduceArea","nowAmountNowArea","nowBookValue","propertyKindFundTypeName","propertyKindFundTypeNo"};
        ResultSet rs = db.querySQL(execSQL);
        Vector rowData = new Vector();
    	while(rs.next()){
        	Object[] data = new Object[column+5];
        	for(int i=0; i<column; i++){
        		data[i] = UNTGRQUERYSQL.nextLine(rs.getString(SQLName[i]));
        	}
        	data[column] = UNTGRQUERYSQL.nextLine(rs.getString(SQLName[column]));
    		data[(column)+1] = Common.getORGANANAME(getQ_enterOrg());
    		data[(column)+2] = "中華民國"+Common.MaskCDate(getQ_enterDateE());
    		data[(column)+3] = UNTGRQUERYSQL.nextLine(rs.getString(SQLName[(column+1)]));
    		if(q_ownership.equals("4")){
            	data[(column)+4] = "製表                                                             "+signName+"                                                              主辦會計人員                                                              機關首長(召集人或其授權代簽人)";
            }else{
            	data[(column)+4] = "製表                              覆核                                       "+signName+"                                       主辦事務人員                                       主辦會計人員                                      機關首長";
            }
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
