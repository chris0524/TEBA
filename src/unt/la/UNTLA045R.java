

/*
*<br>程式目的：土地持份面積與使用面積不符差異明細表報表檔 
*<br>程式代號：untla045r
*<br>撰寫日期：94/12/13
*<br>程式作者：chris
*<br>--------------------------------------------------------
*<br>修改作者　　修改日期　　　修改目的
*<br>--------------------------------------------------------
*/

package unt.la;

import java.sql.ResultSet;
import java.util.Vector;

import javax.swing.table.DefaultTableModel;

import util.*;

public class UNTLA045R extends SuperBean{


	String q_balanceDate;
	String q_enterOrg;
	String q_ownership;
	String q_propertyKind;
	String q_verify;
	String q_signNo1;
	String q_taxCredit;
	String q_grass;
	
	public String getQ_balanceDate(){ return checkGet(q_balanceDate); }
	public void setQ_balanceDate(String s){ q_balanceDate=checkSet(s); }
	public String getQ_enterOrg(){ return checkGet(q_enterOrg); }
	public void setQ_enterOrg(String s){ q_enterOrg=checkSet(s); }
	public String getQ_ownership(){ return checkGet(q_ownership); }
	public void setQ_ownership(String s){ q_ownership=checkSet(s); }
	public String getQ_propertyKind(){ return checkGet(q_propertyKind); }
	public void setQ_propertyKind(String s){ q_propertyKind=checkSet(s); }
	public String getQ_verify(){ return checkGet(q_verify); }
	public void setQ_verify(String s){ q_verify=checkSet(s); }
	public String getQ_signNo1(){ return checkGet(q_signNo1); }
	public void setQ_signNo1(String s){ q_signNo1=checkSet(s); }
	public String getQ_taxCredit(){ return checkGet(q_taxCredit); }
	public void setQ_taxCredit(String s){ q_taxCredit=checkSet(s); }
	public String getQ_grass(){ return checkGet(q_grass); }
	public void setQ_grass(String s){ q_grass=checkSet(s); }

	String isOrganManager;
    String isAdminManager;
    String organID;    
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
    	String[] columns = new String[] {"ENTERORGNAME","ENTERDATE","OWNERSHIP","PROPERTYKIND","SIGNNO","AREA","USEAREA","AREAD","AREAS","USEAREAS","AREADS"};
    	
    	
            
