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
<jsp:useBean id="obj" scope="request" class="unt.ne.UNTNE058F_1">
	<jsp:setProperty name="obj" property="*"/>
</jsp:useBean>
<jsp:useBean id="objList" scope="page" class="java.util.ArrayList"/>
<!-- 保留第一頁查詢條件與頁數使用 -->
<jsp:useBean id="obj2" scope="request" class="unt.ne.UNTNE003F_1">
	<jsp:setProperty name="obj2" property="*"/>
</jsp:useBean>
<!-- 保留第一頁查詢條件與頁數使用 -->
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
<script type="text/javascript">
var insertDefault;	//二維陣列, 新增時, 設定預設值
var changeDepr = false;
insertDefault = new Array(
	
);

function checkField(){
	
}


function queryOne(enterOrg,ownership,propertyNo,serialNo){

}

function checkURL(surl){
	if( (form1.state.value=="insert") || (form1.state.value=="insertError") || (form1.state.value=="update") || (form1.state.value=="updateError")){
		alert("新增或修改狀態無法更換頁標籤，請先點選取消!");
		return false;
	}else{
		if (surl=="untne001f.jsp" || surl=='untne003f_1.jsp' || surl=='untne003f_2.jsp') {
			form1.mainPage1.value="";
			form1.currentPage.value=form1.mainPage.value;
		}
		form1.state.value="queryAll";		
		surl=surl+"?initDtl=Y";
		if (form1.caseNo.value!="" && form1.ownership.value!="" && form1.enterOrg.value!="" && form1.lotNo.value!=""){ 
			form1.state.value = "queryOne";		
		}

		form1.action = surl;	
		form1.queryone_enterOrg.value=form1.mainEnterOrg.value;
		form1.queryone_ownership.value=form1.mainOwnerShip.value;
		form1.queryone_caseNo.value=form1.mainCaseNo.value;
		form1.queryone_differenceKind.value=form1.mainDifferenceKind.value;	
		beforeSubmit();
		form1.submit();
	}
}

function init(){
	
}

</script>
</head>

<body topmargin="0" onLoad="whatButtonFireEvent('<%=obj.getState()%>');init();showErrorMsg('<%=obj.getErrorMsg()%>');">
<form id="form1" name="form1" method="post" onSubmit="return checkField()">

<TABLE STYLE="width:100%;" CELLPADDING=0 CELLSPACING=0 valign="top">
	<tr>		
		<td ID=t3 CLASS="tab_border2"><a href="#" onClick="return checkURL('untne003f_1.jsp');">物品明細</a></td>
		<td ID=t5 CLASS="tab_border2"><a href="#" onClick="return checkURL('untne004f_1.jsp');">附屬設備明細</a></td>
		<td ID=t6 CLASS="tab_border2"><a href="#" onClick="return checkURL('untne057f_1.jsp');">帳務資料</a></td>
		<td ID=t7 CLASS="tab_border1">移動紀錄</td>
	</tr>
	<tr>
		<td class="tab_line2"></td>
		<td class="tab_line2"></td>
		<td class="tab_line2"></td>
		<td class="tab_line1"></td>			
	</tr>
</TABLE>
<div id="queryContainer" style="width:750px;height:400px;display:none">
	<iframe id="queryContainerFrame"></iframe>
	<div class="queryTitle">查詢視窗</div>	
	<% request.setAttribute("UNTNE001Q",obj2);%>
	<jsp:include page="untne003q_1.jsp" />
</div>
<table width="100%" cellspacing="0" cellpadding="0">
<!--Form區============================================================-->

<!--Toolbar區============================================================-->
<tr><td class="bg" style="text-align:center;display:none;">		
	<jsp:include page="../../home/toolbar.jsp" />
	<input type="hidden" name="queryAllFlag" value="<%=obj.getQueryAllFlag()%>">
	<input type="hidden" name="state" value="<%=obj.getState()%>">
	<input class="field_QRO" type="text" name="enterOrg" value="<%=obj.getEnterOrg() %>">
	<input class="field_QRO" type="text" name="ownership" value="<%=obj.getOwnership() %>">
	<input class="field_QRO" type="text" name="caseNo" value="<%=obj.getCaseNo() %>">
	<input class="field_QRO" type="text" name="differenceKind" value="<%=obj.getDifferenceKind() %>">
	<input class="field_QRO" type="text" name="propertyNo" value="<%=obj.getPropertyNo() %>">
	<input class="field_QRO" type="text" name="serialNo" value="<%=obj.getSerialNo() %>">
	<input class="field_QRO" type="text" name="lotNo" value="<%=obj.getLotNo() %>">
	<input class="field_QRO" type="text" name="organID" value="<%=user.getOrganID()%>">
	
</td></tr>
<% request.setAttribute("QueryBean",obj);%>
<jsp:include page="../../home/page.jsp" />
<!--List區============================================================-->
<tr><td class="bg">
<table class="table_form" width="100%" cellspacing="0" cellpadding="0">
	<thead id="listTHEAD">
	<tr>
		<th class="listTH">
		</th>
		<th class="listTH" colspan="6">移出</th>
		<th class="listTH" colspan="5">移入</th>
	</tr>
	<tr>
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',1,false);" href="#">項次</a></th>
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',2,false);" href="#">移動日期</a></th>
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',3,false);" href="#">保管單位</a></th>
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',4,false);" href="#">保管人</a></th>
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',5,false);" href="#">存置地點</a></th>
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',6,false);" href="#">使用單位</a></th>
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',7,false);" href="#">使用人</a></th>
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',8,false);" href="#">保管單位</a></th>
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',9,false);" href="#">保管人</a></th>
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',10,false);" href="#">存置地點</a></th>
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',11,false);" href="#">使用單位</a></th>
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',12,false);" href="#">使用人</a></th>
	</thead>
	<tbody id="listTBODY">
	<%
	boolean primaryArray[] = {false,false,false,false,false,false,false,false,false,false,false,false};
	boolean displayArray[] = {true,true,true,true,true,true,true,true,true,true,true};
	String	alignArray[]   = {"","","","","","","","","","",""};
	out.write(util.View.getQuerylist(primaryArray,displayArray,alignArray,objList,obj.getQueryAllFlag()));
	%>
	</tbody>
</table>
</td></tr>
</table>
</form>
</body>
</html>




