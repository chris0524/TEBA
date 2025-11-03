/*
*<br>程式目的：國產署交換媒體檔 - 目錄總表
*<br>程式代號：UNTGR009R
*<br>撰寫日期：103.09.30
*<br>程式作者：Kang Da
*<br>--------------------------------------------------------
*<br>修改作者　　修改日期　　　修改目的
*<br>--------------------------------------------------------
*/

package unt.sr;

import java.io.File;
import java.sql.ResultSet;
//問題單1814，土改數量計要顯示小數點後兩位
import java.text.DecimalFormat;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import org.apache.commons.io.FileUtils;

import util.Common;
import util.Database;
import util.Datetime;
import util.SuperBean;
import util.TCGHCommon;

public class UNTSR009R extends SuperBean {
		
	private final static String[] PROPERTY_TYPES = {"10","20","31","32","33","40","51","52","53","54","61","62","63","70","80","T0"};
	
	private String isAdminManager;
	private String organID;
	
	public String getIsAdminManager() { return checkGet(isAdminManager); }
	public void setIsAdminManager(String s) { isAdminManager = checkSet(s); }
	public String getOrganID() { return checkGet(organID); }
	public void setOrganID(String s) { organID = checkSet(s); }
	
	private String q_enterOrg;							// 入帳機關
	private String q_enterOrgName;						// 入帳機關-名
	private String q_ownership;							// 權屬
	private String q_differenceKind;					// 財產區分
	private String q_isorganmanager;					// 列印彙總表
	private String q_reportYear;						// 年度
	
	private String q_verify;							// 入帳
	private String q_dataState;							// 資料狀態 
	
	public String getQ_enterOrg() {		return checkGet(q_enterOrg);	}
	public void setQ_enterOrg(String q_enterOrg) {		this.q_enterOrg = checkSet(q_enterOrg);	}
	public String getQ_enterOrgName() {		return checkGet(q_enterOrgName);	}
	public void setQ_enterOrgName(String q_enterOrgName) {		this.q_enterOrgName = checkSet(q_enterOrgName);	}
	public String getQ_ownership() {		return checkGet(q_ownership);	}
	public void setQ_ownership(String q_ownership) {		this.q_ownership = checkSet(q_ownership);	}	
	public String getQ_differenceKind() {		return checkGet(q_differenceKind);	}
	public void setQ_differenceKind(String q_differenceKind) {		this.q_differenceKind = checkSet(q_differenceKind);	}
	public String getQ_isorganmanager() {		return checkGet(q_isorganmanager);	}
	public void setQ_isorganmanager(String q_isorganmanager) {		this.q_isorganmanager = checkSet(q_isorganmanager);	}
	public String getQ_reportYear() {		return checkGet(q_reportYear);	}
	public void setQ_reportYear(String q_reportYear) {		this.q_reportYear = checkSet(q_reportYear);	}
	public String getQ_verify() {		return checkGet(q_verify);	}
	public void setQ_verify(String q_verify) {		this.q_verify = checkSet(q_verify);	}
	public String getQ_dataState() {		return checkGet(q_dataState);	}
	public void setQ_dataState(String q_dataState) {		this.q_dataState = checkSet(q_dataState);	}
	
	private class CalDetail {
		
		private String valuable;
		private String propertytype;
		
		private double amount = 0d;
		private double area = 0d;
		private double value = 0d;
		
		public CalDetail(String valuable, String propertytype) {
			this.valuable = valuable;
			this.propertytype = propertytype;
		}
		
		public void add(double amount, double area, double value) {
			this.amount += amount;
			this.area += area;
			this.value += value;
		}

		public String getValuable() { return valuable; }
		public String getPropertytype() { return propertytype; }
		public double getAmount() { return amount; }
		public double getArea() { return area; }
		public double getValue() { return value; }
	}
	
	private class AllCalData {
		
		private Map<String, CalDetail> details;
		
		public AllCalData() {
			details = new TreeMap<String, CalDetail>();
			
			// 有無數值均須申報 
			// 非珍貴「1」：一般，「2」：珍貴
			String valuable = "1";
			for (int i = 0; i < PROPERTY_TYPES.length; i++) {
				details.put(valuable + "_" + PROPERTY_TYPES[i], new CalDetail(valuable, PROPERTY_TYPES[i]));
			}
			
			// 珍貴
			valuable = "2";
			for (int i = 0; i < PROPERTY_TYPES.length; i++) {
				details.put(valuable + "_" + PROPERTY_TYPES[i], new CalDetail(valuable, PROPERTY_TYPES[i]));
			}
			
		}
		