            String execSQL1="(select a.enterOrg,(select c.organAName form SYSCA_Organ c where a.enterOrg=c.organID) as enterOrgName,(case a.ownership when '1' then '市有' when '2' then '國有' else '' end) as ownership," +
            				"(case a.propertyKind when '01' then '公務用' when '02' then '公共用' when '03' then '事業用' when '04' then '非公用' else '' end) propertyKind," +
            				"a.signNo," +
            				"case ((select g.newHoldArea from UNTLA_AdjustDetail g where a.enterOrg=g.enterOrg and a.ownership=g.ownership and a.propertyNo=g.propertyNo and a.serialNo=g.serialNo and rownum=1 and g.adjustDate in (select max(f.adjustDate) from UNTLA_AdjustDetail f where a.enterOrg=f.enterOrg and a.ownership=f.ownership and a.propertyNo=f.propertyNo and a.serialNo=f.serialNo and f.adjustDate<=" +util.Common.sqlChar(getQ_balanceDate()+"31")+
							")) when null then a.originalHoldArea else ((select g.newHoldArea from UNTLA_AdjustDetail g where a.enterOrg=g.enterOrg and a.ownership=g.ownership and a.propertyNo=g.propertyNo and a.serialNo=g.serialNo and rownum=1 and g.adjustDate in (select max(f.adjustDate) from UNTLA_AdjustDetail f where a.enterOrg=f.enterOrg and a.ownership=f.ownership and a.propertyNo=f.propertyNo and a.serialNo=f.serialNo and f.adjustDate<=" +util.Common.sqlChar(getQ_balanceDate()+"31")+")) end) as area," +
							" case ((select sum(m.useArea) from UNTLA_Manage m where a.enterOrg=m.enterOrg and a.ownership=m.ownership and a.propertyNo=m.propertyNo and a.serialNo=m.serialNo and nvl(m.useDateS,'0000000')<=" +util.Common.sqlChar(getQ_balanceDate()+"31")+" and (case m.useDateE when null then '9999999' else m.useDateE end)>="+util.Common.sqlChar(getQ_balanceDate()+"31")+
							") when null then 0 else ((select sum(m.useArea) from UNTLA_Manage m where a.enterOrg=m.enterOrg and a.ownership=m.ownership and a.propertyNo=m.propertyNo and a.serialNo=m.serialNo and nvl(m.useDateS,'0000000')<=" +util.Common.sqlChar(getQ_balanceDate()+"31")+" and (case m.useDateE when null then '9999999' else m.useDateE end)>="+util.Common.sqlChar(getQ_balanceDate()+"31")+")) end) as useArea " +
							" from UNTLA_Land a " +
							" where 1=1 " ;
        	if (!"".equals(getQ_enterOrg())) {
        		execSQL1+=" and a.enterOrg = " + Common.sqlChar(getQ_enterOrg()) ;
        	} else {
        		if (!getIsAdminManager().equalsIgnoreCase("Y")) {
        			if ("Y".equals(getIsOrganManager())) {					
        				execSQL1 += " and a.enterOrg like '" + getOrganID().substring(0,5) + "%' ";
        			} else {
        				execSQL1+=" and a.enterOrg = " + Common.sqlChar(getOrganID()) ;
        			}
        		}
        	}
        	if (!"".equals(Common.get(getQ_ownership())))
        		execSQL1 = execSQL1 + " and a.ownership = " + util.Common.sqlChar(getQ_ownership());
            if (!"".equals(getQ_verify())) 
            	execSQL1+=" and a.verify = " + Common.sqlChar(getQ_verify()) ;
            if (!"".equals(Common.get(getQ_propertyKind()))&&"00".equals(Common.get(getQ_propertyKind())))
        		execSQL1 += " and a.propertyKind <'04' ";
        	else if (!"".equals(Common.get(getQ_propertyKind())))
        		execSQL1 += " and a.propertyKind = "+ util.Common.sqlChar(getQ_propertyKind());
            if (!"".equals(Common.get(getQ_taxCredit())))
        		execSQL1 = execSQL1 + " and a.taxCredit = " + util.Common.sqlChar(getQ_taxCredit());
        	
        	if (!"".equals(Common.get(getQ_grass())))
        		execSQL1 = execSQL1 + " and a.grass = " + util.Common.sqlChar(getQ_grass());
        	
            if (!"".equals(Common.get(getQ_balanceDate()))){
            	execSQL1 = execSQL1 + " and a.enterDate <= " + util.Common.sqlChar(getQ_balanceDate()+"31");
            }
            execSQL1+=" and a.dataState='1'";
            if (!"".equals(Common.get(getQ_signNo1()))){
            	if ("1".equals(Common.get(getQ_signNo1())))
            		execSQL1 = execSQL1 + " and substr(a.signNo,1,1) = 'E'";
            	else if ("2".equals(Common.get(getQ_signNo1())))
            		execSQL1 = execSQL1 + " and substr(a.signNo,1,1) <> 'E'";
            }
            
