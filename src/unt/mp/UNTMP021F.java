/*
*<br>程式目的：動產廢品處理作業－處理單資料
*<br>程式代號：untmp021f
*<br>程式日期：0941201
*<br>程式作者：cherry
*<br>--------------------------------------------------------
*<br>修改作者　　修改日期　　　修改目的
*<br>--------------------------------------------------------
*/

package unt.mp;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Map;

import unt.ch.UNTCH_Tools;
import util.*;

public class UNTMP021F extends UNTMP021Q{


String enterOrg;
String enterOrgName;
String ownership;
String caseNo1;
String caseName;
String writeDate;
String writeUnit;
String proofDoc;
String proofYear;
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
public String getProofYear(){ return checkGet(proofYear); }
public void setProofYear(String s){ proofYear=checkSet(s); }
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
public String getVerify(){ return checkGet(verify); }
public void setVerify(String s){ verify=checkSet(s); }
public String getNotes(){ return checkGet(notes); }
public void setNotes(String s){ notes=checkSet(s); }
public String getCheckEnterOrg(){ return checkGet(checkEnterOrg); }
public void setCheckEnterOrg(String s){ checkEnterOrg=checkSet(s); }
public String getDifferenceKind() {	return checkGet(differenceKind);}
public void setDifferenceKind(String differenceKind) {	this.differenceKind = checkSet(differenceKind);}

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
	//	select substring(CONVERT(varchar, max(substring(caseno1,8,3)+1001)),2,3) as caseno1 
	String sql="select substring(cast(max(substring(caseno1,8,3))+10000001 AS varchar),2,7) as caseNo1 from UNTMP_DEALPROOF " +
	" where enterorg = " + Common.sqlChar(enterOrg) +
	" and substring(caseno1,1,3) = " + Common.sqlChar(Datetime.getYYY()) + 
	"";		
try {		
	rs = db.querySQL(sql);
	if (rs.next()){
		if (rs.getString("caseNo1")==null)
			setCaseNo1(Datetime.getYYYMMDD().substring(0,3)+"0000001");
		else
		    setCaseNo1(Datetime.getYYYMMDD().substring(0,3)+rs.getString("caseNo1"));
	} else {
		setCaseNo1(Datetime.getYYYMMDD().substring(0,3)+"0000001");
	}
	} catch (Exception e) {
		e.printStackTrace();
	} finally {
		db.closeAll();
	}
	//取得處理單編號－號
	setProofNo(MaxClosingYM.getMaxProofNo("UNTMP_DEALPROOF",enterOrg,ownership,this.getProofYear()));
	//===================
	String[][] checkSQLArray = new String[1][4];
 	checkSQLArray[0][0]=" select count(*) as checkresult from UNTMP_DEALPROOF where 1=1 " + 
		" and enterorg = " + Common.sqlChar(enterOrg) + 
		" and caseno1 = " + Common.sqlChar(caseNo1) + 
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
	execSQLArray[0]=" insert into UNTMP_DEALPROOF(" +
			" enterorg,"+
			" differencekind,"+
			" caseno1,"+
			" casename,"+
			" writedate,"+
			" writeunit,"+
			" proofyear,"+
			" proofdoc,"+
			" proofno,"+
			" manageno,"+
			" dealdate,"+
			" reducedeal,"+
			" realizevalue,"+
			" shiftorg,"+
			" verify,"+
			" notes,"+
			" editid,"+
			" editdate,"+
			" edittime"+
		" )Values(" +
			Common.sqlChar(enterOrg) + "," +
			Common.sqlChar(differenceKind) + "," +
			Common.sqlChar(caseNo1) + "," +
			Common.sqlChar(caseName) + "," +
			Common.sqlChar(ul._transToCE_Year(writeDate)) + "," +
			Common.sqlChar(writeUnit) + "," +
			Common.sqlChar(proofYear) + "," +
			Common.sqlChar(proofDoc) + "," +
			Common.sqlChar(proofNo) + "," +
			Common.sqlChar(manageNo) + "," +
			Common.sqlChar(ul._transToCE_Year(dealDate)) + "," +
			Common.sqlChar(reduceDeal) + "," +
			Common.sqlChar(realizeValue) + "," +
			Common.sqlChar(shiftOrg) + "," +
			Common.sqlChar(verify) + "," +
			Common.sqlChar(notes) + "," +
			Common.sqlChar(getEditID()) + "," +
			Common.sqlChar(ul._transToCE_Year(getEditDate())) + "," +
			Common.sqlChar(getEditTime()) + ")" ;
	return execSQLArray;
}

