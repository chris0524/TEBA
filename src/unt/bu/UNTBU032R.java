

/*
*<br>程式目的：建物持份面積與使用面積不符差異總表報表檔 
*<br>程式代號：untbu032r
*<br>撰寫日期：94/12/14
*<br>程式作者：chris
*<br>--------------------------------------------------------
*<br>修改作者　　修改日期　　　修改目的
*<br>--------------------------------------------------------
*/

package unt.bu;

import java.sql.ResultSet;
import java.util.Vector;

import javax.swing.table.DefaultTableModel;

import util.*;

public class UNTBU032R extends SuperBean{


	String q_balanceDate;
	String q_enterOrg;
	String q_ownership;
	String q_propertyKind;
	String q_verify;
	
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
    	String[] columns = new String[] {"ENTERORGNAME","ENTERDATE","OWNERSHIP","PROPERTYKIND","NAME","COUNT1","AREA1","COUNT2","AREA2","COUNTD","AREAD"};
    	
    	String execSQL="select distinct a.enterOrg,c.organAName as enterOrgName,a.propertyKind as propertyKindN," +
    	" (case a.propertyKind when '01' then '公務用' when '02' then '公共用' when '03' then '事業用' when '04' then '非公用' else '' end) propertyKind " ;
    	execSQL+=" from UNTBU_Building a left join UNTBU_AdjustDetail b on a.enterorg=b.enterorg and  a.ownership=b.ownership and a.propertyNo=b.propertyNo and a.serialNo=b.serialNo left join SYSCA_Organ c on a.enterOrg=c.organID,SYSPK_PropertyCode d ";
    	execSQL+=" where 1=1 and a.propertyNo=d.propertyNo ";
    	execSQL+=" and a.ownership = "+ util.Common.sqlChar(getQ_ownership());
    	execSQL+=" and d.enterOrg in ('000000000A',a.enterOrg) ";
    	execSQL+=" and d.propertyType='1' ";
    	if (!"".equals(getQ_enterOrg())) {
    		execSQL+=" and a.enterOrg = " + Common.sqlChar(getQ_enterOrg()) ;
    	} else {
    		if (!getIsAdminManager().equalsIgnoreCase("Y")) {
    			if ("Y".equals(getIsOrganManager())) {					
    				execSQL += " and a.enterOrg like '" + getOrganID().substring(0,5) + "%' ";
    			} else {
    				execSQL+=" and a.enterOrg = " + Common.sqlChar(getOrganID()) ;
    			}
    		}
    	}
    	
    	if (!"".equals(Common.get(getQ_propertyKind()))&&"00".equals(Common.get(getQ_propertyKind())))
    		execSQL += " and a.propertyKind <'04' ";
    	else if (!"".equals(Common.get(getQ_propertyKind())))
    		execSQL += " and a.propertyKind = "+ util.Common.sqlChar(getQ_propertyKind());
    	if (!"".equals(getQ_verify())) 
    		execSQL+=" and a.verify = " + Common.sqlChar(getQ_verify()) ;
    	if (!"".equals(Common.get(getQ_balanceDate()))){
    		execSQL += " and a.enterDate <= " + util.Common.sqlChar(getQ_balanceDate()+"31");
    	}
    	execSQL+=" order by enterOrgName,propertyKindN ";
    	
    	String Name[]={"鹽埕區","鼓山區","左營區","楠梓區","三民區","新興區","前金區","苓雅區","前鎮區","旗津區","小港區","外縣市","小計","抵繳稅款","合計"};
    	String SignNo[]={"E01","E02","E03","E04","E05","E06","E07","E08","E09","E10","E11"};
    	
    	String DateTrn=getQ_balanceDate();
    	String YM="";
    	if(DateTrn!=null){
    		if(!"0".equals(DateTrn.substring(0,1)))YM=DateTrn.substring(0,3)+"年";
    		else YM=DateTrn.substring(1,3)+"年";
    		if(!"0".equals(DateTrn.substring(3,4)))YM+=DateTrn.substring(3,5)+"月";
    		else YM+=DateTrn.substring(4,5)+"月";
    		
    		
    	}
    	String OwnerShip;
    	String EnterOrg;
    	String EnterOrgName;
    	String BalaceDate=YM+"建物持分面積與使用面積不符差異總表";
    	String PropertyKind;
    	String tansToDouble;
    	String execSQL1;
    	int loop1,Count1,SumCount1,Count2,SumCount2;
    	double Area1,SumArea1,Area2,SumArea2;
    	
