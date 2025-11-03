/*
*<br>程式目的：土地合併分割重測重劃作業－減損單
*<br>程式代號：untla055f
*<br>程式日期：
*<br>程式作者：Yuan-Ren Jheng
*<br>--------------------------------------------------------
*<br>修改作者　　修改日期　　　修改目的
*<br>--------------------------------------------------------
*/

package unt.la;

import java.sql.ResultSet;
import java.util.ArrayList;

import util.Common;
import unt.ch.UNTCH_Tools;
import util.Database;
import util.MaxClosingYM;

public class UNTLA055F extends UNTLA054Q{

	String enterOrg;
	String ownership;
	String caseNo;
	String cause;
	String caseName;
	String writeDate;
	String writeUnit;
	String proofDoc;
	String proofNo;
	String manageNo;
	String summonsNo;
	String reduceDate;
	String approveOrg;
	String approveDate;
	String approveDoc;
	String mergeDivision;
	String verify;
	String notes;
	String editID;
	String editDate;
	String editTime;
	String enterOrgName;
	public String getEnterOrg() {return checkGet(enterOrg);}
	public void setEnterOrg(String enterOrg) {this.enterOrg = checkSet(enterOrg);}
	public String getOwnership() {return checkGet(ownership);}
	public void setOwnership(String ownership) {this.ownership = checkSet(ownership);}
	public String getCaseNo() {return checkGet(caseNo);}
	public void setCaseNo(String caseNo) {this.caseNo = checkSet(caseNo);}
	public String getCaseName() {return checkGet(caseName);}
	public void setCaseName(String caseName) {this.caseName = checkSet(caseName);}
	public String getWriteDate() {return checkGet(writeDate);}
	public void setWriteDate(String writeDate) {this.writeDate = checkSet(writeDate);}
	public String getWriteUnit() {return checkGet(writeUnit);}
	public void setWriteUnit(String writeUnit) {this.writeUnit = checkSet(writeUnit);}
	public String getProofDoc() {return checkGet(proofDoc);}
	public void setProofDoc(String proofDoc) {this.proofDoc = checkSet(proofDoc);}
	public String getProofNo() {return checkGet(proofNo);}
	public void setProofNo(String proofNo) {this.proofNo = checkSet(proofNo);}
	public String getManageNo() {return checkGet(manageNo);}
	public void setManageNo(String manageNo) {this.manageNo = checkSet(manageNo);}
	public String getSummonsNo() {return checkGet(summonsNo);}
	public void setSummonsNo(String summonsNo) {this.summonsNo = checkSet(summonsNo);}
	public String getReduceDate() {return checkGet(reduceDate);}
	public void setReduceDate(String reduceDate) {this.reduceDate = checkSet(reduceDate);}
	public String getApproveOrg() {return checkGet(approveOrg);}
	public void setApproveOrg(String approveOrg) {this.approveOrg = checkSet(approveOrg);}
	public String getApproveDate() {return checkGet(approveDate);}
	public void setApproveDate(String approveDate) {this.approveDate = checkSet(approveDate);}
	public String getApproveDoc() {return checkGet(approveDoc);}
	public void setApproveDoc(String approveDoc) {this.approveDoc = checkSet(approveDoc);}
	public String getMergeDivision() {return checkGet(mergeDivision);}
	public void setMergeDivision(String mergeDivision) {this.mergeDivision = checkSet(mergeDivision);}
	public String getVerify() {return checkGet(verify);}
	public void setVerify(String verify) {this.verify = checkSet(verify);}
	public String getNotes() {return checkGet(notes);}
	public void setNotes(String notes) {this.notes = checkSet(notes);}
	public String getEditID() {return checkGet(editID);}
	public void setEditID(String editID) {this.editID = checkSet(editID);}
	public String getEditDate() {return checkGet(editDate);}
	public void setEditDate(String editDate) {this.editDate = checkSet(editDate);}
	public String getEditTime() {return checkGet(editTime);}
	public void setEditTime(String editTime) {this.editTime = checkSet(editTime);}
	public String getEnterOrgName() {return checkGet(enterOrgName);}
	public void setEnterOrgName(String enterOrgName) {this.enterOrgName = checkSet(enterOrgName);}
	public String getCause() {
		return checkGet(cause);
	}
	public void setCause(String cause) {
		this.cause = checkSet(cause);
	}

