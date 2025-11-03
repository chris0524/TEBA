<%@ page contentType="text/html;charset=UTF-8" import="java.sql.ResultSet,java.sql.DatabaseMetaData,util.*,unt.ch.UNTCH_Tools" %>
<%@ page import="org.json.simple.*"%>
<%
response.addHeader("Pragma", "No-cache");
response.addHeader("Cache-Control", "no-cache");
response.addDateHeader("Expires", 1);
UNTCH_Tools ul = new UNTCH_Tools();
String q = Common.checkGet(request.getParameter("q"));
String enterOrg = Common.checkGet(request.getParameter("enterOrg"));
String deprYM = ul._transToCE_Year(Common.checkGet(request.getParameter("deprYM")));
String propertyType = Common.checkGet(request.getParameter("propertyType"));
String differenceKind = Common.checkGet(request.getParameter("differenceKind"));

JSONObject sign=new JSONObject();
JSONArray  detail=new JSONArray();
if (q!=null && !q.equals("")){
	Database db = new Database();
	ResultSet rs = null;
	try {
		String sql = " select enterorg,deprym,propertytype,differencekind from UNTDP_MONTHDEPR a where 1=1 ";
		       if (!"".equals(enterOrg)) 
			       sql +=" and a.enterorg = " + Common.sqlChar(Common.esapi(enterOrg)) ;
		       if (!"".equals(deprYM)) 
			       sql +=" and a.deprym = " + Common.sqlChar(Common.esapi(deprYM)) ;
		       if (!"".equals(differenceKind)) 
			       sql +=" and a.differencekind = " + Common.sqlChar(Common.esapi(differenceKind)) ;
		       if (!"".equals(propertyType)) 
		       sql +=" and a.propertytype = " + Common.sqlChar(Common.esapi(propertyType)) ;
		   
		rs = db.querySQL(sql);
		while (rs.next()) {
			
			JSONObject item=new JSONObject();		
			/**
			for(int j=0;j<colNames.length;j++){
				item.put(colNames[j],Common.get(rs.getString(colNames[j])));	   
			}
			**/
			
			item.put("enterorg",Common.get(rs.getString("enterorg")));
			item.put("deprym",Common.get(rs.getString("deprym")));
			item.put("propertytype",Common.get(rs.getString("propertytype")));
			item.put("differencekind",Common.get(rs.getString("differencekind")));
			//item.put("signName",Common.get(rs.getString("signName")));
			
			detail.add(item);
			
		}
		
		// 查是否已有入帳
		sql = " select count(*) from UNTDP_MONTHDEPR a where 1=1 "
			  + " and a.verify ='Y' and a.enterorg = " + Common.sqlChar(Common.esapi(enterOrg))
			  + " and a.deprym = " + Common.sqlChar(Common.esapi(deprYM));
		 if (!"".equals(differenceKind)) { 
			sql +=" and a.differencekind = " + Common.sqlChar(Common.esapi(differenceKind)) ;
		 }
	     if (!"".equals(propertyType)) { 
			sql +=" and a.propertytype = " + Common.sqlChar(Common.esapi(propertyType)) ;
	     }
	     int verifyCount = 0;
	     rs = db.querySQL(sql);
	     if (rs.next()) {
	    	 verifyCount = rs.getInt(1);
	     }
		sign.put("detail",detail);
		sign.put("isVerify", verifyCount > 0 ? true : false);
		out.print(sign);
	}catch(Exception e) {
		e.printStackTrace();
	}finally{
		db.closeAll();
	}
}	
 

 
%>
