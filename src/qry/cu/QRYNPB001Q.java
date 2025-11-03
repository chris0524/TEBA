package qry.cu;

import java.sql.ResultSet;

import util.Common;
import util.Database;
import util.Datetime;
import util.QueryBean;

public class QRYNPB001Q extends QueryBean {

	String q_queryType;
	String q_propertyType;
	String q_signNo1;
	String q_signNo2;
	String q_signNo3;
	String q_signNo4;
	String q_signNo5;
	String q_fakeDivision;
	String q_applyID;
	String q_applyName;

	public String getQ_queryType(){ return checkGet(q_queryType); }
	public void setQ_queryType(String s){ q_queryType=checkSet(s); }	
	public String getQ_propertyType(){ return checkGet(q_propertyType); }
	public void setQ_propertyType(String s){ q_propertyType=checkSet(s); }
	public String getQ_signNo1(){ return checkGet(q_signNo1); }
	public void setQ_signNo1(String s){ q_signNo1=checkSet(s); }
	public String getQ_signNo2(){ return checkGet(q_signNo2); }
	public void setQ_signNo2(String s){ q_signNo2=checkSet(s); }
	public String getQ_signNo3(){ return checkGet(q_signNo3); }
	public void setQ_signNo3(String s){ q_signNo3=checkSet(s); }
	public String getQ_signNo4(){ return checkGet(q_signNo4); }
	public void setQ_signNo4(String s){ q_signNo4=checkSet(s); }
	public String getQ_signNo5(){ return checkGet(q_signNo5); }
	public void setQ_signNo5(String s){ q_signNo5=checkSet(s); }
	public String getQ_fakeDivision(){ return checkGet(q_fakeDivision); }
	public void setQ_fakeDivision(String s){ q_fakeDivision=checkSet(s); }
	public String getQ_applyID(){ return checkGet(q_applyID); }
	public void setQ_applyID(String s){ q_applyID=checkSet(s); }
	public String getQ_applyName(){ return checkGet(q_applyName); }
	public void setQ_applyName(String s){ q_applyName=checkSet(s); }
	
	public String getPreSQL() {
		String preSQL = "";
		String q_signNo = "";
		if (!"".equals(getQ_propertyType()))
			preSQL+=" and substr(b.propertyNo,0,1) = " + Common.sqlChar(getQ_propertyType()) ;
		if (!"".equals(getQ_signNo1()))
			q_signNo=getQ_signNo1().substring(0,1)+"______";
		if (!"".equals(getQ_signNo2()))
			q_signNo=getQ_signNo2().substring(0,3)+"____";			
		if (!"".equals(getQ_signNo3())){
			if (getQ_signNo3().length()==4){
				q_signNo="E__" + getQ_signNo3();
			}else{
				q_signNo=getQ_signNo3();
			}	
		}
		if (!"".equals(getQ_signNo4())){
			if (getQ_propertyType().equals("1")) {
				setQ_signNo4(Common.formatFrontZero(getQ_signNo4(),4));
				setQ_signNo5(Common.formatFrontZero(getQ_signNo5(),4));
			} else {
				setQ_signNo4(Common.formatFrontZero(getQ_signNo4(),5));
				setQ_signNo5(Common.formatFrontZero(getQ_signNo5(),3));				
			}
			if ("".equals(q_signNo)){
				q_signNo="_______"+getQ_signNo4()+getQ_signNo5();
			}else{
				q_signNo=q_signNo+getQ_signNo4()+getQ_signNo5();				
			}
		}				
		if (!"".equals(q_signNo))
			preSQL+=" and d.signNo like '" + q_signNo + "%'" ;
		return preSQL;
	}
	
