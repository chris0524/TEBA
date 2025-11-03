package unt.rp;

import java.sql.ResultSet;
import java.util.ArrayList;

import javax.swing.table.DefaultTableModel;

import org.apache.log4j.Logger;

import unt.ch.UNTCH004F01;
import unt.ch.UNTCH_Tools;
import util.Common;
import util.Database;
import util.Datetime;
import util.SuperBean;
import util.report.ReportUtil;
import TDlib_Simple.tools.src.StringTools;

/*
*<br>程式目的：財產撥出單
*<br>程式代號：UNTRP007R
*<br>撰寫日期：
*<br>程式作者：
*<br>--------------------------------------------------------
*<br>修改作者　　修改日期　　　修改目的
*<br>--------------------------------------------------------
*/
public class UNTRP007R extends SuperBean {
	
	//#region fields 
	private String isOrganManager;
	private String isAdminManager;
	private String organID;
	private String editID;
	private String username;
    public String getOrganID() { return checkGet(organID); }
    public void setOrganID(String s) { organID = checkSet(s); }
    public String getIsOrganManager() { return checkGet(isOrganManager); }
    public void setIsOrganManager(String s) { isOrganManager = checkSet(s); }
    public String getIsAdminManager() { return checkGet(isAdminManager); }
    public void setIsAdminManager(String s) { isAdminManager = checkSet(s); }
	public String getEditID() {return checkGet(editID);}
	public void setEditID(String editID) {this.editID = checkSet(editID);}   
	public String getUsername() { return checkGet(username); }
	public void setUsername(String username) { this.username = checkSet(username); }

	private String q_enterOrg;
	private String q_enterOrgName;
	private String q_ownership;
	private String q_differenceKind;
	private String q_caseNoE;
	private String q_proofYear;
	private String q_adjustDateS;
	private String q_adjustDateE;
	private String q_proofDoc;
	private String q_proofNoS;
	private String q_proofNoE;
	private String q_kind;
	private String q_note;
	private String q_reportType;						// 報表類型
	
	public String getQ_reportType() { return checkGet(q_reportType); }
	public void setQ_reportType(String q_reportType) { this.q_reportType = checkGet(q_reportType); }
	public String getQ_note() {return checkGet(q_note);}
	public void setQ_note(String q_note) {this.q_note = checkSet(q_note);}
	public String getQ_enterOrg(){ return checkGet(q_enterOrg); }
	public void setQ_enterOrg(String s){ q_enterOrg=checkSet(s); }
	public String getQ_enterOrgName(){ return checkGet(q_enterOrgName); }
	public void setQ_enterOrgName(String s){ q_enterOrgName=checkSet(s); }
	public String getQ_ownership(){ return checkGet(q_ownership); }
	public void setQ_ownership(String s){ q_ownership=checkSet(s); }
	public String getQ_differenceKind() {return checkGet(q_differenceKind);}
	public void setQ_differenceKind(String q_differenceKind) {this.q_differenceKind = checkSet(q_differenceKind);}	String q_caseNoS;
	public String getQ_caseNoS(){ return checkGet(q_caseNoS); }
	public void setQ_caseNoS(String s){ q_caseNoS=checkSet(s); }
	public String getQ_caseNoE(){ return checkGet(q_caseNoE); }
	public void setQ_caseNoE(String s){ q_caseNoE=checkSet(s); }
	public String getQ_proofYear() {return checkGet(q_proofYear);}
	public void setQ_proofYear(String q_proofYear) {this.q_proofYear = checkSet(q_proofYear);}
	public String getQ_adjustDateS(){ return checkGet(q_adjustDateS); }
	public void setQ_adjustDateS(String s){ q_adjustDateS=checkSet(s); }
	public String getQ_adjustDateE(){ return checkGet(q_adjustDateE); }
	public void setQ_adjustDateE(String s){ q_adjustDateE=checkSet(s); }
	public String getQ_proofDoc(){ return checkGet(q_proofDoc); }
	public void setQ_proofDoc(String s){ q_proofDoc=checkSet(s); }
	public String getQ_proofNoS(){ return checkGet(q_proofNoS); }
	public void setQ_proofNoS(String s){ q_proofNoS=checkSet(s); }
	public String getQ_proofNoE(){ return checkGet(q_proofNoE); }
	public void setQ_proofNoE(String s){ q_proofNoE=checkSet(s); }
	public String getQ_kind(){ return checkGet(q_kind); }
	public void setQ_kind(String s){ q_kind=checkSet(s); }
	//#end 
	
