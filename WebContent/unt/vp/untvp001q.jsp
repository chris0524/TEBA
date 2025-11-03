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
unt.vp.UNTVP001Q untvpAddProofQuery = (unt.vp.UNTVP001Q)request.getAttribute("UNTVP001Q");
%>
<!--Query區============================================================-->
<table class="queryTable"  border="1">
<input type="hidden" name="q_checkQuery" value="<%=untvpAddProofQuery.getQ_checkQuery()%>">
	<tr>
		<td class="queryTDLable" >入帳機關：</td>
		<td class="queryTDInput" >
			<%=util.View.getPopOrgan("field_Q","q_enterOrg",untvpAddProofQuery.getQ_enterOrg(),untvpAddProofQuery.getQ_enterOrgName(),"N&js=alteredEnterOrg();")%>
		</td>
		<td class="queryTDLable"><div align="right">權屬：</div></td>
 		<td class="queryTDInput">
 			<select class="field_Q" type="select" name="q_ownership">
 				<%=util.View.getOnwerOption(untvpAddProofQuery.getQ_ownership())%>
			</select>
      </td>
	</tr>
	<tr>
		<td class="queryTDLable">電腦單號：</td>
		<td class="queryTDInput">
			  起<input class="field_Q" type="text" name="q_caseNoS" size="12" maxlength="10" value="<%=untvpAddProofQuery.getQ_caseNoS()%>">&nbsp;~&nbsp;
			  訖<input class="field_Q" type="text" name="q_caseNoE" size="12" maxlength="10" value="<%=untvpAddProofQuery.getQ_caseNoE()%>">
		</td>
		<td class="queryTDLable">入帳日期：</td>
		<td class="queryTDInput">
			起<%=util.View.getPopCalndar("field_Q","q_enterDateS",untvpAddProofQuery.getQ_enterDateS())%>&nbsp;~&nbsp;
			訖<%=util.View.getPopCalndar("field_Q","q_enterDateE",untvpAddProofQuery.getQ_enterDateE())%>
		</td>
    </tr>
	<tr>
		<td class="queryTDLable">財產編號：</td>
		<td colspan="3" class="queryTDInput">
			起<%=util.View.getPopProperty("field_Q","q_propertyNoS",untvpAddProofQuery.getQ_propertyNoS(),untvpAddProofQuery.getQ_propertyNoSName(),"9")%>&nbsp;
			訖<%=util.View.getPopProperty("field_Q","q_propertyNoE",untvpAddProofQuery.getQ_propertyNoE(),untvpAddProofQuery.getQ_propertyNoEName(),"9")%>
		</td>
	</tr>
	<tr>
		<td class="queryTDLable">財產分號：</td>
		<td class="queryTDInput"> 
			起<input class="field_Q" type="text" name="q_serialNoS" size="7" maxlength="7" value="<%=untvpAddProofQuery.getQ_serialNoS()%>" onChange="addChar(this ,7);">&nbsp;~&nbsp;
			訖<input class="field_Q" type="text" name="q_serialNoE" size="7" maxlength="7" value="<%=untvpAddProofQuery.getQ_serialNoE()%>" onChange="addChar(this ,7);">
		</td>
		<td class="queryTDLable">資料狀態：</td>
		<td class="queryTDInput">
			<select class="field_Q" type="select" name="q_dataState">
				<%=util.View.getTextOption("1;現存;2;已減損",untvpAddProofQuery.getQ_dataState())%>			
			</select>			
		</td>	
	</tr>
	<tr>
		<td class="queryTDLable">入帳：</td>
		<td class="queryTDInput">
			<select class="field_Q" type="select" name="q_verify">
				<%=util.View.getYNOption(untvpAddProofQuery.getQ_verify())%>
			</select>
		</td>
		<td class="queryTDLable">月結：</td>
		<td class="queryTDInput">
			<select class="field_Q" type="select" name="q_closing">
				<%=util.View.getYNOption(untvpAddProofQuery.getQ_closing())%>
			</select>
		</td>		
	</tr>
	<tr>
		<td class="queryTDLable">財產性質：</td>
		<td class="queryTDInput">
			<select class="field_Q" type="select" name="q_propertyKind">			
				<%=util.View.getOption("select codeID, codeName from SYSCA_Code where codeKindID='PKD' ",untvpAddProofQuery.getQ_propertyKind())%>
			</select>		
		</td>			
		<td class="queryTDLable">基金財產：</td>
		<td class="queryTDInput">
			<select class="field_Q" type="select" name="q_fundType">
				<script>changeEnterOrg_FundType(form1.q_enterOrg.value,'<%=user.getOrganID()%>','q_fundType','<%=untvpAddProofQuery.getQ_fundType()%>','<%=user.getIsAdminManager()%>','<%=user.getIsOrganManager()%>');</script>
			</select>
		</td>		
	</tr>
	<tr>
		<td class="queryTDLable">增加原因：</td>
		<td class="queryTDInput" colspan="3">
			<select class="field_Q" type="select" name="q_cause">
				<%=util.View.getOption("select codeID, codeName from SYSCA_Code where codeKindID='CAB'", untvpAddProofQuery.getQ_cause())%>
			</select>
		</td>	
	</tr>	
	<tr>
		<td class="queryTDLable"><div align="right">填單日期：</div></td>
		<td class="queryTDInput">
			起<%=util.View.getPopCalndar("field_Q","q_writeDateS",untvpAddProofQuery.getQ_writeDateS())%>&nbsp;~&nbsp;
			訖<%=util.View.getPopCalndar("field_Q","q_writeDateE",untvpAddProofQuery.getQ_writeDateE())%>
		</td>
		<td class="queryTDLable">案名：</td>
		<td class="queryTDInput">
			<input class="field_Q" type="text" name="q_caseName" size="20" maxlength="25" value="<%=untvpAddProofQuery.getQ_caseName()%>">
		</td>		
	</tr>
	<tr>
		<td class="queryTDLable">增加單編號：</td>
		<td class="queryTDInput"  colspan="3">
			<input class="field_Q" type="text" name="q_proofDoc" size="15" maxlength="20" value="<%=untvpAddProofQuery.getQ_proofDoc()%>">字
			起<input class="field_Q" type="text" name="q_proofNoS" size="10" maxlength="20" value="<%=untvpAddProofQuery.getQ_proofNoS()%>" onChange="addChar(this ,7);">&nbsp;~&nbsp;
			訖<input class="field_Q" type="text" name="q_proofNoE" size="10" maxlength="20" value="<%=untvpAddProofQuery.getQ_proofNoE()%>" onChange="addChar(this ,7);">號
		</td>
	</tr>	
	<tr>
		<td class="queryTDInput" colspan="4" style="text-align:center;">
			<input class="toolbar_default" followPK="false" type="submit" name="querySubmit" value="確　　定" >
			<input class="toolbar_default" followPK="false" type="button" name="queryCannel" value="取　　消" onClick="whatButtonFireEvent(this.name)">
		</td>
	</tr>
</table>
