<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<jsp:useBean id="user" scope="session" class="util.User"/>
<%@ page import="util.*"%>
<%
	String showMag="";
	if("383030000D".equals(user.getOrganID())){
		
	}
%>
<html>
<head>
<meta http-equiv="Content-Language" content="zh-tw"/>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
<meta http-equiv="pragma" content="no-cache"/>
<meta http-equiv="Cache-control" content="no-cache"/>
<link rel="stylesheet" href="../js/default.css" type="text/css">
<style>
body {
	margin-top: 0px;
	margin-left: 0px;
	margin-right: 0px;
	margin-bottom: 0px;
}

div.titleBar {
	font-family: "新細明體"; 
	font-size: 12px;
	background-color: #EDEDED;
	border-top:1px solid #000000;
	border-bottom:1px solid #000000;
	padding: 0px 12px 0px 12px;
	text-align: left;
	vertical-align: top;
}

a.titleBarButton {
	font-family: transparent; 
	font-size: transparent;
	background-color: transparent;
	border: 1px solid #EDEDED;
	color: #000000;
	padding: 2px 6px 0px 6px;
	text-decoration: none;
}

a.titleBarButton:hover {
	font-family: transparent; 
	font-size: transparent;
	background-color: transparent;
	border: 1px outset #EDEDED;
	color: #000000;
	padding: 2px 6px 0px 6px;
	text-decoration: none;
}

div#topbar{
	position:absolute;
	border: 1px solid black;
	padding: 2px;
	background-color: lightyellow;
	width: 550px;
	height: 85px;
	visibility: hidden;
	z-index: 100;
	overflow:auto;
}
#Layer1 {
	position:absolute;
	width:71px;
	height:34px;
	z-index:101;
	left: 846px;
	top: 13px;
}
</style>
<script type="text/javascript" src="../js/popup.js"></script>
<script type="text/javascript" src="../js/function.js"></script>
<script type="text/javascript">
var boxMsgFlag = false;
var broadcastMsg = "";

function isObj(obj){
	return (!((obj==null)||(obj==undefined)));
}

function titleBarButtonClick(thisobj){
	for(var i=0; i<document.links.length; i++){  
		obj = document.links[i];
		if (obj.className=="titleBarButton"){
			obj.style.background="#EDEDED";
			obj.style.color="#000000";
		}
	}
	thisobj.style.background="blue";	
	thisobj.style.color="#FFFFFF";
}

function openKMS() {
	moshe=window.open('','body');
	moshe.document.write('<html>');
	moshe.document.write('<head>');
	moshe.document.write('<meta http-equiv=Content-Type content=text/html; charset=UTF-8>');			
	moshe.document.write('<title>.:: 登入系統 ::.</title>');
	moshe.document.write('</head>');
	moshe.document.write('<body topmargin="0" leftmargin="0" marginwidth="0" marginheight="0">\n');	
	moshe.document.write("<form name=loginForm action='http://128.1.1.18/SmartKMS/do/login' method='post'>"); 
	moshe.document.write("<input type='hidden' name='uid' value='<%=user.getUserID()%>'>");    
	moshe.document.write("<input type='hidden' name='pw' value='<%=user.getPassword()%>'>");    
	moshe.document.write("<\/form>");
	moshe.document.write("<\/body><\/html>");
	moshe.loginForm.submit();
	//moshe.close();
}

function openPersonalWindow(){
	var actItem = "b1";
	for(var i=0; i<document.links.length; i++){  
		var obj = document.links[i];
		if (obj.className=="titleBarButton" && obj.style.background=="blue"){
			actItem = obj.id;
		}
	}	
	var prop='';
	prop=prop+'toolbar=no;location=no,directories=no,menubar=no,status=no,scrollbars=yes,resizable=yes,';
	prop=prop+'width=750,';
	prop=prop+'height=390';
	window.open('../sys/ap/sysap001f_s.jsp?actItem='+actItem,'個人基本資料維護',prop);
}

function sessionTimeout() {
	if ("<%=user.getUserID()%>"=="") {
		var prop='';
		prop='scroll:yes;status:no;help:no;dialogWidth:300px;dialogHeight:120px';
		window.showModalDialog('sessionTimeout.jsp',window,prop);
			top.top.location.href='../index.jsp';
	} 
}

