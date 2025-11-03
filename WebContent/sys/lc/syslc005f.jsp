<!--
*<br>程式目的：地政機關資料批次轉入 
*<br>程式代號：syslc005f
*<br>撰寫日期：
*<br>程式作者：
*<br>--------------------------------------------------------
*<br>修改作者　　修改日期　　　修改目的
*<br>--------------------------------------------------------
-->
<%@ page contentType="text/html;charset=big5" %>
<%@ include file="../../home/head.jsp" %>
<%@ page import="java.io.*"%>
<%@ page import="java.util.*"%>
<%@ page import="com.oreilly.servlet.*" %>
<%@ page import="com.oreilly.servlet.multipart.*" %>
<jsp:useBean id="obj" scope="request" class="sys.lc.SYSLC005F">
	<jsp:setProperty name="obj" property="*"/>
</jsp:useBean>
<%
int maxPostSize = 1024*1024*100;//100M

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
	if(contentType.startsWith("multipart/form-data")) {
		isMultipart = true;
	}
}

if(isMultipart) {
	uploadCaseID = new java.rmi.dgc.VMID().toString();
	uploadCaseID = uploadCaseID.replaceAll(":","_");
	File tempDirectory = new File(System.getProperty("java.io.tmpdir"));
	tempDirectory = new File(tempDirectory,uploadCaseID);
	tempDirectory.mkdirs();

	//半小時後將其刪除
	//util.FileDeleteProcess.scheduleDeleteFile(tempDirectory,1800000);

	FileRenamePolicy policy = new DefaultFileRenamePolicy();
	String encoding = "MS950";

	try{
		mr = new MultipartRequest(request,tempDirectory.getAbsolutePath(),maxPostSize,encoding,policy);
		actionType = Common.checkGet(mr.getParameter("actionType"));
	}catch(IOException e){
		actionResult="doFileSizeFail";	
	}
}

if("into".equals(actionType)) {
	Enumeration enu = mr.getFileNames();
	File uploadFile = null;
	try {
		if(enu.hasMoreElements()){
			String name = (String)enu.nextElement();
			uploadFile = mr.getFile(name);
		
			fileName = uploadFile.getName();
			
			//取得上傳檔案路徑
			datafile	= System.getProperty("java.io.tmpdir")+File.separator+uploadCaseID+File.separator + fileName;
			
			obj.setFileName(this.getServletContext().getRealPath("\\sqlfile\\sys\\lc"));
			
			//執行新增資料
			obj.setTranYYYMM(Common.checkGet(mr.getParameter("tranYYYMM")));
			obj.setpriceYear(Common.checkGet(mr.getParameter("priceYear")));
			obj.setOrganID(user.getOrganID());
			//obj.setvalueYear(mr.getParameter("valueYear"));
		
			actionResult = obj.batchInsert(datafile);
			
			if("doImportLog".equals(actionResult)){
				actionMessage = "轉檔完成!!但內含錯誤資料!!";
				
			}else if("doImportError".equals(actionResult)){
				actionMessage = "轉檔資料格式錯誤!!";
				
			}else{
				actionMessage = "轉檔完成!!";
			}
			
		}else{
			actionResult = "doImportFail";
			actionMessage = "找不到匯入的檔案";
		}
	} catch(Exception e) {
		actionResult = "doImportFail";
		actionMessage = e.toString();
	} finally{
		File f=new File(datafile);
		if(f.exists()){
			f.delete();
		}
	}
}

%>

<%@page import="util.Datetime"%>
<html>
<head>
<meta http-equiv="Content-Language" content="zh-tw"/>
<meta http-equiv="Content-Type" content="text/html; charset=MS950"/>
<meta http-equiv="Expires" content="-1"/>
<meta http-equiv="pragma" content="no-cache"/>
<meta http-equiv="Cache-control" content="no-cache"/>
<link rel="stylesheet" href="../../js/default.css" type="text/css">
<script language="javascript" src="../../js/validate.js"></script>
<script language="javascript" src="../../js/function.js"></script>
<script language="javascript" src="../../js/tablesoft.js"></script>
<script language="javascript" src="syslc001f.js"></script>
<script language="javascript">

var actionResult = '<%=actionResult%>';
var actionMessage = '<%=actionMessage%>';
var moshe;
var time=1;

