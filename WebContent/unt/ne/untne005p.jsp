<!--
*<br>程式目的：非消耗品增加單查詢檔 
*<br>程式代號：untne005p
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
<%@ page import="net.sf.jasperreports.engine.*"%>

<jsp:useBean id="obj" scope="request" class="unt.ne.UNTNE005R">
    <jsp:setProperty name="obj" property="*"/>
</jsp:useBean>
<%
	out.clear();

    TableModelReportEnvironment env = new TableModelReportEnvironment();
	javax.swing.table.DefaultTableModel model = obj.getResultModel();

	env.setTableModel(model);
	env.setHttpFileName("UNTNE005R");
	if (TCGHCommon.getSYSCODEName("01", "ETO").equals(obj.getEnterOrg()) || TCGHCommon.getSYSCODEName("10", "ETO").equals(obj.getEnterOrg())) {  //科技部、科技辦公室
		env.setJasperFile(this.getServletContext().getRealPath("/report/unt/ne/untne005r.jasper"));
	} else {
		env.setJasperFile(this.getServletContext().getRealPath("/report/unt/ne/untne005r_2.jasper"));  //其餘機關不畫線
	}
	env.setFormat(obj.getQ_reportType());

    HashMap parms = new HashMap();
	//parms.put("enterorgname", obj.getEnterOrgName());
	//parms.put("todaydate", util.Datetime.getYYYMMDD());
	parms.put("image", this.getServletContext().getRealPath("/images/untne005r_report.bmp"));
	parms.put("subReportPath", this.getServletContext().getRealPath("/report/unt/ne/untne005r_subreport.jasper"));
//	parms.put("subReportPath1", this.getServletContext().getRealPath("/report/unt/ne/untne005r_subreportDetail.jasper"));
	
    TableModelReportGenerator generator = new TableModelReportGenerator(env);
    generator.reportToHttpResponse(request, response, parms);
	out = pageContext.pushBody();
	
%>
