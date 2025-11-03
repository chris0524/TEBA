<!--
*<br>程式目的：減損月報總表
*<br>程式代號：untgr023r
*<br>撰寫日期：105/11/22
*<br>程式作者：Jim.Lin
*<br>--------------------------------------------------------
*<br>修改作者　　修改日期　　　修改目的
*<br>--------------------------------------------------------
-->

<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../home/head.jsp" %>
<%@ page import="java.util.*"%>
<%@ page import="util.report.*"%>

<jsp:useBean id="obj" scope="request" class="unt.gr.UNTGR023R">
    <jsp:setProperty name="obj" property="*"/>
</jsp:useBean>
<%
try{
    out.clear();

    TableModelReportEnvironment env = new TableModelReportEnvironment();
    javax.swing.table.DefaultTableModel model = obj.getResultModel();
    if(model != null){
        env.setTableModel(model);
        env.setHttpFileName("UNTGR023R");
        env.setJasperFile(this.getServletContext().getRealPath("/report/unt/gr/untgr023r.jasper"));
        
        env.setFormat(obj.getQ_reportType());
        String datetime=util.Datetime.getYYYMMDD();
        datetime=datetime.substring(0,3) + "年" + datetime.substring(3,5) + "月" + datetime.substring(5)+ "日";

        HashMap parms = new HashMap();
        parms.put("P0", obj.getQ_enterorgName());
        parms.put("P1", obj.getUserName());
        parms.put("P2", datetime);
        parms.put("P3", obj.getQ_year()+" 年 "+obj.getQ_month()+" 月");
        TableModelReportGenerator generator = new TableModelReportGenerator(env);
        generator.reportToHttpResponse(request, response, parms);
        out = pageContext.pushBody();
    }
}catch(Exception e){
    e.printStackTrace();
}
    
%>
