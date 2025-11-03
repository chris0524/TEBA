package unt.dp;

import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import unt.ch.UNTCH_Tools;
import util.*;

public class UNTDP011F01 extends QueryBean{

	private String enterOrg;
	private String enterOrgName;
	private String ownership;
	private String ownershipName;
	private String differenceKind;
	private String propertyNo;
	private String propertyNoName;
	private String serialNo;
	private String serialNo1;
	private String propertyName1;
	private String apportionYear;
	private String monthDepr;
	private String notes;
	private String editID;
	private String editDate;
	private String updateMaster;
	private String oldDeprMethod;
	private String oldBuildFeeCB;
	private String oldDeprUnitCB;
	private String oldDeprPark;
	private String oldDeprUnit;
	private String oldDeprUnit1;
	private String oldDeprAccounts;
	private String oldScrapValue;
	private String oldDeprAmount;
	private String oldAccumDepr;
	private String oldApportionMonth;
	private String oldMonthDepr;
	private String newDeprMethod;
	private String newBuildFeeCB;
	private String newDeprUnitCB;
	private String newDeprPark;
	private String newDeprUnit;
	private String newDeprUnit1;
	private String newDeprAccounts;
	private String newScrapValue;
	private String newDeprAmount;
	private String newAccumDepr;
	private String newApportionMonth;
	private String newMonthDepr;
	private String oldDeprParkName;
	private String oldDeprUnitName;
	private String oldDeprUnit1Name;
	private String oldDeprAccountsName;
	private String oldMonthDepr1;
	private String oldApportionEndYM;
	private String newMonthDepr1;
	private String newApportionEndYM;
	private String bookvalue;
	private String netvalue;
	private String lotNo;
	private String canChgDeprUnitCB;

	private String q_enterOrg;
	private String q_propertyNoS;
	private String q_propertyNoE;
	private String q_propertyNoSName;
	private String q_propertyNoEName;
	private String q_differenceKind;
	private String q_serialNoS;
	private String q_serialNoE;
	private String q_propertyNoName1;

