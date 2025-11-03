
<!--
程式目的：電腦軟體保管資料維護
程式代號：unteg002f
程式日期：0940906
程式作者：clive.chang
--------------------------------------------------------
修改作者　　修改日期　　　修改目的
--------------------------------------------------------
-->

<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../home/head.jsp"%>
<jsp:useBean id="obj" scope="request" class="unt.eg.UNTEG004F">
	<jsp:setProperty name="obj" property="*" />
</jsp:useBean>
<jsp:useBean id="objList" scope="page" class="java.util.ArrayList" />

<%
	objList = obj.queryAll();
	if (!objList.isEmpty()) {
		if("".equals(Common.checkGet(obj.getOwnership()))){
			obj.setOwnership(((String[])objList.get(0))[10]);
			obj.setDifferenceKind(((String[])objList.get(0))[11]);
		}
	}
%>

<html>
<head>
<meta http-equiv="Content-Language" content="zh-tw" />
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<meta http-equiv="Expires" content="-1" />
<meta http-equiv="pragma" content="no-cache" />
<meta http-equiv="Cache-control" content="no-cache" />
<link rel="stylesheet" href="../../js/default.css" type="text/css">
<script type="text/javascript" src="../../js/validate.js"></script>
<script type="text/javascript" src="../../js/function.js"></script>
<script type="text/javascript" src="../../js/tablesoft.js"></script>
<script type="text/javascript" src="../../js/sList2.js"></script>
<script type="text/javascript">
var insertDefault;	//二維陣列, 新增時, 設定預設值
insertDefault = new Array(
	
);
function checkField(){
	
}
function queryOne(enterOrg,ownership,differenceKind){
	form1.enterOrg.value=enterOrg;
	form1.ownership.value=ownership;
	form1.differenceKind.value=differenceKind;
}

function checkURL(surl){
	if (surl == "unteg001f.jsp") {
		form1.currentPage.value = form1.mainPage.value;
	} else {
		form1.currentPage.value = "1";
	}
	
	form1.action=surl;
	beforeSubmit();
	form1.submit();
}

function init() {
	setDisplayItem("spanQueryAll,spanUpdate,spanDelete,spanNextInsert,spanInsert,spanConfirm,spanClear,spanListHidden","H");
}

function clickAddReport(){
	var sUrl = "../ch/untch003f01.jsp?enterOrg="+form1.enterOrg.value+"&q_engineeringNo="+form1.engineeringNo.value+"&q_engineeringNoName="+encodeURIComponent(form1.engineeringName.value)+"&eg_ownership="+form1.ownership.value+"&eg_differenceKind="+form1.differenceKind.value;
	sUrl += "&isEngineering=Y";
	window.open(sUrl,'MyWindow','scrollbars=1, resizable=yes, status=yes, toolbar=no,menubar=no,width=1100,height=600');	
}

</script>
</head>

<body topmargin="0"	onLoad="whatButtonFireEvent('<%=obj.getState()%>');init();showErrorMsg('<%=obj.getErrorMsg()%>');">
<form id="form1" name="form1" method="post"	onSubmit="return checkField()">
<TABLE STYLE="width:100%;" CELLPADDING=0 CELLSPACING=0 valign="top">
	<tr>
		<td ID=t1 CLASS="tab_border2" HEIGHT="25"><a href="#" onClick="return checkURL('unteg001f.jsp');"><font color="red">*</font>營建工程資料維護</a></td>
		<td ID=t2 CLASS="tab_border2"><a href="#" onClick="return checkURL('unteg002f.jsp');">未入帳新增財產</a></td>
		<td ID=t3 CLASS="tab_border2"><a href="#" onClick="return checkURL('unteg003f.jsp');">已入帳新增財產</a></td>
	    <td ID=t4 CLASS="tab_border1">未入帳增減值</a></td>
	    <td ID=t5 CLASS="tab_border2"><a href="#" onClick="return checkURL('unteg005f.jsp');">已入帳增減值</a></td>
	</tr>
	<tr>
		<td class="tab_line2"></td>
		<td class="tab_line2"></td>
		<td class="tab_line2"></td>
		<td class="tab_line1"></td>
		<td class="tab_line2"></td>
	</tr>
