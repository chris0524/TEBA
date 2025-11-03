

/*
*<br>程式目的：動產保管人清冊報表檔 
*<br>程式代號：untmp031r
*<br>撰寫日期：94/11/25
*<br>程式作者：chris
*<br>--------------------------------------------------------
*<br>修改作者　　修改日期　　　修改目的
*<br>--------------------------------------------------------
*/

package unt.mp;

import java.sql.ResultSet;
import java.util.Vector;

import javax.swing.table.DefaultTableModel;

import util.*;

public class UNTMP031R extends SuperBean{
	String q_enterOrg;
	String q_enterOrgName;
	String q_ownership;
	String q_dataState;
	String q_verify;
	String q_keepBureau;
	String q_keepUnit;
	String q_keeper;
	String q_propertyKind;
	String q_fundType;
	String q_valuable;
	String q_print;

	public String getQ_enterOrg(){ return checkGet(q_enterOrg); }
	public void setQ_enterOrg(String s){ q_enterOrg=checkSet(s); }
	public String getQ_enterOrgName(){ return checkGet(q_enterOrgName); }
	public void setQ_enterOrgName(String s){ q_enterOrgName=checkSet(s); }
	public String getQ_ownership(){ return checkGet(q_ownership); }
	public void setQ_ownership(String s){ q_ownership=checkSet(s); }
	public String getQ_dataState(){ return checkGet(q_dataState); }
	public void setQ_dataState(String s){ q_dataState=checkSet(s); }
	public String getQ_verify(){ return checkGet(q_verify); }
	public void setQ_verify(String s){ q_verify=checkSet(s); }
	public String getQ_keepBureau(){ return checkGet(q_keepBureau); }
	public void setQ_keepBureau(String s){ q_keepBureau=checkSet(s); }
	public String getQ_keepUnit(){ return checkGet(q_keepUnit); }
	public void setQ_keepUnit(String s){ q_keepUnit=checkSet(s); }
	public String getQ_keeper(){ return checkGet(q_keeper); }
	public void setQ_keeper(String s){ q_keeper=checkSet(s); }
	public String getQ_propertyKind(){ return checkGet(q_propertyKind); }
	public void setQ_propertyKind(String s){ q_propertyKind=checkSet(s); }
	public String getQ_fundType(){ return checkGet(q_fundType); }
	public void setQ_fundType(String s){ q_fundType=checkSet(s); }
	public String getQ_valuable(){ return checkGet(q_valuable); }
	public void setQ_valuable(String s){ q_valuable=checkSet(s); }
	public String getQ_print(){ return checkGet(q_print); }
	public void setQ_print(String s){ q_print=checkSet(s); }

	String isOrganManager;
    String isAdminManager;
    String organID;    
    public String getOrganID() { return checkGet(organID); }
    public void setOrganID(String s) { organID = checkSet(s); }
    public String getIsOrganManager() { return checkGet(isOrganManager); }
    public void setIsOrganManager(String s) { isOrganManager = checkSet(s); }
    public String getIsAdminManager() { return checkGet(isAdminManager); }
    public void setIsAdminManager(String s) { isAdminManager = checkSet(s); } 

	String enterOrg;
	String ownership;
    String propertyNo;
	String serialNo;
	String equipmentName;

