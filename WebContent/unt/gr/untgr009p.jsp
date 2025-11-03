<!--
*<br>程式目的：財產增減結存表
*<br>程式代號：untgr009p
*<br>撰寫日期：0950310
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

<jsp:useBean id="obj" scope="request" class="unt.gr.UNTGR009R">
    <jsp:setProperty name="obj" property="*"/>
</jsp:useBean>
<%
	out.clear();
	obj.setFileName(this.getServletContext().getRealPath("\\sqlfile\\unt\\gr"));
	TableModelReportEnvironment env = new TableModelReportEnvironment();
	if(!"".equals(obj.getQ_enterDateS())){
		javax.swing.table.DefaultTableModel model = obj.getResultModel();
	
		env.setTableModel(model);
		env.setHttpFileName("UNTGR009R");
		env.setJasperFile(this.getServletContext().getRealPath("/report/unt/gr/untgr009r.jasper"));
		env.setFormat(obj.getReportType());
		
	
		HashMap<String,Object> parms = new HashMap<String,Object>();
		if("Y".equals(obj.getQ_isorganmanager())){
			parms.put("ENTERORGNAME", TCGHCommon.getSysca_Organ().get(obj.getQ_enterOrg())+"及所屬");
		}else{
			if("1".equals(obj.getQ_enterorgKind1())){
				parms.put("ENTERORGNAME", TCGHCommon.getSysca_Organ().get(obj.getQ_enterOrg())+"及所屬");
			}else if("2".equals(obj.getQ_enterorgKind1())){
				parms.put("ENTERORGNAME", TCGHCommon.getSysca_Organ().get(obj.getQ_enterOrg()).replaceAll("科技部新竹", "") + "作業基金");
			}else{
				parms.put("ENTERORGNAME", ReportUtil.getTitleByEnterOrgCode(obj.getQ_enterOrg(), obj.getQ_differenceKind()));
			}
		}
		String datetime=util.Datetime.getYYYMMDD();
		datetime=datetime.substring(0,3) + "年" + datetime.substring(3,5) + "月" + datetime.substring(5)+ "日";
		parms.put("todaydate", datetime);
		parms.put("ENTERDATESE", "中華民國"+Common.MaskCDate(obj.getQ_enterDateS())+"起至"+Common.MaskCDate(obj.getQ_enterDateE()));
		parms.put("USERNAME", obj.getUserName());
		String ownershipName="";
		if(obj.getQ_ownership().equals("1")){
			ownershipName = "國有財產增減結存表(中央機關使用)";
		}else if(obj.getQ_ownership().equals("2")){
			ownershipName = "直轄市有財產增減結存表(中央機關使用)";
		}else if(obj.getQ_ownership().equals("3")){
			ownershipName = "縣有財產增減結存表(中央機關使用)";
		}else if(obj.getQ_ownership().equals("4")){
			ownershipName = "市有財產增減結存表(中央機關使用)";
		}else if(obj.getQ_ownership().equals("5")){
			ownershipName = "鄉(鎮、市)有財產增減結存表(中央機關使用)";
		} else {
			ownershipName = "財產增減結存表(中央機關使用)";
		}
		parms.put("OWNERSHIPNAME", ownershipName);
		parms.put("tel", TCGHCommon.getLookup("select emptel from SYSAP_EMP where empid = " + Common.sqlChar(user.getUserID())));
		TableModelReportGenerator generator = new TableModelReportGenerator(env);
		generator.reportToHttpResponse(request, response, parms);
		out = pageContext.pushBody();
	}
%>
