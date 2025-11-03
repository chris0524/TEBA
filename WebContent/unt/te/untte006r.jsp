<!--
*<br>程式目的：地價稅課稅統計表
*<br>程式代號：untte006r
*<br>撰寫日期：96/9/10
*<br>程式作者：blair
*<br>--------------------------------------------------------
*<br>修改作者　　修改日期　　　修改目的
*<br>--------------------------------------------------------
--> 
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../home/head.jsp" %>
<jsp:useBean id="obj" scope="request" class="unt.te.UNTTE006R">
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
//	new Array("enterOrg","<%=user.getOrganID()%>")
//);

function checkField(){
	var alertStr="";
	alertStr += checkEmpty(form1.q_lndYYY,"資料年度");
	if(alertStr.length!=0){ alert(alertStr); return false; }
	return true;	
}
</script>

</head>
<body topmargin="10" >

<form id="form1" name="form1" method="post" action="untte006p.jsp" onSubmit="return checkField()" target="_blank">
<table class="bg" width="70%" cellspacing="0" cellpadding="0" align="center"><tr><td>
<table class="queryTable"  border="1">
	<tr>
        <td class="td_form" colspan="2" style="text-align:center"></font>地價稅資料統計表<font color="red">(A4 直式)</font></td>
	</tr>
	<input type="hidden" name="isAdminManager" value="<%=user.getIsAdminManager()%>">
	<input type="hidden" name="isOrganManager" value="<%=user.getIsOrganManager()%>">
	<input type="hidden" name="organID" value="<%=user.getOrganID()%>">
	<input type="hidden" name="q_levyType" value="6">
	<tr>
		<td class="td_form" color="red" ><font color="red">*</font>資料年度</td>
		<td class="td_form_white">
			<input class="field" type="text" name="q_lndYYY" size="3" maxlength="3" value="<%=obj.getQ_lndYYY()%>">
		</td>		
	</tr>
	<tr>
		<td class="queryTDLable">土地標示：</td>		
		<td class="queryTDInput">
			<select class="field_Q" type="select" name="q_signNo1" onChange="changeSignNo1('q_signNo1','q_signNo2','q_signNo3','');">
			<%=util.View.getOption("select signNo, signName from SYSCA_Sign where signNo like '_000000' order by seqno",obj.getQ_signNo1())%>
			</select>
			<select class="field_Q" type="select" name="q_signNo2" onChange="changeSignNo2('q_signNo1','q_signNo2','q_signNo3','');">
				<script>changeSignNo1('q_signNo1','q_signNo2','q_signNo3','<%=obj.getQ_signNo2()%>');</script>
			</select>				
		</td>
	</tr>
	
	<!-- toolbar-->
	<tr><td class="td_form" colspan="2" style="background-color:black"></td></tr>		
	<tr><td class="td_form" colspan="2" style="text-align:center">
		<input class="toolbar_default" type="submit" name="btn_ok"		value="確　　定" >
		<input class="toolbar_default" type="reset"  name="btn_reset"	value="取　　消" >
	</td></tr>	
	<tr><td class="td_form" colspan="2" style="background-color:black"></td></tr>
	<!-- end toolbar-->
</table>
</td></tr></table>	
</form>
</body>
</html>
