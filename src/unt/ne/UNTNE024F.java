/*
*<br>程式目的：非消耗品增減值作業－增減值單資料
*<br>程式代號：UNTNE024F
*<br>程式日期：0941121
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

public class UNTNE024F extends UNTNE024Q{

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
String adjustDate;
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
public String getSummonsDate() {
	return checkGet(summonsDate);
}
public void setSummonsDate(String summonsDate) {
	this.summonsDate = checkSet(summonsDate);
}
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
public String getAdjustDate(){ return checkGet(adjustDate); }
public void setAdjustDate(String s){ adjustDate=checkSet(s); }
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
String checkDifferenceKind;
String checkOldDifferenceKind;
public String getProofYear(){return checkGet(proofYear);}
public void setProofYear(String s) {proofYear = checkSet(s);}
public String getCheckDifferenceKind() {return checkGet(checkDifferenceKind);}
public void setCheckDifferenceKind(String s) {checkDifferenceKind = checkSet(s);}
public String getCheckOldDifferenceKind() {return checkGet(checkOldDifferenceKind);}
public void setCheckOldDifferenceKind(String s) {checkOldDifferenceKind = checkSet(s);}

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
private String adjustDateTemp;
public String getAdjustDateTemp() {return checkGet(adjustDateTemp);}
public void setAdjustDateTemp(String adjustDateTemp) {this.adjustDateTemp = checkSet(adjustDateTemp);}
private String bookvalueFlag;
public String getBookvalueFlag() { return checkGet(bookvalueFlag); }
public void setBookvalueFlag(String bookvalueFlag) { this.bookvalueFlag = checkSet(bookvalueFlag); }


int sumBookValue;
Hashtable h = new Hashtable();
String changeNo="";

/**
 * 判斷是否為入帳用
 * @return
 */
public String queryOldVerify() {
	Database db = null;
	try {
		db = new Database();
		return db.getLookupField("select verify from UNTNE_ADJUSTPROOF where enterorg = " + Common.sqlChar(this.getEnterOrg()) + " and caseno = " + Common.sqlChar(this.getCaseNo()));
	} finally {
		db.closeAll();
	}
}

public boolean checkUpdateError() throws Exception{
	UNTCH_Tools ut = new UNTCH_Tools();
	Database db = new Database();
	ResultSet rs;
	boolean result = true;
	
	if(checkClosingYMfromUNTAC_CLOSINGNE(getAdjustDate(),getEnterOrg(),getDifferenceKind())){
		setErrorMsg("入帳日期需為最大月結年月＋１");
		result = false;
	}
	
	String sql = "with x as ("
			+ " select enterorg,caseno,ownership,differencekind,propertyno,serialno from UNTNE_ADJUSTDETAIL"
			+ " where enterorg = " + Common.sqlChar(getEnterOrg())
			+ " and caseno = " + Common.sqlChar(getCaseNo())
			+ " )"
			+ " select COUNT(1) as count from UNTNE_ADJUSTDETAIL d,x"
			+ " where d.enterorg = x.enterorg"
			+ " and d.ownership = x.ownership"
			+ " and d.differencekind = x.differencekind"
			+ " and d.propertyno = x.propertyno"
			+ " and d.serialno = x.serialno"
			+ " and d.caseno != x.caseno"
			+ " and d.verify = 'Y'"
			+ " and d.adjustdate >= " + ut._transToCE_Year(this.getAdjustDate());
	
	rs = db.querySQL_NoChange(sql);
	rs.next();
	
	if(!"0".equals(rs.getString("count"))) {
		setErrorMsg("入帳失敗, 檢查到已有入帳的物品增減值資料其入帳日期大於等於此單據入帳日期");
		result = false;
	}
	
	return result;
}

public boolean checReVerifyError(){
	boolean result = true;
	if(checkNECanNotReVerify(getAdjustDateTemp(),getEnterOrg(),getDifferenceKind())){
		setErrorMsg("當月已月結不可取消入帳");
		result = false;
	}
	return result;
}

