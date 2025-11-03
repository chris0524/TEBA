/*
*<br>程式目的：物品折舊沖回明細表
*<br>程式代號：untne061r
*<br>撰寫日期：111/06/06
*<br>程式作者：Kasper.Lee
*<br>--------------------------------------------------------
*<br>修改作者　　修改日期　　　修改目的
*<br>--------------------------------------------------------
*/

package unt.ne;

import java.sql.ResultSet;
import java.util.Vector;

import javax.swing.table.DefaultTableModel;

import util.Common;
import util.Database;
import util.Datetime;
import util.SuperBean;

public class UNTNE061R extends SuperBean {
	Object[] columns = new Object[]{"f0", "f1", "f2", "f3", "f4", "f5", 
											"f6", "f7", "f8", "f9", "f10", "f11", 
											"f12", "f13", "f14", "f15", "f16", "f17"};
	private String q_enterorg;
	private String q_enterorgName;
	private String q_proofYear;
	private String q_proofDoc;
	private String q_proofNo;
	private String q_reportType;		// 報表類型

	public String getQ_enterorg() { return checkGet(q_enterorg); }
	public void setQ_enterorg(String q_enterorg) { this.q_enterorg = checkSet(q_enterorg); }
	public String getQ_enterorgName() {	return checkGet(q_enterorgName); }
	public void setQ_enterorgName(String q_enterorgName) { this.q_enterorgName = checkSet(q_enterorgName); }
	public String getQ_proofYear() {return checkGet(q_proofYear);}
	public void setQ_proofYear(String q_proofYear) {this.q_proofYear = checkSet(q_proofYear);}
	public String getQ_proofDoc(){ return checkGet(q_proofDoc); }
	public void setQ_proofDoc(String s){ q_proofDoc=checkSet(s); }
	public String getQ_proofNo(){ return checkGet(q_proofNo); }
	public void setQ_proofNo(String s){ q_proofNo=checkSet(s); }
	public String getQ_reportType() {	return checkGet(q_reportType);}
	public void setQ_reportType(String q_reportType) { this.q_reportType = checkSet(q_reportType); }


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
			StringBuilder sb = new StringBuilder();
			
