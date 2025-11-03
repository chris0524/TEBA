package unt.gr;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.DecimalFormat;
import java.util.Map;
import java.util.Vector;

import javax.swing.table.DefaultTableModel;

import util.Common;
import util.Database;
import util.Datetime;
import util.SuperBean;
import util.TCGHCommon;
import TDlib_Simple.tools.src.MathTools;

/*
*<br>程式目的：物品增減結存表 
*<br>程式代號：UNTGR009R2
*<br>撰寫日期：
*<br>程式作者：
*<br>--------------------------------------------------------
*<br>修改作者　　修改日期　　　修改目的
*<br>--------------------------------------------------------
*/
public class UNTGR009R2 extends SuperBean{
	
	String q_enterOrg;
	String q_enterOrgName;
	String q_ownership;
	String q_isorganmanager;
	String q_differenceKind;
	String q_reportType;
	String q_reportYear;
	String q_reportMonth;
	String q_reportSeason;
	String q_verify;
	String q_valueable;
	String q_dataState;
	String q_enterDateS;
	String q_enterDateE;
//	String q_propertyKind;
//	String isorganmanagerT;
	String q_enterorgKind1;
	
	private String reportType;						// 報表類型
	
	public String getReportType() { return checkGet(reportType); }
	public void setReportType(String reportType) { this.reportType = checkGet(reportType); }
	public String getQ_enterorgKind1(){ return checkGet(q_enterorgKind1); }
	public void setQ_enterorgKind1(String s){ q_enterorgKind1=checkSet(s); }
	public String getQ_enterOrg(){ return checkGet(q_enterOrg); }
	public void setQ_enterOrg(String s){ q_enterOrg=checkSet(s); }
	public String getQ_enterOrgName(){ return checkGet(q_enterOrgName); }
	public void setQ_enterOrgName(String s){ q_enterOrgName=checkSet(s); }
	public String getQ_ownership(){ return checkGet(q_ownership); }
	public void setQ_ownership(String s){ q_ownership=checkSet(s); }
	public String getQ_isorganmanager(){ return checkGet(q_isorganmanager); }
	public void setQ_isorganmanager(String s){ q_isorganmanager=checkSet(s); }
	public String getQ_differenceKind(){ return checkGet(q_differenceKind); }
	public void setQ_differenceKind(String s){ q_differenceKind=checkSet(s); }
	public String getQ_reportType(){ return checkGet(q_reportType); }
	public void setQ_reportType(String s){ q_reportType=checkSet(s); }
	public String getQ_reportYear(){ return checkGet(q_reportYear); }
	public void setQ_reportYear(String s){ q_reportYear=checkSet(s); }
	public String getQ_reportMonth(){ return checkGet(q_reportMonth); }
	public void setQ_reportMonth(String s){ q_reportMonth=checkSet(s); }
	public String getQ_reportSeason(){ return checkGet(q_reportSeason); }
	public void setQ_reportSeason(String s){ q_reportSeason=checkSet(s); }
	public String getQ_verify(){ return checkGet(q_verify); }
	public void setQ_verify(String s){ q_verify=checkSet(s); }
	public String getQ_valuable(){ return checkGet(q_valueable); }
	public void setQ_valuable(String s){ q_valueable=checkSet(s); }
	public String getQ_dataState(){ return checkGet(q_dataState); }
	public void setQ_dataState(String s){ q_dataState=checkSet(s); }
	public String getQ_enterDateS(){ return checkGet(q_enterDateS); }
	public void setQ_enterDateS(String s){ q_enterDateS=checkSet(s); }
	public String getQ_enterDateE(){ return checkGet(q_enterDateE); }
	public void setQ_enterDateE(String s){ q_enterDateE=checkSet(s); }
	String YYYMM = Datetime.getMonthDay(getQ_enterDateS(), -1, "month"); //上期結存所取的年月
	public String getYYYMM(){ return checkGet(YYYMM); }
	public void setYYYMM(String s){ YYYMM=checkSet(s); }
	String fileName;
	public String getFileName() { return checkGet(fileName); }
	public void setFileName(String s) { fileName = checkSet(s); }
	
	String userName;
	public String getUserName(){ return checkGet(userName); }
	public void setUserName(String s){ userName=checkSet(s); }


