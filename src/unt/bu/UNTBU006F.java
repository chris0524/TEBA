/*
*<br>程式目的：建物主檔資料維護--基地資料
*<br>程式代號：untbu006f
*<br>程式日期：0940919
*<br>程式作者：clive.chang
*<br>--------------------------------------------------------
*<br>修改作者　　修改日期　　　修改目的
*<br>--------------------------------------------------------
*/

package unt.bu;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import unt.ch.UNTCH_Tools;
import util.Common;
import util.Database;
import TDlib_Simple.com.src.SQLCreator;

public class UNTBU006F extends UNTBU001Q{

	private String enterOrg;
	private String ownership;
	private String differenceKind;
	private String propertyNo;
	private String serialNo;
	private String serialNo1;
	private String signNo;
	private String signNo1;
	private String signNo2;
	private String signNo3;
	private String signNo4;
	private String signNo5;
	private String ownership1;
	private String owner;
	private String manageOrg;
	private String area;
	private String holdNume;
	private String holdDeno;
	private String holdArea;
	private String useSeparate;
	private String useKind;
	private String field;
	private String landRule;
	private String notes1;
	private String notes;
	private String editID;
	private String editDate;
	private String editTime;

	private String enterOrgName;
	private String ownerName;
	private String manageOrgName;
	
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
	public String getOwnership1() {return checkGet(ownership1);}
	public void setOwnership1(String ownership1) {this.ownership1 = checkSet(ownership1);}
	public String getOwner() {return checkGet(owner);}
	public void setOwner(String owner) {this.owner = checkSet(owner);}
	public String getManageOrg() {return checkGet(manageOrg);}
	public void setManageOrg(String manageOrg) {this.manageOrg = checkSet(manageOrg);}
	public String getArea() {return checkGet(area);}
	public void setArea(String area) {this.area = checkSet(area);}
	public String getHoldNume() {return checkGet(holdNume);}
	public void setHoldNume(String holdNume) {this.holdNume = checkSet(holdNume);}
	public String getHoldDeno() {return checkGet(holdDeno);}
	public void setHoldDeno(String holdDeno) {this.holdDeno = checkSet(holdDeno);}
	public String getHoldArea() {return checkGet(holdArea);}
	public void setHoldArea(String holdArea) {this.holdArea = checkSet(holdArea);}
	public String getUseSeparate() {return checkGet(useSeparate);}
	public void setUseSeparate(String useSeparate) {this.useSeparate = checkSet(useSeparate);}
	public String getUseKind() {return checkGet(useKind);}
	public void setUseKind(String useKind) {this.useKind = checkSet(useKind);}
	public String getField() {return checkGet(field);}
	public void setField(String field) {this.field = checkSet(field);}
	public String getLandRule() {return checkGet(landRule);}
	public void setLandRule(String landRule) {this.landRule = checkSet(landRule);}
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
	public String getEnterOrgName() {return checkGet(enterOrgName);}
	public void setEnterOrgName(String enterOrgName) {this.enterOrgName = checkSet(enterOrgName);}
	public String getOwnerName() {return checkGet(ownerName);}
	public void setOwnerName(String ownerName) {this.ownerName = checkSet(ownerName);}
	public String getManageOrgName() {return checkGet(manageOrgName);}
	public void setManageOrgName(String manageOrgName) {this.manageOrgName = checkSet(manageOrgName);}

	private Map getPKMap(){
		Map map = new HashMap();
		
		map.put("enterOrg", getEnterOrg());
		map.put("ownership", getOwnership());
		map.put("differenceKind", getDifferenceKind());
		map.put("propertyNo", getPropertyNo());
		map.put("serialNo", getSerialNo());
		map.put("serialNo1", getSerialNo1());
		
		return map;
	}
	
