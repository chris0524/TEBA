
<!--
程式目的：廠商資料維護
程式代號：untba001f
程式日期：0950330
程式作者：clive.chang
--------------------------------------------------------
修改作者　　修改日期　　　修改目的
--------------------------------------------------------
-->

<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../home/head.jsp" %>
<jsp:useBean id="obj" scope="request" class="sys.ca.SYSCA005F">
	<jsp:setProperty name="obj" property="*"/>
</jsp:useBean>
<jsp:useBean id="objList" scope="page" class="java.util.ArrayList"/>

<%
obj.setQueryAllFlag("true"); 
obj.setEnterOrg(user.getOrganID());
obj.setQ_enterOrg(user.getOrganID());

if ("queryAll".equals(obj.getState())) {
	if ("false".equals(obj.getQueryAllFlag())){obj.setQueryAllFlag("true"); }
}else if ("queryOne".equals(obj.getState())) {
	obj = (sys.ca.SYSCA005F)obj.queryOne();	
}else if ("insert".equals(obj.getState()) || "insertError".equals(obj.getState())) {
	obj.insert();
}else if ("update".equals(obj.getState()) || "updateError".equals(obj.getState())) {
	obj.update();
}else if ("delete".equals(obj.getState()) || "deleteError".equals(obj.getState())) {
	obj.delete();
}


objList = obj.queryAll();

String popStoreNo = Common.checkGet(request.getParameter("popStoreNo"));
String popStoreName = Common.checkGet(request.getParameter("popStoreName"));
%>

<html>
<head>
<title>廠商資料維護</title>
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
		//alertStr += checkQuery();
	}else if(form1.state.value=="insert"||form1.state.value=="update"){
		alertStr += checkEmpty(form1.enterOrg,"入帳機關");
		alertStr += checkEmpty(form1.storeNo,"廠商代碼");		
		alertStr += checkEmpty(form1.storeName,"廠商名稱");
		alertStr += checkLen(form1.notes,"備註",250);
	}
	if(alertStr.length!=0){ alert(alertStr); return false; }
	beforeSubmit();
}
function queryOne(enterOrg,storeNo){
	form1.enterOrg.value=enterOrg;
	form1.storeNo.value=storeNo;
	form1.state.value="queryOne";
	beforeSubmit();
	form1.submit();
}

function selectStore(selectID,selectName){
	if (isObj(opener.document.all.item("<%=popStoreNo%>"))) {		
		opener.document.all.item("<%=popStoreNo%>").value=selectID;		
	}
	if (isObj(opener.document.all.item("<%=popStoreName%>"))) {
		opener.document.all.item("<%=popStoreName%>").value=selectName;		
	}
	window.close();
}

function init() {
	setDisplayItem("spanNextInsert","H");
}
</script>
</head>

<body topmargin="5" onLoad="whatButtonFireEvent('<%=obj.getState()%>');init();showErrorMsg('<%=obj.getErrorMsg()%>');">
<form id="form1" name="form1" method="post" onSubmit="return checkField()">
<input type="hidden" name="enterOrg" value="<%=user.getOrganID()%>">
<input type="hidden" name="popStoreNo" value="<%=popStoreNo%>">
<input type="hidden" name="popStoreName" value="<%=popStoreName%>">

