<!--
*<br>程式目的：非消耗品增加單查詢檔 
*<br>程式代號：untne005p
*<br>撰寫日期：103/09/19
*<br>程式作者：Mike Kao
*<br>--------------------------------------------------------
*<br>修改作者　　修改日期　　　修改目的
*<br>--------------------------------------------------------
-->
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../home/head.jsp" %>
<jsp:useBean id="obj" scope="request" class="unt.ne.UNTNE005R">
	<jsp:setProperty name="obj" property="*"/>
</jsp:useBean>
<%
String q_proofDoc = Common.checkGet(request.getParameter("q_proofDoc"));
// if(!"".equals(q_proofDoc)){
//	 obj.setQ_proofDoc(q_proofDoc);
// }else{
//	 obj.setQ_proofDoc(new unt.ch.UNTCH_Tools()._getProofDoc(user.getOrganID(),"G1"));
// }

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
<script language="javascript">

var insertDefault;	//二維陣列, 新增時, 設定預設值
function checkField(){
	var alertStr="";
	alertStr += checkDate(form1.q_writeDateS,"填單日期-起");
	alertStr += checkDate(form1.q_writeDateE,"填單日期-訖");
	alertStr += checkEmpty(form1.q_enterOrg,"管理機關");
	alertStr += checkEmpty(form1.q_kind,"聯數類別");

	var columns = [form1.q_caseNoS,form1.q_caseNoE,form1.q_writeDateS,form1.q_writeDateE,form1.q_proofYear];
	var names = ["電腦單號－起","電腦單號－訖","填單日期－起","填單日期－訖","編號年"];
	alertStr += checkAtLeastOne(columns,names);
	
	if(alertStr.length!=0){ 
		alert(alertStr); 
		return false; 
	}else{
    	form1.action="untne005p.jsp";
		return true;
	}
}

function init(){
	if(form1.q_proofYear.value == ""){
		form1.q_proofYear.value = <%= Datetime.getYYY() %>;	
	}	
	form1.objPath.value = top.fbody.mainhead.document.getElementById("pathname").innerText;
}
</script>

</head>
<body topmargin="10"  onload="init();" >
<form id="form1" name="form1" method="post" action="untne005p.jsp" onSubmit="return checkField()" target="_blank">
<input type="hidden" name="userID" value="<%=user.getUserID()%>">
<input type="hidden" name="organID" value="<%=user.getOrganID()%>">
<input type="hidden" name="editID" value="<%=session.getAttribute("editID")%>">
<input type="hidden" name="isOrganManager" value="<%=user.getIsOrganManager()%>">
<input type="hidden" name="isAdminManager" value="<%=user.getIsAdminManager()%>">	
<input type="hidden" name="objPath" >
<!-- <input type="hidden" name="q_ownership" value="1"> -->
<table class="bg" width="70%" cellspacing="0" cellpadding="0" align="center"><tr><td>
	<table class="queryTable"  border="1">
	<tr>
        <td class="td_form" colspan="2" style="text-align:center">非消耗品增加單 <font color="red">(A4 橫式)</font></td>
	</tr>
	<tr>
		<td class="queryTDLable" width="25%"><font color="red">*</font>入帳機關：</td>
		<td class="queryTDInput" >
			<%=util.View.getPopOrgan("field_Q","q_enterOrg",user.getOrganID(),user.getOrganName(),"N")%>
		</td>
	</tr>
	<tr>
		<td class="queryTDLable">電腦單號：</td>
		<td class="queryTDInput" colspan="4">
			  起<input class="field_Q" type="text" name="q_caseNoS" size="12" maxlength="10" value="<%=obj.getQ_caseNoS()%>">&nbsp;~&nbsp;
			  訖<input class="field_Q" type="text" name="q_caseNoE" size="12" maxlength="10" value="<%=obj.getQ_caseNoE()%>">
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
		<td class="queryTDLable">增加單編號：</td>
		<td class="queryTDInput"  colspan="2">
			<input class="field_Q" type="text" name="q_proofYear" size="5" maxlength="5" value="<%=obj.getQ_proofYear()%>">年
			<input class="field_Q" type="text" name="q_proofDoc" size="15" maxlength="20" value="<%=obj.getQ_proofDoc()%>">字
			起<input class="field_Q" type="text" name="q_proofNoS" size="10" maxlength="20" value="<%=obj.getQ_proofNoS()%>" onChange="addChar(this ,7);">&nbsp;~&nbsp;
			訖<input class="field_Q" type="text" name="q_proofNoE" size="10" maxlength="20" value="<%=obj.getQ_proofNoE()%>" onChange="addChar(this ,7);">號
		</td>
	</tr>
	<tr>
		<td class="queryTDLable"><font color="red">*</font>聯數類別：</td>
		<td class="queryTDInput">
			<select class="field_Q" name="q_kind">
				<%=util.View.getOption("select codeID, codeName from SYSCA_Code where codeKindID='PFK' order by codeID ", new unt.ch.UNTCH_Tools()._getDefaultValue(user.getOrganID(), "proofKindNE"))%>
			</select>
		</td>
	</tr>
	<tr>
		<td class="queryTDLable">單據是否列印備註：</td>
		<td class="queryTDInput">
			<select class="field_Q" type="select" name="q_note">
			　　<%=util.View.getYNOption(new unt.ch.UNTCH_Tools()._getDefaultValue(user.getOrganID(), "proofNotes"))%>
			</select>
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
		</td>
	</tr>
	<tr>
		<td class="queryTDInput" colspan="2" style="text-align:center;">
			<font color="red">本列印作業，顯示列印一次最多100筆，核完章後請務必至「物品主檔資料維護」進行入帳!!</font>
		</td>
	</tr>
	</table>
</td></tr></table>	
</form>
</body>
</html>
