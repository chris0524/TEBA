/*
*<br>程式目的：動產盤點資料維護
*<br>程式代號：untpd001f
*<br>程式日期：
*<br>程式作者：shan
*<br>--------------------------------------------------------
*<br>修改作者　　修改日期　　　修改目的
*<br>--------------------------------------------------------
*/

package unt.pd;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Map;

import unt.ch.UNTCH_Tools;
import util.*;

public class UNTPD001F extends UNTPD001Q{


String enterOrg;
String enterOrgName;
public String getEnterOrg(){ return checkGet(enterOrg); }
public void setEnterOrg(String s){ enterOrg=checkSet(s); }
public String getEnterOrgName(){ return checkGet(enterOrgName); }
public void setEnterOrgName(String s){ enterOrgName=checkSet(s); }

String serialNo1;//盤點序號
String barCode;
String ownership;
public String getSerialNo1(){ return checkGet(serialNo1); }
public void setSerialNo1(String s){ serialNo1=checkSet(s); }
public String getBarCode(){ return checkGet(barCode); }
public void setBarCode(String s){ barCode=checkSet(s); }
public String getOwnership(){ return checkGet(ownership); }
public void setOwnership(String s){ ownership=checkSet(s); }

String closingDate;//資料截止日期
String bookAmount;
String actualAmount; //盤點數量
String bookUnit;
String bookValue;
public String getClosingDate(){ return checkGet(closingDate); }
public void setClosingDate(String s){ closingDate=checkSet(s); }
public String getBookAmount(){ return checkGet(bookAmount); }
public void setBookAmount(String s){ bookAmount=checkSet(s); }
public String getActualAmount(){ return checkGet(actualAmount); }
public void setActualAmount(String s){ actualAmount=checkSet(s); }
public String getBookUnit(){ return checkGet(bookUnit); }
public void setBookUnit(String s){ bookUnit=checkSet(s); }
public String getBookValue(){ return checkGet(bookValue); }
public void setBookValue(String s){ bookValue=checkSet(s); }
String checkResult;//盤點結果
String oddsCause;//異常原因
String propertyKind;
String fundType;
String propertyNo;
String propertyNoName;
public String getCheckResult(){ return checkGet(checkResult); }
public void setCheckResult(String s){ checkResult=checkSet(s); }
public String getOddsCause(){ return checkGet(oddsCause); }
public void setOddsCause(String s){ oddsCause=checkSet(s); }
public String getPropertyKind(){ return checkGet(propertyKind); }
public void setPropertyKind(String s){ propertyKind=checkSet(s); }
public String getFundType(){ return checkGet(fundType); }
public void setFundType(String s){ fundType=checkSet(s); }
public String getPropertyNo(){ return checkGet(propertyNo); }
public void setPropertyNo(String s){ propertyNo=checkSet(s); }
public String getPropertyNoName(){ return checkGet(propertyNoName); }
public void setPropertyNoName(String s){ propertyNoName=checkSet(s); }
String serialNo;
String buyDate;
String propertyName1;
String oldPropertyNo;
String oldSerialNo;
public String getSerialNo(){ return checkGet(serialNo); }
public void setSerialNo(String s){ serialNo=checkSet(s); }
public String getBuyDate(){ return checkGet(buyDate); }
public void setBuyDate(String s){ buyDate=checkSet(s); }
public String getPropertyName1(){ return checkGet(propertyName1); }
public void setPropertyName1(String s){ propertyName1=checkSet(s); }
public String getOldPropertyNo(){ return checkGet(oldPropertyNo); }
public void setOldPropertyNo(String s){ oldPropertyNo=checkSet(s); }
public String getOldSerialNo(){ return checkGet(oldSerialNo); }
public void setOldSerialNo(String s){ oldSerialNo=checkSet(s); }

String nameplate;
String specification;
String material;
String propertyUnit;
String limitYear;
String originalLimitYear;

public String getNameplate(){ return checkGet(nameplate); }
public void setNameplate(String s){ nameplate=checkSet(s); }
public String getSpecification(){ return checkGet(specification); }
public void setSpecification(String s){ specification=checkSet(s); }
public String getMaterial(){ return checkGet(material); }
public void setMaterial(String s){ material=checkSet(s); }
public String getPropertyUnit(){ return checkGet(propertyUnit); }
public void setPropertyUnit(String s){ propertyUnit=checkSet(s); }
public String getLimitYear(){ return checkGet(limitYear); }
public void setLimitYear(String s){ limitYear=checkSet(s); }
public String getOriginalLimitYear() { return checkGet(originalLimitYear); }
public void setOriginalLimitYear(String s) { originalLimitYear = checkGet(s); }

String place;
String place2;
public String getPlace(){ return checkGet(place); }
public void setPlace(String s){ place=checkSet(s); }
public String getPlace2(){ return checkGet(place2); }
public void setPlace2(String s){ place2=checkSet(s); }
public String getKeepUnit(){ return checkGet(keepUnit); }
public void setKeepUnit(String s){ keepUnit=checkSet(s); }

String keepUnit;
String keeper;
String useUnit;
String userNo;
public String getKeeper(){ return checkGet(keeper); }
public void setKeeper(String s){ keeper=checkSet(s); }
public String getUseUnit(){ return checkGet(useUnit); }
public void setUseUnit(String s){ useUnit=checkSet(s); }
public String getUserNo(){ return checkGet(userNo); }
public void setUserNo(String s){ userNo=checkSet(s); }
String keepBureau;
String useBureau;
public String getKeepBureau(){ return checkGet(keepBureau); }	
public void setKeepBureau(String s){ keepBureau=checkSet(s); }
public String getUseBureau(){ return checkGet(useBureau); }	
public void setUseBureau(String s){ useBureau=checkSet(s); }

String notes;
public String getNotes(){ return checkGet(notes); }
public void setNotes(String s){ notes=checkSet(s); }
String differenceKind; 	//財產區分別
String scrappedNote; 	//報廢註記
String labelNote; 		//補印標籤註記
String moveNote; 		//移動註記
String sourceDate;		//取得日期
String userNote;		//使用註記
String place1;			//存置地點代碼
String place1Name;		//存置地點名稱
String busignNo1;			//縣市1碼
String busignNo2;			//鄉鎮1碼
String busignNo3;			//地段4碼
String busignNo4;			//母號
String busignNo5;			//地號
String lasignNo1;			//縣市1碼
String lasignNo2;			//鄉鎮1碼
String lasignNo3;			//地段4碼
String lasignNo4;			//母號
String lasignNo5;			//地號
String doorPlate4;		//門牌
public String getDifferenceKind(){ return checkGet(differenceKind); }
public void setDifferenceKind(String s){ differenceKind=checkSet(s); }
public String getScrappedNote(){ return checkGet(scrappedNote); }
public void setScrappedNote(String s){ scrappedNote=checkSet(s); }
public String getLabelNote(){ return checkGet(labelNote); }
public void setLabelNote(String s){ labelNote=checkSet(s); }
public String getMoveNote(){ return checkGet(moveNote); }
public void setMoveNote(String s){ moveNote=checkSet(s); }
public String getSourceDate(){ return checkGet(sourceDate); }
public void setSourceDate(String s){ sourceDate=checkSet(s); }
public String getUserNote(){ return checkGet(userNote); }
public void setUserNote(String s){ userNote=checkSet(s); }
public String getPlace1(){ return checkGet(place1); }
public void setPlace1(String s){ place1=checkSet(s); }
public String getPlace1Name(){ return checkGet(place1Name); }
public void setPlace1Name(String s){ place1Name=checkSet(s); }
public String getBuSignNo1(){ return checkGet(busignNo1); }
public void setBuSignNo1(String s){ busignNo1=checkSet(s); }
public String getBuSignNo2(){ return checkGet(busignNo2); }
public void setBuSignNo2(String s){ busignNo2=checkSet(s); }
public String getBuSignNo3(){ return checkGet(busignNo3); }
public void setBuSignNo3(String s){ busignNo3=checkSet(s); }
public String getBuSignNo4(){ return checkGet(busignNo4); }
public void setBuSignNo4(String s){ busignNo4=checkSet(s); }
public String getBuSignNo5(){ return checkGet(busignNo5); }
public void setBuSignNo5(String s){ busignNo5=checkSet(s); }
public String getLasignNo1(){ return checkGet(lasignNo1); }
public void setLaSignNo1(String s){ lasignNo1=checkSet(s); }
public String getLaSignNo2(){ return checkGet(lasignNo2); }
public void setLaSignNo2(String s){ lasignNo2=checkSet(s); }
public String getLaSignNo3(){ return checkGet(lasignNo3); }
public void setLaSignNo3(String s){ lasignNo3=checkSet(s); }
public String getLaSignNo4(){ return checkGet(lasignNo4); }
public void setLaSignNo4(String s){ lasignNo4=checkSet(s); }
public String getLaSignNo5(){ return checkGet(lasignNo5); }
public void setLaSignNo5(String s){ lasignNo5=checkSet(s); }
public String getDoorPlate4(){ return checkGet(doorPlate4); }
public void setDoorPlate4(String s){ doorPlate4=checkSet(s); }
String placeNote;
String placeDetail1;
String placeDetail;
String placeDetail1Name;
public String getPlaceNote() {return checkGet(placeNote);}
public void setPlaceNote(String placeNote) {this.placeNote = checkSet(placeNote);}
public String getPlaceDetail1() {return checkGet(placeDetail1);}
public void setPlaceDetail1(String placeDetail1) {this.placeDetail1 = checkSet(placeDetail1);}
public String getPlaceDetail() {return checkGet(placeDetail);}
public void setPlaceDetail(String placeDetail) {this.placeDetail = checkSet(placeDetail);}
public String getPlaceDetail1Name() {return checkGet(placeDetail1Name);}
public void setPlaceDetail1Name(String placeDetail1Name) {this.placeDetail1Name = checkSet(placeDetail1Name);}



//傳回執行insert前之檢查sql
protected String[][] getInsertCheckSQL(){
	String[][] checkSQLArray = new String[1][4];
 	checkSQLArray[0][0]="";
	checkSQLArray[0][1]=">";
	checkSQLArray[0][2]="0";
	checkSQLArray[0][3]="該筆資料己重複，請重新輸入！";
	return checkSQLArray;
}


//傳回insert sql
protected String[] getInsertSQL(){
	Map differenceMap = TCGHCommon.getSysca_Code("DFK"); //財產區分別
	Map KeepUnitMap = TCGHCommon.getUntmp_KeepUnitCode(enterOrg); //保管單位、使用單位
	Map KeeperMap = TCGHCommon.getUntmp_KeeperCode(enterOrg); //保管人、使用人
	Map PlaceMap = TCGHCommon.getSysca_PlaceCode(enterOrg); //存置地點
	Map SignMap = TCGHCommon.getSysca_SignCode(); //縣市鄉鎮街
	String signNo1 = "";
	String signNo2 = "";
	String signNo3 = "";
	String signNo4 = "";
	String signNo5 = "";
	if (propertyNo.startsWith("1")) {
		signNo1 = lasignNo1;
		signNo2 = lasignNo2;
		signNo3 = lasignNo3;
		signNo4 = lasignNo4;
		signNo5 = lasignNo5;
	} else if (propertyNo.startsWith("2")) {
		signNo1 = busignNo1;
		signNo2 = busignNo2;
		signNo3 = busignNo3;
		signNo4 = busignNo4;
		signNo5 = busignNo5;
	}
	
	String[] execSQLArray = new String[1];
	String scrappedNote_u;	//報廢註記
	String labelNote_u;		//補印標籤註記
	String moveNote_u;		//移動註記
	if ("on".equals(scrappedNote)) {
		scrappedNote_u = "Y";
	} else {
		scrappedNote_u = "N";
	}
	if ("on".equals(labelNote)) {
		labelNote_u = "Y";
	} else {
		labelNote_u = "N";
	}
	if ("on".equals(moveNote)) {
		moveNote_u = "Y";
	} else {
		moveNote_u = "N";
	}
	setBookAmount("0");
	
	execSQLArray[0]= " insert into UNTPD_CHECKMOVABLE(" 
//		 		   + " enterorg, " 
//		 		   + " serialno1, "
//		 		   + " barcode, "
//		 		   + " ownership, "
//		 		   + " closingdate, "
//		 		   + " bookamount, "
//		 		   + " actualamount, "
//		 		   + " bookunit, "
//		 		   + " bookvalue, "
//		 		   + " checkresult, "
//		 		   + " oddscause, "
//		 		   + " propertykind, "
//		 		   + " fundtype, "
//		 		   + " propertyno, "
//		 		   + " serialno, "
//		 		   + " buydate, "
//		 		   + " propertyname1, "
//		 		   + " oldpropertyno, "
//		 		   + " oldserialno, "
//		 		   + " nameplate, "
//		 		   + " specification, "
//		 		   + " place, "
//		 		   + " keepunit, "
//		 		   + " keeper, "
//		 		   + " useunit, "
//		 		   + " userno, "
//		 		   + " notes, "
//		 		   + " editid, "
//		 		   + " editdate, "
//		 		   + " edittime "

				+ "enterorg           ,"
		 		 + "serialno1          ,"
		 		 + "barcode            ,"
		 		 + "ownership          ,"
		 		 + "differencekind     ,"
		 		 + "differencekindname ,"
		 		 + "propertytype       ,"//
		 		 + "propertytypename   ,"//
		 		 + "closingdate        ,"
		 		 + "bookamount         ,"
		 		 + "actualamount       ,"
		 		 + "bookunit           ,"
		 		 + "bookvalue          ,"
		 		 + "checkresult        ,"
		 		 + "oddscause          ,"
		 		 + "scrappednote       ,"
		 		 + "labelnote          ,"
		 		 + "movenote           ,"
		 		 + "propertykind       ,"
		 		 + "fundtype           ,"
		 		 + "propertyno         ,"
		 		 + "serialno           ,"
		 		 + "buydate            ,"
		 		 + "sourcedate         ,"
		 		 + "propertyname       ,"//
		 		 + "propertyname1      ,"
		 		 + "oldpropertyno      ,"
		 		 + "oldserialno        ,"
		 		 + "nameplate          ,"
		 		 + "specification      ,"
		 		 + "propertyunit       ,"
		 		 + "material           ,"
		 		 + "originallimityear  ,"
		 		 + "limityear          ,"
		 		 + "useyear            ,"//
		 		 + "keepunit           ,"
		 		 + "keepunitname       ,"
		 		 + "keeper             ,"
		 		 + "keepername         ,"
		 		 + "useunit            ,"
		 		 + "useunitname        ,"
		 		 + "userno             ,"
		 		 + "usernoname         ,"
		 		 + "usernote           ,"
		 		 + "place1             ,"
		 		 + "place1name         ,"
		 		 + "place              ,"
		 		 + "signno             ,"
		 		 + "signnoname         ,"
		 		 + "doorplate4         ,"
		 		 + "notes              ,"
		 		 + "editid             ,"
		 		 + "editdate           ,"
		 		 + "edittime           "
		 		   
		 		   + " )Values("
		 		   + Common.sqlChar(enterOrg) + ","
//				   + " ( select isnull(max(serialno1),'0')+1 as maxSerialno1 from Untpd_Checkmovable where 1=1 and enterorg = " + Common.sqlChar(enterOrg) + ")" + ","
		 		   + Common.sqlChar(serialNo1) + ","
		 		   + Common.sqlChar(barCode) + ","
				   + Common.sqlChar(ownership) + ","
				   + Common.sqlChar(differenceKind) + ","
				   + Common.sqlChar((String)differenceMap.get(differenceKind)) + "," 
				   + Common.sqlChar("") + "," //TODO:propertytype
				   + Common.sqlChar("") + "," //TODO:propertytypename
				   + Common.sqlChar(Datetime.changeTaiwanYYMMDD(closingDate, "2")) + ","
				   + Common.sqlChar(bookAmount) + ","
				   + Common.sqlChar(actualAmount) + ","
		           + Common.sqlChar(bookUnit) + ","
		           + Common.sqlChar(bookValue) + ","
		   		   + Common.sqlChar("3") + ","   // 盤點結果預設為3(未盤點)
				   + Common.sqlChar(oddsCause) + ","
				   + Common.sqlChar(scrappedNote_u) + ","
				   + Common.sqlChar(labelNote_u) + ","
				   + Common.sqlChar(moveNote_u) + ","
				   + Common.sqlChar(propertyKind) + ","
				   + Common.sqlChar(fundType) + ","
				   + Common.sqlChar(propertyNo) + ","
				   + Common.sqlChar(serialNo) + ","
		   		   + Common.sqlChar(Datetime.changeTaiwanYYMMDD(buyDate, "2")) + ","
		   		   + Common.sqlChar(Datetime.changeTaiwanYYMMDD(sourceDate, "2")) + ","
		   		   + Common.sqlChar("") + "," //TODO:propertyname
		 		   + Common.sqlChar(propertyName1) + ","
		 		   + Common.sqlChar(oldPropertyNo) + ","
		 		   + Common.sqlChar(oldSerialNo) + ","
		 		   + Common.sqlChar(nameplate) + ","
		 		   + Common.sqlChar(specification) + ","
		 		  + Common.sqlChar(propertyUnit) + ","
		 		 + Common.sqlChar(material) + ","
		 		 + Common.sqlChar(originalLimitYear) + ","
		 		 + Common.sqlChar(limitYear) + ","
		 		 + Common.sqlChar("") + "," //TODO:useyear
	 		   + Common.sqlChar(keepUnit) + ","
		 		  + Common.sqlChar((String)KeepUnitMap.get(keepUnit)) + ","
		 		   + Common.sqlChar(keeper) + ","
		 		  + Common.sqlChar((String)KeeperMap.get(keeper)) + ","
		 		   + Common.sqlChar(useUnit) + ","
		 		  + Common.sqlChar((String)KeepUnitMap.get(useUnit)) + ","
		 		   + Common.sqlChar(userNo) + ","
		 		  + Common.sqlChar((String)KeeperMap.get(userNo)) + ","
		 		 + Common.sqlChar(userNote) + ","
		 		   + Common.sqlChar(place1) + ","
		 		   + Common.sqlChar((String)PlaceMap.get(place1)) + ","
		 		   + Common.sqlChar(place) + ","
		 		  + Common.sqlChar(signNo3 + signNo4 + signNo5) + ","
		 		 + Common.sqlChar((String)SignMap.get(signNo1) + (String)SignMap.get(signNo2) + (String)SignMap.get(signNo3) + signNo4 + signNo5) + ","
		 		 + Common.sqlChar(doorPlate4) + ","
		 		+ Common.sqlChar(notes) + ","
		 		   + Common.sqlChar(getEditID()) + ","
		 		   + Common.sqlChar(Datetime.changeTaiwanYYMMDD(getEditDate(), "2")) + ","
		 		   + Common.sqlChar(getEditTime()) 
		 		   + ")" ;
	return execSQLArray;
}


//傳回update sql
protected String[] getUpdateSQL(){
	String[] execSQLArray = new String[1];
	Map differenceMap = TCGHCommon.getSysca_Code("DFK"); //財產區分別
	Map KeepUnitMap = TCGHCommon.getUntmp_KeepUnitCode(enterOrg); //保管單位、使用單位
	Map KeeperMap = TCGHCommon.getUntmp_KeeperCode(enterOrg); //保管人、使用人
	Map PlaceMap = TCGHCommon.getSysca_PlaceCode(enterOrg); //存置地點
	Map SignMap = TCGHCommon.getSysca_SignCode(); //縣市鄉鎮街
	String signNo1 = "";
	String signNo2 = "";
	String signNo3 = "";
	String signNo4 = "";
	String signNo5 = "";
	if (propertyNo.startsWith("1")) {
		signNo1 = lasignNo1;
		signNo2 = lasignNo2;
		signNo3 = lasignNo3;
		signNo4 = lasignNo4;
		signNo5 = lasignNo5;
	} else if (propertyNo.startsWith("2")) {
		signNo1 = busignNo1;
		signNo2 = busignNo2;
		signNo3 = busignNo3;
		signNo4 = busignNo4;
		signNo5 = busignNo5;
	}
	
	
	String scrappedNote_u;	//報廢註記
	String labelNote_u;		//補印標籤註記
	String moveNote_u;		//移動註記
	if ("on".equals(scrappedNote)) {
		scrappedNote_u = "Y";
	} else {
		scrappedNote_u = "N";
	}
	if ("on".equals(labelNote)) {
		labelNote_u = "Y";
	} else {
		labelNote_u = "N";
	}
	if ("on".equals(moveNote)) {
		moveNote_u = "Y";
	} else {
		moveNote_u = "N";
	}
	execSQLArray[0]= " update UNTPD_CHECKMOVABLE set " 
		   		   + " ownership = " + Common.sqlChar(ownership) + "," //權屬
		   		   + " closingdate = " + Common.sqlChar(Datetime.changeTaiwanYYMMDD(closingDate, "2")) + "," //資料截止日期
		   		   + " differencekind = " + Common.sqlChar(differenceKind) + "," //財產區分別
		   		   + " differencekindname = " + Common.sqlChar((String)differenceMap.get(differenceKind)) + "," //財產區分別
		   		   + " bookamount = " + Common.sqlChar(bookAmount) + "," //帳列數量
		   		   + " actualamount = " + Common.sqlChar(actualAmount) + "," //盤點數量
		   		   + " bookunit = " + Common.sqlChar(bookUnit) + "," //單價
		   		   + " bookvalue = " + Common.sqlChar(bookValue) + "," //總價
		   		   + " checkresult = " + Common.sqlChar(checkResult) + "," //盤點結果
		   		   + " oddscause = " + Common.sqlChar(oddsCause) + "," //異常原因
		   		   + " propertykind = " + Common.sqlChar(propertyKind) + "," //財產性質
		   		   + " fundtype = " + Common.sqlChar(fundType) + "," //基金財產
		   		   + " propertyno = " + Common.sqlChar(propertyNo) + "," //財產編號    財產編號_名稱
		   		   + " serialno = " + Common.sqlChar(serialNo) + "," //財產分號
		   		   + " buydate = " + Common.sqlChar(Datetime.changeTaiwanYYMMDD(buyDate, "2")) + "," //購置日期
		   		   + " propertyname1 = " + Common.sqlChar(propertyName1) + "," //財產別名
		   		   + " oldpropertyno = " + Common.sqlChar(oldPropertyNo) + "," //原財產編號
		   		   + " oldserialno = " + Common.sqlChar(oldPropertyNo) + "," //原財產分號
		   		   + " nameplate = " + Common.sqlChar(nameplate) + "," //廠牌
		   		   + " specification = " + Common.sqlChar(specification) + "," //型式
		   		   + " material = " + Common.sqlChar(material) + "," //主要材質
		   		   + " propertyunit = " + Common.sqlChar(propertyUnit) + "," //單位
		   		   + " originallimityear = " + Common.sqlChar(originalLimitYear) + "," //原使用年限
		   		   + " limityear = " + Common.sqlChar(limitYear).replace("年", "").replace("月", "") + "," //使用年限(月)
		   		   + " scrappedNote = " + Common.sqlChar(scrappedNote_u) + "," //報廢註記
		   		   + " labelNote = " + Common.sqlChar(labelNote_u) + "," //補印標籤註記
		   		   + " moveNote = " + Common.sqlChar(moveNote_u) + "," //移動註記
		   		   + " place1 = " + Common.sqlChar(placeDetail1) + "," //存置地點
		   		   + " place1name = " + Common.sqlChar((String)PlaceMap.get(place1)) + "," //存置中文
		   		   + " place = " + Common.sqlChar(placeDetail) + "," //存置地點說明
		   		   + " sourcedate = " + Common.sqlChar(Datetime.changeTaiwanYYMMDD(sourceDate, "2")) + "," //取得日期
		   		   + " notes = " + Common.sqlChar(notes) + "," //備註
		   		   + " keepunit = " + Common.sqlChar(keepUnit) + "," //保管單位
		   		   + " keepunitname = " + Common.sqlChar((String)KeepUnitMap.get(keepUnit)) + "," //保管單位名稱
		   		   + " keeper = " + Common.sqlChar(keeper) + "," //保管人
		   		   + " keepername = " + Common.sqlChar((String)KeeperMap.get(keeper)) + "," //保管人名稱
		   		   + " useunit = " + Common.sqlChar(useUnit) + "," //使用單位
		   		   + " useunitname = " + Common.sqlChar((String)KeepUnitMap.get(useUnit)) + "," //使用單位名稱
		   		   + " userno = " + Common.sqlChar(userNo) + "," //使用人
		   		   + " usernoname = " + Common.sqlChar((String)KeeperMap.get(userNo)) + "," //使用人名稱
		   		   + " signno = " + Common.sqlChar(signNo3 + signNo4 + signNo5) + "," //土地/建物標示代碼
		   		   + " signnoname = " + Common.sqlChar((String)SignMap.get(signNo1) + (String)SignMap.get(signNo2) + (String)SignMap.get(signNo3) + signNo4 + signNo5) + "," //土地/建物標示代碼
		   		   + " doorPlate4 = " + Common.sqlChar(doorPlate4) + "," //門牌
		   		   + " editid = " + Common.sqlChar(getEditID()) + ","  //異動人員
		   		   + " editdate = " + Common.sqlChar(Datetime.changeTaiwanYYMMDD(getEditDate(), "2")) + ","  //異動日期
		   		   + " edittime = " + Common.sqlChar(getEditTime()) //異動時間
		   		   + " where 1=1 "
		   		   + " and enterorg = " + Common.sqlChar(enterOrg) 
		   		   + " and serialno1 = " + Common.sqlChar(serialNo1)
		   		   + "";
	
	String strtemp = "";
	if(("1".equals(getPropertyNo().substring(0,1)) && !"111".equals(getPropertyNo().substring(0,3))) || ("8".equals(getPropertyNo().substring(0,1)))){
		strtemp = "UNTLA_LAND";
	}else if("2".equals(getPropertyNo().substring(0,1))){
		strtemp = "UNTBU_BUILDING";
	}else if("3".equals(getPropertyNo().substring(0,1)) || "4".equals(getPropertyNo().substring(0,1)) || "5".equals(getPropertyNo().substring(0,1))){
		strtemp = "UNTMP_MOVABLEDETAIL";
	}else if("111".equals(getPropertyNo().substring(0,3))){
		strtemp = "UNTRF_ATTACHMENT";
	}
	
	String sql = "update " + strtemp + " set" +
						" place = " + Common.sqlChar(this.getPlaceDetail()) + "," + 
						" place1 = " + Common.sqlChar(this.getPlaceDetail1()) +
					" from " + strtemp + " a" +
					" inner join UNTPD_CHECKMOVABLE b" +
					" on a.enterorg = b.enterorg and a.differencekind = b.differencekind and a.ownership = b.ownership and a.propertyno = b.propertyno and a.serialno = b.serialno" +
					" and b.enterorg = "  + Common.sqlChar(enterOrg) +
					" and b.serialno1 = " + Common.sqlChar(serialNo1);
	
	Database db = null;
	try{
		db = new Database();
		db.excuteSQLNoAutoCommit(sql);
	}catch(Exception e){
		e.printStackTrace();
	}finally{
		db.closeAll();
	}
	
	return execSQLArray;
}


//傳回delete sql
protected String[] getDeleteSQL(){
	String[] execSQLArray = new String[1];
	execSQLArray[0]= " delete Untpd_Checkmovable where 1=1 "
		           + " and enterOrg = " + Common.sqlChar(enterOrg)
		           + " and serialno1 = " + Common.sqlChar(serialNo1)
		           + " and propertyno = " + Common.sqlChar(propertyNo)
		           //+ " and serialno = " + Common.sqlChar(serialNo)
		           + "";
	//System.out.println(execSQLArray[0]);
	return execSQLArray;
}


/**
 * <br>
 * <br>目的：依主鍵查詢單一資料
 * <br>參數：無
 * <br>傳回：傳回本物件
*/

public UNTPD001F  queryOne() throws Exception{
	Database db = new Database();
	UNTPD001F obj = this;
	try {
		String sql= " select p.enterOrg, p.differencekind, p.serialno1, p.barCode, p.ownership, p.closingdate, p.bookamount, p.actualamount, p.bookunit, p.bookvalue, p.checkresult, p.oddscause, p.propertykind, " +
				" p.fundtype, p.propertyno, p.propertyname, p.originallimityear, p.limityear, p.propertyunit, p.material, p.serialno, p.buydate, p.propertyname1, p.oldpropertyno, p.oldserialno, p.nameplate, p.specification, p.scrappednote, " +
		        " p.scrappednote, p.labelnote, p.movenote, p.sourcedate, p.usernote, p.place1, b.place1 as placedetail1, p.place, b.place as placedetail, p.place1name, p.keepunit, p.keeper, p.useunit, p.userno, p.signno, " +
		        " p.doorplate4, p.notes, p.editid, p.editdate, p.editTime, isnull(s.organsname,'') organsname "
			  + " from UNTPD_CHECKMOVABLE p "
			  + " left outer join SYSCA_ORGAN s on p.enterorg=s.organid "
			  + " left join UNTMP_MOVABLEDETAIL b on p.enterorg = b.enterorg and p.ownership = b.ownership and p.propertyno = b.propertyno and p.serialno = b.serialno and p.differencekind = b.differencekind "
			  + " where 1=1 "
		 	  + " and p.enterorg = " + Common.sqlChar(enterOrg)
		 	  + " and p.serialno1 = " + Common.sqlChar(serialNo1)
		 	  + "";
		//System.out.println(sql);
		ResultSet rs = db.querySQL(sql);
		if (rs.next()){
			obj.setEnterOrg(rs.getString("enterOrg"));
			obj.setEnterOrgName(rs.getString("organsname"));
			obj.setDifferenceKind(rs.getString("differencekind"));
			obj.setSerialNo1(rs.getString("serialno1"));
			obj.setBarCode(rs.getString("barCode"));
			obj.setOwnership(rs.getString("ownership"));
			String closingdate = rs.getString("closingdate");
			if (closingdate.length() == 8) {
				obj.setClosingDate(Datetime.changeTaiwanYYMMDD(closingdate, "1"));
			} else {
				obj.setClosingDate("");
			}
			
			obj.setBookAmount(rs.getString("bookamount"));
			obj.setActualAmount(rs.getString("actualamount"));
			obj.setBookUnit(rs.getString("bookunit"));
			obj.setBookValue(rs.getString("bookvalue"));
			obj.setCheckResult(rs.getString("checkresult"));
			obj.setOddsCause(rs.getString("oddscause"));
			obj.setPropertyKind(rs.getString("propertykind"));
			obj.setFundType(rs.getString("fundtype"));
			obj.setPropertyNo(rs.getString("propertyno"));
			obj.setPropertyNoName(rs.getString("propertyname"));
			obj.setOriginalLimitYear(rs.getString("originallimityear"));
			obj.setLimitYear(Datetime.formatMonths(rs.getString("limityear")));
			obj.setPropertyUnit(rs.getString("propertyunit"));
			obj.setMaterial(rs.getString("material"));
			obj.setSerialNo(rs.getString("serialno"));
			String buydate = rs.getString("buydate");
			if (buydate.length() == 8) {
				obj.setBuyDate(Datetime.changeTaiwanYYMMDD(buydate, "1"));
			} else {
				obj.setBuyDate("");
			}
			obj.setPropertyName1(rs.getString("propertyname1"));
			obj.setOldPropertyNo(rs.getString("oldpropertyno"));
			obj.setOldSerialNo(rs.getString("oldserialno"));
			obj.setNameplate(rs.getString("nameplate"));
			obj.setSpecification(rs.getString("specification"));
			obj.setScrappedNote(rs.getString("scrappednote"));
			obj.setLabelNote(rs.getString("labelnote"));
			obj.setMoveNote(rs.getString("movenote"));
			String sourcedate = rs.getString("sourcedate");
			if (sourcedate.length() == 8) {
				obj.setSourceDate(Datetime.changeTaiwanYYMMDD(sourcedate, "1"));
			} else {
				obj.setSourceDate("");
			}
			obj.setUserNote(rs.getString("usernote"));
			
			obj.setPlace1(rs.getString("place1") == null ? "" : rs.getString("place1"));
			obj.setPlace(rs.getString("place") == null ? "" : rs.getString("place"));
			obj.setPlace1Name(rs.getString("place1name") == null ? "" : rs.getString("place1name"));
			
			obj.setPlaceDetail1(rs.getString("placeDetail1") == null ? "" : rs.getString("placeDetail1"));
			obj.setPlaceDetail(rs.getString("placeDetail") == null ? "" : rs.getString("placeDetail"));
			String placeDetail1Name = new UNTCH_Tools().getPlace1Name(enterOrg, Common.get(rs.getString("placeDetail1")));
			obj.setPlaceDetail1Name(placeDetail1Name);
			
			if(!"".equals(Common.get(rs.getString("placeDetail1"))) || !"".equals(Common.get(rs.getString("placeDetail")))){
				obj.setPlaceNote("Y");
			}else{
				obj.setPlaceNote("N");
			}
			
			obj.setKeepUnit(rs.getString("keepunit"));
			obj.setKeeper(rs.getString("keeper"));
			obj.setUseUnit(rs.getString("useunit"));
			obj.setUserNo(rs.getString("userno"));
			String signno = Common.get(rs.getString("signno"));
			if (signno.length() >= 1) {
				obj.setLaSignNo1(signno.substring(0, 1) + "000000");
				obj.setBuSignNo1(signno.substring(0, 1) + "000000");
			}
			if (signno.length() >= 3) {
				obj.setLaSignNo2(signno.substring(0, 3) + "0000");
				obj.setBuSignNo2(signno.substring(0, 3) + "0000");
			}
			if (signno.length() >= 7) {
				obj.setLaSignNo3(signno.substring(0, 7));
				obj.setBuSignNo3(signno.substring(0, 7));
			}
			if (signno.length() >= 11) {
				obj.setLaSignNo4(signno.substring(7, 11));
				obj.setBuSignNo4(signno.substring(7, 11));
			}
			if (signno.length() >= 11) {
				obj.setLaSignNo4(signno.substring(7, 11));
			}
			if (signno.length() >= 1) {
				obj.setBuSignNo4(signno.substring(7, 12));
			}
			if (signno.length() >= 15) {
				obj.setLaSignNo5(signno.substring(11, 15));
				obj.setBuSignNo5(signno.substring(12, 15));
			}
			obj.setDoorPlate4(rs.getString("doorplate4"));
			obj.setNotes(rs.getString("notes"));
			obj.setEditID(rs.getString("editid"));
			String editdate = rs.getString("editdate");
			if (editdate.length() == 8) {
				obj.setEditDate(Datetime.changeTaiwanYYMMDD(editdate, "1"));
			} else {
				obj.setEditDate("");
			}
			obj.setEditTime(rs.getString("editTime"));
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

	String where="";
	//入帳機關
	if ("".equals(getQ_enterOrg())){
		where += " and p.enterorg = " + Common.sqlChar(getEnterOrg()) + " ";
	}else{
		where += " and p.enterorg = " + Common.sqlChar(getQ_enterOrg()) + " ";
	}
	//財產區分別
	if (!"".equals(getQ_differenceKind())){
		where += "and p.differencekind = " + Common.sqlChar(getQ_differenceKind()) + " ";			}
	//權屬
	if (!"".equals(getQ_ownership())){
		where += "and p.ownership = " + Common.sqlChar(getQ_ownership()) + " ";			}
	
	//財產大類
	if (!"".equals(getQ_propertyType())){
		where += "and p.propertytype = " + Common.sqlChar(getQ_propertyType()) + " ";		}
	
	//條碼
	if (!"".equals(getQ_barcode())){
		where += "and p.barcode = " + Common.sqlChar(getQ_barcode()) + " ";				}
	//盤點結果
	if (!"".equals(getQ_checkResult())){
		where += "and p.checkresult = " + Common.sqlChar(getQ_checkResult()) + " ";		}
	//財產性質
	if (!"".equals(getQ_propertyKind())){
		where += "and p.propertykind = " + Common.sqlChar(getQ_propertyKind()) + " ";		}
	//基金財產
	if (!"".equals(getQ_fundType())){
		where += "and p.fundtype = " + Common.sqlChar(getQ_fundType()) + " ";				}
	//財產編號起
	if (!"".equals(getQ_propertyNoS())){
		where += "and p.propertyno >= " + Common.sqlChar(getQ_propertyNoS()) + " ";		}
	//財產編號訖
	if (!"".equals(getQ_propertyNoE())){
		where += "and p.propertyno <= " + Common.sqlChar(getQ_propertyNoE()) + " ";		}
	//財產分號起
	if (!"".equals(getQ_serialNoS())){
		where += "and p.serialno >= " + Common.sqlChar(getQ_serialNoS()) + " ";			}
	//財產分號訖
	if (!"".equals(getQ_serialNoE())){
		where += "and p.serialno <= " + Common.sqlChar(getQ_serialNoE()) + " ";			}
	//原財產編號
	if (!"".equals(getQ_oldPropertyNo())){
		where += "and p.oldpropertyno = " + Common.sqlChar(getQ_oldPropertyNo()) + " ";	}
	//原財產分號起
	if (!"".equals(getQ_oldSerialNoS())){
		where += "and p.oldserialno >= " + Common.sqlChar(getQ_oldSerialNoS()) + " ";		}
	//原財產分號訖
	if (!"".equals(getQ_oldSerialNoE())){
		where += "and p.oldserialno <= " + Common.sqlChar(getQ_oldSerialNoE() + " ");		}
	//購置日期起
	if (!"".equals(getQ_buyDateS())){
		where += "and p.buydate >= " + Common.sqlChar(Datetime.changeTaiwanYYMMDD(getQ_buyDateS(), "2")) + " ";				}
	//購置日期訖
	if (!"".equals(getQ_buyDateE())){
		where += "and p.buydate <= " + Common.sqlChar(Datetime.changeTaiwanYYMMDD(getQ_buyDateE(), "2")) + " ";				}
	//財產性質
//	if (!"".equals(getQ_propertyName1())){
//		where += "and p.propertyName1 = " + Common.sqlChar(getQ_propertyName1());	}
	//保管單位
	if (!"".equals(getQ_keepUnit())){
		where += "and p.keepunit = " + Common.sqlChar(getQ_keepUnit()) + " ";				}
	//保管人
	if (!"".equals(getQ_keeper())){
		where += "and p.keeper = " + Common.sqlChar(getQ_keeper()) + " ";				}
	//使用單位
	if (!"".equals(getQ_useUnit())){
		where += "and p.useunit = " + Common.sqlChar(getQ_useUnit()) + " ";				}
	//使用單人
	if (!"".equals(getQ_userNo())){
		where += "and p.userno = " + Common.sqlChar(getQ_userNo()) + " ";					}
	//使用註記
	if (!"".equals(getQ_userNote())){
		where += "and p.usernote like " + Common.sqlChar("%" +getQ_userNote() + "%") + " ";				}
	//存置地點
	if (!"".equals(getQ_place1())){
		where += "and p.place1 = " + Common.sqlChar(getQ_place1()) + " ";				}
	//存置地點說明
	if (!"".equals(getQ_place())){
		where += "and p.place like " + Common.sqlChar("%" +getQ_place() + "%") + " ";				}
	//報廢註記
	if ("Y".equals(getQ_scrappednote())){
		where += "and p.scrappednote = " + Common.sqlChar(getQ_scrappednote()) + " ";
	} 
	
	//補印標籤註記
	if ("Y".equals(getQ_labelnote())){
		where += "and p.labelnote = " + Common.sqlChar(getQ_labelnote())+ " ";
	} 

	//移動註記
	if ("Y".equals(getQ_movenote())){
		where += "and p.movenote = " + Common.sqlChar(getQ_movenote())+ " ";
	}

	
	
	
	Database db = new Database();
	ArrayList objList=new ArrayList();
	
	try {
		String sql = " select p.enterorg ,p.serialno1 ,p.propertyno ,p.serialno ,p.differencekindname "
				   + " ,p.propertyname1 ,p.bookamount ,p.actualamount ,p.checkresult"
				   + " ,p.bookvalue ,p.keepunit ,p.keeper "
				   + " ,p.keepunitname ,p.keepername, "
				   + " (select a.propertyname from SYSPK_PROPERTYCODE a where (a.enterorg=p.enterorg or a.enterorg='000000000A') and a.propertyno=p.propertyno ) as propertyname "
				   + " from "
				   + " UNTPD_CHECKMOVABLE p "
				   + " where 1=1 "
	       		   + where
	       		   + " order by p.propertyno ,p.serialno "
	       		   + "";

		//System.out.println(sql);
		ResultSet rs = db.querySQL(sql,true);
		processCurrentPageAttribute(rs);
		if (getTotalRecord() > 0) {
			int count = getRecordStart();
			int end = getRecordEnd();
			do {
			if (count > end) break;
			String rowArray[]=new String[13];
			rowArray[0]=rs.getString("enterorg");
			rowArray[1]=rs.getString("serialno1");
			rowArray[2]=rs.getString("differencekindname"); 
			rowArray[3]=rs.getString("propertyno"); 
			rowArray[4]=rs.getString("serialno"); 
			rowArray[5]=rs.getString("propertyname"); 
			rowArray[6]=rs.getString("propertyname1");
			rowArray[7]=rs.getString("bookamount"); 
			rowArray[8]=rs.getString("actualamount"); 
			rowArray[9]=rs.getString("bookvalue");
			rowArray[10]=rs.getString("keepunitname");
			rowArray[11]=rs.getString("keepername");
			if("1".equals(rs.getString("checkresult"))){
				rowArray[12]="盤點正常"; 
			}else if("2".equals(rs.getString("checkresult"))){
				rowArray[12]="盤點異常";
			}else{
				rowArray[12]="";
			}
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

/**
 * <br>
 * <br>目的：「盤點序號serialNo1」為新增資料後自動產生，編碼規則取同一個「入帳機關enterOrg」之最大「盤點序號serialNo1」+1
 * <br>參數：enterOrg 機關代號
 * <br>傳回：String
*/
public String getInserSerialNo1(String enterOrg){
	
	Database db = new Database();
	
	String sql="  select isnull(max(serialno1),'0')+1 as serialno1 from UNTPD_CHECKMOVABLE  ";
	sql+=" where enterorg="+Common.sqlChar(enterOrg);
	ResultSet rs=null;
	
	try 
	{
		rs = db.querySQL(sql);
		
		if (rs.next())
		{
			return rs.getString("serialno1");		
		}
		
	} 
	catch (Exception e) 
	{
		e.printStackTrace();
	}
		
	return "";
}

/**
 * <br>
 * <br>目的：2.	「資料截止日期closingDate」：依「入帳機關enterOrg」取「動產盤點資料檔UNTPD_CheckMovable」之最小的「資料截止日期closingDate」
 * <br>參數：enterOrg 機關代號
 * <br>傳回：String
*/
public String getInserClosingDate(String enterOrg){
	
	Database db = new Database();
	
	String sql="  select min(closingdate) closingdate from UNTPD_CHECKMOVABLE  ";
	sql+=" where enterorg="+Common.sqlChar(enterOrg);
	ResultSet rs=null;
	
	try 
	{
		rs = db.querySQL(sql);
		
		if (rs.next())
		{
			return Datetime.changeTaiwanYYMMDD(rs.getString("closingdate"), "1");	
		}
		
	} 
	catch (Exception e) 
	{
		e.printStackTrace();
	}
		
	return "";
}

}


