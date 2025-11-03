<!--
程式目的：
程式代號：untdu012f
程式日期：0970711
程式作者：shan
--------------------------------------------------------
修改作者　　修改日期　　　修改目的
--------------------------------------------------------
-->

<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../home/head.jsp" %>
<jsp:useBean id="obj" scope="request" class="unt.du.UNTDU012F">
	<jsp:setProperty name="obj" property="*"/>
</jsp:useBean>
<jsp:useBean id="objList" scope="page" class="java.util.ArrayList"/>

<%
%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
<meta http-equiv="pragma" content="no-cache"/>
<meta http-equiv="Cache-control" content="no-cache"/>
<link rel="stylesheet" href="../../js/default.css" type="text/css"/>
<script type="text/javascript" src="../../js/validate.js"></script>
<script type="text/javascript" src="../../js/function.js"></script>
<script type="text/javascript" src="../../js/tablesoft.js"></script>
<script language="javascript">
var insertDefault;	//二維陣列, 新增時, 設定預設值
function checkField(){
	var alertStr="";
		alertStr += checkEmpty(form1.q_enterOrg,"入帳機關");
		alertStr += checkEmpty(form1.q_ownership,"權屬");
		alertStr += checkEmpty(form1.q_chengClass,"修改項目");
		//alertStr += checkEmpty(form1.q_propertyNo,"財產編號");
		//alertStr += checkEmpty(form1.q_serialNo,"財產分號");
	if(alertStr.length!=0){ 
		form1.state.value = 'init';
		alert(alertStr); 
		return false; 
	}else{
		form1.state.value = 'queryAll';
	}
}

function checkURL(surl){
		form1.action = surl;
		form1.submit();
}

function addChar(slen){
	var sv = slen.value;
	var sk = sv.length;
	if(sk != 0){
	for(i=0 ;i<( 7 - sk ) ;i++){
		sv = '0' + sv;
	}
	}
	slen.value = sv;
}

</script>
</head>

