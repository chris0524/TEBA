<!--
*<br>程式目的：財產折舊月報明細表(代管資產)(中科管局格式)
*<br>程式代號：untdp019r
*<br>撰寫日期：103/09/02
*<br>程式作者：Anthony.Wang
*<br>--------------------------------------------------------
*<br>修改作者　　修改日期　　　修改目的
*<br>--------------------------------------------------------
-->

<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../home/head.jsp" %>
<%@ page import="java.util.*"%>
<%@ page import="util.report.*"%>

<jsp:useBean id="obj" scope="request" class="unt.dp.UNTDP019R">
    <jsp:setProperty name="obj" property="*"/>
</jsp:useBean>
<%
	out.clear();

    TableModelReportEnvironment env = new TableModelReportEnvironment();
	javax.swing.table.DefaultTableModel model = obj.getResultModel();

	env.setTableModel(model);
	env.setHttpFileName("UNTDP019R");
	if ("1".equals(obj.getQ_page())) { //跳頁方式:部門別
		if("113".equals(obj.getQ_differenceKind())){
			env.setJasperFile(this.getServletContext().getRealPath("/report/unt/dp/untdp019r_3.jasper"));	
		}else{
			env.setJasperFile(this.getServletContext().getRealPath("/report/unt/dp/untdp019r.jasper"));			
		}
	} else if ("2".equals(obj.getQ_page())) { //跳頁方式:部門別單位
		if("113".equals(obj.getQ_differenceKind())){
			env.setJasperFile(this.getServletContext().getRealPath("/report/unt/dp/untdp019r_4.jasper"));	
		}else{
			env.setJasperFile(this.getServletContext().getRealPath("/report/unt/dp/untdp019r_2.jasper"));	
		}
	}
	
	env.setFormat(obj.getQ_reportType());
	String datetime=util.Datetime.getYYYMMDD();
	datetime=datetime.substring(0,3) + "年" + datetime.substring(3,5) + "月" + datetime.substring(5)+ "日";

    HashMap parms = new HashMap();
    parms.put("todaydate", datetime);
    parms.put("USERNAME", obj.getUserName());
    TableModelReportGenerator generator = new TableModelReportGenerator(env);
    generator.reportToHttpResponse(request, response, parms);
	out = pageContext.pushBody();
	
%>
