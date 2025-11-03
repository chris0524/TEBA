/*
*<br>程式目的：管理機管財產編號
*<br>程式代號：syspk005f
*<br>程式日期：0950224
*<br>程式作者：clive.chang
*<br>--------------------------------------------------------
*<br>修改作者　　修改日期　　　修改目的
*<br>--------------------------------------------------------
*/

package sys.pk;

import java.sql.ResultSet;
import java.util.ArrayList;
import util.*;

public class SYSPK005F extends SuperBean{


String applyNo;
String seqNo;
String propertyNo;
String propertyName;
String editKind;
String propertyUnit;
String material;
String limitYear;
String memo;
String isClose;
String highestLevelIsAgree;


String q_applyOrgNo;
String q_applyOrgNoName;
String q_applyDate;
String q_applyDateE;
String q_cityIsAgree;
String q_cityDate;
String q_cityDateE;
String q_highestLevelIsAgree;
String q_highestLevelDate;
String q_highestLevelDateE;
String q_cityPublishDate;
String q_cityPublishDateE;
String q_cityResponseDate;
String q_cityResponseDateE;
String q_memo;


public String getHighestLevelIsAgree(){ return checkGet(highestLevelIsAgree); }
public void setHighestLevelIsAgree(String s){ highestLevelIsAgree=checkSet(s); }
public String getApplyNo(){ return checkGet(applyNo); }
public void setApplyNo(String s){ applyNo=checkSet(s); }
public String getSeqNo(){ return checkGet(seqNo); }
public void setSeqNo(String s){ seqNo=checkSet(s); }
public String getPropertyNo(){ return checkGet(propertyNo); }
public void setPropertyNo(String s){ propertyNo=checkSet(s); }
public String getPropertyName(){ return checkGet(propertyName); }
public void setPropertyName(String s){ propertyName=checkSet(s); }
public String getEditKind(){ return checkGet(editKind); }
public void setEditKind(String s){ editKind=checkSet(s); }
public String getPropertyUnit(){ return checkGet(propertyUnit); }
public void setPropertyUnit(String s){ propertyUnit=checkSet(s); }
public String getMaterial(){ return checkGet(material); }
public void setMaterial(String s){ material=checkSet(s); }
public String getLimitYear(){ return checkGet(limitYear); }
public void setLimitYear(String s){ limitYear=checkSet(s); }
public String getIsClose(){ return checkGet(isClose); }
public void setIsClose(String s){ isClose=checkSet(s); }
public String getMemo(){ return checkGet(memo); }
public void setMemo(String s){ memo=checkSet(s); }


public String getQ_applyOrgNo(){ return checkGet(q_applyOrgNo); }
public void setQ_applyOrgNo(String s){ q_applyOrgNo=checkSet(s); }
public String getQ_applyOrgNoName(){ return checkGet(q_applyOrgNoName); }
public void setQ_applyOrgNoName(String s){ q_applyOrgNoName=checkSet(s); }
public String getQ_applyDate(){ return checkGet(q_applyDate); }
public void setQ_applyDate(String s){ q_applyDate=checkSet(s); }
public String getQ_applyDateE(){ return checkGet(q_applyDateE); }
public void setQ_applyDateE(String s){ q_applyDateE=checkSet(s); }
public String getQ_cityIsAgree(){ return checkGet(q_cityIsAgree); }
public void setQ_cityIsAgree(String s){ q_cityIsAgree=checkSet(s); }
public String getQ_cityDate(){ return checkGet(q_cityDate); }
public void setQ_cityDate(String s){ q_cityDate=checkSet(s); }
public String getQ_cityDateE(){ return checkGet(q_cityDateE); }
public void setQ_cityDateE(String s){ q_cityDateE=checkSet(s); }
public String getQ_highestLevelIsAgree(){ return checkGet(q_highestLevelIsAgree); }
public void setQ_highestLevelIsAgree(String s){ q_highestLevelIsAgree=checkSet(s); }
public String getQ_highestLevelDate(){ return checkGet(q_highestLevelDate); }
public void setQ_highestLevelDate(String s){ q_highestLevelDate=checkSet(s); }
public String getQ_highestLevelDateE(){ return checkGet(q_highestLevelDateE); }
public void setQ_highestLevelDateE(String s){ q_highestLevelDateE=checkSet(s); }
public String getQ_cityPublishDate(){ return checkGet(q_cityPublishDate); }
public void setQ_cityPublishDate(String s){ q_cityPublishDate=checkSet(s); }
public String getQ_cityPublishDateE(){ return checkGet(q_cityPublishDateE); }
public void setQ_cityPublishDateE(String s){ q_cityPublishDateE=checkSet(s); }
public String getQ_cityResponseDate(){ return checkGet(q_cityResponseDate); }
public void setQ_cityResponseDate(String s){ q_cityResponseDate=checkSet(s); }
public String getQ_cityResponseDateE(){ return checkGet(q_cityResponseDateE); }
public void setQ_cityResponseDateE(String s){ q_cityResponseDateE=checkSet(s); }
public String getQ_memo(){ return checkGet(q_memo); }
public void setQ_memo(String s){ q_memo=checkSet(s); }

//傳回執行insert前之檢查sql
protected String[][] getInsertCheckSQL(){
	//取得流水號
	Database db = new Database();
	ResultSet rs;	
	String sql="select max(seqNo)+1 as seqNo from SYSPK_ApplyProperty where applyNo="+Common.sqlChar(applyNo);
	try {		
		rs = db.querySQL(sql);
		if (rs.next() && rs.getInt(1)>0){
			setSeqNo(Common.formatFrontZero(rs.getString(1),6));
		}else{
			setSeqNo("000001");
		}
	} catch (Exception e) {
		e.printStackTrace();
	} finally {
		db.closeAll();
	}			
	String[][] checkSQLArray = new String[2][4];
 	checkSQLArray[0][0]=" select count(*) as checkResult from SYSPK_applyProperty where 1=1 " + 
 		" and applyNo = " + Common.sqlChar(applyNo) + 
 		" and seqNo = " + Common.sqlChar(seqNo) + 
 		"";
 	checkSQLArray[0][1]=">";
 	checkSQLArray[0][2]="0";
 	checkSQLArray[0][3]="該筆資料己重複，請重新輸入！";	
 	
 	checkSQLArray[1][0]="";
	checkSQLArray[1][1]="";
	checkSQLArray[1][2]="";
	checkSQLArray[1][3]="";
	
	if (!"".equals(getPropertyNo())) {
	 	checkSQLArray[1][0]=" select count(*) as checkResult from SYSPK_applyProperty where 1=1 " + 
			" and applyNo = " + Common.sqlChar(applyNo) + 
			" and propertyNo = " + Common.sqlChar(propertyNo) + 
			"";
		checkSQLArray[1][1]=">";
		checkSQLArray[1][2]="0";
		checkSQLArray[1][3]="該筆財產編號己重複，請重新輸入！";
	}
	return checkSQLArray;
}


//傳回insert sql
protected String[] getInsertSQL(){
	String[] execSQLArray = new String[1];
	execSQLArray[0]=" insert into SYSPK_applyProperty(" +
			" applyNo,"+
			" seqNo,"+
			" propertyNo,"+
			" propertyName,"+
			" editKind,"+
			" propertyUnit,"+
			" material,"+
			" limitYear,"+
			" memo,"+
			" editID,"+
			" editDate,"+
			" editTime"+
		" )Values(" +
			Common.sqlChar(applyNo) + "," +
			Common.sqlChar(seqNo) + "," +
			Common.sqlChar(propertyNo) + "," +
			Common.sqlChar(propertyName) + "," +
			Common.sqlChar(editKind) + "," +
			Common.sqlChar(propertyUnit) + "," +
			Common.sqlChar(material) + "," +
			Common.sqlChar(limitYear) + "," +
			Common.sqlChar(memo) + "," +
			Common.sqlChar(getEditID()) + "," +
			Common.sqlChar(getEditDate()) + "," +
			Common.sqlChar(getEditTime()) + ")" ;
	return execSQLArray;
}



protected String[][] getUpdateCheckSQL(){
	String[][] checkSQLArray = new String[1][4];	 	
 	checkSQLArray[0][0]="";
	checkSQLArray[0][1]="";
	checkSQLArray[0][2]="";
	checkSQLArray[0][3]="";
	
	if (!"".equals(getPropertyNo())) {
	 	checkSQLArray[0][0]=" select count(*) as checkResult from SYSPK_applyProperty where 1=1 " + 
			" and applyNo = " + Common.sqlChar(applyNo) + 
			" and seqNo <> " + Common.sqlChar(seqNo) + 
			"";
		checkSQLArray[0][1]=">";
		checkSQLArray[0][2]="0";
		checkSQLArray[0][3]="該筆財產編號己重複，請重新輸入！";
	}
	return checkSQLArray;
}
//傳回update sql
protected String[] getUpdateSQL(){
	String[] execSQLArray = new String[1];
	execSQLArray[0]=" update SYSPK_applyProperty set " +
			" propertyNo = " + Common.sqlChar(propertyNo) + "," +
			" propertyName = " + Common.sqlChar(propertyName) + "," +
			" editKind = " + Common.sqlChar(editKind) + "," +
			" propertyUnit = " + Common.sqlChar(propertyUnit) + "," +
			" material = " + Common.sqlChar(material) + "," +
			" limitYear = " + Common.sqlChar(limitYear) + "," +
			" memo = " + Common.sqlChar(memo) + "," +
			" editID = " + Common.sqlChar(getEditID()) + "," +
			" editDate = " + Common.sqlChar(getEditDate()) + "," +
			" editTime = " + Common.sqlChar(getEditTime()) + 
		" where 1=1 " + 
			" and applyNo = " + Common.sqlChar(applyNo) +
			" and seqNo = " + Common.sqlChar(seqNo) +
			"";
	return execSQLArray;
}


//傳回delete sql
protected String[] getDeleteSQL(){
	String[] execSQLArray = new String[1];
	execSQLArray[0]=" delete SYSPK_applyProperty where 1=1 " +
			" and applyNo = " + Common.sqlChar(applyNo) +
			" and seqNo = " + Common.sqlChar(seqNo) +
			"";
	return execSQLArray;
}


/**
 * <br>
 * <br>目的：依主鍵查詢單一資料
 * <br>參數：無
 * <br>傳回：傳回本物件
*/

public SYSPK005F  queryOne() throws Exception{
	Database db = new Database();
	SYSPK005F obj = this;
	try {
		String sql=" select a.applyNo, a.seqNo, a.propertyNo, a.propertyName, a.editKind, a.propertyUnit, a.material, a.limitYear, a.memo, a.editID, a.editDate, a.editTime  "+
			" from SYSPK_applyProperty a where 1=1 " +
			" and a.applyNo = " + Common.sqlChar(applyNo) +
			" and a.seqNo = " + Common.sqlChar(seqNo) +
			"";
		ResultSet rs = db.querySQL(sql);
		if (rs.next()){
			obj.setApplyNo(rs.getString("applyNo"));
			obj.setSeqNo(rs.getString("seqNo"));
			obj.setPropertyNo(rs.getString("propertyNo"));
			obj.setPropertyName(rs.getString("propertyName"));
			obj.setEditKind(rs.getString("editKind"));
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
	try {
		String sql=" select a.applyNo, a.seqNo, a.propertyNo, a.propertyName, decode(a.editKind,'N','新增','U','修改','D','刪除','') editKind, a.propertyUnit, a.limitYear "+
			" from SYSPK_applyProperty a where a.applyNo="+Common.sqlChar(applyNo); 
		ResultSet rs = db.querySQL(sql+" order by propertyNo");
		while (rs.next()){
			String rowArray[]=new String[7];
			rowArray[0]=rs.getString("applyNo");
			rowArray[1]=rs.getString("seqNo");
			rowArray[2]=rs.getString("propertyNo"); 
			rowArray[3]=rs.getString("propertyName"); 
			rowArray[4]=rs.getString("editKind"); 
			rowArray[5]=rs.getString("propertyUnit"); 
			rowArray[6]=rs.getString("limitYear"); 
			objList.add(rowArray);
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


