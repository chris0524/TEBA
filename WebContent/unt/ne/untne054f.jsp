<!--
*<br>程式目的： 
*<br>程式代號：
*<br>撰寫日期：
*<br>程式作者：
*<br>--------------------------------------------------------
*<br>修改作者　　修改日期　　　修改目的
*<br>--------------------------------------------------------
-->
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../home/head.jsp" %>
<%@ page import="java.io.*"%>
<%@ page import="java.util.*"%>
<%@ page import="com.oreilly.servlet.*" %>
<%@ page import="com.oreilly.servlet.multipart.*" %>
<jsp:useBean id="obj" scope="request" class="unt.ne.UNTNE054F">
	<jsp:setProperty name="obj" property="*"/>
</jsp:useBean>
<%
obj.setProgType("UNTNE054F");

String enterOrg = Common.checkGet(request.getParameter("enterorg"));
String ownership = Common.checkGet(request.getParameter("ownership"));
String caseNo = Common.checkGet(request.getParameter("caseno"));
String differenceKind = Common.checkGet(request.getParameter("differenceKind"));


obj.setEnterOrg(enterOrg);
obj.setOwnership(ownership);
obj.setCaseNo(caseNo);
obj.setDifferenceKind(differenceKind);

String errorDataPath = "";

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
    File tempDirectory = new File(application.getInitParameter("filestoreLocation"));
    
    tempDirectory = new File(tempDirectory,uploadCaseID);                
    tempDirectory.mkdirs();             
    
  	//半小時後將其刪除
    //util.FileDeleteProcess.scheduleDeleteFile(tempDirectory,1800000);   
  
    FileRenamePolicy policy = new DefaultFileRenamePolicy();
    String encoding = "UTF-8";

    try{
    	mr = new MultipartRequest(request,tempDirectory.getAbsolutePath(),maxPostSize,encoding,policy);
    	if (mr!=null) {
    		actionType = mr.getParameter("actionType");
    	}
    	
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
			datafile	= application.getInitParameter("filestoreLocation")+File.separator+uploadCaseID+File.separator;
	        
	        obj.setFilePath(datafile);
	        obj.setFileName(fileName);
	        
	        //執行匯入
			actionResult = obj.batchInsert();			
		
			if("doImportLog".equals(actionResult)){
				actionMessage = "轉檔完成!!但內含錯誤資料!!";
			}else{
				actionMessage = "轉檔完成!!";
			}
	        
			//如果格式錯誤
			if("erTitle".equals(actionResult)){
				actionMessage = obj.getErTitle();
			}
			
	    }else{
	        actionResult = "doImportFail";
	        actionMessage = "找不到匯入的檔案";            
	    }
    } catch(Exception e) {
    	e.printStackTrace();
        actionResult = "doImportFail";
        actionMessage = e.toString();    	
    } finally{
    	File f = new File(datafile + File.separator + fileName);
    	if(f.exists()){    		
    		f.delete();
    	}    	
    	errorDataPath = uploadCaseID + ":;:" + "errorList.txt";
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
<script type="text/javascript" src="../../js/validate.js"></script>
<script type="text/javascript" src="../../js/function.js"></script>
<script type="text/javascript" src="../../js/tablesoft.js"></script>
<script type="text/javascript" src="../../js/jquery-1.8.2.js"></script>
<script type="text/javascript">
var actionResult = '<%=actionResult%>';
var actionMessage = '<%=actionMessage%>';
var moshe;
var time=1;

function checkAfterAction(){    
    switch(actionResult){
    case 'noAction':
        break;
    case 'doImportSuccess':
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
		moshe.document.write('<meta http-equiv=Content-Type content=text/html; charset=UTF-8>');			
		moshe.document.write('<title>.:: 資料傳輸結果 ::.</title>');
		moshe.document.write('</head>');
		moshe.document.write('<body topmargin="0" leftmargin="0" marginwidth="0" marginheight="0">\n');	
		moshe.document.write('<br><br><div align="center"><font color="#CC0000">' + actionMessage + '</font>');
		moshe.document.write('</body></html>');

		opener.form1.state.value='queryAll';
		opener.form1.submit();
        break;
    case 'doFileSizeFail':
    	moshe=window.open("","exp","");
    	moshe.close();
        alert("檔案太大！請將檔案重新分割！");
        break;
    case 'doImportFail':
        alert(actionMessage);
        break;        
    case 'doImportLog':
    	$("tr[name='errorList']").show();
    	break;
    case 'erTitle':
    	alert(actionMessage);
    	break;
    }
}

function checkField(){
	debugger;
	var alertStr="";
	var allowExt="xls";
    if(form1.filePlace.value == ""){
            alert("您必需指定檔案存放位置!!!");
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
    			alert("上傳的檔案格式必須是Excel檔(xls)，請重新輸入!");
    			return false;
    		}
    	}    	
    }
    
	if(alertStr.length != 0) { 
		alert(alertStr); 
		return false; 
	} else {
		form1.actionType.value = "into";		
	}
	
}

function init(){
	$("tr[name='errorList']").hide();
}
</script>
</head>
<body topmargin="10" onLoad="init();checkAfterAction();">
<form id="form1" name="form1"  method="post" enctype="multipart/form-data" onSubmit="return checkField();">
<input type="hidden" name="isAdminManager" value="<%=user.getIsAdminManager()%>">
<input type="hidden" name="isOrganManager" value="<%=user.getIsOrganManager()%>">
<input type="hidden" name="organID" value="<%=user.getOrganID()%>">
<input type="hidden" name="actionType" value="">
<input type="hidden" name="uploadCaseID" value="<%=uploadCaseID%>" >
<input class="field_Q" type="hidden" name="enterOrg" size="10" maxlength="10" value="<%=enterOrg%>" >
<input class="field_Q" type="hidden" name="ownership" value="<%=ownership%>" >
<input class="field_Q" type="hidden" name="caseNo" size="15" maxlength="10" value="<%=caseNo%>">
<input class="field_Q" type="hidden" name="differenceKind" size="15" maxlength="10" value="<%=differenceKind%>">
			
<table class="bg" width="70%" cellspacing="0" cellpadding="0" align="center"><tr><td>
	<table class="queryTable"  border="1">
	<tr>
		<td class="queryTDLable"><font color="red">*</font>檔案位置：</td>
		<td class="queryTDInput" colspan="2">
			<input type="file" class="field_Q" name="filePlace">
		</td>
	</tr>
	<tr name="errorList">
		<td class="queryTDLable"></td>
		<td class="queryTDInput" colspan="2">
			<input class="" type="button" name="btn_errorListDownload" onclick="openDownloadWindow('<%=errorDataPath%>');" value="下載錯誤清單">
		</td>
	</tr>
	
	<tr>
		<td class="queryTDInput" colspan="3" style="text-align:center;">
			<input type="submit" class="field" name="btn01" value="Excel匯入">
		</td>
	</tr>
    
	</table>
</td></tr></table>	
				
</form>
</body>
</html>
