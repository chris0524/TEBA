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
		String sql = " delete  from UNTDP_MONTHDEPR  where 1=1 and enterorg="+Common.sqlChar(enterOrg) ;
		       sql+= " and deprym="+Common.sqlChar(deprYM)+"and differencekind="+Common.sqlChar(differenceKind);
		       if (!"".equals(propertyType)) 
		       sql +=" and propertytype = " + Common.sqlChar(propertyType) ;
		       db.excuteSQL(sql);
		
		
	}catch(Exception e) {
		e.printStackTrace();
	}finally{
		db.closeAll();
	}
}	
 

 
%>
