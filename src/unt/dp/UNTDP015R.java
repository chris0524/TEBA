package unt.dp;

import java.sql.ResultSet;
import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Map;
import java.util.Vector;

import javax.swing.table.DefaultTableModel;

import unt.ch.UNTCH_Tools;
import util.Common;
import util.Database;
import util.Datetime;
import util.TCGHCommon;

/**
 *<br/>程式目的：財產折舊攤提月報表(基金財產) 
 *<br/>程式代號：untdp015r
 *<br/>撰寫日期：94/12/12
 *<br/>程式作者：Chris
 *<br/>--------------------------------------------------------
 *<br/>修改作者　　修改日期　　　修改目的
 *<br/>--------------------------------------------------------
*/
public class UNTDP015R extends UNTDP001F {

	private String enterOrg;
	private String organaName;
	private String deprUnitName;
	private String deprUnit;
	private String deprUnitCB;
	private String deprPark;
	private String q_enterOrg;
	private String q_deprYM;
	private String q_differenceKind;
	private String q_originalDeprPark;
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
	public String getDeprPark(){ return checkGet(deprPark); }
	public void setDeprPark(String s){ deprPark=checkSet(s); }
	public String getQ_enterOrg(){ return checkGet(q_enterOrg); }
	public void setQ_enterOrg(String s){ q_enterOrg=checkSet(s); }
	public String getQ_deprYM(){ return checkGet(q_deprYM); }
	public void setQ_deprYM(String s){ q_deprYM=checkSet(s); }
	public String getQ_differenceKind(){ return checkGet(q_differenceKind); }
	public void setQ_differenceKind(String s){ q_differenceKind=checkSet(s); }
	public String getQ_originalDeprPark(){ return checkGet(q_originalDeprPark); }
	public void setQ_originalDeprPark(String s){ q_originalDeprPark=checkSet(s); }
	public String getQ_groupType() { return checkGet(q_groupType); }
	public void setQ_groupType(String q_groupType) { this.q_groupType = checkSet(q_groupType); }

	private String isOrganManager;
	private String isAdminManager;
	private String organID;
	public String getOrganID() { return checkGet(organID); }
	public void setOrganID(String s) { organID = checkSet(s); }
	public String getIsOrganManager() { return checkGet(isOrganManager); }
	public void setIsOrganManager(String s) { isOrganManager = checkSet(s); }
	public String getIsAdminManager() { return checkGet(isAdminManager); }
	public void setIsAdminManager(String s) { isAdminManager = checkSet(s); } 
	
	/**
	 * 預設是以 q_groupType : A (部門別+會計科目) 
	 * @return
	 */
	private String getSelectVariable() {
		String defaultText = "a.enterorg as enterorg, x.organaname as organaname, a.deprpark, p.deprparkname as deprparkname , a.deprunit as deprunit, z.deprunitname as deprunitname, a.depraccounts, f.depraccountsname,";
		if ("B".equals(this.getQ_groupType())) { // groupType B (部門別+部門別單位+會計科目)
			defaultText += "a.deprunit1,";
		}
		return defaultText;
	}
	
	/**
	 * 預設是以 q_groupType : A (部門別+會計科目) 
	 * @return
	 */
	private String getGroupbyVariable() {
		String defaultText = " a.enterorg, x.organaname, a.deprpark, p.deprparkname, a.deprunit, z.deprunitname, a.depraccounts, f.depraccountsname";
		if ("B".equals(this.getQ_groupType())) { // groupType B (部門別+部門別單位+會計科目)
			defaultText += ", a.deprunit1";
		}
		return defaultText;
	}
	
