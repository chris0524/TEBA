<!--
*<br>程式目的：BeanLocker解鎖作業
*<br>程式代號：sysap999f
*<br>撰寫日期：104.10.08
*<br>程式作者：CHLin
*<br>--------------------------------------------------------
*<br>修改作者　　修改日期　　　修改目的
*<br>--------------------------------------------------------
-->
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../home/head.jsp" %>
<% 
	
	if ("Y".equals(user.getIsAdminManager())) {
		String msg = "";	
		String btnSubmit = Common.get(request.getParameter("btnSubmit"));
		if (!"".equals(btnSubmit)) {
			String[] unlockUserList = request.getParameterValues("lockedBean");
			if (unlockUserList!=null && unlockUserList.length>0) {
				for (int i=0; i<unlockUserList.length; i++) {
					BeanLocker.forceUnlock(unlockUserList[i]);
				}
				msg = "alert('已成功移除被鎖定之Bean！');";
			}
		}
		
%>
	<html>
		<head>
			<script type="text/javascript">
				function init() {
					<%=msg%>
				}
			</script>
		</head>
		<body onload="init();">
			<form id="form1" name="form1" method="post">
			<table align="center" cellpadding="0" cellspacing="0">
			<!--Form區============================================================-->
			<tr><td class="bg">
			
			    <table class="table_form" width="100%" height="100%">
			    <tr>
			    <td class="td_lable" width="200">請選擇要解除鎖定之BeanLocker</td>
			    </tr>
			    <tr>
			        <td align="center" class="td_form_white" >
						<select  style="width:1000" class="field_Q" name="lockedBean" size="10" multiple="true" id="lockedBean">
							<%=BeanLocker.getLockedOptionsHtml()%>				
			            </select>
			        </td>
			    </tr>
			    <tr>
			        <td class="queryTDInput" colspan="3" style="text-align:center;">
			        	<input class="toolbar_default" followPK="false" type="submit" name="btnSubmit" value="確　　定" >
			        </td>
			    </tr>
			    </table>
			
			</td></tr>
			</table>
			</form>
		</body>	
	</html>
<%
	} else {
%>
		您必須是系統管理員才可使用這作業
<%
	}
%>