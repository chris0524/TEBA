<!--
程式目的：土地合併分割重測重劃作業－調整前後對應資料
程式代號：untla066f
程式日期：
程式作者：Yuan-Ren Jheng
--------------------------------------------------------
修改作者　　修改日期　　　修改目的
--------------------------------------------------------
-->

<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../home/head.jsp" %>
<jsp:useBean id="obj" scope="request" class="unt.la.UNTLA066F">
	<jsp:setProperty name="obj" property="*"/>
</jsp:useBean>
<jsp:useBean id="objList" scope="page" class="java.util.ArrayList"/>

<%
if ("queryOne".equals(obj.getState())) {
	obj = (unt.la.UNTLA066F)obj.queryOne();
	
}else if ("insert".equals(obj.getState())) {
	obj.insert();
	
}else if ("update".equals(obj.getState())) {
	obj.update();
	
}else if ("delete".equals(obj.getState())) {
	obj.delete();
	
}else if ("insertError".equals(obj.getState()) || "updateError".equals(obj.getState()) || "deleteError".equals(obj.getState())){
	
}

if ("queryAll".equals(obj.getState())) {
	if ("false".equals(obj.getQueryAllFlag())){obj.setQueryAllFlag("true"); }
}else if("init".equals(obj.getState())) {
	
}else{
	obj.setQueryAllFlag("true");	
}

if ("true".equals(obj.getQueryAllFlag())){
	objList = obj.queryAll();	
	obj = (unt.la.UNTLA066F)obj.queryOne();
	
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
);

function checkField(){	
	var alertStr="";
	if(form1.state.value=="queryAll"){
		alertStr += checkQuery();
	}else if(form1.state.value=="insert"||form1.state.value=="insertError"||form1.state.value=="update"||form1.state.value=="updateError"){

		
	}

	if(alertStr.length!=0){ alert(alertStr); return false; }

	
	
	beforeSubmit();
}

function queryOne(enterOrg,ownership,propertyNo,serialNo,mergeDivisionBatch){
	form1.enterOrg.value = enterOrg;
	form1.ownership.value = ownership;
	form1.propertyNo.value = propertyNo;
	form1.serialNo.value = serialNo;
	form1.mergeDivisionBatch.value = mergeDivisionBatch;

	form1.state.value="queryOne";
	beforeSubmit();
	form1.submit();
}

function init(){
	setDisplayItem("spanInsert,spanQueryAll,spanUpdate,spanDelete,spanClear,spanConfirm,spanNextInsert", "H");
		
}


