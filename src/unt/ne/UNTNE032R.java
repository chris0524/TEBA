

/*
*<br>程式目的：非消耗品減損清冊報表檔 
*<br>程式代號：untne032r
*<br>撰寫日期：94/11/30
*<br>程式作者：chris
*<br>--------------------------------------------------------
*<br>修改作者　　修改日期　　　修改目的
*<br>--------------------------------------------------------
*/

package unt.ne;

import java.sql.ResultSet;
import java.util.Vector;

import javax.swing.table.DefaultTableModel;

import util.*;

public class UNTNE032R extends SuperBean{
	String q_enterOrg;
	String q_enterOrgName;
	String q_ownership;
	String q_verify;
	String q_keepUnit;
	String q_keeper;
	String q_reduceDateS;
	String q_reduceDateE;
	String q_propertyKind;
	String q_fundType;
	String q_valuable;

	public String getQ_enterOrg(){ return checkGet(q_enterOrg); }
	public void setQ_enterOrg(String s){ q_enterOrg=checkSet(s); }
	public String getQ_enterOrgName(){ return checkGet(q_enterOrgName); }
	public void setQ_enterOrgName(String s){ q_enterOrgName=checkSet(s); }
	public String getQ_ownership(){ return checkGet(q_ownership); }
	public void setQ_ownership(String s){ q_ownership=checkSet(s); }
	public String getQ_verify(){ return checkGet(q_verify); }
	public void setQ_verify(String s){ q_verify=checkSet(s); }
	public String getQ_keepUnit(){ return checkGet(q_keepUnit); }
	public void setQ_keepUnit(String s){ q_keepUnit=checkSet(s); }
	public String getQ_keeper(){ return checkGet(q_keeper); }
	public void setQ_keeper(String s){ q_keeper=checkSet(s); }
	public String getQ_reduceDateS(){ return checkGet(q_reduceDateS); }
	public void setQ_reduceDateS(String s){ q_reduceDateS=checkSet(s); }
	public String getQ_reduceDateE(){ return checkGet(q_reduceDateE); }
	public void setQ_reduceDateE(String s){ q_reduceDateE=checkSet(s); }
	public String getQ_propertyKind(){ return checkGet(q_propertyKind); }
	public void setQ_propertyKind(String s){ q_propertyKind=checkSet(s); }
	public String getQ_fundType(){ return checkGet(q_fundType); }
	public void setQ_fundType(String s){ q_fundType=checkSet(s); }
	public String getQ_valuable(){ return checkGet(q_valuable); }
	public void setQ_valuable(String s){ q_valuable=checkSet(s); }


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
    	String[] columns = new String[] {"ENTERORGNAME","OWNERSHIP","REDUCEDATE1","PROPERTYKIND","PROPERTYNO","PROPERTYNAME","SPECIFICATION","LIMITYEAR","PROPERTYUNIT","ADJUSTBOOKAMOUNT","ADJUSTBOOKVALUE1","ADJUSTBOOKVALUE","BUYDATE","REDUCEDATE","CAUSE","CASENO","KEEPER","PROPERTYNO1"};
    	
    	String execSQL="select c.organAName as enterOrgName," +
    			"((select f.codename from SYSCA_CODE f where f.codekindid = 'PKD' and b.propertyKind=f.codeid)||' '||(select g.codename from SYSCA_CODE g where g.codekindid = 'FUD' and b.fundType=g.codeid))propertyKind," +
    			"b.propertyNo||'-'||b.serialNo as propertyNo," +
    			"d.propertyName," +
    			"b.specification||'/'||b.nameplate as specification," +
    			"d.limitYear,b.otherLimitYear," +
    			"d.propertyUnit,b.otherPropertyUnit," +
    			"b.adjustBookAmount," +
    			"b.oldBookUnit," +
    			"b.adjustBookValue," +
    			"b.buyDate," +
    			"a.reduceDate," +
    			"(select f.codename from SYSCA_CODE f where f.codekindid = 'CAC' and b.cause=f.codeid)cause,b.cause1," +
    			"a.caseNo," +
    			"(select h.keeperName from UNTMP_Keeper h where b.enterOrg=h.enterOrg and b.keepUnit=h.unitNo and b.keeper=h.keeperNo ) as keeper ";
    	execSQL+=" from UNTNE_ReduceProof a,UNTNE_ReduceDetail b,SYSCA_Organ c,SYSPK_PropertyCode2 d ";
    	execSQL+=" where a.enterorg=b.enterorg and  a.ownership=b.ownership and a.caseno=b.caseno and b.enterOrg=d.enterOrg and b.propertyNo=d.propertyNo and a.enterOrg=c.organID(+)";
    	execSQL=execSQL+" and d.propertyType='1' ";
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
    	