	public DefaultTableModel getResultModel() throws Exception{
		DefaultTableModel model = new javax.swing.table.DefaultTableModel();		
		setYYYMM(Datetime.getMonthDay(getQ_enterDateS(), -1, "month").substring(0, 5));
		Database db = new Database();
		
		Map<String,String> DifferencekindMap = TCGHCommon.getSysca_Code("DFK");
		//欄
		int column = 4 ;
		//列
		int row = 28 ;
			String[] columns = new String[(column*row)+10];
			int k=0;
			for(int i=1; i<(column+1); i++){
				for(int j=1; j<(row+1); j++){
					columns[k] = "COLUMNS_"+Common.formatFrontZero(String.valueOf(j), 2)+i;
					k++;
				}
			}
			columns[(column*row)] = "COLUMNS_035"; 		//本期折舊
			columns[(column*row) + 1] = "COLUMNS_055";	//本期折舊
			columns[(column*row) + 2] = "COLUMNS_115";	//本期折舊
			columns[(column*row) + 3] = "COLUMNS_135";	//本期折舊
			columns[(column*row) + 4] = "COLUMNS_185";	//本期折舊
			columns[(column*row) + 5] = "COLUMNS_225";	//本期折舊
			columns[(column*row) + 6] = "COLUMNS_245";	//本期折舊
			columns[(column*row) + 7] = "COLUMNS_265";	//本期折舊
			columns[(column*row) + 8] = "COLUMNS_285";	//本期折舊
			columns[(column*row) + 9] = "DIFFERENCEKIND";	//財產區分別
			
			Vector<Object[]> rowData = new Vector<Object[]>();
			
			double [] aa =  new double[26];//本期結存陣列
			Object[] data = new Object[(column*row)+10];
			for (int i = 0 ; i < data.length;i++) {
				data[i] = "0";
			}
			
			//#region 上期結存   
			long totalamt1 = 0;
			ResultSet rs = db.querySQL(queryUNTGR_UNTGR009R2());
			int count = 0;
			double data10 = 0;//房屋建築及設備總值
			double data17 = 0;//交通及運輸設備總值
			double data21 = 0;//雜項設備總值
			while(rs.next()) {
				count++;
				if ("10".equals(rs.getString("propertytype"))) { //土地
					totalamt1 += rs.getLong("bvsubtotal");
					DecimalFormat df=new DecimalFormat("#.######");
					double x = rs.getDouble("area")/10000;
					String s=df.format(x);
					data[0] = Common.moneyFormat(Common.checknullzero(rs.getString("amount")));
					data[1] = s;
					data[2] = Common.moneyFormat(Common.checknullzero(rs.getString("bvsubtotal")));
					aa[0] += rs.getDouble("amount");
					aa[1] += Double.parseDouble(s);
					aa[2] += rs.getDouble("bvsubtotal");
				} else if ("20".equals(rs.getString("propertytype"))) { //土地改良
					totalamt1 += rs.getDouble("bvsubtotal");
					data[3] = Common.moneyFormat(Common.checknullzero(rs.getString("amount")));
					data[4] = Common.moneyFormat(Common.checknullzero(rs.getString("bvsubtotal")));
					aa[3] += rs.getDouble("amount");
					aa[4] += rs.getDouble("bvsubtotal");
				} else if ("31".equals(rs.getString("propertytype"))) { //房屋建築及設備辦公房
					totalamt1 += rs.getLong("bvsubtotal");
					data10 += rs.getDouble("bvsubtotal");
					data[5] = Common.moneyFormat(Common.checknullzero(rs.getString("amount")));
					data[6] = Common.moneyFormat(Common.checknullzero(rs.getString("area")));
					aa[5] += rs.getDouble("amount");
					aa[6] += rs.getDouble("area");
				} else if ("32".equals(rs.getString("propertytype"))) { //房屋建築及設備宿舍
					totalamt1 += rs.getLong("bvsubtotal");
					data10 += rs.getDouble("bvsubtotal");
					data[7] = Common.moneyFormat(Common.checknullzero(rs.getString("amount")));
					data[8] = Common.moneyFormat(Common.checknullzero(rs.getString("area")));
					aa[7] += rs.getDouble("amount");
					aa[8] += rs.getDouble("area");
				} else if ("33".equals(rs.getString("propertytype"))) { //房屋建築及設備其他
					totalamt1 += rs.getDouble("bvsubtotal");
					data10 += rs.getDouble("bvsubtotal");
					data[9] = Common.moneyFormat(Common.checknullzero(rs.getString("amount")));
					aa[9] += rs.getDouble("amount");
				} else if ("40".equals(rs.getString("propertytype"))) { //機械及設備
					totalamt1 += rs.getLong("bvsubtotal");
					data[11] = Common.moneyFormat(Common.checknullzero(rs.getString("amount")));
					data[12] = Common.moneyFormat(Common.checknullzero(rs.getString("bvsubtotal")));
					aa[11] += rs.getDouble("amount");
					aa[12] += rs.getDouble("bvsubtotal");
				} else if ("51".equals(rs.getString("propertytype"))) { //交通及運輸設備船
					totalamt1 += rs.getLong("bvsubtotal");
					data17 += rs.getDouble("bvsubtotal");
					data[13] = Common.moneyFormat(Common.checknullzero(rs.getString("amount")));
					aa[13] += rs.getDouble("amount");
				} else if ("52".equals(rs.getString("propertytype"))) { //交通及運輸設備飛機
					totalamt1 += rs.getLong("bvsubtotal");
					data17 += rs.getDouble("bvsubtotal");
					data[14] = Common.moneyFormat(Common.checknullzero(rs.getString("amount")));
					aa[14] += rs.getDouble("amount");
				} else if ("53".equals(rs.getString("propertytype"))) { //交通及運輸設備汽（機）車
					totalamt1 += rs.getLong("bvsubtotal");
					data17 += rs.getDouble("bvsubtotal");
					data[15] = Common.moneyFormat(Common.checknullzero(rs.getString("amount")));
					aa[15] += rs.getDouble("amount");
				} else if ("54".equals(rs.getString("propertytype"))) { //交通及運輸設備其他
					totalamt1 += rs.getDouble("bvsubtotal");
					data17 += rs.getDouble("bvsubtotal");
					data[16] = Common.moneyFormat(Common.checknullzero(rs.getString("amount")));
					aa[16] += rs.getDouble("amount");
				} else if ("61".equals(rs.getString("propertytype"))) { //雜項設備總值圖書
					totalamt1 += rs.getDouble("bvsubtotal");
					data21 += rs.getDouble("bvsubtotal");
					data[18] = Common.moneyFormat(Common.checknullzero(rs.getString("amount")));
					aa[18] += rs.getDouble("amount");
				} else if ("62".equals(rs.getString("propertytype"))) { //雜項設備總值博物(限「珍貴」)
//					totalamt1 += rs.getLong("bvsubtotal");
//					data21 += rs.getDouble("bvsubtotal");
//					data[19] = Common.moneyFormat(Common.checknullzero(rs.getString("amount")));
//					aa[19] += rs.getLong("amount");
				} else if ("63".equals(rs.getString("propertytype"))) { //雜項設備總值其他
					totalamt1 += rs.getDouble("bvsubtotal");
					data21 += rs.getDouble("bvsubtotal");
					data[20] = Common.moneyFormat(Common.checknullzero(rs.getString("amount")));
					aa[20] += rs.getDouble("amount");
				} else if ("70".equals(rs.getString("propertytype"))) { //有價證券
					totalamt1 += rs.getDouble("bvsubtotal");
					data[22] = Common.moneyFormat(Common.checknullzero(rs.getString("amount")));
					data[23] = Common.moneyFormat(Common.checknullzero(rs.getString("bvsubtotal")));
					aa[22] += rs.getDouble("amount");
					aa[23] += rs.getDouble("bvsubtotal");
				} else if ("80".equals(rs.getString("propertytype"))) { //權利
					totalamt1 += rs.getDouble("bvsubtotal");
					data[24] = Common.moneyFormat(Common.checknullzero(rs.getString("amount")));
					data[25] = Common.moneyFormat(Common.checknullzero(rs.getString("bvsubtotal")));
					aa[24] += rs.getDouble("amount");
					aa[25] += rs.getDouble("bvsubtotal");
				}
			}
			data[10] = Common.moneyFormat(String.valueOf(data10));//房屋建築及設備總值
			aa[10] += data10;
			data[17] = Common.moneyFormat(String.valueOf(data17));//交通及運輸設備總值
			aa[17] += data17;
			data[21] = Common.moneyFormat(String.valueOf(data21));//雜項設備總值
			aa[21] += data21;
			data[26] = "";
			data[27] = Common.moneyFormat(String.valueOf(totalamt1));//上期結存總值
			//#end 
			
			//#region 本期新增  
			long totalamt2 = 0;
			rs = db.querySQL(querySQL012());
			if (rs.next()) {
				totalamt2 += rs.getDouble("3-2");
				data[28] = Common.moneyFormat(Common.checknullzero(rs.getString("1-2")));
				data[29] = String.valueOf(rs.getDouble("2-2"));
				data[30] = Common.moneyFormat(Common.checknullzero(rs.getString("3-2")));
				aa[0] += rs.getDouble("1-2");
				aa[1] += rs.getDouble("2-2");
				aa[2] += rs.getDouble("3-2");
			}
			rs = db.querySQL(querySQL042());
			if (rs.next()) {
				totalamt2 += rs.getDouble("5-2");
				data[31] = Common.moneyFormat(Common.checknullzero(rs.getString("4-2")));
				data[32] = Common.moneyFormat(Common.checknullzero(rs.getString("5-2")));
				aa[3] += rs.getDouble("4-2");
				aa[4] += rs.getDouble("5-2");
			}
			rs = db.querySQL(querySQL062());
			if (rs.next()) {
				data[33] = Common.moneyFormat(Common.checknullzero(rs.getString("6-2")));
				data[34] = Common.moneyFormat(Common.checknullzero(rs.getString("7-2")));
				aa[5] += rs.getDouble("6-2");
				aa[6] += rs.getDouble("7-2");
			}
			rs = db.querySQL(querySQL082());
			if (rs.next()) {
				data[35] = Common.moneyFormat(Common.checknullzero(rs.getString("8-2")));
				data[36] = Common.moneyFormat(Common.checknullzero(rs.getString("9-2")));
				aa[7] += rs.getLong("8-2");
				aa[8] += rs.getDouble("9-2");
			}
			rs = db.querySQL(querySQL102());
			if (rs.next()) {
				data[37] = Common.moneyFormat(Common.checknullzero(rs.getString("10-2")));
				aa[9] += rs.getDouble("10-2");
			}
			rs = db.querySQL(querySQL112());
			if (rs.next()) {
				totalamt2 += rs.getLong("11-2");
				data[38] = Common.moneyFormat(Common.checknullzero(rs.getString("11-2")));
				aa[10] += rs.getDouble("11-2");
			}
			rs = db.querySQL(querySQL122());
			if (rs.next()) {
				totalamt2 += rs.getDouble("13-2");
				data[39] = Common.moneyFormat(Common.checknullzero(rs.getString("12-2")));
				data[40] = Common.moneyFormat(Common.checknullzero(rs.getString("13-2")));
				aa[11] += rs.getDouble("12-2");
				aa[12] += rs.getDouble("13-2");
			}
			rs = db.querySQL(querySQL142());
			if (rs.next()) {
				data[41] = Common.moneyFormat(Common.checknullzero(rs.getString("14-2")));
				aa[13] += rs.getDouble("14-2");
			}
			rs = db.querySQL(querySQL152());
			if (rs.next()) {
				data[42] = Common.moneyFormat(Common.checknullzero(rs.getString("15-2")));
				aa[14] += rs.getDouble("15-2");
			}
			rs = db.querySQL(querySQL162());
			if (rs.next()) {
				data[43] = Common.moneyFormat(Common.checknullzero(rs.getString("16-2")));
				aa[15] += rs.getDouble("16-2");
			}
			rs = db.querySQL(querySQL172());
			if (rs.next()) {
				data[44] = Common.moneyFormat(Common.checknullzero(rs.getString("17-2")));
				aa[16] += rs.getDouble("17-2");
			}
			rs = db.querySQL(querySQL182());
			if (rs.next()) {
				totalamt2 += rs.getDouble("18-2");
				data[45] = Common.moneyFormat(Common.checknullzero(rs.getString("18-2")));
				aa[17] += rs.getDouble("18-2");
			}
			rs = db.querySQL(querySQL192());
			if (rs.next()) {
				data[46] = Common.moneyFormat(Common.checknullzero(rs.getString("19-2")));
				aa[18] += rs.getDouble("19-2");
			}
//			rs = db.querySQL(querySQL202());
//			if (rs.next()) {
//				data[47] = Common.moneyFormat(Common.checknullzero(rs.getString("20-2")));
//				aa[19] += rs.getLong("20-2");
//			}
			rs = db.querySQL(querySQL212());
			if (rs.next()) {
				data[48] = Common.moneyFormat(Common.checknullzero(rs.getString("21-2")));
				aa[20] += rs.getDouble("21-2");
			}
			rs = db.querySQL(querySQL222());
			if (rs.next()) {
				totalamt2 += rs.getDouble("22-2");
				data[49] = Common.moneyFormat(Common.checknullzero(rs.getString("22-2")));
				aa[21] += rs.getDouble("22-2");
			}
			
			rs = db.querySQL(querySQL232());
			if (rs.next()) {
				totalamt2 += rs.getDouble("24-2");
				data[50] = Common.moneyFormat(Common.checknullzero(rs.getString("23-2")));
				data[51] = Common.moneyFormat(Common.checknullzero(rs.getString("24-2")));
				aa[22] += rs.getDouble("23-2");
				aa[23] += rs.getDouble("24-2");
			}
			rs = db.querySQL(querySQL252());
			if (rs.next()) {
				totalamt2 += rs.getDouble("26-2");
				data[52] = Common.moneyFormat(Common.checknullzero(rs.getString("25-2")));
				data[53] = Common.moneyFormat(Common.checknullzero(rs.getString("26-2")));
				aa[24] += rs.getDouble("25-2");
				aa[25] += rs.getDouble("26-2");
			}
			data[54] = "";
			data[55] = Common.moneyFormat(String.valueOf(totalamt2));
			//#end 
			
			//#region 本期減少  
			long totalamt3 = 0;
			rs = db.querySQL(querySQL013());
			if (rs.next()) {
				totalamt3 += rs.getDouble("3-3");
				data[56] = Common.moneyFormat(Common.checknullzero(rs.getString("1-3")));
				data[57] = String.valueOf(rs.getDouble("2-3"));
				data[58] = Common.moneyFormat(Common.checknullzero(rs.getString("3-3")));
				aa[0] -= rs.getDouble("1-3");
				aa[1] -= rs.getDouble("2-3");
				aa[2] -= rs.getDouble("3-3");
			}
			rs = db.querySQL(querySQL043());
			if (rs.next()) {
				totalamt3 += rs.getDouble("5-3");
				data[59] = Common.moneyFormat(Common.checknullzero(rs.getString("4-3")));
				data[60] = Common.moneyFormat(Common.checknullzero(rs.getString("5-3")));
				aa[3] -= rs.getDouble("4-3");
				aa[4] -= rs.getDouble("5-3");
			}
			rs = db.querySQL(querySQL063());
			if (rs.next()) {
				data[61] = Common.moneyFormat(Common.checknullzero(rs.getString("6-3")));
				data[62] = Common.moneyFormat(Common.checknullzero(rs.getString("7-3")));
				aa[5] -= rs.getDouble("6-3");
				aa[6] -= rs.getDouble("7-3");
			}
			rs = db.querySQL(querySQL083());
			if (rs.next()) {
				data[63] = Common.moneyFormat(Common.checknullzero(rs.getString("8-3")));
				data[64] = Common.moneyFormat(Common.checknullzero(rs.getString("9-3")));
				aa[7] -= rs.getDouble("8-3");
				aa[8] -= rs.getDouble("9-3");
			}
			rs = db.querySQL(querySQL103());
			if (rs.next()) {
				data[65] = Common.moneyFormat(Common.checknullzero(rs.getString("10-3")));
				aa[9] -= rs.getDouble("10-3");
			}
			rs = db.querySQL(querySQL113());
			if (rs.next()) {
				data[66] = Common.moneyFormat(Common.checknullzero(rs.getString("11-3")));
				totalamt3 += rs.getDouble("11-3");
				aa[10] -= rs.getDouble("11-3");
			}
			rs = db.querySQL(querySQL123());
			if (rs.next()) {
				totalamt3 += rs.getLong("13-3");
				data[67] = Common.moneyFormat(Common.checknullzero(rs.getString("12-3")));
				data[68] = Common.moneyFormat(Common.checknullzero(rs.getString("13-3")));
				aa[11] -= rs.getDouble("12-3");
				aa[12] -= rs.getDouble("13-3");
			}
			rs = db.querySQL(querySQL143());
			if (rs.next()) {
				data[69] = Common.moneyFormat(Common.checknullzero(rs.getString("14-3")));
				aa[13] -= rs.getDouble("14-3");
			}
			rs = db.querySQL(querySQL153());
			if (rs.next()) {
				data[70] = Common.moneyFormat(Common.checknullzero(rs.getString("15-3")));
				aa[14] -= rs.getDouble("15-3");
			}
			rs = db.querySQL(querySQL163());
			if (rs.next()) {
				data[71] = Common.moneyFormat(Common.checknullzero(rs.getString("16-3")));
				aa[15] -= rs.getDouble("16-3");
			}
			rs = db.querySQL(querySQL173());
			if (rs.next()) {
				data[72] = Common.moneyFormat(Common.checknullzero(rs.getString("17-3")));
				aa[16] -= rs.getDouble("17-3");
			}
			rs = db.querySQL(querySQL183());
			if (rs.next()) {
				totalamt3 += rs.getDouble("18-3");
				data[73] = Common.moneyFormat(Common.checknullzero(rs.getString("18-3")));
				aa[17] -= rs.getDouble("18-3");
			}
			rs = db.querySQL(querySQL193());
			if (rs.next()) {
				data[74] = Common.moneyFormat(Common.checknullzero(rs.getString("19-3")));
				aa[18] -= rs.getDouble("19-3");
			}
//			rs = db.querySQL(querySQL203());
//			if (rs.next()) {
//				data[75] = Common.moneyFormat(Common.checknullzero(rs.getString("20-3")));
//				aa[19] -= rs.getLong("20-3");
//			}
			rs = db.querySQL(querySQL213());
			if (rs.next()) {
				data[76] = Common.moneyFormat(Common.checknullzero(rs.getString("21-3")));
				aa[20] -= rs.getDouble("21-3");
			}
			rs = db.querySQL(querySQL223());
			if (rs.next()) {
				totalamt3 += rs.getDouble("22-3");
				data[77] = Common.moneyFormat(Common.checknullzero(rs.getString("22-3")));
				aa[21] -= rs.getDouble("22-3");
			}
			rs = db.querySQL(querySQL233());
			if (rs.next()) {
				totalamt3 += rs.getDouble("24-3");
				data[78] = Common.moneyFormat(Common.checknullzero(rs.getString("23-3")));
				data[79] = Common.moneyFormat(Common.checknullzero(rs.getString("24-3")));
				aa[22] -= rs.getDouble("23-3");
				aa[23] -= rs.getDouble("24-3");
			}
			rs = db.querySQL(querySQL253());
			if (rs.next()) {
				totalamt3 += rs.getDouble("26-3");
				data[80] = Common.moneyFormat(Common.checknullzero(rs.getString("25-3")));
				data[81] = Common.moneyFormat(Common.checknullzero(rs.getString("26-3")));
				aa[24] -= rs.getDouble("25-3");
				aa[25] -= rs.getDouble("26-3");
			}
			data[82] = "";
			data[83] = Common.moneyFormat(String.valueOf(totalamt3));
			
			//本期減少
			long totalamt4 = 0;
			//#end 
			
			//#region 物品無折舊 一律填0
			data[112] = "0"; //土地本期折舊
			//土地改良物本期折舊 
			totalamt4 += 0;
			data[113] = "0";
			aa[4] -= 0;
			
			//房屋建築及設備本期折舊
			totalamt4 += 0;
			data[114] = "0";
			aa[10] -= 0;
			
			//房屋建築及設備本期折舊
			totalamt4 += 0;
			data[115] = "0";
			aa[12] -= 0;
				
			//交通及運輸設備本期折舊
			totalamt4 += 0;
			data[116] = "0";
			aa[17] -= 0;
			
			//雜項設備本期折舊
				totalamt4 += 0;
				data[117] = "0";
				aa[21] -= 0;
				
			//有價證券本期折舊
			data[118] = "0";
			//權利本期折舊
			data[119] = "0";
			//總值本期折舊
			data[120] = "0";
			//#end 
			
			//財產區分別
			data[121] = getQ_differenceKind() + " " +DifferencekindMap.get(getQ_differenceKind());
			//本期結存
			for (int i = 0 ;i<aa.length;i++) {
				data[i + 84] = Common.moneyFormat(String.valueOf(aa[i]));
				data[85] = String.valueOf(aa[1]);
			}
			data[110] = "";
			//總值本期結存
			data[111] = Common.moneyFormat(String.valueOf(totalamt1 + totalamt2 - totalamt3 - totalamt4));
			

			MathTools mt = new MathTools();
			
			data[26] = mt._addition(clearMoneyFormat(data[0]), 
							mt._addition(clearMoneyFormat(data[3]), 
							mt._addition(clearMoneyFormat(data[5]),
							mt._addition(clearMoneyFormat(data[7]),
							mt._addition(clearMoneyFormat(data[9]),
							mt._addition(clearMoneyFormat(data[11]),
							mt._addition(clearMoneyFormat(data[13]),
							mt._addition(clearMoneyFormat(data[14]),
							mt._addition(clearMoneyFormat(data[15]),
							mt._addition(clearMoneyFormat(data[16]),
							mt._addition(clearMoneyFormat(data[18]),
							mt._addition(clearMoneyFormat(data[20]),
							mt._addition(clearMoneyFormat(data[22]),
							clearMoneyFormat(data[24])
							)))))))))))));
		
//			data[54] = mt._addition(clearMoneyFormat(data[28]), 
//							mt._addition(clearMoneyFormat(data[31]), 
//							mt._addition(clearMoneyFormat(data[33]),
//							mt._addition(clearMoneyFormat(data[35]),
//							mt._addition(clearMoneyFormat(data[37]),
//							mt._addition(clearMoneyFormat(data[39]),
//							mt._addition(clearMoneyFormat(data[41]),
//							mt._addition(clearMoneyFormat(data[42]),
//							mt._addition(clearMoneyFormat(data[43]),
//							mt._addition(clearMoneyFormat(data[44]),
//							mt._addition(clearMoneyFormat(data[46]),
//							mt._addition(clearMoneyFormat(data[48]),
//							mt._addition(clearMoneyFormat(data[50]),
//							clearMoneyFormat(data[52])
//							)))))))))))));
			
			data[54] = String.valueOf(
					Double.parseDouble(clearMoneyFormat(data[28]))+
					Double.parseDouble(clearMoneyFormat(data[31]))+ 
					Double.parseDouble(clearMoneyFormat(data[33]))+
					Double.parseDouble(clearMoneyFormat(data[35]))+
					Double.parseDouble(clearMoneyFormat(data[37]))+
					Double.parseDouble(clearMoneyFormat(data[39]))+
					Double.parseDouble(clearMoneyFormat(data[41]))+
					Double.parseDouble(clearMoneyFormat(data[42]))+
					Double.parseDouble(clearMoneyFormat(data[43]))+
					Double.parseDouble(clearMoneyFormat(data[44]))+
					Double.parseDouble(clearMoneyFormat(data[46]))+
					Double.parseDouble(clearMoneyFormat(data[48]))+
					Double.parseDouble(clearMoneyFormat(data[50]))+
					Double.parseDouble(clearMoneyFormat(data[52]))
					);
			
//			data[82] = mt._addition(clearMoneyFormat(data[56]), 
//							mt._addition(clearMoneyFormat(data[59]), 
//							mt._addition(clearMoneyFormat(data[61]),
//							mt._addition(clearMoneyFormat(data[63]),
//							mt._addition(clearMoneyFormat(data[65]),
//							mt._addition(clearMoneyFormat(data[67]),
//							mt._addition(clearMoneyFormat(data[69]),
//							mt._addition(clearMoneyFormat(data[70]),
//							mt._addition(clearMoneyFormat(data[71]),
//							mt._addition(clearMoneyFormat(data[72]),
//							mt._addition(clearMoneyFormat(data[74]),
//							mt._addition(clearMoneyFormat(data[76]),
//							mt._addition(clearMoneyFormat(data[78]),
//							clearMoneyFormat(data[80])
//							)))))))))))));
			
			data[82] = String.valueOf( 
					Double.parseDouble(clearMoneyFormat(data[56]))+ 
					Double.parseDouble(clearMoneyFormat(data[59]))+
					Double.parseDouble(clearMoneyFormat(data[61]))+
					Double.parseDouble(clearMoneyFormat(data[63]))+
					Double.parseDouble(clearMoneyFormat(data[65]))+
					Double.parseDouble(clearMoneyFormat(data[67]))+
					Double.parseDouble(clearMoneyFormat(data[69]))+
					Double.parseDouble(clearMoneyFormat(data[70]))+
					Double.parseDouble(clearMoneyFormat(data[71]))+
					Double.parseDouble(clearMoneyFormat(data[72]))+
					Double.parseDouble(clearMoneyFormat(data[74]))+
					Double.parseDouble(clearMoneyFormat(data[76]))+
					Double.parseDouble(clearMoneyFormat(data[78]))+
					Double.parseDouble(clearMoneyFormat(data[80]))
					);
			
//			data[110] = mt._addition(clearMoneyFormat(data[84]), 
//							mt._addition(clearMoneyFormat(data[87]), 
//							mt._addition(clearMoneyFormat(data[89]),
//							mt._addition(clearMoneyFormat(data[91]),
//							mt._addition(clearMoneyFormat(data[93]),
//							mt._addition(clearMoneyFormat(data[95]),
//							mt._addition(clearMoneyFormat(data[97]),
//							mt._addition(clearMoneyFormat(data[98]),
//							mt._addition(clearMoneyFormat(data[99]),
//							mt._addition(clearMoneyFormat(data[100]),
//							mt._addition(clearMoneyFormat(data[102]),
//							mt._addition(clearMoneyFormat(data[104]),
//							mt._addition(clearMoneyFormat(data[106]),
//							clearMoneyFormat((data[108])
//							))))))))))))));
			
			data[110] = String.valueOf(  
					Double.parseDouble(clearMoneyFormat(data[84]))+ 
					Double.parseDouble(clearMoneyFormat(data[87]))+
					Double.parseDouble(clearMoneyFormat(data[89]))+
					Double.parseDouble(clearMoneyFormat(data[91]))+
					Double.parseDouble(clearMoneyFormat(data[93]))+
					Double.parseDouble(clearMoneyFormat(data[95]))+
					Double.parseDouble(clearMoneyFormat(data[97]))+
					Double.parseDouble(clearMoneyFormat(data[98]))+
					Double.parseDouble(clearMoneyFormat(data[99]))+
					Double.parseDouble(clearMoneyFormat(data[100]))+
					Double.parseDouble(clearMoneyFormat(data[102]))+
					Double.parseDouble(clearMoneyFormat(data[104]))+
					Double.parseDouble(clearMoneyFormat(data[106]))+
					Double.parseDouble(clearMoneyFormat(data[108]))
					);
			
			rowData.addElement(data);
			Object[][] data1 = new Object[0][0];
			data1 = (Object[][]) rowData.toArray(data1);
			model.setDataVector(data1, columns);

			return model;
	
	}
	
