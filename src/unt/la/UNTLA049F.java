
/*
*<br>程式目的：使用人資料
*<br>程式代號：npbgr003f
*<br>程式日期：0950207
*<br>程式作者：clive.chang
*<br>--------------------------------------------------------
*<br>修改作者　　修改日期　　　修改目的
*<br>--------------------------------------------------------
*/

package unt.la;

import java.sql.ResultSet;
import java.util.ArrayList;

import util.Common;
import util.*;

public class UNTLA049F extends QueryBean{

String applyType;
String payerYN;
String registryYN;
String applyID;
String seq;
String checkSeq;
String checkApplyID;
String oldApplyID;
String applyName;
String homeTel;
String mobile;
String zipcode;
String liveAdd1;
String liveAdd2;
String liveAdd3;
String liveAdd4;
String notes;
String caseNo;
String isDefault;
String enterOrg;
String enterOrgName;
String ownership;
String propertyNo;
String propertyNoName;
String serialNo;
String serialNo1;
String fakeDivision;

public String getFakeDivision(){ return checkGet(fakeDivision); }
public void setFakeDivision(String s){ fakeDivision=checkSet(s); }
public String getEnterOrg(){ return checkGet(enterOrg); }
public void setEnterOrg(String s){ enterOrg=checkSet(s); }
public String getEnterOrgName(){ return checkGet(enterOrgName); }
public void setEnterOrgName(String s){ enterOrgName=checkSet(s); }
public String getOwnership(){ return checkGet(ownership); }
public void setOwnership(String s){ ownership=checkSet(s); }
public String getPropertyNo(){ return checkGet(propertyNo); }
public void setPropertyNo(String s){ propertyNo=checkSet(s); }
public String getPropertyNoName(){ return checkGet(propertyNoName); }
public void setPropertyNoName(String s){ propertyNoName=checkSet(s); }
public String getSerialNo(){ return checkGet(serialNo); }
public void setSerialNo(String s){ serialNo=checkSet(s); }
public String getSerialNo1(){ return checkGet(serialNo1); }
public void setSerialNo1(String s){ serialNo1=checkSet(s); }
public String getApplyType(){ return checkGet(applyType); }
public void setApplyType(String s){ applyType=checkSet(s); }
public String getPayerYN(){ return checkGet(payerYN); }
public void setPayerYN(String s){ payerYN=checkSet(s); }
public String getRegistryYN(){ return checkGet(registryYN); }
public void setRegistryYN(String s){ registryYN=checkSet(s); }
public String getApplyID(){ return checkGet(applyID); }
public void setApplyID(String s){ applyID=checkSet(s); }
public String getSeq(){ return checkGet(seq); }
public void setSeq(String s){ seq=checkSet(s); }
public String getCheckSeq(){ return checkGet(checkSeq); }
public void setCheckSeq(String s){ checkSeq=checkSet(s); }
public String getOldApplyID(){ return checkGet(oldApplyID); }
public void setOldApplyID(String s){ oldApplyID=checkSet(s); }
public String getCheckApplyID(){ return checkGet(checkApplyID); }
public void setCheckApplyID(String s){ checkApplyID=checkSet(s); }
public String getApplyName(){ return checkGet(applyName); }
public void setApplyName(String s){ applyName=checkSet(s); }
public String getHomeTel(){ return checkGet(homeTel); }
public void setHomeTel(String s){ homeTel=checkSet(s); }
public String getMobile(){ return checkGet(mobile); }
public void setMobile(String s){ mobile=checkSet(s); }
public String getZipcode(){ return checkGet(zipcode); }
public void setZipcode(String s){ zipcode=checkSet(s); }
public String getLiveAdd1(){ return checkGet(liveAdd1); }
public void setLiveAdd1(String s){ liveAdd1=checkSet(s); }
public String getLiveAdd2(){ return checkGet(liveAdd2); }
public void setLiveAdd2(String s){ liveAdd2=checkSet(s); }
public String getLiveAdd3(){ return checkGet(liveAdd3); }
public void setLiveAdd3(String s){ liveAdd3=checkSet(s); }
public String getLiveAdd4(){ return checkGet(liveAdd4); }
public void setLiveAdd4(String s){ liveAdd4=checkSet(s); }
public String getNotes(){ return checkGet(notes); }
public void setNotes(String s){ notes=checkSet(s); }
public String getCaseNo(){ return checkGet(caseNo); }
public void setCaseNo(String s){ caseNo=checkSet(s); }
String changeKey;
String seqNo;
public String getChangeKey(){ return checkGet(changeKey); }
public void setChangeKey(String s){ changeKey=checkSet(s); }
public String getSeqNo(){ return checkGet(seqNo); }
public void setSeqNo(String s){ seqNo=checkSet(s); }


//	傳回執行insert前之檢查sql
protected String[][] getInsertCheckSQL(){
	//取得案號
	Database db = new Database();
	ResultSet rs;
	try {		
		if ("".equals(getApplyID())) {		
			rs = db.querySQL("select max(applyID) + 1 as caseNo from NPBGR_Person where applyID like " + Common.sqlChar(Datetime.getYYYMMDD() + "%"));
			if (rs.next()){
				if (rs.getString("caseNo")==null)
					setApplyID(Datetime.getYYYMMDD()+"001");
				else
					setApplyID(Datetime.getYYYMMDD()+Common.formatFrontZero(rs.getString("caseNo").substring(7),3));
			} else {
				setApplyID(Datetime.getYYYMMDD()+"001");
			}
			rs.close();
		}
		
		rs = db.querySQL("select count(*) as checkResult from UNTLA_Person where payerYN='Y' "+
				" and enterOrg = " + Common.sqlChar(enterOrg) + 
				" and ownership = " + Common.sqlChar(ownership) + 
				" and propertyNo = " + Common.sqlChar(propertyNo) + 
				" and serialNo = " + Common.sqlChar(serialNo) + 
				" and serialNo1 = " + Common.sqlChar(serialNo1) + 
				"");
		if (rs.next()) {
			if (rs.getInt("checkResult")<=0) {
				setPayerYN("Y");
			}else {
				setPayerYN("N");
			}
		} 
		rs.close();
		
	} catch (Exception e) {
		e.printStackTrace();
	} finally {
		db.closeAll();
	}	
	
	String[][] checkSQLArray = new String[1][4];
 	checkSQLArray[0][0]=" select count(*) as checkResult from UNTLA_Person where 1=1 " + 
		" and applyID = " + Common.sqlChar(applyID) +
		" and seq = " + Common.sqlChar(seq) +
		" and enterOrg = " + Common.sqlChar(enterOrg) + 
		" and ownership = " + Common.sqlChar(ownership) + 
		" and propertyNo = " + Common.sqlChar(propertyNo) + 
		" and serialNo = " + Common.sqlChar(serialNo) + 
		" and serialNo1 = " + Common.sqlChar(serialNo1) + 		
		"";
//System.out.println(checkSQLArray[0][0].toString()); 	
	checkSQLArray[0][1]=">";
	checkSQLArray[0][2]="0";
	checkSQLArray[0][3]="該筆資料己重複，請重新輸入！";
	
	return checkSQLArray;
}

//	傳回insert sql
protected String[] getInsertSQL(){		

	StringBuffer sbSQL = new StringBuffer();
	Database db = new Database();
	int check=0;
	String maxSeq="";
	boolean checkCount=false;

	try {
		String sql_c="select count(*) as checkResult, max(seq)+1 as seq from NPBGR_Person where 1=1 "+ 
			" and applyID = " + Common.sqlChar(applyID) +
			"";		

		ResultSet rs = db.querySQL(sql_c);		
		if (rs.next()) {
			if(rs.getInt("checkResult")>0){	
				String sql_d="select count(*) as checkResult, max(seq)+1 as seq from NPBGR_Person where 1=1 "+ 
					" and applyID = " + Common.sqlChar(applyID) +
					" and seq = " + Common.sqlChar(seq) +
					" and applyName = " + Common.sqlChar(applyName) +
			 		" and (case liveAdd4 when null then 'n' else liveAdd4 end) = (case " + Common.sqlChar(liveAdd4) +" when null then 'n' else " + Common.sqlChar(liveAdd4) +" end)"	+
					"";				
				ResultSet rs_d = db.querySQL(sql_d);		
				if (rs_d.next()) {
					if(rs_d.getInt("checkResult")>0){	
						checkCount=false;
						maxSeq=seq;
					}else{
						maxSeq=Common.formatFrontZero(rs.getString("seq"),2);
						checkCount=true;
					}
				}
				
			}else{
				maxSeq="01";
				checkCount=true;
			}
		}	
		

		sbSQL.append(" insert into UNTLA_Person(" ).append(
			" enterOrg,").append(
			" ownership,").append(
			" propertyNo,").append(
			" serialNo,").append(
			" serialNo1,").append(
			" applyType,").append(
			" payerYN,").append(
			" registryYN,").append(
			" applyID,").append(
			" seq,").append(
			" applyName,").append(
			" homeTel,").append(
			" mobile,").append(
			" zipcode,").append(
			" liveAdd1,").append(
			" liveAdd2,").append(
			" liveAdd3,").append(
			" liveAdd4,").append(
			" notes,").append(
			" editID,").append(
			" editDate,").append(
			" editTime").append(
		" )Values(" ).append(
			Common.sqlChar(enterOrg) ).append( "," ).append(
			Common.sqlChar(ownership) ).append( "," ).append(
			Common.sqlChar(propertyNo) ).append( "," ).append(
			Common.sqlChar(serialNo) ).append( "," ).append(
			Common.sqlChar(serialNo1) ).append( "," ).append(		
			Common.sqlChar(applyType) ).append( "," ).append(
			Common.sqlChar(payerYN) ).append( "," ).append(
			Common.sqlChar(registryYN) ).append( "," ).append(
			Common.sqlChar(applyID) ).append( "," ).append(
			Common.sqlChar(maxSeq) ).append( "," ).append(
			Common.sqlChar(applyName) ).append( "," ).append(
			Common.sqlChar(homeTel) ).append( "," ).append(
			Common.sqlChar(mobile) ).append( "," ).append(
			Common.sqlChar(zipcode) ).append( "," ).append(
			Common.sqlChar(liveAdd1) ).append( "," ).append(
			Common.sqlChar(liveAdd2) ).append( "," ).append(
			Common.sqlChar(liveAdd3) ).append( "," ).append(
			Common.sqlChar(liveAdd4) ).append( "," ).append(
			Common.sqlChar(notes) ).append( "," ).append(
			Common.sqlChar(getEditID()) ).append( "," ).append(
			Common.sqlChar(getEditDate()) ).append( "," ).append(
			Common.sqlChar(getEditTime()) ).append( "):;:") ;	
	} catch (Exception e) {
		e.printStackTrace();
	} finally {
		db.closeAll();
	}
	
	if(checkCount){
		sbSQL.append(" insert into NPBGR_Person(" ).append(
			" applyType,").append(
			" applyID,").append(
			" seq,").append(
			" applyName,").append(
			" homeTel,").append(
			" mobile,").append(
			" zipcode,").append(
			" liveAdd1,").append(
			" liveAdd2,").append(
			" liveAdd3,").append(
			" liveAdd4,").append(
			" notes,").append(
			" editID,").append(
			" editDate,").append(
			" editTime").append(
		" )Values(" ).append(
			Common.sqlChar(applyType) ).append( "," ).append(
			Common.sqlChar(applyID) ).append( "," ).append(
			Common.sqlChar(maxSeq) ).append( "," ).append(
			Common.sqlChar(applyName) ).append( "," ).append(
			Common.sqlChar(homeTel) ).append( "," ).append(
			Common.sqlChar(mobile) ).append( "," ).append(
			Common.sqlChar(zipcode) ).append( "," ).append(
			Common.sqlChar(liveAdd1) ).append( "," ).append(
			Common.sqlChar(liveAdd2) ).append( "," ).append(
			Common.sqlChar(liveAdd3) ).append( "," ).append(
			Common.sqlChar(liveAdd4) ).append( "," ).append(
			Common.sqlChar(notes) ).append( "," ).append(
			Common.sqlChar(getEditID()) ).append( "," ).append(
			Common.sqlChar(getEditDate()) ).append( "," ).append(
			Common.sqlChar(getEditTime()) ).append( "):;:") ;
					
	}
	
//System.out.println(sbSQL.toString());	
	
	if (!"".equals(getChangeKey())) {
		sbSQL.append(" delete from NPBGR_ChangePerson where caseNo=").append(Common.sqlChar(changeKey)).append(" and seqNo='1' and kindNo='2' :;:");		
		sbSQL.append(" insert into NPBGR_ChangePerson(caseNo, seqNo, kindNo, applyType, payerYN, registryYN, applyID, applyName, homeTel, mobile, zipcode, liveAdd1, liveAdd2, liveAdd3, liveAdd4) select ").append(
				Common.sqlChar(changeKey)).append(",'1','2', a.applyType, a.payerYN, a.registryYN, a.applyID, a.applyName, a.homeTel, a.mobile, a.zipcode, a.liveAdd1, a.liveAdd2, a.liveAdd3, a.liveAdd4 from UNTLA_Person a where ").append(
				" a.enterOrg=").append(Common.sqlChar(enterOrg) ).append( " and a.ownership=" ).append(
				Common.sqlChar(ownership) ).append( " and a.propertyNo=" ).append(
				Common.sqlChar(propertyNo) ).append( " and a.serialNo=" ).append(
				Common.sqlChar(serialNo) ).append( " and a.serialNo1=" ).append(
				Common.sqlChar(serialNo1) ).append(":;:");		
	}
	setCheckSeq(maxSeq);
	setCheckApplyID(applyID);
	return sbSQL.toString().split(":;:");
}


//	傳回update sql
protected String[] getUpdateSQL(){	

	Database db = new Database();
	ResultSet rs;
	String check_applyID="";
	StringBuffer sbSQL = new StringBuffer();
	String maxSeq="";
	boolean checkCount=false;
		String sql_c="select count(*) as checkResult, max(seq)+1 as seq from NPBGR_Person where 1=1 "+ 
			" and applyID = " + Common.sqlChar(applyID) +
			"";	
		try {
			ResultSet rs_c = db.querySQL(sql_c);		
			if (rs_c.next()) {
				if(rs_c.getInt("checkResult")>0){	
					String sql_d="select count(*) as checkResult, max(seq)+1 as seq from NPBGR_Person where 1=1 "+ 
						" and applyID = " + Common.sqlChar(applyID) +
						" and applyName = " + Common.sqlChar(applyName) +
				 		" and (case liveAdd4 when null then 'n' else liveAdd4 end) = (case " + Common.sqlChar(liveAdd4) +" when null then 'n' else " + Common.sqlChar(liveAdd4) +" end)"	+
						"";			
					ResultSet rs_d = db.querySQL(sql_d);		
					if (rs_d.next()) {	
							checkCount=false;
							maxSeq=seq;
						}else{
							maxSeq=Common.formatFrontZero(rs_c.getString("seq"),2);
							checkCount=true;
						}		
				}else{
					maxSeq="01";
					checkCount=true;
				}
			}
		
	
	if(checkCount){
		sbSQL.append(" insert into NPBGR_Person(" ).append(
		" applyType,").append(
		" applyID,").append(
		" seq,").append(
		" applyName,").append(
		" homeTel,").append(
		" mobile,").append(
		" zipcode,").append(
		" liveAdd1,").append(
		" liveAdd2,").append(
		" liveAdd3,").append(
		" liveAdd4,").append(
		" notes,").append(
		" editID,").append(
		" editDate,").append(
		" editTime").append(
	" )Values(" ).append(
		Common.sqlChar(applyType) ).append( "," ).append(
		Common.sqlChar(applyID) ).append( "," ).append(
		Common.sqlChar(maxSeq) ).append( "," ).append(
		Common.sqlChar(applyName) ).append( "," ).append(
		Common.sqlChar(homeTel) ).append( "," ).append(
		Common.sqlChar(mobile) ).append( "," ).append(
		Common.sqlChar(zipcode) ).append( "," ).append(
		Common.sqlChar(liveAdd1) ).append( "," ).append(
		Common.sqlChar(liveAdd2) ).append( "," ).append(
		Common.sqlChar(liveAdd3) ).append( "," ).append(
		Common.sqlChar(liveAdd4) ).append( "," ).append(
		Common.sqlChar(notes) ).append( "," ).append(
		Common.sqlChar(getEditID()) ).append( "," ).append(
		Common.sqlChar(getEditDate()) ).append( "," ).append(
		Common.sqlChar(getEditTime()) ).append( "):;:");
	}

	
	/*sbSQL.append(" delete UNTLA_Person where 1=1 " ).append(
		" and enterOrg = " ).append( Common.sqlChar(enterOrg) ).append( 
		" and ownership = " ).append( Common.sqlChar(ownership) ).append( 
		" and propertyNo = " ).append( Common.sqlChar(propertyNo) ).append( 
		" and serialNo = " ).append( Common.sqlChar(serialNo) ).append( 
		" and serialNo1 = " ).append( Common.sqlChar(serialNo1) ).append(
		" and applyID=").append(Common.sqlChar(checkApplyID)).append(
		" and seq=").append(Common.sqlChar(checkSeq)).append(":;:");*/
	
    String[] execSQLArray = new String[1];
	
	execSQLArray[0]=" delete UNTLA_Person where 1=1 " +
	    " and enterOrg = " + Common.sqlChar(enterOrg) +
		" and ownership = " + Common.sqlChar(ownership) +
		" and propertyNo = " + Common.sqlChar(propertyNo) +
		" and serialNo = " + Common.sqlChar(serialNo) +
		" and serialNo1 = " + Common.sqlChar(serialNo1) +
		" and applyID = " + Common.sqlChar(checkApplyID) +
		" and seq = " + Common.sqlChar(checkSeq) +
		"";
	
	db.excuteSQL(execSQLArray);
	
	try {
		String sql="select count(*) as checkResult from UNTLA_Person where payerYN='Y' and kindno='2' " +
		" and caseNo=" + Common.sqlChar(caseNo) +
		"";	
	rs = db.querySQL(sql);
	if (rs.next()) {	
		if (rs.getInt("checkResult")>0) {
			setPayerYN("N");
		}else {
			setPayerYN("Y");	
		} 
	}else{
		setPayerYN("Y");
	}
	rs.close();
	} catch (Exception e) {
		e.printStackTrace();
	} finally {
		db.closeAll();
	}
	
	sbSQL.append(" insert into UNTLA_Person(" ).append(
			" enterOrg,").append(
			" ownership,").append(
			" propertyNo,").append(
			" serialNo,").append(
			" serialNo1,").append(
			" applyType,").append(
			" payerYN,").append(
			" registryYN,").append(
			" applyID,").append(
			" seq,").append(
			" applyName,").append(
			" homeTel,").append(
			" mobile,").append(
			" zipcode,").append(
			" liveAdd1,").append(
			" liveAdd2,").append(
			" liveAdd3,").append(
			" liveAdd4,").append(
			" notes,").append(
			" editID,").append(
			" editDate,").append(
			" editTime").append(
		" )Values(" ).append(
			Common.sqlChar(enterOrg) ).append( "," ).append(
			Common.sqlChar(ownership) ).append( "," ).append(
			Common.sqlChar(propertyNo) ).append( "," ).append(
			Common.sqlChar(serialNo) ).append( "," ).append(
			Common.sqlChar(serialNo1) ).append( "," ).append(		
			Common.sqlChar(applyType) ).append( "," ).append(
			Common.sqlChar(payerYN) ).append( "," ).append(
			Common.sqlChar(registryYN) ).append( "," ).append(
			Common.sqlChar(applyID) ).append( "," ).append(
			Common.sqlChar(maxSeq) ).append( "," ).append(
			Common.sqlChar(applyName) ).append( "," ).append(
			Common.sqlChar(homeTel) ).append( "," ).append(
			Common.sqlChar(mobile) ).append( "," ).append(
			Common.sqlChar(zipcode) ).append( "," ).append(
			Common.sqlChar(liveAdd1) ).append( "," ).append(
			Common.sqlChar(liveAdd2) ).append( "," ).append(
			Common.sqlChar(liveAdd3) ).append( "," ).append(
			Common.sqlChar(liveAdd4) ).append( "," ).append(
			Common.sqlChar(notes) ).append( "," ).append(
			Common.sqlChar(getEditID()) ).append( "," ).append(
			Common.sqlChar(getEditDate()) ).append( "," ).append(
			Common.sqlChar(getEditTime()) ).append( "):;:") ;	
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.closeAll();
		}	
	check_applyID=applyID;
	return sbSQL.toString().split(":;:");
}


//	傳回delete sql
protected String[] getDeleteSQL(){
	//System.out.println("seq:"+getSeq());
	String[] execSQLArray = new String[1];
	execSQLArray[0]=" delete UNTLA_Person where 1=1 " +
			" and applyID = " + Common.sqlChar(applyID) +
			" and seq = " + Common.sqlChar(seq) +
			" and enterOrg = " + Common.sqlChar(enterOrg) + 
			" and ownership = " + Common.sqlChar(ownership) + 
			" and propertyNo = " + Common.sqlChar(propertyNo) + 
			" and serialNo = " + Common.sqlChar(serialNo) + 
			" and serialNo1 = " + Common.sqlChar(serialNo1) +
			"";
//System.out.println(execSQLArray[0].toString());	
	return execSQLArray;
}


/**
 * <br>
 * <br>目的：依主鍵查詢單一資料
 * <br>參數：無
 * <br>傳回：傳回本物件
*/

public UNTLA049F  queryOne() throws Exception{
	Database db = new Database();
	UNTLA049F obj = this;
	try {
		String sql=" select a.applyType, a.payerYN, a.registryYN, a.applyID, a.seq, a.applyName, a.homeTel, a.mobile, a.zipcode, a.liveAdd1, a.liveAdd2, a.liveAdd3, a.liveAdd4, a.notes, a.editID, a.editDate, a.editTime  "+
			" from UNTLA_Person a where 1=1 " +
			" and a.applyID = " + Common.sqlChar(applyID) +
			" and a.seq = " + Common.sqlChar(checkSeq) +
			" and enterOrg = " + Common.sqlChar(enterOrg) + 
			" and ownership = " + Common.sqlChar(ownership) + 
			" and propertyNo = " + Common.sqlChar(propertyNo) + 
			" and serialNo = " + Common.sqlChar(serialNo) + 
			" and serialNo1 = " + Common.sqlChar(serialNo1) +
			"";
//System.out.println(sql);		
		ResultSet rs = db.querySQL(sql);
		if (rs.next()){

			obj.setApplyType(rs.getString("applyType"));
			obj.setPayerYN(rs.getString("payerYN"));
			obj.setRegistryYN(rs.getString("registryYN"));
			obj.setApplyID(rs.getString("applyID"));
			obj.setCheckApplyID(rs.getString("applyID"));
			obj.setSeq(rs.getString("seq"));
			obj.setApplyName(rs.getString("applyName"));
			obj.setHomeTel(rs.getString("homeTel"));
			obj.setMobile(rs.getString("mobile"));
			obj.setZipcode(rs.getString("zipcode"));
			obj.setLiveAdd1(rs.getString("liveAdd1"));
			obj.setLiveAdd2(rs.getString("liveAdd2"));
			obj.setLiveAdd3(rs.getString("liveAdd3"));
			obj.setLiveAdd4(rs.getString("liveAdd4"));
			obj.setNotes(rs.getString("notes"));
			obj.setEditID(rs.getString("editID"));
			obj.setEditDate(rs.getString("editDate"));
			obj.setEditTime(rs.getString("editTime"));
		}
		checkSeq=seq;
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
		String sql=" select case a.applyType when '2' then '公司' else '個人' end as applyType, case a.payerYN when 'Y' then '是' else '否' end as payerYN, a.seq, case a.registryYN when 'Y' then '是' else '否' end as registryYN, a.applyID, a.applyName, a.homeTel, a.mobile, a.zipcode, a.zipcode, (select c.addrName from SYSCA_ADDR c where a.liveAdd1=c.addrID)as addrNameC, (select d.addrName from SYSCA_ADDR d where a.liveAdd2=d.addrID) as addrNameD, (select e.addrName from SYSCA_ADDR e where a.liveAdd3=e.addrID) as addrNameE, a.liveAdd4, a.notes "+
			" from UNTLA_Person a  where 1=1 " +
			" and a.enterOrg = " + Common.sqlChar(enterOrg) + 
			" and a.ownership = " + Common.sqlChar(ownership) + 
			" and a.propertyNo = " + Common.sqlChar(propertyNo) + 
			" and a.serialNo = " + Common.sqlChar(serialNo) + 
			" and a.serialNo1 = " + Common.sqlChar(serialNo1) +
			"";	
//System.out.println(sql);		
		ResultSet rs = db.querySQL(sql+ " order by a.payerYN desc");
		while (rs.next()){
			counter++;
			String rowArray[]=new String[10];
			rowArray[0]=rs.getString("applyType"); 
			rowArray[1]=rs.getString("payerYN"); 
			rowArray[2]=rs.getString("registryYN");
			rowArray[3]=rs.getString("applyID"); 
			rowArray[4]=rs.getString("applyName"); 
			rowArray[5]=rs.getString("homeTel"); 
			rowArray[6]=rs.getString("mobile");  
			rowArray[7]=rs.getString("zipcode") + rs.getString("addrNameC") + rs.getString("addrNameD") + rs.getString("addrNameE") + rs.getString("liveAdd4"); 
			rowArray[8]=rs.getString("applyID");
			rowArray[9]=rs.getString("seq");
			objList.add(rowArray);
		}
		setStateQueryAllSuccess();
	} catch (Exception e) {
		e.printStackTrace();
	} finally {
		db.closeAll();
	}
	return objList;
}

public String getNPBGR_Person(String enterOrg,String ownership,String propertyNo,String serialNo,String serialNo1) throws Exception{
	StringBuffer sbHTML = new StringBuffer();
	sbHTML = sbHTML.append("");
	Database db = new Database();
	try {
		//case a.registryYN when 'Y' then '是' else '否' end as registryYN, 
		String sql=" select (case a.applyType when '2' then '公司' else '個人' end) as applyType, (case a.payerYN when 'Y' then '是' else '否' end) as payerYN, a.seq, decode(a.registryYN,'Y','是','否') as registryYN, a.applyID, a.applyName, a.homeTel, a.mobile, a.zipcode, a.zipcode, (select c.addrName from SYSCA_ADDR c where a.liveAdd1=c.addrID) as addrNameC, (select d.addrName from SYSCA_ADDR d where a.liveAdd2=d.addrID) as addrNameD, (select e.addrName from SYSCA_ADDR e where a.liveAdd3=e.addrID) as addrNameE, a.liveAdd4, a.notes "+
			" from UNTLA_Person a where 1=1 " +
			" and a.enterOrg = " + Common.sqlChar(enterOrg) + 
			" and a.ownership = " + Common.sqlChar(ownership) + 
			" and a.propertyNo = " + Common.sqlChar(propertyNo) + 
			" and a.serialNo = " + Common.sqlChar(serialNo) + 
			" and a.serialNo1 = " + Common.sqlChar(serialNo1) +
			"";		
//System.out.println(sql);		
		ResultSet rs = db.querySQL(sql+ " order by payerYN desc ");
		int counter = 0;
		while (rs.next()){
			counter++;
			sbHTML.append(" <tr class='listTR' onmouseover=\"this.className='listTRMouseover'\" onmouseout=\"this.className='listTR'\" onClick=\"queryOne('").append( enterOrg ).append("','").append( ownership ).append("','").append( propertyNo ).append("','").append( serialNo ).append("','").append( serialNo1 ).append("','").append( rs.getString("applyID") ).append("','").append(rs.getString("seq")).append("');\">\n");		
			sbHTML.append(" <td class='listTD' >").append(counter).append(".</td> ");
			sbHTML.append(" <td class='listTD' >").append(rs.getString("applyType")).append("</td>\n");
			sbHTML.append(" <td class='listTD' >").append(rs.getString("payerYN")).append("</td>\n");									
			sbHTML.append(" <td class='listTD' >").append(rs.getString("registryYN")).append("</td>\n");
			sbHTML.append(" <td class='listTD' >").append(rs.getString("applyID")).append("</td>\n");
			sbHTML.append(" <td class='listTD' >").append(rs.getString("applyName")).append("</td>\n");
			sbHTML.append(" <td class='listTD' >").append(Common.get(rs.getString("hometel"))).append("</td>\n");
			sbHTML.append(" <td class='listTD' >").append(Common.get(rs.getString("mobile"))).append("</td>\n");
			sbHTML.append(" <td class='listTD' >").append(Common.get(rs.getString("zipcode")) + Common.get(rs.getString("addrNameC")) + Common.get(rs.getString("addrNameD")) + Common.get(rs.getString("addrNameE")) + Common.get(rs.getString("liveAddr"))).append("</td>\n");
			sbHTML.append("</tr>\n ");	
//System.out.println(sbHTML.toString());			
		}		
		if (counter==0) {
			sbHTML.append(" <tr class='listTR' ><td class='listTD' colspan='100' style='color:red'>查無繳款人資料，請先到產籍資料增加使用人資料，再重新開徵！</td></tr>");			
		}				
	} catch (Exception e) {
		e.printStackTrace();
	} finally {
		db.closeAll();
	}
	
	return sbHTML.toString();
}

}
