<!--
*<br>程式目的：動產財產卡查詢檔 
*<br>程式代號：untmp007p
*<br>撰寫日期：94/11/28
*<br>程式作者：Cherry
*<br>--------------------------------------------------------
*<br>修改作者　　修改日期　　　修改目的
*<br>--------------------------------------------------------
-->

<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../home/head.jsp" %>
<%@ page import="java.util.*"%>
<%@ page import="util.report.*"%>

<jsp:useBean id="obj" scope="request" class="unt.mp.UNTMP007R">
    <jsp:setProperty name="obj" property="*"/>
</jsp:useBean>
<%
	out.clear();

    TableModelReportEnvironment env = new TableModelReportEnvironment();
	javax.swing.table.DefaultTableModel model = obj.getResultModel();

	env.setTableModel(model);
	env.setHttpFileName("UNTMP007R");
	env.setJasperFile(this.getServletContext().getRealPath("/report/unt/mp/untmp007r.jasper"));
	env.setFormat(obj.getQ_reportType());
	//env.setFormat(ReportEnvironment.VAL_FORMAT_HTML);
	

    HashMap parms = new HashMap();
	//parms.put("enterorgname", obj.getEnterOrgName());
	//parms.put("todaydate", util.Datetime.getYYYMMDD());
	parms.put("subReportPathProof", this.getServletContext().getRealPath("/report/unt/mp/untmp007r_subreportProof.jasper"));
	parms.put("subReportPathMoveDetail", this.getServletContext().getRealPath("/report/unt/mp/untmp007r_subreportMoveDetail.jasper"));
    parms.put("subReportPathReduce", this.getServletContext().getRealPath("/report/unt/mp/untmp007r_subreportReduce.jasper"));
    parms.put("subReportPathDepr", this.getServletContext().getRealPath("/report/unt/mp/untmp007r_subreportDepr.jasper"));
        
    TableModelReportGenerator generator = new TableModelReportGenerator(env);
    generator.reportToHttpResponse(request, response, parms);
	out = pageContext.pushBody();
	
%>
