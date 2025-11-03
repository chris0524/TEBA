<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
<title></title>
<link rel="stylesheet" href="../js/default.css" type="text/css">
<script language="javascript" src="../js/foldernav.js"></script>
<style>
.bg { 
  background-color: #FBFFFD;
  border: 1px solid #EDEDED;
  color: #000000;
  cursor: default;
  left: 0px;
  margin: 1px;
  padding: 2px 6px 0px 6px;
  top: 0px;
  width:175px;
  height:400px;
 }
</style>
<script type="text/javascript">
function label3(parentName,objthis){
	var label1Name=document.all.item(parentName.substring(0,2)+"a").innerHTML;
	var label2Name=document.all.item(parentName.substring(0,3)+"a").innerHTML;
	var selfName=objthis.innerHTML;		
	top.fbody.mainhead.document.all.item("pathname").innerHTML =label1Name +
		"&nbsp;&nbsp;&nbsp;&nbsp;>>&nbsp;&nbsp;&nbsp;&nbsp;" + label2Name +
		"&nbsp;&nbsp;&nbsp;&nbsp;>>&nbsp;&nbsp;&nbsp;&nbsp;" + selfName ;
}
function label2(parentName,objthis){
	var label1Name=document.all.item(parentName.substring(0,2)+"a").innerHTML;
	var selfName=objthis.innerHTML;		
	top.fbody.mainhead.document.all.item("pathname").innerHTML =label1Name +
		"&nbsp;&nbsp;&nbsp;&nbsp;>>&nbsp;&nbsp;&nbsp;&nbsp;" + selfName ;
}
</script>
</head>

<body class="background3" topmargin="0" leftmargin="0" >


<table border="0" width="100%" cellspacing="0" cellpadding="0">
<tr><td valign="top" style="padding-top:5px;">

<div ID="mParent" class="parent" style="width:400px;">
<a id="ma" href="#" onClick="label3('m11');FolderExpand('m','top')">
<img name="mTree" src="../image/menu/topopen.gif" class="icon" align="absmiddle" border="0"><font color="black" class="item">功能選單</font></a>
</div>

<div ID="mChild">

