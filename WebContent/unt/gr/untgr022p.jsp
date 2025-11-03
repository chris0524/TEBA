<!--
*<br>程式目的：珍貴動產、不動產目錄總表 
*<br>程式代號：untgr022p
*<br>撰寫日期：103.09.26
*<br>程式作者：Kang Da
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

<jsp:useBean id="obj" scope="request" class="unt.gr.UNTGR022R">
    <jsp:setProperty name="obj" property="*"/>
</jsp:useBean>
<%
try{
	TableModelReportEnvironment env = new TableModelReportEnvironment();
	javax.swing.table.DefaultTableModel model = obj.getResultModel();
	if(null == model || model.getRowCount() == 0){
		out.write("查無資料!");
		return;
	}
	
	env.setTableModel(model);
	env.setHttpFileName("UNTGR022R");
	env.setJasperFile(this.getServletContext().getRealPath("/report/unt/gr/untgr022r.jasper"));
	env.setFormat(obj.getQ_reportType());
	
	java.util.HashMap<String, Object> parms = new java.util.HashMap<String, Object>();
	String organName = TCGHCommon.getLookup("select organaname from SYSCA_ORGAN where organid = " + Common.sqlChar(obj.getQ_enterOrg()));
	if("Y".equals(obj.getQ_isorganmanager())){
		parms.put("organAName", organName+"及所屬");
	}else{
		if("1".equals(obj.getQ_enterorgKind1())){
			parms.put("organAName", organName+"及所屬");
		}else if("2".equals(obj.getQ_enterorgKind1())){
			parms.put("organAName", organName.replaceAll("科技部新竹", "") + "作業基金");
		}else{
			parms.put("organAName", organName);
		}
	}
// 	110.07.14 問題單2151 報表移除權屬
// 	parms.put("reportName", TCGHCommon.getSYSCODEName(obj.getQ_ownership(), "OWA") + "珍貴動產、不動產目錄總表");
	parms.put("reportName", "珍貴動產、不動產目錄總表");
	parms.put("diff", obj.getQ_differenceKind() + " " + TCGHCommon.getSYSCODEName(obj.getQ_differenceKind(), "DFK"));
	parms.put("username", Common.get(user.getUserName()));
	parms.put("yyy", obj.getQ_reportYear() + " 年 12 月 31 日");
	parms.put("date", Common.formatYYYMMDD(Datetime.getYYYMMDD(), 2));
	
	TableModelReportGenerator generator = new TableModelReportGenerator(env);
	out.clear();
	generator.reportToHttpResponse(request, response, parms);
	out = pageContext.pushBody();
}catch(Exception e){
	e.printStackTrace();
	out.write("發生異常!");
}	
	
%>
