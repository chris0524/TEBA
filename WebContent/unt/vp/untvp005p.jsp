<!--
*<br>程式目的：有價證券財產卡查詢檔 
*<br>程式代號：untvp005p
*<br>撰寫日期：94/11/09
*<br>程式作者：Cherry
*<br>--------------------------------------------------------
*<br>修改作者　　修改日期　　　修改目的
*<br>--------------------------------------------------------
-->

<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../home/head.jsp" %>
<%@ page import="java.util.*"%>
<%@ page import="util.report.*"%>

<jsp:useBean id="obj" scope="request" class="unt.vp.UNTVP005R">
    <jsp:setProperty name="obj" property="*"/>
</jsp:useBean>
<%
	out.clear();

    TableModelReportEnvironment env = new TableModelReportEnvironment();
	javax.swing.table.DefaultTableModel model = obj.getResultModel();

	env.setTableModel(model);
    env.setHttpFileName("UNTVP005R");
	env.setJasperFile(this.getServletContext().getRealPath("/report/unt/vp/untvp005r.jasper"));
	env.setFormat(obj.getQ_reportType());
	//env.setFormat(ReportEnvironment.VAL_FORMAT_HTML);
	

    HashMap parms = new HashMap();
	//parms.put("enterorgname", obj.getEnterOrgName());
	//parms.put("todaydate", util.Datetime.getYYYMMDD());
	parms.put("subReportPathAdjust", this.getServletContext().getRealPath("/report/unt/vp/untvp005r_subreportAdjust.jasper"));
	parms.put("subReportPathCapital", this.getServletContext().getRealPath("/report/unt/vp/untvp005r_subreportCapital.jasper"));
	//TDCM問題單1797，與keri 討論後增加顯示減損紀錄
	parms.put("subReportPathReduce", this.getServletContext().getRealPath("/report/unt/vp/untvp005r_subreportReduce.jasper"));
    
    TableModelReportGenerator generator = new TableModelReportGenerator(env);
    generator.reportToHttpResponse(request, response, parms);
	out = pageContext.pushBody();
	
%>
