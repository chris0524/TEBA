<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../home/head.jsp" %>
<script language="javascript" src="../../js/changeEnterOrg_FundType.js"></script>
<script type="text/javascript" src="../../js/function.js"></script>
<%
unt.mp.UNTMP044Q untmpAddProofQuery = (unt.mp.UNTMP044Q)request.getAttribute("UNTMP044Q");
%>
<script>
		function changeAll(){
			changeQ_enterOrg(form1.q_enterOrg.value,'q_keepUnit','');
			changeQ_enterOrg(form1.q_enterOrg.value,'q_useUnit','');
			changeQ_enterOrgAndKeeper();
			changeEnterOrg_FundType(form1.q_enterOrg.value,'<%=user.getOrganID()%>','q_fundType','','<%=user.getIsAdminManager()%>','<%=user.getIsOrganManager()%>');
		}
		
		function changeQ_enterOrgAndKeeper(){
			getKeeper(form1.q_enterOrg, this, form1.q_keeper, '');
			getKeeper(form1.q_enterOrg, this, form1.q_userNo, '');
		}
		
		function changeQ_enterOrg(q_enterOrg,q_No,selectValue){
			var obj2 = document.all.item(q_No);
			obj2.options.length=0;
			var oOption = document.createElement("Option");	
			obj2.options.add(oOption);
			oOption.innerText = "請選擇";
			oOption.value = "";		
			var queryValue=(q_enterOrg==""?"<%=user.getOrganID()%>":q_enterOrg);	
			if (queryValue!=""){
				var xmlDoc=document.createElement("xml");	
				xmlDoc.async=false;			
				if(xmlDoc.load("xmlQ_enterOrg.jsp?q_enterOrg="+queryValue)){		
					var nodesLen=xmlDoc.documentElement.childNodes.length;
					for(i=0; i<nodesLen; i++){
						unitNo=xmlDoc.documentElement.childNodes.item(i).getAttribute("unitNo");
						unitName=xmlDoc.documentElement.childNodes.item(i).getAttribute("unitName");
						var oOption = document.createElement("Option");	
						obj2.options.add(oOption);
						oOption.innerText = unitName;
						oOption.value = unitNo;		
				      	if(unitNo == selectValue){
		        			oOption.selected=true;
						}           										
					}
				}else{
					return false;	
				}
			}	
		}

function changeSelect(){
	//財產性質為「03:事業用」時，須控制「基金財產」資料
	if(form1.q_propertyKind.value == "03") document.all.q_fundType.disabled=false;
	else{
		document.all.q_fundType.disabled=true;
		form1.q_fundType.value="";
	}
}

</script>

<!--Query區============================================================-->
<table class="queryTable"  border="1">
<tr>
	<td class="queryTDLable" >入帳機關：</td>
	<td class="queryTDInput" >
		<input class="field_Q" type="hidden" name="q_enterOrg" size="10" maxlength="10" value="<%=untmpAddProofQuery.getQ_enterOrg()%>">
		[<input class="field_QRO" type="text" name="q_enterOrgName" size="20" maxlength="50" value="<%=untmpAddProofQuery.getQ_enterOrgName()%>">]
		<input class="field_Q" type="button" name="btn_q_enterOrg" onclick="popOrgan('q_enterOrg','q_enterOrgName','N&js=changeAll()')" value="..." title="機關輔助視窗">
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
		起<input class="field_Q" type="text" name="q_caseNoS" size="10" maxlength="10" value="<%=untmpAddProofQuery.getQ_caseNoS()%>">&nbsp;~&nbsp;
		訖<input class="field_Q" type="text" name="q_caseNoE" size="10" maxlength="10" value="<%=untmpAddProofQuery.getQ_caseNoE()%>">
	</td>
		<td class="queryTDLable">入帳日期：</td>
		<td class="queryTDInput">
		起<%=util.View.getPopCalndar("field_Q","q_enterDateS",untmpAddProofQuery.getQ_enterDateS())%>&nbsp;~&nbsp;
		訖<%=util.View.getPopCalndar("field_Q","q_enterDateE",untmpAddProofQuery.getQ_enterDateE())%> 
	</td>
</tr>
<tr>
	<td class="queryTDLable">財產編號：</td>		
	<td class="queryTDInput" colspan="3">	
	<input name="querySelect" type="hidden" class="field_Q" value="AddProof">			
	起<%=util.View.getPopProperty("field_Q","q_propertyNoS",untmpAddProofQuery.getQ_propertyNoS(),untmpAddProofQuery.getQ_propertyNoSName(),"3,4,5")%>&nbsp;~&nbsp;
	訖<%=util.View.getPopProperty("field_Q","q_propertyNoE",untmpAddProofQuery.getQ_propertyNoE(),untmpAddProofQuery.getQ_propertyNoEName(),"3,4,5")%>
	</td>
