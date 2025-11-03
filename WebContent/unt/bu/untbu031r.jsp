<!--
*<br>程式目的：未辦理保存登記建物清冊報表 
*<br>程式代號：untbu031
*<br>撰寫日期：94/10/25
*<br>程式作者：Chris
*<br>--------------------------------------------------------
*<br>修改作者　　修改日期　　　修改目的
*<br>--------------------------------------------------------
-->
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../home/head.jsp" %>






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
//	new Array("enterOrg","<%=user.getOrganID()%>")
//);
function checkField(){
	return true;
	//var alertStr="";
	//alertStr += checkEmpty(form1.enterOrg,"入帳機關");
	//if(alertStr.length!=0){ alert(alertStr); return false; }
		
}
</script>

</head>
<body topmargin="10" >

<form id="form1" name="form1" method="post" action="untbu031p.jsp" onSubmit="return checkField()" target="_blank">
<table class="bg" width="70%" cellspacing="0" cellpadding="0" align="center"><tr><td>
<table class="queryTable" width="100%">
	<tr>
        <td class="td_form" colspan="2" style="text-align:center"></font>未辦理保存登記建物清冊查詢列印<font color="red">(A4 橫式)</font></td>
	</tr>
	<tr><td class="queryTDLable">入帳機關：</td>
		<td class="queryTDInput">
			<%=util.View.getPopOrgan("field_Q","enterOrg",user.getOrganID(),user.getOrganName())%>
			<input type="hidden" name="isAdminManager" value="<%=user.getIsAdminManager()%>">
			<input type="hidden" name="isOrganManager" value="<%=user.getIsOrganManager()%>">
			<input type="hidden" name="organID" value="<%=user.getOrganID()%>">
			<input type="hidden" name="signNo" value="">
			
		</td></tr>
	<!-- toolbar-->
	<tr><td class="td_form" colspan="2" style="background-color:black"></td></tr>		
	<tr><td class="td_form" colspan="2" style="text-align:center">
		<input class="toolbar_default" type="submit" name="btn_ok"		value="確    定" >
		<input class="toolbar_default" type="reset"  name="btn_reset"	value="取    消" >
	</td></tr>	
	<tr><td class="td_form" colspan="2" style="background-color:black"></td></tr>
	<!-- end toolbar-->
</table>
</td></tr></table>	
</form>
</body>
</html>