var persistclose=0; //set to 0 or 1. 1 means once the bar is manually closed, it will remain closed for browser session
var startX = 206; //set x offset of bar in pixels , 260 for TAJK default
var startY = 1; //set y offset of bar in pixels
var verticalpos="fromtop"; //enter "fromtop" or "frombottom"

function iecompattest(){
	return (document.compatMode && document.compatMode!="BackCompat")? document.documentElement : document.body;
}

function get_cookie(Name) {
	var search = Name + "=";
	var returnvalue = "";
	if (document.cookie.length > 0) {
		offset = document.cookie.indexOf(search);
		if (offset != -1) {
			offset += search.length;
			end = document.cookie.indexOf(";", offset);
			if (end == -1) end = document.cookie.length;
			returnvalue=unescape(document.cookie.substring(offset, end));
		}
	}
	return returnvalue;
}

function closebar(){
	if (persistclose)
	document.cookie="remainclosed=1";
	document.getElementById("topbar").style.visibility="hidden";
}

function staticbar(){
	barheight=document.getElementById("topbar").offsetHeight;
	var ns = (navigator.appName.indexOf("Netscape") != -1) || window.opera;
	var d = document;
	function ml(id){
		var el=d.getElementById(id);
		if (!persistclose || persistclose && get_cookie("remainclosed")=="")
		el.style.visibility="visible";
		if(d.layers)el.style=el;
		el.sP=function(x,y){this.style.left=x+"px";this.style.top=y+"px";};
		el.x = startX;
		if (verticalpos=="fromtop")
			el.y = startY;
		else{
			el.y = ns ? pageYOffset + innerHeight : iecompattest().scrollTop + iecompattest().clientHeight;
			el.y -= startY;
		}
		return el;
	}
	window.stayTopLeft=function(){
		if (verticalpos=="fromtop"){
			var pY = ns ? pageYOffset : iecompattest().scrollTop;
			ftlObj.y += (pY + startY - ftlObj.y)/8;
		}else{
			var pY = ns ? pageYOffset + innerHeight - barheight: iecompattest().scrollTop + iecompattest().clientHeight - barheight;
			ftlObj.y += (pY - startY - ftlObj.y)/8;
		}
		ftlObj.sP(ftlObj.x, ftlObj.y);
		setTimeout("stayTopLeft()", 10);
	};
	ftlObj = ml("topbar");
	stayTopLeft();
}

function showFloatingTopBar(floatingField) {
	//floatingField = "財產編號:propertyNo;分號:serialNo:;:土地標示:signNo1,signNo2,signNo3,signNo4,signNo5:;:";
	var strHTML = "";
	var sFlag = false;
	var i=0;
	if (floatingField!=null && floatingField!="") {
		var arrField = floatingField.split(":;:");
		for (i=0; i<arrField.length; i++) {
			var arrSubField = arrField[i].split(";");
			for (var j=0; j<arrSubField.length; j++) {
				var arrFieldName = arrSubField[j].split(":");
				if (arrFieldName.length>1) {
					if (arrFieldName[0].length>0) strHTML += arrFieldName[0]+":";
					if (arrFieldName[1].length>0) {
						var arrFieldValue = arrFieldName[1].split(",");
						for (var k=0; k<arrFieldValue.length; k++) {
					 		if (isObj(top.fbody.main.document.all.item(arrFieldValue[k]))) {
					 			sFlag = true;
								//obj = document.all.item(arrFieldValue[k]);
								obj = top.fbody.main.document.all.item(arrFieldValue[k]);
								if((obj.type=="text")||(obj.type=="textarea")||(obj.type=="hidden")) {
									if (obj.name=="signNo5") strHTML += "-" + obj.value;
									else strHTML += obj.value;
								}else if((obj.type=="select")||(obj.type=="select-one")){
									if (obj.value.length>0) {
										for (var e = 0; e < obj.length; e++) {      
									   		if (obj.options[e].selected == true) {         
									   			strHTML += obj.options[e].text;
									   		}
										}
										//if (obj.value.length>0) strHTML += obj.text;								
									}
								}
							}
						}
					}
				}
				strHTML += " ";
			}
			if (i!=(arrField.length-1))	strHTML += "<br>\n";
		}
		if (sFlag) {
			var strClose = '<a href="" onClick="closebar(); return false"><img src="../image/close.gif" border="0" /></a>';
			//document.getElementById("topbar").innerHTML = inHTML+strHTML;
			var inHTML = '<table width="100%" border="0" cellpadding="0" cellspacing="0"><tr><td valign="top" width="100%">'+strHTML+'</td>';
			inHTML += '<td valign="top" align="right">'+strClose+'</td></tr></table>';
			document.getElementById("topbar").innerHTML = inHTML;			
			closebar();
			staticbar();
		}
	}
}


