package util;

import java.sql.ResultSet;

public class General extends QueryBean {
	
	public String getPropertyList(String sql, String preWord) throws Exception{
		Database db = new Database();
		StringBuffer sbHTML = new StringBuffer("");
		try {
			if (sql!=null && sql.trim().length()>0) {
				ResultSet rs = db.querySQL(sql, true);				
				processCurrentPageAttribute(rs);
				if (getTotalRecord() > 0) {
					int count = getRecordStart();
					int end = getRecordEnd();
					do {
						if (count > end)
							break;
						StringBuffer strLink = new StringBuffer(0).append(Common.sqlChar(Common.checkGet(rs.getString("propertyNo"))) ).append( "," ).append(
							Common.sqlChar(Common.checkGet(rs.getString("propertyName"))) ).append( "," ).append(
							Common.sqlChar(Common.checkGet(rs.getString("propertyType"))) ).append( "," ).append(
							Common.sqlChar(Common.checkGet(rs.getString("propertyUnit"))) ).append( "," ).append(
							Common.sqlChar(Common.checkGet(rs.getString("material"))) ).append( "," ).append(
							Common.sqlChar(Common.checkGet(rs.getString("limitYear"))));				
						sbHTML.append(" <tr class='listTR' onmouseover=\"this.className='listTRMouseover'\" onmouseout=\"this.className='listTR'\" onClick=\"selectProperty(").append( strLink ).append( ")\" >");
						sbHTML.append(" <td class='listTD' >").append(count).append(".</td> ");
						sbHTML.append(" <td class='listTD' >").append(Common.get(Common.checkGet(rs.getString("propertyNo")))).append("</td> ");
						sbHTML.append(" <td class='listTD' >").append(Common.get(Common.checkGet(rs.getString("propertyName")))).append("</td> ");
						sbHTML.append(" <td class='listTD' >").append(Common.get(Common.checkGet(rs.getString("material")))).append("</td> ");
						sbHTML.append(" <td class='listTD' >").append(Common.get(Common.checkGet(rs.getString("limitYear")))).append("</td> ");
						sbHTML.append(" </tr> ");
						count++;
					} while (rs.next());
				} else {
					sbHTML.append(" <tr class='listTR' ><td class='listTD' colspan='100' style='color:red'>查無資料，請您重新輸入查詢條件！</td></tr>");
//					if (Common.get(preWord).length()>0) {
//						sbHTML.append(" <tr class='listTR' ><td class='listTD' colspan='100' style='color:red'>本作業只查詢前置詞為「").append(preWord).append("」之財產編號！</td></tr>");
//					}
				}
				rs.getStatement().close();
				rs.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.closeAll();
		}
		return sbHTML.toString();
	}	
	
	public String getPropertyList_forTCGH(String sql, String preWord) throws Exception{
		Database db = new Database();
		StringBuffer sbHTML = new StringBuffer("");
		try {
			if (sql!=null && sql.trim().length()>0) {
				ResultSet rs = db.querySQL(sql, true);				
				processCurrentPageAttribute(rs);
				if (getTotalRecord() > 0) {
					int count = getRecordStart();
					int end = getRecordEnd();
					do {
						if (count > end)
							break;
						StringBuffer strLink = new StringBuffer(0).append(Common.sqlChar(Common.checkGet(rs.getString("propertyNo"))) ).append( "," ).append(
							Common.sqlChar(Common.checkGet(rs.getString("propertyName"))) ).append( "," ).append(
							Common.sqlChar(Common.checkGet(rs.getString("propertyType"))) ).append( "," ).append(
							Common.sqlChar(Common.checkGet(rs.getString("propertyUnit"))) ).append( "," ).append(
							Common.sqlChar(Common.checkGet(rs.getString("material"))) ).append( "," ).append(
							Common.sqlChar(Common.checkGet(rs.getString("limitYear"))));				
						sbHTML.append(" <tr class='listTR' onmouseover=\"this.className='listTRMouseover'\" onmouseout=\"this.className='listTR'\" onClick=\"selectProperty(").append( strLink ).append( ")\" >");
						sbHTML.append(" <td class='listTD' >").append(count).append(".</td> ");
						sbHTML.append(" <td class='listTD' >").append(Common.get(Common.checkGet(rs.getString("propertyNo")))).append("</td> ");
						sbHTML.append(" <td class='listTD' >").append(Common.get(Common.checkGet(rs.getString("propertyName")))).append("</td> ");
						sbHTML.append(" <td class='listTD' >").append(Common.get(Common.checkGet(rs.getString("material")))).append("</td> ");
						sbHTML.append(" <td class='listTD' >").append(Common.get(Common.checkGet(rs.getString("propertyUnit")))).append("</td> ");
						sbHTML.append(" <td class='listTD' >").append(Common.get(Common.checkGet(rs.getString("limitYear")))).append("</td> ");
						sbHTML.append(" </tr> ");
						count++;
					} while (rs.next());
				} else {
					sbHTML.append(" <tr class='listTR' ><td class='listTD' colspan='100' style='color:red'>查無資料，請您重新輸入查詢條件！</td></tr>");
				}
				rs.getStatement().close();
				rs.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.closeAll();
		}
		return sbHTML.toString();
	}	
	
	
	public String getOrganList(String sql) throws Exception{
		StringBuffer sbHTML = new StringBuffer("");
		Database db = new Database();
		int count = 0;
		try{
			if (sql!=null && sql.trim().length()>0) {
				ResultSet rs = db.querySQL(sql + " order by a.organID ", true);				
				processCurrentPageAttribute(rs);
				if (getTotalRecord() > 0) {
					count = getRecordStart();
					int end = getRecordEnd();
					do {
						if (count > end)
							break;
						sbHTML.append(" <tr class='listTR' onmouseover=\"this.className='listTRMouseover'\" onmouseout=\"this.className='listTR'\" onClick=\"selectOrgan('").append(Common.checkGet(rs.getString("organID"))).append("','").append(Common.checkGet(rs.getString("organAName"))).append("')\" >");
						sbHTML.append(" <td class='listTD' >").append(count).append(".</td> ");
						sbHTML.append(" <td class='listTD' >").append(Common.checkGet(rs.getString("organID"))).append("</td> ");
						sbHTML.append(" <td class='listTD' >").append(Common.checkGet(rs.getString("organAName"))).append("</td> ");
						sbHTML.append(" </tr> ");	
						count++;
					} while (rs.next());
				}
			}
			sbHTML.append(" <tr class='listTR' onmouseover=\"this.className='listTRMouseover'\" onmouseout=\"this.className='listTR'\" onClick=\"selectOrgan('','')\" >");
			sbHTML.append(" <td class='listTD' >").append((count)).append(".</td> ");
			sbHTML.append(" <td class='listTD' >&nbsp;</td> ");
			sbHTML.append(" <td class='listTD' >&nbsp;</td> ");
			sbHTML.append(" </tr> ");			
			if (count==0){
				sbHTML.append(" <tr class='listTR' ><td class='listTD' colspan='3' style='color:red'>查無資料，請您重新輸入查詢條件！</td></tr>");
			}			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.closeAll();
		}
		return sbHTML.toString();
	}		
	
	public String getPersonList(String sql) throws Exception{
		StringBuffer sbHTML = new StringBuffer("");
		Database db = new Database();
		int count = 0;
		try{
			if (sql!=null && sql.trim().length()>0) {
				ResultSet rs = db.querySQL(sql , true);				
				processCurrentPageAttribute(rs);
				if (getTotalRecord() > 0) {
					count = getRecordStart();
					int end = getRecordEnd();
					do {
						if (count > end)
							break;
						sbHTML.append(" <tr class='listTR' onmouseover=\"this.className='listTRMouseover'\" onmouseout=\"this.className='listTR'\" onClick=\"selectOrgan('").append(Common.checkGet(rs.getString("seq"))).append("','").append(Common.checkGet(rs.getString("applyID"))).append("','").append(Common.checkGet(rs.getString("applyName"))).append("','").append(Common.checkGet(rs.getString("liveAdd4"))).append("')\" >");
						sbHTML.append(" <td class='listTD' >").append(count).append(".</td> ");
						sbHTML.append(" <td class='listTD' >").append(Common.checkGet(rs.getString("seq"))).append("</td> ");
						sbHTML.append(" <td class='listTD' >").append(Common.checkGet(rs.getString("applyID"))).append("</td> ");
						sbHTML.append(" <td class='listTD' >").append(Common.checkGet(rs.getString("applyName"))).append("</td> ");
						sbHTML.append(" <td class='listTD' >").append(Common.checkGet(rs.getString("liveAdd4"))).append("</td> ");						
						sbHTML.append(" </tr> ");	
						count++;
					} while (rs.next());
				}
			}
			//sbHTML.append(" <tr class='listTR' onmouseover=\"this.className='listTRMouseover'\" onmouseout=\"this.className='listTR'\" onClick=\"selectOrgan('','')\" >");
			//sbHTML.append(" <td class='listTD' >").append((count)).append(".</td> ");
			//sbHTML.append(" <td class='listTD' >&nbsp;</td> ");
			//sbHTML.append(" <td class='listTD' >&nbsp;</td> ");
			//sbHTML.append(" <td class='listTD' >&nbsp;</td> ");
			//sbHTML.append(" <td class='listTD' >&nbsp;</td> ");
			//sbHTML.append(" </tr> ");			
			if (count==0){
				sbHTML.append(" <tr class='listTR' ><td class='listTD' colspan='3' style='color:red'>查無資料，請您重新輸入查詢條件！</td></tr>");
			}			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.closeAll();
		}
		return sbHTML.toString();
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
	
	/**
	 * 會格式化SQL Database 的formatSQL 有bug...不是很建議使用
	 * @param sql 
	 * @return
	 * @throws Exception
	 */
	public String getQueryList(String sql) throws Exception{
		return getQueryList(sql, true);
	}

	/**
	 * @param sql	
	 * @param formatSQL	 : false : 不重新formatSQL 傳什麼SQL 就照樣執行  ; true 則會重新format SQL
	 * @return
	 * @throws Exception
	 */
	public String getQueryList(String sql, boolean formatSQL) throws Exception{
		StringBuffer sbHTML = new StringBuffer("");
		Database db = new Database();
		ResultSet rs = null;
		int count = 0;
		try{
			if (sql!=null && sql.trim().length()>0) {
				if (formatSQL) {
					rs = db.querySQL(sql, true);
				} else {
					rs = db.querySQLByScroll_NoChange(sql);
				}
				processCurrentPageAttribute(rs);
				if (getTotalRecord() > 0) {
					count = getRecordStart();
					int end = getRecordEnd();
					do {
						if (count > end)
							break;
						sbHTML.append(" <tr class='listTR' onmouseover=\"this.className='listTRMouseover'\" onmouseout=\"this.className='listTR'\" onClick=\"selectProperty('").append(Common.checkGet(rs.getString(1))).append("','").append(Common.checkGet(rs.getString(2))).append("','')\" >");
						sbHTML.append(" <td class='listTD' >").append(count).append(".</td> ");
						sbHTML.append(" <td class='listTD' >").append(Common.checkGet(rs.getString(1))).append("</td> ");
						sbHTML.append(" <td class='listTD' >").append(Common.checkGet(rs.getString(2))).append("</td> ");
						sbHTML.append(" </tr> ");	
						count++;
					} while (rs.next());
				}
			}
		
			if (count==0){
				sbHTML.append(" <tr class='listTR' ><td class='listTD' colspan='3' style='color:red'>查無資料，請您重新輸入查詢條件！</td></tr>");
			}			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (rs != null) {
				try {
					rs.close();
					rs = null;
				} catch (Exception e) {
					// ignore
				}
			}
			db.closeAll();
		}
		return sbHTML.toString();
	}
}
