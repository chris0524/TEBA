/*
*<br>程式目的： 
*<br>程式代號：untbu016r
*<br>撰寫日期：94/12/12
*<br>程式作者：Chris
*<br>--------------------------------------------------------
*<br>修改作者　　修改日期　　　修改目的
*<br>--------------------------------------------------------
*/

package unt.dp;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import javax.swing.table.DefaultTableModel;

import unt.ch.UNTCH_Tools;
import util.Common;
import util.Database;
import util.Datetime;
import TDlib_Simple.tools.src.StringTools;

public class UNTDP016R extends UNTDP001F{

	String enterOrg;
	String organaName;
	String deprUnitName;
	String deprUnit;
	String deprUnitCB;
	String deprPark;
	String q_enterOrg;
	String q_deprYM;
	String q_differenceKind;
	String q_deprPark;
	String q_deprUnit;
	String q_deprUnit1;
	String q_page;
	private String q_reportType;						// 報表類型
	
	public String getQ_reportType() { return checkGet(q_reportType); }
	public void setQ_reportType(String q_reportType) { this.q_reportType = checkGet(q_reportType); }
	public String getEnterOrg(){ return checkGet(enterOrg); }
	public void setEnterOrg(String s){ enterOrg=checkSet(s); }
	public String getOrganaName(){ return checkGet(organaName); }
	public void setOrganaName(String s){ organaName=checkSet(s); }
	public String getDeprUnitName(){ return checkGet(deprUnitName); }
	public void setDeprUnitName(String s){ deprUnitName=checkSet(s); }
	public String getDeprUnit(){ return checkGet(deprUnit); }
	public void setDeprUnit(String s){ deprUnit=checkSet(s); }
	public String getDeprUnitCB(){ return checkGet(deprUnitCB); }
	public void setDeprUnitCB(String s){ deprUnitCB=checkSet(s); }
	public String getDeprPark(){ return checkGet(deprPark); }
	public void setDeprPark(String s){ deprPark=checkSet(s); }
	public String getQ_enterOrg(){ return checkGet(q_enterOrg); }
	public void setQ_enterOrg(String s){ q_enterOrg=checkSet(s); }
	public String getQ_deprYM(){ return checkGet(q_deprYM); }
	public void setQ_deprYM(String s){ q_deprYM=checkSet(s); }
	public String getQ_differenceKind(){ return checkGet(q_differenceKind); }
	public void setQ_differenceKind(String s){ q_differenceKind=checkSet(s); }
	public String getQ_deprPark(){ return checkGet(q_deprPark); }
	public void setQ_deprPark(String s){ q_deprPark=checkSet(s); }
	public String getQ_deprUnit(){ return checkGet(q_deprUnit); }
	public void setQ_deprUnit(String s){ q_deprUnit=checkSet(s); }
	public String getQ_deprUnit1(){ return checkGet(q_deprUnit1); }
	public void setQ_deprUnit1(String s){ q_deprUnit1=checkSet(s); }
	public String getQ_page(){ return checkGet(q_page); }
	public void setQ_page(String s){ q_page=checkSet(s); }
	