//傳回執行insert前之檢查sql
protected String[][] getInsertCheckSQL(){
	String[][] checkSQLArray = new String[1][4];
 	checkSQLArray[0][0]=" select count(*) as checkResult from UNTNE_AdjustProof where 1=1 " + 
		" and enterOrg = " + Common.sqlChar(enterOrg) + 
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
	String sql="select cast(max(substring(caseNo,4,7))+1 AS varchar) as caseNo from UNTNE_AdjustProof " +
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
	//取得增減值單編號－號
	setProofNo(MaxClosingYM.getMaxProofNo("UNTNE_AdjustProof",enterOrg,ownership,this.getProofYear()));
	//===================
	String[] execSQLArray = new String[1];
	execSQLArray[0]=" insert into UNTNE_AdjustProof (" +
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
			" adjustDate,"+
			" approveOrg,"+
			" approveDate,"+
			" approveDoc,"+
			" verify,"+
			" notes,"+
			" editID,"+
			" editDate,"+
			" editTime,"+
			" differencekind,"+
			" proofyear,"+
			" summonsdate"+
		" ) Values (" +
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
			Common.sqlChar(ul._transToCE_Year(this.getAdjustDate())) + "," +
			Common.sqlChar(this.getApproveOrg()) + "," +
			Common.sqlChar(ul._transToCE_Year(this.getApproveDate())) + "," +
			Common.sqlChar(this.getApproveDoc()) + "," + 
			Common.sqlChar(this.getVerify()) + "," +
			Common.sqlChar(this.getNotes()) + "," +
			Common.sqlChar(getEditID()) + "," +
			Common.sqlChar(ul._transToCE_Year(getEditDate())) + "," +
			Common.sqlChar(getEditTime()) + "," +
	        Common.sqlChar(getDifferenceKind()) + "," +
	        Common.sqlChar(getProofYear()) + "," + 
	        Common.sqlChar(ul._transToCE_Year(getSummonsDate())) + ")";
	
	return execSQLArray;
}

