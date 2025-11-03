/*
*<br>程式目的：財產結存統計表 
*<br>程式代號：PUBGR016R
*<br>撰寫日期：
*<br>程式作者：
*<br>--------------------------------------------------------
*<br>修改作者　　修改日期　　　修改目的
*<br>--------------------------------------------------------
*/

package pub.gr;

import java.sql.ResultSet;
import java.util.*;
import javax.swing.table.DefaultTableModel;
import util.*;

public class PUBGR016R extends SuperBean {
		
	private final static String CHECK_ENTERORG = TCGHCommon.getSYSCODEName("01", "ETO");
	private final static String CHECK_ENTERORG_2 = TCGHCommon.getSYSCODEName("02", "ETO");
	
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
	private String q_valuable;							// 珍貴財產
	private String q_isorganmanager;					// 列印彙總表
	private String q_reportYear;						// 年度
	private String q_reportMM;							// 月份
	private String q_reportSeason;						// 季別
	private String q_reportType;
	
	private String q_enterDateS;						// 結存日期
	private String q_enterDateE;						// 結存日期
	private String q_verify;							// 入帳
	private String q_dataState;							// 資料狀態 
	
	String q_enterorgKind1; //及所屬含實中
	private String reportType;						// 報表類型
	
	public String getReportType() { return checkGet(reportType); }
	public void setReportType(String reportType) { this.reportType = checkGet(reportType); }
	public String getQ_enterorgKind1(){ return checkGet(q_enterorgKind1); }
	public void setQ_enterorgKind1(String s){ q_enterorgKind1=checkSet(s); }
	
	public String getQ_enterOrg() {		return checkGet(q_enterOrg);	}
	public void setQ_enterOrg(String q_enterOrg) {		this.q_enterOrg = checkSet(q_enterOrg);	}
	public String getQ_enterOrgName() {		return checkGet(q_enterOrgName);	}
	public void setQ_enterOrgName(String q_enterOrgName) {		this.q_enterOrgName = checkSet(q_enterOrgName);	}
	public String getQ_ownership() {		return checkGet(q_ownership);	}
	public void setQ_ownership(String q_ownership) {		this.q_ownership = checkSet(q_ownership);	}	
	public String getQ_differenceKind() {		return checkGet(q_differenceKind);	}
	public void setQ_differenceKind(String q_differenceKind) {		this.q_differenceKind = checkSet(q_differenceKind);	}
	public String getQ_valuable() {		return checkGet(q_valuable);	}
	public void setQ_valuable(String q_valuable) {		this.q_valuable = checkSet(q_valuable);	}
	public String getQ_isorganmanager() {		return checkGet(q_isorganmanager);	}
	public void setQ_isorganmanager(String q_isorganmanager) {		this.q_isorganmanager = checkSet(q_isorganmanager);	}
	public String getQ_reportYear() {		return checkGet(q_reportYear);	}
	public void setQ_reportYear(String q_reportYear) {		this.q_reportYear = checkSet(q_reportYear);	}
	public String getQ_reportMM() {		return checkGet(q_reportMM);	}
	public void setQ_reportMM(String q_reportMM) {		this.q_reportMM = checkSet(q_reportMM);	}
	public String getQ_reportSeason() {		return checkGet(q_reportSeason);	}
	public void setQ_reportSeason(String q_reportSeason) {		this.q_reportSeason = checkSet(q_reportSeason);	}
	public String getQ_reportType() {		return checkGet(q_reportType);	}
	public void setQ_reportType(String q_reportType) {		this.q_reportType = checkSet(q_reportType);	}
	
	public String getQ_enterDateS() {		return checkGet(q_enterDateS);	}
	public void setQ_enterDateS(String q_enterDateS) {		this.q_enterDateS = checkSet(q_enterDateS);	}
	public String getQ_enterDateE() {		return checkGet(q_enterDateE);	}
	public void setQ_enterDateE(String q_enterDateE) {		this.q_enterDateE = checkSet(q_enterDateE);	}
	public String getQ_verify() {		return checkGet(q_verify);	}
	public void setQ_verify(String q_verify) {		this.q_verify = checkSet(q_verify);	}
	public String getQ_dataState() {		return checkGet(q_dataState);	}
	public void setQ_dataState(String q_dataState) {		this.q_dataState = checkSet(q_dataState);	}
	
