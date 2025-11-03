

/*
*<br>程式目的：未辦理保存登記建物清冊報表檔 
*<br>程式代號：untbu031r
*<br>撰寫日期：94/10/26
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

public class UNTBU031R extends SuperBean{

String enterOrg;
String enterOrgName;
String signNo;



public String getEnterOrg(){ return checkGet(enterOrg); }
public void setEnterOrg(String s){ enterOrg=checkSet(s); }
public String getEnterOrgName(){ return checkGet(enterOrgName); }
public void setEnterOrgName(String s){ enterOrgName=checkSet(s); }
public String getSignNo(){ return checkGet(signNo); }
public void setSignNo(String s){ signNo=checkSet(s); }
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
        int i;
        String[] columns = new String[] {"ENTERORG","ENTERORGNAME","OWNERSHIP","PROPERTYNO","PROPERTYNAME","PROPERTYNAME1","DOORPLATE","BOOKVALUE"};
    	String execSQL="select a.enterOrg, c.organAName as enterOrgName, a.ownership,a.propertyNo,a.serialNo,b.propertyName,a.propertyName1," +
    			"(select k.addrname from sysca_addr k where k.addrkind='1' and k.addrid=a.doorPlate1) as addrname1," +
    			"(select l.addrname from sysca_addr l where l.addrkind='2' and l.addrid=a.doorPlate2) as addrname2, " +
    			"(select m.addrname from sysca_addr m where m.addrkind='3' and m.addrid=a.doorPlate3) as addrname3, " +
    			"a.doorPlate4 as addrname4,a.bookValue " +
    			"from UNTBU_Building a,SYSPK_PropertyCode b, SYSCA_Organ c where a.propertyno=b.propertyno and a.enterOrg=c.organID ";
    	//execSQL=execSQL+" and a.propertyNo=b.propertyNo ";
    	if (!"".equals(Common.get(enterOrg)) & !"".equals(Common.get(enterOrgName))){
    		execSQL = execSQL + " and a.enterorg = " + util.Common.sqlChar(enterOrg);
    	}
    	else {
    		if (!getIsAdminManager().equalsIgnoreCase("Y")) {
    			if ("Y".equals(getIsOrganManager())) {					
    				execSQL += " and a.enterOrg like '" + getOrganID().substring(0,5) + "%' ";
    			} else {
    				execSQL+=" and a.enterOrg = " + Common.sqlChar(getOrganID()) ;
    			}
    		}
    	}
    	execSQL = execSQL + " and a.dataState = "+ util.Common.sqlChar("1");
    	execSQL = execSQL + " and a.verify = "+ util.Common.sqlChar("Y");
    	execSQL = execSQL + " and a.signNo is null ";
    	
    	execSQL=execSQL+" order by a.enterorg,a.ownership,a.propertyNo,a.serialNO";
    	
    	String tansToDouble;
    	
        ResultSet rs = db.querySQL(execSQL);
        Vector rowData = new Vector();
        while(rs.next()){
            Object[] data = new Object[20];
            data[0] = rs.getString("enterorg");
            data[1] = rs.getString("enterorgname");
            data[2] = rs.getString("ownership");
            if("2".equals(data[2])){data[2]="國有";}
            else data[2]="市有";
            data[3] = rs.getString("propertyNo").substring(0,7) + "-" + rs.getString("propertyNo").substring(7) + "-" + rs.getString("serialNo");
            data[4] = rs.getString("propertyName");
            data[5] = rs.getString("propertyName1");
            data[6] = rs.getString("addrname1") + rs.getString("addrname2") + rs.getString("addrname3") + rs.getString("addrname4");
            tansToDouble= rs.getString("bookValue");
            if(tansToDouble!=null)data[7]= new Double(Double.parseDouble(tansToDouble));
            else data[7]=null;
            
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
