<!--
*<br>程式目的：財產檢查單   
*<br>程式代號：untgr015p
*<br>撰寫日期：95/05/16
*<br>程式作者：Cherry
*<br>--------------------------------------------------------
*<br>修改作者　　修改日期　　　修改目的
*<br>--------------------------------------------------------
-->

<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../home/head.jsp" %>
<%@ page import="java.sql.*"%>
<%@ page import="java.util.*"%>
<%@ page import="unt.ch.UNTCH_Tools"%>
<%@ page import="util.report.*"%>
<%@ page import="net.sf.jasperreports.engine.*"%>

<jsp:useBean id="obj" scope="request" class="unt.gr.UNTGR015R">
    <jsp:setProperty name="obj" property="*"/>
</jsp:useBean>
<%

	out.clear();
	obj.setFileName(this.getServletContext().getRealPath("\\sqlfile\\unt\\gr"));
	TableModelReportEnvironment env = new TableModelReportEnvironment();
	javax.swing.table.DefaultTableModel model = obj.getResultModel();

	env.setTableModel(model);
	env.setHttpFileName("UNTGR015R");
	
	if(obj.getQ_pageWay().equals("1")){
		env.setJasperFile(this.getServletContext().getRealPath("/report/unt/gr/untgr015r-1.jasper"));
	}else if(obj.getQ_pageWay().equals("2")){
		env.setJasperFile(this.getServletContext().getRealPath("/report/unt/gr/untgr015r-2.jasper"));
	}else if(obj.getQ_pageWay().equals("3")){
		env.setJasperFile(this.getServletContext().getRealPath("/report/unt/gr/untgr015r-3.jasper"));
	}else if(obj.getQ_pageWay().equals("4")){
		env.setJasperFile(this.getServletContext().getRealPath("/report/unt/gr/untgr015r-4.jasper"));
	}
	env.setFormat(obj.getQ_reportType());


	HashMap<String,Object> parms = new HashMap<String,Object>();
	String datetime=util.Datetime.getYYYMMDD();
	datetime=datetime.substring(0,3) + "年" + datetime.substring(3,5) + "月" + datetime.substring(5)+ "日";
	parms.put("todaydate", datetime);
	parms.put("ENTERORGNAME", ReportUtil.getTitleByEnterOrgCode(obj.getQ_enterOrg(), obj.getQ_differenceKind()));
	parms.put("USERNAME", obj.getUserName());
	if ("1".equals(obj.getQ_propertyKind1())) {
		parms.put("ITEMNAME", "財產");
	} else {
		parms.put("ITEMNAME", "物品");
	}
	
	TableModelReportGenerator generator = new TableModelReportGenerator(env);
	generator.reportToHttpResponse(request, response, parms);
	out = pageContext.pushBody();
	
%>
