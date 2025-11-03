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
unt.rf.UNTRF008Q untrfReduceQuery = (unt.rf.UNTRF008Q)request.getAttribute("UNTRF008Q");
%>
<!--Query區============================================================-->
	<table class="queryTable"  border="1">
	<tr>
		<td class="queryTDInput" colspan="4">
			<input name="querySelect" type="radio" class="field_Q" value="AddProof" <%=untrfReduceQuery.getQueryTab1()%>>
			&nbsp;<font color="green">查詢減損單資料</font>&nbsp;&nbsp;&nbsp;		
			<input name="querySelect" type="radio" class="field_Q" value="detail" <%=untrfReduceQuery.getQueryTab2()%>>
			&nbsp;<font color="green">查詢減損單明細資料</font>
		</td>
	</tr>				
	<tr>
		<td class="queryTDLable">入帳機關：</td>
		<td class="queryTDInput">
			<%=util.View.getPopOrgan("field_Q","q_enterOrg",untrfReduceQuery.getQ_enterOrg(),untrfReduceQuery.getQ_enterOrgName(),"N&js=alteredEnterOrg();")%>
		</td>
		<td class="queryTDLable">權屬：</td>
		<td class="queryTDInput">
			<select class="field_Q" type="select" name="q_ownership">
			<%=util.View.getOnwerOption(untrfReduceQuery.getQ_ownership())%>
			</select>
		</td>
	</tr>
	<tr>
		<td class="queryTDLable">電腦單號：</td>
		<td class="queryTDInput">
			起<input class="field_Q" type="text" name="q_caseNoS" size="10" maxlength="10" value="<%=untrfReduceQuery.getQ_caseNoS()%>">&nbsp;~&nbsp;
			訖<input class="field_Q" type="text" name="q_caseNoE" size="10" maxlength="10" value="<%=untrfReduceQuery.getQ_caseNoE()%>">			
		</td>
		<td class="queryTDLable">減損日期：</td>
		<td class="queryTDInput">
			起<%=util.View.getPopCalndar("field_Q","q_reduceDateS",untrfReduceQuery.getQ_reduceDateS())%>&nbsp;~&nbsp;
			訖<%=util.View.getPopCalndar("field_Q","q_reduceDateE",untrfReduceQuery.getQ_reduceDateE())%>
		</td>
	</tr>
	<tr>
		<td class="queryTDLable">財產編號：</td>		
		<td class="queryTDInput" colspan="3">		
 			起<%=util.View.getPopProperty("field_Q","q_propertyNoS",untrfReduceQuery.getQ_propertyNoS(),untrfReduceQuery.getQ_propertyNoSName(),"111")%>
			訖<%=util.View.getPopProperty("field_Q","q_propertyNoE",untrfReduceQuery.getQ_propertyNoE(),untrfReduceQuery.getQ_propertyNoEName(),"111")%>
		</td>
	</tr>
	<tr>
		<td class="queryTDLable" >財產分號：</td>
		<td class="queryTDInput" >
			起<input class="field_Q" type="text" name="q_serialNoS" size="7" maxlength="7" value="<%=untrfReduceQuery.getQ_serialNoS()%>" onChange="addChar(this ,7);">&nbsp;~&nbsp;
			訖<input class="field_Q" type="text" name="q_serialNoE" size="7" maxlength="7" value="<%=untrfReduceQuery.getQ_serialNoE()%>" onChange="addChar(this ,7);">
		</td>
		<td class="queryTDLable">核准機關：</td>
		<td class="queryTDInput">
			<select class="field_Q" type="select" name="q_approveOrg">
			<%=util.View.getOption("select  codeID, codeName from SYSCA_Code where codeKindID='APO' ", "")%>
			</select>
		</td>
	</tr>
	<tr>
		<td class="queryTDLable">入帳：</td>
		<td class="queryTDInput" colspan="3">
			<select class="field_Q" type="select" name="q_verify">
				<%=util.View.getYNOption(untrfReduceQuery.getQ_verify())%>
			</select>					
		</td>
	</tr>
	<tr>
		<td class="queryTDLable">財產性質：</td>
		<td class="queryTDInput">
			<select class="field_Q" type="select" name="q_propertyKind">
				<%=util.View.getOption("select codeID, codeName from SYSCA_Code where codeKindID='PKD' ",untrfReduceQuery.getQ_propertyKind())%>
			</select>		
		</td>	
		<td class="queryTDLable">基金財產：</td>
		<td class="queryTDInput">
			<select class="field_Q" type="select" name="q_fundType">
				<script>
					changeEnterOrg_FundType(form1.q_enterOrg.value,'<%=user.getOrganID()%>','q_fundType','<%=untrfReduceQuery.getQ_fundType()%>','<%=user.getIsAdminManager()%>','<%=user.getIsOrganManager()%>');
				</script>
			</select>
		</td>				
	</tr>
	<tr>
		<td class="queryTDLable">珍貴財產：</td>
		<td class="queryTDInput">
			<select class="field_Q" type="select" name="q_valuable">
				<%=util.View.getYNOption(untrfReduceQuery.getQ_valuable())%>
			</select>		
		</td>		
		<td class="queryTDLable">抵繳遺產稅：</td>
		<td class="queryTDInput">
			<select class="field_Q" type="select" name="q_taxCredit">
				<%=util.View.getYNOption(untrfReduceQuery.getQ_taxCredit())%>
			</select>		
		</td>					
	</tr>
	<tr>
		<td class="queryTDLable">減損原因：</td>
		<td class="queryTDInput">
			<select class="field_Q" type="select" name="q_cause">
			<%=util.View.getOption("select  codeID, codeName from SYSCA_Code where codeKindID='CAC'", "")%>
			</select>
		</td>		
		<td class="queryTDLable">接管機關：</td>
		<td class="queryTDInput">
			<%=util.View.getPopOrgan("field_Q","q_newEnterOrg",untrfReduceQuery.getQ_newEnterOrg(),untrfReduceQuery.getQ_newEnterOrgName(),"Y")%>
		</td>
	</tr>
	<tr>
		<td class="queryTDLable">填單日期：</td>
		<td class="queryTDInput">
			起<%=util.View.getPopCalndar("field_Q","q_writeDateS",untrfReduceQuery.getQ_writeDateS())%>&nbsp;~&nbsp;
			訖<%=util.View.getPopCalndar("field_Q","q_writeDateE",untrfReduceQuery.getQ_writeDateE())%>
		</td>
		<td class="queryTDLable">案名：</td>
		<td class="queryTDInput">
			<input class="field_Q" type="text" name="q_caseName" size="25" maxlength="25" value="<%=untrfReduceQuery.getQ_caseName()%>">
		</td>
	</tr>
	<tr>
		<td class="queryTDLable">減損單編號：</td>
		<td class="queryTDInput" colspan="3">
			<input class="field_Q" type="text" name="q_proofDoc" size="10" maxlength="20" value="<%=untrfReduceQuery.getQ_proofDoc()%>"> 字
			起<input class="field_Q" type="text" name="q_proofNoS" size="10" maxlength="20" value="<%=untrfReduceQuery.getQ_proofNoS()%>" onChange="addChar(this ,7);">&nbsp;~&nbsp;
			訖<input class="field_Q" type="text" name="q_proofNoE" size="10" maxlength="20" value="<%=untrfReduceQuery.getQ_proofNoE()%>" onChange="addChar(this ,7);"> 號
		</td>
	</tr>
	<tr>
		<td class="queryTDInput" colspan="4" style="text-align:center;">
			<input class="toolbar_default" followPK="false" type="submit" name="querySubmit" value="確　　定" >
			<input class="toolbar_default" followPK="false" type="button" name="queryCannel" value="取　　消" onClick="whatButtonFireEvent(this.name)">
		</td>
	</tr>
	</table>