	public String getUNTMPSql(String tableYYYMM){
		StringBuffer sb = new StringBuffer();
		
		StringBuffer conditionUNTMP = new StringBuffer();
		if ("N".equals(getQ_isorganmanager())) {
			if("".equals(getQ_enterorgKind1())){
				conditionUNTMP.append(" and b.enterorg = ").append(Common.sqlChar(getQ_enterOrg()));
			}else{
				if ("1".equals(getQ_enterorgKind1()) && "120".equals(getQ_differenceKind()) && !TCGHCommon.getSYSCODEName("01", "ETO").equals(getQ_enterOrg())) {
					conditionUNTMP.append(" and (b.enterorg = ").append(Common.sqlChar(getQ_enterOrg())).append( " or b.enterorg in (select organid from SYSCA_ORGAN where organsuperior1=").append(Common.sqlChar(getQ_enterOrg())).append(" ))");
				} else if ("2".equals(getQ_enterorgKind1()) && "120".equals(getQ_differenceKind()) && TCGHCommon.getSYSCODEName("02", "ETO").equals(getQ_enterOrg())) {
					conditionUNTMP.append(" and (b.enterorg = ").append(Common.sqlChar(getQ_enterOrg())).append(" or b.enterorg in (select organid from SYSCA_ORGAN where organsuperior2=").append(Common.sqlChar(getQ_enterOrg())).append(" or organsuperior3=").append(Common.sqlChar(getQ_enterOrg())).append(" ))");
				} else if ("3".equals(getQ_enterorgKind1()) && "110".equals(getQ_differenceKind()) && TCGHCommon.getSYSCODEName("01", "ETO").equals(getQ_enterOrg())) {
					conditionUNTMP.append(" and (b.enterorg = ").append(Common.sqlChar(getQ_enterOrg())).append(" or b.enterorg in (select organid from SYSCA_ORGAN where organsuperior2=").append(Common.sqlChar(getQ_enterOrg())).append(" or organsuperior3=").append(Common.sqlChar(getQ_enterOrg())).append(" ))");
				}
			}
		} else if ("Y".equals(getQ_isorganmanager()) && "110".equals(getQ_differenceKind()) && !TCGHCommon.getSYSCODEName("01", "ETO").equals(getQ_enterOrg())) {
			conditionUNTMP.append(" and (b.enterorg=").append(Common.sqlChar(getQ_enterOrg())).append(" or b.enterorg in (select organid from SYSCA_ORGAN where organsuperior1=").append(Common.sqlChar(getQ_enterOrg())).append(" ))");
		} else if ("Y".equals(getQ_isorganmanager()) && "110".equals(getQ_differenceKind()) && TCGHCommon.getSYSCODEName("01", "ETO").equals(getQ_enterOrg())) {
			conditionUNTMP.append(" and b.enterorg in (select organid from SYSCA_ORGAN where ismanager='Y')");
		} else if ("Y".equals(getQ_isorganmanager()) && "120".equals(getQ_differenceKind())) {
			conditionUNTMP.append(" and (b.enterorg=").append(Common.sqlChar(getQ_enterOrg())).append(" or b.enterorg in (select organid from SYSCA_ORGAN where organsuperior2=").append(Common.sqlChar(getQ_enterOrg())).append(" ))");
		}
		
//		if("N".equals(getQ_isorganmanager())){
//			conditionUNTMP.append(" and b.enterorg = ").append(Common.sqlChar(getQ_enterOrg()));
//		}else if("Y".equals(getQ_isorganmanager())){
//			if("120".equals(getQ_differenceKind())){
//				conditionUNTMP.append(" and (b.enterorg = ").append(Common.sqlChar(getQ_enterOrg()))
//				   			  .append(" or b.enterorg in (select organid from SYSCA_ORGAN where organsuperior2 = ").append(Common.sqlChar(getQ_enterOrg())).append(")")
//				   			  .append(")");
//			}else if("110".equals(getQ_differenceKind())){
//				if(CHECK_ENTERORG.equals(getQ_enterOrg())){
//					conditionUNTMP.append(" and (b.enterorg = ").append(Common.sqlChar(getQ_enterOrg()))
//					   			  .append(" or b.enterorg in (select organid from SYSCA_ORGAN where ismanager = 'Y'))");
//				}else{
//					conditionUNTMP.append(" and (b.enterorg = ").append(Common.sqlChar(getQ_enterOrg()))
//					   		   	  .append(" or b.enterorg in (select organid from SYSCA_ORGAN where organsuperior1 = ").append(Common.sqlChar(getQ_enterOrg())).append(")")
//					   		   	  .append(")");
//				}
//			}
//		}
		
		if(!"".equals(getQ_ownership()))
			conditionUNTMP.append(" and b.ownership = ").append(Common.sqlChar(getQ_ownership()));
		if(!"".equals(getQ_differenceKind()))
			conditionUNTMP.append(" and b.differencekind = ").append(Common.sqlChar(getQ_differenceKind()));
		if(!"".equals(getQ_valuable()))
			conditionUNTMP.append(" and a.valuable = ").append(Common.sqlChar(getQ_valuable()));
		if(!"".equals(getQ_verify()))
			conditionUNTMP.append(" and b.verify = ").append(Common.sqlChar(getQ_verify()));
		if(!"".equals(getQ_dataState()))
			conditionUNTMP.append(" and b.datastate = ").append(Common.sqlChar(getQ_dataState()));
		
		if("110".equals(getQ_differenceKind())){
			sb.append(" select b.enterorg, b.propertyno, 1 as bookamount, b.bookvalue")
			  .append(" from UNTMP_MOVABLE" + tableYYYMM + " a, UNTMP_MOVABLEDETAIL" + tableYYYMM + " b ")
			  .append(" where a.enterorg = b.enterorg and a.ownership = b.ownership and a.differencekind = b.differencekind and a.lotno = b.lotno ")
			  .append(" and (SUBSTRING(b.propertyno,1,1) = '4' or SUBSTRING(b.propertyno,1,1) = '3' or SUBSTRING(b.propertyno,1,1) = '5') ")
			  .append(conditionUNTMP.toString());
		}else{
			sb.append(" select b.enterorg, b.propertyno, 1 as bookamount, b.bookvalue")
			  .append(" from UNTMP_MOVABLE" + tableYYYMM + " a, UNTMP_MOVABLEDETAIL" + tableYYYMM + " b ")
			  .append(" where a.enterorg = b.enterorg and a.ownership = b.ownership and a.differencekind = b.differencekind and a.lotno = b.lotno ")
			  .append(" and (SUBSTRING(b.propertyno,1,1) = '4' or SUBSTRING(b.propertyno,1,1) = '3' or SUBSTRING(b.propertyno,1,1) = '5') ")
			  .append(conditionUNTMP.toString());
		}
		
		return sb.toString();
	}
	
