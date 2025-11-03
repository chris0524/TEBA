<!--
*<br>程式目的：增減值單報表檔 
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

<jsp:useBean id="obj" scope="request" class="unt.rp.UNTRP004R">
    <jsp:setProperty name="obj" property="*"/>
</jsp:useBean>
<%
	out.clear();

    TableModelReportEnvironment env = new TableModelReportEnvironment();
	javax.swing.table.DefaultTableModel model = obj.getResultModel();
	
	env.setTableModel(model);
	env.setHttpFileName("UNTRP004R");
	String reportPath = "/report/unt/rp/untrp004r.jasper";
	if (TCGHCommon.getSYSCODEName("01", "ETO").equals(obj.getQ_enterOrg()) && "120".equals(obj.getQ_differenceKind())) {
		reportPath = "/report/unt/rp/untrp004r_1.jasper";
	}
	env.setJasperFile(this.getServletContext().getRealPath(reportPath));
	env.setFormat(obj.getQ_reportType());

    HashMap parms = new HashMap();
	//parms.put("enterorgname", obj.getEnterOrgName());
	//parms.put("todaydate", util.Datetime.getYYYMMDD());
    
    TableModelReportGenerator generator = new TableModelReportGenerator(env);
	generator.reportToHttpResponse(request, response, parms);
    out = pageContext.pushBody();
	
%>
