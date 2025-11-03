<!--
*<br>程式目的：國有財產目錄(普通公務機關用)  
*<br>程式代號：untgr011p
*<br>撰寫日期：95/05/15
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

<jsp:useBean id="obj" scope="request" class="unt.gr.UNTGR011R">
    <jsp:setProperty name="obj" property="*"/>
</jsp:useBean>
<jsp:useBean id="obj2" scope="request" class="unt.gr.UNTGR013R">
    <jsp:setProperty name="obj2" property="*"/>
</jsp:useBean>
<%
try{
	TableModelReportEnvironment env = new TableModelReportEnvironment();
	javax.swing.table.DefaultTableModel model = null;
	//問題單1741   若大於等於105年則不使用untgr011r的程式，改以untgr013r程式及報表
	if (obj.isQYearOver105()) { // >= 105 年度 使用另外一張報表
		env.setJasperFile(this.getServletContext().getRealPath("/report/unt/gr/untgr013r105.jasper"));
        obj2.setQ_reportYear(obj.getQ_reportYear());
        obj2.setQ_ownership(obj.getQ_ownership());
        obj2.setQ_enterOrg(obj.getQ_enterOrg());
        obj2.setQ_differenceKind(obj.getQ_differenceKind());
        obj2.setQ_valuable(obj.getQ_valuable());
        obj2.setQ_dataState(obj.getQ_dataState());
        obj2.setQ_verify(obj.getQ_verify());
        obj2.setIsAdminManager(obj.getIsAdminManager());
        obj2.setQ_isorganmanager(obj.getQ_isorganmanager());
	    model = obj2.getResultModel();//改呼叫untgr013r.grtResultMode
	    if(null == model || model.getRowCount() == 0){
	        out.write("查無資料!");
	        return;
	    }
	    env.setTableModel(model);
	    env.setHttpFileName("UNTGR011R");
	} else {
		env.setJasperFile(this.getServletContext().getRealPath("/report/unt/gr/untgr011r.jasper"));
		model = obj.getResultModel();
        if(null == model || model.getRowCount() == 0){
            out.write("查無資料!");
            return;
        }
        env.setTableModel(model);
        env.setHttpFileName("UNTGR011R");
	}
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
			parms.put("organAName", ReportUtil.getTitleByEnterOrgCode(obj.getQ_enterOrg(), obj.getQ_differenceKind()));
		}
	}
	parms.put("reportName", TCGHCommon.getSYSCODEName(obj.getQ_ownership(), "OWA") + "財產目錄(普通公務機關使用)");
	parms.put("diff", obj.getQ_differenceKind() + " " + TCGHCommon.getSYSCODEName(obj.getQ_differenceKind(), "DFK"));
	parms.put("username", Common.get(user.getUserName()));
	parms.put("yyymmdd", "中華民國    " + obj.getQ_reportYear() + " 年 12 月 31 日 ");
	parms.put("date", Common.formatYYYMMDD(Datetime.getYYYMMDD(), 2));
	parms.put("enterOrg", ReportUtil.getEnterOrgByDFK(obj.getQ_enterOrg(), obj.getQ_differenceKind()));
	
	TableModelReportGenerator generator = new TableModelReportGenerator(env);
	out.clear();
	generator.reportToHttpResponse(request, response, parms);
	out = pageContext.pushBody();
}catch(Exception e){
	e.printStackTrace();
	out.write("發生異常!");
}
%>
