<!--
*<br>程式目的：系統操作說明
*<br>程式代號：untgr034r
*<br>撰寫日期：112/12/29
*<br>程式作者：chris.lin
*<br>--------------------------------------------------------
*<br>修改作者　　修改日期　　　修改目的
*<br>--------------------------------------------------------
-->
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../home/head.jsp" %>

<% String downloadFilePath01 = application.getInitParameter("filestoreLocation") + "/不提列折舊設定說明.mp4"; %>
<% String downloadFilePath02 = application.getInitParameter("filestoreLocation") + "/公務類財產轉作業基金.mp4"; %>
<% String downloadFilePath03 = application.getInitParameter("filestoreLocation") + "/財產增加單維護.mp4"; %>
<% String downloadFilePath04 = application.getInitParameter("filestoreLocation") + "/財產移動單維護.mp4"; %>
<% String downloadFilePath05 = application.getInitParameter("filestoreLocation") + "/財產增減值單維護.mp4"; %>
<% String downloadFilePath06 = application.getInitParameter("filestoreLocation") + "/存置地點維護.mp4"; %>
<% String downloadFilePath07 = application.getInitParameter("filestoreLocation") + "/財產盤點維護.mp4"; %>

<% String downloadFilePath11 = application.getInitParameter("filestoreLocation") + "/不提列折舊設定流程說明.docx"; %>
<% String downloadFilePath12 = application.getInitParameter("filestoreLocation") + "/公務類財產轉作業基金流程操作說明.docx"; %>
<% String downloadFilePath13 = application.getInitParameter("filestoreLocation") + "/公告地價作業流程.docx"; %>
<% String downloadFilePath14 = application.getInitParameter("filestoreLocation") + "/地籍地段代碼異動清冊功能說明.docx"; %>
<% String downloadFilePath15 = application.getInitParameter("filestoreLocation") + "/物品編號正規化_功能異動說明.docx"; %>
<% String downloadFilePath16 = application.getInitParameter("filestoreLocation") + "/財產撥入功能優化操作說明.docx"; %>


<% String downloadFilePath21 = application.getInitParameter("filestoreLocation") + "/111年增修功能教育訓練簡報.pptx"; %>
<% String downloadFilePath22 = application.getInitParameter("filestoreLocation") + "/112年系統功能教育訓練簡報.pptx"; %>
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
<script language="javascript">
//var insertDefault;	//二維陣列, 新增時, 設定預設值

function btn01_event(){
	openDownloadWindow('','<%=downloadFilePath01%>');
}

function btn02_event(){
	openDownloadWindow('','<%=downloadFilePath02%>');
}

function btn03_event(){
	openDownloadWindow('','<%=downloadFilePath03%>');
}

function btn04_event(){
	openDownloadWindow('','<%=downloadFilePath04%>');
}

function btn05_event(){
	openDownloadWindow('','<%=downloadFilePath05%>');
}

function btn06_event(){
	openDownloadWindow('','<%=downloadFilePath06%>');
}

function btn07_event(){
	openDownloadWindow('','<%=downloadFilePath07%>');
}

function btn11_event(){
	openDownloadWindow('','<%=downloadFilePath11%>');
}

function btn12_event(){
	openDownloadWindow('','<%=downloadFilePath12%>');
}

function btn13_event(){
	openDownloadWindow('','<%=downloadFilePath13%>');
}

function btn14_event(){
	openDownloadWindow('','<%=downloadFilePath14%>');
}

function btn15_event(){
	openDownloadWindow('','<%=downloadFilePath15%>');
}

function btn16_event(){
	openDownloadWindow('','<%=downloadFilePath16%>');
}

function btn21_event(){
	openDownloadWindow('','<%=downloadFilePath21%>');
}

