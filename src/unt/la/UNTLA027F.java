/*
*<br>程式目的：土地合併分割作業－案件資料
*<br>程式代號：untla027f
*<br>程式日期：0940818
*<br>程式作者：carey
*<br>--------------------------------------------------------
*<br>修改作者　　修改日期　　　修改目的
*<br>--------------------------------------------------------
*/

package unt.la;

import java.sql.ResultSet;
import java.util.ArrayList;

import util.*;

public class UNTLA027F extends UNTLA027Q{


String enterOrg;
String enterOrgName;
String ownership;
String caseNo;
String caseName;
String cause;
String cause1;
String enterDate;
String approveOrg;
String approveDate;
String approveDoc;
String mergeReduce;
String mergeAdd;
String divisionReduce;
String divisionAdd;
String verify;
String notes;
String editID;
String editDate;
String editTime;
String oldVerify;

String mergeDivision;
String mergeDivisionDate;
String showMsg;
String closing;
String seqNo;
String checkApprove;

public String getClosing(){ return checkGet(closing); }
public void setClosing(String s){ closing=checkSet(s); }

public String getEnterOrg(){ return checkGet(enterOrg); }
public void setEnterOrg(String s){ enterOrg=checkSet(s); }
public String getEnterOrgName(){ return checkGet(enterOrgName); }
public void setEnterOrgName(String s){ enterOrgName=checkSet(s); }
public String getOwnership(){ return checkGet(ownership); }
public void setOwnership(String s){ ownership=checkSet(s); }
public String getCaseNo(){ return checkGet(caseNo); }
public void setCaseNo(String s){ caseNo=checkSet(s); }
public String getCaseName(){ return checkGet(caseName); }
public void setCaseName(String s){ caseName=checkSet(s); }
public String getCause(){ return checkGet(cause); }
public void setCause(String s){ cause=checkSet(s); }
public String getCause1(){ return checkGet(cause1); }
public void setCause1(String s){ cause1=checkSet(s); }
public String getEnterDate(){ return checkGet(enterDate); }
public void setEnterDate(String s){ enterDate=checkSet(s); }
public String getApproveOrg(){ return checkGet(approveOrg); }
public void setApproveOrg(String s){ approveOrg=checkSet(s); }
public String getApproveDate(){ return checkGet(approveDate); }
public void setApproveDate(String s){ approveDate=checkSet(s); }
public String getApproveDoc(){ return checkGet(approveDoc); }
public void setApproveDoc(String s){ approveDoc=checkSet(s); }
public String getMergeReduce(){ return checkGet(mergeReduce); }
public void setMergeReduce(String s){ mergeReduce=checkSet(s); }
public String getMergeAdd(){ return checkGet(mergeAdd); }
public void setMergeAdd(String s){ mergeAdd=checkSet(s); }
public String getDivisionReduce(){ return checkGet(divisionReduce); }
public void setDivisionReduce(String s){ divisionReduce=checkSet(s); }
public String getDivisionAdd(){ return checkGet(divisionAdd); }
public void setDivisionAdd(String s){ divisionAdd=checkSet(s); }
public String getVerify(){ return checkGet(verify); }
public void setVerify(String s){ verify=checkSet(s); }
public String getNotes(){ return checkGet(notes); }
public void setNotes(String s){ notes=checkSet(s); }
public String getEditID(){ return checkGet(editID); }
public void setEditID(String s){ editID=checkSet(s); }
public String getEditDate(){ return checkGet(editDate); }
public void setEditDate(String s){ editDate=checkSet(s); }
public String getEditTime(){ return checkGet(editTime); }
public void setEditTime(String s){ editTime=checkSet(s); }
public String getOldVerify(){ return checkGet(oldVerify); }
public void setOldVerify(String s){ oldVerify=checkSet(s); }

public String getMergeDivision(){ return checkGet(mergeDivision); }
public void setMergeDivision(String s){ mergeDivision=checkSet(s); }
public String getMergeDivisionDate(){ return checkGet(mergeDivisionDate); }
public void setMergeDivisionDate(String s){ mergeDivisionDate=checkSet(s); }
public String getShowMsg(){ return checkGet(showMsg); }
public void setShowMsg(String s){ showMsg=checkSet(s); }
public String getSeqNo(){ return checkGet(seqNo); }
public void setSeqNo(String s){ seqNo=checkSet(s); }
public String getCheckApprove(){ return checkGet(checkApprove); }
public void setCheckApprove(String s){ checkApprove=checkSet(s); }

//審核用
String verifyError;
String checkEnterOrg;
String mergeDivisionEnterDate;
String mergeDivisionCause;
String mergeDivisionCause1;
String reduceDetailEnterOrg;
String reduceDetailOwnership;
String reduceDetailCaseNo;
String landCaseNo;
String mergeDivision1KindNo;
String mergeDivision1CaseNo1;
String mergeDivision1Table;

public String getMergeDivision1Table(){ return checkGet(mergeDivision1Table); }
public void setMergeDivision1Table(String s){ mergeDivision1Table=checkSet(s); }
public String getMergeDivision1CaseNo1(){ return checkGet(mergeDivision1CaseNo1); }
public void setMergeDivision1CaseNo1(String s){ mergeDivision1CaseNo1=checkSet(s); }
public String getMergeDivision1KindNo(){ return checkGet(mergeDivision1KindNo); }
public void setMergeDivision1KindNo(String s){ mergeDivision1KindNo=checkSet(s); }
public String getLandCaseNo(){ return checkGet(landCaseNo); }
public void setLandCaseNo(String s){ landCaseNo=checkSet(s); }
public String getReduceDetailEnterOrg(){ return checkGet(reduceDetailEnterOrg); }
public void setReduceDetailEnterOrg(String s){ reduceDetailEnterOrg=checkSet(s); }
public String getReduceDetailOwnership(){ return checkGet(reduceDetailOwnership); }
public void setReduceDetailOwnership(String s){ reduceDetailOwnership=checkSet(s); }
public String getReduceDetailCaseNo(){ return checkGet(reduceDetailCaseNo); }
public void setReduceDetailCaseNo(String s){ reduceDetailCaseNo=checkSet(s); }
public String getMergeDivisionEnterDate(){ return checkGet(mergeDivisionEnterDate); }
public void setMergeDivisionEnterDate(String s){ mergeDivisionEnterDate=checkSet(s); }
public String getMergeDivisionCause(){ return checkGet(mergeDivisionCause); }
public void setMergeDivisionCause(String s){ mergeDivisionCause=checkSet(s); }
public String getMergeDivisionCause1(){ return checkGet(mergeDivisionCause1); }
public void setMergeDivisionCause1(String s){ mergeDivisionCause1=checkSet(s); }
public String getCheckEnterOrg(){ return checkGet(checkEnterOrg); }
public void setCheckEnterOrg(String s){ checkEnterOrg=checkSet(s); }
public String getVerifyError(){ return checkGet(verifyError); }
public void setVerifyError(String s){ verifyError=checkSet(s); }

//傳回執行insert前之檢查sql
protected String[][] getInsertCheckSQL(){
	String[][] checkSQLArray = new String[1][4];
 	checkSQLArray[0][0]=" select count(*) as checkResult from UNTLA_MergeDivision where 1=1 " + 
		" and enterOrg = " + Common.sqlChar(enterOrg) + 
		" and ownership = " + Common.sqlChar(ownership) + 
		" and caseNo = " + Common.sqlChar(caseNo) + 
		"";
	checkSQLArray[0][1]=">";
	checkSQLArray[0][2]="0";
	checkSQLArray[0][3]="該筆資料己重複，請重新輸入！";
	return checkSQLArray;
}


//傳回insert sql
protected String[] getInsertSQL(){
	
	//取得電腦單號
	Database db = new Database();
	ResultSet rs, rs_1;	
	String checkCaseNo="";
	String checkCaseNo_1="";
	String[] execSQLArray = new String[1];
	try {	
		String sql="select (max(caseNo) + 1) as caseNo from UNTLA_MergeDivision " +
			" where enterOrg = " + Common.sqlChar(enterOrg) +
			" and ownership = " + Common.sqlChar(ownership) +
			" and caseNo like " + Common.sqlChar(Datetime.getYYYMMDD() + "%") + 
			"";	
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
		
		setCaseNo(getEditDate()+checkCaseNo);
		
		if(enterOrg.equals("000000002Z")){
			String[] execSQLArray_1 = new String[1];
			String sql_1="select (max(caseNo) + 1) as caseNo from NPBGR_Change " +
				" where caseNo like  " + Common.sqlChar(Datetime.getYYYMMDD() + "%") + 
				"";		
			rs_1 = db.querySQL(sql_1);

			if (rs_1.next()){
				if (rs_1.getString("caseNo")==null){
					checkCaseNo_1="001";
				}else{
				    checkCaseNo_1=rs_1.getString("caseNo");
				}
			}else{
				checkCaseNo_1="001";
			}
			if (Integer.parseInt(checkCaseNo)>Integer.parseInt(checkCaseNo_1)){
				setCaseNo(getEditDate()+checkCaseNo);
			}else{
				setCaseNo(getEditDate()+checkCaseNo_1);
			}
			
			execSQLArray_1[0]=" insert into npbgr_change(" +
				" enterOrg," +
				" ownership," +
				" caseNo," +
				" caseNo2," +
				" propertyNo,"+
				" serialNo,"+
				" propertyType," +
				" applyDate," +
				" caseType," +
				" signNo," +
				" applyReason," +
				" editID," +
				" editDate," +
				" editTime " +
			" )Values(" +
				Common.sqlChar(enterOrg) + "," +
				Common.sqlChar(ownership) + "," +
				Common.sqlChar(caseNo) + "," +
				Common.sqlChar(caseNo) + "," +
				Common.sqlChar("") + "," +
				Common.sqlChar("") + "," +
				Common.sqlChar("") + "," +
				Common.sqlChar("") + "," +
				Common.sqlChar("") + "," +			
				//設定caseType=5為分割
				Common.sqlChar("") + "," +
				Common.sqlChar("") + "," +
				Common.sqlChar(getEditID()) + "," +
				Common.sqlChar(getEditDate()) + "," +
				Common.sqlChar(getEditTime()) + ")" ;
			
			db.excuteSQL(execSQLArray_1);	
		}

		execSQLArray[0]=" insert into UNTLA_MergeDivision(" +
			" enterOrg,"+
			" ownership,"+
			" caseNo,"+
			" caseName,"+
			" cause,"+
			" cause1,"+
			" enterDate,"+
			" approveOrg,"+
			" approveDate,"+
			" approveDoc,"+
			" mergeReduce,"+
			" mergeAdd,"+
			" divisionReduce,"+
			" divisionAdd,"+
			" verify,"+
			" closing,"+
			" notes,"+
			" editID,"+
			" editDate,"+
			" editTime"+
		" )Values(" +
			Common.sqlChar(enterOrg) + "," +
			Common.sqlChar(ownership) + "," +
			Common.sqlChar(caseNo) + "," +
			Common.sqlChar(caseName) + "," +
			Common.sqlChar(cause) + "," +
			Common.sqlChar(cause1) + "," +
			Common.sqlChar(enterDate) + "," +
			Common.sqlChar(approveOrg) + "," +
			Common.sqlChar(approveDate) + "," +
			Common.sqlChar(approveDoc) + "," +
			Common.sqlChar(mergeReduce) + "," +
			Common.sqlChar(mergeAdd) + "," +
			Common.sqlChar(divisionReduce) + "," +
			Common.sqlChar(divisionAdd) + "," +
			Common.sqlChar(verify) + "," +
			Common.sqlChar(closing) + "," +
			Common.sqlChar(notes) + "," +
			Common.sqlChar(getEditID()) + "," +
			Common.sqlChar(getEditDate()) + "," +
			Common.sqlChar(getEditTime()) + ")" ;
		
	} catch (Exception e) {
		e.printStackTrace();
	} finally {
		db.closeAll();
	}
	
	return execSQLArray;
	

}


//傳回update sql
public void update(){
    Database db = new Database();    
    String[] execSQLArray = null;
	setEditDate(Datetime.getYYYMMDD());
	setEditTime(Datetime.getHHMMSS());
    String strSQL ="";
    try{
		//更新土地合併分割案件檔
	    strSQL+=" update UNTLA_MergeDivision set " + "\n" +
				" caseName = " + Common.sqlChar(caseName) + "," + "\n" +
				" cause = " + Common.sqlChar(cause) + "," + "\n" +
				" cause1 = " + Common.sqlChar(cause1) + "," + "\n" +
				" enterDate = " + Common.sqlChar(enterDate) + "," + "\n" +
				" approveOrg = " + Common.sqlChar(approveOrg) + "," + "\n" +
				" approveDate = " + Common.sqlChar(approveDate) + "," + "\n" +
				" approveDoc = " + Common.sqlChar(approveDoc) + "," + "\n" +
				" mergeReduce = " + Common.sqlChar(mergeReduce) + "," + "\n" +
				" mergeAdd = " + Common.sqlChar(mergeAdd) + "," + "\n" +
				" divisionReduce = " + Common.sqlChar(divisionReduce) + "," + "\n" +
				" divisionAdd = " + Common.sqlChar(divisionAdd) + "," + "\n" +
				" verify = " + Common.sqlChar(verify) + "," + "\n" +
				" closing = " + Common.sqlChar(closing) + "," + "\n" +
				" notes = " + Common.sqlChar(notes) + "," + "\n" +
				" editID = " + Common.sqlChar(getEditID()) + "," + "\n" +
				" editDate = " + Common.sqlChar(getEditDate()) + "," + "\n" +
				" editTime = " + Common.sqlChar(getEditTime()) +  "\n" +
			" where 1=1 " +  "\n" +
				" and enterOrg = " + Common.sqlChar(enterOrg) + "\n" +
				" and ownership = " + Common.sqlChar(ownership) + "\n" +
				" and caseNo = " + Common.sqlChar(caseNo) + "\n" +
				":;:";
		
		//更新土地減損單
	    strSQL+=" update UNTLA_ReduceProof set " + "\n" +
				" caseName = " + Common.sqlChar(caseName) + "," + "\n" +		
				" reduceDate = " + Common.sqlChar(enterDate) + "," + "\n" +
				" approveOrg = " + Common.sqlChar(approveOrg) + "," + "\n" +
				" approveDate = " + Common.sqlChar(approveDate) + "," + "\n" +
				" approveDoc = " + Common.sqlChar(approveDoc) + "," + "\n" +
				" verify = " + Common.sqlChar(verify) + "," + "\n" +
				" closing = " + Common.sqlChar(closing) + "," + "\n" +
				" notes = " + Common.sqlChar(notes) +  "\n" +
			" where 1=1 " +  "\n" +
				" and enterOrg = " + Common.sqlChar(enterOrg) + "\n" +
				" and ownership = " + Common.sqlChar(ownership) + "\n" +
				" and caseNo in (" + Common.sqlChar(mergeReduce) + ","  + Common.sqlChar(divisionReduce) + ")" + "\n" +
				":;:";	
		
		//更新土地減損單明細
	    strSQL+=" update UNTLA_ReduceDetail set " + "\n" +
				" cause = " + Common.sqlChar(cause) + "," + "\n" +
				" cause1 = " + Common.sqlChar(cause1) + "," + "\n" +
				" reduceDate = " + Common.sqlChar(enterDate) + "," + "\n" +
				" verify = " + Common.sqlChar(verify) + "," + "\n" +
				" closing = " + Common.sqlChar(closing) + "," + "\n" +
				" notes = " + Common.sqlChar(notes) +  "\n" +
			" where 1=1 " +  "\n" +
				" and enterOrg = " + Common.sqlChar(enterOrg) + "\n" +
				" and ownership = " + Common.sqlChar(ownership) + "\n" +
				" and caseNo in (" + Common.sqlChar(mergeReduce) + ","  + Common.sqlChar(divisionReduce) + ")" + "\n" +
				":;:";	
		
		//更新土地增加單
	    strSQL+=" update UNTLA_AddProof set " + "\n" +
				" caseName = " + Common.sqlChar(caseName) + "," + "\n" +		
				" enterDate = " + Common.sqlChar(enterDate) + "," + "\n" +
				" verify = " + Common.sqlChar(verify) + "," + "\n" +
				" closing = " + Common.sqlChar(closing) + "," + "\n" +
				" notes = " + Common.sqlChar(notes) +  "\n" +
			" where 1=1 " +  "\n" +
				" and enterOrg = " + Common.sqlChar(enterOrg) + "\n" +
				" and ownership = " + Common.sqlChar(ownership) + "\n" +
				" and caseNo in (" + Common.sqlChar(mergeAdd) + ","  + Common.sqlChar(divisionAdd) + ")" + "\n" +
				":;:";	
		//更新土地主檔
	    strSQL+=" update UNTLA_Land set " + "\n" +
				" cause = " + Common.sqlChar(cause) + "," + "\n" +
				" cause1 = " + Common.sqlChar(cause1) + "," + "\n" +
				" enterDate = " + Common.sqlChar(enterDate) + "," + "\n" +
				" closing = " + Common.sqlChar(closing) + "," + "\n" +
				" verify = " + Common.sqlChar(verify) + "\n" +
			" where 1=1 " +  "\n" +
				" and enterOrg = " + Common.sqlChar(enterOrg) + "\n" +
				" and ownership = " + Common.sqlChar(ownership) + "\n" +
				" and caseNo in (" + Common.sqlChar(mergeAdd) + ","  + Common.sqlChar(divisionAdd) + ")" + "\n" +
				":;:";	
	    
//	    if(!"000000002Z".equals(enterOrg)){
	    	//更新資料為歷史資料
	    String sqlup_1= " select a.enterOrg, a.ownership, a.caseNo, a.propertyNo, a.serialNo, a.signNo," +
			" a.propertyNo " +
			" from UNTLA_ReduceDetail a" +
			" where 1=1" +
			" and enterOrg = " + Common.sqlChar(enterOrg) + 
			" and ownership = " + Common.sqlChar(ownership) +
			" and caseNo in (" + Common.sqlChar(mergeReduce) + ","  + Common.sqlChar(divisionReduce) + ")" +
			"";

			ResultSet rsup_1 = db.querySQL(sqlup_1);
			while (rsup_1.next()){	
				
				//修改isdefault為"0"
				String sqlup_2= " select a.serialNo1 " +
					" from UNTLA_Manage a" +
					" where 1=1" +
					" and a.enterOrg = " + Common.sqlChar(rsup_1.getString("enterOrg")) + 
					" and a.ownership = " + Common.sqlChar(rsup_1.getString("ownership")) + 
					" and a.propertyNo = " + Common.sqlChar(rsup_1.getString("propertyNo")) + 
					" and a.serialNo = " + Common.sqlChar(rsup_1.getString("serialNo")) + 
					" and a.useArea <> '0' " + 
					"";
		
				ResultSet rsup_2 = db.querySQL(sqlup_2);
				while (rsup_2.next()){
					strSQL +=" update UNTLA_Manage set isdefault='0' " +
							" where enterOrg=" + Common.sqlChar(rsup_1.getString("enterOrg")) + 
							" and ownership = " + Common.sqlChar(rsup_1.getString("ownership")) + 
							" and propertyNo = " + Common.sqlChar(rsup_1.getString("propertyNo")) + 
							" and serialNo = " + Common.sqlChar(rsup_1.getString("serialNo")) + 
							" and serialNo1 = " + Common.sqlChar(rsup_2.getString("serialNo1")) + 
							":;:";				
				}
			}
//	    }
			if("000000002Z".equals(enterOrg)){
				String sql_v=" select enterOrg, ownership, propertyNo, serialNo from untla_land where 1=1 "+
					" and caseNo="+Common.sqlChar(caseNo)+
					"";

				ResultSet rs_v = db.querySQL(sql_v);
				while (rs_v.next()) {
					strSQL += "update untla_land set "+
					" verify = 'Y'," +
					" editID = " + Common.sqlChar(getEditID()) + "," +
					" editDate = " + Common.sqlChar(getEditDate()) + "," +
					" editTime = " + Common.sqlChar(getEditTime()) + 	                
					" where 1=1 " + 
					" and enterOrg = " + Common.sqlChar(rs_v.getString("enterOrg")) +
					" and ownership = " + Common.sqlChar(rs_v.getString("ownership")) +
					" and propertyNo = " + Common.sqlChar(rs_v.getString("propertyNo")) +
					" and serialNo = " + Common.sqlChar(rs_v.getString("serialNo")) +
					":;:";	
				}
			}	
    
		if(verify.equals("Y")){
			setMergeDivisionEnterDate(enterDate);
			setMergeDivisionCause(cause);
			setMergeDivisionCause1(cause1);
		    strSQL += checkVerify();
		}
	    if (!"Y".equals(getVerifyError())) {
			execSQLArray = strSQL.split(":;:");
			db.excuteSQL(execSQLArray);
			setStateUpdateSuccess();
			setErrorMsg("修改完成");				
		} else {			   
	       setStateUpdateSuccess();
	       queryOne();
		}
	} catch (Exception e) {
		e.printStackTrace();
	} finally {
		db.closeAll();
	}	
}


//傳回delete sql
protected String[] getDeleteSQL(){
	String[] execSQLArray = new String[13];
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
			" and b.caseNo in( " + Common.sqlChar(mergeAdd) + "," + Common.sqlChar(divisionAdd) + ") " +
			" ) " +
			"";	
	}

