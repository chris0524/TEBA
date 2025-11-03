<%@ page contentType="text/html;charset=UTF-8" import="java.sql.*,util.*"%>
<%@ include file="../../home/head.jsp" %>
<%!
//將日期加年月日
private String getDotDate(String sDate) {
	if (sDate!=null && sDate.length()>3) {		
		if (sDate.length()==7) return sDate.substring(0,3)+"."+sDate.substring(3,5)+"."+sDate.substring(5);
		else if (sDate.length()==5) return sDate.substring(0,3)+"."+sDate.substring(3,5);
		else return sDate;
	}
	return "";
}

private String transToCE_Year(String s, int len){
	String result = "";
	if(Common.get(s).equals("")){
	}else{
		result = String.valueOf(Integer.parseInt(s.substring(0,3)) + 1911) + s.substring(3,len);
	}
	return result;
}

private String transToROC_Year(String s, int len){
	String result = "";
	if(Common.get(s).equals("")){					
	}else{
		result = Common.formatFrontZero(String.valueOf(Integer.parseInt(s.substring(0,4)) - 1911),3) + s.substring(4,len);
	}
	return result;
}

static public String getPopCalndarHere(String className, String inputName, String inputValue) {
	StringBuffer rtnStr = new StringBuffer();
	rtnStr.append("<input class=\""+ className +"\" type=\"text\" name=\""+inputName+"\" size=\"7\" maxlength=\"7\" value=\""+inputValue+"\">\n");		 
	rtnStr.append("<input class=\""+ className +"\" type=\"button\" name=\"btn_"+inputName+"\" onclick=\"popCalndarHere('"+inputName+"')\" value=\"...\" title=\"萬年曆輔助視窗\">\n");
    return rtnStr.toString();
}      
%>
<%
String popField = Common.get(request.getParameter("popField"));
String preWord = Common.get(request.getParameter("preWord"));
String isLookup = Common.get(request.getParameter("isLookup"));
String jsFunction = Common.get(request.getParameter("js"));
String strJavaScript = "";

String q_bulletinDate = Common.get(request.getParameter("q_bulletinDate"));
String q_suiteDateS = Common.get(request.getParameter("q_suiteDateS"));
String q_suiteDateE = Common.get(request.getParameter("q_suiteDateE"));

if ("Y".equals(Common.get(isLookup))) {
	strJavaScript = "" +	
	"	if (isObj(opener.document.all.item(\"suitDateS\"))) {\n" +
	"		opener.document.all.item(\"suitDateS\").value=suiteDateS;\n" +
	"	}\n" +
	"	if (isObj(opener.document.all.item(\"suitDateE\"))) {\n" +
	"		opener.document.all.item(\"suitDateE\").value=suiteDateE;\n" +
	"	}\n" +
	"	if (isObj(opener.document.all.item(\"suiteDateS\"))) {\n" +
	"		opener.document.all.item(\"suiteDateS\").value=suiteDateS;\n" +
	"	}\n" +
	"	if (isObj(opener.document.all.item(\"suiteDateE\"))) {\n" +
	"		opener.document.all.item(\"suiteDateE\").value=suiteDateE;\n" +
	"	}\n";	
}

if (!"".equals(Common.get(jsFunction))) strJavaScript += "\nopener." + jsFunction + ";\n\n";

StringBuffer sbHTML = new StringBuffer();
if (Common.get(preWord).length()>0) {
	int count = 0;
	Database db = new Database();
	String sql = "select bulletindate, suitdates, suitdatee from UNTLA_BulletinDate where bulletinkind='"+Common.esapi(preWord)+"'";
	if (!"".equals(transToCE_Year(Common.get(q_bulletinDate),5)))	
		sql += " and bulletinDate = " + Common.sqlChar(Common.esapi(transToCE_Year(q_bulletinDate,5)));
	if (!"".equals(transToCE_Year(Common.get(q_suiteDateS),7)))
		sql += " and suitDateS>=" + Common.sqlChar(Common.esapi(transToCE_Year(q_suiteDateS,7)));
	if (!"".equals(transToCE_Year(Common.get(q_suiteDateE),7)))
		sql += " and suitDateE<=" + Common.sqlChar(Common.esapi(transToCE_Year(q_suiteDateE,7)));
	ResultSet rs = db.querySQL(sql+" order by bulletindate desc");	
	
	while (rs.next()) {
		StringBuffer strLink = new StringBuffer(0).append(Common.sqlChar(transToROC_Year(rs.getString("bulletindate"),6))).append( "," ).append(
			Common.sqlChar(transToROC_Year(rs.getString("suitdates"),7))).append( "," ).append(
			Common.sqlChar(transToROC_Year(rs.getString("suitdatee"),7)));				
		sbHTML.append(" <tr class='listTR' onmouseover=\"this.className='listTRMouseover'\" onmouseout=\"this.className='listTR'\" onClick=\"selectBulletinDate(").append( strLink ).append( ")\" >");
		sbHTML.append(" <td class='listTD' >").append(count).append(".</td> ");
		sbHTML.append(" <td class='listTD' >").append(getDotDate(transToROC_Year(rs.getString("bulletindate"),6))).append("</td> ");
		sbHTML.append(" <td class='listTD' >").append(getDotDate(transToROC_Year(rs.getString("suitdates"),8)));
		sbHTML.append("~").append(getDotDate(transToROC_Year(rs.getString("suitdatee"),8))).append("</td> ");		
		sbHTML.append(" </tr> ");	
		count++;
	}
}
%>
<html>
<head>
<title>公告年月輔助視窗</title>
<meta http-equiv="Content-Language" content="zh-tw"/>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
<meta http-equiv="Expires" content="-1"/>
<meta http-equiv="pragma" content="no-cache"/>
<meta http-equiv="Cache-control" content="no-cache"/>
<link rel="stylesheet" href="../../js/default.css?1=ss" type="text/css">
<script type="text/javascript" src="../../js/validate.js"></script>
<script type="text/javascript" src="../../js/function.js"></script>
<script type="text/javascript" src="../../js/tablesoft.js"></script>

