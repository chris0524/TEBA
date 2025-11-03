<!--
*<br>程式目的：非消耗品毀損報廢單報表檔 
*<br>程式代號：untne016r
*<br>撰寫日期：104/09/15
*<br>程式作者：Mike Kao
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

<jsp:useBean id="obj" scope="request" class="unt.ne.UNTNE016R">
    <jsp:setProperty name="obj" property="*"/>
</jsp:useBean>
<%
	out.clear();
 
    TableModelReportEnvironment env = new TableModelReportEnvironment();
	javax.swing.table.DefaultTableModel model = obj.getResultModel();

	env.setTableModel(model);
	env.setHttpFileName("UNTNE016R");
	if (TCGHCommon.getSYSCODEName("04", "ETO").equals(obj.getOrganID())) {
		env.setJasperFile(this.getServletContext().getRealPath("/report/unt/ne/untne016r_01.jasper"));
	} else {
		env.setJasperFile(this.getServletContext().getRealPath("/report/unt/ne/untne016r.jasper"));
	}
	env.setFormat(obj.getQ_reportType());
	

    HashMap parms = new HashMap();
//	parms.put("enterorgname", obj.getEnterOrgName());
//	parms.put("todaydate", util.Datetime.getYYYMMDD());
    
    TableModelReportGenerator generator = new TableModelReportGenerator(env);
    generator.reportToHttpResponse(request, response, parms);
	out = pageContext.pushBody();
	
%>
