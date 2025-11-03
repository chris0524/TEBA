<!--
程式目的：
程式代號：
程式日期：
程式作者：shan
--------------------------------------------------------
修改作者　　修改日期　　　修改目的
--------------------------------------------------------
-->

<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../home/head.jsp" %>
<jsp:useBean id="obj" scope="request" class="unt.gr.UNTGR027F">
	<jsp:setProperty name="obj" property="*"/>
</jsp:useBean>
<jsp:useBean id="objList" scope="page" class="java.util.ArrayList"/>

<%
if ("queryAll".equals(obj.getState())) {
	if ("false".equals(obj.getQueryAllFlag())){obj.setQueryAllFlag("true"); }
}else if ("queryOne".equals(obj.getState())) {
	obj = (unt.gr.UNTGR027F)obj.queryOne();	
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
<script language="javascript">
var insertDefault;	//二維陣列, 新增時, 設定預設值

insertDefault = new Array(
	new Array("enterOrg","<%=user.getOrganID()%>"),
	new Array("enterOrgName","<%=user.getOrganName()%>"),
	new Array("ownership","1"),
	new Array("ownershipShow","1")
);

function checkField(){
	var alertStr="";
	if(form1.state.value=="queryAll"){
		alertStr += checkQuery();
	}else if(form1.state.value=="insert"||form1.state.value=="insertError"||form1.state.value=="update"||form1.state.value=="updateError"){
		alertStr += checkEmpty(form1.enterOrg,"入帳機關");
		alertStr += checkEmpty(form1.ownership,"權屬");
		alertStr += checkEmpty(form1.fundCode,"基金名稱");
    	alertStr += checkEmpty(form1.propertyClass,"類別");
    	alertStr += checkEmpty(form1.closeYM,"結存年月");
    	alertStr += checkEmpty(form1.closeAmount,"結存數量");
    	alertStr += checkEmpty(form1.closeArea,"結存面積");
    	alertStr += checkEmpty(form1.closeValue,"結存價值");
	}
	if(alertStr.length!=0){ alert(alertStr); return false; }
	beforeSubmit();
}
function queryOne(enterOrg,ownership,fundCode,propertyClass,closeYM){
	form1.enterOrg.value=enterOrg;
	form1.ownership.value=ownership;
	form1.fundCode.value=fundCode;
	form1.propertyClass.value=propertyClass;
	form1.closeYM.value=closeYM;
	form1.state.value="queryOne";
	beforeSubmit();
	form1.submit();
}

function init(){

}
</script>
</head>

<body topmargin="0" onLoad="init();whatButtonFireEvent('<%=obj.getState()%>');showErrorMsg('<%=obj.getErrorMsg()%>');">
<form id="form1" name="form1" method="post" onSubmit="return checkField()">

<!--Query區============================================================-->
<div id="queryContainer" style="width:400px;height:250px;display:none">
	<iframe id="queryContainerFrame"></iframe>
	<div class="queryTitle">查詢視窗</div>
	<table class="queryTable"  border="1">
	<tr>
		<td class="queryTDLable" width="20%">入帳機關：</td>
		<td class="queryTDInput" width="30%">
			<%=util.View.getPopOrgan("field_Q","q_enterOrg",obj.getQ_enterOrg(),obj.getQ_enterOrgName(), "N")%>
		</td>
	</tr>
	<tr>
		<td class="queryTDLable">權屬：</td>
		<td class="queryTDInput">
			<select class="field_Q" type="select" name="q_ownership">
				<%=util.View.getOnwerOption(obj.getQ_ownership())%>			
			</select>
		</td>
	</tr>
	<tr>
		<td class="queryTDLable">類別：</td>
		<td class="queryTDInput">
			<select class="field_Q" type="select" name="q_propertyClass">
				<%=util.View.getTextOption("1;土地;2;房屋建築及設備;3;動產設備",obj.getQ_propertyClass())%>
			</select>
		</td>
	</tr>
	<tr>
		<td class="queryTDLable">結存年月：</td>
		<td class="queryTDInput">
			<input class="field_Q" type="text" name="q_closeYM" size="5" maxlength="5" value="<%=obj.getQ_closeYM()%>" ><font color="red">（例：09601）</font>
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
		<td class="td_form" width="10%"><font color="red">*</font>入帳機關：</td>
		<td class="td_form_white" width="32%" colspan="3">
			<input class="field_PRO" type="hidden" name="enterOrg" size="10" maxlength="10" value="<%=obj.getEnterOrg()%>" >
			[<input class="field_PRO" type="text" name="enterOrgName" size="20" maxlength="50" value="<%=obj.getEnterOrgName()%>">]
			權屬：
			<select class="field_P" type="select" name="ownership">
				<%=util.View.getOnwerOption(obj.getOwnership())%>
			</select>
		</td>
	</tr>
	<tr>
		<td class="td_form" width="10%"><font color="red">*</font>基金名稱：</td>
		<td class="td_form_white" colspan="3">
			<select class="field_P" type="select" name="fundCode" >
				<%=util.View.getOption("select codeid ,codename from SYSCA_Code where codekindid='FNM' and decode('"+user.getIsAdminManager()+"','Y','"+user.getOrganID()+"',codecon1)='"+Common.esapi(user.getOrganID())+"' order by codeid", obj.getFundCode())%>
			</select>
			<font color="red">*</font>類別：
			<select class="field_P" type="select" name="propertyClass">
				<%=util.View.getTextOption("1;土地;2;房屋建築及設備;3;動產設備",obj.getPropertyClass())%>
			</select>
		</td>
	</tr>
	<tr>
		<td class="td_form" width="10%">結存資料：</td>
		<td class="td_form_white" colspan="3">
		<font color="red">*</font>年月：
			<input class="field_P" type="text" name="closeYM" size="5" maxlength="5" value="<%=obj.getCloseYM()%>" ><font color="red">（例：09601）</font>
		<br><font color="red">*</font>數量：
			<input class="field" type="text" name="closeAmount" size="15" maxlength="13" value="<%=obj.getCloseAmount()%>" >
		<br><font color="red">*</font>面積：
			<input class="field" type="text" name="closeArea" size="15" maxlength="10" value="<%=obj.getCloseArea()%>" >平方公尺	
		<br><font color="red">*</font>價值：
			<input class="field" type="text" name="closeValue" size="15" maxlength="15" value="<%=obj.getCloseValue()%>">
		</td>
	</tr>
	<tr>
		<td class="td_form">備註：</td>
      	<td class="td_form_white"><textarea class="field" type="text" name="notes" cols="25" rows="4"><%=obj.getNotes()%></textarea></td>
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
	<input type="hidden" name="isAdminManager" value="<%=user.getIsAdminManager()%>">	
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
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',1,false);" href="#">入帳機關</a></th>
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',2,false);" href="#">基金名稱</a></th>
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',3,false);" href="#">類別</a></th>
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',4,false);" href="#">結存年月</a></th>
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',5,false);" href="#">結存數量</a></th>
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',6,false);" href="#">結存面積</a></th>
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',7,false);" href="#">結存價值</a></th>
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',8,false);" href="#">說明</a></th>
	</tr>
	</thead>
	<tbody id="listTBODY">
	<%
	boolean primaryArray[] = {true,false,false,true,true,true,false,true,false,false,false,false};
	boolean displayArray[] = {false,true,true,false,false,false,true,true,true,true,true,true};
	out.write(util.View.getQuerylist(primaryArray,displayArray,objList,obj.getQueryAllFlag()));
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
		//刪除之前多出現一道確認訊息
       	case "delete":
			break;
			
		//做查詢時,將某些欄位填入預設值
		case "queryAll":
			if (parse(form1.q_enterOrg.value).length<=0) {
				form1.q_enterOrg.value="<%=user.getOrganID()%>";
				form1.q_enterOrgName.value="<%=user.getOrganName()%>";
			}
			break;				
	}
	return true;
}
</script>
</body>
</html>



