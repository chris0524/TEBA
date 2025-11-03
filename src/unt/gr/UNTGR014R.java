/*
*<br>程式目的：財產盤存報告表 
*<br>程式代號：UNTGR014R
*<br>撰寫日期：95/02/14
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

public class UNTGR014R extends SuperBean{
		
	String q_enterOrg;
	String q_enterOrgName;
	String q_ownership;
	String q_propertyKind;
	String q_propertyKind1;
	String q_fundType;
	String q_valuable;
	String q_propertyType;
	String q_reportDate;
	String q_pageWay;
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
	public String getQ_valuable(){ return checkGet(q_valuable); }
	public void setQ_valuable(String s){ q_valuable=checkSet(s); }
	public String getQ_propertyType(){ return checkGet(q_propertyType); }
	public void setQ_propertyType(String s){ q_propertyType=checkSet(s); }
	public String getQ_reportDate(){ return checkGet(q_reportDate); }
	public void setQ_reportDate(String s){ q_reportDate=checkSet(s); }
	public String getQ_pageWay(){ return checkGet(q_pageWay); }
	public void setQ_pageWay(String s){ q_pageWay=checkSet(s); }
	
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

protected String[] querySQL(){
	String querySQL[]= new String[3];
	//珍貴財產、財產性質、基金財產的查詢條件
		if (!"".equals(getQ_propertyKind())){
			querySQL[0]=" "+getQ_propertyKind();
		}else{
			querySQL[0]=" 99";
		}
		if (!"".equals(getQ_fundType())){
			querySQL[0]+=" "+getQ_fundType();
		}else{
			querySQL[0]+=" 99";
		}
		if (!"".equals(getQ_valuable())){
			querySQL[0]+=" "+getQ_valuable();
		}else{
			querySQL[0]+=" 99";
		}
	//財產類別的查詢條件
		if (!"".equals(getQ_propertyType())){
			querySQL[1]=" "+getQ_propertyType();
		}else{
			querySQL[1]=" 99";
		}
	//換頁方式
		if("1".equals(getQ_pageWay()) && "1".equals(getQ_propertyKind1())){
			querySQL[2] = " UNTGR_untgr014r";
		}else if("2".equals(getQ_pageWay()) && "1".equals(getQ_propertyKind1())){
			querySQL[2] = " UNTGR_untgr014r1";
		}else if("1".equals(getQ_pageWay()) && "2".equals(getQ_propertyKind1())){
			querySQL[2] = " UNTGR_untgr014r2";
		}else if("2".equals(getQ_pageWay()) && "2".equals(getQ_propertyKind1())){
			querySQL[2] = " UNTGR_untgr014r3";
		}
return querySQL;
}	
	
public DefaultTableModel getResultModel() throws Exception{
    DefaultTableModel model = new javax.swing.table.DefaultTableModel();
    Database db = new Database();
    String querySQL[] = querySQL();
    
    String signName = "";
    String signName2 = "";
	if(getQ_propertyKind1().equals("1")){
		signName = "財產主管人員";
		signName2 = "財產管理人員";
	}else if(getQ_propertyKind1().equals("2")){
		signName = "物品主管人員";
		signName2 = "物品管理人員";
	}
    //欄
    int column = 9 ;
    try{
    	//執行SQL File檔案
    	Common.callSQLFile(getFileName()+"\\untgr010r.sql "+getEditID()+" "+getQ_enterOrg()+" "+getQ_reportDate()+" "+getQ_reportDate()+querySQL[0]+" "+getQ_ownership()+querySQL[2]+querySQL[1]+" "+getQ_propertyKind1()+" "+getIsorganmanagerT());
    	// iReport 中column的名稱----COLUMNS_1,COLUMNS_2,COLUMNS_3.......,COLUMNS_10
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
    	//抓取存在於UNTGR_untgr014r中的資料
    	String execSQL= Common.callSQLFileRead(getFileName()+"\\untgr010r_1.sql",2,getEditID()+":;:"+querySQL[2].trim());
    	//System.out.println(execSQL);
    	//==========================================
    	String[] SQLName = new String[] {"propertyNo1","propertyNo2","propertyNo3","propertyNo4","propertyNo5","propertyName","propertyUnit","nowAmount","nowBookValue","propertyKindFundTypeName","propertyKindFundTypeNo"};
        ResultSet rs = db.querySQL(execSQL);
        Vector rowData = new Vector();
    	while(rs.next()){
        	Object[] data = new Object[column+5];
        	for(int i=0; i<column; i++){
        		data[i] = UNTGRQUERYSQL.nextLine(rs.getString(SQLName[i]));
        	}
        	data[column] = UNTGRQUERYSQL.nextLine(rs.getString(SQLName[column]));
    		data[(column)+1] = Common.getORGANANAME(getQ_enterOrg());
    		data[(column)+2] = "至中華民國"+Common.MaskDate(getQ_reportDate())+"止";
    		data[(column)+3] = UNTGRQUERYSQL.nextLine(rs.getString(SQLName[(column+1)]));
    		if(q_ownership.equals("4")){//權屬(目前選單沒有4)
    			data[(column)+4] = "製表                                                             "+signName+"                                                             主辦會計人員                                                               機關首長(召集人或其授權代簽人)";
            }else{
            	data[(column)+4]  = signName2+"                                             "+signName+"                                                  主辦會計人員                                                  機關(單位)首長                                         ";
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
