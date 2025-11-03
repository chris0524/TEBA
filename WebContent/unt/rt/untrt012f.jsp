<!--
程式目的：他項權利證明書領狀紀錄
程式代號：untrt012f
程式日期：0941018
程式作者：clive.chang
--------------------------------------------------------
修改作者　　修改日期　　　修改目的
--------------------------------------------------------
-->

<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../home/head.jsp" %>
<jsp:useBean id="obj" scope="request" class="unt.rt.UNTRT012F">
	<jsp:setProperty name="obj" property="*"/>
</jsp:useBean>
<jsp:useBean id="objList" scope="page" class="java.util.ArrayList"/>

<%
if ("queryAll".equals(obj.getState())) {
	if ("false".equals(obj.getQueryAllFlag())){obj.setQueryAllFlag("true"); }
}else if ("queryOne".equals(obj.getState())) {
	obj = (unt.rt.UNTRT012F)obj.queryOne();	
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
	new Array("enterOrg","<%=user.getOrganID()%>"),
	new Array("ownership","1"),
	new Array("editID","<%=user.getUserID()%>"),
	new Array("editDate","<%=util.Datetime.getYYYMMDD()%>"),
	new Array("drawDate","<%=util.Datetime.getYYYMMDD()%>")
);

function checkField(){
	var alertStr="";
	if(form1.state.value=="queryAll"){
		alertStr += checkQuery();
	}else if(form1.state.value=="insert"||form1.state.value=="update"){
		alertStr += checkEmpty(form1.enterOrg,"入帳號機關");
		alertStr += checkEmpty(form1.ownership,"權屬");
		alertStr += checkEmpty(form1.propertyNo,"財產編號");
		alertStr += checkEmpty(form1.serialNo,"財產分號");		
		alertStr += checkEmpty(form1.drawDate,"領狀日期");
		alertStr += checkDate(form1.drawDate,"領狀日期");
		alertStr += checkEmpty(form1.drawCause, "領狀原因");
		if (form1.drawCause.value=="99") {
			alertStr += checkEmpty(form1.drawCause1,"領狀原因--其他說明");
		} else {
			form1.drawCause1.value = "";
		}
		alertStr += checkEmpty(form1.drawName,"領狀人");
		alertStr += checkDate(form1.returnDate,"歸還日期");	
		alertStr += checkLen(form1.notes, "備註", 250)		
	}
	if(alertStr.length!=0){ alert(alertStr); return false; }
	beforeSubmit();
}
function queryOne(enterOrg,ownership,propertyNo,serialNo,serialNo1){
	form1.enterOrg.value=enterOrg;
	form1.ownership.value=ownership;
	form1.propertyNo.value=propertyNo;
	form1.serialNo.value=serialNo;
	form1.serialNo1.value=serialNo1;
	form1.state.value="queryOne";
	beforeSubmit();
	form1.submit();
}

function getDrawProof()
{
	var enterOrg = parse(form1.enterOrg.value);
	var ownership = parse(form1.ownership.value);
	if (enterOrg.length>0 && ownership.length>0) {
		window.open('untrt012f_lookup.jsp?enterOrg=' + enterOrg + '&ownership='+ ownership,'MyWindow','scrollbars=1, resizable=yes, status=yes, toolbar=no,menubar=no,width=750,height=450');
	} else {
		alert("請輸入帳號機關及權屬");
	}
}
</script>
</head>

<body topmargin="0" onLoad="whatButtonFireEvent('<%=obj.getState()%>');showErrorMsg('<%=obj.getErrorMsg()%>');">
<form id="form1" name="form1" method="post" onSubmit="return checkField()">