	//刪除土地增加單
	execSQLArray[7]=" delete UNTLA_AddProof where 1=1 " +
			" and enterOrg = " + Common.sqlChar(enterOrg) +
			" and ownership = " + Common.sqlChar(ownership) +
			" and caseNo in( " + Common.sqlChar(mergeAdd) + "," + Common.sqlChar(divisionAdd) + ") " +
			"";

	//刪除土地主檔(刪除此檔前需先刪除其相關的明細檔，如土地管理檔...等)
	execSQLArray[8]=" delete UNTLA_Land where 1=1 " +
			" and enterOrg = " + Common.sqlChar(enterOrg) +
			" and ownership = " + Common.sqlChar(ownership) +
			" and caseNo in( " + Common.sqlChar(mergeAdd) + "," + Common.sqlChar(divisionAdd) + ") " +
			"";	
	
	//刪除土地減損單
	execSQLArray[9]=" delete UNTLA_ReduceProof where 1=1 " +
			" and enterOrg = " + Common.sqlChar(enterOrg) +
			" and ownership = " + Common.sqlChar(ownership) +
			" and caseNo in( " + Common.sqlChar(mergeReduce) + "," + Common.sqlChar(divisionReduce) + ") " +  
			"";
	
	//刪除土地減損單明細檔
	execSQLArray[10]=" delete UNTLA_ReduceDetail where 1=1 " +
			" and enterOrg = " + Common.sqlChar(enterOrg) +
			" and ownership = " + Common.sqlChar(ownership) +
			" and caseNo in( " + Common.sqlChar(mergeReduce) + "," + Common.sqlChar(divisionReduce) + ") " +
			"";
	
	
	//刪除合併分割案件檔
	execSQLArray[11]=" delete UNTLA_MergeDivision where 1=1 " +
			" and enterOrg = " + Common.sqlChar(enterOrg) +
			" and ownership = " + Common.sqlChar(ownership) +
			" and caseNo = " + Common.sqlChar(caseNo) +
			"";
	
