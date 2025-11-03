<!--
*<br>程式目的：園區公共設施建設費總額報表檔 
*<br>程式代號：
*<br>撰寫日期：
*<br>程式作者：
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

<jsp:useBean id="obj" scope="request" class="unt.eg.UNTEG006R">
    <jsp:setProperty name="obj" property="*"/>
</jsp:useBean>
<%
try{
	out.clear();

    TableModelReportEnvironment env = new TableModelReportEnvironment();
	javax.swing.table.DefaultTableModel model = obj.getResultModel();
	if (model == null) {
		out.write("資料庫裡沒有符合的資料");
		return;
	}
	
	env.setTableModel(model);
	env.setHttpFileName("UNTEG006R");
	env.setJasperFile(this.getServletContext().getRealPath("/report/unt/eg/unteg006r.jasper"));
	env.setFormat(ReportEnvironment.VAL_FORMAT_XLS);
	//env.setFormat(ReportEnvironment.VAL_FORMAT_HTML);

    HashMap parms = new HashMap();
    parms = obj.getParams();
	//parms.put("enterorgname", obj.getEnterOrgName());
	//parms.put("todaydate", util.Datetime.getYYYMMDD());
    
    TableModelReportGenerator generator = new TableModelReportGenerator(env);
	generator.reportToHttpResponse(request, response, parms);
    out = pageContext.pushBody();
    
} catch (Exception e){
	out.write("產製報表時發生錯誤,請聯繫系統人員協助排除");
	return;
}	
	
%>
