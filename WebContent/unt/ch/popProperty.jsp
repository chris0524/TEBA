<%@ page contentType="text/html;charset=UTF-8" import="util.*"%>
<%@ include file="../../home/head.jsp" %>
<jsp:useBean id="obj" scope="request" class="util.General">
	<jsp:setProperty name="obj" property="*"/>
</jsp:useBean>
<%
String q_propertyNo = Common.checkGet(request.getParameter("q_propertyNo"));
String q_propertyName = Common.checkGet(request.getParameter("q_propertyName"));

String popPropertyNo = Common.checkGet(request.getParameter("popPropertyNo"));
String popPropertyName = Common.checkGet(request.getParameter("popPropertyName"));
String preWord = Common.checkGet(request.getParameter("preWord"));
String isQuery = Common.checkGet(request.getParameter("isQuery"));
String isLookup = Common.checkGet(request.getParameter("isLookup"));
String jsFunction = Common.checkGet(request.getParameter("js"));
String strLink = "";

if (Common.get(preWord).equals("6")) {
	//RequestDispatcher dispatcher = request.getRequestDispatcher("syspk007f_s.jsp");
	//dispatcher.forward(request, response);
}

String strJavaScript = "";
if ("Y".equals(Common.get(isLookup))) {
	strJavaScript = "" +	
	"	if (isObj(opener.document.all.item(\"propertyType\"))) {\n" +
	"		opener.document.all.item(\"propertyType\").value=propertyType;\n" +
	"	}\n" +
	"	if (isObj(opener.document.all.item(\"propertyUnit\"))) {\n" +
	"		opener.document.all.item(\"propertyUnit\").value=propertyUnit;\n" +
	"	}\n" +
	"	if (isObj(opener.document.all.item(\"material\"))) {\n" +
	"		opener.document.all.item(\"material\").value=material;\n" +
	"	}\n" +
	"	if (isObj(opener.document.all.item(\"limitYear\"))) {\n" +
	"		opener.document.all.item(\"limitYear\").value=limitYear;\n"+
	"	}\n" +
	"	if (isObj(opener.document.all.item(\"otherLimitYear\"))) {\n" +
	"		if (parseInt(limitYear)>0) {\n"+
	"			opener.document.all.item(\"otherLimitYear\").readOnly=true;\n"+	
	"		}\n"+
	"	}\n";	
}

if (!"".equals(Common.get(jsFunction))) strJavaScript += "\nopener." + jsFunction + ";\n\n";

String sql=" select a.propertyNo, a.propertyName, a.propertyType, a.propertyUnit, a.material, a.limitYear " +
			" from SYSPK_PropertyCode a where a.enterOrg='000000000A' " ;

if (Common.get(preWord).length()>0 && Common.get(preWord).indexOf(",")>0) {
	String[] arrPreWord = preWord.split(",");
	boolean sFlag = true;
	String strSQL1="";
	for (int i=0; i<arrPreWord.length; i++) {
		if ("1".equals(arrPreWord[i])) {
			strSQL1=" and a.propertyNo not like '111%' ";
		}
		if (Integer.parseInt(arrPreWord[i])>=503){
			sFlag = false;
		}
		if (arrPreWord[i].substring(0,1).equals("5") || arrPreWord[i].substring(0,1).equals("6")) {
			strLink = "<a href='#' onClick=\"openUpdateWindow();\">機關自用財產編號</a>";			
		}
	}
	if (sFlag) {
		sql+=" and a.propertyType='1' ";				
	}
	sql += strSQL1 + " and substr(a.propertyNo,0,1) in (" + Common.get(Common.esapi(preWord)) + ")";
	//if (q_propertyNo==null) q_propertyNo=arrPreWord[0];  // 取消預設值   by Timtoy.Tsai 20091118
} else if (Common.get(preWord).length()>0) {
	if (q_propertyNo==null) q_propertyNo=preWord;			
	if (Integer.parseInt(preWord)<503){
		sql+=" and a.propertyType='1' ";
	}
	
	if (preWord.substring(0,1).equals("5") || preWord.substring(0,1).equals("6")) {
		strLink = "<a href='#' onClick=\"openUpdateWindow();\">機關自用財產編號</a>";
	}	
	
	if ("1".equals(preWord)){
		sql+=" and a.propertyNo not like '111%' ";
	}		
	sql+=" and a.propertyNo like '" + Common.get(Common.esapi(preWord)) +"%' ";
} else {
	sql+=" and a.propertyType='1' ";
}
sql+=" and a.propertyNo like '" + Common.get(Common.esapi(q_propertyNo)) +"%' " +
	" and a.propertyName like '%" + Common.get(Common.esapi(q_propertyName)) +"%' " ;

//if(q_propertyNo != null){
//	if(Common.get(q_propertyNo).length() >= 3 && (
//		"503".equals(q_propertyNo.substring(0,3)) ||
//		"504".equals(q_propertyNo.substring(0,3)) ||
//		"505".equals(q_propertyNo.substring(0,3)))) {
		
		strLink = "<a href='#' onClick=\"openUpdateWindow('ch');\">機關自用財產編號</a>";
//	}
//}


