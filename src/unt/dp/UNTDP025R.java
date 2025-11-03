package unt.dp;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import javax.swing.table.DefaultTableModel;

import org.apache.log4j.Logger;

import unt.ch.UNTCH_Tools;
import util.Common;
import util.Database;
import util.Datetime;
import util.SuperBean;

/**
 * <br/>程式目的：轉帳憑證黏存單(公務財產) 
 * <br/>程式代號：UNTDP025R
 * <br/>程式日期：104/11/16
 * <br/>程式作者：Kang Da
 * <br/>--------------------------------------------------------
 * <br/>修改作者　　     修改日期　　　			修改目的
 * <br/>
 * <br/>--------------------------------------------------------
 */
public class UNTDP025R extends SuperBean {

	final private String[] columns = new String[] {"f0", "f1", "f2", "f3", "f4", "f5", "f6", "f7", "f8", "f9", "f10", "f11", "f12", "f13", "f14","f15"};
	
	private Logger logger = Logger.getLogger(this.getClass());
	
	private String q_enterOrg;
	private String q_deprYM;
	private String q_differenceKind;
	private String isOrganManager;
	private String isAdminManager;
	private String organID;
	private String editID;
	private String q_printsign;
	private String q_changePage;
	private String q_reportType;						// 報表類型
	
	public String getQ_reportType() { return checkGet(q_reportType); }
	public void setQ_reportType(String q_reportType) { this.q_reportType = checkGet(q_reportType); }
	public String getQ_changePage() {return checkGet(q_changePage);}
	public void setQ_changePage(String s) {q_changePage =checkSet(s);}
	public String getQ_enterOrg(){ return checkGet(q_enterOrg); }
	public void setQ_enterOrg(String s){ q_enterOrg=checkSet(s); }
	public String getQ_deprYM(){ return checkGet(q_deprYM); }
	public void setQ_deprYM(String s){ q_deprYM=checkSet(s); }
	public String getQ_differenceKind(){ return checkGet(q_differenceKind); }
	public void setQ_differenceKind(String s){ q_differenceKind=checkSet(s); }
	
	public String getQ_printSign() {return checkGet(q_printsign);}
	public void setQ_printSign(String printsign) {this.q_printsign = checkSet(printsign);}  
	
	

	public String getOrganID() { return checkGet(organID); }
	public void setOrganID(String s) { organID = checkSet(s); }
	public String getIsOrganManager() { return checkGet(isOrganManager); }
	public void setIsOrganManager(String s) { isOrganManager = checkSet(s); }
	public String getIsAdminManager() { return checkGet(isAdminManager); }
	public void setIsAdminManager(String s) { isAdminManager = checkSet(s); } 
	public String getEditID() {return checkGet(editID);}
	public void setEditID(String editID) {this.editID = checkSet(editID);}   

	 
	
	private String getSQL() {
		UNTCH_Tools ut = new UNTCH_Tools();
		StringBuilder sql = new StringBuilder();
		sql.append(" select a.enterorg, a.deprym, a.differencekind,")
		   .append(" SUM(CASE a.deprunitcb WHEN 'N' THEN a.monthdepr ELSE scaledmonthdepr END) monthdepr, ")
		   .append(" (case a.propertytype ")
		   .append(" when '2' then '140202 累計折舊─土地改良物'")
		   .append(" when '3' then '140402 累計折舊─房屋建築及設備'")
		   .append(" when '4' then '140502 累計折舊─機械及設備'")
		   .append(" when '5' then '140602 累計折舊─交通及運輸設備'")
		   .append(" when '6' then '140702 累計折舊─雜項設備'")
		   .append(" else null end) as propertytype1");
		
		//問題單1893 因選擇分頁時，判斷顯示園區別中文程式寫死對應的園區別代碼只能顯示對應的園區別名稱，修正為依照折舊月檔中的園區別代碼至園區別代碼檔查詢中文顯示
		if("1".equals(Common.get(getQ_changePage()))) {
			sql.append(", (select x.deprparkname from SYSCA_DEPRPARK x where a.enterorg = x.enterorg and a.deprpark = x.deprparkno) as deprpark1");
		}
		
		sql.append(" FROM UNTDP_MONTHDEPR a WHERE 1=1 and a.deprmethod != '01' ");
		
		if (!"".equals(this.getQ_enterOrg())) {
			sql.append(" and a.enterorg = ").append(Common.sqlChar(this.getQ_enterOrg()));
		}
		
		if (!"".equals(this.getQ_differenceKind())) {
			sql.append(" and a.differencekind = ").append(Common.sqlChar(this.getQ_differenceKind()));
		}
		
		if (!"".equals(this.getQ_deprYM())) {
			sql.append(" and a.deprym = ").append(Common.sqlChar(ut._transToCE_Year(this.getQ_deprYM())));
		}
		
		// 先排除權利
		sql.append(" and a.propertytype != '8' ");
		//問題單1755 :新增以園區別當分頁條件
		if("1".equals(Common.get(getQ_changePage()))) {
			sql.append(" group by a.enterorg, a.deprym, a.differencekind, a.deprpark, a.propertytype");
			sql.append(" order by a.deprpark,a.propertytype");
		}else {
			sql.append(" group by a.enterorg, a.deprym, a.differencekind, a.propertytype");
			sql.append(" order by a.propertytype");
		}
		
		return sql.toString();
	}
	
