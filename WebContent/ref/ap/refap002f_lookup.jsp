<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../home/head.jsp" %>
<html>
<head>
<meta http-equiv="Content-Language" content="zh-tw"/>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
<meta http-equiv="Expires" content="-1"/>
<meta http-equiv="pragma" content="no-cache"/>
<meta http-equiv="Cache-control" content="no-cache"/>
<head>
<script type="text/javascript">
function selectedIndexList() {
	var strRelative;
	var j = 0;
	for (var i = 0; i < document.relative_list.sList.length; i++) {
		if (document.relative_list.sList.options[i].selected==true) {
			if (j==0) {
				strRelative = document.relative_list.sList.options[i].value;
				j++;
			} else {			
				strRelative += '、' + document.relative_list.sList.options[i].value;		
			}
		}
   	}
	opener.document.forms[0].councilman.value = strRelative;
	window.close();
}


function initIndexList()
{
	var oStr = opener.document.forms[0].councilman.value;
	var arrStr = oStr.split('、');
	for (var j=0; j<arrStr.length; j++) {
		for (var i = 0; i < document.relative_list.sList.length; i++) {
			if (document.relative_list.sList.options[i].value==arrStr[j]) {
				document.relative_list.sList.options[i].selected=true;
			}
	   	}	
	}
}
</script>
</head>

<body topmargin="0" onLoad="initIndexList();">
<form name="relative_list">
<table width="100%"  border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td><input type="button" name="btnSelectedList" value="選取" onClick="selectedIndexList();">　<input type='button' name='btnClose' value='關閉視窗' OnClick=Javascript:window.close()></td>
  </tr>
  <tr>
    <td>
    <select name="sList" size="18" multiple>
    	<%=util.View.getOption(" select councilmanName as id, councilmanName from REF_MAN order by seqID","") %>
		<option value="...">．．．．．．．．．．．．．．．．</option>
    </select>
</td>  </tr>
</table>
<p>&nbsp;</p></form>
