/*
*<br>程式目的：財產毀損報廢單
*<br>程式代號：
*<br>撰寫日期：
*<br>程式作者：
*<br>--------------------------------------------------------
*<br>修改作者　　修改日期　　　修改目的
*<br>--------------------------------------------------------
*/
package unt.rp;

import java.sql.ResultSet;
import java.util.Collections;
import java.util.Comparator;
import java.util.Vector;

import javax.swing.table.DefaultTableModel;

import unt.ch.UNTCH004F01;
import unt.ch.UNTCH_Tools;
import util.Common;
import util.Database;
import util.Datetime;
import util.SuperBean;
import util.report.ReportUtil;
import TDlib_Simple.tools.src.LogTools;
import TDlib_Simple.tools.src.MathTools;
import TDlib_Simple.tools.src.StringTools;

public class UNTRP005R extends SuperBean {
	private LogTools log = new LogTools();
	
	public DefaultTableModel getResultModel() throws Exception{
	    DefaultTableModel model = null;
	    Database db = null;
	    StringBuilder stb = new StringBuilder();
	    try{
	    	String[] columns = new String[] {
						    			"head01","head02","head03","head04","head05",
								    	"head06","head07",
								    	"detail01","detail02","detail03","detail04","detail05",
								    	"detail06","detail07","detail08","detail09","detail10",
								    	"detail11",
								    	"total01","total02","total03","total04",
								    	"tail01",
								    	"q_kind","q_notes"
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
			
			stb.append(" order by propertyNo");
			
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
					//原值單價  總計
					String sum_bookUnit = "0";
					//原值總價  總計
					String sum_bookValue = "0";
					//帳面價值  總計
					String sum_netValue = "0";
					//已提折舊數額  總計
					String sum_accumdepr = "0";	
					//殘餘價值  總計
					String sum_scrapvalue = "0";
					
					
					rs = db.querySQL(stb.toString());
				    while(rs.next()){
				    	
				    	Object[] data = new Object[columns.length];
				    	
				    	//===========================================================
				    	//						Head
				    	//===========================================================
				    	//印表日期
				    	String datetime = Datetime.getYYYMMDD();
				    	data[0] = datetime.substring(0,3) + "年" + datetime.substring(3,5) + "月" + datetime.substring(5) + "日";
				    	//財產區分別
				    	String differenceKind = ut._queryData("differenceKind")._from("UNTLA_REDUCEPROOF a ")._with(queryCondition+" and a.caseno='"+checkGet(rs.getString("caseNo"))+"' ")._toString();
				    	data[1] = differenceKind + "　" + ut._getDifferenceKindName(differenceKind);
				    	//編號
						data[2] = Common.get(rs.getString("proofyear")) + "年" +
			    				  Common.get(rs.getString("proofdoc")) + "字第" +
			    				  Common.get(rs.getString("proofno")) + "號";
				    	//印表人
				    	data[3] = ut._queryData("empname")._from("SYSAP_EMP a ")._with("and organid = '" + getOrganID() + "' and empid = '" + getEditID() + "'")._toString();
				    	//全銜
				    	data[4] = ReportUtil.getTitleByEnterOrgCodeNew(this.getQ_enterOrg(), this.getQ_differenceKind()); 
				    	
				    	//第　　聯
				    	data[5] = kindName[i];
				    	//填單日期
				    	String writedate = ut._transToROC_Year(ut._queryData("writeDate")._from("UNTLA_REDUCEPROOF a ")._with(queryCondition+" and a.caseno='"+checkGet(rs.getString("caseNo"))+"' ")._toString());
				    	data[6] = writedate.substring(0,3) + "年" + writedate.substring(3,5) + "月" + writedate.substring(5) + "日";
				    	
				    	
				    	//===========================================================
				    	//						Detail
				    	//===========================================================
				    	//財產編號 (含分號) 財產名稱
				    	data[7] = checkGet(rs.getString("propertyNo")) + "－" + checkGet(rs.getString("serialNo")) + "\n" + ut._getPropertyNoName(rs.getString("propertyNo"));
		//		    	data[7] = ReportUtil.getStringWithOldSerialNo(this.getQ_enterOrg(), Common.get(data[7]), "原財產分號", Common.get(rs.getString("oldserialno")));
				    	//別名/型式/廠牌 (或土地建物標示）
						    	String signNoStr = rs.getString("signNo");
						    	String typeStr;
						    	String signNoName = "";
						    	if(!"".equals(checkGet(signNoStr))){
						    		signNoName = ut._getSignNoName(signNoStr.substring(0,7));
				
									typeStr = rs.getString("type");
						    		if("LA".equals(typeStr) && signNoStr.length() >= 15){
						    			signNoName += signNoStr.substring(7,11) + "-" + signNoStr.substring(11) + "地號";
						    			
						    		}else if("BU".equals(typeStr) && signNoStr.length() >= 15){		
						    			signNoName += signNoStr.substring(7,12) + "-" + signNoStr.substring(12) + "建號";
						    		}
						    	}		    	
				    	
						String check_propertyName1 = checkGet(rs.getString("propertyName1"));
						String check_specification = checkGet(rs.getString("specification"));
						String check_nameplate = checkGet(rs.getString("nameplate"));
						    	
				    	data[8] =  check_propertyName1 + 
				    				(("".equals(check_specification))?"":"/" + check_specification) +
				    				(("".equals(check_nameplate))?"":"/" + check_nameplate) +
					    			 "\n" +
					    			signNoName;
				    	//單位
				    	String unitName = "";
				    	if("2".equals(Common.checkGet(rs.getString("propertyNo")).substring(0,1))){
				    		unitName = "平方公尺";
				    	}else{
				    		unitName = ut._getPropertyUnit(rs.getString("propertyNo"));
				    	}
				    	data[9] = unitName;
				    	//數量  計量數
				    	data[10] = checkGet(rs.getString("amount")) + "\n" +
			    					st._getMoneyFormat(checkGet(rs.getString("measure")));
				    	//原值單價  原值總價  帳面價值
				    	data[11] = st._getMoneyFormat(checkGet(rs.getString("bookUnit"))) + "\n" +
			    					st._getMoneyFormat(checkGet(rs.getString("bookValue"))) + "\n" +
			    					st._getMoneyFormat(checkGet(rs.getString("netValue")));
				    	//取得日期 購置日期
				    	data[12] = Common.MaskDate(ut._transToROC_Year(checkGet(rs.getString("sourceDate")))) + "\n" + 
				    			   Common.MaskDate(ut._transToROC_Year(checkGet(rs.getString("buyDate"))));
				    	//使用年限  已使用年數
	//			    	if("".equals(checkGet(ut._getLimitYear(rs.getString("propertyNo"))))){
	//			    		data[13] = checkGet(rs.getString("otherLimitYear")) + "\n" + 
	//			    					checkGet(rs.getString("useYear"));	
	//			    	}else{
	//			    		data[13] = ut._getLimitYear(rs.getString("propertyNo")) + "\n" + 
	//			    					checkGet(rs.getString("useYear"));	
	//			    	}
				    	int otherLimitMonth = "".equals(Common.get(rs.getString("otherLimitYear")))? 0 : Integer.parseInt(rs.getString("otherLimitYear")); // otherLimitYear以單位月分存取需轉成年
						int year = otherLimitMonth /12;
						int month = otherLimitMonth  % 12;
						data[13] = year +"年"+month+"個月" + "\n" + checkGet(rs.getString("useYear"))+"年"+checkGet(rs.getString("useMonth"))+"個月";
				    	
				    	//已提折舊數額
				    	data[14] = st._getMoneyFormat(checkGet(rs.getString("accumDepr")));
				    	//報損報廢原因/報損報廢後處理情形
				    	data[15] = checkGet(rs.getString("causeName"));
				    	if(!"".equals(checkGet(rs.getString("cause2"))))
				    			data[15]=data[15]+ "/" + checkGet(rs.getString("cause2")) ;
				    	if(!"".equals(checkGet(rs.getString("reduceDeal2"))))
				    			data[15]=data[15]+ "/" + checkGet(rs.getString("reduceDeal2"));
				    	
				    	//殘餘價值
				    	data[16] = st._getMoneyFormat(checkGet(rs.getString("scrapValue")));
				    	//備註
				    	data[17] = checkGet(rs.getString("notes"));
				    	
				    	
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
	
			    		//帳面價值  總計
				    		sum_netValue = mt._addition_withBigDecimal(sum_netValue, ("".equals(checkGet(rs.getString("netvalue")))?"0":rs.getString("netvalue")));
				    		
						//已提折舊數額  總計
				    		sum_accumdepr = mt._addition_withBigDecimal(sum_accumdepr, ("".equals(checkGet(rs.getString("accumdepr")))?"0":rs.getString("accumdepr")));
				    		
						//殘餘價值  總計
				    		sum_scrapvalue = mt._addition_withBigDecimal(sum_scrapvalue, ("".equals(checkGet(rs.getString("scrapvalue")))?"0":rs.getString("scrapvalue")));
						
				    	
				    		
					    	
				    	data[18] = "";
				    	
//				    	data[19] = st._getMoneyFormat(sum_bookUnit) + "\n" +
//					    			st._getMoneyFormat(sum_bookValue) + "\n" +
//					    			st._getMoneyFormat(sum_netValue);
				    	
				    	data[19] = "\n" +
					    			st._getMoneyFormat(sum_bookValue) + "\n" +
					    			st._getMoneyFormat(sum_netValue);
				    	
				    	data[20] = st._getMoneyFormat(sum_accumdepr);
				    	
				    	data[21] = st._getMoneyFormat(sum_scrapvalue);
				    	
				    	//===========================================================
				    	//						Tail
				    	//===========================================================
				    	//單據備註
				    	data[22] = ut._queryData("notes")._from("UNTLA_REDUCEPROOF a ")._with(queryCondition+" and a.caseno='"+checkGet(rs.getString("caseNo"))+"' ")._toString();
				    	
				    	//聯數群組判斷用
				    	data[23] = checkGet(rs.getString("caseNo"))+"_"+String.valueOf(i);
				    	//判斷是否列印備註用
				    	data[24] = q_note;
				    	
				    	rowData.addElement(data);
				    	
				    }
				}
		    }
			
		    
		    //第一聯、第二聯、第三聯 排序用
		    Collections.sort(rowData,
		            new Comparator<Object[]>() {
		                public int compare(Object[] o1, Object[] o2) {
		                    return o1[23].toString().compareTo(o2[23].toString());
		                }
		            });
		    
		  //=================================================================
		    Object[][] data = new Object[0][0];
	        data = (Object[][])rowData.toArray(data);
	        model = new javax.swing.table.DefaultTableModel();
	        model.setDataVector(data, columns);
	    }catch(Exception x){
	    	log._execLogError(x.getMessage());
	    	x.printStackTrace();
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
							" a.propertyNo, ").append(
							" a.serialNo, ").append(
							" a.propertyName1, ").append(
							" null as specification, ").append(
							" null as nameplate, ").append(
							" a.signno, ").append(
							" a.holdarea as amount, ").append(
							" null as measure, ").append(
							" a.bookunit, ").append(
							" a.bookvalue, ").append(
							" a.netvalue, ").append(		
							" null as AccumDepr, ").append(		
							" case when isnull(a.cause,'499') = '499' then a.cause1 else (select codename from SYSCA_CODE z where codekindid = 'CAA' and z.codeid = a.cause and codecon1 in (2,4)) end as causeName, ").append(
							" null as cause2, ").append(					
							" null as reduceDeal2, ").append(					
							" null as scrapvalue, ").append(					
							" a.notes, ").append(			
							" null as otherLimitYear, ").append(
							" null as useYear, ").append(
							" null as useMonth, ").append(
							" a.oldserialno, ").append(		
							" z.proofyear, ").append(
							" z.proofdoc, ").append(
							" z.proofno ").append(		
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
							" null as nameplate, ").append(
							" a.signno, ").append(
							" a.holdarea as amount, ").append(
							" null as measure, ").append(							
							" null as bookunit, ").append(
							" a.bookvalue, ").append(
							" a.netvalue, ").append(		
							" isnull(a.oldAccumDepr,0) as AccumDepr, ").append(					
							" case when isnull(a.cause,'499') = '499' then a.cause1 else (select codename from SYSCA_CODE z where codekindid = 'CAA' and z.codeid = a.cause and codecon1 in (2,4)) end as causeName, ").append(		
							" a.cause2, ").append(					
							" a.reduceDeal2, ").append(					
							" isnull(a.newscrapvalue,0) as scrapvalue, ").append(					
							" a.notes, ").append(			
							" a.otherLimitYear, ").append(
							" a.useYear, ").append(
							" a.useMonth, ").append(
							" a.oldserialno, ").append(
							" z.proofyear, ").append(
							" z.proofdoc, ").append(
							" z.proofno ").append(				
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
							" null as nameplate, ").append(
							" null as signno, ").append(
							" '1' as amount, ").append(
							" a.measure, ").append(									
							" null as bookunit, ").append(
							" a.bookvalue, ").append(
							" a.netvalue, ").append(		
							" isnull(a.oldAccumDepr,0) as AccumDepr, ").append(	
							" case when isnull(a.cause,'499') = '499' then a.cause1 else (select codename from SYSCA_CODE z where codekindid = 'CAA' and z.codeid = a.cause and codecon1 in (2,4)) end as causeName, ").append(		
							" a.cause2, ").append(					
							" a.reduceDeal2, ").append(					
							" isnull(a.newscrapvalue,0) as scrapvalue, ").append(					
							" a.notes, ").append(			
							" a.otherLimitYear, ").append(
							" a.useYear, ").append(
							" a.useMonth, ").append(
							" a.oldserialno, ").append(	
							" z.proofyear, ").append(
							" z.proofdoc, ").append(
							" z.proofno ").append(				
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
							" a.nameplate, ").append(
							" null as signno, ").append(
							" a.adjustbookamount as amount, ").append(
							" null as measure, ").append(									
							" a.adjustbookunit as bookunit, ").append(
							" a.adjustbookvalue as bookvalue, ").append(
							" a.adjustnetvalue as netvalue, ").append(		
							" a.adjustAccumDepr as AccumDepr, ").append(	
							" case when isnull(a.cause,'499') = '499' then a.cause1 else (select codename from SYSCA_CODE z where codekindid = 'CAA' and z.codeid = a.cause and codecon1 in (2,4)) end as causeName, ").append(		
							" a.cause2, ").append(					
							" a.reduceDeal2, ").append(					
							" isnull(a.newscrapvalue,0) as scrapvalue, ").append(					
							" a.notes, ").append(			
							" a.otherLimitYear, ").append(
							" a.useYear, ").append(
							" a.useMonth, ").append(
							" a.oldserialno, ").append(
							" z.proofyear, ").append(
							" z.proofdoc, ").append(
							" z.proofno ").append(		
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
							" null as nameplate, ").append(
							" null as signno, ").append(
							" bookamount as amount, ").append(
							" null as measure, ").append(									
							" null as bookunit, ").append(
							" bookvalue, ").append(
							" null as netvalue, ").append(		
							" null as AccumDepr, ").append(		
							" case when isnull(a.cause,'499') = '499' then a.cause1 else (select codename from SYSCA_CODE z where codekindid = 'CAA' and z.codeid = a.cause and codecon1 in (2,4)) end as causeName, ").append(
							" null as cause2, ").append(					
							" null as reduceDeal2, ").append(					
							" null as scrapvalue, ").append(					
							" notes, ").append(			
							" null as otherLimitYear, ").append(
							" null as useYear, ").append(	
							" null as useMonth, ").append(
							" oldserialno, ").append(
							" proofyear, ").append(
							" proofdoc, ").append(
							" proofno ").append(				
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
							" null as nameplate, ").append(
							" null as signno, ").append(
							" 1 as amount, ").append(
							" null as measure, ").append(									
							" null as bookunit, ").append(
							" bookvalue, ").append(
							" netvalue, ").append(		
							" isnull(a.oldaccumdepr,0) as AccumDepr, ").append(	
							" case when isnull(a.cause,'499') = '499' then a.cause1 else (select codename from SYSCA_CODE z where codekindid = 'CAA' and z.codeid = a.cause and codecon1 in (2,4)) end as causeName, ").append(		
							" null as cause2, ").append(					
							" null as reduceDeal2, ").append(					
							" isnull(a.newscrapvalue, 0) as ScrapValue, ").append(					
							" notes, ").append(			
							" otherLimitYear, ").append(
							" useYear, ").append(
							" useMonth, ").append(
							" oldserialno, ").append(
							" proofyear, ").append(
							" proofdoc, ").append(
							" proofno ").append(		
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