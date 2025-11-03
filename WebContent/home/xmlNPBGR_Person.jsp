<%@ page contentType="text/html;charset=UTF-8" import="util.*"%>
<%@ include file="head.jsp" %>
<jsp:useBean id="obj" scope="request" class="util.General">
	<jsp:setProperty name="obj" property="*"/>
</jsp:useBean>
<%
String applyID = Common.checkGet(request.getParameter("applyID"));


String sql="select applyType, applyID, applyName, homeTel, mobile, zipcode, liveAdd1, liveAdd2, liveAdd3, liveAdd4, seq " +
" from NPBGR_Person where 1=1 ";
if (!"".equals(Common.get(applyID))) sql += " and applyID=" + Common.sqlChar(Common.esapi(applyID));


String strList = obj.getPersonList(sql);
%>
<html>
<head>
<title>輔助視窗</title>
<meta http-equiv="Content-Language" content="zh-tw"/>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
<meta http-equiv="Expires" content="-1"/>
<meta http-equiv="pragma" content="no-cache"/>
<meta http-equiv="Cache-control" content="no-cache"/>
<link rel="stylesheet" href="../js/default.css?1=ss" type="text/css">
<script language="javascript" src="../js/validate.js"></script>
<script language="javascript" src="../js/function.js"></script>

<script language="javascript">
function selectOrgan(seq,applyID,applyName,liveAdd4){

	if (isObj(opener.document.all.item("seq"))) {		
		opener.document.all.item("seq").value=seq;		
	}
	if (isObj(opener.document.all.item("applyID"))) {		
		opener.document.all.item("applyID").value=applyID;		
	}
	if (isObj(opener.document.all.item("applyName"))) {		
		opener.document.all.item("applyName").value=applyName;		
	}
	if (isObj(opener.document.all.item("liveAdd4"))) {		
		opener.document.all.item("liveAdd4").value=liveAdd4;		
	}
	
	window.close();
}
</script>
</head>
<body topmargin="3" leftmargin="3" rightmargin="3" bottommargin="3">
<form id="form1" name="form1" method="post">


<table width="100%" cellspacing="0" cellpadding="0">
<!--Form區============================================================-->


<!--Toolbar區============================================================-->

<!--List區============================================================-->
<tr><td class="bg" >
<div id="listContainer" style="height:250px">
<table class="table_form" width="100%" cellspacing="0" cellpadding="0">
	<thead id="listTHEAD">
	<tr>
		<th class="listTH">&nbsp;</th>
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',1,false);" href="#">序號</a></th>
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',2,false);" href="#">身份字號</a></th>
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',3,false);" href="#">姓名</a></th>
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',4,false);" href="#">住址</a></th>
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
