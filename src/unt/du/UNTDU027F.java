/*
*<br>程式目的：01.土地改良物主檔-增加單資料
*<br>程式代號：
*<br>程式日期：0970711
*<br>程式作者：shan
*<br>--------------------------------------------------------
*<br>修改作者　　修改日期　　　修改目的
*<br>--------------------------------------------------------
*/

package unt.du;

import java.sql.ResultSet;
import java.util.ArrayList;
import util.*;

public class UNTDU027F extends QueryBean{

//主ｋｅｙ
String enterOrg;
String enterOrgName;
String ownership;
String caseNo;
String propertyNo;
String lotNo;
String serialNo;

public String getEnterOrg(){ return checkGet(enterOrg); }
public void setEnterOrg(String s){ enterOrg=checkSet(s); }
public String getEnterOrgName(){ return checkGet(enterOrgName); }
public void setEnterOrgName(String s){ enterOrgName=checkSet(s); }
public String getOwnership(){ return checkGet(ownership); }
public void setOwnership(String s){ ownership=checkSet(s); }
public String getCaseNo(){ return checkGet(caseNo); }
public void setCaseNo(String s){ caseNo=checkSet(s); }
public String getPropertyNo(){ return checkGet(propertyNo); }
public void setPropertyNo(String s){ propertyNo=checkSet(s); }
public String getLotNo(){ return checkGet(lotNo); }
public void setLotNo(String s){ lotNo=checkSet(s); }
public String getSerialNo(){ return checkGet(serialNo); }
public void setSerialNo(String s){ serialNo=checkSet(s); }

//查詢
String q_enterOrg;
String q_enterOrgName;
String q_ownership;
String q_caseNo;
String q_propertyNo;
String q_serialNo;
String q_lotNo;
public String getQ_enterOrg(){ return checkGet(q_enterOrg); }
public void setQ_enterOrg(String s){ q_enterOrg=checkSet(s); }
public String getQ_enterOrgName(){ return checkGet(q_enterOrgName); }
public void setQ_enterOrgName(String s){ q_enterOrgName=checkSet(s); }
public String getQ_ownership(){ return checkGet(q_ownership); }
public void setQ_ownership(String s){ q_ownership=checkSet(s); }
public String getQ_caseNo(){ return checkGet(q_caseNo); }
public void setQ_caseNo(String s){ q_caseNo=checkSet(s); }
public String getQ_propertyNo(){ return checkGet(q_propertyNo); }
public void setQ_propertyNo(String s){ q_propertyNo=checkSet(s); }
public String getQ_serialNo(){ return checkGet(q_serialNo); }
public void setQ_serialNo(String s){ q_serialNo=checkSet(s); }
public String getQ_lotNo(){ return checkGet(q_lotNo); }
public void setQ_lotNo(String s){ q_lotNo=checkSet(s); }
String q_chengClass;
public String getQ_chengClass(){ return checkGet(q_chengClass); }
public void setQ_chengClass(String s){ q_chengClass=checkSet(s); }

//提供修改欄位
String caseName;
String writeDate;
String writeUnit;
String proofDoc;
String proofNo;
String manageNo;
String summonsNo;
String unusableCause;
String demolishDate;
String reduceDate;
String approveOrg;
String approveDate;
String approveDoc;
String verify;
String notes;
String closing;
public String getCaseName(){ return checkGet(caseName); }	public void setCaseName(String s){ caseName=checkSet(s); }
public String getWriteDate(){ return checkGet(writeDate); }	public void setWriteDate(String s){ writeDate=checkSet(s); }
public String getWriteUnit(){ return checkGet(writeUnit); }	public void setWriteUnit(String s){ writeUnit=checkSet(s); }
public String getProofDoc(){ return checkGet(proofDoc); }	public void setProofDoc(String s){ proofDoc=checkSet(s); }
public String getProofNo(){ return checkGet(proofNo); }	public void setProofNo(String s){ proofNo=checkSet(s); }
public String getManageNo(){ return checkGet(manageNo); }	public void setManageNo(String s){ manageNo=checkSet(s); }
public String getSummonsNo(){ return checkGet(summonsNo); }	public void setSummonsNo(String s){ summonsNo=checkSet(s); }
public String getUnusableCause(){ return checkGet(unusableCause); }	public void setUnusableCause(String s){ unusableCause=checkSet(s); }
public String getDemolishDate(){ return checkGet(demolishDate); }	public void setDemolishDate(String s){ demolishDate=checkSet(s); }
public String getReduceDate(){ return checkGet(reduceDate); }	public void setReduceDate(String s){ reduceDate=checkSet(s); }
public String getApproveOrg(){ return checkGet(approveOrg); }	public void setApproveOrg(String s){ approveOrg=checkSet(s); }
public String getApproveDate(){ return checkGet(approveDate); }	public void setApproveDate(String s){ approveDate=checkSet(s); }
public String getApproveDoc(){ return checkGet(approveDoc); }	public void setApproveDoc(String s){ approveDoc=checkSet(s); }
public String getVerify(){ return checkGet(verify); }	public void setVerify(String s){ verify=checkSet(s); }
public String getNotes(){ return checkGet(notes); }	public void setNotes(String s){ notes=checkSet(s); }
public String getClosing(){ return checkGet(closing); }	public void setClosing(String s){ closing=checkSet(s); }

//傳回update sql
protected String[] getUpdateSQL(){
	String[] execSQLArray = new String[1];
	execSQLArray[0] = " update UNTRF_ReduceProof set "
					+ " caseName= " +  Common.sqlChar(caseName) + ","
					+ " writeDate= " +  Common.sqlChar(writeDate) + ","
					+ " writeUnit= " +  Common.sqlChar(writeUnit) + ","
					+ " proofDoc= " +  Common.sqlChar(proofDoc) + ","
					+ " proofNo= " +  Common.sqlChar(proofNo) + ","
					+ " manageNo= " +  Common.sqlChar(manageNo) + ","
					+ " summonsNo= " +  Common.sqlChar(summonsNo) + ","
					+ " unusableCause= " +  Common.sqlChar(unusableCause) + ","
					+ " demolishDate= " +  Common.sqlChar(demolishDate) + ","
					+ " reduceDate= " +  Common.sqlChar(reduceDate) + ","
					+ " approveOrg= " +  Common.sqlChar(approveOrg) + ","
					+ " approveDate= " +  Common.sqlChar(approveDate) + ","
					+ " approveDoc= " +  Common.sqlChar(approveDoc) + ","
					+ " verify= " +  Common.sqlChar(verify) + ","
					+ " notes= " +  Common.sqlChar(notes) + ","
					+ " closing= " +  Common.sqlChar(closing) + ","
					+ " editID = " + Common.sqlChar(getEditID()) + ","
					+ " editDate = " + Common.sqlChar(getEditDate()) + ","
					+ " editTime = " + Common.sqlChar(getEditTime())
					+ " where 1=1 " 
			   		+ " and enterOrg = " + Common.sqlChar(enterOrg)
			   		+ " and ownership = " + Common.sqlChar(ownership)
			   		+ " and caseNo = " + Common.sqlChar(caseNo)
					+ "";
	//System.out.println(execSQLArray[0]);
	return execSQLArray;
}

/**
 * <br>
 * <br>目的：依主鍵查詢單一資料
 * <br>參數：無
 * <br>傳回：傳回本物件
*/

public UNTDU027F queryOne() throws Exception{
	Database db = new Database();
	UNTDU027F obj = this;
	try {
		String sql = " select a.enterOrg ,o.organsname as enterOrgName ,a.ownership ,a.caseNo " + "\n"
				   + " ,caseName ,writeDate ,writeUnit ,proofDoc ,proofNo" + "\n"
				   + " ,manageNo ,summonsNo ,unusableCause ,demolishDate" + "\n"
				   + " ,reduceDate ,approveOrg ,approveDate ,approveDoc" + "\n"
				   + " ,verify ,notes ,closing" + "\n"
		   		   + " 		  ,a.editid ,a.editDate ,a.editTime " + "\n"
		   		   + " from UNTRF_ReduceProof a ,sysca_organ o " + "\n"
		   		   + " where 1=1 " + "\n"
		   		   + " and a.enterorg=o.organid " + "\n" 
		   		   + " and a.enterOrg = " + Common.sqlChar(enterOrg)
		   		   + " and a.ownership = " + Common.sqlChar(ownership)
		   		   + " and a.caseNo = " + Common.sqlChar(caseNo)
		   		   + "";

		//System.out.println(sql);
		ResultSet rs = db.querySQL(sql);
		if (rs.next()){
			
			obj.setEnterOrg(rs.getString("enterOrg"));
			obj.setEnterOrgName(rs.getString("enterOrgName"));
			obj.setOwnership(rs.getString("ownership"));
			obj.setCaseNo(rs.getString("caseNo"));
			
			/* 修改資料區 */
			obj.setCaseName(rs.getString("caseName"));
			obj.setWriteDate(rs.getString("writeDate"));
			obj.setWriteUnit(rs.getString("writeUnit"));
			obj.setProofDoc(rs.getString("proofDoc"));
			obj.setProofNo(rs.getString("proofNo"));
			obj.setManageNo(rs.getString("manageNo"));
			obj.setSummonsNo(rs.getString("summonsNo"));
			obj.setUnusableCause(rs.getString("unusableCause"));
			obj.setDemolishDate(rs.getString("demolishDate"));
			obj.setReduceDate(rs.getString("reduceDate"));
			obj.setApproveOrg(rs.getString("approveOrg"));
			obj.setApproveDate(rs.getString("approveDate"));
			obj.setApproveDoc(rs.getString("approveDoc"));
			obj.setVerify(rs.getString("verify"));
			obj.setNotes(rs.getString("notes"));
			obj.setClosing(rs.getString("closing"));
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
	try {
		String sql = " select a.enterOrg ,o.organsname as enterOrgName ,a.ownership ,a.caseno " + "\n"
				   + "		  ,(select x.codeName from sysca_code x where a.ownership = x.codeid and x.codekindid = 'OWA') as ownershipName "
				   + " from UNTRF_ReduceProof a ,sysca_organ o " + "\n"
				   + " where 1=1 " + "\n"
				   + " and a.enterorg=o.organid " + "\n" ;
				   if (!"".equals(getQ_enterOrg()))
				   {	sql += " and a.enterOrg = " + Common.sqlChar(getQ_enterOrg()) ;	}
				   if (!"".equals(getQ_ownership()))
				   {	sql += " and a.ownership = " + Common.sqlChar(getQ_ownership()) ;	}
				   if (!"".equals(getQ_caseNo()))
				   {	sql += " and a.caseNo = " + Common.sqlChar(getQ_caseNo()) ;		}

				sql += " order by enterorg ,ownership ,caseno";
			
		//System.out.println(sql);
		ResultSet rs = db.querySQL(sql,true);
		processCurrentPageAttribute(rs);
		if (getTotalRecord() > 0) {
			int count = getRecordStart();
			int end = getRecordEnd();
			do {
				if (count > end) break;
			String rowArray[]=new String[5];
			rowArray[0]=Common.get(rs.getString("enterOrg")); 
			rowArray[1]=Common.get(rs.getString("enterOrgName")); 
			rowArray[2]=Common.get(rs.getString("ownership"));
			rowArray[3]=Common.get(rs.getString("ownershipName"));
			rowArray[4]=Common.get(rs.getString("caseno"));
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
	return objList;
}

}
