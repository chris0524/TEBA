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
String sSQL = "";
String sSQL1 = "";

Database db = new Database();

String caseNo = "";
String maxCaseNo = "";
String ymd = util.Datetime.getYYYMMDD();
String ym = util.Datetime.getYYYMM();

try {	
	if(recNo_s == 1) {
		dropTable(db,"PT_GOOD_TEMP");
		db.exeSQL("CREATE TABLE PT_GOOD_TEMP AS(SELECT ROWNUM as id,A.* FROM PT_GOOD A)");
		db.exeSQL("CREATE unique index KFCP.PT_GOOD_IDX1 on KFCP.PT_GOOD_TEMP (GOOD_CODE, GOOD_NUM, GOOD_SORG, GOOD_SUNIT)");
		db.exeSQL("CREATE index KFCP.PT_GOOD_IDX2 on KFCP.PT_GOOD_TEMP (GOOD_SORG, GOOD_SUNIT)");
		db.exeSQL("CREATE index KFCP.PT_GOOD_IDX3 on KFCP.PT_GOOD_TEMP (GOOD_CODE, GOOD_NAME, GOOD_UNIT)");

		db.exeSQL("UPDATE PT_GOOD_TEMP SET GOOD_BDATE=REPLACE(GOOD_BDATE,'*','0')");
		
		db.exeSQL("DELETE UNTNE_NONEXP");
		db.exeSQL("DELETE UNTNE_NONEXPDETAIL");
		db.exeSQL("DELETE UNTNE_ADDPROOF");
	}
	
	SQL ="select ID \n"
	    +",DECODE((GOOD_SORG||GOOD_SUNIT),"
	    +"   '376470000A11', '000000011Z',"
        +"   GOOD_SORG"
        +" ) as enterOrg \n"      
        +",DECODE(GOOD_RIGHT,"
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
        +",DECODE(GOOD_CAUSE,"
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
        +",DECODE(GOOD_CAUSE,"
        +"   'L', (select CODE_NAME from PT_CODE where CODE_TYPE='O' and CODE_NO='L'),"
        +"   'S', (select CODE_NAME from PT_CODE where CODE_TYPE='O' and CODE_NO='S'),"
        +"   'T', (select CODE_NAME from PT_CODE where CODE_TYPE='O' and CODE_NO='T'),"
        +"   'U', (select CODE_NAME from PT_CODE where CODE_TYPE='O' and CODE_NO='U'),"
        +"   'V', (select CODE_NAME from PT_CODE where CODE_TYPE='O' and CODE_NO='V'),"        
        +"   ' '"
        +" ) as cause1 \n"              
    	+",DECODE(GOOD_RARE,"
        +"   '1','Y',"
        +"	 '0','N',"
        +"	 'N'"
        +" ) as valuable \n"
        +",("
        +"   lpad(substr(nvl(GOOD_BDATE,'00001'),1,3)+nvl(GOOD_YEAR,0),3,'0')||"
        +"   substr(nvl(GOOD_BDATE,'0000000'),4,4)"
        +" ) as permitReduceDate \n"               
        +",DECODE(GOOD_KIND,"
        +"   '01', '01',"
        +"   '02', '02',"
        +"   '05', '03',"
        +"   '00', '04',"
        +"   ' '"
        +" ) as propertyKind \n" 
        +",DECODE(GOOD_CAUSE,"
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
        +" ,GOOD_GDATE as moveDate \n"
        +" ,GOOD_BDATE as sourceDate \n"
        +" ,substrb(GOOD_STATUTE, 1, 40) as sourceDoc \n"      
        +" ,GOOD_OCODE as oldPropertyNo \n"
        +" ,substrb(GOOD_ONUM, 1, 7) as oldSerialNo \n" 
        +" ,substrb(GOOD_ONUM, 1, 7) as oldSerialNoS \n"
        +" ,substrb(GOOD_ONUM, 1, 7) as oldSerialNoE \n" 
        +" ,DECODE(substr(GOOD_CODE,0,1), \n"
        +"   6,REPLACE(GOOD_CODE,'-',''), \n"
        +"   '6'||REPLACE(GOOD_CODE,'-','') \n"
        +" 	) as propertyNo \n"
		+" ,nvl(GOOD_NOTE,'') as notes \n"
		+" ,nvl(GOOD_BDATE,'0000000') as GOOD_BDATE \n"
		+" ,nvl(GOOD_SSTORE,' ') as GOOD_SSTORE \n"
		+" ,nvl(GOOD_OUNO,' ') as GOOD_OUNO \n"
		+" ,nvl(GOOD_OKEEP,' ') as GOOD_OKEEP \n"
		+" ,nvl(GOOD_OKNO,' ') as GOOD_OKNO \n"
		+" ,nvl(GOOD_UNO,' ') as GOOD_UNO \n"
		+" ,nvl(GOOD_KNO,' ') as GOOD_KNO \n"
		+" ,nvl(GOOD_PLACE,' ') as GOOD_PLACE \n"
		+" ,substrb(replace(GOOD_SPEC,'''',''), 1, 80) as specification \n"
		+" ,substrb(replace(GOOD_SPEC,'''',''), 81, 60) as nameplate \n"
		+" ,(select distinct docname from UNTMP_DOC where docno='G1') as proofDoc"
		+" ,GOOD_KEEP,GOOD_SNAME,GOOD_SDATE,GOOD_NUM,GOOD_EDATE,GOOD_AMOUNT,GOOD_PRICE,GOOD_VALUE \n"
		+" from PT_GOOD_TEMP A \n"
		+" where ID>="+ recNo_s +" and ID <="+ recNo_e +" \n"
		+" and GOOD_RIGHT in('01','11') \n"
		+"";
	ResultSet rs = db.querySQL(SQL);
    
    while(rs.next()) {
    	rowNo = rs.getInt("ID");  
    	//System.out.println(rowNo);
    	
    	//電腦單號
    	String caseNoDay = rs.getString("GOOD_EDATE");
    	if(isValidDate(caseNoDay)) {
    		caseNoDay = caseNoDay.substring(0,5);
    	} else {
    		caseNoDay = ym;
    	}   	
    	sSQL1 = "select NVL(MAX(substr(CASENO,6,5)),0)+1 from UNTNE_NONEXP WHERE 1 = 1"
    			+ " AND enterOrg = '"+ Common.esapi(rs.getString("enterOrg")) +"'"
    			+ " AND ownership = '"+ Common.esapi(rs.getString("ownership")) +"'"
    			+ " AND substr(CASENO,1,5) = '"+ Common.esapi(caseNoDay) +"'"
    			+ "";
    	maxCaseNo = db.getLookupField(sSQL1);
    	caseNo = caseNoDay + fullLeftZero(maxCaseNo,5);
    	
    	//財產批號
    	sSQL = "select NVL(MAX(lotNo),0)+1 from UNTNE_NONEXP WHERE 1 = 1"
    			+ " AND enterOrg = '"+ Common.esapi(rs.getString("enterOrg")) +"'"
    			+ " AND ownership = '"+ Common.esapi(rs.getString("ownership")) +"'"
    			+ " AND propertyNo = '"+ Common.esapi(rs.getString("propertyNo")) +"'"   			
    			+ "";
    	String lotNo = fullLeftZero(db.getLookupField(sSQL),7);
       	
    	//條碼
    	String code5 = cutString(rs.getString("ownership"),0,1) + cutString(rs.getString("GOOD_BDATE"),0,3) + cutString(rs.getString("propertyNo"),0,1);
    	sSQL = "select NVL(MAX(substr(barCode,7,6)),0)+1 from UNTNE_NONEXPDETAIL WHERE 1 = 1"
			+ " AND enterOrg = '"+ Common.esapi(rs.getString("enterOrg")) +"'"
			+ " AND substr(barCode,0,5) = '"+ Common.esapi(code5) +"'"
			+ "";   	
    	String barCode = code5 + fullLeftZero(db.getLookupField(sSQL),6);
    	
    	
    	//增加單編號－號
    	sSQL = "select NVL(MAX(substr(proofNo,6,5)),0)+1 from UNTNE_ADDPROOF WHERE 1 = 1"
			+ " AND enterOrg = '"+ Common.esapi(rs.getString("enterOrg")) +"'"
			+ " AND ownership = '"+ Common.esapi(rs.getString("ownership")) +"'"
			+ " AND substr(proofNo,1,5) = '"+ ym +"'"
			+ "";
		String proofNo = ym + fullLeftZero(db.getLookupField(sSQL),5);  	


    	//寫入物品主檔
    	iSQLm = "insert into UNTNE_NONEXP("
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
					+",'"+ Common.esapi(caseNo)                           +"' --caseNo           \n" 
					+",'"+ Common.esapi(rs.getString("propertyNo"))		+"' --propertyNo       \n" 
					+",'"+ Common.esapi(lotNo)							+"' --lotNo            \n" 
					+",'"+ Common.esapi(rs.getString("GOOD_NUM")) 		+"' --serialNoS        \n" 
					+",'"+ Common.esapi(rs.getString("GOOD_NUM")) 		+"' --serialNoE        \n"  
					+",'"+ Common.esapi(rs.getString("cause"))			+"' --cause            \n" 
					+",'"+ Common.esapi(rs.getString("cause1"))			+"' --cause1           \n"
					+",'"+ Common.esapi(rs.getString("GOOD_EDATE")) 		+"' --enterDate        \n" 
					+",'"+ Common.esapi(rs.getString("GOOD_BDATE")) 		+"' --buyDate          \n" 
					+",'"+ Common.esapi("1")            				  	+"' --dataState        \n" 
					+",'"+ Common.esapi("Y")            			   	  	+"' --verify           \n" 
					+",'"+ Common.esapi("N")            				    +"' --closing          \n" 
					+",'"+ Common.esapi(rs.getString("propertyKind"))  	+"' --propertyKind     \n" 
					+",'"+ Common.esapi(rs.getString("valuable"))			+"' --valuable         \n" 
					+"," + Common.esapi(rs.getString("GOOD_AMOUNT")) 		+"  --originalAmount   \n" 
					+"," + Common.esapi(rs.getString("GOOD_PRICE")) 		+"  --originalUnit     \n" 
					+"," + Common.esapi(rs.getString("GOOD_VALUE")) 		+"  --originalBV       \n" 
					+"," + Common.esapi(rs.getString("GOOD_AMOUNT")) 		+"  --bookAmount       \n" 
					+"," + Common.esapi(rs.getString("GOOD_VALUE")) 		+"  --bookValue        \n" 					
					+",'" + Common.esapi(rs.getString("nameplate")) 		+"' --nameplate        \n"
					+",'" + Common.esapi(rs.getString("specification")) 	+"' --specification    \n" 
					+",'"+ Common.esapi(rs.getString("GOOD_SSTORE")) 		+"' --storeNo          \n"
					+",'"+ Common.esapi(rs.getString("sourceKind")) 		+"' --sourceKind       \n" 
					+",'"+ Common.esapi(rs.getString("sourceDate")) 		+"' --sourceDate       \n" 
					+",'"+ Common.esapi(rs.getString("sourceDoc")) 		+"' --sourceDoc        \n"
					+",'"+ Common.esapi(rs.getString("permitReduceDate"))	+"' --permitReduceDate \n"
					+",'"+ Common.esapi(rs.getString("oldPropertyNo")) 	+"' --oldPropertyNo    \n"
					+",'"+ Common.esapi(rs.getString("oldSerialNoS")) 	+"' --oldSerialNoS     \n"
					+",'"+ Common.esapi(rs.getString("oldSerialNoE")) 	+"' --oldSerialNoE     \n"					
					+",'"+ Common.esapi(rs.getString("notes"))			+"' --notes            \n" 
					+",'"+ Common.esapi(rs.getString("GOOD_SNAME")) 		+"' --editID           \n" 
					+",'"+ Common.esapi(rs.getString("GOOD_SDATE")) 		+"' --editDate         \n" 
     		+")";	
    	//System.out.println(iSQLm);   	
    	db.exeSQL(iSQLm);
   	
		//寫入物品明細
    	iSQLd = "insert into UNTNE_NONEXPDETAIL("
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
					+",'"+ Common.esapi(rs.getString("GOOD_NUM")) 	+"' --serialNo         \n" 
					+",'"+ Common.esapi("1")            				+"' --dataState        \n" 		
					+",'"+ Common.esapi("Y")            			   	+"' --verify           \n" 
					+",'"+ Common.esapi("N")            				+"' --closing          \n" 
					+"," + Common.esapi(rs.getString("GOOD_AMOUNT"))  +"  --originalAmount   \n" 
					+"," + Common.esapi(rs.getString("GOOD_VALUE"))   +"  --originalBV       \n" 
					+"," + Common.esapi(rs.getString("GOOD_AMOUNT"))  +"  --bookAmount       \n" 
					+"," + Common.esapi(rs.getString("GOOD_VALUE"))   +"  --bookValue        \n" 
					+",'"+ Common.esapi(rs.getString("GOOD_OKNO")) 	+"' --originalKeepUnit \n" 
					+",'"+ Common.esapi(rs.getString("GOOD_OKEEP")) +"' --originalKeeper   \n" 
					+",'"+ Common.esapi(rs.getString("GOOD_OKNO")) 	+"' --originalUseUnit  \n" 
					+",'"+ Common.esapi(rs.getString("GOOD_OKEEP"))	+"' --originalUser     \n"
					+",'"+ Common.esapi(rs.getString("moveDate"))	+"' --moveDate         \n"
					+",'"+ Common.esapi(rs.getString("GOOD_KNO")) 	+"' --keepUnit 	       \n" 
					+",'"+ Common.esapi(rs.getString("GOOD_KEEP")) 	+"' --keeper           \n" 
					+",'"+ Common.esapi(rs.getString("GOOD_KNO")) 	+"' --useUnit          \n" 
					+",'"+ Common.esapi(rs.getString("GOOD_KEEP"))	+"' --userNo           \n"
					+",'"+ Common.esapi(rs.getString("GOOD_PLACE")) +"' --place            \n" 
					+",'"+ Common.esapi(rs.getString("notes"))		+"' --notes            \n"  
					+",'"+ Common.esapi(rs.getString("oldPropertyNo"))+"' --oldPropertyNo    \n" 
					+",'"+ Common.esapi(rs.getString("oldSerialNo")) 	+"' --oldSerialNo      \n" 
					+",'"+ Common.esapi(rs.getString("GOOD_SNAME")) 	+"' --editID           \n" 
					+",'"+ Common.esapi(rs.getString("GOOD_SDATE")) 	+"' --editDate         \n" 
					+",'"+ Common.esapi(barCode) 						+"' --barCode          \n"
			+")";
    	//System.out.println(iSQLd);   	
    	db.exeSQL(iSQLd);	
    	
		//寫入物品增加單
    	iSQLa = "insert into UNTNE_ADDPROOF("
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
					+",'"+ Common.esapi(rs.getString("GOOD_EDATE")) 	+"' --enterDate        \n"
					+",'"+ Common.esapi("Y")            			   	+"' --verify           \n" 
					+",'"+ Common.esapi("N")            				+"' --closing          \n" 					
					+",'"+ Common.esapi(rs.getString("notes"))		+"' --notes            \n"  
					+",'"+ Common.esapi(rs.getString("GOOD_SNAME")) 	+"' --editID           \n" 
					+",'"+ Common.esapi(rs.getString("GOOD_SDATE")) 	+"' --editDate         \n" 
			+")";
    	//System.out.println(iSQLa);   	
    	db.exeSQL(iSQLa);
/*    	
    	iSQLm = null;
    	iSQLd = null;
    	iSQLa = null;
    	sSQL = null;    	
    	lotNo = null;
    	caseNo = null;
    	maxCaseNo = null;
    	code5 = null;
    	barCode = null;
*/
    }//while end
} catch(Exception e) {
	/*
	System.out.println(">>rowNo = "+ rowNo);
	System.out.println("SQL = "+ SQL);
	System.out.println("sSQL1 = "+ sSQL1);	
	System.out.println("sSQL = "+ sSQL);
	System.out.println("maxCaseNo = "+ maxCaseNo);
	System.out.println("caseNo = "+ caseNo);
	System.out.println("iSQLm = "+ iSQLm);
	System.out.println("iSQLd = "+ iSQLd);
	System.out.println("iSQLa = "+ iSQLa);
	*/
    e.printStackTrace();
} finally {
    db.closeAll();
}
%>
