
<!--
程式目的：使用人資料
程式代號：npbgr002f
程式日期：0950217
程式作者：clive.chang
--------------------------------------------------------
修改作者　　修改日期　　　修改目的
--------------------------------------------------------
-->

<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%> 
<%@ include file="../../home/head.jsp" %>
<jsp:useBean id="obj" scope="request" class="unt.la.UNTLA049F">
	<jsp:setProperty name="obj" property="*"/>
</jsp:useBean>
<jsp:useBean id="objList" scope="page" class="java.util.ArrayList"/>

<%
String popEnterOrg = util.Common.get(request.getParameter("popEnterOrg"));
String popOwnership = util.Common.get(request.getParameter("popOwnership"));
String popPropertyNo = util.Common.get(request.getParameter("popPropertyNo"));
String popSerialNo = util.Common.get(request.getParameter("popSerialNo"));
String popSerialNo1 = util.Common.get(request.getParameter("popSerialNo1"));


if ("queryAll".equals(obj.getState())) {
	if ("false".equals(obj.getQueryAllFlag())){obj.setQueryAllFlag("true"); }
}else if ("queryOne".equals(obj.getState())) {
	obj = (unt.la.UNTLA049F)obj.queryOne();	
}else if ("insert".equals(obj.getState()) || "insertError".equals(obj.getState())) {
	obj.insert();
}else if ("update".equals(obj.getState()) || "updateError".equals(obj.getState())) {
	obj.update();
	obj = (unt.la.UNTLA049F)obj.queryOne();
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
<script language="javascript" src="../../js/getNPBGR_Person.js"></script>
<script language="javascript" src="npbgr001q.js"></script>
<script language="javascript">
var insertDefault;	//二維陣列, 新增時, 設定預設值

insertDefault = new Array(
	new Array("applyType","1"),
	new Array("payerYN","Y"),
	new Array("registryYN","N")
);

function checkField(){
	var alertStr="";
	if(form1.state.value=="queryAll"){
		alertStr += checkQuery();
	}else if(form1.state.value=="insert"||form1.state.value=="insertError"||form1.state.value=="update"||form1.state.value=="updateError"){
		alertStr += checkEmpty(form1.applyType,"身分別");
		alertStr += checkEmpty(form1.payerYN,"是否為繳款人");		
		if (parse(form1.applyID.value).length>0) {
			form1.applyID.value = form1.applyID.value.toUpperCase();
			if (form1.state.value=="insert"||form1.state.value=="insertError") {		
				if (form1.applyType.value=="2") alertStr += checkCompID(form1.applyID,"身分證字號/");
				else alertStr += checkPersonalID(form1.applyID,"身分證字號/統一編號");		
			}
		}
		if (form1.state.value=="update"||form1.state.value=="updateError") {
			alertStr += checkEmpty(form1.applyID,"身分證字號/統一編號");			
			if (form1.applyID.value!=form1.oldApplyID.value) {
				if (form1.applyType.value=="2") alertStr += checkCompID(form1.applyID,"身分證字號/");
				else alertStr += checkPersonalID(form1.applyID,"身分證字號/統一編號");
			}
		}			
		alertStr += checkEmpty(form1.applyName,"個人姓名/公司名稱");
		alertStr += checkLen(form1.liveAdd4,"通訊地址",50);
		alertStr += checkLen(form1.notes,"備註",250);
	}
	if(alertStr.length!=0){ alert(alertStr); return false; }
	beforeSubmit();
}

function queryOne(enterOrg,ownership,propertyNo,serialNo,serialNo1,applyID,checkSeq){
	form1.enterOrg.value=enterOrg;
	form1.ownership.value=ownership;
	form1.propertyNo.value=propertyNo;
	form1.serialNo.value=serialNo;
	form1.serialNo1.value=serialNo1;
	form1.applyID.value=applyID;
	form1.checkSeq.value=checkSeq;
	form1.state.value="queryOne";
	beforeSubmit();
	form1.submit();
}

function init() {
	document.all.item("spanQueryAll").style.display = "none";
}

</script>
</head>

<body topmargin="0" onLoad="whatButtonFireEvent('<%=obj.getState()%>');init();showErrorMsg('<%=obj.getErrorMsg()%>');">
<form id="form1" name="form1" method="post" onSubmit="return checkField()">
<input type="hidden" name="seq" value="<%=obj.getSeq()%>">
<!--Query區============================================================-->

<!--Form區============================================================-->
<tr><td class="bg">
	<div id="formContainer">
	<table class="table_form" width="100%" height="100%">
	<tr>
	  <td class="td_form">承租人：</td>
	  <td colspan="3" class="td_form_white"><font color="red">*</font>身分別：
	    <select class="field" type="select" name="applyType">
          <%=util.View.getTextOption("1;個人;2;公司",obj.getApplyType())%>
        </select>
        　
        身分證字號/統一編號：
        <input class="field" type="text" name="applyID" size="10" maxlength="10" value="<%=obj.getApplyID()%>" onBlur="getNPBGR_Person();">
        <br><font color="red">*</font>是否為繳款人：
        <select class="field" type="select" name="payerYN">
          <%=util.View.getYNOption(obj.getPayerYN())%>
        </select>　
                <font color="red">*</font>個人姓名/公司名稱：
                <input class="field" type="text" name="applyName" size="20" maxlength="20" value="<%=obj.getApplyName()%>"></td>
	  </tr>
	<tr>
		<td class="td_form"><font color="red">*</font>是否設籍：</td>
		<td colspan="3" class="td_form_white">
<select class="field" type="select" name="registryYN">
          <%=util.View.getYNOption(obj.getRegistryYN())%>
        </select>　		
			聯絡電話：<input class="field" type="text" name="homeTel" size="20" maxlength="20" value="<%=obj.getHomeTel()%>">		　
			行動電話：
			<input class="field" type="text" name="mobile" size="10" maxlength="10" value="<%=obj.getMobile()%>">		</td>
		</tr>
	<tr>
		<td class="td_form">通訊地址：</td>
		<td class="td_form_white" colspan="3">
      
          <input name="liveAdd4" type="text" class="field" value="<%=obj.getLiveAdd4()%>" size="40" maxlength="50">		</td>
	</tr>
	<tr>
		<td class="td_form">備註：</td>
		<td class="td_form_white">
			<textarea class="field" name="notes" cols="24" rows="4"><%=obj.getNotes()%></textarea>		</td>
		<td class="td_form">異動人員/日期：</td>
		<td class="td_form_white">
			[<input class="field_RO" type="text" name="editID" size="10" value="<%=obj.getEditID()%>">/<input class="field_RO" type="text" name="editDate" size="7" value="<%=obj.getEditDate()%>">]		</td>
	</tr>
	</table>
	</div>
</td></tr>

<!--Toolbar區============================================================-->
<tr><td class="bg" style="text-align:center">
	<input type="hidden" name="changeKey" value="<%=obj.getChangeKey()%>">
	<input type="hidden" name="oldApplyID" value="<%=obj.getApplyID()%>">
	<input type="hidden" name="checkApplyID" value="<%=obj.getCheckApplyID()%>">
	<input type="hidden" name="checkSeq" value="<%=obj.getCheckSeq()%>">
	<input type="hidden" name="enterOrg" value="<%=popEnterOrg%>">
	<input type="hidden" name="ownership" value="<%=popOwnership%>">	
	<input type="hidden" name="propertyNo" value="<%=popPropertyNo%>">
	<input type="hidden" name="serialNo" value="<%=popSerialNo%>">
	<input type="hidden" name="serialNo1" value="<%=popSerialNo1%>">
	<input type="hidden" name="fakeDivision" value="<%=obj.getFakeDivision()%>">	
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
		<th class="listTH"><a class="text_link_w" onClick="return sortTable('listTBODY',1,false);" href="#">身分別</a></th>
		<th class="listTH"><a class="text_link_w" onClick="return sortTable('listTBODY',2,false);" href="#">繳款人</a></th>
		<th class="listTH"><a class="text_link_w" onClick="return sortTable('listTBODY',3,false);" href="#">設籍</a></th>		
		<th class="listTH"><a class="text_link_w" onClick="return sortTable('listTBODY',4,false);" href="#">身分証字號/統一編號</a></th>
		<th class="listTH"><a class="text_link_w" onClick="return sortTable('listTBODY',5,false);" href="#">姓名/公司名稱</a></th>
		<th class="listTH"><a class="text_link_w" onClick="return sortTable('listTBODY',6,false);" href="#">聯絡電話</a></th>
		<th class="listTH"><a class="text_link_w" onClick="return sortTable('listTBODY',7,false);" href="#">行動電話</a></th>
		<th class="listTH"><a class="text_link_w" onClick="return sortTable('listTBODY',8,false);" href="#">通訊地址</a></th>
	</tr>
	</thead>
	<tbody id="listTBODY"> 
		<%=obj.getNPBGR_Person(popEnterOrg,popOwnership,popPropertyNo,popSerialNo,popSerialNo1)%>  
	</tbody>
</table>
</div>
</td></tr>
</table>
</form>
</body>
</html>



