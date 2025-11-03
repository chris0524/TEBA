/*
*<br>程式目的：財產編號批次修正作業 
*<br>程式代號：untup004f
*<br>撰寫日期：95/02/14
*<br>程式作者：stephen
*<br>--------------------------------------------------------
*<br>修改作者　　修改日期　　　修改目的
*<br>--------------------------------------------------------
*/

package unt.up;

import java.sql.ResultSet;
import java.util.Vector;

import javax.swing.table.DefaultTableModel;

import util.*;

public class UNTUP004F extends SuperBean{
		
	String q_enterOrg;
	String q_enterOrgName;
	String oldPropertyNo;
	String oldPropertyNoName;

	String newPropertyNo;
	String newPropertyNoName;	

	String isOrganManager;
	String isAdminManager;
	String organID;			
	
	public String getQ_enterOrg(){ return checkGet(q_enterOrg); }
	public void setQ_enterOrg(String s){ q_enterOrg=checkSet(s); }
	public String getQ_enterOrgName(){ return checkGet(q_enterOrgName); }
	public void setQ_enterOrgName(String s){ q_enterOrgName=checkSet(s); }
    public String getNewPropertyNo() {
        return checkGet(newPropertyNo);
    }    
    public void setNewPropertyNo(String newPropertyNo) {
        this.newPropertyNo = checkSet(newPropertyNo);
    }
    public String getOldPropertyNo() {
        return checkGet(oldPropertyNo);
    }
    public void setOldPropertyNo(String oldPropertyNo) {
        this.oldPropertyNo = checkSet(oldPropertyNo);
    }    
    public String getOldPropertyNoName() {
        return checkGet(oldPropertyNoName);
    }
    public void setOldPropertyNoName(String oldPropertyNoName) {
        this.oldPropertyNoName = checkSet(oldPropertyNoName);
    }
    public String getNewPropertyNoName() {
        return checkGet(newPropertyNoName);
    }
    public void setNewPropertyNoName(String newPropertyNoName) {
        this.newPropertyNoName = checkSet(newPropertyNoName);
    }    
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
    	
    	String[] columns = new String[] {"SIGNDESC","SIGNNO3","SIGNNO4","SIGNNO5","AREA","HOLDRANGE","HOLDAREA","VALUEUNIT","HOLDAMOUNT","DATE"};
    	
    	String execSQL="select a.signNo, substr(b.signDesc,4,3) as signDesc, substr(b.signDesc,7) as signDesc1, a.area, a.holdnumerator, a.holddenominator, a.valueunit " +
		               " from PUBAT_Land a, SYSCA_Sign b" +
					   " where substr(a.signNo,1,7)=b.signNo " +
					   " and a.ownership='1' " +
					   "";	
    	
    	/*if (!"".equals(getQ_signNo1()))
			q_signNo=getQ_signNo1().substring(0,1)+"______";
		if (!"".equals(getQ_signNo2()))
			q_signNo=getQ_signNo2().substring(0,3)+"____";			
		if (!"".equals(getQ_signNo3())){
			if (getQ_signNo3().length()==4){
				q_signNo="E__" + getQ_signNo3();
			}else{
				q_signNo=getQ_signNo3();
			}	
		}
		if (!"".equals(getQ_signNo4())){
			setQ_signNo4(Common.formatFrontZero(getQ_signNo4(),4));
			setQ_signNo5(Common.formatFrontZero(getQ_signNo5(),4));	
			if ("".equals(q_signNo)){
				q_signNo="_______"+getQ_signNo4()+getQ_signNo5();
			}else{
				q_signNo=q_signNo+getQ_signNo4()+getQ_signNo5();				
			}
		}
		
		if (!"".equals(q_signNo) && q_signNo != null) {
			execSQL += " and a.signNo like '" + q_signNo + "%'" ;
		}*/
    	
        ResultSet rs = db.querySQL(execSQL);
        Vector rowData = new Vector();
        int i;
        int intHoldArea;
        int intHoldAmount;
        String sdp ="" ;
        String sdpp ="";
        String dotpos;
        String dotposs;
        float holdArea = 0;
        float holdAmount = 0;
        String strHoldArea;
        String strHoldAmount;
        while(rs.next()){
        	/*holdArea = rs.getFloat("area")*(rs.getFloat("holdNumerator")/rs.getFloat("holdDenominator"));
        	strHoldArea = Float.toString(holdArea);
        	if ( strHoldArea.indexOf(".") == -1 ){
				Math.round(holdArea);
				strHoldArea = holdArea+"."+sdp; 
				holdArea = Float.parseFloat(strHoldArea);
			}else{
				dotpos = strHoldArea.substring(strHoldArea.indexOf(".")+1);
				if(dotpos.length() > 2)
					dotpos = dotpos.substring(0,2) + "." + dotpos.substring(2);
					intHoldArea = Math.round(Float.parseFloat(dotpos));
					strHoldArea = strHoldArea.substring(0,strHoldArea.indexOf(".")) + "."+ intHoldArea;
					holdArea = Float.parseFloat(strHoldArea);
			}
        	holdAmount = rs.getFloat("valueunit")*rs.getFloat("area")*(rs.getFloat("holdNumerator"))/(rs.getFloat("holdDenominator"));
        	strHoldAmount = Float.toString(holdAmount);
        	if ( strHoldAmount.indexOf(".") == -1 ){
				Math.round(holdAmount);
				strHoldAmount = holdAmount+"."+sdpp; 
				holdAmount = Float.parseFloat(strHoldAmount);
			}else{
				dotposs = strHoldAmount.substring(strHoldAmount.indexOf(".")+1);
				if(dotposs.length() > 2)
					dotposs = dotposs.substring(0,2) + "." + dotposs.substring(2);
					intHoldAmount = Math.round(Float.parseFloat(dotposs));
					strHoldAmount = strHoldAmount.substring(0,strHoldAmount.indexOf(".")) + "."+ intHoldAmount;
					holdAmount = Float.parseFloat(strHoldAmount);
			}
        	Object[] data = new Object[10];
            data[0] = rs.getString("signDesc");
            data[1] = rs.getString("signDesc1");
            data[2] = "　"+rs.getString("signNo").substring(7,11);
            data[3] = "-"+rs.getString("signNo").substring(11,15);
            data[4] = Common.areaFormat(rs.getFloat("area")+"")+"　";
            data[5] = rs.getString("holdNumerator")+"/"+rs.getString("holdDenominator");
            data[6] = Common.areaFormat(holdArea+"")+"　";
            data[7] = new Float(rs.getFloat("valueunit"));
			data[8] = Common.areaFormat(holdAmount+"")+"　";
			data[9] = Datetime.getYYYMMDD().substring(0,3)+" / "+Datetime.getYYYMMDD().substring(3,5)+" / "+Datetime.getYYYMMDD().substring(5,7);
            for(i=0;i<10;i++)if(data[i]==null)data[i]="";
            rowData.addElement(data);*/
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
