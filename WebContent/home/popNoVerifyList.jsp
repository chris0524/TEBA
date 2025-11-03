<%@ page contentType="text/html;charset=UTF-8"%>
<jsp:useBean id="obj" scope="page" class="sys.wm.SYSWM001F"/>
<jsp:useBean id="objList" scope="page" class="java.util.ArrayList"/>
<jsp:useBean id="user" scope="session" class="util.User"/>
<% 
String tableNumber = request.getParameter("tableNumber");
obj.setEnterOrg(user.getOrganID());
objList = obj.queryAllNoVerify(tableNumber);

%>
<html>
<head>
<title>系統公告</title>
<meta http-equiv="Content-Language" content="zh-tw"/>
<meta http-equiv="Content-Type"   content="text/html; charset=UTF-8"/>
<meta http-equiv="Expires" content="-1"/>
<meta http-equiv="pragma" content="no-cache"/>
<meta http-equiv="Cache-Control" content="private"/>
<link rel="stylesheet" href="../js/default.css?1=ss" type="text/css">
<script language="javascript" src="../js/validate.js" charset="UTF-8"></script>
<script language="javascript" src="../js/function.js" charset="UTF-8"></script>
<script language="javascript">

</script>
<style type="text/css">
</style>
</head>
<body>
<form id="form1" name="form1" method="post" >
<table width="90%" border="0" align="center" cellpadding="0" cellspacing="0" id="table25">     
	<tr><td class="bg">
		<div id="listContainer">
			<table class="table_form" width="100%" cellspacing="0" cellpadding="0">
				<thead id="listTHEAD">
					<tr>
						<th class="listTH"><a class="text_link_w" >NO.</a></th>
						<th class="listTH"><a class="text_link_w" onClick="return sortTable('listTBODY',1,false);" >表單類別</a></th>
						<th class="listTH"><a class="text_link_w" onClick="return sortTable('listTBODY',2,false);" >單號/案號</a></th>
						<th class="listTH"><a class="text_link_w" onClick="return sortTable('listTBODY',3,false);" >建單日期</a></th>
						<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',4,false);" >建單人員</a></th>
					</tr>			
				</thead>
				<tbody id="listTBODY">
				<%
					boolean primaryArray[] = {false,true,false,false};
					boolean displayArray[] = {true,true,true,true};
					out.write(util.View.getQuerylist(primaryArray,displayArray,objList,"true"));
				%>
				</tbody>
			</table>
		</div>
	</td></tr>                               
</table>
</form>

</body>
</html>

