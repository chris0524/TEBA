/*
*<br>程式目的：非消耗品移動單報表檔 
*<br>程式代號：untne011r
*<br>撰寫日期：103/09/15
*<br>程式作者：Cherry
*<br>--------------------------------------------------------
*<br>修改作者　　修改日期　　　修改目的
*<br>--------------------------------------------------------
*/

package unt.ne;

import java.sql.ResultSet;
import java.text.DecimalFormat;
import java.util.Map;
import java.util.Vector;

import javax.swing.table.DefaultTableModel;

import unt.ch.UNTCH_Tools;
import util.Common;
import util.Database;
import util.Datetime;
import util.SuperBean;
import util.TCGHCommon;

public class UNTNE011R extends SuperBean{

	String enterOrg;
	String ownership;
	String caseNoS;
	String caseNoE;
	String writeDateS;
	String writeDateE;
	String proofDoc;
	String proofNoS;
	String proofNoE;
	
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
	private String q_reportType;						// 報表類型
	
	public String getQ_reportType() { return checkGet(q_reportType); }
	public void setQ_reportType(String q_reportType) { this.q_reportType = checkGet(q_reportType); }
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

	String isOrganManager;
    String isAdminManager;
    String organID;    
    DecimalFormat amount = new DecimalFormat("###,###,###,##0.00");
    
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
    	String[] columns = new String[] {"ENTERORGNAME","WRITEDATE","WRITEUNIT","PROOFDOC","Q_KIND","CASENO",
						    			 "SOURCEBUYDATE","NEWMOVEDATE","PROPERTYNOSERIALNO",
						    			 "NAMEPLATESPECIFICATION","BOOKAMOUNTPROPERTYUNIT",
						    			 "OLDKEEPUNITNAME","OLDKEEPER",
						    			 "NEWKEEPUNITNAME","NEWKEEPER","NEWPLACE",
						    			 "BOOKUNITBOOKVALUE","LIMITYEAR","USEYEARUSEMONTH",
						    			 "TOTALCOUNT","SUMBOOKUNITBOOKVALUE","Q_NOTE", "TAIL01"
						    			 };
    	String execSQL="select c.organAName as enterOrgName, a.enterorg, " +
    			" b.lotNO,"+
    			" a.writeDate," +
    			" a.writeUnit," +
    			" a.proofYear+'年'+a.proofDoc+'字第'+a.proofNo+'號' as proofDoc," +
    			" a.ownership, " +
    			" (select z.codeName from sysca_code z where 1=1 and (z.codeKindID='owa' or z.codeKindID='OWA') and a.ownership=z.codeID) as ownershipName, " +
    			" a.caseNO, " +
    			" b.sourceDate, " +
    			" b.buyDate, " +
    			" a.moveDate, " +
    			" b.propertyNo, " +
    			" b.serialNo, "+
    			" d.propertyName, " +
    			" b.propertyName1, " +
    			" b.specification, " +
    			" b.nameplate, " +
    			" d.propertyUnit, b.otherPropertyUnit, " +
    			" b.bookAmount, b.bookUnit, b.bookValue, " +
    			" (select c.unitName from UNTMP_KeepUnit c where b.enterOrg=c.enterOrg and b.newKeepUnit=c.unitNo) as newKeepUnitName, " +
    			" (select c.unitName from UNTMP_KeepUnit c where b.enterOrg=c.enterOrg and b.oldKeepUnit=c.unitNo) as oldKeepUnitName, " +
    			" b.oldkeeper, " +
    			" b.newkeeper, " +
    			" b.newPlace, " + //存置地點說明
    			" b.newPlace1, " + //存置地點代碼
    			" b.otherLimitYear, " +
    			" b.useYear, b.useMonth, " +
    			" b.propertyKind," +
    			" (case b.propertyKind when '01' then '公務用' when '02' then '公共用' when '03' then '事業用' when '04' then '非公用' else '' end) propertyKindName, "+
    			" b.differencekind, " +
    			" b.oldserialno " +
    			" from UNTNE_MoveProof a"+
    			" left join SYSCA_Organ c on a.enterOrg=c.organID,"+
    			" UNTNE_MoveDetail b,SYSPK_PropertyCode2 d "+
    			" where a.enterorg=b.enterorg and a.caseno=b.caseNo and a.ownership=b.ownership " +
    			" and b.propertyNo=d.propertyNo "+
    			" and d.enterOrg=b.enterOrg "+
    			"";
    	String queryCondition = "";
//System.out.println("-- untne011r getResultModel --\n"+execSQL);    	
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
    	if (!"".equals(Common.get(getQ_ownership()))){
    		queryCondition += " and a.ownership = " + util.Common.sqlChar(getQ_ownership());
    	}
    	if (!"".equals(Common.get(getQ_caseNoS())))
    		queryCondition += " and a.caseNo >= " + util.Common.sqlChar(getQ_caseNoS());
    	if (!"".equals(Common.get(getQ_caseNoE())))
    		queryCondition += " and a.caseNo <= " + util.Common.sqlChar(getQ_caseNoE());
    	