<script type="text/javascript">
function selectBulletinDate(bulletinDate,suiteDateS,suiteDateE){
	var length = opener.form1.after_bulletinDate.length;
	for(i=0;i<length;i++){
		opener.form1.after_bulletinDate[i].value=bulletinDate;
	}
	
	<%=strJavaScript%>
	window.close();
}

function checkField(){
	var sb = new StringBuffer();
	if ((form1.q_bulletinDate.value.length==0)&&(form1.q_suiteDateS.value.length==0)&&(form1.q_suiteDateE.value.length==0)){
		sb.append("請至少輸入一個查詢條件!");
	}
	sb.append(checkYYYMM(form1.q_bulletinDate,"公告年月"));
	sb.append(checkDate(form1.q_suiteDateS,"適用期間-起"));
	sb.append(checkDate(form1.q_suiteDateE,"適用期間-起"));
	if (sb.toString().length>0) {
		alert(sb.toString());
		return false;
	} else {
		return true;
	}
}

function popCalndarHere(dateField,js,sY,sM){
	var prop="";
	prop=prop+"scroll:yes;status:no;help:no;";
	prop=prop+"dialogWidth:210px;";
	prop=prop+"dialogHeight:240px";
	var obj = form1;
	var strDate=window.showModalDialog('calendar.jsp?sY='+sY+'&sM='+sM,window,prop);
	if (isObj(strDate)){ 
		document.all.item(dateField).value=strDate;
		if (js!=null && js.length>0)	eval(js);
	}
}
</script>
</head>
<body topmargin="3" leftmargin="3" rightmargin="3" bottommargin="3">
<form id="form1" name="form1" method="post" onSubmit="return checkField()">
<input type="hidden" name="popField" value="<%=popField%>">
<input type="hidden" name="preWord" value="<%=preWord%>">
<input type="hidden" name="isQuery" value="true">
<input type="hidden" name="isLookup" value="<%=isLookup%>">
<table width="100%" cellspacing="0" cellpadding="0">
<!--Form區============================================================-->
<tr><td class="bg" >
	<div id="formContainer" style="height:70px">
	<table class="table_form" width="100%" height="100%">	
	<tr>		
		<td class="td_form">&nbsp;<font color="red">*</font>公告年月：</td>
		<td class="td_form_white">
			<input class="field_Q" type="text" name="q_bulletinDate" size="5" maxlength="5" value="<%=Common.get(q_bulletinDate)%>">
		</td>		
	</tr>	
	
	<tr>		
		<td class="td_form">&nbsp;<font color="red">*</font>適用期間：</td>
		<td class="td_form_white">
			起<%=getPopCalndarHere("field_Q","q_suiteDateS",q_suiteDateS)%>　訖<%=getPopCalndarHere("field_Q","q_suiteDateE",q_suiteDateE)%>
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

<!--List區============================================================-->
<tr><td class="bg" >
<div id="listContainer" style="height:300px">
<table class="table_form" width="100%" cellspacing="0" cellpadding="0">
	<thead id="listTHEAD">
	<tr>
	<thead id="listTHEAD">
	<tr>
		<th class="listTH">&nbsp;</th>
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',1,false);" href="#">公告年月</a></th>
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',2,false);" href="#">適用期間</a></th>
	</tr>		
	</thead>
	<tbody id="listTBODY">
		<%=sbHTML%>
	</tbody>
</table>
</div>
</td></tr>
</table>	
</form>
</body>
</html>
