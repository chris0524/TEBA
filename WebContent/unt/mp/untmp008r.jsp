<!--
*<br>程式目的：動產標籤報表檔 
*<br>程式代號：untmp008r
*<br>撰寫日期：94/11/26
*<br>程式作者：Cherry
*<br>--------------------------------------------------------
*<br>修改作者　　修改日期　　　修改目的
*<br>--------------------------------------------------------
-->
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../home/head.jsp" %>
<jsp:useBean id="obj" scope="request" class="unt.mp.UNTMP008R">
	<jsp:setProperty name="obj" property="*"/>
</jsp:useBean>
<%
obj.setQ_ownership("1");
%>
<html>
<head>
<meta http-equiv="Content-Language" content="zh-tw"/>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
<meta http-equiv="Expires" content="-1"/>
<meta http-equiv="pragma" content="no-cache"/>
<meta http-equiv="Cache-control" content="no-cache"/>
<link rel="stylesheet" href="../../js/default.css" type="text/css">
<script language="javascript" src="../../js/validate.js"></script>
<script language="javascript" src="../../js/function.js"></script>
<script language="javascript" src="../../js/tablesoft.js"></script>
<script language="javascript" src="../../js/changeEnterOrg_FundType.js"></script>
<script language="javascript" src="../../js/changeEnterOrg_KeepUnit.js"></script>
<script language="javascript" src="../../js/sList2.js"></script>
<script language="javascript">

var insertDefault;	//二維陣列, 新增時, 設定預設值
function checkField(){
	var alertStr="";
	alertStr += checkDate(form1.q_enterDateS,"入帳日期-起");
	alertStr += checkDate(form1.q_enterDateE,"入帳日期-訖");
	alertStr += checkDate(form1.q_writeDateS,"填單日期-起");
	alertStr += checkDate(form1.q_writeDateE,"填單日期-訖");
	alertStr += checkInt(form1.q_blankSpace,"標籤跳空");
	alertStr += checkEmpty(form1.q_enterOrg,"管理機關");
	alertStr += checkEmpty(form1.q_ownership,"權屬");
	if(alertStr.length!=0){ 
		alert(alertStr); 
		return false; 
	}else{
		if (document.all.q_labelType[0].checked) {
			form1.action="untmp008p.jsp";
		}else{
    		form1.action="untmp037p.jsp";
    	}
		return true;
	}
}



function link_untmp044f(){
	var url="untmp044f.jsp";
	form1.state.value="queryAll";
	//form1.action=url;
	untmp044f = window.open(url);
	//form1.submit();
}

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

</head>
<body topmargin="10" >

<form id="form1" name="form1" method="post" onReset="form1.q_enterOrg.value='<%=user.getOrganID()%>';alteredEnterOrg();form1.q_propertyKind.value='';changeSelect();" onSubmit="return checkField()" target="_blank">
<input type="hidden" name="userID" value="<%=user.getUserID()%>">
<input type="hidden" name="organID" value="<%=user.getOrganID()%>">
<input type="hidden" name="isOrganManager" value="<%=user.getIsOrganManager()%>">
<input type="hidden" name="isAdminManager" value="<%=user.getIsAdminManager()%>">	
<input type="hidden" name="tempEnterOrg" value="<%=user.getOrganID()%>">		
<table class="bg" width="102%" cellspacing="0" cellpadding="0" align="center"><tr><td>
<input type="hidden" name="state" value="<%=obj.getState()%>">
	<table class="queryTable"  border="1">
	<tr>
        <td class="td_form" colspan="4" style="text-align:center">
        	動產標籤 <font color="red">(A4 直式)</font>
        	<input type="hidden" class="field_Q" name="q_workKind" value="a" size="11" maxlength="11">
        </td>        
	</tr>
<!-- 	
	<tr>
		<td class="queryTDLable"><font color="red">*</font>單據種類：</td>
		<td class="queryTDInput" colspan="4">
			<select class="field_Q" type="select" name="q_workKind">
				<option value='a' selected>增加單</option>
				<option value='j'>增減值單</option>
-->				
				<!-- <option value='l'>減損單</option>  -->
<!--				
				<option value='n'>移動單</option>
			</select>
		</td>
	</tr>
