<!--
*<br>程式目的：土地建物標示代碼匯入(段名代碼表txt)
*<br>程式代號：syslc006f
*<br>撰寫日期：
*<br>程式作者：
*<br>--------------------------------------------------------
*<br>修改作者　　修改日期　　　修改目的
*<br>--------------------------------------------------------
-->
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="../../home/head.jsp" %>
<%@ page import="java.io.*"%>
<%@ page import="java.util.*"%>
<%@ page import="com.oreilly.servlet.*" %>
<%@ page import="com.oreilly.servlet.multipart.*" %>
<jsp:useBean id="obj" scope="request" class="sys.lc.SYSLC006F">
	<jsp:setProperty name="obj" property="*"/>
</jsp:useBean>
<%
int maxPostSize = 1024 * 1024 * 100;  //100M

boolean isMultipart = false;	//判斷是否檔案上傳成功變數

String contentType = request.getContentType();

MultipartRequest mr = null;

String uploadCaseID = "";
String actionType = "";
String fileName = "";
String datafile = "";

String actionResult = "";	//轉檔狀態記錄變數
String actionMessage = "";	//畫面轉檔型態顯示訊息

if(contentType != null) {
	contentType = contentType.toLowerCase();
	if (contentType.startsWith("multipart/form-data")) {
		isMultipart = true;
	}
}

if (isMultipart) {
	uploadCaseID = new java.rmi.dgc.VMID().toString();
	uploadCaseID = uploadCaseID.replaceAll(":", "_");
	File tempDirectory = new File(System.getProperty("java.io.tmpdir"));
	tempDirectory = new File(tempDirectory,uploadCaseID);
	tempDirectory.mkdirs();

	FileRenamePolicy policy = new DefaultFileRenamePolicy();
	String encoding = "UTF-8";

	try {
		mr = new MultipartRequest(request,tempDirectory.getAbsolutePath(),maxPostSize,encoding,policy);
		actionType = Common.checkGet(mr.getParameter("actionType"));
	} catch(IOException e) {
		actionResult = "doFileSizeFail";	
	}
}

if ("into".equals(actionType)) {
	Enumeration enu = mr.getFileNames();
	File uploadFile = null;
	try {
		if (enu.hasMoreElements()) {
			String name = (String)enu.nextElement();
			uploadFile = mr.getFile(name);
		
			fileName = uploadFile.getName();
			
			//取得上傳檔案路徑
			datafile = System.getProperty("java.io.tmpdir") + File.separator + uploadCaseID + File.separator + fileName;
			
			obj.setFileName(this.getServletContext().getRealPath("\\sqlfile\\sys\\lc"));
		
			actionResult = obj.batchInsert(datafile);
			
			if ("doImportLog".equals(actionResult)) {
				actionMessage = "轉檔完成!!但內含錯誤資料!!";
				
			} else if ("doImportError".equals(actionResult)) {
				actionMessage = "轉檔資料格式錯誤!!";
				
			} else {
				actionMessage = "轉檔完成!!";
			}
			
		} else {
			actionResult = "doImportFail";
			actionMessage = "找不到匯入的檔案";
		}
	} catch(Exception e) {
		actionResult = "doImportFail";
		actionMessage = e.toString();
	} finally {
		File f = new File(datafile);
		if (f.exists()) {
			f.delete();
		}
	}
}

%>

<%@page import="util.Datetime"%>
<html>
<head>
<meta http-equiv="Content-Language" content="zh-tw"/>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
<meta http-equiv="Expires" content="-1"/>
<meta http-equiv="pragma" content="no-cache"/>
<meta http-equiv="Cache-control" content="no-cache"/>
<link rel="stylesheet" href="../../js/default.css" type="text/css">
<script language="javascript" src="../../js/validate.js"></script>
<script language="javascript" src="../../js/function.js"></script>
<script language="javascript" src="../../js/tablesoft.js"></script>
<script language="javascript">

var actionResult = '<%=actionResult%>';
var actionMessage = '<%=actionMessage%>';
var moshe;
var time=1;

function checkAfterAction() {
	switch(actionResult) {
	case 'noAction':
		break;
	case 'doImportSuccess':
	case 'doImportLog':
		var prop = "";
		var windowTop = (document.body.clientHeight+150)/2+10;
		var windowLeft = (document.body.clientWidth-150)/2+100;
		prop = prop+'toolbar=no;location=no,directories=no,menubar=no,status=yes,scrollbars=yes,resizable=yes,';
		prop = prop+"width=400,height=110,";
		prop = prop+"top=" + windowTop + ",";
		prop = prop+"left=" + windowLeft;
		moshe = window.open("", "exp", prop); 
		moshe.close();
		moshe = window.open("", "exp", prop);
		moshe.document.write('<html>');
		moshe.document.write('<head>');
		moshe.document.write('<meta http-equiv=Content-Type content=text/html; charset=MS950>');
		moshe.document.write('<title>.:: 資料傳輸結果 ::.</title>');
		moshe.document.write('</head>');
		moshe.document.write('<body topmargin="0" leftmargin="0" marginwidth="0" marginheight="0">\n');	
		moshe.document.write('<br><br><div align="center"><font color="#CC0000">' + actionMessage + '</font></div>');
		moshe.document.write('</body></html>');
		break;
	case 'doFileSizeFail':
		moshe = window.open("", "exp", "");
		moshe.close();
		alert("檔案太大！請將檔案重新分割！");
		break;
	case 'doImportError':
		alert("轉檔資料格式錯誤！！");
		break;
	case 'doImportFail':
		alert(actionMessage);
		break;
	}
}