    	if (!"".equals(Common.get(getQ_writeDateS())))
    		queryCondition += " and a.writeDate >= " + util.Common.sqlChar(new Datetime().changeTaiwanYYMMDD(getQ_writeDateS(),"2"));
    	if (!"".equals(Common.get(getQ_writeDateE())))
    		queryCondition += " and a.writeDate <= " + util.Common.sqlChar(new Datetime().changeTaiwanYYMMDD(getQ_writeDateE(),"2"));
    	
    	if (!"".equals(Common.get(getQ_proofYear()))){
    		queryCondition += " and a.proofyear = "  + Common.sqlChar(getQ_proofYear());
    	}
    	
    	if (!"".equals(Common.get(getQ_proofDoc()))){
    		queryCondition += " and a.proofDoc like " + util.Common.sqlChar("%"+getQ_proofDoc()+"%");
    	}
    	if (!"".equals(Common.get(getQ_proofNoS())))
    		queryCondition += " and a.proofNo >= " + util.Common.sqlChar(getQ_proofNoS());
    	if (!"".equals(Common.get(getQ_proofNoE())))
    		queryCondition += " and a.proofNo <= " + util.Common.sqlChar(getQ_proofNoE());
    	
    	execSQL += queryCondition + " order by a.caseNo,b.propertyKind,b.differencekind,b.propertyno,b.serialno";
    	//String execSQL2="select a.caseno,count(*) "+execSQL1+" group by a.caseno order by a.caseno";
    	
	    	String[] kindName = null;
			if("1".equals(q_kind)){			kindName = new String[]{"第一聯"};
			}else if("2".equals(q_kind)){	kindName = new String[]{"第二聯"};
			}else if("3".equals(q_kind)){	kindName = new String[]{"第三聯"};
			}else if("4".equals(q_kind)){	kindName = new String[]{"第一聯","第二聯","第三聯"};
			}else if("5".equals(q_kind)){	kindName = new String[]{"第一聯","第二聯","第二聯 ","第三聯"};
			}
			
