

/*
*<br>程式目的：土地改良物移交清冊報表檔 
*<br>程式代號：untrf011r
*<br>撰寫日期：94/11/9
*<br>程式作者：Chris
*<br>--------------------------------------------------------
*<br>修改作者　　修改日期　　　修改目的
*<br>--------------------------------------------------------
*/

package unt.rf;

import java.sql.ResultSet;
import java.util.Vector;

import javax.swing.table.DefaultTableModel;

import util.*;

public class UNTRF011R extends SuperBean{


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

    String orderBy;    
    public String getOrderBy() {return checkGet(orderBy);}
	public void setOrderBy(String orderBy) {this.orderBy = checkSet(orderBy);}

public String getUseRelation() {
    Database db = new Database();
    try{   
    	String sql = "";
    	
    }catch(Exception x){
        x.printStackTrace();
    }finally{
        db.closeAll();
    }
	return caseNoE;
}
    

public DefaultTableModel getResultModel() throws Exception{
    DefaultTableModel model = new javax.swing.table.DefaultTableModel();
    Database db = new Database();
    try{
    	while(q_caseNoS!=null&&!"".equals(q_caseNoS)&&q_caseNoS.length()<10){q_caseNoS=q_caseNoS+"0";}
    	while(q_caseNoE!=null&&!"".equals(q_caseNoE)&&q_caseNoE.length()<10){q_caseNoE=q_caseNoE+"9";}
    	String[] columns = new String[] {"OWNERSHIP","ENTERORGNAME","WRITEDATE","WRITEUNIT","PROOFNO","NEWENTERORG","CASENO","TRANSFERDATE","MANAGENO","PROPERTYNO","PROPERTYNAME","SIGNNO","PROPERTYUNIT","MEASURE","BOOKVALUE","CAUSE","KIND","PROPERTYNAME1","USERELATION"};
    	String execSQL="select c.organAName as enterOrgName,a.writeDate,a.writeUnit,a.proofDoc||'字第'||a.proofNo||'號' as proofNo,(select l1.organAName from SYSCA_Organ l1 where b.newEnterOrg=l1.organID(+))newEnterOrg,a.caseNO,b.transferDate,a.manageNo,substr(b.propertyNo,0,7)||'-'||substr(b.propertyNo,8,3)||'-'||b.serialNo as propertyNo,d.propertyName," + "\n" +
    			"((select  k.signDesc||substr(h.signNo,8,4)||'-'||substr(h.signNo,12,4)||'地號'from UNTRF_Base h,SYSCA_Sign k where h.enterOrg=b.enterOrg and h.ownership=b.ownership and h.propertyNo=b.propertyNo and h.serialNo=b.serialNo and substr(h.signNo,1,7)=k.signNo and rownum=1 )||'等'|| (select count(*) from UNTRF_Base x where x.enterOrg=b.enterOrg and x.ownership=b.ownership and x.propertyNo=b.propertyNo and x.serialNo=b.serialNo) ||'筆')signNo," + "\n" +
    			"d.propertyUnit,b.measure,b.bookValue,(select f.codename from SYSCA_CODE f where f.codekindid = 'CAC' and b.cause=f.codeid)cause,b.cause1 "+ "\n" +
    	        ",b.propertyname1"+ "\n" +
    	        ",(select count(*)userelation from UNTRF_Manage m ,SYSCA_Code n where b.enterorg = m.enterorg and b.ownership = m.ownership and b.propertyno = m.propertyno and b.serialno = m.serialno and n.codeKindID='URE' and n.codeid = m.userelation and nvl(m.usedates,'0000000') <= '"+Datetime.getYYYMMDD()+"' and nvl(m.usedatee,'9999999') >= '"+Datetime.getYYYMMDD()+"')userelationNum "+ "\n" +
    	        ",(select n.codename from UNTRF_Manage m ,SYSCA_Code n where b.enterorg = m.enterorg and b.ownership = m.ownership and b.propertyno = m.propertyno and b.serialno = m.serialno and n.codeKindID='URE' and n.codeid = m.userelation and nvl(m.usedates,'0000000') <= '"+Datetime.getYYYMMDD()+"' and nvl(m.usedatee,'9999999') >= '"+Datetime.getYYYMMDD()+"' and rownum<=1)userelation" + "\n" ;
    	String execSQL1=" from UNTRF_ReduceProof a,UNTRF_ReduceDetail b,SYSCA_Organ c,SYSPK_PropertyCode d  ";
    	execSQL1=execSQL1+"where a.enterorg=b.enterorg and a.caseno=b.caseno and a.ownership=b.ownership and b.propertyNo=d.propertyNo and a.enterOrg=c.organID(+) ";
    	execSQL1=execSQL1+" and d.enterOrg in ('000000000A',a.enterOrg) ";
    	execSQL1=execSQL1+" and d.propertyType='1' ";
    	execSQL1=execSQL1+" and b.cause in ('01','08') ";
    	if (!"".equals(Common.get(getQ_ownership()))){
    		execSQL1 = execSQL1 + " and a.ownership = " + util.Common.sqlChar(getQ_ownership());
    	}
    	/*if (!"".equals(Common.get(getQ_enterOrg()))){
    		execSQL = execSQL + " and a.enterorg = " + util.Common.sqlChar(getQ_enterOrg());
    	}
    	*/
    	if (!"".equals(getQ_enterOrgName())) {
    		execSQL1+=" and a.enterOrg = " + Common.sqlChar(getQ_enterOrg()) ;
    	} else {
    		if (!getIsAdminManager().equalsIgnoreCase("Y")) {
    			if ("Y".equals(getIsOrganManager())) {					
    				execSQL1 += " and a.enterOrg like '" + getOrganID().substring(0,5) + "%' ";
    			} else {
    				execSQL1+=" and a.enterOrg = " + Common.sqlChar(getOrganID()) ;
    			}
    		}
    	}
    	
    	if (!"".equals(Common.get(getQ_caseNoS())))
    		execSQL1 = execSQL1 + " and a.caseNo >= " + util.Common.sqlChar(getQ_caseNoS());
    	if (!"".equals(Common.get(getQ_caseNoE())))
    		execSQL1 = execSQL1 + " and a.caseNo <= " + util.Common.sqlChar(getQ_caseNoE());
    	
    	if (!"".equals(Common.get(getQ_writeDateS())))
    		execSQL1 = execSQL1 + " and a.writeDate >= " + util.Common.sqlChar(new Datetime().changeTaiwanYYMMDD(getQ_writeDateS(),"2"));
    	if (!"".equals(Common.get(getQ_writeDateE())))
    		execSQL1 = execSQL1 + " and a.writeDate <= " + util.Common.sqlChar(new Datetime().changeTaiwanYYMMDD(getQ_writeDateE(),"2"));
    	
    	if (!"".equals(Common.get(getQ_proofDoc()))){
    		execSQL1 = execSQL1 + " and a.proofDoc like " + util.Common.sqlChar("%"+getQ_proofDoc()+"%");
    	}
    	if (!"".equals(Common.get(getQ_proofNoS())))
    		execSQL1 = execSQL1 + " and a.proofNo >= " + util.Common.sqlChar(getQ_proofNoS());
    	if (!"".equals(Common.get(getQ_proofNoE())))
    		execSQL1 = execSQL1 + " and a.proofNo <= " + util.Common.sqlChar(getQ_proofNoE());
    	
    	
    	execSQL=execSQL+execSQL1+" order by a.caseno,b.propertyno,b.serialno";
    	String kindName[]={"第一聯","第二聯","第三聯"};
    	String tansToDouble;
    	//String DateTrn;
    	String causetemp;
    	String ownershiptemp="";
    	
    	if ("2".equals(Common.get(getQ_ownership()))){
    		ownershiptemp="國有土地改良物移交清冊";
    	}else {
    		ownershiptemp="縣有土地改良物移交清冊";
    	}
    	//System.out.println(execSQL);
    	if(!"4".equals(q_kind)){
    		if("3".equals(q_kind)){
    			q_kind=kindName[2];
    		}else if("2".equals(q_kind)){
    			q_kind=kindName[1];
    		}else{ 
    			q_kind=kindName[0];
    		}
            ResultSet rs = db.querySQL(execSQL);
            Vector rowData = new Vector();
            while(rs.next()){
        	    Object[] data = new Object[32];
        	    data[0]=ownershiptemp;
                data[1] = rs.getString("enterOrgName");
                data[2] = Common.MaskCDate(rs.getString("writeDate"));
        	    data[3] = rs.getString("writeUnit");
                data[4] = rs.getString("proofNo");
                data[5] = rs.getString("newEnterOrg");
                data[6] = rs.getString("caseNO")+"　";
                data[7] = Common.MaskDate(rs.getString("transferDate"));
        	    data[8] = rs.getString("manageNo");
                data[9] = rs.getString("propertyNo");
                data[10] = rs.getString("propertyName");
                data[11] = rs.getString("signNo");
                if("等0筆".equals(data[11]))data[11]="";
                data[12] = rs.getString("propertyUnit");
            
                tansToDouble = rs.getString("measure");
                if(tansToDouble!=null)data[13]= new Double(Double.parseDouble(tansToDouble));
                else data[13]=null;
            
                tansToDouble = rs.getString("bookValue");
                if(tansToDouble!=null)data[14]= new Double(Double.parseDouble(tansToDouble));
                else data[14]=null;
            
                data[15] = rs.getString("cause");
                causetemp= rs.getString("cause1");
                if("其他".equals(data[15])||data[15]==null)data[15]=causetemp;
                data[16]=q_kind;               
                
                data[17]=rs.getString("propertyname1");
                if(Integer.parseInt(rs.getString("userelationNum")) >1 ){
                	data[18] = rs.getString("userelation") + "等" + rs.getString("userelationNum") + "幾筆";
                }else{
                	data[18] = rs.getString("userelation");
                }
                rowData.addElement(data);        
            }
            Object[][] data = new Object[0][0];
            data = (Object[][])rowData.toArray(data);
            model.setDataVector(data, columns);
    	}else{
    		int j;
    		Vector rowData = new Vector();
    		for(j=0;j<3;j++){
    		q_kind=kindName[j];
    		ResultSet rs = db.querySQL(execSQL);
    		while(rs.next()){
            	Object[] data = new Object[30];
            	//Object[] temp = new Object[30];
            	data[0]=ownershiptemp;
                data[1] = rs.getString("enterOrgName");
                data[2] = Common.MaskCDate(rs.getString("writeDate"));
            	data[3] = rs.getString("writeUnit");
                data[4] = rs.getString("proofNo");
                data[5] = rs.getString("newEnterOrg");
                data[6] = rs.getString("caseNO")+"　";
                data[7] = Common.MaskDate(rs.getString("transferDate"));
            	data[8] = rs.getString("manageNo");
                data[9] = rs.getString("propertyNo");
                data[10] = rs.getString("propertyName");
                data[11] = rs.getString("signNo");
                if("等0筆".equals(data[11]))data[11]="";
                data[12] = rs.getString("propertyUnit");
                tansToDouble = rs.getString("measure");
                if(tansToDouble!=null)data[13]= new Double(Double.parseDouble(tansToDouble));
                else data[13]=null;
                
                tansToDouble = rs.getString("bookValue");
                if(tansToDouble!=null)data[14]= new Double(Double.parseDouble(tansToDouble));
                else data[14]=null;
                
                data[15] = rs.getString("cause");
                causetemp= rs.getString("cause1");
                if("其他".equals(data[15])||data[15]==null)data[15]=causetemp;
                data[16]=q_kind;      
                data[17]=rs.getString("propertyname1");
                
                if(Integer.parseInt(rs.getString("userelationNum")) >1 ){
                	data[18] = rs.getString("userelation") + "等" + rs.getString("userelationNum") + "幾筆";
                }else{
                	data[18] = rs.getString("userelation");
                }
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