	String isOrganManager;
	String isAdminManager;
	String organID;
	private String editID;
	public String getOrganID() { return checkGet(organID); }
	public void setOrganID(String s) { organID = checkSet(s); }
	public String getIsOrganManager() { return checkGet(isOrganManager); }
	public void setIsOrganManager(String s) { isOrganManager = checkSet(s); }
	public String getIsAdminManager() { return checkGet(isAdminManager); }
	public void setIsAdminManager(String s) { isAdminManager = checkSet(s); } 
	public String getEditID() {return checkGet(editID);}
	public void setEditID(String editID) {this.editID = checkSet(editID);}   


	
public DefaultTableModel getResultModel() throws Exception{
	
	UNTCH_Tools ut = new UNTCH_Tools();
	DefaultTableModel model = new javax.swing.table.DefaultTableModel();
	Map<String,String> notes = new HashMap<String,String>();
	Database db = new Database();
	try{
		String note_sql= "select b.deprunitname,c.deprunit1name,a.notes from SYSCA_DEPRUNITCOMPARISON a"+
				" left join SYSCA_DEPRUNIT b on a.deprunitno = b.deprunitno and a.enterorg = b.enterorg"+
				" left join SYSCA_DEPRUNIT1 c on a.deprunit1no = c.deprunit1no and a.enterorg = c.enterorg"+
				" where  a.notes is NOT NULL";
		ResultSet note_rs = db.querySQL(note_sql);
		while(note_rs.next()){
			notes.put(note_rs.getString("deprunitname")+"_"+note_rs.getString("deprunit1name"), note_rs.getString("notes"));
		}
		
	}catch(Exception e){
		System.out.println(e);
	}
	
	StringTools st = new StringTools();
	String execSQL="";
	try{
		String[] columns = new String[] {"head01","head02","head03","head04","head05","head06","detail01","detail02","detail03","detail04",
				"detail05","differencekind","sum", "zhSum","detail07","detail08","no","onlyYM","note","no2","detail09","detail10","note1" ,"note2"};
		
		String getIfCondition = doGetIfCondition();
		
		if(!"".equals(getIfCondition)){
			//南科管局
			if(getQ_enterOrg().equals("A42030000G")){
					execSQL = " select * from (";
					execSQL += " select '1' as yeartype,a.enterorg,a.deprym,a.differencekind,a.deprpark,a.deprunit,";
					if ("1".equals(getQ_page())) { // 部門別
						execSQL += " a.depraccounts,";
					} else if ("2".equals(getQ_page()) || "3".equals(getQ_page()) || "4".equals(getQ_page())) { // 園區別+部門別+部門單位+會計科目 or 園區別+部門別 or 園區別
						execSQL += " a.deprunit1,a.depraccounts,";						
					} 
					execSQL += " SUM(CASE a.deprunitcb WHEN 'N' THEN a.monthDepr1 ELSE scaledmonthdepr1 END) monthdepr,"+
							" (case a.propertytype when '2' then '5111 土地改良物折舊' when '3' then '5121 一般房屋折舊' when '4' then '5131 機械及設備折舊' when '5' then '5141 交通及運輸折舊' when '6' then '5151 雜項設備折舊' when '8' then '5998 其他攤銷費用' else '' end) as propertytype,"+
							" (case a.propertytype when '2' then '130203 累計折舊－土地改良物' when '3' then '130303 累計折舊－房屋建築' when '4' then '130403 累計折舊－機械設備' when '5' then '130503 累計折舊－交通及運輸' when '6' then '130603 累計折舊－其他設備' when '8' then '170101-1001 專利權' else '' end) as propertytype1,";
					if ("1".equals(getQ_page())) { // 部門別
						execSQL += " k.deprparkname, z.deprunitname, x.depraccountsname";
					} else if ("2".equals(getQ_page()) || "3".equals(getQ_page()) || "4".equals(getQ_page()) ) { // 園區別+部門別+部門單位+會計科目 or 園區別+部門別 or 園區別
						execSQL += " k.deprparkname, z.deprunitname, y.deprunit1name, x.depraccountsname";
					}
					execSQL += " from UNTDP_MONTHDEPR a "+
							" left join SYSCA_DEPRPARK k on a.enterorg = k.enterorg and a.deprpark=k.deprparkno " +
							" left join SYSCA_DEPRUNIT z on a.enterorg = z.enterorg and a.deprunit=z.deprunitno " +
							" left join SYSCA_DEPRUNIT1 y on a.enterorg = y.enterorg and a.deprunit1=y.deprunit1no " +
							" left join SYSCA_DEPRACCOUNTS x on a.enterorg = x.enterorg and a.depraccounts=x.depraccountsno"+
							" where 1=1 and a.monthdepr1 <>0 "+	getIfCondition;
					if ("1".equals(getQ_page())) { // 部門別
						execSQL += " GROUP BY a.enterorg, a.deprym, a.differencekind, a.deprpark, a.deprunit, a.depraccounts, x.depraccountsname, a.propertytype, k.deprparkname, z.deprunitname ";
					} else if ("2".equals(getQ_page()) || "3".equals(getQ_page())  || "4".equals(getQ_page()) ) { // 園區別+部門別+部門單位+會計科目 or 園區別+部門別  or 園區別
						execSQL += " GROUP BY a.enterorg, a.deprym, a.differencekind, a.deprpark, a.deprunit, a.deprunit1, a.depraccounts, x.depraccountsname, a.propertytype, k.deprparkname, z.deprunitname, y.deprunit1name ";
					}
					
					execSQL +=" union ";
					execSQL +=" select '2' as yeartype,a.enterorg,a.deprym,a.differencekind,a.deprpark,a.deprunit,";
					if ("1".equals(getQ_page())) { // 部門別
						execSQL += " a.depraccounts,";
					} else if ("2".equals(getQ_page()) || "3".equals(getQ_page())  || "4".equals(getQ_page()) ) { // 園區別+部門別+部門單位+會計科目 or 園區別+部門別 or 園區別
						execSQL += " a.deprunit1,a.depraccounts,";
					}
					execSQL += " SUM(CASE a.deprunitcb WHEN 'N' THEN a.monthDepr2 ELSE scaledmonthdepr2 END) monthdepr,"+
							" (case a.propertytype when '2' then '5111 土地改良物折舊' when '3' then '5121 一般房屋折舊' when '4' then '5131 機械及設備折舊' when '5' then '5141 交通及運輸折舊' when '6' then '5151 雜項設備折舊' when '8' then '5998 其他攤銷費用' else '' end) as propertytype,"+
							" (case a.propertytype when '2' then '130203 累計折舊－土地改良物' when '3' then '130303 累計折舊－房屋建築' when '4' then '130403 累計折舊－機械設備' when '5' then '130503 累計折舊－交通及運輸' when '6' then '130603 累計折舊－其他設備' when '8' then '170101-1001 專利權' else '' end) as propertytype1,";
					if ("1".equals(getQ_page())) { // 部門別
						execSQL += " k.deprparkname, z.deprunitname, x.depraccountsname";
					} else if ("2".equals(getQ_page()) || "3".equals(getQ_page())  || "4".equals(getQ_page()) ) { // 園區別+部門別+部門單位+會計科目 or 園區別+部門別 or 園區別
						execSQL += " k.deprparkname, z.deprunitname, y.deprunit1name, x.depraccountsname";
					}
					execSQL += " from UNTDP_MONTHDEPR a "+
							" left join SYSCA_DEPRPARK k on a.enterorg = k.enterorg and a.deprpark=k.deprparkno " +
							" left join SYSCA_DEPRUNIT z on a.enterorg = z.enterorg and a.deprunit=z.deprunitno " +
							" left join SYSCA_DEPRUNIT1 y on a.enterorg = y.enterorg and a.deprunit1=y.deprunit1no " +
							" left join SYSCA_DEPRACCOUNTS x on a.enterorg = x.enterorg and a.depraccounts=x.depraccountsno"+
							" where 1=1 and a.monthdepr2 <>0 "+	getIfCondition;
					if ("1".equals(getQ_page())) { // 部門別
						execSQL += " GROUP BY a.enterorg, a.deprym, a.differencekind, a.deprpark, a.deprunit, a.depraccounts, x.depraccountsname, a.propertytype, k.deprparkname, z.deprunitname ) b ";
						execSQL += " order by b.enterorg, b.deprym, b.differencekind, b.deprunit, b.propertytype, b.depraccounts, b.yeartype";
					} else if ("2".equals(getQ_page()) || "3".equals(getQ_page())  || "4".equals(getQ_page()) ) { // 園區別+部門別+部門單位+會計科目 or 園區別+部門別 or 園區別
						execSQL += " GROUP BY a.enterorg, a.deprym, a.differencekind, a.deprpark, a.deprunit, a.deprunit1, a.depraccounts, x.depraccountsname, a.propertytype, k.deprparkname, z.deprunitname, y.deprunit1name ) b ";
						if ("2".equals(getQ_page())) { // 園區別+部門別+部門單位+會計科目
							execSQL += " order by b.enterorg, b.deprym, b.differencekind, b.deprpark, b.deprunit, b.deprunit1, b.propertytype, b.depraccounts, b.yeartype";
						}else if ("3".equals(getQ_page()) || "4".equals(getQ_page()) ) { // 園區別+部門別  or 園區別
							execSQL += " order by b.enterorg, b.deprym, b.differencekind, b.deprpark, b.deprunit, b.propertytype, b.yeartype";
						}
						
					}
			}
			//其他
			else{
					execSQL=" select '0' as yeartype,a.enterorg,a.deprym,a.differencekind,a.deprpark,a.deprunit,a.depraccounts,";
					
					if ("2".equals(this.getQ_page())) { // 園區別+部門別+部門單位+會計科目 
						execSQL += "a.deprunit1,";
					}
					execSQL += " SUM(CASE a.deprunitcb WHEN 'N' THEN a.monthdepr ELSE scaledmonthdepr END) monthdepr,"+
							" (case a.propertytype when '2' then '5111 土地改良物折舊' when '3' then '5121 一般房屋折舊' when '4' then '5131 機械及設備折舊' when '5' then '5141 交通及運輸折舊' when '6' then '5151 雜項設備折舊'  when '8' then '5998 其他攤銷費用' else '' end) as propertytype,"+
							" (case a.propertytype when '2' then '130203 累計折舊－土地改良物' when '3' then '130303 累計折舊－房屋建築' when '4' then '130403 累計折舊－機械設備' when '5' then '130503 累計折舊－交通及運輸' when '6' then '130603 累計折舊－其他設備'  when '8' then '170101-1001 專利權' else '' end) as propertytype1,";
					
					execSQL += " k.deprparkname, z.deprunitname, x.depraccountsname ";
					if ("2".equals(this.getQ_page())) { // 園區別+部門別+部門單位+會計科目
						execSQL += ",y.deprunit1name";
					}
					execSQL += " from UNTDP_MONTHDEPR a "+
							" left join SYSCA_DEPRPARK k on a.enterorg = k.enterorg and a.deprpark=k.deprparkno " +
							" left join SYSCA_DEPRUNIT z on a.enterorg = z.enterorg and a.deprunit=z.deprunitno " +
							" left join SYSCA_DEPRUNIT1 y on a.enterorg = y.enterorg and a.deprunit1=y.deprunit1no " +
							" left join SYSCA_DEPRACCOUNTS x on a.enterorg = x.enterorg and a.depraccounts=x.depraccountsno"+
							" where 1=1 "+	getIfCondition;
					if ("1".equals(getQ_page()) || "3".equals(this.getQ_page()) || "4".equals(this.getQ_page()) ) { // 部門別  or 園區別+部門別    or 園區別
						execSQL += " GROUP BY a.enterorg, a.deprym, a.differencekind, a.deprpark, a.deprunit, a.depraccounts, x.depraccountsname, a.propertytype, k.deprparkname, z.deprunitname ";
						if ("1".equals(this.getQ_page())) {
							execSQL += " order by a.enterorg, a.deprym, a.differencekind, a.deprunit, a.propertytype, a.depraccounts";
						} else {
							execSQL += " order by a.enterorg, a.deprym, a.differencekind, a.deprpark, a.deprunit, a.propertytype";
						}
					} else if ("2".equals(getQ_page()) || "3".equals(getQ_page()) || "4".equals(this.getQ_page()) ) { // 園區別+部門別+部門單位+會計科目  or 園區別+部門別    or 園區別
						execSQL += " GROUP BY a.enterorg, a.deprym, a.differencekind, a.deprpark, a.deprunit, a.deprunit1, a.depraccounts, x.depraccountsname, a.propertytype, k.deprparkname, z.deprunitname, y.deprunit1name ";
						execSQL += " order by a.enterorg, a.deprym, a.differencekind, a.deprpark, a.deprunit, a.deprunit1, a.depraccounts, a.propertytype";
					}
			}	
		
		
			ResultSet rs = db.querySQL(execSQL);
			
			Vector rowData = new Vector();
			int i=1;
			int sum = 0;
			List deprunitList = new ArrayList();
			String note_tmp = "";
			String note2 ="";
			while(rs.next()){
				
				String datetime = Datetime.getYYYMMDD();
				
				Object[] data = new Object[columns.length];
				//全銜
				data[0] = ut._queryData("organaname")._from("SYSCA_ORGAN a ")._with(" and organid = '" + this.getQ_enterOrg() + "'")._toString();
				//印表人
				data[1] = ut._queryData("empname")._from("SYSAP_EMP a ")._with("and organid = '" + getOrganID() + "' and empid = '" + getEditID() + "'")._toString();
				//印表日期
				data[2] = datetime.substring(0,3) + "/" + datetime.substring(3,5) + "/" + datetime.substring(5);
				
				// 用途說明
				if ("1".equals(getQ_page())) {  // 部門別
					data[3] = Datetime.changeTaiwanYYMM(checkGet(rs.getString("deprym")), "1").substring(0, 3)+"年"+Datetime.changeTaiwanYYMM(checkGet(rs.getString("deprym")), "1").substring(3, 5)+"月份    "+checkGet(rs.getString("deprparkname"))+"-" + Common.get(rs.getString("deprunit"))+ " " + checkGet(rs.getString("deprunitname"));
				} else if ("2".equals(getQ_page())) { // 園區別+部門別+部門單位+會計科目
					data[3] = Datetime.changeTaiwanYYMM(checkGet(rs.getString("deprym")), "1").substring(0, 3)+"年"+Datetime.changeTaiwanYYMM(checkGet(rs.getString("deprym")), "1").substring(3, 5)+"月份    "+checkGet(rs.getString("deprparkname"))+"-" + Common.get(rs.getString("deprunit"))+ " " + checkGet(rs.getString("deprunitname"))+"-"+checkGet(rs.getString("deprunit1name"));
				} else if ("3".equals(getQ_page())) { // 園區別+部門別
					data[3] = Datetime.changeTaiwanYYMM(checkGet(rs.getString("deprym")), "1").substring(0, 3)+"年"+Datetime.changeTaiwanYYMM(checkGet(rs.getString("deprym")), "1").substring(3, 5)+"月份    "+checkGet(rs.getString("deprparkname"))+"-" + Common.get(rs.getString("deprunit"))+ " " + checkGet(rs.getString("deprunitname"));
				} else if ("4".equals(getQ_page())) { // 園區別
					data[3] = Datetime.changeTaiwanYYMM(checkGet(rs.getString("deprym")), "1").substring(0, 3)+"年"+Datetime.changeTaiwanYYMM(checkGet(rs.getString("deprym")), "1").substring(3, 5)+"月份    "+checkGet(rs.getString("deprparkname"));
				}
				if ("1".equals(getQ_page())) {
					if(!deprunitList.contains(data[3])){
						deprunitList.add(data[3]);
						sum = 0;
					}
				} else if ("2".equals(getQ_page())) {
					if(!deprunitList.contains(data[3] + checkGet(rs.getString("depraccounts")))){
						deprunitList.add(data[3] + checkGet(rs.getString("depraccounts")));
						sum = 0;
					}
				} else if ("3".equals(getQ_page())) {
					if(!deprunitList.contains(data[3])){
						deprunitList.add(data[3]);
						sum = 0;
					}
				} else if ("4".equals(getQ_page())) {
					if(!deprunitList.contains(data[3])){
						deprunitList.add(data[3]);
						sum = 0;
					}
				}
				
				// 所屬年月份
				data[4] = Datetime.changeTaiwanYYMM(checkGet(rs.getString("deprym")), "1").substring(0, 3)+"年"+Datetime.changeTaiwanYYMM(checkGet(rs.getString("deprym")), "1").substring(3, 5)+"月份";
				
				// 費用名稱
				if ("1".equals(getQ_page())) { // 部門別
					data[5] = checkGet(rs.getString("deprparkname"))+"-" + Common.get(rs.getString("deprunit"))+ " " + checkGet((rs.getString("deprunitname")));
				} else if ("2".equals(getQ_page())) { // 園區別+部門別+部門單位+會計科目
					data[5] = checkGet(rs.getString("deprparkname"))+"-" + Common.get(rs.getString("deprunit"))+ " " + checkGet(rs.getString("deprunitname"))+"-"+checkGet(rs.getString("deprunit1name"));
				} else if ("3".equals(getQ_page())) { // 園區別+部門別
					data[5] = checkGet(rs.getString("deprparkname"))+"-" + Common.get(rs.getString("deprunit"))+ " " + checkGet(rs.getString("deprunitname"));
				} else if ("4".equals(getQ_page())) { // 園區別
					data[5] = checkGet(rs.getString("deprparkname"));
				}
				
				// 部門別
				data[6] = checkGet(rs.getString("deprunit"));
				
				if(checkGet(rs.getString("yeartype")).equals("0") || checkGet(rs.getString("yeartype")).equals("1")){
					data[7] = checkGet(rs.getString("depraccounts"))+" "+checkGet(rs.getString("depraccountsname"));}
				else{
					data[7] = " 520298 其他 ";}
				
				if(checkGet(rs.getString("yeartype")).equals("0") || checkGet(rs.getString("yeartype")).equals("1")){
					data[8] = checkGet(rs.getString("propertytype"));}
				else{ 
					data[8] = "9198 雜項費用";}
				
				data[9] = rs.getDouble("monthDepr");
				sum += Integer.parseInt(rs.getString("monthDepr") == null? "0":rs.getString("monthDepr"));
				
//				if(checkGet(rs.getString("deprunitcb")).equals("N") && checkGet(rs.getString("yeartype")).equals("0")){
//				data[9] = checkGet(rs.getString("monthDepr"));}
//				else if(checkGet(rs.getString("deprunitcb")).equals("N") && checkGet(rs.getString("yeartype")).equals("1")){
//				data[9] = checkGet(rs.getString("monthDepr1"));}
//				else if(checkGet(rs.getString("deprunitcb")).equals("N") && checkGet(rs.getString("yeartype")).equals("2")){
//				data[9] = checkGet(rs.getString("monthDepr2"));}
//				else if(checkGet(rs.getString("deprunitcb")).equals("Y") && checkGet(rs.getString("yeartype")).equals("0")){
//				data[9] = checkGet(rs.getString("scaledmonthdepr"));}
//				else if(checkGet(rs.getString("deprunitcb")).equals("Y") && checkGet(rs.getString("yeartype")).equals("1")){
//				data[9] = checkGet(rs.getString("scaledmonthdepr1"));}
//				else if(checkGet(rs.getString("deprunitcb")).equals("Y") && checkGet(rs.getString("yeartype")).equals("2")){
//				data[9] = checkGet(rs.getString("scaledmonthdepr2"));}
				//貸方
				data[10] = checkGet(rs.getString("propertytype1"));
				data[11] = checkGet(rs.getString("differencekind"));
				data[12] = st._getMoneyFormat(String.valueOf(sum)); 
				data[13] = Common.numToZh(String.valueOf(sum));
				if("2".equals(getQ_page())){
				data[14] = checkGet(rs.getString("depraccounts")) +"-"+ checkGet(rs.getString("propertytype")).substring(0,4);
				data[15] = checkGet(rs.getString("depraccountsname"))+"-"+checkGet(rs.getString("propertytype")).substring(4);
				
				//流水號
				data[16] = String.valueOf(i);
				data[17] = Datetime.changeTaiwanYYMM(checkGet(rs.getString("deprym")), "1").substring(0, 3)+"年"+Datetime.changeTaiwanYYMM(checkGet(rs.getString("deprym")), "1").substring(3, 5)+"月份";
				
				note_tmp = Datetime.changeTaiwanYYMM(checkGet(rs.getString("deprym")), "1").substring(0, 3)+"年"+Datetime.changeTaiwanYYMM(checkGet(rs.getString("deprym")), "1").substring(3, 5)+"月份 "+checkGet(rs.getString("deprparkname"))+" " + checkGet(rs.getString("deprunitname"))+"-"+checkGet(rs.getString("deprunit1name"))+ "設備折舊";


				
				if("510402".equals(checkGet(rs.getString("depraccounts")))){
					data[18] = note_tmp+"(土地)";
					if(notes.containsKey(rs.getString("deprunitname")+"_"+rs.getString("deprunit1name"))) {
						data[18] = note_tmp+"(土地-"+notes.get(rs.getString("deprunitname")+"_"+rs.getString("deprunit1name"))+")";
					}
				}else if("510403".equals(checkGet(rs.getString("depraccounts")))){
					data[18] = note_tmp + "(廠房)";
					if(notes.containsKey(rs.getString("deprunitname")+"_"+rs.getString("deprunit1name"))) {
						data[18] = note_tmp + "(廠房-"+notes.get(rs.getString("deprunitname")+"_"+rs.getString("deprunit1name"))+")";
					}
				}else if("510406".equals(checkGet(rs.getString("depraccounts")))){
					data[18] = note_tmp + "(其他建築物)";
					if(notes.containsKey(rs.getString("deprunitname")+"_"+rs.getString("deprunit1name"))) {
						data[18] = note_tmp + "(其他建築物-"+notes.get(rs.getString("deprunitname")+"_"+rs.getString("deprunit1name"))+")";
					}
				}else if("510405".equals(checkGet(rs.getString("depraccounts")))){
					data[18] = note_tmp + "(住宅)";
					if(notes.containsKey(rs.getString("deprunitname")+"_"+rs.getString("deprunit1name"))) {
						data[18] = note_tmp + "(住宅-"+notes.get(rs.getString("deprunitname")+"_"+rs.getString("deprunit1name"))+")";
					}
				}else{
					data[18] = note_tmp;
					if(notes.containsKey(rs.getString("deprunitname")+"_"+rs.getString("deprunit1name"))) {
						data[18] = note_tmp + "("+notes.get(rs.getString("deprunitname")+"_"+rs.getString("deprunit1name"))+")";
					}
				}

				data[19] = String.valueOf(i+1);
				data[20] = checkGet(rs.getString("propertytype1")).substring(0,6)+"-1001";
				data[21] = checkGet(rs.getString("propertytype1")).substring(6);
				data[22] = note_tmp;
				
				}
				
				if ("5998".equals(checkGet(rs.getString("propertytype").substring(0,4)))) {
					 note2 = "專利權攤銷";
				} else {
					 note2 = "設備折舊";
				}
				
				data[23] = note2;
				
				i=i+2;
				rowData.addElement(data);
				
				
			}
		
			Object[][] data = new Object[0][0];
			data = (Object[][])rowData.toArray(data);
			model.setDataVector(data, columns);
		}
	}catch(Exception x){
		x.printStackTrace();
	}finally{
		db.closeAll();
	}
	return model;
}

private String doGetIfCondition(){
	UNTCH_Tools ut = new UNTCH_Tools();
	String whereSql=" and a.deprmethod != '01' ";
	if (!"".equals(getQ_enterOrg())) 
		whereSql +=" and a.enterOrg = " + Common.sqlChar(getQ_enterOrg()) ; 
	if (!"".equals(getQ_deprYM())) 
		whereSql +=" and a.deprym = " + Common.sqlChar(ut._transToCE_Year(getQ_deprYM())) ;
	if (!"".equals(getQ_differenceKind())) 
		whereSql +=" and a.differencekind = " + Common.sqlChar(getQ_differenceKind()) ;
	if (!"".equals(getQ_deprPark())) 
		whereSql +=" and a.deprpark = " + Common.sqlChar(getQ_deprPark()) ;
	if (!"".equals(getQ_deprUnit())) 
		whereSql +=" and a.deprunit = " + Common.sqlChar(getQ_deprUnit()) ;
	if (!"".equals(getQ_deprUnit1())) 
		whereSql +=" and a.deprunit1 = " + Common.sqlChar(getQ_deprUnit1()) ;
	
	return whereSql;
  }	

}