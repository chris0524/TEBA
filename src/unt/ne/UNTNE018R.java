/*
*<br>程式目的：非消耗品撥出單 
*<br>程式代號：untne018r
*<br>撰寫日期：104/09/16
*<br>程式作者：Mike Kao
*<br>--------------------------------------------------------
*<br>修改作者　　修改日期　　　修改目的
*<br>--------------------------------------------------------
*/

package unt.ne;

import java.sql.ResultSet;
import java.util.Vector;

import javax.swing.table.DefaultTableModel;

import unt.ch.UNTCH_Tools;
import util.Common;
import util.Database;
import util.Datetime;
import util.SuperBean;

public class UNTNE018R extends SuperBean{
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

	String q_enterOrg;
	String q_enterOrgName;
	String q_ownership;
	String q_caseNoS;
	String q_caseNoE;
	String q_writeDateS;
	String q_writeDateE;
	String q_proofYear;
	String q_proofDoc;
	String q_proofNoS;
	String q_proofNoE;
	String q_kind;
	String q_note;
	
	String isOrganManager;
    String isAdminManager;
    String organID;   
	private String q_reportType;						// 報表類型
	
	public String getQ_reportType() {	return checkGet(q_reportType);}
	public void setQ_reportType(String q_reportType) { this.q_reportType = checkSet(q_reportType); }	
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
	public String getQ_proofYear() {return checkGet(q_proofYear);}
	public void setQ_proofYear(String qProofYear) {q_proofYear = checkSet(qProofYear);}
	public String getQ_proofDoc(){ return checkGet(q_proofDoc); }
	public void setQ_proofDoc(String s){ q_proofDoc=checkSet(s); }
	public String getQ_proofNoS(){ return checkGet(q_proofNoS); }
	public void setQ_proofNoS(String s){ q_proofNoS=checkSet(s); }
	public String getQ_proofNoE(){ return checkGet(q_proofNoE); }
	public void setQ_proofNoE(String s){ q_proofNoE=checkSet(s); }
	public String getQ_kind(){ return checkGet(q_kind); }
	public void setQ_kind(String s){ q_kind=checkSet(s); } 
	public String getQ_note() {return checkGet(q_note);}
	public void setQ_note(String qNote) {q_note = checkSet(qNote);}
    public String getOrganID() { return checkGet(organID); }
    public void setOrganID(String s) { organID = checkSet(s); }
    
