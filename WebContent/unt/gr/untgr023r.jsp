<!--
*<br>程式目的：財產減損月報總表
*<br>程式代號：untgr023r
*<br>撰寫日期：105/11/18
*<br>程式作者：Jim.Lin
*<br>--------------------------------------------------------
*<br>修改作者　　修改日期　　　修改目的
*<br>--------------------------------------------------------
-->
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../home/head.jsp" %>
<jsp:useBean id="obj" scope="request" class="unt.gr.UNTGR023R">
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
	alertStr += checkEmpty(form1.q_enterorg,"入帳機關");
	alertStr += checkEmpty(form1.q_ownership,"權屬");
    alertStr += checkEmpty(form1.q_differencekind,"財產區分別");
    alertStr += checkEmpty(form1.q_year,"年度");
    alertStr += checkEmpty(form1.q_month,"月份");
    alertStr += checkNumber(form1.q_year,"年度");
    alertStr += checkNumber(form1.q_month,"月份");
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
<form id="form1" name="form1" method="post" action="untgr023p.jsp" onReset="check_reset();" onSubmit="return checkField()" target="_blank">
<table class="bg" width="60%" cellspacing="0" cellpadding="0" align="center"><tr><td>
	<table class="queryTable"  border="1">
	<input class="field_P" type="hidden" name="userName" value="<%=user.getUserName()%>">
	<input class="field_P" type="hidden" name="q_verify" value="Y">
	<input class="field_P" type="hidden" name="q_valuable" value="N">
	<tr>
        <td class="td_form" colspan="4" style="text-align:center">財產減損月報總表<font color="red">(A4橫式)</font></td>
	</tr>
	<tr id="tr1" style="display:none">
        <td class="queryTDLable"><font color="red">*</font>入帳機關：</td>
        <td class="queryTDInput">
            <%=util.View.getPopOrgan("field_Q","q_enterorg",obj.getQ_enterorg().equals("")?user.getOrganID():obj.getQ_enterorg(),obj.getQ_enterorgName().equals("")?user.getOrganName():obj.getQ_enterorgName(),"N")%>
        </td>
    </tr>
	<tr>
        <td class="queryTDLable"><font color="red">*</font>權屬：</td>
        <td class="queryTDInput">
            <select class="field_Q" type="select" name="q_ownership">
                <%=util.View.getOnwerOption("".equals(obj.getQ_ownership())?"1":obj.getQ_ownership())%>         
            </select>
        </td>
	</tr>
	<tr>
        <td class="queryTDLable"><font color="red">*</font>財產區分別：</td>
        <td class="queryTDInput">
            <select class="field_Q" type="select" name="q_differencekind">
                <%=util.View.getOption("select codeID, codeName from SYSCA_Code where codeKindID='DFK' and codeID in ('110','111','120','112')" ,"")%>
            </select>
        </td>
    </tr>
    <tr>
        <td class="queryTDLable"><font color="red">*</font>年度：</td>
        <td class="queryTDInput">
        <input type="text" class="field_Q" name="q_year"  size="3" maxlength="3"onchange="this.value=lpad(this.value, 3, '0', true)"> 
        </td>
    </tr>
    <tr>
        <td class="queryTDLable"><font color="red">*</font>月份：</td>
        <td class="queryTDInput">
        <input type="text" class="field_Q" name="q_month"  size="2" maxlength="2" onchange="this.value=lpad(this.value, 2, '0', true)"> 
        </td>
    </tr>
   	<tr>
		<td class="queryTDLable"><font color="red">*</font>報表類型：</td>
		<td class="queryTDInput">
			<select class="field_Q" type="select" id="q_reportType" name="q_reportType">
				<%=View.getJasperReportFormatOption("Excel")%>
			</select>
		</td>
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
