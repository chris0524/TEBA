<!--
*<br>程式目的：非消耗品登記卡查詢檔 
*<br>程式代號：untne006p
*<br>撰寫日期：103/09/19
*<br>程式作者：Mike Kao
*<br>--------------------------------------------------------
*<br>修改作者　　修改日期　　　修改目的
*<br>--------------------------------------------------------
-->
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../home/head.jsp" %>
<jsp:useBean id="obj" scope="request" class="unt.ne.UNTNE006R">
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
	alertStr += checkEmpty(form1.q_enterOrg,"管理機關");
	alertStr += checkEmpty(form1.q_ownership,"權屬");

	var err1=checkAllValue( [form1.q_propertyNoS,form1.q_propertyNoE] ,["財產編號－起","財產編號－訖"] );
	var err2=checkAllValue( [form1.q_enterDateS,form1.q_enterDateE] ,["入帳日期－起","入帳日期－訖"] );
	var err3=checkAllValue( [form1.q_proofYear,form1.q_proofNoS,form1.q_proofNoE] ,["編號年","編號－起","編號－訖"] );
	
	if(err1 != "" && err2 !="" && err3 !=""){
		alertStr += "財產編號－起訖, 入帳日期－起訖, 編號年號－起訖 \n三組須任一組都有值!";
	}	
	if(alertStr.length!=0){ 
		alert(alertStr); 
		return false; 
	}else{
		form1.q_keeper.disabled = false;
    	form1.action="untne006p.jsp";
		return true;
	}
}

function changeSelect(){
	//物品性質為「03:事業用」時，須控制「基金物品」資料
	if(form1.q_propertyKind.value == "03") document.all.q_fundType.disabled=false;
	else{
		document.all.q_fundType.disabled=true;
		form1.q_fundType.value="";
	}
}

function alteredEnterOrg(){
	changeAll();
	changeEnterOrg_FundType(form1.q_enterOrg.value,'<%=user.getOrganID()%>','q_fundType','','<%=user.getIsAdminManager()%>','<%=user.getIsOrganManager()%>');
}

