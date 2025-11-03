<!--
程式目的：動產明細批次修改保管資料
程式代號：untmp004f_batch
程式日期：0951020
程式作者：cherry
--------------------------------------------------------
修改作者　　修改日期　　　修改目的
--------------------------------------------------------
-->

<%@ page contentType="text/html;charset=UTF-8" import="util.*"%>
<%@ include file="../../home/head.jsp" %>
<jsp:useBean id="obj" scope="request" class="unt.mp.UNTMP004F">
	<jsp:setProperty name="obj" property="*"/>
</jsp:useBean>

<%
String popEnterOrg = request.getParameter("enterOrg");
String popOwnership = request.getParameter("ownership");
String popPropertyNo = request.getParameter("propertyNo");
String popLotNo = request.getParameter("LotNo");
String popDifferenceKind = request.getParameter("differenceKind");

if (Common.get(popEnterOrg).length()>5) obj.setEnterOrg(popEnterOrg);
if (Common.get(popOwnership).length()>0) obj.setOwnership(popOwnership);
if (Common.get(popPropertyNo).length()>3) obj.setPropertyNo(popPropertyNo);
if (Common.get(popLotNo).length()>0) obj.setLotNo(popLotNo);
if (Common.get(popDifferenceKind).length()>0) obj.setDifferenceKind(popDifferenceKind);


if ("batchInsert".equals(obj.getState())) {
	int i = 0;
	String[] kys = request.getParameterValues("strKeys");
	StringBuffer sbSQL = new StringBuffer();
	for (i=0; i<kys.length; i++) {		
		sbSQL.append(" update UNTMP_MovableDetail set " ).append(
				" originalMoveDate = " ).append( Common.sqlChar(Common.esapi(Common.get(request.getParameter("moveDate_"+kys[i])))) ).append( "," ).append(
				" originalKeepUnit = " ).append( Common.sqlChar(Common.esapi(Common.get(request.getParameter("keepUnit_"+kys[i])))) ).append( "," ).append(
				" originalKeeper = " ).append( Common.sqlChar(Common.esapi(Common.get(request.getParameter("keeper_"+kys[i])))) ).append( "," ).append(
				" originalUseUnit = " ).append( Common.sqlChar(Common.esapi(Common.get(request.getParameter("useUnit_"+kys[i])))) ).append( "," ).append(
				" originalUser = " ).append( Common.sqlChar(Common.esapi(Common.get(request.getParameter("user_"+kys[i])))) ).append( "," ).append(
				" originalPlace = " ).append( Common.sqlChar(Common.esapi(Common.get(request.getParameter("place_"+kys[i])))) ).append( "," ).append(
				" originalPlace1 = " ).append( Common.sqlChar(Common.esapi(Common.get(request.getParameter("place1_"+kys[i])))) ).append( "," ).append(
				" originalUserNote = " ).append( Common.sqlChar(Common.esapi(Common.get(request.getParameter("userNote_"+kys[i]))))).append( "," ).append(
				" moveDate = " ).append( Common.sqlChar(Common.esapi(Common.get(request.getParameter("moveDate_"+kys[i])))) ).append( "," ).append(
				" keepUnit = " ).append( Common.sqlChar(Common.esapi(Common.get(request.getParameter("keepUnit_"+kys[i])))) ).append( "," ).append(
				" keeper = " ).append( Common.sqlChar(Common.esapi(Common.get(request.getParameter("keeper_"+kys[i])))) ).append( "," ).append(
				" useUnit = " ).append( Common.sqlChar(Common.esapi(Common.get(request.getParameter("useUnit_"+kys[i])))) ).append( "," ).append(
				" userNo = " ).append( Common.sqlChar(Common.esapi(Common.get(request.getParameter("user_"+kys[i])))) ).append( "," ).append(
				" place = " ).append( Common.sqlChar(Common.esapi(Common.get(request.getParameter("place_"+kys[i])))) ).append( "," ).append(
				" place1 = " ).append( Common.sqlChar(Common.esapi(Common.get(request.getParameter("place1_"+kys[i])))) ).append( "," ).append(
				" userNote = " ).append( Common.sqlChar(Common.esapi(Common.get(request.getParameter("userNote_"+kys[i]))))).append( "," ).append(
				" editID = ").append( Common.sqlChar(Common.esapi(user.getUserID())) ).append( "," ).append(
				" editDate = ").append( Common.sqlChar(Common.esapi(Datetime.getYYYMMDD())) ).append( "," ).append(
				" editTime = ").append( Common.sqlChar(Common.esapi(Datetime.getHHMMSS())) ).append(
				" where 1=1 " ).append(
				" and enterOrg = " ).append( Common.sqlChar(Common.esapi(obj.getEnterOrg())) ).append(
				" and ownership = " ).append( Common.sqlChar(Common.esapi(obj.getOwnership())) ).append(
				" and propertyNo = " ).append( Common.sqlChar(Common.esapi(obj.getPropertyNo())) ).append(
				" and serialNo = " ).append( Common.sqlChar(Common.esapi(kys[i])) ).append(
				" and differenceKind = " ).append( Common.sqlChar(Common.esapi(obj.getDifferenceKind())) ).append(
				":;:") ;
		obj.setSerialNo(kys[i]);
	}
	if (i>0) {
		obj.insertProcess(sbSQL.toString());		
	}
}
%>

