<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="java.sql.*"  %>
<%@ page import="java.util.Vector"  %>
<%@ page import="util.Database"%>
<%@ page import="util.Common"%>
<%
int rowNo = 0;
String SQL = "";
String iSQLm = "";
Database db = new Database();
Vector orgList = new Vector();
ResultSet rs = null;

try {	
	db.exeSQL("DELETE SYSPK_PropertyCode WHERE enterOrg<>'000000000A'");
	
	//--所有機關
	SQL = "select ORGANID from sysca_organ t "
		+"where ismanager='Y'  "
		+"and substr(organid,1,7)!='3839999'  "
		+"and organid not in ("
		+"  '000000001Z','000000003Z','000000004Z','000000005Z','000000006Z','000000007Z','000000008Z','000000009Z','000000010Z','000000012Z','000000013Z','000000100F','000000200F','000000300F','000000400F','000000500F','000000600F','000000800F','000000900F','000001000F','000001100F' "
		+") "
		+"";
	rs = db.querySQL(SQL);
	while(rs.next()) {
		orgList.add(rs.getString("ORGANID"));
	}
	rs.close();
	
	//--wow財產編號503~505
	SQL = "select (NOR_CODE||NOR_NO) as propertyNo"
		+" ,'1' as propertyType"
		+" ,NOR_NAME as propertyName"
		+" ,NOR_UNIT as propertyUnit"
		+" ,NOR_STUFF as material"
		+" ,nvl(NOR_YEAR,0) as limitYear"
		+" ,NOR_NOTE as memo"
		+" ,NOR_SNAME as editID"
		+" ,NOR_SDATE as editDate"
		+" from PT_NORMAL "
		+" where substr(nor_code,1,3) in ('503','504','505')  "
		+"";
	
	for(int i = 0; i < orgList.size(); i++) {
		//rs = db.querySQL(SQL);
		Statement stmt = db.getConnection().createStatement(ResultSet.TYPE_FORWARD_ONLY,ResultSet.CONCUR_READ_ONLY);
		rs = stmt.executeQuery(SQL);
		
	    while(rs.next()) {
	    	rowNo = rs.getRow();  
	    	System.out.println(i +":"+ rowNo);  
						
	    	//寫入財產編號檔
	    	iSQLm = "insert into SYSPK_PropertyCode("
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
	     				+" "+ Common.sqlChar((String)orgList.get(i)) 		+" --enterOrg      \n"
			     		+","+ Common.sqlChar(rs.getString("propertyNo")) 	+" --propertyNo    \n"
			     		+","+ Common.sqlChar(rs.getString("propertyType")) +" --propertyType  \n"
			     		+","+ Common.sqlChar(rs.getString("propertyName")) +" --propertyName  \n"
			     		+","+ Common.sqlChar(rs.getString("propertyUnit")) +" --propertyUnit  \n"
			     		+","+ Common.sqlChar(rs.getString("material")) 	+" --material      \n"
			     		+","+ Common.sqlChar(rs.getString("limitYear")) 	+" --limitYear     \n"
			     		+","+ Common.sqlChar(rs.getString("memo"))	+" --memo          \n"
			     		+","+ Common.sqlChar(rs.getString("editID"))		+" --editID        \n"
			     		+","+ Common.sqlChar(rs.getString("editDate")) 	+" --editDate      \n"
	     		+")";	 	
	    	db.exeSQL(iSQLm);	
			iSQLm = null;    	
	    }//while end
	    
	    rs.close();	
	    stmt.close();
	}//for end
    
} catch(Exception e) {
	//System.out.println(">>rowNo = "+ rowNo);
	//System.out.println("/*SQL*/ "+ SQL);
	//System.out.println("/*iSQLm*/ "+ iSQLm);
	
    e.printStackTrace();
    out.println(e);
} finally {
    db.closeAll();
}
%>