	public String getEnterOrg() {return checkGet(enterOrg);}
	public void setEnterOrg(String enterOrg) {this.enterOrg = checkSet(enterOrg);}
	public String getEnterOrgName() {return checkGet(enterOrgName);}
	public void setEnterOrgName(String enterOrgName) {this.enterOrgName = checkSet(enterOrgName);}
	public String getOwnership() {return checkGet(ownership);}
	public void setOwnership(String ownership) {this.ownership = checkSet(ownership);}
	public String getOwnershipName() {return checkGet(ownershipName);}
	public void setOwnershipName(String ownershipName) {this.ownershipName = checkSet(ownershipName);}
	public String getPropertyNo() {return checkGet(propertyNo);}
	public void setPropertyNo(String propertyNo) {this.propertyNo = checkSet(propertyNo);}
	public String getPropertyNoName() {return checkGet(propertyNoName);}
	public void setPropertyNoName(String propertyNoName) {this.propertyNoName = checkSet(propertyNoName);}
	public String getSerialNo() {return checkGet(serialNo);}
	public void setSerialNo(String serialNo) {this.serialNo = checkSet(serialNo);}
	public String getPropertyName1() {return checkGet(propertyName1);}
	public void setPropertyName1(String propertyName1) {this.propertyName1 = checkSet(propertyName1);}
	public String getApportionYear() {return checkGet(apportionYear);}
	public void setApportionYear(String apportionYear) {this.apportionYear = checkSet(apportionYear);}
	public String getMonthDepr() {return checkGet(monthDepr);}
	public void setMonthDepr(String monthDepr) {this.monthDepr = checkSet(monthDepr);}
	public String getNotes() {return checkGet(notes);}
	public void setNotes(String notes) {this.notes = checkSet(notes);}
	public String getEditID() {return checkGet(editID);}
	public void setEditID(String editID) {this.editID = checkSet(editID);}
	public String getEditDate() {return checkGet(editDate);}
	public void setEditDate(String editDate) {this.editDate = checkSet(editDate);}
	public String getDifferenceKind() {return checkGet(differenceKind);}
	public void setDifferenceKind(String differenceKind) {this.differenceKind = checkSet(differenceKind);}
	public String getSerialNo1() {return checkGet(serialNo1);}
	public void setSerialNo1(String serialNo1) {this.serialNo1 = checkSet(serialNo1);}
	public String getUpdateMaster() {return checkGet(updateMaster);}
	public void setUpdateMaster(String updatemaster) {this.updateMaster = checkSet(updatemaster);}
	public String getOldDeprMethod() {return checkGet(oldDeprMethod);}
	public void setOldDeprMethod(String oldDeprMethod) {this.oldDeprMethod = checkSet(oldDeprMethod);}
	public String getOldBuildFeeCB() {return checkGet(oldBuildFeeCB);}
	public void setOldBuildFeeCB(String oldBuildFeeCB) {this.oldBuildFeeCB = checkSet(oldBuildFeeCB);}
	public String getOldDeprUnitCB() {return checkGet(oldDeprUnitCB);}
	public void setOldDeprUnitCB(String oldDeprUnitCB) {this.oldDeprUnitCB = checkSet(oldDeprUnitCB);}
	public String getOldDeprPark() {return checkGet(oldDeprPark);}
	public void setOldDeprPark(String oldDeprPark) {this.oldDeprPark = checkSet(oldDeprPark);}
	public String getOldDeprUnit() {return checkGet(oldDeprUnit);}
	public void setOldDeprUnit(String oldDeprUnit) {this.oldDeprUnit = checkSet(oldDeprUnit);}
	public String getOldDeprUnit1() {return checkGet(oldDeprUnit1);}
	public void setOldDeprUnit1(String oldDeprUnit1) {this.oldDeprUnit1 = checkSet(oldDeprUnit1);}	
	public String getOldDeprAccounts() {return checkGet(oldDeprAccounts);}
	public void setOldDeprAccounts(String oldDeprAccounts) {this.oldDeprAccounts = checkSet(oldDeprAccounts);}
	public String getOldScrapValue() {return checkGet(oldScrapValue);}
	public void setOldScrapValue(String oldScrapValue) {this.oldScrapValue = checkSet(oldScrapValue);}
	public String getOldDeprAmount() {return checkGet(oldDeprAmount);}
	public void setOldDeprAmount(String oldDeprAmount) {this.oldDeprAmount = checkSet(oldDeprAmount);}
	public String getOldAccumDepr() {return checkGet(oldAccumDepr);}
	public void setOldAccumDepr(String oldAccumDepr) {this.oldAccumDepr = checkSet(oldAccumDepr);}
	public String getOldApportionMonth() {return checkGet(oldApportionMonth);}
	public void setOldApportionMonth(String oldApportionMonth) {this.oldApportionMonth = checkSet(oldApportionMonth);}
	public String getOldMonthDepr() {return checkGet(oldMonthDepr);}
	public void setOldMonthDepr(String oldMonthDepr) {this.oldMonthDepr = checkSet(oldMonthDepr);}
	public String getNewDeprMethod() {return checkGet(newDeprMethod);}
	public void setNewDeprMethod(String newDeprMethod) {this.newDeprMethod = checkSet(newDeprMethod);}
	public String getNewBuildFeeCB() {return checkGet(newBuildFeeCB);}
	public void setNewBuildFeeCB(String newBuildFeeCB) {this.newBuildFeeCB = checkSet(newBuildFeeCB);}
	public String getNewDeprUnitCB() {return checkGet(newDeprUnitCB);}
	public void setNewDeprUnitCB(String newDeprUnitCB) {this.newDeprUnitCB = checkSet(newDeprUnitCB);}
	public String getNewDeprPark() {return checkGet(newDeprPark);}
	public void setNewDeprPark(String newDeprPark) {this.newDeprPark = checkSet(newDeprPark);}
	public String getNewDeprUnit() {return checkGet(newDeprUnit);}
	public void setNewDeprUnit(String newDeprUnit) {this.newDeprUnit = checkSet(newDeprUnit);}
	public String getNewDeprUnit1() {return checkGet(newDeprUnit1);}
	public void setNewDeprUnit1(String newDeprUnit1) {this.newDeprUnit1 = checkSet(newDeprUnit1);}
	public String getNewDeprAccounts() {return checkGet(newDeprAccounts);}
	public void setNewDeprAccounts(String newDeprAccounts) {this.newDeprAccounts = checkSet(newDeprAccounts);}
	public String getNewScrapValue() {return checkGet(newScrapValue);}
	public void setNewScrapValue(String newScrapValue) {this.newScrapValue = checkSet(newScrapValue);}
	public String getNewDeprAmount() {return checkGet(newDeprAmount);}
	public void setNewDeprAmount(String newDeprAmount) {this.newDeprAmount = checkSet(newDeprAmount);}
	public String getNewAccumDepr() {return checkGet(newAccumDepr);}
	public void setNewAccumDepr(String newAccumDepr) {this.newAccumDepr = checkSet(newAccumDepr);}
	public String getNewApportionMonth() {return checkGet(newApportionMonth);}
	public void setNewApportionMonth(String newApportionMonth) {this.newApportionMonth = checkSet(newApportionMonth);}
	public String getNewMonthDepr() {return checkGet(newMonthDepr);}
	public void setNewMonthDepr(String newMonthDepr) {this.newMonthDepr = checkSet(newMonthDepr);}
	public String getOldDeprParkName() {return checkGet(oldDeprParkName);}
	public void setOldDeprParkName(String oldDeprParkName) {this.oldDeprParkName = checkSet(oldDeprParkName);}
	public String getOldDeprUnitName() {return checkGet(oldDeprUnitName);}
	public void setOldDeprUnitName(String oldDeprUnitName) {this.oldDeprUnitName = checkSet(oldDeprUnitName);}
	public String getOldDeprUnit1Name() {return checkGet(oldDeprUnit1Name);}
	public void setOldDeprUnit1Name(String oldDeprUnit1Name) {this.oldDeprUnit1Name = checkSet(oldDeprUnit1Name);}	
	public String getOldDeprAccountsName() {return checkGet(oldDeprAccountsName);}
	public void setOldDeprAccountsName(String oldDeprAccountsName) {this.oldDeprAccountsName = checkSet(oldDeprAccountsName);}
	public String getOldMonthDepr1() {return checkGet(oldMonthDepr1);}
	public void setOldMonthDepr1(String oldMonthDepr1) {this.oldMonthDepr1 = checkSet(oldMonthDepr1);}
	public String getOldApportionEndYM() {return checkGet(oldApportionEndYM);}
	public void setOldApportionEndYM(String oldApportionEndYM) {this.oldApportionEndYM = checkSet(oldApportionEndYM);}
	public String getNewMonthDepr1() {return checkGet(newMonthDepr1);}
	public void setNewMonthDepr1(String newMonthDepr1) {this.newMonthDepr1 = checkSet(newMonthDepr1);}
	public String getNewApportionEndYM() {return checkGet(newApportionEndYM);}
	public void setNewApportionEndYM(String newApportionEndYM) {this.newApportionEndYM = checkSet(newApportionEndYM);}
	public String getBookValue() {return checkGet(bookvalue);}
	public void setBookValue(String bookValue) {this.bookvalue = checkSet(bookValue);}
	public String getNetValue() {return checkGet(netvalue);}
	public void setNetValue(String netValue) {this.netvalue = checkSet(netValue);}	
	public String getLotNo() {return checkGet(lotNo);}
	public void setLotNo(String lotno) { this.lotNo = checkSet(lotno);}
	public String getCanChgDeprUnitCB() {return checkGet(canChgDeprUnitCB);}
	public void setCanChgDeprUnitCB(String canChgDeprUnitCB) {this.canChgDeprUnitCB = checkSet(canChgDeprUnitCB);}
	