	//查詢條件1:依所選的「列印彙總表(isorganmanager)」控制各table「入帳機關(enterOrg)」需符合的條件
	protected String getCondition1(){
		String Condition="";
		if ("N".equals(getQ_isorganmanager())) {
			if("".equals(getQ_enterorgKind1())){
				Condition += " and d.enterorg=" + Common.sqlChar(getQ_enterOrg());
			}else{
				Condition += " and ( d.enterorg=" + Common.sqlChar(getQ_enterOrg()) + getCondition1_1()+")";
			}
		} else if ("Y".equals(getQ_isorganmanager()) && "110".equals(getQ_differenceKind()) && !TCGHCommon.getSYSCODEName("01", "ETO").equals(getQ_enterOrg())) {
			Condition += " and (d.enterorg=" + Common.sqlChar(getQ_enterOrg()) + " or d.enterorg in (select organid from SYSCA_ORGAN where organsuperior1=" + Common.sqlChar(getQ_enterOrg())+" ))";
		} else if ("Y".equals(getQ_isorganmanager()) && "110".equals(getQ_differenceKind()) && TCGHCommon.getSYSCODEName("01", "ETO").equals(getQ_enterOrg())) {
			Condition += " and d.enterorg in (select organid from SYSCA_ORGAN where ismanager='Y')";
		} else if ("Y".equals(getQ_isorganmanager()) && "120".equals(getQ_differenceKind())) {
			Condition += " and (d.enterorg=" + Common.sqlChar(getQ_enterOrg()) + " or d.enterorg in (select organid from SYSCA_ORGAN where organsuperior2=" + Common.sqlChar(getQ_enterOrg())+" ))";
		}
		return Condition;
	}
	//TODO
	//查詢條件1-1依所選的「含實中」、「及所屬」控制各table「入帳機關(enterOrg)」需符合的條件
		protected String getCondition1_1(){
			String Condition="";
			if ("1".equals(getQ_enterorgKind1()) && "120".equals(getQ_differenceKind()) && !TCGHCommon.getSYSCODEName("01", "ETO").equals(getQ_enterOrg())) {
				Condition += " or d.enterorg in (select organid from SYSCA_ORGAN where organsuperior1=" + Common.sqlChar(getQ_enterOrg())+" )";
			} else if ("2".equals(getQ_enterorgKind1()) && "120".equals(getQ_differenceKind()) && TCGHCommon.getSYSCODEName("02", "ETO").equals(getQ_enterOrg())) {
				Condition += " or d.enterorg in (select organid from SYSCA_ORGAN where organsuperior2=" + Common.sqlChar(getQ_enterOrg())+" or organsuperior3=" + Common.sqlChar(getQ_enterOrg())+" )";
			} else if ("1".equals(getQ_enterorgKind1()) && "110".equals(getQ_differenceKind()) && TCGHCommon.getSYSCODEName("01", "ETO").equals(getQ_enterOrg())) {
				Condition += " or d.enterorg in (select organid from SYSCA_ORGAN where organsuperior2=" + Common.sqlChar(getQ_enterOrg())+" or organsuperior3=" + Common.sqlChar(getQ_enterOrg())+" )";
			}
			return Condition;
		}
	
