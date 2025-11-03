

/*
*<br>程式目的：動產撥出清冊報表檔 
*<br>程式代號：untmp018r
*<br>撰寫日期：94/11/23
*<br>程式作者：Chris
*<br>--------------------------------------------------------
*<br>修改作者　　修改日期　　　修改目的
*<br>--------------------------------------------------------
*/

package unt.mp;

import java.sql.ResultSet;
import java.util.Vector;

import javax.swing.table.DefaultTableModel;

import util.*;

public class UNTMP018R extends SuperBean{


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
    	String[] columns = new String[] {"OWNERSHIP","ENTERORGNAME","WRITEDATE","CASENO","PROOFDOC","PROPERTYNO","PROPERTYNAME","SPECIFICATION","PROPERTYUNIT","ADJUSTBOOKAMOUNT","BUYDATE","LIMITYEAR","USEYEAR","OLDBOOKUNIT","ADJUSTBOOKVALUE","CAUSE"};
    	String execSQL="select c.organAName as enterOrgName," +
    					" (select z.codeName from sysca_code z where 1=1 and (z.codeKindID='owa' or z.codeKindID='OWA') and a.ownership=z.codeID) as ownershipName, " +
		    			" a.writeDate," +
		    			" a.caseNO," +
		    			" a.proofDoc||'字第'||a.proofNo||'號' as proofNo," +
		    			" substr(b.propertyNo,0,7)||'-'||substr(b.propertyNo,8)||'-'||b.serialNo as propertyNo," +
		    			" d.propertyName," +
		    			" b.specification||'/'||b.nameplate as specification," +
		    			" d.propertyUnit," +
		    			" b.adjustBookAmount," +
		    			" b.buyDate," +
		    			" d.limitYear,b.otherLimitYear," +
		    			" b.useYear||'年'||b.useMonth||'個月' as useYear," +
		    			" b.oldBookUnit," +
		    			" b.adjustBookValue," +
		    			" (select f.codename from SYSCA_CODE f where f.codekindid = 'CAC' and b.cause=f.codeid)cause, " +
		    			" b.cause1, " +
		    			" (select  z.organAName from SYSCA_Organ z where 1=1 and b.newEnterOrg=z.organID) as newEnterOrgName " +
		    			""; 
    	String execSQL1=" from UNTMP_ReduceProof a,UNTMP_ReduceDetail b,SYSCA_Organ c,SYSPK_PropertyCode d  ";
    	execSQL1=execSQL1+" where a.enterorg=b.enterorg and a.caseno=b.caseno and a.ownership=b.ownership and b.propertyNo=d.propertyNo and a.enterOrg=c.organID(+) ";
    	execSQL1=execSQL1+" and d.enterOrg in ('000000000A',a.enterOrg) ";
    	execSQL1=execSQL1+" and d.propertyType='1' and b.cause='01' ";
    	if (!"".equals(Common.get(getQ_ownership()))){
    		execSQL1 = execSQL1 + " and a.ownership = " + util.Common.sqlChar(getQ_ownership());
    	}
    	/*if (!"".equals(Common.get(getQ_enterOrg()))){
    		execSQL = execSQL + " and a.enterorg = " + util.Common.sqlChar(getQ_enterOrg());
    	}
    	*/
    	if (!"".equals(getQ_enterOrg())) {
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
    	//String execSQL2="select a.caseno,count(*) "+execSQL1+" group by a.caseno order by a.caseno";
    	String tansToDouble;
    	//String DateTrn;
    	String causetemp;
    	String limitYeartemp;
    	/*
    	int groupcount[]=new int[1024];
    	int loop1=0;
    	ResultSet rscount = db.querySQL(execSQL2);
    	while(rscount.next()){groupcount[loop1]=Integer.parseInt(rscount.getString("count(*)"));loop1++;}
    	*/
        //int i,n,loop2;
    	ResultSet rs = db.querySQL(execSQL);
    	
        Vector rowData = new Vector();
        //loop2=0;n=0;
        while(rs.next()){
        	Object[] data = new Object[16];
        	//Object[] temp = new Object[30];
            data[0] = rs.getString("ownershipName");
            data[1] = rs.getString("enterOrgName");
            data[2] = Common.MaskCDate(rs.getString("writeDate"));
        	data[3] = rs.getString("caseNO")+"　";
            data[4] = rs.getString("proofNo");
            data[5] = rs.getString("propertyNo");
            data[6] = rs.getString("propertyName");
            if(!"/".equals(rs.getString("specification")))
            	data[7] = rs.getString("specification");
            else
            	data[7]="";
            data[8] = rs.getString("propertyUnit");
            
            tansToDouble = rs.getString("adjustBookAmount");
            if(tansToDouble!=null)data[9]= new Double(Double.parseDouble(tansToDouble));
            else data[9]=null;
            
            data[10] = Common.MaskDate(rs.getString("buyDate"));
        	
        	data[11] = rs.getString("limitYear");
        	limitYeartemp=rs.getString("otherLimitYear");;
        	if(data[11]==null)data[11]=limitYeartemp;
        	
        	data[12] = rs.getString("useYear");
        	
        	tansToDouble = rs.getString("oldBookUnit");
            if(tansToDouble!=null)data[13]= new Double(Double.parseDouble(tansToDouble));
            else data[13]=null;
            
            tansToDouble = rs.getString("adjustBookValue");
            if(tansToDouble!=null)data[14]= new Double(Double.parseDouble(tansToDouble));
            else data[14]=null;
            
            data[15] = rs.getString("cause")+Common.get(rs.getString("newEnterOrgName"));
            causetemp= rs.getString("cause1");
            if("其他".equals(data[15])||data[15]==null)data[15]=causetemp;
            
            //for(i=0;i<20;i++)if(data[i]==null)data[i]="";
            rowData.addElement(data);
            /*loop2++;
            if(loop2==groupcount[n]){
            for(i=0;i<16;i++)temp[i]=data[i];
            data = new Object[30];
            for(i=0;i<5;i++)data[i]=temp[i];
            for(i=5;i<16;i++)data[i]=null;
        	for(i=10-loop2%10;i>0&&i!=10;i--)rowData.addElement(data);
            n++;loop2=0;
            }*/
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
