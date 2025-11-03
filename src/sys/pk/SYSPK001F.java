
/*
*<br>程式目的：行政院函知案件管理
*<br>程式代號：syspk001f
*<br>程式日期：0950222
*<br>程式作者：clive.chang
*<br>--------------------------------------------------------
*<br>修改作者　　修改日期　　　修改目的
*<br>--------------------------------------------------------
*/

package sys.pk;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Vector;

import util.Common;
import util.Database;
import util.Datetime;
import util.SuperBean;

public class SYSPK001F extends SuperBean{


String noticeNo;
String highestLevelDoc;
String highestLevelDate;
String cityPublishDoc;
String cityPublishDate;
String verify;
String memo;

String q_noticeNo;
String q_noticeNoE;
String q_highestLevelDate;
String q_highestLevelDateE;
String q_cityPublishDate;
String q_cityPublishDateE;
String q_verify;


public String getNoticeNo(){ return checkGet(noticeNo); }
public void setNoticeNo(String s){ noticeNo=checkSet(s); }
public String getHighestLevelDoc(){ return checkGet(highestLevelDoc); }
public void setHighestLevelDoc(String s){ highestLevelDoc=checkSet(s); }
public String getHighestLevelDate(){ return checkGet(highestLevelDate); }
public void setHighestLevelDate(String s){ highestLevelDate=checkSet(s); }
public String getCityPublishDoc(){ return checkGet(cityPublishDoc); }
public void setCityPublishDoc(String s){ cityPublishDoc=checkSet(s); }
public String getCityPublishDate(){ return checkGet(cityPublishDate); }
public void setCityPublishDate(String s){ cityPublishDate=checkSet(s); }
public String getVerify(){ return checkGet(verify); }
public void setVerify(String s){ verify=checkSet(s); }
public String getMemo(){ return checkGet(memo); }
public void setMemo(String s){ memo=checkSet(s); }

public String getQ_noticeNo(){ return checkGet(q_noticeNo); }
public void setQ_noticeNo(String s){ q_noticeNo=checkSet(s); }
public String getQ_noticeNoE(){ return checkGet(q_noticeNoE); }
public void setQ_noticeNoE(String s){ q_noticeNoE=checkSet(s); }
public String getQ_highestLevelDate(){ return checkGet(q_highestLevelDate); }
public void setQ_highestLevelDate(String s){ q_highestLevelDate=checkSet(s); }
public String getQ_highestLevelDateE(){ return checkGet(q_highestLevelDateE); }
public void setQ_highestLevelDateE(String s){ q_highestLevelDateE=checkSet(s); }
public String getQ_cityPublishDate(){ return checkGet(q_cityPublishDate); }
public void setQ_cityPublishDate(String s){ q_cityPublishDate=checkSet(s); }
public String getQ_cityPublishDateE(){ return checkGet(q_cityPublishDateE); }
public void setQ_cityPublishDateE(String s){ q_cityPublishDateE=checkSet(s); }
public String getQ_verify(){ return checkGet(q_verify); }
public void setQ_verify(String s){ q_verify=checkSet(s); }

//傳回執行insert前之檢查sql
protected String[][] getInsertCheckSQL(){
	//取得流水號
	Database db = new Database();
	ResultSet rs;	
	String sql="select max(noticeNo)+1 as noticeNo from SYSPK_Notice ";
	try {		
		rs = db.querySQL(sql);
		if (rs.next() && rs.getInt(1)>0){
			setNoticeNo(Common.formatFrontZero(rs.getString(1),6));
		}else{
			setNoticeNo("000001");
		}
	} catch (Exception e) {
		e.printStackTrace();
	} finally {
		db.closeAll();
	}	
	String[][] checkSQLArray = new String[2][4];
 	checkSQLArray[0][0]=" select count(*) as checkResult from SYSPK_Notice where 1=1 " +  
		" and noticeNo = " + Common.sqlChar(noticeNo) +  
		"";
	checkSQLArray[0][1]=">";
	checkSQLArray[0][2]="0";
	checkSQLArray[0][3]="該筆資料己重複，請重新輸入！";

 	checkSQLArray[1][0]=" select count(*) as checkResult from SYSPK_Notice where 1=1 " +  
 		" and highestLevelDoc = " + Common.sqlChar(highestLevelDoc) + 
 		" and highestLevelDate = " + Common.sqlChar(highestLevelDate) + 
 		"";
	checkSQLArray[1][1]=">";
	checkSQLArray[1][2]="0";
	checkSQLArray[1][3]="該筆行政院函文號己重複，請重新輸入！";	
	return checkSQLArray;
}


//傳回insert sql
protected String[] getInsertSQL(){
	String[] execSQLArray = new String[1];
	execSQLArray[0]=" insert into SYSPK_Notice(" +
			" noticeNo,"+
			" highestLevelDoc,"+
			" highestLevelDate,"+
			" cityPublishDoc,"+
			" cityPublishDate,"+
			" verify,"+
			" memo,"+
			" editID,"+
			" editDate,"+
			" editTime"+
		" )Values(" +
			Common.sqlChar(noticeNo) + "," +
			Common.sqlChar(highestLevelDoc) + "," +
			Common.sqlChar(highestLevelDate) + "," +
			Common.sqlChar(cityPublishDoc) + "," +
			Common.sqlChar(cityPublishDate) + "," +
			Common.sqlChar("N") + "," +
			Common.sqlChar(memo) + "," +
			Common.sqlChar(getEditID()) + "," +
			Common.sqlChar(getEditDate()) + "," +
			Common.sqlChar(getEditTime()) + ")" ;
	return execSQLArray;
}


protected String[][] getUpdateCheckSQL(){	
	Vector vSQL = new Vector();
	String[] arrSQL = new String[4];
	String[][] checkSQLArray;
	if ("Y".equals(getVerify())) {
		arrSQL[0]=" select count(*) as checkResult from SYSPK_NoticeProperty where 1=1 " +  
			" and noticeNo = " + Common.sqlChar(noticeNo) +  
			"";
		arrSQL[1]="<=";
		arrSQL[2]="0";
		arrSQL[3]="財產編號頁籤中，無任何資料，故無法通過審核，請查驗！";
		vSQL.addElement(arrSQL);

		//取得流水號
		Database db = new Database();
		ResultSet rs;	
		String sql=" select propertyNo, propertyName, editKind, propertyUnit, material, limitYear from SYSPK_NoticeProperty where noticeNo="+Common.sqlChar(noticeNo)+" and editKind='N' ";
		try {		
			rs = db.querySQL(sql);
			while (rs.next()) {
				arrSQL[0]=" select count(*) as checkResult from SYSPK_PropertyCode where propertyNo=" + Common.sqlChar(rs.getString("propertyNo"));
				arrSQL[1]=">";
				arrSQL[2]="0";
				arrSQL[3]="財產編號「"+rs.getString("propertyNo")+"」已存在於財產代碼檔中，故無法新增！";
				vSQL.addElement(arrSQL);				
			}
			rs.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.closeAll();
		}			
		checkSQLArray = new String[vSQL.size()][];
		for(int i=0;i<checkSQLArray.length;i++){
			checkSQLArray[i] = (String[])vSQL.elementAt(i);
		}		
	} else {
		checkSQLArray = new String[1][4];		
	 	checkSQLArray[0][0]="";
	 	checkSQLArray[0][1]="";
	 	checkSQLArray[0][2]="";
	 	checkSQLArray[0][3]="";		
	}
	return checkSQLArray;
}


//傳回update sql
protected String[] getUpdateSQL(){
	StringBuffer sbSQL = new StringBuffer("");	
	if ("Y".equals(getVerify())) {
		//取得流水號
		Database db = new Database();
		ResultSet rs;	
		String sql=" select propertyNo, propertyName, editKind, propertyUnit, material, limitYear, memo from SYSPK_NoticeProperty where noticeNo="+Common.sqlChar(noticeNo);
		try {		
			rs = db.querySQL(sql);
			while (rs.next()) {					
				if ("N".equals(rs.getString("editKind"))||"U".equals(rs.getString("editKind"))) {
					//刪除財產編號
					sbSQL.append(" delete from SYSPK_PropertyCode where propertyNo=");
					sbSQL.append(Common.sqlChar(rs.getString("propertyNo")));
					sbSQL.append(" and enterOrg='000000000A':;:");
					
					//新增財產編號
					sbSQL.append(" insert into SYSPK_PropertyCode(enterOrg,propertyNo,propertyName,propertyType,propertyUnit,material,limitYear,memo,editID,editDate,editTime)values('000000000A',");
					sbSQL.append(Common.sqlChar(Common.get(rs.getString("propertyNo"))));
					sbSQL.append(",");
					sbSQL.append(Common.sqlChar(Common.get(rs.getString("propertyName"))));
					sbSQL.append(",'1',");
					sbSQL.append(Common.sqlChar(Common.get(rs.getString("propertyUnit"))));
					sbSQL.append(",");					
					sbSQL.append(Common.sqlChar(Common.get(rs.getString("material"))));
					sbSQL.append(",");					
					sbSQL.append(Common.sqlChar(Common.get(rs.getString("limitYear"))));
					sbSQL.append(",");
					sbSQL.append(Common.sqlChar(Common.get(rs.getString("memo"))));
					sbSQL.append(",");
					sbSQL.append(Common.sqlChar(getEditID()));
					sbSQL.append(",");
					sbSQL.append(Common.sqlChar(Datetime.getYYYMMDD()));
					sbSQL.append(",");
					sbSQL.append(Common.sqlChar(Datetime.getHHMM()));
					sbSQL.append("):;:");
				} else {
					if ("D".equals(rs.getString("editKind"))) {
						sbSQL.append(" update SYSPK_PropertyCode set propertyType='0' where enterOrg='000000000A' and propertyNo=");
						sbSQL.append(Common.sqlChar(rs.getString("propertyNo")));
						sbSQL.append(":;:");						
					}
					/**
					else if("U".equals(rs.getString("editKind"))) {
						sbSQL.append(" update SYSPK_PropertyCode set propertyType='1',propertyName");
						sbSQL.append(Common.sqlChar(rs.getString("propertyName")));
						sbSQL.append(",");
						sbSQL.append(Common.sqlChar(rs.getString("propertyName")));
						sbSQL.append(",propertyUnit=");						
						sbSQL.append(Common.sqlChar(Common.get(rs.getString("propertyUnit"))));
						sbSQL.append(",material=");
						sbSQL.append(Common.sqlChar(Common.get(rs.getString("material"))));
						sbSQL.append(",limitYear=");
						sbSQL.append(Common.sqlChar(Common.get(rs.getString("limitYear"))));
						sbSQL.append(",");
						sbSQL.append(",memo=");
						sbSQL.append(Common.sqlChar(Common.get(rs.getString("memo"))));						
						sbSQL.append(",editID=");
						sbSQL.append(Common.sqlChar(getEditID()));
						sbSQL.append(",editDate=");
						sbSQL.append(Common.sqlChar(Datetime.getYYYMMDD()));
						sbSQL.append(",editTime=");
						sbSQL.append(Common.sqlChar(Datetime.getHHMM()));
						sbSQL.append(" where enterOrg='000000000A' and propertyNo=");
						sbSQL.append(Common.sqlChar(rs.getString("propertyNo")));
						sbSQL.append(":;:");
					}
					**/					
				}
				
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.closeAll();
		}
	}
	sbSQL.append(" update SYSPK_Notice set " +
			" cityPublishDoc = " + Common.sqlChar(cityPublishDoc) + "," +
			" cityPublishDate = " + Common.sqlChar(cityPublishDate) + "," +
			" verify = " + Common.sqlChar(verify) + "," +
			" memo = " + Common.sqlChar(memo) + "," +
			" editID = " + Common.sqlChar(getEditID()) + "," +
			" editDate = " + Common.sqlChar(getEditDate()) + "," +
			" editTime = " + Common.sqlChar(getEditTime()) + 
		" where 1=1 " + 
			" and noticeNo = " + Common.sqlChar(noticeNo)+":;:");
	return sbSQL.toString().split(":;:");
}


protected String[][] getDeleteCheckSQL(){
	String[][] checkSQLArray = new String[1][4];
 	checkSQLArray[0][0]=" select count(*) as checkResult from SYSPK_Notice where verify='Y' " +  
		" and noticeNo = " + Common.sqlChar(noticeNo) +  
		"";
	checkSQLArray[0][1]=">";
	checkSQLArray[0][2]="0";
	checkSQLArray[0][3]="該筆資料己經審核確認，故不允許刪除！";	
	return checkSQLArray;
}

//傳回delete sql
protected String[] getDeleteSQL(){
	String[] execSQLArray = new String[2];
	execSQLArray[0]=" delete SYSPK_NoticeProperty where 1=1 " +
		" and noticeNo = " + Common.sqlChar(noticeNo);	
	execSQLArray[1]=" delete SYSPK_Notice where 1=1 " +
			" and noticeNo = " + Common.sqlChar(noticeNo);
	return execSQLArray;
}


/**
 * <br>
 * <br>目的：依主鍵查詢單一資料
 * <br>參數：無
 * <br>傳回：傳回本物件
*/

public SYSPK001F  queryOne() throws Exception{
	Database db = new Database();
	SYSPK001F obj = this;
	try {
		String sql=" select a.noticeNo, a.highestLevelDoc, a.highestLevelDate, a.cityPublishDoc, a.cityPublishDate, a.verify, a.memo, a.editID, a.editDate, a.editTime  "+
			" from SYSPK_Notice a where a.noticeNo = " + Common.sqlChar(noticeNo);
		ResultSet rs = db.querySQL(sql);
		if (rs.next()){
			obj.setNoticeNo(rs.getString("noticeNo"));
			obj.setHighestLevelDoc(rs.getString("highestLevelDoc"));
			obj.setHighestLevelDate(rs.getString("highestLevelDate"));
			obj.setCityPublishDoc(rs.getString("cityPublishDoc"));
			obj.setCityPublishDate(rs.getString("cityPublishDate"));
			obj.setVerify(rs.getString("verify"));
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
		String sql=" select a.noticeNo, a.highestLevelDoc, a.highestLevelDate, decode(a.verify,'Y','審核通過','') verify "+
			" from SYSPK_Notice a where 1=1 "; 
			if (!"".equals(getQ_noticeNo()))
				sql+=" and a.noticeNo >= " + Common.sqlChar(Common.formatFrontZero(getQ_noticeNo(),6)) ;
			if (!"".equals(getQ_noticeNoE()))
				sql+=" and a.noticeNo <= " + Common.sqlChar(Common.formatFrontZero(getQ_noticeNo(),6)) ;			
			if (!"".equals(getQ_highestLevelDate()))
				sql+=" and a.highestLevelDate >= " + Common.sqlChar(getQ_highestLevelDate()) ;
			if (!"".equals(getQ_highestLevelDateE()))
				sql+=" and a.highestLevelDate <= " + Common.sqlChar(getQ_highestLevelDateE()) ;
			if (!"".equals(getQ_cityPublishDateE()))
				sql+=" and a.cityPublishDate <= " + Common.sqlChar(getQ_cityPublishDateE()) ;
			if (!"".equals(getQ_verify()))
				sql+=" and a.verify = " + Common.sqlChar(getQ_verify()) ;			

		ResultSet rs = db.querySQL(sql+" order by a.highestLevelDate");
		while (rs.next()){
			counter++;
			String rowArray[]=new String[4];
			rowArray[0]=rs.getString("noticeNo");  
			rowArray[1]=rs.getString("highestLevelDate");
			rowArray[2]=rs.getString("highestLevelDoc");			
			rowArray[3]=rs.getString("verify");
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

