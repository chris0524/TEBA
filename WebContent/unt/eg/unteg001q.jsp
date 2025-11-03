<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../home/head.jsp" %>
<%
unt.eg.UNTEG001Q obj = (unt.eg.UNTEG001Q)request.getAttribute("UNTEG001Q");
%>
<script type="text/javascript">
function checkDeprUnitCB(){
	if($("input[name='cb_q_deprUnitCB']").attr('checked') == 'checked'){
		$("input[name='q_deprUnitCB']").val('Y');
	}else{
		$("input[name='q_deprUnitCB']").val('N');
	}
}
</script>

<!--Query區============================================================-->
<table class="queryTable"  border="1">

	<tr>
		<td class="queryTDLable"  width="30%">工程編號：</td>
		<td class="queryTDInput">
			<input class="field_Q" type="text" name="q_engineeringNo" size="20" value="<%=obj.getQ_engineeringNo()%>">
		</td>
	</tr>
	<tr>
		<td class="queryTDLable"  width="30%">工程名稱：</td>
		<td class="queryTDInput">
			<input class="field_Q" type="text" name="q_engineeringName" size="50" value="<%=obj.getQ_engineeringName()%>">
		</td>
	</tr>
	<tr>
		<td class="queryTDInput" colspan="2" style="text-align:center;">
			<input class="toolbar_default" followPK="false" type="submit" name="querySubmit" value="確　　定" >
			<input class="toolbar_default" followPK="false" type="button" name="queryCannel" value="取　　消" onClick="whatButtonFireEvent(this.name)">
		</td>
	</tr>
</table>
