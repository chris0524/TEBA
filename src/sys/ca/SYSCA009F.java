/*
*<br>程式目的：地址代碼維護
*<br>程式代號：sysca009f
*<br>程式日期：0980623
*<br>程式作者：Chu-Hung
*<br>--------------------------------------------------------
*<br>修改作者　　修改日期　　　修改目的
*<br>--------------------------------------------------------
*/

package sys.ca;

import java.sql.ResultSet;
import java.util.ArrayList;
import util.*;

public class SYSCA009F extends QueryBean{


String addrKind;
String addrID;
String cod2_Desc;
String addrName;
String zipCode;
String seqNo;

String q_addrKind;
String q_addrID;
String q_cod2_Desc;

public String getAddrKind(){ return checkGet(addrKind); }
public void setAddrKind(String s){ addrKind=checkSet(s); }
public String getAddrID(){ return checkGet(addrID); }
public void setAddrID(String s){ addrID=checkSet(s); }
public String getCod2_Desc(){ return checkGet(cod2_Desc); }
public void setCod2_Desc(String s){ cod2_Desc=checkSet(s); }
public String getAddrName(){ return checkGet(addrName); }
public void setAddrName(String s){ addrName=checkSet(s); }
public String getZipCode(){ return checkGet(zipCode); }
public void setZipCode(String s){ zipCode=checkSet(s); }
public String getSeqNo(){ return checkGet(seqNo); }
public void setSeqNo(String s){ seqNo=checkSet(s); }

public String getQ_addrKind(){ return checkGet(q_addrKind); }
public void setQ_addrKind(String s){ q_addrKind=checkSet(s); }
public String getQ_addrID(){ return checkGet(q_addrID); }
public void setQ_addrID(String s){ q_addrID=checkSet(s); }
public String getQ_cod2_Desc(){ return checkGet(q_cod2_Desc); }
public void setQ_cod2_Desc(String s){ q_cod2_Desc=checkSet(s); }


//傳回執行insert前之檢查sql
protected String[][] getInsertCheckSQL(){
	String[][] checkSQLArray = new String[1][4];
 	checkSQLArray[0][0]=" select count(*) as checkResult from SYSCA_Addr where 1=1 " + 
		" and addrID = " + Common.sqlChar(addrID) + 
		"";
	checkSQLArray[0][1]=">";
	checkSQLArray[0][2]="0";
	checkSQLArray[0][3]="該筆資料己重複，請重新輸入！";
	return checkSQLArray;
}


//傳回insert sql
protected String[] getInsertSQL(){
	String[] execSQLArray = new String[1];
	execSQLArray[0]=" insert into SYSCA_Addr(" +
			" addrKind,"+
			" addrID,"+
			" cod2_Desc,"+
			" addrName,"+
			" zipCode,"+
			" seqNo"+
		" )Values(" +
			Common.sqlChar(addrKind) + "," +
			Common.sqlChar(addrID) + "," +
			Common.sqlChar(cod2_Desc) + "," +
			Common.sqlChar(addrName) + "," +
			Common.sqlChar(zipCode) + "," +
			Common.sqlChar(seqNo) + ")" ;
	return execSQLArray;
}


//傳回update sql
protected String[] getUpdateSQL(){
	String[] execSQLArray = new String[1];
	execSQLArray[0]=" update SYSCA_Addr set " +
			" addrKind = " + Common.sqlChar(addrKind) + "," +
			" cod2_Desc = " + Common.sqlChar(cod2_Desc) + "," +
			" addrName = " + Common.sqlChar(addrName) + "," +
			" zipCode = " + Common.sqlChar(zipCode) + "," +
			" seqNo = " + Common.sqlChar(seqNo) +
		" where 1=1 " + 
			" and addrID = " + Common.sqlChar(addrID) +
			"";
	//System.out.println(execSQLArray[0]);
	return execSQLArray;
}


//傳回delete sql
protected String[] getDeleteSQL(){
	String[] execSQLArray = new String[1];
	execSQLArray[0]=" delete SYSCA_Addr where 1=1 " +
			" and addrID = " + Common.sqlChar(addrID) +
			"";
	return execSQLArray;
}


/**
 * <br>
 * <br>目的：依主鍵查詢單一資料
 * <br>參數：無
 * <br>傳回：傳回本物件
*/

public SYSCA009F  queryOne() throws Exception{
	Database db = new Database();
	SYSCA009F obj = this;
	try {
		String sql=" select a.addrKind, a.addrID, a.cod2_Desc, a.addrName, a.zipCode, a.seqNo "+
			" from SYSCA_Addr a where 1=1 " +
			" and a.addrID = " + Common.sqlChar(addrID) +
			"";
		//System.out.println(sql);
		ResultSet rs = db.querySQL(sql);
		if (rs.next()){
			obj.setAddrKind(rs.getString("addrKind"));
			obj.setAddrID(rs.getString("addrID"));
			obj.setCod2_Desc(rs.getString("cod2_Desc"));
			obj.setAddrName(rs.getString("addrName"));
			obj.setZipCode(rs.getString("zipCode"));
			obj.setSeqNo(rs.getString("seqNo"));
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
		String sql=" select decode(a.addrKind,'1','縣市','2','鄉鎮市區','3','村里') as addrKind, " +
		           " a.addrID, a.cod2_Desc, a.addrName, a.zipCode, a.seqNo " +
			       " from SYSCA_Addr a where 1=1 "; 
			if (!"".equals(getQ_addrKind()))
				sql+=" and a.addrKind = " + Common.sqlChar(getQ_addrKind()) ;
			if (!"".equals(getQ_addrID()))
				sql+=" and a.addrID = " + Common.sqlChar(getQ_addrID()) ;
			if (!"".equals(getQ_cod2_Desc()))
				sql+=" and a.cod2_Desc like '" + getQ_cod2_Desc() +"%' " ;
			sql+=" order by a.seqNo ";
		//System.out.println(sql);
		ResultSet rs = db.querySQL(sql,true);
		processCurrentPageAttribute(rs);
		if (getTotalRecord() > 0) {
			int count = getRecordStart();
			int end = getRecordEnd();
			do {
				if (count > end) break;
			    String rowArray[]=new String[7];
			    rowArray[0]=Common.get(rs.getString("addrKind")); 
			    rowArray[1]=Common.get(rs.getString("addrID")); 
		     	rowArray[2]=Common.get(rs.getString("cod2_Desc")); 
		    	rowArray[3]=Common.get(rs.getString("addrName")); 
		    	rowArray[4]=Common.get(rs.getString("zipCode")); 
		    	rowArray[5]=Common.get(rs.getString("seqNo")); 
			    objList.add(rowArray);
			    count++;
			/*if (counter==getListLimit()){
				setErrorMsg(getListLimitError());
				break;
			}*/
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


