/*
*<br>程式目的：土地主檔資料維護－管理資料
*<br>程式代號：untla003f
*<br>程式日期：0940908
*<br>程式作者：novia
*<br>--------------------------------------------------------
*<br>修改作者　　修改日期　　　修改目的
*<br>clive.chang 0941219	Debug & Modify for Testing and autual running..
*<br>--------------------------------------------------------
*/

package unt.la;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import util.Common;
import unt.ch.UNTCH_Tools;
import util.Database;
import util.Datetime;
import TDlib_Simple.com.src.SQLCreator;
import TDlib_Simple.tools.src.StringTools;

public class UNTLA003F extends UNTLA001Q{

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
		UNTCH_Tools ut = new UNTCH_Tools();
		
		map.put("enterOrg", getEnterOrg());
		map.put("ownership", getOwnership());
		map.put("differenceKind", getDifferenceKind());
		map.put("propertyNo", getPropertyNo());
		map.put("serialNo", getSerialNo());
		map.put("serialNo1", getSerialNo1());
		map.put("useUnit", getUseUnit());
		map.put("useUnit1", getUseUnit1());
		map.put("useRelation", getUseRelation());
		map.put("useDateS", ut._transToCE_Year(getUseDateS()));
		map.put("useDateE", ut._transToCE_Year(getUseDateE()));
		map.put("useArea", getUseArea());
		map.put("notes1", getNotes1());
		map.put("notes", getNotes());
		map.put("editID", getEditID());	
		map.put("editDate", ut._transToCE_Year(getEditDate()));
		map.put("editTime", getEditTime());
		
		return map;
	}


//傳回執行insert前之檢查sql
protected String[][] getInsertCheckSQL(){
	
	Database db = new Database();
	try {
		//取得管理次序
		String sql="select (max(serialNo1) + 1) as serialNo1 from UNTLA_Manage " +
			" where enterOrg = " + Common.sqlChar(enterOrg) + 
			" and ownership = " + Common.sqlChar(ownership) + 
			" and propertyNo = " + Common.sqlChar(propertyNo) + 
			" and serialNo = " + Common.sqlChar(serialNo) +
			" and differenceKind = " + Common.sqlChar(differenceKind) + 
			"";		

		ResultSet rs = db.querySQL(sql);
		if (rs.next()){
			if("".equals(checkGet(rs.getString("serialNo1")))){
				setSerialNo1("1");
			}else{
				setSerialNo1(rs.getString("serialNo1"));	
			}
		    
		}else{
			setSerialNo1("1");
		}
		rs.getStatement().close();
		
	} catch (Exception e) {
		e.printStackTrace();
	} finally {
		db.closeAll();
	} 	
	String[][] checkSQLArray = new String[1][4];
 	checkSQLArray[0][0]=" select count(*) as checkResult from UNTLA_Manage where 1=1 " + 
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
	execSQLArray[0] = new SQLCreator()._obtainSQLforInsert("UNTLA_MANAGE", getPKMap(), getRecordMap());
	return execSQLArray;
}

public void insert2(){
	Database db = new Database();
	
	try {
		if (!beforeExecCheck(getInsertCheckSQL())){
			setStateInsertError();
		}else{
			if(checkArea(this.getUseArea())){
				setErrorMsg("該筆土地使用面積總合＞該筆土地總面積");
				setStateInsertError();
			}else{
				db.excuteSQL(getInsertSQL());
				setStateInsertSuccess();
				setErrorMsg("新增完成");	
			}	
		}

	} catch (Exception e) {
		e.printStackTrace();
	} finally {
		db.closeAll();
	}			
}



//傳回update sql
protected String[] getUpdateSQL(){
	String[] execSQLArray = new String[1];
	execSQLArray[0] = new SQLCreator()._obtainSQLforUpdate("UNTLA_MANAGE", getPKMap(), getRecordMap());
	return execSQLArray;
}

public void update2(){
	Database db = new Database();
	try {
		if (!beforeExecCheck(getUpdateCheckSQL())){
			setStateUpdateError();
		}else{			
			if(checkArea(this.getUseArea())){		   	
				setErrorMsg("該筆土地使用面積總合＞該筆土地總面積");
				setStateUpdateError();
			}else{
				db.excuteSQL(getUpdateSQL());
				setStateUpdateSuccess();
				setErrorMsg("修改完成");	
			}			
		}

	} catch (Exception e) {
		e.printStackTrace();
	} finally {
		db.closeAll();
	}			
}	


