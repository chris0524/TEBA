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
unt.rt.UNTRT008Q untrtAdjustProofQuery = (unt.rt.UNTRT008Q)request.getAttribute("UNTRT008Q");

String queryType1 = "", queryType2 = "";
if ("adjustDetail".equals(untrtAdjustProofQuery.getQueryType())) queryType2 = "checked=\"CHECKED\"";	
else if("adjustProof".equals(untrtAdjustProofQuery.getQueryType())) queryType1 = "checked=\"CHECKED\"";

%>
<!--Query區============================================================-->
<table class="queryTable"  border="1">
	<tr>
	  <td class="queryTDLable" >查詢類別：</td>
	  <td class="queryTDInput" colspan="3">
	  <input name="querySelect" type="radio" class="field_Q" value="adjustProof" <%=queryType1%>>
      <font color="green">增減值單</font>&nbsp;&nbsp;&nbsp;	
	  <input name="querySelect" type="radio" class="field_Q" value="adjustDetail" <%=queryType2%>>
	  <font color="green">增減值單明細</font>
	  </td>
	</tr>
	<tr>
		<td class="queryTDLable" >入帳機關：</td>
		<td class="queryTDInput">
			<%=util.View.getPopOrgan("field_Q","q_enterOrg",untrtAdjustProofQuery.getQ_enterOrg(),untrtAdjustProofQuery.getQ_enterOrgName(),"N&js=alteredEnterOrg();")%>
		</td>
		<td class="queryTDLable">權屬：</td>
		<td class="queryTDInput">
			<select class="field_Q" type="select" name="q_ownership">
				<%=util.View.getOnwerOption(untrtAdjustProofQuery.getQ_ownership())%>
			</select>
		</td>		
	</tr>
	<tr>
		<td class="queryTDLable">電腦單號：</td>
		<td class="queryTDInput">
			起<input class="field_Q" type="text" name="q_caseNoS" size="10" maxlength="10" value="<%=untrtAdjustProofQuery.getQ_caseNoS()%>">&nbsp;~&nbsp;
			訖<input class="field_Q" type="text" name="q_caseNoE" size="10" maxlength="10" value="<%=untrtAdjustProofQuery.getQ_caseNoE()%>">			
		</td>	
		<td class="queryTDLable">入帳日期：</td>
		<td class="queryTDInput">
			起<%=util.View.getPopCalndar("field_Q","q_adjustDateS",untrtAdjustProofQuery.getQ_adjustDateS())%>&nbsp;~&nbsp;
			訖<%=util.View.getPopCalndar("field_Q","q_adjustDateE",untrtAdjustProofQuery.getQ_adjustDateE())%>
		</td>	
	</tr>
	<tr>
		<td class="queryTDLable">財產編號：</td>
		<td colspan="3" class="queryTDInput">
			起<%=util.View.getPopProperty("field_Q","q_propertyNoS",untrtAdjustProofQuery.getQ_propertyNoS(),untrtAdjustProofQuery.getQ_propertyNoSName(),"8")%>
			訖<%=util.View.getPopProperty("field_Q","q_propertyNoE",untrtAdjustProofQuery.getQ_propertyNoE(),untrtAdjustProofQuery.getQ_propertyNoEName(),"8")%>
		</td>
	</tr>
	<tr>
		<td class="queryTDLable">財產分號：</td>
		<td class="queryTDInput">
			起<input class="field_Q" type="text" name="q_serialNoS" size="7" maxlength="7" value="<%=untrtAdjustProofQuery.getQ_serialNoS()%>" onChange="addChar(this ,7);">&nbsp;~&nbsp;
			訖<input class="field_Q" type="text" name="q_serialNoE" size="7" maxlength="7" value="<%=untrtAdjustProofQuery.getQ_serialNoE()%>" onChange="addChar(this ,7);">
		</td>			
		<td class="queryTDLable">入帳：</td>
		<td class="queryTDInput">
			<select class="field_Q" type="select" name="q_verify">
				<%=util.View.getYNOption(untrtAdjustProofQuery.getQ_verify())%>
			</select>
		</td>
	</tr>
	<tr>
		<td class="queryTDLable">土地標示：</td>
		<td class="queryTDInput" colspan="3">
			<select class="field_Q" type="select" name="q_signNo1" onChange="changeSignNo1('q_signNo1','q_signNo2','q_signNo3','');">
			<%=util.View.getOption("select signNo, signName from SYSCA_Sign where signNo like '_000000' order by seqno",untrtAdjustProofQuery.getQ_signNo1())%>
			</select>
			<select class="field_Q" type="select" name="q_signNo2" onChange="changeSignNo2('q_signNo1','q_signNo2','q_signNo3','');">
				<script>changeSignNo1('q_signNo1','q_signNo2','q_signNo3','<%=untrtAdjustProofQuery.getQ_signNo2()%>');</script>
			</select>		
			<select class="field_Q" type="select" name="q_signNo3">
				<script>changeSignNo2('q_signNo1','q_signNo2','q_signNo3','<%=untrtAdjustProofQuery.getQ_signNo3()%>');</script>
			</select>
			&nbsp;
			<input class="field_Q" type="text" name="q_signNo4" size="4" maxlength="4" value="<%=untrtAdjustProofQuery.getQ_signNo4()%>">&nbsp;-
			<input class="field_Q" type="text" name="q_signNo5" size="4" maxlength="4" value="<%=untrtAdjustProofQuery.getQ_signNo5()%>">		
		</td>
	</tr>
	<tr>
		<td class="queryTDLable">財產性質：</td>
		<td class="queryTDInput">
			<select class="field_Q" type="select" name="q_propertyKind">			
				<%=util.View.getOption("select codeID, codeName from SYSCA_Code where codeKindID='PKD' ",untrtAdjustProofQuery.getQ_propertyKind())%>
			</select>		
		</td>			
		<td class="queryTDLable">基金財產：</td>
		<td class="queryTDInput" colspan="3">
			<select class="field_Q" type="select" name="q_fundType">
				<script>changeEnterOrg_FundType(form1.q_enterOrg.value,'<%=user.getOrganID()%>','q_fundType','<%=untrtAdjustProofQuery.getQ_fundType()%>','<%=user.getIsAdminManager()%>','<%=user.getIsOrganManager()%>');</script>
			</select>
		</td>		
	</tr>
	<tr>
		<td class="queryTDLable">增減值原因：</td>
		<td class="queryTDInput" colspan="3">
			<select class="field_Q" type="select" name="q_cause">
				<%=util.View.getOption("select codeID, codeName from SYSCA_Code where codeKindID='CAE'", untrtAdjustProofQuery.getQ_cause())%>
			</select>
		</td>	
	</tr>
	<tr>
		<td class="queryTDLable">填單日期：</td>
		<td class="queryTDInput">
			起<%=util.View.getPopCalndar("field_Q","q_writeDateS",untrtAdjustProofQuery.getQ_writeDateS())%>&nbsp;~&nbsp;
			訖<%=util.View.getPopCalndar("field_Q","q_writeDateE",untrtAdjustProofQuery.getQ_writeDateE())%>			
		</td>
		<td class="queryTDLable">增減值案名：</td>
		<td class="queryTDInput">
			<input class="field_Q" type="text" name="q_caseName" size="25" maxlength="25" value="<%=untrtAdjustProofQuery.getQ_caseName()%>">
		</td>
	</tr>
	<tr>
		<td class="queryTDLable">增減值單編號：</td>
		<td class="queryTDInput" colspan="3">		
			<input class="field_Q" type="text" name="q_proofDoc" size="15" maxlength="20" value="<%=untrtAdjustProofQuery.getQ_proofDoc()%>">字
			起<input class="field_Q" type="text" name="q_proofNoS" size="10" maxlength="20" value="<%=untrtAdjustProofQuery.getQ_proofNoS()%>" onChange="addChar(this ,7);">&nbsp;~&nbsp;
			訖<input class="field_Q" type="text" name="q_proofNoE" size="10" maxlength="20" value="<%=untrtAdjustProofQuery.getQ_proofNoE()%>" onChange="addChar(this ,7);">號
		</td>
	</tr>
	<tr>
		<td class="queryTDInput" colspan="4" style="text-align:center;">
			<input class="toolbar_default" followPK="false" type="submit" name="querySubmit" value="確　　定" >
			<input class="toolbar_default" followPK="false" type="button" name="queryCannel" value="取　　消" onClick="whatButtonFireEvent(this.name)">
		</td>
	</tr>
</table>
