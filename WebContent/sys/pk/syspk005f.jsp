<!--
程式目的：行政院函知財產編號
程式代號：syspk002f
程式日期：0950222
程式作者：clive.chang
--------------------------------------------------------
修改作者　　修改日期　　　修改目的
--------------------------------------------------------
-->

<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../home/head.jsp" %>
<jsp:useBean id="obj" scope="request" class="sys.pk.SYSPK005F">
	<jsp:setProperty name="obj" property="*"/>
</jsp:useBean>
<jsp:useBean id="objList" scope="page" class="java.util.ArrayList"/>

<%
if ("queryAll".equals(obj.getState())) {
	if ("false".equals(obj.getQueryAllFlag())){obj.setQueryAllFlag("true"); }
}else if ("queryOne".equals(obj.getState())) {
	obj = (sys.pk.SYSPK005F)obj.queryOne();	
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

insertDefault = new Array(
	new Array("editKind","N"),
	new Array("editID","<%=user.getUserID()%>"),
	new Array("editDate",getToday())
);

function checkField(){
	var alertStr="";	
	if(form1.state.value=="queryAll"){
		alertStr += checkQuery();
	}else if(form1.state.value=="insert"||form1.state.value=="insertError"||form1.state.value=="update"||form1.state.value=="updateError"){				
		if (form1.highestLevelIsAgree.value=="Y") {
			alertStr += checkEmpty(form1.propertyNo,"財產編號");
			alertStr += checkAlphaInt(form1.propertyNo,"財產編號",9);
			if (form1.propertyNo.value.length>3) {
				var preWordList = "12345";
				var preWord = form1.propertyNo.value.substring(0,3);
				var preWord1 = form1.propertyNo.value.substring(0,1);
				if (preWordList.indexOf(preWord1,0)==-1) {
					alertStr += "對不起，財產編號的第一碼必需是1,2,3,4,5開頭的字串!\n";
				}
				if (preWord>502) {
					alertStr += "對不起，財產編號的前三碼不能是>502的字串!\n";
				}
			}		
		}
		alertStr += checkEmpty(form1.propertyName,"財產名稱");
		alertStr += checkEmpty(form1.editKind,"異動種類");
		if (form1.limitYear.value!="") {
			alertStr += checkInt(form1.limitYear,"使用年限");
			if (!parseInt("0"+form1.limitYear.value)>0) alertStr+="使用年限必須輸入大於0的值，否則請留空白！\n";
		}		
	}
	if(alertStr.length!=0){ alert(alertStr); return false; }
	beforeSubmit();
}
function queryOne(applyNo,seqNo){
	form1.applyNo.value=applyNo;
	form1.seqNo.value=seqNo;
	form1.state.value="queryOne";
	beforeSubmit();
	form1.submit();
}

function checkURL(surl){
	if (form1.state.value=="insert"||form1.state.value=="insertError"||form1.state.value=="update"||form1.state.value=="updateError"){	
		alert("新增或修改狀態無法更換頁標籤，請先點選取消!");
	}else{
		form1.state.value="queryOne";
		form1.action = surl;
		beforeSubmit();
		form1.submit();
	}
}

function callLookup() {
	if (form1.propertyNo.value.length==10) getProperty('propertyNo','propertyName',form1.propertyNo.value.substring(0,1)+'&isLookup=Y','N');
}

function init() {
	setFormItem("queryAll","R");
	document.all.item("spanQueryAll").style.display="none";	
	if (form1.isClose.value=="Y") {
		setFormItem("insert,update,delete","R");
	}
}
</script>
</head>

<body topmargin="0" onLoad="whatButtonFireEvent('<%=obj.getState()%>');init();showErrorMsg('<%=obj.getErrorMsg()%>');">
<form id="form1" name="form1" method="post" onSubmit="return checkField()">

<TABLE STYLE="width:100%;" CELLPADDING=0 CELLSPACING=0 valign="top">
	<tr>
		<td ID=t1 CLASS="tab_border2" HEIGHT="25"><a href="#" onClick="return checkURL('syspk004f.jsp');">管理機關申請案件管理</a></td>		
		<td ID=t2 CLASS="tab_border1">管理機關申請案財產編號</td>
	</tr>
	<tr>
		<td class="tab_line2"></td>
		<td class="tab_line1"></td>
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
		<td class="td_form">案號：</td>
		<td class="td_form_white">
			[<input class="field_QRO" type="text" name="applyNo" size="6" maxlength="6" value="<%=obj.getApplyNo()%>">]　流水號：[<input class="field_PRO" type="text" name="seqNo" size="6" maxlength="6" value="<%=obj.getSeqNo()%>">]</td>
			
		<td class="td_form"><font color="red">*</font>異動種類：</td>
		<td class="td_form_white">
			<select class="field" type="select" name="editKind">
				<%=util.View.getTextOption("N;新增;U;修改;D;刪除",obj.getEditKind())%>
			</select>		</td>
	</tr>
	<tr>
	  <td class="td_form"><font color="red">*</font>財產編號：</td>
	  <td class="td_form_white">
			<input class="field" type="text" name="propertyNo" size="10" maxlength="10" value="<%=obj.getPropertyNo()%>" onBlur="callLookup();" onChange="callLookup();">		</td>
	  <td class="td_form"><font color="red">*</font>財產名稱：</td>
	  <td class="td_form_white">
			<input class="field" type="text" name="propertyName" size="20" maxlength="50" value="<%=obj.getPropertyName()%>">		</td>
	</tr>
	<tr>
	  <td class="td_form">單位：</td>
	  <td colspan="3" class="td_form_white">
			<input class="field" type="text" name="propertyUnit" size="12" maxlength="25" value="<%=obj.getPropertyUnit()%>">		
			 　材質：
			 <input class="field" type="text" name="material" size="25" maxlength="25" value="<%=obj.getMaterial()%>">
			 　使用年限：
			 <input class="field" type="text" name="limitYear" size="3" maxlength="3" value="<%=obj.getLimitYear()%>">		</td>
		</tr>
	<tr>
	  <td class="td_form">備註：</td>
	  <td class="td_form_white">
			<textarea name="memo" cols="25" rows="4" class="field"><%=obj.getMemo()%></textarea>		</td>
	  <td class="td_form">異動人員/日期：</td>
	  <td class="td_form_white"> [
	    <input class="field_RO" type="text" name="editID" size="10" value="<%=obj.getEditID()%>">
	    /
	    <input class="field_RO" type="text" name="editDate" size="7" value="<%=obj.getEditDate()%>">
	    ] </td>
	</tr>
	</table>
	</div>
</td></tr>

<!--Toolbar區============================================================-->
<tr><td class="bg" style="text-align:center">
	<input type="hidden" name="highestLevelIsAgree" value="<%=obj.getHighestLevelIsAgree()%>">
	<input type="hidden" name="isClose" value="<%=obj.getIsClose()%>">
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
		<th class="listTH"><a class="text_link_w" onClick="return sortTable('listTBODY',1,false);" href="#">財產編號</a></th>
		<th class="listTH"><a class="text_link_w" onClick="return sortTable('listTBODY',2,false);" href="#">財產名稱</a></th>
		<th class="listTH"><a class="text_link_w" onClick="return sortTable('listTBODY',3,false);" href="#">異動種類</a></th>
		<th class="listTH"><a class="text_link_w" onClick="return sortTable('listTBODY',4,false);" href="#">單位</a></th>
		<th class="listTH"><a class="text_link_w" onClick="return sortTable('listTBODY',5,false);" href="#">使用年限</a></th>
	</tr>
	</thead>
	<tbody id="listTBODY">
	<%
	boolean primaryArray[] = {true,true,false,false,false,false,false};
	boolean displayArray[] = {false,false,true,true,true,true,true};
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