		private String genKey(String valuable, String type) {
			String prefix = "1_";
			if ("Y".equals(valuable)) {
				prefix = "2_";
			}
			return prefix + type;
		}
		
		public void add(String valuable, String type, double amount, double area, double value) {
			String tmpKey = this.genKey(valuable, type);
			CalDetail calDetail = this.details.get(tmpKey);
			calDetail.add(amount, area, value);
		}
		
		public CalDetail getDetail(String key) {
			return this.details.get(key);
		}
		
		public Set<String> getKeySet() {
			return this.details.keySet();
		}
		
	}
	
	/**
	 * 取得q_reportYear 下一年  不更動q_reportYear 本身
	 * @return
	 */
	private String getQ_reportNextYear() {
		int nextYear = Integer.parseInt(this.getQ_reportYear()) + 1;
		return Common.formatFrontZero(String.valueOf(nextYear), 3);
	}
	
	private String getQuerySQL() {
		String reportNextYear = this.getQ_reportNextYear();
		
		StringBuilder sql = new StringBuilder();
		sql.append(" select valuable, propertytype ")
		   .append(" , round(sum(oldamount),2) as amount ")
		   .append(" , case when propertytype = '10' then Round( CAST((Sum(oldarea) / 10000) AS float), 6) else round(sum(oldarea), 6) end AS area ")
		   .append(" , sum(oldbvsubtotal) as value ")
		   .append(" from UNTGR_UNTGR009R d where 1=1 ");
		
		if ("N".equals(getQ_isorganmanager())) {
			sql.append(" and d.enterorg = ").append(Common.sqlChar(this.getQ_enterOrg()));
		} else if ("Y".equals(this.getQ_isorganmanager()) && "110".equals(this.getQ_differenceKind()) && !TCGHCommon.getSYSCODEName("01", "ETO").equals(this.getQ_enterOrg())) {
			sql.append(" and (d.enterorg = ").append(Common.sqlChar(this.getQ_enterOrg()))
			   .append(" or d.enterorg in (select organid from SYSCA_ORGAN where organsuperior1 = ").append(Common.sqlChar(this.getQ_enterOrg())).append("))");
		} else if ("Y".equals(this.getQ_isorganmanager()) && "110".equals(this.getQ_differenceKind()) && TCGHCommon.getSYSCODEName("01", "ETO").equals(this.getQ_enterOrg())) {
			sql.append(" and d.enterorg in (select organid from SYSCA_ORGAN where ismanager='Y')");
		} else if ("Y".equals(this.getQ_isorganmanager()) && "120".equals(this.getQ_differenceKind())) {
			sql.append(" and (d.enterorg = ").append(Common.sqlChar(getQ_enterOrg()))
				.append(" or d.enterorg in (select organid from SYSCA_ORGAN where organsuperior2 = ").append(Common.sqlChar(getQ_enterOrg())).append(" or organsuperior3=").append(Common.sqlChar(getQ_enterOrg())).append(" ))");
		}
		
		if (!"".equals(this.getQ_ownership())) {
			sql.append(" and d.ownership = ").append(Common.sqlChar(this.getQ_ownership()));
		}
		
		if (!"".equals(this.getQ_differenceKind())) {
			sql.append(" and d.differencekind = ").append(Common.sqlChar(this.getQ_differenceKind()));
		}
		
		if (!"".equals(reportNextYear)) {
			sql.append(" and d.reportyear = ").append(Common.sqlChar(reportNextYear));
		}
		
		sql.append(" and d.reporttype = '1' and d.reportmonth = '01' ");
		sql.append(" group by propertytype,valuable  ");
		sql.append(" order by valuable,propertytype ");
		return sql.toString();
	}
	
