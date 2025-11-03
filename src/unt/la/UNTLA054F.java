/*
*<br>程式目的：土地合併分割重測重劃作業－案件資料
*<br>程式代號：untla054f
*<br>程式日期：
*<br>程式作者：Yuan-Ren Jheng
*<br>--------------------------------------------------------
*<br>修改作者　　修改日期　　　修改目的
*<br>--------------------------------------------------------
*/

package unt.la;

import java.sql.ResultSet;
import java.util.ArrayList;

//import util.Common;
import unt.ch.UNTCH_Tools;
import util.Common;
import util.Database;
import util.Datetime;

public class UNTLA054F extends UNTLA054Q{
	
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
	String divisionReduce;
	String divisionAdd;
	String verify;
	String notes;
	String editID;
	String editDate;
	String editTime;

	public String getEnterOrg() {return checkGet(enterOrg);}
	public void setEnterOrg(String enterOrg) {this.enterOrg = checkSet(enterOrg);}	
	public String getEnterOrgName() {return checkGet(enterOrgName);}
	public void setEnterOrgName(String enterOrgName) {this.enterOrgName = checkSet(enterOrgName);}	
	public String getOwnership() {return checkGet(ownership);}
	public void setOwnership(String ownership) {this.ownership = checkSet(ownership);}
	public String getCaseNo() {return checkGet(caseNo);}
	public void setCaseNo(String caseNo) {this.caseNo = checkSet(caseNo);}
	public String getCaseName() {return checkGet(caseName);}
	public void setCaseName(String caseName) {this.caseName = checkSet(caseName);}
	public String getCause() {return checkGet(cause);}
	public void setCause(String cause) {this.cause = checkSet(cause);}
	public String getCause1() {return checkGet(cause1);}
	public void setCause1(String cause1) {this.cause1 = checkSet(cause1);}	
	public String getEnterDate() {return checkGet(enterDate);}
	public void setEnterDate(String enterDate) {this.enterDate = checkSet(enterDate);}
	public String getApproveOrg() {return checkGet(approveOrg);}
	public void setApproveOrg(String approveOrg) {this.approveOrg = checkSet(approveOrg);}
	public String getApproveDate() {return checkGet(approveDate);}
	public void setApproveDate(String approveDate) {this.approveDate = checkSet(approveDate);}
	public String getApproveDoc() {return checkGet(approveDoc);}
	public void setApproveDoc(String approveDoc) {this.approveDoc = checkSet(approveDoc);}
	public String getDivisionReduce() {return checkGet(divisionReduce);}
	public void setDivisionReduce(String divisionReduce) {this.divisionReduce = checkSet(divisionReduce);}
	public String getDivisionAdd() {return checkGet(divisionAdd);}
	public void setDivisionAdd(String divisionAdd) {this.divisionAdd = checkSet(divisionAdd);}
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
	
	private String differenceKind;
	public String getDifferenceKind() {return checkGet(differenceKind);}
	public void setDifferenceKind(String differenceKind) {this.differenceKind = checkSet(differenceKind);}
	
	
	public void setSQLBeanValue(){
		UNTCH_Tools ut = new UNTCH_Tools();
		sqlbean.setTableName("UNTLA_MergeDivision");
		
		sqlbean.primarykeyMap.clear();
		sqlbean.columnMap.clear();
		
		sqlbean.primarykeyMap.put("caseNo", 	this.getCaseNo());
		sqlbean.primarykeyMap.put("enterOrg", 	this.getEnterOrg());
		sqlbean.primarykeyMap.put("ownership", 	this.getOwnership());
		sqlbean.primarykeyMap.put("differenceKind",	this.getDifferenceKind());
		
		sqlbean.columnMap.put("enterOrg", 		this.getEnterOrg());
		sqlbean.columnMap.put("ownership", 		this.getOwnership());
		sqlbean.columnMap.put("caseNo", 		this.getCaseNo());
		sqlbean.columnMap.put("differenceKind",	this.getDifferenceKind());
		sqlbean.columnMap.put("caseName", 		this.getCaseName());
		sqlbean.columnMap.put("cause", 			this.getCause());
		sqlbean.columnMap.put("enterDate", 		ut._transToCE_Year(this.getEnterDate()));
		sqlbean.columnMap.put("approveOrg", 	this.getApproveOrg());
		sqlbean.columnMap.put("approveDate", 	ut._transToCE_Year(this.getApproveDate()));
		sqlbean.columnMap.put("approveDoc", 	this.getApproveDoc());
		sqlbean.columnMap.put("divisionReduce", this.getDivisionReduce());
		sqlbean.columnMap.put("divisionAdd", 	this.getDivisionAdd());
		sqlbean.columnMap.put("verify", 		this.getVerify());
		sqlbean.columnMap.put("notes", 			this.getNotes());
		sqlbean.columnMap.put("editID", 		this.getEditID());
		sqlbean.columnMap.put("editDate", 		ut._transToCE_Year(this.getEditDate()));
		sqlbean.columnMap.put("editTime", 		this.getEditTime());
	}

	public UNTLA054Q_data getParameterData(){		
		qbean.tableName="UNTLA_MergeDivision";
		qbean.caseNo=this.getCaseNo_Merge();
		qbean.enterOrg=this.getEnterOrg();
		qbean.ownership=this.getOwnership();
		qbean.differenceKind=this.getDifferenceKind();
		return qbean;
	}
	
	//清除畫面所有欄位資料用
	public void clearAllDataForView(){
		this.setOwnership("");
		this.setCaseNo("");
		this.setDifferenceKind("");
		this.setCaseName("");
		this.setEnterDate("");
		this.setVerify("");
		this.setApproveOrg("");
		this.setApproveDate("");
		this.setApproveDoc("");
		this.setCause("");
		this.setDivisionReduce("");
		this.setDivisionAdd("");
		this.setNotes("");
		this.setEditID("");
		this.setEditDate("");
		this.setEditTime("");
	}