	public DefaultTableModel getResultModel2() throws Exception{
	    DefaultTableModel model = null;
	    Database db = null;
	    ResultSet rs = null;
	    ArrayList<Object[]> rowData = null;
	    StringBuilder stb = new StringBuilder();
	    try{
	    	String[] columns = new String[] {"f0", "f1", "f2", "f3", "f4", "f5", "f6", "f7", "f8", "f9", 
	    									 "f10", "f11", "f12", "f13", "f14", "f15", "f16", "f17", "f18", "f19", 
	    									 "f20", "f21", "f22", "f23", "f24", "f25"};
		
			//=================================================================
			stb.append(" select * ");    	
			stb.append(" ,(select top 1 unitname from UNTMP_KEEPUNIT where enterorg=a.enterorg and unitno=a.writeunit) as writeunitname ");
			stb.append(" ,(select top 1 codename from SYSCA_CODE where codekindid ='DFK' and codeid=a.differencekind) as dfkname ");
			stb.append(" ,(select top 1 z.propertyname from SYSPK_PROPERTYCODE z where 1=1 and enterorg in (a.enterorg,'000000000A') and propertyno=a.propertyno) as propertyname ");
			stb.append(" ,(select top 1 z.propertyunit from SYSPK_PROPERTYCODE z where 1=1 and enterorg in (a.enterorg, '000000000A') and propertyno=a.propertyno) as propertyunit ");
			stb.append(" from (");
			stb = doGetSQLforUNTLA_ReduceDetail(stb);    	
			stb.append(doGetIfCondition("LA"));
			stb.append(" union ");
			stb = doGetSQLforUNTBU_ReduceDetail(stb);
			stb.append(doGetIfCondition("BU"));
			stb.append(" union ");
			stb = doGetSQLforUNTRF_ReduceDetail(stb);
			stb.append(doGetIfCondition("RF"));
			stb.append(" union ");
			stb = doGetSQLforUNTMP_ReduceDetail(stb);
			stb.append(doGetIfCondition("MP"));
			stb.append(" union ");
			stb = doGetSQLforUNTVP_ReduceProof(stb);
			stb.append(doGetIfCondition("VP"));
			stb.append(" union ");
			stb = doGetSQLforUNTRT_ReduceProof(stb);
			stb.append(doGetIfCondition("RT"));
			stb.append(") a");
			
			stb.append(" order by enterorg, caseno,propertyNo");
			
			UNTCH_Tools ut = new UNTCH_Tools();
			StringTools st = new StringTools();
			
			String[] kindNames = null;
			if("1".equals(q_kind)){			kindNames = new String[]{"第一聯"};
			}else if("2".equals(q_kind)){	kindNames = new String[]{"第二聯"};
			}else if("3".equals(q_kind)){	kindNames = new String[]{"第三聯"};
			}else if("4".equals(q_kind)){	kindNames = new String[]{"第一聯","第二聯","第三聯"};
			}else if("5".equals(q_kind)){	kindNames = new String[]{"第一聯","第二聯","第二聯","第三聯"};
    		}
			
			db = new Database();
			
		    
		    // 問題單1396: 檢查是否為最新資料
	    	String checksql = "select distinct enterorg,caseno,verify from(" + stb.toString().substring(0, stb.indexOf("order by")) +") as base";
	    	rs = db.querySQL(checksql);
	    	UNTCH004F01 untch004f01 = new UNTCH004F01();
	    	while(rs.next()){
	    		untch004f01.setEnterOrg(Common.get(rs.getString("enterorg")));
	    		untch004f01.setCaseNo(Common.get(rs.getString("caseno")));
	    		if("N".equals(Common.get(rs.getString("verify"))) && untch004f01.checkNewData()){
	    			this.setErrorMsg("減損明細資料非最新帳面淨值，請先至財產減損單維護執行[帶入最新資料]");
	    			return null;
	    		}
	    	}
	    	
	    	String reportTitle = ReportUtil.getTitleByEnterOrgCodeNew(this.getQ_enterOrg(), this.getQ_differenceKind());
		    
		    if (kindNames != null) {
		    	rs = db.querySQL(stb.toString(), true);	
		    	rowData = new ArrayList<Object[]>();
		    	
		    	String datetime = Datetime.getYYYMMDD();
		    	String printDate = Common.MaskCDate(datetime);
				for (String kindname : kindNames) {
					
				    while (rs.next()) {
				    	Object[] data = new Object[columns.length];
				    	
				    	// 印表日期
				    	data[0] = printDate;
				    	
				    	//填單日期
				    	data[1] = Common.MaskCDate(ut._transToROC_Year(rs.getString("writedate")));
				    	
				    	//填造單位
				    	data[2] = Common.get(rs.getString("writeunitname"));
				    	
				    	//編號
				    	data[3] = Common.get(rs.getString("proofyear")) + "年" + Common.get(rs.getString("proofdoc")) + "字第" + Common.get(rs.getString("proofno")) + "號";
				    	
				    	//印表人
				    	data[4] = this.getUsername();
				    	
				    	//財產區分別
				    	data[5] = Common.get(rs.getString("differencekind")) + " " + Common.get(rs.getString("dfkname"));
				    	
				    	//全銜
				    	data[6] = reportTitle;
				    	
				    	//第　　聯
				    	data[7] = kindname;
				    	
				    	//電腦單號
				    	data[8] = checkGet(rs.getString("caseNo"));
				    	
				    	//財產編號 (含分號) 財產名稱
				    	data[9] = checkGet(rs.getString("propertyNo")) + "－" + checkGet(rs.getString("serialNo")) + "\n" + Common.get(rs.getString("propertyname"));
				    	//別名/型式
				    	String check_propertyName1 = checkGet(rs.getString("propertyName1"));
				    	String check_specification = checkGet(rs.getString("specification"));
							
				    	data[10] =  check_propertyName1 + (("".equals(check_specification))?"":"/" + check_specification);
				    	//單位
				    	String unitName = "";
				    	if("2".equals(Common.checkGet(rs.getString("propertyNo")).substring(0,1))){
				    		unitName = "平方公尺";
				    	}else{
				    		unitName = Common.get(rs.getString("propertyunit"));
				    	}
				    	data[11] = unitName;
				    	
				    	//數量
				    	data[12] = checkGet(rs.getString("amount"));
				    	
				    	//取得日期 購置日期
				    	data[13] = Common.MaskDate(ut._transToROC_Year(checkGet(rs.getString("sourceDate")))) + "\n" + 
				    			   Common.MaskDate(ut._transToROC_Year(checkGet(rs.getString("buyDate"))));
				    	//使用年限 
				    	int otherLimitMonth = "".equals(Common.get(rs.getString("otherLimitYear"))) ? 0 : Integer.parseInt(rs.getString("otherLimitYear")); // otherLimitYear以單位月分存取需轉成年
						data[14] = Common.MonthsFormat(otherLimitMonth);
				    	
				    	//已使用年數
						String useYear = "".equals(checkGet(rs.getString("useYear")))? "0": checkGet(rs.getString("useYear"));
						String usemonth = "".equals(checkGet(rs.getString("usemonth")))? "0": checkGet(rs.getString("usemonth"));
				    	data[15] = useYear + "年" + usemonth + "個月";
				    	//單價  帳面價值
				    	data[16] = st._getMoneyFormat(checkGet(rs.getString("bookUnit")))+ "\n" +
				    			   st._getMoneyFormat(checkGet(rs.getString("bookValue")));
				    	//累計折舊 總價 
				    	data[17] = st._getMoneyFormat("".equals(checkGet(rs.getString("accumdepr")))?"0":rs.getString("accumdepr"))+"\n"+
				    			   st._getMoneyFormat(checkGet(rs.getString("netValue")));
				    	//移撥原因
				    	data[18] = checkGet(rs.getString("causeName"))+"/"+checkGet(rs.getString("cause2"));
				    	
				    	
				    	//===========================================================
				    	//						Total
				    	//===========================================================
				    	// 數量 加總用
				    	data[19] = Common.getNumeric(rs.getString("amount"));
				    	
				    	// 單價 加總用
				    	data[20] = Common.getNumeric(rs.getString("bookunit"));
				    	
				    	// 總價 加總用
				    	data[21] = Common.getNumeric(rs.getString("bookValue"));
				    	
				    	// 帳面價值 加總用
				    	data[22] = Common.getNumeric(rs.getString("netvalue"));
				    	
				    	// 累計折舊 加總用
				    	data[23] = Common.getNumeric(rs.getString("accumdepr"));
				    		
				    	// 是否列印備註
				    	data[24] = this.getQ_note();
				    	if ("Y".equals(this.getQ_note())) {
				    		data[25] = Common.get(rs.getString("notes"));
				    	}
				    	
				    	rowData.add(data);
				    }
				    
				    
				    rs.beforeFirst();
				}
		    }
			
		    if (rowData != null && rowData.size() > 0) {
		    	model = new DefaultTableModel(rowData.toArray(new Object[rowData.size()][columns.length]), columns);
		    }
	    } finally {
	    	if (rs != null) {
	    		try {
	    			rs.close();
	    			rs = null;
	    		} catch (Exception e) {
	    			//ignore
	    		}
	    	}
	        db.closeAll();
	    }
	    return model;
	}
	
		
	//=================================================================
	//	查詢條件
	//=================================================================
		private String doGetIfCondition(String table){
			StringTools st = new StringTools();
			UNTCH_Tools ut = new UNTCH_Tools();
			StringBuilder stb = new StringBuilder();
			
			if(!st._isEmpty(getQ_enterOrg())){
				stb.append(" and a.enterOrg = ").append(Common.sqlChar(getQ_enterOrg()));
			}
			if(!st._isEmpty(getQ_ownership())){
				stb.append(" and a.ownership = ").append(Common.sqlChar(getQ_ownership()));
			}
			if(!st._isEmpty(getQ_caseNoS())){
				stb.append(" and a.caseNo >= ").append(Common.sqlChar(getQ_caseNoS()));
			}
			if(!st._isEmpty(getQ_caseNoE())){
				stb.append(" and a.caseNo <= ").append(Common.sqlChar(getQ_caseNoE()));
			}
			
			String alias = "z.";
			if("VP".equals(table) || "RT".equals(table) || "".equals(table)){
				alias = "a.";
			}
			
			if(!st._isEmpty(getQ_adjustDateS())){
				if("VP".equals(table)){
					stb.append(" and " + alias + "adjustDate >= ").append(Common.sqlChar(ut._transToCE_Year(getQ_adjustDateS())));
				}else{
					stb.append(" and " + alias + "reduceDate >= ").append(Common.sqlChar(ut._transToCE_Year(getQ_adjustDateS())));
				}			}
			if(!st._isEmpty(getQ_adjustDateE())){
				if("VP".equals(table)){
					stb.append(" and " + alias + "adjustDate <= ").append(Common.sqlChar(ut._transToCE_Year(getQ_adjustDateE())));
				}else{
					stb.append(" and " + alias + "reduceDate <= ").append(Common.sqlChar(ut._transToCE_Year(getQ_adjustDateE())));
				}			}
			if(!st._isEmpty(getQ_proofYear())){
				stb.append(" and " + alias + "proofYear = ").append(Common.sqlChar(getQ_proofYear()));
			}
			if(!st._isEmpty(getQ_proofDoc())){
				stb.append(" and " + alias + "proofDoc = ").append(Common.sqlChar(getQ_proofDoc()));
			}
			if(!st._isEmpty(getQ_proofNoS())){
				stb.append(" and " + alias + "proofNo >= ").append(Common.sqlChar(getQ_proofNoS()));
			}
			if(!st._isEmpty(getQ_proofNoE())){
				stb.append(" and " + alias + "proofNo <= ").append(Common.sqlChar(getQ_proofNoE()));
			}
	    		
			return stb.toString();
		}
	
