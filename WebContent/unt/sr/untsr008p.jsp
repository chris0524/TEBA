<!--
*<br>程式目的：國產署交換媒體檔－增減結存表 
*<br>程式代號：untsr008p
*<br>撰寫日期：0950310
*<br>程式作者：Cherry
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

<jsp:useBean id="obj" scope="request" class="unt.sr.UNTSR008R">
    <jsp:setProperty name="obj" property="*"/>
</jsp:useBean>
<%
	out.clear();
	obj.createOutputTxt();
	response.sendRedirect(request.getContextPath()+"/downloadFileSimple?fileName=" + java.net.URLEncoder.encode(obj.getTxtPath(), "UTF-8"));
	
	//String txtpath = "C:\\Program Files\\Apache Software Foundation\\Tomcat 6.0\\webapps\\TBEK\\report\\unt\\sr\\PL383999960A00010303051636.txt";
	//response.sendRedirect(request.getContextPath()+"/downloadFileSimple?fileName=" + txtpath);
	
	out = pageContext.pushBody();
	
%>
