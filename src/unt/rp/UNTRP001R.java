/*
*<br>程式目的：增加單報表檔 
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
import java.util.Collections;
import java.util.Comparator;
import java.util.Vector;

import javax.swing.table.DefaultTableModel;

import util.*;
import util.report.ReportUtil;
import unt.ch.UNTCH_Tools;
import TDlib_Simple.tools.src.LogTools;
import TDlib_Simple.tools.src.StringTools;
import TDlib_Simple.tools.src.MathTools;

public class UNTRP001R extends SuperBean {
	private LogTools log = new LogTools();
	
	public DefaultTableModel getResultModel() throws Exception{
	    DefaultTableModel model = null;
	    Database db = null;
	    StringBuilder stb = new StringBuilder();
	    try{
	    	String[] columns = new String[] {
						    			"head01","head02","head03","head04","head05",
								    	"head06","head07","head08","head09","head10",
								    	"head11","head12","head13",
								    	"detail01","detail02","detail03","detail04","detail05",
								    	"detail06","detail07","detail08","detail09","detail10",
								    	"detail11","detail12","detail13","detail14","detail15",
								    	"total02",
								    	"tail01",
								    	"q_kind","q_notes",
								    	"total01",
								    	"summonsDate",
								    	"sortList",
								    	"detail16",
								    	"type","originalbv"
								    	};
		
			//=================================================================
			
			stb.append(" select * from (");    	
			stb = doGetSQLforUNTLA_Land(stb);    	
			stb.append(doGetIfCondition("LA"));
			stb.append(" union all ");
			stb = doGetSQLforUNTBU_Building(stb);
			stb.append(doGetIfCondition("BU"));
			stb.append(" union all ");
			stb = doGetSQLforUNTRF_Attachment(stb);
			stb.append(doGetIfCondition("RF"));
			stb.append(" union all ");
			stb = doGetSQLforUNTMP_Movable(stb);
			stb.append(doGetIfCondition("MP"));
			stb.append(" union all ");
			stb = doGetSQLforUNTVP_SHARE(stb);
			stb.append(doGetIfCondition("VP"));
			stb.append(" union all ");
			stb.append("(");
			stb = doGetSQLforUNTRT_AddDetail(stb);
			stb.append(doGetIfCondition("RT"));
			stb.append(")");
			stb.append(") a");
			stb.append(" where a.caseno in ( ");
				//限定100個單號
				stb.append(" select top 100 b.caseno from (");    	
				stb = doGetSQLforUNTLA_Land(stb);    	
				stb.append(doGetIfCondition("LA"));
				stb.append(" union all ");
				stb = doGetSQLforUNTBU_Building(stb);
				stb.append(doGetIfCondition("BU"));
				stb.append(" union all ");
				stb = doGetSQLforUNTRF_Attachment(stb);
				stb.append(doGetIfCondition("RF"));
				stb.append(" union all ");
				stb = doGetSQLforUNTMP_Movable(stb);
				stb.append(doGetIfCondition("MP"));
				stb.append(" union all ");
				stb = doGetSQLforUNTVP_SHARE(stb);
				stb.append(doGetIfCondition("VP"));
				stb.append(" union all ");
				stb.append("(");
				stb = doGetSQLforUNTRT_AddDetail(stb);
				stb.append(doGetIfCondition("RT"));
				stb.append(")");
				stb.append(") b");			
				//限定100個單號
			stb.append(" ) ");
			stb.append(" order by caseNo,propertyNo,serialno ");
			
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
		    
			//使用者操作記錄
			if("".equals(this.getObjPath())){
				this.setObjPath("功能選單 > > 單位財產系統 > > 財產管理 > > 財產增加單維護 > > 列印增加單");
			}
			Common.insertRecordSql(stb.toString(), this.getOrganID(), this.getUserID(), "UNTRP001R", this.getObjPath().replaceAll("&gt;", ">"));
			
			rs = db.querySQL(stb.toString());	
			
		    if(kindName != null){
				int sum_bookAmount_LA = 0;			//數量 總計
				int sum_bookAmount_BU = 0;			//數量 總計
				int sum_bookAmount_RF = 0;			//數量 總計
				int sum_bookAmount_MP = 0;			//數量 總計
				int sum_bookAmount_VP = 0;			//數量 總計
				int sum_bookAmount_RT = 0;			//數量 總計
				String sum_bookUnit_LA = "0";		//單價 總計
				String sum_bookValue_LA = "0";		//總價  總計
				String sum_bookUnit_BU = "0";		//單價 總計
				String sum_bookValue_BU = "0";		//總價  總計
				String sum_bookUnit_RF = "0";		//單價 總計
				String sum_bookValue_RF = "0";		//總價  總計
				String sum_bookUnit_MP = "0";		//單價 總計
				String sum_bookValue_MP = "0";		//總價  總計
				String sum_bookUnit_VP = "0";		//單價 總計
				String sum_bookValue_VP = "0";		//總價  總計
				String sum_bookUnit_RT = "0";		//單價 總計
				String sum_bookValue_RT = "0";		//總價  總計
				String tempCaseNo = "";		    	
		    	while(rs.next()){

		    		for(int i = 0 ; i < kindName.length; i++){					

						
									
					
				    
				    	
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
				    	String writedate = ut._transToROC_Year(ut._queryData("writeDate")._from("UNTLA_ADDPROOF a ")._with(queryCondition)._toString());
				    	if (writedate.length() >= 7) {
				    		data[1] = writedate.substring(0,3) + "年" + writedate.substring(3,5) + "月" + writedate.substring(5) + "日";
				    	} else {
				    		data[1] = "";
				    	}
				    	//填造單位
				    	String writeunit = ut._queryData("writeunit")._from("UNTLA_ADDPROOF a ")._with(queryCondition)._toString();
				    	if(!"".equals(checkGet(writeunit))){
				    		data[2] = ut._queryData("unitname")._from("UNTMP_KEEPUNIT")._with(" and enterorg = '" + getQ_enterOrg() + "' and unitno = '" + writeunit + "'")._toString();
				    	}
				    	//編號
				    	data[3] = ut._queryData("proofYear")._from("UNTLA_ADDPROOF a ")._with(queryCondition)._toString() + 
				    				"年" +
				    				ut._queryData("proofDoc")._from("UNTLA_ADDPROOF a ")._with(queryCondition)._toString() +
				    				"字第" +
				    				ut._queryData("proofNo")._from("UNTLA_ADDPROOF a ")._with(queryCondition)._toString() +
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
				    	String engineeringNotemp = ut._queryData("engineeringNo")._from("UNTLA_ADDPROOF a ")._with(queryCondition)._toString();
	
				    	cond = "";
				    	if(!st._isEmpty(getQ_enterOrg())){
				    		cond += " and a.enterOrg = " + Common.sqlChar(getQ_enterOrg());
						}
				    	
				    	data[4] = ut._queryData("engineeringName")._from("UNTEG_ENGINEERINGCASE a ")._with(cond + " and engineeringNo = '" + engineeringNotemp + "'")._toString();
				    	
				    	//印表人
				    	data[5] = ut._queryData("empname")._from("SYSAP_EMP a ")._with("and organid = '" + getOrganID() + "' and empid = '" + getEditID() + "'")._toString();
				    	//財產區分別
				    	String differenceKind = ut._queryData("differenceKind")._from("UNTLA_ADDPROOF a ")._with(queryCondition)._toString();
				    	data[6] = differenceKind + "　" + ut._getDifferenceKindName(differenceKind);
				    	//合併分割案號		    	
				    	data[7] = ut._queryData("mergeDivision")._from("UNTLA_ADDPROOF a ")._with(queryCondition)._toString();
				    	//全銜
				    	data[8] = ReportUtil.getTitleByEnterOrgCodeNew(this.getQ_enterOrg(), this.getQ_differenceKind());
				    	
				    	//第　　聯
				    	data[9] = kindName[i];
				    	//電腦單號
				    	data[10] = checkGet(rs.getString("caseNo"));
				    	//財產管理單位編號
				    	data[11] = checkGet(rs.getString("enterOrg"));
				    	//傳票號數
				    	data[12] = ut._queryData("summonsNo")._from("UNTLA_ADDPROOF a ")._with(queryCondition)._toString();
				    	
				    	//===========================================================
				    	//						Detail
				    	//===========================================================
				    	//取得日期  購置日期
				    	data[13] = Common.MaskDate(ut._transToROC_Year(checkGet(rs.getString("sourceDate")))) + "\n" + 
				    			   Common.MaskDate(ut._transToROC_Year(checkGet(rs.getString("buyDate"))));
				    	//財產編號  財產名稱  財產大類
				    	data[14] = checkGet(rs.getString("propertyNo")) + "－" + checkGet(rs.getString("serialNo")) + "\n" + 
									ut._getPropertyNoName(rs.getString("enterOrg"),rs.getString("propertyNo")) + "\n" + 
									ut._getPropertyNoGroupName(rs.getString("propertyNo"));
				    	//別名/型式/廠牌（或土地建物標示）
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
						String check_specification = Common.get(rs.getString("specification"));
						String check_nameplate = checkGet(rs.getString("nameplate"));
						    	
				    	data[15] =  check_propertyName1 + 
				    				(("".equals(check_specification))?"":"/" + check_specification) +
				    				(("".equals(check_nameplate))?"":"/" + check_nameplate) +
					    			 "\n" +
					    			signNoName;
		
				    	//來源
				    	data[16] = ut._getSourceKindName(checkGet(rs.getString("sourceKind")));
				    	//單位 數量
				    	String checkStr1 = rs.getString("propertyno").substring(0,1);
				    	String checkStr3 = rs.getString("propertyno").substring(0,3);
				    	if ("111".equals(checkStr3)) {
				    		data[17] = ut._getPropertyUnit(rs.getString("propertyNo")); 
				    		data[18] = st._getMoneyFormat(checkGet(rs.getString("originalMeasure")));
				    	} else if ("1".equals(checkStr1) || "2".equals(checkStr1)) {
				    		data[17] = "平方公尺"; 
//				    		data[18] = checkGet(rs.getString("originalHoldArea"));
				    		data[18] = rs.getString("originalHoldArea");
				    		
				    	} else {
				    		data[17] = Common.get(rs.getString("otherpropertyunit"));
				    		if ("".equals(Common.get(data[17]))) {
				    			data[17] = ut._getPropertyUnit(rs.getString("propertyNo"));
				    		}
				    		data[18] = rs.getString("originalAmount");
				    	}
				    	//單價  總價
				    	if(rs.getString("type").equals("MP")){
				    		String originalunit = "".equals(checkGet(rs.getString("originalunit")))? checkGet(rs.getString("originalbv")):checkGet(rs.getString("originalunit"));
				    		data[19] = st._getMoneyFormat(originalunit) + "\n" +
		    				st._getMoneyFormat(checkGet(rs.getString("originalbv")));
				    	}else{
				    		data[19] = st._getMoneyFormat(checkGet(rs.getString("originalunit"))) + "\n" +
				    				st._getMoneyFormat(checkGet(rs.getString("originalbv")));
				    	}
				    	//保管單位 存置地點
				    	data[20] = Common.get(ut._queryData("unitname")._from("UNTMP_KEEPUNIT a ")._with(" and enterorg = '" + rs.getString("enterorg") + "' and unitno = '" + rs.getString("keepunit") + "'")._toString()) + "\n" +
				    	checkGet(rs.getString("place") + "\n" + checkGet(rs.getString("placeDetail")));
				    	//保管人簽章
				    	data[21] = checkGet(ut._queryData("keepername")._from("UNTMP_KEEPER")._with(" and enterorg = '" + rs.getString("enterorg") + "' and keeperno = '" + checkGet(rs.getString("keeper")) + "'")._toString());
				    	//折舊方法
				    	data[22] = ut._getDeprMethodName(checkGet(rs.getString("deprmethod")));
				    	//殘值        	
				    	data[23] = st._getMoneyFormat(checkGet(rs.getString("scrapvalue")));
				    	//使用年限
				    	if (!"0".equals(checkGet(rs.getString("originallimityear"))) && !"".equals(checkGet(rs.getString("originallimityear")))) {
				    		data[24] = checkGet(rs.getString("originallimityear"))+"年0個月";
				    	} else {
				    		int otherLimitMonth = "".equals(Common.get(rs.getString("otherlimityear")))? 0 : Integer.parseInt(rs.getString("otherLimitYear")); // otherLimitYear以單位月分存取需轉成年
							int year = otherLimitMonth /12;
							int month = otherLimitMonth % 12;
							data[24] = year +"年"+month+"個月";
				    	}
	
				    	//園區別
				    	data[25] = ut._queryData("deprparkname")._from("SYSCA_DEPRPARK")._with(" and deprparkno = '" + checkGet(rs.getString("deprPark")) + "' and enterorg = '" + rs.getString("enterorg") + "'")._toString();
				    	//部門別        	
				    	data[26] = ut._queryData("deprunitname")._from("SYSCA_DEPRUNIT")._with(" and deprunitno = '" + checkGet(rs.getString("deprUnit")) + "' and enterorg = '" + rs.getString("enterorg") + "'")._toString();
				    	//部門別單位(1070622問題單1728)
				    	data[35] = ut._queryData("deprunit1name")._from("SYSCA_DEPRUNIT1")._with("and deprunit1no = '" + checkGet(rs.getString("deprUnit1")) + "' and enterorg = '" + rs.getString("enterorg") + "'")._toString();
				    	//會計科目        	
				    	data[27] = ut._queryData("depraccountsname")._from("SYSCA_DEPRACCOUNTS")._with(" and depraccountsno = '" + checkGet(rs.getString("depraccounts")) + "' and enterorg = '" + rs.getString("enterorg") + "'")._toString();
				    	
	
				    	
				    	//===========================================================
				    	//						Total
				    	//===========================================================
				    	if(i==0){//第一次才加總計算
					    	if(!tempCaseNo.equals(rs.getString("caseNo"))){
					    		 sum_bookUnit_LA = "0";		//單價 總計
								 sum_bookValue_LA = "0";		//總價  總計
								 sum_bookUnit_BU = "0";		//單價 總計
								 sum_bookValue_BU = "0";		//總價  總計
								 sum_bookUnit_RF = "0";		//單價 總計
								 sum_bookValue_RF = "0";		//總價  總計
								 sum_bookUnit_MP = "0";		//單價 總計
								 sum_bookValue_MP = "0";		//總價  總計
								 sum_bookUnit_VP = "0";		//單價 總計
								 sum_bookValue_VP = "0";		//總價  總計
								 sum_bookUnit_RT = "0";		//單價 總計
								 sum_bookValue_RT = "0";		//總價  總計
								 sum_bookAmount_LA = 0;			//數量 總計
								 sum_bookAmount_BU = 0;			//數量 總計
								 sum_bookAmount_RF = 0;			//數量 總計
								 sum_bookAmount_MP = 0;			//數量 總計
								 sum_bookAmount_VP = 0;			//數量 總計
								 sum_bookAmount_RT = 0;			//數量 總計								 
					    	}
					    	//單價  總價  總計
					    	if(rs.getString("type").equals("LA")){
							    	sum_bookUnit_LA = mt._addition_withBigDecimal(sum_bookUnit_LA, ("".equals(checkGet(rs.getString("originalunit")))?"0":rs.getString("originalunit")));
							    	sum_bookValue_LA = mt._addition_withBigDecimal(sum_bookValue_LA, ("".equals(checkGet(rs.getString("originalbv")))?"0":rs.getString("originalbv")));
							    	sum_bookAmount_LA++;
					    	}
					    	else if(rs.getString("type").equals("BU")){
					    		sum_bookUnit_BU = mt._addition_withBigDecimal(sum_bookUnit_BU, ("".equals(checkGet(rs.getString("originalunit")))?"0":rs.getString("originalunit")));
						    	sum_bookValue_BU = mt._addition_withBigDecimal(sum_bookValue_BU, ("".equals(checkGet(rs.getString("originalbv")))?"0":rs.getString("originalbv")));
						    	sum_bookAmount_BU++;
					    	}
					    	else if(rs.getString("type").equals("RF")){
					    		sum_bookUnit_RF = mt._addition_withBigDecimal(sum_bookUnit_RF, ("".equals(checkGet(rs.getString("originalunit")))?"0":rs.getString("originalunit")));
						    	sum_bookValue_RF = mt._addition_withBigDecimal(sum_bookValue_RF, ("".equals(checkGet(rs.getString("originalbv")))?"0":rs.getString("originalbv")));
						    	sum_bookAmount_RF++;
					    	}
					    	else if(rs.getString("type").equals("MP")){
					    		sum_bookUnit_MP = mt._addition_withBigDecimal(sum_bookUnit_MP, ("".equals(checkGet(rs.getString("originalunit")))?"0":rs.getString("originalunit")));
						    	sum_bookValue_MP = mt._addition_withBigDecimal(sum_bookValue_MP, ("".equals(checkGet(rs.getString("originalbv")))?"0":rs.getString("originalbv")));
						    	sum_bookAmount_MP++;
					    	}
					    	else if(rs.getString("type").equals("VP")){
					    		sum_bookUnit_VP = mt._addition_withBigDecimal(sum_bookUnit_VP, ("".equals(checkGet(rs.getString("originalunit")))?"0":rs.getString("originalunit")));
						    	sum_bookValue_VP = mt._addition_withBigDecimal(sum_bookValue_VP, ("".equals(checkGet(rs.getString("originalbv")))?"0":rs.getString("originalbv")));
						    	sum_bookAmount_VP++;
					    	}
					    	else if(rs.getString("type").equals("RT")){
					    		sum_bookUnit_RT = mt._addition_withBigDecimal(sum_bookUnit_RT, ("".equals(checkGet(rs.getString("originalunit")))?"0":rs.getString("originalunit")));
						    	sum_bookValue_RT = mt._addition_withBigDecimal(sum_bookValue_RT, ("".equals(checkGet(rs.getString("originalbv")))?"0":rs.getString("originalbv")));
						    	sum_bookAmount_RT++;
					    	}
					    	if(rs.getString("type").equals("LA")){
						    	tempCaseNo = rs.getString("caseNo");
						    	data[28] = st._getMoneyFormat(sum_bookUnit_LA);
					    	}
					    	else if(rs.getString("type").equals("BU")){
					    		tempCaseNo = rs.getString("caseNo");
					    		data[28] = st._getMoneyFormat(sum_bookUnit_BU);  
					    	}
					    	else if(rs.getString("type").equals("RF")){
					    		tempCaseNo = rs.getString("caseNo");
					    		data[28] = st._getMoneyFormat(sum_bookUnit_RF);  
					    	}
					    	else if(rs.getString("type").equals("MP")){
					    		tempCaseNo = rs.getString("caseNo");
					    		data[28] = st._getMoneyFormat(sum_bookUnit_MP); 
					    	}
					    	else if(rs.getString("type").equals("VP")){
					    		tempCaseNo = rs.getString("caseNo");
					    		data[28] = st._getMoneyFormat(sum_bookUnit_VP); 
					    	}
					    	else if(rs.getString("type").equals("RT")){
					    		tempCaseNo = rs.getString("caseNo");
					    		data[28] = st._getMoneyFormat(sum_bookUnit_RT);
					    	}
				    	}
				    	
				    	data[28] = st._getMoneyFormat(String.valueOf(Long.parseLong(sum_bookValue_LA)+Long.parseLong(sum_bookValue_BU)+Long.parseLong(sum_bookValue_RF)+Long.parseLong(sum_bookValue_MP)+Long.parseLong(sum_bookValue_VP)+Long.parseLong(sum_bookValue_RT)));
				    	
				    	data[32] = String.valueOf(sum_bookAmount_LA+sum_bookAmount_BU+sum_bookAmount_RF+sum_bookAmount_MP+sum_bookAmount_VP+sum_bookAmount_RT);
				    	//===========================================================
				    	//						Tail
				    	//===========================================================
				    	//單據備註
				    	data[29] = ut._queryData("notes")._from("UNTLA_ADDPROOF a ")._with(queryCondition)._toString();
				    	
				    	//聯數群組判斷用
				    	data[30] = String.valueOf(i);
				    	//判斷是否列印備註用
				    	data[31] = q_note;
				    	
				    	//傳票日期
				    	String summonsDate = ut._transToROC_Year(ut._queryData("summonsDate")._from("UNTLA_ADDPROOF a ")._with(queryCondition)._toString());
				    	if(!summonsDate.equals("")){
				    		data[33] = summonsDate.substring(0,3) + "年" + summonsDate.substring(3,5) + "月" + summonsDate.substring(5) + "日";
				    	}else{
				    		data[33] = "";
				    	}
				    	//排序用欄位
				    	data[34] = checkGet(rs.getString("caseNo"))+"_"+i;
				    	
				    	//財產大類
				    	if("MP".equals(Common.get(rs.getString("type")))) {
				    		data[36] = Common.get(rs.getString("type")) + checkStr1;
				    	}else {
					    	data[36] = Common.get(rs.getString("type"));
				    	}
				    	
				    	data[37] = BigDecimal.valueOf(rs.getDouble("originalbv"));
				    	
				    	rowData.addElement(data);
					}
		    	}
		    }
		    
		    //第一聯、第二聯、第三聯 排序用
		    Collections.sort(rowData,
		            new Comparator<Object[]>() {
		                public int compare(Object[] o1, Object[] o2) {
		                    return o1[34].toString().compareTo(o2[34].toString());
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
				stb.append(" and a.enterorg = ").append(Common.sqlChar(getQ_enterOrg()));
			}
			if(!st._isEmpty(getQ_ownership())){
				stb.append(" and a.ownership = ").append(Common.sqlChar(getQ_ownership()));
			}
			if(!st._isEmpty(getQ_caseNoS())){
				stb.append(" and a.caseno >= ").append(Common.sqlChar(getQ_caseNoS()));
			}
			if(!st._isEmpty(getQ_caseNoE())){
				stb.append(" and a.caseno <= ").append(Common.sqlChar(getQ_caseNoE()));
			}
			
			String alias = "z.";
			if("RT".equals(table) || "".equals(table)){
				alias = "a.";
			}else if("VP".equals(table)){
				alias = "g.";
			}
			
			if(!st._isEmpty(getQ_writeDateS())){
				stb.append(" and " + alias + "writedate >= ").append(Common.sqlChar(ut._transToCE_Year(getQ_writeDateS())));
			}
			if(!st._isEmpty(getQ_writeDateE())){
				stb.append(" and " + alias + "writedate <= ").append(Common.sqlChar(ut._transToCE_Year(getQ_writeDateE())));
			}
			if(!st._isEmpty(getQ_proofYear())){
				stb.append(" and " + alias + "proofyear = ").append(Common.sqlChar(getQ_proofYear()));
			}
			if(!st._isEmpty(getQ_proofDoc())){
				stb.append(" and " + alias + "proofdoc = ").append(Common.sqlChar(getQ_proofDoc()));
			}
			if(!st._isEmpty(getQ_proofNoS())){
				stb.append(" and " + alias + "proofno >= ").append(Common.sqlChar(getQ_proofNoS()));
			}
			if(!st._isEmpty(getQ_proofNoE())){
				stb.append(" and " + alias + "proofno <= ").append(Common.sqlChar(getQ_proofNoE()));
			}
			
	    		
			return stb.toString();
		}
	
	//=================================================================
	//	各Table的查詢SQL組成
	//=================================================================
		
		private StringBuilder doGetSQLforUNTLA_Land(StringBuilder stb){
			stb.append("select ").append(
							" 'LA' as type, ").append(
							" a.enterOrg, ").append(
							" a.caseNo, ").append(
		    				" a.sourceDate, ").append(
		    				" a.buyDate, ").append(
							" a.propertyNo, ").append(
							" a.serialNo, ").append(
							" a.propertyName1, ").append(
							" null as specification, ").append(
							" null as nameplate, ").append(
							" a.signNo, ").append(
							" a.sourceKind, ").append(
							" a.originalHoldArea, ").append(
							" null as originalMeasure, ").append(
							" null as originalAmount, ").append(
							" originalHoldArea as measure, ").append(
							" a.originalunit, ").append(
							" a.originalbv, ").append(
							" isnull((select placename from SYSCA_PLACE p where a.place1 = p.placeno and a.enterorg = p.enterorg),a.place) as place, ").append(
							" a.keepunit, a.place as placeDetail, ").append(
							" a.keeper, ").append(
							" null as otherlimityear, ").append(
							" null as otherpropertyunit, ").append(
							" null as originallimityear, ").append(
							" null as scrapvalue, ").append(
							" null as depraccounts, ").append(
							" null as deprPark, ").append(
							" null as deprUnit, ").append(
							//1070622問題單1728
							" null as deprUnit1, ").append(
							" null as deprmethod ").append(
						"from UNTLA_LAND a").append(
							" left join UNTLA_ADDPROOF z on a.enterorg = z.enterorg and a.ownership = z.ownership and a.caseno = z.caseno and a.differencekind = z.differencekind ").append(
							" where 1=1 ").append(
						""); 
			return stb;
		}
		
		private StringBuilder doGetSQLforUNTBU_Building(StringBuilder stb){
			stb.append("select ").append(
							" 'BU' as type, ").append(
							" a.enterOrg, ").append(
							" a.caseNo, ").append(
			    			" a.sourceDate, ").append(
			    			" a.buyDate, ").append(
			    			" a.propertyNo, ").append(
	    					" a.serialNo, ").append(
			    			" a.propertyName1, ").append(
			    			" null as specification, ").append(
			    			" null as nameplate,").append(
			    			" a.signNo, ").append(
			    			" a.sourceKind, ").append(
	    					" a.originalHoldArea, ").append(
							" null as originalMeasure, ").append(
							" null as originalAmount, ").append(
			    			" originalHoldArea as measure, ").append(
			    			" a.originalbv as originalunit,").append(
			    			" a.originalbv, ").append(
			    			" isnull((select placename from SYSCA_PLACE p where a.place1 = p.placeno and a.enterorg = p.enterorg),a.place) as place, ").append(
	    					" a.keepunit, a.place as placeDetail, ").append(
			    			" a.keeper, ").append(
			    			" a.otherlimityear, ").append(
			    			" a.otherpropertyunit, ").append(
			    			" 0 as originallimityear, ").append(
							" a.scrapvalue, ").append(
			    			" a.depraccounts, ").append(
	    					" a.deprPark, ").append(
							" a.deprUnit, ").append(
							//1070622問題單1728
							" a.deprUnit1, ").append(
			    			" a.deprmethod").append(
		    			" from UNTBU_BUILDING a ").append(
	    					" left join UNTBU_ADDPROOF z on a.enterorg = z.enterorg and a.ownership = z.ownership and a.caseno = z.caseno and a.differencekind = z.differencekind ").append(
							" where 1=1 ").append(
		    			"");
			return stb;
		}
		
		private StringBuilder doGetSQLforUNTRF_Attachment(StringBuilder stb){
			stb.append("select ").append(
							" 'RF' as type, ").append(
							" a.enterOrg, ").append(
							" a.caseNo, ").append(
			    			" a.sourceDate, ").append(
			    			" a.buydate as buyDate, ").append(
			    			" a.propertyNo, ").append(
	    					" a.serialNo, ").append(
			    			" a.propertyName1, ").append(
			    			" null as specification, ").append(
			    			" null as nameplate,").append(
			    			" null as signNo, ").append(
			    			" a.sourceKind, ").append(
	    					" null as originalHoldArea, ").append(
							" a.originalMeasure, ").append(
							" null as originalAmount, ").append(
			    			" a.originalMeasure, ").append(
			    			" a.originalbv as originalunit,").append(
			    			" a.originalbv, ").append(
			    			" isnull((select placename from SYSCA_PLACE p where a.place1 = p.placeno and a.enterorg = p.enterorg),a.place) as place, ").append(
	    					" a.keepunit, a.place as placeDetail, ").append(
							" a.keeper, ").append(
							" a.otherlimityear, ").append(
							" a.otherpropertyunit, ").append(
			    			" 0 as originallimityear, ").append(
			    			" a.scrapvalue, ").append(
			    			" a.depraccounts, ").append(
	    					" a.deprPark, ").append(
							" a.deprUnit, ").append(
							//1070622問題單1728
							" a.deprUnit1, ").append(
			    			" a.deprmethod").append(
		    			" from UNTRF_ATTACHMENT a ").append(
		    				" left join UNTRF_ADDPROOF z on a.enterorg = z.enterorg and a.ownership = z.ownership and a.caseno = z.caseno and a.differencekind = z.differencekind ").append(
    						" where 1=1 ").append(
		    			"");
			return stb;
		}
		
		private StringBuilder doGetSQLforUNTMP_Movable(StringBuilder stb){
			stb.append("select ").append(
							" 'MP' as type, ").append(
							" a.enterOrg, ").append(
							" a.caseNo, ").append(
			    			" a.sourceDate, ").append(
			    			" a.buyDate, ").append(
			    			" a.propertyNo, ").append(
	    					" b.serialNo, ").append(
			    			" b.propertyName1, ").append(
			    			" a.specification, ").append(
			    			" a.nameplate,").append(
			    			" null as signNo, ").append(
			    			" a.sourceKind, ").append(
	    					" null as originalHoldArea, ").append(
							" null as originalMeasure, ").append(
							" b.bookamount, ").append(
			    			" null as measure, ").append(
			    			" a.originalUnit,").append(
			    			" b.originalBV, ").append(
			    			" isnull((select placename from SYSCA_PLACE p where b.place1 = p.placeno and b.enterorg = p.enterorg),b.place) as place, ").append(
	    					" b.keepunit, b.place as placeDetail, ").append(
							" b.keeper, ").append(
							" b.otherlimityear, ").append(
							" a.otherpropertyunit, ").append(
			    			" 0 as originallimityear, ").append(
			    			" b.scrapvalue, ").append(
			    			" b.depraccounts, ").append(
	    					" b.deprPark, ").append(
							" b.deprUnit, ").append(
							//1070622問題單1728
							" b.deprUnit1, ").append(
			    			" b.deprmethod").append(
		    			" from UNTMP_MOVABLE a left join UNTMP_MOVABLEDETAIL b").append(
		    					" on b.enterorg = a.enterorg").append(
		    					" and b.ownership = a.ownership").append(
		    					" and b.differenceKind = a.differenceKind").append(
		    					" and b.propertyno = a.propertyno").append(
		    					" and b.serialno between a.serialnos and a.serialnoe").append(
		    				" left join UNTMP_ADDPROOF z on a.enterorg = z.enterorg and a.ownership = z.ownership and a.caseno = z.caseno and a.differencekind = z.differencekind ").append(
		    					" where 1=1 ").append(
		    			"");
			return stb;
		}
		
		private StringBuilder doGetSQLforUNTVP_SHARE(StringBuilder stb){
			stb.append("select ").append(
							" 'VP' as type, ").append(
							" a.enterorg, ").append(
							" a.caseno as caseNo, ").append(
			    			" a.sourceDate as sourceDate, ").append(
			    			" a.buydate, ").append(
			    			" a.propertyno, ").append(
	    					" a.serialno, ").append(
			    			" a.propertyName1, ").append(
			    			" null as specification, ").append(
			    			" null as nameplate,").append(
			    			" null as signNo, ").append(
			    			" a.sourcekind as sourceKind, ").append(
	    					" null as originalHoldArea, ").append(
							" null as originalMeasure, ").append(
			    			" a.originalamount, ").append(
			    			" a.originalamount as measure, ").append(
			    			" a.originalbv as originalunit,").append(
			    			" a.originalbv, ").append(
			    			" isnull((select placename from SYSCA_PLACE p where a.place1 = p.placeno and a.enterorg = p.enterorg),a.place) as place, ").append(
	    					" a.keepunit, a.place as placeDetail, ").append(
							" a.keeper, ").append(
							" null as otherlimityear, ").append(
							" a.otherpropertyunit, ").append(
							" null as originallimityear, ").append(
			    			" null as scrapvalue, ").append(
			    			" null as depraccounts, ").append(
	    					" null as deprPark, ").append(
							" null as deprUnit, ").append(
							//1070622問題單1728
							" null as deprUnit1, ").append(
			    			" null as deprmethod").append(
		    			" from UNTVP_ADDPROOF a ").append(
		    			" left join UNTLA_ADDPROOF g on a.enterorg=g.enterorg and a.ownership=g.ownership and a.caseno=g.caseno and a.differencekind=g.differencekind ").append(
		    			" where 1=1 ").append(
		    			"");
			return stb;
		}
		
		private StringBuilder doGetSQLforUNTRT_AddDetail(StringBuilder stb){
			stb.append("select ").append(
							" 'RT' as type, ").append(
							" a.enterOrg, ").append(
							" caseNo, ").append(
			    			" sourceDate, ").append(
			    			" buyDate, ").append(
			    			" a.propertyNo, ").append(
	    					" a.serialNo, ").append(
			    			" propertyName1, ").append(
			    			" null as specification, ").append(
			    			" null as nameplate,").append(
			    			" z.signNo, ").append(
			    			" a.sourcekind as sourceKind, ").append(
			    			" null as originalHoldArea, ").append(
							" null as originalMeasure, ").append(
							" '1' as originalAmount, ").append(
			    			" '1' as measure, ").append(
			    			" null as originalunit,").append(
			    			" originalbv, ").append(
			    			" isnull((select placename from SYSCA_PLACE p where a.place1 = p.placeno and a.enterorg = p.enterorg),a.place) as place, ").append(
	    					" keepunit, a.place as placeDetail, ").append(
							" keeper, ").append(
							" a.otherlimityear, ").append(
							" a.otherpropertyunit, ").append(
							" 0 as originallimityear, ").append(
			    			" a.scrapvalue as scrapvalue, ").append(
			    			" a.depraccounts as depraccounts, ").append(
	    					" a.deprpark as deprPark, ").append(
							" a.deprunit as deprUnit, ").append(
							//1070622問題單1728
							" a.deprunit1 as deprUnit1, ").append(
			    			" a.deprmethod as deprmethod").append(
		    			" from UNTRT_ADDPROOF a ").append(
		    					"left join UNTRT_ADDDETAIL z ").append(
			    					"on z.enterorg = a.enterorg ").append(
	    							"and z.ownership = a.ownership ").append(
									"and z.propertyno = a.propertyno ").append(
									"and z.serialno = a.serialno ").append(
		    					"where 1=1 ").append(
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