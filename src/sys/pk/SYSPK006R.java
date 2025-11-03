/*
*<br>程式目的：財物標準分類核定表
*<br>程式代號：syspk06r
*<br>撰寫日期：96/3/27
*<br>程式作者：Cherry
*<br>--------------------------------------------------------
*<br>修改作者　　修改日期　　　修改目的
*<br>--------------------------------------------------------
*/

package sys.pk;

import java.sql.ResultSet;
import java.util.Vector;
import javax.swing.table.DefaultTableModel;
import util.*;

public class SYSPK006R extends SuperBean{

	String q_highestLevelDateB;
	String q_highestLevelDateE;
	String q_highestLevelIsAgree;
	String q_applyDateB;
	String q_applyDateE;
	String q_cityIsAgree;
	String q_isClose;

	public String getQ_highestLevelDateB(){ return checkGet(q_highestLevelDateB); }
	public void setQ_highestLevelDateB(String s){ q_highestLevelDateB=checkSet(s); }
	public String getQ_highestLevelDateE(){ return checkGet(q_highestLevelDateE); }
	public void setQ_highestLevelDateE(String s){ q_highestLevelDateE=checkSet(s); }
	public String getQ_highestLevelIsAgree(){ return checkGet(q_highestLevelIsAgree); }
	public void setQ_highestLevelIsAgree(String s){ q_highestLevelIsAgree=checkSet(s); }
	public String getQ_applyDateB(){ return checkGet(q_applyDateB); }
	public void setQ_applyDateB(String s){ q_applyDateB=checkSet(s); }
	public String getQ_applyDateE(){ return checkGet(q_applyDateE); }
	public void setQ_applyDateE(String s){ q_applyDateE=checkSet(s); }
	public String getQ_cityIsAgree(){ return checkGet(q_cityIsAgree); }
	public void setQ_cityIsAgree(String s){ q_cityIsAgree=checkSet(s); }
	public String getQ_isClose(){ return checkGet(q_isClose); }
	public void setQ_isClose(String s){ q_isClose=checkSet(s); }
	
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
	    String SQL = "";
	    String execSQL = "";
	    try{
	    	String[] columns = new String[] {"highestLevelDate","group","term","order","link","propertyNo","propertyName","propertyUnit","material","limitYear","memo"};
	    	if (!"".equals(Common.get(getQ_highestLevelDateB())))
	    		SQL+= " and b.highestLevelDate >= " + util.Common.sqlChar(getQ_highestLevelDateB());
	    	if (!"".equals(Common.get(getQ_highestLevelDateE())))
	    		SQL+= " and b.highestLevelDate <= " + util.Common.sqlChar(getQ_highestLevelDateE());
	    	if (!"".equals(Common.get(getQ_highestLevelIsAgree())))
	    		SQL+= " and b.highestlevelisagree = " + util.Common.sqlChar(getQ_highestLevelIsAgree());
	    	if (!"".equals(Common.get(getQ_applyDateB())))
	    		SQL+= " and b.applyDate >= " + util.Common.sqlChar(getQ_applyDateB());
	    	if (!"".equals(Common.get(getQ_applyDateE())))
	    		SQL+= " and b.applyDate <= " + util.Common.sqlChar(getQ_applyDateE());
	    	if (!"".equals(Common.get(getQ_cityIsAgree())))
	    		SQL+= " and b.cityIsAgree = " + util.Common.sqlChar(getQ_cityIsAgree());
	    	if (!"".equals(Common.get(getQ_isClose())))
	    		SQL+= " and b.isClose = " + util.Common.sqlChar(getQ_isClose());
	    	execSQL=" (select '' as groupNo, '' as termNo, '' as orderNo, '' as linkNo, " +
					" (substr(a.propertyNo,1,7)||'-'||substr(a.propertyNo,8)) as propertyNo, " +
					" '　　　　'||a.propertyName as propertyName, " +
					" a.editKind, a.propertyUnit, a.material, " +
					" a.limitYear, b.highestLevelDoc, b.highestLevelDate, " +
					" decode(a.editKind,'N','新增','U',a.memo,'D','刪除','') as memo, " +
					" substr(a.propertyNo,1,7)||'E' as propertyNo1 " +
					" from SYSPK_ApplyProperty a, SYSPK_Apply b " +
					" where a.applyNo=b.applyNo" + SQL +
					")";
	    	execSQL += "union(" +
	    				" select substr(a.propertyNo,1,1) as groupNo, " +
	    				" '' as termNo, '' as orderNo, '' as linkNo, " +
	    				" '' as propertyNo, " +
	    				" c.propertyName, " +
	    				" '' as editKind, '' as propertyUnit, '' as material, " + 
	    				" '' as limitYear, '' as highestLevelDoc, '' as highestLevelDate, " +
	    				" '' as memo, " +
	    				" substr(a.propertyNo,1,7)||'A' as propertyNo1 " +
	    				" from SYSPK_ApplyProperty a, SYSPK_Apply b, Syspk_Propertycode c " +
	    				" where a.applyNo=b.applyNo " +
	    				" and substr(a.propertyNo,1,1)=c.propertyNo" + SQL +
	    				")";
	    	execSQL += "union(" +
						" select '' as groupNo, " +
						" substr(a.propertyNo,2,2)||'　' as termNo, '' as orderNo, '' as linkNo, " +
						" '' as propertyNo, " +
						" '　'||c.propertyName as propertyName, " +
						" '' as editKind, '' as propertyUnit, '' as material, " + 
						" '' as limitYear, '' as highestLevelDoc, '' as highestLevelDate, " +
						" '' as memo, " +
						" substr(a.propertyNo,1,7)||'B' as propertyNo1 " +
						" from SYSPK_ApplyProperty a, SYSPK_Apply b, Syspk_Propertycode c " +
						" where a.applyNo=b.applyNo " +
						" and substr(a.propertyNo,1,3)=c.propertyNo" + SQL +
						")";
	    	execSQL += "union(" +
						" select '' as groupNo, " +
						" '' as termNo, substr(a.propertyNo,4,2)||'　' as orderNo, '' as linkNo, " +
						" '' as propertyNo, " +
						" '　　'||c.propertyName as propertyName, " +
						" '' as editKind, '' as propertyUnit, '' as material, " + 
						" '' as limitYear, '' as highestLevelDoc, '' as highestLevelDate, " +
						" '' as memo, " +
						" substr(a.propertyNo,1,7)||'C' as propertyNo1 " +
						" from SYSPK_ApplyProperty a, SYSPK_Apply b, Syspk_Propertycode c " +
						" where a.applyNo=b.applyNo " +
						" and substr(a.propertyNo,1,5)=c.propertyNo" + SQL +
						")";
	    	execSQL += "union(" +
						" select '' as groupNo, " +
						" '' as termNo, '' as orderNo, substr(a.propertyNo,6,2)||'　' as linkNo, " +
						" '' as propertyNo, " +
						" '　　　'||c.propertyName as propertyName, " +
						" '' as editKind, '' as propertyUnit, '' as material, " + 
						" '' as limitYear, '' as highestLevelDoc, '' as highestLevelDate, " +
						" '' as memo, " +
						" substr(a.propertyNo,1,7)||'D' as propertyNo1 " +
						" from SYSPK_ApplyProperty a, SYSPK_Apply b, Syspk_Propertycode c " +
						" where a.applyNo=b.applyNo " +
						" and substr(a.propertyNo,1,7)=c.propertyNo" + SQL +
						")";	    	
	    	execSQL+=" order by propertyNo1, propertyNo";
	    	//System.out.println(execSQL);
	        ResultSet rs = db.querySQL(execSQL);
	        Vector rowData = new Vector();
	        while(rs.next()){
	        	Object[] data = new Object[11];
	        	data[0] = "行政院回文日期"+Common.MaskDate(q_highestLevelDateB)+"~"+Common.MaskDate(q_highestLevelDateE);
	        	data[1] = Common.get(rs.getString("groupNo"));
	        	data[2] = Common.get(rs.getString("termNo"));
	        	data[3] = Common.get(rs.getString("orderNo"));
	        	data[4] = Common.get(rs.getString("linkNo"));
	        	data[5] = Common.get(rs.getString("propertyNo"));
	        	data[6] = Common.get(rs.getString("propertyName"));
	        	data[7] = Common.get(rs.getString("propertyUnit"));
	        	data[8] = Common.get(rs.getString("material"));
	        	data[9] = Common.get(rs.getString("limitYear"));
	        	data[10] = Common.get(rs.getString("memo"));
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