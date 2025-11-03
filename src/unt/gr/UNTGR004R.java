/*
*<br>程式目的：財產明細分類帳 
*<br>程式代號：UNTGR004R
*<br>撰寫日期：0950303
*<br>程式作者：Cherry
*<br>--------------------------------------------------------
*<br>修改作者　　修改日期　　　修改目的
*<br>--------------------------------------------------------
*/

package unt.gr;

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

public class UNTGR004R extends SuperBean{
		
	String q_enterOrg;
	String q_enterOrgName;
	String userName;
	String q_differenceKind;
	String q_ownership;
	String q_reportYM;
	String q_propertyType;
	String q_propertyNoS;
	String q_propertyNoSName;
	String q_propertyNoE;
	String q_propertyNoEName;
	String q_propertyKind1;
	String q_valuable;
	String q_verify;		
	DecimalFormat amount = new DecimalFormat("###,###,###,##0.00");
	private String q_reportType;						// 報表類型
	
	public String getQ_reportType() { return checkGet(q_reportType); }
	public void setQ_reportType(String q_reportType) { this.q_reportType = checkGet(q_reportType); }
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
	public String getQ_reportYM(){ return checkGet(q_reportYM); }
	public void setQ_reportYM(String s){ q_reportYM=checkSet(s); }
	public String getQ_propertyType(){ return checkGet(q_propertyType); }
	public void setQ_propertyType(String s){ q_propertyType=checkSet(s); }
	public String getQ_propertyNoS(){ return checkGet(q_propertyNoS); }
	public void setQ_propertyNoS(String s){ q_propertyNoS=checkSet(s); }
	public String getQ_propertyNoSName(){ return checkGet(q_propertyNoSName); }
	public void setQ_propertyNoSName(String s){ q_propertyNoSName=checkSet(s); }
	public String getQ_propertyNoE(){ return checkGet(q_propertyNoE); }
	public void setQ_propertyNoE(String s){ q_propertyNoE=checkSet(s); }
	public String getQ_propertyNoEName(){ return checkGet(q_propertyNoEName); }
	public void setQ_propertyNoEName(String s){ q_propertyNoEName=checkSet(s); }
	public String getQ_propertyKind1(){ return checkGet(q_propertyKind1); }
	public void setQ_propertyKind1(String s){ q_propertyKind1=checkSet(s); }
	public String getQ_valuable(){ return checkGet(q_valuable); }
	public void setQ_valuable(String s){ q_valuable=checkSet(s); }
	public String getQ_verify(){ return checkGet(q_verify); }
	public void setQ_verify(String s){ q_verify=checkSet(s); }

	String nowPropertyNo;
	String oldPropertyNo;
	public String getNowPropertyNo(){ return checkGet(nowPropertyNo); }
	public void setNowPropertyNo(String s){ nowPropertyNo=checkSet(s); }
	public String getOldPropertyNo(){ return checkGet(oldPropertyNo); }
	public void setOldPropertyNo(String s){ oldPropertyNo=checkSet(s); }
	
	String isOrganManager;
	String isAdminManager;
	String organID;    
	public String getOrganID() { return checkGet(organID); }
	public void setOrganID(String s) { organID = checkSet(s); }
	public String getIsOrganManager() { return checkGet(isOrganManager); }
	public void setIsOrganManager(String s) { isOrganManager = checkSet(s); }
	public String getIsAdminManager() { return checkGet(isAdminManager); }
	public void setIsAdminManager(String s) { isAdminManager = checkSet(s); }
	
