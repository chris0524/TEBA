/*
*<br>程式目的：國有土地增加單報表檔 
*<br>程式代號：UNTLA010R
*<br>撰寫日期：94/11/03 
*<br>程式作者：chris ,
*<br>--------------------------------------------------------
*<br>修改作者　　修改日期　　　修改目的
*	 ------    -------    -----------------------------------
*	 shan	   0980414    Title改為自動取得為國有.市有或縣有之狀態
*<br>--------------------------------------------------------
*/

package unt.la;

import java.sql.ResultSet;
import java.util.Vector;

import javax.swing.table.DefaultTableModel;

import util.*;

public class UNTLA010R extends SuperBean{
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
    	String[] columns = new String[] {"ENTERORGNAME","MERGEDIVISION","WRITEDATE","WRITEUNIT","PROOFNO","CASENO"
    									,"MANAGENO","SOURCEDATE","PROPERTYNO","PROPERTYNAME"
    									,"SIGNNO","SOURCEKIND","ORIGINALUNIT","ORIGINALBV","KIND","NUM","UNIT"
    									,"summonsNo","accountingTitle","propertyKind","propertyKindName","area"
    									,"ownershiptitle"};
    	
    	String sql = " select a.enterorg "
    			   + " 		 ,(select c.organAName from SYSCA_Organ c where a.enterOrg=c.organID) as enterOrgName "
    			   + " 		 ,a.mergeDivision ,a.writeDate ,a.writeUnit " + "\n"
    			   + " 		 ,a.proofDoc, a.proofNo ,a.caseNO ,a.manageNo " + "\n"
    			   + " 		 ,b.sourceDate ,b.propertyNo, b.serialNo " + "\n"
    			   + " 		 ,(select x.propertyName from SYSPK_PropertyCode x where b.propertyNo=x.propertyNo and x.enterOrg in ('000000000A',a.enterOrg) and x.propertyType='1' ) as propertyName " + "\n"
				   + "       ,b.signno "
    			   + " 		 ,(select f.codename from SYSCA_CODE f where f.codekindid = 'SKB' and b.sourceKind=f.codeid)sourceKind " + "\n"
    			   + " 		 ,nvl(b.originalUnit ,0) as originalUnit ,nvl(b.area,0) as area, nvl(b.originalBV ,0) as originalBV " + "\n"
    			   + " 		 ,a.summonsNo, b.accountingTitle ,b.propertyKind ,b.originalHoldArea " + "\n"
    			   + " 		 ,(select codename from sysca_code x where x.codekindid = 'PKD' and x.codeid = b.propertykind ) as propertyKindName " + "\n"
    			   + " 		 ,(select codename from sysca_code x where x.codekindid = 'OWA' and x.codeid = a.ownership ) as ownershipName " + "\n"
    			   + " from UNTLA_AddProof a ,UNTLA_Land b ,SYSPK_PropertyCode d " + "\n"
    			   + " where 1=1 " + "\n"
    			   + " and a.enterorg=b.enterorg and a.caseno=b.caseno and a.ownership=b.ownership " + "\n"
    			   + " and d.enterOrg in ('000000000A',a.enterOrg) " + "\n"
    			   + " and b.propertyNo=d.propertyNo " + "\n"
    			   + " and d.propertyType='1' " + "\n"
    			   + "";
    			   
    	if (!"".equals(getQ_enterOrg())) {
    		sql += " and a.enterOrg = " + Common.sqlChar(getQ_enterOrg()) ;
    	} else {
    		if (!getIsAdminManager().equalsIgnoreCase("Y")) {
    			if ("Y".equals(getIsOrganManager())) {					
    				sql += " and a.enterOrg like '" + getOrganID().substring(0,5) + "%' ";
    			} else {
    				sql += " and a.enterOrg = " + Common.sqlChar(getOrganID()) ;
    			}
    		}
    	}
    	
    	if(!"".equals(getQ_ownership()))
    		sql += " and a.ownership = " + Common.sqlChar(getQ_ownership());
    		
    	if (!"".equals(Common.get(getQ_caseNoS())))
    		sql += " and a.caseNo >= " + util.Common.sqlChar(getQ_caseNoS());
    	if (!"".equals(Common.get(getQ_caseNoE())))
    		sql += " and a.caseNo <= " + util.Common.sqlChar(getQ_caseNoE());
    	if (!"".equals(Common.get(getQ_writeDateS())))
    		sql += " and a.writeDate >= " + util.Common.sqlChar(new Datetime().changeTaiwanYYMMDD(getQ_writeDateS(),"2"));
    	if (!"".equals(Common.get(getQ_writeDateE())))
    		sql += " and a.writeDate <= " + util.Common.sqlChar(new Datetime().changeTaiwanYYMMDD(getQ_writeDateE(),"2"));
    	if (!"".equals(Common.get(getQ_proofDoc()))){
    		sql += " and a.proofDoc like " + util.Common.sqlChar("%"+getQ_proofDoc()+"%");
    	}
    	if (!"".equals(Common.get(getQ_proofNoS())))
    		sql += " and a.proofNo >= " + util.Common.sqlChar(getQ_proofNoS());
    	if (!"".equals(Common.get(getQ_proofNoE())))
    		sql += " and a.proofNo <= " + util.Common.sqlChar(getQ_proofNoE());
    	
    	sql += " order by b.propertyKind,a.caseno,b.propertyno,b.serialno";
    	
    	//System.out.println(sql);
    	String kindName[]={"第一聯","第二聯","第三聯"};
    	
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
    			rs = db.querySQL(sql);
    			while(rs.next()){
	            	Object[] data = new Object[23];
	            	data[0] = rs.getString("enterOrgName");
            		data[1]="合併分割案號:" + Common.get(rs.getString("mergeDivision"));
	                data[2] = Common.MaskCDate(rs.getString("writeDate"));
	                data[3] = rs.getString("writeUnit");
	                data[4] = rs.getString("proofDoc") + "字第" + rs.getString("proofNo") + "號";
	                data[5] = rs.getString("caseNO")+"　";
	                data[6] = Common.get(rs.getString("manageNo"))+"　";
	                data[7] = Common.MaskDate(rs.getString("sourceDate"));
	                data[8] = rs.getString("propertyNo").substring(0,7) + "-" + rs.getString("propertyNo").substring(7) + "-" + rs.getString("serialNo");
	                data[9] = rs.getString("propertyName");
	                if(!"".equals(Common.get(rs.getString("signNo")))){
	                	data[10] = getSignDescName(rs.getString("signNo").substring(0,7)) + rs.getString("signNo").substring(7,11) + "-" + rs.getString("signNo").substring(11) + "地號";;
	                }
	                data[11] = rs.getString("sourceKind");
	                data[12]= new Double(Double.parseDouble(rs.getString("originalUnit")));
	                data[13]= new Double(Double.parseDouble(rs.getString("originalBV")));
	                data[14] = kindName[k-1];
	                data[15] = "1";
	                data[16] = "平方公尺";
	                data[17] = Common.get(rs.getString("summonsNo"))+"　";
	                data[18] = rs.getString("accountingTitle");
	                data[19] = Common.get(rs.getString("propertyKind"));
	                data[20] = Common.get(rs.getString("propertyKindName"));
	                data[21] = new Double(Common.get(rs.getString("originalHoldArea")));
	                data[22] = rs.getString("ownershipName") + "土地增加單";
	                rowData.addElement(data);
    			}//wheile
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
