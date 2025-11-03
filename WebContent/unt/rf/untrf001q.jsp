<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../home/head.jsp" %>
<%
unt.rf.UNTRF001F_Q untrfQuery = (unt.rf.UNTRF001F_Q)request.getAttribute("UNTRF001Q");
%>
<script type="text/javascript" src="../../js/function.js"></script>
<script language="javascript">
function alteredEnterOrg(){
	changeEnterOrg_FundType(form1.q_enterOrg.value,'<%=user.getOrganID()%>','q_fundType','','<%=user.getIsAdminManager()%>','<%=user.getIsOrganManager()%>');
}
</script>
	<table class="queryTable"  border="1">
	<tr>
		<td class="queryTDInput" colspan="4">
			<input name="querySelect" type="radio" class="field_Q" value="AddProof" <%=untrfQuery.getQueryTab1()%>>
			&nbsp;<font color="green">查詢增加單</font>&nbsp;&nbsp;&nbsp;		
			<input name="querySelect" type="radio" class="field_Q" value="Detail" <%=untrfQuery.getQueryTab2()%>>
			&nbsp;<font color="green">查詢基本資料</font>
			
		</td>
	</tr>				
	  <tr>
        <td class="queryTDLable">入帳機關：</td>
        <td class="queryTDInput">
        <%=util.View.getPopOrgan("field_Q","q_enterOrg",untrfQuery.getQ_enterOrg(),untrfQuery.getQ_enterOrgName(),"N&js=alteredEnterOrg();")%> </td>
        <td class="queryTDLable">權屬：</td>
        <td class="queryTDInput"><select class="field_Q" type="select" name="q_ownership">
            <%=util.View.getOnwerOption(untrfQuery.getQ_ownership())%>
          </select>
        </td>
      </tr>
	  <tr>
	    <td class="queryTDLable">電腦單號：</td>
	    <td class="queryTDInput">
			    起<input class="field_Q" type="text" name="q_caseNoS" size="10" maxlength="10" value="<%=untrfQuery.getQ_caseNoS()%>">&nbsp;~&nbsp;
			    訖<input class="field_Q" type="text" name="q_caseNoE" size="10" maxlength="10" value="<%=untrfQuery.getQ_caseNoE()%>">
        </td>
	    <td class="queryTDLable">入帳日期：</td>
	    <td class="queryTDInput">
			    起<%=util.View.getPopCalndar("field_Q","q_enterDateS",untrfQuery.getQ_enterDateS())%>&nbsp;~&nbsp;
			    訖<%=util.View.getPopCalndar("field_Q","q_enterDateE",untrfQuery.getQ_enterDateE())%> 
	    </td>
	  </tr>
	  <tr>
        <td class="queryTDLable">財產編號：</td>
        <td colspan="3" class="queryTDInput">
			        起<%=util.View.getPopProperty("field_Q","q_propertyNoS",untrfQuery.getQ_propertyNoS(),untrfQuery.getQ_propertyNoSName(),"111")%>&nbsp;~&nbsp;
			        訖<%=util.View.getPopProperty("field_Q","q_propertyNoE",untrfQuery.getQ_propertyNoE(),untrfQuery.getQ_propertyNoEName(),"111")%>
        </td>
      </tr>
	  <tr>
        <td class="queryTDLable">財產分號：</td>
        <td class="queryTDInput">
			起<input class="field_Q" type="text" name="q_serialNoS" size="7" maxlength="7" value="<%=untrfQuery.getQ_serialNoS()%>" onChange="addChar(this ,7);">&nbsp;~&nbsp;
        	訖<input class="field_Q" type="text" name="q_serialNoE" size="7" maxlength="7" value="<%=untrfQuery.getQ_serialNoE()%>" onChange="addChar(this ,7);">
        </td>
        <td class="queryTDLable">資料狀態：</td>
        <td class="queryTDInput"><select class="field_Q" type="select" name="q_dataState">
            <%=util.View.getTextOption("1;現存;2;已減損",untrfQuery.getQ_dataState())%>
          </select>
        </td>
      </tr>
	  <tr>
	    <td class="queryTDLable">入帳：</td>
	    <td class="queryTDInput"><select class="field_Q" type="select" name="q_verify">
            <%=util.View.getYNOption(untrfQuery.getQ_verify())%>
          </select>
        </td>
	    <td class="queryTDLable">月結：</td>
	    <td class="queryTDInput"><select class="field_Q" type="select" name="q_closing">
            <%=util.View.getYNOption(untrfQuery.getQ_closing())%>
          </select>
        </td>
	  </tr>	
	<tr>
	  <td class="queryTDLable">財產性質：</td>
	  <td class="queryTDInput"><select class="field_Q" type="select" name="q_propertyKind">
          <%=util.View.getOption("select codeID, codeName from SYSCA_Code where codeKindID='PKD' ",untrfQuery.getQ_propertyKind())%>
        </select>
      </td>
	  <td class="queryTDLable">基金財產：</td>
	  <td class="queryTDInput">
	  	<select class="field_Q" type="select" name="q_fundType">
         <script>changeEnterOrg_FundType(form1.q_enterOrg.value,'<%=user.getOrganID()%>','q_fundType',form1.q_fundType.value,'<%=user.getIsAdminManager()%>','<%=user.getIsOrganManager()%>');</script>
        </select>
      </td>
	</tr>
	<tr>
	  <td class="queryTDLable">珍貴財產：</td>
	  <td class="queryTDInput"><select class="field_Q" type="select" name="q_valuable">
          <%=util.View.getYNOption(untrfQuery.getQ_valuable())%>
        </select>
      </td>
		<td class="queryTDLable">抵繳遺產稅：</td>
		<td class="queryTDInput"><select class="field_Q" type="select" name="q_taxCredit">
            <%=util.View.getYNOption(untrfQuery.getQ_taxCredit())%>
          </select>
        </td>
	</tr>		
	<tr>
	  <td class="queryTDLable">增加原因：</td>
	  <td class="queryTDInput"><select class="field_Q" type="select" name="q_cause">
          <%=util.View.getOption("select  codeID, codeName from SYSCA_Code where codeKindID='CAB'", untrfQuery.getQ_cause())%>
      </select></td>
		<td class="queryTDLable">&nbsp;</td>
		<td class="queryTDInput">&nbsp;</td>				
	</tr>		
	<tr>
	  	<td class="queryTDLable">填單日期：</td>
	  	<td class="queryTDInput"> 
			  起<%=util.View.getPopCalndar("field_Q","q_writeDateS",untrfQuery.getQ_writeDateS())%>&nbsp;~&nbsp;
			  訖<%=util.View.getPopCalndar("field_Q","q_writeDateE",untrfQuery.getQ_writeDateE())%> 
		</td>
		<td class="queryTDLable">案名：</td>
		<td class="queryTDInput">
			<input class="field_Q" type="text" name="q_caseName" size="20" maxlength="25" value="<%=untrfQuery.getQ_caseName()%>">
		</td>		
	</tr>	
	<tr>
		<td class="queryTDLable">增加單編號：</td>
		<td class="queryTDInput"  colspan="3">		
			<input class="field_Q" type="text" name="q_proofDoc" size="15" maxlength="20" value="<%=untrfQuery.getQ_proofDoc()%>">字
			起<%=util.View.getPopCalndar("field_Q","q_proofNoS",untrfQuery.getQ_proofNoS())%>&nbsp;~&nbsp;
			訖<%=util.View.getPopCalndar("field_Q","q_proofNoE",untrfQuery.getQ_proofNoE())%>號
		</td>
	</tr>	
	<tr>
		<td class="queryTDInput" colspan="4" style="text-align:center;">
			<input class="toolbar_default" followPK="false" type="submit" name="querySubmit" value="確　　定">
			<input class="toolbar_default" followPK="false" type="button" name="queryCannel" value="取　　消" onClick="whatButtonFireEvent(this.name)">
		</td>
	</tr>
	</table>
