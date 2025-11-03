/*
*<br>程式目的：國有建物減損單報表檔 
*<br>程式代號：untbu020r
*<br>撰寫日期：94/11/09
*<br>程式作者：chris
*<br>--------------------------------------------------------
*<br>修改作者　　修改日期　　　修改目的
*<br>--------------------------------------------------------
*/
package unt.bu;

import java.sql.ResultSet;
import java.util.Vector;

import javax.swing.table.DefaultTableModel;

import util.*;

public class UNTBU020R extends SuperBean{
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
    	String[] columns = new String[] {"ENTERORGNAME","WRITEDATE","WRITEUNIT","PROOFNO","CASENO"
    									,"SOURCEDATE","PROPERTYNO","PROPERTYNAME","SIGNNO","SIGNDESC","PROPERTYUNIT","ADDRNAME"
    									,"BOOKVALUE","CAUSE","LIMITYEAR","USEMONTH","KIND","NUM","summonsNo"
    									,"propertyKind","propertyKindName","ownershipTitle","HOLDAREA"};
    	
    	String execSQL = " select c.organAName as enterOrgName ,a.writeDate ,a.writeUnit ,a.proofDoc, a.proofNo " + "\n" 
		   + " 		 ,a.caseNO ,b.sourceDate  ,b.propertyNo, b.serialNo " + "\n" 
		   + " 		 ,d.propertyName ,b.signNo " + "\n"
		   + " 		 ,d.propertyUnit ,b.bookValue ,(select f.codename from SYSCA_CODE f where f.codekindid = 'CAC' and b.cause=f.codeid)cause " + "\n" 
		   + " 		 ,b.cause1 ,d.limitYear, b.otherLimitYear ,b.useYear, b.useMonth " + "\n" 
		   + " 		 ,a.summonsNo " + "\n" 
		   + " 		 ,b.propertyKind ,(select x.codeName from sysca_code x where b.propertyKind = x.codeid and x.codekindid = 'PKD') as propertyKindName " + "\n"
		   + " 		 ,b.ownership ,(select x.codeName from sysca_code x where b.ownership = x.codeid and x.codekindid = 'OWA') as ownershipName " + "\n"
		   + "       ,b.doorplate1, b.doorplate2, b.doorplate3, b.doorplate4 " + "\n"
           + "       ,(select count(*) from UNTBU_Base c where c.enterOrg = a.enterOrg and c.ownership = a.ownership and c.propertyNo = b.propertyNo and c.serialno = b.serialno) as count " + "\n"
		   + "		 ,b.holdarea"	
		   + " from UNTBU_ReduceProof a left join SYSCA_Organ c on a.enterOrg=c.organID,UNTBU_ReduceDetail b ,SYSPK_PropertyCode d " + "\n" 
		   + " where 1=1 " + "\n"
		   + " and a.enterorg = b.enterorg and a.caseno = b.caseno and a.ownership = b.ownership " + "\n" 
		   + " and b.propertyNo = d.propertyNo " + "\n"
		   + " and d.enterOrg in ('000000000A',a.enterOrg) " + "\n"
		   + " and d.propertyType='1' " + "\n"
		   + "";
    	

