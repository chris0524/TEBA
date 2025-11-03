<!--
*<br>程式目的：財產撥出單
*<br>程式代號：
*<br>撰寫日期：
*<br>程式作者：
*<br>--------------------------------------------------------
*<br>修改作者　　修改日期　　　修改目的
*<br>--------------------------------------------------------
-->

<%@ page import="org.apache.logging.log4j.*"%>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../home/head.jsp" %>
<%@ page import="java.sql.*"%>
<%@ page import="java.util.*"%>
<%@ page import="util.report.*"%>
<%@ page import="net.sf.jasperreports.engine.*"%>

<jsp:useBean id="obj" scope="request" class="unt.rp.UNTRP007R">
    <jsp:setProperty name="obj" property="*"/>
</jsp:useBean>
<%
	try {
		out.clear();
	
	    TableModelReportEnvironment env = new TableModelReportEnvironment();
		javax.swing.table.DefaultTableModel model = obj.getResultModel2();
		
		if(model ==  null){
			out.write(obj.getErrorMsg());
			return;
		}
		
		env.setTableModel(model);
		env.setHttpFileName("UNTRP007R");
		env.setJasperFile(this.getServletContext().getRealPath("/report/unt/rp/untrp007r.jasper"));
		env.setFormat(obj.getQ_reportType());
	
	    HashMap parms = new HashMap();
		//parms.put("enterorgname", obj.getEnterOrgName());
		//parms.put("todaydate", util.Datetime.getYYYMMDD());
	    
	    TableModelReportGenerator generator = new TableModelReportGenerator(env);
		generator.reportToHttpResponse(request, response, parms);
	    out = pageContext.pushBody();
	} catch (Exception e) {
		LogManager.getLogger(this.getClass()).error("產製untrp007r發生錯誤", e);
		out.write("產製報表時發生錯誤,請聯繫系統人員協助排除");
		return;
	}
	
%>
