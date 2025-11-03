/*
*<br>程式目的：管理機關申請案件管理
*<br>程式代號：syspk004f
*<br>程式日期：0950227
*<br>程式作者：clive.chang
*<br>--------------------------------------------------------
*<br>修改作者　　修改日期　　　修改目的
*<br>--------------------------------------------------------
*/

package sys.pk;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Vector;

import util.*;

public class SYSPK004F extends SuperBean{


String applyNo;
String applyOrgNo;
String applyOrgNoName;
String applyDoc;
String applyDate;
String cityIsAgree;
String cityDoc;
String cityDate;
String cityDisallow;
String highestLevelIsAgree;
String highestLevelDoc;
String highestLevelDate;
String highestLevelDisallow;
String cityPublishDoc;
String cityPublishDate;
String cityResponseDoc;
String cityResponseDate;
String isClose;
String memo;

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

public String getApplyNo(){ return checkGet(applyNo); }
public void setApplyNo(String s){ applyNo=checkSet(s); }
public String getApplyOrgNo(){ return checkGet(applyOrgNo); }
public void setApplyOrgNo(String s){ applyOrgNo=checkSet(s); }
public String getApplyOrgNoName(){ return checkGet(applyOrgNoName); }
public void setApplyOrgNoName(String s){ applyOrgNoName=checkSet(s); }
public String getApplyDoc(){ return checkGet(applyDoc); }
public void setApplyDoc(String s){ applyDoc=checkSet(s); }
public String getApplyDate(){ return checkGet(applyDate); }
public void setApplyDate(String s){ applyDate=checkSet(s); }
public String getCityIsAgree(){ return checkGet(cityIsAgree); }
public void setCityIsAgree(String s){ cityIsAgree=checkSet(s); }
public String getCityDoc(){ return checkGet(cityDoc); }
public void setCityDoc(String s){ cityDoc=checkSet(s); }
public String getCityDate(){ return checkGet(cityDate); }
public void setCityDate(String s){ cityDate=checkSet(s); }
public String getCityDisallow(){ return checkGet(cityDisallow); }
public void setCityDisallow(String s){ cityDisallow=checkSet(s); }
public String getHighestLevelIsAgree(){ return checkGet(highestLevelIsAgree); }
public void setHighestLevelIsAgree(String s){ highestLevelIsAgree=checkSet(s); }
public String getHighestLevelDoc(){ return checkGet(highestLevelDoc); }
public void setHighestLevelDoc(String s){ highestLevelDoc=checkSet(s); }
public String getHighestLevelDate(){ return checkGet(highestLevelDate); }
public void setHighestLevelDate(String s){ highestLevelDate=checkSet(s); }
public String getHighestLevelDisallow(){ return checkGet(highestLevelDisallow); }
public void setHighestLevelDisallow(String s){ highestLevelDisallow=checkSet(s); }
public String getCityPublishDoc(){ return checkGet(cityPublishDoc); }
public void setCityPublishDoc(String s){ cityPublishDoc=checkSet(s); }
public String getCityPublishDate(){ return checkGet(cityPublishDate); }
public void setCityPublishDate(String s){ cityPublishDate=checkSet(s); }
public String getCityResponseDoc(){ return checkGet(cityResponseDoc); }
public void setCityResponseDoc(String s){ cityResponseDoc=checkSet(s); }
public String getCityResponseDate(){ return checkGet(cityResponseDate); }
public void setCityResponseDate(String s){ cityResponseDate=checkSet(s); }
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
	String sql="select max(applyNo)+1 as applyNo from SYSPK_Apply ";
	try {		
		rs = db.querySQL(sql);
		if (rs.next() && rs.getInt(1)>0){
			setApplyNo(Common.formatFrontZero(rs.getString(1),6));
		}else{
			setApplyNo("000001");
		}
	} catch (Exception e) {
		e.printStackTrace();
	} finally {
		db.closeAll();
	}		
	String[][] checkSQLArray = new String[1][4];
 	checkSQLArray[0][0]=" select count(*) as checkResult from SYSPK_Apply where 1=1 " + 
		" and applyNo = " + Common.sqlChar(applyNo) + 
		"";
	checkSQLArray[0][1]=">";
	checkSQLArray[0][2]="0";
	checkSQLArray[0][3]="該筆資料己重複，請重新輸入！";
	return checkSQLArray;
}