    public String getIsOrganManager() { return checkGet(isOrganManager); }
    public void setIsOrganManager(String s) { isOrganManager = checkSet(s); }
    public String getIsAdminManager() { return checkGet(isAdminManager); }
    public void setIsAdminManager(String s) { isAdminManager = checkSet(s); } 


public DefaultTableModel getResultModel() throws Exception{
    DefaultTableModel model = new javax.swing.table.DefaultTableModel();
    Database db = new Database();
    UNTCH_Tools ut = new UNTCH_Tools();
    String KOC = "\n(召集人或其授權代簽人)";
    try{
    	while(q_caseNoS!=null&&!"".equals(q_caseNoS)&&q_caseNoS.length()<10){q_caseNoS=q_caseNoS+"0";}
    	while(q_caseNoE!=null&&!"".equals(q_caseNoE)&&q_caseNoE.length()<10){q_caseNoE=q_caseNoE+"9";}
    	String[] columns = new String[] {"ENTERORGNAME","WRITEDATE","PROPERTYKINDNAME","WRITEUNIT","PROOFDOCPROOFNO","KIND","CASENO","PROPERTYNOSERIALNO","SPECIFICATIONNAMEPLATE","PROPERTYUNIT","BOOKAMOUNT","SOURCEDATEBUYDATE","LIMITYEAR","USEYEAR","BOOKUNIT","BOOKVALUE","CAUSE", "Q_NOTE", "TAIL01"};
    	String execSQL="select b.enterOrg, b.ownership, b.propertyNo, b.serialNo, " +
		    			" c.organAName as enterOrgName," +
		    			" (select z.codeName from sysca_code z where 1=1 and (z.codeKindID='owa' or z.codeKindID='OWA') and a.ownership=z.codeID) as ownershipName, " +
		    			" a.writeDate," +
		    			" (case b.propertyKind when '01' then '公務用' when '02' then '公共用' when '03' then '事業用' when '04' then '非公用' else '' end) propertyKindName, "+
		    			" a.writeunit," +
		    			" a.caseno," +
		    			" a.proofYear+'年'+a.proofDoc+'字第'+a.proofNo+'號' as proofdocProofNo," +
		    			" b.propertyNo+'-'+b.serialNo as propertynoSerialno," +
		    			" d.propertyName," +
		    			" b.specification+'/'+isnull(b.nameplate,' ') as specificationNameplate," +
//		    			" b.specification as specificationNameplate," +
		    			" d.propertyUnit,b.otherPropertyUnit," +
		    			" b.adjustBookUnit,1 as adjustBookAmount,b.adjustBookValue," +
		    			" b.sourceDate," +
		    			" b.buyDate," +
//		    			" d.limitYear," +
		    			" b.otherLimitYear," +
//		    			" b.useyear, b.usemonth, "+
		    		    " (select codename from SYSCA_CODE z where codekindid = 'CAA' and z.codeid = b.cause and codecon1 in (2,4)) as causeName, " +
		    			" CONVERT(VARCHAR(4),b.useyear) + '年' +  CONVERT(VARCHAR(2),b.usemonth) + '個月' as useYear " + 
		    			" from UNTNE_REDUCEPROOF a"+
		    			" left join SYSCA_Organ c on a.enterOrg=c.organID," +
    			        " UNTNE_REDUCEDETAIL b,SYSPK_PropertyCode2 d  " +
    					" where a.enterorg=b.enterorg and a.caseno=b.caseno and a.ownership=b.ownership and b.enterOrg=d.enterOrg and b.propertyNo=d.propertyNo ";
    	//execSQL1=execSQL1+" and d.enterOrg in ('000000000A',a.enterOrg) ";
    	String queryCondition = "";
    	if (!"".equals(Common.get(getQ_ownership()))){
    		queryCondition =  " and a.ownership = " + util.Common.sqlChar(getQ_ownership());
    	}
    	/*if (!"".equals(Common.get(getQ_enterOrg()))){
    		execSQL = execSQL + " and a.enterorg = " + util.Common.sqlChar(getQ_enterOrg());
    	}
    	*/
    	if (!"".equals(getQ_enterOrg())) {
    		queryCondition += " and a.enterOrg = " + Common.sqlChar(getQ_enterOrg()) ;
    	} else {
    		if (!getIsAdminManager().equalsIgnoreCase("Y")) {
    			if ("Y".equals(getIsOrganManager())) {					
    				queryCondition += " and a.enterOrg like '" + getOrganID().substring(0,5) + "%' ";
    			} else {
    				queryCondition += " and a.enterOrg = " + Common.sqlChar(getOrganID()) ;
    			}
    		}
    	}
    	
    	if (!"".equals(Common.get(getQ_caseNoS())))
    		queryCondition +=  " and a.caseNo >= " + util.Common.sqlChar(getQ_caseNoS());
    	if (!"".equals(Common.get(getQ_caseNoE())))
    		queryCondition +=  " and a.caseNo <= " + util.Common.sqlChar(getQ_caseNoE());
    	
    	if (!"".equals(Common.get(getQ_writeDateS())))
    		queryCondition +=  " and a.writeDate >= " + util.Common.sqlChar(new Datetime().changeTaiwanYYMMDD(getQ_writeDateS(),"2"));
    	if (!"".equals(Common.get(getQ_writeDateE())))
    		queryCondition +=  " and a.writeDate <= " + util.Common.sqlChar(new Datetime().changeTaiwanYYMMDD(getQ_writeDateE(),"2"));
    	
    	if (!"".equals(Common.get(getQ_proofYear()))){
    		queryCondition += " and a.proofyear = "  + Common.sqlChar(getQ_proofYear());
    	}
    	
    	if (!"".equals(Common.get(getQ_proofDoc()))){
    		queryCondition +=  " and a.proofDoc like " + util.Common.sqlChar("%"+getQ_proofDoc()+"%");
    	}
    	if (!"".equals(Common.get(getQ_proofNoS())))
    		queryCondition +=  " and a.proofNo >= " + util.Common.sqlChar(getQ_proofNoS());
    	if (!"".equals(Common.get(getQ_proofNoE())))
    		queryCondition +=  " and a.proofNo <= " + util.Common.sqlChar(getQ_proofNoE());
    	
    	
    	execSQL += queryCondition + " order by a.caseno,b.propertyno,b.serialno";
    	
    	//System.out.println(execSQL);
    	
    	String[] kindName = null;
		if("1".equals(q_kind)){			kindName = new String[]{"第一聯"};
		}else if("2".equals(q_kind)){	kindName = new String[]{"第二聯"};
		}else if("3".equals(q_kind)){	kindName = new String[]{"第三聯"};
		}else if("4".equals(q_kind)){	kindName = new String[]{"第一聯","第二聯","第三聯"};
		}else if("5".equals(q_kind)){	kindName = new String[]{"第一聯","第二聯","第二聯 ","第三聯"};
		}
		
    	String tansToDouble;
    	String DateTrn;
    	String DateTrn2;
    	String causetemp;
    	String limitYeartemp;
    	String PropertyUnit;
    	
    	Vector rowData = new Vector();
		
		   	 if(kindName != null){
		   	   for(int i=0; i<kindName.length; i++){
		    		
		        ResultSet rs = db.querySQL(execSQL);
			        while(rs.next()){
			        	Object[] data = new Object[columns.length];
			        	setEnterOrg(rs.getString("enterOrg"));
			        	setOwnership(rs.getString("ownership"));
			        	setPropertyNo(rs.getString("propertyNo"));
			        	setSerialNo(rs.getString("serialNo"));
			            data[0] = rs.getString("enterOrgName");
			            data[1] = Common.MaskCDate(Datetime.changeTaiwanYYMMDD(rs.getString("writeDate"), "1"));
			            data[2] = Common.get(rs.getString("propertyKindName"));
			            data[3] =  Common.get(rs.getString("writeUnit"));
			            data[4] =  Common.get(rs.getString("proofdocProofNo"));
			            data[5] = kindName[i];
			        	data[6] =  Common.get(rs.getString("caseno"));       	
			            data[7] =  Common.get(rs.getString("propertynoSerialno"))+"\n"+ Common.get(rs.getString("propertyName"));
			            if(!"".equals(rs.getString("specificationNameplate")))
			            	data[8] =  Common.get(rs.getString("specificationNameplate"));
			            else
			            	data[8]="";
			            
			            data[9] =  Common.get(rs.getString("otherPropertyUnit"));
//			            data[9] =  Common.get(rs.getString("propertyUnit"));
//			            PropertyUnit=  Common.get(rs.getString("otherPropertyUnit"));
//			            if(data[9]==null)data[9]=PropertyUnit;
			            
			            tansToDouble = rs.getString("adjustBookAmount");
			            if(tansToDouble!=null)data[10]= new Double(Double.parseDouble(tansToDouble));
			            else data[10]=null;
			            
			            data[11] =  Common.MaskDate(Datetime.changeTaiwanYYMMDD(rs.getString("sourceDate"), "1")) + "\n" + Common.MaskDate(Datetime.changeTaiwanYYMMDD(rs.getString("buyDate"), "1"));
//			        	data[12] =  Common.get(rs.getString("limitYear"));
//			        	limitYeartemp=  Common.get(rs.getString("otherLimitYear"));
//			        	if("".equals(data[12]))data[12]=limitYeartemp;
			            int otherLimitMonth = "".equals(Common.get(rs.getString("otherLimitYear")))? 0 : Integer.parseInt(rs.getString("otherLimitYear")); // otherLimitYear以單位月分存取需轉成年
						int year = otherLimitMonth /12;
						int month = otherLimitMonth % 12;
						data[12] = year +"年"+month+"個月";
			        	
			//        	data[13] = Common.get(rs.getString("useyear"))+ "年" + Common.get(rs.getString("usemonth")) + "個月";
			        	data[13] = Common.get(rs.getString("useYear"));
			        	tansToDouble = rs.getString("adjustBookUnit");
			            if(tansToDouble!=null)data[14]= new Double(Double.parseDouble(tansToDouble));
			            else data[14]= new Double(0);
			            tansToDouble = rs.getString("adjustBookValue");
			            if(tansToDouble!=null)data[15]= new Double(Double.parseDouble(tansToDouble));
			            else data[15]= new Double(0);
			            data[16] = rs.getString("causeName");
			            
			            data[17] = getQ_note();
			    		//單據備註
			    		data[18] = ut._queryData("notes")._from("UNTNE_REDUCEPROOF a ")._with(queryCondition)._toString();
			            
			            //for(i=0;i<20;i++)if(data[i]==null)data[i]="";
			            rowData.addElement(data);
			        }
			        rs.close();
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