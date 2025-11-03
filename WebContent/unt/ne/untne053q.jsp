<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../home/head.jsp" %>
<%
unt.ne.UNTNE053F UNTNEF = (unt.ne.UNTNE053F)request.getAttribute("UNTNE053F");
%>
<script language='javascript'>
function checknewEnterOrgReceiveCB(){
	if($('#q_newEnterOrgReceiveCB').attr("checked")=='checked'){
		$('#q_newEnterOrgReceive').val("Y");
	}else{
		$('#q_newEnterOrgReceive').val("");
	}
}


$(function(){
	if($('#q_newEnterOrgReceive').val()=='Y'){
		$('#q_newEnterOrgReceiveCB').attr("checked",true)
	}
});

</script>

<!--Query區============================================================-->
	<table class="queryTable"  border="1">
	<tr>
		<td class="queryTDLable">撥出機關：</td>
		<td class="queryTDInput">
			<%=util.View.getPopOrgan("field_Q","q_oldEnterOrg",UNTNEF.getQ_oldEnterOrg(),UNTNEF.getQ_oldEnterOrgName())%>
		</td>
	</tr>
	<tr>
		<td class="queryTDLable">物品區分別：</td>
		<td class="queryTDInput">
			<select class="field_Q" type="select" name="">
			　　<%=util.View.getTextOption("110;公務用-公務類;120;公務用-作業基金;","")%>
			</select>	
		</td>
	</tr>	
	<tr>
		<td class="queryTDLable">撥出機關電腦單號：</td>
		<td class="queryTDInput">
			<input class="field_Q" type="text" name="q_caseNo" size="10" maxlength="10" value="<%=UNTNEF.getQ_caseNo()%>" >			
		</td>
	</tr>		
	<tr>
		<td class="queryTDLable">包含已接收物品資料：</td>
		<td class="queryTDInput">
			<input class="field_Q" type="checkbox" id="q_newEnterOrgReceiveCB" name="q_newEnterOrgReceiveCB" onclick="checknewEnterOrgReceiveCB()">
			<input class="field_Q" type="hidden" id="q_newEnterOrgReceive" name="q_newEnterOrgReceive" value="<%=UNTNEF.getQ_newEnterOrgReceive() %>">			
		</td>
	</tr>
	
	<tr>
		<td class="queryTDInput" colspan="4" style="text-align:center;">
			<input class="toolbar_default" followPK="false" type="submit" name="querySubmit" value="確　　定" >
			<input class="toolbar_default" followPK="false" type="button" name="queryCannel" value="取　　消" onClick="whatButtonFireEvent(this.name)">
		</td>
	</tr>
	</table>