            execSQL1+=")union(";
            execSQL1+="select a.enterOrg,(select c.organAName from SYSCA_Organ c where a.enterOrg=c.organID) as enterOrgName,(case a.ownership when '1' then '市有' when '2' then '國有' else '' end) as ownership," +
            		"(case a.propertyKind when '01' then '公務用' when '02' then '公共用' when '03' then '事業用' when '04' then '非公用' else '' end) as propertyKind," +
            		"a.signNo," +
            		"case ((select g.newHoldArea from UNTLA_AdjustDetail g where a.enterOrg=g.enterOrg and a.ownership=g.ownership and a.propertyNo=g.propertyNo and a.serialNo=g.serialNo and rownum=1 and g.adjustDate in (select max(f.adjustDate) from UNTLA_AdjustDetail f where a.enterOrg=f.enterOrg and a.ownership=f.ownership and a.propertyNo=f.propertyNo and a.serialNo=f.serialNo and f.adjustDate<=" +util.Common.sqlChar(getQ_balanceDate()+"31")+
					")) when null then a.originalHoldArea else ((select g.newHoldArea from UNTLA_AdjustDetail g where a.enterOrg=g.enterOrg and a.ownership=g.ownership and a.propertyNo=g.propertyNo and a.serialNo=g.serialNo and rownum=1 and g.adjustDate in (select max(f.adjustDate) from UNTLA_AdjustDetail f where a.enterOrg=f.enterOrg and a.ownership=f.ownership and a.propertyNo=f.propertyNo and a.serialNo=f.serialNo and f.adjustDate<=" +util.Common.sqlChar(getQ_balanceDate()+"31")+")) end) as area," +
					" case ((select sum(m.useArea) from UNTLA_Manage m where a.enterOrg=m.enterOrg and a.ownership=m.ownership and a.propertyNo=m.propertyNo and a.serialNo=m.serialNo and (case m.useDateS when null then '0000000' else m.useDateS end)<=" +util.Common.sqlChar(getQ_balanceDate()+"31")+" and (case m.useDateE when null then '9999999' else m.useDateE end)>="+util.Common.sqlChar(getQ_balanceDate()+"31")+
					") when null then 0 else ((select sum(m.useArea) from UNTLA_Manage m where a.enterOrg=m.enterOrg and a.ownership=m.ownership and a.propertyNo=m.propertyNo and a.serialNo=m.serialNo and (case m.useDateS when null then '0000000' end)<=" +util.Common.sqlChar(getQ_balanceDate()+"31")+" and (case m.useDateE when null then '9999999' else m.useDateE end)>="+util.Common.sqlChar(getQ_balanceDate()+"31")+")) end) as useArea " +
					" from UNTLA_Land a " +
					" where 1=1 " ;
    	
    	if (!"".equals(getQ_enterOrg())) {
    		execSQL1+=" and a.enterOrg = " + Common.sqlChar(getQ_enterOrg()) ;
    	} else {
    		if (!getIsAdminManager().equalsIgnoreCase("Y")) {
    			if ("Y".equals(getIsOrganManager())) {					
    				execSQL1 += " and a.enterOrg like '" + getOrganID().substring(0,5) + "%' ";
    			} else {
    				execSQL1+=" and a.enterOrg = " + Common.sqlChar(getOrganID()) ;
    			}
    		}
    	}
    	if (!"".equals(Common.get(getQ_ownership())))
    		execSQL1 = execSQL1 + " and a.ownership = " + util.Common.sqlChar(getQ_ownership());
        if (!"".equals(getQ_verify())) 
        	execSQL1+=" and a.verify = " + Common.sqlChar(getQ_verify()) ;
        if (!"".equals(Common.get(getQ_propertyKind()))&&"00".equals(Common.get(getQ_propertyKind())))
    		execSQL1 += " and a.propertyKind <'04' ";
    	else if (!"".equals(Common.get(getQ_propertyKind())))
    		execSQL1 += " and a.propertyKind = "+ util.Common.sqlChar(getQ_propertyKind());
        if (!"".equals(Common.get(getQ_taxCredit())))
    		execSQL1 = execSQL1 + " and a.taxCredit = " + util.Common.sqlChar(getQ_taxCredit());
    	
