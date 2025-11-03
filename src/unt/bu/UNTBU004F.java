/*
*<br>程式目的：建物主檔資料維護--樓層資料
*<br>程式代號：untbu004f
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

import util.Common;
import unt.ch.UNTCH_Tools;
import util.Database;
import TDlib_Simple.com.src.SQLCreator;
import TDlib_Simple.tools.src.StringTools;

public class UNTBU004F extends UNTBU001Q{

	private String enterOrg;
	private String enterOrgName;
	private String ownership;
	private String differenceKind;
	private String propertyNo;
	private String propertyNoName;
	private String serialNo;
	private String serialNo1;
	private String floor;
	private String purpose;
	private String area;
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
	public String getFloor() {return checkGet(floor);}
	public void setFloor(String floor) {this.floor = checkSet(floor);}
	public String getPurpose() {return checkGet(purpose);}
	public void setPurpose(String purpose) {this.purpose = checkSet(purpose);}
	public String getArea() {return checkGet(area);}
	public void setArea(String area) {this.area = checkSet(area);}
	public String getNotes() {return checkGet(notes);}
	public void setNotes(String notes) {this.notes = checkSet(notes);}
	public String getEditID() {return checkGet(editID);}
	public void setEditID(String editID) {this.editID = checkSet(editID);}
	public String getEditDate() {return checkGet(editDate);}
	public void setEditDate(String editDate) {this.editDate = checkSet(editDate);}
	public String getEditTime() {return checkGet(editTime);}
	public void setEditTime(String editTime) {this.editTime = checkSet(editTime);}
	
	private String batchInsertFlag;
	public String getBatchInsertFlag() {return checkGet(batchInsertFlag);}
	public void setBatchInsertFlag(String batchInsertFlag) {this.batchInsertFlag = checkSet(batchInsertFlag);}
	
	
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
		map.put("floor", getFloor());
		map.put("purpose", getPurpose());
		map.put("area", getArea());
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
	String sql="select max(serialNo1)+1 as serialNo1 from UNTBU_Floor " +
		" where enterOrg = " + Common.sqlChar(enterOrg) +
		" and ownership = " + Common.sqlChar(ownership) +
		" and propertyNo = " + Common.sqlChar(propertyNo)+
		" and serialNo = " + Common.sqlChar(serialNo)+
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
 	checkSQLArray[0][0]=" select count(*) as checkResult from UNTBU_Floor where 1=1 " + 
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
	
	execSQLArray[0] = new SQLCreator()._obtainSQLforInsert("UNTBU_Floor", getPKMap(), getRecordMap());
	
	return execSQLArray;
}


//傳回update sql
protected String[] getUpdateSQL(){
	String[] execSQLArray = new String[1];
	
	execSQLArray[0] = new SQLCreator()._obtainSQLforUpdate("UNTBU_Floor", getPKMap(), getRecordMap());
	
	return execSQLArray;
}


//傳回delete sql
protected String[] getDeleteSQL(){
	String[] execSQLArray = new String[1];
	
	execSQLArray[0] = new SQLCreator()._obtainSQLforDelete("UNTBU_Floor", getPKMap(), getRecordMap());

	return execSQLArray;
}


/**
 * <br>
 * <br>目的：依主鍵查詢單一資料
 * <br>參數：無
 * <br>傳回：傳回本物件
*/

