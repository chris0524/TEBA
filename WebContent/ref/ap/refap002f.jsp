<!--
程式目的：質詢或建決議案資料
程式代號：refap002f
程式日期：0950522
程式作者：clive.chang
--------------------------------------------------------
修改作者　　修改日期　　　修改目的
--------------------------------------------------------
-->

<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../home/head.jsp" %>
<jsp:useBean id="obj" scope="request" class="ref.ap.REFAP002F">
	<jsp:setProperty name="obj" property="*"/>
</jsp:useBean>
<jsp:useBean id="objList" scope="page" class="java.util.ArrayList"/>

<%
if ("".equals(obj.getQ_orgID())) {
	obj.setQ_orgID(user.getOrganID());
	obj.setQ_orgIDName(user.getOrganName());
	obj.setQueryAllFlag("true");	
}

if ("queryAll".equals(obj.getState())) {
	if ("false".equals(obj.getQueryAllFlag())){obj.setQueryAllFlag("true"); }
}else if ("queryOne".equals(obj.getState())) {
	obj = (ref.ap.REFAP002F)obj.queryOne();	
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
	new Array("itemID","1"),
	new Array("expire","六"),
	new Array("counter","一"),
	new Array("meetKind","1"),
	new Array("kindID_main","02"),
	new Array("orgID","<%=user.getOrganID()%>"),
	new Array("orgIDName","<%=user.getOrganName()%>")	
);

function checkField(){
	var alertStr="";
	if(form1.state.value=="queryAll"){
		alertStr += checkQuery();
	}else if(form1.state.value=="insert"||form1.state.value=="insertError"||form1.state.value=="update"||form1.state.value=="updateError"){
		alertStr += checkEmpty(form1.itemID,"質詢暨建決議案");
		alertStr += checkEmpty(form1.expire,"屆");
		alertStr += checkEmpty(form1.counter,"次");
		alertStr += checkEmpty(form1.meetKind,"會議種類");
		alertStr += checkEmpty(form1.councilman,"議員名稱");
		alertStr += checkEmpty(form1.kindID_main,"主要功能分類");
		alertStr += checkEmpty(form1.caseSubject,"提案說明");
		alertStr += checkLen(form1.caseSubject,"提案說明",200);
		alertStr += checkEmpty(form1.caseDate,"提案日期");
		alertStr += checkDate(form1.caseDate,"提案日期");
		alertStr += checkEmpty(form1.orgID,"辦理機關");
		alertStr += checkLen(form1.handle1,"辦理情形1",500);
		alertStr += checkLen(form1.handle2,"辦理情形2",500);
		alertStr += checkLen(form1.handle3,"辦理情形3",500);
		alertStr += checkLen(form1.handle4,"辦理情形4",500);
		alertStr += checkLen(form1.handle5,"辦理情形5",500);
		alertStr += checkLen(form1.handle6,"辦理情形6",500);
		alertStr += checkLen(form1.handle7,"辦理情形7",500);
		alertStr += checkLen(form1.handle8,"辦理情形8",500);
		alertStr += checkLen(form1.handle9,"辦理情形9",500);
		alertStr += checkLen(form1.handle10,"辦理情形10",500);
		alertStr += checkDate(form1.handleDate,"辦理日期");
		alertStr += checkDate(form1.responseDate,"回覆日期");
		alertStr += checkDate(form1.assureDate,"允諾日期");
		alertStr += checkLen(form1.memo,"備註",50);
	}
	if(alertStr.length!=0){ alert(alertStr); return false; }
	beforeSubmit();
}

function queryOne(seqID){
	form1.seqID.value=seqID;
	form1.state.value="queryOne";
	beforeSubmit();
	form1.submit();
}

function openPrint(seqID){
	var obj;
	var props="";
	var count = 0;
	for(var i=0; i<document.forms[0].elements.length; i++){
		obj = document.forms[0].elements[i];
		if (obj.className=="field_PQ" || obj.className=="field_Q"){	
			if (count==0) props += obj.name + "=" + obj.value;
			else props += "&" + obj.name + "=" + obj.value;
			count++;
		}
	}
	window.open('refap003r.jsp?'+props);
	//,'MeetingView','toolbar=yes;location=no,directories=yes,menubar=yes,status=no,scrollbars=yes,resizable=yes,width=750,height=550');
}

function relativeList()
{
	window.open('refap002f_lookup.jsp','CouncilMemberLookup','scrollbars=0, status=no, toolbar=no,menubar=no,width=350,height=350');
}

</script>
</head>

<body topmargin="0" onLoad="whatButtonFireEvent('<%=obj.getState()%>');showErrorMsg('<%=obj.getErrorMsg()%>');">
<form id="form1" name="form1" method="post" onSubmit="return checkField()">

<!--Query區============================================================-->
<div id="queryContainer" style="width:750px;height:400px;display:none">
	<iframe id="queryContainerFrame"></iframe>
	<div class="queryTitle">查詢視窗</div>
	<% request.setAttribute("qBean",obj);%>
	<jsp:include page="refap002q.jsp" />
