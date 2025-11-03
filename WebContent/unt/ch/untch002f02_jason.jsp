<%@ page contentType="text/html;charset=UTF-8" import="java.sql.ResultSet,java.sql.DatabaseMetaData,util.*,unt.ch.UNTCH_Tools" %>
<%@ page import="org.json.simple.*"%>
<%
response.addHeader("Pragma", "No-cache");
response.addHeader("Cache-Control", "no-cache");
response.addDateHeader("Expires", 1);
UNTCH_Tools ul = new UNTCH_Tools();

String q = Common.get(request.getParameter("q"));
String enterOrg = Common.get(request.getParameter("enterOrg"));
String differenceKind = Common.get(request.getParameter("differenceKind"));
String ownership = Common.get(request.getParameter("ownership"));
String propertyNo = Common.get(request.getParameter("propertyNo"));
String serialNo = Common.get(request.getParameter("serialNo"));


JSONObject sign=new JSONObject();
JSONArray  detail=new JSONArray();
String enterDateName = "";
if (q!=null && !q.equals("")){
	Database db = new Database();
	ResultSet rs = null;
	try {
    	String execSQL=" select sum(cnt) as cnt from ("+
		"select count(1) as cnt from UNTLA_REDUCEDETAIL where enterorg = "+Common.sqlChar(Common.esapi(enterOrg))+" and differencekind="+Common.sqlChar(Common.esapi(differenceKind))+" and ownership="+Common.sqlChar(Common.esapi(ownership))+" and propertyno="+Common.sqlChar(Common.esapi(propertyNo))+"  and serialno="+Common.sqlChar(Common.esapi(serialNo))+" and verify='Y' "+"\n"+
		"union select count(1)  as cnt from UNTBU_REDUCEDETAIL where enterorg = "+Common.sqlChar(Common.esapi(enterOrg))+" and differencekind="+Common.sqlChar(Common.esapi(differenceKind))+" and ownership="+Common.sqlChar(Common.esapi(ownership))+" and propertyno="+Common.sqlChar(Common.esapi(propertyNo))+"  and serialno="+Common.sqlChar(Common.esapi(serialNo))+" and verify='Y' "+"\n"+
		"union select count(1)  as cnt from UNTRF_REDUCEDETAIL where enterorg = "+Common.sqlChar(Common.esapi(enterOrg))+" and differencekind="+Common.sqlChar(Common.esapi(differenceKind))+" and ownership="+Common.sqlChar(Common.esapi(ownership))+" and propertyno="+Common.sqlChar(Common.esapi(propertyNo))+"  and serialno="+Common.sqlChar(Common.esapi(serialNo))+" and verify='Y' "+"\n"+
		"union select count(1)  as cnt from UNTMP_REDUCEDETAIL where enterorg = "+Common.sqlChar(Common.esapi(enterOrg))+" and differencekind="+Common.sqlChar(Common.esapi(differenceKind))+" and ownership="+Common.sqlChar(Common.esapi(ownership))+" and propertyno="+Common.sqlChar(Common.esapi(propertyNo))+"  and serialno="+Common.sqlChar(Common.esapi(serialNo))+" and verify='Y' "+"\n"+
		"union select count(1)  as cnt from UNTVP_REDUCEPROOF where enterorg = "+Common.sqlChar(Common.esapi(enterOrg))+" and differencekind="+Common.sqlChar(Common.esapi(differenceKind))+" and ownership="+Common.sqlChar(Common.esapi(ownership))+" and propertyno="+Common.sqlChar(Common.esapi(propertyNo))+"  and serialno="+Common.sqlChar(Common.esapi(serialNo))+" and verify='Y' "+"\n"+
		"union select count(1)  as cnt from UNTRT_REDUCEPROOF where enterorg = "+Common.sqlChar(Common.esapi(enterOrg))+" and differencekind="+Common.sqlChar(Common.esapi(differenceKind))+" and ownership="+Common.sqlChar(Common.esapi(ownership))+" and propertyno="+Common.sqlChar(Common.esapi(propertyNo))+"  and serialno="+Common.sqlChar(Common.esapi(serialNo))+" and verify='Y' "+"\n"+
		" ) as A ";

		//System.out.println("@@@@@@@@@@@@@==>"+execSQL);
		rs = db.querySQL_NoChange(execSQL);
		JSONObject item=new JSONObject();
		if (rs.next() && rs.getLong("cnt")>0 ) {
			item.put("cnt",rs.getLong("cnt"));
			detail.add(item);			
		}
		sign.put("detail",detail);
		out.print(sign);
	}catch(Exception e) {
		e.printStackTrace();
	}finally{
		db.closeAll();
	}
}	
 

 
%>
