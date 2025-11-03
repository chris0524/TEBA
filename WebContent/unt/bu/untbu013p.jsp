<!--
*<br>程式目的：建物財產卡報表檔 
*<br>程式代號：untbu013r
*<br>撰寫日期：94/12/12
*<br>程式作者：Chris
*<br>--------------------------------------------------------
*<br>修改作者　　修改日期　　　修改目的
*<br>--------------------------------------------------------
-->

<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../home/head.jsp" %>
<%@ page import="java.util.*"%>
<%@ page import="util.report.*"%>

<jsp:useBean id="obj" scope="request" class="unt.bu.UNTBU013R">
    <jsp:setProperty name="obj" property="*"/>
</jsp:useBean>
<%
	out.clear();

    TableModelReportEnvironment env = new TableModelReportEnvironment();
	javax.swing.table.DefaultTableModel model = obj.getResultModel();

	env.setTableModel(model);
	env.setHttpFileName("UNTBU013R");
	env.setJasperFile(this.getServletContext().getRealPath("/report/unt/bu/untbu013r.jasper"));
	env.setFormat(obj.getQ_reportType());
	//env.setFormat(ReportEnvironment.VAL_FORMAT_HTML);
	

    HashMap parms = new HashMap();
	//parms.put("enterorgname", obj.getEnterOrgName());
	//parms.put("todaydate", util.Datetime.getYYYMMDD());
	parms.put("subReportPathManage", this.getServletContext().getRealPath("/report/unt/bu/untbu013r_subreportManage.jasper"));
	
	parms.put("subReportPathBase", this.getServletContext().getRealPath("/report/unt/bu/untbu013r_subreportBase.jasper"));
    parms.put("subReportPathProof", this.getServletContext().getRealPath("/report/unt/bu/untbu013r_subreportProof.jasper"));
    parms.put("subReportPathDepr", this.getServletContext().getRealPath("/report/unt/bu/untbu013r_subreportDepr.jasper"));
    TableModelReportGenerator generator = new TableModelReportGenerator(env);
    generator.reportToHttpResponse(request, response, parms);
	out = pageContext.pushBody();
	
%>
