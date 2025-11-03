<!--
*<br>程式目的：有價證券財產卡報表檔 
*<br>程式代號：untvp005r
*<br>撰寫日期：94/11/09
*<br>程式作者：Cherry
*<br>--------------------------------------------------------
*<br>修改作者　　修改日期　　　修改目的
*<br>--------------------------------------------------------
-->
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../home/head.jsp" %>
<jsp:useBean id="obj" scope="request" class="unt.vp.UNTVP005R">
	<jsp:setProperty name="obj" property="*"/>
</jsp:useBean>
<%
if("".equals(util.Common.checkGet(obj.getQ_enterOrg()))){obj.setQ_enterOrg(user.getOrganID());}
if("".equals(util.Common.checkGet(obj.getQ_enterOrgName()))){obj.setQ_enterOrgName(user.getOrganName());}
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
<script type="text/javascript" src="../../js/jquery-1.8.2.js"></script>
<script language="javascript" src="../../js/sList2.js"></script>
<script language="javascript">

var insertDefault;	//二維陣列, 新增時, 設定預設值
function checkField(){
	var alertStr="";
	alertStr += checkDate(form1.q_writeDateS,"填單日期-起");
	alertStr += checkDate(form1.q_writeDateE,"填單日期-訖");
	alertStr += checkDate(form1.q_enterDateS,"入帳日期-起");
	alertStr += checkDate(form1.q_enterDateE,"入帳日期-訖");
	alertStr += checkEmpty(form1.q_enterOrg,"管理機關");

	var err1=checkAllValue( [form1.q_propertyNoS,form1.q_propertyNoE] ,["財產編號－起","財產編號－訖"] );
	var err2=checkAllValue( [form1.q_enterDateS,form1.q_enterDateE] ,["入帳日期－起","入帳日期－訖"] );
	var err3=checkAllValue( [form1.q_proofYear,form1.q_proofNoS,form1.q_proofNoE] ,["增加單編號","編號－起","編號－訖"] );
	
	if(err1 != "" && err2 !="" && err3 !=""){
		alertStr += "財產編號－起訖, 入帳日期－起訖, 增加單編號－起訖 \n三組須任一組都有值!";
	}	
	
	if(alertStr.length!=0){ 
		alert(alertStr); 
		return false; 
	}else{
    	form1.action="untvp005p.jsp";
		return true;
	}
}

function changeSelect(){
	//財產性質為「03:事業用」時，須控制「基金財產」資料
	if(form1.q_propertyKind.value == "03") document.all.q_fundType.disabled=false;
	else{
		document.all.q_fundType.disabled=true;
		form1.q_fundType.value="";
	}
}

function alteredEnterOrg(){
	//changeEnterOrg_FundType(form1.q_enterOrg.value,'<%=user.getOrganID()%>','q_fundType','','<%=user.getIsAdminManager()%>','<%=user.getIsOrganManager()%>');
}

function check_reset(){
	form1.q_enterOrg.value='<%=user.getOrganID()%>';
	alteredEnterOrg();
	form1.q_propertyKind.value='';
	changeSelect();
}

function init() {
	form1.objPath.value = top.fbody.mainhead.document.getElementById("pathname").innerText;
}
</script>

</head>
<body topmargin="10" onload='init();'>

