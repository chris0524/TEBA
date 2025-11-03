<!--
*<br>程式目的：土地改良物減值毀損單
*<br>程式代號：untrf025p
*<br>撰寫日期：09705/22
*<br>程式作者：shan
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

<jsp:useBean id="obj" scope="request" class="unt.rf.UNTRF025R">
    <jsp:setProperty name="obj" property="*"/>
</jsp:useBean>
<%
	out.clear();
    TableModelReportEnvironment env = new TableModelReportEnvironment();
	javax.swing.table.DefaultTableModel model = obj.getResultModel();

	env.setTableModel(model);
	env.setHttpFileName("UNTRF025R");
	env.setJasperFile(this.getServletContext().getRealPath("/report/unt/rf/untrf025r.jasper"));
	env.setFormat(ReportEnvironment.VAL_FORMAT_XLS);
	//env.setFormat(ReportEnvironment.VAL_FORMAT_PDF);
	//env.setFormat(ReportEnvironment.VAL_FORMAT_HTML);
	
    HashMap parms = new HashMap();

    TableModelReportGenerator generator = new TableModelReportGenerator(env);
    generator.reportToHttpResponse(request, response, parms);
	out = pageContext.pushBody();
%>
