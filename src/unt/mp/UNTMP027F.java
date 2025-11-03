/*
*<br>程式目的：動產增減值作業－批次新增明細資料
*<br>程式代號：untmp027f
*<br>程式日期：0941122
*<br>程式作者：judy
*<br>--------------------------------------------------------
*<br>修改作者　　修改日期　　　修改目的
*<br>--------------------------------------------------------
*/

package unt.mp;

import java.sql.ResultSet;
import java.util.ArrayList;
import util.*;

public class UNTMP027F extends SuperBean{

String enterOrg;
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

String adjustType;
String adjustBookValue;

public String getAdjustType(){ return checkGet(adjustType); }
public void setAdjustType(String s){ adjustType=checkSet(s); }
public String getAdjustBookValue(){ return checkGet(adjustBookValue); }
public void setAdjustBookValue(String s){ adjustBookValue=checkSet(s); }

String verifyError;
int bookValueCheck=0;
String valuableCheck="";

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
	Database db = new Database();  
	String[] execSQLArray;
	//setEditID(getUserID());
	setEditDate(Datetime.getYYYMMDD());
	setEditTime(Datetime.getHHMMSS());		
	int newBookValue=0;
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
			String Attachmentsql =   " select " +
									 " a.enterOrg, " +
									 " a.ownership, " + 
									 " a.propertyNo, " +
									 " b.serialNo, " +
									 " a.lotNo, " +
									 " a.propertyName1, " +
									 " a.propertyKind, " +
									 " a.fundType, " +
									 " a.valuable, " +
									 " a.sourceDate, "+
									 " b.oldPropertyNo, " +
									 " b.oldSerialNo, " +
									 " b.bookAmount, " +
									 " b.bookValue, " +
									 " a.originalUnit, " +
									 " b.keepUnit, b.keeper, b.useUnit, b.userNo, b.place "+
									 " from UNTMP_Movable a, UNTMP_MovableDetail b "+
									 " where 1=1 " +
									 " and a.enterOrg=b.enterOrg and a.ownership=b.ownership and a.propertyNo=b.propertyNo and a.lotNo=b.lotNo " +
									 " and a.enterOrg = " + Common.sqlChar(strKeys[0]) +
									 " and a.ownership = " + Common.sqlChar(strKeys[1]) +
									 " and a.propertyNo = " + Common.sqlChar(strKeys[2]) +
									 " and b.serialNo = " + Common.sqlChar(strKeys[3]) +
									 "";
			rs = db.querySQL(Attachmentsql);
			if (rs.next()){
			//insert 資料
				enterOrg = rs.getString("enterOrg");
				ownership = rs.getString("ownership");
				propertyNo = rs.getString("propertyNo");
				serialNo = rs.getString("serialNo");
				if(rs.getString("OriginalUnit")!=null){
					oldBookUnit = rs.getString("bookValue");
					adjustBookUnit = "0";
					newBookUnit = rs.getString("bookValue");
				}else{
					oldBookUnit = "";
					adjustBookUnit = "";
					newBookUnit = "";
				}
				bookValueCheck = rs.getInt("bookValue");
				valuableCheck = rs.getString("valuable");
				if(adjustType.equals("1")){
					newBookValue = rs.getInt("bookValue") + Integer.parseInt(adjustBookValue);
				}else if(adjustType.equals("2")){
					newBookValue = rs.getInt("bookValue") - Integer.parseInt(adjustBookValue);
				}
				sbSQL.append(" insert into UNTMP_AdjustDetail(" ).append( 
						  " enterOrg,").append(
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
						  " adjustBookValue,").append(
						  " keepUnit,").append(
						  " keeper,").append(
						  " useUnit,").append(
						  " userNo,").append(
						  " place,").append(
						  " oldPropertyNo,").append(
						  " oldSerialNo,").append(
						  " adjustType," ).append(
						  " oldBookUnit," ).append(
						  " adjustBookUnit," ).append(
						  " newBookUnit,").append(
						  " closing,").append(
						  " editID,").append(
						  " editDate,").append(
						  " editTime ").append(
						  ") values ( ").append( 
							Common.sqlChar(q_enterOrg)                    ).append( "," ).append(
							Common.sqlChar(q_ownership)                   ).append( "," ).append(
							Common.sqlChar(q_caseNo)                      ).append( "," ).append(
							Common.sqlChar(rs.getString("propertyNo"))    ).append( "," ).append(
							Common.sqlChar(rs.getString("lotNo"))    		).append( "," ).append(
							Common.sqlChar(rs.getString("serialNo"))      ).append( "," ).append(
							Common.sqlChar(rs.getString("propertyName1")) ).append( "," ).append(
							Common.sqlChar(q_cause)                       ).append( "," ).append(
							Common.sqlChar(q_adjustDate)                  ).append( "," ).append(
							Common.sqlChar("N")					  ).append( "," ).append(
							Common.sqlChar(rs.getString("propertyKind"))  ).append( "," ).append(
							Common.sqlChar(rs.getString("fundType"))      ).append( "," ).append(
							Common.sqlChar(rs.getString("valuable"))      ).append( "," ).append(
							Common.sqlChar(rs.getString("sourceDate"))    ).append( "," ).append(
							Common.sqlChar(rs.getString("bookAmount"))    ).append( "," ).append(
							Common.sqlChar(rs.getString("bookValue"))    ).append( "," ).append(
							Common.sqlChar(newBookValue+"")    ).append( "," ).append(
							Common.sqlChar(adjustBookValue)    ).append( "," ).append(
							Common.sqlChar(rs.getString("keepUnit")) 					  ).append( "," ).append(
							Common.sqlChar(rs.getString("keeper")) 						  ).append( "," ).append(
							Common.sqlChar(rs.getString("useUnit")) 					  ).append( "," ).append(
							Common.sqlChar(rs.getString("userNo")) 						  ).append( "," ).append(
							Common.sqlChar(rs.getString("place")) 						  ).append( "," ).append(
							Common.sqlChar(rs.getString("oldPropertyNo")) ).append( "," ).append(
							Common.sqlChar(rs.getString("oldSerialNo"))   ).append( "," ).append(
							Common.sqlChar(adjustType)					  ).append( "," ).append(
							Common.sqlChar(oldBookUnit)					  ).append( "," ).append(
							Common.sqlChar(adjustBookUnit)					  ).append( "," ).append(
							Common.sqlChar(newBookUnit)					  ).append( "," ).append(
							Common.sqlChar("N")					  ).append( "," ).append(
							Common.sqlChar(getEditID())                   ).append( "," ).append(
							Common.sqlChar(getEditDate())                 ).append( "," ).append(
							Common.sqlChar(getEditTime())                 ).append(
							" ):;:");
			}
			//if(adjustType.equals("2") && Integer.parseInt(adjustBookValue)>=bookValueCheck && valuableCheck.equals("N")){
			//	 setVerifyError("Y");
			//	 setErrorMsg("增減別為減少時，增減價值必須小於原帳面金額─總價。");
			//	 break;
			//}else if(adjustType.equals("2") && Integer.parseInt(adjustBookValue)>bookValueCheck && valuableCheck.equals("Y")){
			//	 setVerifyError("Y");
			//	 setErrorMsg("增減別為減少時，增減價值必須小於等於原帳面金額─總價。");
			//	 break;
			//}
			int adjustBookValue = 0, bookValue = 0;
			if(!"".equals(Common.get(getAdjustBookValue()))){
				adjustBookValue = Integer.parseInt(getAdjustBookValue());
			}
			if(!"".equals(Common.get(rs.getString("bookValue")))){
				bookValue = Integer.parseInt(rs.getString("bookValue"));
			}
			
