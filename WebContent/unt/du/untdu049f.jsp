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
<jsp:useBean id="obj" scope="request" class="unt.du.UNTDU049F">
	<jsp:setProperty name="obj" property="*"/>
</jsp:useBean>
<%
if("".equals(util.Common.checkGet(obj.getQ_enterOrg()))){obj.setQ_enterOrg(user.getOrganID());}
if("".equals(util.Common.checkGet(obj.getQ_enterOrgName()))){obj.setQ_enterOrgName(user.getOrganName());}


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

String enterOrg = "";
String q_enterOrg = "";
String ownership = "";
String differenceKind = "";
String propertyType = "";

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
    String encoding = "UTF-8";

    try{
    	mr = new MultipartRequest(request,tempDirectory.getAbsolutePath(),maxPostSize,encoding,policy);
    	if (mr!=null) {
    		actionType = Common.checkGet(mr.getParameter("actionType"));
    	}
    	
    }catch(IOException e){
    	actionResult="doFileSizeFail";	
    }
}
if ("exportAll".equals(actionType)) {  //Excel匯出
	enterOrg = Common.checkGet(mr.getParameter("enterOrg"));
	ownership = Common.checkGet(mr.getParameter("ownership"));
	differenceKind = Common.checkGet(mr.getParameter("differenceKind"));
	propertyType = Common.checkGet(mr.getParameter("propertyType"));
	obj.setEnterOrg(enterOrg);
	obj.setOwnership(ownership);
	obj.setDifferenceKind(differenceKind);
	obj.setPropertyType(propertyType);
	obj.exportAll();
	}else if ("importAll".equals(actionType) ){  //Excel匯入
	 Enumeration enu = mr.getFileNames();
	    File uploadFile = null;
	    try {
		    if(enu.hasMoreElements()){
		        String name = (String)enu.nextElement();
		        uploadFile = mr.getFile(name);
		        
		        fileName = uploadFile.getName();
		        
		        //取得上傳檔案路徑
				datafile	= System.getProperty("java.io.tmpdir")+File.separator+uploadCaseID+File.separator + fileName;
		        
		        obj.setFilePath(datafile);
		     //   obj.setFileName(fileName);
		        q_enterOrg = Common.checkGet(mr.getParameter("q_enterOrg"));
		        obj.setQ_enterOrg(q_enterOrg);
		        
				actionResult = obj.batchInsert();	    	
				if("doImportFail".equals(actionResult)){
					actionMessage = "轉檔失敗!!但內含錯誤資料!!";
				}else if("noData".equals(actionResult)){
					actionMessage = "檔案內無資料!!";
				}else if("totalColumnIncorrect".equals(actionResult)){
					actionMessage = "檔案欄位數不正確";
				}else if("importSucess".equals(actionResult)){
					actionMessage = "資料上傳成功";		
				}else if(actionResult.indexOf("checkDataValue") != -1){
					actionMessage = actionResult.replaceAll("checkDataValue,","");
					actionResult = "checkDataValue";
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
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
<meta http-equiv="Expires" content="-1"/>
<meta http-equiv="pragma" content="no-cache"/>
<meta http-equiv="Cache-control" content="no-cache"/>
<link rel="stylesheet" href="../../js/default.css" type="text/css">
<script language="javascript" src="../../js/validate.js"></script>
<script language="javascript" src="../../js/function.js"></script>
<script language="javascript" src="../../js/tablesoft.js"></script>
<script language="javascript" src="syslc001f.js"></script>

<style>
	.queryTDLable1{
		background-color:#DFFF8B;
		padding: 2px 2px 2px 2px;
		height:25px;
		text-align:right;	
		text-valign:bottom;	
	}
	
</style>
<script language="javascript">

var actionResult = '<%=actionResult%>';
var actionMessage = '<%=actionMessage%>';
//var moshe;
var time=1;

function checkAfterAction(){  
	var moshe;

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
		moshe.document.write('<meta http-equiv=Content-Type content=text/html; charset=UTF-8>');			
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
    case 'importSucess':
    case 'noData':
    case 'totalColumnIncorrect':
    case 'checkDataValue':
        alert(actionMessage);
        break;
    }
}

function checkField(){
	var alertStr="";
	var allowExt="xls";
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
    			alert("上傳的檔案格式必須是Excel檔(xls)，請重新輸入!");
    			return false;
    		}
    	}    	
    }
	alertStr += checkEmpty(form1.enterOrg,"入帳機關");
	//alertStr += checkEmpty(form1.ownership,"權屬");
	//alertStr += checkEmpty(form1.differenceKind,"財產區分別");
	//alertStr += checkEmpty(form1.propertyType,"財產大類");
	//alertStr += checkEmpty(form1.valueYear,"公告地價年度");
	//alertStr += checkYYY(form1.valueYear,"公告地價年度");
	
	if(alertStr.length != 0) { 
		alert(alertStr); 
		return false; 
	} else {
		form1.actionType.value = "importAll";
				
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


function doQuery(){
	var i=0
	var xmlDoc=document.createElement("xml");
	xmlDoc.async=false;

	var randomnumber=Math.floor(Math.random()*10000000000000);
	var queryValue = "?abc="+randomnumber;
	
	if(xmlDoc.load("syslc001f_query.jsp"+queryValue)){
		document.all.item("row_count").value=xmlDoc.documentElement.childNodes.item(i).getAttribute("row_count");
		document.all.item("total_row_count").value=xmlDoc.documentElement.childNodes.item(i).getAttribute("total_row_count");
	}
}
function clickExportAll(){
	var alertStr="";
	alertStr += checkEmpty(form1.enterOrg,"入帳機關");
	alertStr += checkEmpty(form1.ownership,"權屬");
	alertStr += checkEmpty(form1.differenceKind,"財產區分別");
	alertStr += checkEmpty(form1.propertyType,"財產大類");
	if(alertStr.length != 0) { 
		alert(alertStr); 
		return false; }
	else{
	form1.actionType.value="exportAll";	
	form1.submit();
	}
}

function exportPopup(){
	var moshe;
	if (form1.actionType.value=="exportAll") {
	    var url = "../../downloadFileSimple?fileName=" + form1.excelFileName.value;
		moshe=window.open('','MyWindow','scrollbars=1, resizable=yes, status=no, toolbar=no,menubar=no,width=400,height=150');
		moshe.document.write('<html>');
		moshe.document.write('<head>');
		moshe.document.write('<meta http-equiv=Content-Type content=text/html; charset=UTF-8>');			
		moshe.document.write('<title>.:: 資料輸出 ::.</title>');
		moshe.document.write('</head>');
		moshe.document.write('<body topmargin="0" marginwidth="0" marginheight="0">\n');
		moshe.document.write('<div align=center><br><h>資料輸出成功!</h><br><br></div>');    
		moshe.document.write('<br>輸出檔案下載 --> <a href="' + url + '">下載資料</a><br><br>');	
		moshe.document.write('</body></html>');	
		form1.actionType.value="init";	
	}
}

</script>
</head>
<body topmargin="10" onLoad="exportPopup();checkAfterAction();showErrorMsg('<%=obj.getErrorMsg()%>');">
<form id="form1" name="form1"  method="post" enctype="multipart/form-data" onSubmit="return checkField();">
<input type="hidden" name="state" value="<%=obj.getState()%>">
<input type="hidden" name="queryAllFlag" value="<%=obj.getQueryAllFlag()%>">
<input type="hidden" name="userID" value="<%=user.getUserID()%>">
<input type="hidden" name="organID" value="<%=user.getOrganID()%>">
<input type="hidden" name="isOrganManager" value="<%=user.getIsOrganManager()%>">
<input type="hidden" name="isAdminManager" value="<%=user.getIsAdminManager()%>">		
<input type="hidden" name="excelFileName" value="<%=obj.getExcelFileName()%>">
<input type="hidden" name="actionType" value="<%=obj.getActionType()%>">
<input type="hidden" name="uploadCaseID" value="<%=uploadCaseID%>" >
			
<table class="bg" width="70%" cellspacing="0" cellpadding="0" align="center"><tr><td>
	<table class="queryTable"  border="1">
	
	<tr>
		<td class="queryTDLable1" colspan="3" style="text-align:center">折舊欄位補登－Excel匯出</td>		
	</tr>
	<tr>
		<td class="queryTDLable"><font color="red">*</font>入帳機關：</td>
		<td class="queryTDInput" colspan="2">
			<%=util.View.getPopOrgan("field_Q","enterOrg",user.getOrganID(),user.getOrganName())%>
		</td>
	</tr>
		
	<tr>
		<td class="queryTDLable"><font color="red">*</font>權屬：</td>
		<td class="queryTDInput" colspan="2">
			<select class="field_Q" type="select" name="ownership">
				<%=util.View.getOnwerOption(obj.getOwnership())%>
			</select>
		</td>
	</tr>	
	<tr>
		<td class="queryTDLable"><font color="red">*</font>財產區分別：</td>
		<td colspan="2" class="queryTDInput">
	    <%=util.View._getSelectHTML("field", "differenceKind", obj.getDifferenceKind(),"DFK") %>
       	</td>
	</tr>
    <tr>
		<td class="queryTDLable"><font color="red">*</font>財產大類：</td>
		<td colspan="2" class="queryTDInput">
		<select class="field_Q" type="select" name="propertyType">
		<%=util.View.getOption("select codeID, codeName from SYSCA_Code where codeKindID='PTT' and codeid in ('2','3','4','5','6') ",obj.getPropertyType())%>
		</select>
		</td>
	</tr>	
	<tr>
		<td class="queryTDInput" colspan="3" style="text-align:center;">
			<input class="toolbar_default" type="button" followPK="false" name="exportAll" value="輸出Excel檔"  onClick="clickExportAll();">
		</td>
	</tr>
	<tr>
		<td class="queryTDInput" colspan="3"></td>
	</tr>
	<tr>
		<td class="queryTDLable1" colspan="3" style="text-align:center">折舊欄位補登－Excel匯入</td>		
	</tr>		
	<tr>
		<td class="queryTDLable">入帳機關：</td>
		<td class="queryTDInput" colspan="2">
			<%=util.View.getPopOrgan("field_Q","q_enterOrg",obj.getQ_enterOrg(),obj.getQ_enterOrgName(),"N")%>
		</td>
	</tr>	
	<tr>
		<td class="queryTDLable"><font color="red">*</font>檔案位置：</td>
		<td class="queryTDInput" colspan="2">
			<input type="file" class="field_Q" name="filePlace">
		</td>
	</tr>
	<tr>
		<td class="queryTDInput" colspan="3" style="text-align:center;">
			<input type="submit" class=toolbar_default name="btn02" value="Excel匯入">
		</td>
	</tr>
    
	</table>
</td></tr></table>	
				
</form>
</body>
</html>