    	if (!"".equals(getQ_enterOrg())) {
    		execSQL+= " and a.enterOrg = " + Common.sqlChar(getQ_enterOrg()) ;
    	} else {
    		if (!getIsAdminManager().equalsIgnoreCase("Y")) {
    			if ("Y".equals(getIsOrganManager())) {					
    				execSQL += " and a.enterOrg = '" + getOrganID() + "' ";
    			} else {
    				execSQL += " and a.enterOrg = " + Common.sqlChar(getOrganID()) ;
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
    	execSQL += " order by a.caseno,b.propertyno,b.serialno";
    	
    	String kindName[]={"第一聯","第二聯","第三聯"};
    	String causetemp="";
    	String tansToDouble;
    	String oldCaseNo="";
    	String newCaseNo="";
    	double totalAmount=0;
    	double amount=0;
    	double totalValue=0;
    	double value=0;
    
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
            	Object[] data = new Object[columns.length]; 
            	data[0] = rs.getString("enterOrgName");
                data[1] = Common.MaskCDate(rs.getString("writeDate"));
                data[2] = rs.getString("writeUnit");
                data[3] = rs.getString("proofDoc") + "字第" + rs.getString("proofNo") + "號";
                data[4] = rs.getString("caseNO")+"　";
                oldCaseNo = newCaseNo;
                newCaseNo = rs.getString("caseNO");
                data[5] = Common.MaskDate(rs.getString("sourceDate"));
                data[6] = rs.getString("propertyNo").substring(0,7) + "-" + rs.getString("propertyNo").substring(7) + "-" + rs.getString("serialNo");
                data[7] = rs.getString("propertyName");
                
                data[8] = Common.get(rs.getString("signNo"))+"建號";
                
                
                int t = Integer.parseInt(rs.getString("count"));
                if(t>1){
                	data[9] = Common.get(getSignDescName(rs.getString("signNo").substring(0,7)) + rs.getString("signNo").substring(7,11) + '-' + rs.getString("signNo").substring(11))+"地號等"+rs.getString("count")+"筆";
                }else{
                	data[9]= Common.get(getSignDescName(rs.getString("signNo").substring(0,7)) + rs.getString("signNo").substring(7,11) + '-' + rs.getString("signNo").substring(11))+"地號";
                }
                
                data[10] = rs.getString("propertyUnit");
                
                //彰化縣彰化市文化里01214-001號
//                int i = 0 ,m=0;
//                if(!"".equals(Common.get(rs.getString("addrname")))){
//                	if(rs.getString("addrname").length()>10){
//                	    i = Integer.parseInt(rs.getString("addrname").substring(9,14));
//                        m = Integer.parseInt(rs.getString("addrname").substring(15,18));
//                        data[11] = rs.getString("addrname").substring(0,9)+i+"-"+m+"號";
//                    }else{
//                    	data[11] = rs.getString("addrname").substring(0,9)+"-"+"號";
//                    }
//                }else{
//                	data[11] = "";
//                }
                data[11]=getAddrName(rs.getString("doorplate1"), rs.getString("doorplate2"), rs.getString("doorplate3"), rs.getString("doorplate4"));
                
                
                tansToDouble= rs.getString("bookValue");
                if(tansToDouble!=null){
                	data[12]= new Double(Double.parseDouble(tansToDouble));
                	value = Double.parseDouble(tansToDouble);
                }else{
                	data[12]=null;
                	value = 0;
                }
                data[13] = rs.getString("cause");
                causetemp= rs.getString("cause1");
                if("其他".equals(data[13])||data[13]==null)data[13]=causetemp;
                data[14] = "".equals(rs.getString("LimitYear"))?rs.getString("otherLimitYear"):rs.getString("LimitYear");                
                data[15] = rs.getString("useYear") + "年" + rs.getString("useMonth") + "個月";
                data[16] = kindName[j-1];
                data[17] = new Double(1);
                data[18] = Common.get(rs.getString("summonsNo"))+"　";
                data[19] = Common.get(rs.getString("propertyKind"));
                data[20] = Common.get(rs.getString("propertyKindName"));
                data[21] = Common.get(rs.getString("ownershipName"))+"財產減損單";
                data[22] = Common.get(rs.getString("holdarea"));
                
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

private String getAddrName(String doorplate1, String doorplate2, String doorplate3, String doorplate4){
	String result = "";

	if("".equals(checkGet(doorplate1)) || "".equals(checkGet(doorplate2)) || "".equals(checkGet(doorplate3))){
		
	}else{
		String sql = "select f.cod2_desc from SYSCA_Addr f" +
					" where f.addrid = " + doorplate1.substring(0,3) + doorplate2.substring(3,7) + doorplate3.substring(7,11);
		
		result = queryFromDB(sql) + doorplate4;
	}
	return result;
}


private String getSignDescName(String signNo){
	String result = null;
	
	String sql = "select signdesc from SYSCA_SIGN where" +
				" signNo = " + Common.sqlChar(signNo);
	result = queryFromDB(sql);
	
	return result;
}

	private String queryFromDB(String sql){
		Database db = null;
		ResultSet rs = null;
		String result = "";
		try{
			db = new Database();
			rs = db.querySQL(sql);
			if(rs.next()){
				result = rs.getString(1);
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			db.closeAll();
		}
		return result;
	}
}
