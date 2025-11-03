/*
*<br>程式目的：國有建物增加單報表檔 
*<br>程式代號：untbu012p
*<br>撰寫日期：94/11/01
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

public class UNTBU012R extends SuperBean{
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
    									,"SOURCEDATE","PROPERTYNO","PROPERTYNAME","SIGNNO","SOURCEKIND","PROPERTYUNIT"
    									,"ORIGINALBV","SCRAPVALUE","LIMITYEAR","DEPRMETHOD","KIND","NUM","summonsNo"
    									,"accountingTitle","propertyKind","propertyKindName","ownershipTitle","DOORPLATE","signdesc","fundsSource"
    									,"AREA"};

    	String execSQL = " select b.propertyno as ppno2 , b.serialNo, c.organAName as enterOrgName ,a.writeDate ,a.writeUnit " + "\n" 
    				   + " 		 ,a.proofDoc, a.proofNo " + "\n" 
    				   + " 		 ,a.caseNO ,a.manageNo ,b.sourceDate " + "\n" 
    				   + " 		 ,b.propertyNo, b.serialNo " + "\n" 
    				   + " 		 ,d.propertyName ,b.signNo " + "\n" 
    				   + " 		 ,(select x.codename from sysca_code x where x.codekindid = 'SKB' and b.sourceKind = x.codeid) as sourceKind " + "\n" 
    				   + " 		 ,d.propertyUnit ,b.originalBV ,b.scrapValue " + "\n" 
    				   + " 		 ,d.limitYear ,b.otherLimitYear " + "\n" 
    				   + " 		 ,b.deprMethod ,a.summonsNo ,b.accountingTitle " + "\n" 
    				   + " 		 ,b.propertyKind ,(select x.codeName from sysca_code x where b.propertyKind = x.codeid and x.codekindid = 'PKD') as propertyKindName " + "\n" 
    				   + " 		 ,b.ownership ,(select x.codeName from sysca_code x where b.ownership = x.codeid and x.codekindid = 'OWA') as ownershipName " + "\n" 
    				   + " 		 ,(select k1.addrname from sysca_addr k1 where k1.addrkind='1' and k1.addrid=b.doorPlate1) as addrname1" + "\n" 
    				   + " 		 ,(select l1.addrname from sysca_addr l1 where l1.addrkind='2' and l1.addrid=b.doorPlate2) as addrname2" + "\n"
    				   + " 		 ,(select m1.addrname from sysca_addr m1 where m1.addrkind='3' and m1.addrid=b.doorPlate3) as addrname3 " + "\n"
    				   + " 		 ,b.doorPlate4 as addrname4 " + "\n"
    				   + "		 ,(select x.codeName from SYSCA_Code x where x.codeid = b.fundsSource and codeKindID='FSO') as fundsSource " + "\n"
    				   + "       ,(select count(*) from  UNTBU_Base x where x.enterorg = a.enterorg and x.ownership = a.ownership and x.propertyno = b.propertyno and x.serialno = b.serialno) as signdescCount " + "\n"
    				   + " 		 ,b.area"
    				   + " 		 from UNTBU_AddProof a left join SYSCA_Organ c on a.enterOrg=c.organID,UNTBU_Building b ,SYSPK_PropertyCode d " + "\n"  //, UNTBU_Base f
    				   + " 		 where 1=1 " + "\n" 
    				   + " 		 and a.enterorg = b.enterorg and a.caseno = b.caseno and a.ownership = b.ownership " + "\n" 
    				   + " 		 and b.propertyNo = d.propertyNo " + "\n" 
    				   + " 		 and d.enterOrg in ('000000000A',a.enterOrg)  and d.propertyType='1' " + "\n" 
    				   + "";
    	// 坐落地號
    	String execSQL2 = " select f.signno from UNTBU_Base f where 1=1 ";
    	
    	if (!"".equals(getQ_enterOrg())) {
    		execSQL +=" and a.enterOrg = " + Common.sqlChar(getQ_enterOrg()) ;
    		execSQL2+=" and f.enterOrg = " + Common.sqlChar(getQ_enterOrg()) ;
    	} else {
    		if (!getIsAdminManager().equalsIgnoreCase("Y")) {
    			if ("Y".equals(getIsOrganManager())) {					
    				//execSQL += " and a.enterOrg like '" + getOrganID().substring(0,5) + "%' ";
    				execSQL += " and ( a.enterOrg = "+ Common.sqlChar(getOrganID()) +" or a.enterOrg in ( select organID from SYSCA_Organ where organSuperior = "+ Common.sqlChar(getOrganID()) +" ) ) ";
    			} else {
    				execSQL += " and a.enterOrg = " + Common.sqlChar(getOrganID()) ;
    			}
    		}
    	}
    	if (!"".equals(Common.get(getQ_ownership())))
    		execSQL +=" and a.ownership = " + util.Common.sqlChar(getQ_ownership());
    		execSQL2+=" and f.ownership = " + util.Common.sqlChar(getQ_ownership());
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

//System.out.println("-- untbu012r getResultModel --\n"+execSQL);
	String kindName[]={"第一聯","第二聯","第三聯"};
    String tansToDouble;
	String DateTrn;
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
        	Object[] data = new Object[27];
        	data[0] = rs.getString("enterOrgName");
        	data[1] = Common.MaskCDate(rs.getString("writeDate"));
        	data[2] = rs.getString("writeUnit");
            data[3] = rs.getString("proofDoc") + "字第" + rs.getString("proofNo") + "號";
            data[4] = rs.getString("caseNO")+"　";
            oldCaseNo = newCaseNo;
            newCaseNo = rs.getString("caseNO");
            data[5] = Common.get(rs.getString("manageNo"))+"　";
            DateTrn = rs.getString("sourceDate");
            if(DateTrn!=null)data[6]=DateTrn.substring(0,3)+"/"+DateTrn.substring(3,5)+"/"+DateTrn.substring(5,7);
        	else data[6]=null;
            data[7] = rs.getString("propertyNo");
            data[8] = rs.getString("propertyName");
            data[9] = getSignDescName(rs.getString("signNo").substring(0,7)) + rs.getString("signNo").substring(7,12) + '-' + rs.getString("signNo").substring(12) + "建號";
            if("-建號".equals(data[9]))data[9]="";
            data[10] = rs.getString("sourceKind");
            data[11] = rs.getString("propertyUnit");
            tansToDouble= rs.getString("originalBV");
            if(tansToDouble!=null){
            	data[12]= new Double(Double.parseDouble(tansToDouble));
            	value = Double.parseDouble(tansToDouble);
            }else{
            	data[12]=null;
            	value = 0;
            }
            tansToDouble= rs.getString("scrapValue");
            if(tansToDouble!=null)data[13]= new Double(Double.parseDouble(tansToDouble));
            else data[13]=null;
            data[14] = "".equals(Common.get(rs.getString("limitYear")))?Common.get(rs.getString("otherLimitYear")):Common.get(rs.getString("limitYear"));               
            data[15] = Common.get(rs.getString("deprMethod"));
            if("02".equals(data[15]))data[15]="直線法";
            else if("03".equals(data[15]))data[15]="直線法";
            else if("04".equals(data[15]))data[15]="直線法";
            else if("05".equals(data[15]))data[15]="報廢法";
            else data[15]="";
            data[16] = kindName[j-1];
            data[17] = new Double(1);
            data[18] = Common.get(rs.getString("summonsNo"))+"　";
            data[19] = Common.get(rs.getString("accountingTitle"));
            data[20] = Common.get(rs.getString("propertyKind"));
            data[21] = Common.get(rs.getString("propertyKindName"));
            data[22] = rs.getString("ownershipName")+"建物增加單";
            data[23] = Common.get(rs.getString("addrname1") + rs.getString("addrname2") + rs.getString("addrname3") + rs.getString("addrname4"));
            
            int t = Integer.parseInt(rs.getString("signdescCount"));
            
            execSQL2 += " and  f.propertyno = " + Common.sqlChar(rs.getString("ppno2")) + " and f.serialNo = "+ Common.sqlChar(rs.getString("serialNo"));
//System.out.println("---------- "+execSQL2);
            ResultSet rs2 = db.querySQL(execSQL2);
            
            if(t>1){          	           	 
            	 if(rs2.next()){
            		 data[24] = getSignDescName(rs2.getString("signNo").substring(0,7)) + rs2.getString("signNo").substring(8,12) + '-' + rs2.getString("signNo").substring(12) + "地號" + "等" + rs.getString("signdescCount") + "筆";
            	 }
            }else{
            	if(rs2.next()){
            		data[24] = Common.get(getSignDescName(rs2.getString("signNo").substring(0,7)));
            	}
            }

            data[25] = rs.getString("fundsSource");            
            data[26] = rs.getDouble("AREA");
            
            rowData.addElement(data);
         }//while
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
