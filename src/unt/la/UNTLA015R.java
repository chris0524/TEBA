/*
*<br>程式目的：土地移交清冊 
*<br>程式代號：unla015r
*<br>撰寫日期：94/11/8
*<br>程式作者：chris
*<br>--------------------------------------------------------
*<br>修改作者　　修改日期　　　修改目的
*<br>--------------------------------------------------------
*/

package unt.la;

import java.sql.ResultSet;
import java.util.Vector;

import javax.swing.table.DefaultTableModel;

import net.sf.jasperreports.engine.data.JRTableModelDataSource;



import util.*;

public class UNTLA015R extends SuperBean{


	String shareEnterOrg;
	String shareOwnership;
	String shareCaseNo;
	String enterOrg;
	String ownership;
	String caseNoS;
	String caseNoE;
	String writeDateS;
	String writeDateE;
	String proofDoc;
	String proofNoS;
	String proofNoE;
	

	// 新增查詢欄位
	int nocount;
	double sumaread;
    double sumholdaread;
    double sumbookvalued;
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
	public String getShareEnterOrg(){ return checkGet(shareEnterOrg); }
	public void setShareEnterOrg(String s){ shareEnterOrg=checkSet(s); }
	public String getShareOwnership(){ return checkGet(shareOwnership); }
	public void setShareOwnership(String s){ shareOwnership=checkSet(s); }
	public String getShareCaseNo(){ return checkGet(shareCaseNo); }
	public void setShareCaseNo(String s){ shareCaseNo=checkSet(s); }
	
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

	String propertyNo;
	String serialNo;

    public String getPropertyNo() { return checkGet(propertyNo); }
    public void setPropertyNo(String s) { propertyNo = checkSet(s); } 
    public String getSerialNo() { return checkGet(serialNo); }
    public void setSerialNo(String s) { serialNo = checkSet(s); } 
    
