/*
*<br>程式目的：非消耗品廢品處理作業－處理單資料
*<br>程式代號：untne020f
*<br>程式日期：0941212
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
import util.*;

public class UNTNE020F extends UNTNE020Q{


String enterOrg;
String enterOrgName;
String ownership;
String caseNo1;
String caseName;
String writeDate;
String writeUnit;
String proofDoc;
String proofNo;
String manageNo;
String dealDate;
String reduceDeal;
String realizeValue;
String shiftOrg;
String shiftOrgName;
String verify;
String notes;
String checkDealDate;
String checkVerify;
String checkEnterOrg;
String differenceKind;

public String getCheckVerify(){ return checkGet(checkVerify); }
public void setCheckVerify(String s){ checkVerify=checkSet(s); }
public String getCheckDealDate(){ return checkGet(checkDealDate); }
public void setCheckDealDate(String s){ checkDealDate=checkSet(s); }
public String getEnterOrg(){ return checkGet(enterOrg); }
public void setEnterOrg(String s){ enterOrg=checkSet(s); }
public String getEnterOrgName(){ return checkGet(enterOrgName); }
public void setEnterOrgName(String s){ enterOrgName=checkSet(s); }
public String getOwnership(){ return checkGet(ownership); }
public void setOwnership(String s){ ownership=checkSet(s); }
public String getCaseNo1(){ return checkGet(caseNo1); }
public void setCaseNo1(String s){ caseNo1=checkSet(s); }
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
public String getDealDate(){ return checkGet(dealDate); }
public void setDealDate(String s){ dealDate=checkSet(s); }
public String getReduceDeal(){ return checkGet(reduceDeal); }
public void setReduceDeal(String s){ reduceDeal=checkSet(s); }
public String getRealizeValue(){ return checkGet(realizeValue); }
public void setRealizeValue(String s){ realizeValue=checkSet(s); }
public String getShiftOrg(){ return checkGet(shiftOrg); }
public void setShiftOrg(String s){ shiftOrg=checkSet(s); }
public String getShiftOrgName(){ return checkGet(shiftOrgName); }
public void setShiftOrgName(String s){ shiftOrgName=checkSet(s); }
public String getDifferenceKind() {return checkGet(this.differenceKind);}
public void setDifferenceKind(String s) {differenceKind = checkSet(s);}
String proofYear;
public String getProofYear(){return checkGet(proofYear);}
public void setProofYear(String s) {proofYear = checkSet(s);}

public String getVerify(){ return checkGet(verify); }
public void setVerify(String s){ verify=checkSet(s); }

public String getNotes(){ return checkGet(notes); }
public void setNotes(String s){ notes=checkSet(s); }
public String getCheckEnterOrg(){ return checkGet(checkEnterOrg); }
public void setCheckEnterOrg(String s){ checkEnterOrg=checkSet(s); }

String caseNo;
String propertyNo;
String serialNo;
String oldVerify;
String verifyError="";
String checkReduceDate;

public String getCheckReduceDate(){ return checkGet(checkReduceDate); }
public void setCheckReduceDate(String s){ checkReduceDate=checkSet(s); }
public String getVerifyError(){ return checkGet(verifyError); }
public void setVerifyError(String s){ verifyError=checkSet(s); }
public String getCaseNo(){ return checkGet(caseNo); }
public void setCaseNo(String s){ caseNo=checkSet(s); }
public String getPropertyNo(){ return checkGet(propertyNo); }
public void setPropertyNo(String s){ propertyNo=checkSet(s); }
public String getSerialNo(){ return checkGet(serialNo); }
public void setSerialNo(String s){ serialNo=checkSet(s); }
public String getOldVerify(){ return checkGet(oldVerify); }
public void setOldVerify(String s){ oldVerify=checkSet(s); }

//傳回執行insert前之檢查sql
protected String[][] getInsertCheckSQL(){
	//取得電腦單號
	Database db = new Database();
	ResultSet rs;	
	String sql="select cast(max(substring(caseNo1,4,7))+1 AS varchar) as caseNo1 from UNTNE_DealProof " +
		" where enterOrg = " + Common.sqlChar(enterOrg) +
		" and substring(caseNo1,1,3) = " + Common.sqlChar(Datetime.getYYY()) + 
		"";		
	try {		
		rs = db.querySQL(sql);
		if (rs.next()){
			if (rs.getString("caseNo1")==null)
				setCaseNo1(Datetime.getYYYMMDD().substring(0,3)+"0000001");
			else
				setCaseNo1(Datetime.getYYYMMDD().substring(0,3)+ Common.formatFrontZero(rs.getString("caseNo1"), 7));
		} else {
			setCaseNo1(Datetime.getYYYMMDD().substring(0,3)+"0000001");
		}
	} catch (Exception e) {
		e.printStackTrace();
	} finally {
		db.closeAll();
	}
	//取得處理單編號－號
	setProofNo(MaxClosingYM.getMaxProofNo("UNTNE_DealProof",enterOrg,ownership,this.getProofYear()));
	//===================
	String[][] checkSQLArray = new String[1][4];
 	checkSQLArray[0][0]=" select count(*) as checkResult from UNTNE_DealProof where 1=1 " + 
		" and enterOrg = " + Common.sqlChar(enterOrg) + 
		" and caseNo1 = " + Common.sqlChar(caseNo1) + 
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
	execSQLArray[0]=" insert into UNTNE_DealProof(" +
			" enterOrg,"+
			" caseNo1,"+
			" caseName,"+
			" writeDate,"+
			" writeUnit,"+
			" proofDoc,"+
			" proofNo,"+
			" manageNo,"+
			" dealDate,"+
			" reduceDeal,"+
			" realizeValue,"+
			" shiftOrg,"+
			" verify,"+
			" notes,"+
			" editID,"+
			" editDate,"+
			" editTime,"+
			" differencekind,"+
			" proofyear"+
		" )Values(" +
			Common.sqlChar(this.getEnterOrg()) + "," +
			Common.sqlChar(this.getCaseNo1()) + "," +
			Common.sqlChar(this.getCaseName()) + "," +
			Common.sqlChar(ul._transToCE_Year(this.getWriteDate())) + "," +
			Common.sqlChar(this.getWriteUnit()) + "," +
			Common.sqlChar(this.getProofDoc()) + "," +
			Common.sqlChar(this.getProofNo()) + "," +
			Common.sqlChar(this.getManageNo()) + "," +
			Common.sqlChar(ul._transToCE_Year(this.getDealDate())) + "," +
			Common.sqlChar(this.getReduceDeal()) + "," +
			Common.sqlChar(realizeValue) + "," +
			Common.sqlChar(this.getShiftOrg()) + "," +
			Common.sqlChar(this.getVerify()) + "," +
			Common.sqlChar(this.getNotes()) + "," +
			Common.sqlChar(getEditID()) + "," +
			Common.sqlChar(ul._transToCE_Year(getEditDate())) + "," +
			Common.sqlChar(getEditTime()) + "," +
	        Common.sqlChar(getDifferenceKind()) + "," +
	        Common.sqlChar(getProofYear()) + ")" ;
//	System.out.println("-----------"+execSQLArray[0]);
	return execSQLArray;
}

protected void getUpdateCheck(){
    
}
//傳回執行update前之檢查sql
protected String[][] getUpdateCheckSQL(){
	if ("Y".equals(getVerify())) {
		String[][] checkSQLArray = new String[2][4];
		//該筆處理單之明細資料標籤要有資料才能做已審核設定
	 	checkSQLArray[0][0]=" select count(*) as checkResult from UNTNE_DealDetail where 1=1 " + 
							" and enterOrg = " + Common.sqlChar(enterOrg) + 
							" and caseNo1 = " + Common.sqlChar(caseNo1) + 
							"";
		checkSQLArray[0][1]="<=";
		checkSQLArray[0][2]="0";
		checkSQLArray[0][3]="該筆處理單之明細資料標籤要有資料才能做此審核設定！";
		
	 	checkSQLArray[1][0]=" select max(reduceDate) as checkResult from UNTNE_DealDetail where 1=1 " + 
	 						" and enterOrg = " + Common.sqlChar(enterOrg) +
	 						" and caseNo1 = " + Common.sqlChar(caseNo1) +
	 						"";
	 	checkSQLArray[1][1]=">";
	 	checkSQLArray[1][2]=dealDate;
	 	checkSQLArray[1][3]="審核設定有錯，「廢品處理日期」不可小於該筆處理單之處理明細資料的「最大減損日期」！";

	 	return checkSQLArray;	
	} else { return super.getUpdateCheckSQL(); }
}

//檢查update時，proofyear是否有改變
private boolean checkProofyearChanged(String enterorg, String caseno, String newproofyear){
	boolean isChanged = false;
	String oldproofyear ="";
	ResultSet rs;
	Database db = new Database();
	try{
		String sql = "select proofyear from UNTNE_DEALPROOF where enterorg=" + Common.sqlChar(enterorg) +
								" and caseno1=" + Common.sqlChar(caseno);
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
    String strSQLArray = ""; 
    String[] execSQLArray = null;
    String className = this.getClass().getName();
    
	setEditDate(Datetime.getYYYMMDD());
	setEditTime(Datetime.getHHMMSS());	
    try{
		strSQLArray = " update UNTNE_DEALPROOF set " +
						" casename = " + Common.sqlChar(this.getCaseName()) + "," +
						" writedate = " + Common.sqlChar(ul._transToCE_Year(this.getWriteDate())) + "," +
						" writeunit = " + Common.sqlChar(this.getWriteUnit()) + "," +
						" proofdoc = " + Common.sqlChar(this.getProofDoc()) + "," +
						" proofno = " + Common.sqlChar(this.getProofNo()) + "," +
						" manageno = " + Common.sqlChar(this.getManageNo()) + "," +
						" dealdate = " + Common.sqlChar(ul._transToCE_Year(this.getDealDate())) + "," +
						" reducedeal = " + Common.sqlChar(this.getReduceDeal()) + "," +
						" realizevalue = " + Common.getInteger(this.getRealizeValue()) + "," +
						" shiftorg = " + Common.sqlChar(this.getShiftOrg()) + "," +
						" verify = " + Common.sqlChar(this.getVerify()) + "," +
						" notes = " + Common.sqlChar(this.getNotes()) + "," +
						" editid = " + Common.sqlChar(getEditID()) + "," +
						" editdate = " + Common.sqlChar(ul._transToCE_Year(getEditDate())) + "," +
						" edittime = " + Common.sqlChar(getEditTime()) + "," +
						" differencekind = " + Common.sqlChar(getDifferenceKind()) + "," +
						" proofyear = " + Common.sqlChar(getProofYear()) +  
					" where 1=1 " + 
						" and enterorg = " + Common.sqlChar(enterOrg) +
						" and caseno1 = " + Common.sqlChar(caseNo1) +
						":;:";		
		//問題單1290補充: update時更改編號-年，重新取得編號-號
		if(this.checkProofyearChanged(getEnterOrg(), getCaseNo1(), getProofYear())){
			String newproofno = MaxClosingYM.getMaxProofNo("UNTNE_DEALPROOF",enterOrg,ownership,this.getProofYear());
			strSQLArray += " update UNTNE_DEALPROOF set proofno=" + Common.sqlChar(newproofno) +
										" where 1=1 " +
										" and enterorg = " + Common.sqlChar(enterOrg) +
										" and caseno1 = " + Common.sqlChar(caseNo1) +
										":;:";
		}
	    strSQLArray += " update UNTNE_DEALDETAIL set " +
						" dealdate = " + Common.sqlChar(ul._transToCE_Year(dealDate)) + 
					" where 1=1 " + 
						" and enterorg = " + Common.sqlChar(enterOrg) +
						" and caseno1 = " + Common.sqlChar(caseNo1) +
						":;:";
	    if(getVerify().equals("Y") && !"X".equals(getVerifyError())){   // VerifyError=X為由物品異動單確認作業(UNTAC007F)的更新註記
	        strSQLArray += checkVerify();
	    }
		if (!"Y".equals(getVerifyError())) {
			execSQLArray = strSQLArray.split(":;:");
			db.excuteSQL_NoChange(execSQLArray);
			setStateUpdateSuccess();
			
			//使用者操作記錄
			Common.insertUpdateRecordSql("", this.getEnterOrg(), this.getUserID(), className.substring(className.lastIndexOf('.') + 1), this.getObjPath().replaceAll("&gt;", ">") + " > > 修改" + this.getCaseNo1());
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
    //return execSQLArray;
}


//傳回delete sql
protected String[] getDeleteSQL(){
	String[] execSQLArray = new String[2];
	execSQLArray[0]=" delete UNTNE_DealProof where 1=1 " +
			" and enterOrg = " + Common.sqlChar(enterOrg) +
			" and caseNo1 = " + Common.sqlChar(caseNo1) +
			"";
	execSQLArray[1]=" delete UNTNE_DealDetail where 1=1 " +
			" and enterOrg = " + Common.sqlChar(enterOrg) +
			" and caseNo1 = " + Common.sqlChar(caseNo1) +
			"";
	return execSQLArray;
}


/**
 * <br>
 * <br>目的：依主鍵查詢單一資料
 * <br>參數：無
 * <br>傳回：傳回本物件
*/

