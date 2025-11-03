package unt.dp.util;

import java.sql.ResultSet;
import java.sql.Statement;

import util.Common;
import util.Database;


/**
 * <br/>程式目的：折舊相關共用method
 * <br/>程式代號：UNTDPUtil
 * <br/>程式日期：
 * <br/>程式作者：Kang Da
 * <br/>--------------------------------------------------------
 * <br/>修改作者　　     修改日期　　　			修改目的
 * <br/>
 * <br/>--------------------------------------------------------
 */
public class UNTDPUtil {
	
	/**
	 * 取得最後一次入帳的依比例－本期累計折舊  (ScalednewAccumDepr)
	 * @param enterorg			: 入帳機關  
	 * @param ownership			: 權屬
	 * @param differencekind	: 區分別
	 * @param propertyno		: 財產編號	
	 * @param serialno			: 財產分號
	 * @param deprpark			: 園區別
	 * @param deprunit			: 部門別
	 * @param deprunit1			: 單位別
	 * @param depraccount		: 會計科目
	 */
	public static long getLastScalednewAccumDepr(String enterorg, String ownership, String differencekind, String propertyno, String serialno, 
			                                     String deprpark, String deprunit, String deprunit1, String depraccounts) throws Exception {
		StringBuilder sql = new StringBuilder();
		
		sql.append(" select top 1 scalednewaccumdepr from UNTDP_MONTHDEPR where 1=1 ")
		   .append(" and enterorg = ").append(Common.sqlChar(enterorg))
		   .append(" and ownership = ").append(Common.sqlChar(ownership))
		   .append(" and differencekind = ").append(Common.sqlChar(differencekind))
		   .append(" and propertyno = ").append(Common.sqlChar(propertyno))
		   .append(" and serialno = ").append(Common.sqlChar(serialno))
		   .append(" and deprpark = ").append(Common.sqlChar(deprpark))
		   .append(" and deprunit = ").append(Common.sqlChar(deprunit))
		   .append(" and deprunit1 = ").append(Common.sqlChar(deprunit1))
		   .append(" and depraccounts = ").append(Common.sqlChar(depraccounts))
		   .append(" and verify = 'Y' ")
		   .append(" order by deprym desc");
		
		Database db = null;
		Statement statement = null;
		ResultSet rs = null;
		long retVal = 0;
		try {
			db = new Database();
			statement = db.getForwardStatement();
			
			rs = statement.executeQuery(sql.toString());
			
			if (rs.next()) {
				retVal = rs.getLong("scalednewaccumdepr");
			}
			
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (Exception e) {
					//ignore
				}
			}
			if (db != null) {
				db.closeAll();
			}
		}
		return retVal;
		
	}
	
}