function btn22_event(){
	openDownloadWindow('','<%=downloadFilePath22%>');
}
</script>
</head>
<body topmargin="10">
<form id="form1" name="form1"  target="_blank">
<table class="bg" width="40%" cellspacing="0" cellpadding="0" align="center"><tr><td>
	<table class="queryTable"  border="1">
	<tr>
        <td class="td_form" colspan="2" style="text-align:center">影音教學檔</td>
	</tr>
	<tr>
        <td class="queryTDLable">不提列折舊說明</td>
        <td class="queryTDInput">
		<input class="toolbar_default" type="button" followPK="true" name="btn01" value="檔案下載" onclick="btn01_event();">&nbsp;
        </td>
    </tr>
	<tr>
        <td class="queryTDLable">公務類財產轉作業基金</td>
        <td class="queryTDInput">
		<input class="toolbar_default" type="button" followPK="true" name="btn02" value="檔案下載" onclick="btn02_event();">&nbsp;
        </td>
    </tr>
    <tr>
        <td class="queryTDLable">財產增加單維護說明</td>
        <td class="queryTDInput">
		<input class="toolbar_default" type="button" followPK="true" name="btn03" value="檔案下載" onclick="btn03_event();">&nbsp;
        </td>
    </tr>
    <tr>
        <td class="queryTDLable">財產移動單維護說明</td>
        <td class="queryTDInput">
		<input class="toolbar_default" type="button" followPK="true" name="btn04" value="檔案下載" onclick="btn04_event();">&nbsp;
        </td>
    </tr>
    <tr>
        <td class="queryTDLable">財產增減值單維護說明</td>
        <td class="queryTDInput">
		<input class="toolbar_default" type="button" followPK="true" name="btn05" value="檔案下載" onclick="btn05_event();">&nbsp;
        </td>
    </tr>
    <tr>
        <td class="queryTDLable">存置地點維護</td>
        <td class="queryTDInput">
		<input class="toolbar_default" type="button" followPK="true" name="btn06" value="檔案下載" onclick="btn06_event();">&nbsp;
        </td>
    </tr>
    <tr>
        <td class="queryTDLable">財產盤點維護說明</td>
        <td class="queryTDInput">
		<input class="toolbar_default" type="button" followPK="true" name="btn07" value="檔案下載" onclick="btn07_event();">&nbsp;
        </td>
    </tr>
    <tr>
        <td class="queryTDLable">系統維護說明</td>
        <td class="queryTDInput">
		<input class="toolbar_default" type="button" followPK="true" name="btn07" value="檔案下載" onclick="btn07_event();">&nbsp;
        </td>
    </tr>
	</table>
</td></tr></table>	

<table class="bg" width="40%" cellspacing="0" cellpadding="0" align="center"><tr><td>
	<table class="queryTable"  border="1">
	<tr>
        <td class="td_form" colspan="2" style="text-align:center">操作說明檔(WORD)</td>
	</tr>
	<tr>
        <td class="queryTDLable">不提列折舊說明</td>
        <td class="queryTDInput">
		<input class="toolbar_default" type="button" followPK="true" name="btn11" value="檔案下載" onclick="btn11_event();">&nbsp;
        </td>
    </tr>
	<tr>
        <td class="queryTDLable">公務類財產轉作業基金</td>
        <td class="queryTDInput">
		<input class="toolbar_default" type="button" followPK="true" name="btn12" value="檔案下載" onclick="btn12_event();">&nbsp;
        </td>
    </tr>
    <tr>
        <td class="queryTDLable">公告地價作業流程</td>
        <td class="queryTDInput">
		<input class="toolbar_default" type="button" followPK="true" name="btn13" value="檔案下載" onclick="btn13_event();">&nbsp;
        </td>
    </tr>
    <tr>
        <td class="queryTDLable">地籍地段代碼異動清冊功能說明</td>
        <td class="queryTDInput">
		<input class="toolbar_default" type="button" followPK="true" name="btn14" value="檔案下載" onclick="btn14_event();">&nbsp;
        </td>
    </tr>
    <tr>
        <td class="queryTDLable">物品編號正規化_功能異動說明</td>
        <td class="queryTDInput">
		<input class="toolbar_default" type="button" followPK="true" name="btn15" value="檔案下載" onclick="btn15_event();">&nbsp;
        </td>
    </tr>
    <tr>
        <td class="queryTDLable">財產撥入功能優化操作說明</td>
        <td class="queryTDInput">
		<input class="toolbar_default" type="button" followPK="true" name="btn16" value="檔案下載" onclick="btn16_event();">&nbsp;
        </td>
    </tr>
	</table>
</td></tr></table>

<table class="bg" width="40%" cellspacing="0" cellpadding="0" align="center"><tr><td>
	<table class="queryTable"  border="1">
	<tr>
        <td class="td_form" colspan="2" style="text-align:center">教育訓練簡報下載</td>
	</tr>
	<tr>
        <td class="queryTDLable">111年度教育訓練</td>
        <td class="queryTDInput">
		<input class="toolbar_default" type="button" followPK="true" name="btn21" value="檔案下載" onclick="btn21_event();">&nbsp;
        </td>
    </tr>
	<tr>
        <td class="queryTDLable">112年度教育訓練</td>
        <td class="queryTDInput">
		<input class="toolbar_default" type="button" followPK="true" name="btn22" value="檔案下載" onclick="btn22_event();">&nbsp;
        </td>
    </tr>
	</table>
</td></tr></table>	
</form>
</body>
</html>
