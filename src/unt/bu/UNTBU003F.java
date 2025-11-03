/*
*<br>程式目的：建物主檔資料維護--管理資料
*<br>程式代號：untbu003f
*<br>程式日期：0940918
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

import util.Common;
import unt.ch.UNTCH_Tools;
import util.Database;
import TDlib_Simple.com.src.SQLCreator;
import TDlib_Simple.tools.src.StringTools;

public class UNTBU003F extends UNTBU001Q{
	
	private String enterOrg;
	private String enterOrgName;
	private String ownership;
	private String differenceKind;
	private String propertyNo;
	private String propertyNoName;
	private String serialNo;
	private String serialNo1;
	private String useUnit;
	private String useUnitName;
	private String useUnit1;
	private String useRelation;
	private String useDateS;
	private String useDateE;
	private String useArea;
	private String notes1;
	private String notes;
	private String editID;
	private String editDate;
	private String editTime;

	public String getEnterOrg() {return checkGet(enterOrg);}
	public void setEnterOrg(String enterOrg) {this.enterOrg = checkSet(enterOrg);}
	public String getEnterOrgName() {return checkGet(enterOrgName);}
	public void setEnterOrgName(String enterOrgName) {this.enterOrgName = checkSet(enterOrgName);}
	public String getOwnership() {return checkGet(ownership);}
	public void setOwnership(String ownership) {this.ownership = checkSet(ownership);}
	public String getDifferenceKind() {return checkGet(differenceKind);}
	public void setDifferenceKind(String differenceKind) {this.differenceKind = checkSet(differenceKind);}
	public String getPropertyNo() {return checkGet(propertyNo);}
	public void setPropertyNo(String propertyNo) {this.propertyNo = checkSet(propertyNo);}
	public String getPropertyNoName() {return checkGet(propertyNoName);}
	public void setPropertyNoName(String propertyNoName) {this.propertyNoName = checkSet(propertyNoName);}
	public String getSerialNo() {return checkGet(serialNo);}
	public void setSerialNo(String serialNo) {this.serialNo = checkSet(serialNo);}
	public String getSerialNo1() {return checkGet(serialNo1);}
	public void setSerialNo1(String serialNo1) {this.serialNo1 = checkSet(serialNo1);}
	public String getUseUnit() {return checkGet(useUnit);}
	public void setUseUnit(String useUnit) {this.useUnit = checkSet(useUnit);}
	public String getUseUnitName() {return checkGet(useUnitName);}
	public void setUseUnitName(String useUnitName) {this.useUnitName = checkSet(useUnitName);}
	public String getUseUnit1() {return checkGet(useUnit1);}
	public void setUseUnit1(String useUnit1) {this.useUnit1 = checkSet(useUnit1);}
	public String getUseRelation() {return checkGet(useRelation);}
	public void setUseRelation(String useRelation) {this.useRelation = checkSet(useRelation);}
	public String getUseDateS() {return checkGet(useDateS);}
	public void setUseDateS(String useDateS) {this.useDateS = checkSet(useDateS);}
	public String getUseDateE() {return checkGet(useDateE);}
	public void setUseDateE(String useDateE) {this.useDateE = checkSet(useDateE);}
	public String getUseArea() {return checkGet(useArea);}
	public void setUseArea(String useArea) {this.useArea = checkSet(useArea);}
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
		map.put("useUnit", getUseUnit());
		map.put("useUnit1", getUseUnit1());
		map.put("useRelation", getUseRelation());
		map.put("useDateS", new UNTCH_Tools()._transToCE_Year(getUseDateS()));
		map.put("useDateE", new UNTCH_Tools()._transToCE_Year(getUseDateE()));
		map.put("useArea", getUseArea());
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
	String sql="select max(serialNo1)+1 as serialNo1 from UNTBU_Manage " +
		" where enterOrg = " + Common.sqlChar(enterOrg) +
		" and ownership = " + Common.sqlChar(ownership) +
		" and propertyNo = " + Common.sqlChar(propertyNo)+
		" and serialNo = " + Common.sqlChar(serialNo) +
		" and differenceKind = " + Common.sqlChar(differenceKind);	
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
 	checkSQLArray[0][0]=" select count(*) as checkResult from UNTBU_Manage where 1=1 " + 
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
	
	execSQLArray[0] = new SQLCreator()._obtainSQLforInsert("UNTBU_Manage", getPKMap(), getRecordMap());
	
	return execSQLArray;
}


//傳回update sql
protected String[] getUpdateSQL(){
	String[] execSQLArray = new String[1];

	execSQLArray[0] = new SQLCreator()._obtainSQLforUpdate("UNTBU_Manage", getPKMap(), getRecordMap());
	
	return execSQLArray;
}


//傳回delete sql
protected String[] getDeleteSQL(){
	String[] execSQLArray = new String[1];
	
	execSQLArray[0]= " delete UNTBU_Manage where 1=1 " +
		" and enterOrg = " + Common.sqlChar(enterOrg) +
		" and ownership = " + Common.sqlChar(ownership) +
		" and propertyNo = " + Common.sqlChar(propertyNo) +
		" and serialNo = " + Common.sqlChar(serialNo) +
		" and serialNo1 = " + Common.sqlChar(serialNo1) +
		" and differenceKind = " + Common.sqlChar(differenceKind) +
		"";	
	
	return execSQLArray;
}


/**
 * <br>
 * <br>目的：依主鍵查詢單一資料
 * <br>參數：無
 * <br>傳回：傳回本物件
*/

