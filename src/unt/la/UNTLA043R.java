/*
 *<br>程式目的：土地明細清冊報表檔(untla043r)
 *<br>撰寫日期：94/12/07 原作：chris
 *<br>--------------------------------------------------------
 *<br>修改日期    修改作者　　  修改目的
 *	 0980414   shan        Tbek彰化財產系統
 *<br>--------------------------------------------------------
 */

package unt.la;

import java.sql.ResultSet;
import java.util.Vector;

import javax.swing.table.DefaultTableModel;

import util.Common;

import unt.ch.UNTCH_Tools;
import util.*;

public class UNTLA043R extends SuperBean {

	String q_balanceDate;
	String q_enterOrg;
	String q_ownership;
	String q_verify;
	String q_propertyKind;
	String q_fundType;
	String q_valuable;
	String q_taxCredit;
	String q_grass;
	String q_enterOrgName;
	String q_signNo1;
    String q_signNo2;
    String q_signNo3;
	
	/*
	String q_signNo1;
    String q_signNo2;
    String q_signNo3;
*/
    String q_differenceKind;
	private String q_reportType;						// 報表類型
	
	public String getQ_reportType() { return checkGet(q_reportType); }
	public void setQ_reportType(String q_reportType) { this.q_reportType = checkGet(q_reportType); }
    public String getQ_differenceKind() {
    	return checkGet(q_differenceKind);
    }
    
	public void setQ_differenceKind(String s) {
		q_differenceKind = checkSet(s);
	}
	
	public String getQ_enterOrgName() {
		return checkGet(q_enterOrgName);
	}

	public void setQ_enterOrgName(String s) {
		q_enterOrgName = checkSet(s);
	}

	public String getQ_balanceDate() {
		return checkGet(q_balanceDate);
	}

	public void setQ_balanceDate(String s) {
		q_balanceDate = checkSet(s);
	}

	public String getQ_enterOrg() {
		return checkGet(q_enterOrg);
	}

	public void setQ_enterOrg(String s) {
		q_enterOrg = checkSet(s);
	}

	public String getQ_ownership() {
		return checkGet(q_ownership);
	}

	public void setQ_ownership(String s) {
		q_ownership = checkSet(s);
	}

	public String getQ_verify() {
		return checkGet(q_verify);
	}

	public void setQ_verify(String s) {
		q_verify = checkSet(s);
	}

	public String getQ_propertyKind() {
		return checkGet(q_propertyKind);
	}

	public void setQ_propertyKind(String s) {
		q_propertyKind = checkSet(s);
	}

	public String getQ_fundType() {
		return checkGet(q_fundType);
	}

	public void setQ_fundType(String s) {
		q_fundType = checkSet(s);
	}

	public String getQ_valuable() {
		return checkGet(q_valuable);
	}

	public void setQ_valuable(String s) {
		q_valuable = checkSet(s);
	}

	public String getQ_taxCredit() {
		return checkGet(q_taxCredit);
	}

	public void setQ_taxCredit(String s) {
		q_taxCredit = checkSet(s);
	}

	public String getQ_grass() {
		return checkGet(q_grass);
	}

	public void setQ_grass(String s) {
		q_grass = checkSet(s);
	}
	
	public String getQ_signNo1(){ return checkGet(q_signNo1); }
	public void setQ_signNo1(String s){ q_signNo1=checkSet(s); }
	public String getQ_signNo2(){ return checkGet(q_signNo2); }
	public void setQ_signNo2(String s){ q_signNo2=checkSet(s); }
	public String getQ_signNo3(){ return checkGet(q_signNo3); }
	public void setQ_signNo3(String s){ q_signNo3=checkSet(s); }
	
	/*
	

	public String getLaSignNo1() {return checkGet(laSignNo1);}
	public void setLaSignNo1(String laSignNo1) {this.laSignNo1 = checkSet(laSignNo1);}
	public String getLaSignNo2() {return checkGet(laSignNo2);}
	public void setLaSignNo2(String laSignNo2) {this.laSignNo2 = checkSet(laSignNo2);}
	public String getLaSignNo3() {return checkGet(laSignNo3);}
	public void setLaSignNo3(String laSignNo3) {this.laSignNo3 = checkSet(laSignNo3);}
*/
	String isOrganManager;
	String isAdminManager;
	String organID;

	public String getOrganID() {
		return checkGet(organID);
	}

	public void setOrganID(String s) {
		organID = checkSet(s);
	}

	public String getIsOrganManager() {
		return checkGet(isOrganManager);
	}

	public void setIsOrganManager(String s) {
		isOrganManager = checkSet(s);
	}

