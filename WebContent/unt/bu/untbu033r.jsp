<!--
*<br>程式目的：建物持份面積與使用面積不符差異明細表查詢檔 
*<br>程式代號：untbu033r
*<br>撰寫日期：94/12/14
*<br>程式作者：chris
*<br>--------------------------------------------------------
*<br>修改作者　　修改日期　　　修改目的
*<br>--------------------------------------------------------
-->
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../home/head.jsp" %>
<jsp:useBean id="obj" scope="request" class="unt.bu.UNTBU033R">
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

<script language="javascript">
//var insertDefault;	//二維陣列, 新增時, 設定預設值
//insertDefault = new Array(
//	new Array("kind","4")
//);
function checkField(){

    
	var alertStr="";
	alertStr += checkEmpty(form1.q_balanceDate,"製表年月");
	alertStr += checkEmpty(form1.q_enterOrg,"入帳機關");
	alertStr += checkEmpty(form1.q_ownership,"權屬");
	if(alertStr.length!=0){ alert(alertStr); return false; }
	if(form1.q_balanceDate.value.length<5){alert("製表年月YYYMM需填完整");return false;}
	return true;
		
}
</script>

</head>
<body topmargin="10" >

<form id="form1" name="form1" method="post" action="untbu033p.jsp" onSubmit="return checkField()" target="_blank">
<table class="bg" width="70%" cellspacing="0" cellpadding="0" align="center"><tr><td>
	<table class="queryTable"  border="1">
	<tr>
        <td class="td_form" colspan="2" style="text-align:center"></font>建物持份面積與使用面積不符差異明細表<font color="red">(A4 橫式)</font></td>
	</tr>
	<tr>
		<td class="queryTDLable"><font color="red">*</font>製表年月：</td>
		<td class="queryTDInput">
			<input class="field_Q" type="text" name="q_balanceDate" size="5" maxlength="5" value="<%=obj.getQ_balanceDate()%>">(YYYMM)
		</td>
	</tr>
	<tr>
		<td class="queryTDLable"><font color="red">*</font>入帳機關：</td>
		<td class="queryTDInput">
			<%=util.View.getPopOrgan("field_Q","q_enterOrg",user.getOrganID(),user.getOrganName())%>
			<input type="hidden" name="isAdminManager" value="<%=user.getIsAdminManager()%>">
			<input type="hidden" name="isOrganManager" value="<%=user.getIsOrganManager()%>">
			<input type="hidden" name="organID" value="<%=user.getOrganID()%>">
			<input type="hidden" name="q_verify" value="Y">
		</td>
	</tr>
	<tr>
		<td class="queryTDLable"><font color="red">*</font>權屬：</td>
		<td class="queryTDInput">
			<select class="field_Q" type="select" name="q_ownership">

            <option value='1' >市有</option>
            <option value='2' >國有</option>

			</select>
		</td>
	</tr>
	<tr>
		<td class="queryTDLable">財產性質：</td>
		<td class="queryTDInput">
			<select class="field_Q" type="select" name="q_propertyKind">
			<option value='' >請選擇</option>
			<option value='00' >公用</option>
			<option value='01' >公務用</option>
			<option value='02' >公共用</option>
			<option value='03' >事業用</option>
			<option value='04' >非公用</option>
			</select>
		</td>
	</tr>
	<tr>
		<td class="queryTDLable">縣市：</td>
		<td class="queryTDInput">
			<select class="field_Q" type="select" name="q_signNo1">
			<option value='' >請選擇</option>
			<option value='1' >彰化縣</option>
			<option value='2' >外縣市</option>
			</select>
		</td>
	</tr>
	<tr>
	<td class="queryTDLable">抵繳遺產稅：</td>
		<td class="queryTDInput">
			<select class="field_Q" type="select" name="q_taxCredit">
			<%=util.View.getYNOption(obj.getQ_taxCredit())%>
			</select>
		</td>
	</tr>
	
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
