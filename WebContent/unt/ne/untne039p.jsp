<!--
*<br>程式目的：非消耗品報廢殘值處理清冊報表檔 
*<br>程式代號：untne039r
*<br>撰寫日期：94/11/28 
*<br>程式作者：chris
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

<jsp:useBean id="obj" scope="request" class="unt.ne.UNTNE039R">
    <jsp:setProperty name="obj" property="*"/>
</jsp:useBean>
<%
	out.clear();

    TableModelReportEnvironment env = new TableModelReportEnvironment();
	javax.swing.table.DefaultTableModel model = obj.getResultModel();

	env.setTableModel(model);
	env.setHttpFileName("UNTNE039R");
	env.setJasperFile(this.getServletContext().getRealPath("/report/unt/ne/untne039r.jasper"));
	env.setFormat(ReportEnvironment.VAL_FORMAT_XLS);
	//env.setFormat(ReportEnvironment.VAL_FORMAT_HTML);
	
    String DateTrn=util.Datetime.getYYYMMDD();
    DateTrn="中華民國"+DateTrn.substring(0,3)+"年"+DateTrn.substring(3,5)+"月"+DateTrn.substring(5,7)+"日";
    HashMap parms = new HashMap();
	//parms.put("enterorgname", obj.getEnterOrgName());
	parms.put("todaydate", DateTrn);
    parms.put("amount3", obj.getAmount3()); 
    parms.put("amount4", obj.getAmount4());
    parms.put("amount5", obj.getAmount5());
    parms.put("value3", obj.getValue3());
    parms.put("value4", obj.getValue4());
    parms.put("value5", obj.getValue5());    
    TableModelReportGenerator generator = new TableModelReportGenerator(env);
    generator.reportToHttpResponse(request, response, parms);
	out = pageContext.pushBody();
	//System.out.println(obj.getEnterOrg());
	//System.out.println(obj.getEnterOrgName());
	//System.out.println("ok");
%>
