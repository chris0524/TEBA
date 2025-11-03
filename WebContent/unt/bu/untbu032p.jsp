<!--
*<br>程式目的：建物持份面積與使用面積不符差異總表報表檔 
*<br>程式代號：untbu032r
*<br>撰寫日期：94/12/14
*<br>程式作者：Chris
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

<jsp:useBean id="obj" scope="request" class="unt.bu.UNTBU032R">
    <jsp:setProperty name="obj" property="*"/>
</jsp:useBean>
<%
	out.clear();

    TableModelReportEnvironment env = new TableModelReportEnvironment();
	javax.swing.table.DefaultTableModel model = obj.getResultModel();

	env.setTableModel(model);
    env.setHttpFileName("UNTBU032R");
	env.setJasperFile(this.getServletContext().getRealPath("/report/unt/bu/untbu032r.jasper"));
	env.setFormat(ReportEnvironment.VAL_FORMAT_XLS);
	//env.setFormat(ReportEnvironment.VAL_FORMAT_HTML);
	
    String DateTrn=util.Datetime.getYYYMMDD();
    DateTrn=DateTrn.substring(0,3)+"/"+DateTrn.substring(3,5)+"/"+DateTrn.substring(5,7);
    HashMap parms = new HashMap();
	//parms.put("enterorgname", obj.getEnterOrgName());
	parms.put("todaydate", DateTrn);
    
    TableModelReportGenerator generator = new TableModelReportGenerator(env);
    generator.reportToHttpResponse(request, response, parms);
	out = pageContext.pushBody();
	//System.out.println(obj.getEnterOrg());
	//System.out.println(obj.getEnterOrgName());
	//System.out.println("ok");
%>
