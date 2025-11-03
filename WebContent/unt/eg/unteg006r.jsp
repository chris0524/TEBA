<!--
*<br>程式目的：園區公共設施建設費總額報表檔  
*<br>程式代號：
*<br>撰寫日期：
*<br>程式作者：
*<br>--------------------------------------------------------
*<br>修改作者　　修改日期　　　修改目的
*<br>--------------------------------------------------------
-->
<%@page import="org.syntax.jedit.InputHandler.document_end"%>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../home/head.jsp" %>
<jsp:useBean id="obj" scope="request" class="unt.eg.UNTEG006R">
	<jsp:setProperty name="obj" property="*"/>
</jsp:useBean>
<%
if("".equals(Common.checkGet(obj.getEnterOrg()))){
	obj.setEnterOrg(user.getOrganID());
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
<script type="text/javascript" src="../../js/validate.js"></script>
<script type="text/javascript" src="../../js/function.js"></script>
<script type="text/javascript" src="../../js/tablesoft.js"></script>
<script type="text/javascript" src="../../js/jquery-1.8.2.js"></script>
<script type="text/javascript" src="../../js/TJS.js"></script>
<script type="text/javascript" src="../../js/DataCouplingCtrl.js?v=2.0"></script>
<script type="text/javascript">

function checkField(){
	var alertStr="";
		alertStr += checkEmpty(form1.q_enterOrg,"入帳機關");
		alertStr += checkDate(form1.q_enterDateS,"入帳日期－起");
		alertStr += checkDate(form1.q_enterDateE,"入帳日期－訖");
		alertStr += checkEmpty(form1.q_enterDateS,"入帳日期－起");
		alertStr += checkEmpty(form1.q_enterDateE,"入帳日期－訖");
	
	if(alertStr.length!=0){ alert(alertStr); return false; }
	return true;
}

function init(){
	var dcc1 = new DataCouplingCtrlPlus(form1.q_enterOrg, form1.q_unitidQuickly, form1.q_unitid,null,null,false,false);

	
	//入帳機關：登入者為系統管理人員(SYSAP_EMP.isadminmanager= 'Y')才顯示，其他人員則隱藏。
	if(form1.isAdminManager.value=='Y'){		
		document.getElementById('tr1').style.display="";
	}
	//組室別：登入者為系統管理人員(SYSAP_EMP.isadminmanager= 'Y') or 財產管理人員(SYSAP_EMP.roleid = '3')才顯示，其他人員則隱藏。
	if (form1.isAdminManager.value=='Y' || form1.roleID.value=='3') {
		document.getElementById('tr2').style.display="";
	}
}

</script>

</head>
<body topmargin="10" onLoad="init();closebar();">
<form id="form1" name="form1" method="post" action="unteg006p.jsp" onSubmit="return checkField()" target="_blank">
<table class="bg" width="70%" cellspacing="0" cellpadding="0" align="center"><tr><td>
	<div>
	<table class="queryTable"  border="1">
	<tr>
        <td class="td_form" colspan="2" style="text-align:center">
        	園區公共設施建設費總額<font color="red">(A4 橫式)</font>
        </td>
	</tr>
	<tr id="tr1" style="display:none">
		<td class="queryTDLable"><font color="red">*</font>入帳機關：</td>
		<td class="queryTDInput">
			<%=util.View.getPopOrgan("field_Q","q_enterOrg",user.getOrganID(),user.getOrganName())%>
		</td>
	</tr>
	<tr>
		<td class="queryTDLable"><font color="red">*</font>入帳日期：</td>
		<td class="queryTDInput">
			起<%=util.View.getPopCalndar("field_Q","q_enterDateS",obj.getQ_enterDateS())%>&nbsp;~&nbsp;
			訖<%=util.View.getPopCalndar("field_Q","q_enterDateE",obj.getQ_enterDateE())%>
		</td>
	</tr>
	<tr id="tr2" style="display:none">
		<td class="td_form">組室別：</td>
		<td class="queryTDInput">
			<%=View.getSelectCtrlGroup("select unitno, unitname from UNTMP_KEEPUNIT where enterorg = '" + obj.getEnterOrg() + "' order by unitno ", 
				                                                       "field", "form1", "q_unitid", "q_unitidQuickly", obj.getQ_unitid()) %>
			<input class="field" type="button" name="btn_q_unitid" onclick="popUnitNo(form1.organID.value,'q_unitid')" value="..." title="單位輔助視窗">
	   </td>
	</tr>
	</div>
	<tr>
		<td class="queryTDInput" colspan="2" style="text-align:center;">
			<input class="toolbar_default" followPK="false" type="submit" name="querySubmit" value="確　　定" >
			<input class="toolbar_default" followPK="false" type="reset" name="queryCannel" value="取　　消" >
			
			<input type="hidden" name="isAdminManager" value="<%=user.getIsAdminManager()%>">
			<input type="hidden" name="isOrganManager" value="<%=user.getIsOrganManager()%>">
			<input type="hidden" name="organID" value="<%=user.getOrganID()%>">
			<input type="hidden" name="roleID" value="<%=user.getRoleid()%>">
			<input type="hidden" name="editID" value="<%=session.getAttribute("editID")%>">
		</td>
	</tr>
	</table>
</td></tr></table>	
</form>
</body>
</html>