			sb.append(" SELECT ROW_NUMBER() OVER(PARTITION BY x.enterorg ORDER BY propertyno1) AS Seq,        ")
				.append("        x.propertyno1,                                                               ")
				.append("        x.propertyno2,                                                               ")
				.append("        x.scrapvalue,                                                                ")
				.append("        x.originallimityear,                                                         ")
				.append("        x.otherlimityear,                                                            ")
				.append("        x.buydate,                                                                   ")
				.append("        x.enterdate,                                                                 ")
				.append("        x.originalbv,                                                                ")
				.append("        x.netvalue,                                                                  ")
				.append("        x.oldmonthdepr,                                                              ")
				.append("        x.oldaccumdepr,                                                             ")
				.append("        x.oldmonthdepr1,                                                             ")
				.append("        x.originalnetvalue,                                                          ")
				.append("        x.originalaccumdepr,                                                                ")
				.append("        x.monthdepr,                                                                 ")
				.append("        x.monthdepr1,                                                                ")
				.append("        x.apportionmonth                                                             ")
				.append(" FROM                                                                                ")
				.append(" (                                                                                   ")
				.append("     SELECT a.enterorg,                                                              ")
				.append("            b.proofyear,                                                             ")
				.append("            b.proofdoc,                                                              ")
				.append("            b.proofno,                                                               ")
				.append("            a.propertyno + '/' + a.serialno AS 'propertyno1',                        ")
				.append("            c.propertyno + '/' + c.serialno AS 'propertyno2',                        ")
				.append("            0 AS scrapvalue,                                                         ")
				.append("            a.originallimityear,                                                     ")
				.append("            a.otherlimityear,                                                        ")
				.append("            d.buydate,                                                               ")
				.append("     (                                                                                ")
				.append("         SELECT z.enterdate                                                           ")
				.append("         FROM UNTNE_NONEXPDETAIL z                                                    ")
				.append("         WHERE z.enterorg = a.enterorg                                                ")
				.append("               AND z.ownership = a.ownership                                          ")
				.append("               AND z.differencekind = '110'                                           ")
				.append("               AND z.propertyno = a.oldpropertyno                                     ")
				.append("               AND z.serialno = a.oldserialno                                         ")
				.append("     ) AS 'enterdate',                                                                ")
				.append("     (                                                                               ")
				.append("         SELECT z.originalbv                                                         ")
				.append("         FROM UNTNE_NONEXPDETAIL z                                                  ")
				.append("         WHERE z.enterorg = a.enterorg                                               ")
				.append("               AND z.ownership = a.ownership                                         ")
				.append("               AND z.differencekind = '110'                                           ")
				.append("               AND z.propertyno = a.oldpropertyno                                    ")
				.append("               AND z.serialno = a.oldserialno                                        ")
				.append("     ) AS 'originalbv',                                                              ")
				.append("            0 AS netvalue,                                                           ")
				.append("            0 AS oldmonthdepr,                                                       ")
				.append("            0 AS oldaccumdepr,                                                      ")
				.append("            0 AS oldmonthdepr1,                                                      ")
				.append("            a.originalbv AS 'originalnetvalue',                                      ")
				.append("            0 AS originalaccumdepr,                                                         ")
				.append("            0 AS monthdepr,                                                          ")
				.append("            0 AS monthdepr1,                                                         ")
				.append("            0 AS apportionmonth                                                      ")
				.append("     FROM UNTNE_NONEXPDETAIL a                                                       ")
				.append("          INNER JOIN UNTNE_ADDPROOF b ON a.enterorg = b.enterorg                     ")
				.append("                                         AND a.caseno = b.caseno                     ")
				.append("          INNER JOIN UNTNE_REDUCEDETAIL c ON a.enterorg = c.enterorg                 ")
				.append("                                             AND a.ownership = c.ownership           ")
				.append("                                             AND c.differencekind = '110'            ")
				.append("                                             AND a.oldpropertyno = c.propertyno      ")
				.append("                                             AND a.oldserialno = c.serialno          ")
				.append("                                             AND b.reducecaseno = c.caseno           ")
				.append("          INNER JOIN UNTNE_NONEXP d ON a.enterorg = d.enterorg                       ")
				.append("                                        AND a.ownership = d.ownership                ")
				.append("                                        AND a.differencekind = d.differencekind      ")
				.append("                                        AND a.propertyno = d.propertyno              ")
				.append("                                        AND a.lotno = d.lotno                        ")
				.append("     WHERE ISNULL(b.reducecaseno, '') != ''                                          ")
				.append("          AND a.datastate = '1'                                                       ")
				.append(" ) x                                                                                 ")
				.append(" WHERE x.enterorg = ").append(Common.sqlChar(this.getQ_enterorg()))
				.append("       AND x.proofyear = ").append(Common.sqlChar(this.getQ_proofYear()))
				.append("       AND x.proofdoc = ").append(Common.sqlChar(this.getQ_proofDoc()))
				.append("       AND x.proofno = ").append(Common.sqlChar(this.getQ_proofNo()));
			
			//#end
			ResultSet rs = db.querySQL_NoChange(sb.toString());
			
			while(rs.next()) {
				Object[] data = new Object[columns.length];

				data[0] = Common.get(rs.getString("seq"));
				data[1] = Common.get(rs.getString("propertyno2"));
				data[2] = Common.get(rs.getString("propertyno1"));
				data[3] = Common.getInt(rs.getString("scrapvalue"));			
				data[4] = Common.get(rs.getString("originallimityear"));	
				data[5] = Common.get(rs.getString("otherlimityear"));
				data[6] = Common.get(Datetime.changeTaiwanYYMMDD(rs.getString("buydate"), "1"));
				data[7] = Common.get(Datetime.changeTaiwanYYMMDD(rs.getString("enterdate"), "1"));
				data[8] = Common.getInt(rs.getString("originalbv"));			
				data[9] = Common.getInt(rs.getString("netvalue"));	
				data[10] = Common.getInt(rs.getString("oldmonthdepr"));
				data[11] = Common.getInt(rs.getString("oldaccumdepr"));
				data[12] = Common.getInt(rs.getString("oldmonthdepr1"));
				data[13] = Common.getInt(rs.getString("originalnetvalue"));			
				data[14] = Common.getInt(rs.getString("originalaccumdepr"));	
				data[15] = Common.getInt(rs.getString("monthdepr"));
				data[16] = Common.get(rs.getString("apportionmonth"));
				data[17] = Common.getInt(rs.getString("monthdepr1"));
				
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