	//儲存切換不同頁面時所需要的訊息
	public void setTransData_MainToTran(){
		this.setCaseNo(this.getCaseNo_Merge());
		this.setDivisionReduce(this.getCaseNo_Reduce());
		this.setDivisionAdd(this.getCaseNo_Add());
		this.setPropertyNo_Reduce("");
		this.setSerialNo_Reduce("");
		this.setPropertyNo_Add("");
		this.setSerialNo_Add("");
	}
	
	//儲存切換不同頁面時所需要的訊息
	public void setTransData_TranToMain(){
		this.setCaseNo_Merge(this.getCaseNo());
		this.setCaseNo_Reduce(this.getDivisionReduce());
		this.setCaseNo_Add(this.getDivisionAdd());
		this.setPropertyNo_Reduce("");
		this.setSerialNo_Reduce("");
		this.setPropertyNo_Add("");
		this.setSerialNo_Add("");
	}
	
//傳回insert sql
protected String[] getInsertSQL(){
	String[] execSQLArray = new String[1];

	setTransData_MainToTran();
	
	setCaseNo(getNewCaseNoFromDB(getParameterData()));
	
	setTransData_TranToMain();
	
	setSQLBeanValue();
	execSQLArray[0]=sqlbean.getSQLMethod_Insert(sqlbean);
	
	LogBean.outputLogDebug(execSQLArray[0].toString());
	
	extend_getInsertSQL();
	
	return execSQLArray;
}	

//傳回update sql
protected String[] getUpdateSQL(){
	String[] execSQLArray = new String[5];
	
	setTransData_MainToTran();
		
	setSQLBeanValue();
	execSQLArray[0]=sqlbean.getSQLMethod_Update(sqlbean);
	execSQLArray[1]=getUpdateSQL_UNTLA_ReduceProof();
	execSQLArray[2]=getUpdateSQL_UNTLA_ReduceDetail();
	execSQLArray[3]=getUpdateSQL_UNTLA_AddProof();
	execSQLArray[4]=getUpdateSQL_UNTLA_Land();
	
	
	LogBean.outputLogDebug("execSQLArray[0] : "+execSQLArray[0]+"\n"+
					"execSQLArray[1] : "+execSQLArray[1]+"\n"+
					"execSQLArray[2] : "+execSQLArray[2]+"\n"+
					"execSQLArray[3] : "+execSQLArray[3]+"\n"+
					"execSQLArray[4] : "+execSQLArray[4]+"\n");
	
	extend_getUpdateSQL();
	
	return execSQLArray;
}	
	
	private String getUpdateSQL_UNTLA_ReduceProof(){
		UNTCH_Tools ut = new UNTCH_Tools();
		String sql="UPDATE UNTLA_ReduceProof SET" +
						" CASENAME = " + Common.sqlChar(this.getCaseName()) +
						" ,REDUCEDATE = " + Common.sqlChar(ut._transToCE_Year(this.getEnterDate())) +
						" ,APPROVEORG = " + Common.sqlChar(this.getApproveOrg()) +
						" ,APPROVEDATE = " + Common.sqlChar(ut._transToCE_Year(this.getApproveDate())) +
						" ,APPROVEDOC = " + Common.sqlChar(this.getApproveDoc()) +
						" ,VERIFY = " + Common.sqlChar(this.getVerify()) +
						" ,NOTES = " + Common.sqlChar(this.getNotes()) +
					" WHERE 1=1" +
						" AND CASENO = " + Common.sqlChar(this.getCaseNo_Reduce()) +
						" AND ENTERORG = " + Common.sqlChar(this.getEnterOrg()) +
						" AND OWNERSHIP = " + Common.sqlChar(this.getOwnership()) +
						" AND differencekind = " + Common.sqlChar(this.getDifferenceKind());
		return sql;	
	}
	
	private String getUpdateSQL_UNTLA_ReduceDetail(){
		UNTCH_Tools ut = new UNTCH_Tools();		
		String sql="UPDATE UNTLA_ReduceDetail SET" +
						" CAUSE = " + Common.sqlChar(this.getCause()) +
						" ,REDUCEDATE = " + Common.sqlChar(ut._transToCE_Year(this.getEnterDate())) +
						" ,VERIFY = " + Common.sqlChar(this.getVerify()) +
						" ,NOTES = " + Common.sqlChar(this.getNotes()) +
					" WHERE 1=1" +
						" AND CASENO = " + Common.sqlChar(this.getCaseNo_Reduce()) +
						" AND ENTERORG = " + Common.sqlChar(this.getEnterOrg()) +
						" AND OWNERSHIP = " + Common.sqlChar(this.getOwnership()) +
						" AND differencekind = " + Common.sqlChar(this.getDifferenceKind());
		return sql;	
	}
	
	private String getUpdateSQL_UNTLA_AddProof(){
		UNTCH_Tools ut = new UNTCH_Tools();
		String sql="UPDATE UNTLA_AddProof SET" +
						" CASENAME = " + Common.sqlChar(this.getCaseName()) +
						" ,ENTERDATE = " + Common.sqlChar(ut._transToCE_Year(this.getEnterDate())) +
						" ,VERIFY = " + Common.sqlChar(this.getVerify()) +
						" ,NOTES = " + Common.sqlChar(this.getNotes()) +
					" WHERE 1=1" +
						" AND CASENO = " + Common.sqlChar(this.getCaseNo_Add()) +
						" AND ENTERORG = " + Common.sqlChar(this.getEnterOrg()) +
						" AND OWNERSHIP = " + Common.sqlChar(this.getOwnership()) +
						" AND differencekind = " + Common.sqlChar(this.getDifferenceKind());
		return sql;	
	}

	private String getUpdateSQL_UNTLA_Land(){
		UNTCH_Tools ut = new UNTCH_Tools();
		String sql="UPDATE UNTLA_Land SET" +
						" CAUSE = " + Common.sqlChar(this.getCause()) +
						" ,ENTERDATE = " + Common.sqlChar(ut._transToCE_Year(this.getEnterDate())) +
						" ,VERIFY = " + Common.sqlChar(this.getVerify()) +
					" WHERE 1=1" +
						" AND CASENO = " + Common.sqlChar(this.getCaseNo_Add()) +
						" AND ENTERORG = " + Common.sqlChar(this.getEnterOrg()) +
						" AND OWNERSHIP = " + Common.sqlChar(this.getOwnership()) +
						" AND differencekind = " + Common.sqlChar(this.getDifferenceKind());
		return sql;	
	}
	
