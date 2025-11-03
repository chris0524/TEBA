
<%@page import="util.Database"%>
<%@page import="java.sql.*"%>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%

String result=request.getReader().readLine();

String[] resultArray=result.split("&");

Database db=new Database();
ResultSet rs;
String sql="",returnStr="";

try{
	String str=resultArray[4].replace("=","='") + "'";
	String[] strArray=str.split("=");
	String[] editArray=resultArray[6].split("=");
	
	//搜尋是否已存在資料
	sql="select count(*) as count from UNTLA_VALUE_"+util.Common.esapi(editArray[1])+" a " +
		" where 1=1" +
			" and " + util.Common.esapi(resultArray[0].replace("=","='")) + "'" +
			" and " + util.Common.esapi(resultArray[1].replace("=","='")) + "'" +
			" and (propertyno) in (select propertyno from PROPERTYTABLE_"+util.Common.esapi(editArray[1])+" where checkflag='Y')" +
			" and (serialno) in (select serialno from PROPERTYTABLE_"+util.Common.esapi(editArray[1])+" where checkflag='Y')" +
			" and " + util.Common.esapi(str);

	rs = db.querySQL(sql);
	if(rs.next()){	returnStr=rs.getString("count");}
	rs.close();
	
	String[] splitArray;
	
	if("0".equals(returnStr)){
		sql="insert into UNTLA_VALUE_"+util.Common.esapi(editArray[1])+" (enterorg, ownership, propertyno, serialno, bulletindate, valueunit, editid, editdate, edittime, suitdates, suitdatee, notes)values" +
			"(";
			
			for(int i=0;i<=8;i++){
				if(i==2){
					sql+="(select propertyno from PROPERTYTABLE_"+util.Common.esapi(editArray[1])+" where checkflag='Y'),";
				}else if(i==3){
					sql+="(select serialno from PROPERTYTABLE_"+util.Common.esapi(editArray[1])+" where checkflag='Y'),";
				}else{
					splitArray = resultArray[i].split("=");
					sql+="'"+util.Common.esapi(splitArray[1])+"',";
				}
			}
				
		sql+="(select suitdates from UNTLA_BULLETINDATE where 1=1 and bulletinkind = '1' and bulletindate='201301'),"+
			 "(select suitdatee from UNTLA_BULLETINDATE where 1=1 and bulletinkind = '1' and bulletindate='201301'),"+
			 "null"+
			")";

		db.querySQL_NoChange(sql);		
	}else{
	
		sql="update UNTLA_VALUE_"+editArray[1]+" set " +
					resultArray[5].replace("=","='") + "'" + 
				" where 1=1" +
				" and " + resultArray[0].replace("=","='") + "'" +
				" and " + resultArray[1].replace("=","='") + "'" +
				" and (propertyno) in (select propertyno from PROPERTYTABLE_"+editArray[1]+" where checkflag='Y')" +
				" and (serialno) in (select serialno from PROPERTYTABLE_"+editArray[1]+" where checkflag='Y')" +
				" and " + str;

		db.querySQL_NoChange(sql);
		
	}	

	sql="update UNTLA_LAND_"+util.Common.esapi(editArray[1])+" set " +
				" originaldate = '" + util.Common.esapi(resultArray[4].split("=")[1]) + "'" + 
			" where 1=1" +
			" and " + util.Common.esapi(resultArray[0].replace("=","='")) + "'" +
			" and " + util.Common.esapi(resultArray[1].replace("=","='")) + "'" +
			" and (propertyno) in (select propertyno from PROPERTYTABLE_"+util.Common.esapi(editArray[1])+" where checkflag='Y')" +
			" and (serialno) in (select serialno from PROPERTYTABLE_"+util.Common.esapi(editArray[1])+" where checkflag='Y')" ;
	
	db.querySQL_NoChange(sql);
	
}catch(Exception e){
	e.printStackTrace();
}finally{
	db.closeAll();
}

%>
