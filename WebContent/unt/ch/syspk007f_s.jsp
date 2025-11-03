<!--
程式目的：財產編號維護  - 機關自用財產編號
程式代號：syspk007f_s
程式日期：0950607
程式作者：clive.chang
--------------------------------------------------------
修改作者　　修改日期　　　修改目的
--------------------------------------------------------
-->
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../home/head.jsp" %>
<jsp:useBean id="obj" scope="request" class="sys.pk.SYSPK007F_S">
	<jsp:setProperty name="obj" property="*"/>
</jsp:useBean>
<%
obj.setEnterOrg(user.getOrganID());
obj.setIsOrganManager(user.getIsOrganManager());
obj.setIsAdminManager(user.getIsAdminManager());
obj.setIsPop("N");
obj.setPreWord("5");

if ("queryAll".equals(obj.getState())) {
	if ("false".equals(obj.getQueryAllFlag())){obj.setQueryAllFlag("true"); }
}else if ("queryOne".equals(obj.getState())) {
	obj = (sys.pk.SYSPK007F_S)obj.queryOne();	
}else if ("insert".equals(obj.getState()) || "insertError".equals(obj.getState())) {
	obj.insert();
	obj.setQ_propertyNo("");
}else if ("update".equals(obj.getState()) || "updateError".equals(obj.getState())) {
	obj.update();
}else if ("delete".equals(obj.getState()) || "deleteError".equals(obj.getState())) {
	obj.delete();
}else if ("import".equals(obj.getState())) {
	if (obj.doImportProcess(user.getOrganID(), user.getUserID()) && user.getCannotVerify().equals("N")) 
		obj.setErrorMsg("已成功匯入行政院主計處501~503開頭的動產編號！");
}else if ("importNe".equals(obj.getState())) {
	if (obj.doImportNeProcess(user.getOrganID(), user.getUserID()) && user.getCannotVerify().equals("N")) 
		obj.setErrorMsg("已成功匯入3~6類的財產編號！");
}

String strJavaScript = "";
if (obj.getJsFunction()!=null && !"".equals(obj.getJsFunction()) && !"null".equals(obj.getJsFunction())) strJavaScript += "\nopener." + obj.getJsFunction() + ";\n\n";

String strHTML;
strHTML = obj.getPropertyList(new Boolean("true"));
%>

<html>
<head>
<meta http-equiv="Content-Language" content="zh-tw"/>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
<meta http-equiv="Expires" content="1"/>
<meta http-equiv="pragma" content="no-cache"/>
<title>機關自用財產編號維護</title>
<meta http-equiv="Cache-control" content="no-cache"/>
<link rel="stylesheet" href="../../js/default.css" type="text/css">
<script type="text/javascript" src="../../js/validate.js"></script>
<script type="text/javascript" src="../../js/function.js"></script>
<script type="text/javascript" src="../../js/tablesoft.js"></script>
<script type="text/javascript">
var insertDefault;	//二維陣列, 新增時, 設定預設值
function checkField(){
	var alertStr="";
	if(form1.state.value=="queryAll"){
		alertStr += checkQuery();
	}else if(form1.state.value=="insert"||form1.state.value=="update"){
		alertStr += checkEmpty(form1.propertyNo,"財產編號");
		alertStr += checkAlphaInt(form1.propertyNo,"財產編號");
		//alertStr += checkEmpty(form1.propertyType,"財產型態");
		alertStr += checkEmpty(form1.propertyName,"名稱");
		if (form1.limitYear.value!="") alertStr += checkInt(form1.limitYear,"限用年度");
	}
	if(alertStr.length!=0){ alert(alertStr); return false; }
	if (parseInt(form1.limitYear.value,10)<2){
		alert('列為財產之耐用年限應大於等於2年');
		return false;
	}
	beforeSubmit();
}

function doImport() {
	form1.state.value="import";
	form1.submit();
}

function doImportNe() {
	if (confirm("是否繼續執行匯入非消耗品編號?")) {
	    form1.state.value="importNe";
	    form1.submit();
	}
}

function selectProperty(propertyNo,propertyName,propertyType,propertyUnit,material,limitYear, memo, editID, editDate){
	<%=obj.getJS()%>
	if (parseInt(limitYear)<=0) {
		limitYear = "";
	}
	<%=strJavaScript%>	
	
	opener.changePropertyNo(propertyNo);
	
	window.close();
}

