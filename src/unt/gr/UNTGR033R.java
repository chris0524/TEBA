/*
*<br>程式目的：地籍地段代碼異動清冊
*<br>程式代號：untgr033R
*<br>撰寫日期：112/05/26
*<br>程式作者：chris.lin
*<br>--------------------------------------------------------
*<br>修改作者　　修改日期　　　修改目的
*<br>--------------------------------------------------------
*/

package unt.gr;

import java.sql.ResultSet;
import java.util.Vector;

import javax.swing.table.DefaultTableModel;

import util.Common;
import util.Database;
import util.Datetime;
import util.SuperBean;

public class UNTGR033R extends SuperBean {
	Object[] columns = new Object[]{"f0", "f1", "f2", "f3", "f4", "f5", "f6", "f7", "f8","f9"};
	
	private String q_enterorg;
	private String q_enterorgName;
	private String q_reportType;						// 報表類型
	
	public String getQ_reportType() { return checkGet(q_reportType); }
	public void setQ_reportType(String q_reportType) { this.q_reportType = checkGet(q_reportType); }
	public String getQ_enterorg() { return checkGet(q_enterorg); }
	public void setQ_enterorg(String q_enterorg) { this.q_enterorg = checkSet(q_enterorg); }
	public String getQ_enterorgName() {	return checkGet(q_enterorgName); }
	public void setQ_enterorgName(String q_enterorgName) { this.q_enterorgName = checkSet(q_enterorgName); }

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
			
			execSQL.append(" select b.propertyno,b.serialno,(select codename from SYSCA_CODE where b.differencekind = SYSCA_CODE.codeid and SYSCA_CODE.codekindid='DFK') as differencekind,b.propertyname1,null as landsignno, ")
				   .append(" (s.signdesc + SUBSTRING(b.signno,8,4) + '-' + SUBSTRING(b.signno,12,4)) as signno,b.doorplate4 as doorplate,(select uk.unitname from UNTMP_KEEPUNIT uk where b.keepunit= uk.unitno) as keepunit, ")
				   .append(" (select kp.keepername from UNTMP_KEEPER kp where b.keeper= kp.keeperno) as keeper,s.memo  from UNTBU_BUILDING b ")
				   .append(" left join SYSCA_SIGN s on SUBSTRING(b.signno,0,8) =  s.signno ")
				   .append(" where (s.signdesc like '%註銷%' or s.signdesc like '%調整%') and b.datastate='1' ");
					if (this.getQ_enterorgName() != null && !"".equals(this.getQ_enterorgName())) {
						execSQL.append(" and b.enterorg = " + Common.sqlChar(this.getQ_enterorg()));
					}
			execSQL.append(" union ")
				   .append(" select c.propertyno,c.serialno,(select codename from SYSCA_CODE where c.differencekind = SYSCA_CODE.codeid and SYSCA_CODE.codekindid='DFK') as differencekind,c.propertyname1,(s.signdesc + SUBSTRING(c.signno,8,4) + '-' + SUBSTRING(c.signno,12,4)) as landsignno, ")
				   .append(" null as signno, ")
				   .append(" null as doorplate,(select uk.unitname from UNTMP_KEEPUNIT uk where c.keepunit= uk.unitno) as keepunit, ")
				   .append(" (select kp.keepername from UNTMP_KEEPER kp where c.keeper= kp.keeperno) as keeper,s.memo  from UNTLA_LAND c ")
				   .append(" left join SYSCA_SIGN s on SUBSTRING(c.signno,0,8) =  s.signno ")
				   .append(" where (s.signdesc like '%註銷%' or s.signdesc like '%調整%') and c.datastate='1'");
					if (this.getQ_enterorgName() != null && !"".equals(this.getQ_enterorgName())) {
						execSQL.append(" and c.enterorg = " + Common.sqlChar(this.getQ_enterorg()));
					}
			execSQL.append(" order by propertyno , serialno ");
				
			System.out.println(execSQL);
			//#end
			ResultSet rs = db.querySQL_NoChange(execSQL.toString());
			
			while(rs.next()) {
				Object[] data = new Object[columns.length];

				data[0] = Common.get(rs.getString("propertyno"));
				data[1] = Common.get(rs.getString("serialno"));
				data[2] = Common.get(rs.getString("propertyname1"));
				data[3] = Common.get(rs.getString("landsignno"));
				data[4] = Common.get(rs.getString("signno"));	
				data[5] = Common.get(rs.getString("doorplate"));	
				data[6] = Common.get(rs.getString("keepunit"));	
				data[7] = Common.get(rs.getString("keeper"));	
				data[8] = Common.get(rs.getString("memo"));	
				data[9] = Common.get(rs.getString("differencekind"));	
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