    	if (!"".equals(Common.get(getQ_grass())))
    		execSQL1 = execSQL1 + " and a.grass = " + util.Common.sqlChar(getQ_grass());
    	
        if (!"".equals(Common.get(getQ_balanceDate()))){
        	execSQL1 = execSQL1 + " and a.enterDate <= " + util.Common.sqlChar(getQ_balanceDate()+"31");
        }
        if (!"".equals(Common.get(getQ_signNo1()))){
        	if ("1".equals(Common.get(getQ_signNo1())))
        		execSQL1 = execSQL1 + " and substr(a.signNo,1,1) = 'E'";
        	else if ("2".equals(Common.get(getQ_signNo1())))
        		execSQL1 = execSQL1 + " and substr(a.signNo,1,1) <> 'E'";
        }
            execSQL1+=" and a.dataState='2'";
            execSQL1+=" and a.reduceDate>"+ util.Common.sqlChar(getQ_balanceDate()+"31");
            execSQL1+=")";
            String BalaceDate=Common.MaskCDate(getQ_balanceDate())+"土地持分面積與使用面積不符差異明細表";
            String transToDouble;
            double AreaD;
            int checkadd=0,checkrs=0;
            String orgname="",owner="",propter="";
            ResultSet rs3 = db.querySQL(execSQL1);
            Vector rowData = new Vector();
        	while(rs3.next()){
        		Object[] data = new Object[11];
        		checkrs=1;
        		orgname = rs3.getString("enterOrgName");
        		owner = rs3.getString("ownership");
        		propter = rs3.getString("propertyKind");
        		data[0] = rs3.getString("enterOrgName");
        		data[1] = BalaceDate;
        		data[2] = rs3.getString("ownership");
        		data[3] = rs3.getString("propertyKind");
        		data[4] = getSignDescName(rs3.getString("signNo").substring(0,7)) + rs3.getString("signNo").substring(8,12) + '-' + rs3.getString("signNo").substring(12) + "地號";
        		if("-地號".equals(data[4]))data[12]="";
        		AreaD=0;
        		transToDouble=rs3.getString("area");
        		if(transToDouble!=null){
        			data[5]=new Double(Double.parseDouble(transToDouble));
        			AreaD=Double.parseDouble(transToDouble);
        		}
        		transToDouble=rs3.getString("useArea");
        		if(transToDouble!=null){
        			data[6]=new Double(Double.parseDouble(transToDouble));
        			AreaD=AreaD-Double.parseDouble(transToDouble);
        		}
        		data[7]=new Double(AreaD);
        		transToDouble=rs3.getString("area");
        		if(transToDouble!=null){
        			data[8]=Common.areaFormat(transToDouble)+"　";
        		}
        		transToDouble=rs3.getString("useArea");
        		if(transToDouble!=null){
        			data[9]=Common.areaFormat(transToDouble)+"　";
        		}
        		data[10]=Common.areaFormat(AreaD+"")+"　";
        			if(AreaD!=0){
        				checkadd=1;
        				rowData.addElement(data);
        			}
        	}
        	if (checkadd == 0 && checkrs == 1){
           		Object[] data = new Object[11];
        		data[0] = orgname;
        		data[1] = BalaceDate;
        		data[2] = owner;
        		data[3] = propter;
        		data[4] = "本期無異動";
         		data[5]=null;
        		data[6]=null;
        		data[7]=null;
        		data[8]=null;
        		data[9]=null;
        		data[10]=null;
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
}
/**
        		if (rs3.isLast()){
        			if(AreaD!=0){
        				checkadd=1;
        				rowData.addElement(data);
        			}else if(rowData.equals("")){
        				data[4]="本期無差異資料";
        				data[8]=null;
        				data[9]=null;
        				data[10]=null;
        				rowData.addElement(data);
        			}
        		}else{
        			if(AreaD!=0){
        				checkadd=1;
        				rowData.addElement(data);
        			}
        		}

**/
