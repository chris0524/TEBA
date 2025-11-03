/*
*<br>程式目的：有價證券增減值單查詢檔 
*<br>程式代號：untvp009r
*<br>撰寫日期：94/11/10
*<br>程式作者：Cherry
*<br>--------------------------------------------------------
*<br>修改作者　　修改日期　　　修改目的
*<br>--------------------------------------------------------
*/

package unt.vp;

import java.sql.ResultSet;
import java.util.Vector;

import javax.swing.table.DefaultTableModel;
import net.sf.jasperreports.engine.data.JRTableModelDataSource;

import util.*;

public class UNTVP009R extends UNTVP006Q{

	String enterOrg;
	String ownership;
	String caseNoS;
	String caseNoE;
	String writeDateS;
	String writeDateE;
	String proofDoc;
	String proofNoS;
	String proofNoE;

	String q_enterOrg;
	String q_enterOrgName;
	String q_ownership;
	String q_caseNoS;
	String q_caseNoE;
	String q_writeDateS;
	String q_writeDateE;
	String q_proofYear;
	String q_proofDoc;
	String q_proofNoS;
	String q_proofNoE;
	String q_kind;

	String shareEnterOrg;
	String shareOwnership;
	String sharePropertyNo;
	String shareSerialNo;
	String shareCaseNo;
	
	public String getShareEnterOrg(){ return checkGet(shareEnterOrg); }
	public void setShareEnterOrg(String s){ shareEnterOrg=checkSet(s); }
	public String getShareOwnership(){ return checkGet(shareOwnership); }
	public void setShareOwnership(String s){ shareOwnership=checkSet(s); }
	public String getSharePropertyNo(){ return checkGet(sharePropertyNo); }
	public void setSharePropertyNo(String s){ sharePropertyNo=checkSet(s); }
	public String getShareSerialNo(){ return checkGet(shareSerialNo); }
	public void setShareSerialNo(String s){ shareSerialNo=checkSet(s); }
	public String getShareCaseNo(){ return checkGet(shareCaseNo); }
	public void setShareCaseNo(String s){ shareCaseNo=checkSet(s); }

	public String getEnterOrg(){ return checkGet(enterOrg); }
	public void setEnterOrg(String s){ enterOrg=checkSet(s); }
	
	public String getOwnership(){ return checkGet(ownership); }
	public void setOwnership(String s){ ownership=checkSet(s); }
	public String getCaseNoS(){ return checkGet(caseNoS); }
	public void setCaseNoS(String s){ caseNoS=checkSet(s); }
	public String getCaseNoE(){ return checkGet(caseNoE); }
	public void setCaseNoE(String s){ caseNoE=checkSet(s); }
	public String getWriteDateS(){ return checkGet(writeDateS); }
	public void setWriteDateS(String s){ writeDateS=checkSet(s); }
	public String getWriteDateE(){ return checkGet(writeDateE); }
	public void setWriteDateE(String s){ writeDateE=checkSet(s); }
	public String getQ_proofYear() {return checkGet(q_proofYear);}
	public void setQ_proofYear(String qProofYear) {q_proofYear = checkSet(qProofYear);}
	public String getProofDoc(){ return checkGet(proofDoc); }
	public void setProofDoc(String s){ proofDoc=checkSet(s); }
	public String getProofNoS(){ return checkGet(proofNoS); }
	public void setProofNoS(String s){ proofNoS=checkSet(s); }
	public String getProofNoE(){ return checkGet(proofNoE); }
	public void setProofNoE(String s){ proofNoE=checkSet(s); }