<!--第01子系統-->
<div ID="m1Parent" class="parent"><a class="item" href="#" onClick="FolderExpand('m1')"><img name="m1Tree" src="../image/menu/Tplus.gif" align="absmiddle" border="0"></a><img name="m1Folder" src="../image/menu/foldericon.gif" class="icon" align="absmiddle" border="0"><a id="m1a" class="item" color="black" href="#" onClick="FolderExpand('m1')">單位財產系統</a></div>
<div ID="m1Child" CLASS="child">
	<div ID="m11Parent" class="parent"><img src="../image/menu/I.gif" align="absmiddle" border="0"><a class="item" href="#" onClick="FolderExpand('m11')"><img name="m11Tree" src="../image/menu/Tplus.gif" align="absmiddle" border="0"></a><img name="m11Folder" src="../image/menu/foldericon.gif" class="icon" align="absmiddle" border="0"><a id="m11a" class="item" color="black" href="#" onClick="FolderExpand('m11')">土地產籍管理作業</a></div>
	<div ID="m11Child" CLASS="child">
		<img src="../image/menu/I.gif" align="absmiddle"><img src="../image/menu/I.gif" align="absmiddle"><img src="../image/menu/T.gif" align="absmiddle"><img src="../image/menu/htmlicon.gif" align="absmiddle" class="icon"><a class="item" href="../unt/la/untla001f.jsp" onclick="label3('m11',this);" target="main">土地產籍管理作業</a><br>
		<img src="../image/menu/I.gif" align="absmiddle"><img src="../image/menu/I.gif" align="absmiddle"><img src="../image/menu/L.gif" align="absmiddle"><img src="../image/menu/htmlicon.gif" align="absmiddle" class="icon"><a class="item" href="../unt/la/untla001f.jsp" onclick="label3('m11',this);" target="main">XXXX管理作業</a><br>
	</div>
	<div ID="m12Parent" class="parent"><img src="../image/menu/I.gif" align="absmiddle" border="0"><a class="item" href="#" onClick="FolderExpand('m12')"><img name="m12Tree" src="../image/menu/Tplus.gif" align="absmiddle" border="0"></a><img name="m12Folder" src="../image/menu/foldericon.gif" class="icon" align="absmiddle" border="0"><a id="m12a" class="item" color="black" href="#" onClick="FolderExpand('m12')" >建物產籍管理作業</a></div>
	<div ID="m12Child" CLASS="child">
		<img src="../image/menu/I.gif" align="absmiddle"><img src="../image/menu/I.gif" align="absmiddle"><img src="../image/menu/T.gif" align="absmiddle"><img src="../image/menu/htmlicon.gif" align="absmiddle" class="icon"><a class="item" href="../sys/ca/sysca001f.jsp" onclick="label3('m12',this);" target="main">建物主檔維護作業建物主檔維護作業</a><br>		
		<img src="../image/menu/I.gif" align="absmiddle"><img src="../image/menu/I.gif" align="absmiddle"><img src="../image/menu/T.gif" align="absmiddle"><img src="../image/menu/htmlicon.gif" align="absmiddle" class="icon"><a class="item" href="../cri/sam001r.jsp" onclick="label3('m12',this);" target="main">sam001</a><br>		
		<img src="../image/menu/I.gif" align="absmiddle"><img src="../image/menu/I.gif" align="absmiddle"><img src="../image/menu/T.gif" align="absmiddle"><img src="../image/menu/htmlicon.gif" align="absmiddle" class="icon"><a class="item" href="../cri/sam002r.jsp" onclick="label3('m12',this);" target="main">sam002</a><br>		
		<img src="../image/menu/I.gif" align="absmiddle"><img src="../image/menu/I.gif" align="absmiddle"><img src="../image/menu/T.gif" align="absmiddle"><img src="../image/menu/htmlicon.gif" align="absmiddle" class="icon"><a class="item" href="../cri/sam003r.jsp" onclick="label3('m12',this);" target="main">sam003</a><br>		
		<img src="../image/menu/I.gif" align="absmiddle"><img src="../image/menu/I.gif" align="absmiddle"><img src="../image/menu/T.gif" align="absmiddle"><img src="../image/menu/htmlicon.gif" align="absmiddle" class="icon"><a class="item" href="../cri/cri510r.jsp" onclick="label3('m12',this);" target="main">個案基本資料表</a><br>		
		<img src="../image/menu/I.gif" align="absmiddle"><img src="../image/menu/I.gif" align="absmiddle"><img src="../image/menu/T.gif" align="absmiddle"><img src="../image/menu/htmlicon.gif" align="absmiddle" class="icon"><a class="item" href="../cri/cri513r.jsp" onclick="label3('m12',this);" target="main">服務概況月報表</a><br>
		<img src="../image/menu/I.gif" align="absmiddle"><img src="../image/menu/I.gif" align="absmiddle"><img src="../image/menu/T.gif" align="absmiddle"><img src="../image/menu/htmlicon.gif" align="absmiddle" class="icon"><a class="item" href="../cri/cri514r.jsp" onclick="label3('m12',this);" target="main">申請概況月報表</a><br>
		<img src="../image/menu/I.gif" align="absmiddle"><img src="../image/menu/I.gif" align="absmiddle"><img src="../image/menu/T.gif" align="absmiddle"><img src="../image/menu/htmlicon.gif" align="absmiddle" class="icon"><a class="item" href="../cri/cri512r.jsp" onclick="label3('m12',this);" target="main">服務費印領清冊</a><br>
		<img src="../image/menu/I.gif" align="absmiddle"><img src="../image/menu/I.gif" align="absmiddle"><img src="../image/menu/T.gif" align="absmiddle"><img src="../image/menu/htmlicon.gif" align="absmiddle" class="icon"><a class="item" href="../cri/cri515r.jsp" onclick="label3('m12',this);" target="main">費用年度統計表</a><br>		
		<img src="../image/menu/I.gif" align="absmiddle"><img src="../image/menu/I.gif" align="absmiddle"><img src="../image/menu/T.gif" align="absmiddle"><img src="../image/menu/htmlicon.gif" align="absmiddle" class="icon"><a class="item" href="../cri/cri517r.jsp" onclick="label3('m12',this);" target="main">個案年度統計表</a><br>
		<img src="../image/menu/I.gif" align="absmiddle"><img src="../image/menu/I.gif" align="absmiddle"><img src="../image/menu/T.gif" align="absmiddle"><img src="../image/menu/htmlicon.gif" align="absmiddle" class="icon"><a class="item" href="../cri/cri511r.jsp" onclick="label3('m12',this);" target="main">保育員扣繳證明清冊</a><br>
		<img src="../image/menu/I.gif" align="absmiddle"><img src="../image/menu/I.gif" align="absmiddle"><img src="../image/menu/L.gif" align="absmiddle"><img src="../image/menu/htmlicon.gif" align="absmiddle" class="icon"><a class="item" href="../cri/cri516r.jsp" onclick="label3('m12',this);" target="main">保育員年度統計表<br>
	</div>	
	<div ID="m13Parent" class="parent"><img src="../image/menu/I.gif" align="absmiddle" border="0"><a class="item" href="#" onClick="FolderExpand('m13','last')"><img name="m13Tree" src="../image/menu/Lplus.gif" align="absmiddle" border="0"></a><img name="m13Folder" src="../image/menu/foldericon.gif" class="icon" align="absmiddle" border="0"><a id="m13a" class="item" color="black"  href="#" onClick="FolderExpand('m13','last')">臨托暨短托決策支援</a></div>
	<div ID="m13Child" CLASS="child">
		<img src="../image/menu/I.gif" align="absmiddle"><img src="../image/menu/white.gif" align="absmiddle"><img src="../image/menu/T.gif" align="absmiddle"><img src="../image/menu/htmlicon.gif" align="absmiddle" class="icon"><a class="item" href="../cri/cri521.jsp" onclick="label3('m13',this);" target="main">個案分析</a><br>
		<img src="../image/menu/I.gif" align="absmiddle"><img src="../image/menu/white.gif" align="absmiddle"><img src="../image/menu/T.gif" align="absmiddle"><img src="../image/menu/htmlicon.gif" align="absmiddle" class="icon"><a class="item" href="../cri/cri522.jsp" onclick="label3('m13',this);" target="main">個案申請時段統計</a><br>	
		<img src="../image/menu/I.gif" align="absmiddle"><img src="../image/menu/white.gif" align="absmiddle"><img src="../image/menu/T.gif" align="absmiddle"><img src="../image/menu/htmlicon.gif" align="absmiddle" class="icon"><a class="item" href="../cri/cri523.jsp" onclick="label3('m13',this);" target="main">服務項目分析</a><br>
		<img src="../image/menu/I.gif" align="absmiddle"><img src="../image/menu/white.gif" align="absmiddle"><img src="../image/menu/L.gif" align="absmiddle"><img src="../image/menu/htmlicon.gif" align="absmiddle" class="icon"><a class="item" href="../cri/cri524.jsp" onclick="label3('m13',this);" target="main">保育員分析</a><br>	
	
	</div>
