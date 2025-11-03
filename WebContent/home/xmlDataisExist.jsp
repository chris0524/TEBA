<%@ page contentType="application/xml;charset=UTF-8" import="java.sql.*,util.*"%>
<jsp:useBean id="objCommon" scope="page" class="util.Common"/>
<%

String sSQLType = Common.checkGet(request.getParameter("sSQLType"));
String sSQL = Common.checkGet(request.getParameter("sSQL"));
//System.out.println(sSQL);

String sFirstFieldData = objCommon.getFirstField(sSQL, sSQLType);

//System.out.println(sFirstFieldData);

StringBuffer strXML = new StringBuffer();
strXML.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
strXML.append("<ResultSet>");
strXML.append("<record FirstFieldData=\""+Common.checkGet(sFirstFieldData)+"\" /> ");
strXML.append("</ResultSet>");	

//objFileOut.Foo(strXML.toString(),"FirstFieldData.xml");

out.write(strXML.toString());

%>
