/*
*<br>程式目的：財產折舊攤提月報總表(基金財產) 
*<br>程式代號：untdp014r
*<br>撰寫日期：94/12/12
*<br>程式作者：Chris
*<br>--------------------------------------------------------
*<br>修改作者　　修改日期　　　修改目的
*<br>--------------------------------------------------------
*/

package unt.dp;

import java.sql.ResultSet;
import java.util.Map;
import java.util.Vector;

import javax.swing.table.DefaultTableModel;

import unt.ch.UNTCH_Tools;
import util.Common;
import util.Database;
import util.Datetime;
import util.TCGHCommon;

public class UNTDP014R extends UNTDP001F{

	private String enterOrg;
	private String organaName;
	private String deprUnitName;
	private String deprUnit;
	private String deprUnitCB;
	private String monthDepr;
	private String scaledMonthDepr;
	private String q_enterOrg;
	private String q_deprYM;
	private String q_differenceKind;
	private String q_groupType; // 統計方式:
	private String q_reportType;		// 報表類型
	
	public String getQ_reportType() {	return checkGet(q_reportType);}
	public void setQ_reportType(String q_reportType) { this.q_reportType = checkSet(q_reportType); }	
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
	public String getQ_enterOrg(){ return checkGet(q_enterOrg); }
	public void setQ_enterOrg(String s){ q_enterOrg=checkSet(s); }
	public String getQ_deprYM(){ return checkGet(q_deprYM); }
	public void setQ_deprYM(String s){ q_deprYM=checkSet(s); }
	public String getQ_differenceKind(){ return checkGet(q_differenceKind); }
	public void setQ_differenceKind(String s){ q_differenceKind=checkSet(s); }
	public String getQ_groupType() { return checkGet(q_groupType); }
	public void setQ_groupType(String q_groupType) { this.q_groupType = checkSet(q_groupType); }
	
