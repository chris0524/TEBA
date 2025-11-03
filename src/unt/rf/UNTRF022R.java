
/*
*<br>程式目的：土地改良物毀損報廢單報表檔 
*<br>程式代號：untrf022r
*<br>撰寫日期：95/12/29
*<br>程式作者：Jim Chou
*<br>--------------------------------------------------------
*<br>修改作者　　修改日期　　　修改目的
*<br>--------------------------------------------------------
*/
package unt.rf;
import java.sql.ResultSet;
import java.util.Vector;

import javax.swing.table.DefaultTableModel;

import util.*;

public class UNTRF022R extends SuperBean{

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
    	
    	String[] columns = new String[] {"OWNERSHIP","ENTERORGNAME","WRITEDATE","CASENO","PROPERTYNO","PROPERTYNAME","PROPERTYUNIT","OLDBOOKUNIT","ADJUSTBOOKAMOUNT","ADJUSTBOOKVALUE","BUYDATE","LIMITYEAR","USEYEAR","ACCUMDEPR1","CAUSE","SCRAPVALUE1","KIND"};
    	String execSQL = " select c.organAName as enterOrgName " + "\n"
    				   + " 		  ,a.writeDate ,a.caseNO " + "\n"
    				   + " 		  ,substr(b.propertyNo,0,7)||'-'||substr(b.propertyNo,8,3)||'-'||b.serialNo as propertyNo " + "\n"
    				   + " 		  ,d.propertyName ,d.propertyUnit " + "\n"
    				   + " 		  ,b.measure ,b.bookValue " + "\n"
    				   + " 		  ,b.buildDate ,d.limitYear,b.otherLimitYear " + "\n"
    				   + " 		  ,b.useYear||'年'||b.useMonth||'個月' as useYear ,b.deprMethod " + "\n"
    				   + " 		  ,decode(b.accumDepr1,'','0',b.accumDepr1) as accumDepr1 " + "\n"
    				   + " 		  ,(select f.codename from SYSCA_CODE f where f.codekindid = 'CAC' and b.cause=f.codeid)cause ,b.cause1 " + "\n"
    				   + " 		  , decode(b.scrapValue1,'','0',b.scrapValue1) as scrapValue1 " + "\n"
    				   + " 		  ,b.propertyKind ,(select x.codeName from sysca_code x where b.propertyKind = x.codeid and x.codekindid = 'PKD') as propertyKindName " + "\n"
    				   + " 		  ,b.ownership ,(select x.codeName from sysca_code x where b.ownership = x.codeid and x.codekindid = 'OWA') as ownershipName " + "\n"
    				   + " 		  from UNTRF_ReduceProof a,UNTRF_ReduceDetail b,SYSCA_Organ c,SYSPK_PropertyCode d " + "\n"
    				   + " 		  where a.enterorg=b.enterorg and a.caseno=b.caseno and a.ownership=b.ownership and b.propertyNo=d.propertyNo and a.enterOrg=c.organID(+) " + "\n"
    				   + " 		  and d.enterOrg in ('000000000A',a.enterOrg) " + "\n"
    				   + " 		  and d.propertyType='1' " + "\n"
    				   + "";
    	if (!"".equals(Common.get(getQ_ownership()))){
    		execSQL += " and a.ownership = " + util.Common.sqlChar(getQ_ownership());
    	}    	
    	if (!"".equals(getQ_enterOrg())) {
    		execSQL += " and a.enterOrg = " + Common.sqlChar(getQ_enterOrg()) ;
    	} else {
    		if (!getIsAdminManager().equalsIgnoreCase("Y")) {
    			if ("Y".equals(getIsOrganManager())) {					
    				execSQL += " and a.enterOrg like '" + getOrganID().substring(0,5) + "%' ";
    			} else {
    				execSQL+=" and a.enterOrg = " + Common.sqlChar(getOrganID()) ;
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
    	
    	
    	execSQL += " order by a.caseno,b.propertyno,b.serialno";
    	
    	String kindName[]={"第一聯","第二聯","第三聯"};
    	String tansToDouble;
    	String DateTrn;
    	String causetemp;
    	String limitYeartemp;
    	String DeprMethod;

		//j ,i ,k 為報表收執第幾聯之判斷
		int j ,i ,k;
		if("4".equals(q_kind)){
			i = 3 ;
			j = 1 ;
		}else{
			i = Integer.parseInt(q_kind);
			j = Integer.parseInt(q_kind);
		}
		
        ResultSet rs = null ;
        Vector rowData = new Vector();
		for(k=j ;k<=i ;k++){
			rs = db.querySQL(execSQL);
    		    while(rs.next()){
            	   Object[] data = new Object[17];
            	   data[0] = rs.getString("ownershipName");
                   data[1] = rs.getString("enterOrgName");
                   data[2] = Common.MaskCDate(rs.getString("writeDate"));
            	   data[3] = rs.getString("caseNO")+"　";
                   data[4] = rs.getString("propertyNo");
                   data[5] = rs.getString("propertyName");
                   data[6] = rs.getString("propertyUnit");
                   data[7] = null;
                   tansToDouble = rs.getString("measure");
                   if(tansToDouble!=null){
                	   data[8]= new Double(Double.parseDouble(tansToDouble));
                   }else{
                	   data[8]=null;
                   }
                   tansToDouble = rs.getString("bookValue");
                   if(tansToDouble!=null){
                	   data[9]= new Double(Double.parseDouble(tansToDouble));
                   }else{
                	   data[9]=null;
                   }
            	   DateTrn = rs.getString("buildDate");
            	   if(DateTrn!=null){
            		   data[10]=DateTrn.substring(0,3)+"/"+DateTrn.substring(3,5)+"/"+DateTrn.substring(5,7);
            	   }else{
            		   data[10]=null;
            	   }            	
            	   data[11] = rs.getString("limitYear");
            	   limitYeartemp=rs.getString("otherLimitYear");;
            	   if(data[11]==null){
            		   data[11]=limitYeartemp;
            	   }            	
            	   data[12] = rs.getString("useYear");
            	   DeprMethod = rs.getString("deprMethod");
            	   data[13]= new Double(rs.getString("accumDepr1"));
            	   if("1".equals(DeprMethod)){
            		   data[13]="";
            	   }            	
                   data[14] = rs.getString("cause");
                   causetemp= rs.getString("cause1");
                   if("其他".equals(data[14])||data[14]==null){
                	   data[14]=causetemp;
                   }               
                   data[15]= new Double(rs.getString("scrapValue1"));
            	   if("1".equals(DeprMethod)){
            		   data[15]="";
            	   }            	
                   data[16]= kindName[k-1];
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
