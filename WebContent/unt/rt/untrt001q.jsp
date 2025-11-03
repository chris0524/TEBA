<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../home/head.jsp" %>
<script language="javascript" src="../../js/changeEnterOrg_FundType.js"></script>
<script type="text/javascript" src="../../js/function.js"></script>
<script language="javascript">
function alteredEnterOrg(){
	changeEnterOrg_FundType(form1.q_enterOrg.value,'<%=user.getOrganID()%>','q_fundType','','<%=user.getIsAdminManager()%>','<%=user.getIsOrganManager()%>');
}
</script>
<%
unt.rt.UNTRT001Q untrtAddProofQuery = (unt.rt.UNTRT001Q)request.getAttribute("UNTRT001Q");

String queryType1 = "", queryType2 = "";
if ("AddDetail".equals(untrtAddProofQuery.getQueryType())) queryType2 = "checked=\"CHECKED\"";	
else if("addProof".equals(untrtAddProofQuery.getQueryType())) queryType1 = "checked=\"CHECKED\"";

%>
<!--Query區============================================================-->
<table class="queryTable"  border="1">
	<tr>
	  <td class="queryTDInput" colspan="4">
	  <input name="querySelect" type="radio" class="field_Q" value="addProof" <%=queryType1%>>
      <font color="green">查詢權利增加單</font>&nbsp;&nbsp;&nbsp;		
	  <input name="querySelect" type="radio" class="field_Q" value="AddDetail" <%=queryType2%>>
	  <font color="green">查詢權利標的資料</font>
	  </td>
	</tr>
	<tr>
		<td class="queryTDLable" >入帳機關：</td>
		<td class="queryTDInput" >
			<%=util.View.getPopOrgan("field_Q","q_enterOrg",untrtAddProofQuery.getQ_enterOrg(),untrtAddProofQuery.getQ_enterOrgName(),"N&js=alteredEnterOrg();")%>
		</td>
		<td class="queryTDLable">權屬：</td>
		<td class="queryTDInput">
			<select class="field_Q" type="select" name="q_ownership">
			<%=util.View.getOnwerOption(untrtAddProofQuery.getQ_ownership())%>
			</select>
		</td>		
	</tr>
	<tr>
		<td class="queryTDLable">電腦單號：</td>
		<td class="queryTDInput">
			起<input class="field_Q" type="text" name="q_caseNoS" size="10" maxlength="10" value="<%=untrtAddProofQuery.getQ_caseNoS()%>">&nbsp;~&nbsp;
			訖<input class="field_Q" type="text" name="q_caseNoE" size="10" maxlength="10" value="<%=untrtAddProofQuery.getQ_caseNoE()%>">			
		</td>	
		<td class="queryTDLable">入帳日期：</td>
		<td class="queryTDInput">
			起<%=util.View.getPopCalndar("field_Q","q_enterDateS",untrtAddProofQuery.getQ_enterDateS())%>&nbsp;~&nbsp;
			訖<%=util.View.getPopCalndar("field_Q","q_enterDateE",untrtAddProofQuery.getQ_enterDateE())%>
		</td>		
	</tr>
	<tr>
		<td class="queryTDLable">財產編號：</td>
		<td colspan="3" class="queryTDInput">
			起<%=util.View.getPopProperty("field_Q","q_propertyNoS",untrtAddProofQuery.getQ_propertyNoS(),untrtAddProofQuery.getQ_propertyNoSName(),"8")%>&nbsp;~&nbsp;
			訖<%=util.View.getPopProperty("field_Q","q_propertyNoE",untrtAddProofQuery.getQ_propertyNoE(),untrtAddProofQuery.getQ_propertyNoEName(),"8")%>
		</td>
	</tr>
	<tr>
		<td class="queryTDLable">財產分號：</td>
		<td class="queryTDInput">
			起<input class="field_Q" type="text" name="q_serialNoS" size="7" maxlength="7" value="<%=untrtAddProofQuery.getQ_serialNoS()%>" onChange="addChar(this ,7);">&nbsp;~&nbsp;
			訖<input class="field_Q" type="text" name="q_serialNoE" size="7" maxlength="7" value="<%=untrtAddProofQuery.getQ_serialNoE()%>" onChange="addChar(this ,7);">
		</td>			
		<td class="queryTDLable">資料狀態：</td>
		<td class="queryTDInput">
			<select class="field_Q" type="select" name="q_dataState">
				<%=util.View.getTextOption("1;現存;2;已減損",untrtAddProofQuery.getQ_dataState())%>			
			</select>			
		</td>	
	</tr>
	<tr>
		<td class="queryTDLable">土地標示：</td>
		<td class="queryTDInput" colspan="3">
			<select class="field_Q" type="select" name="q_signNo1" onChange="changeSignNo1('q_signNo1','q_signNo2','q_signNo3','');">
			<%=util.View.getOption("select signNo, signName from SYSCA_Sign where signNo like '_000000' order by seqno",untrtAddProofQuery.getQ_signNo1())%>
			</select>
			<select class="field_Q" type="select" name="q_signNo2" onChange="changeSignNo2('q_signNo1','q_signNo2','q_signNo3','');">
				<script>changeSignNo1('q_signNo1','q_signNo2','q_signNo3','<%=untrtAddProofQuery.getQ_signNo2()%>');</script>
			</select>		
			<select class="field_Q" type="select" name="q_signNo3">
				<script>changeSignNo2('q_signNo1','q_signNo2','q_signNo3','<%=untrtAddProofQuery.getQ_signNo3()%>');</script>
			</select>
			&nbsp;
			<input class="field_Q" type="text" name="q_signNo4" size="4" maxlength="4" value="<%=untrtAddProofQuery.getQ_signNo4()%>">&nbsp;-
			<input class="field_Q" type="text" name="q_signNo5" size="4" maxlength="4" value="<%=untrtAddProofQuery.getQ_signNo5()%>">		
		</td>
	</tr>
	<tr>
		<td class="queryTDLable">入帳：</td>
		<td class="queryTDInput">
			<select class="field_Q" type="select" name="q_verify">
			<%=util.View.getYNOption(untrtAddProofQuery.getQ_verify())%>
			</select>
		</td>
		<td class="queryTDLable">月結：</td>
		<td class="queryTDInput">
			<select class="field_Q" type="select" name="q_closing">
			<%=util.View.getYNOption(untrtAddProofQuery.getQ_closing())%>
			</select>
		</td>		
	</tr>
	<tr>
		<td class="queryTDLable">財產性質：</td>
		<td class="queryTDInput">
			<select class="field_Q" type="select" name="q_propertyKind">			
				<%=util.View.getOption("select codeID, codeName from SYSCA_Code where codeKindID='PKD' ",untrtAddProofQuery.getQ_propertyKind())%>
			</select>		
		</td>			
		<td class="queryTDLable">基金財產：</td>
		<td class="queryTDInput" colspan="4">
			<select class="field_Q" type="select" name="q_fundType">
				<script>changeEnterOrg_FundType(form1.q_enterOrg.value,'<%=user.getOrganID()%>','q_fundType','<%=untrtAddProofQuery.getQ_fundType()%>','<%=user.getIsAdminManager()%>','<%=user.getIsOrganManager()%>');</script>
			</select>
		</td>		
	</tr>
	<tr>
		<td class="queryTDLable">增加原因：</td>
		<td class="queryTDInput" colspan="3">
			<select class="field_Q" type="select" name="q_cause">
				<%=util.View.getOption("select codeID, codeName from SYSCA_Code where codeKindID='CAB'", untrtAddProofQuery.getQ_cause())%>
			</select>
		</td>	
	</tr>	
	<tr>
		<td class="queryTDLable">填單日期：</td>
		<td class="queryTDInput">
			起<%=util.View.getPopCalndar("field_Q","q_writeDateS",untrtAddProofQuery.getQ_writeDateS())%>&nbsp;~&nbsp;
			訖<%=util.View.getPopCalndar("field_Q","q_writeDateE",untrtAddProofQuery.getQ_writeDateE())%>			
		</td>
		<td class="queryTDLable">案名：</td>
		<td class="queryTDInput">
			<input class="field_Q" type="text" name="q_caseName" size="20" maxlength="25" value="<%=untrtAddProofQuery.getQ_caseName()%>">
		</td>		
	</tr>
	<tr>
		<td class="queryTDLable">增加單編號：</td>
		<td class="queryTDInput"  colspan="4">		
			<input class="field_Q" type="text" name="q_proofDoc" size="15" maxlength="20" value="<%=untrtAddProofQuery.getQ_proofDoc()%>">字
			起<input class="field_Q" type="text" name="q_proofNoS" size="10" maxlength="20" value="<%=untrtAddProofQuery.getQ_proofNoS()%>" onChange="addChar(this ,7);">&nbsp;~&nbsp;
			訖<input class="field_Q" type="text" name="q_proofNoE" size="10" maxlength="20" value="<%=untrtAddProofQuery.getQ_proofNoE()%>" onChange="addChar(this ,7);">號
		</td>
	</tr>	
	<tr>
		<td class="queryTDInput" colspan="4" style="text-align:center;">
			<input class="toolbar_default" followPK="false" type="submit" name="querySubmit" value="確　　定" >
			<input class="toolbar_default" followPK="false" type="button" name="queryCannel" value="取　　消" onClick="whatButtonFireEvent(this.name)">
		</td>
	</tr>
</table>