	//刪除合併分割案號
	execSQLArray[12]=" delete npbgr_change where 1=1 " +
			" and enterOrg = " + Common.sqlChar(enterOrg) +
			" and ownership = " + Common.sqlChar(ownership) +
			" and caseNo = " + Common.sqlChar(caseNo) +
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

public UNTLA027F  queryOne() throws Exception{
	Database db = new Database();
	UNTLA027F obj = this;
	try {
		String sql=" select c.checkApprove, a.enterOrg, (select b.organSName from SYSCA_Organ b where b.organID = a.enterOrg ) organSName, " +
				" a.ownership, a.caseNo, a.caseName, a.cause, a.cause1, a.enterDate, a.approveOrg, a.approveDate, a.approveDoc, a.mergeReduce, a.mergeAdd, a.divisionReduce, a.divisionAdd, a.verify, a.closing, a.notes, a.editID, a.editDate, a.editTime, a.editID, a.editDate, a.editTime  "+
			" from UNTLA_MergeDivision a, npbgr_change c where 1=1 " +
			" and a.caseNo = c.caseNo " +
			" and a.enterOrg = " + Common.sqlChar(enterOrg) +
			" and a.ownership = " + Common.sqlChar(ownership) +
			" and a.caseNo = " + Common.sqlChar(caseNo) +
			"";
//System.out.println("UNTLA027F_queryOne==>"+sql);
		ResultSet rs = db.querySQL(sql);
		if (rs.next()){
			obj.setEnterOrg(rs.getString("enterOrg"));
			obj.setEnterOrgName(rs.getString("organSName"));
			obj.setOwnership(rs.getString("ownership"));
			obj.setCaseNo(rs.getString("caseNo"));
			obj.setCaseName(rs.getString("caseName"));
			obj.setCause(rs.getString("cause"));
			obj.setCause1(rs.getString("cause1"));
			obj.setEnterDate(rs.getString("enterDate"));
			obj.setApproveOrg(rs.getString("approveOrg"));
			obj.setApproveDate(rs.getString("approveDate"));
			obj.setApproveDoc(rs.getString("approveDoc"));
			obj.setMergeReduce(rs.getString("mergeReduce"));
			obj.setReduceCaseNo(rs.getString("mergeReduce"));
			obj.setMergeAdd(rs.getString("mergeAdd"));
			obj.setAddCaseNo(rs.getString("mergeAdd"));
			obj.setDivisionReduce(rs.getString("divisionReduce"));
			obj.setReduceCaseNo1(rs.getString("divisionReduce"));
			obj.setDivisionAdd(rs.getString("divisionAdd"));
			obj.setAddCaseNo1(rs.getString("divisionAdd"));
			obj.setVerify(rs.getString("verify"));
			obj.setClosing(rs.getString("closing"));
			obj.setOldVerify(rs.getString("verify"));
			obj.setCheckApprove(rs.getString("checkApprove"));
			obj.setNotes(rs.getString("notes"));
			obj.setEditID(rs.getString("editID"));
			obj.setEditDate(rs.getString("editDate"));
			obj.setEditTime(rs.getString("editTime"));
			obj.setMergeDivision(rs.getString("caseNo"));
			obj.setMergeDivisionDate(rs.getString("enterDate"));
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
		String sql=" select a.enterOrg, (select organSName from SYSCA_Organ b where b.organID=a.enterOrg ) organSName, " +
				" a.ownership, " +
				" (case a.ownership when '2' then '國有' when '3' then '縣有' when '5' then '鄉鎮市' end) as ownershipName, " +
				" a.caseNo, a.caseName, " +
				" a.cause, (select codeName from SYSCA_Code c where codeKindID='CAA' and codeCon1='4' and c.codeID=a.cause) causeName, " +
				" a.enterDate, a.cause1, " +
				" (case a.verify when 'Y' then '是' when 'N' then '否' else '' end) as verify, " +
				" a.mergeReduce, a.mergeAdd, a.divisionReduce, a.divisionAdd " +
				" from UNTLA_MergeDivision a where 1=1 "; 
		if ("".equals(getQ_checkQuery())) {
			sql+=" and a.enterOrg = " + Common.sqlChar(getEnterOrg()) ;
			sql+=" and a.ownership = " + Common.sqlChar(getOwnership()) ;
			sql+=" and a.caseNo=" + Common.sqlChar(getCaseNo());
		} else {
			if (!"".equals(getQ_enterOrg())) {
				sql+=" and a.enterOrg = " + Common.sqlChar(getQ_enterOrg()) ;
			} else {
				if (!getIsAdminManager().equalsIgnoreCase("Y")) {
					if ("Y".equals(getIsOrganManager())) {					
						sql += " and a.enterOrg like '" + getOrganID().substring(0,5) + "%' ";					
					} else {
						sql+=" and a.enterOrg = " + Common.sqlChar(getOrganID()) ;
					}
				}
			}			if (!"".equals(getQ_ownership()))
				sql+=" and a.ownership = " + Common.sqlChar(getQ_ownership()) ;
		    if (!"".equals(getQ_caseNoS()))
		    	sql+=" and a.caseNo   >= rpad(" + Common.sqlChar(getQ_caseNoS()) + ",10,'0')";
		    if (!"".equals(getQ_caseNoE()))
		    	sql+=" and a.caseNo   <= rpad(" + Common.sqlChar(getQ_caseNoE()) + ",10,'9')";
			if (!"".equals(getQ_caseName()))
				sql+=" and a.caseName like " + Common.sqlChar("%"+getQ_caseName()+"%") ;
			if (!"".equals(getQ_cause()))
				sql+=" and a.cause = " + Common.sqlChar(getQ_cause()) ;
		    if (!"".equals(getQ_enterDateS()))
		    	sql+=" and a.enterDate >= " + Common.sqlChar(new Datetime().changeTaiwanYYMMDD(getQ_enterDateS(),"2")) ;
		    if (!"".equals(getQ_enterDateE()))
		    	sql+=" and a.enterDate <= " + Common.sqlChar(new Datetime().changeTaiwanYYMMDD(getQ_enterDateE(),"2")) ;
			if (!"".equals(getQ_approveOrg()))
				sql+=" and a.approveOrg = " + Common.sqlChar(getQ_approveOrg()) ;
			if (!"".equals(getQ_verify()))
				sql+=" and a.verify = " + Common.sqlChar(getQ_verify()) ;
		}
			sql+=" order by a.enterOrg, a.ownership, a.caseNo ";
//System.out.println("all: "+sql);
		ResultSet rs = db.querySQL(sql);
		while (rs.next()){
			counter++;
			String rowArray[]=new String[14];
			rowArray[0]=rs.getString("enterOrg"); 
			rowArray[1]=rs.getString("ownership");
			rowArray[2]=rs.getString("ownershipName"); 
			rowArray[3]=rs.getString("caseNo"); 
			rowArray[4]=rs.getString("caseName"); 
			rowArray[5]=rs.getString("cause");
			rowArray[6]=rs.getString("causeName");
			rowArray[7]=rs.getString("enterDate"); 
			rowArray[8]=rs.getString("verify");
			rowArray[9]=rs.getString("cause1");
			rowArray[10]=rs.getString("mergeReduce");
			rowArray[11]=rs.getString("mergeAdd");
			rowArray[12]=rs.getString("divisionReduce");
			rowArray[13]=rs.getString("divisionAdd");
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

//取得最大的月結年月
protected String getMaxClosingYM(){
	Database db = new Database();
	ResultSet rs;	
	String closingYM ="" ;
	String sql="select max(closingYM) as closingYM from UNTGR_Closing " +
		" where enterOrg = " + Common.sqlChar(enterOrg) +
		"";		
	try {		
		rs = db.querySQL(sql);
		if (rs.next()){
		    closingYM = rs.getString("closingYM")==null?"0":rs.getString("closingYM");
		}
	} catch (Exception e) {
		e.printStackTrace();
	} finally {
		db.closeAll();
	}
	return closingYM;
}

//全部審核
public void approveAll()throws Exception{	

	Database db = new Database();
	try {    
	    int i = 0,counter = 0;
		String rowArray[]=new String[9];
		java.util.Iterator it= queryAll().iterator();
		String[] execSQLArray;
		String strSQL = "";
		while(it.hasNext()){
		    counter++;
			rowArray= (String[])it.next();
			if(rowArray[8].equals("否")){
				i++;
				setVerify("Y");
				setOldVerify("N");
				enterOrg = rowArray[0];
				ownership= rowArray[1];
				caseNo=rowArray[3];
				//setMergeDivisionEnterDate(rowArray[7]);
				setMergeDivisionEnterDate(rowArray[7]==null || "".equals(rowArray[7]) ? Datetime.getYYYMMDD():rowArray[7]);
				setMergeDivisionCause(rowArray[5]);
				setMergeDivisionCause1(rowArray[9]);
				mergeReduce=rowArray[10];
				mergeAdd=rowArray[11];
				divisionReduce=rowArray[12];
				divisionAdd=rowArray[13];
				setEditID(getUserID());
				setEditDate(Datetime.getYYYMMDD());
				setEditTime(Datetime.getHHMMSS());	
				if(enterOrg.equals(checkEnterOrg)){
					strSQL += "update UNTLA_MergeDivision set "+
							" verify = 'Y'," +
							" editID = " + Common.sqlChar(getEditID()) + "," +
							" editDate = " + Common.sqlChar(getEditDate()) + "," +
							" editTime = " + Common.sqlChar(getEditTime()) + 	                
							" where 1=1 " + 
							" and enterOrg = " + Common.sqlChar(enterOrg) +
							" and ownership = " + Common.sqlChar(ownership) +
							" and caseNo = " + Common.sqlChar(caseNo) +
							":;:";	
					//if(!"000000002Z".equals(enterOrg)){
						//更新資料為歷史資料
					    String sqlup_1= " select a.enterOrg, a.ownership, a.caseNo, a.propertyNo, a.serialNo, a.signNo," +
							" a.propertyNo " +
							" from UNTLA_ReduceDetail a" +
							" where 1=1" +
							" and a.caseNo=" + Common.sqlChar(caseNo) + 
							"";				
						ResultSet rsup_1 = db.querySQL(sqlup_1);
						while (rsup_1.next()){	
							
							//修改isdefault為"0"
							String sqlup_2= " select a.serialNo1 " +
								" from UNTLA_Manage a" +
								" where 1=1" +
								" and a.enterOrg = " + Common.sqlChar(rsup_1.getString("enterOrg")) + 
								" and a.ownership = " + Common.sqlChar(rsup_1.getString("ownership")) + 
								" and a.propertyNo = " + Common.sqlChar(rsup_1.getString("propertyNo")) + 
								" and a.serialNo = " + Common.sqlChar(rsup_1.getString("serialNo")) + 
								" and a.useArea <> '0' " + 
								"";
						
							ResultSet rsup_2 = db.querySQL(sqlup_2);
							while (rsup_2.next()){
								strSQL +=" update UNTLA_Manage set isdefault='0' " +
										" where enterOrg=" + Common.sqlChar(rsup_1.getString("enterOrg")) + 
										" and ownership = " + Common.sqlChar(rsup_1.getString("ownership")) + 
										" and propertyNo = " + Common.sqlChar(rsup_1.getString("propertyNo")) + 
										" and serialNo = " + Common.sqlChar(rsup_1.getString("serialNo")) + 
										" and serialNo1 = " + Common.sqlChar(rsup_2.getString("serialNo1")) + 
										":;:";				
							}
						}
						if("000000002Z".equals(enterOrg)){
							String sql_v=" select enterOrg, ownership, propertyNo, serialNo from untla_land where 1=1 "+
								" and caseNo="+Common.sqlChar(caseNo)+
								"";
		
							ResultSet rs_v = db.querySQL(sql_v);
							while (rs_v.next()) {
								strSQL += "update untla_land set "+
								" verify = 'Y'," +
								" editID = " + Common.sqlChar(getEditID()) + "," +
								" editDate = " + Common.sqlChar(getEditDate()) + "," +
								" editTime = " + Common.sqlChar(getEditTime()) + 	                
								" where 1=1 " + 
								" and enterOrg = " + Common.sqlChar(rs_v.getString("enterOrg")) +
								" and ownership = " + Common.sqlChar(rs_v.getString("ownership")) +
								" and propertyNo = " + Common.sqlChar(rs_v.getString("propertyNo")) +
								" and serialNo = " + Common.sqlChar(rs_v.getString("serialNo")) +
								":;:";	
							}
						}
					//}
				
					strSQL+= checkVerify();
					if (!super.beforeExecCheck(this.getUpdateCheckSQL())){
				           setVerifyError("Y");
				           //setStateUpdateError();
				           queryOne();
				           break;
					}
					if ("Y".equals(getVerifyError())) {
					    break;
					}
				}
			}
			if (counter==getListLimit()){
				setErrorMsg(getListLimitError());
				break;
			}						
		}
		if (i>0) {
			if (!"Y".equals(getVerifyError())) {
				execSQLArray = strSQL.split(":;:");
				db.excuteSQL(execSQLArray);
				setStateUpdateSuccess();
				setErrorMsg("全部入帳完成");				
			} else {			   
	           setStateUpdateSuccess();
	           queryOne();
			}
		}			
	} catch (Exception e) {
		e.printStackTrace();
	} finally {
		db.closeAll();
	}		
}


//審核設定
protected String checkVerify(){
  String sql="";
  setEditTime(Datetime.getHHMMSS());

  if("000000002Z".equals(enterOrg)){
	  if(!"Y".equals(View.getLookupField("select approveYN from npbgr_change where 1=1 and caseNo = " + Common.sqlChar(caseNo)))){
			 setVerifyError("Y");
			 setErrorMsg("該筆合併分割案尚未通過土地異動審核！");
	  }
  }
	  //將「審核」由「N:否」改為「Y:是」
		if(getVerify().equals("Y")){
		  	if(Integer.parseInt(View.getLookupField("select count(*) from UNTLA_MergeDivision where 1=1 and enterOrg = " + Common.sqlChar(enterOrg) +" and ownership = " + Common.sqlChar(ownership) +" and caseNo = " + Common.sqlChar(caseNo) + "and ((mergeReduce is not null and mergeAdd is not null) or (divisionReduce is not null and divisionAdd is not null))"))<1){
	            setVerifyError("Y");
	            setErrorMsg("該筆合併分割案之(合併減損單、合併增加單)或(分割減損單、分割增加單)，要有資料才能做此入帳設定！");
		  	}else{
				if(Integer.parseInt(getMaxClosingYM()) < Integer.parseInt(getMergeDivisionEnterDate().substring(0,5))){
					//修改UNTLA_ReduceProof資料
			        sql = "update UNTLA_ReduceProof set"+
					        " verify = 'Y'," +
					        " reduceDate = " + Common.sqlChar(getMergeDivisionEnterDate()) + "," +
					        " editID = " + Common.sqlChar(getEditID()) + "," +
					        " editDate = " + Common.sqlChar(getEditDate()) + "," +
					        " editTime = " + Common.sqlChar(getEditTime()) + 	                
					        " where 1=1 " + 
					        " and enterOrg = " + Common.sqlChar(enterOrg) +
					        " and ownership = " + Common.sqlChar(ownership) +
					        " and caseNo in (" + Common.sqlChar(mergeReduce) + ","  + Common.sqlChar(divisionReduce) + ")" +
					        ":;:";
			        //修改UNTLA_ReduceDetail資料
			        sql += updateReduceDetail();
			        //修改UNTLA_AddProof資料
			        sql += "update UNTLA_AddProof set"+
					        " verify = 'Y'," +
					        " closing = 'Y'," +
					        " enterDate = " + Common.sqlChar(getMergeDivisionEnterDate()) + "," +
					        " editID = " + Common.sqlChar(getEditID()) + "," +
					        " editDate = " + Common.sqlChar(getEditDate()) + "," +
					        " editTime = " + Common.sqlChar(getEditTime()) + 	                
					        " where 1=1 " + 
					        " and enterOrg = " + Common.sqlChar(enterOrg) +
					        " and ownership = " + Common.sqlChar(ownership) +
					        " and mergeDivision = " + Common.sqlChar(caseNo) +
					        ":;:";
			        //新增UNTLA_MergeDivision1資料
			        sql += insertMergeDivision1();
			    }else{
			    	setVerifyError("Y");
			        setStateUpdateError();
			        setErrorMsg("入帳設定有錯，合併分割日期年月必須大於月結年月！");
			    }
	  		}
		}
//System.out.println("全:\n"+sql);
	return sql;
}

//修改UNTLA_ReduceDetail資料
protected String updateReduceDetail(){
	String sql="";
	Database db = new Database();
		String strSQL ="select a.enterOrg, a.ownership, a.caseNo " +
						" from UNTLA_ReduceProof a where 1=1 " +
						" and enterOrg = " + Common.sqlChar(enterOrg) + 
						" and ownership = " + Common.sqlChar(ownership) + 
						" and caseNo in (" + Common.sqlChar(mergeReduce) + ","  + Common.sqlChar(divisionReduce) + ")" + 
						"";		
	try {	
//System.out.println(strSQL);
		ResultSet rs = db.querySQL(strSQL);
		while (rs.next()){
			setReduceDetailEnterOrg(rs.getString("enterOrg"));
			setReduceDetailOwnership(rs.getString("ownership"));
			setReduceDetailCaseNo(rs.getString("caseNo"));
			sql+= "update UNTLA_ReduceDetail set "+
				" verify = 'Y'," +
				" reduceDate = " + Common.sqlChar(getMergeDivisionEnterDate()) +
				" where 1=1 " + 
				" and enterOrg = " + Common.sqlChar(rs.getString("enterOrg")) +
				" and ownership = " + Common.sqlChar(rs.getString("ownership")) +
				" and caseNo = " + Common.sqlChar(rs.getString("caseNo")) +
				":;:";	
			//修改UNTLA_Land資料
			sql+= updateLand();
		}
	} catch (Exception e) {
		e.printStackTrace();
	} finally {
		db.closeAll();
	}
//System.out.println("修改UNTLA_ReduceDetail資料:\n"+sql);	
	return sql;
}

//修改UNTLA_Land資料
protected String updateLand(){
	String sql="";
	Database db = new Database();
		String strSQL ="(select distinct d.enterOrg, d.ownership, d.propertyNo, d.serialNo  from UNTLA_MergeDivision c, " +
						" UNTLA_ReduceDetail d "+
						" where 1=1 "+ 
						" and c.enterOrg=d.enterOrg and c.ownership=d.ownership and c.mergeReduce=d.caseNo  "+
						" and c.enterOrg = " + Common.sqlChar(getReduceDetailEnterOrg()) +
						" and c.ownership = " + Common.sqlChar(getReduceDetailOwnership()) + 
						" and c.caseNo = " + Common.sqlChar(caseNo) +
						" )union "+
						" (select distinct e.enterOrg, e.ownership, e.propertyNo, e.serialNo from UNTLA_MergeDivision c, "+ 
						" UNTLA_ReduceDetail e "+
						" where 1=1 "+ 
						" and c.enterOrg=e.enterOrg and c.ownership=e.ownership and c.divisionReduce=e.caseNo  "+
						" and c.enterOrg = " + Common.sqlChar(getReduceDetailEnterOrg()) + 
						" and c.ownership = " + Common.sqlChar(getReduceDetailOwnership()) + 
						" and c.caseNo = " + Common.sqlChar(caseNo) +
						" )";	
	try {	
		ResultSet rs = db.querySQL(strSQL);
		while (rs.next()){
			sql+="update UNTLA_Land set "+
				" verify = 'Y'," +
				" closing = 'Y'," +
				//" enterDate = " + Common.sqlChar(getMergeDivisionEnterDate()) + "," +
				" dataState = '2'," +
				" reduceDate = " + Common.sqlChar(getMergeDivisionEnterDate()) + "," +
				" reduceCause = " + Common.sqlChar(getMergeDivisionCause()) + "," +
				" reduceCause1 = " + Common.sqlChar(getMergeDivisionCause1()) + "," +
				" holdNume = 0 , " +
				" holdArea = 0 , " +
				" bookValue = 0 " +
				" where 1=1 " + 
				" and enterOrg = " + Common.sqlChar(rs.getString("enterOrg")) +
				" and ownership = " + Common.sqlChar(rs.getString("ownership")) +
				" and propertyNo = " + Common.sqlChar(rs.getString("propertyNo")) +
				" and serialNo = " + Common.sqlChar(rs.getString("serialNo")) +
				":;:";	
			//修改UNTLA_Manage資料
			sql+="update UNTLA_Manage set "+
				" useDateE = " + Common.sqlChar(getMergeDivisionEnterDate()) +
				" where 1=1 " + 
				" and enterOrg = " + Common.sqlChar(rs.getString("enterOrg")) +
				" and ownership = " + Common.sqlChar(rs.getString("ownership")) +
				" and propertyNo = " + Common.sqlChar(rs.getString("propertyNo")) +
				" and serialNo = " + Common.sqlChar(rs.getString("serialNo")) +
				" and useDateE is null "+
				":;:";	
		}
	} catch (Exception e) {
		e.printStackTrace();
	} finally {
		db.closeAll();
	}
//System.out.println("修改UNTLA_Land資料:\n"+sql);		
	return sql;
}

//新增UNTLA_MergeDivision1資料
protected String insertMergeDivision1(){
	String sql="";
		if(mergeReduce!=null && !"".equals(mergeReduce)){
			setMergeDivision1KindNo("1");
			setMergeDivision1CaseNo1(mergeReduce);
			setMergeDivision1Table("UNTLA_ReduceProof a, UNTLA_ReduceDetail b ");
			sql+=insertMergeDivision1MergeDivision();
		}
		if(mergeAdd!=null && !"".equals(mergeAdd)){
			setMergeDivision1KindNo("2");
			setMergeDivision1CaseNo1(mergeAdd);
			setMergeDivision1Table("UNTLA_AddProof a, UNTLA_Land b ");
			sql+=insertMergeDivision1MergeDivision();
		}
		if(divisionReduce!=null && !"".equals(divisionReduce)){
			setMergeDivision1KindNo("3");
			setMergeDivision1CaseNo1(divisionReduce);
			setMergeDivision1Table("UNTLA_ReduceProof a, UNTLA_ReduceDetail b ");
			sql+=insertMergeDivision1MergeDivision();
		}
		if(divisionAdd!=null && !"".equals(divisionAdd)){
			setMergeDivision1KindNo("4");
			setMergeDivision1CaseNo1(divisionAdd);
			setMergeDivision1Table("UNTLA_AddProof a, UNTLA_Land b ");
			sql+=insertMergeDivision1MergeDivision();
		}
//System.out.println("新增UNTLA_MergeDivision1資料:\n"+sql);		
	return sql;
}

//新增UNTLA_MergeDivision1資料
protected String insertMergeDivision1MergeDivision(){
	String sql="";
	Database db = new Database();
	ResultSet rs;
	String strSQL;
	String checkReduceDetail="";
	String tableName="";
	if(getMergeDivision1KindNo().equals("1") || getMergeDivision1KindNo().equals("3")){
		tableName="減損單";
	}else if(getMergeDivision1KindNo().equals("2") || getMergeDivision1KindNo().equals("4")){
		tableName="增加單";
	}
	try {	
			strSQL ="select a.enterOrg, a.ownership, a.caseNo, " +
					" a.writeDate, a.proofDoc, a.proofNo, " +
					" b.propertyNo, b.serialNo, b.signNo, b.area, b.holdNume, " +
					" b.holdDeno, b.holdArea, b.bookUnit, b.bookValue, b.useSeparate, " +
					" b.useKind, b.field" ;
			if(getMergeDivision1KindNo().equals("1") || getMergeDivision1KindNo().equals("3")){
				strSQL+= ", b.oldPropertyNo, b.oldSerialNo ";
			}
			strSQL+=" from " + getMergeDivision1Table()+
					" where 1=1 " +
					" and a.enterOrg = " + Common.sqlChar(enterOrg) + 
					" and a.ownership = " + Common.sqlChar(ownership) + 
					" and a.caseNo = " + Common.sqlChar(getMergeDivision1CaseNo1()) +
					" and a.enterOrg=b.enterOrg and a.ownership=b.ownership and a.caseNo=b.caseNo "+
					"";	
			rs = db.querySQL(strSQL);
			while (rs.next()){
				checkReduceDetail="Y";
				sql+= "insert into UNTLA_MergeDivision1(" +
					" enterOrg, " +
					" ownership, " +
					" caseNo, " +
					" cause, " +
					" cause1, " +
					" enterDate, " +
					" kindNo, " +
					" caseNo1, " +
					" writeDate, " +
					" proofDoc, " +
					" proofNo, " +
					" propertyNo, " +
					" serialNo, " +
					" signNo, " +
					" area, " +
					" holdNume, " +
					" holdDeno, " +
					" holdArea, " +
					" bookUnit, " +
					" bookValue, " +
					" useSeparate, " +
					" useKind, " +
					" field, " +
					" oldPropertyNo, " +
					" oldSerialNo, " +
					" editID, " +
					" editDate, " +
					" editTime " +
				" )Values(" +
					Common.sqlChar(enterOrg) + "," +
					Common.sqlChar(ownership) + "," +
					Common.sqlChar(caseNo) + "," +
					Common.sqlChar(getMergeDivisionCause()) + "," +
					Common.sqlChar(getMergeDivisionCause1()) + "," +
					Common.sqlChar(getMergeDivisionEnterDate()) + "," +
					Common.sqlChar(getMergeDivision1KindNo()) + "," +
					Common.sqlChar(rs.getString("caseNo")) + "," +
					Common.sqlChar(rs.getString("writeDate")) + "," +
					Common.sqlChar(rs.getString("proofDoc")) + "," +
					Common.sqlChar(rs.getString("proofNo")) + "," +
					Common.sqlChar(rs.getString("propertyNo")) + "," +
					Common.sqlChar(rs.getString("serialNo")) + "," +
					Common.sqlChar(rs.getString("signNo")) + "," +
					Common.sqlChar(rs.getString("area")) + "," +
					Common.sqlChar(rs.getString("holdNume")) + "," +
					Common.sqlChar(rs.getString("holdDeno")) + "," +
					Common.sqlChar(rs.getString("holdArea")) + "," +
					Common.sqlChar(rs.getString("bookUnit")) + "," +
					Common.sqlChar(rs.getString("bookValue")) + "," +
					Common.sqlChar(rs.getString("useSeparate")) + "," +
					Common.sqlChar(rs.getString("useKind")) + "," +
					Common.sqlChar(rs.getString("field")) + "," +
					Common.sqlChar(getMergeDivision1KindNo().equals("1") || getMergeDivision1KindNo().equals("3")?rs.getString("oldPropertyNo"):"") + "," +
					Common.sqlChar(getMergeDivision1KindNo().equals("1") || getMergeDivision1KindNo().equals("3")?rs.getString("oldSerialNo"):"") + "," +
					Common.sqlChar(getEditID()) + "," +
					Common.sqlChar(getEditDate()) + "," +
					Common.sqlChar(getEditTime()) + ")" +
					":;:";	
			}
	        if(!checkReduceDetail.equals("Y") && (getVerify().equals("Y"))){
	            setVerifyError("Y");
	            setErrorMsg("該筆"+tableName+"之明細資料要有資料才能做此入帳設定！");
	        }
	} catch (Exception e) {
		e.printStackTrace();
	} finally {
		db.closeAll();
	}
	return sql;
}

//新增回復分割異動資料
public String changeDivisionRe(){
	Database db = new Database();
	String showMsg="";
	String[] execSQLArray = new String[3];
	try {	
		String sql_check=" select count(*) as checkResult from npbgr_change where 1=1 " + 
			" and enterOrg = " + Common.sqlChar(enterOrg) + 
			" and ownership = " + Common.sqlChar(ownership) + 
			" and caseNo2 = " + Common.sqlChar(caseNo) + 
			" and checkApprove = 'Y' " + 
			"";		
	
		ResultSet rs_check = db.querySQL(sql_check);
		if (rs_check.next()){
			
			if(rs_check.getInt("checkResult") == 0){
				setStateQueryOneSuccess();
				setErrorMsg("本案尚未做過分割異動 !!");
			}else{

				//回復分割案件
				execSQLArray[0]=" update NPBGR_Change set " +
					" propertyType = " + Common.sqlChar("") + "," +
					" serialNo = " + Common.sqlChar("") + "," +
					" propertyNo = " + Common.sqlChar("") + "," +
					" applyDate = " + Common.sqlChar("") + "," +
					" applyReason = " + Common.sqlChar("") + "," +
					" caseType = " + Common.sqlChar("") + "," +		
					" signNo = " + Common.sqlChar("") + "," +
					" checkApprove = " + Common.sqlChar("N") + "," +
					" editID = " + Common.sqlChar(getEditID()) + "," +
					" editDate = " + Common.sqlChar(getEditDate()) + "," +
					" editTime = " + Common.sqlChar(getEditTime()) + 
				" where 1=1 " + 
					" and caseNo = " + Common.sqlChar(caseNo) +
					"";
				
				
				//刪除分割案件明細
				execSQLArray[1]=" delete npbgr_changedetail where 1=1 " +
						" and caseNo = " + Common.sqlChar(caseNo) +
						"";
				
				//刪除分割案件人檔
				execSQLArray[2]=" delete npbgr_changeperson where 1=1 " +
						" and caseNo = " + Common.sqlChar(caseNo) +
						"";
					
				db.excuteSQL(execSQLArray);	
				setStateUpdateSuccess();
				setErrorMsg("回復分割異動成功 !");
			}
		}	
		queryOne();
	} catch (Exception e) {
		e.printStackTrace();
	} finally {
		db.closeAll();
	}
	return showMsg;
}	


//新增分割異動資料
public String changeDivision(){
	Database db = new Database();
	StringBuffer sbSQL = new StringBuffer();
	int seqNo_check=0;
	int seqNo_1=0;
	int seqNo_2=0;
	String caseNo_check="";
	String showMsg="";
	try {	
		
		String sql_check=" select count(*) as checkResult from npbgr_change where 1=1 " + 
			" and enterOrg = " + Common.sqlChar(enterOrg) + 
			" and ownership = " + Common.sqlChar(ownership) + 
			" and caseNo2 = " + Common.sqlChar(caseNo) + 
			" and checkApprove = 'Y' " + 
			"";		
	
		ResultSet rs_check = db.querySQL(sql_check);
		if (rs_check.next()){
			
			if(rs_check.getInt("checkResult")>0){
				setStateQueryOneSuccess();
				setErrorMsg("本案已做過分割異動 , 請至非公用異動作業查詢相關資料!!");
			}else{				
				String sqlup_1= " select a.enterOrg, a.ownership, a.caseNo, a.propertyNo, a.serialNo, a.signNo," +
					" a.propertyNo " +
					" from UNTLA_ReduceDetail a" +
					" where 1=1" +
					" and a.enterOrg='000000002Z'" +
					" and a.caseNo=" + Common.sqlChar(divisionReduce) + 
					"";
	
				ResultSet rsup_1 = db.querySQL(sqlup_1);
				while (rsup_1.next()){	
					//if("000000002Z".equals(enterOrg)){
						//修改isdefault為"0"
						String sqlup_2= " select a.fakeDivision " +
						" from UNTLA_Manage a" +
						" where 1=1" +
						" and a.enterOrg = " + Common.sqlChar(rsup_1.getString("enterOrg")) + 
						" and a.ownership = " + Common.sqlChar(rsup_1.getString("ownership")) + 
						" and a.propertyNo = " + Common.sqlChar(rsup_1.getString("propertyNo")) + 
						" and a.serialNo = " + Common.sqlChar(rsup_1.getString("serialNo")) + 
						" and a.useArea <> '0' " + 
						"";
					
						ResultSet rsup_2 = db.querySQL(sqlup_2);
						while (rsup_2.next()){	
							sbSQL.append(" update UNTLA_Manage set isdefault='0' where enterOrg=").append( Common.sqlChar(rsup_1.getString("enterOrg"))).append(" and ownership=").append( Common.sqlChar(rsup_1.getString("ownership"))).append(" and propertyNo=").append( Common.sqlChar(rsup_1.getString("propertyNo"))).append(" and serialNo=").append( Common.sqlChar(rsup_1.getString("serialNo"))).append(" and fakeDivision=").append( Common.sqlChar(rsup_2.getString("fakeDivision"))).append( " :;:");
						}
					//}
				
				
					//抓取減損單明細及增加單明細差異
					String sql_diff=" select b.serialno,b.serialno1, b.useUnit, b.useArea, b.useRelation, b.dimArea, b.useDateS, b.useDateE, b.useUnit1," +
						" b.disType, b.disArea1, b.disArea2, b.disDateS, b.disDateE, a.caseNo, a.signNo, b.fakeDivision " +
						" from UNTLA_Land a, UNTLA_Manage b " +
						" where a.enterOrg=b.enterOrg and a.ownership=b.ownership " +
						" and a.propertyNo=b.propertyno and a.serialNo=b.serialno" +
						" and a.enterOrg = " + Common.sqlChar(rsup_1.getString("enterOrg")) + 
						" and a.ownership = " + Common.sqlChar(rsup_1.getString("ownership")) + 
						" and a.propertyNo = " + Common.sqlChar(rsup_1.getString("propertyNo")) + 
						" and b.useArea <> '0' " + 	
						" and a.caseNo=" + Common.sqlChar(divisionAdd) + 
						"";
		
					ResultSet rs_diff = db.querySQL(sql_diff);
					while (rs_diff.next()){
						seqNo_check++;
					
						//抓取修改前資料
						
						String sql_a=" select a.area, a.caseNo, a.signNo" +
						" from UNTLA_ReduceDetail a " +
						" where 1=1 " +
						" and a.caseNo=" + Common.sqlChar(divisionReduce) + 
						"";
												
					ResultSet rs_a = db.querySQL(sql_a);
					String[] execSQLArray_2 = new String[2];
					if (rs_a.next()){
						
						execSQLArray_2[0]=" insert into npbgr_changedetail(" +
							" caseNo," +
							" seqNo," +
							" serialNo1,"+
							" signNo,"+								
							" useUnit," +
							" useArea," +
							" useRelation," +
							" dimArea," +
							" useDateS," +							
							" useDateE," +
							" disType," +
							" disArea1," +
							" disArea2," +
							" disDateS," +								
							" disDateE," +
							" kindno" +
							" )Values(" +				
						Common.sqlChar(caseNo) + "," +
						Common.sqlChar(Common.get(seqNo_check+"")) + "," +
						Common.sqlChar("") + "," +	
						Common.sqlChar(Common.get(Common.get(rs_a.getString("signNo")))) + "," +					
						Common.sqlChar("") + "," +
						Common.sqlChar(Common.get(Common.get(rs_a.getString("area")))) + "," +
						Common.sqlChar("99") + "," +
						Common.sqlChar("") + "," +
						Common.sqlChar("") + "," +							
						Common.sqlChar("") + "," +
						Common.sqlChar("") + "," +
						Common.sqlChar("") + "," +
						Common.sqlChar("") + "," +
						Common.sqlChar("") + "," +							
						Common.sqlChar("") + "," +
						Common.sqlChar("1") + ")" ;
					
					}
					
					execSQLArray_2[1]=" insert into NPBGR_ChangePerson(" +
						" caseNo,"+
						" seqNo,"+
						" kindNo,"+
						" applyType,"+
						" payerYN,"+
						" registryYN,"+
						" applyID,"+
						" seq,"+
						" applyName,"+
						" homeTel,"+
						" mobile,"+
						" liveAdd4,"+
						" editID,"+
						" editDate,"+
						" editTime"+
					" )Values(" +
						Common.sqlChar(Common.get(caseNo)) + "," +
						Common.sqlChar(Common.get(seqNo_check+"")) + "," +
						Common.sqlChar("1") + "," +
						Common.sqlChar("") + "," +				
						Common.sqlChar("") + "," +
						Common.sqlChar("") + "," +
						Common.sqlChar("0000000000") + "," +
						Common.sqlChar("") + "," +
						Common.sqlChar("") + "," +
						Common.sqlChar("") + "," +
						Common.sqlChar("") + "," +
						Common.sqlChar("") + "," +					
						Common.sqlChar(getEditID()) + "," +
						Common.sqlChar(getEditDate()) + "," +
						Common.sqlChar(getEditTime()) + ")" ;
					
					db.excuteSQL(execSQLArray_2);		
					
						//抓取修改後資料
						String sql_b=" select b.serialno1, b.useUnit, b.useArea, b.useRelation, b.dimArea, b.useDateS, b.useDateE, b.useUnit1," +
							" b.disType, b.disArea1, b.disArea2, b.disDateS, b.disDateE, a.caseNo, a.signNo, b.fakeDivision " +				
							" from UNTLA_Land a, UNTLA_Manage b " +
							" where a.enterOrg=b.enterOrg and a.ownership=b.ownership " +
							" and a.propertyNo=b.propertyno and a.serialNo=b.serialno" +
							" and a.enterOrg = " + Common.sqlChar(rsup_1.getString("enterOrg")) + 
							" and a.ownership = " + Common.sqlChar(rsup_1.getString("ownership")) + 
							" and a.propertyNo = " + Common.sqlChar(rsup_1.getString("propertyNo")) + 
							" and a.signno like " + Common.sqlChar(rs_diff.getString("signNo")) + 
							" and b.useArea <> '0' " + 
							" and a.caseNo=" + Common.sqlChar(divisionAdd) + 
							"";
//					
						ResultSet rs_b = db.querySQL(sql_b);
						String[] execSQLArray_3 = new String[2];
						if (rs_b.next()){
							//seqNo_check ++;
							execSQLArray_3[0]=" insert into npbgr_changedetail(" +
								" caseNo," +
								" seqNo," +
								" serialNo1,"+
								" signNo,"+
								" fakeDivision," +
								" useUnit," +
								" useArea," +
								" useRelation," +
								" dimArea," +
								" useDateS," +
								" useDateE," +
								" disType," +
								" disArea1," +
								" disArea2," +
								" disDateS," +
								" disDateE," +
								" kindno" +
								" )Values(" +				
							Common.sqlChar(caseNo) + "," +
							Common.sqlChar(Common.get(seqNo_check+"")) + "," +
							Common.sqlChar(Common.get(rs_b.getString("serialNo1"))) + "," +
							Common.sqlChar(Common.get(rs_diff.getString("signNo"))) + "," +
							Common.sqlChar(Common.get(rs_b.getString("fakeDivision"))) + "," +
							Common.sqlChar(Common.get(rs_b.getString("useUnit"))) + "," +
							Common.sqlChar(Common.get(rs_b.getString("useArea"))) + "," +
							Common.sqlChar(Common.get(rs_b.getString("useRelation"))) + "," +
							Common.sqlChar(Common.get(rs_b.getString("dimArea"))) + "," +
							Common.sqlChar(Common.get(rs_b.getString("useDateS"))) + "," +
							Common.sqlChar(Common.get(rs_b.getString("useDateE"))) + "," +
							Common.sqlChar(Common.get(rs_b.getString("disType"))) + "," +
							Common.sqlChar(Common.get(rs_b.getString("disArea1"))) + "," +
							Common.sqlChar(Common.get(rs_b.getString("disArea2"))) + "," +
							Common.sqlChar(Common.get(rs_b.getString("disDateS"))) + "," +
							Common.sqlChar(Common.get(rs_b.getString("disDateE"))) + "," +
							Common.sqlChar("2") + ")" ;
							
						}else{
											
							execSQLArray_3[0]=" insert into npbgr_changedetail(" +
								" caseNo," +
								" seqNo," +
								" serialNo1,"+
								" signNo,"+
								" fakeDivision," +						
								" useUnit," +
								" useArea," +
								" useRelation," +
								" dimArea," +
								" useDateS," +								
								" useDateE," +
								" disType," +
								" disArea1," +
								" disArea2," +
								" disDateS," +								
								" disDateE," +
								" kindno" +
								" )Values(" +				
							Common.sqlChar(caseNo) + "," +
							Common.sqlChar(Common.get(seqNo_check+"")) + "," +
							Common.sqlChar("") + "," +
							Common.sqlChar(Common.get(rsup_1.getString("signNo"))) + "," +
							Common.sqlChar(Common.get(rs_diff.getString("fakeDivision"))) + "," +						
							Common.sqlChar("") + "," +
							Common.sqlChar("") + "," +
							Common.sqlChar("99") + "," +
							Common.sqlChar("") + "," +
							Common.sqlChar("") + "," +
							Common.sqlChar("") + "," +							
							Common.sqlChar("") + "," +
							Common.sqlChar("") + "," +
							Common.sqlChar("") + "," +
							Common.sqlChar("") + "," +
							Common.sqlChar("") + "," +							
							Common.sqlChar("2") + ")" ;
								
						}
						
						//抓取修改後人檔資料
						String sql_b_1=" select f.applyid, f.applyname, f.applytype, f.hometel, f.mobile," +
							" f.liveadd4, f.payeryn, f.registryyn, f.serialNo1,f.seq" +
							" from UNTLA_Land a, untla_person f " +
							" where a.enterOrg=f.enterOrg and a.ownership=f.ownership " +
							" and a.propertyNo=f.propertyno and a.serialNo=f.serialno" +
							" and f.payeryn='Y' " +
							" and a.enterOrg = " + Common.sqlChar(rsup_1.getString("enterOrg")) + 
							" and a.ownership = " + Common.sqlChar(rsup_1.getString("ownership")) + 
							" and a.propertyNo = " + Common.sqlChar(rsup_1.getString("propertyNo")) + 
							" and a.signno like " + Common.sqlChar(rs_diff.getString("signNo")) + 
							" and a.caseNo=" + Common.sqlChar(divisionAdd) + 
							
							"";
						
						ResultSet rs_b_1 = db.querySQL(sql_b_1);						
						if (rs_b_1.next()){
							execSQLArray_3[1]=" insert into NPBGR_ChangePerson(" +
								" caseNo,"+
								" seqNo,"+
								" kindNo,"+
								" applyType,"+
								" payerYN,"+
								" registryYN,"+
								" applyID,"+
								" seq,"+
								" applyName,"+
								" homeTel,"+
								" mobile,"+
								" liveAdd4,"+
								" editID,"+
								" editDate,"+
								" editTime"+
							" )Values(" +
								Common.sqlChar(Common.get(caseNo)) + "," +
								Common.sqlChar(Common.get(seqNo_check+"")) + "," +
								Common.sqlChar("2") + "," +
								Common.sqlChar(Common.get(rs_b_1.getString("applyType"))) + "," +				
								Common.sqlChar(Common.get(rs_b_1.getString("payerYN"))) + "," +
								Common.sqlChar(Common.get(rs_b_1.getString("registryYN"))) + "," +
								Common.sqlChar(Common.get(rs_b_1.getString("applyID"))) + "," +
								Common.sqlChar(Common.get(rs_b_1.getString("seq"))) + "," +
								Common.sqlChar(Common.get(rs_b_1.getString("applyName"))) + "," +
								Common.sqlChar(Common.get(rs_b_1.getString("homeTel"))) + "," +
								Common.sqlChar(Common.get(rs_b_1.getString("mobile"))) + "," +
								Common.sqlChar(Common.get(rs_b_1.getString("liveAdd4"))) + "," +					
								Common.sqlChar(getEditID()) + "," +
								Common.sqlChar(getEditDate()) + "," +
								Common.sqlChar(getEditTime()) + ")" ;
						}else{
							execSQLArray_3[1]=" insert into NPBGR_ChangePerson(" +
								" caseNo,"+
								" seqNo,"+
								" kindNo,"+
								" applyType,"+
								" payerYN,"+
								" registryYN,"+
								" applyID,"+
								" seq,"+
								" applyName,"+
								" homeTel,"+
								" mobile,"+
								" liveAdd4,"+
								" editID,"+
								" editDate,"+
								" editTime"+
							" )Values(" +
								Common.sqlChar(Common.get(caseNo)) + "," +
								Common.sqlChar(Common.get(seqNo_check+"")) + "," +
								Common.sqlChar("2") + "," +
								Common.sqlChar("") + "," +				
								Common.sqlChar("") + "," +
								Common.sqlChar("") + "," +
								Common.sqlChar("0000000000") + "," +
								Common.sqlChar("") + "," +
								Common.sqlChar("") + "," +
								Common.sqlChar("") + "," +
								Common.sqlChar("") + "," +
								Common.sqlChar("") + "," +					
								Common.sqlChar(getEditID()) + "," +
								Common.sqlChar(getEditDate()) + "," +
								Common.sqlChar(getEditTime()) + ")" ;
						}
						
						db.excuteSQL(execSQLArray_3);	
					}	
					String[] execSQLArray_1 = new String[1];
					
					String caseType="";
					String applyReason="";
					
					if("402".equals(cause)){
						caseType="5";
						applyReason="分割後管理資料異動    (分割案號:"+caseNo+")";
					}else if("404".equals(cause)){
						caseType="6";
						applyReason="重測後管理資料異動    (重測案號:"+caseNo+")";
					}else if("405".equals(cause)){
						caseType="7";
						applyReason="重劃後管理資料異動    (重劃案號:"+caseNo+")";
					}else if("403".equals(cause)){
						caseType="8";
						applyReason="逕為分割後管理資料異動    (逕為分割案號:"+caseNo+")";
					}else{
						caseType="4";
					}
					
					execSQLArray_1[0]=" update NPBGR_Change set " +
						" propertyType = " + Common.sqlChar("1") + "," +
						" serialNo = " + Common.sqlChar(rsup_1.getString("serialNo")) + "," +
						" propertyNo = " + Common.sqlChar(rsup_1.getString("propertyNo")) + "," +
						" applyDate = " + Common.sqlChar(Datetime.getYYYMMDD()) + "," +
						" applyReason = " + Common.sqlChar(applyReason) + "," +
						" caseType = " + Common.sqlChar(caseType) + "," +		
						" checkApprove = " + Common.sqlChar("Y") + "," +
						" signNo = " + Common.sqlChar(rsup_1.getString("signNo")) + "," +
						" editID = " + Common.sqlChar(getEditID()) + "," +
						" editDate = " + Common.sqlChar(getEditDate()) + "," +
						" editTime = " + Common.sqlChar(getEditTime()) + 
					" where 1=1 " + 
						" and caseNo = " + Common.sqlChar(caseNo) +
						"";
									
					db.excuteSQL(execSQLArray_1);	

				}
				
				db.excuteSQL(sbSQL.toString().split(":;:"));
				setStateUpdateSuccess();
				setErrorMsg("新增非公用異動記錄單號 : "+caseNo);

			}
			
		}
		queryOne();
	} catch (Exception e) {
		e.printStackTrace();
	} finally {
		db.closeAll();
	}
	return showMsg;
}

//回復審核設定
public void approveRe()throws Exception{	
	Database db = new Database();
	String checkCount = "";
	String enterOrgQuery = "";
	String ownershipQuery = "";
	String caseNoQuery = "";
	String mergeReduceQuery = "";
	String divisionReduceQuery = "";
	String mergeAddQuery = "";
	String divisionAddQuery = "";
	String enterDateQuery = "";
	int count = 0;
	try {
		String[] execSQLArray;
		String strSQL = "";
		String sql ="select enterOrg, ownership, caseNo, mergeReduce, mergeAdd," +
					" divisionReduce, divisionAdd, enterDate" +
					" from untla_MergeDivision " +
					" where 1=1 " +
	    			" and enterOrg = " + Common.sqlChar(enterOrg) +
	    			" and ownership = " + Common.sqlChar(ownership) +
	    			" and caseNo = " + Common.sqlChar(caseNo) +
					"" ;
	
		ResultSet rs = db.querySQL(sql);
		while (rs.next()){
			count++;
			enterOrgQuery = rs.getString("enterOrg");
			ownershipQuery = rs.getString("ownership");
			caseNoQuery = rs.getString("caseNo");
			mergeReduceQuery = rs.getString("mergeReduce");
			divisionReduceQuery = rs.getString("divisionReduce");
			mergeAddQuery = rs.getString("mergeAdd");
			divisionAddQuery = rs.getString("divisionAdd");
			enterDateQuery = rs.getString("enterDate");
			
			//檢查資料是否已做過增減值或減損作業
			String sql_1 ="select count(*) as checkCount" +
			" from UNTLA_AddProof a, UNTLA_Land b" +
			" where 1=1"+
			" and a.enterOrg = " + Common.sqlChar(enterOrgQuery) +
			" and a.ownership = " + Common.sqlChar(ownershipQuery) +
			" and a.mergeDivision = " + Common.sqlChar(caseNoQuery) +
			" and a.enterOrg = b.enterOrg"+
			" and a.ownership = b.ownership" +
			" and a.caseNo = b.caseNo" +
			" and (b.enterOrg) in (" +
			" (select c.enterOrg" +
			" from UNTLA_AdjustDetail c" +
			" where c.enterOrg = b.enterOrg and c.ownership = b.ownership" +
			" and c.propertyNo = b.propertyNo and c.serialNo = b.serialNo" +
			" ) union (" +
			" select d.enterOrg" +
			" from UNTLA_ReduceDetail d" +
			" where d.caseNo !=decode("+ Common.sqlChar(divisionReduceQuery) +",'99','99',"+ Common.sqlChar(divisionReduceQuery) +")" +
			" and d.enterOrg = b.enterOrg and d.ownership = b.ownership" +
			" and d.propertyNo = b.propertyNo and d.serialNo = b.serialNo)" +
			" )" +
			" and (b.ownership) in (" +
			" (select c.ownership" +
			" from UNTLA_AdjustDetail c" +
			" where c.enterOrg = b.enterOrg and c.ownership = b.ownership" +
			" and c.propertyNo = b.propertyNo and c.serialNo = b.serialNo" +
			" ) union (" +
			" select d.ownership" +
			" from UNTLA_ReduceDetail d" +
			" where d.caseNo !=decode("+ Common.sqlChar(divisionReduceQuery) +",'99','99',"+ Common.sqlChar(divisionReduceQuery) +")" +
			" and d.enterOrg = b.enterOrg and d.ownership = b.ownership" +
			" and d.propertyNo = b.propertyNo and d.serialNo = b.serialNo)" +
			" )" +
			" and (b.propertyNo) in (" +
			" (select c.propertyNo" +
			" from UNTLA_AdjustDetail c" +
			" where c.enterOrg = b.enterOrg and c.ownership = b.ownership" +
			" and c.propertyNo = b.propertyNo and c.serialNo = b.serialNo" +
			" ) union (" +
			" select d.propertyNo" +
			" from UNTLA_ReduceDetail d" +
			" where d.caseNo !=decode("+ Common.sqlChar(divisionReduceQuery) +",'99','99',"+ Common.sqlChar(divisionReduceQuery) +")" +
			" and d.enterOrg = b.enterOrg and d.ownership = b.ownership" +
			" and d.propertyNo = b.propertyNo and d.serialNo = b.serialNo)" +
			" )" +
			" and (b.serialNo) in (" +
			" (select c.serialNo" +
			" from UNTLA_AdjustDetail c" +
			" where c.enterOrg = b.enterOrg and c.ownership = b.ownership" +
			" and c.propertyNo = b.propertyNo and c.serialNo = b.serialNo" +
			" ) union (" +
			" select d.serialNo" +
			" from UNTLA_ReduceDetail d" +
			" where d.caseNo !=decode("+ Common.sqlChar(divisionReduceQuery) +",'99','99',"+ Common.sqlChar(divisionReduceQuery) +")" +
			" and d.enterOrg = b.enterOrg and d.ownership = b.ownership" +
			" and d.propertyNo = b.propertyNo and d.serialNo = b.serialNo)" +
			" )" +
			"" ;	
			
			ResultSet rs_1 = db.querySQL(sql_1);
			if (rs_1.next()){
				checkCount = rs_1.getString("checkCount");
			}
			
			//------------------------------------------------------------------------------------------------
			//回覆入帳設定
			if(closing.equals("N") && verify.equals("Y") && checkCount.equals("0")){
				//合併分割案
				strSQL += "update UNTLA_MergeDivision set verify ='N' " +
					" where enterOrg=" + Common.sqlChar(enterOrgQuery) +
					" and ownership=" + Common.sqlChar(ownershipQuery) +
					" and caseNo=" + Common.sqlChar(caseNoQuery) +
					":;:";
				
				//合併分割增加單
				
					strSQL += "update UNTLA_AddProof set verify ='N', closing ='N'" +
						" where enterOrg=" + Common.sqlChar(enterOrgQuery) +
						" and ownership=" + Common.sqlChar(ownershipQuery) +
						" and caseNo=" + Common.sqlChar(mergeAddQuery) +
						":;:";
					strSQL += "update UNTLA_Land set verify ='N', closing ='N'" +
						" where enterOrg=" + Common.sqlChar(enterOrgQuery) +
						" and ownership=" + Common.sqlChar(ownershipQuery) +
						" and caseNo=" + Common.sqlChar(mergeAddQuery) +
						":;:";
				
					strSQL += "update UNTLA_AddProof set verify ='N', closing ='N'" +
						" where enterOrg=" + Common.sqlChar(enterOrgQuery) +
						" and ownership=" + Common.sqlChar(ownershipQuery) +
						" and caseNo=" + Common.sqlChar(divisionAddQuery) +
						":;:";
				//if(!"000000002Z".equals(enterOrg)){	
					strSQL += "update UNTLA_Land set verify ='N', closing ='N'" +
						" where enterOrg=" + Common.sqlChar(enterOrgQuery) +
						" and ownership=" + Common.sqlChar(ownershipQuery) +
						" and caseNo=" + Common.sqlChar(divisionAddQuery) +
						":;:";
				//}

				//合併分割減損單
				strSQL += "update UNTLA_ReduceProof set verify ='N'" +
					" where enterOrg=" + Common.sqlChar(enterOrgQuery) +
					" and ownership=" + Common.sqlChar(ownershipQuery) +
					" and caseNo=" + Common.sqlChar(mergeReduceQuery) +
					":;:";
				strSQL += "update UNTLA_ReduceDetail set verify ='N'" +
					" where enterOrg=" + Common.sqlChar(enterOrgQuery) +
					" and ownership=" + Common.sqlChar(ownershipQuery) +
					" and caseNo=" + Common.sqlChar(mergeReduceQuery) +
					":;:";
				strSQL += "update UNTLA_ReduceProof set verify ='N'" +
					" where enterOrg=" + Common.sqlChar(enterOrgQuery) +
					" and ownership=" + Common.sqlChar(ownershipQuery) +
					" and caseNo=" + Common.sqlChar(divisionReduceQuery) +
					":;:";
				strSQL += "update UNTLA_ReduceDetail set verify ='N'" +
					" where enterOrg=" + Common.sqlChar(enterOrgQuery) +
					" and ownership=" + Common.sqlChar(ownershipQuery) +
					" and caseNo=" + Common.sqlChar(divisionReduceQuery) +
					":;:";
		
				//合併分割減損單明細
				String sql_3 ="(select enterOrg, ownership, propertyNo, serialNo, holdNume,  holdArea, bookValue" +
					" from UNTLA_ReduceDetail" +
					" where 1=1 " +
	    			" and enterOrg = " + Common.sqlChar(enterOrgQuery) +
	    			" and ownership = " + Common.sqlChar(ownershipQuery) +
	    			" and caseNo = " + Common.sqlChar(mergeReduceQuery) +
					" ) union (" +
					" select enterOrg, ownership, propertyNo, serialNo, holdNume,  holdArea, bookValue" +
					" from UNTLA_ReduceDetail" +
					" where 1=1 " +
	    			" and enterOrg = " + Common.sqlChar(enterOrgQuery) +
	    			" and ownership = " + Common.sqlChar(ownershipQuery) +
	    			" and caseNo = " + Common.sqlChar(divisionReduceQuery) +
					" )" +
					"" ;		
				ResultSet rs_3 = db.querySQL(sql_3);
				while (rs_3.next()){
					strSQL += "update UNTLA_Land set" +
						" dataState='1'," +
						" reduceDate=null," +
						" reduceCause=null," +
						" reduceCause1=null," +
						" holdNume=" + Common.sqlChar(rs_3.getString("holdNume")) +","+
						" holdArea=" + Common.sqlChar(rs_3.getString("holdArea")) +","+
						" bookValue=" + Common.sqlChar(rs_3.getString("bookValue")) +
						" where enterOrg=" + Common.sqlChar(rs_3.getString("enterOrg")) +
						" and ownership=" + Common.sqlChar(rs_3.getString("ownership")) +
						" and propertyNo=" + Common.sqlChar(rs_3.getString("propertyNo")) +
						" and serialNo=" + Common.sqlChar(rs_3.getString("serialNo")) +
						":;:";	
					
					//檢查土地管理檔
					String sql_4 ="select useDateE" +
							" from UNTLA_Manage" +
							" where enterOrg=" + Common.sqlChar(rs_3.getString("enterOrg")) +
							" and ownership=" + Common.sqlChar(rs_3.getString("ownership")) +
							" and propertyNo=" + Common.sqlChar(rs_3.getString("propertyNo")) +
							" and serialNo=" + Common.sqlChar(rs_3.getString("serialNo")) +
							"";
					ResultSet rs_4 = db.querySQL(sql_4);
					while (rs_4.next()){
						if(rs_4.getString("useDateE").equals(enterDateQuery)){
							strSQL += "update UNTLA_Manage set useDateE = null" +
								" where enterOrg=" + Common.sqlChar(rs_3.getString("enterOrg")) +
								" and ownership=" + Common.sqlChar(rs_3.getString("ownership")) +
								" and propertyNo=" + Common.sqlChar(rs_3.getString("propertyNo")) +
								" and serialNo=" + Common.sqlChar(rs_3.getString("serialNo")) +
								":;:";
						}
					}
				}
				
				//if(!"000000002Z".equals(enterOrg)){
					//回復歷史資料為現存
				    String sqlup_1= " select a.enterOrg, a.ownership, a.caseNo, a.propertyNo, a.serialNo, a.signNo," +
						" a.propertyNo " +
						" from UNTLA_ReduceDetail a" +
						" where 1=1" +
						" and a.caseNo=" + Common.sqlChar(caseNo) + 
						"";				
					ResultSet rsup_1 = db.querySQL(sqlup_1);
					while (rsup_1.next()){	
						
						//修改isdefault為"0"
						String sqlup_2= " select a.serialNo1 " +
							" from UNTLA_Manage a" +
							" where 1=1" +
							" and a.enterOrg = " + Common.sqlChar(rsup_1.getString("enterOrg")) + 
							" and a.ownership = " + Common.sqlChar(rsup_1.getString("ownership")) + 
							" and a.propertyNo = " + Common.sqlChar(rsup_1.getString("propertyNo")) + 
							" and a.serialNo = " + Common.sqlChar(rsup_1.getString("serialNo")) + 
							" and a.useArea <> '0' " + 
							"";
					
						ResultSet rsup_2 = db.querySQL(sqlup_2);
						while (rsup_2.next()){
							strSQL +=" update UNTLA_Manage set isdefault='1' " +
									" where enterOrg=" + Common.sqlChar(rsup_1.getString("enterOrg")) + 
									" and ownership = " + Common.sqlChar(rsup_1.getString("ownership")) + 
									" and propertyNo = " + Common.sqlChar(rsup_1.getString("propertyNo")) + 
									" and serialNo = " + Common.sqlChar(rsup_1.getString("serialNo")) + 
									" and serialNo1 = " + Common.sqlChar(rsup_2.getString("serialNo1")) + 
									":;:";				
						}
					}
				//}
				
				
				//刪除合併分割對照檔
				strSQL +=" delete UNTLA_MergeDivision1 where 1=1 " +
					" and enterOrg = " + Common.sqlChar(enterOrg) +
	    			" and ownership = " + Common.sqlChar(ownership) +
	    			" and caseNo = " + Common.sqlChar(caseNo) +
	    			"";		
				
			}else{
				setVerifyError("Y");
				if(closing.equals("Y")){
					setErrorMsg("已月結的資料無法回復入帳，請先取消月結，再回此作業回復入帳！");
				}else if(verify.equals("N")){
					setErrorMsg("尚未入帳，請直接修改資料即可！");
				//}else if( mergeDivision != null){
					//setErrorMsg("請至合併分割作業進行回復審審核入帳！");				
				}else if(!checkCount.equals("0")){
					setErrorMsg("合併分割增加單之土地資料已做過增減值或減損作業 ，\\n 請先刪除增減值或減損作業之資料再回此作業回復入帳！");				
				}

			}
			if ("Y".equals(getVerifyError())) {
			    break;
			}
		}	
		//System.out.println("回復："+strSQL);
		if (!"Y".equals(getVerifyError())) {			
			execSQLArray = strSQL.split(":;:");
			db.excuteSQL(execSQLArray);
			setStateUpdateSuccess();
			setErrorMsg("回復入帳完成");	
			queryOne();
		} else {			   
			setStateUpdateSuccess();
	        queryOne();
		}	
		
	} catch (Exception e) {
		e.printStackTrace();
	} finally {
		db.closeAll();
	}		
}

}


