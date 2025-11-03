/*
*<br>程式目的：他項權利證明書備查簿查詢檔 
*<br>程式代號：untrt014r
*<br>撰寫日期：94/11/23
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

public class UNTRT014R extends SuperBean{

    String q_drawState;
    String q_enterOrg;
    String q_ownership;
    String q_propertyNoS;
    String q_propertyNoE;
    String q_propertyNoSName;
    String q_propertyNoEName;
    String q_serialNoS;
    String q_serialNoE;
    String q_enterDateS;
    String q_enterDateE;
    String q_dataState;
    String q_verify;
    String q_proofVerify;
    String q_propertyKind;
    String q_fundType;
    String q_drawDateS;
    String q_drawDateE;
    String q_drawName;
    String q_drawCause;

    public String getQ_drawState(){ return checkGet(q_drawState); }
    public void setQ_drawState(String s){ q_drawState=checkSet(s); }
    public String getQ_enterOrg(){ return checkGet(q_enterOrg); }
    public void setQ_enterOrg(String s){ q_enterOrg=checkSet(s); }
    public String getQ_ownership(){ return checkGet(q_ownership); }
    public void setQ_ownership(String s){ q_ownership=checkSet(s); }
    public String getQ_propertyNoS(){ return checkGet(q_propertyNoS); }
    public void setQ_propertyNoS(String s){ q_propertyNoS=checkSet(s); }
    public String getQ_propertyNoE(){ return checkGet(q_propertyNoE); }
    public void setQ_propertyNoE(String s){ q_propertyNoE=checkSet(s); }
    public String getQ_propertyNoSName(){ return checkGet(q_propertyNoSName); }
    public void setQ_propertyNoSName(String s){ q_propertyNoSName=checkSet(s); }
    public String getQ_propertyNoEName(){ return checkGet(q_propertyNoEName); }
    public void setQ_propertyNoEName(String s){ q_propertyNoEName=checkSet(s); }
    public String getQ_serialNoS(){ return checkGet(q_serialNoS); }
    public void setQ_serialNoS(String s){ q_serialNoS=checkSet(s); }
    public String getQ_serialNoE(){ return checkGet(q_serialNoE); }
    public void setQ_serialNoE(String s){ q_serialNoE=checkSet(s); }
    public String getQ_enterDateS(){ return checkGet(q_enterDateS); }
    public void setQ_enterDateS(String s){ q_enterDateS=checkSet(s); }
    public String getQ_enterDateE(){ return checkGet(q_enterDateE); }
    public void setQ_enterDateE(String s){ q_enterDateE=checkSet(s); }
    public String getQ_verify(){ return checkGet(q_verify); }
    public void setQ_verify(String s){ q_verify=checkSet(s); }
    public String getQ_dataState(){ return checkGet(q_dataState); }
    public void setQ_dataState(String s){ q_dataState=checkSet(s); }
    public String getQ_proofVerify(){ return checkGet(q_proofVerify); }
    public void setQ_proofVerify(String s){ q_proofVerify=checkSet(s); }
    public String getQ_propertyKind(){ return checkGet(q_propertyKind); }
    public void setQ_propertyKind(String s){ q_propertyKind=checkSet(s); }
    public String getQ_fundType(){ return checkGet(q_fundType); }
    public void setQ_fundType(String s){ q_fundType=checkSet(s); }
    public String getQ_drawDateS(){ return checkGet(q_drawDateS); }
    public void setQ_drawDateS(String s){ q_drawDateS=checkSet(s); }
    public String getQ_drawDateE(){ return checkGet(q_drawDateE); }
    public void setQ_drawDateE(String s){ q_drawDateE=checkSet(s); }
    public String getQ_drawName(){ return checkGet(q_drawName); }
    public void setQ_drawName(String s){ q_drawName=checkSet(s); }
    public String getQ_drawCause(){ return checkGet(q_drawCause); }
    public void setQ_drawCause(String s){ q_drawCause=checkSet(s); }

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
	//UNTRT014R obj = this;
    DefaultTableModel model = new javax.swing.table.DefaultTableModel();
    Database db = new Database();
    try{
    	String[] columns = new String[] {"ENTERORGNAME","OWNERSHIP","OWNERSHIPNAME","DATE","PROPERTYNAME","PROOFDOC1","REGISTERDATE","ENTERDATE","DRAWDATE","DRAWNAME","DRAWCAUSE","RETURNDATE"};

    	String execSQL="select c.ORGANANAME as enterOrgName, a.ownership, decode(a.ownership,'1','市有','2','國有','') ownershipName, " +
    					" b.propertyName, a.proofDoc1, a.registerDate, a.enterDate, " +
    					" d.drawDate, d.drawName, e.codeName as drawCause, d.returnDate " +
    					" from UNTRT_AddProof a,SYSPK_PropertyCode b, SYSCA_Organ c, UNTRT_DrawProof d, SYSCA_Code e "+
						" where 1=1 and a.dataState='1' and a.nonProof='Y' " +
						" and a.propertyNo = b.propertyNo and a.enterOrg = c.organID " +
						" and a.enterOrg=d.enterOrg(+) and a.ownership=d.ownership(+) and a.propertyNo=d.propertyNo(+) and a.serialNo=d.serialNo(+) " +
						" and d.drawCause=e.codeID(+) and e.codeKindID(+) = 'DCA'"+
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
		if (!"".equals(getQ_serialNoS()))
		    execSQL+=" and a.serialNo >= " + Common.sqlChar(getQ_serialNoS());		
		if (!"".equals(getQ_serialNoE()))
		    execSQL+=" and a.serialNo <=" + Common.sqlChar(getQ_serialNoE());			
		if (!"".equals(getQ_enterDateS()))
		    execSQL+=" and a.enterDate >= " + Common.sqlChar(new Datetime().changeTaiwanYYMMDD(getQ_enterDateS(),"2")) ;
		if (!"".equals(getQ_enterDateE()))
		    execSQL+=" and a.enterDate <= " + Common.sqlChar(new Datetime().changeTaiwanYYMMDD(getQ_enterDateE(),"2")) ;   	    
		if (!"".equals(getQ_verify()))
		    execSQL+=" and a.verify = " + Common.sqlChar(getQ_verify()) ;
		if (!"".equals(getQ_proofVerify()))
		    execSQL+=" and a.proofVerify = " + Common.sqlChar(getQ_proofVerify()) ;
		if (!"".equals(getQ_propertyKind()))
			execSQL +=" and a.propertyKind = " + Common.sqlChar(getQ_propertyKind()) ;
		if (!"".equals(getQ_fundType()))
			execSQL +=" and a.fundType = " + Common.sqlChar(getQ_fundType()) ;
		if (!"".equals(getQ_drawDateS()))
		    execSQL+=" and d.drawDate >= " + Common.sqlChar(getQ_drawDateS());		
		if (!"".equals(getQ_drawDateE()))
		    execSQL+=" and d.drawDate <= " + Common.sqlChar(getQ_drawDateE());   	    
		if (!"".equals(getQ_drawName()))
		    execSQL+=" and d.drawName like '%" + getQ_drawName() + "%'";
		if (!"".equals(getQ_drawCause()))
		    execSQL+=" and d.drawCause=" + Common.sqlChar(getQ_drawCause());

    	execSQL+=" order by a.ownership, a.propertyNo, a.serialNo";
        ResultSet rs = db.querySQL(execSQL);
        Vector rowData = new Vector();
        //int i;
        while(rs.next()){
        	Object[] data = new Object[12];
            data[0] = rs.getString("enterOrgName");
            data[1] = rs.getString("ownership");
            data[2] = rs.getString("ownershipName");
            data[3] = Datetime.getYYY()+"/"+Datetime.getMM()+"/"+Datetime.getYYYMMDD().substring(5,7);
            data[4] = rs.getString("propertyName");
            if(rs.getString("proofDoc1")==null){
                data[5] = "免繕狀";
            }else{
                data[5] = rs.getString("proofDoc1");
            }
            if(rs.getString("registerDate")!=null){
                data[6] = rs.getString("registerDate").substring(0,3)+"/"+rs.getString("registerDate").substring(3,5)+"/"+rs.getString("registerDate").substring(5,7);
            }else{
                data[6] = rs.getString("registerDate");
            }
            if(rs.getString("enterDate")!=null){
                data[7] = rs.getString("enterDate").substring(0,3)+"/"+rs.getString("enterDate").substring(3,5)+"/"+rs.getString("enterDate").substring(5,7);
            }else{
                data[7] = rs.getString("enterDate");
            }
            if(rs.getString("drawDate")!=null){
                data[8] = rs.getString("drawDate").substring(0,3)+"/"+rs.getString("drawDate").substring(3,5)+"/"+rs.getString("drawDate").substring(5,7);
            }else{
                data[8] = rs.getString("drawDate");
            }
            data[9] = rs.getString("drawName");
            data[10] = rs.getString("drawCause");
            data[11] = Common.MaskDate(rs.getString("returnDate"));
            //for(i=0;i<12;i++)if(data[i]==null)data[i]="";
            if(getQ_drawState().equals("1")){
                if(rs.getString("drawDate")!=null){
                    rowData.addElement(data);
                }
            }else if(getQ_drawState().equals("2")){
                if(rs.getString("drawDate")==null){
                    rowData.addElement(data);
                }
            }else{
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

}
