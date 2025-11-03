<%@page import="java.io.FileWriter"%>
<%@page import="java.io.BufferedWriter"%>
<%@ page contentType="application/xml;charset=UTF-8" import="java.sql.*,util.*"%>
<%
	String enterOrg = Common.checkGet(request.getParameter("enterOrg"));
	String organID = Common.checkGet(request.getParameter("organID"));
	String isAdminManager = Common.checkGet(request.getParameter("isAdminManager"));	
	String isOrganManager = Common.checkGet(request.getParameter("isOrganManager"));
	String source = Common.get(Common.checkGet(request.getParameter("source")));
	
	//String sql = "select codeID, codeName from SYSCA_Code where " +
	//			 " codeKindID='FUD' " +
	//			 "";
	//if (!"".equals(enterOrg)) {
	//	sql+=" and codeCon3 = " + Common.sqlChar(enterOrg) ;
	//} else {
	//	if (!isAdminManager.equalsIgnoreCase("Y")) {
	//		if ("Y".equals(isOrganManager)) {					
	//			sql += " and codeCon3 like '" + organID.substring(0,5) + "%' ";					
	//		} else {
	//			sql+=" and codeCon3 = " + Common.sqlChar(organID) ;
	//		}
	//	}
	//}
	//sql+=" order by codeCon3, codeID";

	//-- 查詢：「基金財產」依權限帶資料供選擇
	String sql = " select distinct a.codeID, a.codeName " 	+ "\n" +
	         	 " from SYSCA_Code a"  +
//	          	 " from SYSCA_Code a, SYSCA_FundOrgan b" 	+ "\n" +
	         	 (("sysca008f".equals(source))?"":" , SYSCA_FundOrgan b ") +
	        	 " where a.codekindid = 'FUD' " 			+ "\n" +
//	        	 "";
//	        	 " and a.codeID = b.fundNo ";
	        	 (("sysca008f".equals(source))?"":" and a.codeID = b.fundNo ") + "\n" ;
	        	 
	if(("sysca008f".equals(source))){
		
	}else{
		if (!"".equals(enterOrg)) {
			sql += " and b.enterOrg = '"+ Common.esapi(enterOrg) +"' "		+ "\n" ;
		} else {
			if (!"Y".equals(isAdminManager)) {
				if ("Y".equals(isOrganManager)) {					
					sql += " and ( b.enterOrg = '"+ Common.esapi(organID) +"' or " + "\n" +
					       "       b.enterOrg  in (select organID from SYSCA_Organ where organSuperior='"+ Common.esapi(organID) +"') " + "\n" + 
					       "      ) "+ "\n" ;
				} else {
					sql += " and b.enterOrg = '"+ Common.esapi(organID) +"' "+ "\n" ;  
				}
			}
		}
	}
	sql += " order by a.codeid " + "\n" ;
	//System.out.println("xmlChangeEnterOrg_FundType.jsp-sql:\n"+ sql);
	
	StringBuffer strXML = new StringBuffer();
	strXML.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
	strXML.append("<ResultSet>");

	StringBuilder data = new StringBuilder();
	Database db = new Database();
	try {
		ResultSet rs = db.querySQL(sql);
		
		while (rs.next()){
			strXML.append("<record codeID=\""+Common.checkGet(rs.getString(1))+"\" codeName=\""+Common.checkGet(rs.getString(2))+"\" /> ");
		}
		
	} catch (Exception e) {
		e.printStackTrace();
	} finally {
		db.closeAll();
	}	
	
	strXML.append("</ResultSet>");	
	out.write(strXML.toString());
%>
