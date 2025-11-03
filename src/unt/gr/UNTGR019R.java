/*
*<br>程式目的：財產目錄總表 
*<br>程式代號：UNTGR019R
*<br>撰寫日期：103.09.26
*<br>程式作者：Kang Da
*<br>--------------------------------------------------------
*<br>修改作者　　修改日期　　　修改目的
*<br>--------------------------------------------------------
*/

package unt.gr;

import java.sql.ResultSet;
import javax.swing.table.DefaultTableModel;
import util.*;

public class UNTGR019R extends SuperBean {
	
	private final static String CHECK_ENTERORG = TCGHCommon.getSYSCODEName("01", "ETO");
	
	private String isAdminManager;
	private String organID;
	
	public String getIsAdminManager() { return checkGet(isAdminManager); }
	public void setIsAdminManager(String s) { isAdminManager = checkSet(s); }
	public String getOrganID() { return checkGet(organID); }
	public void setOrganID(String s) { organID = checkSet(s); }
	
	private String q_enterOrg;							// 入帳機關
	private String q_enterOrgName;				// 入帳機關-名
	private String q_ownership;							// 權屬
	private String q_differenceKind;					// 財產區分
	private String q_isorganmanager;				// 列印彙總表
	private String q_reportYear;						// 年度	
	private String q_valuable;								// 珍貴財產
	private String q_reportType;						//報表類別
	private String q_reportMonth;					//月份
	private String reportType;						// 報表類型
	
	public String getReportType() { return checkGet(reportType); }
	public void setReportType(String reportType) { this.reportType = checkGet(reportType); }
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
	public String getQ_valuable() {		return checkGet(q_valuable);	}
	public String getQ_reportType() {		return checkGet(q_reportType);	}
	public void setQ_reportType(String q_reportType) {		this.q_reportType = checkSet(q_reportType);	}	
	public String getQ_reportMonth() {		return checkGet(q_reportMonth);	}
	public void setQ_reportMonth(String q_reportMonth) {		this.q_reportMonth = checkSet(q_reportMonth);	}
	public void setQ_valuable(String q_valuable) {		this.q_valuable = checkSet(q_valuable);	}
	
	//1080305 TDCM 新增
		String q_enterorgKind1; //及所屬含實中
		public String getQ_enterorgKind1(){ return checkGet(q_enterorgKind1); }
		public void setQ_enterorgKind1(String s){ q_enterorgKind1=checkSet(s); }
	
	public String getQuerySQL(boolean isValuable){
		StringBuffer conditionSb = new StringBuffer();
		if("N".equals(getQ_isorganmanager())){
			//1080305 TDCM 新增 :查詢條件，依所選的「含實中」、「及所屬」控制各table「入帳機關(enterOrg)」需符合的條件
			if("".equals(getQ_enterorgKind1())){
				conditionSb.append(" and a.enterorg = ").append(Common.sqlChar(getQ_enterOrg()));
			}else{
				if ("1".equals(getQ_enterorgKind1()) && "120".equals(getQ_differenceKind()) && !TCGHCommon.getSYSCODEName("01", "ETO").equals(getQ_enterOrg())) {
					conditionSb.append(" and (a.enterorg = ").append(Common.sqlChar(getQ_enterOrg())).append( " or a.enterorg in (select organid from SYSCA_ORGAN where organsuperior1=").append(Common.sqlChar(getQ_enterOrg())).append(" ))");
				} else if ("2".equals(getQ_enterorgKind1()) && "120".equals(getQ_differenceKind()) && TCGHCommon.getSYSCODEName("02", "ETO").equals(getQ_enterOrg())) {
					conditionSb.append(" and (a.enterorg = ").append(Common.sqlChar(getQ_enterOrg())).append(" or a.enterorg in (select organid from SYSCA_ORGAN where organsuperior2=").append(Common.sqlChar(getQ_enterOrg())).append(" or organsuperior3=").append(Common.sqlChar(getQ_enterOrg())).append(" ))");
				} else if ("3".equals(getQ_enterorgKind1()) && "110".equals(getQ_differenceKind()) && TCGHCommon.getSYSCODEName("01", "ETO").equals(getQ_enterOrg())) {
					conditionSb.append(" and (a.enterorg = ").append(Common.sqlChar(getQ_enterOrg())).append(" or a.enterorg in (select organid from SYSCA_ORGAN where organsuperior2=").append(Common.sqlChar(getQ_enterOrg())).append(" or organsuperior3=").append(Common.sqlChar(getQ_enterOrg())).append(" ))");
				}
			}
		}else if("Y".equals(getQ_isorganmanager())){
			if("120".equals(getQ_differenceKind())){
				conditionSb.append(" and (a.enterorg = ").append(Common.sqlChar(getQ_enterOrg()))
						   .append(" or a.enterorg in (select organid from SYSCA_ORGAN where organsuperior2 = ").append(Common.sqlChar(getQ_enterOrg())).append(")")
						   .append(")");			
			}else if("110".equals(getQ_differenceKind())){
				if(CHECK_ENTERORG.equals(getQ_enterOrg())){
					conditionSb.append(" and a.enterorg in (select organid from SYSCA_ORGAN where ismanager = 'Y')");
				}else{
					conditionSb.append(" and (a.enterorg = ").append(Common.sqlChar(getQ_enterOrg()))
							   .append(" or a.enterorg in (select organid from SYSCA_ORGAN where organsuperior1 = ").append(Common.sqlChar(getQ_enterOrg())).append(")")
							   .append(")");			
				}
			}
		}
		
		if(!"".equals(getQ_ownership())){
			conditionSb.append(" and a.ownership = ").append(Common.sqlChar(getQ_ownership()));
		}
		if(!"".equals(getQ_differenceKind())){
			conditionSb.append(" and a.differencekind = ").append(Common.sqlChar(getQ_differenceKind()));
		}
		if(!"".equals(getQ_reportYear())){
			//依畫面輸入之年度+1年
			String reportyear = String.valueOf(Integer.parseInt(getQ_reportYear())+1);
			conditionSb.append(" and a.reportyear = ").append(Common.sqlChar(reportyear));
		}
		if(!"".equals(getQ_reportType())){
			conditionSb.append(" and a.reporttype = ").append(Common.sqlChar(getQ_reportType()));
		}
		if(!"".equals(getQ_reportMonth())){
			conditionSb.append(" and a.reportmonth = ").append(Common.sqlChar(getQ_reportMonth()));
		}
		
		if(isValuable){
			if(!"".equals(getQ_valuable()))
				conditionSb.append(" and a.valuable = ").append(Common.sqlChar(getQ_valuable()));
		}
		
		return conditionSb.toString();
	}
	
