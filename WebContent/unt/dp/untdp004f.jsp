<!--
程式目的：
程式代號：
程式日期：
程式作者：
--------------------------------------------------------
修改作者　　修改日期　　　修改目的
--------------------------------------------------------
-->

<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%> 
<%@ include file="../../home/head.jsp" %>
<jsp:useBean id="obj" scope="request" class="unt.dp.UNTDP004F">
	<jsp:setProperty name="obj" property="*"/>
</jsp:useBean>
<jsp:useBean id="objList" scope="page" class="java.util.ArrayList"/>

<%

if ("printAddProof1".equals(obj.getState())) {
	if(obj.checkUpdateError()){
		obj.printAddProof1();
	}
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
<script language="javascript" src="../../js/changeEnterOrg_FundType.js"></script>
<script language="javascript" src="../../js/changeEnterOrg_KeepUnit.js"></script>
<script language="javascript" src="../../js/sList2.js"></script>
<script language="javascript">
var submiting = false;

var insertDefault;	//二維陣列, 新增時, 設定預設值

insertDefault = new Array(
	
);
function getEnterDate() {

}

function checkField(){
	var alertStr="";
	if (submiting) {
		return false;
	} else {
		submiting = true;
	}
	alertStr += checkEmpty(form1.enterOrg,"入帳機關");
	alertStr += checkEmpty(form1.deprY,"折舊年月的年");
	alertStr += checkYYY(form1.deprY,"折舊年月的年");
	alertStr += checkEmpty(form1.deprM,"折舊年月的月");
	alertStr += checkMM(form1.deprM,"折舊年月的月");
	alertStr += checkEmpty(form1.differenceKind,"財產區分別");
	if(alertStr.length!=0){ 
		alert(alertStr); 
		submiting = false;
		return false; 
	}
	document.all.state.value='printAddProof1';	
	return true;
}

function queryOne(enterOrg,ownership,caseNo){

}

function clickApproveAll(){

}

function checkURL(surl){
	form1.action = surl;
	beforeSubmit();
	form1.submit();
}

function init(){
	var arrField = new Array("update","delete");
	if (form1.enterOrg.value!="<%=user.getOrganID()%>" ) {		
		setFormField(new Array("update","delete"),"R");
	}

	setFormItem("printAddProof1", "O");	
	document.all.state.value='init';

}

function loadUntch012r(){
	
}

function loadUntch013r(){
	
}
function checkID()
{
	if(form1.isAdminManager.value=='Y'){
	
		document.getElementById('tr1').style.display="";
	}	   
}
</script>
</head>

<body topmargin="0" onLoad="checkID();whatButtonFireEvent('<%=obj.getState()%>');init();showErrorMsg('<%=obj.getErrorMsg()%>');">
<form id="form1" name="form1" method="post" onSubmit="return checkField()">

<!--隱藏欄位區============================================================-->
<table style="display:none"><tr><td>
	<input type="hidden" name="state" value="<%=obj.getState()%>">
	<input type="hidden" name="queryAllFlag" value="<%=obj.getQueryAllFlag()%>">
	<input type="hidden" name="userID" value="<%=user.getUserID()%>">
	<input type="hidden" name="organID" value="<%=user.getOrganID()%>">
	<input type="hidden" name="isOrganManager" value="<%=user.getIsOrganManager()%>">
	<input type="hidden" name="isAdminManager" value="<%=user.getIsAdminManager()%>">
	<input type="hidden" name="verify" value="Y">
	
</td></tr></table>

<!--Query區============================================================-->

<!--頁籤區============================================================-->

<!--Form區============================================================-->
<table width="70%"  cellspacing="0" cellpadding="0" align="center">
<tr><td class="bg">
	<div>
	<table class="table_form" width="100%" height="100%">
		<tr id="tr1" style="display:none">
			<td class="queryTDLable" ><font color="red">*</font>入帳機關：</td>
	        <td class="queryTDInput">
		  	    <%=util.View.getPopOrgan("field_Q","enterOrg",user.getOrganID(),user.getOrganName(),"N")%>
		    </td>		
		</tr>
		<tr>
      <td class="queryTDLable"><font color="red">*</font>財產區分別 ：</td>
      <td class="queryTDInput"><select class="field_Q" type="select" name="differenceKind">
          <%=util.View.getOption("select codeID, codeName from SYSCA_Code where codeKindID='DFK' ",obj.getDifferenceKind())%>
        </select>      </td>
       </tr>
        <tr>
			<td class="queryTDLable"><font color="red">*</font>折舊年月：</td>
			<td class="queryTDInput">
				<input type="text" class="field_Q" name="deprY" value="<%=obj.getDeprY()%>" size="3" maxlength="3">
				年
				<input type="text" class="field_Q" name="deprM" value="<%=obj.getDeprM()%>" size="2" maxlength="2">
				月
			</td>
		<tr>
			<td class="queryTDInput" colspan="2" style="text-align:center;">
							
			</td>
		</tr>
		<tr>
			<td class="queryTDInput" colspan="2" style="text-align:center;">
				<input class="toolbar_default" type="submit" followPK="false" name="printAddProof1" value="取消入帳" >			
			</td>
		</tr>				
	</table>
	</div>
</td></tr>


<!--Toolbar區============================================================-->
<tr><td class="bg" style="text-align:center;display:none;">
	<jsp:include page="../../home/toolbar.jsp" />	
</td></tr>

<!--List區============================================================-->

</table>
</form>
</body>
</html>
