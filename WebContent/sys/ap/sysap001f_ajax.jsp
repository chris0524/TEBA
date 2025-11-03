<%@ page contentType="application/xml;charset=UTF-8" import="java.sql.*,util.*"%>
<%@ page import="org.json.simple.*"%>
<%

String organID = Common.checkGet(request.getParameter("organID"));
String keeperno = Common.checkGet(request.getParameter("keeperno"));
String[] tables = {"UNTLA_LAND", "UNTBU_BUILDING", "UNTRF_ATTACHMENT", "UNTMP_MOVABLEDETAIL", "UNTNE_NONEXPDETAIL"};
String tableName = "";
String sql = "";
ResultSet rs;
JSONObject item = new JSONObject();

	Database db = new Database();
	try {
		
		for (String table : tables) {
			if (table.equals("UNTLA_LAND")) {
				tableName = "土地主檔";
			}
			if (table.equals("UNTBU_BUILDING")) {
				tableName = "建物主檔";
			}
			if (table.equals("UNTRF_ATTACHMENT")) {
				tableName = "土改主檔";
			}
			if (table.equals("UNTMP_MOVABLEDETAIL")) {
				tableName = "動產主檔";
			}
			if (table.equals("UNTNE_NONEXPDETAIL")) {
				tableName = "物品主檔";
			}
			
			sql =" select TOP 1 " +
				 " (select a.organsname from SYSCA_ORGAN a where  a.organid like enterorg ) as organsname, " +
				 " (select b.codename from SYSCA_CODE b where b.codekindid = 'OWA' and b.codeid = ownership) as ownership, " +
				 " (select c.codename from SYSCA_CODE c where c.codekindid = 'DFK' and c.codeid = differencekind) as differencekind, " +
				 " propertyno, serialno, " +
				 " (select count(*) from " + table + " where ( keeper = '" + Common.esapi(keeperno) + "' or " + " userno = '" + Common.esapi(keeperno) + "' )  and datastate = '1' and enterorg = '" + Common.esapi(organID) + "') as count " +
				 " from " + table +
				 " where ( keeper = '" + Common.esapi(keeperno) + "'" +
		 		 " or userno = '" + Common.esapi(keeperno) + "' ) " +
				 " and enterorg = '" + Common.esapi(organID) + "'" +
				 " and datastate = '1' " +
				 " order by ownership, differencekind asc ";
			
			rs = db.querySQL(sql);
			if (rs.next()) {
				item.put("organsname", Common.checkGet(rs.getString("organsname")));
				item.put("ownership", Common.checkGet(rs.getString("ownership")));
				item.put("differencekind", Common.checkGet(rs.getString("differencekind")));
				item.put("propertyno", Common.checkGet(rs.getString("propertyno")));
				item.put("serialno", Common.checkGet(rs.getString("serialno")));
				item.put("count", Common.checkGet(rs.getString("count")));
			}
		}
		response.getWriter().write(item.toString());
		
	} catch (Exception e) {
		e.printStackTrace();
		item.put("msg", "財產查詢發生錯誤");
		response.getWriter().write(item.toString());
	} finally {
		db.closeAll();
	}	
	
%>
