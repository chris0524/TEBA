<!--
*<br>程式目的：土地增減單月報表 
*<br>程式代號：untgr004r
*<br>撰寫日期：0950303
*<br>程式作者：Cherry
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
<%@ page import="unt.ch.UNTCH_Tools"%>
<jsp:useBean id="obj" scope="request" class="unt.gr.UNTGR004R">
    <jsp:setProperty name="obj" property="*"/>
</jsp:useBean>
<%
	out.clear();
    UNTCH_Tools ut = new UNTCH_Tools();
    TableModelReportEnvironment env = new TableModelReportEnvironment();
	javax.swing.table.DefaultTableModel model = obj.getResultModel();
	env.setTableModel(model);
	env.setHttpFileName("UNTGR004R");
	if (obj.isReportYMOver10501()) {
		env.setJasperFile(this.getServletContext().getRealPath("/report/unt/gr/untgr004r105.jasper"));
	} else {
		env.setJasperFile(this.getServletContext().getRealPath("/report/unt/gr/untgr004r.jasper"));
	}
	env.setFormat(obj.getQ_reportType());
	Map<String,String> ownerShipMap = TCGHCommon.getSysca_Code("OWA"); 					//權屬名稱
	String datetime=util.Datetime.getYYYMMDD();
	datetime=datetime.substring(0,3) + "年" + datetime.substring(3,5) + "月" + datetime.substring(5)+ "日";

	HashMap<String,Object> parms = new HashMap<String,Object>();
	parms.put("ENTERORGNAME", ut._queryData("organaname")._from("SYSCA_ORGAN a ")._with(" and organid = '" + obj.getQ_enterOrg() + "'")._toString());
	parms.put("todaydate", datetime);
	if ("1".equals(obj.getQ_propertyKind1())) {
		parms.put("ITEMNAME", "財產");
	} else {
		parms.put("ITEMNAME", "物品");
	}
	parms.put("OWNERSHIP", ownerShipMap.get(obj.getQ_ownership()));
	
	parms.put("ENTERDATESE", "中華民國" + obj.getQ_reportYM().substring(0,3) + "年" + obj.getQ_reportYM().substring(3,5) + "月");
	parms.put("USERNAME", obj.getUserName());
	

	TableModelReportGenerator generator = new TableModelReportGenerator(env);
	generator.reportToHttpResponse(request, response, parms);
	out = pageContext.pushBody();
	
%>
