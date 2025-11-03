<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../home/head.jsp" %>
<%
sys.ca.SYSCA004F qBean = (sys.ca.SYSCA004F)request.getAttribute("qBean");
%>
	<table class="queryTable"  border="1">
	<tr name='tr_enterOrg'>
		<td class="queryTDLable">所屬機關：</td>
		<td class="queryTDInput">
			<%=util.View.getPopOrgan("field_Q","q_enterOrg",qBean.getQ_enterOrg(),qBean.getQ_enterOrgName(),"")%>
		</td>	
	</tr>
	<tr>
		<td class="queryTDLable" width="40%">保管使用人代碼：</td>
		<td class="queryTDInput">
			<input class="field_Q" type="text" name="q_keeperNo" size="10" maxlength="10" value="<%=qBean.getQ_keeperNo()%>">
		</td>
	</tr>
	<tr>
		<td class="queryTDLable">保管使用人姓名：</td>
		<td class="queryTDInput">
			<input class="field_Q" type="text" name="q_keeperName" size="20" maxlength="20" value="<%=qBean.getQ_keeperName()%>">
		</td>
	</tr>
	<tr>
		<td class="queryTDLable">是否現任：</td>
		<td class="queryTDInput">
			<select class="field_Q" type="select" name="q_incumbencyYN">
			　　<%=util.View.getYNOption(qBean.getQ_incumbencyYN())%>
			</select>
		</td>
	</tr>
	<tr>
		<td class="queryTDInput" colspan="2" style="text-align:center;">
			<input type="hidden" name="organID" value="<%=user.getOrganID()%>">	
			<input type="hidden" name="isOrganManager" value="<%=user.getIsOrganManager()%>">
			<input type="hidden" name="isAdminManager" value="<%=user.getIsAdminManager()%>">			
			<input class="toolbar_default" followPK="false" type="submit" name="querySubmit" value="確　　定" >
			<input class="toolbar_default" followPK="false" type="button" name="queryCannel" value="取　　消" onClick="whatButtonFireEvent(this.name)">
		</td>
	</tr>
	</table>
