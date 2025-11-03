<!--
程式目的：計算減損單明細總數 用來做一些判斷, 回傳jsonStr   {"length", val}, 不傳任何參數會在此程式中噴錯, 但仍會回傳length = 0
程式代號：queryReduceDetailCount
程式日期：1040528
程式作者：Kang Da
--------------------------------------------------------
修改作者　　修改日期　　　修改目的
--------------------------------------------------------
-->
<%@page import="util.Common"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="util.Database"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%!

	public String getMainStatement(String mainTable, String leftJoinTable) {
	    if (!"".equals(leftJoinTable)) {
			return " select count(*) as length from "+mainTable+" a  left join "+leftJoinTable+" b on a.enterorg=b.enterorg and a.ownership=b.ownership and a.caseno=b.caseno ";
	    } else {
	    	return " select count(*) as length from " + mainTable + " a ";
	    }
    }
	public String getCondition(ServletRequest request) throws Exception {
		String enterorg = Common.get(request.getParameter("enterorg"));
		String ownership = Common.get(request.getParameter("ownership"));
		String caseno = Common.get(request.getParameter("caseno"));
		String differencekind = Common.get(request.getParameter("differencekind"));
		
		StringBuilder condition = new StringBuilder();
		if (!"".equals(enterorg)) {
			condition.append(" and a.enterorg = ").append(Common.sqlChar(enterorg));
		}
		
		if (!"".equals(caseno)) {
			condition.append(" and a.caseno = ").append(Common.sqlChar(caseno));
		}
		
		if (!"".equals(ownership)) {
			condition.append(" and a.ownership = ").append(Common.sqlChar(ownership));
		}
		
		if (!"".equals(differencekind)) {
			condition.append(" and a.differencekind = ").append(Common.sqlChar(differencekind));
		}
		
		if (condition.length() > 0) {
			condition.append("");
			condition.insert(0, " where 1=1 ");
			return condition.toString();
		} else {
			throw new Exception("queryReduceDetailCount 沒有傳入任何參數");
		}
	}
%>
<%
	
	int detailLength = 0;
	Database database = new Database();
	ResultSet rs = null;
	try {
		String condition = this.getCondition(request);
		StringBuilder sql = new StringBuilder();
		sql.append(" select sum(length) as sumLength from ( ")
			 .append(this.getMainStatement("UNTLA_REDUCEDETAIL", "UNTLA_REDUCEPROOF"))
			 .append(condition)
			 .append(" union ")
			 .append(this.getMainStatement("UNTBU_REDUCEDETAIL", "UNTBU_REDUCEPROOF"))
			 .append(condition)
			 .append(" union ")
			 .append(this.getMainStatement("UNTRF_REDUCEDETAIL", "UNTRF_REDUCEPROOF"))
			 .append(condition)
			 .append(" union ")
			 .append(this.getMainStatement("UNTMP_REDUCEDETAIL", "UNTMP_REDUCEPROOF"))
			 .append(condition)
			 .append(" union ")
			  .append(this.getMainStatement("UNTVP_REDUCEPROOF", ""))
			 .append(condition)
			 .append(" union ")
			 .append(this.getMainStatement("UNTRT_REDUCEPROOF", ""))
			 .append(condition)
			 .append(" ) as qq ");
	
		//System.out.println(sql.toString());

		rs = database.querySQL(sql.toString());
		if (rs.next()) {
			detailLength = rs.getInt("sumLength");
		}
	} catch(Exception e) {
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
	
	response.getWriter().write("{\"length\":" + detailLength + "}");
%>