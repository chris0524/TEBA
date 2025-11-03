/*
*<br>程式目的：財產增減結存表 
*<br>程式代號：UNTGR009R
*<br>撰寫日期：0950309
*<br>程式作者：Cherry
*<br>--------------------------------------------------------
*<br>修改作者　　修改日期　　　修改目的
*<br>--------------------------------------------------------
*/

package unt.sr;

import java.io.File;
import java.io.FileOutputStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.ResultSet;
import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import util.Common;
import util.Database;
import util.Datetime;
import util.SuperBean;
import util.TCGHCommon;

public class UNTSR008R extends SuperBean{
	
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
	
	String userName;
	public String getUserName(){ return checkGet(userName); }
	public void setUserName(String s){ userName=checkSet(s); }

	private String txtPath;
	private String fileName;
	public String getTxtPath() {return txtPath;}
	public void setTxtPath(String txtPath) {this.txtPath = txtPath;}
	public String getFileName() { return checkGet(fileName); }
	public void setFileName(String s) { fileName = checkSet(s); }
	
	private StringBuilder stb = new StringBuilder();
	private int recordCount=0;

	public void createOutputTxt(){
		String pathString="";
		FileOutputStream fout = null;		
		try{
			File tempDirectory = new File(System.getProperty("java.io.tmpdir"));

			String timeString = Datetime.getHHMMSS();
			
			setFileName(obtainEnterOrgRealCode(Common.get(this.getQ_enterOrg()),Common.get(this.getQ_differenceKind())));
			
			pathString = tempDirectory.getAbsoluteFile() + File.separator + "PG" + this.getFileName() + Common.formatFrontZero((getYYYMMDD() + timeString.substring(0,4)),11) + ".TXT";
		
			fout = new FileOutputStream(pathString);
			
			execLoop(fout);
			
		}catch(Exception e){
			setErrorMsg("產生檔案失敗！！");
			e.printStackTrace();
		}finally{
			if (fout != null) {
				try {
					fout.close();
				} catch (Exception e) {
					//ignore
				}
			}
			this.setTxtPath(pathString);
		}
	}
	
	
	private void execLoop(FileOutputStream fout) throws Exception{		
		Database db = new Database();
		try{
			setQ_valuable("N");
			execInputIntoTXT_EndofTXT(getResultModel());
			setQ_valuable("Y");
			execInputIntoTXT_EndofTXT(getResultModel());
			execInputIntoTXT_EndofTXT();
			//問題單1814 編碼改為UTF-8
			fout.write(stb.toString().getBytes("utf-8"));
			stb.delete(0, stb.length());
			
		}catch(Exception e){
			throw e;
		}finally{
			db.closeAll();
		}
	}
	
	private void execInputIntoTXT_EndofTXT(AllCalData _data){
		String code[] = {"10","20","31","32","33","40","51","52","53","54","61","62","63","70","80","T0"};
		for (int i =0;i < code.length;i++) {
			if (_data.isContainsType(code[i])) {
				if ("N".endsWith(getQ_valuable())) {
					stb.append("000001");//組號
				} else {
					stb.append("000002");//組號
				}
				stb.append("|").append(this.isReportYearOver105() ? "PG01" : "PA01");//僅有主要資料集，「PA01」:104以前, 105以後為PG01
				stb.append("|X");//動作屬性 固定為「X」
				stb.append("|").append(this.getQ_reportYear());//統計年
				/*
				 * 1.中央機關：四季分別填報「03」、「06」、「09」、「12」
				*/
				if ("".equals(this.getQ_reportMonth())) { //統計月，四季分別填報「03」、「06」、「09」、「12」
					if ("1".equals(this.getQ_reportSeason())) {
						stb.append("|03");
					} else if ("2".equals(this.getQ_reportSeason())) {
						stb.append("|06");
					} else if ("3".equals(this.getQ_reportSeason())) {
						stb.append("|09");
					} else if ("4".equals(this.getQ_reportSeason())) {
						stb.append("|12");
					}
				} else {
					stb.append("|").append(this.getQ_reportMonth());//統計月
				}
				
				//財產性質「1」：一般，「2」：珍貴
				if ("N".endsWith(getQ_valuable())) {
					stb.append("|1");
				} else {
					stb.append("|2");
				}
				
				//財產別「1」：公務用，「2」：公共用， 「3」：事業用，「4」：基金用
				if ("110".equals(this.getQ_differenceKind())) { 
					stb.append("|1"); 
				} else {
					stb.append("|4");
				}
				stb.append("|").append(code[i].substring(0,1));
				stb.append("|").append(code[i].substring(1,2));
				
				stb.append(_data.getCalDetail(code[i]).toMediaText(code[i], this.isReportYearOver105()));
				stb.append("\r\n");
				recordCount++;
			}
		}
		if ("N".endsWith(getQ_valuable())) {
			stb.append("000001|EOR\r\n");
			recordCount++;
		} else {
			stb.append("000002|EOR\r\n");
			recordCount++;
		}
		
	}
	
	
	private String obtainEnterOrgRealCode(String enterOrg,String differenceKind){
		String returnStr = "";
		if("110".equals(differenceKind)){
			returnStr=enterOrg+"000";
		} else if("120".equals(differenceKind)){
			
			 Database db = new Database();
			 String strSQL = "";
			 try {
					strSQL +=" select codeID from SYSCA_Code where codeKindID='FUD' and codeid in (select fundno from SYSCA_FUNDORGAN where enterorg ='"+enterOrg+"'"+
					")";
					ResultSet rs = db.querySQL(strSQL);
					if (rs.next()){
						returnStr=rs.getString("codeID");
					}
			 }catch (Exception e) {
				 e.printStackTrace();
				} finally {
				db.closeAll();
				}	
		}
		return returnStr;
	}
	
	private void execInputIntoTXT_EndofTXT(){
		recordCount++;
		
		stb.append("EOF").append("|").append(
				getFileName()).append("|").append(						//機關代碼(管理機關代碼)
				Common.formatFrontZero(String.valueOf(recordCount),6)).append("|");
																		//總記錄	筆數
			
				//媒體檔	檢核碼
				//(程式版本+總記錄筆數)×13÷11+(總記錄筆數），其產生結果的小數取末五位其餘無條件捨去
				//假設程式版本為 1	
				Double programNumber = 1.1;
		 
				Double d=new Double(((programNumber + new Double(recordCount).doubleValue())*13.0)/11.0 + new Double(recordCount).doubleValue());
				String str = String.valueOf(java.lang.Math.floor(d*100000)/100000);
				
		stb.append(str).append("|")								//媒體檔	檢核碼
			.append(get4YMMDD() + Datetime.getHHMMSS());					//日期標章				
	}
	
    public String getYYYMMDD() {
        StringBuffer sb = new StringBuffer();
        Calendar cal = Calendar.getInstance();
        int y = cal.get(Calendar.YEAR) - 1911;
        int m = cal.get(Calendar.MONTH) + 1;
        int d = cal.get(Calendar.DATE);        
        if (y<=99){ sb.append("0"); }
        sb.append(Integer.toString(y));
        if (m<=9){ sb.append("0"); }
        sb.append(Integer.toString(m));
        if (d<=9){ sb.append("0"); }
        sb.append(Integer.toString(d));
        return sb.toString();
    }
	
    //西元年
    public String get4YMMDD() {
        StringBuffer sb = new StringBuffer();
        Calendar cal = Calendar.getInstance();
        int y = cal.get(Calendar.YEAR) ;
        int m = cal.get(Calendar.MONTH) + 1;
        int d = cal.get(Calendar.DATE);        
        if (y<=99){ sb.append("0"); }
        sb.append(Integer.toString(y));
        if (m<=9){ sb.append("0"); }
        sb.append(Integer.toString(m));
        if (d<=9){ sb.append("0"); }
        sb.append(Integer.toString(d));
        return sb.toString();
    }
    
    private class CalDetail {
    	
    	private double 上期結存_數量 = 0d;
    	private double 上期結存_面積 = 0d;
    	private double 上期結存_價值 = 0d;
    	
    	private double 本期新增_數量 = 0d;
    	private double 本期新增_面積 = 0d;
    	private double 本期新增_價值 = 0d;
    	
    	private double 本期減少_數量 = 0d;
    	private double 本期減少_面積 = 0d;
    	private double 本期減少_價值 = 0d;
    	
    	private double 本期折舊_價值 = 0d;
    	
    	private double 本期結存_數量 = 0d;
//    	private double 本期結存_面積 = 0d;
    	private BigDecimal 本期結存_面積 = new BigDecimal(0.0);
    	private double 本期結存_價值 = 0d;
    	
    	public void add上期結存(double amount, double area, double value) {
    		上期結存_數量 += amount;
    		上期結存_面積 += area;
    		上期結存_價值 += value;
    		
    		本期結存_數量 += amount;
//    		本期結存_面積 += area;
    		本期結存_面積 = 本期結存_面積.add(new BigDecimal(String.valueOf(area)));
    		
    		本期結存_價值 += value;
    	}
    	
    	public void add本期新增(double amount, double area, double value) {
    		本期新增_數量 += amount;
    		本期新增_面積 += area;
    		本期新增_價值 += value;
    		
    		本期結存_數量 += amount;
//    		本期結存_面積 += area;
    		本期結存_面積 = 本期結存_面積.add(new BigDecimal(String.valueOf(area))) ;
    		本期結存_價值 += value;
    	}
    	
    	public void add本期減少(double amount, double area, double value) {
    		本期減少_數量 += amount;
    		本期減少_面積 += area;
    		本期減少_價值 += value;
    		
    		本期結存_數量 -= amount;
//    		本期結存_面積 -= area;
    		本期結存_面積 = 本期結存_面積.subtract(new BigDecimal(String.valueOf(area)));
    		本期結存_價值 -= value;
    	}
    	
    	public void add本期折舊(double value) {
    		本期折舊_價值 += value;
    		本期結存_價值 -= value;
    	}
    	
    	public String toMediaText(String type, boolean isNewType) {
    		//TDCM 問題單1814，土改數量計要顯示至小數點後兩位
    		DecimalFormat df = new DecimalFormat("######0.00"); 
			
    		StringBuilder text = new StringBuilder();
    		text.append("|").append(("20".equals(type) ? df.format(this.上期結存_數量) : (long) this.上期結存_數量))
    		    .append("|").append(this.上期結存_面積)
    		    .append("|").append((long) this.上期結存_價值)
    		    .append("|").append(("20".equals(type) ? df.format(this.本期新增_數量) : (long) this.本期新增_數量))
    		    .append("|").append(this.本期新增_面積)
    		    .append("|").append((long) this.本期新增_價值);
    		
    		text.append("|").append(("20".equals(type) ? df.format(this.本期減少_數量) : (long) this.本期減少_數量))
				.append("|").append(this.本期減少_面積);
    		if (isNewType) {
    			// 新格式本期折舊 獨立於本期減少之後
    			text.append("|").append((long) this.本期減少_價值)
    				.append("|").append((long) this.本期折舊_價值);
    		} else {
    			// 舊格式 本期折舊 算入 本期減少
				text.append("|").append((long) (this.本期減少_價值 + this.本期折舊_價值));
    		}
    		
    		text.append("|").append(("20".equals(type) ? df.format(this.本期結存_數量) : (long) this.本期結存_數量))
    			.append("|").append(this.本期結存_面積)
    			.append("|").append((long) this.本期結存_價值);
    		return text.toString();
    	}
    	
    }
    
    private class AllCalData {
    	
    	Map<String, CalDetail> map;
    	
    	public AllCalData() {
    		this.map = new HashMap<String, CalDetail>();
    	}
    	
    	public CalDetail getCalDetail(String type) {
    		CalDetail detail = this.map.get(type);
    		if (detail == null) {
    			detail = new CalDetail();
    			this.map.put(type, detail);
    		}
    		return detail;
    	}
    	
    	public void add上期結存(String type, double amount, double area, double value) {
    		CalDetail tmpDetail = this.getCalDetail(type);
    		tmpDetail.add上期結存(amount, area, value);
    	}
    	
    	public void add本期新增(String type, double amount, double area, double value) {
    		CalDetail tmpDetail = this.getCalDetail(type);
    		tmpDetail.add本期新增(amount, area, value);
    	}
    	
    	public void add本期減少(String type, double amount, double area, double value) {
    		CalDetail tmpDetail = this.getCalDetail(type);
    		tmpDetail.add本期減少(amount, area, value);
    	}
    	
    	public void add本期折舊(String type, double value) {
    		CalDetail tmpDetail = this.getCalDetail(type);
    		tmpDetail.add本期折舊(value);
    	}
    	
    	public double get上期結存總價值() {
    		double total = 0.0d;
    		for (String tmpKey : this.map.keySet()) {
    			if ("T0".equals(tmpKey)) {
    				continue;
    			}
    			total += this.map.get(tmpKey).上期結存_價值;
    		}
    		return total;
    	}
    	
