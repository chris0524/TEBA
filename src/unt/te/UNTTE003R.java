/*
*<br>程式目的：繳納清冊 
*<br>程式代號：untte003r
*<br>撰寫日期：94/11/24
*<br>程式作者：unique.chiang
*<br>--------------------------------------------------------
*<br>修改作者　　修改日期　　　修改目的
*<br>--------------------------------------------------------
*/

package unt.te;

import java.sql.ResultSet;
import java.util.Vector;

import javax.swing.table.DefaultTableModel;
import net.sf.jasperreports.engine.data.JRTableModelDataSource;

import util.*;

public class UNTTE003R extends SuperBean{
	String q_enterOrg;
	String q_enterOrgName;

	String taxYear;
	String taxKind;
	String isOrganManager;
	String isAdminManager;
	String organID;       	
	
	public String getTaxYear() {
		return checkGet(taxYear);
	}
	public void setTaxYear(String taxYear) {
		this.taxYear = checkSet(taxYear);
	}
	
	public String getTaxKind() {
		return checkGet(taxKind);
	}
	public void setTaxKind(String taxKind) {
		this.taxKind = checkSet(taxKind);
	}
	
	public String getOrganID() { return checkGet(organID); }
	public void setOrganID(String s) { organID = checkSet(s); }
	public String getIsOrganManager() { return checkGet(isOrganManager); }
	public void setIsOrganManager(String s) { isOrganManager = checkSet(s); }
	public String getIsAdminManager() { return checkGet(isAdminManager); }
	public void setIsAdminManager(String s) { isAdminManager = checkSet(s); }
	public String getQ_enterOrg(){ return checkGet(q_enterOrg); }
	public void setQ_enterOrg(String s){ q_enterOrg=checkSet(s); }
	public String getQ_enterOrgName(){ return checkGet(q_enterOrgName); }
	public void setQ_enterOrgName(String s){ q_enterOrgName=checkSet(s); }

public DefaultTableModel getResultModel() throws Exception{
	UNTTE003R obj = this;
    DefaultTableModel model = new javax.swing.table.DefaultTableModel();
    Database db = new Database();
    try{
    	String[] columns = new String[] {"ORGANSNAME","N_SIGNO","T_TAXKIND","REMITAREA","TAXAMOUNT","T_DOOR"};

    	String execSQLLand="SELECT (SELECT ORGANANAME FROM SYSCA_ORGAN B WHERE B.ORGANID=A.ENTERORG) ORGANSNAME, " + "\n" +
		                   "       (SELECT E.SIGNDESC||SUBSTR(SUBSTR(C.SIGNNO,8),1,4)||'-'||SUBSTR(SUBSTR(C.SIGNNO,8),5,4)||'地號' N_SNO " + "\n" +
						   "		  FROM UNTLA_LAND C, SYSCA_SIGN E " + "\n" +
						   "		 WHERE C.ENTERORG=A.ENTERORG " + "\n" +
						   "		   AND C.PROPERTYNO=A.PROPERTYNO " + "\n" +
						   "		   AND C.SERIALNO=A.SERIALNO " + "\n" +
						   "		   AND C.OWNERSHIP=A.OWNERSHIP " + "\n" +
						   "		   AND SUBSTR(C.SIGNNO, 1, 7)=E.SIGNNO ) N_SIGNO, " + "\n" +
						   "	       (SELECT CODENAME " + "\n" +
						   "	          FROM SYSCA_CODE G " + "\n" +
						   "	         WHERE G.CODEKINDID='TKD' " + "\n" +
						   "	           AND G.CODEID=A.TAXKIND) T_TAXKIND, " + "\n" +
						   "	       A.REMITAREA,A.TAXAMOUNT,'' T_DOOR, 'A' T_LB, A.ENTERORG " + "\n" +
						   "	  FROM UNTLA_TAX A " + "\n" + 
						   "     WHERE 1=1 " + "\n" ;

    	String execSQLBuild="SELECT (SELECT ORGANANAME FROM SYSCA_ORGAN I WHERE I.ORGANID=H.ENTERORG) ORGANSNAME, " + "\n" +
		                    "       (SELECT F.SIGNDESC||SUBSTR(SUBSTR(D.SIGNNO,8),1,5)||'-'||SUBSTR(SUBSTR(D.SIGNNO,8),6,3)||'建號' N_SNO1 " + "\n" +
							"	       FROM UNTBU_BUILDING D, SYSCA_SIGN F " + "\n" +
							"	      WHERE D.ENTERORG=H.ENTERORG " + "\n" +
							"	        AND D.PROPERTYNO=H.PROPERTYNO " + "\n" +
							"           AND D.SERIALNO=H.SERIALNO " + "\n" +
							"       	AND D.OWNERSHIP=H.OWNERSHIP " + "\n" +
							"           AND SUBSTR(D.SIGNNO, 1, 7)=F.SIGNNO ) N_SIGNO, " + "\n" +
							"       (SELECT CODENAME " + "\n" +
							"          FROM SYSCA_CODE J " + "\n" +
							"         WHERE J.CODEKINDID='TKD' " + "\n" +
							"           AND J.CODEID=H.TAXKIND) T_TAXKIND, " + "\n" +
							"       NULL REMITAREA,H.TAXAMOUNT, " + "\n" +
							"       (SELECT K.ADDRNAME||L.ADDRNAME||M.ADDRNAME||J.DOORPLATE4 DOORPLATE " + "\n" +
							"	       FROM UNTBU_BUILDING J, SYSCA_ADDR K, SYSCA_ADDR L, SYSCA_ADDR M " + "\n" +
							"	      WHERE J.ENTERORG=H.ENTERORG " + "\n" +
							"	        AND J.PROPERTYNO=H.PROPERTYNO " + "\n" +
							"	        AND J.SERIALNO=H.SERIALNO " + "\n" +
							"           AND J.OWNERSHIP=H.OWNERSHIP " + "\n" +
							"      	    AND J.DATASTATE='1' " + "\n" +
							"           AND J.DOORPLATE1=K.ADDRID " + "\n" +
							"           AND K.ADDRKIND='1' " + "\n" +
							"           AND J.DOORPLATE2=L.ADDRID " + "\n" +
							"           AND L.ADDRKIND='2' " + "\n" +
							"	        AND J.DOORPLATE3=M.ADDRID " + "\n" +
							"           AND M.ADDRKIND='3')  T_DOOR, 'B' T_LB, H.ENTERORG " + "\n" +
							"  FROM UNTBU_TAX H " + "\n" +
        					" WHERE 1=1 " + "\n" ;							
		
		String execSQLA = "";					
		String execSQLB = "";		
		if (!"".equals(getQ_enterOrg())) {
			execSQLA +=" AND A.ENTERORG = " + Common.sqlChar(getQ_enterOrg())  + "\n";
			execSQLB +=" AND H.ENTERORG = " + Common.sqlChar(getQ_enterOrg())  + "\n";			
		} else {
			if (!getIsAdminManager().equalsIgnoreCase("Y")) {
				if ("Y".equals(getIsOrganManager())) {					
					execSQLA += " AND A.ENTERORG LIKE '" + getOrganID().substring(0,5) + "%' "  + "\n" ;					
					execSQLB += " AND H.ENTERORG LIKE '" + getOrganID().substring(0,5) + "%' "  + "\n" ;					
				} else {
					execSQLA +=" AND A.ENTERORG = " + Common.sqlChar(getOrganID()) + "\n" ;
					execSQLB +=" AND H.ENTERORG = " + Common.sqlChar(getOrganID()) + "\n" ;					
				}
			}
		}

		if (!"".equals(Common.get(getTaxYear()))) {
			execSQLA +=" AND A.TAXYEAR = " +  util.Common.sqlChar(Common.formatFrontZero(getTaxYear(),3)) + "\n";
			execSQLB +=" AND H.TAXYEAR = " + util.Common.sqlChar(Common.formatFrontZero(getTaxYear(),3)) + "\n";			
	    }		
		
		if (!"".equals(Common.get(getTaxKind()))) {
			execSQLA +=" AND A.TAXKIND = " + util.Common.sqlChar(getTaxKind()) + "\n";
			execSQLB +=" AND H.TAXKIND = " + util.Common.sqlChar(getTaxKind()) + "\n";			
	    }		
    	
    	String execSQL = execSQLLand + execSQLA + "UNION"  + "\n" +
		                 execSQLBuild + execSQLB + " ORDER BY ENTERORG,T_LB";

        //System.out.println(execSQL);
        
        ResultSet rs = db.querySQL(execSQL);
        Vector rowData = new Vector();
        String sREMITAREA = "", sTAXAMOUNT = "";
        int i;
        while(rs.next()){
        	Object[] data = new Object[6];
            data[0] = rs.getString("ORGANSNAME");
            data[1] = rs.getString("N_SIGNO");
            data[2] = rs.getString("T_TAXKIND");
            sREMITAREA = rs.getString("REMITAREA");
            if (sREMITAREA==null) {
            	data[3] = null;
            }
            else {
            	data[3] = Common.areaFormat(sREMITAREA) + "　";
            }

            data[4] =  Common.valueFormat(rs.getString("TAXAMOUNT"));
            
            data[5] = rs.getString("T_DOOR");
            /*for(i=0;i<data.length;i++) {
            	if(data[i]==null)data[i]="";
            }*/
            //for (i=0;i<12;i++)
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

public DefaultTableModel getSubModel(String caseCode) throws Exception{
	UNTTE003R obj = this;
	DefaultTableModel model = new javax.swing.table.DefaultTableModel();
    /*Database db = new Database();
    try{
        String[] columns = new String[] { "ORIGINALUA", "ORIGINALSHEET","ORIGINALAMOUNT","ORIGINALUV","ORIGINALBV","ORIGINALPROOFS","ORIGINALPROOFE"};
    	String execSQL="select nvl(a.originalUA,0) as originalUA, nvl(a.originalSheet,0) as originalSheet, nvl(a.originalAmount,0) as originalAmount, nvl(a.originalUV,0) as originalUV, nvl(a.originalBV,0) as originalBV, nvl(a.originalProofS,'') as originalProofS, nvl(a.originalProofE,'') as originalProofE " +
						" from UNTVP_Share a "+
						" where 1=1 "+
						" and a.enterOrg=" + util.Common.sqlChar(obj.getShareEnterOrg())+
						" and a.ownership=" + util.Common.sqlChar(obj.getShareOwnership())+
						" and a.propertyNo=" + util.Common.sqlChar(obj.getSharePropertyNo())+
						" and a.serialNo=" + util.Common.sqlChar(obj.getShareSerialNo())+
    					" and a.enterDate=" + util.Common.sqlChar(obj.getEnterDate());
    	execSQL+=" order by a.serialNo1";
    	ResultSet rs = db.querySQL(execSQL);
    	Vector rowData = new Vector();
    	int i;
    	while(rs.next()){
    		Object[] data = new Object[7];
    		data[0] = new Double(Double.parseDouble(rs.getString("originalUA")));
    		data[1] = new Double(Double.parseDouble(rs.getString("originalSheet")));
    		data[2] = new Double(Double.parseDouble(rs.getString("originalAmount")));
    		data[3] = new Double(Double.parseDouble(rs.getString("originalUV")));
    		data[4] = new Double(Double.parseDouble(rs.getString("originalBV")));
    		data[5] = rs.getString("originalProofS");
    		data[6] = rs.getString("originalProofE");
    		for(i=0;i<7;i++)if(data[i]==null)data[i]="";
    		rowData.addElement(data);
    	}
    	
        Object[][] data = new Object[0][0];
        data = (Object[][])rowData.toArray(data);
        model.setDataVector(data, columns);
    }catch(Exception x){
    	x.printStackTrace();
    }finally{
        db.closeAll();
    }*/
    
    return model;

}

}
