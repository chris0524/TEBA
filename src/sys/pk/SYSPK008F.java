
/*
*<br>程式目的：財產編號維護
*<br>程式代號：syspk007f
*<br>程式日期：0940623
*<br>程式作者：griffin
*<br>--------------------------------------------------------
*<br>修改作者　　修改日期　　　修改目的
*<br>--------------------------------------------------------
*/

package sys.pk;

import java.sql.ResultSet;
import java.util.ArrayList;
import util.*;

public class SYSPK008F extends SuperBean{

String propertyType;
String propertyNo;
String propertyName;
String propertyUnit;
String material;
String limitYear;
String memo;


String q_propertyNo;
String q_propertyName;

public String getPropertyType(){ return checkGet(propertyType); }
public void setPropertyType(String s){ propertyType=checkSet(s); }
public String getPropertyNo(){ return checkGet(propertyNo); }
public void setPropertyNo(String s){ propertyNo=checkSet(s); }
public String getPropertyName(){ return checkGet(propertyName); }
public void setPropertyName(String s){ propertyName=checkSet(s); }
public String getPropertyUnit(){ return checkGet(propertyUnit); }
public void setPropertyUnit(String s){ propertyUnit=checkSet(s); }
public String getMaterial(){ return checkGet(material); }
public void setMaterial(String s){ material=checkSet(s); }
public String getLimitYear(){ return checkGet(limitYear); }
public void setLimitYear(String s){ limitYear=checkSet(s); }
public String getMemo(){ return checkGet(memo); }
public void setMemo(String s){ memo=checkSet(s); }


public String getQ_propertyNo(){ return checkGet(q_propertyNo); }
public void setQ_propertyNo(String s){ q_propertyNo=checkSet(s); }
public String getQ_propertyName(){ return checkGet(q_propertyName); }
public void setQ_propertyName(String s){ q_propertyName=checkSet(s); }


//傳回執行insert前之檢查sql
protected String[][] getInsertCheckSQL(){
	String[][] checkSQLArray = new String[1][4];
 	checkSQLArray[0][0]=" select count(*) as checkResult from SYSPK_PropertyCode where 1=1 " + 
		" and propertyNo = " + Common.sqlChar(propertyNo) + 
		"";
	checkSQLArray[0][1]=">";
	checkSQLArray[0][2]="0";
	checkSQLArray[0][3]="該筆資料己重複，請重新輸入！";
	return checkSQLArray;
}


//傳回insert sql
protected String[] getInsertSQL(){
	String[] execSQLArray = new String[1];
	execSQLArray[0]=" insert into SYSPK_PropertyCode(" +
			" enterOrg,"+
			" propertyNo,"+
			" propertyType,"+
			" propertyName,"+
			" propertyUnit,"+
			" material,"+
			" limitYear,"+
			" memo,"+
			" editID,"+
			" editDate,"+
			" editTime"+
		" )Values(" +
			Common.sqlChar("000000000A") + "," +
			Common.sqlChar(propertyNo) + "," +
			Common.sqlChar(propertyType) + "," +
			Common.sqlChar(propertyName) + "," +
			Common.sqlChar(propertyUnit) + "," +
			Common.sqlChar(material) + "," +
			Common.sqlChar(limitYear) + "," +
			Common.sqlChar(memo) + "," +
			Common.sqlChar(getEditID()) + "," +
			Common.sqlChar(getEditDate()) + "," +
			Common.sqlChar(getEditTime()) + ")" ;
	return execSQLArray;
}


//傳回update sql
protected String[] getUpdateSQL(){
	String[] execSQLArray = new String[1];
	execSQLArray[0]=" update SYSPK_PropertyCode set " +
			" propertyType = " + Common.sqlChar(propertyType) + "," +
			" propertyName = " + Common.sqlChar(propertyName) + "," +
			" propertyUnit = " + Common.sqlChar(propertyUnit) + "," +
			" material = " + Common.sqlChar(material) + "," +
			" limitYear = " + Common.sqlChar(limitYear) + "," +
			" memo = " + Common.sqlChar(memo) + "," +
			" editID = " + Common.sqlChar(getEditID()) + "," +
			" editDate = " + Common.sqlChar(getEditDate()) + "," +
			" editTime = " + Common.sqlChar(getEditTime()) +  
		" where 1=1 " + 
			" and propertyNo = " + Common.sqlChar(propertyNo) +
			"";
	return execSQLArray;
}


//傳回delete sql
protected String[] getDeleteSQL(){
	String[] execSQLArray = new String[1];
	execSQLArray[0]=" update SYSPK_PropertyCode set propertyType='0' where 1=1 " +
			" and propertyNo = " + Common.sqlChar(propertyNo) +
			"";
	return execSQLArray;
}

/**
 * <br>
 * <br>目的：依主鍵查詢單一資料
 * <br>參數：無
 * <br>傳回：傳回本物件
*/

public SYSPK008F  queryOne() throws Exception{
	Database db = new Database();
	SYSPK008F obj = this;
	try {
		String sql=" select propertyNo, propertyType, propertyName, propertyUnit, material, limitYear, memo, editID, editDate, editTime "+
			" from SYSPK_PropertyCode where 1=1 " +
			" and propertyNo = " + Common.sqlChar(propertyNo) +
			"";
		ResultSet rs = db.querySQL(sql);
		if (rs.next()){
			obj.setPropertyNo(rs.getString("propertyNo"));
			obj.setPropertyType(rs.getString("propertyType"));
			obj.setPropertyName(rs.getString("propertyName"));
			obj.setPropertyUnit(rs.getString("propertyUnit"));
			obj.setMaterial(rs.getString("material"));
			obj.setLimitYear(rs.getString("limitYear"));
			obj.setMemo(rs.getString("memo"));
			obj.setEditID(rs.getString("editID"));
			obj.setEditDate(rs.getString("editDate"));
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
	Database db = new Database();
	ArrayList objList=new ArrayList();
	int counter=0;
	
	try {
		String sql=" select a.propertyNo, a.propertyName, a.material, a.propertyUnit, a.limitYear "+
			" from SYSPK_PropertyCode a where 1=1 "; 
			if (!"".equals(getQ_propertyNo()))
				sql+=" and a.propertyNo   like '" + getQ_propertyNo() + "%'" ;
			if (!"".equals(getQ_propertyName()))
				sql+=" and a.propertyName like '%" + getQ_propertyName() + "%'" ;
		ResultSet rs = db.querySQL(sql);
		System.out.println("2=>"+Datetime.getHHMMSS());
		while (rs.next()){
			counter++;
			String rowArray[]=new String[5];
			rowArray[0]=rs.getString("propertyNo"); 
			rowArray[1]=rs.getString("propertyName"); 
			rowArray[2]=rs.getString("propertyUnit"); 
			rowArray[3]=rs.getString("material"); 
			rowArray[4]=rs.getString("limitYear"); 
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


