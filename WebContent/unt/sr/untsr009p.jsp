<!--
*<br>程式目的：國產署交換媒體檔 - 目錄總表
*<br>程式代號：untsr009p
*<br>撰寫日期：103.09.30
*<br>程式作者：Kang Da
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

<jsp:useBean id="obj" scope="request" class="unt.sr.UNTSR009R">
    <jsp:setProperty name="obj" property="*"/>
</jsp:useBean>
<%
try{
	java.io.File outputFile = obj.exportTxt2();
	if(outputFile == null){
		out.write("查無資料!");
	}else{
		java.io.OutputStream os = null;
		java.io.FileInputStream is = null;
		try{
	        response.setContentType("application/x-download");
	        response.setHeader("Cache-control","");
		    response.setContentLength((int)outputFile.length());	        
		    response.setHeader("Content-Disposition", "attachment; filename=\"" + outputFile.getName() + "\"");
		    
	    	byte b[] = new byte[(int)outputFile.length()];
	    	if(outputFile.isFile()){
				is = new java.io.FileInputStream(outputFile);
				os = response.getOutputStream();
				
				int read = 0;
				while((read = is.read(b)) != -1){
	    	    	os.write(b,0,read);
	    	    }
	    	}
	    }catch(Exception x){
	    	
	    }finally{
	    	if(os != null){
	    		os.flush();
	    		os.close();
	    		response.flushBuffer();
	    		out.clear();
	    		out = pageContext.pushBody();
	    	}
	        if(is != null){
	        	is.close();
	        }
	        if(outputFile != null){
	        	outputFile.delete();
	        }
	    }
	}
}catch(Exception e){
	e.printStackTrace();
	out.write("發生異常!");
}
%>