<body topmargin="0" onLoad="">
<form id="form1" name="form1" method="post" onSubmit="return checkField()">
<!-- Form -->
<table class="bg" width="60%" cellspacing="0" cellpadding="0" align="center"><tr><td>
<table class="queryTable"  border="1">
	<tr>
        <td class="td_form" colspan="4" style="text-align:center">資料修改</td>
	</tr>
	<tr>
		<td class="queryTDLable" width="20%">修改項目：</td>
		<td class="queryTDInput" width="30%">
		<select class="field_RO" type="select" name="q_chengClass">
			<%=util.View.getTextOption("1;土地;2;建物;3;動產;4;土地改良物;5;非消耗品",obj.getQ_chengClass())%>
		</select>
		</td>
	</tr>
	<tr>
		<td class="queryTDLable" width="20%">入帳機關：</td>
		<td class="queryTDInput" width="30%">
			<%=util.View.getPopOrgan("field_Q","q_enterOrg",obj.getQ_enterOrg(),obj.getQ_enterOrgName(), "N")%>
		</td>
	</tr>
	<tr>
		<td class="queryTDLable">權屬：</td>
		<td class="queryTDInput">
			<select class="field_Q" type="select" name="q_ownership">
				<%=util.View.getOnwerOption(obj.getQ_ownership())%>			
			</select>
		</td>
	</tr>
	<tr>
		<td class="queryTDLable">電腦單號：</td>
		<td class="queryTDInput">
			<input class="field_Q" type="text" name="q_caseNo" size="10" maxlength="10" value="<%=obj.getQ_caseNo()%>">
		</td>
	</tr>
	<tr>
		<td class="queryTDLable">財產編號：</td>
		<td class="queryTDInput">
			<input class="field_Q" type="text" name="q_propertyNo" size="11" maxlength="11" value="<%=obj.getQ_propertyNo()%>">
		</td>
	</tr>
	<tr>
		<td class="queryTDLable">批號：</td>
		<td class="queryTDInput">
			<input class="field_Q" type="text" name="q_lotNo" size="7" maxlength="7" value="<%=obj.getQ_lotNo()%>" onBlur="addChar(this)">
		</td>
	</tr>
	<tr>
		<td class="queryTDLable">財產分號：</td>
		<td class="queryTDInput">
			<input class="field_Q" type="text" name="q_serialNo" size="7" maxlength="7" value="<%=obj.getQ_serialNo()%>" onChange="addChar(this ,7);">
		</td>
	</tr>
	<tr>
		<td class="queryTDInput" colspan="4" style="text-align:center;">
			<input class="toolbar_default" followPK="false" type="submit" name="querySubmit" value="確　　定" >
			<input class="toolbar_default" followPK="false" type="reset" name="queryCannel" value="取　　消" >
		</td>
	</tr>
	<%
	
	StringBuffer sbHEML = new StringBuffer();
	if("queryAll".equals(obj.getState())){
		if("1".equals(obj.getQ_chengClass())){
		sbHEML.append( " <tr class='listTR' onmouseover=\"this.className='listTRMouseover'\" onmouseout=\"this.className='listTR'\" onClick=\"return checkURL('untdu015f.jsp');\" >");
		sbHEML.append( " <td class='listTD' colspan='4'>01.土地主檔資料維護-增加單資料</td> ");
		sbHEML.append( " <tr> " );
		sbHEML.append( " <tr class='listTR' onmouseover=\"this.className='listTRMouseover'\" onmouseout=\"this.className='listTR'\" onClick=\"return checkURL('untdu016f.jsp');\" >");
		sbHEML.append( " <td class='listTD' colspan='4'>02.土地主檔資料維護-基本資料</td> ");
		sbHEML.append( " <tr> " );
		sbHEML.append( " <tr class='listTR' onmouseover=\"this.className='listTRMouseover'\" onmouseout=\"this.className='listTR'\" onClick=\"return checkURL('untdu034f.jsp');\" >");
		sbHEML.append( " <td class='listTD' colspan='4'>03.土地增減值作業-增減值單</td> ");
		sbHEML.append( " <tr> " );
		sbHEML.append( " <tr class='listTR' onmouseover=\"this.className='listTRMouseover'\" onmouseout=\"this.className='listTR'\" onClick=\"return checkURL('untdu021f.jsp');\" >");
		sbHEML.append( " <td class='listTD' colspan='4'>04.土地增減值作業-增減值單明細</td> ");
		sbHEML.append( " <tr> " );
		sbHEML.append( " <tr class='listTR' onmouseover=\"this.className='listTRMouseover'\" onmouseout=\"this.className='listTR'\" onClick=\"return checkURL('untdu017f.jsp');\" >");
		sbHEML.append( " <td class='listTD' colspan='4'>05.土地減少作業-減損單資料</td> ");
		sbHEML.append( " <tr> " );
		sbHEML.append( " <tr class='listTR' onmouseover=\"this.className='listTRMouseover'\" onmouseout=\"this.className='listTR'\" onClick=\"return checkURL('untdu018f.jsp');\" >");
		sbHEML.append( " <td class='listTD' colspan='4'>06.土地減少作業-減損單明細資料</td> ");
		sbHEML.append( " <tr> " );
		}
		
		if("2".equals(obj.getQ_chengClass())){
		sbHEML.append( " <tr class='listTR' onmouseover=\"this.className='listTRMouseover'\" onmouseout=\"this.className='listTR'\" onClick=\"return checkURL('untdu022f.jsp');\" >");
		sbHEML.append( " <td class='listTD' colspan='4'>01.建物主檔資料維護-增加單資料</td> ");
		sbHEML.append( " <tr> " );
		sbHEML.append( " <tr class='listTR' onmouseover=\"this.className='listTRMouseover'\" onmouseout=\"this.className='listTR'\" onClick=\"return checkURL('untdu020f.jsp');\" >");
		sbHEML.append( " <td class='listTD' colspan='4'>02.建物主檔資料維護-基本資料</td> ");
		sbHEML.append( " <tr> " );
		sbHEML.append( " <tr class='listTR' onmouseover=\"this.className='listTRMouseover'\" onmouseout=\"this.className='listTR'\" onClick=\"return checkURL('untdu023f.jsp');\" >");
		sbHEML.append( " <td class='listTD' colspan='4'>03.建物增減值作業-增減值單資料</td> ");
		sbHEML.append( " <tr> " );
		sbHEML.append( " <tr class='listTR' onmouseover=\"this.className='listTRMouseover'\" onmouseout=\"this.className='listTR'\" onClick=\"return checkURL('untdu019f.jsp');\" >");
		sbHEML.append( " <td class='listTD' colspan='4'>04.建物增減值作業-增減值單明細資料</td> ");
		sbHEML.append( " <tr> " );
		sbHEML.append( " <tr class='listTR' onmouseover=\"this.className='listTRMouseover'\" onmouseout=\"this.className='listTR'\" onClick=\"return checkURL('untdu024f.jsp');\" >");
		sbHEML.append( " <td class='listTD' colspan='4'>05.建物減少作業-減損單資料</td> ");
		sbHEML.append( " <tr> " );
		sbHEML.append( " <tr class='listTR' onmouseover=\"this.className='listTRMouseover'\" onmouseout=\"this.className='listTR'\" onClick=\"return checkURL('untdu038f.jsp');\" >");
		sbHEML.append( " <td class='listTD' colspan='4'>06.建物減少作業-減損單明細資料</td> ");
		sbHEML.append( " <tr> " );
		}
		
		if("3".equals(obj.getQ_chengClass())){
		sbHEML.append( " <tr class='listTR' onmouseover=\"this.className='listTRMouseover'\" onmouseout=\"this.className='listTR'\" onClick=\"return checkURL('untdu031f.jsp');\" >");
		sbHEML.append( " <td class='listTD' colspan='4'>01.動產主檔資料維護-增加單資料</td> ");
		sbHEML.append( " <tr> " );
		sbHEML.append( " <tr class='listTR' onmouseover=\"this.className='listTRMouseover'\" onmouseout=\"this.className='listTR'\" onClick=\"return checkURL('untdu013f.jsp');\" >");
		sbHEML.append( " <td class='listTD' colspan='4'>02.動產主檔資料維護-基本資料</td> ");
		sbHEML.append( " <tr> " );
		sbHEML.append( " <tr class='listTR' onmouseover=\"this.className='listTRMouseover'\" onmouseout=\"this.className='listTR'\" onClick=\"return checkURL('untdu014f.jsp');\" >");
		sbHEML.append( " <td class='listTD' colspan='4'>03.動產主檔資料維護-明細資料</td> ");
		sbHEML.append( " <tr> " );
		sbHEML.append( " <tr class='listTR' onmouseover=\"this.className='listTRMouseover'\" onmouseout=\"this.className='listTR'\" onClick=\"return checkURL('untdu032f.jsp');\" >");
		sbHEML.append( " <td class='listTD' colspan='4'>04.動產增減值作業-增減值單資料</td> ");
		sbHEML.append( " <tr> " );
		sbHEML.append( " <tr class='listTR' onmouseover=\"this.className='listTRMouseover'\" onmouseout=\"this.className='listTR'\" onClick=\"return checkURL('untdu035f.jsp');\" >");
		sbHEML.append( " <td class='listTD' colspan='4'>05.動產增減值作業-增減值單明細資料</td> ");
		sbHEML.append( " <tr> " );
		sbHEML.append( " <tr class='listTR' onmouseover=\"this.className='listTRMouseover'\" onmouseout=\"this.className='listTR'\" onClick=\"return checkURL('untdu033f.jsp');\" >");
		sbHEML.append( " <td class='listTD' colspan='4'>06.動產減少作業 -減少單資料</td> ");
		sbHEML.append( " <tr> " );
		sbHEML.append( " <tr class='listTR' onmouseover=\"this.className='listTRMouseover'\" onmouseout=\"this.className='listTR'\" onClick=\"return checkURL('untdu039f.jsp');\" >");
		sbHEML.append( " <td class='listTD' colspan='4'>07.動產減少作業 -減少單明細資料</td> ");
		sbHEML.append( " <tr> " );
		}
		if("4".equals(obj.getQ_chengClass())){
		sbHEML.append( " <tr class='listTR' onmouseover=\"this.className='listTRMouseover'\" onmouseout=\"this.className='listTR'\" onClick=\"return checkURL('untdu025f.jsp');\" >");
		sbHEML.append( " <td class='listTD' colspan='4'>01.土地改良物主檔-增加單資料</td> ");
		sbHEML.append( " <tr> " );
		sbHEML.append( " <tr class='listTR' onmouseover=\"this.className='listTRMouseover'\" onmouseout=\"this.className='listTR'\" onClick=\"return checkURL('untdu042f.jsp');\" >");
		sbHEML.append( " <td class='listTD' colspan='4'>02.土地改良物主檔-基本資料</td> ");
		sbHEML.append( " <tr> " );
		sbHEML.append( " <tr class='listTR' onmouseover=\"this.className='listTRMouseover'\" onmouseout=\"this.className='listTR'\" onClick=\"return checkURL('untdu026f.jsp');\" >");
		sbHEML.append( " <td class='listTD' colspan='4'>03.土地改良物增減值作業-增減值單資料</td> ");
		sbHEML.append( " <tr> " );
		sbHEML.append( " <tr class='listTR' onmouseover=\"this.className='listTRMouseover'\" onmouseout=\"this.className='listTR'\" onClick=\"return checkURL('untdu036f.jsp');\" >");
		sbHEML.append( " <td class='listTD' colspan='4'>04.土地改良物增減值作業-增減值單明細資料</td> ");
		sbHEML.append( " <tr> " );
		sbHEML.append( " <tr class='listTR' onmouseover=\"this.className='listTRMouseover'\" onmouseout=\"this.className='listTR'\" onClick=\"return checkURL('untdu027f.jsp');\" >");
		sbHEML.append( " <td class='listTD' colspan='4'>05.土地改良物減少作業-減損單資料</td> ");
		sbHEML.append( " <tr> " );
		sbHEML.append( " <tr class='listTR' onmouseover=\"this.className='listTRMouseover'\" onmouseout=\"this.className='listTR'\" onClick=\"return checkURL('untdu040f.jsp');\" >");
		sbHEML.append( " <td class='listTD' colspan='4'>06.土地改良物減少作業-減損單明細資料</td> ");
		sbHEML.append( " <tr> " );
		}
		if("5".equals(obj.getQ_chengClass())){
		sbHEML.append( " <tr class='listTR' onmouseover=\"this.className='listTRMouseover'\" onmouseout=\"this.className='listTR'\" onClick=\"return checkURL('untdu028f.jsp');\" >");
		sbHEML.append( " <td class='listTD' colspan='4'>01.非消耗品資料維護-增加單資料</td> ");
		sbHEML.append( " <tr> " );
		
		sbHEML.append( " <tr class='listTR' onmouseover=\"this.className='listTRMouseover'\" onmouseout=\"this.className='listTR'\" onClick=\"return checkURL('untdu043f.jsp');\" >");
		sbHEML.append( " <td class='listTD' colspan='4'>02.非消耗品資料維護-基本資料</td> ");
		sbHEML.append( " <tr> " );
		sbHEML.append( " <tr class='listTR' onmouseover=\"this.className='listTRMouseover'\" onmouseout=\"this.className='listTR'\" onClick=\"return checkURL('untdu044f.jsp');\" >");
		sbHEML.append( " <td class='listTD' colspan='4'>03.非消耗品資料維護-明細資料</td> ");
		sbHEML.append( " <tr> " );
		
		sbHEML.append( " <tr class='listTR' onmouseover=\"this.className='listTRMouseover'\" onmouseout=\"this.className='listTR'\" onClick=\"return checkURL('untdu029f.jsp');\" >");
		sbHEML.append( " <td class='listTD' colspan='4'>04.非消耗品增減值作業-增減值單資料</td> ");
		sbHEML.append( " <tr> " );
		sbHEML.append( " <tr class='listTR' onmouseover=\"this.className='listTRMouseover'\" onmouseout=\"this.className='listTR'\" onClick=\"return checkURL('untdu037f.jsp');\" >");
		sbHEML.append( " <td class='listTD' colspan='4'>05.非消耗品增減值作業-增減值單明細資料</td> ");
		sbHEML.append( " <tr> " );
		sbHEML.append( " <tr class='listTR' onmouseover=\"this.className='listTRMouseover'\" onmouseout=\"this.className='listTR'\" onClick=\"return checkURL('untdu030f.jsp');\" >");
		sbHEML.append( " <td class='listTD' colspan='4'>06.非消耗品減少作業-減損單資料</td> ");
		sbHEML.append( " <tr> " );
		sbHEML.append( " <tr class='listTR' onmouseover=\"this.className='listTRMouseover'\" onmouseout=\"this.className='listTR'\" onClick=\"return checkURL('untdu041f.jsp');\" >");
		sbHEML.append( " <td class='listTD' colspan='4'>07.非消耗品減少作業-減損單明細資料</td> ");
		sbHEML.append( " <tr> " );
		}
	}
	out.write(sbHEML.toString());
	%>
</table>
</table>	
<table style="display:none">
<tr><td class="bg">
	<jsp:include page="../../home/toolbar.jsp" />
</td></tr>
</table>

<!--隱藏欄位-->

	<input type="hidden" name="state" value="<%=obj.getState()%>">
	<input type="hidden" name="queryAllFlag" value="<%=obj.getQueryAllFlag()%>">
	<input type="hidden" name="userID" value="<%=user.getUserID()%>">
</form>
</body>
</html>