	public DefaultTableModel getResultModel() throws Exception {
		UNTCH_Tools ul = new UNTCH_Tools();
		UNTDP015R obj = this;
		DefaultTableModel model = new javax.swing.table.DefaultTableModel();
		Database db = new Database();
		//Map<String,String> deprunitMap = TCGHCommon.getSysca_DeprUnit(this.getQ_enterOrg());		//部門別名稱
		Map<String,String> deprunit1Map = TCGHCommon.getSysca_DeprUnit1(this.getQ_enterOrg());		//部門別單位名稱
		
		try{
			if(q_deprYM != null){
				String[] columns = new String[] {"head01", "head02", "head03", "head04", "head05",
						                         "detail01", "detail02", "detail03", "detail04", "detail05",
						                         "detail06", "detail07", "detail08", "sum"};
				if ("B".equals(this.getQ_groupType())) { // groupType B (部門別+部門別單位+會計科目)  多顯示部門別單位
					int oriLength = columns.length;
					columns = Arrays.copyOf(columns, oriLength + 1);
					columns[oriLength] = "detail09";
				}
				String tempDeprpark="";
				if(getQ_originalDeprPark().equals(null) || getQ_originalDeprPark().equals("")){
				}else{
					tempDeprpark=" and a.deprpark = " + Common.sqlChar(getQ_originalDeprPark()) ;
				}
				String execSQL=" select " + this.getSelectVariable() + 
								" SUM(CASE WHEN a.propertytype = '2' THEN CASE WHEN a.deprunitcb = 'Y' THEN ISNULL(a.scaledmonthdepr, 0) ELSE ISNULL(a.monthdepr, 0) END ELSE 0 end) AS d1," +
								" SUM(CASE WHEN a.propertytype = '3' THEN CASE WHEN a.deprunitcb = 'Y' THEN ISNULL(a.scaledmonthdepr, 0) ELSE ISNULL(a.monthdepr, 0) END ELSE 0 end) AS d2," +
								" SUM(CASE WHEN a.propertytype = '4' THEN CASE WHEN a.deprunitcb = 'Y' THEN ISNULL(a.scaledmonthdepr, 0) ELSE ISNULL(a.monthdepr, 0) END ELSE 0 end) AS d3," +
								" SUM(CASE WHEN a.propertytype = '5' THEN CASE WHEN a.deprunitcb = 'Y' THEN ISNULL(a.scaledmonthdepr, 0) ELSE ISNULL(a.monthdepr, 0) END ELSE 0 end) AS d4," +
								" SUM(CASE WHEN a.propertytype = '6' THEN CASE WHEN a.deprunitcb = 'Y' THEN ISNULL(a.scaledmonthdepr, 0) ELSE ISNULL(a.monthdepr, 0) END ELSE 0 end) AS d5," +
								" SUM(CASE WHEN a.propertytype = '8' THEN CASE WHEN a.deprunitcb = 'Y' THEN ISNULL(a.scaledmonthdepr, 0) ELSE ISNULL(a.monthdepr, 0) END ELSE 0 end) AS d6 " +
								//" (select ISNULL(SUM(ISNULL(scaledmonthdepr,0)),0)  from UNTDP_MONTHDEPR where propertytype='2' and enterorg=a.enterorg and deprunit = a.deprunit and  deprunitcb = 'Y' and differencekind='120'"+tempDeprpark+" AND deprym = a.deprym) + (select ISNULL(SUM(ISNULL(monthdepr,0)),0)  from UNTDP_MONTHDEPR where propertytype='2' and enterorg=a.enterorg and deprpark=a.deprpark and deprunit = a.deprunit and deprunit1 = a.deprunit1 and deprunitcb = 'N' and differencekind='120'"+tempDeprpark+" AND deprym = a.deprym)  as d1," +
								//" (select ISNULL(SUM(ISNULL(scaledmonthdepr,0)),0)  from UNTDP_MONTHDEPR where propertytype='3' and enterorg=a.enterorg and deprunit = a.deprunit and  deprunitcb = 'Y' and differencekind='120'"+tempDeprpark+" AND deprym = a.deprym) + (select ISNULL(SUM(ISNULL(monthdepr,0)),0)  from UNTDP_MONTHDEPR where propertytype='3' and enterorg=a.enterorg and deprpark=a.deprpark and deprunit = a.deprunit and deprunit1 = a.deprunit1 and deprunitcb = 'N' and differencekind='120'"+tempDeprpark+" AND deprym = a.deprym)  as d2," +
								//" (select ISNULL(SUM(ISNULL(scaledmonthdepr,0)),0)  from UNTDP_MONTHDEPR where propertytype='4' and enterorg=a.enterorg and deprunit = a.deprunit and  deprunitcb = 'Y' and differencekind='120'"+tempDeprpark+" AND deprym = a.deprym) + (select ISNULL(SUM(ISNULL(monthdepr,0)),0)  from UNTDP_MONTHDEPR where propertytype='4' and enterorg=a.enterorg and deprpark=a.deprpark and deprunit = a.deprunit and deprunit1 = a.deprunit1 and deprunitcb = 'N' and differencekind='120'"+tempDeprpark+" AND deprym = a.deprym)  as d3," +
								//" (select ISNULL(SUM(ISNULL(scaledmonthdepr,0)),0)  from UNTDP_MONTHDEPR where propertytype='5' and enterorg=a.enterorg and deprunit = a.deprunit and  deprunitcb = 'Y' and differencekind='120'"+tempDeprpark+" AND deprym = a.deprym) + (select ISNULL(SUM(ISNULL(monthdepr,0)),0)  from UNTDP_MONTHDEPR where propertytype='5' and enterorg=a.enterorg and deprpark=a.deprpark and deprunit = a.deprunit and deprunit1 = a.deprunit1 and deprunitcb = 'N' and differencekind='120'"+tempDeprpark+" AND deprym = a.deprym)  as d4," +
								//" (select ISNULL(SUM(ISNULL(scaledmonthdepr,0)),0)  from UNTDP_MONTHDEPR where propertytype='6' and enterorg=a.enterorg and deprunit = a.deprunit and  deprunitcb = 'Y' and differencekind='120'"+tempDeprpark+" AND deprym = a.deprym) + (select ISNULL(SUM(ISNULL(monthdepr,0)),0)  from UNTDP_MONTHDEPR where propertytype='6' and enterorg=a.enterorg and deprpark=a.deprpark and deprunit = a.deprunit and deprunit1 = a.deprunit1 and deprunitcb = 'N' and differencekind='120'"+tempDeprpark+" AND deprym = a.deprym)  as d5 " +
								" from UNTDP_MONTHDEPR a "+
								" left join SYSCA_ORGAN x on a.enterorg = x.organid "+
								" left join SYSCA_DEPRUNIT z on  a.enterorg = z.enterorg and a.deprunit=z.deprunitno "+
								" left join SYSCA_DEPRPARK p on a.enterorg=p.enterorg and a.deprpark=p.deprparkno "+
								" left join SYSCA_DEPRACCOUNTS f on a.enterorg = f.enterorg and a.depraccounts = f.depraccountsno "+
								" where 1=1 " +	
								"";
		
				if (!"".equals(getQ_enterOrg())) {
					execSQL +=" and a.enterorg = " + Common.sqlChar(getQ_enterOrg());
				} else {
					if (!getIsAdminManager().equalsIgnoreCase("Y")) {
						if ("Y".equals(getIsOrganManager())) {
							//execSQL += " and a.enterOrg like '" + getOrganID().substring(0,5) + "%' ";
							execSQL += " and ( a.enterorg = "+ Common.sqlChar(getOrganID()) +" or a.enterorg in ( select organid from SYSCA_ORGAN where organsuperior = "+ Common.sqlChar(getOrganID()) +" ) ) ";
						} else {
							execSQL += " and a.enterorg = " + Common.sqlChar(getOrganID());
						}
					}
				}
				if (!"".equals(getQ_deprYM())) 
					execSQL +=" and a.deprym = " + Common.sqlChar(ul._transToCE_Year(getQ_deprYM()));
				if (!"".equals(getQ_differenceKind())) 
					execSQL +=" and a.differencekind = " + Common.sqlChar(getQ_differenceKind());
				if (!"".equals(getQ_originalDeprPark())) 
					execSQL +=" and a.deprpark = " + Common.sqlChar(getQ_originalDeprPark());
				
				execSQL += " GROUP BY " + this.getGroupbyVariable();
				execSQL += " order by enterorg,deprpark,deprunit";
				if ("B".equals(this.getQ_groupType())) {
					execSQL += ",deprunit1";
				}
				execSQL += ",depraccounts";
				
				System.out.println("=> " + execSQL);		
				ResultSet rs = db.querySQL(execSQL);
				Vector<Object> rowData = new Vector<Object>();
				Calendar cal = Calendar.getInstance();
				cal.set(Calendar.YEAR,Integer.parseInt(ul._transToCE_Year(q_deprYM).substring(0, 4)));
				cal.set(Calendar.MONTH, Integer.parseInt(ul._transToCE_Year(q_deprYM).substring(4, 6)));
				cal.set(Calendar.DAY_OF_MONTH, 1);
				cal.add(Calendar.DAY_OF_MONTH, -1); 
				int lastDate = cal.get(Calendar.DAY_OF_MONTH);
				cal.set(Calendar.DAY_OF_MONTH, 1);
				int firstDate = cal.get(Calendar.DAY_OF_MONTH);
				long sum =0;
				long sum1 =0;
				long sum2 =0;
				long sum3 =0;
				long sum4 =0;
				long sum5 =0;
				long sum6 =0;
				long sum7 =0;
				long tSum1 = 0;
				long tSum2 = 0;
				long tSum3 = 0;
				long tSum4 = 0;
				long tSum5 = 0;
				long tSum6 = 0;
				long tSum7 = 0;
				String currentDeprPark = null;
				String datetime = Datetime.getYYYMMDD();
				String tmpOrganaName = "";
				while(rs.next()){
					obj.setEnterOrg(checkGet(rs.getString("enterOrg")));
					obj.setOrganaName(checkGet(rs.getString("organaName")));
					obj.setDeprUnitName(checkGet(rs.getString("deprUnitName")));
					obj.setDeprUnit(checkGet(rs.getString("deprUnit")));
					obj.setDeprPark(checkGet(rs.getString("deprParkName")));
					
					String tmpDeprPark = Common.get(rs.getString("deprParkName"));
					tmpOrganaName = Common.get(rs.getString("organaName"));
					if (currentDeprPark == null) {
						currentDeprPark = tmpDeprPark;
					} else if (!tmpDeprPark.equals(currentDeprPark)) {
						// 加入合計
						Object[] sumRow = new Object[columns.length];
						sumRow[0] = tmpOrganaName;
						sumRow[1] = "  中華民國"+getQ_deprYM().substring(0, 3)+" 年 "+getQ_deprYM().substring(3, 5)+" 月 "+firstDate+"  日起至   ";
						sumRow[2] = getQ_deprYM().substring(0, 3)+" 年 "+getQ_deprYM().substring(3, 5)+" 月 "+lastDate+" 日止";
						sumRow[3] = currentDeprPark;
						sumRow[4] = datetime.substring(0,3) + "年" + datetime.substring(3,5) + "月" + datetime.substring(5)+ "日";
						sumRow[5] = "合計";
						sumRow[6] = "";
						sumRow[7] = Common.valueFormat(String.valueOf(sum1));
						sumRow[8] = Common.valueFormat(String.valueOf(sum2));
						sumRow[9] = Common.valueFormat(String.valueOf(sum3));
						sumRow[10] = Common.valueFormat(String.valueOf(sum4));
						sumRow[11] = Common.valueFormat(String.valueOf(sum5));
						sumRow[12] = Common.valueFormat(String.valueOf(sum6));
						sumRow[13] = Common.valueFormat(String.valueOf(sum7));
						rowData.add(sumRow);
						currentDeprPark = tmpDeprPark;
						sum1 = 0;
						sum2 = 0;
						sum3 = 0;
						sum4 = 0;
						sum5 = 0;
						sum6 = 0;
						sum7 = 0;
					}
		
					
					Object[] data = new Object[columns.length];
					data[0] = tmpOrganaName;
					data[1] = "  中華民國"+getQ_deprYM().substring(0, 3)+" 年 "+getQ_deprYM().substring(3, 5)+" 月 "+firstDate+"  日起至   ";
					data[2] = getQ_deprYM().substring(0, 3)+" 年 "+getQ_deprYM().substring(3, 5)+" 月 "+lastDate+" 日止";
					data[3] = tmpDeprPark;
					//印表日期
					data[4] = datetime.substring(0,3) + "年" + datetime.substring(3,5) + "月" + datetime.substring(5)+ "日";
					data[5] = checkGet(rs.getString("deprUnitName"));
					data[6] = Common.get(rs.getString("depraccounts")) + Common.get(rs.getString("depraccountsname"));
					String d1 = rs.getString("d1");
					String d2 = rs.getString("d2");
					String d3 = rs.getString("d3");
					String d4 = rs.getString("d4");
					String d5 = rs.getString("d5");
					String d6 = rs.getString("d6");
					
					data[7] = Common.valueFormat(d1);
					data[8] = Common.valueFormat(d2);
					data[9] = Common.valueFormat(d3);
					data[10] = Common.valueFormat(d4);
					data[11] = Common.valueFormat(d5);
					data[12] = Common.valueFormat(d6);
					
					sum =  Long.valueOf(d1) + Long.valueOf(d2) + Long.valueOf(d3) + Long.valueOf(d4) + Long.valueOf(d5) + Long.valueOf(d6);
					sum1 +=  Long.valueOf(d1);
					sum2 +=  Long.valueOf(d2);
					sum3 +=  Long.valueOf(d3);
					sum4 +=  Long.valueOf(d4);
					sum5 +=  Long.valueOf(d5);
					sum6 +=  Long.valueOf(d6);
					sum7 += sum;
					
					tSum1 += Long.valueOf(d1);
					tSum2 += Long.valueOf(d2);
					tSum3 += Long.valueOf(d3);
					tSum4 += Long.valueOf(d4);
					tSum5 += Long.valueOf(d5);
					tSum6 += Long.valueOf(d6);
					tSum7 += sum;
					data[13] = Common.valueFormat(String.valueOf(sum));
					
					if ("B".equals(this.getQ_groupType())) {
						data[14] = Common.get(deprunit1Map.get(rs.getString("deprunit1")));
					}
					rowData.addElement(data);
				}
				
				
				// 加入最後的合計 與 總計
				Object[] sumRow = new Object[columns.length];
				sumRow[0] = tmpOrganaName;
				sumRow[1] = "  中華民國"+getQ_deprYM().substring(0, 3)+" 年 "+getQ_deprYM().substring(3, 5)+" 月 "+firstDate+"  日起至   ";
				sumRow[2] = getQ_deprYM().substring(0, 3)+" 年 "+getQ_deprYM().substring(3, 5)+" 月 "+lastDate+" 日止";
				sumRow[3] = currentDeprPark;
				sumRow[4] = datetime.substring(0,3) + "年" + datetime.substring(3,5) + "月" + datetime.substring(5)+ "日";
				sumRow[5] = "合計";
				sumRow[6] = "";
				sumRow[7] = Common.valueFormat(String.valueOf(sum1));
				sumRow[8] = Common.valueFormat(String.valueOf(sum2));
				sumRow[9] = Common.valueFormat(String.valueOf(sum3));
				sumRow[10] = Common.valueFormat(String.valueOf(sum4));
				sumRow[11] = Common.valueFormat(String.valueOf(sum5));
				sumRow[12] = Common.valueFormat(String.valueOf(sum6));
				sumRow[13] = Common.valueFormat(String.valueOf(sum7));
				rowData.add(sumRow);
				
				Object[] tSumRow = new Object[columns.length];
				tSumRow[0] = tmpOrganaName;
				tSumRow[1] = "  中華民國"+getQ_deprYM().substring(0, 3)+" 年 "+getQ_deprYM().substring(3, 5)+" 月 "+firstDate+"  日起至   ";
				tSumRow[2] = getQ_deprYM().substring(0, 3)+" 年 "+getQ_deprYM().substring(3, 5)+" 月 "+lastDate+" 日止";
				tSumRow[3] = currentDeprPark;
				tSumRow[4] = datetime.substring(0,3) + "年" + datetime.substring(3,5) + "月" + datetime.substring(5)+ "日";
				tSumRow[5] = "總計";
				tSumRow[6] = "";
				tSumRow[7] = Common.valueFormat(String.valueOf(tSum1));
				tSumRow[8] = Common.valueFormat(String.valueOf(tSum2));
				tSumRow[9] = Common.valueFormat(String.valueOf(tSum3));
				tSumRow[10] = Common.valueFormat(String.valueOf(tSum4));
				tSumRow[11] = Common.valueFormat(String.valueOf(tSum5));
				tSumRow[12] = Common.valueFormat(String.valueOf(tSum6));
				tSumRow[13] = Common.valueFormat(String.valueOf(tSum7));
				rowData.add(tSumRow);
				
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
	
	
	//年度格式
	public static String yearFormat(String year){
		String formatString = new String();
		DecimalFormat df = new DecimalFormat("000");
		if(year!=null && !year.equals("")){
			try{
				formatString = df.format(Double.parseDouble(year));
			}catch (NumberFormatException e) {
				formatString =year;
		 }
		}else{
			formatString =year;
		}
		return formatString;
	}  
}