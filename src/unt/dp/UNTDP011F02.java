package unt.dp;

import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import unt.ch.UNTCH_Tools;
import util.*;

public class UNTDP011F02 extends QueryBean{

	private String enterOrg;
	private String enterOrgName;
	private String ownership;
	private String differenceKind;
	private String propertyNo;
	private String propertyNoName;
	private String serialNo;
	private String serialNo1;
	private String deprPark;
	private String deprUnit;
	private String deprUnit1;
	private String deprAccounts;
	private String deprPark_h;
	private String deprUnit_h;
	private String deprUnit1_h;
	private String deprAccounts_h;
	private String deprPercent;
	private String notes;
	private String editID;
	private String editDate;
	private String isHistory;
	private String deprShareAmount;
	private String bookvalue;
	
	public String getEnterOrg() {return checkGet(enterOrg);}
	public void setEnterOrg(String enterOrg) {this.enterOrg = checkSet(enterOrg);}
	public String getEnterOrgName() {return checkGet(enterOrgName);}
	public void setEnterOrgName(String enterOrgName) {this.enterOrgName = checkSet(enterOrgName);}
	public String getOwnership() {return checkGet(ownership);}
	public void setOwnership(String ownership) {this.ownership = checkSet(ownership);}
	public String getPropertyNo() {return checkGet(propertyNo);}
	public void setPropertyNo(String propertyNo) {this.propertyNo = checkSet(propertyNo);}
	public String getPropertyNoName() {return checkGet(propertyNoName);}
	public void setPropertyNoName(String propertyNoName) {this.propertyNoName = checkSet(propertyNoName);}
	public String getNotes() {return checkGet(notes);}
	public void setNotes(String notes) {this.notes = checkSet(notes);}
	public String getEditID() {return checkGet(editID);}
	public void setEditID(String editID) {this.editID = checkSet(editID);}
	public String getEditDate() {return checkGet(editDate);}
	public void setEditDate(String editDate) {this.editDate = checkSet(editDate);}
	public String getDifferenceKind() {return checkGet(differenceKind);}
	public void setDifferenceKind(String differenceKind) {this.differenceKind = checkSet(differenceKind);}
	public String getSerialNo() {return checkGet(serialNo);}
	public void setSerialNo(String serialNo) {this.serialNo = checkSet(serialNo);}
	public String getSerialNo1() {return checkGet(serialNo1);}
	public void setSerialNo1(String serialNo1) {this.serialNo1 = checkSet(serialNo1);}
	public String getDeprPark() {return checkGet(deprPark);}
	public void setDeprPark(String deprPark) {this.deprPark = checkSet(deprPark);}
	public String getDeprUnit() {return checkGet(deprUnit);}
	public void setDeprUnit(String deprUnit) {this.deprUnit = checkSet(deprUnit);}	
	public String getDeprUnit1() {return checkGet(deprUnit1);}
	public void setDeprUnit1(String deprUnit1) {this.deprUnit1 = checkSet(deprUnit1);}
	public String getDeprAccounts() {return checkGet(deprAccounts);}
	public void setDeprAccounts(String deprAccounts) {this.deprAccounts = checkSet(deprAccounts);}
	public String getDeprPercent() {return checkGet(deprPercent);}
	public void setDeprPercent(String deprPercent) {this.deprPercent = checkSet(deprPercent);}
	public String getIsHistory() {return checkGet(isHistory);}
	public void setIsHistory(String isHistory) {this.isHistory = checkSet(isHistory);}
	public String getDeprPark_h() {return checkGet(deprPark_h);}
	public void setDeprPark_h(String deprPark) {this.deprPark_h = checkSet(deprPark);}
	public String getDeprUnit_h() {return checkGet(deprUnit_h);}
	public void setDeprUnit_h(String deprUnit) {this.deprUnit_h = checkSet(deprUnit);}	
	public String getDeprUnit1_h() {return checkGet(deprUnit1_h);}
	public void setDeprUnit1_h(String deprUnit1) {this.deprUnit1_h = checkSet(deprUnit1);}
	public String getDeprAccounts_h() {return checkGet(deprAccounts_h);}
	public void setDeprAccounts_h(String deprAccounts) {this.deprAccounts_h = checkSet(deprAccounts);}
	public String getDeprShareAmount() {return checkGet(deprShareAmount);}
	public void setDeprShareAmount(String deprShareAmount) {this.deprShareAmount = checkSet(deprShareAmount);}
	public String getBookValue() {return checkGet(bookvalue);}
	public void setBookValue(String bookValue) {this.bookvalue = checkSet(bookValue);}
	
