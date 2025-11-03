<!--
*<br>程式目的：建物所有權狀備查簿查詢檔 
*<br>程式代號：untbu029r
*<br>撰寫日期：94/12/13
*<br>程式作者：chris
*<br>--------------------------------------------------------
*<br>修改作者　　修改日期　　　修改目的
*<br>--------------------------------------------------------
-->
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../home/head.jsp" %>
<jsp:useBean id="obj" scope="request" class="unt.bu.UNTBU029R">
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
        var alertStr="";
		alertStr += checkDate(form1.q_enterDateS,"入帳日期－起");
		alertStr += checkDate(form1.q_enterDateE,"入帳日期－訖");
		alertStr += checkDate(form1.q_drawDateS,"領狀日期－起");
		alertStr += checkDate(form1.q_drawDateE,"領狀日期－訖");
		if(alertStr.length!=0){ alert(alertStr); return false; }
    //return true;
	//beforeSubmit();	
    //form1.submit();
}
</script>
</head>
<body topmargin="10" >
<form id="form1" name="form1" method="post" action="untbu029p.jsp" onSubmit="return checkField()" target="_blank">
		<input type="hidden" name="q_enterOrg" value="<%=user.getOrganID()%>">
		<input type="hidden" name="isAdminManager" value="<%=user.getIsAdminManager()%>">
		<input type="hidden" name="isOrganManager" value="<%=user.getIsOrganManager()%>">
		<input type="hidden" name="organID" value="<%=user.getOrganID()%>">
	    <input type="hidden" name="q_dataState" value="1">	
	    <input type="hidden" name="q_nonProof" value="Y">
	    
<table class="bg" width="100%" cellspacing="0" cellpadding="0" align="center"><tr><td>
	<table class="queryTable"  border="1">
	<tr>
        <td class="td_form" colspan="4" style="text-align:center">建物所有權狀備查簿<font color="red">(A4 橫式)</font></td>
	</tr>
	<tr>
		<td class="queryTDLable">領狀情形：</td>
		<td class="queryTDInput">
			<select class="field_Q" type="select" name="q_drawState">
			<option value='' >請選擇</option>
			<option value='1' >有領狀資料</option>
			<option value='2' >無領狀資料</option>
			</select>
		</td>
			<td class="queryTDLable">權屬：</td>
		<td class="queryTDInput">
			<select class="field_Q" type="select" name="q_ownership">
			<option value='' >請選擇</option>   
            <option value='1' selected>市有</option>
            <option value='2' >國有</option>
			
			</select>
		</td>
	</tr>
	<tr>
		<td class="queryTDLable">入帳日期：</td>
		<td class="queryTDInput">
			起<%=util.View.getPopCalndar("field_Q","q_enterDateS",obj.getQ_enterDateS())%>&nbsp;~&nbsp;
			訖<%=util.View.getPopCalndar("field_Q","q_enterDateE",obj.getQ_enterDateE())%>
		</td>
		<td class="queryTDLable">增加單審核：</td>
		<td class="queryTDInput">
			<select class="field_Q" type="select" name="q_verify">
			<%=util.View.getYNOption(obj.getQ_verify())%>
			
			</select>
		</td>
	</tr>
	<tr>	
		<td class="queryTDLable">建物標示：</td>
		<td class="queryTDInput" >
			<select class="field_Q" type="select" name="q_signNo1" onChange="changeSignNo1('q_signNo1','q_signNo2','','');">
			<%=util.View.getOption("select signNo, signName from SYSCA_Sign where signNo like '_000000' order by seqno","E000000")%>
			</select>
					
		
		
			<select class="field_Q" type="select" name="q_signNo2" >
				<script>changeSignNo1('q_signNo1','q_signNo2','','<%=obj.getQ_signNo2()%>');</script>
			</select>
		</td>
		<td class="queryTDLable">權狀審核：</td>
		<td class="queryTDInput">
			<select class="field_Q" type="select" name="q_proofVerify">
			<%=util.View.getYNOption(obj.getQ_proofVerify())%>
			
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
		<td class="queryTDLable">基金財產：</td>
		<td class="queryTDInput">
			<select class="field_Q" type="select" name="q_fundType">
				<script>changeEnterOrg_FundType(form1.q_enterOrg.value,'<%=user.getOrganID()%>','q_fundType','<%=obj.getQ_fundType()%>','<%=user.getIsAdminManager()%>','<%=user.getIsOrganManager()%>');</script>
			</select>
		</td>
	</tr>
	<tr>	
		<td class="queryTDLable">珍貴財產：</td>
		<td class="queryTDInput">
			<select class="field_Q" type="select" name="q_valuable">
			<%=util.View.getYNOption(obj.getQ_valuable())%>
			</select>
		</td>
		<td class="queryTDLable">抵繳遺產稅：</td>
		<td class="queryTDInput">
			<select class="field_Q" type="select" name="q_taxCredit">
			<%=util.View.getYNOption(obj.getQ_taxCredit())%>
			</select>
		</td>
	</tr>
	<tr>	
		
		<td class="queryTDLable">領狀日期：</td>
		<td class="queryTDInput" >
			起<%=util.View.getPopCalndar("field_Q","q_drawDateS",obj.getQ_drawDateS())%>&nbsp;~&nbsp;
			訖<%=util.View.getPopCalndar("field_Q","q_drawDateE",obj.getQ_drawDateE())%>
		</td>
		<td class="queryTDLable">領狀人：</td>
		<td class="queryTDInput" >
			<input class="field_Q" type="text" name="q_drawName" size="20" maxlength="20" value="<%=obj.getQ_drawName()%>">
		</td>
	</tr>
	<tr>	
		<td class="queryTDLable">領狀原因：</td>
		<td class="queryTDInput" colspan="4">
			<select class="field_Q" type="select" name="q_drawCause">
			<%=util.View.getOption("select codeID, codeName from SYSCA_Code where codeKindID='DCA' ", obj.getQ_drawCause())%>
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
