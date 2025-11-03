<!--
*<br>程式目的：繳納清冊 
*<br>程式代號：untte003p
*<br>撰寫日期：94/11/24
*<br>程式作者：unique.chiang
*<br>--------------------------------------------------------
*<br>修改作者　　修改日期　　　修改目的
*<br>--------------------------------------------------------
-->

<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../home/head.jsp" %>
<%@ page import="java.util.*"%>
<%@ page import="util.report.*"%>

<jsp:useBean id="obj" scope="request" class="unt.te.UNTTE003R">
    <jsp:setProperty name="obj" property="*"/>
</jsp:useBean>
<%
	out.clear();

    TableModelReportEnvironment env = new TableModelReportEnvironment();
	javax.swing.table.DefaultTableModel model = obj.getResultModel();

	env.setTableModel(model);
	env.setHttpFileName("UNTTE003R");
	env.setJasperFile(this.getServletContext().getRealPath("/report/unt/te/untte003r.jasper"));
	env.setFormat(ReportEnvironment.VAL_FORMAT_XLS);
	//env.setFormat(ReportEnvironment.VAL_FORMAT_HTML);
	

    HashMap parms = new HashMap();
	//parms.put("enterorgname", obj.getEnterOrgName());
	//parms.put("todaydate", util.Datetime.getYYYMMDD());
	//parms.put("subReportPath", this.getServletContext().getRealPath("/report/unt/vp/untvp004r_subreport.jasper"));
    
    TableModelReportGenerator generator = new TableModelReportGenerator(env);
    generator.reportToHttpResponse(request, response, parms);
	out = pageContext.pushBody();
	
%>