	public boolean checkUpdateError(){
		boolean result = true;
		if(checkCanNotReVerify(getEnterDate(),getEnterOrg(),getDifferenceKind())){
			setErrorMsg("當月已月結不可取消入帳");
			result = false;
		}
		if(checkInOtherProof(getEnterOrg(), getCaseNo_Add())){
			setErrorMsg("土地資料已做過增減值或減損作業或移動作業，請先刪除增減值或減損作業或移動作業之資料再回此作業取消入帳!");
			result = false;
		}
		
		return result;
	}


//傳回delete sql
protected String[] getDeleteSQL(){
	
	setTransData_MainToTran();
	
	setSQLBeanValue();
	
	StringBuilder stb = new StringBuilder();
	
	stb.append(sqlbean.getSQLMethod_Delete(sqlbean)).append(":;:");
	
	if("".equals(checkGet(getCaseNo_Reduce()))){
		
	}else{
		stb.append(getDeleteSQL_UNTLA_ReduceDetail()).append(":;:");	
		stb.append(getDeleteSQL_ReduceProof_for("UNTLA_REDUCEPROOF")).append(":;:");
		stb.append(getDeleteSQL_ReduceProof_for("UNTBU_REDUCEPROOF")).append(":;:");
		stb.append(getDeleteSQL_ReduceProof_for("UNTRF_REDUCEPROOF")).append(":;:");
		stb.append(getDeleteSQL_ReduceProof_for("UNTMP_REDUCEPROOF")).append(":;:");
	}
	
	if("".equals(checkGet(getCaseNo_Add()))){
		
	}else{
		stb.append(getDeleteSQL_UNTLA_Value()).append(":;:");	
		stb.append(getDeleteSQL_UNTLA_Manage()).append(":;:");
		stb.append(getDeleteSQL_UNTLA_Attachment()).append(":;:");
		stb.append(getDeleteSQL_UNTLA_Land()).append(":;:");
		stb.append(getDeleteSQL_addProof_for("UNTLA_ADDPROOF")).append(":;:");
		stb.append(getDeleteSQL_addProof_for("UNTBU_ADDPROOF")).append(":;:");
		stb.append(getDeleteSQL_addProof_for("UNTRF_ADDPROOF")).append(":;:");
		stb.append(getDeleteSQL_addProof_for("UNTMP_ADDPROOF")).append(":;:");
	}
	
	String[] execSQLArray = stb.toString().split(":;:");
	
	for(String s : execSQLArray){
		LogBean.outputLogDebug("execSQLArray : " + s + "\n");
	}
		
	extend_getDeleteSQL();
	
	return execSQLArray;
}

	private String getDeleteSQL_UNTLA_ReduceDetail(){
		String sql="DELETE FROM UNTLA_ReduceDetail" +						
					" WHERE 1=1" +
						" AND CASENO = " + Common.sqlChar(this.getCaseNo_Reduce())+
						" AND ENTERORG = " + Common.sqlChar(this.getEnterOrg())+
						" AND OWNERSHIP = " + Common.sqlChar(this.getOwnership())+
						" AND differencekind = " + Common.sqlChar(this.getDifferenceKind());
		return sql;	
	}

	private String getDeleteSQL_ReduceProof_for(String tableName){
		String sql="DELETE FROM " + tableName +						
					" WHERE 1=1" +
						" AND CASENO = " + Common.sqlChar(this.getCaseNo_Reduce())+
						" AND ENTERORG = " + Common.sqlChar(this.getEnterOrg())+
						" AND OWNERSHIP = " + Common.sqlChar(this.getOwnership())+
						" AND differencekind = " + Common.sqlChar(this.getDifferenceKind());
		return sql;	
	}
	
	private String getDeleteSQL_UNTLA_Value(){
		String sql="DELETE FROM UNTLA_Value" +						
					" WHERE 1=1" +
					" AND ENTERORG = " + Common.sqlChar(this.getEnterOrg())+
					" AND OWNERSHIP = " + Common.sqlChar(this.getOwnership())+
					" AND differencekind = " + Common.sqlChar(this.getDifferenceKind())+
					" AND PROPERTYNO IN " + 
					" ("+
						" SELECT PROPERTYNO FROM UNTLA_LAND"+
						" WHERE 1=1"+
						" AND CASENO = " + Common.sqlChar(this.getCaseNo_Add())+
						" AND ENTERORG = " + Common.sqlChar(this.getEnterOrg())+
						" AND OWNERSHIP = " + Common.sqlChar(this.getOwnership())+		
						" AND differencekind = " + Common.sqlChar(this.getDifferenceKind())+
					")"+
					" AND SERIALNO IN " + 
					" ("+
						" SELECT SERIALNO FROM UNTLA_LAND"+
						" WHERE 1=1"+
						" AND CASENO = " + Common.sqlChar(this.getCaseNo_Add())+
						" AND ENTERORG = " + Common.sqlChar(this.getEnterOrg())+
						" AND OWNERSHIP = " + Common.sqlChar(this.getOwnership())+		
						" AND differencekind = " + Common.sqlChar(this.getDifferenceKind())+
					")";
		return sql;	
	}
	
