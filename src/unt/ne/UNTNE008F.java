/*
*<br>程式目的：非消耗品保管使用異動作業－移動單資料
*<br>程式代號：untne008f
*<br>程式日期：0941017
*<br>程式作者：carey
*<br>--------------------------------------------------------
*<br>修改作者　　修改日期　　　修改目的
*<br>--------------------------------------------------------
*/

package unt.ne;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Map;

import org.apache.log4j.Logger;

import unt.ch.UNTCH_Tools;
import util.*;

public class UNTNE008F extends UNTNE008Q{
	private Logger logger = Logger.getLogger(this.getClass());

String enterOrg;
String enterOrgName;
String ownership;
String caseNo;
String caseName;
String writeDate;
String writeUnit;
String proofDoc;
String proofNo;
String moveDate;
String verify;
String notes;
String differenceKind;

String propertyNo;
String serialNo;
String verifyError;

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
public String getMoveDate(){ return checkGet(moveDate); }
public void setMoveDate(String s){ moveDate=checkSet(s); }
public String getVerify(){ return checkGet(verify); }
public void setVerify(String s){ verify=checkSet(s); }
public String getNotes(){ return checkGet(notes); }
public void setNotes(String s){ notes=checkSet(s); }
public String getDifferenceKind() {return checkGet(this.differenceKind);}
public void setDifferenceKind(String s) {differenceKind = checkSet(s);}


public String getPropertyNo(){ return checkGet(propertyNo); }
public void setPropertyNo(String s){ propertyNo=checkSet(s); }
public String getSerialNo(){ return checkGet(serialNo); }
public void setSerialNo(String s){ serialNo=checkSet(s); }
public String getVerifyError(){ return checkGet(verifyError); }
public void setVerifyError(String s){ verifyError=checkSet(s); }

String newMoveDate;

public String getNewMoveDate(){ return checkGet(newMoveDate); }
public void setNewMoveDate(String s){ newMoveDate=checkSet(s); }
String proofYear;
public String getProofYear(){return checkGet(proofYear);}
public void setProofYear(String s) {proofYear = checkSet(s);}
private String moveDateTemp;
public String getMoveDateTemp() {return checkGet(moveDateTemp);}
public void setMoveDateTemp(String moveDateTemp) {this.moveDateTemp = checkSet(moveDateTemp);}

