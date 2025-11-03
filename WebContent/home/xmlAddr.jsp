<%@ page contentType="application/xml;charset=UTF-8" import="java.sql.*,util.*"%>
<%

String addr1 = Common.checkGet(request.getParameter("addr1"));
String addr2 = Common.checkGet(request.getParameter("addr2"));
String sql ="";
if(addr1!=null){	
	if(addr1.equals("6300000000")||addr1.equals("6400000000")){
		sql=" select addrID, addrName, zipCode from SYSCA_Addr " +
			" where addrID like '" + Common.esapi(addr1.substring(0,2)) + "___00000'" +
			" and addrID <> '" + Common.esapi(addr1) + "'";
	}else{
		sql=" select addrID, addrName, zipCode from SYSCA_Addr " +
			" where addrID like '" + Common.esapi(addr1.substring(0,5)) + "__000'" +
			" and addrID <> '" + Common.esapi(addr1) + "'";					
	}		
}else{
	sql=" select addrID , addrName, zipCode from SYSCA_Addr "+
	    " where addrID like '"+Common.esapi(addr2.substring(0,7))+"___'"+
	    " and addrID <> '" + Common.esapi(addr2) + "'";
}
//out.write(sql);
StringBuffer strXML = new StringBuffer();
strXML.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
strXML.append("<ResultSet>");
Database db = new Database();
try {		

	ResultSet rs = db.querySQL(sql);
	while (rs.next()){
		if(!rs.getString(2).equals("明禮里")){
		    strXML.append("<record addrID=\""+Common.checkGet(rs.getString(1))+"\" addrName=\""+Common.checkGet(rs.getString(2))+"\" zipcode=\""+Common.get(Common.checkGet(rs.getString(3)))+"\" /> ");
		}
	}			
} catch (Exception e) {
	e.printStackTrace();
} finally {
	db.closeAll();
}	

strXML.append("</ResultSet>");	
out.write(strXML.toString());

%>