	private Map getRecordMap(){
		Map map = new HashMap();
		
		map.put("enterOrg", getEnterOrg());
		map.put("ownership", getOwnership());
		map.put("differenceKind", getDifferenceKind());
		map.put("propertyNo", getPropertyNo());
		map.put("serialNo", getSerialNo());
		map.put("serialNo1", getSerialNo1());
		map.put("signNo", getSignNo());
		map.put("ownership1", getOwnership1());
		map.put("owner", getOwner());
		map.put("manageOrg", getManageOrg());
		map.put("area", getArea());
		map.put("holdNume", getHoldNume());
		map.put("holdDeno", getHoldDeno());
		map.put("holdArea", getHoldArea());
		map.put("useSeparate", getUseSeparate());
		map.put("useKind", getUseKind());
		map.put("field", getField());
		map.put("landRule", getLandRule());
		map.put("notes1", getNotes1());
		map.put("notes", getNotes());
		map.put("editID", getEditID());
		map.put("editDate", new UNTCH_Tools()._transToCE_Year(getEditDate()));
		map.put("editTime", getEditTime());

		return map;
	}
	
	
//傳回執行insert前之檢查sql
protected String[][] getInsertCheckSQL(){
	//取得自動編號
	Database db = new Database();
	ResultSet rs;	
	String sql="select max(serialNo1)+1 as serialNo1 from UNTBU_Base " +
		" where enterOrg = " + Common.sqlChar(enterOrg) +
		" and ownership = " + Common.sqlChar(ownership) +
		" and propertyNo = " + Common.sqlChar(propertyNo) +
		" and serialNo = " + Common.sqlChar(serialNo) +
		" and differenceKind = " + Common.sqlChar(differenceKind) ;	
	try {		
		rs = db.querySQL(sql);
		if (rs.next()){
			if (rs.getString("serialNo1")==null)
				setSerialNo1("1");
			else
				setSerialNo1(rs.getString("serialNo1"));
		}
	} catch (Exception e) {
		e.printStackTrace();
	} finally {
		db.closeAll();
	}	
	String[][] checkSQLArray = new String[1][4];
 	checkSQLArray[0][0]=" select count(*) as checkResult from UNTBU_Base where 1=1 " + 
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
	
	this.setSignNo(this.getSignNo3() + this.getSignNo4() + this.getSignNo5());
	
	execSQLArray[0] = new SQLCreator()._obtainSQLforInsert("UNTBU_Base", getPKMap(), getRecordMap());
	
	return execSQLArray;
}


//傳回update sql
protected String[] getUpdateSQL(){
	String[] execSQLArray = new String[1];
	
	this.setSignNo(this.getSignNo3() + this.getSignNo4() + this.getSignNo5());
	
	execSQLArray[0] = new SQLCreator()._obtainSQLforUpdate("UNTBU_Base", getPKMap(), getRecordMap());
	
	return execSQLArray;
}


//傳回delete sql
protected String[] getDeleteSQL(){
	String[] execSQLArray = new String[1];
	
	execSQLArray[0] = new SQLCreator()._obtainSQLforDelete("UNTBU_Base", getPKMap(), getRecordMap());
	
	return execSQLArray;
}


/**
 * <br>
 * <br>目的：依主鍵查詢單一資料
 * <br>參數：無
 * <br>傳回：傳回本物件
*/

public UNTBU006F  queryOne() throws Exception{
	Database db = new Database();
	UNTBU006F obj = this;
	try {
		String sql=" select a.*, "
				+ "b.organSName as manageOrgName "+
			" from UNTBU_Base a left join SYSCA_Organ b on a.manageOrg=b.organID where 1=1 " +
			" and a.enterOrg = " + Common.sqlChar(enterOrg) +
			" and a.ownership = " + Common.sqlChar(ownership) +
			" and a.propertyNo = " + Common.sqlChar(propertyNo) +
			" and a.serialNo = " + Common.sqlChar(serialNo) +
			" and a.serialNo1 = " + Common.sqlChar(serialNo1) +
			" and differenceKind = " + Common.sqlChar(differenceKind) +
			"";
		ResultSet rs = db.querySQL(sql);
		if (rs.next()){			
			obj.setEnterOrg(rs.getString("enterOrg"));
			obj.setOwnership(rs.getString("ownership"));
			obj.setPropertyNo(rs.getString("propertyNo"));
			obj.setSerialNo(rs.getString("serialNo"));
			obj.setSerialNo1(rs.getString("serialNo1"));
			obj.setSignNo(rs.getString("signNo"));
			if (getSignNo().length()>11) {
				obj.setSignNo1(rs.getString("signNo").substring(0,1)+"000000");
				obj.setSignNo2(rs.getString("signNo").substring(0,3)+"0000");
				obj.setSignNo3(rs.getString("signNo").substring(0,7));			
				obj.setSignNo4(rs.getString("signNo").substring(7,11));
				obj.setSignNo5(rs.getString("signNo").substring(11));
			}
			obj.setOwnership1(rs.getString("ownership1"));
			obj.setOwner(rs.getString("owner"));
			obj.setManageOrg(rs.getString("manageOrg"));
			obj.setManageOrgName(rs.getString("manageOrgName"));
			obj.setArea(rs.getString("area"));
			obj.setHoldNume(rs.getString("holdNume"));
			obj.setHoldDeno(rs.getString("holdDeno"));
			obj.setHoldArea(rs.getString("holdArea"));
			obj.setUseSeparate(rs.getString("useSeparate"));
			obj.setUseKind(rs.getString("useKind"));
			obj.setField(rs.getString("field"));
			obj.setLandRule(rs.getString("landRule"));
			obj.setNotes1(rs.getString("notes1"));
			obj.setNotes(rs.getString("notes"));
			obj.setEditID(rs.getString("editID"));
			obj.setEditDate(new UNTCH_Tools()._transToROC_Year(rs.getString("editDate")));
			obj.setEditTime(rs.getString("editTime"));
			obj.setDifferenceKind(rs.getString("differenceKind"));
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
	
	try {
		ResultSet rs = null;
		String sql=" select a.enterOrg, a.ownership, a.propertyNo, a.serialNo, a.serialNo1, a.differencekind, a.signNo, a.ownership1, " +
			" c.codeName as ownership1Name, a.owner "+
			" from UNTBU_Base a left join SYSCA_Code c on a.ownership1=c.codeID and c.codeKindID='OWN' where 1=1 " +
			""; 
			if (!"".equals(getEnterOrg()))
				sql+=" and a.enterOrg = " + Common.sqlChar(getEnterOrg()) ;
			if (!"".equals(getOwnership()))
				sql+=" and a.ownership = " + Common.sqlChar(getOwnership()) ;
			if (!"".equals(getPropertyNo()))
				sql+=" and a.propertyNo = " + Common.sqlChar(getPropertyNo()) ;
			if (!"".equals(getSerialNo()))
				sql+=" and a.serialNo = " + Common.sqlChar(getSerialNo()) ;
			if (!"".equals(getDifferenceKind()))
				sql+=" and a.differencekind = " + Common.sqlChar(getDifferenceKind()) ;
		
		rs = db.querySQL(sql + " order by a.serialNo1 ",true);
		//System.out.println(sql);
		processCurrentPageAttribute(rs);			
		if (getTotalRecord() > 0) {
			int count = getRecordStart();
			int end = getRecordEnd();
			do {
				if (count > end)
					break;
			String rowArray[]=new String[9];
			rowArray[0]=rs.getString("enterOrg"); 
			rowArray[1]=rs.getString("ownership"); 
			rowArray[2]=rs.getString("propertyNo"); 
			rowArray[3]=rs.getString("serialNo"); 
			rowArray[4]=rs.getString("serialNo1"); 
			rowArray[5]=getSignDescName(rs.getString("signNo").substring(0,7)) + rs.getString("signNo").substring(7,11) + '-' + rs.getString("signNo").substring(11); 
			rowArray[6]=rs.getString("ownership1Name"); 
			rowArray[7]=rs.getString("owner"); 
			rowArray[8]=rs.getString("differencekind");
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


private String getSignDescName(String signNo){
	Database db = null;
	ResultSet rs = null;
	String sql = null;
	String result = null;
	try{
		sql = "select signdesc from SYSCA_SIGN where" +
				" signNo = " + Common.sqlChar(signNo);
		
		db = new Database();
		rs = db.querySQL(sql);
		if(rs.next()){				
			result = rs.getString("signdesc");
		}
		rs.close();
	}catch(Exception e){
		System.out.println("getSignDescName Exception => " + e.getMessage());
	}finally{
		db.closeAll();
	}
	return result;
}
}