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
<jsp:useBean id="obj" scope="request" class="unt.ch.UNTCH001F06">
	<jsp:setProperty name="obj" property="*"/>
</jsp:useBean>
<jsp:useBean id="objList" scope="page" class="java.util.ArrayList"/>
<!-- 保留第一頁查詢條件與頁數使用 -->
<jsp:useBean id="obj2" scope="request" class="unt.ch.UNTCH001F02_2">
	<jsp:setProperty name="obj2" property="*"/>
</jsp:useBean>

<%

objList = obj.queryAll();


unt.ch.UNTCH001_tab uch = new unt.ch.UNTCH001_tab();
unt.ch.UNTCH001_tab_QUERY uch_QUERY = new unt.ch.UNTCH001_tab_QUERY();
String tabs = "";
String checkStr3 = obj.getPropertyNo().substring(0,3);
String checkStr1 = obj.getPropertyNo().substring(0,1);
if("111".equals(checkStr3)){
	if("true".equals(obj.getIsAddProof())){	tabs = uch._createTabData(uch._RF_ADD, 9);
	}else if("query".equals(obj.getIsAddProof())){
											uch_QUERY._setupViewType(uch_QUERY._queryOrMaintenance);
											tabs = uch_QUERY._createTabData(uch_QUERY._RF_ADD, 8);
	}else if("_query".equals(obj.getIsAddProof())){
											uch._setupViewType(uch._query);
											tabs = uch._createTabData(uch._RF_ADD, 8);
	}else if("_maintenance".equals(obj.getIsAddProof())){
											uch._setupViewType(uch._maintenance);
											tabs = uch._createTabData(uch._RF_ADD, 8);
	}else{									uch._setupViewType(uch._queryOrMaintenance);
											tabs = uch._createTabData(uch._RF_ADD, 8);
	}
}else if("1".equals(checkStr1)){
	if("true".equals(obj.getIsAddProof())){	tabs = uch._createTabData(uch._LA_ADD, 11);
	}else if("query".equals(obj.getIsAddProof())){
											uch_QUERY._setupViewType(uch_QUERY._queryOrMaintenance);
											tabs = uch_QUERY._createTabData(uch_QUERY._LA_ADD, 10);
	}else if("_query".equals(obj.getIsAddProof())){
											uch._setupViewType(uch._query);
											tabs = uch._createTabData(uch._LA_ADD, 10);
	}else if("_maintenance".equals(obj.getIsAddProof())){
											uch._setupViewType(uch._maintenance);
											tabs = uch._createTabData(uch._LA_ADD, 10);
	}else{									uch._setupViewType(uch._queryOrMaintenance);
											tabs = uch._createTabData(uch._LA_ADD, 10);
	}
}else if("2".equals(checkStr1)){
	if("true".equals(obj.getIsAddProof())){	tabs = uch._createTabData(uch._BU_ADD, 13);
	}else if("query".equals(obj.getIsAddProof())){
											uch_QUERY._setupViewType(uch_QUERY._queryOrMaintenance);
											tabs = uch_QUERY._createTabData(uch_QUERY._BU_ADD, 12);
	}else if("_query".equals(obj.getIsAddProof())){
											uch._setupViewType(uch._query);
											tabs = uch._createTabData(uch._BU_ADD, 12);
	}else if("_maintenance".equals(obj.getIsAddProof())){
											uch._setupViewType(uch._maintenance);
											tabs = uch._createTabData(uch._BU_ADD, 12);
	}else{									uch._setupViewType(uch._queryOrMaintenance);
											tabs = uch._createTabData(uch._BU_ADD, 12);
	}
}else if("3".equals(checkStr1) || "4".equals(checkStr1) || "5".equals(checkStr1)){
	if("true".equals(obj.getIsAddProof())){	tabs = uch._createTabData(uch._MP_ADD, 10);
	}else if("query".equals(obj.getIsAddProof())){
											uch_QUERY._setupViewType(uch_QUERY._queryOrMaintenance);
											tabs = uch_QUERY._createTabData(uch_QUERY._MP_ADD, 9);
	}else if("_query".equals(obj.getIsAddProof())){
											uch._setupViewType(uch._query);
											tabs = uch._createTabData(uch._MP_ADD, 6);
	}else if("_maintenance".equals(obj.getIsAddProof())){
											uch._setupViewType(uch._maintenance);
											tabs = uch._createTabData(uch._MP_ADD, 6);
	}else{									uch._setupViewType(uch._maintenance);
											tabs = uch._createTabData(uch._MP_ADD, 6);
	}
}else if("8".equals(checkStr1)){
	if("true".equals(obj.getIsAddProof())){	tabs = uch._createTabData(uch._RT_ADD, 6);
	}else if("query".equals(obj.getIsAddProof())){
											uch_QUERY._setupViewType(uch_QUERY._queryOrMaintenance);
											tabs = uch_QUERY._createTabData(uch_QUERY._RT_ADD, 5);
	}else if("_query".equals(obj.getIsAddProof())){
											uch._setupViewType(uch._query);
											tabs = uch._createTabData(uch._RT_ADD, 5);
	}else if("_maintenance".equals(obj.getIsAddProof())){
											uch._setupViewType(uch._maintenance);
											tabs = uch._createTabData(uch._RT_ADD, 5);
	}else{									uch._setupViewType(uch._queryOrMaintenance);
											tabs = uch._createTabData(uch._RT_ADD, 5);
	}
}else if("9".equals(checkStr1)){
	if("true".equals(obj.getIsAddProof())){	tabs = uch._createTabData(uch._VP_ADD, 7);
	}else if("query".equals(obj.getIsAddProof())){
											uch_QUERY._setupViewType(uch_QUERY._queryOrMaintenance);
											tabs = uch_QUERY._createTabData(uch_QUERY._VP_ADD, 6);
	}else if("_query".equals(obj.getIsAddProof())){
											uch._setupViewType(uch._query);
											tabs = uch._createTabData(uch._VP_ADD, 6);
	}else if("_maintenance".equals(obj.getIsAddProof())){
											uch._setupViewType(uch._maintenance);
											tabs = uch._createTabData(uch._VP_ADD, 6);											
	}else{									uch._setupViewType(uch._queryOrMaintenance);
											tabs = uch._createTabData(uch._VP_ADD, 6);
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
	if (surl=="../ch/untch001f02.jsp"){
		if('<%=obj.getIsAddProof()%>' != 'true'){
			form1.state.value="queryAll";
		}
	}else {
        form1.state.value = "queryAll";
    }
	if(surl=='../ch/untch001f01.jsp' || surl=='../ch/untch001f02_1.jsp' || surl=='../ch/untch001f02_2.jsp') {
		form1.mainPage1.value="";
		form1.currentPage.value=form1.mainPage.value;
	}	
	form1.queryone_enterOrg.value=form1.mainEnterOrg.value;
	form1.queryone_ownership.value=form1.mainOwnerShip.value;
	form1.queryone_caseNo.value=form1.mainCaseNo.value;
	form1.queryone_differenceKind.value=form1.mainDifferenceKind.value;
	
	form1.action = surl;
	beforeSubmit();
	form1.submit();
}

function init(){
	if(form1.progID.value == 'untch001f02extend01'){
		setDisplayItem("spanInsert,spanUpdate,spanDelete,spanClear,spanConfirm,spanNextInsert","H");
	}
}

</script>
</head>

<body topmargin="0" onLoad="whatButtonFireEvent('<%=obj.getState()%>');init();showErrorMsg('<%=obj.getErrorMsg()%>');">
<form id="form1" name="form1" method="post" onSubmit="return checkField()">

<!--Query區============================================================-->
<div id="queryContainer" style="width:750px;height:400px;display:none">
	<iframe id="queryContainerFrame"></iframe>
	<div class="queryTitle">查詢視窗</div>	
	<% request.setAttribute("UNTCH001Q",obj2);%>
	<jsp:include page="../ch/untch001q02_2.jsp" />
</div>

<TABLE STYLE="width:100%;" CELLPADDING=0 CELLSPACING=0 valign="top">
	<%=tabs %>
</TABLE>

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
	<input class="field_QRO" type="hidden" name="lotNo" value="<%=obj.getLotNo() %>">
	<input type="hidden" name="isAddProof" value="<%=obj.getIsAddProof()%>">
	<input type="hidden" name="progID" value="<%=obj.getProgID()%>">
	<input type="hidden" name="listContainerActiveRowId" value="<%=obj.getListContainerActiveRowId()%>">
	<input type="hidden" class="field_Q" name="p_proofYear" value="<%=obj.getProofYear()%>">
	<input type="hidden" class="field_Q" name="p_proofDoc" value="<%=obj.getProofDoc()%>">
	<input type="hidden" class="field_Q" name="p_proofNo" value="<%=obj.getProofNo()%>">
	<input type="hidden" class="field_Q" name="p_summonsDate" value="<%=obj.getSummonsDate()%>">	
	
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