function checkAfterAction(){
	switch(actionResult){
	case 'noAction':
		break;
	case 'doImportSuccess':
	case 'doImportLog':
		var prop = "";
		var windowTop=(document.body.clientHeight+150)/2+10;
		var windowLeft=(document.body.clientWidth-150)/2+100;
		prop=prop+'toolbar=no;location=no,directories=no,menubar=no,status=yes,scrollbars=yes,resizable=yes,';
		prop=prop+"width=400,height=110,";
		prop=prop+"top="+windowTop+",";
		prop=prop+"left="+windowLeft;
		moshe=window.open("","exp",prop); 
		moshe.close();
		moshe=window.open("","exp",prop);
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
		moshe=window.open("","exp","");
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

function checkField(){
	var alertStr="";
	var allowExt="txt";
	if(form1.filePlace.value == ""){
		alert("您必需指定檔案存放位置");
		return false;
	}else{
		var extPos = form1.filePlace.value.lastIndexOf(".");
		var ext = form1.filePlace.value.toLowerCase().substring(extPos+1);
		if (extPos==-1) {
			alert("無法判斷您上傳的檔案格式，請檢查檔案是否有副檔名並重新輸入!");
			return false;
		}
		if (parse(allowExt).length>0) {
			if (allowExt.search(ext)== -1 ) {
				alert("上傳的檔案格式必須是TXT檔(txt)，請重新輸入!");
				return false;
			}
		}
	}
	alertStr += checkEmpty(form1.enterOrg,"入帳機關");
	alertStr += checkEmpty(form1.tranYYYMM,"轉入年月");
	alertStr += checkYYYMM(form1.tranYYYMM,"轉入年月");
	alertStr += checkEmpty(form1.priceYear,"公告現值年度");
	alertStr += checkYYY(form1.priceYear,"公告現值年度");
	if(alertStr.length != 0) { 
		alert(alertStr); 
		return false; 
	} else {
		form1.actionType.value = "into";
	}
}

function cancelUpload(){
	window.close();
}

function doProExcel(){
	var prop = "";
	prop=prop+'toolbar=no;location=no,directories=no,menubar=no,status=yes,scrollbars=yes,resizable=yes,';
	prop=prop+'width=400,';
	prop=prop+'height=110';
	window.open("syslc005p.jsp","exp",prop);
}


function checkID(){
	if(form1.isAdminManager.value=='Y'){
		document.getElementById('tr1').style.display="";
	}
}
</script>
</head>
<body topmargin="10" onLoad="checkID();checkAfterAction();">
<form id="form1" name="form1"  method="post" enctype="multipart/form-data" onSubmit="return checkField();">
<input type="hidden" name="isAdminManager" value="<%=user.getIsAdminManager()%>">
<input type="hidden" name="isOrganManager" value="<%=user.getIsOrganManager()%>">
<input type="hidden" name="organID" value="<%=user.getOrganID()%>">
			
<table class="bg" width="70%" cellspacing="0" cellpadding="0" align="center"><tr><td>
	<table class="queryTable"  border="1">
	<tr>
		<td class="td_form" colspan="2" style="text-align:center">地政機關資料批次轉入</td>
	</tr>
	<tr id="tr1" style="display:none">
		<td class="queryTDLable"><font color="red">*</font>入帳機關：</td>
		<td class="queryTDInput">
			<%=util.View.getPopOrgan("field_Q","enterOrg",user.getOrganID(),user.getOrganName(),"N")%>
		</td>
	</tr>	
	<tr>
		<td class="queryTDLable"><font color="red">*</font>轉入年月：</td>
		<td class="queryTDInput">
			<input class="field_Q" type="text" name="tranYYYMM" size="5" maxlength="5" value="<%=obj.getTranYYYMM()%>" onblur="check4Int(this.name,'轉入年月');checkFormZero(this.name,5);"><font color="red">(YYYMM)</font>
		</td>
	</tr>
	<tr>
		<td class="queryTDLable"><font color="red">*</font>公告現值年度：</td>
		<td class="queryTDInput">
			<input class="field_Q" type="text" name="priceYear" size="3" maxlength="3" value="<%=obj.getpriceYear()%>" onblur="check4Int(this.name,'公告現值年度');checkFormZero(this.name,3);"><font color="red">(YYY)</font>
		</td>
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
		<td class="queryTDInput" colspan="2" style="text-align:center;">
			<input type="hidden" name="actionType" value="">
			<input type="hidden" name="uploadCaseID" value="<%=uploadCaseID%>" >
			<input type="hidden" name="logPath" value="<%=obj.getLogPath()%>">
			<input class="toolbar_default" followPK="false" type="submit" name="querySubmit" value="確定轉入" >
		</td>
	</tr>
	</table>
</td></tr></table>
			
</form>
</body>
</html>