</div>

<table width="100%" cellspacing="0" cellpadding="0">
<!--Form區============================================================-->
<tr><td class="bg">
	<div id="formContainer">
	<table class="table_form" width="100%" height="100%">
	<tr>
	  <td class="td_form"><font color="red">*</font>質詢暨建決議案：</td>
	  <td colspan="3" class="td_form_white">
			<select class="field" type="select" name="itemID">				
				<%=util.View.getTextOption("1;總質詢;2;市政報告後質詢;3;部門質詢;4;議員提案;5;臨時提案",obj.getItemID())%>
			</select>
			<input class="field_P" type="hidden" name="seqID" value="<%=obj.getSeqID()%>"></td>
	</tr>
	<tr>
		<td class="td_form"><font color="red">*</font>會議屆次：</td>
		<td colspan="3" class="td_form_white">
			第
			  <select class="field" type="select" name="expire">
			  	<%=util.View.getTextOption("一;一;二;二;三;三;四;四;五;五;六;六;七;七;八;八;九;九;十;十;十一;十一;十二;十二",obj.getExpire())%>
			</select>
			  屆第
			  <select class="field" type="select" name="counter">
			  	<%=util.View.getTextOption("一;一;二;二;三;三;四;四;五;五;六;六;七;七;八;八;九;九;十;十;十一;十一;十二;十二;十三;十三;十四;十四",obj.getCounter())%>			  
			  </select>
			  次
			  <select class="field" type="select" name="meetKind">
			  	<%=util.View.getTextOption("1;定期會議;2;臨時會",obj.getMeetKind())%>
			  </select>　
			  　案號：
			  <input class="field" type="text" name="caseNo" size="12" maxlength="25" value="<%=obj.getCaseNo()%>"></td>
		</tr>
	<tr>
		<td class="td_form"><font color="red">*</font>議員名稱：</td>
		<td colspan="3" class="td_form_white">
			<input class="field" type="text" name="councilman" size="50" maxlength="100" value="<%=obj.getCouncilman()%>"> <input class="field" name="btnRelativeSelect" type="button" id="btnRelativeSelect" value="...." onClick="relativeList();"></td>
		</tr>
	<tr>
	  <td class="td_form"><font color="red">*</font>主要功能分類：</td>
	  <td colspan="3" class="td_form_white">
			主分類&nbsp;&nbsp;：
			  <select class="field" type="select" name="kindID_main">
			<%=util.View.getOption("select codeID, codeName from SYSCA_Code where codeKindID='REF' ", obj.getKindID_main())%>
			</select>　
			<br>
			次分類1：
			<select class="field" type="select" name="kindID_other1">
			<%=util.View.getOption("select codeID, codeName from SYSCA_Code where codeKindID='REF' ", obj.getKindID_other1())%>
			</select>　
			<br>
			次分類2：
			<select class="field" type="select" name="kindID_other2">
			<%=util.View.getOption("select codeID, codeName from SYSCA_Code where codeKindID='REF' ", obj.getKindID_other2())%>
			</select>		</td>
	</tr>
	<tr>
	  <td class="td_form"><font color="red">*</font>質詢或建決議案事項：<br>
