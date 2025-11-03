<!--
*<br>程式目的：財產折舊月報明細表(公務財產)
*<br>程式代號：untdp024p
*<br>撰寫日期：104/11/16
*<br>程式作者：CHLin copy from untdp013p
*<br>--------------------------------------------------------
*<br>修改作者　　修改日期　　　修改目的
*<br>--------------------------------------------------------
-->
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../home/head.jsp" %>
<%@ page import="java.util.*"%>
<%@page import="org.apache.logging.log4j.*"%>
<%@ page import="util.report.*"%>

<jsp:useBean id="obj" scope="request" class="unt.dp.UNTDP024R">
    <jsp:setProperty name="obj" property="*"/>
</jsp:useBean>
<%
try {
	out.clear();

    TableModelReportEnvironment env = new TableModelReportEnvironment();
	javax.swing.table.DefaultTableModel model = obj.getResultModel();
	if (model != null) {
		env.setTableModel(model);
		env.setHttpFileName("UNTDP024R");
		env.setJasperFile(this.getServletContext().getRealPath("/report/unt/dp/untdp024r.jasper"));
		env.setFormat(obj.getQ_reportType());
		String datetime=util.Datetime.getYYYMMDD();
		datetime=datetime.substring(0,3) + "年" + datetime.substring(3,5) + "月" + datetime.substring(5)+ "日";
	
	    HashMap parms = new HashMap();
	    parms.put("todaydate", datetime);
	    parms.put("USERNAME", obj.getUserName());
	    parms.put("showDeprPark", "1".equals(obj.getQ_changePage())?true:false);
	    TableModelReportGenerator generator = new TableModelReportGenerator(env);
	    generator.reportToHttpResponse(request, response, parms);
		out = pageContext.pushBody();
	} else {
		out.write("查無資料");
	}
} catch(Exception e) {
	out.write("產製財產折舊月報明細表(公務財產)時發生錯誤");
	LogManager.getLogger(this.getClass()).error("產製財產折舊月報明細表(公務財產)時發生錯誤", e);
}
	
%>
