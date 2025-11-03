/*
*<br>程式目的：整批輸入非消耗品盤點結果作業
*<br>程式代號：untpd017f
*<br>程式日期：1030915
*<br>程式作者：Mike Kao
*<br>--------------------------------------------------------
*<br>修改作者　　修改日期　　　修改目的
*<br>--------------------------------------------------------
*/

package unt.pd;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Map;

import util.*;
import util.Common;

public class UNTPD017F extends SuperBean2{

String q_enterOrg;
String q_enterOrg2;
String q_ownership;
String q_differenceKind;
String q_propertyType;
String q_propertyNoS;
String q_propertyNoSName;
String q_propertyNoE;
String q_propertyNoEName;
String q_serialNoS;
String q_serialNoE;
String q_buyDateS;
String q_buyDateE;
String q_barcode;
String q_checkResult;
String q_oldPropertyNo;
String q_oldSerialNoS;
String q_oldSerialNoE;
String q_propertyKind;
String q_fundType;
String q_keepUnit;
String q_keeper;
String q_useUnit;
String q_userNo;
String q_place1;
String q_place;
String q_place1Name;
String q_notes;
String q_scrappednote;
String q_labelnote;
String q_movenote;


String enterorg;
String ownership;
String differencekind;
String propertyno;
String serialno;
String checkresult; 
String scrappednote; 
String labelnote;
String movenote; 
String oddscause;


public String getQ_enterOrg() {return checkGet(q_enterOrg);}
public void setQ_enterOrg(String qEnterOrg) {q_enterOrg = checkSet(qEnterOrg);}
public String getQ_enterOrg2() {return checkGet(q_enterOrg2);}
public void setQ_enterOrg2(String qEnterOrg2) {q_enterOrg2 = checkSet(qEnterOrg2);}
public String getQ_ownership() {return checkGet(q_ownership);}
public void setQ_ownership(String qOwnership) {q_ownership = checkSet(qOwnership);}
public String getQ_differenceKind() {return checkGet(q_differenceKind);}
public void setQ_differenceKind(String qDifferenceKind) {q_differenceKind = checkSet(qDifferenceKind);}
public String getQ_propertyType() {return checkGet(q_propertyType);}
public void setQ_propertyType(String qPropertyType) {q_propertyType = checkSet(qPropertyType);}
public String getQ_propertyNoS() {return checkGet(q_propertyNoS);}
public void setQ_propertyNoS(String qPropertyNoS) {q_propertyNoS = checkSet(qPropertyNoS);}
public String getQ_propertyNoE() {return checkGet(q_propertyNoE);}
public void setQ_propertyNoE(String qPropertyNoE) {q_propertyNoE = checkSet(qPropertyNoE);}
public String getQ_serialNoS() {return checkGet(q_serialNoS);}
public void setQ_serialNoS(String qSerialNoS) {q_serialNoS = checkSet(qSerialNoS);}
public String getQ_serialNoE() {return checkGet(q_serialNoE);}
public void setQ_serialNoE(String qSerialNoE) {q_serialNoE = checkSet(qSerialNoE);}
public String getQ_buyDateS() {return checkGet(q_buyDateS);}
public void setQ_buyDateS(String qBuyDateS) {q_buyDateS = checkSet(qBuyDateS);}
public String getQ_buyDateE() {return checkGet(q_buyDateE);}
public void setQ_buyDateE(String qBuyDateE) {q_buyDateE = checkSet(qBuyDateE);}
public String getQ_barcode() {return checkGet(q_barcode);}
public void setQ_barcode(String qBarcode) {q_barcode = checkSet(qBarcode);}
public String getQ_checkResult() {return checkGet(q_checkResult);}
public void setQ_checkResult(String qCheckResult) {q_checkResult = checkSet(qCheckResult);}
public String getQ_oldPropertyNo() {return checkGet(q_oldPropertyNo);}
public void setQ_oldPropertyNo(String qOldPropertyNo) {q_oldPropertyNo = checkSet(qOldPropertyNo);}
public String getQ_oldSerialNoS() {return checkGet(q_oldSerialNoS);}
public void setQ_oldSerialNoS(String qOldSerialNoS) {q_oldSerialNoS = checkSet(qOldSerialNoS);}
public String getQ_oldSerialNoE() {return checkGet(q_oldSerialNoE);}
public void setQ_oldSerialNoE(String qOldSerialNoE) {q_oldSerialNoE = checkSet(qOldSerialNoE);}
public String getQ_propertyKind() {return checkGet(q_propertyKind);}
public void setQ_propertyKind(String qPropertyKind) {q_propertyKind = checkSet(qPropertyKind);}
public String getQ_fundType() {return checkGet(q_fundType);}
public void setQ_fundType(String qFundType) {q_fundType = checkSet(qFundType);}
public String getQ_keepUnit() {return checkGet(q_keepUnit);}
public void setQ_keepUnit(String qKeepUnit) {q_keepUnit = checkSet(qKeepUnit);}
public String getQ_keeper() {return checkGet(q_keeper);}
public void setQ_keeper(String qKeeper) {q_keeper = checkSet(qKeeper);}
public String getQ_useUnit() {return checkGet(q_useUnit);}
public void setQ_useUnit(String qUseUnit) {q_useUnit = checkSet(qUseUnit);}
public String getQ_userNo() {return checkGet(q_userNo);}
public void setQ_userNo(String qUserNo) {q_userNo = checkSet(qUserNo);}
public String getQ_place1() {return checkGet(q_place1);}
public void setQ_place1(String qPlace1) {q_place1 = checkSet(qPlace1);}
public String getQ_place1Name() {return checkGet(q_place1Name);}
public void setQ_place1Name(String qPlace1Name) {q_place1Name = checkSet(qPlace1Name);}
public String getQ_notes() {return checkGet(q_notes);}
public void setQ_notes(String qNotes) {q_notes = checkSet(qNotes);}
public String getQ_scrappednote() {return checkGet(q_scrappednote);}
public void setQ_scrappednote(String qScrappednote) {q_scrappednote = checkSet(qScrappednote);}
public String getQ_labelnote() {return checkGet(q_labelnote);}
public void setQ_labelnote(String qLabelnote) {q_labelnote = checkSet(qLabelnote);}
public String getQ_movenote() {return checkGet(q_movenote);}
public void setQ_movenote(String qMovenote) {q_movenote = checkSet(qMovenote);}
public String getQ_propertyNoSName() {return checkGet(q_propertyNoSName);}
public void setQ_propertyNoSName(String qPropertyNoSName) {q_propertyNoSName = checkSet(qPropertyNoSName);}
public String getQ_propertyNoEName() {return checkGet(q_propertyNoEName);}
public void setQ_propertyNoEName(String qPropertyNoEName) {q_propertyNoEName = checkSet(qPropertyNoEName);}
public String getQ_place() {return checkGet(q_place);}
public void setQ_place(String qPlace) {q_place = checkSet(qPlace);}

public String getEnterorg() {return checkGet(enterorg);}
public void setEnterorg(String enterorg) {this.enterorg = checkSet(enterorg);}
public String getOwnership() {return checkGet(ownership);}
public void setOwnership(String ownership) {this.ownership = checkSet(ownership);}
public String getDifferencekind() {return checkGet(differencekind);}
public void setDifferencekind(String differencekind) {this.differencekind = checkSet(differencekind);}
public String getPropertyno() {return checkGet(propertyno);}
public void setPropertyno(String propertyno) {this.propertyno = checkSet(propertyno);}
public String getSerialno() {return checkGet(serialno);}
public void setSerialno(String serialno) {this.serialno = checkSet(serialno);}
public String getCheckresult() {return checkGet(checkresult);}
public void setCheckresult(String checkresult) {this.checkresult = checkSet(checkresult);}
public String getScrappednote() {return checkGet(scrappednote);}
public void setScrappednote(String scrappednote) {this.scrappednote = checkSet(scrappednote);}
public String getLabelnote() {return checkGet(labelnote);}
public void setLabelnote(String labelnote) {this.labelnote = checkSet(labelnote);}
public String getMovenote() {return checkGet(movenote);}
public void setMovenote(String movenote) {this.movenote = checkSet(movenote);}
public String getOddscause() {return checkGet(oddscause);}
public void setOddscause(String oddscause) {this.oddscause = checkSet(oddscause);}

String toggleAll;
String strKeySet[] = null;
public String getToggleAll(){ if (!"".equals(checkGet(toggleAll))) return "checked"; else return "unchecked"; }    
public void setToggleAll(String s){ toggleAll=checkSet(s); }    
public String[] getsKeySet(){ return strKeySet; }
public void setsKeySet(String[] s){ strKeySet=s; }

/**
 * <br>
 * <br>目的：依查詢欄位查詢多筆資料
 * <br>參數：無
 * <br>傳回：傳回ArrayList
*/

public ArrayList queryAll() throws Exception{
	Map PropertyMap = null;
	if("init".equals(this.getState())){
		PropertyMap = TCGHCommon.getSyspk_PropertyCode2(getQ_enterOrg2()); //物品名稱
	} else {
		PropertyMap = TCGHCommon.getSyspk_PropertyCode2(getQ_enterOrg()); //物品名稱
	}
	
	Database db = new Database();
	ArrayList objList=new ArrayList();

	try {
		String sql = "";
		
		sql += " select "+
			   "enterorg, ownership, differencekind, differencekindname, propertyno, serialno, propertyname, propertyname1," +
			   "bookamount, actualamount,bookvalue, keepunitname, keepername, checkresult,"+ 
			   "scrappednote, labelnote, movenote "+  
			   "from UNTPD_CHECKNONEXP where 1=1";
		
				if("init".equals(this.getState())){
					if(!"".equals(getQ_enterOrg2())){
						 sql += " and enterorg = " + Common.sqlChar(getQ_enterOrg2()) ;
					}
				} else {
					if(!"".equals(getQ_enterOrg())){
						 sql += " and enterorg = " + Common.sqlChar(getQ_enterOrg()) ;
					} 
				}
				 
			    if(!"".equals(getQ_ownership())){
				     sql += " and ownership = "+ Common.sqlChar(getQ_ownership()) ;
			    }
			    
			    if(!"".equals(getQ_differenceKind())){
				     sql += " and differencekind = "+ Common.sqlChar(getQ_differenceKind()) ;
			    }
			    
			    if(!"".equals(getQ_propertyType())){
				     sql += " and propertytype = "+ Common.sqlChar(getQ_propertyType()) ;
			    }
			    
			    if(!"".equals(getQ_propertyNoS()) && !"".equals(getQ_propertyNoE()) ){
				     sql += " and propertyno between "+ Common.sqlChar(getQ_propertyNoS()) +" and "+ Common.sqlChar(getQ_propertyNoE());
			    }
			    
			    if(!"".equals(getQ_serialNoS()) && !"".equals(getQ_serialNoE()) ){
				     sql += " and serialno between "+ Common.sqlChar(getQ_serialNoS()) +" and "+ Common.sqlChar(getQ_serialNoE());
			    }
			    
			    if(!"".equals(getQ_buyDateS()) && !"".equals(getQ_buyDateE()) ){
				     sql += " and buydate between "+ Common.sqlChar(new Datetime().changeTaiwanYYMMDD(getQ_buyDateS(),"2")) +" and "+ Common.sqlChar(new Datetime().changeTaiwanYYMMDD(getQ_buyDateE(),"2"));  
			    }
			    
			    if(!"".equals(getQ_barcode())){
				     sql += " and barcode = "+ Common.sqlChar(getQ_barcode()) ;
			    }
			    
			    if(!"".equals(getQ_checkResult())){
				     sql += " and checkresult = "+ Common.sqlChar(getQ_checkResult()) ;
			    }
			    
			    if(!"".equals(getQ_oldPropertyNo())){
				     sql += " and oldpropertyno = "+ Common.sqlChar(getQ_oldPropertyNo()) ;
			    }

			    if(!"".equals(getQ_oldSerialNoS()) && !"".equals(getQ_oldSerialNoE()) ){
				     sql += " and oldserialno between "+ Common.sqlChar(getQ_oldSerialNoS()) +" and "+ Common.sqlChar(getQ_oldSerialNoE());
			    }
			    
			    
			    if(!"".equals(getQ_propertyKind())){
				     sql += " and propertykind = "+ Common.sqlChar(getQ_propertyKind()) ;
			    }
			    
			    if(!"".equals(getQ_fundType())){
				     sql += " and fundtype = "+ Common.sqlChar(getQ_fundType()) ;
			    }
			    
			    if(!"".equals(getQ_keepUnit())){
				     sql += " and keepunit = "+ Common.sqlChar(getQ_keepUnit()) ;
			    }
			    
			    if(!"".equals(getQ_keeper())){
				     sql += " and keeper = "+ Common.sqlChar(getQ_keeper()) ;
			    }
			    
			    if(!"".equals(getQ_useUnit())){
				     sql += " and useunit = "+ Common.sqlChar(getQ_useUnit()) ;
			    }
			    
			    if(!"".equals(getQ_userNo())){
				     sql += " and userno = "+ Common.sqlChar(getQ_userNo()) ;
			    }
			    
			    if(!"".equals(getQ_place1())){
				     sql += " and place1 = "+ Common.sqlChar(getQ_place1()) ;
			    }
			    
			    if(!"".equals(getQ_place())){
				     sql += " and place like "+ Common.sqlChar("%"+getQ_place()+"%") ;
			    }
			    
			    if(!"".equals(getQ_notes())){
				     sql += " and notes = "+ Common.sqlChar(getQ_notes()) ;
			    }
			    
			    if("Y".equals(getQ_scrappednote())){
				     sql += " and scrappednote = "+ Common.sqlChar(getQ_scrappednote()) ;
			    }
			    
			    if("Y".equals(getQ_labelnote())){
				     sql += " and labelnote = "+ Common.sqlChar(getQ_labelnote()) ;
			    }
			    
			    if("Y".equals(getQ_movenote())){
				     sql += " and movenote = "+ Common.sqlChar(getQ_movenote()) ;
			    }
		
			    sql += "  order by enterorg, ownership, differencekind, propertyno, serialno ";
	
		ResultSet rs = db.querySQL(sql.toString(),true);
		processCurrentPageAttribute(rs);
		if (getTotalRecord() > 0) {
			int count = getRecordStart();
			int end = getRecordEnd();
			do{
				if (count > end)
					break;
				
				String rowArray[]=new String[16];
				rowArray[0]=rs.getString("enterorg") == null? "" : rs.getString("enterorg");
				rowArray[1]=rs.getString("ownership")== null? "" : rs.getString("ownership");
				rowArray[2]=rs.getString("differencekind")== null? "" : rs.getString("differencekind");
				rowArray[3]=rs.getString("differencekindname")== null? "" : rs.getString("differencekindname");
				rowArray[4]=rs.getString("propertyno")== null? "" : rs.getString("propertyno");
				rowArray[5]=rs.getString("serialno")== null? "" : rs.getString("serialno");
				rowArray[6]=(String)PropertyMap.get(rs.getString("propertyno")) == null? "" :(String)PropertyMap.get(rs.getString("propertyno"));      //enterOrg+ propertyNo 取得SYSPK_PropertyCode2.propertyName
				rowArray[7]=rs.getString("bookamount")== null? "" : rs.getString("bookamount");
				rowArray[8]=rs.getString("actualamount")== null? "" : rs.getString("actualamount");
				rowArray[9]=rs.getString("bookvalue")== null? "" : Common.valueFormat(Common.get(rs.getString("bookvalue")));
				rowArray[10]=rs.getString("keepunitname")== null? "" : rs.getString("keepunitname");
				rowArray[11]=rs.getString("keepername")== null? "" : rs.getString("keepername");
				String checkresult = rs.getString("checkresult") == null? "" : rs.getString("checkresult");
				if("1".equals(checkresult)){
					rowArray[12]= "盤點正常";
				}else if("2".equals(checkresult)){
					rowArray[12]= "盤點異常";
				}else{
					rowArray[12]= "";
				}
				rowArray[13]=rs.getString("scrappednote")== null? "N" : rs.getString("scrappednote");
				rowArray[14]=rs.getString("labelnote")== null? "N" : rs.getString("labelnote");
				rowArray[15]=rs.getString("movenote")== null? "N" : rs.getString("movenote");

				objList.add(rowArray);
				count++;
			}while (rs.next());
		}
		

		setStateQueryAllSuccess();

	} catch (Exception e) {
		e.printStackTrace();
	} finally {
		db.closeAll();
	}
	return objList;
}


/**
 * <br>
 * <br>目的：依主鍵查詢單一資料
 * <br>參數：無
 * <br>傳回：傳回本物件
*/

//public UNTPD017F  queryOne() throws Exception{
//	Database db = new Database();
//	UNTPD017F obj = this;
//	try {
//		String sql=" select checkresult, scrappednote, labelnote, movenote, oddscause  "+
//			" from UNTPD_CHECKNONEXP  where 1=1 " ;
//			 if(!"".equals(getEnterorg())){
//				 sql += " and enterorg = " + Common.sqlChar(getEnterorg()) ;
//			 }
//			 if(!"".equals(getOwnership())){
//				 sql += " and ownership = " + Common.sqlChar(getOwnership()) ;
//			 }
//			 if(!"".equals(getDifferencekind())){
//				 sql += " and differencekind = " + Common.sqlChar(getDifferencekind()) ;
//			 }
//			 if(!"".equals(getPropertyno())){
//				 sql += " and propertyno = " + Common.sqlChar(getPropertyno()) ;
//			 }
//			 if(!"".equals(getSerialno())){
//				 sql += " and serialno = " + Common.sqlChar(getSerialno()) ;
//			 }
//		ResultSet rs = db.querySQL(sql);
//		if (rs.next()){
//			obj.setCheckresult(rs.getString("checkresult") == null ? "" : rs.getString("checkresult"));
//			obj.setScrappednote(rs.getString("scrappednote") == null ? "N" : rs.getString("scrappednote"));
//			obj.setLabelnote(rs.getString("labelnote") == null ? "N" : rs.getString("labelnote"));
//			obj.setMovenote(rs.getString("movenote") == null ? "N" : rs.getString("movenote"));
//			obj.setOddscause(rs.getString("oddscause") == null ? "" : rs.getString("oddscause"));
//		}
//		rs.getStatement().close();
//		rs.close();
//		setStateQueryOneSuccess();
//	} catch (Exception e) {
//		e.printStackTrace();
//	} finally {
//		db.closeAll();
//	}
//	return obj;
//}


//傳回update sql
protected String[] getUpdateSQL(){
	
	int getcut=0;
	String[] strKeys = null;
	String[] execSQLArray = null;
	if(getsKeySet()!=null){
		getcut = getsKeySet().length;	//有勾選的list中資料數
		execSQLArray = new String[getcut];
		for (int i=0; i<getcut; i++) {	//將挑選的資料組成一字串
			int count=0;
			strKeys = getsKeySet()[i].split(",");
			
			execSQLArray[i]=" update UNTPD_CHECKNONEXP set " +
	 		 " checkresult = " + Common.sqlChar(getCheckresult()) + "," ;
	 		 if("Y".equals(getScrappednote())){
	 			execSQLArray[i] += " scrappednote = 'Y', " ;
			 }else{
				execSQLArray[i] += " scrappednote = 'N', " ; 
			 }
	 		 if("Y".equals(getLabelnote())){
	 			execSQLArray[i] += " labelnote = 'Y', " ;
			 }else{
				execSQLArray[i] += " labelnote = 'N', " ; 
			 }
	 		 if("Y".equals(getMovenote())){
	 			execSQLArray[i] += " movenote = 'Y', " ;
			 }else{
				execSQLArray[i] += " movenote = 'N', " ; 
			 }
	 		 if ("1".endsWith(getCheckresult())) { //1:盤點正常;2:盤點異常
	 			 execSQLArray[i] += " actualamount = " + Common.sqlChar(strKeys[5]) + ", " ;
	 		 }
	 		 
	 		execSQLArray[i] += " oddscause = " + Common.sqlChar(getOddscause()) + " where 1=1 " ; 
	 		
			 if(count < strKeys.length){
				 if(!"".equals(strKeys[count])){
					 execSQLArray[i] += " and enterorg = " + Common.sqlChar(strKeys[count]) ;
				 }
			 }
			 count++;
			 if(count < strKeys.length){
				 if(!"".equals(strKeys[count])){
					 execSQLArray[i] += " and ownership = " + Common.sqlChar(strKeys[count]) ;
				 }
			 }
			 count++;
			 if(count < strKeys.length){
				 if(!"".equals(strKeys[count])){
					 execSQLArray[i] += " and differencekind = " + Common.sqlChar(strKeys[count]) ;
				 }
			 }
			 count++;
			 if(count < strKeys.length){
				 if(!"".equals(strKeys[count])){
					 execSQLArray[i] += " and propertyno = " + Common.sqlChar(strKeys[count]) ;
				 }
			 }
			 count++;
			 if(count < strKeys.length){
				 if(!"".equals(strKeys[count])){
					 execSQLArray[i] += " and serialno = " + Common.sqlChar(strKeys[count]) ;
				 }
			 }
		 
		}
	}
	return execSQLArray;
}


//傳回delete sql
//protected String[] getDeleteSQL(){
//	String[] execSQLArray = new String[1];
//	execSQLArray[0]=" delete UNTPD_CHECKNONEXP where 1=1 " ;
//	 if(!"".equals(getEnterorg())){
//		 execSQLArray[0] += " and enterorg = " + Common.sqlChar(getEnterorg()) ;
//	 }
//	 if(!"".equals(getOwnership())){
//		 execSQLArray[0] += " and ownership = " + Common.sqlChar(getOwnership()) ;
//	 }
//	 if(!"".equals(getDifferencekind())){
//		 execSQLArray[0] += " and differencekind = " + Common.sqlChar(getDifferencekind()) ;
//	 }
//	 if(!"".equals(getPropertyno())){
//		 execSQLArray[0] += " and propertyno = " + Common.sqlChar(getPropertyno()) ;
//	 }
//	 if(!"".equals(getSerialno())){
//		 execSQLArray[0] += " and serialno = " + Common.sqlChar(getSerialno()) ;
//	 }
//	return execSQLArray;
//}



}