public UNTBU003F  queryOne() throws Exception{
	Database db = new Database();
	UNTBU003F obj = this;
	try {
		String sql=" select a.*, " +
			" b.organSName as useUnitName " +
			" from UNTBU_Manage a left join SYSCA_Organ b on a.useUnit = b.organID where 1=1 " +
			" and a.enterOrg = " + Common.sqlChar(enterOrg) +
			" and a.ownership = " + Common.sqlChar(ownership) +
			" and a.propertyNo = " + Common.sqlChar(propertyNo) +
			" and a.serialNo = " + Common.sqlChar(serialNo) +
			" and a.serialNo1 = " + Common.sqlChar(serialNo1) +
			" and a.differenceKind = " + Common.sqlChar(differenceKind) +
			"";

		StringTools st = new StringTools();
		ResultSet rs = db.querySQL(sql);
		if (rs.next()){
			obj.setEnterOrg(rs.getString("enterOrg"));
			obj.setOwnership(rs.getString("ownership"));
			obj.setPropertyNo(rs.getString("propertyNo"));
			obj.setSerialNo(rs.getString("serialNo"));
			obj.setSerialNo1(rs.getString("serialNo1"));
			obj.setUseUnit(rs.getString("useUnit"));
			obj.setUseUnitName(rs.getString("useUnitName"));			
			obj.setUseUnit1(rs.getString("useUnit1"));
			obj.setUseRelation(rs.getString("useRelation"));
			obj.setUseDateS(new UNTCH_Tools()._transToROC_Year(rs.getString("useDateS")));
			obj.setUseDateE(new UNTCH_Tools()._transToROC_Year(rs.getString("useDateE")));
			obj.setUseArea(st._getDotFormat(rs.getString("useArea"),2));
			obj.setNotes1(rs.getString("notes1"));
			obj.setNotes(rs.getString("Notes"));
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
		String sql=" select a.enterOrg, b.organSName as useUnitName, a.ownership, " +
			" (case a.ownership when '2' then '國有' else '市有' end) as ownershipName, " +
			" a.propertyNo, a.serialNo, a.serialNo1, a.useUnit, a.useUnit1, a.useRelation, c.codeName as useRelationName, a.useDateS, a.useDateE, a.useArea "+
			" from UNTBU_Manage a left join SYSCA_Organ b on a.useUnit=b.organID, SYSCA_Code c where 1=1 " +
			" and a.useRelation=c.codeID and c.codeKindID='URE' " +
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
				sql+=" and a.differenceKind = " + Common.sqlChar(getDifferenceKind()) ;
			
		StringTools st = new StringTools();
		ResultSet rs = db.querySQL(sql + " order by serialNo1",true);
		processCurrentPageAttribute(rs);			
		if (getTotalRecord() > 0) {
			int count = getRecordStart();
			int end = getRecordEnd();
			do {
				if (count > end)
					break;
			String rowArray[]=new String[11];
			rowArray[0]=rs.getString("enterOrg"); 
			rowArray[1]=rs.getString("ownership"); 
			rowArray[2]=rs.getString("propertyNo"); 
			rowArray[3]=rs.getString("serialNo"); 
			rowArray[4]=rs.getString("serialNo1"); 
			rowArray[5]=rs.getString("useUnitName"); 
			rowArray[6]=rs.getString("useUnit1"); 
			rowArray[7]=rs.getString("useRelationName"); 
			rowArray[8]=new UNTCH_Tools()._transToROC_Year(rs.getString("useDateS")); 
			rowArray[9]=new UNTCH_Tools()._transToROC_Year(rs.getString("useDateE")); 
			rowArray[10]=st._getDotFormat(rs.getString("useArea"),2); 
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