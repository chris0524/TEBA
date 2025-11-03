<!--
*<br>程式目的：資訊設備現況調查歷年資料查詢
*<br>程式代號：sysst016p
*<br>撰寫日期：96/07/09
*<br>程式作者：sam.hsueh
*<br>--------------------------------------------------------
*<br>修改作者　　修改日期　　　修改目的
*<br>--------------------------------------------------------
-->

<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../home/head.jsp" %>
<%@ page import="java.util.*"%>
<%@ page import="util.report.*"%>

<jsp:useBean id="obj" scope="request" class="sys.st.SYSST016R">
    <jsp:setProperty name="obj" property="*"/>
</jsp:useBean>
<%
	//System.out.println("year:"+obj.getQ_year());
	//System.out.println(obj.getQ_enterOrg());
	obj.setFileName(this.getServletContext().getRealPath("\\sqlfile\\sys\\st"));
	obj.setFilestoreLocation(this.getServletContext().getRealPath("/sys/st/templates/sysst016r.xls"));
	obj.genExcelFile();
	response.sendRedirect("../../downloadFileSimple?fileName=" + obj.getExcelFileName());
		
%>
