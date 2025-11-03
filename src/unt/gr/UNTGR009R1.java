/*
*<br>程式目的：國有財產增減結存表 (地方政府使用)
*<br>程式代號：UNTGR009R1
*<br>撰寫日期：
*<br>程式作者：
*<br>--------------------------------------------------------
*<br>修改作者　　修改日期　　　修改目的
*<br>--------------------------------------------------------
*/

package unt.gr;

import java.sql.ResultSet;
import java.util.Vector;

import javax.swing.table.DefaultTableModel;

import util.*;

public class UNTGR009R1 extends SuperBean{
	
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
	String q_verify;
	
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
	public String getQ_verify() {return checkGet(q_verify);}
	public void setQ_verify(String qVerify) {q_verify = checkSet(qVerify);}
	
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

    try{
    	//執行SQL File檔案
    	
    	Common.callSQLFile(getFileName()+"\\untgr005r.sql "+getEditID()+" "+getQ_enterOrg()+" "+getQ_enterDateS()+" "+getQ_enterDateE()+querySQL+" N "+getQ_ownership()+" UNTGR_untgr009r"+" "+getIsorganmanagerT());
    	
    	Common.callSQLFile(getFileName()+"\\untgr009r1.sql "+getEditID());

    	/* iReport 中column的名稱----COLUMNS_1_1,COLUMNS_2_1,COLUMNS_3_1.......,COLUMNS_12_1
    	   COLUMNS_1_2,COLUMNS_2_2,COLUMNS_3_2.......,COLUMNS_12_2............,COLUMNS_12_15 */
    	String[] columns = {"COLUMNS_1_1", "COLUMNS_2_1", "COLUMNS_3_1", "COLUMNS_4_1", "COLUMNS_5_1", "COLUMNS_6_1", "COLUMNS_7_1", "COLUMNS_8_1", "COLUMNS_9_1", "COLUMNS_10_1", "COLUMNS_11_1", "COLUMNS_12_1",
    						"COLUMNS_1_2", "COLUMNS_2_2", "COLUMNS_3_2", "COLUMNS_4_2", "COLUMNS_5_2", "COLUMNS_6_2", "COLUMNS_7_2", "COLUMNS_8_2", "COLUMNS_9_2", "COLUMNS_10_2", "COLUMNS_11_2", "COLUMNS_12_2",
    						"COLUMNS_1_3", "COLUMNS_2_3", "COLUMNS_3_3", "COLUMNS_4_3", "COLUMNS_5_3", "COLUMNS_6_3", "COLUMNS_7_3", "COLUMNS_8_3", "COLUMNS_9_3", "COLUMNS_10_3", "COLUMNS_11_3", "COLUMNS_12_3",
    						"COLUMNS_1_4", "COLUMNS_2_4", "COLUMNS_3_4", "COLUMNS_4_4", "COLUMNS_5_4", "COLUMNS_6_4", "COLUMNS_7_4", "COLUMNS_8_4", "COLUMNS_9_4", "COLUMNS_10_4", "COLUMNS_11_4", "COLUMNS_12_4",
    						"COLUMNS_1_5", "COLUMNS_2_5", "COLUMNS_3_5", "COLUMNS_4_5", "COLUMNS_5_5", "COLUMNS_6_5", "COLUMNS_7_5", "COLUMNS_8_5", "COLUMNS_9_5", "COLUMNS_10_5", "COLUMNS_11_5", "COLUMNS_12_5",
    						"COLUMNS_1_6", "COLUMNS_2_6", "COLUMNS_3_6", "COLUMNS_4_6", "COLUMNS_5_6", "COLUMNS_6_6", "COLUMNS_7_6", "COLUMNS_8_6", "COLUMNS_9_6", "COLUMNS_10_6", "COLUMNS_11_6", "COLUMNS_12_6",
    						"COLUMNS_1_7", "COLUMNS_2_7", "COLUMNS_3_7", "COLUMNS_4_7", "COLUMNS_5_7", "COLUMNS_6_7", "COLUMNS_7_7", "COLUMNS_8_7", "COLUMNS_9_7", "COLUMNS_10_7", "COLUMNS_11_7", "COLUMNS_12_7",
    						"COLUMNS_1_8", "COLUMNS_2_8", "COLUMNS_3_8", "COLUMNS_4_8", "COLUMNS_5_8", "COLUMNS_6_8", "COLUMNS_7_8", "COLUMNS_8_8", "COLUMNS_9_8", "COLUMNS_10_8", "COLUMNS_11_8", "COLUMNS_12_8",
    						"COLUMNS_1_9", "COLUMNS_2_9", "COLUMNS_3_9", "COLUMNS_4_9", "COLUMNS_5_9", "COLUMNS_6_9", "COLUMNS_7_9", "COLUMNS_8_9", "COLUMNS_9_9", "COLUMNS_10_9", "COLUMNS_11_9", "COLUMNS_12_9",
    						
    						"PROPERTYKINDFUNDTYPENAME", "ENTERORGNAME", "ENTERDATESE", "DATE", "PROPERTYKINDFUNDTYPENO", "ownershipTitle"};
    	
    	//==========================================
    	String[] SQLName = new String[] {"oldAmount","oldArea","oldBookValue","addAmount","addArea","addBookValue","reduceAmount","reduceArea","reduceBookValue","nowAmount","nowArea","nowBookValue","propertyKindFundTypeName","propertyKindFundTypeNo"};
    	//抓取存在於UNTGR_untgr009r1中的資料
    	String execSQL= Common.callSQLFileRead(getFileName()+"\\untgr009r1_1.sql",2,getEditID()+":;:UNTGR_untgr009r1");
    	//==========================================
        ResultSet rs = db.querySQL(execSQL);
        
        Vector rowData = new Vector();
    	int dataNo = 0;
    	String date = Common.MaskCDate(Datetime.getYYYMMDD());
    	String rowArray[]=new String[columns.length];    	
    	
    	while(rs.next()){
    		rowArray[(dataNo*12) + 0] = rs.getString(SQLName[0]);
    		rowArray[(dataNo*12) + 1] = rs.getString(SQLName[1]);
    		rowArray[(dataNo*12) + 2] = rs.getString(SQLName[2]);
    		rowArray[(dataNo*12) + 3] = rs.getString(SQLName[3]);
    		rowArray[(dataNo*12) + 4] = rs.getString(SQLName[4]);
    		rowArray[(dataNo*12) + 5] = rs.getString(SQLName[5]);
    		rowArray[(dataNo*12) + 6] = rs.getString(SQLName[6]);
    		rowArray[(dataNo*12) + 7] = rs.getString(SQLName[7]);
    		rowArray[(dataNo*12) + 8] = rs.getString(SQLName[8]);
    		rowArray[(dataNo*12) + 9] = rs.getString(SQLName[9]);
    		rowArray[(dataNo*12) + 10] = rs.getString(SQLName[10]);
    		rowArray[(dataNo*12) + 11] = rs.getString(SQLName[11]);    		
    		
    		dataNo++;
    
       	}
    
    	rowArray[108] = "";
    	rowArray[109] = Common.getORGANANAME(getQ_enterOrg());
    	rowArray[110] = "中華民國"+Common.MaskCDate(getQ_enterDateS())+"起至"+Common.MaskCDate(getQ_enterDateE())+"止";
    	rowArray[111] = "製表日期："+date;
    	rowArray[112] = "";    	
    	rowArray[113] = getTableFile.getOwnershipName(getQ_ownership()) + "財產增減結存表 (地方政府使用)";
    	

		rowData.addElement(rowArray);

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

}
