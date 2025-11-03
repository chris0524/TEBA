/*
*<br>程式目的：非消耗品主檔資料維護－增加單資料
*<br>程式代號：untne001f
*<br>程式日期：0941028
*<br>程式作者：cherry
*<br>--------------------------------------------------------
*<br>修改作者　　修改日期　　　修改目的
*<br>--------------------------------------------------------
*/

package unt.ne;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Map;

import unt.ch.UNTCH_Tools;
import util.Common;
import util.Database;
import util.Datetime;
import util.MaxClosingYM;
import util.TCGHCommon;
import TDlib_Simple.com.src.DBServerTools;

public class UNTNE001F extends UNTNE001Q{


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
String enterDate;
String verify;
String notes;
String oldVerify;
String checkEnterOrg;
String differenceKind;


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
public String getEnterDate(){ return checkGet(enterDate); }
public void setEnterDate(String s){ enterDate=checkSet(s); }
public String getVerify(){ return checkGet(verify); }
public void setVerify(String s){ verify=checkSet(s); }
public String getNotes(){ return checkGet(notes); }
public void setNotes(String s){ notes=checkSet(s); }
public String getOldVerify(){ return checkGet(oldVerify); }
public void setOldVerify(String s){ oldVerify=checkSet(s); }
public String getCheckEnterOrg(){ return checkGet(checkEnterOrg); }
public void setCheckEnterOrg(String s){ checkEnterOrg=checkSet(s); }
public String getDifferenceKind() {return checkGet(this.differenceKind);}
public void setDifferenceKind(String s) {differenceKind = checkSet(s);}


public String getSummonsDate() {
	return checkGet(summonsDate);
}
public void setSummonsDate(String summonsDate) {
	this.summonsDate = checkSet(summonsDate);
}


String closingYM;
public String getClosingYM() {return checkGet(closingYM);}
public void setClosingYM(String closingYM) {closingYM = checkSet(closingYM);}
String proofYear;
public String getProofYear(){return checkGet(proofYear);}
public void setProofYear(String s) {proofYear = checkSet(s);}

String verifyError;
String approve="";

public String getVerifyError(){ return checkGet(verifyError); }
public void setVerifyError(String s){ verifyError=checkSet(s); }
public String getApprove(){ return checkGet(approve); }
public void setApprove(String s){ approve=checkSet(s); }
private String enterDateTemp;
public String getEnterDateTemp() {return checkGet(enterDateTemp);}
public void setEnterDateTemp(String enterDateTemp) {this.enterDateTemp = checkSet(enterDateTemp);}	


public boolean checkUpdateError(){
	boolean result = true;
	if("Y".equals(getVerify()) && isBigThanEnterDate()){
		result = false;
	}else if("Y".equals(getVerify()) && checkClosingYMfromUNTAC_CLOSINGNE(getEnterDate(),getEnterOrg(),getDifferenceKind())){
	result = false;
	setErrorMsg("入帳日期需為最大月結年月＋１");
	}else if("N".equals(getVerify()) && checkNECanNotReVerify(getEnterDateTemp(),getEnterOrg(),getDifferenceKind())){
	result = false;
	setErrorMsg("當月已月結不可取消入帳");
	}
	return result;
}

//檢查update時，proofyear是否有改變
private boolean checkProofyearChanged(String enterorg, String caseno, String newproofyear){
	boolean isChanged = false;
	String oldproofyear ="";
	ResultSet rs;
	Database db = new Database();
	try{
		String sql = "select proofyear from UNTNE_ADDPROOF where enterorg=" + Common.sqlChar(enterorg) +
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

//傳回執行insert前之檢查sql
protected String[][] getInsertCheckSQL(){
	//取得電腦單號
	Database db = new Database();
	ResultSet rs;	
	String sql="select cast(max(substring(caseNo,4,7))+1 AS varchar) as caseNo from UNTNE_AddProof " +
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
	setProofNo(MaxClosingYM.getMaxProofNo("UNTNE_AddProof",enterOrg,ownership,this.getProofYear()));
	//===================
	String[][] checkSQLArray = new String[1][4];
 	checkSQLArray[0][0]=" select count(*) as checkResult from UNTNE_AddProof where 1=1 " + 
		" and enterOrg = " + Common.sqlChar(enterOrg) +  
		" and caseNo = " + Common.sqlChar(caseNo) + 
		"";
	checkSQLArray[0][1]=">";
	checkSQLArray[0][2]="0";
	checkSQLArray[0][3]="該筆資料己重複，請重新輸入！";
	return checkSQLArray;
}


//傳回insert sql
protected String[] getInsertSQL(){
	UNTCH_Tools ul = new UNTCH_Tools();
	String[] execSQLArray = new String[1];
	execSQLArray[0]=" insert into UNTNE_AddProof(" +
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
			" enterDate,"+
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
			Common.sqlChar(ul._transToCE_Year(this.getEnterDate())) + "," +
			Common.sqlChar(this.getVerify()) + "," +
			Common.sqlChar(this.getNotes()) + "," +
			Common.sqlChar(this.getEditID()) + "," +
			Common.sqlChar(ul._transToCE_Year(this.getEditDate())) + "," +
			Common.sqlChar(this.getEditTime()) + "," +
	        Common.sqlChar(this.getDifferenceKind()) + "," +
	        Common.sqlChar(this.getProofYear()) + ")" ;
	return execSQLArray;
}

//傳回執行update前之檢查sql
protected String[][] getUpdateCheckSQL(){
	if ("N".equals(getVerify())) {
		String[][] checkSQLArray = new String[1][4];
	 	checkSQLArray[0][0]="select count(*) as checkResult from UNTNE_NONEXPDETAIL a " +
				" where enterOrg="+Common.sqlChar(getEnterOrg())+
				" and ownership="+Common.sqlChar(getOwnership())+
				" and caseNo = " + Common.sqlChar(getCaseNo()) + 
				" and differenceKind = " + Common.sqlChar(getDifferenceKind()) +
				" and (EXISTS (select 1 from UNTNE_ADJUSTDETAIL b where a.enterorg = b.enterorg and a.ownership = b.ownership and a.differencekind = b.differencekind and a.propertyno = b.propertyno and a.serialno = b.serialno)" +
				" or EXISTS (select 1 from UNTNE_REDUCEDETAIL b where a.enterorg = b.enterorg and a.ownership = b.ownership and a.differencekind = b.differencekind and a.propertyno = b.propertyno and a.serialno = b.serialno)" +
				" or EXISTS (select 1 from UNTNE_MOVEDETAIL b where a.enterorg = b.enterorg and a.ownership = b.ownership and a.differencekind = b.differencekind and a.propertyno = b.propertyno and a.serialno = b.serialno)" +
				" or EXISTS (select 1 from UNTNE_DEALDETAIL b where a.enterorg = b.enterorg and a.ownership = b.ownership and a.differencekind = b.differencekind and a.propertyno = b.propertyno and a.serialno = b.serialno)" +
				" );";
		checkSQLArray[0][1]=">";
		checkSQLArray[0][2]="0";
		checkSQLArray[0][3]="已做過增減值或減損或移動或廢品處理作業，請先刪除增減值或減損或移動或廢品處理作業之資料再回此作業取消入帳！";
		return checkSQLArray;		
	} else if ("Y".equals(getVerify())) {
		String[][] checkSQLArray = new String[1][4];
	 	checkSQLArray[0][0]="select count(*) as checkResult from UNTNE_NONEXPDETAIL a " +
				" where enterOrg="+Common.sqlChar(getEnterOrg())+
				" and ownership="+Common.sqlChar(getOwnership())+
				" and caseNo = " + Common.sqlChar(getCaseNo()) + 
				" and differenceKind = " + Common.sqlChar(getDifferenceKind()) +
				" ;";
		checkSQLArray[0][1]="=";
		checkSQLArray[0][2]="0";
		checkSQLArray[0][3]="沒有明細資料無法入帳！";
		return checkSQLArray;	
	} else {
		return super.getUpdateCheckSQL();
	}
}

//執行update
public void update() {
    Database db = new Database();
    UNTCH_Tools ul = new UNTCH_Tools();
    String strSQL = "";
	String[] execSQLArray = null;
	String className = this.getClass().getName();
	
	setEditID(getUserID());
	setEditDate(Datetime.getYYYMMDD());
	setEditTime(Datetime.getHHMMSS());	
	
	try {
		// update前檢查
		if (!beforeExecCheck(getUpdateCheckSQL())){
			setStateUpdateError();
			throw new Exception(getErrorMsg());
		}else{
			// addproof update sql
			strSQL +=" update UNTNE_AddProof set " +
					" caseName = " + Common.sqlChar(this.getCaseName()) + "," +
					" writeDate = " + Common.sqlChar(ul._transToCE_Year(this.getWriteDate())) + "," +
					" writeUnit = " + Common.sqlChar(this.getWriteUnit()) + "," +
					" proofDoc = " + Common.sqlChar(this.getProofDoc()) + "," +
					" proofNo = " + Common.sqlChar(this.getProofNo()) + "," +
					" manageNo = " + Common.sqlChar(this.getManageNo()) + "," +
					" summonsNo = " + Common.sqlChar(this.getSummonsNo()) + "," +
					" summonsDate = " + Common.sqlChar(ul._transToCE_Year(this.getSummonsDate())) + "," +
					" enterDate = " + Common.sqlChar(ul._transToCE_Year(this.getEnterDate())) + "," +
					" verify = " + Common.sqlChar(this.getVerify()) + "," +
					" notes = " + Common.sqlChar(this.getNotes()) + "," +
					" editID = " + Common.sqlChar(this.getEditID()) + "," +
					" editDate = " + Common.sqlChar(ul._transToCE_Year(this.getEditDate())) + "," +
					" editTime = " + Common.sqlChar(this.getEditTime()) + "," +
					" differencekind = " + Common.sqlChar(this.getDifferenceKind()) + "," +
					" proofyear = " + Common.sqlChar(this.getProofYear()) + 
				" where 1=1 " + 
					" and enterOrg = " + Common.sqlChar(this.getEnterOrg()) +
					" and caseNo = " + Common.sqlChar(this.getCaseNo()) +
					":;:";
			
			//問題單1290補充: update時更改編號-年，重新取得編號-號
			if(this.checkProofyearChanged(getEnterOrg(), getCaseNo(), getProofYear())){
				String newproofno = MaxClosingYM.getMaxProofNo("UNTNE_ADDPROOF",enterOrg,ownership,this.getProofYear());
				strSQL += " update UNTNE_ADDPROOF set proofno=" + Common.sqlChar(newproofno) +
									" where 1=1 " +
									" and enterorg = " + Common.sqlChar(this.getEnterOrg()) +
									" and caseno = " + Common.sqlChar(this.getCaseNo()) +
									":;:";
			}			
			
			// 入帳狀態改變(執行入帳 or 回復入帳), update 批&明細
			if (!getVerify().equals(getOldVerify())) {	
				strSQL += "update UNTNE_NONEXP set"+
				        " verify = " + Common.sqlChar(getVerify()) + "," +
			            " enterDate = " + Common.sqlChar(ul._transToCE_Year(getEnterDate())) + "," +
						" editID = " + Common.sqlChar(getEditID()) + "," +
						" editDate = " + Common.sqlChar(ul._transToCE_Year(getEditDate())) + "," +
						" editTime = " + Common.sqlChar(getEditTime()) +         
						" where 1=1 " + 
						" and enterOrg = " + Common.sqlChar(enterOrg) +
						" and caseNo = " + Common.sqlChar(caseNo) +
						":;:"; 
				strSQL += "update UNTNE_NONEXPDETAIL set"+
				        " verify = " + Common.sqlChar(getVerify()) + "," +
			            " enterDate = " + Common.sqlChar(ul._transToCE_Year(getEnterDate())) + "," +
						" editID = " + Common.sqlChar(getEditID()) + "," +
						" editDate = " + Common.sqlChar(ul._transToCE_Year(getEditDate())) + "," +
						" editTime = " + Common.sqlChar(getEditTime()) +         
						" where 1=1 " + 
						" and enterOrg = " + Common.sqlChar(enterOrg) +
						" and caseNo = " + Common.sqlChar(caseNo) +
						":;:"; 
			}
			
			// 執行update sql
			execSQLArray = strSQL.split(":;:");
			db.excuteSQL(execSQLArray);
		    setStateUpdateSuccess();
		    
		    //使用者操作記錄
			Common.insertUpdateRecordSql("", this.getEnterOrg(), this.getUserID(), className.substring(className.lastIndexOf('.') + 1), this.getObjPath().replaceAll("&gt;", ">") + " > > 修改" + this.getCaseNo());
			setErrorMsg("修改完成");
		}	    
	} catch (Exception e) {
		e.printStackTrace();
		setStateUpdateError();		
		setErrorMsg(e.getMessage());
	} finally {
		db.closeAll();
	}
}

//傳回delete sql
protected String[] getDeleteSQL(){
	String[] execSQLArray = null;
	String strSQLArray = ""; 

	Database db = new Database();
	ResultSet rs;	
	String sql="select enterOrg, ownership, propertyNo, lotNo from UNTNE_Nonexp " +
		" where enterOrg = " + Common.sqlChar(enterOrg) +
		" and caseNo = " + Common.sqlChar(caseNo) + 
		"";		
	try {		
		rs = db.querySQL(sql);
		while (rs.next()){
			strSQLArray += " delete from UNTNE_Nonexp " +
			" where enterOrg = " + Common.sqlChar(enterOrg) +
			" and caseNo = " + Common.sqlChar(caseNo) +
			" and propertyNo = " + Common.sqlChar(rs.getString("propertyNo")) +
			" and lotNo = " + Common.sqlChar(rs.getString("lotNo")) +
			":;:";
								
			strSQLArray += " delete from UNTNE_NonexpDetail " +
			" where enterOrg = " + Common.sqlChar(enterOrg) +
			" and propertyNo = " + Common.sqlChar(rs.getString("propertyNo")) +
			" and lotNo = " + Common.sqlChar(rs.getString("lotNo")) +
			":;:";

			strSQLArray += " delete from UNTNE_Attachment1 " +
			" where enterOrg = " + Common.sqlChar(enterOrg) +
			" and propertyNo = " + Common.sqlChar(rs.getString("propertyNo")) +
			" and lotNo = " + Common.sqlChar(rs.getString("lotNo")) +
			":;:";

			strSQLArray += " delete from UNTNE_Attachment2 " +
			" where enterOrg = " + Common.sqlChar(enterOrg) +
			" and propertyNo = " + Common.sqlChar(rs.getString("propertyNo")) +
			" and lotNo = " + Common.sqlChar(rs.getString("lotNo")) +
			":;:";			
		}
		rs.close();		
	} catch (Exception e) {
		e.printStackTrace();
	} finally {
		db.closeAll();
	}		
	strSQLArray +=" delete from UNTNE_AddProof where 1=1 " +
	" and enterOrg = " + Common.sqlChar(enterOrg) +
	" and caseNo = " + Common.sqlChar(caseNo) +
	":;:";
	
	strSQLArray += " update UNTNE_REDUCEPROOF set addcaseno = null where 1=1 " +
			" and enterorg = " + Common.sqlChar(enterOrg) +
			" and ownership = " + Common.sqlChar(ownership) +
			" and addcaseno = " + Common.sqlChar(caseNo) +
			":;:";
	
	execSQLArray = strSQLArray.split(":;:");	
	return execSQLArray;
}



/**
 * <br>
 * <br>目的：依主鍵查詢單一資料
 * <br>參數：無
 * <br>傳回：傳回本物件
*/

public UNTNE001F  queryOne() throws Exception{
	UNTCH_Tools ul = new UNTCH_Tools();
	Database db = new Database();
	UNTNE001F obj = this;
	try {
		String sql=" select b.organSName, a.enterOrg, a.ownership, a.caseNo, a.caseName, a.writeDate, a.writeUnit, a.proofDoc, a.proofNo, a.manageNo, a.summonsNo, a.enterDate, a.verify, a.notes, a.editID, a.editDate, a.editID, a.editDate, a.editTime, a.proofYear, a.differencekind,a.summonsDate  "+
		" from UNTNE_AddProof a, SYSCA_Organ b where 1=1 " +
		" and a.enterOrg = " + Common.sqlChar(enterOrg) +
		" and a.caseNo = " + Common.sqlChar(caseNo) +
		" and b.organID = a.enterOrg " +
		"";
		ResultSet rs = db.querySQL(sql);
		if (rs.next()){
			obj.setEnterOrg(rs.getString("enterOrg"));
			obj.setOwnership(rs.getString("ownership"));
			obj.setEnterOrgName(rs.getString("organSName"));
			obj.setCaseNo(rs.getString("caseNo"));
			obj.setCaseName(rs.getString("caseName"));
			obj.setWriteDate(ul._transToROC_Year(rs.getString("writeDate")));
			obj.setWriteUnit(rs.getString("writeUnit"));
			obj.setProofDoc(rs.getString("proofDoc"));
			obj.setProofNo(rs.getString("proofNo"));
			obj.setManageNo(rs.getString("manageNo"));
			obj.setSummonsNo(rs.getString("summonsNo"));
			obj.setSummonsDate(ul._transToROC_Year(rs.getString("summonsDate")));
			obj.setEnterDate(ul._transToROC_Year(rs.getString("enterDate")));			
			obj.setVerify(rs.getString("verify"));
			obj.setOldVerify(rs.getString("verify"));
			obj.setNotes(rs.getString("notes"));
			obj.setEditID(rs.getString("editID"));
			obj.setEditDate(ul._transToROC_Year(rs.getString("editDate")));
			obj.setEditTime(rs.getString("editTime"));
			obj.setProofYear(rs.getString("proofYear"));
			obj.setDifferenceKind(rs.getString("differencekind"));
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
	
	setOldPage();
	UNTCH_Tools ul = new UNTCH_Tools();
	Database db = new Database();
	ArrayList objList=new ArrayList();
	int counter=0;
	Map<String,String> differencekindMap = TCGHCommon.getSysca_Code("DFK"); 					//財產區分別名稱
	try {
		String sql=" select distinct a.enterOrg, a.ownership, a.differencekind," +
					" (select z.codeName from sysca_code z where 1=1 and (z.codeKindID='owa' or z.codeKindID='OWA') and a.ownership=z.codeID) as ownershipName, " +
					" a.caseNo, a.caseName,cast(a.proofyear as int) as proofyear,a.proofdoc,a.proofno,"+
					" a.writeDate, a.enterDate, (case a.verify when 'Y' then '是' else '否' end) verify "+
					" from UNTNE_AddProof a"+
					" left join UNTNE_Nonexp b on a.caseNo=b.caseNo and a.enterOrg=b.enterOrg"+
					" left join UNTNE_NonexpDetail c on c.enterOrg=b.enterOrg and b.lotNo=c.lotNo and b.propertyNo=c.propertyNo and b.caseno=c.caseno"+
					" where 1=1 " ;
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
		
		if ("nonexp".equals(getQuerySelect()) || "nonexpDetail".equals(getQuerySelect()) || "".equals(getQuerySelect())) {
			sql+=" and a.enterOrg = " + Common.sqlChar(getQ_enterOrg()) ;
			
		} else {
			if (!"".equals(getQ_enterOrg())) {
				sql+=" and a.enterOrg = " + Common.sqlChar(getQ_enterOrg()) ;
			} else {
				if (!getIsAdminManager().equalsIgnoreCase("Y")) {
					if ("Y".equals(getIsOrganManager())) {					
						//sql += " and a.enterOrg like '" + getOrganID().substring(0,5) + "%' ";		
						sql += " and ( a.enterOrg = "+ Common.sqlChar(getOrganID()) +" or a.enterOrg in ( select organID from SYSCA_Organ where organSuperior = "+ Common.sqlChar(getOrganID()) +" ) ) ";					
					} else {
						sql+=" and a.enterOrg = " + Common.sqlChar(getOrganID()) ;
					}
				}
			}
			if (!"".equals(getQ_caseNoS()))
				sql+=" and a.caseNo >= " + Common.sqlChar(Common.formatRearString(getQ_caseNoS(),10,'0'));		
			if (!"".equals(getQ_caseNoE()))
				sql+=" and a.caseNo <= " + Common.sqlChar(Common.formatRearString(getQ_caseNoE(),10, '9'));
			if (!"".equals(getQ_ownership()))
				sql+=" and a.ownership = " + Common.sqlChar(getQ_ownership()) ;   	    
			if (!"".equals(getQ_enterDateS()))
				sql+=" and a.enterDate >= " + Common.sqlChar(ul._transToCE_Year(getQ_enterDateS()));
			if (!"".equals(getQ_enterDateE()))
				sql+=" and a.enterDate <= " + Common.sqlChar(ul._transToCE_Year(getQ_enterDateE()));
			if (!"".equals(getQ_proofDoc()))
				sql+=" and a.proofDoc like '%" + getQ_proofDoc() + "%'" ;
			if (!"".equals(getQ_proofNoS())) 
				sql+=" and a.proofNo >= " + Common.sqlChar(getQ_proofNoS());		
			if (!"".equals(getQ_proofNoE())) 
				sql+=" and a.proofNo <= " + Common.sqlChar(getQ_proofNoE());		 
			if (!"".equals(getQ_writeDateS()))
				sql+=" and a.writeDate >= " + Common.sqlChar(ul._transToCE_Year(getQ_writeDateS()));		
			if (!"".equals(getQ_writeDateE()))
				sql+=" and a.writeDate<=" + Common.sqlChar(ul._transToCE_Year(getQ_writeDateE()));   	    
			if (!"".equals(getQ_verify()))
				sql+=" and a.verify  = " + Common.sqlChar(getQ_verify()) ;
			if (!"".equals(getQ_cause()))
				sql+=" and b.cause = " + Common.sqlChar(getQ_cause()) ;			
			if (!"".equals(getQ_propertyNoS()))
				sql+=" and b.propertyNo >= " + Common.sqlChar(getQ_propertyNoS());		
			if (!"".equals(getQ_propertyNoE()))
				sql+=" and b.propertyNo <= " + Common.sqlChar(getQ_propertyNoE());						
			if (!"".equals(getQ_lotNo()))
				sql+=" and b.lotNo = " + Common.sqlChar(Common.formatFrontZero(getQ_lotNo(),7)) ;
			if (!"".equals(getQ_serialNoS()))
				sql+=" and c.serialNo >= " + Common.sqlChar(getQ_serialNoS());		
			if (!"".equals(getQ_serialNoE()))
				sql+=" and c.serialNo <= " + Common.sqlChar(getQ_serialNoE());			
			if (!"".equals(getQ_dataState()))
				sql+=" and b.dataState = " + Common.sqlChar(getQ_dataState()) ;
			if (!"".equals(getQ_propertyKind()))
				sql+=" and b.propertyKind = " + Common.sqlChar(getQ_propertyKind()) ;
			if (!"".equals(getQ_fundType()))
				sql+=" and b.fundType = " + Common.sqlChar(getQ_fundType()) ;
			if (!"".equals(getQ_valuable()))
				sql+=" and b.valuable = " + Common.sqlChar(getQ_valuable()) ;	    
			if (!"".equals(getQ_keepUnit()))
				sql+=" and c.keepUnit = " + Common.sqlChar(getQ_keepUnit()) ;	    
			if (!"".equals(getQ_keeper()))
				sql+=" and c.keeper = " + Common.sqlChar(getQ_keeper()) ;	    
			if (!"".equals(getQ_useUnit()))
				sql+=" and c.useUnit = " + Common.sqlChar(getQ_useUnit()) ;	    
			if (!"".equals(getQ_userNo()))
				sql+=" and c.userNo = " + Common.sqlChar(getQ_userNo()) ;	    
			if (!"".equals(getQ_buyDateS()))
				sql+=" and b.buyDate >= " + Common.sqlChar(ul._transToCE_Year(getQ_buyDateS()));
			if (!"".equals(getQ_buyDateE()))
				sql+=" and b.buyDate <= " + Common.sqlChar(ul._transToCE_Year(getQ_buyDateE()));
			if (!"".equals(getQ_propertyName1()))
				sql+=" and b.propertyName1 like " + Common.sqlChar("%"+getQ_propertyName1()+"%") ;	
			if (!"".equals(getQ_oldSerialNoS()))
				sql+=" and c.oldserialno >= " + Common.sqlChar(Common.formatFrontZero(getQ_oldSerialNoS(),7));
			if (!"".equals(getQ_oldSerialNoE()))
				sql+=" and c.oldserialno <=" + Common.sqlChar(Common.formatFrontZero(getQ_oldSerialNoE(),7));
			if (!"".equals(getQ_oldlotNoS())){
				sql += " and c.oldlotNo >= " + Common.sqlChar(getQ_oldlotNoS());
			}
			if (!"".equals(getQ_oldlotNoE())){
				sql += " and c.oldlotNo <= " + Common.sqlChar(getQ_oldlotNoE());	
			}
			
			if (!"".equals(getQ_differenceKind()))
				sql+=" and a.differencekind = " + Common.sqlChar(getQ_differenceKind()) ;
			if (!"".equals(getQ_proofYear()))
				sql+=" and a.proofyear = " + Common.sqlChar(getQ_proofYear()) ;
			if(!"".equals(getQ_userNote()))
				sql+=" and c.userNote like " + Common.sqlChar("%" + getQ_userNote() + "%");
			if(!"".equals(getQ_place()))
				sql+=" and c.place1 = " + Common.sqlChar(getQ_place1());
			if(!"".equals(getQ_place1()))
				sql+=" and c.place like " + Common.sqlChar("%" + getQ_place() + "%");
			
			if (!"".equals(getQ_notes())) {
				sql += " and ( replace(a.notes, '-', space(0)) like " + Common.sqlChar("%" + getQ_notes().replace("-", "") + "%") + "or";
				sql += " replace(b.notes like, '-', space(0)) " + Common.sqlChar("%" + getQ_notes().replace("-", "") + "%") + "or";
				sql += " replace(c.notes like, '-', space(0)) " + Common.sqlChar("%" + getQ_notes().replace("-", "") + "%") + ")";
			}
		}
		
		if ("1".equals(this.getRoleid())) {
			// 1185 增加能查到單的異動人員是自己的單
			sql += " and ( a.editid = " + Common.sqlChar(this.getUserID()) +
						 " or c.keeper = " + Common.sqlChar(this.getUserID()) +
						 " )";			
		} else if ("2".equals(this.getRoleid())) {
			sql += " and ( a.editid = " + Common.sqlChar(this.getUserID()) +
						 " or a.writeunit = " + Common.sqlChar(this.getUnitID()) +
						 " )";
		}
		
		sql+=" order by proofyear desc,a.proofno desc";
		System.out.println("---------- "+sql);
		ResultSet rs = db.querySQL(sql.toString(),true);
		processCurrentPageAttribute(rs);
		if (getTotalRecord() > 0) {
			int count = getRecordStart();
			int end = getRecordEnd();
			do {
				if (count > end)
					break;
			String rowArray[]=new String[10];
			rowArray[0]=Common.get(rs.getString("enterOrg")); 
			rowArray[1]=Common.get(rs.getString("ownership")); 
			rowArray[2]=Common.get(rs.getString("ownershipName"));
			rowArray[3]=differencekindMap.get(rs.getString("differencekind"));
			rowArray[4]=Common.get(rs.getString("proofyear"))+"年"+Common.get(rs.getString("proofdoc"))+"字第"+Common.get(rs.getString("proofno"))+"號"; 
			rowArray[5]=Common.get(rs.getString("caseNo")); 
			rowArray[6]=Common.get(rs.getString("caseName")); 
			rowArray[7]=ul._transToROC_Year(rs.getString("writeDate")); 
			rowArray[8]=Common.get(rs.getString("verify")); 
			rowArray[9]=new Datetime().changeTaiwanYYMMDD(rs.getString("enterDate"),"1"); 
			
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

//取得最大的月結年月
protected String getMaxClosingYM(String enterOrg,String differenceKind){
	Database db = new Database();
	ResultSet rs;	
	String closing1YM ="" ;
	String sql="select max(closing1ym) as closing1ym from UNTAC_CLOSINGNE " +
		" where enterorg = " + Common.sqlChar(enterOrg) +
		" and differencekind = "+ Common.sqlChar(differenceKind)+
		" ";		
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

//==============================================================

	public boolean isBigThanEnterDate(){
		boolean result = false;
		result = checkBuyDate_sourceDate_buildDate();		
		return result;
	}

	private boolean checkBuyDate_sourceDate_buildDate(){
		Database db = null;
		boolean result = false;
		ResultSet rs = null;
		int buydate = 0;
		int sourcedate = 0;
		try{
			int maxdate = Integer.parseInt(new UNTCH_Tools()._transToCE_Year(getEnterDate()));
			String sql = "select " +
							" max(buydate) as buydate," +
							" max(sourcedate) as sourcedate" +
							" from (" +
							" select " + 
							" case when buydate is null then 0 else buydate end as buydate," +
							" case when sourcedate is null then 0 else sourcedate end as sourcedate " + 
							" from UNTNE_NONEXP " + 
							" where 1=1 " + getCheckEnterDateCondition() +
							") a";
			
			db = new Database();
			rs = db.querySQL(sql);
			rs.next();
			buydate = rs.getInt("buydate");
			sourcedate = rs.getInt("sourcedate");
			rs.close();
			
			if( maxdate < buydate || maxdate < sourcedate){
				result = true;
				setErrorMsg("入帳日期不可小於 購置日期、物品取得日期");
			}
			
		}catch(Exception e){
			result = true;
			setErrorMsg("檢查失敗！");
		}finally{
			db.closeAll();
		}
		return result;
	}

	private String getCheckEnterDateCondition(){
		String result = " and enterorg = " + Common.sqlChar(getEnterOrg()) +
						" and caseno = " + Common.sqlChar(getCaseNo());
			return result;
	}

	//檢查此電腦單號在指定的table中是否有資料
	public boolean checkDataCountIsZero(String enterOrg, String caseNo){
		boolean result = false;
			
		String condition = " where 1=1" +
			" and enterorg = '" + enterOrg + "'" +
			" and caseNo = '" + caseNo + "'";
			
		String sql = " select count(*) as count from UNTNE_NONEXPDETAIL " + condition;
			
		DBServerTools dbt = new DBServerTools();
		dbt._setDatabase(new Database());
		String checkDataCount = dbt._execSQL_asString(sql);
			
		if("0".equals(checkDataCount)){
			result = true;
		}
		dbt = null;
		checkDataCount = null;
		condition = null;
		sql = null;
		return result;
	}		
}


