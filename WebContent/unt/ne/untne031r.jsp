<!--
*<br>程式目的：物品耐用年限已到期明細清冊查詢檔 
*<br>程式代號：untne031r
*<br>撰寫日期：94/11/30
*<br>程式作者：chris
*<br>--------------------------------------------------------
*<br>修改作者　　修改日期　　　修改目的
*<br>--------------------------------------------------------
-->
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../home/head.jsp" %>
<jsp:useBean id="obj" scope="request" class="unt.ne.UNTNE031R">
	<jsp:setProperty name="obj" property="*"/>
</jsp:useBean>
<jsp:useBean id="objList" scope="page" class="java.util.ArrayList"/>

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
<script language="javascript" src="../../js/sList2.js"></script>
<script language="javascript" src="../../unt/mp/changeEnterOrg_KeepUnit.js"></script>
<script language="javascript" src="../../js/changeEnterOrg_FundType.js"></script>
<script language="javascript">
//var insertDefault;	//二維陣列, 新增時, 設定預設值
//insertDefault = new Array(
//	new Array("q_signNo1","S000000")
//);
function checkField(){
    //return true;
	var alertStr="";
	alertStr += checkEmpty(form1.q_enterOrg,"入帳機關");
	alertStr += checkEmpty(form1.q_ownership,"權屬");
	alertStr += checkDate(form1.q_permitReduceDateS,"可報廢日期－起");
	alertStr += checkEmpty(form1.q_permitReduceDateS,"可報廢日期－起");
	alertStr += checkDate(form1.q_permitReduceDateE,"可報廢日期－訖");
	alertStr += checkEmpty(form1.q_permitReduceDateE,"可報廢日期－起");
	if(alertStr.length!=0){ alert(alertStr); return false; }
	return true;
	//beforeSubmit();	
    //form1.submit();
}

function changeSelect(){
	//財產性質為「03:事業用」時，須控制「基金財產」資料
	if(form1.q_propertyKind.value == "03") document.all.q_fundType.disabled=false;
	else{
		document.all.q_fundType.disabled=true;
		form1.q_fundType.value="";
	}
}

function changeAll(){
	changeQ_enterOrg(form1.q_enterOrg.value,'q_keepUnit','');
	changeQ_enterOrgAndKeeper();
}

function changeQ_enterOrgAndKeeper(){
	getKeeper(form1.q_enterOrg, this, form1.q_keeper, '');
}

function alteredEnterOrg(){
	changeAll();
	changeEnterOrg_FundType(form1.q_enterOrg.value,'<%=user.getOrganID()%>','q_fundType','','<%=user.getIsAdminManager()%>','<%=user.getIsOrganManager()%>');
}

function check_reset(){
	form1.q_enterOrg.value='<%=user.getOrganID()%>';
	alteredEnterOrg();
	form1.q_propertyKind.value='';
	changeSelect();
}

</script>
</head>
<body topmargin="10" >
<form id="form1" name="form1" method="post" action="untne031p.jsp" onReset="check_reset();" onSubmit="return checkField()" target="_blank">
<table class="bg" width="70%" cellspacing="0" cellpadding="0" align="center"><tr><td>
<!--隱藏欄位===========================================================-->
<input type="hidden" name="isAdminManager" value="<%=user.getIsAdminManager()%>">
<input type="hidden" name="isOrganManager" value="<%=user.getIsOrganManager()%>">
<input type="hidden" name="organID" value="<%=user.getOrganID()%>">
<input type="hidden" name="tempEnterOrg" value="<%=user.getOrganID()%>">
<input type="hidden" name="q_dataState" value="1">
<input type="hidden" name="q_verify" value="Y">
<input class="field_QRO" type="hidden" name="q_ownership" value="1">
<input class="field_QRO" type="hidden" name="q_valuable" value="N">
<!--==================================================================-->
	<table class="queryTable"  border="1">
	<tr>
        <td class="td_form" colspan="2" style="text-align:center">物品耐用年限已到期明細清冊<font color="red">(A4 橫式)</font></td>
	</tr>
	<tr>
		<td class="queryTDLable"><font color="red">*</font>入帳機關：</td>
		<td class="queryTDInput">
			<input class="field_Q" type="hidden" name="q_enterOrg" size="10" maxlength="10" value="<%=obj.getQ_enterOrg().equals("")?user.getOrganID():obj.getQ_enterOrg()%>">
			[<input class="field_QRO" type="text" name="q_enterOrgName" size="20" maxlength="50" value="<%=obj.getQ_enterOrgName().equals("")?user.getOrganName():obj.getQ_enterOrgName()%>">]
			<input class="field_Q" type="button" name="btn_q_enterOrg" onclick="popOrgan('q_enterOrg','q_enterOrgName','N&js=alteredEnterOrg();')" value="..." title="機關輔助視窗">
		</td>
	</tr>
