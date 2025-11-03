<%@ page contentType="application/xml;charset=UTF-8" import="java.sql.*,util.*"%>
<%@ page import="org.joda.time.DateTime"%> 
<%@ page import="org.joda.time.Period"%> 
<%@ page import="org.joda.time.PeriodType"%> 
<%@ include file="head.jsp" %>
<%
String OrganID = Common.get(Common.checkGet(request.getParameter("OrganID")));
String Ownership = Common.get(Common.checkGet(request.getParameter("Ownership")));
String SerialNo = Common.get(Common.checkGet(request.getParameter("SerialNo")));
String LevyNo = Common.get(Common.checkGet(request.getParameter("LevyNo")));
String PropertyNo = Common.get(Common.checkGet(request.getParameter("PropertyNo")));
String bulletinDate = Common.get(Common.checkGet(request.getParameter("bulletinDate")));
String uri = request.getRequestURI();
String sql2 = "";
String sql3 = "";
double valueUnit=1;
String intervalDateS="", intervalDateE="";

Database db = new Database();

sql2 = " select nvl(a.valueUnit,1)valueUnit from UNTLA_Value a " +
       " where 1=1 " +
       " and a.bulletinDate = '" + Common.esapi(bulletinDate) +"' " +
       "";

if (!"".equals(Common.get(OrganID)))	
sql2 += " and a.enterOrg = " + Common.sqlChar(Common.esapi(OrganID));
if (!"".equals(Common.get(Ownership)))
sql2 += " and a.Ownership ="+Common.sqlChar(Common.esapi(Ownership));
if (!"".equals(Common.get(SerialNo)))
sql2 += " and a.SerialNo = "+ Common.sqlChar(Common.esapi(SerialNo));
if (!"".equals(Common.get(PropertyNo)))
sql2 += " and a.PropertyNo ="+ Common.sqlChar(Common.esapi(PropertyNo));
//System.out.println(sql2);
ResultSet rs2 = db.querySQL(sql2);	

StringBuffer strXML = new StringBuffer();
strXML.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
strXML.append("<ResultSet>");

try {		

	ResultSet rs = db.querySQL(sql2); 
	if(rs.next()){
		valueUnit=rs.getDouble("valueUnit");
        strXML.append("<record valueUnit=\"").append(Common.checkGet(rs.getString("valueUnit"))).append("\" ");
	}else{
		strXML.append("<record valueUnit=\"1\" /> ");
	}		
	
	//算出應收金額
	sql3 = " select a.levyyyy,a.levyMM,a.levydate1,a.levydate2,a.disvalue1,a.disvalue2,a.disarea1,a.disarea2 from npbbl_levy a " +
	    " where 1=1 " +
	    " and a.levyno = " + Common.sqlChar( Common.esapi(LevyNo) ) + 
	    ""; 
	
	ResultSet rs_3 = db.querySQL(sql3);	
	if(rs_3.next()){
		double receiveValue = 0;
		String levyYYY=Common.get(rs_3.getString("levyYYY"));
		if ("01".equals(rs_3.getString("levyMM"))) {
			intervalDateS = levyYYY+"0101";
			intervalDateE = levyYYY+"0630";
		} else {
			intervalDateS = levyYYY+"0701";
			intervalDateE = levyYYY+"1231";				
		}		
		
		String sDate = Common.get(rs_3.getString("levydate1"));
		String eDate = Common.get(rs_3.getString("levydate2"));
		
		if ((Integer.parseInt(sDate)<=Integer.parseInt(intervalDateS)) && (Integer.parseInt(eDate)>=Integer.parseInt(intervalDateE))) {	
			receiveValue = (((rs_3.getDouble("disArea1") * rs_3.getDouble("disvalue1") + rs_3.getDouble("disArea2") * rs_3.getDouble("disvalue2"))*valueUnit)/2);		
		}else{
			if (Integer.parseInt(sDate)<=Integer.parseInt(intervalDateS)) {
				sDate = intervalDateS;
			}
			if (Integer.parseInt(eDate)>=Integer.parseInt(intervalDateE)) {
				eDate = intervalDateE;
			}						
			eDate = Datetime.getDateAdd("d",1,eDate);

			DateTime start =  new DateTime(Integer.parseInt(sDate.substring(0,3))+1911,Integer.parseInt(sDate.substring(3,5)),Integer.parseInt(sDate.substring(5)), 0, 0, 0, 0);
		    DateTime end = new DateTime(Integer.parseInt(eDate.substring(0,3))+1911,Integer.parseInt(eDate.substring(3,5)),Integer.parseInt(eDate.substring(5)), 0, 0, 0, 0);
		    Period p = new Period(start, end, PeriodType.yearMonthDay());

		    int diffMonth = p.getMonths();
		    int diffDay = p.getDays();

			double dMon = 0, dDay = 0;
			if (diffMonth>0) {
				dMon = ((rs_3.getDouble("disArea1") * rs_3.getDouble("disvalue1") + rs_3.getDouble("disArea2") * rs_3.getDouble("disvalue2"))* valueUnit) * (diffMonth/12.0);
			}
			if (diffDay>0) {
				dDay = ((rs_3.getDouble("disArea1") * rs_3.getDouble("disvalue1") + rs_3.getDouble("disArea2") * rs_3.getDouble("disvalue2"))* valueUnit) * (diffDay/365.0);
			}
			receiveValue = (dMon+dDay);	
		}
	
		 strXML.append(" receiveValue=\"").append(Math.floor(receiveValue)).append("\" /> ");
		 

		
	}
	rs_3.getStatement().close();
	
} catch (Exception e) {
	e.printStackTrace();
} finally {
	db.closeAll();
}	

strXML.append("</ResultSet>");	
out.write(strXML.toString());
%>