</TABLE>
<!--Query區============================================================-->
<div id="queryContainer" style="width:400px;height:400px;display:none">
	<iframe id="queryContainerFrame"></iframe>
	<div class="queryTitle">查詢視窗</div>	
	<% request.setAttribute("UNTEG001Q",obj);%>
	<jsp:include page="unteg001q.jsp" />
</div>
<table width="100%" cellspacing="0" cellpadding="0">
<!--Form區============================================================-->

<!--Toolbar區============================================================-->
<tr>
	<td class="bg" style="text-align:center">
		<input type="hidden" name="state" value="<%=obj.getState()%>"> 
		<input type="hidden" name="queryAllFlag" value="<%=obj.getQueryAllFlag()%>">
		<input type="hidden" name="userID" value="<%=user.getUserID()%>"> 
		<input type="hidden" name="organID" value="<%=user.getOrganID()%>">
		<input type="hidden" name="isOrganManager" value="<%=user.getIsOrganManager()%>">
		<input type="hidden" name="isAdminManager" value="<%=user.getIsAdminManager()%>">
		
		<input type="hidden" class="field_Q" name="enterOrg" value="<%=obj.getEnterOrg()%>">
		<input type="hidden" class="field_Q" name="engineeringNo" value="<%=obj.getEngineeringNo()%>">
		<input type="hidden" class="field_Q" name="engineeringName" value="<%=obj.getEngineeringName()%>">
		
		<input class="field_Q" type="hidden" name="q_deprUnitCB" size="10" maxlength="10" value="<%=obj.getQ_deprUnitCB()%>">
		
		<input type="hidden" class="field_Q" name="ownership" value="<%=obj.getOwnership()%>">
		<input type="hidden" class="field_Q" name="differenceKind" value="<%=obj.getDifferenceKind()%>">
		
		|&nbsp;<input class="toolbar_default" type="button" followPK="false" name="addReport" value="增減值單維護" onClick="clickAddReport();">
		<jsp:include page="../../home/toolbar.jsp" />
		</td>
</tr>
<tr><td>
	<% request.setAttribute("QueryBean",obj);%>
	<jsp:include page="../../home/page.jsp" />
</td></tr>

<!--List區============================================================-->
<tr>
	<td class="bg">
	<div id="listContainer" style="width: 100%;	height: 450px; ">
	<table class="table_form" width="100%" cellspacing="0" cellpadding="0">
		<thead id="listTHEAD">
			<tr>
				<th class="listTH"><a class="text_link_w">NO.</a></th>
				<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',1,false);" href="#">減損單編號</a></th>
				<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',2,false);" href="#">財產編號/序號</a></th>
				<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',3,false);" href="#">財產名稱/別名</a></th>
				<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',4,false);" href="#">數量</a></th>
				<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',5,false);" href="#">總面積</a></th>
				<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',6,false);" href="#">總金額</a></th>
				<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',7,false);" href="#">取得日期</a></th>
				<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',8,false);" href="#">入帳日期</a></th>
				<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',9,false);" href="#">屬公共設施建設費</a></th>
		</thead>
		<tbody id="listTBODY">
			<%
			boolean primaryArray[] = {false,false,false,false,false,false,false,false,false,true,true,true};
			boolean displayArray[] = {true,true,true,true,true,true,true,true,true,false,false,false};
			out.write(util.View.getQuerylist(primaryArray,displayArray,objList,obj.getQueryAllFlag()));
			%>
		</tbody>
	</table>
	</div>
	</td>
</tr>
</table>
</form>

</body>
</html>
