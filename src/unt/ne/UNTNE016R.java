

/*
*<br>程式目的：非消耗品毀損報廢單報表檔 
*<br>程式代號：untne016r
*<br>撰寫日期：104/09/15
*<br>程式作者：Mike Kao
*<br>--------------------------------------------------------
*<br>修改作者　　修改日期　　　修改目的
*<br>--------------------------------------------------------
*/

package unt.ne;

import java.sql.ResultSet;
import java.util.Map;
import java.util.Vector;

import javax.swing.table.DefaultTableModel;

import TDlib_Simple.tools.src.StringTools;
import unt.ch.UNTCH_Tools;
import util.*;
import util.report.ReportUtil;

public class UNTNE016R extends SuperBean{


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
	String q_proofYear;
	String q_proofDoc;
	String q_proofNoS;
	String q_proofNoE;
	String q_kind;
	String q_note;
	String q_reducedeal;
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
	public String getCaseNoS(){ return checkGet(caseNoS); }
	public void setCaseNoS(String s){ caseNoS=checkSet(s); }
	public String getCaseNoE(){ return checkGet(caseNoE); }
	public void setCaseNoE(String s){ caseNoE=checkSet(s); }
	public String getWriteDateS(){ return checkGet(writeDateS); }
	public void setWriteDateS(String s){ writeDateS=checkSet(s); }
	public String getWriteDateE(){ return checkGet(writeDateE); }
	public void setWriteDateE(String s){ writeDateE=checkSet(s); }
	public String getQ_proofYear() {return checkGet(q_proofYear);}
	public void setQ_proofYear(String qProofYear) {q_proofYear = checkSet(qProofYear);}
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
	public String getQ_note() {return checkGet(q_note);}
	public void setQ_note(String qNote) {q_note = checkSet(qNote);}
    public String getOrganID() { return checkGet(organID); }
    public void setOrganID(String s) { organID = checkSet(s); }
    public String getQ_reducedeal() {return checkGet(q_reducedeal);}
	public void setQ_reducedeal(String qReducedeal) {q_reducedeal = checkSet(qReducedeal);}
	public String getIsOrganManager() { return checkGet(isOrganManager); }
    public void setIsOrganManager(String s) { isOrganManager = checkSet(s); }
    public String getIsAdminManager() { return checkGet(isAdminManager); }
    public void setIsAdminManager(String s) { isAdminManager = checkSet(s); } 



public DefaultTableModel getResultModel() throws Exception{
	Map<String,String> differencekindMap = TCGHCommon.getSysca_Code("DFK"); 					//財產區分別名稱
    DefaultTableModel model = new javax.swing.table.DefaultTableModel();
    Database db = new Database();
    UNTCH_Tools ut = new UNTCH_Tools();
    StringTools st = new StringTools();
    try{
    	while(q_caseNoS!=null&&!"".equals(q_caseNoS)&&q_caseNoS.length()<10){q_caseNoS=q_caseNoS+"0";}
    	while(q_caseNoE!=null&&!"".equals(q_caseNoE)&&q_caseNoE.length()<10){q_caseNoE=q_caseNoE+"9";}
    	String[] columns = new String[] {"ENTERORGNAME", "DIFFERENTKIND", "CASENO1", "KIND", "WRITEDATE",
    			                         "PROPERTYNOSERIALNO", "PROPERTYNAME1", "PROPERTYUNIT", "OLDBOOKAMOUNT", "OLDBOOKUNITOLDBOOKVALUE",
    			                         "SOURCEDATEBUYDATE", "LIMITYEARUSEYEAR", "ADJUSTBOOKVALUE", "REDUCEDEAL", "NEWBOOKVALUE",
    			                         "NOTES", "OLDBOOKVALUE", "Q_NOTE", "TAIL01" ,"PROOFDOC"}; 
    	String execSQL="select c.organAName as enterOrgName," +
		    			" (select z.codeName from sysca_code z where 1=1 and (z.codeKindID='owa' or z.codeKindID='OWA') and b.ownership=z.codeID) as ownershipName, " +
		    			" a.writeDate," +
		    			" b.differencekind,"+
		    			" a.caseno," +
		    			" a.proofyear+'年'+a.proofdoc+'字第'+a.proofno+'號' as proofdoc," +
		    			" b.propertyNo+'-'+b.serialNo as propertynoSerialno," +
		    			" b.oldserialno, " +
		    			" d.propertyName," +
		    			" b.propertyName1," +
		    			" b.specification, " +
		    			" b.nameplate, " +
		    			" d.propertyUnit,b.otherPropertyUnit," +
		    			" b.oldBookValue," +
		    			" b.oldbookUnit," +
		    			" b.oldbookAmount," +
		    			" b.newBookValue," +
		    			" 1  as adjustBookAmount," +
		    			" b.adjustBookUnit," +
		    			" b.adjustBookValue," +
		    			" b.sourceDate," +
		    			" b.buyDate," +
//		    			" d.limitYear," +
		    			" b.otherLimitYear," +
		    			" CONVERT(VARCHAR(8),b.useyear) + '年' +  CONVERT(VARCHAR(8),b.usemonth) + '個月' as useYear," +
		    			" (select z.codeName from sysca_code z where 1=1 and  z.codeKindID='CAA' and b.cause=z.codeID) as causeName, " +
		    			" b.cause1, " +
    					" b.notes " +
		    			" from UNTNE_REDUCEPROOF a " +
		    			" left join SYSCA_Organ c on a.enterOrg=c.organID," +
    			        " UNTNE_REDUCEDETAIL b,SYSPK_PropertyCode2 d  " +
    					" where a.enterorg=b.enterorg and a.caseno=b.caseno and b.enterorg=d.enterorg and b.propertyno=d.propertyno and d.propertytype='1'  ";
    	String queryCondition = "";
    	if (!"".equals(getQ_enterOrg())) {
    		queryCondition+=" and a.enterOrg = " + Common.sqlChar(getQ_enterOrg()) ;
    	} else {
    		if (!getIsAdminManager().equalsIgnoreCase("Y")) {
    			if ("Y".equals(getIsOrganManager())) {					
    				queryCondition += " and a.enterOrg like '" + getOrganID().substring(0,5) + "%' ";
    			} else {
    				queryCondition+=" and a.enterOrg = " + Common.sqlChar(getOrganID()) ;
    			}
    		}
    	}
    	
    	if (!"".equals(Common.get(getQ_caseNoS())))
    		queryCondition += " and a.caseno >= " + util.Common.sqlChar(getQ_caseNoS());
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
    	
    	
    	execSQL += queryCondition + " order by a.caseno, b.propertyno, b.serialno ";
    	
    	//System.out.println(execSQL);
    	
    	String[] kindName = null;
		if("1".equals(q_kind)){			kindName = new String[]{"第一聯"};
		}else if("2".equals(q_kind)){	kindName = new String[]{"第二聯"};
		}else if("3".equals(q_kind)){	kindName = new String[]{"第三聯"};
		}else if("4".equals(q_kind)){	kindName = new String[]{"第一聯","第二聯","第三聯"};
		}else if("5".equals(q_kind)){	kindName = new String[]{"第一聯","第二聯","第二聯 ","第三聯"};
		}
		
    	String tansToDouble;
    	String tansToDouble2;
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
		        	data[0] = rs.getString("enterOrgName");
		            data[1] = Common.get(rs.getString("differencekind"))+" "+differencekindMap.get(rs.getString("differencekind"));
		         	data[2] =  Common.get(rs.getString("caseno"));
		         	data[3] =  kindName[i];
		            data[4] = Common.MaskCDate(Datetime.changeTaiwanYYMMDD(rs.getString("writeDate"), "1"));
		            
		            data[5] =  Common.get(rs.getString("propertynoSerialno"))+"\n"+ Common.get(rs.getString("propertyName"));
//		            data[5] = ReportUtil.getStringWithOldSerialNo(this.getQ_enterOrg(), Common.get(data[5]), "原財產分號", Common.get(rs.getString("oldserialno")));
		            
		            data[6] =  Common.get(rs.getString("propertyName1"))+"\n"+ Common.get(rs.getString("specification"))+"\n"+ Common.get(rs.getString("nameplate"));
//		            if(rs.getString("propertyUnit")!=null && !"".equals(rs.getString("propertyUnit"))){
//		            	data[7] =   Common.get(rs.getString("propertyUnit"));
//		            }else{
		            	data[7] =   Common.get(rs.getString("otherPropertyUnit"));
//		            }
		            
		        
		            tansToDouble = rs.getString("adjustbookAmount");
		            if(tansToDouble!=null)data[8]= new Double(Double.parseDouble(tansToDouble));
		            else data[8]=null;
		            
		            data[9]= Common.get(st._getMoneyFormat(rs.getString("adjustbookUnit")))+"\n"+Common.get(st._getMoneyFormat(rs.getString("adjustBookValue")));
		      
		            DateTrn =  Datetime.changeTaiwanYYMMDD(rs.getString("sourceDate"), "1");
		            DateTrn2 = Datetime.changeTaiwanYYMMDD(rs.getString("buyDate"), "1"); 
		        	if(!"".equals(DateTrn) &&  !"".equals(DateTrn2))data[10]=DateTrn.substring(0,3)+"/"+DateTrn.substring(3,5)+"/"+DateTrn.substring(5,7)+"\n"+DateTrn2.substring(0,3)+"/"+DateTrn2.substring(3,5)+"/"+DateTrn2.substring(5,7);
		        	else data[10]=null;
		        	int otherLimitMonth = "".equals(Common.get(rs.getString("otherLimitYear")))? 0 : Integer.parseInt(rs.getString("otherLimitYear")); // otherLimitYear以單位月分存取需轉成年
					int year = otherLimitMonth /12;
					int month = otherLimitMonth % 12;
					data[11] = year +"年"+month+"個月"+ "\n" + Common.get(rs.getString("useYear"));
		        	 
		        	tansToDouble = rs.getString("adjustBookValue");
		            if(tansToDouble!=null)data[12]= new Double(Double.parseDouble(tansToDouble));
		            else data[12]=null;
    
		        	data[13] = Common.get(rs.getString("causeName")) ;
		        	if(!"".equals(Common.get(rs.getString("cause1")))){
		        		data[13] = data[13] + "/"+ Common.get(rs.getString("cause1"));
		        	}
		        	data[14] = new Double(Double.parseDouble(rs.getString("newBookValue"))) ;

		        	data[15] = Common.get(rs.getString("notes")) ;
		
		        	tansToDouble = rs.getString("oldBookValue");
		            if(tansToDouble!=null){
		            	data[16]= new Double(Double.parseDouble(tansToDouble));
		            }else{ 
		            	data[16]=null;
		            }
		            
		            data[17] = getQ_note();
		    		//單據備註
		    		data[18] = ut._queryData("notes")._from("UNTNE_REDUCEPROOF a ")._with(queryCondition)._toString();
		    		
		    		data[19] = Common.get(rs.getString("proofDoc"));
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