	public String getQuerySQL2(boolean isValuable){
		StringBuffer conditionSb = new StringBuffer();
		if("N".equals(getQ_isorganmanager())){
			if("".equals(getQ_enterorgKind1())){
				conditionSb.append(" and enterorg = ").append(Common.sqlChar(getQ_enterOrg()));
			} else{
				if ("1".equals(getQ_enterorgKind1()) && "120".equals(getQ_differenceKind()) && !TCGHCommon.getSYSCODEName("01", "ETO").equals(getQ_enterOrg())) {
					conditionSb.append(" and (enterorg = ").append(Common.sqlChar(getQ_enterOrg())).append( " or enterorg in (select organid from SYSCA_ORGAN where organsuperior1=").append(Common.sqlChar(getQ_enterOrg())).append(" ))");
				} else if ("2".equals(getQ_enterorgKind1()) && "120".equals(getQ_differenceKind()) && TCGHCommon.getSYSCODEName("02", "ETO").equals(getQ_enterOrg())) {
					conditionSb.append(" and (enterorg = ").append(Common.sqlChar(getQ_enterOrg())).append(" or enterorg in (select organid from SYSCA_ORGAN where organsuperior2=").append(Common.sqlChar(getQ_enterOrg())).append(" or organsuperior3=").append(Common.sqlChar(getQ_enterOrg())).append(" ))");
				} else if ("3".equals(getQ_enterorgKind1()) && "110".equals(getQ_differenceKind()) && TCGHCommon.getSYSCODEName("01", "ETO").equals(getQ_enterOrg())) {
					conditionSb.append(" and (enterorg = ").append(Common.sqlChar(getQ_enterOrg())).append(" or enterorg in (select organid from SYSCA_ORGAN where organsuperior2=").append(Common.sqlChar(getQ_enterOrg())).append(" or organsuperior3=").append(Common.sqlChar(getQ_enterOrg())).append(" ))");
				}
			}
		}else if("Y".equals(getQ_isorganmanager())){
			if("120".equals(getQ_differenceKind())){
				if(CHECK_ENTERORG_2.equals(getQ_enterOrg())){
					conditionSb.append(" and (enterorg = ").append(Common.sqlChar(getQ_enterOrg()))
					   .append(" or enterorg in (select organid from SYSCA_ORGAN where organsuperior2= " + Common.sqlChar(TCGHCommon.getSYSCODEName("02", "ETO")) + " or organsuperior3 = " + Common.sqlChar(TCGHCommon.getSYSCODEName("02", "ETO")) + "))");
				}else{
					conditionSb.append(" and (enterorg = ").append(Common.sqlChar(getQ_enterOrg()))
					   .append(" or enterorg in (select organid from SYSCA_ORGAN where organsuperior2 = ").append(Common.sqlChar(getQ_enterOrg())).append(")")
					   .append(")");
				}	
			}else if("110".equals(getQ_differenceKind())){
				if(CHECK_ENTERORG.equals(getQ_enterOrg())){
					conditionSb.append(" and (enterorg = ").append(Common.sqlChar(getQ_enterOrg()))
							   .append(" or enterorg in (select organid from SYSCA_ORGAN where ismanager = 'Y'))");
				}else{
					conditionSb.append(" and (enterorg = ").append(Common.sqlChar(getQ_enterOrg()))
							   .append(" or enterorg in (select organid from SYSCA_ORGAN where organsuperior1 = ").append(Common.sqlChar(getQ_enterOrg())).append(")")
							   .append(")");			
				}
			}
		}
		if(!"".equals(getQ_ownership()))
			conditionSb.append(" and ownership = ").append(Common.sqlChar(getQ_ownership()));
		if(!"".equals(getQ_differenceKind()))
			conditionSb.append(" and differencekind = ").append(Common.sqlChar(getQ_differenceKind()));
		if(!"".equals(getQ_verify()))
			conditionSb.append(" and verify = ").append(Common.sqlChar(getQ_verify()));
		if(!"".equals(getQ_dataState()))
			conditionSb.append(" and datastate = ").append(Common.sqlChar(getQ_dataState()));
		
		if(isValuable){
			if(!"".equals(getQ_valuable())){
				conditionSb.append(" and valuable = " + Common.sqlChar(getQ_valuable()));
			}
		}
		
		return conditionSb.toString();
	}
	