function queryOne(propertyNo, propertyName, propertyType, propertyUnit, material, limitYear, memo, editID, editDate){
	form1.propertyNo.value=propertyNo;
	//form1.propertyType.value=propertyType;
	form1.propertyName.value=propertyName;	
	form1.propertyUnit.value=propertyUnit;	
	form1.material.value=material;	
	form1.limitYear.value=limitYear;	
	form1.memo.value=memo;
	form1.editID.value=editID;
	form1.editDate.value=editDate;	
	if (form1.propertyNo.value!="" && form1.propertyName.value!="") {
		form1.state.value="queryOneSuccess";
		whatButtonFireEvent('queryOneSuccess');			
	}	
	//form1.state.value="queryOne";
	//beforeSubmit();
	//form1.submit();
}

function init() {
	setDisplayItem("spanNextInsert,spanListHidden","H");
	//queryHidden('queryContainer');
	
	if(form1.cannotVerify.value=="Y"){
        document.all('spanInsert').disabled='false';
        document.all('spanUpdate').disabled='false';
        document.all('spanDelete').disabled='false';
    }
	form1.isLookup.value = "<%=Common.get(request.getParameter("isLookup"))%>";
}

function showErrorMsg2(error){
	var msg=error;		
	if(msg !=null && msg.length!=0){
		var strMsg = "新增完成,修改完成,刪除完成";
		var sFlag = false;
		try {	
			var arrMsg = strMsg.split(",");
			for (var i=0; i<arrMsg.length; i++) {
				if (arrMsg[i]==msg) sFlag = true;
			}
			if (sFlag) top.frames['title'].showBoxMsg(msg);
			else alert(msg);
			
		} catch(e) {		
		  alert(msg);	
		}	  
	} else {
		try {	
			if (isObj(top.frames['title'])) top.frames['title'].showBoxMsg(msg);
		} catch(e) {
		}
	}
	//if(msg=="新增完成"){window.close();}
	
	if (isObj(form1.permissionField)) doPermission(form1.permissionField.value); 
	return false;
} 
</script>
</head>

<body topmargin="5" onLoad="whatButtonFireEvent('<%=obj.getState()%>');init();showErrorMsg2('<%=obj.getErrorMsg()%>');">
<form id="form1" name="form1" method="post" onSubmit="return checkField()">

<!--Query區============================================================-->
<div id="queryContainer" style="width:400px;height:130px;display:none">
	<iframe id="queryContainerFrame"></iframe>
	<div class="queryTitle">查詢視窗</div>
	<table class="queryTable"  border="1">
	<tr>
		<td class="queryTDLable">財產編號：</td>
		<td class="queryTDInput">
			<input class="field_Q" type="text" name="q_propertyNo" size="11" maxlength="11" value="<%=obj.getQ_propertyNo()%>">
		</td>
	</tr>	
	<tr>
		<td class="queryTDLable">名稱：</td>
		<td class="queryTDInput">
			<input class="field_Q" type="text" name="q_propertyName" size="20" maxlength="50" value="<%=obj.getQ_propertyName()%>">
		    <br><font color="red">※可輸入關鍵字查詢</font>
		</td>
	</tr>
	<tr>
		<td class="queryTDInput" colspan="2" style="text-align:center;">
			<input class="toolbar_default" followPK="false" type="submit" name="querySubmit" value="確　　定" >
			<input class="toolbar_default" followPK="false" type="button" name="queryCannel" value="取　　消" onClick="whatButtonFireEvent(this.name)">
		</td>
	</tr>
	</table>
</div>