	public String checkDeprPercent_All(){
		String condition = " and enterorg = " + Common.sqlChar(getEnterOrg()) + 
						" and ownership = " + Common.sqlChar(getOwnership()) + 
						" and differencekind = " + Common.sqlChar(getDifferenceKind()) +
						" and propertyno = " + Common.sqlChar(getPropertyNo()) +
						" and serialno = " + Common.sqlChar(getSerialNo()) +
						" and (ishistory = 'N' or ishistory is null)";
		
		return new UNTCH_Tools()._queryData("SUM(deprpercent)")._from("UNTCH_DEPRPERCENT")._with(condition)._toString();
	}
	
	public String checkDeprPercent_queryOne(){
		String condition = " and enterorg = " + Common.sqlChar(getEnterOrg()) + 
						" and ownership = " + Common.sqlChar(getOwnership()) + 
						" and differencekind = " + Common.sqlChar(getDifferenceKind()) +
						" and propertyno = " + Common.sqlChar(getPropertyNo()) +
						" and serialno = " + Common.sqlChar(getSerialNo()) +
						("".equals(checkGet(getSerialNo1()))?"":" and serialno1 != " + Common.sqlChar(getSerialNo1())) +
						" and (ishistory = 'N' or ishistory is null)";
		
		return new UNTCH_Tools()._queryData("SUM(deprpercent)")._from("UNTCH_DEPRPERCENT")._with(condition)._toString();
	}
	
	public String checkDeprShareAmount_All(){
		String condition = " and enterorg = " + Common.sqlChar(getEnterOrg()) + 
						" and ownership = " + Common.sqlChar(getOwnership()) + 
						" and differencekind = " + Common.sqlChar(getDifferenceKind()) +
						" and propertyno = " + Common.sqlChar(getPropertyNo()) +
						" and serialno = " + Common.sqlChar(getSerialNo()) +
						" and (ishistory = 'N' or ishistory is null)";
		
		return new UNTCH_Tools()._queryData("SUM(deprshareamount)")._from("UNTCH_DEPRPERCENT")._with(condition)._toString();
	}
	
