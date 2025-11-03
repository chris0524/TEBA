<!--
*<br>程式目的：地價稅課稅明細資料表
*<br>程式代號：untte005r
*<br>撰寫日期：96/9/10
*<br>程式作者：Chris
*<br>--------------------------------------------------------
*<br>修改作者　　修改日期　　　修改目的
*<br>--------------------------------------------------------
-->  
 
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.sql.*"%>
<%@ page import="java.util.*"%>
<%@ page import="util.report.*"%>
<%@ page import="net.sf.jasperreports.engine.*"%>

<jsp:useBean id="obj" scope="request" class="unt.te.UNTTE005R">
    <jsp:setProperty name="obj" property="*"/>
</jsp:useBean>
<%
	out.clear();

    TableModelReportEnvironment env = new TableModelReportEnvironment();
	javax.swing.table.DefaultTableModel model = obj.getResultModel();

	env.setTableModel(model);
	env.setHttpFileName("UNTTE005R");
	env.setJasperFile(this.getServletContext().getRealPath("/report/unt/te/untte005r.jasper"));
	env.setFormat(ReportEnvironment.VAL_FORMAT_XLS);
	//env.setFormat(ReportEnvironment.VAL_FORMAT_HTML);
	
    String DateTrn=util.Datetime.getYYYMMDD();
    DateTrn=util.Common.MaskCDate(DateTrn);
    HashMap parms = new HashMap();
    parms.put("todaydate",DateTrn);
   
    
	TableModelReportGenerator generator = new TableModelReportGenerator(env);
    generator.reportToHttpResponse(request, response, parms);
	out = pageContext.pushBody();
	
%>
