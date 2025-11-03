
<%@page import="util.Datetime"%>
<%@page import="util.Database"%>
<%@page import="java.sql.*"%>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>

<%

String result=request.getReader().readLine();

String[] resultArray=result.split("&");

Database db=new Database();
ResultSet rs;
String sql, returnStr="";

try{
	String typeStr = resultArray[0].replace("type=","");

	String[] editStr = resultArray[(resultArray.length-1)].replace("colvalue%5B%5D=","").replace("%3D","='").split("=");

	if("valueUnit".equals(typeStr)){
		String str=resultArray[7].replace("colvalue%5B%5D=","").replace("%3D","='") + "'";

		String[] strArray=str.split("=");
		boolean checkCountFlag = true;
		
		sql="select count(*) as COUNT from  UNTLA_VALUE_"+editStr[1].replace(".","") +				 
			" where 1=1" +
			" and " + util.Common.esapi(resultArray[1].replace("colvalue%5B%5D=","").replace("%3D","='")).replace("''", "'") + "'" +
			" and " + util.Common.esapi(resultArray[2].replace("colvalue%5B%5D=","").replace("%3D","='")).replace("''", "'") + "'" +
			" and " + util.Common.esapi(resultArray[3].replace("colvalue%5B%5D=","").replace("%3D","='")).replace("''", "'") + "'" +
			" and " + util.Common.esapi(resultArray[4].replace("colvalue%5B%5D=","").replace("%3D","='")).replace("''", "'") + "'" +
			" and " + util.Common.esapi(resultArray[5].replace("colvalue%5B%5D=","").replace("%3D","='")).replace("''", "'") + "'" +
			" and " + str.toLowerCase();
		rs = db.querySQL_NoChange(sql);
// 		rs = db.querySQL(sql);
		while(rs.next()){
			if("0".equals(rs.getString("COUNT"))){	checkCountFlag = false;}			
		}

		if(checkCountFlag){
			sql="update UNTLA_VALUE_"+editStr[1].replace(".","")+" set " +
					resultArray[5].toLowerCase().replace("colvalue%5B%5D=","").replace("%3D","='") + "'" + 
				" where 1=1" +
				" and " + resultArray[1].replace("colvalue%5B%5D=","").replace("%3D","='") + "'" +
				" and " + resultArray[2].replace("colvalue%5B%5D=","").replace("%3D","='") + "'" +
				" and " + resultArray[3].replace("colvalue%5B%5D=","").replace("%3D","='") + "'" +
				" and " + resultArray[4].replace("colvalue%5B%5D=","").replace("%3D","='") + "'" +
				" and " + resultArray[5].replace("colvalue%5B%5D=","").replace("%3D","='") + "'" +
				" and " + str;			

		}else{
			
			sql="insert into UNTLA_VALUE_" + editStr[1].replace(".","") + "(" +
					" enterorg, ownership, differencekind, propertyno, serialno, bulletindate, " +
					" valueunit, suitdates, suitdatee, notes, editid, editdate, edittime" +
				")values(" +
				resultArray[1].replace("colvalue%5B%5D=","").replace("%3D","='").split("=")[1] + "'," +
				resultArray[2].replace("colvalue%5B%5D=","").replace("%3D","='").split("=")[1] + "'," +
				resultArray[3].replace("colvalue%5B%5D=","").replace("%3D","='").split("=")[1] + "'," +
				resultArray[4].replace("colvalue%5B%5D=","").replace("%3D","='").split("=")[1] + "'," +
				resultArray[5].replace("colvalue%5B%5D=","").replace("%3D","='").split("=")[1] + "'," +
				str.split("=")[1] + "," +				
				resultArray[6].replace("colvalue%5B%5D=","").replace("%3D","='").split("=")[1].toLowerCase() + "'," +
				"(select suitdates from UNTLA_BULLETINDATE where bulletinkind = '1' and bulletindate = " + str.split("=")[1] + ")," +
				"(select suitdatee from UNTLA_BULLETINDATE where bulletinkind = '1' and bulletindate = " + str.split("=")[1] + ")," +
				"null," +
				"'" + editStr[1].replace(".","") + "'," +
				"'" + Datetime.getYYYMMDD() + "'," +
				"'" + Datetime.getHHMMSS() + "')" ;
			
		}
		
	}else{
		sql="update UNTLA_LAND_"+editStr[1].replace(".","")+" set " ;
		
		int arraylen = resultArray.length;	
		for(int i=5;i<=(arraylen-2);i++){
			sql+=resultArray[i].replace("colvalue%5B%5D=","").replace("%3D","='") + "'" ;
			if(i==(arraylen-2)){
				
			}else{
				sql+=",";
			}
		}	

		sql+=" where 1=1" +
				" and " + util.Common.esapi(resultArray[1].replace("colvalue%5B%5D=","").replace("%3D","='")).replace("''", "'") + "'" +
				" and " + util.Common.esapi(resultArray[2].replace("colvalue%5B%5D=","").replace("%3D","='")).replace("''", "'") + "'" +
				" and " + util.Common.esapi(resultArray[3].replace("colvalue%5B%5D=","").replace("%3D","='")).replace("''", "'") + "'" +
				" and " + util.Common.esapi(resultArray[4].replace("colvalue%5B%5D=","").replace("%3D","='")).replace("''", "'") + "'" +
				" and " + util.Common.esapi(resultArray[5].replace("colvalue%5B%5D=","").replace("%3D","='")).replace("''", "'") + "'";

	}
	db.exeSQL_NoChange(sql);
// 	db.querySQL(sql);

	if(sql.contains("holdArea")){
		
		sql=" update UNTLA_MANAGE_" +util.Common.esapi(editStr[1].replace(".",""))+ " a set " +
				" useArea = (select holdarea from UNTLA_LAND_" +util.Common.esapi(editStr[1].replace(".",""))+ " b where 1=1 " +
							" and a.enterorg=b.enterorg and a.ownership=b.ownership and a.differencekind=b.differencekind and a.propertyno=b.propertyno and a.serialno=b.serialno" +
							")" +
			" where 1=1" +
			" and " + util.Common.esapi(resultArray[1].replace("colvalue%5B%5D=","").replace("%3D","='")).replace("''", "'") + "'" +
			" and " + util.Common.esapi(resultArray[2].replace("colvalue%5B%5D=","").replace("%3D","='")).replace("''", "'") + "'" +
			" and " + util.Common.esapi(resultArray[3].replace("colvalue%5B%5D=","").replace("%3D","='")).replace("''", "'") + "'" +
			" and " + util.Common.esapi(resultArray[4].replace("colvalue%5B%5D=","").replace("%3D","='")).replace("''", "'") + "'" +				
			" and " + util.Common.esapi(resultArray[5].replace("colvalue%5B%5D=","").replace("%3D","='")).replace("''", "'") + "'" +
			" and (select count(*) from UNTLA_MANAGE_" +util.Common.esapi(editStr[1].replace(".",""))+
					" where 1=1" +
					" and " + util.Common.esapi(resultArray[1].replace("colvalue%5B%5D=","").replace("%3D","='")).replace("''", "'") + "'" +
					" and " + util.Common.esapi(resultArray[2].replace("colvalue%5B%5D=","").replace("%3D","='")).replace("''", "'") + "'" +
					" and " + util.Common.esapi(resultArray[3].replace("colvalue%5B%5D=","").replace("%3D","='")).replace("''", "'") + "'" +
					" and " + util.Common.esapi(resultArray[4].replace("colvalue%5B%5D=","").replace("%3D","='")).replace("''", "'") + "'" +
					" and " + util.Common.esapi(resultArray[5].replace("colvalue%5B%5D=","").replace("%3D","='")).replace("''", "'") + "'" +
					") = 1";
		try{
// 			db.queryUpdateSQL_NoChange(sql);
			db.exeSQL_NoChange(sql);
// 			db.querySQL_NoChange(sql);
		}catch(Exception e){
			
		}
		
		sql=" update UNTLA_ATTACHMENT_" +util.Common.esapi(editStr[1].replace(".",""))+ " a set " +
				" useArea = (select holdarea from UNTLA_LAND_" +util.Common.esapi(editStr[1].replace(".",""))+ " b where 1=1 " +
							" and a.enterorg=b.enterorg and a.ownership=b.ownership and a.differencekind = b.differencekind and a.propertyno=b.propertyno and a.serialno=b.serialno" +
							")" +
			" where 1=1" +
			" and " + util.Common.esapi(resultArray[1].replace("colvalue%5B%5D=","").replace("%3D","='")).replace("''", "'") + "'" +
			" and " + util.Common.esapi(resultArray[2].replace("colvalue%5B%5D=","").replace("%3D","='")).replace("''", "'") + "'" +
			" and " + util.Common.esapi(resultArray[3].replace("colvalue%5B%5D=","").replace("%3D","='")).replace("''", "'") + "'" +
			" and " + util.Common.esapi(resultArray[4].replace("colvalue%5B%5D=","").replace("%3D","='")).replace("''", "'") + "'" +				
			" and " + util.Common.esapi(resultArray[5].replace("colvalue%5B%5D=","").replace("%3D","='")).replace("''", "'") + "'" +
			" and (select count(*) from UNTLA_ATTACHMENT_" +util.Common.esapi(editStr[1].replace(".",""))+
					" where 1=1" +
					" and " + util.Common.esapi(resultArray[1].replace("colvalue%5B%5D=","").replace("%3D","='")).replace("''", "'") + "'" +
					" and " + util.Common.esapi(resultArray[2].replace("colvalue%5B%5D=","").replace("%3D","='")).replace("''", "'") + "'" +
					" and " + util.Common.esapi(resultArray[3].replace("colvalue%5B%5D=","").replace("%3D","='")).replace("''", "'") + "'" +
					" and " + util.Common.esapi(resultArray[4].replace("colvalue%5B%5D=","").replace("%3D","='")).replace("''", "'") + "'" +
					" and " + util.Common.esapi(resultArray[5].replace("colvalue%5B%5D=","").replace("%3D","='")).replace("''", "'") + "'" +
					") = 1";
		
		try{
			db.exeSQL_NoChange(sql);
// 			db.queryUpdateSQL_NoChange(sql);
// 			db.querySQL(sql);
		}catch(Exception e){
			
		}
	}
	
}catch(Exception e){
	e.printStackTrace();
}finally{
	db.closeAll();
}

%>
