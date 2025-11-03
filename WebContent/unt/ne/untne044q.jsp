<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../home/head.jsp" %>
<script language="javascript" src="../../js/changeEnterOrg_FundType.js"></script>
<script type="text/javascript" src="../../js/function.js"></script>
<%
unt.ne.UNTNE044Q untneQuery = (unt.ne.UNTNE044Q)request.getAttribute("UNTNE044Q");
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
		//物品性質為「03:事業用」時，須控制「基金物品」資料
		if(form1.q_propertyKind.value == "03"){
	 	document.all.q_fundType.disabled=false;
		}else{
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
		<input class="field_Q" type="hidden" name="q_enterOrg" size="10" maxlength="10" value="<%=untneQuery.getQ_enterOrg()%>">
		[<input class="field_QRO" type="text" name="q_enterOrgName" size="20" maxlength="50" value="<%=untneQuery.getQ_enterOrgName()%>">]
		<input class="field_Q" type="button" name="btn_q_enterOrg" onclick="popOrgan('q_enterOrg','q_enterOrgName','N&js=changeAll()')" value="..." title="機關輔助視窗">
	</td>
    <td class="queryTDLable"><div align="right">權屬：</div>
    <td class="queryTDInput">
		<select class="field_Q" type="select" name="q_ownership">
			<%=util.View.getOnwerOption(untneQuery.getQ_ownership())%>
		</select>
	</td>
	<tr>
	  <td class="queryTDLable">電腦單號：</td>
	  <td class="queryTDInput">
			起<input class="field_Q" type="text" name="q_caseNoS" size="10" maxlength="10" value="<%=untneQuery.getQ_caseNoS()%>">&nbsp;~&nbsp;
		           訖<input class="field_Q" type="text" name="q_caseNoE" size="10" maxlength="10" value="<%=untneQuery.getQ_caseNoE()%>">
      </td>
	  <td class="queryTDLable">入帳日期：</td>
	  <td class="queryTDInput">
			起<%=util.View.getPopCalndar("field_Q","q_enterDateS",untneQuery.getQ_enterDateS())%>&nbsp;~&nbsp;
		           訖<%=util.View.getPopCalndar("field_Q","q_enterDateE",untneQuery.getQ_enterDateE())%> 
  	  </td>
	</tr>
	<tr>
		<td class="queryTDLable">物品編號：</td>		
		<td class="queryTDInput" colspan="3">	
			<input name="querySelect" type="hidden" class="field_Q" value="AddProof">			
			起<input class="field_Q" type="text" name="q_propertyNoS" size="12" maxlength="12" value="<%=untneQuery.getQ_propertyNoS()%>" onChange="getProperty('q_propertyNoS','q_propertyNoSName','6')">
			<input class="field_Q" type="button" name="btn_q_propertyNoS" onclick="popProperty('q_propertyNoS','q_propertyNoSName','6')" value="..." title="財產編號輔助視窗">
			[<input class="field_QRO" type="text" name="q_propertyNoSName" size="20" maxlength="50" value="<%=untneQuery.getQ_propertyNoSName()%>">]
			訖<input class="field_Q" type="text" name="q_propertyNoE" size="12" maxlength="12" value="<%=untneQuery.getQ_propertyNoE()%>" onChange="getProperty('q_propertyNoE','q_propertyNoEName','6')">
			<input class="field_Q" type="button" name="btn_q_propertyNoE" onclick="popProperty('q_propertyNoE','q_propertyNoEName','6')" value="..." title="財產編號輔助視窗">
			[<input class="field_QRO" type="text" name="q_propertyNoEName" size="20" maxlength="50" value="<%=untneQuery.getQ_propertyNoEName()%>">]
		</td>
	</tr>	
	<tr>
		<td class="queryTDLable">物品分號：</td>
		<td class="queryTDInput">
			起<input class="field_Q" type="text" name="q_serialNoS" size="7" maxlength="7" value="<%=untneQuery.getQ_serialNoS()%>" onChange="addChar(this ,7);">&nbsp;~&nbsp;
			訖<input class="field_Q" type="text" name="q_serialNoE" size="7" maxlength="7" value="<%=untneQuery.getQ_serialNoE()%>" onChange="addChar(this ,7);">
		</td>		
		<td class="queryTDLable">月結：</td>
		<td class="queryTDInput">
			<select class="field_Q" type="select" name="q_closing">
				<%=util.View.getYNOption(untneQuery.getQ_closing())%>
			</select>
		</td>
	</tr>
	<tr>
		<td class="queryTDLable">物品性質：</td>
		<td class="queryTDInput">
			<select class="field_Q" type="select" name="q_propertyKind" onChange="changeSelect();">			
				<%=util.View.getOption("select codeID, codeName from SYSCA_Code where codeKindID='PKD' ",untneQuery.getQ_propertyKind())%>
			</select>		
		</td>			
		<td class="queryTDLable">基金物品：</td>
		<td class="queryTDInput">
			<select class="field_Q" type="select" name="q_fundType" disabled=true>
				<script>changeEnterOrg_FundType(form1.q_enterOrg.value,'<%=user.getOrganID()%>','q_fundType','<%=untneQuery.getQ_fundType()%>','<%=untneQuery.getIsAdminManager()%>','<%=user.getIsOrganManager()%>');</script>
			</select>
		</td>		
	</tr>
	<tr>
		<td class="queryTDLable">填單日期：</td>
		<td class="queryTDInput" colspan="3">
			起<%=util.View.getPopCalndar("field_Q","q_writeDateS",untneQuery.getQ_writeDateS())%>&nbsp;~&nbsp;
		   	訖<%=util.View.getPopCalndar("field_Q","q_writeDateE",untneQuery.getQ_writeDateE())%> 
		</td>		
	</tr>
	<tr>
		<td class="queryTDLable">增加單編號：</td>
		<td class="queryTDInput" colspan="3">		
			<input class="field_Q" type="text" name="q_proofDoc" size="15" maxlength="20" value="<%=untneQuery.getQ_proofDoc()%>">字
			起<input class="field_Q" type="text" name="q_proofNoS" size="10" maxlength="20" value="<%=untneQuery.getQ_proofNoS()%>" onChange="addChar(this ,7);">&nbsp;~&nbsp;
		   	訖<input class="field_Q" type="text" name="q_proofNoE" size="10" maxlength="20" value="<%=untneQuery.getQ_proofNoE()%>" onChange="addChar(this ,7);">號
		</td>
	</tr>
	<tr>
		<td class="queryTDLable">保管單位：</td>
		<td class="queryTDInput">
			<select class="field_Q" type="select" name="q_keepUnit" onChange="getKeeper(form1.q_enterOrg, this, form1.q_keeper, '');">
  			<%=util.View.getOption("select unitno, unitname from UNTMP_KEEPUNIT where enterorg = '" + user.getOrganID() + "' order by unitno ", untneQuery.getQ_keepUnit()) %>			
			</select>
		</td>
	    <td class="queryTDLable"><div align="right">保管人：</div></td>
	    <td class="queryTDInput"><select class="field_Q" type="select" name="q_keeper">
            <script>getKeeper(form1.q_enterOrg, form1.q_keepUnit, form1.q_keeper, '<%=untneQuery.getQ_keeper()%>');;</script>
          </select>
        </td>
	</tr>
	<tr>
		<td class="queryTDLable">使用單位：</td>
		<td class="queryTDInput">
			<select class="field_Q" type="select" name="q_useUnit" onChange="getKeeper(form1.q_enterOrg, this, form1.q_userNo, '');">
  				<%=util.View.getOption("select unitno, unitname from UNTMP_KEEPUNIT where enterorg = '" + user.getOrganID() + "' order by unitno ", untneQuery.getQ_useUnit())%>			
			</select>

		</td>
	    <td class="queryTDLable"><div align="right">使用人：</div></td>
	    <td class="queryTDInput"><select class="field_Q" type="select" name="q_userNo">
            <script>getKeeper(form1.q_enterOrg, form1.q_useUnit, form1.q_userNo, '<%=untneQuery.getQ_userNo()%>');</script>
          </select>
        </td>
	</tr>
</tr>
	<tr>
	<td class="queryTDInput" colspan="4" style="text-align:center;">
		<input class="toolbar_default" followPK="false" type="submit" name="querySubmit" value="確　　定" >
		<input class="toolbar_default" followPK="false" type="button" name="queryCannel" value="取　　消" onClick="whatButtonFireEvent(this.name)">
	</td>
</table>
