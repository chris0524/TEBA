

/*
*<br>程式目的：土地增減單月報表 
*<br>程式代號：UNTGR003R
*<br>撰寫日期：95/02/13
*<br>程式作者：
*<br>--------------------------------------------------------
*<br>修改作者　　修改日期　　　修改目的
*<br>--------------------------------------------------------
*/

package unt.gr;

import java.sql.ResultSet;
import java.util.Vector;

import javax.swing.table.DefaultTableModel;

import util.*;

public class UNTGR003R extends SuperBean{
		
	String q_enterOrg;
	String q_enterOrgName;
	String q_ownership;
	String q_reportYM;
	String q_verify;		
	
	public String getQ_enterOrg(){ return checkGet(q_enterOrg); }
	public void setQ_enterOrg(String s){ q_enterOrg=checkSet(s); }
	public String getQ_enterOrgName(){ return checkGet(q_enterOrgName); }
	public void setQ_enterOrgName(String s){ q_enterOrgName=checkSet(s); }
	public String getQ_ownership(){ return checkGet(q_ownership); }
	public void setQ_ownership(String s){ q_ownership=checkSet(s); }
	public String getQ_reportYM(){ return checkGet(q_reportYM); }
	public void setQ_reportYM(String s){ q_reportYM=checkSet(s); }
	public String getQ_verify(){ return checkGet(q_verify); }
	public void setQ_verify(String s){ q_verify=checkSet(s); }
	
	String isOrganManager;
	String isAdminManager;
	String organID;    
	public String getOrganID() { return checkGet(organID); }
	public void setOrganID(String s) { organID = checkSet(s); }
	public String getIsOrganManager() { return checkGet(isOrganManager); }
	public void setIsOrganManager(String s) { isOrganManager = checkSet(s); }
	public String getIsAdminManager() { return checkGet(isAdminManager); }
	public void setIsAdminManager(String s) { isAdminManager = checkSet(s); }

//查詢條件
protected String querySQL(String date){
	String querySQL="";
		if (!"".equals(getQ_enterOrg())) {
			querySQL +=" and a.enterOrg = " + Common.sqlChar(getQ_enterOrg()) ;
		} else {
			if (!getIsAdminManager().equalsIgnoreCase("Y")) {
				if ("Y".equals(getIsOrganManager())) {					
					querySQL += " and a.enterOrg like '" + getOrganID().substring(0,5) + "%' ";					
				} else {
					querySQL +=" and a.enterOrg = " + Common.sqlChar(getOrganID()) ;
				}
			}
		}
		if (!"".equals(Common.get(getQ_ownership())))
			querySQL +=" and a.ownership = " + util.Common.sqlChar(getQ_ownership());
		if (!"".equals(Common.get(getQ_verify())))
			querySQL +=" and a.verify = " + util.Common.sqlChar(getQ_verify());
		if (!"".equals(Common.get(getQ_reportYM())))
			querySQL +=" and substr(a." + date + ",1,5) = " + util.Common.sqlChar(getQ_reportYM());
	return querySQL;
}

public DefaultTableModel getResultModel() throws Exception{
    DefaultTableModel model = new javax.swing.table.DefaultTableModel();
    Database db = new Database();
    try{
    	String[] columns = new String[] {"REPORTYM","PROOF","SIGNNONAME","CAUSENAME","ENTERDATE","ENTERORGNAME","printdate","magName"};
    	String execSQL="(" +
    				" select a.proofDoc||'字第'||a.proofNo||'號' proof,"+
    			    " b.signNo,"+
    			    " (select signdesc from sysca_sign c where c.signNo=substr(b.signNo,1,7)) ||substr(b.signNo,8,4)||'-'||substr(b.signNo,12,4) || '地號' signNoName,"+
    			    " decode(cause,'499',cause1,(select codename from sysca_code d where d.codekindid='CAA' and d.codeid=b.cause)) causeName,"+
    			    " a.enterDate enterDate"+
    			    " from UNTLA_AddProof a,UNTLA_Land b where 1=1 "; 
    		execSQL += querySQL("enterDate");
    		execSQL +=" and a.enterOrg=b.enterOrg"+
    			    " and a.ownership=b.ownership"+
    			    " and a.caseNo=b.caseNo"+
    			    " )"+
    			    " union"+
    			    " ("+
    			    " select a.proofDoc||'字第'||a.proofNo||'號' proof,"+
    			    " b.signNo,"+
    			    " (select signdesc from sysca_sign c where c.signNo=substr(b.signNo,1,7)) ||substr(b.signNo,8,4)||'-'||substr(b.signNo,12,4) || '地號' signNoName,"+
    			    " decode(cause,'499',cause1,(select codename from sysca_code d where d.codekindid='CAA' and d.codeid=b.cause)) causeName,"+
    			    " a.reduceDate enterDate"+
    			    " from UNTLA_ReduceProof a,UNTLA_ReduceDetail b where 1=1";
    		execSQL += querySQL("reduceDate");
    		execSQL +=" and a.enterOrg=b.enterOrg"+
					" and a.ownership=b.ownership"+
					" and a.caseNo=b.caseNo"+
					" )"+
					" order by enterDate,signNo";    	
        ResultSet rs = db.querySQL(execSQL);
        Vector rowData = new Vector();
        //int i;
        while(rs.next()){
        	Object[] data = new Object[8];
            data[0] = Common.MaskCDate(q_reportYM);
            data[1] = rs.getString("proof");
            data[2] = rs.getString("signNoName");
            data[3] = rs.getString("causeName");
            data[4] = Common.MaskDate(rs.getString("enterDate"));
            data[5] = Common.getORGANANAME(getQ_enterOrg());
            data[6] = "列印日期："+Common.MaskDate(Datetime.getYYYMMDD());
            data[7] = "管理者：" + getQ_enterOrgName();
            
            //for(i=0;i<6;i++)if(data[i]==null)data[i]="";
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