//傳回delete sql
protected String[] getDeleteSQL(){
	String[] execSQLArray = new String[2];
	
	execSQLArray[0]= " delete UNTLA_Manage where 1=1 " +
		" and enterOrg = " + Common.sqlChar(enterOrg) +
		" and ownership = " + Common.sqlChar(ownership) +
		" and propertyNo = " + Common.sqlChar(propertyNo) +
		" and serialNo = " + Common.sqlChar(serialNo) +
		" and serialNo1 = " + Common.sqlChar(serialNo1) +
		" and differenceKind = " + Common.sqlChar(differenceKind) +
		"";	

	execSQLArray[1]= " delete UNTLA_Person where 1=1 " +
		" and enterOrg = " + Common.sqlChar(enterOrg) +
		" and ownership = " + Common.sqlChar(ownership) +
		" and propertyNo = " + Common.sqlChar(propertyNo) +
		" and serialNo = " + Common.sqlChar(serialNo) +
		" and serialNo1 = " + Common.sqlChar(serialNo1) +
		"";
	
	return execSQLArray;
}


/**
 * <br>
 * <br>目的：依主鍵查詢單一資料
 * <br>參數：無
 * <br>傳回：傳回本物件
*/

public UNTLA003F  queryOne() throws Exception{
	Database db = new Database();
	UNTLA003F obj = this;
	try {
		String sql=" select b.organSName as enterOrgName,c.propertyName,a.enterOrg, a.ownership, a.propertyNo, " +
					" a.serialNo, a.serialNo1, a.useUnit, " +
					" (select d.organSName from SYSCA_Organ d where a.useUnit = d.organID)as useUnitName, " +
					" a.useUnit1, a.useRelation, " +
					" a.useDateS, a.useDateE, a.useArea, a.notes1, " +
					" a.notes, " +
					" a.editID, a.editDate, a.editTime, a.differenceKind "+
					" from UNTLA_Manage a, SYSCA_Organ b, SYSPK_PropertyCode c" +
					" where 1=1 " +
					" and a.enterOrg = " + Common.sqlChar(enterOrg) +
					" and a.ownership = " + Common.sqlChar(ownership) +
					" and a.propertyNo = " + Common.sqlChar(propertyNo) +
					" and a.serialNo = " + Common.sqlChar(serialNo) +
					" and a.serialNo1 = " + Common.sqlChar(serialNo1) +
					" and a.differenceKind = " + Common.sqlChar(differenceKind) +					
					" and b.organID=a.enterOrg " +
					" and a.propertyNo = c.propertyNo" +			
					"";
		ResultSet rs = db.querySQL(sql);
		UNTCH_Tools ut = new UNTCH_Tools();
		if (rs.next()){
			obj.setEnterOrg(rs.getString("enterOrg"));
			obj.setEnterOrgName(rs.getString("enterOrgName"));
			obj.setOwnership(rs.getString("ownership"));
			obj.setPropertyNo(rs.getString("propertyNo"));
			obj.setPropertyNoName(rs.getString("propertyName"));
			obj.setSerialNo(rs.getString("serialNo"));
			obj.setSerialNo1(rs.getString("serialNo1"));
			obj.setUseUnit(rs.getString("useUnit"));
			obj.setUseUnitName(rs.getString("useUnitName"));
			obj.setUseUnit1(rs.getString("useUnit1"));
			obj.setUseRelation(rs.getString("useRelation"));
			obj.setUseDateS(ut._transToROC_Year(rs.getString("useDateS")));
			obj.setUseDateE(ut._transToROC_Year(rs.getString("useDateE")));
			obj.setUseArea(rs.getString("useArea"));
			obj.setNotes1(rs.getString("notes1"));
			obj.setNotes(rs.getString("notes"));
			obj.setEditID(rs.getString("editID"));
			obj.setEditDate(ut._transToROC_Year(rs.getString("editDate")));
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
		String sql=" select a.differenceKind," +
			" (select organSName from SYSCA_Organ b where a.useUnit = b.organID) as organSName," +
			" a.enterOrg, a.ownership, a.propertyNo, a.serialNo, a.serialNo1, a.useUnit, a.useUnit1,c.codeName useRelation, a.useDateS, a.useDateE, a.useArea "+
			" from UNTLA_Manage a left join SYSCA_Code c on c.codeKindID='URE' and a.useRelation = c.codeID"+
			" where 1=1 "+
			"   and a.enterOrg = " + Common.sqlChar(enterOrg) +
			"   and a.ownership = " + Common.sqlChar(ownership) +
			"   and a.differenceKind = " + Common.sqlChar(differenceKind) +
			"   and a.propertyNo = " + Common.sqlChar(propertyNo) +
			"   and a.serialNo = " + Common.sqlChar(serialNo) ;

		ResultSet rs = db.querySQL(sql+" order by serialNo1",true);
		UNTCH_Tools ul = new UNTCH_Tools();
		StringTools st = new StringTools();
		processCurrentPageAttribute(rs);			
		if (getTotalRecord() > 0) {
			int count = getRecordStart();
			int end = getRecordEnd();
			do {
				if (count > end)
					break;
			String rowArray[]=new String[14];
			rowArray[0]=rs.getString("enterOrg"); 
			rowArray[1]=rs.getString("ownership"); 
			rowArray[2]=rs.getString("propertyNo"); 
			rowArray[3]=rs.getString("serialNo"); 
			rowArray[4]=rs.getString("serialNo1"); 
			rowArray[5]=rs.getString("organSName");	
			rowArray[6]=rs.getString("useUnit"); 
			rowArray[7]=rs.getString("useUnit1"); 
			rowArray[8]=rs.getString("useRelation"); 
			rowArray[9]=ul._transToROC_Year(rs.getString("useDateS")); 
			rowArray[10]=ul._transToROC_Year(rs.getString("useDateE"));
			rowArray[11]=st._getDotFormat(rs.getString("useArea"), 2); 
			rowArray[12]=""; 
			rowArray[13]=rs.getString("differenceKind"); 
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


public boolean checkArea(String nowUseArea){
	Database db = new Database();
	String checkSQL = "" ;
	String checkSQL2 = "" ;
	double useArea = 0 ;
	double totalArea = 0 ;
	double Results = 0 ;
	boolean checkarea = false ;
	
	if("1".equals(checkIsDefault())){	
		checkSQL =" select (case sum(useArea) when null then 0 else sum(useArea) end) + " + nowUseArea +
				  " as useArea from UNTLA_Manage a where 1=1" + 
				  " and enterOrg = " + Common.sqlChar(enterOrg) +
				  " and ownership = " + Common.sqlChar(ownership) + 
				  " and propertyNo = " + Common.sqlChar(propertyNo) +
				  " and serialNo =" + Common.sqlChar(serialNo)+
				  " and differenceKind =" + Common.sqlChar(differenceKind)+ 
				  " and serialNo1 !=" + Common.sqlChar(serialNo1)+
				  " and (case useDateS when null then '0000000' when '' then '0000000' else useDateS end) <= " + Common.sqlChar(new UNTCH_Tools()._transToCE_Year(Datetime.getYYYMMDD())) + 
				  " and (case useDateE when null then '9999999' when '' then '9999999' else useDateE end) >= " + Common.sqlChar(new UNTCH_Tools()._transToCE_Year(Datetime.getYYYMMDD())) +
				  "";
				
		try {
			//System.out.println("checkSQL--->"+checkSQL);
			ResultSet rs = db.querySQL(checkSQL);
			if (rs.next()){
				useArea = Double.parseDouble(rs.getString("useArea"));	
			}
		} catch (Exception e) {
			
		} finally {
			db.closeAll();
		}
		
		checkSQL2 =" select (case area when null then 0 else area end) as area from UNTLA_Land where 1=1" + "\n" +
		  " and enterOrg = " + Common.sqlChar(enterOrg) + "\n" +
		  " and ownership = " + Common.sqlChar(ownership) + "\n" +
		  " and propertyNo = " + Common.sqlChar(propertyNo) + "\n" +
		  " and serialNo =" + Common.sqlChar(serialNo) + "\n" +
		  " and differenceKind =" + Common.sqlChar(differenceKind);
	
		try {
			//System.out.println("checkSQL2--->"+checkSQL2);
			ResultSet rs2 = db.querySQL(checkSQL2);
			if (rs2.next()){
				totalArea=Double.parseDouble(rs2.getString("area"));
			}
		} catch (Exception e) {
			
		} finally {
			db.closeAll();
		}
		Results = totalArea - useArea ;	
		if(Results < 0){
			checkarea = true ;
			//setErrorMsg("該筆土地使用面積＞該筆土地總面積");	
		}
	}
	return checkarea ;
}

public String getHoldAreaFromUntla_Land(){
	UNTLA054_ServerBean serBean = new UNTLA054_ServerBean();
	String returnStr;
	
	String sql = "select holdarea from Untla_Land" + 
				" where 1=1" +
				" and enterOrg = " + Common.sqlChar(this.getEnterOrg()) +
				" and ownership = " + Common.sqlChar(this.getOwnership()) +
				" and propertyNo = " + Common.sqlChar(this.getPropertyNo()) +
				" and serialNo = " + Common.sqlChar(this.getSerialNo()) +
				" and differenceKind = " + Common.sqlChar(this.getDifferenceKind()) ;
	
	returnStr = serBean.getNameData("HOLDAREA", sql);
	return returnStr;
	
}

private String checkIsDefault(){
	String str = "";
	int checkUseDateS = 0;
	int checkUseDateE = 0;
	
	if("".equals(Common.get(useDateS))){	checkUseDateS = 0;
	}else{									checkUseDateS = Integer.parseInt(useDateS);
	}
	if("".equals(Common.get(useDateE))){	checkUseDateE = 9999999;
	}else{									checkUseDateE = Integer.parseInt(useDateE);
	}
	
	int today = Integer.parseInt(Datetime.getYYYMMDD());
	
	if(checkUseDateS <= today && checkUseDateE >= today){
		str = "1";		//現存
	}else if(checkUseDateE >= today){		
		str = "1";		//現存		
	}else{
		str = "0";		//歷史
	}
	return str;
}

}