function check_reset(){
	form1.q_enterOrg.value='<%=user.getOrganID()%>';
	alteredEnterOrg();
	changeAll();
	form1.q_propertyKind.value='';
	changeSelect();
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

 
function init() {	
	if ('<%=user.getRoleid()%>' == '1'){
		var mySelect = form1.q_keeper[0];
		mySelect.remove(0);		
		form1.q_keeper.disabled = true;
		form1.btn_q_keeper.disabled = true;
	}
	form1.objPath.value = top.fbody.mainhead.document.getElementById("pathname").innerText;
}

</script>

</head>
<body topmargin="10" onload='init();'>

<form id="form1" name="form1" method="post" action="untne006p.jsp" onReset="check_reset();" onSubmit="return checkField()" target="_blank">
<input type="hidden" name="userID" value="<%=user.getUserID()%>">
<input type="hidden" name="organID" value="<%=user.getOrganID()%>">
<input type="hidden" name="isOrganManager" value="<%=user.getIsOrganManager()%>">
<input type="hidden" name="isAdminManager" value="<%=user.getIsAdminManager()%>">	
<input type="hidden" name="tempEnterOrg" value="<%=user.getOrganID()%>">
<input type="hidden" name="objPath" >
<!-- <input type="hidden" name="q_ownership" value="1"> -->
<!--審核：-->
<input class="field_Q" type="hidden" name="q_verify" value="Y">			
<table class="bg" width="102%" cellspacing="0" cellpadding="0" align="center"><tr><td>
	<table class="queryTable"  border="1">
	<tr>
        <td class="td_form" colspan="4" style="text-align:center">物品登記卡 <font color="red">(A4 直式)</font></td>
	</tr>
	<tr style="display:none">
		<td class="queryTDLable"><font color="red">*</font>單據種類：</td>
		<td class="queryTDInput" colspan="4">
			<select class="field_Q" type="select" name="q_workKind">
				<option value='a' selected>增加單</option>
				<option value='j'>增減值單</option>
				<option value='l'>減損單</option>
				<option value='n'>移動單</option>
			</select>
		</td>
	</tr>
	<tr>
		<td class="queryTDLable"><font color="red">*</font>入帳機關：</td>
		<td class="queryTDInput" colspan="3">
			<input class="field_Q" type="hidden" name="q_enterOrg" size="10" maxlength="10" value="<%=obj.getQ_enterOrg().equals("")?user.getOrganID():obj.getQ_enterOrg()%>">
			[<input class="field_QRO" type="text" name="q_enterOrgName" size="25" maxlength="50" value="<%=obj.getQ_enterOrgName().equals("")?user.getOrganName():obj.getQ_enterOrgName()%>">]
			<input class="field_Q" type="button" name="btn_q_enterOrg" onclick="popOrgan('q_enterOrg','q_enterOrgName','N&js=alteredEnterOrg();')" value="..." title="機關輔助視窗">
		</td>
	</tr>
	<tr>
		<td class="queryTDLable"><div align="right"><font color="red">*</font>權屬：</div></td>
 		<td class="queryTDInput" colspan="3">
 			<select class="field_Q" type="select" name="q_ownership">
 				<%=util.View.getOnwerOption(obj.getQ_ownership())%>			
			</select>
		</td>
	</tr>
	<tr>
	  <td class="queryTDLable">電腦單號：</td>
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
		<td class="queryTDLable">物品編號：</td>		
		<td class="queryTDInput" colspan="3">	
			<input name="querySelect" type="hidden" class="field_Q" value="AddProof">		
			起<input class="field_Q" type="text" name="q_propertyNoS" size="12" maxlength="12" value="<%=obj.getQ_propertyNoS()%>" onChange="getProperty('q_propertyNoS','q_propertyNoSName','','NE')">
			<input class="field_Q" type="button" name="btn_q_propertyNoS" onclick="popProperty('q_propertyNoS','q_propertyNoSName','6')" value="..." title="財產編號輔助視窗">
			[<input class="field_QRO" type="text" name="q_propertyNoSName" size="20" maxlength="50" value="<%=obj.getQ_propertyNoSName()%>">]&nbsp;~&nbsp;
			訖<input class="field_Q" type="text" name="q_propertyNoE" size="12" maxlength="12" value="<%=obj.getQ_propertyNoE()%>" onChange="getProperty('q_propertyNoE','q_propertyNoEName','','NE')">
			<input class="field_Q" type="button" name="btn_q_propertyNoE" onclick="popProperty('q_propertyNoE','q_propertyNoEName','6')" value="..." title="財產編號輔助視窗">
			[<input class="field_QRO" type="text" name="q_propertyNoEName" size="20" maxlength="50" value="<%=obj.getQ_propertyNoEName()%>">]
			
		</td>
	</tr>	
	<tr>
		<td class="queryTDLable">物品分號：</td>
		<td class="queryTDInput">
			起<input class="field_Q" type="text" name="q_serialNoS" size="7" maxlength="7" value="<%=obj.getQ_serialNoS()%>" onChange="addChar(this ,7);">&nbsp;~&nbsp;
			訖<input class="field_Q" type="text" name="q_serialNoE" size="7" maxlength="7" value="<%=obj.getQ_serialNoE()%>" onChange="addChar(this ,7);">
		</td>		
		<td class="queryTDLable"><div align="right">資料狀態：</div></td>
		<td class="queryTDInput">
			<select class="field_Q" type="select" name="q_dataState">
				<option value=''>請選擇</option><option value='1' selected>現存</option><option value='2'>已減損</option>
			</select>
		</td>
	</tr>
	<tr>
		<td class="queryTDLable">月結：</td>
		<td class="queryTDInput">
			<select class="field_Q" type="select" name="q_closing">
				<%=util.View.getYNOption(obj.getQ_closing())%>
			</select>
		</td>
		<td class="queryTDLable">填單日期：</td>
		<td class="queryTDInput">
			起<%=util.View.getPopCalndar("field_Q","q_writeDateS",obj.getQ_writeDateS())%>&nbsp;~&nbsp;
			訖<%=util.View.getPopCalndar("field_Q","q_writeDateE",obj.getQ_writeDateE())%> 
		</td>		
	</tr>
	<tr>
		<td class="queryTDLable">物品性質：</td>
		<td class="queryTDInput">
			<select class="field_Q" type="select" name="q_propertyKind" >			
				<%=util.View.getOption("select codeID, codeName from SYSCA_Code where codeKindID='PKD' ",obj.getQ_propertyKind())%>
			</select>		
		</td>			
		<td class="queryTDLable">基金物品：</td>
		<td class="queryTDInput">
			<select class="field_Q" type="select" name="q_fundType">
				<%=util.View.getOption("select a.codeID, a.codeName from SYSCA_Code a , SYSCA_FUNDORGAN b where a.codeKindID='FUD' and a.codeid=b.fundno"  , obj.getQ_fundType())%>	
			</select>
		</td>		
	</tr>		

	<tr>
		<td class="queryTDLable">珍貴物品：</td>
		<td class="queryTDInput" colspan="3">
			<select class="field_Q" type="select" name="q_valuable">
			<%=util.View.getYNOption(obj.getQ_valuable())%>
			</select>		
		</td>		
	</tr>		
	
	<tr>
		<td class="queryTDLable">增加單編號：</td>
		<td class="queryTDInput" colspan="3">	
			<input class="field_Q" type="text" name="q_proofYear" size="3" maxlength="3" value="<%=obj.getQ_proofYear()%>">年	
			<input class="field_Q" type="text" name="q_proofDoc" size="15" maxlength="20" value="<%=obj.getQ_proofDoc()%>">字
			起<input class="field_Q" type="text" name="q_proofNoS" size="10" maxlength="20" value="<%=obj.getQ_proofNoS()%>" onChange="addChar(this ,7);">&nbsp;~&nbsp;
			訖<input class="field_Q" type="text" name="q_proofNoE" size="10" maxlength="20" value="<%=obj.getQ_proofNoE()%>" onChange="addChar(this ,7);">號
		</td>
	</tr>
	<tr>
		<td class="queryTDLable"><div align="right">保管單位：</div></td>
		<td class="queryTDInput"  colspan="3">
	        	<select class="field_Q" type="select" name="q_keepUnit" onchange="//form1.q_useUnit.value = this.value">
				　　<%=util.View.getOption("select unitno, unitname from UNTMP_KEEPUNIT where enterorg = '" + user.getOrganID() + "' order by unitno ",obj.getQ_keepUnit())%>
				</select>
				<input class="field_Q" type="button" name="btn_q_keepUnit" onclick="popUnitNo(form1.organID.value,'q_keepUnit')" value="..." title="單位輔助視窗">
				&nbsp;&nbsp;&nbsp;保管人
				<%if("1".equals(user.getRoleid())){ %>
					<select class="field_Q" type="select" name="q_keeper" onchange="//form1.q_userNo.value = this.value">
						<%=util.View.getOption("select a.keeperno, CASE a.incumbencyyn WHEN 'N' THEN a.keepername + '(停用)' ELSE a.keepername END AS keepername from UNTMP_KEEPER a LEFT OUTER JOIN SYSAP_EMP b ON a.enterorg = b.organid AND a.keeperno = b.empid where 1=1 and enterorg = '" + user.getOrganID() + "' and  a.keeperno='"+user.getKeeperno()+"' order by case a.incumbencyyn when 'Y' then 'Y' else 'N' end DESC,convert(varchar,keepername) ",user.getKeeperno())%>
		            </select>
				<%}else{ %>
					<select class="field_Q" type="select" name="q_keeper" onchange="//form1.q_userNo.value = this.value">
						<%=util.View.getOption("select a.keeperno, CASE a.incumbencyyn WHEN 'N' THEN a.keepername + '(停用)' ELSE a.keepername END AS keepername from UNTMP_KEEPER a LEFT OUTER JOIN SYSAP_EMP b ON a.enterorg = b.organid AND a.keeperno = b.empid where 1=1 and enterorg = '" + user.getOrganID() + "' order by case a.incumbencyyn when 'Y' then 'Y' else 'N' end DESC,convert(varchar,keepername) ",obj.getQ_keeper())%>
		            </select>
				<%} %>				
		        <input class="field_Q" type="button" name="btn_q_keeper" onclick="popUnitMan(form1.organID.value,'q_keeper')" value="..." title="人員輔助視窗">
		</td>
	</tr>
	<tr>
		<td class="queryTDLable"><div align="right">使用單位：</div></td>
		<td class="queryTDInput" colspan="3">
	        	<select class="field_Q" type="select" name="q_useUnit" onchange="//form1.q_keepUnit.value = this.value">
				<%=util.View.getOption("select unitno, unitname from UNTMP_KEEPUNIT where enterorg = '" + user.getOrganID() + "' order by unitno ",obj.getQ_useUnit())%>
			    </select>	
				<input class="field_Q" type="button" name="btn_q_useUnit" onclick="popUnitNo(form1.organID.value,'q_useUnit')" value="..." title="單位輔助視窗">
				&nbsp;&nbsp;&nbsp;使用人
				<select class="field_Q" type="select" name="q_userNo" onchange="//form1.q_keeper.value = this.value">
					<%=util.View.getOption("select a.keeperno, CASE a.incumbencyyn WHEN 'N' THEN a.keepername + '(停用)' ELSE a.keepername END AS keepername from UNTMP_KEEPER a LEFT OUTER JOIN SYSAP_EMP b ON a.enterorg = b.organid AND a.keeperno = b.empid where 1=1 and enterorg = '" + user.getOrganID() + "' order by case a.incumbencyyn when 'Y' then 'Y' else 'N' end DESC,convert(varchar,keepername) ",obj.getQ_userNo())%>
				</select>
				<input class="field_Q" type="button" name="btn_q_userNo" onclick="popUnitMan(form1.organID.value,'q_userNo')" value="..." title="人員輔助視窗">
		</td>
	</tr>
	<tr>
		<td class="queryTDLable"><font color="red">*</font>報表類型：</td>
		<td class="queryTDInput">
			<select class="field_Q" type="select" id="q_reportType" name="q_reportType">
				<%=View.getJasperReportFormatOption("Excel")%>
			</select>
		</td>
	</tr>	
	<tr>
		<td class="queryTDInput" colspan="4" style="text-align:center;">
			<input class="toolbar_default" followPK="false" type="submit" name="querySubmit" value="確　　定" >
			<input class="toolbar_default" followPK="false" type="reset" name="queryCannel" value="取　　消" >
		</td>
	</tr>
	<tr>
		<td class="queryTDInput" colspan="4" style="text-align:center;">
			<font color="red">本列印作業，顯示列印一次最多100筆</font>
		</td>
	</tr>	
	</table>
</td></tr></table>	
</form>
</body>
</html>