	//=================================================================
	//	各Table的查詢SQL組成
	//=================================================================
		
		private StringBuilder doGetSQLforUNTLA_ReduceDetail(StringBuilder stb){
			stb.append("select ").append(
					" 'LA' as type, ").append(
							" a.enterOrg, ").append(
							" a.caseNo, ").append(
							" a.verify, ").append(
							" a.sourceDate, ").append(
							" a.buyDate, ").append(
							" a.propertyNo, ").append(
							" a.serialNo, ").append(
							" a.propertyName1, ").append(
							" null as specification, ").append(
							" a.holdarea as amount, ").append(
							" a.bookunit, ").append(
							" a.bookvalue, ").append(
							" a.netvalue, ").append(		
							" (select codename from SYSCA_CODE z where codekindid = 'CAA' and z.codeid = a.cause and codecon1 in (2,4)) as causeName, ").append(
							" a.cause1 as cause2,").append(
							" null as otherLimitYear, ").append(
							" null as useYear, ").append(
							" null as usemonth, ").append(
							" null as accumDepr ").append(			
							" ,z.writedate ,z.writeunit ,z.proofyear ,z.proofdoc ,z.proofno ,z.differencekind ,z.notes ").append(
						"from UNTLA_REDUCEDETAIL a").append(
							" left join UNTLA_REDUCEPROOF z on a.enterorg = z.enterorg and a.ownership = z.ownership and a.caseno = z.caseno and a.differencekind = z.differencekind ").append(
							" where 1=1 ").append(
						""); 
			return stb;
		}
		