<font color="#FF0000">(請輸入200字以內)　</font></td>
	  <td colspan="3" class="td_form_white">
			<textarea class="field" name="caseSubject" cols="50" rows="3"><%=obj.getCaseSubject()%></textarea>		</td>
	</tr>
	<tr>
	<td class="td_form"><font color="red">*</font>質詢或建決議案日期</td>
	<td class="td_form_white"><%=util.View.getPopCalndar("field","caseDate",obj.getCaseDate())%></td>
	<td class="td_form">關鍵字：</td>
	<td class="td_form_white">
			<input class="field" type="text" name="keyWord" size="20" maxlength="50" value="<%=obj.getKeyWord()%>">		</td>
	</tr>
	<tr>
	  <td class="td_form"><font color="red">*</font>辦理機關：</td>
	  <td class="td_form_white">
			<%=util.View.getPopOrgan("field","orgID",obj.getOrgID(),obj.getOrgIDName())%>		</td>
	  <td class="td_form">辦理日期：</td>
	  <td class="td_form_white">
			<%=util.View.getPopCalndar("field","handleDate",obj.getHandleDate())%>		</td>
	</tr>
	<tr>
	  <td class="td_form">辦理情形1：</td>
	  <td colspan="3" class="td_form_white">
			<textarea class="field" name="handle1" cols="60" rows="3"><%=obj.getHandle1()%></textarea>		</td>
		</tr>
	<tr>
	  <td class="td_form">辦理情形2：</td>
	  <td colspan="3" class="td_form_white">
			<textarea class="field" name="handle2" cols="60" rows="3"><%=obj.getHandle2()%></textarea>		</td>
	</tr>
	<tr>
	  <td class="td_form">辦理情形3：</td>
	  <td colspan="3" class="td_form_white">
			<textarea class="field" name="handle3" cols="60" rows="3"><%=obj.getHandle3()%></textarea>		</td>
	</tr>
	<tr>
	  <td class="td_form">辦理情形4：</td>
	  <td colspan="3" class="td_form_white"><textarea class="field" name="handle4" cols="60" rows="3"><%=obj.getHandle4()%></textarea></td>
	</tr>
	<tr>
	  <td class="td_form">辦理情形5：</td>
	  <td colspan="3" class="td_form_white">
			<textarea class="field" name="handle5" cols="60" rows="3"><%=obj.getHandle5()%></textarea>		</td>
	</tr>
	<tr>
	  <td class="td_form">辦理情形6：</td>
	  <td colspan="3" class="td_form_white">
			<textarea class="field" name="handle6" cols="60" rows="3"><%=obj.getHandle6()%></textarea>		</td>
	</tr>
	<tr>
	  <td class="td_form">辦理情形7：</td>
	  <td colspan="3" class="td_form_white">
			<textarea class="field" name="handle7" cols="60" rows="3"><%=obj.getHandle7()%></textarea>		</td>
	</tr>
	<tr>
	  <td class="td_form">辦理情形8：</td>
	  <td colspan="3" class="td_form_white">
			<textarea class="field" name="handle8" cols="60" rows="3"><%=obj.getHandle8()%></textarea>		</td>
	</tr>
	<tr>
	  <td class="td_form">辦理情形9：</td>
	  <td colspan="3" class="td_form_white">
			<textarea class="field" name="handle9" cols="60" rows="3"><%=obj.getHandle9()%></textarea>		</td>
		</tr>
	<tr>
	  <td class="td_form">辦理情形10：</td>
	  <td colspan="3" class="td_form_white">
			<textarea class="field" name="handle10" cols="60" rows="3"><%=obj.getHandle10()%></textarea>		</td>
		</tr>
	<tr>
	  <td class="td_form">回覆日期：</td>
	  <td class="td_form_white">
			<%=util.View.getPopCalndar("field","responseDate",obj.getResponseDate())%>		</td>
		<td class="td_form">回覆文號：</td>
		<td class="td_form_white">
			<input class="field" type="text" name="responseDoc" size="20" maxlength="20" value="<%=obj.getResponseDoc()%>">		</td>
	</tr>
	<tr>
	  <td class="td_form">是否允諾：</td>
	  <td class="td_form_white">
			<select class="field" type="select" name="isAssure">
			<%=util.View.getYNOption(obj.getIsAssure())%>
			</select>		</td>
		<td class="td_form">允諾日期：</td>
		<td class="td_form_white">
			<%=util.View.getPopCalndar("field","assureDate",obj.getAssureDate())%>		</td>
	</tr>
	<tr>
	  <td class="td_form">備註：</td>
	  <td colspan="3" class="td_form_white">
			<input class="field" type="text" name="memo" size="50" maxlength="50" value="<%=obj.getMemo()%>">		</td>
		</tr>
	<tr>
	  <td class="td_form">異動人員/日期：</td>
	  <td colspan="3" class="td_form_white"> [
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
	<input type="hidden" name="state" value="<%=obj.getState()%>">
	<input type="hidden" name="queryAllFlag" value="<%=obj.getQueryAllFlag()%>">
	<input type="hidden" name="userID" value="<%=user.getUserID()%>">
	<jsp:include page="../../home/toolbar.jsp" />
	<input class="toolbar_default" type="button" followPK="false" name="viewBtn1" value="全文預覽" onClick="openPrint();">&nbsp;|		
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
		<th class="listTH"><a class="text_link_w" onClick="return sortTable('listTBODY',1,false);" href="#">質詢暨建決議案</a></th>
		<th class="listTH"><a class="text_link_w" onClick="return sortTable('listTBODY',2,false);" href="#">會議屆次</a></th>
		<th class="listTH"><a class="text_link_w" onClick="return sortTable('listTBODY',3,false);" href="#">提案說明</a></th>
		<th class="listTH"><a class="text_link_w" onClick="return sortTable('listTBODY',4,false);" href="#">提案日期</a></th>
	</tr>
	</thead>
	<tbody id="listTBODY">
	<%
	boolean primaryArray[] = {false,true,false,false,false,false};
	boolean displayArray[] = {true,false,true,true,true,true};
	out.write(util.View.getPageQuerylist(primaryArray,displayArray,objList,obj.getQueryAllFlag()));
	%>
	</tbody>
</table>
</div>
</td></tr>
</table>
</form>
<script language="javascript">
localButtonFireListener.beforeWhatButtonFireEvent = function(buttonName){	
	switch (buttonName)	{		
		case "queryAll":
			if (parse(form1.q_orgID.value).length<=0) {
				form1.q_orgID.value="<%=user.getOrganID()%>";
				form1.q_orgIDName.value="<%=user.getOrganName()%>";
			}
			break;
	}
	return true;
}
</script>
</body>
</html>



