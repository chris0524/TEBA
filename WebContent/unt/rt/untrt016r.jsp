<!--
*<br>程式目的：權利增減表報表檔 
*<br>程式代號：untrt016r
*<br>撰寫日期：94/11/23
*<br>程式作者：Cherry
*<br>--------------------------------------------------------
*<br>修改作者　　修改日期　　　修改目的
*<br>--------------------------------------------------------
-->
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../home/head.jsp" %>
<jsp:useBean id="obj" scope="request" class="unt.rt.UNTRT016R">
	<jsp:setProperty name="obj" property="*"/>
</jsp:useBean>

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
<script language="javascript">

var insertDefault;	//二維陣列, 新增時, 設定預設值
function checkField(){
	var alertStr="";
	alertStr += checkDate(form1.q_enterDateS,"異動日期-起");
	alertStr += checkDate(form1.q_enterDateE,"異動日期-訖");
	alertStr += checkEmpty(form1.q_enterDateS,"異動日期-起");
	alertStr += checkEmpty(form1.q_enterDateE,"異動日期-訖");
	alertStr += checkEmpty(form1.q_enterOrg,"管理機關");
	if(alertStr.length!=0){ 
		alert(alertStr); 
		return false; 
	}else{
    	form1.action="untrt016p.jsp";
		return true;
	}
}


function changeSelect(){
	//財產性質為「03:事業用」時，須控制「基金財產」資料
	if(form1.q_propertyKind.value == "03") document.all.q_fundType.disabled=false;
	else{
		document.all.q_fundType.disabled=true;
		form1.q_fundType.value="";
	}
}

function alteredEnterOrg(){
	changeEnterOrg_FundType(form1.q_enterOrg.value,'<%=user.getOrganID()%>','q_fundType','','<%=user.getIsAdminManager()%>','<%=user.getIsOrganManager()%>');
}

function check_reset(){
	form1.q_enterOrg.value='<%=user.getOrganID()%>';
	alteredEnterOrg();
	form1.q_propertyKind.value='';
	changeSelect();
}

</script>

</head>
<body topmargin="10" >

<form id="form1" name="form1" method="post" action="untrt016p.jsp" onReset="check_reset();" onSubmit="return checkField()" target="_blank">
<input type="hidden" name="userID" value="<%=user.getUserID()%>">
<input type="hidden" name="organID" value="<%=user.getOrganID()%>">
<input type="hidden" name="isOrganManager" value="<%=user.getIsOrganManager()%>">
<input type="hidden" name="isAdminManager" value="<%=user.getIsAdminManager()%>">			
<table class="bg" width="60%" cellspacing="0" cellpadding="0" align="center"><tr><td>
	<table class="queryTable"  border="1">
	<tr>
        <td class="td_form" colspan="2" style="text-align:center">權利增減表 <font color="red">(A4 橫式)</font></td>
	</tr>
	<tr>
		<td class="queryTDLable" ><font color="red">*</font>管理機關：</td>
		<td class="queryTDInput" >
			<%=util.View.getPopOrgan("field_Q","q_enterOrg",obj.getQ_enterOrg().equals("")?user.getOrganID():obj.getQ_enterOrg(),obj.getQ_enterOrgName().equals("")?user.getOrganName():obj.getQ_enterOrgName(),"N&js=alteredEnterOrg();")%>
		</td>
	</tr>
	<tr>
		<td class="queryTDLable"><div align="right"><font color="red">*</font>權屬：</div></td>
 		<td class="queryTDInput">
 			<select class="field_Q" type="select" name="q_ownership">
 				<option value='1'>市有</option><option value='2'>國有</option>
			</select>
		</td>
	</tr>
	<tr>
		<td class="queryTDLable">財產性質：</td>
		<td class="queryTDInput">
			<select class="field_Q" type="select" name="q_propertyKind" onChange="changeSelect();">			
				<%=util.View.getOption("select codeID, codeName from SYSCA_Code where codeKindID='PKD' ",obj.getQ_propertyKind())%>
			</select>		
		</td>			
	</tr>
	<tr>
		<td class="queryTDLable">基金財產：</td>
		<td class="queryTDInput" colspan="4">
			<select class="field_Q" type="select" name="q_fundType" disabled=true>
				<script>changeEnterOrg_FundType(form1.q_enterOrg.value,'<%=user.getOrganID()%>','q_fundType','<%=obj.getQ_fundType()%>','<%=user.getIsAdminManager()%>','<%=user.getIsOrganManager()%>');</script>
			</select>
		</td>		
	</tr>
	<tr>
		<td class="queryTDLable"><div align="right"><font color="red">*</font>異動日期：</div></td>
		<td class="queryTDInput" colspan="4">
			起<%=util.View.getPopCalndar("field_Q","q_enterDateS",obj.getQ_enterDateS())%>&nbsp;~&nbsp;
			訖<%=util.View.getPopCalndar("field_Q","q_enterDateE",obj.getQ_enterDateE())%>
		</td>
	</tr>
		<!--審核：-->
		<input type="hidden" name="q_verify" value="Y">
	<tr>
		<td class="queryTDInput" colspan="2" style="text-align:center;">
			<input class="toolbar_default" followPK="false" type="submit" name="querySubmit" value="確　　定" >
			<input class="toolbar_default" followPK="false" type="reset" name="queryCannel" value="取　　消" >
		</td>
	</tr>
	</table>
</td></tr></table>	
</form>
</body>
</html>
