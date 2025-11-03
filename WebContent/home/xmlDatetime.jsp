<%@ page contentType="application/xml;charset=UTF-8"
	import="org.joda.time.*,util.Common"%>
<%
String sDate = Common.checkGet(request.getParameter("sDate"));
String eDate = Common.checkGet(request.getParameter("eDate"));

if (sDate.length()==7 && eDate.length()==7) {
    DateTime start =  new DateTime(Integer.parseInt(sDate.substring(0,3))+1911,Integer.parseInt(sDate.substring(3,5)),Integer.parseInt(sDate.substring(5)), 0, 0, 0, 0);
    DateTime end = new DateTime(Integer.parseInt(eDate.substring(0,3))+1911,Integer.parseInt(eDate.substring(3,5)),Integer.parseInt(eDate.substring(5)), 0, 0, 0, 0);
    Period p = new Period(start, end, PeriodType.yearMonthDay());	
    
	StringBuffer strXML = new StringBuffer();
	strXML.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
	strXML.append("<ResultSet>");	
	strXML.append("<record " + 
			" diffYears=\""+p.getYears()+ "\""+
			" diffMonths=\""+p.getMonths()+"\""+				
			" diffDays=\""+p.getDays()+"\""+
	" /> ");			
	strXML.append("</ResultSet>");	
	out.write(strXML.toString());	
}

%>
