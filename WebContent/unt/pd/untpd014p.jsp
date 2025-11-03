<!--
*<br>程式目的：非消耗品盤點清冊
*<br>程式代號：untpd014p
*<br>撰寫日期：2014/09/30
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

<jsp:useBean id="obj" scope="request" class="unt.pd.UNTPD014R">
    <jsp:setProperty name="obj" property="*"/>
</jsp:useBean>
<%

	String signature = Common.get(obj.getQ_signature());
	String titleName = Common.get(obj.getQ_titleName());
	String note = Common.get(obj.getQ_note());
	String noteText = Common.get(obj.getQ_noteText());
	String lockId = obj.getLockId(user.getOrganID()); 
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
	env.setHttpFileName("UNTPD014R");
	
	if("2".equals(titleName)) {
		//都印或都不印(簽核欄+備註) 或只印簽核欄不印備註
		if (("Y".equals(signature) && "Y".equals(note)) || 
			("N".equals(signature) && "N".equals(note)) ||
			("Y".equals(signature) && "N".equals(note))) {
			env.setJasperFile(this.getServletContext().getRealPath("/report/unt/pd/untpd014r-2.jasper"));
		} else if ( "N".equals(signature) && "Y".equals(note) ) {  //只印備註
			env.setJasperFile(this.getServletContext().getRealPath("/report/unt/pd/untpd014r-2_1.jasper"));
		}
		
		if ("PDF".equals(obj.getQ_reportType())) {
			env.setFormat(ReportEnvironment.VAL_FORMAT_PDF);
		} else {
			env.setFormat(ReportEnvironment.VAL_FORMAT_XLS);
		}
	} else {
		//都印或都不印(簽核欄+備註) 或只印簽核欄不印備註
		if (("Y".equals(signature) && "Y".equals(note)) || 
			("N".equals(signature) && "N".equals(note)) ||
			("Y".equals(signature) && "N".equals(note))) {
			env.setJasperFile(this.getServletContext().getRealPath("/report/unt/pd/untpd014r.jasper"));	
			//只印備註
		} else if  ( "N".equals(signature) && "Y".equals(note) ) {
			env.setJasperFile(this.getServletContext().getRealPath("/report/unt/pd/untpd014r_1.jasper"));
		}
		
		env.setFormat(obj.getQ_reportType());
	}
	
//	env.setFormat(ReportEnvironment.VAL_FORMAT_PDF);
	//env.setFormat(ReportEnvironment.VAL_FORMAT_HTML);
	

    HashMap parms = new HashMap();
	parms.put("enterorgname", "");
	//parms.put("todaydate", util.Datetime.getYYYMMDD());
	parms.put("titleName", titleName);
	parms.put("signature", signature);
	parms.put("noteText",  noteText);
	parms.put("note", note);
    
    TableModelReportGenerator generator = new TableModelReportGenerator(env);
    generator.reportToHttpResponse(request, response, parms);
	out = pageContext.pushBody();
	
%>
