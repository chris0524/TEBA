/*
*<br>程式目的：非消品盤點資料維護
*<br>程式代號: untpd012f
*<br>程式日期：1030911
*<br>程式作者：Mike Kao
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

public class UNTPD012F extends UNTPD012Q{


String enterOrg;
String enterOrgName;
String serialNo1;//盤點序號
String barCode;
String ownership;
String closingDate;//資料截止日期
String bookAmount;
String actualAmount; //盤點數量
String bookUnit;
String bookValue;
String checkResult;//盤點結果
String oddsCause;//異常原因
String propertyKind;
String differenceKind;
String fundType;
String propertyNo;
String propertyNoName;
String serialNo;
String buyDate;
String sourceDate;
String propertyName;
String propertyName1;
String oldPropertyNo;
String oldSerialNo;
String nameplate;
String specification;
String material;
String propertyUnit;
String limitYear;
String useyear;
String place1;
String place1name;
String place;
String keepUnit;
String keeper;
String useUnit;
String userNo;
String usernote;
String notes;
String scrappedNote;
String labelNote;
String moveNote;
String placeNote;
String placeDetail1;
String placeDetail;
String placeDetail1Name;


public String getEnterOrg(){ return checkGet(enterOrg); }
public void setEnterOrg(String s){ enterOrg=checkSet(s); }
public String getEnterOrgName(){ return checkGet(enterOrgName); }
public void setEnterOrgName(String s){ enterOrgName=checkSet(s); }
public String getSerialNo1(){ return checkGet(serialNo1); }
public void setSerialNo1(String s){ serialNo1=checkSet(s); }
public String getBarCode(){ return checkGet(barCode); }
public void setBarCode(String s){ barCode=checkSet(s); }
public String getOwnership(){ return checkGet(ownership); }
public void setOwnership(String s){ ownership=checkSet(s); }
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
public String getPlace(){ return checkGet(place); }
public void setPlace(String s){ place=checkSet(s); }
public String getKeepUnit(){ return checkGet(keepUnit); }
public void setKeepUnit(String s){ keepUnit=checkSet(s); }
public String getKeeper(){ return checkGet(keeper); }
public void setKeeper(String s){ keeper=checkSet(s); }
public String getUseUnit(){ return checkGet(useUnit); }
public void setUseUnit(String s){ useUnit=checkSet(s); }
public String getUserNo(){ return checkGet(userNo); }
public void setUserNo(String s){ userNo=checkSet(s); }
public String getNotes(){ return checkGet(notes); }
public void setNotes(String s){ notes=checkSet(s); }
public String getSourceDate() {return checkGet(sourceDate);}
public void setSourceDate(String sourceDate) {this.sourceDate = checkSet(sourceDate);}
public String getPropertyName() {return checkGet(propertyName);}
public void setPropertyName(String propertyName) {this.propertyName = checkSet(propertyName);}
public String getUseyear() {return checkGet(useyear);}
public void setUseyear(String useyear) {this.useyear = checkSet(useyear);}
public String getPlace1() {return checkGet(place1);}
public void setPlace1(String place1) {this.place1 = checkSet(place1);}
public String getPlace1name() {return checkGet(place1name);}
public void setPlace1name(String place1name) {this.place1name = checkSet(place1name);}
public String getUsernote() {return checkGet(usernote);}
public void setUsernote(String usernote) {this.usernote = checkSet(usernote);}
public String getScrappedNote() {return checkGet(scrappedNote);}
public void setScrappedNote(String scrappedNote) {this.scrappedNote = checkSet(scrappedNote);}
public String getLabelNote() {return checkGet(labelNote);}
public void setLabelNote(String labelNote) {this.labelNote = checkSet(labelNote);}
public String getMoveNote() {return checkGet(moveNote);}
public void setMoveNote(String moveNote) {this.moveNote = checkSet(moveNote);}
public String getDifferenceKind() {return checkGet(differenceKind);}
public void setDifferenceKind(String differenceKind) {this.differenceKind = checkSet(differenceKind);}
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


public int getUseYear(String date){
	int useYear = 0;
	if(date != null){
		String today = Datetime.getYYYMMDD();
		String sdate = Datetime.changeTaiwanYYMMDD(date, "1");
		long calDate = Datetime.DateSubtraction(sdate, today);
		useYear = (int) (calDate/365);
	}
	
	return useYear;
}

//傳回insert sql
protected String[] getInsertSQL(){
	Map KeepUnitMap = TCGHCommon.getUntmp_KeepUnitCode(getEnterOrg()); //保管單位、使用單位
	Map KeeperMap = TCGHCommon.getUntmp_KeeperCode(getEnterOrg()); //保管人、使用人
	Map PropertyMap = TCGHCommon.getSyspk_PropertyCode2(getEnterOrg()); //物品名稱
	String[] execSQLArray = new String[1];
	execSQLArray[0]= " insert into UNTPD_CHECKNONEXP (" 
		 		   + " enterorg, "
		 		   + " closingdate, "
		 		   + " ownership, "
		 		   + " serialno1, "
		 		   + " barcode, "
		 		   + " bookamount, "
		 		   + " actualamount, "
		 		   + " bookunit, "
		 		   + " bookvalue, "
		 		   + " checkresult, "
		 		   + " oddscause, "
		 		   + " scrappednote, "
		 		   + " labelnote, "
		 		   + " movenote, "
		 		   + " differencekind, "
		 		   + " differencekindname, "
		 		   + " propertykind, "
		 		   + " fundtype, "
		 		   + " propertyno, "  
		 		   + " serialno, "
		 		   + " buydate, "
		 		   + " sourcedate, "
		 		   + " propertyname, "
		 		   + " propertyname1, "
		 		   + " oldpropertyno, "
		 		   + " oldserialno, "
		 		   + " nameplate, "
		 		   + " specification, "
		 		   + " material, "
		 		   + " propertyUnit, "
		 		   + " limitYear, "
		 		   + " useyear, "
		 		   + " keepunit, "
		 		   + " keepunitname, "
		 		   + " keeper, "
		 		   + " keepername, "
		 		   + " useunit, "
		 		   + " useunitname, "
		 		   + " userno, "
		 		   + " usernoname, "
		 		   + " usernote, "
		 		   + " place1, "
		 	       + " place1name, "
		 		   + " place, "
		 		   + " notes, "
		 		   + " editid, "
		 		   + " editdate, "
		 		   + " edittime "
		 		   + " )values("
		 		   + Common.sqlChar(getEnterOrg()) + ","
		 		   + Common.sqlChar(new Datetime().changeTaiwanYYMMDD(getClosingDate(),"2")) + ","
		 		   + Common.sqlChar(getOwnership()) + ","
				   + " ( select ISNULL(max(serialno1),'0')+1 as maxSerialno1 from UNTPD_CHECKNONEXP where 1=1 and enterorg = " + Common.sqlChar(getEnterOrg()) + ")" + ","
				   + Common.sqlChar(getDifferenceKind()+"-"+getPropertyNo()+"-"+getSerialNo()) + ","
				   + Common.sqlChar(getBookAmount()) + ","
				   + Common.sqlChar(getActualAmount()) + ","
		           + Common.sqlChar(getBookUnit()) + ","
		           + Common.sqlChar(getBookValue()) + ","
		           + Common.sqlChar("3") + ","   // 盤點結果預設為3(未盤點)
				   + Common.sqlChar(getOddsCause()) + ","
				   + Common.sqlChar(getScrappedNote()) + ","
				   + Common.sqlChar(getLabelNote()) + ","
				   + Common.sqlChar(getMoveNote()) + "," 
				   + Common.sqlChar(getDifferenceKind()) + ","   ; 
				   if("110".equals(getDifferenceKind())){
					   execSQLArray[0] += Common.sqlChar("公務用-公務類") + "," ;  
				   }else if("120".equals(getDifferenceKind())){
					   execSQLArray[0] += Common.sqlChar("公務用-作業基金") + "," ;   
				   }else{
					   execSQLArray[0] += "" + "," ;   
				   }
				   execSQLArray[0] += Common.sqlChar(getPropertyKind()) + ","
				   + Common.sqlChar(getFundType()) + ","
				   + Common.sqlChar(getPropertyNo()) + ","
				   + Common.sqlChar(getSerialNo()) + ","
		   		   + Common.sqlChar(new Datetime().changeTaiwanYYMMDD(getBuyDate(),"2")) + ","
		   		   + Common.sqlChar(new Datetime().changeTaiwanYYMMDD(getSourceDate(),"2")) + ","
		   	       + Common.sqlChar((String)PropertyMap.get(getPropertyNo())) + ","
		 		   + Common.sqlChar(getPropertyName1()) + ","   
		 		   + Common.sqlChar(getOldPropertyNo()) + ","
		 		   + Common.sqlChar(getOldSerialNo()) + ","
		 		   + Common.sqlChar(getNameplate()) + ","
		 		   + Common.sqlChar(getSpecification()) + ","
		 		   + Common.sqlChar(getMaterial()) + ","
		 		   + Common.sqlChar(getPropertyUnit()) + ","
		 		   + "(select limityear from SYSPK_PROPERTYCODE2 where 1=1 and enterorg = " + Common.sqlChar(getEnterOrg()) + " and propertyno ="+ Common.sqlChar(getPropertyNo()) + "),"
		 		   + getUseYear(new Datetime().changeTaiwanYYMMDD(getBuyDate(),"2")) + ","  //useyear
		 		   + Common.sqlChar(getKeepUnit()) + ","
		 		   + Common.sqlChar((String)KeepUnitMap.get(getKeepUnit())) + ","
		 		   + Common.sqlChar(getKeeper()) + ","
		 		   + Common.sqlChar((String)KeeperMap.get(getKeeper())) + ","
		 		   + Common.sqlChar(getUseUnit()) + ","
		 		   + Common.sqlChar((String)KeepUnitMap.get(getUseUnit())) + ","
		 		   + Common.sqlChar(getUserNo()) + ","
		 		   + Common.sqlChar((String)KeeperMap.get(getUserNo())) + ","
		 		   + Common.sqlChar(getUsernote()) + ","		 		   
	 	 		   + Common.sqlChar(getPlace1()) + ","
		 		   + Common.sqlChar(getPlace1name()) + ","
		 		   + Common.sqlChar(getPlace()) + "," 
		 	  	   + Common.sqlChar(getNotes()) + ","  
		 		   + Common.sqlChar(getEditID()) + ","
		 		   + Common.sqlChar(new Datetime().changeTaiwanYYMMDD(getEditDate(),"2")) + ","
		 		   + Common.sqlChar(getEditTime()) 
		 		   + ")" ;
	return execSQLArray;
}


//傳回update sql
protected String[] getUpdateSQL(){
	Map KeepUnitMap = TCGHCommon.getUntmp_KeepUnitCode(getEnterOrg()); //保管單位、使用單位
	Map KeeperMap = TCGHCommon.getUntmp_KeeperCode(getEnterOrg()); //保管人、使用人
	Map PropertyMap = TCGHCommon.getSyspk_PropertyCode2(getEnterOrg()); //物品名稱
	String[] execSQLArray = new String[2];
	execSQLArray[0]= " update UNTPD_CHECKNONEXP set " 
//		   		   + " enterorg = " + Common.sqlChar(getEnterOrg()) + ","
//		   		   + " serialno1 = " + Common.sqlChar(getSerialNo1()) + ","
		           + " closingdate = " + Common.sqlChar(new Datetime().changeTaiwanYYMMDD(getClosingDate(),"2")) + ","
		           + " ownership = " + Common.sqlChar(getOwnership()) + ","
		   		   + " barcode = " + Common.sqlChar(getDifferenceKind()+"-"+getPropertyNo()+"-"+getSerialNo()) + ","
		   		   + " bookamount = " + Common.sqlChar(getBookAmount()) + ","
		   		   + " actualamount = " + Common.sqlChar(getActualAmount()) + ","
		   		   + " bookunit = " + Common.sqlChar(getBookUnit()) + ","
		   		   + " bookvalue = " + Common.sqlChar(getBookValue()) + ","
		   		   + " checkresult = " + Common.sqlChar(getCheckResult()) + ","
		   		   + " oddscause = " + Common.sqlChar(getOddsCause()) + ",";
		   		   if("Y".equals(getScrappedNote())){
		   			   execSQLArray[0] += "scrappednote = 'Y', ";
		   		   }else{
		   			   execSQLArray[0] += "scrappednote = 'N', ";
		   		   }
		   		   if("Y".equals(getLabelNote())){
		   			   execSQLArray[0] += "labelnote = 'Y', ";
		   		   }else{
		   			   execSQLArray[0] += "labelnote = 'N', ";
		   		   }
		   		   if("Y".equals(getMoveNote())){
		   			   execSQLArray[0] += "moveNote = 'Y', ";
		   		   }else{
		   			   execSQLArray[0] += "moveNote = 'N', ";
		   		   }
		   		   execSQLArray[0] += " differencekind = " + Common.sqlChar(getDifferenceKind()) + ",";
				   if("110".equals(getDifferenceKind())){
					   execSQLArray[0] += " differencekindname = " + Common.sqlChar("公務用-公務類") + "," ;  
				   }else if("120".equals(getDifferenceKind())){
					   execSQLArray[0] += " differencekindname = " + Common.sqlChar("公務用-作業基金") + "," ;   
				   }else{
					   execSQLArray[0] += " differencekindname = '' " + "," ;   
				   }
				   execSQLArray[0] += " propertykind = " + Common.sqlChar(getPropertyKind()) + ","
		   		   + " fundtype = " + Common.sqlChar(getFundType()) + ","
		   		   + " propertyno = " + Common.sqlChar(getPropertyNo()) + ","
		   		   + " serialno = " + Common.sqlChar(getSerialNo()) + ","
		   		   + " buydate = " + Common.sqlChar(new Datetime().changeTaiwanYYMMDD(getBuyDate(),"2")) + ","
		   		   + " sourcedate = " + Common.sqlChar(new Datetime().changeTaiwanYYMMDD(getSourceDate(),"2")) + ","
		   		   + " propertyname = " + Common.sqlChar((String)PropertyMap.get(getPropertyNo())) + ","
		   		   + " propertyname1 = " + Common.sqlChar(getPropertyName1()) + ","
		   		   + " oldpropertyno = " + Common.sqlChar(getOldPropertyNo()) + ","
		   		   + " oldserialno = " + Common.sqlChar(getOldSerialNo()) + ","
		   		   + " nameplate = " + Common.sqlChar(getNameplate()) + ","
		   		   + " specification = " + Common.sqlChar(getSpecification()) + "," 
		   	       + " material = " + Common.sqlChar(getMaterial()) + ","	  
		   		   + " propertyunit = " + Common.sqlChar(getPropertyUnit()) + ","
		   		   + " limitYear = " + Common.sqlChar(getLimitYear()) + ","
		   		   + " useyear = " +   Common.sqlChar(String.valueOf(getUseYear(new Datetime().changeTaiwanYYMMDD(getBuyDate(),"2")))) + ","  //useyear 	   	
		   		   + " keepunit = " + Common.sqlChar(getKeepUnit()) + ","
		   		   + " keepunitname = " + Common.sqlChar((String)KeepUnitMap.get(getKeepUnit())) + ","
		   		   + " keeper = " + Common.sqlChar(getKeeper()) + ","
		   		   + " keepername = " + Common.sqlChar((String)KeeperMap.get(getKeeper())) + ","
		   		   + " useunit = " + Common.sqlChar(getUseUnit()) + ","
		   		   + " useunitname = " + Common.sqlChar((String)KeepUnitMap.get(getUseUnit())) + ","
		   		   + " userno = " + Common.sqlChar(getUserNo()) + ","	
		   		   + " usernoname = " + Common.sqlChar((String)KeeperMap.get(getUserNo())) + ","
		   	       + " usernote = " + Common.sqlChar(getUsernote()) + ","
		   		   + " place1 = " + Common.sqlChar(getPlaceDetail1()) + ","
		   	       + " place1name = " + Common.sqlChar(getPlace1name()) + ","
		   		   + " place = " + Common.sqlChar(getPlaceDetail()) + ","
		   		   + " notes = " + Common.sqlChar(getNotes()) + ","		   		 
		   		   + " editID = " + Common.sqlChar(getEditID()) + "," 
		   		   + " editDate = " + Common.sqlChar(new Datetime().changeTaiwanYYMMDD(getEditDate(),"2")) + "," 
		   		   + " editTime = " + Common.sqlChar(getEditTime()) 
		   		   + " where 1=1 " 
		   		   + " and enterOrg = " + Common.sqlChar(getEnterOrg()) 
		   		   + " and serialno1 = " + Common.sqlChar(getSerialNo1())
		   		   + "";
	
	
	String sql = "update UNTNE_NONEXPDETAIL set" +
						" place = " + Common.sqlChar(this.getPlaceDetail()) + "," + 
						" place1 = " + Common.sqlChar(this.getPlaceDetail1()) +
					" from UNTNE_NONEXPDETAIL a" +
					" inner join UNTPD_CHECKNONEXP b" +
					" on a.enterorg = b.enterorg and a.differencekind = b.differencekind and a.ownership = b.ownership and a.propertyno = b.propertyno and a.serialno = b.serialno" +
					" and b.enterorg = "  + Common.sqlChar(enterOrg) +
					" and b.serialno1 = " + Common.sqlChar(serialNo1);
	
	Database db = null;
	try{
		db = new Database();
		db.querySQL_NoChange(sql);
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
	execSQLArray[0]= " delete UNTPD_CHECKNONEXP where 1=1 "
		           + " and enterOrg = " + Common.sqlChar(getEnterOrg())
		           + " and serialno1 = " + Common.sqlChar(getSerialNo1());
	//System.out.println(execSQLArray[0]);
	return execSQLArray;
}


/**
 * <br>
 * <br>目的：依主鍵查詢單一資料
 * <br>參數：無
 * <br>傳回：傳回本物件
*/

