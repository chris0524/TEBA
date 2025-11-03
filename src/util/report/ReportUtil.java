package util.report;

import java.sql.ResultSet;

import util.Common;
import util.Database;
import util.TCGHCommon;

/**
 * <br/>程式目的：報表工具
 * <br/>程式代號：ReportUtil
 * <br/>程式日期：104/05/27
 * <br/>程式作者：Kang Da
 * <br/>--------------------------------------------------------
 * <br/>修改作者　　     修改日期　　　			修改目的
 * <br/>--------------------------------------------------------
 */
public class ReportUtil {
	
	/**
	 * 根據客戶646  科技部-基金類 報表抬頭一率顯示  [行政院國家科學技術發展基金]
	 * @return 
	 */
	public static String getTitleByEnterOrgName(String enterOrgName, String differenceKind) {
		if ("行政院國家科學及技術委員會".equals(enterOrgName) && "120".equals(differenceKind)) {
			return "行政院國家科學技術發展基金";
		} else {
			return enterOrgName;
		}
	}
	
	/**
	 * 根據客戶646  科技部-基金類 報表抬頭一率顯示  [行政院國家科學技術發展基金]
	 * @param enterOrg
	 * @param differenceKind
	 * @return
	 */
	public static String getTitleByEnterOrgCode(String enterOrg, String differenceKind) {
		if (TCGHCommon.getSYSCODEName("01", "ETO").equals(enterOrg) && "120".equals(differenceKind)) {
			return "行政院國家科學技術發展基金";
		} else {
			return TCGHCommon.getLookup("select organaname from SYSCA_ORGAN where organid = " + Common.sqlChar(enterOrg));
		}
	}
	
	public static String getTitleByEnterOrgCodeNew(String enterOrg, String differenceKind) {
		if (TCGHCommon.getSYSCODEName("01", "ETO").equals(enterOrg) && "120".equals(differenceKind)) {
			return "行政院國家科學技術發展基金\r\n";
		} else {
			return TCGHCommon.getLookup("select titlename1 from SYSCA_ORGAN where organid = " + Common.sqlChar(enterOrg)) + "\r\n" + TCGHCommon.getLookup("select titlename2 from SYSCA_ORGAN where organid = " + Common.sqlChar(enterOrg));
		}
	}
	
	/**
	 * 客戶 750   加印原分號需求僅限三個機關(竹科管局(A27040000G)、竹科實中(A10120000U)、中科實中(A102V0000U))
	 * @param enterOrg
	 * @param oriFieldValue
	 * @param oldserialnoName : 原物品分號 | 原財產分號
	 * @param oldserialno
	 * @return
	 */
	public static String getStringWithOldSerialNo(String enterOrg, String oriFieldValue, String oldserialnoName, String oldserialno) {
		return getStringWithOldSerialNo(enterOrg, oriFieldValue, "", oldserialnoName, oldserialno);
	}
	
	/**
	 * 
	 * @param enterOrg
	 * @param oriFieldValue
	 * @param separator       : 物品分號|財產分號  + separator + 原物品分號 | 原財產分號
	 * @param oldserialnoName : 原物品分號 | 原財產分號
	 * @param oldserialno
	 * @return
	 */
	public static String getStringWithOldSerialNo(String enterOrg, String oriFieldValue, String separator, String oldserialnoName, String oldserialno) {
		if (!"".equals(oldserialno) && 
		     (TCGHCommon.getSYSCODEName("02", "ETO").equals(enterOrg) || TCGHCommon.getSYSCODEName("05", "ETO").equals(enterOrg) || TCGHCommon.getSYSCODEName("06", "ETO").equals(enterOrg))) {
			oriFieldValue = oriFieldValue + separator + "(" + oldserialnoName + ":" + oldserialno + ")"; 
		}
		return oriFieldValue;
	}
	
	/**
	 * 取得 管理機關(基金)名稱, 若區分別為120 則為基金名稱, 反之則呼叫 {@link #getTitleByEnterOrgCode(String, String)}
	 * @param enterorg : organid
	 * @param dfk      : 財產區分別
	 * @return
	 */
	public static String getEnterOrgByDFK(String enterorg, String dfk) throws Exception {
		String organAName = "";
		if ("120".equals(dfk)) {
			Database db = new Database();
			ResultSet rs = null;
			try {
				StringBuilder sql = new StringBuilder();
				sql.append(" select b.codename from SYSCA_FUNDORGAN a, SYSCA_CODE b where 1=1 ")
				   .append(" and a.enterorg = ").append(Common.sqlChar(enterorg))
				   .append(" and b.codekindid = 'FUD' and a.fundno = b.codeid");
				
				rs = db.querySQL(sql.toString());
				if (rs.next()) {
					organAName = rs.getString(1);
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
		} else {
			organAName = ReportUtil.getTitleByEnterOrgCode(enterorg, dfk);
		}
		
		return organAName;
	}
}