		private StringBuilder doGetSQLforUNTBU_ReduceDetail(StringBuilder stb){
			stb.append("select ").append(
							" 'BU' as type, ").append(
							" a.enterOrg, ").append(
							" a.caseNo, ").append(
							" a.verify, ").append(
		    				" a.sourceDate, ").append(
		    				" a.buyDate, ").append(
							" a.propertyNo, ").append(
							" a.serialNo, ").append(
							" a.propertyName1, ").append(
							" null as specification, ").append(
							" a.holdarea as amount, ").append(
							" null as bookunit, ").append(
							" a.bookvalue, ").append(
							" a.netvalue, ").append(		
							" (select codename from SYSCA_CODE z where codekindid = 'CAA' and z.codeid = a.cause and codecon1 in (2,4)) as causeName, ").append(
							" cause2,").append(
							" a.otherLimitYear, ").append(
							" a.useYear, ").append(
							" a.usemonth, ").append(
							" isnull(a.oldaccumdepr ,'0') as accumdepr ").append(
							" ,z.writedate ,z.writeunit ,z.proofyear ,z.proofdoc ,z.proofno ,z.differencekind ,z.notes ").append(
		    			" from UNTBU_REDUCEDETAIL a").append(
							" left join UNTBU_REDUCEPROOF z on a.enterorg = z.enterorg and a.ownership = z.ownership and a.caseno = z.caseno and a.differencekind = z.differencekind ").append(
							" where 1=1 ").append(
		    			"");
			return stb;
		}
		
