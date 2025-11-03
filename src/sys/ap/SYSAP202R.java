/*
*<br>程式目的：保固維護記錄表   
*<br>程式代號：SYSAP202R
*<br>撰寫日期：0950918
*<br>程式作者：sam.hsueh
*<br>--------------------------------------------------------
*<br>修改作者　　修改日期　　　修改目的
*<br>--------------------------------------------------------
*/

package sys.ap;

import java.sql.ResultSet;
import java.util.Vector;

import javax.swing.table.DefaultTableModel;

import net.sf.jasperreports.engine.data.JRTableModelDataSource;

import util.*;

public class SYSAP202R extends SuperBean{
		
	String m_date;

	public String getM_date(){ return checkGet(m_date); }
	public void setM_date(String s){ m_date=checkSet(s); }


public DefaultTableModel getResultModel() throws Exception{
    DefaultTableModel model = new javax.swing.table.DefaultTableModel();
    Database db = new Database();
    String todayDate = Common.MaskDate(Datetime.getYYYMMDD());  
    int column = 3 ;
    try{
    	String[] columns = new String[column+1];
		int k=0;
    	for(int i=1; i<(column+1); i++){
    		columns[k] = "COLUMNS_"+i;
	    	k++;
    	}
		columns[3] = "DATE";	
	String execSQL= "select m_id, m_sno, m_note" +
			" from sysap_maintain" +
			" where 1=1" +
			" and m_date = " + Common.sqlChar(m_date) + 
			" order by m_id, m_sno" + 
			"";	
	String[] SQLName = new String[] {"m_id","m_sno","m_note"};
	ResultSet rs = db.querySQL(execSQL);
    Vector rowData = new Vector();
    int count=0;
    String strID="";
    while(rs.next()){
    	count++;
    	Object[] data = new Object[column+1];
    		if(!strID.equals(rs.getString(SQLName[0]))){
    			data[0] = rs.getString(SQLName[0]);
        	}else{
        		data[0]="";
        	}
    		data[1] = "　"+rs.getString(SQLName[1])+" : ";
    		data[2] = rs.getString(SQLName[2]);

    	strID=rs.getString(SQLName[0]);
     	data[column] = Common.MaskDate(m_date);
     	rowData.addElement(data);
     	
    }
    Object[][] data1 = new Object[0][0];
    data1 = (Object[][])rowData.toArray(data1);
    model.setDataVector(data1, columns);
  
    }catch(Exception x){
       x.printStackTrace();
    }finally{
        db.closeAll();
    }
    return model;
}

}
