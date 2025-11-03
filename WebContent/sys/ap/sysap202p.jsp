<!--
*<br>程式目的：保固維護記錄表
*<br>程式代號：sysap202p
*<br>撰寫日期：0950918
*<br>程式作者：sam.hsueh
*<br>--------------------------------------------------------
*<br>修改作者　　修改日期　　　修改目的
*<br>--------------------------------------------------------
-->

<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.sql.*"%>
<%@ page import="java.util.*"%>
<%@ page import="util.report.*"%>
<%@ page import="net.sf.jasperreports.engine.*"%>

<jsp:useBean id="obj" scope="request" class="sys.ap.SYSAP202R">
    <jsp:setProperty name="obj" property="*"/>
</jsp:useBean>
<%
	out.clear();
	//obj.setFileName(this.getServletContext().getRealPath("\\sqlfile\\sys\\ap"));
    TableModelReportEnvironment env = new TableModelReportEnvironment();
	javax.swing.table.DefaultTableModel model = obj.getResultModel();

	env.setTableModel(model);
	env.setHttpFileName("SYSAP202R");
	env.setJasperFile(this.getServletContext().getRealPath("/report/sys/ap/sysap202r.jasper"));
	env.setFormat(ReportEnvironment.VAL_FORMAT_XLS);
	//env.setFormat(ReportEnvironment.VAL_FORMAT_HTML);
	

    HashMap parms = new HashMap();
	//parms.put("enterorgname", obj.getEnterOrgName());
	//parms.put("todaydate", util.Datetime.getYYYMMDD());
	//parms.put("subReportPath", this.getServletContext().getRealPath("/report/pub/gr/pubgr0031r_subreport.jasper"));
    
    TableModelReportGenerator generator = new TableModelReportGenerator(env);
    generator.reportToHttpResponse(request, response, parms);
	out = pageContext.pushBody();
	
%>

