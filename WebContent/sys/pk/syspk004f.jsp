<!--
程式目的：管理機關申請案件管理
程式代號：syspk004f
程式日期：0950227
程式作者：clive.chang
--------------------------------------------------------
修改作者　　修改日期　　　修改目的
--------------------------------------------------------
-->

<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../home/head.jsp" %>
<jsp:useBean id="obj" scope="request" class="sys.pk.SYSPK004F">
	<jsp:setProperty name="obj" property="*"/>
</jsp:useBean>
<jsp:useBean id="objList" scope="page" class="java.util.ArrayList"/>

<%
if ("queryAll".equals(obj.getState())) {
	if ("false".equals(obj.getQueryAllFlag())){obj.setQueryAllFlag("true"); }
}else if ("queryOne".equals(obj.getState())) {
	obj = (sys.pk.SYSPK004F)obj.queryOne();	
}else if ("insert".equals(obj.getState()) || "insertError".equals(obj.getState())) {
	obj.insert();
}else if ("update".equals(obj.getState()) || "updateError".equals(obj.getState())) {
	obj.update();
}else if ("delete".equals(obj.getState()) || "deleteError".equals(obj.getState())) {
	obj.delete();
}
if ("true".equals(obj.getQueryAllFlag())){
	objList = obj.queryAll();
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
<script language="javascript" src="../../js/validate.js"></script>
<script language="javascript" src="../../js/function.js"></script>
<script language="javascript" src="../../js/tablesoft.js"></script>
<script language="javascript">
var insertDefault;	//二維陣列, 新增時, 設定預設值
function checkField(){
	var alertStr="";
	if(form1.state.value=="queryAll"){
		alertStr += checkQuery();
	}else if(form1.state.value=="insert"||form1.state.value=="insertError"||form1.state.value=="update"||form1.state.value=="updateError"){
		alertStr += checkEmpty(form1.applyOrgNo,"申請機關");
		alertStr += checkEmpty(form1.applyDoc,"申請文號");
		alertStr += checkEmpty(form1.applyDate,"申請日期");
		alertStr += checkDate(form1.applyDate,"申請日期");
		alertStr += checkDate(form1.cityDate,"市府發文日期");
		if (form1.cityIsAgree.value=="N") alertStr += checkEmpty(form1.cityDisallow,"市府駁回原因");
		
		alertStr += checkDate(form1.highestLevelDate,"行政院回文日期");
		alertStr += checkDate(form1.cityPublishDate,"市府發行函日期");
		alertStr += checkDate(form1.cityResponseDate,"市府函復申請機關日期");
		if (form1.highestLevelIsAgree.value=="Y") {
			alertStr += checkEmpty(form1.highestLevelDate,"行政院回文日期");
			alertStr += checkEmpty(form1.highestLevelDoc,"行政院回文文號");			
			if (form1.isClose.value=="Y") {
				alertStr += checkEmpty(form1.cityPublishDate,"市府發行函日期");
				alertStr += checkEmpty(form1.cityPublishDoc,"市府發行函文號");
				alertStr += checkEmpty(form1.cityResponseDate,"市府函復申請機關日期");
				alertStr += checkEmpty(form1.cityResponseDoc,"市府函復申請機關文號");
			}
		} else if (form1.highestLevelIsAgree.value=="N") {
			alertStr += checkEmpty(form1.highestLevelDisallow,"行政院駁回原因");
		}		
		alertStr += checkLen(form1.memo,"備註",250);
	}
	if(alertStr.length!=0){ alert(alertStr); return false; }
	beforeSubmit();
}
function queryOne(applyNo){
	form1.applyNo.value=applyNo;
	form1.state.value="queryOne";
	beforeSubmit();
	form1.submit();
}

function getDisallow() {
	if (form1.cityIsAgree.value=="N") setFormItem("cityDisallow","O");
	else {
		form1.cityDisallow.value=="";
		setFormItem("cityDisallow","R");		
	}
	if (form1.highestLevelIsAgree.value=="N") setFormItem("highestLevelDisallow","O");
	else {
		form1.highestLevelDisallow.value="";
		setFormItem("highestLevelDisallow","R");
	}
}

function checkURL(surl){
	columnTrim(form1.applyNo);
	if (form1.state.value=="insert"||form1.state.value=="insertError"||form1.state.value=="update"||form1.state.value=="updateError"){	
		alert("新增或修改狀態無法更換頁標籤，請先點選取消!");
	}else if(form1.applyNo.value==""){
		alert("請先執行查詢!");
	}else{			
		form1.state.value="queryAll";
		form1.action = surl;
		beforeSubmit();
		form1.submit();
	}
}

function init() {
	if (form1.isClose.value=="Y") {
		setFormItem("update,delete","R");
	}
}
</script>

</head>

<body topmargin="0" onLoad="whatButtonFireEvent('<%=obj.getState()%>');showErrorMsg('<%=obj.getErrorMsg()%>');">
<form id="form1" name="form1" method="post" onSubmit="return checkField()">

<TABLE STYLE="width:100%;" CELLPADDING=0 CELLSPACING=0 valign="top">
	<tr>
		<td ID=t1 CLASS="tab_border1" HEIGHT="25">管理機關申請案件管理</td>		
		<td ID=t2 CLASS="tab_border2"><a href="#" onClick="return checkURL('syspk005f.jsp');">管理機關申請案財產編號</a></td>
	</tr>
	<tr>
		<td class="tab_line1"></td>
		<td class="tab_line2"></td>
	</tr>
</TABLE>

<!--Query區============================================================-->
<div id="queryContainer" style="width:500px;height:360px;display:none">
	<iframe id="queryContainerFrame"></iframe>
	<div class="queryTitle">查詢視窗</div>
	<table class="queryTable"  border="1">
	<tr>
		<td class="queryTDLable">申請機關：</td>
		<td class="queryTDInput">
			<%=util.View.getPopOrgan("field_Q","q_applyOrgNo",obj.getQ_applyOrgNo(),obj.getQ_applyOrgNoName(),"Y")%>
		</td>
	</tr>
	<tr>
		<td class="queryTDLable">申請日期：</td>
		<td class="queryTDInput">
			起<%=util.View.getPopCalndar("field_Q","q_applyDate",obj.getQ_applyDate())%>&nbsp;~&nbsp;
			訖<%=util.View.getPopCalndar("field_Q","q_applyDateE",obj.getQ_applyDateE())%>			
		</td>
	</tr>
	<tr>
		<td class="queryTDLable">市府同意/駁回：</td>
		<td class="queryTDInput">
			<select class="field_Q" type="select" name="q_cityIsAgree">
				<%=util.View.getTextOption("Y;同意;N;駁回", obj.getQ_cityIsAgree())%>
			</select>
		</td>
	</tr>
	<tr>
		<td class="queryTDLable">市府發文日期：</td>
		<td class="queryTDInput">
			起<%=util.View.getPopCalndar("field_Q","q_cityDate",obj.getQ_cityDate())%>&nbsp;~&nbsp;
			訖<%=util.View.getPopCalndar("field_Q","q_cityDateE",obj.getQ_cityDateE())%>
		</td>
	</tr>
	<tr>
		<td class="queryTDLable">行政院同意╱駁回：</td>
		<td class="queryTDInput">
			<select class="field_Q" type="select" name="q_highestLevelIsAgree">
				<%=util.View.getTextOption("Y;同意;N;駁回", obj.getQ_highestLevelIsAgree())%>			
			</select>
		</td>
	</tr>
	<tr>
		<td class="queryTDLable">行政院回文日期：</td>
		<td class="queryTDInput">
			起<%=util.View.getPopCalndar("field_Q","q_highestLevelDate",obj.getQ_highestLevelDate())%>&nbsp;~&nbsp;
			訖<%=util.View.getPopCalndar("field_Q","q_highestLevelDateE",obj.getQ_highestLevelDateE())%>
		</td>
	</tr>
	<tr>
		<td class="queryTDLable">市府發行函日期：</td>
		<td class="queryTDInput">
			起<%=util.View.getPopCalndar("field_Q","q_cityPublishDate",obj.getQ_cityPublishDate())%>&nbsp;~&nbsp;
			訖<%=util.View.getPopCalndar("field_Q","q_cityPublishDateE",obj.getQ_cityPublishDateE())%>
		</td>
	</tr>
	<tr>
		<td class="queryTDLable">市府函復申請機關日期：</td>
		<td class="queryTDInput">
			起<%=util.View.getPopCalndar("field_Q","q_cityResponseDate",obj.getQ_cityResponseDate())%>&nbsp;~&nbsp;
			訖<%=util.View.getPopCalndar("field_Q","q_cityResponseDateE",obj.getQ_cityResponseDateE())%>
		</td>
	</tr>
	<tr>
		<td class="queryTDInput" colspan="2" style="text-align:center;">
			<input class="toolbar_default" followPK="false" type="submit" name="querySubmit" value="確　　定" >
			<input class="toolbar_default" followPK="false" type="button" name="queryCannel" value="取　　消" onClick="whatButtonFireEvent(this.name)">
		</td>
	</tr>
	</table>
</div>

<table width="100%" cellspacing="0" cellpadding="0">
<!--Form區============================================================-->
<tr><td class="bg">
	<div id="formContainer">
	<table class="table_form" width="100%" height="100%">
	<tr>
		<td class="td_form">流水號：</td>
		<td class="td_form_white">
			[<input class="field_PRO" type="text" name="applyNo" size="10" maxlength="10" value="<%=obj.getApplyNo()%>">]		</td>
		</tr>
	<tr>
	  <td class="td_form"><font color="red">*</font>申請機關：</td>
	  <td class="td_form_white">
			<%=util.View.getPopOrgan("field","applyOrgNo",obj.getApplyOrgNo(),obj.getApplyOrgNoName(),"Y")%>　</td>
	  </tr>
	<tr>
	<td class="td_form"><font color="red">*</font>申請文號：</td>
	<td class="td_form_white"><%=util.View.getPopCalndar("field","applyDate",obj.getApplyDate())%>
			<input class="field" type="text" name="applyDoc" size="20" maxlength="20" value="<%=obj.getApplyDoc()%>"></td>
	</tr>
	<tr>
	  <td class="td_form">市府同意/駁回：</td>
	  <td class="td_form_white">
			<select class="field" type="select" name="cityIsAgree" onChange="getDisallow();">
				<%=util.View.getTextOption("Y;同意;N;駁回", obj.getCityIsAgree())%>				
			</select>
				　
			駁回原因：
			<input class="field" type="text" name="cityDisallow" size="30" maxlength="50" value="<%=obj.getCityDisallow()%>"></td>
		</tr>
	<tr>
	  <td class="td_form">市府發文文號：</td>
	  <td class="td_form_white">
			<%=util.View.getPopCalndar("field","cityDate",obj.getCityDate())%> <input class="field" type="text" name="cityDoc" size="20" maxlength="20" value="<%=obj.getCityDoc()%>">		</td>
		</tr>
	<tr>
	  <td class="td_form">行政院同意/駁回：</td>
	  <td class="td_form_white">
			<select class="field" type="select" name="highestLevelIsAgree" onChange="getDisallow();">
				<%=util.View.getTextOption("Y;同意;N;駁回", obj.getHighestLevelIsAgree())%>
			</select>
				　
			駁回原因：
			<input class="field" type="text" name="highestLevelDisallow" size="30" maxlength="50" value="<%=obj.getHighestLevelDisallow()%>">		</td>
		</tr>
	<tr>
	  <td class="td_form">行政院回文文號：</td>
	  <td class="td_form_white">
			<%=util.View.getPopCalndar("field","highestLevelDate",obj.getHighestLevelDate())%> <input class="field" type="text" name="highestLevelDoc" size="20" maxlength="20" value="<%=obj.getHighestLevelDoc()%>">		</td>
		</tr>
	<tr>
	  <td class="td_form">市府發行函文號：</td>
	  <td class="td_form_white">
			<%=util.View.getPopCalndar("field","cityPublishDate",obj.getCityPublishDate())%>
			<input class="field" type="text" name="cityPublishDoc" size="20" maxlength="20" value="<%=obj.getCityPublishDoc()%>">		</td>
		</tr>
	<tr>
	  <td class="td_form">市府函復申請機關文號：</td>
	  <td class="td_form_white">
			<%=util.View.getPopCalndar("field","cityResponseDate",obj.getCityResponseDate())%>
			<input class="field" type="text" name="cityResponseDoc" size="20" maxlength="20" value="<%=obj.getCityResponseDoc()%>">		</td>
		</tr>
	<tr>
	  <td class="td_form">是否結案：</td>
	  <td class="td_form_white">
			<select class="field" type="select" name="isClose">
			<%=util.View.getYNOption(obj.getIsClose())%>
			</select>		</td>
		</tr>
	<tr>
	  <td class="td_form">備註：</td>
	  <td class="td_form_white">
			<textarea class="field" name="memo" cols="24" rows="4"><%=obj.getMemo()%></textarea>		</td>
		</tr>
	<tr>
	  <td class="td_form">異動人員/日期：</td>
	  <td class="td_form_white">[
	    <input class="field_RO" type="text" name="editID" size="10" value="<%=obj.getEditID()%>">
	    /
	    <input class="field_RO" type="text" name="editDate" size="7" value="<%=obj.getEditDate()%>">
	    ]</td>
	</tr>
	</table>
	</div>
</td></tr>

<!--Toolbar區============================================================-->
<tr><td class="bg" style="text-align:center">
	<input type="hidden" name="state" value="<%=obj.getState()%>">
	<input type="hidden" name="queryAllFlag" value="<%=obj.getQueryAllFlag()%>">
	<input type="hidden" name="userID" value="<%=user.getUserID()%>">
	<jsp:include page="../../home/toolbar.jsp" />
</td></tr>

<!--List區============================================================-->
<tr><td class="bg">
<div id="listContainer">
<table class="table_form" width="100%" cellspacing="0" cellpadding="0">
	<thead id="listTHEAD">
	<tr>
		<th class="listTH" ><a class="text_link_w" >NO.</a></th>
		<th class="listTH"><a class="text_link_w" onClick="return sortTable('listTBODY',1,false);" href="#">申請機關</a></th>
		<th class="listTH"><a class="text_link_w" onClick="return sortTable('listTBODY',2,false);" href="#">申請日期</a></th>
		<th class="listTH"><a class="text_link_w" onClick="return sortTable('listTBODY',3,false);" href="#">市府同意/駁回</a></th>
		<th class="listTH"><a class="text_link_w" onClick="return sortTable('listTBODY',4,false);" href="#">行政院同意╱駁回</a></th>
		<th class="listTH"><a class="text_link_w" onClick="return sortTable('listTBODY',5,false);" href="#">行政院回文日期</a></th>
		<th class="listTH"><a class="text_link_w" onClick="return sortTable('listTBODY',6,false);" href="#">行政院回文文號</a></th>
	</tr>
	</thead>
	<tbody id="listTBODY">
	<%
	boolean primaryArray[] = {true,false,false,false,false,false,false};
	boolean displayArray[] = {false,true,true,true,true,true,true};
	out.write(util.View.getQuerylist(primaryArray,displayArray,objList,obj.getQueryAllFlag()));
	%>
	</tbody>
</table>
</div>
</td></tr>
</table>
</form>
<script language="javascript">
localButtonFireListener.whatButtonFireEvent = function(buttonName){
	switch (buttonName)	{
		case "insert":
		case "insertError":
			getDisallow();
			setFormItem("isClose","R");
			break;
			
		case "update":
		case "updateError":
			getDisallow();
			break;
	}
	
}
</script>
</body>
</html>