</div>	



<!--第09子系統-->
<div ID="m9Parent" class="parent"><a class="item" href="#" onClick="FolderExpand('m9','last')"><img name="m9Tree" src="../image/menu/Lplus.gif" align="absmiddle" border="0"></a><img name="m9Folder" src="../image/menu/foldericon.gif" class="icon" align="absmiddle" border="0"><a id="m9a" class="item" color="black" href="#" onClick="FolderExpand('m9','last')">系統管理</a></div>
<div ID="m9Child" CLASS="child">
	<img src="../image/menu/white.gif" align="absmiddle"><img src="../image/menu/T.gif" align="absmiddle"><img src="../image/menu/htmlicon.gif" align="absmiddle" class="icon"><a class="item" href="../sys/ca/sysca001f.jsp"  onclick="label2('m9',this);" target="main">代碼管理作業</a><br>
	<img src="../image/menu/white.gif" align="absmiddle"><img src="../image/menu/T.gif" align="absmiddle"><img src="../image/menu/htmlicon.gif" align="absmiddle" class="icon"><a class="item" href="../sys/ca/sysca002f.jsp"  onclick="label2('m9',this);" target="main">機關管理作業</a><br>
	<img src="../image/menu/white.gif" align="absmiddle"><img src="../image/menu/T.gif" align="absmiddle"><img src="../image/menu/htmlicon.gif" align="absmiddle" class="icon"><a class="item" href="../sys/pk/syspk007f.jsp"  onclick="label2('m9',this);" target="main">財產編號維護</a><br>
	<img src="../image/menu/white.gif" align="absmiddle"><img src="../image/menu/T.gif" align="absmiddle"><img src="../image/menu/htmlicon.gif" align="absmiddle" class="icon"><a class="item" href="../sys/pk/syspk001f.jsp"  onclick="label2('m9',this);" target="main">行政院函知案件管理</a><br>
	<img src="../image/menu/white.gif" align="absmiddle"><img src="../image/menu/T.gif" align="absmiddle"><img src="../image/menu/htmlicon.gif" align="absmiddle" class="icon"><a class="item" href="../sys/sys103f.jsp"  onclick="label2('m9',this);" target="main">國定假日管理</a><br>
	<img src="../image/menu/white.gif" align="absmiddle"><img src="../image/menu/T.gif" align="absmiddle"><img src="../image/menu/htmlicon.gif" align="absmiddle" class="icon"><a class="item" href="../sys/ap/sysap001f.jsp"  onclick="label3('m9',this);" target="main">使用者資料權限管理</a><br>
	<img src="../image/menu/white.gif" align="absmiddle"><img src="../image/menu/L.gif" align="absmiddle"><img src="../image/menu/htmlicon.gif" align="absmiddle" class="icon"><a class="item" href="../sys/ap/sysap002f.jsp"  onclick="label3('m9',this);" target="main">群組管理</a><br>
</div>

</td></tr>
</table>
</body>
</html>
