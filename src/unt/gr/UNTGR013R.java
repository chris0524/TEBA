
package unt.gr;

import java.sql.ResultSet;
import java.util.Map;

import javax.swing.table.DefaultTableModel;

import unt.ch.UNTCH_Tools;
import util.Common;
import util.Database;
import util.Datetime;
import util.SuperBean;
import util.TCGHCommon;

/**
 *<br>程式目的：財產目錄(特種基金用)
 *<br>程式代號：UNTGR013R
 *<br>撰寫日期：103.10.02
 *<br>程式作者：Kang Da
 *<br>--------------------------------------------------------
 *<br>修改作者　　修改日期　　　修改目的
 *<br>--------------------------------------------------------
 */
public class UNTGR013R extends SuperBean {
	
	private final static String MM = "12";
	private final static String CHECK_ENTERORG = TCGHCommon.getSYSCODEName("01", "ETO");
	
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
	private String q_reportType;						// 報表類型
	private String q_valuable;							// 珍貴財產						
	private String q_verify;							// 入帳
	private String q_dataState;							// 資料狀態 
	
	
	public String getQ_reportType() {	return q_reportType;}
	public void setQ_reportType(String q_reportType) { this.q_reportType = checkSet(q_reportType); }
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
	public void setQ_valuable(String q_valuable) {		this.q_valuable = checkSet(q_valuable);	}
	public String getQ_verify() {		return checkGet(q_verify);	}
	public void setQ_verify(String q_verify) {		this.q_verify = checkSet(q_verify);	}
	public String getQ_dataState() {		return checkGet(q_dataState);	}
	public void setQ_dataState(String q_dataState) {		this.q_dataState = checkSet(q_dataState);	}
	
	//1080305 TDCM 新增
		String q_enterorgKind1; //及所屬含實中
		public String getQ_enterorgKind1(){ return checkGet(q_enterorgKind1); }
		public void setQ_enterorgKind1(String s){ q_enterorgKind1=checkSet(s); }
	