	private String UNTNE008F_currentPage;
	public String getUNTNE008F_currentPage() { return checkGet(UNTNE008F_currentPage); }
	public void setUNTNE008F_currentPage(String UNTNE008F_currentPage) {
		this.UNTNE008F_currentPage = checkSet(UNTNE008F_currentPage);
	}
	
public boolean checkUpdateError(){
	boolean result = true;
	UNTCH_Tools ut = new UNTCH_Tools();
	if ("Y".equals(getVerify())) {
		if(!checkClosingYM("verify")){
			setErrorMsg("入帳日期需為最大月結年月＋１");
			result = false;
		}
		
		if (!checkNewestMovedate(ut._transToCE_Year(getMoveDate()))) {
			setErrorMsg("入帳日期需為最新一筆資料");
			result = false;
		}
	}
	return result;
}

public boolean checkReVerifyError(){
	boolean result = true;
	UNTCH_Tools ut = new UNTCH_Tools();
	if(!checkClosingYM("reverify")){
		setErrorMsg("當月已月結不可取消入帳");
		result = false;
	}
	
	if(!checkExistsNotVerify()){
		setErrorMsg("單據內有物品存在於尚未入帳之移動單內，無法回復入帳");
		result = false;
	}
	
	if (!checkNewestMovedate(ut._transToCE_Year(getMoveDateTemp()))) {
		setErrorMsg("非最後一筆入帳的資料，無法回復入帳");
		result = false;
	}
	return result;
}

//檢查月結年月
private boolean checkClosingYM(String type){
	boolean result = true;
	ResultSet rs;
	Database db = new Database();
	try{
		// 取得全部明細的區分別
		String sql = "select distinct differencekind from UNTNE_MOVEDETAIL"
				+ " where 1=1"
					+ " and enterorg=" + Common.sqlChar(getEnterOrg())
					+ " and caseno=" + Common.sqlChar(getCaseNo())
				+ "";
		rs = db.querySQL(sql);
		while(rs.next()){
			String differencekind = rs.getString(1);
			if ("verify".equals(type)) {
				if(super.checkClosingYMfromUNTAC_CLOSINGNE(getMoveDate(),getEnterOrg(),differencekind)){
					result = false;
					break;
				}
			} else if ("reverify".equals(type)) {
				if(super.checkNECanNotReVerify(getMoveDateTemp(),getEnterOrg(),differencekind)){
					result = false;
					break;
				}
			}
		}
	}catch(Exception e){
		e.printStackTrace();
	}finally{
		db.closeAll();
	}
	return result;
}

//檢查是否為最後一筆移動資料
private boolean checkNewestMovedate(String movedate){
	boolean result = false;
	ResultSet rs;
	Database db = new Database();
	try{
		String sql = "select count(1) from UNTNE_MOVEDETAIL a"
				+ " where 1=1"
					+ " and enterorg=" + Common.sqlChar(getEnterOrg())
					+ " and caseno=" + Common.sqlChar(getCaseNo())
					+ " and exists ("
						+ " select 1 from UNTNE_MOVEDETAIL"
						+ " where enterorg=a.enterorg and ownership=a.ownership and differencekind=a.differencekind "
							+ " and propertyno=a.propertyno and serialno=a.serialno"
							+ " and caseno<>a.caseno"
							+ " and newmovedate>="  + Common.sqlChar(movedate)
					+ ")"
				+ "";
		rs = db.querySQL(sql);
		if(rs.next()){
			if (rs.getInt(1) == 0) {
				result = true;
			}
		}
	}catch(Exception e){
		e.printStackTrace();
	}finally{
		db.closeAll();
	}
	return result;
}
	
//檢查單據內物品，是否有存在於其他單據且未入帳的移動資料
private boolean checkExistsNotVerify(){
	boolean result = false;
	ResultSet rs;
	Database db = new Database();
	try{
		String sql = "select count(1) from UNTNE_MOVEDETAIL a"
				+ " where 1=1"
					+ " and enterorg=" + Common.sqlChar(getEnterOrg())
					+ " and caseno=" + Common.sqlChar(getCaseNo())
					+ " and exists ("
						+ " select 1 from UNTNE_MOVEDETAIL"
						+ " where enterorg=a.enterorg and ownership=a.ownership and differencekind=a.differencekind "
							+ " and propertyno=a.propertyno and serialno=a.serialno"
							+ " and verify='N'"
					+ ")"	
				+ "";
		rs = db.querySQL(sql);
		if(rs.next()){
			if (rs.getInt(1) == 0) {
				result = true;
			}
		}
	}catch(Exception e){
		e.printStackTrace();
	}finally{
		db.closeAll();
	}
	return result;
}

//傳回執行insert前之檢查sql
protected String[][] getInsertCheckSQL(){
	String[][] checkSQLArray = new String[1][4];
 	checkSQLArray[0][0]=" select count(*) as checkResult from UNTNE_MoveProof where 1=1 " + 
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
	//取得電腦單號
	Database db = new Database();
	ResultSet rs;	
	String sql="select cast(max(substring(caseNo,4,7))+1 AS varchar) as caseNo from UNTNE_MoveProof " +
		" where enterOrg = " + Common.sqlChar(enterOrg) +
		" and substring(caseNo,1,3) = " + Common.sqlChar(Datetime.getYYYMMDD().substring(0,3)) + 
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
	//取得移動單編號－號
	setProofNo(MaxClosingYM.getMaxProofNo("UNTNE_MoveProof",enterOrg,ownership,this.getProofYear()));
	//===================
    String[] execSQLArray = new String[1];
	execSQLArray[0]=" insert into UNTNE_MoveProof(" +
			" enterOrg,"+
			" ownership,"+
			" caseNo,"+
			" caseName,"+
			" writeDate,"+
			" writeUnit,"+
			" proofDoc,"+
			" proofNo,"+
			" moveDate,"+
			" verify,"+
			" notes,"+
			" editID,"+
			" editDate,"+
			" editTime,"+
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
			Common.sqlChar(ul._transToCE_Year(this.getMoveDate())) + "," +
			Common.sqlChar(this.getVerify()) + "," +
			Common.sqlChar(this.getNotes()) + "," +
			Common.sqlChar(getEditID()) + "," +
			Common.sqlChar(ul._transToCE_Year(getEditDate())) + "," +
			Common.sqlChar(getEditTime()) + "," +
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
		String sql = "select proofyear from UNTNE_MOVEPROOF where enterorg=" + Common.sqlChar(enterorg) +
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

//傳回執行update前之檢查sql
protected String[][] getUpdateCheckSQL(){
	if ("Y".equals(getVerify())) {
		String[][] checkSQLArray = new String[1][4];
	 	checkSQLArray[0][0]=" select count(*) as checkResult from UNTNE_MoveDetail where 1=1 " + 
			" and enterOrg = " + Common.sqlChar(enterOrg) + 
			" and caseNo = " + Common.sqlChar(caseNo) + 
			"";
		checkSQLArray[0][1]="<=";
		checkSQLArray[0][2]="0";
		checkSQLArray[0][3]="該筆移動單之移動單明細標籤要有資料才能入帳！";
	
		return checkSQLArray;
		
	} else { return super.getUpdateCheckSQL(); }
}	

//傳回update sql
public void update(){
	UNTCH_Tools ul = new UNTCH_Tools();
    Database db = new Database();    
	String[] execSQLArray = new String[3];
	String strSQL = "";
	String className = this.getClass().getName();
	
	setEditID(getUserID());
	setEditDate(Datetime.getYYYMMDD());
	setEditTime(Datetime.getHHMMSS());					
	//更新非消耗品移動單
	strSQL += " update UNTNE_MoveProof set " + "\n" +
			" caseName = " + Common.sqlChar(this.getCaseName()) + "," + "\n" +
			" writeDate = " + Common.sqlChar(ul._transToCE_Year(this.getWriteDate())) + "," + "\n" +
			" writeUnit = " + Common.sqlChar(this.getWriteUnit()) + "," + "\n" +
			" proofDoc = " + Common.sqlChar(this.getProofDoc()) + "," + "\n" +
			" proofNo = " + Common.sqlChar(this.getProofNo()) + "," + "\n" +
			" moveDate = " + Common.sqlChar(ul._transToCE_Year(this.getMoveDate())) + "," + "\n" +
			" verify = " + Common.sqlChar(this.getVerify()) + "," + "\n" +
			" notes = " + Common.sqlChar(this.getNotes()) + "," + "\n" +
			" editID = " + Common.sqlChar(getEditID()) + "," + "\n" +
			" editDate = " + Common.sqlChar(ul._transToCE_Year(getEditDate())) + "," + "\n" +
			" editTime = " + Common.sqlChar(getEditTime()) + "," + "\n" +
			" proofyear = " + Common.sqlChar(getProofYear()) +  "\n" +
		" where 1=1 " + "\n" + 
			" and enterOrg = " + Common.sqlChar(this.getEnterOrg()) + "\n" +
			" and caseNo = " + Common.sqlChar(this.getCaseNo()) + "\n" +
			":;:";	
	//問題單1290補充: update時更改編號-年，重新取得編號-號
	if(this.checkProofyearChanged(getEnterOrg(), getCaseNo(), getProofYear())){
		String newproofno = MaxClosingYM.getMaxProofNo("UNTNE_MOVEPROOF",enterOrg,ownership,this.getProofYear());
		strSQL += " update UNTNE_MOVEPROOF set proofno=" + Common.sqlChar(newproofno) +
							" where 1=1 " +
							" and enterorg = " + Common.sqlChar(enterOrg) +
							" and caseno = " + Common.sqlChar(caseNo) +
							":;:";
	}
	//更新非消耗品移動單明細檔
	strSQL+=" update UNTNE_MoveDetail set " +
			" newMoveDate = " + Common.sqlChar(ul._transToCE_Year(moveDate)) + "," + "\n" +	
			" verify = " + Common.sqlChar(verify) + "," + "\n" +	
			" editID = " + Common.sqlChar(getEditID()) + "," + "\n" +
			" editDate = " + Common.sqlChar(ul._transToCE_Year(getEditDate())) + "," + "\n" +
			" editTime = " + Common.sqlChar(getEditTime()) + "\n" +
		" where 1=1 " + 
			" and enterOrg = " + Common.sqlChar(enterOrg) +
			//" and ownership = " + Common.sqlChar(ownership) +
			" and caseNo = " + Common.sqlChar(caseNo) +		
			":;:";

	if ("Y".equals(getVerify())) {
		strSQL += checkVerify() + ":;:";
	}
	
	execSQLArray = strSQL.split(":;:");
	
//for (int i=0;i<execSQLArray.length;i++){System.out.println(execSQLArray[i]);}
	
	if (!"Y".equals(getVerifyError())) {
	    try {
			if (!super.beforeExecCheck(this.getUpdateCheckSQL())){	   			
				setStateUpdateError();
				setVerify("N");		
			}else if("Y".equals(getVerifyError())){
		        setStateUpdateError();
		        setVerify("N");	
		        //setErrorMsg("入帳設定有錯，移動日期之年月必須大於最大的月結年月！");
			} else {	    	
				db.excuteSQL(execSQLArray);		        
				setStateUpdateSuccess();
				
				//使用者操作記錄
				Common.insertUpdateRecordSql("", this.getEnterOrg(), this.getUserID(), className.substring(className.lastIndexOf('.') + 1), this.getObjPath().replaceAll("&gt;", ">") + " > > 修改" + this.getCaseNo());
				setErrorMsg("修改完成");		
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.closeAll();
		}	
	} else {
		setVerify("N");	
		setStateUpdateError();		
	}
}


//傳回delete sql
protected String[] getDeleteSQL(){
	UNTCH_Tools ul = new UNTCH_Tools();
	String[] execSQLArray = new String[2];
	//刪除非消耗品移動單
	execSQLArray[0]=" delete UNTNE_MoveProof where 1=1 " +
			" and enterOrg = " + Common.sqlChar(enterOrg) +
			//" and ownership = " + Common.sqlChar(ownership) +
			" and caseNo = " + Common.sqlChar(caseNo) +
			"";
	
	//刪除非消耗品移動單明細檔
	execSQLArray[1]=" delete UNTNE_MoveDetail where 1=1 " +
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

public UNTNE008F  queryOne() throws Exception{
	UNTCH_Tools ul = new UNTCH_Tools();
	Database db = new Database();
	UNTNE008F obj = this;
	try {
		String sql=" select b.organSName, a.enterOrg, a.ownership, a.caseNo, a.caseName, a.writeDate, a.writeUnit, a.proofDoc, a.proofNo, a.moveDate, a.verify, a.notes, a.editID, a.editDate, a.editTime,a.proofyear  "+
			" from UNTNE_MoveProof a,SYSCA_Organ b where 1=1 " +
			" and a.enterOrg = " + Common.sqlChar(enterOrg) +
			//" and a.ownership = " + Common.sqlChar(ownership) +
			" and a.caseNo = " + Common.sqlChar(caseNo) +
			" and b.organID = a.enterOrg" +
			"";
//System.out.println("UNTNE008F_queryOne==>"+sql);
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
			obj.setMoveDate(ul._transToROC_Year(rs.getString("moveDate")));
			obj.setNewMoveDate(ul._transToROC_Year(rs.getString("moveDate")));
			obj.setVerify(rs.getString("verify"));
			obj.setNotes(rs.getString("notes"));
			obj.setEditID(rs.getString("editID"));
			obj.setEditDate(ul._transToROC_Year(rs.getString("editDate")));
			obj.setEditTime(rs.getString("editTime"));
			obj.setProofYear(rs.getString("proofyear"));
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
	Map<String,String> differencekindMap = TCGHCommon.getSysca_Code("DFK"); 					//財產區分別名稱
	Database db = new Database();
	ArrayList objList=new ArrayList();
	int counter=0;
	try {
		String sql= " select distinct a.enterOrg, a.ownership, " +
					" cast(a.proofyear as int) as proofyear,a.proofdoc,a.proofno," +
					" (select z.codeName from sysca_code z where 1=1 and (z.codeKindID='owa' or z.codeKindID='OWA') and a.ownership=z.codeID) as ownershipName, " +
					" a.caseNo, a.caseName, a.writeDate, a.moveDate,(case a.verify when 'Y' then '是' else '否' end) verifyName "+
					" from UNTNE_MoveProof a"+
					" left join UNTNE_MoveDetail b on a.enterOrg=b.enterOrg and a.caseNo=b.caseNo " +
					" left join SYSCA_Organ c on a.enterOrg = c.organID "+
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
		
		if ("MoveDetail".equals(getQuerySelect()) || "".equals(getQuerySelect())) {
			sql+=" and a.enterOrg=" + Common.sqlChar(getEnterOrg());
			//sql+=" and a.ownership=" + Common.sqlChar(getOwnership());			
			sql+=" and a.caseNo=" + Common.sqlChar(getCaseNo());
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
			if (!"".equals(getQ_ownership()))
				sql+=" and a.ownership = " + Common.sqlChar(getQ_ownership()) ;
			if (!"".equals(getQ_caseNoS()))
				sql+=" and a.caseNo >= rpad(" + Common.sqlChar(getQ_caseNoS()) + ",10,'0')";
			if (!"".equals(getQ_caseNoE()))
				sql+=" and a.caseNo <= rpad(" + Common.sqlChar(getQ_caseNoE()) + ",10,'9')";
			if (!"".equals(getQ_caseName()))
				sql+=" and a.caseName like " + Common.sqlChar("%"+getQ_caseName()+"%") ;
			if (!"".equals(getQ_writeDateS()))
				sql+=" and a.writeDate >= " + Common.sqlChar(ul._transToCE_Year(getQ_writeDateS())) ;
			if (!"".equals(getQ_writeDateE()))
				sql+=" and a.writeDate <= " + Common.sqlChar(ul._transToCE_Year(getQ_writeDateE())) ;
			if (!"".equals(getQ_moveDateS()))
				sql+=" and a.moveDate >= " + Common.sqlChar(ul._transToCE_Year(getQ_moveDateS())) ;
			if (!"".equals(getQ_moveDateE()))
				sql+=" and a.moveDate <= " + Common.sqlChar(ul._transToCE_Year(getQ_moveDateE())) ;
			if (!"".equals(getQ_proofDoc()))
				sql+=" and a.proofDoc like " + Common.sqlChar("%"+getQ_proofDoc()+"%") ;
			if (!"".equals(getQ_proofNoS()))
				sql+=" and a.proofNo >= " + Common.sqlChar(getQ_proofNoS()) ;
			if (!"".equals(getQ_proofNoE()))
				sql+=" and a.proofNo <= " + Common.sqlChar(getQ_proofNoE()) ;
			if (!"".equals(getQ_verify()))
				sql+=" and a.verify = " + Common.sqlChar(getQ_verify()) ;
			
			if (!"".equals(getQ_propertyNoS()))
				sql+=" and b.propertyNo >= " + Common.sqlChar(getQ_propertyNoS()) ;
			if (!"".equals(getQ_propertyNoE()))
				sql+=" and b.propertyNo <= " + Common.sqlChar(getQ_propertyNoE()) ;
			if (!"".equals(getQ_serialNoS()))
				sql+=" and b.serialNo >= " + Common.sqlChar(getQ_serialNoS()) ;
			if (!"".equals(getQ_serialNoE()))
				sql+=" and b.serialNo <= " + Common.sqlChar(getQ_serialNoE()) ;
			if (!"".equals(getQ_propertyKind()))
				sql+=" and b.propertyKind = " + Common.sqlChar(getQ_propertyKind());
			if (!"".equals(getQ_fundType()))
				sql+=" and b.fundType = " + Common.sqlChar(getQ_fundType()) ;
			if (!"".equals(getQ_valuable()))
				sql+=" and b.valuable = " + Common.sqlChar(getQ_valuable()) ;
			if (!"".equals(getQ_oldKeepUnit()))
				sql+=" and b.oldKeepUnit = " + Common.sqlChar(getQ_oldKeepUnit()) ;
			if (!"".equals(getQ_oldKeeper()))
				sql+=" and b.oldKeeper = " + Common.sqlChar(getQ_oldKeeper()) ;
			if (!"".equals(getQ_oldUseUnit()))
				sql+=" and b.oldUseUnit = " + Common.sqlChar(getQ_oldUseUnit()) ;
			if (!"".equals(getQ_oldUserNo()))
				sql+=" and b.oldUserNo = " + Common.sqlChar(getQ_oldUserNo()) ;
			if (!"".equals(getQ_newKeepUnit()))
				sql+=" and b.newKeepUnit = " + Common.sqlChar(getQ_newKeepUnit()) ;
			if (!"".equals(getQ_newKeeper()))
				sql+=" and b.newKeeper = " + Common.sqlChar(getQ_newKeeper()) ;
			if (!"".equals(getQ_newUseUnit()))
				sql+=" and b.newUseUnit = " + Common.sqlChar(getQ_newUseUnit()) ;
			if (!"".equals(getQ_newUserNo()))
				sql+=" and b.newUserNo = " + Common.sqlChar(getQ_newUserNo()) ;
			if (!"".equals(getQ_oldUserNote()))
				sql+=" and b.oldusernote like " + Common.sqlChar("%"+getQ_oldUserNote()+"%") ;
			if (!"".equals(getQ_oldPlace1()))
				sql+=" and b.oldplace1 = " + Common.sqlChar(getQ_oldPlace1()) ;
			if (!"".equals(getQ_oldPlace()))
				sql+=" and b.oldplace like " + Common.sqlChar("%"+getQ_oldPlace()+"%") ;
			if (!"".equals(getQ_newUserNote()))
				sql+=" and b.newusernote like " + Common.sqlChar("%"+getQ_newUserNote()+"%") ;
			if (!"".equals(getQ_newPlace()))
				sql+=" and b.newplace like " + Common.sqlChar("%"+getQ_newPlace()+"%") ;
			if (!"".equals(getQ_newPlace1()))
				sql+=" and b.newplace1 = " + Common.sqlChar(getQ_newPlace1()) ;
			if (!"".equals(getQ_differenceKind()))
				sql+=" and b.differencekind = " + Common.sqlChar(getQ_differenceKind()) ;
			if (!"".equals(getQ_proofYear()))
				sql+=" and a.proofyear = " + Common.sqlChar(getQ_proofYear()) ;
			if (!"".equals(getQ_propertyName1()))
				sql+=" and b.propertyname1 like " + Common.sqlChar("%"+getQ_propertyName1()+"%") ;
			
		}
		if ("1".equals(this.getRoleid())) {
			// 1185 增加能查到單的異動人員是自己的移動單
			sql += " and ( a.editid = " + Common.sqlChar(this.getUserID()) +
						 " or b.oldkeeper = " + Common.sqlChar(this.getUserID()) +
						 " or b.olduserno = " + Common.sqlChar(this.getUserID()) +
						 " or b.newkeeper = " + Common.sqlChar(this.getUserID()) +
						 " or b.newuserno = " + Common.sqlChar(this.getUserID()) +
						 " )";			
		} else if ("2".equals(this.getRoleid())) {
			sql += " and ( a.editid = " + Common.sqlChar(this.getUserID()) +
						 " or b.oldkeepunit = " + Common.sqlChar(this.getUnitID()) +
						 " or b.olduseunit = " + Common.sqlChar(this.getUnitID()) +
						 " or b.newkeepunit = " + Common.sqlChar(this.getUnitID()) +
						 " or b.newuseunit = " + Common.sqlChar(this.getUnitID()) +
						 " or b.oldkeeper = " + Common.sqlChar(this.getUserID()) +
						 " or b.olduserno = " + Common.sqlChar(this.getUserID()) +
						 " or b.newkeeper = " + Common.sqlChar(this.getUserID()) +
						 " or b.newuserno = " + Common.sqlChar(this.getUserID()) +
						 " )";
		}
		
		sql += " order by proofyear desc,a.proofno desc ";
//System.out.println("UNTNE008F_queryAll==>"+sql);
			ResultSet rs = db.querySQL(sql,true);
			processCurrentPageAttribute(rs);
			if (getTotalRecord() > 0) {
				int count = getRecordStart();
				int end = getRecordEnd();
				do {
					if (count > end)
						break;
					String rowArray[]=new String[9];
					rowArray[0]=Common.get(rs.getString("enterOrg")); 
					rowArray[1]=Common.get(rs.getString("ownership")); 
					rowArray[2]=Common.get(rs.getString("ownershipName")); 
					rowArray[3]=Common.get(rs.getString("proofyear"))+"年"+Common.get(rs.getString("proofdoc"))+"字第"+Common.get(rs.getString("proofno"))+"號"; 
					rowArray[4]=Common.get(rs.getString("caseNo")); 
					rowArray[5]=Common.get(rs.getString("caseName")); 
					rowArray[6]=ul._transToROC_Year(rs.getString("writeDate")); 
					rowArray[7]=ul._transToROC_Year(rs.getString("moveDate")); 
					rowArray[8]=Common.get(rs.getString("verifyName")); 
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

//全部入帳
public void approveAll()throws Exception{
	UNTCH_Tools ul = new UNTCH_Tools();
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
				//setOldVerify("N");
				enterOrg = rowArray[0];
				//ownership= rowArray[1];
				caseNo   = rowArray[5];
				setMoveDate(rowArray[7]==null || "".equals(rowArray[7]) ? Datetime.getYYYYMMDD():rowArray[7]);
				setEditID(getUserID());
				setEditDate(Datetime.getYYYYMMDD());
				setEditTime(Datetime.getHHMMSS());	
				if (enterOrg.equals(getOrganID())) {
				    //更新非消耗品移動單
				    strSQL += "update UNTNE_MoveProof set "+
				                " verify = 'Y'," +
				                " moveDate = " + Common.sqlChar(ul._transToCE_Year(getMoveDate())) + "," +
				    			" editID = " + Common.sqlChar(getEditID()) + "," +
				    			" editDate = " + Common.sqlChar(ul._transToCE_Year(getEditDate())) + "," +
				    			" editTime = " + Common.sqlChar(getEditTime()) + 	                
				        		" where 1=1 " + 
				    			" and enterOrg = " + Common.sqlChar(enterOrg) +
				    			//" and ownership = " + Common.sqlChar(ownership) +
				    			" and caseNo = " + Common.sqlChar(caseNo) +
				    			":;:";								
					strSQL += checkVerify() + ":;:";

					//更新非消耗品移動單明細檔
					strSQL += "update UNTNE_MoveDetail set " +
								" verify='Y'," +
								" newMoveDate=" + Common.sqlChar(ul._transToCE_Year(getMoveDate())) + "," +
								" editID = " + Common.sqlChar(getEditID()) + "," +
				    			" editDate = " + Common.sqlChar(ul._transToCE_Year(getEditDate())) + "," +
				    			" editTime = " + Common.sqlChar(getEditTime()) + 
				        		" where 1=1 " + 
				    			" and enterOrg = " + Common.sqlChar(enterOrg) +
				    			//" and ownership = " + Common.sqlChar(ownership) +
				    			" and caseNo = " + Common.sqlChar(caseNo) +
								":;:";

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
				//setVerify("N");
				//setClosing("N");
				//setOldVerify("N");
				setStateUpdateSuccess();
				setErrorMsg("全部入帳完成");				
			} else {			   
	           //setVerifyError("Y");
	           setStateUpdateSuccess();
	           //setErrorMsg("入帳設定有錯，入帳年月必須大於月結年月！");		           
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
    	//if((Double.parseDouble(getMaxClosingYM(enterOrg))) < (Double.parseDouble(ul._transToCE_Year(getMoveDate()).substring(0,6)))){
    	//更新「非消耗品主檔－批號明細」
    	Database db = new Database();
    	String checkDetail="";
    	try {
    		String SQL = "select c.enterOrg, c.ownership, c.propertyNo, c.serialNo,c.differencekind, " +
    					" c.newMoveDate,c.newKeepUnit,c.newKeeper,c.newUseUnit,c.newUserNo,c.newPlace,c.newplace1, " +
    					" c.propertyname1" +
    					" from UNTNE_MoveDetail c where 1=1 " +
    					" and c.enterOrg = " + Common.sqlChar(enterOrg) + "\n" +
    					//" and c.ownership = " + Common.sqlChar(ownership) + "\n" +
    					" and c.caseNo = " + Common.sqlChar(caseNo) + "\n" +
    					"";
    		ResultSet rs = db.querySQL(SQL);
    		while (rs.next()){
    			checkDetail="Y";
    			sql += " update UNTNE_NonexpDetail set " +
    				" moveDate = " + Common.sqlChar(ul._transToCE_Year(getMoveDate())) + "," +
    				" keepUnit = " + Common.sqlChar(rs.getString("newKeepUnit")) + "," +
    				" keeper = " + Common.sqlChar(rs.getString("newKeeper")) + "," +
    				" useUnit = " + Common.sqlChar(rs.getString("newUseUnit")) + "," +
    				" userNo = " + Common.sqlChar(rs.getString("newUserNo")) + "," +
    				" place = " + Common.sqlChar(rs.getString("newPlace")) + "," +
    				" place1 = " + Common.sqlChar(rs.getString("newplace1")) + "," +
    				" propertyname1 = " + Common.sqlChar(rs.getString("propertyname1")) + 
    				" where 1=1 " +
    				" and enterOrg = " + Common.sqlChar(rs.getString("enterOrg")) + 
    				" and ownership = " + Common.sqlChar(rs.getString("ownership")) +
    				" and propertyNo = " + Common.sqlChar(rs.getString("propertyNo")) +
    				" and serialNo = " + Common.sqlChar(rs.getString("serialNo")) +
    				" and differencekind = " + Common.sqlChar(rs.getString("differencekind")) +
    				":;:";
    		}
            if(!checkDetail.equals("Y")){
                setVerifyError("Y");
                setErrorMsg("該筆移動單之明細資料標籤要有資料才能入帳！");
            }
    	} catch (Exception e) {
    		e.printStackTrace();
    	} finally {
    		db.closeAll();
    	}
	//}else{
    //	setVerifyError("Y");   
   //     setStateUpdateError();
    //    setErrorMsg("入帳設定有錯，移動日期之年月必須大於最大的月結年月！");
    //}
	return sql;
}

//回復入帳設定
public void approveRe()throws Exception{
	Database db = new Database();
	String enterOrgQuery = "";
	String differenceKindNoQuery;
	String caseNoQuery = "";
	String propertyNoQuery = "";
	String serialNoQuery = "";
	int count = 0;
	try {
		String[] execSQLArray;
		String strSQL = "";
		String sql ="select a.caseNo, b.enterOrg, b.ownership, b.propertyNo, b.serialNo, b.lotNo,b.differencekind, " +
					" a.editDate, a.editTime, a.moveDate, " +
					" b.oldMoveDate, b.oldKeepUnit, b.oldKeeper, b.oldUseUnit, b.oldUserNo, b.oldPlace, " +
					" b.propertyname1" +
					" from untne_moveProof a, untne_moveDetail b " +
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
			caseNoQuery = rs.getString("caseNo");
			propertyNoQuery = rs.getString("propertyNo");
			serialNoQuery = rs.getString("serialNo");
			differenceKindNoQuery = rs.getString("differencekind");
			
			if(verify.equals("Y")){
				//依據該移動單「入帳機關enterOrg＋權屬ownership＋電腦單號caseNo」設定
				if(count==1){
					strSQL += "update UNTNE_MOVEPROOF set verify ='N', movedate='' " +
							" where enterorg=" + Common.sqlChar(enterOrgQuery) +
							" and caseno=" + Common.sqlChar(caseNoQuery) +
							":;:";
					strSQL += "update UNTNE_MOVEDETAIL set verify ='N', newmovedate='' " +
							" where enterorg=" + Common.sqlChar(enterOrgQuery) +
							" and caseno=" + Common.sqlChar(caseNoQuery) +
							":;:";				
				}

				//依據移動單明細「入帳機關enterOrg＋權屬ownership＋財產編號propertyNo＋財產分號serialNo」設定
				strSQL +="update UNTNE_NONEXPDETAIL set " +
						" movedate = " + Common.sqlChar(rs.getString("oldMoveDate")) + "," +
						" keepunit = " + Common.sqlChar(rs.getString("oldKeepUnit")) + "," +
						" keeper = " + Common.sqlChar(rs.getString("oldKeeper")) + "," +
						" useunit = " + Common.sqlChar(rs.getString("oldUseUnit")) + "," +
						" userno = " + Common.sqlChar(rs.getString("oldUserNo")) + "," +
						" place = " + Common.sqlChar(rs.getString("oldPlace")) + "," +
						" propertyname1 = " + Common.sqlChar(rs.getString("propertyname1")) +
						" where enterorg=" + Common.sqlChar(enterOrgQuery) +
						" and propertyno=" + Common.sqlChar(propertyNoQuery) +
						" and serialno=" + Common.sqlChar(serialNoQuery) +
						" and differencekind=" + Common.sqlChar(differenceKindNoQuery) +
						":;:";
			}else{
				setVerifyError("Y");
				if(verify.equals("N")){
					setErrorMsg("尚未入帳，請直接修改資料即可！");
				}
			}
			if ("Y".equals(getVerifyError())) {
			    break;
			}
		}
		
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
		this.logger.error("回復移動單發生異常", e);
		this.setErrorMsg("回復移動單發生異常");
		this.setStateUpdateError();
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


