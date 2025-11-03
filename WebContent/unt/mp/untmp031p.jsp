<!--
*<br>程式目的：動產保管人清冊報表檔 
*<br>程式代號：untmp031r
*<br>撰寫日期：94/11/25
*<br>程式作者：chris
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

<jsp:useBean id="obj" scope="request" class="unt.mp.UNTMP031R">
    <jsp:setProperty name="obj" property="*"/>
</jsp:useBean>
<%
	out.clear();

    TableModelReportEnvironment env = new TableModelReportEnvironment();
	javax.swing.table.DefaultTableModel model = obj.getResultModel();

	env.setTableModel(model);
	env.setHttpFileName("UNTMP031R");
	env.setJasperFile(this.getServletContext().getRealPath("/report/unt/mp/untmp031r.jasper"));
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