<form id="form1" name="form1" method="post" action="untvp005p.jsp" onReset="check_reset();" onSubmit="return checkField()" target="_blank">
<input type="hidden" name="userID" value="<%=user.getUserID()%>">
<input type="hidden" name="organID" value="<%=user.getOrganID()%>">
<input type="hidden" name="isOrganManager" value="<%=user.getIsOrganManager()%>">
<input type="hidden" name="isAdminManager" value="<%=user.getIsAdminManager()%>">				
<input type="hidden" name="objPath" >		
<table class="bg" width="102%" cellspacing="0" cellpadding="0" align="center"><tr><td>
	<table class="queryTable"  border="1">
	<tr>
        <td class="td_form" colspan="4" style="text-align:center">有價證券財產卡 <font color="red">(A4 直式)</font></td>
	</tr>
	<tr>
		<td class="queryTDLable"><font color="red">*</font>入帳機關：</td>
		<td class="queryTDInput" >
			<%=util.View.getPopOrgan("field_Q","q_enterOrg",obj.getQ_enterOrg().equals("")?user.getOrganID():obj.getQ_enterOrg(),obj.getQ_enterOrgName().equals("")?user.getOrganName():obj.getQ_enterOrgName(),"N&js=alteredEnterOrg();")%>
		</td>
		<td class="queryTDLable"><div align="right">權屬：</div></td>
 		<td class="queryTDInput">
 			<select class="field_Q" type="select" name="q_ownership">
 				<%=util.View.getOnwerOption(obj.getQ_ownership())%>
			</select>
		</td>
	</tr>
	<tr>
		<td class="queryTDLable">電腦單號：</td>
		<td class="queryTDInput">
			  起<input class="field_Q" type="text" name="q_caseNoS" size="12" maxlength="10" value="<%=obj.getQ_caseNoS()%>">&nbsp;~&nbsp;
			  訖<input class="field_Q" type="text" name="q_caseNoE" size="12" maxlength="10" value="<%=obj.getQ_caseNoE()%>">
		</td>
		<td class="queryTDLable">入帳日期：</td>
		<td class="queryTDInput">
			起<%=util.View.getPopCalndar("field_Q","q_enterDateS",obj.getQ_enterDateS())%>&nbsp;~&nbsp;
			訖<%=util.View.getPopCalndar("field_Q","q_enterDateE",obj.getQ_enterDateE())%>
		</td>
	</tr>
	<tr>
		<td class="queryTDLable">財產編號：</td>
		<td colspan="3" class="queryTDInput" colspan="4">
			起<%=util.View.getPopProperty("field_Q","q_propertyNoS",obj.getQ_propertyNoS(),obj.getQ_propertyNoSName(),"9")%>&nbsp;~&nbsp;
			訖<%=util.View.getPopProperty("field_Q","q_propertyNoE",obj.getQ_propertyNoE(),obj.getQ_propertyNoEName(),"9")%>
		</td>
	</tr>
	<tr>
		<td class="queryTDLable">財產分號：</td>
		<td class="queryTDInput"> 
			起<input class="field_Q" type="text" name="q_serialNoS" size="7" maxlength="7" value="<%=obj.getQ_serialNoS()%>" onChange="addChar(this ,7);">&nbsp;~&nbsp;
			訖<input class="field_Q" type="text" name="q_serialNoE" size="7" maxlength="7" value="<%=obj.getQ_serialNoE()%>" onChange="addChar(this ,7);">
		</td>
		<td class="queryTDLable">資料狀態：</td>
		<td class="queryTDInput">
			<select class="field_Q" type="select" name="q_dataState">
				<%=util.View.getTextOption("1;現存;2;已減損","1")%>			
			</select>			
		</td>	
	</tr>
	<tr>
		<td class="queryTDLable"><div align="right">填單日期：</div></td>
		<td class="queryTDInput" colspan="4">
			起<%=util.View.getPopCalndar("field_Q","q_writeDateS",obj.getQ_writeDateS())%>&nbsp;~&nbsp;
			訖<%=util.View.getPopCalndar("field_Q","q_writeDateE",obj.getQ_writeDateE())%>
		</td>
	</tr>
	<tr>
		<td class="queryTDLable">財產性質：</td>
		<td class="queryTDInput">
			<select class="field_Q" type="select" name="q_propertyKind" onChange="changeSelect();">			
				<%=util.View.getOption("select codeID, codeName from SYSCA_Code where codeKindID='PKD' ",obj.getQ_propertyKind())%>
			</select>		
		</td>			
		<td class="queryTDLable">基金財產：</td>
		<td class="queryTDInput">
			<select class="field_Q" type="select" name="q_fundType">
				<%=util.View.getOption("select codeID, codeName from SYSCA_Code where codeKindID='FUD'", obj.getQ_fundType())%>
			</select> 
		</td>		
	</tr>
	<tr>
		<td class="queryTDLable">增加單編號：</td>
		<td class="queryTDInput"  colspan="4">
			<input class="field_Q" type="text" name="q_proofYear" size="5" maxlength="5" value="<%=obj.getQ_proofYear()%>">年
			<input class="field_Q" type="text" name="q_proofDoc" size="15" maxlength="20" value="<%=obj.getQ_proofDoc()%>">字
			起<input class="field_Q" type="text" name="q_proofNoS" size="10" maxlength="20" value="<%=obj.getQ_proofNoS()%>" onChange="addChar(this ,7);">&nbsp;~&nbsp;
			訖<input class="field_Q" type="text" name="q_proofNoE" size="10" maxlength="20" value="<%=obj.getQ_proofNoE()%>" onChange="addChar(this ,7);">號
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
		<!--審核：-->
		<input type="hidden" name="q_verify" value="Y">
	<tr>
		<td class="queryTDInput" colspan="4" style="text-align:center;">
			<input class="toolbar_default" followPK="false" type="submit" name="querySubmit" value="確　　定" >
			<input class="toolbar_default" followPK="false" type="reset" name="queryCannel" value="取　　消" >
		</td>
	</tr>
	<tr>
		<td class="queryTDInput" colspan="4" style="text-align:center;">
			<font color="red">本列印作業，顯示列印一次最多100筆</font>
		</td>
	</tr>	
	</table>
</td></tr></table>	
</form>
</body>
</html>