<!--<tr>
		<td class="queryTDLable"><font color="red">*</font>權屬：</td>
		<td class="queryTDInput">
			<select class="field_Q" type="select" name="q_ownership">
            <option value='1' >市有</option>
            <option value='2' >國有</option>
			</select>
		</td>
	</tr>
-->	
	<tr>
		<td class="queryTDLable">物品編號：</td>		
		<td class="queryTDInput" colspan="3">	
			起<%=util.View.getPopProperty("field_Q","q_propertyNoS",obj.getQ_propertyNoS(),obj.getQ_propertyNoSName(),"6")%>
			<br>訖<%=util.View.getPopProperty("field_Q","q_propertyNoE",obj.getQ_propertyNoE(),obj.getQ_propertyNoEName(),"6")%>
		</td>
	</tr>
	<tr>
		<td class="queryTDLable">保管單位：</td>
		<td class="queryTDInput">
			<select class="field_Q" type="select" name="q_keepUnit" onChange="getKeeper(form1.q_enterOrg, this, form1.q_keeper, '');">
			<%=util.View.getOption("select unitno, unitname from UNTMP_KEEPUNIT where enterorg = '" + user.getOrganID() + "' order by unitno ", obj.getQ_keepUnit()) %>			
			</select>
		</td>
	</tr>
	<tr>
	    <td class="queryTDLable">保管人：</td>
	    <td class="queryTDInput">
	    	<select class="field_Q" type="select" name="q_keeper">
            <script>getKeeper(form1.q_enterOrg, form1.q_keepUnit, form1.q_keeper, '<%=obj.getQ_keepUnit()%>');</script>
          </select>
        </td>
	</tr>
	<tr>
		<td class="queryTDLable"><font color="red">*</font>可報廢日期：</td>
		<td class="queryTDInput">
			起<%=util.View.getPopCalndar("field_Q","q_permitReduceDateS",obj.getQ_permitReduceDateS())%>&nbsp;~&nbsp;
			訖<%=util.View.getPopCalndar("field_Q","q_permitReduceDateE",obj.getQ_permitReduceDateE())%>
		</td>
	</tr>
	<tr>
		<td class="queryTDLable">物品性質：</td>
		<td class="queryTDInput">
			<select class="field_Q" type="select" name="q_propertyKind" onChange="changeSelect();">
				<%=util.View.getOption("select codeID, codeName from SYSCA_Code where codeKindID='PKD' ",obj.getQ_propertyKind())%>
			</select>
		</td>
	</tr>
	<tr>
		<td class="queryTDLable">基金物品：</td>
		<td class="queryTDInput">
			<select class="field_Q" type="select" name="q_fundType" disabled=true>
				<script>changeEnterOrg_FundType(form1.q_enterOrg.value,'<%=user.getOrganID()%>','q_fundType','<%=obj.getQ_fundType()%>','<%=user.getIsAdminManager()%>','<%=user.getIsOrganManager()%>');</script>
			</select>
		</td>
	</tr>
<!--<tr>
		<td class="queryTDLable">珍貴物品：</td>
		<td class="queryTDInput">
			<select class="field_Q" type="select" name="q_valuable">
				<%=util.View.getYNOption(obj.getQ_valuable())%>
			</select>
		</td> 
	</tr>
-->	
	<tr>
		<td class="queryTDInput" colspan="2" style="text-align:center;">
			<input class="toolbar_default" followPK="false" type="submit" name="querySubmit" value="確　　定" >
			<input class="toolbar_default" followPK="false" type="reset" name="queryCannel" value="取　　消" >
		</td>
	</tr>
	</table>
</td></tr></table>	
</form>
</body>
</html>