	private String getDeleteSQL_UNTLA_Manage(){
		String sql="DELETE FROM UNTLA_Manage" +						
					" WHERE 1=1" +
					" AND ENTERORG = " + Common.sqlChar(this.getEnterOrg())+
					" AND OWNERSHIP = " + Common.sqlChar(this.getOwnership())+
					" AND differencekind = " + Common.sqlChar(this.getDifferenceKind())+
					" AND PROPERTYNO IN " + 
					" ("+
						" SELECT PROPERTYNO FROM UNTLA_LAND"+
						" WHERE 1=1"+
						" AND CASENO = " + Common.sqlChar(this.getCaseNo_Add())+
						" AND ENTERORG = " + Common.sqlChar(this.getEnterOrg())+
						" AND OWNERSHIP = " + Common.sqlChar(this.getOwnership())+		
						" AND differencekind = " + Common.sqlChar(this.getDifferenceKind())+
					")"+
					" AND SERIALNO IN " + 
					" ("+
						" SELECT SERIALNO FROM UNTLA_LAND"+
						" WHERE 1=1"+
						" AND CASENO = " + Common.sqlChar(this.getCaseNo_Add())+
						" AND ENTERORG = " + Common.sqlChar(this.getEnterOrg())+
						" AND OWNERSHIP = " + Common.sqlChar(this.getOwnership())+			
						" AND differencekind = " + Common.sqlChar(this.getDifferenceKind())+
					")";
		return sql;	
	}
	
	private String getDeleteSQL_UNTLA_Attachment(){
		String sql="DELETE FROM UNTLA_Attachment" +						
					" WHERE 1=1" +
					" AND ENTERORG = " + Common.sqlChar(this.getEnterOrg())+
					" AND OWNERSHIP = " + Common.sqlChar(this.getOwnership())+
					" AND differencekind = " + Common.sqlChar(this.getDifferenceKind())+
					" AND PROPERTYNO IN " + 
					" ("+
						" SELECT PROPERTYNO FROM UNTLA_LAND"+
						" WHERE 1=1"+
						" AND CASENO = " + Common.sqlChar(this.getCaseNo_Add())+
						" AND ENTERORG = " + Common.sqlChar(this.getEnterOrg())+
						" AND OWNERSHIP = " + Common.sqlChar(this.getOwnership())+
						" AND differencekind = " + Common.sqlChar(this.getDifferenceKind())+
					")"+
					" AND SERIALNO IN " + 
					" ("+
						" SELECT SERIALNO FROM UNTLA_LAND"+
						" WHERE 1=1"+
						" AND CASENO = " + Common.sqlChar(this.getCaseNo_Add())+
						" AND ENTERORG = " + Common.sqlChar(this.getEnterOrg())+
						" AND OWNERSHIP = " + Common.sqlChar(this.getOwnership())+
						" AND differencekind = " + Common.sqlChar(this.getDifferenceKind())+
					")";
		return sql;	
	}

	private String getDeleteSQL_UNTLA_Land(){
		String sql="DELETE FROM UNTLA_Land" +						
					" WHERE 1=1" +
						" AND CASENO = " + Common.sqlChar(this.getCaseNo_Add())+
						" AND ENTERORG = " + Common.sqlChar(this.getEnterOrg())+
						" AND OWNERSHIP = " + Common.sqlChar(this.getOwnership())+
						" AND differencekind = " + Common.sqlChar(this.getDifferenceKind());
		return sql;	
	}
	
