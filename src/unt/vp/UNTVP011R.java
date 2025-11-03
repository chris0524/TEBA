

/*
*<br>程式目的：股票明細分類帳 
*<br>程式代號：untvp011r
*<br>撰寫日期：98/07/20
*<br>程式作者：Chu-Hung
*<br>--------------------------------------------------------
*<br>修改作者　　修改日期　　　修改目的
*<br>--------------------------------------------------------
*/

package unt.vp;

import java.sql.ResultSet;
import java.util.Vector;

import javax.swing.table.DefaultTableModel;

import net.sf.jasperreports.engine.data.JRTableModelDataSource;

import util.*;

public class UNTVP011R extends SuperBean{

	String q_enterOrg;
	String q_propertyno;
	String q_securityName;
	String q_enterOrgName;
	
	public String getQ_enterOrg(){ return checkGet(q_enterOrg); }
	public void setQ_enterOrg(String s){ q_enterOrg=checkSet(s); }
	public String getQ_securityName(){ return checkGet(q_securityName); }
	public void setQ_securityName(String s){ q_securityName=checkSet(s); }
	public String getQ_propertyno(){ return checkGet(q_propertyno); }
	public void setQ_propertyno(String s){ q_propertyno=checkSet(s); }
    public String getQ_enterOrgName(){ return checkGet(q_enterOrgName); }
	public void setQ_enterOrgName(String s){ q_enterOrgName=checkSet(s); }
	
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
	String enterOrgName;
	public String getEnterOrg(){ return checkGet(enterOrg); }
	public void setEnterOrg(String s){ enterOrg=checkSet(s); }
	public String getOwnership(){ return checkGet(ownership); }
	public void setOwnership(String s){ ownership=checkSet(s); }
	public String getPropertyNo(){ return checkGet(propertyNo); }
	public void setPropertyNo(String s){ propertyNo=checkSet(s); }
	public String getSerialNo(){ return checkGet(serialNo); }
	public void setSerialNo(String s){ serialNo=checkSet(s); }
	public String getEnterOrgName() { return checkGet(enterOrgName); }
	public void setEnterOrgName(String s) { enterOrgName = checkSet(s); } 
	
