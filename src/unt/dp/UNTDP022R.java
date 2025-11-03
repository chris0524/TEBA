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
import java.util.List;
import java.util.Vector;

import javax.swing.table.DefaultTableModel;

import unt.ch.UNTCH_Tools;
import util.Common;
import util.Database;
import util.Datetime;
import TDlib_Simple.tools.src.StringTools;

public class UNTDP022R extends UNTDP001F{

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

	private String purpose;
	private String debitAccounts;
	private String creditAccounts;
	public String getPurpose() {return checkGet(purpose);}
	public void setPurpose(String purpose) {this.purpose = checkSet(purpose);}
	public String getDebitAccounts() {return checkGet(debitAccounts);}
	public void setDebitAccounts(String debitAccounts) {this.debitAccounts = checkSet(debitAccounts);}
	public String getCreditAccounts() {return checkGet(creditAccounts);}
	public void setCreditAccounts(String creditAccounts) {this.creditAccounts = checkSet(creditAccounts);}
	
	
public DefaultTableModel getResultModel() throws Exception{
	
	UNTCH_Tools ut = new UNTCH_Tools();
	DefaultTableModel model = new javax.swing.table.DefaultTableModel();
	Database db = new Database();
	StringTools st = new StringTools();
	String execSQL="";	

	try{
		String[] columns = new String[] {"head01","head02","head03","head04","head05","head06","detail01","detail02","detail03","detail04",
				"detail05","differencekind","sum", "zhSum","changeNewPage"};
		//南科管局
		if(getQ_enterOrg().equals("A42030000G")){
			execSQL = " select * from (";
			execSQL += " select '1' as yeartype,a.enterorg,a.deprym,a.differencekind,a.deprpark,a.deprunit,";
			if ("1".equals(getQ_page())) {
				execSQL += " a.depraccounts,";
			} else if ("2".equals(getQ_page())) {
				execSQL += " a.deprunit1,a.depraccounts,";
			}
			execSQL += " SUM(CASE a.deprunitcb WHEN 'N' THEN a.monthDepr1 ELSE scaledmonthdepr1 END) monthdepr,"+
					" (case a.propertytype when '2' then '5111 土地改良物折舊' when '3' then '5121 一般房屋折舊' when '4' then '5131 機械及設備折舊' when '5' then '5141 交通及運輸折舊' when '6' then '5151 雜項設備折舊' else '' end) as propertytype,"+
					" (case a.enterorg when 'A42020000G' then '180710-1001 累計折舊 代管資產' else '180710 累計折舊 代管資產' end) as propertytype1,";
					if ("1".equals(getQ_page())) {
						execSQL += " k.deprparkname, z.deprunitname, x.depraccountsname";
					} else if ("2".equals(getQ_page())) {
						execSQL += " k.deprparkname, z.deprunitname, y.deprunit1name, x.depraccountsname";
					}
					execSQL += " from UNTDP_MONTHDEPR a "+
					" left join SYSCA_DEPRPARK k on a.enterorg = k.enterorg and a.deprpark=k.deprparkno " +
					" left join SYSCA_DEPRUNIT z on a.enterorg = z.enterorg and a.deprunit=z.deprunitno " +
					" left join SYSCA_DEPRUNIT1 y on a.enterorg = y.enterorg and a.deprunit1=y.deprunit1no " +
					" left join SYSCA_DEPRACCOUNTS x on a.enterorg = x.enterorg and a.depraccounts=x.depraccountsno"+
					" where 1=1 and a.monthdepr1 <>0 "+	doGetIfCondition();
					if ("1".equals(getQ_page())) {
						execSQL += " GROUP BY a.enterorg, a.deprym, a.differencekind, a.deprpark, a.deprunit, a.depraccounts, x.depraccountsname, a.propertytype, k.deprparkname, z.deprunitname ";
					} else if ("2".equals(getQ_page())) {
						execSQL += " GROUP BY a.enterorg, a.deprym, a.differencekind, a.deprpark, a.deprunit, a.deprunit1, a.depraccounts, x.depraccountsname, a.propertytype, k.deprparkname, z.deprunitname, y.deprunit1name ";
					}
					
			execSQL +=" union ";
			execSQL +=" select '2' as yeartype,a.enterorg,a.deprym,a.differencekind,a.deprpark,a.deprunit,";
			if ("1".equals(getQ_page())) {
				execSQL += " a.depraccounts,";
			} else if ("2".equals(getQ_page())) {
				execSQL += " a.deprunit1,a.depraccounts,";
			}
			execSQL += " SUM(CASE a.deprunitcb WHEN 'N' THEN a.monthDepr2 ELSE scaledmonthdepr2 END) monthdepr,"+
					" (case a.propertytype when '2' then '5111 土地改良物折舊' when '3' then '5121 一般房屋折舊' when '4' then '5131 機械及設備折舊' when '5' then '5141 交通及運輸折舊' when '6' then '5151 雜項設備折舊' else '' end) as propertytype,"+
					" (case a.enterorg when 'A42020000G' then '180710-1001 累計折舊 代管資產' else '180710 累計折舊 代管資產' end) as propertytype1,";
			if ("1".equals(getQ_page())) {
				execSQL += " k.deprparkname, z.deprunitname, x.depraccountsname";
			} else if ("2".equals(getQ_page())) {
				execSQL += " k.deprparkname, z.deprunitname, y.deprunit1name, x.depraccountsname";
			}
			execSQL += " from UNTDP_MONTHDEPR a "+
					" left join SYSCA_DEPRPARK k on a.enterorg = k.enterorg and a.deprpark=k.deprparkno " +
					" left join SYSCA_DEPRUNIT z on a.enterorg = z.enterorg and a.deprunit=z.deprunitno " +
					" left join SYSCA_DEPRUNIT1 y on a.enterorg = y.enterorg and a.deprunit1=y.deprunit1no " +
					" left join SYSCA_DEPRACCOUNTS x on a.enterorg = x.enterorg and a.depraccounts=x.depraccountsno"+
					" where 1=1 and a.monthdepr2 <>0 "+	doGetIfCondition();
			if ("1".equals(getQ_page())) {
				execSQL += " GROUP BY a.enterorg, a.deprym, a.differencekind, a.deprpark, a.deprunit, a.depraccounts, x.depraccountsname, a.propertytype, k.deprparkname, z.deprunitname ) b ";
				execSQL += " order by b.enterorg, b.deprym, b.differencekind, b.deprunit, b.propertytype, b.depraccounts, b.yeartype";
			} else if ("2".equals(getQ_page())) {
				execSQL += " GROUP BY a.enterorg, a.deprym, a.differencekind, a.deprpark, a.deprunit, a.deprunit1, a.depraccounts, x.depraccountsname, a.propertytype, k.deprparkname, z.deprunitname, y.deprunit1name ) b ";
				execSQL += " order by b.enterorg, b.deprym, b.differencekind, b.deprpark, b.deprunit, b.deprunit1, b.propertytype, b.depraccounts, b.yeartype";
			}
		
		} else { // 非南科管局
			execSQL=" select '0' as yeartype,a.enterorg,a.deprym,a.differencekind,a.deprpark,a.deprunit,";
			if ("1".equals(getQ_page())) {
				execSQL += " a.depraccounts,";
			} else if ("2".equals(getQ_page())) {
				execSQL += " a.deprunit1,a.depraccounts,";
			}
			execSQL += " SUM(CASE a.deprunitcb WHEN 'N' THEN a.monthdepr ELSE scaledmonthdepr END) monthdepr,"+
				" (case a.propertytype when '2' then '5111 土地改良物折舊' when '3' then '5121 一般房屋折舊' when '4' then '5131 機械及設備折舊' when '5' then '5141 交通及運輸折舊' when '6' then '5151 雜項設備折舊' else '' end) as propertytype,"+
				" (case a.enterorg when 'A42020000G' then '180710-1001 累計折舊 代管資產' else '180710 累計折舊 代管資產' end) as propertytype1,";
				if ("1".equals(getQ_page())) {
					execSQL += " k.deprparkname, z.deprunitname, x.depraccountsname";
				} else if ("2".equals(getQ_page())) {
					execSQL += " k.deprparkname, z.deprunitname, y.deprunit1name, x.depraccountsname";
				}	
				execSQL += " from UNTDP_MONTHDEPR a "+
				" left join SYSCA_DEPRPARK k on a.enterorg = k.enterorg and a.deprpark=k.deprparkno " +
				" left join SYSCA_DEPRUNIT z on a.enterorg = z.enterorg and a.deprunit=z.deprunitno " +
				" left join SYSCA_DEPRUNIT1 y on a.enterorg = y.enterorg and a.deprunit1=y.deprunit1no " +
				" left join SYSCA_DEPRACCOUNTS x on a.enterorg = x.enterorg and a.depraccounts=x.depraccountsno"+
				" where 1=1 "+	doGetIfCondition();
				if ("1".equals(getQ_page())) {
					execSQL += " GROUP BY a.enterorg, a.deprym, a.differencekind, a.deprpark, a.deprunit, a.depraccounts, x.depraccountsname, a.propertytype, k.deprparkname, z.deprunitname ";
					execSQL += " order by a.enterorg, a.deprym, a.differencekind, a.deprunit, a.propertytype, a.depraccounts";
				} else if ("2".equals(getQ_page())) {
					execSQL += " GROUP BY a.enterorg, a.deprym, a.differencekind, a.deprpark, a.deprunit, a.deprunit1, a.depraccounts, x.depraccountsname, a.propertytype, k.deprparkname, z.deprunitname, y.deprunit1name ";
					execSQL += " order by a.enterorg, a.deprym, a.differencekind, a.deprpark, a.deprunit, a.deprunit1, a.depraccounts, a.propertytype";
				}
		}
		
		//System.out.println("=> " + execSQL);		
		ResultSet rs = db.querySQL(execSQL);
		Vector rowData = new Vector();
		
		Vector rowData_forA42020000G = null; 
		boolean isA42020000G = false;
		if("A42020000G".equals(this.getQ_enterOrg())){
			isA42020000G = true;
			rowData_forA42020000G = new Vector();
		}
		
		//int i;
		int sum = 0;
		int sum2 = 0 ;
		List deprunitList = new ArrayList(); 
		List deprunitList2 = new ArrayList();
		while(rs.next()){
		
			String datetime = Datetime.getYYYMMDD();
			
			Object[] data = new Object[columns.length];
			
			//全銜
			data[0] = ut._queryData("organaname")._from("SYSCA_ORGAN a ")._with(" and organid = '" + this.getQ_enterOrg() + "'")._toString();
			//印表人
			data[1] = ut._queryData("empname")._from("SYSAP_EMP a ")._with("and organid = '" + getOrganID() + "' and empid = '" + getEditID() + "'")._toString();
			//印表日期
			data[2] = datetime.substring(0,3) + "/" + datetime.substring(3,5) + "/" + datetime.substring(5);
			if ("1".equals(getQ_page())) {
				data[3] = Datetime.changeTaiwanYYMM(checkGet(rs.getString("deprym")), "1").substring(0, 3)+"年"+Datetime.changeTaiwanYYMM(checkGet(rs.getString("deprym")), "1").substring(3, 5)+"月份    "+checkGet(rs.getString("deprunit"))+checkGet(rs.getString("deprunitname"));
			} else if ("2".equals(getQ_page())) {
				data[3] = Datetime.changeTaiwanYYMM(checkGet(rs.getString("deprym")), "1").substring(0, 3)+"年"+Datetime.changeTaiwanYYMM(checkGet(rs.getString("deprym")), "1").substring(3, 5)+"月份    "+checkGet(rs.getString("deprparkname"))+"-"+checkGet(rs.getString("deprunitname"))+"-"+checkGet(rs.getString("deprunit1name"));
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
			}
			
			data[4] = Datetime.changeTaiwanYYMM(checkGet(rs.getString("deprym")), "1").substring(0, 3)+"年"+Datetime.changeTaiwanYYMM(checkGet(rs.getString("deprym")), "1").substring(3, 5)+"月份";
			if ("1".equals(getQ_page())) {
				data[5] = checkGet(rs.getString("deprunit"))+checkGet((rs.getString("deprunitname")));
			} else if ("2".equals(getQ_page())) {
				data[5] = checkGet(rs.getString("deprparkname"))+"-"+checkGet(rs.getString("deprunitname"))+"-"+checkGet(rs.getString("deprunit1name"));
			}
			
			//借方-會計科目
			data[6] = checkGet(rs.getString("deprunit"));
			if (checkGet(rs.getString("yeartype")).equals("0") || checkGet(rs.getString("yeartype")).equals("1")) {
				data[7] = checkGet(rs.getString("depraccounts"))+" "+checkGet(rs.getString("depraccountsname"));
			} else {
				data[7] = " 520298 其他 ";
			}
			
			// 借方-會計子目
			if(checkGet(rs.getString("yeartype")).equals("0") || checkGet(rs.getString("yeartype")).equals("1")){
				if("113".equals(this.getQ_differenceKind())){
					data[8] = "5998 其他攤銷費用";
				}else{
					// 問題單 1227 yearType in (0,1) 借方-會計子目 為 581代管資產折舊
					data[8] = "5701 代管資產折舊";
				}				
			}
			else{ 
				data[8] = "9198 雜項費用";
			}
			data[9] = st._getMoneyFormat(rs.getString("monthDepr"));
			sum += Integer.parseInt(rs.getString("monthDepr") == null? "0":rs.getString("monthDepr"));
			
//			if(checkGet(rs.getString("deprunitcb")).equals("N") && checkGet(rs.getString("yeartype")).equals("0")){
//			data[9] = checkGet(rs.getString("monthDepr"));}
//			else if(checkGet(rs.getString("deprunitcb")).equals("N") && checkGet(rs.getString("yeartype")).equals("1")){
//			data[9] = checkGet(rs.getString("monthDepr1"));}
//			else if(checkGet(rs.getString("deprunitcb")).equals("N") && checkGet(rs.getString("yeartype")).equals("2")){
//			data[9] = checkGet(rs.getString("monthDepr2"));}
//			else if(checkGet(rs.getString("deprunitcb")).equals("Y") && checkGet(rs.getString("yeartype")).equals("0")){
//			data[9] = checkGet(rs.getString("scaledmonthdepr"));}
//			else if(checkGet(rs.getString("deprunitcb")).equals("Y") && checkGet(rs.getString("yeartype")).equals("1")){
//			data[9] = checkGet(rs.getString("scaledmonthdepr1"));}
//			else if(checkGet(rs.getString("deprunitcb")).equals("Y") && checkGet(rs.getString("yeartype")).equals("2")){
//			data[9] = checkGet(rs.getString("scaledmonthdepr2"));}
			
			
			// 貸方-會計科目
			if("113".equals(this.getQ_differenceKind())){
				data[10] = "180101-1001遞延費用";
			}else{
				data[10] = checkGet(rs.getString("propertytype1"));
			}
			
			data[11] = checkGet(rs.getString("differencekind"));
			data[12] = st._getMoneyFormat(String.valueOf(sum));
			data[13] = Common.numToZh(String.valueOf(sum));
			
			rowData.addElement(data);
			
			if(isA42020000G){
				Object[] data_forA42020000G = data.clone();
			
				data_forA42020000G[3] = data_forA42020000G[3].toString().split("月份")[0] + "月份 " + checkGet(this.getPurpose());
				data_forA42020000G[7] = checkGet(this.getDebitAccounts());
				data_forA42020000G[10]= checkGet(this.getCreditAccounts());				
				data_forA42020000G[14] = "isA42020000G";
				
	
				if ("1".equals(getQ_page())) {
					if(!deprunitList2.contains(data_forA42020000G[0].toString() + data_forA42020000G[4].toString() +data_forA42020000G[11].toString() + data_forA42020000G[6].toString() + data_forA42020000G[14].toString())){
						deprunitList2.add(data_forA42020000G[0].toString() + data_forA42020000G[4].toString() + data_forA42020000G[11].toString() + data_forA42020000G[6].toString() + data_forA42020000G[14].toString());
						sum2 = 0;
					}
				}
				if ("2".equals(getQ_page())) {
					if(!deprunitList2.contains(data_forA42020000G[0].toString() + data_forA42020000G[4].toString() +data_forA42020000G[11].toString() + data_forA42020000G[3].toString()+ data_forA42020000G[6].toString() + data_forA42020000G[7].toString())){
						deprunitList2.add(data_forA42020000G[0].toString() + data_forA42020000G[4].toString() + data_forA42020000G[11].toString() + data_forA42020000G[3].toString()+ data_forA42020000G[6].toString() + data_forA42020000G[7].toString());
						sum2 = 0;
					}
				}
				sum2 += Integer.parseInt(rs.getString("monthDepr") == null? "0":rs.getString("monthDepr"));
				data_forA42020000G[12] = st._getMoneyFormat(String.valueOf(sum2));
				data_forA42020000G[13] = Common.numToZh(String.valueOf(sum2));
				rowData_forA42020000G.addElement(data_forA42020000G);
			}
		}
		if(isA42020000G && "111".equals(this.getQ_differenceKind())){
			rowData.addAll(rowData_forA42020000G);
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

private String doGetIfCondition(){
	UNTCH_Tools ut = new UNTCH_Tools();
	String whereSql="";
	if (!"".equals(getQ_enterOrg())) 
		whereSql =" and a.enterOrg = " + Common.sqlChar(getQ_enterOrg()) ; 
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