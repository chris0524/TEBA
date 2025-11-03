<!--
*<br>程式目的： 
*<br>程式代號：
*<br>撰寫日期：
*<br>程式作者：
*<br>--------------------------------------------------------
*<br>修改作者　　修改日期　　　修改目的
*<br>--------------------------------------------------------
-->

<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../home/head.jsp" %>
<%@ page import="java.util.*"%>
<%@ page import="util.report.*"%>

<jsp:useBean id="obj" scope="request" class="unt.dp.UNTDP016R">
    <jsp:setProperty name="obj" property="*"/>
</jsp:useBean>
<%
	out.clear();

    TableModelReportEnvironment env = new TableModelReportEnvironment();
	javax.swing.table.DefaultTableModel model = obj.getResultModel();

	env.setTableModel(model);
	env.setHttpFileName("UNTDP016R");
	if ("1".equals(obj.getQ_page())) { // 部門別
		env.setJasperFile(this.getServletContext().getRealPath("/report/unt/dp/untdp016r.jasper"));
	} else if  ("2".equals(obj.getQ_page())) { // 園區別+部門別+部門單位+會計科目
		env.setJasperFile(this.getServletContext().getRealPath("/report/unt/dp/untdp016r_1.jasper"));
	} else if  ("3".equals(obj.getQ_page())) { // 園區別+部門別
		env.setJasperFile(this.getServletContext().getRealPath("/report/unt/dp/untdp016r_2.jasper"));
	} else if  ("4".equals(obj.getQ_page())) { // 園區別
		env.setJasperFile(this.getServletContext().getRealPath("/report/unt/dp/untdp016r_4.jasper"));
	}
	if("Y".equals(request.getParameter("selectPrint"))){
		env.setJasperFile(this.getServletContext().getRealPath("/report/unt/dp/untdp016r_3.jasper"));
	}
	env.setFormat(obj.getQ_reportType());
	//env.setFormat(ReportEnvironment.VAL_FORMAT_HTML);
	 String DateTrn=util.Datetime.getYYYMMDD();
    DateTrn=DateTrn.substring(0,3)+"."+DateTrn.substring(3,5)+"."+DateTrn.substring(5,7);	

    HashMap parms = new HashMap();
	//parms.put("enterorgname", obj.getEnterOrgName());
	//parms.put("todaydate", util.Datetime.getYYYMMDD());
      parms.put("todaydate", DateTrn);
    TableModelReportGenerator generator = new TableModelReportGenerator(env);
    generator.reportToHttpResponse(request, response, parms);
	out = pageContext.pushBody();
	
%>
