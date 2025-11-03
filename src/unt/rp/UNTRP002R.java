/*
*<br>程式目的：移動單報表檔 
*<br>程式代號：
*<br>撰寫日期：
*<br>程式作者：
*<br>--------------------------------------------------------
*<br>修改作者　　修改日期　　　修改目的
*<br>--------------------------------------------------------
*/
package unt.rp;

import java.sql.ResultSet;
import java.util.Vector;

import javax.swing.table.DefaultTableModel;

import unt.ch.UNTCH_Tools;
import util.Common;
import util.Database;
import util.Datetime;
import util.SuperBean;
import util.report.ReportUtil;
import TDlib_Simple.tools.src.LogTools;
import TDlib_Simple.tools.src.StringTools;

public class UNTRP002R extends SuperBean {
	private LogTools log = new LogTools();
	
	public DefaultTableModel getResultModel() throws Exception{
	    DefaultTableModel model = null;
	    Database db = null;
	    StringBuilder stb = new StringBuilder();
	    try{
	    	String[] columns = new String[] {
						    			"head01","head02","head03","head04","head05",
								    	"head06","head07","head08",
								    	"detail01","detail02","detail03","detail04","detail05",
								    	"detail06","detail07","detail08","detail09","detail10",
								    	"detail11","detail12","detail13","detail14",
								    	"q_kind","q_notes",
								    	"total01","total02","detail15","total03","detail16"
								    	};
		
			//=================================================================
			
			stb = doGetSQLforUNTMP_MOVEDETAIL(stb);
			stb.append(doGetIfCondition("MP"));
			stb.append(" order by enterorg, caseno, differencekind , propertyno, serialno ");
			
			//=================================================================			
			log._execLogDebug(stb.toString());
			//=================================================================
			
			UNTCH_Tools ut = new UNTCH_Tools();
			StringTools st = new StringTools();
			
			String[] kindName = null;
			if("1".equals(q_kind)){			kindName = new String[]{"第一聯"};
			}else if("2".equals(q_kind)){	kindName = new String[]{"第二聯"};
			}else if("3".equals(q_kind)){	kindName = new String[]{"第三聯"};
			}else if("4".equals(q_kind)){	kindName = new String[]{"第一聯","第二聯","第三聯"};
			}else if("5".equals(q_kind)){	kindName = new String[]{"第一聯","第二聯","第二聯","第三聯"};
    		}
			
			String oldCaseNo = "";
			String newCaseNo = "";
			db = new Database();
			ResultSet rs = null;
		    Vector rowData = new Vector();	
		    if(kindName != null){
		    	
		    	//使用者操作記錄
				Common.insertRecordSql(stb.toString(), this.getOrganID(), this.getUserID(), "UNTRP002R", this.getObjPath().replaceAll("&gt;", ">"));
				
				for(int i = 0 ; i < kindName.length; i++){
					rs = db.querySQL(stb.toString());
					long sumNetValue = 0;
					long sumBookValue = 0;
					int count = 0;
				    while(rs.next()){
				    	Object[] data = new Object[columns.length];
				    	
				    	//===========================================================
				    	//						Head
				    	//===========================================================
				    	//印表日期
				    	String datetime = Datetime.getYYYMMDD();
				    	
				    	String queryCondition = doGetIfCondition("");
				    	if(!"".equals(Common.get(rs.getString("caseno")))){
				    		queryCondition += " and a.caseno=" + Common.sqlChar(rs.getString("caseno"))+" ";
				    	}
				    	
				    	data[0] = datetime.substring(0,3) + "年" + datetime.substring(3,5) + "月" + datetime.substring(5) + "日";
				    	//填單日期
				    	String writedate = ut._transToROC_Year(ut._queryData("writeDate")._from("UNTMP_MOVEPROOF a ")._with(queryCondition)._toString());
				    	if (writedate.length() >= 7) {
				    		data[1] = writedate.substring(0,3) + "年" + writedate.substring(3,5) + "月" + writedate.substring(5) + "日";
				    	} else {
				    		data[1] = "";
				    	}
				    	//填造單位
				    	String writeunit = ut._queryData("writeunit")._from("UNTMP_MOVEPROOF a ")._with(queryCondition)._toString();
				    	if(!"".equals(checkGet(writeunit))){
				    		data[2] = ut._queryData("unitname")._from("UNTMP_KEEPUNIT")._with(" and enterorg = '" + getQ_enterOrg() + "' and unitno = '" + writeunit + "'")._toString();
				    	}
				    	//編號
				    	data[3] = ut._queryData("proofYear")._from("UNTMP_MOVEPROOF a ")._with(queryCondition)._toString() + 
				    				"年" +
				    				ut._queryData("proofDoc")._from("UNTMP_MOVEPROOF a ")._with(queryCondition)._toString() +
				    				"字第" +
				    				ut._queryData("proofNo")._from("UNTMP_MOVEPROOF a ")._with(queryCondition)._toString() +
				    				"號";
				    	
				    	//印表人
				    	data[4] = ut._queryData("empname")._from("SYSAP_EMP a ")._with("and organid = '" + getOrganID() + "' and empid = '" + getEditID() + "'")._toString();
				    	//全銜
				    	data[5] = ut._queryData("organaname")._from("SYSCA_ORGAN a ")._with(" and organid = '" + this.getQ_enterOrg() + "'")._toString();
				    	//第　　聯
				    	data[6] = kindName[i];
				    	
				    	newCaseNo = checkGet(rs.getString("caseNo"));
				    	if(!newCaseNo.equals(oldCaseNo)){
				    		count = 0;
				    		sumBookValue = 0;
				    		sumNetValue = 0;
				    		oldCaseNo = newCaseNo ;
				    	}
				    	count++;
				    	//電腦單號
				    	data[7] = checkGet(rs.getString("caseNo"));
				    	
				    	//===========================================================
				    	//						Detail
				    	//===========================================================
				    	//取得日期+購置日期
				    	data[8] = Common.MaskDate(ut._transToROC_Year(checkGet(rs.getString("sourceDate")))) + "\n" + Common.MaskDate(ut._transToROC_Year(checkGet(rs.getString("buyDate"))));
				    	//移出日期
				    	data[9] = Common.MaskDate(ut._transToROC_Year(checkGet(rs.getString("movedate"))));
				    	//財產編號（含分號）
				    	data[10] = checkGet(rs.getString("differencekind")) + "－" + checkGet(rs.getString("propertyNo")) + "－" + checkGet(rs.getString("serialNo"));
				    	//data[10] = ReportUtil.getStringWithOldSerialNo(this.getQ_enterOrg(), Common.get(data[10]), "原財產分號", Common.get(rs.getString("oldserialno")));
				    	
				    	//財產名稱+財產別名
				    	data[11] = ut._getPropertyNoName(rs.getString("enterorg"), rs.getString("propertyNo")) + "\n" + checkGet(rs.getString("propertyName1"));
				    	//廠牌/型式
				    	data[12] =  checkGet(rs.getString("nameplate")) + "\n" + checkGet(rs.getString("specification"));	
				    	//單位
				    	String otherpopertyunit = Common.get(rs.getString("otherpropertyunit"));
				    	if ("".equals(otherpopertyunit)) {
				    		otherpopertyunit = ut._getPropertyUnit(rs.getString("propertyNo"));
				    	}
				    	data[13] = otherpopertyunit;
				    	
				    	//移出  單位
				    	data[14] = checkGet(rs.getString("oldkeepunit"));
				    	//移出  保管人
				    	data[15] = checkGet(rs.getString("oldkeeper"));
				    	//移入  單位
				    	data[16] = checkGet(rs.getString("newKeepUnit"));
				    	//移入  保管人
				    	data[17] = checkGet(rs.getString("newKeeper"));
				    	//移入 存置地點
				    	data[18] = ut._queryData("placename")._from("SYSCA_PLACE a ")._with(" and enterorg = '" + rs.getString("enterorg") + "' and placeno = '" + rs.getString("newplace1") + "'")._toString();
				    	if("".equals(data[18]) || data[18] == null){
				    		data[18] = checkGet(rs.getString("newplace"));
				    	}
				    	//價值
				    	data[19] = st._getMoneyFormat(checkGet(rs.getString("netvalue")));
						//使用年限
//						if("".equals(checkGet(rs.getString("otherLimitYear")))){
//							data[20] = ut._getLimitYear(rs.getString("propertyNo"));	
//						}else{
//							data[20] = checkGet(rs.getString("otherLimitYear"));
//						}
				    	int otherLimitMonth = "".equals(Common.get(rs.getString("otherLimitYear")))? 0 : Integer.parseInt(rs.getString("otherLimitYear")); // otherLimitYear以單位月分存取需轉成年
						int year = otherLimitMonth /12;
						int month = otherLimitMonth % 12;
						data[20] = year +"年"+month+"個月";
						
				    	//已使用年數
				    	if(rs.getString("useYear") != null && rs.getString("usemonth") != null){
				    		data[21] = checkGet(rs.getString("useYear"))+"年"+checkGet(rs.getString("usemonth"))+"個月";
				    	}else{
				    		data[21] ="";
				    	}
	
				    	
				    	
				    	//===========================================================
				    	
				    	//聯數群組判斷用
				    	data[22] = String.valueOf(i);
				    	//判斷是否列印備註用
				    	data[23] = q_note;
				    	
				    	data[24] = String.valueOf(count);
				    	
				    	sumNetValue += rs.getLong("netvalue");
				    	data[25] = st._getMoneyFormat(checkGet(String.valueOf(sumNetValue)));
				    	
				    	long bookvalue = rs.getLong("bookvalue");
				    	sumBookValue += bookvalue;
				    	data[26] = st._getMoneyFormat(Common.get(bookvalue));
				    	data[27] = st._getMoneyFormat(Common.get(sumBookValue));;
				    	data[28] = ut._queryData("notes")._from("UNTMP_MOVEPROOF a ")._with(queryCondition)._toString();
				    	rowData.addElement(data);
				    	
				    }
				    rs.close();
				}
		    }
			
		  //=================================================================
		    Object[][] data = new Object[0][0];
	        data = (Object[][])rowData.toArray(data);
	        model = new javax.swing.table.DefaultTableModel();
	        model.setDataVector(data, columns);
	    }catch(Exception x){
	    	x.printStackTrace();
	    	log._execLogError(x.getMessage());
	    }finally{
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
			if("".equals(table)){
				alias = "a.";
			}
			
			if(!st._isEmpty(getQ_writeDateS())){
				stb.append(" and " + alias + "writeDate >= ").append(Common.sqlChar(ut._transToCE_Year(getQ_writeDateS())));
			}
			if(!st._isEmpty(getQ_writeDateE())){
				stb.append(" and " + alias + "writeDate <= ").append(Common.sqlChar(ut._transToCE_Year(getQ_writeDateE())));
			}
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
		
		private StringBuilder doGetSQLforUNTMP_MOVEDETAIL(StringBuilder stb){
			stb.append("select ").append(
							" a.buydate, ").append(
							" a.enterorg, ").append(
							" a.caseno, ").append(
							" a.sourcedate, ").append(
							" z.movedate, ").append(
							" a.propertyno, ").append(
							" a.propertyname1, ").append(
							" a.serialno, ").append(
							" a.nameplate, ").append(
							" a.specification,").append(
							" (select unitname from UNTMP_KEEPUNIT z where z.enterorg = a.enterorg and z.unitno = a.oldkeepunit) as oldKeepUnit,").append(
							" (select keepername from UNTMP_KEEPER z where z.enterorg = a.enterorg and z.keeperno = a.oldkeeper) as oldKeeper,").append(
							" (select unitname from UNTMP_KEEPUNIT z where z.enterorg = a.enterorg and z.unitno = a.newkeepunit) as newKeepUnit,").append(
							" (select keepername from UNTMP_KEEPER z where z.enterorg = a.enterorg and z.keeperno = a.newkeeper) as newKeeper,").append(
							" a.newplace1, ").append(
							" a.newplace, ").append(
							" a.bookvalue, ").append(
							" a.netvalue, ").append(
							" a.otherlimityear, ").append(
							" a.otherpropertyunit, ").append(
							" a.useyear, ").append(
							" a.usemonth, ").append(
							" a.differencekind, ").append(
							" a.oldserialno ").append(
						"from UNTMP_MOVEDETAIL a").append(
							" left join UNTMP_MOVEPROOF z on a.enterorg = z.enterorg and a.ownership = z.ownership and a.caseno = z.caseno ").append(
							" where 1=1 ").append(
						""); 
			return stb;
		}
		
		
	//=================================================================================
	
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
	String q_differenceKind;
	String q_caseNoE;
	String q_proofYear;
	String q_writeDateS;
	String q_writeDateE;
	String q_proofDoc;
	String q_proofNoS;
	String q_proofNoE;
	String q_kind;
	String q_note;
	private String q_reportType;						// 報表類型
	
	public String getQ_reportType() { return checkGet(q_reportType); }
	public void setQ_reportType(String q_reportType) { this.q_reportType = checkGet(q_reportType); }
	public String getQ_note() {return checkGet(q_note);}
	public void setQ_note(String q_note) {this.q_note = checkSet(q_note);}
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
	public String getQ_differenceKind() {return checkGet(q_differenceKind);}
	public void setQ_differenceKind(String q_differenceKind) {this.q_differenceKind = checkSet(q_differenceKind);}	String q_caseNoS;
	public String getQ_caseNoS(){ return checkGet(q_caseNoS); }
	public void setQ_caseNoS(String s){ q_caseNoS=checkSet(s); }
	public String getQ_caseNoE(){ return checkGet(q_caseNoE); }
	public void setQ_caseNoE(String s){ q_caseNoE=checkSet(s); }
	public String getQ_proofYear() {return checkGet(q_proofYear);}
	public void setQ_proofYear(String q_proofYear) {this.q_proofYear = checkSet(q_proofYear);}
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
	private String isOrganManager;
	private String isAdminManager;
	private String organID;
	private String editID;
    public String getOrganID() { return checkGet(organID); }
    public void setOrganID(String s) { organID = checkSet(s); }
    public String getIsOrganManager() { return checkGet(isOrganManager); }
    public void setIsOrganManager(String s) { isOrganManager = checkSet(s); }
    public String getIsAdminManager() { return checkGet(isAdminManager); }
    public void setIsAdminManager(String s) { isAdminManager = checkSet(s); }
	public String getEditID() {return checkGet(editID);}
	public void setEditID(String editID) {this.editID = checkSet(editID);}   

}