    	if("1".equals(Common.get(getQ_ownership())))OwnerShip="市有";
    	else OwnerShip="國有";
    	
        ResultSet rs = db.querySQL(execSQL);
        ResultSet rs2;
        ResultSet rs3;
        Vector rowData = new Vector();
        loop1=0;
        while(rs.next()){
        	SumCount1=0;SumCount2=0;
        	SumArea1=0;SumArea2=0;
        	
        	EnterOrg=rs.getString("enterOrg");
        	EnterOrgName = rs.getString("enterOrgName");
        	PropertyKind = rs.getString("propertyKind");
            //高雄市各區
        	for(loop1=0;loop1<11;loop1++){
            	execSQL1="(select (case (select g.newHoldArea from UNTBU_AdjustDetail g where a.enterOrg=g.enterOrg and a.ownership=g.ownership and a.propertyNo=g.propertyNo and a.serialNo=g.serialNo and rownum=1 and g.adjustDate in (select max(f.adjustDate) from UNTBU_AdjustDetail f where a.enterOrg=f.enterOrg and a.ownership=f.ownership and a.propertyNo=f.propertyNo and a.serialNo=f.serialNo and f.adjustDate<=" +util.Common.sqlChar(getQ_balanceDate()+"31")+
					")) when null then a.originalHoldArea else (select g.newHoldArea from UNTBU_AdjustDetail g where a.enterOrg=g.enterOrg and a.ownership=g.ownership and a.propertyNo=g.propertyNo and a.serialNo=g.serialNo and rownum=1 and g.adjustDate in (select max(f.adjustDate) from UNTBU_AdjustDetail f where a.enterOrg=f.enterOrg and a.ownership=f.ownership and a.propertyNo=f.propertyNo and a.serialNo=f.serialNo and f.adjustDate<=" +util.Common.sqlChar(getQ_balanceDate()+"31")+ ")) end) as area," +
					" (case (select sum(m.useArea) from UNTBU_Manage m where a.enterOrg=m.enterOrg and a.ownership=m.ownership and a.propertyNo=m.propertyNo and a.serialNo=m.serialNo and nvl(m.useDateS,'0000000')<=" +util.Common.sqlChar(getQ_balanceDate()+"31")+" and nvl(m.useDateE,'9999999')>="+util.Common.sqlChar(getQ_balanceDate()+"31")+
            		") when null then 0 else (select sum(m.useArea) from UNTBU_Manage m where a.enterOrg=m.enterOrg and a.ownership=m.ownership and a.propertyNo=m.propertyNo and a.serialNo=m.serialNo and nvl(m.useDateS,'0000000')<=" +util.Common.sqlChar(getQ_balanceDate()+"31")+" and nvl(m.useDateE,'9999999')>="+util.Common.sqlChar(getQ_balanceDate()+"31")+") end) as useArea " +
					" from UNTBU_Building a " ;
	        execSQL1+=" where 1=1 " ;
	        execSQL1+=" and a.enterOrg="+util.Common.sqlChar(EnterOrg);
	        execSQL1+=" and a.ownership = "+ util.Common.sqlChar(getQ_ownership());
            if (!"".equals(getQ_verify())) 
        		execSQL1+=" and a.verify = " + Common.sqlChar(getQ_verify()) ;
            execSQL1+=" and a.propertyKind="+Common.sqlChar(rs.getString("propertyKindN"));
        	
            execSQL1+=" and a.taxCredit='N' ";
        	
        	if (!"".equals(Common.get(getQ_balanceDate()))){
        		execSQL1 = execSQL1 + " and a.enterDate <= " + util.Common.sqlChar(getQ_balanceDate()+"31");
        	}
        	execSQL1+=" and a.dataState='1'";
        	execSQL1+=" and a.signNo like '"+SignNo[loop1]+"%'";
        	execSQL1+=")union(";
        	execSQL1+="select (case (select g.newHoldArea from UNTBU_AdjustDetail g where a.enterOrg=g.enterOrg and a.ownership=g.ownership and a.propertyNo=g.propertyNo and a.serialNo=g.serialNo and rownum=1 and g.adjustDate in (select max(f.adjustDate) from UNTBU_AdjustDetail f where a.enterOrg=f.enterOrg and a.ownership=f.ownership and a.propertyNo=f.propertyNo and a.serialNo=f.serialNo and f.adjustDate<=" +util.Common.sqlChar(getQ_balanceDate()+"31")+
    				")) when null then a.originalHoldArea else (select g.newHoldArea from UNTBU_AdjustDetail g where a.enterOrg=g.enterOrg and a.ownership=g.ownership and a.propertyNo=g.propertyNo and a.serialNo=g.serialNo and rownum=1 and g.adjustDate in (select max(f.adjustDate) from UNTBU_AdjustDetail f where a.enterOrg=f.enterOrg and a.ownership=f.ownership and a.propertyNo=f.propertyNo and a.serialNo=f.serialNo and f.adjustDate<=" +util.Common.sqlChar(getQ_balanceDate()+"31")+")) end) as area," +
    				" nvl((select sum(m.useArea) from UNTBU_Manage m where a.enterOrg=m.enterOrg and a.ownership=m.ownership and a.propertyNo=m.propertyNo and a.serialNo=m.serialNo and nvl(m.useDateS,'0000000')<=" +util.Common.sqlChar(getQ_balanceDate()+"31")+" and nvl(m.useDateE,'9999999')>="+util.Common.sqlChar(getQ_balanceDate()+"31")+
            		"),0) as useArea " +
					" from UNTBU_Building a " ;
	        	execSQL1+=" where 1=1 " ;
	        	execSQL1+=" and a.enterOrg="+util.Common.sqlChar(EnterOrg);
	        	execSQL1+=" and a.ownership = "+ util.Common.sqlChar(getQ_ownership());
        	if (!"".equals(getQ_verify())) 
        		execSQL1+=" and a.verify = " + Common.sqlChar(getQ_verify()) ;
        	execSQL1+=" and a.propertyKind="+Common.sqlChar(rs.getString("propertyKindN"));
        	execSQL1+=" and a.taxCredit='N' ";
        	
        	if (!"".equals(Common.get(getQ_balanceDate()))){
        		execSQL1 = execSQL1 + " and a.enterDate <= " + util.Common.sqlChar(getQ_balanceDate()+"31");
        	}
        	execSQL1+=" and a.dataState='2'";
        	execSQL1+=" and a.reduceDate>"+ util.Common.sqlChar(getQ_balanceDate()+"31");
        	execSQL1+=" and a.signNo like '"+SignNo[loop1]+"%'";
        	execSQL1+=")";
        	rs2 = db.querySQL(execSQL1);
        	Count1=0;Count2=0;Area1=0;Area2=0;
        	while(rs2.next()){
        		if (!"0".equals(rs2.getString("Area"))&&rs2.getString("Area")!=null){
        			Count1++;
        			Area1+=Double.parseDouble(rs2.getString("Area"));
        		} 
        		if (!"0".equals(rs2.getString("useArea"))&&rs2.getString("useArea")!=null){
        			Count2++;
        			Area2+=Double.parseDouble(rs2.getString("useArea"));
        		}
        		
            }//whil rs2
        	Object[] data = new Object[columns.length];
        	data[0] = EnterOrgName;
        	data[1] = BalaceDate;
            data[2] = OwnerShip;
            data[3] = PropertyKind;
        	data[4]=Name[loop1];
        	data[5]=Count1+"";
        	data[6]=Common.areaFormat(Area1+"")+"　";
        	data[7]=Count2+"";
        	data[8]=Common.areaFormat(Area2+"")+"　";
        	data[9]=(Count1-Count2)+"";
        	data[10]=Common.areaFormat((Area1-Area2)+"")+"　";
        	SumCount1+=Count1;SumCount2+=Count2;
        	SumArea1+=Area1;SumArea2+=Area2;
        	rowData.addElement(data);
			}//for
            //外縣市
            execSQL1="(select nvl((select g.newHoldArea from UNTBU_AdjustDetail g where a.enterOrg=g.enterOrg and a.ownership=g.ownership and a.propertyNo=g.propertyNo and a.serialNo=g.serialNo and rownum=1 and g.adjustDate in (select max(f.adjustDate) from UNTBU_AdjustDetail f where a.enterOrg=f.enterOrg and a.ownership=f.ownership and a.propertyNo=f.propertyNo and a.serialNo=f.serialNo and f.adjustDate<=" +util.Common.sqlChar(getQ_balanceDate()+"31")+
				")),a.originalHoldArea) as area," +
				" (case (select sum(m.useArea) from UNTBU_Manage m where a.enterOrg=m.enterOrg and a.ownership=m.ownership and a.propertyNo=m.propertyNo and a.serialNo=m.serialNo and nvl(m.useDateS,'0000000')<=" +util.Common.sqlChar(getQ_balanceDate()+"31")+" and nvl(m.useDateE,'9999999')>="+util.Common.sqlChar(getQ_balanceDate()+"31")+
        		") when null then 0 else (select sum(m.useArea) from UNTBU_Manage m where a.enterOrg=m.enterOrg and a.ownership=m.ownership and a.propertyNo=m.propertyNo and a.serialNo=m.serialNo and nvl(m.useDateS,'0000000')<=" +util.Common.sqlChar(getQ_balanceDate()+"31")+" and nvl(m.useDateE,'9999999')>="+util.Common.sqlChar(getQ_balanceDate()+"31")+")) as useArea " +
				" from UNTBU_Building a " ;
        	execSQL1+=" where 1=1 " ;
        	execSQL1+=" and a.enterOrg="+util.Common.sqlChar(EnterOrg);
        	execSQL1+=" and a.ownership = "+ util.Common.sqlChar(getQ_ownership());
            if (!"".equals(getQ_verify())) 
            	execSQL1+=" and a.verify = " + Common.sqlChar(getQ_verify()) ;
            execSQL1+=" and a.propertyKind="+Common.sqlChar(rs.getString("propertyKindN"));
        	execSQL1+=" and a.taxCredit='N' ";
            
            if (!"".equals(Common.get(getQ_balanceDate()))){
            	execSQL1 = execSQL1 + " and a.enterDate <= " + util.Common.sqlChar(getQ_balanceDate()+"31");
            }
            execSQL1+=" and a.dataState='1'";
            execSQL1+=" and substr(a.signNo,1,1)<>'E'";
            execSQL1+=")union(";
            execSQL1+="select (case (select g.newHoldArea from UNTBU_AdjustDetail g where a.enterOrg=g.enterOrg and a.ownership=g.ownership and a.propertyNo=g.propertyNo and a.serialNo=g.serialNo and rownum=1 and g.adjustDate in (select max(f.adjustDate) from UNTBU_AdjustDetail f where a.enterOrg=f.enterOrg and a.ownership=f.ownership and a.propertyNo=f.propertyNo and a.serialNo=f.serialNo and f.adjustDate<=" +util.Common.sqlChar(getQ_balanceDate()+"31")+
				")) when null then a.originalHoldArea else (select g.newHoldArea from UNTBU_AdjustDetail g where a.enterOrg=g.enterOrg and a.ownership=g.ownership and a.propertyNo=g.propertyNo and a.serialNo=g.serialNo and rownum=1 and g.adjustDate in (select max(f.adjustDate) from UNTBU_AdjustDetail f where a.enterOrg=f.enterOrg and a.ownership=f.ownership and a.propertyNo=f.propertyNo and a.serialNo=f.serialNo and f.adjustDate<=" +util.Common.sqlChar(getQ_balanceDate()+"31")+")) end) as area," +
				" (case (select sum(m.useArea) from UNTBU_Manage m where a.enterOrg=m.enterOrg and a.ownership=m.ownership and a.propertyNo=m.propertyNo and a.serialNo=m.serialNo and nvl(m.useDateS,'0000000')<=" +util.Common.sqlChar(getQ_balanceDate()+"31")+" and nvl(m.useDateE,'9999999')>="+util.Common.sqlChar(getQ_balanceDate()+"31")+
        		") when null then 0 else (select sum(m.useArea) from UNTBU_Manage m where a.enterOrg=m.enterOrg and a.ownership=m.ownership and a.propertyNo=m.propertyNo and a.serialNo=m.serialNo and nvl(m.useDateS,'0000000')<=" +util.Common.sqlChar(getQ_balanceDate()+"31")+" and nvl(m.useDateE,'9999999')>="+util.Common.sqlChar(getQ_balanceDate()+"31")+") end) as useArea " +
				" from UNTBU_Building a " ;
        	execSQL1+=" where 1=1 " ;
        	execSQL1+=" and a.enterOrg="+util.Common.sqlChar(EnterOrg);
        	execSQL1+=" and a.ownership = "+ util.Common.sqlChar(getQ_ownership());
            if (!"".equals(getQ_verify())) 
            	execSQL1+=" and a.verify = " + Common.sqlChar(getQ_verify()) ;
            execSQL1+=" and a.propertyKind="+Common.sqlChar(rs.getString("propertyKindN"));
        	execSQL1+=" and a.taxCredit='N' ";
            
            if (!"".equals(Common.get(getQ_balanceDate()))){
            	execSQL1 = execSQL1 + " and a.enterDate <= " + util.Common.sqlChar(getQ_balanceDate()+"31");
            }
            execSQL1+=" and a.dataState='2'";
            execSQL1+=" and a.reduceDate>"+ util.Common.sqlChar(getQ_balanceDate()+"31");
            execSQL1+=" and substr(a.signNo,1,1)<>'E'";
            execSQL1+=")";
            
            rs3 = db.querySQL(execSQL1);
            
        	Count1=0;Count2=0;Area1=0;Area2=0;
        	while(rs3.next()){
        		if (!"0".equals(rs3.getString("Area"))&&rs3.getString("Area")!=null){
        			Count1++;
        			Area1+=Double.parseDouble(rs3.getString("Area"));
        		} 
        		if (!"0".equals(rs3.getString("useArea"))&&rs3.getString("useArea")!=null){
        			Count2++;
        			Area2+=Double.parseDouble(rs3.getString("useArea"));
        		}
        		
            }//whil rs2
        	Object[] data = new Object[columns.length];
        	data[0] = EnterOrgName;
        	data[1] = BalaceDate;
            data[2] = OwnerShip;
            data[3] = PropertyKind;
            data[4]=Name[11];
        	data[5]=Count1+"";
        	data[6]=Common.areaFormat(Area1+"")+"　";
        	data[7]=Count2+"";
        	data[8]=Common.areaFormat(Area2+"")+"　";
        	data[9]=(Count1-Count2)+"";
        	data[10]=Common.areaFormat((Area1-Area2)+"")+"　";
        	SumCount1+=Count1;SumCount2+=Count2;
        	SumArea1+=Area1;SumArea2+=Area2;
        	rowData.addElement(data);
        	
        	//小計
        	data = new Object[columns.length];
        	data[0] = EnterOrgName;
        	data[1] = BalaceDate;
            data[2] = OwnerShip;
            data[3] = PropertyKind;
        	data[4]=Name[12];
        	data[5]=SumCount1+"";
        	data[6]=Common.areaFormat(SumArea1+"")+"　";
        	data[7]=SumCount2+"";
        	data[8]=Common.areaFormat(SumArea2+"")+"　";
        	data[9]=(SumCount1-SumCount2)+"";
        	data[10]=Common.areaFormat((SumArea1-SumArea2)+"")+"　";
        	rowData.addElement(data);
        	
        	//抵繳稅款
        	execSQL1="(select (case (select g.newHoldArea from UNTBU_AdjustDetail g where a.enterOrg=g.enterOrg and a.ownership=g.ownership and a.propertyNo=g.propertyNo and a.serialNo=g.serialNo and rownum=1 and g.adjustDate in (select max(f.adjustDate) from UNTBU_AdjustDetail f where a.enterOrg=f.enterOrg and a.ownership=f.ownership and a.propertyNo=f.propertyNo and a.serialNo=f.serialNo and f.adjustDate<=" +util.Common.sqlChar(getQ_balanceDate()+"31")+
				")) when null then a.originalHoldArea else (select g.newHoldArea from UNTBU_AdjustDetail g where a.enterOrg=g.enterOrg and a.ownership=g.ownership and a.propertyNo=g.propertyNo and a.serialNo=g.serialNo and rownum=1 and g.adjustDate in (select max(f.adjustDate) from UNTBU_AdjustDetail f where a.enterOrg=f.enterOrg and a.ownership=f.ownership and a.propertyNo=f.propertyNo and a.serialNo=f.serialNo and f.adjustDate<=" +util.Common.sqlChar(getQ_balanceDate()+"31")+")) end) as area," +
				" (case (select sum(m.useArea) from UNTBU_Manage m where a.enterOrg=m.enterOrg and a.ownership=m.ownership and a.propertyNo=m.propertyNo and a.serialNo=m.serialNo and nvl(m.useDateS,'0000000')<=" +util.Common.sqlChar(getQ_balanceDate()+"31")+" and nvl(m.useDateE,'9999999')>="+util.Common.sqlChar(getQ_balanceDate()+"31")+
        		") when null then 0 else (select sum(m.useArea) from UNTBU_Manage m where a.enterOrg=m.enterOrg and a.ownership=m.ownership and a.propertyNo=m.propertyNo and a.serialNo=m.serialNo and nvl(m.useDateS,'0000000')<=" +util.Common.sqlChar(getQ_balanceDate()+"31")+" and nvl(m.useDateE,'9999999')>="+util.Common.sqlChar(getQ_balanceDate()+"31")+") end) as useArea " +
				" from UNTBU_Building a " ;
        	execSQL1+=" where 1=1 " ;
        	execSQL1+=" and a.enterOrg="+util.Common.sqlChar(EnterOrg);
        	execSQL1+=" and a.ownership = "+ util.Common.sqlChar(getQ_ownership());
        	if (!"".equals(getQ_verify())) 
        		execSQL1+=" and a.verify = " + Common.sqlChar(getQ_verify()) ;
        	execSQL1+=" and a.propertyKind="+Common.sqlChar(rs.getString("propertyKindN"));
        	execSQL1+=" and a.taxCredit='Y' ";

        	if (!"".equals(Common.get(getQ_balanceDate()))){
        		execSQL1 = execSQL1 + " and a.enterDate <= " + util.Common.sqlChar(getQ_balanceDate()+"31");
        	}
        	execSQL1+=" and a.dataState='1'";
        	execSQL1+=")union(";
        	execSQL1+="select (case (select g.newHoldArea from UNTBU_AdjustDetail g where a.enterOrg=g.enterOrg and a.ownership=g.ownership and a.propertyNo=g.propertyNo and a.serialNo=g.serialNo and rownum=1 and g.adjustDate in (select max(f.adjustDate) from UNTBU_AdjustDetail f where a.enterOrg=f.enterOrg and a.ownership=f.ownership and a.propertyNo=f.propertyNo and a.serialNo=f.serialNo and f.adjustDate<=" +util.Common.sqlChar(getQ_balanceDate()+"31")+
				")) when null then a.originalHoldArea else (select g.newHoldArea from UNTBU_AdjustDetail g where a.enterOrg=g.enterOrg and a.ownership=g.ownership and a.propertyNo=g.propertyNo and a.serialNo=g.serialNo and rownum=1 and g.adjustDate in (select max(f.adjustDate) from UNTBU_AdjustDetail f where a.enterOrg=f.enterOrg and a.ownership=f.ownership and a.propertyNo=f.propertyNo and a.serialNo=f.serialNo and f.adjustDate<=" +util.Common.sqlChar(getQ_balanceDate()+"31")+"))) as area," +
				" (case (select sum(m.useArea) from UNTBU_Manage m where a.enterOrg=m.enterOrg and a.ownership=m.ownership and a.propertyNo=m.propertyNo and a.serialNo=m.serialNo and nvl(m.useDateS,'0000000')<=" +util.Common.sqlChar(getQ_balanceDate()+"31")+" and nvl(m.useDateE,'9999999')>="+util.Common.sqlChar(getQ_balanceDate()+"31")+
        		") when null then 0 else (select sum(m.useArea) from UNTBU_Manage m where a.enterOrg=m.enterOrg and a.ownership=m.ownership and a.propertyNo=m.propertyNo and a.serialNo=m.serialNo and nvl(m.useDateS,'0000000')<=" +util.Common.sqlChar(getQ_balanceDate()+"31")+" and nvl(m.useDateE,'9999999')>="+util.Common.sqlChar(getQ_balanceDate()+"31")+") end) as useArea " +
				" from UNTBU_Building a " ;
        	execSQL1+=" where 1=1 " ;
        	execSQL1+=" and a.enterOrg="+util.Common.sqlChar(EnterOrg);
        	execSQL1+=" and a.ownership = "+ util.Common.sqlChar(getQ_ownership());
        	if (!"".equals(getQ_verify())) 
        		execSQL1+=" and a.verify = " + Common.sqlChar(getQ_verify()) ;
        	execSQL1+=" and a.propertyKind="+Common.sqlChar(rs.getString("propertyKindN"));
        	execSQL1+=" and a.taxCredit='Y' ";

        	if (!"".equals(Common.get(getQ_balanceDate()))){
        		execSQL1 = execSQL1 + " and a.enterDate <= " + util.Common.sqlChar(getQ_balanceDate()+"31");
        	}
        	execSQL1+=" and a.dataState='2'";
        	execSQL1+=" and a.reduceDate>"+ util.Common.sqlChar(getQ_balanceDate()+"31");
        	execSQL1+=")";
        	rs3 = db.querySQL(execSQL1);
        	
        	Count1=0;Count2=0;Area1=0;Area2=0;
        	while(rs3.next()){
        		if (!"0".equals(rs3.getString("Area"))&&rs3.getString("Area")!=null){
        			Count1++;
        			Area1+=Double.parseDouble(rs3.getString("Area"));
        		} 
        		if (!"0".equals(rs3.getString("useArea"))&&rs3.getString("useArea")!=null){
        			Count2++;
        			Area2+=Double.parseDouble(rs3.getString("useArea"));
        		}
        		
        	}//whil rs2
        	data = new Object[columns.length];
        	data[0] = EnterOrgName;
        	data[1] = BalaceDate;
            data[2] = OwnerShip;
            data[3] = PropertyKind;
        	data[4]=Name[13];
        	data[5]=Count1+"";
        	data[6]=Common.areaFormat(Area1+"")+"　";
        	data[7]=Count2+"";
        	data[8]=Common.areaFormat(Area2+"")+"　";
        	data[9]=(Count1-Count2)+"";
        	data[10]=Common.areaFormat((Area1-Area2)+"")+"　";
        	SumCount1+=Count1;SumCount2+=Count2;
        	SumArea1+=Area1;SumArea2+=Area2;
        	rowData.addElement(data);
        	
        	//        	合計
        	data = new Object[columns.length];
        	data[0] = EnterOrgName;
        	data[1] = BalaceDate;
            data[2] = OwnerShip;
            data[3] = PropertyKind;
        	data[4]=Name[14];
        	data[5]=SumCount1+"";
        	data[6]=Common.areaFormat(SumArea1+"")+"　";
        	data[7]=SumCount2+"";
        	data[8]=Common.areaFormat(SumArea2+"")+"　";
        	data[9]=(SumCount1-SumCount2)+"";
        	data[10]=Common.areaFormat((SumArea1-SumArea2)+"")+"　";
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
