<!--
*<br>程式目的：非消耗品登記卡查詢檔 
*<br>程式代號：untne006p
*<br>撰寫日期：103/09/19
*<br>程式作者：Mike Kao
*<br>--------------------------------------------------------
*<br>修改作者　　修改日期　　　修改目的
*<br>--------------------------------------------------------
-->

<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../home/head.jsp" %>
<%@ page import="java.util.*"%>
<%@ page import="util.report.*"%>

<jsp:useBean id="obj" scope="request" class="unt.ne.UNTNE006R">
    <jsp:setProperty name="obj" property="*"/>
</jsp:useBean>
<%
	out.clear();

    TableModelReportEnvironment env = new TableModelReportEnvironment();
	javax.swing.table.DefaultTableModel model = obj.getResultModel();

	env.setTableModel(model);
	env.setHttpFileName("UNTNE006R");
	env.setJasperFile(this.getServletContext().getRealPath("/report/unt/ne/untne006r.jasper"));
	env.setFormat(obj.getQ_reportType());
	//env.setFormat(ReportEnvironment.VAL_FORMAT_HTML);
	

    HashMap parms = new HashMap();
	//parms.put("enterorgname", obj.getEnterOrgName());
	//parms.put("todaydate", util.Datetime.getYYYMMDD());
	parms.put("subReportPathProof", this.getServletContext().getRealPath("/report/unt/ne/untne006r_subreportProof.jasper"));
//	parms.put("subReportPathMoveDetail", this.getServletContext().getRealPath("/report/unt/ne/untne006r_subreportMoveDetail.jasper"));
    parms.put("subReportPathReduce", this.getServletContext().getRealPath("/report/unt/ne/untne006r_subreportReduce.jasper"));
        
    TableModelReportGenerator generator = new TableModelReportGenerator(env);
    generator.reportToHttpResponse(request, response, parms);
	out = pageContext.pushBody();
	
%>