function checkField() {
	var alertStr = "";
	var allowExt = "txt";
	if (form1.filePlace.value == "") {
		alert("您必需指定檔案存放位置");
		return false;
	} else {
		var extPos = form1.filePlace.value.lastIndexOf(".");
		var ext = form1.filePlace.value.toLowerCase().substring(extPos + 1);
		if (extPos == -1) {
			alert("無法判斷您上傳的檔案格式，請檢查檔案是否有副檔名並重新輸入!");
			return false;
		}
		if (parse(allowExt).length > 0) {
			if (allowExt.search(ext) == -1 ) {
				alert("上傳的檔案格式必須是TXT檔(txt)，請重新輸入!");
				return false;
			}
		}
	}
	if (alertStr.length != 0) { 
		alert(alertStr); 
		return false; 
	} else {
		viewShow('messageContainer');
		form1.actionType.value = "into";
		beforeSubmit();	
	}
}

function viewShow(viewName){
	var obj = document.all.item(viewName);
	var objHeight = obj.style.height;
	var objWidth = obj.style.width;
	
	objHeight = objHeight.substring(0, objHeight.length - 2);
	objWidth = objWidth.substring(0, objWidth.length - 2);

	obj.style.top = (document.body.clientHeight - Number(objHeight)) / 2 - 40;
	obj.style.left = (document.body.clientWidth - Number(objWidth)) / 2;
	obj.style.display = "block";	
}

function toDownloadPage() {
	var prop = "";
	prop=prop+"width=window.screen.availWidth-10,height=window.screen.availHeight-30,";
	prop=prop+"top=0,";
	prop=prop+"left=0,";
	prop=prop+"scrollbars=no";
	returnWindow = window.open("https://lisp.land.moi.gov.tw/MMS/MMSpage.aspx#gobox02", "", prop);
}


</script>
</head>
<body topmargin="10" onLoad="checkAfterAction();">
<form id="form1" name="form1"  method="post" enctype="multipart/form-data" onSubmit="return checkField();">
<input type="hidden" name="isAdminManager" value="<%=user.getIsAdminManager()%>">
<input type="hidden" name="isOrganManager" value="<%=user.getIsOrganManager()%>">
<input type="hidden" name="organID" value="<%=user.getOrganID()%>">
			
<table class="bg" width="70%" cellspacing="0" cellpadding="0" align="center"><tr><td>
	<table class="queryTable"  border="1">
	<tr>
		<td class="td_form" colspan="2" style="text-align:center">土地建物標示代碼匯入(段名代碼表txt)</td>
	</tr>
	
	<tr>
		<td class="queryTDLable"><font color="red">*</font>檔案位置：</td>
		<td class="queryTDInput">
			<input type="file" class="field_Q" name="filePlace">
		</td>
	</tr>
	<tr>
		<td colspan="2" class="td_form_white" style="text-align:center"><font color="blue">傳輸的檔案限制為<%=(maxPostSize/1048576)%>MB，請勿超過。</font></td>
	</tr>
	<tr>
		<td colspan="2" class="td_form_white" style="text-align:center">
			<input class="toolbar_default" type="button" name="btnOpen" value="到資料查詢網站" onClick="toDownloadPage()">
		</td>
	</tr>
	<tr>
		<td class="queryTDInput" colspan="2" style="text-align:center;">
			<input type="hidden" name="actionType" value="">
			<input type="hidden" name="uploadCaseID" value="<%=uploadCaseID%>" >
			<input type="hidden" name="logPath" value="<%=obj.getLogPath()%>">
			<input class="toolbar_default" followPK="false" type="submit" name="querySubmit" value="確定轉入" >
		</td>
	</tr>
	</table>
	
	<div id="messageContainer" style="position:absolute;width:400px;height:100px;display:none">
	<div class="queryTitle">訊息</div>
	<table class="queryTable" border="1">
	<tr>
		<td class="queryTDInput" colspan="4" style="text-align: center;">
			程式處理中，請稍候
		</td>
	</tr>
	</table>
</div>
</td></tr></table>
			
</form>
</body>
</html>
