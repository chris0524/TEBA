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
unt.vp.UNTVP006Q untvpAdjustQuery = (unt.vp.UNTVP006Q)request.getAttribute("UNTVP006Q");
%>
<!--Query區============================================================-->
<table class="queryTable"  border="1">
<input type="hidden" name="q_checkQuery" value="<%=untvpAdjustQuery.getQ_checkQuery()%>">
	<tr>
		<td class="queryTDLable" >入帳機關：</td>
		<td class="queryTDInput" >
			<%=util.View.getPopOrgan("field_Q","q_enterOrg",untvpAdjustQuery.getQ_enterOrg(),untvpAdjustQuery.getQ_enterOrgName(),"N&js=alteredEnterOrg();")%>
		</td>
		<td class="queryTDLable">權屬：</td>
		<td class="queryTDInput">
			<select class="field_Q" type="select" name="q_ownership">
			<%=util.View.getOnwerOption(untvpAdjustQuery.getQ_ownership())%>
			</select>
		</td>		
	</tr>
	<tr>
		<td class="queryTDLable">電腦單號：</td>
		<td class="queryTDInput">
			  起<input class="field_Q" type="text" name="q_caseNoS" size="12" maxlength="10" value="<%=untvpAdjustQuery.getQ_caseNoS()%>">&nbsp;~&nbsp;
			 訖<input class="field_Q" type="text" name="q_caseNoE" size="12" maxlength="10" value="<%=untvpAdjustQuery.getQ_caseNoE()%>">
		</td>
		<td class="queryTDLable">入帳日期：</td>
		<td class="queryTDInput">
			起<%=util.View.getPopCalndar("field_Q","q_adjustDateS",untvpAdjustQuery.getQ_adjustDateS())%>&nbsp;~&nbsp;
			訖<%=util.View.getPopCalndar("field_Q","q_adjustDateE",untvpAdjustQuery.getQ_adjustDateE())%>
		</td>	
	</tr>
	<tr>
		<td class="queryTDLable">財產編號：</td>
		<td colspan="3" class="queryTDInput">
			起<%=util.View.getPopProperty("field_Q","q_propertyNoS",untvpAdjustQuery.getQ_propertyNoS(),untvpAdjustQuery.getQ_propertyNoSName(),"9")%>
			訖<%=util.View.getPopProperty("field_Q","q_propertyNoE",untvpAdjustQuery.getQ_propertyNoE(),untvpAdjustQuery.getQ_propertyNoEName(),"9")%>
		</td>
	</tr>
	<tr>
		<td class="queryTDLable">財產分號：</td>
		<td class="queryTDInput">
			起<input class="field_Q" type="text" name="q_serialNoS" size="7" maxlength="7" value="<%=untvpAdjustQuery.getQ_serialNoS()%>" onChange="addChar(this ,7);">&nbsp;~&nbsp;
			訖<input class="field_Q" type="text" name="q_serialNoE" size="7" maxlength="7" value="<%=untvpAdjustQuery.getQ_serialNoE()%>" onChange="addChar(this ,7);">
		</td>
		<td class="queryTDLable">入帳：</td>
		<td class="queryTDInput">
			<select class="field_Q" type="select" name="q_verify">
			<%=util.View.getYNOption(untvpAdjustQuery.getQ_verify())%>
			</select>
		</td>
	</tr>
	<tr>
		<td class="queryTDLable">財產性質：</td>
		<td class="queryTDInput">
			<select class="field_Q" type="select" name="q_propertyKind">			
				<%=util.View.getOption("select codeID, codeName from SYSCA_Code where codeKindID='PKD' ",untvpAdjustQuery.getQ_propertyKind())%>
			</select>		
		</td>			
		<td class="queryTDLable">基金財產：</td>
		<td class="queryTDInput">
			<select class="field_Q" type="select" name="q_fundType">
				<script>changeEnterOrg_FundType(form1.q_enterOrg.value,'<%=user.getOrganID()%>','q_fundType','<%=untvpAdjustQuery.getQ_fundType()%>','<%=user.getIsAdminManager()%>','<%=user.getIsOrganManager()%>');</script>
			</select>
		</td>		
	</tr>
	<tr>
		<td class="queryTDLable"><div align="right">填單日期：</div></td>
		<td class="queryTDInput">
			起<%=util.View.getPopCalndar("field_Q","q_writeDateS",untvpAdjustQuery.getQ_writeDateS())%>&nbsp;~&nbsp;
			訖<%=util.View.getPopCalndar("field_Q","q_writeDateE",untvpAdjustQuery.getQ_writeDateE())%>
		</td>
		<td class="queryTDLable">增減值案名：</td>
		<td class="queryTDInput">
			<input class="field_Q" type="text" name="q_caseName" size="25" maxlength="25" value="<%=untvpAdjustQuery.getQ_caseName()%>">
		</td>
	</tr>
	<tr>
		<td class="queryTDLable">發行法人名稱：</td>
		<td class="queryTDInput" colspan="3">
			<input class="field_Q" type="text" name="q_securityName" size="35" maxlength="15" value="<%=untvpAdjustQuery.getQ_securityName()%>">
		</td>
	</tr>
	<tr>
		<td class="queryTDLable">增減值單編號：</td>
		<td class="queryTDInput" colspan="3">
			<input class="field_Q" type="text" name="q_proofDoc" size="15" maxlength="20" value="<%=untvpAdjustQuery.getQ_proofDoc()%>">字
			起<input class="field_Q" type="text" name="q_proofNoS" size="10" maxlength="20" value="<%=untvpAdjustQuery.getQ_proofNoS()%>" onChange="addChar(this ,7);">&nbsp;~&nbsp;
			訖<input class="field_Q" type="text" name="q_proofNoE" size="10" maxlength="20" value="<%=untvpAdjustQuery.getQ_proofNoE()%>" onChange="addChar(this ,7);">號
		</td>
	</tr>	
	<tr>
		<td class="queryTDInput" colspan="4" style="text-align:center;">
			<input class="toolbar_default" followPK="false" type="submit" name="querySubmit" value="確　　定" >
			<input class="toolbar_default" followPK="false" type="button" name="queryCannel" value="取　　消" onClick="whatButtonFireEvent(this.name)">
		</td>
	</tr>
</table>