<!--Query區============================================================-->
<div id="queryContainer" style="width:500px;height:300px;display:none">
	<iframe id="queryContainerFrame"></iframe>
	<div class="queryTitle">查詢視窗</div>
	<table class="queryTable"  border="1">
	<tr>
		<td class="queryTDLable">權屬：</td>
		<td class="queryTDInput">
			<select class="field_Q" type="select" name="q_ownership">
			<%=util.View.getOnwerOption(obj.getQ_ownership())%>
			</select>
			<input class="field_Q" type="hidden" name="q_enterOrg" value="<%=user.getOrganID()%>">
		</td>
	</tr>
	<tr>
		<td class="queryTDLable">財產編號：</td>
		<td class="queryTDInput">
			<%=util.View.getPopProperty("field_Q","q_propertyNo",obj.getQ_propertyNo(),obj.getQ_propertyNoName(),"8")%>
		</td>
	</tr>
	<tr>
		<td class="queryTDLable">財產分號起：</td>
		<td class="queryTDInput">
			起<input class="field_Q" type="text" name="q_serialNoS" size="7" maxlength="7" value="<%=obj.getQ_serialNoS()%>" onChange="addChar(this ,7);">&nbsp;~&nbsp;
			訖<input class="field_Q" type="text" name="q_serialNoE" size="7" maxlength="7" value="<%=obj.getQ_serialNoE()%>" onChange="addChar(this ,7);">
		</td>
	</tr>
	<tr>
		<td class="queryTDLable">領狀日期：</td>
		<td class="queryTDInput">
			起<%=util.View.getPopCalndar("field_Q","q_drawDateS",obj.getQ_drawDateS())%>&nbsp;~&nbsp;
			訖<%=util.View.getPopCalndar("field_Q","q_drawDateE",obj.getQ_drawDateE())%>
		</td>
	</tr>
	<tr>
		<td class="queryTDLable">領狀人：</td>
		<td class="queryTDInput">
			<input name="q_drawName" type="text" class="field_Q" value="<%=obj.getQ_drawName()%>" size="12" maxlength="20">
		</td>
	</tr>
	<tr>
		<td class="queryTDLable">領狀原因：</td>
		<td class="queryTDInput">
			<select class="field_Q" type="select" name="q_drawCause">
			<%=util.View.getOption("select codeID, codeName from SYSCA_Code where codeKindID='DCA' ", obj.getQ_drawCause())%>
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
	  <td class="td_form"><font color="red">*</font>權屬：</td>
	  <td class="td_form_white"><select class="field_P" type="select" name="ownership">
          <%=util.View.getOnwerOption(obj.getOwnership())%>
        </select>
          <input type="hidden" name="enterOrg" value="<%=obj.getEnterOrg()%>">      </td>
		<td class="td_form">領狀次序：</td>
		<td class="td_form_white"> [
            <input class="field_PRO" type="text" name="serialNo1" size="3" maxlength="3" value="<%=obj.getSerialNo1()%>">
  ] </td>
	</tr>
	<tr>
		<td class="td_form"><font color="red">*</font>財產編號：</td>
		<td colspan="3" class="td_form_white">
			<%=util.View.getPopProperty("field_P","propertyNo",obj.getPropertyNo(),obj.getPropertyNoName(),"8")%>
 　分號：            
 <input class="field_P" type="text" name="serialNo" size="7" maxlength="7" value="<%=obj.getSerialNo()%>"> <input class="field_P" type="button" name="btnDrawProof" onClick="getDrawProof();" value="證明書查詢" title="輸入輔助">
 <br>
 原財產編號：[
 <input class="field_RO" type="text" name="oldPropertyNo" size="10" maxlength="10" value="<%=obj.getOldPropertyNo()%>">
]
 　分號：[
<input class="field_RO" type="text" name="oldSerialNo" size="7" maxlength="7" value="<%=obj.getOldSerialNo()%>">
]</td>
		</tr>
	<tr>
	  <td class="td_form"><font color="red">*</font>領狀日期：</td>
	  <td class="td_form_white"><%=util.View.getPopCalndar("field","drawDate",obj.getDrawDate())%> </td>
		<td class="td_form"><font color="red">*</font>領狀人：</td>
		<td class="td_form_white"><input class="field" type="text" name="drawName" size="20" maxlength="20" value="<%=obj.getDrawName()%>">        </td>
	</tr>
	<tr>
	  <td class="td_form"><font color="red">*</font>領狀原因：</td>
	  <td colspan="3" class="td_form_white">
			<select class="field" type="select" name="drawCause">
			<%=util.View.getOption("select codeID, codeName from SYSCA_Code where codeKindID='DCA' ", obj.getDrawCause())%>
			</select>
	    　其他說明：
	      <input class="field" type="text" name="drawCause1" size="20" maxlength="20" value="<%=obj.getDrawCause1()%>">        </td>
		</tr>
	<tr>
	  <td class="td_form">歸還日期：</td>
	  <td colspan="3" class="td_form_white"><%=util.View.getPopCalndar("field","returnDate",obj.getReturnDate())%> </td>
	  </tr>
	<tr>
	  <td class="td_form">備註：</td>
	  <td colspan="3" class="td_form_white"><textarea class="field" name="notes" cols="45" rows="4"><%=obj.getNotes()%></textarea></td>
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
</td></tr>

<!--List區============================================================-->
<tr><td class="bg">
<div id="listContainer">
<table class="table_form" width="100%" cellspacing="0" cellpadding="0">
	<thead id="listTHEAD">
	<tr>
		<th class="listTH" ><a class="text_link_w" >NO.</a></th>
		<th class="listTH"><a class="text_link_w" onClick="return sortTable('listTBODY',1,false);" href="#">權屬</a></th>
		<th class="listTH"><a class="text_link_w" onClick="return sortTable('listTBODY',2,false);" href="#">領狀日期</a></th>
		<th class="listTH"><a class="text_link_w" onClick="return sortTable('listTBODY',3,false);" href="#">領狀人</a></th>
		<th class="listTH"><a class="text_link_w" onClick="return sortTable('listTBODY',4,false);" href="#">領狀原因</a></th>
		<th class="listTH"><a class="text_link_w" onClick="return sortTable('listTBODY',5,false);" href="#">歸還日期</a></th>
	</thead>
	<tbody id="listTBODY">
	<%
	boolean primaryArray[] = {false,false,false,false,false,true,true,true,true,true};
	boolean displayArray[] = {true,true,true,true,true,false,false,false,false,false};
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



