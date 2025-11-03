<!--
*<br>程式目的：財產折舊月報總表(基金財產)
*<br>程式代號：untdp026r
*<br>撰寫日期：105/04/14
*<br>程式作者：Jim.Lin
*<br>--------------------------------------------------------
*<br>修改作者　　修改日期　　　修改目的
*<br>--------------------------------------------------------
-->

<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../home/head.jsp" %>
<%@ page import="java.util.*"%>
<%@ page import="util.report.*"%>

<jsp:useBean id="obj" scope="request" class="unt.dp.UNTDP026R">
    <jsp:setProperty name="obj" property="*"/>
</jsp:useBean>
<%
try{
	out.clear();

    TableModelReportEnvironment env = new TableModelReportEnvironment();
	javax.swing.table.DefaultTableModel model = obj.getResultModel();
	if(model != null){
		env.setTableModel(model);
		env.setHttpFileName("UNTDP026R");
		if ("1".equals(obj.getQ_page())) { //跳頁方式:部門別
			env.setJasperFile(this.getServletContext().getRealPath("/report/unt/dp/untdp026r.jasper"));
		} else if ("2".equals(obj.getQ_page())) { //跳頁方式:部門別單位
			env.setJasperFile(this.getServletContext().getRealPath("/report/unt/dp/untdp026r_2.jasper"));
		} else if ("3".equals(obj.getQ_page())) { //跳頁方式:部門別單位+會計科目
			env.setJasperFile(this.getServletContext().getRealPath("/report/unt/dp/untdp026r_3.jasper"));
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
	}
}catch(Exception e){
	e.printStackTrace();
}
	
%>