	//查詢條件2:畫面條件
	protected String getCondition2(int i){
		String enterdate = null;
		if (i == 1) {
			enterdate = "enterdate";
		} else if (i == 2) {
			enterdate = "adjustdate";
		} else if (i == 3) {
			enterdate = "reducedate";
		} else if (i == 4) {
			enterdate = "deprym";
		}
		String Condition = "";
		if (i == 4) {
			Condition=" and d." + enterdate + " >= " + Common.sqlChar(Datetime.changeTaiwanYYMM(getQ_enterDateS().substring(0,6), "2")) +
			" and d." + enterdate + " <= " + Common.sqlChar(Datetime.changeTaiwanYYMM(getQ_enterDateE().substring(0,6), "2"));
		} else {
			Condition=" and d." + enterdate + " >= " + Common.sqlChar(Datetime.changeTaiwanYYMMDD(getQ_enterDateS(), "2")) +
			" and d." + enterdate + " <= " + Common.sqlChar(Datetime.changeTaiwanYYMMDD(getQ_enterDateE(), "2"));
		}

		return Condition;
	}
	
	//查詢條件3:資料狀態
	protected String getCondition3(){
		String Condition="";
		if (!"".equals(Common.get(getQ_dataState())))	//資料狀態
			Condition += " and d.datastate = " + Common.sqlChar(getQ_dataState());
		return Condition;
	}
	
