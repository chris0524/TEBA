<!--
*<br>程式目的：動產標籤查詢檔 
*<br>程式代號：untmp008p
*<br>撰寫日期：94/11/26
*<br>程式作者：Cherry
*<br>--------------------------------------------------------
*<br>修改作者　　修改日期　　　修改目的
*<br>--------------------------------------------------------
-->

<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../home/head.jsp" %>
<%@ page import="java.util.*"%>
<%@ page import="util.report.*"%>

<jsp:useBean id="obj" scope="request" class="unt.mp.UNTMP051R01">
    <jsp:setProperty name="obj" property="*"/>
</jsp:useBean>
<%
	out.clear();

    TableModelReportEnvironment env = new TableModelReportEnvironment();
	javax.swing.table.DefaultTableModel model = obj.getResultModel();

	env.setTableModel(model);
	env.setHttpFileName("UNTMP051R01");
	env.setJasperFile(this.getServletContext().getRealPath("/report/unt/mp/untmp051r01.jasper"));
	env.setFormat(obj.getQ_reportType());
	//env.setFormat(ReportEnvironment.VAL_FORMAT_XLS);
	//env.setFormat(ReportEnvironment.VAL_FORMAT_HTML);
	

   	java.util.HashMap<String, Object> parms = new java.util.HashMap<String, Object>();
    // System.out.println("xx"+obj.getQ_printType());
	//parms.put("option",obj.getQ_printType());
	//parms.put("todaydate", util.Datetime.getYYYMMDD());
  
    TableModelReportGenerator generator = new TableModelReportGenerator(env);
    generator.reportToHttpResponse(request, response, parms);
	out = pageContext.pushBody();
	
%>
