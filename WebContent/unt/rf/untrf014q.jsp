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
unt.rf.UNTRF014Q untrfAdjustQuery = (unt.rf.UNTRF014Q)request.getAttribute("UNTRF014Q");
%>
<!--Query區============================================================-->
	<table class="queryTable"  border="1">
	<tr>
		<td class="queryTDInput" colspan="4">
			<input name="querySelect" type="radio" class="field_Q" value="AddProof" <%=untrfAdjustQuery.getQueryTab1()%>>
			&nbsp;<font color="green">查詢增減值單資料</font>&nbsp;&nbsp;&nbsp;		
			<input name="querySelect" type="radio" class="field_Q" value="detail" <%=untrfAdjustQuery.getQueryTab2()%>>
			&nbsp;<font color="green">查詢增減值單明細資料</font>
		</td>
	</tr>	
	<tr>
		<td class="queryTDLable">入帳機關：</td>
		<td class="queryTDInput">
			<%=util.View.getPopOrgan("field_Q","q_enterOrg",untrfAdjustQuery.getQ_enterOrg(),untrfAdjustQuery.getQ_enterOrgName(),"N&js=alteredEnterOrg();")%>
		</td>
		<td class="queryTDLable">權屬：</td>
		<td class="queryTDInput">
			<select class="field_Q" type="select" name="q_ownership">
			<%=util.View.getOnwerOption(untrfAdjustQuery.getQ_ownership())%>
			</select>
		</td>
	</tr>
	<tr>
		<td class="queryTDLable">電腦單號：</td>
		<td class="queryTDInput">
			起<input class="field_Q" type="text" name="q_caseNoS" size="10" maxlength="10" value="<%=untrfAdjustQuery.getQ_caseNoS()%>">&nbsp;~&nbsp;
			訖<input class="field_Q" type="text" name="q_caseNoE" size="10" maxlength="10" value="<%=untrfAdjustQuery.getQ_caseNoE()%>">			
		</td>
		<td class="queryTDLable">增減值日期：</td>
		<td class="queryTDInput">
			起<%=util.View.getPopCalndar("field_Q","q_adjustDateS",untrfAdjustQuery.getQ_adjustDateS())%>&nbsp;~&nbsp;
			訖<%=util.View.getPopCalndar("field_Q","q_adjustDateE",untrfAdjustQuery.getQ_adjustDateE())%>
		</td>
	</tr>
	<tr>
		<td class="queryTDLable">財產編號：</td>		
		<td class="queryTDInput" colspan="3">		
 			起<%=util.View.getPopProperty("field_Q","q_propertyNoS",untrfAdjustQuery.getQ_propertyNoS(),untrfAdjustQuery.getQ_propertyNoSName(),"111")%>
			訖<%=util.View.getPopProperty("field_Q","q_propertyNoE",untrfAdjustQuery.getQ_propertyNoE(),untrfAdjustQuery.getQ_propertyNoEName(),"111")%>
		</td>
	</tr>
	<tr>
		<td class="queryTDLable" >財產分號：</td>
		<td class="queryTDInput" >
			起<input class="field_Q" type="text" name="q_serialNoS" size="7" maxlength="7" value="<%=untrfAdjustQuery.getQ_serialNoS()%>" onChange="addChar(this ,7);">&nbsp;~&nbsp;
			訖<input class="field_Q" type="text" name="q_serialNoE" size="7" maxlength="7" value="<%=untrfAdjustQuery.getQ_serialNoE()%>" onChange="addChar(this ,7);">
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
		<td class="queryTDInput">
			<select class="field_Q" type="select" name="q_verify">
				<%=util.View.getYNOption(untrfAdjustQuery.getQ_verify())%>
			</select>					
		</td>
		<td class="queryTDLable">增減值原因：</td>
		<td class="queryTDInput">
			<select class="field_Q" type="select" name="q_cause">
				<%=util.View.getTextOption("1;資產重估調整;2;整建;3;其它",untrfAdjustQuery.getQ_cause())%>
			</select>			
		</td>			
	</tr>
	<tr>
		<td class="queryTDLable">財產性質：</td>
		<td class="queryTDInput">
			<select class="field_Q" type="select" name="q_propertyKind">
				<%=util.View.getOption("select codeID, codeName from SYSCA_Code where codeKindID='PKD' ",untrfAdjustQuery.getQ_propertyKind())%>
			</select>		
		</td>	
		<td class="queryTDLable">基金財產：</td>
		<td class="queryTDInput">
			<select class="field_Q" type="select" name="q_fundType">
				<script>changeEnterOrg_FundType(form1.q_enterOrg.value,'<%=user.getOrganID()%>','q_fundType','<%=untrfAdjustQuery.getQ_fundType()%>','<%=user.getIsAdminManager()%>','<%=user.getIsOrganManager()%>');</script>
			</select>
		</td>				
	</tr>
	<tr>
		<td class="queryTDLable">珍貴財產：</td>
		<td class="queryTDInput">
			<select class="field_Q" type="select" name="q_valuable">
				<%=util.View.getYNOption(untrfAdjustQuery.getQ_valuable())%>
			</select>		
		</td>		
		<td class="queryTDLable">抵繳遺產稅：</td>
		<td class="queryTDInput">
			<select class="field_Q" type="select" name="q_taxCredit">
				<%=util.View.getYNOption(untrfAdjustQuery.getQ_taxCredit())%>
			</select>		
		</td>					
	</tr>
	<tr>
		<td class="queryTDLable">填單日期：</td>
		<td class="queryTDInput">
			起<%=util.View.getPopCalndar("field_Q","q_writeDateS",untrfAdjustQuery.getQ_writeDateS())%>&nbsp;~&nbsp;
			訖<%=util.View.getPopCalndar("field_Q","q_writeDateE",untrfAdjustQuery.getQ_writeDateE())%>
		</td>
		<td class="queryTDLable">案名：</td>
		<td class="queryTDInput">
			<input class="field_Q" type="text" name="q_caseName" size="25" maxlength="25" value="<%=untrfAdjustQuery.getQ_caseName()%>">
		</td>
	</tr>
	<tr>
		<td class="queryTDLable">增減值單編號：</td>
		<td class="queryTDInput" colspan="3">
			<input class="field_Q" type="text" name="q_proofDoc" size="10" maxlength="20" value="<%=untrfAdjustQuery.getQ_proofDoc()%>"> 字
			起<input class="field_Q" type="text" name="q_proofNoS" size="10" maxlength="20" value="<%=untrfAdjustQuery.getQ_proofNoS()%>" onChange="addChar(this ,7);">&nbsp;~&nbsp;
			訖<input class="field_Q" type="text" name="q_proofNoE" size="10" maxlength="20" value="<%=untrfAdjustQuery.getQ_proofNoE()%>" onChange="addChar(this ,7);"> 號
		</td>
	</tr>
	<tr>
		<td class="queryTDInput" colspan="4" style="text-align:center;">
			<input class="toolbar_default" followPK="false" type="submit" name="querySubmit" value="確　　定" >
			<input class="toolbar_default" followPK="false" type="button" name="queryCannel" value="取　　消" onClick="whatButtonFireEvent(this.name)">
		</td>
	</tr>
	</table>
