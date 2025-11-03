/*
*<br>程式目的：有價證券主檔資料維護-股份資料
*<br>程式代號：untvp002f
*<br>程式日期：0940927
*<br>程式作者：cherry
*<br>--------------------------------------------------------
*<br>修改作者　　修改日期　　　修改目的
*<br>--------------------------------------------------------
*/

package unt.vp;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import TDlib_Simple.com.src.SQLCreator;
import unt.ch.UNTCH001Q;
import unt.ch.UNTCH_Tools;
import util.Common;
import util.Database;

public class UNTVP002F extends UNTCH001Q{


//傳回執行insert前之檢查sql
protected String[][] getInsertCheckSQL(){
	//取得股份次序
	Database db = new Database();
	ResultSet rs;	
	String sql="select case when max(serialNo1) is null then 1 else (max(serialNo1) + 1) end as serialNo1 from UNTVP_Share " +
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
 	checkSQLArray[0][0]=" select count(*) as checkResult from UNTVP_Share where 1=1 " + 
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
	execSQLArray[0] = new SQLCreator()._obtainSQLforInsert("UNTVP_Share", getPKMap(), getRecordMap());	
	return execSQLArray;
}


//傳回update sql
protected String[] getUpdateSQL(){
	String[] execSQLArray = new String[1];	
	execSQLArray[0] = new SQLCreator()._obtainSQLforUpdate("UNTVP_Share", getPKMap(), getRecordMap());	
	return execSQLArray;
}


//傳回delete sql
protected String[] getDeleteSQL(){
	String[] execSQLArray = new String[1];	
	execSQLArray[0] = new SQLCreator()._obtainSQLforDelete("UNTVP_Share", getPKMap(), getRecordMap());	
	return execSQLArray;
}


/**
 * <br>
 * <br>目的：依主鍵查詢單一資料
 * <br>參數：無
 * <br>傳回：傳回本物件
*/

public UNTVP002F  queryOne() throws Exception{
	Database db = new Database();
	UNTVP002F obj = this;
	try {
		String sql=" select a.*, " +
					"(select organsname from SYSCA_ORGAN z where z.organid = a.enterorg) as enterOrgName," +
					"(select codename from SYSCA_CODE z where z.codekindid = 'OWA' and z.codeid = a.ownership) as ownershipName," +
					"(select codename from SYSCA_CODE z where z.codekindid = 'DFK' and z.codeid = a.differenceKind) as differenceKindName," +
					"(select propertyname from SYSPK_PROPERTYCODE z where z.enterorg = a.enterorg and z.propertyno = a.propertyno) as propertyNoName" +
					" from UNTVP_Share a where 1=1 " +
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
			obj.setEnterOrg(rs.getString("EnterOrg"));
			obj.setOwnership(rs.getString("Ownership"));
			obj.setDifferenceKind(rs.getString("DifferenceKind"));
			obj.setPropertyNo(rs.getString("PropertyNo"));
			obj.setSerialNo(rs.getString("SerialNo"));
			obj.setSerialNo1(rs.getString("SerialNo1"));
			obj.setBookAmount(rs.getString("BookAmount"));
			obj.setBookUnitValue(rs.getString("BookUnitValue"));
			obj.setBookValue(rs.getString("BookValue"));
			obj.setProofS(rs.getString("ProofS"));
			obj.setProofE(rs.getString("ProofE"));
			obj.setNotes(rs.getString("Notes"));
			obj.setEditID(rs.getString("EditID"));
			obj.setEditDate(ul._transToROC_Year(rs.getString("EditDate")));
			obj.setEditTime(rs.getString("EditTime"));

			obj.setEnterOrgName(rs.getString("EnterOrgName"));
			obj.setOwnershipName(rs.getString("OwnershipName"));
			obj.setDifferenceKindName(rs.getString("DifferenceKindName"));
			obj.setPropertyNoName(rs.getString("PropertyNoName"));		
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
	UNTVP002F obj = this;
	Database db = new Database();
	
	try {
		String sql=" select a.*"+
					" from UNTVP_Share a where 1=1 "; 
							
				sql+=" and a.enterOrg = " + Common.sqlChar(getEnterOrg()) ;
				sql+=" and a.ownership = " + Common.sqlChar(getOwnership()) ;
				sql+=" and a.propertyNo = " + Common.sqlChar(getPropertyNo()) ;
				sql+=" and a.differenceKind = " + Common.sqlChar(getDifferenceKind()) ;
				sql+=" and a.serialNo = " + Common.sqlChar(getSerialNo()) ;
			
		ResultSet rs = db.querySQL(sql,true);
		processCurrentPageAttribute(rs);			
		if (getTotalRecord() > 0) {
			int count = getRecordStart();
			int end = getRecordEnd();
			do {
				if (count > end)
					break;
			String rowArray[]=new String[10];
			rowArray[0]=rs.getString("enterOrg"); 
			rowArray[1]=rs.getString("ownership");
			rowArray[2]=rs.getString("differenceKind");
			rowArray[3]=rs.getString("propertyNo"); 
			rowArray[4]=rs.getString("serialNo"); 
			rowArray[5]=rs.getString("serialNo1");
			
			rowArray[6]=Common.get(rs.getString("bookAmount"));
			rowArray[7]=Common.get(rs.getString("bookUnitValue"));
			rowArray[8]=Common.get(rs.getString("bookValue"));
			rowArray[9]=Common.get(rs.getString("proofS")) + "－" + Common.get(rs.getString("proofE"));	
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
		
		map.put("EnterOrg", getEnterOrg());
		map.put("Ownership", getOwnership());
		map.put("DifferenceKind", getDifferenceKind());
		map.put("PropertyNo", getPropertyNo());
		map.put("SerialNo", getSerialNo());
		map.put("SerialNo1", getSerialNo1());
		map.put("BookAmount", getBookAmount());
		map.put("BookUnitValue", getBookUnitValue());
		map.put("BookValue", getBookValue());
		map.put("ProofS", getProofS());
		map.put("ProofE", getProofE());
		map.put("Notes", getNotes());
		map.put("EditID", getEditID());
		map.put("EditDate", new UNTCH_Tools()._transToCE_Year(getEditDate()));
		map.put("EditTime", getEditTime());
		
		return map;
	}


private String enterOrg;
private String ownership;
private String differenceKind;
private String propertyNo;
private String serialNo;
private String serialNo1;
private String bookAmount;
private String bookUnitValue;
private String bookValue;
private String proofS;
private String proofE;
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
public String getBookAmount() {return checkGet(bookAmount);}
public void setBookAmount(String bookAmount) {this.bookAmount = checkSet(bookAmount);}
public String getBookUnitValue() {return checkGet(bookUnitValue);}
public void setBookUnitValue(String bookUnitValue) {this.bookUnitValue = checkSet(bookUnitValue);}
public String getBookValue() {return checkGet(bookValue);}
public void setBookValue(String bookValue) {this.bookValue = checkSet(bookValue);}
public String getProofS() {return checkGet(proofS);}
public void setProofS(String proofS) {this.proofS = checkSet(proofS);}
public String getProofE() {return checkGet(proofE);}
public void setProofE(String proofE) {this.proofE = checkSet(proofE);}
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
public String getPropertyNoName() {	return checkGet(propertyNoName);}
public void setPropertyNoName(String propertyNoName) {this.propertyNoName = checkSet(propertyNoName);}

}