protected void getUpdateCheck(){
    
}
//傳回執行update前之檢查sql
protected String[][] getUpdateCheckSQL(){
	if ("Y".equals(getVerify())) {
		String[][] checkSQLArray = new String[2][4];
		//該筆處理單之明細資料標籤要有資料才能做已審核設定
	 	checkSQLArray[0][0]=" select count(*) as checkresult from UNTMP_DEALDETAIL where 1=1 " + 
							" and enterorg = " + Common.sqlChar(enterOrg) + 
							" and differencekind = " + Common.sqlChar(differenceKind) + 
							" and caseno1 = " + Common.sqlChar(caseNo1) + 
							"";
		checkSQLArray[0][1]="<=";
		checkSQLArray[0][2]="0";
		checkSQLArray[0][3]="該筆處理單之明細資料標籤要有資料才能做此審核設定！";
		
	 	checkSQLArray[1][0]=" select max(reduceDate) as checkresult from UNTMP_DEALDETAIL where 1=1 " + 
	 						" and enterorg = " + Common.sqlChar(enterOrg) +
	 						" and differencekind = " + Common.sqlChar(ownership) +
	 						" and caseno1 = " + Common.sqlChar(caseNo1) +
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
		String sql = "select proofyear from UNTMP_DEALPROOF where enterorg=" + Common.sqlChar(enterorg) +
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
		strSQLArray = " update UNTMP_DEALPROOF set " +
						" casename = " + Common.sqlChar(caseName) + "," +
						" writedate = " + Common.sqlChar(ul._transToCE_Year(writeDate)) + "," +
						" differencekind = " + Common.sqlChar(getDifferenceKind()) + "," +
						" writeunit = " + Common.sqlChar(writeUnit) + "," +
						" proofyear = " + Common.sqlChar(proofYear) + "," +
						" proofdoc = " + Common.sqlChar(proofDoc) + "," +
						" proofno = " + Common.sqlChar(proofNo) + "," +
						" manageno = " + Common.sqlChar(manageNo) + "," +
						" dealdate = " + Common.sqlChar(ul._transToCE_Year(dealDate)) + "," +
						" reducedeal = " + Common.sqlChar(reduceDeal) + "," +
						" realizevalue = " + Common.getInteger(realizeValue) + "," +
						" shiftorg = " + Common.sqlChar(shiftOrg) + "," +
						" verify = " + Common.sqlChar(verify) + "," +
						" notes = " + Common.sqlChar(notes) + "," +
						" editid = " + Common.sqlChar(getEditID()) + "," +
						" editdate = " + Common.sqlChar(ul._transToCE_Year(getEditDate())) + "," +
						" edittime = " + Common.sqlChar(getEditTime()) + 
					" where 1=1 " + 
						" and enterorg = " + Common.sqlChar(enterOrg) +
						" and caseno1 = " + Common.sqlChar(caseNo1) +
						":;:";

		//問題單1290補充: update時更改編號-年，重新取得編號-號
		if(this.checkProofyearChanged(getEnterOrg(), getCaseNo1(), getProofYear())){
			String newproofno = MaxClosingYM.getMaxProofNo("UNTMP_DEALPROOF",enterOrg,ownership,this.getProofYear());
			strSQLArray += "update UNTMP_DEALPROOF set proofno="+Common.sqlChar(newproofno) +
										" where 1=1 " +
										" and enterorg = " + Common.sqlChar(enterOrg) +
										" and caseno1 = " + Common.sqlChar(caseNo1) +
										":;:";
		}
		
	    strSQLArray += "update UNTMP_DEALDETAIL set " +
						" dealdate = " + Common.sqlChar(dealDate) + 
					" where 1=1 " + 
						" and enterorg = " + Common.sqlChar(enterOrg) +
						" and caseno1 = " + Common.sqlChar(caseNo1) +
						":;:";
	    
	    if(getVerify().equals("Y")){
	        strSQLArray += checkVerify();
	    }
		if (!"Y".equals(getVerifyError())) {
			System.out.println("123132132==>"+strSQLArray.toString());
			execSQLArray = strSQLArray.split(":;:");
			
			db.exeSQL_NoChange(execSQLArray);
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
	execSQLArray[0]=" delete UNTMP_DEALPROOF where 1=1 " +
			" and enterorg = " + Common.sqlChar(enterOrg) +
			" and caseno1 = " + Common.sqlChar(caseNo1) +
			"";
	execSQLArray[1]=" delete UNTMP_DEALDETAIL where 1=1 " +
			" and enterorg = " + Common.sqlChar(enterOrg) +
			" and caseno1 = " + Common.sqlChar(caseNo1) +
			"";
	return execSQLArray;
}


/**
 * <br>
 * <br>目的：依主鍵查詢單一資料
 * <br>參數：無
 * <br>傳回：傳回本物件
*/

public UNTMP021F  queryOne() throws Exception{
	UNTCH_Tools ul = new UNTCH_Tools();
	Database db = new Database();
	UNTMP021F obj = this;
	try {
		String sql=" select a.enterorg, b.organsname as enterorgname, a.differencekind,a.proofyear, a.caseno1, a.casename, a.writedate, a.writeunit, a.proofdoc, a.proofno, a.manageno, a.dealdate, a.reducedeal," +
					" (SELECT sum(realizevalue1) FROM  UNTMP_DEALDETAIL  WHERE a.enterorg = enterorg AND a.caseno1 = caseno1 ) as realizevalue, " +
					" a.shiftorg, (select c.organsname from SYSCA_ORGAN c where 1=1 and a.shiftorg = c.organid) as shiftorgname, " +
					" a.verify, a.notes, a.editid, a.editdate, a.edittime  "+
					" from UNTMP_DEALPROOF a, SYSCA_ORGAN b where 1=1 " +
					" and a.enterorg=b.organid " +
					" and a.enterorg = " + Common.sqlChar(enterOrg) +
					" and a.caseno1 = " + Common.sqlChar(caseNo1) +
					"";
//System.out.println("queryOne_sql"+sql);
		ResultSet rs = db.querySQL_NoChange(sql);
		if (rs.next()){
			obj.setEnterOrg(rs.getString("enterorg"));
			obj.setDifferenceKind(rs.getString("differencekind"));
			obj.setEnterOrgName(rs.getString("enterorgname"));
			obj.setCaseNo1(rs.getString("caseno1"));
			obj.setCaseName(rs.getString("casename"));
			obj.setWriteDate(ul._transToROC_Year(rs.getString("writedate")));
			obj.setWriteUnit(rs.getString("writeunit"));
			obj.setProofYear(rs.getString("proofyear"));
			obj.setProofDoc(rs.getString("proofdoc"));
			obj.setProofNo(rs.getString("proofno"));
			obj.setManageNo(rs.getString("manageno"));
			obj.setDealDate(ul._transToROC_Year(rs.getString("dealdate")));
			obj.setReduceDeal(rs.getString("reducedeal"));
			obj.setRealizeValue(rs.getString("realizevalue"));
			obj.setShiftOrg(rs.getString("shiftorg"));
			obj.setShiftOrgName(rs.getString("shiftorgname"));
			obj.setVerify(rs.getString("verify"));
			obj.setNotes(rs.getString("notes"));
			obj.setEditID(rs.getString("editid"));
			obj.setEditDate(ul._transToROC_Year(rs.getString("editdate")));
			obj.setEditTime(rs.getString("edittime"));
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
	UNTCH_Tools ul = new UNTCH_Tools();
	Database db = new Database();
	ArrayList objList=new ArrayList();
	Map<String,String> differencekindMap = TCGHCommon.getSysca_Code("DFK"); 					//財產區分別名稱
	int counter=0;
	try {
		String sql="select * from (" +
					" select distinct a.enterorg, a.differencekind, " +
					" a.caseno1, a.casename, a.writedate, a.dealdate, " +
					" a.proofyear, a.proofdoc, a.proofno," +
					" a.reducedeal, (select c.codename from  SYSCA_CODE c where c.codekindid='RDL'  and a.reducedeal = c.codeid) as reduceDealname, (SELECT sum(realizevalue1) FROM  UNTMP_DEALDETAIL  WHERE a.enterorg = enterorg AND a.caseno1 = caseno1 ) as realizevalue, " +
					" a.shiftorg, (select b.organsname from SYSCA_ORGAN b where a.shiftorg=b.organid ) as shiftorgname, case a.verify when 'Y' then '是' when 'N' then '否' else '' end verifyname " +
					" from UNTMP_DEALPROOF a "+
					" left join UNTMP_DEALDETAIL d on a.enterorg=d.enterorg  and a.caseno1=d.caseno1"+
					"  where 1=1 ";
		
		   
		if ("dealDetail".equals(getQuerySelect()) || "".equals(getQuerySelect())) {
			sql+=" and a.enterorg = " + Common.sqlChar(getEnterOrg()) ;
		    sql+=" and a.differencekind = " + Common.sqlChar(getDifferenceKind()) ;
			sql+=" and a.caseno1=" + Common.sqlChar(getCaseNo1());
		} else {
			if (!"".equals(getQ_enterOrg())) {
				sql+=" and a.enterorg = " + Common.sqlChar(getQ_enterOrg()) ;
			} else {
				if (!getIsAdminManager().equalsIgnoreCase("Y")) {
					if ("Y".equals(getIsOrganManager())) {					
						sql += " and a.enterorg like '" + getOrganID().substring(0,5) + "%' ";					
					} else {
						sql+=" and a.enterorg = " + Common.sqlChar(getOrganID()) ;
					}
				}
			}
			if (!"".equals(getQ_differenceKind()))
				sql+=" and a.differencekind = " + Common.sqlChar(getQ_differenceKind()) ;
			if (!"".equals(getQ_caseNo1S()))
				sql+=" and a.caseno1 >= " + Common.sqlChar(Common.formatRearString(getQ_caseNo1S(),10,'0'));		
			if (!"".equals(getQ_caseNo1E()))
				sql+=" and a.caseno1<=" + Common.sqlChar(Common.formatRearString(getQ_caseNo1E(),10, '9'));
			if (!"".equals(getQ_dealDateS()))
				sql+=" and a.dealdate >= " + Common.sqlChar(new Datetime().changeTaiwanYYMMDD(getQ_dealDateS(),"2"));
			if (!"".equals(getQ_dealDateE()))
				sql+=" and a.dealdate <= " + Common.sqlChar(new Datetime().changeTaiwanYYMMDD(getQ_dealDateE(),"2"));
			if (!"".equals(getQ_verify()))
				sql+=" and a.verify  = " + Common.sqlChar(getQ_verify()) ;
			if (!"".equals(getQ_caseName()))
				sql+=" and a.casename  like " + Common.sqlChar(getQ_caseName()+"%") ;   	    
			if (!"".equals(getQ_writeDateS()))
				sql+=" and a.writedate >= " + Common.sqlChar(new Datetime().changeTaiwanYYMMDD(getQ_writeDateS(),"2"));		
			if (!"".equals(getQ_writeDateE()))
				sql+=" and a.writedate <= " + Common.sqlChar(new Datetime().changeTaiwanYYMMDD(getQ_writeDateE(),"2"));   	    
			if (!"".equals(getQ_proofDoc()))
				sql+=" and a.proofdoc like '%" + getQ_proofDoc() + "%'" ;
			if (!"".equals(getQ_proofNoS())) 
				sql+=" and a.proofno >= " + Common.sqlChar(getQ_proofNoS());		
			if (!"".equals(getQ_proofNoE())) 
				sql+=" and a.proofno <= " + Common.sqlChar(getQ_proofNoE());		 
			if (!"".equals(getQ_reduceDeal()))
				sql+=" and a.reducedeal = " + Common.sqlChar(getQ_reduceDeal()) ;
			if (!"".equals(getQ_shiftOrg()))
				sql+=" and a.shiftorg = " + Common.sqlChar(getQ_shiftOrg()) ;
			if (!"".equals(getQ_propertyNoS()))
				sql+=" and d.propertyno >= " + Common.sqlChar(getQ_propertyNoS());		
			if (!"".equals(getQ_propertyNoE()))
				sql+=" and d.propertyno <= " + Common.sqlChar(getQ_propertyNoE());						
			if (!"".equals(getQ_serialNoS()))
				sql+=" and d.serialno >= " + Common.sqlChar(getQ_serialNoS());		
			if (!"".equals(getQ_serialNoE()))
				sql+=" and d.serialno <= " + Common.sqlChar(getQ_serialNoE());			
			if (!"".equals(getQ_propertyKind()))
				sql+=" and d.propertykind = " + Common.sqlChar(getQ_propertyKind()) ;
			if (!"".equals(getQ_fundType()))
				sql+=" and d.fundtype = " + Common.sqlChar(getQ_fundType()) ;
			if (!"".equals(getQ_valuable()))
				sql+=" and d.valuable = " + Common.sqlChar(getQ_valuable()) ;
			if (!"".equals(getQ_proofYear()))
				sql+=" and a.proofyear = " + Common.sqlChar(getQ_proofYear()) ;
		}
		sql+= " ) a order by cast(a.proofyear as int) desc, a.proofno desc";
//System.out.println("queryAll="+sql);
		ResultSet rs = db.querySQL(sql.toString(),true);
		processCurrentPageAttribute(rs);	
		if (getTotalRecord() > 0) {
			int count = getRecordStart();
			int end = getRecordEnd();
			do {
				if (count > end)
					break;
			String rowArray[]=new String[14];
			rowArray[0]=rs.getString("enterorg"); 
			rowArray[1]=differencekindMap.get(rs.getString("differencekind"));
			rowArray[2]=rs.getString("caseno1"); 
			rowArray[3]=Common.get(rs.getString("proofYear")) + "年" +
						Common.get(rs.getString("proofDoc")) + "字第" + 
						Common.get(rs.getString("proofNo")) + "號"; 
			rowArray[4]=ul._transToROC_Year(rs.getString("writedate")); 
			rowArray[5]=ul._transToROC_Year(rs.getString("dealdate")); 
			rowArray[6]=rs.getString("reducedealname"); 
			rowArray[7]=rs.getString("realizevalue"); 
			rowArray[8]=rs.getString("shiftorgname"); 
			rowArray[9]=rs.getString("verifyname"); 
			rowArray[10]=rs.getString("differencekind");
			rowArray[11]=rs.getString("reducedeal");
			rowArray[12]=rs.getString("realizevalue");
			rowArray[13]=rs.getString("shiftorg");
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


//全部審核
public void approveAll()throws Exception{	
    //UNTMP021F obj = this;
	Database db = new Database();
	UNTCH_Tools ul = new UNTCH_Tools();
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
				differenceKind= rowArray[10];
				caseNo1 = rowArray[2];
				setDealDate(rowArray[5]==null || "".equals(rowArray[5]) ? Datetime.getYYYMMDD():rowArray[5]);
				reduceDeal = rowArray[11];
				realizeValue = rowArray[12];
				shiftOrg = rowArray[13];
				setEditID(getUserID());
				setEditDate(Datetime.getYYYYMMDD());
				setEditTime(Datetime.getHHMMSS());		
				if(enterOrg.equals(checkEnterOrg)){
				    strSQL += "update UNTMP_DEALPROOF set "+
				    		" verify = 'Y'," +
				    		" dealdate = " + Common.sqlChar(ul._transToCE_Year(getDealDate())) + "," +
							" editid = " + Common.sqlChar(getEditID()) + "," +
							" editdate = " + Common.sqlChar(ul._transToCE_Year(getEditDate())) + "," +
							" edittime = " + Common.sqlChar(getEditTime()) + 	                
							" where 1=1 " + 
							" and enterorg = " + Common.sqlChar(enterOrg) +
							" and differencekind = " + Common.sqlChar(differenceKind) +
							" and caseno1 = " + Common.sqlChar(caseNo1) +
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
				db.exeSQL_NoChange(execSQLArray);
				//setVerify("N");
				//setClosing("N");
				//setOldVerify("N");
				setStateUpdateSuccess();
				setErrorMsg("全部審核完成");				
			} else {			   
	           //setVerifyError("Y");
	           setStateUpdateSuccess();
	           queryOne();
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
    UNTMP021F obj = this;
	Database db = new Database();
	ResultSet rs;	
	String maxReduceDate ="" ;
	String sql="select max(reducedate) as reducedate from UNTMP_DEALDETAIL " +
		" where 1=1" +
		" and enterorg = " + Common.sqlChar(enterOrg) +
		" and differencekind = " + Common.sqlChar(differenceKind) +
		" and caseno1 = " + Common.sqlChar(caseNo1) +
		"";		
	try {		
		rs = db.querySQL_NoChange(sql);
		if (rs.next()){
		    if(rs.getString("reducedate")==null){
		        obj.setCheckReduceDate("N");
		        maxReduceDate = "99999999";
		    }else{
		        maxReduceDate = rs.getString("reducedate");
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
	        
	        //找出enterOrg, ownership, propertyNo, serialNo, caseNo等Key 值去更新UNTMP_ReduceDetail
	        sql = " select b.enterorg, b.differencekind, b.caseno, b.propertyno, b.serialno, " + "\n"
	        	+ " isnull(isnull(b.realizevalue1 ,a.realizevalue) ,0) as realizevalue " + "\n"
	        	+ " from UNTMP_DEALPROOF a ,UNTMP_DEALDETAIL b " + "\n"
	        	+ " where 1=1 " + "\n"
	        	+ " and a.enterorg=b.enterorg " + "\n"
	        	+ " and a.differencekind=b.differencekind " + "\n"
	        	+ " and a.caseno1=b.caseno1 " + "\n"
	        	+ " and b.enterorg = " + Common.sqlChar(enterOrg) + "\n"
	        	+ " and b.differencekind = " + Common.sqlChar(differenceKind) + "\n"
	        	+ " and b.caseno1 = " + Common.sqlChar(caseNo1);
	        rs = db.querySQL_NoChange(sql);
	        while (rs.next()) {
	        	 checkDetail="Y";
	        	strSQL += "update UNTMP_DEALDETAIL set verify='Y'," +
    					  " dealdate=" + Common.sqlChar(ul._transToCE_Year(getDealDate())) +
    					  " where 1=1 " +
    					  " and enterorg = " + Common.sqlChar(enterOrg) +
    					  " and differencekind = " + Common.sqlChar(differenceKind) +
    					  " and caseno1 = " + Common.sqlChar(caseNo1) +
    					  ":;:";
	            strSQL += "update UNTMP_REDUCEDETAIL set " +
	            			" dealcaseno= " + Common.sqlChar(caseNo1) + "," +
							" dealdate = " + Common.sqlChar(ul._transToCE_Year(getDealDate())) + "," +
							" reducedeal = "  + Common.sqlChar(reduceDeal) + "," +
							" realizevalue = "  + Common.sqlChar(rs.getString("realizevalue")) + "," +
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
	String differenceKindQuery = "";
	String caseNoQuery = "";
	String caseNo1Query = "";
	String propertyNoQuery = "";
	String serialNoQuery = "";
	int count = 0;
	try {    
		String[] execSQLArray;
		String strSQL = "";
		String sql ="select a.caseno1, b.caseno, b.enterorg, b.differencekind, b.propertyno, b.serialno " +
					" from UNTMP_DEALPROOF a, UNTMP_DEALDETAIL b " +
					" where 1=1 " +
					" and a.enterorg = b.enterorg and a.differencekind = b.differencekind and a.caseno1 = b.caseno1" +
	    			" and a.enterorg = " + Common.sqlChar(enterOrg) +
	    			" and a.differencekind = " + Common.sqlChar(differenceKind) +
	    			" and a.caseno1 = " + Common.sqlChar(caseNo1) +
					" order by b.enterorg, b.differencekind, b.caseno1, b.propertyno, b.serialno" +
					"" ;
//System.out.println("sql="+sql);		
		ResultSet rs = db.querySQL_NoChange(sql);
		while (rs.next()){
			count++;
			enterOrgQuery = rs.getString("enterorg");
			differenceKindQuery = rs.getString("differenceKind");
			caseNo1Query = rs.getString("caseno1");
			caseNoQuery = rs.getString("caseno");
			propertyNoQuery = rs.getString("propertyno");
			serialNoQuery = rs.getString("serialno");
			//------------------------------------------------------------------------------------------------
//System.out.println("verify="+verify);
			if(verify.equals("Y")){
				//依據該處理單「入帳機關enterOrg＋權屬differenceKind＋電腦單號caseNo」設定
				System.out.println("count="+count);
				if(count==1){
					
					strSQL += "update UNTMP_DEALPROOF set verify ='N',dealdate = null, realizevalue = null " +
							" where enterorg=" + Common.sqlChar(enterOrgQuery) +
							" and differencekind=" + Common.sqlChar(differenceKindQuery) +
							" and caseno1=" + Common.sqlChar(caseNo1Query) +
							":;:";				
					strSQL += "update UNTMP_DEALDETAIL set verify ='N',dealdate = null " +
							" where enterorg=" + Common.sqlChar(enterOrgQuery) +
							" and differencekind=" + Common.sqlChar(differenceKindQuery) +
							" and caseno1=" + Common.sqlChar(caseNo1Query) +
							":;:";				
				}
				
				//依據處理單明細「入帳機關enterOrg＋權屬ownership＋減損單電腦單號caseNo＋財產編號propertyNo＋財產分號serialNo」設定
				strSQL +="update UNTMP_REDUCEDETAIL set " +
						" dealcaseno = null, " +
						" dealdate = null, " +
						" reducedeal = null, " +
						" realizevalue = null, " +
						" shiftorg = null " +
						" where enterorg=" + Common.sqlChar(enterOrgQuery) +
						" and differencekind=" + Common.sqlChar(differenceKindQuery) +
						" and caseno=" + Common.sqlChar(caseNoQuery) +
						" and propertyno=" + Common.sqlChar(propertyNoQuery) +
						" and serialno=" + Common.sqlChar(serialNoQuery) +
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
			db.exeSQL_NoChange(execSQLArray);
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

}


