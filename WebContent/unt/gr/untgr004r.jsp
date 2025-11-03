<!--
*<br>程式目的：財產明細分類帳 
*<br>程式代號：untgr004r
*<br>撰寫日期：0950303
*<br>程式作者：Cherry
*<br>--------------------------------------------------------
*<br>修改作者　　修改日期　　　修改目的
*<br>--------------------------------------------------------
-->
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../home/head.jsp" %>
<jsp:useBean id="obj" scope="request" class="unt.gr.UNTGR004R">
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
<script language="javascript" src="../../js/validate.js"></script>
<script language="javascript" src="../../js/function.js"></script>
<script language="javascript" src="../../js/tablesoft.js"></script>
<script language="javascript" src="../../js/changeEnterOrg_FundType.js"></script>
<script language="javascript">
var readbg="#EEEEEE";
var editbg="#FFFFFF";
//var insertDefault;	//二維陣列, 新增時, 設定預設值

function checkField(){
	var alertStr="";
	alertStr += checkEmpty(form1.q_enterOrg,"入帳機關");
	alertStr += checkEmpty(form1.q_ownership,"權屬");
	alertStr += checkEmpty(form1.q_differenceKind,"財產區分別");
	alertStr += checkEmpty(form1.q_reportYM,"總結年度月份");
	alertStr += checkYYYMM(form1.q_reportYM,"總結年度月份");
	//alertStr += checkEmpty(form1.q_propertyNoS,"財產編號-起");
	//alertStr += checkEmpty(form1.q_propertyNoE,"財產編號-訖");
	//if(document.getElementById("P1").checked==true){
		//alertStr += checkEmpty(form1.q_propertyType,"財產類別");
	//}
	if(alertStr.length!=0){ alert(alertStr); return false; }
}

function changeKind() {
	if(document.getElementById("P2").checked==true){
		setFormItem("q_propertyType","R");
		form1.q_propertyType.value="";
		form1.propertyNumber.value="6";
	}else if(document.getElementById("P1").checked==true){
		setFormItem("q_propertyType","O");
		form1.propertyNumber.value="";
	}
}

function changeQPropertyNo(){
	if(parse(form1.q_propertyNoS.value).length>0){
		if(form1.q_propertyNoS.value.substr(0,3)=='111'){
			getProperty('q_propertyNoS','q_propertyNoSName',form1.q_propertyNoS.value.substr(0,3));
		}else{
			getProperty('q_propertyNoS','q_propertyNoSName',form1.q_propertyNoS.value.substr(0,1));
		}
	}else{
		form1.q_propertyNoSName.value = "";
	}
	if(parse(form1.q_propertyNoE.value).length>0){
		if(form1.q_propertyNoE.value.substr(0,3)=='111'){
			getProperty('q_propertyNoE','q_propertyNoEName',form1.q_propertyNoE.value.substr(0,3));
		}else{
			getProperty('q_propertyNoE','q_propertyNoEName',form1.q_propertyNoE.value.substr(0,1));
		}
	}else{
		form1.q_propertyNoEName.value = "";
	}
}

function check_reset(){
	form1.q_enterOrg.value='<%=user.getOrganID()%>';
	document.getElementById("P1").checked=true;
	changeKind();
}
function changePropertyNo(propertyNo){}

