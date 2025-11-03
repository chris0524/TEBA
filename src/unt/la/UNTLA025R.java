

/*
*<br>程式目的：土地公告地價差額清冊報表檔 
*<br>程式代號：untla025r
*<br>撰寫日期：94/11/11
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

public class UNTLA025R extends SuperBean{

	String enterOrg;
	String ownership;
	String bulletinDate;
	String signNo1;
	String signNo2;
	String propertyKind;
	String fundType;
	String grass;


	public String getEnterOrg(){ return checkGet(enterOrg); }
	public void setEnterOrg(String s){ enterOrg=checkSet(s); }
	public String getOwnership(){ return checkGet(ownership); }
	public void setOwnership(String s){ ownership=checkSet(s); }
	public String getBulletinDate(){ return checkGet(bulletinDate); }
	public void setBulletinDate(String s){ bulletinDate=checkSet(s); }
	public String getSignNo1(){ return checkGet(signNo1); }
	public void setSignNo1(String s){ signNo1=checkSet(s); }
	public String getSignNo2(){ return checkGet(signNo2); }
	public void setSignNo2(String s){ signNo2=checkSet(s); }
	public String getPropertyKind(){ return checkGet(propertyKind); }
	public void setPropertyKind(String s){ propertyKind=checkSet(s); }
	public String getFundType(){ return checkGet(fundType); }
	public void setFundType(String s){ fundType=checkSet(s); }
	public String getGrass(){ return checkGet(grass); }
	public void setGrass(String s){ grass=checkSet(s); }
	
	String q_enterOrg;
	String q_ownership;
	String q_bulletinDate;
	String q_signNo1;
	String q_signNo2;
	String q_propertyKind;
	String q_fundType;
	String q_grass;


	public String getQ_enterOrg(){ return checkGet(q_enterOrg); }
	public void setQ_enterOrg(String s){ q_enterOrg=checkSet(s); }
	public String getQ_ownership(){ return checkGet(q_ownership); }
	public void setQ_ownership(String s){ q_ownership=checkSet(s); }
	public String getQ_bulletinDate(){ return checkGet(q_bulletinDate); }
	public void setQ_bulletinDate(String s){ q_bulletinDate=checkSet(s); }
	public String getQ_signNo1(){ return checkGet(q_signNo1); }
	public void setQ_signNo1(String s){ q_signNo1=checkSet(s); }
	public String getQ_signNo2(){ return checkGet(q_signNo2); }
	public void setQ_signNo2(String s){ q_signNo2=checkSet(s); }
	public String getQ_propertyKind(){ return checkGet(q_propertyKind); }
	public void setQ_propertyKind(String s){ q_propertyKind=checkSet(s); }
	public String getQ_fundType(){ return checkGet(q_fundType); }
	public void setQ_fundType(String s){ q_fundType=checkSet(s); }
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
    	String[] columns = new String[] {"ENTERORGNAME","OWNERSHIP","VALUABLE","PROPERTYKIND","FUNDTYPE","SIGNNO","SIGNDESC","OLDHOLDAREA","OLDBOOKUNIT","NEWBOOKUNIT","ADJUSTBOOKVALUE","SIGNNO1","OLDHOLDAREAS"};
    	
    	String execSQL="select (select c.organAName from SYSCA_Organ c where a.enterOrg=c.organID) as enterOrgName,a.valuable,a.propertykind,(select f.codename  from SYSCA_CODE f where f.codekindid = 'FUD' and a.fundtype=f.codeid )as fundtype," +
    			"(select e.signDesc from SYSCA_SIGN e where substr(a.signNo,1,3)||'0000' = e.signNo) as signNo2," +
    			"a.signno, a.oldHoldArea,a.oldBookUnit,a.newBookUnit,a.adjustBookValue,a.signno as signno1 ";
    	execSQL=execSQL+" from UNTLA_AdjustDetail a ";
    	execSQL=execSQL+" where 1=1 ";
    	execSQL+=" and a.cause='1' ";
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
    	if (!"".equals(Common.get(getQ_ownership())))
    		execSQL = execSQL + " and a.ownership = " + util.Common.sqlChar(getQ_ownership());
    	if (!"".equals(Common.get(getQ_bulletinDate())))
    		execSQL = execSQL + " and a.bulletinDate = " + util.Common.sqlChar(getQ_bulletinDate());
    	
    	if (!"".equals(Common.get(getQ_signNo2())))
    		execSQL = execSQL + " and a.signNo like " + util.Common.sqlChar(getQ_signNo2().substring(0,3)+"%");
    	else if(!"".equals(Common.get(getQ_signNo1())))
    		execSQL = execSQL + " and a.signNo like " + util.Common.sqlChar(getQ_signNo1().substring(0,1)+"%");
    	
    	if (!"".equals(Common.get(getQ_propertyKind())))
    		execSQL = execSQL + " and a.propertyKind = " + util.Common.sqlChar(getQ_propertyKind());
    	
    	if (!"".equals(Common.get(getQ_fundType()))){
    		execSQL = execSQL + " and a.fundType = " + util.Common.sqlChar(getQ_fundType());
    	}
    	if (!"".equals(Common.get(getQ_grass())))
    		execSQL = execSQL + " and a.grass = " + util.Common.sqlChar(getQ_grass());
    	
    	
    	execSQL=execSQL+" order by a.valuable,a.propertyKind,fundType,signNo1";
    	
    	
    	String tansToDouble;
    	double Doubletemp;
    	String enterownership="";
    	//System.out.println(getQ_ownership()+"i");
    	if("1".equals(Common.get(getQ_ownership()))){
    		enterownership=getQ_bulletinDate().substring(1,3)+"年度市有土地公告地價差額清冊";
    		
    	}
    	else enterownership=getQ_bulletinDate().substring(1,3)+"年度國有土地公告地價差額清冊";
    	ResultSet rs = db.querySQL(execSQL);
        Vector rowData = new Vector();
        while(rs.next()){
        	Object[] data = new Object[13];
            data[0] = rs.getString("enterOrgName");
            data[1] = enterownership;
            data[2] = rs.getString("valuable");
            if("Y".equals(data[2]))data[2]="珍貴財產";
            else data[2]="";
            data[3] = rs.getString("propertykind");
            if("01".equals(data[3]))data[3]="公務用";
            else if("02".equals(data[3]))data[3]="公共用";
            else if("03".equals(data[3]))data[3]="事業用";
            else data[3]="非公用";
            data[4] = rs.getString("fundType");
            data[5] = rs.getString("signNo2");
            data[6] = getSignDescName(rs.getString("signNo").substring(0,7));
            if("-地號".equals(data[6]))data[6]="";
            
            tansToDouble= rs.getString("oldHoldArea");
            if(tansToDouble!=null)data[7]= new Double(Double.parseDouble(tansToDouble));
            else data[7]=null;
            
            tansToDouble= rs.getString("oldBookUnit");
            if(tansToDouble!=null)data[8]= new Double(Double.parseDouble(tansToDouble));
            else data[8]=null;
            
            tansToDouble= rs.getString("newBookUnit");
            if(tansToDouble!=null)data[9]= new Double(Double.parseDouble(tansToDouble));
            else data[9]=null;
            
            
            tansToDouble= rs.getString("adjustBookValue");
            if(tansToDouble!=null)data[10]= new Double(Double.parseDouble(tansToDouble));
            else data[10]=null;
            
            data[11]=rs.getString("signNo1").substring(0,3);
            data[12]=Common.areaFormat(rs.getString("oldHoldArea"))+"　";
            //for(i=0;i<23;i++)if(data[i]==null)data[i]="";
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