		private StringBuilder doGetSQLforUNTRF_ReduceDetail(StringBuilder stb){
			stb.append("select ").append(
							" 'RF' as type, ").append(
							" a.enterOrg, ").append(
							" a.caseNo, ").append(
							" a.verify, ").append(
		    				" a.sourceDate, ").append(
		    				" a.buyDate, ").append(
							" a.propertyNo, ").append(
							" a.serialNo, ").append(
							" a.propertyName1, ").append(
							" null as specification, ").append(
							" '1' as amount, ").append(
							" null as bookunit, ").append(
							" a.bookvalue, ").append(
							" a.netvalue, ").append(		
							" (select codename from SYSCA_CODE z where codekindid = 'CAA' and z.codeid = a.cause and codecon1 in (2,4)) as causeName, ").append(
							" cause2,").append(
							" a.otherLimitYear, ").append(
							" a.useYear, ").append(
							" a.usemonth, ").append(
							" a.oldaccumdepr as accumDepr ").append(
							" ,z.writedate ,z.writeunit ,z.proofyear ,z.proofdoc ,z.proofno ,z.differencekind ,z.notes ").append(
		    			" from UNTRF_REDUCEDETAIL a").append(
							" left join UNTRF_REDUCEPROOF z on a.enterorg = z.enterorg and a.ownership = z.ownership and a.caseno = z.caseno and a.differencekind = z.differencekind ").append(
							" where 1=1 ").append(
		    			"");
			return stb;
		}
		
