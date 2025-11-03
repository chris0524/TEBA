/*
*<br>程式目的：他項權利證明書差異清冊查詢檔 
*<br>程式代號：untrt013r
*<br>撰寫日期：94/11/22
*<br>程式作者：Cherry
*<br>--------------------------------------------------------
*<br>修改作者　　修改日期　　　修改目的
*<br>--------------------------------------------------------
*/

package unt.rt;

import java.sql.ResultSet;
import java.util.Vector;

import javax.swing.table.DefaultTableModel;

import util.*;

public class UNTRT013R extends UNTRT008Q{
	
public DefaultTableModel getResultModel() throws Exception{
	UNTRT013R obj = this;
    DefaultTableModel model = new javax.swing.table.DefaultTableModel();
    Database db = new Database();
    try{
    	String[] columns = new String[] {"ENTERORGNAME","OWNERSHIP","OWNERSHIPNAME","DATE","PROPERTYNO","PROPERTYNAME","ENTERDATE","PROOFDOC1"};

    	String execSQL="select c.ORGANANAME as enterOrgName, a.ownership, decode(a.ownership,'1','市有','2','國有','') ownershipName, (a.propertyNo || '-' || a.serialNo) as propertyNo, " +
    					" b.propertyName, a.enterDate, a.proofDoc1 " +
    					" from UNTRT_AddProof a,SYSPK_PropertyCode b, SYSCA_Organ c "+
						" where 1=1 and a.dataState='1' and a.nonProof='Y' and a.proofVerify='N' " +
						" and a.propertyNo = b.propertyNo and a.enterOrg = c.organID "+
						"";

		if (!"".equals(getQ_enterOrg())) {
			execSQL +=" and a.enterOrg = " + Common.sqlChar(getQ_enterOrg()) ;
		} else {
			if (!getIsAdminManager().equalsIgnoreCase("Y")) {
				if ("Y".equals(getIsOrganManager())) {					
					execSQL += " and a.enterOrg like '" + getOrganID().substring(0,5) + "%' ";					
				} else {
					execSQL +=" and a.enterOrg = " + Common.sqlChar(getOrganID()) ;
				}
			}
		}
    	if (!"".equals(getQ_ownership()))
    		execSQL +=" and a.ownership = " + util.Common.sqlChar(getQ_ownership());
		if (!"".equals(getQ_propertyNoS()))
			execSQL +=" and a.propertyNo >= " + Common.sqlChar(getQ_propertyNoS());		
		if (!"".equals(getQ_propertyNoE()))
			execSQL +=" and a.propertyNo<=" + Common.sqlChar(getQ_propertyNoE());						
		if (!"".equals(getQ_propertyKind()))
			execSQL +=" and a.propertyKind = " + Common.sqlChar(getQ_propertyKind()) ;
		if (!"".equals(getQ_fundType()))
			execSQL +=" and a.fundType = " + Common.sqlChar(getQ_fundType()) ;
    	execSQL+=" order by a.ownership, a.propertyNo, a.serialNo";
        ResultSet rs = db.querySQL(execSQL);
        Vector rowData = new Vector();
        int i;
        while(rs.next()){
        	
        	Object[] data = new Object[8];
            data[0] = rs.getString("enterOrgName");
            data[1] = rs.getString("ownership");
            data[2] = rs.getString("ownershipName");
            data[3] = Datetime.getYYY()+"/"+Datetime.getMM()+"/"+Datetime.getYYYMMDD().substring(5,7);
            data[4] = rs.getString("propertyNo");
            data[5] = rs.getString("propertyName");
            data[6] = rs.getString("enterDate").substring(0,3)+"/"+rs.getString("enterDate").substring(3,5)+"/"+rs.getString("enterDate").substring(5,7);
            if(rs.getString("proofDoc1")==null){
                data[7] = "免繕狀";
            }else{
                data[7] = rs.getString("proofDoc1");
            }
            //for(i=0;i<8;i++)if(data[i]==null)data[i]="";
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
