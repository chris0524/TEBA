<!--
*<br>程式目的：財產折舊月報總表(基金財產)
*<br>程式代號：untdp026r
*<br>撰寫日期：105/04/13
*<br>程式作者：Jim.Lin
*<br>--------------------------------------------------------
*<br>修改作者　　修改日期　　　修改目的
*<br>--------------------------------------------------------
-->
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../home/head.jsp" %>
<jsp:useBean id="obj" scope="request" class="unt.dp.UNTDP026R">
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

function checkField(){
	var alertStr="";
	alertStr += checkEmpty(form1.q_enterOrg,"入帳機關");
	alertStr += checkEmpty(form1.q_deprYM,"折舊年月");
	alertStr += checkYYYMM(form1.q_deprYM,"折舊年月");
	
	if(alertStr.length!=0){ 
		alert(alertStr); 
		return false; 
	}
	
	if (!confirm("此報表產製較費時間,請耐心等候不要關閉視窗")) {
		return false;
	}
}

function alteredEnterOrg(){
	changeEnterOrg_FundType(form1.q_enterOrg.value,'<%=user.getOrganID()%>','q_fundType','','<%=user.getIsAdminManager()%>','<%=user.getIsOrganManager()%>');
}

function check_reset(){
	form1.q_enterOrg.value='<%=user.getOrganID()%>';
	alteredEnterOrg();
}
function checkID(){
	if(form1.isAdminManager.value=='Y'){
	
		document.getElementById('tr1').style.display="";
	}	   
}
</script>

</head>
<body topmargin="10" onload="checkID();" >

<form id="form1"  name="form1" method="post" action="untdp026p.jsp" onReset="check_reset();" onSubmit="return checkField()" target="_blank">
<table class="bg" width="100%" cellspacing="0" cellpadding="0" align="center">
	<tr>
		<td>
			<table class="queryTable" cellspacing="0" cellpadding="0" border="1">
				<tr>
					<td class="td_form" colspan="4" style="text-align:center">財產折舊月報總表(基金財產)<font color="red">(A4 橫式)</font></td>
				</tr>
				<tr id="tr1" style="display:none">
					<td class="queryTDLable"><font color="red">*</font>入帳機關：</td>
					<td class="queryTDInput">
						<%=util.View.getPopOrgan("field_Q","q_enterOrg",user.getOrganID(),user.getOrganName(),"N")%>
					</td>
				</tr>
				<tr>
					<td class="queryTDLable" width="30%"><font color="red">*</font>折舊年月：</td>
					<td class="queryTDInput">
						<br>
						<input type="text" class="field_Q" name="q_deprYM"  size="5" maxlength="5">				
						<br>
						<font color="red">例如：103年 2月，則輸入10302</font>
					</td>
				</tr>
				<tr style="display:none">
					<td class="queryTDLable"><font color="red">*</font>財產區分別：</td>
					<td class="queryTDInput">
						<select class="field_Q" type="select" name="q_differenceKind">
							<%=util.View.getOption("select codeID, codeName from SYSCA_Code where codeKindID='DFK'" ,"120")%>
						</select>
					</td>
				</tr>
				<tr>
					<td class="queryTDLable">部門別換頁方式：</td>
					<td class="queryTDInput">
						<select class="field" type="select" name="q_page">
							<option value="1">部門別</option>
							<option value="2">部門別單位</option>
							<option value="3">部門別單位+會計科目</option>
						</select>
					</td>
				</tr>
				<tr>
					<td class="queryTDLable">園區別：</td>
					<td class="queryTDInput">
						<select class="field_Q" type="select" name="q_deprPark">
							<%=util.View.getOption("select deprparkno, deprparkname from SYSCA_DEPRPARK where 1=1 and enterorg = '" + Common.esapi(user.getOrganID()) + "' ", obj.getQ_deprPark())%>
						</select>
					</td>
				</tr>
				<tr>
					<td class="queryTDLable">部門別：</td>
					<td class="queryTDInput">
						<select class="field" type="select" name="q_deprUnit">
							<%=util.View.getOption("select deprunitno, deprunitname from SYSCA_DEPRUNIT where 1=1 and enterorg = '" + Common.esapi(user.getOrganID()) + "' ", obj.getQ_deprUnit())%>											
						</select>
					</td>
				</tr>
				<tr>
					<td class="queryTDLable">部門別單位：</td>
					<td class="queryTDInput">
						<select class="field" type="select" name="q_deprUnit1">
							<%=util.View.getOption("select deprunit1no, deprunit1name from SYSCA_DEPRUNIT1 where 1=1 and enterorg = '" + Common.esapi(user.getOrganID()) + "' ", obj.getQ_deprUnit1())%>
						</select>
					</td>
				</tr>
				<tr>
					<td class="queryTDLable">會計科目：</td>
					<td class="queryTDInput">
						<select class="field_Q" type="select" name="q_deprAccounts">
							<%=util.View.getOption("SELECT a.depraccountsno , a.depraccountsname FROM SYSCA_DEPRACCOUNTS a WHERE   a.enterorg = '"+ Common.esapi(user.getOrganID()) +"'","")%>
						</select>
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
						<input class="toolbar_default" followPK="false" type="submit" name="querySubmit" value="確　　定" >
						<input class="toolbar_default" followPK="false" type="reset" name="queryCannel" value="取　　消" >
						
						<input type="hidden" name="isAdminManager" value="<%=user.getIsAdminManager()%>">
						<input type="hidden" name="isOrganManager" value="<%=user.getIsOrganManager()%>">
						<input type="hidden" name="organID" value="<%=user.getOrganID()%>">
						<input type="hidden" name="userName" value="<%=user.getUserName()%>">
					</td>
				</tr>
			</table>
		</td>
	</tr>
</table>	
</form>
</body>
</html>