	String isOrganManager;
	String isAdminManager;
	String organID;
	String editID;
	public String getOrganID() { return checkGet(organID); }
	public void setOrganID(String s) { organID = checkSet(s); }
	public String getIsOrganManager() { return checkGet(isOrganManager); }
	public void setIsOrganManager(String s) { isOrganManager = checkSet(s); }
	public String getIsAdminManager() { return checkGet(isAdminManager); }
	public void setIsAdminManager(String s) { isAdminManager = checkSet(s); }
	public String getEditID() {return checkGet(editID);}
	public void setEditID(String editID) {this.editID = checkSet(editID);} 

	
public DefaultTableModel getResultModel() throws Exception{
	
	UNTCH_Tools ul = new UNTCH_Tools();
	UNTDP014R obj = this;
	DefaultTableModel model = new javax.swing.table.DefaultTableModel();
	Database db = new Database();
	Map<String,String> deprunitMap = TCGHCommon.getSysca_DeprUnit(this.getQ_enterOrg());		//部門別名稱
	Map<String,String> deprunit1Map = TCGHCommon.getSysca_DeprUnit1(this.getQ_enterOrg());		//部門別單位名稱
	Map<String,String> deprAccountsMap = TCGHCommon.getSysca_DeprAccounts(this.getQ_enterOrg());		//部門會計科目名稱
	
	try{
		String[] columns = new String[] {"head01","head02","detail01","detail02","detail03","detail04","detail05","detail06","detail07","detail09","sum","sum1","sum2","sum3","sum4","sum5","sum6"};
										
		String execSQL=" select a.enterorg as enterOrg , a.deprunit as deprUnit, x.organaname as organaname,z.deprunitname as deprunitname,";
               if ("C".equals(this.getQ_groupType())) {
            	   execSQL +=" a.depraccounts, ";
			   }else{
				   execSQL +=" a.deprunit1 as deprUnit1,";
			   }
			   execSQL +=" SUM(CASE WHEN a.propertytype = '2' THEN CASE WHEN a.deprunitcb = 'Y' THEN ISNULL(a.scaledmonthdepr, 0) ELSE ISNULL(a.monthdepr, 0) END ELSE 0 end) AS d1," +
						" SUM(CASE WHEN a.propertytype = '3' THEN CASE WHEN a.deprunitcb = 'Y' THEN ISNULL(a.scaledmonthdepr, 0) ELSE ISNULL(a.monthdepr, 0) END ELSE 0 end) AS d2," +
						" SUM(CASE WHEN a.propertytype = '4' THEN CASE WHEN a.deprunitcb = 'Y' THEN ISNULL(a.scaledmonthdepr, 0) ELSE ISNULL(a.monthdepr, 0) END ELSE 0 end) AS d3," +
						" SUM(CASE WHEN a.propertytype = '5' THEN CASE WHEN a.deprunitcb = 'Y' THEN ISNULL(a.scaledmonthdepr, 0) ELSE ISNULL(a.monthdepr, 0) END ELSE 0 end) AS d4," +
						" SUM(CASE WHEN a.propertytype = '6' THEN CASE WHEN a.deprunitcb = 'Y' THEN ISNULL(a.scaledmonthdepr, 0) ELSE ISNULL(a.monthdepr, 0) END ELSE 0 end) AS d5 " +
//						" (select ISNULL(SUM(ISNULL(scaledmonthdepr,0)),0)  from UNTDP_MONTHDEPR where propertytype='2' and enterorg=a.enterorg and deprunit = a.deprunit and deprunitcb = 'Y' and differencekind='120') + (select ISNULL(SUM(ISNULL(monthdepr,0)),0)  from UNTDP_MONTHDEPR where propertytype='2' and enterorg=a.enterorg and deprunit = a.deprunit and deprunit1 = a.deprunit1 and deprunitcb = 'N' and differencekind='120')  as d1," +
//						" (select ISNULL(SUM(ISNULL(scaledmonthdepr,0)),0)  from UNTDP_MONTHDEPR where propertytype='3' and enterorg=a.enterorg and deprunit = a.deprunit and deprunitcb = 'Y' and differencekind='120') + (select ISNULL(SUM(ISNULL(monthdepr,0)),0)  from UNTDP_MONTHDEPR where propertytype='3' and enterorg=a.enterorg and deprunit = a.deprunit and deprunit1 = a.deprunit1 and deprunitcb = 'N' and differencekind='120')  as d2," +
//						" (select ISNULL(SUM(ISNULL(scaledmonthdepr,0)),0)  from UNTDP_MONTHDEPR where propertytype='4' and enterorg=a.enterorg and deprunit = a.deprunit and deprunitcb = 'Y' and differencekind='120') + (select ISNULL(SUM(ISNULL(monthdepr,0)),0)  from UNTDP_MONTHDEPR where propertytype='4' and enterorg=a.enterorg and deprunit = a.deprunit and deprunit1 = a.deprunit1 and deprunitcb = 'N' and differencekind='120')  as d3," +
//						" (select ISNULL(SUM(ISNULL(scaledmonthdepr,0)),0)  from UNTDP_MONTHDEPR where propertytype='5' and enterorg=a.enterorg and deprunit = a.deprunit and deprunitcb = 'Y' and differencekind='120') + (select ISNULL(SUM(ISNULL(monthdepr,0)),0)  from UNTDP_MONTHDEPR where propertytype='5' and enterorg=a.enterorg and deprunit = a.deprunit and deprunit1 = a.deprunit1 and deprunitcb = 'N' and differencekind='120')  as d4," +
//						" (select ISNULL(SUM(ISNULL(scaledmonthdepr,0)),0)  from UNTDP_MONTHDEPR where propertytype='6' and enterorg=a.enterorg and deprunit = a.deprunit and deprunitcb = 'Y' and differencekind='120') + (select ISNULL(SUM(ISNULL(monthdepr,0)),0)  from UNTDP_MONTHDEPR where propertytype='6' and enterorg=a.enterorg and deprunit = a.deprunit and deprunit1 = a.deprunit1 and deprunitcb = 'N' and differencekind='120')  as d5 "+
						" from UNTDP_MONTHDEPR a "+
						" left join SYSCA_ORGAN x on a.enterorg = x.organid "+
						" left join SYSCA_DEPRUNIT z on  a.enterorg = z.enterorg and a.deprunit=z.deprunitno "+
						" where 1=1 ";

		if (!"".equals(getQ_enterOrg())) {
			execSQL +=" and a.enterOrg = " + Common.sqlChar(getQ_enterOrg()) ;
		} else {
			if (!getIsAdminManager().equalsIgnoreCase("Y")) {
				if ("Y".equals(getIsOrganManager())) {
					//execSQL += " and a.enterOrg like '" + getOrganID().substring(0,5) + "%' ";	
					execSQL += " and ( a.enterOrg = "+ Common.sqlChar(getOrganID()) +" or a.enterOrg in ( select organID from SYSCA_Organ where organSuperior = "+ Common.sqlChar(getOrganID()) +" ) ) ";
				} else {
					execSQL += " and a.enterOrg = " + Common.sqlChar(getOrganID()) ;
				}
			}
		}
		if (!"".equals(getQ_deprYM())) 
			execSQL +=" and a.deprym = " + Common.sqlChar(ul._transToCE_Year(getQ_deprYM())) ;
		if (!"".equals(getQ_differenceKind())) 
			execSQL +=" and a.differencekind = " + Common.sqlChar(getQ_differenceKind()) ;
		
		execSQL+=" GROUP BY " +
		" a.enterorg , " +
		" a.deprunit , " +
		" x.organaname , " +
		" z.deprunitname ";
		
		if ("C".equals(this.getQ_groupType())) {
			execSQL += ",a.depraccounts";
		}else{
			execSQL += ",a.deprunit1";
		}		
		
		execSQL += " order by a.enterorg,a.deprunit";
		if ("B".equals(this.getQ_groupType())) {
			execSQL += ",a.deprunit1";
		}
		if ("C".equals(this.getQ_groupType())) {
			execSQL += ",a.depraccounts";
		}
			
			

		//System.out.println("=> " + execSQL);		
		ResultSet rs = db.querySQL(execSQL);
		Vector<Object> rowData = new Vector<Object>();
		long sum =0;
		long sum1 =0;
		long sum2 =0;
		long sum3 =0;
		long sum4 =0;
		long sum5 =0;
		long sum6 =0;
		
		while(rs.next()){
			obj.setEnterOrg(checkGet(rs.getString("enterOrg")));
			obj.setOrganaName(checkGet(rs.getString("organaName")));
			obj.setDeprUnitName(checkGet(rs.getString("deprUnitName")));
			obj.setDeprUnit(checkGet(rs.getString("deprUnit")));

			String datetime = Datetime.getYYYMMDD();
			Object[] data = new Object[columns.length];
			data[0] = checkGet(rs.getString("organaName"));
			data[1] = getQ_deprYM().substring(0, 3)+"年"+getQ_deprYM().substring(3, 5)+"月";
			data[2] = checkGet(deprunitMap.get(rs.getString("deprunit")));
			if ("C".equals(this.getQ_groupType())) {
				data[3] = checkGet(deprAccountsMap.get(rs.getString("depraccounts")));
			}else{
				data[3] = checkGet(deprunit1Map.get(rs.getString("deprunit1")));	
			}
			String d1 = rs.getString("d1");
			String d2 = rs.getString("d2");
			String d3 = rs.getString("d3");
			String d4 = rs.getString("d4");
			String d5 = rs.getString("d5");
			
			data[4] = Common.valueFormat(d1);
			data[5] = Common.valueFormat(d2);
			data[6] = Common.valueFormat(d3);
			data[7] = Common.valueFormat(d4);
			data[8] = Common.valueFormat(d5);
			
			sum =  Long.valueOf(d1) + Long.valueOf(d2) + Long.valueOf(d3) + Long.valueOf(d4) + Long.valueOf(d5) ;
			sum1 +=  Long.valueOf(d1);
			sum2 +=  Long.valueOf(d2);
			sum3 +=  Long.valueOf(d3);
			sum4 +=  Long.valueOf(d4);
			sum5 +=  Long.valueOf(d5);
			sum6 += sum;
				if (sum == 0l &&
					sum1 == 0l &&
					sum2 == 0l &&
					sum3 == 0l &&
					sum4 == 0l &&
					sum5 == 0l &&
					sum6 == 0l) {
					continue;
				}
			//印表日期
			data[9] = datetime.substring(0,3) + "年" + datetime.substring(3,5) + "月" + datetime.substring(5)+ "日";
			
			data[10] = Common.valueFormat(String.valueOf(sum));
			data[11] = Common.valueFormat(String.valueOf(sum1));
			data[12] = Common.valueFormat(String.valueOf(sum2));
			data[13] = Common.valueFormat(String.valueOf(sum3));
			data[14] = Common.valueFormat(String.valueOf(sum4));
			data[15] = Common.valueFormat(String.valueOf(sum5));
			data[16] = Common.valueFormat(String.valueOf(sum6));
			
			rowData.addElement(data);
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



}