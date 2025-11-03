<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../home/head.jsp" %>
<%
unt.la.UNTLA054Q UNTLAQ = (unt.la.UNTLA054Q)request.getAttribute("UNTLA054Q");
%>
<script type="text/javascript">

//pop機關輔助視窗,並回傳機關代碼及名稱
function popOrgan(popOrganID,popOrganName,isLimit){
	var prop="";
	var windowTop=(document.body.clientHeight-400)/2+180;
	var windowLeft=(document.body.clientWidth-400)/2+250;
	prop=prop+"width=550,height=420,";
	prop=prop+"top="+windowTop+",";
	prop=prop+"left="+windowLeft+",";
	prop=prop+"scrollbars=no";
	returnWindow=window.open("untla054f_popOrgan.jsp?popOrganID="+popOrganID+"&popOrganName="+popOrganName+"&isLimit="+isLimit,"",prop);
}

</script>

<!--Query區============================================================-->
	<table class="queryTable"  border="1">
	<tr>
		<td class="queryTDLable">入帳機關：</td>
		<td class="queryTDInput">
			<%=util.View.getPopOrgan("field_Q","q_enterOrg",UNTLAQ.getQ_enterOrg(),UNTLAQ.getQ_enterOrgName())%>
		</td>
	</tr>
	<tr>
		<td class="queryTDLable">權屬：</td>
		<td class="queryTDInput">
			<select class="field_Q" type="select" name="q_ownership">
				<%=util.View.getOnwerOption(UNTLAQ.getQ_ownership())%>
			</select>
		</td>
	</tr>
	<tr>	
		<td class="queryTDLable">財產區分別：</td>
		<td class="queryTDInput">
			<%=util.View._getSelectHTML("field_Q", "q_differenceKind", UNTLAQ.getQ_differenceKind(),"DFK") %>
		</td>
	</tr>
 	<tr style="display:none;">
		<td class="queryTDLable"style="display:none;">電腦單號：</td>
		<td class="queryTDInput"style="display:none;">
			起&nbsp;<input class="field_Q" type="text" name="q_caseNoS" size="10" maxlength="10" value="<%=UNTLAQ.getQ_caseNoS()%>" onchange="getFrontZero(this.name, 10);">&nbsp;~&nbsp;
			訖&nbsp;<input class="field_Q" type="text" name="q_caseNoE" size="10" maxlength="10" value="<%=UNTLAQ.getQ_caseNoE()%>" onchange="getFrontZero(this.name, 10);">			
		</td>
	</tr>
	<tr style="display:none;"> 
		<td class="queryTDLable"style="display:none;">案名：</td>
		<td class="queryTDInput"style="display:none;">
			<input class="field_Q" type="text" name="q_caseName" size="20" maxlength="50" value="<%=UNTLAQ.getQ_caseName()%>">			
		</td>
	</tr>

	<tr>
		<td class="queryTDLable">原因：</td>
		<td class="queryTDInput">
			<select class="field_Q" type="select" name="q_cause">
				<%=util.View.getOption("select codeID, codeName from SYSCA_Code where codeKindID='CAA'  and codeid between '401' and '405' ", UNTLAQ.getQ_cause())%>
			</select>
		</td>
	</tr>
	<tr>
		<td class="queryTDLable">入帳：</td>
		<td class="queryTDInput">
			<select class="field_Q" type="select" name="q_verify">
				<%=util.View.getYNOption(UNTLAQ.getQ_verify())%>
			</select>
		</td>
	</tr>
	<tr>
		<td class="queryTDLable">入帳日期：</td>
		<td class="queryTDInput">
			起<%=util.View.getPopCalndar("field_Q","q_enterDateS",UNTLAQ.getQ_enterDateS())%>&nbsp;~&nbsp;
			訖<%=util.View.getPopCalndar("field_Q","q_enterDateE",UNTLAQ.getQ_enterDateE())%>
		</td>
	</tr>
 	
	<tr>
		<td class="queryTDInput" colspan="4" style="text-align:center;">
			<input class="toolbar_default" followPK="false" type="submit" name="querySubmit" value="確　　定" >
			<input class="toolbar_default" followPK="false" type="button" name="queryCannel" value="取　　消" onClick="whatButtonFireEvent(this.name)">
		</td>
	</tr>
	</table>
