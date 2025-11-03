<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="java.sql.*"  %>
<%@ page import="util.Database"%>
<%@ include file="func.jsp"%>
<%@ page import="util.Common" %>

<%
int recNo_s = Integer.parseInt(request.getParameter("recNo_s"));
int recNo_e = Integer.parseInt(request.getParameter("recNo_e"));

int rowNo = 0;
String SQL = "";
String iSQLm = "";
String iSQLd = "";
String iSQLa = "";
String sSQL1 = "";
String sSQL2 = "";
String sSQL3 = "";
String sSQL4 = "";
Database db = new Database();

String caseNo = "";
String maxCaseNo = "";
String ym = util.Datetime.getYYYMM();

try {	
	if(recNo_s == 1) {
		dropTable(db,"PT_MABLE_TEMP");
		db.exeSQL("CREATE TABLE PT_MABLE_TEMP AS(SELECT ROWNUM as id,A.* FROM PT_MABLE A)");
		db.exeSQL("CREATE unique index KFCP.PT_MABLE_IDX1 on KFCP.PT_MABLE_TEMP (MABLE_CODE, MABLE_NUM, MABLE_SORG, MABLE_SUNIT)");
		db.exeSQL("CREATE index KFCP.PT_MABLE_IDX2 on KFCP.PT_MABLE_TEMP (MABLE_SORG, MABLE_SUNIT)");
		db.exeSQL("CREATE index KFCP.PT_MABLE_IDX3 on KFCP.PT_MABLE_TEMP (MABLE_CODE, MABLE_NAME, MABLE_UNIT)");
		
		db.exeSQL("delete UNTMP_MOVABLE");
		db.exeSQL("delete UNTMP_MOVABLEDETAIL");
		db.exeSQL("delete UNTMP_ADDPROOF");
	}

	SQL ="select ID \n"
	    +",DECODE((MABLE_SORG||MABLE_SUNIT),"
	    +"   '376470000A11', '000000011Z',"
        +"   MABLE_SORG"
        +" ) as enterOrg \n"      
    	+",MABLE_SORG as enterOrg \n"		
        +",DECODE(MABLE_RIGHT,"
        +"   '01','3',"
        +"	 '02','5',"
        +"	 '03','5',"
        +"	 '04','5',"
        +"	 '05','9',"
        +"	 '06','2',"
        +"	 '07','3',"
        +"	 '08','9',"
        +"	 '09','9',"
        +"	 '10','9',"
        +"	 '11','2',"
        +"	 '12','3',"
        +"	 ' '"
        +" ) as ownership \n"
        +",DECODE(MABLE_CAUSE,"
        +"   'N','04',"
        +"   'M','03',"
        +"   'A','01',"
        +"   'D','04',"
        +"   'E','03',"
        +"   'H','01',"
        +"   'I','01',"
        +"   'J','03',"
        +"   'K','04',"
        +"   'L','99',"
        +"   'B','02',"
        +"   'O','06',"
        +"   'P','10',"
        +"   'Q','90',"
        +"   'R','01',"
        +"   'S','99',"
        +"   'T','99',"
        +"   'U','99',"
        +"   'V','99',"
        +"   ' '"
        +" ) as cause \n"      
        +",DECODE(MABLE_CAUSE,"
        +"   'L', (select CODE_NAME from PT_CODE where CODE_TYPE='O' and CODE_NO='L'),"
        +"   'S', (select CODE_NAME from PT_CODE where CODE_TYPE='O' and CODE_NO='S'),"
        +"   'T', (select CODE_NAME from PT_CODE where CODE_TYPE='O' and CODE_NO='T'),"
        +"   'U', (select CODE_NAME from PT_CODE where CODE_TYPE='O' and CODE_NO='U'),"
        +"   'V', (select CODE_NAME from PT_CODE where CODE_TYPE='O' and CODE_NO='V'),"        
        +"   ' '"
        +" ) as cause1 \n"              
    	+",DECODE(MABLE_RARE,"
        +"   '1','Y',"
        +"	 '0','N',"
        +"	 'N'"
        +" ) as valuable \n"
        +",("
        +"   lpad(substr(nvl(MABLE_BDATE,'00001'),1,3)+nvl(MABLE_YEAR,0),3,'0')||"
        +"   lpad(substr(nvl(MABLE_BDATE,'00001'),4,2)-1,2,'0')"
        +" ) as useEndYM \n"
        +",("
        +"   lpad(substr(nvl(MABLE_BDATE,'00001'),1,3)+nvl(MABLE_YEAR,0),3,'0')||"
        +"   substr(nvl(MABLE_BDATE,'0000000'),4,4)"
        +" ) as permitReduceDate \n"       
        +",DECODE(MABLE_KIND,"
        +"   '01', '01',"
        +"   '02', '02',"
        +"   '05', '03',"
        +"   '00', '04',"
        +"   ' '"
        +" ) as propertyKind \n" 
        +",DECODE(MABLE_CAUSE,"
        +"   'N', '04',"
        +"   'M', '02',"
        +"   'A', '01',"
        +"   'D', '04',"
        +"   'E', '02',"
        +"   'H', '01',"
        +"   'I', '01',"
        +"   'J', '02',"
        +"   'K', '04',"
        +"   'L', '99',"
        +"   'B', '99',"
        +"   'O', '03',"
        +"   'P', '12',"
        +"   'Q', '90',"
        +"   'R', '01',"
        +"   'S', '13',"
        +"   'T', '05',"
        +"   'U', '10',"
        +"   'V', '99',"
        +"   ' '"
        +" ) as sourceKind \n" 
        +" ,MABLE_GDATE as moveDate \n"
        +" ,MABLE_BDATE as sourceDate \n"
        +" ,substrb(MABLE_STATUTE, 1, 40) as sourceDoc \n"      
        +" ,MABLE_OCODE as oldPropertyNo \n"
        +" ,substrb(MABLE_ONUM, 1, 7) as oldSerialNo \n" 
        +" ,substrb(MABLE_ONUM, 1, 7) as oldSerialNoS \n"
        +" ,substrb(MABLE_ONUM, 1, 7) as oldSerialNoE \n" 
        +" ,REPLACE(MABLE_CODE,'-','') as propertyNo \n"
		+" ,nvl(MABLE_NOTE,'') as notes \n"
		+" ,nvl(MABLE_BDATE,'0000000') as MABLE_BDATE \n"
		+" ,nvl(MABLE_SSTORE,' ') as MABLE_SSTORE \n"
		+" ,nvl(MABLE_OUNO,' ') as MABLE_OUNO \n"
		+" ,nvl(MABLE_OKEEP,' ') as MABLE_OKEEP \n"
		+" ,nvl(MABLE_OKNO,' ') as MABLE_OKNO \n"
		+" ,nvl(MABLE_UNO,' ') as MABLE_UNO \n"
		+" ,nvl(MABLE_KNO,' ') as MABLE_KNO \n"
		+" ,nvl(MABLE_PLACE,' ') as MABLE_PLACE \n"
		+" ,substrb(replace(MABLE_SPEC,'''',''), 1, 80) as specification \n"
		+" ,substrb(replace(MABLE_SPEC,'''',''), 81, 60) as nameplate \n"
		+" ,(select distinct docname from UNTMP_DOC where docno='D3') as proofDoc"
		+" ,MABLE_EDATE as enterDate"
		+" ,MABLE_KEEP,MABLE_SNAME,MABLE_SDATE,MABLE_RETAIN,MABLE_NUM,MABLE_AMOUNT,MABLE_PRICE,MABLE_VALUE \n"
		+" from PT_MABLE_TEMP A \n"
		+" where ID>="+ recNo_s +" and ID <="+ recNo_e +" \n"
		+" and MABLE_RIGHT in('01','11') \n"
		+"";
	ResultSet rs = db.querySQL(SQL);
   
    while(rs.next()) {
    	rowNo = rs.getInt("ID");  
    	//System.out.println(rowNo);
    	  	
    	//電腦單號
    	String caseNoDay = rs.getString("enterDate");
    	if(isValidDate(caseNoDay)) {
    		caseNoDay = caseNoDay.substring(0,5);
    	} else {
    		caseNoDay = ym;
    	}   	
    	sSQL1 = "select NVL(MAX(substr(CASENO,6,5)),0)+1 from UNTMP_MOVABLE WHERE 1 = 1"
    			+ " AND enterOrg = '"+ Common.esapi(rs.getString("enterOrg")) +"'"
    			+ " AND ownership = '"+ Common.esapi(rs.getString("ownership")) +"'"
    			+ " AND substr(CASENO,1,5) = '"+ Common.esapi(caseNoDay) +"'"
    			+ "";
    	maxCaseNo = db.getLookupField(sSQL1);
    	caseNo = caseNoDay + fullLeftZero(maxCaseNo,5);
    	
    	//財產批號
    	sSQL2 = "select NVL(MAX(lotNo),0)+1 from UNTMP_MOVABLE WHERE 1 = 1"
    			+ " AND enterOrg = '"+ Common.esapi(rs.getString("enterOrg")) +"'"
    			+ " AND ownership = '"+ Common.esapi(rs.getString("ownership")) +"'"
    			+ " AND propertyNo = '"+ Common.esapi(rs.getString("propertyNo")) +"'"   			
    			+ "";
    	String lotNo = fullLeftZero(db.getLookupField(sSQL2),7);
       	
    	//條碼
    	String code5 = cutString(rs.getString("ownership"),0,1) + cutString(rs.getString("MABLE_BDATE"),0,3) + cutString(rs.getString("propertyNo"),0,1);
    	sSQL3 = "select NVL(MAX(substr(barCode,7,6)),0)+1 from UNTMP_MOVABLEDETAIL WHERE 1 = 1"
			+ " AND enterOrg = '"+ Common.esapi(rs.getString("enterOrg")) +"'"
			+ " AND substr(barCode,0,5) = '"+ Common.esapi(code5) +"'"
			+ "";   	
    	String barCode = code5 + fullLeftZero(db.getLookupField(sSQL3),6);
    	  	
    	//增加單編號－號
    	sSQL4 = "select NVL(MAX(substr(proofNo,6,5)),0)+1 from UNTMP_ADDPROOF WHERE 1 = 1"
			+ " AND enterOrg = '"+ Common.esapi(rs.getString("enterOrg")) +"'"
			+ " AND ownership = '"+ Common.esapi(rs.getString("ownership")) +"'"
			+ " AND substr(proofNo,1,5) = '"+ ym +"'"
			+ "";
		String proofNo = ym + fullLeftZero(db.getLookupField(sSQL4),5);  	


    	//寫入動產主檔
    	iSQLm = "insert into UNTMP_MOVABLE("
		        	+" enterOrg        "
		        	+",ownership       "
		        	+",caseNo          "
		        	+",propertyNo      "
		        	+",lotNo           "
		        	+",serialNoS       "
		        	+",serialNoE       "
		        	+",cause           "
		        	+",cause1          "
		        	+",enterDate       "
		        	+",buyDate         "
		        	+",dataState       "
		        	+",verify          "
		        	+",closing         "
		        	+",propertyKind    "
		        	+",valuable        "
		        	+",originalAmount  "
		        	+",originalUnit    "
		        	+",originalBV      "		        	
		        	+",bookAmount      "
		        	+",bookValue       "		        	
		        	+",nameplate       "
		        	+",specification   "
		        	+",storeNo         "		        	
		        	+",sourceKind      "
		        	+",sourceDate      "
		        	+",sourceDoc       "
		        	+",deprMethod      "
		        	+",scrapValue      "
		        	+",useEndYM        "
		        	+",permitReduceDate"		        	
		        	+",oldPropertyNo   "
		        	+",oldSerialNoS    "
		        	+",oldSerialNoE    "		        	
		        	+",notes           "
		        	+",editID          "
		        	+",editDate        "        				
     		+") values(\n"
					+" '"+ Common.esapi(rs.getString("enterOrg"))			+"' --enterOrg         \n" 
					+",'"+ Common.esapi(rs.getString("ownership"))		+"' --ownership        \n" 
					+",'"+ Common.esapi(caseNo)                        +"' --caseNo           \n" 
					+",'"+ Common.esapi(rs.getString("propertyNo"))		+"' --propertyNo       \n" 
					+",'"+ Common.esapi(lotNo)							+"' --lotNo            \n" 
					+",'"+ Common.esapi(rs.getString("MABLE_NUM")) 		+"' --serialNoS        \n" 
					+",'"+ Common.esapi(rs.getString("MABLE_NUM")) 		+"' --serialNoE        \n"  
					+",'"+ Common.esapi(rs.getString("cause"))			+"' --cause            \n" 
					+",'"+ Common.esapi(rs.getString("cause1"))			+"' --cause1           \n"
					+",'"+ Common.esapi(rs.getString("enterDate")) 		+"' --enterDate        \n" 
					+",'"+ Common.esapi(rs.getString("MABLE_BDATE")) 		+"' --buyDate          \n" 
					+",'"+ Common.esapi("1")            				  	+"' --dataState        \n" 
					+",'"+ Common.esapi("Y")            			   	  	+"' --verify           \n" 
					+",'"+ Common.esapi("N")            				    +"' --closing          \n" 
					+",'"+ Common.esapi(rs.getString("propertyKind"))  	+"' --propertyKind     \n" 
					+",'"+ Common.esapi(rs.getString("valuable"))			+"' --valuable         \n" 
					+"," + Common.esapi(rs.getString("MABLE_AMOUNT")) 	+"  --originalAmount   \n" 
					+"," + Common.esapi(rs.getString("MABLE_PRICE")) 		+"  --originalUnit     \n" 
					+"," + Common.esapi(rs.getString("MABLE_VALUE")) 		+"  --originalBV       \n" 
					+"," + Common.esapi(rs.getString("MABLE_AMOUNT")) 	+"  --bookAmount       \n" 
					+"," + Common.esapi(rs.getString("MABLE_VALUE")) 		+"  --bookValue        \n" 					
					+",'" + Common.esapi(rs.getString("nameplate")) 		+"' --nameplate        \n"
					+",'" + Common.esapi(rs.getString("specification")) 	+"' --specification    \n" 
					+",'"+ Common.esapi(rs.getString("MABLE_SSTORE")) 	+"' --storeNo          \n"
					+",'"+ Common.esapi(rs.getString("sourceKind")) 		+"' --sourceKind       \n" 
					+",'"+ Common.esapi(rs.getString("sourceDate")) 		+"' --sourceDate       \n" 
					+",'"+ Common.esapi(rs.getString("sourceDoc")) 		+"' --sourceDoc        \n"
					+",'"+ Common.esapi("01")           				    +"' --deprMethod       \n"
					+"," + Common.esapi(rs.getString("MABLE_RETAIN")) 	+"  --scrapValue       \n" 
					+",'"+ Common.esapi(rs.getString("useEndYM"))			+"' --useEndYM         \n" 
					+",'"+ Common.esapi(rs.getString("permitReduceDate"))	+"' --permitReduceDate \n" 					
					+",'"+ Common.esapi(rs.getString("oldPropertyNo")) 	+"' --oldPropertyNo    \n"
					+",'"+ Common.esapi(rs.getString("oldSerialNoS")) 	+"' --oldSerialNoS     \n"
					+",'"+ Common.esapi(rs.getString("oldSerialNoE")) 	+"' --oldSerialNoE     \n"					
					+",'"+ Common.esapi(rs.getString("notes"))			+"' --notes            \n" 
					+",'"+ Common.esapi(rs.getString("MABLE_SNAME")) 		+"' --editID           \n" 
					+",'"+ Common.esapi(rs.getString("MABLE_SDATE")) 		+"' --editDate         \n" 
     		+")";	
    	//System.out.println(iSQLm);   	
    	db.exeSQL(iSQLm);
    	
		//寫入動產明細
    	iSQLd = "insert into UNTMP_MOVABLEDETAIL("
		        	+" enterOrg        "
		        	+",ownership       "
		        	+",propertyNo      "
		        	+",lotNo           "
		        	+",serialNo        "
		        	+",dataState       "
		        	+",verify          "
		        	+",closing         "
		        	+",originalAmount  "
		        	+",originalBV      "
		        	+",bookAmount      "
		        	+",bookValue       "        	
		        	+",originalKeepUnit"
		        	+",originalKeeper  "
		        	+",originalUseUnit "
		        	+",originalUser    "
		        	+",moveDate        "
		        	+",keepUnit        "
		        	+",keeper          "
		        	+",useUnit         "
		        	+",userNo          "
		        	+",place           "
		        	+",scrapValue      "
		        	+",notes           "
		        	+",oldPropertyNo   "
		        	+",oldSerialNo     "
		        	+",editID          "
		        	+",editDate        " 
		        	+",barCode         "
			+") values(\n"
					+" '"+ Common.esapi(rs.getString("enterOrg")) 	+"' --enterOrg         \n" 
					+",'"+ Common.esapi(rs.getString("ownership"))	+"' --ownership        \n" 
					+",'"+ Common.esapi(rs.getString("propertyNo"))	+"' --propertyNo       \n" 
					+",'"+ Common.esapi(lotNo)						+"' --lotNo            \n" 
					+",'"+ Common.esapi(rs.getString("MABLE_NUM")) 	+"' --serialNo         \n" 
					+",'"+ Common.esapi("1")            			+"' --dataState        \n" 		
					+",'"+ Common.esapi("Y")            			+"' --verify           \n" 
					+",'"+ Common.esapi("N")            			+"' --closing          \n" 
					+"," + Common.esapi(rs.getString("MABLE_AMOUNT")) +"  --originalAmount   \n" 
					+"," + Common.esapi(rs.getString("MABLE_VALUE"))  +"  --originalBV       \n" 
					+"," + Common.esapi(rs.getString("MABLE_AMOUNT")) +"  --bookAmount       \n" 
					+"," + Common.esapi(rs.getString("MABLE_VALUE"))  +"  --bookValue        \n" 
					+",'"+ Common.esapi(rs.getString("MABLE_OKNO"))   +"' --originalKeepUnit \n" 
					+",'"+ Common.esapi(rs.getString("MABLE_OKEEP"))  +"' --originalKeeper   \n" 
					+",'"+ Common.esapi(rs.getString("MABLE_OKNO"))   +"' --originalUseUnit  \n" 
					+",'"+ Common.esapi(rs.getString("MABLE_OKEEP"))  +"' --originalUser     \n"
					+",'"+ Common.esapi(rs.getString("moveDate"))	  +"' --moveDate         \n"
					+",'"+ Common.esapi(rs.getString("MABLE_KNO")) 	+"' --keepUnit 	       \n" 
					+",'"+ Common.esapi(rs.getString("MABLE_KEEP")) +"' --keeper           \n" 
					+",'"+ Common.esapi(rs.getString("MABLE_KNO")) 	+"' --useUnit          \n" 
					+",'"+ Common.esapi(rs.getString("MABLE_KEEP"))	+"' --userNo           \n"
					+",'"+ Common.esapi(rs.getString("MABLE_PLACE"))  +"' --place            \n" 
					+"," + Common.esapi(rs.getString("MABLE_RETAIN")) +"  --scrapValue       \n" 
					+",'"+ Common.esapi(rs.getString("notes"))		+"' --notes            \n"  
					+",'"+ Common.esapi(rs.getString("oldPropertyNo"))+"' --oldPropertyNo    \n" 
					+",'"+ Common.esapi(rs.getString("oldSerialNo")) 	+"' --oldSerialNo      \n" 
					+",'"+ Common.esapi(rs.getString("MABLE_SNAME")) 	+"' --editID           \n" 
					+",'"+ Common.esapi(rs.getString("MABLE_SDATE")) 	+"' --editDate         \n" 
					+",'"+ Common.esapi(barCode) 						+"' --barCode          \n"
			+")";
    	//System.out.println(iSQLd);   	
    	db.exeSQL(iSQLd);	
  	
		//寫入動產增加單
    	iSQLa = "insert into UNTMP_ADDPROOF("
		        	+" enterOrg        "
		        	+",ownership       "
		        	+",caseNo          "
		        	+",proofDoc        "
		        	+",proofNo         "
		        	+",enterDate       "
		        	+",verify          "
		        	+",closing         "
		        	+",notes           "
		        	+",editID          "
		        	+",editDate        "
			+") values(\n"
					+" '"+ Common.esapi(rs.getString("enterOrg")) 	+"' --enterOrg         \n" 
					+",'"+ Common.esapi(rs.getString("ownership"))	+"' --ownership        \n" 
					+",'"+ Common.esapi(caseNo)                         +"' --caseNo           \n" 
					+",'"+ Common.esapi(rs.getString("proofDoc"))		+"' --proofDoc         \n" 
					+",'"+ Common.esapi(proofNo)						+"' --proofNo          \n"
					+",'"+ Common.esapi(rs.getString("enterDate")) 	+"' --enterDate        \n"
					+",'"+ Common.esapi("Y")            			   	+"' --verify           \n" 
					+",'"+ Common.esapi("N")            				+"' --closing          \n" 					
					+",'"+ Common.esapi(rs.getString("notes"))		+"' --notes            \n"  
					+",'"+ Common.esapi(rs.getString("MABLE_SNAME")) 	+"' --editID           \n" 
					+",'"+ Common.esapi(rs.getString("MABLE_SDATE")) 	+"' --editDate         \n" 
			+")";
    	//System.out.println(iSQLa);   	
    	db.exeSQL(iSQLa);

    }//while end

} catch(Exception e) {
	/*
	System.out.println(">>rowNo = "+ rowNo);
	System.out.println("sSQL1 "+ sSQL1);
	System.out.println("maxCaseNo = "+ maxCaseNo);
	System.out.println("caseNo = "+ caseNo);

	System.out.println("sSQL2 "+ sSQL2);
	System.out.println("sSQL3 "+ sSQL3);
	System.out.println("sSQL4 "+ sSQL3);
	
	System.out.println("iSQLm "+ iSQLm);
	System.out.println("iSQLd "+ iSQLd);
	System.out.println("iSQLa "+ iSQLa);
	*/
	
    e.printStackTrace();
} finally {
    db.closeAll();
}
%>
