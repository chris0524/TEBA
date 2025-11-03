<!--
*<br>程式目的：珍貴動產、不動產增減表 
*<br>程式代號：untgr020p
*<br>撰寫日期：0950314
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

<jsp:useBean id="obj" scope="request" class="unt.gr.UNTGR020R">
    <jsp:setProperty name="obj" property="*"/>
</jsp:useBean>
<%

	out.clear();
	obj.setFileName(this.getServletContext().getRealPath("\\sqlfile\\unt\\gr"));
    TableModelReportEnvironment env = new TableModelReportEnvironment();
	javax.swing.table.DefaultTableModel model = obj.getResultModel();

	env.setTableModel(model);
	env.setHttpFileName("UNTGR020R");
	env.setJasperFile(this.getServletContext().getRealPath("/report/unt/gr/untgr020r.jasper"));
	env.setFormat(obj.getReportType());

	Map<String,String> ownerShipMap = TCGHCommon.getSysca_Code("OWA"); 					//權屬名稱
	String datetime=util.Datetime.getYYYMMDD();
	datetime=datetime.substring(0,3) + "年" + datetime.substring(3,5) + "月" + datetime.substring(5)+ "日";

	HashMap<String,Object> parms = new HashMap<String,Object>();
	if("Y".equals(obj.getQ_isorganmanager())){
		parms.put("ENTERORGNAME", TCGHCommon.getSysca_Organ().get(obj.getQ_enterOrg())+"及所屬");
	}else{
		parms.put("ENTERORGNAME", TCGHCommon.getSysca_Organ().get(obj.getQ_enterOrg()));
	}
	parms.put("todaydate", datetime);
	
	parms.put("ITEMNAME", "珍貴動產、不動產");

	parms.put("OWNERSHIP", ownerShipMap.get(obj.getQ_ownership()));
	
	parms.put("ENTERDATESE", "中華民國"+Common.MaskCDate(obj.getQ_enterDateS())+"起至"+Common.MaskCDate(obj.getQ_enterDateE()));
	parms.put("USERNAME", obj.getUserName());
	
	parms.put("DIFFERENCEKIND", obj.getP_differenceKind());
    
    TableModelReportGenerator generator = new TableModelReportGenerator(env);
    generator.reportToHttpResponse(request, response, parms);
	out = pageContext.pushBody();
	
%>
