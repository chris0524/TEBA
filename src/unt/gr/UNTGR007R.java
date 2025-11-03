/*
*<br>程式目的：國有財產增減表 
*<br>程式代號：UNTGR007R
*<br>撰寫日期：0950314
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

public class UNTGR007R extends SuperBean{
	
	String q_enterOrg;
	String q_enterOrgName;
	String userName;
	String q_differenceKind;
	String q_ownership;
	String q_propertyKind;
	String q_fundType;
	String q_fundTypeKind;
	String q_valuable;
	String q_enterDateS;
	String q_enterDateE;
	String q_reportType;
	String q_reportYear;
	String q_reportMonth;
	String q_periodType;
	String q_reportSeason;		
	String q_grass;
	String q_isorganmanager;
	String isorganmanagerT;
	String q_verify;
	String q_enterorgKind;
	String reportType;
	
	public String getReportType() { return checkGet(reportType); }
	public void setReportType(String reportType) { this.reportType = checkGet(reportType); }
	public String getIsorganmanagerT(){ return checkGet(isorganmanagerT); }
	public void setIsorganmanagerT(String s){ isorganmanagerT=checkSet(s); }	
	public String getQ_isorganmanager(){ return checkGet(q_isorganmanager); }
	public void setQ_isorganmanager(String s){ q_isorganmanager=checkSet(s); }
	public String getQ_grass(){ return checkGet(q_grass); }
	public void setQ_grass(String s){ q_grass=checkSet(s); }
	public String getQ_enterOrg(){ return checkGet(q_enterOrg); }
	public void setQ_enterOrg(String s){ q_enterOrg=checkSet(s); }
	public String getQ_enterOrgName(){ return checkGet(q_enterOrgName); }
	public void setQ_enterOrgName(String s){ q_enterOrgName=checkSet(s); }
	public String getUserName(){ return checkGet(userName); }
	public void setUserName(String s){ userName=checkSet(s); }
	public String getQ_differenceKind(){ return checkGet(q_differenceKind); }
	public void setQ_differenceKind(String s){ q_differenceKind=checkSet(s); }
	public String getQ_ownership(){ return checkGet(q_ownership); }
	public void setQ_ownership(String s){ q_ownership=checkSet(s); }
	public String getQ_propertyKind(){ return checkGet(q_propertyKind); }
	public void setQ_propertyKind(String s){ q_propertyKind=checkSet(s); }
	public String getQ_fundType(){ return checkGet(q_fundType); }
	public void setQ_fundTypeKind(String s){ q_fundTypeKind=checkSet(s); }
	public String getQ_fundTypeKind(){ return checkGet(q_fundTypeKind); }
	public void setQ_fundType(String s){ q_fundType=checkSet(s); }
	public String getQ_valuable(){ return checkGet(q_valuable); }
	public void setQ_valuable(String s){ q_valuable=checkSet(s); }
	public String getQ_enterDateS(){ return checkGet(q_enterDateS); }
	public void setQ_enterDateS(String s){ q_enterDateS=checkSet(s); }
	public String getQ_enterDateE(){ return checkGet(q_enterDateE); }
	public void setQ_enterDateE(String s){ q_enterDateE=checkSet(s); }
	public String getQ_reportType(){ return checkGet(q_reportType); }
	public void setQ_reportType(String s){ q_reportType=checkSet(s); }
	public String getQ_reportYear(){ return checkGet(q_reportYear); }
	public void setQ_reportYear(String s){ q_reportYear=checkSet(s); }
	public String getQ_reportMonth(){ return checkGet(q_reportMonth); }
	public void setQ_reportMonth(String s){ q_reportMonth=checkSet(s); }
	public String getQ_periodType(){ return checkGet(q_periodType); }
	public void setQ_periodType(String s){ q_periodType=checkSet(s); }
	public String getQ_reportSeason(){ return checkGet(q_reportSeason); }
	public void setQ_reportSeason(String s){ q_reportSeason=checkSet(s); }
	public String getQ_verify(){ return checkGet(q_verify); }
	public void setQ_verify(String s){ q_verify=checkSet(s); }
	public String getQ_enterorgKind() { return checkGet(q_enterorgKind); }
	public void setQ_enterorgKind(String q_enterorgKind) { this.q_enterorgKind = checkSet(q_enterorgKind); }

	String isOrganManager;
	String isAdminManager;
	String organID;    
	public String getOrganID() { return checkGet(organID); }
	public void setOrganID(String s) { organID = checkSet(s); }
	public String getIsOrganManager() { return checkGet(isOrganManager); }
	public void setIsOrganManager(String s) { isOrganManager = checkSet(s); }
	public String getIsAdminManager() { return checkGet(isAdminManager); }
	public void setIsAdminManager(String s) { isAdminManager = checkSet(s); }

	String q_propertyKind1;
	public String getQ_propertyKind1() { return checkGet(q_propertyKind1); }
	public void setQ_propertyKind1(String s) { q_propertyKind1 = checkSet(s); }
	
	String q_isPrintoldlotno;
	public String getQ_isPrintoldlotno() { return checkGet(q_isPrintoldlotno); }
	public void setQ_isPrintoldlotno(String s) { q_isPrintoldlotno = checkSet(s); }
	
	String fileName;
	public String getFileName() { return checkGet(fileName); }
	public void setFileName(String s) { fileName = checkSet(s); }
	
	private String p_differenceKind;
	public String getP_differenceKind() { return checkGet(p_differenceKind); }
	public void setP_differenceKind(String p_differenceKind) { this.p_differenceKind = checkSet(p_differenceKind); }
	
	public DefaultTableModel getResultModel() throws Exception {
		Map<String,String> differenceKindMap = TCGHCommon.getSysca_Code("DFK"); 					//財產區分別名稱
		
		DefaultTableModel model = new javax.swing.table.DefaultTableModel();
		Database db = new Database();
		try {
			
			String[] columns;
			columns = new String[] { 
					"DIFFERENCEKIND","CNT", "COLUMNS_1", "COLUMNS_2",
					"COLUMNS_3", "COLUMNS_4", "COLUMNS_5", "COLUMNS_6",
					"COLUMNS_7", "COLUMNS_8", "COLUMNS_9", "COLUMNS_10",
					"COLUMNS_11", "COLUMNS_12", "COLUMNS_13", "COLUMNS_14",
					"PROPERTYNAME","moneyofpropertytype","moneyofdetail","sumofall","sumofaccumdepr","add_sum","add_area","reduce_sum","reduce_area"};
			String execSQL = "DECLARE @reportYear AS NVARCHAR(3) SET @reportYear = " + Common.sqlChar(this.getQ_reportYear());
			if ("1".equals(getQ_propertyKind1())) {
				execSQL += " select * from (" + querySQL1() + ") aa order by aa.propertyno,aa.serialno,aa.enterdate ";//財產
			} else if ("2".equals(getQ_propertyKind1())) {
				execSQL += " select * from (" + querySQL2() + ") aa order by aa.propertyno,aa.serialno,aa.enterdate ";//物品
			}
			System.out.println(execSQL);
			ResultSet rs = db.getForwardStatement().executeQuery(execSQL);
			
			Vector<Object[]> rowData = new Vector<Object[]>();
			int totalCNT = 0;
			int i = 0;
			long moneyofpropertytype=0;
			long sumofdetail=0;
			long sumofpropertytype=0;
			long sumofreduce=0;
			while (rs.next()) {
				i++;
				Object[] data = new Object[25];
				String differenceKind = differenceKindMap.get(getQ_differenceKind());
				this.setP_differenceKind(differenceKind);
				data[0] = differenceKind;
				int currCNT = rs.getInt("cnt");
				totalCNT += currCNT;
				long reduce_bookvalue = rs.getLong("reduce_bookvalue");
				totalCNT += (int)reduce_bookvalue;
				data[1] = currCNT;
				String propertyno = Common.checkGet(rs.getString("propertyno"));
				String oldlotno = Common.checkGet(rs.getString("oldlotno"));
				if (propertyno.length() > 7) {
					if (Common.checkGet(rs.getString("serialno")).length() == 0 ) {
						if ("Y".equals(getQ_isPrintoldlotno())) {
							data[2] = propertyno.substring(0, 7) + "-" + propertyno.substring(7) + ("".equals(oldlotno)?"":"\n原批號:" +oldlotno);
						} else {
							data[2] = propertyno.substring(0, 7) + "-" + propertyno.substring(7);
						}
					} else {
						if ("Y".equals(getQ_isPrintoldlotno())) {
							data[2] = propertyno.substring(0, 7) + "-" + propertyno.substring(7) + "-"  + rs.getString("serialno") + ("".equals(oldlotno)?"":"\n原批號:" +oldlotno);
						} else {
							data[2] = propertyno.substring(0, 7) + "-" + propertyno.substring(7) + "-"  + rs.getString("serialno");
						}
					}
				} else {
					if (Common.checkGet(rs.getString("serialno")).length() == 0 ) {
						if ("Y".equals(getQ_isPrintoldlotno())) {
							data[2] = propertyno + ("".equals(oldlotno)?"":"\n原批號:" +oldlotno);
						} else {
							data[2] = propertyno;
						}
					} else {
						if ("Y".equals(getQ_isPrintoldlotno())) {
							data[2] = propertyno + "-" + rs.getString("serialno") + ("".equals(oldlotno)?"":"\n原批號:" +oldlotno);
						} else {
							data[2] = propertyno + "-" + rs.getString("serialno");
						}
					}
				}

				data[3] = Common.checkGet(rs.getString("propertyname"));
				data[4] = Common.checkGet(rs.getString("propertyunit"));
				data[5] = Common.checkGet(rs.getString("bookunit"));
				data[6] = Common.checkGet(rs.getString("add_amount"));
				data[7] = rs.getLong("add_bookvalue");
				data[8] = Common.checkGet(rs.getString("limityear"));
				data[9] = Common.MaskDate(Datetime.changeTaiwanYYMMDD(rs.getString("sourcedate"), "1"));
				data[10] = Common.MaskDate(Datetime.changeTaiwanYYMMDD(rs.getString("buydate"), "1"));
				data[11] = Common.checkGet(rs.getString("reduce_amount"));
				data[12] = rs.getLong("reduce_bookvalue");
				data[13] = Common.checkGet(rs.getString("reduce_cause"));
				data[14] = Common.checkGet(rs.getString("approveorg"));
				data[15] = Common.checkGet(rs.getString("notes"));
				data[16] = Common.checkGet(rs.getString("propertyTypeName"));
				
				if (data[2].equals("111") || data[2].equals("2")
						|| data[2].equals("3") || data[2].equals("4")
						|| data[2].equals("5") || data[2].equals("8")) {
					moneyofpropertytype = Long.valueOf(data[12].toString());
					sumofpropertytype += moneyofpropertytype;
					sumofdetail = 0;// 更換財產大類時 將實際減少(細項加總)歸零
				} else {
					sumofdetail += Long.valueOf(data[12].toString());// 實際減少(細項加總)累加
					sumofreduce += Long.valueOf(data[12].toString());//總計實際減少  (沒有歸零動作) 
				}
				data[17] = moneyofpropertytype;// 存放財產大類本期折舊
				data[18] = sumofdetail;// 存放實際減少(細項加總)
				data[19] = sumofreduce;
				data[20] = sumofpropertytype;
				data[21] = Common.checkGet(rs.getString("add_sum")); //add_sum 
				data[22] = Common.checkGet(rs.getString("add_area")); //add_area
				data[23] = Common.checkGet(rs.getString("reduce_sum")); //reduce_sum
				data[24] = Common.checkGet(rs.getString("reduce_area")); //reduce_area						
				rowData.addElement(data);
			}
			
			
			
			if (totalCNT == 0){
				rowData.clear();
			}
			
			Object[][] data = new Object[0][0];
			data = (Object[][]) rowData.toArray(data);
			model.setDataVector(data, columns);
		} catch (Exception x) {
			x.printStackTrace();
		} finally {
			db.closeAll();
		}
		return model;

	}

	// 查詢條件
	private String whereCon(String date,boolean valuable) {
		String whereCon = "" ;
		if (!"".equals(getQ_enterOrg())) {
			if ("Y".equals(Common.get(this.getQ_enterorgKind()))) {
				whereCon += " and d.enterorg in (" + Common.sqlChar(getQ_enterOrg()) + ", " + Common.sqlChar(TCGHCommon.getSYSCODEName("10", "ETO")) + ")";
			} else {
				whereCon+=" and d.enterorg = " + Common.sqlChar(getQ_enterOrg()) ;
			}
		} else {
			if (!getIsAdminManager().equalsIgnoreCase("Y")) {
				if ("Y".equals(getIsOrganManager())) {					
					whereCon += " and d.enterorg like '" + getOrganID().substring(0,5) + "%' ";
				} else {
					whereCon +=" and d.enterorg = " + Common.sqlChar(getOrganID()) ;
				}
			} 
		}
		if (!"".equals(Common.get(getQ_ownership())))	//權屬
			whereCon += " and d.ownership = " + util.Common.sqlChar(getQ_ownership());
		if (!"".equals(Common.get(getQ_differenceKind())))	//財產區分別
			whereCon += " and d.differencekind = " + util.Common.sqlChar(getQ_differenceKind());
		if (!"".equals(Common.get(getQ_enterDateS())))	//入帳日期起
			whereCon += " and d." + date + "  >= " + util.Common.sqlChar(Datetime.changeTaiwanYYMMDD(getQ_enterDateS(), "2"));
		if (!"".equals(Common.get(getQ_enterDateE())))	//入帳日期訖
			whereCon += " and d." + date + "  <= " + util.Common.sqlChar(Datetime.changeTaiwanYYMMDD(getQ_enterDateE(), "2"));
		whereCon += " and d.verify = " + util.Common.sqlChar(getQ_verify());
		if (valuable) {
			whereCon += " and d.valuable = " + util.Common.sqlChar(getQ_valuable());
		}
		 
		return whereCon;
	}
	// 查詢條件 沒有入帳日期
	private String whereCon_enterDate(String date,boolean valuable) {
		String whereCon = "" ;
		if (!"".equals(getQ_enterOrg())) {
			if ("Y".equals(Common.get(this.getQ_enterorgKind()))) {
				whereCon += " and d.enterorg in (" + Common.sqlChar(getQ_enterOrg()) + ", " + Common.sqlChar(TCGHCommon.getSYSCODEName("10", "ETO")) + ")";
			} else {
				whereCon+=" and d.enterorg = " + Common.sqlChar(getQ_enterOrg()) ;
			}
		} else {
			if (!getIsAdminManager().equalsIgnoreCase("Y")) {
				if ("Y".equals(getIsOrganManager())) {					
					whereCon += " and d.enterorg like '" + getOrganID().substring(0,5) + "%' ";
				} else {
					whereCon +=" and d.enterorg = " + Common.sqlChar(getOrganID()) ;
				}
			} 
		}
		if (!"".equals(Common.get(getQ_ownership())))	//權屬
			whereCon += " and d.ownership = " + util.Common.sqlChar(getQ_ownership());
		if (!"".equals(Common.get(getQ_differenceKind())))	//財產區分別
			whereCon += " and d.differencekind = " + util.Common.sqlChar(getQ_differenceKind());
		if (!"".equals(Common.get(getQ_enterDateS())))	//折舊年月起
			whereCon += " and d." + date + "  >= " + util.Common.sqlChar(Datetime.changeTaiwanYYMM(getQ_enterDateS(), "2"));
		if (!"".equals(Common.get(getQ_enterDateE())))	//折舊年月訖
			whereCon += " and d." + date + "  <= " + util.Common.sqlChar(Datetime.changeTaiwanYYMM(getQ_enterDateE(), "2"));
		whereCon += " and d.verify = " + util.Common.sqlChar(getQ_verify());
		if (valuable) {
			whereCon += " and d.valuable = " + util.Common.sqlChar(getQ_valuable());
		}
		 
		return whereCon;
	}
	// 財產查詢
	private String querySQL1() {
		String querySQL = 
				//" 土地增加 "
				" select "
				+ " 1 as cnt,  "
				+ " d.propertyno,  "
				+ " d.serialno,  "
				+ " ISNULL(c.propertyname,'') propertyname,  "
				+ " '平方公尺' as propertyunit,  "
				+ " cast(d.originalunit AS varchar) as bookunit, "
				+ " d.originalholdarea as add_amount,  "
				+ " d.originalbv as add_bookvalue, "
				+ " '' as limityear, "
				+ " d.sourcedate,  "
				+ " d.buydate, "
				+ " '0' as reduce_amount,  "
				+ " '0' as reduce_bookvalue, "
				+ " '' as reduce_cause,  "
				+ " d.sourcedoc as approveorg, "
				+ " m.proofyear + '-' + m.proofdoc + '-' + m.proofno as notes, "
				+ " '土地' propertyTypeName, "
				+ " d.enterdate as enterdate,  "
				+ " isnull(d.oldlotno,'') oldlotno, "
				+ " '1' as add_sum , "
				+ " d.originalholdarea as add_area, "
				+ " '0' as reduce_sum, "
				+ " '0' as reduce_area "
				+ " from "
				+ " UNTLA_ADDPROOF m "
				+ " inner join UNTLA_LAND d on "
				+ " m.enterorg = d.enterorg  "
				+ " and m.ownership = d.ownership  "
				+ " and m.caseno = d.caseno  "
				+ " and m.differencekind = d.differencekind  "
				+ " left outer join SYSPK_PROPERTYCODE c on  "
				+ " c.enterorg in (d.enterorg,'000000000A')  "
				+ " and d.propertyno = c.propertyno  "
				+ " where 1 = 1 " + whereCon("enterdate",true)
				+ " union all  "
				+ "  "
				//+ " --土地增減值 "
				+ " select "
				+ " 1 as cnt,  "
				+ " d.propertyno,  "
				+ " d.serialno,  "
				+ " ISNULL(c.propertyname,'') propertyname,  "
				+ " '平方公尺' as propertyunit,  "
				+ " '' as bookunit,  "
				+ " case when d.adjustholdarea > 0 then d.adjustholdarea else 0 end as add_amount, "
				+ " d.addbookvalue as add_bookvalue, "
				+ " '' as limityear, "
				+ " d.sourcedate,  "
				+ " d.buydate, "
				+ " case when d.adjustholdarea < 0 then ABS(d.adjustholdarea) else 0 end as reduce_amount, "
				+ " d.reducebookvalue as reduce_bookvalue, "
				+ " case when d.reducebookvalue > 0 then (case when isnull(co.codename,'') = '其他' then isnull(co1.codename,'') else co.codename end)else '' end as reduce_cause, "
				+ " isnull(co2.codename,'') + cast(SUBSTRING(m.approvedate,1,4) - 1911 as varchar) + SUBSTRING(m.approvedate,5,4) + m.approvedoc  as approveorg, "
				+ " m.proofyear + '-' + m.proofdoc + '-' + m.proofno as notes, "
				+ " '土地' propertyTypeName, "
				+ " d.adjustdate as enterdate, "
				+ " isnull(d.oldlotno,'') oldlotno, "
				+ " '0' as add_sum , "
				+ " case when d.adjustholdarea > 0 then d.adjustholdarea else 0 end as add_area, "
				+ " '0' as reduce_sum, "
				+ " '0' as reduce_area "
				+ " from "
				+ " UNTLA_ADJUSTPROOF m  "
				+ " inner join UNTLA_ADJUSTDETAIL d on "
				+ " m.enterorg = d.enterorg  "
				+ " and m.ownership = d.ownership  "
				+ " and m.caseno = d.caseno  "
				+ " and m.differencekind = d.differencekind  "
				+ " left outer join SYSPK_PROPERTYCODE c on  "
				+ " c.enterorg in (d.enterorg,'000000000A')  "
				+ " and d.propertyno = c.propertyno  "
				+ " left outer join SYSCA_CODE co on "
				+ " co.codekindid = 'AJC'  "
				+ " and d.cause = co.codeid  "
				+ " left outer join SYSCA_CODE co1 on  "
				+ " co1.codekindid = 'AJC' "
				+ " and d.cause1 = co1.codeid  "
				+ " left outer join SYSCA_CODE co2 on  "
				+ " co2.codekindid = 'APO' "
				+ " and m.approveorg = co2.codeid  "
				+ " where 1 = 1 " + whereCon("adjustdate",true)
				+ " union all  "
				+ "  "
				//+ " --土地減損 "
				+ " select "
				+ " 1 as cnt,  "
				+ " d.propertyno,  "
				+ " d.serialno,  "
				+ " ISNULL(c.propertyname,'') propertyname,  "
				+ " '平方公尺' as propertyunit,  "
				+ " cast(d.bookunit AS varchar) as bookunit, "
				+ " '0' as add_amount, "
				+ " '0' as add_bookvalue,  "
				+ " '' as limityear, "
				+ " d.sourcedate,  "
				+ " d.buydate, "
				+ " ABS(d.holdarea) as reduce_amount, "
				+ " d.bookvalue as reduce_bookvalue, "
				+ " case when isnull(co.codename,'') = '其他' then isnull(co1.codename,'') else co.codename end as reduce_cause, "
				+ " isnull(co2.codename,'') + cast(SUBSTRING(m.approvedate,1,4) - 1911 as varchar) + SUBSTRING(m.approvedate,5,4) + m.approvedoc  as approveorg, "
				+ " m.proofyear + '-' + m.proofdoc + '-' + m.proofno as notes, "
				+ " '土地' propertyTypeName, "
				+ " d.reducedate as enterdate, "
				+ " isnull(d.oldlotno,'') oldlotno, "
				+ " '0' as add_sum , "
				+ " '0' as add_area, "
				+ " '1' as reduce_sum, "
				+ " d.holdarea as reduce_area "
				+ " from "
				+ " UNTLA_REDUCEPROOF m  "
				+ " inner join UNTLA_REDUCEDETAIL d on "
				+ " m.enterorg = d.enterorg  "
				+ " and m.ownership = d.ownership  "
				+ " and m.caseno = d.caseno  "
				+ " and m.differencekind = d.differencekind  "
				+ " left outer join SYSPK_PROPERTYCODE c on  "
				+ " c.enterorg in (d.enterorg,'000000000A')  "
				+ " and d.propertyno = c.propertyno  "
				+ " left outer join SYSCA_CODE co on "
				+ " co.codekindid = 'CAA'  "
				+ " and d.cause = co.codeid  "
				+ " left outer join SYSCA_CODE co1 on  "
				+ " co1.codekindid = 'CAA' "
				+ " and d.cause1 = co1.codeid  "
				+ " left outer join SYSCA_CODE co2 on  "
				+ " co2.codekindid = 'APO' "
				+ " and m.approveorg = co2.codeid  "
				+ " where 1 = 1 " + whereCon("reducedate",true)
				+ " union all  "
				+ "  "
				//+ " --土地改良物增加 "
				+ " select "
				+ " 1 as cnt,  "
				+ " d.propertyno,  "
				+ " d.serialno,  "
				+ " ISNULL(c.propertyname,'') propertyname,  "
				+ " ISNULL(c.propertyunit,'') as propertyunit, "
				+ " cast(d.originalbv AS varchar)  as bookunit,  "
				+ " '1' as add_amount, "
				+ " d.originalbv - (isnull(d.originalaccumdepr1,0) + isnull(d.originalaccumdepr2,0)) as add_bookvalue, "
				+ " cast(case when isnull(d.otherlimityear,0) <> 0 then cast(d.otherlimityear/12 as int) else c.limityear end as varchar) as limityear,  "
				+ " d.sourcedate,  "
				+ " d.buydate, "
				+ " '0' as reduce_amount,  "
				+ " '0' as reduce_bookvalue, "
				+ " '' as reduce_cause,  "
				+ " d.sourcedoc as approveorg, "
				+ " m.proofyear + '-' + m.proofdoc + '-' + m.proofno as notes, "
				+ " '土地改良物' propertyTypeName,  "
				+ " d.enterdate as enterdate,  "
				+ " isnull(d.oldlotno,'') oldlotno, "
				+ " '1' as add_sum , "
				+ " '0' as add_area, "
				+ " '0' as reduce_sum, "
				+ " '0' as reduce_area "				
				+ " from "
				+ " UNTRF_ADDPROOF m "
				+ " inner join UNTRF_ATTACHMENT d on "
				+ " m.enterorg = d.enterorg  "
				+ " and m.ownership = d.ownership  "
				+ " and m.caseno = d.caseno  "
				+ " and m.differencekind = d.differencekind  "
				+ " left outer join SYSPK_PROPERTYCODE c on  "
				+ " c.enterorg in (d.enterorg,'000000000A')  "
				+ " and d.propertyno = c.propertyno  "
				+ " where 1 = 1 " + whereCon("enterdate",true)
				+ " union all  "
				+ "  "
				//+ " --土地改良物增減值 "
				+ " select "
				+ " 1 as cnt,  "
				+ " d.propertyno,  "
				+ " d.serialno,  "
				+ " ISNULL(c.propertyname,'') propertyname,  "
				+ " ISNULL(c.propertyunit,'') as propertyunit, "
				+ " '' as bookunit,  "
				+ " '0' as add_amount, "
				+ " case d.differencekind when '110' then (case when @reportYear >= 105 then d.addnetvalue else d.addbookvalue end) else d.addnetvalue end as add_bookvalue, "
				+ " cast(case when isnull(d.otherlimityear,0) <> 0 then cast(d.otherlimityear/12 as int) else c.limityear end as varchar) as limityear,  "
				+ " d.sourcedate,  "
				+ " d.buydate, "
				+ " '0' as reduce_amount,  "
				+ " case d.differencekind when '110' then (case when @reportYear >= 105 then d.reducenetvalue else d.reducebookvalue end) else d.reducenetvalue end as reduce_bookvalue, "
				+ " case when d.reducebookvalue > 0 then case when isnull(co.codename,'') = '其他' then isnull(co1.codename,'') else co.codename end else '' end as reduce_cause,  "
				+ " isnull(co2.codename,'') + cast(SUBSTRING(m.approvedate,1,4) - 1911 as varchar) + SUBSTRING(m.approvedate,5,4) + m.approvedoc  as approveorg, "
				+ " m.proofyear + '-' + m.proofdoc + '-' + m.proofno as notes, "
				+ " '土地改良物' propertyTypeName,  "
				+ " d.adjustdate as enterdate, "
				+ " isnull(d.oldlotno,'') oldlotno, "
				+ " '0' as add_sum , "
				+ " '0' as add_area, "
				+ " '0' as reduce_sum, "
				+ " '0' as reduce_area "				
				+ " from "
				+ " UNTRF_ADJUSTPROOF m  "
				+ " inner join UNTRF_ADJUSTDETAIL d on "
				+ " m.enterorg = d.enterorg  "
				+ " and m.ownership = d.ownership  "
				+ " and m.caseno = d.caseno  "
				+ " and m.differencekind = d.differencekind  "
				+ " left outer join SYSPK_PROPERTYCODE c on  "
				+ " c.enterorg in (d.enterorg,'000000000A')  "
				+ " and d.propertyno = c.propertyno  "
				+ " left outer join SYSCA_CODE co on "
				+ " co.codekindid = 'AJC'  "
				+ " and d.cause = co.codeid  "
				+ " left outer join SYSCA_CODE co1 on  "
				+ " co1.codekindid = 'AJC' "
				+ " and d.cause1 = co1.codeid  "
				+ " left outer join SYSCA_CODE co2 on  "
				+ " co2.codekindid = 'APO' "
				+ " and m.approveorg = co2.codeid  "
				+ " where 1 = 1 " + whereCon("adjustdate",true)
				+ " union all  "
				+ "  "
				//+ " --土地改良物減損 "
				+ " select "
				+ " 1 as cnt,  "
				+ " d.propertyno,  "
				+ " d.serialno,  "
				+ " ISNULL(c.propertyname,'') propertyname,  "
				+ " ISNULL(c.propertyunit,'') as propertyunit, "
				+ " '' as bookunit,  "
				+ " '0' as add_amount, "
				+ " '0' as add_bookvalue,  "
				+ " cast(case when isnull(d.otherlimityear,0) <> 0 then cast(d.otherlimityear/12 as int) else c.limityear end as varchar) as limityear,  "
				+ " d.sourcedate,  "
				+ " d.buydate, "
				+ " '1' as reduce_amount,  "
				+ " case d.differencekind when '110' then (case when @reportYear >= 105 then d.netvalue else d.bookvalue end) else d.netvalue end as reduce_bookvalue, "
				+ " case when isnull(co.codename,'') = '其他' then isnull(co1.codename,'') else co.codename end as reduce_cause, "
				+ " isnull(co2.codename,'') + cast(SUBSTRING(m.approvedate,1,4) - 1911 as varchar) + SUBSTRING(m.approvedate,5,4) + m.approvedoc  as approveorg, "
				+ " m.proofyear + '-' + m.proofdoc + '-' + m.proofno as notes, "
				+ " '土地改良物' propertyTypeName,  "
				+ " d.reducedate as enterdate, "
				+ " isnull(d.oldlotno,'') oldlotno, "
				+ " '0' as add_sum , "
				+ " '0' as add_area, "
				+ " '1' as reduce_sum, "
				+ " '0' as reduce_area "
				+ " from "
				+ " UNTRF_REDUCEPROOF m  "
				+ " inner join UNTRF_REDUCEDETAIL d on "
				+ " m.enterorg = d.enterorg  "
				+ " and m.ownership = d.ownership  "
				+ " and m.caseno = d.caseno  "
				+ " and m.differencekind = d.differencekind  "
				+ " left outer join SYSPK_PROPERTYCODE c on  "
				+ " c.enterorg in (d.enterorg,'000000000A')  "
				+ " and d.propertyno = c.propertyno  "
				+ " left outer join SYSCA_CODE co on "
				+ " co.codekindid = 'CAA'  "
				+ " and d.cause = co.codeid  "
				+ " left outer join SYSCA_CODE co1 on  "
				+ " co1.codekindid = 'CAA' "
				+ " and d.cause1 = co1.codeid  "
				+ " left outer join SYSCA_CODE co2 on  "
				+ " co2.codekindid = 'APO' "
				+ " and m.approveorg = co2.codeid  "
				+ " where 1 = 1 " + whereCon("reducedate",true)
				+ " union all  "
				+ "  "
				//+ " --土地改良物折舊 "
				+ " select "
				+ " 0 as cnt,  "
				+ " '111' as propertyno, "
				+ " '' as serialno,  "
				+ " '土地改良物折舊' as propertyname,  "
				+ " '' as propertyunit,  "
				+ " '' as bookunit,  "
				+ " '0' as add_amount, "
				+ " '0' as add_bookvalue,  "
				+ " '' as limityear, "
				+ " '' as sourcedate,  "
				+ " '' as buydate, "
				+ " '0' as reduce_amount,  "
				+ " isnull(SUM(case when deprunitcb = 'Y' then isnull(scaledmonthdepr,0) else ISNULL(monthdepr,0)  end),0) as reduce_bookvalue, "
				+ " '' as reduce_cause,  "
				+ " '' as approveorg,  "
				+ " '本期折舊' as notes, "
				+ " '土地改良物' propertyTypeName,  "
				+ " '99999999' as enterdate, "
				+ " '' oldlotno, "
				+ " '0' as add_sum , "
				+ " '0' as add_area, "
				+ " '0' as reduce_sum, "
				+ " '0' as reduce_area "				
				+ " from "
				+ " UNTDP_MONTHDEPR d  "
				+ " where  "
				+ " d.propertytype = '2' "
				+ whereCon_enterDate("deprym",true)
				+ " union all  "
				+ "  "
				//+ " --建物增加 "
				+ " select "
				+ " 1 as cnt,  "
				+ " d.propertyno,  "
				+ " d.serialno,  "
				+ " ISNULL(c.propertyname,'') propertyname,  "
				+ " ISNULL(c.propertyunit,'') as propertyunit, "
				+ " cast(d.originalbv AS varchar) as bookunit,  "
				// 如果為辦公房屋(2010101001A至20105030003) or 宿舍(20106010001至20106040001) 且 畫面.年度 >= 105  則為 d.originalHoldArea  否則為 '1'
				+ " case when ((d.propertyno between '2010101001A' and '20105030003' or d.propertyno='2010503002A' or d.propertyno between '20106010001' and '20106040001') and @reportYear >= 105) then d.originalholdarea else '1' end as add_amount, "
				+ " d.originalbv - (isnull(d.originalaccumdepr1,0) + isnull(d.originalaccumdepr2,0)) as add_bookvalue, "
				+ " cast(case when isnull(d.otherlimityear,0) <> 0 then cast(d.otherlimityear/12 as int) else c.limityear end as varchar) as limityear,  "
				+ " d.sourcedate,  "
				+ " d.buydate, "
				+ " '0' as reduce_amount,  "
				+ " '0' as reduce_bookvalue, "
				+ " '' as reduce_cause,  "
				+ " d.sourcedoc as approveorg, "
				+ " m.proofyear + '-' + m.proofdoc + '-' + m.proofno as notes, "
				+ " '建物' propertyTypeName, "
				+ " d.enterdate as enterdate,  "
				+ " isnull(d.oldlotno,'') oldlotno, "
				+ " '1' as add_sum , "
				+ " CASE WHEN ((d.propertyno BETWEEN '2010101001A'	AND '20105030003' OR d.propertyno BETWEEN '20106010001'	AND '20106040001') AND @reportYear >= 105) THEN d.originalholdarea ELSE '1' END as add_area, "
				+ " '0' as reduce_sum, "
				+ " '0' as reduce_area "
				+ " from "
				+ " UNTBU_ADDPROOF m "
				+ " inner join UNTBU_BUILDING d on "
				+ " m.enterorg = d.enterorg  "
				+ " and m.ownership = d.ownership  "
				+ " and m.caseno = d.caseno  "
				+ " and m.differencekind = d.differencekind  "
				+ " left outer join SYSPK_PROPERTYCODE c on  "
				+ " c.enterorg in (d.enterorg,'000000000A')  "
				+ " and d.propertyno = c.propertyno  "
				+ " where 1 = 1 " + whereCon("enterdate",true)
				+ " union all  "
				+ "  "
				//+ " --建物增減值 "
				+ " select "
				+ " 1 as cnt,  "
				+ " d.propertyno,  "
				+ " d.serialno,  "
				+ " ISNULL(c.propertyname,'') propertyname,  "
				+ " ISNULL(c.propertyunit,'') as propertyunit, "
				+ " cast(d.originalbv AS varchar) as bookunit,  "
				// 如果為辦公房屋(2010101001A至20105030003) or 宿舍(20106010001至20106040001) 且 畫面.年度 >= 105  則為 d.originalHoldArea  否則為 '1'
				+ " case when ((d.propertyno between '2010101001A' and '20105030003' or d.propertyno='2010503002A' or d.propertyno between '20106010001' and '20106040001') and @reportYear >= 105) then (case when d.adjustholdarea > 0 then d.adjustholdarea else '0' end) else '0' end as add_amount, "
				+ " case d.differencekind when '110' then (case when @reportYear >= 105 then d.addnetvalue else d.addbookvalue end) else d.addnetvalue end as add_bookvalue,  "
				+ " cast(case when isnull(d.otherlimityear,0) <> 0 then cast(d.otherlimityear/12 as int) else c.limityear end as varchar) as limityear,  "
				+ " d.sourcedate,  "
				+ " d.buydate, "
				+ " case when ((d.propertyno between '2010101001A' and '20105030003' or d.propertyno='2010503002A' or d.propertyno between '20106010001' and '20106040001') and @reportYear >= 105) then (case when d.adjustholdarea < 0 then ABS(d.adjustholdarea) else '0' end) else '0' end as reduce_amount, "
				+ " case d.differencekind when '110' then (case when @reportYear >= 105 then d.reducenetvalue else d.reducebookvalue end) else d.reducenetvalue end as reduce_bookvalue, "
				+ " case when d.reducebookvalue > 0 then case when isnull(co.codename,'') = '其他' then isnull(co1.codename,'') else co.codename end else '' end as reduce_cause,  "
				+ " isnull(co2.codename,'') + cast(SUBSTRING(m.approvedate,1,4) - 1911 as varchar) + SUBSTRING(m.approvedate,5,4) + m.approvedoc  as approveorg, "
				+ " isnull(m.proofyear + '-' + m.proofdoc + '-' + m.proofno, d.notes) as notes, "
				+ " '建物' propertyTypeName, "
				+ " d.adjustdate as enterdate, "
				+ " isnull(d.oldlotno,'') oldlotno, "
				+ " '0' as add_sum , "
				+ " case when d.adjustholdarea > 0 then d.adjustholdarea else 0 end as add_area, "
				+ " '0' as reduce_sum, "
				+ " '0' as reduce_area "				
				+ " from "
				+ " UNTBU_ADJUSTPROOF m  "
				+ " inner join UNTBU_ADJUSTDETAIL d on "
				+ " m.enterorg = d.enterorg  "
				+ " and m.ownership = d.ownership  "
				+ " and m.caseno = d.caseno  "
				+ " and m.differencekind = d.differencekind  "
				+ " left outer join SYSPK_PROPERTYCODE c on  "
				+ " c.enterorg in (d.enterorg,'000000000A')  "
				+ " and d.propertyno = c.propertyno  "
				+ " left outer join SYSCA_CODE co on "
				+ " co.codekindid = 'AJC'  "
				+ " and d.cause = co.codeid  "
				+ " left outer join SYSCA_CODE co1 on  "
				+ " co1.codekindid = 'AJC' "
				+ " and d.cause1 = co1.codeid  "
				+ " left outer join SYSCA_CODE co2 on  "
				+ " co2.codekindid = 'APO' "
				+ " and m.approveorg = co2.codeid  "
				+ " where 1 = 1 " + whereCon("adjustdate",true)
				+ " union all  "
				+ "  "
				//+ " --建物減損  "
				+ " select "
				+ " 1 as cnt,  "
				+ " d.propertyno,  "
				+ " d.serialno,  "
				+ " ISNULL(c.propertyname,'') propertyname,  "
				+ " ISNULL(c.propertyunit,'') as propertyunit, "
				+ " '' as bookunit,  "
				+ " '0' as add_amount, "
				+ " '0' as add_bookvalue,  "
				+ " cast(case when isnull(d.otherlimityear,0) <> 0 then cast(d.otherlimityear/12 as int) else c.limityear end as varchar) as limityear,  "
				+ " d.sourcedate,  "
				+ " d.buydate, "
				+ " case when ((d.propertyno between '2010101001A' and '20105030003' or d.propertyno='2010503002A' or d.propertyno between '20106010001' and '20106040001') and @reportYear >= 105) then d.holdarea else '1' end as reduce_amount, "
				+ " case d.differencekind when '110' then (case when @reportYear >= 105 then d.netvalue else d.bookvalue end) else d.netvalue end as reduce_bookvalue, "
				+ " case when isnull(co.codename,'') = '其他' then isnull(co1.codename,'') else co.codename end as reduce_cause, "
				+ " isnull(co2.codename,'') + cast(SUBSTRING(m.approvedate,1,4) - 1911 as varchar) + SUBSTRING(m.approvedate,5,4) + m.approvedoc  as approveorg, "
				+ " m.proofyear + '-' + m.proofdoc + '-' + m.proofno as notes, "
				+ " '建物' propertyTypeName, "
				+ " d.reducedate as enterdate, "
				+ " isnull(d.oldlotno,'') oldlotno, "
				+ " '0' as add_sum , "
				+ " '0' as add_area, "
				+ " '1' as reduce_sum, "
				+ " case when ((d.propertyno between '2010101001A' and '20105030003' or d.propertyno='2010503002A' or d.propertyno between '20106010001' and '20106040001') and @reportYear >= 105) then d.holdarea else '1' end as reduce_area "
				+ " from "
				+ " UNTBU_REDUCEPROOF m  "
				+ " inner join UNTBU_REDUCEDETAIL d on "
				+ " m.enterorg = d.enterorg  "
				+ " and m.ownership = d.ownership  "
				+ " and m.caseno = d.caseno  "
				+ " and m.differencekind = d.differencekind  "
				+ " left outer join SYSPK_PROPERTYCODE c on  "
				+ " c.enterorg in (d.enterorg,'000000000A')  "
				+ " and d.propertyno = c.propertyno  "
				+ " left outer join SYSCA_CODE co on "
				+ " co.codekindid = 'CAA'  "
				+ " and d.cause = co.codeid  "
				+ " left outer join SYSCA_CODE co1 on  "
				+ " co1.codekindid = 'CAA' "
				+ " and d.cause1 = co1.codeid  "
				+ " left outer join SYSCA_CODE co2 on  "
				+ " co2.codekindid = 'APO' "
				+ " and m.approveorg = co2.codeid  "
				+ " where 1 = 1 " + whereCon("reducedate",true)
				+ " union all  "
				+ "  "
				//+ " --建物折舊 "
				+ " select "
				+ " 0 as cnt,  "
				+ " '2' as propertyno, "
				+ " '' as serialno,  "
				+ " '房屋建築及設備折舊' as propertyname,  "
				+ " '' as propertyunit,  "
				+ " '' as bookunit,  "
				+ " '0' as add_amount, "
				+ " '0' as add_bookvalue,  "
				+ " '' as limityear, "
				+ " '' as sourcedate,  "
				+ " '' as buydate, "
				+ " '0' as reduce_amount,  "
				+ " isnull(SUM(case when deprunitcb = 'Y' then isnull(scaledmonthdepr,0) else ISNULL(monthdepr,0)  end),0) as reduce_bookvalue, "
				+ " '' as reduce_cause,  "
				+ " '' as approveorg,  "
				+ " '本期折舊' as notes, "
				+ " '建物' propertyTypeName, "
				+ " '99999999' as enterdate, "
				+ " '' oldlotno, "
				+ " '0' as add_sum , "
				+ " '0' as add_area, "
				+ " '0' as reduce_sum, "
				+ " '0' as reduce_area "				
				+ " from "
				+ " UNTDP_MONTHDEPR d  "
				+ " where  "
				+ " d.propertytype = '3' "
				+  whereCon_enterDate("deprym",true)
				+ " union all  "
				+ "  "
				//+ " --動產增加 "
				+ " select "
				+ " 1 as cnt,  "
				+ " d.propertyno,  "
				+ " case when d.serialnos = d.serialnoe then d.serialnos else d.serialnos + '-'  + d.serialnoe end as serialno,  "
				+ " ISNULL(c.propertyname,'') propertyname,  "
				+ " case when ISNULL(c.propertyunit,'') = '' then ISNULL(d.otherpropertyunit,'') else c.propertyunit end as propertyunit,  "
				+ " cast(d.originalunit as varchar) as bookunit, "
				+ " case when SUBSTRING(d.propertyno,1,3) = '503' then f.originalamount else e.detailCnt end as add_amount, "
				+ " d.originalbv - ((isnull(d.originalaccumdepr1,0) + isnull(d.originalaccumdepr2,0)) * e.detailCnt) as add_bookvalue, "
				+ " cast(case when isnull(d.otherlimityear,0) <> 0 then cast(d.otherlimityear/12 as int) else c.limityear end as varchar) as limityear,  "
				+ " d.sourcedate,  "
				+ " d.buydate, "
				+ " '0' as reduce_amount,  "
				+ " '0' as reduce_bookvalue, "
				+ " '' as reduce_cause,  "
				+ " d.sourcedoc as approveorg, "
				+ " m.proofyear + '-' + m.proofdoc + '-' + m.proofno as notes, "
				+ " case substring(d.propertyno,1,1) when '3' then '機械及設備' when '4' then '交通及運輸設備' when '5' then '雜項設備' end as propertyTypeName, "
				+ " d.enterdate as enterdate,  "
				+ " isnull(d.oldlotno,'') oldlotno, "
				+ " case when SUBSTRING(d.propertyno,1,3) = '503' then f.originalamount else e.detailCnt end as add_sum, "
				+ " '0' as add_area, "
				+ " '0' as reduce_sum, "
				+ " '0' as reduce_area "				
				+ " from "
				+ " UNTMP_ADDPROOF m "
				+ " inner join UNTMP_MOVABLE d on  "
				+ " m.enterorg = d.enterorg  "
				+ " and m.ownership = d.ownership  "
				+ " and m.caseno = d.caseno  "
				+ " and m.differencekind = d.differencekind  "
				+ " left outer join SYSPK_PROPERTYCODE c on  "
				+ " c.enterorg in (d.enterorg,'000000000A')  "
				+ " and d.propertyno = c.propertyno  "
				+ " OUTER APPLY ("
				+ "   select count(1) as detailCnt from UNTMP_MOVABLEDETAIL r where d.enterorg=r.enterorg and d.ownership=r.ownership and d.differencekind=r.differencekind and d.propertyno=r.propertyno and d.lotno=r.lotno "
				+ " ) as e"
				+ " OUTER APPLY ("
				+ "   SELECT r.originalamount FROM UNTMP_MOVABLEDETAIL r WHERE d.enterorg = r.enterorg AND d.ownership = r.ownership AND d.differencekind = r.differencekind AND d.propertyno = r.propertyno AND d.lotno = r.lotno AND SUBSTRING(d.propertyno,1,3) = '503' "
				+ " ) as f"
				+ " where 1 = 1 " + whereCon("enterdate",true)
				+ " union all  "
				+ "  "
				//+ " --動產增減值 "
				+ " select "
				+ " 1 as cnt,  "
				+ " d.propertyno,  "
				+ " d.serialno,  "
				+ " ISNULL(c.propertyname,'') propertyname,  "
				+ " case when ISNULL(c.propertyunit,'') = '' then ISNULL(d.otherpropertyunit,'') else c.propertyunit end as propertyunit,  "
				+ " '' as bookunit,  "
				+ " '0' as add_amount, "
				+ " case d.differencekind when '110' then (case when @reportYear >= 105 then d.addnetvalue else d.addbookvalue end) else d.addnetvalue end as add_bookvalue,  "
				+ " cast(case when isnull(d.otherlimityear,0) <> 0 then cast(d.otherlimityear/12 as int) else c.limityear end as varchar) as limityear,  "
				+ " d.sourcedate,  "
				+ " d.buydate, "
				+ " '0' as reduce_amount,  "
				+ " case d.differencekind when '110' then (case when @reportYear >= 105 then d.reducenetvalue else d.reducebookvalue end) else d.reducenetvalue end as reduce_bookvalue, "
				+ " case when d.reducebookvalue > 0 then case when isnull(co.codename,'') = '其他' then isnull(co1.codename,'') else co.codename end else '' end as reduce_cause,  "
				+ " isnull(co2.codename,'') + cast(SUBSTRING(m.approvedate,1,4) - 1911 as varchar) + SUBSTRING(m.approvedate,5,4) + m.approvedoc  as approveorg, "
				+ " m.proofyear + '-' + m.proofdoc + '-' + m.proofno as notes, "
				+ " case substring(d.propertyno,1,1) when '3' then '機械及設備' when '4' then '交通及運輸設備' when '5' then '雜項設備' end as propertyTypeName, "
				+ " d.adjustdate as enterdate, "
				+ " isnull(d.oldlotno,'') oldlotno, "
				+ " '0' as add_sum , "
				+ " '0' as add_area, "
				+ " '0' as reduce_sum, "
				+ " '0' as reduce_area "				
				+ " from "
				+ " UNTMP_ADJUSTPROOF m  "
				+ " inner join UNTMP_ADJUSTDETAIL d on "
				+ " m.enterorg = d.enterorg  "
				+ " and m.ownership = d.ownership  "
				+ " and m.caseno = d.caseno  "
				+ " and m.differencekind = d.differencekind  "
				+ " left outer join SYSPK_PROPERTYCODE c on  "
				+ " c.enterorg in (d.enterorg,'000000000A')  "
				+ " and d.propertyno = c.propertyno  "
				+ " left outer join SYSCA_CODE co on "
				+ " co.codekindid = 'AJC'  "
				+ " and d.cause = co.codeid  "
				+ " left outer join SYSCA_CODE co1 on  "
				+ " co1.codekindid = 'AJC' "
				+ " and d.cause1 = co1.codeid  "
				+ " left outer join SYSCA_CODE co2 on  "
				+ " co2.codekindid = 'APO' "
				+ " and m.approveorg = co2.codeid  "
				+ " where 1 = 1 " + whereCon("adjustdate",true)
				+ " union all  "
				+ "  "
				//+ " --動產減損 "
				+ " select "
				+ " 1 as cnt,  "
				+ " d.propertyno,  "
				+ " d.serialno,  "
				+ " ISNULL(c.propertyname,'') propertyname,  "
				+ " case when ISNULL(c.propertyunit,'') = '' then ISNULL(d.otherpropertyunit,'') else c.propertyunit end as propertyunit,  "
				+ " cast(d.adjustbookunit as varchar) as bookunit,  "
				+ " '0' as add_amount, "
				+ " '0' as add_bookvalue,  "
				+ " cast(case when isnull(d.otherlimityear,0) <> 0 then cast(d.otherlimityear/12 as int) else c.limityear end as varchar) as limityear,  "
				+ " d.sourcedate,  "
				+ " d.buydate, "
				+ " d.adjustbookamount as reduce_amount, "
				+ " case d.differencekind when '110' then (case when @reportYear >= 105 then d.adjustnetvalue else d.adjustbookvalue end) else d.adjustnetvalue end as reduce_bookvalue, "
				+ " case when isnull(co.codename,'') = '其他' then isnull(co1.codename,'') else co.codename end as reduce_cause, "
				+ " isnull(co2.codename,'') + cast(SUBSTRING(m.approvedate,1,4) - 1911 as varchar) + SUBSTRING(m.approvedate,5,4) + m.approvedoc  as approveorg, "
				+ " m.proofyear + '-' + m.proofdoc + '-' + m.proofno as notes, "
				+ " case substring(d.propertyno,1,1) when '3' then '機械及設備' when '4' then '交通及運輸設備' when '5' then '雜項設備' end as propertyTypeName, "
				+ " d.reducedate as enterdate, "
				+ " isnull(d.oldlotno,'') oldlotno, "
				+ " '0' as add_sum , "
				+ " '0' as add_area, "
				+ " d.adjustbookamount as reduce_sum, "
				+ " '0' as reduce_area "
				+ " from "
				+ " UNTMP_REDUCEPROOF m  "
				+ " inner join UNTMP_REDUCEDETAIL d on "
				+ " m.enterorg = d.enterorg  "
				+ " and m.ownership = d.ownership  "
				+ " and m.caseno = d.caseno  "
				+ " and m.differencekind = d.differencekind  "
				+ " left outer join SYSPK_PROPERTYCODE c on  "
				+ " c.enterorg in (d.enterorg,'000000000A')  "
				+ " and d.propertyno = c.propertyno  "
				+ " left outer join SYSCA_CODE co on "
				+ " co.codekindid = 'CAA'  "
				+ " and d.cause = co.codeid  "
				+ " left outer join SYSCA_CODE co1 on  "
				+ " co1.codekindid = 'CAA' "
				+ " and d.cause1 = co1.codeid  "
				+ " left outer join SYSCA_CODE co2 on  "
				+ " co2.codekindid = 'APO' "
				+ " and m.approveorg = co2.codeid  "
				+ " where 1 = 1 " + whereCon("reducedate",true)
				+ " union all  "
				+ "  "
				//+ " --動產折舊(機械及設備) "
				+ " select "
				+ " 0 as cnt,  "
				+ " '3' as propertyno, "
				+ " '' as serialno,  "
				+ " '機械及設備折舊' as propertyname,  "
				+ " '' as propertyunit,  "
				+ " '' as bookunit,  "
				+ " '0' as add_amount, "
				+ " '0' as add_bookvalue,  "
				+ " '' as limityear, "
				+ " '' as sourcedate,  "
				+ " '' as buydate, "
				+ " '0' as reduce_amount,  "
				+ " isnull(SUM(case when deprunitcb = 'Y' then isnull(scaledmonthdepr,0) else ISNULL(monthdepr,0)  end),0) as reduce_bookvalue, "
				+ " '' as reduce_cause,  "
				+ " '' as approveorg,  "
				+ " '本期折舊' as notes, "
				+ " '機械及設備' as propertyTypeName,  "
				+ " '99999999' as enterdate, "
				+ " '' oldlotno, "
				+ " '0' as add_sum , "
				+ " '0' as add_area, "
				+ " '0' as reduce_sum, "
				+ " '0' as reduce_area "				
				+ " from "
				+ " UNTDP_MONTHDEPR d  "
				+ " where  "
				+ " d.propertytype = '4' "
				+ whereCon_enterDate("deprym",true)
				+ " union all  "
				+ "  "
				//+ " --動產折舊(交通及運輸設備) "
				+ " select "
				+ " 0 as cnt,  "
				+ " '4' as propertyno, "
				+ " '' as serialno,  "
				+ " '交通及運輸設備折舊' as propertyname,  "
				+ " '' as propertyunit,  "
				+ " '' as bookunit,  "
				+ " '0' as add_amount, "
				+ " '0' as add_bookvalue,  "
				+ " '' as limityear, "
				+ " '' as sourcedate,  "
				+ " '' as buydate, "
				+ " '0' as reduce_amount,  "
				+ " isnull(SUM(case when deprunitcb = 'Y' then isnull(scaledmonthdepr,0) else ISNULL(monthdepr,0)  end),0) as reduce_bookvalue, "
				+ " '' as reduce_cause,  "
				+ " '' as approveorg,  "
				+ " '本期折舊' as notes, "
				+ " '交通及運輸設備' as propertyTypeName, "
				+ " '99999999' as enterdate, "
				+ " '' oldlotno, "
				+ " '0' as add_sum , "
				+ " '0' as add_area, "
				+ " '0' as reduce_sum, "
				+ " '0' as reduce_area "				
				+ " from "
				+ " UNTDP_MONTHDEPR d  "
				+ " where  "
				+ " d.propertytype = '5' "
				+ whereCon_enterDate("deprym",true)
				+ " union all  "
				+ "  "
				//+ " --動產折舊(雜項設備) "
				+ " select "
				+ " 0 as cnt,  "
				+ " '5' as propertyno, "
				+ " '' as serialno,  "
				+ " '雜項設備折舊' as propertyname,  "
				+ " '' as propertyunit,  "
				+ " '' as bookunit,  "
				+ " '0' as add_amount, "
				+ " '0' as add_bookvalue,  "
				+ " '' as limityear, "
				+ " '' as sourcedate,  "
				+ " '' as buydate, "
				+ " '0' as reduce_amount,  "
				+ " isnull(SUM(case when deprunitcb = 'Y' then isnull(scaledmonthdepr,0) else ISNULL(monthdepr,0)  end),0) as reduce_bookvalue, "
				+ " '' as reduce_cause,  "
				+ " '' as approveorg,  "
				+ " '本期折舊' as notes, "
				+ " '雜項設備' as propertyTypeName,  "
				+ " '99999999' as enterdate, "
				+ " '' oldlotno, "
				+ " '0' as add_sum , "
				+ " '0' as add_area, "
				+ " '0' as reduce_sum, "
				+ " '0' as reduce_area "				
				+ " from "
				+ " UNTDP_MONTHDEPR d  "
				+ " where  "
				+ " d.propertytype = '6' "
				+ whereCon_enterDate("deprym",true)
				+ " union all  "
				+ "  "
				//+ " --有價證券增加 "
				+ " select "
				+ " 1 as cnt,  "
				+ " d.propertyno,  "
				+ " d.serialno,  "
				+ " ISNULL(c.propertyname,'') propertyname,  "
				+ " ISNULL(c.propertyunit,'') as propertyunit, "
				+ " '' as bookunit,  "
				+ " d.originalamount as add_amount,  "
				+ " d.originalbv as add_bookvalue, "
				+ " '' as limityear, "
				+ " d.sourcedate,  "
				+ " d.buydate, "
				+ " '0' as reduce_amount,  "
				+ " '0' as reduce_bookvalue, "
				+ " '' as reduce_cause,  "
				+ " d.sourcedoc as approveorg, "
				+ " d.proofyear + '-' + d.proofdoc + '-' + d.proofno as notes, "
				+ " '有價證券' as propertyTypeName, "
				+ " d.enterdate as enterdate,  "
				+ " isnull(d.oldlotno,'') oldlotno, "
				+ " d.originalamount as add_sum , "
				+ " '0' as add_area, "
				+ " '0' as reduce_sum, "
				+ " '0' as reduce_area "				
				+ " from "
				+ " UNTVP_ADDPROOF d "
				+ " left outer join SYSPK_PROPERTYCODE c on  "
				+ " c.enterorg in (d.enterorg,'000000000A')  "
				+ " and d.propertyno = c.propertyno  "
				+ " where 1 = 1 " + whereCon("enterdate",false)
				+ " union all  "
				+ "  "
				//+ " --有價證券增減值 "
				+ " select "
				+ " 1 as cnt,  "
				+ " d.propertyno,  "
				+ " d.serialno,  "
				+ " ISNULL(c.propertyname,'') propertyname,  "
				+ " ISNULL(c.propertyunit,'') as propertyunit, "
				+ " '' as bookunit,  "
				+ " d.addbookamount as add_amount, "
				+ " d.addbookvalue as add_bookvalue, "
				+ " '' as limityear, "
				+ " d.sourcedate,  "
				+ " d.buydate, "
				+ " d.reducebookamount as reduce_amount, "
				+ " d.reducebookvalue reduce_bookvalue,  "
				+ " case when d.reducebookvalue > 0 then case when isnull(co.codename,'') = '其他' then isnull(co1.codename,'') else co.codename end else '' end as reduce_cause,  "
				+ " isnull(co2.codename,'') + cast(SUBSTRING(d.approvedate,1,4) - 1911 as varchar) + SUBSTRING(d.approvedate,5,4) + d.approvedoc  as approveorg, "
				+ " d.proofyear + '-' + d.proofdoc + '-' + d.proofno as notes, "
				+ " '有價證券' as propertyTypeName, "
				+ " d.adjustdate as enterdate, "
				+ " isnull(d.oldlotno,'') oldlotno, "
				+ " d.addbookamount as add_sum , "
				+ " '0' as add_area, "
				+ " d.reducebookamount as reduce_sum, "
				+ " '0' as reduce_area "				
				+ " from "
				+ " UNTVP_ADJUSTPROOF d  "
				+ " left outer join SYSPK_PROPERTYCODE c on  "
				+ " c.enterorg in (d.enterorg,'000000000A')  "
				+ " and d.propertyno = c.propertyno  "
				+ " left outer join SYSCA_CODE co on "
				+ " co.codekindid = 'AJC'  "
				+ " and d.cause = co.codeid  "
				+ " left outer join SYSCA_CODE co1 on  "
				+ " co1.codekindid = 'AJC' "
				+ " and d.cause1 = co1.codeid  "
				+ " left outer join SYSCA_CODE co2 on  "
				+ " co2.codekindid = 'APO' "
				+ " and d.approveorg = co2.codeid  "
				+ " where 1 = 1 " + whereCon("adjustdate",false)
				+ " union all  "
				+ "  "
				//+ " --有價證券減損 "
				+ " select "
				+ " 1 as cnt,  "
				+ " d.propertyno,  "
				+ " d.serialno,  "
				+ " ISNULL(c.propertyname,'') propertyname,  "
				+ " ISNULL(c.propertyunit,'') as propertyunit, "
				+ " '' as bookunit,  "
				+ " '0' as add_amount, "
				+ " '0' as add_bookvalue,  "
				+ " '' as limityear, "
				+ " d.sourcedate,  "
				+ " d.buydate, "
				+ " d.bookamount as reduce_amount, "
				+ " bookvalue as reduce_bookvalue, "
				+ " case when isnull(co.codename,'') = '其他' then isnull(co1.codename,'') else co.codename end as reduce_cause, "
				+ " isnull(co2.codename,'') + cast(SUBSTRING(d.approvedate,1,4) - 1911 as varchar) + SUBSTRING(d.approvedate,5,4) + d.approvedoc  as approveorg, "
				+ " d.proofyear + '-' + d.proofdoc + '-' + d.proofno as notes, "
				+ " '有價證券' as propertyTypeName, "
				+ " d.adjustdate as enterdate, "
				+ " isnull(d.oldlotno,'') oldlotno, "
				+ " '0' as add_sum , "
				+ " '0' as add_area, "
				+ " d.bookamount as reduce_sum, "
				+ " '0' as reduce_area "				
				+ " from "
				+ " UNTVP_REDUCEPROOF d  "
				+ " left outer join SYSPK_PROPERTYCODE c on  "
				+ " c.enterorg in (d.enterorg,'000000000A')  "
				+ " and d.propertyno = c.propertyno  "
				+ " left outer join SYSCA_CODE co on "
				+ " co.codekindid = 'CAA'  "
				+ " and d.cause = co.codeid  "
				+ " left outer join SYSCA_CODE co1 on  "
				+ " co1.codekindid = 'CAA' "
				+ " and d.cause1 = co1.codeid  "
				+ " left outer join SYSCA_CODE co2 on  "
				+ " co2.codekindid = 'APO' "
				+ " and d.approveorg = co2.codeid  "
				+ " where 1 = 1 " + whereCon("adjustdate",false)
				+ " union all  "
				+ "  "
				//+ " --權利增加 "
				+ " select "
				+ " 1 as cnt,  "
				+ " d.propertyno,  "
				+ " d.serialno,  "
				+ " ISNULL(c.propertyname,'') propertyname,  "
				+ " ISNULL(c.propertyunit,'') as propertyunit, "
				+ " '' as bookunit,  "
				+ " '1' as add_amount, "
				+ " d.originalbv - (isnull(d.originalaccumdepr1,0) + isnull(d.originalaccumdepr2,0)) as add_bookvalue, "
				+ " '' as limityear, "
				+ " d.sourcedate,  "
				+ " d.buydate, "
				+ " '0' as reduce_amount,  "
				+ " '0' as reduce_bookvalue, "
				+ " '' as reduce_cause,  "
				+ " d.sourcedoc as approveorg, "
				+ " d.proofyear + '-' + d.proofdoc + '-' + d.proofno as notes, "
				+ " '權利' as propertyTypeName,  "
				+ " d.enterdate as enterdate,  "
				+ " isnull(d.oldlotno,'') oldlotno, "
				+ " '1' as add_sum , "
				+ " '0' as add_area, "
				+ " '0' as reduce_sum, "
				+ " '0' as reduce_area "
				+ " from "
				+ " UNTRT_ADDPROOF d "
				+ " left outer join SYSPK_PROPERTYCODE c on  "
				+ " c.enterorg in (d.enterorg,'000000000A')  "
				+ " and d.propertyno = c.propertyno  "
				+ " where 1 = 1 " + whereCon("enterdate",false)
				+ " union all  "
				+ "  "
				//+ " --權利增減值 "
				+ " select "
				+ " 1 as cnt,  "
				+ " d.propertyno,  "
				+ " d.serialno,  "
				+ " ISNULL(c.propertyname,'') propertyname,  "
				+ " ISNULL(c.propertyunit,'') as propertyunit, "
				+ " '' as bookunit,  "
				+ " '0' as add_amount, "
				+ " case d.differencekind when '110' then (case when @reportYear>= 105 then d.addnetvalue else d.addbookvalue end) else d.addnetvalue end as add_bookvalue, "
				+ " '' as limityear, "
				+ " d.sourcedate,  "
				+ " d.buydate, "
				+ " '0' as reduce_amount,  "
				+ " case d.differencekind when '110' then (case when @reportYear>=105 then d.reducenetvalue else d.reducebookvalue end) else d.reducenetvalue end as reduce_bookvalue, "
				+ " case when d.reducebookvalue > 0 then case when isnull(co.codename,'') = '其他' then isnull(co1.codename,'') else co.codename end else '' end as reduce_cause,  "
				+ " isnull(co2.codename,'') + cast(SUBSTRING(d.approvedate,1,4) - 1911 as varchar) + SUBSTRING(d.approvedate,5,4) + d.approvedoc  as approveorg, "
				+ " d.proofyear + '-' + d.proofdoc + '-' + d.proofno as notes, "
				+ " '權利' as propertyTypeName,  "
				+ " d.adjustdate as enterdate, "
				+ " isnull(d.oldlotno,'') oldlotno, "
				+ " '0' as add_sum , "
				+ " '0' as add_area, "
				+ " '0' as reduce_sum, "
				+ " '0' as reduce_area "
				+ " from "
				+ " UNTRT_ADJUSTPROOF d  "
				+ " left outer join SYSPK_PROPERTYCODE c on  "
				+ " c.enterorg in (d.enterorg,'000000000A')  "
				+ " and d.propertyno = c.propertyno  "
				+ " left outer join SYSCA_CODE co on "
				+ " co.codekindid = 'AJC'  "
				+ " and d.cause = co.codeid  "
				+ " left outer join SYSCA_CODE co1 on  "
				+ " co1.codekindid = 'AJC' "
				+ " and d.cause1 = co1.codeid  "
				+ " left outer join SYSCA_CODE co2 on  "
				+ " co2.codekindid = 'APO' "
				+ " and d.approveorg = co2.codeid  "
				+ " where 1 = 1 " + whereCon("adjustdate",false)
				+ " union all  "
				+ "  "
				//+ " --權利減損 "
				+ " select "
				+ " 1 as cnt,  "
				+ " d.propertyno,  "
				+ " d.serialno,  "
				+ " ISNULL(c.propertyname,'') propertyname,  "
				+ " ISNULL(c.propertyunit,'') as propertyunit, "
				+ " '' as bookunit,  "
				+ " '0' as add_amount, "
				+ " '0' as add_bookvalue,  "
				+ " '' as limityear, "
				+ " d.sourcedate,  "
				+ " d.buydate, "
				+ " '1' as reduce_amount,  "
				+ " case d.differencekind when '110' then (case when @reportYear >= 105 then d.netvalue else d.bookvalue end) else d.netvalue end as reduce_bookvalue, "
				+ " case when isnull(co.codename,'') = '其他' then isnull(co1.codename,'') else co.codename end as reduce_cause, "
				+ " isnull(co2.codename,'') + cast(SUBSTRING(d.approvedate,1,4) - 1911 as varchar) + SUBSTRING(d.approvedate,5,4) + d.approvedoc  as approveorg, "
				+ " d.proofyear + '-' + d.proofdoc + '-' + d.proofno as notes, "
				+ " '權利' as propertyTypeName,  "
				+ " d.reducedate as enterdate, "
				+ " isnull(d.oldlotno,'') oldlotno, "
				+ " '0' as add_sum , "
				+ " '0' as add_area, "
				+ " '0' as reduce_sum, "
				+ " '0' as reduce_area "
				+ " from "
				+ " UNTRT_REDUCEPROOF d  "
				+ " left outer join SYSPK_PROPERTYCODE c on  "
				+ " c.enterorg in (d.enterorg,'000000000A')  "
				+ " and d.propertyno = c.propertyno  "
				+ " left outer join SYSCA_CODE co on "
				+ " co.codekindid = 'CAA'  "
				+ " and d.cause = co.codeid  "
				+ " left outer join SYSCA_CODE co1 on  "
				+ " co1.codekindid = 'CAA' "
				+ " and d.cause1 = co1.codeid  "
				+ " left outer join SYSCA_CODE co2 on  "
				+ " co2.codekindid = 'APO' "
				+ " and d.approveorg = co2.codeid  "
				+ " where 1 = 1 " + whereCon("reducedate",false)
				
				// 權利攤銷
				+ " union all " 
				+ " select 0 as cnt" 
				+ " ,'8' as propertyno"
				+ " ,'' as serialno" 
				+ " ,'權利攤銷' as propertyname" 
				+ " ,'' as propertyunit" 
				+ " ,'' as bookunit"
				+ " ,'0' as add_amount"
				+ " ,'0' as add_bookvalue"
				+ " ,'' as limityear"
				+ " ,'' as sourcedate"
				+ " ,'' as buydate"
				+ " ,'0' as reduce_amount"
				+ " ,isnull(SUM(case when deprunitcb = 'Y' then isnull(scaledmonthdepr,0) else ISNULL(monthdepr,0)  end), 0) as reduce_bookvalue"
				+ " ,'' as reduce_cause"
				+ " ,'' as approveorg"
				+ " ,'本期攤銷' as notes"
				+ " ,'權利' as propertyTypeName"
				+ " ,'99999999' as enterdate"
				+ " ,'' as oldlotno, "
				+ " '0' as add_sum , "
				+ " '0' as add_area, "
				+ " '0' as reduce_sum, "
				+ " '0' as reduce_area "
				+ " from "
				+ " UNTDP_MONTHDEPR d  "
				+ " where  "
				+ " d.propertytype = '8' "
				+  whereCon_enterDate("deprym",true)
				+ "";
		return querySQL;
	}
	
	// 物品查詢
	private String querySQL2() {
		String querySQL =
		// 物品增加
		" select                                                                                                                                                            "
				+ " 1 as cnt,                                                                                                                                                            "	
				+ " d.propertyno,                                                                                                                                                     "
				+ " case when d.serialnos = d.serialnoe then d.serialnos else d.serialnos + '-'  + d.serialnoe end as serialno,                                                       "
				+ " ISNULL(c.propertyname,'') propertyname,                                                                                                                           "
				+ " case when ISNULL(c.propertyunit,'') = '' then ISNULL(d.otherpropertyunit,'') else c.propertyunit end as propertyunit,                                             "
				+ " cast(d.originalunit AS varchar) as bookunit,                                                                                                                      "
				+ " (select sum(1) from UNTNE_NONEXPDETAIL  dd where d.enterorg=dd.enterorg and d.ownership=dd.ownership and d.lotno=dd.lotno and d.propertyno=dd.propertyno and d.differencekind=dd.differencekind ) as add_amount,"
				+ " d.originalbv as add_bookvalue,                                                                                                                                    "
				+ " cast(case when isnull(d.otherlimityear,0) <> 0 then cast(d.otherlimityear/12 as int) else c.limityear end as varchar) as limityear,                                              "
				+ " d.sourcedate,                                                                                                                                                     "
				+ " d.buydate,                                                                                                                                                        "
				+ " '0' as reduce_amount,                                                                                                                                             "
				+ " '0' as reduce_bookvalue,                                                                                                                                          "
				+ " '' as reduce_cause,                                                                                                                                               "
				+ " d.sourcedoc as approveorg,                                                                                                                                        "
				+ " m.proofyear + '-' + m.proofdoc + '-' + m.proofno as notes,                                                                                                        "
				+ " case substring(d.propertyno,1,1) when '3' then '機械及設備' when '4' then '交通及運輸設備' when '5' then '雜項設備' when '6' then '非消耗品' end as propertyTypeName,"
				+ " d.enterdate as enterdate,                                                                                                                                         "
				+ " isnull(d.oldlotno,'') oldlotno,                                                                                                                                    "
				+ " (select sum(1) from UNTNE_NONEXPDETAIL  dd where d.enterorg=dd.enterorg and d.ownership=dd.ownership and d.lotno=dd.lotno and d.propertyno=dd.propertyno and d.differencekind=dd.differencekind ) as add_sum , "
				+ " '0' as add_area, "
				+ " '0' as reduce_sum, "
				+ " '0' as reduce_area "
				+ " from                                                                                                                                                              "
				+ " UNTNE_ADDPROOF m                                                                                                                                                  "
				+ " inner join UNTNE_NONEXP d on                                                                                                                                      "
				+ " m.enterorg = d.enterorg                                                                                                                                           "
				+ " and m.ownership = d.ownership                                                                                                                                     "
				+ " and m.caseno = d.caseno                                                                                                                                           "
				+ " and m.differencekind = d.differencekind                                                                                                                           "
				+ " left outer join SYSPK_PROPERTYCODE2 c on                                                                                                                          "
				+ " c.enterorg in (d.enterorg)                                                                                                                           "
				+ " and d.propertyno = c.propertyno                                                                                                                                   "
				+ " where 1 = 1 " + whereCon("enterdate",true)
				+ " union all                                                                                                                                                         "
				+ "                                                                                                                                                                   "
				+
				// 物品增減值
				" select                                                                                                                                                            "
				+ " 1 as cnt,                                                                                                                                                            "
				+ " d.propertyno,                                                                                                                                                     "
				+ " d.serialno,                                                                                                                                                       "
				+ " ISNULL(c.propertyname,'') propertyname,                                                                                                                           "
				+ " case when ISNULL(c.propertyunit,'') = '' then ISNULL(d.otherpropertyunit,'') else c.propertyunit end as propertyunit,                                             "
				+ " '' as bookunit,                                                                                                                                                   "
				+ " '0' as add_amount,                                                                                                                                                "
				+ " d.addbookvalue as add_bookvalue,                                                                                                                                  "
				+ " cast(case when isnull(d.otherlimityear,0) <> 0 then cast(d.otherlimityear/12 as int) else c.limityear end as varchar) as limityear,                                              "
				+ " d.sourcedate,                                                                                                                                                     "
				+ " d.buydate,                                                                                                                                                        "
				+ " '0' as reduce_amount,                                                                                                                                             "
				+ " d.reducebookvalue as reduce_bookvalue,                                                                                                                            "
				+ " case when d.reducebookvalue > 0 then (case when isnull(co.codename,'') = '其他' then isnull(co1.codename,'') else co.codename end)else '' end as reduce_cause,    "
				+ " isnull(co2.codename,'') + cast(SUBSTRING(m.approvedate,1,4) - 1911 as varchar) + SUBSTRING(m.approvedate,5,4) + m.approvedoc  as approveorg,                      "
				+ " m.proofyear + '-' + m.proofdoc + '-' + m.proofno as notes,                                                                                                        "
				+ " case substring(d.propertyno,1,1) when '3' then '機械及設備' when '4' then '交通及運輸設備' when '5' then '雜項設備' when '6' then '非消耗品' end as propertyTypeName,"
				+ " d.adjustdate as enterdate,                                                                                                                                        "
				+ " isnull(d.oldlotno,'') oldlotno,                                                                                                                                    "
				+ " '0' as add_sum , "
				+ " '0' as add_area, "
				+ " '0' as reduce_sum, "
				+ " '0' as reduce_area "
				+ " from                                                                                                                                                              "
				+ " UNTNE_ADJUSTPROOF m                                                                                                                                               "
				+ " inner join UNTNE_ADJUSTDETAIL d on                                                                                                                                "
				+ " m.enterorg = d.enterorg                                                                                                                                           "
				+ " and m.ownership = d.ownership                                                                                                                                     "
				+ " and m.caseno = d.caseno                                                                                                                                           "
				+ " and m.differencekind = d.differencekind                                                                                                                           "
				+ " left outer join SYSPK_PROPERTYCODE2 c on                                                                                                                          "
				+ " c.enterorg in (d.enterorg)                                                                                                                           "
				+ " and d.propertyno = c.propertyno                                                                                                                                   "
				+ " left outer join SYSCA_CODE co on                                                                                                                                  "
				+ " co.codekindid = 'AJC'                                                                                                                                             "
				+ " and d.cause = co.codeid                                                                                                                                           "
				+ " left outer join SYSCA_CODE co1 on                                                                                                                                 "
				+ " co1.codekindid = 'AJC'                                                                                                                                            "
				+ " and d.cause1 = co1.codeid                                                                                                                                         "
				+ " left outer join SYSCA_CODE co2 on                                                                                                                                 "
				+ " co2.codekindid = 'APO'                                                                                                                                            "
				+ " and m.approveorg = co2.codeid                                                                                                                                     "
				+ " where 1 = 1 " + whereCon("adjustdate",true)
				+ " union all                                                                                                                                                         "
				+ "                                                                                                                                                                   "
				+
				// 物品減損
				" select                                                                                                                                                            "
				+ " 1 as cnt,                                                                                                                                                         "
				+ " d.propertyno,                                                                                                                                                     "
				+ " d.serialno,                                                                                                                                                       "
				+ " ISNULL(c.propertyname,'') propertyname,                                                                                                                           "
				+ " case when ISNULL(c.propertyunit,'') = '' then ISNULL(d.otherpropertyunit,'') else c.propertyunit end as propertyunit,                                             "
				+ " cast(d.adjustbookunit as varchar) as bookunit,                                                                                                                    "
				+ " '0' as add_amount,                                                                                                                                                "
				+ " '0' as add_bookvalue,                                                                                                                                             "
				+ " cast(case when isnull(d.otherlimityear,0) <> 0 then cast(d.otherlimityear/12 as int) else c.limityear end as varchar) as limityear,                                              "
				+ " d.sourcedate,                                                                                                                                                     "
				+ " d.buydate,                                                                                                                                                        "
				+ " 1 as reduce_amount,                                                                                                                              "
				+ " d.adjustbookvalue as reduce_bookvalue,                                                                                                                            "
				+ " case when isnull(co.codename,'') = '其他' then isnull(co1.codename,'') else co.codename end as reduce_cause,                                                      "
				+ " isnull(co2.codename,'') + cast(SUBSTRING(m.approvedate,1,4) - 1911 as varchar) + SUBSTRING(m.approvedate,5,4) + m.approvedoc  as approveorg,                      "
				+ " m.proofyear + '-' + m.proofdoc + '-' + m.proofno as notes,                                                                                                        "
				+ " case substring(d.propertyno,1,1) when '3' then '機械及設備' when '4' then '交通及運輸設備' when '5' then '雜項設備' when '6' then '非消耗品' end as propertyTypeName,"
				+ " d.reducedate as enterdate,                                                                                                                                        "
				+ " isnull(d.oldlotno,'') oldlotno,                                                                                                                                    "
				+ " '0' as add_sum , "
				+ " '0' as add_area, "
				+ " '1' as reduce_sum, "
				+ " '0' as reduce_area "
				+ " from                                                                                                                                                              "
				+ " UNTNE_REDUCEPROOF m                                                                                                                                               "
				+ " inner join UNTNE_REDUCEDETAIL d on                                                                                                                                "
				+ " m.enterorg = d.enterorg                                                                                                                                           "
				+ " and m.ownership = d.ownership                                                                                                                                     "
				+ " and m.caseno = d.caseno                                                                                                                                           "
				+ " and m.differencekind = d.differencekind                                                                                                                           "
				+ " left outer join SYSPK_PROPERTYCODE2 c on                                                                                                                          "
				+ " c.enterorg in (d.enterorg)                                                                                                                           "
				+ " and d.propertyno = c.propertyno                                                                                                                                   "
				+ " left outer join SYSCA_CODE co on                                                                                                                                  "
				+ " co.codekindid = 'CAA'                                                                                                                                             "
				+ " and d.cause = co.codeid                                                                                                                                           "
				+ " left outer join SYSCA_CODE co1 on                                                                                                                                 "
				+ " co1.codekindid = 'CAA'                                                                                                                                            "
				+ " and d.cause1 = co1.codeid                                                                                                                                         "
				+ " left outer join SYSCA_CODE co2 on                                                                                                                                 "
				+ " co2.codekindid = 'APO'                                                                                                                                            "
				+ " and m.approveorg = co2.codeid                                                                                                                                     "
				+ " where 1 = 1 " + whereCon("reducedate",true);
		return querySQL;
	}
}