<table width="100%" cellspacing="0" cellpadding="0">
<!--Form區============================================================-->
<tr><td class="bg">
	<table class="table_form" width="100%" height="100%">
	<tr>
		<td class="td_form"><font color="red">*</font>財產編號：</td>
		<td class="td_form_white">
			<table width="100%" border="0">
			<tr><td>
				<input class="field_P" type="text" name="propertyNo" size="11" maxlength="11" value="<%=obj.getPropertyNo()%>">
			</td>
			<td align="right" ><%if (obj.getPrefix().equals("'503','504','505'")) {%>
				<a href="#" onClick="doImport();" ><img src="../../image/hand_direction2.gif" align="absmiddle" border="0">匯入行政院主計處動產編號</a>
				<%} %>
				<%//匯入3~6類的財產編號至某機關的非消耗品編號 
					if (obj.getPrefix().equals("6")) {%>
				<a href="#" onClick="doImportNe();"><img src="../../image/hand_direction2.gif" align="absmiddle" border="0">匯入非消耗品編號</a>
				<%} %>
			</td></tr>
			</table>					
		</td>
	</tr>	

	<tr>
		<td class="td_form"><font color="red">*</font>名稱：</td>
		<td class="td_form_white" >
			<input class="field" type="text" name="propertyName" size="20" maxlength="50" value="<%=obj.getPropertyName()%>"></td>
	</tr>
	<tr>
		<td class="td_form">單位：</td>
		<td class="td_form_white">
			<input class="field" type="text" name="propertyUnit" size="5" maxlength="20" value="<%=obj.getPropertyUnit()%>">		　
			材質：
			<input class="field" type="text" name="material" size="10" maxlength="20" value="<%=obj.getMaterial()%>">　
		           最低限用年度：
			<input class="field" type="text" name="limitYear" size="3" maxlength="3" value="<%=obj.getLimitYear()%>"></td>
	    </tr>
	<tr>
		<td class="td_form">備註：</td>
		<td class="td_form_white">
			<input class="field" type="text" name="memo" size="20" maxlength="50" value="<%=obj.getMemo()%>">		</td>
	</tr>
	<tr>
		<td class="td_form">異動資訊：</td>
		<td class="td_form_white">
			[<input class="field_RO" type="text" name="editID" size="10" value="<%=obj.getEditID()%>">/
			<input class="field_RO" type="text" name="editDate" size="7" value="<%=obj.getEditDate()%>">]</td>
	</tr>
	</table>
</td></tr>

<!--Toolbar區============================================================-->
<tr><td class="bg" style="text-align:center">
	<input type="hidden" name="state" value="<%=obj.getState()%>">
	<input type="hidden" name="queryAllFlag" value="<%=obj.getQueryAllFlag()%>">
	<input type="hidden" name="userID" value="<%=user.getUserID()%>">
	<input type="hidden" name="popPropertyNo" value="<%=obj.getPopPropertyNo()%>">
	<input type="hidden" name="popPropertyName" value="<%=obj.getPopPropertyName()%>">
	<input type="hidden" name="preWord" value="<%=obj.getPreWord()%>">
	<input type="hidden" name="q_enterOrg" value="<%=obj.getQ_enterOrg()%>">	
	<input type="hidden" name="isQuery" value="true">
	<input type="hidden" name="isLookup" value="<%=obj.getIsLookup()%>">
	<input type="hidden" name="jsFunction" value="<%=obj.getJsFunction()%>">	
	<input type="hidden" name="cannotVerify" value="<%=user.getCannotVerify()%>">
	<jsp:include page="../../home/toolbar.jsp" />
</td></tr>
<tr><td>
<% request.setAttribute("QueryBean",obj);%>
<jsp:include page="../../home/page.jsp" />
</td></tr>
<!--List區============================================================-->
<tr><td class="bg">
<div id="listContainer" style="width: 100%;	height: 220px;	overflow: auto;	scrollbar-base-color:#eeeeee;">
<table class="table_form" width="100%" cellspacing="0" cellpadding="0">
	<thead id="listTHEAD">
	<tr>
		<th class="listTH" >&nbsp;</th>
		<th class="listTH"><a class="text_link_w">選擇</a></th>		
		<th class="listTH"><a class="text_link_w" onClick="return sortTable('listTBODY',2,false);" href="#">財產編號</a></th>
		<th class="listTH"><a class="text_link_w" onClick="return sortTable('listTBODY',3,false);" href="#">名稱</a></th>
		<th class="listTH"><a class="text_link_w" onClick="return sortTable('listTBODY',4,false);" href="#">材質</a></th>
		<th class="listTH"><a class="text_link_w" onClick="return sortTable('listTBODY',5,false);" href="#">使用年限</a></th>
	</thead>
	<tbody id="listTBODY">
	<%
	//boolean primaryArray[] = {true,true,true,true,true,true,true,true,true,true};
	//boolean displayArray[] = {false,true,false,true,true,true,true,false,false,false};
	//out.write(util.View.getQuerylist(primaryArray,displayArray,objList,obj.getQueryAllFlag()));
	//out.write(obj.getPropertyList());
	
	out.write(strHTML);
	%>
	</tbody>
</table>
</div>
</td></tr>
</table>
</form>
</body>
</html>
