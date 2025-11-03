<!--
程式目的：建物樓層批次新增資料
程式代號：untbu001f_batch
程式日期：0950522
程式作者：clive.chang
--------------------------------------------------------
修改作者　　修改日期　　　修改目的
--------------------------------------------------------
-->


<%@ page contentType="text/html;charset=UTF-8" import="util.*"%>
<%@ include file="../../home/head.jsp" %>
<jsp:useBean id="obj" scope="request" class="unt.bu.UNTBU004F">
	<jsp:setProperty name="obj" property="*"/>
</jsp:useBean>

<%
String popEnterOrg = Common.checkGet(request.getParameter("enterOrg"));
String popOwnership = Common.checkGet(request.getParameter("ownership"));
String popPropertyNo = Common.checkGet(request.getParameter("propertyNo"));
String popSerialNo = Common.checkGet(request.getParameter("serialNo"));
String popDifferenceKind = Common.checkGet(request.getParameter("differenceKind"));

if (Common.get(popEnterOrg).length()>5) obj.setEnterOrg(popEnterOrg);
if (Common.get(popOwnership).length()>0) obj.setOwnership(popOwnership);
if (Common.get(popPropertyNo).length()>3) obj.setPropertyNo(popPropertyNo);
if (Common.get(popSerialNo).length()>0) obj.setSerialNo(popSerialNo);
if (Common.get(popDifferenceKind).length()>0) obj.setDifferenceKind(popDifferenceKind);


if ("batchInsert".equals(obj.getState())) {
	int i = 0;
	String[] kys = request.getParameterValues("strKeys");
	String sSQL = "";
	StringBuffer sbSQL = new StringBuffer();
	for (i=0; i<kys.length; i++) {		
		sbSQL.append(" insert into UNTBU_Floor(" ).append(
			" enterOrg,").append(
			" ownership,").append(
			" propertyNo,").append(
			" serialNo,").append(
			" serialNo1,").append(
			" differenceKind,").append(
			" floor,").append(
			" purpose,").append(
			" area,").append(
			" notes,").append(
			" editID,").append(
			" editDate,").append(
			" editTime").append(
		" )Values(" ).append(
			Common.sqlChar(obj.getEnterOrg()) ).append( "," ).append(
			Common.sqlChar(obj.getOwnership()) ).append( "," ).append(
			Common.sqlChar(obj.getPropertyNo()) ).append( "," ).append(
			Common.sqlChar(obj.getSerialNo()) ).append( "," ).append(
			Common.sqlChar("KD12377493") ).append( "," ).append(
			Common.sqlChar(obj.getDifferenceKind()) ).append( "," ).append(
			Common.sqlChar(kys[i]) ).append( "," ).append(
			Common.sqlChar(Common.checkGet(request.getParameter("purpose_"+kys[i]))) + "," +
			Common.sqlChar(Common.checkGet(request.getParameter("area_"+kys[i]))) + "," +
			Common.sqlChar("") ).append( "," ).append(
			Common.sqlChar(user.getUserID()) ).append( "," ).append(
			Common.sqlChar(Datetime.getYYYMMDD()) ).append( "," ).append(
			Common.sqlChar(Datetime.getHHMMSS()) ).append( "):;:") ;		
	}
	if (i>0) {
		obj.insertProcess(sbSQL.toString());		
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
<script language="javascript" src="../../js/sList2.js"></script>
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
	    	alertStr += checkEmpty(document.all.item("area_"+e.value),"樓層面積(㎡)",7,2);	    	
			alertStr += checkFloat(document.all.item("area_"+e.value),"樓層面積(㎡)",7,2);
	    	if (parseFloat("0"+document.all.item("area_"+e.value).value)>0) {
	    		document.all.item("area_"+e.value).style.backgroundColor="";
	    	} else {	    	
	    		document.all.item("area_"+e.value).style.backgroundColor=errorbg;
	    		alertStr += "樓層面積必須>0，請重新輸入！\n";
	    	}
	    	sFlag = true;
	    }
	}
	if (sFlag==false) alertStr+="您尚未勾選任何資料，若無資料可供勾選，請到代碼參數管理作業增加或洽詢相關承辦人員！\n";
	return alertStr;
}

function init() {
	if ("Y"=="<%=obj.getBatchInsertFlag()%>") {
		alert("批次新增完成！");
		opener.popWinFlag = true;
		opener.queryOne(form1.enterOrg.value,form1.ownership.value,form1.propertyNo.value,form1.serialNo.value,form1.serialNo1.value);
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
	<input type="hidden" name="serialNo1" value="<%=obj.getSerialNo1()%>">
	<input type="hidden" name="popCount" value="100">
	<input type="hidden" name="batchInsertFlag" value="<%=obj.getBatchInsertFlag()%>">
	<input class="toolbar_default" name="doAdd" type="submit" value="確定新增列表中選取的資料">	
</td></tr>

<!--List區============================================================-->
<tr><td class="bg">
<%=obj.getFloorCodeList()%>
</td></tr>
</table>
</form>
</body>
</html>



