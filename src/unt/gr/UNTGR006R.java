/*
*<br>程式目的：市有財產增減表 
*<br>程式代號：UNTGR006R
*<br>撰寫日期：0950313
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

public class UNTGR006R extends SuperBean{
		
	String q_enterOrg;
	String q_enterOrgName;
	String q_ownership;
	String q_propertyKind;
	String q_propertyKind1;
	String q_fundType;
	String q_valueable;
	String q_enterDateS;
	String q_enterDateE;
	String q_reportType;
	String q_reportYear;
	String q_reportMonth;
	String q_periodType;
	String q_reportSeason;		
	String q_grass;
	String q_isorganmanager;
	String isorganmanagerT;
	
	public String getIsorganmanagerT(){ return checkGet(isorganmanagerT); }
	public void setIsorganmanagerT(String s){ isorganmanagerT=checkSet(s); }
	public String getQ_isorganmanager(){ return checkGet(q_isorganmanager); }
	public void setQ_isorganmanager(String s){ q_isorganmanager=checkSet(s); }
	public String getQ_grass(){ return checkGet(q_grass); }
	public void setQ_grass(String s){ q_grass=checkSet(s); }
	public String getQ_enterOrg(){ return checkGet(q_enterOrg); }
	public void setQ_enterOrg(String s){ q_enterOrg=checkSet(s); }
	public String getQ_enterOrgName(){ return checkGet(q_enterOrgName); }
	public void setQ_enterOrgName(String s){ q_enterOrgName=checkSet(s); }
	public String getQ_ownership(){ return checkGet(q_ownership); }
	public void setQ_ownership(String s){ q_ownership=checkSet(s); }
	public String getQ_propertyKind(){ return checkGet(q_propertyKind); }
	public void setQ_propertyKind(String s){ q_propertyKind=checkSet(s); }
	public String getQ_propertyKind1(){ return checkGet(q_propertyKind1); }
	public void setQ_propertyKind1(String s){ q_propertyKind1=checkSet(s); }
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
	public String getQ_reportSeason(){ return checkGet(q_reportSeason); }
	public void setQ_reportSeason(String s){ q_reportSeason=checkSet(s); }
	
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
    String titleName = "";
    String reprotTypeName = "";
    String noDataName = "";
    String cloumn_15="";
    String cloumn_16="";
    String cloumn_17="";
    String cloumn_18="";
    String propertyKindFundTypeName="";
    //判斷財產性質
    String propertyKindName = "";
    if(!"".equals(getQ_propertyKind())){
    	if("01".equals(getQ_propertyKind())){
    		propertyKindName = "公務用";
    	}else if("02".equals(getQ_propertyKind())){
    		propertyKindName = "公共用";
    	}else if("03".equals(getQ_propertyKind())){
    		if(!"".equals(getQ_fundType())){
    			propertyKindName = "事業用　"+MaxClosingYM.getCountValues("select b.codename as count from sysca_code b where b.codekindid='FUD' and b.codeid="+Common.sqlChar(getQ_fundType()));
    		}else{
    			propertyKindName = "事業用";
    		}
    	}else if("04".equals(getQ_propertyKind())){
    		propertyKindName = "非公用";
    	}
    }
    //判斷為財產或非消耗品
    String table="";
    String propertyKind1Name="";
	if(getQ_propertyKind1().equals("1")){
		table="untgr006r";
		propertyKind1Name="財產";
		cloumn_15 = "分類編號";
		cloumn_16 = "財產號碼及品質規格";
		cloumn_17 = "財產經管人員";
		cloumn_18 = "說明：1.本表每季填送一式乙份(不要加裝封底面)隨同財產分類量值統計季報表併寄。\n" +  
		"            2.本表應分類加註小計。";
		propertyKindFundTypeName = "財產性質分類：";
	}else if(getQ_propertyKind1().equals("2")){
		table="untgr006r1";
		propertyKind1Name="非消耗品";
		cloumn_15 = "物品分號";
		cloumn_16 = "物品號碼及品質規格";
		cloumn_17 = "物品經管人員";
		cloumn_18 = "";
		propertyKindFundTypeName = "物品性質分類：";
	}
    int count = 0;
    if(getQ_reportType().equals("1")){
    	titleName = propertyKind1Name+"增減月報表";
    	reprotTypeName = "";
    	noDataName = "月";
    }else if(getQ_reportType().equals("2")){
    	titleName = propertyKind1Name+"增減季報表";
    	reprotTypeName = "(第"+getQ_reportSeason()+"季)";
    	noDataName = "季";
    }else if(getQ_reportType().equals("3")){
    	titleName = propertyKind1Name+"增減表";
    	reprotTypeName = "(半年報)";
    	noDataName = "期";
    }else if(getQ_reportType().equals("4")){
    	titleName = propertyKind1Name+"增減表";
    	reprotTypeName = "";
    	noDataName = "期";
    }
    //欄
    int column = 18 ;
    //是否為新草衙
    String grass="";
    if (!"".equals(getQ_grass())){
    	grass =" "+getQ_grass();
	}else{
		grass =" 99";
	}
    try{
    	//執行SQL File檔案
    	Common.callSQLFile(getFileName()+"\\untgr006r.sql "+getEditID()+" "+getQ_enterOrg()+" "+getQ_enterDateS()+" "+getQ_enterDateE()+" "+getQ_propertyKind()+" "+getQ_fundType()+" "+" N "+" "+getQ_ownership()+" "+"untgr006r"+" "+getQ_propertyKind1()+" "+"99"+" "+getIsorganmanagerT());
    	// iReport 中column的名稱----COLUMNS_1,COLUMNS_2,COLUMNS_3.......,COLUMNS_14
    	String[] columns = new String[column+6];
		int k=0;
    	for(int i=1; i<(column+1); i++){
    		columns[k] = "COLUMNS_"+i;
	    	k++;
    	}
    	columns[column] = "PROPERTYKINDFUNDTYPENAME";
    	columns[(column)+1] = "ENTERORGNAME";
    	columns[(column)+2] = "REPORTTYPENAME";
    	columns[(column)+3] = "ENTERDATESE";
    	columns[(column)+4] = "PROPERTYKINDFUNDTYPENO";
    	columns[(column)+5] = "SIGN";
    	//==========================================
    	//抓取存在於UNTGR_untgr006r中的資料
    	//String execSQL= UNTGRQUERYSQL.getUntGrQuerySqlAdjust(getEditID(),"untgr006r");
    	String execSQL= Common.callSQLFileRead(getFileName()+"//untgr006r_1.sql",2,getEditID()+":;:"+table);
    	//==========================================
    	ResultSet rs = db.querySQL(execSQL);
        Vector rowData = new Vector();
    	while(rs.next()){
    		count++;
        	Object[] data = new Object[column+6];
        	data[0] = (null!=rs.getString("propertyNo1")?rs.getString("propertyNo1"):"")+(rs.getString("serialNo1")!=null?"\n"+rs.getString("serialNo1"):"");
        	data[1] = rs.getString("propertyName");
        	data[2] = rs.getString("specification");
        	data[3] = rs.getString("sourceDate");
        	data[4] = rs.getString("limitYear");
        	data[5] = rs.getString("propertyUnit");
        	data[6] = rs.getString("bookUnit");
        	data[7] = UNTGRQUERYSQL.nextLine(rs.getString("add_amount"));
        	data[8] = rs.getString("add_bookValue");
        	data[9] = UNTGRQUERYSQL.nextLine(rs.getString("reduce_amount"));
        	data[10] = rs.getString("reduce_bookValue");
        	data[11] = rs.getString("reduce_cause");
        	data[12] = rs.getString("scrapValue2");
        	data[13] = rs.getString("approveOrg");
        	data[14] = cloumn_15;
        	data[15] = cloumn_16;
        	data[16] = cloumn_17;
        	data[17] = cloumn_18;
        	data[column] = rs.getString("propertyKindFundTypeName");
    		data[(column)+1] = Common.getORGANANAME(getQ_enterOrg());
    		data[(column)+2] = titleName;
    		data[(column)+3] = "中華民國"+Common.MaskCDate(getQ_enterDateS())+"起至"+Common.MaskCDate(getQ_enterDateE())+"止"+reprotTypeName;
    		data[(column)+4] = rs.getString("propertyKindFundTypeNo");
    		if(q_ownership.equals("4")){
            	data[(column)+5] = "製表                                                             "+data[16]+"                                                              主辦會計人員                                                              機關首長(召集人或其授權代簽人)";
            }else{
            	data[(column)+5] = "製表                              覆核                                       "+data[16]+"                                       主辦事務人員                                       主辦會計人員                                      機關首長";
            }
    		rowData.addElement(data);
       	}
    	if(count==0){
    		Object[] data = new Object[column+6];
        	data[0] = "";
        	data[1] = "　　　本";
        	data[2] = "　　　"+noDataName;
        	data[3] = "無";
        	data[4] = "異";
        	data[5] = "動";
        	data[6] = "";
        	data[7] = "";
        	data[8] = "";
        	data[9] = "";
        	data[10] = "";
        	data[11] = "";
        	data[12] = "";
        	data[13] = "";
        	data[14] = cloumn_15;
        	data[15] = cloumn_16;
        	data[16] = cloumn_17;
        	data[17] = cloumn_18;
        	data[column] = propertyKindFundTypeName+propertyKindName;
    		data[(column)+1] = Common.getORGANANAME(getQ_enterOrg());
    		data[(column)+2] = titleName;
    		data[(column)+3] = "中華民國"+Common.MaskCDate(getQ_enterDateS())+"起至"+Common.MaskCDate(getQ_enterDateE())+"止"+reprotTypeName;
    		data[(column)+4] = "";
    		if(q_ownership.equals("4")){
    			data[(column)+5] = "製表                                                             "+data[16]+"                                                              主辦會計人員                                                              機關首長(召集人或其授權代簽人)";
            }else{
            	data[(column)+5] = "製表                              覆核                                       "+data[16]+"                                       主辦事務人員                                       主辦會計人員                                      機關首長";
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