	public DefaultTableModel getResultModel() throws Exception {
		Map<String,String> differenceKindMap = TCGHCommon.getSysca_Code("DFK"); 					//財產區分別名稱
		
		DefaultTableModel model = new javax.swing.table.DefaultTableModel();
		Database db = new Database();
		double balance_Amount=0;
		long balance_BookValue=0;
		try {
			String[] columns;
			columns = new String[] { 
					"DIFFERENCEKIND", "PROPERTYNO", "PROPERTYNAME",
					"PROPERTYUNIT", "LIMITYEAR", "ENTERDATE_YY", "ENTERDATE_MM",
					"ENTERDATE_DD", "BOOKNOTES", "SERIALNO", "ADD_AMOUNT",
					"ADD_BOOKUNIT", "ADD_BOOKVALUE", "REDUCE_AMOUNT", "REDUCE_BOOKUNIT",
					"REDUCE_BOOKVALUE", "BALANCE_AMOUNT", "BALANCE_BOOKUNIT", "BALANCE_BOOKVALUE",
					"MONTHDEPR", "NEWACCUMDEPR", "NEWNETVALUE"};
			String execSQL = "";
			
			if ("1".equals(getQ_propertyKind1())) {
				execSQL = " select * from (" + querySQL1() + ") aa where 1=1 ";
			} else if ("2".equals(getQ_propertyKind1())) {
				execSQL = " select * from (" + querySQL2() + ") aa where 1=1 ";
			}
			if (!"".equals(Common.get(getQ_propertyType()))) {	//財產大類
				int propertyType = Integer.parseInt(getQ_propertyType());
				switch (propertyType) {
					case 1: //土地
						execSQL += " and substring(aa.propertyno,1,1) = '1' and substring(aa.propertyno,1,3) <> '111' ";
						break;
					case 2: //土地
						execSQL += " and substring(aa.propertyno,1,3) = '111' ";
						break;
					case 3: //建物
						execSQL += " and substring(aa.propertyno,1,1) = '2' ";
						break;
					case 4: //動產
						execSQL += " and substring(aa.propertyno,1,1) in ('3','4','5') ";
						break;
					case 5: //有價證券
						execSQL += " and substring(aa.propertyno,1,1) = '9' ";
						break;
					case 6: //權利
						execSQL += " and substring(aa.propertyno,1,1) = '8' ";
						break;
					default:
						break;
				}
			}
			execSQL += " order by aa.propertyno,aa.enterdate,aa.serialno ";
			ResultSet rs = db.getForwardStatement().executeQuery(execSQL);
			Vector<Object[]> rowData = new Vector<Object[]>();
			int i = 0;
			while (rs.next()) {
				i++;
				Object[] data = new Object[columns.length];
				setNowPropertyNo(rs.getString("propertyNo"));
				data[0] = checkGet(differenceKindMap.get(getQ_differenceKind()));
				String propertyno = rs.getString("propertyno").toString();
				if (propertyno.length() > 7) {
					data[1] = checkGet(propertyno.substring(0, 7)) + "-" + checkGet(propertyno.substring(7));
				} else {
					data[1] = checkGet(propertyno) ;
				}
				data[2] = checkGet(rs.getString("propertyname"));
				data[3] = checkGet(rs.getString("propertyunit"));
				data[4] = checkGet(rs.getString("limityear"));
//				System.out.println(checkGet(rs.getString("enterdate")));
				String Enterdate = "";
				if(checkGet(rs.getString("enterdate")).length() == 6){ //deprym
					Enterdate = Datetime.changeTaiwanYYMM(checkGet(rs.getString("enterdate")), "1");
					Enterdate += Datetime.getLastDayOfMonth(rs.getString("enterdate")); // 取得月結年月 最後一日
				}else{
					Enterdate = Datetime.changeTaiwanYYMMDD(checkGet(rs.getString("enterdate")), "1");
				}
				if (Enterdate.length() >= 3) {
					data[5] = Enterdate.substring(0, 3);
				} else {
					data[5] = "";
				}
				if (Enterdate.length() >= 5) {
					data[6] = Enterdate.substring(3, 5);
				} else {
					data[6] = "";
				}
				if (Enterdate.length() >= 7) {
					data[7] = Enterdate.substring(5, 7);
				} else {
					data[7] = "";
				}
				data[8] = "".equals(checkGet(rs.getString("booknotes")))?"0":"1";
				data[9] = checkGet(rs.getString("serialno"));
				data[10] = checkGet(rs.getString("add_amount")) ;
				data[11] = rs.getLong("add_bookunit") + "";
				data[12] = rs.getLong("add_bookvalue") + "";
				data[13] = checkGet(rs.getString("reduce_amount"));
				data[14] = rs.getLong("reduce_bookunit") + "";
				data[15] = rs.getLong("reduce_bookvalue") + "";
				double addAmount = rs.getString("add_amount")==null?0:rs.getDouble("add_amount"); 
				double reduceAmount = rs.getString("reduce_amount")==null?0:rs.getDouble("reduce_amount");
				//addAmount = addAmount.substring(0,(addAmount.indexOf("\n")<0?1:addAmount.indexOf("\n")));
				//reduceAmount = reduceAmount.substring(0,(reduceAmount.indexOf("\n")<0?1:reduceAmount.indexOf("\n"))); 
//				if(getNowPropertyNo().equals(getOldPropertyNo())){
//					balance_Amount = balance_Amount + addAmount - reduceAmount;
//					balance_BookValue = balance_BookValue + rs.getLong("add_BookValue") - rs.getLong("reduce_BookValue");
//				}else{
					balance_Amount = addAmount - reduceAmount;
					balance_BookValue = rs.getLong("add_BookValue") - rs.getLong("reduce_BookValue");
//				}
				if(balance_Amount < 0) balance_Amount = 0;
				if(balance_BookValue < 0) balance_BookValue = 0;
				data[16] = new Double(balance_Amount);
//				if(!"".equals(checkGet(String.valueOf(balance_Amount))))
//					data[16] = amount.format(balance_Amount).toString();
				data[17] = "";
				data[18] = checkGet(String.valueOf(balance_BookValue));
				data[19] = Common.get(rs.getString("monthdepr"));
				data[20] = Common.get(rs.getString("newaccumdepr"));
				data[21] = Common.get(rs.getString("newnetvalue"));
				rowData.addElement(data);
				setOldPropertyNo(getNowPropertyNo());
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
	
	/**
	 * 總結年度月份是否>= 10501
	 * @return
	 */
	public boolean isReportYMOver10501() {
		int reportYM = Common.getInt(this.getQ_reportYM());
		return reportYM >= 10501 ? true : false;
	}

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
		if (!"".equals(Common.get(getQ_ownership())))	//權屬
			whereCon += " and d.ownership = " + util.Common.sqlChar(getQ_ownership());
		if (!"".equals(Common.get(getQ_differenceKind())))	//財產區分別
			whereCon += " and d.differencekind = " + util.Common.sqlChar(getQ_differenceKind());
		if (!"".equals(Common.get(getQ_propertyNoS())))	//財產編號起
			whereCon += " and d.propertyno >= " + Common.sqlChar(getQ_propertyNoS());
		if (!"".equals(Common.get(getQ_propertyNoE())))	//財產編號訖
			whereCon += " and d.propertyno <= " + Common.sqlChar(getQ_propertyNoE());
		if (!"".equals(Common.get(getQ_reportYM())))	//總結年度月份
			whereCon += " and substring(d." + date + ",1,5) <= " + util.Common.sqlChar(Datetime.changeTaiwanYYMM(getQ_reportYM(), "2"));
		if (!"".equals(Common.get(getQ_verify()))) //是否入帳
			whereCon += " and d.verify = " + util.Common.sqlChar(getQ_verify());
		if (valuable && !"".equals(Common.get(getQ_valuable()))) //珍貴財產
			whereCon += " and d.valuable = " + util.Common.sqlChar(getQ_valuable());

		 
		return whereCon;
	}
	// 財產查詢
	private String querySQL1() {
		String querySQL = 
			//折舊資料
			  " select                                                                                                                                                 "
			+ " d.propertyno,                                                                                                                                          "
			+ " ISNULL(c.propertyname,'') as propertyname,                                                                                                             "
			+ " ISNULL(c.propertyunit,'') as propertyunit,                                                                                                             "
			+ " cast(isnull(c.limityear,'') as varchar) as limityear,                                                                                                  "
			+ " d.deprym as enterdate,                                                                                                                                 "
			+ " '' as entertype,                                                                                                                                       "
			+ " '' as summonsno,                                                                                                                                       "
			+ " '折舊' as booknotes,                                                                                                                                   "
			+ " '' as serialno,                                                                                                                                        "
			+ " '0' as add_amount,                                                                                                                                     "
			+ " '0' as add_bookunit,                                                                                                                                   "
			+ " '0' as add_bookvalue,                                                                                                                                  "
			+ " '0' as reduce_amount,                                                                                                                                  "
			+ " '0' as reduce_bookunit,                                                                                                                                "
			+ " SUM(case deprunitcb when 'N' then ISNULL(d.monthdepr,'0') when 'Y' then ISNULL(d.scaledmonthdepr,'0') else '0' end) as reduce_bookvalue                "
			+ " ,SUM(case deprunitcb when 'N' then ISNULL(d.monthdepr, '0') when 'Y' then ISNULL(d.scaledmonthdepr, '0') else '0' end) as monthdepr "
			+ " ,SUM(case deprunitcb when 'N' then ISNULL(d.newaccumdepr, '0') when 'Y' then ISNULL(d.scalednewaccumdepr, '0') else '0' end) as newaccumdepr "
			+ " ,SUM(case deprunitcb when 'N' then ISNULL(d.newnetvalue, '0') when 'Y' then ISNULL(d.scalednewnetvalue, '0') else '0' end) as newnetvalue "
			+ " from                                                                                                                                                   "
			+ " UNTDP_MONTHDEPR d                                                                                                                                      "
			+ " left outer join SYSPK_PROPERTYCODE c on                                                                                                                "
			+ " c.enterorg in (d.enterorg,'000000000A')                                                                                                                "
			+ " and d.propertyno = c.propertyno                                                                                                                        "
			+ " where 1 = 1 " + whereCon("enterdate",true)
			+ " group by d.enterorg,d.differencekind,d.propertyno,ISNULL(c.propertyname,''),ISNULL(c.propertyunit,''),cast(isnull(c.limityear,'') as varchar),d.deprym "
			+ " union all                                                                                                  "
			+ "                                                                                                            "
			//--土地增加	
			+ " select                                                                                                     "
			+ " d.propertyno,                                                                                              "
			+ " ISNULL(c.propertyname,'') propertyname,                                                                    "
			+ " '平方公尺' as propertyunit,                                                                                "
			+ " '' as limityear,                                                                                           "
			+ " d.enterdate as enterdate,                                                                                  "
			+ " '' as entertype,                                                                                           "
			+ " m.summonsno as summonsno,                                                                                  "
			+ " isnull(s.signname,'') as booknotes,                                                                        "
			+ " d.serialno,                                                                                                "
			+ " d.originalholdarea as add_amount,                                                                          "
			+ " d.originalunit as add_bookunit,                                                                            "
			+ " d.originalbv as add_bookvalue,                                                                             "
			+ " '0' as reduce_amount,                                                                                      "
			+ " '0' as reduce_bookunit,                                                                                    "
			+ " '0' as reduce_bookvalue                                                                                    "
			+ " ,'0' as monthdepr, '0' as newaccumdepr, '0' as newnetvalue " 
			+ " from                                                                                                       "
			+ " UNTLA_ADDPROOF m                                                                                           "
			+ " inner join UNTLA_LAND d on                                                                                 "
			+ " m.enterorg = d.enterorg                                                                                    "
			+ " and m.ownership = d.ownership                                                                              "
			+ " and m.caseno = d.caseno                                                                                    "
			+ " and m.differencekind = d.differencekind                                                                    "
			+ " left outer join SYSPK_PROPERTYCODE c on                                                                    "
			+ " c.enterorg in (d.enterorg,'000000000A')                                                                    "
			+ " and d.propertyno = c.propertyno                                                                            "
			+ " left outer join SYSCA_SIGN s on d.signno = s.signno                                                        "
			+ " where 1 = 1 " + whereCon("enterdate",true)
			+ " union all                                                                                                  "
			+ "                                                                                                            "
			//--土地增減值
			+ " select                                                                                                     "
			+ " d.propertyno,                                                                                              "
			+ " ISNULL(c.propertyname,'') propertyname,                                                                    "
			+ " '平方公尺' as propertyunit,                                                                                "
			+ " '' as limityear,                                                                                           "
			+ " d.adjustdate as enterdate,                                                                                 "
			+ " '' as entertype,                                                                                           "
			+ " m.summonsno as summonsno,                                                                                  "
			+ " isnull(s.signname,'') as booknotes,                                                                        "
			+ " d.serialno,                                                                                                "
			+ " case when d.adjustholdarea > 0 then d.adjustholdarea else 0 end as add_amount,                             "
			+ " d.addbookunit as add_bookunit,                                                                             "
			+ " d.addbookvalue as add_bookvalue,                                                                           "
			+ " case when d.adjustholdarea < 0 then -1 * d.adjustholdarea else 0 end as reduce_amount,                     "
			+ " d.reducebookunit as reduce_bookunit,                                                                       "
			+ " d.reducebookvalue as reduce_bookvalue                                                                      "
			+ " ,'0' as monthdepr, '0' as newaccumdepr, '0' as newnetvalue "
			+ " from                                                                                                       "
			+ " UNTLA_ADJUSTPROOF m                                                                                        "
			+ " inner join UNTLA_ADJUSTDETAIL d on                                                                         "
			+ " m.enterorg = d.enterorg                                                                                    "
			+ " and m.ownership = d.ownership                                                                              "
			+ " and m.caseno = d.caseno                                                                                    "
			+ " and m.differencekind = d.differencekind                                                                    "
			+ " left outer join SYSPK_PROPERTYCODE c on                                                                    "
			+ " c.enterorg in (d.enterorg,'000000000A')                                                                    "
			+ " and d.propertyno = c.propertyno                                                                            "
			+ " left outer join SYSCA_SIGN s on d.signno = s.signno                                                        "
			+ " where 1 = 1 " + whereCon("adjustdate",true)
			+ " union all                                                                                                  "
			+ "                                                                                                            "
			//--土地減損
			+ " select                                                                                                     "
			+ " d.propertyno,                                                                                              "
			+ " ISNULL(c.propertyname,'') propertyname,                                                                    "
			+ " '平方公尺' as propertyunit,                                                                                "
			+ " '' as limityear,                                                                                           "
			+ " d.reducedate as enterdate,                                                                                 "
			+ " '' as entertype,                                                                                           "
			+ " m.summonsno as summonsno,                                                                                  "
			+ " isnull(s.signname,'') as booknotes,                                                                        "
			+ " d.serialno,                                                                                                "
			+ " '0' as add_amount,                                                                                         "
			+ " '0' as add_bookunit,                                                                                       "
			+ " '0' as add_bookvalue,                                                                                      "
			+ " d.holdarea as reduce_amount,                                                                               "
			+ " cast(d.bookunit AS varchar) as reduce_bookunit,                                                            "
			+ " d.bookvalue as reduce_bookvalue                                                                            "
			+ " ,'0' as monthdepr, '0' as newaccumdepr, '0' as newnetvalue "
			+ " from                                                                                                       "
			+ " UNTLA_REDUCEPROOF m                                                                                        "
			+ " inner join UNTLA_REDUCEDETAIL d on                                                                         "
			+ " m.enterorg = d.enterorg                                                                                    "
			+ " and m.ownership = d.ownership                                                                              "
			+ " and m.caseno = d.caseno                                                                                    "
			+ " and m.differencekind = d.differencekind                                                                    "
			+ " left outer join SYSPK_PROPERTYCODE c on                                                                    "
			+ " c.enterorg in (d.enterorg,'000000000A')                                                                    "
			+ " and d.propertyno = c.propertyno                                                                            "
			+ " left outer join SYSCA_SIGN s on d.signno = s.signno                                                        "
			+ " where 1 = 1 " + whereCon("reducedate",true)
			+ " union all                                                                                                  "
			+ "                                                                                                            "
			//--土地改良物增加
			+ " select                                                                                                     "
			+ " d.propertyno,                                                                                              "
			+ " ISNULL(c.propertyname,'') propertyname,                                                                    "
			+ " ISNULL(c.propertyunit,'') as propertyunit,                                                                 "
			+ " cast(isnull(c.limityear,'') as varchar) as limityear,                                                      "
			+ " d.enterdate as enterdate,                                                                                  "
			+ " '' as entertype,                                                                                           "
			+ " m.summonsno as summonsno,                                                                                  "
			+ " isnull(c.material,'') as booknotes,                                                                        "
			+ " d.serialno,                                                                                                "
			+ " '1' as add_amount,                                                                                         "
			+ " '0' as add_bookunit,                                                                                       "
			+ " d.originalbv as add_bookvalue,                                                                             "
			+ " '0' as reduce_amount,                                                                                      "
			+ " '0' as reduce_bookunit,                                                                                    "
			+ " '0' as reduce_bookvalue                                                                                    "
			+ " ,'0' as monthdepr, '0' as newaccumdepr, '0' as newnetvalue "
			+ " from                                                                                                       "
			+ " UNTRF_ADDPROOF m                                                                                           "
			+ " inner join UNTRF_ATTACHMENT d on                                                                           "
			+ " m.enterorg = d.enterorg                                                                                    "
			+ " and m.ownership = d.ownership                                                                              "
			+ " and m.caseno = d.caseno                                                                                    "
			+ " and m.differencekind = d.differencekind                                                                    "
			+ " left outer join SYSPK_PROPERTYCODE c on                                                                    "
			+ " c.enterorg in (d.enterorg,'000000000A')                                                                    "
			+ " and d.propertyno = c.propertyno                                                                            "
			+ " where 1 = 1 " + whereCon("enterdate",true)
			+ " union all                                                                                                  "
			+ "                                                                                                            "
			//--土地改良物增減值
			+ " select                                                                                                     "
			+ " d.propertyno,                                                                                              "
			+ " ISNULL(c.propertyname,'') propertyname,                                                                    "
			+ " ISNULL(c.propertyunit,'') as propertyunit,                                                                 "
			+ " cast(isnull(c.limityear,'') as varchar) as limityear,                                                      "
			+ " d.adjustdate as enterdate,                                                                                 "
			+ " '' as entertype,                                                                                           "
			+ " m.summonsno as summonsno,                                                                                  "
			+ " isnull(c.material,'') as booknotes,                                                                        "
			+ " d.serialno,                                                                                                "
			+ " '0' as add_amount,                                                                                         "
			+ " '0' as add_bookunit,                                                                                       "
			+ " case d.differencekind when '110' then (case when substring(d.adjustdate, 1,4) >= 2016 then d.addnetvalue else d.addbookvalue end) else d.addnetvalue end as add_bookvalue,              "
			+ " '0' as reduce_amount,                                                                                      "
			+ " '0' as reduce_bookunit,                                                                                    "
			+ " case d.differencekind when '110' then (case when substring(d.adjustdate, 1,4) >= 2016 then d.reducenetvalue else d.reducebookvalue end) else d.reducenetvalue end as reduce_bookvalue      "
			+ " ,'0' as monthdepr, '0' as newaccumdepr, '0' as newnetvalue "
			+ " from                                                                                                       "
			+ " UNTRF_ADJUSTPROOF m                                                                                        "
			+ " inner join UNTRF_ADJUSTDETAIL d on                                                                         "
			+ " m.enterorg = d.enterorg                                                                                    "
			+ " and m.ownership = d.ownership                                                                              "
			+ " and m.caseno = d.caseno                                                                                    "
			+ " and m.differencekind = d.differencekind                                                                    "
			+ " left outer join SYSPK_PROPERTYCODE c on                                                                    "
			+ " c.enterorg in (d.enterorg,'000000000A')                                                                    "
			+ " and d.propertyno = c.propertyno                                                                            "
			+ " where 1 = 1 " + whereCon("adjustdate",true)
			+ " union all                                                                                                  "
			+ "                                                                                                            "
			//--土地改良物減損
			+ " select                                                                                                     "
			+ " d.propertyno,                                                                                              "
			+ " ISNULL(c.propertyname,'') propertyname,                                                                    "
			+ " ISNULL(c.propertyunit,'') as propertyunit,                                                                 "
			+ " cast(isnull(c.limityear,'') as varchar) as limityear,                                                      "
			+ " d.reducedate as enterdate,                                                                                 "
			+ " '' as entertype,                                                                                           "
			+ " m.summonsno as summonsno,                                                                                  "
			+ " isnull(c.material,'') as booknotes,                                                                        "
			+ " d.serialno,                                                                                                "
			+ " '0' as add_amount,                                                                                         "
			+ " '0' as add_bookunit,                                                                                       "
			+ " '0' as add_bookvalue,                                                                                      "
			+ " '1' as reduce_amount,                                                                                      "
			+ " '0' as reduce_bookunit,                                                                                    "
			+ " case d.differencekind when '110' then (case when substring(d.reducedate, 1,4) >= 2016 then d.netvalue else d.bookvalue end) else d.netvalue end as reduce_bookvalue                  "
			+ " ,'0' as monthdepr, '0' as newaccumdepr, '0' as newnetvalue "
			+ " from                                                                                                       "
			+ " UNTRF_REDUCEPROOF m                                                                                        "
			+ " inner join UNTRF_REDUCEDETAIL d on                                                                         "
			+ " m.enterorg = d.enterorg                                                                                    "
			+ " and m.ownership = d.ownership                                                                              "
			+ " and m.caseno = d.caseno                                                                                    "
			+ " and m.differencekind = d.differencekind                                                                    "
			+ " left outer join SYSPK_PROPERTYCODE c on                                                                    "
			+ " c.enterorg in (d.enterorg,'000000000A')                                                                    "
			+ " and d.propertyno = c.propertyno                                                                            "
			+ " where 1 = 1 " + whereCon("reducedate",true)
			+ "                                                                                                            "
			+ " union all                                                                                                  "
			+ "                                                                                                            "
			//--建物增加
			+ " select                                                                                                     "
			+ " d.propertyno,                                                                                              "
			+ " ISNULL(c.propertyname,'') propertyname,                                                                    "
			+ " ISNULL(c.propertyunit,'') as propertyunit,                                                                 "
			+ " cast(isnull(c.limityear,'') as varchar) as limityear,                                                      "
			+ " d.enterdate as enterdate,                                                                                  "
			+ " '' as entertype,                                                                                           "
			+ " m.summonsno as summonsno,                                                                                  "
			+ " isnull(s.signname,'') + d.doorplate4 as booknotes,                                                         "
			+ " d.serialno,                                                                                                "
			+ " '1' as add_amount,                                                                                         "
			+ " '0' as add_bookunit,                                                                                       "
			+ " d.originalbv as add_bookvalue,                                                                             "
			+ " '0' as reduce_amount,                                                                                      "
			+ " '0' as reduce_bookunit,                                                                                    "
			+ " '0' as reduce_bookvalue                                                                                    "
			+ " ,'0' as monthdepr, '0' as newaccumdepr, '0' as newnetvalue "
			+ " from                                                                                                       "
			+ " UNTBU_ADDPROOF m                                                                                           "
			+ " inner join UNTBU_BUILDING d on                                                                             "
			+ " m.enterorg = d.enterorg                                                                                    "
			+ " and m.ownership = d.ownership                                                                              "
			+ " and m.caseno = d.caseno                                                                                    "
			+ " and m.differencekind = d.differencekind                                                                    "
			+ " left outer join SYSPK_PROPERTYCODE c on                                                                    "
			+ " c.enterorg in (d.enterorg,'000000000A')                                                                    "
			+ " and d.propertyno = c.propertyno                                                                            "
			+ " left outer join SYSCA_SIGN s on d.signno = s.signno                                                        "
			+ " where 1 = 1 " + whereCon("enterdate",true)
			+ " union all                                                                                                  "
			+ "                                                                                                            "
			//--建物增減值
			+ " select                                                                                                     "
			+ " d.propertyno,                                                                                              "
			+ " ISNULL(c.propertyname,'') propertyname,                                                                    "
			+ " ISNULL(c.propertyunit,'') as propertyunit,                                                                 "
			+ " cast(isnull(c.limityear,'') as varchar) as limityear,                                                      "
			+ " d.adjustdate as enterdate,                                                                                 "
			+ " '' as entertype,                                                                                           "
			+ " m.summonsno as summonsno,                                                                                  "
			+ " isnull(s.signname,'') + isnull(b.doorplate4,'') as booknotes,                                              "
			+ " d.serialno,                                                                                                "
			+ " '0' as add_amount,                                                                                         "
			+ " '0' as add_bookunit,                                                                                       "
			+ " case d.differencekind when '110' then (case when substring(d.adjustdate, 1,4) >= 2016 then d.addnetvalue else d.addbookvalue end) else d.addnetvalue end as add_bookvalue, "
			+ " '0' as reduce_amount,                                                                                      "
			+ " '0' as reduce_bookunit,                                                                                    "
			+ " case d.differencekind when '110' then (case when substring(d.adjustdate, 1,4) >= 2016 then d.reducenetvalue else d.reducebookvalue end) else d.reducenetvalue end as reduce_bookvalue "
			+ " ,'0' as monthdepr, '0' as newaccumdepr, '0' as newnetvalue "
			+ " from                                                                                                       "
			+ " UNTBU_ADJUSTPROOF m                                                                                        "
			+ " inner join UNTBU_ADJUSTDETAIL d on                                                                         "
			+ " m.enterorg = d.enterorg                                                                                    "
			+ " and m.ownership = d.ownership                                                                              "
			+ " and m.caseno = d.caseno                                                                                    "
			+ " and m.differencekind = d.differencekind                                                                    "
			+ " left outer join SYSPK_PROPERTYCODE c on                                                                    "
			+ " c.enterorg in (d.enterorg,'000000000A')                                                                    "
			+ " and d.propertyno = c.propertyno                                                                            "
			+ " left outer join SYSCA_SIGN s on d.signno = s.signno                                                        "
			+ " left outer join UNTBU_BUILDING b on                                                                        "
			+ " d.enterorg = b.enterorg                                                                                    "
			+ " and d.ownership = b.ownership                                                                              "
			+ " and d.differencekind = b.differencekind                                                                    "
			+ " and d.propertyno = b.propertyno                                                                            "
			+ " and d.serialno = b.serialno                                                                                "
			+ " where 1 = 1 " + whereCon("adjustdate",true)
			+ " union all                                                                                                  "
			+ "                                                                                                            "
			//--建物減損
			+ " select                                                                                                     "
			+ " d.propertyno,                                                                                              "
			+ " ISNULL(c.propertyname,'') propertyname,                                                                    "
			+ " ISNULL(c.propertyunit,'') as propertyunit,                                                                 "
			+ " cast(isnull(c.limityear,'') as varchar) as limityear,                                                      "
			+ " d.reducedate as enterdate,                                                                                 "
			+ " '' as entertype,                                                                                           "
			+ " m.summonsno as summonsno,                                                                                  "
			+ " isnull(s.signname,'') + d.doorplate4 as booknotes,                                                         "
			+ " d.serialno,                                                                                                "
			+ " '0' as add_amount,                                                                                         "
			+ " '0' as add_bookunit,                                                                                       "
			+ " '0' as add_bookvalue,                                                                                      "
			+ " '1' as reduce_amount,                                                                                      "
			+ " '0' as reduce_bookunit,                                                                                    "
			+ " case d.differencekind when '110' then (case when substring(d.reducedate,1,4) >=2016 then d.netvalue else d.bookvalue end) else d.netvalue end as reduce_bookvalue                  "
			+ " ,'0' as monthdepr, '0' as newaccumdepr, '0' as newnetvalue "
			+ " from                                                                                                       "
			+ " UNTBU_REDUCEPROOF m                                                                                        "
			+ " inner join UNTBU_REDUCEDETAIL d on                                                                         "
			+ " m.enterorg = d.enterorg                                                                                    "
			+ " and m.ownership = d.ownership                                                                              "
			+ " and m.caseno = d.caseno                                                                                    "
			+ " and m.differencekind = d.differencekind                                                                    "
			+ " left outer join SYSPK_PROPERTYCODE c on                                                                    "
			+ " c.enterorg in (d.enterorg,'000000000A')                                                                    "
			+ " and d.propertyno = c.propertyno                                                                            "
			+ " left outer join SYSCA_SIGN s on d.signno = s.signno                                                        "
			+ " where 1 = 1 " + whereCon("reducedate",true)
			+ " union all                                                                                                  "
			+ "                                                                                                            "
			//--動產增加
			+ " select                                                                                                     "
			+ " d.propertyno,                                                                                              "
			+ " ISNULL(c.propertyname,'') propertyname,                                                                    "
			+ " ISNULL(c.propertyunit,'') as propertyunit,                                                                 "
			+ " cast(isnull(c.limityear,'') as varchar) as limityear,                                                      "
			+ " d.enterdate as enterdate,                                                                                  "
			+ " '' as entertype,                                                                                           "
			+ " m.summonsno as summonsno,                                                                                  "
			+ " d.specification + '/' + d.nameplate as booknotes,                                                          "
			+ " case when d.serialnos = d.serialnoe then d.serialnos else d.serialnos + '-'  + d.serialnoe end as serialno,"
			+ " d.originalamount as add_amount,                                                                            "
			+ " d.originalunit as add_bookunit,                                                                            "
			+ " d.originalbv as add_bookvalue,                                                                             "
			+ " '0' as reduce_amount,                                                                                      "
			+ " '0' as reduce_bookunit,                                                                                    "
			+ " '0' as reduce_bookvalue                                                                                    "
			+ " ,'0' as monthdepr, '0' as newaccumdepr, '0' as newnetvalue "
			+ " from                                                                                                       "
			+ " UNTMP_ADDPROOF m                                                                                           "
			+ " inner join UNTMP_MOVABLE d on                                                                              "
			+ " m.enterorg = d.enterorg                                                                                    "
			+ " and m.ownership = d.ownership                                                                              "
			+ " and m.caseno = d.caseno                                                                                    "
			+ " and m.differencekind = d.differencekind                                                                    "
			+ " left outer join SYSPK_PROPERTYCODE c on                                                                    "
			+ " c.enterorg in (d.enterorg,'000000000A')                                                                    "
			+ " and d.propertyno = c.propertyno                                                                            "
			+ " where 1 = 1 " + whereCon("enterdate",true)
			+ " union all                                                                                                  "
			+ "                                                                                                            "
			//--動產增減值
			+ " select                                                                                                     "
			+ " d.propertyno,                                                                                              "
			+ " ISNULL(c.propertyname,'') propertyname,                                                                    "
			+ " ISNULL(c.propertyunit,'') as propertyunit,                                                                 "
			+ " cast(isnull(c.limityear,'') as varchar) as limityear,                                                      "
			+ " d.adjustdate as enterdate,                                                                                 "
			+ " '' as entertype,                                                                                           "
			+ " m.summonsno as summonsno,                                                                                  "
			+ " isnull(mo.specification,'') + '/' + isnull(mo.nameplate,'') as booknotes,                                  "
			+ " d.serialno,                                                                                                "
			+ " '0' as add_amount,                                                                                         "
			+ " case d.differencekind when '110' then (case when substring(d.adjustdate,1,4)>=2016 then d.addnetunit else d.addbookunit end) else d.addnetunit end as add_bookunit, "
			+ " case d.differencekind when '110' then (case when substring(d.adjustdate,1,4)>=2016 then d.addnetvalue else d.addbookvalue end) else d.addnetvalue end as add_bookvalue, "
			+ " '0' as reduce_amount,                                                                                      "
			+ " case d.differencekind when '110' then (case when substring(d.adjustdate,1,4)>=2016 then d.reducenetunit else d.reducebookunit end) else d.reducenetunit end as reduce_bookunit,        "
			+ " case d.differencekind when '110' then (case when substring(d.adjustdate,1,4)>=2016 then d.reducenetvalue else d.reducebookvalue end) else d.reducenetvalue end as reduce_bookvalue      "
			+ " ,'0' as monthdepr, '0' as newaccumdepr, '0' as newnetvalue "
			+ " from                                                                                                       "
			+ " UNTMP_ADJUSTPROOF m                                                                                        "
			+ " inner join UNTMP_ADJUSTDETAIL d on                                                                         "
			+ " m.enterorg = d.enterorg                                                                                    "
			+ " and m.ownership = d.ownership                                                                              "
			+ " and m.caseno = d.caseno                                                                                    "
			+ " and m.differencekind = d.differencekind                                                                    "
			+ " left outer join SYSPK_PROPERTYCODE c on                                                                    "
			+ " c.enterorg in (d.enterorg,'000000000A')                                                                    "
			+ " and d.propertyno = c.propertyno                                                                            "
			+ " left outer join UNTMP_MOVABLE mo on                                                                        "
			+ " d.enterorg = mo.enterorg                                                                                   "
			+ " and d.ownership = mo.ownership                                                                             "
			+ " and d.differencekind = mo.differencekind                                                                   "
			+ " and d.propertyno = mo.propertyno                                                                           "
			+ " and d.lotno = mo.lotno                                                                                     "
			+ " where 1 = 1 " + whereCon("adjustdate",true)
			+ " union all                                                                                                  "
			+ "                                                                                                            "
			//--動產減損
			+ " select                                                                                                     "
			+ " d.propertyno,                                                                                              "
			+ " ISNULL(c.propertyname,'') propertyname,                                                                    "
			+ " ISNULL(c.propertyunit,'') as propertyunit,                                                                 "
			+ " cast(isnull(c.limityear,'') as varchar) as limityear,                                                      "
			+ " d.reducedate as enterdate,                                                                                 "
			+ " '' as entertype,                                                                                           "
			+ " m.summonsno as summonsno,                                                                                  "
			+ " d.specification + '/' + d.nameplate as booknotes,                                                          "
			+ " d.serialno,                                                                                                "
			+ " '0' as add_amount,                                                                                         "
			+ " '0' as add_bookunit,                                                                                       "
			+ " '0' as add_bookvalue,                                                                                      "
			+ " d.adjustbookamount as reduce_amount,                                                                       "
			+ " case d.differencekind when '110' then (case when substring(d.reducedate,1,4) >=2016 then d.adjustnetunit else d.adjustbookunit end) else d.adjustnetunit end as reduce_bookunit,        "
			+ " case d.differencekind when '110' then (case when substring(d.reducedate,1,4) >=2016 then d.adjustnetvalue else d.adjustbookvalue end) else d.adjustnetvalue end as reduce_bookvalue      "
			+ " ,'0' as monthdepr, '0' as newaccumdepr, '0' as newnetvalue "
			+ " from                                                                                                       "
			+ " UNTMP_REDUCEPROOF m                                                                                        "
			+ " inner join UNTMP_REDUCEDETAIL d on                                                                         "
			+ " m.enterorg = d.enterorg                                                                                    "
			+ " and m.ownership = d.ownership                                                                              "
			+ " and m.caseno = d.caseno                                                                                    "
			+ " and m.differencekind = d.differencekind                                                                    "
			+ " left outer join SYSPK_PROPERTYCODE c on                                                                    "
			+ " c.enterorg in (d.enterorg,'000000000A')                                                                    "
			+ " and d.propertyno = c.propertyno                                                                            "
			+ " where 1 = 1 " + whereCon("reducedate",true)
			+ " union all                                                                                                  "
			+ "                                                                                                            "
			//--有價證券增加
			+ " select                                                                                                     "
			+ " d.propertyno,                                                                                              "
			+ " ISNULL(c.propertyname,'') propertyname,                                                                    "
			+ " ISNULL(c.propertyunit,'') as propertyunit,                                                                 "
			+ " '' as limityear,                                                                                           "
			+ " d.enterdate as enterdate,                                                                                  "
			+ " '' as entertype,                                                                                           "
			+ " d.summonsno as summonsno,                                                                                  "
			+ " d.securityname as booknotes,                                                                               "
			+ " d.serialno as serialno,                                                                                    "
			+ " d.originalamount as add_amount,                                                                            "
			+ " '0' as add_bookunit,                                                                                       "
			+ " d.originalbv as add_bookvalue,                                                                             "
			+ " '0' as reduce_amount,                                                                                      "
			+ " '0' as reduce_bookunit,                                                                                    "
			+ " '0' as reduce_bookvalue                                                                                    "
			+ " ,'0' as monthdepr, '0' as newaccumdepr, '0' as newnetvalue "
			+ " from                                                                                                       "
			+ " UNTVP_ADDPROOF d                                                                                           "
			+ " left outer join SYSPK_PROPERTYCODE c on                                                                    "
			+ " c.enterorg in (d.enterorg,'000000000A')                                                                    "
			+ " and d.propertyno = c.propertyno                                                                            "
			+ " where 1 = 1 " + whereCon("enterdate",false)
			+ " union all                                                                                                  "
			+ "                                                                                                            "
			//--有價證券增減值
			+ " select                                                                                                     "
			+ " d.propertyno,                                                                                              "
			+ " ISNULL(c.propertyname,'') propertyname,                                                                    "
			+ " ISNULL(c.propertyunit,'') as propertyunit,                                                                 "
			+ " '' as limityear,                                                                                           "
			+ " d.adjustdate as enterdate,                                                                                 "
			+ " '' as entertype,                                                                                           "
			+ " d.summonsno as summonsno,                                                                                  "
			+ " d.securityname as booknotes,                                                                               "
			+ " d.serialno,                                                                                                "
			+ " d.addbookamount as add_amount,                                                                             "
			+ " '0' as add_bookunit,                                                                                       "
			+ " d.addbookvalue as add_bookvalue,                                                                           "
			+ " d.reducebookamount as reduce_amount,                                                                       "
			+ " '0' reduce_bookunit,                                                                                       "
			+ " d.reducebookvalue as reduce_bookvalue                                                                      "
			+ " ,'0' as monthdepr, '0' as newaccumdepr, '0' as newnetvalue "
			+ " from                                                                                                       "
			+ " UNTVP_ADJUSTPROOF d                                                                                        "
			+ " left outer join SYSPK_PROPERTYCODE c on                                                                    "
			+ " c.enterorg in (d.enterorg,'000000000A')                                                                    "
			+ " and d.propertyno = c.propertyno                                                                            "
			+ " where 1 = 1 " + whereCon("adjustdate",false)
			+ " union all                                                                                                  "
			+ "                                                                                                            "
			//--有價證券減損
			+ " select                                                                                                     "
			+ " d.propertyno,                                                                                              "
			+ " ISNULL(c.propertyname,'') propertyname,                                                                    "
			+ " ISNULL(c.propertyunit,'') as propertyunit,                                                                 "
			+ " '' as limityear,                                                                                           "
			+ " d.adjustdate as enterdate,                                                                                 "
			+ " '' as entertype,                                                                                           "
			+ " d.summonsno as summonsno,                                                                                  "
			+ " d.securityname as booknotes,                                                                               "
			+ " d.serialno,                                                                                                "
			+ " '0' as add_amount,                                                                                         "
			+ " '0' as add_bookunit,                                                                                       "
			+ " '0' as add_bookvalue,                                                                                      "
			+ " d.bookamount as reduce_amount,                                                                             "
			+ " '0' as reduce_bookunit,                                                                                    "
			+ " d.bookvalue as reduce_bookvalue                                                                            "
			+ " ,'0' as monthdepr, '0' as newaccumdepr, '0' as newnetvalue "
			+ " from                                                                                                       "
			+ " UNTVP_REDUCEPROOF d                                                                                        "
			+ " left outer join SYSPK_PROPERTYCODE c on                                                                    "
			+ " c.enterorg in (d.enterorg,'000000000A')                                                                    "
			+ " and d.propertyno = c.propertyno                                                                            "
			+ " where 1 = 1 " + whereCon("adjustdate",false)
			+ " union all                                                                                                  "
			+ "                                                                                                            "
			//--權利增加
			+ " select                                                                                                     "
			+ " d.propertyno,                                                                                              "
			+ " ISNULL(c.propertyname,'') propertyname,                                                                    "
			+ " ISNULL(c.propertyunit,'') as propertyunit,                                                                 "
			+ " '' as limityear,                                                                                           "
			+ " d.enterdate as enterdate,                                                                                  "
			+ " '' as entertype,                                                                                           "
			+ " d.summonsno as summonsno,                                                                                  "
			+ " ''as booknotes,                                                                                            "
			+ " d.serialno as serialno,                                                                                    "
			+ " '1' as add_amount,                                                                                         "
			+ " '0' as add_bookunit,                                                                                       "
			+ " d.originalbv as add_bookvalue,                                                                             "
			+ " '0' as reduce_amount,                                                                                      "
			+ " '0' as reduce_bookunit,                                                                                    "
			+ " '0' as reduce_bookvalue                                                                                    "
			+ " ,'0' as monthdepr, '0' as newaccumdepr, '0' as newnetvalue "
			+ " from                                                                                                       "
			+ " UNTRT_ADDPROOF d                                                                                           "
			+ " left outer join SYSPK_PROPERTYCODE c on                                                                    "
			+ " c.enterorg in (d.enterorg,'000000000A')                                                                    "
			+ " and d.propertyno = c.propertyno                                                                            "
			+ " where 1 = 1 " + whereCon("enterdate",false)
			+ " union all                                                                                                  "
			+ "                                                                                                            "
			//--權利增減值
			+ " select                                                                                                     "
			+ " d.propertyno,                                                                                              "
			+ " ISNULL(c.propertyname,'') propertyname,                                                                    "
			+ " ISNULL(c.propertyunit,'') as propertyunit,                                                                 "
			+ " '' as limityear,                                                                                           "
			+ " d.adjustdate as enterdate,                                                                                 "
			+ " '' as entertype,                                                                                           "
			+ " d.summonsno as summonsno,                                                                                  "
			+ " '' as booknotes,                                                                                           "
			+ " d.serialno,                                                                                                "
			+ " '0' as add_amount,                                                                                         "
			+ " '0' as add_bookunit,                                                                                       "
			+ " case d.differencekind when '110' then (case when substring(d.adjustdate,1,4)>=2016 then d.addnetvalue else d.addbookvalue end) else d.addnetvalue end as add_bookvalue, "
			+ " '0' as reduce_amount,                                                                                      "
			+ " '0' reduce_bookunit,                                                                                       "
			+ " case d.differencekind when '110' then (case when substring(d.adjustdate,1,4)>=2016 then d.reducenetvalue else d.reducebookvalue end) else d.reducenetvalue end as reduce_bookvalue  "
			+ " ,'0' as monthdepr, '0' as newaccumdepr, '0' as newnetvalue "
			+ " from                                                                                                       "
			+ " UNTRT_ADJUSTPROOF d                                                                                        "
			+ " left outer join SYSPK_PROPERTYCODE c on                                                                    "
			+ " c.enterorg in (d.enterorg,'000000000A')                                                                    "
			+ " and d.propertyno = c.propertyno                                                                            "
			+ " where 1 = 1 " + whereCon("adjustdate",false)
			+ " union all                                                                                                  "
			+ "                                                                                                            "
			//--權利減損
			+ " select                                                                                                     "
			+ " d.propertyno,                                                                                              "
			+ " ISNULL(c.propertyname,'') propertyname,                                                                    "
			+ " ISNULL(c.propertyunit,'') as propertyunit,                                                                 "
			+ " '' as limityear,                                                                                           "
			+ " d.reducedate as enterdate,                                                                                 "
			+ " '' as entertype,                                                                                           "
			+ " d.summonsno as summonsno,                                                                                  "
			+ " '' as booknotes,                                                                                           "
			+ " d.serialno,                                                                                                "
			+ " '0' as add_amount,                                                                                         "
			+ " '0' as add_bookunit,                                                                                       "
			+ " '0' as add_bookvalue,                                                                                      "
			+ " '0' as reduce_amount,                                                                                      "
			+ " '0' as reduce_bookunit,                                                                                    "
			+ " case d.differencekind when '110' then (case when substring(d.reducedate,1,4)>=2016 then d.netvalue else d.bookvalue end) else d.netvalue end as reduce_bookvalue                                                                            "
			+ " ,'0' as monthdepr, '0' as newaccumdepr, '0' as newnetvalue "
			+ " from                                                                                                       "
			+ " UNTRT_REDUCEPROOF d                                                                                        "
			+ " left outer join SYSPK_PROPERTYCODE c on                                                                    "
			+ " c.enterorg in (d.enterorg,'000000000A')                                                                    "
			+ " and d.propertyno = c.propertyno                                                                            "
			+ " where 1 = 1 " + whereCon("reducedate",false);
		return querySQL;
	}
	
	// 物品查詢
	private String querySQL2() {
		String querySQL =
			//物品增加
			  " select                                                                                                      "
			+ " d.propertyno,                                                                                               "
			+ " ISNULL(c.propertyname,'') propertyname,                                                                     "
			+ " ISNULL(c.propertyunit,'') as propertyunit,                                                                  "
			+ " cast(isnull(c.limityear,'') as varchar) as limityear,                                                       "
			+ " d.enterdate as enterdate,                                                                                   "
			+ " '' as entertype,                                                                                            "
			+ " m.summonsno as summonsno,                                                                                   "
			+ " d.specification + '/' + d.nameplate as booknotes,                                                           "
			+ " case when d.serialnos = d.serialnoe then d.serialnos else d.serialnos + '-'  + d.serialnoe end as serialno, "
			+ " d.originalamount as add_amount,                                                                             "
			+ " d.originalunit as add_bookunit,                                                                             "
			+ " d.originalbv as add_bookvalue,                                                                              "
			+ " '0' as reduce_amount,                                                                                       "
			+ " '0' as reduce_bookunit,                                                                                     "
			+ " '0' as reduce_bookvalue                                                                                     "
			+ " ,'0' as monthdepr, '0' as newaccumdepr, '0' as newnetvalue " 
			+ " from                                                                                                        "
			+ " UNTNE_ADDPROOF m                                                                                            "
			+ " inner join UNTNE_NONEXP d on                                                                                "
			+ " m.enterorg = d.enterorg                                                                                     "
			+ " and m.ownership = d.ownership                                                                               "
			+ " and m.caseno = d.caseno                                                                                     "
			+ " and m.differencekind = d.differencekind                                                                     "
			+ " left outer join SYSPK_PROPERTYCODE2 c on                                                                    "
			+ " c.enterorg in (d.enterorg,'000000000A')                                                                     "
			+ " and d.propertyno = c.propertyno                                                                             "
			+ " where 1 = 1 " + whereCon("enterdate",true)
			+ " union all                                                                                                   "
			+ "                                                                                                             "
			//物品增減值
			+ " select                                                                                                      "
			+ " d.propertyno,                                                                                               "
			+ " ISNULL(c.propertyname,'') propertyname,                                                                     "
			+ " ISNULL(c.propertyunit,'') as propertyunit,                                                                  "
			+ " cast(isnull(c.limityear,'') as varchar) as limityear,                                                       "
			+ " d.adjustdate as enterdate,                                                                                  "
			+ " '' as entertype,                                                                                            "
			+ " m.summonsno as summonsno,                                                                                   "
			+ " isnull(mo.specification,'') + '/' + isnull(mo.nameplate,'') as booknotes,                                   "
			+ " d.serialno,                                                                                                 "
			+ " '0' as add_amount,                                                                                          "
			+ " d.addbookunit as add_bookunit,                                                                              "
			+ " d.addbookvalue as add_bookvalue,                                                                            "
			+ " '0' as reduce_amount,                                                                                       "
			+ " d.reducebookunit as reduce_bookunit,                                                                        "
			+ " d.reducebookvalue as reduce_bookvalue                                                                       "
			+ " ,'0' as monthdepr, '0' as newaccumdepr, '0' as newnetvalue " 
			+ " from                                                                                                        "
			+ " UNTNE_ADJUSTPROOF m                                                                                         "
			+ " inner join UNTNE_ADJUSTDETAIL d on                                                                          "
			+ " m.enterorg = d.enterorg                                                                                     "
			+ " and m.ownership = d.ownership                                                                               "
			+ " and m.caseno = d.caseno                                                                                     "
			+ " and m.differencekind = d.differencekind                                                                     "
			+ " left outer join SYSPK_PROPERTYCODE2 c on                                                                    "
			+ " c.enterorg in (d.enterorg,'000000000A')                                                                     "
			+ " and d.propertyno = c.propertyno                                                                             "
			+ " left outer join UNTMP_MOVABLE mo on                                                                         "
			+ " d.enterorg = mo.enterorg                                                                                    "
			+ " and d.ownership = mo.ownership                                                                              "
			+ " and d.differencekind = mo.differencekind                                                                    "
			+ " and d.propertyno = mo.propertyno                                                                            "
			+ " and d.lotno = mo.lotno                                                                                      "
			+ " where 1 = 1 " + whereCon("adjustdate",true)
			+ " union all                                                                                                   "
			+ "                                                                                                             "
			//物品減損
			+ " select                                                                                                      "
			+ " d.propertyno,                                                                                               "
			+ " ISNULL(c.propertyname,'') propertyname,                                                                     "
			+ " ISNULL(c.propertyunit,'') as propertyunit,                                                                  "
			+ " cast(isnull(c.limityear,'') as varchar) as limityear,                                                       "
			+ " d.reducedate as enterdate,                                                                                  "
			+ " '' as entertype,                                                                                            "
			+ " m.summonsno as summonsno,                                                                                   "
			+ " d.specification + '/' + d.nameplate as booknotes,                                                           "
			+ " d.serialno,                                                                                                 "
			+ " '0' as add_amount,                                                                                          "
			+ " '0' as add_bookunit,                                                                                        "
			+ " '0' as add_bookvalue,                                                                                       "
			+ " d.adjustbookamount as reduce_amount,                                                                        "
			+ " d.adjustbookunit as reduce_bookunit,                                                                        "
			+ " d.adjustbookvalue as reduce_bookvalue                                                                       "
			+ " ,'0' as monthdepr, '0' as newaccumdepr, '0' as newnetvalue " 
			+ " from                                                                                                        "
			+ " UNTNE_REDUCEPROOF m                                                                                         "
			+ " inner join UNTNE_REDUCEDETAIL d on                                                                          "
			+ " m.enterorg = d.enterorg                                                                                     "
			+ " and m.ownership = d.ownership                                                                               "
			+ " and m.caseno = d.caseno                                                                                     "
			+ " and m.differencekind = d.differencekind                                                                     "
			+ " left outer join SYSPK_PROPERTYCODE2 c on                                                                    "
			+ " c.enterorg in (d.enterorg,'000000000A')                                                                     "
			+ " and d.propertyno = c.propertyno                                                                             "
			+ " where 1 = 1 " + whereCon("reducedate",true);
		return querySQL;
	}
}
