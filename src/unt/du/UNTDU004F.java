/*
*<br>程式目的：非消耗品型式廠牌資料修正
*<br>程式代號：untdu004f
*<br>程式日期：0951004
*<br>程式作者：sam.hsueh
*<br>--------------------------------------------------------
*<br>修改作者　　修改日期　　　修改目的
*<br>--------------------------------------------------------
*/

package unt.du;

import java.sql.ResultSet;
import java.util.ArrayList;
import util.*;

public class UNTDU004F extends QueryBean{


String enterOrg;
String enterOrgName;
String ownership;
String propertyNo;
String lotNo;
String serialNoS;
String serialNoE;
String oldPropertyNo;
String oldSerialNoS;
String oldSerialNoE;
String propertyName1;
String enterDate;
String buyDate;
String dataState;
String originalAmount;
String originalUnit;
String originalBV;
String bookAmount;
String bookValue;
String nameplate;
String specification;

String q_enterOrg;
String q_enterOrgName;
String q_propertyNoSName;
String q_propertyNoEName;
String q_propertyNoS;
String q_propertyNoE;
String q_propertyNo;
String q_serialNoS;
String q_serialNoE;
String q_oldSerialNoS;
String q_oldSerialNoE;
String q_propertyName1;
String q_nameplate;
String q_specification;

public String getEnterOrg(){ return checkGet(enterOrg); }
public void setEnterOrg(String s){ enterOrg=checkSet(s); }
public String getEnterOrgName(){ return checkGet(enterOrgName); }
public void setEnterOrgName(String s){ enterOrgName=checkSet(s); }
public String getOwnership(){ return checkGet(ownership); }
public void setOwnership(String s){ ownership=checkSet(s); }
public String getPropertyNo(){ return checkGet(propertyNo); }
public void setPropertyNo(String s){ propertyNo=checkSet(s); }
public String getLotNo(){ return checkGet(lotNo); }
public void setLotNo(String s){ lotNo=checkSet(s); }
public String getSerialNoS(){ return checkGet(serialNoS); }
public void setSerialNoS(String s){ serialNoS=checkSet(s); }
public String getSerialNoE(){ return checkGet(serialNoE); }
public void setSerialNoE(String s){ serialNoE=checkSet(s); }
public String getOldPropertyNo(){ return checkGet(oldPropertyNo); }
public void setOldPropertyNo(String s){ oldPropertyNo=checkSet(s); }
public String getOldSerialNoS(){ return checkGet(oldSerialNoS); }
public void setOldSerialNoS(String s){ oldSerialNoS=checkSet(s); }
public String getOldSerialNoE(){ return checkGet(oldSerialNoE); }
public void setOldSerialNoE(String s){ oldSerialNoE=checkSet(s); }
public String getPropertyName1(){ return checkGet(propertyName1); }
public void setPropertyName1(String s){ propertyName1=checkSet(s); }
public String getEnterDate(){ return checkGet(enterDate); }
public void setEnterDate(String s){ enterDate=checkSet(s); }
public String getBuyDate(){ return checkGet(buyDate); }
public void setBuyDate(String s){ buyDate=checkSet(s); }
public String getDataState(){ return checkGet(dataState); }
public void setDataState(String s){ dataState=checkSet(s); }
public String getOriginalAmount(){ return checkGet(originalAmount); }
public void setOriginalAmount(String s){ originalAmount=checkSet(s); }
public String getOriginalUnit(){ return checkGet(originalUnit); }
public void setOriginalUnit(String s){ originalUnit=checkSet(s); }
public String getOriginalBV(){ return checkGet(originalBV); }
public void setOriginalBV(String s){ originalBV=checkSet(s); }
public String getBookAmount(){ return checkGet(bookAmount); }
public void setBookAmount(String s){ bookAmount=checkSet(s); }
public String getBookValue(){ return checkGet(bookValue); }
public void setBookValue(String s){ bookValue=checkSet(s); }
public String getNameplate(){ return checkGet(nameplate); }
public void setNameplate(String s){ nameplate=checkSet(s); }
public String getSpecification(){ return checkGet(specification); }
public void setSpecification(String s){ specification=checkSet(s); }

public String getQ_enterOrg(){ return checkGet(q_enterOrg); }
public void setQ_enterOrg(String s){ q_enterOrg=checkSet(s); }
public String getQ_enterOrgName(){ return checkGet(q_enterOrgName); }
public void setQ_enterOrgName(String s){ q_enterOrgName=checkSet(s); }
public String getQ_propertyNoSName(){ return checkGet(q_propertyNoSName); }
public void setQ_propertyNoSName(String s){ q_propertyNoSName=checkSet(s); }
public String getQ_propertyNoEName(){ return checkGet(q_propertyNoEName); }
public void setQ_propertyNoEName(String s){ q_propertyNoEName=checkSet(s); }
public String getQ_propertyNoS(){ return checkGet(q_propertyNoS); }
public void setQ_propertyNoS(String s){ q_propertyNoS=checkSet(s); }
public String getQ_propertyNoE(){ return checkGet(q_propertyNoE); }
public void setQ_propertyNoE(String s){ q_propertyNoE=checkSet(s); }
public String getQ_propertyNo(){ return checkGet(q_propertyNo); }
public void setQ_propertyNo(String s){ q_propertyNo=checkSet(s); }
public String getQ_serialNoS(){ return checkGet(q_serialNoS); }
public void setQ_serialNoS(String s){ q_serialNoS=checkSet(s); }
public String getQ_serialNoE(){ return checkGet(q_serialNoE); }
public void setQ_serialNoE(String s){ q_serialNoE=checkSet(s); }
public String getQ_oldSerialNoS(){ return checkGet(q_oldSerialNoS); }
public void setQ_oldSerialNoS(String s){ q_oldSerialNoS=checkSet(s); }
public String getQ_oldSerialNoE(){ return checkGet(q_oldSerialNoE); }
public void setQ_oldSerialNoE(String s){ q_oldSerialNoE=checkSet(s); }
public String getQ_propertyName1(){ return checkGet(q_propertyName1); }
public void setQ_propertyName1(String s){ q_propertyName1=checkSet(s); }
public String getQ_nameplate(){ return checkGet(q_nameplate); }
public void setQ_nameplate(String s){ q_nameplate=checkSet(s); }
public String getQ_specification(){ return checkGet(q_specification); }
public void setQ_specification(String s){ q_specification=checkSet(s); }



//傳回update sql
protected String[] getUpdateSQL(){
	String[] execSQLArray = new String[4];
	execSQLArray[0]=" update UNTNE_Nonexp set " +
		" nameplate = " + Common.sqlChar(nameplate) + "," +
		" specification = " + Common.sqlChar(specification) + "," +
		" editID = " + Common.sqlChar(getEditID()) + "," +
		" editDate = " + Common.sqlChar(getEditDate()) + "," +
		" editTime = " + Common.sqlChar(getEditTime()) + 
		" where 1=1 " + 
		" and enterOrg = " + Common.sqlChar(enterOrg) +
		" and ownership = " + Common.sqlChar(ownership) +
		" and propertyNo = " + Common.sqlChar(propertyNo) +
		" and lotNo = " + Common.sqlChar(lotNo) +
		"";

	execSQLArray[1]=" update UNTNE_MoveDetail set " +
		" nameplate = " + Common.sqlChar(nameplate) + "," +
		" specification = " + Common.sqlChar(specification) + "," +
		" editID = " + Common.sqlChar(getEditID()) + "," +
		" editDate = " + Common.sqlChar(getEditDate()) + "," +
		" editTime = " + Common.sqlChar(getEditTime()) + 
		" where 1=1 " + 
		" and enterOrg = " + Common.sqlChar(enterOrg) +
		" and ownership = " + Common.sqlChar(ownership) +
		" and propertyNo = " + Common.sqlChar(propertyNo) +
		" and lotNo = " + Common.sqlChar(lotNo) +
		"";
	
	execSQLArray[2]=" update UNTNE_ReduceDetail set " +
		" nameplate = " + Common.sqlChar(nameplate) + "," +
		" specification = " + Common.sqlChar(specification) + "," +
		" editID = " + Common.sqlChar(getEditID()) + "," +
		" editDate = " + Common.sqlChar(getEditDate()) + "," +
		" editTime = " + Common.sqlChar(getEditTime()) + 
		" where 1=1 " + 
		" and enterOrg = " + Common.sqlChar(enterOrg) +
		" and ownership = " + Common.sqlChar(ownership) +
		" and propertyNo = " + Common.sqlChar(propertyNo) +
		" and lotNo = " + Common.sqlChar(lotNo) +
		"";
	
	execSQLArray[3]=" update UNTNE_DealDetail set " +
		" nameplate = " + Common.sqlChar(nameplate) + "," +
		" specification = " + Common.sqlChar(specification) + "," +
		" editID = " + Common.sqlChar(getEditID()) + "," +
		" editDate = " + Common.sqlChar(getEditDate()) + "," +
		" editTime = " + Common.sqlChar(getEditTime()) + 
		" where 1=1 " + 
		" and enterOrg = " + Common.sqlChar(enterOrg) +
		" and ownership = " + Common.sqlChar(ownership) +
		" and propertyNo = " + Common.sqlChar(propertyNo) +
		" and lotNo = " + Common.sqlChar(lotNo) +
		"";
		

	return execSQLArray;
}



/**
 * <br>
 * <br>目的：依主鍵查詢單一資料
 * <br>參數：無
 * <br>傳回：傳回本物件
*/

public UNTDU004F  queryOne() throws Exception{
	Database db = new Database();
	UNTDU004F obj = this;
	try {
		String sql=" select a.enterOrg, b.organSName, a.ownership, a.propertyNo, a.lotNo, a.serialNoS, a.serialNoE, a.oldPropertyNo, a.oldSerialNoS, a.oldSerialNoE, a.propertyName1, a.enterDate, a.buyDate, a.dataState, a.originalAmount, a.originalUnit, a.originalBV, a.bookAmount, a.bookValue, a.nameplate, a.specification, a.editID, a.editDate, a.editTime  "+
			" from UNTNE_Nonexp a, SYSCA_Organ b where 1=1 " +
			" and a.enterOrg = " + Common.sqlChar(enterOrg) +
			" and a.ownership = " + Common.sqlChar(ownership) +
			" and a.propertyNo = " + Common.sqlChar(propertyNo) +
			" and a.lotNo = " + Common.sqlChar(lotNo) +
			" and b.organID = a.enterOrg" +
			"";	
		ResultSet rs = db.querySQL(sql);
		if (rs.next()){
			obj.setEnterOrgName(rs.getString("organSName"));
			obj.setEnterOrg(rs.getString("enterOrg"));
			obj.setOwnership(rs.getString("ownership"));
			obj.setPropertyNo(rs.getString("propertyNo"));
			obj.setLotNo(rs.getString("lotNo"));
			obj.setSerialNoS(rs.getString("serialNoS"));
			obj.setSerialNoE(rs.getString("serialNoE"));
			obj.setOldPropertyNo(rs.getString("oldPropertyNo"));
			obj.setOldSerialNoS(rs.getString("oldSerialNoS"));
			obj.setOldSerialNoE(rs.getString("oldSerialNoE"));
			obj.setPropertyName1(rs.getString("propertyName1"));
			obj.setEnterDate(rs.getString("enterDate"));
			obj.setBuyDate(rs.getString("buyDate"));
			obj.setDataState(rs.getString("dataState"));
			obj.setOriginalAmount(rs.getString("originalAmount"));
			obj.setOriginalUnit(rs.getString("originalUnit"));
			obj.setOriginalBV(rs.getString("originalBV"));
			obj.setBookAmount(rs.getString("bookAmount"));
			obj.setBookValue(rs.getString("bookValue"));
			obj.setNameplate(rs.getString("nameplate"));
			obj.setSpecification(rs.getString("specification"));
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
		String sql=" select a.enterOrg, a.ownership, a.propertyNo, a.lotNo, a.serialNoS, a.serialNoE, a.oldSerialNoS, a.oldSerialNoE, a.nameplate, a.specification "+
			" from UNTNE_Nonexp a where 1=1 "; 
			if (!"".equals(getQ_enterOrg()))
				sql+=" and a.enterOrg = " + Common.sqlChar(getQ_enterOrg()) ;
			if (!"".equals(getQ_propertyNoS()))
				sql+=" and a.propertyNo >= " + Common.sqlChar(getQ_propertyNoS()) ;
			if (!"".equals(getQ_propertyNoE()))
				sql+=" and a.propertyNo <= " + Common.sqlChar(getQ_propertyNoE()) ;
			if (!"".equals(getQ_serialNoS()))
				sql+=" and a.serialNoS >= " + Common.sqlChar(Common.formatFrontZero(getQ_serialNoS(),7)) ;
			if (!"".equals(getQ_serialNoE()))
				sql+=" and a.serialNoE <= " + Common.sqlChar(Common.formatFrontZero(getQ_serialNoE(),7)) ;
			if (!"".equals(getQ_oldSerialNoS()))
				sql+=" and a.oldSerialNoS >= " + Common.sqlChar(Common.formatFrontZero(getQ_oldSerialNoS(),7)) ;
			if (!"".equals(getQ_oldSerialNoE()))
				sql+=" and a.oldSerialNoE <= " + Common.sqlChar(Common.formatFrontZero(getQ_oldSerialNoE(),7)) ;
			if (!"".equals(getQ_propertyName1()))
				sql+=" and a.propertyName1 like '%" + getQ_propertyName1() +"%' " ;
			if (!"".equals(getQ_nameplate()))
				sql+=" and a.nameplate like '%" + getQ_nameplate() +"%' " ;
			if (!"".equals(getQ_specification()))
				sql+=" and a.specification like '%" + getQ_specification() +"%' " ;
			sql+=" order by enterOrg, ownership, propertyNo";			
		ResultSet rs = db.querySQL(sql,true);
		processCurrentPageAttribute(rs);
		if (getTotalRecord() > 0) {
			int count = getRecordStart();
			int end = getRecordEnd();
			do {
				if (count > end)
					break;
				String rowArray[]=new String[8];
				rowArray[0]=Common.get(rs.getString("enterOrg")); 
				rowArray[1]=Common.get(rs.getString("ownership")); 
				rowArray[2]=Common.get(rs.getString("propertyNo")); 
				rowArray[3]=Common.get(rs.getString("lotNo")); 
				rowArray[4]=Common.get(rs.getString("serialNoS"))+"-"+Common.get(rs.getString("serialNoE")); 
				rowArray[5]=Common.get(rs.getString("oldSerialNoS"))+"-"+Common.get(rs.getString("oldSerialNoE")); 
				rowArray[6]=Common.get(rs.getString("nameplate")); 
				rowArray[7]=Common.get(rs.getString("specification"));
				count++;
				objList.add(rowArray);				
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


