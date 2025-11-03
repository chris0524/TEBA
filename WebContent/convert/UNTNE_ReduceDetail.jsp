<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="java.sql.*"  %>
<%@ page import="util.Database"%>
<%@ page import="util.Common" %>
<%@ include file="func.jsp"%>

<%
int rowNo = 0;
String SQL = "";
String caseNo = "";
String sSQL1 = "";
String sSQL2 = "";
String sSQL3 = "";
String sSQL4 = "";
String iSQLm = "";
String sSQL = "";

String sSQL5 = "";
String sSQL6 = "";
String sSQL7 = "";
String iSQLd = "";
Database db = new Database();

try {	
	db.exeSQL("UPDATE PT_GDCD SET GDCD_BDATE=REPLACE(GDCD_BDATE,'*','0')");
	db.exeSQL("DELETE UNTNE_ReduceDetail");
	db.exeSQL("DELETE UNTNE_NonexpDetail where DATASTATE='2'");
	
	SQL ="select \n"
	    +" DECODE((GDCD_SORG||GDCD_SUNIT),"
	    +"   '376470000A11', '000000011Z',"
        +"   GDCD_SORG"
        +" ) as enterOrg \n"      
    	+",DECODE(GDCD_RIGHT, \n"
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
        +",REPLACE(GDCD_CODE,'-','') as propertyNo \n"
        +",GDCD_NUM as serialNo  \n"
        +",nvl(GDCD_EDATE,'0000000') as enterDate \n"
        +",nvl(GDCD_BDATE,'0000000') as buyDate  \n"
        +",GDCD_NO,GDCD_SORG,GDCD_SUNIT \n"
        +",DECODE(GDCD_KIND,"
        +"   '01', '01',"
        +"   '02', '02',"
        +"   '05', '03',"
        +"   '00', '04',"
        +"   ' '"
        +" ) as propertyKind \n"      
        +",nvl(GDCD_OLD,0) as oldBookAmount \n"
        +",nvl(GDCD_PRICE,0) as oldBookUnit \n"
        +",nvl(GDCD_VALUE,0) as oldBookValue \n"
        +",nvl(GDCD_AMOUNT,0) as adjustBookAmount \n"
        +",nvl(GDCD_VALUE,0) as adjustBookValue \n"
        +",0 as newBookAmount \n"
        +",0 as newBookValue \n"
		+",substrb(replace(GDCD_SPEC,'''',''), 1, 80) as specification \n"
		+",substrb(replace(GDCD_SPEC,'''',''), 81, 60) as nameplate \n"
		+",GDCD_BDATE as sourceDate \n"
		+",nvl(GDCD_KNO,' ') as keepUnit \n"  
		+",nvl(GDCD_KEEP,' ') as keeper \n"  
		+",nvl(GDCD_KNO,' ') as useUnit \n"   
		+",nvl(GDCD_KEEP,' ') as userNo \n"  	
		+",(to_char(sysdate,'yyyy')-1911 - nvl(substr(GDCD_BDATE,0,3),0)) as useYear \n"
		+",MOD(MONTHS_BETWEEN ( TO_DATE(lpad(to_char(sysdate,'yyyy')-1911,3,'0')||to_char(sysdate,'mm'),'YYYMM') \n"
        +"                     ,TO_DATE(nvl(substr(GDCD_BDATE,0,5),'09810'),'YYYMM')),12) as useMonth \n"
        +",DECODE(GDCD_HANDLE,"
        +"   '丟棄', '06',"
        +"   '交換', '04',"
        +"   '回收', '06',"
        +"   '利用', '02',"
        +"   '銷毀', '05',"
        +"   '轉撥', '03',"
        +"   '變賣', '01',"
        +"   ' '"
        +" ) as reduceDeal \n"              
		+",GDCD_PAY as realizeValue \n"
        +",GDCD_NOTE as notes \n"
		+",GDCD_SNAME,GDCD_SDATE \n"
		
		+",nvl(GDCD_OLD ,0) as  originalAmount\n"   
		+",nvl(GDCD_VALUE ,0) as originalBV \n" 
		+",nvl(GDCD_AMOUNT ,0) as bookAmount \n"
		+",nvl(GDCD_VALUE ,0) as bookValue \n" 
		
		+" from PT_GDCD A \n"
		+" where GDCD_RIGHT in('01','11') \n"
		+" AND GDCD_CHK='1' "
		+"";
	ResultSet rs = db.querySQL(SQL);
	
    while(rs.next()) {
    	rowNo = rs.getRow();  
    	//System.out.println(rowNo);
    	
    	//電腦單號,應到減損主檔抓
    	sSQL1 = "select caseNo from UNTNE_ReduceProof WHERE 1=1"
		+ " AND GDC_NO = '"+ Common.esapi(rs.getString("GDCD_NO")) +"'"
		+ " AND GDC_SORG = '"+ Common.esapi(rs.getString("GDCD_SORG")) +"'"
		+ " AND GDC_SUNIT = '"+ Common.esapi(rs.getString("GDCD_SUNIT")) +"'" 
		+ "";
    	caseNo = db.getLookupField(sSQL1);
    	if(caseNo==null || "".equals(caseNo)) {
    		continue;
    	}
    	    	
		//到物品主檔抓珍貴財產,寫入減損明細檔
		String valuable = " ";
		String permitReduceDate = " ";
		sSQL2 = "select nvl(valuable,' ') as valuable, permitReduceDate "
			+ " from UNTNE_Nonexp WHERE 1 = 1"
			+ " AND enterOrg = '"+ Common.esapi(rs.getString("enterOrg")) +"'"
			+ " AND ownership = '"+ Common.esapi(rs.getString("ownership")) +"'"
			+ " AND propertyNo = '"+ Common.esapi(rs.getString("propertyNo")) +"'"
			+ " AND serialNoS = '"+ Common.esapi(rs.getString("serialNo")) +"'"
			+ "";		
		Statement sStmt = db.getConnection().createStatement(ResultSet.TYPE_FORWARD_ONLY,ResultSet.CONCUR_READ_ONLY);
		ResultSet sRs = sStmt.executeQuery(sSQL2);
		if(sRs.next()) {
			valuable = sRs.getString("valuable");
			permitReduceDate = sRs.getString("permitReduceDate");
		}
		sRs.close();
		sStmt.close();
		
		//到物品明細檔抓相對資料,寫入減損明細檔
		String lotNo = " ";
		String moveDate = "";
		String place = "";
		String oldPropertyNo = "";
		String oldSerialNo = "";
		sSQL3 = "select lotNo, moveDate, place, oldPropertyNo, oldSerialNo "
			+ " from UNTNE_NonexpDetail WHERE 1 = 1"
			+ " AND enterOrg = '"+ Common.esapi(rs.getString("enterOrg")) +"'"
			+ " AND ownership = '"+ Common.esapi(rs.getString("ownership")) +"'"
			+ " AND propertyNo = '"+ Common.esapi(rs.getString("propertyNo")) +"'"
			+ " AND serialNo = '"+ Common.esapi(rs.getString("serialNo")) +"'"
			+ "";		
		sStmt = db.getConnection().createStatement(ResultSet.TYPE_FORWARD_ONLY,ResultSet.CONCUR_READ_ONLY);
		sRs = sStmt.executeQuery(sSQL3);
		if(sRs.next()) {
			lotNo = sRs.getString("lotNo");
			moveDate = sRs.getString("moveDate");
			place = sRs.getString("place");
			oldPropertyNo = sRs.getString("oldPropertyNo");
			oldSerialNo = sRs.getString("oldSerialNo");
		}
		sRs.close();
		sStmt.close();

		//到減損主檔抓相對資料,寫入減損明細檔
        String cause = " ";
        String cause1 = "";
        String reduceDate = "";
        String newEnterOrg = "";
        String verify = "N";
		sSQL4 = "select \n"
	        //減損原因
	        +" DECODE(GDC_REASON, \n"
	        +"   'B','02', \n"
	        +"   'C','07', \n"
	        +"   'F','08', \n"
	        +"   'G','04', \n"
	        +"   'J','99', \n"
	        +"   'U','03', \n"
	        +"   'D','99', \n"
	        +"   'V','14', \n"
	        +"   'O','99', \n"
	        +"   ' ' \n"
	        +" ) as cause \n"		        
	        //GDC_REASON轉到新代碼為其他時,將舊系統的中文名稱轉入此欄位
	        +" ,DECODE(GDC_REASON, \n" 
	        +"   'J','轉減', \n"
	        +"   'D','病死', \n"
	        +"   'O','其他', \n"
	        +"   ' ' \n"
	        +" ) as cause1 \n"
	        +" ,GDC_RDATE as reduceDate \n"
		    +" ,DECODE(GDC_NSUNIT, \n"
		    +"   NULL, GDC_NSORG, \n"
		    +"   ' ' , GDC_NSORG, \n"
	        +"   (SELECT MAX(UNIT_UNO) FROM pt_unit WHERE UNIT_NO=A.GDC_NSUNIT) \n"
	        +" ) as newEnterOrg \n"
			+ " from PT_GDC A WHERE 1 = 1 \n"
			+ " AND GDC_NO = '"+ Common.esapi(rs.getString("GDCD_NO")) +"'"
			+ " AND GDC_SORG = '"+ Common.esapi(rs.getString("GDCD_SORG")) +"'"
			+ " AND GDC_SUNIT = '"+ Common.esapi(rs.getString("GDCD_SUNIT")) +"'"
			+ "";		
		sStmt = db.getConnection().createStatement(ResultSet.TYPE_FORWARD_ONLY,ResultSet.CONCUR_READ_ONLY);
		sRs = sStmt.executeQuery(sSQL4);
		if(sRs.next()) {
			cause = sRs.getString("cause");
			cause1 = sRs.getString("cause1");
			reduceDate = sRs.getString("reduceDate");
			newEnterOrg = sRs.getString("newEnterOrg");
			if(reduceDate!=null && !reduceDate.equals("")) {
				verify = "Y";
			}			
		}
		sRs.close();
		sStmt.close();
				
		
    	//寫入物品減損明細
    	iSQLm = "insert into UNTNE_ReduceDetail("
		        	+" enterOrg    "
		        	+",ownership   "
		        	+",caseNo      "
		        	+",propertyNo  "
		        	+",lotNo       "
		        	+",serialNo    "
		        	+",enterDate   "
		        	+",buyDate     "
		        	+",cause       "
		        	+",cause1      "
		        	+",reduceDate  "
		        	+",newEnterOrg "
		        	+",verify      "
		        	+",closing     "
		        	+",propertyKind"
		        	+",valuable    "
		        	+",oldBookAmount"
		        	+",oldBookUnit "
		        	+",oldBookValue"
		        	+",adjustBookAmount"
		        	+",adjustBookValue"
		        	+",newBookAmount"
		        	+",newBookValue"
		        	+",nameplate   "
		        	+",specification"
		        	+",sourceDate  "
		        	+",moveDate    "
		        	+",keepUnit    "
		        	+",keeper      "
		        	+",useUnit     "
		        	+",userNo      "
		        	+",place       "
		        	+",useYear     "
		        	+",useMonth    "
		        	+",permitReduceDate  "
		        	+",reduceDeal  "	
		        	+",realizeValue"
		        	+",notes       "
		        	+",oldPropertyNo"
		        	+",oldSerialNo "
		        	+",editID      "
		        	+",editDate    " 
     		+") values(\n"
					+" '"+ Common.esapi(rs.getString("enterOrg")) 	+"' --enterOrg    \n" 
					+",'"+ Common.esapi(rs.getString("ownership"))	+"' --ownership   \n" 
					+",'"+ Common.esapi(caseNo)                       +"' --caseNo      \n"
					+",'"+ Common.esapi(rs.getString("propertyNo"))	+"' --propertyNo  \n"
					+",'"+ Common.esapi(lotNo)						+"' --lotNo       \n"
					+",'"+ Common.esapi(rs.getString("serialNo"))		+"' --serialNo    \n"
					+",'"+ Common.esapi(rs.getString("enterDate"))	+"' --enterDate   \n"
					+",'"+ Common.esapi(rs.getString("buyDate"))		+"' --buyDate     \n"
					+",'"+ Common.esapi(cause)						+"' --cause       \n"
					+",'"+ Common.esapi(cause1)						+"' --cause1      \n"
					+",'"+ Common.esapi(reduceDate)					+"' --reduceDate  \n"
					+",'"+ Common.esapi(newEnterOrg)					+"' --newEnterOrg \n"
					+",'"+ Common.esapi(verify)  						+"' --verify      \n"
					+",'"+ "N"   						+"' --closing     \n"
					+",'"+ Common.esapi(rs.getString("propertyKind"))	+"' --propertyKind\n"
					+",'"+ Common.esapi(valuable)						+"' --valuable    \n"			
					+","+ Common.esapi(rs.getString("oldBookAmount")) +" --oldBookAmount\n"
					+","+ Common.esapi(rs.getString("oldBookUnit"))	+" --oldBookUnit  \n"
					+","+ Common.esapi(rs.getString("oldBookValue"))	+" --oldBookValue \n"
					+","+ Common.esapi(rs.getString("adjustBookAmount"))	+" --adjustBookAmount  \n"
					+","+ Common.esapi(rs.getString("adjustBookValue"))	+" --adjustBookValue   \n"
					+","+ Common.esapi(rs.getString("newBookAmount")) +" --newBookAmount\n"
					+","+ Common.esapi(rs.getString("newBookValue"))	+" --newBookValue \n"					
					+",'"+ Common.esapi(rs.getString("nameplate"))	+"' --nameplate   \n"
					+",'"+ Common.esapi(rs.getString("specification"))+"' --specification\n"
					+",'"+ Common.esapi(rs.getString("sourceDate"))	+"' --sourceDate  \n"
					+",'"+ Common.esapi(moveDate)						+"' --moveDate    \n"
					+",'"+ Common.esapi(rs.getString("keepUnit"))		+"' --keepUnit    \n"
					+",'"+ Common.esapi(rs.getString("keeper"))		+"' --keeper      \n"
					+",'"+ Common.esapi(rs.getString("useUnit"))		+"' --useUnit     \n"
					+",'"+ Common.esapi(rs.getString("userNo"))		+"' --userNo      \n"
					+",'"+ Common.esapi(place)						+"' --place       \n"
					+", "+ Common.esapi(rs.getString("useYear"))		+"  --useYear     \n"
					+", "+ Common.esapi(rs.getString("useMonth"))		+"  --useMonth    \n"
					+",'"+ Common.esapi(permitReduceDate)				+"' --permitReduceDate  \n"
					+",'"+ Common.esapi(rs.getString("reduceDeal")) 	+"' --reduceDeal  \n"
					+", " + Common.esapi(rs.getString("realizeValue"))+"  --realizeValue\n"
					+",'" + Common.esapi(rs.getString("notes"))		+"' --notes       \n"
					+",'"+ Common.esapi(oldPropertyNo)   				+"' --oldPropertyNo\n"
					+",'"+ Common.esapi(oldSerialNo)   				+"' --oldSerialNo \n"
					+",'"+ Common.esapi(rs.getString("GDCD_SNAME"))   +"' --editID      \n" 
					+",'"+ Common.esapi(rs.getString("GDCD_SDATE"))   +"' --editDate    \n" 
     		+")";	 	
    	db.exeSQL(iSQLm);	


    	//無更新主檔日,則不回寫物品明細
    	if(!verify.equals("Y")) {
    		continue;
    	}   	
    	//***所有重複的資料***物品明細已有此筆,則不回寫
    	//select GDCD_SORG,GDCD_RIGHT,GDCD_CODE,GDCD_NUM,count(0) from PT_GDCD where 1=1 
		//group by GDCD_SORG,GDCD_RIGHT,GDCD_CODE,GDCD_NUM
		//having count(0) >1
		sSQL="select count(0) from UNTNE_NonexpDetail where 1=1 "
			+" and enterOrg='"+ Common.esapi(rs.getString("enterOrg")) +"'"
			+" and ownership='"+ Common.esapi(rs.getString("ownership")) +"'"
			+" and propertyNo='"+ Common.esapi(rs.getString("propertyNo")) +"'"
			+" and serialNo='"+ Common.esapi(rs.getString("serialNo")) +"'"
			+"";
    	int cnt = toInt(db.getLookupField(sSQL));   	
    	if(cnt > 0) {
    		continue;
    	} 	
		

    	//條碼
    	String code5 = cutString(rs.getString("ownership"),0,1) + cutString(rs.getString("buyDate"),0,3) + cutString(rs.getString("propertyNo"),0,1);
    	sSQL5 = "select NVL(MAX(substr(barCode,7,6)),0)+1 from UNTNE_NonexpDetail WHERE 1 = 1"
			+ " AND enterOrg = '"+ Common.esapi(rs.getString("enterOrg")) +"'"
			+ " AND substr(barCode,0,5) = '"+ code5 +"'"
			+ "";   	
    	String barCode = code5 + fullLeftZero(db.getLookupField(sSQL5),6);
    	
    	sSQL6 = " select NONEXP_ID from UNTNE_ReduceProof where 1=1"
		+ " AND GDC_NO = '"+ Common.esapi(rs.getString("GDCD_NO")) +"'"
		+ " AND GDC_SORG = '"+ Common.esapi(rs.getString("GDCD_SORG")) +"'"
		+ " AND GDC_SUNIT = '"+ Common.esapi(rs.getString("GDCD_SUNIT")) +"'" 
		+ "";
    	String NONEXP_ID = db.getLookupField(sSQL6);
    	
    	sSQL7 = " select lotNo from UNTNE_Nonexp where 1=1"
    		+ " AND enterOrg = '"+ Common.esapi(rs.getString("enterOrg")) +"'"
    		+ " AND ownership = '"+ Common.esapi(rs.getString("ownership")) +"'"
    		+ " AND propertyNo = '"+ Common.esapi(rs.getString("propertyNo")) +"'"
    		+ " AND caseNo = '"+ NONEXP_ID +"'"
    		+ "";
    	lotNo = db.getLookupField(sSQL7);
    	if(lotNo==null || "".equals(lotNo)) {
    		continue;
    	}
    		
    	//回寫物品明細
    	iSQLd = "insert into UNTNE_NonexpDetail("
		        	+" enterOrg    "
		        	+",ownership   "
		        	+",propertyNo  "        	
		        	+",lotNo       "
		        	+",serialNo    "
		        	+",dataState   "
		        	+",reduceDate  "
		        	+",reduceCause "
		        	+",reduceCause1"
		        	+",verify      "
		        	+",closing     "
		        	+",originalAmount"
		        	+",originalBV  "
		        	+",bookAmount  "
		        	+",bookValue   "
		        	+",originalKeepUnit"
		        	+",originalKeeper"
		        	+",originalUseUnit"
		        	+",originalUser"
		        	+",keepUnit    "
		        	+",keeper      "
		        	+",useUnit     "
		        	+",userNo      "
		        	+",notes       "
		        	+",editID      "
		        	+",editDate    " 
		        	+",barCode     "
     		+") values(\n"
					+" '"+ Common.esapi(rs.getString("enterOrg")) 	+"' --enterOrg    \n" 
					+",'"+ Common.esapi(rs.getString("ownership"))	+"' --ownership   \n" 
					+",'"+ Common.esapi(rs.getString("propertyNo"))	+"' --propertyNo  \n"					
					+",'"+ Common.esapi(lotNo)						+"' --lotNo       \n"
					+",'"+ Common.esapi(rs.getString("serialNo"))		+"' --serialNo    \n"
					+",'"+ "2"							+"' --dataState    \n"
					+",'"+ Common.esapi(reduceDate)					+"' --reduceDate  \n"					
					+",'"+ Common.esapi(cause)						+"' --reduceCause \n"
					+",'"+ Common.esapi(cause1)						+"' --reduceCause1\n"
					+",'"+ Common.esapi(verify) 						+"' --verify      \n"
					+",'"+ "N"   						+"' --closing     \n"
					+", "+ Common.esapi(rs.getString("originalAmount"))+"  --originalAmount    \n"
					+", "+ Common.esapi(rs.getString("originalBV"))	+"  --originalBV    \n"
					+", "+ Common.esapi(rs.getString("bookAmount"))	+"  --bookAmount    \n"
					+", "+ Common.esapi(rs.getString("bookValue"))	+"  --bookValue    \n"
					+",'"+ Common.esapi(rs.getString("keepUnit"))		+"' --originalKeepUnit    \n"
					+",'"+ Common.esapi(rs.getString("keeper"))		+"' --originalKeeper    \n"
					+",'"+ Common.esapi(rs.getString("keepUnit"))		+"' --originalUseUnit    \n"
					+",'"+ Common.esapi(rs.getString("keeper"))		+"' --originalUser    \n"
					+",'"+ Common.esapi(rs.getString("keepUnit"))		+"' --keepUnit    \n"
					+",'"+ Common.esapi(rs.getString("keeper"))		+"' --keeper    \n"
					+",'"+ Common.esapi(rs.getString("keepUnit"))		+"' --useUnit    \n"
					+",'"+ Common.esapi(rs.getString("keeper"))		+"' --userNo    \n"					
					+",'" + Common.esapi(rs.getString("notes"))		+"' --notes       \n"
					+",'"+ Common.esapi(rs.getString("GDCD_SNAME"))   +"' --editID      \n" 
					+",'"+ Common.esapi(rs.getString("GDCD_SDATE"))   +"' --editDate    \n" 
					+",'"+ Common.esapi(barCode) 						+"' --barCode      \n"
     		+")";	 	   	  	
    
    }//while end
    //System.out.println("finish!!");
} catch(Exception e) {
	/*
	System.out.println(">>rowNo = "+ rowNo);
	System.out.println("SQL "+ SQL);
	System.out.println("sSQL1 "+ sSQL1);
	System.out.println("caseNo = "+ caseNo);
	System.out.println("/sSQL2/ "+ sSQL2);
	System.out.println("/sSQL3/ "+ sSQL3);
	System.out.println("/sSQL4/ "+ sSQL4);
	System.out.println("/iSQLm/ "+ iSQLm);
	
	System.out.println("/sSQL5/ "+ sSQL5);
	System.out.println("/sSQL6/ "+ sSQL6);
	System.out.println("/sSQL7/ "+ sSQL7);
	System.out.println("/SQLd/ "+ iSQLd);
	*/
    e.printStackTrace();
    out.println(e);
} finally {
    db.closeAll();
}
%>
