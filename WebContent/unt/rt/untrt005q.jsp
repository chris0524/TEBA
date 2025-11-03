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
unt.rt.UNTRT005Q untrtReduceProofQuery = (unt.rt.UNTRT005Q)request.getAttribute("UNTRT005Q");
String queryType1 = "", queryType2 = "";
if ("reduceDetail".equals(untrtReduceProofQuery.getQueryType())) queryType2 = "checked=\"CHECKED\"";	
else if("reduceProof".equals(untrtReduceProofQuery.getQueryType())) queryType1 = "checked=\"CHECKED\"";
%>
<!--Query區============================================================-->
<table class="queryTable"  border="1">
	<tr>
	  <td class="queryTDLable" >查詢類別：</td>
	  <td class="queryTDInput" colspan="3">
	  <input name="querySelect" type="radio" class="field_Q" value="reduceProof" <%=queryType1%>>
      <font color="green">減損單</font>&nbsp;&nbsp;&nbsp;		
	  <input name="querySelect" type="radio" class="field_Q" value="reduceDetail" <%=queryType2%>>
	  <font color="green">減損單明細</font>
	  </td>
	</tr>
	<tr>
		<td class="queryTDLable" >入帳機關：</td>
		<td class="queryTDInput">
			<%=util.View.getPopOrgan("field_Q","q_enterOrg",untrtReduceProofQuery.getQ_enterOrg(),untrtReduceProofQuery.getQ_enterOrgName(),"N&js=alteredEnterOrg();")%>
		</td>
		<td class="queryTDLable">權屬：</td>
		<td class="queryTDInput">
			<select class="field_Q" type="select" name="q_ownership">
				<%=util.View.getOnwerOption(untrtReduceProofQuery.getQ_ownership())%>
			</select>
		</td>		
	</tr>
	<tr>
		<td class="queryTDLable">電腦單號：</td>
		<td class="queryTDInput">
			起<input class="field_Q" type="text" name="q_caseNoS" size="10" maxlength="10" value="<%=untrtReduceProofQuery.getQ_caseNoS()%>">&nbsp;~&nbsp;
			訖<input class="field_Q" type="text" name="q_caseNoE" size="10" maxlength="10" value="<%=untrtReduceProofQuery.getQ_caseNoE()%>">			
		</td>	
		<td class="queryTDLable">入帳日期：</td>
		<td class="queryTDInput">
			起<%=util.View.getPopCalndar("field_Q","q_reduceDateS",untrtReduceProofQuery.getQ_reduceDateS())%>&nbsp;~&nbsp;
			訖<%=util.View.getPopCalndar("field_Q","q_reduceDateE",untrtReduceProofQuery.getQ_reduceDateE())%>
		</td>	
	</tr>
	<tr>
		<td class="queryTDLable">財產編號：</td>
		<td colspan="3" class="queryTDInput">
			起<%=util.View.getPopProperty("field_Q","q_propertyNoS",untrtReduceProofQuery.getQ_propertyNoS(),untrtReduceProofQuery.getQ_propertyNoSName(),"8")%>&nbsp;
			訖<%=util.View.getPopProperty("field_Q","q_propertyNoE",untrtReduceProofQuery.getQ_propertyNoE(),untrtReduceProofQuery.getQ_propertyNoEName(),"8")%>
		</td>
	</tr>
	<tr>
		<td class="queryTDLable">財產分號：</td>
		<td class="queryTDInput">
			起<input class="field_Q" type="text" name="q_serialNoS" size="7" maxlength="7" value="<%=untrtReduceProofQuery.getQ_serialNoS()%>" onChange="addChar(this ,7);">&nbsp;~&nbsp;
			訖<input class="field_Q" type="text" name="q_serialNoE" size="7" maxlength="7" value="<%=untrtReduceProofQuery.getQ_serialNoE()%>" onChange="addChar(this ,7);">
		</td>			
		<td class="queryTDLable">入帳：</td>
		<td class="queryTDInput">
			<select class="field_Q" type="select" name="q_verify">
				<%=util.View.getYNOption(untrtReduceProofQuery.getQ_verify())%>
			</select>
		</td>
	</tr>
	<tr>
		<td class="queryTDLable">土地標示：</td>
		<td class="queryTDInput" colspan="3">
			<select class="field_Q" type="select" name="q_signNo1" onChange="changeSignNo1('q_signNo1','q_signNo2','q_signNo3','');">
			<%=util.View.getOption("select signNo, signName from SYSCA_Sign where signNo like '_000000' order by seqno",untrtReduceProofQuery.getQ_signNo1())%>
			</select>
			<select class="field_Q" type="select" name="q_signNo2" onChange="changeSignNo2('q_signNo1','q_signNo2','q_signNo3','');">
				<script>changeSignNo1('q_signNo1','q_signNo2','q_signNo3','<%=untrtReduceProofQuery.getQ_signNo2()%>');</script>
			</select>		
			<select class="field_Q" type="select" name="q_signNo3">
				<script>changeSignNo2('q_signNo1','q_signNo2','q_signNo3','<%=untrtReduceProofQuery.getQ_signNo3()%>');</script>
			</select>
			&nbsp;
			<input class="field_Q" type="text" name="q_signNo4" size="4" maxlength="4" value="<%=untrtReduceProofQuery.getQ_signNo4()%>">&nbsp;-
			<input class="field_Q" type="text" name="q_signNo5" size="4" maxlength="4" value="<%=untrtReduceProofQuery.getQ_signNo5()%>">		
		</td>
	</tr>
	<tr>
		<td class="queryTDLable">財產性質：</td>
		<td class="queryTDInput">
			<select class="field_Q" type="select" name="q_propertyKind">			
				<%=util.View.getOption("select codeID, codeName from SYSCA_Code where codeKindID='PKD' ",untrtReduceProofQuery.getQ_propertyKind())%>
			</select>		
		</td>			
		<td class="queryTDLable">基金財產：</td>
		<td class="queryTDInput" colspan="3">
			<select class="field_Q" type="select" name="q_fundType">
				<script>changeEnterOrg_FundType(form1.q_enterOrg.value,'<%=user.getOrganID()%>','q_fundType','<%=untrtReduceProofQuery.getQ_fundType()%>','<%=user.getIsAdminManager()%>','<%=user.getIsOrganManager()%>');</script>
			</select>
		</td>		
	</tr>
	<tr>
		<td class="queryTDLable">減損原因：</td>
		<td class="queryTDInput" colspan="3">
			<select class="field_Q" type="select" name="q_cause">
				<%=util.View.getOption("select codeID, codeName from SYSCA_Code where codeKindID='CAC'", untrtReduceProofQuery.getQ_cause())%>
			</select>
		</td>	
	</tr>	
	<tr>
		<td class="queryTDLable">填單日期：</td>
		<td class="queryTDInput">
			起<%=util.View.getPopCalndar("field_Q","q_writeDateS",untrtReduceProofQuery.getQ_writeDateS())%>&nbsp;~&nbsp;
			訖<%=util.View.getPopCalndar("field_Q","q_writeDateE",untrtReduceProofQuery.getQ_writeDateE())%>			
		</td>
		<td class="queryTDLable">減損案名：</td>
		<td class="queryTDInput">
			<input class="field_Q" type="text" name="q_caseName" size="20" maxlength="25" value="<%=untrtReduceProofQuery.getQ_caseName()%>">
		</td>		
	</tr>
	<tr>
		<td class="queryTDLable">減損單編號：</td>
		<td class="queryTDInput" colspan="3">		
			<input class="field_Q" type="text" name="q_proofDoc" size="15" maxlength="20" value="<%=untrtReduceProofQuery.getQ_proofDoc()%>">字
			起<input class="field_Q" type="text" name="q_proofNoS" size="10" maxlength="20" value="<%=untrtReduceProofQuery.getQ_proofNoS()%>" onChange="addChar(this ,7);">&nbsp;~&nbsp;
			訖<input class="field_Q" type="text" name="q_proofNoE" size="10" maxlength="20" value="<%=untrtReduceProofQuery.getQ_proofNoE()%>" onChange="addChar(this ,7);">號
		</td>
	</tr>	
	<tr>
		<td class="queryTDInput" colspan="4" style="text-align:center;">
			<input class="toolbar_default" followPK="false" type="submit" name="querySubmit" value="確　　定" >
			<input class="toolbar_default" followPK="false" type="button" name="queryCannel" value="取　　消" onClick="whatButtonFireEvent(this.name)">
		</td>
	</tr>
</table>