	public String getQuerySQL1(){
		StringBuffer conditionSb = new StringBuffer();
		if("N".equals(getQ_isorganmanager())){
			if("".equals(getQ_enterorgKind1())){
				conditionSb.append(" and enterorg = ").append(Common.sqlChar(getQ_enterOrg()));
			} else{
				if ("1".equals(getQ_enterorgKind1()) && "120".equals(getQ_differenceKind()) && !TCGHCommon.getSYSCODEName("01", "ETO").equals(getQ_enterOrg())) {
					conditionSb.append(" and (enterorg = ").append(Common.sqlChar(getQ_enterOrg())).append( " or enterorg in (select organid from SYSCA_ORGAN where organsuperior1=").append(Common.sqlChar(getQ_enterOrg())).append(" ))");
				} else if ("2".equals(getQ_enterorgKind1()) && "120".equals(getQ_differenceKind()) && TCGHCommon.getSYSCODEName("02", "ETO").equals(getQ_enterOrg())) {
					conditionSb.append(" and (enterorg = ").append(Common.sqlChar(getQ_enterOrg())).append(" or enterorg in (select organid from SYSCA_ORGAN where organsuperior2=").append(Common.sqlChar(getQ_enterOrg())).append(" or organsuperior3=").append(Common.sqlChar(getQ_enterOrg())).append(" ))");
				} else if ("3".equals(getQ_enterorgKind1()) && "110".equals(getQ_differenceKind()) && TCGHCommon.getSYSCODEName("01", "ETO").equals(getQ_enterOrg())) {
					conditionSb.append(" and (enterorg = ").append(Common.sqlChar(getQ_enterOrg())).append(" or enterorg in (select organid from SYSCA_ORGAN where organsuperior2=").append(Common.sqlChar(getQ_enterOrg())).append(" or organsuperior3=").append(Common.sqlChar(getQ_enterOrg())).append(" ))");
				}
			}
		}else if("Y".equals(getQ_isorganmanager())){
			if("120".equals(getQ_differenceKind())){
				if(CHECK_ENTERORG_2.equals(getQ_enterOrg())){
					conditionSb.append(" and (enterorg = ").append(Common.sqlChar(getQ_enterOrg()))
					   .append(" or enterorg in (select organid from SYSCA_ORGAN where organsuperior2= " + Common.sqlChar(TCGHCommon.getSYSCODEName("02", "ETO")) + " or organsuperior3 = " + Common.sqlChar(TCGHCommon.getSYSCODEName("02", "ETO")) + "))");
				}else{
					conditionSb.append(" and (enterorg = ").append(Common.sqlChar(getQ_enterOrg()))
					   .append(" or enterorg in (select organid from SYSCA_ORGAN where organsuperior2 = ").append(Common.sqlChar(getQ_enterOrg())).append(")")
					   .append(")");
				}			
			}else if("110".equals(getQ_differenceKind())){
				if(CHECK_ENTERORG.equals(getQ_enterOrg())){
					conditionSb.append(" and (enterorg = ").append(Common.sqlChar(getQ_enterOrg()))
							   .append(" or enterorg in (select organid from SYSCA_ORGAN where ismanager = 'Y'))");
				}else{
					conditionSb.append(" and (enterorg = ").append(Common.sqlChar(getQ_enterOrg()))
							   .append(" or enterorg in (select organid from SYSCA_ORGAN where organsuperior1 = ").append(Common.sqlChar(getQ_enterOrg())).append(")")
							   .append(")");			
				}
			}
		}
		if(!"".equals(getQ_ownership()))
			conditionSb.append(" and ownership = ").append(Common.sqlChar(getQ_ownership()));
		if(!"".equals(getQ_differenceKind()))
			conditionSb.append(" and differencekind = ").append(Common.sqlChar(getQ_differenceKind()));
		if(!"".equals(getQ_valuable()))
			conditionSb.append(" and valuable = " + Common.sqlChar(getQ_valuable()));
		return conditionSb.toString();
	}

