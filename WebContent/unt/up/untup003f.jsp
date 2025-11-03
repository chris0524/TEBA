<!--
程式目的：單位財產資料轉入作業－檢視錯誤情形
程式代號：untup003f
程式日期：0950710
程式作者：clive.chang
--------------------------------------------------------
修改作者　　修改日期　　　修改目的
--------------------------------------------------------
-->

<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../home/head.jsp" %>
<jsp:useBean id="obj" scope="request" class="unt.up.UNTUP003F">
	<jsp:setProperty name="obj" property="*"/>
</jsp:useBean>
<%
String upType = util.Common.get(request.getParameter("upType"));
if ("".equals(obj.getEnterOrg())) obj.setEnterOrg(user.getOrganID());

if ("queryOne".equals(obj.getState())) {
	obj = (unt.up.UNTUP003F)obj.queryOne(upType, obj.getEnterOrg(),obj.getTextSerialNo());	
} else if ("doTransfer".equals(obj.getState())) {
	obj.doTransferProcess(upType, obj.getEnterOrg(), user.getUserID());
}
String strList = obj.getErrorColumnList(upType, obj.getEnterOrg());
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
var insertDefault;	//二維陣列, 新增時, 設定預設值
var masterFlag = true;
var dtlFlag = false;

function getXmlHttpRequestObject() {
	var req = false;
	if(window.XMLHttpRequest) {
	  try { req = new XMLHttpRequest(); } catch(e) { req = false; }
	}
	else if(window.ActiveXObject)
	{
	  try { req = new ActiveXObject("Msxml2.XMLHTTP"); } catch(e) {
	  try { req = new ActiveXObject("Microsoft.XMLHTTP"); } catch(e) { req = false; } }
	}
	return req;
}

function processReqChange( req, handler ) {
	if (req.readyState == 4 && req.status == 200 && req.responseXML ) {
		//handler( req, req.responseXML ); 		
		handler( req );
	}
}

function parseXMLFile( req ) {
	var nl = req.responseXML.getElementsByTagName( 'record' );
	alert(nl.length);
	for( var i = 0; i < nl.length; i++ ) {	
		var nli = nl.item( i );	
		form1.textSerialNo.value = nli.getAttribute('textSerialNo').toString();
		form1.errorColumn.value = nl.item(i).getAttribute('errorColumn');
	}
}

function parseXMLFile( req ) {
	var response = req.responseXML.documentElement;
	var x=response.getElementsByTagName("ResultSet");	
	for( var i = 0; i < x.length; i++ ) {	
		//alert(x[i].getElementsByTagName("textSerialNo").data );
		alert(i);
	}
}



function queryOne(enterOrg,textSerialNo,sTable){
	if (masterFlag) {	
		var url = "xmlup.jsp?enterOrg="+enterOrg+"&textSerialNo="+textSerialNo+"&sTable="+sTable;
		var xmlDoc=document.createElement("xml");		
		xmlDoc.async=false;			
		if(xmlDoc.load(url)){	
			for(i=0; i<xmlDoc.documentElement.childNodes.length; i++){
				var node = xmlDoc.documentElement.childNodes.item(i);
				form1.textSerialNo.value = node.getAttribute("textSerialNo");
				form1.errorColumn.value = node.getAttribute("errorColumn");
			}		
		}
	}
	/**
	var req = getXmlHttpRequestObject();
	if(req) {
		req.onreadystatechange = function() { processReqChange( req, parseXMLFile ); };
		req.open("GET", url, true);
		req.send("");		
	}
	**/
}

function queryOneDetail(enterOrg,textSerialNo,sTable){
	if (dtlFlag) {
		var url = "xmlup.jsp?enterOrg="+enterOrg+"&textSerialNo="+textSerialNo+"&sTable="+sTable;
		var xmlDoc=document.createElement("xml");		
		xmlDoc.async=false;			
		if(xmlDoc.load(url)){	
			for(i=0; i<xmlDoc.documentElement.childNodes.length; i++){
				var node = xmlDoc.documentElement.childNodes.item(i);
				form1.textSerialNo.value = node.getAttribute("textSerialNo");
				form1.errorColumn.value = node.getAttribute("errorColumn");
			}		
		}
	}
}


