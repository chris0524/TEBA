/*
*<br>程式目的：公用財產異動計畫 
*<br>程式代號：untgr018r
*<br>撰寫日期：0950303
*<br>程式作者：Cherry
*<br>--------------------------------------------------------
*<br>修改作者　　修改日期　　　修改目的
*<br>--------------------------------------------------------
*/

package unt.gr;

import java.sql.ResultSet;
import java.util.Collections;
import java.util.Comparator;
import java.util.Vector;

import javax.swing.table.DefaultTableModel;

import util.*;

public class UNTGR018R extends SuperBean{
		
	String q_enterOrg;
	String q_enterOrgName;
	String q_ownership;
	String verify;
	String planYear;
	
	String isOrganManager;
	String isAdminManager;
	String organID;			
	
	public String getQ_enterOrg(){ return checkGet(q_enterOrg); }
	public void setQ_enterOrg(String s){ q_enterOrg=checkSet(s); }
	public String getQ_enterOrgName(){ return checkGet(q_enterOrgName); }
	public void setQ_enterOrgName(String s){ q_enterOrgName=checkSet(s); }
	public String getQ_ownership(){ return checkGet(q_ownership); }
	public void setQ_ownership(String s){ q_ownership=checkSet(s); }

    public String getVerify() {
        return checkGet(verify);
    }
    public void setVerify(String verify) {
        this.verify = checkSet(verify);
    }
    public String getPlanYear() {
        return checkGet(planYear);
    }
    public void setPlanYear(String planYear) {
        this.planYear = checkSet(planYear);
    }    
	

	public String getOrganID() { return checkGet(organID); }
	public void setOrganID(String s) { organID = checkSet(s); }
	public String getIsOrganManager() { return checkGet(isOrganManager); }
	public void setIsOrganManager(String s) { isOrganManager = checkSet(s); }
	public String getIsAdminManager() { return checkGet(isAdminManager); }
	public void setIsAdminManager(String s) { isAdminManager = checkSet(s); }
    


public DefaultTableModel getResultModel() throws Exception{
    DefaultTableModel model = new javax.swing.table.DefaultTableModel();
    Database db = new Database();
    try{
    	String[] columns = new String[] {"DATATYPE","PLANYEAR","DATE","DATATYPENAME","DATAUNIT","ADDAMOUNT","ADDVALUE","ADDVALUET","REDUCEAMOUNT","REDUCEVALUE","REDUCEVALUET","NOTES","TITLE"};
    	String date = Common.MaskCDate(Datetime.getYYYMMDD());
    	String execSQL=" select a.dataType, " +
    				   " decode( a.dataType,'1', '土  地  購  置', '2', '房  屋  建  築', '3', '其  他  建  築', '4', '機  械  設  備', '5', '交通及運輸設備', '6', '資  訊  設  備', '7', '其  他  設  備', '8' , '投 資 及 其 他') as dataTypeName, " +
    				   " a.dataUnit, a.addAmount, a.addValue, a.reduceAmount, a.reduceValue, a.notes " +
		               " from UNTGR_Plan a" +
					   " where 1=1 " +
					   "";	
	
			if (!"".equals(getQ_enterOrg())) {
				execSQL +=" and a.enterOrg = " + Common.sqlChar(getQ_enterOrg()) ;
			} else {
				if (!getIsAdminManager().equalsIgnoreCase("Y")) {
					if ("Y".equals(getIsOrganManager())) {					
						execSQL += " or organID in (select organID from SYSCA_Organ where organSuperior='"+ Common.sqlChar(getOrganID())+"'))";					
					} else {
						execSQL +=" and a.enterOrg = " + Common.sqlChar(getOrganID()) ;
					}
				}
			}
			if (!"".equals(Common.get(getPlanYear())))
				execSQL +=" and a.planYear = " + util.Common.sqlChar(getPlanYear());
		execSQL += " order by a.dataType ";
		System.out.println("untgr018r-----"+execSQL);
        ResultSet rs = db.querySQL(execSQL);
        Vector rowData = new Vector();
        //int i;
        while(rs.next()){
        	Object[] data = new Object[13];
            data[0] = rs.getString("dataType");
            data[1] = "中華民國"+Integer.parseInt(planYear)+"年度";
            data[2] = "填表日期："+date;
            data[3] = rs.getString("dataTypeName");
            data[4] = rs.getString("dataUnit");
            data[5] = rs.getString("addAmount");
            data[6] = rs.getString("addValue")==null?new Double(0):new Double(rs.getString("addValue"));
            data[7] = Common.valueFormat(rs.getString("addValue"));
            data[8] = rs.getString("reduceAmount");
			data[9] = rs.getString("reduceValue")==null?new Double(0):new Double(rs.getString("reduceValue"));
			data[10] = Common.valueFormat(rs.getString("reduceValue"));
			data[11] = rs.getString("notes");
			data[12] = getTableFile.getOrgName(getQ_enterOrg());
            //for(i=0;i<12;i++)if(data[i]==null)data[i]="";
            rowData.addElement(data);
        }
        //====找出不完整的資料(區分),並塞入空值=====
    	int[] dataType = new int[] {1,2,3,4,5,6,7,8};
        if(rowData.size()<8){
	        for (int i=0;i<rowData.size(); i++){
	        	dataType[new Integer(((Object[])rowData.elementAt(i))[0].toString()).intValue()-1] = 0;
	        }
	        for(int l=1; l<9; l++){
            	String[] dataTypeName = new String[] {"土  地  購  置","房  屋  建  築","其  他  建  築","機  械  設  備","交通及運輸設備","資  訊  設  備","其  他  設  備","投 資 及 其 他"};
            	if(l==dataType[l-1]){
            		Object[] data = new Object[12];
            		data[0] = l+"";
            		data[3] = dataTypeName[l-1];
            		data[6] = new Double(0);
            		data[9] = new Double(0);
            		for(int i=0;i<12;i++)if(data[i]==null)data[i]="";
            		rowData.addElement(data);
            	}
	        }
        }
        //====================================
	    Object[][] data = new Object[0][0];
	    Collections.sort(rowData,com);
        data = (Object[][])rowData.toArray(data);
        model.setDataVector(data, columns);
    }catch(Exception x){
       x.printStackTrace();
    }finally{
        db.closeAll();
    }
    
    return model;

}

Comparator com = new Comparator(){
    public int compare(Object o1, Object o2) {
    	Object[] obj1 = (Object[])o1;
    	Object[] obj2 = (Object[])o2;
    	
        return ((String)obj1[0]).compareTo((String)obj2[0]);
    }
};

}