	private String getDeleteSQL_addProof_for(String tableName){
		String sql="DELETE FROM " + tableName +						
					" WHERE 1=1" +
						" AND CASENO = " + Common.sqlChar(this.getCaseNo_Add())+
						" AND ENTERORG = " + Common.sqlChar(this.getEnterOrg())+
						" AND OWNERSHIP = " + Common.sqlChar(this.getOwnership())+
						" AND differencekind = " + Common.sqlChar(this.getDifferenceKind());
		return sql;	
	}
	

//依主鍵查詢單一資料
public UNTLA054F  queryOne() throws Exception{
	Database db = new Database();
	UNTLA054F obj = this;
	UNTCH_Tools ut = new UNTCH_Tools();
	
	String sql="select * from UNTLA_MergeDivision where 1=1"+
				" and caseno = "+ Common.sqlChar(getCaseNo_Merge())+
				" and enterorg = "+ Common.sqlChar(getEnterOrg())+
				" and ownership = "+ Common.sqlChar(getOwnership())+
				" AND differencekind = " + Common.sqlChar(this.getDifferenceKind());

	try {
		LogBean.outputLogDebug(sql);
		
		ResultSet rs = db.querySQL(sql);
		if (rs.next()){
			obj.setEnterOrg(rs.getString("enterOrg"));				
			obj.setOwnership(rs.getString("ownership"));			
			obj.setCaseNo(rs.getString("caseNo"));					
			obj.setCaseNo_Merge(rs.getString("caseNo"));
			obj.setCaseName(rs.getString("caseName"));
			obj.setDifferenceKind(rs.getString("differencekind"));
			obj.setCause(rs.getString("cause"));						
			obj.setEnterDate(ut._transToROC_Year(rs.getString("enterDate")));			
			obj.setApproveOrg(rs.getString("approveOrg"));			
			obj.setApproveDate(ut._transToROC_Year(rs.getString("approveDate")));		
			obj.setApproveDoc(rs.getString("approveDoc"));			
			obj.setDivisionReduce(rs.getString("divisionReduce"));	
			obj.setCaseNo_Reduce(rs.getString("divisionReduce"));
			obj.setDivisionAdd(rs.getString("divisionAdd"));		
			obj.setCaseNo_Add(rs.getString("divisionAdd"));
			obj.setVerify(rs.getString("verify"));					
			obj.setNotes(rs.getString("notes"));					
			obj.setEditID(rs.getString("editID"));					
			obj.setEditDate(ut._transToROC_Year(rs.getString("editDate")));				
			obj.setEditTime(rs.getString("editTime"));
			
			obj.setEnterOrgName(getEnterOrgNameFromDB(getParameterData()));
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
	UNTCH_Tools ut = new UNTCH_Tools();
	
	String sql="select "+
				" a.ownership, a.differencekind,"+
				" (select codename from SYSCA_CODE z where z.codekindid = 'DFK' and z.codeid = a.differencekind) as differencekindName," +
				" (select z.codeName from sysca_code z where 1=1 and (z.codeKindID='owa' or z.codeKindID='OWA') and z.codeid=a.ownership) as ownershipName,"+
				" a.caseNo, a.caseName, "+
				" (select codeName from SYSCA_Code where codeKindID='CAA' and codeid=a.cause) as causeName,"+
				" a.enterDate, (case a.verify when 'Y' then '是' when 'N' then '否' else '' end) as verify, "+
				" a.enterorg,"+
				" a.divisionadd," +
				" a.divisionreduce" +
				" from UNTLA_MergeDivision a where 1=1";
	
	if (!"".equals(getQ_enterOrg())) {		sql+=" and a.enterOrg = " + Common.sqlChar(getQ_enterOrg()) ;}
	if (!"".equals(getQ_ownership())) {		sql+=" and a.ownership = " + Common.sqlChar(getQ_ownership()) ;}
	if (!"".equals(getQ_differenceKind())) {sql+=" and a.differenceKind = " + Common.sqlChar(getQ_differenceKind()) ;}
	if (!"".equals(getQ_caseNoS())) {		sql+=" and a.caseNo >= " + Common.sqlChar(getQ_caseNoS()) ;}
	if (!"".equals(getQ_caseNoE())) {		sql+=" and a.caseNo <= " + Common.sqlChar(getQ_caseNoE()) ;}
	if (!"".equals(getQ_caseName())) {		sql+=" and a.caseName like  " + Common.sqlChar("%"+getQ_caseName()+"%") ;}		
	if (!"".equals(getQ_cause())) {			sql+=" and a.cause = " + Common.sqlChar(getQ_cause()) ;}
	if (!"".equals(getQ_enterDateS())) {	sql+=" and a.enterDate >= " + Common.sqlChar(ut._transToCE_Year(getQ_enterDateS())) ;}
	if (!"".equals(getQ_enterDateE())) {	sql+=" and a.enterDate <= " + Common.sqlChar(ut._transToCE_Year(getQ_enterDateE())) ;}
	if (!"".equals(getQ_verify())) {		sql+=" and a.verify = " + Common.sqlChar(getQ_verify()) ;}
	
	sql+=" order by a.caseno desc ";

	try {
		LogBean.outputLogDebug(sql);
		
		ResultSet rs = db.querySQL(sql,true);
		processCurrentPageAttribute(rs);			
		if (getTotalRecord() > 0) {
			int count = getRecordStart();
			int end = getRecordEnd();
			do {
				if (count > end)
					break;
			String rowArray[]=new String[12];
			
			rowArray[0]=Common.get(rs.getString("enterorg"));
			rowArray[1]=Common.get(rs.getString("ownership")); 
			rowArray[2]=Common.get(rs.getString("ownershipName"));
			rowArray[3]=Common.get(rs.getString("differenceKind"));
			rowArray[4]=Common.get(rs.getString("differenceKindName"));
			rowArray[5]=Common.get(rs.getString("caseNo"));
			rowArray[6]=Common.get(rs.getString("caseName")); 
			rowArray[7]=Common.get(rs.getString("causeName")); 
			rowArray[8]=ut._transToROC_Year(Common.get(rs.getString("enterDate"))); 
			rowArray[9]=Common.get(rs.getString("verify"));
			rowArray[10]=Common.get(rs.getString("divisionadd"));
			rowArray[11]=Common.get(rs.getString("divisionreduce"));
			
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


//<<<<<<<<<<<<<<<<<<<<<　Verify 入帳流程　>>>>>>>>>>>>>>>>>>>>>>>>

public void execCheckCanApproveRe(){
	String sql=" select count(*) as count "+
				" from UNTLA_AddProof a,UNTLA_Land b "+
					" where a.enterOrg      = '"+this.getEnterOrg()+"'"+
					" and a.ownership     = '"+this.getOwnership()+"'"+
					" and a.differencekind     = '"+this.getDifferenceKind()+"'"+
					" and a.mergeDivision = '"+this.getCaseNo()+"'"+
					" and a.enterOrg      = b.enterOrg"+
					" and a.ownership     = b.ownership"+
					" and a.caseNo        = b.caseNo"+
					" and a.differencekind = b.differencekind"+
					" and (b.enterOrg) in "+
					" ("+
						" ("+
						" select c.enterOrg"+
						" from UNTLA_AdjustDetail c"+
						" where c.enterOrg  =b.enterOrg"+
						" and c.ownership =b.ownership"+
						" and c.differencekind = b.differencekind"+
						" and c.propertyNo=b.propertyNo"+
						" and c.serialNo  =b.serialNo"+
						" )"+
						" union"+
						" ("+
						" select d.enterOrg"+
						" from UNTLA_ReduceDetail d"+
						" where d.caseNo    !=(case '"+("".equals(this.getDivisionReduce())?"99":this.getDivisionReduce())+"' when '99' then '99' else '"+this.getDivisionReduce()+"' end)"+
						" and d.enterOrg  =b.enterOrg"+
						" and d.ownership =b.ownership"+
						" and d.differencekind = b.differencekind"+
						" and d.propertyNo=b.propertyNo"+
						" and d.serialNo  =b.serialNo"+
						" )"+
					" )" +
					" and (b.ownership) in "+
					" ("+
						" ("+
						" select c.ownership"+
						" from UNTLA_AdjustDetail c"+
						" where c.enterOrg  =b.enterOrg"+
						" and c.ownership =b.ownership"+
						" and c.differencekind = b.differencekind"+
						" and c.propertyNo=b.propertyNo"+
						" and c.serialNo  =b.serialNo"+
						" )"+
						" union"+
						" ("+
						" select d.ownership"+
						" from UNTLA_ReduceDetail d"+
						" where d.caseNo    !=(case '"+("".equals(this.getDivisionReduce())?"99":this.getDivisionReduce())+"' when '99' then '99' else '"+this.getDivisionReduce()+"' end)"+
						" and d.enterOrg  =b.enterOrg"+
						" and d.ownership =b.ownership"+
						" and d.differencekind = b.differencekind"+
						" and d.propertyNo=b.propertyNo"+
						" and d.serialNo  =b.serialNo"+
						" )"+
					" )" +
					" and (b.propertyNo) in "+
					" ("+
						" ("+
						" select c.propertyNo"+
						" from UNTLA_AdjustDetail c"+
						" where c.enterOrg  =b.enterOrg"+
						" and c.ownership =b.ownership"+
						" and c.differencekind = b.differencekind"+
						" and c.propertyNo=b.propertyNo"+
						" and c.serialNo  =b.serialNo"+
						" )"+
						" union"+
						" ("+
						" select d.propertyNo"+
						" from UNTLA_ReduceDetail d"+
						" where d.caseNo    !=(case '"+("".equals(this.getDivisionReduce())?"99":this.getDivisionReduce())+"' when '99' then '99' else '"+this.getDivisionReduce()+"' end)"+
						" and d.enterOrg  =b.enterOrg"+
						" and d.ownership =b.ownership"+
						" and d.differencekind = b.differencekind"+
						" and d.propertyNo=b.propertyNo"+
						" and d.serialNo  =b.serialNo"+
						" )"+
					" )" +
					" and (b.serialNo) in "+
					" ("+
						" ("+
						" select c.serialNo"+
						" from UNTLA_AdjustDetail c"+
						" where c.enterOrg  =b.enterOrg"+
						" and c.ownership =b.ownership"+
						" and c.differencekind = b.differencekind"+
						" and c.propertyNo=b.propertyNo"+
						" and c.serialNo  =b.serialNo"+
						" )"+
						" union"+
						" ("+
						" select d.serialNo"+
						" from UNTLA_ReduceDetail d"+
						" where d.caseNo    !=(case '"+("".equals(this.getDivisionReduce())?"99":this.getDivisionReduce())+"' when '99' then '99' else '"+this.getDivisionReduce()+"' end)"+
						" and d.enterOrg  =b.enterOrg"+
						" and d.ownership =b.ownership"+
						" and d.differencekind = b.differencekind"+
						" and d.propertyNo=b.propertyNo"+
						" and d.serialNo  =b.serialNo"+
						" )"+
					" )";
	
	LogBean.outputLogDebug(sql);
				
	if("N".equals(this.getVerify())){
		setErrorMsg("尚未入帳，請直接修改資料即可");		
	}else if(!"0".equals(getNameData("count", sql))) {	
		setErrorMsg("增加單之土地資料已做過增減值或減損作業，請先刪除增減值或減損作業之資料再回此作業回復入帳");
	}else{
		setVerify("N");
		execVerifyProduce();
		setErrorMsg("回復入帳完成");
	}
	
	extend_execCheckCanApproveRe();
}

public void execVerifyProduce(){
	boolean isVerify = ("Y".equals(this.getVerify())?true:false);
	this.setEditID(getUserID());
	this.setEditDate(Datetime.getYYYYMMDD());
	this.setEditTime(Datetime.getHHMMSS());
	
	execVerifyProduce_UpdataTo_UNTLA_MergeDivision(isVerify);
	execVerifyProduce_UpdataTo_UNTLA_ReduceProof(isVerify);
	execVerifyProduce_UpdataTo_UNTLA_ReduceDetail(isVerify);
	execVerifyProduce_UpdataTo_UNTLA_AddProof(isVerify);
	execVerifyProduce_UpdataTo_UNTLA_LAND_in_UNTLA_ReduceDetail(isVerify);
	execVerifyProduce_UpdataTo_UNTLA_LAND_in_UNTLA_LAND(isVerify);
	execVerifyProduce_UpdataTo_UNTLA_Manage(isVerify);
	
	extend_execVerifyProduce();
}



	private void execVerifyProduce_UpdataTo_UNTLA_MergeDivision(boolean isVerify){
		String sql=getVerifySQL_UpdataTo_UNTLA_MergeDivision(isVerify);
		execSQL_ForNoResult(sql);
	}

		private String getVerifySQL_UpdataTo_UNTLA_MergeDivision(boolean isVerify){
			UNTCH_Tools ut = new UNTCH_Tools();
			String sql="UPDATE UNTLA_MERGEDIVISION SET" 
						  + " verify = " + Common.sqlChar((isVerify)?"Y":"N") 
						  + " ,enterdate = " + Common.sqlChar((isVerify)?(ut._transToCE_Year(this.getEnterDate())):"") 
						  + ",editid=" + Common.sqlChar(this.getEditID())
						  + ",editdate=" + Common.sqlChar(this.getEditDate())
						  + ",edittime=" + Common.sqlChar(this.getEditTime())
					 + " WHERE 1=1"
						  + " AND caseno = " + Common.sqlChar(this.getCaseNo_Merge())
						  + " AND enterorg = " + Common.sqlChar(this.getEnterOrg())
						  + " AND ownership = " + Common.sqlChar(this.getOwnership())
						  + " AND differencekind = " + Common.sqlChar(this.getDifferenceKind());
			return sql;	
		}

	private void execVerifyProduce_UpdataTo_UNTLA_ReduceProof(boolean isVerify){
		String sql=getVerifySQL_UpdataTo_UNTLA_ReduceProof(isVerify);
		execSQL_ForNoResult(sql);
	}

		private String getVerifySQL_UpdataTo_UNTLA_ReduceProof(boolean isVerify){
			UNTCH_Tools ut = new UNTCH_Tools();
			String sql="UPDATE UNTLA_REDUCEPROOF SET" +
							" verify = " + Common.sqlChar((isVerify)?"Y":"N") +
							",reducedate = " + Common.sqlChar((isVerify)?(ut._transToCE_Year(this.getEnterDate())):"") +
							",editid=" + Common.sqlChar(this.getEditID())+
							",editdate=" + Common.sqlChar(this.getEditDate())+
							",edittime=" + Common.sqlChar(this.getEditTime())+
						" WHERE 1=1" +
							" AND caseno = " + Common.sqlChar(this.getCaseNo_Reduce())+
							" AND enterorg = " + Common.sqlChar(this.getEnterOrg())+
							" AND ownership = " + Common.sqlChar(this.getOwnership())+
							" AND differencekind = " + Common.sqlChar(this.getDifferenceKind());
			return sql;	
		}

	private void execVerifyProduce_UpdataTo_UNTLA_ReduceDetail(boolean isVerify){
		String sql=getVerifySQL_UpdataTo_UNTLA_ReduceDetail(isVerify);
		execSQL_ForNoResult(sql);
	}

		private String getVerifySQL_UpdataTo_UNTLA_ReduceDetail(boolean isVerify){
			UNTCH_Tools ut = new UNTCH_Tools();
			String sql="UPDATE UNTLA_REDUCEDETAIL SET" +
							" verify = " + Common.sqlChar((isVerify)?"Y":"N") +
							",reducedate = " + Common.sqlChar((isVerify)?(ut._transToCE_Year(this.getEnterDate())):"") +
							",editid=" + Common.sqlChar(this.getEditID())+
							",editdate=" + Common.sqlChar(this.getEditDate())+
							",edittime=" + Common.sqlChar(this.getEditTime())+
						" WHERE 1=1" +
							" AND caseno = " + Common.sqlChar(this.getCaseNo_Reduce())+
							" AND enterorg = " + Common.sqlChar(this.getEnterOrg())+
							" AND ownership = " + Common.sqlChar(this.getOwnership())+
							" AND differencekind = " + Common.sqlChar(this.getDifferenceKind());
			return sql;	
		}

	private void execVerifyProduce_UpdataTo_UNTLA_AddProof(boolean isVerify){
		String sql=getVerifySQL_UpdataTo_UNTLA_AddProof(isVerify);
		execSQL_ForNoResult(sql);
	}

		private String getVerifySQL_UpdataTo_UNTLA_AddProof(boolean isVerify){
			UNTCH_Tools ut = new UNTCH_Tools();
			String sql="UPDATE UNTLA_ADDPROOF SET" +
							" verify = " + Common.sqlChar((isVerify)?"Y":"N") +							
							",enterdate = " + Common.sqlChar((isVerify)?(ut._transToCE_Year(this.getEnterDate())):"") +
							",editid=" + Common.sqlChar(this.getEditID())+
							",editdate=" + Common.sqlChar(this.getEditDate())+
							",edittime=" + Common.sqlChar(this.getEditTime())+
						" WHERE 1=1" +
							" AND caseno = " + Common.sqlChar(this.getCaseNo_Add())+
							" AND enterorg = " + Common.sqlChar(this.getEnterOrg())+
							" AND ownership = " + Common.sqlChar(this.getOwnership())+
							" AND differencekind = " + Common.sqlChar(this.getDifferenceKind());
			return sql;	
		}

	private void execVerifyProduce_UpdataTo_UNTLA_LAND_in_UNTLA_ReduceDetail(boolean isVerify){
		String sql=getVerifySQL_UpdataTo_UNTLA_LAND(isVerify);
		execSQL_ForNoResult(sql);
	}

		//Update Untla_Land的資料 (減損資料改為入帳)
		private String getVerifySQL_UpdataTo_UNTLA_LAND(boolean isVerify){
			UNTCH_Tools ut = new UNTCH_Tools();
			String updateField = "";
			
			// #region 入帳、回復入帳需更新之欄位
			if(isVerify){
				updateField = "ul.datastate = '2'"
							+ ",ul.reducedate = " + Common.sqlChar(ut._transToCE_Year(this.getEnterDate()))
							+ ",ul.reducecause = " + Common.sqlChar(this.getCause())
							+ ",ul.holdnume='0'"
							+ ",ul.holdarea='0'"
							+ ",ul.bookvalue='0'"
							+ ",ul.netvalue='0'"
							+ ",ul.editid=" + Common.sqlChar(this.getEditID())
							+ ",ul.editdate=" + Common.sqlChar(this.getEditDate())
							+ ",ul.edittime=" + Common.sqlChar(this.getEditTime());
			}else {
				updateField = "ul.datastate = '1'"
							+ ",ul.reducedate = NULL"
							+ ",ul.reducecause = NULL"
							+ ",ul.holdnume = ur.holdnume"
							+ ",ul.holdarea = ur.holdarea"
							+ ",ul.bookvalue = ur.bookvalue"
							+ ",ul.netvalue = ur.netvalue"
							+ ",ul.editid=" + Common.sqlChar(this.getEditID())
							+ ",ul.editdate=" + Common.sqlChar(this.getEditDate())
							+ ",ul.edittime=" + Common.sqlChar(this.getEditTime());
			}
			// #endregion
			
			String sql = "with ur as("
					   + "select * from UNTLA_REDUCEDETAIL"
					   + " where enterorg = " + Common.sqlChar(this.getEnterOrg())
					   + " and caseno = " + Common.sqlChar(this.getCaseNo_Reduce())
					   + ")"
					   + " update ul"
					   + " set "
					   + updateField
					   + " from UNTLA_LAND ul , ur"
					   + " where ul.enterorg = ur.enterorg"
					   + " and ul.ownership = ur.ownership"
					   + " and ul.differencekind = ur.differencekind"
					   + " and ul.propertyno = ur.propertyno"
					   + " and ul.serialno = ur.serialno";
			
			return sql;	
		}
		
	private void execVerifyProduce_UpdataTo_UNTLA_LAND_in_UNTLA_LAND(boolean isVerify){
		String sql=getVerifySQL_UpdataTo_UNTLA_LAND_in_UNTLA_LAND(isVerify);
		execSQL_ForNoResult(sql);
	}
		
		//Update 在Untla_Land的資料到Untla_Land (現存資料改為入帳)
		private String getVerifySQL_UpdataTo_UNTLA_LAND_in_UNTLA_LAND(boolean isVerify){
			String sql="UPDATE UNTLA_LAND SET" +
						 " verify = " + (isVerify?"'Y'":"'N'") +
						 ",editid=" + Common.sqlChar(this.getEditID())+
						 ",editdate=" + Common.sqlChar(this.getEditDate())+
						 ",edittime=" + Common.sqlChar(this.getEditTime())+
					   " WHERE 1=1" +
					 	 " AND caseno = " + Common.sqlChar(this.getDivisionAdd()) +
					 	 " AND enterorg = " + Common.sqlChar(this.getEnterOrg()) +
						 " AND ownership = " + Common.sqlChar(this.getOwnership()) +
						 " AND differencekind = " + Common.sqlChar(this.getDifferenceKind());
			
			return sql;	
		}

		
	private void execVerifyProduce_UpdataTo_UNTLA_Manage(boolean isVerify){
//		String sql=getVerifySQL_UpdataTo_UNTLA_Manage(isVerify);
//		execSQL_ForNoResult(sql);
	}

//		private String getVerifySQL_UpdataTo_UNTLA_Manage(boolean isVerify){
//			String sql="UPDATE ("+
//							" select b.useDateE from UNTLA_ReduceDetail a, UNTLA_Manage b "+
//							" WHERE 1=1 "+
//							" AND a.enterorg=b.enterorg and a.ownership=b.ownership and a.propertyno=b.propertyno and a.serialno=b.serialno"+
//							" AND a.CASENO = " + Common.sqlChar(this.getDivisionReduce())+
//							" AND a.ENTERORG = " + Common.sqlChar(this.getEnterOrg())+
//							" AND a.OWNERSHIP = " + Common.sqlChar(this.getOwnership())+
//							" AND a.differencekind = " + Common.sqlChar(this.getDifferenceKind())+
//						") SET" +
//						((isVerify)?" isDefault = '0'" : " isDefault = '1'");
//			return sql;	
//		}
		
/**入帳檢查*/
public String execVerifyCheck(){
	String message="";
	
	if(checkClosingYMfromUNTAC_CLOSINGPT(getEnterDate(),getEnterOrg(),getDifferenceKind())) {
		message="入帳日期需為最大月結年月＋１";
	}
	if(checkBuyDate_sourceDate(getEnterDate(), getEnterOrg(), getCaseNo_Add())) {
		message="入帳日期不可小於購置日期、財產取得日期";
	}
	
//	boolean manageFlag = false;
//	boolean attachmentFlag = false;
	
//	//入帳時，檢核該單據每筆土地資料皆必須至少有一筆「管理資料(UNTLA_Manage)」，若不符合，則不允許入帳，並提示使用者。
//		sql = " select sum(count) as COUNT from (" +
//				" select (select case when count(*)=0 then 1 else 0 end from UNTLA_Manage b where 1=1 and a.enterOrg=b.enterOrg and a.ownership=b.ownership and a.differencekind=b.differencekind and a.propertyNo=b.propertyNo and a.serialNo=b.serialNo) as count"+
//				" from Untla_Land a" +
//		 		" where 1=1" +
//		 		" and a.enterOrg = " + Common.sqlChar(this.getEnterOrg()) +
//		 		" and a.ownership = " + Common.sqlChar(this.getOwnership()) +
//		 		" and a.caseNo = " + Common.sqlChar(this.getCaseNo_Add()) +
//		 		" and a.differencekind = " + Common.sqlChar(this.getDifferenceKind()) +
//		 		" ) a";
//		
//		if("0".equals(getNameData("COUNT", sql))){	manageFlag = false;
//		}else{										manageFlag = true;
//		}				
//		
//	//入帳時，檢核該單據每筆土地資料皆必須至少有一筆「地上物資料(UNTLA_Attachment)」，若不符合，則不允許入帳，並提示使用者。
//		sql = " select sum(count) as COUNT from (" +
//					" select (select case when count(*)=0 then 1 else 0 end from UNTLA_Attachment b where 1=1 and a.enterOrg=b.enterOrg and a.ownership=b.ownership and a.differencekind=b.differencekind and a.propertyNo=b.propertyNo and a.serialNo=b.serialNo) as count"+
//					" from Untla_Land a" +
//			 		" where 1=1" +
//			 		" and a.enterOrg = " + Common.sqlChar(this.getEnterOrg()) +
//			 		" and a.ownership = " + Common.sqlChar(this.getOwnership()) +
//			 		" and a.caseNo = " + Common.sqlChar(this.getCaseNo_Add()) +
//			 		" and a.differencekind = " + Common.sqlChar(this.getDifferenceKind()) +
//			 		" ) a";
//		
//		if("0".equals(getNameData("COUNT", sql))){		attachmentFlag = false;
//		}else{											attachmentFlag = true;
//		}				
//		
//		sql = null;
//
//	 if(manageFlag && attachmentFlag){	 message = "每筆財產至少有一筆管理資料和一筆地上物資料才可入帳，請重新檢查！！";		 
//	 }else if(manageFlag){				 message = "每筆財產至少有一筆管理資料才可入帳，請重新檢查！！";
//	 }else if(attachmentFlag){			 message = "每筆財產至少有一筆地上物資料才可入帳，請重新檢查！！";
//	 }	 
		
	 return message;
}


	public boolean checkDataCountIsZero(String enterOrg, String caseNo){
		boolean result = false;
		
		String sql = "select" + 
						" (select case COUNT(*) when 0 then 0 else 1 end from UNTLA_LAND b where a.enterorg = b.enterorg and a.caseno = b.mergedivision ) +" +
						" (select case COUNT(*) when 0 then 0 else 1 end from UNTLA_REDUCEDETAIL c where a.caseno = c.mergedivision and a.enterorg = c.enterorg and a.differencekind = c.differencekind and a.enterorg = c.enterorg and a.differencekind = c.differencekind and a.ownership = c.ownership) as count" +
					" from UNTLA_MERGEDIVISION a" +
					" where 1=1" +
						" and a.enterorg = '" + getEnterOrg() + "'" +
						" and a.caseno = '" + getCaseNo() + "'";
		
		Database db = null;
		ResultSet rs = null;
		try{
			db = new Database();
			rs = db.querySQL(sql);
			if(rs.next()){
				if(rs.getString("count").equals("0")){
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
		
//============================== pulg-in ==================================
		
	protected void extend_execVerifyProduce(){
		
		
	}
		
}


