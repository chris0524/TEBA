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


<%@page import="tw.gov.nsc.soa.org.dataservice_interface.GetOrgInfo"%><jsp:useBean id="obj" scope="request" class="unt.rp.UNTRP006R">
    <jsp:setProperty name="obj" property="*"/>
</jsp:useBean>
<%
	out.clear();

    TableModelReportEnvironment env = new TableModelReportEnvironment();
	javax.swing.table.DefaultTableModel model = obj.getResultModel();

	if(model ==  null){
		out.write(obj.getErrorMsg());
		return;
	}
	
	env.setTableModel(model);
	env.setHttpFileName("UNTRP006R");
	boolean isPrintKeeper = "Y".equals(obj.getQ_isPrintKeeper());
	String reportPath = "/report/unt/rp/";
	
	String reportName = "untrp006r.jasper";
	HashMap<String, Object> parms = new HashMap<String, Object>(0);
	if (TCGHCommon.getSYSCODEName("04", "ETO").equals(obj.getOrganID())) {
		reportName =  "untrp006r_1.jasper";
	} else if (TCGHCommon.getSYSCODEName("01", "ETO").equals(obj.getQ_enterOrg()) && "120".equals(obj.getQ_differenceKind())) {
		reportName = "untrp006r_2.jasper";
	}
	if (isPrintKeeper) {
		parms.put("keeper", "保管人：" + obj.getKeeper());
	}
	
	env.setJasperFile(this.getServletContext().getRealPath(reportPath + reportName));
	env.setFormat(obj.getQ_reportType());

    
	//parms.put("enterorgname", obj.getEnterOrgName());
	//parms.put("todaydate", util.Datetime.getYYYMMDD());
    
    TableModelReportGenerator generator = new TableModelReportGenerator(env);
	generator.reportToHttpResponse(request, response, parms);
    out = pageContext.pushBody();
	
%>
