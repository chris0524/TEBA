<%@ page import="java.net.URLDecoder"%>
<%@ page contentType="text/html;charset=UTF-8" import="util.*"%>
<%@ include file="head.jsp" %>
<jsp:useBean id="obj" scope="request" class="util.General">
	<jsp:setProperty name="obj" property="*"/>
</jsp:useBean>
<%
String popID = Common.checkGet(request.getParameter("popID"));
String popIDName = Common.checkGet(request.getParameter("popIDName"));
String typeName1 = URLDecoder.decode(Common.checkGet(request.getParameter("typeName")), "UTF-8");
String codekindid = Common.checkGet(request.getParameter("codekindid"));
String condition = Common.checkGet(request.getParameter("condition"));

String q_ID = Common.checkGet(request.getParameter("q_ID"));
String q_Name = Common.checkGet(request.getParameter("q_Name"));

String typeName = "";
if(popID.equals("sourceKind") || popID.equals("q_sourceKind")){
	typeName = "財產來源";
}else if ("增減值原因".equals(typeName1)){
	typeName = typeName1;
}else if(popID.equals("cause") || popID.equals("q_cause")){
	if("2,4".equals(condition)){
		typeName = "減損原因";
	}else {
		typeName = "增加原因";
	}
	
}


String[] colNames = new String[3];
colNames[0] = typeName + "輔助視窗";
colNames[1] = typeName;
colNames[2] = typeName + "名稱";



String strList = "";

String sql = " select codeid, codename from SYSCA_CODE "+
		" where codekindid = '" + Common.esapi(codekindid) + "'";

if("SKD".equals(Common.checkGet(codekindid)) || "AJC".equals(Common.checkGet(codekindid))){
}else{
	sql += " and codecon1 in (" + Common.esapi(condition) + ")";
}
		
		

if("".equals(Common.get(q_ID))){
}else{					sql += " and codeid = '" + Common.get(Common.esapi(q_ID)) +"'";
}
if("".equals(Common.get(q_Name))){
}else{					sql += " and codename like '%" + Common.get(Common.esapi(q_Name)) +"%'";
}


sql += " order by codeid";
System.out.print(sql);
strList = obj.getQueryList(sql);


%>
<html>
<head>
<title><%=colNames[0] %></title>
<meta http-equiv="Content-Language" content="zh-tw"/>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
<meta http-equiv="Expires" content="-1"/>
<meta http-equiv="pragma" content="no-cache"/>
<meta http-equiv="Cache-control" content="no-cache"/>
<link rel="stylesheet" href="../js/default.css?1=ss" type="text/css">
<script type="text/javascript" src="../js/validate.js"></script>
<script type="text/javascript" src="../js/function.js"></script>
<script type="text/javascript" src="../js/tablesoft.js"></script>
<script type="text/javascript">
function selectProperty(popID, popIDName){
	if (isObj(opener.document.all.item("<%=popID%>"))) {		
		opener.document.all.item("<%=popID%>").value = popID;		
	}
	if (isObj(opener.document.all.item("<%=popIDName%>"))) {
		opener.document.all.item("<%=popIDName%>").value = popIDName;		
	}
	opener.checkSelfDepr();
	opener.getSourceKindName(popID, popIDName);

	window.close();
}

function init() {
	
}
</script>
</head>
<body topmargin="3" leftmargin="3" rightmargin="3" bottommargin="3" onLoad="init();">
<form id="form1" name="form1" method="post" onSubmit="return checkField()">
<input type="hidden" name="q_enterOrg" value="<%=user.getOrganID()%>">
<input type="hidden" name="popID" value="<%=popID%>">
<input type="hidden" name="popIDName" value="<%=popIDName%>">

<table width="100%" cellspacing="0" cellpadding="0">
<!--Form區============================================================-->
<tr><td class="bg" >
	<div id="formContainer" style="height:70px">
	<table class="table_form" width="100%" height="100%">	
	<tr>		
		<td class="td_form">&nbsp;<font color="red">*</font><%=colNames[1] %>：</td>
		<td class="td_form_white">
			<input class="field" type="text" name="q_ID" size="11" maxlength="11" value="<%=Common.get(q_ID)%>">
		</td>		
	</tr>	
	
	<tr>		
		<td class="td_form">&nbsp;<font color="red">*</font><%=colNames[2] %>：</td>
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
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',1,false);" href="#"><%=colNames[1] %></a></th>
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',2,false);" href="#"><%=colNames[2] %></a></th>
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
