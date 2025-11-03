<!--
*<br>程式目的：市有財產目錄(普通公務機關用)  
*<br>程式代號：untgr010p
*<br>撰寫日期：95/05/15
*<br>程式作者：Cherry
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

<jsp:useBean id="obj" scope="request" class="unt.gr.UNTGR010R">
    <jsp:setProperty name="obj" property="*"/>
</jsp:useBean>
<%
	String q_propertyKind1 = request.getParameter("q_propertyKind1");
	out.clear();
	obj.setFileName(this.getServletContext().getRealPath("\\sqlfile\\unt\\gr"));
    TableModelReportEnvironment env = new TableModelReportEnvironment();
	javax.swing.table.DefaultTableModel model = obj.getResultModel();

	env.setTableModel(model);
	env.setHttpFileName("UNTGR010R");
	if(q_propertyKind1.equals("1")){
		env.setJasperFile(this.getServletContext().getRealPath("/report/unt/gr/untgr010r.jasper"));
	}else if(q_propertyKind1.equals("2")){
		env.setJasperFile(this.getServletContext().getRealPath("/report/unt/gr/untgr010r1.jasper"));
	}
	env.setFormat(ReportEnvironment.VAL_FORMAT_PDF);
	//env.setFormat(ReportEnvironment.VAL_FORMAT_HTML);
	

    HashMap parms = new HashMap();
	//parms.put("enterorgname", obj.getEnterOrgName());
	//parms.put("todaydate", util.Datetime.getYYYMMDD());
    
    TableModelReportGenerator generator = new TableModelReportGenerator(env);
    generator.reportToHttpResponse(request, response, parms);
	out = pageContext.pushBody();
	
%>
