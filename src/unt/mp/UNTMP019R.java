/*
*<br>程式目的：動產撥出單報表檔 
*<br>程式代號：untmp019r
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

public class UNTMP019R extends SuperBean{
	String enterOrg;
	String ownership;
	String propertyNo;
	String serialNo;
	String caseNoS;
	String caseNoE;
	String writeDateS;
	String writeDateE;
	String proofDoc;
	String proofNoS;
	String proofNoE;
	String cause;

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
	public String getPropertyNo(){ return checkGet(propertyNo); }
	public void setPropertyNo(String s){ propertyNo=checkSet(s); }
	public String getSerialNo(){ return checkGet(serialNo); }
	public void setSerialNo(String s){ serialNo=checkSet(s); }
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
	public String getCause(){ return checkGet(cause); }
	public void setCause(String s){ cause=checkSet(s); }

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

    String equipmentName;
	public String getEquipmentName(){ return checkGet(equipmentName); }
	public void setEquipmentName(String s){ equipmentName=checkSet(s); }

public DefaultTableModel getResultModel() throws Exception{
    DefaultTableModel model = new javax.swing.table.DefaultTableModel();
    Database db = new Database();
    String KOC = "\n(召集人或其授權代簽人)";
    try{
    	while(q_caseNoS!=null&&!"".equals(q_caseNoS)&&q_caseNoS.length()<10){q_caseNoS=q_caseNoS+"0";}
    	while(q_caseNoE!=null&&!"".equals(q_caseNoE)&&q_caseNoE.length()<10){q_caseNoE=q_caseNoE+"9";}
    	String[] columns = new String[] {"OWNERSHIP","ENTERORGNAME","WRITEDATE","CASENO","PROOFDOC","PROPERTYNO",
    			                         "PROPERTYNAME","SPECIFICATION","PROPERTYUNIT","ADJUSTBOOKAMOUNT","BUYDATE",
    			                         "LIMITYEAR","USEYEAR","OLDBOOKUNIT","ADJUSTBOOKVALUE","CAUSE","KIND",
    			                         "NEWENTERORG","NEWENTERORGNAME",
    			                         "ATTACHMENT2","KOC","UNIT1","UNIT2"};
    	String execSQL="select b.enterOrg as shareEnterOrg, b.ownership as shareOwnership, b.propertyNo as sharePropertyNo, b.serialNo as shareSerialNo, " +
		    			" (select z.codeName from sysca_code z where 1=1 and (z.codeKindID='owa' or z.codeKindID='OWA') and a.ownership=z.codeID) as ownershipName, " +
		    			" c.organAName as enterOrgName," +
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
		    			" (select f.codename from SYSCA_CODE f where f.codekindid = 'CAC' and b.cause=f.codeid)cause,b.cause1, " +
		    			" b.newEnterOrg, " +
		    			" (select  z.organAName from SYSCA_Organ z where 1=1 and b.newEnterOrg=z.organID) as newEnterOrgName, " +
		    			" (select o.organaname from sysca_organ o where a.enterorg = o.organid) as unit1, " +
		    			" (select o.organaname from sysca_organ o where b.newenterorg = o.organid) as unit2 " +
		    			" ,b.lotno" +
		    			" from UNTMP_ReduceProof a,UNTMP_ReduceDetail b,SYSCA_Organ c,SYSPK_PropertyCode d  " +
    	 " where a.enterorg=b.enterorg and a.caseno=b.caseno and a.ownership=b.ownership and b.propertyNo=d.propertyNo and a.enterOrg=c.organID(+) " +
    	 " and d.enterOrg in ('000000000A',a.enterOrg) " +

    	 //20110915 Yuan-Ren modify
    	 //<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
    	 //" and d.propertyType='1' and b.cause='08' " ;
    	 " and d.propertyType='1' and b.cause in ('01','08') " ;
    	 //>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
	
    	if (!"".equals(Common.get(getQ_ownership()))){
    		execSQL += " and a.ownership = " + util.Common.sqlChar(getQ_ownership());
    	}
    	/*if (!"".equals(Common.get(getQ_enterOrg()))){
    		execSQL = execSQL + " and a.enterorg = " + util.Common.sqlChar(getQ_enterOrg());
    	}
    	*/
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
    		execSQL +=  " and a.caseNo <= " + util.Common.sqlChar(getQ_caseNoE());
    	
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

    	execSQL+=" order by a.caseno,unit2,b.propertyno,b.serialno";

    	String kindName[]={"第一聯","第二聯","第三聯"};
    	String tansToDouble;
    	//String DateTrn;
    	String causetemp;
    	String limitYeartemp;
    	//String DeprMethod;
    	
    	if(!"4".equals(q_kind)){
    		if("3".equals(q_kind))q_kind=kindName[2];
    		else if("2".equals(q_kind))q_kind=kindName[1];
    		else q_kind=kindName[0];
    	

        ResultSet rs = db.querySQL(execSQL);
        Vector rowData = new Vector();
        UNTMP019R obj = this;
        while(rs.next()){
        	obj.setCause(rs.getString("cause"));
        	Object[] data = new Object[23];
        	setEnterOrg(rs.getString("shareEnterOrg"));
        	setOwnership(rs.getString("shareOwnership"));
        	setPropertyNo(rs.getString("sharePropertyNo"));
        	setSerialNo(rs.getString("shareSerialNo"));
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
            data[15] = rs.getString("cause");
            causetemp= rs.getString("cause1");
            if("其他".equals(data[15])||data[15]==null)data[15]=causetemp;
            data[16]=q_kind;
            data[17] = rs.getString("newEnterOrg");
            data[18] = rs.getString("newEnterOrgName");
            getTotalEquipmentName(rs.getString("lotno"));
            data[19] = getEquipmentName();
            if(q_ownership.equals("4")){
            	data[20] = KOC;
            }else{
            	data[20] = "";
            }
            
            if(!"".equals(Common.get(rs.getString("unit1")))){
            	data[21] = "撥出單位: "+rs.getString("unit1");
            }else{
            	data[21] = "撥出單位: ";
            }
            
            if(!"".equals(Common.get(rs.getString("unit2")))){
            	data[22] = "撥入單位: "+rs.getString("unit2");
            }else{
            	data[22] = "撥入單位: ";
            }

            //for(i=0;i<20;i++)if(data[i]==null)data[i]="";
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
            	Object[] data = new Object[23];
            	setEnterOrg(rs.getString("shareEnterOrg"));
            	setOwnership(rs.getString("shareOwnership"));
            	setPropertyNo(rs.getString("sharePropertyNo"));
            	setSerialNo(rs.getString("shareSerialNo"));
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
                data[15] = rs.getString("cause");
                causetemp= rs.getString("cause1");
                if("其他".equals(data[15])||data[15]==null)data[15]=causetemp;
                data[16]=q_kind;
                data[17] = rs.getString("newEnterOrg");
                data[18] = rs.getString("newEnterOrgName");
                getTotalEquipmentName(rs.getString("lotno"));
                data[19] = getEquipmentName();
                if(q_ownership.equals("4")){
                	data[20] = KOC;
                }else{
                	data[20] = "";
                }
                if(!"".equals(Common.get(rs.getString("unit1")))){
                	data[21] = "撥出單位: "+rs.getString("unit1");
                }else{
                	data[21] = "撥出單位: ";
                }
                
                if(!"".equals(Common.get(rs.getString("unit2")))){
                	data[22] = "撥入單位: "+rs.getString("unit2");
                }else{
                	data[22] = "撥入單位: ";
                }
                //for(i=0;i<20;i++)if(data[i]==null)data[i]="";
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

//抓取該動產之「UNTMP_Attachment2動產批號明細附屬設備檔」中資料狀態為「1:現存」者之「equipmentName名稱」，名稱與名稱之間以「、」區隔
protected void getTotalEquipmentName(String lotno){
    equipmentName="";
	Database db = new Database();	
	ResultSet rs;	
	String sql="select a.equipmentName " +
				" from UNTMP_Attachment1 a" +
				" where 1=1 " + 
				" and a.enterOrg=" + util.Common.sqlChar(getEnterOrg())+
				" and a.ownership=" + util.Common.sqlChar(getOwnership())+
				" and a.propertyNo=" + util.Common.sqlChar(getPropertyNo())+
				"  and a.lotNo = " + util.Common.sqlChar(lotno) +
				//" and a.serialNo=" + util.Common.sqlChar(getSerialNo())+
				"";	
	//System.out.println(sql);
	try{
		rs = db.querySQL(sql);
		while (rs.next()){
		    equipmentName += "、"+rs.getString("equipmentName");
		}
		if(equipmentName.equals("")){
		    equipmentName += "、 ";
		}	
		setEquipmentName(equipmentName.substring(1));
	} catch (Exception e) {
		e.printStackTrace();
	} finally {
		db.closeAll();
	}
}

}
