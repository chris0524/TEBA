<!--
程式目的：土地合併分割重測重劃作業－增加單地上物資料
程式代號：untla064f
程式日期：
程式作者：Yuan-Ren Jheng
--------------------------------------------------------
修改作者　　修改日期　　　修改目的
--------------------------------------------------------
-->

<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../home/head.jsp" %>
<jsp:useBean id="obj" scope="request" class="unt.la.UNTLA064F">
	<jsp:setProperty name="obj" property="*"/>
</jsp:useBean>
<jsp:useBean id="objList" scope="page" class="java.util.ArrayList"/>

<%
if ("queryOne".equals(obj.getState())) {
	obj = (unt.la.UNTLA064F)obj.queryOne();
	
}else if ("insert".equals(obj.getState())) {
	obj.insert();
	
}else if ("update".equals(obj.getState())) {
	obj.update();
	
}else if ("delete".equals(obj.getState())) {
	obj.delete();
	obj.clearAllDataForView();
	obj.setSerialNo1_064("");
	
}else if ("insertError".equals(obj.getState()) || "updateError".equals(obj.getState()) || "deleteError".equals(obj.getState())){
	obj.clearAllDataForView();

}

if ("queryAll".equals(obj.getState())) {
	if ("false".equals(obj.getQueryAllFlag())){obj.setQueryAllFlag("true"); }
}else if("init".equals(obj.getState())) {
	obj.clearAllDataForView();
	obj.setSerialNo1_064("");
	
}else{
	obj.setQueryAllFlag("true");	
}

