<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../home/head.jsp" %>
<%@page import="util.Common"%>
<script type="text/javascript" src="../../js/function.js"></script>
<%
unt.mp.UNTMP001Q untmpAddProofQuery = (unt.mp.UNTMP001Q)request.getAttribute("UNTMP001Q");
%>

<script language="javascript">

function changeAll(){
	changeEnterOrg_FundType(form1.q_enterOrg.value,'<%=user.getOrganID()%>','q_fundType','','<%=user.getIsAdminManager()%>','<%=user.getIsOrganManager()%>');
	changeBureau(form1.q_enterOrg, form1.q_keepBureau, '');
	changeBureau(form1.q_enterOrg, form1.q_useBureau, '');
	changeKeepUnit(form1.q_enterOrg, form1.q_keepBureau, form1.q_keepUnit,'');
	changeKeepUnit(form1.q_enterOrg, form1.q_useBureau, form1.q_useUnit,'');
	getKeeper(form1.q_enterOrg, form1.q_keepUnit, form1.q_keeper, '','N');
	getKeeper(form1.q_enterOrg, form1.q_useUnit, form1.q_userNo, '','N');
}

</script>

<!--Query區============================================================-->
<table class="queryTable"  border="1">
	<tr>
	  <td class="queryTDInput" colspan="4">
	  <input name="querySelect" type="radio" class="field_Q" value="addProof" <%=untmpAddProofQuery.getQueryType1()%> >
      &nbsp;<font color="green">查詢增加單</font>&nbsp;&nbsp;&nbsp;	
	  <input name="querySelect" type="radio" class="field_Q" value="movable" <%=untmpAddProofQuery.getQueryType2()%> >
	  &nbsp;<font color="green">查詢基本資料</font>&nbsp;&nbsp;&nbsp;	
	  <input name="querySelect" type="radio" class="field_Q" value="movableDetail" <%=untmpAddProofQuery.getQueryType3()%> >
      &nbsp;<font color="green">查詢動產明細資料</font>
	  </td>
	  </tr>
	<tr>
 		<td class="queryTDLable" >入帳機關：</td>
        <td class="queryTDInput">
	  		<%=util.View.getPopOrgan("field_Q","q_enterOrg",untmpAddProofQuery.getQ_enterOrg(),untmpAddProofQuery.getQ_enterOrgName(),"N&js=changeAll();")%>
	    </td>
        <td class="queryTDLable"><div align="right">權屬：</div>
        <td class="queryTDInput">
      	<select class="field_Q" type="select" name="q_ownership">
            <%=util.View.getOnwerOption(untmpAddProofQuery.getQ_ownership())%>
        </select>
      </td>
	</tr>
	<tr>
		<td class="queryTDLable">電腦單號：</td>
		<td class="queryTDInput">
			  起<input class="field_Q" type="text" name="q_caseNoS" size="12" maxlength="10" value="<%=untmpAddProofQuery.getQ_caseNoS()%>">&nbsp;~&nbsp;
			  訖<input class="field_Q" type="text" name="q_caseNoE" size="12" maxlength="10" value="<%=untmpAddProofQuery.getQ_caseNoE()%>">
		</td>
		<td class="queryTDLable">入帳日期：</td>
		<td class="queryTDInput">
			起<%=util.View.getPopCalndar("field_Q","q_enterDateS",untmpAddProofQuery.getQ_enterDateS())%>&nbsp;~&nbsp;
			訖<%=util.View.getPopCalndar("field_Q","q_enterDateE",untmpAddProofQuery.getQ_enterDateE())%>
		</td>
    </tr>
	<tr>
		<td class="queryTDLable">財產編號：</td>
		<td colspan="3" class="queryTDInput">
			起<%=util.View.getPopProperty("field_Q","q_propertyNoS",untmpAddProofQuery.getQ_propertyNoS(),untmpAddProofQuery.getQ_propertyNoSName(),"3,4,5")%>
			訖<%=util.View.getPopProperty("field_Q","q_propertyNoE",untmpAddProofQuery.getQ_propertyNoE(),untmpAddProofQuery.getQ_propertyNoEName(),"3,4,5")%>
		</td>
	</tr>
	<tr>
		<td class="queryTDLable">財產分號：</td>
		<td class="queryTDInput"> 
		     	 起<input class="field_Q" type="text" name="q_serialNoS" size="7" maxlength="7" value="<%=untmpAddProofQuery.getQ_serialNoS()%>" onChange="addChar(this ,7);">&nbsp;~&nbsp;
			           訖<input class="field_Q" type="text" name="q_serialNoE" size="7" maxlength="7" value="<%=untmpAddProofQuery.getQ_serialNoE()%>" onChange="addChar(this ,7);">
 				批號：<input class="field_Q" type="text" name="q_lotNo" size="7" maxlength="7" value="<%=untmpAddProofQuery.getQ_lotNo()%>">
		</td>
		<td class="queryTDLable"><div align="right">資料狀態：</div></td>
		<td class="queryTDInput">
			<select class="field_Q" type="select" name="q_dataState">
				<%=util.View.getTextOption("1;現存;2;已減損",untmpAddProofQuery.getQ_dataState())%>
			</select>
		</td>
	</tr>
	<tr>
		<td class="queryTDLable">原財產分號：</td>
		<td class="queryTDInput" colspan="3"> 
			起<input class="field_Q" type="text" name="q_oldSerialNoS" size="7" maxlength="7" value="<%=untmpAddProofQuery.getQ_oldSerialNoS()%>">&nbsp;~&nbsp;
			訖<input class="field_Q" type="text" name="q_oldSerialNoE" size="7" maxlength="7" value="<%=untmpAddProofQuery.getQ_oldSerialNoE()%>">
		</td>
	</tr>		
	<tr>
		<td class="queryTDLable">財產別名：</td>
		<td class="queryTDInput" colspan="3"> 
			<input class="field_Q" type="text" name="q_propertyName1" size="25" maxlength="25" value="<%=untmpAddProofQuery.getQ_propertyName1()%>">
		</td>
	</tr>
	<tr>
		<td class="queryTDLable">入帳：</td>
		<td class="queryTDInput">
			<select class="field_Q" type="select" name="q_verify">
			<%=util.View.getYNOption(untmpAddProofQuery.getQ_verify())%>
			</select>
			&nbsp;　　　　月結：
			<select class="field_Q" type="select" name="q_closing">
				<%=util.View.getYNOption(untmpAddProofQuery.getQ_closing())%>
			</select>
		</td>
		<td class="queryTDLable">購置日期：</td>
		<td class="queryTDInput">
			起<%=util.View.getPopCalndar("field_Q","q_buyDateS",untmpAddProofQuery.getQ_buyDateS())%>&nbsp;~&nbsp;
			訖<%=util.View.getPopCalndar("field_Q","q_buyDateE",untmpAddProofQuery.getQ_buyDateE())%>
		</td>		
	</tr>    
	<tr>
		<td class="queryTDLable">財產性質：</td>
		<td class="queryTDInput">
			<select class="field_Q" type="select" name="q_propertyKind">
				<%=util.View.getOption("select codeID, codeName from SYSCA_Code where codeKindID='PKD' ",untmpAddProofQuery.getQ_propertyKind())%>
			</select>
		</td>
		<td class="queryTDLable">基金財產：</td>
		<td class="queryTDInput">
			<%=util.View._getSelectHTML_withEnterOrg("field", "fundType", untmpAddProofQuery.getQ_fundType(),"FUD", untmpAddProofQuery.getQ_enterOrg()) %>
		</td>
	</tr>
	<tr>
		<td class="queryTDLable"><div align="right">珍貴財產：</div></td>
		<td class="queryTDInput">
			<select class="field_Q" type="select" name="q_valuable">
				<%=util.View.getYNOption(untmpAddProofQuery.getQ_valuable())%>
			</select>
		</td>
		<td class="queryTDLable"><div align="right">增加原因：</div></td>
		<td class="queryTDInput">
			<select class="field_Q" type="select" name="q_cause">
				<%=util.View.getOption("select  codeID, codeName from SYSCA_Code where codeKindID='CAB'", untmpAddProofQuery.getQ_cause())%>
			</select>
		</td>
	</tr>
	<tr>
		<td class="queryTDLable"><div align="right">填單日期：</div></td>
		<td class="queryTDInput">
			起<%=util.View.getPopCalndar("field_Q","q_writeDateS",untmpAddProofQuery.getQ_writeDateS())%>&nbsp;~&nbsp;
			訖<%=util.View.getPopCalndar("field_Q","q_writeDateE",untmpAddProofQuery.getQ_writeDateE())%>
		</td>
		<td class="queryTDLable">案名：</td>
		<td class="queryTDInput">
		<input class="field_Q" type="text" name="q_caseName" size="25" maxlength="25" value="<%=untmpAddProofQuery.getQ_caseName()%>">
		</td>
	</tr>
	<tr>
		<td class="queryTDLable">盤點結果：</td>
		<td class="queryTDInput"  colspan="3">
			<select class="field_Q" type="select" name="q_checkResult">
			<%=util.View.getTextOption("1;盤點正常;2;盤點異常",untmpAddProofQuery.getQ_checkResult())%>
			</select>
		</td>
	</tr>
	<tr>
		<td class="queryTDLable">增加單編號：</td>
		<td class="queryTDInput"  colspan="3">
			<input class="field_Q" type="text" name="q_proofDoc" size="10" maxlength="20" value="<%=untmpAddProofQuery.getQ_proofDoc()%>">字
			起<input class="field_Q" type="text" name="q_proofNoS" size="10" maxlength="20" value="<%=untmpAddProofQuery.getQ_proofNoS()%>" onChange="addChar(this ,7);">&nbsp;~&nbsp;
			訖<input class="field_Q" type="text" name="q_proofNoE" size="10" maxlength="20" value="<%=untmpAddProofQuery.getQ_proofNoE()%>" onChange="addChar(this ,7);">號
		</td>
	</tr>
	<tr>
		<td class="queryTDLable"><div align="right">保管單位：</div></td>
		<td class="queryTDInput"  colspan="3">
			<div style="display:none">		
				保管處別
				<select class="field_Q" type="select" name="q_keepBureau" onChange="changeKeepUnit(form1.q_enterOrg, this, form1.q_keepUnit,'');getKeeper(form1.q_enterOrg, form1.q_keepUnit, form1.q_keeper, '','N');">
					<script>changeBureau(form1.q_enterOrg, form1.q_keepBureau, '<%=untmpAddProofQuery.getQ_keepBureau()%>');</script>
				</select>
				&nbsp;&nbsp; 保管單位：
			</div>
			
			<select class="field_Q" type="select" name="q_keepUnit" onChange="getKeeper(form1.q_enterOrg, this, form1.q_keeper, '','N');">
  				<script>changeKeepUnit(form1.q_enterOrg, form1.q_keepBureau, form1.q_keepUnit,'<%=untmpAddProofQuery.getQ_keepUnit()%>');</script>
			</select>
			&nbsp;&nbsp; 保管人：
			<select class="field_Q" type="select" name="q_keeper">
            	<script>getKeeper(form1.q_enterOrg, form1.q_keepUnit, form1.q_keeper, '<%=untmpAddProofQuery.getQ_keeper()%>','N');</script>
            </select>
		</td>
	</tr>
	<tr>
		<td class="queryTDLable"><div align="right">使用單位：</div></td>
		<td class="queryTDInput" colspan="3">
			<div style="display:none">	
				使用處別
				<select class="field_Q" type="select" name="q_useBureau" onChange="changeKeepUnit(form1.q_enterOrg, this, form1.q_useUnit,'');getKeeper(form1.q_enterOrg, form1.q_useUnit, form1.q_userNo, '','N');">
					<script>changeBureau(form1.q_enterOrg, form1.q_useBureau, '<%=untmpAddProofQuery.getQ_useBureau()%>');</script>
				</select>
				&nbsp;&nbsp; 使用單位：
			</div>
			<select class="field_Q" type="select" name="q_useUnit" onChange="getKeeper(form1.q_enterOrg, this, form1.q_userNo, '','N');">
				<script>changeKeepUnit(form1.q_enterOrg, form1.q_useBureau, form1.q_useUnit,'<%=untmpAddProofQuery.getQ_useUnit()%>');</script>
			</select>
			&nbsp;&nbsp; 使用人：
			<select class="field_Q" type="select" name="q_userNo">
			  <script>getKeeper(form1.q_enterOrg, form1.q_useUnit, form1.q_userNo, '<%=untmpAddProofQuery.getQ_userNo()%>','N');</script>
          	</select>
		</td>
	</tr>
			<input class="field_Q" type="hidden" name="queryone_enterOrg" value="<%=untmpAddProofQuery.getQueryone_enterOrg()%>">
			<input class="field_Q" type="hidden" name="queryone_ownership" value="<%=untmpAddProofQuery.getQueryone_ownership()%>">
			<input class="field_Q" type="hidden" name="queryone_caseNo" value="<%=untmpAddProofQuery.getQueryone_caseNo()%>">
			<input class="field_Q" type="hidden" name="queryone_differenceKind" value="<%=untmpAddProofQuery.getQueryone_differenceKind()%>">
	<tr>
		<td class="queryTDInput" colspan="4" style="text-align:center;">
			<input class="toolbar_default" followPK="false" type="submit" name="querySubmit" value="確　　定" >
			<input class="toolbar_default" followPK="false" type="button" name="queryCannel" value="取　　消" onClick="whatButtonFireEvent(this.name)">
		</td>
	</tr>
</table>