	public DefaultTableModel getResultModel() throws Exception {
	    DefaultTableModel model = null;
	    Database db = null;
	    
	    try{
	    	java.util.List<String[]> arrList = new java.util.ArrayList<String[]>();
	    	String[] cols = new String[]{
						    			"F11", "F12", "F13", 
						    			"F21", "F22", 
						    			"F31", "F32", "F33", "F34", "F35", "F36",
						    			"F41", "F42",
						    			"F51", "F52", "F53", "F54", "F55",
						    			"F61", "F62", "F63",
						    			"F71", "F72", 
						    			"F81", "F82",
						    			"F91"
						    			};
	    	
	    	db = new Database();
			
	    	String[] rowArray = new String[cols.length];
			boolean flag = false;								// 判斷是否真的有資料使用
	    	
			// 土地
			String sql = " select SUM(isnull(oldamount,0)), SUM(isnull(round(oldarea/10000,6),0)), SUM(isnull(oldbvsubtotal,0))" +
						 		  " from UNTGR_UNTGR009R a" + 
								  " where propertytype='10'";
			sql += getQuerySQL(true) + " group by propertytype ";
			ResultSet rs = db.querySQL(sql);
		    if(rs.next()){
		    	if(rs.getDouble(1) > 0){
		    		if(!flag){
			    		flag = true;
			    	}
		    	}
		    	rowArray[0] = Common.MoneyFormat(rs.getDouble(1), 0);														// F11	
		    	rowArray[1] = rs.getDouble(2)==0?"0":Common.MoneyFormat(rs.getDouble(2), 6);			// F12
		    	rowArray[2] = Common.MoneyFormat(rs.getDouble(3), 0);														// F13
		    }
		    sql = null;
		    rs.close();
		    rs = null;
		    
		    // 土地改良物
		    sql = " select  SUM(isnull(oldamount,0)), SUM(isnull(oldbvsubtotal,0))" +
			 		  " from UNTGR_UNTGR009R a" + 
					  " where propertytype='20'";
		    sql += getQuerySQL(true) + " group by propertytype ";
			rs = db.querySQL(sql);
		    if(rs.next()){
		    	if(rs.getDouble(1) > 0){
		    		if(!flag){
			    		flag = true;
			    	}
		    	}
		    	rowArray[3] = Common.MoneyFormat(rs.getDouble(1), 0);					// F21
		    	rowArray[4] = Common.MoneyFormat(rs.getDouble(2), 0);					// F22
		    }
		    sql = null;
		    rs.close();
		    rs = null;
		    
		    // 辦公房屋 & 價值
		    sql = " select  SUM(isnull(oldamount,0)), SUM(isnull(oldarea,0)), SUM(isnull(oldbvsubtotal,0))" +
			 		  " from UNTGR_UNTGR009R a" + 
					  " where propertytype='31'";
		    sql += getQuerySQL(true) + " group by propertytype ";
			rs = db.querySQL(sql);
		    if(rs.next()){
		    	if(rs.getDouble(1) > 0){
		    		if(!flag){
			    		flag = true;
			    	}
		    	}
		    	rowArray[5] = Common.MoneyFormat(rs.getDouble(1), 0);														// F31	
		    	rowArray[6] = Common.MoneyFormat(rs.getDouble(2), 2);														// F32
		    	rowArray[10] = Common.MoneyFormat(rs.getDouble(3), 0);														// F36
		    }
		    sql = null;
		    rs.close();
		    rs = null;
		    
		    // 宿舍
		    sql = " select  SUM(isnull(oldamount,0)), SUM(isnull(oldarea,0))" +
			 		  " from UNTGR_UNTGR009R a" + 
					  " where propertytype='32'";
		    sql += getQuerySQL(true) + " group by propertytype ";
			rs = db.querySQL(sql);
		    if(rs.next()){
		    	if(rs.getDouble(1) > 0){
		    		if(!flag){
			    		flag = true;
			    	}
		    	}
		    	rowArray[7] = Common.MoneyFormat(rs.getDouble(1), 0);														// F33	
		    	rowArray[8] = Common.MoneyFormat(rs.getDouble(2), 2);														// F34
		    }
		    sql = null;
		    rs.close();
		    rs = null;

		    // 其他
		    sql = " select  SUM(isnull(oldamount,0))" +
			 		  " from UNTGR_UNTGR009R a" + 
					  " where propertytype='33'";
		    sql += getQuerySQL(true) + " group by propertytype ";
			rs = db.querySQL(sql);
		    if(rs.next()){
		    	if(rs.getDouble(1) > 0){
		    		if(!flag){
			    		flag = true;
			    	}
		    	}
		    	rowArray[9] = Common.MoneyFormat(rs.getDouble(1), 0);														// F35
		    }
		    sql = null;
		    rs.close();
		    rs = null;
		    
		    // 機械及設備
		    sql = " select  SUM(isnull(oldamount,0)), SUM(isnull(oldbvsubtotal,0))" +
			 		  " from UNTGR_UNTGR009R a" + 
					  " where propertytype='40'";
		    sql += getQuerySQL(true) + " group by propertytype ";
			rs = db.querySQL(sql);
		    if(rs.next()){
		    	if(rs.getDouble(1) > 0){
		    		if(!flag){
			    		flag = true;
			    	}
		    	}
		    	rowArray[11] = Common.MoneyFormat(rs.getDouble(1), 0);															// F41	
		    	rowArray[12] = Common.MoneyFormat(rs.getDouble(2), 0);															// F42
		    }
		    sql = null;
		    rs.close();
		    rs = null;
		    
		    // 交通及運輸設備-船 & 價值
		    sql = " select  SUM(isnull(oldamount,0)), SUM(isnull(oldbvsubtotal,0))" +
			 		  " from UNTGR_UNTGR009R a" + 
					  " where propertytype='51'";
		    sql += getQuerySQL(true) + " group by propertytype ";
			rs = db.querySQL(sql);
		    if(rs.next()){
		    	if(rs.getDouble(1) > 0){
		    		if(!flag){
			    		flag = true;
			    	}
		    	}
		    	rowArray[13] = Common.MoneyFormat(rs.getDouble(1), 0);															// F51	
		    	rowArray[17] = Common.MoneyFormat(rs.getDouble(2), 0);															// F55
		    }
		    sql = null;
		    rs.close();
		    rs = null;

		    // 交通及運輸設備-飛機
		    sql = " select  SUM(isnull(oldamount,0))" +
			 		  " from UNTGR_UNTGR009R a" + 
					  " where propertytype='52'";
		    sql += getQuerySQL(true) + " group by propertytype ";
			rs = db.querySQL(sql);
		    if(rs.next()){
		    	if(rs.getDouble(1) > 0){
		    		if(!flag){
			    		flag = true;
			    	}
		    	}
		    	rowArray[14] = Common.MoneyFormat(rs.getDouble(1), 0);															// F52
		    }
		    sql = null;
		    rs.close();
		    rs = null;

		    // 交通及運輸設備-汽機車
		    sql = " select  SUM(isnull(oldamount,0))" +
			 		  " from UNTGR_UNTGR009R a" + 
					  " where propertytype='53'";
		    sql += getQuerySQL(true) + " group by propertytype ";
			rs = db.querySQL(sql);
		    if(rs.next()){
		    	if(rs.getDouble(1) > 0){
		    		if(!flag){
			    		flag = true;
			    	}
		    	}
		    	rowArray[15] = Common.MoneyFormat(rs.getDouble(1), 0);															// F53
		    }
		    sql = null;
		    rs.close();
		    rs = null;

		    // 交通及運輸設備-其他
		    sql = " select  SUM(isnull(oldamount,0))" +
			 		  " from UNTGR_UNTGR009R a" + 
					  " where propertytype='54'";
		    sql += getQuerySQL(true) + " group by propertytype ";
			rs = db.querySQL(sql);
		    if(rs.next()){
		    	if(rs.getDouble(1) > 0){
		    		if(!flag){
			    		flag = true;
			    	}
		    	}
		    	rowArray[16] = Common.MoneyFormat(rs.getDouble(1), 0);															// F54
		    }
		    sql = null;
		    rs.close();
		    rs = null;

		    // 雜項-圖書 & 價值
		    sql = " select  SUM(isnull(oldamount,0)), SUM(isnull(oldbvsubtotal,0))" +
			 		  " from UNTGR_UNTGR009R a" + 
					  " where propertytype='61'";
		    sql += getQuerySQL(true) + " group by propertytype ";
			rs = db.querySQL(sql);
		    if(rs.next()){
		    	if(rs.getDouble(1) > 0){
		    		if(!flag){
			    		flag = true;
			    	}
		    	}
		    	rowArray[18] = Common.MoneyFormat(rs.getDouble(1), 0);															// F61
		    	rowArray[20] = Common.MoneyFormat(rs.getDouble(2), 0);															// F63
		    }
		    sql = null;
		    rs.close();
		    rs = null;

		    // 雜項-其他
		    sql = " select  SUM(isnull(oldamount,0))" +
			 		  " from UNTGR_UNTGR009R a" + 
					  " where propertytype='63'";
		    sql += getQuerySQL(true) + " group by propertytype ";
			rs = db.querySQL(sql);
		    if(rs.next()){
		    	if(rs.getDouble(1) > 0){
		    		if(!flag){
			    		flag = true;
			    	}
		    	}
		    	rowArray[19] = Common.MoneyFormat(rs.getDouble(1), 0);															// F62
		    }
		    sql = null;
		    rs.close();
		    rs = null;
		    
		    // 有價證劵
		    sql = " select  SUM(isnull(oldamount,0)), SUM(isnull(oldbvsubtotal,0))" +
			 		  " from UNTGR_UNTGR009R a" + 
					  " where propertytype='70'";
		    sql += getQuerySQL(true) + " group by propertytype ";
			rs = db.querySQL(sql);
		    if(rs.next()){
		    	if(rs.getDouble(1) > 0 && rs.getDouble(2) > 0){
		    		if(!flag){
			    		flag = true;
			    	}
		    	}
		    	rowArray[21] = Common.MoneyFormat(rs.getDouble(1), 0);					// F71	
		    	rowArray[22] = Common.MoneyFormat(rs.getDouble(2), 0);					// F72
		    }
		    sql = null;
		    rs.close();
		    rs = null;
		    
		    // 權利
		    sql = " select  SUM(isnull(oldamount,0)), SUM(isnull(oldbvsubtotal,0))" +
			 		  " from UNTGR_UNTGR009R a" + 
					  " where propertytype='80'";
		    sql += getQuerySQL(true) + " group by propertytype ";
			rs = db.querySQL(sql);
		    if(rs.next()){
		    	if(rs.getDouble(1) > 0){
		    		if(!flag){
			    		flag = true;
			    	}
		    	}
		    	rowArray[23] = Common.MoneyFormat(rs.getDouble(1), 0);					// F81
		    	rowArray[24] = Common.MoneyFormat(rs.getDouble(2), 0);					// F82
		    }
		    sql = null;
		    rs.close();
		    rs = null;
		    
		    // 總值
		    sql = " select  SUM(isnull(oldbvsubtotal,0))" +
			 		  " from UNTGR_UNTGR009R a" + 
					  " where propertytype='T0'";
		    sql += getQuerySQL(true) + " group by propertytype ";
			rs = db.querySQL(sql);
		    if(rs.next()){
		    	if(rs.getDouble(1) > 0){
		    		if(!flag){
			    		flag = true;
			    	}
		    	}
		    	rowArray[25] = Common.MoneyFormat(rs.getDouble(1), 0);					// F91
		    }
		    sql = null;
		    rs.close();
		    rs = null;		    
			
			arrList.add(rowArray);
		    
		    if(arrList.size()>0 && flag){
		    	Object[][] data = new Object[0][0];
		        data = (Object[][])arrList.toArray(data);
		        model = new javax.swing.table.DefaultTableModel();
		        model.setDataVector(data, cols);
		    }
	    }catch(Exception x){
	    	System.out.println("[TCGH]-[UNTGR019R]-[列印發生異常]");
	    	x.printStackTrace();
	    }finally{
	    	if(db != null){
	    		db.closeAll();
	    	}
	    }
	    
	    return model;
	}
	
}
