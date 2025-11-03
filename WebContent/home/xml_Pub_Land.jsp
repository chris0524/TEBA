<%@ page contentType="application/xml;charset=UTF-8" import="java.sql.*,util.*"%>
<%@ include file="head.jsp" %>
<%
	String sType = Common.checkGet(request.getParameter("sType"));
	String enterOrg = Common.checkGet(request.getParameter("enterOrg"));
	String ownership = Common.checkGet(request.getParameter("ownership"));
	String datastate = Common.checkGet(request.getParameter("datastate"));
	
	String propertyNo = "" ;
	String serialNo = "" ;
	String signNo1 = "" ;
	String signNo2 = "" ;
	String signNo3 = "" ;
	String signNo4 = "" ;
	String signNo5 = "" ;
	
	if("PN".equals(Common.get(sType))) {
		propertyNo = Common.checkGet(request.getParameter("propertyNo"));
		serialNo = Common.checkGet(Common.formatFrontZero(request.getParameter("serialNo"),7));
	}else if("SN".equals(Common.get(sType))){
		signNo1 = Common.checkGet(request.getParameter("signNo1"));
		signNo2 = Common.checkGet(request.getParameter("signNo2"));
		signNo3 = Common.checkGet(request.getParameter("signNo3"));
		signNo4 = Common.checkGet(Common.formatFrontZero(request.getParameter("signNo4"),4));
		signNo5 = Common.checkGet(Common.formatFrontZero(request.getParameter("signNo5"),4));         
	}

	String sSQL = " select a.enterorg ,o.organsname " + "\n"
		 		+ "        ,a.ownership ,a.propertyno ,p.propertyname as propertyNoName ,a.serialno " + "\n"
		 		+ "        ,a.signno " + "\n"
		 		+ "        ,substr(a.signno,1,1)||'000000' as signno1 ,substr(a.signno,1,3)||'0000'as signno2 ,substr(a.signno,1,7) as signno3 " + "\n"
		 		+ "        ,substr(a.signno,8,4) as signno4 ,substr(a.signno,12,4)as signno5 " + "\n"
		 		+ "        ,a.enterDate ,a.useSeparate " + "\n"
		 		+ " from untla_land a ,sysca_organ o ,syspk_propertycode p " + "\n"
		 		+ " where 1=1 " + "\n"
		 		+ " and a.enterorg = o.organid  " + "\n"
		 		+ " and a.propertyno = p.propertyno  and p.enterorg in (a.enterorg ,'000000000A') " + "\n"
		 		+ " and a.enterorg = " + Common.sqlChar( Common.esapi(enterOrg) ) + "\n"
		 		+ " and a.ownership = " + Common.sqlChar( Common.esapi(ownership) ) + "\n"
		 		+ " and a.verify = 'Y' " + "\n"
		 		+ "";
		 
	if("PN".equals(Common.get(sType))) {
		sSQL += " and a.propertyno = " + Common.sqlChar(Common.esapi(propertyNo));
		sSQL += " and a.serialno = " + Common.sqlChar(Common.esapi(serialNo));
	}else if("SN".equals(Common.get(sType))){
		sSQL += " and a.signno = " + Common.sqlChar( Common.esapi(signNo3 + signNo4 + signNo5) );
	}
	
	if(!"".equals(Common.get(datastate))){
		sSQL += " and a.datastate = " + Common.sqlChar( Common.esapi(datastate) ) + "\n";
	}
	//System.out.println(sSQL);

StringBuffer strXML = new StringBuffer(1000);
		strXML.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
		strXML.append("<ResultSet>");
	Database db = new Database();
	try {		
		ResultSet rs = db.querySQL(sSQL);
		while (rs.next()){
			strXML.append("<record ");
			strXML.append(" propertyNo=\"").append(Common.get(Common.checkGet(rs.getString("propertyNo")))).append( "\"");
			strXML.append(" propertyNoName=\"").append(Common.get(Common.checkGet(rs.getString("propertyNoName")))).append( "\"");
			strXML.append(" serialNo=\"").append(Common.get(Common.checkGet(rs.getString("serialNo")))).append( "\"");
			
			strXML.append(" signNo1=\"").append(Common.get(Common.checkGet(rs.getString("signNo1")))).append( "\"");
			strXML.append(" signNo2=\"").append(Common.get(Common.checkGet(rs.getString("signNo2")))).append( "\"");
			strXML.append(" signNo3=\"").append(Common.get(Common.checkGet(rs.getString("signNo3")))).append( "\"");
			strXML.append(" signNo4=\"").append(Common.get(Common.checkGet(rs.getString("signNo4")))).append( "\"");
			strXML.append(" signNo5=\"").append(Common.get(Common.checkGet(rs.getString("signNo5")))).append( "\"");
			
			strXML.append(" useSeparate=\"").append(Common.get(Common.checkGet(rs.getString("useSeparate")))).append( "\"");
			strXML.append(" enterDate=\"").append(Common.get(Common.checkGet(rs.getString("enterDate")))).append( "\"");

			strXML.append(" />");
		}		
	} catch (Exception e) {
		e.printStackTrace();
	} finally {
		db.closeAll();
	}	
	
	strXML.append("</ResultSet>");
	//System.out.println(strXML.toString());
	out.write(strXML.toString());
%>