		private StringBuilder doGetSQLforUNTMP_ReduceDetail(StringBuilder stb){
			stb.append("select ").append(
							" 'MP' as type, ").append(
							" a.enterOrg, ").append(
							" a.caseNo, ").append(
							" a.verify, ").append(
		    				" a.sourceDate, ").append(
		    				" a.buyDate, ").append(
							" a.propertyNo, ").append(
							" a.serialNo, ").append(
							" a.propertyName1, ").append(
							" a.specification, ").append(
							" a.adjustbookamount as amount, ").append(
							" a.adjustbookunit as bookunit, ").append(
							" a.adjustbookvalue as bookvalue, ").append(
							" a.adjustnetvalue as netvalue, ").append(		
							" (select codename from SYSCA_CODE z where codekindid = 'CAA' and z.codeid = a.cause and codecon1 in (2,4)) as causeName, ").append(
							" cause2,").append(
							" a.otherLimitYear, ").append(
							" a.useYear, ").append(
							" a.usemonth, ").append(
							" a.adjustaccumDepr as accumDepr ").append(
							" ,z.writedate ,z.writeunit ,z.proofyear ,z.proofdoc ,z.proofno ,z.differencekind ,z.notes ").append(
		    			" from UNTMP_REDUCEDETAIL a").append(
							" left join UNTMP_REDUCEPROOF z on a.enterorg = z.enterorg and a.ownership = z.ownership and a.caseno = z.caseno and a.differencekind = z.differencekind ").append(
							" where 1=1 ").append(
		    			"");
			return stb;
		}
		
		private StringBuilder doGetSQLforUNTVP_ReduceProof(StringBuilder stb){
			stb.append("select ").append(
							" 'VP' as type, ").append(
							" enterOrg, ").append(
							" caseNo, ").append(
							" verify, ").append(
		    				" sourceDate, ").append(
		    				" buyDate, ").append(
							" propertyNo, ").append(
							" serialNo, ").append(
							" propertyName1, ").append(
							" null as specification, ").append(
							" bookamount as amount, ").append(
							" null as bookunit, ").append(
							" bookvalue, ").append(
							" null as netvalue, ").append(		
							" (select codename from SYSCA_CODE z where codekindid = 'CAA' and z.codeid = a.cause and codecon1 in (2,4)) as causeName, ").append(	
							" a.cause1 as cause2,").append(									
							" null as otherLimitYear, ").append(
							" null as useYear, ").append(
							" null as usemonth, ").append(
							" null as accumDepr ").append(
							" ,a.writedate ,a.writeunit ,a.proofyear ,a.proofdoc ,a.proofno ,a.differencekind ,a.notes ").append(
		    			" from UNTVP_REDUCEPROOF a where 1=1 ").append(
		    			"");
			return stb;
		}
		
		private StringBuilder doGetSQLforUNTRT_ReduceProof(StringBuilder stb){
			stb.append("select ").append(
							" 'RT' as type, ").append(
							" enterOrg, ").append(
							" caseNo, ").append(
							" verify, ").append(
		    				" sourceDate, ").append(
		    				" buyDate, ").append(
							" propertyNo, ").append(
							" serialNo, ").append(
							" propertyName1, ").append(
							" null as specification, ").append(
							" 1 as amount, ").append(
							" null as bookunit, ").append(
							" bookvalue, ").append(
							" netvalue, ").append(		
							" (select codename from SYSCA_CODE z where codekindid = 'CAA' and z.codeid = a.cause and codecon1 in (2,4)) as causeName, ").append(
							" cause1 as cause2,").append(
							" otherLimitYear, ").append(
							" useYear, ").append(
							" usemonth, ").append(
							" oldaccumdepr as accumDepr ").append(
							" ,a.writedate ,a.writeunit ,a.proofyear ,a.proofdoc ,a.proofno ,a.differencekind ,a.notes ").append(
		    			" from UNTRT_REDUCEPROOF a where 1=1 ").append(
		    			"");
			return stb;
		}
		
	//=================================================================================
	
	

}