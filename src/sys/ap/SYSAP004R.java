

/*
*<br>程式目的：建物毀損報廢單報表檔 
*<br>程式代號：sysap004r
*<br>撰寫日期：96/01/08
*<br>程式作者：Jim Chou
*<br>--------------------------------------------------------
*<br>修改作者　　修改日期　　　修改目的
*<br>--------------------------------------------------------
*/

package sys.ap;

import java.sql.ResultSet;
import java.util.Vector;

import javax.swing.table.DefaultTableModel;

import util.*;

public class SYSAP004R extends SuperBean{
    
	String isstop;
	String organid;
	String groupid;
	String q_kind;
	
	public String getIsstop(){ return checkGet(isstop); }
	public void setIsstop(String s){ isstop=checkSet(s); }
	
	public String getOrganid(){ return checkGet(organid); }
	public void setOrganid(String s){ organid=checkSet(s); }
	
	public String getGroupid(){ return checkGet(groupid); }
	public void setGroupid(String s){ groupid=checkSet(s); }
	
	public String getQ_kind(){ return checkGet(q_kind); }
	public void setQ_kind(String s){ q_kind=checkSet(s); }
	
	String q_type;
	public String getQ_type(){ return checkGet(q_type); }
	public void setQ_type(String s){ q_type=checkSet(s); }
public DefaultTableModel getResultModel() throws Exception{
	String whereSQL="";
	if("1".equals(q_type)){
		whereSQL = " and a.ismanager='Y' " ; 
	}else{
		whereSQL = " and a.organid like 'KOC%' ";
	}
    DefaultTableModel model = new javax.swing.table.DefaultTableModel();
    Database db = new Database();
    try{
    	String[] columns = new String[] {"ORGANSNAME","EMPNAME","EMPTEL","EMPEMAIL","ENTERORGNAME","TODAY"};
    	String execSQL = " select a.organid,b.organSName ,a.empname ,a.emptel ,a.empemail " + "\n"
    				   + " from SYSAP_Emp a,SYSCA_Organ b " + "\n"
    				   + " where a.organID=b.organID " + "\n"
    				   + " and substr(a.organID,1,7)!= '3839999' " + "\n"
    				   + " and a.groupid not in ('test','council','class','sys') " + "\n"
    				   + whereSQL
    				   + " and a.isadminmanager='N' " + "\n"
    				   + " and a.isstop = 'N' " + "\n"
    				   + " order by a.organid ";
    	System.out.println("execSQL "+execSQL);
        ResultSet rs = db.querySQL(execSQL);   
        Vector rowData = new Vector();
        while(rs.next()){
        	Object[] data = new Object[6];
        	data[0] = rs.getString("organSName");
            data[1] = rs.getString("empname");
            data[2] = rs.getString("emptel");
        	data[3] = rs.getString("empemail");
        	data[4] = "";
        	data[5] = Datetime.getYYY()+"/"+Datetime.getMM()+"/"+Datetime.getYYYMMDD().substring(5);
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
