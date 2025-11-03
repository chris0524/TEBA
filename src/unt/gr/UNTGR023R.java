/*
*<br>程式目的：財產折舊月報表 
*<br>程式代號：untgr023r
*<br>撰寫日期：95/07/11
*<br>程式作者：Cherry
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

public class UNTGR023R extends SuperBean{
	Object[] columns = new Object[]{"f0","f1","f2","f3","f4"};
	
	private String q_enterorg;
	private String q_enterorgName;
	private String q_ownership;
	private String q_differencekind;
	private String q_year;
	private String q_month;
	private String q_valuable;
	private String q_verify;
	private String q_reportType;						// 報表類型
	
	public String getQ_reportType() { return checkGet(q_reportType); }
	public void setQ_reportType(String q_reportType) { this.q_reportType = checkGet(q_reportType); }
	public String getQ_enterorg() {return checkGet(q_enterorg);}
	public void setQ_enterorg(String q_enterorg) {this.q_enterorg = checkSet(q_enterorg);}
	public String getQ_enterorgName() {	return checkGet(q_enterorgName); }
	public void setQ_enterorgName(String q_enterorgName) { this.q_enterorgName = checkSet(q_enterorgName); }
	public String getQ_ownership() {return checkGet(q_ownership);}
	public void setQ_ownership(String q_ownership) {this.q_ownership = checkSet(q_ownership);}
	public String getQ_differencekind() {return checkGet(q_differencekind);}
	public void setQ_differencekind(String q_differencekind) {this.q_differencekind = checkSet(q_differencekind);}
	public String getQ_year() {return checkGet(q_year);}
	public void setQ_year(String q_year) {this.q_year = checkSet(q_year);}
	public String getQ_month() {return checkGet(q_month);}
	public void setQ_month(String q_month) {this.q_month = checkSet(q_month);}
	public String getQ_valuable() { return checkGet(q_valuable); }
	public void setQ_valuable(String q_valuable) { this.q_valuable = checkSet(q_valuable); }
	public String getQ_verify() { return checkGet(q_verify); }
	public void setQ_verify(String q_verify) { this.q_verify = checkSet(q_verify); }

	String userName;
	public String getUserName() { return checkGet(userName); }
	public void setUserName(String s) { userName = checkSet(s); }

	public DefaultTableModel getResultModel() throws Exception{
		
		DefaultTableModel model = null;
		Database db = new Database();
		
		Map<String,String> differenceKindMap = TCGHCommon.getSysca_Code("DFK");//財產區分別名稱
		
		String yearOfYYYY = new Datetime().changeTaiwan(this.getQ_year(),"2");
		String startDate = yearOfYYYY + this.getQ_month() + "01";
		String endDate = yearOfYYYY + this.getQ_month() + new Datetime().getLastDayOfMonth(yearOfYYYY + this.getQ_month());
		
		try{
			Vector rowData = new Vector();
			model = new javax.swing.table.DefaultTableModel();
			//#region SQL
			String execSQL = "DECLARE @enterorg AS NVARCHAR(20) SET @enterorg = " + Common.sqlChar(this.getQ_enterorg()) + "; "
					+ "DECLARE @ownership AS NVARCHAR(10) SET @ownership = " + Common.sqlChar(this.getQ_ownership()) + "; "
					+ "DECLARE @differencekind AS NVARCHAR(10) SET @differencekind = " + Common.sqlChar(this.getQ_differencekind()) + "; "
					+ "DECLARE @datestart AS NVARCHAR(10) SET @datestart = " + Common.sqlChar(startDate) + "; "
					+ "DECLARE @dateend AS NVARCHAR(10) SET @dateend = " + Common.sqlChar(endDate) + "; "
					+ "DECLARE @verify AS NVARCHAR(2) SET @verify = " + Common.sqlChar(this.getQ_verify()) + "; "
					+ "DECLARE @valuable AS NVARCHAR(2) SET @valuable = " + Common.sqlChar(this.getQ_valuable()) + "; "
					+ "with DETAIL as ("
					
					+ "select "
					+ "'1-L' as 'typeandsort','土地' as 'typename',sum(bookvalue) as bookvalue,'0' as oldAccumDepr,sum(netvalue) as netvalue "
					+ "from UNTLA_REDUCEDETAIL "
					+ "where enterorg = @enterorg and ownership =@ownership and differencekind = @differencekind and reducedate >= @datestart and reducedate <= @dateend "
					+ "and verify = @verify and valuable = @valuable "
					
					+ "union "
					
					+ "select "
					+ "'2-F' as 'typeandsort','土地改良物' as 'typename',sum(bookvalue) as bookvalue,SUM(oldaccumdepr) as oldAccumDepr,sum(netvalue) as netvalue "
					+ "from UNTRF_REDUCEDETAIL "
					+ "where enterorg = @enterorg and ownership =@ownership and differencekind = @differencekind and reducedate >= @datestart and reducedate <= @dateend "
					+ "and verify = @verify and valuable = @valuable "
					
					+ "union "

					+ "select "
					+ "'3-B' as 'typeandsort','房屋建築及設備' as 'typename',sum(bookvalue) as bookvalue,SUM(oldaccumdepr) as oldAccumDepr,sum(netvalue) as netvalue "
					+ "from UNTBU_REDUCEDETAIL "
					+ "where enterorg = @enterorg and ownership =@ownership and differencekind = @differencekind and reducedate >= @datestart and reducedate <= @dateend "
					+ "and verify = @verify and valuable = @valuable "
					
					+ "union "

					+ "select "
					+ "'8-P' as 'typeandsort','有價證券' as 'typename',sum(bookvalue) as bookvalue,'0' as oldAccumDepr,sum(bookvalue) as netvalue "
					+ "from UNTVP_REDUCEPROOF "
					+ "where enterorg = @enterorg and ownership =@ownership and differencekind = @differencekind and adjustdate >= @datestart and adjustdate <= @dateend "
					+ "and verify = @verify "
					
					+ "union "

					+ "select "
					+ "'9-R' as 'typeandsort','權利' as 'typename',sum(bookvalue) as bookvalue,sum(oldaccumdepr) as oldAccumDepr,sum(netvalue) as netvalue "
					+ "from UNTRT_REDUCEPROOF "
					+ "where enterorg = @enterorg and ownership =@ownership and differencekind = @differencekind and reducedate >= @datestart and reducedate <= @dateend "
					+ "and verify = @verify "
					
					+ "union "

					+ "select "
					+ "'31-M' as 'typeandsort', "
					+ "'機械及設備' as 'typename', "
					+ "sum(oldbookvalue) as bookvalue,sum(oldaccumdepr) as oldAccumDepr,sum(oldnetvalue) as netvalue "
					+ "from UNTMP_REDUCEDETAIL "
					+ "where enterorg = @enterorg and ownership =@ownership and differencekind = @differencekind and reducedate >= @datestart and reducedate <= @dateend and substring(propertyno, 1, 1) = '3' "
					+ "and verify = @verify and valuable = @valuable "
					
					+ "union "

					+ "select "
					+ "'41-M' as 'typeandsort', "
					+ "'交通及運輸設備' as 'typename', "
					+ "sum(oldbookvalue) as bookvalue,sum(oldaccumdepr) as oldAccumDepr,sum(oldnetvalue) as netvalue "
					+ "from UNTMP_REDUCEDETAIL "
					+ "where enterorg = @enterorg and ownership =@ownership and differencekind = @differencekind and reducedate >= @datestart and reducedate <= @dateend and substring(propertyno, 1, 1) = '4' "
					+ "and verify = @verify and valuable = @valuable "
					
					+ "union "

					+ "select "
					+ "'51-M' as 'typeandsort', "
					+ "'雜項設備' as 'typename', "
					+ "sum(oldbookvalue) as bookvalue,sum(oldaccumdepr) as oldAccumDepr,sum(oldnetvalue) as netvalue "
					+ "from UNTMP_REDUCEDETAIL "
					+ "where enterorg = @enterorg and ownership =@ownership and differencekind = @differencekind and reducedate >= @datestart and reducedate <= @dateend and substring(propertyno, 1, 1) = '5' "
					+ "and verify = @verify and valuable = @valuable "

					+ "union "
					
					+ "select "
					+ "'1-L' as 'typeandsort','土地' as 'typename',sum(reducebookvalue) as bookvalue,'0' as oldAccumDepr,sum(reducenetvalue) as netvalue "
					+ "from UNTLA_ADJUSTDETAIL "
					+ "where enterorg = @enterorg and ownership =@ownership and differencekind = @differencekind and adjustdate >= @datestart and adjustdate <= @dateend "
					+ "and verify = @verify and valuable = @valuable "
					
					+ "union "
					
					+ "select "
					+ "'2-F' as 'typeandsort','土地改良物' as 'typename',sum(reducebookvalue) as bookvalue,SUM(reduceaccumdepr) as oldAccumDepr,sum(reducenetvalue) as netvalue "
					+ "from UNTRF_ADJUSTDETAIL "
					+ "where enterorg = @enterorg and ownership =@ownership and differencekind = @differencekind and adjustdate >= @datestart and adjustdate <= @dateend "
					+ "and verify = @verify and valuable = @valuable "
					
					+ "union "

					+ "select "
					+ "'3-B' as 'typeandsort','房屋建築及設備' as 'typename',sum(reducebookvalue) as bookvalue,SUM(reduceaccumdepr) as oldAccumDepr,sum(reducenetvalue) as netvalue "
					+ "from UNTBU_ADJUSTDETAIL "
					+ "where enterorg = @enterorg and ownership =@ownership and differencekind = @differencekind and adjustdate >= @datestart and adjustdate <= @dateend "
					+ "and verify = @verify and valuable = @valuable "
					
					+ "union "

					+ "select "
					+ "'8-P' as 'typeandsort','有價證券' as 'typename',sum(reducebookvalue) as bookvalue,'0' as oldAccumDepr,sum(reducebookvalue) as netvalue "
					+ "from UNTVP_ADJUSTPROOF "
					+ "where enterorg = @enterorg and ownership =@ownership and differencekind = @differencekind and adjustdate >= @datestart and adjustdate <= @dateend "
					+ "and verify = @verify "
					
					+ "union "

					+ "select "
					+ "'9-R' as 'typeandsort','權利' as 'typename',sum(reducebookvalue) as bookvalue,sum(reduceaccumdepr) as oldAccumDepr,sum(reducenetvalue) as netvalue "
					+ "from UNTRT_ADJUSTPROOF "
					+ "where enterorg = @enterorg and ownership =@ownership and differencekind = @differencekind and adjustdate >= @datestart and adjustdate <= @dateend "
					+ "and verify = @verify "
					
					+ "union "

					+ "select "
					+ "'31-M' as 'typeandsort', "
					+ "'機械及設備' as 'typename', "
					+ "sum(reducebookvalue) as bookvalue,sum(reduceaccumdepr) as oldAccumDepr,sum(reducenetvalue) as netvalue "
					+ "from UNTMP_ADJUSTDETAIL "
					+ "where enterorg = @enterorg and ownership =@ownership and differencekind = @differencekind and adjustdate >= @datestart and adjustdate <= @dateend and substring(propertyno, 1, 1) = '3' "
					+ "and verify = @verify and valuable = @valuable "
					
					+ "union "

					+ "select "
					+ "'41-M' as 'typeandsort', "
					+ "'交通及運輸設備' as 'typename', "
					+ "sum(reducebookvalue) as bookvalue,sum(reduceaccumdepr) as oldAccumDepr,sum(reducenetvalue) as netvalue "
					+ "from UNTMP_ADJUSTDETAIL "
					+ "where enterorg = @enterorg and ownership =@ownership and differencekind = @differencekind and adjustdate >= @datestart and adjustdate <= @dateend and substring(propertyno, 1, 1) = '4' "
					+ "and verify = @verify and valuable = @valuable "
					
					+ "union "

					+ "select "
					+ "'51-M' as 'typeandsort', "
					+ "'雜項設備' as 'typename', "
					+ "sum(reducebookvalue) as bookvalue,sum(reduceaccumdepr) as oldAccumDepr,sum(reducenetvalue) as netvalue "
					+ "from UNTMP_ADJUSTDETAIL "
					+ "where enterorg = @enterorg and ownership =@ownership and differencekind = @differencekind and adjustdate >= @datestart and adjustdate <= @dateend and substring(propertyno, 1, 1) = '5' "
					+ "and verify = @verify and valuable = @valuable "

					+ ")"
					+ "SELECT typeandsort,typename,SUM(bookvalue) AS bookvalue,SUM(oldAccumDepr) AS oldAccumDepr,SUM(netvalue) AS netvalue FROM DETAIL "
					+ "GROUP BY typeandsort,typename "
					+ "ORDER BY typeandsort";
			//#end
			System.out.println(execSQL);
			ResultSet rs = db.querySQL_NoChange(execSQL);
			
			while(rs.next()){
				Object[] data = new Object[columns.length];
				
				data[0] = Common.get(differenceKindMap.get(this.getQ_differencekind()));
				
				data[1] = Common.get(rs.getString("typename"));
				data[2] = Common.getNumeric(rs.getString("bookvalue"));
				data[3] = Common.getNumeric(rs.getString("oldAccumDepr"));
				data[4] = Common.getNumeric(rs.getString("netvalue"));
				
				rowData.addElement(data);
			}
			
			Object[][] datas = new Object[0][0];
			datas = (Object[][])rowData.toArray(datas);
			model.setDataVector(datas, columns);
			
		}catch(Exception x){
			x.printStackTrace();
		}finally{
			db.closeAll();
		}
		
		return model;
	}
	
}
