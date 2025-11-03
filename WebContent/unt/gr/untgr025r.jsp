<!--
*<br>程式目的：毀損處理清冊 
*<br>程式代號：untgr025r
*<br>撰寫日期：95/7/11
*<br>程式作者：Cherry
*<br>--------------------------------------------------------
*<br>修改作者　　修改日期　　　修改目的
*<br>--------------------------------------------------------
-->
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../home/head.jsp" %>
<jsp:useBean id="obj" scope="request" class="unt.gr.UNTGR025R">
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
//var insertDefault;	//二維陣列, 新增時, 設定預設值
function checkField() {
	var alertStr="";
	alertStr += checkEmpty(form1.q_enterOrg,"入帳機關");
	alertStr += checkEmpty(form1.q_ownership,"權屬");
	alertStr += checkEmpty(form1.q_damageExpireS,"毀損報局屆滿日-起");	
	alertStr += checkEmpty(form1.q_damageExpireE,"毀損報局屆滿日-訖");		
	alertStr += checkDate(form1.q_damageExpireS,"毀損報局屆滿日-起");	
	alertStr += checkDate(form1.q_damageExpireE,"毀損報局屆滿日-訖");			
	if(alertStr.length!=0){ alert(alertStr); return false; }
}

function alteredEnterOrg(){
	changeEnterOrg_FundType(form1.q_enterOrg.value,'<%=user.getOrganID()%>','q_fundType','','<%=user.getIsAdminManager()%>','<%=user.getIsOrganManager()%>');
}

function check_reset(){
	form1.q_enterOrg.value='<%=user.getOrganID()%>';
	alteredEnterOrg();
}

</script>
</head>
<body topmargin="10">
<form id="form1" name="form1" method="post" action="untgr025p.jsp" onReset="check_reset();" onSubmit="return checkField()" target="_blank">
<table class="bg" width="60%" cellspacing="0" cellpadding="0" align="center"><tr><td>
	<table class="queryTable"  border="1">
	<tr>
        <td class="td_form" colspan="4" style="text-align:center">毀損處理清冊<font color="red">(A4橫式)</font></td>
	</tr>
	<tr>
		<td class="queryTDLable"><font color="red">*</font>入帳機關：</td>
		<td class="queryTDInput">
			<%=util.View.getPopOrgan("field_Q","q_enterOrg",obj.getQ_enterOrg().equals("")?user.getOrganID():obj.getQ_enterOrg(),obj.getQ_enterOrgName().equals("")?user.getOrganName():obj.getQ_enterOrgName(),"N&js=alteredEnterOrg();")%>
			<input type="hidden" name="organID" value="<%=user.getOrganID()%>">
			<input type="hidden" name="isOrganManager" value="<%=user.getIsOrganManager()%>">
			<input type="hidden" name="isAdminManager" value="<%=user.getIsAdminManager()%>">			
			<input type="hidden" name="q_verify" value="Y">
			<input class="field_P" type="hidden" name="editID" value="<%=user.getUserID()%>">
		</td>
	</tr>
	<tr>
		<td class="queryTDLable"><font color="red">*</font>權屬：</td>
		<td class="queryTDInput">
			<select class="field" type="select" name="q_ownership">
	            <option value='1'>市有</option>
	            <option value='2'>國有</option>
			</select>
		</td>
	</tr>
	<tr>
      <td class="queryTDLable">財產性質：</td>
      <td class="queryTDInput"><select class="field_Q" type="select" name="q_propertyKind">
      	<%=util.View.getTextOption("00;公用;01;公務用;02;公共用;03;事業用;04;非公用", obj.getQ_propertyKind())%>
      </select></td>
	</tr>
	<tr>
		<td class="queryTDLable">基金財產：</td>
		<td class="queryTDInput">
		    <select class="field_Q" type="select" name="q_fundType">
			  <script>changeEnterOrg_FundType(form1.q_enterOrg.value,'<%=user.getOrganID()%>','q_fundType','<%=obj.getQ_fundType()%>','<%=user.getIsAdminManager()%>','<%=user.getIsOrganManager()%>');</script>
			</select>
		</td>	
	</tr>	
	<tr>
		<td class="queryTDLable"><font color="red">*</font>毀損報局屆滿日：</td>
		<td class="queryTDInput">
			起<%=util.View.getPopCalndar("field_Q","q_damageExpireS",obj.getQ_damageExpireS())%>&nbsp;~&nbsp;
			訖<%=util.View.getPopCalndar("field_Q","q_damageExpireE",obj.getQ_damageExpireE())%>
		</td>		
	</tr>										
	<tr>
      <td class="queryTDLable">財產類別：</td>
      <td class="queryTDInput"><select class="field_Q" type="select" name="q_propertyType">
      	<%=util.View.getTextOption("1;建物;2;土地改良物;3;動產;4;非消耗品", obj.getQ_propertyType())%>
      </select></td>
	</tr>	
	<tr>
		<td class="queryTDInput" colspan="4" style="text-align:center;">
			<input class="toolbar_default" followPK="false" type="submit" name="querySubmit" value="確　　定">
			<input class="toolbar_default" followPK="false" type="reset" name="queryCannel" value="取　　消">
		</td>
	</tr>
	</table>
</td></tr></table>	
</form>
</body>
</html>