			Vector rowData = new Vector();
			UNTCH_Tools ut = new UNTCH_Tools();
			if(kindName != null){
				//使用者操作記錄
				Common.insertRecordSql(execSQL, this.getOrganID(), this.getUserID(), "UNTNE011R", this.getObjPath().replaceAll("&gt;", ">"));
				
		    	for(int i=0; i<kindName.length; i++){
		    		
			        ResultSet rs = db.querySQL(execSQL);
		
			        int sumBookUnit = 0;
			        int sumBookValue = 0;
					int count = 0;
				
				        while(rs.next()){
				        	Map<String,String> keeperMap = TCGHCommon.getUntmp_KeeperCode(rs.getString("enterorg"));
				        	Map<String,String> placeMap = TCGHCommon.getSysca_PlaceCode(rs.getString("enterorg"));
				        	Map<String,String> unitMap = TCGHCommon.getUntmp_KeepUnitCode(rs.getString("enterorg"));
				        	count++;
				        	Object[] data = new Object[columns.length];
				        	data[0] = rs.getString("enterOrgName");
				            data[1] = Common.MaskCDate(Datetime.changeTaiwanYYMMDD(rs.getString("writeDate"), "1"));
				            data[2] = unitMap.get(Common.get(rs.getString("writeUnit")));
				        	data[3] = Common.get(rs.getString("proofDoc"));
				            data[4] = kindName[i];
				            data[5] = Common.get(rs.getString("caseNo"));
				            data[6] = Common.MaskDate(Datetime.changeTaiwanYYMMDD(rs.getString("sourceDate"), "1")) + "\n" + Common.MaskDate(Datetime.changeTaiwanYYMMDD(rs.getString("buyDate"), "1"));
				            data[7] = Common.MaskDate(Datetime.changeTaiwanYYMMDD(rs.getString("moveDate"), "1"));
				            String serialNo = "";
				            if(rs.getString("serialNo") != null ){
				            	serialNo = "("+rs.getString("serialNo") +")";
				            }
				            data[8] = Common.get(rs.getString("differencekind")) + "-" + Common.get(rs.getString("propertyNo"))+serialNo;
//				            data[8] = ReportUtil.getStringWithOldSerialNo(this.getQ_enterOrg(), Common.get(data[8]), "原物品分號 ", Common.get(rs.getString("oldserialno")));
				            
				            data[8] = data[8] + "\n"+ Common.get(rs.getString("propertyName"))+"\n"+ Common.get(rs.getString("propertyName1"));
				            data[9] = Common.get(rs.getString("nameplate"))+"\n"+Common.get(rs.getString("specification"));
				            
				            
				            if(rs.getString("propertyUnit")!=null && !"".equals(rs.getString("propertyUnit"))){
				            	data[10] = amount.format(rs.getDouble("bookAmount")) +"\n"+ Common.get(rs.getString("propertyUnit"));
				            }else{
				            	data[10] = Common.valueFormat(rs.getString("bookAmount")) +"\n"+ Common.get(rs.getString("otherPropertyUnit"));
				            }
				            data[11] = Common.get(rs.getString("oldKeepUnitName"));
				            data[12] = keeperMap.get(Common.get(rs.getString("oldkeeper")));
				            data[13] = Common.get(rs.getString("newKeepUnitName"));
				            data[14] = keeperMap.get(Common.get(rs.getString("newkeeper")));
				            String placeName = placeMap.get(Common.get(rs.getString("newPlace1")));
				            if (!"".equals(placeName)) {
				            	data[15] = placeName;
				            } else {
				            	data[15] = Common.get(rs.getString("newPlace"));
				            }
				            
				            
				            data[16] = Common.valueFormat(Common.get(rs.getString("bookUnit"))) +"\n"+ Common.valueFormat(rs.getString("bookValue"));    
				            
				            int otherLimitMonth = "".equals(Common.get(rs.getString("otherLimitYear")))? 0 : Integer.parseInt(rs.getString("otherLimitYear")); // otherLimitYear以單位月分存取需轉成年
							int year = otherLimitMonth /12;
							int month = otherLimitMonth % 12;
							data[17] = year +"年"+month+"個月";
				            if(rs.getString("useYear")!=null && !"".equals(rs.getString("useYear")) && rs.getInt("useYear")!=0){
				            	data[18] = Common.get(rs.getString("useYear"))+"年\n"+Common.get(rs.getString("useMonth"))+"個月";
				            }else{
				            	data[18] = Common.get(rs.getString("useMonth"))+"個月";
				            }
				            
				            data[19] = String.valueOf(count);
					    	
				            sumBookUnit += rs.getInt("bookUnit");
				            sumBookValue += rs.getInt("bookValue");
//					    	data[20] = Common.valueFormat(Common.get(String.valueOf(sumBookUnit))) +"\n"+ Common.valueFormat(Common.get(String.valueOf(sumBookValue)));
				            data[20] = Common.valueFormat(Common.get(String.valueOf(sumBookValue)));
					    	data[21] = getQ_note();
					    	//單據備註
					  	    data[22] = ut._queryData("notes")._from("UNTNE_MoveProof a ")._with(queryCondition)._toString();
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