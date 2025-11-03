<!--
*<br>程式目的：公有房屋及附著物拆除改建(報廢)查核報告表查詢檔 
*<br>程式代號：untbu014r
*<br>撰寫日期：94/11/22
*<br>程式作者：chris
*<br>--------------------------------------------------------
*<br>修改作者　　修改日期　　　修改目的
     Jim Chou 096/01/09    a4 paper --- a3 paper 
*<br>--------------------------------------------------------
-->

<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../home/head.jsp" %>
<%@ page import="java.sql.*"%>
<%@ page import="java.util.*"%>
<%@ page import="util.report.*"%>
<%@ page import="net.sf.jasperreports.engine.*"%>

<jsp:useBean id="obj" scope="request" class="unt.bu.UNTBU014R">
    <jsp:setProperty name="obj" property="*"/>
</jsp:useBean>
<%
	out.clear();

    TableModelReportEnvironment env = new TableModelReportEnvironment();
	javax.swing.table.DefaultTableModel model = obj.getResultModel();

	env.setTableModel(model);
	env.setHttpFileName("UNTBU014R");
	env.setJasperFile(this.getServletContext().getRealPath("/report/unt/bu/untbu014r.jasper"));
	env.setFormat(ReportEnvironment.VAL_FORMAT_XLS);
// 	env.setXLSPageSize(ReportEnvironment.VAL_XLS_PAGE_SIZE_A3_PAPERSIZE);
	//env.setFormat(ReportEnvironment.VAL_FORMAT_HTML);
	

    HashMap parms = new HashMap();
	//parms.put("enterorgname", obj.getEnterOrgName());
	//parms.put("todaydate", util.Datetime.getYYYMMDD());
    
    TableModelReportGenerator generator = new TableModelReportGenerator(env);
    generator.reportToHttpResponse(request, response, parms);
	out = pageContext.pushBody();
	
%>
