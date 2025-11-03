<!--
程式目的：
程式代號：
程式日期：
程式作者：
--------------------------------------------------------
修改作者　　修改日期　　　修改目的
--------------------------------------------------------
-->

<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%> 
<%@ include file="../../home/head.jsp" %>
<jsp:useBean id="obj" scope="request" class="unt.dp.UNTDP001F">
	<jsp:setProperty name="obj" property="*"/>
</jsp:useBean>
<jsp:useBean id="objList" scope="page" class="java.util.ArrayList"/>

<%
if ("printAddProof".equals(obj.getState())) {
	obj.printAddProof();

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
<script language="javascript" src="../../js/changeEnterOrg_FundType.js"></script>
<script language="javascript" src="../../js/changeEnterOrg_KeepUnit.js"></script>
<script language="javascript" src="../../js/sList2.js"></script>
<script language="javascript">
var insertDefault;	//二維陣列, 新增時, 設定預設值

insertDefault = new Array(
	
);

function getEnterDate() {

}

function checkField(){
	//return true;
	var alertStr="";
	alertStr += checkEmpty(form1.enterOrg,"入帳機關");
	alertStr += checkEmpty(form1.deprYM,"本次計算折舊的年月");
	alertStr += checkYYYMM(form1.deprYM,"本次計算折舊的年月");
	//alertStr += checkEmpty(form1.propertyType,"財產大類");
	alertStr += checkEmpty(form1.differenceKind,"財產區分別");
	if(alertStr.length!=0){ alert(alertStr); return false; }
	return confirmData();
}
function insertData(){
	
	document.all.state.value='printAddProof';	
	//form1.submit();
	
	
}
function getRemoteData(uri,q) {
		var x ;
		if(window.XMLHttpRequest){
			x = new XMLHttpRequest();
		} else {
			x = new ActiveXObject("Microsoft.XMLHTTP"); 
		}
		var url=uri;
		x.open('Post',url,false); 
		x.setRequestHeader('Content-type', 'application/x-www-form-urlencoded');
		x.setRequestHeader( "If-Modified-Since", "Sat, 1 Jan 2000 00:00:00 GMT" );
		x.send(encodeURI('q='+q));
		return x.responseText.trim();      
}
function confirmData(){
		var q="1&enterOrg=" + form1.enterOrg.value+"&deprYM="+form1.deprYM.value+"&propertyType="+form1.propertyType.value+"&differenceKind="+form1.differenceKind.value;
		var ajaxuri="untdp001f_jason.jsp";
		var x=getRemoteData(ajaxuri,q);
		var sign = eval('(' + x + ')');
		if (sign.isVerify) {
			alert("資料已入帳, 無法再計算折舊");
			return false;
		}
		
		if(sign['detail']==null || sign['detail']==""){
			insertData();
			return true;
		} else {
			var detail=sign['detail'];
			for(var i=0;i<detail.length;i++){
				var item = detail[i];
				var enterorg = item.enterorg;
				var deprym = item.deprym;
				var propertytype = item.propertyType;
				var differencekind = item.differencekind;
			}
			if(confirm("已存在資料，是否重新計算折舊")){
				deletedata();
				insertData();
				return true;
			} else{
				return false;
			}
		}	
}
function deletedata() {

	var q="1&enterOrg=" + form1.enterOrg.value+"&deprYM="+form1.deprYM.value+"&propertyType="+form1.propertyType.value+"&differenceKind="+form1.differenceKind.value;
	var ajaxuri="untdp001fdelete_jason.jsp";
	var x=getRemoteData(ajaxuri,q);
	
}

function queryOne(enterOrg,ownership,caseNo){

}

function clickApproveAll(){

}

function checkURL(surl){
	form1.action = surl;
	beforeSubmit();
	form1.submit();
}

function init(){
	var arrField = new Array("update","delete");
	if (form1.enterOrg.value!="<%=user.getOrganID()%>" ) {		
		setFormField(new Array("update","delete"),"R");
	}

	setFormItem("printAddProof", "O");	
	document.all.state.value='init';
	
}

function loadUntch012r(){
	
}

function loadUntch013r(){
	
}

function checkID()
{
	if(form1.isAdminManager.value=='Y'){
	
		document.getElementById('tr1').style.display="";
	}	   
}
</script>
</head>

<body topmargin="0" onLoad="checkID();whatButtonFireEvent('<%=obj.getState()%>');init();showErrorMsg('<%=obj.getErrorMsg()%>');">
<form id="form1" name="form1" method="post" onSubmit="return checkField()">

<!--隱藏欄位區============================================================-->
<table style="display:none"><tr><td>
	<input type="text" name="state" value="<%=obj.getState()%>">
	<input type="hidden" name="queryAllFlag" value="<%=obj.getQueryAllFlag()%>">
	<input type="hidden" name="userID" value="<%=user.getUserID()%>">
	<input type="hidden" name="organID" value="<%=user.getOrganID()%>">
	<input type="hidden" name="isOrganManager" value="<%=user.getIsOrganManager()%>">
	<input type="hidden" name="isAdminManager" value="<%=user.getIsAdminManager()%>">
	
</td></tr></table>

<!--頁籤區============================================================-->
<table STYLE="width:100%;" CELLPADDING=0 CELLSPACING=0 valign="top">	
		
</table>

<!--Form區============================================================-->
<table width="70%" cellspacing="0" cellpadding="0" align="center">
<tr><td class="bg">
	<table class="queryTable" border="1">
		<tr id="tr1" style="display:none">
			<td class="queryTDLable" ><font color="red">*</font>入帳機關：</td>
	        <td class="queryTDInput">
		  	    <%=util.View.getPopOrgan("field_Q","enterOrg",user.getOrganID(),user.getOrganName(),"N")%>
		    </td>		
		</tr>
		<tr>
			<td class="queryTDLable" width="30%"><font color="red">*</font>本次計算折舊的年月：</td>
			<td class="queryTDInput">
				<br>
				<input type="text" class="field_Q" name="deprYM"  size="5" maxlength="5">				
				<br>
				<font color="red">例如：103年 2月，則輸入10302</font>
			</td>
		</tr>
		<tr>
			<td class="queryTDLable" width="30%"><font color="red"></font>財產大類：</td>
			<td class="queryTDInput">
				<select class="field_Q" type="select" name="propertyType">
				　　<%=util.View.getOption("select codeID, codeName from SYSCA_Code where codeKindID='PTT' and codeid in ('2','3','4','5','6','8') ",obj.getPropertyType())%>
				</select>
			</td>
		</tr>
		<tr>
      <td class="queryTDLable"><font color="red">*</font>財產區分別 ：</td>
      <td class="queryTDInput"><select class="field_Q" type="select" name="differenceKind">
          <%=util.View.getOption("select codeID, codeName from SYSCA_Code where codeKindID='DFK' ",obj.getDifferenceKind())%>
        </select>      </td>
       </tr>
		<tr>
			<td class="queryTDLable" width="30%">提示：</td>
			<td class="queryTDInput">
				根據折舊財產的數量不同，會花不同的計算時間，如果有大量的財產折舊，將會花較多的時間，請耐心等待。
			</td>
		</tr>
	</table>
</td></tr>

<!--Toolbar區============================================================-->
<tr><td class="bg" style="text-align:center;">
	<div style="display:none;">
	<jsp:include page="../../home/toolbar.jsp" />
	</div>
	<span id="btnUntch012r">
		<br>&nbsp;|
		<input class="toolbar_default" type="submit" followPK="false" name="printAddProof" value="開始計算" >&nbsp;|
	</span>
	
</td></tr>

<!--List區============================================================-->

</table>
</form>
</body>
</html>



