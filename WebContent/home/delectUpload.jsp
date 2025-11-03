<%@ page contentType="text/html;charset=UTF-8" import="java.sql.*,util.*"%>
<%@ page import="java.io.File"%>
<%
String popFileID = Common.checkGet(request.getParameter("fileID"));

if(!"".equals(popFileID)){
	Common.RemoveDirectory(new File(application.getInitParameter("filestoreLocation")+"/"+popFileID.replaceAll(":;:","/")).getParentFile());
}

Database db = new Database();
try {		
//System.out.println("good:"+popFileID);
} catch (Exception e) {
	e.printStackTrace();
} finally {
	db.closeAll();
}	


%>