/*
*<br>程式目的：土地合併分割作業－分割增加單
*<br>程式代號：untla036f
*<br>程式日期：0940930
*<br>程式作者：carey
*<br>--------------------------------------------------------
*<br>修改作者　　修改日期　　　修改目的
*<br>--------------------------------------------------------
*/

package unt.la;

import java.sql.ResultSet;
import java.util.ArrayList;
import util.*;

public class UNTLA036F extends UNTLA027Q{


String enterOrg;
String enterOrgName;
String ownership;
String addCaseNo1;
String caseName;
String writeDate;
String writeUnit;
String proofDoc;
String proofNo;
String manageNo;
String summonsNo;
String enterDate;
String mergeDivision;
String verify;
String closing;
String notes;
String editID;
String editDate;

String mergeReduce;
String mergeAdd;
String divisionReduce;
String divisionAdd;
String cause;
String cause1;
String mergeDivisionDate;
String approveOrg;
String approveDate;
String approveDoc;


public String getEnterOrg(){ return checkGet(enterOrg); }
public void setEnterOrg(String s){ enterOrg=checkSet(s); }
public String getEnterOrgName(){ return checkGet(enterOrgName); }
public void setEnterOrgName(String s){ enterOrgName=checkSet(s); }
public String getOwnership(){ return checkGet(ownership); }
public void setOwnership(String s){ ownership=checkSet(s); }
public String getAddCaseNo1(){ return checkGet(addCaseNo1); }
public void setAddCaseNo1(String s){ addCaseNo1=checkSet(s); }
public String getCaseName(){ return checkGet(caseName); }
public void setCaseName(String s){ caseName=checkSet(s); }
public String getWriteDate(){ return checkGet(writeDate); }
public void setWriteDate(String s){ writeDate=checkSet(s); }
public String getWriteUnit(){ return checkGet(writeUnit); }
public void setWriteUnit(String s){ writeUnit=checkSet(s); }
public String getProofDoc(){ return checkGet(proofDoc); }
public void setProofDoc(String s){ proofDoc=checkSet(s); }
public String getProofNo(){ return checkGet(proofNo); }
public void setProofNo(String s){ proofNo=checkSet(s); }
public String getManageNo(){ return checkGet(manageNo); }
public void setManageNo(String s){ manageNo=checkSet(s); }
public String getSummonsNo(){ return checkGet(summonsNo); }
public void setSummonsNo(String s){ summonsNo=checkSet(s); }
public String getEnterDate(){ return checkGet(enterDate); }
public void setEnterDate(String s){ enterDate=checkSet(s); }
public String getMergeDivision(){ return checkGet(mergeDivision); }
public void setMergeDivision(String s){ mergeDivision=checkSet(s); }
public String getVerify(){ return checkGet(verify); }
public void setVerify(String s){ verify=checkSet(s); }
public String getClosing(){ return checkGet(closing); }
public void setClosing(String s){ closing=checkSet(s); }
public String getNotes(){ return checkGet(notes); }
public void setNotes(String s){ notes=checkSet(s); }
public String getEditID(){ return checkGet(editID); }
public void setEditID(String s){ editID=checkSet(s); }
public String getEditDate(){ return checkGet(editDate); }
public void setEditDate(String s){ editDate=checkSet(s); }

public String getMergeReduce(){ return checkGet(mergeReduce); }
public void setMergeReduce(String s){ mergeReduce=checkSet(s); }
public String getMergeAdd(){ return checkGet(mergeAdd); }
public void setMergeAdd(String s){ mergeAdd=checkSet(s); }
public String getDivisionReduce(){ return checkGet(divisionReduce); }
public void setDivisionReduce(String s){ divisionReduce=checkSet(s); }
public String getDivisionAdd(){ return checkGet(divisionAdd); }
public void setDivisionAdd(String s){ divisionAdd=checkSet(s); }
public String getCause(){ return checkGet(cause); }
public void setCause(String s){ cause=checkSet(s); }
public String getCause1(){ return checkGet(cause1); }
public void setCause1(String s){ cause1=checkSet(s); }
public String getMergeDivisionDate(){ return checkGet(mergeDivisionDate); }
public void setMergeDivisionDate(String s){ mergeDivisionDate=checkSet(s); }
public String getApproveOrg(){ return checkGet(approveOrg); }
public void setApproveOrg(String s){ approveOrg=checkSet(s); }
public String getApproveDate(){ return checkGet(approveDate); }
public void setApproveDate(String s){ approveDate=checkSet(s); }
public String getApproveDoc(){ return checkGet(approveDoc); }
public void setApproveDoc(String s){ approveDoc=checkSet(s); }

//	傳回執行insert前之檢查sql
protected String[][] getInsertCheckSQL(){
	String[][] checkSQLArray = new String[2][4];
 	checkSQLArray[0][0]=" select count(*) as checkResult from UNTLA_AddProof where 1=1 " + 
		" and enterOrg = " + Common.sqlChar(enterOrg) + 
		" and ownership = " + Common.sqlChar(ownership) + 
		" and caseNo = " + Common.sqlChar(addCaseNo1) + 
		"";
	checkSQLArray[0][1]=">";
	checkSQLArray[0][2]="0";
	checkSQLArray[0][3]="該筆資料己重複，請重新輸入！";

	//合併增加單是否已存在
	checkSQLArray[1][0]=" select count(*) as checkResult from UNTLA_MergeDivision where 1=1 " + 
		" and enterOrg = " + Common.sqlChar(enterOrg) + 
		" and ownership = " + Common.sqlChar(ownership) + 
		" and caseNo = " + Common.sqlChar(mergeDivision) +
		" and divisionAdd is not null " + 
		"";
	checkSQLArray[1][1]=">";
	checkSQLArray[1][2]="0";
	checkSQLArray[1][3]="該案件之合併增加單已存在，不可再新增！";
	return checkSQLArray;
}


//	傳回insert sql
protected String[] getInsertSQL(){
	//取得電腦單號
	Database db = new Database();
	ResultSet rs;	
	String sql="select (max(caseNo) + 1) as caseNo from UNTLA_AddProof " +
		" where enterOrg = " + Common.sqlChar(enterOrg) +
		" and ownership = " + Common.sqlChar(ownership) +
		" and caseNo like " + Common.sqlChar(Datetime.getYYYMMDD() + "%") + 
		"";		
	try {		
		rs = db.querySQL(sql);
		if (rs.next()){
			if("".equals(checkGet(rs.getString("caseNo")))){
				setCaseNo(Datetime.getYYYMMDD() + "001");
			}else{
				setCaseNo(Datetime.getYYYMMDD() + Common.formatFrontZero(rs.getString("caseNo").substring(7),3));
			}
		} else {
			setCaseNo(Datetime.getYYYMMDD()+"001");
		}
		
		//取得增加單編號－號
		/**
		sql="select substr(max(substr(proofNo,8,3))+1001,2,3) as proofNo from UNTLA_AddProof " +
			" where enterOrg = " + Common.sqlChar(enterOrg) +
			" and ownership = " + Common.sqlChar(ownership) +
			" and substr(proofNo,1,7) = " + Common.sqlChar(getEditDate()) + 
			"";		
		rs = db.querySQL(sql);
		if (rs.next()){
			if (rs.getString("proofNo")==null)
				setProofNo(getEditDate()+"001");
			else
			    setProofNo(getEditDate()+rs.getString("proofNo"));
		}
		**/
	} catch (Exception e) {
		e.printStackTrace();
	} finally {
		db.closeAll();
	}
	setProofNo(MaxClosingYM.getMaxProofNo("UNTLA_AddProof",enterOrg,ownership));
	String[] execSQLArray = new String[2];
	execSQLArray[0]=" insert into UNTLA_AddProof(" +
			" enterOrg,"+
			" ownership,"+
			" caseNo,"+
			" caseName,"+
			" writeDate,"+
			" writeUnit,"+
			" proofDoc,"+
			" proofNo,"+
			" manageNo,"+
			" summonsNo,"+
			" enterDate,"+
			" mergeDivision,"+
			" verify,"+
			" closing,"+
			" notes,"+
			" editID,"+
			" editDate,"+
			" editTime"+
		" )Values(" +
			Common.sqlChar(enterOrg) + "," +
			Common.sqlChar(ownership) + "," +
			Common.sqlChar(addCaseNo1) + "," +
			Common.sqlChar(caseName) + "," +
			Common.sqlChar(writeDate) + "," +
			Common.sqlChar(writeUnit) + "," +
			Common.sqlChar(proofDoc) + "," +
			Common.sqlChar(proofNo) + "," +
			Common.sqlChar(manageNo) + "," +
			Common.sqlChar(summonsNo) + "," +
			Common.sqlChar(enterDate) + "," +
			Common.sqlChar(mergeDivision) + "," +
			Common.sqlChar(verify) + "," +
			Common.sqlChar(closing) + "," +
			Common.sqlChar(notes) + "," +
			Common.sqlChar(getEditID()) + "," +
			Common.sqlChar(getEditDate()) + "," +
			Common.sqlChar(getEditTime()) + ")" ;
	
	execSQLArray[1]=" update UNTLA_MergeDivision set " +
			" divisionAdd = " + Common.sqlChar(addCaseNo1) + "," +
			" editID = " + Common.sqlChar(getEditID()) + "," +
			" editDate = " + Common.sqlChar(getEditDate()) + "," +
			" editTime = " + Common.sqlChar(getEditTime()) + 
			" where 1=1 " + 
			" and enterOrg = " + Common.sqlChar(enterOrg) +
			" and ownership = " + Common.sqlChar(ownership) +
			" and caseNo = " + Common.sqlChar(mergeDivision) +
			"";
	
	return execSQLArray;
}

//	傳回update sql
protected String[] getUpdateSQL(){
	String[] execSQLArray = new String[1];
	execSQLArray[0]=" update UNTLA_AddProof set " +
			" writeDate = " + Common.sqlChar(writeDate) + "," +
			" writeUnit = " + Common.sqlChar(writeUnit) + "," +
			" proofDoc = " + Common.sqlChar(proofDoc) + "," +
			" manageNo = " + Common.sqlChar(manageNo) + "," +
			" summonsNo = " + Common.sqlChar(summonsNo) + "," +
			" notes = " + Common.sqlChar(notes) + "," +
			" editID = " + Common.sqlChar(getEditID()) + "," +
			" editDate = " + Common.sqlChar(getEditDate()) + "," +
			" editTime = " + Common.sqlChar(getEditTime()) + 
		" where 1=1 " + 
			" and enterOrg = " + Common.sqlChar(enterOrg) +
			" and ownership = " + Common.sqlChar(ownership) +
			" and caseNo = " + Common.sqlChar(addCaseNo1) +
			"";
	return execSQLArray;
}

//	傳回delete sql
protected String[] getDeleteSQL(){
	String[] execSQLArray = new String[10];
	String delTable[] = {"UNTLA_Manage", "UNTLA_Person", "UNTLA_Attachment", "UNTLA_Value", "UNTLA_Price", "UNTLA_ViewScene", "UNTLA_Tax"};
	
	//刪除土地主檔之明細檔
	for(int i=0;i<delTable.length;i++){
	    execSQLArray[i]=" delete " + delTable[i]+ " a where exists ( " +
			" select * from UNTLA_Land b where 1=1 " +
			" and b.enterOrg = a.enterOrg " +
			" and b.ownership = a.ownership " +
			" and b.propertyNo = a.propertyNo " +
			" and b.serialNo = a.serialNo " +
			" and b.enterOrg = " + Common.sqlChar(enterOrg) +
			" and b.ownership = " + Common.sqlChar(ownership) +
			" and b.caseNo = " + Common.sqlChar(addCaseNo1) +
			" ) " +
			"";	
	}
	
	//刪除土地增加單
	execSQLArray[7]=" delete UNTLA_AddProof where 1=1 " +
			" and enterOrg = " + Common.sqlChar(enterOrg) +
			" and ownership = " + Common.sqlChar(ownership) +
			" and caseNo = " + Common.sqlChar(addCaseNo1) + 
			"";

	//刪除土地主檔(刪除此檔前需先刪除其相關的明細檔，如土地管理檔...等)
	execSQLArray[8]=" delete UNTLA_Land where 1=1 " +
			" and enterOrg = " + Common.sqlChar(enterOrg) +
			" and ownership = " + Common.sqlChar(ownership) +
			" and caseNo = " + Common.sqlChar(addCaseNo1) +
			"";	

	//更新合併分割案件檔
	execSQLArray[9]=" update UNTLA_MergeDivision set " +
			" divisionAdd = null " + "," + 
			" editID = " + Common.sqlChar(getEditID()) + "," +
			" editDate = " + Common.sqlChar(getEditDate()) + "," +
			" editTime = " + Common.sqlChar(getEditTime()) + 
		" where 1=1 " + 
			" and enterOrg = " + Common.sqlChar(enterOrg) +
			" and ownership = " + Common.sqlChar(ownership) +
			" and caseNo = " + Common.sqlChar(mergeDivision) +
			"";
	
	//for (int i=0;i<execSQLArray.length;i++){System.out.println(execSQLArray[i]);}

	
	return execSQLArray;
}


/**
 * <br>
 * <br>目的：依主鍵查詢單一資料
 * <br>參數：無
 * <br>傳回：傳回本物件
*/

public UNTLA036F  queryOne() throws Exception{
	Database db = new Database();
	UNTLA036F obj = this;
	try {
		String sql=" select a.enterOrg, (select b.organSName from SYSCA_Organ b where b.organID = a.enterOrg ) organSName, a.ownership, a.caseNo, a.caseName, a.writeDate, a.writeUnit, a.proofDoc, a.proofNo, a.manageNo, a.summonsNo, a.enterDate, a.mergeDivision, a.verify, a.closing, a.notes, a.editID, a.editDate, a.editID, a.editDate, a.editTime  "+
		" from UNTLA_AddProof a where 1=1 " +
		" and a.enterOrg = " + Common.sqlChar(enterOrg) +
		" and a.ownership = " + Common.sqlChar(ownership) +
		" and a.caseNo = " + Common.sqlChar(addCaseNo1) +
		"";

		ResultSet rs = db.querySQL(sql);
		if (rs.next()){
			obj.setEnterOrg(rs.getString("enterOrg"));
			obj.setOwnership(rs.getString("ownership"));
			obj.setEnterOrgName(rs.getString("organSName"));
			obj.setAddCaseNo1(rs.getString("caseNo"));
			obj.setCaseName(rs.getString("caseName"));
			obj.setWriteDate(rs.getString("writeDate"));
			obj.setWriteUnit(rs.getString("writeUnit"));
			obj.setProofDoc(rs.getString("proofDoc"));
			obj.setProofNo(rs.getString("proofNo"));
			obj.setManageNo(rs.getString("manageNo"));
			obj.setSummonsNo(rs.getString("summonsNo"));
			obj.setEnterDate(rs.getString("enterDate"));
			obj.setMergeDivision(rs.getString("mergeDivision"));
			obj.setVerify(rs.getString("verify"));
			obj.setClosing(rs.getString("closing"));
			obj.setNotes(rs.getString("notes"));
			obj.setEditID(rs.getString("editID"));
			obj.setEditDate(rs.getString("editDate"));
			obj.setEditTime(rs.getString("editTime"));
		}else{
			obj.setEnterOrg(enterOrg);
			obj.setEnterOrgName(enterOrgName);
			obj.setOwnership(ownership);
			obj.setAddCaseNo1(addCaseNo1);
			obj.setCaseName(caseName);
			obj.setWriteDate("");
			obj.setWriteUnit("");
			obj.setProofDoc("");
			obj.setProofNo("");
			obj.setManageNo("");
			obj.setSummonsNo("");
			obj.setEnterDate(mergeDivisionDate);
			obj.setApproveOrg("");
			obj.setApproveDate("");
			obj.setApproveDoc("");
			obj.setMergeDivision(mergeDivision);
			obj.setVerify(verify);
			obj.setClosing("");
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
		String sql=" select a.enterOrg, ownership,(case a.ownership when '2' then '國有' when '3' then '縣有' when '5' then '鄉鎮市' end) ownershipName, a.caseNo, a.caseName,"+
		    " a.writeDate, a.enterDate, (case a.verify when 'Y' then '是' else '否' end) verify, (case a.closing when 'Y' then '是' else '否' end) closing "+
			" from UNTLA_AddProof a where 1=1 " +
			" and a.enterOrg = " + Common.sqlChar(enterOrg) +
			" and a.ownership = " + Common.sqlChar(ownership) +
			" and a.caseNo = " + Common.sqlChar(addCaseNo1) +
			"";
System.out.println(sql);		
		ResultSet rs = db.querySQL(sql);
		while (rs.next()){
			counter++;
			String rowArray[]=new String[9];
			rowArray[0]=rs.getString("enterOrg"); 
			rowArray[1]=rs.getString("ownership"); 
			rowArray[2]=rs.getString("ownershipName"); 
			rowArray[3]=rs.getString("caseNo"); 
			rowArray[4]=rs.getString("caseName"); 
			rowArray[5]=rs.getString("writeDate"); 
			rowArray[6]=rs.getString("enterDate"); 
			rowArray[7]=rs.getString("verify"); 
			rowArray[8]=rs.getString("closing"); 
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


