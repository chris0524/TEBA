<!--
*<br>程式目的：單位機關與地政處地籍比對差異清冊
*<br>程式代號：sysot003r
*<br>撰寫日期：0950727
*<br>程式作者：sam.hsueh
*<br>--------------------------------------------------------
*<br>修改作者　　修改日期　　　修改目的
*<br>--------------------------------------------------------
-->

<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.sql.*"%>
<%@ page import="java.util.*"%>
<%@ page import="util.report.*"%>
<%@ page import="util.*"%>
<%@ page import="net.sf.jasperreports.engine.*"%>
<%@ page import="org.owasp.esapi.ESAPI" %>
<jsp:useBean id="obj" scope="request" class="sys.lc.SYSLC004R">
    <jsp:setProperty name="obj" property="*"/>
</jsp:useBean>
<%
out.clear();
TableModelReportEnvironment env = new TableModelReportEnvironment();
javax.swing.table.DefaultTableModel model = obj.getResultModel();
	
env.setTableModel(model);
env.setHttpFileName("SYSLC004R");
env.setJasperFile(application.getRealPath("/report/sys/lc/syslc004r.jasper"));

env.setFormat(obj.getQ_reportType());

HashMap parms = new HashMap();
String DateTrn=util.Datetime.getYYYMMDD();
DateTrn=util.Common.MaskCDate(DateTrn);
//parms.put("today", DateTrn);
    
TableModelReportGenerator generator = new TableModelReportGenerator(env);
java.io.File outputFile = new java.io.File(System.getProperty("java.io.tmpdir")+java.io.File.separator+env.getHttpFileName()+".xls");

generator.reportToFile(outputFile,parms);
ESAPI.httpUtilities().setCurrentHTTP(request, response);
ESAPI.httpUtilities().getCurrentResponse().sendRedirect(ESAPI.httpUtilities().getCurrentRequest().getContextPath()+"/downloadFileSimple?fileName=" + outputFile);
//response.sendRedirect(request.getContextPath()+"/downloadFileSimple?fileName=" + outputFile);

%>