	protected String getCondition4(){
		String Condition="";
		if (!"".equals(Common.get(getQ_valuable())))	//珍貴財產
			Condition += " and mm.valuable = " + Common.sqlChar(getQ_valuable());
		return Condition;
	}
	
	protected String getCondition41(){
		String Condition="";
		if (!"".equals(Common.get(getQ_valuable())))	//珍貴財產
			Condition += " and d.valuable = " + Common.sqlChar(getQ_valuable());
		return Condition;
	}
	
	//查詢條件5:畫面條件
	protected String getCondition5(){
		String Condition="";
		if (!"".equals(Common.get(getQ_ownership())))	//權屬條件
			Condition += " and d.ownership = " + Common.sqlChar(getQ_ownership());
		if (!"".equals(Common.get(getQ_differenceKind())))	//財產區分別
			Condition += " and d.differencekind = " + Common.sqlChar(getQ_differenceKind());
		if (!"".equals(Common.get(getQ_verify())))	//入帳
			Condition += " and d.verify = " + Common.sqlChar(getQ_verify());
		return Condition;
	}
	
	//增減結存表初始檔
	protected String queryUNTGR_UNTGR009R2(){
		String querySQL=" select propertytype,sum(oldamount) amount,sum(oldarea) area,sum(oldbvsubtotal) bvsubtotal " +
			" from " +
			" UNTGR_UNTGR009R2 d " +
			" where 1=1 ";
		
		querySQL += getCondition1();
		if (!"".equals(Common.get(getQ_ownership())))		//權屬條件
			querySQL += " and d.ownership = " + Common.sqlChar(getQ_ownership());
		if (!"".equals(Common.get(getQ_differenceKind())))	//財產區分別
			querySQL += " and d.differencekind = " + Common.sqlChar(getQ_differenceKind());
		if (!"".equals(Common.get(getQ_valuable())))		//入帳
			querySQL += " and d.valuable = " + Common.sqlChar(getQ_valuable());
		if (!"".equals(Common.get(getQ_reportYear())))		//報表年度
			querySQL += " and d.reportyear = " + Common.sqlChar(getQ_reportYear());
		querySQL += " and d.reporttype = " + Common.sqlChar("1");
		querySQL += " and d.reportmonth = " + Common.sqlChar(getQ_enterDateS().substring(3,5));
		querySQL += " group by propertytype ";
		return querySQL;
	}
	
	public String checkTableExist(String YYYMM){
		String[] tableName = new String[] {"UNTNE_NONEXP","UNTNE_NONEXPDETAIL"}; 
		String[] tableChineseName = new String[] {"物品主檔","物品批號明細檔"}; 
		
		String[] queryMonth = new String[2];
		queryMonth[0] = YYYMM;
		queryMonth[1] = new Datetime().getMonthDay(YYYMM,-1,"month");
		
		String message = "";
		Database db = new Database();
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = db.getConnection();
		for(int i=0; i< tableName.length; i++){
			for(int j=0; j< queryMonth.length; j++){
				try{
					ps = conn.prepareStatement(" select top 1 * from " + tableName[i]+"_"+queryMonth[j] );		
					rs = ps.executeQuery();				
				} catch (Exception ex) {
					message +=  " "+tableChineseName[i] + "之" +queryMonth[j]+"資料,";
				}
			}
		}
		if(!"".equals(message)){
			message = "目前尚未備存" + message.substring(0, message.length()-1) + "!!";
		}
		return message;
	} 
	
	/**
	 * 土地本期增加
	 */
	protected String querySQL012(){
		String querySQL=" select " +
				" sum(aa.[1-2]) '1-2', " +
				" sum(aa.[2-2]) '2-2', " +
				" sum(aa.[3-2]) '3-2' " +
				" from ( " +
				" select " +
				" SUM(isnull(d.originalamount, 0)) '1-2', " +
				" 0 '2-2', " +
				" SUM(isnull(d.originalbv,'0')) '3-2' " +
				" from " +
				" UNTNE_NONEXPDETAIL d " +
				" inner join UNTNE_NONEXP mm on d.enterorg=mm.enterorg and d.ownership=mm.ownership and d.differencekind=mm.differencekind and d.propertyno=mm.propertyno and d.lotno=mm.lotno " +
				" where " +
				" substring(d.propertyno,1,1) = '1' " +
				" and substring(d.propertyno,1,3) <> '111' ";
			querySQL += getCondition1() + getCondition2(1) + getCondition4() + getCondition5();
			querySQL += " union " +
				" select " +
				" 0 '1-2', " +
				" 0 '2-2', " +
				" SUM(isnull(d.addbookvalue,'0')) '3-2' " +
				" from " +
				" UNTNE_ADJUSTDETAIL d " +
				" where " +
				" substring(d.propertyno,1,1) = '1' " +
				" and substring(d.propertyno,1,3) <> '111' ";
			querySQL += getCondition1() + getCondition2(2) + getCondition41() + getCondition5();
			querySQL += " ) aa";
			
			return querySQL;
	}
	

	/**
	 * 土地改良物本期增加
	 */
	protected String querySQL042(){
		String querySQL=" select " +
				" sum(aa.[4-2]) '4-2', " +
				" sum(aa.[5-2]) '5-2' " +
				" from ( " +
				" select " +
				" SUM(isnull(d.originalamount, 0)) '4-2', " +
				" SUM(isnull(d.originalbv,'0')) '5-2' " +
				" from " +
				" UNTNE_NONEXPDETAIL d " +
				" inner join UNTNE_NONEXP mm on d.enterorg=mm.enterorg and d.ownership=mm.ownership and d.differencekind=mm.differencekind and d.propertyno=mm.propertyno and d.lotno=mm.lotno " +
				" where " +
				" substring(d.propertyno,1,3) = '111' ";
				querySQL += getCondition1() + getCondition2(1) + getCondition4() + getCondition5();
				querySQL += " union " +
				" select " +
				" 0 '4-2', " +
				" SUM(isnull(d.addbookvalue,0)) '5-2' " +
				" from " +
				" UNTNE_ADJUSTDETAIL d " +
				" where " +
				" substring(d.propertyno,1,3) = '111' ";
				querySQL += getCondition1() + getCondition2(2) + getCondition41() + getCondition5();
				querySQL += " ) aa";
			return querySQL;
	}
	
	/**
	 * 房屋建築及設備(辦公房屋)本期增加
	 */
	protected String querySQL062(){
		String querySQL=" select " +
				" sum(aa.[6-2]) '6-2', " +
				" sum(aa.[7-2]) '7-2' " +
				" from ( " +
				" select " +
				" SUM(isnull(d.originalamount, 0)) '6-2', " +
				" 0 '7-2' " +
				" from " +
				" UNTNE_NONEXPDETAIL d " +
				" inner join UNTNE_NONEXP mm on d.enterorg=mm.enterorg and d.ownership=mm.ownership and d.differencekind=mm.differencekind and d.propertyno=mm.propertyno and d.lotno=mm.lotno " +
				" where " +
				" substring(d.propertyno, 1,5) between '20000' and '20105' ";
				querySQL += getCondition1() + getCondition2(1) + getCondition4() + getCondition5();
				querySQL += " union " +
				" select " +
				" 0 '6-2', " +
				" 0 '7-2' " +
				" from " +
				" UNTNE_ADJUSTDETAIL d " +
				" where " +
				" substring(d.propertyno, 1,5) between '20000' and '20105' ";
				querySQL += getCondition1() + getCondition2(2) + getCondition41() + getCondition5();
				querySQL += " ) aa";
			return querySQL;
	}
	
	/**
	 * 房屋建築及設備(宿舍)本期增加
	 */
	protected String querySQL082(){
		String querySQL=" select " +
				" sum(aa.[8-2]) '8-2', " +
				" sum(aa.[9-2]) '9-2' " +
				" from ( " +
				" select " +
				" SUM(isnull(d.originalamount, 0)) '8-2', " +
				" 0 '9-2' " +
				" from " +
				" UNTNE_NONEXPDETAIL d " +
				" inner join UNTNE_NONEXP mm on d.enterorg=mm.enterorg and d.ownership=mm.ownership and d.differencekind=mm.differencekind and d.propertyno=mm.propertyno and d.lotno=mm.lotno " +
				" where " +
				" substring(d.propertyno,1,5) = '20106' ";
				querySQL += getCondition1() + getCondition2(1) + getCondition4() + getCondition5();
				querySQL += " union " +
				" select " +
				" 0 '8-2', " +
				" 0 '9-2' " +
				" from " +
				" UNTNE_ADJUSTDETAIL d " +
				" where " +
				" substring(d.propertyno,1,5) = '20106' ";
				querySQL += getCondition1() + getCondition2(2) + getCondition41() + getCondition5();
				querySQL += " ) aa";
			return querySQL;
	}
	