	public DefaultTableModel getResultModel() throws Exception {
		DefaultTableModel model = new javax.swing.table.DefaultTableModel();
		Database db = null;
		
		try{
			java.util.List<String[]> arrList = new java.util.ArrayList<String[]>();
			String[] cols = new String[]{
										"F1", "F2", "F3", "F4", "F5", "F6", "F7", "F8", "F9", "F10", 
										"F11", "F12", "F13", "F14", "F15", "F16", "F17", "F18", "F19", "F20",
										"F21", "F22", "F23", "F24", "F25", "F26", "F27", "F28"
										};
			
			db = new Database();
			
			// 機關群組資料
			java.util.Map<String, double[]> enterOrgMap = new java.util.TreeMap<String, double[]>();  
			
				if(!"".equals(getQ_enterDateE())){
						// TABLE年度
						String tableYYYMM = "_" + getQ_enterDateE().substring(0, 5);
						String YYY = new Datetime().getMonthDay(getQ_enterDateE().substring(0, 5),1,"month").substring(0,3);
						String MM = new Datetime().getMonthDay(getQ_enterDateE().substring(0, 5),1,"month").substring(3,5);
						
						String sql2 = null;
						ResultSet rs2 = null;
						String sql1 = " select ";
						if(CHECK_ENTERORG.equals(getQ_enterOrg()) || CHECK_ENTERORG_2.equals(getQ_enterOrg())){
							sql1 += " (SELECT CASE WHEN cc.organsuperior1 IS NULL THEN organid ELSE organsuperior1 END from SYSCA_ORGAN cc WHERE cc.organid = a.enterorg),";
						} else {
							sql1 += " enterorg,";
						}
						sql1 += " propertytype, SUM(ISNULL(oldamount, 0)), SUM(ISNULL(oldarea, 0)), SUM(ISNULL(oldbvsubtotal, 0)) " +
						 " FROM UNTGR_UNTGR009R a " +
						 " where a.reportyear = " + Common.sqlChar(YYY) +
						 " and a.reportmonth = " + Common.sqlChar(MM);
						
						ResultSet rs1 = db.querySQL(sql1 + getQuerySQL1() + " group by enterorg,propertytype ");
						int count = 0;
						// 將查詢結果統計放進map key為機關
						while (rs1.next()) {
							count++;
							double[] d = enterOrgMap.get(Common.get(rs1.getString(1)));
							if(d == null){
								d = new double[27];
								enterOrgMap.put(Common.get(rs1.getString(1)), d);
							}
							if("10".equals(Common.get(rs1.getString(2)))){
								d[0] += rs1.getDouble(3);           //土地-筆數
								d[1] += rs1.getDouble(4)/10000;     //土地-面積
								d[2] += rs1.getDouble(5);           //土地-價值
							}else if("20".equals(Common.get(rs1.getString(2)))){
								d[3] += rs1.getDouble(3);           //土改-數量
								d[4] += rs1.getDouble(5);           //土改-價值
							}else if("31".equals(Common.get(rs1.getString(2)))){
								d[5] += rs1.getDouble(3);           //房屋-辦公房屋-棟數
								d[6] += rs1.getDouble(4);           //房屋-辦公房屋-面積
								d[10] += rs1.getDouble(5);          //房屋-價值
							}else if("32".equals(Common.get(rs1.getString(2)))){
								d[7] += rs1.getDouble(3);           //房屋-宿舍-棟數
								d[8] += rs1.getDouble(4);           //房屋-宿舍-面積
								d[10] += rs1.getDouble(5);          //房屋-價值
							}else if("33".equals(Common.get(rs1.getString(2)))){
								d[9] += rs1.getDouble(3);           //房屋-其他
								d[10] += rs1.getDouble(5);          //房屋-價值
							}else if("40".equals(Common.get(rs1.getString(2)))){
								d[11] += rs1.getDouble(3);          //機械設備-數量 
								d[12] += rs1.getDouble(5);          //機械設備-價值
							}else if("51".equals(Common.get(rs1.getString(2)))){
								d[13] += rs1.getDouble(3);          //交通-船
								d[17] += rs1.getDouble(5);          //交通-價值
							}else if("52".equals(Common.get(rs1.getString(2)))){
								d[14] += rs1.getDouble(3);          //交通-飛機
								d[17] += rs1.getDouble(5);          //交通-價值
							}else if("53".equals(Common.get(rs1.getString(2)))){
								d[15] += rs1.getDouble(3);          //交通-車
								d[17] += rs1.getDouble(5);          //交通-價值
							}else if("54".equals(Common.get(rs1.getString(2)))){
								d[16] += rs1.getDouble(3);          //交通-其他
								d[17] += rs1.getDouble(5);          //交通-價值
							}else if("61".equals(Common.get(rs1.getString(2)))){
								d[18] += rs1.getDouble(3);          //雜項-圖書
								d[21] += rs1.getDouble(5);          //雜項-價值
							}else if("62".equals(Common.get(rs1.getString(2)))){
								d[19] += rs1.getDouble(3);          //雜項-博物
								d[21] += rs1.getDouble(5);          //雜項-價值
							}else if("63".equals(Common.get(rs1.getString(2)))){
								d[20] += rs1.getDouble(3);          //雜項-其他
								d[21] += rs1.getDouble(5);          //雜項-價值
							}else if("70".equals(Common.get(rs1.getString(2)))){
								d[22] += rs1.getDouble(3);          //有價證券-數量
								d[23] += rs1.getDouble(5);          //有價證券-價值
							}else if("80".equals(Common.get(rs1.getString(2)))){
								d[24] += rs1.getDouble(3);          //權利-數量
								d[25] += rs1.getDouble(5);          //權利-價值
							}else if("T0".equals(Common.get(rs1.getString(2)))){
								d[26] += rs1.getDouble(5);          //總值
							}
						}
						sql1 = null;
						rs1.close();
						rs1 = null;
						if (count==0) {
							// 土地
							sql2 = " select enterorg, COUNT(*), SUM(ISNULL(holdarea, 0)/10000), SUM(ISNULL(bookvalue, 0)) " +
										 " from UNTLA_LAND" + tableYYYMM +
										 " where SUBSTRING(propertyno,1,1) = '1' and SUBSTRING(propertyno,1,3) <> '111' ";
							rs2 = db.querySQL(sql2 + getQuerySQL2(true) + " group by enterorg ");
							while(rs2.next()) {
								if("".equals(Common.get(rs2.getString(1)))){
									continue;
								}
								
								double[] d = enterOrgMap.get(Common.get(rs2.getString(1)));
								if(d == null){
									d = new double[27];
									enterOrgMap.put(Common.get(rs2.getString(1)), d);
								}
								d[0] += rs2.getDouble(2);
								d[1] += rs2.getDouble(3);
								d[2] += rs2.getDouble(4);
								
								d[26] += rs2.getDouble(4);
							}
							sql2 = null;
							rs2.close();
							rs2 = null;
							
							// 土地改良物
							if("110".equals(getQ_differenceKind())){
								sql2 = " select enterorg, COUNT(*), SUM(ISNULL(bookvalue, 0)) " +
									  " from UNTRF_ATTACHMENT" + tableYYYMM +
									  " where SUBSTRING(propertyno,1,3) = '111' "; 
							}else{
								sql2 = " select enterorg, COUNT(*), SUM(ISNULL(netvalue, 0)) " +
									  " from UNTRF_ATTACHMENT" + tableYYYMM +
									  " where SUBSTRING(propertyno,1,3) = '111' ";
							}
							rs2 = db.querySQL(sql2 + getQuerySQL2(true) + " group by enterorg ");
							while(rs2.next()){
								if("".equals(Common.get(rs2.getString(1)))){
									continue;
								}
								
								double[] d = enterOrgMap.get(Common.get(rs2.getString(1)));
								if(d == null){
									d = new double[27];
									enterOrgMap.put(Common.get(rs2.getString(1)), d);
								}
								d[3] += rs2.getDouble(2);
								d[4] += rs2.getDouble(3);
								
								d[26] += rs2.getDouble(3);
							}
							sql2 = null;
							rs2.close();
							rs2 = null;
							
							// 房屋建築
							if("110".equals(getQ_differenceKind())){
								sql2 = " select enterorg, propertyno, holdarea, bookvalue " +
									  " from UNTBU_BUILDING" + tableYYYMM +
									  " where (SUBSTRING(propertyno,1,7) = '2010201' or SUBSTRING(propertyno,1,5) = '20106' or SUBSTRING(propertyno,1,1) = '2') ";
							}else{
								sql2 = " select enterorg, propertyno, holdarea, netvalue " +
								  	  " from UNTBU_BUILDING" + tableYYYMM +
								  	  " where (SUBSTRING(propertyno,1,7) = '2010201' or SUBSTRING(propertyno,1,5) = '20106' or SUBSTRING(propertyno,1,1) = '2') ";
							}
							rs2 = db.querySQL(sql2 + getQuerySQL2(true));
							while(rs2.next()){
								if("".equals(Common.get(rs2.getString(1)))){
									continue;
								}
								if("".equals(Common.get(rs2.getString(2)))){
									continue;
								}
								
								double[] d = enterOrgMap.get(Common.get(rs2.getString(1)));
								if(d == null){
									d = new double[27];
									enterOrgMap.put(Common.get(rs2.getString(1)), d);
								}
								if(Common.get(rs2.getString(2)).startsWith("2010201")){
									d[5]++;
									d[6] += rs2.getDouble(3);							
								}else if(Common.get(rs2.getString(1)).startsWith("20106")){
									d[7]++;
									d[8] += rs2.getDouble(3);									
								}else{
									d[9]++;
								}
								d[10] += rs2.getDouble(4);
								
								d[26] += rs2.getDouble(4);
							}
							sql2 = null;
							rs2.close();
							rs2 = null;
							
							// 機械設備、交通運輸、雜項設備
							sql2 = getUNTMPSql(tableYYYMM);
							rs2 = db.querySQL(sql2);
							while(rs2.next()){
								if("".equals(Common.get(rs2.getString(1)))){
									continue;
								}
								if("".equals(Common.get(rs2.getString(2)))){
									continue;
								}
								
								double[] d = enterOrgMap.get(Common.get(rs2.getString(1)));
								if(d == null){
									d = new double[27];
									enterOrgMap.put(Common.get(rs2.getString(1)), d);
								}
								
								if(Common.get(rs2.getString(2)).startsWith("3")){
									d[11] += rs2.getDouble(3);
									d[12] += rs2.getDouble(4);
									
									d[26] += rs2.getDouble(4);
								}else if(Common.get(rs2.getString(2)).startsWith("5")){
									if(Common.get(rs2.getString(2)).startsWith("503")){
										d[18] += rs2.getDouble(3);
									}else if(Common.get(rs2.getString(2)).startsWith("504")){
										if("N".equals(getQ_valuable())){						// 若為一般，但有504開頭，則加總在其他
											d[20] += rs2.getDouble(3);
										}else{
											d[19] += rs2.getDouble(3);
										}
									}else{
										d[20] += rs2.getDouble(3);
									}
									d[21] += rs2.getDouble(4);
									
									d[26] += rs2.getDouble(4);
								}else{
									if(Common.get(rs2.getString(2)).startsWith("40201")){
										d[13] += rs2.getDouble(3);
									}else if(Common.get(rs2.getString(2)).startsWith("40302")){
										d[14] += rs2.getDouble(3);
									}else if(Common.get(rs2.getString(2)).startsWith("40104")){
										d[15] += rs2.getDouble(3);
									}else if(Common.get(rs2.getString(2)).startsWith("4010702")){
										if(!Common.get(rs2.getString(2)).startsWith("401070207") && !Common.get(rs2.getString(2)).startsWith("401070208") && !Common.get(rs2.getString(2)).startsWith("401070209")){
											d[15] += rs2.getDouble(3);
										}else{
											d[16] += rs2.getDouble(3);
										}
									}else if(Common.get(rs2.getString(2)).startsWith("4010701") || Common.get(rs2.getString(2)).startsWith("4010703") || 
											 Common.get(rs2.getString(2)).startsWith("4010704") || Common.get(rs2.getString(2)).startsWith("4010705") ){
										d[15] += rs2.getDouble(3);
									}else{
										d[16] += rs2.getDouble(3);
									}
									d[19] += rs2.getDouble(4);
									
									d[26] += rs2.getDouble(4);
								}
							}
							sql2 = null;
							rs2.close();
							rs2 = null;
							
							// 有價證劵
							if("N".equals(getQ_valuable())){
								sql2 = " select enterorg, SUM(ISNULL(bookamount, 0)), SUM(ISNULL(bookvalue, 0)) " +
								  	  " from UNTVP_ADDPROOF" + tableYYYMM +
								  	  " where SUBSTRING(propertyno,1,1) = '9' ";
								rs2 = db.querySQL(sql2 + getQuerySQL2(false) + " group by enterorg ");
								while(rs2.next()){
									if("".equals(Common.get(rs2.getString(1)))){
										continue;
									}
									
									double[] d = enterOrgMap.get(Common.get(rs2.getString(1)));
									if(d == null){
										d = new double[27];
										enterOrgMap.put(Common.get(rs2.getString(1)), d);
									}
									d[22] += rs2.getDouble(2);
									d[23] += rs2.getDouble(3);
									
									d[26] += rs2.getDouble(3);
								}
								sql2 = null;
								rs2.close();
								rs2 = null;
							}
							
							// 權利
							if("N".equals(getQ_valuable())){
								sql2 = " select enterorg, COUNT(*), SUM(ISNULL(bookvalue, 0)) " +
								  	  " from UNTRT_ADDPROOF" + tableYYYMM +
								  	  " where SUBSTRING(propertyno,1,1) = '8' ";	
								rs2 = db.querySQL(sql2 + getQuerySQL2(false) + " group by enterorg ");
								while(rs2.next()){
									if("".equals(Common.get(rs2.getString(1)))){
										continue;
									}
									
									double[] d = enterOrgMap.get(Common.get(rs2.getString(1)));
									if(d == null){
										d = new double[27];
										enterOrgMap.put(Common.get(rs2.getString(1)), d);
									}
									d[24] += rs2.getDouble(2);
									d[25] += rs2.getDouble(3);
									
									d[26] += rs2.getDouble(3);
								}
								sql2 = null;
								rs2.close();
								rs2 = null;
							}
						}

						// 輸出
						if(enterOrgMap.size() > 0){
							outPutArray(arrList, enterOrgMap, cols.length);
							
							enterOrgMap.clear();
						}
						enterOrgMap = null;
						
						if(arrList.size() > 0){
							Object[][] data = new Object[0][0];
							data = (Object[][])arrList.toArray(data);
							model = new javax.swing.table.DefaultTableModel();
							model.setDataVector(data, cols);
						}
				}
			}catch(Exception x){
				System.out.println("[TCGH]-[PUBGR016R]-[列印發生異常]");
				x.printStackTrace();
			}finally{
				if(db != null){
					db.closeAll();
				}
			}
		
		return model;
	}

