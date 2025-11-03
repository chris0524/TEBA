<!--
*<br>程式目的： 
*<br>程式代號：
*<br>撰寫日期：
*<br>程式作者：
*<br>--------------------------------------------------------
*<br>修改作者　　修改日期　　　修改目的
*<br>--------------------------------------------------------
-->

<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../home/head.jsp" %>
<%@ page import="java.util.*"%>
<%@ page import="util.report.*"%>

<jsp:useBean id="obj" scope="request" class="unt.dp.UNTDP021R">
    <jsp:setProperty name="obj" property="*"/>
</jsp:useBean>
<%
	out.clear();

    TableModelReportEnvironment env = new TableModelReportEnvironment();
	javax.swing.table.DefaultTableModel model = obj.getResultModel();

	env.setTableModel(model);
	env.setHttpFileName("UNTPD021R");
	String reportName = "untdp021r.jasper";
	if ("B".equals(obj.getQ_groupType())) {
		reportName = "untdp021r_B.jasper";
	}
	env.setJasperFile(this.getServletContext().getRealPath("/report/unt/dp/" + reportName));
	env.setFormat(obj.getQ_reportType());
	//env.setFormat(ReportEnvironment.VAL_FORMAT_HTML);
	 String DateTrn=util.Datetime.getYYYMMDD();
    DateTrn=DateTrn.substring(0,3)+"."+DateTrn.substring(3,5)+"."+DateTrn.substring(5,7);	

    HashMap parms = new HashMap();
	//parms.put("enterorgname", obj.getEnterOrgName());
	//parms.put("todaydate", util.Datetime.getYYYMMDD());
      parms.put("todaydate", DateTrn);
    TableModelReportGenerator generator = new TableModelReportGenerator(env);
    generator.reportToHttpResponse(request, response, parms);
	out = pageContext.pushBody();
	
%>