	/**
	 * 處理f3的資料
	 * @param deprYM
	 * @return
	 */
	private String getF3Val(String deprYM) {
		if (!"".equals(deprYM) && deprYM.length() == 6) {
			return Datetime.changeTaiwanYYMM(deprYM, "1").substring(0, 3) + "年" + Datetime.changeTaiwanYYMM(deprYM, "1").substring(3, 5) + "月份公務財產折舊";
		} else {
			return deprYM;
		}
	}
	
	public DefaultTableModel getResultModel() throws Exception {

		UNTCH_Tools ut = new UNTCH_Tools();
		DefaultTableModel model = null;
		Database db = new Database();
		List<Object[]> rows = new ArrayList<Object[]>();
		try {
			String reportOrganName = ut._queryData("organaname")._from("SYSCA_ORGAN a ")._with(" and organid = '" + this.getQ_enterOrg() + "'")._toString();
			String username = ut._queryData("empname")._from("SYSAP_EMP a ")._with("and organid = '" + this.getOrganID() + "' and empid = '" + this.getEditID() + "'")._toString();
			String datetime = Datetime.getYYYMMDD();
			
			String sql = this.getSQL();
			ResultSet rs = db.querySQL(sql);
			//int i=0;
			List deprunitList = new ArrayList();
			List indexList = new ArrayList();
			List sumList = new ArrayList();
			int index =0;
			int sum = 0;
			while (rs.next()) {
				Object[] data = new Object[columns.length];
				//全銜
				data[0] = reportOrganName;
				//印表人
				data[1] = username;
				//印表日期
				data[2] = datetime.substring(0,3) + "/" + datetime.substring(3,5) + "/" + datetime.substring(5);
				// 用途說明 ，問題單1755，當以園區別分頁時，顯示(園區別)
				if("1".equals(Common.get(getQ_changePage()))){
					data[3] = this.getF3Val(Common.get(rs.getString("deprym")))+" "+Common.get(rs.getString("deprpark1"));
				}else{
					data[3] = this.getF3Val(Common.get(rs.getString("deprym")));
				}
				 
				// 所屬年月份  ，問題單1755，當以園區別分頁時不用顯示園區別欄位，所以直接拿原值，不要拿data[3]的。
				data[4] = this.getF3Val(Common.get(rs.getString("deprym")));
				// 費用名稱，問題單1755，當以園區別分頁時不用顯示園區別欄位，所以直接拿原值，不要拿data[3]的。
				data[5] = this.getF3Val(Common.get(rs.getString("deprym")));
				// 借方科目-部門別
				data[6] = "";
				// 借方科目-會計科目
				data[7] = "";
				// 借方科目-會計子目
				data[8] = "";
				// 借方科目-金額 
				data[9] = "";
				//以園區別分頁時，借方要出現一筆
				if ("1".equals(Common.get(getQ_changePage()))) {
					if(!deprunitList.contains(data[3])){
						deprunitList.add(data[3]);
						indexList.add(index);
						if(!(index == 0)){
							sumList.add(sum);
						}
						sum = 0;
						
					}
				}
				sum += Integer.parseInt(rs.getString("monthDepr") == null ? "0" : rs.getString("monthDepr"));
				
				// 貸方科目-會計科目	
				data[10] = Common.get(rs.getString("propertytype1"));
				// 貸方-金額
				data[14] = Common.moneyFormat(Common.get(rs.getString("monthDepr")));
				
				// 區分別(隱藏欄位)
				data[11] = Common.get(rs.getString("differencekind"));
				// 合計
				data[12] = Common.moneyFormat(sum); 
				// 合計新臺幣
				data[13] = Common.numToZh(String.valueOf(sum));
				//園區別 (分頁)
				if(!"".equals(Common.get(getQ_changePage()))){
					data[15]= Common.get(rs.getString("deprpark1"));
				}else{
					data[15]="";
				}
				
				if(rs.isLast()){
					sumList.add(sum);
				}
				rows.add(data);
				
				index+=1;
			}
			Object[] indexsArray = indexList.toArray();
			Object[] sumArray = sumList.toArray();
			
			if (rows != null && rows.size() > 0) {
				if("1".equals(Common.get(getQ_changePage()))) {
					// 借方只顯示一筆
					if ("A42020000G".equals(getQ_enterOrg())) {
						for(int i =0; i < indexsArray.length; i++ ){
							rows.get((Integer)indexsArray[i])[7] = "510801 固定資產折舊";
							rows.get((Integer)indexsArray[i])[9] = Common.moneyFormat((Integer)sumArray[i]);
						}
					} else {
						for(int i =0; i < indexsArray.length; i++ ){
							rows.get((Integer)indexsArray[i])[7] = "360101 資本資產總額";
							rows.get((Integer)indexsArray[i])[9] = Common.moneyFormat((Integer)sumArray[i]);
						}
					}
				}else {
					if ("A42020000G".equals(getQ_enterOrg())) {
						rows.get(0)[7] = "510801 固定資產折舊";
						rows.get(0)[9] = Common.moneyFormat(sum);
					} else {
						rows.get(0)[7] = "360101 資本資產總額";
						rows.get(0)[9] = Common.moneyFormat(sum);
					}
				}
				model = new DefaultTableModel();
				model.setDataVector(rows.toArray(new Object[rows.size()][columns.length]), columns);
			}	
			
		} finally {
			db.closeAll();
			
			if (rows != null) {
				rows.clear();
				rows = null;
			}
		}
		return model;
	}

}