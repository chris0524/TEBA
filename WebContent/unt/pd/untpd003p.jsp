<!--
*<br>程式目的：動產盤點名細表
*<br>程式代號：untpd003p
*<br>撰寫日期：
*<br>程式作者：Mike Kao
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

<jsp:useBean id="obj" scope="request" class="unt.pd.UNTPD003R">
    <jsp:setProperty name="obj" property="*"/>
</jsp:useBean>
<%
	String signature = Common.get(obj.getQ_signature());
	String titleName = Common.get(obj.getQ_titleName());
	String note = Common.get(obj.getQ_note());
	String noteText = Common.get(obj.getQ_noteText());
	String lockId = obj.getLockId(user.getOrganID()); 
	String chengPageStyle = Common.get(obj.getQ_chengPageStyle());
	if (BeanLocker.isLocked(lockId)) {
		out.write(BeanLocker.getLockedMsg(lockId));
		return;
	}
	
	if ("".equals(note)) {
		note = "N";
	}
	
	out.clear();
	obj.setFileName(this.getServletContext().getRealPath("\\sqlfile\\unt\\pd"));
    TableModelReportEnvironment env = new TableModelReportEnvironment();
	javax.swing.table.DefaultTableModel model = obj.getResultModel();
	
	if (model == null) {
		out.write("資料庫沒有符合的資料");
		return;
	}
	
	env.setTableModel(model);
	env.setHttpFileName("UNTPD003R");
	
	
	if("2".equals(titleName)) {
		//都印或都不印(簽核欄+備註) 或只印簽核欄不印備註
		if (("Y".equals(signature) && "Y".equals(note)) || 
			("N".equals(signature) && "N".equals(note)) ||
			("Y".equals(signature) && "N".equals(note))) {
			env.setJasperFile(this.getServletContext().getRealPath("/report/unt/pd/untpd003r-2.jasper"));	
			//只印備註
		} else if  ( "N".equals(signature) && "Y".equals(note) ) {
			env.setJasperFile(this.getServletContext().getRealPath("/report/unt/pd/untpd003r-2_1.jasper"));
		}
		
	} else {
		//都印(簽核欄+備註) 或只印簽核欄不印備註
		if (("Y".equals(signature) && "Y".equals(note)) || 
			("Y".equals(signature) && "N".equals(note))) {
			env.setJasperFile(this.getServletContext().getRealPath("/report/unt/pd/untpd003r.jasper"));		
			
		} //都不印(簽核欄+備註)
		else if ("N".equals(signature) && "N".equals(note)) {
			env.setJasperFile(this.getServletContext().getRealPath("/report/unt/pd/untpd003r_2.jasper"));	
		} //只印備註
		else if  ( "N".equals(signature) && "Y".equals(note) ) {
			env.setJasperFile(this.getServletContext().getRealPath("/report/unt/pd/untpd003r_1.jasper"));
		}
	}
	
	env.setFormat(obj.getQ_reportType());


    HashMap parms = new HashMap();
	parms.put("enterorgname", "");
	//parms.put("todaydate", util.Datetime.getYYYMMDD());
	parms.put("titleName", titleName);
	parms.put("signature", signature);
	parms.put("noteText",  noteText);
	parms.put("note", note);
	parms.put("chengPageStyle", chengPageStyle);
    
    TableModelReportGenerator generator = new TableModelReportGenerator(env);
    generator.reportToHttpResponse(request, response, parms);
	out = pageContext.pushBody();
	
%>

