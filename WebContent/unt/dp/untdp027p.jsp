<!--
程式目的：轉帳憑證黏存單(權利) 
程式代號：untdp027p
程式日期：111/10/12
程式作者：chris.lin
-->
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../home/head.jsp" %>
<%@ page import="java.util.*"%>
<%@ page import="org.apache.logging.log4j.*"%>
<%@ page import="util.report.*"%>

<jsp:useBean id="obj" scope="request" class="unt.dp.UNTDP027R">
    <jsp:setProperty name="obj" property="*"/>
</jsp:useBean>
<%
	try {
		out.clear();
	
	    TableModelReportEnvironment env = new TableModelReportEnvironment();
		javax.swing.table.DefaultTableModel model = obj.getResultModel();
	
		if (model == null) {
			out.write("查無資料");
			return;
		}
		
		env.setTableModel(model);
		env.setHttpFileName("UNTDP027R");
		
		if("1".equals(obj.getQ_printSign())){
			env.setJasperFile(this.getServletContext().getRealPath("/report/unt/dp/untdp027r_1.jasper"));
		}else{
			env.setJasperFile(this.getServletContext().getRealPath("/report/unt/dp/untdp027r.jasper"));
		}
		
		env.setFormat(ReportEnvironment.VAL_FORMAT_XLS);
		String DateTrn=util.Datetime.getYYYMMDD();
	    DateTrn=DateTrn.substring(0,3)+"."+DateTrn.substring(3,5)+"."+DateTrn.substring(5,7);	
	
	    HashMap parms = new HashMap();
	    parms.put("todaydate", DateTrn);
	    TableModelReportGenerator generator = new TableModelReportGenerator(env);
	    generator.reportToHttpResponse(request, response, parms);
		out = pageContext.pushBody();
	} catch (Exception e) {
		LogManager.getLogger(this.getClass()).error("產製轉帳憑證黏存單(權利)時發生錯誤", e);
		out.write("產製轉帳憑證黏存單(權利)時發生錯誤");
	}
	
%>