function showBoxMsg(msg,b) { 
	//new popUp(760, 1, 200, 70, "boxMsg", msg, "white", "black", "10pt 細明體", "即時訊息", "#C8E8D9", "black", "#EFF9FF", "gray", "#EFF9FF", true, true, true, true, true);

	if(msg !=null && msg.length!=0){ 
	 
		if(isObj(b)){
			new popUp(10 , 1 , 684 , 90 , "boxMsg_BroadCastMsg" , msg, "white" , "black" , "16pt 新細明體" , "重要通知，請大家告訴大家" , "#C8E8D9" , "black" , "#EFF9FF", "#EFF9FF" , "black" , true , false , false , true , false , false , '../image/min.gif' , '../image/max.gif' , '../image/close.gif' , '../image/resize.gif');
		}
		
		if (boxMsgFlag) {			
			//fadeboxout("boxMsg");
			changecontent("boxMsg",msg);			
			fadeboxin("boxMsg");
		} else {
			if(!isObj(b)){
				//fadeboxout("boxMsg");
				new popUp(790 , 1 , 200 , 70 , "boxMsg" , msg, "white" , "black" , "10pt 新細明體" , "即時訊息" , "#C8E8D9" , "black" , "#EFF9FF", "#EFF9FF" , "black" , true , true , true , true , false , false , '../image/min.gif' , '../image/max.gif' , '../image/close.gif' , '../image/resize.gif');
				boxMsgFlag = true;
			}
		}
		
	} else {
		if (boxMsgFlag) { 
			//hidebox("boxMsg");
			fadeboxout("boxMsg");
		}
	}
}

function BroadCastMsg() { 
	var x = getRemoteData('msgBroadCast.jsp');	
	if (x!=null && x.length!=0) {
		if (x!=broadcastMsg) {
			showBoxMsg("<font color='red'>"+x+"</font>",true);
			broadcastMsg = x;
		} 
	}
	//window.setTimeout("BroadCastMsg()", 1000);
}
</script>

</head>
<body onLoad="sessionTimeout();BroadCastMsg();">
<form>
<table width="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td width="100%" background="../image/main01.gif"><table width="100%" height="70" border="0" cellpadding="0" cellspacing="0">
    <tr>
    <td valign="top" background="../image/top_02.jpg"><img src="../image/logo_B.jpg" alt="" align="top"></td>
    <td width="370" align="right" valign="top" background="../image/top_04.jpg"><span class="text_link_w" >[<%=user.getUserName()+" | "+user.getOrganName()%>]&nbsp;
			[<a class="text_link_w" href="logout.jsp" target="_top">登出</a>]</span>&nbsp;&nbsp;
		</td>
  </tr>
  </table>
      </td>
  </tr>
</table>
<div class="titleBar" style="width:100%;" >
	&nbsp;|&nbsp;
	<a id="b1" class="titleBarButton" target="body" href="body.jsp" onClick="titleBarButtonClick(this);" style="background:blue;color:white;">財&nbsp;產&nbsp;管&nbsp;理</a>&nbsp;|&nbsp;
	<a id="b5" class="titleBarButton" href="#" onClick="openPersonalWindow();titleBarButtonClick(this);">個人基本資料維護</a>&nbsp;|&nbsp;	
	&nbsp;<marquee onMouseOver="this.stop()" onMouseOut="this.start()" direction=left width="520" class="titleBarButton" scrollamount="3">
		<font style="FILTER: glow(color=mediumblue); WIDTH: 250%" color=#ffffff size="2"><%=showMag%></font>
	</marquee>
</div>
<div id="topbar">
</div>
			<input type="hidden" name="uid" value="<%=user.getUserID()%>">
			<input type="hidden" name="pwd" value="<%=user.getPassword()%>">
			<input type="hidden" name="oid" value="<%=user.getOrganID()%>">
			<input type="hidden" name="oname" value="<%=user.getOrganName()%>">
			<input type="hidden" name="isomgr" value="<%=user.getIsOrganManager()%>">
			<input type="hidden" name="ismgr" value="<%=user.getIsAdminManager()%>">

</form>
</body>
</html>
