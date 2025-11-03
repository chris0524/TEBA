/*
*<br>程式目的：市有土地增加單報表檔 
*<br>程式代號：untla009r
*<br>撰寫日期：94/11/02
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

public class UNTLA009R extends SuperBean{
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
    	String[] columns = new String[] {"ENTERORGNAME","WRITEDATE","WRITEUNIT","PROOFNO","MERGEDIVISION","CASENO","MANAGENO","PROPERTYKIND","VALUABLE","SOURCEDATE","PROPERTYNO","PROPERTYNAME","SIGNNO","ORIGINALAREA","ORIGINALHOLDAREA","ORIGINALHOLDNUME","ORIGINALBASIS","ORIGINALUNIT","ORIGINALBV","USESEPARATE","PROOFDOC","SOURCEKIND","USESTATE","KIND","summonsNo","accountingTitle"};
    	String execSQL="select (select c.organAName from SYSCA_Organ c where a.enterOrg=c.organID) as enterOrgName,a.writeDate,a.writeUnit,a.proofDoc, a.proofNo,a.mergeDivision,a.caseNO,a.manageNo,b.propertyKind,b.valuable,b.sourceDate,b.propertyNo, b.serialNo,d.propertyName, b.signno,b.originalArea,b.originalHoldArea,b.originalHoldNume, b.originalHoldDeno," +
		    			" (case b.originalBasis when '1' then '公告地價' when '2' then '公告現值' when '3' then '取得價格' else '' end) as originalBasis," +
		    			" b.originalUnit, b.originalBV, b.accountingTitle, a.summonsNo, " +
		    			" (select h.codename from SYSCA_CODE h where h.codekindid = 'SEP' and b.useSeparate=h.codeid)useSeparate," +
		    			" b.proofDoc," +
		    			" (select f.codename from SYSCA_CODE f where f.codekindid = 'SKB' and b.sourceKind=f.codeid)sourceKind," +
		    			" (case b.cause when '499' then b.cause1 else (select f.codename from SYSCA_CODE f where f.codekindid = 'CAA' and b.cause=f.codeid) end)causeName, " +
		    			" (select g.codename from SYSCA_CODE g where g.codekindid = 'UST' and b.useState=g.codeid)useState "+
		    			" from UNTLA_AddProof a,UNTLA_Land b,SYSCA_Organ c,SYSPK_PropertyCode d  "+
		    			" where a.enterorg=b.enterorg and a.caseno=b.caseno and a.ownership=b.ownership and b.propertyNo=d.propertyNo "+
		    			" and a.ownership = '3' "+
		    			" and d.enterOrg in ('000000000A',a.enterOrg) "+
		    			" and d.propertyType='1' ";
    	if (!"".equals(getQ_enterOrg())) {
    		execSQL+=" and a.enterOrg = " + Common.sqlChar(getQ_enterOrg()) ;
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
    	execSQL += " order by a.caseno,b.valuable,b.propertykind,b.propertyno,b.serialno";
    	
    	//System.out.println(execSQL);
    	
    	String kindName[]={"第一聯","第二聯","第三聯"};
    	String tansToDouble;
    	
    	
    	if(!"4".equals(q_kind)){
    		if("3".equals(q_kind))q_kind=kindName[2];
    		else if("2".equals(q_kind))q_kind=kindName[1];
    		else q_kind=kindName[0];
        ResultSet rs = db.querySQL(execSQL);
        Vector rowData = new Vector();
        while(rs.next()){
        	Object[] data = new Object[26];
        	data[0] = rs.getString("enterOrgName");
        	data[1] = Common.MaskCDate(rs.getString("writeDate"));
            data[2] = rs.getString("writeUnit");
            data[3] = rs.getString("proofDoc") + "字第" + rs.getString("proofNo") + "號";
            data[4] = rs.getString("mergeDivision");
            if(data[4]!=null)data[4]="合併分割案號:"+data[4];
            data[5] = rs.getString("caseNO")+"　";
            data[6] = Common.get(rs.getString("manageNo"))+"　";
            data[7] = rs.getString("propertyKind");
            if("01".equals(data[7]))data[7]="公務用";
            else if("02".equals(data[7]))data[7]="公共用";
            else if("03".equals(data[7]))data[7]="事業用";
            else data[7]="非公用";
            data[8] = rs.getString("valuable");
            if("Y".equals(data[8]))data[8]="珍貴財產";
            else data[8]="";
            data[9] = Common.MaskDate(rs.getString("sourceDate"));
            data[10] = rs.getString("propertyNo").substring(0,7) + "-" + rs.getString("propertyNo").substring(7) + "-" + rs.getString("serialNo");
            data[11] = rs.getString("propertyName");
            data[12] = getSignDescName(rs.getString("signNo").substring(0,7)) + rs.getString("signNo").substring(7,11) + "-" + rs.getString("signNo").substring(11) + "地號";
            if("-地號".equals(data[12]))data[12]="";
            data[13]= rs.getString("originalArea")+"　";
            data[14]= new Double(rs.getString("originalHoldArea"));
            data[15] = rs.getString("originalHoldNume") + "/" + rs.getString("originalHoldDeno");
            data[16] = rs.getString("originalBasis");
            tansToDouble= rs.getString("originalUnit");
            if(tansToDouble!=null)data[17]= new Double(Double.parseDouble(tansToDouble));
            else data[17]=null;
            tansToDouble= rs.getString("originalBV");
            if(tansToDouble!=null)data[18]= new Double(Double.parseDouble(tansToDouble));
            else data[18]=null;
            data[19] = rs.getString("useSeparate");
            data[20] = rs.getString("proofDoc");
            if(data[20]==null||"".equals(data[20]))data[20]="免繕狀";
            data[21] = rs.getString("causeName");
            data[22] = rs.getString("useState");
            data[23] = q_kind;
            data[24] = Common.get(rs.getString("summonsNo"))+"　";
            data[25] = rs.getString("accountingTitle");
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
            	Object[] data = new Object[26];
            	//Object[] temp = new Object[30];
            	data[0] = rs.getString("enterOrgName");
            	data[1] = Common.MaskCDate(rs.getString("writeDate"));
                data[2] = rs.getString("writeUnit");
                data[3] = rs.getString("proofNo");
                data[4] = rs.getString("mergeDivision");
                if(data[4]!=null)data[4]="合併分割案號:"+data[4];
                data[5] = rs.getString("caseNO")+"　";
                data[6] = Common.get(rs.getString("manageNo"))+"　";
                data[7] = rs.getString("propertyKind");
                if("01".equals(data[7]))data[7]="公務用";
                else if("02".equals(data[7]))data[7]="公共用";
                else if("03".equals(data[7]))data[7]="事業用";
                else data[7]="非公用";
                data[8] = rs.getString("valuable");
                if("Y".equals(data[8]))data[8]="珍貴財產";
                else data[8]="";
                data[9] = Common.MaskDate(rs.getString("sourceDate"));
                data[10] = rs.getString("propertyNo");
                data[11] = rs.getString("propertyName");
                data[12] = rs.getString("signNo");
                if("-地號".equals(data[12]))data[12]="";
                data[13]= Common.areaFormat(rs.getString("originalArea"))+"　";
                data[14]= new Double(rs.getString("originalHoldArea"));
                data[15] = rs.getString("originalHoldNume");
                data[16] = rs.getString("originalBasis");
                tansToDouble= rs.getString("originalUnit");
                if(tansToDouble!=null)data[17]= new Double(Double.parseDouble(tansToDouble));
                else data[17]=null;
                tansToDouble= rs.getString("originalBV");
                if(tansToDouble!=null)data[18]= new Double(Double.parseDouble(tansToDouble));
                else data[18]=null;
                data[19] = rs.getString("useSeparate");
                data[20] = rs.getString("proofDoc");
                if(data[20]==null||"".equals(data[20]))data[20]="免繕狀";
                data[21] = rs.getString("causeName");
                data[22] = rs.getString("useState");
                data[23] = q_kind;
                data[24] = Common.get(rs.getString("summonsNo"))+"　";
                data[25] = rs.getString("accountingTitle");
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
