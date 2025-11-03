<!--
*<br>程式目的：單位財產資料轉入作業－轉入暫存檔 
*<br>程式代號：untup001f
*<br>撰寫日期：95/07/06
*<br>程式作者：clive.chang
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
<jsp:useBean id="obj" scope="request" class="unt.up.UNTUP001F">
	<jsp:setProperty name="obj" property="*"/>
</jsp:useBean>
<%
	int maxPostSize = 1024*1024*15; //50 M
	int i = 0;
	int j = 0;
	
    boolean isMultipart = false;

    String contentType = request.getContentType();
    if(contentType!=null){
        contentType = contentType.toLowerCase();
        if(contentType.startsWith("multipart/form-data")){
            isMultipart = true;
        }
    }
    MultipartRequest mr = null;
    String uploadCaseID = "";
    String actionType = "";
    String uploadType = "";
    String fileName = "";
    
    if(isMultipart){            
        uploadCaseID = new java.rmi.dgc.VMID().toString();
        uploadCaseID = uploadCaseID.replaceAll(":","_");
        
        File tempDirectory = new File(System.getProperty("java.io.tmpdir"));                
        tempDirectory = new File(tempDirectory,uploadCaseID);                
        tempDirectory.mkdirs();        
        
        FileRenamePolicy policy = new DefaultFileRenamePolicy();
        String encoding = "UTF-8";
        
        mr = new MultipartRequest(request,tempDirectory.getAbsolutePath(),maxPostSize,encoding,policy);
        actionType = mr.getParameter("actionType");
        uploadType = mr.getParameter("uploadType");
    }
    
    String actionResult = "noAction";
    String actionMessage = "";
    
    if("doUpload".equals(actionType)){        
        Enumeration enu = mr.getFileNames();
        File uploadFile = null;
        
        if(enu.hasMoreElements()){
            String name = (String)enu.nextElement();
            uploadFile = mr.getFile(name);
            if ((int) uploadFile.length()>0)  {
            	obj.setEditID(user.getUserID());
            	if ("".equals(obj.getEnterOrg())) {
            		obj.setEnterOrg(user.getOrganID());
            	}
	            if (obj.doUploadProcess(uploadFile, uploadType, user.getUserID())) {
		            actionResult = "doImportSuccess";
		            actionMessage = "成功傳輸";
		            if (!"".equals(obj.getErrorMsg())) {
		            	actionResult = "doUploadFail";
		            	actionMessage = obj.getErrorMsg();
		            }
	            } else {
	            	actionResult = "doUploadFail";
	            	actionMessage = obj.getErrorMsg();	            	
	            }
            	Common.RemoveDirectory(uploadFile.getParentFile());	            
	            //fileName = uploadFile.getName();				
	   			//String datafile	 = System.getProperty("java.io.tmpdir")+File.separator+uploadCaseID+File.separator+fileName;
	   				
            } else {
            	if (!Common.RemoveDirectory(uploadFile.getParentFile())) {
            		uploadFile.deleteOnExit();
            		uploadFile.getParentFile().deleteOnExit();
            	}
                actionResult = "doUploadFail";
                actionMessage = "上傳失敗，因為上傳的檔案中無任何內容！";            	
            }
        }else{
            actionResult = "doImportFail";
            actionMessage = "找不到匯入的檔案";            
        }
    }
    //obj.dbClose();   
	//util.View.getPopOrgan("field_Q","q_enterOrg",obj.getQ_enterOrg().equals("")?user.getOrganID():obj.getQ_enterOrg(),obj.getQ_enterOrgName().equals("")?user.getOrganName():obj.getQ_enterOrgName())
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
	    prop=prop+'toolbar=no;location=no,directories=no,menubar=no,status=yes,scrollbars=yes,resizable=yes,';
	    prop=prop+'width=400,';
	    prop=prop+'height=110';	           
		var moshe=window.open("","exp",prop); 
		moshe.close();
		moshe=window.open("","exp",prop); 		
		moshe.document.write('<html>');
		moshe.document.write('<head>');
		moshe.document.write('<meta http-equiv=Content-Type content=text/html; charset=UTF-8>');			
		moshe.document.write('<title>.:: 資料傳輸成功 ::.</title>');
		moshe.document.write('</head>');
		moshe.document.write('<body topmargin="2" leftmargin="10" marginwidth="0" marginheight="0">\n');	
		moshe.document.write('<br><div align="center"><font color="#CC0000">' + actionMessage + ' <%=obj.getSuccessRecordCount()%> 筆資料</font></div>'); 
		moshe.document.write('<br><div align="center"><font color="#CC0000">錯誤 <%=obj.getErrorRecordCount()%> 筆資料</font></div>'); 		
		moshe.document.write('</body></html>');					                    
        break;
    case 'doImportFail':
		var moshe=window.open("","exp",prop); 
		moshe.close();    
        alert(actionMessage);
        break;
    }
}

