package sys.ap;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import util.Common;
import util.Database;
import util.SuperBean;

/**
 * <br/>程式目的：序號修正作業
 * <br/>程式代號：SYSAP301F
 * <br/>程式日期：104/05/14
 * <br/>程式作者：Kang Da
 * <br/>--------------------------------------------------------
 * <br/>修改作者　　     修改日期　　　			修改目的
 * <br/>--------------------------------------------------------
 */
public class SYSAP301F extends SuperBean {

	private Logger logger = Logger.getLogger(this.getClass());
	
	private String isExecUpdate;
	public String getIsExecUpdate() { return checkGet(isExecUpdate); }
	public void setIsExecUpdate(String isExecUpdate) { this.isExecUpdate = checkSet(isExecUpdate); }
	
	private String targetTable;
	public String getTargetTable() { return checkGet(targetTable); }
	public void setTargetTable(String targetTable) { this.targetTable = checkSet(targetTable); }

	private Map<String, Integer> caseSerialNoMap = new HashMap<String, Integer>();
	
	public String doFix() {
		StringBuilder result = new StringBuilder();
		result.append(this.doFixNullCaseSerialNo());
		result.append("<br/>");
		result.append(this.doFixRepeat());
		return result.toString();
	}
	
	private String doFixNullCaseSerialNo() {
		
		ResultSet rs =  null;
		Database database = new Database();
		List<UPDATE_DETAIL> updateList = new ArrayList<UPDATE_DETAIL>();
		StringBuilder htmlBuilder = new StringBuilder("<table width=\"100%\">");
		htmlBuilder.append("<tr><td align=\"center\" colspan=\"11\">" + this.getTargetTable() + "移動單修正資料</td></tr>");
		if (!"Y".equals(this.getIsExecUpdate())) {
			htmlBuilder.append("<tr>")
					   .append("<td align=\"center\">入帳機關</td>")
					   .append("<td align=\"center\">電腦單號</td>")
					   .append("<td align=\"center\">權屬代碼</td>")
					   .append("<td align=\"center\">物品區分別代碼</td>")
					   .append("<td align=\"center\">物品編號</td>")
					   .append("<td align=\"center\">物品分號</td>")
					   .append("<td align=\"center\">入帳</td>")
					   .append("<td align=\"center\">最後修改日期</td>")
					   .append("<td align=\"center\">最後修改時間</td>")
					   .append("<td align=\"center\">原序號</td>")
					   .append("<td align=\"center\">更新後序號</td>")
			           .append("</tr>");
		}
		try {
			database.setAutoCommit(false);
			rs = database.querySQL("select *  from " + this.getTargetTable() + " where caseserialno is null and verify = 'N' order by enterorg, caseno,editdate, edittime");
			while (rs.next()) {
				String enterorg = rs.getString("enterorg");
				String caseno = rs.getString("caseno");
				String ownership = rs.getString("ownership");
				String propertyno = rs.getString("propertyno");
				String differencekind = rs.getString("differencekind");
				String serialno = rs.getString("serialno");
				String ori_caseserialno = rs.getString("caseserialno");
				int new_caseserialno = this.getDetailCaseSerialNo(enterorg, caseno, ownership);
				String editDate = rs.getString("editdate");
				String editTime = rs.getString("edittime");
				String verify = rs.getString("verify");
				updateList.add(new UPDATE_DETAIL(this.getTargetTable(), enterorg, caseno, ownership, propertyno, differencekind, serialno, ori_caseserialno, new_caseserialno, editDate, editTime, verify));
			}
			
			if (updateList.size() > 0) {
			
				for (UPDATE_DETAIL mu : updateList) {
					if ("Y".equals(this.getIsExecUpdate())) {
						htmlBuilder.append("<tr><td align=\"left\" colspan=\"11\">").append(mu.toUpdateSQL()).append(";</td></tr>");
						this.logger.info(mu.toUpdateSQL());
						database.excuteSQL(mu.toUpdateSQL());
					} else {
						htmlBuilder.append(mu.toHtmlTRStr());
					}
				}
				
				if (updateList.size() > 0 && "Y".equals(this.getIsExecUpdate())) {
					database.doCommit();
				}
			} else {
				htmlBuilder.append("<tr><td align=\"center\" colspan=\"11\">沒有資料需要被異動</td></tr>");
			}
			
		} catch (Exception e) {
			this.logger.error("序號更正發生錯誤", e);
			htmlBuilder.append("<tr><td>ERROR:" + e.getMessage() + "</td></tr>");
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (Exception e) {
					//ignore
				}
			}
			database.closeAll();
			
		}
		return htmlBuilder.append("</table>").toString();
	}
	
	private String doFixRepeat() {
		
		ResultSet rs = null;
		ResultSet rs2 = null;
		Database database = new Database();
		StringBuilder htmlBuilder = new StringBuilder("<table width=\"100%\">");
		htmlBuilder.append("<tr><td align=\"center\" colspan=\"11\">" + this.getTargetTable() + "減損單修正資料</td></tr>");
		if (!"Y".equals(this.getIsExecUpdate())) {
			htmlBuilder.append("<tr>")
					   .append("<td align=\"center\">入帳機關</td>")
					   .append("<td align=\"center\">電腦單號</td>")
					   .append("<td align=\"center\">權屬代碼</td>")
					   .append("<td align=\"center\">物品區分別代碼</td>")
					   .append("<td align=\"center\">物品編號</td>")
					   .append("<td align=\"center\">物品分號</td>")
					   .append("<td align=\"center\">入帳</td>")
					   .append("<td align=\"center\">最後修改日期</td>")
					   .append("<td align=\"center\">最後修改時間</td>")
					   .append("<td align=\"center\">原序號</td>")
					   .append("<td align=\"center\">更新後序號</td>")
			           .append("</tr>");
		}
		List<UPDATE_DETAIL> updateList = new ArrayList<UPDATE_DETAIL>();
		try {
			database.setAutoCommit(false);
			String sql = " select qwe.* from ( " +
	                     " select enterorg, caseno, ownership, caseserialno, count(*) as n from " + 
					     this.getTargetTable() +
					      " where 1=1 and caseserialno is not null " + 
	                      // " and verify='N' " +
					      " and caseno in ('1020000011','1030000101') " +
	                     " group by  enterorg, caseno, ownership, caseserialno " +
	                     " ) as qwe where n > 1 ";
			
			rs = database.querySQL(sql, false, false);
			while (rs.next()) {
				String enterorg = rs.getString("enterorg");
				String caseno = rs.getString("caseno");
				String ownership = rs.getString("ownership");
				String caseserialno = rs.getString("caseserialno");
				updateList.addAll(this.doFixRepeatDetail(enterorg, caseno, ownership, caseserialno));
			}
			
			if (updateList.size() > 0) {
				
				for (UPDATE_DETAIL mu : updateList) {
					if ("Y".equals(this.getIsExecUpdate())) {
						htmlBuilder.append("<tr><td align=\"left\" colspan=\"11\">").append(mu.toUpdateSQL()).append(";</td></tr>");
						this.logger.info(mu.toUpdateSQL());
						database.excuteSQL(mu.toUpdateSQL());
					} else {
						htmlBuilder.append(mu.toHtmlTRStr());
					}
				}
				
				if (updateList.size() > 0 && "Y".equals(this.getIsExecUpdate())) {
					database.doCommit();
				}
			} else {
				htmlBuilder.append("<tr><td align=\"center\" colspan=\"11\">沒有資料需要被異動</td></tr>");
			}
		} catch (Exception e) {
			this.logger.error("修正重複序號時發生錯誤", e);
			htmlBuilder.append("<tr><td>ERROR:" + e.getMessage() + "</td></tr>");
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (Exception e) {
					// ignore
				}
			}
			
			if (rs2 != null) {
				try {
					rs2.close();
				} catch (Exception e) {
					// ignore
				}
			}
			
			database.closeAll();
		}
		return htmlBuilder.append("</table>").toString();
	}
	
	private List<UPDATE_DETAIL> doFixRepeatDetail(String enterorg, String caseno, String ownership, String caseserialno) throws Exception {
		
		ResultSet rs = null;
		Database database= new Database();
		List<UPDATE_DETAIL> updateList = new ArrayList<UPDATE_DETAIL>();
		try {
			String sql = " select * from " + this.getTargetTable() + " where enterorg = " + Common.sqlChar(enterorg) + 
			             " and  caseno = " + Common.sqlChar(caseno) + " and ownership = " + Common.sqlChar(ownership);
			
			if (caseserialno == null) {
				sql += " and caseserialno is null ";
			} else {
				sql += " and caseserialno = " + Common.sqlChar(caseserialno);
			}
			rs = database.querySQL(sql);
			boolean isFirst = true;
			while (rs.next()) {
				if (isFirst) {
					isFirst = false;
					continue;
				}
				String propertyno = rs.getString("propertyno");
				String differencekind = rs.getString("differencekind");
				String serialno = rs.getString("serialno");
				String editdate = rs.getString("editdate");
				String edittime = rs.getString("edittime");
				String verify = rs.getString("verify");
				int newCaseSerialNo = this.getDetailCaseSerialNo(enterorg, caseno, ownership);
				updateList.add(new UPDATE_DETAIL(this.getTargetTable(), enterorg, caseno, ownership, propertyno, differencekind, serialno, caseserialno, newCaseSerialNo, editdate, edittime, verify));
			}
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (Exception e) {
					//ignore
				}
			}
			database.closeAll();
			
		}
		return updateList;
	}
	
	private int getDetailCaseSerialNo(String enterOrg, String caseNo, String ownership) {
		
		
		String indefineKey = enterOrg + "_" + caseNo + "_" + ownership;
		Integer currCaseSerialNo = this.caseSerialNoMap.get(indefineKey);
		if (currCaseSerialNo != null) {
			currCaseSerialNo = currCaseSerialNo + 1;
			this.caseSerialNoMap.put(indefineKey, currCaseSerialNo);
			return currCaseSerialNo;
		}
		
		String sql = "select ISNULL(max(caseSerialNo),0)+1 as caseserialno "+
					 " from " + this.getTargetTable() + 
					 " where enterorg = " + Common.sqlChar(enterOrg) + 
					 " and caseno = " + Common.sqlChar(caseNo) +
					 " and ownership = " + Common.sqlChar(ownership);
		
		Database database = new Database();
		ResultSet rs = null;
		int caseSerialNo = 1;
		try {
			rs = database.querySQL(sql);
			if (rs.next()){
				caseSerialNo = rs.getInt("caseSerialNo");
				this.caseSerialNoMap.put(indefineKey, caseSerialNo);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (Exception e) {
					//ignore
				}
			}
			
			database.closeAll();
		}
		return caseSerialNo;
		
	}
	
	public class UPDATE_DETAIL {
		private String taregtTable;
		private String enterorg;
		private String caseno;
		private String ownership;
		private String propertyno;
		private String differencekind;
		private String serialno;
		private String ori_caseserialno;
		private int new_caseserialno;
		private String lastEditDate;
		private String lastEditTime;
		private String verify;
		
		public UPDATE_DETAIL(String targetTable, String enterorg, String caseno, String ownership, String propertyno, String differencekind, String serialno, String ori_caseserialno, int new_caseserialno, String lastEditDate, String lastEditTime, String verify) {
			this.taregtTable = targetTable;
			this.enterorg = enterorg;
			this.caseno = caseno;
			this.ownership = ownership;
			this.propertyno = propertyno;
			this.differencekind = differencekind;
			this.serialno = serialno;
			this.ori_caseserialno = ori_caseserialno;
			this.new_caseserialno = new_caseserialno;
			this.lastEditDate = lastEditDate;
			this.lastEditTime = lastEditTime;
			this.verify = verify;
		}

		public String getEnterorg() { return enterorg; }
		public String getCaseno() { return caseno; }
		public String getOwnership() { return ownership; }
		public String getPropertyno() { return propertyno; }
		public String getDifferencekind() { return differencekind; }
		public String getSerialno() { return serialno; }
		public String getOri_caseserialno() { return ori_caseserialno; }
		public int getNew_caseserialno() { return new_caseserialno; }
		
		public String toUpdateSQL() {
			return " update " + this.taregtTable + " set caseserialno = " + this.new_caseserialno + " where enterorg = " + Common.sqlChar(this.enterorg) +
				   " and caseno = " + Common.sqlChar(this.caseno) + " and ownership = " + Common.sqlChar(this.ownership) +
				   " and propertyno = " + Common.sqlChar(this.propertyno) + " and differencekind = " + Common.sqlChar(this.differencekind) +
				   " and serialno = " + Common.sqlChar(this.serialno);
		}
		
		public String toHtmlTRStr() {
			StringBuilder htmlBuilder = new StringBuilder();
			htmlBuilder.append("<tr>")
			           .append("<td align=\"center\">").append(this.enterorg).append("</td>")
			           .append("<td align=\"center\">").append(this.caseno).append("</td>")
			           .append("<td align=\"center\">").append(this.ownership).append("</td>")
			           .append("<td align=\"center\">").append(this.differencekind).append("</td>")
			           .append("<td align=\"center\">").append(this.propertyno).append("</td>")
			           .append("<td align=\"center\">").append(this.serialno).append("</td>")
			           .append("<td align=\"center\">").append(this.verify).append("</td>")
			           .append("<td align=\"center\">").append(this.lastEditDate).append("</td>")
			           .append("<td align=\"center\">").append(this.lastEditTime).append("</td>")
			           .append("<td align=\"center\">").append(this.ori_caseserialno).append("</td>")
			           .append("<td align=\"center\">").append(this.new_caseserialno).append("</td>");
			htmlBuilder.append("</tr>");
			return htmlBuilder.toString();
		}
		
	}
	
}