			if(adjustType.equals("2")){
			    if((!"".equals(Common.get(rs.getString("fundType")))) && (bookValue-adjustBookValue > bookValue) ){
			    	setVerifyError("Y");
			    	setErrorMsg("增減別為減少,又為基金類別時，增減帳面金額─總價必須小於等於原帳面金額─總價\n"); 
			    	break;
			    }else if(("Y".equals(Common.get(rs.getString("valuable")))) && (bookValue-adjustBookValue > bookValue) ){
			    	setVerifyError("Y");
			    	setErrorMsg("增減別為減少,又為珍貴財產時，增減帳面金額─總價必須小於等於原帳面金額─總價\n"); 
			    	break;
			    }else if(bookValue-adjustBookValue >= bookValue){
			    	setVerifyError("Y");
			    	setErrorMsg("增減別為減少時,增減帳面金額─總價必須小於原帳面金額─總價\n");
			    	break;
			    }
		        
			    if( ( ("".equals(Common.get(rs.getString("fundType")))) && ("N".equals(Common.get(rs.getString("valuable")))) ) && (bookValue-adjustBookValue) <= 0 ){
			    	setVerifyError("Y");
			    	setErrorMsg("增減別為減少,又不為珍貴財產或基金類別時,新價值必須大於0\n");
			    	break;
			    }else if( ( (!"".equals(Common.get(rs.getString("fundType")))) || ("Y".equals(Common.get(rs.getString("valuable")))) ) && (bookValue-adjustBookValue) < 0 ){
			    	setVerifyError("Y");
			    	setErrorMsg("增減別為減少又為珍貴財產或基金類別時,新價值必須大等於0\n");
			    	break;
			    }
			}
		}
	} catch (Exception e) {
		e.printStackTrace();
	}
	try{
		if (!"Y".equals(getVerifyError())) {
			execSQLArray = sbSQL.toString().split(":;:");
			db.excuteSQL(execSQLArray);
			setStateInsertSuccess();
			setErrorMsg("新增完成");				
		} else {			   
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

public UNTMP027F  queryOne() throws Exception{
	Database db = new Database();
	UNTMP027F obj = this;
	try {
		String sql=" select distinct a.enterOrg, a.ownership, a.caseNo, a.propertyNo, b.serialNo, a.lotNo, a.propertyName1, " +
			" a.propertyKind, a.fundType, a.valuable, "+
			" a.sourceDate, b.bookAmount, " +
			" b.bookValue, b.keepUnit , b.keeper , b.useUnit, b.userNo , b.place, b.oldPropertyNo, b.oldSerialNo, " +
			" (select f.unitName from UNTMP_KeepUnit f where b.enterOrg=f.enterOrg and b.keepUnit=f.unitNo) as keepUnitName, "+
			" (select f.unitName from UNTMP_KeepUnit f where b.enterOrg=f.enterOrg and b.useUnit=f.unitNo) as useUnitName, "+
			" (select f.keeperName from UNTMP_Keeper f where b.enterOrg=f.enterOrg and b.keepUnit=f.unitNo and b.keeper=f.keeperNo) as keeperName, "+		
			" (select f.keeperName from UNTMP_Keeper f where b.enterOrg=f.enterOrg and b.useUnit=f.unitNo and b.userNo=f.keeperNo) as userName, "+		
			" c.organSName as enterOrgName, d.propertyName, d.material, d.propertyUnit, d.limitYear, a.otherMaterial, a.otherPropertyUnit, a.otherLimitYear "+
			" from UNTMP_Movable a, UNTMP_MovableDetail b, SYSCA_ORGAN c, "+
			" SYSPK_PropertyCode d where 1=1 " +
			" and a.enterOrg=b.enterOrg and a.ownership=b.ownership and a.propertyNo=b.propertyNo and a.lotNo=b.lotNo" +
			" and a.enterOrg 	= " + Common.sqlChar(enterOrg) +
			" and a.ownership 	= " + Common.sqlChar(ownership) +
			" and a.propertyNo 	= " + Common.sqlChar(propertyNo) +
			" and b.serialNo 	= " + Common.sqlChar(serialNo) +
			" and a.enterOrg	= c.organID " +
			" and a.propertyNo 	= d.propertyNo "+
			" and d.enterOrg in('000000000A',a.enterOrg) and d.propertyType='1'" +
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
	Database db = new Database();
	ArrayList objList=new ArrayList();
	int counter=0;
	try {
		String sql= " select distinct a.enterOrg, d.propertyName, a.ownership, a.caseNo, "+
					" a.propertyNo, b.serialNo," +
					" decode(a.propertyKind,'01','公務用','02','公共用','03','事業用','04','非公用','') propertyKind,"+
					" b.bookAmount, b.bookValue, a.sourceDate "+
					" from UNTMP_Movable a, UNTMP_MovableDetail b, SYSCA_Organ c ,SYSPK_PropertyCode d " +
					" where 1=1 and a.verify='Y' and a.dataState='1' and b.dataState='1' " +
					" and a.enterOrg=b.enterOrg and a.ownership=b.ownership and a.propertyNo=b.propertyNo and a.lotNo=b.lotNo " +
					" and a.enterOrg = c.organID " +
					" and a.propertyNo = d.propertyNo "+
					" and d.enterOrg in('000000000A',a.enterOrg) and d.propertyType='1'" +
					" and a.enterOrg 	= " + Common.sqlChar(q_enterOrg) +
					" and a.ownership 	= " + Common.sqlChar(q_ownership) +
					" and (a.enterOrg , a.ownership , a.propertyNo , b.serialNo)  not in ( " +
					"  select enterOrg , ownership , propertyNo , serialNo from UNTMP_AdjustDetail  where verify='N' " +
					"  ) " +
					" and (a.enterOrg , a.ownership , a.propertyNo , b.serialNo)  not in ( " +
					"  select enterOrg , ownership , propertyNo , serialNo from UNTMP_ReduceDetail where verify='N' " +
					"  ) " +
					" and (a.enterOrg , a.ownership , a.propertyNo , b.serialNo)  not in ( " +
					"  select enterOrg , ownership , propertyNo , serialNo from UNTMP_moveDetail where verify='N' " +
					"  ) " ;
			if (!"".equals(getQ_propertyNoS()))
				sql+=" and a.propertyNo >= " + Common.sqlChar(getQ_propertyNoS());		
			if (!"".equals(getQ_propertyNoE()))
				sql+=" and a.propertyNo<=" + Common.sqlChar(getQ_propertyNoE());						
			if (!"".equals(getQ_serialNoS())){
				setQ_serialNoS(Common.formatFrontZero(getQ_serialNoS(),7));
				sql+=" and b.serialNo >= " + Common.sqlChar(getQ_serialNoS()) ;
			}
			if (!"".equals(getQ_serialNoE())){
				setQ_serialNoE(Common.formatFrontZero(getQ_serialNoE(),7));
				sql+=" and b.serialNo <= " + Common.sqlChar(getQ_serialNoE()) ;
			}
			if (!"".equals(getQ_propertyKind()))
				sql+=" and a.propertyKind = " + Common.sqlChar(getQ_propertyKind()) ;
			if (!"".equals(getQ_fundType()))
				sql+=" and a.fundType = " + Common.sqlChar(getQ_fundType()) ;
			if (!"".equals(getQ_valuable()))
				sql+=" and a.valuable = " + Common.sqlChar(getQ_valuable()) ;
			sql+=" order by a.enterOrg, a.ownership, a.propertyNo, b.serialNo ";
		ResultSet rs = db.querySQL(sql);
		//把查到的資料放入list裡..
		//和.jsp裡的 List區 內容要搭配起來
		while (rs.next()){
			counter++;
			String rowArray[]=new String[7];
			rowArray[0]=rs.getString("enterOrg");
			rowArray[1]=rs.getString("ownership");
			rowArray[2]=rs.getString("propertyNo"); 
			rowArray[3]=rs.getString("serialNo"); 
			rowArray[4]=rs.getString("propertyName");
			rowArray[5]=rs.getString("bookAmount");
			rowArray[6]=rs.getString("bookValue"); 
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