<html>
<head>
<title>動產保管資料修改視窗</title>
<meta http-equiv="Content-Language" content="zh-tw"/>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
<meta http-equiv="Expires" content="-1"/>
<meta http-equiv="pragma" content="no-cache"/>
<meta http-equiv="Cache-control" content="no-cache"/>
<link rel="stylesheet" href="../../js/default.css" type="text/css">
<script language="javascript" src="../../js/validate.js"></script>
<script language="javascript" src="../../js/function.js?v=0.1"></script>
<script language="javascript" src="../../js/tablesoft.js"></script>
<script language="javascript" src="../../js/sList2.js"></script>
<script language="javascript" src="../../js/jquery-1.8.2.js"></script>
<script language="javascript">
function checkField(){
	var alertStr = "";
	alertStr += checkItemSelect();
	if(alertStr.length!=0){ 
		alert(alertStr); 
		return false; 
	} else {
		form1.state.value="batchInsert";
		return true;
		//form1.submit();	
	}
}

function checkItemSelect() {
	var sFlag = false;
	var alertStr = "";
	for (var i = 0; i < form1.elements.length; i++) {
	    var e = form1.elements[i];
	    if (e.name == "strKeys" && e.checked==true) {	    	
	    	alertStr += checkEmpty(document.all.item("keepUnit_"+e.value),"保管單位");	  
	    	alertStr += checkEmpty(document.all.item("keeper_"+e.value),"保管人");	
	    	alertStr += checkEmpty(document.all.item("useUnit_"+e.value),"使用單位");	
	    	alertStr += checkEmpty(document.all.item("user_"+e.value),"使用人");	  	
			//alertStr += checkEmpty(document.all.item("place_"+e.value),"存置地點");	
	    	sFlag = true;
	    }
	}
	if (sFlag==false) alertStr+="您尚未勾選任何資料！\n";
	return alertStr;
}

function init() {
	if ("Y"=="<%=obj.getBatchInsertFlag()%>") {
		alert("批次修改完成！");
		opener.popWinFlag = true;
		opener.queryOne(form1.enterOrg.value,form1.ownership.value,form1.caseNo.value,form1.differenceKind.value,form1.propertyNo.value,"<%=obj.getSerialNo()%>",form1.lotNo.value);
		window.close();
	} else if("E"=="<%=obj.getBatchInsertFlag()%>") {
		alert("<%=obj.getErrorMsg()%>");
	}
}

</script>
</head>

<body topmargin="0" onLoad="init();">
<form name="form1" method="post" onSubmit="return checkField()">
<table width="100%" cellspacing="0" cellpadding="0">
<!--Toolbar區============================================================-->
<tr><td class="bg" style="text-align:center">
	<input type="hidden" name="state" value="<%=obj.getState()%>">
	<input type="hidden" name="queryAllFlag" value="<%=obj.getQueryAllFlag()%>">
	<input type="hidden" name="userID" value="<%=user.getUserID()%>">
	<input type="hidden" name="enterOrg" value="<%=obj.getEnterOrg()%>">
	<input type="hidden" name="ownership" value="<%=obj.getOwnership()%>">	
	<input type="hidden" name="propertyNo" value="<%=obj.getPropertyNo()%>">
	<input type="hidden" name="serialNo" value="<%=obj.getSerialNo()%>">
	<input type="hidden" name="differenceKind" value="<%=obj.getDifferenceKind()%>">
	<input type="hidden" name="caseNo" value="<%=obj.getCaseNo()%>">
	<input type="hidden" name="lotNo" value="<%=obj.getLotNo()%>">
	<input type="hidden" name="popCount" value="100">
	<input type="hidden" name="batchInsertFlag" value="<%=obj.getBatchInsertFlag()%>">
	<input class="toolbar_default" name="doAdd" type="submit" value="確定修改列表中選取的資料">	
</td></tr>

<!--List區============================================================-->
<tr><td >
<%=obj.getDetailCodeList()%>
</td></tr>
</table>
</form>
</body>
</html>