    	if (!"".equals(Common.get(getQ_verify())))
    		execSQL = execSQL + " and a.verify = " + util.Common.sqlChar(getQ_verify());
    	if(!"".equals(Common.get(getQ_keepUnit())))
    		execSQL = execSQL + " and b.keepUnit = " + util.Common.sqlChar(getQ_keepUnit());
    	if(!"".equals(Common.get(getQ_keeper())))
    		execSQL = execSQL + " and b.keeper = " + util.Common.sqlChar(getQ_keeper());
    	if (!"".equals(Common.get(getQ_reduceDateS())))
    		execSQL = execSQL + " and b.reduceDate >= " + util.Common.sqlChar(getQ_reduceDateS());
    	if (!"".equals(Common.get(getQ_reduceDateE())))
    		execSQL = execSQL + " and b.reduceDate <= " + util.Common.sqlChar(getQ_reduceDateE());
    	
    	if (!"".equals(Common.get(getQ_propertyKind())))
    		execSQL = execSQL + " and b.propertyKind = " + util.Common.sqlChar(getQ_propertyKind());
    	
    	if (!"".equals(Common.get(getQ_fundType()))){
    		execSQL = execSQL + " and b.fundType = " + util.Common.sqlChar(getQ_fundType());
    	}
    	if (!"".equals(Common.get(getQ_valuable())))
    		execSQL = execSQL + " and b.valuable = " + util.Common.sqlChar(getQ_valuable());
    	
    	
    	execSQL=execSQL+" order by a.enterOrg,propertyKind,propertyNo,b.serialNo ";
    	//System.out.println(execSQL);
    	
    	String tansToDouble;
    	//String DateTrn;
    	String limitYeartemp;
    	String PropertyUnit;
    	//double Doubletemp;
    	String OwnerShip="";
    	if("1".equals(Common.get(getQ_ownership()))){
    		OwnerShip="市有";
    		
    	}
    	else OwnerShip="國有";
    	String ReducwDate1="列印區間: "+getQ_reduceDateS().substring(0,3)+"/"+getQ_reduceDateS().substring(3,5)+"/"+getQ_reduceDateS().substring(5,7)+" 至 "+getQ_reduceDateE().substring(0,3)+"/"+getQ_reduceDateE().substring(3,5)+"/"+getQ_reduceDateE().substring(5,7)+" 止";
    	String OldBookUnit="";
    	String causetemp;
    	
    	ResultSet rs = db.querySQL(execSQL);
        Vector rowData = new Vector();
        while(rs.next()){
        	Object[] data = new Object[30];
        	data[0] = rs.getString("enterOrgName");
            data[1] = OwnerShip;
            data[2] = ReducwDate1;
            data[3] = rs.getString("propertyKind");
            data[4] = rs.getString("propertyNo");
            data[5] = rs.getString("propertyName");
            if(!"/".equals(rs.getString("specification")))
            	data[6] = rs.getString("specification");
            else
            	data[6]="";
            
            data[7] = rs.getString("limitYear");
        	limitYeartemp=rs.getString("otherLimitYear");;
        	if(data[7]==null)data[7]=limitYeartemp;
        	
            data[8] = rs.getString("propertyUnit");
            PropertyUnit= rs.getString("otherPropertyUnit");
            if(data[8]==null)data[8]=PropertyUnit;
            
            tansToDouble= rs.getString("adjustBookAmount");
            if(tansToDouble!=null)data[9]= new Double(Double.parseDouble(tansToDouble));
            else data[9]=null;
            
            OldBookUnit=rs.getString("oldBookUnit");
            tansToDouble= rs.getString("adjustBookValue");
            if(OldBookUnit!=null&&tansToDouble!=null)data[10]= new Double(Double.parseDouble(tansToDouble));
            else data[10]=null;
            if(tansToDouble!=null)data[11]= new Double(Double.parseDouble(tansToDouble));
            else data[11]=null;
            
            data[12] = Common.MaskDate(rs.getString("buyDate"));
            data[13] = Common.MaskDate(rs.getString("reduceDate"));
        	data[14] = rs.getString("cause");
            causetemp= rs.getString("cause1");
            if("其他".equals(data[14])||data[14]==null)data[14]=causetemp;
            
            data[15] = rs.getString("caseNo")+"　";
            data[16] = rs.getString("keeper");
            data[17]=rs.getString("propertyno").substring(0,1);
            
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