	public String getQ_enterOrg() {return checkGet(q_enterOrg);}
	public void setQ_enterOrg(String q_enterOrg) {this.q_enterOrg = checkSet(q_enterOrg);}
	public String getQ_differenceKind() {return checkGet(q_differenceKind);}
	public void setQ_differenceKind(String q_differenceKind) {this.q_differenceKind = checkSet(q_differenceKind);}
	public String getQ_propertyNoS() {return checkGet(q_propertyNoS);}
	public void setQ_propertyNoS(String q_propertyNoS) {this.q_propertyNoS = checkSet(q_propertyNoS);}
	public String getQ_propertyNoE() {return checkGet(q_propertyNoE);}
	public void setQ_propertyNoE(String q_propertyNoE) {this.q_propertyNoE = checkSet(q_propertyNoE);}	
	public String getQ_serialNoS() {return checkGet(q_serialNoS);}
	public void setQ_serialNoS(String q_serialNoS) {this.q_serialNoS = checkSet(q_serialNoS);}
	public String getQ_serialNoE() {return checkGet(q_serialNoE);}
	public void setQ_serialNoE(String q_serialNoE) {this.q_serialNoE = checkSet(q_serialNoE);}
	public String getQ_propertyNoName1() {return checkGet(q_propertyNoName1);}
	public void setQ_propertyNoName1(String q_propertyNoName1) {this.q_propertyNoName1 = checkSet(q_propertyNoName1);}
	public String getQ_propertyNoSName() {return checkGet(q_propertyNoSName);}
	public void setQ_propertyNoSName(String qPropertyNoSName) {q_propertyNoSName = checkSet(qPropertyNoSName);}
	public String getQ_propertyNoEName() {return checkGet(q_propertyNoEName);}
	public void setQ_propertyNoEName(String qPropertyNoEName) {q_propertyNoEName = checkSet(qPropertyNoEName);}
		
	//傳回insert sql
	protected String[] getInsertSQL(){

				setSerialNo1(getMaxSerialNo1(getQ_enterOrg(),getOwnership(),getDifferenceKind(),getPropertyNo(),getSerialNo()));
				setUpdateMaster("N");
				if(getSerialNo() != null ){ 
					setSerialNo(new Common().appendZero(7, getSerialNo()));
				}
				
				editDate = new SimpleDateFormat("yyyyMMdd").format(new Date());
				
				String[] execSQLArray = new String[1];
				execSQLArray[0]=" INSERT INTO UNTDP_DEPRADJUST" +
		           "(enterorg"+
		           ",ownership"+
		           ",differencekind"+
		           ",propertyno"+
		           ",serialno"+
		           ",serialno1"+
		           ",lotno"+
		           ",updatemaster"+
		           ",propertyname1"+
		           ",bookvalue"+
		           ",netvalue"+
		           ",olddeprmethod"+
		           ",oldbuildfeecb"+
		           ",olddeprunitcb"+
		           ",olddeprpark"+
		           ",olddeprunit"+
		           ",olddeprunit1"+
		           ",olddepraccounts"+           
		           ",oldscrapvalue"+
		           ",olddepramount"+
		           ",oldaccumdepr"+
		           ",oldapportionmonth"+
		           ",oldmonthdepr"+
		           ",oldmonthdepr1"+
		           ",oldapportionendym"+
		           ",newdeprmethod"+
		           ",newbuildfeecb"+
		           ",newdeprunitcb"+
		           ",newdeprpark"+
		           ",newdeprunit"+
		           ",newdeprunit1"+
		           ",newdepraccounts"+
		           ",newscrapvalue"+
		           ",newdepramount"+
		           ",newaccumdepr"+
		           ",newapportionmonth"+
		           ",newmonthdepr"+
		           ",newmonthdepr1"+
		           ",newapportionendym"+
		           ",notes"+
		           ",editid"+
		           ",editdate"+
		           ",edittime"+
		           ") VALUES (" +
						Common.sqlChar(getQ_enterOrg()) + "," +
						Common.sqlChar(getOwnership()) + "," +
						Common.sqlChar(getDifferenceKind()) + "," +
						Common.sqlChar(getPropertyNo()) + "," +
						Common.sqlChar(getSerialNo()) + "," +
						Common.sqlChar(getSerialNo1()) + "," +
						Common.sqlChar(getLotNo()) + "," +						
						Common.sqlChar(getUpdateMaster()) + "," +
						Common.sqlChar(getPropertyName1()) + "," +
						Common.sqlChar(getBookValue()) + "," +
						Common.sqlChar(getNetValue()) + "," +
						Common.sqlChar(getOldDeprMethod()) + "," +
						Common.sqlChar(getOldBuildFeeCB()) + "," +
						Common.sqlChar(("".equals(getOldDeprUnitCB())? "N" :getOldDeprUnitCB())) + "," +
						Common.sqlChar(getOldDeprPark()) + "," +
						Common.sqlChar(getOldDeprUnit()) + "," +
						Common.sqlChar(getOldDeprUnit1()) + "," +
						Common.sqlChar(getOldDeprAccounts()) + "," +
						Common.sqlChar(getOldScrapValue()) + "," +
						Common.sqlChar(getOldDeprAmount()) + "," +
						Common.sqlChar(getOldAccumDepr()) + "," +
						Common.sqlChar(getOldApportionMonth()) + "," +
						Common.sqlChar(getOldMonthDepr()) + "," +
						Common.sqlChar(getOldMonthDepr1()) + "," +
						Common.sqlChar(Datetime.changeTaiwanYYMM(getOldApportionEndYM(),"2")) + "," +
						Common.sqlChar(getNewDeprMethod()) + "," +
						Common.sqlChar(getNewBuildFeeCB()) + "," +
						Common.sqlChar(getNewDeprUnitCB()) + "," +
						Common.sqlChar(getNewDeprPark()) + "," +
						Common.sqlChar(getNewDeprUnit()) + "," +
						Common.sqlChar(getNewDeprUnit1()) + "," +
						Common.sqlChar(getNewDeprAccounts()) + "," +
						Common.sqlChar(getNewScrapValue()) + "," +
						Common.sqlChar(getNewDeprAmount()) + "," +
						Common.sqlChar(getNewAccumDepr()) + "," +
						Common.sqlChar(getNewApportionMonth()) + "," +
						Common.sqlChar(getNewMonthDepr()) + "," +
						Common.sqlChar(getNewMonthDepr1()) + "," +
						Common.sqlChar(Datetime.changeTaiwanYYMM(getNewApportionEndYM(),"2")) + "," +
						Common.sqlChar(getNotes()) + "," +
						Common.sqlChar(getEditID()) + "," +
						Common.sqlChar(getEditDate()) + "," +
						Common.sqlChar(getEditTime()) + ")" ;
				//System.out.println("insertstr="+execSQLArray[0]);
				return execSQLArray;
	}

	
	//傳回執行insert前之檢查sql
	protected String[][] getInsertCheckSQL(){
		String[][] checkSQLArray = new String[1][4];
	 	checkSQLArray[0][0]=" select count(*) as checkResult from UNTDP_DEPRADJUST where 1=1 " + 
		" and enterorg = " + Common.sqlChar(getEnterOrg()) + 
		" and ownership = " + Common.sqlChar(getOwnership()) +		
		" and differencekind = " + Common.sqlChar(getDifferenceKind()) + 
		" and propertyno = " + Common.sqlChar(getPropertyNo()) +
		" and serialno = " + Common.sqlChar(getSerialNo()) +
		" and updateMaster='N' "+
		"";
	 	//System.out.println(checkSQLArray[0][0]);
		checkSQLArray[0][1]=">";
		checkSQLArray[0][2]="0";
		checkSQLArray[0][3]="該筆財產己經重複折舊資料調整 ，請重新輸入！";	
		return checkSQLArray;
	}

