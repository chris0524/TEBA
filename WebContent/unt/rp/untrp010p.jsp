<!--
*<br>程式目的：財產保管人清冊報表檔 
*<br>程式代號：untrp010r
*<br>撰寫日期：103/08/12
*<br>程式作者：Anthony.Wang
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

<jsp:useBean id="obj" scope="request" class="unt.rp.UNTRP010R">
    <jsp:setProperty name="obj" property="*"/>
</jsp:useBean>
<%
	out.clear();

    TableModelReportEnvironment env = new TableModelReportEnvironment();
	javax.swing.table.DefaultTableModel model = obj.getResultModel();

	env.setTableModel(model);
    env.setHttpFileName("UNTRP010R");
	env.setJasperFile(this.getServletContext().getRealPath("/report/unt/rp/untrp010r.jasper"));
	env.setFormat(obj.getReportType());
	
	String datetime=util.Datetime.getYYYMMDD();
	datetime=datetime.substring(0,3) + "年" + datetime.substring(3,5) + "月" + datetime.substring(5)+ "日";

    HashMap parms = new HashMap();
    parms.put("todaydate", datetime);
    parms.put("USERNAME", obj.getUserName());
    TableModelReportGenerator generator = new TableModelReportGenerator(env);
    generator.reportToHttpResponse(request, response, parms);
	out = pageContext.pushBody();

%>
