<%@page import="java.sql.ResultSet"%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="../home/head.jsp" %>
<%@ page import="org.json.simple.*"%>
<%
//如果沒有設定驗證filter，請自行加權限驗證，否則請勿使用本程式..

	response.addHeader("Pragma", "No-cache");
	response.addHeader("Cache-Control", "no-cache");
	String q = Common.checkGet(request.getParameter("q"));
	
	Database db = new Database();
	ResultSet rs = null;
	try {	
		JSONObject ret = new JSONObject();
		ret.put("cnt", 0);
		if (!"".equals(q)) {
			String[] keyArray = q.split(",");
			// A27040000G|111|10411
			StringBuilder subCondition = new StringBuilder();
			for (String tmpKey : keyArray) {
				String[] detail  = tmpKey.split("\\|");
				
				
				if (subCondition.length() > 0) {
					subCondition.append(" or ");
				}
				
				String deprYM = Datetime.getDateAdd("m", 1, detail[2] + "01");
				//System.out.println(deprYM);
				deprYM = Datetime.changeTaiwanYYMM(deprYM.substring(0, 5), "2");
				
				subCondition.append("(enterorg=").append(Common.sqlChar(detail[0]))
										.append(" and differencekind=").append(Common.sqlChar(detail[1]))
							            .append(" and deprym=").append(Common.sqlChar(deprYM)).append(")");
				
			}
			
// select count(*) from UNTDP_MONTHDEPR where isnull(verify, '') <> 'Y' 
// and ((enterorg = 'A27040000G' and differencekind = '111' and deprym ='201511') or (enterorg = 'A27040000G' and differencekind = '112' and deprym ='201511'))

			StringBuilder sql = new StringBuilder();
			sql.append("select count(*) as cnt from UNTDP_MONTHDEPR where isnull(verify, '') <> 'Y' ")
			     .append(" and (").append(subCondition.toString()).append(")");
			
			System.out.println(sql.toString());
			
			rs = db.querySQL(sql.toString());
			int cnt = 0;
			if (rs.next()) {
				cnt = rs.getInt("cnt");
			}
			ret.put("cnt", cnt);
		}
		out.write(ret.toString());
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
		
		if (db != null) {
			db.closeAll();
		}
	}
%>