	/**
	 * 房屋建築及設備(其他)本期增加
	 */
	protected String querySQL102(){
		String querySQL=" select " +
				" sum(aa.[10-2]) '10-2' " +
				" from ( " +
				" select " +
				" SUM(isnull(d.originalamount, 0)) '10-2' " +
				" from " +
				" UNTNE_NONEXPDETAIL d " +
				" inner join UNTNE_NONEXP mm on d.enterorg=mm.enterorg and d.ownership=mm.ownership and d.differencekind=mm.differencekind and d.propertyno=mm.propertyno and d.lotno=mm.lotno " +
				" where " +
				" d.propertyno like '2%' " +
				" and substring(d.propertyno, 1,5) not between '20000' and '20105' and substring(d.propertyno, 1,5) != '20106' ";
				querySQL += getCondition1() + getCondition2(1) + getCondition4() + getCondition5();
				querySQL += " union " +
				" select " +
				" 0 '10-2' " +
				" from " +
				" UNTNE_ADJUSTDETAIL d " +
				" where " +
				" d.propertyno like '2%' " +
				" and substring(d.propertyno, 1,5) not between '20000' and '20105' and substring(d.propertyno, 1,5) != '20106' ";
				querySQL += getCondition1() + getCondition2(2) + getCondition41() + getCondition5();
				querySQL += " ) aa";
			return querySQL;
	}
	
	/**
	 * 房屋建築及設備本期增加(價值)
	 */
	protected String querySQL112(){
		String querySQL=" select " +
				" sum(aa.[11-2]) '11-2' " +
				" from ( " +
				" select " +
				" SUM(isnull(d.originalbv,0)) '11-2' " +
				" from " +
				" UNTNE_NONEXPDETAIL d " +
				" inner join UNTNE_NONEXP mm on d.enterorg=mm.enterorg and d.ownership=mm.ownership and d.differencekind=mm.differencekind and d.propertyno=mm.propertyno and d.lotno=mm.lotno " +
				" where " +
				" substring(d.propertyno,1,1) = '2' ";
				querySQL += getCondition1() + getCondition2(1) + getCondition4() + getCondition5();
				querySQL += " union " +
				" select " +
				" SUM( isnull(d.addbookvalue,0) ) '11-2' " +
				" from " +
				" UNTNE_ADJUSTDETAIL d " +
				" where " +
				" substring(d.propertyno,1,1) = '2' ";
				querySQL += getCondition1() + getCondition2(2) + getCondition41() + getCondition5();
				querySQL += " ) aa";
				return querySQL;
	}
	
	/**
	 * 機械及設備本期增加
	 */
	protected String querySQL122(){
		String querySQL=" select " +
		" sum(aa.[12-2]) '12-2', " +
		" sum(aa.[13-2]) '13-2' " +
		" from ( " +
		" select " +
		" count(1) as '12-2', " +
		" SUM(isnull(d.originalbv,0)) '13-2' " +
		" from " +
		" UNTNE_NONEXPDETAIL d " +
		" inner join UNTNE_NONEXP mm on d.enterorg=mm.enterorg and d.ownership=mm.ownership and d.differencekind=mm.differencekind and d.propertyno=mm.propertyno and d.lotno=mm.lotno " +
		" where " +
		" substring(d.propertyno,1,1) = '3' ";
		querySQL += getCondition1() + getCondition2(1) + getCondition4() + getCondition5();
		querySQL += " union " +
		" select " +
		" 0 '12-2', " +
		" SUM( isnull(d.addbookvalue,0) ) '13-2' " +
		" from " +
		" UNTNE_ADJUSTDETAIL d " +
		" where " +
		" substring(d.propertyno,1,1) = '3' ";
		querySQL += getCondition1() + getCondition2(2) + getCondition41() + getCondition5();
		querySQL += " ) aa";
		return querySQL;
	}
	
	/**
	 * 交通及運輸設備本期增加(船)數量
	 */
	protected String querySQL142(){
		String querySQL=" select " +
		" sum(aa.[14-2]) '14-2' " +
		" from ( " +
		" select " +
		" count(1) as '14-2' " +
		" from " +
		" UNTNE_NONEXPDETAIL d " +
		" inner join UNTNE_NONEXP mm on d.enterorg=mm.enterorg and d.ownership=mm.ownership and d.differencekind=mm.differencekind and d.propertyno=mm.propertyno and d.lotno=mm.lotno " +
		" where " +
		" substring(d.propertyno, 1,5) between '40200' and '40202' ";
		querySQL += getCondition1() + getCondition2(1) + getCondition4() + getCondition5();
		querySQL += " ) aa";
		return querySQL;
	}
	
	/**
	 * 交通及運輸設備本期增加(飛機)數量
	 */
	protected String querySQL152(){
		String querySQL=" select " +
		" sum(aa.[15-2]) '15-2' " +
		" from ( " +
		" select " +
		" count(1) as '15-2' " +
		" from " +
		" UNTNE_NONEXPDETAIL d " +
		" inner join UNTNE_NONEXP mm on d.enterorg=mm.enterorg and d.ownership=mm.ownership and d.differencekind=mm.differencekind and d.propertyno=mm.propertyno and d.lotno=mm.lotno " +
		" where " +
		" substring(d.propertyno, 1,5) like '40302%'";
		querySQL += getCondition1() + getCondition2(1) + getCondition4() + getCondition5();
		querySQL += " ) aa";
		return querySQL;
	}
	
	/**
	 * 交通及運輸設備本期增加(汽機車)數量
	 */
	protected String querySQL162(){
		String querySQL=" select " +
		" sum(aa.[16-2]) '16-2' " +
		" from ( " +
		" select " +
		" count(1) as '16-2' " +
		" from " +
		" UNTNE_NONEXPDETAIL d " +
		" inner join UNTNE_NONEXP mm on d.enterorg=mm.enterorg and d.ownership=mm.ownership and d.differencekind=mm.differencekind and d.propertyno=mm.propertyno and d.lotno=mm.lotno " +
		" where " +
		" d.propertyno like '40107%' " ;
		querySQL += getCondition1() + getCondition2(1) + getCondition4() + getCondition5();
		querySQL += " ) aa";
		return querySQL;
	}
	
	/**
	 * 交通及運輸設備本期增加(其他)數量
	 */
	protected String querySQL172(){
		String querySQL=" select " +
		" sum(aa.[17-2]) '17-2' " +
		" from ( " +
		" select " +
		" count(1) as '17-2' " +
		" from " +
		" UNTNE_NONEXPDETAIL d " +
		" inner join UNTNE_NONEXP mm on d.enterorg=mm.enterorg and d.ownership=mm.ownership and d.differencekind=mm.differencekind and d.propertyno=mm.propertyno and d.lotno=mm.lotno " +
		" where " +
		" d.propertyno like '4%' " +
		" and substring(d.propertyno, 1,5) not between '40200' and '40202' " +
		" and substring(d.propertyno, 1,5) not like '40302%'" +
		" and d.propertyno not like '40107%' ";
		querySQL += getCondition1() + getCondition2(1) + getCondition4() + getCondition5();
		querySQL += " ) aa";
		return querySQL;
	}
	
	/**
	 * 交通及運輸設備本期增加(全部)價值
	 */
	protected String querySQL182(){
		String querySQL=" select " +
		" sum(aa.[18-2]) '18-2' " +
		" from ( " +
		" select " +
		" SUM(isnull(d.originalbv,0)) '18-2' " +
		" from " +
		" UNTNE_NONEXPDETAIL d " +
		" inner join UNTNE_NONEXP mm on d.enterorg=mm.enterorg and d.ownership=mm.ownership and d.differencekind=mm.differencekind and d.propertyno=mm.propertyno and d.lotno=mm.lotno " +
		" where " +
		" substring(d.propertyno,1,1) = '4' ";
		querySQL += getCondition1() + getCondition2(1) + getCondition4() + getCondition5();
		querySQL += " union " +
		" select " +
		" SUM( isnull(d.addbookvalue,0) ) '18-2' " +
		" from " +
		" UNTNE_ADJUSTDETAIL d " +
		" where " +
		" substring(d.propertyno,1,1) = '4' ";
		querySQL += getCondition1() + getCondition2(2) + getCondition41() + getCondition5();
		querySQL += " ) aa";
		return querySQL;
	}
	
	/**
	 * 雜項設備本期增加(圖書)數量
	 */
	protected String querySQL192(){
		String querySQL=" select " +
		" sum(aa.[19-2]) '19-2' " +
		" from ( " +
		" select " +
		" count(1) as '19-2' " +
		" from " +
		" UNTNE_NONEXPDETAIL d " +
		" inner join UNTNE_NONEXP mm on d.enterorg=mm.enterorg and d.ownership=mm.ownership and d.differencekind=mm.differencekind and d.propertyno=mm.propertyno and d.lotno=mm.lotno " +
		" where " +
		" substring(d.propertyno,1,3) = '503' ";
		querySQL += getCondition1() + getCondition2(1) + getCondition4() + getCondition5();
		querySQL += " ) aa";
		return querySQL;
	}
	