	String differenceKind;
	String proofYear;
	public String getDifferenceKind() {return checkGet(differenceKind);}
	public void setDifferenceKind(String differenceKind) {this.differenceKind = checkSet(differenceKind);}
	public String getProofYear() {return checkGet(proofYear);}
	public void setProofYear(String proofYear) {this.proofYear = checkSet(proofYear);}
	
	String reduceCause;
	String reduceCause1;
	public String getReduceCause() {return checkGet(reduceCause);}
	public void setReduceCause(String reduceCause) {this.reduceCause = checkSet(reduceCause);}
	public String getReduceCause1() {return checkGet(reduceCause1);}
	public void setReduceCause1(String reduceCause1) {this.reduceCause1 = checkSet(reduceCause1);}
	

	//檢查update時，proofyear是否有改變
	private boolean checkProofyearChanged(String tableName, String enterorg, String caseno, String newproofyear){
		boolean isChanged = false;
		String oldproofyear ="";
		ResultSet rs;
		Database db = new Database();
		try{
			String sql = "select proofyear from "+tableName+" where enterorg=" + Common.sqlChar(enterorg) +
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
	
	public void setSQLBeanValue(String tableName){
		UNTCH_Tools ut = new UNTCH_Tools();
		sqlbean.setTableName(tableName);
		
		sqlbean.primarykeyMap.clear();
		sqlbean.columnMap.clear();
		
		sqlbean.primarykeyMap.put("caseNo", 		this.getCaseNo());
		sqlbean.primarykeyMap.put("enterOrg", 		this.getEnterOrg());
		sqlbean.primarykeyMap.put("ownership",		this.getOwnership());
		sqlbean.primarykeyMap.put("differenceKind",	this.getDifferenceKind());
		sqlbean.columnMap.put("cause",			this.getReduceCause());
		sqlbean.columnMap.put("cause1",			this.getReduceCause1());
		sqlbean.columnMap.put("enterOrg",		this.getEnterOrg());
		sqlbean.columnMap.put("ownership",		this.getOwnership());
		sqlbean.columnMap.put("caseNo",			this.getCaseNo());
		sqlbean.columnMap.put("differenceKind",	this.getDifferenceKind());
		sqlbean.columnMap.put("caseName",		this.getCaseName());
		sqlbean.columnMap.put("writeDate",		ut._transToCE_Year(this.getWriteDate()));
		sqlbean.columnMap.put("writeUnit",		this.getWriteUnit());
		sqlbean.columnMap.put("proofYear",		this.getProofYear());
		sqlbean.columnMap.put("proofDoc",		this.getProofDoc());

		//問題單1290補充: update時更改編號-年，重新取得編號-號
		String proofno = this.getProofNo();
		if(this.checkProofyearChanged(tableName, getEnterOrg(), getCaseNo(), getProofYear())){
			proofno = MaxClosingYM.getMaxProofNo(tableName,this.getEnterOrg(),this.getOwnership(),this.getProofYear());
		}
		sqlbean.columnMap.put("proofNo",		proofno);
		sqlbean.columnMap.put("manageNo",		this.getManageNo());
		sqlbean.columnMap.put("summonsNo",		this.getSummonsNo());
		sqlbean.columnMap.put("reduceDate",		ut._transToCE_Year(this.getReduceDate()));
		sqlbean.columnMap.put("approveOrg",		this.getApproveOrg());
		sqlbean.columnMap.put("approveDate",	ut._transToCE_Year(this.getApproveDate()));
		sqlbean.columnMap.put("approveDoc",		this.getApproveDoc());
		sqlbean.columnMap.put("verify",			this.getVerify());
		sqlbean.columnMap.put("notes",			this.getNotes());
		sqlbean.columnMap.put("editID",			this.getEditID());
		sqlbean.columnMap.put("editDate",		ut._transToCE_Year(this.getEditDate()));
		sqlbean.columnMap.put("editTime",		this.getEditTime());

		if(tableName.startsWith("UNTLA")){
			sqlbean.columnMap.put("mergeDivision",	this.getCaseNo_Merge());	
		}
		
	}

	public UNTLA054Q_data getParameterData(){
		qbean.tableName="UNTLA_ReduceProof";
		qbean.caseNo=this.getCaseNo_Reduce();
		qbean.enterOrg=this.getEnterOrg();
		qbean.ownership=this.getOwnership();		
		qbean.differenceKind=this.getDifferenceKind();
		return qbean;
	}
	
	//清除畫面所有欄位資料用
	public void clearAllDataForView(){
		this.setWriteDate("");
		this.setWriteUnit("");
		this.setProofDoc("");
		this.setProofNo("");
		this.setManageNo("");
		this.setSummonsNo("");
		this.setReduceDate("");
		this.setEditID("");
		this.setEditDate("");
		this.setEditTime("");
	}

	//儲存切換不同頁面時所需要的訊息
	public void setTransData_MainToTran(){
		this.setCaseNo(this.getCaseNo_Reduce());
	}
	
	//儲存切換不同頁面時所需要的訊息
	public void setTransData_TranToMain(){
		this.setCaseNo_Reduce(this.getCaseNo());
	}
	
//傳回insert sql
protected String[] getInsertSQL(){
    String[] execSQLArray = new String[5];

    setTransData_MainToTran();
	
    //取得新caseNo
    setCaseNo(getNewCaseNoFromDB(getParameterData()));
    //取得新proofNo
    setProofNo(MaxClosingYM.getMaxProofNo("UNTLA_REDUCEPROOF",enterOrg,ownership,this.getProofYear()));

    setTransData_TranToMain();
    
	setSQLBeanValue("UNTLA_REDUCEPROOF");
	execSQLArray[0]=sqlbean.getSQLMethod_Insert(sqlbean);	
	
	setSQLBeanValue("UNTBU_REDUCEPROOF");
	execSQLArray[1]=sqlbean.getSQLMethod_Insert(sqlbean);
	
	setSQLBeanValue("UNTRF_REDUCEPROOF");
	execSQLArray[2]=sqlbean.getSQLMethod_Insert(sqlbean);
	
	setSQLBeanValue("UNTMP_REDUCEPROOF");
	execSQLArray[3]=sqlbean.getSQLMethod_Insert(sqlbean);
	
	execSQLArray[4]=getInsertSQL_UpdateToUNTLA_MergeDivision();	
	
	LogBean.outputLogDebug("execSQLArray[0] : "+execSQLArray[0]+"\n"+
							"execSQLArray[1] : "+execSQLArray[1]+"\n"+
							"execSQLArray[2] : "+execSQLArray[2]+"\n"+
							"execSQLArray[3] : "+execSQLArray[3]+"\n"+
							"execSQLArray[4] : "+execSQLArray[4]+"\n");
	
	extend_getInsertSQL();
	
	return execSQLArray;
}
	
	private String getInsertSQL_UpdateToUNTLA_MergeDivision(){
		String sql="update UNTLA_MergeDivision set"+
						" divisionReduce = "+Common.sqlChar(this.getCaseNo_Reduce())+
					" where caseNo = "+Common.sqlChar(this.getCaseNo_Merge())+
						" and enterorg = "+Common.sqlChar(this.getEnterOrg())+
						" and ownership = "+Common.sqlChar(this.getOwnership())+
						" and differencekind = "+Common.sqlChar(this.getDifferenceKind());
		return sql;
	}


//傳回update sql
protected String[] getUpdateSQL(){
	String[] execSQLArray = new String[5];
	
	setTransData_MainToTran();
	
	setSQLBeanValue("UNTLA_REDUCEPROOF");	
	execSQLArray[0]=sqlbean.getSQLMethod_Update(sqlbean);
	
	setSQLBeanValue("UNTBU_REDUCEPROOF");	
	execSQLArray[1]=sqlbean.getSQLMethod_Update(sqlbean);
	
	setSQLBeanValue("UNTRF_REDUCEPROOF");	
	execSQLArray[2]=sqlbean.getSQLMethod_Update(sqlbean);
	
	setSQLBeanValue("UNTMP_REDUCEPROOF");	
	execSQLArray[3]=sqlbean.getSQLMethod_Update(sqlbean);	
	
	execSQLArray[4]=getUpdateSQL_UNTLA_MergeDivision();
	
	LogBean.outputLogDebug("execSQLArray[0] : "+execSQLArray[0]+"\n"+
							"execSQLArray[1] : "+execSQLArray[1]+"\n"+
							"execSQLArray[2] : "+execSQLArray[2]+"\n"+
							"execSQLArray[3] : "+execSQLArray[3]+"\n"+
							"execSQLArray[4] : "+execSQLArray[4]+"\n");
	
	extend_getUpdateSQL();
	
	return execSQLArray;
}

	private String getUpdateSQL_UNTLA_MergeDivision(){
		String sql="update UNTLA_MergeDivision set"+
							" divisionReduce = "+Common.sqlChar(this.getCaseNo_Reduce())+
						" where caseNo = "+Common.sqlChar(this.getCaseNo_Merge())+
							" and enterorg = "+Common.sqlChar(this.getEnterOrg())+
							" and ownership = "+Common.sqlChar(this.getOwnership())+
							" and differencekind = "+Common.sqlChar(this.getDifferenceKind());
		return sql;
	}


//傳回delete sql
protected String[] getDeleteSQL(){
	String[] execSQLArray = new String[6];
	
	setTransData_MainToTran();
	
	execSQLArray[0]=getDeleteSQL_UpdateToUNTLA_MergeDivision();
	execSQLArray[1]=getDeleteSQL_DeleteFromUNTLA_ReduceDetail();
	setSQLBeanValue("UNTLA_REDUCEPROOF");
	execSQLArray[2]=sqlbean.getSQLMethod_Delete(sqlbean);
	
	setSQLBeanValue("UNTBU_REDUCEPROOF");
	execSQLArray[3]=sqlbean.getSQLMethod_Delete(sqlbean);
	
	setSQLBeanValue("UNTRF_REDUCEPROOF");
	execSQLArray[4]=sqlbean.getSQLMethod_Delete(sqlbean);
	
	setSQLBeanValue("UNTMP_REDUCEPROOF");
	execSQLArray[5]=sqlbean.getSQLMethod_Delete(sqlbean);
		
	LogBean.outputLogDebug("execSQLArray[0] : "+execSQLArray[0]+"\n"+
							"execSQLArray[1] : "+execSQLArray[1]+"\n"+
							"execSQLArray[2] : "+execSQLArray[2]+"\n"+
							"execSQLArray[3] : "+execSQLArray[3]+"\n"+
							"execSQLArray[4] : "+execSQLArray[4]+"\n"+
							"execSQLArray[5] : "+execSQLArray[5]+"\n");
	
	extend_getDeleteSQL();
	
	return execSQLArray;
}
			
	private String getDeleteSQL_UpdateToUNTLA_MergeDivision(){
		String sql="update UNTLA_MergeDivision set"+
					" divisionReduce = null"+
					" where caseNo = "+Common.sqlChar(this.getCaseNo_Merge())+
					" and enterorg = "+Common.sqlChar(this.getEnterOrg())+
					" and ownership = "+Common.sqlChar(this.getOwnership())+
					" and differencekind = "+Common.sqlChar(this.getDifferenceKind());
		return sql;
	}
		
	private String getDeleteSQL_DeleteFromUNTLA_ReduceDetail(){
		String sql="delete from UNTLA_ReduceDetail where 1=1"+
			" and caseNo = "+Common.sqlChar(this.getCaseNo_Reduce())+
			" and enterorg = "+Common.sqlChar(this.getEnterOrg())+
			" and ownership = "+Common.sqlChar(this.getOwnership())+
			" and differencekind = "+Common.sqlChar(this.getDifferenceKind());
		return sql;
	}

//依主鍵查詢單一資料
public UNTLA055F  queryOne() throws Exception{
	Database db = new Database();
	UNTLA055F obj = this;
	String sql="select * from UNTLA_ReduceProof a where 1=1"+
				" and caseno = "+ Common.sqlChar(getCaseNo_Reduce())+
				" and enterorg = "+ Common.sqlChar(getEnterOrg())+
				" and ownership = "+ Common.sqlChar(getOwnership())+
				" and differencekind = "+Common.sqlChar(this.getDifferenceKind());

	try {
		LogBean.outputLogDebug(sql);
		
		UNTCH_Tools ut = new UNTCH_Tools();
		ResultSet rs = db.querySQL(sql);
		if (rs.next()){
			obj.setEnterOrg(rs.getString("enterOrg"));
			obj.setOwnership(rs.getString("ownership"));
			obj.setCaseNo(rs.getString("caseNo"));
			obj.setCaseNo_Reduce(rs.getString("caseNo"));
			obj.setCaseName(rs.getString("caseName"));
			obj.setDifferenceKind(rs.getString("differencekind"));
			obj.setWriteDate(ut._transToROC_Year(rs.getString("writeDate")));
			obj.setWriteUnit(rs.getString("writeUnit"));
			obj.setProofYear(rs.getString("proofYear"));
			obj.setProofDoc(rs.getString("proofDoc"));
			obj.setProofNo(rs.getString("proofNo"));
			obj.setManageNo(rs.getString("manageNo"));
			obj.setSummonsNo(rs.getString("summonsNo"));
			obj.setReduceDate(ut._transToROC_Year(rs.getString("reduceDate")));
			obj.setApproveOrg(rs.getString("approveOrg"));
			obj.setApproveDate(ut._transToROC_Year(rs.getString("approveDate")));
			obj.setApproveDoc(rs.getString("approveDoc"));
			obj.setMergeDivision(rs.getString("mergeDivision"));
			obj.setVerify(rs.getString("verify"));
			obj.setNotes(rs.getString("notes"));
			obj.setEditID(rs.getString("editID"));
			obj.setEditDate(ut._transToROC_Year(rs.getString("editDate")));
			obj.setEditTime(rs.getString("editTime"));

			obj.setReduceCause(rs.getString("cause"));
			
			obj.setEnterOrgName(this.getEnterOrgNameFromDB(getParameterData()));
			setTransData_TranToMain();			
		}
		
		setStateQueryOneSuccess();
	} catch (Exception e) {
		LogBean.outputLogError("Exception : "+e.getMessage()+"\n"+sql);
		
	} finally {
		db.closeAll();
	}
	return obj;
}

//依查詢欄位查詢多筆資料
public ArrayList queryAll() throws Exception{
	Database db = new Database();
	ArrayList objList=new ArrayList();
	
	String sql="select "+
					" enterOrg, ownership, differencekind,"+
					" (select codename from SYSCA_CODE z where z.codekindid = 'DFK' and z.codeid = a.differencekind) as differencekindName," +
					" (select z.organsname from sysca_organ z where 1=1 and z.organID = enterOrg) as enterOrgName,"+
					" (select z.codeName from sysca_code z where 1=1 and (z.codeKindID = 'owa' or z.codeKindID = 'OWA') and z.codeid = ownership) as ownershipName,"+
					" caseNo, caseName, writeDate, reduceDate, (case verify when 'Y' then '是' when 'N' then '否' else '' end) as verify"+
				" from UNTLA_ReduceProof a where 1=1"+
					" and enterorg = " + Common.sqlChar(this.getEnterOrg()) +
					" and ownership = " + Common.sqlChar(this.getOwnership()) +
					" and caseno = " + Common.sqlChar(this.getCaseNo_Reduce()) +
					" and differencekind = " + Common.sqlChar(this.getDifferenceKind());
	
	sql+=" order by enterOrg, ownership, caseNo, differencekind ";

	try {
		LogBean.outputLogDebug(sql);
		
		UNTCH_Tools ut = new UNTCH_Tools();		
		ResultSet rs = db.querySQL(sql,true);
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
			rowArray[3]=Common.get(rs.getString("differencekind"));
			rowArray[4]=Common.get(rs.getString("differencekindName"));		
			rowArray[5]=Common.get(rs.getString("caseNo")); 
			rowArray[6]=Common.get(rs.getString("caseName")); 
			rowArray[7]=ut._transToROC_Year(Common.get(rs.getString("writeDate"))); 
			rowArray[8]=ut._transToROC_Year(Common.get(rs.getString("reduceDate")));
			rowArray[9]=Common.get(rs.getString("verify"));
			
			objList.add(rowArray);
			count++;
			} while (rs.next());
		}
		setStateQueryAllSuccess();
	} catch (Exception e) {
		LogBean.outputLogError("Exception : "+e.getMessage()+"\n"+sql);
		
	} finally {
		db.closeAll();
	}
	return objList;
}


}