	//傳回update sql
	protected String[] getUpdateSQL(){
		String nextSerialNo1 = serialNo1 == null? "1": String.valueOf(Integer.parseInt(serialNo1)+1);
		//setUpdateMaster("Y");
		editDate = new SimpleDateFormat("yyyyMM").format(new Date());
		String[] execSQLArray = new String[1];
		execSQLArray[0]=" update UNTDP_DEPRADJUST set " +
		// 		 " serialno1 = " + Common.sqlChar(nextSerialNo1) + "," +
		//		 " updatemaster = " + Common.sqlChar(getUpdateMaster()) + "," +
		//         " propertyname1 = " + Common.sqlChar(getPropertyName1()) + "," +
	    //         " bookvalue = " + Common.sqlChar(getBookValue()) + "," +
	    //         " netvalue = " + Common.sqlChar(getNetValue()) + "," +
		         " olddeprmethod = " + Common.sqlChar(getOldDeprMethod()) + "," +
		         " oldbuildfeecb = " + Common.sqlChar(getOldBuildFeeCB()) + "," +
		         " olddeprunitcb = " + Common.sqlChar(("".equals(getOldDeprUnitCB())? "N" :getOldDeprUnitCB())) + "," +
		         " olddeprpark = " + Common.sqlChar(getOldDeprPark()) + "," +
		         " olddeprunit = " + Common.sqlChar(getOldDeprUnit()) + "," +
		//           " olddeprunit1 = " + Common.sqlChar(nextSerialNo1) + "," +
		         " olddepraccounts = " + Common.sqlChar(getOldDeprAccounts()) + "," +           
		         " oldscrapvalue = " + Common.sqlChar(getOldScrapValue()) + "," +
		         " olddepramount = " + Common.sqlChar(getOldDeprAmount()) + "," +
		         " oldaccumdepr = " + Common.sqlChar(getOldAccumDepr()) + "," +
		         " oldapportionmonth = " + Common.sqlChar(getOldApportionMonth()) + "," +
		         " oldmonthdepr = " + Common.sqlChar(getOldMonthDepr()) + "," +
	             " oldmonthdepr1 = " + Common.sqlChar(getOldMonthDepr1()) + "," +
	             " oldapportionendym = " + Common.sqlChar(Datetime.changeTaiwanYYMM(getOldApportionEndYM(),"2")) + "," +
		         " newdeprmethod = " + Common.sqlChar(getNewDeprMethod()) + "," +
		         " newbuildfeecb = " + Common.sqlChar(getNewBuildFeeCB()) + "," +
		         " newdeprunitcb = " + Common.sqlChar(("".equals(getNewDeprUnitCB())? "N" :getNewDeprUnitCB())) + "," +
		         " newdeprpark = " + Common.sqlChar(getNewDeprPark()) + "," +
		         " newdeprunit = " + Common.sqlChar(getNewDeprUnit()) + "," +
		         " newdeprunit1 = " + Common.sqlChar(getNewDeprUnit1()) + "," +
		         " newdepraccounts = " + Common.sqlChar(getNewDeprAccounts()) + "," +
		         " newscrapvalue = " + Common.sqlChar(getNewScrapValue()) + "," +
		         " newdepramount = " + Common.sqlChar(getNewDeprAmount()) + "," +
		         " newaccumdepr = " + Common.sqlChar(getNewAccumDepr()) + "," +
		         " newapportionmonth = " + Common.sqlChar(getNewApportionMonth()) + "," +
		         " newmonthdepr = " + Common.sqlChar(getNewMonthDepr()) + "," +
		         " newmonthdepr1 = " + Common.sqlChar(getNewMonthDepr1()) + "," +
	             " newapportionendym = " + Common.sqlChar(Datetime.changeTaiwanYYMM(getNewApportionEndYM(),"2")) + "," +
		         " notes = " + Common.sqlChar(getNotes()) + "," +
		         " editid = " + Common.sqlChar(getEditID()) + "," +
		         " editdate = " + Common.sqlChar(getEditDate()) + "," +
		         " edittime = " + Common.sqlChar(getEditTime()) +
			" where 1=1 " + 
			" and enterorg = " + Common.sqlChar(getEnterOrg()) + 
			" and ownership = " + Common.sqlChar(getOwnership()) +		
			" and differencekind = " + Common.sqlChar(getDifferenceKind()) + 
			" and propertyno = " + Common.sqlChar(getPropertyNo()) +
			" and serialno = " + Common.sqlChar(getSerialNo()) + 
			"";
		//System.out.println(execSQLArray[0]);
		return execSQLArray;
	}


