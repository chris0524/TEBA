/*
*<br>程式目的：權利主檔資料維護-標的資料
*<br>程式代號：untrt002f
*<br>程式日期：0940930
*<br>程式作者：cherry
*<br>--------------------------------------------------------
*<br>修改作者　　修改日期　　　修改目的
*<br>--------------------------------------------------------
*/

package unt.rt;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import unt.ch.UNTCH001Q;
import unt.ch.UNTCH_Tools;
import util.Common;
import util.Database;
import TDlib_Simple.com.src.SQLCreator;

public class UNTRT002F extends UNTCH001Q{


//傳回執行insert前之檢查sql
protected String[][] getInsertCheckSQL(){
	//取得標的次序
	Database db = new Database();
	ResultSet rs;	
	String sql="select case when max(serialNo1) is null then 1 else (max(serialNo1)+1) end as serialNo1 from UNTRT_AddDetail " +
		" where enterOrg = " + Common.sqlChar(enterOrg) + 
		" and ownership = " + Common.sqlChar(ownership) + 
		" and propertyNo = " + Common.sqlChar(propertyNo) + 
		" and serialNo = " + Common.sqlChar(serialNo) + 
		" and differenceKind = " + Common.sqlChar(differenceKind) +		
		"";		
	try {		
		rs = db.querySQL(sql);
		if (rs.next()){
		    setSerialNo1(Common.formatFrontZero(rs.getString("serialNo1"),3));
		} else {
			setSerialNo1("001");
		}
	} catch (Exception e) {
		e.printStackTrace();
	} finally {
		db.closeAll();
	}
	String[][] checkSQLArray = new String[1][4];
 	checkSQLArray[0][0]=" select count(*) as checkResult from UNTRT_AddDetail where 1=1 " + 
		" and enterOrg = " + Common.sqlChar(enterOrg) + 
		" and ownership = " + Common.sqlChar(ownership) + 
		" and propertyNo = " + Common.sqlChar(propertyNo) + 
		" and serialNo = " + Common.sqlChar(serialNo) + 
		" and serialNo1 = " + Common.sqlChar(serialNo1) + 
		" and differenceKind = " + Common.sqlChar(differenceKind) +
		"";
	checkSQLArray[0][1]=">";
	checkSQLArray[0][2]="0";
	checkSQLArray[0][3]="該筆資料己重複，請重新輸入！";
	return checkSQLArray;
}


//傳回insert sql
protected String[] getInsertSQL(){
	String[] execSQLArray = new String[1];
	execSQLArray[0] = new SQLCreator()._obtainSQLforInsert("UNTRT_AddDetail", getPKMap(), getRecordMap());
	return execSQLArray;
}
//傳回update sql
protected String[] getUpdateSQL(){
	String[] execSQLArray = new String[1];
	execSQLArray[0] = new SQLCreator()._obtainSQLforUpdate("UNTRT_AddDetail", getPKMap(), getRecordMap());
	return execSQLArray;
}


//傳回delete sql
protected String[] getDeleteSQL(){
	String[] execSQLArray = new String[1];
	execSQLArray[0] = new SQLCreator()._obtainSQLforDelete("UNTRT_AddDetail", getPKMap(), getRecordMap());
	return execSQLArray;
}


/**
 * <br>
 * <br>目的：依主鍵查詢單一資料
 * <br>參數：無
 * <br>傳回：傳回本物件
*/

public UNTRT002F  queryOne() throws Exception{
	Database db = new Database();
	UNTRT002F obj = this;
	try {
		String sql=" select a.*," +
					"(select organsname from SYSCA_ORGAN z where z.organid = a.enterorg) as enterOrgName," +
					"(select codename from SYSCA_CODE z where z.codekindid = 'OWA' and z.codeid = a.ownership) as ownershipName," +
					"(select codename from SYSCA_CODE z where z.codekindid = 'DFK' and z.codeid = a.differenceKind) as differenceKindName," +
					"(select propertyname from SYSPK_PROPERTYCODE z where z.enterorg = a.enterorg and z.propertyno = a.propertyno) as propertyNoName" +
					" from UNTRT_AddDetail a where 1=1 " +
					" and a.enterOrg = " + Common.sqlChar(enterOrg) +
					" and a.ownership = " + Common.sqlChar(ownership) +
					" and a.propertyNo = " + Common.sqlChar(propertyNo) +
					" and a.serialNo = " + Common.sqlChar(serialNo) +
					" and a.serialNo1 = " + Common.sqlChar(serialNo1) +
					" and a.differenceKind = " + Common.sqlChar(differenceKind) + 
					"";
		UNTCH_Tools ul = new UNTCH_Tools();
		ResultSet rs = db.querySQL(sql);
		if (rs.next()){
			obj.setEnterOrgName(rs.getString("EnterOrgName"));
			obj.setOwnershipName(rs.getString("OwnershipName"));
			obj.setDifferenceKindName(rs.getString("DifferenceKindName"));
			obj.setPropertyNoName(rs.getString("PropertyNoName"));
			
			obj.setEnterOrg(rs.getString("EnterOrg"));
			obj.setOwnership(rs.getString("Ownership"));
			obj.setDifferenceKind(rs.getString("DifferenceKind"));
			obj.setPropertyNo(rs.getString("PropertyNo"));
			obj.setSerialNo(rs.getString("SerialNo"));
			obj.setSerialNo1(rs.getString("SerialNo1"));
			obj.setSignNo(rs.getString("SignNo"));
			if(rs.getString("signNo").length() == 15){
				obj.setSignNo1(rs.getString("signNo").substring(0,1)+"000000");
				obj.setSignNo2(rs.getString("signNo").substring(0,3)+"0000");
				obj.setSignNo3(rs.getString("signNo").substring(0,7));
				obj.setSignNo4(rs.getString("signNo").substring(7,11));
				obj.setSignNo5(rs.getString("signNo").substring(11));
			}
			obj.setArea(rs.getString("Area"));
			obj.setHoldNume(rs.getString("HoldNume"));
			obj.setHoldDeno(rs.getString("HoldDeno"));
			obj.setHoldArea(rs.getString("HoldArea"));
			obj.setRightHoldNume(rs.getString("RightHoldNume"));
			obj.setRightHoldDeno(rs.getString("RightHoldDeno"));
			obj.setSetArea(rs.getString("SetArea"));
			obj.setRegisterNo1(rs.getString("RegisterNo1"));
			obj.setRightKind(rs.getString("RightKind"));
			obj.setRegisterNo2(rs.getString("RegisterNo2"));
			obj.setSetRightScope(rs.getString("SetRightScope"));
			obj.setSetObligor(rs.getString("SetObligor"));
			obj.setLandPurpose(rs.getString("LandPurpose"));
			obj.setDoorPlate(rs.getString("DoorPlate"));
			obj.setBuildingArea(rs.getString("BuildingArea"));
			obj.setBuildingOwner(rs.getString("BuildingOwner"));
			obj.setBuildingPurpose(rs.getString("BuildingPurpose"));
			obj.setNotes1(rs.getString("Notes1"));
			obj.setNotes(rs.getString("Notes"));
			obj.setEditID(rs.getString("EditID"));
			obj.setEditDate(ul._transToROC_Year(rs.getString("EditDate")));
			obj.setEditTime(rs.getString("EditTime"));

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
	ArrayList objList=new ArrayList();
	Database db = new Database();

	try {
		String sql=" select a.*, case a.ownership when '1' then '市有' when '2' then '國有' else '' end as ownershipName, " +
				" b.propertyName, d.organSName," +
				" e.dataState as checkDataState, e.verify as verify," +
				" c.signDesc as signName, a.signNo,"+
				" case e.dataState when '1' then '現存' when '2' then '已減損' else '' end as dataState" +
				" from UNTRT_AddDetail a, SYSPK_PropertyCode b, SYSCA_SIGN c, SYSCA_Organ d, UNTRT_AddProof e where 1=1 " +
				" and substring(a.signNo,1,7)= c.signNo" +
				" and a.enterOrg = e.enterOrg and a.ownership = e.ownership " +
				" and a.propertyNo = e.propertyNo and a.serialNo = e.serialNo " +
				" and a.propertyNo = b.propertyNo" +
				" and a.enterOrg = d.organID"+
				"";
		
	
			sql+=" and a.enterOrg = " + Common.sqlChar(getEnterOrg()) ;
			sql+=" and a.ownership = " + Common.sqlChar(getOwnership()) ;
			sql+=" and a.propertyNo = " + Common.sqlChar(getPropertyNo()) ;
			sql+=" and a.differenceKind = " + Common.sqlChar(getDifferenceKind()) ;
			sql+=" and a.serialNo=" + Common.sqlChar(getSerialNo());			
	
		
		ResultSet rs = db.querySQL(sql,true);
		processCurrentPageAttribute(rs);			
		if (getTotalRecord() > 0) {
			int count = getRecordStart();
			int end = getRecordEnd();
			do {
				if (count > end)
					break;
			String rowArray[]=new String[16];
			rowArray[0]=rs.getString("organSName"); 
			rowArray[1]=rs.getString("ownershipName"); 
			rowArray[2]=rs.getString("propertyName"); 
			rowArray[3]=rs.getString("serialNo"); 
			rowArray[4]=rs.getString("serialNo1"); 
			rowArray[5]= rs.getString("signName")+rs.getString("signNo").substring(7,11)+"-"+rs.getString("signNo").substring(11);
			rowArray[6]=""; 
			rowArray[7]=rs.getString("dataState"); 
			rowArray[8]=rs.getString("setArea"); 
			rowArray[9]=rs.getString("setObligor"); 
			rowArray[10]=rs.getString("enterOrg"); 
			rowArray[11]=rs.getString("ownership"); 
			rowArray[12]=rs.getString("propertyNo"); 
			rowArray[13]=rs.getString("serialNo"); 
			rowArray[14]=rs.getString("serialNo1");
			rowArray[15]=rs.getString("differenceKind"); 
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

	private Map getPKMap(){
		Map map = new HashMap();
		
		map.put("EnterOrg", getEnterOrg());
		map.put("Ownership", getOwnership());
		map.put("DifferenceKind", getDifferenceKind());
		map.put("PropertyNo", getPropertyNo());
		map.put("SerialNo", getSerialNo());
		map.put("SerialNo1", getSerialNo1());
		
		return map;
	}

	private Map getRecordMap(){
		Map map = new HashMap();
		
		String signNoStr = "";
		if("".equals(Common.get(getSignNo3()))){
		}else{
			signNoStr = this.getSignNo3() + this.getSignNo4() + this.getSignNo5();
		}		 
		
		map.put("EnterOrg", getEnterOrg());
		map.put("Ownership", getOwnership());
		map.put("DifferenceKind", getDifferenceKind());
		map.put("PropertyNo", getPropertyNo());
		map.put("SerialNo", getSerialNo());
		map.put("SerialNo1", getSerialNo1());
		map.put("SignNo", signNoStr);
		map.put("Area", getArea());
		map.put("HoldNume", getHoldNume());
		map.put("HoldDeno", getHoldDeno());
		map.put("HoldArea", getHoldArea());
		map.put("RightHoldNume", getRightHoldNume());
		map.put("RightHoldDeno", getRightHoldDeno());
		map.put("SetArea", getSetArea());
		map.put("RegisterNo1", getRegisterNo1());
		map.put("RightKind", getRightKind());
		map.put("RegisterNo2", getRegisterNo2());
		map.put("SetRightScope", getSetRightScope());
		map.put("SetObligor", getSetObligor());
		map.put("LandPurpose", getLandPurpose());
		map.put("DoorPlate", getDoorPlate());
		map.put("BuildingArea", getBuildingArea());
		map.put("BuildingOwner", getBuildingOwner());
		map.put("BuildingPurpose", getBuildingPurpose());
		map.put("Notes1", getNotes1());
		map.put("Notes", getNotes());
		map.put("EditID", getEditID());
		map.put("EditDate", getEditDate());
		map.put("EditTime", getEditTime());

		return map;
	}


private String enterOrg;
private String ownership;
private String differenceKind;
private String propertyNo;
private String serialNo;
private String serialNo1;
private String signNo;
private String area;
private String holdNume;
private String holdDeno;
private String holdArea;
private String rightHoldNume;
private String rightHoldDeno;
private String setArea;
private String registerNo1;
private String rightKind;
private String registerNo2;
private String setRightScope;
private String setObligor;
private String landPurpose;
private String doorPlate;
private String buildingArea;
private String buildingOwner;
private String buildingPurpose;
private String notes1;
private String notes;
private String editID;
private String editDate;
private String editTime;
public String getEnterOrg() {return checkGet(enterOrg);}
public void setEnterOrg(String enterOrg) {this.enterOrg = checkSet(enterOrg);}
public String getOwnership() {return checkGet(ownership);}
public void setOwnership(String ownership) {this.ownership = checkSet(ownership);}
public String getDifferenceKind() {return checkGet(differenceKind);}
public void setDifferenceKind(String differenceKind) {this.differenceKind = checkSet(differenceKind);}
public String getPropertyNo() {return checkGet(propertyNo);}
public void setPropertyNo(String propertyNo) {this.propertyNo = checkSet(propertyNo);}
public String getSerialNo() {return checkGet(serialNo);}
public void setSerialNo(String serialNo) {this.serialNo = checkSet(serialNo);}
public String getSerialNo1() {return checkGet(serialNo1);}
public void setSerialNo1(String serialNo1) {this.serialNo1 = checkSet(serialNo1);}
public String getSignNo() {return checkGet(signNo);}
public void setSignNo(String signNo) {this.signNo = checkSet(signNo);}
public String getArea() {return checkGet(area);}
public void setArea(String area) {this.area = checkSet(area);}
public String getHoldNume() {return checkGet(holdNume);}
public void setHoldNume(String holdNume) {this.holdNume = checkSet(holdNume);}
public String getHoldDeno() {return checkGet(holdDeno);}
public void setHoldDeno(String holdDeno) {this.holdDeno = checkSet(holdDeno);}
public String getHoldArea() {return checkGet(holdArea);}
public void setHoldArea(String holdArea) {this.holdArea = checkSet(holdArea);}
public String getRightHoldNume() {return checkGet(rightHoldNume);}
public void setRightHoldNume(String rightHoldNume) {this.rightHoldNume = checkSet(rightHoldNume);}
public String getRightHoldDeno() {return checkGet(rightHoldDeno);}
public void setRightHoldDeno(String rightHoldDeno) {this.rightHoldDeno = checkSet(rightHoldDeno);}
public String getSetArea() {return checkGet(setArea);}
public void setSetArea(String setArea) {this.setArea = checkSet(setArea);}
public String getRegisterNo1() {return checkGet(registerNo1);}
public void setRegisterNo1(String registerNo1) {this.registerNo1 = checkSet(registerNo1);}
public String getRightKind() {return checkGet(rightKind);}
public void setRightKind(String rightKind) {this.rightKind = checkSet(rightKind);}
public String getRegisterNo2() {return checkGet(registerNo2);}
public void setRegisterNo2(String registerNo2) {this.registerNo2 = checkSet(registerNo2);}
public String getSetRightScope() {return checkGet(setRightScope);}
public void setSetRightScope(String setRightScope) {this.setRightScope = checkSet(setRightScope);}
public String getSetObligor() {return checkGet(setObligor);}
public void setSetObligor(String setObligor) {this.setObligor = checkSet(setObligor);}
public String getLandPurpose() {return checkGet(landPurpose);}
public void setLandPurpose(String landPurpose) {this.landPurpose = checkSet(landPurpose);}
public String getDoorPlate() {return checkGet(doorPlate);}
public void setDoorPlate(String doorPlate) {this.doorPlate = checkSet(doorPlate);}
public String getBuildingArea() {return checkGet(buildingArea);}
public void setBuildingArea(String buildingArea) {this.buildingArea = checkSet(buildingArea);}
public String getBuildingOwner() {return checkGet(buildingOwner);}
public void setBuildingOwner(String buildingOwner) {this.buildingOwner = checkSet(buildingOwner);}
public String getBuildingPurpose() {return checkGet(buildingPurpose);}
public void setBuildingPurpose(String buildingPurpose) {this.buildingPurpose = checkSet(buildingPurpose);}
public String getNotes1() {return checkGet(notes1);}
public void setNotes1(String notes1) {this.notes1 = checkSet(notes1);}
public String getNotes() {return checkGet(notes);}
public void setNotes(String notes) {this.notes = checkSet(notes);}
public String getEditID() {return checkGet(editID);}
public void setEditID(String editID) {this.editID = checkSet(editID);}
public String getEditDate() {return checkGet(editDate);}
public void setEditDate(String editDate) {this.editDate = checkSet(editDate);}
public String getEditTime() {return checkGet(editTime);}
public void setEditTime(String editTime) {this.editTime = checkSet(editTime);}

private String enterOrgName;
private String ownershipName;
private String differenceKindName;
private String propertyNoName;
public String getEnterOrgName() {return checkGet(enterOrgName);}
public void setEnterOrgName(String enterOrgName) {this.enterOrgName = checkSet(enterOrgName);}
public String getOwnershipName() {return checkGet(ownershipName);}
public void setOwnershipName(String ownershipName) {this.ownershipName = checkSet(ownershipName);}
public String getDifferenceKindName() {return checkGet(differenceKindName);}
public void setDifferenceKindName(String differenceKindName) {this.differenceKindName = checkSet(differenceKindName);}
public String getPropertyNoName() {return checkGet(propertyNoName);}
public void setPropertyNoName(String propertyNoName) {	this.propertyNoName = checkSet(propertyNoName);}

private String signNo1;
private String signNo2;
private String signNo3;
private String signNo4;
private String signNo5;
public String getSignNo1() {return checkGet(signNo1);}
public void setSignNo1(String signNo1) {this.signNo1 = checkSet(signNo1);}
public String getSignNo2() {return checkGet(signNo2);}
public void setSignNo2(String signNo2) {this.signNo2 = checkSet(signNo2);}
public String getSignNo3() {return checkGet(signNo3);}
public void setSignNo3(String signNo3) {this.signNo3 = checkSet(signNo3);}
public String getSignNo4() {return checkGet(signNo4);}
public void setSignNo4(String signNo4) {this.signNo4 = checkSet(signNo4);}
public String getSignNo5() {return checkGet(signNo5);}
public void setSignNo5(String signNo5) {this.signNo5 = checkSet(signNo5);}

}