	/**
	 * 雜項設備本期增加(其他)數量
	 */
	protected String querySQL212(){
		String querySQL=" select " +
		" sum(aa.[21-2]) '21-2' " +
		" from ( " +
		" select " +
		" count(1) as '21-2' " +
		" from " +
		" UNTNE_NONEXPDETAIL d " +
		" inner join UNTNE_NONEXP mm on d.enterorg=mm.enterorg and d.ownership=mm.ownership and d.differencekind=mm.differencekind and d.propertyno=mm.propertyno and d.lotno=mm.lotno " +
		" where " +
		" (d.propertyno like '5%' or d.propertyno like '6%')" +
		" and d.propertyno not like '503%'" ;
		querySQL += getCondition1() + getCondition2(1) + getCondition4() + getCondition5();
		querySQL += " ) aa";
		return querySQL;
	}
	
	/**
	 * 雜項設備本期增加(全部)價值
	 */
	protected String querySQL222(){
		String querySQL=" select " +
		" sum(aa.[22-2]) '22-2' " +
		" from ( " +
		" select " +
		" SUM(isnull(d.originalbv,0)) '22-2' " +
		" from " +
		" UNTNE_NONEXPDETAIL d " +
		" inner join UNTNE_NONEXP mm on d.enterorg=mm.enterorg and d.ownership=mm.ownership and d.differencekind=mm.differencekind and d.propertyno=mm.propertyno and d.lotno=mm.lotno " +
		" where " +
		" (" +
		" substring(d.propertyno,1,1) = '5' " +
		" or substring(d.propertyno,1,1) = '6' " +
		" )";
		querySQL += getCondition1() + getCondition2(1) + getCondition4() + getCondition5();
		querySQL += " union " +
		" select " +
		" SUM( isnull(d.addbookvalue,0) ) '22-2' " +
		" from " +
		" UNTNE_ADJUSTDETAIL d " +
		" where " +
		" (" +
		" substring(d.propertyno,1,1) = '5' " +
		" or substring(d.propertyno,1,1) = '6' " +
		" )";
		querySQL += getCondition1() + getCondition2(2) + getCondition41() + getCondition5();
		querySQL += " ) aa";
		return querySQL;
	}

	/**
	 * 有價證券本期增加
	 */
	protected String querySQL232(){
		String querySQL=" select " +
				" sum(aa.[23-2]) '23-2', " +
				" sum(aa.[24-2]) '24-2' " +
				" from ( " +
				" select " +
				" sum(isnull(d.originalamount,'0')) as '23-2', " +
				" sum(isnull(d.originalbv,'0')) '24-2' " +
				" from " +
				" UNTNE_NONEXPDETAIL d " +
				" where " +
				" substring(d.propertyno,1,1) = '9' ";
				querySQL += getCondition1() + getCondition2(1) + getCondition5();
				querySQL += " union " +
				" select " +
				" 0 '23-2', " +
				" sum(isnull(d.addbookvalue,'0')) '24-2' " +
				" from " +
				" UNTNE_ADJUSTDETAIL d " +
				" where " +
				" substring(d.propertyno,1,1) = '9' ";
				querySQL += getCondition1() + getCondition2(2) + getCondition5();
				querySQL += " ) aa";
				return querySQL;
	}
	
	/**
	 * 權利本期增加
	 */
	protected String querySQL252(){
		String querySQL=" select " +
		" sum(aa.[25-2]) '25-2', " +
		" sum(aa.[26-2]) '26-2' " +
		" from ( " +
		" select " +
		" sum(isnull(d.originalamount,'0')) '25-2', " +
		" sum(isnull(d.originalbv,'0')) '26-2' " +
		" from " +
		" UNTNE_NONEXPDETAIL d " +
		" where " +
		" substring(d.propertyno,1,1) = '8' ";
		querySQL += getCondition1() + getCondition2(1) + getCondition5();
		querySQL += " union " +
		" select " +
		" 0 '25-2', " +
		" sum(isnull(d.addbookvalue,'0')) '26-2' " +
		" from " +
		" UNTNE_ADJUSTDETAIL d " +
		" where " +
		" substring(d.propertyno,1,1) = '8' ";
		querySQL += getCondition1() + getCondition2(2) + getCondition5();
		querySQL += " ) aa";
		return querySQL;
	}
	
	/**
	 * 土地本期減少
	 */
	protected String querySQL013() {
		String querySQL=" select " +
				" sum(aa.[1-3]) '1-3', " +
				" sum(aa.[2-3]) '2-3', " +
				" sum(aa.[3-3]) '3-3' " +
				" from ( " +
				" select " +
				" 0 '1-3', " +
				" 0 '2-3', " +
				" SUM(isnull(d.reducebookvalue,'0')) '3-3' " +
				" from " +
				" UNTNE_ADJUSTDETAIL d " +
				" where " +
				" substring(d.propertyno,1,1) = '1' " +
				" and substring(d.propertyno,1,3) <> '111' ";
				querySQL += getCondition1() + getCondition2(2) + getCondition41() + getCondition5();
				querySQL += " union " +
				" select " +
				" SUM(isnull(d.adjustbookamount,'0')) '1-3', " +
				" 0 '2-3', " +
				" SUM(isnull(d.adjustbookvalue,'0')) '3-3' " +
				" from " +
				" UNTNE_REDUCEDETAIL d " +
				" where " +
				" substring(d.propertyno,1,1) = '1' " +
				" and substring(d.propertyno,1,3) <> '111' ";
				querySQL += getCondition1() + getCondition2(3) + getCondition41() + getCondition5();
				querySQL += " ) aa";
			return querySQL;
	}
	
	/**
	 * 土地改良物本期減少
	 */
	protected String querySQL043() {
		String querySQL=" select " +
				" sum(aa.[4-3]) '4-3', " +
				" sum(aa.[5-3]) '5-3' " +
				" from ( " +
				" select " +
				" 0 '4-3', " +
				" SUM(isnull(d.reducebookvalue,0)) '5-3' " +
				" from " +
				" UNTNE_ADJUSTDETAIL d " +
				" where " +
				" substring(d.propertyno,1,3) = '111' ";
				querySQL += getCondition1() + getCondition2(2) + getCondition41() + getCondition5();
				querySQL += " union " +
				" select " +
				" SUM(isnull(d.adjustbookamount,'0')) '4-3', " +
				" SUM(ISNULL(d.adjustbookvalue,0)) '5-3' " +
				" from " +
				" UNTNE_REDUCEDETAIL d " +
				" where " +
				" substring(d.propertyno,1,3) = '111' ";
				querySQL += getCondition1() + getCondition2(3) + getCondition41() + getCondition5();
				querySQL += " ) aa";
			return querySQL;
	}
	
	/**
	 * 房屋建築及設備(辦公房屋)本期減少
	 */
	protected String querySQL063() {
		String querySQL=" select " +
				" sum(aa.[6-3]) '6-3', " +
				" sum(aa.[7-3]) '7-3' " +
				" from ( " +
				" select " +
				" SUM(isnull(d.adjustbookamount,'0')) '6-3', " +
				" 0 '7-3' " +
				" from " +
				" UNTNE_REDUCEDETAIL d " +
				" where " +
				" substring(d.propertyno, 1,5) between '20000' and '20105' ";
				querySQL += getCondition1() + getCondition2(3) + getCondition41() + getCondition5();
				querySQL += " ) aa";
				return querySQL;
	}
	
	/**
	 * 房屋建築及設備(宿舍)本期減少
	 */
	protected String querySQL083() {
		String querySQL=" select " +
				" sum(aa.[8-3]) '8-3', " +
				" sum(aa.[9-3]) '9-3' " +
				" from ( " +
				" select " +
				" SUM(isnull(d.adjustbookamount,'0')) '8-3', " +
				" 0 '9-3' " +
				" from " +
				" UNTNE_REDUCEDETAIL d " +
				" where " +
				" substring(d.propertyno,1,5) = '20106' ";
				querySQL += getCondition1() + getCondition2(3) + getCondition41() + getCondition5();
				querySQL += " ) aa";
				return querySQL;
	}
	
	/**
	 * 房屋建築及設備(其他)本期減少
	 */
	protected String querySQL103() {
		String querySQL=" select " +
				" sum(aa.[10-3]) '10-3' " +
				" from ( " +
				" select " +
				" SUM(isnull(d.adjustbookamount,'0')) '10-3' " +
				" from " +
				" UNTNE_REDUCEDETAIL d " +
				" where " +
				" d.propertyno like '2%' " +
				" and substring(d.propertyno, 1,5) not between '20000' and '20105' and substring(d.propertyno, 1,5) != '20106' ";
				querySQL += getCondition1() + getCondition2(3) + getCondition41() + getCondition5();
				querySQL += " ) aa";
				return querySQL;
	}
	
	/**
	 * 房屋建築及設備本期減少(價值)
	 */
	protected String querySQL113() {
		String querySQL=" select " +
				" sum(aa.[11-3]) '11-3' " +
				" from ( " +
				" select " +
				" SUM(isnull(d.reducebookvalue,0)) '11-3' " +
				" from " +
				" UNTNE_ADJUSTDETAIL d " +
				" where " +
				" substring(d.propertyno,1,1) = '2' ";
				querySQL += getCondition1() + getCondition2(2) + getCondition41() + getCondition5();
				querySQL += " union " +
				" select " +
				" SUM(isnull(d.adjustbookvalue,0)) '11-3' " +
				" from " +
				" UNTNE_REDUCEDETAIL d " +
				" where " +
				" substring(d.propertyno,1,1) = '2' ";
				querySQL += getCondition1() + getCondition2(3) + getCondition41() + getCondition5();
				querySQL += " ) aa";
				return querySQL;
	}
	