//檢查update時，proofyear是否有改變
private boolean checkProofyearChanged(String enterorg, String caseno, String newproofyear){
	boolean isChanged = false;
	String oldproofyear ="";
	ResultSet rs;
	Database db = new Database();
	try{
		String sql = "select proofyear from UNTNE_ADJUSTPROOF where enterorg=" + Common.sqlChar(enterorg) +
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

//傳回 sql
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
	//更改 UNTNE_AdjustProof TABLE
	    strSQL   =" update UNTNE_AdjustProof set " +
				" caseName = " + Common.sqlChar(this.getCaseName()) + "," +
				" writeDate = " + Common.sqlChar(ul._transToCE_Year(this.getWriteDate())) + "," +
				" writeUnit = " + Common.sqlChar(this.getWriteUnit()) + "," +
				" proofDoc = " + Common.sqlChar(this.getProofDoc()) + "," +
				" proofNo = " + Common.sqlChar(this.getProofNo()) + "," +
				" manageNo = " + Common.sqlChar(this.getManageNo()) + "," +
				" summonsNo = " + Common.sqlChar(this.getSummonsNo()) + "," +
				" summonsDate = " + Common.sqlChar(ul._transToCE_Year(this.getSummonsDate())) + "," +
				" adjustDate = " + Common.sqlChar(ul._transToCE_Year(this.getAdjustDate())) + "," +
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
		
		//問題單1290補充: update時更改編號-年，重新取得編號-號
		if(this.checkProofyearChanged(getEnterOrg(), getCaseNo(), getProofYear())){
			String newproofno = MaxClosingYM.getMaxProofNo("UNTNE_ADJUSTPROOF",enterOrg,ownership,this.getProofYear());
			strSQL += " update UNTNE_ADJUSTPROOF set proofno=" + Common.sqlChar(newproofno) +
								" where 1=1 " +
								" and enterorg = " + Common.sqlChar(enterOrg) +
								" and caseno = " + Common.sqlChar(caseNo) +
								":;:";
		}
		if(verify.equals("Y")){
			strSQL += checkVerify();
			strSQL += updNonexp();
			strSQL += updNonexpDetail();
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
		//刪除 UNTNE_AdjustProof TABLE
		execSQLArray[0]=" delete UNTNE_AdjustProof where 1=1 " +
				" and enterOrg = " + Common.sqlChar(enterOrg) +
				" and caseNo = " + Common.sqlChar(caseNo) +
				"";
				
		//刪除 UNTNE_AdjustDetail TABLE
		execSQLArray[1]=" delete UNTNE_AdjustDetail where 1=1 " +
				" and enterOrg = " + Common.sqlChar(enterOrg) +
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

public UNTNE024F  queryOne() throws Exception{
	UNTCH_Tools ul = new UNTCH_Tools();
	Database db = new Database();
	UNTNE024F obj = this;
	try {
		String sql=" select a.enterOrg, b.organSName, a.ownership, a.caseNo, a.caseName, a.writeDate, a.writeUnit, a.proofDoc, a.proofNo, a.manageNo, a.summonsNo, a.adjustDate, a.approveOrg, a.approveDate, a.approveDoc, a.verify, a.notes, a.editID, a.editDate, a.editTime, a.editID, a.editDate, a.editTime,a.proofYear, a.differencekind ,a.summonsDate"+
			" from UNTNE_AdjustProof a, SYSCA_Organ b where 1=1 " +
			" and a.enterOrg = " + Common.sqlChar(enterOrg) +
			" and a.caseNo = " + Common.sqlChar(caseNo) +
			" and b.organID = a.enterOrg" +
			" order by a.enterOrg, a.ownership, a.caseNo ";
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
			obj.setAdjustDate(ul._transToROC_Year(rs.getString("adjustDate")));
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
			
			// 問題單2272 查詢有單時，須看單據裡有沒有減值後原值為0的資料
			StringBuilder sql2 = new StringBuilder();
			sql2.append(" SELECT										 ").	
			append(" (                                                   ").
			append("     SELECT COUNT(*)                                 ").
			append("     FROM UNTNE_ADJUSTDETAIL b                       ").
			append("     WHERE a.enterorg = b.enterorg                   ").
			append("           AND a.ownership = b.ownership             ").
			append("           AND a.differencekind = b.differencekind   ").
			append("           AND a.caseno = b.caseno                   ").
			append("           AND b.newbookvalue = 0                    ").
			append(" ) AS count                                          ").
			append(" from UNTNE_ADJUSTPROOF a                               ").
			append(" WHERE a.caseno = ").append(Common.sqlChar(caseNo)).
			append("       AND a.enterorg = ").append(Common.sqlChar(enterOrg)).
			append("       AND a.ownership = ").append(Common.sqlChar(ownership)).
			append("       AND a.differencekind = ").append(Common.sqlChar(differenceKind));
			
			rs = db.querySQL(sql2.toString());
			if (rs.next()){ 
				int count = rs.getInt("count");
				
				if (count > 0) {
					obj.setBookvalueFlag("Y");
				} else {
					obj.setBookvalueFlag("N");
				}
			}
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
				" a.caseNo, a.caseName, a.writeDate, a.proofDoc, a.proofNo, a.adjustDate,cast(a.proofyear as int) as proofyear, (case a.verify when 'Y' then '是' else '否' end) verify " +
//				" b.propertyNo "+
				" from UNTNE_AdjustProof a"+
                " left join UNTNE_AdjustDetail b on a.enterOrg=b.enterOrg and a.ownership=b.ownership and a.caseNo=b.caseNo," +
                " SYSCA_Organ c where 1=1 " +
				" and a.enterOrg = c.organID ";  
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
						//sql += " and a.enterOrg like '" + getOrganID().substring(0,5) + "%' ";	
						sql += " and( a.enterOrg = " + Common.sqlChar(getOrganID()) ;
						sql += " or organID in (select organID from SYSCA_Organ where organSuperior='"+ Common.sqlChar(getOrganID())+"'))";
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
			if (!"".equals(getQ_adjustDateS()))
				sql+=" and a.adjustDate >= " + Common.sqlChar(getQ_adjustDateS()) ;
			if (!"".equals(getQ_adjustDateE()))
				sql+=" and a.adjustDate <= " + Common.sqlChar(getQ_adjustDateE()) ;
			if (!"".equals(getQ_caseName()))
				sql+=" and a.caseName like " + Common.sqlChar(getQ_caseName()+"%") ;
			if (!"".equals(getQ_proofDoc()))
				sql+=" and a.proofDoc like " + Common.sqlChar(getQ_proofDoc()+"%") ;
			if (!"".equals(getQ_proofNoS()))
				sql+=" and a.proofNo >= " + Common.sqlChar(getQ_proofNoS()) ;
			if (!"".equals(getQ_proofNoE()))
				sql+=" and a.proofNo <= " + Common.sqlChar(getQ_proofNoE()) ;
			if (!"".equals(getQ_writeDateS()))
				sql+=" and a.writeDate >= " + Common.sqlChar(new Datetime().changeTaiwanYYMMDD(getQ_writeDateS(),"2")) ;
			if (!"".equals(getQ_writeDateE()))
				sql+=" and a.writeDate <= " + Common.sqlChar(new Datetime().changeTaiwanYYMMDD(getQ_writeDateE(),"2")) ;    
			if (!"".equals(getQ_approveOrg()))
				sql+=" and a.approveOrg = " + Common.sqlChar(getQ_approveOrg()) ;
			if (!"".equals(getQ_propertyNoS()))
				sql+=" and b.propertyNo >= " + Common.sqlChar(getQ_propertyNoS());		
			if (!"".equals(getQ_propertyNoE()))
				sql+=" and b.propertyNo <= " + Common.sqlChar(getQ_propertyNoE());						
			if (!"".equals(getQ_serialNoS()))
				sql+=" and b.serialNo >= " + Common.sqlChar(getQ_serialNoS());		
			if (!"".equals(getQ_serialNoE()))
				sql+=" and b.serialNo <= " + Common.sqlChar(getQ_serialNoE());			
			if (!"".equals(getQ_propertyKind()))
				sql+=" and b.propertyKind = " + Common.sqlChar(getQ_propertyKind());
			if (!"".equals(getQ_fundType()))
				sql+=" and b.fundType = " + Common.sqlChar(getQ_fundType()) ;
			if (!"".equals(getQ_valuable()))
				sql+=" and b.valuable = " + Common.sqlChar(getQ_valuable()) ;
			if (!"".equals(getQ_differenceKind()))
				sql+=" and a.differencekind = " + Common.sqlChar(getQ_differenceKind()) ;
			if (!"".equals(getQ_proofYear()))
				sql+=" and a.proofyear = " + Common.sqlChar(getQ_proofYear()) ;
			if (!"".equals(getQ_userNote()))
				sql+=" and a.userNote like " + Common.sqlChar("%"+getQ_userNote()+"%") ;
			if (!"".equals(getQ_keepUnit()))
				sql+=" and a.keepUnit = " + Common.sqlChar(getQ_keepUnit()) ;	    
			if (!"".equals(getQ_keeper()))
				sql+=" and a.keeper = " + Common.sqlChar(getQ_keeper()) ;	    
			if (!"".equals(getQ_useUnit()))
				sql+=" and a.useUnit = " + Common.sqlChar(getQ_useUnit()) ;	    
			if (!"".equals(getQ_userNo()))
				sql+=" and a.userNo = " + Common.sqlChar(getQ_userNo()) ;	   
			if(!"".equals(getQ_place1()))
				sql+=" and a.place1 like " + Common.sqlChar("%" + getQ_place1() + "%");
		}
		if ("1".equals(this.getRoleid())) {
			// 1185 增加能查到單的異動人員是自己的單
			sql += " and ( a.editid = " + Common.sqlChar(this.getUserID()) +
						 " or b.keeper = " + Common.sqlChar(this.getUserID()) +
						 " )";			
		} else if ("2".equals(this.getRoleid())) {
			sql += " and ( a.editid = " + Common.sqlChar(this.getUserID()) +
						 " or b.keepunit = " + Common.sqlChar(this.getUnitID()) +
						 " or b.keeper = " + Common.sqlChar(this.getUserID()) +
						 " )";
		}
		
		//排序方式不能變更,會影響全部入帳(因為UNTNE_Nonexp要計算bookValue所以需要排序將同一個enterOrg,ownership,propertyNo放在一起)
		sql+=" order by proofyear desc,a.proofno desc ";
		ResultSet rs = db.querySQL(sql.toString(),true);
		processCurrentPageAttribute(rs);
		//把查到的資料放入list裡..
		//和.jsp裡的 List區 內容要搭配起來
		if (getTotalRecord() > 0) {
			int count = getRecordStart();
			int end = getRecordEnd();
			do {
				if (count > end)
					break;
			String rowArray[] = new String[10];
			rowArray[0] = rs.getString("enterOrg"); 
			rowArray[1] = rs.getString("ownership"); 
			rowArray[2] = rs.getString("ownershipName");
			rowArray[3] = differencekindMap.get(rs.getString("differencekind"));
			rowArray[4] = rs.getString("caseNo"); 
			rowArray[5] = Common.get(rs.getString("proofYear")) + "年" +
							Common.get(rs.getString("proofDoc")) + "字第" + 
							Common.get(rs.getString("proofNo")) + "號";  
			rowArray[6] = ul._transToROC_Year(rs.getString("writeDate")); 
			rowArray[7] = ul._transToROC_Year(rs.getString("adjustDate")); 
			rowArray[8] = rs.getString("verify");
			rowArray[9] = rs.getString("differenceKind"); 
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
//逐筆=>approveAll() -> update UNTNE_AdjustProof
//               ↘----> checkVerify() --> update UNTNE_AdjustDetail 檔案
//								↘----> 查最大年月及符合條件與否? 
//				  ↘----> updNonexp() --> update UNTNE_Nonexp 檔案
//				  ↘----> updNonexpDetail() --> update UNTNE_NonexpDetail 檔案

//全部入帳
public void approveAll()throws Exception{
	UNTCH_Tools ul = new UNTCH_Tools();
	Database db = new Database();
	try {    
	    int i = 0,counter = 0;
	    String rowArray[]=new String[10];
	    java.util.Iterator it= queryAll().iterator();
	    String[] execSQLArray;
		String strSQL="";
		while(it.hasNext()){
		    counter++;
			rowArray= (String[])it.next();
			if(rowArray[8].equals("否")){
			    i++;
				setVerify("Y");
				enterOrg = rowArray[0];
				ownership= rowArray[1];
				caseNo   = rowArray[4];
				setAdjustDate(rowArray[7]==null || "".equals(rowArray[7]) ? Datetime.getYYYYMMDD():rowArray[7]);
				setEditID(getUserID());
				setEditDate(Datetime.getYYYYMMDD());
				setEditTime(Datetime.getHHMMSS());	
				if (enterOrg.equals(getOrganID())) {
					strSQL +=  " update UNTNE_AdjustProof set "+
			                " verify = 'Y'," +
			                " adjustDate = " + Common.sqlChar(getAdjustDate()) + "," +
			    			" editID = " + Common.sqlChar(getEditID()) + "," +
			    			" editDate = " + Common.sqlChar(ul._transToCE_Year(getEditDate())) + "," +
			    			" editTime = " + Common.sqlChar(getEditTime()) + 	                
			        		" where 1=1 " + 
			    			" and enterOrg = " + Common.sqlChar(enterOrg) +
			    			" and caseNo = " + Common.sqlChar(caseNo) +
			    			":;:";				
					strSQL += checkVerify();
					strSQL += updNonexp();
					strSQL += updNonexpDetail();
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
		if((Double.parseDouble(getMaxClosingYM(enterOrg))) < (Double.parseDouble(ul._transToCE_Year(getAdjustDate()).substring(0,6)))){
		//更改 UNTNE_AdjustDetail 檔案資料
		sql =   " update UNTNE_AdjustDetail set"+
		        " verify = 'Y'," +
		        " adjustDate = " + Common.sqlChar(ul._transToCE_Year(getAdjustDate())) + "," +
				" editID = " + Common.sqlChar(getEditID()) + "," +
				" editDate = " + Common.sqlChar(ul._transToCE_Year(getEditDate())) + "," +
				" editTime = " + Common.sqlChar(getEditTime()) + 	                
				" where 1=1 " + 
				" and enterOrg = " + Common.sqlChar(enterOrg) +
				" and caseNo = " + Common.sqlChar(caseNo) +
				":;:"; 
		}
	else{
	    setVerifyError("Y");   
		setStateUpdateError();
        setErrorMsg("入帳設定有錯，增減值日期之年月必須大於月結年月！");
		}
	return sql;
}

//異動 UNTNE_Nonexp 檔案
//設迴圈 取 detail 的 propertyNo ,   where verify = 'N' 資料
protected String updNonexp(){
	Database db = new Database();
	String sqlNonexp = "";
	UNTNE024F obj = this;
	int count=0;
	//1.更新 Nonexp.bookvalue = bookvalue + adjustdetail.addBookValue-adjustdetail.reduceBookValue
	//2.更新 Nonexpdetail.bookvalue = adjustdetail.newbookvalue
	try {
		String sql =" select b.enterOrg, b.ownership, b.differencekind, a.lotNo, b.propertyNo, b.serialNo, "+
					" a.bookValue, b.addBookValue, b.reduceBookValue "+
					" from UNTNE_Nonexp a, UNTNE_AdjustDetail b "+
					" where 1=1 " +
					" and a.enterOrg=b.enterOrg and a.ownership=b.ownership and a.differencekind = b.differencekind and a.propertyNo=b.propertyNo and a.lotNo=b.lotNo "+
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
			changeNo = rs.getString("enterOrg")+rs.getString("ownership")+rs.getString("propertyNo")+rs.getString("lotNo");
			Integer I = (Integer) h.get(changeNo+"_sumBookValue");
			if (I!=null) {
				if(!(obj.getCheckEnterOrg()).equals(obj.getCheckOldEnterOrg()) || !(obj.getCheckOwnership()).equals(obj.getCheckOldOwnership()) || !(obj.getCheckPropertyNo()).equals(obj.getCheckOldPropertyNo()) || !(obj.getCheckLotNo()).equals(obj.getCheckOldLotNo())|| !(obj.getCheckDifferenceKind()).equals(obj.getCheckOldDifferenceKind())){
					h.put(changeNo+"_sumBookValue", new Integer(((Integer) h.get(changeNo+"_sumBookValue")).intValue()));
				}else{
					h.put(changeNo+"_sumBookValue", new Integer(sumBookValue));
				}
			} else {
				h.put(changeNo+"_sumBookValue", new Integer(rs.getInt("BookValue")));
			}    
			//=========				    
		    if(!(obj.getCheckEnterOrg()).equals(obj.getCheckOldEnterOrg()) || !(obj.getCheckOwnership()).equals(obj.getCheckOldOwnership()) || !(obj.getCheckPropertyNo()).equals(obj.getCheckOldPropertyNo()) || !(obj.getCheckLotNo()).equals(obj.getCheckOldLotNo()) || !(obj.getCheckDifferenceKind()).equals(obj.getCheckOldDifferenceKind())){
		    	sumBookValue=((Integer) h.get(changeNo+"_sumBookValue")).intValue();
		    }   
		    if(!(rs.getString("addBookValue").equals("0"))){
		        sumBookValue += rs.getInt("addBookValue");
		    }
		    if(!(rs.getString("reduceBookValue").equals("0"))){
		        sumBookValue -= rs.getInt("reduceBookValue");
		    }
	    	obj.setCheckOldEnterOrg(obj.getCheckEnterOrg());
	    	obj.setCheckOldOwnership(obj.getCheckOwnership());
	    	obj.setCheckOldLotNo(obj.getCheckLotNo());
	    	obj.setCheckOldPropertyNo(obj.getCheckPropertyNo());
	    	obj.setCheckOldDifferenceKind(obj.getCheckDifferenceKind());
	        sqlNonexp += " update UNTNE_Nonexp set " +
			    		  " bookValue =  "  + Common.sqlInt(sumBookValue+"") +
						  " where 1=1 " + 
						  " and enterOrg = " + Common.sqlChar(rs.getString("enterOrg")) +
						  " and ownership = " + Common.sqlChar(rs.getString("ownership")) +
						  " and propertyNo = " + Common.sqlChar(rs.getString("propertyNo")) +
						  " and lotNo = " + Common.sqlChar(rs.getString("lotNo")) +
						  " and differenceKind = " + Common.sqlChar(rs.getString("differenceKind")) +
						  ":;:" ;
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


//異動 UNTNE_NonexpDetail 檔案
//設迴圈 取 detail 的 propertyNo , serialNo ,  sum(newBookValue)  where verify = 'N' 資料
protected String updNonexpDetail(){
	Database db = new Database();
	String sqlNonexpDetail = "";
	String checkDetail="";
	
	//2.更新 Nonexpdetail.bookvalue = adjustdetail.newbookvalue
	try {
		String sql =" select propertyNo , serialNo , "+
					" newBookValue ,differencekind"+
					" from UNTNE_AdjustDetail "+
					" where 1=1 "+
					" and enterOrg = " + Common.sqlChar(enterOrg) +
					" and caseNo = " + Common.sqlChar(caseNo) +
					" order by propertyNo , serialNo "+
					"" ;
		ResultSet rs = db.querySQL(sql);
		while (rs.next()){
		    checkDetail="Y";
		    sqlNonexpDetail +=   " update UNTNE_NonexpDetail set " +
					    		  " bookValue =  "  + Common.sqlChar(rs.getString("newBookValue"))  +
								  " where 1=1 " + 
								  " and enterOrg = " + Common.sqlChar(enterOrg) +
								  " and ownership = " + Common.sqlChar(ownership) +
								  " and propertyNo = " + Common.sqlChar(rs.getString("propertyNo")) +
								  " and serialNo = " + Common.sqlChar(rs.getString("serialNo")) +
								  " and differenceKind = " + Common.sqlChar(rs.getString("differenceKind")) +
								  ":;:" ;
		}
        if(!checkDetail.equals("Y")){
            setVerifyError("Y");
            setErrorMsg("該筆增減值單之明細資料標籤要有資料才能入帳！");
        }
	} catch (Exception e) {
		e.printStackTrace();
	} finally {
		db.closeAll();
	}
	return sqlNonexpDetail;
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
		String adjustOriginal = "",reduceMax = "",reduceMaxSql = "";
		String adjustMax = "",adjustMaxSql = "";
		int count = 0;
		String plus = "";
		int addBookValue= 0;
		int reduceBookValue = 0;
		try {    
			String[] execSQLArray;
			String strSQL = "";
			String sql ="select a.caseNo, b.enterOrg, b.ownership, b.propertyNo, b.serialNo, b.lotNo,b.differenceKind, " +
						" a.editDate, a.editTime, a.adjustDate, b.oldBookValue, " +
						" b.addBookValue,b.reduceBookValue " +
						" from untne_adjustProof a, untne_adjustDetail b " +
						" where 1=1 " +
						" and a.enterOrg = b.enterOrg and a.ownership = b.ownership and a.caseNo = b.caseNo" +
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
				//該增減值單之明細資料,存在未入帳的「非消耗品減損單明細檔UNTNE_ReduceDetail」資料，則提示使用者
				reduceSql = "select count(*) count from UNTNE_REDUCEDETAIL " +
							" where enterorg="+Common.sqlChar(enterOrgQuery)+" and ownership="+Common.sqlChar(ownershipQuery)+" and propertyno="+Common.sqlChar(propertyNoQuery)+" and serialno="+Common.sqlChar(serialNoQuery)+" and differencekind="+Common.sqlChar(differenceKindQuery)+" and verify='N'";
				reduceCount = MaxClosingYM.getCountValues(reduceSql);
				
				//該增減值單之明細資料,存在未入帳的「非消耗品增減值單明細檔UNTNE_AdjustDetail」資料，則提示使用者
				adjustSql = "select count(*) count from UNTNE_ADJUSTDETAIL " +
							" where enterorg="+Common.sqlChar(enterOrgQuery)+" and ownership="+Common.sqlChar(ownershipQuery)+" and propertyno="+Common.sqlChar(propertyNoQuery)+" and serialno="+Common.sqlChar(serialNoQuery)+" and differencekind="+Common.sqlChar(differenceKindQuery)+" and verify='N'";
				
				adjustCount = MaxClosingYM.getCountValues(adjustSql);
				
				//------------------------------------------------------------------------------------------------
				adjustOriginal = rs.getString("adjustDate");
				//該增減值單之增減值日期≦增減值單明細資料之其他增減值單之最大的增減值日期則提示使用者
				adjustMaxSql = "select count(*) count from UNTNE_ADJUSTDETAIL " +
								" where enterorg="+Common.sqlChar(enterOrgQuery)+" and ownership="+Common.sqlChar(ownershipQuery)+" and propertyno="+Common.sqlChar(propertyNoQuery)+" and serialno="+Common.sqlChar(serialNoQuery)+" and differencekind="+Common.sqlChar(differenceKindQuery)+" and verify='Y' " +
								" and '" + adjustOriginal + "'<=adjustdate " +
								" and caseno!="+Common.sqlChar(caseNoQuery) ;
				adjustMax = MaxClosingYM.getCountValues(adjustMaxSql);
	
				//該增減值單之增減值日期≦增減值單明細資料之其他減損單之最大的減損日期則提示使用者
				reduceMaxSql = "select count(*) count from UNTNE_REDUCEDETAIL " +
								" where enterorg="+Common.sqlChar(enterOrgQuery)+" and ownership="+Common.sqlChar(ownershipQuery)+" and propertyno="+Common.sqlChar(propertyNoQuery)+" and serialno="+Common.sqlChar(serialNoQuery)+" and differencekind="+Common.sqlChar(differenceKindQuery)+" and verify='Y' " +
								" and '" + adjustOriginal + "'<=reducedate " ;
				reduceMax = MaxClosingYM.getCountValues(reduceMaxSql);
				
				//------------------------------------------------------------------------------------------------
				if( verify.equals("Y") && reduceCount.equals("0") && adjustCount.equals("0") && reduceMax.equals("0") && adjustMax.equals("0")){
					//依據該增減值單「入帳機關enterOrg＋權屬ownership＋電腦單號caseNo」設定
					if(count==1){
						strSQL += "update UNTNE_ADJUSTPROOF set verify ='N',adjustdate='' " +
								" where enterorg=" + Common.sqlChar(enterOrgQuery) +
								" and caseno=" + Common.sqlChar(caseNoQuery) +
								":;:";				
						strSQL += "update UNTNE_ADJUSTDETAIL set verify ='N',adjustdate='' " +
								" where enterorg=" + Common.sqlChar(enterOrgQuery) +
								" and caseno=" + Common.sqlChar(caseNoQuery) +
								":;:";				
					}
					//依據增減值單明細「入帳機關enterOrg＋權屬ownership＋財產編號propertyNo＋財產批號lotNo」設定
					if(rs.getInt("addBookValue")!=0){
						addBookValue = rs.getInt("addBookValue");
					}
					if(rs.getInt("reduceBookValue")!=0){
						reduceBookValue = rs.getInt("reduceBookValue");
					}
					strSQL +="update UNTNE_NONEXP set " +
							" bookvalue = (bookvalue -"  + addBookValue + "+"+reduceBookValue+" )" +
							" where enterorg=" + Common.sqlChar(enterOrgQuery) +
							" and ownership=" + Common.sqlChar(ownershipQuery) +
							" and propertyno=" + Common.sqlChar(propertyNoQuery) +
							" and lotno=" + Common.sqlChar(rs.getString("lotNo")) +
							" and differencekind=" + Common.sqlChar(differenceKindQuery) +
							":;:";
	
					//依據增減值單明細「入帳機關enterOrg＋權屬ownership＋財產編號propertyNo＋財產分號serialNo」設定
					strSQL +="update UNTNE_NONEXPDETAIL set " +
							" bookvalue = " + rs.getString("oldBookValue") +
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
						setErrorMsg("減損作業存在未入帳的資料，無法回復入帳！");
					}else if(!adjustCount.equals("0")){
						setErrorMsg("增減值作業存在未入帳的資料，無法回復入帳！");
					}else if(!adjustMax.equals("0")){
						setErrorMsg("並非最後一筆入帳(增減值)的資料，無法回復入帳！");
					}else if(!reduceMax.equals("0")){
						setErrorMsg("並非最後一筆入帳(減損)的資料，無法回復入帳！");
					}
				}
				if ("Y".equals(getVerifyError())) {
				    break;
				}
			}
			//System.out.println("回復："+strSQL);
			if (!"Y".equals(getVerifyError())) {
				execSQLArray = strSQL.split(":;:");
				db.exeSQL_NoChange(execSQLArray);
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
}