<%@ page contentType="text/html;charset=UTF-8"%>
<jsp:useBean id="obj" scope="page" class="sys.wm.SYSWM001F"/>
<%@ page import="util.*" %>
<%
String newsID = util.Common.checkGet(request.getParameter("newsID"));
String isHTML = "N";
obj.setNewsID(newsID);
obj.queryOne();
StringBuffer sbHTML = new StringBuffer();

String[] arrFileName=null;	


if (!"".equals(obj.getItemPicture1())) {
	arrFileName=obj.getItemPicture1().split(":;:");
	if (arrFileName.length>1) {
		sbHTML.append("<tr>\n");
		sbHTML.append("<td bgcolor='E2F0CF' class='td_form'>附件一：</td>");
		sbHTML.append("<td bgcolor='F6FCE9' class='td_form_white'  style='text-align:left'>");
		sbHTML.append("<a href='#' onClick=\"getFile('");
		sbHTML.append(obj.getItemPicture1());
		sbHTML.append("');\">");
		sbHTML.append(Common.checkGet(arrFileName[1]));
		sbHTML.append("</a></td>\n");
		sbHTML.append("</tr>\n");		
		//Common.RemoveDirectory(new File(filestoreLocation+File.separator+Common.checkGet(arrFileName[1]));
		
	}
}
if (!"".equals(obj.getItemPicture2())) {
	arrFileName=obj.getItemPicture2().split(":;:");
	if (arrFileName.length>1) {
		sbHTML.append("<tr>\n");
		sbHTML.append("<td bgcolor='E2F0CF' class='td_form'>附件二：</td>");
		sbHTML.append("<td bgcolor='F6FCE9' class='td_form_white'  style='text-align:left'>");
		sbHTML.append("<a href='#' onClick=\"getFile('");
		sbHTML.append(obj.getItemPicture2());
		sbHTML.append("');\">");
		sbHTML.append(Common.checkGet(arrFileName[1]));
		sbHTML.append("</a></td>\n");
		sbHTML.append("</tr>\n");		
		//Common.RemoveDirectory(new File(filestoreLocation+File.separator+Common.checkGet(arrFileName[1]));
		
	}	
	//if (arrFileName.length>1) Common.RemoveDirectory(new File(filestoreLocation+File.separator+Common.checkGet(arrFileName[1]));
}	
if (!"".equals(obj.getItemPicture3())) {
	arrFileName=obj.getItemPicture3().split(":;:");
	if (arrFileName.length>1) {
		sbHTML.append("<tr>\n");
		sbHTML.append("<td bgcolor='E2F0CF' class='td_form'>附件三：</td>");
		sbHTML.append("<td bgcolor='F6FCE9' class='td_form_white'  style='text-align:left'>");
		sbHTML.append("<a href='#' onClick=\"getFile('");
		sbHTML.append(obj.getItemPicture3());
		sbHTML.append("');\">");
		sbHTML.append(Common.checkGet(arrFileName[1]));
		sbHTML.append("</a></td>\n");
		sbHTML.append("</tr>\n");		
	}
}	
if (!"".equals(obj.getItemPicture4())) {
	arrFileName=obj.getItemPicture4().split(":;:");
	if (arrFileName.length>1) {
		sbHTML.append("<tr>\n");
		sbHTML.append("<td bgcolor='E2F0CF' class='td_form'>附件四：</td>");
		sbHTML.append("<td bgcolor='F6FCE9' class='td_form_white'  style='text-align:left'>");
		sbHTML.append("<a href='#' onClick=\"getFile('");
		sbHTML.append(obj.getItemPicture4());
		sbHTML.append("');\">");
		sbHTML.append(Common.checkGet(arrFileName[1]));
		sbHTML.append("</a></td>\n");
		sbHTML.append("</tr>\n");		
	}	
}	

