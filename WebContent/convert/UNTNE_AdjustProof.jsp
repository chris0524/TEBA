<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="java.sql.*"  %>
<%@ page import="util.Common" %>
<%@ page import="util.Database"%>
<%@ include file="func.jsp"%>

<%
int rowNo = 0;
String SQL = "";
String iSQLm = "";
String iSQLd = "";
String sSQL = "";
String cSQL = "";
String pSQL = "";
String uSQL = "";
Database db = new Database();

String caseNo = "";
String maxCaseNo = "";
String ymd = util.Datetime.getYYYMMDD();
String ym = util.Datetime.getYYYMM();

try {	
	db.exeSQL("ALTER TABLE UNTNE_ADJUSTPROOF MODIFY approveDoc varchar2(50)");	
	db.exeSQL("DELETE UNTNE_ADJUSTPROOF");
	db.exeSQL("DELETE UNTNE_AdjustDetail");	
	
	SQL ="select \n"
	    +" DECODE((GRD_SORG||GRD_SUNIT),"
	    +"   '376470000A11', '000000011Z',"
        +"   GRD_SORG"
        +" ) as enterOrg \n"      
    	+",GRD_SORG as enterOrg \n"		
        +",DECODE(GRD_RIGHT,"
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
        +" ,GRD_EDATE \n"
        +" ,(select distinct docname from UNTMP_Doc_Temp where docno='G3') AS proofDoc \n"
		+" ,GRD_RDATE \n"
		+" ,nvl(GRD_GOV,' ') as GRD_GOV \n"
		+" ,GRD_STATU \n"
		+" ,DECODE(GRD_RDATE,NULL,'N','','N','Y') as verify \n"
		+" ,(GRD_NOTE||'；增減值編號：'||GRD_NO) as notes \n"
		+" ,GRD_SNAME,GRD_SDATE \n"
		
		+" --------以下為明細檔資料--------------------- \n"
		+" ,REPLACE(GRD_CODE,'-','') as propertyNo \n"
		+" ,GRD_NUM \n"
		+" ,'不明' as cause \n"
        +",DECODE(GRD_KIND,"
        +"   '01', '01',"
        +"   '02', '02',"
        +"   '05', '03',"
        +"   '00', '04',"
        +"   ' '"
        +" ) as propertyKind \n" 
        +" ,GRD_BDATE \n"
        +" ,nvl(GRD_AMOUNT,0) as bookAmount \n"
        +" ,nvl(GRD_OPRICE,0) as oldBookUnit \n"
        +" ,nvl(GRD_OVALUE,0) as oldBookValue \n"
        +" ,decode( SIGN(nvl(GRD_PRICE,1)),"
        +"          -1,2,"
        +"          1"
        +")as adjustType \n"
        +" ,ABS(nvl(GRD_PRICE,0)) as adjustBookUnit \n"
        +" ,ABS(nvl(GRD_PRICE,0)) as adjustBookValue \n"
        +" ,nvl(GRD_VALUE, nvl(GRD_OVALUE,0)) as newBookUnit \n"
        +" ,nvl(GRD_VALUE, nvl(GRD_OVALUE,0)) as newBookValue \n"
        +" ,nvl(GRD_PRICE,0) as GRD_PRICE \n"
		+" from PT_GRD A \n"
		+" where GRD_CHK = '1' \n"
		//+" and GRD_RIGHT in('01','11') \n"
		+"";
	ResultSet rs = db.querySQL(SQL);
	
    while(rs.next()) {
    	rowNo = rs.getRow();  
    	//System.out.println(rowNo);
    	  	
    	//電腦單號  	
    	String caseNoDay = rs.getString("GRD_EDATE");
    	if(isValidDate(caseNoDay)) {
    		caseNoDay = caseNoDay.substring(0,5);
    	} else {
    		caseNoDay = ym;
    	}   	
    	cSQL = "select NVL(MAX(substr(CASENO,6,5)),0)+1 from UNTNE_ADJUSTPROOF WHERE 1 = 1"
    			+ " AND enterOrg = '"+ Common.esapi(rs.getString("enterOrg")) +"'"
    			+ " AND ownership = '"+ Common.esapi(rs.getString("ownership")) +"'"
    			+ " AND substr(CASENO,1,5) = '"+ Common.esapi(caseNoDay) +"'"
    			+ "";
    	maxCaseNo = db.getLookupField(cSQL);
    	caseNo = caseNoDay + fullLeftZero(maxCaseNo,5);     	
    	    	
    	//增減值單編號－號
    	String proofNo = "";
    	pSQL = "select NVL(MAX(substr(proofNo,6,5)),0)+1 from UNTNE_ADJUSTPROOF WHERE 1 = 1"
			+ " AND enterOrg = '"+ Common.esapi(rs.getString("enterOrg")) +"'"
			+ " AND ownership = '"+ Common.esapi(rs.getString("ownership")) +"'"
			+ " AND substr(proofNo,1,5) = '"+ Common.esapi(ym) +"'"
			+ "";
		proofNo = ym + fullLeftZero(db.getLookupField(pSQL),5);  
				 
		//到物品主檔抓珍貴財產,寫入增減明細檔
		sSQL = "select distinct valuable "
			+ " from UNTNE_Nonexp WHERE 1 = 1"
			+ " AND enterOrg = '"+ Common.esapi(rs.getString("enterOrg")) +"'"
			+ " AND ownership = '"+ Common.esapi(rs.getString("ownership")) +"'"
			+ " AND propertyNo = '"+ Common.esapi(rs.getString("propertyNo")) +"'"
			+ " AND serialNoS = '"+ Common.esapi(rs.getString("GRD_NUM")) +"'"
			+ "";
		String valuable = db.getLookupField(sSQL);
		if(valuable.equals(""))valuable = " ";

		//到物品明細檔抓相對資料,寫入增減明細檔
		String lotNo = " ";
		String keepUnit = " ";
		String keeper = " ";
		String useUnit = " ";
		String userNo = " ";
		String place = " ";
		String oldPropertyNo = " ";
		String oldSerialNo = " ";
		sSQL = "select lotNo,keepUnit,keeper,useUnit,userNo,place,oldPropertyNo,oldSerialNo "
			+ " from UNTNE_NonexpDetail WHERE 1 = 1"
			+ " AND enterOrg = '"+ Common.esapi(rs.getString("enterOrg")) +"'"
			+ " AND ownership = '"+ Common.esapi(rs.getString("ownership")) +"'"
			+ " AND propertyNo = '"+ Common.esapi(rs.getString("propertyNo")) +"'"
			+ " AND serialNo = '"+ Common.esapi(rs.getString("GRD_NUM")) +"'"
			+ "";		
		Statement sStmt = db.getConnection().createStatement(ResultSet.TYPE_FORWARD_ONLY,ResultSet.CONCUR_READ_ONLY);
		ResultSet sRs = sStmt.executeQuery(sSQL);
		if(sRs.next()) {
			lotNo = sRs.getString("lotNo");
			keepUnit = sRs.getString("keepUnit");
			keeper = sRs.getString("keeper");
			useUnit = sRs.getString("useUnit");
			userNo = sRs.getString("userNo");
			place = sRs.getString("place");
			oldPropertyNo = sRs.getString("oldPropertyNo");
			oldSerialNo = sRs.getString("oldSerialNo");
		}
		sRs.close();
		sStmt.close();


    	//寫入物品增減值主檔
    	iSQLm = "insert into UNTNE_ADJUSTPROOF("
		        	+" enterOrg    "
		        	+",ownership   "
		        	+",caseNo      "
		        	+",writeDate   "
		        	+",proofDoc    "	
		        	+",proofNo     "
		        	+",adjustDate  "
		        	+",approveOrg  "
		        	+",approveDoc  "
		        	+",verify      "
		        	+",closing     "
		        	+",notes       "
		        	+",editID      "
		        	+",editDate    " 
     		+") values(\n"
					+" '"+ Common.esapi(rs.getString("enterOrg")) 	+"' --enterOrg    \n" 
					+",'"+ Common.esapi(rs.getString("ownership"))	+"' --ownership   \n" 
					+",'"+ Common.esapi(caseNo)                       +"' --caseNo      \n"
					+",'"+ Common.esapi(rs.getString("GRD_EDATE"))    +"' --writeDate   \n"
					+",'"+ Common.esapi(rs.getString("proofDoc"))   	+"' --proofDoc    \n" 
					+",'"+ Common.esapi(proofNo)		   				+"' --proofNo     \n" 
					+",'"+ Common.esapi(rs.getString("GRD_RDATE")) 	+"' --adjustDate  \n"
					+",'"+ Common.esapi(rs.getString("GRD_GOV"))   	+"' --approveOrg  \n" 
					+",'"+ Common.esapi(rs.getString("GRD_STATU"))  	+"' --approveDoc  \n" 
					+",'"+ Common.esapi(rs.getString("verify"))		+"' --verify      \n"
					+",'"+ "N"   						+"' --closing     \n"
					+",'"+ Common.esapi(rs.getString("notes"))		+"' --notes       \n" 
					+",'"+ Common.esapi(rs.getString("GRD_SNAME"))    +"' --editID      \n" 
					+",'"+ Common.esapi(rs.getString("GRD_SDATE"))    +"' --editDate    \n" 
     		+")";	 	
    	db.exeSQL(iSQLm);
    	

		//寫入物品增減值明細
    	iSQLd = "insert into UNTNE_AdjustDetail("
		        	+" enterOrg    "
		        	+",ownership   "
		        	+",caseNo      "
		        	+",propertyNo  "
		        	+",lotNo       "
		        	+",serialNo    "
		        	+",cause       "
		        	+",adjustDate  "
		        	+",verify      "
		        	+",closing     "
		        	+",propertyKind"
		        	+",valuable    "
		        	+",sourceDate  "
		        	+",bookAmount  "
		        	+",oldBookUnit "
		        	+",oldBookValue"
		        	+",adjustType  "
		        	+",adjustBookUnit"
		        	+",adjustBookValue"
		        	+",newBookUnit "
		        	+",newBookValue"
		        	+",keepUnit    "
		        	+",keeper      "
		        	+",useUnit     "
		        	+",userNo      "
		        	+",place       "
		        	+",oldPropertyNo"
		        	+",oldSerialNo "
		        	+",editID      "
		        	+",editDate    " 		        	
     		+") values(\n"
					+" '"+ Common.esapi(rs.getString("enterOrg")) 	+"' --enterOrg    \n" 
					+",'"+ Common.esapi(rs.getString("ownership"))	+"' --ownership   \n" 
					+",'"+ Common.esapi(caseNo)                       +"' --caseNo      \n"
					+",'"+ Common.esapi(rs.getString("propertyNo"))	+"' --propertyNo  \n"
					+",'"+ Common.esapi(lotNo)                        +"' --lotNo       \n"
					+",'"+ Common.esapi(rs.getString("GRD_NUM"))		+"' --serialNo    \n"
					+",'"+ Common.esapi(rs.getString("cause"))		+"' --cause       \n"
					+",'"+ Common.esapi(rs.getString("GRD_RDATE")) 	+"' --adjustDate  \n"
					+",'"+ Common.esapi(rs.getString("verify"))		+"' --verify      \n"
					+",'"+ "N"   						+"' --closing     \n"
					+",'"+ Common.esapi(rs.getString("propertyKind"))	+"' --propertyKind\n"
					+",'"+ Common.esapi(valuable)						+"' --valuable    \n"
					+",'"+ Common.esapi(rs.getString("GRD_BDATE"))	+"' --sourceDate  \n"
					+", "+ Common.esapi(rs.getString("bookAmount"))	+"  --bookAmount  \n"
					+", "+ Common.esapi(rs.getString("oldBookUnit"))	+"  --oldBookUnit \n"
					+", "+ Common.esapi(rs.getString("oldBookValue"))	+"  --oldBookValue\n"
					+",'"+ Common.esapi(rs.getString("adjustType"))	+"' --adjustType  \n"
					+", "+ Common.esapi(rs.getString("adjustBookUnit"))+" --adjustBookUnit  \n"
					+", "+ Common.esapi(rs.getString("adjustBookValue"))+" --adjustBookValue  \n"
					+", "+ Common.esapi(rs.getString("newBookUnit"))	+" --newBookUnit  \n"
					+", "+ Common.esapi(rs.getString("newBookValue"))	+" --newBookValue \n"
					+",'"+ Common.esapi(keepUnit)						+"' --keepUnit    \n"
					+",'"+ Common.esapi(keeper)						+"' --keeper      \n"
					+",'"+ Common.esapi(useUnit)						+"' --useUnit     \n"
					+",'"+ Common.esapi(userNo)						+"' --userNo      \n"
					+",'"+ Common.esapi(place)						+"' --place       \n"
					+",'"+ Common.esapi(oldPropertyNo)				+"' --oldPropertyNo\n"
					+",'"+ Common.esapi(oldSerialNo)					+"' --oldSerialNo \n"
					+",'"+ Common.esapi(rs.getString("GRD_SNAME"))    +"' --editID      \n" 
					+",'"+ Common.esapi(rs.getString("GRD_SDATE"))    +"' --editDate    \n" 					
			+")";	 	
		db.exeSQL(iSQLd);		
	
		//更新主檔日不為空時
		if(rs.getString("GRD_RDATE")!=null && !rs.getString("GRD_RDATE").equals("")) {
			//更新物品主檔
			uSQL = "update UNTNE_Nonexp set "
			+"  originalUnit = originalUnit - "+ Common.esapi(rs.getString("GRD_PRICE"))
			+" ,originalBV = originalBV - "+ Common.esapi(rs.getString("GRD_PRICE"))
			+" where 1=1 "
			+" and enterOrg='"+ Common.esapi(rs.getString("enterOrg")) +"'"
			+" and ownership='"+ Common.esapi(rs.getString("ownership")) +"'"
			+" and propertyNo='"+ Common.esapi(rs.getString("propertyNo")) +"'"
			+" and lotNo='"+ Common.esapi(lotNo) +"'"
			+" and dataState='1'"
			+"";
			db.exeSQL(uSQL);
			
			//更新物品明細檔
			uSQL = "update UNTNE_NonexpDetail set "
			+"  originalBV = originalBV - "+ Common.esapi(rs.getString("GRD_PRICE"))
			+" where 1=1 "
			+" and enterOrg='"+ Common.esapi(rs.getString("enterOrg")) +"'"
			+" and ownership='"+ Common.esapi(rs.getString("ownership")) +"'"
			+" and propertyNo='"+ Common.esapi(rs.getString("propertyNo")) +"'"
			+" and serialNo='"+ Common.esapi(rs.getString("GRD_NUM")) +"'"
			+" and dataState='1'"
			+"";
			db.exeSQL(uSQL);
		}
		
		iSQLm = null;
		iSQLd = null;
		cSQL = null;
		pSQL = null;
		proofNo = null;
    	caseNo = null;
    	maxCaseNo = null;
    	uSQL = null;
    	
		lotNo = null;
		keepUnit = null;
		keeper = null;
		useUnit = null;
		userNo = null;
		place = null;
		oldPropertyNo = null;
		oldSerialNo = null;    	
    }//while end
} catch(Exception e) {
	/*
	System.out.println(">>rowNo = "+ rowNo);
	System.out.println("SQL = "+ SQL);
	System.out.println("cSQL = "+ cSQL);
	System.out.println("maxCaseNo = "+ maxCaseNo);
	System.out.println("caseNo = "+ caseNo);
	System.out.println("pSQL = "+ pSQL);
	System.out.println("sSQL = "+ sSQL);
	System.out.println("iSQLm = "+ iSQLm);
	System.out.println("iSQLd = "+ iSQLd);
	System.out.println("uSQL = "+ uSQL);
	*/
    e.printStackTrace();
    out.println(e);
} finally {
    db.closeAll();
}
%>
