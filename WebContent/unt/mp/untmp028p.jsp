<!--
*<br>程式目的：動產增減值單報表檔 
*<br>程式代號：untmp028r
*<br>撰寫日期：94/11/24
*<br>程式作者：chris
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

<jsp:useBean id="obj" scope="request" class="unt.mp.UNTMP028R">
    <jsp:setProperty name="obj" property="*"/>
</jsp:useBean>
<%
	out.clear();

    TableModelReportEnvironment env = new TableModelReportEnvironment();
	javax.swing.table.DefaultTableModel model = obj.getResultModel();

	env.setTableModel(model);
	env.setHttpFileName("UNTMP028R");
	env.setJasperFile(this.getServletContext().getRealPath("/report/unt/mp/untmp028r.jasper"));
	env.setFormat(ReportEnvironment.VAL_FORMAT_XLS);
	//env.setFormat(ReportEnvironment.VAL_FORMAT_HTML);
	

    HashMap parms = new HashMap();
	parms.put("keeperDetailPath", this.getServletContext().getRealPath("/report/unt/mp/keeperDetail.jasper"));
	parms.put("mp028rDetrilPath", this.getServletContext().getRealPath("/report/unt/mp/untmp028rDetail.jasper"));

    TableModelReportGenerator generator = new TableModelReportGenerator(env);
    generator.reportToHttpResponse(request, response, parms);
	out = pageContext.pushBody();
	
%>
