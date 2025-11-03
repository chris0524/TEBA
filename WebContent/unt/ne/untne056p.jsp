<!--
*<br>程式目的：物品報廢單 
*<br>程式代號：untne056r
*<br>撰寫日期：104/09/24
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

<jsp:useBean id="obj" scope="request" class="unt.ne.UNTNE056R">
    <jsp:setProperty name="obj" property="*"/>
</jsp:useBean>
<%
	out.clear();

    TableModelReportEnvironment env = new TableModelReportEnvironment();
	javax.swing.table.DefaultTableModel model = obj.getResultModel();

	env.setTableModel(model);
	env.setHttpFileName("UNTNE056R");
	if (TCGHCommon.getSYSCODEName("04", "ETO").equals(obj.getOrganID())) {
		env.setJasperFile(this.getServletContext().getRealPath("/report/unt/ne/untne056r_1.jasper"));
	} else {
		env.setJasperFile(this.getServletContext().getRealPath("/report/unt/ne/untne056r.jasper"));
	}
	env.setFormat(obj.getQ_reportType());
	

	HashMap<String, Object> parms = new HashMap<String, Object>(2);
	parms.put("ENTERORGNAME", user.getOrganName());
	if ("Y".equals(obj.getQ_isPrintKeeper())) {
		parms.put("KEEPER", "保管人：" + obj.getKeeper());
	}
	//parms.put("todaydate", util.Datetime.getYYYMMDD());
    
    TableModelReportGenerator generator = new TableModelReportGenerator(env);
    generator.reportToHttpResponse(request, response, parms);
	out = pageContext.pushBody();
	
%>
