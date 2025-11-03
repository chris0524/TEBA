<%@ page contentType="text/html;charset=UTF-8" import="java.sql.ResultSet,java.sql.DatabaseMetaData,util.*,unt.ch.UNTCH_Tools" %>
<%@ page import="org.json.simple.*"%>
<%
response.addHeader("Pragma", "No-cache");
response.addHeader("Cache-Control", "no-cache");
response.addDateHeader("Expires", 1);
UNTCH_Tools ul = new UNTCH_Tools();

String q = Common.get(request.getParameter("q"));
String enterOrg = Common.get(request.getParameter("enterOrg"));
String isOrganManager = Common.get(request.getParameter("isOrganManager"));
String OrganID =   Common.get(request.getParameter("OrganID"));
String serialNoS = Common.get(request.getParameter("serialNoS"));
String serialNoE = Common.get(request.getParameter("serialNoE"));
String ownership = Common.get(request.getParameter("ownership"));
String caseNoS = Common.get(request.getParameter("caseNoS"));
String caseNoE = Common.get(request.getParameter("caseNoE"));
String proofDoc = Common.get(request.getParameter("proofDoc"));
String proofNoS = Common.get(request.getParameter("proofNoS"));
String proofNoE = Common.get(request.getParameter("proofNoE"));
String propertyNoS = Common.get(request.getParameter("propertyNoS"));
String propertyNoE = Common.get(request.getParameter("propertyNoE"));
String dataState = Common.get(request.getParameter("dataState"));
String enterDateS = Common.get(request.getParameter("enterDateS"));
String enterDateE = Common.get(request.getParameter("enterDateE"));
String propertyKind = Common.get(request.getParameter("propertyKind"));
String fundType = Common.get(request.getParameter("fundType"));
String differenceKind = Common.get(request.getParameter("differenceKind"));
String propertyName1 = Common.get(request.getParameter("propertyName1"));
String keepUnit = Common.get(request.getParameter("keepUnit"));
String keeper = Common.get(request.getParameter("keeper"));
String useUnit = Common.get(request.getParameter("useUnit"));
String userNo = Common.get(request.getParameter("userNo"));
String userNote = Common.get(request.getParameter("userNote"));
String place1 = Common.get(request.getParameter("place1"));
String placeNote = Common.get(request.getParameter("placeNote"));
String valuable = Common.get(request.getParameter("valuable"));
String writeDateS = Common.get(request.getParameter("writeDateS"));
String writeDateE = Common.get(request.getParameter("writeDateE"));
String workKind = Common.get(request.getParameter("workKind"));
JSONObject sign=new JSONObject();
JSONArray  detail=new JSONArray();
String enterDateName = "";
if (q!=null && !q.equals("")){
	Database db = new Database();
	ResultSet rs = null;
	try {
    	String execSQL="select count(1) cnt "+
		" from UNTMP_ADDPROOF a left join UNTMP_MOVABLE b on a.caseno=b.caseno and a.enterorg=b.enterorg and a.ownership=b.ownership "+"\n"+
		" left join SYSCA_CODE h on b.sourcekind=h.codeid and h.codekindid = 'SKC' "+"\n"+
		" left join UNTMP_MOVABLEDETAIL c on c.enterorg=b.enterorg and c.ownership=b.ownership and b.lotno=c.lotno and b.propertyno=c.propertyno"+"\n"+
		" left join SYSCA_CODE g on b.fundtype=g.codeid and g.codekindid = 'FUD'"+"\n"+
		" left join UNTMP_ADJUSTDETAIL k on k.enterorg=c.enterorg and k.ownership=c.ownership and k.propertyno=c.propertyno and k.lotno=c.lotno and k.serialno=c.serialno"+"\n"+
		" left join UNTMP_ADJUSTPROOF j on j.enterorg=k.enterorg and j.ownership=k.ownership and j.caseno=k.caseno"+"\n"+
		" left join UNTMP_REDUCEDETAIL m on m.enterorg=c.enterorg and m.ownership=c.ownership and m.propertyno=c.propertyno and m.lotno=c.lotno and m.serialno=c.serialno"+"\n"+
		" left join UNTMP_REDUCEPROOF l on l.enterorg=m.enterorg and l.ownership=m.ownership and l.caseno=m.caseno"+"\n"+
		" left join UNTMP_MOVEDETAIL o on  o.enterorg=c.enterorg and o.ownership=c.ownership and o.propertyno=c.propertyno and o.lotno=c.lotno and o.serialno=c.serialno"+"\n"+
		" left join UNTMP_MOVEPROOF n on n.enterorg=o.enterorg and n.ownership=o.ownership and n.caseno=o.caseno"+"\n"+
		" left join  SYSCA_ORGAN d on b.enterorg = d.organid " +"\n"+
		" left join  UNTMP_KEEPER f on  b.enterorg=f.enterorg  and c.originalkeeper=f.keeperno " +"\n"+
		" left join  UNTMP_KEEPUNIT i on b.enterorg=i.enterorg and c.originalkeepunit=i.unitno " +"\n"+
		" where 1=1 " +"\n"+
		//" and b.propertyno = e.propertyno and e.enterorg in('000000000a',b.enterorg) and e.propertytype='1' "+"\n"+
		"";
    	if("a".equals(workKind)){
    		enterDateName = "enterdate";
    	}else if("j".equals(workKind)){
    		enterDateName = "adjustdate";
    	}else if("l".equals(workKind)){
    		enterDateName = "reducedate";
    	}else if("n".equals(workKind)){
    		enterDateName = "movedate";
    	}
    	if (!"".equals(Common.get(enterOrg))) {
			execSQL +=" and a.enterorg = " + Common.sqlChar(Common.esapi(enterOrg)) ;
		} else {
			if (!isOrganManager.equalsIgnoreCase("Y")) {
				if ("Y".equals(isOrganManager)) {					
					execSQL += " and a.enterorg like '" + Common.esapi(OrganID.substring(0,5)) + "%' ";					
				} else {
					execSQL +=" and a.enterorg = " + Common.sqlChar(Common.esapi(OrganID)) ;
				}
			}
		}
		if (!"".equals(Common.get(ownership)))
    		execSQL +=" and a.ownership = " + util.Common.sqlChar(Common.esapi(ownership));
		if (!"".equals(Common.get(caseNoS)))
			execSQL +=" and "+workKind+".caseno >= " + Common.sqlChar(Common.formatRearString(Common.esapi(caseNoS),10,'0'));		
		if (!"".equals(Common.get(caseNoE)))
			execSQL +=" and "+workKind+".caseno <= " + Common.sqlChar(Common.formatRearString(Common.esapi(caseNoE),10, '9'));
		if (!"".equals(Common.get(proofDoc)))
			execSQL += " and "+workKind+".proofdoc like '%" + Common.esapi(proofDoc) + "%'" ;
		if (!"".equals(Common.get(proofNoS))) 
			execSQL += " and "+workKind+".proofno >= " + Common.sqlChar(Common.esapi(proofNoS));		
		if (!"".equals(Common.get(proofNoE))) 
			execSQL += " and "+workKind+".proofno <= " + Common.sqlChar(Common.esapi(proofNoE));		 
		if (!"".equals(Common.get(writeDateS)))
    		execSQL += " and "+workKind+".writedate >= " + util.Common.sqlChar(Common.esapi(writeDateS));
    	if (!"".equals(Common.get(writeDateE)))
    		execSQL += " and "+workKind+".writedate <= " + util.Common.sqlChar(Common.esapi(writeDateE));
		if (!"".equals(Common.get(enterDateS)))
		    execSQL+=" and "+workKind+"." + enterDateName + " >= " + Common.sqlChar(Common.esapi(enterDateS));
		if (!"".equals(Common.get(enterDateE)))
		    execSQL+=" and "+workKind+"." + enterDateName + " <= " + Common.sqlChar(Common.esapi(enterDateE));
		if (!"".equals(Common.get(propertyNoS)))
		    execSQL+=" and b.propertyno >= " + Common.sqlChar(Common.esapi(propertyNoS));		
		if (!"".equals(Common.get(propertyNoE)))
		    execSQL+=" and b.propertyno <= " + Common.sqlChar(Common.esapi(propertyNoE));
		if (!"".equals(Common.get(dataState))){
		    execSQL+=" and b.datastate = " + Common.sqlChar(Common.esapi(dataState)) ;	    
			execSQL+=" and c.datastate = " + Common.sqlChar(Common.esapi(dataState)) ;
		}
		if (!"".equals(Common.get(propertyKind)))
		    execSQL+=" and b.propertykind = " + Common.sqlChar(Common.esapi(propertyKind)) ;
		if (!"".equals(Common.get(fundType)))
		    execSQL+=" and b.fundtype = " + Common.sqlChar(Common.esapi(fundType)) ;
		if (!"".equals(Common.get(valuable)))
		    execSQL+=" and b.valuable = " + Common.sqlChar(Common.esapi(valuable)) ;	    
		if (!"".equals(Common.get(serialNoS)))
		    execSQL+=" and c.serialno >= " + Common.sqlChar(Common.esapi(serialNoS));		
		if (!"".equals(Common.get(serialNoE)))
		    execSQL+=" and c.serialno <= " + Common.sqlChar(Common.esapi(serialNoE));			
		if (!"".equals(Common.get(keepUnit)))
		    execSQL+=" and c.keepunit = " + Common.sqlChar(Common.esapi(keepUnit)) ;	    
		if (!"".equals(Common.get(keeper)))
		    execSQL+=" and c.keeper = " + Common.sqlChar(Common.esapi(keeper)) ;	    
		if (!"".equals(Common.get(useUnit)))
		    execSQL+=" and c.useunit = " + Common.sqlChar(Common.esapi(useUnit)) ;	    
		if (!"".equals(Common.get(userNo)))
		    execSQL+=" and c.userno = " + Common.sqlChar(Common.esapi(userNo)) ;
		if(!"".equals(differenceKind))
    		execSQL+=" and c.differencekind = " + Common.sqlChar(Common.esapi(differenceKind)) ;
		if(!"".equals(userNote))
			execSQL+=" and c.usernote like " + Common.sqlChar("%"+Common.esapi(userNote)+"%") ;
		if(!"".equals(place1))
			execSQL+=" and c.place1 = " + Common.sqlChar(Common.esapi(place1)) ;
		if(!"".equals(placeNote))
			execSQL+=" and c.place like " + Common.sqlChar("%"+Common.esapi(placeNote)+"%") ;
		System.out.println("@@@@@@@@@@@@@==>"+execSQL);
		rs = db.querySQL_NoChange(execSQL);
		JSONObject item=new JSONObject();
		if (rs.next() && rs.getLong("cnt") > 1500) {
			item.put("cnt","超過一千五百筆");
			detail.add(item);
		} else {
			
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