    String orderBy;    
    public String getOrderBy() {return checkGet(orderBy);}
	public void setOrderBy(String orderBy) {this.orderBy = checkSet(orderBy);}
	
	
public DefaultTableModel getResultModel() throws Exception{
	
	UNTLA015R obj = this;
    DefaultTableModel model = new javax.swing.table.DefaultTableModel();
    Database db = new Database();
    try{
    	while(q_caseNoS!=null&&!"".equals(q_caseNoS)&&q_caseNoS.length()<10){q_caseNoS=q_caseNoS+"0";}
    	while(q_caseNoE!=null&&!"".equals(q_caseNoE)&&q_caseNoE.length()<10){q_caseNoE=q_caseNoE+"9";}
    	// 必須跟ireportㄧ致
    	String[] columns = new String[] {"OWNERSHIP","ENTERORGNAME","WRITEDATE","WRITEUNIT","PROOFNO"
    									,"NEWENTERORG","CASENO","TRANSFERDATE","MANAGENO","KIND"
    									,"SubReportM","nocount","sumarea","sumholdarea"
    									,"sumbookvalue"};
    	
    	String execSQL = " select distinct a.enterOrg as shareEnterOrg ,b.propertyNo ,b.serialNo " + "\n"
    				   + "		  ,a.ownership as shareOwnership " + "\n"
    				   + "		  ,(select x.codeName from sysca_code x where a.ownership = x.codeid and x.codekindid = 'OWA') as ownershipName " + "\n"
    				   + "		  ,a.caseNo as shareCaseNo "
    				   + "		  ,(select c.organAName from SYSCA_Organ c where a.enterOrg = c.organID)as enterOrgName "
    				   + " 		  ,a.writeDate ,a.writeUnit " + "\n"
    				   + "		  ,a.proofDoc, a.proofNo " + "\n"
    				   + "		  ,(select l2.organAName from SYSCA_Organ l2 where l2.organID = b.newEnterOrg) as newEnterOrg " + "\n"
    				   + "		  ,a.caseNO ,b.transferDate ,a.manageNo " + "\n"
    				   + "		  ,(select count(*) from untla_reducedetail j where j.enterorg=b.enterorg and j.ownership=b.ownership and j.caseno=b.caseno) as nocount " + "\n" 
    				   + " from UNTLA_ReduceProof a ,UNTLA_ReduceDetail b ,SYSPK_PropertyCode d " + "\n"
    				   + " where 1=1 " + "\n"
    				   + " and a.enterOrg = b.enterOrg and a.ownership = b.ownership and a.caseNo = b.caseNo " + "\n"
    				   + " and b.propertyNo = d.propertyNo " + "\n"
    				   + " and d.enterOrg in ('000000000A',a.enterOrg) " + "\n"
    				   + " and d.propertyType='1' " + "\n"
    				   + " and b.cause='201' " + "\n"
    				   + "";
    	//System.out.println("execSQL"+execSQL);
    	if (!"".equals(Common.get(getQ_ownership()))){
    		execSQL += " and a.ownership = " + util.Common.sqlChar(getQ_ownership());
    	}
    	if (!"".equals(getQ_enterOrg())) {
    		execSQL += " and a.enterOrg = " + Common.sqlChar(getQ_enterOrg()) ;
    	} else {
    		if (!getIsAdminManager().equalsIgnoreCase("Y")) {
    			if ("Y".equals(getIsOrganManager())) {					
    				//execSQL += " and a.enterOrg like '" + getOrganID().substring(0,5) + "%' ";
    				execSQL += " and( a.enterOrg = " + Common.sqlChar(getOrganID()) ;
    				execSQL += " or organID in (select organID from SYSCA_Organ where organSuperior="+ Common.sqlChar(getOrganID())+"))";
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
    	//System.out.println("execSQL"+execSQL);
    	execSQL += " order by a.caseno ";
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
			rs = db.querySQL(execSQL);
    		while(rs.next()){
            	Object[] data = new Object[15];
            	obj.setShareEnterOrg(rs.getString("shareEnterOrg"));
            	obj.setShareOwnership(rs.getString("shareOwnership"));
            	obj.setShareCaseNo(rs.getString("shareCaseNo"));
            	obj.setPropertyNo(rs.getString("propertyNo"));
            	obj.setSerialNo(rs.getString("serialNo"));
            	
            	data[0]= rs.getString("ownershipName") + "土地移交清冊";
                data[1] = rs.getString("enterOrgName");
                data[2] = Common.MaskCDate(rs.getString("writeDate"));
            	data[3] = rs.getString("writeUnit");
                data[4] = rs.getString("proofDoc") + "字第" + rs.getString("proofNo") + "號";
                data[5] = rs.getString("newEnterOrg");
                data[6] = rs.getString("caseNO")+"　";
                data[7] = Common.MaskDate(rs.getString("transferDate"));
            	data[8] = rs.getString("manageNo");
                data[9] = kindName[k-1];
                data[10]= new JRTableModelDataSource(getSubModel((String)data[0]));
                data[11]= rs.getString("nocount");
                data[12]=Common.areaFormat(sumaread+"")+"　";
                data[13]=Common.areaFormat(sumholdaread+"")+"　";
                data[14]=Common.valueFormat(sumbookvalued+"")+"　";
               
                rowData.addElement(data);              
             }
    		sumaread=0;
        	sumholdaread=0;
        	sumbookvalued=0;
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
public DefaultTableModel getSubModel(String caseCode) throws Exception{
	UNTLA015R obj = this;
    DefaultTableModel model = new javax.swing.table.DefaultTableModel();
    Database db = new Database();
    try{
        String[] columns = new String[] { "PROPERTYNO","PROPERTYNAME","SIGNNO","AREA","HOLDAREA","HOLDNUME","BOOKVALUE","BULLETINDATE","VALUEUNIT","useSeparate","useKind","useState1","useRelation","CAUSE"};
    	String execSQL="  select b.propertyNo as propertyNoo,b.serialNo as serialNoo, b.propertyNo, b.serialNo,d.propertyName,b.signNo,b.area,b.holdArea,b.holdNume, b.holdDeno,b.bookValue,b.bookUnit,(case b.valueUnit when null then 0 else b.valueUnit end) as valueUnit, " +
    					" (select f1.codename from SYSCA_CODE f1 where f1.codekindid = 'FIE' and b.field=f1.codeid) as field,(select f.codename from SYSCA_CODE f where f.codekindid = 'CAA' and b.cause=f.codeid)cause,b.cause1 ," +
    					" (select sc.codename from sysca_code sc where b.useSeparate = sc.codeid and sc.codekindid = 'SEP' ) as useSeparate , " +  
     				    " (select ss.codename from sysca_code ss where b.useKind = ss.codeid and ss.codekindid = 'UKD') as useKind ," + 
     				    " (case b.usestate1 when '01' then '空置' when '02' then '建物' when '03' then '農作' when '04' then '其他' end) as useState1 ," + 
     				    " (select count(*) from UNTLA_Manage m where b.enterorg = m.enterorg and b.ownership = m.ownership and b.propertyno = m.propertyno and b.serialno = m.serialno )as countUseRelation " +
    					" from UNTLA_ReduceProof a, UNTLA_ReduceDetail b, SYSPK_PropertyCode d "  +  //UNTLA_Manage m 
    					" where b.propertyNo=d.propertyNo "+
						" and b.enterOrg=" + util.Common.sqlChar(obj.getShareEnterOrg())+
						" and b.ownership=" + util.Common.sqlChar(obj.getShareOwnership())+
						" and b.CaseNo=" + util.Common.sqlChar(obj.getShareCaseNo())+
						" and b.propertyNo=" + util.Common.sqlChar(obj.getPropertyNo())+
						" and b.serialNo=" + util.Common.sqlChar(obj.getSerialNo())+
						" ";
    	//execSQL+=" and b.enterorg = m.enterorg and b.ownership = m.ownership and b.propertyno = m.propertyno and b.serialno = m.serialno ";
    	execSQL+=" and a.enterorg = b.enterorg and a.ownership = b.ownership and a.caseno = b.caseno ";
    	execSQL+=" and d.enterOrg in ('000000000A',"+util.Common.sqlChar(obj.getShareEnterOrg())+")";
    	execSQL+=" and d.propertyType='1' and b.cause='201' ";
    	execSQL += " order by b.propertyno,b.serialno ";

    	String execSQL2= " select(select cs.codename from sysca_code cs where m.useRelation = cs.codeid and cs.codekindid = 'URE' and (case m.useDateS when null then '0000000' else m.useDateS end) <= " + Common.sqlChar(Datetime.getYYYMMDD()) + " and (case m.useDateE when null then '9999999' else m.useDateE end) >= " + Common.sqlChar(Datetime.getYYYMMDD()) + " ) as useRelation " + 
    	" from UNTLA_Manage m" +
    	" where m.enterOrg=" + util.Common.sqlChar(obj.getShareEnterOrg())+
		" and m.ownership=" + util.Common.sqlChar(obj.getShareOwnership())+
		//" and m.propertyNo=" + util.Common.sqlChar(obj.getPropertyNo())+
		//" and m.serialNo=" + util.Common.sqlChar(obj.getSerialNo())+
		"";
    	//System.out.println("===subexecSQL2==="+execSQL2);
    	//System.out.println("===subexecSQL==="+execSQL);
    	ResultSet rs = db.querySQL(execSQL);
    	Vector rowData = new Vector();
    	//int i;
    	nocount=0;
    	String causetemp="";
    	while(rs.next()){
    		Object[] data = new Object[14];
    		data[0] = rs.getString("propertyNo").substring(0,7) + "-" + rs.getString("propertyNo").substring(7) + "-" +rs.getString("serialNo");
            data[1] = rs.getString("propertyName");
            data[2] = getSignDescName(rs.getString("signNo").substring(0,7)) + rs.getString("signNo").substring(7,11) + "-" + rs.getString("signNo").substring(11) + "地號";
            if("-地號".equals(data[2]))data[2]="";
            
            data[3]= Common.areaFormat(rs.getString("area"))+"　";
            sumaread+=rs.getDouble("area");
            data[4] = Common.areaFormat(rs.getString("holdarea"))+"　";
            sumholdaread+=rs.getDouble("holdarea");
            data[5] = rs.getString("holdNume") + "/" + rs.getString("holdDeno");
            data[6] = Common.valueFormat(rs.getString("bookValue"))+"　";
            sumbookvalued+=rs.getDouble("bookValue");
            data[7] = ("".equals(checkGet(rs.getString("bookUnit"))))?new Double(0):new Double(rs.getString("valueUnit"));
        	data[8] = ("".equals(checkGet(rs.getString("valueUnit"))))?new Double(0):new Double(rs.getString("valueUnit"));
        	data[9] = rs.getString("useSeparate");
        	data[10] = rs.getString("useKind");
        	data[11] = rs.getString("useState1");
        	execSQL2 += " and m.propertyNo= '"+rs.getString("propertyNoo")+"'";
        	execSQL2 += " and m.serialNo= '"+rs.getString("serialNoo")+"'";
        	int t = Integer.parseInt(rs.getString("countUseRelation"));
        	ResultSet rs2 = db.querySQL(execSQL2);     	
            if(t>1)
            {	
           	   if(rs2.next())
           	   {
           		   data[12] = rs2.getString("useRelation") + "等" + rs.getString("countUseRelation") + "筆";
           	   }
           }
           else
           {
           	if(rs2.next())
           	{
           		data[12] = Common.get(rs2.getString("useRelation"));
           	}
           }
            
        	data[13] = rs.getString("cause");
              causetemp= rs.getString("cause1");
              if("其他".equals(data[13])||data[13]==null)data[13]=causetemp;
              nocount++;
              rowData.addElement(data);      
          //  data[9] = rs.getString("field");
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