String strList = "";
if (isQuery != null) {
	strList = obj.getPropertyList(sql + " order by a.propertyNo", preWord);
}
//StringBuffer sbHTML = new StringBuffer();
%>
<html>
<head>
<title>財產編號輔助視窗</title>
<meta http-equiv="Content-Language" content="zh-tw"/>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
<meta http-equiv="Expires" content="-1"/>
<meta http-equiv="pragma" content="no-cache"/>
<meta http-equiv="Cache-control" content="no-cache"/>
<link rel="stylesheet" href="../../js/default.css?1=ss" type="text/css">
<script type="text/javascript" src="../../js/validate.js"></script>
<script type="text/javascript" src="../../js/function.js"></script>
<script type="text/javascript" src="../../js/tablesoft.js"></script>
<script type="text/javascript" src="../../js/jquery-1.8.2.js"></script>
<script type="text/javascript">
function selectProperty(propertyNo,propertyName,propertyType,propertyUnit,material,limitYear,propertyName1){
	if (isObj(opener.document.all.item("<%=popPropertyNo%>"))) {
		opener.document.all.item("<%=popPropertyNo%>").value=propertyNo;
		
	}
	if (isObj(opener.document.all.item("<%=popPropertyName%>"))) {		
		opener.document.all.item("<%=popPropertyName%>").value=propertyName;		
	}
	if (isObj(opener.document.all.item("propertyName1"))) {
		if(propertyName1 == null){
			propertyName1 = "";
		}
		opener.document.all.item("propertyName1").value=propertyName;		
	}
	if (isObj(opener.document.all.item("material"))) {
		if(material == null){
			material = "";
		}
		opener.document.all.item("material").value=material;		
	}
	if (isObj(opener.document.all.item("otherMaterial"))) {
		if(material == null){
			material = "";
		}
		opener.document.all.item("otherMaterial").value=material;		
	}
	if (isObj(opener.document.all.item("propertyUnit"))) {
		if(propertyUnit == null){
			propertyUnit = "";
		}
		opener.document.all.item("propertyUnit").value=propertyUnit;		
	}
	if (isObj(opener.document.all.item("otherPropertyUnit"))) {
		if(propertyUnit == null){
			propertyUnit = "";
		}
		opener.document.all.item("otherPropertyUnit").value=propertyUnit;		
	}
	if (isObj(opener.document.all.item("limitYear"))) {
		if(limitYear == null){
			limitYear = "";
		}
		opener.document.all.item("limitYear").value=limitYear;		
	}
	
	if (parseInt(limitYear)<=0) {
		limitYear = "";
	}
	<%=strJavaScript%>

	opener.changePropertyNo(propertyNo);
	
	window.close();
}

function checkField(){
	columnTrim(form1.q_propertyNo);
	columnTrim(form1.q_propertyName);
	if ((form1.q_propertyNo.value.length==0)&&(form1.q_propertyName.value.length==0)){
		alert("「財產編號」或「財產名稱」請至少輸入一個查詢條件!");
		return false;
	}
}

function openUpdateWindow(type) {
	if (isObj(opener.document.all.item("q_enterOrg"))) {
		form1.q_enterOrg.value = opener.document.all.item("q_enterOrg").value;
	}
	if ("ne" == type) { //非消
		form1.action = "../unt/ne/syspk007f_s.jsp";
	} else { // 財產自用
		form1.action = "../unt/ch/syspk007f_s.jsp";
	}
	form1.submit();
}

function init() {
	if (form1.preWord.value=="6") {
		openUpdateWindow("ne");
	}
}
</script>
</head>
<body topmargin="3" leftmargin="3" rightmargin="3" bottommargin="3" onLoad="init();">
<form id="form1" name="form1" method="post" onSubmit="return checkField()">
<input type="hidden" name="q_enterOrg" value="<%=user.getOrganID()%>">
<input type="hidden" name="popPropertyNo" value="<%=popPropertyNo%>">
<input type="hidden" name="popPropertyName" value="<%=popPropertyName%>">
<input type="hidden" name="preWord" value="<%=preWord%>">
<input type="hidden" name="isQuery" value="true">
<input type="hidden" name="isLookup" value="<%=isLookup%>">
<input type="hidden" name="jsFunction" value="<%=jsFunction%>">
<table width="100%" cellspacing="0" cellpadding="0">
<!--Form區============================================================-->
<tr><td class="bg" >
	<div id="formContainer" style="height:70px">
	<table class="table_form" width="100%" height="100%">	
	<tr>		
		<td class="td_form">&nbsp;<font color="red">*</font>財產編號：</td>
		<td class="td_form_white">
			<input class="field" type="text" name="q_propertyNo" size="11" maxlength="11" value="<%=Common.get(q_propertyNo)%>">　<%=strLink%>
		</td>		
	</tr>	
	
	<tr>		
		<td class="td_form">&nbsp;<font color="red">*</font>財產名稱：</td>
		<td class="td_form_white">
			<input class="field" type="text" name="q_propertyName" size="20" maxlength="30" value="<%=Common.get(q_propertyName)%>"><font color="red">※可輸入關鍵字查詢</font>
		</td>		
	</tr>	
	</table>
	</div>
</td></tr>

<!--Toolbar區============================================================-->
<tr><td class="bg" style="text-align:center">
	<input class="toolbar_default" followPK="false" type="submit" name="querySubmit" value="確　　定" >
	<input class="toolbar_default" followPK="false" type="button" name="queryCannel" value="取　　消" onClick="window.close()">
</td></tr>
<tr><td>
<% request.setAttribute("QueryBean",obj);%>
<jsp:include page="../../home/page.jsp" />
</td></tr>
<!--List區============================================================-->
<tr><td class="bg" >
<div id="listContainer" style="height:300px">
<table class="table_form" width="100%" cellspacing="0" cellpadding="0">
	<thead id="listTHEAD">
	<tr>
		<th class="listTH">&nbsp;</th>
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',1,false);" href="#">財產編號</a></th>
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',2,false);" href="#">財產名稱</a></th>
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',3,false);" href="#">材質</a></th>
	</tr>		
	</thead>
	<tbody id="listTBODY">
		<%=strList%>
	</tbody>
</table>
</div>
</td></tr>
</table>	
</form>
</body>
</html>
