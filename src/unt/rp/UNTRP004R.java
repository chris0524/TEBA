/*
*<br>程式目的：增減值單報表檔 
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
import TDlib_Simple.tools.src.MathTools;
import TDlib_Simple.tools.src.StringTools;

public class UNTRP004R extends SuperBean {
	private LogTools log = new LogTools();
	
	public DefaultTableModel getResultModel() throws Exception{
	    DefaultTableModel model = null;
	    Database db = null;
	    StringBuilder stb = new StringBuilder();
	    try{
	    	String[] columns = new String[] {
						    			"head01","head02","head03","head04","head05",
								    	"head06","head07","head08","head09","head10",
								    	"head11","head12",
								    	"detail01","detail02","detail03","detail04","detail05",
								    	"detail06","detail07","detail08","detail09","detail10",
								    	"detail11","detail12", "detail13",
								    	"total01","total02","total03","total04","total05",
								    	"total06","total07","total08",
								    	"tail01",
								    	"q_kind","q_notes",
								    	"summonsDate"
								    	};
		
			//=================================================================
			
			stb.append(" select * from (");    	
			stb = doGetSQLforUNTLA_AdjustDetail(stb);    	
			stb.append(doGetIfCondition("LA"));
			stb.append(" union ");
			stb = doGetSQLforUNTBU_AdjustDetail(stb);
			stb.append(doGetIfCondition("BU"));
			stb.append(" union ");
			stb = doGetSQLforUNTRF_AdjustDetail(stb);
			stb.append(doGetIfCondition("RF"));
			stb.append(" union ");
			stb = doGetSQLforUNTMP_AdjustDetail(stb);
			stb.append(doGetIfCondition("MP"));
			stb.append(" union ");
			stb = doGetSQLforUNTVP_AdjustProof(stb);
			stb.append(doGetIfCondition("VP"));
			stb.append(" union ");
			stb = doGetSQLforUNTRT_AdjustProof(stb);
			stb.append(doGetIfCondition("RT"));
			stb.append(") a");
			
			stb.append(" order by enterorg, caseno, propertyno, serialno ");
			
			//=================================================================			
			log._execLogDebug(stb.toString());
			//=================================================================
			
			UNTCH_Tools ut = new UNTCH_Tools();
			MathTools mt = new MathTools();
			StringTools st = new StringTools();
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
		    if(kindName != null){
				for(int i = 0 ; i < kindName.length; i++){
					//數量 總計
					String sum_oldbookAmount = "0";	
					String sum_addbookAmount = "0";
					String sum_reducebookAmount = "0";
					String sum_newbookAmount = "0";
					//計量數 總計
					String sum_oldmeasure = "0";	
					String sum_addmeasure = "0";
					String sum_reducemeasure = "0";
					String sum_newmeasure = "0";
					//原值  總計
					String sum_oldbookValue = "0";	
					String sum_addbookValue = "0";
					String sum_reducebookValue = "0";
					String sum_newbookValue = "0";
					//累計折舊  總計
					String sum_oldAccumDepr = "0";
					String sum_addAccumDepr = "0";
					String sum_reduceAccumDepr = "0";
					String sum_newAccumDepr = "0";
					//帳面價值  總計
					String sum_oldnetValue = "0";	
					String sum_addnetValue = "0";
					String sum_reducenetValue = "0";
					String sum_newnetValue = "0";
					
					String oldCaseNo = "";
					String newCaseNo = "";
					
					rs = db.querySQL(stb.toString());
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
				    	String writedate = ut._transToROC_Year(ut._queryData("writeDate")._from("UNTLA_ADJUSTPROOF a ")._with(queryCondition)._toString());
				    	data[1] = writedate.substring(0,3) + "年" + writedate.substring(3,5) + "月" + writedate.substring(5) + "日";
				    	//填造單位
				    	String writeunit = ut._queryData("writeunit")._from("UNTLA_ADJUSTPROOF a ")._with(queryCondition)._toString();
				    	if(!"".equals(checkGet(writeunit))){
				    		data[2] = ut._queryData("unitname")._from("UNTMP_KEEPUNIT")._with(" and enterorg = '" + getQ_enterOrg() + "' and unitno = '" + writeunit + "'")._toString();
				    	}
				    	//編號
				    	data[3] = ut._queryData("proofYear")._from("UNTLA_ADJUSTPROOF a ")._with(queryCondition)._toString() + 
				    				"年" +
				    				ut._queryData("proofDoc")._from("UNTLA_ADJUSTPROOF a ")._with(queryCondition)._toString() +
				    				"字第" +
				    				ut._queryData("proofNo")._from("UNTLA_ADJUSTPROOF a ")._with(queryCondition)._toString() +
				    				"號";
				    	
				    	//工程名稱
				    	String cond = "";
				    	if(!st._isEmpty(getQ_enterOrg())){
				    		cond += " and a.enterOrg = " + Common.sqlChar(getQ_enterOrg());
						}
						if(!st._isEmpty(getQ_ownership())){
							cond += " and a.ownership = " + Common.sqlChar(getQ_ownership());
						}
						if(!st._isEmpty(getQ_caseNoS())){
							cond += " and a.caseno >= " + Common.sqlChar(getQ_caseNoS());
						}
						if(!st._isEmpty(getQ_caseNoE())){
							cond += " and a.caseno <= " + Common.sqlChar(getQ_caseNoE());
						}
				    	String engineeringNotemp = ut._queryData("engineeringNo")._from("UNTLA_ADJUSTPROOF a ")._with(queryCondition)._toString();
	
				    	cond = "";
				    	if(!st._isEmpty(getQ_enterOrg())){
				    		cond += " and a.enterOrg = " + Common.sqlChar(getQ_enterOrg());
						}
				    	
				    	data[4] = ut._queryData("engineeringName")._from("UNTEG_ENGINEERINGCASE a ")._with(cond + " and engineeringNo = '" + engineeringNotemp + "'")._toString();
				    	
				    	//印表人
				    	data[5] = ut._queryData("empname")._from("SYSAP_EMP a ")._with("and organid = '" + getOrganID() + "' and empid = '" + getEditID() + "'")._toString();
				    	//財產區分別
				    	String differenceKind = ut._queryData("differenceKind")._from("UNTLA_ADJUSTPROOF a ")._with(queryCondition)._toString();
				    	data[6] = differenceKind + "　" + ut._getDifferenceKindName(differenceKind);
				    	//全銜
				    	data[7] = ReportUtil.getTitleByEnterOrgCodeNew(this.getQ_enterOrg(), this.getQ_differenceKind());
				    	
				    	//第　　聯
				    	data[8] = kindName[i];
				    	
				    	newCaseNo = checkGet(rs.getString("caseNo"));
				    	if(!newCaseNo.equals(oldCaseNo)){
				    		sum_oldbookValue = "0";
				    		sum_addbookValue = "0";
				    		sum_reducebookValue = "0";
				    		sum_newbookValue = "0";
				    		sum_oldAccumDepr = "0";
				    		sum_reduceAccumDepr = "0";
				    		sum_addAccumDepr = "0";
				    		sum_newAccumDepr = "0";
				    		sum_oldnetValue = "0";
				    		sum_addnetValue = "0";
				    		sum_reducenetValue = "0";
				    		sum_newnetValue = "0";
				    		oldCaseNo = newCaseNo ;
				    	}
				    	
				    	//電腦單號
				    	data[9] = checkGet(rs.getString("caseNo"));
	
				    	//財產管理單位編號
				    	data[10] = checkGet(rs.getString("enterOrg"));
				    	//傳票號數
				    	data[11] = ut._queryData("summonsNo")._from("UNTLA_ADJUSTPROOF a ")._with(queryCondition)._toString();
				    	
				    	//===========================================================
				    	//						Detail
				    	//===========================================================
				    	//取得日期  購置日期 財產大類
				    	data[12] = Common.MaskDate(ut._transToROC_Year(checkGet(rs.getString("sourceDate")))) + "\n" + 
				    				Common.MaskDate(ut._transToROC_Year(checkGet(rs.getString("buyDate")))) + "\n" +
				    				ut._getPropertyNoGroupName(rs.getString("propertyNo"));
				    	//財產編號  財產名稱  財產別名
				    	data[13] = checkGet(rs.getString("propertyNo")) + "－" + checkGet(rs.getString("serialNo"));
//				    	data[13] = ReportUtil.getStringWithOldSerialNo(this.getQ_enterOrg(), Common.get(data[13]), "原財產分號", Common.get(rs.getString("oldserialno")));
				    	data[13] = data[13] + "\n" + ut._getPropertyNoName(rs.getString("propertyNo")) + "\n" + 
								   checkGet(rs.getString("propertyName1"));
			    	
				    	//增/減值原因					    	    	
				    	data[14] = checkGet(rs.getString("cause")) + "\\" +
				    				checkGet(rs.getString("cause1"));
		
				    	//單位 使用單位 保管人
				    	String unitName = "";
				    	if("2".equals(Common.checkGet(rs.getString("propertyNo")).substring(0,1))){
				    		unitName = "平方公尺";
				    	}else{
//				    		unitName = ut._getPropertyUnit(rs.getString("propertyNo"));
				    		unitName = rs.getString("otherpropertyunit");				    		
				    	}
				    	//單位為空或null時直接取財編檔的單位
				    	if( unitName==null || unitName.equals("")){
				    		unitName = ut._getPropertyUnit(rs.getString("propertyNo"));
				    	}
				    	
				    	data[15] = unitName + "\n" + 
				    				checkGet(ut._queryData("unitname")._from("UNTMP_KEEPUNIT")._with(" and enterorg = '" + getQ_enterOrg() + "' and unitno = '" + checkGet(rs.getString("keepunit")) + "'")._toString()) + "\n" +
				    				checkGet(ut._queryData("keepername")._from("UNTMP_KEEPER")._with(" and enterorg = '" + getQ_enterOrg() + "' and keeperno = '" + checkGet(rs.getString("keeper")) + "'")._toString());
				    	//原價  數量  計量數
				    	if(rs.getString("propertyNo").startsWith("1") || rs.getString("propertyNo").startsWith("2") || rs.getString("propertyNo").startsWith("111")){
				    		data[16] = st._getMoneyForma_withDot(checkGet(rs.getString("oldbookAmount")),2);	
				    	}else{
				    		data[16] = checkGet(rs.getString("oldbookAmount"));
				    	}
				    	
				    	//原價  原值 累計折舊 帳面價值 使用年限(月)
				    	data[17] = st._getMoneyFormat(String.valueOf(Common.getNumeric(rs.getString("oldbookvalue")))) + "\n" +
				    			   st._getMoneyFormat(String.valueOf(Common.getNumeric(rs.getString("oldaccumdepr")))) + "\n" +
				    			   st._getMoneyFormat(String.valueOf(Common.getNumeric(rs.getString("oldnetvalue")))) + "\n" +
				    			   st._getMoneyFormat(String.valueOf(Common.getNumeric(rs.getString("otherlimityear"))));
				    	
				    	//增加  數量  計量數
				    	data[18] = checkGet(rs.getString("addbookAmount"));
				    	//增加   原值 累計折舊 帳面價值 使用年限(月)
				    	data[19] = st._getMoneyFormat(String.valueOf(Common.getNumeric(rs.getString("addbookvalue")))) + "\n" + 
			    					"0\n" +
			    					st._getMoneyFormat(String.valueOf(Common.getNumeric(rs.getString("addnetvalue")))) + "\n" + 
			    					st._getMoneyFormat(String.valueOf(Common.getNumeric(rs.getString("addlimityear"))));
				    	
				    	//減少  數量  計量數
				    	data[20] = checkGet(rs.getString("reducebookAmount"));
				    	//減少  原值 累計折舊 帳面價值 使用年限(月)
				    	data[21] = st._getMoneyFormat(String.valueOf(Common.getNumeric(rs.getString("reducebookvalue")))) + "\n" +
				    				st._getMoneyFormat(String.valueOf(Common.getNumeric(rs.getString("reduceAccumDepr")))) + "\n" +
				    				st._getMoneyFormat(String.valueOf(Common.getNumeric(rs.getString("reducenetvalue")))) + "\n" +
				    				st._getMoneyFormat(String.valueOf(Common.getNumeric(rs.getString("reducelimityear"))));
				    	
				    	//餘額  數量  計量數
				    	if(rs.getString("propertyNo").startsWith("1") || rs.getString("propertyNo").startsWith("2") || rs.getString("propertyNo").startsWith("111")){
				    		data[22] = st._getMoneyForma_withDot(checkGet(rs.getString("newbookAmount")),2);
				    	}else{
				    		data[22] = checkGet(rs.getString("newbookAmount"));
				    	}
				    	
				    	//餘額  原值 累計折舊 帳面價值 使用年限(月)
				    	data[23] = st._getMoneyFormat(String.valueOf(Common.getNumeric(rs.getString("newbookvalue")))) + "\n" +
				    			   st._getMoneyFormat(String.valueOf(Common.getNumeric(rs.getString("newaccumdepr")))) + "\n" +
				    			   st._getMoneyFormat(String.valueOf(Common.getNumeric(rs.getString("newnetvalue")))) + "\n" +
				    			   st._getMoneyFormat(String.valueOf(Common.getNumeric(rs.getString("otherlimityear"))+Common.getNumeric(rs.getString("addlimityear"))-Common.getNumeric(rs.getString("reducelimityear"))));
				    	
				    	//園區別 部門別 部門別單位 會計科目
				    	data[24] = checkGet(rs.getString("deprparkname")) +"\n" + checkGet(rs.getString("deprunitname")) +"\n" + 
				    			   checkGet(rs.getString("deprunit1name")) +"\n" + checkGet(rs.getString("depraccountsname")); 
				    	
				    	//===========================================================
				    	//						Total
				    	//===========================================================
				    	//數量 總計
				    		sum_oldbookAmount = mt._addition_withBigDecimal(sum_oldbookAmount, ("".equals(checkGet(rs.getString("oldbookAmount")))?"0":rs.getString("oldbookAmount")));	
				    		sum_addbookAmount = mt._addition_withBigDecimal(sum_addbookAmount, ("".equals(checkGet(rs.getString("addbookAmount")))?"0":rs.getString("addbookAmount")));
				    		sum_reducebookAmount = mt._addition_withBigDecimal(sum_reducebookAmount, ("".equals(checkGet(rs.getString("reducebookAmount")))?"0":rs.getString("reducebookAmount")));
				    		sum_newbookAmount = mt._addition_withBigDecimal(sum_newbookAmount, ("".equals(checkGet(rs.getString("newbookAmount")))?"0":rs.getString("newbookAmount")));
				    	//計量數 總計
				    		sum_oldmeasure = mt._addition_withBigDecimal(sum_oldmeasure, ("".equals(checkGet(rs.getString("oldmeasure")))?"0":rs.getString("oldmeasure")));	
				    		sum_addmeasure = mt._addition_withBigDecimal(sum_addmeasure, ("".equals(checkGet(rs.getString("addmeasure")))?"0":rs.getString("addmeasure")));
				    		sum_reducemeasure = mt._addition_withBigDecimal(sum_reducemeasure, ("".equals(checkGet(rs.getString("reducemeasure")))?"0":rs.getString("reducemeasure")));
				    		sum_newmeasure = mt._addition_withBigDecimal(sum_newmeasure, ("".equals(checkGet(rs.getString("newmeasure")))?"0":rs.getString("newmeasure")));			    		
				    	//原值  總計
				    		sum_oldbookValue = mt._addition_withBigDecimal(sum_oldbookValue, ("".equals(checkGet(rs.getString("oldbookValue")))?"0":rs.getString("oldbookValue")));
				    		sum_addbookValue = mt._addition_withBigDecimal(sum_addbookValue, ("".equals(checkGet(rs.getString("addbookValue")))?"0":rs.getString("addbookValue")));
				    		sum_reducebookValue = mt._addition_withBigDecimal(sum_reducebookValue, ("".equals(checkGet(rs.getString("reducebookValue")))?"0":rs.getString("reducebookValue")));
				    		sum_newbookValue = mt._addition_withBigDecimal(sum_newbookValue, ("".equals(checkGet(rs.getString("newbookValue")))?"0":rs.getString("newbookValue")));
				    	//累計折舊  總計
				    		sum_oldAccumDepr = mt._addition_withBigDecimal(sum_oldAccumDepr, ("".equals(checkGet(rs.getString("oldaccumdepr")))?"0":rs.getString("oldaccumdepr")));
				    		sum_reduceAccumDepr = mt._addition_withBigDecimal(sum_reduceAccumDepr, ("".equals(checkGet(rs.getString("reduceAccumDepr")))?"0":rs.getString("reduceAccumDepr")));
				    		sum_newAccumDepr = mt._addition_withBigDecimal(sum_newAccumDepr, ("".equals(checkGet(rs.getString("newaccumdepr")))?"0":rs.getString("newaccumdepr")));
				    	//帳面價值  總計
				    		sum_oldnetValue = mt._addition_withBigDecimal(sum_oldnetValue, ("".equals(checkGet(rs.getString("oldnetValue")))?"0":rs.getString("oldnetValue")));	
				    		sum_addnetValue = mt._addition_withBigDecimal(sum_addnetValue, ("".equals(checkGet(rs.getString("addnetValue")))?"0":rs.getString("addnetValue")));
				    		sum_reducenetValue = mt._addition_withBigDecimal(sum_reducenetValue, ("".equals(checkGet(rs.getString("reducenetValue")))?"0":rs.getString("reducenetValue")));
				    		sum_newnetValue = mt._addition_withBigDecimal(sum_newnetValue, ("".equals(checkGet(rs.getString("newnetValue")))?"0":rs.getString("newnetValue")));
					    	
				    	data[25] = "";
				    	
				    	// 原價 總計
				    	data[26] = st._getMoneyFormat(sum_oldbookValue) + "\n" +
				    			   st._getMoneyFormat(sum_oldAccumDepr) + "\n" +
				    			   st._getMoneyFormat(sum_oldnetValue);
				    	
				    	data[27] = "";
				    	
				    	// 增加 總計
				    	data[28] = st._getMoneyFormat(sum_addbookValue) + "\n" +
				    			   st._getMoneyFormat(sum_addAccumDepr) + "\n" +
				    			   st._getMoneyFormat(sum_addnetValue);
	
				    	data[29] = "";
				    	
				    	// 減少 總計
				    	data[30] = st._getMoneyFormat(sum_reducebookValue) + "\n" +
				    			   st._getMoneyFormat(sum_reduceAccumDepr) + "\n" +
				    			   st._getMoneyFormat(sum_reducenetValue);			    	
				    	
				    	data[31] = "";
				    	
				    	// 餘額 總計
				    	data[32] = st._getMoneyFormat(sum_newbookValue) + "\n" +
				    			   st._getMoneyFormat(sum_newAccumDepr)  + "\n" +
				    			   st._getMoneyFormat(sum_newnetValue);
				    	
				    	//===========================================================
				    	//						Tail
				    	//===========================================================
				    	//單據備註
				    	data[33] = ut._queryData("notes")._from("UNTLA_ADJUSTPROOF a ")._with(queryCondition)._toString();
				    	
				    	//聯數群組判斷用
				    	data[34] = String.valueOf(i);
				    	//判斷是否列印備註用
				    	data[35] = q_note;
				    	//傳票日期
				    	String summonsDate = ut._transToROC_Year(ut._queryData("summonsDate")._from("UNTLA_ADJUSTPROOF a ")._with(queryCondition)._toString());
				    	if(!summonsDate.equals("")){
				    	data[36] = summonsDate.substring(0,3) + "年" + summonsDate.substring(3,5) + "月" + summonsDate.substring(5) + "日";}
				    	else{
				    		data[36]= "";
				    	}
				    	rowData.addElement(data);
				    	
				    }
				}
		    }
			
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
				stb.append(" and " + alias + "adjustDate >= ").append(Common.sqlChar(ut._transToCE_Year(getQ_adjustDateS())));
			}
			if(!st._isEmpty(getQ_adjustDateE())){
				stb.append(" and " + alias + "adjustDate <= ").append(Common.sqlChar(ut._transToCE_Year(getQ_adjustDateE())));
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
		
		private StringBuilder doGetSQLforUNTLA_AdjustDetail(StringBuilder stb){
			stb.append("select ").append(
							" 'LA' as type, ").append(
							" a.enterOrg, ").append(
							" a.caseNo, ").append(
		    				" a.sourceDate, ").append(
		    				" a.buyDate, ").append(
							" a.propertyNo, ").append(
							" a.serialNo, ").append(
							" a.propertyName1, ").append(
							" case when a.cause = '99' then '其他：' + a.cause1 else (select codename from SYSCA_CODE z where z.codekindid = 'AJC' and z.codeid = a.cause) end as cause, ").append(
							" a.cause1, ").append(
							" a.keepunit, ").append(
							" a.keeper, ").append(
							" a.oldholdarea as oldBookamount, ").append(
							" case when a.adjustholdarea >= 0 then a.adjustholdarea else 0 end as addBookamount, ").append(
							" case when a.adjustholdarea < 0 then ABS(a.adjustholdarea) else 0 end as reduceBookamount, ").append(
							" a.newholdarea as newBookamount, ").append(
							" null as oldMeasure, ").append(
							" null as addMeasure, ").append(
							" null as reduceMeasure, ").append(
							" null as newMeasure, ").append(
							" null as otherpropertyunit, ").append(
							" a.oldbookValue, ").append(
							" a.addbookValue, ").append(
							" a.reducebookValue, ").append(
							" a.newbookValue, ").append(
							" a.oldnetValue, ").append(
							" a.addnetValue, ").append(
							" a.reducenetValue, ").append(
							" null as reduceAccumDepr, ").append(
							" newnetValue, ").append(
							" null as otherlimityear, ").append(
							" null as addlimityear, ").append(
							" null as reducelimityear, ").append(
							" a.oldserialno as oldserialno, ").append(
							" null as oldaccumdepr, null as newaccumdepr, ").append(
							" null as deprparkname, ").append(
							" null as deprunitname, ").append(
							" null as deprunit1name, ").append(
							" null as depraccountsname ").append(
						"from UNTLA_ADJUSTDETAIL a ").append(
							" left join UNTLA_ADJUSTPROOF z on a.enterorg = z.enterorg and a.ownership = z.ownership and a.caseno = z.caseno and a.differencekind = z.differencekind ").append(
							" where 1=1 ").append(
						""); 
			return stb;
		}
		
		private StringBuilder doGetSQLforUNTBU_AdjustDetail(StringBuilder stb){
			stb.append("select ").append(
							" 'BU' as type, ").append(
							" a.enterOrg, ").append(
							" a.caseNo, ").append(
			    			" a.sourceDate, ").append(
			    			" a.buyDate, ").append(
			    			" a.propertyNo, ").append(
							" a.serialNo, ").append(
			    			" a.propertyName1, ").append(
	    					" case when a.cause = '99' then '其他：' + a.cause1 else (select codename from SYSCA_CODE z where z.codekindid = 'AJC' and z.codeid = a.cause) end as cause, ").append(
	    					" a.cause1, ").append(
							" a.keepunit, ").append(
							" a.keeper, ").append(
							" a.oldholdarea as oldBookamount, ").append(
							" case when a.adjustholdarea >= 0 then a.adjustholdarea else 0 end as addBookamount, ").append(
							" case when a.adjustholdarea < 0 then ABS(a.adjustholdarea) else 0 end as reduceBookamount, ").append(
							" a.newholdarea as newBookamount, ").append(
							" null as oldMeasure, ").append(
							" null as addMeasure, ").append(
							" null as reduceMeasure, ").append(
							" null as newMeasure, ").append(
							" a.otherpropertyunit, ").append(
							" a.oldbookValue, ").append(
							" a.addbookValue, ").append(
							" a.reducebookValue, ").append(
							" a.newbookValue, ").append(
							" a.oldnetValue, ").append(
							" a.addnetValue, ").append(
							" a.reducenetValue, ").append(
							" a.reduceaccumdepr, ").append(
							" a.newnetValue, ").append(
							" a.otherlimityear, ").append(
							" a.addlimityear, ").append(
							" a.reducelimityear, ").append(
							" a.oldserialno as oldserialno, ").append(
							" a.oldaccumdepr, a.newaccumdepr, ").append(
							" (select deprparkname from SYSCA_DEPRPARK x where a.enterorg = x.enterorg and a.newdeprpark = x.deprparkno) as deprparkname, ").append(
							" (select deprunitname from SYSCA_DEPRUNIT x where a.enterorg = x.enterorg and a.newdeprunit = x.deprunitno) as deprunitname, ").append(
							" (select deprunit1name from SYSCA_DEPRUNIT1 x where a.enterorg = x.enterorg and a.newdeprunit1 = x.deprunit1no) as deprunit1name,").append(
							" (select depraccountsname from SYSCA_DEPRACCOUNTS x where a.enterorg = x.enterorg and a.newdepraccounts = x.depraccountsno) as depraccountsname").append(
		    			" from UNTBU_ADJUSTDETAIL a").append(
							" left join UNTBU_ADJUSTPROOF z on a.enterorg = z.enterorg and a.ownership = z.ownership and a.caseno = z.caseno and a.differencekind = z.differencekind ").append(
							" where 1=1 ").append(
		    			"");
			return stb;
		}
		
		private StringBuilder doGetSQLforUNTRF_AdjustDetail(StringBuilder stb){
			stb.append("select ").append(
							" 'RF' as type, ").append(
							" a.enterOrg, ").append(
							" a.caseNo, ").append(
			    			" a.sourceDate, ").append(
			    			" a.buyDate, ").append(
			    			" a.propertyNo, ").append(
							" a.serialNo, ").append(
			    			" a.propertyName1, ").append(
	    					" case when a.cause = '99' then '其他：' + a.cause1 else (select codename from SYSCA_CODE z where z.codekindid = 'AJC' and z.codeid = a.cause) end as cause, ").append(
							" a.cause1, ").append(
							" a.keepunit, ").append(
							" a.keeper, ").append(
							" a.oldMeasure as oldBookamount, ").append(
							" a.addMeasure as addBookamount, ").append(
							" a.reduceMeasure as reduceBookamount, ").append(
							" a.newMeasure as newBookamount, ").append(
							" a.oldMeasure, ").append(
							" a.addMeasure, ").append(
							" a.reduceMeasure, ").append(
							" a.newMeasure, ").append(
							" a.otherpropertyunit, ").append(
							" a.oldbookValue, ").append(
							" a.addbookValue, ").append(
							" a.reducebookValue, ").append(
							" a.newbookValue, ").append(
							" a.oldnetValue, ").append(
							" a.addnetValue, ").append(
							" a.reducenetValue, ").append(
							" a.reduceAccumDepr, ").append(
							" a.newnetValue, ").append(
							" a.otherlimityear, ").append(
							" a.addlimityear, ").append(
							" a.reducelimityear, ").append(
							" a.oldserialno as oldserialno,  ").append(	
							" a.oldaccumdepr, a.newaccumdepr, ").append(
							" (select deprparkname from SYSCA_DEPRPARK x where a.enterorg = x.enterorg and a.newdeprpark = x.deprparkno) as deprparkname, ").append(
							" (select deprunitname from SYSCA_DEPRUNIT x where a.enterorg = x.enterorg and a.newdeprunit = x.deprunitno) as deprunitname, ").append(
							" (select deprunit1name from SYSCA_DEPRUNIT1 x where a.enterorg = x.enterorg and a.newdeprunit1 = x.deprunit1no) as deprunit1name,").append(
							" (select depraccountsname from SYSCA_DEPRACCOUNTS x where a.enterorg = x.enterorg and a.newdepraccounts = x.depraccountsno) as depraccountsname").append(
		    			" from UNTRF_ADJUSTDETAIL a").append(
							" left join UNTRF_ADJUSTPROOF z on a.enterorg = z.enterorg and a.ownership = z.ownership and a.caseno = z.caseno and a.differencekind = z.differencekind ").append(
							" where 1=1 ").append(
		    			"");
			return stb;
		}
		
		private StringBuilder doGetSQLforUNTMP_AdjustDetail(StringBuilder stb){
			stb.append("select ").append(
							" 'MP' as type, ").append(
							" a.enterOrg, ").append(
							" a.caseNo, ").append(
			    			" a.sourceDate, ").append(
			    			" a.buyDate, ").append(
			    			" a.propertyNo, ").append(
							" a.serialNo, ").append(		
			    			" a.propertyName1, ").append(
	    					" case when a.cause = '99' then '其他：' + a.cause1 else (select codename from SYSCA_CODE z where z.codekindid = 'AJC' and z.codeid = a.cause) end as cause, ").append(
							" a.cause1, ").append(
							" a.keepunit, ").append(
							" a.keeper, ").append(
							" (case when a.oldbookvalue >0 then a.bookamount else 0 end) as oldBookamount, ").append(
							" '0' as addBookamount, ").append(
							" '0' as reduceBookamount, ").append(
							" (case when a.newbookvalue >0 then a.bookamount else 0 end) as newBookamount, ").append(
							" null as oldMeasure, ").append(
							" null as addMeasure, ").append(
							" null as reduceMeasure, ").append(
							" null as newMeasure, ").append(
							" a.otherpropertyunit, ").append(
							" a.oldbookValue, ").append(
							" a.addbookValue, ").append(
							" a.reducebookValue, ").append(
							" a.newbookValue, ").append(
							" a.oldnetValue, ").append(
							" a.addnetValue, ").append(
							" a.reducenetValue, ").append(
							" a.reduceAccumDepr, ").append(
							" a.newnetValue, ").append(
							" a.otherlimityear, ").append(
							" a.addlimityear, ").append(
							" a.reducelimityear, ").append(
							" a.oldserialno as oldserialno, ").append(	
							" a.oldaccumdepr, a.newaccumdepr, ").append(
							" (select deprparkname from SYSCA_DEPRPARK x where a.enterorg = x.enterorg and a.newdeprpark = x.deprparkno) as deprparkname, ").append(
							" (select deprunitname from SYSCA_DEPRUNIT x where a.enterorg = x.enterorg and a.newdeprunit = x.deprunitno) as deprunitname, ").append(
							" (select deprunit1name from SYSCA_DEPRUNIT1 x where a.enterorg = x.enterorg and a.newdeprunit1 = x.deprunit1no) as deprunit1name,").append(
							" (select depraccountsname from SYSCA_DEPRACCOUNTS x where a.enterorg = x.enterorg and a.newdepraccounts = x.depraccountsno) as depraccountsname").append(
		    			" from UNTMP_ADJUSTDETAIL a").append(
							" left join UNTMP_ADJUSTPROOF z on a.enterorg = z.enterorg and a.ownership = z.ownership and a.caseno = z.caseno and a.differencekind = z.differencekind ").append(
							" where 1=1 ").append(
		    			"");
			return stb;
		}
		
		private StringBuilder doGetSQLforUNTVP_AdjustProof(StringBuilder stb){
			stb.append("select ").append(
							" 'VP' as type, ").append(
							" enterOrg, ").append(
							" caseno, ").append(
			    			" null as sourceDate, ").append(
			    			" buyDate, ").append(
			    			" propertyNo, ").append(
							" serialNo, ").append(
			    			" propertyName1, ").append(
	    					" case when a.cause = '99' then '其他：' + a.cause1 else (select codename from SYSCA_CODE z where z.codekindid = 'AJC' and z.codeid = a.cause) end as cause, ").append(
	    					" a.cause1, ").append(
							" keepunit, ").append(
							" keeper, ").append(
							" (case when a.oldbookAmount >0 then a.oldbookAmount else 0 end) as oldBookamount, ").append(
							" (case when a.addbookAmount >0 then a.addbookAmount else 0 end) as addBookamount, ").append(
							" (case when a.reducebookAmount >0 then a.reducebookAmount else 0 end) as reduceBookamount, ").append(
							" (case when a.newbookAmount >0 then a.newbookAmount else 0 end) as newBookamount, ").append(
							" null as oldMeasure, ").append(
							" null as addMeasure, ").append(
							" null as reduceMeasure, ").append(
							" null as newMeasure, ").append(
							" a.otherpropertyunit, ").append(
							" oldbookValue, ").append(
							" addbookValue, ").append(
							" reducebookValue, ").append(
							" newbookValue, ").append(
							" null as oldnetValue, ").append(
							" null as addnetValue, ").append(
							" null as reducenetValue, ").append(
							" null as reduceAccumDepr, ").append(
							" null as newnetValue, ").append(
							" null as otherlimityear, ").append(
							" null as addlimityear, ").append(
							" null as reducelimityear, ").append(		
							" a.oldserialno as oldserialno, ").append(	
							" null as oldaccumdepr, null as newaccumdepr, ").append(
							" null as deprparkname, ").append(
							" null as deprunitname, ").append(
							" null as deprunit1name, ").append(
							" null as depraccountsname ").append(
		    			" from UNTVP_ADJUSTPROOF a where 1=1 ").append(
		    			"");
			return stb;
		}
		
		private StringBuilder doGetSQLforUNTRT_AdjustProof(StringBuilder stb){
			stb.append("select ").append(
							" 'RT' as type, ").append(
							" enterOrg, ").append(
							" caseNo, ").append(
			    			" null as sourceDate, ").append(
			    			" buyDate, ").append(
			    			" propertyNo, ").append(
							" serialNo, ").append(
			    			" propertyName1, ").append(
	    					" case when a.cause = '99' then '其他：' + a.cause1 else (select codename from SYSCA_CODE z where z.codekindid = 'AJC' and z.codeid = a.cause) end as cause, ").append(
	    					" a.cause1, ").append(
							" keepunit, ").append(
							" keeper, ").append(
							" (case when a.oldbookvalue >0 then a.oldbookvalue else 0 end) as oldBookamount, ").append(
							" 0 as addBookamount, ").append(
							" 0 as reduceBookamount, ").append(
							" (case when a.newbookValue >0 then a.newbookValue else 0 end) as newBookamount, ").append(
							" null as oldMeasure, ").append(
							" null as addMeasure, ").append(
							" null as reduceMeasure, ").append(
							" null as newMeasure, ").append(
							" a.otherpropertyunit, ").append(
							" oldbookValue, ").append(
							" addbookValue, ").append(
							" reducebookValue, ").append(
							" newbookValue, ").append(
							" null as oldnetValue, ").append(
							" null as addnetValue, ").append(
							" null as reducenetValue, ").append(
							" a.reduceAccumDepr, ").append(
							" null as newnetValue, ").append(
							" a.otherlimityear, ").append(
							" a.addlimityear, ").append(
							" a.reducelimityear, ").append(
							" a.oldserialno as oldserialno, ").append(
							" a.oldaccumdepr, a.newaccumdepr, ").append(
							" (select deprparkname from SYSCA_DEPRPARK x where a.enterorg = x.enterorg and a.newdeprpark = x.deprparkno) as deprparkname, ").append(
							" (select deprunitname from SYSCA_DEPRUNIT x where a.enterorg = x.enterorg and a.newdeprunit = x.deprunitno) as deprunitname, ").append(
							" (select deprunit1name from SYSCA_DEPRUNIT1 x where a.enterorg = x.enterorg and a.newdeprunit1 = x.deprunit1no) as deprunit1name,").append(
							" (select depraccountsname from SYSCA_DEPRACCOUNTS x where a.enterorg = x.enterorg and a.newdepraccounts = x.depraccountsno) as depraccountsname").append(
		    			" from UNTRT_ADJUSTPROOF a where 1=1 ").append(
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