<!--
*<br>程式目的：國有財產增減結存表
*<br>程式代號：untsr001p
*<br>撰寫日期：
*<br>程式作者：YuanRen
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

<jsp:useBean id="obj" scope="request" class="unt.sr.UNTSR001R">
    <jsp:setProperty name="obj" property="*"/>
</jsp:useBean>
<%
	String processType = util.Common.get(request.getParameter("processType"));
	String q_enterDateS = util.Common.get(request.getParameter("q_enterDateS"));
	String q_enterDateE = util.Common.get(request.getParameter("q_enterDateE"));

	out.clear();
	obj.setFileName(this.getServletContext().getRealPath("\\sqlfile\\unt\\sr"));
	  
	TableModelReportEnvironment env = new TableModelReportEnvironment();
	javax.swing.table.DefaultTableModel model = obj.getResultModel(processType,q_enterDateS,q_enterDateE);

	env.setTableModel(model);
	env.setHttpFileName("UNTSR001R");
	env.setJasperFile(this.getServletContext().getRealPath("/report/unt/sr/untsr001r.jasper"));
	env.setFormat(ReportEnvironment.VAL_FORMAT_XLS);
	//env.setFormat(ReportEnvironment.VAL_FORMAT_HTML);
	

    HashMap parms = new HashMap();
	//parms.put("enterorgname", obj.getEnterOrgName());
	//parms.put("todaydate", util.Datetime.getYYYMMDD());
    
    TableModelReportGenerator generator = new TableModelReportGenerator(env);
//	java.io.File outputFile = new java.io.File(System.getProperty("java.io.tmpdir")+java.io.File.separator+util.Common.getVMID()+".xls");
//	generator.reportToFile(outputFile,parms);
// 	response.sendRedirect(request.getContextPath()+"/downloadFileSimple?fileName=" + outputFile);

	String filename = null;
	
	if("UNVALUABLE".equals(processType)){
		filename=java.net.URLEncoder.encode("國有財產增減結存表(非珍貴財產)(untsr001r)", "UTF-8") + ".xls";
	}else if("VALUABLE".equals(processType)){
		filename=java.net.URLEncoder.encode("國有財產增減結存表(珍貴財產)(untsr001r)", "UTF-8") + ".xls";	
	}else{
		filename=java.net.URLEncoder.encode("國有財產增減結存表(untsr001r)", "UTF-8") + ".xls";
	}
	
	

	
	
	
	//以下不必變更
	//***************************************************************************************************
	String fileDir=System.getProperty("java.io.tmpdir")+java.io.File.separator + util.Common.getVMID();
	util.Common.CreateDir(fileDir);	
	java.io.File outputFile = new java.io.File(fileDir + java.io.File.separator + filename);

   	generator.reportToFile(outputFile,parms);   	
   	
	if(outputFile.exists()){//檢驗檔案是否存在

        try{
            response.setHeader("Content-Disposition","attachment; filename=\""  + outputFile.getName() + "\""); 
            java.io.OutputStream output = response.getOutputStream();
            java.io.InputStream in = new java.io.FileInputStream(outputFile);
            byte[] b = new byte[2048];
            int len;
            
            while((len = in.read(b))>0){
            	output.write(b,0,len);
            }
            in.close();
            
            output.flush();
            output.close();   //關閉串流
            
            out.clear();
            out = pageContext.pushBody();
            
        }catch(Exception ex){
            out.println("Exception : "+ex.toString());
            out.println("<br/>");
        }finally{
        	outputFile.delete();
            (new java.io.File(fileDir)).delete();

        }
        
    }else{
        out.println(outputFile.getName()+" : 此檔案不存在");
        out.println("<br/>");
    }
%>
