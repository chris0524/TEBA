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
unt.mp.UNTMP014Q untmpReduceQuery = (unt.mp.UNTMP014Q)request.getAttribute("UNTMP014Q");
%>
<!--Query區============================================================-->
	<table class="queryTable"  border="1">
	<tr>
		<td class="queryTDInput" colspan="4">
			<input name="querySelect" type="radio" class="field_Q" value="AddProof" <%=untmpReduceQuery.getQueryTab1()%>>
			&nbsp;<font color="green">查詢減損單資料</font>&nbsp;&nbsp;&nbsp;		
			<input name="querySelect" type="radio" class="field_Q" value="detail" <%=untmpReduceQuery.getQueryTab2()%>>
			&nbsp;<font color="green">查詢減損單明細資料</font>
		</td>
	</tr>				
	<tr>
		<td class="queryTDLable">入帳機關：</td>
		<td class="queryTDInput">
			<%=util.View.getPopOrgan("field_Q","q_enterOrg",untmpReduceQuery.getQ_enterOrg(),untmpReduceQuery.getQ_enterOrgName(),"N&js=alteredEnterOrg();")%>
		</td>
		<td class="queryTDLable">權屬：</td>
		<td class="queryTDInput">
			<select class="field_Q" type="select" name="q_ownership">
				<%=util.View.getOnwerOption(untmpReduceQuery.getQ_ownership())%>
			</select>
		</td>
	</tr>
	<tr>
		<td class="queryTDLable">電腦單號：</td>
		<td class="queryTDInput">
			起<input class="field_Q" type="text" name="q_caseNoS" size="10" maxlength="10" value="<%=untmpReduceQuery.getQ_caseNoS()%>">&nbsp;~&nbsp;
			訖<input class="field_Q" type="text" name="q_caseNoE" size="10" maxlength="10" value="<%=untmpReduceQuery.getQ_caseNoE()%>">			
		</td>
		<td class="queryTDLable">入帳日期：</td>
		<td class="queryTDInput">
			起<%=util.View.getPopCalndar("field_Q","q_reduceDateS",untmpReduceQuery.getQ_reduceDateS())%>&nbsp;~&nbsp;
			訖<%=util.View.getPopCalndar("field_Q","q_reduceDateE",untmpReduceQuery.getQ_reduceDateE())%>
		</td>
	</tr>
	<tr>
		<td class="queryTDLable">財產編號：</td>		
		<td class="queryTDInput" colspan="3">		
 			起<%=util.View.getPopProperty("field_Q","q_propertyNoS",untmpReduceQuery.getQ_propertyNoS(),untmpReduceQuery.getQ_propertyNoSName(),"3,4,5")%>
			訖<%=util.View.getPopProperty("field_Q","q_propertyNoE",untmpReduceQuery.getQ_propertyNoE(),untmpReduceQuery.getQ_propertyNoEName(),"3,4,5")%>
		</td>
	</tr>
	<tr>
		<td class="queryTDLable" >財產分號：</td>
		<td class="queryTDInput" >
			起<input class="field_Q" type="text" name="q_serialNoS" size="7" maxlength="7" value="<%=untmpReduceQuery.getQ_serialNoS()%>" onChange="addChar(this ,7);">&nbsp;~&nbsp;
			訖<input class="field_Q" type="text" name="q_serialNoE" size="7" maxlength="7" value="<%=untmpReduceQuery.getQ_serialNoE()%>" onChange="addChar(this ,7);">
		</td>
		<td class="queryTDLable">核准機關：</td>
		<td class="queryTDInput">
			<select class="field_Q" type="select" name="q_approveOrg">
				<%=util.View.getOption("select  codeID, codeName from SYSCA_Code where codeKindID='APO' ",untmpReduceQuery.getQ_approveOrg())%>
			</select>
		</td>
	</tr>	
	<tr>
		<td class="queryTDLable">入帳：</td>
		<td class="queryTDInput">
			<select class="field_Q" type="select" name="q_verify">
				<%=util.View.getYNOption(untmpReduceQuery.getQ_verify())%>
			</select>					
		</td>
		<td class="queryTDLable">珍貴財產：</td>
		<td class="queryTDInput">
			<select class="field_Q" type="select" name="q_valuable">
				<%=util.View.getYNOption(untmpReduceQuery.getQ_valuable())%>
			</select>		
		</td>							
	</tr>
	<tr>
		<td class="queryTDLable">財產性質：</td>
		<td class="queryTDInput">
			<select class="field_Q" type="select" name="q_propertyKind">
			<%=util.View.getOption("select codeID, codeName from SYSCA_Code where codeKindID='PKD' ",untmpReduceQuery.getQ_propertyKind())%>
			</select>		
		</td>	
		<td class="queryTDLable">基金財產：</td>
		<td class="queryTDInput">
			<select class="field_Q" type="select" name="q_fundType">
				<script>changeEnterOrg_FundType(form1.q_enterOrg.value,'<%=user.getOrganID()%>','q_fundType','<%=untmpReduceQuery.getQ_fundType()%>','<%=user.getIsAdminManager()%>','<%=user.getIsOrganManager()%>');</script>
			</select>
		</td>				
	</tr>		
	<tr>
		<td class="queryTDLable">減損原因：</td>
		<td class="queryTDInput">
			<select class="field_Q" type="select" name="q_cause">
			<%=util.View.getOption("select  codeID, codeName from SYSCA_Code where codeKindID='CAC' ", untmpReduceQuery.getQ_cause())%>
			</select>
		</td>		
		<td class="queryTDLable">接管機關：</td>
		<td class="queryTDInput">
			<%=util.View.getPopOrgan("field_Q","q_newEnterOrg",untmpReduceQuery.getQ_newEnterOrg(),untmpReduceQuery.getQ_newEnterOrgName(),"Y")%>
		</td>
	</tr>
	<tr>
		<td class="queryTDLable">填單日期：</td>
		<td class="queryTDInput">
			起<%=util.View.getPopCalndar("field_Q","q_writeDateS",untmpReduceQuery.getQ_writeDateS())%>&nbsp;~&nbsp;
			訖<%=util.View.getPopCalndar("field_Q","q_writeDateE",untmpReduceQuery.getQ_writeDateE())%>
		</td>
		<td class="queryTDLable">案名：</td>
		<td class="queryTDInput">
			<input class="field_Q" type="text" name="q_caseName" size="25" maxlength="25" value="<%=untmpReduceQuery.getQ_caseName()%>">
		</td>
	</tr>
	<tr>
		<td class="queryTDLable">減損單編號：</td>
		<td class="queryTDInput" colspan="3">
			<input class="field_Q" type="text" name="q_proofDoc" size="10" maxlength="20" value="<%=untmpReduceQuery.getQ_proofDoc()%>"> 字
			起<input class="field_Q" type="text" name="q_proofNoS" size="10" maxlength="20" value="<%=untmpReduceQuery.getQ_proofNoS()%>" onChange="addChar(this ,7);">&nbsp;~&nbsp;
			訖<input class="field_Q" type="text" name="q_proofNoE" size="10" maxlength="20" value="<%=untmpReduceQuery.getQ_proofNoE()%>" onChange="addChar(this ,7);"> 號
		</td>
	</tr>	 	
	<tr>
		<td class="queryTDLable">廢品處理案號：</td>
		<td class="queryTDInput">
			<input class="field_Q" type="text" name="q_dealCaseNo" size="10" maxlength="10" value="<%=untmpReduceQuery.getQ_dealCaseNo()%>">	
		</td>					
		<td class="queryTDLable">廢品處理方式：</td>
		<td class="queryTDInput">
			<select class="field_Q" type="select" name="q_reduceDeal">
			<%=util.View.getOption("select codeID, codeName from SYSCA_Code where codeKindID='RDL' ", untmpReduceQuery.getQ_reduceDeal())%>
			</select>
		</td>	
	</tr>
	<tr>
		<td class="queryTDLable">廢品轉撥單位：</td>
		<td class="queryTDInput" colspan="3">
			<%=util.View.getPopOrgan("field_Q","q_shiftOrg",untmpReduceQuery.getQ_shiftOrg(),untmpReduceQuery.getQ_shiftOrgName(),"Y")%>
		</td>
	</tr>
	<tr>
		<td class="queryTDInput" colspan="4" style="text-align:center;">
			<input class="toolbar_default" followPK="false" type="submit" name="querySubmit" value="確　　定" >
			<input class="toolbar_default" followPK="false" type="button" name="queryCannel" value="取　　消" onClick="whatButtonFireEvent(this.name)">
		</td>
	</tr>
	</table>
