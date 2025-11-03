<!--
*<br>程式目的：採購財務物價調查表
*<br>程式代號：untgr031p
*<br>撰寫日期：103/08/26
*<br>程式作者：Jerry
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

<jsp:useBean id="obj" scope="request" class="unt.gr.UNTGR031R">
    <jsp:setProperty name="obj" property="*"/>
</jsp:useBean>
<%
	//String q_propertyKind2 = request.getParameter("q_propertyKind2");

	out.clear();
	obj.setFileName(this.getServletContext().getRealPath("\\sqlfile\\unt\\gr"));
    TableModelReportEnvironment env = new TableModelReportEnvironment();
	javax.swing.table.DefaultTableModel model = obj.getResultModel();
	if(model != null){
		env.setTableModel(model);
		env.setHttpFileName("UNTGR031R");
		env.setJasperFile(this.getServletContext().getRealPath("/report/unt/gr/untgr031r.jasper"));
		env.setFormat(obj.getQ_reportType());
		
	
	    HashMap parms = new HashMap();
		//parms.put("enterorgname", obj.getEnterOrgName());
		//parms.put("todaydate", util.Datetime.getYYYMMDD());
	    
	    TableModelReportGenerator generator = new TableModelReportGenerator(env);
	    generator.reportToHttpResponse(request, response, parms);
		out = pageContext.pushBody();
	}
	
%>