	public String getPreSQL1() {
		String preSQL1 = "";
		if (!"".equals(getQ_applyID()))
			preSQL1+=" and e.applyID = " + Common.sqlChar(getQ_applyID()) ;
		if (!"".equals(getQ_applyName()))
			preSQL1+=" and f.applyName like " + Common.sqlChar("%"+getQ_applyName()+"%") ;
		return preSQL1;
	}
	
	
	public String getDisplayList() {
		if ("queryAll".equals(getState())) {	
			try {
				if ("RE".equals(getQ_queryType())) return queryRECase();
				else if ("SA".equals(getQ_queryType())) return querySACase();
				else if ("IN".equals(getQ_queryType())) return queryINCase();				
			} catch (Exception e) {
				e.printStackTrace();
			}			
		}
		return "";
	}

	public String queryRECase() throws Exception{
		Database db = new Database();
		StringBuffer sbHTML = new StringBuffer("");
		int counter=0;
		try {		
			String sql=" select distinct a.caseNo, a.caseState, c.codeName as rentType, a.applyDate, a.holdDateS, a.holdDateE, contractDate, contractDoc "+
				" from NPBRE_Case a, SYSCA_Code c "; 			
				if (getPreSQL().length()>1 && getPreSQL1().length()>1) {		
					sql += ", NPBRE_Property b ";
					if (getQ_propertyType().equals("1")) {				
						sql += ", UNTLA_Land d ";
					} else {
						sql += ", UNTBU_Building d ";
					}
					sql+=", NPBRE_Person e, NPBGR_Person f where a.caseNo=b.caseNo and a.caseNo=e.caseNo and b.enterOrg=d.enterOrg " +
					" and b.ownership=d.ownership and b.propertyNo=d.propertyNo " +
					" and b.serialNo=d.serialNo and d.ownership='1' and e.applyID=f.applyID " + getPreSQL() + getPreSQL1();
				} else if (getPreSQL().length()>1) {		
					sql += ", NPBRE_Property b ";
					if (getQ_propertyType().equals("1")) {				
						sql += ", UNTLA_Land d ";
					} else {
						sql += ", UNTBU_Building d ";
					}
					sql+=" where a.caseNo=b.caseNo and b.enterOrg=d.enterOrg" +
					" and b.ownership=d.ownership and b.propertyNo=d.propertyNo " +
					" and b.serialNo=d.serialNo and d.ownership='1' " + getPreSQL();
				} else if (getPreSQL1().length()>1) {
					sql+=", NPBRE_Person e, NPBGR_Person f where a.caseNo=e.caseNo and e.applyID=f.applyID " + getPreSQL1();				
				} else {
					sql+=" where 1=1 ";
				}					
				sql+=" and  c.codeKindID='RNT' " +
					" and c.codeID=a.rentType ";
				ResultSet rs = db.querySQL(sql+" order by caseNo");				
			
			sbHTML.append("<table class=\"table_form\" width=\"100%\" cellspacing=\"0\" cellpadding=\"0\">\n");
			sbHTML.append("  <thead id=\"listTHEAD\">\n");
			sbHTML.append("    <tr>\n");
			sbHTML.append("		<th class=\"listTH\" ><a class=\"text_link_w\" >NO.</a></th>\n");
			sbHTML.append("		<th class=\"listTH\"><a class=\"text_link_w\" onClick=\"return sortTable('listTBODY',1,false);\" href=\"#\">案號</a></th>\n");
			sbHTML.append("		<th class=\"listTH\"><a class=\"text_link_w\" onClick=\"return sortTable('listTBODY',2,false);\" href=\"#\">作業階段</a></th>\n");
			sbHTML.append("		<th class=\"listTH\"><a class=\"text_link_w\" onClick=\"return sortTable('listTBODY',3,false);\" href=\"#\">出租原因</a></th>\n");
			sbHTML.append("		<th class=\"listTH\"><a class=\"text_link_w\" onClick=\"return sortTable('listTBODY',4,false);\" href=\"#\">申請局收文日期</a></th>\n");
			sbHTML.append("		<th class=\"listTH\"><a class=\"text_link_w\" onClick=\"return sortTable('listTBODY',5,false);\" href=\"#\">租賃期間起</a></th>\n");
			sbHTML.append("		<th class=\"listTH\"><a class=\"text_link_w\" onClick=\"return sortTable('listTBODY',6,false);\" href=\"#\">租賃期間訖</a></th>\n");
			sbHTML.append("	</thead>\n");
			sbHTML.append("	<tbody id=\"listTBODY\">\n");

			while (rs.next()){
				counter++;
				sbHTML.append(" <tr class='listTR'>");
				sbHTML.append(" <td class='listTD'>").append(counter).append(".</td> ");
				sbHTML.append(" <td class='listTD'>").append(Common.checkGet(rs.getString("caseNo"))).append("</td> ");
				if (Common.get(rs.getString("caseState")).equals("20")) {
					if (Common.get(rs.getString("contractDate")).length()==7) 
						sbHTML.append(" <td class='listTD'>").append("案件審核通過-同意出租,己定約").append("</td> ");
					else
						sbHTML.append(" <td class='listTD'>").append("案件審核通過-同意出租,尚未定約").append("</td> ");
				} else {
					sbHTML.append(" <td class='listTD'>").append(getRECaseStateName(Common.checkGet(rs.getString("caseState")))).append("</td> ");					
				}
				sbHTML.append(" <td class='listTD'>").append(Common.checkGet(rs.getString("rentType"))).append("</td> ");
				sbHTML.append(" <td class='listTD'>").append(Common.checkGet(rs.getString("applyDate"))).append("</td> ");
				sbHTML.append(" <td class='listTD'>").append(Common.checkGet(rs.getString("holdDateS"))).append("</td> ");
				sbHTML.append(" <td class='listTD'>").append(Common.checkGet(rs.getString("holdDateE"))).append("</td> ");				
				sbHTML.append(" </tr> ");				
				if (counter==50){
					setErrorMsg(getListLimitError());
					break;
				}
			}
			if (counter==0) sbHTML.append(" <tr class='listTR' ><td class='listTD' colspan='100' style='color:red'>查無資料，請您重新輸入查詢條件！</td></tr>");
			sbHTML.append("	</tbody>\n");
			sbHTML.append("</table>\n");			
			setStateQueryAllSuccess();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.closeAll();
		}
		return sbHTML.toString();
	}	
	
	
	public String querySACase() throws Exception{
		Database db = new Database();
		StringBuffer sbHTML = new StringBuffer("");
		int counter=0;
		try {				
			String sql=" select distinct a.caseNo, decode(a.caseState,'01','案件處理中','02','審核中-房地處分中','03','審核中-鑑價處理中','04','審核中-財審會審議中','05','審核中-第一次降價','06','審核中-第二次降價','09','審核中-房地處分完成','10','審核未通過-案件審核失敗','11','未脫標結案','20','案件審核通過','30','審核通過-繳款中','99','完成出售','') as caseState, decode(a.saleType,'1','標售','2','讓售-佔用','3','讓售-租用','4','讓售-畸零地','5','曾屬新草衙','6','新草衙專案讓售','') saleType, a.bulletinDate, a.applyDate, a.applyDoc, decode(a.appraisalUnit,'1','局內','2','委外','') appraisalUnit "+
				" from NPBSA_Case a "; 
			
				if (getPreSQL().length()>1 && getPreSQL1().length()>1) {		
					sql += ", NPBSA_Property b ";
					if (getQ_propertyType().equals("1")) {				
						sql += ", UNTLA_Land d ";
					} else {
						sql += ", UNTBU_Building d ";
					}
					sql+=", NPBSA_Person e, NPBGR_Person f where a.caseNo=b.caseNo and a.caseNo=e.caseNo and b.enterOrg=d.enterOrg " +
					" and b.ownership=d.ownership and b.propertyNo=d.propertyNo " +
					" and b.serialNo=d.serialNo and d.ownership='1' and e.applyID=f.applyID " + getPreSQL() + getPreSQL1();
				} else if (getPreSQL().length()>1) {		
					sql += ", NPBSA_Property b ";
					if (getQ_propertyType().equals("1")) {				
						sql += ", UNTLA_Land d ";
					} else {
						sql += ", UNTBU_Building d ";
					}
					sql+=" where a.caseNo=b.caseNo and b.enterOrg=d.enterOrg" +
					" and b.ownership=d.ownership and b.propertyNo=d.propertyNo " +
					" and b.serialNo=d.serialNo and d.ownership='1' " + getPreSQL();
				} else if (getPreSQL1().length()>1) {
					sql+=", NPBSA_Person e, NPBGR_Person f where a.caseNo=e.caseNo and e.applyID=f.applyID " + getPreSQL1();				
				} else {
					sql+=" where 1=1 ";
				}
				ResultSet rs = db.querySQL(sql+" order by a.caseNo");				
			
			sbHTML.append("<table class=\"table_form\" width=\"100%\" cellspacing=\"0\" cellpadding=\"0\">\n");
			sbHTML.append("  <thead id=\"listTHEAD\">\n");
			sbHTML.append("    <tr>\n");
			sbHTML.append("		<th class=\"listTH\" ><a class=\"text_link_w\" >NO.</a></th>\n");
			sbHTML.append("		<th class=\"listTH\"><a class=\"text_link_w\" onClick=\"return sortTable('listTBODY',1,false);\" href=\"#\">案號</a></th>\n");
			sbHTML.append("		<th class=\"listTH\"><a class=\"text_link_w\" onClick=\"return sortTable('listTBODY',2,false);\" href=\"#\">作業階段</a></th>\n");
			sbHTML.append("		<th class=\"listTH\"><a class=\"text_link_w\" onClick=\"return sortTable('listTBODY',3,false);\" href=\"#\">出售原因</a></th>\n");
			sbHTML.append("		<th class=\"listTH\"><a class=\"text_link_w\" onClick=\"return sortTable('listTBODY',4,false);\" href=\"#\">公告現值年月</a></th>\n");
			sbHTML.append("		<th class=\"listTH\"><a class=\"text_link_w\" onClick=\"return sortTable('listTBODY',5,false);\" href=\"#\">申購局收文文號</a></th>\n");
			sbHTML.append("		<th class=\"listTH\"><a class=\"text_link_w\" onClick=\"return sortTable('listTBODY',6,false);\" href=\"#\">鑑價單位</a></th>\n");
			sbHTML.append("	</thead>\n");
			sbHTML.append("	<tbody id=\"listTBODY\">\n");

			while (rs.next()){
				counter++;
				sbHTML.append(" <tr class='listTR'>");
				sbHTML.append(" <td class='listTD'>").append(counter).append(".</td> ");
				sbHTML.append(" <td class='listTD'>").append(Common.checkGet(rs.getString("caseNo"))).append("</td> ");
				sbHTML.append(" <td class='listTD'>").append(Common.checkGet(rs.getString("caseState"))).append("</td> ");					
				sbHTML.append(" <td class='listTD'>").append(Common.checkGet(rs.getString("saleType"))).append("</td> ");
				sbHTML.append(" <td class='listTD'>").append(Common.checkGet(rs.getString("bulletinDate"))).append("</td> ");
				sbHTML.append(" <td class='listTD'>").append(Common.checkGet(rs.getString("applyDate"))).append("-").append(Common.get(rs.getString("applyDoc"))).append("</td> ");
				sbHTML.append(" <td class='listTD'>").append(Common.checkGet(rs.getString("appraisalUnit"))).append("</td> ");				
				sbHTML.append(" </tr> ");				
				if (counter==50){
					setErrorMsg(getListLimitError());
					break;
				}
			}
			if (counter==0) {
				return queryDACase();
				//sbHTML.append(" <tr class='listTR' ><td class='listTD' colspan='100' style='color:red'>查無資料，請您重新輸入查詢條件！</td></tr>");
			}
			sbHTML.append("	</tbody>\n");
			sbHTML.append("</table>\n");			
			setStateQueryAllSuccess();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.closeAll();
		}
		return sbHTML.toString();
	}	
	
