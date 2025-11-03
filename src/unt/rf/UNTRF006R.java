/*
*<br>程式目的：國有土地改良物增加單報表檔 
*<br>程式代號：untrf006R
*<br>撰寫日期：94/11/04
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

public class UNTRF006R extends SuperBean{
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
    	
    	String[] columns = new String[] {"ENTERORGNAME","WRITEDATE","WRITEUNIT","PROOFNO","CASENO","MANAGENO"
    									,"SOURCEDATE","PROPERTYNO","PROPERTYNAME","SIGNNO","SOURCEKIND"
    									,"PROPERTYUNIT","MEASURE","ORIGINALBV","SCRAPVALUE","LIMITYEAR"
    									,"DEPRMETHOD","KIND","summonsNo","accountingTitle","propertyKind"
    									,"propertyKindName","ownershipTitle","propertyName1"};
    	
    	String execSQL = " select c.organAName as enterOrgName ,a.writeDate ,a.writeUnit " + "\n"
    				   + " 		 ,a.proofDoc||'字第'||a.proofNo||'號' as proofNo ,a.caseNO ,a.manageNo ,b.sourceDate " + "\n"
    				   + " 		 ,substr(b.propertyNo,0,7)||'-'||substr(b.propertyNo,8,3)||'-'||b.serialNo as propertyNo ,d.propertyName " + "\n"
    				   + " 		 ,((select  k.signDesc||substr(h.signNo,8,4)||'-'||substr(h.signNo,12,4)||'地號' from UNTRF_Base h,SYSCA_Sign k where h.enterOrg=b.enterOrg and h.ownership=b.ownership and h.propertyNo=b.propertyNo and h.serialNo=b.serialNo and substr(h.signNo,1,7)=k.signNo and rownum=1 )||'等'|| (select count(*) from UNTRF_Base x where x.enterOrg=b.enterOrg and x.ownership=b.ownership and x.propertyNo=b.propertyNo and x.serialNo=b.serialNo) ||'筆') as signNo " + "\n" 
    				   + " 		 ,(select f.codename from SYSCA_CODE f where f.codekindid = 'SKB' and b.sourceKind=f.codeid)sourceKind,d.propertyUnit,b.originalMeasure,b.originalBV,b.scrapValue,nvl(d.limitYear,b.otherLimitYear)limitYear,b.deprMethod " + "\n"
    				   + " 		 ,a.summonsNo, b.accountingTitle " + "\n"
    				   + " 		 ,b.propertyKind, b.propertyName1 " + "\n"
    				   + " 		 ,b.propertyKind ,(select x.codeName from sysca_code x where b.propertyKind = x.codeid and x.codekindid = 'PKD') as propertyKindName " + "\n"
    				   + " 		 ,b.ownership ,(select x.codeName from sysca_code x where b.ownership = x.codeid and x.codekindid = 'OWA') as ownershipName " + "\n"
    				   + " 		 from UNTRF_AddProof a ,UNTRF_Attachment b ,SYSCA_Organ c ,SYSPK_PropertyCode d " + "\n"
    				   + " 		 where 1=1 " + "\n"
    				   + " 		 and a.enterorg = b.enterorg and a.caseno = b.caseno and a.ownership = b.ownership " + "\n" 
    				   + " 		 and b.propertyNo = d.propertyNo " + "\n" 
    				   + " 		 and a.enterOrg = c.organID(+) " + "\n"
    				   + " 		 and d.enterOrg in ('000000000A',a.enterOrg) " + "\n"
    				   + " 		 and d.propertyType='1' " + "\n"
    				   + "";

    	if (!"".equals(getQ_enterOrgName())) {
    		execSQL += " and a.enterOrg = " + Common.sqlChar(getQ_enterOrg()) ;
    	} else {
    		if (!getIsAdminManager().equalsIgnoreCase("Y")) {
    			if ("Y".equals(getIsOrganManager())) {					
    				execSQL += " and( a.enterOrg = " + Common.sqlChar(getOrganID()) ;
    				execSQL += " or organID in (select organID from SYSCA_Organ where organSuperior='"+ Common.sqlChar(getOrganID())+"'))";	
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
;
    	String kindName[]={"第一聯","第二聯","第三聯"};
    	String tansToDouble;

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
            	Object[] data = new Object[24];
            	data[0] = rs.getString("enterOrgName");
            	data[1] = Common.MaskCDate(rs.getString("writeDate"));
            	data[2] = rs.getString("writeUnit");
                data[3] = rs.getString("proofNo")+"　";
                data[4] = rs.getString("caseNO")+"　";
                data[5] = Common.get(rs.getString("manageNo"))+"　";
                data[6] = Common.MaskDate(rs.getString("sourceDate"));
            	data[7] = rs.getString("propertyNo");
                data[8] = rs.getString("propertyName");
                data[9] = rs.getString("signNo");
                if("等0筆".equals(data[9])){
                	data[9]="";
                }
                
                data[10] = rs.getString("sourceKind");
                data[11] = rs.getString("propertyUnit");
                tansToDouble= rs.getString("originalMeasure");
                if(tansToDouble!=null){
                	data[12]= new Double(Double.parseDouble(tansToDouble));
                }else{
                	data[12]=null;
                }
                tansToDouble= rs.getString("originalBV");
                if(tansToDouble!=null)data[13]= new Double(Double.parseDouble(tansToDouble));
                else data[13]=null;
                data[14]= Common.valueFormat(rs.getString("scrapValue"));
                data[15] = rs.getString("limitYear");
                data[16] = rs.getString("deprMethod");
                if("02".equals(data[16]))data[16]="直線法";
                else if("03".equals(data[16]))data[16]="直線法";
                else if("04".equals(data[16]))data[16]="直線法";
                else if("05".equals(data[16]))data[16]="報廢法";
                else data[16]="";
                data[17] = kindName[k-1];
                data[18] = Common.get(rs.getString("summonsNo"))+"　";
                data[19] = rs.getString("accountingTitle");
                data[20] = Common.get(rs.getString("propertyKind"));
                data[21] = Common.get(rs.getString("propertyKindName"));
                data[22] = Common.get(rs.getString("ownershipName"))+"財產增加單";
                data[23] = Common.get(rs.getString("propertyName1"));
                
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

}