</tr>
<tr>
	<td class="queryTDLable">財產分號：</td>
	<td class="queryTDInput">
		起<input class="field_Q" type="text" name="q_serialNoS" size="7" maxlength="7" value="<%=untmpAddProofQuery.getQ_serialNoS()%>" onChange="addChar(this ,7);">&nbsp;~&nbsp;
		訖<input class="field_Q" type="text" name="q_serialNoE" size="7" maxlength="7" value="<%=untmpAddProofQuery.getQ_serialNoE()%>" onChange="addChar(this ,7);">
	</td>		
		<!--資料狀態：-->
		<input class="field_Q" type="hidden" name="q_dataState" value="1">
	 	<!--審核：-->
		<input class="field_Q" type="hidden" name="q_verify" value="Y">			
	<td class="queryTDLable">月結：</td>
	<td class="queryTDInput">
		<select class="field_Q" type="select" name="q_closing">
			<%=util.View.getYNOption(untmpAddProofQuery.getQ_closing())%>
		</select>
	</td>
</tr>
<tr>
	<td class="queryTDLable">財產性質：</td>
	<td class="queryTDInput">
		<select class="field_Q" type="select" name="q_propertyKind" onChange="changeSelect();">			
			<%=util.View.getOption("select codeID, codeName from SYSCA_Code where codeKindID='PKD' ",untmpAddProofQuery.getQ_propertyKind())%>
		</select>		
	</td>			
	<td class="queryTDLable">基金財產：</td>
	<td class="queryTDInput">
		<select class="field_Q" type="select" name="q_fundType" disabled=true>
			<script>changeEnterOrg_FundType(form1.q_enterOrg.value,'<%=user.getOrganID()%>','q_fundType','<%=untmpAddProofQuery.getQ_fundType()%>','<%=user.getIsAdminManager()%>','<%=user.getIsOrganManager()%>');</script>
		</select>
	</td>		
</tr>
<tr>
	<td class="queryTDLable">珍貴財產：</td>
	<td class="queryTDInput">
		<select class="field_Q" type="select" name="q_valuable">
			<%=util.View.getYNOption(untmpAddProofQuery.getQ_valuable())%>
	</select>		
	</td>				
	<td class="queryTDLable">填單日期：</td>
	<td class="queryTDInput">
		起<%=util.View.getPopCalndar("field_Q","q_writeDateS",untmpAddProofQuery.getQ_writeDateS())%>&nbsp;~&nbsp;
		訖<%=util.View.getPopCalndar("field_Q","q_writeDateE",untmpAddProofQuery.getQ_writeDateE())%> 
	</td>		
</tr>
<tr>
	<td class="queryTDLable">增加單編號：</td>
	<td class="queryTDInput" colspan="3">		
		<input class="field_Q" type="text" name="q_proofDoc" size="15" maxlength="20" value="<%=untmpAddProofQuery.getQ_proofDoc()%>">字
			起<input class="field_Q" type="text" name="q_proofNoS" size="10" maxlength="20" value="<%=untmpAddProofQuery.getQ_proofNoS()%>" onChange="addChar(this ,7);">&nbsp;~&nbsp;
			訖<input class="field_Q" type="text" name="q_proofNoE" size="10" maxlength="20" value="<%=untmpAddProofQuery.getQ_proofNoE()%>" onChange="addChar(this ,7);">號
	</td>
</tr>
<tr>
	<td class="queryTDLable">保管單位：</td>
	<td class="queryTDInput">
		<select class="field_Q" type="select" name="q_keepUnit" onChange="getKeeper(form1.q_enterOrg, this, form1.q_keeper, '');">
  			<script>changeQ_enterOrg(form1.q_enterOrg.value,'q_keepUnit','<%=untmpAddProofQuery.getQ_keepUnit()%>');</script>
		</select>
	</td>
	<td class="queryTDLable"><div align="right">保管人：</div></td>
	<td class="queryTDInput"><select class="field_Q" type="select" name="q_keeper">
		<script>getKeeper(form1.q_enterOrg, form1.q_keepUnit, form1.q_keeper, '<%=untmpAddProofQuery.getQ_keeper()%>');</script>
	</select>
	</td>
</tr>
<tr>
	<td class="queryTDLable">使用單位：</td>
	<td class="queryTDInput">
		<select class="field_Q" type="select" name="q_useUnit" onChange="getKeeper(form1.q_enterOrg, this, form1.q_userNo, '');">
			<script>changeQ_enterOrg(form1.q_enterOrg.value,'q_useUnit','<%=untmpAddProofQuery.getQ_useUnit()%>');</script>
		</select>
	</td>
	<td class="queryTDLable"><div align="right">使用人：</div></td>
	<td class="queryTDInput"><select class="field_Q" type="select" name="q_userNo">
		<script>getKeeper(form1.q_enterOrg, form1.q_useUnit, form1.q_userNo, '<%=untmpAddProofQuery.getQ_userNo()%>');</script>
	</select>
	</td>
</tr>
<tr>
	<td class="queryTDLable">存置地點：</td>
	<td class="queryTDInput" colspan="3">
		<input type="text" class="field" name="" value="" size="30">
	</td>
</tr>
	<tr>
	<td class="queryTDInput" colspan="4" style="text-align:center;">
		<input class="toolbar_default" followPK="false" type="submit" name="querySubmit" value="確　　定" >
		<input class="toolbar_default" followPK="false" type="button" name="queryCannel" value="取　　消" onClick="whatButtonFireEvent(this.name)">
	</td>
	</tr>
</table>
