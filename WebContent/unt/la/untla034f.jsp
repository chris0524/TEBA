<!--
程式目的：土地合併分割作業－分割減損單
程式代號：untla034f
程式日期：0940920
程式作者：carey
--------------------------------------------------------
修改作者　　修改日期　　　修改目的
--------------------------------------------------------
-->

<%@ page contentType="text/html;charset=UTF-8" import="util.*"%>
<%@ include file="../../home/head.jsp" %>
<jsp:useBean id="obj" scope="request" class="unt.la.UNTLA034F">
	<jsp:setProperty name="obj" property="*"/>
</jsp:useBean>
<jsp:useBean id="objList" scope="page" class="java.util.ArrayList"/>

<%
if ("queryAll".equals(obj.getState())) {
	if ("false".equals(obj.getQueryAllFlag())){obj.setQueryAllFlag("true"); }
}else if ("queryOne".equals(obj.getState())) {
	obj = (unt.la.UNTLA034F)obj.queryOne();	
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
<script language="javascript" src="untla027q.js"></script>
<script language="javascript">
var insertDefault;	//二維陣列, 新增時, 設定預設值
insertDefault = new Array(
	new Array("writeDate","<%=util.Datetime.getYYYMMDD()%>"),
	new Array("writeUnit","<%=user.getOrganName()%>"),
	new Array("proofDoc","<%=new unt.ba.UNTBA002F().getDefaultProofDoc(user.getOrganID(),"A2")%>"),
	new Array("reduceDate","<%=obj.getMergeDivisionDate()%>"),
	new Array("approveOrg","<%=Common.checkGet(request.getParameter("approveOrg"))%>"),
	new Array("approveDate","<%=Common.checkGet(request.getParameter("approveDate"))%>"),
	new Array("approveDoc","<%=Common.checkGet(request.getParameter("approveDoc"))%>"),
	new Array("closing","N")
);
function checkField(){
	var alertStr="";
	if(form1.state.value=="queryAll"){
		alertStr += checkQuery();
	}else if(form1.state.value=="insert"||form1.state.value=="insertError"||form1.state.value=="update"||form1.state.value=="updateError"){
		alertStr += checkEmpty(form1.enterOrg,"入帳機關");
		alertStr += checkEmpty(form1.ownership,"權屬");
		alertStr += checkDate(form1.writeDate,"填單日期");
		alertStr += checkEmpty(form1.proofDoc,"減損單編號－字");
		alertStr += checkEmpty(form1.reduceDate,"減損日期");
		alertStr += checkDate(form1.reduceDate,"減損日期");
		alertStr += checkDate(form1.approveDate,"核准日期");
		alertStr += checkEmpty(form1.verify,"入帳");
		alertStr += checkLen(form1.notes,"備註",250);
	}
	if(alertStr.length!=0){ alert(alertStr); return false; }
	beforeSubmit();
}
function queryOne(enterOrg,ownership,caseNo){
	form1.enterOrg.value=enterOrg;
	form1.ownership.value=ownership;
	form1.caseNo.value=caseNo;
	form1.state.value="queryOne";
	beforeSubmit();
	form1.submit();
}

function gountla01617r(){
	var url = "untla017p.jsp?organID="+form1.organID.value+"&q_enterOrg=" + form1.enterOrg.value + "&q_ownership="+ form1.ownership.value +
		"&q_caseNoS=" + form1.reduceCaseNo1.value + "&q_caseNoE="+form1.reduceCaseNo1.value +
		"&q_kind=4";
	
	window.open(url);
}

function init(){
	document.all.item("spanQueryAll").style.display="none";
	if(parse(form1.reduceCaseNo1.value).length<=0){
		setFormItem("print2,update,delete", "R");
		setFormItem("insert","O");
	}else{
		setFormItem("print2,update,delete", "O");
		setFormItem("insert","R");
	}
	if ("<%=obj.getMergeDivisionVerify()%>"=="Y") {
		setFormItem("insert,update,delete", "R");
	}	
	if (form1.enterOrg.value!="<%=user.getOrganID()%>") {		
		setFormItem("insert,update,delete,print2","R");
	}	
}

</script>
</head>

<body topmargin="5" onLoad="whatButtonFireEvent('<%=obj.getState()%>');init();showErrorMsg('<%=obj.getErrorMsg()%>');">
<form id="form1" name="form1" method="post" onSubmit="return checkField()">

<!--標籤區============================================================-->
<TABLE STYLE="width:100%;" CELLPADDING=0 CELLSPACING=0 valign="top">
	<tr>
		<td ID=t1 CLASS="tab_border2" HEIGHT="25"><a href="#" onClick="return checkURL('untla027f.jsp');">案件資料</a></td>
<!-- 	<td ID=t2 CLASS="tab_border2"><a href="#" onClick="return checkURL('untla028f.jsp');">合併<br>減損單</a></td>		
		<td ID=t3 CLASS="tab_border2"><a href="#" onClick="return checkURL('untla029f.jsp');">合併<br>減損單明細</a></td>
		<td ID=t4 CLASS="tab_border2"><a href="#" onClick="return checkURL('untla030f.jsp');">合併<br>增加單</a></td>
		<td ID=t5 CLASS="tab_border2"><a href="#" onClick="return checkURL('untla031f.jsp');">合併<br>增加單明細</a></td>
		<td ID=t6 CLASS="tab_border2">合併增加單<br>管理資料</td>
 -->		
		<td ID=t7 CLASS="tab_border1">分割<br>減損單</td>
		<td ID=t8 CLASS="tab_border2"><a href="#" onClick="return checkURL('untla035f.jsp');">分割<br>減損單明細</a></td>
		<td ID=t9 CLASS="tab_border2"><a href="#" onClick="return checkURL('untla036f.jsp');">分割<br>增加單</a></td>
		<td ID=t10 CLASS="tab_border2"><a href="#" onClick="return checkURL('untla037f.jsp');">分割<br>增加單明細</a></td>
		<td ID=t11 CLASS="tab_border2">分割增加單<br>管理資料</td>
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
		<td class="tab_line2"></td>	
		<td class="tab_line2"></td>	
		<td class="tab_line2"></td>		
	</tr>
</TABLE>
<!--隱藏欄位(頁籤切換時需保留的資訊)=====================================-->
<input class="field_QRO" type="hidden" name="caseNo" value="<%=obj.getCaseNo()%>">
<input class="field_QRO" type="hidden" name="mergeReduce" size="10" maxlength="10" value="<%=obj.getMergeReduce()%>">
<input class="field_QRO" type="hidden" name="mergeAdd" size="10" maxlength="10" value="<%=obj.getMergeAdd()%>">
<input class="field_QRO" type="hidden" name="divisionReduce" size="10" maxlength="10" value="<%=obj.getDivisionReduce()%>">
<input class="field_QRO" type="hidden" name="divisionAdd" size="10" maxlength="10" value="<%=obj.getDivisionAdd()%>">
<input class="field_QRO" type="hidden" name="mergeDivisionDate" size="7" maxlength="7" value="<%=obj.getMergeDivisionDate()%>">
<input class="field_QRO" type="hidden" name="cause" value="<%=obj.getCause()%>">
<input class="field_QRO" type="hidden" name="cause1" value="<%=obj.getCause1()%>">
<input class="field_QRO" type="hidden" name="mergeDivisionVerify" value="<%=obj.getMergeDivisionVerify()%>">
<input class="field_QRO" type="hidden" name="addCaseNo" value="<%=obj.getAddCaseNo()%>">
<input class="field_QRO" type="hidden" name="reduceCaseNo" value="<%=obj.getReduceCaseNo()%>">
<input class="field_QRO" type="hidden" name="addCaseNo1" value="<%=obj.getAddCaseNo1()%>">
<!--Query區============================================================-->
<div id="queryContainer" style="width:400px;height:400px;display:none">
	<iframe id="queryContainerFrame"></iframe>
	<div class="queryTitle">查詢視窗</div>	
	<% request.setAttribute("UNTLA027Q",obj);%>
	<jsp:include page="untla027q.jsp" />
</div>
<table width="100%" cellspacing="0" cellpadding="0">
<!--Form區============================================================-->
<tr><td class="bg">
	<div id="formContainer">
	<table class="table_form" width="100%" height="100%">
	<tr>
		<td class="td_form">分割案號：</td>
		<td class="td_form_white" colspan="3">
			[<input class="field_QRO" type="text" name="mergeDivision" size="10" maxlength="10" value="<%=obj.getMergeDivision()%>">]
		</td>
	</tr>
	<tr>
		<td class="td_form">入帳機關：</td>
		<td class="td_form_white" colspan="3">
			<input class="field_QRO" type="hidden" name="enterOrg" size="10" maxlength="10" value="<%=obj.getEnterOrg()%>" >
			[<input class="field_QRO" type="text" name="enterOrgName" size="20" maxlength="50" value="<%=obj.getEnterOrgName()%>">]
			&nbsp;　　權屬：
			<select class="field_QRO" type="select" name="ownership">
				<%=util.View.getOnwerOption(obj.getOwnership())%>
			</select>
			&nbsp;　　填單日期：<%=util.View.getPopCalndar("field","writeDate",obj.getWriteDate())%>
		</td>
	</tr>
	<tr>
		<td class="td_form">電腦單號：</td>
		<td class="td_form_white" colspan="3">
			[<input class="field_PRO" type="text" name="reduceCaseNo1" size="10" maxlength="10" value="<%=obj.getReduceCaseNo1()%>">]
			&nbsp;　　　　　減損案名：[<input class="field_QRO" type="text" name="caseName" size="25" maxlength="25" value="<%=obj.getCaseName()%>">]
		</td>
	</tr>
	<tr>
		<td class="td_form">填造單位：</td>
		<td class="td_form_white" colspan="3">
			<input class="field" type="text" name="writeUnit" size="30" maxlength="15" value="<%=obj.getWriteUnit()%>">
			&nbsp;&nbsp;&nbsp;　　財產管理單位編號：<input class="field" type="text" name="manageNo" size="10" maxlength="10" value="<%=obj.getManageNo()%>">
		</td>
	</tr>
	<tr>
		<td class="td_form"><font color="red">*</font>減損單編號：</td>
		<td class="td_form_white" colspan="3">
			<input class="field" type="text" name="proofDoc" size="20" maxlength="10" value="<%=obj.getProofDoc()%>">字第
			[<input class="field_RO" type="text" name="proofNo" size="10" maxlength="20" value="<%=obj.getProofNo()%>">]號
			&nbsp;　　傳票號數：<input class="field" type="text" name="summonsNo" size="15" maxlength="15" value="<%=obj.getSummonsNo()%>">
		</td>
	</tr>
	<tr>
		<td class="td_form">減損日期：</td>
		<td class="td_form_white" colspan="3">
			[<input class="field_QRO" type="text" name="reduceDate" size="7" maxlength="7" value="<%=obj.getReduceDate()%>">]
			&nbsp;&nbsp;&nbsp;　　　　　入帳：
			<select class="field_QRO" type="select" name="verify">
				<%=util.View.getYNOption(obj.getVerify())%>
			</select>
			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;月結：
          	<select class="field_RO" type="select" name="closing">
            	<%=util.View.getYNOption(obj.getClosing())%>
          	</select>
		</td>
	</tr>
	<tr>
		<td class="td_form">核准機關：</td>
		<td class="td_form_white" colspan="3">
			<select class="field_QRO" type="select" name="approveOrg">
			<%=util.View.getOption("select codeID, codeName from SYSCA_Code where codeKindID='APO' ", obj.getApproveOrg())%>
			</select>
			&nbsp;&nbsp;核准日期：[<input class="field_QRO" type="text" name="approveDate" size="7" maxlength="7" value="<%=obj.getApproveDate()%>">]
			&nbsp;&nbsp;核准文號：[<input class="field_QRO" type="text" name="approveDoc" size="24" maxlength="20" value="<%=obj.getApproveDoc()%>">]
		</td>
	</tr>
	<tr>
		<td width="20%" rowspan="3" class="td_form" >備註：</td>
		<td width="30%" class="td_form_white" >	
			<textarea class="field_QRO" name="notes" cols="30" rows="4"><%=obj.getNotes()%></textarea>
		</td>
		<td width="20%" class="td_form">異動人員/日期：</td>
		<td class="td_form_white">
			[<input class="field_RO" type="texta" name="editID" size="10" value="<%=obj.getEditID()%>">/
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
	<input type="hidden" name="organID" value="<%=user.getOrganID()%>">
	<input type="hidden" name="isOrganManager" value="<%=user.getIsOrganManager()%>">
	<input type="hidden" name="isAdminManager" value="<%=user.getIsAdminManager()%>">			
	<jsp:include page="../../home/toolbar.jsp" />
	<input class="toolbar_default" type="button" followPK="false" name="print2" value="列印減損單" disabled="true" onClick="gountla01617r();">&nbsp;|	
</td></tr>
<!--List區============================================================-->
<!--同一個合併分割案件，各類單據(合併減損單、合併增加單、分割減損單、分割增加單)皆只會有一筆資料-->
<tr><td class="bg">
<div id="listContainer">
<table class="table_form" width="100%" cellspacing="0" cellpadding="0">
	<thead id="listTHEAD">
	<tr>
		<th class="listTH" ><a class="text_link_w" >NO.</a></th>
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',1,false);" href="#">入帳機關</a></th>
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',2,false);" href="#">權屬</a></th>
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',3,false);" href="#">電腦單號</a></th>
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',4,false);" href="#">減損案名</a></th>
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',5,false);" href="#">填單日期</a></th>
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',6,false);" href="#">減損日期</a></th>
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',7,false);" href="#">入帳</a></th>
	</thead>
	<tbody id="listTBODY">
	<%
	boolean primaryArray[] = {true,false,true,false,true,false,false,false,false};
	boolean displayArray[] = {false,true,false,true,true,true,true,true,true};
	out.write(util.View.getQuerylist(primaryArray,displayArray,objList,obj.getQueryAllFlag()));
	%>
	</tbody>
</table>
</div>
</td></tr>
</table>
</form>
<script language='javascript'>
localButtonFireListener.beforeWhatButtonFireEvent = function(buttonName){	
	switch (buttonName)	{	
		//刪除之前多出現一道確認訊息
       	case "delete":
		if(!confirm("刪除此減損單，將一併刪除其關聯的明細資料。\n\n您確定要刪除?"))
			return false;
			hasBeenConfirmed = true;
			break;
	}
	return true;
}
</script>
</body>
</html>

