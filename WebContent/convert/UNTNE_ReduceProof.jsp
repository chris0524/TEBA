<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="java.sql.*"  %>
<%@ page import="util.Common" %>
<%@ page import="util.Database,util.Common"%>
<%@ include file="func.jsp"%>

<%
int rowNo = 0;
String SQL = "";
String cSQL = "";
String pSQL = "";
String iSQLm = "";
String lSQL = "";
String sSQL = "";
String sSQL1 = "";
String iSQLd = "";
String sSQL4 = "";
String iSQLa = "";
String uSQL2 = "";
String uSQL = "";
Database db = new Database();

String caseNo = "";
String maxCaseNo = "";
String ymd = util.Datetime.getYYYMMDD();
String ym = util.Datetime.getYYYMM();

try {	
	addcolumn(db,"UNTNE_ReduceProof","GDC_NO","VARCHAR2(12)");
	addcolumn(db,"UNTNE_ReduceProof","GDC_SORG","VARCHAR2(10)");
	addcolumn(db,"UNTNE_ReduceProof","GDC_SUNIT","VARCHAR2(4)");
	addcolumn(db,"UNTNE_ReduceProof","NONEXP_ID","VARCHAR2(10)");
	addcolumn(db,"UNTNE_AddProof","DATASTATE","VARCHAR2(1)");

	db.exeSQL("ALTER TABLE UNTNE_ReduceProof MODIFY approveDoc varchar2(50)");
	db.exeSQL("DELETE UNTNE_ReduceProof");
	db.exeSQL("DELETE UNTNE_Nonexp where DATASTATE='2'");
	db.exeSQL("DELETE UNTNE_AddProof where DATASTATE='2'");
	
	SQL ="select \n"
	    +" DECODE((GDC_SORG||GDC_SUNIT),"
	    +"   '376470000A11', '000000011Z',"
        +"   GDC_SORG"
        +" ) as enterOrg \n"      
        +",DECODE((select MAX(GDCD_RIGHT) from PT_GDCD where GDCD_NO=A.GDC_NO and GDCD_SORG=A.GDC_SORG and GDCD_SUNIT=A.GDC_SUNIT), \n"
        +"   '01','3', \n"
        +"   '02','5', \n"
        +"   '03','5', \n"
        +"   '04','5', \n"
        +"   '05','9', \n"
        +"   '06','2', \n"
        +"   '07','3', \n"
        +"   '08','9', \n"
        +"   '09','9', \n"
        +"   '10','9', \n"
        +"   '11','2', \n"
        +"   '12','3', \n"
        +"   ' ' \n"
        +" ) as ownership \n"
        +" ,GDC_NO,GDC_SORG,GDC_SUNIT,GDC_EDATE \n"
		+" ,(select distinct docname from UNTMP_Doc_Temp where docno='G2') AS proofDoc \n"
		+" ,GDC_RDATE,GDC_MCODE,GDC_STATUTE \n"
		+" ,DECODE(GDC_RDATE,NULL,'N','','N','Y') as verify \n"
		+" ,GDC_NOTE,GDC_SNAME,GDC_SDATE \n"
		
		//要更新物品明細的資料
		+" ,GDC_DDATE as reduceDate"
        +" ,DECODE(GDC_REASON,"
        +"   'B','02',"
        +"   'C','07',"
        +"   'F','08',"
        +"   'G','04',"
        +"   'J','99',"
        +"   'U','03',"
        +"   'D','99',"
        +"   'V','14',"
        +"   'O','99',"
        +"   ' '"        
        +" ) as reduceCause \n"              
        +" ,DECODE(GDC_REASON,"
        +"   'J', '轉減',"
        +"   'D', '病死',"
        +"   'O', '其他',"
        +"   ' '"
        +" ) as reduceCause1 \n"       
		+" from PT_GDC A \n"
		+"";
	ResultSet rs = db.querySQL(SQL);
	
    while(rs.next()) {
    	rowNo = rs.getRow();  
    	//System.out.println(rowNo);
    	
    	//電腦單號
    	String caseNoDay = rs.getString("GDC_EDATE");
    	if(isValidDate(caseNoDay)) {
    		caseNoDay = caseNoDay.substring(0,5);
    	} else {
    		caseNoDay = ym;
    	}   	
    	cSQL = "select NVL(MAX(substr(CASENO,6,5)),0)+1 from UNTNE_ReduceProof WHERE 1 = 1"
    			+ " AND enterOrg = '"+ Common.esapi(rs.getString("enterOrg")) +"'"
    			+ " AND ownership = '"+ Common.esapi(rs.getString("ownership")) +"'"
    			+ " AND substr(CASENO,1,5) = '"+ Common.esapi(caseNoDay) +"'"
    			+ "";
    	maxCaseNo = db.getLookupField(cSQL);
    	caseNo = caseNoDay + fullLeftZero(maxCaseNo,5); 
    	
    	//減損單編號－號
    	String proofNo = "";
    	pSQL = "select NVL(MAX(substr(proofNo,6,5)),0)+1 from UNTNE_ReduceProof WHERE 1 = 1"
			+ " AND enterOrg = '"+ Common.esapi(rs.getString("enterOrg")) +"'"
			+ " AND ownership = '"+ Common.esapi(rs.getString("ownership")) +"'"
			+ " AND substr(proofNo,1,5) = '"+ Common.esapi(ym) +"'"
			+ "";
		proofNo = ym + fullLeftZero(db.getLookupField(pSQL),5);  
			
		
    	//寫入物品減損主檔
    	iSQLm = "insert into UNTNE_ReduceProof("
		        	+" enterOrg    "
		        	+",ownership   "
		        	+",caseNo      "
		        	+",caseName    "
		        	+",writeDate   "
		        	+",proofDoc    "
		        	+",proofNo     "
		        	+",reduceDate  "
		        	+",approveOrg  "
		        	+",approveDoc  "
		        	+",verify      "
		        	+",closing     "
		        	+",notes       "
		        	+",editID      "
		        	+",editDate    " 
		        	+",GDC_NO      "
		        	+",GDC_SORG    "
		        	+",GDC_SUNIT   "		        	
     		+") values(\n"
					+" '"+ Common.esapi(rs.getString("enterOrg")) 	+"' --enterOrg    \n" 
					+",'"+ Common.esapi(rs.getString("ownership"))	+"' --ownership   \n" 
					+",'"+ Common.esapi(caseNo)                     +"' --caseNo      \n"
					+",'"+ Common.esapi(rs.getString("GDC_NO"))		+"' --caseName    \n"
					+",'"+ Common.esapi(rs.getString("GDC_EDATE"))	+"' --writeDate   \n"
					+",'"+ Common.esapi(rs.getString("proofDoc"))   +"' --proofDoc    \n" 
					+",'"+ Common.esapi(proofNo)		   			+"' --proofNo     \n"
					+",'"+ Common.esapi(rs.getString("GDC_RDATE")) 	+"' --reduceDate  \n"
					+",'"+ Common.esapi(rs.getString("GDC_MCODE"))   	+"' --approveOrg  \n" 
					+",'"+ Common.esapi(rs.getString("GDC_STATUTE")) 	+"' --approveDoc  \n"
					+",'"+ Common.esapi(rs.getString("verify"))   	+"' --verify      \n"
					+",'"+ "N"   									+"' --closing     \n"
					+",'"+ Common.esapi(rs.getString("GDC_NOTE"))	  +"' --notes       \n" 
					+",'"+ Common.esapi(rs.getString("GDC_SNAME"))    +"' --editID      \n" 
					+",'"+ Common.esapi(rs.getString("GDC_SDATE"))    +"' --editDate    \n" 
					+",'"+ Common.esapi(rs.getString("GDC_NO"))       +"' --GDC_NO      \n"
					+",'"+ Common.esapi(rs.getString("GDC_SORG"))     +"' --GDC_SORG    \n"
					+",'"+ Common.esapi(rs.getString("GDC_SUNIT"))    +"' --GDC_SUNIT   \n"					
     		+")";	 	
    	db.exeSQL(iSQLm);	

    	//如果更新主檔日期不為空
    	if("Y".equals(rs.getString("verify"))) {
        //if(false){	
    		//到減損明細檔抓相對資料
    		String propertyNo = " ";
    		String serialNoS = " ";
    		String serialNoE = " ";
    		String cause = "99";
    		String cause1 = "不明";
    		String enterDate = "";
    		String buyDate = " ";
    		String propertyKind = " ";
    		String valuable = "N";
    		String originalAmount = "0";
    		String originalUnit = "0";
    		String originalBV = "0";
    		String bookAmount = "0";
    		String bookValue = "0";
    		String permitReduceDate = " ";
    		String nameplate = "";
    		String specification = "";
    		String sourceDate = "";
    		
    		sSQL = "select replace(GDCD_CODE,'-','') as propertyNo"
    			+",nvl(GDCD_NUM,' ') as serialNoS"
    			+",nvl(GDCD_NUM,' ') as serialNoE"
    			+",nvl(GDCD_BDATE,' ') as buyDate"
    			+",nvl(GDCD_KIND,' ') as propertyKind"
    			+",nvl(GDCD_OLD,0) as originalAmount"
    			+",nvl(GDCD_PRICE,0) as originalUnit"
    			+",nvl(GDCD_VALUE,0) as originalBV"
    			+",substrb(replace(GDCD_SPEC,'''',''), 81, 60) as nameplate \n"
    			+",substrb(replace(GDCD_SPEC,'''',''), 1, 80) as specification \n"
    			+",nvl(GDCD_BDATE,' ') as sourceDate \n"
    			+",GDCD_EDATE as enterDate \n"
    			
    			+" from PT_GDCD WHERE 1 = 1"
    			+" AND GDCD_CHK='1' "
    			+" AND GDCD_NO = '"+ Common.esapi(rs.getString("GDC_NO")) +"'"
    			+" AND GDCD_SORG = '"+ Common.esapi(rs.getString("GDC_SORG")) +"'"
    			+" AND GDCD_SUNIT = '"+ Common.esapi(rs.getString("GDC_SUNIT")) +"'"
    			+"";		

    		Statement sStmt = db.getConnection().createStatement(ResultSet.TYPE_FORWARD_ONLY,ResultSet.CONCUR_READ_ONLY);
    		ResultSet sRs = sStmt.executeQuery(sSQL);
    		if(sRs.next()) {
    			propertyNo = sRs.getString("propertyNo");
    			serialNoS = sRs.getString("serialNoS");
    			serialNoE = sRs.getString("serialNoE");
    			buyDate = sRs.getString("buyDate");
    			propertyKind = sRs.getString("propertyKind");
    			originalAmount = sRs.getString("originalAmount");
    			originalUnit = sRs.getString("originalUnit");
    			originalBV = sRs.getString("originalBV");
    			nameplate = sRs.getString("nameplate");
    			specification = sRs.getString("specification");
    			sourceDate = sRs.getString("sourceDate");
    			enterDate = sRs.getString("enterDate");
    			
    			if(isValidDate(buyDate)) {
    				int bdate_y = toInt(buyDate.substring(0,3));
    				int bdate_m = toInt(buyDate.substring(3,5));
    				int bdate_d = toInt(buyDate.substring(5,7));
    				sSQL1 = "select nvl(limitYear,0) from SYSPK_PropertyCode "
            	        +" where enterOrg='"+ Common.esapi(rs.getString("enterOrg")) +"'"
            	        +" and propertyNo='"+ Common.esapi(propertyNo) +"'";
        			int limitYear = toInt(db.getLookupField(sSQL1));      			       
    				
    				permitReduceDate = fullLeftZero(""+(bdate_y+limitYear),3) + fullLeftZero(""+(bdate_m),2) + fullLeftZero(""+(bdate_d),2);
    			}    			
    		}
    		sRs.close();
    		sStmt.close();        	
        	
        	//電腦單號 	
        	cSQL = "select NVL(MAX(substr(CASENO,6,5)),0)+1 from UNTNE_NONEXP WHERE 1 = 1"
        			+ " AND enterOrg = '"+ Common.esapi(rs.getString("enterOrg")) +"'"
        			+ " AND ownership = '"+ Common.esapi(rs.getString("ownership")) +"'"
        			+ " AND substr(CASENO,1,5) = '"+ Common.esapi(caseNoDay) +"'"
        			+ "";
        	maxCaseNo = db.getLookupField(cSQL);
        	caseNo = caseNoDay + fullLeftZero(maxCaseNo,5);        	
        	
    		//財產批號
        	lSQL = "select NVL(MAX(lotNo),0)+1 from UNTNE_NONEXP WHERE 1 = 1"
        			+ " AND enterOrg = '"+ Common.esapi(rs.getString("enterOrg")) +"'"
        			+ " AND ownership = '"+ Common.esapi(rs.getString("ownership")) +"'"
        			+ " AND propertyNo = '"+ Common.esapi(propertyNo) +"'"   			
        			+ "";
        	String lotNo = fullLeftZero(db.getLookupField(lSQL),7);
        	
        	
        	//回寫物品主檔
    		iSQLd = "insert into UNTNE_NONEXP("
	        	+" enterOrg    "
	        	+",ownership   "
	        	+",caseNo      " 
	        	+",propertyNo  "
	        	+",lotNo       "
	        	+",serialNoS   "
	        	+",serialNoE   "
	        	+",cause       "
	        	+",cause1      "
	        	+",enterDate   "
	        	+",buyDate     "
	        	+",dataState   "
	        	+",verify      "
	        	+",closing     "
	        	+",propertyKind"
	        	+",valuable    "
	        	+",originalAmount"
	        	+",originalUnit"
	        	+",originalBV  "
	        	+",bookAmount  "
	        	+",bookValue   "
	        	+",nameplate   "
	        	+",specification"
	        	+",sourceDate  "
	        	+",permitReduceDate  "
	        	+",notes       "
	        	+",editID      "
	        	+",editDate    " 	        	
	     		+") values(\n"
				+" '"+ Common.esapi(rs.getString("enterOrg")) 	+"' --enterOrg    \n" 
				+",'"+ Common.esapi(rs.getString("ownership"))	+"' --ownership   \n" 
				+",'"+ Common.esapi(caseNo)                     +"' --caseNo      \n"
				+",'"+ Common.esapi(propertyNo)                 +"' --propertyNo  \n"
				+",'"+ Common.esapi(lotNo)	                    +"' --lotNo       \n"
				+",'"+ Common.esapi(serialNoS)                  +"' --serialNoS   \n"
				+",'"+ Common.esapi(serialNoE)                  +"' --serialNoE   \n"
				+",'"+ Common.esapi(cause)	                    +"' --cause       \n"
				+",'"+ Common.esapi(cause1)	                    +"' --cause1      \n"
				+",'"+ Common.esapi(enterDate)                  +"' --enterDate   \n"
				+",'"+ Common.esapi(buyDate)                   	+"' --buyDate     \n"
				+",'"+ "2"                   					+"' --dataState   \n"
				+",'"+ "Y"					 					+"' --verify      \n"
				+",'"+ "N"                   					+"' --closing     \n"
				+",'"+ Common.esapi(propertyKind)               +"' --propertyKind\n"
				+",'"+ Common.esapi(valuable)	                +"' --valuable    \n"
				+", "+ Common.esapi(originalAmount)	           	+"  --originalAmount    \n"
				+", "+ Common.esapi(originalUnit)	            +"  --originalUnit\n"
				+", "+ Common.esapi(originalBV)	               	+"  --originalBV  \n"
				+", "+ Common.esapi(bookAmount)	               	+"  --bookAmount  \n"
				+", "+ Common.esapi(bookValue)	               	+"  --bookValue   \n"
				+",'"+ Common.esapi(nameplate)	               	+"' --nameplate   \n"
				+",'"+ Common.esapi(specification )             +"' --specification\n"
				+",'"+ Common.esapi(sourceDate)	               	+"' --sourceDate  \n"
				+",'"+ Common.esapi(permitReduceDate)	        +"' --permitReduceDate  \n"
				+",'"+ Common.esapi(rs.getString("GDC_NOTE"))   +"' --notes       \n" 
				+",'"+ Common.esapi(rs.getString("GDC_SNAME"))  +"' --editID      \n" 
				+",'"+ Common.esapi(rs.getString("GDC_SDATE"))  +"' --editDate    \n" 				
	     		+")";	 	
        	db.exeSQL(iSQLd);

        	String proofDoc = db.getLookupField("select distinct docname from UNTMP_DOC where docno='G1'");
        	
        	//增加單編號－號
        	sSQL4 = "select NVL(MAX(substr(proofNo,6,5)),0)+1 from UNTNE_ADDPROOF WHERE 1 = 1"
    			+ " AND enterOrg = '"+ Common.esapi(rs.getString("enterOrg")) +"'"
    			+ " AND ownership = '"+ Common.esapi(rs.getString("ownership")) +"'"
    			+ " AND substr(proofNo,1,5) = '"+ Common.esapi(ym) +"'"
    			+ "";
    		proofNo = ym + fullLeftZero(db.getLookupField(sSQL4),5);  	
    		
        	//回寫動產增加單
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
    		        	+",DATASTATE       "
    			+") values(\n"
    					+" '"+ Common.esapi(rs.getString("enterOrg")) 	+"' --enterOrg         \n" 
    					+",'"+ Common.esapi(rs.getString("ownership"))	+"' --ownership        \n" 
    					+",'"+ Common.esapi(caseNo)                     +"' --caseNo           \n" 
    					+",'"+ Common.esapi(proofDoc)				    +"' --proofDoc         \n" 
    					+",'"+ Common.esapi(proofNo)					+"' --proofNo          \n"
    					+",'"+ Common.esapi(enterDate) 					+"' --enterDate        \n"
    					+",'"+ "Y"            			   				+"' --verify           \n" 
    					+",'"+ "N"            							+"' --closing          \n" 					
    					+",'"+ Common.esapi(rs.getString("GDC_NOTE"))	+"' --notes            \n"  
    					+",'"+ Common.esapi(rs.getString("GDC_SNAME")) 	+"' --editID           \n" 
    					+",'"+ Common.esapi(rs.getString("GDC_SDATE")) 	+"' --editDate         \n" 
    					+",'"+ "2" 										+"' --DATASTATE        \n" 
    			+")";
        	//System.out.println(iSQLa);   	
        	db.exeSQL(iSQLa);
        	
        	
        	//更新減損主檔的 回寫物品主檔電腦單號
        	uSQL2 = "UPDATE UNTNE_ReduceProof SET NONEXP_ID='"+Common.esapi(caseNo)+"' where 1=1"
    			+" AND GDC_NO = '"+ Common.esapi(rs.getString("GDC_NO")) +"'"
    			+" AND GDC_SORG = '"+ Common.esapi(rs.getString("GDC_SORG")) +"'"
    			+" AND GDC_SUNIT = '"+ Common.esapi(rs.getString("GDC_SUNIT")) +"'";     	
        	db.exeSQL(uSQL2); 
        	
        	
        	//更新物品明細檔
			uSQL = "update UNTNE_NONEXPDetail set "
			+"  reduceDate = '"+ Common.esapi(rs.getString("reduceDate")) +"'"
			+"  ,reduceCause = '"+ Common.esapi(rs.getString("reduceCause")) +"'"
			+"  ,reduceCause1 = '"+ Common.esapi(rs.getString("reduceCause1")) +"'"
			+" where 1=1 "
			+" and enterOrg='"+ Common.esapi(rs.getString("enterOrg")) +"'"
			+" and ownership='"+ Common.esapi(rs.getString("ownership")) +"'"
			+" and propertyNo='"+ Common.esapi(propertyNo) +"'"
			+" and serialNo='"+ Common.esapi(serialNoS) +"'"
			+"";
			db.exeSQL(uSQL); 
    	}

    }//while end
} catch(Exception e) {
	/*
	System.out.println(">>rowNo = "+ rowNo);
	System.out.println("/SQL/ "+ SQL);
	System.out.println("/cSQL/ "+ cSQL);
	System.out.println("maxCaseNo = "+ maxCaseNo);
	System.out.println("caseNo = "+ caseNo);
	System.out.println("/pSQL/ "+ pSQL);
	System.out.println("/iSQLm/ "+ iSQLm);
	
	System.out.println("/lSQL/ "+ lSQL);
	System.out.println("/sSQL/ "+ sSQL);
	System.out.println("/iSQLd/ "+ iSQLd);
	System.out.println("/sSQL4/ "+ sSQL4);
	System.out.println("/iSQLa/ "+ iSQLa);
	System.out.println("/uSQL2/ "+ uSQL2);
	System.out.println("/uSQL/ "+ uSQL);
	*/
    e.printStackTrace();
    out.println(e);
} finally {
    db.closeAll();
}
%>