public UNTBU004F  queryOne() throws Exception{
	Database db = new Database();
	UNTBU004F obj = this;
	try {
		String sql=" select a.enterOrg, a.ownership, a.propertyNo, a.serialNo, a.serialNo1, a.floor, a.purpose, a.area, a.notes, a.editID, a.editDate, a.editTime, a.differenceKind  "+
			" from UNTBU_Floor a where 1=1 " +
			" and a.enterOrg = " + Common.sqlChar(enterOrg) +
			" and a.ownership = " + Common.sqlChar(ownership) +
			" and a.propertyNo = " + Common.sqlChar(propertyNo) +
			" and a.serialNo = " + Common.sqlChar(serialNo) +
			" and a.serialNo1 = " + Common.sqlChar(serialNo1) +
			" and differenceKind = " + Common.sqlChar(differenceKind) +
			"";
		StringTools st = new StringTools();
		ResultSet rs = db.querySQL(sql);
		if (rs.next()){
			obj.setEnterOrg(rs.getString("enterOrg"));
			obj.setOwnership(rs.getString("ownership"));
			obj.setPropertyNo(rs.getString("propertyNo"));
			obj.setSerialNo(rs.getString("serialNo"));
			obj.setSerialNo1(rs.getString("serialNo1"));
			obj.setFloor(rs.getString("floor"));
			obj.setPurpose(rs.getString("purpose"));
			obj.setArea(st._getDotFormat(rs.getString("area"),2));
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
		String sql=" select a.enterOrg, a.ownership, a.propertyNo, a.serialNo, a.serialNo1, a.floor, b.codeName as floorName, a.purpose, a.area, a.notes "+
			" from UNTBU_Floor a, SYSCA_Code b where 1=1 " +
			" and a.floor=b.codeID and b.codekindid='FLC' " +
			"";
		if (!"".equals(getEnterOrg()))
			sql+=" and a.enterOrg = " + Common.sqlChar(getEnterOrg()) ;
		if (!"".equals(getOwnership()))
			sql+=" and a.ownership = " + Common.sqlChar(getOwnership()) ;
		if (!"".equals(getPropertyNo()))
			sql+=" and a.propertyNo = " + Common.sqlChar(getPropertyNo()) ;
		if (!"".equals(getSerialNo()))
			sql+=" and a.serialNo = " + Common.sqlChar(getSerialNo()) ;		

		StringTools st = new StringTools();
		ResultSet rs = db.querySQL(sql + " order by serialNo1",true);
		processCurrentPageAttribute(rs);			
		if (getTotalRecord() > 0) {
			int count = getRecordStart();
			int end = getRecordEnd();
			do {
				if (count > end)
					break;
			String rowArray[]=new String[8];
			rowArray[0]=rs.getString("enterOrg"); 
			rowArray[1]=rs.getString("ownership"); 
			rowArray[2]=rs.getString("propertyNo"); 
			rowArray[3]=rs.getString("serialNo"); 
			rowArray[4]=rs.getString("serialNo1"); 
			rowArray[5]=rs.getString("floorName"); 
			rowArray[6]=rs.getString("purpose"); 
			rowArray[7]=Common.areaFormat(rs.getString("area"));  
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
 * <br>目的：將樓層代碼AUC列出，做拍賣批次新增的選項
 * <br>參數：無
 * <br>傳回：傳回String
*/
public String getFloorCodeList() throws Exception{
	Database db = new Database();
	StringBuffer sbHTML = new StringBuffer();
	sbHTML.append("");
	int counter=0;
	try {
		ResultSet rs = db.querySQL("select codeID, codeName, memo from SYSCA_CODE where codeKindID='FLC' order by codeID");
		sbHTML.append("<table class='queryTable'  border='1'>\n");
		sbHTML.append("<tr><td class='TDLable'><input type=checkbox name=toggleAll onclick=\"ToggleAll(this, document.form1, 'strKeys');\"></td>\n");			
		sbHTML.append("<td class='TDLable' align='center'>樓層</td>\n");
		sbHTML.append("<td class='TDLable' align='center'>面積</td>\n");
		sbHTML.append("<td class='TDLable' align='center'>用途</td>\n");
		sbHTML.append("</tr>\n");		
		while (rs.next()) {
			counter++;			
			sbHTML.append("<tr>\n");
			sbHTML.append("<td class='queryTDInput'><input type=\"checkbox\" name=\"strKeys\" value=\"" ).append( Common.checkGet(rs.getString("codeID")) ).append( "\" onClick=\"Toggle(this,document.form1,'strKeys');\" /></td>\n");
			sbHTML.append("<td class='queryTDInput'><input type='text' class='field_P' size='15' maxlength='15' name='name_" ).append( rs.getString("codeID")).append( "' value='" ).append( Common.checkGet(rs.getString("codeName")) ).append( "'></td>\n");
			sbHTML.append("<td class='queryTDInput'><input type='text' class='field' size='7' maxlength='9' name='area_" ).append( rs.getString("codeID")).append( "' value=''></td>\n");
			sbHTML.append("<td class='queryTDInput'><input type='text' class='field' size='20' maxlength='20' name='purpose_" ).append( rs.getString("codeID")).append( "' value='" ).append( Common.checkGet(rs.getString("memo")) ).append( "'></td>\n");		
			sbHTML.append("</tr>\n");			
		}
		sbHTML.append("</table>\n");
	} catch (Exception e) {
		e.printStackTrace();
	} finally {
		db.closeAll();
	}
	return sbHTML.toString();
}


public void insertProcess(String strSQL) {
	Database db = new Database();
	String[] arrSQL = strSQL.split(":;:");
    try {
		if (!beforeExecCheck(getInsertCheckSQL())){			
			setBatchInsertFlag("E");
		} else {
			int s = Integer.parseInt(getSerialNo1());
			for (int i=0; i<arrSQL.length; i++) {
				setSerialNo1(""+s);
				arrSQL[i]=arrSQL[i].replaceAll("KD12377493",serialNo1);
				s++;
			}
			db.excuteSQL(arrSQL);
			setBatchInsertFlag("Y");
			setStateInsertSuccess();
		}
	} catch (Exception e) {
		e.printStackTrace();
	} finally {
		db.closeAll();
	}
}

}


