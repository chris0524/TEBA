package unt.ch;

import java.sql.ResultSet;
import java.util.ArrayList;

import util.Common;
import util.Database;
import util.SuperBean2;
import TDlib_Simple.tools.src.StringTools;

public class UNTCH001F05 extends UNTCH001Q{

	public ArrayList queryAll() throws Exception{
		Database db = null;
		ArrayList objList=new ArrayList();
		
		try {
			StringBuilder sql = new StringBuilder();
			
			sql.append("select a.*")
				.append(" from UNTDP_MONTHDEPR a where 1=1")
				.append(" and enterorg = " + Common.sqlChar(getEnterOrg()))
				.append(" and ownership = " + Common.sqlChar(getOwnership()))
				.append(" and differenceKind = " + Common.sqlChar(getDifferenceKind()))
				.append(" and propertyNo = " + Common.sqlChar(getPropertyNo()))
				.append(" and serialNo = " + Common.sqlChar(getSerialNo()))
				.append(" order by deprYM, serialNo1");
			//============================================================
			
			log._execLogDebug(sql.toString());
			//============================================================
			
			db = new Database();
			ResultSet rs = db.querySQL(sql.toString(),true);			
			UNTCH_Tools ut = new UNTCH_Tools();
			StringTools st = new StringTools();
			processCurrentPageAttribute(rs);			
			if (getTotalRecord() > 0) {
				int count = getRecordStart();
				int end = getRecordEnd();
				do {
					if (count > end)
						break;
				String rowArray[]=new String[8];
				rowArray[0] = ut._transToROC_Year(Common.get(rs.getString("deprYM")));
				//rowArray[1] = ut._getEmpName(Common.get(rs.getString("enterorg")),Common.get(rs.getString("editID")));
				rowArray[1] = ut._transToROC_Year(Common.get(rs.getString("editDate")));
				
				if("Y".equals(getDeprUnitCB()) || "Y".equals(getOriginalDeprUnitCB())){
					rowArray[2] = st._getMoneyFormat(Common.get(rs.getString("scaledBookValue")));
					rowArray[3] = st._getMoneyFormat(Common.get(rs.getString("scrapValue")));
					rowArray[4] = st._getMoneyFormat(Common.get(rs.getString("scaledOldNetValue")));
					rowArray[5] = st._getMoneyFormat(Common.get(rs.getString("scaledMonthDepr")));
					rowArray[6] = st._getMoneyFormat(Common.get(rs.getString("scaledNewAccumDepr")));
					rowArray[7] = st._getMoneyFormat(Common.get(rs.getString("scaledNewNetValue")));				
					
				}else{
					rowArray[2] = st._getMoneyFormat(Common.get(rs.getString("bookValue")));
					rowArray[3] = st._getMoneyFormat(Common.get(rs.getString("scrapValue")));
					rowArray[4] = st._getMoneyFormat(Common.get(rs.getString("oldNetValue")));
					rowArray[5] = st._getMoneyFormat(Common.get(rs.getString("monthDepr")));
					rowArray[6] = st._getMoneyFormat(Common.get(rs.getString("newAccumDepr")));
					rowArray[7] = st._getMoneyFormat(Common.get(rs.getString("newNetValue")));			
						
				}
				
				objList.add(rowArray);
				count++;
				} while (rs.next());
			}
			setStateQueryAllSuccess();
		} catch (Exception e) {
			log._execLogError(e.getMessage());
		} finally {
			db.closeAll();
		}
		return objList;
		
	}
	//======================================================================
	private String enterOrg;
	private String ownership;
	private String differenceKind;
	private String propertyNo;
	private String serialNo;
	private String serialNo1;
	private String deprYM;
	private String editID;
	private String editDate;
	private String bookValue;
	private String scrapValue;
	private String oldNetValue;
	private String monthDepr;
	private String newAccumDepr;
	private String newNetValue;
	private String deprPercent;
	public String getEnterOrg() {return checkGet(enterOrg);}
	public void setEnterOrg(String enterOrg) {this.enterOrg = checkSet(enterOrg);}
	public String getOwnership() {return checkGet(ownership);}
	public void setOwnership(String ownership) {this.ownership = checkSet(ownership);}
	public String getDifferenceKind() {return checkGet(differenceKind);}
	public void setDifferenceKind(String differenceKind) {this.differenceKind = checkSet(differenceKind);}
	public String getPropertyNo() {return checkGet(propertyNo);}
	public void setPropertyNo(String propertyNo) {this.propertyNo = checkSet(propertyNo);}
	public String getSerialNo() {return checkGet(serialNo);}
	public void setSerialNo(String serialNo) {this.serialNo = checkSet(serialNo);}
	public String getSerialNo1() {return checkGet(serialNo1);}
	public void setSerialNo1(String serialNo1) {this.serialNo1 = checkSet(serialNo1);}
	public String getDeprYM() {return checkGet(deprYM);}
	public void setDeprYM(String deprYM) {this.deprYM = checkSet(deprYM);}
	public String getEditID() {return checkGet(editID);}
	public void setEditID(String editID) {this.editID = checkSet(editID);}
	public String getEditDate() {return checkGet(editDate);}
	public void setEditDate(String editDate) {this.editDate = checkSet(editDate);}
	public String getBookValue() {return checkGet(bookValue);}
	public void setBookValue(String bookValue) {this.bookValue = checkSet(bookValue);}
	public String getScrapValue() {return checkGet(scrapValue);}
	public void setScrapValue(String scrapValue) {this.scrapValue = checkSet(scrapValue);}
	public String getOldNetValue() {return checkGet(oldNetValue);}
	public void setOldNetValue(String oldNetValue) {this.oldNetValue = checkSet(oldNetValue);}
	public String getMonthDepr() {return checkGet(monthDepr);}
	public void setMonthDepr(String monthDepr) {this.monthDepr = checkSet(monthDepr);}
	public String getNewAccumDepr() {return checkGet(newAccumDepr);}
	public void setNewAccumDepr(String newAccumDepr) {this.newAccumDepr = checkSet(newAccumDepr);}
	public String getNewNetValue() {return checkGet(newNetValue);}
	public void setNewNetValue(String newNetValue) {this.newNetValue = checkSet(newNetValue);}
	public String getDeprPercent() {return checkGet(deprPercent);}
	public void setDeprPercent(String deprPercent) {this.deprPercent = checkSet(deprPercent);}

	private String deprUnitCB;
	private String originalDeprUnitCB;
	private String lotNo;
	private String caseNo;
	public String getDeprUnitCB() {return checkGet(deprUnitCB);}
	public void setDeprUnitCB(String deprUnitCB) {this.deprUnitCB = checkSet(deprUnitCB);}
	public String getOriginalDeprUnitCB() {return checkGet(originalDeprUnitCB);}
	public void setOriginalDeprUnitCB(String originalDeprUnitCB) {this.originalDeprUnitCB = checkSet(originalDeprUnitCB);}
	public String getLotNo() {return checkGet(lotNo);}
	public void setLotNo(String lotNo) {this.lotNo = checkSet(lotNo);}
	public String getCaseNo() {return checkGet(caseNo);}
	public void setCaseNo(String caseNo) {this.caseNo = checkSet(caseNo);}
	
}