	//傳回insert sql
	protected String[] getInsertSQL(){


				if(getSerialNo() != null ){ 
					serialNo = new Common().appendZero(7, serialNo);
				}
				setSerialNo1("0");
				editDate = new SimpleDateFormat("yyyyMMdd").format(new Date());
				
				String[] execSQLArray = new String[1];
				execSQLArray[0]=" INSERT INTO UNTCH_DEPRPERCENT "+
		           "(enterorg"+
		           ",ownership"+
		           ",differencekind"+
		           ",propertyno"+
		           ",serialno"+
		           ",serialno1"+
		           ",deprpark"+
		           ",deprunit"+
		           ",deprunit1"+
		           ",depraccounts"+
		           ",deprpercent"+
		           ",ishistory"+
		           ",notes"+
		           ",editid"+
		           ",editdate"+
		           ",edittime"+
		           ",deprShareAmount"+
		           ") select "+ 
						Common.sqlChar(getEnterOrg()) + "," +
						Common.sqlChar(getOwnership()) + "," +
						Common.sqlChar(getDifferenceKind()) + "," +
						Common.sqlChar(getPropertyNo()) + "," +
						Common.sqlChar(getSerialNo()) + "," +
						" isnull(MAX(serialno1), 0) + 1 ," +
						Common.sqlChar(getDeprPark()) + "," +
						Common.sqlChar(getDeprUnit()) + "," +
						Common.sqlChar(getDeprUnit1()) + "," +
						Common.sqlChar(getDeprAccounts()) + "," +
						Common.sqlChar(getDeprPercent()) + "," +
						Common.sqlChar(getIsHistory()) + "," +
						Common.sqlChar(getNotes()) + "," +
						Common.sqlChar(getEditID()) + "," +
						Common.sqlChar(getEditDate()) + "," +
						Common.sqlChar(getEditTime()) + "," +
						Common.sqlChar(getDeprShareAmount()) + 
					" from UNTCH_DEPRPERCENT " +
					" where 1=1 " +
					" and enterorg = " + Common.sqlChar(getEnterOrg()) + 
					" and ownership = " + Common.sqlChar(getOwnership()) +		
					" and differencekind = " + Common.sqlChar(getDifferenceKind()) + 
					" and propertyno = " + Common.sqlChar(getPropertyNo()) +
					" and serialno = " + Common.sqlChar(getSerialNo());
						;
				return execSQLArray;
	}

	
	//傳回執行insert前之檢查sql
	protected String[][] getInsertCheckSQL(){
		String[][] checkSQLArray = new String[1][4];
		checkSQLArray[0][0]=" select count(*) as checkResult from UNTCH_DEPRPERCENT where 1=1 " + 
		" and enterorg = " + Common.sqlChar(getEnterOrg()) + 
		" and ownership = " + Common.sqlChar(getOwnership()) +		
		" and differencekind = " + Common.sqlChar(getDifferenceKind()) + 
		" and propertyno = " + Common.sqlChar(getPropertyNo()) +
		" and serialno = " + Common.sqlChar(getSerialNo()) +
		" and deprpark = " + Common.sqlChar(getDeprPark()) +
		" and deprunit = " + Common.sqlChar(getDeprUnit()) +
		" and deprunit1 = " + Common.sqlChar(getDeprUnit1()) +
		" and depraccounts = " + Common.sqlChar(getDeprAccounts());
		checkSQLArray[0][1]=">";
		checkSQLArray[0][2]="0";
		checkSQLArray[0][3]="該筆廠商名稱己重複，請重新輸入！";	
		return checkSQLArray;
	}

	//傳回update sql
	protected String[] getUpdateSQL(){
		String nextSerialNo1 = serialNo1 == null? "1": String.valueOf(Integer.parseInt(serialNo1)+1);
		editDate = new SimpleDateFormat("yyyyMM").format(new Date());
		String[] execSQLArray = new String[1];
		execSQLArray[0]=" update UNTCH_DEPRPERCENT set " +
				 " deprpark = " + Common.sqlChar(getDeprPark()) + "," +
				 " deprunit = " + Common.sqlChar(getDeprUnit()) + "," +
				 " deprunit1 = " + Common.sqlChar(getDeprUnit1()) + "," +
		         " depraccounts = " + Common.sqlChar(getDeprAccounts()) + "," +
		         " deprpercent = " + Common.sqlChar(getDeprPercent()) + "," +
		         " ishistory = " + Common.sqlChar(getIsHistory()) + "," +
		         " notes = " + Common.sqlChar(getNotes()) + "," +
		         " editid = " + Common.sqlChar(getEditID()) + "," +
		         " editdate = " + Common.sqlChar(getEditDate()) + "," +
		         " edittime = " + Common.sqlChar(getEditTime()) + "," +
		         " deprShareAmount = " + Common.sqlChar(getDeprShareAmount()) +
			" where 1=1 " + 
			" and enterorg = " + Common.sqlChar(getEnterOrg()) + 
			" and ownership = " + Common.sqlChar(getOwnership()) +		
			" and differencekind = " + Common.sqlChar(getDifferenceKind()) + 
			" and propertyno = " + Common.sqlChar(getPropertyNo()) +
			" and serialno = " + Common.sqlChar(getSerialNo()) + 
			" and serialno1 = " + Common.sqlChar(getSerialNo1()) +
			"";
		return execSQLArray;
	}

