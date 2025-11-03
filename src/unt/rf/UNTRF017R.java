/*
*<br>程式目的：市有土地改良物增減值單報表檔 
*<br>程式代號：untrf017r
*<br>撰寫日期：94/11/21
*<br>程式作者：chris
*<br>--------------------------------------------------------
*<br>修改作者　　修改日期　　　修改目的
*<br>--------------------------------------------------------
*/
package unt.rf;

import java.sql.ResultSet;
import java.util.Vector;

import javax.swing.table.DefaultTableModel;

import util.*;

public class UNTRF017R extends SuperBean{
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
	String q_proofDoc;
	String q_proofNoS;
	String q_proofNoE;
	String q_kind;
	
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
    try{
    	while(q_caseNoS!=null&&!"".equals(q_caseNoS)&&q_caseNoS.length()<10){q_caseNoS=q_caseNoS+"0";}
    	while(q_caseNoE!=null&&!"".equals(q_caseNoE)&&q_caseNoE.length()<10){q_caseNoE=q_caseNoE+"9";}
    	String[] columns = new String[] {"ENTERORGNAME","WRITEDATE","WRITEUNIT","PROOFNO","CASENO","MANAGENO","SOURCEDATE","PROPERTYNO","PROPERTYNAME","SIGNNO","CAUSE","OLDMEASURE","OLDBOOKVALUE","ADJUSTMEASURE1","ADJUSTBOOKVALUE1","ADJUSTMEASURE2","ADJUSTBOOKVALUE2","NEWMEASURE","NEWBOOKVALUE","KIND","UNIT","summonsNo","propertyKind","propertyKindName"};
    	String execSQL="select c.organAName as enterOrgName,a.writeDate,a.writeUnit,a.proofDoc||'字第'||a.proofNo||'號' as proofNo,a.caseNO,a.manageNo,b.sourceDate,substr(b.propertyNo,0,7)||'-'||substr(b.propertyNo,8,3)||'-'||b.serialNo as propertyNo,d.propertyName," +
		    			" ((select  k.signDesc||substr(h.signNo,8,4)||'-'||substr(h.signNo,12,4)||'地號'from UNTRF_Base h,SYSCA_Sign k where h.enterOrg=b.enterOrg and h.ownership=b.ownership and h.propertyNo=b.propertyNo and h.serialNo=b.serialNo and substr(h.signNo,1,7)=k.signNo and rownum=1 )||'等'|| (select count(*) from UNTRF_Base x where x.enterOrg=b.enterOrg and x.ownership=b.ownership and x.propertyNo=b.propertyNo and x.serialNo=b.serialNo) ||'筆')signNo," +
		    			" b.cause,b.cause1,b.oldMeasure,b.oldBookValue,b.adjustType,b.adjustMeasure,b.adjustBookValue," +
		    			" b.newMeasure,b.newBookValue,d.propertyUnit, " +
		    			" a.summonsNo, "+
    					" b.propertyKind," +
    					" decode(b.propertyKind,'01','公務用','02','公共用','03','事業用','04','非公用','') propertyKindName "+
    					" from UNTRF_AdjustProof a,UNTRF_AdjustDetail b,SYSCA_Organ c,SYSPK_PropertyCode d  "+
    					" where a.enterorg=b.enterorg and a.caseno=b.caseno and a.ownership=b.ownership and b.propertyNo=d.propertyNo and a.enterOrg=c.organID(+) "+
    					" and a.ownership = '1' "+
    					//" and substr(b.signNo,1,7) = e.signNo(+) "+
    					" and d.enterOrg in ('000000000A',a.enterOrg) "+
    					" and d.propertyType='1' ";
    	/*if (!"".equals(Common.get(getQ_enterOrg()))){
    		execSQL = execSQL + " and a.enterorg = " + util.Common.sqlChar(getQ_enterOrg());
    	}
    	*/
    	if (!"".equals(getQ_enterOrgName())) {
    		execSQL += " and a.enterOrg = " + Common.sqlChar(getQ_enterOrg()) ;
    	} else {
    		if (!getIsAdminManager().equalsIgnoreCase("Y")) {
    			if ("Y".equals(getIsOrganManager())) {					
    				execSQL += " and a.enterOrg like '" + getQ_enterOrg().substring(0,5) + "%' ";
    			} else {
    				execSQL += " and a.enterOrg = " + Common.sqlChar(getQ_enterOrg()) ;
    			}
    		}
    	}
    	if (!"".equals(Common.get(getQ_caseNoS())))
    		execSQL += " and a.caseNo >= " + util.Common.sqlChar(getQ_caseNoS());
    	if (!"".equals(Common.get(getQ_caseNoE())))
    		execSQL += " and a.caseNo <= " + util.Common.sqlChar(getQ_caseNoE());
    	if (!"".equals(Common.get(getQ_writeDateS())))
    		execSQL += " and a.writeDate >= " + util.Common.sqlChar(new Datetime().changeTaiwanYYMMDD(getQ_writeDateS(),"2"));
    	if (!"".equals(Common.get(getQ_writeDateE())))
    		execSQL += " and a.writeDate <= " + util.Common.sqlChar(new Datetime().changeTaiwanYYMMDD(getQ_writeDateE(),"2"));
    	if (!"".equals(Common.get(getQ_proofDoc()))){
    		execSQL += " and a.proofDoc like " + util.Common.sqlChar("%"+getQ_proofDoc()+"%");
    	}
    	if (!"".equals(Common.get(getQ_proofNoS())))
    		execSQL += " and a.proofNo >= " + util.Common.sqlChar(getQ_proofNoS());
    	if (!"".equals(Common.get(getQ_proofNoE())))
    		execSQL += " and a.proofNo <= " + util.Common.sqlChar(getQ_proofNoE());
    	execSQL += " order by a.caseno,b.propertyKind,b.propertyno,b.serialno";
    	
    	String kindName[]={"第一聯","第二聯","第三聯"};
    	String causetemp="";
    	String tansToDouble;
    	//String DateTrn;
    	String AdjustType="";
    	double Doubletemp;
    	if(!"4".equals(q_kind)){
    		if("3".equals(q_kind))q_kind=kindName[2];
    		else if("2".equals(q_kind))q_kind=kindName[1];
    		else q_kind=kindName[0];
    	System.out.println("execSQL "+execSQL);
        ResultSet rs = db.querySQL(execSQL);
        Vector rowData = new Vector();
        while(rs.next()){
        	Object[] data = new Object[24];
        	data[0] = rs.getString("enterOrgName");
        	data[1] = Common.MaskCDate(rs.getString("writeDate"));
        	data[2] = rs.getString("writeUnit");
            data[3] = rs.getString("proofNo");
            data[4] = rs.getString("caseNO")+"　";
            data[5] = Common.get(rs.getString("manageNo"))+"　";
            data[6] = Common.MaskDate(rs.getString("sourceDate"));
        	data[7] = rs.getString("propertyNo");
            data[8] = rs.getString("propertyName");
            data[9] = rs.getString("signNo");
            if("等0筆".equals(data[9]))data[9]="";
            data[10] = rs.getString("cause");
            causetemp= rs.getString("cause1");
            if("1".equals(data[10]))data[10]="資產重估調整";
            else if("2".equals(data[10]))data[10]="整建";
            else data[10]=causetemp;
            tansToDouble= rs.getString("oldMeasure");
            if(tansToDouble!=null)data[11]= new Double(Double.parseDouble(tansToDouble));
            else data[11]=null;
            tansToDouble= rs.getString("oldBookValue");
            if(tansToDouble!=null)data[12]= new Double(Double.parseDouble(tansToDouble));
            else data[12]=null;
            AdjustType=rs.getString("adjustType");
            tansToDouble= rs.getString("adjustMeasure");
            if(tansToDouble!=null){
            	Doubletemp= Double.parseDouble(tansToDouble);
            	if("2".equals(AdjustType)){data[15]=new Double(Doubletemp);data[13]=new Double(0);}
            	else {data[13]=new Double(Doubletemp);data[15]=new Double(0);}
            }
            else {
            	data[13]=null;data[15]=null;
            }
            tansToDouble= rs.getString("adjustBookValue");
            if(tansToDouble!=null){
            	Doubletemp= Double.parseDouble(tansToDouble);
            	if("2".equals(AdjustType)){data[16]=new Double(Doubletemp);data[14]=new Double(0);}
            	else {data[14]=new Double(Doubletemp);data[16]=new Double(0);}
            }
            else {
            	data[14]=null;data[16]=null;
            }
            tansToDouble= rs.getString("newMeasure");
            if(tansToDouble!=null)data[17]= new Double(Double.parseDouble(tansToDouble));
            else data[17]=null;
            tansToDouble= rs.getString("newBookValue");
            if(tansToDouble!=null)data[18]= new Double(Double.parseDouble(tansToDouble));
            else data[18]=null;
            data[19] = q_kind;
            data[20] = rs.getString("propertyUnit");
            data[21] = Common.get(rs.getString("summonsNo"))+"　";
            data[22] = Common.get(rs.getString("propertyKind"));
            data[23] = Common.get(rs.getString("propertyKindName"));
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
    		while(rs.next()){
            	Object[] data = new Object[24];
            	//Object[] temp = new Object[30];
            	data[0] = rs.getString("enterOrgName");
            	data[1] = Common.MaskCDate(rs.getString("writeDate"));
            	data[2] = rs.getString("writeUnit");
                data[3] = rs.getString("proofNo");
                data[4] = rs.getString("caseNO")+"　";
                data[5] = Common.get(rs.getString("manageNo"))+"　";
                data[6] = Common.MaskDate(rs.getString("sourceDate"));
            	data[7] = rs.getString("propertyNo");
                data[8] = rs.getString("propertyName");
                data[9] = rs.getString("signNo");
                if("等0筆".equals(data[9]))data[9]="";
                data[10] = rs.getString("cause");
                causetemp= rs.getString("cause1");
                if("1".equals(data[10]))data[10]="資產重估調整";
                else if("2".equals(data[10]))data[10]="整建";
                else data[10]=causetemp;
                tansToDouble= rs.getString("oldMeasure");
                if(tansToDouble!=null)data[11]= new Double(Double.parseDouble(tansToDouble));
                else data[11]=null;
                tansToDouble= rs.getString("oldBookValue");
                if(tansToDouble!=null)data[12]= new Double(Double.parseDouble(tansToDouble));
                else data[12]=null;
                AdjustType=rs.getString("adjustType");
                tansToDouble= rs.getString("adjustMeasure");
                if(tansToDouble!=null){
                	Doubletemp= Double.parseDouble(tansToDouble);
                	if("2".equals(AdjustType)){data[15]=new Double(Doubletemp);data[13]=new Double(0);}
                	else {data[13]=new Double(Doubletemp);data[15]=new Double(0);}
                }
                else {
                	data[13]=null;data[15]=null;
                }
                tansToDouble= rs.getString("adjustBookValue");
                if(tansToDouble!=null){
                	Doubletemp= Double.parseDouble(tansToDouble);
                	if("2".equals(AdjustType)){data[16]=new Double(Doubletemp);data[14]=new Double(0);}
                	else {data[14]=new Double(Doubletemp);data[16]=new Double(0);}
                }
                else {
                	data[14]=null;data[16]=null;
                }
                tansToDouble= rs.getString("newMeasure");
                if(tansToDouble!=null)data[17]= new Double(Double.parseDouble(tansToDouble));
                else data[17]=null;
                tansToDouble= rs.getString("newBookValue");
                if(tansToDouble!=null)data[18]= new Double(Double.parseDouble(tansToDouble));
                else data[18]=null;
                data[19] = q_kind;
                data[20] = rs.getString("propertyUnit");
                data[21] = Common.get(rs.getString("summonsNo"))+"　";
                data[22] = Common.get(rs.getString("propertyKind"));
                data[23] = Common.get(rs.getString("propertyKindName"));
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

}
