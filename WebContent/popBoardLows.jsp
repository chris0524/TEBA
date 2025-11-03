<%@ page contentType="text/html;charset=UTF-8"%>
<jsp:useBean id="obj" scope="page" class="sys.wm.SYSWM001F"/>
<jsp:useBean id="objList" scope="page" class="java.util.ArrayList"/>
<jsp:useBean id="user" scope="session" class="util.User"/>
<% 

objList = obj.queryAllLows((user.getUserID()+""),util.Common.checkGet(request.getParameter("newsID")));   

%>
<html>
<head>
<title>系統公告</title>
<meta http-equiv="Content-Language" content="zh-tw"/>
<meta http-equiv="Content-Type"   content="text/html; charset=UTF-8"/>
<meta http-equiv="Expires" content="-1"/>
<meta http-equiv="pragma" content="no-cache"/>
<meta http-equiv="Cache-Control" content="private"/>
<link rel="stylesheet" href="js/default.css" type="text/css">
<script language="javascript" src="js/validate.js"></script>
<script language="javascript" src="js/function.js"></script>
<script language="javascript">

</script>
<style type="text/css">
</style>
</head>
<body>
<center>
<form id="form1" name="form1" method="post" >
<table class="table_form" width="100%">

<!--List區============================================================-->
<tr><td class="bg">
<div id="listContainer">
<table class="table_form" width="100%" cellspacing="0" cellpadding="0">
	<thead id="listTHEAD">
	<tr>
		<th class="listTH" ><a class="text_link_w" >NO.</a></th>
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',1,false);" href="#">案號</a></th>
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',2,false);" href="#">土地標示</a></th>
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',3,false);" href="#">日期</a></th>
	</tr>
	</thead>  
	<tbody id="listTBODY">
	<%
	boolean primaryArray[] = {false,false,false};
	boolean displayArray[] = {true,true,true};
	out.write(util.View.getQuerylist(primaryArray,displayArray,objList,obj.getQueryAllFlag()));
	%> 
	</tbody>
	
</table>
	<tr >
		<td class="td_form_white" colspan="2" style="text-align:center">
			<input class="toolbar_default" type="button" name=btnClose value="關閉視窗" onClick="window.close();">		</td>
	</tr>


</table>

</form>
</center>

</body>
</html>