public UNTPD012F  queryOne() throws Exception{
	Database db = new Database();
	UNTPD012F obj = this;
	try {
		String sql= " select p.enterorg, p.closingdate, p.ownership, p.serialno1, p.barcode, p.bookamount, p.actualamount, p.bookunit, p.bookvalue, p.checkresult, p.oddscause, p.scrappednote, p.labelnote, p.movenote, p.differencekind, p.propertykind, p.fundtype, p.propertyno, " +
				" p.propertyname, p.serialno, p.buydate, p.sourcedate, p.propertyname, b.propertyname1, p.oldpropertyno, p.oldserialno, p.nameplate, p.specification, p.material, p.propertyunit, p.limityear, p.useyear, p.keepunit, p.keeper, p.useunit, p.userno, p.usernote, " +
				" p.place1, b.place1 as placedetail1, p.place, b.place as placedetail, p.place1name, p.editid, p.editdate, p.edittime, s.organsname, p.notes" 
				  + " from UNTPD_CHECKNONEXP p " 
				  + " left join UNTNE_NONEXPDETAIL b on p.enterorg = b.enterorg and p.ownership = b.ownership and p.propertyno = b.propertyno and p.serialno = b.serialno and p.differencekind = b.differencekind ,SYSCA_ORGAN s  " 
				  + " where 1=1 "
				  + " and p.enterorg=s.organid "
			 	  + " and p.enterorg = " + Common.sqlChar(enterOrg)
			 	  + " and p.serialno1 = " + Common.sqlChar(serialNo1)
			 	  + "";
		//System.out.println(sql);
		ResultSet rs = db.querySQL_NoChange(sql);
		if (rs.next()){
			obj.setEnterOrg(rs.getString("enterOrg") == null ? "" : rs.getString("enterOrg"));
			obj.setClosingDate(rs.getString("closingdate") == null ? "" : new Datetime().changeTaiwanYYMMDD(rs.getString("closingdate"),"1"));
			obj.setOwnership(rs.getString("ownership") == null ? "" : rs.getString("ownership"));
			obj.setSerialNo1(rs.getString("serialno1") == null ? "" : rs.getString("serialno1"));
			obj.setBarCode(rs.getString("barCode") == null ? "" : rs.getString("barCode"));
			obj.setBookAmount(rs.getString("bookamount") == null ? "0" : rs.getString("bookamount"));
			obj.setActualAmount(rs.getString("actualamount") == null ? "" : rs.getString("actualamount"));
			obj.setBookUnit(rs.getString("bookunit") == null ? "" : rs.getString("bookunit"));
			obj.setBookValue(rs.getString("bookvalue") == null ? "" : rs.getString("bookvalue"));
			obj.setCheckResult(rs.getString("checkresult") == null ? "" : rs.getString("checkresult"));
			obj.setOddsCause(rs.getString("oddscause") == null ? "" : rs.getString("oddscause"));
			obj.setScrappedNote(rs.getString("scrappednote") == null ? "" : rs.getString("scrappednote"));
			obj.setLabelNote(rs.getString("labelnote") == null ? "" : rs.getString("labelnote"));
			obj.setMoveNote(rs.getString("moveNote") == null ? "" : rs.getString("moveNote"));
			obj.setDifferenceKind(rs.getString("differencekind") == null ? "" : rs.getString("differencekind"));
			obj.setPropertyKind(rs.getString("propertykind") == null ? "" : rs.getString("propertykind"));
			obj.setFundType(rs.getString("fundtype") == null ? "" : rs.getString("fundtype"));
			obj.setPropertyNo(rs.getString("propertyno") == null ? "" : rs.getString("propertyno"));
			obj.setPropertyNoName(rs.getString("propertyname") == null ? "" : rs.getString("propertyname"));
			obj.setSerialNo(rs.getString("serialno") == null ? "" : rs.getString("serialno"));
			obj.setBuyDate(rs.getString("buydate") == null ? "" : new Datetime().changeTaiwanYYMMDD(rs.getString("buydate"),"1"));
			obj.setSourceDate(rs.getString("sourceDate") == null ? "" : new Datetime().changeTaiwanYYMMDD(rs.getString("sourceDate"),"1"));
			obj.setPropertyName(rs.getString("propertyname") == null ? "" : rs.getString("propertyname"));
			obj.setPropertyName1(rs.getString("propertyname1") == null ? "" : rs.getString("propertyname1"));
			obj.setOldPropertyNo(rs.getString("oldpropertyno") == null ? "" : rs.getString("oldpropertyno"));
			obj.setOldSerialNo(rs.getString("oldserialno") == null ? "" : rs.getString("oldserialno"));
			obj.setNameplate(rs.getString("nameplate") == null ? "" : rs.getString("nameplate"));
			obj.setSpecification(rs.getString("specification") == null ? "" : rs.getString("specification"));
			obj.setMaterial(rs.getString("material") == null ? "" : rs.getString("material"));
			obj.setPropertyUnit(rs.getString("propertyunit") == null ? "" : rs.getString("propertyunit"));
			obj.setLimitYear(rs.getString("limityear") == null ? "" : Datetime.formatMonths(rs.getString("limityear")));
			obj.setUseyear(rs.getString("useyear") == null ? "" : rs.getString("useyear"));
			obj.setKeepUnit(rs.getString("keepunit") == null ? "" : rs.getString("keepunit"));
			obj.setKeeper(rs.getString("keeper") == null ? "" : rs.getString("keeper"));
			obj.setUseUnit(rs.getString("useunit") == null ? "" : rs.getString("useunit"));
			obj.setUserNo(rs.getString("userno") == null ? "" : rs.getString("userno"));
			obj.setUsernote(rs.getString("usernote") == null ? "" : rs.getString("usernote"));
			obj.setPlace1(rs.getString("place1") == null ? "" : rs.getString("place1"));
			obj.setPlace(rs.getString("place") == null ? "" : rs.getString("place"));
			obj.setPlace1name(rs.getString("place1name") == null ? "" : rs.getString("place1name"));
			
			obj.setPlaceDetail1(rs.getString("placeDetail1") == null ? "" : rs.getString("placeDetail1"));
			obj.setPlaceDetail(rs.getString("placeDetail") == null ? "" : rs.getString("placeDetail"));
			String placeDetail1Name = new UNTCH_Tools().getPlace1Name(enterOrg, Common.get(rs.getString("placeDetail1")));
			obj.setPlaceDetail1Name(placeDetail1Name);
			
			if(!"".equals(Common.get(rs.getString("placeDetail1"))) || !"".equals(Common.get(rs.getString("placeDetail")))){
				obj.setPlaceNote("Y");
			}else{
				obj.setPlaceNote("N");
			}
			
			obj.setNotes(Common.get(rs.getString("notes")));
			obj.setEditID(rs.getString("editID") == null ? "" : rs.getString("editID"));
			obj.setEditDate(rs.getString("editDate") == null ? "" : new Datetime().changeTaiwanYYMMDD(rs.getString("editDate"),"1"));
			obj.setEditTime(rs.getString("editTime") == null ? "" : rs.getString("editTime"));
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
	if ("".equals(getQ_enterOrg())){
		where += " and p.enterorg = " + Common.sqlChar(getEnterOrg());
	}else{
		where += " and p.enterorg = " + Common.sqlChar(getQ_enterOrg());
	}
	if (!"".equals(getQ_ownership())){
		where += " and p.ownership = " + Common.sqlChar(getQ_ownership());			
	}
	if (!"".equals(getQ_differenceKind())){
		where += " and p.differencekind = " + Common.sqlChar(getQ_differenceKind());			
	}
	if (!"".equals(getQ_propertyType())){
		where += " and p.propertyType = " + Common.sqlChar(getQ_propertyType());			
	}
	if (!"".equals(getQ_propertyNoS())){
		where += " and p.propertyNo >= " + Common.sqlChar(getQ_propertyNoS());		
	}
	if (!"".equals(getQ_propertyNoE())){
		where += " and p.propertyNo <= " + Common.sqlChar(getQ_propertyNoE());		
	}
	if (!"".equals(getQ_serialNoS())){
		where += " and p.serialNo >= " + Common.sqlChar(getQ_serialNoS());			
	}
	if (!"".equals(getQ_serialNoE())){
		where += " and p.serialNo <= " + Common.sqlChar(getQ_serialNoE());			
	}	
	if (!"".equals(getQ_buyDateS())){
		where += " and p.buyDate >= " + Common.sqlChar(new Datetime().changeTaiwanYYMMDD(getQ_buyDateS(),"2"));				
	}
	if (!"".equals(getQ_buyDateE())){
		where += " and p.buyDate <= " + Common.sqlChar(new Datetime().changeTaiwanYYMMDD(getQ_buyDateE(),"2"));				
	}
	if (!"".equals(getQ_barcode())){
		where += " and p.barcode = " + Common.sqlChar(getQ_barcode());				
	}
	if (!"".equals(getQ_checkResult())){
		where += " and p.checkResult = " + Common.sqlChar(getQ_checkResult());		
	}
	if (!"".equals(getQ_oldPropertyNo())){
		where += " and p.oldPropertyNo = " + Common.sqlChar(getQ_oldPropertyNo());	
	}
	if (!"".equals(getQ_oldSerialNoS())){
		where += " and p.oldSerialNo >= " + Common.sqlChar(getQ_oldSerialNoS());		
	}
	if (!"".equals(getQ_oldSerialNoE())){
		where += " and p.oldSerialNo <= " + Common.sqlChar(getQ_oldSerialNoE());		
	}
	if (!"".equals(getQ_propertyKind())){
		where += " and p.propertyKind = " + Common.sqlChar(getQ_propertyKind());		
	}
	if (!"".equals(getQ_fundType())){
		where += " and p.fundType = " + Common.sqlChar(getQ_fundType());				
	}
	if (!"".equals(getQ_keepUnit())){
		where += " and p.keepUnit = " + Common.sqlChar(getQ_keepUnit());				
	}
	if (!"".equals(getQ_keeper())){
		where += " and p.keeper = " + Common.sqlChar(getQ_keeper());				
	}
	if (!"".equals(getQ_useUnit())){
		where += " and p.useUnit = " + Common.sqlChar(getQ_useUnit());				
	}
	if (!"".equals(getQ_userNo())){
		where += " and p.userNo = " + Common.sqlChar(getQ_userNo());					
	}
	if (!"".equals(getQ_usernote())){
		where += " and p.usernote like " + Common.sqlChar("%"+getQ_usernote()+"%");	
	}
	if (!"".equals(getQ_place1())){
		where += " and p.place1 = " + Common.sqlChar(getQ_place1());	
	}
	if (!"".equals(getQ_place())){
		where += " and p.place like " + Common.sqlChar("%"+getQ_place()+"%");	
	}	
	//報廢註記
	if ("Y".equals(getQ_scrappedNote())){
		where += " and p.scrappednote = 'Y' ";
	}
	
	//補印標籤註記
	if ("Y".equals(getQ_labelNote())){
		where += " and p.labelnote = 'Y' ";
	} 

	//移動註記
	if ("Y".equals(getQ_moveNote())){
		where += " and p.movenote = 'Y' ";
	} 

	
	Database db = new Database();
	ArrayList objList=new ArrayList();
	try {
		String sql = " select p.enterorg ,p.serialno1 ,p.differencekindname ,p.propertyno "
				   + " ,p.serialno  ,k.propertyname ,p.propertyname1 ,p.bookamount ,p.actualamount ,p.bookvalue " 
				   + " ,p.keepunitname ,p.keepername, p.checkresult " 
				   + " from UNTPD_CHECKNONEXP p "
				   + " left join SYSPK_PROPERTYCODE2 k on "
				   + " p.propertyno=k.propertyno "
				   + " and (p.enterorg=k.enterorg or k.enterorg='000000000A') "
				   + " where 1=1 "
	       		   + where
	       		   + " order by p.propertyno ,p.serialno " ;

		//System.out.println(sql);
		
		
		ResultSet rs = db.querySQL(sql,true);
		processCurrentPageAttribute(rs);
		if (getTotalRecord() > 0) {
			int count = getRecordStart();
			int end = getRecordEnd();
			do {
			if (count > end) break;
			String rowArray[]=new String[13];
			rowArray[0]=Common.get(rs.getString("enterOrg"));  	
			rowArray[1]=Common.get(rs.getString("serialno1")); 	//盤點序號
			rowArray[2]=Common.get(rs.getString("differencekindname")); 
			rowArray[3]=Common.get(rs.getString("propertyno"));
			rowArray[4]=Common.get(rs.getString("serialno")); 
			rowArray[5]=Common.get(rs.getString("propertyname")); 
			rowArray[6]=Common.get(rs.getString("propertyname1")); 
			rowArray[7]=Common.get(rs.getString("bookamount")); 
			rowArray[8]=Common.get(rs.getString("actualamount")); 
			rowArray[9]=Common.valueFormat(Common.get(rs.getString("bookvalue"))); 
			rowArray[10]=Common.get(rs.getString("keepunitname"));
			rowArray[11]=Common.get(rs.getString("keepername"));
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

}


