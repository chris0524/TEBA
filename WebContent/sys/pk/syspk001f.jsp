
<!--
程式目的：行政院函知案件管理
程式代號：syspk001f
程式日期：0950222
程式作者：clive.chang
--------------------------------------------------------
修改作者　　修改日期　　　修改目的
--------------------------------------------------------
-->

<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../home/head.jsp" %>
<jsp:useBean id="obj" scope="request" class="sys.pk.SYSPK001F">
	<jsp:setProperty name="obj" property="*"/>
</jsp:useBean>
<jsp:useBean id="objList" scope="page" class="java.util.ArrayList"/>

<%
if ("queryAll".equals(obj.getState())) {
	if ("false".equals(obj.getQueryAllFlag())){obj.setQueryAllFlag("true"); }
}else if ("queryOne".equals(obj.getState())) {
	obj = (sys.pk.SYSPK001F)obj.queryOne();	
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
	new Array("verify","N"),
	new Array("editID","<%=user.getUserID()%>"),
	new Array("editDate",getToday())
);

function checkField(){
	var alertStr="";
	if(form1.state.value=="queryAll"){
		alertStr += checkQuery();
	}else if(form1.state.value=="insert"||form1.state.value=="update"){
		alertStr += checkDate(form1.highestLevelDate,"行政院副本函日期");		
		alertStr += checkEmpty(form1.highestLevelDate,"行政院副本函日期");
		alertStr += checkEmpty(form1.highestLevelDoc,"行政院副本函文號");	
		alertStr += checkDate(form1.cityPublishDate,"市府發行函日期");
		alertStr += checkEmpty(form1.verify,"審核");
		if (form1.verify.value=="Y") {
			alertStr += checkEmpty(form1.cityPublishDate,"若市府發行函日期");
			alertStr += checkEmpty(form1.cityPublishDoc,"市府發行函文號");
		}
	}
	if(alertStr.length!=0){ alert(alertStr); return false; }
	beforeSubmit();
}
function queryOne(noticeNo){
	form1.noticeNo.value=noticeNo;
	form1.state.value="queryOne";
	beforeSubmit();
	form1.submit();
}

function checkURL(surl){
	columnTrim(form1.noticeNo);
	if (form1.state.value=="insert"||form1.state.value=="insertError"||form1.state.value=="update"||form1.state.value=="updateError"){	
		alert("新增或修改狀態無法更換頁標籤，請先點選取消!");
	}else if(form1.noticeNo.value==""){
		alert("請先執行查詢!");
	}else{			
		form1.state.value="queryAll";
		form1.action = surl;
		beforeSubmit();
		form1.submit();
	}
}

function init() {
	if (form1.verify.value=="Y") {
		setFormItem("update,delete","R");
	}
}
</script>
</head>

<body topmargin="5" onLoad="whatButtonFireEvent('<%=obj.getState()%>');init();showErrorMsg('<%=obj.getErrorMsg()%>');">
<form id="form1" name="form1" method="post" onSubmit="return checkField()">

<TABLE STYLE="width:100%;" CELLPADDING=0 CELLSPACING=0 valign="top">
	<tr>
		<td ID=t1 CLASS="tab_border1" HEIGHT="25">行政院函知案件管理</td>		
		<td ID=t2 CLASS="tab_border2"><a href="#" onClick="return checkURL('syspk002f.jsp');">行政院函知財產編號</a></td>
	</tr>
	<tr>
		<td class="tab_line1"></td>
		<td class="tab_line2"></td>
	</tr>
</TABLE>

<!--Query區============================================================-->
<div id="queryContainer" style="width:550px;height:220px;display:none">
	<iframe id="queryContainerFrame"></iframe>
	<div class="queryTitle">查詢視窗</div>
	<table class="queryTable"  border="1">
	<tr>
		<td class="queryTDLable">案號：</td>
		<td class="queryTDInput">
			起<input class="field_Q" type="text" name="q_noticeNo" value="<%=obj.getQ_noticeNo()%>" size="6" maxlength="6">&nbsp;~&nbsp;
			訖<input class="field_Q" type="text" name="q_noticeNoE" value="<%=obj.getQ_noticeNoE()%>" size="6" maxlength="6">
		</td>
	</tr>
	<tr>
		<td class="queryTDLable">行政院副本函日期：</td>
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
		<td class="queryTDLable">審核：</td>
		<td class="queryTDInput">
			<select name="q_verify" type="select" class="field_Q">
				<%=util.View.getTextOption("Y;己審核;N;未審核",obj.getQ_verify())%>
			</select>
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
		<td class="td_form"><font color="red">*</font>案號：</td>
		<td class="td_form_white">
			[<input type="text" class="field_PRO" name="noticeNo" value="<%=obj.getNoticeNo()%>" size="6">]
		</td>
	</tr>
	<tr>		
		<td class="td_form"><font color="red">*</font>行政院副本函日期文號：</td>
		<td class="td_form_white">
			<%=util.View.getPopCalndar("field_P","highestLevelDate",obj.getHighestLevelDate())%><input class="field_P" type="text" name="highestLevelDoc" size="20" maxlength="20" value="<%=obj.getHighestLevelDoc()%>">
		</td>
	</tr>
	<tr>	
		<td class="td_form">市府發行函日期文號：</td>
		<td class="td_form_white">
			<%=util.View.getPopCalndar("field","cityPublishDate",obj.getCityPublishDate())%><input class="field" type="text" name="cityPublishDoc" size="20" maxlength="20" value="<%=obj.getCityPublishDoc()%>">
		</td>
	</tr>
	<tr>	
		<td class="td_form"><font color="red">*</font>審核：</td>
		<td class="td_form_white">
			<select name="verify" type="select" class="field">
				<%=util.View.getTextOption("Y;己審核;N;未審核",obj.getVerify())%>
			</select>　(<font size="2" color="red">若選已審核，將會把此案件裡的財產編號更新至財產編號代碼檔</font>)
		</td>
	</tr>	
	<tr>		
		<td class="td_form">備註：</td>
		<td class="td_form_white">
			<input class="field" type="text" name="memo" size="50" maxlength="50" value="<%=obj.getMemo()%>">
		</td>
	</tr>
	<tr>
		<td class="td_form">異動人員/日期：</td>
		<td class="td_form_white">
			[<input class="field_RO" type="text" name="editID" size="10" value="<%=obj.getEditID()%>">/
			<input class="field_RO" type="text" name="editDate" size="7" value="<%=obj.getEditDate()%>">]
		</td>
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
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',1,false);" href="#">案號</a></th>
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',2,false);" href="#">行政院副本函日期</a></th>
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',3,false);" href="#">行政院副本函文號</a></th>		
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',4,false);" href="#">審核</a></th>		
	</thead>
	<tbody id="listTBODY">
	<%
	boolean primaryArray[] = {true,false,false,false};
	boolean displayArray[] = {true,true,true,true};
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
			setFormItem("verify","R");
			break;
	}
}
</script>
</body>
</html>

