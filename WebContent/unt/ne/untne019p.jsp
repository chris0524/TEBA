<!--
*<br>程式目的：非消耗品減損單查詢檔 
*<br>程式代號：untne019r
*<br>撰寫日期：103/09/16
*<br>程式作者：Mike Kao
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

<jsp:useBean id="obj" scope="request" class="unt.ne.UNTNE019R">
    <jsp:setProperty name="obj" property="*"/>
</jsp:useBean>
<%
	out.clear();
 
    TableModelReportEnvironment env = new TableModelReportEnvironment();
	javax.swing.table.DefaultTableModel model = obj.getResultModel();

	env.setTableModel(model);
	env.setHttpFileName("UNTNE019R");
	env.setJasperFile(this.getServletContext().getRealPath("/report/unt/ne/untne019r.jasper"));
	env.setFormat(obj.getQ_reportType());
	

    HashMap<String, Object> parms = new HashMap<String, Object>(2);
    if(TCGHCommon.getSYSCODEName("03", "ETO").equals(user.getOrganID())){
    	parms.put("mainUnit","政　　風　　單　　位");
    } else{
    	parms.put("mainUnit","主　　計　　單　　位");
    }
    
    if ("Y".equals(obj.getQ_isPrintKeeper())) {
    	parms.put("KEEPER", "保管人：" + obj.getKeeper());
    }
	//parms.put("enterorgname", obj.getEnterOrgName());
	//parms.put("todaydate", util.Datetime.getYYYMMDD());
    
    TableModelReportGenerator generator = new TableModelReportGenerator(env);
    generator.reportToHttpResponse(request, response, parms);
	out = pageContext.pushBody();
	
%>
