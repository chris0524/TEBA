<%@page import="java.util.HashMap"%>
<%@page import="java.util.Map"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="util.Datetime"%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="org.json.simple.*"%>
<%@ page import="net.sf.json.JSONObject"%>
<%@ page import="util.Common"%>
<%@ page import="util.Database"%>
<%

String action = Common.checkGet(request.getParameter("action"));

String enterorg = Common.checkGet(request.getParameter("enterorg"));
String ownership = Common.checkGet(request.getParameter("ownership"));
String differencekind = Common.checkGet(request.getParameter("differencekind"));
String propertyno = Common.checkGet(request.getParameter("propertyno"));
String serialno = Common.checkGet(request.getParameter("serialno"));

Map<String, Object> data = null;
Database db = new Database();
ResultSet rs = null;
try {		
	JSONObject jObj = new JSONObject();
	boolean isSuccess = false;
	
	if ("getDataOfEndOfLastYear".equals(action)) {
		String lastCEYear = Datetime.changeTaiwan(Datetime.getDateAdd("y", -1, Datetime.getYYYMMDD()).substring(0, 3), "2");
		StringBuilder sql = new StringBuilder();
		sql.append(" select a.newaccumdepr ")
		     .append(" from UNTDP_MONTHDEPR a")
		     .append(" where 1=1 ")
		     .append(" and a.enterorg=").append(Common.sqlChar(enterorg))
		     .append(" and a.ownership=").append(Common.sqlChar(ownership))
		     .append(" and a.differencekind=").append(Common.sqlChar(differencekind))
		     .append(" and a.propertyno=").append(Common.sqlChar(propertyno))
		     .append(" and a.serialno=").append(Common.sqlChar(serialno))
		     .append(" and a.deprym=").append(Common.sqlChar(lastCEYear + "12"));
		rs = db.querySQL(sql.toString());
		if (rs.next()) {
			isSuccess = true;
			data = new HashMap<String, Object>();
			data.put("newaccumdepr", rs.getString("newaccumdepr"));
			jObj.put("data", data);
		} else {
			jObj.put("msg", "查無資料");
		}
	} else {
		jObj.put("msg", "未知的action");
	}
	jObj.put("isSuccess", isSuccess);
	out.write(jObj.toString());
} finally {
	if (rs != null) {
		try {
			rs.close();
			rs = null;
		} catch (Exception e) {
			//ignore
		}
	}
	db.closeAll();
}
%>
