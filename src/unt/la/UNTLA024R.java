/*
*<br>程式目的：國有土地增減值單報表檔 
*<br>程式代號：untla024r
*<br>撰寫日期：94/11/11
*<br>程式作者：chris
*<br>--------------------------------------------------------
*<br>修改作者　　修改日期　　　修改目的
*<br>--------------------------------------------------------
*/

package unt.la;

import java.sql.ResultSet;
import java.util.Vector;

import javax.swing.table.DefaultTableModel;

import util.*;

public class UNTLA024R extends SuperBean{
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

    Double GROUPADJUSTHOLDAREA1=new Double(0);
    Double GROUPADJUSTHOLDAREA2=new Double(0);
    try{
    	while(q_caseNoS!=null&&!"".equals(q_caseNoS)&&q_caseNoS.length()<10){q_caseNoS=q_caseNoS+"0";}
    	while(q_caseNoE!=null&&!"".equals(q_caseNoE)&&q_caseNoE.length()<10){q_caseNoE=q_caseNoE+"9";}
    	String[] columns = new String[] {"ENTERORGNAME","WRITEDATE","WRITEUNIT","PROOFNO","CASENO"
    									,"SOURCEDATE","PROPERTYNO","PROPERTYNAME","CAUSE","NEWHOLDAREA"
    									,"OLDBOOKUNIT","OLDBOOKVALUE","ADJUSTBOOKUNIT1","ADJUSTBOOKVALUE1"
    									,"ADJUSTBOOKUNIT2","ADJUSTBOOKVALUE2","NEWBOOKUNIT","NEWBOOKVALUE"
    									,"KIND","UNIT","summonsNo","propertyKind","propertyKindName","ownershipTitle","signname","OLDHOLDAREA","ADJUSTHOLDAREA1","ADJUSTHOLDAREA2"
    									};
    	
    	String execSQL = " select (select c.organAName from SYSCA_Organ c where a.enterOrg = c.organID) as enterOrgName ,a.writeDate ,a.writeUnit " 
    				   + " ,a.proofDoc, a.proofNo ,a.caseNO " 
    				   + " ,b.sourceDate ,b.propertyNo ,b.serialNo " 
    				   + " ,(select x.propertyName from SYSPK_PropertyCode x where b.propertyNo = x.propertyNo and x.propertyType='1' and x.enterOrg in ('000000000A',a.enterOrg)) as propertyName " 
    				   + " ,b.cause ,b.cause1 ,b.newHoldArea ,b.oldBookUnit ,b.oldBookValue "  
    				   + " ,b.adjustBookUnit ,b.adjustBookValue ,b.newBookUnit ,b.newBookValue ,a.summonsNo " 
    				   + " ,b.propertyKind ,(select x.codeName from sysca_code x where b.propertyKind = x.codeid and x.codekindid = 'PKD') as propertyKindName " 
    				   + " ,b.ownership ,(select x.codeName from sysca_code x where b.ownership = x.codeid and x.codekindid = 'OWA') as ownershipName " 
    				   + " ,b.signNo" 
    				   + " ,b.oldHoldArea,b.Adjustholdarea" 
    				   + " from UNTLA_AdjustProof a "
    				   + " left join UNTLA_AdjustDetail b on a.enterorg = b.enterorg and a.caseno = b.caseno and a.ownership = b.ownership " 
    				   + " where 1=1";
    	
    	if (!"".equals(getQ_enterOrg())) {
    		execSQL+=" and a.enterOrg = " + Common.sqlChar(getQ_enterOrg()) ;
    	} else {
    		if (!getIsAdminManager().equalsIgnoreCase("Y")) {
    			if ("Y".equals(getIsOrganManager())) {					
    				//execSQL += " and a.enterOrg like '" + getOrganID().substring(0,5) + "%' ";
    				execSQL += " and ( a.enterOrg = "+ Common.sqlChar(getOrganID()) +" or a.enterOrg in ( select organID from SYSCA_Organ where organSuperior = "+ Common.sqlChar(getOrganID()) +" ) ) ";
    			} else {
    				execSQL+=" and a.enterOrg = " + Common.sqlChar(getOrganID()) ;
    			}
    		}
    	}
    	if (!"".equals(Common.get(getQ_ownership())))
    		execSQL += " and a.ownership = " + util.Common.sqlChar(getQ_ownership());
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
//System.out.println("-- untla024r getResultModel --\n"+execSQL); 	
    	String kindName[]={"第一聯","第二聯","第三聯"};
    	String causetemp="";
    	//String DateTrn;
    	String tansToDouble;
    	double Doubletemp;
  
    	int j=0 ,k=0 ,l=0 ;
    	if("4".equals(q_kind)){
    		k = 1;
    		l = 3;
    	}else{
    		k = Integer.parseInt(q_kind);
    		l = Integer.parseInt(q_kind);
    	}
    	
    	Vector rowData = new Vector();
    	for(j=k;j<=l;j++){
		
    		ResultSet rs = db.querySQL(execSQL);
    		while(rs.next()){
            	Object[] data = new Object[28];
            	data[0] = rs.getString("enterOrgName");
            	data[1]=Common.MaskCDate(rs.getString("writeDate"));
                data[2] = rs.getString("writeUnit");
                data[3] = rs.getString("proofDoc") + "字第" + rs.getString("proofNo") + "號";
                data[4] = rs.getString("caseNO")+"　";
            	data[5]=Common.MaskDate(rs.getString("sourceDate"));
            	data[6] = rs.getString("propertyNo");
                data[7] = rs.getString("propertyName");
                data[8] = rs.getString("cause");
                causetemp= rs.getString("cause1");
                if("1".equals(data[8]))data[8]="公告地價調整";
                else if("2".equals(data[8]))data[8]="資產重估調整";
                else if("4".equals(data[8]))data[8]="面積調整";
                else data[8]=causetemp;
                
                //data[9]= Common.areaFormat(rs.getString("newHoldArea"))+"　";
                //tansToDouble= Common.areaFormat(rs.getString("newHoldArea"));
                if(!"".equals(checkGet(rs.getString("newHoldArea"))))
                	data[9]= new Double(Double.parseDouble(rs.getString("newHoldArea")));
                else 
                	data[9]=null;
            
                tansToDouble= rs.getString("oldBookUnit");
                if(tansToDouble!=null)data[10]= new Double(Double.parseDouble(tansToDouble));
                else data[10]=null;
                tansToDouble= rs.getString("oldBookValue");
                if(tansToDouble!=null)data[11]= new Double(Double.parseDouble(tansToDouble));
                else data[11]=null;
                tansToDouble= rs.getString("adjustBookUnit");
                if(tansToDouble!=null){
                	Doubletemp= Double.parseDouble(tansToDouble);
                	if(Doubletemp<0){
                		data[14]=new Double(Math.abs(Doubletemp));data[12]=new Double(0);             		
                	}else {
                		data[12]=new Double(Math.abs(Doubletemp));data[14]=new Double(0);                		
                	}
                }
                else {
                	data[12]=null;data[14]=null;
                }
                tansToDouble= rs.getString("adjustBookValue");
                if(tansToDouble!=null){
                	Doubletemp= Double.parseDouble(tansToDouble);
                	if(Doubletemp<0){
                		data[15]=new Double(Math.abs(Doubletemp));data[13]=new Double(0);
                	}else {
                		data[13]=new Double(Math.abs(Doubletemp));data[15]=new Double(0);                
                	}
                }
                else {
                	data[13]=null;data[15]=null;
                }
                tansToDouble= rs.getString("newBookUnit");
                if(tansToDouble!=null){
                	data[16]= new Double(Double.parseDouble(tansToDouble));
                }
                else data[16]=null;
                tansToDouble= rs.getString("newBookValue");
                if(tansToDouble!=null)data[17]= new Double(Double.parseDouble(tansToDouble));
                else data[17]=null;
                data[18]=kindName[j-1];
                data[19]="平方\n公尺";
                data[20]=Common.get(rs.getString("summonsNo"))+"　";
                data[21] = Common.get(rs.getString("propertyKind"));
                data[22] = Common.get(rs.getString("propertyKindName"));
                data[23] = Common.get(rs.getString("ownershipName"))+"財產　 值單";
                data[24] = ("".equals(checkGet(rs.getString("signNo")))?"":Common.get(getSignDescName(rs.getString("signNo").substring(0,7))));
                
                if(!"".equals(checkGet(rs.getString("oldHoldArea"))))
                	data[25]= new Double(Double.parseDouble(rs.getString("oldHoldArea")));
                else 
                	data[25]=null;
                
                if(!"".equals(checkGet(rs.getString("Adjustholdarea")))){
                	if(Double.parseDouble(rs.getString("adjustBookValue")) > 0){
                		data[26]= new Double(Double.parseDouble(rs.getString("Adjustholdarea")));
                	}else if(Double.parseDouble(rs.getString("adjustBookValue")) < 0){
                		data[27]= new Double(Double.parseDouble(rs.getString("Adjustholdarea")));
                	}else{
                		if(Double.parseDouble(rs.getString("Adjustholdarea")) > 0){
                			data[26]= new Double(Double.parseDouble(rs.getString("Adjustholdarea")));
	                	}else if(Double.parseDouble(rs.getString("Adjustholdarea")) < 0){
	                		data[27]= new Double(Double.parseDouble(rs.getString("Adjustholdarea")));
	                	}
                	}
                }else{
                	data[26]=null;
                	data[27]=null;
                }
                rowData.addElement(data);
             }
    		}//for
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


private String getSignDescName(String signNo){
	Database db = null;
	ResultSet rs = null;
	String sql = null;
	String result = null;
	try{
		sql = "select signdesc from SYSCA_SIGN where" +
				" signNo = " + Common.sqlChar(signNo);
		
		db = new Database();
		rs = db.querySQL(sql);
		if(rs.next()){				
			result = rs.getString("signdesc");
		}
		rs.close();
	}catch(Exception e){
		System.out.println("getSignDescName Exception => " + e.getMessage());
	}finally{
		db.closeAll();
	}
	return result;
}
}