	public String queryDACase() throws Exception{
		Database db = new Database();
		StringBuffer sbHTML = new StringBuffer("");
		int counter=0;
		try {				
			String sql=" select distinct a.caseNo, decode(a.caseState,'1','挑選未審查中','2','送市政會審查中','3','市政會審查通過','4','送市議會審查中','5','市議會審查通過','6','送內政部審查中','7','內政部審查通過','8','市政會審查不通過結案','9','市議會審查不通過結案','10','內政部審查不通過結案','') as caseState, a.proceedType, a.townDate, a.councilDate, a.interApprove, a.interDate, a.deadLine "+
				" from NPBDA_Case a, SYSCA_CODE x "; 			
				if (getPreSQL().length()>1) {		
					sql += ", NPBDA_Property b ";
					if (getQ_propertyType().equals("1")) {				
						sql += ", UNTLA_Land d ";
					} else {
						sql += ", UNTBU_Building d ";
					}
					sql+=" where x.codeKindID='PRO' and a.proceedType=x.codeID and a.caseNo=b.caseNo and b.enterOrg=d.enterOrg" +
					" and b.ownership=d.ownership and b.propertyNo=d.propertyNo " +
					" and b.serialNo=d.serialNo and d.ownership='1' " + getPreSQL();
				} else {
					sql+=" where x.codeKindID='PRO' and a.proceedType=x.codeID ";
				}
				ResultSet rs = db.querySQL(sql+" order by a.caseNo");				
			
			sbHTML.append("<table class=\"table_form\" width=\"100%\" cellspacing=\"0\" cellpadding=\"0\">\n");
			sbHTML.append("  <thead id=\"listTHEAD\">\n");
			sbHTML.append("    <tr>\n");
			sbHTML.append("		<th class=\"listTH\" ><a class=\"text_link_w\" >NO.</a></th>\n");
			sbHTML.append("		<th class=\"listTH\"><a class=\"text_link_w\" onClick=\"return sortTable('listTBODY',1,false);\" href=\"#\">案號</a></th>\n");
			sbHTML.append("		<th class=\"listTH\"><a class=\"text_link_w\" onClick=\"return sortTable('listTBODY',2,false);\" href=\"#\">作業階段</a></th>\n");
			sbHTML.append("		<th class=\"listTH\"><a class=\"text_link_w\" onClick=\"return sortTable('listTBODY',3,false);\" href=\"#\">出售原因</a></th>\n");
			sbHTML.append("		<th class=\"listTH\"><a class=\"text_link_w\" onClick=\"return sortTable('listTBODY',4,false);\" href=\"#\">市政會議日期</a></th>\n");
			sbHTML.append("		<th class=\"listTH\"><a class=\"text_link_w\" onClick=\"return sortTable('listTBODY',5,false);\" href=\"#\">市議會議日期</a></th>\n");
			sbHTML.append("		<th class=\"listTH\"><a class=\"text_link_w\" onClick=\"return sortTable('listTBODY',6,false);\" href=\"#\">內政部回覆日期</a></th>\n");
			sbHTML.append("		<th class=\"listTH\"><a class=\"text_link_w\" onClick=\"return sortTable('listTBODY',7,false);\" href=\"#\">處分年限</a></th>\n");
			sbHTML.append("		<th class=\"listTH\"><a class=\"text_link_w\" onClick=\"return sortTable('listTBODY',8,false);\" href=\"#\">處分屆滿日</a></th>\n");			
			sbHTML.append("	</thead>\n");
			sbHTML.append("	<tbody id=\"listTBODY\">\n");

			while (rs.next()){
				counter++;
				sbHTML.append(" <tr class='listTR'>");
				sbHTML.append(" <td class='listTD'>").append(counter).append(".</td> ");
				sbHTML.append(" <td class='listTD'>").append(Common.checkGet(rs.getString("caseNo"))).append("</td> ");
				sbHTML.append(" <td class='listTD'>").append(Common.checkGet(rs.getString("caseState"))).append("</td> ");					
				sbHTML.append(" <td class='listTD'>").append(Common.checkGet(rs.getString("proceedType"))).append("</td> ");
				sbHTML.append(" <td class='listTD'>").append(Common.checkGet(rs.getString("townDate"))).append("</td> ");
				sbHTML.append(" <td class='listTD'>").append(Common.checkGet(rs.getString("councilDate"))).append("</td> ");
				sbHTML.append(" <td class='listTD'>").append(Common.checkGet(rs.getString("deadLine"))).append("</td> ");
				if ("Y".equals(rs.getString("interApprove"))) {
					sbHTML.append(" <td class='listTD'>").append(Datetime.getDateAdd("y",Integer.parseInt(rs.getString("deadLine")),Datetime.getDateAdd("d",-1,rs.getString("interDate")))).append("</td> ");
				} else {
					sbHTML.append(" <td class='listTD'></td> ");
				}				
				sbHTML.append(" </tr> ");				
				if (counter==50){
					setErrorMsg(getListLimitError());
					break;
				}
			}
			if (counter==0) sbHTML.append(" <tr class='listTR' ><td class='listTD' colspan='100' style='color:red'>查無資料，請您重新輸入查詢條件！</td></tr>");
			sbHTML.append("	</tbody>\n");
			sbHTML.append("</table>\n");			
			setStateQueryAllSuccess();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.closeAll();
		}
		return sbHTML.toString();
	}		
	
	
	public String queryINCase() throws Exception{
		Database db = new Database();
		StringBuffer sbHTML = new StringBuffer("");
		int counter=0;
		try {		
			String sql=" select distinct a.caseNo, decode(a.levyType,'1','租金分期','2','補償金分期','3','地價款分期','') as levyType, a.caseState, a.applyDate, a.approveDate, a.contractDate "+
				" from NPBIN_Case a "; 			
				if (getPreSQL().length()>1 && getPreSQL1().length()>1) {		
					sql += ", NPBIN_Property b ";
					if (getQ_propertyType().equals("1")) {				
						sql += ", UNTLA_Land d ";
					} else {
						sql += ", UNTBU_Building d ";
					}
					sql+=", NPBIN_Person e, NPBGR_Person f where a.caseNo=b.caseNo and a.caseNo=e.caseNo and b.enterOrg=d.enterOrg " +
					" and b.ownership=d.ownership and b.propertyNo=d.propertyNo " +
					" and b.serialNo=d.serialNo and d.ownership='1' and e.applyID=f.applyID " + getPreSQL() + getPreSQL1();
				} else if (getPreSQL().length()>1) {		
					sql += ", NPBIN_Property b ";
					if (getQ_propertyType().equals("1")) {				
						sql += ", UNTLA_Land d ";
					} else {
						sql += ", UNTBU_Building d ";
					}
					sql+=" where a.caseNo=b.caseNo and b.enterOrg=d.enterOrg" +
					" and b.ownership=d.ownership and b.propertyNo=d.propertyNo " +
					" and b.serialNo=d.serialNo and d.ownership='1' " + getPreSQL();
				} else if (getPreSQL1().length()>1) {
					sql+=", NPBIN_Person e, NPBGR_Person f where a.caseNo=e.caseNo and e.applyID=f.applyID " + getPreSQL1();				
				} else {
					sql+=" where 1=1 ";
				}
				ResultSet rs = db.querySQL(sql+" order by a.caseNo");
			
			sbHTML.append("<table class=\"table_form\" width=\"100%\" cellspacing=\"0\" cellpadding=\"0\">\n");
			sbHTML.append("  <thead id=\"listTHEAD\">\n");
			sbHTML.append("    <tr>\n");
			sbHTML.append("		<th class=\"listTH\" ><a class=\"text_link_w\" >NO.</a></th>\n");
			sbHTML.append("		<th class=\"listTH\"><a class=\"text_link_w\" onClick=\"return sortTable('listTBODY',1,false);\" href=\"#\">案號</a></th>\n");
			sbHTML.append("		<th class=\"listTH\"><a class=\"text_link_w\" onClick=\"return sortTable('listTBODY',2,false);\" href=\"#\">作業階段</a></th>\n");
			sbHTML.append("		<th class=\"listTH\"><a class=\"text_link_w\" onClick=\"return sortTable('listTBODY',3,false);\" href=\"#\">分期類別</a></th>\n");
			sbHTML.append("		<th class=\"listTH\"><a class=\"text_link_w\" onClick=\"return sortTable('listTBODY',4,false);\" href=\"#\">申請局收文日期</a></th>\n");
			sbHTML.append("		<th class=\"listTH\"><a class=\"text_link_w\" onClick=\"return sortTable('listTBODY',6,false);\" href=\"#\">核准通知函日期</a></th>\n");
			sbHTML.append("	</thead>\n");
			sbHTML.append("	<tbody id=\"listTBODY\">\n");

			while (rs.next()){
				counter++;
				sbHTML.append(" <tr class='listTR'>");
				sbHTML.append(" <td class='listTD'>").append(counter).append(".</td> ");
				sbHTML.append(" <td class='listTD'>").append(Common.checkGet(rs.getString("caseNo"))).append("</td> ");
				if (Common.get(rs.getString("caseState")).equals("30")) {
					if (Common.checkGet(rs.getString("contractDate")).length()==7) 
						sbHTML.append(" <td class='listTD'>").append("審核通過-頭期款繳款中,己定約").append("</td> ");
					else
						sbHTML.append(" <td class='listTD'>").append("審核通過-頭期款繳款中,尚未定約").append("</td> ");
				} else {
					sbHTML.append(" <td class='listTD'>").append(getINCaseStateName(Common.checkGet(rs.getString("caseState")))).append("</td> ");					
				}
				sbHTML.append(" <td class='listTD'>").append(Common.checkGet(rs.getString("levyType"))).append("</td> ");
				sbHTML.append(" <td class='listTD'>").append(Common.checkGet(rs.getString("applyDate"))).append("</td> ");
				sbHTML.append(" <td class='listTD'>").append(Common.checkGet(rs.getString("approveDate"))).append("</td> ");				
				sbHTML.append(" </tr> ");				
				if (counter==50){
					setErrorMsg(getListLimitError());
					break;
				}
			}
			if (counter==0) sbHTML.append(" <tr class='listTR' ><td class='listTD' colspan='100' style='color:red'>查無資料，請您重新輸入查詢條件！</td></tr>");
			sbHTML.append("	</tbody>\n");
			sbHTML.append("</table>\n");			
			setStateQueryAllSuccess();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.closeAll();
		}
		return sbHTML.toString();
	}	
	
