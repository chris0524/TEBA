

/*
*<br>程式目的：建物移交清冊報表檔 
*<br>程式代號：untbu018r
*<br>撰寫日期：94/11/9
*<br>程式作者：Chris
*<br>--------------------------------------------------------
*<br>修改作者　　修改日期　　　修改目的
*<br>--------------------------------------------------------
*/

package unt.bu;

import java.sql.ResultSet;
import java.util.Vector;

import javax.swing.table.DefaultTableModel;

import util.*;

public class UNTBU018R extends SuperBean{


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

public DefaultTableModel getResultModel() throws Exception{
    DefaultTableModel model = new javax.swing.table.DefaultTableModel();
    Database db = new Database();
    try{
    	while(q_caseNoS!=null&&!"".equals(q_caseNoS)&&q_caseNoS.length()<10){q_caseNoS=q_caseNoS+"0";}
    	while(q_caseNoE!=null&&!"".equals(q_caseNoE)&&q_caseNoE.length()<10){q_caseNoE=q_caseNoE+"9";}
    	
    	String[] columns = new String[] {"OWNERSHIP","ENTERORGNAME","WRITEDATE","WRITEUNIT"
    									,"PROOFNO","NEWENTERORG","CASENO","TRANSFERDATE","MANAGENO"
    									,"PROPERTYNO","PROPERTYNAME","PROPERTYNAME1","SIGNNO","DOORPLATE","AREA"
    									,"CAREA","SAREA","HOLDAREA","HOLDNUME","BOOKVALUE","USERELATION","USERCOUNT"
    									,"CAUSE","KIND"};
    	
    	String execSQL = " select c.organAName as enterOrgName ,a.writeDate ,a.writeUnit ,a.proofDoc, a.proofNo " + "\n"
    				   + " 		  ,(select l1.organaname from SYSCA_Organ l1 where l1.organid = b.newenterorg) as newEnterOrg " + "\n"
    				   + " 		  ,a.caseNO ,b.transferDate ,a.manageNo " + "\n"
    				   + " 		  ,b.propertyNo, b.serialNo" + "\n"
    				   + " 		  ,d.propertyName,b.propertyName1,b.signno " + "\n"
    				   + " 		  ,(select k.addrname from sysca_addr k where k.addrkind='1' and k.addrid=b.doorPlate1) as addrname1, " + "\n"
    				   + " 		  (select l.addrname from sysca_addr l where l.addrkind='2' and l.addrid=b.doorPlate2) as addrname2, " + "\n"
    				   + " 		  (select m.addrname from sysca_addr m where m.addrkind='3' and m.addrid=b.doorPlate3) as addrname3, b.doorPlate4 as addrname4 " + "\n"
    				   + " 		  ,b.area ,b.CArea ,b.SArea ,b.holdArea " + "\n"
    				   + " 		  ,b.holdNume, b.holdDeno ,b.bookValue " + "\n" 
    				   + " 		  ,(select f.codename from SYSCA_CODE f where f.codekindid = 'CAC' and b.cause=f.codeid)cause " + "\n"
    				   + " 		  ,b.cause1 " + "\n"
    				   + " 		  ,b.ownership ,(select x.codeName from sysca_code x where b.ownership = x.codeid and x.codekindid = 'OWA') as ownershipName " + "\n"
    				   + " 		  ,b.propertyKind ,(select x.codeName from sysca_code x where b.propertyKind = x.codeid and x.codekindid = 'PKD') as propertyKindName " + "\n"
    				   + "        ,(select codename from SYSCA_Code where codeKindID='URE' and codeid='04') as useRelation " + "\n"
    				   + "        ,(select count(*) as count from UNTBU_ReduceDetail a , UNTBU_Manage c where a.enterorg = c.enterorg and a.ownership = c.ownership and a.serialNo=c.serialNo and a.propertyno = c.propertyno and (case c.useDateS when null then '0000000' else c.useDateS end)<= " + Common.sqlChar(Datetime.getYYYMMDD()) + " and " + Common.sqlChar(Datetime.getYYYMMDD()) + " >= (case c.useDateE when null then '9999999' else c.useDateE end)) as usercount " + "\n"
    				   + " from UNTBU_ReduceProof a left join SYSCA_Organ c on a.enterOrg=c.organID,UNTBU_ReduceDetail b ,SYSPK_PropertyCode d " + "\n"
    				   + " where 1=1 " + "\n"
    				   + " and a.enterorg = b.enterorg and a.caseno = b.caseno and a.ownership = b.ownership " + "\n"
    				   + " and b.propertyNo = d.propertyNo " + "\n"
    				   + " and d.enterOrg in ('000000000A',a.enterOrg) " + "\n"
    				   + " and d.propertyType = '1' " + "\n"
    				   + " and b.cause= '01' " + "\n"
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
    				execSQL += " and a.enterOrg = " + Common.sqlChar(getOrganID()) ;
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
    		q_kind=kindName[k-1];
    	//System.out.println(execSQL);	
            while(rs.next()){
            	Object[] data = new Object[32];
            	data[0] = rs.getString("ownershipName") + "建物移交清冊";
                data[1] = rs.getString("enterOrgName");
                DateTrn = rs.getString("writeDate");
            	data[2]=Common.MaskDate(DateTrn);
            	data[3] = rs.getString("writeUnit");
                data[4] = rs.getString("proofDoc") + "字第" + rs.getString("proofNo") + "號";
                data[5] = rs.getString("newEnterOrg");
                data[6] = rs.getString("caseNO")+"　";
                DateTrn = rs.getString("transferDate");
            	data[7]=Common.MaskDate(DateTrn);
            	
            	data[8] = rs.getString("manageNo");
                data[9] = rs.getString("propertyNo").substring(7,11) + "-" + rs.getString("propertyNo").substring(7) + "-" + rs.getString("serialNo");
                data[10] = rs.getString("propertyName");
                data[11] = rs.getString("propertyName1");
                data[12] = getSignDescName(rs.getString("signNo").substring(0,7)) + rs.getString("signNo").substring(7,12) + '-' + rs.getString("signNo").substring(12) + "建號";
                if("".equals(data[12]))data[12]="";
                data[13]=rs.getString("addrname1") +rs.getString("addrname2") + rs.getString("addrname3") + rs.getString("addrname4");
                
                tansToDouble= rs.getString("area");
                if(tansToDouble!=null)data[14]= new Double(Double.parseDouble(tansToDouble));
                else data[14]=null;
                
                tansToDouble= rs.getString("Carea");
                if(tansToDouble!=null)data[15]= new Double(Double.parseDouble(tansToDouble));
                else data[15]=null;
                
                tansToDouble= rs.getString("Sarea");
                if(tansToDouble!=null)data[16]= new Double(Double.parseDouble(tansToDouble));
                else data[16]=null;
                
                tansToDouble = rs.getString("holdarea");
                if(tansToDouble!=null)data[17]= new Double(Double.parseDouble(tansToDouble));
                else data[17]=null;
                
                data[18] = rs.getString("holdNume") + "/" + rs.getString("holdDeno");
                
                tansToDouble = rs.getString("bookValue");
                if(tansToDouble!=null)data[19]= new Double(Double.parseDouble(tansToDouble));
                else data[19]=null;
                
                data[20] = rs.getString("useRelation");
                
                int o = Integer.parseInt(rs.getString("usercount"));
                if(o<=0){data[21] = " ";
                }else{data[21]= "等"+rs.getString("usercount")+"筆";}
                
                data[22] = rs.getString("cause");
                causetemp= rs.getString("cause1");
                if("其他".equals(data[22])||data[22]==null)data[22]=causetemp;
                data[23] = kindName[k-1];
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