	//傳回delete sql
	protected String[] getDeleteSQL(){
		String[] execSQLArray = new String[1];
		execSQLArray[0]=" delete UNTDP_DEPRADJUST where 1=1 " +
				" and enterorg = " + Common.sqlChar(getEnterOrg()) + 
				" and ownership = " + Common.sqlChar(getOwnership()) +		
				" and differencekind = " + Common.sqlChar(getDifferenceKind()) + 
				" and propertyno = " + Common.sqlChar(getPropertyNo()) +
				" and serialno = " + Common.sqlChar(getSerialNo()) + 
				" and serialno1 = " + Common.sqlChar(getSerialNo1()) +
				"";
		//System.out.println(execSQLArray[0]);
		return execSQLArray;
	}
	
	//傳回update sql
	public void UpdateMainFile(){
		
	    Database db = new Database();    
	    String strSQL = "";
		String[] execSQLArray;
		setEditDate(Datetime.getYYYMMDD());
		setEditTime(Datetime.getHHMMSS());

		try {
				strSQL =" update UNTBU_BUILDING set " +
					    " buildfeecb = " + Common.sqlChar(getNewBuildFeeCB()) + "," +
					    " deprunitcb = " + Common.sqlChar(("".equals(getNewDeprUnitCB())? "N" :getNewDeprUnitCB())) + "," +
					    " deprpark = " + Common.sqlChar(getNewDeprPark()) + "," +
					    " deprunit = " + Common.sqlChar(getNewDeprUnit()) + "," +
					    " deprunit1 = " + Common.sqlChar(getNewDeprUnit1()) + "," +
					    " depraccounts = " + Common.sqlChar(getNewDeprAccounts()) + "," +
					    " apportionmonth = " + Common.sqlNumber(getNewApportionMonth()) + "," +
					    " monthdepr  = " + Common.sqlNumber(getNewMonthDepr()) + "," +
					    " monthdepr1  = " + Common.sqlNumber(getNewMonthDepr1()) + "," +
					    " apportionendym = " + Common.sqlChar(Datetime.changeTaiwanYYMM(getNewApportionEndYM(),"2")) + "," +
						" editid = " + Common.sqlChar(getEditID()) + "," +
						" editdate = " + Common.sqlChar(new UNTCH_Tools()._transToCE_Year(getEditDate())) + "," +
						" edittime = " + Common.sqlChar(getEditTime()) + 
					    " where 1=1 " + 
					    " and enterorg = " + Common.sqlChar(enterOrg) +
					    " and ownership = " + Common.sqlChar(ownership) +
					    " and differencekind = " + Common.sqlChar(differenceKind) +
					    " and propertyno = " + Common.sqlChar(propertyNo) +
					    " and serialno = " + Common.sqlChar(serialNo) +
						""; 
				db.excuteSQL(strSQL);
				//System.out.println(strSQL);
				strSQL =" update UNTRF_ATTACHMENT set " +
					    " buildfeecb = " + Common.sqlChar(getNewBuildFeeCB()) + "," +
					    " deprunitcb = " + Common.sqlChar(("".equals(getNewDeprUnitCB())? "N" :getNewDeprUnitCB())) + "," +
					    " deprpark = " + Common.sqlChar(getNewDeprPark()) + "," +
					    " deprunit = " + Common.sqlChar(getNewDeprUnit()) + "," +
					    " deprunit1 = " + Common.sqlChar(getNewDeprUnit1()) + "," +
					    " depraccounts = " + Common.sqlChar(getNewDeprAccounts()) + "," +
					    " apportionmonth = " + Common.sqlNumber(getNewApportionMonth()) + "," +
					    " monthdepr  = " + Common.sqlNumber(getNewMonthDepr()) + "," + 
					    " monthdepr1  = " + Common.sqlNumber(getNewMonthDepr1()) + "," +
					    " apportionendym = " + Common.sqlChar(Datetime.changeTaiwanYYMM(getNewApportionEndYM(),"2")) + "," +
						" editid = " + Common.sqlChar(getEditID()) + "," +
						" editdate = " + Common.sqlChar(new UNTCH_Tools()._transToCE_Year(getEditDate())) + "," +
						" edittime = " + Common.sqlChar(getEditTime()) + 
					    " where 1=1 " + 
					    " and enterorg = " + Common.sqlChar(enterOrg) +
					    " and ownership = " + Common.sqlChar(ownership) +
					    " and differencekind = " + Common.sqlChar(differenceKind) +
					    " and propertyno = " + Common.sqlChar(propertyNo) +
					    " and serialno = " + Common.sqlChar(serialNo) +
						""; 
				db.excuteSQL(strSQL);
				//System.out.println(strSQL);
				strSQL =" update UNTMP_MOVABLEDETAIL set " +
					    " buildfeecb = " + Common.sqlChar(getNewBuildFeeCB()) + "," +
					    " deprunitcb = " + Common.sqlChar(("".equals(getNewDeprUnitCB())? "N" :getNewDeprUnitCB())) + "," +
					    " deprpark = " + Common.sqlChar(getNewDeprPark()) + "," +
					    " deprunit = " + Common.sqlChar(getNewDeprUnit()) + "," +
					    " deprunit1 = " + Common.sqlChar(getNewDeprUnit1()) + "," +
					    " depraccounts = " + Common.sqlChar(getNewDeprAccounts()) + "," +
					    " apportionmonth = " + Common.sqlNumber(getNewApportionMonth()) + "," +
					    " monthdepr  = " + Common.sqlNumber(getNewMonthDepr()) + "," + 
					    " monthdepr1  = " + Common.sqlNumber(getNewMonthDepr1()) + "," +
					    " apportionendym = " + Common.sqlChar(Datetime.changeTaiwanYYMM(getNewApportionEndYM(),"2")) + "," +
						" editid = " + Common.sqlChar(getEditID()) + "," +
						" editdate = " + Common.sqlChar(new UNTCH_Tools()._transToCE_Year(getEditDate())) + "," +
						" edittime = " + Common.sqlChar(getEditTime()) + 
					    " where 1=1 " + 
					    " and enterorg = " + Common.sqlChar(enterOrg) +
					    " and ownership = " + Common.sqlChar(ownership) +
					    " and differencekind = " + Common.sqlChar(differenceKind) +
					    " and propertyno = " + Common.sqlChar(propertyNo) +
					    " and serialno = " + Common.sqlChar(serialNo) +
						""; 
				//System.out.println(strSQL);
				db.excuteSQL(strSQL);
				
				strSQL =" update UNTRT_ADDPROOF set " +
					    " buildfeecb = " + Common.sqlChar(getNewBuildFeeCB()) + "," +
					    " deprunitcb = " + Common.sqlChar(("".equals(getNewDeprUnitCB())? "N" :getNewDeprUnitCB())) + "," +
					    " deprpark = " + Common.sqlChar(getNewDeprPark()) + "," +
					    " deprunit = " + Common.sqlChar(getNewDeprUnit()) + "," +
					    " deprunit1 = " + Common.sqlChar(getNewDeprUnit1()) + "," +
					    " depraccounts = " + Common.sqlChar(getNewDeprAccounts()) + "," +
					    " apportionmonth = " + Common.sqlNumber(getNewApportionMonth()) + "," +
					    " monthdepr  = " + Common.sqlNumber(getNewMonthDepr()) + "," +
					    " monthdepr1  = " + Common.sqlNumber(getNewMonthDepr1()) + "," +
					    " apportionendym = " + Common.sqlChar(Datetime.changeTaiwanYYMM(getNewApportionEndYM(),"2")) + "," +
						" editid = " + Common.sqlChar(getEditID()) + "," +
						" editdate = " + Common.sqlChar(new UNTCH_Tools()._transToCE_Year(getEditDate())) + "," +
						" edittime = " + Common.sqlChar(getEditTime()) + 
					    " where 1=1 " + 
					    " and enterorg = " + Common.sqlChar(enterOrg) +
					    " and ownership = " + Common.sqlChar(ownership) +
					    " and differencekind = " + Common.sqlChar(differenceKind) +
					    " and propertyno = " + Common.sqlChar(propertyNo) +
					    " and serialno = " + Common.sqlChar(serialNo) +
						""; 
				db.excuteSQL(strSQL);
								
				strSQL =" update UNTDP_DEPRADJUST set " +
						" updatemaster='Y' "+
						" where 1=1 " + 
					    " and enterorg = " + Common.sqlChar(enterOrg) +
					    " and ownership = " + Common.sqlChar(ownership) +
					    " and differencekind = " + Common.sqlChar(differenceKind) +
					    " and propertyno = " + Common.sqlChar(propertyNo) +
					    " and serialno = " + Common.sqlChar(serialNo) +
					    " and serialno1 = " + Common.sqlChar(serialNo1) +
						"";
				db.excuteSQL(strSQL);
				//System.out.println(strSQL);
				setUpdateMaster("Y");
				setStateUpdateSuccess();
				setErrorMsg("修改完成");	
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.closeAll();
		}	
	}
	
