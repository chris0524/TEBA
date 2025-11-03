<!--
*<br>程式目的：折舊預估表(已入帳)
*<br>程式代號：
*<br>撰寫日期：
*<br>程式作者：
*<br>--------------------------------------------------------
*<br>修改作者　　修改日期　　　修改目的
*<br>--------------------------------------------------------
-->

<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../home/head.jsp" %>
<%@ page import="java.sql.*"%>
<%@ page import="java.util.*"%>
<%@ page import="util.report.*"%>
<%@ page import="net.sf.jasperreports.engine.*"%>

<jsp:useBean id="obj" scope="request" class="unt.dp.UNTDP008R">
    <jsp:setProperty name="obj" property="*"/>
</jsp:useBean>
<%
	out.clear();

    TableModelReportEnvironment env = new TableModelReportEnvironment();
	javax.swing.table.DefaultTableModel model = obj.getResultModel();
	
	env.setTableModel(model);
	env.setHttpFileName("UNTDP008R");
	env.setJasperFile(this.getServletContext().getRealPath("/report/unt/dp/untdp008r.jasper"));
	env.setFormat(ReportEnvironment.VAL_FORMAT_XLS);
	String datetime=util.Datetime.getYYYMMDD();
	datetime=Common.MaskCDate(datetime);

	HashMap<String,Object> parms = new HashMap<String,Object>();
	parms.put("todaydate", datetime);
	parms.put("USERNAME", obj.getUserName());
    
	TableModelReportGenerator generator = new TableModelReportGenerator(env);
	generator.reportToHttpResponse(request, response, parms);
    out = pageContext.pushBody();
	
%>
