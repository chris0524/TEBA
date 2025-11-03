/*
*<br>程式目的：非消耗品增減值作業--批次新增明細資料
*<br>程式代號：untne026f
*<br>程式日期：0941123
*<br>程式作者：judy
*<br>--------------------------------------------------------
*<br>修改作者　　修改日期　　　修改目的
*<br>--------------------------------------------------------
*/

package unt.ne;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Map;

import unt.ch.UNTCH_Tools;
import util.Common;
import util.Database;
import util.Datetime;
import util.SuperBean;
import util.TCGHCommon;
import TDlib_Simple.tools.src.MathTools;

public class UNTNE026F extends SuperBean{

String enterOrg;
String differenceKind;
String enterOrgName;
String ownership;
String propertyNo;
String lotNo;
String propertyName;
String serialNo;
String propertyName1;
String propertyKind;
String fundType;
String valuable;
String sourceDate;
String bookAmount;
String keepUnit;
String keepUnitName;
String keeper;
String keeperName;
String useUnit;
String useUnitName;
String userNo;
String userName;
String place;
String oldPropertyNo;
String oldPropertyName;
String oldSerialNo;
String caseNo;

String q_enterOrg; 
String q_ownership; 
String q_caseNo;
String q_adjustDate;
String q_verify;

String q_cause;
String q_cause1;
String q_dataState;
String q_propertyNoS;
String q_propertyNoE;
String q_propertyNoSName;
String q_propertyNoEName;
String q_serialNoS;
String q_serialNoE;
String q_propertyKind;
String q_fundType;
String q_valuable;
String sSQL = "";
String strKeySet[] = null;
String bookValue;
String q_differenceKind;
String q_useUnit;
String q_userNo;
String q_userNote;
String q_place1;
String q_place1Name;
String q_placeNote;
String q_keepUnit;
String q_keeper;
public String getBookValue(){ return checkGet(bookValue); }
public void setBookValue(String s){ bookValue=checkSet(s); }

public String getsSQL(){ return checkGet(sSQL); }
public void setsSQL(String s){ sSQL=checkSet(s); }
public String[] getsKeySet(){ return strKeySet; }
public void setsKeySet(String[] s){ strKeySet=s; }
public String getEnterOrg(){ return checkGet(enterOrg); }
public void setEnterOrg(String s){ enterOrg=checkSet(s); }
public String getEnterOrgName(){ return checkGet(enterOrgName); }
public void setEnterOrgName(String s){ enterOrgName=checkSet(s); }
public String getOwnership(){ return checkGet(ownership); }
public void setOwnership(String s){ ownership=checkSet(s); }
public String getPropertyNo(){ return checkGet(propertyNo); }
public void setPropertyNo(String s){ propertyNo=checkSet(s); }
public String getPropertyName(){ return checkGet(propertyName); }
public void setPropertyName(String s){ propertyName=checkSet(s); }
public String getSerialNo(){ return checkGet(serialNo); }
public void setSerialNo(String s){ serialNo=checkSet(s); }
public String getLotNo(){ return checkGet(lotNo); }
public void setLotNo(String s){ lotNo=checkSet(s); }
public String getPropertyName1(){ return checkGet(propertyName1); }
public void setPropertyName1(String s){ propertyName1=checkSet(s); }
public String getPropertyKind(){ return checkGet(propertyKind); }
public void setPropertyKind(String s){ propertyKind=checkSet(s); }
public String getFundType(){ return checkGet(fundType); }
public void setFundType(String s){ fundType=checkSet(s); }
public String getValuable(){ return checkGet(valuable); }
public void setValuable(String s){ valuable=checkSet(s); }
public String getSourceDate(){ return checkGet(sourceDate); }
public void setSourceDate(String s){ sourceDate=checkSet(s); }
public String getBookAmount(){ return checkGet(bookAmount); }
public void setBookAmount(String s){ bookAmount=checkSet(s); }
public String getKeepUnit(){ return checkGet(keepUnit); }
public void setKeepUnit(String s){ keepUnit=checkSet(s); }
public String getKeepUnitName(){ return checkGet(keepUnitName); }
public void setKeepUnitName(String s){ keepUnitName=checkSet(s); }
public String getKeeper(){ return checkGet(keeper); }
public void setKeeper(String s){ keeper=checkSet(s); }
public String getKeeperName(){ return checkGet(keeperName); }
public void setKeeperName(String s){ keeperName=checkSet(s); }
public String getUseUnit(){ return checkGet(useUnit); }
public void setUseUnit(String s){ useUnit=checkSet(s); }
public String getUseUnitName(){ return checkGet(useUnitName); }
public void setUseUnitName(String s){ useUnitName=checkSet(s); }
public String getUserNo(){ return checkGet(userNo); }
public void setUserNo(String s){ userNo=checkSet(s); }
public String getUserName(){ return checkGet(userName); }
public void setUserName(String s){ userName=checkSet(s); }
public String getPlace(){ return checkGet(place); }
public void setPlace(String s){ place=checkSet(s); }

public String getOldPropertyNo(){ return checkGet(oldPropertyNo); }
public void setOldPropertyNo(String s){ oldPropertyNo=checkSet(s); }
public String getOldPropertyName(){ return checkGet(oldPropertyName); }
public void setOldPropertyName(String s){ oldPropertyName=checkSet(s); }
public String getOldSerialNo(){ return checkGet(oldSerialNo); }
public void setOldSerialNo(String s){ oldSerialNo=checkSet(s); }
public String getCaseNo(){ return checkGet(caseNo); }
public void setCaseNo(String s){ caseNo=checkSet(s); }

public String getQ_dataState(){ return checkGet(q_dataState); }
public void setQ_dataState(String s){ q_dataState=checkSet(s); }
public String getQ_propertyNoS(){ return checkGet(q_propertyNoS); }
public void setQ_propertyNoS(String s){ q_propertyNoS=checkSet(s); }
public String getQ_propertyNoE(){ return checkGet(q_propertyNoE); }
public void setQ_propertyNoE(String s){ q_propertyNoE=checkSet(s); }
public String getQ_propertyNoSName(){ return checkGet(q_propertyNoSName); }
public void setQ_propertyNoSName(String s){ q_propertyNoSName=checkSet(s); }
public String getQ_propertyNoEName(){ return checkGet(q_propertyNoEName); }
public void setQ_propertyNoEName(String s){ q_propertyNoEName=checkSet(s); }
public String getQ_serialNoS(){ return checkGet(q_serialNoS); }
public void setQ_serialNoS(String s){ q_serialNoS=checkSet(s); }
public String getQ_serialNoE(){ return checkGet(q_serialNoE); }
public void setQ_serialNoE(String s){ q_serialNoE=checkSet(s); }
public String getQ_propertyKind(){ return checkGet(q_propertyKind); }
public void setQ_propertyKind(String s){ q_propertyKind=checkSet(s); }
public String getQ_fundType(){ return checkGet(q_fundType); }
public void setQ_fundType(String s){ q_fundType=checkSet(s); }
public String getQ_valuable(){ return checkGet(q_valuable); }
public void setQ_valuable(String s){ q_valuable=checkSet(s); }
public String getQ_caseNo(){ return checkGet(q_caseNo); }
public void setQ_caseNo(String s){ q_caseNo=checkSet(s); }
public String getQ_adjustDate(){ return checkGet(q_adjustDate); }
public void setQ_adjustDate(String s){ q_adjustDate=checkSet(s); }
public String getQ_verify(){ return checkGet(q_verify); }
public void setQ_verify(String s){ q_verify=checkSet(s); }
public String getQ_cause(){ return checkGet(q_cause); }
public void setQ_cause(String s){ q_cause=checkSet(s); }
public String getQ_cause1(){ return checkGet(q_cause1); }
public void setQ_cause1(String s){ q_cause1=checkSet(s); }
public String getQ_enterOrg(){ return checkGet(q_enterOrg); }
public void setQ_enterOrg(String s){ q_enterOrg=checkSet(s); }
public String getQ_ownership(){ return checkGet(q_ownership); }
public void setQ_ownership(String s){ q_ownership=checkSet(s); }
public String getQ_differenceKind() {return checkGet(q_differenceKind);}
public void setQ_differenceKind(String s) {q_differenceKind = checkSet(s);}
public String getQ_useUnit() {return checkGet(q_useUnit);}
public void setQ_useUnit(String qUseUnit) {q_useUnit = checkSet(qUseUnit);}
public String getQ_userNo() {return checkGet(q_userNo);}
public void setQ_userNo(String qUserNo) {q_userNo = checkSet(qUserNo);}
public String getQ_userNote() {return checkGet(q_userNote);}
public void setQ_userNote(String q_userNote) {this.q_userNote = checkSet(q_userNote);}
public String getQ_place1() {return checkGet(q_place1);}
public void setQ_place1(String q_place1) {this.q_place1 = checkSet(q_place1);}
public String getQ_place1Name() {return checkGet(q_place1Name);}
public void setQ_place1Name(String q_place1Name) {this.q_place1Name = checkSet(q_place1Name);}
public String getQ_placeNote() {return checkGet(q_placeNote);}
public void setQ_placeNote(String q_placeNote) {this.q_placeNote = checkSet(q_placeNote);}
public String getQ_keepUnit(){ return checkGet(q_keepUnit); }
public void setQ_keepUnit(String s){ q_keepUnit=checkSet(s); }
public String getQ_keeper(){ return checkGet(q_keeper); }
public void setQ_keeper(String s){ q_keeper=checkSet(s); }
public String getDifferenceKind() {	return checkGet(differenceKind);}
public void setDifferenceKind(String s) {this.differenceKind = checkSet(s);}


String material;
String otherMaterial;
String propertyUnit;
String otherPropertyUnit;
String limitYear;
String otherLimitYear;

public String getMaterial(){ return checkGet(material); }
public void setMaterial(String s){ material=checkSet(s); }
public String getOtherMaterial(){ return checkGet(otherMaterial); }
public void setOtherMaterial(String s){ otherMaterial=checkSet(s); }
public String getPropertyUnit(){ return checkGet(propertyUnit); }
public void setPropertyUnit(String s){ propertyUnit=checkSet(s); }
public String getOtherPropertyUnit(){ return checkGet(otherPropertyUnit); }
public void setOtherPropertyUnit(String s){ otherPropertyUnit=checkSet(s); }
public String getLimitYear(){ return checkGet(limitYear); }
public void setLimitYear(String s){ limitYear=checkSet(s); }
public String getOtherLimitYear(){ return checkGet(otherLimitYear); }
public void setOtherLimitYear(String s){ otherLimitYear=checkSet(s); }

String toggleAll;
public String getToggleAll(){ if (!"".equals(checkGet(toggleAll))) return "checked"; else return "unchecked"; }    
public void setToggleAll(String s){ toggleAll=checkSet(s); }    

String closing;

public String getClosing(){ return checkGet(closing); }
public void setClosing(String s){ closing=checkSet(s); }

String adjustType;
String adjustBookValue;

public String getAdjustType(){ return checkGet(adjustType); }
public void setAdjustType(String s){ adjustType=checkSet(s); }
public String getAdjustBookValue(){ return checkGet(adjustBookValue); }
public void setAdjustBookValue(String s){ adjustBookValue=checkSet(s); }
String q_addBookValue;
String q_reduceBookValue;
public String getQ_addBookValue() {return checkGet(q_addBookValue);}
public void setQ_addBookValue(String q_addBookValue) {this.q_addBookValue = checkSet(q_addBookValue);}
public String getQ_reduceBookValue() {return checkGet(q_reduceBookValue);}
public void setQ_reduceBookValue(String q_reduceBookValue) {this.q_reduceBookValue = checkSet(q_reduceBookValue);}

String verifyError;
int bookValueCheck=0;
String valuableCheck="";
String fundTypeCheck = "";

public String getVerifyError(){ return checkGet(verifyError); }
public void setVerifyError(String s){ verifyError=checkSet(s); }

/**
 * <br>
 * <br>目的：傳回insert sql
 * <br>參數：無 
 * <br>傳回：無 ; 直接更改變數為新增成功與否
*/	
public void insert(){
	StringBuffer sbSQL = new StringBuffer("");
	UNTCH_Tools ul = new UNTCH_Tools();
	Database db = new Database();  
	String[] execSQLArray;
	setEditDate(Datetime.getYYYMMDD());
	setEditTime(Datetime.getHHMMSS());		
	int newBookValue = 0;
	MathTools mt = new MathTools();
	int getcut=0;
	if(getsKeySet()!=null)
		getcut = getsKeySet().length;	//有勾選的list中資料數
	//String sSQL = "";
	//String[] execSQLArray = new String[getcut];
	String[] strKeys = null;
	String oldBookUnit;
	String adjustBookUnit;
	String newBookUnit;
	ResultSet rs = null;
	int i = 0;	
	//int counter=0;
	try {
		for (i=0; i<getcut; i++) {	
			strKeys = getsKeySet()[i].split(",");
				//先找出 Attachment 的值
				String Attachmentsql =   " select  " +
										 " a.enterOrg, " +
										 " a.caseNo, " +
										 " a.ownership, " +
										 " a.caseSerialNo, " +
										 " a.propertyNo, " +
										 " b.serialNo, " +
										 " a.lotNo, " +
										 " a.differenceKind, " +
										 " a.propertyName1, " +
										 " a.propertyKind, " +
										 " a.fundType, " +
										 " a.valuable, " +
										 " a.sourceDate, "+
										 " b.oldPropertyNo, " +
										 " b.oldSerialNo, " +
										 " b.bookAmount," +
										 " a.othermaterial, " +
										 " a.otherpropertyunit, " +
										 " b.bookValue, " +
										 " a.originalUnit,a.buydate, " +
										 " b.keepUnit, b.keeper, b.useUnit, b.userNo, b.place "+
										 " from UNTNE_Nonexp a, UNTNE_NonexpDetail b "+
										 " where 1=1 " +
										 " and a.enterOrg=b.enterOrg and a.ownership=b.ownership and a.propertyNo=b.propertyNo and a.lotNo=b.lotNo and a.differenceKind=b.differenceKind " +
										 " and a.enterOrg = " + Common.sqlChar(strKeys[0]) +
										 " and a.ownership = " + Common.sqlChar(strKeys[1]) +
										 " and a.propertyNo = " + Common.sqlChar(strKeys[2]) +
										 " and b.serialNo = " + Common.sqlChar(strKeys[3]) +
										 " and a.differenceKind = " + Common.sqlChar(strKeys[4]) +
										 "";
//				System.out.println("############==>"+Attachmentsql);
				rs = db.querySQL(Attachmentsql);
				if (rs.next()){
				//insert 資料
					
					if(rs.getString("OriginalUnit")!=null){
						oldBookUnit = rs.getString("bookValue");
						newBookUnit = rs.getString("bookValue");
					}else{
						oldBookUnit = "";
						newBookUnit = "";
					}
					bookValueCheck = rs.getInt("bookValue");
					valuableCheck = checkGet(rs.getString("valuable"));
					fundTypeCheck = checkGet(rs.getString("fundType"));
					newBookValue = Integer.parseInt(rs.getString("bookValue")) + Integer.parseInt(getQ_addBookValue())-Integer.parseInt(getQ_reduceBookValue());					
					sbSQL.append(" insert into UNTNE_ADJUSTDETAIL(" ).append( 
							  " enterOrg,").append(
							  " differencekind,").append(
							  " buydate,").append(
							  " caseSerialNo,").append(
							  " ownership,").append(
							  " caseNo,").append(
							  " propertyNo,").append(
							  " lotNo,").append(
							  " serialNo,").append(
							  " propertyName1,").append(
							  " cause,").append(
							  " adjustDate,").append(
							  " verify,").append(
							  " propertyKind,").append(
							  " fundType,").append(
							  " valuable, ").append(
							  " sourceDate,").append(
							  " bookAmount,").append(
							  " oldBookValue,").append(
							  " newBookValue,").append(
							  " addBookValue,").append(
							  " reduceBookValue,").append(
							  " keepUnit,").append(
							  " keeper,").append(
							  " useUnit,").append(
							  " userNo,").append(
							  " place,").append(
							  " oldPropertyNo,").append(
							  " oldSerialNo,").append(
							  " othermaterial,").append(
							  " otherpropertyunit,").append(
							  " oldBookUnit," ).append(
							  " newBookUnit,").append(
							  " editID,").append(
							  " editDate,").append(
							  " editTime ").append(
							  ") values ( ").append( 
								Common.sqlChar(enterOrg)                    ).append( "," ).append(
								Common.sqlChar(differenceKind)                    ).append( "," ).append(
								Common.sqlChar(rs.getString("buydate"))    ).append( "," ).append(
								Common.sqlChar(rs.getString("caseSerialNo"))    ).append( "," ).append(
								Common.sqlChar(ownership)                   ).append( "," ).append(
								Common.sqlChar(caseNo)                      ).append( "," ).append(
								Common.sqlChar(rs.getString("propertyNo"))    ).append( "," ).append(
								Common.sqlChar(rs.getString("lotNo"))    		).append( "," ).append(
								Common.sqlChar(rs.getString("serialNo"))      ).append( "," ).append(
								Common.sqlChar(rs.getString("propertyName1")) ).append( "," ).append(
								Common.sqlChar(q_cause)                       ).append( "," ).append(
								Common.sqlChar("")                  ).append( "," ).append(
								Common.sqlChar("N")					  ).append( "," ).append(
								Common.sqlChar(rs.getString("propertyKind"))  ).append( "," ).append(
								Common.sqlChar(rs.getString("fundType"))      ).append( "," ).append(
								Common.sqlChar(rs.getString("valuable"))      ).append( "," ).append(
								Common.sqlChar(rs.getString("sourceDate"))    ).append( "," ).append(
								Common.sqlChar(rs.getString("bookAmount"))    ).append( "," ).append(
								Common.sqlChar(rs.getString("bookValue"))    ).append( "," ).append(
								Common.sqlChar(newBookValue+"")    ).append( "," ).append(
								Common.sqlChar(getQ_addBookValue())    ).append( "," ).append(
								Common.sqlChar(getQ_reduceBookValue())    ).append( "," ).append(
								Common.sqlChar(rs.getString("keepUnit")) 					  ).append( "," ).append(
								Common.sqlChar(rs.getString("keeper")) 						  ).append( "," ).append(
								Common.sqlChar(rs.getString("useUnit")) 					  ).append( "," ).append(
								Common.sqlChar(rs.getString("userNo")) 						  ).append( "," ).append(
								Common.sqlChar(rs.getString("place")) 						  ).append( "," ).append(
								Common.sqlChar(rs.getString("oldPropertyNo")) ).append( "," ).append(
								Common.sqlChar(rs.getString("oldSerialNo"))   ).append( "," ).append(
								Common.sqlChar(rs.getString("othermaterial"))   ).append( "," ).append(
								Common.sqlChar(rs.getString("otherpropertyunit"))   ).append( "," ).append(
								Common.sqlChar(oldBookUnit)					  ).append( "," ).append(
								Common.sqlChar(newBookUnit)					  ).append( "," ).append(
								Common.sqlChar(getEditID())                   ).append( "," ).append(
								Common.sqlChar(getEditDate())                 ).append( "," ).append(
								Common.sqlChar(getEditTime())                 ).append(
								" ):;:");	
				}
//				System.out.println("############==>"+sbSQL.toString());
//			if(adjustType.equals("2") && Integer.parseInt(adjustBookValue)>=bookValueCheck && valuableCheck.equals("N")){
//				 setVerifyError("Y");
//				 setErrorMsg("增減別為減少時，增減價值必須小於原帳面金額─總價。");
//				 break;
//			}else if(adjustType.equals("2") && Integer.parseInt(adjustBookValue)>bookValueCheck && valuableCheck.equals("Y")){
//				 setVerifyError("Y");
//				 setErrorMsg("增減別為減少時，增減價值必須小於等於原帳面金額─總價。");
//				 break;
//			}
				//System.out.println("11111==>"+getQ_reduceBookValue());
				//System.out.println("22222==>"+bookValueCheck);
				//System.out.println("33333==>"+fundTypeCheck);
				    if(!fundTypeCheck.equals("") && Integer.parseInt(getQ_reduceBookValue())>bookValueCheck){
					setVerifyError("Y");
					setErrorMsg("基金類別不為空時，減值必須小於等於原帳面金額─總價。");
					break;
				    }else if (valuableCheck.equals("Y")&&Integer.parseInt(getQ_reduceBookValue())>bookValueCheck){
					setVerifyError("Y");
					setErrorMsg("為珍貴財產時，減值必須小於等於原帳面金額─總價。");
					break;
				    }else if(Integer.parseInt(getQ_reduceBookValue())>bookValueCheck){
					setVerifyError("Y");
					setErrorMsg("減值必須小於原帳面金額─總價。");
					break;
				    }
				
				
				
				
		}
	} catch (Exception e) {
		e.printStackTrace();
	}
	try{
		if (!"Y".equals(getVerifyError())) {
			System.out.println("JJJJJJJJJJJJJJj==>");
			execSQLArray = sbSQL.toString().split(":;:");
			db.excuteSQL(execSQLArray);
			setStateInsertSuccess();
			setErrorMsg("新增完成");				
		} else {
			System.out.println("KKKKKKKKKKKK==>");
			setStateInsertSuccess();
	       queryOne();
		}
	} catch (Exception e) {
		e.printStackTrace();
	} finally {
		db.closeAll();
	}
}

/**
 * <br>
 * <br>目的：依主鍵查詢單一資料
 * <br>參數：無
 * <br>傳回：傳回本物件
*/

public UNTNE026F  queryOne() throws Exception{
	Database db = new Database();
	UNTNE026F obj = this;
	try {
		String sql=" select a.enterOrg, a.ownership, a.caseNo, a.propertyNo, b.serialNo, a.lotNo, a.propertyName1, " +
			" a.propertyKind, a.fundType, a.valuable, "+
			" a.sourceDate, b.bookAmount, " +
			" b.bookValue, b.keepUnit , b.keeper , b.useUnit, b.userNo , b.place, b.oldPropertyNo, b.oldSerialNo, " +
			" (select f.unitName from UNTMP_KeepUnit f where b.enterOrg=f.enterOrg and b.keepUnit=f.unitNo) as keepUnitName, "+
			" (select f.unitName from UNTMP_KeepUnit f where b.enterOrg=f.enterOrg and b.useUnit=f.unitNo) as useUnitName, "+
			" (select f.keeperName from UNTMP_Keeper f where b.enterOrg=f.enterOrg and b.keepUnit=f.unitNo and b.keeper=f.keeperNo) as keeperName, "+		
			" (select f.keeperName from UNTMP_Keeper f where b.enterOrg=f.enterOrg and b.useUnit=f.unitNo and b.userNo=f.keeperNo) as userName, "+		
			" c.organSName as enterOrgName, d.propertyName, d.material, d.propertyUnit, d.limitYear, a.otherMaterial, a.otherPropertyUnit, a.otherLimitYear "+
			" from UNTNE_Nonexp a, UNTNE_NonexpDetail b, SYSCA_ORGAN c, "+
			" SYSPK_PropertyCode2 d, SYSPK_PropertyCode2 e where 1=1 " +
			" and a.enterOrg=b.enterOrg and a.ownership=b.ownership and a.propertyNo=b.propertyNo and a.lotNo=b.lotNo" +
			" and a.enterOrg 	= " + Common.sqlChar(enterOrg) +
			" and a.ownership 	= " + Common.sqlChar(ownership) +
			" and a.propertyNo 	= " + Common.sqlChar(propertyNo) +
			" and b.serialNo 	= " + Common.sqlChar(serialNo) +
			" and a.enterOrg	= c.organID " +
			" and a.enterOrg = d.enterOrg " +
			" and a.propertyNo 	= d.propertyNo " +
			" and b.enterOrg = e.enterOrg(+) "+
			" and b.oldpropertyNo = e.propertyNo(+) "+
			"";
		ResultSet rs = db.querySQL(sql);
		if (rs.next()){
			obj.setEnterOrg(rs.getString("enterOrg"));
			obj.setEnterOrgName(rs.getString("enterOrgName"));
			obj.setOwnership(rs.getString("ownership"));
			obj.setPropertyNo(rs.getString("propertyNo"));
			obj.setPropertyName(rs.getString("propertyName"));
			obj.setMaterial(rs.getString("material"));
			obj.setOtherMaterial(rs.getString("otherMaterial"));
			obj.setPropertyUnit(rs.getString("propertyUnit"));
			obj.setOtherPropertyUnit(rs.getString("otherPropertyUnit"));
			obj.setLimitYear(rs.getString("limitYear"));
			obj.setOtherLimitYear(rs.getString("otherLimitYear"));
			obj.setSerialNo(rs.getString("serialNo"));
			obj.setLotNo(rs.getString("lotNo"));
			obj.setPropertyName1(rs.getString("propertyName1"));
			obj.setPropertyKind(rs.getString("propertyKind"));
			obj.setFundType(rs.getString("fundType"));
			obj.setValuable(rs.getString("valuable"));
			obj.setSourceDate(rs.getString("sourceDate"));
			obj.setBookValue(rs.getString("bookValue"));
			obj.setBookAmount(rs.getString("bookAmount"));
			obj.setOldPropertyNo(rs.getString("oldPropertyNo"));
			obj.setOldSerialNo(rs.getString("oldSerialNo"));
			obj.setKeepUnit(rs.getString("keepUnit"));
			obj.setKeepUnitName(rs.getString("keepUnitName"));
			obj.setKeeper(rs.getString("keeper"));
			obj.setKeeperName(rs.getString("keeperName"));
			obj.setUseUnit(rs.getString("useUnit"));
			obj.setUseUnitName(rs.getString("useUnitName"));
			obj.setUserNo(rs.getString("userNo"));
			obj.setUserName(rs.getString("userName"));
			obj.setPlace(rs.getString("place"));
		}
		setStateQueryOneSuccess();
	} catch (Exception e) {
		e.printStackTrace();
	} finally {
		db.closeAll();
	}
	return obj;
} 

/** 
 * <br>
 * <br>目的：依查詢欄位查詢多筆資料
 * <br>參數：無
 * <br>傳回：傳回ArrayList
*/

public ArrayList queryAll() throws Exception{
	Map<String,String> differencekindMap = TCGHCommon.getSysca_Code("DFK"); 					//財產區分別名稱
	Database db = new Database();
	ArrayList objList=new ArrayList();
	int counter=0;
	try {
		String sql= " select distinct a.enterorg, d.propertyname, a.ownership, a.caseno,a.differencekind, "+
					" a.propertyno, a.serialno, a.oldserialno,b.propertyname1," +
					" a.bookamount, a.bookvalue, "+
					" (select x.unitname from UNTMP_KEEPUNIT x where a.enterorg=x.enterorg and a.keepunit=x.unitno) as keepunitname, " +
					" (select y.keepername from UNTMP_KEEPER y where a.enterorg=y.enterorg  and a.keeper=y.keeperno) as keepername " +
					" ,chk.* " + 
					" from UNTNE_NONEXPDETAIL a " +
					" left join UNTNE_NONEXP b on a.enterorg=b.enterorg and a.ownership=b.ownership and a.propertyno=b.propertyno and a.lotno=b.lotno and a.differencekind=b.differencekind " +
					" left join SYSPK_PROPERTYCODE2 d on a.enterorg = d.enterorg and a.propertyno = d.propertyno" +
					" outer apply ( " + 
					" select top 1 x.* from ( " +
					"  select p.verify as chkverify ,isnull(p.proofyear, '') as chkproofyear ,isnull(p.proofdoc, '') as chkproofdoc ,isnull(p.proofno, '') as chkproofno " +
					"  from UNTNE_ADJUSTDETAIL d " + 
					"  left join UNTNE_ADJUSTPROOF p on d.enterorg = p.enterorg and d.caseno = p.caseno " +
					"  where d.enterorg = a.enterorg and d.ownership = a.ownership and d.differencekind = a.differencekind and d.propertyno = a.propertyno and d.serialno = a.serialno and d.verify!='Y' " +
					"  union all " +
					"  select p.verify as chkverify ,isnull(p.proofyear, '') as chkproofyear ,isnull(p.proofdoc, '') as chkproofdoc ,isnull(p.proofno, '') as chkproofno " + 
					"  from UNTNE_REDUCEDETAIL d " + 
					"  left join UNTNE_REDUCEPROOF p on d.enterorg = p.enterorg and d.caseno = p.caseno " +
					"  where d.enterorg = a.enterorg and d.ownership = a.ownership and d.differencekind = a.differencekind and d.propertyno = a.propertyno and d.serialno = a.serialno and d.verify!='Y' " +
					"  ) as x" + 
					" ) as chk " + 
					
					" where 1=1 and a.datastate='1' and a.verify='Y' " +
					" and a.enterorg 	= " + Common.sqlChar(this.getEnterOrg()) +
					" and a.ownership 	= " + Common.sqlChar(this.getOwnership()) +
					" and a.differencekind 	= " + Common.sqlChar(this.getDifferenceKind()) +
					"" ;
			if (!"".equals(getQ_propertyNoS()))
				sql+=" and b.propertyno >= " + Common.sqlChar(getQ_propertyNoS());		
			if (!"".equals(getQ_propertyNoE()))
				sql+=" and b.propertyno<=" + Common.sqlChar(getQ_propertyNoE());						
			if (!"".equals(getQ_serialNoS())){
				setQ_serialNoS(Common.formatFrontZero(getQ_serialNoS(),7));
				sql+=" and b.serialnos >= " + Common.sqlChar(getQ_serialNoS()) ;
			}
			if (!"".equals(getQ_serialNoE())){
				setQ_serialNoE(Common.formatFrontZero(getQ_serialNoE(),7));
				sql+=" and b.serialnoe <= " + Common.sqlChar(getQ_serialNoE()) ;
			}
			if (!"".equals(getQ_propertyKind()))
				sql+=" and b.propertykind = " + Common.sqlChar(getQ_propertyKind()) ;
			if (!"".equals(getQ_fundType()))
				sql+=" and b.fundtype = " + Common.sqlChar(getQ_fundType()) ;
			if (!"".equals(getQ_valuable()))
				sql+=" and b.valuable = " + Common.sqlChar(getQ_valuable()) ;
			if (!"".equals(getQ_keepUnit())){ 
				sql += " and a.keepunit = " + Common.sqlChar(getQ_keepUnit());
			}		
			if (!"".equals(getQ_keeper())){ 
				sql += " and a.keeper = " + Common.sqlChar(getQ_keeper());
			}
			if (!"".equals(getQ_useUnit())){ 
				sql += " and a.useunit = " + Common.sqlChar(getQ_useUnit());
			}
			if (!"".equals(getQ_userNo())){ 
				sql += " and a.userno = " + Common.sqlChar(getQ_userNo());
			}
			if (!"".equals(getQ_keepUnit()))
				sql+=" and a.keepunit = " + Common.sqlChar(getQ_keepUnit()) ;
			if (!"".equals(getQ_keeper()))
				sql+=" and a.keeper = " + Common.sqlChar(getQ_keeper()) ;
			if (!"".equals(getQ_userNote())){ 
				sql += " and a.usernote like " + Common.sqlChar("%" + getQ_userNote() + "%");
			}
			if (!"".equals(getQ_place1())){ 
				sql += " and a.place1 = " + Common.sqlChar(getQ_place1());
			}
			if (!"".equals(getQ_placeNote())){ 
				sql += " and a.placenote like " + Common.sqlChar("%" + getQ_placeNote() + "%");
			}
			sql+=" order by a.enterorg, a.ownership, a.propertyno, a.serialno ";
//			System.out.println("QQQQQQQQQQQQQQq==>"+sql);
		ResultSet rs = db.querySQL_NoChange(sql);
		//把查到的資料放入list裡..
		//和.jsp裡的 List區 內容要搭配起來
		while (rs.next()){
			counter++;
			String rowArray[]=new String[16];
			rowArray[0]=checkGet(rs.getString("enterOrg"));
			rowArray[1]=checkGet(rs.getString("ownership"));
			rowArray[2]=checkGet(rs.getString("propertyNo")); 
			rowArray[3]=checkGet(rs.getString("serialNo"));
			rowArray[4]=checkGet(rs.getString("differenceKind"));
			rowArray[5]=differencekindMap.get(rs.getString("differencekind"));
			rowArray[6]=checkGet(rs.getString("propertyNo")); 
			rowArray[7]=checkGet(rs.getString("serialNo"));
			rowArray[8]=checkGet(rs.getString("oldserialNo"));
			rowArray[9]=checkGet(rs.getString("propertyName"));
			rowArray[10]=checkGet(rs.getString("propertyName1"));
			rowArray[11]=checkGet(rs.getString("keepUnitName"));
			rowArray[12]=checkGet(rs.getString("keeperName"));
			rowArray[13]=checkGet(rs.getString("bookAmount"));
			rowArray[14]=checkGet(rs.getString("bookValue")); 
			
			String chkproofdesc = Common.concatStrs("字第", Common.concatStrs("年", rs.getString("chkproofyear"), rs.getString("chkproofdoc")), rs.getString("chkproofno"));
			if (!"".equals(chkproofdesc)) {
				rowArray[15] = checkGet(chkproofdesc + "號");
			} else {
				rowArray[15] = "";
			}
			objList.add(rowArray);
			if (counter==getListLimit()){
				setErrorMsg(getListLimitError());
				break;
			}
		}
		setStateQueryAllSuccess();
	} catch (Exception e) {
		e.printStackTrace();
	} finally {
		db.closeAll();
	}
	return objList;
}

}