	/**
	 * <br>
	 * <br>目的：依主鍵查詢單一資料
	 * <br>參數：無
	 * <br>傳回：傳回本物件
	*/

	public UNTDP011F01  queryOne() throws Exception{
		Database db = new Database();
		UNTDP011F01 obj = this;
		try {
			String sql=" select *," +
				" (select organsname from SYSCA_ORGAN where organid=a.enterorg) as organsname," +
				" (select propertyname from SYSPK_PROPERTYCODE z where z.enterorg  in (a.enterorg,'000000000A') and z.propertyno = a.propertyno) as propertynomame," +
				" (select deprparkname from SYSCA_DEPRPARK z where z.enterorg=a.enterorg and z.deprparkno=a.olddeprpark) as olddeprparkname,"+
				" (select deprunitname from SYSCA_DEPRUNIT z where z.enterorg=a.enterorg and z.deprunitno=a.olddeprunit) as olddeprunitname,"+
				" (select deprunit1name from SYSCA_DEPRUNIT1 z where z.enterorg=a.enterorg and z.deprunit1no=a.olddeprunit1) as olddeprunit1name,"+
				" (select depraccountsname from SYSCA_DEPRACCOUNTS z where z.enterorg=a.enterorg and z.depraccountsno=a.olddepraccounts) as olddepraccountsname"+
				" from UNTDP_DEPRADJUST a" +
				" outer apply(" +
					" select case when COUNT(1)>0 then 'N' else 'Y' end as canChgDeprUnitCB" +
					" from UNTDP_MONTHDEPR" +
					" where enterorg=a.enterorg and ownership=a.ownership and differencekind=a.differencekind and propertyno=a.propertyno and serialno=a.serialno" +
						" and verify='Y' and deprym=(select closing1ym from UNTAC_CLOSINGPT where enterorg=a.enterorg and differencekind=a.differencekind)" +
				" )as x" +
				" where 1=1" +
				" and a.enterorg = " + Common.sqlChar(enterOrg) +
				" and a.ownership = " + Common.sqlChar(ownership) +
				" and a.differencekind = " + Common.sqlChar(differenceKind) +
				" and a.propertyno = " + Common.sqlChar(propertyNo) +
				" and a.serialno = " + Common.sqlChar(serialNo) +
				" and a.serialno1 = " + Common.sqlChar(serialNo1) +
				"";
			//System.out.println(sql);
			ResultSet rs = db.querySQL_NoChange(sql);
			if (rs.next()){
				obj.setEnterOrg(Common.get(rs.getString("enterOrg")));
				obj.setEnterOrgName(Common.get(rs.getString("organSName")));
				obj.setOwnership(Common.get(rs.getString("ownership")));
				obj.setDifferenceKind(Common.get(rs.getString("differencekind")));
				obj.setPropertyNo(Common.get(rs.getString("propertyno")));
				obj.setPropertyNoName(Common.get(rs.getString("propertynomame")));
				obj.setSerialNo(Common.get(rs.getString("serialno")));
				obj.setLotNo(Common.get(rs.getString("lotno")));
				obj.setSerialNo1(Common.get(rs.getString("serialno1")) );
				obj.setUpdateMaster(Common.get(rs.getString("updatemaster")));
				obj.setPropertyName1(Common.get(rs.getString("propertyname1")));
				obj.setBookValue(Common.get(rs.getString("bookvalue")));
				obj.setNetValue(Common.get(rs.getString("netvalue")));
				obj.setOldDeprMethod(Common.get(rs.getString("olddeprmethod")));
				obj.setOldBuildFeeCB(Common.get( rs.getString("oldbuildfeecb")));
				obj.setOldDeprUnitCB(Common.get(rs.getString("olddeprunitcb")));
				obj.setOldDeprPark(Common.get(rs.getString("olddeprpark")));
				obj.setOldDeprParkName(Common.get(rs.getString("olddeprparkname")));
				obj.setOldDeprUnit(Common.get(rs.getString("olddeprunit")));
				obj.setOldDeprUnitName(Common.get(rs.getString("olddeprunitname")));
				obj.setOldDeprUnit1(Common.get(rs.getString("olddeprunit1")));
				obj.setOldDeprUnit1Name(Common.get(rs.getString("olddeprunit1name")));
				obj.setOldDeprAccounts(Common.get( rs.getString("olddepraccounts")));
				obj.setOldDeprAccountsName(Common.get( rs.getString("olddepraccountsname")));
				obj.setOldScrapValue(Common.get(rs.getString("oldscrapvalue")));
				obj.setOldDeprAmount(Common.get(rs.getString("olddepramount")));
				obj.setOldAccumDepr(Common.get(rs.getString("oldaccumdepr")));
				obj.setOldApportionMonth(Common.get(rs.getString("oldapportionmonth")));
				obj.setOldMonthDepr(Common.get(rs.getString("oldmonthdepr")));
				obj.setOldMonthDepr1(Common.get(rs.getString("oldmonthdepr1")));
				obj.setOldApportionEndYM(Datetime.changeTaiwanYYMM(Common.get(rs.getString("oldapportionendym")),"1"));				
				obj.setNewDeprMethod(Common.get(rs.getString("newdeprmethod")));
				obj.setNewBuildFeeCB(Common.get(rs.getString("newbuildfeecb")));
				obj.setNewDeprUnitCB(Common.get(rs.getString("newdeprunitcb")));
				obj.setNewDeprPark(Common.get(rs.getString("newdeprpark")));
				obj.setNewDeprUnit(Common.get(rs.getString("newdeprunit")));
				obj.setNewDeprUnit1(Common.get(rs.getString("newdeprunit1")));
				obj.setNewDeprAccounts(Common.get(rs.getString("newdepraccounts")));
				obj.setNewScrapValue(Common.get(rs.getString("newscrapvalue")));
				obj.setNewDeprAmount(Common.get(rs.getString("newdepramount")));
				obj.setNewAccumDepr(Common.get(rs.getString("newaccumdepr")));
				obj.setNewApportionMonth(Common.get(rs.getString("newapportionmonth")));
				obj.setNewMonthDepr(Common.get(rs.getString("newmonthdepr")));
				obj.setNewMonthDepr1(Common.get(rs.getString("newmonthdepr1")));
				obj.setNewApportionEndYM(Datetime.changeTaiwanYYMM(Common.get(rs.getString("newapportionendym")),"1"));
				obj.setNotes(Common.get(rs.getString("notes")));
				obj.setEditID(Common.get(rs.getString("editid")));
				obj.setEditDate(Common.get(rs.getString("editdate")));
				obj.setEditTime(Common.get(rs.getString("edittime")));	
				obj.setCanChgDeprUnitCB(Common.get(rs.getString("canChgDeprUnitCB")));
			}
			rs.getStatement().close();
			rs.close();
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
		ArrayList objList = new ArrayList();	
		UNTDP011F01 obj = this;
		try {
			String sql=" select a.*, ( select codename from SYSCA_CODE where codekindid='DFK' and codeid=a.differencekind ) as differencekindname from UNTDP_DEPRADJUST a where 1=1 ";  
				
			if (!"".equals(getQ_enterOrg())){
				sql+=" and a.enterorg like '" + getQ_enterOrg() +"%' " ;
			}
			
			if (!"".equals(getQ_differenceKind())) {
				sql+=" and a.differencekind = " + Common.sqlChar(getQ_differenceKind())+"  " ;
			}
			
			if(!"".equals(getQ_propertyNoS())  && !"".equals(getQ_propertyNoE())){
				sql+=" and a.propertyno between " + Common.sqlChar(getQ_propertyNoS()) + " and " + Common.sqlChar(getQ_propertyNoE()) +" ";
			}else if(!"".equals(getQ_propertyNoS())){
				sql+=" and a.propertyno >= " + Common.sqlChar(getQ_propertyNoS());
			}else if(!"".equals(getQ_propertyNoE())){
				sql+=" and a.propertyno <= " + Common.sqlChar(getQ_propertyNoE());
			}
			
			if(!"".equals(getQ_serialNoS())  && !"".equals(getQ_serialNoE())){
				sql+=" and a.serialno between " + Common.sqlChar(getQ_serialNoS()) + " and " + Common.sqlChar(getQ_serialNoE()) +" ";
			}else if(!"".equals(getQ_serialNoS())){
				sql+=" and a.serialno >= " + Common.sqlChar(getQ_serialNoS());
			}else if(!"".equals(getQ_serialNoE())){
				sql+=" and a.serialno <= " + Common.sqlChar(getQ_serialNoE());
			} 
			
			if (!"".equals(getQ_propertyNoName1()) && getQ_propertyNoName1() != null ){
				sql+=" and a.propertyname1 like " + Common.sqlChar(getQ_propertyNoName1()+"%")+" " ;
			}
			//System.out.println(sql);
			ResultSet rs = db.querySQL(sql+" order by a.ownership,a.differencekind,a.propertyno,a.lotno,a.serialno,a.serialno1 desc", true);
			processCurrentPageAttribute(rs);
			if (getTotalRecord() > 0) {
				int count = getRecordStart();
				int end = getRecordEnd();
				do {
					if (count > end)
						break;
					String rowArray[]=new String[15];
					rowArray[0]=Common.get(rs.getString("differencekind")); 
					rowArray[1]=Common.get(rs.getString("differencekindname")); 
					rowArray[2]=Common.get(rs.getString("propertyno")); 
					rowArray[3]=Common.get(rs.getString("serialno")); 
					rowArray[4]=Common.get(rs.getString("propertyname1")); 
					rowArray[5]=Common.get(rs.getString("oldapportionmonth")); 
					rowArray[6]=Common.get(rs.getString("newapportionmonth")); 
					rowArray[7]=Common.get(rs.getString("olddeprunit")); 
					rowArray[8]=Common.get(rs.getString("newdeprunit")); 
					rowArray[9]=Common.get(rs.getString("olddepraccounts")); 
					rowArray[10]=Common.get(rs.getString("newdepraccounts")); 					
					rowArray[11]=Common.get(rs.getString("olddeprunitcb")); 
					rowArray[12]=Common.get(rs.getString("newdeprunitcb"));
					rowArray[13]=Common.get(rs.getString("serialno1"));
					rowArray[14]=Common.get(rs.getString("ownership"));
					objList.add(rowArray);
					count++;			
				} while (rs.next());
			}
			rs.getStatement().close();
			rs.close();		
			setStateQueryAllSuccess();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.closeAll();
		}
		return objList;
	}
	
	
	/**
	 * <br>目的：取得XX單編號－號
	 * <br>參數：enterorg  , ownership  , differencekind , propertyno  , serialno   
	 * <br>傳回：最大的XX單編號-號
	 */
	static public String getMaxSerialNo1(String enterOrg,String ownership,String differencekind,String propertyno,String serialno){
		Database db = new Database();
		ResultSet rs;	
		String getMaxSerialNo1 = "";
		try {		
			String sql = "select case max(SerialNo1) when null then '' else (max(SerialNo1)+1) end as SerialNo1 from UNTDP_DEPRADJUST "+
						" where enterOrg=" + Common.sqlChar(enterOrg) +
						" and ownership = " + Common.sqlChar(ownership) +
						" and differencekind = " + Common.sqlChar(differencekind) +
						" and propertyno = " + Common.sqlChar(propertyno) +
						" and serialno = " + Common.sqlChar(serialno) +
						"";
			rs = db.querySQL(sql);
			if (rs.next()){
				getMaxSerialNo1 = rs.getString("SerialNo1");
				if("".equals(Common.get(getMaxSerialNo1))){
					getMaxSerialNo1 = "1";
				}else{
					getMaxSerialNo1 = getMaxSerialNo1;
				}
				
			} else {
				getMaxSerialNo1 = "1";
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.closeAll();
		}
		return getMaxSerialNo1;
	}	
}