	String adjustdate;
	String propertyname;
	String adjustPropertyno;
	String adjustBookAmount;
	String adjustBookValue;
	String newbookamount;
    String newbookvalue;
	String notes;
	String checkModel;
	public String getAdjustdate(){ return checkGet(adjustdate); }
	public void setAdjustdate(String s){ adjustdate=checkSet(s); }
	public String getPropertyname(){ return checkGet(propertyname); }
	public void setPropertyname(String s){ propertyname=checkSet(s); }
	public String getAdjustPropertyno(){ return checkGet(adjustPropertyno); }
	public void setAdjustPropertyno(String s){ adjustPropertyno=checkSet(s); }
	public String getAdjustBookAmount(){ return checkGet(adjustBookAmount); }
	public void setAdjustBookAmount(String s){ adjustBookAmount=checkSet(s); }
	public String getAdjustBookValue(){ return checkGet(adjustBookValue); }
	public void setAdjustBookValue(String s){ adjustBookValue=checkSet(s); }
	public String getNewbookamount(){ return checkGet(newbookamount); }
	public void setNewbookamount(String s){ newbookamount=checkSet(s); }
	public String getNewbookvalue(){ return checkGet(newbookvalue); }
	public void setNewbookvalue(String s){ newbookvalue=checkSet(s); }
	public String getNotes(){ return checkGet(notes); }
	public void setNotes(String s){ notes=checkSet(s); }
	public String getCheckModel(){ return checkGet(checkModel); }
	public void setCheckModel(String s){ checkModel=checkSet(s); }

//	取得UNTVP_AdjustProof的Data
/*protected void getAdjustProofData() {
	Database db = new Database();	
	UNTVP011R obj = this;
	ResultSet rs;	
	String sql=" select b.adjustdate, c.propertyname, b.propertyno, b.adjustBookAmount, b.adjustBookValue, " +
	           " b.newbookamount, b.newbookvalue, b.notes " +
               " from UNTVP_AddProof a, UNTVP_AdjustProof b, SYSPK_PropertyCode c " +
               " where a.enterorg = b.enterorg and a.ownership = b.ownership and a.propertyno = b.propertyno " +
               " and a.serialno = "+util.Common.sqlChar(obj.getSerialNo()) +
               " and a.enterOrg = "+util.Common.sqlChar(obj.getEnterOrg()) +
               " and a.propertyNo = "+util.Common.sqlChar(obj.getPropertyNo()) +
               " and a.ownership = "+util.Common.sqlChar(obj.getOwnership()) +
               " and b.propertyno = c.propertyno and a.verify='Y' " +
	           "";	
	System.out.println("Adjust: "+sql);
	try{
		rs = db.querySQL(sql);
		if (rs.next()){
			obj.setAdjustdate(rs.getString("adjustdate"));
			obj.setPropertyname(rs.getString("propertyname"));
			obj.setAdjustPropertyno((rs.getString("propertyno")));
			obj.setAdjustBookAmount(rs.getString("adjustBookAmount"));
			obj.setAdjustBookValue(rs.getString("adjustBookValue"));
			obj.setNewbookamount(rs.getString("newbookamount"));
			obj.setNewbookvalue(rs.getString("newbookvalue"));
			obj.setNotes(rs.getString("notes"));
		} else {
			obj.setAdjustdate(" ");
			obj.setPropertyname(" ");
			obj.setAdjustPropertyno(" ");
			obj.setNotes(" ");
		}
	} catch (Exception e) {
		e.printStackTrace();
	} finally {
		db.closeAll();
	}
}*/

public DefaultTableModel getResultModel() throws Exception{
	UNTVP011R obj = this;
    DefaultTableModel model = new javax.swing.table.DefaultTableModel();
    Database db = new Database();
    try{
    	//12
    	String[] columns = new String[] {"ADDPSECURITYNAME","ADDENTERDATE","ADDPROPERTYNAMENO","ADDORIGINALAMOUNT",
    			                         "ADDORIGINALBV","ADDNOTES","ADDENTERORGNAME",
    			                         "ENTERORGPROPERTYKINDFUNDTYPE","subReportDataSource"
    			                        };
    	
    	String execSQL=" select a.enterorg, a.ownership, a.propertyno, a.serialno, c.organaname as enterOrgName, a.securityName, " +
    	" a.enterdate, b.propertyName, a.propertyno, a.originalamount, a.originalbv, a.notes " +
    	" from UNTVP_AddProof a,SYSPK_PropertyCode b, SYSCA_Organ c " +
    	" where a.propertyNo = b.propertyNo and a.enterorg = c.organid and a.verify='Y' " +
    	"";
    	
    	if (!"".equals(getQ_enterOrg())) {
    		execSQL += " and a.enterOrg = " + Common.sqlChar(getQ_enterOrg()) ;
    	} else {
    		if (!getIsAdminManager().equalsIgnoreCase("Y")) {
    			if ("Y".equals(getIsOrganManager())) {					
    				execSQL += " and a.enterOrg like '" + getOrganID().substring(0,5) + "%' ";
    			} else {
    				execSQL +=" and a.enterOrg = " + Common.sqlChar(getOrganID()) ;
    			}
    		}
    	}
    	
    	if (!"".equals(getQ_propertyno())) {
    		execSQL += " and a.caseno = " + Common.sqlChar(getQ_propertyno()) ;
    	} 
    	
    	execSQL += " order by a.enterDate " ;
    	//System.out.println(execSQL);  	
    	ResultSet rs = db.querySQL(execSQL);
        Vector rowData = new Vector();
        int i;
        while(rs.next()){
        	obj.setEnterOrg(rs.getString("enterOrg"));
			obj.setOwnership(rs.getString("ownership"));
			obj.setPropertyNo(rs.getString("propertyNo"));
			obj.setSerialNo(rs.getString("serialNo"));
			
        	Object[] data = new Object[9];
        	data[0] = rs.getString("securityName");
        	data[1] = rs.getString("enterdate");
        	data[2] = rs.getString("propertyName")+"/"+rs.getString("propertyno");
        	data[3] = new Long(rs.getString("originalamount"));
        	data[4] = new Double(rs.getString("originalbv"));
        	data[5] = rs.getString("notes");
        	data[6] = rs.getString("enterOrgName");
        	data[7] = rs.getString("enterOrg")+rs.getString("ownership")+rs.getString("propertyno")+rs.getString("serialno");
        	data[8] = new JRTableModelDataSource(getSubModel(rs.getString("serialNo")));
        	
        	for(i=0;i<9;i++)if(data[i]==null)data[i]=" ";
			if(obj.getCheckModel().equals("true")){
				rowData.addElement(data);
			}
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

public DefaultTableModel getSubModel(String serialno) throws Exception{
	UNTVP011R obj = this;
	String execSQL="";
	
	ResultSet rs;	
    DefaultTableModel model = new javax.swing.table.DefaultTableModel();
    Database db = new Database();
   
    try{
        String[] columns = new String[] {"ADJUSTDATE","ADJUSTPROPERTYNAME","ADJUSTBOOKAMOUNT","ADJUSTBOOKVALUE",
        		                         "ADJUSTBOOKAMOUNT2","ADJUSTBOOKVALUE2","ADJUSTNEWBOOKAMOUNT",
        		                         "ADJUSTNEWBOOKVALUE","ADJUSTNOTES"};
        execSQL=" select b.adjustdate, c.propertyname, b.propertyno, b.adjustBookAmount, b.adjustBookValue, " +
        " b.newbookamount, b.newbookvalue, b.notes " +
        " from UNTVP_AddProof a, UNTVP_AdjustProof b, SYSPK_PropertyCode c " +
        " where a.enterorg = b.enterorg and a.ownership = b.ownership and a.propertyno = b.propertyno and a.serialno = b.serialno" +
        " and a.serialno = '"+serialno+"' "+
        //" and b.propertyno = "+util.Common.sqlChar(q_propertyno) +
        //" and a.enterOrg = "+util.Common.sqlChar(obj.getEnterOrg()) +
        //" and a.propertyNo = "+util.Common.sqlChar(obj.getPropertyNo()) +
        //" and a.ownership = "+util.Common.sqlChar(obj.getOwnership()) +
        " and b.propertyno = c.propertyno and a.verify='Y' ";	
        
    	if (!"".equals(getQ_enterOrg())) {
    		execSQL += " and a.enterOrg = " + Common.sqlChar(getQ_enterOrg()) ;
    	} else {
    		if (!getIsAdminManager().equalsIgnoreCase("Y")) {
    			if ("Y".equals(getIsOrganManager())) {					
    				execSQL += " and a.enterOrg like '" + getOrganID().substring(0,5) + "%' ";
    			} else {
    				execSQL +=" and a.enterOrg = " + Common.sqlChar(getOrganID()) ;
    			}
    		}
    	}
    	
    	if (!"".equals(getQ_propertyno())) {
    		execSQL += " and a.caseno = " + Common.sqlChar(getQ_propertyno()) ;
    	} 
    	
    	execSQL += " order by b.adjustDate " ;
        
    	Vector rowData = new Vector();
    	int i;
    	Object[] data ;
		rs = db.querySQL(execSQL);
		//System.out.println("sub: "+execSQL);
		
    	while(rs.next()){
    		data = new Object[9];    		
    		data[0] = rs.getString("adjustdate");
    		data[1] = rs.getString("propertyname")+"/"+rs.getString("propertyno");
    		
    		if(Integer.parseInt(rs.getString("adjustBookAmount"))<0 ){
    			data[2] = new Long("0");
    		}else{
    			data[2] = new Long(rs.getString("adjustBookAmount"));
    		}
    		if(Integer.parseInt(rs.getString("adjustBookValue"))<0 ){
    			data[3] = new Double("0");
    		}else{
    			data[3] = new Double(rs.getString("adjustBookValue"));
    		}

    		if(Integer.parseInt(rs.getString("adjustBookAmount"))>0 ){
    			data[4] = new Long("0");
    		}else{
    			data[4] = new Long(rs.getString("adjustBookAmount"));
    		}
    		if(Integer.parseInt(rs.getString("adjustBookValue"))>0 ){
    			data[5] = new Double("0");
    		}else{
    			data[5] = new Double(rs.getString("adjustBookValue"));
    		}
    		
    		data[6] = new Long(rs.getString("newbookamount"));
    		data[7] = new Double(rs.getString("newbookvalue"));
    		data[8] = rs.getString("notes");
    		
    		obj.setCheckModel("true");

    		rowData.addElement(data);    
    	}
    	if(rowData.isEmpty() ){
    		data = new Object[9];   
    		data[0] = "";
    		data[1] = "";
    		data[2] = new Long("0");
    		data[3] = new Double("0");
    		data[4] = new Long("0");
    		data[5] = new Double("0");
    		data[6] = new Long("0");
    		data[7] = new Double("0");
    		data[8] = "";
    		obj.setCheckModel("true");
    		rowData.addElement(data);   
    	}
    Object[][] finalData = new Object[0][0];
    finalData = (Object[][])rowData.toArray(finalData);
    model.setDataVector(finalData, columns);  
    
	}catch(Exception x){
    	x.printStackTrace();
    }finally{
        db.closeAll();
    }
    return model;
    
}

}