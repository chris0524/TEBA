<%@page import="soa.client.utils.SSOUtil, filter.SSOFilter"%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="util.User"%>
<%
String account = util.Common.get(request.getParameter("userID")).equals("")?"":util.Common.get(request.getParameter("userID")).replaceAll("'","").replaceAll("\"","");
String pwd = util.Common.get(request.getParameter("password")).equals("")?"":util.Common.get(request.getParameter("password")).replaceAll("'","").replaceAll("\"","");


User user = null;
if (!"".equals(account) && !"".equals(pwd) ){
	try{
		String token1 = SSOUtil.getToken1(account, pwd, request.getRemoteAddr());
		response.sendRedirect(
				String.format("getToken1.action?%s=%s&%s=%s&%s=%s"
						, SSOFilter.userIdNameFromEip, account
						, SSOFilter.token1NameFromEip, token1
						, SSOFilter.ipNameFromEip, request.getRemoteAddr()));
	}catch(Exception e){
		session.setAttribute("errMsg", e.getMessage());
		session.setAttribute("eipHomePage", SSOFilter.eipIndexPage);
		response.sendRedirect("/TCGH/home/ssoError.jsp");
	}		
}
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@page import="sys.ap.SYSAP003F_Permission"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Language" content="zh-tw" />
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<meta http-equiv="Expires" content="-1" />
<meta http-equiv="pragma" content="no-cache" />
<meta http-equiv="Cache-Control" content="private" />
<title>EIP資訊系統</title>
<link href="js/default.css" rel="stylesheet" type="text/css" />
<link href="js/style.css" rel="stylesheet" type="text/css" />
<script language="javascript" src="js/validate.js"></script>
<script language="javascript" src="js/function.js"></script>
<script language="javascript">
<!--
var popWin;
var popFlag = false;
function checkField(){
	if (document.all.item("userID").value==""){ 
		alert("請輸入使用者帳號");
		return false; 
	}	
	if (document.all.item("password").value==""){ 
		alert("請輸入使用者密碼");
		return false; 
	}
}

function popChgPWD(){
	window.open("chgPWD.jsp","","top=100,left=210,width=500,height=260,scrollbars=no");	
}

function popGetPWD(){
	window.open("getpwd.jsp","","top=100,left=210,width=320,height=240,scrollbars=no");	
}

function init() {
	//window.open('login.jsp','','','top=0,left=0,toolbar=no,location=no,status=no,menubar=no,scrollbars=no,fullscreen=yes');		
	form1.userID.focus();
}


function MM_swapImgRestore() { //v3.0
  var i,x,a=document.MM_sr; for(i=0;a&&i<a.length&&(x=a[i])&&x.oSrc;i++) x.src=x.oSrc;
}

function MM_preloadImages() { //v3.0
  var d=document; if(d.images){ if(!d.MM_p) d.MM_p=new Array();
    var i,j=d.MM_p.length,a=MM_preloadImages.arguments; for(i=0; i<a.length; i++)
    if (a[i].indexOf("#")!=0){ d.MM_p[j]=new Image; d.MM_p[j++].src=a[i];}}
}

function MM_findObj(n, d) { //v4.01
  var p,i,x;  if(!d) d=document; if((p=n.indexOf("?"))>0&&parent.frames.length) {
    d=parent.frames[n.substring(p+1)].document; n=n.substring(0,p);}
  if(!(x=d[n])&&d.all) x=d.all[n]; for (i=0;!x&&i<d.forms.length;i++) x=d.forms[i][n];
  for(i=0;!x&&d.layers&&i<d.layers.length;i++) x=MM_findObj(n,d.layers[i].document);
  if(!x && d.getElementById) x=d.getElementById(n); return x;
}

function MM_swapImage() { //v3.0
  var i,j=0,x,a=MM_swapImage.arguments; document.MM_sr=new Array; for(i=0;i<(a.length-2);i+=3)
   if ((x=MM_findObj(a[i]))!=null){document.MM_sr[j++]=x; if(!x.oSrc) x.oSrc=x.src; x.src=a[i+2];}
}
//-->
</script>
</head>

