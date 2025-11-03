<%@ page import="util.*" %>
<%@ page import="soa.client.utils.SSOUtil" %>
<%@ page import="filter.SSOFilter" %>
<%@ page import="org.owasp.esapi.ESAPI" %>
<%
/*
String token1 = (String) session.getAttribute(SSOFilter.SESSION_SSO_TOKEN1);
if(token1 != null && !"".equals(token1)){
	if (SSOUtil.isNeedValidateToken1(request, SSOFilter.validateSSOToken1Type, SSOFilter.intervalForValidingSSOToken1)){
		String ip = request.getRemoteAddr();
		boolean isValid = SSOUtil.validToken1(token1, ip);
		if (!isValid){
			session.setAttribute("user", null);
			session.setAttribute(SSOFilter.SESSION_SSO_TOKEN1, null);
		}
	}
}
*/
%>

<jsp:useBean id="user" scope="session" class="util.User"/>

<%

if ("".equals(user.getUserID())){
	String prefixScript = "top.top.location.href=";
	String indexPage = null;
	//if(token1 != null && !"".equals(token1)){
		//String eipIndexPage = (String) session.getAttribute(SSOFilter.SESSION_EIP_INDEX_PAGE);
		//indexPage = prefixScript + "'" + eipIndexPage + "';";
	//}else
		indexPage = prefixScript + "'../../index.jsp';";
			
	out.println("<script language=\"javascript\">");
	out.println("var prop='';");
	out.println("prop=prop+'scroll:yes;status:no;help:no;';");
	out.println("prop=prop+'dialogWidth:300px;';");
	out.println("prop=prop+'dialogHeight:120px';");
	out.println("window.showModalDialog('../../home/sessionTimeout.jsp',window,prop);");
	out.println(indexPage);
	out.println("</script>");
	
	return;
}

%>
