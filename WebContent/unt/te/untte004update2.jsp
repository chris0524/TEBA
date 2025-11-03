<!--
*<br>程式目的：地價稅課稅明細上傳作業-載入資料
*<br>程式代號：untpd004update
*<br>撰寫日期：96/08/10
*<br>程式作者：Blair
*<br>--------------------------------------------------------
*<br>修改作者　　修改日期　　　修改目的
*<br>--------------------------------------------------------
-->
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.io.*"%>
<%@ page import="java.util.*"%>
<%@ page import="com.oreilly.servlet.*" %>
<%@ page import="com.oreilly.servlet.multipart.*" %>
<%@ page import="util.Common" %>
<%@ include file="../../home/head.jsp" %>
<jsp:useBean id="obj" scope="request" class="unt.te.UNTTE004UPDATE">
	<jsp:setProperty name="obj" property="*"/>
</jsp:useBean>
<%
    boolean isMultipart = false;
    String contentType = request.getContentType();
    if(contentType!=null){
        contentType = contentType.toLowerCase();
        if(contentType.startsWith("multipart/form-data")){
            isMultipart = true;
        }
    }

    MultipartRequest mr = null;
    int maxPostSize = 1024*1024*50; //50 M   //檔案大小限制
    String saveDirectory = "C:\\Upload\\"; //上傳檔案放置路徑
    String actionType = "";		//上傳檔案之名稱
    String fileName = "";
    String actionResult = "noAction";	//判斷上傳是否成功
    String actionMessage = "";			//執行結果顯示
    if(isMultipart){
    System.out.println(isMultipart);
        FileRenamePolicy policy = new DefaultFileRenamePolicy();
        String encoding = "UTF-8";
        mr = new MultipartRequest(request,saveDirectory,maxPostSize,encoding,policy);
        actionType = mr.getParameter("start");
    }
   if("doUpload".equals(actionType)){   
        Enumeration enu = mr.getFileNames();   
        File uploadFile = null;
        if(enu.hasMoreElements()){
            String name = (String)enu.nextElement();
            uploadFile = mr.getFile(name);  //取得上傳路徑及檔名-->例:C:\\abc\abc.txt
            fileName = mr.getFilesystemName(name);  //取得檔名-->例:abc.txt
        	if ((int) uploadFile.length()>0)  {
        		System.out.println("檔案傳輸成功");
        		actionResult = "doImportSuccess";
        		actionMessage = "檔案 ：" + fileName + " 上傳作業完成";
        		obj.setFileName(fileName);
        		obj.oneCLICKone();
        	} else {
	            actionResult = "doUploadFail";
	            actionMessage = "檔案：" + fileName + " 上傳作業失敗";
	       	}
        }
    }

%>

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
function checkAfterAction(){    
switch(actionResult){
    case 'noAction':
        break;
    case 'doImportSuccess':
    	var prop = "";  
    	var moshe=window.open("","exp",prop); 
		moshe.close();  
	    prop=prop+'toolbar=no;location=no,directories=no,menubar=no,status=yes,scrollbars=yes,resizable=yes,top=300,left=450,';
	    prop=prop+'width=300,';
	    prop=prop+'height=90';	           
		moshe=window.open("","exp",prop); 		
		moshe.document.write('<html>');
		moshe.document.write('<head>');
		moshe.document.write('<meta http-equiv=Content-Type content=text/html; charset=UTF-8>');			
		moshe.document.write('<title>.:: 資料傳輸成功 ::.</title>');
		moshe.document.write('</head>');
		moshe.document.write('<body topmargin="2" leftmargin="10" marginwidth="0" marginheight="0">\n');	
		moshe.document.write('<br><div align="center"><font color="#CC0000">' + actionMessage + '</font></div>'); 
		moshe.document.write('<br><div align="center"><font color="#CC0000"><input type="button" name="cancel" value="確　　定" onClick="window.close();"></font></div>'); 
		moshe.document.write('</body></html>');	
		opener.form1.submit();
		window.close()			                    
        break;
    case 'doImportFail':
		var moshe=window.open("","exp",prop); 
		moshe.close();    
        alert(actionMessage);
        break;
    }
}

function checkField(){
    	var extPos = form1.FILE.value.lastIndexOf(".");
    	var add = form1.FILE.value ;
    	var ext = form1.FILE.value.toLowerCase().substring(extPos+1);
    	var allowExt = "tdf,TDF";
    	if (extPos==-1) {
    		alert("無法判斷您上傳的檔案格式，請檢查檔案是否有副檔名並重新輸入!");
    		return false;
    	}else if (parse(allowExt).length>0) {
    		if (allowExt.search(ext)== -1 ) {
    			alert("上傳的檔案格式必須是tdf檔，請重新輸入!");
    			return false;
    		}
    	}
    		form1.start.value = "doUpload";
   			var prop = "";    
    		prop=prop+'toolbar=no;location=no,directories=no,menubar=no,status=yes,scrollbars=yes,resizable=yes,';
    		prop=prop+'width=300,';
    		prop=prop+'height=90';	           
			var moshe=window.open("","exp",prop); 
			moshe.document.write('<html>');
			moshe.document.write('<head>');
			moshe.document.write('<meta http-equiv=Content-Type content=text/html; charset=UTF-8>');			
			moshe.document.write('<title>.:: 資料匯入中 ::.</title>');
			moshe.document.write('</head>');
			moshe.document.write('<body topmargin="10" leftmargin="10" marginwidth="0" marginheight="0">\n');	
			moshe.document.write('<br><br><div align="center"><font color="#CC0000">資料匯入中，請稍候...</font></div>'); 
			moshe.document.write('</body></html>');	
}

function init(){
	
}
</script>
</head>
<body topmargin="10" onload="checkAfterAction();init();">
<form id="form1" name="form1" method="post" enctype="multipart/form-data" onSubmit="return checkField();">
<input type="hidden" name="organID" value="<%=user.getOrganID()%>">

<table class="bg" width="80%" cellspacing="0" cellpadding="0" align="center"><tr><td>
	<table class="queryTable"  border="1">
    <tr>
        <td class="td_form" colspan="4" style="text-align:center">上傳地價稅課稅明細資料</td>
	</tr>
	<tr>
     	 <td class="queryTDLable"><font color="red">*</font>檔案位置：</td>
     	 <td class="td_form_white"><input type="file" class="field_Q" name="FILE"></td>
     	 <input class="field" type="hidden" name="filename" size="10" maxlength="10" value="<%=obj.getFileName()%>" >
     	 <input class="field" type="hidden" name="start" size="10" maxlength="10">
    </tr>	
    <tr>	
		 <td class="queryTDInput" colspan="2" style="text-align:center;">   
		 <input class="toolbar_default" followPK="false" type="submit" name="upload" value="上　　傳" >
		 <input class="toolbar_default" followPK="false" type="button" name="cancel" value="取　　消" onClick="window.close();">
		 </td>
	</tr>
	</table>
</td></tr></table>	
</form>
</body>
</html>
