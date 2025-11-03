/*
*<br>程式目的：非消耗品減少作業－減損單資料
*<br>程式代號：untne013f
*<br>程式日期：0941111
*<br>程式作者：judy
*<br>--------------------------------------------------------
*<br>修改作者　　修改日期　　　修改目的
*<br>--------------------------------------------------------
*/


package unt.ne;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Map;

import unt.ch.UNTCH_Tools;
import util.Common;
import util.Database;
import util.Datetime;
import util.MaxClosingYM;
import util.TCGHCommon;

public class UNTNE013F extends UNTNE013Q{

/**
 * 如果檔案有維護到的欄位，要來這裡下變數設定
 */
String enterOrg;
String enterOrgName;
String ownership;
String caseNo;
String caseName;
String writeDate;
String writeUnit;
String proofDoc;
String proofNo;
String manageNo;
String summonsNo;
String summonsDate;
String reduceDate;
String approveOrg;
String approveDate;
String approveDoc;
String verify;
String notes;
String differenceKind;




/**
 * 如果檔案有維護到的欄位，要來這裡設定
 */
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
public String getReduceDate(){ return checkGet(reduceDate); }
public void setReduceDate(String s){ reduceDate=checkSet(s); }
public String getApproveOrg(){ return checkGet(approveOrg); }
public void setApproveOrg(String s){ approveOrg=checkSet(s); }
public String getApproveDate(){ return checkGet(approveDate); }
public void setApproveDate(String s){ approveDate=checkSet(s); }
public String getApproveDoc(){ return checkGet(approveDoc); }
public void setApproveDoc(String s){ approveDoc=checkSet(s); }
public String getVerify(){ return checkGet(verify); }
public void setVerify(String s){ verify=checkSet(s); }
public String getNotes(){ return checkGet(notes); }
public void setNotes(String s){ notes=checkSet(s); }
public String getDifferenceKind() {return checkGet(this.differenceKind);}
public void setDifferenceKind(String s) {differenceKind = checkSet(s);}
String proofYear;
public String getProofYear(){return checkGet(proofYear);}
public void setProofYear(String s) {proofYear = checkSet(s);}

public String getSummonsDate() {
	return checkGet(summonsDate);
}
public void setSummonsDate(String summonsDate) {
	this.summonsDate = checkSet(summonsDate);
}
String verifyError;

public String getVerifyError(){ return checkGet(verifyError); }
public void setVerifyError(String s){ verifyError=checkSet(s); }

String checkEnterOrg;
String checkOldEnterOrg;
String checkOwnership;
String checkOldOwnership;
String checkPropertyNo;
String checkOldPropertyNo;
String checkLotNo;
String checkOldLotNo;
String checkDifferenceKind;
String checkOldDifferenceKind;

public String getCheckEnterOrg(){ return checkGet(checkEnterOrg); }
public void setCheckEnterOrg(String s){ checkEnterOrg=checkSet(s); }
public String getCheckOldEnterOrg(){ return checkGet(checkOldEnterOrg); }
public void setCheckOldEnterOrg(String s){ checkOldEnterOrg=checkSet(s); }
public String getCheckOwnership(){ return checkGet(checkOwnership); }
public void setCheckOwnership(String s){ checkOwnership=checkSet(s); }
public String getCheckOldOwnership(){ return checkGet(checkOldOwnership); }
public void setCheckOldOwnership(String s){ checkOldOwnership=checkSet(s); }
public String getCheckPropertyNo(){ return checkGet(checkPropertyNo); }
public void setCheckPropertyNo(String s){ checkPropertyNo=checkSet(s); }
public String getCheckOldPropertyNo(){ return checkGet(checkOldPropertyNo); }
public void setCheckOldPropertyNo(String s){ checkOldPropertyNo=checkSet(s); }
public String getCheckLotNo(){ return checkGet(checkLotNo); }
public void setCheckLotNo(String s){ checkLotNo=checkSet(s); }
public String getCheckOldLotNo(){ return checkGet(checkOldLotNo); }
public void setCheckOldLotNo(String s){ checkOldLotNo=checkSet(s); }
public String getCheckDifferenceKind() {return checkGet(checkDifferenceKind);}
public void setCheckDifferenceKind(String s) {checkDifferenceKind = checkSet(s);}
public String getCheckOldDifferenceKind() {return checkGet(checkOldDifferenceKind);}
public void setCheckOldDifferenceKind(String s) {checkOldDifferenceKind = checkSet(s);}
private String reduceDateTemp;
public String getReduceDateTemp() {return checkGet(reduceDateTemp);}
public void setReduceDateTemp(String reduceDateTemp) {this.reduceDateTemp = checkSet(reduceDateTemp);}

private String addcaseno;
public String getAddcaseno() { return checkGet(addcaseno); }
public void setAddcaseno(String addcaseno) { this.addcaseno = checkSet(addcaseno); }

int sumBookValue ;
int sumBookAmount ;
Hashtable h = new Hashtable();
String changeNo="";

String checkFlag;
public String getCheckFlag() {return checkGet(checkFlag);}
public void setCheckFlag(String checkFlag) {this.checkFlag = checkSet(checkFlag);}

public boolean checkUpdateError(){
	boolean result = true;
	if(checkClosingYMfromUNTAC_CLOSINGNE(getReduceDate(),getEnterOrg(),getDifferenceKind())){
		setErrorMsg("入帳日期需為最大月結年月＋１");
		result = false;
	}
	return result;
}

public boolean checReVerifyError(){
	boolean result = true;
	if(checkNECanNotReVerify(getReduceDateTemp(),getEnterOrg(),getDifferenceKind())){
		setErrorMsg("當月已月結不可取消入帳");
		result = false;
	}
	return result;
}

//傳回執行insert前之檢查sql
protected String[][] getInsertCheckSQL(){
	String[][] checkSQLArray = new String[1][4];
 	checkSQLArray[0][0]=" select count(*) as checkResult from UNTNE_ReduceProof where 1=1 " + 
		" and enterOrg = " + Common.sqlChar(enterOrg) + 
		//" and ownership = " + Common.sqlChar(ownership) + 
		" and caseNo = " + Common.sqlChar(caseNo) + 
		"";
	checkSQLArray[0][1]=">";
	checkSQLArray[0][2]="0";
	checkSQLArray[0][3]="該筆資料已重複，請重新輸入！";
	return checkSQLArray;
}


//傳回insert sql
protected String[] getInsertSQL(){
	UNTCH_Tools ul = new UNTCH_Tools();
	//取得電腦單號
	Database db = new Database();
	ResultSet rs;	
		String sql="select cast(max(substring(caseNo,4,7))+1 AS varchar) as caseNo from UNTNE_REDUCEPROOF " +
		" where enterOrg = " + Common.sqlChar(enterOrg) +
		" and substring(caseNo,1,3) = " + Common.sqlChar(Datetime.getYYY()) +
		"";		
	try {		
		rs = db.querySQL(sql);
		if (rs.next()){
			if (rs.getString("caseNo")==null)
				setCaseNo(Datetime.getYYYMMDD().substring(0,3)+"0000001");
			else
				setCaseNo(Datetime.getYYYMMDD().substring(0,3)+ Common.formatFrontZero(rs.getString("caseNo"), 7));
		} else {
			setCaseNo(Datetime.getYYYMMDD().substring(0,3)+"0000001");
		}
	} catch (Exception e) {
		e.printStackTrace();
	} finally {
		db.closeAll();
	}
	//取得減損單編號－號
	setProofNo(MaxClosingYM.getMaxProofNo("UNTNE_ReduceProof",enterOrg,ownership,this.getProofYear()));
	//===================
	String[] execSQLArray = new String[1];
	execSQLArray[0]=" insert into UNTNE_ReduceProof(" +
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
			" summonsDate,"+
			" reduceDate,"+
			" approveOrg,"+
			" approveDate,"+
			" approveDoc,"+
			" verify,"+
			" notes,"+
			" editID,"+
			" editDate,"+
			" editTime,"+
			" differencekind,"+
			" proofyear"+
		" )Values(" +
			Common.sqlChar(this.getEnterOrg()) + "," +
			Common.sqlChar(this.getOwnership()) + "," +
			Common.sqlChar(this.getCaseNo()) + "," +
			Common.sqlChar(this.getCaseName()) + "," +
			Common.sqlChar(ul._transToCE_Year(this.getWriteDate())) + "," +
			Common.sqlChar(this.getWriteUnit()) + "," +
			Common.sqlChar(this.getProofDoc()) + "," +
			Common.sqlChar(this.getProofNo()) + "," +
			Common.sqlChar(this.getManageNo()) + "," +
			Common.sqlChar(this.getSummonsNo()) + "," +
			Common.sqlChar(ul._transToCE_Year(this.getSummonsDate())) + "," +
			Common.sqlChar(ul._transToCE_Year(this.getReduceDate())) + "," +
			Common.sqlChar(this.getApproveOrg()) + "," +
			Common.sqlChar(ul._transToCE_Year(this.getApproveDate())) + "," +
			Common.sqlChar(this.getApproveDoc()) + "," + 
			Common.sqlChar(this.getVerify()) + "," +
			Common.sqlChar(this.getNotes()) + "," +
			Common.sqlChar(getEditID()) + "," +
			Common.sqlChar(ul._transToCE_Year(getEditDate())) + "," +
			Common.sqlChar(getEditTime()) + "," +
	        Common.sqlChar(getDifferenceKind()) + "," +
	        Common.sqlChar(getProofYear()) + ")" ;
	return execSQLArray;
}

//檢查update時，proofyear是否有改變
private boolean checkProofyearChanged(String enterorg, String caseno, String newproofyear){
	boolean isChanged = false;
	String oldproofyear ="";
	ResultSet rs;
	Database db = new Database();
	try{
		String sql = "select proofyear from UNTNE_REDUCEPROOF where enterorg=" + Common.sqlChar(enterorg) +
								" and caseno=" + Common.sqlChar(caseno);
		rs = db.querySQL(sql);
		if(rs.next()){
			oldproofyear = rs.getString(1);
			if(!newproofyear.equals(oldproofyear)){
				isChanged =true;
			}
		}else{
			System.out.println("查無此單!");
		}
	}catch(Exception e){
		e.printStackTrace();
	}finally{
		db.closeAll();
	}
	return isChanged;
}

//傳回update sql
public void update(){
	UNTCH_Tools ul = new UNTCH_Tools();
    Database db = new Database();  
    String[] execSQLArray = null;
    String strSQL ="";
    String className = this.getClass().getName();
    
	setEditDate(Datetime.getYYYMMDD());
	setEditTime(Datetime.getHHMMSS());	
    //int j;
    try{
    	//問題單1290補充: update時更改編號-年，重新取得編號-號
		if(this.checkProofyearChanged(getEnterOrg(), getCaseNo(), getProofYear())){
			this.setProofNo(MaxClosingYM.getMaxProofNo("UNTNE_REDUCEPROOF",enterOrg,ownership,this.getProofYear()));
		}
		
        strSQL +=" update UNTNE_ReduceProof set " +
				" caseName = " + Common.sqlChar(this.getCaseName()) + "," +
				" writeDate = " + Common.sqlChar(ul._transToCE_Year(this.getWriteDate())) + "," +
				" writeUnit = " + Common.sqlChar(this.getWriteUnit()) + "," +
				" proofDoc = " + Common.sqlChar(this.getProofDoc()) + "," +
				" proofNo = " + Common.sqlChar(this.getProofNo()) + "," +
				" manageNo = " + Common.sqlChar(this.getManageNo()) + "," +
				" summonsNo = " + Common.sqlChar(this.getSummonsNo()) + "," +
				" summonsDate = " + Common.sqlChar(ul._transToCE_Year(this.getSummonsDate())) + "," +
				" reduceDate = " + Common.sqlChar(ul._transToCE_Year(this.getReduceDate())) + "," +
				" approveOrg = " + Common.sqlChar(this.getApproveOrg()) + "," +
				" approveDate = " + Common.sqlChar(ul._transToCE_Year(this.getApproveDate())) + "," +
				" approveDoc = " + Common.sqlChar(this.getApproveDoc()) + "," +
				" verify = " + Common.sqlChar(this.getVerify()) + "," +
				" notes = " + Common.sqlChar(this.getNotes()) + "," +
				" editID = " + Common.sqlChar(getEditID()) + "," +
				" editDate = " + Common.sqlChar(ul._transToCE_Year(getEditDate())) + "," +
				" editTime = " + Common.sqlChar(getEditTime()) + "," +
				" differencekind = " + Common.sqlChar(getDifferenceKind()) + "," +
				" proofyear = " + Common.sqlChar(getProofYear()) + 
			" where 1=1 " + 
				" and enterOrg = " + Common.sqlChar(enterOrg) +
				" and caseNo = " + Common.sqlChar(caseNo) +
				":;:";		
		
		if(verify.equals("Y")){
			strSQL += checkVerify();
			strSQL += updNonexpDetail();
			strSQL += updNonexp();
		}
		if (!"Y".equals(getVerifyError())) {
			execSQLArray = strSQL.split(":;:");
			db.excuteSQL(execSQLArray);
			setStateUpdateSuccess();
			
			//使用者操作記錄
			Common.insertUpdateRecordSql("", this.getEnterOrg(), this.getUserID(), className.substring(className.lastIndexOf('.') + 1), this.getObjPath().replaceAll("&gt;", ">") + " > > 修改" + this.getCaseNo());
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
		String[] execSQLArray = new String[2];
		//刪除 UNTNE_ReduceProof TABLE
		execSQLArray[0]=" delete UNTNE_ReduceProof where 1=1 " +
				" and enterOrg = " + Common.sqlChar(enterOrg) +
				//" and ownership = " + Common.sqlChar(ownership) +
				" and caseNo = " + Common.sqlChar(caseNo) +
				"";
				
		//刪除 UNTNE_ReduceDetail TABLE
		execSQLArray[1]=" delete UNTNE_ReduceDetail where 1=1 " +
				" and enterOrg = " + Common.sqlChar(enterOrg) +
				//" and ownership = " + Common.sqlChar(ownership) +
				" and caseNo = " + Common.sqlChar(caseNo) +
				"";

		return execSQLArray;
	}


/**
 * <br>
 * <br>目的：依主鍵查詢單一資料
 * <br>參數：無
 * <br>傳回：傳回本物件
*/

public UNTNE013F  queryOne() throws Exception{
	Database db = new Database();
	UNTNE013F obj = this;
	UNTCH_Tools ul = new UNTCH_Tools();
	this.setCheckFlag(checkBookValue());
	
	try {
		String sql=" select a.enterOrg, b.organSName, a.ownership, a.caseNo, a.caseName, a.writeDate, a.writeUnit, a.proofDoc, a.proofNo, a.manageNo, a.summonsNo, a.reduceDate, a.approveOrg, a.approveDate, a.approveDoc, a.verify, a.notes, a.editID, a.editDate, a.editTime, a.editID, a.editDate, a.editTime,a.proofYear, a.differencekind,a.summonsDate, a.addcaseno "+
			" from UNTNE_ReduceProof a, SYSCA_Organ b where 1=1 " +
			" and a.enterOrg = " + Common.sqlChar(enterOrg) +
			//" and a.ownership = " + Common.sqlChar(ownership) +
			" and a.caseNo = " + Common.sqlChar(caseNo) +
			" and b.organID = a.enterOrg" +
			" order by a.enterOrg, a.ownership, a.caseNo  ";
		ResultSet rs = db.querySQL(sql);
		if (rs.next()){
			obj.setEnterOrg(rs.getString("enterOrg"));
			obj.setEnterOrgName(rs.getString("organSName"));
			obj.setOwnership(rs.getString("ownership"));
			obj.setCaseNo(rs.getString("caseNo"));
			obj.setCaseName(rs.getString("caseName"));
			obj.setWriteDate(ul._transToROC_Year(rs.getString("writeDate")));
			obj.setWriteUnit(rs.getString("writeUnit"));
			obj.setProofDoc(rs.getString("proofDoc"));
			obj.setProofNo(rs.getString("proofNo"));
			obj.setManageNo(rs.getString("manageNo"));
			obj.setSummonsNo(rs.getString("summonsNo"));
			obj.setSummonsDate(ul._transToROC_Year(rs.getString("summonsDate")));
			obj.setReduceDate(ul._transToROC_Year(rs.getString("reduceDate")));
			obj.setApproveOrg(rs.getString("approveOrg"));
			obj.setApproveDate(ul._transToROC_Year(rs.getString("approveDate")));
			obj.setApproveDoc(rs.getString("approveDoc"));
			obj.setVerify(rs.getString("verify"));
			obj.setNotes(rs.getString("notes"));
			obj.setEditID(rs.getString("editID"));
			obj.setEditDate(ul._transToROC_Year(rs.getString("editDate")));
			obj.setEditTime(rs.getString("editTime"));
			obj.setProofYear(rs.getString("proofYear"));
			obj.setDifferenceKind(rs.getString("differencekind"));
			obj.setAddcaseno(rs.getString("addcaseno"));
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

/**
 * 如果有輸入查詢資料的欄位，要來這裡下sql條件式
 */
public ArrayList queryAll() throws Exception{
	setOldPage();
	UNTCH_Tools ul = new UNTCH_Tools();
	Database db = new Database();
	ArrayList objList=new ArrayList();
	Map<String,String> differencekindMap = TCGHCommon.getSysca_Code("DFK"); 					//財產區分別名稱
	int counter=0;
	try {
		String sql=" select distinct a.enterOrg, a.ownership,a.differencekind, " +
					" (select z.codeName from sysca_code z where 1=1 and (z.codeKindID='owa' or z.codeKindID='OWA') and a.ownership=z.codeID) as ownershipName, " +
					" a.caseNo, a.caseName, a.writeDate, a.proofDoc, a.proofNo, a.reduceDate,cast(a.proofyear as int) as proofyear,a.proofdoc,a.proofno, " +
					" (case a.verify when 'Y' then '是' else '否' end) verify " +
					" from UNTNE_ReduceProof a"+
					" left join UNTNE_ReduceDetail b on a.enterOrg=b.enterOrg and a.caseNo=b.caseNo "+
					" left join SYSCA_Organ c on a.enterOrg = c.organID where 1=1 " +
					"";
		
		//以下四個條件為新增移動單後查詢
		if (!"".equals(getI_enterOrg())) {
			sql += " and a.enterorg = " + Common.sqlChar(getI_enterOrg());
		}
		if (!"".equals(getI_ownership())) {
			sql += " and a.ownership = " + Common.sqlChar(getI_ownership());	
		}
		if (!"".equals(getI_caseNo())) {
			sql += " and a.caseno = " + Common.sqlChar(getI_caseNo());	
		}
		if (!"".equals(getI_differenceKind())) {
			sql += " and a.differencekind = " + Common.sqlChar(getI_differenceKind());	
		}
		//以上四個條件為新增移動單後查詢
		
		if ("detail".equals(getQuerySelect()) || "".equals(getQuerySelect())) {
			sql+=" and a.enterOrg=" + Common.sqlChar(getEnterOrg());		
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
			}
			if (!"".equals(getQ_ownership()))
				sql+=" and a.ownership = " + Common.sqlChar(getQ_ownership()) ;
			if (!"".equals(getQ_caseNoS()))
				sql+=" and a.caseNo >= " + Common.sqlChar(Common.formatFrontZero(getQ_caseNoS(),10));
			if (!"".equals(getQ_caseNoE()))
				sql+=" and a.caseNo <= " + Common.sqlChar(Common.formatFrontZero(getQ_caseNoE(),10));
			if (!"".equals(getQ_verify()))
				sql+=" and a.verify = " + Common.sqlChar(getQ_verify()) ;
			if (!"".equals(getQ_reduceDateS()))
				sql+=" and a.reduceDate >= " + Common.sqlChar(ul._transToCE_Year(getQ_reduceDateS())) ;
			if (!"".equals(getQ_reduceDateE()))
				sql+=" and a.reduceDate <= " + Common.sqlChar(ul._transToCE_Year(getQ_reduceDateE())) ;
			if (!"".equals(getQ_caseName()))
				sql+=" and a.caseName like " + Common.sqlChar(getQ_caseName()+"%") ;
			if (!"".equals(getQ_proofDoc()))
				sql+=" and a.proofDoc like " + Common.sqlChar(getQ_proofDoc()+"%") ;
			if (!"".equals(getQ_proofNoS()))
				sql+=" and a.proofNo >= " + Common.sqlChar(getQ_proofNoS()) ;
			if (!"".equals(getQ_proofNoE()))
				sql+=" and a.proofNo <= " + Common.sqlChar(getQ_proofNoE()) ;
			if (!"".equals(getQ_writeDateS()))
				sql+=" and a.writeDate >= " + Common.sqlChar(ul._transToCE_Year(getQ_writeDateS())) ;
			if (!"".equals(getQ_writeDateE()))
				sql+=" and a.writeDate <= " + Common.sqlChar(ul._transToCE_Year(getQ_writeDateE())) ;    
			if (!"".equals(getQ_approveOrg()))
				sql+=" and a.approveOrg = " + Common.sqlChar(getQ_approveOrg()) ;
			if (!"".equals(getQ_cause()))
				sql+=" and b.cause = " + Common.sqlChar(getQ_cause()) ;			
			if (!"".equals(getQ_newEnterOrg()))
				sql+=" and b.newEnterOrg = " + Common.sqlChar(getQ_newEnterOrg()) ;
			if (!"".equals(getQ_propertyNoS()))
				sql+=" and b.propertyNo >= " + Common.sqlChar(getQ_propertyNoS());		
			if (!"".equals(getQ_propertyNoE()))
				sql+=" and b.propertyNo<=" + Common.sqlChar(getQ_propertyNoE());						
			if (!"".equals(getQ_serialNoS()))
				sql+=" and b.serialNo >= " + Common.sqlChar(getQ_serialNoS());		
			if (!"".equals(getQ_serialNoE()))
				sql+=" and b.serialNo<=" + Common.sqlChar(getQ_serialNoE());			
			if (!"".equals(getQ_propertyKind()))
				sql+=" and b.propertyKind = " + Common.sqlChar(getQ_propertyKind());
			if (!"".equals(getQ_fundType()))
				sql+=" and b.fundType = " + Common.sqlChar(getQ_fundType()) ;
			if (!"".equals(getQ_valuable()))
				sql+=" and b.valuable = " + Common.sqlChar(getQ_valuable()) ;
			if (!"".equals(getQ_dealCaseNo()))
				sql+=" and b.dealCaseNo = " + Common.sqlChar(Common.formatFrontZero(getQ_dealCaseNo(),10)) ;
			if (!"".equals(getQ_reduceDeal()))
				sql+=" and b.reduceDeal = " + Common.sqlChar(getQ_reduceDeal()) ;
			if (!"".equals(getQ_shiftOrg()))
				sql+=" and b.shiftOrg = " + Common.sqlChar(getQ_shiftOrg()) ;
			if (!"".equals(getQ_moveDateS()))
				sql+=" and b.moveDate >= " + Common.sqlChar(ul._transToCE_Year(getQ_moveDateS())) ;
			if (!"".equals(getQ_moveDateE()))
				sql+=" and b.moveDate <= " + Common.sqlChar(ul._transToCE_Year(getQ_moveDateE())) ;
			if (!"".equals(getQ_differenceKind()))
				sql+=" and a.differencekind = " + Common.sqlChar(getQ_differenceKind()) ;
			if (!"".equals(getQ_proofYear()))
				sql+=" and a.proofyear like " + Common.sqlChar("%" + getQ_proofYear() + "%");
			if (!"".equals(getQ_userNote()))
				sql+=" and b.userNote like " + Common.sqlChar("%"+getQ_userNote()+"%") ;
			if (!"".equals(getQ_keepUnit()))
				sql+=" and b.keepUnit = " + Common.sqlChar(getQ_keepUnit()) ;	    
			if (!"".equals(getQ_keeper()))
				sql+=" and b.keeper = " + Common.sqlChar(getQ_keeper()) ;	    
			if (!"".equals(getQ_useUnit()))
				sql+=" and b.useUnit = " + Common.sqlChar(getQ_useUnit()) ;	    
			if (!"".equals(getQ_userNo()))
				sql+=" and b.userNo = " + Common.sqlChar(getQ_userNo()) ;	   
			if(!"".equals(getQ_place1()))
				sql+=" and b.place1 = " + Common.sqlChar(getQ_place1());
			if(!"".equals(getQ_place()))
				sql+=" and b.place like " + Common.sqlChar("%"+getQ_place()+"%");
		}

		if ("1".equals(this.getRoleid())){			
			sql += " and ( b.keeper = " + Common.sqlChar(this.getKeeperno())+
						 " or a.editid = " + Common.sqlChar(this.getUserID()) +
						 " )";	
			
		}else if ("2".equals(this.getRoleid())){
			sql += " and ( b.keepunit = " + Common.sqlChar(this.getUnitID())+
						 " or b.keeper = " + Common.sqlChar(this.getKeeperno())+
						 " or a.editid = " + Common.sqlChar(this.getUserID()) +
						 " )";	
			
		}else if ("3".equals(this.getRoleid())){
			
		}
		
		if("Y".equals(getIsAdminManager())){
			
		}else{
			sql += " and a.enterorg = '" + this.getOrganID() + "'";
		}
		
		//排序方式不能變更,會影響全部入帳(因為UNTNE_Nonexp要計算bookAmount,bookValue所以需要排序將同一個enterOrg,ownership,propertyNo放在一起)
		sql+=" order by proofyear desc,a.proofno desc ";
		
		ResultSet rs = db.querySQL(sql,true);
		//把查到的資料放入list裡..
		//和.jsp裡的 List區 內容要搭配起來
		processCurrentPageAttribute(rs);
		if (getTotalRecord() > 0) {
			int count = getRecordStart();
			int end = getRecordEnd();
			do {
				if (count > end)
					break;
				String rowArray[]=new String[10];
				rowArray[0]=rs.getString("enterOrg"); 
				rowArray[1]=rs.getString("ownership"); 
				rowArray[2]=rs.getString("ownershipName");
				rowArray[3]=differencekindMap.get(rs.getString("differencekind"));
				rowArray[4]=rs.getString("caseNo"); 
				rowArray[5]=rs.getString("proofyear")+"年"+rs.getString("proofdoc")+"字第"+rs.getString("proofno")+"號" ;  
				rowArray[6]=ul._transToROC_Year(rs.getString("writeDate")); 
				rowArray[7]=ul._transToROC_Year(rs.getString("reduceDate")); 
				rowArray[8]=rs.getString("verify");
				rowArray[9]=rs.getString("differenceKind");
			
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
	setNewPage();
	return objList;
}


private String checkBookValue(){
	Database db=new Database();
	String flag="N";
	try{
		String sql="select "+
						" case(sum(case b.submitcitygov when 'Y' then 1 else 0 end) when 0 then 'N' else 'Y' end), "+
						" case(case when sum(b.adjustbookvalue)>=2000000"+
						" then 1"+
						" else 0"+
						" end"+
						" when 0 then 'N' else 'Y' end)"+
					" from untne_reduceproof a, untne_reducedetail b"+
					" where 1=1"+
						" and a.enterorg=b.enterorg and a.ownership=b.ownership and a.caseno=b.caseno"+
						" and a.enterorg=" + Common.sqlChar(enterOrg)+
						" and a.caseno=" + Common.sqlChar(caseNo);
		ResultSet rs=db.querySQL(sql);
		if(rs.next()){
			//減損單入帳時，檢核同一張減損單，多筆減損之價值加總是否達200萬者
//			if("Y".equals(rs.getString(2))){
//				flag="Y2";
//			}
			
			//減損單入帳時，減損明細資料中，有任一筆財產報府核定為「是」
			if("Y".equals(rs.getString(1))){
				flag="Y1";	
			}
		}
		rs.close();
	}catch(Exception e){
		
	}finally{
		db.closeAll();
	}   
	return flag;
}

private void setOldPage() throws Exception{
	if (!"".equals(getPageSize1())) {
		setPageSize(Integer.parseInt(getPageSize1()));
		setTotalPage(Integer.parseInt(getTotalPage1()));
		setCurrentPage(Integer.parseInt(getCurrentPage1()));
		setTotalRecord(Integer.parseInt(getTotalRecord1()));
		setRecordStart(Integer.parseInt(getRecordStart1()));
		setRecordEnd(Integer.parseInt(getRecordEnd1()));
	}
}

private void setNewPage() throws Exception{
	setPageSize1("" + getPageSize());
	setTotalPage1("" + getTotalPage());
	setCurrentPage1("" + getCurrentPage());
	setTotalRecord1("" + getTotalRecord());
	setRecordStart1("" + getRecordStart());
	setRecordEnd1("" + getRecordEnd());
}

//全部入帳的步驟備註：
//逐筆=>approveAll() -> update UNTNE_ReduceProof
//               ↘----> checkVerify() --> update UNTNE_ReduceDetail 檔案
//								↘----> 查最大年月及符合條件與否? 
//				  ↘----> updNonexpDetail()	--> update UNTNE_NonexpDetail 檔案
//				  ↘----> updNonexp() 		--> update UNTNE_Nonexp 檔案

//全部入帳
public void approveAll()throws Exception{
	UNTCH_Tools ul = new UNTCH_Tools();
	Database db = new Database();
	try {    
	    int i = 0,counter = 0;
		String rowArray[]=new String[10];
		java.util.Iterator it= queryAll().iterator();
		String[] execSQLArray;
		String strSQL = "";
		while(it.hasNext()){
		    counter++;
			rowArray= (String[])it.next();
			if(rowArray[8].equals("否")){
			    i++;
				setVerify("Y");
				enterOrg = rowArray[0];
				ownership= rowArray[1];
				caseNo   = rowArray[4];
				setReduceDate(rowArray[7]==null || "".equals(rowArray[7]) ? Datetime.getYYYMMDD():rowArray[7]);
				setEditID(getUserID());
				setEditDate(Datetime.getYYYMMDD());
				setEditTime(Datetime.getHHMMSS());	
				if (enterOrg.equals(getOrganID())) {
				    strSQL += "update UNTNE_ReduceProof set"+
				                " verify = 'Y'," +
				                " reduceDate = " + Common.sqlChar(getReduceDate()) + "," +
				    			" editID = " + Common.sqlChar(getEditID()) + "," +
				    			" editDate = " + Common.sqlChar(ul._transToCE_Year(getEditDate())) + "," +
				    			" editTime = " + Common.sqlChar(getEditTime()) + 	                
				        		" where 1=1 " + 
				    			" and enterOrg = " + Common.sqlChar(enterOrg) +
				    			" and caseNo = " + Common.sqlChar(caseNo) +
				    			":;:";				
				    strSQL += checkVerify();
				    strSQL += updNonexpDetail();
				    strSQL += updNonexp();
					i=i+4;
					if (!super.beforeExecCheck(this.getUpdateCheckSQL())){
				           setVerifyError("Y");
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
		}else{                                   
			setErrorMsg("全部入帳完成");
			setStateUpdateSuccess();    
			queryOne();                 
		}                               	
	} catch (Exception e) {
		e.printStackTrace();
	} finally {
		db.closeAll();
	}		
}

//入帳設定
protected String checkVerify(){
	UNTCH_Tools ul = new UNTCH_Tools();
    String sql="";
	//1.check是否可以更改
	//1.1減損日期之年月不可小於等於最大的月結年月
		if((Double.parseDouble(getMaxClosingYM(enterOrg))) < (Double.parseDouble(ul._transToCE_Year(getReduceDate()).substring(0,6)))){
		//更改 UNTNE_ReduceDetail 檔案資料
		sql = "update UNTNE_ReduceDetail set"+
		        " verify = 'Y'," +
		        " reduceDate = " + Common.sqlChar(ul._transToCE_Year(getReduceDate())) + "," +
				" editID = " + Common.sqlChar(getEditID()) + "," +
				" editDate = " + Common.sqlChar(ul._transToCE_Year(getEditDate())) + "," +
				" editTime = " + Common.sqlChar(getEditTime()) + 	                
				" where 1=1 " + 
				" and enterOrg = " + Common.sqlChar(enterOrg) +
				" and caseNo = " + Common.sqlChar(caseNo) +
				":;:"; 
		}else{
	    setVerifyError("Y");
		setStateUpdateError();
        setErrorMsg("入帳設定有錯，減損日期之年月必須大於月結年月！");
		}
	return sql;
}

//異動 UNTNE_NonexpDetail 檔案
protected String updNonexpDetail(){
	UNTCH_Tools ul = new UNTCH_Tools();
	Database db = new Database();
	String sqlNonexp = "";
	String checkDetail="";
	//1.更新 Nonexp.bookvalue = bookvalue + adjustdetail.adjustbookvalue
	//2.更新 Nonexpdetail.bookvalue = adjustdetail.newbookvalue
	try {
		String sql =" select b.enterOrg, b.ownership, b.differencekind, b.propertyNo, b.serialNo, "+
					" a.bookAmount, b.adjustBookAmount, a.bookValue, b.adjustBookValue, " +
					" b.newBookAmount, b.cause, b.cause1 "+
					" from UNTNE_NonexpDetail a, UNTNE_ReduceDetail b "+
					" where 1=1 " +
					" and a.enterOrg=b.enterOrg and a.ownership=b.ownership and a.propertyNo=b.propertyNo and a.serialNo=b.serialNo and a.differenceKind=b.differenceKind "+
					" and b.enterOrg = " + Common.sqlChar(enterOrg) +
					" and b.caseNo = " + Common.sqlChar(caseNo) +
					" order by a.enterOrg, a.ownership, a.propertyNo, a.serialNo "+
					"" ;
		ResultSet rs = db.querySQL(sql);
		while (rs.next()){
		    checkDetail="Y";
		    if(rs.getString("newBookAmount").equals("0.00")){
		    	sqlNonexp += " update UNTNE_NonexpDetail set " +
	    		  			  " dataState = '2', " +
	    		  			  " reduceDate = " + Common.sqlChar(ul._transToCE_Year(getReduceDate())) + "," +
	    		  			  " reduceCause = " + Common.sqlChar(rs.getString("cause")) + "," +
	    		  			  " reduceCause1 = " + Common.sqlChar(rs.getString("cause1")) + "," +
	    		  			  " bookAmount =  "  + Common.sqlInt((String.valueOf(Double.parseDouble(rs.getString("bookAmount"))-Double.parseDouble(rs.getString("adjustBookAmount"))))+"") + "," +
				    		  " bookValue =  "  + Common.sqlInt((Integer.parseInt(rs.getString("bookValue"))-Integer.parseInt(rs.getString("adjustBookValue")))+"") +
							  " where 1=1 " + 
							  " and enterOrg = " + Common.sqlChar(rs.getString("enterOrg")) +
							  " and ownership = " + Common.sqlChar(rs.getString("ownership")) +
							  " and propertyNo = " + Common.sqlChar(rs.getString("propertyNo")) +
							  " and serialNo = " + Common.sqlChar(rs.getString("serialNo")) +
							  " and differenceKind = " + Common.sqlChar(rs.getString("differenceKind")) +
							  ":;:" ;
		    }else{
		    	sqlNonexp += " update UNTNE_NonexpDetail set " +
				    		  " bookAmount =  "  + Common.sqlInt((String.valueOf(Double.parseDouble(rs.getString("bookAmount"))-Double.parseDouble(rs.getString("adjustBookAmount"))))+"") + "," +
				    		  " bookValue =  "  + Common.sqlInt((Integer.parseInt(rs.getString("bookValue"))-Integer.parseInt(rs.getString("adjustBookValue")))+"") +
							  " where 1=1 " + 
							  " and enterOrg = " + Common.sqlChar(rs.getString("enterOrg")) +
							  " and ownership = " + Common.sqlChar(rs.getString("ownership")) +
							  " and propertyNo = " + Common.sqlChar(rs.getString("propertyNo")) +
							  " and serialNo = " + Common.sqlChar(rs.getString("serialNo")) +
							  " and differenceKind = " + Common.sqlChar(rs.getString("differenceKind")) +
							  ":;:" ;
		    }
		}
        if(!checkDetail.equals("Y")){
            setVerifyError("Y");
            setErrorMsg("該筆減損單之明細資料標籤要有資料才能入帳！");
        }
	} catch (Exception e) {
		e.printStackTrace();
	} finally {
		db.closeAll();
	}
	return sqlNonexp;
}

//異動 UNTNE_Nonexp 檔案
protected String updNonexp(){
	Database db = new Database();
	String sqlNonexp = "";
	UNTNE013F obj = this;
	int count=0;
	try {
		String sql =" select b.enterOrg, b.ownership, a.lotNo, b.propertyNo, b.serialNo,b.differenceKind, "+
					" a.bookAmount, b.adjustBookAmount, a.bookValue, b.adjustBookValue "+
					" from UNTNE_Nonexp a, UNTNE_ReduceDetail b "+
					" where 1=1 " +
					" and a.enterOrg=b.enterOrg and a.ownership=b.ownership and a.propertyNo=b.propertyNo and a.lotNo=b.lotNo and a.differenceKind=b.differenceKind "+
					" and b.enterOrg = " + Common.sqlChar(enterOrg) +
					" and b.caseNo = " + Common.sqlChar(caseNo) +
					" order by a.enterOrg, a.ownership, a.propertyNo, a.lotNo, b.serialNo "+
					"" ;
		ResultSet rs = db.querySQL(sql);
		while (rs.next()){
		    obj.setCheckEnterOrg(rs.getString("enterOrg"));
		    obj.setCheckOwnership(rs.getString("ownership"));
		    obj.setCheckLotNo(rs.getString("lotNo"));
		    obj.setCheckPropertyNo(rs.getString("propertyNo"));
		    obj.setCheckDifferenceKind(rs.getString("differenceKind"));
		    //=======
			changeNo = rs.getString("enterOrg")+rs.getString("ownership")+rs.getString("propertyNo")+rs.getString("lotNo")+rs.getString("differenceKind");
			Integer I = (Integer) h.get(changeNo+"_sumBookAmount");
			if (I!=null) {
				if(!(obj.getCheckEnterOrg()).equals(obj.getCheckOldEnterOrg()) || !(obj.getCheckOwnership()).equals(obj.getCheckOldOwnership()) || !(obj.getCheckPropertyNo()).equals(obj.getCheckOldPropertyNo()) || !(obj.getCheckLotNo()).equals(obj.getCheckOldLotNo()) || !(obj.getCheckDifferenceKind()).equals(obj.getCheckOldDifferenceKind())){
					h.put(changeNo+"_sumBookAmount", new Integer(((Integer) h.get(changeNo+"_sumBookAmount")).intValue()));
					h.put(changeNo+"_sumBookValue", new Integer(((Integer) h.get(changeNo+"_sumBookValue")).intValue()));
				}else{
					h.put(changeNo+"_sumBookAmount", new Integer(sumBookAmount));
					h.put(changeNo+"_sumBookValue", new Integer(sumBookValue));
				}
			} else {
				h.put(changeNo+"_sumBookAmount", new Integer(rs.getInt("BookAmount")));
				h.put(changeNo+"_sumBookValue", new Integer(rs.getInt("BookValue")));
			}    
			//=========	
		    if(!(obj.getCheckEnterOrg()).equals(obj.getCheckOldEnterOrg()) || !(obj.getCheckOwnership()).equals(obj.getCheckOldOwnership()) || !(obj.getCheckPropertyNo()).equals(obj.getCheckOldPropertyNo()) || !(obj.getCheckLotNo()).equals(obj.getCheckOldLotNo()) || !(obj.getCheckDifferenceKind()).equals(obj.getCheckOldDifferenceKind())){
		    	 sumBookAmount=((Integer) h.get(changeNo+"_sumBookAmount")).intValue();
			     sumBookValue=((Integer) h.get(changeNo+"_sumBookValue")).intValue();
		    }   
		        sumBookAmount -= rs.getInt("adjustBookAmount");
		        sumBookValue -= rs.getInt("adjustBookValue");
		    //==========
	    	obj.setCheckOldEnterOrg(obj.getCheckEnterOrg());
	    	obj.setCheckOldOwnership(obj.getCheckOwnership());
	    	obj.setCheckOldLotNo(obj.getCheckLotNo());
	    	obj.setCheckOldPropertyNo(obj.getCheckPropertyNo());
	    	obj.setCheckOldDifferenceKind(obj.getCheckDifferenceKind());
	    	if(sumBookAmount<=0){
		        sqlNonexp += " update UNTNE_Nonexp set " +
		        			  " dataState='2'," +
			  				  " bookAmount =  "  + Common.sqlInt(sumBookAmount+"") + "," +
				    		  " bookValue =  "  + Common.sqlInt(sumBookValue+"") +
							  " where 1=1 " + 
							  " and enterOrg = " + Common.sqlChar(rs.getString("enterOrg")) +
							  " and ownership = " + Common.sqlChar(rs.getString("ownership")) +
							  " and propertyNo = " + Common.sqlChar(rs.getString("propertyNo")) +
							  " and lotNo = " + Common.sqlChar(rs.getString("lotNo")) +
							  " and differenceKind = " + Common.sqlChar(rs.getString("differenceKind")) +
							  ":;:" ;
	    	}else{
		        sqlNonexp += " update UNTNE_Nonexp set " +
	  		  				  " bookAmount =  "  + Common.sqlInt(sumBookAmount+"") + "," +
				    		  " bookValue =  "  + Common.sqlInt(sumBookValue+"") +
							  " where 1=1 " + 
							  " and enterOrg = " + Common.sqlChar(rs.getString("enterOrg")) +
							  " and ownership = " + Common.sqlChar(rs.getString("ownership")) +
							  " and propertyNo = " + Common.sqlChar(rs.getString("propertyNo")) +
							  " and lotNo = " + Common.sqlChar(rs.getString("lotNo")) +
							  " and differenceKind = " + Common.sqlChar(rs.getString("differenceKind")) +
							  ":;:" ;
	    	}
			h.put(changeNo+"_sumBookAmount", new Integer(sumBookAmount));
			h.put(changeNo+"_sumBookValue", new Integer(sumBookValue));
	    	count++;	    	
		}
	} catch (Exception e) {
		e.printStackTrace();
	} finally {
		db.closeAll();
	}
	return sqlNonexp;
}

//回復入帳設定
public void approveRe()throws Exception{	
	Database db = new Database();
	String enterOrgQuery = "";
	String ownershipQuery = "";
	String caseNoQuery = "";
	String propertyNoQuery = "";
	String serialNoQuery = "";
	String differenceKindQuery = "";
	String reduceSql = "",reduceCount = "";
	String adjustSql = "",adjustCount = "";
	String moveSql = "",moveCount = "";
	String reduceOriginal = "",reduceMax = "",reduceMaxSql = "";
	String adjustMax = "",adjustMaxSql = "";
	String moveMax = "",moveMaxSql = "";
	String moveString="",adjustString="",reduceString="";
	int count = 0;
	try {    
		String[] execSQLArray;
		String strSQL = "";
		String sql ="select a.caseNo, a.enterOrg, b.ownership, b.propertyNo, b.serialNo, b.lotNo,b.differenceKind, " +
					" a.editDate, a.editTime, a.reduceDate, b.adjustBookAmount, b.adjustBookValue " +
					" from untne_reduceProof a left join untne_reduceDetail b on  a.enterOrg = b.enterOrg and a.ownership = b.ownership and a.caseNo = b.caseNo" +
					" where 1=1 " +
	    			" and a.enterOrg = " + Common.sqlChar(enterOrg) +
	    			" and a.caseNo = " + Common.sqlChar(caseNo) +
					" order by b.enterOrg, b.ownership, b.propertyNo, b.lotNo, b.serialNo" +
					"" ;
		ResultSet rs = db.querySQL(sql);
		while (rs.next()){
			count++;
			enterOrgQuery = rs.getString("enterOrg");
			ownershipQuery = rs.getString("ownership");
			caseNoQuery = rs.getString("caseNo");
			propertyNoQuery = rs.getString("propertyNo");
			serialNoQuery = rs.getString("serialNo");
			differenceKindQuery = rs.getString("differenceKind");
			//該減損單之明細資料,存在未入帳的「非消耗品減損單明細檔UNTNE_ReduceDetail」資料，則提示使用者
			reduceSql = "select count(*) count from UNTNE_REDUCEDETAIL " +
						" where enterorg="+Common.sqlChar(enterOrgQuery)+" and ownership="+Common.sqlChar(ownershipQuery)+" and propertyno="+Common.sqlChar(propertyNoQuery)+" and serialno="+Common.sqlChar(serialNoQuery)+" and differencekind="+Common.sqlChar(differenceKindQuery)+" and verify='N'";
			reduceCount = MaxClosingYM.getCountValues(reduceSql);
			if(!reduceCount.equals("0")){
				reduceSql = "select caseno from UNTNE_REDUCEDETAIL " +
						" where enterorg="+Common.sqlChar(enterOrgQuery)+" and ownership="+Common.sqlChar(ownershipQuery)+" and propertyno="+Common.sqlChar(propertyNoQuery)+" and serialno="+Common.sqlChar(serialNoQuery)+" and differencekind="+Common.sqlChar(differenceKindQuery)+" and verify='N'";				
				reduceString =reduceString+"減損單電腦單號："+TCGHCommon.getLookup(reduceSql)+" 財產區分別:"+TCGHCommon.getSYSCODEName(differenceKindQuery,"DFK")+" 編號:"+propertyNoQuery+"分號:"+serialNoQuery+"\\n";
			}
			//該減損單之明細資料,存在未入帳的「非消耗品增減值單明細檔UNTNE_AdjustDetail」資料，則提示使用者
			adjustSql = "select count(*) count from UNTNE_ADJUSTDETAIL " +
						" where enterorg="+Common.sqlChar(enterOrgQuery)+" and ownership="+Common.sqlChar(ownershipQuery)+" and propertyno="+Common.sqlChar(propertyNoQuery)+" and serialno="+Common.sqlChar(serialNoQuery)+" and differencekind="+Common.sqlChar(differenceKindQuery)+" and verify='N'";
			adjustCount = MaxClosingYM.getCountValues(adjustSql);
			if(!adjustCount.equals("0")){
				adjustSql = "select caseno from UNTNE_ADJUSTDETAIL " +
						" where enterorg="+Common.sqlChar(enterOrgQuery)+" and ownership="+Common.sqlChar(ownershipQuery)+" and propertyno="+Common.sqlChar(propertyNoQuery)+" and serialno="+Common.sqlChar(serialNoQuery)+" and differencekind="+Common.sqlChar(differenceKindQuery)+" and verify='N'";				
				adjustString =adjustString+"增減值單電腦單號："+TCGHCommon.getLookup(adjustSql)+" 財產區分別:"+TCGHCommon.getSYSCODEName(differenceKindQuery,"DFK")+" 編號:"+propertyNoQuery+"分號:"+serialNoQuery+"\\n";
			}			
			//該減損單之明細資料,存在未入帳的「非消耗品移動單明細檔UNTNE_MoveDetail」資料，則提示使用者
			moveSql = "select count(*) count from UNTNE_MOVEDETAIL " +
					" where enterorg="+Common.sqlChar(enterOrgQuery)+" and ownership="+Common.sqlChar(ownershipQuery)+" and propertyno="+Common.sqlChar(propertyNoQuery)+" and serialno="+Common.sqlChar(serialNoQuery)+" and differencekind="+Common.sqlChar(differenceKindQuery)+" and verify='N'";
			moveCount = MaxClosingYM.getCountValues(moveSql);
			if(!moveCount.equals("0")){
				moveSql = "select caseno from UNTNE_MOVEDETAIL " +
						" where enterorg="+Common.sqlChar(enterOrgQuery)+" and ownership="+Common.sqlChar(ownershipQuery)+" and propertyno="+Common.sqlChar(propertyNoQuery)+" and serialno="+Common.sqlChar(serialNoQuery)+" and differencekind="+Common.sqlChar(differenceKindQuery)+" and verify='N'";				
				moveString =moveString+"移動單電腦單號："+TCGHCommon.getLookup(moveSql)+" 財產區分別:"+TCGHCommon.getSYSCODEName(differenceKindQuery,"DFK")+" 編號:"+propertyNoQuery+"分號:"+serialNoQuery+"\\n";
			}
			//------------------------------------------------------------------------------------------------
			reduceOriginal = rs.getString("reduceDate");
			//該減損單之(減損日期+異動日期+異動時間)≦減損單明細資料之其他減損單之最大的(減損日期+異動日期+異動時間)則提示使用者
			reduceMaxSql = "select count(*) count from UNTNE_REDUCEDETAIL " +
							" where enterorg="+Common.sqlChar(enterOrgQuery)+" and ownership="+Common.sqlChar(ownershipQuery)+" and propertyno="+Common.sqlChar(propertyNoQuery)+" and serialno="+Common.sqlChar(serialNoQuery)+" and differencekind="+Common.sqlChar(differenceKindQuery)+" and verify='Y' " +
							" and '" + reduceOriginal + "'<= reducedate" +
							" and caseno!="+Common.sqlChar(caseNoQuery);
			reduceMax = MaxClosingYM.getCountValues(reduceMaxSql);
			
			//該減損單之(增減日期+異動日期+異動時間)≦減損單明細資料之其他增減值單之最大的(增減值日期+異動日期+異動時間)則提示使用者
			adjustMaxSql = "select count(*) count from UNTNE_ADJUSTDETAIL " +
							" where enterorg="+Common.sqlChar(enterOrgQuery)+" and ownership="+Common.sqlChar(ownershipQuery)+" and propertyno="+Common.sqlChar(propertyNoQuery)+" and serialno="+Common.sqlChar(serialNoQuery)+" and differencekind="+Common.sqlChar(differenceKindQuery)+" and verify='Y' " +
							" and '" + reduceOriginal + "'<= adjustdate" ;
			adjustMax = MaxClosingYM.getCountValues(adjustMaxSql);
			
			//該減損單之(減損日期+異動日期+異動時間)≦減損單明細資料之其他移動單之最大的(移動日期+異動日期+異動時間)則提示使用者
			moveMaxSql = "select count(*) count from UNTNE_MOVEPROOF a, UNTNE_MOVEDETAIL b " +
						" where 1=1 and a.caseno=b.caseno and a.enterorg=b.enterorg and a.ownership=b.ownership " +
						" and b.enterorg="+Common.sqlChar(enterOrgQuery)+" and b.ownership="+Common.sqlChar(ownershipQuery)+" and b.propertyno="+Common.sqlChar(propertyNoQuery)+" and b.serialno="+Common.sqlChar(serialNoQuery)+" and differencekind="+Common.sqlChar(differenceKindQuery)+" and a.verify='Y' " +
						" and '" + reduceOriginal + "'<= a.movedate " ;
			moveMax = MaxClosingYM.getCountValues(moveMaxSql);
			//------------------------------------------------------------------------------------------------
			if( verify.equals("Y") && reduceCount.equals("0") && adjustCount.equals("0") && moveCount.equals("0") && reduceMax.equals("0") && adjustMax.equals("0") && moveMax.equals("0")){
				//依據該減損單「入帳機關enterOrg＋權屬ownership＋電腦單號caseNo」設定
				if(count==1){
					strSQL += "update UNTNE_REDUCEPROOF set reducedate =null,verify ='N' " +
							" where enterorg=" + Common.sqlChar(enterOrgQuery) +
							" and caseno=" + Common.sqlChar(caseNoQuery) +
							":;:";				
					strSQL += "update UNTNE_REDUCEDETAIL set reducedate =null,verify ='N' " +
							" where enterorg=" + Common.sqlChar(enterOrgQuery) +
							" and caseno=" + Common.sqlChar(caseNoQuery) +
							":;:";
				}
				
				//依據減損單明細「入帳機關enterOrg＋權屬ownership＋財產編號propertyNo＋財產批號lotNo」設定
				strSQL +="update UNTNE_NONEXP set bookamount = (bookamount+" + rs.getInt("adjustbookamount") + ")," +
						" bookvalue = (bookvalue+" + rs.getInt("adjustBookValue") + ")," +
						" datastate ='1' " +
						" where enterorg=" + Common.sqlChar(enterOrgQuery) +
						" and ownership=" + Common.sqlChar(ownershipQuery) +
						" and propertyno=" + Common.sqlChar(propertyNoQuery) +
						" and lotno=" + Common.sqlChar(rs.getString("lotNo")) +
						" and differencekind=" + Common.sqlChar(differenceKindQuery) +
						":;:";

				//依據減損單明細「入帳機關enterOrg＋權屬ownership＋財產編號propertyNo＋財產分號serialNo」設定
				strSQL +="update UNTNE_NONEXPDETAIL set bookamount = (bookamount+" + rs.getInt("adjustBookAmount") + ")," +
						" bookvalue = (bookvalue+" + rs.getInt("adjustBookValue") + ")," +
						" datastate ='1', " +
						" reducedate =null, " +
						" reducecause =null, " +
						" reducecause1 =null " +
						" where enterorg=" + Common.sqlChar(enterOrgQuery) +
						" and ownership=" + Common.sqlChar(ownershipQuery) +
						" and propertyno=" + Common.sqlChar(propertyNoQuery) +
						" and serialno=" + Common.sqlChar(serialNoQuery) +
						" and differencekind=" + Common.sqlChar(differenceKindQuery) +
						":;:";
				//----------------------------------------
			}else{
				setVerifyError("Y");
                if(verify.equals("N")){
					setErrorMsg("尚未入帳，請直接修改資料即可！");
				}else if(!reduceCount.equals("0")){
					setErrorMsg("減損作業存在未入帳的資料，無法回復入帳！\\n"+reduceString);
				}else if(!adjustCount.equals("0")){
					setErrorMsg("增減值作業存在未入帳的資料，無法回復入帳！\\n"+adjustString);
				}else if(!moveCount.equals("0")){
					setErrorMsg("保管使用異動作業存在未入帳的資料，無法回復入帳！\\n"+moveString);
				}else if(!reduceMax.equals("0")){
					setErrorMsg("並非最後一筆入帳(減損)的資料，無法回復入帳！");
				}else if(!adjustMax.equals("0")){
					setErrorMsg("並非最後一筆入帳(增減值)的資料，無法回復入帳！");
				}else if(!moveMax.equals("0")){
					setErrorMsg("並非最後一筆入帳(移動)的資料，無法回復入帳！");
				}
			}			
			if ("Y".equals(getVerifyError())) {
			    break;
			}
		}
		//System.out.println("回復："+strSQL);
		if (!"Y".equals(getVerifyError())) {
			execSQLArray = strSQL.split(":;:");
			db.excuteSQL_NoChange(execSQLArray);
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
//取得最大的月結年月
protected String getMaxClosingYM(String enterOrg){
	Database db = new Database();
	ResultSet rs;	
	String closing1YM ="" ;
	String sql="select max(closing1ym) as closing1ym from UNTAC_CLOSINGNE " +
		" where enterorg = " + Common.sqlChar(enterOrg) +
		"";		
	try {		
		rs = db.querySQL(sql);
		if (rs.next()){
		    closing1YM = rs.getString("closing1YM")==null?"000000":rs.getString("closing1YM");
		} else {
			closing1YM = "000000";
		}
	} catch (Exception e) {
		e.printStackTrace();
	} finally {
		db.closeAll();
	}
	return closing1YM;
}

public void updateNewData() throws Exception{
	try{
		Database db = new Database();			
		String sqlne = "update a" +	
				  " set a.useyear = (DateDiff(month, a.buydate , CONVERT(varchar(100), GETDATE(), 112)))/12, " +
				  " a.usemonth = (DateDiff(month, a.buydate , CONVERT(varchar(100), GETDATE(), 112)))%12 " +
				  " from UNTNE_REDUCEDETAIL a, UNTNE_REDUCEPROOF b" +
				  " where 1=1" +
				  " and a.enterorg = b.enterorg  and a.caseno = b.caseno and a.differencekind = b.differencekind and a.ownership = b.ownership" +
				  " and a.enterorg = " + Common.sqlChar(getEnterOrg()) +
				  " and a.caseno = " +  Common.sqlChar(getCaseNo()) +
				  " and a.verify = 'N' " +
				  " ;";
	
		db.excuteSQL(sqlne);
		setStateUpdateSuccess();
		setErrorMsg("帶入最新資料完成");
	}catch(Exception e){
		setStateUpdateError();
		setErrorMsg("帶入最新資料失敗!若問題持續,請洽詢系統管理者或相關承辦人員！");
	}
}
	
	//問題單2299 檢核入帳之減損單是否做過公務轉基金
	public boolean checkTransfer(String func) throws Exception {
		Database db = null;
		StringBuilder sql = new StringBuilder();
		String proofyear = "";
		String proofdoc = "";
		String proofno = "";
		boolean result = true;
		
		try {
			if (!"".equals(this.getAddcaseno())) {
				sql.append(" select * from UNTNE_ADDPROOF where enterorg = ").append(Common.sqlChar(this.getEnterOrg()))
				   .append(" and caseno = ").append(Common.sqlChar(this.getAddcaseno()));
				
				db = new Database();
				ResultSet rs = db.querySQL_NoChange(sql.toString());
				
				while(rs.next()) {
					proofyear = rs.getString("proofyear");
					proofdoc = rs.getString("proofdoc");
					proofno = rs.getString("proofno");
					break;
				}
				if (func == "transfer") {
					this.setErrorMsg("此減損單已執行過公務類轉作業基金，增加單單號:" + proofyear + "年" + proofdoc + "字 第" + proofno + "號" + "，若要重新執行公務轉基金，請先將此增加單刪除。");
				} else if (func == "approveRe") {
					this.setErrorMsg("此減損單已執行過公務類轉作業基金，增加單單號:" + proofyear + "年" + proofdoc + "字 第" + proofno + "號" + "，若要恢復入帳，請先將此增加單刪除。");
				}
				
				result = false;
			} else if (!"110".equals(this.getDifferenceKind())) {
				this.setErrorMsg("此減損單物品區分別非公務類，不可執行公務類轉作業基金。");
				result = false;
			}
		} catch(Exception e) {
			log._execLogError(e.getMessage());
		}
		return result;
	}
	
	//問題單2299 執行公務轉基金
	public void transfer(String table) throws Exception {		
		try {
			if (checkTransfer("transfer")) {
				String newCaseno = transferAddProof(table);
				transferAddDetails(table, newCaseno);
				transferAttachment(newCaseno);
				
				this.setErrorMsg("公務轉基金成功");
			}
		} catch(Exception e) {
			log._execLogError(e.getMessage());
			this.setErrorMsg("公務轉基金失敗，請洽系統工程師。");
		}
	}
	
	//問題單2299 公務轉基金 新增增加單
	private String transferAddProof(String table) throws Exception {
		Database db = null;
		StringBuilder sql = new StringBuilder();
		String differencekind = "120";
		
		try {
			String addCaseno = this.getNewCaseNo(table);  //新單據編號
			String proofNo = MaxClosingYM.getMaxProofNo("UNTNE_ADDPROOF", this.getEnterOrg(), this.getOwnership(), Datetime.getYYY());
			String notes =  Datetime.getYYYMMDD() + "由公務類轉為作業基金";
			db = new Database();
			
			sql.append(" insert into UNTNE_ADDPROOF (enterorg, ownership, caseno, differencekind, writedate, writeunit, proofyear,")
			   .append(" proofdoc, proofno, verify, notes, editid, editdate, edittime, reducecaseno) ")
			   .append(" VALUES( ").append(Common.sqlChar(this.getEnterOrg())).append(", ")
			   .append(Common.sqlChar(this.getOwnership())).append(", ")
			   .append(Common.sqlChar(addCaseno)).append(", ")
			   .append(Common.sqlChar(differencekind)).append(", ")
			   .append(Common.sqlChar(Datetime.getYYYYMMDD())).append(", ")
			   .append(Common.sqlChar(this.getWriteUnit())).append(", ")
			   .append(Common.sqlChar(Datetime.getYYY()))
			   .append(", '非耗品增', ")
			   .append(Common.sqlChar(proofNo))
			   .append(", 'N', ")
			   .append(Common.sqlChar(notes)).append(", ")
			   .append(Common.sqlChar(this.getUserID())).append(", ")
			   .append(Common.sqlChar(Datetime.getYYYYMMDD())).append(", ")
			   .append(Common.sqlChar(Datetime.getHHMMSS())).append(", ")
			   .append(Common.sqlChar(this.getCaseNo())).append(" ); ");
			sql.append(" update UNTNE_REDUCEPROOF set addcaseno = ").append(Common.sqlChar(addCaseno))
			   .append(" , editid = ").append(Common.sqlChar(this.getUserID()))
			   .append(" , editdate = ").append(Common.sqlChar(Datetime.getYYYYMMDD()))
			   .append(" , edittime = ").append(Common.sqlChar(Datetime.getHHMMSS()))
			   .append(" where enterorg = ").append(Common.sqlChar(this.getEnterOrg()))
			   .append(" and caseno = ").append(Common.sqlChar(this.getCaseNo())).append(" ; ");
			
			db.exeSQL_NoChange(sql.toString());
			
			return addCaseno;
		} catch(Exception e) {
			log._execLogError(e.getMessage());
			return null;
		} finally {
			db.closeAll();
		}		
	}
	
	//問題單2299 公務轉基金 新增明細
	private void transferAddDetails(String table, String addCaseno) {
		Database db = null;
		StringBuilder sql = new StringBuilder();
		String differencekind = "120";
		String sourceKind = "32"; //財產來源預設 32 作價撥充
		
		try {
			String reduceCaseNo = this.getCaseNo();   //減損單據編號
			db = new Database();
			
			//明細
			sql.append(" DECLARE rs CURSOR                                                                                                ")
				.append(" FOR SELECT a.enterorg,                                                                                           ")
				.append("            a.differencekind,                                                                                     ")
				.append("            a.ownership,                                                                                          ")
				.append("            a.propertyno,                                                                                         ")
				.append("            a.serialno                                                                                            ")
				.append("     FROM UNTNE_NONEXPDETAIL a                                                                                    ")
				.append("          JOIN UNTNE_REDUCEDETAIL b ON a.enterorg = b.enterorg                                                    ")
				.append("                                       AND a.differencekind = b.differencekind                                    ")
				.append("                                       AND a.ownership = b.ownership                                              ")
				.append("                                       AND a.propertyno = b.propertyno                                            ")
				.append("                                       AND a.serialno = b.serialno                                                ")
				.append("     WHERE a.enterorg = ").append(Common.sqlChar(this.getEnterOrg()))
				.append("           AND b.caseno = ").append(Common.sqlChar(reduceCaseNo))
				.append("     ORDER BY a.enterorg,                                                                                         ")
				.append("              a.ownership,                                                                                        ")
				.append("              a.differencekind,                                                                                   ")
				.append("              a.propertyno,                                                                                       ")
				.append("              a.serialno;                                                                                         ")
				.append(" DECLARE @enterorg NVARCHAR(11);                                                                                  ")
				.append(" DECLARE @differentkind NVARCHAR(3);                                                                              ")
				.append(" DECLARE @ownership NVARCHAR(1);                                                                                  ")
				.append(" DECLARE @propertyno NVARCHAR(11);                                                                                ")
				.append(" DECLARE @serialno NVARCHAR(7);                                                                                   ")
				.append(" DECLARE @caseserialno NVARCHAR(10)= 1;                                                                           ")
				.append(" OPEN rs;                                                                                                         ")
				.append(" FETCH NEXT FROM rs INTO @enterorg, @differentkind, @ownership, @propertyno, @serialno;                           ")
				.append(" WHILE @@Fetch_Status = 0                                                                                         ")
				.append("     BEGIN                                                                                                        ")
				.append("         INSERT INTO UNTNE_NONEXPDETAIL                                                                           ")
				.append("         (enterorg,                                                                                               ")
				.append("          ownership,                                                                                              ")
				.append("          caseno,                                                                                                 ")
				.append("          differencekind,                                                                                         ")
				.append("          propertyno,                                                                                             ")
				.append("          lotno,                                                                                                  ")
				.append("          serialno,                                                                                               ")
				.append("          datastate,                                                                                              ")
				.append("          verify,                                                                                                 ")
				.append("          originalamount,                                                                                         ")
				.append("          originalbv,                                                                                             ")
				.append("          bookamount,                                                                                             ")
				.append("          bookvalue,                                                                                              ")
				.append("          licenseplate,                                                                                           ")
				.append("          purpose,                                                                                                ")
				.append("          originalkeepunit,                                                                                       ")
				.append("          originalkeeper,                                                                                         ")
				.append("          originaluseunit,                                                                                        ")
				.append("          originaluser,                                                                                           ")
				.append("          originalusernote,                                                                                       ")
				.append("          originalplace1,                                                                                         ")
				.append("          originalplace,                                                                                          ")
				.append("          keepunit,                                                                                               ")
				.append("          keeper,                                                                                                 ")
				.append("          useunit,                                                                                                ")
				.append("          userno,                                                                                                 ")
				.append("          usernote,                                                                                               ")
				.append("          place1,                                                                                                 ")
				.append("          place,                                                                                                  ")
				.append("          notes1,                                                                                                 ")
				.append("          barcode,                                                                                                ")
				.append("          notes,                                                                                                  ")
				.append("          oldpropertyno,                                                                                          ")
				.append("          oldserialno,                                                                                            ")
				.append("          editid,                                                                                                 ")
				.append("          editdate,                                                                                               ")
				.append("          edittime,                                                                                               ")
				.append("          originallimityear,                                                                                      ")
				.append("          otherlimityear,                                                                                         ")
				.append("          oldlotno,                                                                                               ")
				.append("          propertyname1                                                                                           ")
				.append("         )                                                                                                        ")
				.append("                SELECT a.enterorg,                                                                                ")
				.append("                       a.ownership,                                                                               ")
				.append(Common.sqlChar(addCaseno)).append(",")
				.append(Common.sqlChar(differencekind)).append(",")
				.append("                       a.propertyno,                                                                              ")
				.append("                (                                                                                                 ")
				.append("                    SELECT CASE                                                                                   ")
				.append("                               WHEN MAX(d.lotno) IS NULL                                                          ")
				.append("                               THEN RIGHT(REPLICATE('0', 7) + CAST('1' AS NVARCHAR), 7)                           ")
				.append("                               ELSE RIGHT(REPLICATE('0', 7) + CAST(MAX(d.lotno) + 1 AS NVARCHAR), 7)              ")
				.append("                           END                                                                                    ")
				.append("                    FROM UNTNE_NONEXPDETAIL d                                                                     ")
				.append("                    WHERE a.enterorg = d.enterorg                                                                 ")
				.append("                          AND a.ownership = d.ownership                                                           ")
				.append("                          AND d.differencekind = ").append(Common.sqlChar(differencekind))
				.append("                          AND a.propertyno = d.propertyno                                                         ")
				.append("                ) AS lotno,                                                                                       ")
				.append("                (                                                                                                 ")
				.append("                    SELECT CASE                                                                                   ")
				.append("                               WHEN MAX(d.serialno) IS NULL                                                       ")
				.append("                               THEN RIGHT(REPLICATE('0', 7) + CAST('1' AS NVARCHAR), 7)                           ")
				.append("                               ELSE RIGHT(REPLICATE('0', 7) + CAST(MAX(d.serialno) + 1 AS NVARCHAR), 7)           ")
				.append("                           END                                                                                    ")
				.append("                    FROM UNTNE_NONEXPDETAIL d                                                                     ")
				.append("                    WHERE a.enterorg = d.enterorg                                                                 ")
				.append("                          AND a.ownership = d.ownership                                                           ")
				.append("                          AND d.differencekind = ").append(Common.sqlChar(differencekind))
				.append("                          AND a.propertyno = d.propertyno                                                         ")
				.append("                ) AS serialno,                                                                                    ")
				.append("                       1,                                                                                         ")
				.append("                       'N',                                                                                       ")
				.append("                       1,                                                                           ")
				.append("                       b.oldbookvalue,                                                                            ")
				.append("                       1,                                                                           ")
				.append("                       b.oldbookvalue,                                                                            ")
				.append("                       a.licenseplate,                                                                            ")
				.append("                       a.purpose,                                                                                 ")
				.append("                       a.originalkeepunit,                                                                        ")
				.append("                       a.originalkeeper,                                                                          ")
				.append("                       a.originaluseunit,                                                                         ")
				.append("                       a.originaluser,                                                                            ")
				.append("                       a.originalusernote,                                                                        ")
				.append("                       a.originalplace1,                                                                          ")
				.append("                       a.originalplace,                                                                           ")
				.append("                       a.keepunit,                                                                                ")
				.append("                       a.keeper,                                                                                  ")
				.append("                       a.useunit,                                                                                 ")
				.append("                       a.userno,                                                                                  ")
				.append("                       a.usernote,                                                                                ")
				.append("                       a.place1,                                                                                  ")
				.append("                       a.place,                                                                                   ")
				.append("                       a.notes1,                                                                                  ")
				.append("                       a.barcode,                                                                                 ")
				.append("                       a.notes,                                                                                   ")
				.append("                       a.propertyno,                                                                              ")
				.append("                       a.serialno,                                                                                ")
				.append(Common.sqlChar(this.getUserID())).append(",")
				.append(Common.sqlChar(Datetime.getYYYYMMDD())).append(",")
				.append(Common.sqlChar(Datetime.getHHMMSS())).append(",")
				.append("                       a.originallimityear,                                                                       ")
				.append("                       a.otherlimityear,                                                                          ")
				.append("                       a.lotno,                                                                                   ")
				.append("                       a.propertyname1                                                                            ")
				.append("                FROM UNTNE_NONEXPDETAIL a                                                                         ")
				.append("                     JOIN UNTNE_REDUCEDETAIL b ON a.enterorg = b.enterorg                                         ")
				.append("                                                  AND a.ownership = b.ownership                                   ")
				.append("                                                  AND a.differencekind = b.differencekind                         ")
				.append("                                                  AND a.propertyno = b.propertyno                                 ")
				.append("                                                  AND a.serialno = b.serialno                                     ")
				.append("                WHERE b.caseno = ").append(Common.sqlChar(reduceCaseNo))
				.append("                      AND a.enterorg = @enterorg                                                                  ")
				.append("                      AND a.differencekind = @differentkind                                                       ")
				.append("                      AND a.ownership = @ownership                                                                ")
				.append("                      AND a.propertyno = @propertyno                                                              ")
				.append("                      AND a.serialno = @serialno;                                                                 ")
				.append("         SELECT @caseserialno = @caseserialno + 1;                                                                ")
				.append("         FETCH NEXT FROM rs INTO @enterorg, @differentkind, @ownership, @propertyno, @serialno;                   ")
				.append("     END;                                                                                                         ")
				.append(" CLOSE rs;                                                                                                        ")
				.append(" DEALLOCATE rs;                                                                                                   ");
			
			//批
			sql.append(" DECLARE rs CURSOR                                                                                                ")
				.append(" FOR SELECT a.enterorg,                                                                                           ")
				.append("            a.differencekind,                                                                                     ")
				.append("            a.ownership,                                                                                          ")
				.append("            a.propertyno,                                                                                         ")
				.append("            a.serialno                                                                                            ")
				.append("     FROM UNTNE_NONEXPDETAIL a                                                                                    ")
				.append("          JOIN UNTNE_REDUCEDETAIL b ON a.enterorg = b.enterorg                                                    ")
				.append("                                       AND a.differencekind = b.differencekind                                    ")
				.append("                                       AND a.ownership = b.ownership                                              ")
				.append("                                       AND a.propertyno = b.propertyno                                            ")
				.append("                                       AND a.serialno = b.serialno                                                ")
				.append("     WHERE a.enterorg = ").append(Common.sqlChar(this.getEnterOrg()))
				.append("           AND b.caseno = ").append(Common.sqlChar(reduceCaseNo))
				.append("     ORDER BY a.enterorg,                                                                                         ")
				.append("              a.ownership,                                                                                        ")
				.append("              a.differencekind,                                                                                   ")
				.append("              a.propertyno,                                                                                       ")
				.append("              a.serialno;                                                                                         ")
				.append(" OPEN rs;                                                                                                         ")
				.append(" FETCH NEXT FROM rs INTO @enterorg, @differentkind, @ownership, @propertyno, @serialno;                           ")
				.append(" WHILE @@Fetch_Status = 0                                                                                         ")
				.append("     BEGIN                                                                                                        ")
				.append("         INSERT INTO UNTNE_NONEXP                                                                                 ")
				.append("         (enterorg,                                                                                               ")
				.append("          ownership,                                                                                              ")
				.append("          caseno,                                                                                                 ")
				.append("          differencekind,                                                                                         ")
				.append("          caseserialno,                                                                                           ")
				.append("          propertyno,                                                                                             ")
				.append("          lotno,                                                                                                  ")
				.append("          serialnos,                                                                                              ")
				.append("          serialnoe,                                                                                              ")
				.append("          otherpropertyunit,                                                                                      ")
				.append("          othermaterial,                                                                                          ")
				.append("          otherlimityear,                                                                                         ")
				.append("          propertyname1,                                                                                          ")
				.append("          approvedate,                                                                                            ")
				.append("          approvedoc,                                                                                             ")
				.append("          buydate,                                                                                                ")
				.append("          datastate,                                                                                              ")
				.append("          verify,                                                                                                 ")
				.append("          propertykind,                                                                                           ")
				.append("          fundtype,                                                                                               ")
				.append("          valuable,                                                                                               ")
				.append("          originalamount,                                                                                         ")
				.append("          originalunit,                                                                                           ")
				.append("          originalbv,                                                                                             ")
				.append("          originalnote,                                                                                           ")
				.append("          accountingtitle,                                                                                        ")
				.append("          bookamount,                                                                                             ")
				.append("          bookvalue,                                                                                              ")
				.append("          fundssource,                                                                                            ")
				.append("          fundssource1,                                                                                           ")
				.append("          grantvalue,                                                                                             ")
				.append("          articlename,                                                                                            ")
				.append("          nameplate,                                                                                              ")
				.append("          specification,                                                                                          ")
				.append("          storeno,                                                                                                ")
				.append("          sourcekind,                                                                                             ")
				.append("          sourcedate,                                                                                             ")
				.append("          sourcedoc,                                                                                              ")
				.append("          oldpropertyno,                                                                                          ")
				.append("          oldserialnos,                                                                                           ")
				.append("          oldserialnoe,                                                                                           ")
				.append("          notes,                                                                                                  ")
				.append("          editid,                                                                                                 ")
				.append("          editdate,                                                                                               ")
				.append("          edittime,                                                                                               ")
				.append("          originallimityear,                                                                                      ")
				.append("          oldlotno,                                                                                               ")
				.append("          bookamount1                                                                                             ")
				.append("         )                                                                                                        ")
				.append("                SELECT a.enterorg,                                                                                ")
				.append("                       a.ownership,                                                                               ")
				.append(Common.sqlChar(addCaseno)).append(",")
				.append(Common.sqlChar(differencekind)).append(",")
				.append("                       @caseserialno AS caseserialno,                                                             ")
				.append("                       a.propertyno,                                                                              ")
				.append("                (                                                                                                 ")
				.append("                    SELECT CASE                                                                                   ")
				.append("                               WHEN MAX(d.lotno) IS NULL                                                          ")
				.append("                               THEN RIGHT(REPLICATE('0', 7) + CAST('1' AS NVARCHAR), 7)                           ")
				.append("                               ELSE RIGHT(REPLICATE('0', 7) + CAST(MAX(d.lotno) + 1 AS NVARCHAR), 7)              ")
				.append("                           END                                                                                    ")
				.append("                    FROM UNTNE_NONEXP d                                                                           ")
				.append("                    WHERE a.enterorg = d.enterorg                                                                 ")
				.append("                          AND a.ownership = d.ownership                                                           ")
				.append("                          AND d.differencekind = ").append(Common.sqlChar(differencekind))
				.append("                          AND a.propertyno = d.propertyno                                                         ")
				.append("                ) AS lotno,                                                                                       ")
				.append("                (                                                                                                 ")
				.append("                    SELECT CASE                                                                                   ")
				.append("                               WHEN MAX(d.serialnoe) IS NULL                                                      ")
				.append("                               THEN RIGHT(REPLICATE('0', 7) + CAST('1' AS NVARCHAR), 7)                           ")
				.append("                               ELSE RIGHT(REPLICATE('0', 7) + CAST(MAX(d.serialnoe) + 1 AS NVARCHAR), 7)          ")
				.append("                           END                                                                                    ")
				.append("                    FROM UNTNE_NONEXP d                                                                           ")
				.append("                    WHERE a.enterorg = d.enterorg                                                                 ")
				.append("                          AND a.ownership = d.ownership                                                           ")
				.append("                          AND d.differencekind = ").append(Common.sqlChar(differencekind))
				.append("                          AND a.propertyno = d.propertyno                                                         ")
				.append("                ) AS serialnos,                                                                                   ")
				.append("                (                                                                                                 ")
				.append("                    SELECT CASE                                                                                   ")
				.append("                               WHEN MAX(d.serialnoe) IS NULL                                                      ")
				.append("                               THEN RIGHT(REPLICATE('0', 7) + CAST('1' AS NVARCHAR), 7)                           ")
				.append("                               ELSE RIGHT(REPLICATE('0', 7) + CAST(MAX(d.serialnoe) + 1 AS NVARCHAR), 7)          ")
				.append("                           END                                                                                    ")
				.append("                    FROM UNTNE_NONEXP d                                                                           ")
				.append("                    WHERE a.enterorg = d.enterorg                                                                 ")
				.append("                          AND a.ownership = d.ownership                                                           ")
				.append("                          AND d.differencekind = ").append(Common.sqlChar(differencekind))
				.append("                          AND a.propertyno = d.propertyno                                                         ")
				.append("                ) AS serialnoe,                                                                                   ")
				.append("                       c.otherpropertyunit,                                                                       ")
				.append("                       c.othermaterial,                                                                           ")
				.append("                       c.otherlimityear,                                                                          ")
				.append("                       c.propertyname1,                                                                           ")
				.append("                       c.approvedate,                                                                             ")
				.append("                       c.approvedoc,                                                                              ")
				.append("                       c.buydate,                                                                                 ")
				.append("                       1,                                                                                         ")
				.append("                       'N',                                                                                       ")
				.append("                       c.propertykind,                                                                            ")
				.append("                       c.fundtype,                                                                                ")
				.append("                       c.valuable,                                                                                ")
				.append("                       1,                                                                                         ")
				.append("                       b.oldbookunit,                                                                             ")
				.append("                       b.oldbookvalue,                                                                            ")
				.append("                       c.originalnote,                                                                            ")
				.append("                       c.accountingtitle,                                                                         ")
				.append("                       1,                                                                                         ")
				.append("                       b.oldbookvalue,                                                                            ")
				.append("                       c.fundssource,                                                                             ")
				.append("                       c.fundssource1,                                                                            ")
				.append("                       c.grantvalue,                                                                              ")
				.append("                       c.articlename,                                                                             ")
				.append("                       c.nameplate,                                                                               ")
				.append("                       c.specification,                                                                           ")
				.append("                       c.storeno,                                                                                 ")
				.append(Common.sqlChar(sourceKind)).append(", ")
				.append("                       c.sourcedate,                                                                              ")
				.append("                       c.sourcedoc,                                                                               ")
				.append("                       a.propertyno,                                                                              ")
				.append("                       a.serialno,                                                                                ")
				.append("                       a.serialno,                                                                                ")
				.append("                       c.notes,                                                                                   ")
				.append(Common.sqlChar(this.getUserID())).append(",")
				.append(Common.sqlChar(Datetime.getYYYYMMDD())).append(",")
				.append(Common.sqlChar(Datetime.getHHMMSS())).append(",")
				.append("                       c.originallimityear,                                                                       ")
				.append("                       c.lotno,                                                                                   ")
				.append("                       1                                                                                          ")
				.append("                FROM UNTNE_NONEXPDETAIL a                                                                         ")
				.append("                     JOIN UNTNE_REDUCEDETAIL b ON a.enterorg = b.enterorg                                         ")
				.append("                                                  AND a.ownership = b.ownership                                   ")
				.append("                                                  AND a.differencekind = b.differencekind                         ")
				.append("                                                  AND a.propertyno = b.propertyno                                 ")
				.append("                                                  AND a.serialno = b.serialno                                     ")
				.append("                     JOIN UNTNE_NONEXP c ON a.enterorg = c.enterorg                                               ")
				.append("                                            AND a.ownership = c.ownership                                         ")
				.append("                                            AND a.differencekind = c.differencekind                               ")
				.append("                                            AND a.propertyno = c.propertyno                                       ")
				.append("                                            AND a.lotno = c.lotno                                                 ")
				.append("                WHERE b.caseno = ").append(Common.sqlChar(reduceCaseNo))
				.append("                      AND a.enterorg = @enterorg                                                                  ")
				.append("                      AND a.differencekind = @differentkind                                                       ")
				.append("                      AND a.ownership = @ownership                                                                ")
				.append("                      AND a.propertyno = @propertyno                                                              ")
				.append("                      AND a.serialno = @serialno;                                                                 ")
				.append("         SELECT @caseserialno = @caseserialno + 1;                                                                ")
				.append("         FETCH NEXT FROM rs INTO @enterorg, @differentkind, @ownership, @propertyno, @serialno;                   ")
				.append("     END;                                                                                                         ")
				.append(" CLOSE rs;                                                                                                        ")
				.append(" DEALLOCATE rs;                                                                                                   ");
			
			if (sql.length() != 0) {
				db.exeSQL_NoChange(sql.toString());				
			}
		} catch(Exception e) {
			log._execLogError(e.getMessage());
		} finally {
			db.closeAll();
		}	
	}
	
	//問題單2299 公務轉基金 新增附屬資料
	private void transferAttachment(String addCaseno) throws Exception {
		Database db = null;
		StringBuilder sql = new StringBuilder();
		StringBuilder sql2 = new StringBuilder();
		String oldDifferencekind = "110";
		String newDifferencekind = "120";
		
		try {
			db = new Database();
			
			sql.append(" select enterorg, caseno, ownership, differencekind, propertyno, serialno, oldserialno, lotno, oldlotno from UNTMP_MOVABLEDETAIL ")
			   .append(" where caseno = ").append(Common.sqlChar(addCaseno)).append(" and enterorg = ").append(Common.sqlChar(this.getEnterOrg()));
			
			ResultSet rs = db.querySQL_NoChange(sql.toString());
			
			while(rs.next()) {
				//物品批號附屬設備檔
				sql2.append(" INSERT INTO UNTNE_ATTACHMENT1                             ")
					.append("        SELECT enterorg,                                   ")
					.append("               ownership,                                  ")
					.append(Common.sqlChar(newDifferencekind)).append(", ")
					.append("        		propertyno,            		 		     	")
					.append(Common.sqlChar(rs.getString("lotno"))).append(", ")
					.append("               serialno1,                                  ")
					.append("               equipmentname,                              ")
					.append("               buydate,                                    ")
					.append("               equipmentunit,                              ")
					.append("               equipmentamount,                            ")
					.append("               unitprice,                                  ")
					.append("               totalvalue,                                 ")
					.append("               sourcekind,                                 ")
					.append("               nameplate,                                  ")
					.append("               specification,                              ")
					.append("               notes,                                      ")
					.append(Common.sqlChar(this.getUserID())).append(",")
					.append(Common.sqlChar(Datetime.getYYYYMMDD())).append(",")
					.append(Common.sqlChar(Datetime.getHHMMSS()))
					.append("        FROM UNTNE_ATTACHMENT1                             ")
					.append(" 		WHERE enterorg = ").append(Common.sqlChar(rs.getString("enterorg")))
					.append("       	AND ownership = ").append(Common.sqlChar(rs.getString("ownership")))
					.append("       	AND differencekind = ").append(Common.sqlChar(oldDifferencekind))
					.append("       	AND propertyno = ").append(Common.sqlChar(rs.getString("propertyno")))
					.append("       	AND lotno = ").append(Common.sqlChar(rs.getString("oldlotno"))).append(";");
				
				//物品批號明細附屬設備檔
				sql2.append(" INSERT INTO UNTNE_ATTACHMENT2                                     ")
					.append("        SELECT enterorg,                                           ")
					.append("               ownership,                                          ")
					.append(Common.sqlChar(newDifferencekind)).append(", ")
					.append("        		propertyno,            		 		      			")
					.append(Common.sqlChar(rs.getString("lotno"))).append(", ")
					.append(Common.sqlChar(rs.getString("serialno"))).append(", ")
					.append("               serialno1,                                          ")
					.append("               equipmentname,                                      ")
					.append("               buydate,                                            ")
					.append("               equipmentunit,                                      ")
					.append("               equipmentamount,                                    ")
					.append("               unitprice,                                          ")
					.append("               totalvalue,                                         ")
					.append("               datastate,                                          ")
					.append("               sourcekind,                                         ")
					.append("               nameplate,                                          ")
					.append("               specification,                                      ")
					.append("               notes,                                              ")
					.append(Common.sqlChar(this.getUserID())).append(",")
					.append(Common.sqlChar(Datetime.getYYYYMMDD())).append(",")
					.append(Common.sqlChar(Datetime.getHHMMSS()))
					.append("        FROM UNTNE_ATTACHMENT2                                     ")
					.append(" 		WHERE enterorg = ").append(Common.sqlChar(rs.getString("enterorg")))
					.append("       	AND ownership = ").append(Common.sqlChar(rs.getString("ownership")))
					.append("       	AND differencekind = ").append(Common.sqlChar(oldDifferencekind))
					.append("       	AND propertyno = ").append(Common.sqlChar(rs.getString("propertyno")))
					.append("       	AND serialno = ").append(Common.sqlChar(rs.getString("oldserialno"))).append(";");
			}
			
			if (sql2.length() != 0) {
				db.exeSQL_NoChange(sql2.toString());
			}
		} catch(Exception e) {
			log._execLogError(e.getMessage());
		} finally {
			db.closeAll();
		}		
	}
}
