<!--
程式目的：建物及土地，土地標示資料(補登，修改)
程式代號：untdu006f
程式日期：0981222
程式作者：shan
--------------------------------------------------------
修改作者　　修改日期　　　修改目的
--------------------------------------------------------
-->

<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../home/head.jsp" %>
<jsp:useBean id="obj" scope="request" class="unt.du.UNTDU006F">
	<jsp:setProperty name="obj" property="*"/>
</jsp:useBean>
<jsp:useBean id="objList" scope="page" class="java.util.ArrayList"/>

<%
if ("mpUPcomputerType".equals(obj.getState())) {
	obj.mpUPcomputerType();
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

function checkField(){
	var alertStr="";

	if(form1.q_upType.value=="01"){
		alertStr += checkEmpty(form1.q_signNo1,"縣市");
		alertStr += checkEmpty(form1.q_signNo2,"鄉鎮市區");
		alertStr += checkEmpty(form1.q_signNo3,"地段");
		alertStr += checkEmpty(form1.q_signNo4,"地/建號");
	}else if(form1.q_upType.value=="02"){
		if(form1.q_signNo1.value==""){
			if(form1.q_signNo4.value==""){
				
			}else{
				alert("請輸入縣市、鄉鎮市區、地段");
				return false; 
			}
		}else{
			alertStr += checkEmpty(form1.q_signNo1,"縣市");
			alertStr += checkEmpty(form1.q_signNo2,"鄉鎮市區");
			alertStr += checkEmpty(form1.q_signNo3,"地段");
			alertStr += checkEmpty(form1.q_signNo4,"地/建號");
		}
	}

	alertStr += checkEmpty(form1.q_upType,"作業選擇");
	alertStr += checkEmpty(form1.q_enterOrg,"入帳機關");
	alertStr += checkEmpty(form1.q_ownership,"權屬");
	//alertStr += checkEmpty(form1.q_caseNo,"電腦單號");
	alertStr += checkEmpty(form1.q_propertyNo,"財產編號");
	alertStr += checkEmpty(form1.q_serialNo,"財產分號");

	
	if(alertStr.length!=0){ 
		alert(alertStr);
	 	return false; 
	 }else{
		document.all('state').value='mpUPcomputerType';
	 }
}

</script>
</head>

<body topmargin="0" onLoad="showErrorMsg('<%=obj.getErrorMsg()%>');">
<form id="form1" name="form1" method="post" onSubmit="return checkField()">

<input type="hidden" name="state" value="<%=obj.getState()%>">

<table class="bg" width="60%" cellspacing="0" cellpadding="0" align="center"><tr><td>
<table class="queryTable"  border="1">
	<tr>
        <td class="td_form" colspan="4" style="text-align:center">建物及土地，土地標示資料(補登，修改)</td>
	</tr>
	<tr>
		<td class="queryTDLable"><font color="red">*</font>作業選擇：</td>
		<td class="queryTDInput">
			<select class="field_Q" type="select" name="q_upType" >
			<%=util.View.getTextOption("01;土地資料;02;建物資料", obj.getQ_upType())%>
			</select>
		</td>
	</tr>
	<tr>
		<td class="queryTDLable"><font color="red">*</font>入帳機關：</td>
		<td class="queryTDInput">
			<%=util.View.getPopOrgan("field_Q","q_enterOrg",obj.getQ_enterOrg(),obj.getQ_enterOrgName(),"N")%>
		</td>
	</tr>
	<tr>
		<td class="queryTDLable"><font color="red">*</font>權屬：</td>
		<td class="queryTDInput">
			<select class="field_QRO" type="select" name="q_ownership">
				<%=util.View.getOnwerOption(obj.getQ_ownership())%>			
			</select>	
		</td>
	</tr>
	<!--tr>
		<td class="queryTDLable"><font color="red">*</font>電腦單號：</td>
		<td class="queryTDInput">
		<input class="field_Q" type="text" name="q_caseNo" size="12" maxlength="10" value="<%=obj.getQ_caseNo()%>">
		</td>
	</tr-->
	<tr>
		<td class="queryTDLable"><font color="red">*</font>財產編號：</td>
		<td class="queryTDInput">
			<%=util.View.getPopProperty("field_Q","q_propertyNo",obj.getQ_propertyNo(),obj.getQ_propertyNoName(),"1,2")%>
		</td>
	</tr>
	<tr>
		<td class="queryTDLable"><font color="red">*</font>財產分號：</td>
		<td class="queryTDInput">
			<input class="field_Q" type="text" name="q_serialNo" size="7" maxlength="7" value="<%=obj.getQ_serialNo()%>" onChange="addChar(this ,7);">
		</td>
	</tr>
	<tr>
		<td class="queryTDLable"><font color="red">*</font>土地/建物標示：</td>		
		<td class="queryTDInput" colspan="3">
			<select class="field_Q" type="select" name="q_signNo1" onChange="changeSignNo1('q_signNo1','q_signNo2','q_signNo3','');">
			<%=util.View.getOption("select signNo, signName from SYSCA_Sign where signNo like '_000000' order by seqno",obj.getQ_signNo1())%>
			</select>
			<select class="field_Q" type="select" name="q_signNo2" onChange="changeSignNo2('q_signNo1','q_signNo2','q_signNo3','');">
				<script>changeSignNo1('q_signNo1','q_signNo2','q_signNo3','<%=obj.getQ_signNo2()%>');</script>
			</select>		
			<select class="field_Q" type="select" name="q_signNo3">
				<script>changeSignNo2('q_signNo1','q_signNo2','q_signNo3','<%=obj.getQ_signNo3()%>');</script>
			</select>
			&nbsp;地/建號：
			<input class="field_Q" type="text" name="q_signNo4" size="8" maxlength="8" value="<%=obj.getQ_signNo4()%>">

		</td>
	</tr>
	<td class="queryTDInput" colspan="4" style="text-align:center;">
		<input class="toolbar_default" followPK="false" type="submit" name="querySubmit" value="確　　定" >
		<input class="toolbar_default" followPK="false" type="reset" name="queryCannel" value="取　　消" >
	</td>
</table>
</table>	
</form>
</body>
</html>



