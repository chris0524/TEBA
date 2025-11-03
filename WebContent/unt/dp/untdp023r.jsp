<!--
*<br>程式目的：財產折舊表(代管資產) 
*<br>程式代號：UNTDP023R
*<br>撰寫日期：103/10/07
*<br>程式作者：Mike Kao
*<br>--------------------------------------------------------
*<br>修改作者　　修改日期　　　修改目的
*<br>--------------------------------------------------------
-->
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../home/head.jsp" %>
<jsp:useBean id="obj" scope="request" class="unt.dp.UNTDP023R">
	<jsp:setProperty name="obj" property="*"/>
</jsp:useBean>
<%
	obj.setQ_ownership("1");
%>
<html>
<head>
<meta http-equiv="Content-Language" content="zh-tw"/>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
<meta http-equiv="Expires" content="-1"/>
<meta http-equiv="pragma" content="no-cache"/>
<meta http-equiv="Cache-control" content="no-cache"/>
<link rel="stylesheet" href="../../js/default.css" type="text/css">
<script language="javascript" src="../../js/json.js"></script>
<script language="javascript" src="../../js/validate.js"></script>
<script language="javascript" src="../../js/function.js"></script>
<script language="javascript" src="../../js/tablesoft.js"></script>
<script language="javascript" src="../../unt/mp/changeEnterOrg_KeepUnit.js"></script>
<script language="javascript" src="../../js/changeEnterOrg_FundType.js"></script>
<script language="javascript" src="../../js/changeEnterOrg_KeepUnit.js"></script>
<script language="javascript">
//var insertDefault;	//二維陣列, 新增時, 設定預設值
function checkField(){
	var alertStr="";
	alertStr += checkEmpty(form1.q_enterOrg,"入帳機關");
	alertStr += checkEmpty(form1.q_ownership,"權屬");
	alertStr += checkEmpty(form1.q_deprYM,"折舊年月");
	alertStr += checkEmpty(form1.q_reportType,"報表類別");
	
	if(form1.q_reportType.value == '2'){
		alertStr += checkEmpty(form1.q_deprPark,"園區別");
	} else if(form1.q_reportType.value == '3'){
		alertStr += checkEmpty(form1.q_deprUnit,"部門別");
	} else if(form1.q_reportType.value == '4'){
		alertStr += checkEmpty(form1.q_deprPark,"園區別");
		alertStr += checkEmpty(form1.q_deprUnit,"部門別");
	}
	
	if(alertStr.length!=0){ alert(alertStr); return false; }
}

function changeAll(){
	changeQ_enterOrg(form1.q_enterOrg.value,'q_keepUnit','');
	changeKeepUnit(form1.q_enterOrg, '', form1.q_keepUnit,'');
	changeEnterOrg_FundType(form1.q_enterOrg.value,'<%=user.getOrganID()%>','q_fundType','','<%=user.getIsAdminManager()%>','<%=user.getIsOrganManager()%>');
}

function check_reset(){
	form1.q_enterOrg.value='<%=user.getOrganID()%>';
	alteredEnterOrg();
}


function checkTableExist(obj){
	if(obj.value.length==5){
		var x = getRemoteData('../../ajax/jsonUntdp023rMsg.jsp?q_deprYM='+obj.value);	
		if (x!=null && x.length!=0) {
			alert(x);
		}
	}
}

</script>
</head>
<body topmargin="10">
<form id="form1" name="form1" method="post" action="untdp023p.jsp" onReset="check_reset();" onSubmit="return checkField()" target="_blank">
<table class="bg" width="60%" cellspacing="0" cellpadding="0" align="center"><tr><td>
	<table class="queryTable"  border="1">
	<tr>
        <td class="td_form" colspan="4" style="text-align:center">財產折舊表(代管資產)<font color="red">(A4橫式)</font></td>
	</tr>
	<tr>
		<td class="queryTDLable"><font color="red">*</font>入帳機關：</td>
		<td class="queryTDInput">
			<%=util.View.getPopOrgan("field_RO","q_enterOrg",user.getOrganID(),user.getOrganName(),"N&js=changeAll();")%>
			<input type="hidden" name="isAdminManager" value="<%=user.getIsAdminManager()%>">
			<input type="hidden" name="isOrganManager" value="<%=user.getIsOrganManager()%>">
			<input type="hidden" name="organID" value="<%=user.getOrganID()%>">
			<input class="field_Q" type="hidden" name="q_verify" value="Y">
			<input class="field_P" type="hidden" name="editID" value="<%=user.getUserID()%>">
		</td>
	</tr>
	<tr>
		<td class="queryTDLable"><font color="red">*</font>權屬：</td>
		<td class="queryTDInput">
			<select class="field_Q" type="select" name="q_ownership">
 				<%=util.View.getOnwerOption(obj.getQ_ownership())%>			
			</select>
		</td>
	</tr>
	<tr>
		<td class="queryTDLable"><font color="red">*</font>折舊年月：</td>
		<td class="queryTDInput">
 			<input type="text" class="field" name="q_deprYM"  size="5" maxlength="5" onblur="checkTableExist(this);">
 			<br>
				例如：103年 2月，則輸入10302		
		</td>
	</tr>
	<tr>
		<td class="queryTDLable"><font color="red">*</font>報表類別：</td>
		<td class="queryTDInput">
			<select class="field_Q" type="select" name="q_reportType">
 				<%=util.View.getTextOption("1;總表;2;園區別;3;部門別;4;園區別+部門別",obj.getQ_reportType())%>		
			</select>
		</td>
	</tr>
	<tr>
		<td class="queryTDLable">園區別：</td>
		<td class="queryTDInput">
			<select class="field_Q" type="select" name="q_deprPark">
				<%=util.View.getOption("select deprparkno, deprparkname from SYSCA_DEPRPARK where 1=1 ", obj.getQ_deprPark())%>
			</select>
		</td>
	</tr>
	<tr>
		<td class="queryTDLable">部門別：</td>
		<td class="queryTDInput">
			<select class="field" type="select" name="q_deprUnit">
			　　<%=util.View.getOption("select deprUnitNo, deprUnitName from SYSCA_DeprUnit where enterorg = '" + user.getOrganID() + "'", obj.getQ_deprUnit())%>
			</select>
		</td>
	</tr>
	<tr>
		<td class="queryTDLable">珍貴財產：</td>
		<td class="queryTDInput">
			<select class="field_Q" type="select" name="q_valuable">
 				<%=util.View.getTextOption("N;否;Y;是",obj.getQ_valuable())%>		
			</select>
		</td>
	</tr>
			<input type="hidden" name="q_verify" value="Y"/>
			<input type="hidden" name="q_dataState" value="1"/>
			<input type="hidden" name="q_differenceKind" value="111"/>
	<tr>
		<td class="queryTDInput" colspan="4" style="text-align:center;">
			<input class="toolbar_default" followPK="false" type="submit" name="querySubmit" value="確　　定" >
			<input class="toolbar_default" followPK="false" type="reset" name="queryCannel" value="取　　消" >
		</td>
	</tr>
	</table>
</td></tr></table>	
</form>
</body>
</html>