	/**
	 * 機械及設備本期減少
	 */
	protected String querySQL123() {
		String querySQL=" select " +
		" sum(aa.[12-3]) '12-3', " +
		" sum(aa.[13-3]) '13-3' " +
		" from ( " +
		" select " +
		" 0 '12-3', " +
		" SUM(isnull(d.reducebookvalue,0)) '13-3' " +
		" from " +
		" UNTNE_ADJUSTDETAIL d " +
		" where " +
		" substring(d.propertyno,1,1) = '3' ";
		querySQL += getCondition1() + getCondition2(2) + getCondition41() + getCondition5();
		querySQL += " union " +
		" select " +
		" count(*) as '12-3', " +
		" SUM(isnull(d.adjustbookvalue,0)) '13-3' " +
		" from " +
		" UNTNE_REDUCEDETAIL d " +
		" where " +
		" substring(d.propertyno,1,1) = '3' ";
		querySQL += getCondition1() + getCondition2(3) + getCondition41() + getCondition5();
		querySQL += " ) aa";
		return querySQL;
	}
	
	/**
	 * 交通及運輸設備本期減少(船)數量
	 */
	protected String querySQL143() {
		String querySQL=" select " +
		" sum(aa.[14-3]) '14-3' " +
		" from ( " +
		" select " +
		" count(*) as '14-3' " +
		" from " +
		" UNTNE_REDUCEDETAIL d " +
		" where " +
		" substring(d.propertyno, 1,5) between '40200' and '40202' ";
		querySQL += getCondition1() + getCondition2(3) + getCondition41() + getCondition5();
		querySQL += " ) aa";
		return querySQL;
	}
	
	/**
	 * 交通及運輸設備本期減少(飛機)數量
	 */
	protected String querySQL153() {
		String querySQL=" select " +
		" sum(aa.[15-3]) '15-3' " +
		" from ( " +
		" select " +
		" count(*) as '15-3' " +
		" from " +
		" UNTNE_REDUCEDETAIL d " +
		" where " +
		" substring(d.propertyno, 1,5) like '40302%'";
		querySQL += getCondition1() + getCondition2(3) + getCondition41() + getCondition5();
		querySQL += " ) aa";
		return querySQL;
	}
	
	/**
	 * 交通及運輸設備本期減少(汽機車)數量
	 */
	protected String querySQL163() {
		String querySQL=" select " +
		" sum(aa.[16-3]) '16-3' " +
		" from ( " +
		" select " +
		" count(*) as '16-3' " +
		" from " +
		" UNTNE_REDUCEDETAIL d " +
		" where " +
		" d.propertyno like '40107%' ";
		querySQL += getCondition1() + getCondition2(3) + getCondition41() + getCondition5();
		querySQL += " ) aa";
		return querySQL;
	}
	
	/**
	 * 交通及運輸設備本期減少(其他)數量
	 */
	protected String querySQL173() {
		String querySQL=" select " +
		" sum(aa.[17-3]) '17-3' " +
		" from ( " +
		" select " +
		" count(*) as '17-3' " +
		" from " +
		" UNTNE_REDUCEDETAIL d " +
		" where " +
		" d.propertyno like '4%' " +
		" and substring(d.propertyno, 1,5) not between '40200' and '40202' " +
		" and substring(d.propertyno, 1,5) not like '40302%'" +
		" and d.propertyno not like '40107%' ";
		querySQL += getCondition1() + getCondition2(3) + getCondition41() + getCondition5();
		querySQL += " ) aa";
		return querySQL;
	}
	
	/**
	 * 交通及運輸設備本期減少(全部)價值
	 */
	protected String querySQL183() {
		String querySQL=" select " +
		" sum(aa.[18-3]) '18-3' " +
		" from ( " +
		" select " +
		" SUM(isnull(d.reducebookvalue,0)) '18-3' " +
		" from " +
		" UNTNE_ADJUSTDETAIL d " +
		" where " +
		" substring(d.propertyno,1,1) = '4' ";
		querySQL += getCondition1() + getCondition2(2) + getCondition41() + getCondition5();
		querySQL += " union " +
		" select " +
		" SUM(isnull(d.adjustbookvalue,0)) '18-3' " +
		" from " +
		" UNTNE_REDUCEDETAIL d " +
		" where " +
		" substring(d.propertyno,1,1) = '4' ";
		querySQL += getCondition1() + getCondition2(3) + getCondition41() + getCondition5();
		querySQL += " ) aa";
		return querySQL;
	}
	
	/**
	 * 雜項設備本期減少(圖書)數量
	 */
	protected String querySQL193() {
		String querySQL=" select "+
		" sum(aa.[19-3]) '19-3' " +
		" from ( " +
		" select " +
		" count(*) as '19-3' " +
		" from " +
		" UNTNE_REDUCEDETAIL d " +
		" where " +
		" substring(d.propertyno,1,3) = '503' ";
		querySQL += getCondition1() + getCondition2(3) + getCondition41() + getCondition5();
		querySQL += " ) aa";
		return querySQL;
	}
	
	/**
	 * 雜項設備本期減少(其他)數量
	 */
	protected String querySQL213() {
		String querySQL=" select " +
		" sum(aa.[21-3]) '21-3' " +
		" from ( " +
		" select " +
		" count(*) as '21-3' " +
		" from " +
		" UNTNE_REDUCEDETAIL d " +
		" where " +
		" (d.propertyno like '5%' or d.propertyno like '6%')" +
		" and d.propertyno not like '503%'" ;
		querySQL += getCondition1() + getCondition2(3) + getCondition41() + getCondition5();
		querySQL += " ) aa";
		return querySQL;
	}
	
	/**
	 * 雜項設備本期減少(全部)價值
	 */
	protected String querySQL223() {
		String querySQL=" select " +
		" sum(aa.[22-3]) '22-3' " +
		" from ( " +
		" select " +
		" SUM(isnull(d.reducebookvalue,0)) '22-3' " +
		" from " +
		" UNTNE_ADJUSTDETAIL d " +
		" where " +
		" (" +
		" substring(d.propertyno,1,1) = '5' " +
		" or substring(d.propertyno,1,1) = '6' " +
		" )";
		querySQL += getCondition1() + getCondition2(2) + getCondition41() + getCondition5();
		querySQL += " union " +
		" select " +
		" SUM(isnull(d.adjustbookvalue,0)) '22-3' " +
		" from " +
		" UNTNE_REDUCEDETAIL d " +
		" where " +
		" (" +
		" substring(d.propertyno,1,1) = '5' " +
		" or substring(d.propertyno,1,1) = '6' " +
		" )";
		querySQL += getCondition1() + getCondition2(3) + getCondition41() + getCondition5();
		querySQL += " ) aa";
		return querySQL;
	}
	
	/**
	 * 有價證券本期減少
	 */
	protected String querySQL233() {
		String querySQL=" select " +
				" sum(aa.[23-3]) '23-3', " +
				" sum(aa.[24-3]) '24-3' " +
				" from ( " +
				" select " +
				" 0 '23-3', " +
				" sum(isnull(d.reducebookvalue,'0')) '24-3' " +
				" from " +
				" UNTNE_ADJUSTDETAIL d " +
				" where " +
				" substring(d.propertyno,1,1) = '9' ";
				querySQL += getCondition1() + getCondition2(2) + getCondition5();
				querySQL += " union " +
				" select " +
				" sum(isnull(d.adjustbookamount,'0')) '23-3', " +
				" sum(isnull(d.adjustbookvalue,'0')) '24-3' " +
				" from " +
				" UNTNE_REDUCEDETAIL d " +
				" where " +
				" substring(d.propertyno,1,1) = '9' ";
				querySQL += getCondition1() + getCondition2(3) + getCondition5();
				querySQL += " ) aa";
				return querySQL;
	}
	
	/**
	 * 權利本期減少
	 */
	protected String querySQL253() {
		String querySQL=" select " +
		" sum(aa.[25-3]) '25-3', " +
		" sum(aa.[26-3]) '26-3' " +
		" from ( " +
		" select " +
		" 0 '25-3', " +
		" sum(isnull(d.reducebookvalue,'0')) '26-3' " +
		" from " +
		" UNTNE_ADJUSTDETAIL d " +
		" where " +
		" substring(d.propertyno,1,1) = '8' ";
		querySQL += getCondition1() + getCondition2(2) + getCondition5();
		querySQL += " union " +
		" select " +
		" sum(isnull(d.adjustbookamount,'0')) '25-3', " +
		" sum(isnull(d.adjustbookvalue,'0')) '26-3' " +
		" from " +
		" UNTNE_REDUCEDETAIL d " +
		" where " +
		" substring(d.propertyno,1,1) = '8' ";
		querySQL += getCondition1() + getCondition2(3) + getCondition5();
		querySQL += " ) aa";
		return querySQL;
	}
	
	private String clearMoneyFormat(Object o){
		String result = o.toString();
		return result.replaceAll(",","");
	}
	
}
