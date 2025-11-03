<!--
*<br>程式目的：財產結存統計表  
*<br>程式代號：pubgr016p
*<br>撰寫日期：95/03/16
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

<jsp:useBean id="obj" scope="request" class="pub.gr.PUBGR016R">
    <jsp:setProperty name="obj" property="*"/>
</jsp:useBean>
<%
try{
	TableModelReportEnvironment env = new TableModelReportEnvironment();
	javax.swing.table.DefaultTableModel model = obj.getResultModel();
	
	//if(null == model || model.getRowCount() == 0){
	//	out.write("查無資料!");
	//	return;
	//}
	
	env.setTableModel(model);
	env.setHttpFileName("PUBGR016R");
	env.setJasperFile(this.getServletContext().getRealPath("/report/pub/gr/pubgr016r.jasper"));
	env.setFormat(obj.getReportType());
	
	java.util.HashMap<String, Object> parms = new java.util.HashMap<String, Object>();
	if("Y".equals(obj.getQ_isorganmanager())){
		if (TCGHCommon.getSYSCODEName("02", "ETO").equals(obj.getQ_enterOrg()) && "120".equals(obj.getQ_differenceKind())) {
			String organName = TCGHCommon.getLookup("select organaname from SYSCA_ORGAN where organid = " + Common.sqlChar(obj.getQ_enterOrg()));
			parms.put("organAName", organName.replaceAll("科技部新竹", "") + "作業基金");
		} else {
			String organName = TCGHCommon.getLookup("select organaname from SYSCA_ORGAN where organid = " + Common.sqlChar(obj.getQ_enterOrg()));
			parms.put("organAName", organName+"及所屬");
		}
	}else{
		parms.put("organAName", ReportUtil.getTitleByEnterOrgCode(obj.getQ_enterOrg(), obj.getQ_differenceKind()));
	}
	parms.put("reportName", "主管機關" + TCGHCommon.getSYSCODEName(obj.getQ_ownership(), "OWA") + "財產結存統計表");
	if("1".equals(obj.getQ_reportType())){//月報
		parms.put("yyymmdd", "中華民國    " + obj.getQ_enterDateE().substring(0, 3) + " 年 " + 
				   obj.getQ_enterDateE().substring(3, 5) + " 月 " + 
				   obj.getQ_enterDateE().substring(5, 7) + " 日 ");
	}else{//季報
		if("1".equals(obj.getQ_reportSeason())){//第一季
			parms.put("yyymmdd", "中華民國    " + obj.getQ_enterDateE().substring(0, 3) + "年1月1日 起至 "+ obj.getQ_enterDateE().substring(0, 3) +"年3月31日 ");
		}
		if("2".equals(obj.getQ_reportSeason())){//第二季
			parms.put("yyymmdd", "中華民國    " + obj.getQ_enterDateE().substring(0, 3) + "年4月1日 起至 "+ obj.getQ_enterDateE().substring(0, 3) +"年6月30日 ");
		}
		if("3".equals(obj.getQ_reportSeason())){//第三季
			parms.put("yyymmdd", "中華民國    " + obj.getQ_enterDateE().substring(0, 3) + "年7月1日 起至 "+ obj.getQ_enterDateE().substring(0, 3) +"年9月30日 ");
		}
		if("4".equals(obj.getQ_reportSeason())){//第四季
			parms.put("yyymmdd", "中華民國    " + obj.getQ_enterDateE().substring(0, 3) + "年10月1日 起至 "+ obj.getQ_enterDateE().substring(0, 3) +"年12月31日 ");
		}
	}

	parms.put("diff", obj.getQ_differenceKind() + " " + TCGHCommon.getSYSCODEName(obj.getQ_differenceKind(), "DFK"));
	parms.put("managerOrg", "國家科學及技術委員會");
	parms.put("username", Common.get(user.getUserName()));
	parms.put("date", Common.formatYYYMMDD(Datetime.getYYYMMDD(), 2));
	parms.put("tel", TCGHCommon.getLookup("select emptel from SYSAP_EMP where empid = " + Common.sqlChar(user.getUserID())));
	
	TableModelReportGenerator generator = new TableModelReportGenerator(env);
	out.clear();
	generator.reportToHttpResponse(request, response, parms);
	out = pageContext.pushBody();
}catch(Exception e){
	e.printStackTrace();
	out.write("發生異常!");
}
%>