function checkField(){
    if(form1.uploadType.value=="" || form1.FILE.value == ""){
            alert("您必需指定作業項目及檔案存放位置");
            return false;
    } else {
    	var extPos = form1.FILE.value.lastIndexOf(".");
    	var ext = form1.FILE.value.toLowerCase().substring(extPos+1);
    	var allowExt = "xls,XLS";
    	if (extPos==-1) {
    		alert("無法判斷您上傳的檔案格式，請檢查檔案是否有副檔名並重新輸入!");
    		return false;
    	}
    	if (parse(allowExt).length>0) {
    		if (allowExt.search(ext)== -1 ) {
    			alert("上傳的檔案格式必須是Excel檔，請重新輸入!");
    			return false;
    		}
    	}    	
    }
    form1.actionType.value = "doUpload";
/*
   	var prop = "";    
    prop=prop+'toolbar=no;location=no,directories=no,menubar=no,status=yes,scrollbars=yes,resizable=yes,';
    prop=prop+'width=400,';
    prop=prop+'height=110';	           
	var moshe=window.open("","exp",prop); 
	moshe.document.write('<html>');
	moshe.document.write('<head>');
	moshe.document.write('<meta http-equiv=Content-Type content=text/html; charset=UTF-8>');			
	moshe.document.write('<title>.:: 資料匯入中 ::.</title>');
	moshe.document.write('</head>');
	moshe.document.write('<body topmargin="10" leftmargin="10" marginwidth="0" marginheight="0">\n');	
	moshe.document.write('<br><br><div align="center"><font color="#CC0000">資料匯入中，請稍候...</font></div>'); 
	moshe.document.write('</body></html>');					            
*/
/*
	form1.action = "untup001f.jsp";
	form1.target = "_blank";
	form1.submit();
*/
}
</script>
</head>
<body topmargin="10" onload="checkAfterAction();">
<form id="form1" name="form1" action="untup001f.jsp" method="post" enctype="multipart/form-data" onSubmit="return checkField();">
	<input type="hidden" name="organID" value="<%=user.getOrganID()%>">
	<input type="hidden" name="isOrganManager" value="<%=user.getIsOrganManager()%>">
	<input type="hidden" name="isAdminManager" value="<%=user.getIsAdminManager()%>">			
<table class="bg" width="80%" cellspacing="0" cellpadding="0" align="center"><tr><td>
	<table class="queryTable"  border="1">
    <tr>
      <td colspan="2" class="td_form_white">** 傳輸的檔案限制為<%=(maxPostSize/1048576)%>MB，請勿超過。</td>
      </tr>
    <tr>	
	<tr>
        <td class="td_form" colspan="2"><div align="center">單位財產資料轉入作業－轉入暫存檔</div></td>
	</tr>
	<tr>
		<td class="td_form"><font color="red">*</font>入帳機關：</td>
		<td class="td_form_white">
			<%=util.View.getPopOrgan("field_Q","enterOrg",obj.getEnterOrg().equals("")?user.getOrganID():obj.getEnterOrg(),obj.getEnterOrgName().equals("")?user.getOrganName():obj.getEnterOrgName())%>
		</td>
	</tr>
	<tr>
		<td class="td_form"><font color="red">*</font>作業項目：</td>
		<td class="td_form_white">
			<select name="uploadType" type="select" class="field_Q">
				<%=util.View.getTextOption("LA;土地;BU;建物;RF;土地改良物;MP;動產;VP;有價證券;RT;權利",uploadType)%>
			</select>
		</td>
	</tr>	
    <tr>
      <td class="td_form"><font color="red">*</font>檔案位置：</td>
      <td class="td_form_white"><input type="file" class="field_Q" name="FILE"></td>
    </tr>	
    <tr>	
		<td class="queryTDInput" colspan="2" style="text-align:center;">
    <input type="hidden" name="uploadCaseID" value="<%=uploadCaseID%>" >        
    <input type="hidden" name="actionType" value="">		
			<input class="toolbar_default" followPK="false" type="submit" name="upload" value="轉入暫存檔" >
			<input class="toolbar_default" followPK="false" type="reset" name="cancel" value="取　　消" >
		</td>
	</tr>
	</table>
</td></tr></table>	
</form>
</body>
</html>
