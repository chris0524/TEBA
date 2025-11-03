<%@ page  contentType = "text/html;charset=UTF-8" isErrorPage="true" %>

<%
	String errMsg = (String) session.getAttribute("errMsg");
	String eipHomePage = (String) session.getAttribute("eipHomePage");
	session.invalidate();
%>
<html>
<head>
<title>單一簽入錯誤訊息</title>
</head>

<script>
function showErrMsgAndRedirectToEIP(){
	alert('<%=errMsg%>');
	window.location.href = '<%=eipHomePage%>';
}
</script>

<body onload="showErrMsgAndRedirectToEIP()">


</body>
</html>
