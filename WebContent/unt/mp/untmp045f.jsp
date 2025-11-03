<!--
程式目的：動產標籤桃檔作業
程式代號：untmp045f
程式日期：0961023
程式作者：shan
--------------------------------------------------------
修改作者　　修改日期　　　修改目的
--------------------------------------------------------
-->

<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../home/head.jsp" %>
<jsp:useBean id="obj" scope="request" class="unt.mp.UNTMP045F">
	<jsp:setProperty name="obj" property="*"/>
</jsp:useBean>
<jsp:useBean id="objList" scope="page" class="java.util.ArrayList"/>

<%
obj.setUserID(user.getUserID());
if ("queryAll".equals(obj.getState()) || "init".equals(obj.getState()) ) {
	if ("false".equals(obj.getQueryAllFlag())){obj.setQueryAllFlag("true"); }
}else if("delPrintData".equals(obj.getState())){
	obj.delPrintData();
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
<script language="javascript" src="../../js/sList2.js"></script>
<script language="javascript">
var insertDefault;	//二維陣列, 新增時, 設定預設值
function checkField(){
	var alertStr="";
	if(form1.state.value=="queryAll"){
		alertStr += checkQuery();
	}else if(form1.state.value=="insert"||form1.state.value=="insertError"||form1.state.value=="update"||form1.state.value=="updateError"){
	}
	if(alertStr.length!=0){ alert(alertStr); return false; }
	beforeSubmit();
}

function link_delPrintData(){
	form1.state.value="delPrintData";
	beforeSubmit();
	form1.submit();
	opener.form1.submit();
}

function link_printData(){
	//var url="untmp042p.jsp?userID="+form1.userID.value;
	//untmp042p = window.open(url);
	var url="";
	if (document.all.q_labelType[0].checked) {
		form1.action="untmp042p.jsp";
	}else{
    	form1.action="untmp043p.jsp";
    }	
    queryHidden('goUntmp045f');
}

function link_printData_S(){
	var url="untmp043p.jsp?userID="+form1.userID.value;
	untmp043p = window.open(url);
}

function init(){
form1.state.value='queryAll';
setDisplayItem('spanListHidden,spanListPrint,spanqueryAll,spanInsert,spanUpdate,spanDelete,spanNextInsert,spanConfirm,spanClear','H');
form1.enterOrg.value="<%=user.getOrganID()%>";
form1.toggleAll.checked = false;

}
</script>
</head>

<body topmargin="0" onLoad="whatButtonFireEvent('<%=obj.getState()%>');showErrorMsg('<%=obj.getErrorMsg()%>');init();">
<form id="form1" name="form1" method="post" onSubmit="return checkField()">

<!--Query區============================================================-->
<div id="queryContainer" style="width:750px;height:300px;display:none">
	<iframe id="queryContainerFrame"></iframe>
	<div class="queryTitle">查詢視窗</div>	
	<% request.setAttribute("UNTMP044Q",obj);%>
	<jsp:include page="untmp044q.jsp" />
</div>
<!--列印選擇============================================================-->
<div id="goUntmp045f" style="position:absolute;z-index: 25;left:0;top :0;width:300px;height:80px;display:none">
	<iframe id="goUntmp045fFrame"  style="position:absolute;z-index: -1;left:0;top :0;width:100%;height:100%;"></iframe>	
	<div class="queryTitle">列印選擇</div>
	<table class="queryTable"  border="1">	
	<tr>
		<td class="queryTDLable"><font color="red">*</font>標籤類別：</td>
		<td class="queryTDInput" colspan="4">
			<input class="field_Q" type="radio" name="q_labelType" value="1" CHECKED>大標籤
			<input class="field_Q" type="radio" name="q_labelType" value="2">小標籤
		</td>
	</tr>
	<tr>
		<td class="queryTDLable">標籤跳空：</td>
		<td class="queryTDInput">
			<input class="field_Q" type="text" name="q_blankSpace" size="2" maxlength="2" value="<%=obj.getQ_blankSpace()%>">
		</td>
	</tr>
	<tr>
		<td class="queryTDLable">列印保管人姓名：</td>
		<td class="queryTDInput">
			<input class="field_Q" type="radio" name="q_printKeeper" value="Y" CHECKED>是
			<input class="field_Q" type="radio" name="q_printKeeper" value="N">否
		</td>
	</tr>
	<tr>
		<td class="queryTDInput" colspan="2" style="text-align:center;">
			<input class="toolbar_default" followPK="false" type="submit" name="querySubmit" value="確    定" onClick="link_printData();">
			<input class="toolbar_default" followPK="false" type="button" name="queryCannel" value="取　　消" onClick="queryHidden('goUntmp045f');init();">
		</td>
	</tr>
	</table>
</div>
<table width="100%" cellspacing="0" cellpadding="0">
<!--隱藏欄位區=============================================================-->
	<input type="hidden" name="enterOrg" value="<%=obj.getEnterOrg()%>">
	<input type="hidden" name="state" value="<%=obj.getState()%>">
	<input type="hidden" name="queryAllFlag" value="<%=obj.getQueryAllFlag()%>">
	<input type="hidden" name="userID" value="<%=user.getUserID()%>">
<!--Toolbar區============================================================-->
<tr><td class="bg" style="text-align:center">
	<jsp:include page="../../home/toolbar.jsp" />
	<input class="toolbar_default" type="button" followPK="false" name="upPrintData" value="刪    除" onClick="link_delPrintData();">&nbsp;|
	<input class="toolbar_default" type="button" followPK="false" name="printData_L" value="列　印" onClick="queryShow('goUntmp045f');">&nbsp;|
</td></tr>
<tr><td>
	<% request.setAttribute("QueryBean",obj);%>
	<jsp:include page="../../home/page.jsp" />
</td></tr>
<!--List區============================================================-->
<tr><td class="bg">
<div id="listContainer" style="width: 100%;	height: auto;">
<table class="table_form" width="100%" cellspacing="0" cellpadding="0">
	<thead id="listTHEAD">
	<tr>
		<th class="listTH" ><a class="text_link_w" >NO.</a></th>
		<th class="listTH"><input type=checkbox name=toggleAll class='field_Q' onclick="ToggleAll(this, document.form1, 'sKeySet');" value="Y" <%=obj.getToggleAll()%>></th>
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',1,false);" href="#">財產編號</a></th>		
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',2,false);" href="#">財產分號</a></th>
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',3,false);" href="#">財產名稱</a></th>
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',4,false);" href="#">型式</a></th>
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',5,false);" href="#">廠牌</a></th>
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',6,false);" href="#">保管單位</a></th>
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',7,false);" href="#">保管人</a></th>
	</tr>
	</thead>
	<tbody id="listTBODY">
	<%
	//boolean primaryArray[] = {false,false,false,false,false,false,false,false,false};
	//boolean displayArray[] = {false,false,true,true,true,true,true,true,true};
	//out.write(util.View.getQuerylist(primaryArray,displayArray,objList,obj.getQueryAllFlag()));
	
	int counter=0;
	StringBuffer sbHEML = new StringBuffer();
	if ("true".equals(obj.getQueryAllFlag()) && objList.size()==0){
		sbHEML.append(" <tr class='listTR' ><td class='listTD' colspan='100' style='color:red'>尚未挑選任何資料！</td></tr>");
	}else{
		String rowArray[]=new String[9];
		java.util.Iterator it=objList.iterator();
		String tempJS="";
		String isCheck = "unchecked";	
		while(it.hasNext()){
			counter++;	
			rowArray= (String[])it.next();	//取得陣列資料
			isCheck = "unchecked";

			sbHEML.append(" <tr class='listTR'>\n");
			sbHEML.append(" <td class='listTD'>"+counter+".</td> ");
			sbHEML.append(" <td class='listTD'><input type='checkbox' class='field_Q' name='sKeySet' value=\""+rowArray[0]+","+rowArray[1]+","+rowArray[9]+"\" onclick=\"Toggle(this, document.form1, 'sKeySet');\"" + isCheck + ">\n");
			sbHEML.append(" <td class='listTD'>"+rowArray[2]+"</td>\n ");
			sbHEML.append(" <td class='listTD'>"+rowArray[3]+"</td>\n ");
			sbHEML.append(" <td class='listTD'>"+rowArray[4]+"</td>\n ");
			sbHEML.append(" <td class='listTD'>"+rowArray[5]+"</td>\n ");
			sbHEML.append(" <td class='listTD'>"+rowArray[6]+"</td>\n ");
			sbHEML.append(" <td class='listTD'>"+rowArray[7]+"</td>\n ");
			sbHEML.append(" <td class='listTD'>"+rowArray[8]+"</td>\n ");
		}
	}
	
	out.write(sbHEML.toString());	
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
	case "queryAll"://做查詢時,將某些欄位填入預設值
		form1.q_enterOrg.value="<%=user.getOrganID()%>";
		form1.q_enterOrgName.value="<%=user.getOrganName()%>";
	break;				
	}
	return true;
}
</script>
</body>
</html>