<body onLoad="MM_preloadImages('image/login_r4_c2_f2.jpg','image/login_r4_c6_f2.jpg','image/login_r5_c1_f2.jpg','image/login_r5_c3_f2.jpg','image/login_r5_c5_f2.jpg','image/login_r5_c7_f2.jpg','image/login_r5_c8_f2.jpg');init();">
<form method="post" action="" name="form1" onSubmit="return checkField()">
	<table width="43%" border="0" align="center" cellpadding="0" cellspacing="0" id="table1">
		<tr>
			<td><img name="login_r1_c1" src="image/banner_02.png" width="571" height="82" border="0" id="login_r1_c1" alt="" /></td>
		</tr>
		<tr>
			<td>
<table width="571" border="0" align="center" cellpadding="0" cellspacing="0" id="table5">
  <tr>
    <td rowspan="2" width="196">
	<img name="login_r2_c2" src="image/login_r2_c1.jpg" width="196" height="176" border="0" id="login_r2_c2" alt="" /></td>
    <td rowspan="2" valign="top" background="image/login_r2_c4.jpg">
	<table width="100%" border="0" cellspacing="0" cellpadding="0" id="table6">
      <tr>
        <td height="62">　</td>
      </tr>
      <tr>
        <td height="95">
		<div align="center">
			<table width="100%" border="0" cellpadding="0" cellspacing="0" id="table7">
          <tr>
            <td width="20" align="right" valign="middle">&nbsp;</td>
            <td width="45" align="center" valign="middle">帳號</td>
            <td align="left" valign="middle">
			<input type="text" class="field" tabindex="1" maxlength="20" size="15" name="userID" value="tcgh-test" /></td>
          </tr>
          <tr>
            <td width="20" align="right" valign="middle">&nbsp;</td>
            <td width="45" align="center" valign="middle">密碼</td>
            <td height="32" align="left" valign="middle">
			<input tabindex="2" class="field" type="password" maxlength="20" size="15" value="1q2w3e4r" name="password" /></td>
          </tr>
        </table>
		</div>
		</td>
      </tr>
      
    </table></td>
  </tr>
</table>
			</td>
		</tr>
		<tr>
			<td>
<table width="571" border="0" align="center" cellpadding="0" cellspacing="0" id="table8">
  <tr>
    <td>
	<img name="login_r4_c10" src="image/login_r4_c1.jpg" width="70" height="38" border="0" id="login_r4_c10" alt="" /></td>
    <td><a href="#" onMouseOut="MM_swapImgRestore();" onMouseOver="MM_swapImage('login_r4_c2','','image/login_r4_c2_f2.jpg',1);" onClick="popGetPWD();"><img name="login_r4_c2" src="image/login_r4_c2.jpg" width="207" height="38" border="0" id="login_r4_c2" alt="忘記密碼" /></a></td>
    <td>
	<a onMouseOut="MM_swapImgRestore();" onMouseOver="MM_swapImage('login_r4_c6','','image/login_r4_c6_f2.jpg',1);" onClick="form1.submit();"><input type="image" name="login_r4_c6" src="image/login_r4_c6.jpg" width="221" height="38" border="0" id="login_r4_c6" alt="登入EIP資訊系統" /></a></td>
    <td>
	<img name="login_r4_c13" src="image/login_r4_c9.jpg" width="73" height="38" border="0" id="login_r4_c13" alt="" /></td>
  </tr>
</table>
			</td>
		</tr>
		<tr>
			<td>
<table border="0" cellpadding="0" cellspacing="0" width="524" id="table9">
  <tr>
   	<td width="183">
		<img name="login_r5_c1" src="image/login_r5_c1.jpg" width="183" height="37" border="0" id="login_r5_c1" style="display: block" />
	</td>
   	<td width="179">
		<img name="login_r5_c3" src="image/login_r5_c3_f2.jpg" width="179" height="37" border="0" id="login_r5_c3" style="display: block" /></td>
   	<td width="162">
   		<img name="login_r5_c6" src="image/login_r5_c6.jpg" width="209" height="37" border="0" id="login_r5_c6" />
   	</td>
   </tr>
</table>
			</td>
		</tr>
  </table>
</form>

</body>
</html>
