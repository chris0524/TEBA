<!--
程式目的：
程式代號：
程式日期：
程式作者：
--------------------------------------------------------
修改作者　　修改日期　　　修改目的
--------------------------------------------------------
-->

<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../home/head.jsp" %>
<jsp:useBean id="obj" scope="request" class="unt.ne.UNTNE057F">
	<jsp:setProperty name="obj" property="*"/>
</jsp:useBean>
<jsp:useBean id="objList" scope="page" class="java.util.ArrayList"/>

<%
objList = obj.queryAll();


%>

<html>
<head>
<meta http-equiv="Content-Language" content="zh-tw"/>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
<meta http-equiv="Expires" content="-1"/>
<meta http-equiv="pragma" content="no-cache"/>
<meta http-equiv="Cache-control" content="no-cache"/>
<link rel="stylesheet" href="../../js/default.css" type="text/css">
<script type="text/javascript" src="../../js/validate.js"></script>
<script type="text/javascript" src="../../js/function.js"></script>
<script type="text/javascript" src="../../js/tablesoft.js"></script>
<script type="text/javascript" src="../../js/sList2.js"></script>
<script type="text/javascript" src="../../js/jquery-1.8.2.js"></script>
<script language="javascript" src="untne001q.js"></script>
<script type="text/javascript">
var insertDefault;	//二維陣列, 新增時, 設定預設值
var changeDepr = false;
insertDefault = new Array(
	
);

function checkField(){
}

function queryOne(enterOrg,ownership,propertyNo,serialNo){

}

function init(){
	
}

</script>
</head>

<body topmargin="0" onLoad="whatButtonFireEvent('<%=obj.getState()%>');init();showErrorMsg('<%=obj.getErrorMsg()%>');">
<form id="form1" name="form1" method="post" onSubmit="return checkField()">

<TABLE STYLE="width:100%;" CELLPADDING=0 CELLSPACING=0 valign="top">
	<tr>
		<td ID=t1 CLASS="tab_border2" HEIGHT="25"><a href="#" onClick="return checkURL('untne001f.jsp');"><font color="red">*</font>增加單資料</a></td>		
		<td ID=t2 CLASS="tab_border2"><a href="#" onClick="return checkURL('untne002f.jsp');">基本資料</a></td>
		<td ID=t3 CLASS="tab_border2"><a href="#" onClick="return checkURL('untne003f.jsp');">物品明細</a></td>
		<td ID=t4 CLASS="tab_border2"><a href="#" onClick="return checkURL('untne034f.jsp');">附屬設備</a></td>
		<td ID=t5 CLASS="tab_border2"><a href="#" onClick="return checkURL('untne004f.jsp');">附屬設備明細</a></td>
		<td ID=t6 CLASS="tab_border2">帳務資料</td>
		<td ID=t7 CLASS="tab_border2"><a href="#" onClick="return checkURL('untne058f.jsp');">移動紀錄</a></td>
	</tr>
	<tr>
		<td class="tab_line2"></td>
		<td class="tab_line1"></td>
		<td class="tab_line2"></td>
		<td class="tab_line2"></td>
		<td class="tab_line2"></td>
		<td class="tab_line2"></td>
		<td class="tab_line2"></td>			
	</tr>
</TABLE>
<!--Query區============================================================-->
<div id="queryContainer" style="width:750px;height:400px;display:none">
	<iframe id="queryContainerFrame"></iframe>
	<div class="queryTitle">查詢視窗</div>	
	<% request.setAttribute("UNTNE001Q",obj);%>
	<jsp:include page="untne001q.jsp" />
</div>
<table width="100%" cellspacing="0" cellpadding="0">
<!--Form區============================================================-->
<!--Toolbar區============================================================-->
<tr><td class="bg" style="text-align:center;display:none;">		
	<jsp:include page="../../home/toolbar.jsp" />
	<input type="hidden" name="queryAllFlag" value="<%=obj.getQueryAllFlag()%>">
	<input type="hidden" name="state" value="<%=obj.getState()%>">
	<input type="hidden" name="userID" value="<%=user.getUserID()%>">
	<input type="hidden" name="organID" value="<%=user.getOrganID()%>">
	<input class="field_QRO" type="text" name="enterOrg" value="<%=obj.getEnterOrg() %>">
	<input class="field_QRO" type="text" name="ownership" value="<%=obj.getOwnership() %>">
	<input class="field_QRO" type="text" name="caseNo" value="<%=obj.getCaseNo() %>">
	<input class="field_QRO" type="text" name="differenceKind" value="<%=obj.getDifferenceKind() %>">
	<input class="field_QRO" type="text" name="propertyNo" value="<%=obj.getPropertyNo() %>">
	<input class="field_QRO" type="text" name="serialNo" value="<%=obj.getSerialNo() %>">
	<input class="field_QRO" type="text" name="lotNo" value="<%=obj.getLotNo() %>">
</td></tr>
<tr><td>
<% request.setAttribute("QueryBean",obj);%>
<jsp:include page="../../home/page.jsp" />
</td></tr>
<!--List區============================================================-->
<tr><td class="bg">
<table class="table_form" width="100%" cellspacing="0" cellpadding="0">
	<thead id="listTHEAD">
	<tr>
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',1,false);" href="#">項次</a></th>
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',2,false);" href="#">登記日期</a></th>
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',3,false);" href="#">登記憑證</a></th>
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',4,false);" href="#">登記字號</a></th>
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',5,false);" href="#">摘要</a></th>
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',6,false);" href="#">前次帳面金額</a></th>
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',7,false);" href="#">本次增加金額</a></th>
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',8,false);" href="#">本次減少金額</a></th>
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',9,false);" href="#">帳面金額</a></th>
	</thead>
	<tbody id="listTBODY">
	<%
	boolean primaryArray[] = {false,false,false,false,false,false,false,false};
	boolean displayArray[] = {true,true,true,true,true,true,true,true};
	String	alignArray[]   = {"","","","","right","right","right","right"};
	out.write(util.View.getQuerylist(primaryArray,displayArray,alignArray,objList,obj.getQueryAllFlag()));
	%>
	</tbody>
</table>
</td></tr>
</table>
</form>
</body>
</html>



