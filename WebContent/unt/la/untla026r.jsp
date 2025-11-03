<!--
*<br>程式目的：土地評定現值差額清冊查詢檔 
*<br>程式代號：untla026r
*<br>撰寫日期：94/11/14
*<br>程式作者：chris
*<br>--------------------------------------------------------
*<br>修改作者　　修改日期　　　修改目的
*<br>--------------------------------------------------------
-->
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../home/head.jsp" %>
<jsp:useBean id="obj" scope="request" class="unt.la.UNTLA026R">
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
//insertDefault = new Array(
//	new Array("q_signNo1","S000000")
//);
function checkField(){
    //return true;
	var alertStr="";
	alertStr += checkEmpty(form1.q_enterOrg,"入帳機關");
	alertStr += checkEmpty(form1.q_ownership,"權屬");
	alertStr += checkEmpty(form1.q_bulletinDate,"公告年月");
	if(alertStr.length!=0){ alert(alertStr); return false; }
	if(form1.q_bulletinDate.value.length<5){alert("公告年月YYYMM需填完整");return false;}
    //beforeSubmit();	
    //form1.submit();
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
<body topmargin="10" >

<form id="form1" name="form1" method="post" action="untla026p.jsp" onReset="check_reset();" onSubmit="return checkField()" target="_blank">
<table class="bg" width="70%" cellspacing="0" cellpadding="0" align="center"><tr><td>
	<table class="queryTable"  border="1">
	<tr>
        <td class="td_form" colspan="2" style="text-align:center">土地評定現值差額清冊<font color="red">(A4 直式)</font></td>
	</tr>
	<tr>
		<td class="queryTDLable"><font color="red">*</font>入帳機關：</td>
		<td class="queryTDInput">
			<%=util.View.getPopOrgan("field_Q","q_enterOrg",user.getOrganID(),user.getOrganName(),"N&js=alteredEnterOrg();")%>
			<input type="hidden" name="isAdminManager" value="<%=user.getIsAdminManager()%>">
			<input type="hidden" name="isOrganManager" value="<%=user.getIsOrganManager()%>">
			<input type="hidden" name="organID" value="<%=user.getOrganID()%>">
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
		<td class="queryTDLable"><font color="red">*</font>公告年月：</td>
		<td class="queryTDInput">
			<input class="field_Q" type="text" name="q_bulletinDate" size="5" maxlength="5" value="">(YYYMM)
		</td>
	</tr>
<tr>	
		<td class="queryTDLable">土地標示：</td>
		<td class="queryTDInput">
			<select class="field_Q" type="select" name="q_signNo1" onChange="changeSignNo1('q_signNo1','q_signNo2','','');">
			<%=util.View.getOption("select signNo, signName from SYSCA_Sign where signNo like '_000000' order by seqno","E000000")%>
			</select>
			<select class="field_Q" type="select" name="q_signNo2" >
				<script>changeSignNo1('q_signNo1','q_signNo2','','<%=obj.getQ_signNo2()%>');</script>
			</select>
					
		</td>
	</tr>
	<tr>
		<td class="queryTDLable">財產性質：</td>
		<td class="queryTDInput">
			<select class="field_Q" type="select" name="q_propertyKind">			
				<%=util.View.getOption("select codeID, codeName from SYSCA_Code where codeKindID='PKD' ",obj.getQ_propertyKind())%>
			</select>		
		</td>
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
		<td class="queryTDInput" colspan="2" style="text-align:center;">
			<input class="toolbar_default" followPK="false" type="submit" name="querySubmit" value="確　　定" >
			<input class="toolbar_default" followPK="false" type="reset" name="queryCannel" value="取　　消" >
		</td>
	</tr>
	</table>
</td></tr>
</table>	
</form>
</body>
</html>
