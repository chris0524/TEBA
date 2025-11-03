<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../home/head.jsp" %>
<%
sys.ca.SYSCA003Q qBean = (sys.ca.SYSCA003Q)request.getAttribute("qBean");
%>
	<table class="queryTable"  border="1">	
	
	<tr name="tr_enterOrg">
		<td class="queryTDLable">所屬機關：</td>
		<td class="queryTDInput">
			<%=util.View.getPopOrgan("field_Q","q_enterOrg",qBean.getQ_enterOrg(),qBean.getQ_enterOrgName(),"")%>
		</td>	
	</tr>
	<tr>
		<td class="queryTDLable">單位代碼：</td>
		<td class="queryTDInput">
			<input class="field_Q" type="text" name="q_unitNo" size="10" maxlength="10" value="<%=qBean.getQ_unitNo()%>">
		</td>
	</tr>
	<tr>
		<td class="queryTDLable">單位名稱：</td>
		<td class="queryTDInput">
			<input class="field_Q" type="text" name="q_unitName" size="30" maxlength="60" value="<%=qBean.getQ_unitName()%>">
		</td>
	</tr>
	<tr>
		<td class="queryTDLable">預設部門別：</td>
		<td class="queryTDInput">
			<select class="field_Q" type="select" name="q_deprUnit">
			　　<%=util.View.getOption("select deprUnitNo, deprUnitName from SYSCA_DeprUnit where enterorg = '" + user.getOrganID() + "'", qBean.getQ_deprUnit())%>
			</select>
		</td>
	</tr>
	<tr>
		<td class="queryTDLable">備註：</td>
		<td class="queryTDInput">
			<input class="field_Q" name="q_notes" type="text" size="20" value="<%=qBean.getQ_notes()%>">
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
