/*
*<br>程式目的：刪除詳情查詢
*<br>程式代號：untgr032r
*<br>撰寫日期：110/05/20
*<br>程式作者：Kasper.Lee
*<br>--------------------------------------------------------
*<br>修改作者　　修改日期　　　修改目的
*<br>--------------------------------------------------------
*/

package unt.gr;

import java.sql.ResultSet;
import java.util.Map;
import java.util.Vector;

import javax.swing.table.DefaultTableModel;

import util.Common;
import util.Database;
import util.Datetime;
import util.SuperBean;
import util.TCGHCommon;

public class UNTGR032R extends SuperBean {
	Object[] columns = new Object[]{"f0", "f1", "f2", "f3", "f4", "f5"};
	
	private String q_enterorg;
	private String q_enterorgName;
	private String q_year;
	private String q_month;
	private String q_changeDateS;
	private String q_changeDateE;
	private String q_reportType;						// 報表類型
	
	public String getQ_reportType() { return checkGet(q_reportType); }
	public void setQ_reportType(String q_reportType) { this.q_reportType = checkGet(q_reportType); }
	public String getQ_enterorg() { return checkGet(q_enterorg); }
	public void setQ_enterorg(String q_enterorg) { this.q_enterorg = checkSet(q_enterorg); }
	public String getQ_enterorgName() {	return checkGet(q_enterorgName); }
	public void setQ_enterorgName(String q_enterorgName) { this.q_enterorgName = checkSet(q_enterorgName); }
	public String getQ_year() {return checkGet(q_year);}
	public void setQ_year(String q_year) {this.q_year = checkSet(q_year);}
	public String getQ_month() {return checkGet(q_month);}
	public void setQ_month(String q_month) {this.q_month = checkSet(q_month);}
	public String getQ_changeDateS() { return checkGet(q_changeDateS); }
	public void setQ_changeDateS(String q_changeDateS) { this.q_changeDateS = checkSet(q_changeDateS); }
	public String getQ_changeDateE() { return checkGet(q_changeDateE); }
	public void setQ_changeDateE(String q_changeDateE) { this.q_changeDateE = checkSet(q_changeDateE); }


	String userName;
	public String getUserName() { return checkGet(userName); }
	public void setUserName(String s) { userName = checkSet(s); }

	public DefaultTableModel getResultModel() throws Exception{
		
		DefaultTableModel model = null;
		Database db = new Database();
		
		try{
			Vector rowData = new Vector();
			model = new javax.swing.table.DefaultTableModel();
			//#region SQL
			StringBuilder execSQL = new StringBuilder();
			
			execSQL.append(" select (select x.organaname from SYSCA_ORGAN x where x.organid = a.enterorg) as 'enterorgName', ")
				   .append(" (select x.unitname from UNTMP_KEEPUNIT x where x.unitno = a.unitid and x.enterorg = a.enterorg) as 'unitname', ")
				   .append(" empname, functionType, funname, editdate, edittime ")
				   .append(" from SYSAP_RECORDSQL a")
				   .append(" where functionType in ('insert', 'delete', 'update','query') ");
				
			if (this.getQ_enterorgName() != null && !"".equals(this.getQ_enterorgName())) {
				execSQL.append(" and enterorg = " + Common.sqlChar(this.getQ_enterorg()));
			}
			
			if (this.getQ_changeDateS() != null && !"".equals(this.getQ_changeDateS())) {
				execSQL.append(" and editdate >= " + Common.sqlChar(Datetime.getYYYYMMDDFromRocDate(this.getQ_changeDateS())));
			}
			
			if (this.getQ_changeDateE() != null && !"".equals(this.getQ_changeDateE())) {
				execSQL.append(" and editdate <= " + Common.sqlChar(Datetime.getYYYYMMDDFromRocDate(this.getQ_changeDateE())));
			}
			
			execSQL.append(" order by editdate, edittime ");
			
			System.out.println(execSQL);
			//#end
			ResultSet rs = db.querySQL_NoChange(execSQL.toString());
			
			while(rs.next()) {
				Object[] data = new Object[columns.length];

				data[0] = Common.get(rs.getString("enterorgName"));
				data[1] = Common.get(rs.getString("unitname"));
				data[2] = Common.get(rs.getString("empname"));
				if ("delete".equals(Common.get(rs.getString("functionType")))) {
					data[3] = "刪除";					
				} else if ("update".equals(Common.get(rs.getString("functionType")))) {
					data[3] = "修改";	
				} else if ("insert".equals(Common.get(rs.getString("functionType")))) {
					data[3] = "新增";	
				} else if ("query".equals(Common.get(rs.getString("functionType")))) {
					data[3] = "查詢";	
				}
			
				data[4] = Common.get(rs.getString("funname"));	
				data[5] = Common.get(Common.formatYYYMMDD(Datetime.changeTaiwanYYMMDD(rs.getString("editdate"), "1"), 2))
							+ "\r\n" + Common.MaskTime(rs.getString("edittime"));
				
				rowData.addElement(data);
			}
			
			Object[][] datas = new Object[0][0];
			datas = (Object[][])rowData.toArray(datas);
			model.setDataVector(datas, columns);
			
		} catch(Exception x) {
			x.printStackTrace();
		} finally {
			db.closeAll();
		}
		
		return model;
	}
	
}
