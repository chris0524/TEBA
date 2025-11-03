/*
*<br>程式目的：財產減損單
*<br>程式代號：
*<br>撰寫日期：
*<br>程式作者：
*<br>--------------------------------------------------------
*<br>修改作者　　修改日期　　　修改目的
*<br>--------------------------------------------------------
*/
package unt.rp;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Vector;

import javax.swing.table.DefaultTableModel;

import unt.ch.UNTCH004F01;
import unt.ch.UNTCH_Tools;
import util.Common;
import util.Database;
import util.Datetime;
import util.SuperBean;
import util.View;
import util.report.ReportUtil;
import TDlib_Simple.tools.src.LogTools;
import TDlib_Simple.tools.src.MathTools;
import TDlib_Simple.tools.src.StringTools;

public class UNTRP008R extends SuperBean {
	private LogTools log = new LogTools();
	
	public DefaultTableModel getResultModel() throws Exception{
	    DefaultTableModel model = null;
	    Database db = null;
	    StringBuilder stb = new StringBuilder();
	    try{
	    	String[] columns = new String[] {
										"head01","head02","head03","head04","head05",
										"head06","head07","head08","head09","head10",
										"head11",
										"detail01","detail02","detail03","detail04","detail05",
										"detail06","detail07","detail08","detail09","detail10",
										"detail11",
										"total01","total02","total03",
										"tail01",
										"q_kind","q_notes",
										"summonsDate","newDeprPark","newDeprUnit","newDeprAccounts",
										"type","bookvalue","accumdepr","netvalue"
										};
		
			//=================================================================
			
			stb.append(" select * from (");    	
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
			
			stb.append(" order by caseno, propertyNo, serialno");
			
			//=================================================================			
			log._execLogDebug(stb.toString());
			//=================================================================
			
			UNTCH_Tools ut = new UNTCH_Tools();
			MathTools mt = new MathTools();
			StringTools st = new StringTools();
			
			String queryCondition = doGetIfCondition("");
			
			String[] kindName = null;
			if("1".equals(q_kind)){			kindName = new String[]{"第一聯"};
			}else if("2".equals(q_kind)){	kindName = new String[]{"第二聯"};
			}else if("3".equals(q_kind)){	kindName = new String[]{"第三聯"};
			}else if("4".equals(q_kind)){	kindName = new String[]{"第一聯","第二聯","第三聯"};
			}else if("5".equals(q_kind)){	kindName = new String[]{"第一聯","第二聯","第二聯","第三聯"};
    		}
			
			db = new Database();
			ResultSet rs = null;
		    Vector rowData = new Vector();	
		    
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
		    
		    if(kindName != null){
				for(int i = 0 ; i < kindName.length; i++){
					//數量 總計
					String sum_amount = "0";	
					//計量數 總計
					String sum_measure = "0";
					//總價  總計
					String sum_bookValue = "0";	
					//帳面單價  總計
					String sum_bookUnit = "0";	
					//累計折舊  總計
					String sum_accumdepr = "0";
					//帳面總價  總計
					String sum_netValue = "0";
					
					String preCaseno = "";
					rs = db.querySQL(stb.toString());
					while(rs.next()){
						
						Object[] data = new Object[columns.length];
						
						String caseNo = Common.get(rs.getString("caseNo"));
						if (!preCaseno.equals(caseNo)) {
							// 換單號 總計清空
							//數量 總計
							sum_amount = "0";	
							//計量數 總計
							sum_measure = "0";
							//總價  總計
							sum_bookValue = "0";	
							//帳面單價  總計
							sum_bookUnit = "0";	
							//累計折舊  總計
							sum_accumdepr = "0";
							//帳面總價  總計
							sum_netValue = "0";
							preCaseno = caseNo;
						}
						
						String keeper = Common.get(rs.getString("keeper"));
						if ("Y".equals(this.getQ_isPrintKeeper()) && "".equals(this.getKeeper()) && !"".equals(keeper)) {
							String keepername = View.getLookupField("select keepername from UNTMP_KEEPER where keeperno = " + Common.sqlChar(keeper) + " and enterorg = " + Common.sqlChar(this.getQ_enterOrg()));
				    		this.setKeeper(Common.get(keepername));
						}
						
						
						//===========================================================
						//						Head
						//===========================================================
						//印表日期
						String datetime = Datetime.getYYYMMDD();
						data[0] = datetime.substring(0,3) + "年" + datetime.substring(3,5) + "月" + datetime.substring(5) + "日";
						//填單日期
						String writedate = ut._transToROC_Year(ut._queryData("writeDate")._from("UNTLA_REDUCEPROOF a ")._with(queryCondition)._toString());
						if (writedate.length() >= 7) {
							data[1] = writedate.substring(0,3) + "年" + writedate.substring(3,5) + "月" + writedate.substring(5) + "日";
						} else {
							data[1] = "";
						}
						
						//填造單位
						String writeunit = Common.get(rs.getString("writeunit"));
						if(!"".equals(checkGet(writeunit))){
							data[2] = ut._queryData("unitname")._from("UNTMP_KEEPUNIT")._with(" and enterorg = '" + getQ_enterOrg() + "' and unitno = '" + writeunit + "'")._toString();
						}
						//編號
						data[3] = Common.get(rs.getString("proofyear")) + "年" +
			    				  Common.get(rs.getString("proofdoc")) + "字第" +
			    				  Common.get(rs.getString("proofno")) + "號";
						//印表人
						data[4] = ut._queryData("empname")._from("SYSAP_EMP a ")._with("and organid = '" + getOrganID() + "' and empid = '" + getEditID() + "'")._toString();
						//財產區分別
						String differenceKind = Common.get(rs.getString("differencekind"));
						data[5] = differenceKind + "　" + ut._getDifferenceKindName(differenceKind);
						//合併分割案號		    	
						data[6] = Common.get(rs.getString("mergedivision"));
						//全銜
						data[7] = ReportUtil.getTitleByEnterOrgCodeNew(this.getQ_enterOrg(), this.getQ_differenceKind());
						
						//第　　聯
						data[8] = kindName[i];
						//電腦單號
						data[9] = caseNo;
						//傳票號數
						data[10] = Common.get(rs.getString("summonsno"));
								
						
						//===========================================================
						//						Detail
						//===========================================================
						
						String typeStr = rs.getString("type");
						//取得日期 購置日期
						data[11] = Common.MaskDate(ut._transToROC_Year(checkGet(rs.getString("sourceDate")))) + "\n" + 
								   Common.MaskDate(ut._transToROC_Year(checkGet(rs.getString("buyDate"))));
						//財產編號 (含分號) 財產名稱 財產大類
						data[12] = checkGet(rs.getString("propertyNo")) + "－" + checkGet(rs.getString("serialNo"));
					//	data[12] = ReportUtil.getStringWithOldSerialNo(this.getQ_enterOrg(), Common.get(data[12]), "原財產分號", Common.get(rs.getString("oldserialno")));
						data[12] = data[12] + "\n" + ut._getPropertyNoName(rs.getString("propertyNo")) + "\n" + 
								   ut._getPropertyNoGroupName(rs.getString("propertyNo"));
						
						//別名/型式/廠牌
								String signNoStr = rs.getString("signNo");
								String signNoName = "";
								if(!"".equals(checkGet(signNoStr))){
									signNoName = ut._getSignNoName(signNoStr.substring(0,7));
				
									if("LA".equals(typeStr) && signNoStr.length() >= 15){
										signNoName += signNoStr.substring(7,11) + "-" + signNoStr.substring(11);
										
									}else if("BU".equals(typeStr) && signNoStr.length() >= 15){		
										signNoName += signNoStr.substring(7,12) + "-" + signNoStr.substring(12);
									}
								}		    	
						
						String check_propertyName1 = checkGet(rs.getString("propertyName1"));
						String check_specification = checkGet(rs.getString("specification"));
						String check_nameplate = checkGet(rs.getString("nameplate"));
								
						data[13] =  check_propertyName1 + 
									(("".equals(check_specification))?"":"/" + check_specification) +
									(("".equals(check_nameplate))?"":"/" + check_nameplate) +
									 "\n" +
									signNoName;
						
						//單位
						String unitName = "";
						if("2".equals(Common.checkGet(rs.getString("propertyNo")).substring(0,1))){
							unitName = "平方公尺";
						}else{
							unitName = Common.checkGet(rs.getString("otherpropertyunit"));
				    		if ("".equals(unitName)) {
					    		unitName = ut._getPropertyUnit(rs.getString("propertyNo"));	    			
				    		}
						}
						
						data[14] = unitName;
						//數量  計量數
						if("BU".equals(typeStr)){
							data[15] = "0".equals(checkGet(rs.getString("amount")))? "1":checkGet(rs.getString("amount"))  + "\n" +
							st._getMoneyFormat(checkGet(rs.getString("measure")));
						}else{
							data[15] = checkGet(rs.getString("amount")) + "\n" +
										st._getMoneyFormat(checkGet(rs.getString("measure")));
						}
						//總價  帳面單價
						data[16] = st._getMoneyFormat("".equals(checkGet(rs.getString("bookvalue")))?"0":rs.getString("bookvalue")) + "\n" +
									st._getMoneyFormat("".equals(checkGet(rs.getString("bookunit")))?"0":rs.getString("bookunit"));
						//累計折舊  帳面總價
						data[17] = st._getMoneyFormat("".equals(checkGet(rs.getString("accumdepr")))?"0":rs.getString("accumdepr")) + "\n" +
									st._getMoneyFormat("".equals(checkGet(rs.getString("netvalue")))?"0":rs.getString("netvalue"));
						
						//減損原因
						data[18] = checkGet(rs.getString("causeName"));
						String cause2 = Common.get(rs.getString("cause2")); 
						if (!"".equals(cause2)) {
							data[18] = data[18] + "\r\n" + cause2;
						}
						//繳存地點
						data[19] = checkGet(rs.getString("returnPlace"));	
						
						//使用年限
						int otherLimitMonth = "".equals(Common.get(rs.getString("otherLimitYear")))? 0 : Integer.parseInt(rs.getString("otherLimitYear")); // otherLimitYear以單位月分存取需轉成年
						int year = otherLimitMonth /12;
						int month = otherLimitMonth % 12;
						data[20] = year +"年"+month+"個月";
						
						//已使用年數
						String useYear = checkGet(rs.getString("useYear"));
						String useMonth = checkGet(rs.getString("useMonth"));
						if ("".equals(useYear) && "".equals(useMonth)) {
							int diffDays = Datetime.BetweenTwoMonthCE(checkGet(rs.getString("buydate")), checkGet(rs.getString("writedate")));
							if (diffDays > 0) { 
								useYear = String.valueOf(diffDays/12);
								useMonth = String.valueOf(diffDays % 12);
							}
						} else if ("".equals(useYear)) {
							useYear = "0";
						} else if ("".equals(useMonth)) {
							useMonth = "0";
						}
						data[21] = useYear + "年" + useMonth + "個月";
						
						
						//===========================================================
						//						Total
						//===========================================================
						//數量 總計
							sum_amount = mt._addition_withBigDecimal(sum_amount, ("".equals(checkGet(rs.getString("amount")))?"0":rs.getString("amount")));	
							
						//計量數  總計
							sum_measure = mt._addition_withBigDecimal(sum_measure, ("".equals(checkGet(rs.getString("measure")))?"0":rs.getString("measure")));
							
						//總價  總計
							sum_bookValue = mt._addition_withBigDecimal(sum_bookValue, ("".equals(checkGet(rs.getString("bookValue")))?"0":rs.getString("bookValue")));
							
						//帳面單價  總計
							sum_bookUnit = mt._addition_withBigDecimal(sum_bookUnit, ("".equals(checkGet(rs.getString("bookunit")))?"0":rs.getString("bookunit")));
	
						//累計折舊  總計
							sum_accumdepr = mt._addition_withBigDecimal(sum_accumdepr, ("".equals(checkGet(rs.getString("accumdepr")))?"0":rs.getString("accumdepr")));
							
						//帳面總價  總計
							sum_netValue = mt._addition_withBigDecimal(sum_netValue, ("".equals(checkGet(rs.getString("netValue")))?"0":rs.getString("netValue")));
							
							
							
						data[22] = "";
						
//						data[23] = st._getMoneyFormat(sum_bookValue) + "\n" +
//									st._getMoneyFormat(sum_bookUnit);
						data[23] = st._getMoneyFormat(sum_bookValue);
						
						data[24] = st._getMoneyFormat(sum_accumdepr) + "\n" +
									 st._getMoneyFormat(sum_netValue);
						
						//===========================================================
						//						Tail
						//===========================================================
						//單據備註
						data[25] = ut._queryData("notes")._from("UNTLA_REDUCEPROOF a ")._with(queryCondition)._toString();
						
						//聯數群組判斷用
						data[26] = String.valueOf(i);
						//判斷是否列印備註用
						data[27] = q_note;
						//傳票日期
						String summonsDate = Common.get(rs.getString("summonsdate"));
						if(!summonsDate.equals("")){
						data[28] = Datetime.changeTaiwan(summonsDate.substring(0,4), "1") + "年" + summonsDate.substring(4,6) + "月" + summonsDate.substring(6) + "日";}
						else{
						data[28] = "";
						}
						data[29] = Common.get(rs.getString("newDeprPark"));
						data[30] = Common.get(rs.getString("newDeprUnit"));
						data[31] = Common.get(rs.getString("newDeprAccounts"));
						
						if("MP".equals(Common.get(rs.getString("type")))) {
							data[32] = Common.get(rs.getString("type")) + rs.getString("propertyno").substring(0,1);
						}else {
							data[32] = Common.get(rs.getString("type"));
						}
						
						data[33] = BigDecimal.valueOf(rs.getDouble("bookvalue"));
						
						data[34] = BigDecimal.valueOf(rs.getDouble("accumdepr"));
						
						data[35] = BigDecimal.valueOf(rs.getDouble("netvalue"));
						
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
			if("VP".equals(table) || "RT".equals(table) || "".equals(table)){
				alias = "a.";
			}
			if(!st._isEmpty(getQ_adjustDateS())){
				if("VP".equals(table)){
					stb.append(" and " + alias + "adjustDate >= ").append(Common.sqlChar(ut._transToCE_Year(getQ_adjustDateS())));
				}else{
					stb.append(" and " + alias + "reduceDate >= ").append(Common.sqlChar(ut._transToCE_Year(getQ_adjustDateS())));
				}
			}
			if(!st._isEmpty(getQ_adjustDateE())){
				if("VP".equals(table)){
					stb.append(" and " + alias + "adjustDate <= ").append(Common.sqlChar(ut._transToCE_Year(getQ_adjustDateE())));
				}else{
					stb.append(" and " + alias + "reduceDate <= ").append(Common.sqlChar(ut._transToCE_Year(getQ_adjustDateE())));
				}
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
		
		private StringBuilder doGetSQLforUNTLA_ReduceDetail(StringBuilder stb){
			stb.append("select ").append(
							" 'LA' as type, ").append(
							" a.enterOrg, ").append(
							" a.caseNo, ").append(
							" a.verify, ").append(
		    				" a.sourceDate, ").append(
		    				" a.buyDate, ").append(
		    				" z.writedate, ").append(
							" a.propertyNo, ").append(
							" a.serialNo, ").append(
							" a.propertyName1, ").append(
							" null as specification, ").append(
							" null as nameplate, ").append(
							" a.signno, ").append(
							" a.holdarea as amount, ").append(
							" null as measure, ").append(
							" a.bookvalue, ").append(
							" a.netUnit, ").append(		
							" null as accumDepr, ").append(
							" a.bookunit, ").append(
							" a.netvalue, ").append(										
							" case when isnull(a.cause,'499') = '499' then a.cause1 else (select codename from SYSCA_CODE z where codekindid = 'CAA' and z.codeid = a.cause and codecon1 in (2,4)) end as causeName, ").append(
							" null as returnPlace, ").append(
							" null as otherLimitYear, ").append(
							" '平方公尺' as otherpropertyunit, ").append(
							" null as useYear, ").append(	
							" null as useMonth, ").append(
							" null as cause2, ").append(
							" a.keeper, ").append(
							" a.oldserialno as oldserialno, ").append(
							" z.proofyear, ").append(
							" z.proofdoc, ").append(
							" z.proofno, ").append(
							" z.differencekind, ").append(
							" z.writeunit, ").append(
							" z.mergedivision, ").append(
							" z.summonsno, ").append(
							" z.summonsdate, ").append(
							" null as newDeprPark, ").append(
							" null as newDeprUnit, ").append(
							" null as newDeprAccounts ").append(				
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
		    				" z.writedate, ").append(
							" a.propertyNo, ").append(
							" a.serialNo, ").append(
							" a.propertyName1, ").append(
							" null as specification, ").append(
							" null as nameplate, ").append(
							" a.signno, ").append(
							" a.holdarea as amount, ").append(
							" null as measure, ").append(
							" a.bookvalue, ").append(
							" null as netUnit, ").append(		
							" isnull(a.oldaccumdepr ,'0') as accumdepr, ").append(
							" null as bookunit, ").append(
							" a.netvalue, ").append(
							" case when isnull(a.cause,'499') = '499' then a.cause1 else (select codename from SYSCA_CODE z where codekindid = 'CAA' and z.codeid = a.cause and codecon1 in (2,4)) end as causeName, ").append(
							" null as returnPlace, ").append(
							" a.otherLimitYear, ").append(
							" a.otherpropertyunit, ").append(
							" a.useYear, ").append(	
							" a.useMonth, ").append(
							" a.cause2, ").append(
							" a.keeper, ").append(
							" a.oldserialno as oldserialno, ").append(
							" z.proofyear, ").append(
							" z.proofdoc, ").append(
							" z.proofno, ").append(
							" z.differencekind, ").append(
							" z.writeunit, ").append(
							" null as mergedivision, ").append(
							" z.summonsno, ").append(
							" z.summonsdate, ").append(
							" (select deprparkname from SYSCA_DEPRPARK where a.enterorg = enterorg and a.newDeprPark = deprparkno ) as newDeprPark, ").append(
							" (select deprunitname from SYSCA_DEPRUNIT where a.enterorg = enterorg and a.newdeprunit = deprunitno ) as newDeprUnit, ").append(
							" (select depraccountsname from SYSCA_DEPRACCOUNTS where a.enterorg = enterorg and a.newdepraccounts = depraccountsno ) as newDeprAccounts ").append(		
									
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
		    				" z.writedate, ").append(
							" a.propertyNo, ").append(
							" a.serialNo, ").append(
							" a.propertyName1, ").append(
							" null as specification, ").append(
							" null as nameplate, ").append(
							" null as signno, ").append(
							" '1' as amount, ").append(
							" a.measure, ").append(
							" a.bookvalue, ").append(
							" null as netUnit, ").append(		
							" a.oldaccumdepr as accumDepr, ").append(
							" null as bookunit, ").append(
							" a.netvalue, ").append(										
							" case when isnull(a.cause,'499') = '499' then a.cause1 else (select codename from SYSCA_CODE z where codekindid = 'CAA' and z.codeid = a.cause and codecon1 in (2,4)) end as causeName, ").append(
							" null as returnPlace, ").append(
							" a.otherLimitYear, ").append(
							" a.otherpropertyunit, ").append(
							" a.useYear, ").append(	
							" a.useMonth, ").append(
							" a.cause2, ").append(
							" a.keeper, ").append(
							" a.oldserialno as oldserialno, ").append(
							" z.proofyear, ").append(
							" z.proofdoc, ").append(
							" z.proofno, ").append(
							" z.differencekind, ").append(
							" z.writeunit, ").append(
							" null as mergedivision, ").append(
							" z.summonsno, ").append(
							" z.summonsdate, ").append(
							" (select deprparkname from SYSCA_DEPRPARK where a.enterorg = enterorg and a.newDeprPark = deprparkno ) as newDeprPark, ").append(
							" (select deprunitname from SYSCA_DEPRUNIT where a.enterorg = enterorg and a.newdeprunit = deprunitno ) as newDeprUnit, ").append(
							" (select depraccountsname from SYSCA_DEPRACCOUNTS where a.enterorg = enterorg and a.newdepraccounts = depraccountsno ) as newDeprAccounts ").append(
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
		    				" z.writedate, ").append(
							" a.propertyNo, ").append(
							" a.serialNo, ").append(
							" a.propertyName1, ").append(
							" a.specification, ").append(
							" a.nameplate, ").append(
							" null as signno, ").append(
							" a.adjustbookamount as amount, ").append(
							" null as measure, ").append(
							" a.adjustbookvalue as bookvalue, ").append(
							" a.adjustnetUnit as netunit, ").append(		
							" a.adjustaccumDepr as accumDepr, ").append(
							" a.adjustbookunit as bookunit, ").append(
							" a.adjustnetvalue as netvalue, ").append(										
							" case when isnull(a.cause,'499') = '499' then a.cause1 else (select codename from SYSCA_CODE z where codekindid = 'CAA' and z.codeid = a.cause and codecon1 in (2,4)) end as causeName, ").append(
							" a.returnPlace, ").append(
							" a.otherLimitYear, ").append(
							" a.otherpropertyunit, ").append(
							" a.useYear, ").append(	
							" a.useMonth, ").append(
							" a.cause2, ").append(
							" a.keeper, ").append(
							" a.oldserialno as oldserialno, ").append(
							" z.proofyear, ").append(
							" z.proofdoc, ").append(
							" z.proofno, ").append(
							" z.differencekind, ").append(
							" z.writeunit, ").append(
							" null as mergedivision, ").append(
							" z.summonsno, ").append(
							" z.summonsdate, ").append(
							" (select deprparkname from SYSCA_DEPRPARK where a.enterorg = enterorg and a.newDeprPark = deprparkno ) as newDeprPark, ").append(	
							" (select deprunitname from SYSCA_DEPRUNIT where a.enterorg = enterorg and a.newdeprunit = deprunitno ) as newDeprUnit, ").append(
							" (select depraccountsname from SYSCA_DEPRACCOUNTS where a.enterorg = enterorg and a.newdepraccounts = depraccountsno ) as newDeprAccounts ").append(
		    			" from UNTMP_REDUCEDETAIL a").append(
							" left join UNTMP_REDUCEPROOF z on a.enterorg = z.enterorg and a.ownership = z.ownership and a.caseno = z.caseno and a.differencekind = z.differencekind ").append(
							" where 1=1 ").append(
		    			"");
			return stb;
		}
		
		private StringBuilder doGetSQLforUNTVP_ReduceProof(StringBuilder stb){
			stb.append("select ").append(
							" 'VP' as type, ").append(
							" a.enterOrg, ").append(
							" a.caseNo, ").append(
							" a.verify, ").append(
		    				" a.sourceDate, ").append(
		    				" a.buyDate, ").append(
		    				" z.writedate, ").append(
							" a.propertyNo, ").append(
							" a.serialNo, ").append(
							" a.propertyName1, ").append(
							" a.notes as specification, ").append(
							" null as nameplate, ").append(
							" null as signno, ").append(
							" a.bookamount as amount, ").append(
							" null as measure, ").append(
							" a.bookvalue, ").append(
							" null as netUnit, ").append(		
							" null as accumDepr, ").append(
							" null as bookunit, ").append(
							" null as netvalue, ").append(										
							" case when isnull(a.cause,'499') = '499' then a.cause1 else (select codename from SYSCA_CODE z where codekindid = 'CAA' and z.codeid = a.cause and codecon1 in (2,4)) end as causeName, ").append(
							" a.returnPlace, ").append(
							" null as otherLimitYear, ").append(
							" a.otherpropertyunit, ").append(
							" null as useYear, ").append(	
							" null as useMonth, ").append(
							" null as cause2, ").append(
							" a.keeper, ").append(
							" a.oldserialno as oldserialno, ").append(
							" z.proofyear, ").append(
							" z.proofdoc, ").append(
							" z.proofno, ").append(
							" z.differencekind, ").append(
							" z.writeunit, ").append(
							" null as mergedivision, ").append(
							" z.summonsno, ").append(
							" z.summonsdate, ").append(
							" null as newDeprPark, ").append(
							" null as newDeprUnit, ").append(
							" null as newDeprAccounts ").append(
							" from UNTVP_REDUCEPROOF a ").append(
		    				" left join UNTLA_REDUCEPROOF z on a.enterorg = z.enterorg and a.ownership = z.ownership and a.caseno = z.caseno and a.differencekind = z.differencekind ")
		    				.append("where 1=1 ").append(
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
		    				" writedate, ").append(
							" propertyNo, ").append(
							" serialNo, ").append(
							" propertyName1, ").append(
							" null as specification, ").append(
							" null as nameplate, ").append(
							" null as signno, ").append(
							" '1' as amount, ").append(
							" null as measure, ").append(
							" bookvalue, ").append(
							" null as netUnit, ").append(		
							" oldaccumdepr as accumDepr, ").append(
							" null as bookunit, ").append(
							" netvalue, ").append(										
							" case when isnull(a.cause,'499') = '499' then a.cause1 else (select codename from SYSCA_CODE z where codekindid = 'CAA' and z.codeid = a.cause and codecon1 in (2,4)) end as causeName, ").append(
							" null as returnPlace, ").append(
							" otherLimitYear, ").append(
							" a.otherpropertyunit, ").append(
							" a.useYear, ").append(	
							" a.useMonth, ").append(
							" null as cause2, ").append(
							" a.keeper, ").append(									
							" a.oldserialno as oldserialno, ").append(
							" a.proofyear, ").append(
							" a.proofdoc, ").append(
							" a.proofno, ").append(
							" a.differencekind, ").append(
							" a.writeunit, ").append(
							" null as mergedivision, ").append(
							" a.summonsno, ").append(
							" a.summonsdate, ").append(
							" (select deprparkname from SYSCA_DEPRPARK where a.enterorg = enterorg and a.newDeprPark = deprparkno ) as newDeprPark, ").append(	
							" (select deprunitname from SYSCA_DEPRUNIT where a.enterorg = enterorg and a.newdeprunit = deprunitno ) as newDeprUnit, ").append(
							" (select depraccountsname from SYSCA_DEPRACCOUNTS where a.enterorg = enterorg and a.newdepraccounts = depraccountsno ) as newDeprAccounts ").append(
							" from UNTRT_REDUCEPROOF a where 1=1 ").append(
		    			"");
			return stb;
		}
		
	//=================================================================================
	
	String enterOrg;
	String ownership;
	String caseNoS;
	String caseNoE;
	String adjustDateS;
	String adjustDateE;
	String proofDoc;
	String proofNoS;
	String proofNoE;
	String q_enterOrg;
	String q_enterOrgName;
	String q_ownership;
	String q_differenceKind;
	String q_caseNoE;
	String q_proofYear;
	String q_adjustDateS;
	String q_adjustDateE;
	String q_proofDoc;
	String q_proofNoS;
	String q_proofNoE;
	String q_kind;
	String q_note;
	private String q_isPrintKeeper;
	private String q_reportType;						// 報表類型
	
	public String getQ_reportType() {	return checkGet(q_reportType);}
	public void setQ_reportType(String q_reportType) { this.q_reportType = checkSet(q_reportType); }	
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
	public String getadjustDateS(){ return checkGet(adjustDateS); }
	public void setadjustDateS(String s){ adjustDateS=checkSet(s); }
	public String getadjustDateE(){ return checkGet(adjustDateE); }
	public void setadjustDateE(String s){ adjustDateE=checkSet(s); }
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
	public String getQ_isPrintKeeper() { return checkGet(q_isPrintKeeper); }
	public void setQ_isPrintKeeper(String q_isPrintKeeper) { this.q_isPrintKeeper = checkSet(q_isPrintKeeper); }

	private String isOrganManager;
	private String isAdminManager;
	private String organID;
	private String editID;
	private String keeper;
    public String getOrganID() { return checkGet(organID); }
    public void setOrganID(String s) { organID = checkSet(s); }
    public String getIsOrganManager() { return checkGet(isOrganManager); }
    public void setIsOrganManager(String s) { isOrganManager = checkSet(s); }
    public String getIsAdminManager() { return checkGet(isAdminManager); }
    public void setIsAdminManager(String s) { isAdminManager = checkSet(s); }
	public String getEditID() {return checkGet(editID);}
	public void setEditID(String editID) {this.editID = checkSet(editID);}
	public String getKeeper() { return checkGet(keeper); }
	public void setKeeper(String keeper) { this.keeper = checkSet(keeper); }   
	
	

}