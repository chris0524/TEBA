/*
*<br>程式目的：珍貴動產、不動產增減表 
*<br>程式代號：untgr020r
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

import util.*;

public class UNTGR020R extends SuperBean{
		
	String q_enterOrg;
	String q_enterOrgName;
	String q_ownership;
	String q_propertyKind;
	String q_fundType;
	String q_fundTypeKind;
	String q_valueable;
	String q_enterDateS;
	String q_enterDateE;
	String q_reportType;
	String q_reportYear;
	String q_reportMonth;
	String q_reportSeason;	
	String q_periodType;
	String q_grass;
	String q_isorganmanager;
	String isorganmanagerT;
	String q_differenceKind;
	private String reportType;						// 報表類型
	
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
	public String getQ_ownership(){ return checkGet(q_ownership); }
	public void setQ_ownership(String s){ q_ownership=checkSet(s); }
	public String getQ_propertyKind(){ return checkGet(q_propertyKind); }
	public void setQ_propertyKind(String s){ q_propertyKind=checkSet(s); }
	public String getQ_fundType(){ return checkGet(q_fundType); }
	public void setQ_fundType(String s){ q_fundType=checkSet(s); }
	public void setQ_fundTypeKind(String s){ q_fundTypeKind=checkSet(s); }
	public String getQ_fundTypeKind(){ return checkGet(q_fundTypeKind); }
	public String getQ_valueable(){ return checkGet(q_valueable); }
	public void setQ_valueable(String s){ q_valueable=checkSet(s); }
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
	public String getQ_reportSeason(){ return checkGet(q_reportSeason); }
	public void setQ_reportSeason(String s){ q_reportSeason=checkSet(s); }
	public String getQ_periodType(){ return checkGet(q_periodType); }
	public void setQ_periodType(String s){ q_periodType=checkSet(s); }
	public String getQ_differenceKind(){ return checkGet(q_differenceKind); }
	public void setQ_differenceKind(String s){ q_differenceKind=checkSet(s); }
	
	
	String isOrganManager;
	String isAdminManager;
	String organID;
	String userName;
	public String getOrganID() { return checkGet(organID); }
	public void setOrganID(String s) { organID = checkSet(s); }
	public String getIsOrganManager() { return checkGet(isOrganManager); }
	public void setIsOrganManager(String s) { isOrganManager = checkSet(s); }
	public String getIsAdminManager() { return checkGet(isAdminManager); }
	public void setIsAdminManager(String s) { isAdminManager = checkSet(s); }
	public String getUserName(){ return checkGet(userName); }
	public void setUserName(String s){ userName=checkSet(s); }

	String fileName;

	public String getFileName() { return checkGet(fileName); }
	public void setFileName(String s) { fileName = checkSet(s); }

	
	private String p_differenceKind;
	public String getP_differenceKind() { return checkGet(p_differenceKind); }
	public void setP_differenceKind(String p_differenceKind) { this.p_differenceKind = checkSet(p_differenceKind); }
	
	// 查詢條件
	private String whereCon(String date,boolean valuable) {
		String whereCon = "" ;
		if (!"".equals(getQ_enterOrg())) {
			whereCon+=" and d.enterorg = " + Common.sqlChar(getQ_enterOrg()) ;
		} else {
			if (!getIsAdminManager().equalsIgnoreCase("Y")) {
				if ("Y".equals(getIsOrganManager())) {					
					whereCon += " and d.enterorg like '" + getOrganID().substring(0,5) + "%' ";
				} else {
					whereCon +=" and d.enterorg = " + Common.sqlChar(getOrganID()) ;
				}
			} 
		}
		if (!"".equals(Common.get(getQ_differenceKind())))	//財產區分別
			whereCon += " and d.differencekind = " + util.Common.sqlChar(getQ_differenceKind());
		if (!"".equals(Common.get(getQ_ownership())))	//權屬
			whereCon += " and d.ownership = " + util.Common.sqlChar(getQ_ownership());
		if (!"".equals(Common.get(getQ_enterDateS())))	//入帳日期起
			whereCon += " and d." + date + "  >= " + util.Common.sqlChar(Datetime.changeTaiwanYYMMDD(getQ_enterDateS(), "2"));
		if (!"".equals(Common.get(getQ_enterDateE())))	//入帳日期訖
			whereCon += " and d." + date + "  <= " + util.Common.sqlChar(Datetime.changeTaiwanYYMMDD(getQ_enterDateE(), "2"));
	   	if (!"".equals(getQ_propertyKind()))//財產性質
	    	whereCon +=" and d.propertykind='"+getQ_propertyKind()+"' ";
	    if (!"".equals(getQ_fundType()))//基金財產
	    	whereCon +=" and d.fundtype='"+getQ_fundType()+"' ";
	    	 		
		
		//入帳 固定為「Y:是」
		whereCon += " and d.verify = 'Y' ";
		//珍貴財產 固定為「Y:是」
		if (valuable) {
			whereCon += " and d.valuable = 'Y' ";
		}
				
		
		return whereCon;
	}
	// 財產查詢
	private String querySQL1() {
		String querySQL = 				
				//" 土地增加 "
				" select                                                                                                                                                                   "
				+ " 1 as cnt,                                                                                                                                                                "
				+ " d.propertyno,                                                                                                                                                            "
				+ " d.serialno,                                                                                                                                                              "
				+ " ISNULL(c.propertyname,'') propertyname,                                                                                                                                  "
				+ " '平方公尺' as propertyunit,                                                                                                                                              "
				+ " cast(d.originalunit AS varchar) as bookunit,                                                                                                                             "
				+ " d.originalholdarea as add_amount,                                                                                                                                        "
				+ " d.originalbv as add_bookvalue,                                                                                                                                           "
				+ " '' as limityear,                                                                                                                                                         "
				+ " d.sourcedate,                                                                                                                                                            "
				+ " d.buydate,                                                                                                                                                               "
				+ " '0' as reduce_amount,                                                                                                                                                    "
				+ " '0' as reduce_bookvalue,                                                                                                                                                 "
				+ " '' as reduce_cause,                                                                                                                                                      "
				+ " d.sourcedoc as approveorg,                                                                                                                                               "
				+ " m.proofyear + '-' + m.proofdoc + '-' + m.proofno as notes,                                                                                                               "
				+ " '土地' propertyTypeName,                                                                                                                                                 "
				+ " d.enterdate as enterdate                                                                                                                                                 "
				+ " from                                                                                                                                                                     "
				+ " UNTLA_ADDPROOF m                                                                                                                                                         "
				+ " inner join UNTLA_LAND d on                                                                                                                                               "
				+ " m.enterorg = d.enterorg                                                                                                                                                  "
				+ " and m.ownership = d.ownership                                                                                                                                            "
				+ " and m.caseno = d.caseno                                                                                                                                                  "
				+ " and m.differencekind = d.differencekind                                                                                                                                  "
				+ " left outer join SYSPK_PROPERTYCODE c on                                                                                                                                  "
				+ " c.enterorg in (d.enterorg,'000000000A')                                                                                                                                  "
				+ " and d.propertyno = c.propertyno                                                                                                                                          "
				+ " where 1 = 1 " + whereCon("enterdate",true)
				+ " union all                                                                                                                                                                "
				+ "                                                                                                                                                                          "
				//+ " --土地增減值                                                                                                                                                             "
				+ " select                                                                                                                                                                   "
				+ " 1 as cnt,                                                                                                                                                                "
				+ " d.propertyno,                                                                                                                                                            "
				+ " d.serialno,                                                                                                                                                              "
				+ " ISNULL(c.propertyname,'') propertyname,                                                                                                                                  "
				+ " '平方公尺' as propertyunit,                                                                                                                                              "
				+ " '' as bookunit,                                                                                                                                                          "
				+ " case when d.adjustholdarea > 0 then d.adjustholdarea else 0 end as add_amount,                                                                                           "
				+ " d.addbookvalue as add_bookvalue,                                                                                                                                         "
				+ " '' as limityear,                                                                                                                                                         "
				+ " d.sourcedate,                                                                                                                                                            "
				+ " d.buydate,                                                                                                                                                               "
				+ " case when d.adjustholdarea < 0 then ABS(d.adjustholdarea) else 0 end as reduce_amount,                                                                                   "
				+ " d.reducebookvalue as reduce_bookvalue,                                                                                                                                   "
				+ " case when d.reducebookvalue > 0 then (case when isnull(co.codename,'') = '其他' then isnull(co1.codename,'') else co.codename end)else '' end as reduce_cause,           "
				+ " isnull(co2.codename,'') + cast(SUBSTRING(m.approvedate,1,4) - 1911 as varchar) + SUBSTRING(m.approvedate,5,4) + m.approvedoc  as approveorg,                             "
				+ " m.proofyear + '-' + m.proofdoc + '-' + m.proofno as notes,                                                                                                               "
				+ " '土地' propertyTypeName,                                                                                                                                                 "
				+ " d.adjustdate as enterdate                                                                                                                                                 "
				+ " from                                                                                                                                                                     "
				+ " UNTLA_ADJUSTPROOF m                                                                                                                                                      "
				+ " inner join UNTLA_ADJUSTDETAIL d on                                                                                                                                       "
				+ " m.enterorg = d.enterorg                                                                                                                                                  "
				+ " and m.ownership = d.ownership                                                                                                                                            "
				+ " and m.caseno = d.caseno                                                                                                                                                  "
				+ " and m.differencekind = d.differencekind                                                                                                                                  "
				+ " left outer join SYSPK_PROPERTYCODE c on                                                                                                                                  "
				+ " c.enterorg in (d.enterorg,'000000000A')                                                                                                                                  "
				+ " and d.propertyno = c.propertyno                                                                                                                                          "
				+ " left outer join SYSCA_CODE co on                                                                                                                                         "
				+ " co.codekindid = 'AJC'                                                                                                                                                    "
				+ " and d.cause = co.codeid                                                                                                                                                  "
				+ " left outer join SYSCA_CODE co1 on                                                                                                                                        "
				+ " co1.codekindid = 'AJC'                                                                                                                                                   "
				+ " and d.cause1 = co1.codeid                                                                                                                                                "
				+ " left outer join SYSCA_CODE co2 on                                                                                                                                        "
				+ " co2.codekindid = 'APO'                                                                                                                                                   "
				+ " and m.approveorg = co2.codeid                                                                                                                                            "
				+ " where 1 = 1 " + whereCon("adjustdate",true)
				+ " union all                                                                                                                                                                "
				+ "                                                                                                                                                                          "
				//+ " --土地減損                                                                                                                                                               "
				+ " select                                                                                                                                                                   "
				+ " 1 as cnt,                                                                                                                                                                "
				+ " d.propertyno,                                                                                                                                                            "
				+ " d.serialno,                                                                                                                                                              "
				+ " ISNULL(c.propertyname,'') propertyname,                                                                                                                                  "
				+ " '平方公尺' as propertyunit,                                                                                                                                              "
				+ " cast(d.bookunit AS varchar) as bookunit,                                                                                                                                 "
				+ " '0' as add_amount,                                                                                                                                                       "
				+ " '0' as add_bookvalue,                                                                                                                                                    "
				+ " '' as limityear,                                                                                                                                                         "
				+ " d.sourcedate,                                                                                                                                                            "
				+ " d.buydate,                                                                                                                                                               "
				+ " ABS(d.holdarea) as reduce_amount,                                                                                                                                             "
				+ " d.bookvalue as reduce_bookvalue,                                                                                                                                         "
				+ " case when isnull(co.codename,'') = '其他' then isnull(co1.codename,'') else co.codename end as reduce_cause,                                                             "
				+ " isnull(co2.codename,'') + cast(SUBSTRING(m.approvedate,1,4) - 1911 as varchar) + SUBSTRING(m.approvedate,5,4) + m.approvedoc  as approveorg,                             "
				+ " m.proofyear + '-' + m.proofdoc + '-' + m.proofno as notes,                                                                                                               "
				+ " '土地' propertyTypeName,                                                                                                                                                 "
				+ " d.reducedate as enterdate                                                                                                                                                 "
				+ " from                                                                                                                                                                     "
				+ " UNTLA_REDUCEPROOF m                                                                                                                                                      "
				+ " inner join UNTLA_REDUCEDETAIL d on                                                                                                                                       "
				+ " m.enterorg = d.enterorg                                                                                                                                                  "
				+ " and m.ownership = d.ownership                                                                                                                                            "
				+ " and m.caseno = d.caseno                                                                                                                                                  "
				+ " and m.differencekind = d.differencekind                                                                                                                                  "
				+ " left outer join SYSPK_PROPERTYCODE c on                                                                                                                                  "
				+ " c.enterorg in (d.enterorg,'000000000A')                                                                                                                                  "
				+ " and d.propertyno = c.propertyno                                                                                                                                          "
				+ " left outer join SYSCA_CODE co on                                                                                                                                         "
				+ " co.codekindid = 'CAA'                                                                                                                                                    "
				+ " and d.cause = co.codeid                                                                                                                                                  "
				+ " left outer join SYSCA_CODE co1 on                                                                                                                                        "
				+ " co1.codekindid = 'CAA'                                                                                                                                                   "
				+ " and d.cause1 = co1.codeid                                                                                                                                                "
				+ " left outer join SYSCA_CODE co2 on                                                                                                                                        "
				+ " co2.codekindid = 'APO'                                                                                                                                                   "
				+ " and m.approveorg = co2.codeid                                                                                                                                            "
				+ " where 1 = 1 " + whereCon("reducedate",true)
				+ " union all                                                                                                                                                                "
				+ "                                                                                                                                                                          "
				//+ " --土地改良物增加                                                                                                                                                         "
				+ " select                                                                                                                                                                   "
				+ " 1 as cnt,                                                                                                                                                                "
				+ " d.propertyno,                                                                                                                                                            "
				+ " d.serialno,                                                                                                                                                              "
				+ " ISNULL(c.propertyname,'') propertyname,                                                                                                                                  "
				+ " ISNULL(c.propertyunit,'') as propertyunit,                                                                                                                               "
				+ " '' as bookunit,                                                                                                                                                          "
				+ " '1' as add_amount,                                                                                                                                                       "
				+ " d.originalbv - (isnull(d.originalaccumdepr1,0) + isnull(d.originalaccumdepr2,0)) as add_bookvalue,                                                                                                                                           "
				+ " cast(case when isnull(c.limityear,'') = '' then d.otherlimityear else c.limityear end as varchar) as limityear,                                                          "
				+ " d.sourcedate,                                                                                                                                                            "
				+ " d.buydate,                                                                                                                                                               "
				+ " '0' as reduce_amount,                                                                                                                                                    "
				+ " '0' as reduce_bookvalue,                                                                                                                                                 "
				+ " '' as reduce_cause,                                                                                                                                                      "
				+ " d.sourcedoc as approveorg,                                                                                                                                               "
				+ " m.proofyear + '-' + m.proofdoc + '-' + m.proofno as notes,                                                                                                               "
				+ " '土地改良物' propertyTypeName,                                                                                                                                                 "
				+ " d.enterdate as enterdate                                                                                                                                                 "
				+ " from                                                                                                                                                                     "
				+ " UNTRF_ADDPROOF m                                                                                                                                                         "
				+ " inner join UNTRF_ATTACHMENT d on                                                                                                                                         "
				+ " m.enterorg = d.enterorg                                                                                                                                                  "
				+ " and m.ownership = d.ownership                                                                                                                                            "
				+ " and m.caseno = d.caseno                                                                                                                                                  "
				+ " and m.differencekind = d.differencekind                                                                                                                                  "
				+ " left outer join SYSPK_PROPERTYCODE c on                                                                                                                                  "
				+ " c.enterorg in (d.enterorg,'000000000A')                                                                                                                                  "
				+ " and d.propertyno = c.propertyno                                                                                                                                          "
				+ " where 1 = 1 " + whereCon("enterdate",true)
				+ " union all                                                                                                                                                                "
				+ "                                                                                                                                                                          "
				//+ " --土地改良物增減值                                                                                                                                                       "
				+ " select                                                                                                                                                                   "
				+ " 1 as cnt,                                                                                                                                                                "
				+ " d.propertyno,                                                                                                                                                            "
				+ " d.serialno,                                                                                                                                                              "
				+ " ISNULL(c.propertyname,'') propertyname,                                                                                                                                  "
				+ " ISNULL(c.propertyunit,'') as propertyunit,                                                                                                                               "
				+ " '' as bookunit,                                                                                                                                                          "
				+ " '0' as add_amount,                                                                                                                                                       "
				+ " case d.differencekind when '110' then d.addbookvalue else d.addnetvalue end as add_bookvalue,                                                                            "
				+ " cast(case when isnull(c.limityear,'') = '' then d.otherlimityear else c.limityear end as varchar) as limityear,                                                          "
				+ " d.sourcedate,                                                                                                                                                            "
				+ " d.buydate,                                                                                                                                                               "
				+ " '0' as reduce_amount,                                                                                                                                                    "
				+ " case d.differencekind when '110' then d.reducebookvalue else d.reducenetvalue end as reduce_bookvalue,                                                                   "
				+ " case when d.reducebookvalue > 0 then case when isnull(co.codename,'') = '其他' then isnull(co1.codename,'') else co.codename end else '' end as reduce_cause,            "
				+ " isnull(co2.codename,'') + cast(SUBSTRING(m.approvedate,1,4) - 1911 as varchar) + SUBSTRING(m.approvedate,5,4) + m.approvedoc  as approveorg,                             "
				+ " m.proofyear + '-' + m.proofdoc + '-' + m.proofno as notes,                                                                                                               "
				+ " '土地改良物' propertyTypeName,                                                                                                                                                 "
				+ " d.adjustdate as enterdate                                                                                                                                                 "
				+ " from                                                                                                                                                                     "
				+ " UNTRF_ADJUSTPROOF m                                                                                                                                                      "
				+ " inner join UNTRF_ADJUSTDETAIL d on                                                                                                                                       "
				+ " m.enterorg = d.enterorg                                                                                                                                                  "
				+ " and m.ownership = d.ownership                                                                                                                                            "
				+ " and m.caseno = d.caseno                                                                                                                                                  "
				+ " and m.differencekind = d.differencekind                                                                                                                                  "
				+ " left outer join SYSPK_PROPERTYCODE c on                                                                                                                                  "
				+ " c.enterorg in (d.enterorg,'000000000A')                                                                                                                                  "
				+ " and d.propertyno = c.propertyno                                                                                                                                          "
				+ " left outer join SYSCA_CODE co on                                                                                                                                         "
				+ " co.codekindid = 'AJC'                                                                                                                                                    "
				+ " and d.cause = co.codeid                                                                                                                                                  "
				+ " left outer join SYSCA_CODE co1 on                                                                                                                                        "
				+ " co1.codekindid = 'AJC'                                                                                                                                                   "
				+ " and d.cause1 = co1.codeid                                                                                                                                                "
				+ " left outer join SYSCA_CODE co2 on                                                                                                                                        "
				+ " co2.codekindid = 'APO'                                                                                                                                                   "
				+ " and m.approveorg = co2.codeid                                                                                                                                            "
				+ " where 1 = 1 " + whereCon("adjustdate",true)
				+ " union all                                                                                                                                                                "
				+ "                                                                                                                                                                          "
				//+ " --土地改良物減損                                                                                                                                                         "
				+ " select                                                                                                                                                                   "
				+ " 1 as cnt,                                                                                                                                                                "
				+ " d.propertyno,                                                                                                                                                            "
				+ " d.serialno,                                                                                                                                                              "
				+ " ISNULL(c.propertyname,'') propertyname,                                                                                                                                  "
				+ " ISNULL(c.propertyunit,'') as propertyunit,                                                                                                                               "
				+ " '' as bookunit,                                                                                                                                                          "
				+ " '0' as add_amount,                                                                                                                                                       "
				+ " '0' as add_bookvalue,                                                                                                                                                    "
				+ " cast(case when isnull(c.limityear,'') = '' then d.otherlimityear else c.limityear end as varchar) as limityear,                                                          "
				+ " d.sourcedate,                                                                                                                                                            "
				+ " d.buydate,                                                                                                                                                               "
				+ " '1' as reduce_amount,                                                                                                                                                    "
				+ " case d.differencekind when '110' then d.bookvalue else d.netvalue end as reduce_bookvalue,                                                                               "
				+ " case when isnull(co.codename,'') = '其他' then isnull(co1.codename,'') else co.codename end as reduce_cause,                                                             "
				+ " isnull(co2.codename,'') + cast(SUBSTRING(m.approvedate,1,4) - 1911 as varchar) + SUBSTRING(m.approvedate,5,4) + m.approvedoc  as approveorg,                             "
				+ " m.proofyear + '-' + m.proofdoc + '-' + m.proofno as notes,                                                                                                               "
				+ " '土地改良物' propertyTypeName,                                                                                                                                                 "
				+ " d.reducedate as enterdate                                                                                                                                                 "
				+ " from                                                                                                                                                                     "
				+ " UNTRF_REDUCEPROOF m                                                                                                                                                      "
				+ " inner join UNTRF_REDUCEDETAIL d on                                                                                                                                       "
				+ " m.enterorg = d.enterorg                                                                                                                                                  "
				+ " and m.ownership = d.ownership                                                                                                                                            "
				+ " and m.caseno = d.caseno                                                                                                                                                  "
				+ " and m.differencekind = d.differencekind                                                                                                                                  "
				+ " left outer join SYSPK_PROPERTYCODE c on                                                                                                                                  "
				+ " c.enterorg in (d.enterorg,'000000000A')                                                                                                                                  "
				+ " and d.propertyno = c.propertyno                                                                                                                                          "
				+ " left outer join SYSCA_CODE co on                                                                                                                                         "
				+ " co.codekindid = 'CAA'                                                                                                                                                    "
				+ " and d.cause = co.codeid                                                                                                                                                  "
				+ " left outer join SYSCA_CODE co1 on                                                                                                                                        "
				+ " co1.codekindid = 'CAA'                                                                                                                                                   "
				+ " and d.cause1 = co1.codeid                                                                                                                                                "
				+ " left outer join SYSCA_CODE co2 on                                                                                                                                        "
				+ " co2.codekindid = 'APO'                                                                                                                                                   "
				+ " and m.approveorg = co2.codeid                                                                                                                                            "
				+ " where 1 = 1 " + whereCon("reducedate",true)
				+ " union all                                                                                                                                                                "
				+ "                                                                                                                                                                          "
				//+ " --土地改良物折舊                                                                                                                                                         "
				+ " select                                                                                                                                                                   "
				+ " 0 as cnt,                                                                                                                                                                "
				+ " '111' as propertyno,                                                                                                                                                     "
				+ " '' as serialno,                                                                                                                                                          "
				+ " '土地改良物折舊' as propertyname,                                                                                                                                        "
				+ " '' as propertyunit,                                                                                                                                                      "
				+ " '' as bookunit,                                                                                                                                                          "
				+ " '0' as add_amount,                                                                                                                                                       "
				+ " '0' as add_bookvalue,                                                                                                                                                    "
				+ " '' as limityear,                                                                                                                                                         "
				+ " '' as sourcedate,                                                                                                                                                        "
				+ " '' as buydate,                                                                                                                                                           "
				+ " '0' as reduce_amount,                                                                                                                                                    "
				+ " isnull(SUM(case when deprunitcb = 'N' then ISNULL(monthdepr,0) else isnull(scaledmonthdepr,0) end),0) as reduce_bookvalue,                                               "
				+ " '' as reduce_cause,                                                                                                                                                      "
				+ " '' as approveorg,                                                                                                                                                        "
				+ " '本期折舊' as notes,                                                                                                                                                     "
				+ " '土地改良物' propertyTypeName,                                                                                                                                                 "
				+ " '99999999' as enterdate                                                                                                                                                 "
				+ " from                                                                                                                                                                     "
				+ " UNTDP_MONTHDEPR d                                                                                                                                                        "
				+ " where                                                                                                                                                                    "
				+ " d.propertytype = '2'                                                                                                                                                     "
				+ whereCon("enterdate",true)
				+ " union all                                                                                                                                                                "
				+ "                                                                                                                                                                          "
				//+ " --建物增加                                                                                                                                                               "
				+ " select                                                                                                                                                                   "
				+ " 1 as cnt,                                                                                                                                                                "
				+ " d.propertyno,                                                                                                                                                            "
				+ " d.serialno,                                                                                                                                                              "
				+ " ISNULL(c.propertyname,'') propertyname,                                                                                                                                  "
				+ " ISNULL(c.propertyunit,'') as propertyunit,                                                                                                                               "
				+ " '' as bookunit,                                                                                                                                                          "
				+ " case when ((d.propertyno between '2010101001A' and '20105030003' or d.propertyno='2010503002A' or d.propertyno between '20106010001' and '20106040001') and @reportYear >= 105) then d.originalholdarea else '1' end as add_amount,"
				+ " d.originalbv - (isnull(d.originalaccumdepr1,0) + isnull(d.originalaccumdepr2,0)) as add_bookvalue,                                                                                                                                           "
				+ " cast(case when isnull(c.limityear,'') = '' then d.otherlimityear else c.limityear end as varchar) as limityear,                                                          "
				+ " d.sourcedate,                                                                                                                                                            "
				+ " d.buydate,                                                                                                                                                               "
				+ " '0' as reduce_amount,                                                                                                                                                    "
				+ " '0' as reduce_bookvalue,                                                                                                                                                 "
				+ " '' as reduce_cause,                                                                                                                                                      "
				+ " d.sourcedoc as approveorg,                                                                                                                                               "
				+ " m.proofyear + '-' + m.proofdoc + '-' + m.proofno as notes,                                                                                                               "
				+ " '建物' propertyTypeName,                                                                                                                                                 "
				+ " d.enterdate as enterdate                                                                                                                                                 "
				+ " from                                                                                                                                                                     "
				+ " UNTBU_ADDPROOF m                                                                                                                                                         "
				+ " inner join UNTBU_BUILDING d on                                                                                                                                           "
				+ " m.enterorg = d.enterorg                                                                                                                                                  "
				+ " and m.ownership = d.ownership                                                                                                                                            "
				+ " and m.caseno = d.caseno                                                                                                                                                  "
				+ " and m.differencekind = d.differencekind                                                                                                                                  "
				+ " left outer join SYSPK_PROPERTYCODE c on                                                                                                                                  "
				+ " c.enterorg in (d.enterorg,'000000000A')                                                                                                                                  "
				+ " and d.propertyno = c.propertyno                                                                                                                                          "
				+ " where 1 = 1 " + whereCon("enterdate",true)
				+ " union all                                                                                                                                                                "
				+ "                                                                                                                                                                          "
				//+ " --建物增減值                                                                                                                                                             "
				+ " select                                                                                                                                                                   "
				+ " 1 as cnt,                                                                                                                                                                "
				+ " d.propertyno,                                                                                                                                                            "
				+ " d.serialno,                                                                                                                                                              "
				+ " ISNULL(c.propertyname,'') propertyname,                                                                                                                                  "
				+ " ISNULL(c.propertyunit,'') as propertyunit,                                                                                                                               "
				+ " '' as bookunit,                                                                                                                                                          "
				+ " case when ((d.propertyno between '2010101001A' and '20105030003' or d.propertyno='2010503002A' or d.propertyno between '20106010001' and '20106040001') and @reportYear >= 105) then (case when d.adjustholdarea > 0 then d.adjustholdarea else '0' end) else '0' end as add_amount, "
				+ " case d.differencekind when '110' then d.addbookvalue else d.addnetvalue end as add_bookvalue,                                                                            "
				+ " cast(case when isnull(c.limityear,'') = '' then d.otherlimityear else c.limityear end as varchar) as limityear,                                                          "
				+ " d.sourcedate,                                                                                                                                                            "
				+ " d.buydate,                                                                                                                                                               "
				+ " case when ((d.propertyno between '2010101001A' and '20105030003' or d.propertyno='2010503002A' or d.propertyno between '20106010001' and '20106040001') and @reportYear >= 105) then (case when d.adjustholdarea < 0 then ABS(d.adjustholdarea) else '0' end) else '0' end as reduce_amount, "
				+ " case d.differencekind when '110' then d.reducebookvalue else d.reducenetvalue end as reduce_bookvalue,                                                                   "
				+ " case when d.reducebookvalue > 0 then case when isnull(co.codename,'') = '其他' then isnull(co1.codename,'') else co.codename end else '' end as reduce_cause,            "
				+ " isnull(co2.codename,'') + cast(SUBSTRING(m.approvedate,1,4) - 1911 as varchar) + SUBSTRING(m.approvedate,5,4) + m.approvedoc  as approveorg,                             "
				+ " m.proofyear + '-' + m.proofdoc + '-' + m.proofno as notes,                                                                                                               "
				+ " '建物' propertyTypeName,                                                                                                                                                 "
				+ " d.adjustdate as enterdate                                                                                                                                                 "
				+ " from                                                                                                                                                                     "
				+ " UNTBU_ADJUSTPROOF m                                                                                                                                                      "
				+ " inner join UNTBU_ADJUSTDETAIL d on                                                                                                                                       "
				+ " m.enterorg = d.enterorg                                                                                                                                                  "
				+ " and m.ownership = d.ownership                                                                                                                                            "
				+ " and m.caseno = d.caseno                                                                                                                                                  "
				+ " and m.differencekind = d.differencekind                                                                                                                                  "
				+ " left outer join SYSPK_PROPERTYCODE c on                                                                                                                                  "
				+ " c.enterorg in (d.enterorg,'000000000A')                                                                                                                                  "
				+ " and d.propertyno = c.propertyno                                                                                                                                          "
				+ " left outer join SYSCA_CODE co on                                                                                                                                         "
				+ " co.codekindid = 'AJC'                                                                                                                                                    "
				+ " and d.cause = co.codeid                                                                                                                                                  "
				+ " left outer join SYSCA_CODE co1 on                                                                                                                                        "
				+ " co1.codekindid = 'AJC'                                                                                                                                                   "
				+ " and d.cause1 = co1.codeid                                                                                                                                                "
				+ " left outer join SYSCA_CODE co2 on                                                                                                                                        "
				+ " co2.codekindid = 'APO'                                                                                                                                                   "
				+ " and m.approveorg = co2.codeid                                                                                                                                            "
				+ " where 1 = 1 " + whereCon("adjustdate",true)
				+ " union all                                                                                                                                                                "
				+ "                                                                                                                                                                          "
				//+ " --建物減損                                                                                                                                                               "
				+ " select                                                                                                                                                                   "
				+ " 1 as cnt,                                                                                                                                                                "
				+ " d.propertyno,                                                                                                                                                            "
				+ " d.serialno,                                                                                                                                                              "
				+ " ISNULL(c.propertyname,'') propertyname,                                                                                                                                  "
				+ " ISNULL(c.propertyunit,'') as propertyunit,                                                                                                                               "
				+ " '' as bookunit,                                                                                                                                                          "
				+ " '0' as add_amount,                                                                                                                                                       "
				+ " '0' as add_bookvalue,                                                                                                                                                    "
				+ " cast(case when isnull(c.limityear,'') = '' then d.otherlimityear else c.limityear end as varchar) as limityear,                                                          "
				+ " d.sourcedate,                                                                                                                                                            "
				+ " d.buydate,                                                                                                                                                               "
				+ " case when ((d.propertyno between '2010101001A' and '20105030003' or d.propertyno='2010503002A' or d.propertyno between '20106010001' and '20106040001') and @reportYear >= 105) then d.holdarea else '1' end as reduce_amount, "
				+ " case d.differencekind when '110' then d.bookvalue else d.netvalue end as reduce_bookvalue,                                                                               "
				+ " case when isnull(co.codename,'') = '其他' then isnull(co1.codename,'') else co.codename end as reduce_cause,                                                             "
				+ " isnull(co2.codename,'') + cast(SUBSTRING(m.approvedate,1,4) - 1911 as varchar) + SUBSTRING(m.approvedate,5,4) + m.approvedoc  as approveorg,                             "
				+ " m.proofyear + '-' + m.proofdoc + '-' + m.proofno as notes,                                                                                                               "
				+ " '建物' propertyTypeName,                                                                                                                                                 "
				+ " d.reducedate as enterdate                                                                                                                                                 "
				+ " from                                                                                                                                                                     "
				+ " UNTBU_REDUCEPROOF m                                                                                                                                                      "
				+ " inner join UNTBU_REDUCEDETAIL d on                                                                                                                                       "
				+ " m.enterorg = d.enterorg                                                                                                                                                  "
				+ " and m.ownership = d.ownership                                                                                                                                            "
				+ " and m.caseno = d.caseno                                                                                                                                                  "
				+ " and m.differencekind = d.differencekind                                                                                                                                  "
				+ " left outer join SYSPK_PROPERTYCODE c on                                                                                                                                  "
				+ " c.enterorg in (d.enterorg,'000000000A')                                                                                                                                  "
				+ " and d.propertyno = c.propertyno                                                                                                                                          "
				+ " left outer join SYSCA_CODE co on                                                                                                                                         "
				+ " co.codekindid = 'CAA'                                                                                                                                                    "
				+ " and d.cause = co.codeid                                                                                                                                                  "
				+ " left outer join SYSCA_CODE co1 on                                                                                                                                        "
				+ " co1.codekindid = 'CAA'                                                                                                                                                   "
				+ " and d.cause1 = co1.codeid                                                                                                                                                "
				+ " left outer join SYSCA_CODE co2 on                                                                                                                                        "
				+ " co2.codekindid = 'APO'                                                                                                                                                   "
				+ " and m.approveorg = co2.codeid                                                                                                                                            "
				+ " where 1 = 1 " + whereCon("reducedate",true)
				+ " union all                                                                                                                                                                "
				+ "                                                                                                                                                                          "
				//+ " --建物折舊                                                                                                                                                               "
				+ " select                                                                                                                                                                   "
				+ " 0 as cnt,                                                                                                                                                                "
				+ " '2' as propertyno,                                                                                                                                                       "
				+ " '' as serialno,                                                                                                                                                          "
				+ " '房屋建築及設備折舊' as propertyname,                                                                                                                                    "
				+ " '' as propertyunit,                                                                                                                                                      "
				+ " '' as bookunit,                                                                                                                                                          "
				+ " '0' as add_amount,                                                                                                                                                       "
				+ " '0' as add_bookvalue,                                                                                                                                                    "
				+ " '' as limityear,                                                                                                                                                         "
				+ " '' as sourcedate,                                                                                                                                                        "
				+ " '' as buydate,                                                                                                                                                           "
				+ " '0' as reduce_amount,                                                                                                                                                    "
				+ " isnull(SUM(case when deprunitcb = 'N' then ISNULL(monthdepr,0) else isnull(scaledmonthdepr,0) end),0) as reduce_bookvalue,                                               "
				+ " '' as reduce_cause,                                                                                                                                                      "
				+ " '' as approveorg,                                                                                                                                                        "
				+ " '本期折舊' as notes,                                                                                                                                                     "
				+ " '建物' propertyTypeName,                                                                                                                                                 "
				+ " '99999999' as enterdate                                                                                                                                                 "
				+ " from                                                                                                                                                                     "
				+ " UNTDP_MONTHDEPR d                                                                                                                                                        "
				+ " where                                                                                                                                                                    "
				+ " d.propertytype = '3'                                                                                                                                                     "
				+  whereCon("enterdate",true)
				+ " union all                                                                                                                                                                "
				+ "                                                                                                                                                                          "
				//+ " --動產增加                                                                                                                                                               "
				+ " select                                                                                                                                                                   "
				+ " 1 as cnt,                                                                                                                                                                "
				+ " d.propertyno,                                                                                                                                                            "
				+ " case when d.serialnos = d.serialnoe then d.serialnos else d.serialnos + '-'  + d.serialnoe end as serialno,                                                              "
				+ " ISNULL(c.propertyname,'') propertyname,                                                                                                                                  "
				+ " case when ISNULL(c.propertyunit,'') = '' then ISNULL(d.otherpropertyunit,'') else c.propertyunit end as propertyunit,                                                    "
				+ " cast(d.originalunit as varchar) as bookunit,                                                                                                                             "
				+ " (select sum(1) from UNTMP_MOVABLEDETAIL  dd where d.enterorg=dd.enterorg and d.ownership=dd.ownership and d.lotno=dd.lotno and d.propertyno=dd.propertyno and d.differencekind=dd.differencekind ) as add_amount,"
				+ " d.originalbv - (isnull(d.originalaccumdepr1,0) + isnull(d.originalaccumdepr2,0)) as add_bookvalue,                                                                                                                                           "
				+ " cast(case when isnull(c.limityear,'') = '' then d.otherlimityear else c.limityear end as varchar) as limityear,                                                          "
				+ " d.sourcedate,                                                                                                                                                            "
				+ " d.buydate,                                                                                                                                                               "
				+ " '0' as reduce_amount,                                                                                                                                                    "
				+ " '0' as reduce_bookvalue,                                                                                                                                                 "
				+ " '' as reduce_cause,                                                                                                                                                      "
				+ " d.sourcedoc as approveorg,                                                                                                                                               "
				+ " m.proofyear + '-' + m.proofdoc + '-' + m.proofno as notes,                                                                                                               "
				+ " case substring(d.propertyno,1,1) when '3' then '機械及設備' when '4' then '交通及運輸設備' when '5' then '雜項設備' end as propertyTypeName,                               "
				+ " d.enterdate as enterdate                                                                                                                                                  "
				+ " from                                                                                                                                                                     "
				+ " UNTMP_ADDPROOF m                                                                                                                                                         "
				+ " inner join UNTMP_MOVABLE d on                                                                                                                                            "
				+ " m.enterorg = d.enterorg                                                                                                                                                  "
				+ " and m.ownership = d.ownership                                                                                                                                            "
				+ " and m.caseno = d.caseno                                                                                                                                                  "
				+ " and m.differencekind = d.differencekind                                                                                                                                  "
				+ " left outer join SYSPK_PROPERTYCODE c on                                                                                                                                  "
				+ " c.enterorg in (d.enterorg,'000000000A')                                                                                                                                  "
				+ " and d.propertyno = c.propertyno                                                                                                                                          "
				+ " where 1 = 1 " + whereCon("enterdate",true)
				+ " union all                                                                                                                                                                "
				+ "                                                                                                                                                                          "
				//+ " --動產增減值                                                                                                                                                             "
				+ " select                                                                                                                                                                   "
				+ " 1 as cnt,                                                                                                                                                                "
				+ " d.propertyno,                                                                                                                                                            "
				+ " d.serialno,                                                                                                                                                              "
				+ " ISNULL(c.propertyname,'') propertyname,                                                                                                                                  "
				+ " case when ISNULL(c.propertyunit,'') = '' then ISNULL(d.otherpropertyunit,'') else c.propertyunit end as propertyunit,                                                    "
				+ " '' as bookunit,                                                                                                                                                          "
				+ " '0' as add_amount,                                                                                                                                                       "
				+ " case d.differencekind when '110' then d.addbookvalue else d.addnetvalue end as add_bookvalue,                                                                            "
				+ " cast(case when isnull(c.limityear,'') = '' then d.otherlimityear else c.limityear end as varchar) as limityear,                                                          "
				+ " d.sourcedate,                                                                                                                                                            "
				+ " d.buydate,                                                                                                                                                               "
				+ " '0' as reduce_amount,                                                                                                                                                    "
				+ " case d.differencekind when '110' then d.reducebookvalue else d.reducenetvalue end as reduce_bookvalue,                                                                   "
				+ " case when d.reducebookvalue > 0 then case when isnull(co.codename,'') = '其他' then isnull(co1.codename,'') else co.codename end else '' end as reduce_cause,            "
				+ " isnull(co2.codename,'') + cast(SUBSTRING(m.approvedate,1,4) - 1911 as varchar) + SUBSTRING(m.approvedate,5,4) + m.approvedoc  as approveorg,                             "
				+ " m.proofyear + '-' + m.proofdoc + '-' + m.proofno as notes,                                                                                                               "
				+ " case substring(d.propertyno,1,1) when '3' then '機械及設備' when '4' then '交通及運輸設備' when '5' then '雜項設備' end as propertyTypeName,                               "
				+ " d.adjustdate as enterdate                                                                                                                                                  "
				+ " from                                                                                                                                                                     "
				+ " UNTMP_ADJUSTPROOF m                                                                                                                                                      "
				+ " inner join UNTMP_ADJUSTDETAIL d on                                                                                                                                       "
				+ " m.enterorg = d.enterorg                                                                                                                                                  "
				+ " and m.ownership = d.ownership                                                                                                                                            "
				+ " and m.caseno = d.caseno                                                                                                                                                  "
				+ " and m.differencekind = d.differencekind                                                                                                                                  "
				+ " left outer join SYSPK_PROPERTYCODE c on                                                                                                                                  "
				+ " c.enterorg in (d.enterorg,'000000000A')                                                                                                                                  "
				+ " and d.propertyno = c.propertyno                                                                                                                                          "
				+ " left outer join SYSCA_CODE co on                                                                                                                                         "
				+ " co.codekindid = 'AJC'                                                                                                                                                    "
				+ " and d.cause = co.codeid                                                                                                                                                  "
				+ " left outer join SYSCA_CODE co1 on                                                                                                                                        "
				+ " co1.codekindid = 'AJC'                                                                                                                                                   "
				+ " and d.cause1 = co1.codeid                                                                                                                                                "
				+ " left outer join SYSCA_CODE co2 on                                                                                                                                        "
				+ " co2.codekindid = 'APO'                                                                                                                                                   "
				+ " and m.approveorg = co2.codeid                                                                                                                                            "
				+ " where 1 = 1 " + whereCon("adjustdate",true)
				+ " union all                                                                                                                                                                "
				+ "                                                                                                                                                                          "
				//+ " --動產減損                                                                                                                                                               "
				+ " select                                                                                                                                                                   "
				+ " 1 as cnt,                                                                                                                                                                "
				+ " d.propertyno,                                                                                                                                                            "
				+ " d.serialno,                                                                                                                                                              "
				+ " ISNULL(c.propertyname,'') propertyname,                                                                                                                                  "
				+ " case when ISNULL(c.propertyunit,'') = '' then ISNULL(d.otherpropertyunit,'') else c.propertyunit end as propertyunit,                                                    "
				+ " cast(case d.differencekind when '110' then d.adjustbookunit else d.adjustnetunit end as varchar) as bookunit,                                                            "
				+ " '0' as add_amount,                                                                                                                                                       "
				+ " '0' as add_bookvalue,                                                                                                                                                    "
				+ " cast(case when isnull(c.limityear,'') = '' then d.otherlimityear else c.limityear end as varchar) as limityear,                                                          "
				+ " d.sourcedate,                                                                                                                                                            "
				+ " d.buydate,                                                                                                                                                               "
				+ " 1 as reduce_amount,                                                                                                                                     "
				+ " case d.differencekind when '110' then d.adjustbookvalue else d.adjustnetvalue end as reduce_bookvalue,                                                                   "
				+ " case when isnull(co.codename,'') = '其他' then isnull(co1.codename,'') else co.codename end as reduce_cause,                                                             "
				+ " isnull(co2.codename,'') + cast(SUBSTRING(m.approvedate,1,4) - 1911 as varchar) + SUBSTRING(m.approvedate,5,4) + m.approvedoc  as approveorg,                             "
				+ " m.proofyear + '-' + m.proofdoc + '-' + m.proofno as notes,                                                                                                               "
				+ " case substring(d.propertyno,1,1) when '3' then '機械及設備' when '4' then '交通及運輸設備' when '5' then '雜項設備' end as propertyTypeName,                               "
				+ " d.reducedate as enterdate                                                                                                                                                  "
				+ " from                                                                                                                                                                     "
				+ " UNTMP_REDUCEPROOF m                                                                                                                                                      "
				+ " inner join UNTMP_REDUCEDETAIL d on                                                                                                                                       "
				+ " m.enterorg = d.enterorg                                                                                                                                                  "
				+ " and m.ownership = d.ownership                                                                                                                                            "
				+ " and m.caseno = d.caseno                                                                                                                                                  "
				+ " and m.differencekind = d.differencekind                                                                                                                                  "
				+ " left outer join SYSPK_PROPERTYCODE c on                                                                                                                                  "
				+ " c.enterorg in (d.enterorg,'000000000A')                                                                                                                                  "
				+ " and d.propertyno = c.propertyno                                                                                                                                          "
				+ " left outer join SYSCA_CODE co on                                                                                                                                         "
				+ " co.codekindid = 'CAA'                                                                                                                                                    "
				+ " and d.cause = co.codeid                                                                                                                                                  "
				+ " left outer join SYSCA_CODE co1 on                                                                                                                                        "
				+ " co1.codekindid = 'CAA'                                                                                                                                                   "
				+ " and d.cause1 = co1.codeid                                                                                                                                                "
				+ " left outer join SYSCA_CODE co2 on                                                                                                                                        "
				+ " co2.codekindid = 'APO'                                                                                                                                                   "
				+ " and m.approveorg = co2.codeid                                                                                                                                            "
				+ " where 1 = 1 " + whereCon("reducedate",true)
				+ " union all                                                                                                                                                                "
				+ "                                                                                                                                                                          "
				//+ " --動產折舊(機械及設備)                                                                                                                                                   "
				+ " select                                                                                                                                                                   "
				+ " 0 as cnt,                                                                                                                                                                "
				+ " '3' as propertyno,                                                                                                                                                       "
				+ " '' as serialno,                                                                                                                                                          "
				+ " '機械及設備折舊' as propertyname,                                                                                                                                        "
				+ " '' as propertyunit,                                                                                                                                                      "
				+ " '' as bookunit,                                                                                                                                                          "
				+ " '0' as add_amount,                                                                                                                                                       "
				+ " '0' as add_bookvalue,                                                                                                                                                    "
				+ " '' as limityear,                                                                                                                                                         "
				+ " '' as sourcedate,                                                                                                                                                        "
				+ " '' as buydate,                                                                                                                                                           "
				+ " '0' as reduce_amount,                                                                                                                                                    "
				+ " isnull(SUM(case when deprunitcb = 'N' then ISNULL(monthdepr,0) else isnull(scaledmonthdepr,0) end),0) as reduce_bookvalue,                                               "
				+ " '' as reduce_cause,                                                                                                                                                      "
				+ " '' as approveorg,                                                                                                                                                        "
				+ " '本期折舊' as notes,                                                                                                                                                     "
				+ " '機械及設備' as propertyTypeName,                                                                                                                                        "
				+ " '99999999' as enterdate                                                                                                                                                 "
				+ " from                                                                                                                                                                     "
				+ " UNTDP_MONTHDEPR d                                                                                                                                                        "
				+ " where                                                                                                                                                                    "
				+ " d.propertytype = '4'                                                                                                                                                     "
				+ whereCon("enterdate",true)
				+ " union all                                                                                                                                                                "
				+ "                                                                                                                                                                          "
				//+ " --動產折舊(交通及運輸設備)                                                                                                                                               "
				+ " select                                                                                                                                                                   "
				+ " 0 as cnt,                                                                                                                                                                "
				+ " '4' as propertyno,                                                                                                                                                       "
				+ " '' as serialno,                                                                                                                                                          "
				+ " '交通及運輸設備折舊' as propertyname,                                                                                                                                    "
				+ " '' as propertyunit,                                                                                                                                                      "
				+ " '' as bookunit,                                                                                                                                                          "
				+ " '0' as add_amount,                                                                                                                                                       "
				+ " '0' as add_bookvalue,                                                                                                                                                    "
				+ " '' as limityear,                                                                                                                                                         "
				+ " '' as sourcedate,                                                                                                                                                        "
				+ " '' as buydate,                                                                                                                                                           "
				+ " '0' as reduce_amount,                                                                                                                                                    "
				+ " isnull(SUM(case when deprunitcb = 'N' then ISNULL(monthdepr,0) else isnull(scaledmonthdepr,0) end),0) as reduce_bookvalue,                                               "
				+ " '' as reduce_cause,                                                                                                                                                      "
				+ " '' as approveorg,                                                                                                                                                        "
				+ " '本期折舊' as notes,                                                                                                                                                     "
				+ " '交通及運輸設備' as propertyTypeName,                                                                                                                                        "
				+ " '99999999' as enterdate                                                                                                                                                 "
				+ " from                                                                                                                                                                     "
				+ " UNTDP_MONTHDEPR d                                                                                                                                                        "
				+ " where                                                                                                                                                                    "
				+ " d.propertytype = '5'                                                                                                                                                     "
				+ whereCon("enterdate",true)
				+ " union all                                                                                                                                                                "
				+ "                                                                                                                                                                          "
				//+ " --動產折舊(雜項設備)                                                                                                                                                     "
				+ " select                                                                                                                                                                   "
				+ " 0 as cnt,                                                                                                                                                                "
				+ " '5' as propertyno,                                                                                                                                                       "
				+ " '' as serialno,                                                                                                                                                          "
				+ " '雜項設備折舊' as propertyname,                                                                                                                                          "
				+ " '' as propertyunit,                                                                                                                                                      "
				+ " '' as bookunit,                                                                                                                                                          "
				+ " '0' as add_amount,                                                                                                                                                       "
				+ " '0' as add_bookvalue,                                                                                                                                                    "
				+ " '' as limityear,                                                                                                                                                         "
				+ " '' as sourcedate,                                                                                                                                                        "
				+ " '' as buydate,                                                                                                                                                           "
				+ " '0' as reduce_amount,                                                                                                                                                    "
				+ " isnull(SUM(case when deprunitcb = 'N' then ISNULL(monthdepr,0) else isnull(scaledmonthdepr,0) end),0) as reduce_bookvalue,                                               "
				+ " '' as reduce_cause,                                                                                                                                                      "
				+ " '' as approveorg,                                                                                                                                                        "
				+ " '本期折舊' as notes,                                                                                                                                                     "
				+ " '雜項設備' as propertyTypeName,                                                                                                                                        "
				+ " '99999999' as enterdate                                                                                                                                                 "
				+ " from                                                                                                                                                                     "
				+ " UNTDP_MONTHDEPR d                                                                                                                                                        "
				+ " where                                                                                                                                                                    "
				+ " d.propertytype = '6'                                                                                                                                                     "
				+ whereCon("enterdate",true)
				+ " union all                                                                                                                                                                "
				+ "                                                                                                                                                                          "
				//+ " --有價證券增加                                                                                                                                                           "
				+ " select                                                                                                                                                                   "
				+ " 1 as cnt,                                                                                                                                                                "
				+ " d.propertyno,                                                                                                                                                            "
				+ " d.serialno,                                                                                                                                                              "
				+ " ISNULL(c.propertyname,'') propertyname,                                                                                                                                  "
				+ " ISNULL(c.propertyunit,'') as propertyunit,                                                                                                                               "
				+ " '' as bookunit,                                                                                                                                                          "
				+ " d.originalamount as add_amount,                                                                                                                                          "
				+ " d.originalbv as add_bookvalue,                                                                                                                                           "
				+ " '' as limityear,                                                                                                                                                         "
				+ " d.sourcedate,                                                                                                                                                            "
				+ " d.buydate,                                                                                                                                                               "
				+ " '0' as reduce_amount,                                                                                                                                                    "
				+ " '0' as reduce_bookvalue,                                                                                                                                                 "
				+ " '' as reduce_cause,                                                                                                                                                      "
				+ " d.sourcedoc as approveorg,                                                                                                                                               "
				+ " d.proofyear + '-' + d.proofdoc + '-' + d.proofno as notes,                                                                                                               "
				+ " '有價證券' as propertyTypeName,                                                                                                                                        "
				+ " d.enterdate as enterdate                                                                                                                                                 "
				+ " from                                                                                                                                                                     "
				+ " UNTVP_ADDPROOF d                                                                                                                                                         "
				+ " left outer join SYSPK_PROPERTYCODE c on                                                                                                                                  "
				+ " c.enterorg in (d.enterorg,'000000000A')                                                                                                                                  "
				+ " and d.propertyno = c.propertyno                                                                                                                                          "
				+ " where 1 = 1 " + whereCon("enterdate",false)
				+ " union all                                                                                                                                                                "
				+ "                                                                                                                                                                          "
				//+ " --有價證券增減值                                                                                                                                                         "
				+ " select                                                                                                                                                                   "
				+ " 1 as cnt,                                                                                                                                                                "
				+ " d.propertyno,                                                                                                                                                            "
				+ " d.serialno,                                                                                                                                                              "
				+ " ISNULL(c.propertyname,'') propertyname,                                                                                                                                  "
				+ " ISNULL(c.propertyunit,'') as propertyunit,                                                                                                                               "
				+ " '' as bookunit,                                                                                                                                                          "
				+ " d.addbookamount as add_amount,                                                                                                                                           "
				+ " d.addbookvalue as add_bookvalue,                                                                                                                                         "
				+ " '' as limityear,                                                                                                                                                         "
				+ " d.sourcedate,                                                                                                                                                            "
				+ " d.buydate,                                                                                                                                                               "
				+ " d.reducebookamount as reduce_amount,                                                                                                                                     "
				+ " d.reducebookvalue reduce_bookvalue,                                                                                                                                      "
				+ " case when d.reducebookvalue > 0 then case when isnull(co.codename,'') = '其他' then isnull(co1.codename,'') else co.codename end else '' end as reduce_cause,            "
				+ " isnull(co2.codename,'') + cast(SUBSTRING(d.approvedate,1,4) - 1911 as varchar) + SUBSTRING(d.approvedate,5,4) + d.approvedoc  as approveorg,                             "
				+ " d.proofyear + '-' + d.proofdoc + '-' + d.proofno as notes,                                                                                                               "
				+ " '有價證券' as propertyTypeName,                                                                                                                                        "
				+ " d.adjustdate as enterdate                                                                                                                                                 "
				+ " from                                                                                                                                                                     "
				+ " UNTVP_ADJUSTPROOF d                                                                                                                                                      "
				+ " left outer join SYSPK_PROPERTYCODE c on                                                                                                                                  "
				+ " c.enterorg in (d.enterorg,'000000000A')                                                                                                                                  "
				+ " and d.propertyno = c.propertyno                                                                                                                                          "
				+ " left outer join SYSCA_CODE co on                                                                                                                                         "
				+ " co.codekindid = 'AJC'                                                                                                                                                    "
				+ " and d.cause = co.codeid                                                                                                                                                  "
				+ " left outer join SYSCA_CODE co1 on                                                                                                                                        "
				+ " co1.codekindid = 'AJC'                                                                                                                                                   "
				+ " and d.cause1 = co1.codeid                                                                                                                                                "
				+ " left outer join SYSCA_CODE co2 on                                                                                                                                        "
				+ " co2.codekindid = 'APO'                                                                                                                                                   "
				+ " and d.approveorg = co2.codeid                                                                                                                                            "
				+ " where 1 = 1 " + whereCon("adjustdate",false)
				+ " union all                                                                                                                                                                "
				+ "                                                                                                                                                                          "
				//+ " --有價證券減損                                                                                                                                                           "
				+ " select                                                                                                                                                                   "
				+ " 1 as cnt,                                                                                                                                                                "
				+ " d.propertyno,                                                                                                                                                            "
				+ " d.serialno,                                                                                                                                                              "
				+ " ISNULL(c.propertyname,'') propertyname,                                                                                                                                  "
				+ " ISNULL(c.propertyunit,'') as propertyunit,                                                                                                                               "
				+ " '' as bookunit,                                                                                                                                                          "
				+ " '0' as add_amount,                                                                                                                                                       "
				+ " '0' as add_bookvalue,                                                                                                                                                    "
				+ " '' as limityear,                                                                                                                                                         "
				+ " d.sourcedate,                                                                                                                                                            "
				+ " d.buydate,                                                                                                                                                               "
				+ " d.bookamount as reduce_amount,                                                                                                                                           "
				+ " bookvalue as reduce_bookvalue,                                                                                                                                           "
				+ " case when isnull(co.codename,'') = '其他' then isnull(co1.codename,'') else co.codename end as reduce_cause,                                                             "
				+ " isnull(co2.codename,'') + cast(SUBSTRING(d.approvedate,1,4) - 1911 as varchar) + SUBSTRING(d.approvedate,5,4) + d.approvedoc  as approveorg,                             "
				+ " d.proofyear + '-' + d.proofdoc + '-' + d.proofno as notes,                                                                                                               "
				+ " '有價證券' as propertyTypeName,                                                                                                                                        "
				+ " d.adjustdate as enterdate                                                                                                                                                 "
				+ " from                                                                                                                                                                     "
				+ " UNTVP_REDUCEPROOF d                                                                                                                                                      "
				+ " left outer join SYSPK_PROPERTYCODE c on                                                                                                                                  "
				+ " c.enterorg in (d.enterorg,'000000000A')                                                                                                                                  "
				+ " and d.propertyno = c.propertyno                                                                                                                                          "
				+ " left outer join SYSCA_CODE co on                                                                                                                                         "
				+ " co.codekindid = 'CAA'                                                                                                                                                    "
				+ " and d.cause = co.codeid                                                                                                                                                  "
				+ " left outer join SYSCA_CODE co1 on                                                                                                                                        "
				+ " co1.codekindid = 'CAA'                                                                                                                                                   "
				+ " and d.cause1 = co1.codeid                                                                                                                                                "
				+ " left outer join SYSCA_CODE co2 on                                                                                                                                        "
				+ " co2.codekindid = 'APO'                                                                                                                                                   "
				+ " and d.approveorg = co2.codeid                                                                                                                                            "
				+ " where 1 = 1 " + whereCon("adjustdate",false)
				+ " union all                                                                                                                                                                "
				+ "                                                                                                                                                                          "
				//+ " --權利增加                                                                                                                                                               "
				+ " select                                                                                                                                                                   "
				+ " 1 as cnt,                                                                                                                                                                "
				+ " d.propertyno,                                                                                                                                                            "
				+ " d.serialno,                                                                                                                                                              "
				+ " ISNULL(c.propertyname,'') propertyname,                                                                                                                                  "
				+ " ISNULL(c.propertyunit,'') as propertyunit,                                                                                                                               "
				+ " '' as bookunit,                                                                                                                                                          "
				+ " '1' as add_amount,                                                                                                                                                       "
				+ " d.originalbv - (isnull(d.originalaccumdepr1,0) + isnull(d.originalaccumdepr2,0)) as add_bookvalue,                                                                                                                                           "
				+ " '' as limityear,                                                                                                                                                         "
				+ " d.sourcedate,                                                                                                                                                            "
				+ " d.buydate,                                                                                                                                                               "
				+ " '0' as reduce_amount,                                                                                                                                                    "
				+ " '0' as reduce_bookvalue,                                                                                                                                                 "
				+ " '' as reduce_cause,                                                                                                                                                      "
				+ " d.sourcedoc as approveorg,                                                                                                                                               "
				+ " d.proofyear + '-' + d.proofdoc + '-' + d.proofno as notes,                                                                                                               "
				+ " '權利' as propertyTypeName,                                                                                                                                        "
				+ " d.enterdate as enterdate                                                                                                                                                 "
				+ " from                                                                                                                                                                     "
				+ " UNTRT_ADDPROOF d                                                                                                                                                         "
				+ " left outer join SYSPK_PROPERTYCODE c on                                                                                                                                  "
				+ " c.enterorg in (d.enterorg,'000000000A')                                                                                                                                  "
				+ " and d.propertyno = c.propertyno                                                                                                                                          "
				+ " where 1 = 1 " + whereCon("enterdate",true)
				+ " union all                                                                                                                                                                "
				+ "                                                                                                                                                                          "
				//+ " --權利增減值                                                                                                                                                             "
				+ " select                                                                                                                                                                   "
				+ " 1 as cnt,                                                                                                                                                                "
				+ " d.propertyno,                                                                                                                                                            "
				+ " d.serialno,                                                                                                                                                              "
				+ " ISNULL(c.propertyname,'') propertyname,                                                                                                                                  "
				+ " ISNULL(c.propertyunit,'') as propertyunit,                                                                                                                               "
				+ " '' as bookunit,                                                                                                                                                          "
				+ " '0' as add_amount,                                                                                                                                                       "
				+ " d.addbookvalue as add_bookvalue,                                                                                                                                         "
				+ " '' as limityear,                                                                                                                                                         "
				+ " d.sourcedate,                                                                                                                                                            "
				+ " d.buydate,                                                                                                                                                               "
				+ " '0' as reduce_amount,                                                                                                                                                    "
				+ " d.reducebookvalue as reduce_bookvalue,                                                                                                                                   "
				+ " case when d.reducebookvalue > 0 then case when isnull(co.codename,'') = '其他' then isnull(co1.codename,'') else co.codename end else '' end as reduce_cause,            "
				+ " isnull(co2.codename,'') + cast(SUBSTRING(d.approvedate,1,4) - 1911 as varchar) + SUBSTRING(d.approvedate,5,4) + d.approvedoc  as approveorg,                             "
				+ " d.proofyear + '-' + d.proofdoc + '-' + d.proofno as notes,                                                                                                               "
				+ " '權利' as propertyTypeName,                                                                                                                                        "
				+ " d.adjustdate as enterdate                                                                                                                                                 "
				+ " from                                                                                                                                                                     "
				+ " UNTRT_ADJUSTPROOF d                                                                                                                                                      "
				+ " left outer join SYSPK_PROPERTYCODE c on                                                                                                                                  "
				+ " c.enterorg in (d.enterorg,'000000000A')                                                                                                                                  "
				+ " and d.propertyno = c.propertyno                                                                                                                                          "
				+ " left outer join SYSCA_CODE co on                                                                                                                                         "
				+ " co.codekindid = 'AJC'                                                                                                                                                    "
				+ " and d.cause = co.codeid                                                                                                                                                  "
				+ " left outer join SYSCA_CODE co1 on                                                                                                                                        "
				+ " co1.codekindid = 'AJC'                                                                                                                                                   "
				+ " and d.cause1 = co1.codeid                                                                                                                                                "
				+ " left outer join SYSCA_CODE co2 on                                                                                                                                        "
				+ " co2.codekindid = 'APO'                                                                                                                                                   "
				+ " and d.approveorg = co2.codeid                                                                                                                                            "
				+ " where 1 = 1 " + whereCon("adjustdate",false)
				+ " union all                                                                                                                                                                "
				+ "                                                                                                                                                                          "
				//+ " --權利減損                                                                                                                                                               "
				+ " select                                                                                                                                                                   "
				+ " 1 as cnt,                                                                                                                                                                "
				+ " d.propertyno,                                                                                                                                                            "
				+ " d.serialno,                                                                                                                                                              "
				+ " ISNULL(c.propertyname,'') propertyname,                                                                                                                                  "
				+ " ISNULL(c.propertyunit,'') as propertyunit,                                                                                                                               "
				+ " '' as bookunit,                                                                                                                                                          "
				+ " '0' as add_amount,                                                                                                                                                       "
				+ " '0' as add_bookvalue,                                                                                                                                                    "
				+ " '' as limityear,                                                                                                                                                         "
				+ " d.sourcedate,                                                                                                                                                            "
				+ " d.buydate,                                                                                                                                                               "
				+ " '1' as reduce_amount,                                                                                                                                                    "
				+ " bookvalue as reduce_bookvalue,                                                                                                                                           "
				+ " case when isnull(co.codename,'') = '其他' then isnull(co1.codename,'') else co.codename end as reduce_cause,                                                             "
				+ " isnull(co2.codename,'') + cast(SUBSTRING(d.approvedate,1,4) - 1911 as varchar) + SUBSTRING(d.approvedate,5,4) + d.approvedoc  as approveorg,                             "
				+ " d.proofyear + '-' + d.proofdoc + '-' + d.proofno as notes,                                                                                                               "
				+ " '權利' as propertyTypeName,                                                                                                                                        "
				+ " d.reducedate as enterdate                                                                                                                                                 "
				+ " from                                                                                                                                                                     "
				+ " UNTRT_REDUCEPROOF d                                                                                                                                                      "
				+ " left outer join SYSPK_PROPERTYCODE c on                                                                                                                                  "
				+ " c.enterorg in (d.enterorg,'000000000A')                                                                                                                                  "
				+ " and d.propertyno = c.propertyno                                                                                                                                          "
				+ " left outer join SYSCA_CODE co on                                                                                                                                         "
				+ " co.codekindid = 'CAA'                                                                                                                                                    "
				+ " and d.cause = co.codeid                                                                                                                                                  "
				+ " left outer join SYSCA_CODE co1 on                                                                                                                                        "
				+ " co1.codekindid = 'CAA'                                                                                                                                                   "
				+ " and d.cause1 = co1.codeid                                                                                                                                                "
				+ " left outer join SYSCA_CODE co2 on                                                                                                                                        "
				+ " co2.codekindid = 'APO'                                                                                                                                                   "
				+ " and d.approveorg = co2.codeid                                                                                                                                            "
				+ " where 1 = 1 " + whereCon("reducedate",false); 
		return querySQL;
	}	

	public DefaultTableModel getResultModel() throws Exception {
		Map<String,String> differenceKindMap = TCGHCommon.getSysca_Code("DFK"); 					//財產區分別名稱

		DefaultTableModel model = new javax.swing.table.DefaultTableModel();
		Database db = new Database();
		try {
			String[] columns;
			columns = new String[] { 
					"DIFFERENCEKIND", "COLUMNS_1", "COLUMNS_2",
					"COLUMNS_3", "COLUMNS_4", "COLUMNS_5", "COLUMNS_6",
					"COLUMNS_7", "COLUMNS_8", "COLUMNS_9", "COLUMNS_10",
					"COLUMNS_11", "COLUMNS_12", "COLUMNS_13", "COLUMNS_14",
					"PROPERTYNAME", "CNT" };
			String execSQL = "DECLARE @reportYear AS NVARCHAR(3) SET @reportYear = " + Common.sqlChar(this.getQ_reportYear());
			execSQL += " select * from (" + querySQL1() + ") aa order by aa.propertyno,aa.serialno,aa.enterdate ";

			
			ResultSet rs = db.getForwardStatement().executeQuery(execSQL);
			Vector<Object[]> rowData = new Vector<Object[]>();
			int totalCNT = 0;
			int i = 0;
			while (rs.next()) {
				i++;
				
				int currCNT = rs.getInt("cnt");
				totalCNT += currCNT;
				Object[] data = new Object[17];
				String differenceKind = differenceKindMap.get(getQ_differenceKind());
				this.setP_differenceKind(differenceKind);
				data[0] = differenceKind;
				String propertyno = Common.checkGet(rs.getString("propertyno"));
				if (propertyno.length() > 7) {
					if (Common.checkGet(rs.getString("serialno")).length() == 0 ) {
						data[1] = propertyno.substring(0, 7) + "-" + propertyno.substring(7);
					} else {
						data[1] = propertyno.substring(0, 7) + "-" + propertyno.substring(7) + "-"  + rs.getString("serialno");
					}
				} else {
					if (Common.checkGet(rs.getString("serialno")).length() == 0 ) {
						data[1] = propertyno;
					} else {
						data[1] = propertyno + "-"  + rs.getString("serialno");
					}
				}
				data[2] = rs.getString("propertyname");
				data[3] = rs.getString("propertyunit");
				data[4] = rs.getString("bookunit");
				data[5] = rs.getString("add_amount");
				data[6] = rs.getLong("add_bookvalue");
				data[7] = rs.getString("limityear");
				data[8] = Datetime.changeTaiwanYYMMDD(rs.getString("sourcedate"), "1");
				data[9] = Datetime.changeTaiwanYYMMDD(rs.getString("buydate"), "1");
				data[10] = rs.getString("reduce_amount");
				data[11] = rs.getLong("reduce_bookvalue");
				data[12] = rs.getString("reduce_cause");
				data[13] = rs.getString("approveorg");
				data[14] = rs.getString("notes");
				data[15] = rs.getString("propertyTypeName");
				data[16] = currCNT;
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

}
