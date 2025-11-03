/*
*<br>程式目的：財產移交清冊
*<br>程式代號：
*<br>撰寫日期：
*<br>程式作者：
*<br>--------------------------------------------------------
*<br>修改作者　　修改日期　　　修改目的
*<br>--------------------------------------------------------
*/
package unt.rp;

import java.sql.ResultSet;
import java.util.Map;
import java.util.Vector;

import javax.swing.table.DefaultTableModel;

import unt.ch.UNTCH_Tools;
import util.Common;
import util.Database;
import util.Datetime;
import util.SuperBean;
import util.TCGHCommon;
import TDlib_Simple.tools.src.LogTools;
import TDlib_Simple.tools.src.StringTools;

public class UNTRP003R extends SuperBean {
	private LogTools log = new LogTools();
	
	public DefaultTableModel getResultModel() throws Exception{
	    DefaultTableModel model = null;
	    Database db = null;
	    StringBuilder stb = new StringBuilder();
	    try{
	    	String[] columns = new String[] {
						    			"head01","head02","head03","head04",
								    	"detail01","detail02","detail03","detail04","detail05",
								    	"detail06","detail07","detail08","detail09","detail10",
								    	"keeper","mode","differencekind","head05"
								    	};
		
			//=================================================================
			
	    	stb.append(" select * from (");    	
			stb = doGetSQLforUNTLA_LAND(stb);    	
			stb.append(doGetIfCondition());
			stb.append(" union ");
			stb = doGetSQLforUNTBU_BUILDING(stb);
			stb.append(doGetIfCondition());
			stb.append(" union ");
			stb = doGetSQLforUNTRF_ATTACHMENT(stb);
			stb.append(doGetIfCondition());
			stb.append(" union ");
			stb = doGetSQLforUNTMP_MOVABLEDETAIL(stb);
			stb.append(doGetIfCondition());
			stb.append(" union ");
			stb = doGetSQLforUNTVP_ADDPROOF(stb);
			stb.append(doGetIfCondition());
			stb.append(" union ");
			stb = doGetSQLforUNTRT_ADDPROOF(stb);
			stb.append(doGetIfCondition());
			stb.append(" union ");
			stb = doGetSQLforUNTNE_NONEXPDETAIL(stb);
			stb.append(doGetIfCondition());
			stb.append(") a");
			
			stb.append(" order by keeper,mode desc,differencekind,propertyNo");
			//=================================================================			
			log._execLogDebug(stb.toString());
			//=================================================================
			
			UNTCH_Tools ut = new UNTCH_Tools();
			StringTools st = new StringTools();
			Map<String,String> DifferenceKindMap = TCGHCommon.getSysca_Code("DFK");
			
			db = new Database();
			ResultSet rs = null;
		    Vector rowData = new Vector();		    
			rs = db.querySQL(stb.toString());
	    	
	    	//===========================================================
	    	//						Head
	    	//===========================================================
	    	//印表日期
	    	String datetime = Datetime.getYYYMMDD();
	    	String head01 = datetime.substring(0,3) + "年" + datetime.substring(3,5) + "月" + datetime.substring(5) + "日";
	    	//印表人
	    	String head02 = ut._queryData("empname")._from("SYSAP_EMP a ")._with("and organid = '" + getOrganID() + "' and empid = '" + getEditID() + "'")._toString();
	    	//保管人
	    	String head03 = ut._queryData("keepername")._from("UNTMP_KEEPER a ")._with("and enterorg = '" + getOrganID() + "' and keeperno = '" + getKeeper() + "'")._toString();
	    	//全銜
	    	String head04 = ut._queryData("titlename1")._from("SYSCA_ORGAN a ")._with(" and organid = '" + this.getQ_enterOrg() + "'")._toString();
	    	String head05 = ut._queryData("titlename2")._from("SYSCA_ORGAN a ")._with(" and organid = '" + this.getQ_enterOrg() + "'")._toString();
	    	
		    while(rs.next()){
		    	
		    	Object[] data = new Object[columns.length];
		    	
		    	//===========================================================
		    	//						Head
		    	//===========================================================
		    	//印表日期
		    	data[0] = head01;
		    	//印表人
		    	data[1] = head02;
		    	//保管人(條件沒有指定保管人時使用查詢出來的保管人)
		    	data[2] = ("".equals(head03))? checkGet(rs.getString("keeperName")) : head03;
		    	//全銜
		    	data[3] = head04;
		    	
		    	//===========================================================
		    	//						Detail
		    	//===========================================================
		    	//取得日期
		    	data[4] = Common.MaskDate(ut._transToROC_Year(checkGet(rs.getString("sourceDate"))));
		    	//財產編號  財產分號
		    	data[5] = checkGet(rs.getString("propertyNo")) + "-" + checkGet(rs.getString("serialNo"));
		    	//data[5] = ReportUtil.getStringWithOldSerialNo(this.getQ_enterOrg(), Common.get(data[5]), "\r\n", "原財產分號", Common.get(rs.getString("oldserialno")));
		    	
		    	//別名/型式/廠牌 (或土地建物標示）
				    	String signNoStr = rs.getString("signNo");
				    	String typeStr;
				    	String signNoName = "";
				    	if(!"".equals(checkGet(signNoStr)) && signNoStr.length() >= 7 ){
				    //		System.out.println("signNoStr:"+signNoStr);
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
				    	
		    	data[6] =  check_propertyName1 + 
		    				(("".equals(check_specification))?"":"/" + check_specification) +
		    				(("".equals(check_nameplate))?"":"/" + check_nameplate) +
			    			 "\n" +
			    			signNoName;
		    	//單位
//		    	data[7] = ut._getPropertyUnit(rs.getString("propertyNo"));
		    	data[7] = checkGet(rs.getString("otherpropertyunit"));
		    	//數量
		    	data[8] = checkGet(rs.getString("amount"));
		    	//總價
		    	data[9] = st._getMoneyFormat(checkGet(rs.getString("bookvalue")));
		    	//年限
//		    	data[10] = ut._getLimitYear(rs.getString("propertyNo"));
//		    	if("".equals(data[10])){
//		    		data[10] = checkGet(rs.getString("otherlimityear"));
//		    	}
		    	int otherLimitMonth = "".equals(Common.get(rs.getString("otherLimitYear")))? 0 : Integer.parseInt(rs.getString("otherLimitYear")); // otherLimitYear以單位月分存取需轉成年
				int year = otherLimitMonth /12;
				int month = otherLimitMonth % 12;
				data[10] = year +"年"+month+"個月";
		    	
		    	//存置地點
		    	data[11] = ut._queryData("placename")._from("SYSCA_PLACE a ")._with(" and enterorg = '" + rs.getString("enterorg") + "' and placeno = '" + rs.getString("place1") + "'")._toString();
		    	//保管單位
		    	data[12] = checkGet(rs.getString("keepUnitName"));
		    	//存置地點說明
		    	data[13] = checkGet(rs.getString("place"));
		    	
		    	//===========================================================
		    	data[14] = checkGet(rs.getString("keeper"));
		    	
		    	data[15] = checkGet(rs.getString("mode"));
		    	
		    	String DIF = checkGet(rs.getString("differencekind"));
	            data[16] = DifferenceKindMap.get(DIF).toString();
		    	data[17] = head05;
		    	rowData.addElement(data);
		    	
			}
		    if(rowData.isEmpty()){
		    	// 查詢無資料時補上head部分
		    	Object[] data = new Object[columns.length];
		    	//印表日期
		    	data[0] = head01;
		    	//印表人
		    	data[1] = head02;
		    	//保管人
		    	data[2] = head03;
		    	//全銜
		    	data[3] = head04;
		    	data[17] = head05;
		    	rowData.addElement(data);
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
		private String doGetIfCondition(){
			StringTools st = new StringTools();
			UNTCH_Tools ut = new UNTCH_Tools();
			StringBuilder stb = new StringBuilder();
			
			if(!st._isEmpty(getQ_enterOrg())){
				stb.append(" and a.enterOrg = ").append(Common.sqlChar(getQ_enterOrg()));
			}
//			if(!st._isEmpty(getQ_ownership())){
//				stb.append(" and a.ownership = ").append(Common.sqlChar(getQ_ownership()));
//			}
//			if(!st._isEmpty(getQ_caseNoS())){
//				stb.append(" and a.caseNo >= ").append(Common.sqlChar(getQ_caseNoS()));
//			}
//			if(!st._isEmpty(getQ_caseNoE())){
//				stb.append(" and a.caseNo <= ").append(Common.sqlChar(getQ_caseNoE()));
//			}
//			if(!st._isEmpty(getQ_writeDateS())){
//				stb.append(" and a.writeDate >= ").append(Common.sqlChar(ut._transToCE_Year(getQ_writeDateS())));
//			}
//			if(!st._isEmpty(getQ_writeDateE())){
//				stb.append(" and a.writeDate <= ").append(Common.sqlChar(ut._transToCE_Year(getQ_writeDateE())));
//			}
//			if(!st._isEmpty(getQ_proofYear())){
//				stb.append(" and a.proofYear = ").append(Common.sqlChar(getQ_proofYear()));
//			}
//			if(!st._isEmpty(getQ_proofDoc())){
//				stb.append(" and a.proofDoc = ").append(Common.sqlChar(getQ_proofDoc()));
//			}
//			if(!st._isEmpty(getQ_proofNoS())){
//				stb.append(" and a.proofNo >= ").append(Common.sqlChar(getQ_proofNoS()));
//			}
//			if(!st._isEmpty(getQ_proofNoE())){
//				stb.append(" and a.proofNo <= ").append(Common.sqlChar(getQ_proofNoE()));
//			}
			if(!st._isEmpty(getKeeper())){
				stb.append(" and a.keeper = ").append(Common.sqlChar(getKeeper()));
			}
			
			stb.append(" and a.verify = 'Y'")
			.append(" and a.datastate = '1'");
			
	    		
			return stb.toString();
		}
	
	//=================================================================
	//	各Table的查詢SQL組成
	//=================================================================
		
		private StringBuilder doGetSQLforUNTLA_LAND(StringBuilder stb){
			stb.append("select ").append(
							" 'LA' as type, ").append(
							" '財產' as mode, ").append(
							" differencekind , ").append(
							" enterorg, ").append(
							" sourceDate, ").append(
							" propertyNo, ").append(
							" serialNo, ").append(
							" propertyName1, ").append(
							" null as specification, ").append(
							" null as nameplate, ").append(
							" '1' as amount, ").append(
							" bookvalue, ").append(		
							" place1, ").append(
							" keepunit, ").append(
							" keeper, ").append(													
							" (select unitname from UNTMP_KEEPUNIT z where z.enterorg = a.enterorg and z.unitno = a.keepUnit) as keepUnitName,").append(
							" (select keepername from UNTMP_KEEPER z where z.enterorg = a.enterorg and z.keeperno = a.keeper) as keeperName,").append(
							" place, ").append(
							" signno, ").append(	
							" null as otherpropertyunit, ").append(	
							" null as otherlimityear, ").append(
							" a.oldserialno as oldserialno ").append(	
						"from UNTLA_LAND a where 1=1 ").append(
						""); 
			return stb;
		}
		
		private StringBuilder doGetSQLforUNTBU_BUILDING(StringBuilder stb){
			stb.append("select ").append(
							" 'BU' as type, ").append(
							" '財產' as mode, ").append(
							" differencekind , ").append(
							" enterorg, ").append(
							" sourceDate, ").append(
		    				" propertyNo, ").append(
							" serialNo, ").append(
							" propertyName1, ").append(
							" null as specification, ").append(
							" null as nameplate, ").append(
							" '1' as amount, ").append(
							" bookvalue, ").append(		
							" place1, ").append(
							" keepunit, ").append(
							" keeper, ").append(
							" (select unitname from UNTMP_KEEPUNIT z where z.enterorg = a.enterorg and z.unitno = a.keepUnit) as keepUnitName,").append(
							" (select keepername from UNTMP_KEEPER z where z.enterorg = a.enterorg and z.keeperno = a.keeper) as keeperName,").append(
							" place, ").append(
							" signno, ").append(
							" a.otherpropertyunit, ").append(	
							" otherlimityear, ").append(
							" a.oldserialno as oldserialno ").append(			
		    			" from UNTBU_BUILDING a where 1=1 ").append(
		    			"");
			return stb;
		}
		
		private StringBuilder doGetSQLforUNTRF_ATTACHMENT(StringBuilder stb){
			stb.append("select ").append(
							" 'RF' as type, ").append(
							" '財產' as mode, ").append(
							" differencekind , ").append(
							" enterorg, ").append(
							" sourceDate, ").append(
		    				" propertyNo, ").append(
							" serialNo, ").append(
							" propertyName1, ").append(
							" null as specification, ").append(
							" null as nameplate, ").append(
							" '1' as amount, ").append(
							" bookvalue, ").append(		
							" place1, ").append(
							" keepunit, ").append(
							" keeper, ").append(													
							" (select unitname from UNTMP_KEEPUNIT z where z.enterorg = a.enterorg and z.unitno = a.keepUnit) as keepUnitName,").append(
							" (select keepername from UNTMP_KEEPER z where z.enterorg = a.enterorg and z.keeperno = a.keeper) as keeperName,").append(
							" place, ").append(
							" null as signno, ").append(
							" a.otherpropertyunit, ").append(
							" otherlimityear, ").append(
							" a.oldserialno as oldserialno ").append(			
		    			" from UNTRF_ATTACHMENT a where 1=1 ").append(
		    			"");
			return stb;
		}
		
		private StringBuilder doGetSQLforUNTMP_MOVABLEDETAIL(StringBuilder stb){
			stb.append("select ").append(
							" 'MP' as type, ").append(
							" '財產' as mode, ").append(
							" differencekind , ").append(
							" enterorg, ").append(
							"(select sourcedate from UNTMP_MOVABLE z where z.enterorg = a.enterorg and z.ownership = a.ownership and z.differencekind = a.differencekind and z.lotno = a.lotno and z.propertyno = a.propertyno) as sourceDate, ").append(
		    				" propertyNo, ").append(
							" serialNo, ").append(
							" a.propertyname1,").append(
							" (select specification from UNTMP_MOVABLE z where z.enterorg = a.enterorg and z.ownership = a.ownership and z.differencekind = a.differencekind and z.lotno = a.lotno and z.propertyno = a.propertyno) as specification, ").append(
							" (select nameplate from UNTMP_MOVABLE z where z.enterorg = a.enterorg and z.ownership = a.ownership and z.differencekind = a.differencekind and z.lotno = a.lotno and z.propertyno = a.propertyno) as nameplate, ").append(
							" bookamount as amount, ").append(
							" bookvalue, ").append(		
							" place1, ").append(
							" keepunit, ").append(
							" keeper, ").append(
							" (select unitname from UNTMP_KEEPUNIT z where z.enterorg = a.enterorg and z.unitno = a.keepUnit) as keepUnitName,").append(
							" (select keepername from UNTMP_KEEPER z where z.enterorg = a.enterorg and z.keeperno = a.keeper) as keeperName,").append(
							" place, ").append(
							" null as signno, ").append(
							" (select otherpropertyunit from UNTMP_MOVABLE z where z.enterorg = a.enterorg and z.ownership = a.ownership and z.differencekind = a.differencekind and z.lotno = a.lotno and z.propertyno = a.propertyno) as  otherpropertyunit, ").append(
							" a.otherlimityear, ").append(
							" a.oldserialno as oldserialno ").append(
		    			" from UNTMP_MOVABLEDETAIL a where 1=1").append(
		    			"");
			return stb;
		}
		
		private StringBuilder doGetSQLforUNTVP_ADDPROOF(StringBuilder stb){
			stb.append("select ").append(
							" 'VP' as type, ").append(
							" '財產' as mode, ").append(
							" differencekind , ").append(
							" enterorg, ").append(
							" sourceDate, ").append(
		    				" propertyNo, ").append(
							" serialNo, ").append(
							" propertyName1, ").append(
							" null as specification, ").append(
							" null as nameplate, ").append(
							" bookamount as amount, ").append(
							" bookvalue, ").append(		
							" place1, ").append(
							" keepunit, ").append(
							" keeper, ").append(	
							" (select unitname from UNTMP_KEEPUNIT z where z.enterorg = a.enterorg and z.unitno = a.keepUnit) as keepUnitName,").append(
							" (select keepername from UNTMP_KEEPER z where z.enterorg = a.enterorg and z.keeperno = a.keeper) as keeperName,").append(
							" place, ").append(	
							" null as signno, ").append(
							" a.otherpropertyunit, ").append(
							" null as otherlimityear, ").append(
							" a.oldserialno as oldserialno ").append(
		    			" from UNTVP_ADDPROOF a where 1=1 ").append(
		    			"");
			return stb;
		}
		
		private StringBuilder doGetSQLforUNTRT_ADDPROOF(StringBuilder stb){
			stb.append("select ").append(
							" 'RT' as type, ").append(
							" '財產' as mode, ").append(
							" differencekind , ").append(
							" enterorg, ").append(
							" sourceDate, ").append(
		    				" propertyNo, ").append(
							" serialNo, ").append(
							" null as propertyName1, ").append(
							" null as specification, ").append(
							" null as nameplate, ").append(
							" null as amount, ").append(
							" bookvalue, ").append(		
							" place1, ").append(
							" keepunit, ").append(
							" keeper, ").append(
							" (select unitname from UNTMP_KEEPUNIT z where z.enterorg = a.enterorg and z.unitno = a.keepUnit) as keepUnitName,").append(
							" (select keepername from UNTMP_KEEPER z where z.enterorg = a.enterorg and z.keeperno = a.keeper) as keeperName,").append(
							" place, ").append(
							" null as signno, ").append(
							" a.otherpropertyunit, ").append(
							" null as otherlimityear, ").append(
							" a.oldserialno as oldserialno ").append(
		    			" from UNTRT_ADDPROOF a where 1=1 ").append(
		    			"");
			return stb;
		}
		
		private StringBuilder doGetSQLforUNTNE_NONEXPDETAIL(StringBuilder stb){
			stb.append("select ").append(
							" 'NE' as type, ").append(
							" '物品' as mode, ").append(
							" a.differencekind , ").append(									
							" a.enterorg, ").append(
							" b.sourcedate, ").append(
		    				" a.propertyno, ").append(
							" a.serialno, ").append(
							" b.propertyname1, ").append(
							" b.specification, ").append(
							" b.nameplate, ").append(
							" a.bookamount as amount, ").append(
							" a.bookvalue, ").append(		
							" a.place1, ").append(
							" a.keepunit, ").append(
							" a.keeper, ").append(
							" (select unitname from UNTMP_KEEPUNIT z where z.enterorg = a.enterorg and z.unitno = a.keepUnit) as keepUnitName,").append(
							" (select keepername from UNTMP_KEEPER z where z.enterorg = a.enterorg and z.keeperno = a.keeper) as keeperName,").append(
							" a.place, ").append(	
							" null as signno, ").append(
							" b.otherpropertyunit, ").append(
							" a.otherlimityear, ").append(
							" a.oldserialno as oldserialno ").append(
		    			" from UNTNE_NONEXPDETAIL a, UNTNE_NONEXP b  where 1=1 and a.enterorg = b.enterorg and a.ownership = b.ownership and a.caseno = b.caseno and a.differencekind = b.differencekind and a.propertyno = b.propertyno and a.lotno = b.lotno  ").append(
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
	
	public String getQ_reportType() {	return q_reportType;}
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

	private String keeper;
	public String getKeeper() {return checkGet(keeper);}
	public void setKeeper(String keeper) {this.keeper = checkSet(keeper);}
}