	//傳回執行update前之檢查sql
	protected String[][] getUpdateCheckSQL(){
		String[][] checkSQLArray = new String[1][4];
	 	checkSQLArray[0][0]=" select count(*) as checkResult from UNTCH_DEPRPERCENT where 1=1 " + 
		" and enterorg = " + Common.sqlChar(getEnterOrg()) + 
		" and ownership = " + Common.sqlChar(getOwnership()) +		
		" and differencekind = " + Common.sqlChar(getDifferenceKind()) + 
		" and propertyno = " + Common.sqlChar(getPropertyNo()) +
		" and serialno = " + Common.sqlChar(getSerialNo()) +
		" and deprpark = " + Common.sqlChar(getDeprPark()) +
		" and deprunit = " + Common.sqlChar(getDeprUnit()) +
		" and deprunit1 = " + Common.sqlChar(getDeprUnit1()) +
		" and depraccounts = " + Common.sqlChar(getDeprAccounts()) +
		" and not (" +
		Common.sqlChar(getDeprPark()) + "=" + Common.sqlChar(getDeprPark_h()) +
		"and " + Common.sqlChar(getDeprUnit()) + "=" + Common.sqlChar(getDeprUnit_h()) +
		"and " + Common.sqlChar(getDeprUnit1()) + "=" + Common.sqlChar(getDeprUnit1_h()) +
		"and " + Common.sqlChar(getDeprAccounts()) + "=" + Common.sqlChar(getDeprAccounts_h()) +
		")";
		checkSQLArray[0][1]=">";
		checkSQLArray[0][2]="0";
		checkSQLArray[0][3]="該筆廠商名稱己重複，請重新輸入！";	
		return checkSQLArray;
	}
	
	//傳回delete sql
	protected String[] getDeleteSQL(){
		String[] execSQLArray = new String[1];
		execSQLArray[0]=" delete UNTCH_DEPRPERCENT where 1=1 " +
				" and enterorg = " + Common.sqlChar(getEnterOrg()) + 
				" and ownership = " + Common.sqlChar(getOwnership()) +		
				" and differencekind = " + Common.sqlChar(getDifferenceKind()) + 
				" and propertyno = " + Common.sqlChar(getPropertyNo()) +
				" and serialno = " + Common.sqlChar(getSerialNo()) + 
				" and serialno1 = " + Common.sqlChar(getSerialNo1()) +
				"";
		return execSQLArray;
	}
	
	
	/**
	 * <br>
	 * <br>目的：依主鍵查詢單一資料
	 * <br>參數：無
	 * <br>傳回：傳回本物件
	*/

