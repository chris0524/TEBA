<%@page import="java.io.FileInputStream"%>
<%@page import="java.io.File"%>
<%@page import="org.apache.logging.log4j.*"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<jsp:useBean id="obj"  scope="request" class="sys.rp.SYSRP001R">
	<jsp:setProperty name="obj" property="*" />
</jsp:useBean><html>
<%
	FileInputStream fis = null;
	try {
		out.clear();
		File excelFile = obj.exportExcel();
		if (excelFile == null) {
			out.write("資料庫裡沒有符合的資料");
			return;
		} else if (!excelFile.exists()) {
			out.write("檔案遺失");
			return;
		}
		response.setContentType("application/octet-stream; charset=iso-8859-1;");
		response.setHeader("content-disposition","attachment; filename="+obj.getFileName()+".xls");
		fis = new FileInputStream(excelFile);
		int byteRead;
		while(-1 != (byteRead = fis.read())) {
	        out.write(byteRead);
	    }
		fis.close();
		//out.clear();
        out = pageContext.pushBody();
	} catch (Exception e) {
		LogManager.getLogger(this.getClass()).error("產製折舊資料清查報表時發生錯誤", e);
		out.write("產製報表時發生錯誤,請聯繫系統人員協助排除");
		return;
	} finally {
		if (fis != null) {
			try {
				fis.close();
				fis = null;
			} catch (Exception e) {
				//ignore
			}
		}
	}
%>