</script>
</head>
<body topmargin="10">
<form id="form1" name="form1" method="post" action="untgr004p.jsp" onReset="check_reset();" onSubmit="return checkField()" target="_blank">
<table class="bg" width="65%" cellspacing="0" cellpadding="0" align="center"><tr><td>
	<table class="queryTable"  border="1">
	<tr>
        <td class="td_form" colspan="4" style="text-align:center">財產明細分類帳<font color="red">(A4橫式)</font></td>
	</tr>
	<tr>
		<td class="queryTDLable"><font color="red">*</font>入帳機關：</td>
		<td class="queryTDInput">
			<%=util.View.getPopOrgan("field_Q","q_enterOrg",obj.getQ_enterOrg().equals("")?user.getOrganID():obj.getQ_enterOrg(),obj.getQ_enterOrgName().equals("")?user.getOrganName():obj.getQ_enterOrgName(),"N&js=alteredEnterOrg();")%>
			<input type="hidden" name="isAdminManager" value="<%=user.getIsAdminManager()%>">
			<input type="hidden" name="isOrganManager" value="<%=user.getIsOrganManager()%>">
			<input type="hidden" name="organID" value="<%=user.getOrganID()%>">
			<input class="field_Q" type="hidden" name="q_verify" value="Y">
			<input class="field_Q" type="hidden" name="propertyNumber" value="">
			<input class="field_P" type="hidden" name="userName" value="<%=user.getUserName()%>">
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
		<td class="queryTDLable"><font color="red">*</font>財產區分別：</td>
		<td class="queryTDInput">
			<select class="field_Q" type="select" name="q_differenceKind">
				<%=util.View.getOption("select codeID, codeName from SYSCA_Code where codeKindID='DFK' and codeID in ('110','111','120','112')" ,"")%>
			</select>
		</td>
	</tr>
	<tr>
		<td class="queryTDLable"><font color="red">*</font>查詢種類：</td>
		<td class="queryTDInput">
		    <input id="P1" class="field_Q" type="radio" name="q_propertyKind1" value="1" checked onClick="changeKind();">財產
		    <input id="P2" class="field_Q" type="radio" name="q_propertyKind1" value="2" onClick="changeKind();">物品
		</td>
	</tr>
	<tr>
		<td class="queryTDLable"><font color="red">*</font>總結年度月份：</td>
		<td class="queryTDInput">
			<input class="field_Q" type="text" name="q_reportYM" size="5" maxlength="5" value="<%=obj.getQ_reportYM()%>">
		</td>
	</tr>
	<tr>
		<td class="queryTDLable">財產大類：</td>
		<td class="queryTDInput">
			<select class="field_Q" type="select" name="q_propertyType">
				<%=util.View.getTextOption("1;土地;2;土地改良物;3;建物;4;動產;5;有價證券;6;權利",obj.getQ_propertyType())%>
			</select>
		</td>
	</tr>
	<tr>
		<td class="queryTDLable">財產編號：</td>
	    <td class="queryTDInput">
	    	起<input class="field_Q" type="text" name="q_propertyNoS" size="10" maxlength="11"	value="<%=obj.getQ_propertyNoS()%>" onBlur="changeQPropertyNo();">
			<input class="field_Q" type="button" name="btn_q_propertyNoS" onclick="popProperty('q_propertyNoS','q_propertyNoSName',form1.propertyNumber.value)" value="..."	title="財產編號輔助視窗">
			[<input class="field_QRO" type="text" name="q_propertyNoSName" size="20" maxlength="50" value="<%=obj.getQ_propertyNoSName()%>">] 
		<br>			
			訖<input class="field_Q" type="text" name="q_propertyNoE" size="10" maxlength="11"	value="<%=obj.getQ_propertyNoE()%>" onBlur="changeQPropertyNo();">
			<input class="field_Q" type="button" name="btn_q_propertyNoE" onclick="popProperty('q_propertyNoE','q_propertyNoEName',form1.propertyNumber.value)" value="..."	title="財產編號輔助視窗">
			[<input class="field_QRO" type="text" name="q_propertyNoEName" size="20" maxlength="50" value="<%=obj.getQ_propertyNoEName()%>">] 
		</td>
	</tr>
	<tr>
		<td class="queryTDLable">珍貴財產：</td>
		<td class="queryTDInput">
			<select class="field_Q" type="select" name="q_valuable">
				<%=util.View.getTextOption("Y;是;N;否",obj.getQ_valuable())%>
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
		</td>
	</tr>
	</table>
</td></tr></table>	
</form>
</body>
</html>