    	public double get本期新增總價值() {
    		double total = 0.0d;
    		for (String tmpKey : this.map.keySet()) {
    			if ("T0".equals(tmpKey)) {
    				continue;
    			}
    			total += this.map.get(tmpKey).本期新增_價值;
    		}
    		return total;
    	}
    	
    	public double get本期減少總價值() {
    		double total = 0.0d;
    		for (String tmpKey : this.map.keySet()) {
    			if ("T0".equals(tmpKey)) {
    				continue;
    			}
    			total += this.map.get(tmpKey).本期減少_價值;
    		}
    		return total;
    	}
    	
    	public double get本期折舊總價值() {
    		double total = 0.0d;
    		for (String tmpKey : this.map.keySet()) {
    			if ("T0".equals(tmpKey)) {
    				continue;
    			}
    			total += this.map.get(tmpKey).本期折舊_價值;
    		}
    		return total;
    	}
    	
    	/**
    	 * 計算合計
    	 */
    	public void calT0() {
    		CalDetail tmpDetail = this.getCalDetail("T0");
    		tmpDetail.add上期結存(0d, 0d, this.get上期結存總價值());
    		tmpDetail.add本期新增(0d, 0d, this.get本期新增總價值());
    		tmpDetail.add本期減少(0d, 0d, this.get本期減少總價值());
    		tmpDetail.add本期折舊(this.get本期折舊總價值());
    	}
    	
    	public boolean isContainsType(String type) {
    		return this.map.containsKey(type);
    	}
    	
    }
	
    /**
     * map 規格說明
     * <br/><B>key set</B> 
     * <br/>10 : 土地 
     * <br/>20 : 土改 
     * <br/>31 : 房屋建築及設備辦攻防 
     * <br/>32 : 房屋建築及設備宿舍 
     * <br/>33 : 房屋建築及設備其他 
     * <br/>40 : 機械及設備 
     * <br/>51 : 交通及運輸設備 船 
     * <br/>52 : 交通及運輸設備 飛機 
     * <br/>53 : 交通及運輸設備 汽機車 
     * <br/>54 : 交通及運輸設備 其他 
     * <br/>61 : 雜項設備 圖書 
     * <br/>62 : 雜項設備 博物 
     * <br/>63 : 雜項設備 其他 
     * <br/>70 : 有價證券 
     * <br/>80 : 權利
     * <br/>T0 : 總值
     * <br/>
     * 註1 :  
     * 價值欄位為填寫財產大類的價值，當「財產中類」欄內容為0或1時需填寫 
     * 例1：土地之財產大類代碼為「1」，財產中類代碼為「0」，該筆資料須填報本欄 
     * 例2：房屋建築及設備之財產大類代碼為「3」，其辦公房屋之財產中類代碼為「1」，該筆資料須填報本欄；其宿舍之財產中類代碼為「2」，該筆資料本欄內容值則填0
     */
	public AllCalData getResultModel() throws Exception {
		
		setYYYMM(Datetime.getMonthDay(getQ_enterDateS(), -1, "month").substring(0, 5));
		Database db = new Database();
		AllCalData _data = new AllCalData();

		//#region 上期結存 from UNTGR_UNTGR009R  
		// 上期結存-價值總額
		ResultSet rs = db.querySQL(queryUNTGR_UNTGR009R());
		int count = 0;
		while(rs.next()) {
			count++;
			if ("10".equals(rs.getString("propertytype"))) { //土地
				DecimalFormat df = new DecimalFormat("#.######");
				double x = rs.getDouble("area")/10000;
				String s = df.format(x);
				_data.add上期結存("10", rs.getDouble("amount"), Double.parseDouble(s), rs.getDouble("bvsubtotal"));
				
			} else if ("20".equals(rs.getString("propertytype"))) { //土地改良
				_data.add上期結存("20", rs.getDouble("amount"), 0d, rs.getDouble("bvsubtotal"));
				
			} else if ("31".equals(rs.getString("propertytype"))) { //房屋建築及設備辦公房
				_data.add上期結存("31", rs.getDouble("amount"), rs.getDouble("area"), rs.getDouble("bvsubtotal"));
				
			} else if ("32".equals(rs.getString("propertytype"))) { //房屋建築及設備宿舍
				_data.add上期結存("31", 0d, 0d, rs.getDouble("bvsubtotal"));// 註1
				_data.add上期結存("32", rs.getDouble("amount"), rs.getDouble("area"), 0d);
				
			} else if ("33".equals(rs.getString("propertytype"))) { //房屋建築及設備其他
				_data.add上期結存("31", 0d, 0d, rs.getDouble("bvsubtotal")); // 註1
				_data.add上期結存("33", rs.getDouble("amount"), 0d, 0d);
				
			} else if ("40".equals(rs.getString("propertytype"))) { //機械及設備
				_data.add上期結存("40", rs.getDouble("amount"), 0d, rs.getDouble("bvsubtotal"));
				
			} else if ("51".equals(rs.getString("propertytype"))) { //交通及運輸設備船
				_data.add上期結存("51", rs.getDouble("amount"), 0d, rs.getDouble("bvsubtotal"));

			} else if ("52".equals(rs.getString("propertytype"))) { //交通及運輸設備飛機
				_data.add上期結存("51", 0d, 0d, rs.getDouble("bvsubtotal"));// 註1
				_data.add上期結存("52", rs.getDouble("amount"), 0d, 0d);
				
			} else if ("53".equals(rs.getString("propertytype"))) { //交通及運輸設備汽（機）車
				_data.add上期結存("51", 0d, 0d, rs.getDouble("bvsubtotal"));// 註1
				_data.add上期結存("53", rs.getDouble("amount"), 0d, 0d);
				
			} else if ("54".equals(rs.getString("propertytype"))) { //交通及運輸設備其他
				_data.add上期結存("51", 0d, 0d, rs.getDouble("bvsubtotal"));// 註1
				_data.add上期結存("54", rs.getDouble("amount"), 0d, 0d);
				
			} else if ("61".equals(rs.getString("propertytype"))) { //雜項設備總值圖書
				_data.add上期結存("61", rs.getDouble("amount"), 0d, rs.getDouble("bvsubtotal"));

			} else if ("62".equals(rs.getString("propertytype"))) { //雜項設備總值博物(限「珍貴」)
				if ("Y".equals(this.getQ_valuable())) { //(限「珍貴」)
					_data.add上期結存("61", 0d, 0d, rs.getDouble("bvsubtotal"));// 註1
					_data.add上期結存("62", rs.getDouble("amount"), 0d, 0d);
				}
			} else if ("63".equals(rs.getString("propertytype"))) { //雜項設備總值其他
				_data.add上期結存("61", 0d, 0d, rs.getDouble("bvsubtotal"));// 註1
				_data.add上期結存("63", rs.getDouble("amount"), 0d, 0d);
				
			} else if ("70".equals(rs.getString("propertytype"))) { //有價證券
				_data.add上期結存("70", rs.getDouble("amount"), 0d, rs.getDouble("bvsubtotal"));
				
			} else if ("80".equals(rs.getString("propertytype"))) { //權利
				_data.add上期結存("80", rs.getDouble("amount"), 0d, rs.getDouble("bvsubtotal"));

			}
		}
		//#end 上期結存 from UNTGR_UNTGR009R  

		//#region 上期結存from月結備份檔  (if UNTGR_UNTGR009R沒有資料時)
		// 1580-1: 上期結存一律從初始檔取得，有問題請修正初始檔資料，並且已經移除主檔備份檔，以下程式不予執行
		/*
		if (count == 0) { //如果初始檔沒有資料
			
			//土地
			rs = db.querySQL(querySQL011()); 
			if (rs.next()) {
				_data.add上期結存("10", rs.getDouble("1-1"), rs.getDouble("2-1"), rs.getDouble("3-1"));
			}

			//土地改良
			rs = db.querySQL(querySQL041()); 
			if (rs.next()) {
				_data.add上期結存("20", rs.getDouble("4-1"), 0d, rs.getDouble("5-1"));
			}

			//房屋建築及設備辦公房
			rs = db.querySQL(querySQL061()); 
			if (rs.next()) {
				_data.add上期結存("31", rs.getDouble("6-1"), rs.getDouble("7-1"), 0d);
			}

			//房屋建築及設備宿舍
			rs = db.querySQL(querySQL081()); 
			if (rs.next()) {
				_data.add上期結存("32", rs.getDouble("8-1"), rs.getDouble("9-1"), 0d);
			}

			//房屋建築及設備其他
			rs = db.querySQL(querySQL101()); 
			if (rs.next()) {
				_data.add上期結存("33", rs.getDouble("10-1"), 0d, 0d);
			}

			//房屋建築及設備上期結存(價值)  
			rs = db.querySQL(querySQL111()); 
			if (rs.next()) {
				_data.add上期結存("31", 0d, 0d, rs.getDouble("11-1"));// 註1
			}

			//機械及設備上期結存
			rs = db.querySQL(querySQL121()); 
			if (rs.next()) {
				_data.add上期結存("40", rs.getDouble("12-1"), 0d, rs.getDouble("13-1"));
			}

			//交通及運輸設備上期結存(船)數量
			rs = db.querySQL(querySQL141()); 
			if (rs.next()) {
				_data.add上期結存("51", rs.getDouble("14-1"), 0d, 0d);
			}

			//交通及運輸設備上期結存(飛機)數量
			rs = db.querySQL(querySQL151()); 
			if (rs.next()) {
				_data.add上期結存("52", rs.getDouble("15-1"), 0d, 0d);
			}

			//交通及運輸設備上期結存(汽機車)數量
			rs = db.querySQL(querySQL161()); 
			if (rs.next()) {
				_data.add上期結存("53", rs.getDouble("16-1"), 0d, 0d);
			}

			//交通及運輸設備上期結存(全部)價值
			rs = db.querySQL(querySQL181()); 
			if (rs.next()) {
				_data.add上期結存("51", 0d, 0d, rs.getDouble("18-1"));	//註1
			}

			//雜項設備上期結存(圖書)數量
			rs = db.querySQL(querySQL191()); 
			if (rs.next()) {
				_data.add上期結存("61", rs.getDouble("19-1"), 0d, 0d);
			}

			//雜項設備上期結存(博物)數量  (限「珍貴」)
			if ("Y".equals(this.getQ_valuable())) {
				rs = db.querySQL(querySQL201()); 
				if (rs.next()) {
					_data.add上期結存("62", rs.getDouble("20-1"), 0d, 0d);
				}
			}

			//雜項設備上期結存(其他)數量
			rs = db.querySQL(querySQL211()); 
			if (rs.next()) {
				_data.add上期結存("63", rs.getDouble("21-1"), 0d, 0d);
			}

			//雜項設備上期結存(全部)價值
			rs = db.querySQL(querySQL221()); 
			if (rs.next()) {
				_data.add上期結存("61", 0d, 0d, rs.getLong("22-1")); // 註1
			}

			//有價證券上期結存
			rs = db.querySQL(querySQL231()); 
			if (rs.next()) {
				_data.add上期結存("70", rs.getDouble("23-1"), 0d, rs.getLong("24-1"));
			}

			//權利上期結存
			rs = db.querySQL(querySQL251()); 
			if (rs.next()) {
				_data.add上期結存("80", rs.getDouble("25-1"), 0d, rs.getDouble("26-1"));
			}
		}
		*/
		//#end  上期結存from月結備份檔  (if UNTGR_UNTGR009R沒有資料時)   
			
		//#region 各財產本期新增  
		//土地本期增加
		rs = db.querySQL(querySQL012()); 
		if (rs.next()) {
			_data.add本期新增("10", rs.getDouble("1-2"), rs.getDouble("2-2"), rs.getDouble("3-2"));
		}
		
		//土地改良物本期增加
		rs = db.querySQL(querySQL042()); 
		if (rs.next()) {
			double amount = Common.getNumeric(rs.getDouble("4-2"));
			
			//問題單2231 
			if( "A42010000G".equals(this.getQ_enterOrg()) && "120".equals(this.getQ_differenceKind()) && "111".equals(this.getQ_reportYear()) && ("07".equals(this.getQ_reportMonth()) || "3".equals(this.getQ_reportSeason()))) {
				amount += 1;
			}
			_data.add本期新增("20", amount, 0d, rs.getDouble("5-2"));
		}
		
		// 房屋建築及設備(辦公房屋)本期增加
		rs = db.querySQL(querySQL062()); 
		if (rs.next()) {
			int amount = Common.getInt((rs.getInt("6-2")));
			BigDecimal area = rs.getString("7-2") == null ? new BigDecimal("0") : new BigDecimal(rs.getString("7-2"));
			
			if (TCGHCommon.getSYSCODEName("02", "ETO").equals(this.getQ_enterOrg()) && "120".equals(this.getQ_differenceKind()) && "110".equals(this.getQ_reportYear()) && ("04".equals(this.getQ_reportMonth()) || "2".equals(this.getQ_reportSeason()))) {
				amount += 2;
				area = area.add(new BigDecimal("32748.89"));
			}
			_data.add本期新增("31", amount, area.setScale(6, RoundingMode.HALF_UP).doubleValue(), 0d);
		}
		
		// 房屋建築及設備(宿舍)本期增加
		rs = db.querySQL(querySQL082()); 
		if (rs.next()) {
			_data.add本期新增("32", rs.getLong("8-2"), rs.getDouble("9-2"), 0d);
		}
		
		//房屋建築及設備(其他)本期增加
		rs = db.querySQL(querySQL102()); 
		if (rs.next()) {
			_data.add本期新增("33", rs.getDouble("10-2"), 0d, 0d);
		}
		
		//房屋建築及設備本期增加(價值)
		rs = db.querySQL(querySQL112());
		if (rs.next()) {
			_data.add本期新增("31", 0d, 0d, rs.getDouble("11-2"));	// 註1
		}
		
		//機械及設備本期增加
		rs = db.querySQL(querySQL122()); 
		if (rs.next()) {
			_data.add本期新增("40", rs.getDouble("12-2"), 0d, rs.getDouble("13-2"));
		}
		
		//交通及運輸設備本期增加(船)數量
		rs = db.querySQL(querySQL142()); 
		if (rs.next()) {
			_data.add本期新增("51", rs.getDouble("14-2"), 0d, 0d);
		}
		
		//交通及運輸設備本期增加(飛機)數量
		rs = db.querySQL(querySQL152()); 
		if (rs.next()) {
			_data.add本期新增("52", rs.getDouble("15-2"), 0d, 0d);
		}
		
		//交通及運輸設備本期增加(汽機車)數量
		rs = db.querySQL(querySQL162()); 
		if (rs.next()) {
			_data.add本期新增("53", rs.getLong("16-2"), 0d, 0d);
		}
		
		//交通及運輸設備本期增加(其他)數量
		rs = db.querySQL(querySQL172()); 
		if (rs.next()) {
			double amount = Common.getNumeric(rs.getDouble("17-2"));
			
			//問題單2231 
			if( "A42010000G".equals(this.getQ_enterOrg()) && "120".equals(this.getQ_differenceKind()) && "111".equals(this.getQ_reportYear()) && ("07".equals(this.getQ_reportMonth()) || "3".equals(this.getQ_reportSeason()))) {
				amount += 1;
			}
			_data.add本期新增("54", amount, 0d, 0d);
		}
		
		//交通及運輸設備本期增加(全部)價值
		rs = db.querySQL(querySQL182()); 
		if (rs.next()) {
			_data.add本期新增("51", 0d, 0d, rs.getDouble("18-2"));	// 註1
		}
		
		//雜項設備本期增加(圖書)數量
		rs = db.querySQL(querySQL192()); 
		if (rs.next()) {
			_data.add本期新增("61", rs.getDouble("19-2"), 0d, 0d);
		}
		
		//雜項設備本期增加(博物)數量  (限「珍貴」)
		if ("Y".equals(this.getQ_valuable())) {
			rs = db.querySQL(querySQL202()); 
			if (rs.next()) {
				_data.add本期新增("62", rs.getDouble("20-2"), 0d, 0d);
			}
		}
		
		//雜項設備本期增加(其他)數量
		rs = db.querySQL(querySQL212()); 
		if (rs.next()) {
			double amount = rs.getDouble("21-2");
			if( "A42010000G".equals(this.getQ_enterOrg()) && "110".equals(this.getQ_differenceKind()) && "111".equals(this.getQ_reportYear()) && ("07".equals(this.getQ_reportMonth()) || "3".equals(this.getQ_reportSeason()))) {
				amount += 3;
			}
			_data.add本期新增("63", amount, 0d, 0d);
		}
		
		//雜項設備本期增加(全部)價值
		rs = db.querySQL(querySQL222()); 
		if (rs.next()) {
			int sumAdd = Common.getInt(rs.getString("22-2"));
			
			if( "A42010000G".equals(this.getQ_enterOrg()) && "110".equals(this.getQ_differenceKind()) && "111".equals(this.getQ_reportYear()) && ("07".equals(this.getQ_reportMonth()) || "3".equals(this.getQ_reportSeason()))) {
				sumAdd += 34711;
			}
			_data.add本期新增("61", 0d, 0d, sumAdd);	// 註1
		}

		// 非珍貴財產才計算權利&有價證券
		if ("N".equals(getQ_valuable())) {
			//有價證券本期增加
			rs = db.querySQL(querySQL232()); 
			if (rs.next()) {
				_data.add本期新增("70", rs.getDouble("23-2"), 0d, rs.getDouble("24-2"));
			}
			
			//權利本期增加
			rs = db.querySQL(querySQL252()); 
			if (rs.next()) {
				_data.add本期新增("80", rs.getDouble("25-2"), 0d, rs.getDouble("26-2"));
			}
		}
		//#end 各財產本期新增  
			
		//#region 各財產本期減少  
		// 土地本期減少
		rs = db.querySQL(querySQL013()); 
		if (rs.next()) {
			_data.add本期減少("10", rs.getDouble("1-3"), rs.getDouble("2-3"), rs.getDouble("3-3"));
		}

		//土地改良物本期減少
		rs = db.querySQL(querySQL043()); 
		if (rs.next()) {
			_data.add本期減少("20", rs.getDouble("4-3"), 0d, rs.getDouble("5-3"));
		}

		//房屋建築及設備(辦公房屋)本期減少
		rs = db.querySQL(querySQL063()); 
		if (rs.next()) {
			_data.add本期減少("31", rs.getDouble("6-3"), rs.getDouble("7-3"), 0d);
		}
		
		//房屋建築及設備(宿舍)本期減少
		rs = db.querySQL(querySQL083()); 
		if (rs.next()) {
			_data.add本期減少("32", rs.getDouble("8-3"), rs.getDouble("9-3"), 0d);
		}
		
		//房屋建築及設備(其他)本期減少
		rs = db.querySQL(querySQL103()); 
		if (rs.next()) {
			int nother = rs.getInt("10-3");
			if ("A42010000G".equals(this.getQ_enterOrg()) && "120".equals(this.getQ_differenceKind()) && "110".equals(this.getQ_reportYear()) && ("04".equals(this.getQ_reportMonth()) || "2".equals(this.getQ_reportSeason()))) {
				nother += 2 ;
			}
			_data.add本期減少("33", nother, 0d, 0d);
		}

		//房屋建築及設備本期減少(價值)
		rs = db.querySQL(querySQL113()); 
		if (rs.next()) {
			_data.add本期減少("31", 0d, 0d, rs.getDouble("11-3"));	// 註1
		}
		
		//機械及設備本期減少
		rs = db.querySQL(querySQL123()); 
		if (rs.next()) {
			_data.add本期減少("40", rs.getDouble("12-3"), 0d, rs.getDouble("13-3"));
		}
		
		//交通及運輸設備本期減少(船)數量
		rs = db.querySQL(querySQL143()); 
		if (rs.next()) {
			_data.add本期減少("51", rs.getDouble("14-3"), 0d, 0d);
		}

		//交通及運輸設備本期減少(飛機)數量
		rs = db.querySQL(querySQL153()); 
		if (rs.next()) {
			_data.add本期減少("52", rs.getDouble("15-3"), 0d, 0d);
		}

		//交通及運輸設備本期減少(汽機車)數量
		rs = db.querySQL(querySQL163()); 
		if (rs.next()) {
			_data.add本期減少("53", rs.getDouble("16-3"), 0d, 0d);
		}

		// 交通及運輸設備本期減少(其他)數量
		rs = db.querySQL(querySQL173()); 
		if (rs.next()) {
			_data.add本期減少("54", rs.getDouble("17-3"), 0d, 0d);
		}

		//交通及運輸設備本期減少(其他)價值
		rs = db.querySQL(querySQL183()); 
		if (rs.next()) {
			_data.add本期減少("51", 0d, 0d, rs.getDouble("18-3"));	// 註1
		}

		//雜項設備本期減少(圖書)數量
		rs = db.querySQL(querySQL193()); 
		if (rs.next()) {
			_data.add本期減少("61", rs.getDouble("19-3"), 0d, 0d);
		}

		//雜項設備本期減少(博物)數量 (限「珍貴」)
		if ("Y".equals(this.getQ_valuable())) {
			rs = db.querySQL(querySQL203()); 
			if (rs.next()) {
				_data.add本期減少("62", rs.getDouble("20-3"), 0d, 0d);
			}
		}

		//雜項設備本期減少(其他)數量
		rs = db.querySQL(querySQL213()); 
		if (rs.next()) {
			int amount = Common.getInt(rs.getInt("21-3"));
			
			//問題單2231 
			if( "A42010000G".equals(this.getQ_enterOrg()) && "120".equals(this.getQ_differenceKind()) && "111".equals(this.getQ_reportYear()) && ("07".equals(this.getQ_reportMonth()) || "3".equals(this.getQ_reportSeason()))) {
				amount += 3;
			}
			_data.add本期減少("63", amount, 0d, 0d);
		}

		//雜項設備本期減少(全部)價值
		rs = db.querySQL(querySQL223()); 
		if (rs.next()) {
			int sumReduce = Common.getInt(rs.getString("22-3"));
			
			if( "A42010000G".equals(this.getQ_enterOrg()) && "110".equals(this.getQ_differenceKind()) && "111".equals(this.getQ_reportYear()) && ("07".equals(this.getQ_reportMonth()) || "3".equals(this.getQ_reportSeason()))) {
				sumReduce -= 351;
			}
			_data.add本期減少("61", 0d, 0d, sumReduce);	// 註1
		}

		// 非珍貴財產才計算權利&有價證券
		if ("N".equals(getQ_valuable())) {
			//有價證券本期減少
			rs = db.querySQL(querySQL233()); 
			if (rs.next()) {
				_data.add本期減少("70", rs.getDouble("23-3"), 0d, rs.getDouble("24-3"));
			}

			//權利本期減少
			rs = db.querySQL(querySQL253()); 
			if (rs.next()) {
				_data.add本期減少("80", rs.getDouble("25-3"), 0d, rs.getDouble("26-3"));
			}
		}
		//#end 各財產本期減少  
			
		//#region 各財產本期折舊
		//土地改良物本期折舊
		rs = db.querySQL(querySQL055());
		if (rs.next()) {
			_data.add本期折舊("20", rs.getDouble("5-5"));
		}

		//房屋建築及設備本期折舊
		rs = db.querySQL(querySQL115());
		if (rs.next()) {
			_data.add本期折舊("31", rs.getDouble("11-5"));
		}

		//機械設備本期折舊
		rs = db.querySQL(querySQL135());
		if (rs.next()) {
			_data.add本期折舊("40", rs.getDouble("13-5"));
		}

		//交通及運輸設備本期折舊
		rs = db.querySQL(querySQL185());
		if (rs.next()) {
			_data.add本期折舊("51", rs.getDouble("18-5"));
		}
		
		//雜項設備本期折舊
		rs = db.querySQL(querySQL225());
		if (rs.next()) {
			_data.add本期折舊("61", rs.getDouble("22-5"));
		}
		
		// 權利本期折舊
		rs = db.querySQL(querySQL555());
		if (rs.next()) {
			_data.add本期折舊("80", rs.getDouble("55-5"));
		}
		//#end 各財產本期折舊 
		
		_data.calT0();
		return _data;
	}
	
