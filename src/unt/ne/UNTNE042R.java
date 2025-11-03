/*
*<br>程式目的：動產標籤查詢檔 
*<br>程式代號：
*<br>撰寫日期：
*<br>程式作者：
*<br>--------------------------------------------------------
*<br>修改作者　　修改日期　　　修改目的
*<br>--------------------------------------------------------
*/

package unt.ne;

import java.sql.ResultSet;
import java.util.Vector;

import javax.swing.table.DefaultTableModel;

import util.*;

public class UNTNE042R extends SuperBean{
	String userID;
	public String getUserID(){ return checkGet(userID); }
	public void setUserID(String s){ userID=checkSet(s); }
	
	String q_blankSpace;
	String q_printKeeper;
    public String getQ_blankSpace(){ return checkGet(q_blankSpace); }
    public void setQ_blankSpace(String s){ q_blankSpace=checkSet(s); }
	public String getQ_printKeeper(){ return checkGet(q_printKeeper); }
	public void setQ_printKeeper(String s){ q_printKeeper=checkSet(s); }
	
	public DefaultTableModel getResultModel() throws Exception{
	UNTNE042R obj = this;
    DefaultTableModel model = new javax.swing.table.DefaultTableModel();
    Database db = new Database();
	Object[] data ;
	Vector rowData = new Vector();
	String enterDateName = "";
	//int i;
    try{
    	String[] columns = new String[] {"ENTERORGNAME","OWNERSHIPNAME","PROPERTYKINDNAME","PROPERTYNO","LIMITYEAR","PROPERTYNAME","BUYDATE","SPECIFICATION","KEEPERNAME","PLACE","bookvalue","barcode"};
    	String execSQL= " select a.organaname as enterOrgName"
    				  + " ,a.ownershipname "
    				  + " ,a.propertykindname "
    				  + " ,a.propertyno ,a.serialno "
    				  + " ,a.limityear ,a.otherlimityear "
    				  + " ,nvl(a.propertyname ,a.propertyname1) as propertyname "
    				  + " ,a.buydate "
    				  + " ,a.specification ,a.nameplate "
    				  + " ,a.keepername "
    				  + " ,a.place "
    				  + " ,a.bookvalue "
    				  + " ,a.barcode "
    				  + " from untne_untne044f a "
    				  + " where 1=1 "
    				  + " and a.editid=" + Common.sqlChar(getUserID())
    				  + "";
    	System.out.println(execSQL);
    	if(q_blankSpace==null || q_blankSpace.equals("")){
    		q_blankSpace="0";
    	}
    	int blankSpace=Integer.parseInt(q_blankSpace);
    	if(blankSpace>0){
    	    while(blankSpace>0){  
    	        data = new Object[12];
    	        data[0] = " ";
    	        data[1] = " ";
    	        data[2] = " ";
    	        data[3] = " ";
    	        data[4] = " ";
    	        data[5] = " ";
    	        data[6] = " ";
    	        data[7] = " ";
    	        data[8] = " ";
    	        data[9] = " ";
    	        data[10] = " ";
    	        data[11] = null ;
    	        blankSpace--;
    	        rowData.addElement(data);
    	    }
    	}
        ResultSet rs = db.querySQL(execSQL);
        while(rs.next()){
        	
        	data = new Object[12];
            data[0] = rs.getString("enterOrgName");
            data[1] = rs.getString("ownershipName");
            data[2] = rs.getString("propertyKindName");
            data[3] = "編號："+rs.getString("propertyNo")+"-"+rs.getString("serialNo");
            if(rs.getString("limitYear")!=null){
                data[4] = "年限："+rs.getString("limitYear");
            }else{
                data[4] = "年限："+rs.getString("otherLimitYear");
            }
            data[5] = "名稱："+rs.getString("propertyName");
            data[6] = "購買日期："+Common.MaskDate(rs.getString("buyDate"));
            data[7] = "規格："+Common.get(rs.getString("specification"))+"／"+Common.get(rs.getString("nameplate"));
            if(obj.getQ_printKeeper().equals("Y")){
            	data[8] = "保管人："+rs.getString("keeperName");
            }else{
                data[8] = "保管人：";
            }
            data[9] = "地點："+Common.get(rs.getString("place"));
          	data[10]="價值:"+Common.get(rs.getString("bookvalue"));
            data[11]=Common.get(rs.getString("barcode"));

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