	public String getRECaseStateName(String s){ 
		String rtnStr="";
		if ("01".equals(s)){
			rtnStr = "案件處理中";
		}else if ("10".equals(s)){
			rtnStr = "案件審核不通過";
		}else if ("11".equals(s)){
			rtnStr = "未脫標結案";		
		}else if ("19".equals(s)){
			rtnStr = "終止合約";
		}else if ("20".equals(s)){
			rtnStr = "案件審核通過";
		}else if ("30".equals(s)){
			rtnStr = "案件繳款中";
		}else if ("99".equals(s)){
			rtnStr = "案件處理完成";
		}
		return rtnStr;
	}	
	
	public String getDACaseStateName(String s) {
		String[] arrCode = new String[]{"1","2","3","4","5","6","7","8","9","10"};
		String[] arrName = new String[]{"挑選未審查中","送市政會審查中","市政會審查通過","送市議會審查中","市議會審查通過",
				"送內政部審查中","內政部審查通過","市政會審查不通過結案","市議會審查不通過結案","內政部審查不通過結案"};
		for (int i=0; i<arrCode.length; i++){
			if (util.Common.get(s).equals(arrCode[i])) return arrName[i];
		}
		return "";		
	}
	
	public String getINCaseStateName(String s) {
		String rStr = "";
		String[] arrStr = {"","01","10","20","30","31","99"};
		String[] arrStrName = {"","案件處理中","案件審核失敗-不同意分期","案件審核通過","繳款中","審核通過-分期款繳款中","完成"};
		for (int i=0; i<arrStr.length; i++){
			if (util.Common.get(s).equals(arrStr[i])) rStr = arrStrName[i];
		}		
		return rStr;
	}	
	
}
