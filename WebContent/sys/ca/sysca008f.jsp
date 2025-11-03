<!--
程式目的：基金與機關代碼對照資料維護
程式代號：sysca008f
程式日期：0980622
程式作者：Chu-Hung
--------------------------------------------------------
修改作者　　修改日期　　　修改目的
--------------------------------------------------------
-->

<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../home/head.jsp" %>
<jsp:useBean id="obj" scope="request" class="sys.ca.SYSCA008F">
	<jsp:setProperty name="obj" property="*"/>
</jsp:useBean>
<jsp:useBean id="objList" scope="page" class="java.util.ArrayList"/>
<%
if ("queryAll".equals(obj.getState())) {
	if ("false".equals(obj.getQueryAllFlag())){obj.setQueryAllFlag("true"); }
}else if ("queryOne".equals(obj.getState())) {
	//obj.setFundNo(((String[])objList.get(1))[0]);
	obj = (sys.ca.SYSCA008F)obj.queryOne();	
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
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
<meta http-equiv="pragma" content="no-cache"/>
<meta http-equiv="Cache-control" content="no-cache"/>
<link rel="stylesheet" href="../../js/default.css" type="text/css"/>
<script type="text/javascript" src="../../js/validate.js"></script>
<script type="text/javascript" src="../../js/function.js"></script>
<script type="text/javascript" src="../../js/tablesoft.js"></script>
<script type="text/javascript" src="../../js/changeEnterOrg_FundType.js"></script>
<script type="text/javascript">
var insertDefault;	//二維陣列, 新增時, 設定預設值
insertDefault = new Array(
	new Array("enterOrg","<%=user.getOrganID()%>"),
	new Array("enterOrgName","<%=user.getOrganName()%>")
);               
function checkField(){
	var alertStr="";
	if(form1.state.value=="insert"||form1.state.value=="insertError"||form1.state.value=="update"||form1.state.value=="updateError"){
		alertStr += checkEmpty(form1.fundNo,"基金名稱");
		alertStr += checkEmpty(form1.enterOrg,"管理機關");
	}
	if(alertStr.length!=0){ alert(alertStr); return false; }
	beforeSubmit();
}
function queryOne(fundno2,enterOrg){
    var elOptNew = document.createElement('option');
	elOptNew.value = fundno2;
	form1.fundNo.add(elOptNew, 0);
    form1.fundNo.value = fundno2;
	form1.enterOrg.value = enterOrg;
	form1.state.value="queryOne";

	beforeSubmit();
	form1.submit();
}

function alteredQEnterOrg(){
  changeEnterOrg_FundType(form1.q_enterOrg.value,'<%=user.getOrganID()%>','q_fundNo','','<%=user.getIsAdminManager()%>','<%=user.getIsOrganManager()%>');	
}

function init(){
    //form1.fundNo.disabled=true;
}

function changeEnterOrg_FundType(enterOrg,organID,q_No,selectValue,isAdminManager,isOrganManager){
	var obj2 = document.all.item(q_No);
	obj2.options.length = 0;
	var oOption = document.createElement("Option");	
	obj2.options.add(oOption);
	oOption.innerText = "請選擇";
	oOption.value = "";		
	var queryValue = enterOrg;	
	var xmlDoc = document.createElement("xml");	
	xmlDoc.async = false;

	today = new Date();
	var abc = today.getTime();

	var source = "sysca008f";
	
	var url = getVirtualPath()+"home/xmlChangeEnterOrg_FundType.jsp?enterOrg="+queryValue+"&organID="+organID+"&isAdminManager="+isAdminManager+"&isOrganManager="+isOrganManager+"&source="+source+"&abc="+abc;

	if(xmlDoc.load(url)){
		var nodesLen = xmlDoc.documentElement.childNodes.length;
		for(var i = 0; i < nodesLen; i++) {
			codeID = xmlDoc.documentElement.childNodes.item(i).getAttribute("codeID");
			codeName = xmlDoc.documentElement.childNodes.item(i).getAttribute("codeName");
			var oOption = document.createElement("Option");	
			obj2.options.add(oOption);
			oOption.innerText = codeName;
			oOption.value = codeID;		
	      	if(codeID == selectValue){
    			oOption.selected = true;
			}           										
		}
	}	
}
</script>
</head>

<body topmargin="0" onLoad="init();whatButtonFireEvent('<%=obj.getState()%>');showErrorMsg('<%=obj.getErrorMsg()%>');">
<form id="form1" name="form1" method="post" onSubmit="return checkField()">

<!--Query區============================================================-->
<div id="queryContainer" style="width:400px;height:200px;display:none">
	<iframe id="queryContainerFrame"></iframe>
	<div class="queryTitle">查詢視窗</div>
	<table class="queryTable"  border="1">
	<tr>
		<td class="queryTDLable">管理機關：</td>
		<td class="queryTDInput">
			<%=util.View.getPopOrgan("field_Q","q_enterOrg",obj.getQ_enterOrg(),obj.getQ_enterOrgName(),"N&js=alteredQEnterOrg();")%>
		</td>
	</tr>
	<tr>
		<td class="queryTDLable" width="12%">基金名稱：</td>
		<td class="queryTDInput" width="38%">			
			<select class="field_Q" type="select" name="q_fundNo2" >
				<%=util.View.getOption(" select c.fundno, a.codename from SYSCA_CODE a, SYSCA_ORGAN b, SYSCA_FUNDORGAN c  where 1=1 and codekindid = 'FUD'  and ismanager = 'Y' and b.organid = c.enterorg and a.codeid = c.fundno and c.enterorg = '" + user.getOrganID() + "' order by organid ", obj.getQ_fundNo2())%>
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
		<td class="td_form"><font color="red">*</font>管理機關：</td>
		<td class="td_form_white">
	<%=util.View.getPopOrgan("field","enterOrg",obj.getEnterOrg(),obj.getEnterOrgName(),"")%>
		</td>
	</tr>
	<tr>
		<td class="td_form" width="16%"><font color="red">*</font>基金名稱：</td>
		<td class="td_form_white" width="32%">
		  <select class="field" type="select" name="fundNo">
	         <script>
changeEnterOrg_FundType(form1.enterOrg.value,'<%=user.getOrganID()%>','fundNo','<%=obj.getFundNo()%>','<%=user.getIsAdminManager()%>','<%=user.getIsOrganManager()%>');
             </script>
		  </select>
		</td>
	</tr>
	<tr>
		<td class="td_form">備註：</td>
		<td class="td_form_white">
			<textarea class="field" name="notes" cols="24" rows="4"><%=obj.getNotes()%></textarea>
		</td>
	</tr>
	<tr>
		<td class="td_form">異動時間：</td>
		<td class="td_form_white">
			[<input class="field_RO" type="text" name="editTime" size="6" maxlength="6" value="<%=obj.getEditTime()%>">]
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
	<input type="hidden" name="a" value="">
	<jsp:include page="../../home/toolbar.jsp" />
</td></tr>
<!--List區============================================================-->
<tr><td class="bg">
<div id="listContainer">
<table class="table_form" width="100%" cellspacing="0" cellpadding="0">
	<thead id="listTHEAD">
	<tr>
		<th class="listTH" ><a class="text_link_w" >NO.</a></th>
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',1,false);" href="#">基金名稱</a></th>
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',2,false);" href="#">管理機關</a></th>
	</tr>
	</thead>
	<tbody id="listTBODY">
	<%
	boolean primaryArray[] = {false,false,true,true};
	boolean displayArray[] = {true,true,false,false};
	out.write(util.View.getQuerylist(primaryArray,displayArray,objList,obj.getQueryAllFlag()));
	%>
	</tbody>
</table>
</div>
</td></tr>
</table>
</form>
<script type="text/javascript">
localButtonFireListener.beforeWhatButtonFireEvent = function(buttonName){	
	switch (buttonName)	{	
		//刪除之前多出現一道確認訊息
        case "delete":
		case "queryAll":			
	}
	return true;
}


localButtonFireListener.whatButtonFireEvent = function(buttonName){
	switch (buttonName)	{
		case "insert":
		form1.fundNo.disabled=false;
changeEnterOrg_FundType(form1.enterOrg.value,'<%=user.getOrganID()%>','fundNo','<%=obj.getFundNo()%>','<%=user.getIsAdminManager()%>','<%=user.getIsOrganManager()%>');
        break;
		case "update":
			changeEnterOrg_FundType(form1.enterOrg.value,'<%=user.getOrganID()%>','fundNo','<%=obj.getFundNo()%>','<%=user.getIsAdminManager()%>','<%=user.getIsOrganManager()%>');
			form1.btn_enterOrg.disabled=true;
			form1.fundNo.disabled=true;
		break;
        
		case "insertError":
changeEnterOrg_FundType(form1.enterOrg.value,'<%=user.getOrganID()%>','fundNo','<%=obj.getFundNo()%>','<%=user.getIsAdminManager()%>','<%=user.getIsOrganManager()%>');
        break;
	}
}
</script>
</body>
</html>