	//查詢條件1:依所選的「列印彙總表(isorganmanager)」控制各table「入帳機關(enterOrg)」需符合的條件
	protected String getCondition1(){
		String Condition="";
		if ("N".equals(getQ_isorganmanager())) {
			if ("".equals(getQ_enterorgKind1())){
				Condition += " and d.enterorg=" + Common.sqlChar(getQ_enterOrg());
			}else {
				Condition += " and ( d.enterorg=" + Common.sqlChar(getQ_enterOrg()) + getCondition1_1() + ")";
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
	
	//查詢條件4:珍貴財產
	protected String getCondition4(){
		String Condition="";
		if (!"".equals(Common.get(getQ_valuable())))	//珍貴財產
			Condition += " and d.valuable = " + Common.sqlChar(getQ_valuable());
		return Condition;
	}
	
	//查詢條件4-1:珍貴財產(動產用)
	protected String getCondition41(){
		String Condition="";
		if (!"".equals(Common.get(getQ_valuable())))	//珍貴財產
			Condition += " and m.valuable = " + Common.sqlChar(getQ_valuable());
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
	
	/**
	 * @return q_reportYear >= 105 ? true : false;
	 */
	private boolean isReportYearOver105() {
		return Common.getInt(this.getQ_reportYear()) >= 105;
	}
	
	/**
	 * @param prefix
	 * @return q_reportYear >= 105 ? prefix + netvalue : prefix + bookvalue
	 */
	protected String getBVorNV(String prefix) {
		if (this.isReportYearOver105()) {
			return prefix + "netvalue";
		} else {
			return prefix + "bookvalue";
		}
	}
	
	/**
	 * @param prefix
	 * @return q_reportYear >= 105 ? prefix + addnetvalue : prefix + addbookvalue
	 */
	protected String getAddBVorNV(String prefix) {
		if (this.isReportYearOver105()) {
			return prefix + "addnetvalue";
		} else {
			return prefix + "addbookvalue";
		}
	}
	
	/**
	 * @param prefix
	 * @return q_reportYear >= 105 ? prefix + reducenetvalue : prefix + reducebookvalue
	 */
	protected String getReduceBVorNV(String prefix) {
		if (this.isReportYearOver105()) {
			return prefix + "reducenetvalue";
		} else {
			return prefix + "reducebookvalue";
		}
	}
	
	/**
	 * @param prefix
	 * @return q_reportYear >= 105 ? prefix + adjustnetvalue : prefix + adjustbookvalue
	 */
	protected String getAdjustBVorNV(String prefix) {
		if (this.isReportYearOver105()) {
			return prefix + "adjustnetvalue";
		} else {
			return prefix + "adjustbookvalue";
		}
	}
	
	//新增 :查詢條件依所選的「及所屬」控制各table「入帳機關(enterOrg)」需符合的條件
	protected String getCondition1_1(){
		String Condition="";
		if ("2".equals(getQ_enterorgKind1()) && "120".equals(getQ_differenceKind()) && TCGHCommon.getSYSCODEName("02", "ETO").equals(getQ_enterOrg())) {
			Condition += " or d.enterorg in (select organid from SYSCA_ORGAN where organsuperior2=" + Common.sqlChar(getQ_enterOrg())+" or organsuperior3=" + Common.sqlChar(getQ_enterOrg())+" )";
		}
		return Condition;
	}
	
	//增減結存表初始檔
	protected String queryUNTGR_UNTGR009R(){
		String querySQL=" select propertytype,sum(oldamount) amount,sum(oldarea) area,sum(oldbvsubtotal) bvsubtotal " +
			" from " +
			" UNTGR_UNTGR009R d " +
			" where 1=1 ";
		querySQL += getCondition1();
		if (!"".equals(Common.get(getQ_ownership())))		//權屬條件
			querySQL += " and d.ownership = " + Common.sqlChar(getQ_ownership());
		if (!"".equals(Common.get(getQ_differenceKind())))	//財產區分別
			querySQL += " and d.differencekind = " + Common.sqlChar(getQ_differenceKind());
		if (!"".equals(Common.get(getQ_valuable())))		//入帳
			querySQL += " and d.valuable = " + Common.sqlChar(getQ_valuable());
//		if (!"".equals(Common.get(getQ_reportType())))		//報表總類
//			querySQL += " and d.reporttype = " + Common.sqlChar(getQ_reportType());
		if (!"".equals(Common.get(getQ_reportYear())))		//報表年度
			querySQL += " and d.reportyear = " + Common.sqlChar(getQ_reportYear());
//		if (!"".equals(Common.get(getQ_reportMonth())))		//報表月份
//			querySQL += " and d.reportmonth = " + Common.sqlChar(getQ_reportMonth());
//		if (!"".equals(Common.get(getQ_reportSeason())))	//報表季別
//			querySQL += " and d.reportseason = " + Common.sqlChar(getQ_reportSeason());
		querySQL += " and d.reporttype = " + Common.sqlChar("1");
		querySQL += " and d.reportmonth = " + Common.sqlChar(getQ_enterDateS().substring(3,5));
		querySQL += " group by propertytype ";
		return querySQL;
	}
	
	//土地上期結存
	protected String querySQL011(){
		String querySQL=" select " +
		" COUNT(1) '1-1', " +
		" SUM(isnull(d.holdarea,'0'))/10000 '2-1', " +
		" SUM(isnull(d.bookvalue,'0')) '3-1' " +
		" from " +
		" UNTLA_LAND_" + getYYYMM() + " d " +
		" where " +
		" substring(d.propertyno,1,1) = '1' " +
		" and substring(d.propertyno,1,3) <> '111'";
		querySQL += getCondition1() + getCondition3() + getCondition4() + getCondition5();
		return querySQL;
	}
	
	//土地改良物上期結存
	protected String querySQL041(){
		String querySQL=" select " +
		" COUNT(1) '4-1', " +
		// " sum(case d.differencekind when '110' then isnull(d.bookvalue,'0') else isnull(d.netvalue,'0') end) '5-1' " +
		" sum(case d.differencekind when '110' then isnull(" + this.getBVorNV("d.") + ",'0') else isnull(d.netvalue,'0') end) '5-1' " +
		" from " +
		" UNTRF_ATTACHMENT_" + getYYYMM() + " d " +
		" where " +
		" substring(d.propertyno,1,3) = '111'";
		querySQL += getCondition1() + getCondition3() + getCondition4() + getCondition5();
		return querySQL;
	}
	
	//房屋建築及設備(辦公房屋)上期結存
	protected String querySQL061(){
		String querySQL=" select " +
		" COUNT(1) '6-1', " +
		" SUM(isnull(d.holdarea,'0')) '7-1' " +
		" from " +
		" UNTBU_BUILDING_" + getYYYMM() + " d " +
		" where substring(d.propertyno,1,5) >= '20000' and substring(d.propertyno,1,5) <= '20105'";
		querySQL += getCondition1() + getCondition3() + getCondition4() + getCondition5();
		return querySQL;
	}
	
	//房屋建築及設備(宿舍)上期結存
	protected String querySQL081(){
		String querySQL=" select " +
			" COUNT(1) '8-1', " +
			" SUM(isnull(d.holdarea,'0')) '9-1' " +
			" from " +
			" UNTBU_BUILDING_" + getYYYMM() + " d " +
			" where substring(d.propertyno,1,5) = '20106'";
		querySQL += getCondition1() + getCondition3() + getCondition4() + getCondition5();
		return querySQL;
	}
	
	//房屋建築及設備(其他)上期結存
	protected String querySQL101(){
		String querySQL=" select " +
		" COUNT(1) '10-1' " +
		" from " +
		" UNTBU_BUILDING_" + getYYYMM() + " d " +
		" where " +
		" substring(d.propertyno,1,1) = '2'  " +		
		" and substring(d.propertyno,1,5) > '20106'";
		querySQL += getCondition1() + getCondition3() + getCondition4() + getCondition5();
		return querySQL;
	}

	//房屋建築及設備上期結存(價值)
	protected String querySQL111(){
		String querySQL=" select " +
		//" sum(case d.differencekind when '110' then isnull(d.bookvalue,'0') else isnull(d.netvalue,'0') end) '11-1' " +
		" sum(case d.differencekind when '110' then isnull(" + this.getBVorNV("d.") + ",'0') else isnull(d.netvalue,'0') end) '11-1' " +
		" from " +
		" UNTBU_BUILDING_" + getYYYMM() + " d " +
		" where " +
		" substring(d.propertyno,1,1) = '2'";
		querySQL += getCondition1() + getCondition3() + getCondition4() + getCondition5();
		return querySQL;
	}
	
	//機械及設備上期結存
	protected String querySQL121(){
		String querySQL=" select " +
		" sum(isnull(d.bookamount,'0')) '12-1', " +
		//" sum(case d.differencekind when '110' then isnull(d.bookvalue,'0') else isnull(d.netvalue,'0') end) '13-1' " +
		" sum(case d.differencekind when '110' then isnull(" + this.getBVorNV("d.") + ",'0') else isnull(d.netvalue,'0') end) '13-1' " +
		" from " +
		" UNTMP_MOVABLE_" + getYYYMM() + " m " +
		" inner join UNTMP_MOVABLEDETAIL_" + getYYYMM() + " d on " +
		" m.enterorg=d.enterorg  " +
		" and m.ownership=d.ownership  " +
		" and m.lotno=d.lotno  " +
		" and m.propertyno=d.propertyno " +
		" and m.differencekind=d.differencekind " +
		" where " +
		" substring(d.propertyno,1,1) = '3'";
		querySQL += getCondition1() + getCondition41() + getCondition5();
		return querySQL;
	}
	
	//交通及運輸設備上期結存(船)數量
	protected String querySQL141(){
		String querySQL=" select " +
		" sum(isnull(d.bookamount,'0')) '14-1' " +
		" from " +
		" UNTMP_MOVABLE_" + getYYYMM() + " m " +
		" inner join UNTMP_MOVABLEDETAIL_" + getYYYMM() + " d on " +
		" m.enterorg=d.enterorg  " +
		" and m.ownership=d.ownership  " +
		" and m.lotno=d.lotno  " +
		" and m.propertyno=d.propertyno " +
		" and m.differencekind=d.differencekind " +
		" where " +
		" substring(d.propertyno,1,5) >= '40200' and   substring(d.propertyno,1,5) <= '40202' ";
		querySQL += getCondition1() + getCondition41() + getCondition5();
		return querySQL;
	}
	
	//交通及運輸設備上期結存(飛機)數量
	protected String querySQL151(){
		String querySQL=" select " +
		" sum(isnull(d.bookamount,'0')) '15-1' " +
		" from " +
		" UNTMP_MOVABLE_" + getYYYMM() + " m " +
		" inner join UNTMP_MOVABLEDETAIL_" + getYYYMM() + " d on " +
		" m.enterorg=d.enterorg  " +
		" and m.ownership=d.ownership  " +
		" and m.lotno=d.lotno  " +
		" and m.propertyno=d.propertyno " +
		" and m.differencekind=d.differencekind " +
		" where " +
		" substring(d.propertyno,1,5) >= '40302' and substring(d.propertyno,1,5) <= '40303' ";
		querySQL += getCondition1() + getCondition41() + getCondition5();
		return querySQL;
	}
	
	//交通及運輸設備上期結存(汽機車)數量
	protected String querySQL161(){
		String querySQL=" select " +
		" sum(isnull(d.bookamount,'0')) '16-1' " +
		" from " +
		" UNTMP_MOVABLE_" + getYYYMM() + " m " +
		" inner join UNTMP_MOVABLEDETAIL_" + getYYYMM() + " d on " +
		" m.enterorg=d.enterorg " +
		" and m.ownership=d.ownership  " +
		" and m.lotno=d.lotno " +
		" and m.propertyno=d.propertyno " +
		" and m.differencekind=d.differencekind " +
		" where " +
		" substring(d.propertyno,1,5) = '40107' " ;
		querySQL += getCondition1() + getCondition41() + getCondition5();
		return querySQL;
	}
	
	//交通及運輸設備上期結存(其他)數量
	protected String querySQL171(){
		String querySQL=" select " +
			" sum(isnull(d.bookamount,'0')) '17-1' " +
			" from " +
			" UNTMP_MOVABLE_" + getYYYMM() + " m " +
			" inner join UNTMP_MOVABLEDETAIL_" + getYYYMM() + " d on " +
			" m.enterorg=d.enterorg  " +
			" and m.ownership=d.ownership  " +
			" and m.lotno=d.lotno  " +
			" and m.propertyno=d.propertyno " +
			" and m.differencekind=d.differencekind " +
			" where " +
			" substring(d.propertyno,1,1) = '4' " +
			" and substring(d.propertyno,1,5) <> '40200' " +
			" and substring(d.propertyno,1,5) <> '40201' " +
			" and substring(d.propertyno,1,5) <> '40202' " +
			" and substring(d.propertyno,1,5) <> '40302' " +
			" and substring(d.propertyno,1,5) <> '40303' " +
			" and substring(d.propertyno,1,5) <> '40107' " ;
		querySQL += getCondition1() + getCondition41() + getCondition5();
		return querySQL;
	}
	
	//交通及運輸設備上期結存(全部)價值
	protected String querySQL181(){
		String querySQL=" select " +
		//" sum(case d.differencekind when '110' then isnull(d.bookvalue,'0') else isnull(d.netvalue,'0') end) '18-1' " +
		" sum(case d.differencekind when '110' then isnull(" + this.getBVorNV("d.") + ",'0') else isnull(d.netvalue,'0') end) '18-1' " +
		" from " +
		" UNTMP_MOVABLE_" + getYYYMM() + " m " +
		" inner join UNTMP_MOVABLEDETAIL_" + getYYYMM() + " d on " +
		" m.enterorg=d.enterorg  " +
		" and m.ownership=d.ownership  " +
		" and m.lotno=d.lotno  " +
		" and m.propertyno=d.propertyno " +
		" and m.differencekind=d.differencekind " +
		" where " +
		" substring(d.propertyno,1,1) = '4'";
		querySQL += getCondition1() + getCondition41() + getCondition5();
		return querySQL;
	}
	
	//雜項設備上期結存(圖書)數量
	protected String querySQL191(){
		String querySQL=" select " +
		" sum(isnull(d.bookamount,'0')) '19-1' " +
		" from " +
		" UNTMP_MOVABLE_" + getYYYMM() + " m " +
		" inner join UNTMP_MOVABLEDETAIL_" + getYYYMM() + " d on " +
		" m.enterorg=d.enterorg  " +
		" and m.ownership=d.ownership  " +
		" and m.lotno=d.lotno  " +
		" and m.propertyno=d.propertyno " +
		" and m.differencekind=d.differencekind " +
		" where " +
		" substring(d.propertyno,1,3) = '503'";
		querySQL += getCondition1() + getCondition41() + getCondition5();
		return querySQL;
	}
	
	//雜項設備上期結存(博物)數量
	protected String querySQL201(){
		String querySQL=" select " +
		" sum(isnull(d.bookamount,'0')) '20-1' " +
		" from " +
		" UNTMP_MOVABLE_" + getYYYMM() + " m " +
		" inner join UNTMP_MOVABLEDETAIL_" + getYYYMM() + " d on " +
		" m.enterorg=d.enterorg  " +
		" and m.ownership=d.ownership  " +
		" and m.lotno=d.lotno  " +
		" and m.propertyno=d.propertyno " +
		" and m.differencekind=d.differencekind " +
		" where " +
		" substring(d.propertyno,1,3) = '504'";
		querySQL += getCondition1() + getCondition41() + getCondition5();
		return querySQL;
	}
	
	//雜項設備上期結存(其他)數量
	protected String querySQL211(){
		String querySQL=" select " +
		" sum(isnull(d.bookamount,'0')) '21-1' " +
		" from " +
		" UNTMP_MOVABLE_" + getYYYMM() + " m " +
		" inner join UNTMP_MOVABLEDETAIL_" + getYYYMM() + " d on " +
		" m.enterorg=d.enterorg  " +
		" and m.ownership=d.ownership  " +
		" and m.lotno=d.lotno  " +
		" and m.propertyno=d.propertyno " +
		" and m.differencekind=d.differencekind " +
		" where " +
		" substring(d.propertyno,1,1) = '5' " +
		" and substring(d.propertyno,1,3) <> '503'" ;
		querySQL += getCondition1() + getCondition41() + getCondition5();
		return querySQL;
	}
	
	//雜項設備上期結存(全部)價值
	protected String querySQL221(){
		String querySQL=" select " +
		//" sum(case d.differencekind when '110' then isnull(d.bookvalue,'0') else isnull(d.netvalue,'0') end) '22-1' " +
		" sum(case d.differencekind when '110' then isnull(" + this.getBVorNV("d.") + ",'0') else isnull(d.netvalue,'0') end) '22-1' " +
		" from " +
		" UNTMP_MOVABLE_" + getYYYMM() + " m " +
		" inner join UNTMP_MOVABLEDETAIL_" + getYYYMM() + " d on " +
		" m.enterorg=d.enterorg " +
		" and m.ownership=d.ownership " +
		" and m.lotno=d.lotno " +
		" and m.propertyno=d.propertyno " +
		" and m.differencekind=d.differencekind " +
		" where " +
		" substring(d.propertyno,1,1) = '5'";
		querySQL += getCondition1() + getCondition41() + getCondition5();
		return querySQL;
	}
	
	//有價證券上期結存
	protected String querySQL231(){
		String querySQL=" select " +
		" sum(isnull(d.bookamount,'0')) '23-1', " +
		" sum(isnull(d.bookvalue,'0')) '24-1' " +
		" from " +
		" UNTVP_ADDPROOF_" + getYYYMM() + " d " +
		" where " +
		" substring(d.propertyno,1,1) = '9'";
		querySQL += getCondition1() + getCondition3() + getCondition5();
		return querySQL;
	}
	
	//權利上期結存
	protected String querySQL251(){
		String querySQL=" select " +
		" COUNT(1) '25-1', " +
		//" sum(isnull(d.bookvalue,'0')) '26-1' " +
		" sum(case d.differencekind when '110' then isnull(" + this.getBVorNV("d.") + ",'0') else isnull(d.netvalue,'0') end) '26-1' " +
		" from " +
		" UNTRT_ADDPROOF_" + getYYYMM() + " d " +
		" where " +
		" substring(d.propertyno,1,1) = '8'";
		querySQL += getCondition1() + getCondition3() + getCondition5();
		return querySQL;
	}
	
	//土地本期增加
	protected String querySQL012(){
		String querySQL=" select " +
			" sum(aa.[1-2]) '1-2', " +
			" sum(aa.[2-2]) '2-2', " +
			" sum(aa.[3-2]) '3-2' " +
			" from ( " +
			" select " +
			" COUNT(1) '1-2', " +
			" SUM(isnull(d.originalholdarea,'0'))/10000 '2-2', " +
			" SUM(isnull(d.originalbv,'0')) '3-2' " +
			" from " +
			" UNTLA_LAND d " +
			" where " +
			" substring(d.propertyno,1,1) = '1' " +
			" and substring(d.propertyno,1,3) <> '111' ";
		querySQL += getCondition1() + getCondition2(1) + getCondition4() + getCondition5();
		querySQL += " union " +
			" select " +
			" 0 '1-2', " +
			" SUM(case when isnull(d.adjustholdarea,0) > 0 then isnull(d.adjustholdarea,0) else 0 end)/10000 '2-2', " +
			" SUM(isnull(d.addbookvalue,'0')) '3-2' " +
			" from " +
			" UNTLA_ADJUSTDETAIL d " +
			" where " +
			" substring(d.propertyno,1,1) = '1' " +
			" and substring(d.propertyno,1,3) <> '111' ";
		querySQL += getCondition1() + getCondition2(2) + getCondition4() + getCondition5();
		querySQL += " ) aa";
		
		return querySQL;
	}
	
	//土地改良物本期增加
	protected String querySQL042(){
		String querySQL=" select " +
			" sum(aa.[4-2]) '4-2', " +
			" sum(aa.[5-2]) '5-2' " +
			" from ( " +
			" select " +
			" COUNT(1) '4-2', " +
			" SUM(isnull(d.originalbv,'0') - (isnull(d.originalaccumdepr1,0) + isnull(d.originalaccumdepr2,0))) '5-2' " +
			" from " +
			" UNTRF_ATTACHMENT d " +
			" where " +
			" substring(d.propertyno,1,3) = '111' ";
			querySQL += getCondition1() + getCondition2(1) + getCondition4() + getCondition5();
			querySQL += " union " +
			" select " +
			" 0 '4-2', " +
			// " SUM(case when d.differencekind = '110' then isnull(d.addbookvalue,0) else isnull(d.addnetvalue,0) end) '5-2' " +
			" SUM(case when d.differencekind = '110' then isnull(" + this.getAddBVorNV("d.") + ",0) else isnull(d.addnetvalue,0) end) '5-2' " +
			" from " +
			" UNTRF_ADJUSTDETAIL d " +
			" where " +
			" substring(d.propertyno,1,3) = '111' ";
			querySQL += getCondition1() + getCondition2(2) + getCondition4() + getCondition5();
			querySQL += " ) aa";
		return querySQL;
	}
	
	//房屋建築及設備(辦公房屋)本期增加
	protected String querySQL062(){
		String querySQL=" select " +
			" sum(aa.[6-2]) '6-2', " +
			" sum(aa.[7-2]) '7-2' " +
			" from ( " +
			" select " +
			" COUNT(1) '6-2', " +
			" SUM(isnull(d.originalholdarea,'0')) '7-2' " +
			" from " +
			" UNTBU_BUILDING d " +
			" where " +
			" substring(d.propertyno,1,5) >= '20000' and substring(d.propertyno,1,5) <= '20105' ";
			querySQL += getCondition1() + getCondition2(1) + getCondition4() + getCondition5();
			querySQL += " union " +
			" select " +
			" 0 '6-2', " +
			" SUM(case when isnull(d.adjustholdarea,0) > 0 then isnull(d.adjustholdarea,0) else 0 end) '7-2' " +
			" from " +
			" UNTBU_ADJUSTDETAIL d " +
			" where " +
			" substring(d.propertyno,1,5) >= '20000' and substring(d.propertyno,1,5) <= '20105' ";
			querySQL += getCondition1() + getCondition2(2) + getCondition4() + getCondition5();
			querySQL += " ) aa";
		return querySQL;
	}
	
	//房屋建築及設備(宿舍)本期增加
	protected String querySQL082(){
		String querySQL=" select " +
			" sum(aa.[8-2]) '8-2', " +
			" sum(aa.[9-2]) '9-2' " +
			" from ( " +
			" select " +
			" COUNT(1) '8-2', " +
			" SUM(isnull(d.originalholdarea,'0')) '9-2' " +
			" from " +
			" UNTBU_BUILDING d " +
			" where " +
			" substring(d.propertyno,1,5) = '20106' ";
			querySQL += getCondition1() + getCondition2(1) + getCondition4() + getCondition5();
			querySQL += " union " +
			" select " +
			" 0 '8-2', " +
			" SUM(case when isnull(d.adjustholdarea,0) > 0 then isnull(d.adjustholdarea,0) else 0 end) '9-2' " +
			" from " +
			" UNTBU_ADJUSTDETAIL d " +
			" where " +
			" substring(d.propertyno,1,5) = '20106' ";
			querySQL += getCondition1() + getCondition2(2) + getCondition4() + getCondition5();
			querySQL += " ) aa";
		return querySQL;
	}
	
	//房屋建築及設備(其他)本期增加
	protected String querySQL102(){
		String querySQL=" select " +
			" sum(aa.[10-2]) '10-2' " +
			" from ( " +
			" select " +
			" COUNT(1) '10-2' " +
			" from " +
			" UNTBU_BUILDING d " +
			" where " +
			" substring(d.propertyno,1,1) = '2' " +			
			" and substring(d.propertyno,1,5) > '20106' ";
			querySQL += getCondition1() + getCondition2(1) + getCondition4() + getCondition5();
			querySQL += " union " +
			" select " +
			" 0 '10-2' " +
			" from " +
			" UNTBU_ADJUSTDETAIL d " +
			" where " +
			" substring(d.propertyno,1,1) = '2' " +			
			" and substring(d.propertyno,1,5) > '20106' ";
			querySQL += getCondition1() + getCondition2(2) + getCondition4() + getCondition5();
			querySQL += " ) aa";
		return querySQL;
	}
	
	//房屋建築及設備本期增加(價值)
	protected String querySQL112(){
		String querySQL=" select " +
		" sum(aa.[11-2]) '11-2' " +
		" from ( " +
		" select " +
		" SUM(isnull(d.originalbv,0) - (isnull(d.originalaccumdepr1,0) + isnull(d.originalaccumdepr2,0))) '11-2' " +
		" from " +
		" UNTBU_BUILDING d " +
		" where " +
		" substring(d.propertyno,1,1) = '2' ";
		querySQL += getCondition1() + getCondition2(1) + getCondition4() + getCondition5();
		querySQL += " union " +
		" select " +
		// " SUM(case when d.differencekind = '110' then isnull(d.addbookvalue,0) else isnull(d.addnetvalue,0) end) '11-2' " +
		" SUM(case when d.differencekind = '110' then isnull(" + this.getAddBVorNV("d.") + ",0) else isnull(d.addnetvalue,0) end) '11-2' " +
		" from " +
		" UNTBU_ADJUSTDETAIL d " +
		" where " +
		" substring(d.propertyno,1,1) = '2' ";
		querySQL += getCondition1() + getCondition2(2) + getCondition4() + getCondition5();
		querySQL += " ) aa";
		return querySQL;
	}
	
	//機械及設備本期增加
	protected String querySQL122(){
		String querySQL=" select " +
		" sum(aa.[12-2]) '12-2', " +
		" sum(aa.[13-2]) '13-2' " +
		" from ( " +
		" select " +
		" SUM(isnull(d.originalamount,0)) '12-2', " +
		" SUM(isnull(d.originalbv,0) - (isnull(d.originalaccumdepr1,0) + isnull(d.originalaccumdepr2,0))) '13-2' " +
		" from " +
		" UNTMP_MOVABLE  m " +
		" inner join UNTMP_MOVABLEDETAIL  d on " +
		" m.enterorg=d.enterorg  " +
		" and m.ownership=d.ownership  " +
		" and m.lotno=d.lotno  " +
		" and m.propertyno=d.propertyno " +
		" and m.differencekind=d.differencekind " +
		" where " +
		" substring(d.propertyno,1,1) = '3' ";
		querySQL += getCondition1() + getCondition2(1) + getCondition41() + getCondition5();
		querySQL += " union " +
		" select " +
		" 0 '12-2', " +
		// " SUM(case when d.differencekind = '110' then isnull(d.addbookvalue,0) else isnull(d.addnetvalue,0) end) '13-2' " +
		" SUM(case when d.differencekind = '110' then isnull(" + this.getAddBVorNV("d.") + ",0) else isnull(d.addnetvalue,0) end) '13-2' " +
		" from " +
		" UNTMP_ADJUSTDETAIL d " +
		" where " +
		" substring(d.propertyno,1,1) = '3' ";
		querySQL += getCondition1() + getCondition2(2) + getCondition4() + getCondition5();
		querySQL += " ) aa";
		return querySQL;
	}
	
	//交通及運輸設備本期增加(船)數量
	protected String querySQL142(){
		String querySQL=" select " +
		" sum(aa.[14-2]) '14-2' " +
		" from ( " +
		" select " +
		" SUM(isnull(d.originalamount,0)) '14-2' " +
		" from " +
		" UNTMP_MOVABLE  m " +
		" inner join UNTMP_MOVABLEDETAIL  d on " +
		" m.enterorg=d.enterorg  " +
		" and m.ownership=d.ownership  " +
		" and m.lotno=d.lotno  " +
		" and m.propertyno=d.propertyno " +
		" and m.differencekind=d.differencekind " +
		" where " +
		" substring(d.propertyno,1,5) >= '40200' and substring(d.propertyno,1,5) <= '40202' ";
		querySQL += getCondition1() + getCondition2(1) + getCondition41() + getCondition5();
		querySQL += " union " +
		" select " +
		" 0 '14-2' " +
		" from " +
		" UNTMP_ADJUSTDETAIL d " +
		" where " +
		" substring(d.propertyno,1,5) >= '40200' and substring(d.propertyno,1,5) <= '40202'  ";
		querySQL += getCondition1() + getCondition2(2) + getCondition4() + getCondition5();
		querySQL += " ) aa";
		return querySQL;
	}
	
	//交通及運輸設備本期增加(飛機)數量
	protected String querySQL152(){
		String querySQL=" select " +
		" sum(aa.[15-2]) '15-2' " +
		" from ( " +
		" select " +
		" SUM(isnull(d.originalamount,0)) '15-2' " +
		" from " +
		" UNTMP_MOVABLE  m " +
		" inner join UNTMP_MOVABLEDETAIL  d on " +
		" m.enterorg=d.enterorg  " +
		" and m.ownership=d.ownership  " +
		" and m.lotno=d.lotno  " +
		" and m.propertyno=d.propertyno " +
		" and m.differencekind=d.differencekind " +
		" where " +
		" substring(d.propertyno,1,5) >= '40302' and substring(d.propertyno,1,5) <= '40303' ";
		querySQL += getCondition1() + getCondition2(1) + getCondition41() + getCondition5();
		querySQL += " union " +
		" select " +
		" 0 '15-2' " +
		" from " +
		" UNTMP_ADJUSTDETAIL d " +
		" where " +
		" substring(d.propertyno,1,5) >= '40302' and substring(d.propertyno,1,5) <= '40303' ";
		querySQL += getCondition1() + getCondition2(2) + getCondition4() + getCondition5();
		querySQL += " ) aa";
		return querySQL;
	}
	
	//交通及運輸設備本期增加(汽機車)數量
	protected String querySQL162(){
		String querySQL=" select " +
		" sum(aa.[16-2]) '16-2' " +
		" from ( " +
		" select " +
		" SUM(isnull(d.originalamount,0)) '16-2' " +
		" from " +
		" UNTMP_MOVABLE  m " +
		" inner join UNTMP_MOVABLEDETAIL  d on " +
		" m.enterorg=d.enterorg  " +
		" and m.ownership=d.ownership  " +
		" and m.lotno=d.lotno  " +
		" and m.propertyno=d.propertyno " +
		" and m.differencekind=d.differencekind " +
		" where " +
		" substring(d.propertyno,1,5) = '40107'  " ;
		querySQL += getCondition1() + getCondition2(1) + getCondition41() + getCondition5();
		querySQL += " union " +
		" select " +
		" 0 '16-2' " +
		" from " +
		" UNTMP_ADJUSTDETAIL d " +
		" where " +
		" substring(d.propertyno,1,5) = '40107' ";
		querySQL += getCondition1() + getCondition2(2) + getCondition4() + getCondition5();
		querySQL += " ) aa";
		return querySQL;
	}
	
	//交通及運輸設備本期增加(其他)數量
	protected String querySQL172(){
		String querySQL=" select " +
		" sum(aa.[17-2]) '17-2' " +
		" from ( " +
		" select " +
		" SUM(isnull(d.originalamount,0)) '17-2' " +
		" from " +
		" UNTMP_MOVABLE  m " +
		" inner join UNTMP_MOVABLEDETAIL  d on " +
		" m.enterorg=d.enterorg  " +
		" and m.ownership=d.ownership  " +
		" and m.lotno=d.lotno  " +
		" and m.propertyno=d.propertyno " +
		" and m.differencekind=d.differencekind " +
		" where " +
		" substring(d.propertyno,1,1) = '4' " +
		" and substring(d.propertyno,1,5) <> '40200' " +
		" and substring(d.propertyno,1,5) <> '40201' " +
		" and substring(d.propertyno,1,5) <> '40202' " +
		" and substring(d.propertyno,1,5) <> '40302' " +
		" and substring(d.propertyno,1,5) <> '40107' " ;
		querySQL += getCondition1() + getCondition2(1) + getCondition41() + getCondition5();
		querySQL += " union " +
		" select " +
		" 0 '17-2' " +
		" from " +
		" UNTMP_ADJUSTDETAIL d " +
		" where " +
		" substring(d.propertyno,1,1) = '4' " +
		" and substring(d.propertyno,1,5) <> '40200' " +
		" and substring(d.propertyno,1,5) <> '40201' " +
		" and substring(d.propertyno,1,5) <> '40202' " +
		" and substring(d.propertyno,1,5) <> '40302' " +
		" and substring(d.propertyno,1,5) <> '40107' " ;
		querySQL += getCondition1() + getCondition2(2) + getCondition4() + getCondition5();
		querySQL += " ) aa";
		return querySQL;
	}
	
	//--交通及運輸設備本期增加(全部)價值
	protected String querySQL182(){
		String querySQL=" select " +
		" sum(aa.[18-2]) '18-2' " +
		" from ( " +
		" select " +
		" SUM(isnull(d.originalbv,0) - (isnull(d.originalaccumdepr1,0) + isnull(d.originalaccumdepr2,0))) '18-2' " +
		" from " +
		" UNTMP_MOVABLE  m " +
		" inner join UNTMP_MOVABLEDETAIL  d on " +
		" m.enterorg=d.enterorg  " +
		" and m.ownership=d.ownership  " +
		" and m.lotno=d.lotno  " +
		" and m.propertyno=d.propertyno " +
		" and m.differencekind=d.differencekind " +
		" where " +
		" substring(d.propertyno,1,1) = '4' ";
		querySQL += getCondition1() + getCondition2(1) + getCondition41() + getCondition5();
		querySQL += " union " +
		" select " +
		// " SUM(case when d.differencekind = '110' then isnull(d.addbookvalue,0) else isnull(d.addnetvalue,0) end) '18-2' " +
		" SUM(case when d.differencekind = '110' then isnull(" + this.getAddBVorNV("d.") + ",0) else isnull(d.addnetvalue,0) end) '18-2' " +
		" from " +
		" UNTMP_ADJUSTDETAIL d " +
		" where " +
		" substring(d.propertyno,1,1) = '4' ";
		querySQL += getCondition1() + getCondition2(2) + getCondition4() + getCondition5();
		querySQL += " ) aa";
		return querySQL;
	}
	
	//雜項設備本期增加(圖書)數量
	protected String querySQL192(){
		String querySQL=" select " +
		" sum(aa.[19-2]) '19-2' " +
		" from ( " +
		" select " +
		" SUM(isnull(d.originalamount,0)) '19-2' " +
		" from " +
		" UNTMP_MOVABLE  m " +
		" inner join UNTMP_MOVABLEDETAIL  d on " +
		" m.enterorg=d.enterorg  " +
		" and m.ownership=d.ownership  " +
		" and m.lotno=d.lotno  " +
		" and m.propertyno=d.propertyno " +
		" and m.differencekind=d.differencekind " +
		" where " +
		" substring(d.propertyno,1,3) = '503' ";
		querySQL += getCondition1() + getCondition2(1) + getCondition41() + getCondition5();
		querySQL += " union " +
		" select " +
		" 0 '19-2' " +
		" from " +
		" UNTMP_ADJUSTDETAIL d " +
		" where " +
		" substring(d.propertyno,1,3) = '503' ";
		querySQL += getCondition1() + getCondition2(2) + getCondition4() + getCondition5();
		querySQL += " ) aa";
		return querySQL;
	}
	
	
	//雜項設備本期增加(博物)數量
	protected String querySQL202(){
		String querySQL=" select " +
		" sum(aa.[20-2]) '20-2' " +
		" from ( " +
		" select " +
		" SUM(isnull(d.originalamount,0)) '20-2' " +
		" from " +
		" UNTMP_MOVABLE  m " +
		" inner join UNTMP_MOVABLEDETAIL  d on " +
		" m.enterorg=d.enterorg  " +
		" and m.ownership=d.ownership  " +
		" and m.lotno=d.lotno  " +
		" and m.propertyno=d.propertyno " +
		" and m.differencekind=d.differencekind " +
		" where " +
		" substring(d.propertyno,1,3) = '504' ";
		querySQL += getCondition1() + getCondition2(1) + getCondition41() + getCondition5();
		querySQL += " union " +
		" select " +
		" 0 '20-2' " +
		" from " +
		" UNTMP_ADJUSTDETAIL d " +
		" where " +
		" substring(d.propertyno,1,3) = '504' ";
		querySQL += getCondition1() + getCondition2(2) + getCondition4() + getCondition5();
		querySQL += " ) aa";
		return querySQL;
	}
	
	//雜項設備本期增加(其他)數量
	protected String querySQL212(){
		String querySQL=" select " +
		" sum(aa.[21-2]) '21-2' " +
		" from ( " +
		" select " +
		" SUM(isnull(d.originalamount,0)) '21-2' " +
		" from " +
		" UNTMP_MOVABLE  m " +
		" inner join UNTMP_MOVABLEDETAIL  d on " +
		" m.enterorg=d.enterorg  " +
		" and m.ownership=d.ownership  " +
		" and m.lotno=d.lotno  " +
		" and m.propertyno=d.propertyno " +
		" and m.differencekind=d.differencekind " +
		" where " +
		" substring(d.propertyno,1,1) = '5' " +
		" and substring(d.propertyno,1,3) <> '503' " ;
		querySQL += getCondition1() + getCondition2(1) + getCondition41() + getCondition5();
		querySQL += " union " +
		" select " +
		" 0 '21-2' " +
		" from " +
		" UNTMP_ADJUSTDETAIL d " +
		" where " +
		" substring(d.propertyno,1,1) = '5' " +
		" and substring(d.propertyno,1,3) <> '503' " ;
		querySQL += getCondition1() + getCondition2(2) + getCondition4() + getCondition5();
		querySQL += " ) aa";
		return querySQL;
	}
	
	//雜項設備本期增加(全部)價值
	protected String querySQL222(){
		String querySQL=" select " +
		" sum(aa.[22-2]) '22-2' " +
		" from ( " +
		" select " +
		" SUM(isnull(d.originalbv,0) - (isnull(d.originalaccumdepr1,0) + isnull(d.originalaccumdepr2,0))) '22-2' " +
		" from " +
		" UNTMP_MOVABLE  m " +
		" inner join UNTMP_MOVABLEDETAIL  d on " +
		" m.enterorg=d.enterorg  " +
		" and m.ownership=d.ownership  " +
		" and m.lotno=d.lotno  " +
		" and m.propertyno=d.propertyno " +
		" and m.differencekind=d.differencekind " +
		" where " +
		" substring(d.propertyno,1,1) = '5' ";
		querySQL += getCondition1() + getCondition2(1) + getCondition41() + getCondition5();
		querySQL += " union " +
		" select " +
		// " SUM(case when d.differencekind = '110' then isnull(d.addbookvalue,0) else isnull(d.addnetvalue,0) end) '22-2' " +
		" SUM(case when d.differencekind = '110' then isnull(" + this.getAddBVorNV("d.") + ",0) else isnull(d.addnetvalue,0) end) '22-2' " +
		" from " +
		" UNTMP_ADJUSTDETAIL d " +
		" where " +
		" substring(d.propertyno,1,1) = '5' ";
		querySQL += getCondition1() + getCondition2(2) + getCondition4() + getCondition5();
		querySQL += " ) aa";
		return querySQL;
	}

	//有價證券本期增加
	protected String querySQL232(){
		String querySQL=" select " +
		" sum(aa.[23-2]) '23-2', " +
		" sum(aa.[24-2]) '24-2' " +
		" from ( " +
		" select " +
		" sum(isnull(d.originalamount,'0')) '23-2', " +
		" sum(isnull(d.originalbv,'0')) '24-2' " +
		" from " +
		" UNTVP_ADDPROOF d " +
		" where " +
		" substring(d.propertyno,1,1) = '9' ";
		querySQL += getCondition1() + getCondition2(1) + getCondition5();
		querySQL += " union " +
		" select " +
		" 0 '23-2', " +
		" sum(isnull(d.addbookvalue,'0')) '24-2' " +
		" from " +
		" UNTVP_ADJUSTPROOF d " +
		" where " +
		" substring(d.propertyno,1,1) = '9' ";
		querySQL += getCondition1() + getCondition2(2) + getCondition5();
		querySQL += " ) aa";
		return querySQL;
	}
	
	//權利本期增加
	protected String querySQL252(){
		String querySQL=" select " +
		" sum(aa.[25-2]) '25-2', " +
		" sum(aa.[26-2]) '26-2' " +
		" from ( " +
		" select " +
		" COUNT(1) '25-2', " +
		" sum(isnull(d.originalbv,'0') - (isnull(d.originalaccumdepr1,0) + isnull(d.originalaccumdepr2,0))) '26-2' " +
		" from " +
		" UNTRT_ADDPROOF d " +
		" where " +
		" substring(d.propertyno,1,1) = '8' ";
		querySQL += getCondition1() + getCondition2(1) + getCondition5();
		querySQL += " union " +
		" select " +
		" 0 '25-2', " +
		// " sum(isnull(d.addbookvalue,'0')) '26-2' " +
		" SUM(case when d.differencekind = '110' then isnull(" + this.getAddBVorNV("d.") + ",0) else isnull(d.addnetvalue,0) end) '26-2' " +
		" from " +
		" UNTRT_ADJUSTPROOF d " +
		" where " +
		" substring(d.propertyno,1,1) = '8' ";
		querySQL += getCondition1() + getCondition2(2) + getCondition5();
		querySQL += " ) aa";
		return querySQL;
	}
	
	//土地本期減少
	protected String querySQL013() {
		String querySQL=" select " +
			" sum(aa.[1-3]) '1-3', " +
			" sum(aa.[2-3]) '2-3', " +
			" sum(aa.[3-3]) '3-3' " +
			" from ( " +
			" select " +
			" 0 '1-3', " +
			" SUM(case when isnull(d.adjustholdarea,0) < 0 then isnull(-1 * d.adjustholdarea,0) else 0 end)/10000 '2-3', " +
			" SUM(isnull(d.reducebookvalue,'0')) '3-3' " +
			" from " +
			" UNTLA_ADJUSTDETAIL d " +
			" where " +
			" substring(d.propertyno,1,1) = '1' " +
			" and substring(d.propertyno,1,3) <> '111' ";
			querySQL += getCondition1() + getCondition2(2) + getCondition4() + getCondition5();
			querySQL += " union " +
			" select " +
			" COUNT(1) '1-3', " +
			" SUM(isnull(d.holdarea,'0'))/10000 '2-3', " +
			// " SUM(isnull(d.bookvalue,'0')) '3-3' " +
			" SUM(case when d.differencekind = '110' then isnull(" + this.getBVorNV("d.") + ",0) else isnull(d.netvalue,0) end) '3-3' " +
			" from " +
			" UNTLA_REDUCEDETAIL d " +
			" where " +
			" substring(d.propertyno,1,1) = '1' " +
			" and substring(d.propertyno,1,3) <> '111' ";
			querySQL += getCondition1() + getCondition2(3) + getCondition4() + getCondition5();
			querySQL += " ) aa";
		return querySQL;
	}
	
	//土地改良物本期減少
	protected String querySQL043() {
		String querySQL=" select " +
			" sum(aa.[4-3]) '4-3', " +
			" sum(aa.[5-3]) '5-3' " +
			" from ( " +
			" select " +
			" 0 '4-3', " +
			// " SUM(case when d.differencekind = '110' then isnull(d.reducebookvalue,0) else isnull(d.reducenetvalue,0) end) '5-3' " +
			" SUM(case when d.differencekind = '110' then isnull(" + this.getReduceBVorNV("d.") + ",0) else isnull(d.reducenetvalue,0) end) '5-3' " +
			" from " +
			" UNTRF_ADJUSTDETAIL d " +
			" where " +
			" substring(d.propertyno,1,3) = '111' ";
			querySQL += getCondition1() + getCondition2(2) + getCondition4() + getCondition5();
			querySQL += " union " +
			" select " +
			" COUNT(1) '4-3', " +
			" SUM(case when d.differencekind = '110' then ISNULL(d.bookvalue,0) else ISNULL(d.netvalue,0) end) '5-3' " +
			" from " +
			" UNTRF_REDUCEDETAIL d " +
			" where " +
			" substring(d.propertyno,1,3) = '111' ";
			querySQL += getCondition1() + getCondition2(3) + getCondition4() + getCondition5();
			querySQL += " ) aa";
		return querySQL;
	}
	
	//房屋建築及設備(辦公房屋)本期減少
	protected String querySQL063() {
		String querySQL=" select " +
		" sum(aa.[6-3]) '6-3', " +
		" sum(aa.[7-3]) '7-3' " +
		" from ( " +
		" select " +
		" 0 '6-3', " +
		" SUM(case when isnull(d.adjustholdarea,0) < 0 then isnull(-1 * d.adjustholdarea,0) else 0 end) '7-3' " +
		" from " +
		" UNTBU_ADJUSTDETAIL d " +
		" where " +
		" substring(d.propertyno,1,5) >= '20000' and substring(d.propertyno,1,5) <= '20105'  ";
		querySQL += getCondition1() + getCondition2(2) + getCondition4() + getCondition5();
		querySQL += " union " +
		" select " +
		" COUNT(1) '6-3', " +
		" SUM(isnull(d.holdarea,'0')) '7-3' " +
		" from " +
		" UNTBU_REDUCEDETAIL d " +
		" where " +
		" substring(d.propertyno,1,5) >= '20000' and substring(d.propertyno,1,5) <= '20105'  ";
		querySQL += getCondition1() + getCondition2(3) + getCondition4() + getCondition5();
		querySQL += " ) aa";
		return querySQL;
	}
	
	//房屋建築及設備(宿舍)本期減少
	protected String querySQL083() {
		String querySQL=" select " +
		" sum(aa.[8-3]) '8-3', " +
		" sum(aa.[9-3]) '9-3' " +
		" from ( " +
		" select " +
		" 0 '8-3', " +
		" SUM(case when isnull(d.adjustholdarea,0) < 0 then isnull(-1 * d.adjustholdarea,0) else 0 end) '9-3' " +
		" from " +
		" UNTBU_ADJUSTDETAIL d " +
		" where " +
		" substring(d.propertyno,1,5) = '20106' ";
		querySQL += getCondition1() + getCondition2(2) + getCondition4() + getCondition5();
		querySQL += " union " +
		" select " +
		" COUNT(1) '8-3', " +
		" SUM(isnull(d.holdarea,'0')) '9-3' " +
		" from " +
		" UNTBU_REDUCEDETAIL d " +
		" where " +
		" substring(d.propertyno,1,5) = '20106' ";
		querySQL += getCondition1() + getCondition2(3) + getCondition4() + getCondition5();
		querySQL += " ) aa";
		return querySQL;
	}
	
	//房屋建築及設備(其他)本期減少
	protected String querySQL103() {
		String querySQL=" select " +
		" sum(aa.[10-3]) '10-3' " +
		" from ( " +
		" select " +
		" 0 '10-3' " +
		" from " +
		" UNTBU_ADJUSTDETAIL d " +
		" where " +
		" substring(d.propertyno,1,1) = '2' " +
		" and substring(d.propertyno,1,5) > '20106' ";
		querySQL += getCondition1() + getCondition2(2) + getCondition4() + getCondition5();
		querySQL += " union " +
		" select " +
		" COUNT(1) '10-3' " +
		" from " +
		" UNTBU_REDUCEDETAIL d " +
		" where " +
		" substring(d.propertyno,1,1) = '2' " +
		" and substring(d.propertyno,1,5) > '20106'";
		querySQL += getCondition1() + getCondition2(3) + getCondition4() + getCondition5();
		querySQL += " ) aa";
		return querySQL;
	}
	
	//房屋建築及設備本期減少(價值)
	protected String querySQL113() {
		String querySQL=" select " +
		" sum(aa.[11-3]) '11-3' " +
		" from ( " +
		" select " +
		// " SUM(case when d.differencekind = '110' then isnull(d.reducebookvalue,0) else d.reducenetvalue end) '11-3' " +
		" SUM(case when d.differencekind = '110' then isnull(" + this.getReduceBVorNV("d.") + ",0) else d.reducenetvalue end) '11-3' " +
		" from " +
		" UNTBU_ADJUSTDETAIL d " +
		" where " +
		" substring(d.propertyno,1,1) = '2' ";
		querySQL += getCondition1() + getCondition2(2) + getCondition4() + getCondition5();
		querySQL += " union " +
		" select " +
		//" SUM(case when d.differencekind = '110' then isnull(d.bookvalue,0) else d.netvalue end) '11-3' " +
		" SUM(case when d.differencekind = '110' then isnull(" + this.getBVorNV("d.") + ",0) else d.netvalue end) '11-3' " +
		" from " +
		" UNTBU_REDUCEDETAIL d " +
		" where " +
		" substring(d.propertyno,1,1) = '2' ";
		querySQL += getCondition1() + getCondition2(3) + getCondition4() + getCondition5();
		querySQL += " ) aa";
		return querySQL;
	}
	
	//機械及設備本期減少
	protected String querySQL123() {
		String querySQL=" select " +
		" sum(aa.[12-3]) '12-3', " +
		" sum(aa.[13-3]) '13-3' " +
		" from ( " +
		" select " +
		" 0 '12-3', " +
		// " SUM(case when d.differencekind = '110' then isnull(d.reducebookvalue,0) else isnull(d.reducenetvalue,0) end) '13-3' " +
		" SUM(case when d.differencekind = '110' then isnull(" + this.getReduceBVorNV("d.") + ",0) else isnull(d.reducenetvalue,0) end) '13-3' " +
		" from " +
		" UNTMP_ADJUSTDETAIL d " +
		" where " +
		" substring(d.propertyno,1,1) = '3' ";
		querySQL += getCondition1() + getCondition2(2) + getCondition4() + getCondition5();
		querySQL += " union " +
		" select " +
		" SUM(isnull(d.adjustbookamount,0)) '12-3', " +
		// " SUM(case when d.differencekind = '110' then isnull(d.adjustbookvalue,0) else isnull(d.adjustnetvalue,0) end) '13-3' " +
		" SUM(case when d.differencekind = '110' then isnull(" + this.getAdjustBVorNV("d.") + ",0) else isnull(d.adjustnetvalue,0) end) '13-3' " +
		" from " +
		" UNTMP_REDUCEDETAIL d " +
		" where " +
		" substring(d.propertyno,1,1) = '3' ";
		querySQL += getCondition1() + getCondition2(3) + getCondition4() + getCondition5();
		querySQL += " ) aa";
		return querySQL;
	}
	
	//交通及運輸設備本期減少(船)數量
	protected String querySQL143() {
		String querySQL=" select " +
		" sum(aa.[14-3]) '14-3' " +
		" from ( " +
		" select " +
		" 0 '14-3' " +
		" from " +
		" UNTMP_ADJUSTDETAIL d " +
		" where " +
		" substring(d.propertyno,1,5) >= '40200' and substring(d.propertyno,1,5) <= '40202' ";
		querySQL += getCondition1() + getCondition2(2) + getCondition4() + getCondition5();
		querySQL += " union " +
		" select " +
		" SUM(isnull(d.adjustbookamount,0)) '14-3' " +
		" from " +
		" UNTMP_REDUCEDETAIL d " +
		" where " +
		" substring(d.propertyno,1,5) >= '40200' and substring(d.propertyno,1,5) <= '40202' ";
		querySQL += getCondition1() + getCondition2(3) + getCondition4() + getCondition5();
		querySQL += " ) aa";
		return querySQL;
	}
	
	//交通及運輸設備本期減少(飛機)數量
	protected String querySQL153() {
		String querySQL=" select " +
		" sum(aa.[15-3]) '15-3' " +
		" from ( " +
		" select " +
		" 0 '15-3' " +
		" from " +
		" UNTMP_ADJUSTDETAIL d " +
		" where " +
		" substring(d.propertyno,1,5) >= '40302' and substring(d.propertyno,1,5) <= '40303' ";
		querySQL += getCondition1() + getCondition2(2) + getCondition4() + getCondition5();
		querySQL += " union " +
		" select " +
		" SUM(isnull(d.adjustbookamount,0)) '15-3' " +
		" from " +
		" UNTMP_REDUCEDETAIL d " +
		" where " +
		" substring(d.propertyno,1,5) >= '40302' and substring(d.propertyno,1,5) <= '40303'  ";
		querySQL += getCondition1() + getCondition2(3) + getCondition4() + getCondition5();
		querySQL += " ) aa";
		return querySQL;
	}
	
	//交通及運輸設備本期減少(汽機車)數量
	protected String querySQL163() {
		String querySQL=" select " +
		" sum(aa.[16-3]) '16-3' " +
		" from ( " +
		" select " +
		" 0 '16-3' " +
		" from " +
		" UNTMP_ADJUSTDETAIL d " +
		" where " +
		" substring(d.propertyno,1,5) = '40107' " ;
		querySQL += getCondition1() + getCondition2(2) + getCondition4() + getCondition5();
		querySQL += " union " +
		" select " +
		" SUM(isnull(d.adjustbookamount,0)) '16-3' " +
		" from " +
		" UNTMP_REDUCEDETAIL d " +
		" where " +
		" substring(d.propertyno,1,5) = '40107' ";
		querySQL += getCondition1() + getCondition2(3) + getCondition4() + getCondition5();
		querySQL += " ) aa";
		return querySQL;
	}
	
	//交通及運輸設備本期減少(其他)數量
	protected String querySQL173() {
		String querySQL=" select " +
		" sum(aa.[17-3]) '17-3' " +
		" from ( " +
		" select " +
		" 0 '17-3' " +
		" from " +
		" UNTMP_ADJUSTDETAIL d " +
		" where " +
		" substring(d.propertyno,1,1) = '4' " +
		" and substring(d.propertyno,1,5) <> '40200' " +
		" and substring(d.propertyno,1,5) <> '40201' " +
		" and substring(d.propertyno,1,5) <> '40202' " +
		" and substring(d.propertyno,1,5) <> '40302' " +
		" and substring(d.propertyno,1,5) <> '40303' " +
		" and substring(d.propertyno,1,5) <> '40107' " ;
		querySQL += getCondition1() + getCondition2(2) + getCondition4() + getCondition5();
		querySQL += " union " +
		" select " +
		" SUM(isnull(d.adjustbookamount,0)) '17-3' " +
		" from " +
		" UNTMP_REDUCEDETAIL d " +
		" where " +
		" substring(d.propertyno,1,1) = '4' " +
		" and substring(d.propertyno,1,5) <> '40200' " +
		" and substring(d.propertyno,1,5) <> '40201' " +
		" and substring(d.propertyno,1,5) <> '40202' " +
		" and substring(d.propertyno,1,5) <> '40302' " +
		" and substring(d.propertyno,1,5) <> '40303' " +
		" and substring(d.propertyno,1,5) <> '40107' " ;
		querySQL += getCondition1() + getCondition2(3) + getCondition4() + getCondition5();
		querySQL += " ) aa";
		return querySQL;
	}
	
	//交通及運輸設備本期減少(其他)價值
	protected String querySQL183() {
		String querySQL=" select " +
		" sum(aa.[18-3]) '18-3' " +
		" from ( " +
		" select " +
		// " SUM(case when d.differencekind = '110' then isnull(d.reducebookvalue,0) else isnull(d.reducenetvalue,0) end) '18-3' " +
		" SUM(case when d.differencekind = '110' then isnull(" + this.getReduceBVorNV("d.") + ",0) else isnull(d.reducenetvalue,0) end) '18-3' " +
		" from " +
		" UNTMP_ADJUSTDETAIL d " +
		" where " +
		" substring(d.propertyno,1,1) = '4' ";
		querySQL += getCondition1() + getCondition2(2) + getCondition4() + getCondition5();
		querySQL += " union " +
		" select " +
		//" SUM(case when d.differencekind = '110' then isnull(d.adjustbookvalue,0) else isnull(d.adjustnetvalue,0) end) '18-3' " +
		" SUM(case when d.differencekind = '110' then isnull(" + this.getAdjustBVorNV("d.") + ",0) else isnull(d.adjustnetvalue,0) end) '18-3' " +
		" from " +
		" UNTMP_REDUCEDETAIL d " +
		" where " +
		" substring(d.propertyno,1,1) = '4' ";
		querySQL += getCondition1() + getCondition2(3) + getCondition4() + getCondition5();
		querySQL += " ) aa";
		return querySQL;
	}
	
	//雜項設備本期減少(圖書)數量
	protected String querySQL193() {
		String querySQL=" select "+
		" sum(aa.[19-3]) '19-3' " +
		" from ( " +
		" select " +
		" 0 '19-3' " +
		" from " +
		" UNTMP_ADJUSTDETAIL d " +
		" where " +
		" substring(d.propertyno,1,3) = '503' ";
		querySQL += getCondition1() + getCondition2(2) + getCondition4() + getCondition5();
		querySQL += " union " +
		" select " +
		" SUM(isnull(d.adjustbookamount,0)) '19-3' " +
		" from " +
		" UNTMP_REDUCEDETAIL d " +
		" where " +
		" substring(d.propertyno,1,3) = '503' ";
		querySQL += getCondition1() + getCondition2(3) + getCondition4() + getCondition5();
		querySQL += " ) aa";
		return querySQL;
	}
	
	//雜項設備本期減少(博物)數量
	protected String querySQL203() {
		String querySQL=" select " +
		" sum(aa.[20-3]) '20-3' " +
		" from ( " +
		" select " +
		" 0 '20-3' " +
		" from " +
		" UNTMP_ADJUSTDETAIL d " +
		" where " +
		" substring(d.propertyno,1,3) = '504' ";
		querySQL += getCondition1() + getCondition2(2) + getCondition4() + getCondition5();
		querySQL += " union " +
		" select " +
		" SUM(isnull(d.adjustbookamount,0)) '20-3' " +
		" from " +
		" UNTMP_REDUCEDETAIL d " +
		" where " +
		" substring(d.propertyno,1,3) = '504' ";
		querySQL += getCondition1() + getCondition2(3) + getCondition4() + getCondition5();
		querySQL += " ) aa";
		return querySQL;
	}
	
	//雜項設備本期減少(其他)數量
	protected String querySQL213() {
		String querySQL=" select " +
		" sum(aa.[21-3]) '21-3' " +
		" from ( " +
		" select " +
		" 0 '21-3' " +
		" from " +
		" UNTMP_ADJUSTDETAIL d " +
		" where " +
		" substring(d.propertyno,1,1) = '5' " +
		" and substring(d.propertyno,1,3) <> '503' " ;
		querySQL += getCondition1() + getCondition2(2) + getCondition4() + getCondition5();
		querySQL += " union " +
		" select " +
		" SUM(isnull(d.adjustbookamount,0)) '21-3' " +
		" from " +
		" UNTMP_REDUCEDETAIL d " +
		" where " +
		" substring(d.propertyno,1,1) = '5' " +
		" and substring(d.propertyno,1,3) <> '503' " ;
		querySQL += getCondition1() + getCondition2(3) + getCondition4() + getCondition5();
		querySQL += " ) aa";
		return querySQL;
	}
	
	//雜項設備本期減少(全部)價值
	protected String querySQL223() {
		String querySQL=" select " +
		" sum(aa.[22-3]) '22-3' " +
		" from ( " +
		" select " +
		// " SUM(case when d.differencekind = '110' then isnull(d.reducebookvalue,0) else isnull(d.reducenetvalue,0) end) '22-3' " +
		" SUM(case when d.differencekind = '110' then isnull(" + this.getReduceBVorNV("d.") + ",0) else isnull(d.reducenetvalue,0) end) '22-3' " +
		" from " +
		" UNTMP_ADJUSTDETAIL d " +
		" where " +
		" substring(d.propertyno,1,1) = '5' ";
		querySQL += getCondition1() + getCondition2(2) + getCondition4() + getCondition5();
		querySQL += " union " +
		" select " +
		// " SUM(case when d.differencekind = '110' then isnull(d.adjustbookvalue,0) else isnull(d.adjustnetvalue,0) end) '22-3' " +
		" SUM(case when d.differencekind = '110' then isnull(" + this.getAdjustBVorNV("d.") + ",0) else isnull(d.adjustnetvalue,0) end) '22-3' " +
		" from " +
		" UNTMP_REDUCEDETAIL d " +
		" where " +
		" substring(d.propertyno,1,1) = '5' ";
		querySQL += getCondition1() + getCondition2(3) + getCondition4() + getCondition5();
		querySQL += " ) aa";
		return querySQL;
	}
	
	//有價證券本期減少
	protected String querySQL233() {
		String querySQL=" select " +
		" sum(aa.[23-3]) '23-3', " +
		" sum(aa.[24-3]) '24-3' " +
		" from ( " +
		" select " +
		" 0 '23-3', " +
		" sum(isnull(d.reducebookvalue,'0')) '24-3' " +
		" from " +
		" UNTVP_ADJUSTPROOF d " +
		" where " +
		" substring(d.propertyno,1,1) = '9' ";
		querySQL += getCondition1() + getCondition2(2) + getCondition5();
		querySQL += " union " +
		" select " +
		" sum(isnull(d.bookamount,'0')) '23-3', " +
		" sum(isnull(d.bookvalue,'0')) '24-3' " +
		" from " +
		" UNTVP_REDUCEPROOF d " +
		" where " +
		" substring(d.propertyno,1,1) = '9' ";
		querySQL += getCondition1() + getCondition2(2) + getCondition5();
		querySQL += " ) aa";
		return querySQL;
	}
	
	//權利本期減少
	protected String querySQL253() {
		String querySQL=" select " +
		" sum(aa.[25-3]) '25-3', " +
		" sum(aa.[26-3]) '26-3' " +
		" from ( " +
		" select " +
		" 0 '25-3', " +
		// " sum(isnull(d.reducebookvalue,'0')) '26-3' " +
		" SUM(case when d.differencekind = '110' then isnull(" + this.getReduceBVorNV("d.") + ",0) else isnull(d.reducenetvalue,0) end) '26-3' " +
		" from " +
		" UNTRT_ADJUSTPROOF d " +
		" where " +
		" substring(d.propertyno,1,1) = '8' ";
		querySQL += getCondition1() + getCondition2(2) + getCondition5();
		querySQL += " union " +
		" select " +
		" COUNT(1) '25-3', " +
		// " sum(isnull(d.bookvalue,'0')) '26-3' " +
		" SUM(case when d.differencekind = '110' then isnull(" + this.getBVorNV("d.") + ",0) else isnull(d.netvalue,0) end) '26-3' " +
		" from " +
		" UNTRT_REDUCEPROOF d " +
		" where " +
		" substring(d.propertyno,1,1) = '8' ";
		querySQL += getCondition1() + getCondition2(3) + getCondition5();
		querySQL += " ) aa";
		return querySQL;
	}
	
	//土地改良物本期折舊(價值)
	protected String querySQL055() {
		String querySQL=" select " +
			" SUM(case when d.deprunitcb = 'Y' then ISNULL(d.scaledmonthdepr,0) else ISNULL(d.monthdepr,0) end) '5-5' " +
			" from " +
			" UNTDP_MONTHDEPR d " +
			" where " +
			" substring(d.propertyno,1,3) = '111' and d.propertytype = '2' ";
			querySQL += getCondition1() + getCondition2(4) + getCondition4() + getCondition5();
		return querySQL;
	}
	
	//房屋建築及設備本期折舊(價值)
	protected String querySQL115() {
		String querySQL=" select " +
		" SUM(case when d.deprunitcb = 'Y' then ISNULL(d.scaledmonthdepr,0) else ISNULL(d.monthdepr,0) end) '11-5' " +
		" from " +
		" UNTDP_MONTHDEPR d " +
		" where " +
		" substring(d.propertyno,1,1) = '2' and d.propertytype = '3' ";
		querySQL += getCondition1() + getCondition2(4) + getCondition4() + getCondition5();
		return querySQL;
	}
	
	//機械及設備本期折舊(價值)
	protected String querySQL135() {
		String querySQL=" select " +
		" SUM(case when d.deprunitcb = 'Y' then ISNULL(d.scaledmonthdepr,0) else ISNULL(d.monthdepr,0) end) '13-5' " +
		" from " +
		" UNTDP_MONTHDEPR d " +
		" where " +
		" substring(d.propertyno,1,1) = '3' and d.propertytype = '4' ";
		querySQL += getCondition1() + getCondition2(4) + getCondition4() + getCondition5();
		return querySQL;
	}
	
	//交通及運輸設備本期折舊(價值)
	protected String querySQL185() {
		String querySQL=" select " +
		" SUM(case when d.deprunitcb = 'Y' then ISNULL(d.scaledmonthdepr,0) else ISNULL(d.monthdepr,0) end) '18-5' " +
		" from " +
		" UNTDP_MONTHDEPR d " +
		" where " +
		" substring(d.propertyno,1,1) = '4' and d.propertytype = '5' ";
		querySQL += getCondition1() + getCondition2(4) + getCondition4() + getCondition5();
		return querySQL;
	}
	
	//雜項設備本期折舊(價值)
	protected String querySQL225() {
		String querySQL=" select " +
		" SUM(case when d.deprunitcb = 'Y' then ISNULL(d.scaledmonthdepr,0) else ISNULL(d.monthdepr,0) end) '22-5' " +
		" from " +
		" UNTDP_MONTHDEPR d " +
		" where " +
		" substring(d.propertyno,1,1) = '5' and d.propertytype = '6' ";
		querySQL += getCondition1() + getCondition2(4) + getCondition4() + getCondition5();
		return querySQL;
	}
	
	/**
	 * 權利本期折舊 我真的搞不懂那些數字根據是什麼...是名牌嗎? 拿去簽威力彩能中嗎?
	 * @return
	 */
	protected String querySQL555() {
		String querySQL=" select " +
		" SUM(case when d.deprunitcb = 'Y' then ISNULL(d.scaledmonthdepr,0) else ISNULL(d.monthdepr,0) end) '55-5' " +
		" from " +
		" UNTDP_MONTHDEPR d " +
		" where " +
		" substring(d.propertyno,1,1) = '8' and d.propertytype = '8' ";
		querySQL += getCondition1() + getCondition2(4) + getCondition4() + getCondition5();
		return querySQL;
	}
}
