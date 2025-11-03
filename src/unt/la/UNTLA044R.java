

/*
*<br>程式目的：土地持份面積與使用面積不符差異總表報表檔 
*<br>程式代號：untla044r
*<br>撰寫日期：94/12/09
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

public class UNTLA044R extends SuperBean{


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
    	String[] columns = new String[] {"ENTERORGNAME","ENTERDATE","OWNERSHIP","PROPERTYKIND","NAME","COUNT1","AREA1","VALUE1","COUNT2","AREA2","VALUE2","COUNTD","AREAD","VALUED"};
    	
    	String execSQL="select distinct a.enterOrg,(select c.organAName from SYSCA_Organ c where a.enterOrg=c.organID) as enterOrgName,a.propertyKind as propertyKindN," +
    	" (case a.propertyKind when '01' then '公務用' when '02' then '公共用' when '03' then '事業用' when '04' then '非公用' else ''end) propertyKind " ;
    	execSQL+=" from UNTLA_Land a, SYSPK_PropertyCode d ";
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
    	
    	execSQL+=" order by enterOrgName,propertyKind ";
    	
    	String Name[]={"鹽埕區","鼓山區","左營區","楠梓區","三民區","新興區","前金區","苓雅區","前鎮區","旗津區","小港區","外縣市","小計","草衙專案","抵繳稅款","合計"};
    	String SignNo[]={"E01","E02","E03","E04","E05","E06","E07","E08","E09","E10","E11"};
    	
    	String OwnerShip;
    	String EnterOrg;
    	String EnterOrgName;
    	String BalaceDate=Common.MaskCDate(getQ_balanceDate())+"土地持分面積與使用面積不符差異總表";
    	String PropertyKind;
    	String tansToDouble;
    	String DateTrn;
    	String execSQL1;
    	int loop1,Count1,SumCount1,Count2,SumCount2;
    	double Area1,Value1,SumArea1,SumValue1,Area2,Value2,SumArea2,SumValue2;
    	
    	if("1".equals(Common.get(getQ_ownership())))OwnerShip="市有";
    	else OwnerShip="國有";
    	
        ResultSet rs = db.querySQL(execSQL);
        ResultSet rs2;
        ResultSet rs3;
        Vector rowData = new Vector();
        loop1=0;
        while(rs.next()){
        	SumCount1=0;SumCount2=0;
        	SumArea1=0;SumValue1=0;SumArea2=0;SumValue2=0;
        	
        	EnterOrg=rs.getString("enterOrg");
        	EnterOrgName = rs.getString("enterOrgName");
        	PropertyKind = rs.getString("propertyKind");
            //高雄市各區
        	for(loop1=0;loop1<11;loop1++){
            	execSQL1="(select case ((select g.newHoldArea from UNTLA_AdjustDetail g where a.enterOrg=g.enterOrg and a.ownership=g.ownership and a.propertyNo=g.propertyNo and a.serialNo=g.serialNo and rownum=1 and g.adjustDate in (select max(f.adjustDate) from UNTLA_AdjustDetail f where a.enterOrg=f.enterOrg and a.ownership=f.ownership and a.propertyNo=f.propertyNo and a.serialNo=f.serialNo and f.adjustDate<=" +util.Common.sqlChar(getQ_balanceDate()+"31")+
					")) when null then a.originalHoldArea else ((select g.newHoldArea from UNTLA_AdjustDetail g where a.enterOrg=g.enterOrg and a.ownership=g.ownership and a.propertyNo=g.propertyNo and a.serialNo=g.serialNo and rownum=1 and g.adjustDate in (select max(f.adjustDate) from UNTLA_AdjustDetail f where a.enterOrg=f.enterOrg and a.ownership=f.ownership and a.propertyNo=f.propertyNo and a.serialNo=f.serialNo and f.adjustDate<=" +util.Common.sqlChar(getQ_balanceDate()+"31") + ")) end) as area," +
					" case ((select g2.newBookUnit from UNTLA_AdjustDetail g2 where a.enterOrg=g2.enterOrg and a.ownership=g2.ownership and a.propertyNo=g2.propertyNo and a.serialNo=g2.serialNo and rownum=1 and g2.adjustDate in (select max(f2.adjustDate) from UNTLA_AdjustDetail f2 where a.enterOrg=f2.enterOrg and a.ownership=f2.ownership and a.propertyNo=f2.propertyNo and a.serialNo=f2.serialNo and f2.adjustDate<=" +util.Common.sqlChar(getQ_balanceDate()+"31")+
        			")) when null then a.originalUnit else ((select g2.newBookUnit from UNTLA_AdjustDetail g2 where a.enterOrg=g2.enterOrg and a.ownership=g2.ownership and a.propertyNo=g2.propertyNo and a.serialNo=g2.serialNo and rownum=1 and g2.adjustDate in (select max(f2.adjustDate) from UNTLA_AdjustDetail f2 where a.enterOrg=f2.enterOrg and a.ownership=f2.ownership and a.propertyNo=f2.propertyNo and a.serialNo=f2.serialNo and f2.adjustDate<=" +util.Common.sqlChar(getQ_balanceDate()+"31")+ ")) end) as Unit," +
        			" case ((select g1.newBookValue from UNTLA_AdjustDetail g1 where a.enterOrg=g1.enterOrg and a.ownership=g1.ownership and a.propertyNo=g1.propertyNo and a.serialNo=g1.serialNo and rownum=1 and g1.adjustDate in (select max(f1.adjustDate) from UNTLA_AdjustDetail f1 where a.enterOrg=f1.enterOrg and a.ownership=f1.ownership and a.propertyNo=f1.propertyNo and a.serialNo=f1.serialNo and f1.adjustDate<=" +util.Common.sqlChar(getQ_balanceDate()+"31")+
        			")) when null then a.originalBV else ((select g1.newBookValue from UNTLA_AdjustDetail g1 where a.enterOrg=g1.enterOrg and a.ownership=g1.ownership and a.propertyNo=g1.propertyNo and a.serialNo=g1.serialNo and rownum=1 and g1.adjustDate in (select max(f1.adjustDate) from UNTLA_AdjustDetail f1 where a.enterOrg=f1.enterOrg and a.ownership=f1.ownership and a.propertyNo=f1.propertyNo and a.serialNo=f1.serialNo and f1.adjustDate<=" +util.Common.sqlChar(getQ_balanceDate()+"31")+")) end) as value," +
					" case ((select sum(m.useArea) from UNTLA_Manage m where a.enterOrg=m.enterOrg and a.ownership=m.ownership and a.propertyNo=m.propertyNo and a.serialNo=m.serialNo and (case m.useDateS when null then '0000000' else m.useDateS end)<=" +util.Common.sqlChar(getQ_balanceDate()+"31")+" and (case m.useDateE when null then '9999999' end)>="+util.Common.sqlChar(getQ_balanceDate()+"31")+
            		") when null then 0 else ((select sum(m.useArea) from UNTLA_Manage m where a.enterOrg=m.enterOrg and a.ownership=m.ownership and a.propertyNo=m.propertyNo and a.serialNo=m.serialNo and (case m.useDateS when null then '0000000' else m.useDateS end)<=" +util.Common.sqlChar(getQ_balanceDate()+"31")+" and (case m.useDateE when null then '9999999' end)>="+util.Common.sqlChar(getQ_balanceDate()+"31")+")) end) as useArea " +
					" from UNTLA_Land a " ;
	        execSQL1+=" where 1=1 " ;
	        execSQL1+=" and a.enterOrg="+util.Common.sqlChar(EnterOrg);
	        execSQL1+=" and a.ownership = "+ util.Common.sqlChar(getQ_ownership());
            if (!"".equals(getQ_verify())) 
        		execSQL1+=" and a.verify = " + Common.sqlChar(getQ_verify()) ;
            execSQL1+=" and a.propertyKind="+Common.sqlChar(rs.getString("propertyKindN"));
        	
            execSQL1+=" and a.taxCredit='N' ";
        	execSQL1+=" and a.grass='N' ";
        	
        	if (!"".equals(Common.get(getQ_balanceDate()))){
        		execSQL1 = execSQL1 + " and a.enterDate <= " + util.Common.sqlChar(getQ_balanceDate()+"31");
        	}
        	execSQL1+=" and a.dataState='1'";
        	execSQL1+=" and a.signNo like '"+SignNo[loop1]+"%'";
        	execSQL1+=")union(";
        	execSQL1+="select case ((select g.newHoldArea from UNTLA_AdjustDetail g where a.enterOrg=g.enterOrg and a.ownership=g.ownership and a.propertyNo=g.propertyNo and a.serialNo=g.serialNo and rownum=1 and g.adjustDate in (select max(f.adjustDate) from UNTLA_AdjustDetail f where a.enterOrg=f.enterOrg and a.ownership=f.ownership and a.propertyNo=f.propertyNo and a.serialNo=f.serialNo and f.adjustDate<=" +util.Common.sqlChar(getQ_balanceDate()+"31")+
    				")) when null then a.originalHoldArea else ((select g.newHoldArea from UNTLA_AdjustDetail g where a.enterOrg=g.enterOrg and a.ownership=g.ownership and a.propertyNo=g.propertyNo and a.serialNo=g.serialNo and rownum=1 and g.adjustDate in (select max(f.adjustDate) from UNTLA_AdjustDetail f where a.enterOrg=f.enterOrg and a.ownership=f.ownership and a.propertyNo=f.propertyNo and a.serialNo=f.serialNo and f.adjustDate<=" +util.Common.sqlChar(getQ_balanceDate()+"31")+ ")) end) as area," +
    				" case ((select g2.newBookUnit from UNTLA_AdjustDetail g2 where a.enterOrg=g2.enterOrg and a.ownership=g2.ownership and a.propertyNo=g2.propertyNo and a.serialNo=g2.serialNo and rownum=1 and g2.adjustDate in (select max(f2.adjustDate) from UNTLA_AdjustDetail f2 where a.enterOrg=f2.enterOrg and a.ownership=f2.ownership and a.propertyNo=f2.propertyNo and a.serialNo=f2.serialNo and f2.adjustDate<=" +util.Common.sqlChar(getQ_balanceDate()+"31")+
            		")) when null then a.originalUnit else ((select g2.newBookUnit from UNTLA_AdjustDetail g2 where a.enterOrg=g2.enterOrg and a.ownership=g2.ownership and a.propertyNo=g2.propertyNo and a.serialNo=g2.serialNo and rownum=1 and g2.adjustDate in (select max(f2.adjustDate) from UNTLA_AdjustDetail f2 where a.enterOrg=f2.enterOrg and a.ownership=f2.ownership and a.propertyNo=f2.propertyNo and a.serialNo=f2.serialNo and f2.adjustDate<=" +util.Common.sqlChar(getQ_balanceDate()+"31")+")) end) as Unit," +
            		" case ((select g1.newBookValue from UNTLA_AdjustDetail g1 where a.enterOrg=g1.enterOrg and a.ownership=g1.ownership and a.propertyNo=g1.propertyNo and a.serialNo=g1.serialNo and rownum=1 and g1.adjustDate in (select max(f1.adjustDate) from UNTLA_AdjustDetail f1 where a.enterOrg=f1.enterOrg and a.ownership=f1.ownership and a.propertyNo=f1.propertyNo and a.serialNo=f1.serialNo and f1.adjustDate<=" +util.Common.sqlChar(getQ_balanceDate()+"31")+
            		")) when null then a.originalBV else ((select g1.newBookValue from UNTLA_AdjustDetail g1 where a.enterOrg=g1.enterOrg and a.ownership=g1.ownership and a.propertyNo=g1.propertyNo and a.serialNo=g1.serialNo and rownum=1 and g1.adjustDate in (select max(f1.adjustDate) from UNTLA_AdjustDetail f1 where a.enterOrg=f1.enterOrg and a.ownership=f1.ownership and a.propertyNo=f1.propertyNo and a.serialNo=f1.serialNo and f1.adjustDate<=" +util.Common.sqlChar(getQ_balanceDate()+"31")+")) end) as value," +
            		" case ((select sum(m.useArea) from UNTLA_Manage m where a.enterOrg=m.enterOrg and a.ownership=m.ownership and a.propertyNo=m.propertyNo and a.serialNo=m.serialNo and (case m.useDateS when null then '0000000' else m.useDateS end)<=" +util.Common.sqlChar(getQ_balanceDate()+"31")+" and nvl(m.useDateE,'9999999')>="+util.Common.sqlChar(getQ_balanceDate()+"31")+
            		") when null then 0 else ((select sum(m.useArea) from UNTLA_Manage m where a.enterOrg=m.enterOrg and a.ownership=m.ownership and a.propertyNo=m.propertyNo and a.serialNo=m.serialNo and (case m.useDateS when null then '0000000' else m.useDateS end)<=" +util.Common.sqlChar(getQ_balanceDate()+"31")+" and nvl(m.useDateE,'9999999')>="+util.Common.sqlChar(getQ_balanceDate()+"31")+")) end) as useArea " +
					" from UNTLA_Land a " ;
	        	execSQL1+=" where 1=1 " ;
	        	execSQL1+=" and a.enterOrg="+util.Common.sqlChar(EnterOrg);
	        	execSQL1+=" and a.ownership = "+ util.Common.sqlChar(getQ_ownership());
        	if (!"".equals(getQ_verify())) 
        		execSQL1+=" and a.verify = " + Common.sqlChar(getQ_verify()) ;
        	execSQL1+=" and a.propertyKind="+Common.sqlChar(rs.getString("propertyKindN"));
        	execSQL1+=" and a.taxCredit='N' ";
        	execSQL1+=" and a.grass='N' ";
        	
        	if (!"".equals(Common.get(getQ_balanceDate()))){
        		execSQL1 = execSQL1 + " and a.enterDate <= " + util.Common.sqlChar(getQ_balanceDate()+"31");
        	}
        	execSQL1+=" and a.dataState='2'";
        	execSQL1+=" and a.reduceDate>"+ util.Common.sqlChar(getQ_balanceDate()+"31");
        	execSQL1+=" and a.signNo like '"+SignNo[loop1]+"%'";
        	execSQL1+=")";
        	rs2 = db.querySQL(execSQL1);
        	Count1=0;Count2=0;Area1=0;Value1=0;Area2=0;Value2=0;
        	while(rs2.next()){
        		if (!"0".equals(rs2.getString("Area"))&&rs2.getString("Area")!=null){
        			Count1++;
        			Area1+=Double.parseDouble(rs2.getString("Area"));
        		} 
        		if(rs2.getString("Value")!=null)
        			Value1+=Double.parseDouble(rs2.getString("Value"));
        		if (!"0".equals(rs2.getString("useArea"))&&rs2.getString("useArea")!=null){
        			Count2++;
        			Area2+=Double.parseDouble(rs2.getString("useArea"));
        		}
        		if(rs2.getString("useArea")!=null&&rs2.getString("Unit")!=null)
        			Value2+=Double.parseDouble(rs2.getString("useArea"))*Double.parseDouble(rs2.getString("Unit"));
        		
            }//whil rs2
        	Object[] data = new Object[15];
        	data[0] = EnterOrgName;
        	data[1] = BalaceDate;
            data[2] = OwnerShip;
            data[3] = PropertyKind;
        	data[4]=Name[loop1];
        	data[5]=Count1+"";
        	data[6]=Common.areaFormat(Area1+"")+"　";
        	data[7]=Common.valueFormat(Value1+"")+"　";
        	data[8]=Count2+"";
        	data[9]=Common.areaFormat(Area2+"")+"　";
        	data[10]=Common.valueFormat(Value2+"")+"　";
        	data[11]=(Count1-Count2)+"";
        	data[12]=Common.areaFormat((Area1-Area2)+"")+"　";
        	data[13]=Common.valueFormat((Value1-Value2)+"")+"　";
        	SumCount1+=Count1;SumCount2+=Count2;
        	SumArea1+=Area1;SumValue1+=Value1;SumArea2+=Area2;SumValue2+=Value2;
        	rowData.addElement(data);
			}//for
            //外縣市
            execSQL1="(select case ((select g.newHoldArea from UNTLA_AdjustDetail g where a.enterOrg=g.enterOrg and a.ownership=g.ownership and a.propertyNo=g.propertyNo and a.serialNo=g.serialNo and rownum=1 and g.adjustDate in (select max(f.adjustDate) from UNTLA_AdjustDetail f where a.enterOrg=f.enterOrg and a.ownership=f.ownership and a.propertyNo=f.propertyNo and a.serialNo=f.serialNo and f.adjustDate<=" +util.Common.sqlChar(getQ_balanceDate()+"31")+
				")) when null then a.originalHoldArea else ((select g.newHoldArea from UNTLA_AdjustDetail g where a.enterOrg=g.enterOrg and a.ownership=g.ownership and a.propertyNo=g.propertyNo and a.serialNo=g.serialNo and rownum=1 and g.adjustDate in (select max(f.adjustDate) from UNTLA_AdjustDetail f where a.enterOrg=f.enterOrg and a.ownership=f.ownership and a.propertyNo=f.propertyNo and a.serialNo=f.serialNo and f.adjustDate<=" +util.Common.sqlChar(getQ_balanceDate()+"31")+")) end) as area," +
				" case ((select g2.newBookUnit from UNTLA_AdjustDetail g2 where a.enterOrg=g2.enterOrg and a.ownership=g2.ownership and a.propertyNo=g2.propertyNo and a.serialNo=g2.serialNo and rownum=1 and g2.adjustDate in (select max(f2.adjustDate) from UNTLA_AdjustDetail f2 where a.enterOrg=f2.enterOrg and a.ownership=f2.ownership and a.propertyNo=f2.propertyNo and a.serialNo=f2.serialNo and f2.adjustDate<=" +util.Common.sqlChar(getQ_balanceDate()+"31")+
				")) when null then a.originalUnit else ((select g2.newBookUnit from UNTLA_AdjustDetail g2 where a.enterOrg=g2.enterOrg and a.ownership=g2.ownership and a.propertyNo=g2.propertyNo and a.serialNo=g2.serialNo and rownum=1 and g2.adjustDate in (select max(f2.adjustDate) from UNTLA_AdjustDetail f2 where a.enterOrg=f2.enterOrg and a.ownership=f2.ownership and a.propertyNo=f2.propertyNo and a.serialNo=f2.serialNo and f2.adjustDate<=" +util.Common.sqlChar(getQ_balanceDate()+"31")+")) end) as Unit," +
				" case ((select g1.newBookValue from UNTLA_AdjustDetail g1 where a.enterOrg=g1.enterOrg and a.ownership=g1.ownership and a.propertyNo=g1.propertyNo and a.serialNo=g1.serialNo and rownum=1 and g1.adjustDate in (select max(f1.adjustDate) from UNTLA_AdjustDetail f1 where a.enterOrg=f1.enterOrg and a.ownership=f1.ownership and a.propertyNo=f1.propertyNo and a.serialNo=f1.serialNo and f1.adjustDate<=" +util.Common.sqlChar(getQ_balanceDate()+"31")+
				")) when null then a.originalBV else ((select g1.newBookValue from UNTLA_AdjustDetail g1 where a.enterOrg=g1.enterOrg and a.ownership=g1.ownership and a.propertyNo=g1.propertyNo and a.serialNo=g1.serialNo and rownum=1 and g1.adjustDate in (select max(f1.adjustDate) from UNTLA_AdjustDetail f1 where a.enterOrg=f1.enterOrg and a.ownership=f1.ownership and a.propertyNo=f1.propertyNo and a.serialNo=f1.serialNo and f1.adjustDate<=" +util.Common.sqlChar(getQ_balanceDate()+"31")+")) end) as value," +
				" csae ((select sum(m.useArea) from UNTLA_Manage m where a.enterOrg=m.enterOrg and a.ownership=m.ownership and a.propertyNo=m.propertyNo and a.serialNo=m.serialNo and (case m.useDateS when null then '0000000' else m.useDateS end)<=" +util.Common.sqlChar(getQ_balanceDate()+"31")+" and nvl(m.useDateE,'9999999')>="+util.Common.sqlChar(getQ_balanceDate()+"31")+
        		") when null then 0 else ((select sum(m.useArea) from UNTLA_Manage m where a.enterOrg=m.enterOrg and a.ownership=m.ownership and a.propertyNo=m.propertyNo and a.serialNo=m.serialNo and (case m.useDateS when null then '0000000' else m.useDateS end)<=" +util.Common.sqlChar(getQ_balanceDate()+"31")+" and nvl(m.useDateE,'9999999')>="+util.Common.sqlChar(getQ_balanceDate()+"31")+")) end) as useArea " +
				" from UNTLA_Land a " ;
        	execSQL1+=" where 1=1 " ;
        	execSQL1+=" and a.enterOrg="+util.Common.sqlChar(EnterOrg);
        	execSQL1+=" and a.ownership = "+ util.Common.sqlChar(getQ_ownership());
            if (!"".equals(getQ_verify())) 
            	execSQL1+=" and a.verify = " + Common.sqlChar(getQ_verify()) ;
            execSQL1+=" and a.propertyKind="+Common.sqlChar(rs.getString("propertyKindN"));
        	execSQL1+=" and a.taxCredit='N' ";
            execSQL1+=" and a.grass='N' ";
	
            if (!"".equals(Common.get(getQ_balanceDate()))){
            	execSQL1 = execSQL1 + " and a.enterDate <= " + util.Common.sqlChar(getQ_balanceDate()+"31");
            }
            execSQL1+=" and a.dataState='1'";
            execSQL1+=" and substr(a.signNo,1,1)<>'E'";
            execSQL1+=")union(";
            execSQL1+="select case ((select g.newHoldArea from UNTLA_AdjustDetail g where a.enterOrg=g.enterOrg and a.ownership=g.ownership and a.propertyNo=g.propertyNo and a.serialNo=g.serialNo and rownum=1 and g.adjustDate in (select max(f.adjustDate) from UNTLA_AdjustDetail f where a.enterOrg=f.enterOrg and a.ownership=f.ownership and a.propertyNo=f.propertyNo and a.serialNo=f.serialNo and f.adjustDate<=" +util.Common.sqlChar(getQ_balanceDate()+"31")+
				")) when null then a.originalHoldArea else ((select g.newHoldArea from UNTLA_AdjustDetail g where a.enterOrg=g.enterOrg and a.ownership=g.ownership and a.propertyNo=g.propertyNo and a.serialNo=g.serialNo and rownum=1 and g.adjustDate in (select max(f.adjustDate) from UNTLA_AdjustDetail f where a.enterOrg=f.enterOrg and a.ownership=f.ownership and a.propertyNo=f.propertyNo and a.serialNo=f.serialNo and f.adjustDate<=" +util.Common.sqlChar(getQ_balanceDate()+"31")+")) end) as area," +
				" case ((select g2.newBookUnit from UNTLA_AdjustDetail g2 where a.enterOrg=g2.enterOrg and a.ownership=g2.ownership and a.propertyNo=g2.propertyNo and a.serialNo=g2.serialNo and rownum=1 and g2.adjustDate in (select max(f2.adjustDate) from UNTLA_AdjustDetail f2 where a.enterOrg=f2.enterOrg and a.ownership=f2.ownership and a.propertyNo=f2.propertyNo and a.serialNo=f2.serialNo and f2.adjustDate<=" +util.Common.sqlChar(getQ_balanceDate()+"31")+
    			")) when null then a.originalUnit else ((select g2.newBookUnit from UNTLA_AdjustDetail g2 where a.enterOrg=g2.enterOrg and a.ownership=g2.ownership and a.propertyNo=g2.propertyNo and a.serialNo=g2.serialNo and rownum=1 and g2.adjustDate in (select max(f2.adjustDate) from UNTLA_AdjustDetail f2 where a.enterOrg=f2.enterOrg and a.ownership=f2.ownership and a.propertyNo=f2.propertyNo and a.serialNo=f2.serialNo and f2.adjustDate<=" +util.Common.sqlChar(getQ_balanceDate()+"31")+")) end) as Unit," +
    			" csae ((select g1.newBookValue from UNTLA_AdjustDetail g1 where a.enterOrg=g1.enterOrg and a.ownership=g1.ownership and a.propertyNo=g1.propertyNo and a.serialNo=g1.serialNo and rownum=1 and g1.adjustDate in (select max(f1.adjustDate) from UNTLA_AdjustDetail f1 where a.enterOrg=f1.enterOrg and a.ownership=f1.ownership and a.propertyNo=f1.propertyNo and a.serialNo=f1.serialNo and f1.adjustDate<=" +util.Common.sqlChar(getQ_balanceDate()+"31")+
    			")) when null then a.originalBV else ((select g1.newBookValue from UNTLA_AdjustDetail g1 where a.enterOrg=g1.enterOrg and a.ownership=g1.ownership and a.propertyNo=g1.propertyNo and a.serialNo=g1.serialNo and rownum=1 and g1.adjustDate in (select max(f1.adjustDate) from UNTLA_AdjustDetail f1 where a.enterOrg=f1.enterOrg and a.ownership=f1.ownership and a.propertyNo=f1.propertyNo and a.serialNo=f1.serialNo and f1.adjustDate<=" +util.Common.sqlChar(getQ_balanceDate()+"31")+")) end) as value," +
				" case ((select sum(m.useArea) from UNTLA_Manage m where a.enterOrg=m.enterOrg and a.ownership=m.ownership and a.propertyNo=m.propertyNo and a.serialNo=m.serialNo and (case m.useDateS when null then '0000000' else m.useDateS end)<=" +util.Common.sqlChar(getQ_balanceDate()+"31")+" and nvl(m.useDateE,'9999999')>="+util.Common.sqlChar(getQ_balanceDate()+"31")+
        		") when null then 0 else ((select sum(m.useArea) from UNTLA_Manage m where a.enterOrg=m.enterOrg and a.ownership=m.ownership and a.propertyNo=m.propertyNo and a.serialNo=m.serialNo and (case m.useDateS when null then '0000000' else m.useDateS end)<=" +util.Common.sqlChar(getQ_balanceDate()+"31")+" and nvl(m.useDateE,'9999999')>="+util.Common.sqlChar(getQ_balanceDate()+"31")+")) end) as useArea " +
				" from UNTLA_Land a " ;
        	execSQL1+=" where 1=1 " ;
        	execSQL1+=" and a.enterOrg="+util.Common.sqlChar(EnterOrg);
        	execSQL1+=" and a.ownership = "+ util.Common.sqlChar(getQ_ownership());
            if (!"".equals(getQ_verify())) 
            	execSQL1+=" and a.verify = " + Common.sqlChar(getQ_verify()) ;
            execSQL1+=" and a.propertyKind="+Common.sqlChar(rs.getString("propertyKindN"));
        	execSQL1+=" and a.taxCredit='N' ";
            execSQL1+=" and a.grass='N' ";
	
            if (!"".equals(Common.get(getQ_balanceDate()))){
            	execSQL1 = execSQL1 + " and a.enterDate <= " + util.Common.sqlChar(getQ_balanceDate()+"31");
            }
            execSQL1+=" and a.dataState='2'";
            execSQL1+=" and a.reduceDate>"+ util.Common.sqlChar(getQ_balanceDate()+"31");
            execSQL1+=" and substr(a.signNo,1,1)<>'E'";
            execSQL1+=")";
            
            rs3 = db.querySQL(execSQL1);
            
        	Count1=0;Count2=0;Area1=0;Value1=0;Area2=0;Value2=0;
        	while(rs3.next()){
        		if (!"0".equals(rs3.getString("Area"))&&rs3.getString("Area")!=null){
        			Count1++;
        			Area1+=Double.parseDouble(rs3.getString("Area"));
        		} 
        		if(rs3.getString("Value")!=null)
        			Value1+=Double.parseDouble(rs3.getString("Value"));
        		if (!"0".equals(rs3.getString("useArea"))&&rs3.getString("useArea")!=null){
        			Count2++;
        			Area2+=Double.parseDouble(rs3.getString("useArea"));
        		}
        		if(rs3.getString("useArea")!=null&&rs3.getString("Unit")!=null)
        			Value2+=Double.parseDouble(rs3.getString("useArea"))*Double.parseDouble(rs3.getString("Unit"));
        		
            }//whil rs2
        	Object[] data = new Object[15];
        	data[0] = EnterOrgName;
        	data[1] = BalaceDate;
            data[2] = OwnerShip;
            data[3] = PropertyKind;
            data[4]=Name[11];
        	data[5]=Count1+"";
        	data[6]=Common.areaFormat(Area1+"")+"　";
        	data[7]=Common.valueFormat(Value1+"")+"　";
        	data[8]=Count2+"";
        	data[9]=Common.areaFormat(Area2+"")+"　";
        	data[10]=Common.valueFormat(Value2+"")+"　";
        	data[11]=(Count1-Count2)+"";
        	data[12]=Common.areaFormat((Area1-Area2)+"")+"　";
        	data[13]=Common.valueFormat((Value1-Value2)+"")+"　";
        	SumCount1+=Count1;SumCount2+=Count2;
        	SumArea1+=Area1;SumValue1+=Value1;SumArea2+=Area2;SumValue2+=Value2;
        	rowData.addElement(data);
        	
        	//小計
        	data = new Object[15];
        	data[0] = EnterOrgName;
        	data[1] = BalaceDate;
            data[2] = OwnerShip;
            data[3] = PropertyKind;
        	data[4]=Name[12];
        	data[5]=SumCount1+"";
        	data[6]=Common.areaFormat(SumArea1+"")+"　";
        	data[7]=Common.valueFormat(SumValue1+"")+"　";
        	data[8]=SumCount2+"";
        	data[9]=Common.areaFormat(SumArea2+"")+"　";
        	data[10]=Common.valueFormat(SumValue2+"")+"　";
        	data[11]=(SumCount1-SumCount2)+"";
        	data[12]=Common.areaFormat((SumArea1-SumArea2)+"")+"　";
        	data[13]=Common.valueFormat((SumValue1-SumValue2)+"")+"　";
        	rowData.addElement(data);
        	
        	//草衙專案
        	execSQL1="(select case ((select g.newHoldArea from UNTLA_AdjustDetail g where a.enterOrg=g.enterOrg and a.ownership=g.ownership and a.propertyNo=g.propertyNo and a.serialNo=g.serialNo and rownum=1 and g.adjustDate in (select max(f.adjustDate) from UNTLA_AdjustDetail f where a.enterOrg=f.enterOrg and a.ownership=f.ownership and a.propertyNo=f.propertyNo and a.serialNo=f.serialNo and f.adjustDate<=" +util.Common.sqlChar(getQ_balanceDate()+"31")+
				")) when null then a.originalHoldArea else ((select g.newHoldArea from UNTLA_AdjustDetail g where a.enterOrg=g.enterOrg and a.ownership=g.ownership and a.propertyNo=g.propertyNo and a.serialNo=g.serialNo and rownum=1 and g.adjustDate in (select max(f.adjustDate) from UNTLA_AdjustDetail f where a.enterOrg=f.enterOrg and a.ownership=f.ownership and a.propertyNo=f.propertyNo and a.serialNo=f.serialNo and f.adjustDate<=" +util.Common.sqlChar(getQ_balanceDate()+"31")+")) end) as area," +
				" case ((select g2.newBookUnit from UNTLA_AdjustDetail g2 where a.enterOrg=g2.enterOrg and a.ownership=g2.ownership and a.propertyNo=g2.propertyNo and a.serialNo=g2.serialNo and rownum=1 and g2.adjustDate in (select max(f2.adjustDate) from UNTLA_AdjustDetail f2 where a.enterOrg=f2.enterOrg and a.ownership=f2.ownership and a.propertyNo=f2.propertyNo and a.serialNo=f2.serialNo and f2.adjustDate<=" +util.Common.sqlChar(getQ_balanceDate()+"31")+
				")) when null then a.originalUnit else ((select g2.newBookUnit from UNTLA_AdjustDetail g2 where a.enterOrg=g2.enterOrg and a.ownership=g2.ownership and a.propertyNo=g2.propertyNo and a.serialNo=g2.serialNo and rownum=1 and g2.adjustDate in (select max(f2.adjustDate) from UNTLA_AdjustDetail f2 where a.enterOrg=f2.enterOrg and a.ownership=f2.ownership and a.propertyNo=f2.propertyNo and a.serialNo=f2.serialNo and f2.adjustDate<=" +util.Common.sqlChar(getQ_balanceDate()+"31")+")) end) as Unit," +
				" case ((select g1.newBookValue from UNTLA_AdjustDetail g1 where a.enterOrg=g1.enterOrg and a.ownership=g1.ownership and a.propertyNo=g1.propertyNo and a.serialNo=g1.serialNo and rownum=1 and g1.adjustDate in (select max(f1.adjustDate) from UNTLA_AdjustDetail f1 where a.enterOrg=f1.enterOrg and a.ownership=f1.ownership and a.propertyNo=f1.propertyNo and a.serialNo=f1.serialNo and f1.adjustDate<=" +util.Common.sqlChar(getQ_balanceDate()+"31")+
				")) when null then a.originalBV else ((select g1.newBookValue from UNTLA_AdjustDetail g1 where a.enterOrg=g1.enterOrg and a.ownership=g1.ownership and a.propertyNo=g1.propertyNo and a.serialNo=g1.serialNo and rownum=1 and g1.adjustDate in (select max(f1.adjustDate) from UNTLA_AdjustDetail f1 where a.enterOrg=f1.enterOrg and a.ownership=f1.ownership and a.propertyNo=f1.propertyNo and a.serialNo=f1.serialNo and f1.adjustDate<=" +util.Common.sqlChar(getQ_balanceDate()+"31")+")) end) as value," +
				" case ((select sum(m.useArea) from UNTLA_Manage m where a.enterOrg=m.enterOrg and a.ownership=m.ownership and a.propertyNo=m.propertyNo and a.serialNo=m.serialNo and (case m.useDateS when null then '0000000' else m.useDateS end)<=" +util.Common.sqlChar(getQ_balanceDate()+"31")+" and nvl(m.useDateE,'9999999')>="+util.Common.sqlChar(getQ_balanceDate()+"31")+
        		") when null then 0 else ((select sum(m.useArea) from UNTLA_Manage m where a.enterOrg=m.enterOrg and a.ownership=m.ownership and a.propertyNo=m.propertyNo and a.serialNo=m.serialNo and (case m.useDateS when null then '0000000' else m.useDateS end)<=" +util.Common.sqlChar(getQ_balanceDate()+"31")+" and nvl(m.useDateE,'9999999')>="+util.Common.sqlChar(getQ_balanceDate()+"31")+")) end) as useArea " +
				" from UNTLA_Land a " ;
        	execSQL1+=" where 1=1 " ;
        	execSQL1+=" and a.enterOrg="+util.Common.sqlChar(EnterOrg);
        	execSQL1+=" and a.ownership = "+ util.Common.sqlChar(getQ_ownership());
        	if (!"".equals(getQ_verify())) 
        		execSQL1+=" and a.verify = " + Common.sqlChar(getQ_verify()) ;
        	execSQL1+=" and a.propertyKind="+Common.sqlChar(rs.getString("propertyKindN"));
        	execSQL1+=" and a.grass='Y' ";

        	if (!"".equals(Common.get(getQ_balanceDate()))){
        		execSQL1 = execSQL1 + " and a.enterDate <= " + util.Common.sqlChar(getQ_balanceDate()+"31");
        	}
        	execSQL1+=" and a.dataState='1'";
        	execSQL1+=")union(";
        	execSQL1+="select case ((select g.newHoldArea from UNTLA_AdjustDetail g where a.enterOrg=g.enterOrg and a.ownership=g.ownership and a.propertyNo=g.propertyNo and a.serialNo=g.serialNo and rownum=1 and g.adjustDate in (select max(f.adjustDate) from UNTLA_AdjustDetail f where a.enterOrg=f.enterOrg and a.ownership=f.ownership and a.propertyNo=f.propertyNo and a.serialNo=f.serialNo and f.adjustDate<=" +util.Common.sqlChar(getQ_balanceDate()+"31")+
				")) when null then a.originalHoldArea else ((select g.newHoldArea from UNTLA_AdjustDetail g where a.enterOrg=g.enterOrg and a.ownership=g.ownership and a.propertyNo=g.propertyNo and a.serialNo=g.serialNo and rownum=1 and g.adjustDate in (select max(f.adjustDate) from UNTLA_AdjustDetail f where a.enterOrg=f.enterOrg and a.ownership=f.ownership and a.propertyNo=f.propertyNo and a.serialNo=f.serialNo and f.adjustDate<=" +util.Common.sqlChar(getQ_balanceDate()+"31")+")) end) as area," +
				" case ((select g2.newBookUnit from UNTLA_AdjustDetail g2 where a.enterOrg=g2.enterOrg and a.ownership=g2.ownership and a.propertyNo=g2.propertyNo and a.serialNo=g2.serialNo and rownum=1 and g2.adjustDate in (select max(f2.adjustDate) from UNTLA_AdjustDetail f2 where a.enterOrg=f2.enterOrg and a.ownership=f2.ownership and a.propertyNo=f2.propertyNo and a.serialNo=f2.serialNo and f2.adjustDate<=" +util.Common.sqlChar(getQ_balanceDate()+"31")+
				")) when null then a.originalUnit else ((select g2.newBookUnit from UNTLA_AdjustDetail g2 where a.enterOrg=g2.enterOrg and a.ownership=g2.ownership and a.propertyNo=g2.propertyNo and a.serialNo=g2.serialNo and rownum=1 and g2.adjustDate in (select max(f2.adjustDate) from UNTLA_AdjustDetail f2 where a.enterOrg=f2.enterOrg and a.ownership=f2.ownership and a.propertyNo=f2.propertyNo and a.serialNo=f2.serialNo and f2.adjustDate<=" +util.Common.sqlChar(getQ_balanceDate()+"31")+")) end) as Unit," +
				" csae ((select g1.newBookValue from UNTLA_AdjustDetail g1 where a.enterOrg=g1.enterOrg and a.ownership=g1.ownership and a.propertyNo=g1.propertyNo and a.serialNo=g1.serialNo and rownum=1 and g1.adjustDate in (select max(f1.adjustDate) from UNTLA_AdjustDetail f1 where a.enterOrg=f1.enterOrg and a.ownership=f1.ownership and a.propertyNo=f1.propertyNo and a.serialNo=f1.serialNo and f1.adjustDate<=" +util.Common.sqlChar(getQ_balanceDate()+"31")+
				")) when null then a.originalBV else ((select g1.newBookValue from UNTLA_AdjustDetail g1 where a.enterOrg=g1.enterOrg and a.ownership=g1.ownership and a.propertyNo=g1.propertyNo and a.serialNo=g1.serialNo and rownum=1 and g1.adjustDate in (select max(f1.adjustDate) from UNTLA_AdjustDetail f1 where a.enterOrg=f1.enterOrg and a.ownership=f1.ownership and a.propertyNo=f1.propertyNo and a.serialNo=f1.serialNo and f1.adjustDate<=" +util.Common.sqlChar(getQ_balanceDate()+"31")+")) end) as value," +
				" case ((select sum(m.useArea) from UNTLA_Manage m where a.enterOrg=m.enterOrg and a.ownership=m.ownership and a.propertyNo=m.propertyNo and a.serialNo=m.serialNo and (case m.useDateS when null then '0000000' else m.useDateS end)<=" +util.Common.sqlChar(getQ_balanceDate()+"31")+" and nvl(m.useDateE,'9999999')>="+util.Common.sqlChar(getQ_balanceDate()+"31")+
        		") when null then 0 else ((select sum(m.useArea) from UNTLA_Manage m where a.enterOrg=m.enterOrg and a.ownership=m.ownership and a.propertyNo=m.propertyNo and a.serialNo=m.serialNo and (case m.useDateS when null then '0000000' else m.useDateS end)<=" +util.Common.sqlChar(getQ_balanceDate()+"31")+" and nvl(m.useDateE,'9999999')>="+util.Common.sqlChar(getQ_balanceDate()+"31")+")) end) as useArea " +
				" from UNTLA_Land a " ;
        	execSQL1+=" where 1=1 " ;
        	execSQL1+=" and a.enterOrg="+util.Common.sqlChar(EnterOrg);
        	execSQL1+=" and a.ownership = "+ util.Common.sqlChar(getQ_ownership());
        	if (!"".equals(getQ_verify())) 
        		execSQL1+=" and a.verify = " + Common.sqlChar(getQ_verify()) ;
        	execSQL1+=" and a.propertyKind="+Common.sqlChar(rs.getString("propertyKindN"));
        	execSQL1+=" and a.grass='Y' ";

        	if (!"".equals(Common.get(getQ_balanceDate()))){
        		execSQL1 = execSQL1 + " and a.enterDate <= " + util.Common.sqlChar(getQ_balanceDate()+"31");
        	}
        	execSQL1+=" and a.dataState='2'";
        	execSQL1+=" and a.reduceDate>"+ util.Common.sqlChar(getQ_balanceDate()+"31");
        	execSQL1+=")";
        	rs3 = db.querySQL(execSQL1);
        	
        	Count1=0;Count2=0;Area1=0;Value1=0;Area2=0;Value2=0;
        	while(rs3.next()){
        		if (!"0".equals(rs3.getString("Area"))&&rs3.getString("Area")!=null){
        			Count1++;
        			Area1+=Double.parseDouble(rs3.getString("Area"));
        		} 
        		if(rs3.getString("Value")!=null)
        			Value1+=Double.parseDouble(rs3.getString("Value"));
        		if (!"0".equals(rs3.getString("useArea"))&&rs3.getString("useArea")!=null){
        			Count2++;
        			Area2+=Double.parseDouble(rs3.getString("useArea"));
        		}
        		if(rs3.getString("useArea")!=null&&rs3.getString("Unit")!=null)
        			Value2+=Double.parseDouble(rs3.getString("useArea"))*Double.parseDouble(rs3.getString("Unit"));
        		
        	}//whil rs2
        	data = new Object[15];
        	data[0] = EnterOrgName;
        	data[1] = BalaceDate;
            data[2] = OwnerShip;
            data[3] = PropertyKind;
        	data[4]=Name[13];
        	data[5]=Count1+"";
        	data[6]=Common.areaFormat(Area1+"")+"　";
        	data[7]=Common.valueFormat(Value1+"")+"　";
        	data[8]=Count2+"";
        	data[9]=Common.areaFormat(Area2+"")+"　";
        	data[10]=Common.valueFormat(Value2+"")+"　";
        	data[11]=(Count1-Count2)+"";
        	data[12]=Common.areaFormat((Area1-Area2)+"")+"　";
        	data[13]=Common.valueFormat((Value1-Value2)+"")+"　";
        	SumCount1+=Count1;SumCount2+=Count2;
        	SumArea1+=Area1;SumValue1+=Value1;SumArea2+=Area2;SumValue2+=Value2;
        	rowData.addElement(data);
        	
        	//抵繳稅款
        	execSQL1="(select case ((select g.newHoldArea from UNTLA_AdjustDetail g where a.enterOrg=g.enterOrg and a.ownership=g.ownership and a.propertyNo=g.propertyNo and a.serialNo=g.serialNo and rownum=1 and g.adjustDate in (select max(f.adjustDate) from UNTLA_AdjustDetail f where a.enterOrg=f.enterOrg and a.ownership=f.ownership and a.propertyNo=f.propertyNo and a.serialNo=f.serialNo and f.adjustDate<=" +util.Common.sqlChar(getQ_balanceDate()+"31")+
				")) when null then a.originalHoldArea else ((select g.newHoldArea from UNTLA_AdjustDetail g where a.enterOrg=g.enterOrg and a.ownership=g.ownership and a.propertyNo=g.propertyNo and a.serialNo=g.serialNo and rownum=1 and g.adjustDate in (select max(f.adjustDate) from UNTLA_AdjustDetail f where a.enterOrg=f.enterOrg and a.ownership=f.ownership and a.propertyNo=f.propertyNo and a.serialNo=f.serialNo and f.adjustDate<=" +util.Common.sqlChar(getQ_balanceDate()+"31")+")) end) as area," +
				" case ((select g2.newBookUnit from UNTLA_AdjustDetail g2 where a.enterOrg=g2.enterOrg and a.ownership=g2.ownership and a.propertyNo=g2.propertyNo and a.serialNo=g2.serialNo and rownum=1 and g2.adjustDate in (select max(f2.adjustDate) from UNTLA_AdjustDetail f2 where a.enterOrg=f2.enterOrg and a.ownership=f2.ownership and a.propertyNo=f2.propertyNo and a.serialNo=f2.serialNo and f2.adjustDate<=" +util.Common.sqlChar(getQ_balanceDate()+"31")+
				")) when null then a.originalUnit else ((select g2.newBookUnit from UNTLA_AdjustDetail g2 where a.enterOrg=g2.enterOrg and a.ownership=g2.ownership and a.propertyNo=g2.propertyNo and a.serialNo=g2.serialNo and rownum=1 and g2.adjustDate in (select max(f2.adjustDate) from UNTLA_AdjustDetail f2 where a.enterOrg=f2.enterOrg and a.ownership=f2.ownership and a.propertyNo=f2.propertyNo and a.serialNo=f2.serialNo and f2.adjustDate<=" +util.Common.sqlChar(getQ_balanceDate()+"31")+")) end) as Unit," +
				" case ((select g1.newBookValue from UNTLA_AdjustDetail g1 where a.enterOrg=g1.enterOrg and a.ownership=g1.ownership and a.propertyNo=g1.propertyNo and a.serialNo=g1.serialNo and rownum=1 and g1.adjustDate in (select max(f1.adjustDate) from UNTLA_AdjustDetail f1 where a.enterOrg=f1.enterOrg and a.ownership=f1.ownership and a.propertyNo=f1.propertyNo and a.serialNo=f1.serialNo and f1.adjustDate<=" +util.Common.sqlChar(getQ_balanceDate()+"31")+
				")) when null then a.originalBV else ((select g1.newBookValue from UNTLA_AdjustDetail g1 where a.enterOrg=g1.enterOrg and a.ownership=g1.ownership and a.propertyNo=g1.propertyNo and a.serialNo=g1.serialNo and rownum=1 and g1.adjustDate in (select max(f1.adjustDate) from UNTLA_AdjustDetail f1 where a.enterOrg=f1.enterOrg and a.ownership=f1.ownership and a.propertyNo=f1.propertyNo and a.serialNo=f1.serialNo and f1.adjustDate<=" +util.Common.sqlChar(getQ_balanceDate()+"31")+")) end) as value," +
				" case ((select sum(m.useArea) from UNTLA_Manage m where a.enterOrg=m.enterOrg and a.ownership=m.ownership and a.propertyNo=m.propertyNo and a.serialNo=m.serialNo and (csae m.useDateS when null then '0000000' else m.useDateS end)<=" +util.Common.sqlChar(getQ_balanceDate()+"31")+" and nvl(m.useDateE,'9999999')>="+util.Common.sqlChar(getQ_balanceDate()+"31")+
        		") when null then 0 else ((select sum(m.useArea) from UNTLA_Manage m where a.enterOrg=m.enterOrg and a.ownership=m.ownership and a.propertyNo=m.propertyNo and a.serialNo=m.serialNo and (csae m.useDateS when null then '0000000' else m.useDateS end)<=" +util.Common.sqlChar(getQ_balanceDate()+"31")+" and nvl(m.useDateE,'9999999')>="+util.Common.sqlChar(getQ_balanceDate()+"31")+")) end) as useArea " +
				" from UNTLA_Land a " ;
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
        	execSQL1+="select case ((select g.newHoldArea from UNTLA_AdjustDetail g where a.enterOrg=g.enterOrg and a.ownership=g.ownership and a.propertyNo=g.propertyNo and a.serialNo=g.serialNo and rownum=1 and g.adjustDate in (select max(f.adjustDate) from UNTLA_AdjustDetail f where a.enterOrg=f.enterOrg and a.ownership=f.ownership and a.propertyNo=f.propertyNo and a.serialNo=f.serialNo and f.adjustDate<=" +util.Common.sqlChar(getQ_balanceDate()+"31")+
				")) when null then a.originalHoldArea else ((select g.newHoldArea from UNTLA_AdjustDetail g where a.enterOrg=g.enterOrg and a.ownership=g.ownership and a.propertyNo=g.propertyNo and a.serialNo=g.serialNo and rownum=1 and g.adjustDate in (select max(f.adjustDate) from UNTLA_AdjustDetail f where a.enterOrg=f.enterOrg and a.ownership=f.ownership and a.propertyNo=f.propertyNo and a.serialNo=f.serialNo and f.adjustDate<=" +util.Common.sqlChar(getQ_balanceDate()+"31")+")) end) as area," +
				" case ((select g2.newBookUnit from UNTLA_AdjustDetail g2 where a.enterOrg=g2.enterOrg and a.ownership=g2.ownership and a.propertyNo=g2.propertyNo and a.serialNo=g2.serialNo and rownum=1 and g2.adjustDate in (select max(f2.adjustDate) from UNTLA_AdjustDetail f2 where a.enterOrg=f2.enterOrg and a.ownership=f2.ownership and a.propertyNo=f2.propertyNo and a.serialNo=f2.serialNo and f2.adjustDate<=" +util.Common.sqlChar(getQ_balanceDate()+"31")+
				")) when null then a.originalUnit else ((select g2.newBookUnit from UNTLA_AdjustDetail g2 where a.enterOrg=g2.enterOrg and a.ownership=g2.ownership and a.propertyNo=g2.propertyNo and a.serialNo=g2.serialNo and rownum=1 and g2.adjustDate in (select max(f2.adjustDate) from UNTLA_AdjustDetail f2 where a.enterOrg=f2.enterOrg and a.ownership=f2.ownership and a.propertyNo=f2.propertyNo and a.serialNo=f2.serialNo and f2.adjustDate<=" +util.Common.sqlChar(getQ_balanceDate()+"31")+")) end) as Unit," +
				" case ((select g1.newBookValue from UNTLA_AdjustDetail g1 where a.enterOrg=g1.enterOrg and a.ownership=g1.ownership and a.propertyNo=g1.propertyNo and a.serialNo=g1.serialNo and rownum=1 and g1.adjustDate in (select max(f1.adjustDate) from UNTLA_AdjustDetail f1 where a.enterOrg=f1.enterOrg and a.ownership=f1.ownership and a.propertyNo=f1.propertyNo and a.serialNo=f1.serialNo and f1.adjustDate<=" +util.Common.sqlChar(getQ_balanceDate()+"31")+
				")) when null then a.originalBV else ((select g1.newBookValue from UNTLA_AdjustDetail g1 where a.enterOrg=g1.enterOrg and a.ownership=g1.ownership and a.propertyNo=g1.propertyNo and a.serialNo=g1.serialNo and rownum=1 and g1.adjustDate in (select max(f1.adjustDate) from UNTLA_AdjustDetail f1 where a.enterOrg=f1.enterOrg and a.ownership=f1.ownership and a.propertyNo=f1.propertyNo and a.serialNo=f1.serialNo and f1.adjustDate<=" +util.Common.sqlChar(getQ_balanceDate()+"31")+")) end) as value," +
				" case ((select sum(m.useArea) from UNTLA_Manage m where a.enterOrg=m.enterOrg and a.ownership=m.ownership and a.propertyNo=m.propertyNo and a.serialNo=m.serialNo and (case m.useDateS when null then '0000000' else m.useDateS end)<=" +util.Common.sqlChar(getQ_balanceDate()+"31")+" and nvl(m.useDateE,'9999999')>="+util.Common.sqlChar(getQ_balanceDate()+"31")+
        		") when null then 0 else ((select sum(m.useArea) from UNTLA_Manage m where a.enterOrg=m.enterOrg and a.ownership=m.ownership and a.propertyNo=m.propertyNo and a.serialNo=m.serialNo and (case m.useDateS when null then '0000000' else m.useDateS end)<=" +util.Common.sqlChar(getQ_balanceDate()+"31")+" and nvl(m.useDateE,'9999999')>="+util.Common.sqlChar(getQ_balanceDate()+"31")+")) end) as useArea " +
				" from UNTLA_Land a " ;
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
        	
        	Count1=0;Count2=0;Area1=0;Value1=0;Area2=0;Value2=0;
        	while(rs3.next()){
        		if (!"0".equals(rs3.getString("Area"))&&rs3.getString("Area")!=null){
        			Count1++;
        			Area1+=Double.parseDouble(rs3.getString("Area"));
        		} 
        		if(rs3.getString("Value")!=null)
        			Value1+=Double.parseDouble(rs3.getString("Value"));
        		if (!"0".equals(rs3.getString("useArea"))&&rs3.getString("useArea")!=null){
        			Count2++;
        			Area2+=Double.parseDouble(rs3.getString("useArea"));
        		}
        		if(rs3.getString("useArea")!=null&&rs3.getString("Unit")!=null)
        			Value2+=Double.parseDouble(rs3.getString("useArea"))*Double.parseDouble(rs3.getString("Unit"));
        		
        	}//whil rs2
        	data = new Object[15];
        	data[0] = EnterOrgName;
        	data[1] = BalaceDate;
            data[2] = OwnerShip;
            data[3] = PropertyKind;
        	data[4]=Name[14];
        	data[5]=Count1+"";
        	data[6]=Common.areaFormat(Area1+"")+"　";
        	data[7]=Common.valueFormat(Value1+"")+"　";
        	data[8]=Count2+"";
        	data[9]=Common.areaFormat(Area2+"")+"　";
        	data[10]=Common.valueFormat(Value2+"")+"　";
        	data[11]=(Count1-Count2)+"";
        	data[12]=Common.areaFormat((Area1-Area2)+"")+"　";
        	data[13]=Common.valueFormat((Value1-Value2)+"")+"　";
        	SumCount1+=Count1;SumCount2+=Count2;
        	SumArea1+=Area1;SumValue1+=Value1;SumArea2+=Area2;SumValue2+=Value2;
        	rowData.addElement(data);
        	
        	//        	合計
        	data = new Object[15];
        	data[0] = EnterOrgName;
        	data[1] = BalaceDate;
            data[2] = OwnerShip;
            data[3] = PropertyKind;
        	data[4]=Name[15];
        	data[5]=SumCount1+"";
        	data[6]=Common.areaFormat(SumArea1+"")+"　";
        	data[7]=Common.valueFormat(SumValue1+"")+"　";
        	data[8]=SumCount2+"";
        	data[9]=Common.areaFormat(SumArea2+"")+"　";
        	data[10]=Common.valueFormat(SumValue2+"")+"　";
        	data[11]=(SumCount1-SumCount2)+"";
        	data[12]=Common.areaFormat((SumArea1-SumArea2)+"")+"　";
        	data[13]=Common.valueFormat((SumValue1-SumValue2)+"")+"　";
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
