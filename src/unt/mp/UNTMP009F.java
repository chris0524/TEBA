/*
*<br>程式目的：動產保管使用異動作業－移動單資料
*<br>程式代號：untmp009f
*<br>程式日期：0941017
*<br>程式作者：carey
*<br>--------------------------------------------------------
*<br>修改作者　　修改日期　　　修改目的
*<br>--------------------------------------------------------
*/

package unt.mp;

import java.sql.ResultSet;
import java.util.ArrayList;
import util.*;

public class UNTMP009F extends UNTMP009Q{

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

public String getPropertyNo(){ return checkGet(propertyNo); }
public void setPropertyNo(String s){ propertyNo=checkSet(s); }
public String getSerialNo(){ return checkGet(serialNo); }
public void setSerialNo(String s){ serialNo=checkSet(s); }
public String getVerifyError(){ return checkGet(verifyError); }
public void setVerifyError(String s){ verifyError=checkSet(s); }

String newMoveDate;

public String getNewMoveDate(){ return checkGet(newMoveDate); }
public void setNewMoveDate(String s){ newMoveDate=checkSet(s); }

String closing;

public String getClosing(){ return checkGet(closing); }
public void setClosing(String s){ closing=checkSet(s); }

//傳回執行insert前之檢查sql
protected String[][] getInsertCheckSQL(){
	String[][] checkSQLArray = new String[1][4];
 	checkSQLArray[0][0]=" select count(*) as checkResult from UNTMP_MoveProof where 1=1 " + 
		" and enterOrg = " + Common.sqlChar(enterOrg) + 
		" and ownership = " + Common.sqlChar(ownership) + 
		" and caseNo = " + Common.sqlChar(caseNo) + 
		"";
	checkSQLArray[0][1]=">";
	checkSQLArray[0][2]="0";
	checkSQLArray[0][3]="該筆資料己重複，請重新輸入！";
	return checkSQLArray;
}

//傳回insert sql
protected String[] getInsertSQL(){
	//取得電腦單號
	Database db = new Database();
	ResultSet rs;	
	String sql="select substr(max(substr(caseNo,8,3))+1001,2,3) as caseNo from UNTMP_MoveProof " +
		" where enterOrg = " + Common.sqlChar(enterOrg) +
		" and ownership = " + Common.sqlChar(ownership) +
		" and substr(caseNo,1,7) = " + Common.sqlChar(Datetime.getYYYMMDD()) + 
		"";		
	try {		
		rs = db.querySQL(sql);
		if (rs.next()){
			if (rs.getString("caseNo")==null)
				setCaseNo(Datetime.getYYYMMDD()+"001");
			else
			    setCaseNo(Datetime.getYYYMMDD()+rs.getString("caseNo"));
		} else {
			setCaseNo(Datetime.getYYYMMDD()+"001");
		}
	} catch (Exception e) {
		e.printStackTrace();
	} finally {
		db.closeAll();
	}
	//取得移動單編號－號
	setProofNo(MaxClosingYM.getMaxProofNo("UNTMP_MoveProof",enterOrg,ownership));
	//===================
    String[] execSQLArray = new String[1];
	execSQLArray[0]=" insert into UNTMP_MoveProof(" +
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
			" closing,"+
			" editID,"+
			" editDate,"+
			" editTime"+
		" )Values(" +
			Common.sqlChar(enterOrg) + "," +
			Common.sqlChar(ownership) + "," +
			Common.sqlChar(caseNo) + "," +
			Common.sqlChar(caseName) + "," +
			Common.sqlChar(writeDate) + "," +
			Common.sqlChar(writeUnit) + "," +
			Common.sqlChar(proofDoc) + "," +
			Common.sqlChar(proofNo) + "," +
			Common.sqlChar(moveDate) + "," +
			Common.sqlChar(verify) + "," +
			Common.sqlChar(notes) + "," +
			Common.sqlChar(closing) + "," +
			Common.sqlChar(getEditID()) + "," +
			Common.sqlChar(getEditDate()) + "," +
			Common.sqlChar(getEditTime()) + ")" ;
	return execSQLArray;
}

//傳回執行update前之檢查sql
protected String[][] getUpdateCheckSQL(){
	if ("Y".equals(getVerify())) {
		String[][] checkSQLArray = new String[1][4];
	 	checkSQLArray[0][0]=" select count(*) as checkResult from UNTMP_MoveDetail where 1=1 " + 
			" and enterOrg = " + Common.sqlChar(enterOrg) + 
			" and ownership = " + Common.sqlChar(ownership) + 
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
    Database db = new Database();    
	String[] execSQLArray = new String[3];
	String strSQL = "";
	setEditID(getUserID());
	setEditDate(Datetime.getYYYMMDD());
	setEditTime(Datetime.getHHMMSS());					
	//更新動產移動單
	strSQL += " update UNTMP_MoveProof set " + "\n" +
			" caseName = " + Common.sqlChar(caseName) + "," + "\n" +
			" writeDate = " + Common.sqlChar(writeDate) + "," + "\n" +
			" writeUnit = " + Common.sqlChar(writeUnit) + "," + "\n" +
			" proofDoc = " + Common.sqlChar(proofDoc) + "," + "\n" +
			" proofNo = " + Common.sqlChar(proofNo) + "," + "\n" +
			" moveDate = " + Common.sqlChar(moveDate) + "," + "\n" +
			" verify = " + Common.sqlChar(verify) + "," + "\n" +
			" notes = " + Common.sqlChar(notes) + "," + "\n" +
			" closing = " + Common.sqlChar(closing) + "," + "\n" +
			" editID = " + Common.sqlChar(getEditID()) + "," + "\n" +
			" editDate = " + Common.sqlChar(getEditDate()) + "," + "\n" +
			" editTime = " + Common.sqlChar(getEditTime()) + "\n" +
		" where 1=1 " + "\n" + 
			" and enterOrg = " + Common.sqlChar(enterOrg) + "\n" +
			" and ownership = " + Common.sqlChar(ownership) + "\n" +
			" and caseNo = " + Common.sqlChar(caseNo) + "\n" +
			":;:";

	//更新動產移動單明細檔
	strSQL+=" update UNTMP_MoveDetail set " +
			" newMoveDate = " + Common.sqlChar(moveDate) + "," + "\n" +	
			" verify = " + Common.sqlChar(verify) + "," + "\n" +	
			" editID = " + Common.sqlChar(getEditID()) + "," + "\n" +
			" editDate = " + Common.sqlChar(getEditDate()) + "," + "\n" +
			" editTime = " + Common.sqlChar(getEditTime()) + "\n" +
		" where 1=1 " + 
			" and enterOrg = " + Common.sqlChar(enterOrg) +
			" and ownership = " + Common.sqlChar(ownership) +
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
	String[] execSQLArray = new String[2];
	
	//刪除動產移動單
	execSQLArray[0]=" delete UNTMP_MoveProof where 1=1 " +
			" and enterOrg = " + Common.sqlChar(enterOrg) +
			" and ownership = " + Common.sqlChar(ownership) +
			" and caseNo = " + Common.sqlChar(caseNo) +
			"";
	
	//刪除動產移動單明細檔
	execSQLArray[1]=" delete UNTMP_MoveDetail where 1=1 " +
	" and enterOrg = " + Common.sqlChar(enterOrg) +
	" and ownership = " + Common.sqlChar(ownership) +
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

public UNTMP009F  queryOne() throws Exception{
	Database db = new Database();
	UNTMP009F obj = this;
	try {
		String sql=" select b.organSName, a.enterOrg, a.ownership, a.caseNo, a.caseName, a.writeDate, a.writeUnit, a.proofDoc, a.proofNo, a.moveDate, a.verify, a.notes, a.editID, a.editDate, a.editTime, a.closing "+
			" from UNTMP_MoveProof a,SYSCA_Organ b where 1=1 " +
			" and a.enterOrg = " + Common.sqlChar(enterOrg) +
			" and a.ownership = " + Common.sqlChar(ownership) +
			" and a.caseNo = " + Common.sqlChar(caseNo) +
			" and b.organID = a.enterOrg" +
			"";
		//System.out.println("UNTMP009F_queryOne==>"+sql);
		ResultSet rs = db.querySQL(sql);
		if (rs.next()){
			obj.setEnterOrg(rs.getString("enterOrg"));
			obj.setEnterOrgName(rs.getString("organSName"));
			obj.setOwnership(rs.getString("ownership"));
			obj.setCaseNo(rs.getString("caseNo"));
			obj.setCaseName(rs.getString("caseName"));
			obj.setWriteDate(rs.getString("writeDate"));
			obj.setWriteUnit(rs.getString("writeUnit"));
			obj.setProofDoc(rs.getString("proofDoc"));
			obj.setProofNo(rs.getString("proofNo"));
			obj.setMoveDate(rs.getString("moveDate"));
			obj.setNewMoveDate(rs.getString("moveDate"));
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
	int counter=0;
	try {
		String sql=" select distinct a.enterOrg, a.ownership, " +
					" (select z.codeName from sysca_code z where 1=1 and (z.codeKindID='owa' or z.codeKindID='OWA') and a.ownership=z.codeID) as ownershipName, " +
					" a.caseNo, a.caseName, a.writeDate, a.moveDate,decode(a.verify,'Y','是','否') verifyName "+
					" from UNTMP_MoveProof a,UNTMP_MoveDetail b, SYSCA_Organ c " +
					" where 1=1 " +
					" and a.enterOrg = c.organID " +
					" and a.enterOrg=b.enterOrg(+) " +
					" and a.ownership=b.ownership(+) " +
					" and a.caseNo=b.caseNo(+)"
					;	

		if ("MoveDetail".equals(getQuerySelect()) || "".equals(getQuerySelect())) {
			sql+=" and a.enterOrg=" + Common.sqlChar(getEnterOrg());
			sql+=" and a.ownership=" + Common.sqlChar(getOwnership());			
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
				sql+=" and a.caseNo >= rpad(" + Common.sqlChar(getQ_caseNoS()) + ",10,'0')";
			if (!"".equals(getQ_caseNoE()))
			    sql+=" and a.caseNo <= rpad(" + Common.sqlChar(getQ_caseNoE()) + ",10,'9')";
			if (!"".equals(getQ_caseName()))
			    sql+=" and a.caseName like " + Common.sqlChar("%"+getQ_caseName()+"%") ;
			if (!"".equals(getQ_writeDateS()))
				sql+=" and a.writeDate >= " + Common.sqlChar(new Datetime().changeTaiwanYYMMDD(getQ_writeDateS(),"2")) ;
			if (!"".equals(getQ_writeDateE()))
				sql+=" and a.writeDate <= " + Common.sqlChar(new Datetime().changeTaiwanYYMMDD(getQ_writeDateE(),"2")) ;
			if (!"".equals(getQ_moveDateS()))
				sql+=" and a.moveDate >= " + Common.sqlChar(getQ_moveDateS()) ;
			if (!"".equals(getQ_moveDateE()))
				sql+=" and a.moveDate <= " + Common.sqlChar(getQ_moveDateE()) ;
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
		}			
//System.out.println("UNTMP009F_queryAll==>"+sql);
			ResultSet rs = db.querySQL(sql+ " order by a.enterOrg, a.ownership, a.caseNo");
			while (rs.next()){
			counter++;
			String rowArray[]=new String[8];
			rowArray[0]=rs.getString("enterOrg"); 
			rowArray[1]=rs.getString("ownership");
			rowArray[2]=rs.getString("ownershipName");
			rowArray[3]=rs.getString("caseNo"); 
			rowArray[4]=rs.getString("caseName"); 
			rowArray[5]=rs.getString("writeDate"); 
			rowArray[6]=rs.getString("moveDate"); 
			rowArray[7]=rs.getString("verifyName"); 
			objList.add(rowArray);
			if (counter==getListLimit()){
				setErrorMsg(getListLimitError());
				break;
			}
		}
		setStateQueryAllSuccess();
	} catch (Exception e) {
		e.printStackTrace();
	} finally {
		db.closeAll();
	}
	return objList;
}

//全部入帳
public void approveAll()throws Exception{	
	Database db = new Database();
	try {    
	    int i = 0,counter = 0;
		String rowArray[]=new String[8];
		java.util.Iterator it= queryAll().iterator();
		String[] execSQLArray;
		String strSQL = "";
		while(it.hasNext()){
		    counter++;
			rowArray= (String[])it.next();
			if(rowArray[7].equals("否")){	
				i++;
				setVerify("Y");
//				setOldVerify("N");
				enterOrg = rowArray[0];
				ownership= rowArray[1];
				caseNo   = rowArray[3];
				setMoveDate(rowArray[6]==null || "".equals(rowArray[6]) ? Datetime.getYYYMMDD():rowArray[6]);
				setEditID(getUserID());
				setEditDate(Datetime.getYYYMMDD());
				setEditTime(Datetime.getHHMMSS());	
				if (enterOrg.equals(getOrganID())) {
				    //更新動產移動單
				    strSQL += "update UNTMP_MoveProof set "+
			                " verify = 'Y'," +
			                " moveDate = " + Common.sqlChar(getMoveDate()) + "," +
			    			" editID = " + Common.sqlChar(getEditID()) + "," +
			    			" editDate = " + Common.sqlChar(getEditDate()) + "," +
			    			" editTime = " + Common.sqlChar(getEditTime()) + 	                
			        		" where 1=1 " + 
			    			" and enterOrg = " + Common.sqlChar(enterOrg) +
			    			" and ownership = " + Common.sqlChar(ownership) +
			    			" and caseNo = " + Common.sqlChar(caseNo) +
			    			":;:";								
					strSQL += checkVerify() + ":;:";

					//更新動產移動單明細檔
					strSQL += "update UNTMP_MoveDetail set " +
							" verify='Y'," +
							" newMoveDate=" + Common.sqlChar(getMoveDate()) + "," +
							" editID = " + Common.sqlChar(getEditID()) + "," +
			    			" editDate = " + Common.sqlChar(getEditDate()) + "," +
			    			" editTime = " + Common.sqlChar(getEditTime()) + 
			        		" where 1=1 " + 
			    			" and enterOrg = " + Common.sqlChar(enterOrg) +
			    			" and ownership = " + Common.sqlChar(ownership) +
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
    String sql="";
    if(Integer.parseInt(getMoveDate().substring(0,5)) > Integer.parseInt(MaxClosingYM.getMaxClosingYM(enterOrg))){
    	Database db = new Database();
    	String checkDetail="";
    	try {
    		String SQL = "select c.enterOrg, c.ownership, c.propertyNo, c.serialNo, " +
    					" c.newMoveDate,c.newKeepUnit,c.newKeeper,c.newUseUnit,c.newUserNo,c.newPlace " +
    					" from UNTMP_MoveDetail c where 1=1 " +
    					" and c.enterOrg = " + Common.sqlChar(enterOrg) + "\n" +
    					" and c.ownership = " + Common.sqlChar(ownership) + "\n" +
    					" and c.caseNo = " + Common.sqlChar(caseNo) + "\n" +
    					"";
    		ResultSet rs = db.querySQL(SQL);
    		while (rs.next()){
    			checkDetail="Y";
    			sql += " update UNTMP_MovableDetail set " +
    				" moveDate = " + Common.sqlChar(getMoveDate()) + "," +
    				" keepUnit = " + Common.sqlChar(rs.getString("newKeepUnit")) + "," +
    				" keeper = " + Common.sqlChar(rs.getString("newKeeper")) + "," +
    				" useUnit = " + Common.sqlChar(rs.getString("newUseUnit")) + "," +
    				" userNo = " + Common.sqlChar(rs.getString("newUserNo")) + "," +
    				" place = " + Common.sqlChar(rs.getString("newPlace")) +
    				" where 1=1 " +
    				" and enterOrg = " + Common.sqlChar(rs.getString("enterOrg")) + 
    				" and ownership = " + Common.sqlChar(rs.getString("ownership")) +
    				" and propertyNo = " + Common.sqlChar(rs.getString("propertyNo")) +
    				" and serialNo = " + Common.sqlChar(rs.getString("serialNo")) +
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
    }else{
    	setVerifyError("Y");   
        setStateUpdateError();
        setErrorMsg("入帳設定有錯，移動日期之年月必須大於最大的月結年月！");
    }
	return sql;
}

//回復入帳設定
public void approveRe()throws Exception{	
	Database db = new Database();
	String enterOrgQuery = "";
	String ownershipQuery = "";
	String caseNoQuery = "";
	String propertyNoQuery = "";
	String serialNoQuery = "";
	String reduceSql = "",reduceCount = "";
	String adjustSql = "",adjustCount = "";
	String moveSql = "",moveCount = "";
	String moveOriginal = "",reduceMax = "",reduceMaxSql = "";
	String adjustMax = "",adjustMaxSql = "";
	String moveMax = "",moveMaxSql = "";
	int count = 0;
	try {    
		String[] execSQLArray;
		String strSQL = "";
		String sql ="select a.caseNo, b.enterOrg, b.ownership, b.propertyNo, b.serialNo, b.lotNo, " +
					" a.editDate, a.editTime, a.moveDate, " +
					" b.oldMoveDate, b.oldKeepUnit, b.oldKeeper, b.oldUseUnit, b.oldUserNo, b.oldPlace " +
					" from untmp_moveProof a, untmp_moveDetail b " +
					" where 1=1 " +
					" and a.enterOrg = b.enterOrg and a.ownership = b.ownership and a.caseNo = b.caseNo" +
	    			" and a.enterOrg = " + Common.sqlChar(enterOrg) +
	    			" and a.ownership = " + Common.sqlChar(ownership) +
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
			//該移動單之明細資料,存在未入帳的「動產減損單明細檔UNTMP_ReduceDetail」資料，則提示使用者
			reduceSql = "select count(*) count from untmp_reduceDetail " +
						" where enterOrg="+Common.sqlChar(enterOrgQuery)+" and ownership="+Common.sqlChar(ownershipQuery)+" and propertyNo="+Common.sqlChar(propertyNoQuery)+" and serialNo="+Common.sqlChar(serialNoQuery)+" and verify='N'";
			reduceCount = MaxClosingYM.getCountValues(reduceSql);
			
			//該移動單之明細資料,存在未入帳的「動產增減值單明細檔UNTMP_AdjustDetail」資料，則提示使用者
			adjustSql = "select count(*) count from untmp_adjustDetail " +
						" where enterOrg="+Common.sqlChar(enterOrgQuery)+" and ownership="+Common.sqlChar(ownershipQuery)+" and propertyNo="+Common.sqlChar(propertyNoQuery)+" and serialNo="+Common.sqlChar(serialNoQuery)+" and verify='N'";
			adjustCount = MaxClosingYM.getCountValues(adjustSql);
			
			//該移動單之明細資料,存在未入帳的「動產移動單明細檔UNTMP_MoveDetail」資料，則提示使用者
			moveSql = "select count(*) count from untmp_moveDetail " +
					" where enterOrg="+Common.sqlChar(enterOrgQuery)+" and ownership="+Common.sqlChar(ownershipQuery)+" and propertyNo="+Common.sqlChar(propertyNoQuery)+" and serialNo="+Common.sqlChar(serialNoQuery)+" and verify='N'";
			moveCount = MaxClosingYM.getCountValues(moveSql);
			//------------------------------------------------------------------------------------------------
			moveOriginal = rs.getString("editDate")+rs.getString("editTime")+rs.getString("moveDate");
			//該移動單之(異動日期+異動時間+減損日期)≦移動單明細資料之其他移動單之最大的(異動日期+異動時間+減損日期)則提示使用者
			moveMaxSql = "select count(*) count from untmp_moveProof a, untmp_moveDetail b " +
						" where 1=1 and a.caseNo=b.caseNo and a.enterOrg=b.enterOrg and a.ownership=b.ownership " +
						" and b.enterOrg="+Common.sqlChar(enterOrgQuery)+" and b.ownership="+Common.sqlChar(ownershipQuery)+" and b.propertyNo="+Common.sqlChar(propertyNoQuery)+" and b.serialNo="+Common.sqlChar(serialNoQuery)+" and a.verify='Y' " +
						" and '" + moveOriginal + "'<=(b.editDate || b.editTime || a.moveDate) " +
						" and a.caseNo!="+Common.sqlChar(caseNoQuery);
			moveMax = MaxClosingYM.getCountValues(moveMaxSql);

			//該移動單之(異動日期+異動時間+減損日期)≦移動單明細資料之其他增減值單之最大的(異動日期+異動時間+減損日期)則提示使用者
			adjustMaxSql = "select count(*) count from untmp_adjustDetail " +
							" where enterOrg="+Common.sqlChar(enterOrgQuery)+" and ownership="+Common.sqlChar(ownershipQuery)+" and propertyNo="+Common.sqlChar(propertyNoQuery)+" and serialNo="+Common.sqlChar(serialNoQuery)+" and verify='Y' " +
							" and '" + moveOriginal + "'<=(editDate || editTime || adjustDate) " ;
			adjustMax = MaxClosingYM.getCountValues(adjustMaxSql);

			//該移動單之(異動日期+異動時間+減損日期)≦移動單明細資料之其他減損單之最大的(異動日期+異動時間+減損日期)則提示使用者
			reduceMaxSql = "select count(*) count from untmp_reduceDetail " +
							" where enterOrg="+Common.sqlChar(enterOrgQuery)+" and ownership="+Common.sqlChar(ownershipQuery)+" and propertyNo="+Common.sqlChar(propertyNoQuery)+" and serialNo="+Common.sqlChar(serialNoQuery)+" and verify='Y' " +
							" and '" + moveOriginal + "'<=(editDate || editTime || reduceDate) " ;
			reduceMax = MaxClosingYM.getCountValues(reduceMaxSql);
			//------------------------------------------------------------------------------------------------
			if(closing.equals("N") && verify.equals("Y") && reduceCount.equals("0") && adjustCount.equals("0") && moveCount.equals("0") && reduceMax.equals("0") && adjustMax.equals("0") && moveMax.equals("0")){
				//依據該移動單「入帳機關enterOrg＋權屬ownership＋電腦單號caseNo」設定
				if(count==1){
					strSQL += "update untmp_moveProof set verify ='N' " +
							" where enterOrg=" + Common.sqlChar(enterOrgQuery) +
							" and ownership=" + Common.sqlChar(ownershipQuery) +
							" and caseNo=" + Common.sqlChar(caseNoQuery) +
							":;:";				
					strSQL += "update untmp_moveDetail set verify ='N' " +
							" where enterOrg=" + Common.sqlChar(enterOrgQuery) +
							" and ownership=" + Common.sqlChar(ownershipQuery) +
							" and caseNo=" + Common.sqlChar(caseNoQuery) +
							":;:";				
				}

				//依據移動單明細「入帳機關enterOrg＋權屬ownership＋財產編號propertyNo＋財產分號serialNo」設定
				strSQL +="update untmp_movableDetail set " +
						" moveDate = " + Common.sqlChar(rs.getString("oldMoveDate")) + "," +
						" keepUnit = " + Common.sqlChar(rs.getString("oldKeepUnit")) + "," +
						" keeper = " + Common.sqlChar(rs.getString("oldKeeper")) + "," +
						" useUnit = " + Common.sqlChar(rs.getString("oldUseUnit")) + "," +
						" userNo = " + Common.sqlChar(rs.getString("oldUserNo")) + "," +
						" place = " + Common.sqlChar(rs.getString("oldPlace")) +
						" where enterOrg=" + Common.sqlChar(enterOrgQuery) +
						" and ownership=" + Common.sqlChar(ownershipQuery) +
						" and propertyNo=" + Common.sqlChar(propertyNoQuery) +
						" and serialNo=" + Common.sqlChar(serialNoQuery) +
						":;:";
				//----------------------------------------
			}else{
				setVerifyError("Y");
				if(closing.equals("Y")){
					setErrorMsg("已月結的資料無法回復入帳，請先取消月結，再回此作業回復入帳！");
				}else if(verify.equals("N")){
					setErrorMsg("尚未入帳，請直接修改資料即可！");
				}else if(!reduceCount.equals("0")){
					setErrorMsg("減損作業存在未入帳的資料，無法回復入帳！");
				}else if(!adjustCount.equals("0")){
					setErrorMsg("增減值作業存在未入帳的資料，無法回復入帳！");
				}else if(!moveCount.equals("0")){
					setErrorMsg("保管使用異動作業存在未入帳的資料，無法回復入帳！");
				}else if(!moveMax.equals("0")){
					setErrorMsg("並非最後一筆入帳(移動)的資料，無法回復入帳！");
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
			db.excuteSQL(execSQLArray);
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

}