	public String getEnterOrg(){ return checkGet(enterOrg); }
	public void setEnterOrg(String s){ enterOrg=checkSet(s); }
	public String getOwnership(){ return checkGet(ownership); }
	public void setOwnership(String s){ ownership=checkSet(s); }
	public String getPropertyNo(){ return checkGet(propertyNo); }
	public void setPropertyNo(String s){ propertyNo=checkSet(s); }
	public String getSerialNo(){ return checkGet(serialNo); }
	public void setSerialNo(String s){ serialNo=checkSet(s); }
	public String getEquipmentName(){ return checkGet(equipmentName); }
	public void setEquipmentName(String s){ equipmentName=checkSet(s); }

public DefaultTableModel getResultModel() throws Exception{
    DefaultTableModel model = new javax.swing.table.DefaultTableModel();
    Database db = new Database();
    try{
    	String[] columns = new String[] {"KEEPUNIT","ENTERORGNAME","OWNERSHIP","PROPERTYNO","PROPERTYNAME","PROPERTYNAME1","SPECIFICATION","PROPERTYUNIT","SOURCEDATE","LIMITYEAR","PLACE","BOOKVALUE","EQUIPMENTNAME","KEEPER","BOOKAMOUNT"};
    	
    	String execSQL="select b.enterOrg as checkEnterOrg, b.ownership as checkOwnership, b.propertyNo as checkPropertyNo, b.serialNo as checkSerialNo, " +
    					" (select z.codeName from sysca_code z where 1=1 and (z.codeKindID='owa' or z.codeKindID='OWA') and a.ownership=z.codeID) as ownershipName, " +
		    			//" e.unitName as keepUnit," +
		    			//" c.organAName as enterOrgName," +
		    			" substr(b.propertyNo,0,7)||'-'||substr(b.propertyNo,8)||'-'||b.serialNo as propertyNo," +
		    			" d.propertyName," +
		    			" a.propertyName1," +
		    			" a.specification||'/'||a.nameplate as specification," +
		    			" d.propertyUnit, a.otherPropertyUnit, " +
		    			" a.sourceDate," +
		    			" d.limitYear,a.otherLimitYear," +
		    			" b.place," +
		    			" b.bookValue," +
		    			" (select g.unitName||' '||h.keeperName from UNTMP_KeepUnit g,UNTMP_Keeper h where b.enterOrg=h.enterOrg and b.keepUnit=h.unitNo and b.keeper=h.keeperNo and b.enterOrg=g.enterOrg and b.keepUnit=g.unitNo) as keeper, " +
		    			" b.bookAmount, " +
		    			" (select e.unitName from UNTMP_KeepUnit e where  b.enterOrg=e.enterOrg and b.useUnit=e.unitNo )keepUnit, "+
		    			" (select c.organAName from SYSCA_Organ c where a.enterOrg=c.organID(+))enterOrgName "+
		    			" from UNTMP_Movable a,UNTMP_MovableDetail b,SYSPK_PropertyCode d "+
				    	" where a.enterorg=b.enterorg and  a.ownership=b.ownership and  b.propertyNo=a.propertyNo and a.lotNo=b.lotNo and b.propertyNo=d.propertyNo "+
				    	" and d.enterOrg in ('000000000A',a.enterOrg) "+
				    	" and d.propertyType='1' and b.dataState='1' ";
    	if (!"".equals(getQ_enterOrg())) {
    		execSQL+=" and a.enterOrg = " + Common.sqlChar(getQ_enterOrg()) ;
    	} else {
    		if (!getIsAdminManager().equalsIgnoreCase("Y")) {
    			if ("Y".equals(getIsOrganManager())) {					
    				execSQL += " and a.enterOrg like '" + getOrganID().substring(0,5) + "%' ";
    			} else {
    				execSQL +=" and a.enterOrg = " + Common.sqlChar(getOrganID()) ;
    			}
    		} 
    	}
    	if (!"".equals(Common.get(getQ_ownership())))
    		execSQL += " and a.ownership = " + util.Common.sqlChar(getQ_ownership());
    	if(!"".equals(Common.get(getQ_keepUnit())))
    		execSQL += " and b.keepUnit = " + util.Common.sqlChar(getQ_keepUnit());
    	if(!"".equals(Common.get(getQ_keeper())))
    		execSQL += " and b.keeper = " + util.Common.sqlChar(getQ_keeper());
    	
    	if (!"".equals(Common.get(getQ_propertyKind())))
    		execSQL += " and a.propertyKind = " + util.Common.sqlChar(getQ_propertyKind());
    	
    	if (!"".equals(Common.get(getQ_fundType()))){
    		execSQL += " and a.fundType = " + util.Common.sqlChar(getQ_fundType());
    	}
    	if (!"".equals(Common.get(getQ_valuable())))
    		execSQL += " and a.valuable = " + util.Common.sqlChar(getQ_valuable());
    	if (!"".equals(Common.get(getQ_verify())))
    		execSQL += " and a.verify = " + util.Common.sqlChar(getQ_verify());
    		
    	if("2".equals(Common.get(getQ_print()))){
    	    	execSQL += " and a.fundType is null" ;
    	}else if("3".equals(Common.get(getQ_print())))
    	    	execSQL += " and a.fundType is not null" ;
    	    	
    	execSQL += " order by a.enterOrg,keeper,propertyNo,b.serialNo ";
    	String tansToDouble;
    	//String DateTrn;
    	String limitYeartemp;
    	//double Doubletemp;
    	System.out.println(execSQL);
    	ResultSet rs = db.querySQL(execSQL);
        Vector rowData = new Vector();
        while(rs.next()){
        	setEnterOrg(rs.getString("checkEnterOrg"));
        	setOwnership(rs.getString("checkOwnership"));
        	setPropertyNo(rs.getString("checkPropertyNo"));
        	setSerialNo(rs.getString("checkSerialNo"));
        	Object[] data = new Object[15];
        	data[0] = rs.getString("keepUnit");
            data[1] = rs.getString("enterOrgName");
            data[2] = rs.getString("ownershipName");
            data[3] = rs.getString("propertyNo");
            data[4] = rs.getString("propertyName");
            data[5] = rs.getString("propertyName1");
            if(!"/".equals(rs.getString("specification")))
            	data[6] = rs.getString("specification");
            else
            	data[6]="";
            if(!"".equals(Common.get(rs.getString("propertyUnit"))))
                data[7] = rs.getString("propertyUnit");
            else
            	data[7] = rs.getString("otherPropertyUnit");
            data[8] = Common.MaskDate(rs.getString("sourceDate"));
        	data[9] = rs.getString("limitYear");
        	limitYeartemp=rs.getString("otherLimitYear");;
        	if(data[9]==null)data[9]=limitYeartemp;
        	
        	data[10] = rs.getString("place");
        	tansToDouble= rs.getString("bookValue");
            if(tansToDouble!=null)data[11]= new Double(Double.parseDouble(tansToDouble));
            else data[11]=null;
            getTotalEquipmentName();
            data[12] = getEquipmentName();
            data[13] = rs.getString("keeper");
            data[14] = Common.valueFormat(rs.getString("bookAmount"));
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

//以下做設備的連結
protected void getTotalEquipmentName(){
    StringBuffer equipmentName = new StringBuffer("");
	Database db = new Database();	
	ResultSet rs;	
	String sql="select a.equipmentName " +
				" from UNTMP_Attachment2 a" +
				" where 1=1 and a.dataState='1'" + 
				" and a.enterOrg=" + util.Common.sqlChar(getEnterOrg())+
				" and a.ownership=" + util.Common.sqlChar(getOwnership())+
				" and a.propertyNo=" + util.Common.sqlChar(getPropertyNo())+
				" and a.serialNo=" + util.Common.sqlChar(getSerialNo())+
				"";	
	try{
		rs = db.querySQL(sql);
		while (rs.next()){
		    equipmentName.append("、"+rs.getString("equipmentName"));
		}
		if(equipmentName.toString().equals("")){
		    equipmentName.append("、 ");
		}
		setEquipmentName(equipmentName.toString().substring(1));
	} catch (Exception e) {
		e.printStackTrace();
	} finally {
		db.closeAll();
	}
}

}
