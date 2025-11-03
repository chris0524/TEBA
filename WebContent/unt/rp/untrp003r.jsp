<!--
*<br>程式目的：財產移交清冊
*<br>程式代號：
*<br>撰寫日期：
*<br>程式作者：
*<br>--------------------------------------------------------
*<br>修改作者　　修改日期　　　修改目的
*<br>--------------------------------------------------------
-->
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../home/head.jsp" %>
<jsp:useBean id="obj" scope="request" class="unt.rp.UNTRP003R">
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
		alertStr += checkEmpty(form1.q_ownership,"權屬");
		alertStr += checkDate(form1.q_writeDateS,"填單日期－起");
		alertStr += checkDate(form1.q_writeDateE,"填單日期－訖");
	
	if(alertStr.length!=0){ alert(alertStr); return false; }
	return true;
}

function init(){
	var dcc2 = new DataCouplingCtrlPlus(form1.q_enterOrg, form1.keeperQuickly, form1.keeper, null, null, false, false);
	if ('<%=user.getRoleid()%>' == '1'){		
		form1.btn_keeper.disabled = true;		
		readbg="#EEEEEE";
		setFormItem("keeperQuickly","R");		
	}
}
</script>

</head>
<body topmargin="10" onLoad="init();closebar();">
<form id="form1" name="form1" method="post" action="untrp003p.jsp" onSubmit="return checkField()" target="_blank">
<table class="bg" width="70%" cellspacing="0" cellpadding="0" align="center"><tr><td>
	<table class="queryTable"  border="1">
	<tr>
        <td class="td_form" colspan="2" style="text-align:center">
        	財產移交清冊<font color="red">(A4 橫式)</font>
        </td>
	</tr>
	<tr>
		<td class="queryTDLable"><font color="red">*</font>入帳機關：</td>
		<td class="queryTDInput">
			<%=util.View.getPopOrgan("field_Q","q_enterOrg",user.getOrganID(),user.getOrganName())%>
		</td>
	</tr>
	<tr>
		<td class="queryTDLable">保管人：</td>
		<td class="queryTDInput">
		<%if("1".equals(user.getRoleid())){ %>
			<input type="hidden" name="keeper" value="<%=user.getKeeperno()%>">
			<input class="field_RO" type="text" name="keeperQuickly" value="<%=user.getUserName()%>" size="10">
			<input class="field_RO" type="button" name="btn_keeper" onclick="popUnitMan(form1.q_enterOrg.value,'keeper')" value="..." title="人員輔助視窗">			                                                       
		<%}else{ %>
			<%=View.getSelectCtrlGroup("select a.keeperno, CASE a.incumbencyyn WHEN 'N' THEN a.keepername + '(停用)' ELSE a.keepername END AS keepername from UNTMP_KEEPER a LEFT OUTER JOIN SYSAP_EMP b ON a.enterorg = b.organid AND a.keeperno = b.empid where 1=1 and enterorg = '" + Common.esapi(user.getOrganID()) + "' order by case a.incumbencyyn when 'Y' then 'Y' else 'N' end DESC,convert(varchar,keepername) ", 
			                                                       "field", "form1", "keeper", "keeperQuickly", obj.getKeeper()) %>
			<input class="field" type="button" name="btn_keeper" onclick="popUnitMan(form1.q_enterOrg.value,'keeper')" value="..." title="人員輔助視窗">			                                                       
		<%} %>
			
	        
		</td>
	</tr>
	<tr>
		<td class="queryTDLable"><font color="red">*</font>報表類型：</td>
		<td class="queryTDInput">
			<select class="field_Q" type="select" id="q_reportType" name="q_reportType">
				<%=View.getJasperReportFormatOption("PDF")%>
			</select>
		</td>
	</tr>
	
	<tr>
		<td class="queryTDInput" colspan="2" style="text-align:center;">
			<input class="toolbar_default" followPK="false" type="submit" name="querySubmit" value="確　　定" >
			<input class="toolbar_default" followPK="false" type="reset" name="queryCannel" value="取　　消" >
			
			<input type="hidden" name="isAdminManager" value="<%=user.getIsAdminManager()%>">
			<input type="hidden" name="isOrganManager" value="<%=user.getIsOrganManager()%>">
			<input type="hidden" name="organID" value="<%=user.getOrganID()%>">
			<input type="hidden" name="editID" value="<%=session.getAttribute("editID")%>">
		</td>
	</tr>
	</table>
</td></tr></table>	
</form>
</body>
</html>
