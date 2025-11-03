<%@ page contentType="application/xml;charset=UTF-8" import="java.sql.*,util.*"%>
<%

String organID = Common.checkGet(request.getParameter("organID"));
String keeperno = Common.checkGet(request.getParameter("keeperno"));
StringBuffer matchStr = new StringBuffer();
String[] tables = {"UNTLA_LAND", "UNTBU_BUILDING", "UNTRF_ATTACHMENT", "UNTMP_MOVABLEDETAIL", "UNTNE_NONEXPDETAIL"};
String tableName = "";
String sql = "";

StringBuffer strXML = new StringBuffer();
strXML.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
strXML.append("<ResultSet>");

	Database db = new Database();
	try {		
		for (String table : tables) {
			
			if (table.equals("UNTLA_LAND")) 		 {tableName = "土地主檔";}
			if (table.equals("UNTBU_BUILDING")) 	 {tableName = "建物主檔";}
			if (table.equals("UNTRF_ATTACHMENT")) 	 {tableName = "土改主檔";}
			if (table.equals("UNTMP_MOVABLEDETAIL")) {tableName = "動產主檔";}
			if (table.equals("UNTNE_NONEXPDETAIL"))  {tableName = "物品主檔";}
			
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
			
				ResultSet rs = db.querySQL(sql);
				while (rs.next()){
					if (!rs.getString("count").equals("0")) {
						matchStr.append("此使用者在 " + tableName + " 中尚有保管/使用之財產").append("\r\n");
						matchStr.append("入帳機關：   " + Common.get(rs.getString("organsname"))).append("\r\n");
						matchStr.append("權屬：           " + Common.get(rs.getString("ownership"))).append("\r\n");
						matchStr.append("財產區分別：" + Common.get(rs.getString("differencekind"))).append("\r\n");
						matchStr.append("財產編號：    " + Common.get(rs.getString("propertyno"))).append("\r\n");
						matchStr.append("財產分號：    " + Common.get(rs.getString("serialno")));
						if (rs.getString("count").equals("1")) {
							matchStr.append("\r\n\r\n");
						} else {							
							matchStr.append("                  等共 " + rs.getString("count") + " 筆").append("\r\n\r\n");							
						}
					}
				}						
		}
	} catch (Exception e) {
		matchStr.append("使用者保管/使用財產查詢錯誤，請洽相關人員處理");
		e.printStackTrace();
	} finally {
		db.closeAll();
		strXML.append("<record matchStr=\"" + matchStr.toString() + "\" /> ");
		strXML.append("</ResultSet>");	
		out.write(strXML.toString());
	}	
%>
