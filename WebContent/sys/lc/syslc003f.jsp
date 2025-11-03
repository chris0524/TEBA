<!--
*<br>程式目的：公告地價現值批次轉入 
*<br>程式代號：
*<br>撰寫日期：
*<br>程式作者：
*<br>--------------------------------------------------------
*<br>修改作者　　修改日期　　　修改目的
*<br>--------------------------------------------------------
-->
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../home/head.jsp" %>
<jsp:useBean id="obj" scope="request" class="sys.lc.SYSLC003F">
	<jsp:setProperty name="obj" property="*"/>
</jsp:useBean>
<jsp:useBean id="objList" scope="page" class="java.util.ArrayList"/>

<%
obj.setFileName(this.getServletContext().getRealPath("\\sqlfile\\sys\\lc"));
if ("insert".equals(obj.getState()) || "insertError".equals(obj.getState())) {
	obj.insert();
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

<script language="javascript">
//var insertDefault;	//二維陣列, 新增時, 設定預設值

function checkField() {
	var alertStr="";
	alertStr += checkEmpty(form1.tranType,"轉檔類別");
	alertStr += checkEmpty(form1.bulletinDate,"公告年月");
	alertStr += checkEmpty(form1.tranYYYMM,"轉入年月");
	form1.state.value = "insert";	
	if(alertStr.length!=0){ alert(alertStr); return false; }
}

function change(tranType,bulletinDate,selectValue){
	debugger;
	var queryValue=document.all.item(tranType).value;	
	//清除bulletinDate
	if (isObj(document.all.item(bulletinDate))) {		
		var obj3 = document.all.item(bulletinDate);
		obj3.options.length=0;
		var obj3Option = document.createElement("Option");	
		obj3.options.add(obj3Option);
		obj3Option.innerText = "請選擇";
		obj3Option.value = "";			
	}	
	
	if (queryValue!=""){
		var x = getRemoteData("../../home/xmlSYSOTMovable.jsp?tranType="+queryValue);	
		if (x!=null && x.length!=0) {
			x.replace("\r\n|\n|\r","");
			var arrayOfString = x.split("/");
			for(var i=0; i<arrayOfString.length; i++ ){
		      document.getElementById("bulletinDate").options[i] = new Option(arrayOfString[i],arrayOfString[i]);
			}
		}
	}	
}	

function init(){
	form1.state.value = "init";
}

</script>
</head>
<body topmargin="10" onLoad="init();showErrorMsg('<%=obj.getErrorMsg()%>');">
<form id="form1" name="form1" method="post" onSubmit="return checkField()" >
<input type="hidden" name="userID" value="<%=user.getUserID()%>">
<table class="bg" width="60%" cellspacing="0" cellpadding="0" align="center"><tr><td>
	<table class="queryTable"  border="1">
	<tr>
		<td class="td_form" colspan="3" style="text-align:center">公告地價、申報地價批次轉入主檔</td>
	</tr>

	<tr>
		<td class="queryTDLable" >機關：</td>
		<td class="queryTDInput" colspan="3">
			<%=util.View.getPopOrgan("field_RO","q_enterOrg",user.getOrganID(),user.getOrganName(),"N&js=changeAll();")%>
		</td>		
	</tr>
	<tr>
		<td class="queryTDLable"><font color="red">*</font>轉檔類別：</td>
		<td class="queryTDInput">
			<select type="select" class="field_Q" name="tranType" onChange="change('tranType','bulletinDate','');">
				<%=util.View.getTextOption("1;申報地價;2;公告現值;3;申報地價及公告現值",obj.getTranType())%>
			</select>
		</td>
	</tr>

 	<tr>
		<td class="queryTDLable"><font color="red">*</font>公告年月：</td>
		<td class="queryTDInput">
		<select class="field_Q" type="select" id="bulletinDate" name="bulletinDate">
			<% if("".equals(Common.get(obj.getBulletinDate()))){ %>
				<script>change('tranType','bulletinDate','<%=obj.getBulletinDate()%>');</script>	
			<% } else { %>
				
			<% } %>	
		</select>	
		</td>	
	</tr>
	<tr>
		<td class="queryTDLable"><font color="red">*</font>轉入年月：</td>
		<td class="queryTDInput">
			<select class="field" type="select" name="tranYYYMM">
			　　<%=util.View.getOption("select distinct cast(substring(tranyyymm,1,4) - 1911 as varchar) + substring(tranyyymm,5,2) codeID,cast(substring(tranyyymm,1,4) - 1911 as varchar) + substring(tranyyymm,5,2) codeName from SYSLC_LAND order by cast(substring(tranyyymm,1,4) - 1911 as varchar) + substring(tranyyymm,5,2) desc" ,obj.getTranYYYMM())%>
			</select>
		</td>
	</tr>

	<tr>
		<td class="queryTDInput" colspan="4" style="text-align:center;">
			<input type="hidden" name="state" value="<%=obj.getState()%>">
			<input class="field" type="hidden" name="editID" value="<%=obj.getUserID()%>">
			<input class="toolbar_default" followPK="false" type="submit" name="update" value="確　　定" >
			<input class="toolbar_default" followPK="false" type="reset" name="cannel" value="取　　消" >
		</td>
	</tr>
	</table>
</td></tr></table>	
</form>
</body>
</html>
