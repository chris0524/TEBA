<%@ page contentType="text/html;charset=UTF-8" import="util.*"%>
<%@ include file="head.jsp" %>
<jsp:useBean id="obj" scope="request" class="util.General">
	<jsp:setProperty name="obj" property="*"/>
</jsp:useBean>
<%
String popPlace = Common.checkGet(request.getParameter("popPlace"));
String popPlaceName = Common.checkGet(request.getParameter("popPlaceName"));
String queryValue = Common.checkGet(request.getParameter("queryValue"));

String q_ID = Common.checkGet(request.getParameter("q_ID"));
String q_Name = Common.checkGet(request.getParameter("q_Name"));

String strList = "";


String sql = " select placeNo, (case when isstop ='Y' then placename + '(已停用)' else placename end) from SYSCA_PlACE "+
		" where enterOrg like '" + Common.esapi(queryValue) + "%'" + "and isstop = 'N'";

if("".equals(Common.get(q_ID))){
}else{					sql += " and placeNo = '" + Common.get(Common.esapi(q_ID)) +"'";
}
if("".equals(Common.get(q_Name))){
}else{					sql += " and placeName like '%" + Common.get(Common.esapi(q_Name)) +"%'";
}


sql += " order by enterOrg, placeNo";

strList = obj.getQueryList(sql);

%>
<html>
<head>
<title>存置地點輔助視窗</title>
<meta http-equiv="Content-Language" content="zh-tw"/>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
<meta http-equiv="Expires" content="-1"/>
<meta http-equiv="pragma" content="no-cache"/>
<meta http-equiv="Cache-control" content="no-cache"/>
<link rel="stylesheet" href="../js/default.css?1=ss" type="text/css">
<script language="javascript" src="../js/validate.js"></script>
<script language="javascript" src="../js/function.js"></script>
<script language="javascript" src="../js/tablesoft.js"></script>
<script language="javascript">
function selectProperty(popPlace, popPlaceName){
	if (isObj(opener.document.all.item("<%=popPlace%>"))) {		
		opener.document.all.item("<%=popPlace%>").value = popPlace;		
	}
	if (isObj(opener.document.all.item("place1"))) {		
		opener.document.all.item("place1").value = popPlace;		
	}	
	if (isObj(opener.document.all.item("<%=popPlaceName%>"))) {		
		opener.document.all.item("<%=popPlaceName%>").value = popPlaceName;		
	}
	if (isObj(opener.document.all.item("place1Name"))) {		
		opener.document.all.item("place1Name").value = popPlaceName;		
	}	
	window.close();
}

function init() {
	
}
</script>
</head>
<body topmargin="3" leftmargin="3" rightmargin="3" bottommargin="3" onLoad="init();">
<form id="form1" name="form1" method="post" onSubmit="return checkField()">
<input type="hidden" name="q_enterOrg" value="<%=user.getOrganID()%>">
<input type="hidden" name="popPlace" value="<%=popPlace%>">

<table width="100%" cellspacing="0" cellpadding="0">
<!--Form區============================================================-->
<tr><td class="bg" >
	<div id="formContainer" style="height:70px">
	<table class="table_form" width="100%" height="100%">	
	<tr>		
		<td class="td_form">&nbsp;<font color="red">*</font>代碼：</td>
		<td class="td_form_white">
			<input class="field" type="text" name="q_ID" size="11" maxlength="11" value="<%=Common.get(q_ID)%>">
		</td>		
	</tr>	
	
	<tr>		
		<td class="td_form">&nbsp;<font color="red">*</font>存置地點：</td>
		<td class="td_form_white">
			<input class="field" type="text" name="q_Name" size="20" maxlength="30" value="<%=Common.get(q_Name)%>"><font color="red">※可輸入關鍵字查詢</font>
		</td>		
	</tr>	
	</table>
	</div>
</td></tr>

<!--Toolbar區============================================================-->
<tr><td class="bg" style="text-align:center">
	<input class="toolbar_default" followPK="false" type="submit" name="querySubmit" value="確　　定" >
	<input class="toolbar_default" followPK="false" type="button" name="queryCannel" value="取　　消" onClick="window.close()">
</td></tr>
<tr><td>
<% request.setAttribute("QueryBean",obj);%>
<jsp:include page="page.jsp" />
</td></tr>
<!--List區============================================================-->
<tr><td class="bg" >
<div id="listContainer" style="height:300px">
<table class="table_form" width="100%" cellspacing="0" cellpadding="0">
	<thead id="listTHEAD">
	<tr>
		<th class="listTH">&nbsp;</th>
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',1,false);" href="#">代碼</a></th>
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',2,false);" href="#">存置地點</a></th>
	</tr>		
	</thead>
	<tbody id="listTBODY">
		<%=strList%>
	</tbody>
</table>
</div>
</td></tr>
</table>	
</form>
</body>
</html>