function doTransfer() {
	if (parseInt(form1.successRecordCount.value)>0 && parseInt(form1.errorRecordCount.value)==0) {
	   	var prop = "";    
	    prop=prop+'toolbar=no;location=no,directories=no,menubar=no,status=yes,scrollbars=yes,resizable=yes,';
	    prop=prop+'width=400,';
	    prop=prop+'height=110';	           
		var moshe=window.open("","exp",prop); 
		moshe.document.write('<html>');
		moshe.document.write('<head>');
		moshe.document.write('<meta http-equiv=Content-Type content=text/html; charset=UTF-8>');			
		moshe.document.write('<title>.:: 轉入正式資料檔 ::.</title>');
		moshe.document.write('</head>');
		moshe.document.write('<body topmargin="10" leftmargin="10" marginwidth="0" marginheight="0">\n');	
		moshe.document.write('<br><br><div align="center"><font color="#CC0000">暫存資料轉入正式檔案中，請稍候...</font></div>'); 
		moshe.document.write('</body></html>');
		
		form1.state.value = "doTransfer";
		form1.submit();
	} else {
		alert("對不起，只有在合格筆數>0且不合格筆數=0時方可執行此動作！");
		return;
	}
}

function checkURL(surl){
	form1.action = surl;
	form1.submit();
}

function init() {
	document.all.item("spanInsert").style.display="none";
	document.all.item("spanDelete").style.display="none";
	document.all.item("spanNextInsert").style.display="none";
	document.all.item("spanClear").style.display="none";
	document.all.item("spanQueryAll").style.display="none";
	document.all.item("spanUpdate").style.display="none";
	document.all.item("spanConfirm").style.display="none";
	document.all.item("spanListHidden").style.display="none";
	if (form1.state.value=="doTransfer" && form1.errMsg.value!="") {
	   	var prop = "";
	    prop=prop+'toolbar=no;location=no,directories=no,menubar=no,status=yes,scrollbars=yes,resizable=yes,';
	    prop=prop+'width=400,';
	    prop=prop+'height=110';	
		var moshe=window.open("","exp",prop); 
		moshe.close();
		if (form1.errMsg.value!="") alert(form1.errMsg.value);
	} else {
		if (form1.errMsg.value!="") alert(form1.errMsg.value);
	}
}
</script>
</head>

<body topmargin="0" onLoad="init();">
<form id="form1" name="form1" method="post" onSubmit="return checkField()">
	<input type="hidden" name="errMsg" value="<%=obj.getErrorMsg()%>">
	<input type="hidden" name="state" value="<%=obj.getState()%>">
	<input type="hidden" name="editID" value="<%=user.getUserID()%>">	
	<input type="hidden" name="enterOrg" value="<%=obj.getEnterOrg()%>">
	<input type="hidden" name="enterOrgName" value="<%=obj.getEnterOrgName()%>">
	<input type="hidden" name="organID" value="<%=user.getOrganID()%>">
	<input type="hidden" name="isOrganManager" value="<%=user.getIsOrganManager()%>">
	<input type="hidden" name="isAdminManager" value="<%=user.getIsAdminManager()%>">
<TABLE CELLPADDING=0 CELLSPACING=0 STYLE="width:100%;" valign="top">
	<tr>
		<td ID=t1 CLASS="tab_border1" width="50%"><a href="#" onClick="return checkURL('untup002f.jsp');">返回上一頁</a></td>
	    <td ID=t1 CLASS="tab_border1"><a href="#" onClick="doTransfer();"><img src="../../image/hand_direction2.gif" width="39" height="21" border="0" align="absmiddle">轉入正式資料檔</a></td>
	</tr>
</TABLE>	
<table width="100%" cellspacing="0" cellpadding="0">
<!--Form區============================================================-->
<tr><td class="bg">
	<div id="formContainer">
	<table class="table_form" width="100%" height="100%">
	<tr>	
		<td class="td_form">資料類別：</td>
		<td class="td_form_white">
			<input class="field_RO" type="text" name="dataType" size="10" value="<%=obj.getUploadTypeName(upType)%>">		　
			合格筆數：<input class="field_RO" type="text" name="successRecordCount" size="7" maxlength="7" value="<%=obj.getSuccessRecordCount()%>">　錯誤筆數：<input class="field_RO" type="text" name="errorRecordCount" size="7" maxlength="7" value="<%=obj.getErrorRecordCount()%>">　
			Excel列號：<input class="field_RO" type="text" name="textSerialNo" size="7" maxlength="7" value="<%=obj.getTextSerialNo()%>"></td>			
		</tr>
	<tr>	
		<td class="td_form">錯誤說明：</td>
		<td class="td_form_white">
			<textarea name="errorColumn" cols="65" rows="9" class="field_Q"><%=obj.getErrorColumn()%></textarea>
			<jsp:include page="../../home/toolbar.jsp" />			
		</td>
	</tr>					
	</table>
	</div>
</td></tr>
<tr><td>
<% request.setAttribute("QueryBean",obj);%>
<jsp:include page="../../home/page.jsp" />
</td></tr>
<!--List區============================================================-->
<tr><td class="bg">
<div id="listContainer">
<table class="table_form" width="100%" cellspacing="0" cellpadding="0">
<%=strList%>
</table>
</div>
</td></tr>
</table>
</form>
</body>
</html>
