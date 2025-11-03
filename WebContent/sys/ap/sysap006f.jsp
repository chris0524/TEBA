<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../home/head.jsp" %>
<%

	String btnSubmit = util.Common.checkGet(request.getParameter("btnSubmit"));
	String btnCancel = util.Common.checkGet(request.getParameter("btnCancel"));

	if (!"".equals(btnSubmit)||!"".equals(btnCancel)) {
		util.MsgUtil.setMsg_section1(util.Common.checkGet(request.getParameter("msg_section1")));
		util.MsgUtil.setMsg_section2(util.Common.checkGet(request.getParameter("msg_section2")));
	}
	
%>

<html>
<head>
<meta http-equiv="Content-Language" content="zh-tw"/>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
<meta http-equiv="pragma" content="no-cache"/>
<meta http-equiv="Cache-control" content="no-cache"/>
<link rel="stylesheet" href="../../js/default.css" type="text/css">
<script language="javascript" src="../../js/validate.js"></script>
<script language="javascript" src="../../js/function.js"></script>
<script language="javascript" src="../../js/tablesoft.js"></script>
<script language="javascript">
var insertDefault;  //二維陣列, 新增時, 設定預設值
function checkField(){
    var alertStr=checkEmpty(form1.msg,"廣播訊息");
    if(alertStr.length!=0){ alert(alertStr); return false; }
}

function checkTotalWords(columnName){
	var columnValue = document.getElementById(columnName).value;
	if(columnValue.length != 0 && columnValue.length != 39){
		var space_count = 39-columnValue.length;
		var space = "";
		for(var i=1; i<=space_count ; i++){
			space = space+"　";
		}
		document.getElementById(columnName).value = columnValue+space;
	}
}

function ex1(){
	form1.msg_section1.value="【各位使用者　您們好】本系統預定於 xx：xx → xx：xx 進行系統維護！";
	form1.msg_section2.value="不便之處，敬請見諒。";
	count_together();
}

function count_together(){
	words_count('msg_section1','words_1');
	words_count('msg_section2','words_2');
}


word_count=0;
function words_count(columnName,count){
	var column_len = document.getElementById(columnName).value.length; 
	var words = document.getElementById(count); 
	word_count = column_len;
	words.value=39-word_count;
}

</script>
</head>
<body topmargin="0" onload="count_together();">
<br>
<form id="form1" name="form1" method="post">
<table class="bg" width="68%" cellspacing="0" cellpadding="0" align="center">
<!--Form區============================================================-->
<tr><td class="bg">

    <table class="queryTable" border="1">
    <tr>
    	<td class="td_form" colspan="4" style="text-align:center"><font color="red">請 輸 入 要 廣 播 的 訊 息</font></td>
    </tr>
    <tr>
    	<td class="queryTDLable" style="text-align:center"  width="15%">範　　例</td>
    	<td class="queryTDInput" align="center">                      
    	&nbsp;<input id="P1" class="field_Q" type="radio" name="radio1" value="1" onclick="ex1();"></br>
        &nbsp;<textarea name="msg" cols="60" class="field" readonly="readonly">
【各位使用者您們好】本系統預定於XX：XX→XX：XX進行系統維護！
不便之處，敬請見諒。</textarea>
    	</td>
    </tr>
    <tr>
        <td class="queryTDLable" style="text-align:center"  width="15%">顯示內容</br>
    	<td class="queryTDInput" align="center">
           &nbsp;<input class="field_Q" type="text" name="msg_section1" id="msg_section1" size="61" maxlength="39" value="<%=util.MsgUtil.getMsg_section1()%>" onkeyup="words_count(this.name,'words_1');">
           &nbsp;剩&nbsp;<input class="field_RO" type="text" name="words_1" size="1" style="text-align:center;color:#FF0000;" value="">&nbsp;字
           &nbsp;<input class="field_Q" type="text" name="msg_section2" id="msg_section2" size="61" maxlength="39" value="<%=util.MsgUtil.getMsg_section2()%>" onkeyup="words_count(this.name,'words_2');">
           &nbsp;剩&nbsp;<input class="field_RO" type="text" name="words_2" size="1" style="text-align:center;color:#FF0000;" value="">&nbsp;字
        </td>
    </tr>
    <tr>
        <td class="queryTDInput" colspan="4" style="text-align:center;">
            <input class="toolbar_default" followPK="false" type="submit"  name="btnSubmit"  value="送　　　　出" onclick="checkTotalWords('msg_section1');">&nbsp;|&nbsp;
            <input class="toolbar_default" followPK="false" type="button"  name="btnClean"   value="清空顯示內容" onclick="form1.msg_section1.value='';form1.msg_section2.value='';count_together();">&nbsp;|&nbsp;  
            <input class="toolbar_default" followPK="false" type="submit"  name="btnCancel"  value="清  除　廣  播" onclick="form1.msg_section1.value='';form1.msg_section2.value='';">        
        </td>
    </tr>
     <tr>
        <td class="td_form" colspan="4" style="text-align:center"><font color="red">每段最多可輸入字數：3 9 字（含空格）</font></td>
    </tr>
    </table>

</td></tr>
</table>
</form>
</body>
</html>
