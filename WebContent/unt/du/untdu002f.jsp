<!--
程式目的：動產主檔資料修正
程式代號：untdu002f
程式日期：0950623
程式作者：sam.hsueh
--------------------------------------------------------
修改作者　　修改日期　　　修改目的
--------------------------------------------------------
-->

<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../home/head.jsp" %>
<jsp:useBean id="obj" scope="request" class="unt.du.UNTDU002F">
	<jsp:setProperty name="obj" property="*"/>
</jsp:useBean>
<jsp:useBean id="objList" scope="page" class="java.util.ArrayList"/>
<%
obj.setEnterOrg(user.getOrganID());
obj.setEnterOrgName(user.getOrganName());

if("update".equals(obj.getState()) || "updateError".equals(obj.getState())) {
	obj.update();
}
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
<script language="javascript" src="../../js/getUNTMPMovable_update_1.js"></script>
<script language="javascript">

var insertDefault;	//二維陣列, 新增時, 設定預設值

function checkField(){
	
	var alertStr = "";

	alertStr += checkEmpty(form1.enterOrg,"入帳機關");
	alertStr += checkEmpty(form1.ownership,"權屬");
	alertStr += checkEmpty(form1.propertyNo,"財產編號");
	if(form1.propertyNo.value.substring(0,1)!="2"){
		alertStr += "財產編號開頭需為2\n";
	}	
	alertStr += checkEmpty(form1.serialNo,"分號");
	alertStr += checkEmpty(form1.signNo1,"建物標示");
	alertStr += checkEmpty(form1.signNo2,"建物標示");
	alertStr += checkEmpty(form1.signNo3,"建物標示");
	alertStr += checkEmpty(form1.signNo4,"建號");
	alertStr += checkEmpty(form1.signNo5,"建號");
	form1.state.value = "update";	
	if(alertStr.length!=0){ alert(alertStr); return false; }
	beforeSubmit();
	
}


function sFire(strType) {
	if (strType=='Y') {
		var alertStr = "";
		var len="";
		alertStr += checkEmpty(form1.enterOrg,"入帳機關");
		alertStr += checkEmpty(form1.ownership,"權屬");
		alertStr += checkEmpty(form1.propertyNo,"財產編號");
		alertStr += checkEmpty(form1.serialNo,"財產分號");
						
		if(alertStr.length!=0){ 	
			return false;	
		} else {
			if(form1.serialNo.value.length!=7){ 	
				for(i=0 ; i<7-(form1.serialNo.value.length) ; i++){		
					len += "0";		
				}
				len += form1.serialNo.value;
				form1.serialNo.value=len;	
			}
			if(form1.propertyNo.value.substring(0,1)!="2"){
				alert("財產編號開頭需為2\n");
				return false;
			}	
			getUNTMPMovable_update_1(form1.enterOrg.value, form1.ownership.value, form1.propertyNo.value, form1.serialNo.value);
		}				
	}else{
		//if(form1.originalBV.value!=form1.bookValue.value){
			//form1.bookValue.value=form1.originalBV.value;
		//}
	}
	
}

function init(){
	form1.state.value = "init";
}


</script>
</head>

<body onLoad="init();showErrorMsg('<%=obj.getErrorMsg()%>');">
<form id="form1" name="form1" method="post" onSubmit="return checkField()">

<table width="100%" cellspacing="0" cellpadding="0">

<!--Form區============================================================-->
<tr><td class="bg">
	<div id="formContainer">
	<table class="table_form" width="100%" height="100%">
	<tr>
          <td class="td_form"><font color="red">*</font>入帳機關：</td>
		<td class="td_form_white" colspan="3">		
			<input class="field_PRO" type="hidden" name="enterOrg" size="10" maxlength="10" value="<%=obj.getEnterOrg()%>" >
			[<input class="field_PRO" type="text" name="enterOrgName" size="20" maxlength="50" value="<%=obj.getEnterOrgName()%>">]
			&nbsp;&nbsp;&nbsp;&nbsp;權屬：
      <select class="field_P" type="select" name="ownership" onBlur="sFire('Y');">
        <%=util.View.getOnwerOption("")%>
      </select>
      		</td>
    </tr>  		
	<tr>
		<td class="td_form"><font color="red">*</font>財產編號：</td>
		<td class="td_form_white" colspan="3">
<!--   		<%=util.View.getPopProperty("field_PRO","propertyNo",obj.getPropertyNo(),obj.getPropertyNoName(),"2&isLookup=N&js=sFire('Y')")%>	 -->

 				<input class="field_Q" type="text" name="propertyNo" size="10" maxlength="11" value="<%=obj.getPropertyNo()%>" onChange="getProperty('propertyNo','propertyNoName','2')" onBlur="sFire('Y');">
			<input class="field_Q" type="button" name="btn_propertyNo" onclick="popProperty('propertyNo','propertyNoName','2')" value="..." title="財產編號輔助視窗" onBlur="sFire('Y');">
			[<input class="field_QRO" type="text" name="propertyNoName" size="20" maxlength="50" value="<%=obj.getPropertyNoName()%>" >] 
		
			　　分號：<input class="field" type="text" name="serialNo" size="7" maxlength="7" value="" onBlur="sFire('Y');">
		</td>
		
	</tr>
	<tr>
	  <td class="td_form">門牌：</td>
	  <td class="td_form_white" colspan="3">
        [<input name="doorPlate4" type="text" class="field_PRO" value="<%=obj.getDoorPlate4()%>" size="55">]
</td>
	  </tr>
	<tr>
		<td class="td_form">建物標示：</td>
		<td class="td_form_white" colspan="3">
			<select class="field" type="select" name="signNo1" onChange="changeSignNo1('signNo1','signNo2','signNo3','');">
			<%=util.View.getOption("select signNo, signName from SYSCA_Sign where signNo like '_000000' order by seqno",obj.getSignNo1())%>
			</select>　
			<select class="field" type="select" name="signNo2" onChange="changeSignNo2('signNo1','signNo2','signNo3','');">
				<script>changeSignNo1('signNo1','signNo2','signNo3','<%=obj.getSignNo2()%>');</script>
			</select>　
			<select class="field" type="select" name="signNo3">
				<script>changeSignNo2('signNo1','signNo2','signNo3','<%=obj.getSignNo3()%>');</script>
			</select>
			　建號：		
			<input class="field" type="text" name="signNo4" size="5" maxlength="5" value="<%=obj.getSignNo4()%>"> -
			<input class="field" type="text" name="signNo5" size="3" maxlength="3" value="<%=obj.getSignNo5()%>">
		</td>	
	</tr>

	</table>
	</div>
</td></tr>

<!--Toolbar區============================================================-->

<tr>	
	<td class="bg" style="text-align:center"">
		 	<input type="hidden" name="state" value="<%=obj.getState()%>"> 	 
			<input class="toolbar_default" followPK="false" type="submit" name="update" value="確        定" >
			<input class="toolbar_default" followPK="false" type="reset" name="cannel" value="取　　消">
	</td>
</tr>
</table>
</form>

</body>
</html>



