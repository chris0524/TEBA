<!--
*<br>程式目的： 國有財產局交換媒體檔－土地財產卡
*<br>程式代號：untgr006p
*<br>撰寫日期：
*<br>程式作者：YuanRen
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
<%@ page import="java.net.URLEncoder"%>

<jsp:useBean id="obj" scope="request" class="unt.sr.UNTSR007R">
    <jsp:setProperty name="obj" property="*"/>
</jsp:useBean>
<%
	out.clear();

	if (!"".equals(obj.getCheckSignno())) {
		obj.setFileName(this.getServletContext().getRealPath("\\sqlfile\\unt\\sr"));
	    TableModelReportEnvironment env = new TableModelReportEnvironment();
		javax.swing.table.DefaultTableModel model = obj.getResultModel();
		
		if (model == null) {
			out.write("查無資料!");
		} else {
			env.setTableModel(model);
			env.setHttpFileName("UNTSR007R");

			env.setJasperFile(this.getServletContext().getRealPath("/report/unt/sr/untsr007r.jasper"));
			
			env.setFormat(ReportEnvironment.VAL_FORMAT_PDF);

			HashMap<String,Object> parms = new HashMap<String,Object>();
			TableModelReportGenerator generator = new TableModelReportGenerator(env);
			generator.reportToHttpResponse(request, response, parms);
		}
	} else {
		obj.createOutputTxt();
		ESAPI.httpUtilities().setCurrentHTTP(request, response);
		ESAPI.httpUtilities().getCurrentResponse().sendRedirect(ESAPI.httpUtilities().getCurrentRequest().getContextPath()+"/downloadFileSimple?fileName=" + URLEncoder.encode(obj.getTxtPath(), "UTF-8"));
	}
	
	
	//String txtpath = "C:\\Program Files\\Apache Software Foundation\\Tomcat 6.0\\webapps\\TBEK\\report\\unt\\sr\\PL383999960A00010303051636.txt";
	//response.sendRedirect(request.getContextPath()+"/downloadFileSimple?fileName=" + txtpath);
	
	out = pageContext.pushBody();
	
%>
