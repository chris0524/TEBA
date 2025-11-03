<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../home/head.jsp" %>
<%
unt.la.UNTLA027Q untlaMergeDivisionQuery = (unt.la.UNTLA027Q)request.getAttribute("UNTLA027Q");
%>
<!--Query區============================================================-->
	<table class="queryTable"  border="1">
	<input type="hidden" name="q_checkQuery" value="<%=untlaMergeDivisionQuery.getQ_checkQuery()%>">
<!-- 	<tr>
		<td class="queryTDLable">入帳機關：</td>
		<td class="queryTDInput">
			<%=util.View.getPopOrgan("field_Q","q_enterOrg",untlaMergeDivisionQuery.getQ_enterOrg(),untlaMergeDivisionQuery.getQ_enterOrgName())%>
		</td>
	</tr>
	<tr>
		<td class="queryTDLable">權屬：</td>
		<td class="queryTDInput">
			<select class="field_Q" type="select" name="q_ownership">
				<%=util.View.getOnwerOption(untlaMergeDivisionQuery.getQ_ownership())%>
			</select>
		</td>
	</tr>
 -->	
	<tr>
		<td class="queryTDLable">電腦單號：</td>
		<td class="queryTDInput">
			起<input class="field_Q" type="text" name="q_caseNoS" size="10" maxlength="10" value="<%=untlaMergeDivisionQuery.getQ_caseNoS()%>">&nbsp;~&nbsp;
			訖<input class="field_Q" type="text" name="q_caseNoE" size="10" maxlength="10" value="<%=untlaMergeDivisionQuery.getQ_caseNoE()%>">			
		</td>
	</tr>

	<tr>
		<td class="queryTDLable">原因：</td>
		<td class="queryTDInput">
			<select class="field_Q" type="select" name="q_cause">
			<%=util.View.getOption("select codeID, codeName from SYSCA_Code where codeKindID='CAA'  and codeCon1='4' ", untlaMergeDivisionQuery.getQ_cause())%>
			</select>
		</td>
	</tr>
<!-- <tr>
		<td class="queryTDLable">合併分割日期：</td>
		<td class="queryTDInput">
			起<%=util.View.getPopCalndar("field_Q","q_enterDateS",untlaMergeDivisionQuery.getQ_enterDateS())%>&nbsp;~&nbsp;
			訖<%=util.View.getPopCalndar("field_Q","q_enterDateE",untlaMergeDivisionQuery.getQ_enterDateE())%>
		</td>
	</tr>
	<tr>
		<td class="queryTDLable">核准機關：</td>
		<td class="queryTDInput">
			<select class="field_Q" type="select" name="q_approveOrg">
			<%=util.View.getOption("select codeID, codeName from SYSCA_Code where codeKindID='APO' ", untlaMergeDivisionQuery.getQ_approveOrg())%>
			</select>
		</td>
	</tr>
	<tr>
		<td class="queryTDLable">入帳：</td>
		<td class="queryTDInput">
			<select class="field_Q" type="select" name="q_verify">
			<%=util.View.getYNOption(untlaMergeDivisionQuery.getQ_verify())%>
			</select>
		</td>
	</tr>
 -->		
<input type="hidden" name="q_enterOrg" value="<%=untlaMergeDivisionQuery.getQ_enterOrg()%>">
<input type="hidden" name="q_enterOrgName" value="<%=untlaMergeDivisionQuery.getQ_enterOrgName()%>">
<input type="hidden" name="q_ownership" value="<%=untlaMergeDivisionQuery.getQ_ownership()%>">	
	<tr>
		<td class="queryTDInput" colspan="4" style="text-align:center;">
			<input class="toolbar_default" followPK="false" type="submit" name="querySubmit" value="確　　定" >
			<input class="toolbar_default" followPK="false" type="button" name="queryCannel" value="取　　消" onClick="whatButtonFireEvent(this.name)">
		</td>
	</tr>
	</table>
