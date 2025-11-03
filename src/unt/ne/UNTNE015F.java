/*
*<br>程式目的：非消耗品減少作業－批次新增明細資料
*<br>程式代號：untne015f
*<br>程式日期：0941121
*<br>程式作者：judy
*<br>--------------------------------------------------------
*<br>修改作者　　修改日期　　　修改目的
*<br>--------------------------------------------------------
*/

package unt.ne;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import unt.ch.UNTCH_Tools;
import util.Common;
import util.Database;
import util.SuperBean;

public class UNTNE015F extends SuperBean{

String enterOrgName;
String oldPropertyName;
String propertyName;

String cause;
String cause1;
String newEnterOrg;
String transferDate;
String enterOrg;
String ownership;
String caseNo;
String reduceDate;
String differenceKind;
String verify;
String propertyNo;
String lotNo;
String serialNo;
String material;
String otherMaterial;
String propertyUnit;
String otherPropertyUnit;
String limitYear;
String otherLimitYear;
String propertyName1;
String enterDate;
String buyDate;
String propertyKind;
String fundType;
String valuable;
String accountingTitle;
String oldBookAmount;
String oldBookUnit;
String oldBookValue;
String articleName;
String nameplate;
String specification;
String sourceDate;
String licensePlate;
String moveDate;
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
String oldSerialNo;
String bookAmount;
String originalUnit;
String bookValue;

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
public String getBookAmount(){ return checkGet(bookAmount); }
public void setBookAmount(String s){ bookAmount=checkSet(s); }
public String getOriginalUnit(){ return checkGet(originalUnit); }
public void setOriginalUnit(String s){ originalUnit=checkSet(s); }
public String getBookValue(){ return checkGet(bookValue); }
public void setBookValue(String s){ bookValue=checkSet(s); }
public String getCause(){ return checkGet(cause); }
public void setCause(String s){ cause=checkSet(s); }
public String getCause1(){ return checkGet(cause1); }
public void setCause1(String s){ cause1=checkSet(s); }
public String getNewEnterOrg(){ return checkGet(newEnterOrg); }
public void setNewEnterOrg(String s){ newEnterOrg=checkSet(s); }
public String getTransferDate(){ return checkGet(transferDate); }
public void setTransferDate(String s){ transferDate=checkSet(s); }
public String getEnterOrg(){ return checkGet(enterOrg); }
public void setEnterOrg(String s){ enterOrg=checkSet(s); }
public String getOwnership(){ return checkGet(ownership); }
public void setOwnership(String s){ ownership=checkSet(s); }
public String getCaseNo(){ return checkGet(caseNo); }
public void setCaseNo(String s){ caseNo=checkSet(s); }
public String getReduceDate(){ return checkGet(reduceDate); }
public void setReduceDate(String s){ reduceDate=checkSet(s); }
public String getVerify(){ return checkGet(verify); }
public void setVerify(String s){ verify=checkSet(s); }
public String getPropertyNo(){ return checkGet(propertyNo); }
public void setPropertyNo(String s){ propertyNo=checkSet(s); }
public String getLotNo(){ return checkGet(lotNo); }
public void setLotNo(String s){ lotNo=checkSet(s); }
public String getSerialNo(){ return checkGet(serialNo); }
public void setSerialNo(String s){ serialNo=checkSet(s); }
public String getPropertyName1(){ return checkGet(propertyName1); }
public void setPropertyName1(String s){ propertyName1=checkSet(s); }
public String getEnterDate(){ return checkGet(enterDate); }
public void setEnterDate(String s){ enterDate=checkSet(s); }
public String getBuyDate(){ return checkGet(buyDate); }
public void setBuyDate(String s){ buyDate=checkSet(s); }
public String getPropertyKind(){ return checkGet(propertyKind); }
public void setPropertyKind(String s){ propertyKind=checkSet(s); }
public String getFundType(){ return checkGet(fundType); }
public void setFundType(String s){ fundType=checkSet(s); }
public String getValuable(){ return checkGet(valuable); }
public void setValuable(String s){ valuable=checkSet(s); }
public String getAccountingTitle(){ return checkGet(accountingTitle); }
public void setAccountingTitle(String s){ accountingTitle=checkSet(s); }
public String getOldBookAmount(){ return checkGet(oldBookAmount); }
public void setOldBookAmount(String s){ oldBookAmount=checkSet(s); }
public String getOldBookUnit(){ return checkGet(oldBookUnit); }
public void setOldBookUnit(String s){ oldBookUnit=checkSet(s); }
public String getOldBookValue(){ return checkGet(oldBookValue); }
public void setOldBookValue(String s){ oldBookValue=checkSet(s); }
public String getArticleName(){ return checkGet(articleName); }
public void setArticleName(String s){ articleName=checkSet(s); }
public String getNameplate(){ return checkGet(nameplate); }
public void setNameplate(String s){ nameplate=checkSet(s); }
public String getSpecification(){ return checkGet(specification); }
public void setSpecification(String s){ specification=checkSet(s); }
public String getSourceDate(){ return checkGet(sourceDate); }
public void setSourceDate(String s){ sourceDate=checkSet(s); }
public String getLicensePlate(){ return checkGet(licensePlate); }
public void setLicensePlate(String s){ licensePlate=checkSet(s); }
public String getMoveDate(){ return checkGet(moveDate); }
public void setMoveDate(String s){ moveDate=checkSet(s); }
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
public String getOldSerialNo(){ return checkGet(oldSerialNo); }
public void setOldSerialNo(String s){ oldSerialNo=checkSet(s); }
public String getDifferenceKind() {return checkGet(differenceKind);}
public void setDifferenceKind(String s) {differenceKind = checkSet(s);}


String q_enterOrg;
String q_dataState;
String q_ownership; 
String q_propertyNoS;
String q_propertyNoE;
String q_propertyNoSName;
String q_propertyNoEName;
String q_serialNoS;
String q_serialNoE;
String q_propertyKind;
String q_fundType;
String q_valuable;


String q_caseNo;
String q_reduceDate;
String q_verify;

String q_cause;
String q_cause1;
String q_newEnterOrg;
String q_newEnterOrgName;
String q_transferDate;
String q_differenceKind;

String sSQL = "";
String strKeySet[] = null;

public String getQ_enterOrg(){ return checkGet(q_enterOrg); }
public void setQ_enterOrg(String s){ q_enterOrg=checkSet(s); }
public String getQ_ownership(){ return checkGet(q_ownership); }
public void setQ_ownership(String s){ q_ownership=checkSet(s); }
public String getsSQL(){ return checkGet(sSQL); }
public void setsSQL(String s){ sSQL=checkSet(s); }
public String[] getsKeySet(){ return strKeySet; }
public void setsKeySet(String[] s){ strKeySet=s; }

public String getEnterOrgName(){ return checkGet(enterOrgName); }
public void setEnterOrgName(String s){ enterOrgName=checkSet(s); }
public String getOldPropertyName(){ return checkGet(oldPropertyName); }
public void setOldPropertyName(String s){ oldPropertyName=checkSet(s); }
public String getPropertyName(){ return checkGet(propertyName); }
public void setPropertyName(String s){ propertyName=checkSet(s); }
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
public String getQ_cause(){ return checkGet(q_cause); }
public void setQ_cause(String s){ q_cause=checkSet(s); }
public String getQ_cause1(){ return checkGet(q_cause1); }
public void setQ_cause1(String s){ q_cause1=checkSet(s); }
public String getQ_newEnterOrg(){ return checkGet(q_newEnterOrg); }
public void setQ_newEnterOrg(String s){ q_newEnterOrg=checkSet(s); }
public String getQ_newEnterOrgName(){ return checkGet(q_newEnterOrgName); }
public void setQ_newEnterOrgName(String s){ q_newEnterOrgName=checkSet(s); }
public String getQ_transferDate(){ return checkGet(q_transferDate); }
public void setQ_transferDate(String s){ q_transferDate=checkSet(s); }
public String getQ_caseNo(){ return checkGet(q_caseNo); }
public void setQ_caseNo(String s){ q_caseNo=checkSet(s); }
public String getQ_reduceDate(){ return checkGet(q_reduceDate); }
public void setQ_reduceDate(String s){ q_reduceDate=checkSet(s); }
public String getQ_verify(){ return checkGet(q_verify); }
public void setQ_verify(String s){ q_verify=checkSet(s); }
public String getQ_differenceKind() {return checkGet(q_differenceKind);}
public void setQ_differenceKind(String s) {q_differenceKind = checkSet(s);}

String toggleAll;
public String getToggleAll(){ if (!"".equals(checkGet(toggleAll))) return "checked"; else return "unchecked"; }    
public void setToggleAll(String s){ toggleAll=checkSet(s); }    

String q_buyDateS;
String q_buyDateE;

public String getQ_buyDateS(){ return checkGet(q_buyDateS); }
public void setQ_buyDateS(String s){ q_buyDateS=checkSet(s); }
public String getQ_buyDateE(){ return checkGet(q_buyDateE); }
public void setQ_buyDateE(String s){ q_buyDateE=checkSet(s); }

String closing;

public String getClosing(){ return checkGet(closing); }
public void setClosing(String s){ closing=checkSet(s); }

String q_keepBureau;
String q_keepUnit;
String q_keeper;


public String getQ_keepBureau(){ return checkGet(q_keepBureau); }
public void setQ_keepBureau(String s){ q_keepBureau=checkSet(s); }
public String getQ_keepUnit(){ return checkGet(q_keepUnit); }
public void setQ_keepUnit(String s){ q_keepUnit=checkSet(s); }
public String getQ_keeper(){ return checkGet(q_keeper); }
public void setQ_keeper(String s){ q_keeper=checkSet(s); }


String q_cause2;
public String getQ_cause2(){ return checkGet(q_cause2); }
public void setQ_cause2(String s){ q_cause2=checkSet(s); }
String q_returnPlace;
public String getQ_returnPlace(){ return checkGet(q_returnPlace); }
public void setQ_returnPlace(String s){ q_returnPlace=checkSet(s); }

String q_useUnit;
String q_userNo;
String q_userNote;
String q_place1;
String q_place1Name;
String q_placeNote;

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
String causeName;
public String getCauseName() {return checkGet(causeName);}
public void setCauseName(String causeName) {this.causeName = checkSet(causeName);}
	
