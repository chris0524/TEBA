<%@ page contentType="text/html;charset=UTF-8" import="java.sql.*,util.*"%>
<%@ include file="head.jsp" %>
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

static public String getPopCalndarHere(String className, String inputName, String inputValue) {
	StringBuffer rtnStr = new StringBuffer();
	rtnStr.append("<input class=\""+ className +"\" type=\"text\" name=\""+inputName+"\" size=\"7\" maxlength=\"7\" value=\""+inputValue+"\">\n");		 
	rtnStr.append("<input class=\""+ className +"\" type=\"button\" name=\"btn_"+inputName+"\" onclick=\"popCalndarHere('"+inputName+"')\" value=\"...\" title=\"萬年曆輔助視窗\">\n");
    return rtnStr.toString();
}      
%>
<%
String popField = Common.checkGet(request.getParameter("popField"));

String OrganID = Common.checkGet(request.getParameter("OrganID"));
String Ownership = Common.checkGet(request.getParameter("Ownership"));
String SerialNo = Common.checkGet(request.getParameter("SerialNo"));

String LevyNo = Common.checkGet(request.getParameter("LevyNo"));

String PropertyNo = Common.checkGet(request.getParameter("PropertyNo"));
String preWord = Common.checkGet(request.getParameter("preWord"));
String isLookup = Common.checkGet(request.getParameter("isLookup"));
String jsFunction = Common.checkGet(request.getParameter("js"));
String strJavaScript = "";

String q_bulletinDate = Common.checkGet(request.getParameter("q_bulletinDate"));
String q_suiteDateS = Common.checkGet(request.getParameter("q_suiteDateS"));
String q_suiteDateE = Common.checkGet(request.getParameter("q_suiteDateE"));

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
	if (!"".equals(Common.get(q_bulletinDate)))	
		sql += " and bulletinDate = " + Common.sqlChar(Common.esapi(q_bulletinDate));
	if (!"".equals(Common.get(q_suiteDateS)))
		sql += " and suitDateS>="+Common.sqlChar(Common.esapi(q_suiteDateS));
	if (!"".equals(Common.get(q_suiteDateE)))
		sql += " and suitDateE<="+ Common.sqlChar(Common.esapi(q_suiteDateE));
//System.out.println(sql+" order by bulletindate desc");	
	ResultSet rs = db.querySQL(sql+" order by bulletindate desc");
	while (rs.next()) {
		String valueUnit="";
		String sql2 = " select nvl(a.valueUnit,1)valueUnit from UNTLA_Value a " +
		       " where 1=1 " +
		       " and a.bulletinDate = '" + Common.esapi(rs.getString("bulletindate")) +"' " +
		       "";
	
		if (!"".equals(Common.get(OrganID)))	
		sql2 += " and a.enterOrg = " + Common.sqlChar(Common.esapi(OrganID));
		if (!"".equals(Common.get(Ownership)))
		sql2 += " and a.Ownership ="+Common.sqlChar(Common.esapi(Ownership));
		if (!"".equals(Common.get(SerialNo)))
		sql2 += " and a.SerialNo = "+ Common.sqlChar(Common.esapi(SerialNo));
		if (!"".equals(Common.get(PropertyNo)))
		sql2 += " and a.PropertyNo ="+ Common.sqlChar(Common.esapi(PropertyNo));
		//System.out.println(sql2);
		ResultSet rs2 = db.querySQL(sql2);	
		if(rs2.next()){
			valueUnit=Common.checkGet(rs2.getString("valueUnit"));
		}
	
		StringBuffer strLink = new StringBuffer(0).append(Common.sqlChar(Common.checkGet(rs.getString("bulletindate")))).append( "," ).append(Common.sqlChar(OrganID)).append( "," ).append(Common.sqlChar(Ownership)).append( "," )
		    .append(Common.sqlChar(SerialNo)).append( "," ).append(Common.sqlChar(LevyNo)).append( "," ).append(Common.sqlChar(PropertyNo)).append( "," )
		    .append(Common.sqlChar(Common.checkGet(rs.getString("suitdates")))).append( "," ).append(Common.sqlChar(Common.checkGet(rs.getString("suitdatee"))));				
		sbHTML.append(" <tr class='listTR' onmouseover=\"this.className='listTRMouseover'\" onmouseout=\"this.className='listTR'\" onClick=\"selectBulletinDate(").append( strLink ).append( ")\" >");
		sbHTML.append(" <td class='listTD' >").append(count).append(".</td> ");
		sbHTML.append(" <td class='listTD' >").append(getDotDate(Common.checkGet(rs.getString("bulletindate")))).append("</td> ");
		sbHTML.append(" <td class='listTD' >").append(getDotDate(Common.checkGet(rs.getString("suitdates"))));
		sbHTML.append("~").append(getDotDate(Common.checkGet(rs.getString("suitdatee")))).append("</td> ");	
		sbHTML.append(" <td class='listTD' >").append(valueUnit).append("</td> ");
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
<link rel="stylesheet" href="../js/default.css?1=ss" type="text/css">
<script language="javascript" src="../js/validate.js"></script>
<script language="javascript" src="../js/function.js"></script>
<script language="javascript" src="../js/tablesoft.js"></script>

<script language="javascript">
function selectBulletinDate(bulletinDate,OrganID,Ownership,SerialNo,LevyNo,PropertyNo,suiteDateS,suiteDateE){
	if (isObj(opener.document.all.item("<%=popField%>"))) {		
		opener.document.all.item("<%=popField%>").value=bulletinDate;
		var xmlDoc=document.createElement("xml");	
		xmlDoc.async=false;
		xmlDoc.load("xmlvalueUnit.jsp?bulletinDate="+bulletinDate+"&OrganID="+OrganID+"&Ownership="+Ownership+"&SerialNo="+SerialNo+"&LevyNo="+LevyNo+"&PropertyNo="+PropertyNo);
	    opener.document.all.item("valueUnit").value = xmlDoc.documentElement.childNodes.item(0).getAttribute("valueUnit");
	    opener.document.all.item("receiveValue").value = xmlDoc.documentElement.childNodes.item(0).getAttribute("receiveValue");
	    if(opener.document.all.item("valueUnit").value == "1"){
	        opener.document.all.item("valueUnit").value = "";
	        opener.document.all.item("originalDate").value = "";
	    }  
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
		<th class="listTH">&nbsp;</th>
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',1,false);" href="#">公告年月</a></th>
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',2,false);" href="#">適用期間</a></th>
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',3,false);" href="#">公告地價</a></th>
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