if ("true".equals(obj.getQueryAllFlag())){
	objList = obj.queryAll();	
	obj = (unt.la.UNTLA064F)obj.queryOne();
	
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
<script type="text/javascript" src="../../js/jquery-1.8.2.js"></script>
<script type="text/javascript" src="../../js/TJS.js"></script>
<script type="text/javascript">


var insertDefault;	//二維陣列, 新增時, 設定預設值
insertDefault = new Array(
	new Array("useArea_064","<%=obj.getHoldAreaFromUntla_Land()%>")
);

function checkField(){	
	var alertStr="";
	if(form1.state.value=="queryAll"){
		alertStr += checkQuery();
	}else if(form1.state.value=="insert"||form1.state.value=="insertError"||form1.state.value=="update"||form1.state.value=="updateError"){
		alertStr += checkEmpty(form1.ownership1,"地上物權屬");
		alertStr += checkEmpty(form1.state_064,"地上物狀況");
		alertStr += checkEmpty(form1.purpose,"地上物用途");
		alertStr += checkEmpty(form1.useArea_064,"使用本筆土地面積(㎡)");

		alertStr += checkLen(form1.notes_064,"備註",250);
	}

	if(alertStr.length!=0){ alert(alertStr); return false; }

	
	
	beforeSubmit();
}

function queryOne(serialNo1){
	form1.serialNo1_064.value = serialNo1;
	form1.caseNo.value = form1.caseNo_Add.value;
	form1.propertyNo.value = form1.propertyNo_Add.value;
	form1.serialNo.value = form1.serialNo_Add.value;

	form1.state.value="queryOne";
	beforeSubmit();
	form1.submit();
}

function init(){
	setDisplayItem("spanQueryAll,spanNextInsert", "H");
	setFormItem("insert,update,delete","R");
	setFormItem("dataSelect","O");

	//若沒有選擇任何資料
	if(form1.serialNo1_064.value==''){				setFormItem("update,delete","R");
													setFormItem("insert","O");
	}else{											setFormItem("insert,update,delete","O");}
	
	//非登入者所屬機關所登錄的資料
	if(form1.enterorgNotConfirm.value=="Y"){		setFormItem("insert,update,delete,dataSelect","R");}
	//查出的資料若其「案件資料」已入帳
	if(form1.alreadyVerify.value=="Y"){				setFormItem("insert,update,delete,dataSelect","R");}	

	setFormItem("dataSelect","O");
}


function checkURL(surl){
	if (form1.state.value=="insert"||form1.state.value=="insertError"||form1.state.value=="update"||form1.state.value=="updateError"){
		alert("新增或修改狀態無法更換頁標籤，請先點選取消!");
	}else{
		form1.caseNo.value = form1.caseNo_Add.value;
		form1.propertyNo.value = form1.propertyNo_Add.value;
		form1.serialNo.value = form1.serialNo_Add.value;
		
		form1.action=surl+"?initDtl=Y";

		form1.state.value="queryAll";
		
		beforeSubmit();
		form1.submit();
	}
}

function execDataSelect(){
	var prop="";
	var windowTop=(document.body.clientHeight)/2-100;
	var windowLeft=(document.body.clientWidth)/2-800;
	prop=prop+"scrollbars=1, resizable=yes, status=yes, toolbar=no, menubar=no,";
	prop=prop+"width=1200,height=400,";
	prop=prop+"top="+windowTop+",";
	prop=prop+"left="+windowLeft;
	var condition="?enterOrg="+form1.enterOrg.value+"&ownership="+form1.ownership.value+"&caseNo_Merge="+form1.caseNo_Merge.value+"&differenceKind="+form1.differenceKind.value
					+"&caseNo_Reduce="+form1.caseNo_Reduce.value+"&caseNo_Add="+form1.caseNo_Add.value+"&propertyNo_Add="+form1.propertyNo_Add.value+"&serialNo_Add="+form1.serialNo_Add.value;
	window.open("untla065f.jsp" + condition,'MyWindow',prop);	
}

</script>
</head>

<body topmargin="5" onLoad="whatButtonFireEvent('<%=obj.getState()%>');init();showErrorMsg('<%=obj.getErrorMsg()%>');">
<form id="form1" name="form1" method="post" onSubmit="return checkField()">

<!--標籤區============================================================-->
<TABLE STYLE="width:100%;" CELLPADDING=0 CELLSPACING=0 valign="top">
	<tr>
		<td ID=t1 CLASS="tab_border2"><a href="#" onClick="return checkURL('untla054f.jsp');"><font color="red">*</font>案件資料</a></td>
		<td ID=t2 CLASS="tab_border2"><a href="#" onClick="return checkURL('untla055f.jsp');"><font color="red">*</font>減損單</a></td>
		<td ID=t3 CLASS="tab_border2"><a href="#" onClick="return checkURL('untla056f.jsp');"><font color="red">*</font>減損單明細</a></td>
		<td ID=t4 CLASS="tab_border2"><a href="#" onClick="return checkURL('untla057f.jsp');"><font color="red">*</font>增加單</a></td>
		<td ID=t5 CLASS="tab_border2"><a href="#" onClick="return checkURL('untla058f.jsp');"><font color="red">*</font>增加單明細</a></td>
		<td ID=t6 CLASS="tab_border2"><a href="#" onClick="return checkURL('untla062f.jsp');">增加單管理資料</a></td>
		<td ID=t7 CLASS="tab_border1" HEIGHT="25">增加單地上物資料</td>
		<td ID=t8 CLASS="tab_border2"><a href="#" onClick="return checkURL('untla066f.jsp');">調整前後對應資料</a></td>
	</tr>
	<tr>
		<td class="tab_line2"></td>
		<td class="tab_line2"></td>
		<td class="tab_line2"></td>
		<td class="tab_line2"></td>
		<td class="tab_line2"></td>	
		<td class="tab_line2"></td>	
		<td class="tab_line1"></td>	
		<td class="tab_line2"></td>					
	</tr>
</TABLE>
<!--隱藏欄位===========================================================-->
<input class="field_QRO" type="hidden" name="enterorgNotConfirm" value="<%=obj.getEnterorgNotConfirm()%>">
<input class="field_QRO" type="hidden" name="alreadyVerify" value="<%=obj.getAlreadyVerify()%>">

<input class="field_QRO" type="hidden" name="enterOrg" size="10" maxlength="10" value="<%=obj.getEnterOrg()%>" >
<input class="field_QRO" type="hidden" name="ownership" size="10" maxlength="10" value="<%=obj.getOwnership()%>" >
<input class="field_QRO" type="hidden" name="differenceKind" value="<%=obj.getDifferenceKind()%>">

<input class="field_QRO" type="hidden" name="caseNo_Merge" value="<%=obj.getCaseNo_Merge()%>">
<input class="field_QRO" type="hidden" name="caseNo_Reduce" value="<%=obj.getCaseNo_Reduce()%>">
<input class="field_QRO" type="hidden" name="caseNo_Add" value="<%=obj.getCaseNo_Add()%>">

<input class="field_QRO" type="hidden" name="propertyNo_Reduce" value="<%=obj.getPropertyNo_Reduce()%>">
<input class="field_QRO" type="hidden" name="propertyNo_Add" value="<%=obj.getPropertyNo_Add()%>">
<input class="field_QRO" type="hidden" name="serialNo_Reduce" value="<%=obj.getSerialNo_Reduce()%>">
<input class="field_QRO" type="hidden" name="serialNo_Add" value="<%=obj.getSerialNo_Add()%>">

<input class="field_QRO" type="hidden" name="caseNo">
<input class="field_QRO" type="hidden" name="propertyNo">
<input class="field_QRO" type="hidden" name="serialNo">

<input type="hidden" name="state" value="<%=obj.getState()%>">
<input type="hidden" name="queryAllFlag" value="<%=obj.getQueryAllFlag()%>">
<input type="hidden" name="userID" value="<%=user.getUserID()%>">
<input type="hidden" name="organID" value="<%=user.getOrganID()%>">
<input type="hidden" name="isOrganManager" value="<%=user.getIsOrganManager()%>">
<input type="hidden" name="isAdminManager" value="<%=user.getIsAdminManager()%>">
<input type="hidden" name="listContainerActiveRowId" value="<%=obj.getListContainerActiveRowId()%>">

<!--Query區============================================================-->
<div id="queryContainer" style="width:500px;height:200px;display:none">
	<iframe id="queryContainerFrame"></iframe>
	<div class="queryTitle">查詢視窗</div>	
	<% request.setAttribute("UNTLA054Q",obj);%>
	<jsp:include page="untla054q.jsp" />
</div>
<table width="100%" cellspacing="0" cellpadding="0">
<!--Form區============================================================-->
<tr><td class="bg">
	<div id="formContainer">
	<table class="table_form" width="100%" height="100%">
		<tr>
			<td class="td_form" width="10%"><font color="red">*</font>地上物次序：</td>
			<td class="td_form_white" width="30%">
				[<input class="field_PRO" type="text" name="serialNo1_064" size="3" maxlength="3" value="<%=obj.getSerialNo1_064()%>">]
			</td>
			<td class="td_form" width="10%"><font color="red">*</font>地上物權屬：</td>
			<td class="td_form_white" width="30%">
				<select class="field" type="select" name="ownership1">
					<%=util.View.getOption("select codeID, codeName from SYSCA_Code where codeKindID='OWN' ORDER BY CODEID", obj.getOwnership1())%>
				</select>
			</td>
		</tr>
		<tr>
			<td class="td_form">管理機關：</td>
			<td class="td_form_white">
				<%=util.View.getPopOrgan("field","manageOrg",obj.getManageOrg(),obj.getManageOrgName(),"Y")%>				
			</td>
			<td class="td_form">地上物所有人：</td>
			<td class="td_form_white">
				<input class="field" type="text" name="owner" size="30" maxlength="30" value="<%=obj.getOwner()%>">
			</td>
		</tr>
		<tr>
			<td class="td_form"><font color="red">*</font>地上物狀況：</td>
			<td class="td_form_white">
				<input class="field" type="text" name="state_064" size="30" maxlength="60" value="<%=obj.getState_064()%>">
				<br>
				<font color="red">(門牌、建號或具體之使用狀況)</font>
			</td>
			<td class="td_form"><font color="red">*</font>地上物用途：</td>			
			<td class="td_form_white">
				<select class="field" type="select" name="purpose">
					<%=util.View.getOption("select codeID, codeName from SYSCA_Code where codeKindID='PUR' ORDER BY CODEID", obj.getPurpose())%>
				</select>
			</td>
		</tr>
		<tr>
			<td class="td_form"><font color="red">*</font>使用本筆土地面積(㎡)：</td>
			<td class="td_form_white">
				<input class="field" type="text" name="useArea_064" size="10" maxlength="10" value="<%=obj.getUseArea_064()%>">
			</td>
			<td class="td_form">併用土地標示：</td>			
			<td class="td_form_white">
				<input class="field" type="text" name="mergeUseSign" size="30" maxlength="60" value="<%=obj.getMergeUseSign()%>">
			</td>
		</tr>
		<tr>
			<td class="td_form">其他事項：</td>
			<td class="td_form_white" colspan="3">
				<input class="field" type="text" name="notes1_064" size="60" maxlength="60" value="<%=obj.getNotes1_064()%>">
			</td>
		</tr>
		<tr>
			<td class="td_form">備註：</td>
			<td class="td_form_white"colspan="4">
				<textarea class="field" type="text" name="notes_064" cols="25" rows="4"><%=obj.getNotes_064()%></textarea>
			</td>			
			<td class="td_form"style="display:none;">異動人員/日期：</td>
			<td class="td_form_white"style="display:none;">
				[<input class="field_RO" type="text" name="editID" size="10" value="<%=obj.getEditID()%>">/
				<input class="field_RO" type="text" name="editDate" size="7" value="<%=obj.getEditDate()%>">]
			</td>	
		</tr>
	</table>
	</div>
</td></tr>
<!--Toolbar區============================================================-->
<tr><td class="bg" style="text-align:center">			
	<jsp:include page="../../home/toolbar.jsp" />	
	<input class="toolbar_default" type="button" followPK="false" name="dataSelect" value="資料挑選" disabled="true" onClick="execDataSelect();">&nbsp;|
</td></tr>
<tr><td>
<% request.setAttribute("QueryBean",obj);%>
<jsp:include page="../../home/page.jsp" />
</td></tr>
<!--List區============================================================-->
<tr><td class="bg">
<div id="listContainer">
<table class="table_form" width="100%" cellspacing="0" cellpadding="0">
	<thead id="listTHEAD">
	<tr>
		<th class="listTH" ><a class="text_link_w" >NO.</a></th>
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',1,false);" href="#">地上物次序</a></th>
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',2,false);" href="#">地上物權屬</a></th>
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',3,false);" href="#">地上物所有人</a></th>
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',4,false);" href="#">管理機關</a></th>
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',5,false);" href="#">地上物用途</a></th>
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',6,false);" href="#">使用本筆土地面積(㎡)</a></th>
	</thead>
	<tbody id="listTBODY">
	<%
	boolean primaryArray[] = {true,false,false,false,false,false};
	boolean displayArray[] = {true,true,true,true,true,true};
	out.write(util.View.getQuerylist(primaryArray,displayArray,objList,obj.getQueryAllFlag()));
	%>
	</tbody>
</table>
</div>
</td></tr>
</table>
</form>
</body>
</html>