public UNTNE020F  queryOne() throws Exception{
	UNTCH_Tools ul = new UNTCH_Tools();
	Database db = new Database();
	UNTNE020F obj = this;
	try {
		String sql=" select a.enterOrg, b.organSName as enterOrgName, a.caseNo1, a.caseName, a.writeDate, a.writeUnit, a.proofDoc, a.proofNo, a.manageNo, a.dealDate, a.reduceDeal,a.proofYear, a.differencekind, " +
					" (SELECT sum(realizevalue1) FROM  UNTNE_DEALDETAIL  WHERE a.enterorg = enterorg AND a.caseno1 = caseno1 ) as realizeValue, " +
					" a.shiftOrg, (select c.organSName from SYSCA_Organ c where 1=1 and a.shiftOrg = c.organID) as shiftOrgName, " +
					" a.verify, a.notes, a.editID, a.editDate, a.editTime  "+
					" from UNTNE_DealProof a, SYSCA_Organ b where 1=1 " +
					" and a.enterOrg=b.organID " +
					" and a.enterOrg = " + Common.sqlChar(enterOrg) +
					" and a.caseNo1 = " + Common.sqlChar(caseNo1) +
					"";
		ResultSet rs = db.querySQL(sql);
		if (rs.next()){
			obj.setEnterOrg(rs.getString("enterOrg"));
			obj.setEnterOrgName(rs.getString("enterOrgName"));
			obj.setCaseNo1(rs.getString("caseNo1"));
			obj.setCaseName(rs.getString("caseName"));
			obj.setWriteDate(ul._transToROC_Year(rs.getString("writeDate")));
			obj.setWriteUnit(rs.getString("writeUnit"));
			obj.setProofDoc(rs.getString("proofDoc"));
			obj.setProofNo(rs.getString("proofNo"));
			obj.setManageNo(rs.getString("manageNo"));
			obj.setDealDate(ul._transToROC_Year(rs.getString("dealDate")));
			obj.setReduceDeal(rs.getString("reduceDeal"));
			obj.setRealizeValue(rs.getString("realizeValue"));
			obj.setShiftOrg(rs.getString("shiftOrg"));
			obj.setShiftOrgName(rs.getString("shiftOrgName"));
			obj.setVerify(rs.getString("verify"));
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
	Map<String,String> differencekindMap = TCGHCommon.getSysca_Code("DFK"); 					//財產區分別名稱
	int counter=0;
	try {
		String sql= "select * from (" +
					" select distinct a.enterOrg,a.differenceKind,cast(a.proofyear as int) as proofyear,a.proofdoc,a.proofno," + //decode(a.ownership,1,'市有','2','國有','') ownershipName, " +
					" a.caseNo1, a.caseName, a.writeDate, a.dealDate, " +
					" a.reduceDeal, (select c.codeName from  SYSCA_Code c where c.codeKindID='RDL'  and a.reduceDeal = c.codeID) as reduceDealName, " +
					" (SELECT sum(realizevalue1) FROM  UNTNE_DEALDETAIL  WHERE a.enterorg = enterorg AND a.caseno1 = caseno1 ) as realizeValue, " +
					" a.shiftOrg, (select b.organSName from SYSCA_Organ b where a.shiftOrg=b.organID ) as shiftOrgName, (case a.verify when 'Y' then '是' when 'N' then '否' else '' end) verifyName " +
					" from UNTNE_DEALPROOF a"+
					" left join UNTNE_DealDetail d on a.enterOrg=d.enterOrg  and a.caseNo1=d.caseNo1"+
					" where 1=1 ";
		//以下三個條件為新增移動單後查詢
		if (!"".equals(getI_enterOrg())) {
			sql += " and a.enterorg = " + Common.sqlChar(getI_enterOrg());
		}
		if (!"".equals(getI_caseNo1())) {
			sql += " and a.caseno1 = " + Common.sqlChar(getI_caseNo1());	
		}
		if (!"".equals(getI_differenceKind())) {
			sql += " and a.differencekind = " + Common.sqlChar(getI_differenceKind());	
		}
		//以上三個條件為新增移動單後查詢
		if ("dealDetail".equals(getQuerySelect()) || "".equals(getQuerySelect())) {
			sql+=" and a.enterOrg = " + Common.sqlChar(getEnterOrg()) ;
			sql+=" and a.differencekind = " + Common.sqlChar(getDifferenceKind()) ;
			sql+=" and a.caseNo1=" + Common.sqlChar(getCaseNo1());
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
			
			if (!"".equals(getQ_caseNo1S()))
				sql+=" and a.caseNo1 >= " + Common.sqlChar(Common.formatRearString(getQ_caseNo1S(),10,'0'));		
			if (!"".equals(getQ_caseNo1E()))
				sql+=" and a.caseNo1 <= " + Common.sqlChar(Common.formatRearString(getQ_caseNo1E(),10, '9'));
			if (!"".equals(getQ_dealDateS()))
				sql+=" and a.dealDate >= " + Common.sqlChar(ul._transToCE_Year(getQ_dealDateS()));
			if (!"".equals(getQ_dealDateE()))
				sql+=" and a.dealDate <= " + Common.sqlChar(ul._transToCE_Year(getQ_dealDateE()));
			if (!"".equals(getQ_verify()))
				sql+=" and a.verify  = " + Common.sqlChar(getQ_verify()) ;
			if (!"".equals(getQ_caseName()))
				sql+=" and a.caseName like " + Common.sqlChar(getQ_caseName()+"%") ;   	    
			if (!"".equals(getQ_writeDateS()))
				sql+=" and a.writeDate >= " + Common.sqlChar(ul._transToCE_Year(getQ_writeDateS()));		
			if (!"".equals(getQ_writeDateE()))
				sql+=" and a.writeDate <= " + Common.sqlChar(ul._transToCE_Year(getQ_writeDateE()));   	    
			if (!"".equals(getQ_proofDoc()))
				sql+=" and a.proofDoc like '%" + getQ_proofDoc() + "%'" ;
			if (!"".equals(getQ_proofNoS())) 
				sql+=" and a.proofNo >= " + Common.sqlChar(getQ_proofNoS());		
			if (!"".equals(getQ_proofNoE())) 
				sql+=" and a.proofNo <= " + Common.sqlChar(getQ_proofNoE());		 
			if (!"".equals(getQ_reduceDeal()))
				sql+=" and a.reduceDeal = " + Common.sqlChar(getQ_reduceDeal()) ;
			if (!"".equals(getQ_shiftOrg()))
				sql+=" and a.shiftOrg = " + Common.sqlChar(getQ_shiftOrg()) ;
			if (!"".equals(getQ_propertyNoS()))
				sql+=" and d.propertyNo >= " + Common.sqlChar(getQ_propertyNoS());		
			if (!"".equals(getQ_propertyNoE()))
				sql+=" and d.propertyNo <= " + Common.sqlChar(getQ_propertyNoE());						
			if (!"".equals(getQ_serialNoS()))
				sql+=" and d.serialNo >= " + Common.sqlChar(getQ_serialNoS());		
			if (!"".equals(getQ_serialNoE()))
				sql+=" and d.serialNo <= " + Common.sqlChar(getQ_serialNoE());			
			if (!"".equals(getQ_propertyKind()))
				sql+=" and d.propertyKind = " + Common.sqlChar(getQ_propertyKind()) ;
			if (!"".equals(getQ_fundType()))
				sql+=" and d.fundType = " + Common.sqlChar(getQ_fundType()) ;
			if (!"".equals(getQ_valuable()))
				sql+=" and d.valuable = " + Common.sqlChar(getQ_valuable()) ;
			if (!"".equals(getQ_differenceKind()))
				sql+=" and a.differencekind = " + Common.sqlChar(getQ_differenceKind()) ;
			if (!"".equals(getQ_proofYear()))
				sql+=" and a.proofyear = " + Common.sqlChar(getQ_proofYear()) ;
		}
		sql+=" ) a order by cast(a.proofyear as int) desc,a.proofno desc ";
		//System.out.println("queryAll "+sql);
		ResultSet rs = db.querySQL(sql.toString(),true);
		processCurrentPageAttribute(rs);
		if (getTotalRecord() > 0) {
			int count = getRecordStart();
			int end = getRecordEnd();
			do {
				if (count > end)
					break;
				String rowArray[]=new String[13];
				rowArray[0]=Common.get(rs.getString("enterOrg")); 
				rowArray[1]=differencekindMap.get(rs.getString("differencekind"));
				rowArray[2]=Common.get(rs.getString("caseNo1")); 
				rowArray[3]=Common.get(rs.getString("proofyear"))+"年"+Common.get(rs.getString("proofdoc"))+"字第"+Common.get(rs.getString("proofno"))+"號";  
				rowArray[4]=ul._transToROC_Year(rs.getString("writeDate")); 
				rowArray[5]=ul._transToROC_Year(rs.getString("dealDate")); 
				rowArray[6]=Common.get(rs.getString("reduceDealName")); 
				rowArray[7]=Common.get(rs.getString("realizeValue")); 
				rowArray[8]=Common.get(rs.getString("shiftOrgName")); 
				rowArray[9]=Common.get(rs.getString("verifyName")); 
				rowArray[10]=Common.get(rs.getString("reduceDeal"));
				rowArray[11]=Common.get(rs.getString("shiftOrg"));
				rowArray[12]=Common.get(rs.getString("differenceKind"));
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

//全部審核
public void approveAll()throws Exception{
	UNTCH_Tools ul = new UNTCH_Tools();
    //UNTNE020F obj = this;
	Database db = new Database();
	//ResultSet rs = null;
	//String sql = "";
	String strSQL = "";
	String[] execSQLArray;
  int counter = 0,i = 0;	
	try {    
		String rowArray[]=new String[13];
		java.util.Iterator it= queryAll().iterator();
		while(it.hasNext()){
		    counter++;
			rowArray= (String[])it.next();
			if(rowArray[9].equals("否")){
			    i++;
				setVerify("Y");
				setOldVerify("N");
				enterOrg = rowArray[0];
				caseNo1 = rowArray[2];
				setDealDate(rowArray[5]==null || "".equals(rowArray[5]) ? Datetime.getYYYMMDD():rowArray[5]);
				reduceDeal = rowArray[10];
				realizeValue = rowArray[7];
				shiftOrg = rowArray[11];
				setEditID(getUserID());
				setEditDate(Datetime.getYYYYMMDD());
				setEditTime(Datetime.getHHMMSS());		
				if(enterOrg.equals(checkEnterOrg)){
				    strSQL += "update UNTNE_DealProof set "+
				    		" verify = 'Y'," +
				    		" dealDate = " + Common.sqlChar(ul._transToCE_Year(getDealDate())) + "," +
							" editID = " + Common.sqlChar(getEditID()) + "," +
							" editDate = " + Common.sqlChar(ul._transToCE_Year(getEditDate())) + "," +
							" editTime = " + Common.sqlChar(getEditTime()) + 	                
							" where 1=1 " + 
							" and enterOrg = " + Common.sqlChar(enterOrg) +
							" and caseNo1 = " + Common.sqlChar(caseNo1) +
							":;:";				
				    strSQL += checkVerify();
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
				setErrorMsg("全部審核完成");				
			} else {			   
	           //setVerifyError("Y");
	           setStateUpdateSuccess();
	           //setErrorMsg("審核設定有錯，「廢品處理日期」不可小於該筆處理單之處理明細資料的「最大減損日期」！");		           
			}
		}else{                                   
			setErrorMsg("全部審核完成");
			setStateUpdateSuccess();    
			queryOne();                 
		}                               			
	} catch (Exception e) {
		e.printStackTrace();
	} finally {
		db.closeAll();
	}		
}

//取得最大的減損日期
protected String getMaxReduceDate(){
    UNTNE020F obj = this;
	Database db = new Database();
	ResultSet rs;	
	String maxReduceDate ="" ;
	String sql="select max(reduceDate) as reduceDate from UNTNE_DealDetail " +
		" where 1=1" +
		" and enterOrg = " + Common.sqlChar(enterOrg) +
		" and differencekind = " + Common.sqlChar(differenceKind) +
		" and caseNo1 = " + Common.sqlChar(caseNo1) +
		"";		
	try {		
		rs = db.querySQL(sql);
		if (rs.next()){
		    if(rs.getString("reduceDate")==null){
		        obj.setCheckReduceDate("N");
		        maxReduceDate = "99999999";
		    }else{
		        maxReduceDate = rs.getString("reduceDate");
		        obj.setCheckReduceDate("");
		    }
		}
	} catch (Exception e) {
		e.printStackTrace();
	} finally {
		db.closeAll();
	}
	return Datetime.changeTaiwanYYMMDD(maxReduceDate, "1");
}

//審核設定
protected String checkVerify(){
	UNTCH_Tools ul = new UNTCH_Tools();
	Database db = new Database();
	ResultSet rs = null;
	String sql = "";
	String strSQL = "";
	String checkDetail="";
	//String[] execSQLArray;
	//將「審核」由「N:未核章」改為「Y:已核章」
	try{
	    if(Integer.parseInt(getMaxReduceDate()) < Integer.parseInt(ul._transToCE_Year(getDealDate()))){
	        
	        //找出enterOrg, ownership, propertyNo, serialNo, caseNo等Key 值去更新UNTNE_ReduceDetail
	        sql="select b.enterorg, b.ownership, b.caseno, b.propertyno, b.serialno, b.differencekind, " +
	        		" isnull(isnull(b.realizevalue1 ,a.realizevalue) ,0) as realizeValue " +
	        		" from UNTNE_DEALPROOF a, UNTNE_DEALDETAIL b" +
	            " where 1 = 1 and a.enterorg=b.enterorg " +
	            " and a.differencekind=b.differencekind " +
	            " and a.caseno1=b.caseno1 " +
	            " and b.enterorg = " + Common.sqlChar(enterOrg) +
	            " and b.differencekind = " + Common.sqlChar(differenceKind) +
	            " and b.caseno1 = " + Common.sqlChar(caseNo1) ;
	        rs = db.querySQL_NoChange(sql);
	        while (rs.next()) {
	        	 checkDetail="Y";
	        	strSQL += "update UNTNE_DEALDETAIL set verify='Y'," +
    					  " dealdate=" + Common.sqlChar(ul._transToCE_Year(getDealDate())) +
    					  " where 1=1 " +
    					  " and enterorg = " + Common.sqlChar(enterOrg) +
    					  " and differencekind = " + Common.sqlChar(differenceKind) +
    					  " and caseno1 = " + Common.sqlChar(caseNo1) +
    					  ":;:";
	            strSQL += "update UNTNE_REDUCEDETAIL set " +
	            			" dealcaseno= " + Common.sqlChar(caseNo1) + "," +
							" dealdate = " + Common.sqlChar(ul._transToCE_Year(getDealDate())) + "," +
							" reducedeal = "  + Common.sqlChar(reduceDeal) + "," +
							" realizevalue = "  + Common.sqlChar(rs.getString("realizeValue")) + "," +
							" shiftorg = "  + Common.sqlChar(shiftOrg) +
						" where 1=1 " +
							" and enterorg = " + Common.sqlChar(enterOrg) +
							" and differencekind = " + Common.sqlChar(differenceKind) +
							" and caseno = " + Common.sqlChar(rs.getString("caseNo")) +
							" and propertyno = " + Common.sqlChar(rs.getString("propertyNo")) +
							" and serialno = " + Common.sqlChar(rs.getString("serialNo")) +
							":;:";
	        }
	        rs.close();	
	    }else{
	        setStateUpdateError();
	        setVerifyError("Y");
	        if(!getCheckReduceDate().equals("N")){
	            setErrorMsg("審核設定有錯，「廢品處理日期」不可小於該筆處理單之處理明細資料的「最大減損日期」！");
	        }
	    }
	    if(!checkDetail.equals("Y") && (getVerify().equals("Y") )){
            setVerifyError("Y");
            setErrorMsg("該筆處理單之明細資料標籤要有資料才能做此審核設定！");
        }
	} catch (Exception e) {
		e.printStackTrace();
	}
	return strSQL;
}

//回復審核設定
public void approveRe()throws Exception{	
	Database db = new Database();
	String enterOrgQuery = "";
	String ownershipQuery = "";
	String caseNoQuery = "";
	String caseNo1Query = "";
	String propertyNoQuery = "";
	String serialNoQuery = "";
	String differenceKindQuery="";
	int count = 0;
	try {    
		String[] execSQLArray;
		String strSQL = "";
		String sql ="select a.caseNo1, b.caseNo, b.enterOrg, b.ownership, b.propertyNo, b.serialNo,b.differenceKind " +
					" from untne_DealProof a, untne_DealDetail b " +
					" where 1=1 " +
					" and a.enterOrg = b.enterOrg and a.differencekind = b.differencekind and a.caseNo1 = b.caseNo1" +
	    			" and a.enterOrg = " + Common.sqlChar(enterOrg) +
	    			" and a.differencekind = " + Common.sqlChar(differenceKind) +
	    			" and a.caseNo1 = " + Common.sqlChar(caseNo1) +
					" order by b.enterOrg, b.ownership, b.caseNo1, b.propertyNo, b.serialNo" +
					"" ;
		ResultSet rs = db.querySQL(sql);
		while (rs.next()){
			count++;
			enterOrgQuery = rs.getString("enterOrg");
			ownershipQuery = rs.getString("ownership");
			caseNo1Query = rs.getString("caseNo1");
			caseNoQuery = rs.getString("caseNo");
			propertyNoQuery = rs.getString("propertyNo");
			serialNoQuery = rs.getString("serialNo");
			differenceKindQuery = rs.getString("differenceKind");
			//------------------------------------------------------------------------------------------------
			if(verify.equals("Y")){
				//依據該處理單「入帳機關enterOrg＋權屬ownership＋電腦單號caseNo」設定
				if(count==1){
					strSQL += "update UNTNE_DEALPROOF set verify ='N',dealDate = null, realizeValue = null" +
							" where enterOrg=" + Common.sqlChar(enterOrgQuery) +
							" and caseNo1=" + Common.sqlChar(caseNo1Query) +
							":;:";				
					strSQL += "update UNTNE_DEALDETAIL set verify ='N',dealDate = null " +
							" where enterOrg=" + Common.sqlChar(enterOrgQuery) +
							" and caseNo1=" + Common.sqlChar(caseNo1Query) +
							":;:";				
				}
				
				//依據處理單明細「入帳機關enterOrg＋權屬ownership＋減損單電腦單號caseNo＋財產編號propertyNo＋財產分號serialNo」設定
				strSQL +="update untne_ReduceDetail set " +
						" dealCaseNo = null, " +
						" dealDate = null, " +
						" reduceDeal = null, " +
						" realizeValue = null, " +
						" shiftOrg = null " +
						" where enterOrg=" + Common.sqlChar(enterOrgQuery) +
						" and caseNo=" + Common.sqlChar(caseNoQuery) +
						" and propertyNo=" + Common.sqlChar(propertyNoQuery) +
						" and serialNo=" + Common.sqlChar(serialNoQuery) +
						" and differenceKind=" + Common.sqlChar(differenceKindQuery) +
						":;:";
				//----------------------------------------
			}else{
				setVerifyError("Y");
				if(verify.equals("N")){
					setErrorMsg("尚未審核入帳，請直接修改資料即可！");
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
			setErrorMsg("回復審核完成");	
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

	public boolean checkDataCountIsZero(String enterOrg, String caseNo){
		boolean result = false;
		
		String sql = "select" + 
						" (select case COUNT(*) when 0 then 0 else 1 end from UNTNE_DealDetail b where a.enterOrg=b.enterOrg  and a.caseNo1=b.caseNo1 )" +
					" as countall from UNTNE_DealProof a" +
					" where 1=1" +
						" and a.enterorg = '" + getEnterOrg() + "'" +
						" and a.caseno1 = '" + getCaseNo1() + "'";
		
		Database db = null;
		ResultSet rs = null;
		try{
			db = new Database();
			rs = db.querySQL(sql);
			if(rs.next()){
				if(rs.getString("countall").equals("0")){
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

}