if (!"".equals(obj.getItemPicture4())) {
	arrFileName=obj.getItemPicture5().split(":;:");
	if (arrFileName.length>1) {
		sbHTML.append("<tr>\n");
		sbHTML.append("<td bgcolor='E2F0CF' class='td_form'>附件五：</td>");
		sbHTML.append("<td bgcolor='F6FCE9' class='td_form_white'  style='text-align:left'>");
		sbHTML.append("<a href='#' onClick=\"getFile('");
		sbHTML.append(obj.getItemPicture5());
		sbHTML.append("');\">");
		sbHTML.append(Common.checkGet(arrFileName[1]));
		sbHTML.append("</a></td>\n");
		sbHTML.append("</tr>\n");		
	}
}

if ("checked".equals(obj.getIsHTML())) isHTML = "Y";
%>
<html>
<head>
<title>系統公告</title>
<meta http-equiv="Content-Language" content="zh-tw"/>
<meta http-equiv="Content-Type"   content="text/html; charset=UTF-8"/>
<meta http-equiv="Expires" content="-1"/>
<meta http-equiv="pragma" content="no-cache"/>
<meta http-equiv="Cache-Control" content="private"/>
<link rel="stylesheet" href="js/default.css" type="text/css">
<script language="javascript" src="js/validate.js"></script>
<script language="javascript" src="js/function.js"></script>
<script language="javascript">
function getFile(fileID){

	if (fileID.length>0) {
		var arrFileName = fileID.split(":;:");
		
		if (arrFileName.length>1) {
		    var prop='';
		    prop=prop+'toolbar=no;location=no,directories=no,menubar=no,status=yes,scrollbars=yes,resizable=yes,';
		    prop=prop+'width=400,';
		    prop=prop+'height=400';
		    var url = "downloadFileSimple?fileID=" + fileID; 
		 
		    window.open(url,'下傳檔案',prop);
		} else {
			alert("無法下載該檔案，因為檔案資訊不完整，若問題持續，請洽系統管理者!");
		}		
	} else {
		alert("目前沒有任何檔案可供下載!");	
	}
}
</script>
<style type="text/css">
</style>
</head>
<body>
<center>

<table class="table_form" width="100%">
	<tr>
		<td  colspan="2" class="td_form" style="background:blue;color:white;text-align:left">【系統公告】</td>
	</tr>
	<tr>		
		<td class="td_form" width="20%" >&nbsp;公告主旨：</td>
		<td class="td_form_white" style="text-align:left"><%=obj.getNewsSubject() %></td>
	</tr>	
	<tr>		
		<td class="td_form">&nbsp;公告期限：</td>
		<td class="td_form_white"  style="text-align:left">
			<%=util.Common.MaskDate(obj.getNewsDateS()) + " ~ " + util.Common.MaskDate(obj.getNewsDateE())%></td>
	</tr>		
	<tr>		
		<td class="td_form" >&nbsp;公告內容：</td>
		<td valign="top" class="td_form_white"  style="text-align:left" height="150"><%=util.Common.FormatStr(obj.getNewsContent(), isHTML) %>
</td>
	</tr>	
	<%=sbHTML.toString()%>
	<tr>	
	  <td class="td_form" nowrap>機關/單位/人員/日期：</td>
	  <td colspan="3" class="td_form_white">[
	    <input class="field_RO" type="hidden" name="organID" size="10" value="<%=obj.getOrganID()%>">
	    <input class="field_RO" type="text" name="organName" size="10" value="<%=obj.getOrganName()%>">
	    /
	    <input class="field_RO" type="hidden" name="unitID" size="10" value="<%=obj.getUnitID()%>">
	    <input class="field_RO" type="text" name="unitName" size="10" value="<%=obj.getUnitName()%>">
	    /
	    <input class="field_RO" type="hidden" name="editID" size="10" value="<%=obj.getEditID()%>">
	    <input class="field_RO" type="text" name="editIDName" size="10" value="<%=obj.getEditIDName()%>">
	    /
        <input class="field_RO" type="text" name="editDate" size="7" value="<%=obj.getEditDate()%>">
        ] </td>
	  </tr>	
	<tr >
		<td class="td_form_white" colspan="2" style="text-align:center">
			<input class="toolbar_default" type="button" name=btnClose value="關閉視窗" onClick="window.close();">		</td>
	</tr>	
</table>
</center>

</body>
</html>

