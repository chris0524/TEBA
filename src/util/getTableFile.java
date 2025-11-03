/*
*<br>程式目的：
*<br>程式代號：getTableFile
*<br>撰寫日期：
*<br>程式作者：KangDa Info
*<br>--------------------------------------------------------
*<br>修改作者　　修改日期　　　修改目的
*<br>--------------------------------------------------------
*<br>
*/

package util;

import java.sql.ResultSet;

public class getTableFile {
	
/**
 *	取得某資料庫查詢!!單一位位值
 *	傳入：sql_code：sql查詢字串　getFile：回傳欄位名稱  isNull：若無資料回傳值
 * 傳出：查詢欄位值
**/
static public String query_value( String sql_code ,String getFile ,String isNull ){
	String reValue="";
	Database db = new Database();
	ResultSet rs;

		try{
			rs = db.querySQL(sql_code);
			if(rs.next()){
				reValue=Common.get(rs.getString(getFile));
			}else{
				reValue=isNull;
			}
			rs.getStatement().close();
		} catch (Exception e) {
			e.printStackTrace();
//			System.out.println("DB-CODE:"+sql_code);
		} finally {
			db.closeAll();
		}	
	return reValue;
}
	
/**
 *	取得某資料庫查詢!!單一位位值
 *	傳入：sql_code：sql查詢字串　getFile：回傳欄位名稱  isNull：若無資料回傳值
 * 傳出：查詢欄位值
**/
static public String getOrgName( String orgID ){
	String reValue="";
	String sql_code = " select organid ,organsname from  sysca_organ "
					+ " where organid = " + Common.sqlChar(orgID)
					+ "";
	Database db = new Database();
	ResultSet rs;
		
		try{
			rs = db.querySQL(sql_code);
			if(rs.next()){
				reValue=Common.get(rs.getString("organsname"));
			}
			rs.getStatement().close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.closeAll();
		}

	return reValue;
}
	
/**
 *	取得某資料庫查詢!!單一位位值
 *	傳入：sql_code：sql查詢字串　getFile：回傳欄位名稱  isNull：若無資料回傳值
 * 傳出：查詢欄位值
**/
static public String fundmanage( String closeYM ){
	String reValue="";
	String sql_code = " select a.closeym ,a.enterorg ,o.organsname  " + "\n"
					+ "        ,b.codename||decode(a.propertyclass ,'1','土地','2','房屋建築及設備','3','動產設備')||'價值為'||ltrim(rtrim(to_char(a.closevalue ,'999G999G999G999G990')))||'元' as printData " + "\n"
					+ " from untgr_fundmanage a ,sysca_code b ,sysca_organ o " + "\n"
					+ " where 1=1 " + "\n"
					+ " and a.enterorg = o.organid " + "\n"
					+ " and a.fundcode = b.codeid " + "\n"
					+ " and a.enterorg = b.codecon1 " + "\n"
					+ " and b.codekindid = 'FNM' " + "\n"
					+ " and a.closeym = ( select max(ax.closeym) from untgr_fundmanage ax  " + "\n"
					+ "                   where ax.enterorg = a.enterorg and ax.fundcode = a.fundcode  " + "\n"
					+ "                   and ax.propertyclass = a.propertyclass " + "\n"
					+ " 				  and ax.closeym <= substr(" + Common.sqlChar(closeYM) + ",1,5)) " + "\n"
					+ " order by a.fundcode ,a.propertyclass ";
	
	//System.out.println(sql_code);
	Database db = new Database();
	ResultSet rs;
		try{
			rs = db.querySQL(sql_code);
			while(rs.next()){
				reValue += Common.get(rs.getString("printData")) + "，";
			}
			if(!"".equals(reValue)){
				reValue = reValue.substring( 0 ,reValue.length() -1 );
			}
			rs.getStatement().close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.closeAll();
		}
	
	return reValue;
}

static public String getOwnershipName( String own ){
	String reValue="";
	String sql_code = " select x.codeName from sysca_code x "
					+ "	where x.codeid = " + Common.sqlChar(own)
					+ " and x.codekindid = 'OWA' "
					+ "";
	Database db = new Database();
	ResultSet rs;
		
		try{
			rs = db.querySQL(sql_code);
			if(rs.next()){
				reValue=Common.get(rs.getString("codeName"));
			}
			rs.getStatement().close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.closeAll();
		}

	return reValue;
}
}
