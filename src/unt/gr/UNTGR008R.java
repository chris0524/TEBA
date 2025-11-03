/*
*<br>程式目的：市有財產增減結存表 
*<br>程式代號：UNTGR008R
*<br>撰寫日期：0950308
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

public class UNTGR008R extends SuperBean{
		
	String q_enterOrg;
	String q_enterOrgName;
	String q_ownership;
	String q_propertyKind;
	String q_fundType;
	String q_valueable;
	String q_enterDateS;
	String q_enterDateE;
	String q_reportType;
	String q_reportYear;
	String q_reportMonth;
	String q_periodType;
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
	public String getQ_propertyKind(){ return checkGet(q_propertyKind); }
	public void setQ_propertyKind(String s){ q_propertyKind=checkSet(s); }
	public String getQ_fundType(){ return checkGet(q_fundType); }
	public void setQ_fundType(String s){ q_fundType=checkSet(s); }
	public String getQ_valueable(){ return checkGet(q_valueable); }
	public void setQ_valueable(String s){ q_valueable=checkSet(s); }
	public String getQ_enterDateS(){ return checkGet(q_enterDateS); }
	public void setQ_enterDateS(String s){ q_enterDateS=checkSet(s); }
	public String getQ_enterDateE(){ return checkGet(q_enterDateE); }
	public void setQ_enterDateE(String s){ q_enterDateE=checkSet(s); }
	public String getQ_reportType(){ return checkGet(q_reportType); }
	public void setQ_reportType(String s){ q_reportType=checkSet(s); }
	public String getQ_reportYear(){ return checkGet(q_reportYear); }
	public void setQ_reportYear(String s){ q_reportYear=checkSet(s); }
	public String getQ_reportMonth(){ return checkGet(q_reportMonth); }
	public void setQ_reportMonth(String s){ q_reportMonth=checkSet(s); }
	public String getQ_periodType(){ return checkGet(q_periodType); }
	public void setQ_periodType(String s){ q_periodType=checkSet(s); }
	
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
    //欄
    int column = 12 ;
    //列
    int row = 22 ;
    try{
    	//執行SQL File檔案
    	Common.callSQLFile(getFileName()+"\\untgr005r.sql "+getEditID()+" "+getQ_enterOrg()+" "+getQ_enterDateS()+" "+getQ_enterDateE()+querySQL+" N "+" "+getQ_ownership()+" UNTGR_untgr008r"+" "+getIsorganmanagerT());
    	
    	/* iReport 中column的名稱----COLUMNS_1_1,COLUMNS_2_1,COLUMNS_3_1.......,COLUMNS_12_1
    	   COLUMNS_1_2,COLUMNS_2_2,COLUMNS_3_2.......,COLUMNS_12_2............,COLUMNS_12_22 */
    	String[] columns = new String[(column*row)+7];
		int k=0;
    	for(int j=1; j<(row+1); j++){
	    	for(int i=1; i<(column+1); i++){
	    		columns[k] = "COLUMNS_"+i+"_"+j;
		    	k++;
	    	}
    	}
    	columns[(column*row)] = "PROPERTYKINDFUNDTYPENAME";
    	columns[(column*row)+1] = "ENTERORGNAME";
    	columns[(column*row)+2] = "ENTERDATESE";
    	columns[(column*row)+3] = "REPORTYM";
    	columns[(column*row)+4] = "PROPERTYKINDFUNDTYPENO";
    	columns[(column*row)+5] = "SIGN";
    	columns[(column*row)+6] = "orgTel";
    	//==========================================
    	String[] SQLName = new String[] {"oldAmount","oldArea","oldBookValue","addAmount","addArea","addBookValue","reduceAmount","reduceArea","reduceBookValue","nowAmount","nowArea","nowBookValue","propertyKindFundTypeName","propertyKindFundTypeNo"};
    	//抓取存在於UNTGR_untgr008r中的資料
    	String execSQL= Common.callSQLFileRead(getFileName()+"\\untgr005r_1.sql",2,getEditID()+":;:UNTGR_untgr008r");
    	//==========================================
        ResultSet rs = db.querySQL(execSQL);
        Vector rowData = new Vector();
    	int dataNo = 0;
    	Object[] data = new Object[(column*row)+7];
    	while(rs.next()){
        	if(dataNo==(column*(row-1))){
        		for(int l=0; l<(column); l++){
    	    		data[dataNo] = rs.getString(SQLName[l]);
    	        	dataNo++;
        		}
        		data[(column*row)] = rs.getString(SQLName[12]);
        		data[(column*row)+1] = Common.getORGANANAME(getQ_enterOrg());
        		data[(column*row)+2] = "中華民國"+Common.MaskCDate(getQ_enterDateS())+"起至"+Common.MaskCDate(getQ_enterDateE())+"止";
        		data[(column*row)+3] = "("+Integer.parseInt(getQ_enterDateE().substring(0,3))+"年度)";
        		data[(column*row)+4] = rs.getString(SQLName[13]);
        		if(q_ownership.equals("4")){
                	data[(column*row)+5] = "製表                                                             財產經管人員                                                              主辦會計人員                                                              機關首長(召集人或其授權代簽人)";
                }else{
                	data[(column*row)+5] = "製表                              覆核                                       財產經管人員                                       主辦事務人員                                       主辦會計人員                                      機關首長";
                }
        		data[(column*row)+6]="連絡電話："+getOrgTel();
        		rowData.addElement(data);
        		data = new Object[(column*row)+7];
    			dataNo = 0;
        	}else if(dataNo<(column*(row-1))){
        		for(int l=0; l<(column); l++){
    	    		data[dataNo] = rs.getString(SQLName[l]);
    	        	dataNo++;
    	    	}
        	}
       	}
        Object[][] data1 = new Object[0][0];
        data1 = (Object[][])rowData.toArray(data1);
        model.setDataVector(data1, columns);
    }catch(Exception x){
       x.printStackTrace();
    }finally{
        db.closeAll();
    }
    return model;
}

public String getOrgTel(){
	String reValue="";
    	Database db = new Database();
    	ResultSet rs;
    	String sql = " select emptel from sysap_emp "
			   + " where 1=1 and organid = " + Common.sqlChar(q_enterOrg)
			   + " and isManager='Y' "
			   + " and groupid not in ('test','sys') ";
    	try{
    		rs = db.querySQL(sql);
    		if(rs.next()){
    			reValue=rs.getString("emptel");
    		}
    	} catch (Exception e) {
    		e.printStackTrace();
    	} finally {
    		db.closeAll();
    	}
	return reValue;
}

}