	public UNTDP011F02  queryOne() throws Exception{
		Database db = new Database();
		UNTDP011F02 obj = this;
		try {
			String sql=" select a.*, b.organsname " +
				" from UNTCH_DEPRPERCENT a, SYSCA_ORGAN b where a.enterorg=b.organid " +
				" and a.enterorg = " + Common.sqlChar(getEnterOrg()) +
				" and a.ownership = " + Common.sqlChar(getOwnership()) +
				" and a.differencekind = " + Common.sqlChar(getDifferenceKind()) +
				" and a.propertyno = " + Common.sqlChar(getPropertyNo()) +
				" and a.serialno = " + Common.sqlChar(getSerialNo()) +
				" and a.serialno1 = " + Common.sqlChar(getSerialNo1()) +
				"";
			ResultSet rs = db.querySQL(sql);
			if (rs.next()){
				obj.setEnterOrg(Common.get(rs.getString("enterOrg")));
				obj.setEnterOrgName(Common.get( rs.getString("organSName")));
				obj.setOwnership(Common.get(rs.getString("ownership")));
				obj.setDifferenceKind(Common.get(rs.getString("differencekind")));
				obj.setPropertyNo(Common.get(rs.getString("propertyno")));
				obj.setSerialNo(Common.get(rs.getString("serialno")));
				obj.setSerialNo1(Common.get(rs.getString("serialno1")));
				obj.setDeprPark(Common.get(rs.getString("deprpark")));
				obj.setDeprUnit(Common.get(rs.getString("deprunit")));
				obj.setDeprUnit1(Common.get(rs.getString("deprunit1")));
				obj.setDeprAccounts(Common.get(rs.getString("depraccounts")));
				obj.setDeprPercent(Common.get(rs.getString("deprpercent")));
				obj.setIsHistory(Common.get( rs.getString("ishistory")));
				obj.setNotes(Common.get( rs.getString("notes")));
				obj.setEditID(Common.get(rs.getString("editid")));
				obj.setEditDate(Common.get( rs.getString("editdate")));
				obj.setEditTime(Common.get(rs.getString("edittime")));	
				obj.setDeprShareAmount(Common.get(rs.getString("deprShareAmount")));
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
		UNTDP011F02 obj = this;
		try {
			String sql=" select a.*, " +
					"b.deprparkname,   "+
					"c.deprunitname, " +
					"e.deprunit1name, " +
					"d.depraccountsname " +
					"from UNTCH_DEPRPERCENT a  " +
			" left join SYSCA_DEPRPARK b on a.enterorg = b.enterorg and a.deprpark=b.deprparkno " +
			" left join SYSCA_DEPRUNIT c on a.enterorg = c.enterorg and a.deprunit=c.deprunitno " +
			" left join SYSCA_DEPRUNIT1 e on a.enterorg = e.enterorg and a.deprunit1=e.deprunit1no " +
			" left join SYSCA_DEPRACCOUNTS d on a.enterorg = d.enterorg and a.depraccounts=d.depraccountsno ";
			sql+=" where 1=1 ";
			if (!"".equals(getEnterOrg())) {
				sql+=" and a.enterorg = " + Common.sqlChar(getEnterOrg());
			}
			if (!"".equals(getOwnership())) {
				sql+=" and a.ownership = " + Common.sqlChar(getOwnership());
			}
			if (!"".equals(getDifferenceKind())) {
				sql+=" and a.differencekind = " + Common.sqlChar(getDifferenceKind());
			}
			if (!"".equals(getPropertyNo())) {
				sql+=" and a.propertyno = " + Common.sqlChar(getPropertyNo());
			}
			if (!"".equals(getSerialNo())) {
				sql+=" and a.serialno = " + Common.sqlChar(getSerialNo());
			}
			ResultSet rs = db.querySQL(sql+" order by a.propertyno, a.serialno", true);
			processCurrentPageAttribute(rs);
			if (getTotalRecord() > 0) {
				int count = getRecordStart();
				int end = getRecordEnd();
				do {
					if (count > end)
						break;
					String rowArray[] = new String[14];
					rowArray[0]=Common.get(rs.getString("propertyno")); 
					rowArray[1]=Common.get(rs.getString("serialno")); 
					rowArray[2]=Common.get(rs.getString("deprpark")); 
					rowArray[3]=Common.get(rs.getString("deprparkname")); 
					rowArray[4]=Common.get(rs.getString("deprunit")); 
					rowArray[5]=Common.get(rs.getString("deprunitname")); 
					rowArray[6]=Common.get(rs.getString("deprunit1name")); 
					rowArray[7]=Common.get(rs.getString("depraccounts")); 
					rowArray[8]=Common.get(rs.getString("depraccountsname")); 
					rowArray[9]=Common.get(rs.getString("deprpercent")); 
					rowArray[10]=Common.get(rs.getString("differencekind"));
					rowArray[11]=Common.get(rs.getString("enterorg")); 
					rowArray[12]=Common.get(rs.getString("ownership")); 
					rowArray[13]=Common.get(rs.getString("serialno1")); 
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
}