<!--Query區============================================================-->
<div id="queryContainer" style="width:400px;height:300px;display:none">
	<iframe id="queryContainerFrame"></iframe>
	<div class="queryTitle">查詢視窗</div>
	<table class="queryTable"  border="1">
	<tr>
		<td class="queryTDLable">廠商代碼：</td>
		<td class="queryTDInput">
			<input class="field_Q" type="text" name="q_storeNo" size="10" maxlength="10" value="<%=obj.getQ_storeNo()%>">
		</td>
	</tr>
	<tr>
		<td class="queryTDLable">廠商名稱：</td>
		<td class="queryTDInput">
			<input class="field_Q" type="text" name="q_storeName" size="10" maxlength="10" value="<%=obj.getQ_storeName()%>">
		</td>
	</tr>
	<tr>
		<td class="queryTDLable">連絡電話一：</td>
		<td class="queryTDInput">
			<input class="field_Q" type="text" name="q_tel1" size="20" maxlength="20" value="<%=obj.getQ_tel1()%>">
		</td>
	</tr>
	<tr>
		<td class="queryTDLable">連絡電話二：</td>
		<td class="queryTDInput">
			<input class="field_Q" type="text" name="q_tel2" size="20" maxlength="20" value="<%=obj.getQ_tel2()%>">
		</td>
	</tr>
	<tr>
		<td class="queryTDLable">連絡人：</td>
		<td class="queryTDInput">
			<input class="field_Q" type="text" name="q_seller" size="10" maxlength="10" value="<%=obj.getQ_seller()%>">
		</td>
	</tr>
	<tr>
		<td class="queryTDLable">傳真號碼：</td>
		<td class="queryTDInput">
			<input class="field_Q" type="text" name="q_fax" size="20" maxlength="20" value="<%=obj.getQ_fax()%>">
		</td>
	</tr>
	<tr>
		<td class="queryTDLable">備註：</td>
		<td class="queryTDInput">
			<input class="field_Q" name="q_notes" type="text" size="20" value="<%=obj.getQ_notes()%>">
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
		<td class="td_form"><font color="red">*</font>廠商代碼：</td>
		<td class="td_form_white">
			<input class="field_P" type="text" name="storeNo" size="10" maxlength="10" value="<%=obj.getStoreNo()%>">
		</td>	
		<td class="td_form"><font color="red">*</font>廠商名稱：</td>
		<td class="td_form_white">
			<input class="field" type="text" name="storeName" size="20" maxlength="25" value="<%=obj.getStoreName()%>">
		</td>

	</tr>
	<tr>
		<td class="td_form">連絡電話1：</td>
		<td class="td_form_white">
			<input class="field" type="text" name="tel1" size="20" maxlength="20" value="<%=obj.getTel1()%>">
		</td>	
		<td class="td_form">連絡電話2：</td>
		<td class="td_form_white">
			<input class="field" type="text" name="tel2" size="20" maxlength="20" value="<%=obj.getTel2()%>">
		</td>

	</tr>
	<tr>
		<td class="td_form">連絡人：</td>
		<td class="td_form_white">
			<input class="field" type="text" name="seller" size="20" maxlength="20" value="<%=obj.getSeller()%>">
		</td>	
		<td class="td_form">傳真號碼：</td>
		<td class="td_form_white">
			<input class="field" type="text" name="fax" size="20" maxlength="20" value="<%=obj.getFax()%>">
		</td>
	</tr>
	<tr>		
		<td class="td_form">備註：</td>
		<td class="td_form_white" colspan="3">
			<input class="field" type="text" name="notes" size="50" maxlength="50" value="<%=obj.getNotes()%>">
		</td>
	</tr>
	<tr>
		<td class="td_form">異動人員/日期：</td>
		<td class="td_form_white" colspan="3">
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
		<th class="listTH"><a class="text_link_w" >NO.</a></th>
		<th class="listTH"><a class="text_link_w">選擇</a></th>
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',2,false);" href="#">廠商代碼</a></th>
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',3,false);" href="#">廠商名稱</a></th>
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',4,false);" href="#">連絡電話1</a></th>
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',5,false);" href="#">連絡人</a></th>
	</thead>
	<tbody id="listTBODY">
	<%
	//boolean primaryArray[] = {true,true,false,false,false};
	//boolean displayArray[] = {true,true,true,true,true};
	//out.write(util.View.getQuerylist(primaryArray,displayArray,objList,obj.getQueryAllFlag()));

	int counter=0;
	StringBuffer sbHTML = new StringBuffer();
	if ("true".equals(obj.getQueryAllFlag()) && objList.size()==0){
		sbHTML.append(" <tr class='listTR' ><td class='listTD' colspan='100' style='color:red'>查無資料，請您重新輸入查詢條件！</td></tr>");
	}else{
		String rowArray[]=new String[5];
		java.util.Iterator it=objList.iterator();
		while(it.hasNext()){		
			counter++;	
			rowArray= (String[])it.next();
			sbHTML.append(" <tr class='listTR' onmouseover=\"this.className='listTRMouseover'\" onmouseout=\"this.className='listTR'\" onClick=\"queryOne('").append(rowArray[0]).append("','").append(rowArray[1]).append("')\" >");
			sbHTML.append(" <td class='listTD' >").append(counter).append(".</td> ");
			sbHTML.append(" <td class='listTD' ><a href='#'><img src='../../image/selectNo.gif' border='0' alt='選擇廠商並返回' onclick=\"selectStore('").append(rowArray[1]).append("','").append(rowArray[2]).append("');\"></a></td> ");		
			sbHTML.append(" <td class='listTD' >").append(rowArray[1]).append("</td> ");
			sbHTML.append(" <td class='listTD' >").append(rowArray[2]).append("</td> ");
			sbHTML.append(" <td class='listTD' >").append(rowArray[3]).append("</td> ");			
			sbHTML.append(" <td class='listTD' >").append(rowArray[4]).append("</td></tr> ");			
		}
	}
	out.write(sbHTML.toString());	
	%>
	</tbody>
</table>
</div>
</td></tr>
</table>
</form>
</body>
</html>