	public String getIsAdminManager() {
		return checkGet(isAdminManager);
	}

	public void setIsAdminManager(String s) {
		isAdminManager = checkSet(s);
	}

	String q_order;

	public String getQ_order() {
		return checkGet(q_order);
	}

	public void setQ_order(String s) {
		q_order = checkSet(s);
	}
	String SignNoA;
	public DefaultTableModel getResultModel() throws Exception {

		DefaultTableModel model = new javax.swing.table.DefaultTableModel();
		Database db = new Database();
		try {
			String[] columns = new String[] { "ENTERORGNAME", "PROPERTYKIND",
					"DATATYPE", "ENTERDATE", "OWNERSHIP", "PROPERTYNO",
					"PROPERTYNAME", "PROPERTYNAME1", "SIGNNO1", "SIGNNO2",
					"SIGNNO3", "SIGNNO4", "SIGNNO5", "AREA", "HOLDNUME",
					"HOLDAREA", "USESEPARATE", "USEKIND", "BOOKUNIT",
					"BOOKVALUE", "AREAS", "HOLDAREAS" };
			String execSQL = "";
			String tansToDouble;
			String SignNo3;

			execSQL = " select a.enterorg ,q.organaname as enterorgname, a.propertyno, a.serialno "
					+ "\n"
					+ "        ,( select codename from SYSCA_CODE z where z.codekindid = 'DFK' and z.codeid = a.differencekind) as codename " + "\n"
					+ "        , ( select g.codename from SYSCA_CODE g where g.codekindid = 'FUD' and g.codeid=a.fundtype) as propertykind "
					+ "\n"
					+ "        ,'結存數' as datatype "
					+ "\n"
					+ "        ,(select codename from SYSCA_CODE x where x.codekindid = 'OWA' and x.codeid = a.ownership ) as ownership "
					+ "\n"
					+ "        ,d.propertyname ,a.propertyname1 "
					+ "\n"
					+ "        ,a.signno "
					+ "\n"
					+ "        , a.area"
//					+ "        ,(case when x.oldarea = 0 or x.oldarea is null then (case a.datastate when '1' then a.area when '2' then p.area end) when x.oldarea > 0  then x.oldarea  "
//					+ "\n"
//					+ "               end) as area "
					+ "\n"
//					+ "		   , case when oldholdnume is null then 0 else oldholdnume end as oldholdnume"
					+ "		   , a.datastate"
//					+ "		   , case when x.oldholdnume is null then 0 else x.oldholdnume end as oldholdnumex"
//					+ "		   , case when x.oldholddeno is null then 0 else x.oldholddeno end as oldholddenox"
//					+ "		   , a.holdnume as holdnumea, a.holddeno as holddenoa, p.holdnume as holdnumep, p.holddeno as holddenop"
					+ "		   , a.holdnume, a.holddeno, "
					+ "\n"
					+ " a.holdarea "
//					+ "        ,(case when x.oldholdarea = 0 or x.oldholdarea is null then (case a.datastate when '1' then a.holdarea when '2' then p.holdarea end) when x.oldholdarea > 0 then x.oldholdarea end"
//					+ "\n"
//					+ "               ) as holdarea "
					+ "\n"

					+ "        ,( select m.codename from SYSCA_CODE m where m.codekindid = 'SEP' and m.codeid = a.useseparate ) as useseparate "
					+ "\n"
					+ "        ,( (case (select count(*) from UNTLA_USESEPARATE k  "
					+ "\n"
					+ "                          where k.enterorg = a.enterorg and k.ownership =a.ownership  "
					+ "\n"
					+ "                          and k.propertyno =a.propertyno and k.serialno = a.serialno "
					+ "\n"
					+ "                          and k.editdate > '20080806' "
					+ "\n"
					+ "                        ) "
					+ "\n"
					+ "                    when 1 then ( select g.codename from SYSCA_CODE g ,UNTLA_USESEPARATE kk   "
					+ "\n"
					+ "                            where g.codekindid = 'UKD' and g.codeid = kk.newusekind "
					+ "\n"
					+ "                            and kk.enterorg=a.enterorg and kk.ownership=a.ownership and kk.propertyno=a.propertyno and kk.serialno=a.serialno ) "
					+ "\n"
					+ "                    when 0 then ( select g.codename from SYSCA_CODE g  "
					+ "\n"
					+ "                            where g.codekindid = 'UKD' and g.codeid = a.usekind ) "
					+ "\n"
					+ "                  end) "
					+ "\n"
					+ "          ) as useKind, "
					+ "\n"

//					+ "        ,(case when x.oldbookunit = 0 or x.oldbookunit is null then (case a.datastate when '1' then a.bookunit when '2' then p.bookunit end) when  x.oldbookunit > 0 then x.oldbookunit end"
//					+ "\n"
//					+ "               ) as bookunit "
//					+ "\n"
					+ " a.bookunit, "
//					+ "        ,(case when x.oldbookvalue = 0 or x.oldbookvalue is null then (case a.datastate when '1' then a.bookvalue when '2' then p.bookvalue end) when x.oldbookvalue > 0 then x.oldbookvalue end"
//					+ "\n"
//					+ "               ) as bookvalue "
					+ " a.bookvalue "
					+ "\n"

					+ " from UNTLA_LAND a "
					+ "\n"
//					+ "      left join ( select z.enterorg ,z.ownership ,z.propertyno ,z.serialno ,b.adjustdate "
//					+ "\n"
//					+ "                ,b.oldarea ,b.oldholdnume , b.oldholddeno ,b.oldholdarea "
//					+ "\n"
//					+ "                ,b.oldbookunit ,b.oldbookvalue "
//					+ "\n"
//					+ "         from UNTLA_LAND z,UNTLA_ADJUSTDETAIL b "
//					+ "\n"
//					+ "         where 1=1 "
//					+ "\n"
//					+ "         and z.enterorg = b.enterorg and z.ownership = b.ownership "
//					+ "\n"
//					+ "         and z.propertyno = b.propertyno and z.serialno = b.serialno "
//					+ "\n" + "         and b.enterorg= "
//					+ Common.sqlChar(getQ_enterOrg())
//					+ "\n"
//					+ "         and b.verify = 'Y' "
//					+ "\n"
//					+ "         and b.caseno = ( select min(c.caseno) from UNTLA_ADJUSTDETAIL c "
//					+ "\n"
//					+ "                          where c.enterorg = z.enterorg and c.ownership = z.ownership "
//					+ "\n"
//					+ "                          and c.propertyno = z.propertyno and c.serialno = z.serialno "
//					+ "\n"
//					+ "							 and c.adjustdate > "
//					+ Common.sqlChar(new UNTCH_Tools()._transToCE_Year(getQ_balanceDate()))
//					+ "\n"
//					+ "                        ) "
//					+ "\n"
//					+ "       ) x "
//					+ "\n"
//					+ " on a.enterorg = x.enterorg and a.ownership = x.ownership and a.propertyno = x.propertyno and a.serialno = x.serialno "
//					+ "\n"
//					+ " left join UNTLA_REDUCEDETAIL p "
//					+ "\n"
//					+ " on a.enterorg = p.enterorg and a.ownership = p.ownership and a.propertyno = p.propertyno and a.serialno = p.serialno "
//					+ "\n"

					+ " ,SYSPK_PROPERTYCODE d ,SYSCA_CODE f ,SYSCA_ORGAN q"
					+ "\n"
					+ " where 1=1 "
					+ "\n"
					+ " and a.enterorg = q.organid "
					+ "\n"
					+ " and f.codekindid = 'PKD' and a.propertykind = f.codeid "
					+ "\n"
					
					+ " and a.propertyno = d.propertyno and d.enterorg in ( a.enterorg ,'000000000A') "
					+ "\n"

					+ " and a.verify='Y' and a.datastate = '1' "
					+ "\n"
					+ " and ( a.reducedate is null or a.reducedate > "
					+ Common.sqlChar(new UNTCH_Tools()._transToCE_Year(getQ_balanceDate()))
					+ ") "
					+ "\n"
					+ " and a.enterdate <= "
					+ Common.sqlChar(new UNTCH_Tools()._transToCE_Year(getQ_balanceDate()));

			if (getIsAdminManager().equals("Y")
					&& getIsOrganManager().equals("Y")) {
				execSQL += " and q.ismanager='Y' and ( q.organid=(case 'Y' when 'Y' then q.organid else '"
						+ getOrganID()
						+ "' end) or "
						+ " ( 'Y'='Y' and q.organid in (select organid from SYSCA_ORGAN where organsuperior='"
						+ getOrganID() + "') ) ) ";
			} else if (getIsAdminManager().equals("Y")
					&& getIsOrganManager().equals("N")) {
				execSQL += " and q.ismanager='Y' and ( q.organid=(case 'Y' when 'Y' then q.organid else '"
						+ getOrganID()
						+ "' end) or "
						+ " ( 'N'='Y' and q.organid in (select organid from SYSCA_ORGAN where organsuperior='"
						+ getOrganID() + "') ) ) ";
			} else if (getIsAdminManager().equals("N")
					&& getIsOrganManager().equals("Y")) {
				execSQL += " and q.ismanager='Y' and ( q.organid=(case 'N' when 'Y' then q.organid else '"
						+ getOrganID()
						+ "' end) or "
						+ " ( 'Y'='Y' and q.organid in (select organid from SYSCA_ORGAN where organsuperior='"
						+ getOrganID() + "') ) ) ";
			} else if (getIsAdminManager().equals("N")
					&& getIsOrganManager().equals("N")) {
				execSQL += " and q.ismanager='Y' and ( q.organid=(case 'N' when 'Y' then q.organid else '"
						+ getOrganID()
						+ "' nd) or "
						+ " ( 'N'='Y' and q.organid in (select organid from SYSCA_ORGAN where organsuperior='"
						+ getOrganID() + "') ) ) ";
			}

			if (!"".equals(getQ_enterOrg())) {
				execSQL += " and a.enterorg = "
						+ Common.sqlChar(getQ_enterOrg());
			} else {
				if (!getIsAdminManager().equalsIgnoreCase("Y")) {
					if ("Y".equals(getIsOrganManager())) {
						execSQL += " and a.enterorg like '"
								+ getOrganID().substring(0, 5) + "%' ";
					} else {
						execSQL += " and a.enterorg = "
								+ Common.sqlChar(getOrganID());
					}
				}
			}
			if (!"".equals(Common.get(getQ_ownership()))) {
				execSQL = execSQL + " and a.ownership = "
						+ util.Common.sqlChar(getQ_ownership());
			}

			if (!"".equals(Common.get(getQ_verify()))) {
				execSQL = execSQL + " and a.verify = "
						+ util.Common.sqlChar(getQ_verify());
			}
			if (!"".equals(Common.get(getQ_propertyKind()))) {
				if ("00".equals(Common.get(getQ_propertyKind())))
					execSQL = execSQL + " and a.propertykind <'04' ";
				else
					execSQL = execSQL + " and a.propertykind = "
							+ util.Common.sqlChar(getQ_propertyKind());
			}
			if (!"".equals(getQ_differenceKind())){ 
				execSQL = execSQL + " and a.differencekind = " + util.Common.sqlChar(getQ_differenceKind());
			}
			
			/*
			if(!"".equals(getLaSignNo3())){
	    		SignNoA = getLaSignNo3();
	    	}else if(!"".equals(getLaSignNo2())){
	    		SignNoA = getLaSignNo2().substring(0,3);
	    	}else if(!"".equals(getLaSignNo1())){
	    		SignNoA = getLaSignNo1().substring(0,1);
	    	}
			
			if (!"".equals(getLaSignNo1()))
				execSQL+=" and a.signno like '" +SignNoA+"%' " ;
			*/
			if (!"".equals(Common.get(getQ_fundType()))) {
				execSQL = execSQL + " and a.fundtype = "
						+ util.Common.sqlChar(getQ_fundType());
			}
			if (!"".equals(Common.get(getQ_valuable()))) {
				execSQL = execSQL + " and a.valuable = "
						+ util.Common.sqlChar(getQ_valuable());
			}
			if (!"".equals(Common.get(getQ_taxCredit()))) {
				execSQL = execSQL + " and a.taxcredit = "
						+ util.Common.sqlChar(getQ_taxCredit());
			}

			String signNoStr = "";
			if (!"".equals(this.getQ_signNo1())) {
				signNoStr = this.getQ_signNo1().substring(0, 1);// 縣市1碼
			}
			if (!"".equals(this.getQ_signNo2())) {
				signNoStr = this.getQ_signNo2().substring(0, 3);// 縣市(1) + 鄉鎮市區(2)
			}
			if (!"".equals(this.getQ_signNo3())) {
				signNoStr = this.getQ_signNo3();
			}
			
			if (!"".equals(signNoStr)) {
				execSQL = execSQL + " and a.signno like " + Common.sqlChar(signNoStr + "%");
			}
			

			if ("1".equals(getQ_order())) {
				execSQL = execSQL
						+ " order by a.enterorg ,a.propertykind ,a.propertyno,a.serialno,a.signno ";
			} else {
				execSQL = execSQL
						+ " order by a.enterorg ,a.propertykind ,a.signno,a.propertyno,a.serialno ";
			}

			System.out.println(execSQL);
			
			//使用者操作記錄
			Common.insertRecordSql(execSQL, this.getOrganID(), this.getUserID(), "UNTLA043R", this.getObjPath().replaceAll("&gt;", ">"));
			
			ResultSet rs = db.querySQL_NoChange(execSQL);
			
			Vector rowData = new Vector();
			while (rs.next()) {
				Object[] data = new Object[22];
				data[0] = rs.getString("enterOrgName");
				data[1] = rs.getString("codename");
				data[2] = rs.getString("datatype");
				data[3] = "結存日期:" + Common.MaskDate(getQ_balanceDate());
				data[4] = rs.getString("ownership");
				data[5] = rs.getString("propertyno").substring(0,7) + "-" + rs.getString("propertyno").substring(7) + "-" + rs.getString("serialNo");
				data[6] = rs.getString("propertyname");
				data[7] = rs.getString("propertyname1");
				String SignNo = Common.checkGet(rs.getString("signNo"));
				if ("".equals(SignNo)) {
					data[8] = "";
					data[9] = "";
					data[10] = "";
					data[11] = "";
					data[12] = "";
				} else {
					data[8] = getSignDescName(SignNo.substring(0,1) + "000000");
					data[9] = getSignDescName(SignNo.substring(0,3) + "0000");

					SignNo3 = getSignDescName(SignNo.substring(0,7));
					if (SignNo3 != null && !"".equals(SignNo3)) {
						data[10] = SignNo3.substring(0, SignNo3.indexOf("段") + 1);
						if (SignNo3.length() >= 6)
							data[11] = SignNo3.substring(SignNo3.indexOf("段") + 1);
						else
							data[11] = null;
					} else {
						data[10] = null;
						data[11] = null;
					}
					
					if (!"".equals(SignNo)) {
						if (SignNo.length() >= 11) {
							data[12] = SignNo.substring(7, 11) + "-"
									+ SignNo.substring(11);
						} else {
							data[12] = "";
						}
					} else {
						data[12] = "";
					}
				}
				tansToDouble = rs.getString("area");
				if (tansToDouble != null)
					data[13] = new Double(Double.parseDouble(tansToDouble));
				else
					data[13] = null;

//				if("0".equals(checkGet(rs.getString("oldholdnume")))){
//					if("1".equals(checkGet(rs.getString("datastate")))){
//						data[14] = checkGet(rs.getString("holdnumea")) + "/" + checkGet(rs.getString("holddenoa"));
//					}else if("2".equals(checkGet(rs.getString("datastate")))){
//						data[14] = checkGet(rs.getString("holdnumep")) + "/" + checkGet(rs.getString("holddenop"));
//					}
//					
//				}else if("1".equals(checkGet(rs.getString("oldholdnume")))){
//					data[14] = checkGet(rs.getString("oldholdnumex")) + "/" + checkGet(rs.getString("oldholddenox"));
//				}
				data[14] = checkGet(rs.getString("holdnume")) + "/" + checkGet(rs.getString("holddeno"));
				
				
				tansToDouble = rs.getString("holdArea");
				if (tansToDouble != null)
					data[15] = new Double(Double.parseDouble(tansToDouble));
				else
					data[15] = null;

				data[16] = rs.getString("useseparate");
				data[17] = rs.getString("useKind");

				tansToDouble = rs.getString("bookUnit");
				if (tansToDouble != null)
					data[18] = new Double(Double.parseDouble(tansToDouble));
				else
					data[18] = null;

				tansToDouble = rs.getString("bookValue");
				if (tansToDouble != null)
					data[19] = new Double(Double.parseDouble(tansToDouble));
				else
					data[19] = null;
				data[20] = rs.getString("area")== null?"0":Common.areaFormat(rs.getString("area")) + "　";
				
				data[21] = "".equals(checkGet(rs.getString("holdArea")))?"0":Common.areaFormat(rs.getString("holdArea")) + "　";
				rowData.addElement(data);

			}

			Object[][] data = new Object[0][0];
			data = (Object[][]) rowData.toArray(data);
			model.setDataVector(data, columns);

		} catch (Exception x) {
			x.printStackTrace();
			throw x;
		} finally {
			db.closeAll();
		}

		return model;

	}

	private String getSignDescName(String signNo){
		Database db = null;
		ResultSet rs = null;
		String sql = null;
		String result = null;
		try{
			sql = "select signdesc from SYSCA_SIGN where" +
					" signno = " + Common.sqlChar(signNo);
			System.out.println("sql="+sql);
			db = new Database();
			rs = db.querySQL_NoChange(sql);
			if(rs.next()){				
				result = rs.getString("signdesc");
			}
			rs.close();
		}catch(Exception e){
			System.out.println("getSignDescName Exception => " + e.getMessage());
		}finally{
			db.closeAll();
		}
		return result;
	}
	
}