function checkURL(surl){
	if (form1.state.value=="insert"||form1.state.value=="insertError"||form1.state.value=="update"||form1.state.value=="updateError"){
		alert("新增或修改狀態無法更換頁標籤，請先點選取消!");
	}else{

		
		if(surl=='untla054f.jsp'){			form1.caseNo.value = form1.caseNo_Merge.value;
		}else if(surl=='untla055f.jsp'){	form1.caseNo.value = form1.caseNo_Reduce.value;
		}else if(surl=='untla056f.jsp'){	form1.caseNo.value = form1.caseNo_Reduce.value;
											form1.propertyNo.value = form1.propertyNo_Reduce.value;
											form1.serialNo.value = form1.serialNo_Reduce.value;
		}else if(surl=='untla057f.jsp'){	form1.caseNo.value = form1.caseNo_Add.value;
		}else if(surl=='untla058f.jsp'){	form1.caseNo.value = form1.caseNo_Add.value;
											form1.propertyNo.value = form1.propertyNo_Add.value;
											form1.serialNo.value = form1.serialNo_Add.value;
		}

		
		form1.action=surl+"?initDtl=Y";

		form1.state.value="queryAll";
		
		beforeSubmit();
		form1.submit();
	}
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
		<td ID=t6 CLASS="tab_border2">增加單管理資料</td>
		<td ID=t7 CLASS="tab_border2">增加單地上物資料</td>
		<td ID=t8 CLASS="tab_border1" HEIGHT="25">調整前後對應資料</td>
	</tr>
	<tr>
		<td class="tab_line2"></td>
		<td class="tab_line2"></td>
		<td class="tab_line2"></td>
		<td class="tab_line2"></td>
		<td class="tab_line2"></td>	
		<td class="tab_line2"></td>	
		<td class="tab_line2"></td>	
		<td class="tab_line1"></td>					
	</tr>
</TABLE>
<!--隱藏欄位===========================================================-->
<input class="field_QRO" type="hidden" name="enterorgNotConfirm" value="<%=obj.getEnterorgNotConfirm()%>">
<input class="field_QRO" type="hidden" name="alreadyVerify" value="<%=obj.getAlreadyVerify()%>">

<input class="field_QRO" type="hidden" name="enterOrg" value="<%=obj.getEnterOrg()%>" >
<input class="field_QRO" type="hidden" name="ownership" value="<%=obj.getOwnership()%>" >
<input class="field_QRO" type="hidden" name="caseNo" value="<%=obj.getCaseNo()%>" >
<input class="field_QRO" type="hidden" name="differenceKind" value="<%=obj.getDifferenceKind()%>">
<input class="field_QRO" type="hidden" name="propertyNo" value="<%=obj.getPropertyNo()%>" >
<input class="field_QRO" type="hidden" name="serialNo" value="<%=obj.getSerialNo()%>" >

<input class="field_QRO" type="hidden" name="caseNo_Merge" value="<%=obj.getCaseNo_Merge()%>">
<input class="field_QRO" type="hidden" name="caseNo_Reduce" value="<%=obj.getCaseNo_Reduce()%>">
<input class="field_QRO" type="hidden" name="caseNo_Add" value="<%=obj.getCaseNo_Add()%>">

<input class="field_QRO" type="hidden" name="propertyNo_Reduce" value="<%=obj.getPropertyNo_Reduce()%>">
<input class="field_QRO" type="hidden" name="propertyNo_Add" value="<%=obj.getPropertyNo_Add()%>">
<input class="field_QRO" type="hidden" name="serialNo_Reduce" value="<%=obj.getSerialNo_Reduce()%>">
<input class="field_QRO" type="hidden" name="serialNo_Add" value="<%=obj.getSerialNo_Add()%>">


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
			<td class="td_form" width="10%">批次：</td>
			<td class="td_form_white" width="30%">
				[<input class="field_PRO" type="text" name="mergeDivisionBatch" size="5" maxlength="5" value="<%=obj.getMergeDivisionBatch()%>">]
			</td>
			<td class="td_form" width="10%">調整類別：</td>
			<td class="td_form_white" width="30%">
				<input class="field_PRO" type="hidden" name="adjustType" size="15" maxlength="15" value="<%=obj.getAdjustType()%>">
				[<input class="field_PRO" type="text" name="adjustTypeName" size="15" maxlength="15" value="<%=obj.getAdjustTypeName()%>">]
			</td>
		</tr>
		<tr>
		<td class="td_form">土地標示：</td>
		<td class="td_form_white" colspan="2">
			<input type="hidden" name="signNo" value="<%=obj.getSignNo()%>">
			<select class="field_P" type="select" name="signNo1" onChange="changeSignNo1('signNo1','signNo2','signNo3','');">
			<%=util.View.getOption("select signNo, signName from SYSCA_Sign where signNo like '_000000' order by seqno",obj.getSignNo1())%>
			</select>
			<select class="field_P" type="select" name="signNo2" onChange="changeSignNo2('signNo1','signNo2','signNo3','');">
				<script>changeSignNo1('signNo1','signNo2','signNo3','<%=obj.getSignNo2()%>');</script>
			</select>		
			<select class="field_P" type="select" name="signNo3">
				<script>changeSignNo2('signNo1','signNo2','signNo3','<%=obj.getSignNo3()%>');</script>
			</select>
		</td>
		<td class="td_form_white">
			　地號：
			<input class="field_P" type="text" name="signNo4" size="4" maxlength="4" value="<%=obj.getSignNo4()%>" onChange="getFrontZero(this.name, 4);">
			&nbsp;-&nbsp;
			<input class="field_P" type="text" name="signNo5" size="4" maxlength="4" value="<%=obj.getSignNo5()%>" onChange="getFrontZero(this.name, 4);">		
		</td>
	</tr>
		<tr>
			<td class="td_form" width="10%">權利範圍面積(㎡)：</td>
			<td class="td_form_white" colspan="3">
				[<input class="field_PRO" type="text" name="holdArea" size="15" maxlength="15" value="<%=obj.getHoldArea()%>">]
			</td>
		</tr>
	</table>
	</div>
</td></tr>
<!--Toolbar區============================================================-->
<tr><td class="bg" style="text-align:center">			
	<jsp:include page="../../home/toolbar.jsp" />
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
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',1,false);" href="#">財產編號</a></th>
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',2,false);" href="#">財產分號</a></th>
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',2,false);" href="#">調整類別</a></th>
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',3,false);" href="#">土地標示</a></th>
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',4,false);" href="#">權利範圍面積(㎡)</a></th>
	</thead>
	<tbody id="listTBODY">
	<%
	boolean primaryArray[] = {true,true,true,true,true,false,false,false,true};
	boolean displayArray[] = {false,false,false,true,true,false,true,true,true};
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