	public String getConditionSQL(boolean isDataState, int enterDateType, boolean isValuable) throws Exception {
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
					conditionSb.append(" and (a.enterorg = ").append(Common.sqlChar(getQ_enterOrg()))
							   .append(" or a.enterorg in (select organid from SYSCA_ORGAN where ismanager = 'Y'))");
				}else{
					conditionSb.append(" and (a.enterorg = ").append(Common.sqlChar(getQ_enterOrg()))
							   .append(" or a.enterorg in (select organid from SYSCA_ORGAN where organsuperior1 = ").append(Common.sqlChar(getQ_enterOrg())).append(")")
							   .append(")");			
				}
			}
		}
		
		if(!"".equals(getQ_ownership()))
			conditionSb.append(" and a.ownership = ").append(Common.sqlChar(getQ_ownership()));
		if(!"".equals(getQ_differenceKind()))
			conditionSb.append(" and a.differencekind = ").append(Common.sqlChar(getQ_differenceKind()));
		if(!"".equals(getQ_verify()))
			conditionSb.append(" and a.verify = ").append(Common.sqlChar(getQ_verify()));
		
		int y = Common.getInt(getQ_reportYear()) + 1911;
		if(enterDateType == 1){
			conditionSb.append(" and a.enterdate >= ").append(Common.sqlChar(y + "0101"))
					   .append(" and a.enterdate <= ").append(Common.sqlChar(y + "1231"));
		}else if(enterDateType == 2){
			conditionSb.append(" and a.adjustdate >= ").append(Common.sqlChar(y + "0101"))
			   		   .append(" and a.adjustdate <= ").append(Common.sqlChar(y + "1231"));
		}else if(enterDateType == 3){
			conditionSb.append(" and a.reducedate >= ").append(Common.sqlChar(y + "0101"))
			   		   .append(" and a.reducedate <= ").append(Common.sqlChar(y + "1231"));
		}
		
		if(isDataState){
			if(!"".equals(getQ_dataState()))
				conditionSb.append(" and a.datastate = ").append(Common.sqlChar(getQ_dataState()));
		}
		
		if(isValuable){
			if(!"".equals(getQ_valuable()))
				conditionSb.append(" and a.valuable = ").append(Common.sqlChar(getQ_valuable()));
		}
		
		return conditionSb.toString();
	}
	
	public String getConditionSQL2(String reportyear) throws Exception {
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
					conditionSb.append(" and (a.enterorg = ").append(Common.sqlChar(getQ_enterOrg()))
							   .append(" or a.enterorg in (select organid from SYSCA_ORGAN where ismanager = 'Y'))");
				}else{
					conditionSb.append(" and (a.enterorg = ").append(Common.sqlChar(getQ_enterOrg()))
							   .append(" or a.enterorg in (select organid from SYSCA_ORGAN where organsuperior1 = ").append(Common.sqlChar(getQ_enterOrg())).append(")")
							   .append(")");			
				}
			}
		}
		
		if(!"".equals(getQ_ownership()))
			conditionSb.append(" and a.ownership = ").append(Common.sqlChar(getQ_ownership()));
		if(!"".equals(getQ_differenceKind()))
			conditionSb.append(" and a.differencekind = ").append(Common.sqlChar(getQ_differenceKind()));
		if(!"".equals(getQ_valuable()))
			conditionSb.append(" and a.valuable = ").append(Common.sqlChar(getQ_valuable()));
		if(!"".equals(reportyear))
			conditionSb.append(" and a.reportyear = ").append(Common.sqlChar(""  + (reportyear)));
		return conditionSb.toString();
	}
	
	/**
	 * reportYear >= 105 ?
	 * @return
	 */
	public boolean isOver105Year() {
		return Integer.parseInt(this.getQ_reportYear()) >= 105;
	}
	
	public DefaultTableModel getResultModel() throws Exception {
	    DefaultTableModel model = null;
	    Database db = null;
	    
	    try{
	    	java.util.List<String[]> arrList = new java.util.ArrayList<String[]>();
	    	String[] cols = new String[]{
						    			"F0", "F1", "F2", "F3", "F4", "F5", 
						    			"F6", "F7", "F8", "F9", "F10", "F11", "F12", "CKEY"
						    			};
	    	
	    	db = new Database();
	    	
	    	String sameField = " select SUBSTRING(a.propertyno,1,1) as propertyNo1, SUBSTRING(a.propertyno,2,2) as propertyNo2, " +
			   				   " SUBSTRING(a.propertyno,4,2) as propertyNo3, SUBSTRING(a.propertyno,6,2) as propertyNo4, " +  
			   				   " (select b.propertyname from SYSPK_PROPERTYCODE b where (b.enterorg = a.enterorg or b.enterorg = '000000000A') and b.propertyno = substring(a.propertyno,1,7)) as propertyName, ";
	    	
	    	String nowYearSameField = " 0 as oldAmount, 0 as oldArea, 0 as addAmount, 0 as addArea, 0 as reduceAmount, 0 as reduceArea, ";
	    	String lastYearSameField = " 0 as addAmount, 0 as addArea, 0 as reduceAmount, 0 as reduceArea, " +
					   				   " 0 as nowAmount, 0 as nowArea, 0 as nowBookValue, 0 as nowAccumDepr, 0 as nowNetValue ";
	    	
	    	String addReduceSameField1 = " select SUBSTRING(a.propertyno,1,1) as propertyNo1, SUBSTRING(a.propertyno,2,2) as propertyNo2, " + 
			 							 " SUBSTRING(a.propertyno,4,2) as propertyNo3, SUBSTRING(a.propertyno,6,2) as propertyNo4, " + 
			 							 " (select b.propertyname from SYSPK_PROPERTYCODE b where (b.enterorg = a.enterorg or b.enterorg = '000000000A') and b.propertyno = substring(a.propertyno,1,7)) as propertyName, " + 
			 							 " 0 as oldAmount, 0 as oldArea, ";
	    	String addReduceSameField2 = " 0 as nowAmount, 0 as nowArea, 0 as nowBookValue, 0 as nowAccumDepr, 0 as nowNetValue ";
	    	
	    	String tableNow = "_" + getQ_reportYear() + MM;
	    	String tableLast = "_" + Common.formatFrontZero(String.valueOf(Common.getInt(getQ_reportYear())-1), 3) + MM;
	    	String nextyear = Common.formatFrontZero(String.valueOf(Common.getInt(getQ_reportYear()) + 1), 3); //reportYear + 1
	    	UNTCH_Tools ul = new UNTCH_Tools();
	    	String yyyymm = ul._transToCE_Year(this.getQ_reportYear()) + MM;
	   // 	tableNow = "";
	   // 	tableLast = "";
	    	
	    	StringBuilder sb = new StringBuilder();
	    	
	    	// [結存]-[去年] ----> [1-土地][2-建物][3-土地改良][4-動產][5-有價證劵][6-權利]
	    	String UNTGR_UNTGR011R = queryUNTGR_UNTGR011R(sameField, lastYearSameField);
	    	sb.append(UNTGR_UNTGR011R);
	    	
	    	// [結存]-[本年] bookvalue, area from 初始檔
	    	if (sb.length() > 0) {
	    		sb.append(" union all ").append(this.queryUNTGR_UNTGR011RForNow(nextyear, sameField));
	    	}
	    	
	    	// [結存]-[本年] accumdepr from 折舊月檔
	    	if (sb.length() > 0) {
	    		sb.append(" union all ").append(this.queryYearTotalAccumDepr(yyyymm, sameField));
	    	}
	    	
	    	// [增加、減少]-[土地] ----> [1-土地主][2-土地增減值][3-土地減損]
	    	for(int i=1; i<=3; i++){
	    		getLandAddReduceSQL(sb, i, addReduceSameField1, addReduceSameField2, true);
	    	}
	    	
	    	// [增加、減少]-[土地改良] ----> [1-土地改良主][2-土地改良增減值][3-土地改良減損]
	    	for(int i=1; i<=3; i++){
	    		getAttachmentAddReduceSQL(sb, i, addReduceSameField1, addReduceSameField2, true);
	    	}
	    	
	    	// [增加、減少]-[建物] ----> [1-建物主][2-建物增減值][3-建物減損]
	    	for(int i=1; i<=3; i++){
	    		getBuildingAddReduceSQL(sb, i, addReduceSameField1, addReduceSameField2, true);
	    	}
	    	
	    	// [增加、減少]-[動產] ----> [1-動產主][2-動產增減值][3-動產減損]
	    	for(int i=1; i<=3; i++){
	    		getMovableAddReduceSQL(sb, i, addReduceSameField1, addReduceSameField2, true);
	    	}
	    	
	    	// [增加、減少]-[有價證劵] ----> [1-有價證劵主][2-有價證劵增減值][3-有價證劵減損]
	    	for(int i=1; i<=3; i++){
	    		getVP_AddProofAddReduceSQL(sb, i, addReduceSameField1, addReduceSameField2, true);
	    	}
	    	
	    	// [增加、減少]-[權利] ----> [1-權利主][2-權利增減值][3-權利減損]
	    	for(int i=1; i<=3; i++){
	    		getRT_AddProofAddReduceSQL(sb, i, addReduceSameField1, addReduceSameField2, true);
	    	}
	    	
	    	
	    	String nameOf503 = TCGHCommon.getLookup(" select propertyname from SYSPK_PROPERTYCODE " +
													" where (enterorg = " + Common.sqlChar(getQ_enterOrg()) + " or enterorg = '000000000A') " +
													" and propertyno = '503' ");
	    	java.util.Map<String, java.util.Map<String, double[]>> outMap = new java.util.TreeMap<String, java.util.Map<String, double[]>>(); 
	    	
	    	String sql = " select T.propertyNo1, T.propertyNo2, T.propertyNo3, T.propertyNo4, T.propertyName, " +
	    				 " SUM(ISNULL(T.oldAmount, 0)), SUM(ISNULL(T.oldArea, 0)), SUM(ISNULL(T.addAmount, 0)), SUM(ISNULL(T.addArea, 0)), " +
	    				 " SUM(ISNULL(T.reduceAmount, 0)), SUM(ISNULL(T.reduceArea, 0)), " +
	    				 " SUM(ISNULL(T.nowAmount, 0)), SUM(ISNULL(T.nowArea, 0)), " +
	    				 " SUM(ISNULL(T.nowBookValue, 0)), SUM(ISNULL(T.nowAccumDepr, 0)), SUM(ISNULL(T.nowNetValue, 0)) " +
	    				 " from (" + sb.toString() + ") as T " +
	    			     " group by T.propertyNo1, T.propertyNo2, T.propertyNo3, T.propertyNo4, T.propertyName " + 
	    			     " order by T.propertyNo1, T.propertyNo2, T.propertyNo3, T.propertyNo4 ";
	    	ResultSet rs = db.querySQL(sql);
	    	while(rs.next()){
	    		if("".equals(Common.get(rs.getString(1)))){
	    			continue;
	    		}
	    		if("".equals(Common.get(rs.getString(2)))){
	    			continue;
	    		}
	    		
	    		// 需判斷土地和土地改良
	    		String key1 = Common.get(rs.getString(1));
	    		if("1".equals(key1)){
	    			if("11".equals(Common.get(rs.getString(2)))){
	    				key1 = "111";
	    			}
	    		}
	    		
	    		java.util.Map<String, double[]> dtl = outMap.get(key1);
	    		if(dtl == null){
	    			dtl = new java.util.TreeMap<String, double[]>();
	    			outMap.put(key1, dtl);
	    		}
	    		
	    		String key2;
	    		if("5".equals(Common.get(rs.getString(1))) && "03".equals(Common.get(rs.getString(2)))){
	    			key2 = "5_03_ _ _" + (nameOf503.equals("")?" ":nameOf503);
	    		}else{
	    			key2 = Common.get(rs.getString(1)) + "_" + Common.get(rs.getString(2)) + "_" + 
	    				   (Common.get(rs.getString(3)).equals("")?" ":Common.get(rs.getString(3))) + "_" +  
	    				   (Common.get(rs.getString(4)).equals("")?" ":Common.get(rs.getString(4))) + "_" + 
	    				   (Common.get(rs.getString(5)).equals("")?" ":Common.get(rs.getString(5)));
	    		}
	    		
	    		// 各類小計
	    		double[] t = dtl.get("Z");
	    		if(t == null){
	    			t = new double[11];
	    			dtl.put("Z", t);
	    		}
	    		t[0] += rs.getDouble(6);
	    		t[1] += rs.getDouble(7);
	    		t[2] += rs.getDouble(8);
	    		t[3] += rs.getDouble(9);
	    		t[4] += rs.getDouble(10);
	    		t[5] += rs.getDouble(11);
	    		t[6] += rs.getDouble(6) + rs.getDouble(8) - rs.getDouble(10);
	    		t[7] += rs.getDouble(7) + rs.getDouble(9) - rs.getDouble(11);
//	    		t[6] += rs.getDouble(12);
//	    		t[7] += rs.getDouble(13);
	    		t[8] += rs.getDouble(14);
	    		t[9] += rs.getDouble(15);
	    		t[10] += rs.getDouble(16);
	    		
	    		// 各類明細值
	    		double[] d = dtl.get(key2);
	    		if(d == null){
	    			d = new double[11];
	    			dtl.put(key2, d);
	    		}
	    		d[0] += rs.getDouble(6);
	    		d[1] += rs.getDouble(7);
	    		d[2] += rs.getDouble(8);
	    		d[3] += rs.getDouble(9);
	    		d[4] += rs.getDouble(10);
	    		d[5] += rs.getDouble(11);
	    		d[6] += rs.getDouble(6) + rs.getDouble(8) - rs.getDouble(10);
	    		d[7] += rs.getDouble(7) + rs.getDouble(9) - rs.getDouble(11);
//	    		d[6] += rs.getDouble(12);
//	    		d[7] += rs.getDouble(13);
	    		d[8] += rs.getDouble(14);
	    		d[9] += rs.getDouble(15);
	    		d[10] += rs.getDouble(16);
	    	}
	    	rs.close();
	    	rs = null;
	    	
	    	double totalAmount1 = 0.0d;
	    	double totalAmount2 = 0.0d;
	    	double totalAmount3 = 0.0d;
	    	for(Map.Entry<String, java.util.Map<String, double[]>> o : outMap.entrySet()){
	    		for(Map.Entry<String, double[]> dtl : o.getValue().entrySet()){
	    			String[] keys;
	    			if(dtl.getKey().equals("Z")){
	    				keys = new String[5];
	    				keys[0] = getGroupName1(o.getKey());
	    				keys[1] = getGroupName2(o.getKey());
	    			}else{
	    				keys = dtl.getKey().split("_");
	    			}
	    			if(keys.length != 5){
	    				continue;
	    			}
	    			double[] d = dtl.getValue();
	    			
	    			String[] rowArray = new String[cols.length];
	    			rowArray[0] = Common.get(keys[0]);
	    			rowArray[1] = Common.get(keys[1]);
	    			rowArray[2] = Common.get(keys[2]);
	    			rowArray[3] = Common.get(keys[3]);
	    			rowArray[4] = Common.get(keys[4]);
	    			
	    			boolean isLandOrBuilding = false;
	    			if("1".equals(o.getKey()) || "2".equals(o.getKey())){
	    				isLandOrBuilding = true;
	    			}else{
	    				isLandOrBuilding = false;
	    			}
	    			
	    			if ("1".equals(keys[0]) && !"11".equals(keys[1])) { //若為土地則要將面積換算為公頃
		    			rowArray[5] = Common.MoneyFormat(d[0], 0) + (isLandOrBuilding?"\n" + (d[1]==0?"0":Common.MoneyFormat(d[1]/10000, 6)):"");
	    				rowArray[6] = Common.MoneyFormat(d[2], 0) + (isLandOrBuilding?"\n" + (d[3]==0?"0":Common.MoneyFormat(d[3]/10000, 6)):"");
	    				rowArray[7] = Common.MoneyFormat(d[4], 0) + (isLandOrBuilding?"\n" + (d[5]==0?"0":Common.MoneyFormat(d[5]/10000, 6)):"");
	    				rowArray[8] = Common.MoneyFormat(d[6], 0) + (isLandOrBuilding?"\n" + (d[7]==0?"0":Common.MoneyFormat(d[7]/10000, 6)):"");
    				}else if("2".equals(o.getKey())){
    					rowArray[5] = Common.MoneyFormat(d[0], 0) + "\n" + (d[1]==0?"0":Common.MoneyFormat(d[1], 2));
        				rowArray[6] = Common.MoneyFormat(d[2], 0) + "\n" + (d[3]==0?"0":Common.MoneyFormat(d[3], 2));
        				rowArray[7] = Common.MoneyFormat(d[4], 0) + "\n" + (d[5]==0?"0":Common.MoneyFormat(d[5], 2));
        				rowArray[8] = Common.MoneyFormat(d[6], 0) + "\n" + (d[7]==0?"0":Common.MoneyFormat(d[7], 2));
	    			} else if("9".equals(o.getKey())){
    					rowArray[5] = Common.MoneyFormat(d[0], 1);
        				rowArray[6] = Common.MoneyFormat(d[2], 1);
        				rowArray[7] = Common.MoneyFormat(d[4], 1);
        				rowArray[8] = Common.MoneyFormat(d[6], 1);
	    			} else {
	    				rowArray[5] = d[0] + (isLandOrBuilding?"\n" + (d[1]==0?"0":d[1]):"");
	    				rowArray[6] = d[2] + (isLandOrBuilding?"\n" + (d[3]==0?"0":d[3]):"");
	    				rowArray[7] = d[4] + (isLandOrBuilding?"\n" + (d[5]==0?"0":d[5]):"");
	    				rowArray[8] = d[6] + (isLandOrBuilding?"\n" + (d[7]==0?"0":d[7]):"");
	    			}
	    			
	    			//土地合計使用
	    			if ("土地".equals(keys[0])) { 
		    			rowArray[5] = Common.MoneyFormat(d[0], 0) + (isLandOrBuilding?"\n" + (d[1]==0?"0":Common.MoneyFormat(d[1]/10000, 6)):"");
	    				rowArray[6] = Common.MoneyFormat(d[2], 0) + (isLandOrBuilding?"\n" + (d[3]==0?"0":Common.MoneyFormat(d[3]/10000, 6)):"");
	    				rowArray[7] = Common.MoneyFormat(d[4], 0) + (isLandOrBuilding?"\n" + (d[5]==0?"0":Common.MoneyFormat(d[5]/10000, 6)):"");
	    				rowArray[8] = Common.MoneyFormat(d[6], 0) + (isLandOrBuilding?"\n" + (d[7]==0?"0":Common.MoneyFormat(d[7]/10000, 6)):"");
	    			}
	    			//土地合計使用
	    			if ("建物".equals(keys[0])) { 
	    				rowArray[5] = Common.MoneyFormat(d[0], 0) + (isLandOrBuilding?"\n" + (d[1]==0?"0":Common.MoneyFormat(d[1], 2)):"");
	    				rowArray[6] = Common.MoneyFormat(d[2], 0) + (isLandOrBuilding?"\n" + (d[3]==0?"0":Common.MoneyFormat(d[3], 2)):"");
	    				rowArray[7] = Common.MoneyFormat(d[4], 0) + (isLandOrBuilding?"\n" + (d[5]==0?"0":Common.MoneyFormat(d[5], 2)):"");
	    				rowArray[8] = Common.MoneyFormat(d[6], 0) + (isLandOrBuilding?"\n" + (d[7]==0?"0":Common.MoneyFormat(d[7], 2)):"");
	    			}
	    			if ("有價證劵".equals(keys[0])) { 
	    				rowArray[5] = Common.MoneyFormat(d[0], 1) + (isLandOrBuilding?"\n" + (d[1]==0?"0":Common.MoneyFormat(d[1], 2)):"");
	    				rowArray[6] = Common.MoneyFormat(d[2], 1) + (isLandOrBuilding?"\n" + (d[3]==0?"0":Common.MoneyFormat(d[3], 2)):"");
	    				rowArray[7] = Common.MoneyFormat(d[4], 1) + (isLandOrBuilding?"\n" + (d[5]==0?"0":Common.MoneyFormat(d[5], 2)):"");
	    				rowArray[8] = Common.MoneyFormat(d[6], 1) + (isLandOrBuilding?"\n" + (d[7]==0?"0":Common.MoneyFormat(d[7], 2)):"");
	    			}
    				rowArray[9] = Common.MoneyFormat(d[8], 0);
	    			rowArray[10] = "";
	    			rowArray[11] = Common.MoneyFormat(d[9], 0);
	    			rowArray[12] = Common.MoneyFormat(d[10], 0);
	    			rowArray[13] = dtl.getKey().equals("Z")?"Z":o.getKey();
	    			
	    			if ("Z".equals(dtl.getKey())) {
		    			totalAmount1 += d[8];
		    			totalAmount2 += d[9];
		    			totalAmount3 += d[10];
	    			}
	    			arrList.add(rowArray);
	    		}
	    	}
	    	
	    	// 合計處理
	    	if(outMap.size() > 0){
	    		String[] totArray = new String[cols.length];
	    		
	    		totArray[0] = "合計";
	    		totArray[9] = Common.MoneyFormat(totalAmount1, 0);
	    		totArray[10] = "";
	    		totArray[11] = Common.MoneyFormat(totalAmount2, 0);
	    		totArray[12] = Common.MoneyFormat(totalAmount3, 0);
	    		totArray[13] = "T";
	    		arrList.add(totArray);
	    	}
	    	outMap.clear();
	    	outMap = null;
	    	
	    	if(arrList.size() > 0){
		    	String[][] data = new String[0][0];
		        data = (String[][])arrList.toArray(data);
		        model = new javax.swing.table.DefaultTableModel();
		        model.setDataVector(data, cols);
		    }
	    }catch(Exception e){
	    	e.printStackTrace();
	    }finally{
	    	if(db != null){
	    		db.closeAll();
	    	}
	    }
	    
	    return model;
	}
	
	public String getGroupName1(String index){
		String r;
		if("1".equals(index)){
			r = "土地";
		}else if("111".equals(index)){
			r = "土地改良物";
		}else if("2".equals(index)){
			r = "建物";
		}else if("3".equals(index)){
			r = "動產";
		}else if("4".equals(index)){
			r = "動產";
		}else if("5".equals(index)){
			r = "動產";
		}else if("8".equals(index)){
			r = "權利";
		}else if("9".equals(index)){
			r = "有價證劵";
		}else{
			r = "";
		}
		
		return r;
	}
	
	public String getGroupName2(String index){
		String r;
		if("1".equals(index)){
			r = "土地小計";
		}else if("111".equals(index)){
			r = "土地改良物小計";
		}else if("2".equals(index)){
			r = "房屋建築及設備小計";
		}else if("3".equals(index)){
			r = "機械及設備小計";
		}else if("4".equals(index)){
			r = "交通運輸及設備小計";
		}else if("5".equals(index)){
			r = "雜項設備小計";
		}else if("8".equals(index)){
			r = "權利小計";
		}else if("9".equals(index)){
			r = "有價證劵小計";
		}else{
			r = "";
		}
		
		return r;
	}
	
	public String queryUNTGR_UNTGR011R(String sameField, String lastYearSameField) throws Exception {
		StringBuilder sb = new StringBuilder();
		sb.append(sameField).append(" a.oldamount as oldAmount, a.oldarea as oldArea, ").append(lastYearSameField)
		.append(" from UNTGR_UNTGR011R a where 1 = 1 ").append(getConditionSQL2(this.getQ_reportYear()));
		return sb.toString();
	}
	
	/**
	 * 上年度結存 from 初始檔
	 */
	private String queryUNTGR_UNTGR011RForNow(String nextyear, String sameField) throws Exception {
		StringBuilder sb = new StringBuilder();
		sb.append(sameField)
		  .append(" 0 as oldamount ,0 as oldarea ,0 as addamount ,0 as addarea ,0 as reduceamount ,0 as reducearea ")
		  .append(" ,isnull(a.oldamount, 0) as nowamount ,isnull(a.oldarea, 0) as nowarea ,isnull(a.oldbookvalue, 0) as nowbookvalue ,0 as nowaccumdepr ,0 as nownetvalue ")
		  .append(" from UNTGR_UNTGR011R a where 1 = 1 ").append(getConditionSQL2(nextyear));
		return sb.toString();
	}
	
	/**
	 * 取得指定年月的 累計折舊
	 * @param yyyymm
	 * @param samefields
	 * @return
	 */
	private String queryYearTotalAccumDepr(String yyyymm, String samefields) {
		StringBuilder sb = new StringBuilder();
		String joinCodition = " ON a.enterorg = b.enterorg AND a.ownership = b.ownership AND a.differencekind = b.differencekind AND a.propertyno = b.propertyno AND a.serialno = b.serialno";
		String joinTables[] = {" UNTRF_ATTACHMENT b "," UNTBU_BUILDING b "," UNTMP_MOVABLEDETAIL b " , " UNTRT_ADDPROOF b "};
		
		sb.append(" ( ");
		
		for(int i = 0 ; i < 4 ; i++ ) {
			sb.append(samefields)
			  .append(" 0 as oldAmount ,0 as oldArea ,0 as addAmount ,0 as addArea ,0 as reduceAmount ,0 as reduceArea ")
			  .append(" ,0 as nowAmount ,0 as nowArea ,0 as nowBookValue ,isnull(CASE a.deprunitcb WHEN 'Y' THEN a.scalednewaccumdepr ELSE a.newaccumdepr END, 0) as nowAccumDepr ,0 as nowNetValue ")
			  .append(" from UNTDP_MONTHDEPR a ");
			
			sb.append(" inner join ");
			sb.append(joinTables[i]);
			
			sb.append(joinCodition);
			
			sb.append(" where 1=1 and (b.datastate = '1' or (substring(b.reducedate, 1, 4) > ").append(Common.sqlChar(Datetime.changeTaiwan((getQ_reportYear()), "2"))).append(" and b.datastate = '2')) ");
			
			if("N".equals(getQ_isorganmanager())){
				if ("".equals(getQ_enterorgKind1())){
					sb.append(" and a.enterorg = ").append(Common.sqlChar(getQ_enterOrg()));
				} else{
					if ("1".equals(getQ_enterorgKind1()) && "120".equals(getQ_differenceKind()) && !TCGHCommon.getSYSCODEName("01", "ETO").equals(getQ_enterOrg())) {
						sb.append(" and (a.enterorg = ").append(Common.sqlChar(getQ_enterOrg())).append( " or a.enterorg in (select organid from SYSCA_ORGAN where organsuperior1=").append(Common.sqlChar(getQ_enterOrg())).append(" ))");
					} else if ("2".equals(getQ_enterorgKind1()) && "120".equals(getQ_differenceKind()) && TCGHCommon.getSYSCODEName("02", "ETO").equals(getQ_enterOrg())) {
						sb.append(" and (a.enterorg = ").append(Common.sqlChar(getQ_enterOrg())).append(" or a.enterorg in (select organid from SYSCA_ORGAN where organsuperior2=").append(Common.sqlChar(getQ_enterOrg())).append(" or organsuperior3=").append(Common.sqlChar(getQ_enterOrg())).append(" ))");
					} else if ("3".equals(getQ_enterorgKind1()) && "110".equals(getQ_differenceKind()) && TCGHCommon.getSYSCODEName("01", "ETO").equals(getQ_enterOrg())) {
						sb.append(" and (a.enterorg = ").append(Common.sqlChar(getQ_enterOrg())).append(" or a.enterorg in (select organid from SYSCA_ORGAN where organsuperior2=").append(Common.sqlChar(getQ_enterOrg())).append(" or organsuperior3=").append(Common.sqlChar(getQ_enterOrg())).append(" ))");
					}
				}
			}else if("Y".equals(getQ_isorganmanager())){
				if("120".equals(getQ_differenceKind())){
					sb.append(" and (a.enterorg = ").append(Common.sqlChar(getQ_enterOrg()))
					  .append(" or a.enterorg in (select organid from SYSCA_ORGAN where organsuperior2 = ").append(Common.sqlChar(getQ_enterOrg())).append(")")
					  .append(")");			
				}else if("110".equals(getQ_differenceKind())){
					if(CHECK_ENTERORG.equals(getQ_enterOrg())){
						sb.append(" and (a.enterorg = ").append(Common.sqlChar(getQ_enterOrg()))
						  .append(" or a.enterorg in (select organid from SYSCA_ORGAN where ismanager = 'Y'))");
					}else{
						sb.append(" and (a.enterorg = ").append(Common.sqlChar(getQ_enterOrg()))
						   .append(" or a.enterorg in (select organid from SYSCA_ORGAN where organsuperior1 = ").append(Common.sqlChar(getQ_enterOrg())).append(")")
						   .append(")");			
					}
				}
			}
			
			if(!"".equals(getQ_ownership())){
				sb.append(" and a.ownership = ").append(Common.sqlChar(getQ_ownership()));
			}
			if(!"".equals(getQ_differenceKind())){
				sb.append(" and a.differencekind = ").append(Common.sqlChar(getQ_differenceKind()));
			}
			if(!"".equals(getQ_valuable())){
				sb.append(" and a.valuable = ").append(Common.sqlChar(getQ_valuable()));
			}
			
			if(!"".equals(getQ_reportYear())){
				sb.append(" and a.deprym = ").append(Common.sqlChar(yyyymm));
			}
			if( i != 3) {
				sb.append(" union all ");
			}else {
				sb.append(" ) ");
			}
		}
		return sb.toString();
	}
	
	/**
	 * @param sb
	 * @param tableType
	 * @param yyymm
	 * @param nowYear
	 * @param sameField
	 * @param nowYearSameField
	 * @param lastYearSameField
	 * @param isNeed
	 * @deprecated 禁用備份檔
	 * @throws Exception
	 */
	public void getYearCloseSQL(StringBuilder sb, int tableType, String yyymm, boolean nowYear,
										String sameField, String nowYearSameField, String lastYearSameField, boolean isNeed) throws Exception {
		if(nowYear){
			switch(tableType){
			case 1:
				sb.append(isNeed?" union all (":"")
				  .append(sameField).append(nowYearSameField).append(" 1 as nowAmount, a.holdarea as nowArea, a.bookvalue as nowBookValue, 0 as nowAccumDepr, a.bookvalue as nowNetValue ")
				  .append(" from UNTLA_LAND" + yyymm + " a where 1 = 1 ").append(getConditionSQL(true, 0, true))
				  .append(isNeed?")":"");
				break;
			case 2:
				sb.append(isNeed?" union all (":"")
				  .append(sameField).append(nowYearSameField)
				  .append(" 1 as nowAmount, a.holdarea as nowArea, a.bookvalue as nowBookValue, a.accumdepr as nowAccumDepr, a.netvalue as nowNetValue ")
				  .append(" from UNTBU_BUILDING" + yyymm + " a where 1 = 1 ").append(getConditionSQL(true, 0, true))
				  .append(isNeed?")":"");
				break;
			case 3:
				sb.append(isNeed?" union all (":"")
				  .append(sameField).append(nowYearSameField)
				  .append(" 1 as nowAmount, 0 as nowArea, a.bookvalue as nowBookValue, a.accumdepr as nowAccumDepr, a.netvalue as nowNetValue ")
				  .append(" from UNTRF_ATTACHMENT" + yyymm + " a where 1 = 1 ").append(getConditionSQL(true, 0, true))
				  .append(isNeed?")":"");
				break;
			case 4:
				sb.append(isNeed?" union all (":"")
				  .append(sameField).append(nowYearSameField)
				  .append(" 1 as nowAmount, 0 as nowArea, a.bookvalue as nowBookValue, a.accumdepr as nowAccumDepr, a.netvalue as nowNetValue ")
				  .append(" from UNTMP_MOVABLEDETAIL" + yyymm + " a left join UNTMP_MOVABLE" + yyymm + " c ")
				  .append(" on c.enterorg = a.enterorg and c.ownership = a.ownership and c.differencekind = a.differencekind and c.propertyno = a.propertyno and c.lotno = a.lotno ")
				  .append(" where 1=1 ")
				  .append(getConditionSQL(true, 0, false))
				  .append(" and c.valuable = ").append(Common.sqlChar(getQ_valuable()))
				  .append(isNeed?")":"");
				break;
			case 5:
				sb.append(isNeed?" union all (":"")
				  .append(sameField).append(nowYearSameField)
				  .append(" a.bookamount as nowAmount, 0 as nowArea, a.bookvalue as nowBookValue, 0 as nowAccumDepr, a.bookvalue as nowNetValue ")
				  .append(" from UNTVP_ADDPROOF" + yyymm + " a where 1 = 1 ").append(getConditionSQL(true, 0, false))
				  .append(isNeed?")":"");
				break;
			case 6:
				sb.append(isNeed?" union all (":"")
				  .append(sameField).append(nowYearSameField)
				  .append(" 1 as nowAmount, 0 as nowArea, a.bookvalue as nowBookValue, 0 as nowAccumDepr, a.netvalue as nowNetValue ")
				  .append(" from UNTRT_ADDPROOF" + yyymm + " a where 1 = 1 ").append(getConditionSQL(true, 0, false))
				  .append(isNeed?")":"");
				break;
			}
		}else{
			switch(tableType){
			case 1:
				sb.append(isNeed?" union all (":"")
				  .append(sameField).append(" 1 as oldAmount, a.holdarea as oldArea, ").append(lastYearSameField)
				  .append(" from UNTLA_LAND" + yyymm + " a where 1 = 1 ").append(getConditionSQL(true, 0, true))
				  .append(isNeed?")":"");
				break;
			case 2:
				sb.append(isNeed?" union all (":"")
				  .append(sameField).append(" 1 as oldAmount, a.holdarea as oldArea, ").append(lastYearSameField)
				  .append(" from UNTBU_BUILDING" + yyymm + " a where 1 = 1 ").append(getConditionSQL(true, 0, true))
				  .append(isNeed?")":"");
				break;
			case 3:
				sb.append(isNeed?" union all (":"")
				  .append(sameField).append(" 1 as oldAmount, 0 as oldArea, ").append(lastYearSameField)
				  .append(" from UNTRF_ATTACHMENT" + yyymm + " a where 1 = 1 ").append(getConditionSQL(true, 0, true))
				  .append(isNeed?")":"");
				break;
			case 4:
				sb.append(isNeed?" union all (":"")
				  .append(sameField).append(" 1 as oldAmount, 0 as oldArea, ").append(lastYearSameField)
				  .append(" from UNTMP_MOVABLEDETAIL" + yyymm + " a left join UNTMP_MOVABLE" + yyymm + " c ")
				  .append(" on c.enterorg = a.enterorg and c.ownership = a.ownership and c.differencekind = a.differencekind and c.propertyno = a.propertyno and c.lotno = a.lotno ")
				  .append(" where 1=1 ")
				  .append(getConditionSQL(true, 0, false))
				  .append(" and c.valuable = ").append(Common.sqlChar(getQ_valuable()))
				  .append(isNeed?")":"");
				break;
			case 5:
				sb.append(isNeed?" union all (":"")
				  .append(sameField).append(" a.bookamount as oldAmount, 0 as oldArea, ").append(lastYearSameField)
				  .append(" from UNTVP_ADDPROOF" + yyymm + " a where 1 = 1 ").append(getConditionSQL(true, 0, false))
				  .append(isNeed?")":"");
				break;
			case 6:
				sb.append(isNeed?" union all (":"")
				  .append(sameField).append(" 1 as oldAmount, 0 as oldArea, ").append(lastYearSameField)
				  .append(" from UNTRT_ADDPROOF" + yyymm + " a where 1 = 1 ").append(getConditionSQL(true, 0, false))
				  .append(isNeed?")":"");
				break;
			}
		}		
	}
	
	// 土地增加、減損
	public void getLandAddReduceSQL(StringBuilder sb, int landType, String addReduceSameField1, String addReduceSameField2, boolean isNeed) throws Exception {
		switch(landType){
		case 1:
			sb.append(isNeed?" union all (":"")
			  .append(addReduceSameField1)
			  .append(" 1 as addAmount, a.originalholdarea as addArea, 0 as reduceAmount, 0 as reduceArea, ")
			  .append(addReduceSameField2)
			  .append(" from UNTLA_LAND a where 1 = 1 ").append(getConditionSQL(false, landType, true))
			  .append(isNeed?")":"");
			break;
		case 2:
			sb.append(isNeed?" union all (":"")
			  .append(addReduceSameField1)
			  .append(" 0 as addAmount, Case when a.adjustholdarea>0 then a.adjustholdarea else 0 End as addArea, ")
			  .append(" 0 as reduceAmount, Case when a.adjustholdarea<0 then -a.adjustholdarea else 0 End as reduceArea, ")
			  .append(addReduceSameField2)
			  .append(" from UNTLA_ADJUSTDETAIL a where 1 = 1 ").append(getConditionSQL(false, landType, true))
			  .append(isNeed?")":"");
			break;
		case 3:
			sb.append(isNeed?" union all (":"")
			  .append(addReduceSameField1)
			  .append(" 0 as addAmount, 0 as addArea, 1 as reduceAmount, a.holdarea as reduceArea, ")
			  .append(addReduceSameField2)
			  .append(" from UNTLA_REDUCEDETAIL a where 1 = 1 ").append(getConditionSQL(false, landType, true))
			  .append(isNeed?")":"");
			break;
		}
	}
	
	// 土地改良增加、減損
	public void getAttachmentAddReduceSQL(StringBuilder sb, int attachmentType, String addReduceSameField1, String addReduceSameField2, boolean isNeed) throws Exception {
		switch(attachmentType){
		case 1:
			sb.append(isNeed?" union all (":"")
			  .append(addReduceSameField1)
			  .append(" 1 as addAmount, 0 as addArea, 0 as reduceAmount, 0 as reduceArea, ")
			  .append(addReduceSameField2)
			  .append(" from UNTRF_ATTACHMENT a where 1 = 1 ").append(getConditionSQL(false, attachmentType, true))
			  .append(isNeed?")":"");
			break;
		case 2:
			sb.append(isNeed?" union all (":"")
			  .append(addReduceSameField1)
			  .append(" 0 as addAmount, 0 as addArea, 0 as reduceAmount, 0 as reduceArea, ")
			  .append(addReduceSameField2)
			  .append(" from UNTRF_ADJUSTDETAIL a where 1 = 1 ").append(getConditionSQL(false, attachmentType, true))
			  .append(isNeed?")":"");
			break;
		case 3:
			sb.append(isNeed?" union all (":"")
			  .append(addReduceSameField1)
			  .append(" 0 as addAmount, 0 as addArea, 1 as reduceAmount, 0 as reduceArea, ")
			  .append(addReduceSameField2)
			  .append(" from UNTRF_REDUCEDETAIL a where 1 = 1 ").append(getConditionSQL(false, attachmentType, true))
			  .append(isNeed?")":"");
			break;
		}
	}
	
	// 建物改良增加、減損
	public void getBuildingAddReduceSQL(StringBuilder sb, int buildingType, String addReduceSameField1, String addReduceSameField2, boolean isNeed) throws Exception {
		switch(buildingType){
		case 1:
			sb.append(isNeed?" union all (":"")
			  .append(addReduceSameField1)
			  .append(" 1 as addAmount, a.originalholdarea as addArea, 0 as reduceAmount, 0 as reduceArea, ")
			  .append(addReduceSameField2)
			  .append(" from UNTBU_BUILDING a where 1 = 1 ").append(getConditionSQL(false, buildingType, true))
			  .append(isNeed?")":"");
			break;
		case 2:
			sb.append(isNeed?" union all (":"")
			  .append(addReduceSameField1)
			  .append(" 0 as addAmount, Case when a.adjustholdarea>0 then a.adjustholdarea else 0 End as addArea, ")
			  .append(" 0 as reduceAmount, Case when a.adjustholdarea<0 then -a.adjustholdarea else 0 End as reduceArea, ")
			  .append(addReduceSameField2)
			  .append(" from UNTBU_ADJUSTDETAIL a where 1 = 1 ").append(getConditionSQL(false, buildingType, true))
			  .append(isNeed?")":"");
			break;
		case 3:
			sb.append(isNeed?" union all (":"")
			  .append(addReduceSameField1)
			  .append(" 0 as addAmount, 0 as addArea, 1 as reduceAmount, a.holdarea as reduceArea, ")
			  .append(addReduceSameField2)
			  .append(" from UNTBU_REDUCEDETAIL a where 1 = 1 ").append(getConditionSQL(false, buildingType, true))
			  .append(isNeed?")":"");
			break;
		}
	}
	
	// 動產增加、減損
	public void getMovableAddReduceSQL(StringBuilder sb, int movableType, String addReduceSameField1, String addReduceSameField2, boolean isNeed) throws Exception {
		switch(movableType){
		case 1:
			sb.append(isNeed?" union all (":"")
			  .append(addReduceSameField1)
			  .append(" 1 as addAmount, 0 as addArea, 0 as reduceAmount, 0 as reduceArea, ")
			  .append(addReduceSameField2)
			  .append(" from UNTMP_MOVABLEDETAIL a left join UNTMP_MOVABLE c ")
			  .append(" on c.enterorg = a.enterorg and c.ownership = a.ownership and c.differencekind = a.differencekind and c.propertyno = a.propertyno and c.lotno = a.lotno ")
			  .append(" where 1 = 1 ").append(getConditionSQL(false, movableType, false))
			  .append(" and c.valuable = ").append(Common.sqlChar(getQ_valuable()))
			  .append(isNeed?")":"");
			break;
		case 2:
			sb.append(isNeed?" union all (":"")
			  .append(addReduceSameField1)
			  .append(" 0 as addAmount, 0 as addArea, 0 as reduceAmount, 0 as reduceArea, ")
			  .append(addReduceSameField2)
			  .append(" from UNTMP_ADJUSTDETAIL a where 1 = 1 ").append(getConditionSQL(false, movableType, true))
			  .append(isNeed?")":"");
			break;
		case 3:
			sb.append(isNeed?" union all (":"")
			  .append(addReduceSameField1)
			  .append(" 0 as addAmount, 0 as addArea, 1 as reduceAmount, 0 as reduceArea, ")
			  .append(addReduceSameField2)
			  .append(" from UNTMP_REDUCEDETAIL a where 1 = 1 ").append(getConditionSQL(false, movableType, true))
			  .append(isNeed?")":"");
			break;
		}
	}
	
	// 有價證劵增加、減損
	public void getVP_AddProofAddReduceSQL(StringBuilder sb, int addProofType, String addReduceSameField1, String addReduceSameField2, boolean isNeed) throws Exception {
		switch(addProofType){
		case 1:
			sb.append(isNeed?" union all (":"")
			  .append(addReduceSameField1)
			  .append(" a.originalamount as addAmount, 0 as addArea, 0 as reduceAmount, 0 as reduceArea, ")
			  .append(addReduceSameField2)
			  .append(" from UNTVP_ADDPROOF a where 1 = 1 ").append(getConditionSQL(false, addProofType, false))
			  .append(isNeed?")":"");
			break;
		case 2:
			sb.append(isNeed?" union all (":"")
			  .append(addReduceSameField1)
			  .append(" addbookamount as addAmount, 0 as addArea, reducebookamount as reduceAmount, 0 as reduceArea, ")
			  .append(addReduceSameField2)
			  .append(" from UNTVP_ADJUSTPROOF a where 1 = 1 ").append(getConditionSQL(false, addProofType, false))
			  .append(isNeed?")":"");
			break;
		case 3:
			sb.append(isNeed?" union all (":"")
			  .append(addReduceSameField1)
			  .append(" 0 as addAmount, 0 as addArea, a.bookamount as reduceAmount, 0 as reduceArea, ")
			  .append(addReduceSameField2)
			  .append(" from UNTVP_REDUCEPROOF a where 1 = 1 ").append(getConditionSQL(false, 2, false))
			  .append(isNeed?")":"");
			break;
		}
	}
	
	// 權利增加、減損
	public void getRT_AddProofAddReduceSQL(StringBuilder sb, int addProofType, String addReduceSameField1, String addReduceSameField2, boolean isNeed) throws Exception {
		switch(addProofType){
		case 1:
			sb.append(isNeed?" union all (":"")
			  .append(addReduceSameField1)
			  .append(" 1 as addAmount, 0 as addArea, 0 as reduceAmount, 0 as reduceArea, ")
			  .append(addReduceSameField2)
			  .append(" from UNTRT_ADDPROOF a where 1 = 1 ").append(getConditionSQL(false, addProofType, false))
			  .append(isNeed?")":"");
			break;
		case 2:
			sb.append(isNeed?" union all (":"")
			  .append(addReduceSameField1)
			  .append(" 0 as addAmount, 0 as addArea, 0 as reduceAmount, 0 as reduceArea, ")
			  .append(addReduceSameField2)
			  .append(" from UNTRT_ADJUSTPROOF a where 1 = 1 ").append(getConditionSQL(false, addProofType, false))
			  .append(isNeed?")":"");
			break;
		case 3:
			sb.append(isNeed?" union all (":"")
			  .append(addReduceSameField1)
			  .append(" 0 as addAmount, 0 as addArea, 1 as reduceAmount, 0 as reduceArea, ")
			  .append(addReduceSameField2)
			  .append(" from UNTRT_REDUCEPROOF a where 1 = 1 ").append(getConditionSQL(false, addProofType, false))
			  .append(isNeed?")":"");
			break;
		}
	}
}
