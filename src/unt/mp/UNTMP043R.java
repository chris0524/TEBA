/*
*<br>程式目的：動產小標籤查詢檔 
*<br>程式代號：untmp037r
*<br>撰寫日期：94/11/29
*<br>程式作者：Cherry
*<br>--------------------------------------------------------
*<br>修改作者　　修改日期　　　修改目的
*<br>--------------------------------------------------------
*/

package unt.mp;

import java.sql.ResultSet;
import java.util.Vector;

import javax.swing.table.DefaultTableModel;

import util.*;

public class UNTMP043R extends QueryBean{
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
	UNTMP043R obj = this;
    DefaultTableModel model = new javax.swing.table.DefaultTableModel();
    Database db = new Database();
	Object[] data ;
	Vector rowData = new Vector();
	String enterDateName = "";

    try{
    	String[] columns = new String[] {"ENTERORGNAME","PROPERTYNAME","PROPERTYNO","KEEPERNAME","SOURCEDATE","LIMITYEAR","bookvalue","barcode","place"};

    	String execSQL= " select c.organsname as enterOrgName "
			  + " ,nvl(c.propertyname,c.propertyname1) as propertyname "
			  + " ,c.propertyno ,c.serialno "
			  + " ,c.keepername "
			  + " ,c.buydate "
			  + " ,c.limityear ,c.otherlimityear "
			  + " ,c.bookvalue "
			  + " ,c.barcode "
			  + " ,c.place "
			  + " from untmp_untmp044f c "
			  + " where 1=1 "
			  + " and c.editid=" + Common.sqlChar(getUserID())
			  + "";
    	System.out.println(execSQL);
       	if(q_blankSpace==null || q_blankSpace.equals("")){
    		q_blankSpace="0";
    	}
    	int blankSpace=Integer.parseInt(q_blankSpace);
    	if(blankSpace>0){
    	    while(blankSpace>0){  
    	        data = new Object[9];
    	        data[0] = " ";
    	        data[1] = " ";
    	        data[2] = " ";
    	        data[3] = " ";
    	        data[4] = " ";
    	        data[5] = " ";
    	        data[6] = " ";
    	        data[7] = null;
    	        data[8] = " ";

    	        blankSpace--;
    	        rowData.addElement(data);
    	    }
    	}
    	
        ResultSet rs = db.querySQL(execSQL);
        while(rs.next()){
           	data = new Object[9];
            data[0] = rs.getString("enterOrgName");
            data[1] = "名稱："+rs.getString("propertyName");
            data[2] = "編號："+rs.getString("propertyNo")+"-"+rs.getString("serialNo");
            if(obj.getQ_printKeeper().equals("Y")){
            	data[3] = "保管人："+Common.get(rs.getString("keeperName"));
            }else{
            	data[3] = "保管人：";
            }
            if(rs.getString("buydate")!=null){
                data[4] = "購買日期："+rs.getString("buydate").substring(0,3)+"/"+rs.getString("buydate").substring(3,5)+"/"+rs.getString("buydate").substring(5,7);
            }else{
                data[4] = "購買日期：";
            }
            if(rs.getString("limitYear")!=null){
                data[5] = "年限："+rs.getString("limitYear");
            }else{
                data[5] = "年限："+rs.getString("otherLimitYear");
            }
            data[6]="價值:"+Common.get(rs.getString("bookvalue"));
            data[7]=Common.get(rs.getString("barcode"));
            data[8]="地點:"+Common.get(rs.getString("place"));
            
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