	private int getCaseSerialNo(String enterOrg, String caseNo, String ownership) {
		String sql="select ISNULL(max(caseSerialNo),0)+1 as caseSerialNo "+
				" from UNTNE_ReduceDetail " +
				" where enterOrg = " + Common.sqlChar(enterOrg) + 
				" and caseNo = " + Common.sqlChar(caseNo) +
				" and ownership = " + Common.sqlChar(ownership);
		
		Database database = new Database();
		ResultSet rs = null;
		int caseSerialNo = 1;
		try {
			rs = database.querySQL(sql);
			if (rs.next()) {
				caseSerialNo = rs.getInt("caseSerialNo");
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (Exception e) {
					//ignore
				}
			}
			database.closeAll();
		}
		return caseSerialNo;
	}

/**
 * <br>
 * <br>目的：傳回insert sql
 * <br>參數：無 
 * <br>傳回：無 ; 直接更改變數為新增成功與否
*/	
protected String[] getInsertSQL(){
	UNTCH_Tools ul = new UNTCH_Tools();
	StringBuffer sbSQL = new StringBuffer("");
	Database db = new Database();  
	int getcut=0;
	if(getsKeySet()!=null)
		getcut = getsKeySet().length;	//有勾選的list中資料數
	//String sSQL = "";
	//String[] execSQLArray = new String[getcut];
	String[] strKeys = null;
	ResultSet rs = null;
	int i = 0;	
	//int counter=0;
	Map<String, Integer> caseSerialNoMap = new HashMap<String, Integer>();
	try {
		for (i=0; i<getcut; i++) {	
		strKeys = getsKeySet()[i].split(",");
			//先找出 movable 和 movabledetail 的值
		    String movablesql = " select b.propertyNo , b.lotNo , b.serialNo ,a.differenceKind,a.caseSerialNo,  "+
							    " a.otherMaterial, a.otherPropertyUnit, a.otherLimitYear , a.propertyName1 , a.enterDate ,  a.buyDate , " +
								" a.propertyKind , a.fundType , a.valuable , a.accountingTitle ," +
								" b.bookAmount , (case a.originalUnit when '' then a.originalUnit else b.bookValue end) as originalUnit, b.bookValue , "+
								" a.articleName , a.nameplate , a.specification , a.sourceDate , "+
								" b.licensePlate, b.moveDate , b.keepUnit , b.keeper , b.useUnit , b.userNo , b.place ,b.userNote,b.place1, "+
								" b.oldPropertyNo , b.oldSerialNo " +
								" from untne_nonexp a , untne_nonexpDetail b "+
								" where a.enterOrg = b.enterOrg "+
								" and a.ownership = b.ownership"+
								" and a.differencekind = b.differencekind"+
								" and a.propertyno = b.propertyno " +
								" and a.lotNo=b.lotNo "+
								" and b.enterOrg = " + Common.sqlChar(strKeys[0]) +
								" and b.ownership = " + Common.sqlChar(strKeys[1]) +
								" and b.differenceKind = " + Common.sqlChar(strKeys[2]) +
								" and b.propertyNo = " + Common.sqlChar(strKeys[3]) +
								" and b.serialNo = " + Common.sqlChar(strKeys[4]) +
								" " ;

			rs = db.querySQL(movablesql);
			if (rs.next()){
			    //已使用月數 = (系統日期 - 建築日期) ★/12 之餘額月數
				// total month = 月份 + 年*12月    format:yyymmdd
				int sMonth = Integer.parseInt(getEditDate().substring(3,5)) + (Integer.parseInt(getEditDate().substring(0,3)) * 12 ); 
				int eMonth = Integer.parseInt(ul._transToROC_Year(rs.getString("buyDate")).substring(3,5)) + (Integer.parseInt(ul._transToROC_Year(rs.getString("buyDate")).substring(0,3)) * 12 );
				String useMonth = ""+(sMonth - eMonth) % 12 ;

			    //已使用年數 = (系統日期 - 建築日期) ★/12 之商數
			    String useYear = ""+(sMonth - eMonth) / 12  ;
			    
			    String caseSerialNoKey = enterOrg + "_" + caseNo + "_" + ownership;
			    Integer caseSerialNo = caseSerialNoMap.get(caseSerialNoKey);
			    if (caseSerialNo == null) {
			    	caseSerialNo = this.getCaseSerialNo(enterOrg, caseNo, ownership);
			    }
			    caseSerialNoMap.put(caseSerialNoKey, caseSerialNo + 1);
			    
			//insert 資料
			    sbSQL.append(" insert into UNTNE_ReduceDetail(" ).append(
				  " enterOrg,").append(
				  " ownership,").append(
				  " caseNo,").append(
				  " reduceDate,").append(
				  " verify,").append(
				  " cause,").append(
				  " cause1,").append(
				  " newEnterOrg,").append(
				  " transferDate,").append(
				  " propertyNo, " ).append(
				  " lotNo," ).append(
				  " serialNo," ).append(
				  " otherMaterial," ).append(
				  " otherPropertyUnit,").append(
				  " otherLimitYear," ).append(
				  " propertyName1," ).append(
				  " enterDate," ).append(
				  " buyDate," ).append(
				  " propertyKind," ).append(
				  " fundType," ).append(
				  " valuable," ).append(
				  " accountingTitle," ).append(
				  " oldBookAmount," ).append(
				  " oldBookUnit," ).append(
				  " oldBookValue," ).append(
				  " articleName," ).append(
				  " nameplate," ).append(
				  " specification," ).append(
				  " sourceDate," ).append(
				  " licensePlate," ).append(
				  " moveDate," ).append(
				  " keepUnit," ).append(
				  " keeper," ).append(
				  " useUnit," ).append(
				  " userNo," ).append(
				  " place1," ).append(
				  " oldPropertyNo," ).append(
				  " oldSerialNo," ).append(	
				  " adjustBookAmount,").append(
				  " adjustBookValue,").append(
				  " adjustBookUnit,").append(
				  " newBookAmount,").append(
				  " newBookValue,").append(
				  " newBookUnit,").append(
				  " useYear," ).append(
				  " useMonth," ).append(
				  " cause2," ).append(			//2009.03.27 add >shsn
				  " returnPlace," ).append(		//2009.03.27 add >shsn
				  " editID,").append(
				  " editDate,").append(
				  " editTime, ").append(
				  " differenceKind,").append(
				  " caseSerialNo,").append(
				  " userNote,").append(
				  " place").append(
				  ") values ( ").append( 
				 	Common.sqlChar(enterOrg)						).append( "," ).append(
					Common.sqlChar(ownership)						).append( "," ).append(
					Common.sqlChar(caseNo)						).append( "," ).append(
					Common.sqlChar(reduceDate)					).append( "," ).append(
					Common.sqlChar(verify)						).append( "," ).append(
					Common.sqlChar(q_cause)							).append( "," ).append(
					Common.sqlChar(q_cause1)						).append( "," ).append(
					Common.sqlChar(q_newEnterOrg)					).append( "," ).append(
					Common.sqlChar(q_transferDate)					).append( "," ).append(
					Common.sqlChar(rs.getString("propertyNo"))		).append( "," ).append(				  	
					Common.sqlChar(rs.getString("lotNo"))			).append( "," ).append(
					Common.sqlChar(rs.getString("serialNo"))		).append( "," ).append(
					Common.sqlChar(rs.getString("otherMaterial"))		).append( "," ).append(
					Common.sqlChar(rs.getString("otherPropertyUnit"))		).append( "," ).append(
					Common.sqlChar(rs.getString("otherLimitYear"))	).append( "," ).append(
					Common.sqlChar(rs.getString("propertyName1"))	).append( "," ).append(
					Common.sqlChar(rs.getString("enterDate"))		).append( "," ).append(
					Common.sqlChar(rs.getString("buyDate"))			).append( "," ).append(
					Common.sqlChar(rs.getString("propertyKind"))	).append( "," ).append(
					Common.sqlChar(rs.getString("fundType"))		).append( "," ).append(
					Common.sqlChar(rs.getString("valuable"))		).append( "," ).append(
					Common.sqlChar(rs.getString("accountingTitle"))	).append( "," ).append(
					Common.sqlChar(rs.getString("bookAmount"))	).append( "," ).append(
					Common.sqlChar(rs.getString("originalUnit"))		).append( "," ).append(					
					Common.sqlChar(rs.getString("bookValue"))	).append( "," ).append(
					Common.sqlChar(rs.getString("articleName"))		).append( "," ).append(
					Common.sqlChar(rs.getString("nameplate"))		).append( "," ).append(
					Common.sqlChar(rs.getString("specification"))	).append( "," ).append(
					Common.sqlChar(rs.getString("sourceDate"))		).append( "," ).append(
					Common.sqlChar(rs.getString("licensePlate"))	).append( "," ).append(
					Common.sqlChar(rs.getString("moveDate"))		).append( "," ).append(
					Common.sqlChar(rs.getString("keepUnit"))		).append( "," ).append(
					Common.sqlChar(rs.getString("keeper"))			).append( "," ).append(
					Common.sqlChar(rs.getString("useUnit"))			).append( "," ).append(
					Common.sqlChar(rs.getString("userNo"))			).append( "," ).append(
					Common.sqlChar(rs.getString("place1"))			).append( "," ).append(
					Common.sqlChar(rs.getString("oldPropertyNo"))	).append( "," ).append(
					Common.sqlChar(rs.getString("oldSerialNo"))		).append( "," ).append(
					Common.sqlChar(rs.getString("bookAmount"))		).append( "," ).append(
					Common.sqlChar(rs.getString("bookValue"))		).append( "," ).append(
					Common.sqlChar(rs.getString("originalUnit"))		).append( "," ).append(
					Common.sqlChar("0")		).append( "," ).append(
					Common.sqlChar("0")		).append( "," ).append(
					Common.sqlChar("0")		).append( "," ).append(
					Common.sqlChar(useYear)		).append( "," ).append(
					Common.sqlChar(useMonth)		).append( "," ).append(
					Common.sqlChar(getQ_cause2())		).append( "," ).append(			//2009.03.27 add >shsn
					Common.sqlChar(getQ_returnPlace())		).append( "," ).append(		//2009.03.27 add >shsn
					Common.sqlChar(getEditID())                   	).append( "," ).append(
					Common.sqlChar(getEditDate())                 	).append( "," ).append(
					Common.sqlChar(getEditTime())                 	).append( "," ).append(
					Common.sqlChar(differenceKind)                 	).append( "," ).append(
					Common.sqlChar(Common.formatFrontZero(String.valueOf(caseSerialNo),5))).append( "," ).append(
					Common.sqlChar(rs.getString("userNote"))        ).append( "," ).append(
					Common.sqlChar(rs.getString("place"))           ).append(
					" ):;:");	
			}
		}
			//setStateQueryOneSuccess();
	} catch (Exception e) {
		e.printStackTrace();
	} finally {
		db.closeAll();
		
		if (caseSerialNoMap != null) {
			caseSerialNoMap.clear();
			caseSerialNoMap = null;
		}
	}
	//execSQLArray[i] = sSQL;
	setStateInsertSuccess();
	return sbSQL.toString().split(":;:");		        
}

/**
 * <br>
 * <br>目的：依主鍵查詢單一資料
 * <br>參數：無
 * <br>傳回：傳回本物件
*/

public UNTNE015F  queryOne() throws Exception{
	Database db = new Database();
	UNTNE015F obj = this;
	try {
		String sql= " select a.enterOrg, c.organSName as enterOrgName, a.ownership, " +
					" a.propertyNo, a.lotNo, a.serialNo, d.propertyName, " +
					" d.propertyUnit, b.otherPropertyUnit, d.material, b.otherMaterial, d.limitYear, b.otherLimitYear, " +
					" b.propertyName1, b.enterDate, b.buyDate, b.propertyKind, "+
					" b.fundType, b.valuable, b.accountingTitle, a.bookAmount, b.originalUnit, a.bookValue, b.articleName, b.nameplate, b.specification, b.sourceDate, " +
					" a.licensePlate,a.moveDate, a.keepUnit, a.keeper, a.useUnit, a.userNo, a.place, " +
					" (select f.unitName from UNTMP_KeepUnit f where a.enterOrg=f.enterOrg and a.keepUnit=f.unitNo) as keepUnitName, "+
					" (select f.unitName from UNTMP_KeepUnit f where a.enterOrg=f.enterOrg and a.useUnit=f.unitNo) as useUnitName, "+
					" (select f.keeperName from UNTMP_Keeper f where a.enterOrg=f.enterOrg and a.keeper=f.keeperNo) as keeperName, "+		
					" (select f.keeperName from UNTMP_Keeper f where a.enterOrg=f.enterOrg and a.userNo=f.keeperNo) as userName, " +
					"  a.oldPropertyNo, a.oldSerialNo "+		
					" from UNTNE_NonexpDetail a, UNTNE_Nonexp b, SYSCA_ORGAN c, "+
					" SYSPK_PropertyCode2 d " +
					" where 1=1 " +
					" and a.enterOrg=b.enterOrg and a.ownership=b.ownership and a.propertyNo=b.propertyNo and a.lotNo=b.lotNo "+
					" and a.enterOrg 	= " + Common.sqlChar(enterOrg) +
					" and a.ownership 	= " + Common.sqlChar(ownership) +
					" and b.caseNo 		= " + Common.sqlChar(Common.formatFrontZero(caseNo,10)) +
					" and a.propertyNo 	= " + Common.sqlChar(propertyNo) +
					" and a.serialNo 	= " + Common.sqlChar(serialNo) +
					" and a.enterOrg	= c.organID " +
					" and a.enterOrg = d.enterOrg " +
					" and a.propertyNo 	= d.propertyNo "+
					" " ;
		ResultSet rs = db.querySQL(sql);
		if (rs.next()){
		    obj.setEnterOrg(rs.getString("enterOrg"));
		    obj.setEnterOrgName(rs.getString("enterOrgName"));
		    obj.setOwnership(rs.getString("ownership"));
			obj.setPropertyNo(rs.getString("propertyNo"));
			obj.setPropertyName(rs.getString("propertyName"));
			obj.setLotNo(rs.getString("lotNo"));
			obj.setSerialNo(rs.getString("serialNo"));
			obj.setMaterial(rs.getString("material"));
			obj.setOtherMaterial(rs.getString("otherMaterial"));
			obj.setPropertyUnit(rs.getString("propertyUnit"));
			obj.setOtherPropertyUnit(rs.getString("otherPropertyUnit"));
			obj.setLimitYear(rs.getString("limitYear"));
			obj.setOtherLimitYear(rs.getString("otherLimitYear"));
			obj.setPropertyName1(rs.getString("propertyName1"));
			obj.setEnterDate(rs.getString("enterDate"));
			obj.setBuyDate(rs.getString("buyDate"));
			obj.setPropertyKind(rs.getString("propertyKind"));
			obj.setFundType(rs.getString("fundType"));
			obj.setValuable(rs.getString("valuable"));
			obj.setAccountingTitle(rs.getString("accountingTitle"));
			obj.setBookAmount(rs.getString("bookAmount"));
			obj.setOriginalUnit(rs.getString("originalUnit"));
			obj.setBookValue(rs.getString("bookValue"));
			obj.setArticleName(rs.getString("articleName"));
			obj.setNameplate(rs.getString("nameplate"));
			obj.setSpecification(rs.getString("specification"));
			obj.setSourceDate(rs.getString("sourceDate"));
			obj.setLicensePlate(rs.getString("licensePlate"));
			obj.setMoveDate(rs.getString("moveDate"));
			obj.setKeepUnit(rs.getString("keepUnit"));
			obj.setKeepUnitName(rs.getString("keepUnitName"));
			obj.setKeeper(rs.getString("keeper"));
			obj.setKeeperName(rs.getString("keeperName"));
			obj.setUseUnit(rs.getString("useUnit"));
			obj.setUseUnitName(rs.getString("useUnitName"));
			obj.setUserNo(rs.getString("userNo"));
			obj.setUserName(rs.getString("userName"));
			obj.setPlace(rs.getString("place"));
			obj.setOldPropertyNo(rs.getString("oldPropertyNo"));
			obj.setOldSerialNo(rs.getString("oldSerialNo"));
			obj.setOldPropertyName(rs.getString("oldPropertyNo"));
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
	Database db = new Database();
	ArrayList objList=new ArrayList();
	int counter=0;
	try {
		String sql=" select a.enterorg, " +
				" a.ownership, " +
				" b.caseno, " +
				" a.propertyno, " +
				" a.serialno," +
				" a.oldserialno," +
				" d.propertyname," 	+
				" b.propertyname1," 	+
				" a.differencekind," 	+
				" a.lotno," 	+
				" (case b.propertykind when '01' then '公務用' when '02' then '公共用' when '03' then '事業用' when '04' then '非公用' else '' end) propertykind,"+
				"(select unitname from UNTMP_KEEPUNIT z where z.enterorg = a.enterorg and z.unitno = a.keepunit) as keepunit," +
				"(select keepername from UNTMP_KEEPER z where z.enterorg = a.enterorg and z.keeperno = a.keeper) as keeper," +
				" (select placename from SYSCA_PLACE z where z.enterorg = a.enterorg and z.placeno = a.place1) as place1name " +
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
				" and a.enterorg 	= " + Common.sqlChar(q_enterOrg) +
				" and a.ownership 	= " + Common.sqlChar(q_ownership) +
				" and a.differencekind 	= " + Common.sqlChar(q_differenceKind) +
				"" ;
			if (!"".equals(getQ_propertyNoS()))
				sql+=" and a.propertyno >= " + Common.sqlChar(getQ_propertyNoS());		
			if (!"".equals(getQ_propertyNoE()))
				sql+=" and a.propertyno<=" + Common.sqlChar(getQ_propertyNoE());						
			if (!"".equals(getQ_serialNoS())){
				setQ_serialNoS(Common.formatFrontZero(getQ_serialNoS(),7));
				sql+=" and a.serialno >= " + Common.sqlChar(getQ_serialNoS()) ;
			}
			if (!"".equals(getQ_serialNoE())){
				setQ_serialNoE(Common.formatFrontZero(getQ_serialNoE(),7));
				sql+=" and a.serialno <= " + Common.sqlChar(getQ_serialNoE()) ;
			}					
			if (!"".equals(getQ_propertyKind()))
				sql+=" and b.propertykind = " + Common.sqlChar(getQ_propertyKind()) ;
			if (!"".equals(getQ_fundType()))
				sql+=" and b.fundtype = " + Common.sqlChar(getQ_fundType()) ;
			if (!"".equals(getQ_valuable()))
				sql+=" and b.valuable = " + Common.sqlChar(getQ_valuable()) ;
			if (!"".equals(getQ_buyDateS()))
				sql+=" and b.buydate >= " + Common.sqlChar(getQ_buyDateS());
			if (!"".equals(getQ_buyDateE()))
				sql+=" and b.buydate <= " + Common.sqlChar(getQ_buyDateE());
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
			if (!"".equals(getQ_differenceKind())){ 
				sql += " and a.differencekind = " + Common.sqlChar(getQ_differenceKind());
			}
			sql+=" order by a.enterorg, a.ownership, a.propertyno, a.serialno ";
			
		//System.out.println(sql);
		ResultSet rs = db.querySQL_NoChange(sql);
		//把查到的資料放入list裡..
		//和.jsp裡的 List區 內容要搭配起來
		while (rs.next()){
			counter++;
			String rowArray[]=new String[13];
			rowArray[0]=rs.getString("enterOrg");
			rowArray[1]=rs.getString("ownership");
			rowArray[2]=rs.getString("differenceKind");
			rowArray[3]=rs.getString("propertyNo"); 
			rowArray[4]=rs.getString("serialNo");
			rowArray[5]=rs.getString("oldserialNo");
			rowArray[6]=checkGet(rs.getString("lotNo"));
			rowArray[7]=checkGet(rs.getString("propertyName"));
			rowArray[8]=checkGet(rs.getString("propertyName1"));
			rowArray[9]=checkGet(rs.getString("keepunit"));
			rowArray[10]=checkGet(rs.getString("keeper"));
			rowArray[11]=checkGet(rs.getString("place1Name"));
			
			String chkproofdesc = Common.concatStrs("字第", Common.concatStrs("年", rs.getString("chkproofyear"), rs.getString("chkproofdoc")), rs.getString("chkproofno"));
			if (!"".equals(chkproofdesc)) {
				rowArray[12] = checkGet(chkproofdesc + "號");
			} else {
				rowArray[12] = "";
			}
			
			objList.add(rowArray);
//			if (counter==getListLimit()){
//				setErrorMsg(getListLimitError());
//				break;
//			}
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