	private String genLine(String groupKey, String valuable, String reportYear, String differencekind, String propertytype, double amount, double area, double value) {
		
		String type1 = propertytype.substring(0, 1);
		String type2 = propertytype.substring(1, 2);
		//問題單1814，土改數量計要顯示小數點後兩位
		DecimalFormat df = new DecimalFormat("######0.00");  
		
		StringBuilder line = new StringBuilder();
		line.append(Common.formatFrontZero(groupKey, 6))	//組號  主鍵相同則相同   主鍵為   「統計年」+「統計月」+「財產性質」+「財產別」
			.append("|PB01")	// 資料集代碼
			.append("|X")		// 動作屬性
			.append("|").append(reportYear)	// 統計年
			.append("|12")					// 統計月 固定為每年12月
			.append("|").append("Y".equals(valuable) ? "1" : "2")	// 財產性質 珍貴財產:1  非珍貴:2
			.append("|").append("110".equals(differencekind) ? "1" : "4")	// 財產別
			.append("|").append(type1)	// 財產大類
			.append("|").append(type2);	// 財產中類
		
			if("8".equals(type1) || "T".equals(type1)) {
				line.append("|").append("0.0");
			}else {
				line.append("|").append("2".equals(type1) ? String.valueOf(df.format(amount)) : String.valueOf(amount));
			}
			
			line.append("|").append(String.valueOf(area))
			.append("|").append(String.valueOf(Common.getLong(value)))
			.append("\r\n");
		return line.toString();
	}
	
	private String genEndLine(String groupKey) {
		return Common.formatFrontZero(groupKey, 6) + "|EOR\r\n"; 
	}
	
	private String genLastLine(String enterOrgCode, int rowCNT, String chkStr) {
		return "EOF" + "|" + enterOrgCode + "|" + rowCNT + "|" + chkStr + "|" + Datetime.getYYYYMMDD() + Datetime.getHHMMSS();
	}
	
	public File exportTxt2() throws Exception {
		Database db = null;
		ResultSet rs = null;
		File f = null;
		try {
			
			// 媒體檔 - 機關代碼
	    	String enterOrgCode;
	    	if("110".equals(getQ_differenceKind())){
	    		enterOrgCode = getQ_enterOrg() + "000";
	    	}else{
	    		enterOrgCode = TCGHCommon.getLookup("select fundno from SYSCA_FUNDORGAN where enterorg = " + Common.sqlChar(getQ_enterOrg()));
	    	}
	    	
	    	String fileName = "PB" + enterOrgCode + Datetime.getYYYMMDD() + Datetime.getHHMM() + ".TXT";
			
			StringBuilder content = new StringBuilder();
			
			db = new Database();
			rs = db.querySQL_NoChange(this.getQuerySQL());
			
			AllCalData calData = new AllCalData();
			while (rs.next()) {
				String valuable = rs.getString("valuable");
				String propertytype = rs.getString("propertytype");
				double amount = rs.getDouble("amount");
				double area = rs.getDouble("area");
				double value = rs.getDouble("value");
				calData.add(valuable, propertytype, amount, area, value);
			}
			
			
			//  主鍵為   「統計年」+「統計月」+「財產性質」+「財產別」
			String preGroupKey = "";
			String tmpGroupKey = "";
			int rowCNT = 1; // 起始1 表最後一行
			for (String tmpKey : calData.getKeySet()) {
				CalDetail tmpDetail = calData.getDetail(tmpKey);
				tmpGroupKey = tmpDetail.getValuable();
				if ("".equals(preGroupKey)) {
					preGroupKey = tmpGroupKey;
				} else if (!preGroupKey.equals(tmpGroupKey)) {
					content.append(this.genEndLine(preGroupKey));
					rowCNT++;
					preGroupKey = tmpGroupKey;
				}
				content.append(this.genLine(tmpGroupKey, 
											"1".equals(tmpGroupKey) ? "Y" : "N", 
											this.getQ_reportYear(), 
											this.getQ_differenceKind(), 
											tmpDetail.getPropertytype(), 
											tmpDetail.getAmount(), 
											tmpDetail.getArea(), 
											tmpDetail.getValue()));
				rowCNT ++;
			}
			
			content.append(this.genEndLine(preGroupKey));
			rowCNT++;
			
			// 計算媒體檔檢核碼 - (程式版本+總記錄筆數)×13÷11+(總記錄筆數），其產生結果的小數取末五位其餘無條件捨去
			double programNumber = 1.1;
	    	double d = (programNumber + rowCNT) * 13.0/11.0 + rowCNT;
	    	String chkStr = String.valueOf(Math.floor(d * 100000) / 100000);
			
			content.append(this.genLastLine(enterOrgCode, rowCNT, chkStr));
			
			f = new File(Common.getTempDirectory() + File.separator + fileName.toUpperCase());
			//問題單1814 編碼改為UTF-8
			FileUtils.writeStringToFile(f, content.toString(),"utf-8");
		} finally {
			
			if (rs != null) {
				try {
					rs.close();
				} catch (Exception e) {
					//ignore
				}
				
			}
			
			db.closeAll();
		}
		return f;
	}
}
