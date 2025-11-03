package unt.dp;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.swing.table.DefaultTableModel;

import org.apache.log4j.Logger;

import util.Common;
import util.Database;
import util.Datetime;
import util.SuperBean;
import util.TCGHCommon;

/**
 * <br/>程式目的：財產折舊月報明細表(公務財產)
 * <br/>程式代號：UNTDP024R
 * <br/>程式日期：104/11/16
 * <br/>程式作者：Kang Da  copy from UNTDP013R
 * <br/>--------------------------------------------------------
 * <br/>修改作者　　     修改日期　　　			修改目的
 * <br/>
 * <br/>--------------------------------------------------------
 */
public class UNTDP024R extends SuperBean {
	
	
	final private String[] FIELD_NAMES = {"f0", "f1", "f2", "f3", "f4", "f5", "f6", "f7", "f8", "f9", "f10", "f11", "f12", "f13", "f14", "f15", "f16", "f17", "f18"};
	
	private Logger logger = Logger.getLogger(this.getClass());
	
	private String q_enterOrg;			//入帳機關
	private String q_deprYM; 			//折舊年月
	private String q_differenceKind; 	//財產區分別
	private String q_propertyType;		//財產大類
	private String q_changePage;		//換頁方式
	private String q_reportType;		// 報表類型
	
	public String getQ_reportType() {	return checkGet(q_reportType);}
	public void setQ_reportType(String q_reportType) { this.q_reportType = checkSet(q_reportType); }	
	public String getQ_changePage() {return checkGet(q_changePage);}
	public void setQ_changePage(String s) {q_changePage = checkSet(s);}
	public String getQ_enterOrg(){ return checkGet(q_enterOrg); }
	public void setQ_enterOrg(String s){ q_enterOrg=checkSet(s); }
	public String getQ_deprYM(){ return checkGet(q_deprYM); }
	public void setQ_deprYM(String s){ q_deprYM=checkSet(s); }
	public String getQ_differenceKind(){ return checkGet(q_differenceKind); }
	public void setQ_differenceKind(String s){ q_differenceKind=checkSet(s); }
	public String getQ_propertyType(){ return checkGet(q_propertyType); }
	public void setQ_propertyType(String s){ q_propertyType=checkSet(s); }
	
	private String isOrganManager;
	private String isAdminManager;
	private String organID;  
	private String userName;  
	public String getOrganID() { return checkGet(organID); }
	public void setOrganID(String s) { organID = checkSet(s); }
	public String getIsOrganManager() { return checkGet(isOrganManager); }
	public void setIsOrganManager(String s) { isOrganManager = checkSet(s); }
	public String getIsAdminManager() { return checkGet(isAdminManager); }
	public void setIsAdminManager(String s) { isAdminManager = checkSet(s); } 
	public String getUserName() { return checkGet(userName); }
	public void setUserName(String s) { userName = checkSet(s); }
	