	public void outPutArray(java.util.List<String[]> arrList, java.util.Map<String, double[]> map, int len) throws Exception {
		if(arrList == null){
			arrList = new java.util.ArrayList<String[]>();
		}
		if(map == null){
			map = new java.util.HashMap<String, double[]>();
		}
		
		if(map.size() == 0){
			return;
		}
		java.util.Map<String, double[]> maptmp = new java.util.HashMap<String, double[]>();

		// 機關方式區分
//		int fieldType = 0;
//		if("120".equals(getQ_differenceKind())){
//			fieldType = 1;
//		}else{
//			if("Y".equals(getQ_isorganmanager())){
//				if(CHECK_ENTERORG.equals(getQ_enterOrg())){
//					fieldType = 4;
//				}else{
//					fieldType = 3;
//				}
//			}else{
//				fieldType = 0;
//			}
//		}
		
//		if(fieldType == 3){
			double[] tmp = new double[27];
			for(Map.Entry<String, double[]> dtl : map.entrySet()){
				tmp = new double[27];
				for(int i=0; i<dtl.getValue().length; i++){
					tmp[i] += dtl.getValue()[i];
				}
				maptmp.put(dtl.getKey(), tmp);
			}
//			map.clear();
//			
//			map.put(getQ_enterOrg(), tmp);
//		}else if(fieldType == 4){
//			StringBuffer sb = new StringBuffer();
//			
//			// 查出目前單位資料
//			java.util.Map<String, double[]> tmp = new java.util.HashMap<String, double[]>();
//			for(Map.Entry<String, double[]> dtl : map.entrySet()){
//				if(sb.toString().length() > 0){
//					sb.append(",");
//				}
//				sb.append(Common.sqlChar(dtl.getKey()));
//				
//				tmp.put(dtl.getKey(), dtl.getValue());
//			}
//			
//			// 進行各單位加總
//			java.util.Map<String, String> checkUpOrgan = TCGHCommon.getSYSCA_ORGAN_SUPERIOR1(null, "", true);
//			for(Map.Entry<String, double[]> dtl : tmp.entrySet()){
//				String superior1 = Common.get(checkUpOrgan.get(dtl.getKey()));
//				
//				// 判斷有無上層機關
//				if(!"".equals(superior1)){
//					double[] d = map.get(superior1);
//					if(d != null){
//						for(int i=0; i<d.length; i++){
//							d[i] += dtl.getValue()[i];
//						}
//					}
//					
//					map.remove(dtl.getKey());						// 加總後，移除本身資料
//				}else{
//					// nothing .....
//				}
//			}
//			
//			tmp.clear();
//			tmp = null;
//			checkUpOrgan.clear();
//			checkUpOrgan = null;
//		}
		
		java.util.Map<String, String> enterOrgNameMap = TCGHCommon.getSysca_Organ(null, true);
		
		// 想依照機關排序，先將所有HashMap裡的entry放入List
		List<Map.Entry<String, double[]>> list_OrgData =
			new ArrayList<Map.Entry<String, double[]>>(maptmp.entrySet());

		// 依機關排序
		Collections.sort(list_OrgData, new Comparator<Map.Entry<String, double[]>>(){
			public int compare(Map.Entry<String, double[]> entry1,
								Map.Entry<String, double[]> entry2){
				if(CHECK_ENTERORG.equals(getQ_enterOrg()) || CHECK_ENTERORG_2.equals(getQ_enterOrg())){
					return (Common.lpad(entry1.getKey(), 10, "A").compareTo(Common.lpad(entry2.getKey(), 10, "A")));
				} else {
					return (Common.lpad(entry2.getKey(), 10, "A").compareTo(Common.lpad(entry1.getKey(), 10, "A")));
				}
				
			}
		});
		
		if(CHECK_ENTERORG.equals(getQ_enterOrg())){
			enterOrgNameMap.put(TCGHCommon.getSYSCODEName("02", "ETO"), enterOrgNameMap.get(TCGHCommon.getSYSCODEName("02", "ETO")) + "及所屬");
			enterOrgNameMap.put(TCGHCommon.getSYSCODEName("03", "ETO"), enterOrgNameMap.get(TCGHCommon.getSYSCODEName("03", "ETO")) + "及所屬");
			enterOrgNameMap.put(TCGHCommon.getSYSCODEName("04", "ETO"), enterOrgNameMap.get(TCGHCommon.getSYSCODEName("04", "ETO")) + "及所屬");
		}else if(CHECK_ENTERORG_2.equals(getQ_enterOrg()) && "Y".equals(getQ_isorganmanager())){
			enterOrgNameMap.put(TCGHCommon.getSYSCODEName("02", "ETO"), enterOrgNameMap.get(TCGHCommon.getSYSCODEName("02", "ETO")) + "(含竹科實中)");
			enterOrgNameMap.put(TCGHCommon.getSYSCODEName("03", "ETO"), enterOrgNameMap.get(TCGHCommon.getSYSCODEName("03", "ETO")) + "(含中科實中)");
			enterOrgNameMap.put(TCGHCommon.getSYSCODEName("04", "ETO"), enterOrgNameMap.get(TCGHCommon.getSYSCODEName("04", "ETO")) + "(含南科實中)");
		}
		
		
		double[] total = new double[27];
		for(Map.Entry<String, double[]> o : list_OrgData){
			String[] rowArray = new String[len];
			
			rowArray[0] = Common.get(enterOrgNameMap.get(o.getKey())).equals("")?o.getKey():Common.get(enterOrgNameMap.get(o.getKey()));
			for(int i=1; i<rowArray.length; i++){
				int _index = i - 1;
				double val = o.getValue()[_index];
				total[_index] = total[_index] + val;
				if(i == 2){
					rowArray[i] = val == 0 ? "0" : Common.MoneyFormat(val, 6);
				}else if(i == 7 || i == 9){
					rowArray[i] = val == 0 ? "0" : Common.MoneyFormat(val, 2);
				}else{
					if(i == 4 || i == 12 || i == 23 || i == 25  ){
						rowArray[i] = String.valueOf(val);
					}else{
						rowArray[i] = Common.MoneyFormat(val, 0);
					}
				}
			}
			arrList.add(rowArray);
		}
		String[] rowArray = new String[len];
		rowArray[0] = "　　　總計";
		for(int i = 1; i < rowArray.length; i++){
			double val = total[i - 1];
			if(i == 2){
				rowArray[i] = val == 0 ? "0" : Common.MoneyFormat(val, 6);
			}else if(i == 7 || i == 9){
				rowArray[i] = val == 0 ? "0" : Common.MoneyFormat(val, 2);
			}else{
				if(i == 4 || i == 12 || i == 23 || i == 25  ){
					rowArray[i] = String.valueOf(val);
				}else{
					rowArray[i] = Common.MoneyFormat(val, 0);
				}
			}
		}
		arrList.add(rowArray);
		enterOrgNameMap.clear();
		enterOrgNameMap = null;
	}
	
}