-->
	<tr>
		<td class="queryTDLable"><font color="red">*</font>標籤類別：</td>
		<td class="queryTDInput">
			<input class="field_Q" type="radio" name="q_labelType" value="1" CHECKED>大標籤
			<input class="field_Q" type="radio" name="q_labelType" value="2">小標籤
		</td>
		<td class="queryTDLable">移動單電腦單號</td>
		<td class="queryTDInput" colspan="2">
			<input class="field_Q" type="text" name="q_reduceCaseNo" size="10" maxlength="10" value="<%=obj.getQ_reduceCaseNo()%>">
		</td>
	</tr>
	<tr>
	<td class="queryTDLable" ><font color="red">*</font>入帳機關：</td>
        <td class="queryTDInput">
	  	    <%=util.View.getPopOrgan("field_Q","q_enterOrg",user.getOrganID(),user.getOrganName(),"N&js=changeAll();")%>
	    </td>
		<td class="queryTDLable"><div align="right"><font color="red">*</font>權屬：</div></td>
 		<td class="queryTDInput">
 			<select class="field_Q" type="select" name="q_ownership">
 				<%=util.View.getOnwerOption(obj.getQ_ownership())%>			
			</select>
		</td>
	</tr>
	<tr>
	  <td class="queryTDLable">增加單電腦單號：</td>
	  <td class="queryTDInput">
			  起<input class="field_Q" type="text" name="q_caseNoS" size="10" maxlength="10" value="<%=obj.getQ_caseNoS()%>">&nbsp;~&nbsp;
			  訖<input class="field_Q" type="text" name="q_caseNoE" size="10" maxlength="10" value="<%=obj.getQ_caseNoE()%>">
      </td>
	  <td class="queryTDLable">入帳日期：</td>
	  <td class="queryTDInput">
			  起<%=util.View.getPopCalndar("field_Q","q_enterDateS",obj.getQ_enterDateS())%>&nbsp;~&nbsp;
			  訖<%=util.View.getPopCalndar("field_Q","q_enterDateE",obj.getQ_enterDateE())%>
	  </td>
	</tr>
	<tr>
		<td class="queryTDLable">財產編號：</td>		
		<td class="queryTDInput" colspan="3">	
			<input name="querySelect" type="hidden" class="field_Q" value="AddProof">			
			起<%=util.View.getPopProperty("field_Q","q_propertyNoS",obj.getQ_propertyNoS(),obj.getQ_propertyNoSName(),"3,4,5")%>
			訖<%=util.View.getPopProperty("field_Q","q_propertyNoE",obj.getQ_propertyNoE(),obj.getQ_propertyNoEName(),"3,4,5")%>
		</td>
	</tr>	
	<tr>
		<td class="queryTDLable">財產分號：</td>
		<td class="queryTDInput">
			起<input class="field_Q" type="text" name="q_serialNoS" size="7" maxlength="7" value="<%=obj.getQ_serialNoS()%>" onChange="addChar(this ,7);">&nbsp;~&nbsp;
			訖<input class="field_Q" type="text" name="q_serialNoE" size="7" maxlength="7" value="<%=obj.getQ_serialNoE()%>" onChange="addChar(this ,7);">
		</td>		
		<!--資料狀態：-->
		<input class="field_Q" type="hidden" name="q_dataState" value="1">
	 	<!--審核：-->
		<input class="field_Q" type="hidden" name="q_verify" value="Y">			

		<td class="queryTDLable">月結：</td>
		<td class="queryTDInput">
			<select class="field_Q" type="select" name="q_closing">
				<%=util.View.getYNOption(obj.getQ_closing())%>
			</select>
		</td>
	</tr>
	<tr>
		<td class="queryTDLable">財產性質：</td>
		<td class="queryTDInput">
			<select class="field_Q" type="select" name="q_propertyKind">			
				<%=util.View.getOption("select codeID, codeName from SYSCA_Code where codeKindID='PKD' ",obj.getQ_propertyKind())%>
			</select>		
		</td>			
		<td class="queryTDLable">基金財產：</td>
		<td class="queryTDInput">
			<select class="field_Q" type="select" name="q_fundType">
				<script>changeEnterOrg_FundType(form1.q_enterOrg.value,'<%=user.getOrganID()%>','q_fundType','<%=obj.getQ_fundType()%>','<%=user.getIsAdminManager()%>','<%=user.getIsOrganManager()%>');</script>
			</select>
		</td>		
	</tr>		
	<tr>
		<td class="queryTDLable">珍貴財產：</td>
		<td class="queryTDInput">
			<select class="field_Q" type="select" name="q_valuable">
			<%=util.View.getYNOption(obj.getQ_valuable())%>
			</select>		
		</td>				
		<td class="queryTDLable">填單日期：</td>
		<td class="queryTDInput">
			起<%=util.View.getPopCalndar("field_Q","q_writeDateS",obj.getQ_writeDateS())%>&nbsp;~&nbsp;
			訖<%=util.View.getPopCalndar("field_Q","q_writeDateE",obj.getQ_writeDateE())%> 
		</td>		
	</tr>		
	<tr>
		<td class="queryTDLable">填單編號：</td>
		<td class="queryTDInput" colspan="3">		
			<input class="field_Q" type="text" name="q_proofDoc" size="15" maxlength="20" value="<%=obj.getQ_proofDoc()%>">字
			起<input class="field_Q" type="text" name="q_proofNoS" size="10" maxlength="20" value="<%=obj.getQ_proofNoS()%>" onChange="addChar(this ,7);">&nbsp;~&nbsp;
			訖<input class="field_Q" type="text" name="q_proofNoE" size="10" maxlength="20" value="<%=obj.getQ_proofNoE()%>" onChange="addChar(this ,7);">號
		</td>
	</tr>
	<tr>
		<td class="queryTDLable"><div align="right">保管單位：</div></td>
		<td class="queryTDInput"  colspan="3">
			<div style="display:none">	
			保管處別：
			<select class="field_Q" type="select" name="q_keepBureau" onChange="changeKeepUnit(form1.q_enterOrg, this, form1.q_keepUnit,'');getKeeper(form1.q_enterOrg, form1.q_keepUnit, form1.q_keeper, '','N');">
				<script>changeBureau(form1.q_enterOrg, form1.q_keepBureau, '<%=obj.getQ_keepBureau()%>');</script>
			</select>
			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 
			</div>
			
			<select class="field_Q" type="select" name="q_keepUnit" onChange="getKeeper(form1.q_enterOrg, this, form1.q_keeper, '','N');">
  				<script>changeKeepUnit(form1.q_enterOrg, form1.q_keepBureau, form1.q_keepUnit,'<%=obj.getQ_keepUnit()%>');</script>
			</select>
			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 保管人：
			<select class="field_Q" type="select" name="q_keeper">
            	<script>getKeeper(form1.q_enterOrg, form1.q_keepUnit, form1.q_keeper, '<%=obj.getQ_keeper()%>','N');</script>
            </select>
		</td>
	</tr>
	<tr>
		<td class="queryTDLable"><div align="right">使用單位：</div></td>
		<td class="queryTDInput" colspan="3">
			<div style="display:none">	
			使用處別：
			<select class="field_Q" type="select" name="q_useBureau" onChange="changeKeepUnit(form1.q_enterOrg, this, form1.q_useUnit,'');getKeeper(form1.q_enterOrg, form1.q_useUnit, form1.q_userNo, '','N');">
				<script>changeBureau(form1.q_enterOrg, form1.q_useBureau, '<%=obj.getQ_useBureau()%>');</script>
			</select>
			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 
			</div>
			
			<select class="field_Q" type="select" name="q_useUnit" onChange="getKeeper(form1.q_enterOrg, this, form1.q_userNo, '','N');">
				<script>changeKeepUnit(form1.q_enterOrg, form1.q_useBureau, form1.q_useUnit,'<%=obj.getQ_useUnit()%>');</script>
			</select>
			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 使用人：
			<select class="field_Q" type="select" name="q_userNo">
			  <script>getKeeper(form1.q_enterOrg, form1.q_useUnit, form1.q_userNo, '<%=obj.getQ_userNo()%>','N');</script>
          	</select>
		</td>
	</tr>
	<tr>
		<td class="queryTDLable">標籤跳空：</td>
		<td class="queryTDInput">
			<input class="field_Q" type="text" name="q_blankSpace" size="2" maxlength="2" value="<%=obj.getQ_blankSpace()%>">
		</td>	
		<td class="queryTDLable">列印保管人姓名：</td>
		<td class="queryTDInput">
			<input class="field_Q" type="radio" name="q_printKeeper" value="Y" CHECKED>是
			<input class="field_Q" type="radio" name="q_printKeeper" value="N">否
		</td>
	</tr>
	<tr>
		<td class="queryTDInput" colspan="4" style="text-align:center;">
			<input class="toolbar_default" followPK="false" type="submit" name="querySubmit" value="列　　印" >
			<input class="toolbar_default" followPK="false" type="reset" name="queryCannel" value="取　　消" >
		</td>
	</tr>
	</table>
</td></tr></table>	
</form>
</body>
</html>