	private String getSQL() {
		StringBuilder sql = new StringBuilder();
		
		sql.append(" select * from ( ")
		   .append(" select distinct ")
		   .append(" a.enterorg, a.differencekind, a.deprym, a.serialno1, a.propertytype, a.propertyno, a.serialno, a.propertyname1,")
		   .append(" a.buydate, a.scrapvalue, a.limityear, a.bookamount,")
		   .append(" case a.deprunitcb when 'Y' then a.scaledbookvalue else a.bookvalue end as 'bookvalue',")
		   .append(" case a.deprunitcb when 'Y' then a.scaledmonthdepr else a.monthdepr end as 'monthdepr',")
		   .append(" case a.deprunitcb when 'Y' then a.scalednewaccumdepr else a.newaccumdepr end as 'newaccumdepr',")
		   .append(" case a.deprunitcb when 'Y' then a.scalednewnetvalue else a.newnetvalue end as 'newnetvalue',")
		   .append(" a.deprpercent, a.depraccounts, a.deprpark,")
		   .append(" (select z.deprparkname FROM SYSCA_DEPRPARK z where a.enterorg = z.enterorg and a.deprpark = z.deprparkno ) as deprparkname ")
		   .append(" FROM UNTDP_MONTHDEPR a WHERE 1=1 ");
		
		if (!"".equals(this.getQ_enterOrg())) {
			sql.append(" and a.enterorg = " + Common.sqlChar(this.getQ_enterOrg()));
		} else {
			if (!this.getIsAdminManager().equalsIgnoreCase("Y")) {
				if ("Y".equals(this.getIsOrganManager())) {					
					sql.append(" and a.enterorg like '" + this.getOrganID().substring(0,5) + "%' ");
				} else {
					sql.append(" and a.enterorg = " + Common.sqlChar(this.getOrganID()));
				}
			} 
		}
		   
		
		if (!"".equals(Common.get(getQ_deprYM()))) {
			sql.append(" and a.deprym = " + Common.sqlChar(Datetime.changeTaiwan(this.getQ_deprYM().substring(0, 3), "2") + this.getQ_deprYM().substring(3, 5)));
		}
		if (!"".equals(Common.get(this.getQ_differenceKind()))) {
			sql.append(" and a.differencekind = " + Common.sqlChar(this.getQ_differenceKind()));
		}
		if (!"".equals(Common.get(getQ_propertyType()))) {
			sql.append(" and a.propertytype = " + Common.sqlChar(this.getQ_propertyType()));
		}
		
		if (!"".equals(Common.get(getQ_changePage()))){
			sql.append(" ) as main order by enterorg, deprym, differencekind, deprpark, propertytype, propertyno, serialno, serialno1");
		}else{
			sql.append(" ) as main order by enterorg, deprym, differencekind, propertytype, propertyno, serialno, serialno1");
		}
		
		
		
		return sql.toString();
	}

	
	public DefaultTableModel getResultModel() throws Exception {
		
		Map<String,String> differencekindMap = TCGHCommon.getSysca_Code("DFK"); 					//財產區分別名稱
		Map<String,String> propertytypeMap = TCGHCommon.getSysca_Code("PTT"); 						//財產大類名稱
		Map<String,String> enterorgMap = TCGHCommon.getSysca_Organ();								//機關名稱
		Map<String,String> propertynameMap = TCGHCommon.getSyspk_PropertyCode(this.getQ_enterOrg());//財產名稱

		DefaultTableModel model = null;
		Database db = new Database();
		List<Object[]> rows = null;
		try {
			if (!"".equals(this.getQ_enterOrg()) ||  !"".equals(this.getOrganID())) {
				
				String sql = this.getSQL();
				ResultSet rs = db.querySQL(sql);
				rows = new ArrayList<Object[]>();
				while (rs.next()) {
					Object[] data = new Object[FIELD_NAMES.length];
					// 機關名稱
					data[0] = Common.get(enterorgMap.get(this.getQ_enterOrg()));
					// 財產區分別
					data[1] = Common.get(differencekindMap.get(rs.getString("differencekind")));
					// 財產大類
					data[2] = Common.get(propertytypeMap.get(rs.getString("propertytype")));
					// propertyno
					String propertyno = Common.get(rs.getString("propertyno"));
					if (propertyno.length() > 7) {
						data[3] = Common.get(propertyno.substring(0, 7)) + "-" + checkGet(propertyno.substring(7));
					} else {
						data[3] = Common.get(propertyno);
					}
					
					// serialno
					data[4] = Common.get(rs.getString("serialno"));
					// 財產別名
					data[5] = Common.get(rs.getString("propertyname1"));
					// 購置日期
					try {
						data[6] = Common.get(Common.MaskDate(Datetime.changeTaiwanYYMMDD(rs.getString("buydate"), "1")));
					} catch (Exception e) {
						this.logger.error("購置日期資料有誤," + Common.get(rs.getString("buydate")), e);
						data[6] = rs.getString("buydate");
					}
					// 殘值
					data[7] = Common.get(rs.getString("scrapvalue"));
					// 年限
					data[8] = Common.formatOtherLimityear(1, Common.get(rs.getString("limityear")));
					// 數量
					data[9] = rs.getLong("bookamount");
					// 總價
					data[10] = rs.getDouble("bookvalue");
					// 本月折舊
					data[11] = rs.getDouble("monthdepr");
					// 累計折舊
					data[12] = rs.getDouble("newaccumdepr");
					// 淨值
					data[13] = rs.getDouble("newnetvalue");
					// 分攤百分比
					data[14] = String.valueOf((rs.getDouble("deprpercent")));
					
					// 報表起始日
					data[15] = Common.get(Common.formatYYYMMDD(this.getQ_deprYM()+ "01", 2)); 
					int year = Integer.parseInt(this.getQ_deprYM().substring(0,3));
					int month = Integer.parseInt(this.getQ_deprYM().substring(3,5));
					data[16] = Common.formatYYYMMDD(this.getQ_deprYM() + Common.getLastDayOfMonth(year,month),2);
					
					data[17] = propertyno + "-" + 
							   Common.get(rs.getString("serialno")) + "\n" + 
							   Common.get(propertynameMap.get(rs.getString("propertyno")));
					
					if ("1".equals(Common.get(getQ_changePage()))){
						data[18] = Common.get(rs.getString("deprparkname"));
					}
					
					rows.add(data);
				}

				if (rows != null && rows.size() > 0) {
					model = new DefaultTableModel();
					model.setDataVector(rows.toArray(new Object[rows.size()][FIELD_NAMES.length]), FIELD_NAMES);
				}
				
			}
		} finally {
			db.closeAll();
			
			if (rows != null) {
				rows.clear();
				rows = null;
			}
			
			differencekindMap.clear();
			differencekindMap = null;
			propertytypeMap.clear();
			propertytypeMap = null;
			enterorgMap.clear();
			enterorgMap = null;
			propertynameMap.clear();
			propertynameMap = null;
		}
		return model;
	}
}