	public String getQ_enterOrg(){ return checkGet(q_enterOrg); }
	public void setQ_enterOrg(String s){ q_enterOrg=checkSet(s); }
	public String getQ_enterOrgName(){ return checkGet(q_enterOrgName); }
	public void setQ_enterOrgName(String s){ q_enterOrgName=checkSet(s); }
	public String getQ_ownership(){ return checkGet(q_ownership); }
	public void setQ_ownership(String s){ q_ownership=checkSet(s); }
	public String getQ_caseNoS(){ return checkGet(q_caseNoS); }
	public void setQ_caseNoS(String s){ q_caseNoS=checkSet(s); }
	public String getQ_caseNoE(){ return checkGet(q_caseNoE); }
	public void setQ_caseNoE(String s){ q_caseNoE=checkSet(s); }
	public String getQ_writeDateS(){ return checkGet(q_writeDateS); }
	public void setQ_writeDateS(String s){ q_writeDateS=checkSet(s); }
	public String getQ_writeDateE(){ return checkGet(q_writeDateE); }
	public void setQ_writeDateE(String s){ q_writeDateE=checkSet(s); }
	public String getQ_proofDoc(){ return checkGet(q_proofDoc); }
	public void setQ_proofDoc(String s){ q_proofDoc=checkSet(s); }
	public String getQ_proofNoS(){ return checkGet(q_proofNoS); }
	public void setQ_proofNoS(String s){ q_proofNoS=checkSet(s); }
	public String getQ_proofNoE(){ return checkGet(q_proofNoE); }
	public void setQ_proofNoE(String s){ q_proofNoE=checkSet(s); }
	public String getQ_kind(){ return checkGet(q_kind); }
	public void setQ_kind(String s){ q_kind=checkSet(s); }
	
public DefaultTableModel getResultModel() throws Exception{
	UNTVP009R obj = this;
    DefaultTableModel model = new javax.swing.table.DefaultTableModel();
    Database db = new Database();
    try{
    	String[] columns = new String[] {"ENTERORGNAME","WRITEDATE","WRITEUNIT","PROOF","CASENO","OWNERSHIP",
    			                         "PROPERTYKIND","FUNDTYPE","KEEPUNIT","PROPERTYNO","PROPERTYNAME",
    			                         "PROPERTYNAME1","PLACE","SECURITYMEAT","SECURITYNAME","SECURITYADDR",
    			                         "SECURITYITEM","SECURITYTIME","SECURITYORG","SECURITYDOC","CAUSE",
    			                         "OLDBOOKAMOUNT","OLDBOOKVALUE","ADJUSTBOOKAMOUNT","ADJUSTBOOKVALUE",
    			                         "NEWBOOKAMOUNT","NEWBOOKVALUE","Q_KIND","subReportDataSource",
    			                         "ADJUSTBOOKAMOUNTCHECK","ADJUSTBOOKVALUECHECK","caseNoPage","summonsNo"};

    	String execSQL="select distinct a.enterOrg as shareEnterOrg, a.ownership as shareOwnership, a.propertyNo as sharePropertyNo, a.serialNo as shareSerialNo, a.caseNo as shareCaseNo, " +
		" c.ORGANANAME as enterOrgName, a.writeDate, a.writeUnit, a.proofDoc, a.proofNo, a.caseNo," +
		" (select x.codeName from sysca_code x where a.ownership = x.codeid and x.codekindid = 'OWA') as ownership, " +
		" (select x.codeName from sysca_code x where a.propertyKind = x.codeid and x.codekindid = 'PKD') as  propertyKind, " +
		" d.codeName as fundType, e.unitName as keepUnit,(a.propertyNo||'-'||a.serialNo) as propertyNo, " +
		" b.propertyName, a.propertyName1, a.place, a.securityMeat, a.securityName, " +
		" a.securityAddr, a.securityItem, a.securityTime, " +
		" (select f.ORGANANAME from SYSCA_Organ f where a.securityOrg = f.organID)as securityOrg, " +
		" a.securityDoc, a.cause1, a.cause, " +
		" (select g.codeName from SYSCA_Code g where a.cause=g.codeID(+) and g.codeKindID(+) = 'CAD') as causeName, " +
		" a.oldBookAmount, a.oldBookValue, " +
		" nvl(a.adjustBookAmount,0) as adjustBookAmount, nvl(a.adjustBookValue,0) as adjustBookValue, " +
		" a.newBookAmount, a.newBookValue, a.summonsNo "+
		" from UNTVP_AdjustProof a,SYSPK_PropertyCode b, SYSCA_Organ c,SYSCA_Code d, UNTMP_KeepUnit e "+
		" where 1=1 and a.propertyNo = b.propertyNo and a.enterOrg = c.organID and a.fundType=d.codeID(+) and d.codeKindID(+) = 'FUD' and a.enterOrg=e.enterOrg(+) and a.keepUnit=e.unitNo(+)"+
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
    	if (!"".equals(Common.get(getQ_ownership()))){
    		execSQL +=" and a.ownership = " + util.Common.sqlChar(getQ_ownership());
    	}
		if (!"".equals(getQ_caseNoS()))
			execSQL +=" and a.caseNo >= " + Common.sqlChar(Common.formatRearString(getQ_caseNoS(),10,'0'));		
		if (!"".equals(getQ_caseNoE()))
			execSQL +=" and a.caseNo<=" + Common.sqlChar(Common.formatRearString(getQ_caseNoE(),10, '9'));
    	
    	if (!"".equals(Common.get(getQ_writeDateS())))
    		execSQL += " and a.writeDate >= " + util.Common.sqlChar(new Datetime().changeTaiwanYYMMDD(getQ_writeDateS(),"2"));
    	if (!"".equals(Common.get(getQ_writeDateE())))
    		execSQL += " and a.writeDate <= " + util.Common.sqlChar(new Datetime().changeTaiwanYYMMDD(getQ_writeDateE(),"2"));
    	if (!"".equals(getQ_proofYear())) 
			execSQL += " and a.proofYear = " + Common.sqlChar(getQ_proofYear());	
		if (!"".equals(getQ_proofDoc()))
			execSQL += " and a.proofDoc like '%" + getQ_proofDoc() + "%'" ;
		if (!"".equals(getQ_proofNoS())) 
			execSQL += " and a.proofNo >= " + Common.sqlChar(getQ_proofNoS());		
		if (!"".equals(getQ_proofNoE())) 
			execSQL += " and a.proofNo <= " + Common.sqlChar(getQ_proofNoE());		 
    
		execSQL+=" order by a.caseno, a.propertyNo, a.serialNo";
    	String kindName[]={"第一聯","第二聯","第三聯"};
    	if(!"4".equals(q_kind)){
    		if("3".equals(q_kind))q_kind=kindName[2];
    		else if("2".equals(q_kind))q_kind=kindName[1];
    		else q_kind=kindName[0];
    	
        ResultSet rs = db.querySQL(execSQL);
        Vector rowData = new Vector();
        //int i;
        while(rs.next()){
        	obj.setShareEnterOrg(rs.getString("shareEnterOrg"));
        	obj.setShareOwnership(rs.getString("shareOwnership"));
        	obj.setSharePropertyNo(rs.getString("sharePropertyNo"));
        	obj.setShareSerialNo(rs.getString("shareSerialNo"));
        	obj.setShareCaseNo(rs.getString("shareCaseNo"));
        	
        	Object[] data = new Object[34];
            data[0] = rs.getString("enterOrgName");
            data[1] = Integer.parseInt(rs.getString("writeDate").substring(0,3))+"年"+Integer.parseInt(rs.getString("writeDate").substring(3,5))+"月"+Integer.parseInt(rs.getString("writeDate").substring(5))+"日";
            data[2] = rs.getString("writeUnit");
            data[3] = rs.getString("proofDoc")+"字第"+rs.getString("proofNo")+"號";
            data[4] = "電腦單號："+rs.getString("caseNo");
            data[5] = rs.getString("ownership");
            data[6] = rs.getString("propertyKind");
            data[7] = rs.getString("fundType");
            data[8] = rs.getString("keepUnit");
            data[9] = rs.getString("propertyNo");
            data[10] = rs.getString("propertyName");
            data[11] = rs.getString("propertyName1");
            data[12] = rs.getString("place");
            data[13] = rs.getString("securityMeat");
            data[14] = rs.getString("securityName");
            data[15] = rs.getString("securityAddr");
            data[16] = rs.getString("securityItem");
            if(rs.getString("securityTime")!=null){
            	data[17] = rs.getString("securityTime").substring(0,3)+"/"+rs.getString("securityTime").substring(3,5);
            }else{
            	data[17] = rs.getString("securityTime");
            }
            data[18] = rs.getString("securityOrg");
            data[19] = rs.getString("securityDoc");
            if(rs.getString("cause")=="99"){
            	data[20] = rs.getString("cause1");
            }else{
            	data[20] = rs.getString("causeName");
            }
            //data[21] = new Double(rs.getString("oldBookSheet"));
            data[21] = new Double(rs.getString("oldBookAmount"));
            data[22] = new Double(rs.getString("oldBookValue"));
			/*if (Integer.parseInt(rs.getString("adjustBookSheet"))>=0){
				data[24] = new Integer(rs.getString("adjustBookSheet"));
				data[32] = "增";
			}else{
				data[24] = new Integer(Integer.parseInt(rs.getString("adjustBookSheet"))*-1);
				data[32] = "減";
			}*/
			if (Long.parseLong(rs.getString("adjustBookAmount"))>=0){
				data[23] = new Long(rs.getString("adjustBookAmount"));
				data[29] = "增";
			}else{
				data[23] = new Long(Long.parseLong(rs.getString("adjustBookAmount"))*-1);
				data[29] = "減";
			}
			if (Long.parseLong(rs.getString("adjustBookValue"))>=0){
				data[24] = new Long(rs.getString("adjustBookValue"));
				data[30] = "增";
			}else{
				data[24] = new Long(Long.parseLong(rs.getString("adjustBookValue"))*-1);
				data[30] = "減";
			}
            //data[27] = new Double(rs.getString("newBookSheet"));
            data[25] = new Double(rs.getString("newBookAmount"));
            data[26] = new Double(rs.getString("newBookValue"));
            data[27] = q_kind;
            data[28] = new JRTableModelDataSource(getSubModel((String)data[0]));  
            data[31] = rs.getString("caseNo");
            data[32] = Common.get(rs.getString("summonsNo"))+"　";
            //for(i=0;i<35;i++)if(data[i]==null)data[i]="";
            rowData.addElement(data);
        }
    	
        Object[][] data = new Object[0][0];
        data = (Object[][])rowData.toArray(data);
        model.setDataVector(data, columns);
    	}//if
    	else
    	{
    		int j;
    		Vector rowData = new Vector();
    		for(j=0;j<3;j++){
    		q_kind=kindName[j];
    		ResultSet rs = db.querySQL(execSQL);
            //int i;
            while(rs.next()){
            	obj.setShareEnterOrg(rs.getString("shareEnterOrg"));
            	obj.setShareOwnership(rs.getString("shareOwnership"));
            	obj.setSharePropertyNo(rs.getString("sharePropertyNo"));
            	obj.setShareSerialNo(rs.getString("shareSerialNo"));
            	obj.setShareCaseNo(rs.getString("shareCaseNo"));

            	Object[] data = new Object[33];
                data[0] = rs.getString("enterOrgName");
                data[1] = Integer.parseInt(rs.getString("writeDate").substring(0,3))+"年"+Integer.parseInt(rs.getString("writeDate").substring(3,5))+"月"+Integer.parseInt(rs.getString("writeDate").substring(5))+"日";
                data[2] = rs.getString("writeUnit");
                data[3] = rs.getString("proofDoc")+"字第"+rs.getString("proofNo")+"號";
                data[4] = "電腦單號："+rs.getString("caseNo");
                data[5] = rs.getString("ownership");
                data[6] = rs.getString("propertyKind");
                data[7] = rs.getString("fundType");
                data[8] = rs.getString("keepUnit");
                data[9] = rs.getString("propertyNo");
                data[10] = rs.getString("propertyName");
                data[11] = rs.getString("propertyName1");
                data[12] = rs.getString("place");
                data[13] = rs.getString("securityMeat");
                data[14] = rs.getString("securityName");
                data[15] = rs.getString("securityAddr");
                data[16] = rs.getString("securityItem");
                if(rs.getString("securityTime")!=null){
                	data[17] = rs.getString("securityTime").substring(0,3)+"/"+rs.getString("securityTime").substring(3,5);
                }else{
                	data[17] = rs.getString("securityTime");
                }
                data[18] = rs.getString("securityOrg");
                data[19] = rs.getString("securityDoc");
                if(rs.getString("cause")=="99"){
                	data[20] = rs.getString("cause1");
                }else{
                	data[20] = rs.getString("causeName");
                }
                //data[21] = new Double(rs.getString("oldBookSheet"));
                data[21] = new Double(rs.getString("oldBookAmount"));
                data[22] = new Double(rs.getString("oldBookValue"));
    			/*if (Integer.parseInt(rs.getString("adjustBookSheet"))>=0){
    				data[24] = new Integer(rs.getString("adjustBookSheet"));
    				data[32] = "增";
    			}else{
    				data[24] = new Integer(Integer.parseInt(rs.getString("adjustBookSheet"))*-1);
    				data[32] = "減";
    			}*/
    			if (Long.parseLong(rs.getString("adjustBookAmount"))>=0){
    				data[23] = new Long(rs.getString("adjustBookAmount"));
    				data[29] = "增";
    			}else{
    				data[23] = new Long(Long.parseLong(rs.getString("adjustBookAmount"))*-1);
    				data[29] = "減";
    			}
    			if (Long.parseLong(rs.getString("adjustBookValue"))>=0){
    				data[24] = new Long(rs.getString("adjustBookValue"));
    				data[30] = "增";
    			}else{
    				data[24] = new Long(Long.parseLong(rs.getString("adjustBookValue"))*-1);
    				data[30] = "減";
    			}
                //data[27] = new Double(rs.getString("newBookSheet"));
                data[25] = new Double(rs.getString("newBookAmount"));
                data[26] = new Double(rs.getString("newBookValue"));
                data[27] = q_kind;
                data[28] = new JRTableModelDataSource(getSubModel((String)data[0]));
                data[31] = rs.getString("caseNo")+j;
                data[32] = Common.get(rs.getString("summonsNo"))+"　";
                //for(i=0;i<35;i++)if(data[i]==null)data[i]="";
                rowData.addElement(data);
             }
    		}//for
            Object[][] data = new Object[0][0];
            data = (Object[][])rowData.toArray(data);
            model.setDataVector(data, columns);
        	}//else
    }catch(Exception x){
       x.printStackTrace();
    }finally{
        db.closeAll();
    }
    
    return model;
}

public DefaultTableModel getSubModel(String caseCode) throws Exception{
	UNTVP009R obj = this;
    DefaultTableModel model = new javax.swing.table.DefaultTableModel();
    Database db = new Database();
    try{
        String[] columns = new String[] { "ADJUSTTYPE","ADJUSTBOOKUA","ADJUSTBOOKAMOUNT",
        		                          "ADJUSTBOOKUV","ADJUSTBOOKVALUE","ADJUSTPROOFS","ADJUSTPROOFE"};
    	String execSQL="select nvl(a.adjustBookUA,0) as adjustBookUA, nvl(a.adjustBookAmount,0) as adjustBookAmount, nvl(a.adjustBookUV,0) as adjustBookUV, nvl(a.adjustBookValue,0) as adjustBookValue, nvl(a.adjustProofS,'') as adjustProofS, nvl(a.adjustProofE,'') as adjustProofE, " +
    					" decode(a.adjustType,'1','增加','2','減少','') adjustType " +
						" from UNTVP_AdjustDetail a "+
						" where 1=1 "+
						" and a.enterOrg=" + util.Common.sqlChar(obj.getShareEnterOrg())+
						" and a.ownership=" + util.Common.sqlChar(obj.getShareOwnership())+
						" and a.caseNo=" + util.Common.sqlChar(obj.getShareCaseNo())+
						" and a.propertyNo=" + util.Common.sqlChar(obj.getSharePropertyNo())+
						" and a.serialNo=" + util.Common.sqlChar(obj.getShareSerialNo());
    
    	execSQL+=" order by a.serialNo1";
    	ResultSet rs = db.querySQL(execSQL);
    	Vector rowData = new Vector();
    	int i;
    	while(rs.next()){
    		Object[] data = new Object[7];
    		data[0] = rs.getString("adjustType");
    		data[1] = new Double(rs.getString("adjustBookUA"));
    		//data[2] = new Double(rs.getString("adjustBookSheet"));
    		data[2] = new Double(rs.getString("adjustBookAmount"));
    		data[3] = new Double(rs.getString("adjustBookUV"));
    		data[4] = new Double(rs.getString("adjustBookValue"));
    		data[5] = rs.getString("adjustProofS");
    		data[6] = rs.getString("adjustProofE");
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
    }
    
    return model;

}

}