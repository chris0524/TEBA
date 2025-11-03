/*
*<br>程式目的：非消耗品減損單查詢檔 
*<br>程式代號：untne019r
*<br>撰寫日期：103/09/16
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
import util.*;
import util.report.ReportUtil;

public class UNTNE019R extends SuperBean{
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
	private String q_isPrintKeeper;
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
	public String getQ_isPrintKeeper() { return checkGet(q_isPrintKeeper); }
	public void setQ_isPrintKeeper(String q_isPrintKeeper) { this.q_isPrintKeeper = checkSet(q_isPrintKeeper); }

	String isOrganManager;
    String isAdminManager;
    String organID;    
    private String keeper;
    public String getOrganID() { return checkGet(organID); }
    public void setOrganID(String s) { organID = checkSet(s); }
    public String getIsOrganManager() { return checkGet(isOrganManager); }
    public void setIsOrganManager(String s) { isOrganManager = checkSet(s); }
    public String getIsAdminManager() { return checkGet(isAdminManager); }
    public void setIsAdminManager(String s) { isAdminManager = checkSet(s); } 
	public String getKeeper() { return checkGet(keeper); }
	public void setKeeper(String keeper) { this.keeper = checkSet(keeper); }
	
	public DefaultTableModel getResultModel() throws Exception{
	    DefaultTableModel model = new javax.swing.table.DefaultTableModel();
	    Database db = new Database();
	    UNTCH_Tools ut = new UNTCH_Tools();
	    String KOC = "\n(召集人或其授權代簽人)";
	    try{
	    	while(q_caseNoS!=null&&!"".equals(q_caseNoS)&&q_caseNoS.length()<10){q_caseNoS=q_caseNoS+"0";}
	    	while(q_caseNoE!=null&&!"".equals(q_caseNoE)&&q_caseNoE.length()<10){q_caseNoE=q_caseNoE+"9";}
	    	String[] columns = new String[] {"ENTERORGNAME","WRITEDATE","WRITEUNIT","PROOFDOC","KIND",
	    			                         "CASENO","SUMMONSNO","SOURCEDATEBUYDATE","PROPERTYNOSERIALNO","SPECIFICATIONNAMEPLATE",
	    			                         "PROPERTYUNIT","ADJUSTBOOKAMOUNT","OLDBOOKUNIT","ADJUSTBOOKVALUE","CAUSE",
	    			                         "RETURNPLACE","LIMITYEAR","USEYEAR","propertyKind","propertyKindName",
	    			                         "summonsdate","Q_NOTE", "TAIL01"};
	    	String execSQL=" select c.organaname as enterorgName," +       
					    	" (select z.codename from SYSCA_CODE z where 1=1 and (z.codekindid='owa' or z.codekindid='OWA') and a.ownership=z.codeid) as ownershipName,"  +
					    	" a.writedate," +                   
					    	" (select z.unitname from UNTMP_KEEPUNIT z where 1=1 and z.enterorg= a.enterorg and z.unitno=a.writeunit) as writeunit," +                   
					    	" a.proofyear+'年'+a.proofdoc+'字第'+a.proofno+'號' as proofdoc," +
					    	" a.caseno," +                   
					    	" b.sourcedate," +                    
					    	" b.buydate," +                   
					    	" b.propertyno+'-'+b.serialno as propertynoserialno," +
					    	" d.propertyname," +
					    	" case substring(b.propertyno,1,1) when '3' then '機械及設備' when '4' then '交通及運輸設備' when '5' then '雜項設備' end as propertytypename,"+
					    	" b.propertyname1, " +
					    	" b.specification, " +
					    	" b.nameplate, " +
					    	" d.propertyunit," +                   
					    	" b.otherpropertyunit," +                   
					    	" 1 as adjustbookamount," +                   
					    	" b.oldbookunit," +                   
					    	" b.adjustbookvalue," +                   
					    	" (select f.codename from SYSCA_CODE f where f.codekindid = 'CAA' and b.cause=f.codeid) as cause," +
					    	" b.cause1," +                   
					    	" b.returnplace," +                   
//					    	" d.limityear," +                   
					    	" b.otherlimityear," +                   
					    	" CONVERT(VARCHAR(4),b.useyear) + '年' +  CONVERT(VARCHAR(2),b.usemonth) + '個月' as useYear," +
					    	" a.summonsno," +
					    	" a.summonsdate," +  
					    	" b.propertykind," +       
					    	" b.oldserialno, " +
					    	" b.cause2, " +
					    	" b.keeper, " + 
					    	" a.notes, " +
					    	" (case b.propertykind when '01' then '公務用' when '02' then '公共用' when '03' then '事業用' when '04' then '非公用' else '' end) as propertykindName" +
					    	" from UNTNE_REDUCEPROOF a" +                   
					    	" left join SYSCA_ORGAN c on a.enterorg=c.organid," +
					    	" UNTNE_REDUCEDETAIL b,SYSPK_PROPERTYCODE2 d" + 
					    	" where a.enterorg=b.enterorg and a.caseno=b.caseno and a.ownership=b.ownership and b.enterorg=d.enterorg and b.propertyno=d.propertyno " +
					    	//" and d.enterOrg in ('000000000A',a.enterOrg) "+
					    	" and d.propertytype='1' ";
	    	String queryCondition = "";
	    	
	    	if (!"".equals(Common.get(getQ_ownership()))){
	    		queryCondition += " and a.ownership = " + util.Common.sqlChar(getQ_ownership());
	    	}
	    	if (!"".equals(getQ_enterOrg())) {
	    		queryCondition += " and a.enterorg = " + Common.sqlChar(getQ_enterOrg()) ;
	    	} else {
	    		if (!getIsAdminManager().equalsIgnoreCase("Y")) {
	    			if ("Y".equals(getIsOrganManager())) {					
	    				queryCondition += " and a.enterorg like '" + getOrganID().substring(0,5) + "%' ";
	    			} else {
	    				queryCondition += " and a.enterorg = " + Common.sqlChar(getOrganID()) ;
	    			}
	    		}
	    	}
	    	if (!"".equals(Common.get(getQ_caseNoS())))
	    		queryCondition += " and a.caseno >= " + util.Common.sqlChar(getQ_caseNoS());
	    	if (!"".equals(Common.get(getQ_caseNoE())))
	    		queryCondition += " and a.caseno <= " + util.Common.sqlChar(getQ_caseNoE());
	    	if (!"".equals(Common.get(getQ_writeDateS())))
	    		queryCondition += " and a.writedate >= " + util.Common.sqlChar(new Datetime().changeTaiwanYYMMDD(getQ_writeDateS(),"2"));
	    	if (!"".equals(Common.get(getQ_writeDateE())))
	    		queryCondition += " and a.writedate <= " + util.Common.sqlChar(new Datetime().changeTaiwanYYMMDD(getQ_writeDateE(),"2"));
	    	
	    	if (!"".equals(Common.get(getQ_proofYear()))){
	    		queryCondition += " and a.proofyear = "  + Common.sqlChar(getQ_proofYear());
	    	}
	    	
	    	if (!"".equals(Common.get(getQ_proofDoc()))){
	    		queryCondition += " and a.proofdoc like " + util.Common.sqlChar("%"+getQ_proofDoc()+"%");
	    	}
	    	if (!"".equals(Common.get(getQ_proofNoS())))
	    		queryCondition += " and a.proofno >= " + util.Common.sqlChar(getQ_proofNoS());
	    	if (!"".equals(Common.get(getQ_proofNoE())))
	    		queryCondition += " and a.proofno <= " + util.Common.sqlChar(getQ_proofNoE());
	    	
	    	execSQL += queryCondition + " order by a.caseno,b.propertykind,b.propertyno,b.serialno";
            System.out.println(execSQL);
	
	
	    	String[] kindName = null;
			if("1".equals(q_kind)){			kindName = new String[]{"第一聯"};
			}else if("2".equals(q_kind)){	kindName = new String[]{"第二聯"};
			}else if("3".equals(q_kind)){	kindName = new String[]{"第三聯"};
			}else if("4".equals(q_kind)){	kindName = new String[]{"第一聯","第二聯","第三聯"};
			}else if("5".equals(q_kind)){	kindName = new String[]{"第一聯","第二聯","第二聯 ","第三聯"};
			}
			
	    	String tansToDouble;
	    	String causetemp;
	    	String limitYeartemp;
	    	String PropertyUnit;
	
			 Vector rowData = new Vector();
				
	    	 if(kindName != null){
		    	   for(int i=0; i<kindName.length; i++){
		    		   	ResultSet rs = db.querySQL(execSQL);
				        while(rs.next()){
				        	
				        	String keeper = Common.get(rs.getString("keeper"));
							if ("Y".equals(this.getQ_isPrintKeeper()) && "".equals(this.getKeeper()) && !"".equals(keeper)) {
								String keepername = View.getLookupField("select keepername from UNTMP_KEEPER where keeperno = " + Common.sqlChar(keeper) + " and enterorg = " + Common.sqlChar(this.getQ_enterOrg()));
					    		this.setKeeper(Common.get(keepername));
							}
				        	
				        	Object[] data = new Object[columns.length];
				            data[0] = Common.get(rs.getString("enterOrgName"));
				            data[1] = Common.MaskCDate(Datetime.changeTaiwanYYMMDD(rs.getString("writeDate"), "1"));
				        	data[2] = Common.get(rs.getString("writeUnit"));
				        	data[3] = Common.get(rs.getString("proofDoc"));
				        	data[4] = kindName[i];
				            data[5] = Common.get(rs.getString("caseNO"));
				            data[6] = Common.get(rs.getString("summonsNo"))+"　";
				            data[7] = Common.MaskDate(Datetime.changeTaiwanYYMMDD(rs.getString("sourceDate"), "1")) + "\n" + Common.MaskDate(Datetime.changeTaiwanYYMMDD(rs.getString("buyDate"), "1"));
				            
				            data[8] = Common.get(rs.getString("propertynoSerialNo"));
//				            data[8] = ReportUtil.getStringWithOldSerialNo(this.getQ_enterOrg(), Common.get(data[8]), "原物品分號 ", Common.get(rs.getString("oldserialno")));
				            data[8] = data[8] + "\n" + Common.get(rs.getString("propertyName")) + "\n" + Common.get(rs.getString("propertytypename"));
				            
				            data[9] = Common.get(rs.getString("propertyname1"))+ "\n" + Common.get(rs.getString("specification"))+ "\n" +Common.get(rs.getString("nameplate"));
				            if("".equals(Common.get(rs.getString("otherPropertyUnit")))){
				            	data[10] = Common.get(rs.getString("propertyUnit"));
				            }else{
				            	data[10] = Common.get(rs.getString("otherPropertyUnit"));
				            }
				            tansToDouble = rs.getString("adjustBookAmount");
				            if(tansToDouble!=null)data[11]= new Double(Double.parseDouble(tansToDouble));
				            else data[11]=null;
				            tansToDouble = rs.getString("oldBookUnit");
				            if(tansToDouble!=null)data[12]= new Double(Double.parseDouble(tansToDouble));
				            else data[12]=null;
				            tansToDouble = rs.getString("adjustBookValue");
				            if(tansToDouble!=null)data[13]= new Double(Double.parseDouble(tansToDouble));
				            else data[13]=null;
				            data[14] = Common.get(rs.getString("cause"));
				            causetemp= rs.getString("cause1");
				            if("其他".equals(data[14])||data[14]==null)data[14]=causetemp;
				            
				            String cause2 = Common.get(rs.getString("cause2"));
				            if (!"".equals(cause2)) {
				            	data[14] = data[14] + "\r\n" + cause2;
				            }
				            
				            data[15] = Common.get(rs.getString("returnPlace"));
				            int otherLimitMonth = "".equals(Common.get(rs.getString("otherLimitYear")))? 0 : Integer.parseInt(rs.getString("otherLimitYear")); // otherLimitYear以單位月分存取需轉成年
							int year = otherLimitMonth /12;
							int month = otherLimitMonth % 12;
							data[16] = year +"年"+month+"個月";
				        	data[17] = Common.get(rs.getString("useYear"));
				            data[18] = Common.get(rs.getString("propertyKind"));
				            data[19] = Common.get(rs.getString("propertyKindName"));
				            //傳票日期
				            String summonsdate = Common.get(rs.getString("summonsdate"));
				            if(!summonsdate.equals("")){
				            	data[20] = ut._transToROC_Year(rs.getString("summonsdate")).substring(0,3) + "年" + ut._transToROC_Year(rs.getString("summonsDate")).substring(3,5) + "月" + ut._transToROC_Year(rs.getString("summonsDate")).substring(5) + "日";}
				            else{
				            	data[20] = "";
				            }
				            
				            data[21] = getQ_note();
				            
				    		//單據備註
				            if("Y".equals(getQ_note())) {
				            	data[22] = Common.get(rs.getString("notes"));
				            }else {
				            	data[22] = "";
				            }
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