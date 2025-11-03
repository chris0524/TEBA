<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="java.sql.*"  %>
<%@ page import="java.util.Vector"  %>
<%@ page import="util.Database"%>
<%
int rowNo = 0;
String SQL = "";
String iSQLm = "";
Database db = new Database();
Vector orgList = new Vector();
ResultSet rs = null;

try {	
	db.exeSQL("DELETE SYSPK_PropertyCode2 WHERE enterOrg<>'000000000A'");
	
	//--所有機關
	SQL = "SELECT DECODE((A.GOOD_SORG||A.GOOD_SUNIT),"
		+"	'376470000A11', '000000011Z',"
		+"	A.GOOD_SORG"
		+"	) as enterOrg"
		+"	from (SELECT DISTINCT GOOD_SORG,GOOD_SUNIT FROM PT_GOOD)A";
			
	rs = db.querySQL(SQL);
	while(rs.next()) {
		orgList.add(rs.getString("ORGANID"));
	}
	rs.close();
	
	//--wow財產編號
	SQL = "select "
        +" DECODE(substr(NOR_CODE,0,1), \n"
        +"   6,NOR_CODE||NOR_NO, \n"
        +"   '6'||NOR_CODE||NOR_NO \n"
        +" 	) as propertyNo \n"
		+" ,'1' as propertyType"
		+" ,NOR_NAME as propertyName"
		+" ,NOR_UNIT as propertyUnit"
		+" ,NOR_STUFF as material"
		+" ,nvl(NOR_YEAR,0) as limitYear"
		+" ,replace(NOR_NOTE,'null','') as memo"
		+" ,NOR_SNAME as editID"
		+" ,NOR_SDATE as editDate"
		+" from PT_NORMAL where "
		+" NOR_CODE||'-'||NOR_NO in( "
		+" 	( select distinct GOOD_CODE from PT_GOOD ) "
		+" 	union "
		+" 	( select distinct gdcd_code from PT_GDCD ) "
		+" 	union "
		+" 	( select distinct grd_code from PT_GRD ) "
		+" ) "
		+"";
	
	for(int i = 0; i < orgList.size(); i++) {
		//rs = db.querySQL(SQL);
		Statement stmt = db.getConnection().createStatement(ResultSet.TYPE_FORWARD_ONLY,ResultSet.CONCUR_READ_ONLY);
		rs = stmt.executeQuery(SQL);
		
	    while(rs.next()) {
	    	rowNo = rs.getRow();  
	    	System.out.println(i +":"+ rowNo);  
						
	    	//寫入物品編號檔
	    	iSQLm = "insert into SYSPK_PropertyCode2("
			    		+" enterOrg     " 
			    		+",propertyNo   "
			    		+",propertyType "
			    		+",propertyName "
			    		+",propertyUnit "
			    		+",material     "
			    		+",limitYear    "
			    		+",memo         "
			    		+",editID       "
			    		+",editDate     " 
	     		+") values(\n"
	     				+" '"+ (String)orgList.get(i) 		+"' --enterOrg      \n"
			     		+",'"+ rs.getString("propertyNo") 	+"' --propertyNo    \n"
			     		+",'"+ rs.getString("propertyType") +"' --propertyType  \n"
			     		+",'"+ rs.getString("propertyName") +"' --propertyName  \n"
			     		+",'"+ rs.getString("propertyUnit") +"' --propertyUnit  \n"
			     		+",'"+ rs.getString("material") 	+"' --material      \n"
			     		+",'"+ rs.getString("limitYear") 	+"' --limitYear     \n"
			     		+",'"+ rs.getString("memo") 		+"' --memo          \n"
			     		+",'"+ rs.getString("editID") 		+"' --editID        \n"
			     		+",'"+ rs.getString("editDate") 	+"' --editDate      \n"
	     		+")";	 	
	    	db.exeSQL(iSQLm);	
			iSQLm = null;    	
	    }//while end
	    
	    rs.close();	
	    stmt.close();
	}//for end
    
} catch(Exception e) {
	System.out.println(">>rowNo = "+ rowNo);
	System.out.println("/*SQL*/ "+ SQL);
	System.out.println("/*iSQLm*/ "+ iSQLm);
	
    e.printStackTrace();
    out.println(e);
} finally {
    db.closeAll();
}
%>
