<!--
*<br>程式目的：地籍地段代碼異動清冊
*<br>程式代號：untgr033p
*<br>撰寫日期：112/05/26
*<br>程式作者：chris.lin
*<br>--------------------------------------------------------
*<br>修改作者　　修改日期　　　修改目的
*<br>--------------------------------------------------------
-->

<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../home/head.jsp" %>
<%@ page import="java.util.*"%>
<%@ page import="util.report.*"%>

<jsp:useBean id="obj" scope="request" class="unt.gr.UNTGR033R">
    <jsp:setProperty name="obj" property="*"/>
</jsp:useBean>
<%
try{
    out.clear();

    TableModelReportEnvironment env = new TableModelReportEnvironment();
    javax.swing.table.DefaultTableModel model = obj.getResultModel();
    if(null == model || model.getRowCount() == 0){
		out.write("查無資料!");
		return;
	} else {
        env.setTableModel(model);
        env.setHttpFileName("UNTGR033R");
        env.setJasperFile(this.getServletContext().getRealPath("/report/unt/gr/untgr033r.jasper"));
        
        env.setFormat(obj.getQ_reportType());
        String datetime = util.Datetime.getYYYMMDD();
        datetime = datetime.substring(0, 3) + "年" + datetime.substring(3, 5) + "月" + datetime.substring(5)+ "日";

        HashMap parms = new HashMap();
        parms.put("P0", obj.getUserName());
        parms.put("P1", datetime);
        TableModelReportGenerator generator = new TableModelReportGenerator(env);
        generator.reportToHttpResponse(request, response, parms);
        out = pageContext.pushBody();
    }
} catch(Exception e) {
    e.printStackTrace();
}
    
%>