//傳回insert sql
protected String[] getInsertSQL(){
	String[] execSQLArray = new String[1];
	execSQLArray[0]=" insert into SYSPK_Apply(" +
			" applyNo,"+
			" applyOrgNo,"+
			" applyDoc,"+
			" applyDate,"+
			" cityIsAgree,"+
			" cityDoc,"+
			" cityDate,"+
			" cityDisallow,"+
			" highestLevelIsAgree,"+
			" highestLevelDoc,"+
			" highestLevelDate,"+
			" highestLevelDisallow,"+
			" cityPublishDoc,"+
			" cityPublishDate,"+
			" cityResponseDoc,"+
			" cityResponseDate,"+
			" isClose,"+
			" memo,"+
			" editID,"+
			" editDate,"+
			" editTime"+
		" )Values(" +
			Common.sqlChar(applyNo) + "," +
			Common.sqlChar(applyOrgNo) + "," +
			Common.sqlChar(applyDoc) + "," +
			Common.sqlChar(applyDate) + "," +
			Common.sqlChar(cityIsAgree) + "," +
			Common.sqlChar(cityDoc) + "," +
			Common.sqlChar(cityDate) + "," +
			Common.sqlChar(cityDisallow) + "," +
			Common.sqlChar(highestLevelIsAgree) + "," +
			Common.sqlChar(highestLevelDoc) + "," +
			Common.sqlChar(highestLevelDate) + "," +
			Common.sqlChar(highestLevelDisallow) + "," +
			Common.sqlChar(cityPublishDoc) + "," +
			Common.sqlChar(cityPublishDate) + "," +
			Common.sqlChar(cityResponseDoc) + "," +
			Common.sqlChar(cityResponseDate) + "," +
			Common.sqlChar(isClose) + "," +
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
	if ("Y".equals(cityIsAgree) && "Y".equals(highestLevelIsAgree) && "Y".equals(isClose)) {
		arrSQL[0]=" select count(*) as checkResult from SYSPK_ApplyProperty where 1=1 " +  
			" and applyNo = " + Common.sqlChar(applyNo) +  
			"";
		arrSQL[1]="<=";
		arrSQL[2]="0";
		arrSQL[3]="財產編號頁籤中，無任何資料，故無法通過審核，請查驗！";
		vSQL.addElement(arrSQL);

		//取得流水號
		Database db = new Database();
		ResultSet rs;	
		String sql=" select propertyNo, propertyName, editKind, propertyUnit, material, limitYear from SYSPK_ApplyProperty where applyNo="+Common.sqlChar(applyNo);
		try {		
			rs = db.querySQL(sql);
			while (rs.next()) {
				if ("N".equals(Common.get(rs.getString("editKind")))) {
					arrSQL[0]=" select count(*) as checkResult from SYSPK_PropertyCode where propertyNo=" + Common.sqlChar(rs.getString("propertyNo"));
					arrSQL[1]=">";
					arrSQL[2]="0";
					arrSQL[3]="財產編號「"+rs.getString("propertyNo")+"」已存在於財產代碼檔中，故無法新增！";
					vSQL.addElement(arrSQL);
				}
				
				String vNo = Common.get(rs.getString("propertyNo"));
				if (vNo.length()>8) {
					String preWordList = "12345";
					String preWord = vNo.substring(0,3);
					String preWord1 = vNo.substring(0,1);
					if (preWordList.indexOf(preWord1,0)==-1) {
						arrSQL[0]=" select 22 as checkResult from dual ";
						arrSQL[1]=">";
						arrSQL[2]="0";
						arrSQL[3]="財產編號「"+rs.getString("propertyNo")+"」不是1,2,3,4,5開頭的字串，故無法新增！";
						vSQL.addElement(arrSQL);
					}
					if (Integer.parseInt(preWord)>502) {
						arrSQL[0]=" select 22 as checkResult from dual ";
						arrSQL[1]=">";
						arrSQL[2]="0";
						arrSQL[3]="對不起，財產編號的前三碼不能是>502的字串!\n";
						vSQL.addElement(arrSQL);
					}
				} else {
					arrSQL[0]=" select 22 as checkResult from dual ";
					arrSQL[1]=">";
					arrSQL[2]="0";
					arrSQL[3]="財產編號「"+rs.getString("propertyNo")+"」長度小於9，故無法新增！";
					vSQL.addElement(arrSQL);
				}
				
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
	if ("Y".equals(cityIsAgree) && "Y".equals(highestLevelIsAgree) && "Y".equals(isClose)) {
		//取得流水號
		Database db = new Database();
		ResultSet rs;	
		String sql=" select propertyNo, propertyName, editKind, propertyUnit, material, limitYear, memo from SYSPK_ApplyProperty where applyNo="+Common.sqlChar(applyNo);
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
				}
				
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.closeAll();
		}
	}	

	sbSQL.append(" update SYSPK_Apply set " +
			" applyOrgNo = " + Common.sqlChar(applyOrgNo) + "," +
			" applyDoc = " + Common.sqlChar(applyDoc) + "," +
			" applyDate = " + Common.sqlChar(applyDate) + "," +
			" cityIsAgree = " + Common.sqlChar(cityIsAgree) + "," +
			" cityDoc = " + Common.sqlChar(cityDoc) + "," +
			" cityDate = " + Common.sqlChar(cityDate) + "," +
			" cityDisallow = " + Common.sqlChar(cityDisallow) + "," +
			" highestLevelIsAgree = " + Common.sqlChar(highestLevelIsAgree) + "," +
			" highestLevelDoc = " + Common.sqlChar(highestLevelDoc) + "," +
			" highestLevelDate = " + Common.sqlChar(highestLevelDate) + "," +
			" highestLevelDisallow = " + Common.sqlChar(highestLevelDisallow) + "," +
			" cityPublishDoc = " + Common.sqlChar(cityPublishDoc) + "," +
			" cityPublishDate = " + Common.sqlChar(cityPublishDate) + "," +
			" cityResponseDoc = " + Common.sqlChar(cityResponseDoc) + "," +
			" cityResponseDate = " + Common.sqlChar(cityResponseDate) + "," +
			" isClose = " + Common.sqlChar(isClose) + "," +
			" memo = " + Common.sqlChar(memo) + "," +
			" editID = " + Common.sqlChar(getEditID()) + "," +
			" editDate = " + Common.sqlChar(getEditDate()) + "," +
			" editTime = " + Common.sqlChar(getEditTime()) + 
		" where 1=1 " + 
			" and applyNo = " + Common.sqlChar(applyNo) +
			":;:");
	return sbSQL.toString().split(":;:");
}

protected String[][] getDeleteCheckSQL(){
	String[][] checkSQLArray = new String[1][4];
 	checkSQLArray[0][0]=" select count(*) as checkResult from SYSPK_Apply where isClose='Y' " +  
		" and applyNo = " + Common.sqlChar(applyNo) +  
		"";
	checkSQLArray[0][1]=">";
	checkSQLArray[0][2]="0";
	checkSQLArray[0][3]="該筆資料己經由行院同意且已結案，故不允許刪除！";	
	return checkSQLArray;
}


//傳回delete sql
protected String[] getDeleteSQL(){
	String[] execSQLArray = new String[2];
	execSQLArray[0]=" delete SYSPK_ApplyProperty where 1=1 " +
		" and applyNo = " + Common.sqlChar(applyNo) +
		"";	
	execSQLArray[1]=" delete SYSPK_Apply where 1=1 " +
			" and applyNo = " + Common.sqlChar(applyNo) +
			"";
	return execSQLArray;
}


/**
 * <br>
 * <br>目的：依主鍵查詢單一資料
 * <br>參數：無
 * <br>傳回：傳回本物件
*/

public SYSPK004F  queryOne() throws Exception{
	Database db = new Database();
	SYSPK004F obj = this;
	try {
		String sql=" select a.applyNo, a.applyOrgNo, b.organSName as applyOrgNoName, a.applyDoc, a.applyDate, a.cityIsAgree, a.cityDoc, a.cityDate, a.cityDisallow, a.highestLevelIsAgree, a.highestLevelDoc, a.highestLevelDate, a.highestLevelDisallow, a.cityPublishDoc, a.cityPublishDate, a.cityResponseDoc, a.cityResponseDate, a.isClose, a.memo, a.editID, a.editDate, a.editTime  "+
			" from SYSPK_Apply a, SYSCA_Organ b where a.applyOrgNo=b.organID " +
			" and a.applyNo = " + Common.sqlChar(applyNo) +
			"";
		ResultSet rs = db.querySQL(sql);
		if (rs.next()){
			obj.setApplyNo(rs.getString("applyNo"));
			obj.setApplyOrgNo(rs.getString("applyOrgNo"));
			obj.setApplyOrgNoName(rs.getString("applyOrgNoName"));
			obj.setApplyDoc(rs.getString("applyDoc"));
			obj.setApplyDate(rs.getString("applyDate"));
			obj.setCityIsAgree(rs.getString("cityIsAgree"));
			obj.setCityDoc(rs.getString("cityDoc"));
			obj.setCityDate(rs.getString("cityDate"));
			obj.setCityDisallow(rs.getString("cityDisallow"));
			obj.setHighestLevelIsAgree(rs.getString("highestLevelIsAgree"));
			obj.setHighestLevelDoc(rs.getString("highestLevelDoc"));
			obj.setHighestLevelDate(rs.getString("highestLevelDate"));
			obj.setHighestLevelDisallow(rs.getString("highestLevelDisallow"));
			obj.setCityPublishDoc(rs.getString("cityPublishDoc"));
			obj.setCityPublishDate(rs.getString("cityPublishDate"));
			obj.setCityResponseDoc(rs.getString("cityResponseDoc"));
			obj.setCityResponseDate(rs.getString("cityResponseDate"));
			obj.setIsClose(rs.getString("isClose"));
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
		String sql=" select a.applyNo, a.applyOrgNo, b.organSName as applyOrgNoName, a.applyDate, decode(a.cityIsAgree,'Y','同意','N','駁回','') cityIsAgree, decode(a.highestLevelIsAgree,'Y','同意','N','駁回','') highestLevelIsAgree, a.highestLevelDate, a.highestLevelDoc "+
			" from SYSPK_Apply a, SYSCA_Organ b where a.applyOrgNo=b.organID "; 
			if (!"".equals(getQ_applyOrgNo()))
				sql+=" and a.applyOrgNo = " + Common.sqlChar(getQ_applyOrgNo()) ;
			
			if (!"".equals(getQ_applyDate()))
				sql+=" and a.applyDate >= " + Common.sqlChar(getQ_applyDate()) ;
			if (!"".equals(getQ_applyDateE()))
				sql+=" and a.applyDate <= " + Common.sqlChar(getQ_applyDateE()) ;
			
			if (!"".equals(getQ_cityIsAgree()))
				sql+=" and a.cityIsAgree = " + Common.sqlChar(getQ_cityIsAgree()) ;
			
			if (!"".equals(getQ_cityDate()))
				sql+=" and a.cityDate >= " + Common.sqlChar(getQ_cityDate()) ;
			if (!"".equals(getQ_cityDateE()))
				sql+=" and a.cityDate <= " + Common.sqlChar(getQ_cityDateE()) ;
			
			if (!"".equals(getQ_highestLevelIsAgree()))
				sql+=" and a.highestLevelIsAgree = " + Common.sqlChar(getQ_highestLevelIsAgree()) ;
			
			if (!"".equals(getQ_highestLevelDate()))
				sql+=" and a.highestLevelDate >= " + Common.sqlChar(getQ_highestLevelDate()) ;
			if (!"".equals(getQ_highestLevelDateE()))
				sql+=" and a.highestLevelDate <= " + Common.sqlChar(getQ_highestLevelDateE()) ;
			
			if (!"".equals(getQ_cityResponseDate()))
				sql+=" and a.cityResponseDate >= " + Common.sqlChar(getQ_cityResponseDate()) ;
			if (!"".equals(getQ_cityResponseDateE()))
				sql+=" and a.cityResponseDate <= " + Common.sqlChar(getQ_cityResponseDateE()) ;
			
			if (!"".equals(getQ_cityPublishDate()))
				sql+=" and a.cityPublishDate >= " + Common.sqlChar(getQ_cityPublishDate()) ;			
			if (!"".equals(getQ_cityPublishDateE()))
				sql+=" and a.cityPublishDate <= " + Common.sqlChar(getQ_cityPublishDateE()) ;
		ResultSet rs = db.querySQL(sql);
		while (rs.next()){
			counter++;
			String rowArray[]=new String[7];
			rowArray[0]=rs.getString("applyNo"); 
			rowArray[1]=rs.getString("applyOrgNoName"); 
			rowArray[2]=rs.getString("applyDate"); 
			rowArray[3]=rs.getString("cityIsAgree"); 
			rowArray[4]=rs.getString("highestLevelIsAgree"); 
			rowArray[5]=rs.getString("highestLevelDate"); 
			rowArray[6]=rs.getString("highestLevelDoc"); 
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


