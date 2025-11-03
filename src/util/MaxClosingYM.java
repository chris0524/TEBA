/*
*<br>程式目的：取得最大月結年月
*<br>程式日期：0950801
*<br>程式作者：Cherry
*<br>--------------------------------------------------------
*<br>修改作者　　修改日期　　　修改目的
*<br>--------------------------------------------------------
*/

package util;

import java.sql.ResultSet;

public class MaxClosingYM extends SuperBean{

 /**
 * @deprecated 沒有這個table .... 若有引用 請檢察邏輯是否正確
 */
static public String getMaxClosingYM(String enterOrg){
	Database db = new Database();
	ResultSet rs;	
	String closingYM ="" ;
	String sql="select max(closingYM) as closingYM from UNTGR_CLOSING " +
		" where enterOrg = " + Common.sqlChar(enterOrg) +
		"";	
	//System.out.println("取得最大的月結年月的SQL: \n"+sql);
	try {		
		rs = db.querySQL(sql);
		if (rs.next()){
		    closingYM = rs.getString("closingYM")==null?"00000":rs.getString("closingYM");
		}else{
			closingYM = "00000";
		}
	} catch (Exception e) {
		e.printStackTrace();
	} finally {
		db.closeAll();
	}
	return closingYM;
}

/**
 * @deprecated 沒有這個table .... 若有引用 請檢察邏輯是否正確
 */
static public String getMaxClosingYM_Nonexp(String enterOrg){
	Database db = new Database();
	ResultSet rs;	
	String closingYM ="" ;
	String sql="select max(closingYM) as closingYM from UNTGR_CLOSING1 " +
		" where enterOrg = " + Common.sqlChar(enterOrg) +
		"";	
	//System.out.println("取得最大的月結年月的SQL: \n"+sql);
	try {		
		rs = db.querySQL(sql);
		if (rs.next()){
		    closingYM = rs.getString("closingYM")==null?"00000":rs.getString("closingYM");
		}else{
			closingYM = "00000";
		}
	} catch (Exception e) {
		e.printStackTrace();
	} finally {
		db.closeAll();
	}
	return closingYM;
}

/**
 * <br>目的：取得執行SQL後的值
 * <br>參數：執行的SQL
 * <br>傳回：執行後SQL所查出的值
 */
static public String getCountValues(String sql){
	Database db = new Database();
	ResultSet rs;	
	String count ="" ;
	try {		
		rs = db.querySQL(sql);
		if (rs.next()){
		    count = rs.getString("count");
		}
	} catch (Exception e) {
		e.printStackTrace();
	} finally {
		db.closeAll();
	}
	return count;
}

/**
 * <br>目的：取得XX單編號－號
 * <br>參數：table名稱
 * <br>傳回：最大的XX單編號-號
 * @deprecated 要建立非本年度的單會有問題，請改用傳入編號-年的方法
 * {@link MaxClosingYM#getMaxProofNo(table, enterOrg, ownership, proofyear)}
 */
static public String getMaxProofNo(String table,String enterOrg,String ownership){
	return getMaxProofNo(table, enterOrg, ownership, Datetime.getYYY());
}

/**
 * <br>目的：取得XX單編號－號
 * <br>參數：table名稱
 * <br>傳回：最大的XX單編號-號
 */
static public String getMaxProofNo(String table,String enterOrg,String ownership, String proofyear){
	Database db = new Database();
	ResultSet rs;	
	String maxProofNo = "";
	try {		
		String sql = "select case max(CAST(proofno  AS int)) when null then '' else (max(CAST(proofno  AS int))+1) end as proofNo from " + table +
					" where enterOrg = " + Common.sqlChar(enterOrg) +
//					" and ownership = " + Common.sqlChar(ownership) +
					" and substring(proofyear,1,3) = " + Common.sqlChar(Common.formatFrontZero(proofyear,3)) + 
					"";
		rs = db.querySQL(sql);
		if (rs.next()){
			maxProofNo = rs.getString("proofNo");
			if("".equals(Common.get(maxProofNo))){
				maxProofNo = "0000001";
			}else{
				maxProofNo = Common.formatFrontZero(maxProofNo,7);
			}			
		} else {
			maxProofNo = "0000001";
		}
	} catch (Exception e) {
		e.printStackTrace();
	} finally {
		db.closeAll();
	}
	return maxProofNo;
}

}


