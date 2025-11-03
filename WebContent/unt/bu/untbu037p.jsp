<!--
*<br>程式目的：建物毀損報廢單報表檔 
*<br>程式代號：untbu037r
*<br>撰寫日期：95/12/29
*<br>程式作者：Jim Chou
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

<jsp:useBean id="obj" scope="request" class="unt.bu.UNTBU037R">
    <jsp:setProperty name="obj" property="*"/>
</jsp:useBean>
<%
	out.clear();
 
    TableModelReportEnvironment env = new TableModelReportEnvironment();
	javax.swing.table.DefaultTableModel model = obj.getResultModel();

	env.setTableModel(model);
    env.setHttpFileName("UNTBU037R");
	env.setJasperFile(this.getServletContext().getRealPath("/report/unt/bu/untbu037r.jasper"));
	env.setFormat(ReportEnvironment.VAL_FORMAT_XLS);
	
    HashMap parms = new HashMap();
    
    TableModelReportGenerator generator = new TableModelReportGenerator(env);
    generator.reportToHttpResponse(request, response, parms);
	out = pageContext.pushBody();
	
%>
