/*
*<br>程式目的：財產分類量值統計季報表 
*<br>程式代號：untgr005r
*<br>撰寫日期：0950306
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

public class UNTGR005R extends SuperBean{
		
	String q_enterOrg;
	String q_enterOrgName;
	String q_ownership;
	String q_propertyKind;
	String q_fundType;
	String q_valueable;
	String q_reportSeason;
	String q_enterDateS;
	String q_enterDateE;
	String q_verify;	
	String q_reportYear;
	String q_reportMonth;
	String q_reportType;
	String q_isorganmanager;
	String isorganmanagerT;
	
	public String getIsorganmanagerT(){ return checkGet(isorganmanagerT); }
	public void setIsorganmanagerT(String s){ isorganmanagerT=checkSet(s); }	
	public String getQ_isorganmanager(){ return checkGet(q_isorganmanager); }
	public void setQ_isorganmanager(String s){ q_isorganmanager=checkSet(s); }	
	public String getQ_reportType(){ return checkGet(q_reportType); }
	public void setQ_reportType(String s){ q_reportType=checkSet(s); }
	public String getQ_reportYear(){ return checkGet(q_reportYear); }
	public void setQ_reportYear(String s){ q_reportYear=checkSet(s); }
	public String getQ_reportMonth(){ return checkGet(q_reportMonth); }
	public void setQ_reportMonth(String s){ q_reportMonth=checkSet(s); }
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
	public String getQ_reportSeason(){ return checkGet(q_reportSeason); }
	public void setQ_reportSeason(String s){ q_reportSeason=checkSet(s); }
	public String getQ_enterDateS(){ return checkGet(q_enterDateS); }
	public void setQ_enterDateS(String s){ q_enterDateS=checkSet(s); }
	public String getQ_enterDateE(){ return checkGet(q_enterDateE); }
	public void setQ_enterDateE(String s){ q_enterDateE=checkSet(s); }
	public String getQ_verify(){ return checkGet(q_verify); }
	public void setQ_verify(String s){ q_verify=checkSet(s); }

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
	
	String ownership;
	public String getOwnership() { return checkGet(ownership); }
	public void setOwnership(String s) { ownership = checkSet(s); }
	
	String q_periodType;
	public String getQ_periodType(){ return checkGet(q_periodType); }
	public void setQ_periodType(String s){ q_periodType=checkSet(s); }
	
//	查詢條件
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

//報表產製
public DefaultTableModel getResultModel() throws Exception{
    DefaultTableModel model = new javax.swing.table.DefaultTableModel();
    Database db = new Database();
    String querySQL = querySQL();
    String titleName = "";
    String SeasonName = "";
    if(getQ_reportType().equals("1")){
    	titleName = "財 產 分 類 量 值 統 計 月 報 表";
    	SeasonName = "";
    }else if(getQ_reportType().equals("2")){
    	titleName = "財 產 分 類 量 值 統 計 季 報 表";
    	SeasonName = "("+Integer.parseInt(getQ_enterDateE().substring(0,3))+"年度第"+(!getQ_reportSeason().equals("") && getQ_reportSeason()!=null?getQ_reportSeason():"　")+"季)";
    }else if(getQ_reportType().equals("3")){
    	titleName = "財 產 分 類 量 值 統 計 半 年 報 表";
    	SeasonName = "";
    }else if(getQ_reportType().equals("4")){
    	titleName = "財 產 分 類 量 值 統 計 報 表";
    	SeasonName = "";
    } 
    
    //欄
    int column = 12 ;
    //列
    int row = 13 ;
    try{
    	//執行SQL File檔案
//    	System.out.println(getFileName()+"\\untgr005r.sql "+getEditID()+" "+getQ_enterOrg()+" "+getQ_enterDateS()+" "+getQ_enterDateE()+querySQL+" 99 1 UNTGR_untgr005r"+" "+getIsorganmanagerT());
    	Common.callSQLFile(getFileName()+"\\untgr005r.sql "+getEditID()+" "+getQ_enterOrg()+" "+getQ_enterDateS()+" "+getQ_enterDateE()+querySQL+" N " + getOwnership()+ " UNTGR_untgr005r"+" "+getIsorganmanagerT());

    	
    	/* iReport 中column的名稱----COLUMNS_1_1,COLUMNS_2_1,COLUMNS_3_1...,COLUMNS_12_1
    	   						    COLUMNS_1_2,COLUMNS_2_2,COLUMNS_3_2...,COLUMNS_12_2...,COLUMNS_12_22 */
    	String[] columns = new String[(column*row)+8];
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
    	columns[(column*row)+5] = "TITLENAME";
    	columns[(column*row)+6] = "orgTel";
    	columns[(column*row)+7] = "ownership";
    	
    	//抓取存在於UNTGR_untgr005r中的資料
    	String execSQL= Common.callSQLFileRead(getFileName()+"\\untgr005r_1.sql",2,getEditID()+":;:UNTGR_untgr005r");

    	
    	String[] SQLName = new String[] {"oldAmount","oldArea","oldBookValue","addAmount","addArea","addBookValue","reduceAmount","reduceArea","reduceBookValue","nowAmount","nowArea","nowBookValue","propertyKindFundTypeName","propertyKindFundTypeNo"};
  
    	Database db1=new Database();
    	String ownershipName=null;
    	try{
    		String sql="SELECT CODENAME FROM SYSCA_CODE WHERE CODEKINDID='OWA' and codeid='"+this.getOwnership()+"'";
    		ResultSet rs1=db1.querySQL(sql);
    		if(rs1.next()){
    			ownershipName=rs1.getString("CODENAME");
    		}
    		rs1.close();
    	}catch(Exception e){
    		e.printStackTrace();
    	}finally{
    		db1.closeAll();
    	}
    	
        ResultSet rs = db.querySQL(execSQL);
        Vector rowData = new Vector();
    	int dataNo = 0;
    	Object[] data = new Object[(column*row)+8];
    	while(rs.next()){
        	if(dataNo==(column*(row-1))){
        		for(int l=0; l<(column); l++){
    	    		data[dataNo] = rs.getString(SQLName[l]);
    	        	dataNo++;
        		}
        		data[(column*row)] = rs.getString(SQLName[12]);
        		data[(column*row)+1] = Common.getORGANANAME(getQ_enterOrg());
        		data[(column*row)+2] = "中華民國"+Common.MaskCDate(getQ_enterDateS())+"起至"+Common.MaskCDate(getQ_enterDateE())+"止";
        		data[(column*row)+3] = SeasonName;
        		data[(column*row)+4] = rs.getString(SQLName[13]);
        		data[(column*row)+5] = titleName;
        		data[(column*row)+6] = "連絡電話："+getOrgTel();
        		data[(column*row)+7] = ownershipName;
        		rowData.addElement(data);
        		data = new Object[(column*row)+8];
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
