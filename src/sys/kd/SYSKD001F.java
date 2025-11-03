/*
*<br>程式目的：動產條碼資料批次新增
*<br>程式代號：sysmt001f
*<br>程式日期：0961023
*<br>程式作者：shan
*<br>--------------------------------------------------------
*<br>修改作者　　修改日期　　　修改目的
*<br>--------------------------------------------------------
*/

package sys.kd;

import java.sql.ResultSet;
import java.util.ArrayList;
import util.*;

public class SYSKD001F extends QueryBean{


String q_enterOrg;
String q_enterOrgName;
public String getQ_enterOrg(){ return checkGet(q_enterOrg); }
public void setQ_enterOrg(String s){ q_enterOrg=checkSet(s); }
public String getQ_enterOrgName(){ return checkGet(q_enterOrgName); }
public void setQ_enterOrgName(String s){ q_enterOrgName=checkSet(s); }

String toggleAll;
public String getToggleAll(){ if (!"".equals(checkGet(toggleAll))) return "checked"; else return "unchecked"; }    
public void setToggleAll(String s){ toggleAll=checkSet(s); }

String strKeySet[] = null;
public String[] getsKeySet(){ return strKeySet; }
public void setsKeySet(String[] s){ strKeySet=s; }

/**
 * <br>
 * <br>目的：依查詢欄位查詢多筆資料
 * <br>參數：無
 * <br>傳回：傳回ArrayList
*/

public ArrayList queryAll() throws Exception{
	Database db = new Database();
	ArrayList objList=new ArrayList();
	try {
		String sql = " select a.id ,a.enterorg ,o.organsname ,a.propertyno ,a.serialno ,a.mdcd_chk "
				   + " from pt_mdcd_temp a ,sysca_organ o "
				   + " where 1=1 "
				   + " and a.enterorg = o.organid(+) "
				   + " and a.enterorg = " + Common.sqlChar(getQ_enterOrg())
				   + " and a.mdcd_chk = '0' "
				   + " and not exists ( select * from untmp_reducedetail b "
				   + "            		where 1=1 "
				   + "       			and b.enterorg = a.enterorg "
				   + "      	 		and b.ownership = a.ownership "
				   + "       			and b.propertyno = a.propertyno "
				   + "       			and b.serialno = a.serialno "
				   + "     		      ) "
				   + ""; 
		ResultSet rs = db.querySQL(sql,true);
		processCurrentPageAttribute(rs);
		if (getTotalRecord() > 0) {
			int count = getRecordStart();
			int end = getRecordEnd();
			do {
				if (count > end) break;
				String rowArray[]=new String[5];
				
				rowArray[0] = Common.get(rs.getString("id")); 
				rowArray[1] = Common.get(rs.getString("organsname")); 
				rowArray[2] = Common.get(rs.getString("propertyno"));
				rowArray[3] = Common.get(rs.getString("serialno")); 
				rowArray[4] = Common.get(rs.getString("mdcd_chk")); 
				
				objList.add(rowArray);
				count++;
			} while (rs.next());
		}
		setStateQueryAllSuccess();
	} catch (Exception e) {
		e.printStackTrace();
	} finally {
		db.closeAll();
	}
	return objList;
}

public void link_addData(){
	int getcut=0;
	String str_barcode="";
	String[] strKeys = null;
	
	if(getsKeySet()!=null){
		getcut = getsKeySet().length;	//有勾選的list中資料數
	}
	
	/* execSQLArray 陣列內容如下列所示
	 * 0 :caseNo (增加單)
	 * 1 :proofNo (增加單)
	 * 2 :lotNo (動產主檔)
	 * 3 :barcode (動產主檔)
	 * 4 :reduce caseNo (減損主檔)
	 * 5 :reduce Proof (減損主檔)
	 */
	String[] execSQLArray = new String[5];
	
	String[] addPkey = null;
	Database db = new Database();
	
	for (int i=0; i<getcut; i++) {
		//System.out.println(getPkey(getsKeySet()[i])[0]);
		addPkey = getPkey(getsKeySet()[i]);
		
		//一.insert UNTMP_ADDPROOF
		execSQLArray[0] = getUntmp_addProof_sql(getsKeySet()[i] ,addPkey[0] ,addPkey[1]);
		
		//二.insert UNTMP_MOVABLE
		execSQLArray[1] = getUntmp_movable_sql(getsKeySet()[i] ,addPkey[0] ,addPkey[2]);
		
		//三.insert UNTMP_MOVABLEDETAIL
		execSQLArray[2] = getUntmp_movableDetail(getsKeySet()[i] ,addPkey[2] ,addPkey[3]);
		
		//四.insert UNTMP_ReduceProof 
		execSQLArray[3] = getUntmp_reduceProof(getsKeySet()[i] ,addPkey[4] ,addPkey[5]);
		
		//五.insert UNTMP_ReduceDetail
		execSQLArray[4] = getUntmp_ReduceDetail(getsKeySet()[i] ,addPkey[4] ,addPkey[2]);
		//System.out.println(execSQLArray[4]);
			
		try{
			db.excuteSQL(execSQLArray);
			setErrorMsg("資料已轉入");
		} catch (Exception e) {
			e.printStackTrace();
			setErrorMsg("轉檔錯誤");
		} finally {
			db.closeAll();
		}
		
	}
	
}

public String[] getPkey( String id ){
		
	String sql = " select substr(nvl(a.mdcd_edate ,a.mdcd_bdate),1,5) as enterDate "
			   + "        ,substr(nvl(a.mdc_rdate ,a.mdcd_bdate),1,5) as reduceDate "
			   + "        ,a.enterOrg ,a.ownership ,a.propertyNo ,a.serialNo ,a.propertyKind "
			   + "        ,a.ownership || substr(a.mdcd_edate,0,3) || substr(a.propertyno ,0 ,1) as barcodeType"
			   + " from  pt_mdcd_temp a "
			   + " where 1=1 "
			   + " and id = " + Common.sqlChar(id);
	
	//System.out.println(sql);
	String[] pkey = new String[6]; 
	Database db = new Database();
	try {
		ResultSet rs = db.querySQL(sql);
		String addCaseNo_sql = "";
		String addproofNo_sql = "";
		String addlotNo_sql = "";
		String addbarCode_sql = "";
		String redCaseNo_sql = "";
		String redproofNo_sql = "";
		
		if(rs.next()){
			addCaseNo_sql = " select " + Common.sqlChar(rs.getString("enterDate").substring(0,5)) + " ||  lpad(substr(nvl(max(a.caseno),'0000000000'),6,5)+1 ,5,'0') as addProof_caseNo "
			  			  + " from untmp_addproof a "
			  			  + " where 1=1 "
			  			  + " and a.enterorg = " + Common.sqlChar(rs.getString("enterOrg"))
			  			  + " and substr(a.enterdate ,1 ,5) = " + Common.sqlChar(rs.getString("enterDate").substring(0,5))
			  			  + "";
			
			addproofNo_sql = " select " + Common.sqlChar(rs.getString("enterDate").substring(0,5)) + " ||  lpad(substr(nvl(max(a.proofNo),'0000000000'),6,5)+1 ,5,'0') as addProof_proofNo "
			  			   + " from untmp_addproof a "
			  			   + " where 1=1 "
			  			   + " and a.enterorg = " + Common.sqlChar(rs.getString("enterOrg"))
			  			   + " and substr(a.enterdate ,1 ,5) = " + Common.sqlChar(rs.getString("enterDate").substring(0,5))
			  			   + "";
			
			addlotNo_sql = " select lpad(nvl(max(a.lotno) ,'0') +1 ,'7' ,'0') as addProof_lotNo "
						 + " from untmp_movable a "
						 + " where 1=1 "
						 + " and a.enterorg = " + Common.sqlChar(rs.getString("enterorg"))
						 + " and a.ownership = " + Common.sqlChar(rs.getString("ownership"))
						 + " and a.propertyno = " + Common.sqlChar(rs.getString("propertyno"))
						 + "";
			
			addbarCode_sql = " select " + rs.getString("barcodeType") + " || lpad((substr(nvl(max(a.barcode),'00000000000'),7,6)+1) ,6,'0') as barCode"
				 		   + " from untmp_movableDetail a "
				 		   + " where 1=1 "
				 		   + " and a.enterorg = " + Common.sqlChar(rs.getString("enterorg"))
				 		   + " and substr(a.barCode,0,5) = " + rs.getString("barcodeType")
				 		   + "";
			
			redCaseNo_sql = " select " + Common.sqlChar(rs.getString("reduceDate").substring(0,5)) + " ||  lpad(substr(nvl(max(a.caseno),'0000000000'),6,5)+1 ,5,'0') as reduce_caseNo "
			  			  + " from UNTMP_ReduceProof a "
			  			  + " where 1=1 "
			  			  + " and a.enterorg = " + Common.sqlChar(rs.getString("enterOrg"))
			  			  + " and substr(a.writeDate ,1 ,5) = " + Common.sqlChar(rs.getString("reduceDate").substring(0,5))
			  			  + "";
			
			redproofNo_sql = " select " + Common.sqlChar(rs.getString("reduceDate").substring(0,5)) + " ||  lpad(substr(nvl(max(a.proofNo),'0000000000'),6,5)+1 ,5,'0') as redProof_proofNo "
			   			   + " from UNTMP_ReduceProof a "
			   			   + " where 1=1 "
			   			   + " and a.enterorg = " + Common.sqlChar(rs.getString("enterOrg"))
			   			   + " and substr(a.writeDate ,1 ,5) = " + Common.sqlChar(rs.getString("reduceDate").substring(0,5))
			   			   + "";
		}
		
		pkey[0] = getTableFile.query_value(addCaseNo_sql ,"addProof_caseNo" ,"999999999");
		pkey[1] = getTableFile.query_value(addproofNo_sql ,"addProof_proofNo" ,"999999999");
		pkey[2] = getTableFile.query_value(addlotNo_sql ,"addProof_lotNo" ,"9999999");
		pkey[3] = getTableFile.query_value(addbarCode_sql ,"barCode" ,"9999999");
		pkey[4] = getTableFile.query_value(redCaseNo_sql ,"reduce_caseNo" ,"9999999999");
		pkey[5] = getTableFile.query_value(redproofNo_sql ,"redProof_proofNo" ,"9999999999");
		//System.out.println(redCaseNo_sql);
		
	} catch (Exception e) {
		e.printStackTrace();
	} finally {
		db.closeAll();
	}
	return pkey;
}

public String getUntmp_addProof_sql( String id ,String caseNo ,String proofNo){
	String sql = " insert into UNTMP_ADDPROOF( "
			   + " enterOrg "
			   + " ,ownership "
			   + " ,caseNo "
			   + " ,proofDoc "
			   + " ,proofNo "
			   + " ,enterDate "
			   + " ,verify "
			   + " ,closing "
			   + " ,notes "
			   + " ,editID "
			   + " ,editDate "
			   + " )( "
			   + " select "
  			   + " a.enterorg " 													/*	enterorg  */
 			   + " ,a.ownership "													/*  ownership */ 
  			   + " ," + Common.sqlChar(caseNo) 						 				/*  caseNo    */
  			   + " ,(select distinct x.docname from UNTMP_DOC x where x.docno='D1' and x.enterorg = a.enterorg ) "		/*  proofDoc  */
  			   + " ," + Common.sqlChar(proofNo)											/*  proofNo   */
  			   + " ,a.mdcd_edate "													/*  enterDate */
  			   + " ,'Y' "															/*  verify    */
  			   + " ,'N' "															/*  closing   */
 			   + " ,null "															/*  notes     */ 
  			   + " ,'KDSYS99' "														/*  editID    */
 			   + " ,a.Mdcd_SDATE "													/*  editDate  */ 
 			   + " from pt_mdcd_temp a "
 			   + " where a.id = " + Common.sqlChar(id)
 			   + ")";
	return sql;
}

public String getUntmp_movable_sql( String id ,String caseNo ,String lotNo){
	String sql = " insert into UNTMP_MOVABLE( "
			   + " enterOrg "
			   + " ,ownership "
			   + " ,caseNo "
			   + " ,propertyNo "
			   + " ,lotNo "
			   + " ,serialNoS "
			   + " ,serialNoE "
			   + " ,cause "
			   + " ,cause1 "
			   + " ,enterDate "
			   + " ,buyDate "
			   + " ,dataState "
			   + " ,verify "
			   + " ,closing "
			   + " ,propertyKind "
			   + " ,valuable "
			   + " ,originalAmount "
			   + " ,originalUnit "
			   + " ,originalBV "
			   + " ,bookAmount "
			   + " ,bookValue "
			   + " ,nameplate "
			   + " ,specification "
			   + " ,storeNo "
			   + " ,sourceKind "
			   + " ,sourceDate "
			   + " ,sourceDoc "
			   + " ,deprMethod "
			   + " ,scrapValue "
			   + " ,useEndYM "
			   + " ,permitReduceDate "
			   + " ,oldPropertyNo "
			   + " ,oldSerialNoS "
			   + " ,oldSerialNoE "
			   + " ,notes "
			   + " ,editID "
			   + " ,editDate "
			   + " )( "
			   + " select "
			   + " a.enterorg "								/* enterOrg            */ 
			   + " ,a.ownership "							/* ownership           */
			   + " ," + Common.sqlChar(caseNo)				/* caseNo              */ 
			   + " ,a.propertyno "							/* propertyNo          */
			   + " ," + Common.sqlChar(lotNo) 				/* lotNo               */
			   + " ,a.serialno "							/* serialNoS           */
			   + " ,a.serialno "							/* serialNoE           */
			   + " ,'99' "									/* cause               */ 
			   + " ,null "									/* cause1              */ 
			   + " ,a.mdcd_edate "							/* enterDate           */ 
			   + " ,nvl(a.mdcd_bdate ,a.mdcd_edate) "		/* buyDate             */ 
			   + " ,'2' "									/* dataState           */
			   + " ,'Y' "									/* verify              */ 
			   + " ,'N' "									/* closing             */
			   + " ,a.propertykind "						/* propertyKind        */
			   + " ,'N' " 									/* valuable            */
			   + " ,nvl(a.mdcd_amount,0) "					/* originalAmount      */
			   + " ,nvl(a.mdcd_price,0)	"					/* originalUnit        */
			   + " ,nvl(a.mdcd_value,0) "					/* originalBV          */
			   + " ,nvl(a.mdcd_amount,0) "					/* bookAmount          */
			   + " ,nvl(a.mdcd_value,0) "					/* bookValue           */
			   + " ,substrb(replace(a.mdcd_spec,'''',''), 81, 60) " /* nameplate           */ 
			   + " ,substrb(replace(a.mdcd_spec,'''',''), 1, 80) "	/* specification       */ 
			   + " ,'null' "								/* storeNo             */ 
			   + " ,'99' "									/* sourceKind          */ 
			   + " ,mdcd_bdate "							/* sourceDate          */
			   + " ,null "									/* sourceDoc           */ 
			   + " ,'01' " 									/* deprMethod          */ 
			   + " ,a.mdcd_retain "							/* scrapValue          */ 
			   + " ,( lpad(substr(nvl(mdcd_bdate,'00001'),1,3) + nvl(mdcd_year,0) ,3 ,'0') || "
			   + "    lpad(substr(nvl(mdcd_bdate,'00001'),4,2) - 1 ,2 ,'0') " 		/* useEndYM            */ 
			   + "   ) "
			   + " ,( lpad(substr(nvl(mdcd_bdate,'00001'),1,3)+nvl(mdcd_year,0),3,'0') || "
			   + "    substr(nvl(mdcd_bdate,'0000000'),4,4 ) " 						/* permitReduceDate    */ 
			   + "  ) "
			   + " ,null "									/* oldPropertyNo       */
			   + " ,null "									/* oldSerialNoS        */
			   + " ,null "									/* oldSerialNoE        */
			   + " ,null "									/* notes               */
			   + " ,'KDSYS99' "								/* editID              */ 
			   + " ,a.mdcd_sdate " 							/* editDate            */
			   + " from PT_MDCD_TEMP a "
			   + " where 1=1 "
			   + " and a.id = " + Common.sqlChar(id)
			   + " ) "
			   + "";
	
	return sql ; 
}

public String getUntmp_movableDetail( String id ,String lotNo ,String barCode ){
	String sql = " insert into UNTMP_MOVABLEDETAIL( "
			   + " enterOrg "
			   + " ,ownership "
			   + " ,propertyNo "
			   + " ,lotNo "
			   + " ,serialNo "
			   + " ,dataState "
			   + " ,verify "
			   + " ,closing "
			   + " ,originalAmount "
			   + " ,originalBV "
			   + " ,bookAmount "
			   + " ,bookValue "
			   + " ,originalKeepUnit "
			   + " ,originalKeeper "
			   + " ,originalUseUnit "
			   + " ,originalUser "
			   + " ,moveDate "
			   + " ,keepUnit "
			   + " ,keeper "
			   + " ,useUnit "
			   + " ,userNo "
			   + " ,place "
			   + " ,scrapValue "
			   + " ,notes "
			   + " ,oldPropertyNo "
			   + " ,oldSerialNo "
			   + " ,editID "
			   + " ,editDate "
			   + " ,barCode "
			   + " )( "
               + " select "
               + " a.enterorg " 							/* enterOrg          */  
               + " ,a.ownership "							/* ownership         */ 
               + " ,a.propertyno " 							/* propertyNo        */ 
               + " ," + Common.sqlChar(lotNo)								/* lotNo             */
               + " ,a.serialno "							/* serialNo          */ 
               + " ,'2' "									/* dataState         */ 
               + " ,'Y' "									/* verify            */ 
               + " ,'N' "									/* closing           */ 
               + " ,nvl(a.mdcd_amount ,0) "					/* originalAmount    */
               + " ,nvl(a.mdcd_value  ,0) " 				/* originalBV        */
               
               + " ,nvl(a.mdcd_amount ,0) "					/* bookAmount        */ 
               + " ,nvl(a.mdcd_value  ,0) "					/* bookValue         */ 
               + " ,nvl(a.mdcd_keep ,nvl(a.mdcd_kno,'99999')) "		/* originalKeepUnit  */ 
               + " ,nvl(a.mdcd_kno ,nvl(a.mdcd_kno,'99999')) "	/* originalKeeper    */ 
               + " ,nvl(a.mdcd_keep ,nvl(a.mdcd_kno,'99999')) " 	/* originalUseUnit   */ 
               + " ,nvl(a.mdcd_kno,nvl(a.mdcd_keep,'99999')) "		/* originalUser      */ 
               + " ,a.mdcd_edate "							/* moveDate          */
               + " ,nvl(a.mdcd_kno,'99999')	"				/* keepUnit          */ 
               + " ,nvl(a.mdcd_keep,'99999') "				/* keeper            */
               + " ,nvl(a.mdcd_kno,'99999') "				/* useUnit           */ 
               + " ,nvl(a.mdcd_keep,'99999') "				/* userNo            */ 
               + " ,'減損檔資料無記錄' "						/* place             */ 
               + " ,a.mdcd_retain "							/* scrapValue        */ 
               + " ,null "									/* notes             */
               + " ,null "									/* oldPropertyNo     */
               + " ,null "									/* oldSerialNo       */ 
               + " ,'KDSYS99' "								/* editID            */ 
               + " ,a.mdcd_sdate "							/* editDate          */ 
               + " , " +Common.sqlChar(barCode)				/* barCode           */ 
               + " from PT_MDCD_TEMP a "
               + " where 1=1 "
               + " and id = " + Common.sqlChar(id)
               + " ) ";
	return sql;
}

public String getUntmp_reduceProof( String id ,String caseNo ,String proofNo){
	String sql = " insert into UNTMP_ReduceProof( "
			   + " enterOrg "
			   + " ,ownership "
			   + " ,caseNo "
			   + " ,caseName "
			   + " ,writeDate "
			   + " ,proofDoc "
			   + " ,proofNo "
			   + " ,reduceDate "
			   + " ,approveOrg "
			   + " ,approveDoc "
			   + " ,verify "
			   + " ,closing "
			   + " ,notes "
			   + " ,editID "
			   + " ,editDate "
			   + " )( "
			   + " select "
			   + " a.enterorg " 								/* enterOrg   */
			   + " ,a.ownership "								/* ownership  */
			   + " , " + Common.sqlChar(caseNo)					/* caseNo     */
			   + " ,a.MDC_NO " 									/* caseName   */
			   + " ,a.MDC_EDATE "								/* writeDate  */
			   + " ,(select distinct x.docname from UNTMP_Doc_Temp x where x.docno='D2' ) "	/* proofDoc   */
			   + " , " + Common.sqlChar(proofNo)				/* proofNo    */  
			   + " ,a.MDC_RDATE "								/* reduceDate */ 
			   + " ,a.MDC_MCODE "								/* approveOrg */ 
			   + " ,MDC_STATUTE "								/* approveDoc */ 
			   + " ,DECODE(MDC_RDATE,NULL,'N','','N','Y') "		/* verify     */ 
			   + " ,'N' "										/* closing    */ 
			   + " ,a.MDC_NOTE "								/* notes      */ 
			   + " ,'KDSYS99' "									/* editID     */ 
			   + " ,a.mdc_ddate "								/* editDate   */ 
			   + " from PT_MDCD_TEMP a "
			   + " where 1=1 "
			   + " and a.id = " + Common.sqlChar(id)
			   + " )";
	return sql;
}

public String getUntmp_ReduceDetail( String id ,String caseNo ,String lotNo){
	String sql = " insert into UNTMP_ReduceDetail( " 
			   + " enterOrg "
			   + " ,ownership "
			   + " ,caseNo " 
			   + " ,propertyNo " 
			   + " ,lotNo " 
			   + " ,serialNo "  
			   + " ,enterDate "  
			   + " ,buyDate "  
			   + " ,cause " 
			   + " ,cause1 " 
			   + " ,reduceDate " 
			   + " ,newEnterOrg " 
			   + " ,verify " 
			   + " ,closing " 
			   + " ,propertyKind " 
			   + " ,valuable " 
			   + " ,oldBookAmount " 
			   + " ,oldBookUnit " 
			   + " ,oldBookValue " 
			   + " ,adjustBookAmount " 
			   + " ,adjustBookValue " 
			   + " ,newBookAmount " 
			   + " ,newBookValue " 
			   + " ,nameplate " 
			   + " ,specification " 
			   + " ,sourceDate " 
			   + " ,moveDate " 
			   + " ,keepUnit " 
			   + " ,keeper " 
			   + " ,useUnit " 
			   + " ,userNo " 
			   + " ,place " 
			   + " ,useYear " 
			   + " ,useMonth " 
			   + " ,deprMethod " 
			   + " ,scrapValue " 
			   + " ,useEndYM " 
			   + " ,permitReduceDate " 
			   + " ,reduceDeal " 
			   + " ,realizeValue " 
			   + " ,notes " 
			   + " ,oldPropertyNo " 
			   + " ,oldSerialNo " 
			   + " ,editID " 
			   + " ,editDate " 
			   + " )( " 
               + " select "
               + " a.enterorg "														/* enterOrg         */ 
               + " ,a.ownership "													/* ownership        */ 
               + " , " + Common.sqlChar(caseNo)										/* caseNo           */ 
               + " ,a.propertyno "													/* propertyNo       */ 
               + " , " + Common.sqlChar(lotNo)										/* lotNo            */ 
               + " ,a.serialno "													/* serialNo         */ 
               + " ,nvl(a.MDCD_EDATE,'0000000') "									/* enterDate        */ 
               + " ,nvl(MDCD_BDATE,'0000000') "										/* buyDate          */ 
               + " ,decode( a.MDC_REASON "											/* cause            */ 
               + " 			,'B','02' ,'C','07' ,'F','08' "
               + " 			,'G','04' ,'U','03' ,'D','99' "
               + " 			,'V','14' ,'99' "
               + " 		  ) "
               + " ,decode( a.MDC_REASON ,'J','轉減' ,'D','病死' ,'O','其他' ,'' ) " 		/* cause1           */ 
               + " ,a.MDC_RDATE " 														/* reduceDate       */ 
               + " ,DECODE( a.MDC_NSUNIT "												/* newEnterOrg      */ 
               + "          ,NULL ,MDC_NSORG "
               + "          ,'' ,MDC_NSORG "
               + "          ,(SELECT MAX(x.UNIT_UNO) FROM PT.PT_UNIT x WHERE x.UNIT_NO = A.MDC_NSUNIT) "
               + "        ) "
               + " ,DECODE(a.MDC_RDATE,NULL ,'N' ,'' ,'N' ,'Y') "						/* verify           */ 
               + " ,'N' "																/* closing          */ 
               + " ,a.propertykind "													/* propertyKind     */ 
               + " ,'N' "																/* valuable         */ 
               + " ,nvl(a.mdcd_amount ,'0') "											/* oldBookAmount    */ 
               + " ,nvl(a.MDCD_PRICE ,'0') "											/* oldBookUnit      */ 
               + " ,nvl(MDCD_VALUE ,'0') "												/* oldBookValue     */ 
               + " ,nvl(MDCD_AMOUNT ,'0') "												/* adjustBookAmount */ 
               + " ,nvl(MDCD_VALUE ,'0') "												/* adjustBookValue  */ 
               + " ,'0' "																/* newBookAmount    */ 
               + " ,'0' "																/* newBookValue     */ 
               + " ,substrb(replace(a.MDCD_SPEC,'''',''), 81, 60) "						/* nameplate        */ 
               + " ,substrb(replace(a.MDCD_SPEC,'''',''), 1, 80) "						/* specification    */ 
               + " ,a.MDCD_BDATE "														/* sourceDate       */ 
               + " ,null "																/* moveDate         */ 
               + " ,a.mdcd_kno "														/* keepUnit         */ 
               + " ,nvl(a.mdcd_keep,'99999') "											/* keeper           */ 
               + " ,a.mdcd_kno "														/* useUnit          */ 
               + " ,nvl(a.mdcd_keep,'99999') "											/* userNo           */ 
               + " ,null "																/* place            */ 
               + " ,( to_char(sysdate,'yyyy')-1911 - nvl(substr(MDCD_BDATE,0,3),0)) "	/* useYear          */ 
               + "    ,MOD(MONTHS_BETWEEN(TO_DATE(lpad(to_char(sysdate,'yyyy')-1911,3,'0')||to_char(sysdate,'mm'),'YYYMM' "
               + "   ) "
               + " ,TO_DATE(nvl(substr(a.MDCD_BDATE,0,5),'09810'),'YYYMM')),12) " 		/* useMonth         */ 
               + " ,'01' "																/* deprMethod       */ 
               + " ,a.MDCD_RETAIN "														/* scrapValue       */ 
               + " ,mod( MONTHS_BETWEEN ( to_date(lpad(to_char(sysdate ,'yyyy')-1911,3,'0')||to_char(sysdate,'mm'),'YYYMM') "	/* useEndYM         */  
               + "       ,TO_DATE(nvl(substr(MDCD_BDATE,0,5),'09810'),'YYYMM')),12) "

               + " ,( lpad(substr(nvl(a.mdcd_bdate,'00001'),1,3) + nvl(a.mdcd_year ,0),3,'0') || " /* permitReduceDate */ 
               + "    substr(nvl(a.mdcd_bdate,'0000000'),4,4) "
               + " ) "
 
               + " ,decode( a.MDCD_HANDLE " 											/* reduceDeal       */
               + "          ,'丟棄','06' ,'交換','04' ,'回收','06' "
               + "          ,'利用','02' ,'銷毀','05' ,'轉撥','03' "
               + "          ,'變賣','01' ,'99' "
               + "        ) "
               + " ,a.MDCD_PAY "	 													/* realizeValue     */ 
               + " ,a.mdcd_note "														/* notes            */ 
               + " ,a.mdcd_code "														/* oldPropertyNo    */ 
               + " ,a.mdcd_num "														/* oldSerialNo      */ 
               + " ,'KDSYS99' "															/* editID           */ 
               + " ,a.mdcd_sdate "														/* editDate         */ 
               + " from PT_MDCD_TEMP a "
               + " where 1=1 "
               + " and a.id = " + Common.sqlChar(id)
               + " )";
	return sql;
}

}//public class SYSKD001F extends QueryBean{ =>end

