package unt.ch;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import util.*;
import TDlib_Simple.tools.src.StringTools;

public class UNTCH001F04 extends UNTCH001Q{

	String notes;
	String editID;
	String editDate;
	String editTime;
	
	public String getNotes() {return checkGet(notes);}
	public void setNotes(String notes) {this.notes = checkSet(notes);}
	public String getEditID() {return checkGet(editID);}
	public void setEditID(String editID) {this.editID = checkSet(editID);}
	public String getEditDate() {return checkGet(editDate);}
	public void setEditDate(String editDate) {this.editDate = checkSet(editDate);}
	public String getEditTime() {return checkGet(editTime);}
	public void setEditTime(String editTime) {this.editTime = checkSet(editTime);}

	public ArrayList queryAll() throws Exception{
		
		String sql = "select * from (" +				
					"select " +
							"1 as orderID," +
							"enterdate as ownershipDate," +
							"'增加單' as prooftype," +
							"(select proofYear + '年' + proofDoc + '字第' + proofNo + '號' from UNTLA_ADDPROOF z where z.enterorg = a.enterorg and z.ownership = a.ownership and z.differencekind = a.differencekind and z.caseno = a.caseno) as proofData," +
							"originalNote," +
							"0 as oldNetValue," +
							"originalbv as addNetValue," +
							"0 as reducebookvalue," +
							"originalbv as newNetValue" +
						" from UNTLA_LAND a where 1=1" +
						condition() +
					" union" +
					" select " +
							"2 as orderID," +
							"adjustdate as ownershipDate," +
							"'增減值單' as prooftype," +
							"(select proofYear + '年' + proofDoc + '字第' + proofNo + '號' from UNTLA_ADJUSTPROOF z where z.enterorg = a.enterorg and z.ownership = a.ownership and z.differencekind = a.differencekind and z.caseno = a.caseno) as proofData," +
							"null as originalNote," +
							"oldNetValue," +
							"addNetValue," +
							"reducebookvalue," +
							"newNetValue" +
						" from UNTLA_ADJUSTDETAIL a where 1=1" +
						condition() +
					" union" +
					" select " +
							"3 as orderID," +
							"reducedate as ownershipDate," +
							"'減損單' as prooftype," +
							"(select proofYear + '年' + proofDoc + '字第' + proofNo + '號' from UNTLA_REDUCEPROOF z where z.enterorg = a.enterorg and z.ownership = a.ownership and z.differencekind = a.differencekind and z.caseno = a.caseno) as proofData," +
							"null as originalNote," +
							"bookvalue as oldNetValue," +
							"0 as addNetValue," +
							"bookvalue as reducebookvalue," +
							"0 as newNetValue" +
						" from UNTLA_REDUCEDETAIL a where 1=1" +
						condition() +
					" union" +
					" select " +
							"1 as orderID," +
							"enterdate as ownershipDate," +
							"'增加單' as prooftype," +
							"(select proofYear + '年' + proofDoc + '字第' + proofNo + '號' from UNTLA_ADDPROOF z where z.enterorg = a.enterorg and z.ownership = a.ownership and z.differencekind = a.differencekind and z.caseno = a.caseno) as proofData," +
							"originalNote," +
							"0 as oldNetValue," +
							"originalbv as addNetValue," +
							"0 as reducebookvalue," +
							"originalbv as newNetValue" +
						" from UNTBU_BUILDING a where 1=1" +
						condition() +
					" union" +
					" select " +
							"2 as orderID," +
							"adjustdate as ownershipDate," +
							"'增減值單' as prooftype," +
							"(select proofYear + '年' + proofDoc + '字第' + proofNo + '號' from UNTLA_ADJUSTPROOF z where z.enterorg = a.enterorg and z.ownership = a.ownership and z.differencekind = a.differencekind and z.caseno = a.caseno) as proofData," +
							"null as originalNote," +
							"oldNetValue," +
							"addNetValue," +
							"reducebookvalue," +
							"newNetValue" +
						" from UNTBU_ADJUSTDETAIL a where 1=1" +
						condition() +
					" union" +
					" select " +
							"3 as orderID," +
							"reducedate as ownershipDate," +
							"'減損單' as prooftype," +
							"(select proofYear + '年' + proofDoc + '字第' + proofNo + '號' from UNTLA_REDUCEPROOF z where z.enterorg = a.enterorg and z.ownership = a.ownership and z.differencekind = a.differencekind and z.caseno = a.caseno) as proofData," +
							"null as originalNote," +
							"bookvalue as oldNetValue," +
							"0 as addNetValue," +
							"bookvalue as reducebookvalue," +
							"0 as newNetValue" +
						" from UNTBU_REDUCEDETAIL a where 1=1" +
						condition() +
					" union" +
					" select " +
							"1 as orderID," +
							"enterdate as ownershipDate," +
							"'增加單' as prooftype," +
							"(select proofYear + '年' + proofDoc + '字第' + proofNo + '號' from UNTLA_ADDPROOF z where z.enterorg = a.enterorg and z.ownership = a.ownership and z.differencekind = a.differencekind and z.caseno = a.caseno) as proofData," +
							"originalNote," +
							"0 as oldNetValue," +
							"originalbv as addNetValue," +
							"0 as reducebookvalue," +
							"originalbv as newNetValue" +
						" from UNTRF_ATTACHMENT a where 1=1" +
						condition() +
					" union" +
					" select " +
							"2 as orderID," +
							"adjustdate as ownershipDate," +
							"'增減值單' as prooftype," +
							"(select proofYear + '年' + proofDoc + '字第' + proofNo + '號' from UNTLA_ADJUSTPROOF z where z.enterorg = a.enterorg and z.ownership = a.ownership and z.differencekind = a.differencekind and z.caseno = a.caseno) as proofData," +
							"null as originalNote," +
							"oldNetValue," +
							"addNetValue," +
							"reducebookvalue," +
							"newNetValue" +
						" from UNTRF_ADJUSTDETAIL a where 1=1" +
						condition() +
					" union" +
					" select " +
							"3 as orderID," +
							"reducedate as ownershipDate," +
							"'減損單' as prooftype," +
							"(select proofYear + '年' + proofDoc + '字第' + proofNo + '號' from UNTLA_REDUCEPROOF z where z.enterorg = a.enterorg and z.ownership = a.ownership and z.differencekind = a.differencekind and z.caseno = a.caseno) as proofData," +
							"null as originalNote," +
							"bookvalue as oldNetValue," +
							"0 as addNetValue," +
							"bookvalue as reducebookvalue," +
							"0 as newNetValue" +
						" from UNTRF_REDUCEDETAIL a where 1=1" +
						condition() +
					" union" +
					" select " +
							"1 as orderID," +
							"enterdate as ownershipDate," +
							"'增加單' as prooftype," +
							"(select proofYear + '年' + proofDoc + '字第' + proofNo + '號' from UNTLA_ADDPROOF z where z.enterorg = a.enterorg and z.ownership = a.ownership and z.differencekind = a.differencekind and z.caseno = a.caseno) as proofData," +
							"null as originalNote," +
							"0 as oldNetValue," +
							"originalbv as addNetValue," +
							"0 as reducebookvalue," +
							"originalbv as newNetValue" +
						" from UNTMP_MOVABLEDETAIL a where 1=1" +
						condition() +
					" union" +
					" select " +
							"2 as orderID," +
							"adjustdate as ownershipDate," +
							"'增減值單' as prooftype," +
							"(select proofYear + '年' + proofDoc + '字第' + proofNo + '號' from UNTLA_ADJUSTPROOF z where z.enterorg = a.enterorg and z.ownership = a.ownership and z.differencekind = a.differencekind and z.caseno = a.caseno) as proofData," +
							"null as originalNote," +
							"oldNetValue," +
							"addNetValue," +
							"reducebookvalue," +
							"newNetValue" +
						" from UNTMP_ADJUSTDETAIL a where 1=1" +
						condition() +
					" union" +
					" select " +
							"3 as orderID," +
							"reducedate as ownershipDate," +
							"'減損單' as prooftype," +
							"(select proofYear + '年' + proofDoc + '字第' + proofNo + '號' from UNTLA_REDUCEPROOF z where z.enterorg = a.enterorg and z.ownership = a.ownership and z.differencekind = a.differencekind and z.caseno = a.caseno) as proofData," +
							"null as originalNote," +
							"oldNetValue," +
//							"(case when adjustnetvalue >= 0 then adjustnetvalue else 0 end)  as addNetValue," +
//							"(case when adjustnetvalue < 0 then adjustnetvalue else 0 end) as reduceNetValue," +
							"0 as addNetValue," +
							"adjustnetvalue as reduceNetValue," +
							"newNetValue" +
						" from UNTMP_REDUCEDETAIL a where 1=1" +
						condition() +
					" union" +
					" select " +
							"4 as orderID," +
							"dealdate as ownershipDate," +
							"'廢品處理單' as prooftype," +
							"(select proofYear + '年' + proofDoc + '字第' + proofNo + '號' from UNTMP_DEALPROOF z where z.enterorg = a.enterorg and 1 = a.ownership and z.differencekind = a.differencekind and z.caseno1 = a.caseno1) as proofData," +
							"null as originalNote," +
							"0 as oldNetValue," +
							"0 as addNetValue," +
							"0 as reducebookvalue," +
							"0 as newNetValue" +
							" from UNTMP_DEALDETAIL a where 1=1" +
						condition() +						
					" union" +
					" select " +
							"1 as orderID," +
							"enterdate as ownershipDate," +
							"'增加單' as prooftype," +
							"proofYear + '年' + proofDoc + '字第' + proofNo + '號' as proofData," +
							"originalNote," +
							"0 as oldNetValue," +
							"originalbv as addNetValue," +
							"0 as reducebookvalue," +
							"originalbv as newNetValue" +
						" from UNTVP_ADDPROOF a where 1=1" +
						condition() +
					" union" +
					" select " +
							"2 as orderID," +
							"adjustdate as ownershipDate," +
							"'增減值單' as prooftype," +
							"proofYear + '年' + proofDoc + '字第' + proofNo + '號' as proofData," +
							"null as originalNote," +
							"oldbookvalue as oldNetValue," +
							"addbookvalue as addNetValue," +
							"reducebookvalue," +
							"newbookvalue as newNetValue" +
						" from UNTVP_ADJUSTPROOF a where 1=1" +
						condition() +
					" union" +
					" select " +
							"3 as orderID," +
							"adjustdate as ownershipDate," +
							"'減損單' as prooftype," +
							"proofYear + '年' + proofDoc + '字第' + proofNo + '號' as proofData," +
							"null as originalNote," +
							"bookvalue as oldNetValue," +
							"0 as addNetValue," +
							"bookvalue as reducebookvalue," +
							"0 as newNetValue" +
						" from UNTVP_REDUCEPROOF a where 1=1" +
						condition() +
					" union" +
					" select " +
							"1 as orderID," +
							"enterdate as ownershipDate," +
							"'增加單' as prooftype," +
							"proofYear + '年' + proofDoc + '字第' + proofNo + '號' as proofData," +
							"originalNote," +
							"0 as oldNetValue," +
							"originalbv as addNetValue," +
							"0 as reducebookvalue," +
							"originalbv as newNetValue" +
						" from UNTRT_ADDPROOF a where 1=1" +
						condition() +
					" union" +
					" select " +
							"2 as orderID," +
							"adjustdate as ownershipDate," +
							"'增減值單' as prooftype," +
							"proofYear + '年' + proofDoc + '字第' + proofNo + '號' as proofData," +
							"null as originalNote," +
							"oldbookvalue as oldNetValue," +
							"addbookvalue as addNetValue," +
							"reducebookvalue," +
							"newbookvalue as newNetValue" +
						" from UNTRT_ADJUSTPROOF a where 1=1" +
						condition() +
					" union" +
					" select " +
							"3 as orderID," +
							"reducedate as ownershipDate," +
							"'減損單' as prooftype," +
							"proofYear + '年' + proofDoc + '字第' + proofNo + '號' as proofData," +
							"null as originalNote," +
							"bookvalue as oldNetValue," +
							"0 as addNetValue," +
							"bookvalue as reducebookvalue," +
							"0 as newNetValue" +
						" from UNTRT_REDUCEPROOF a where 1=1" +
						condition() +
					") a order by a.orderid";
		
		Database db = null;
		ArrayList objList=new ArrayList();
		try {
			db = new Database();	
			StringTools st = new StringTools();
			UNTCH_Tools ut = new UNTCH_Tools();
			ResultSet rs = db.querySQL(sql,true);			
			processCurrentPageAttribute(rs);			
			if (getTotalRecord() > 0) {
				int count = getRecordStart();
				int end = getRecordEnd();
				do {
					if (count > end)
						break;
				String rowArray[]=new String[8];
				rowArray[0] = ut._transToROC_Year(checkGet(rs.getString("ownershipDate")));
				rowArray[1] = checkGet(rs.getString("prooftype"));
				rowArray[2] = checkGet(rs.getString("proofData"));
				rowArray[3] = checkGet(rs.getString("originalNote"));
				rowArray[4] = st._getMoneyFormat(checkGet(rs.getString("oldNetValue")));
				rowArray[5] = st._getMoneyFormat(checkGet(rs.getString("addNetValue")));
				rowArray[6] = st._getMoneyFormat(checkGet(rs.getString("reducebookvalue")));
				rowArray[7] = st._getMoneyFormat(checkGet(rs.getString("newNetValue")));
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
	
		private String condition(){
			String result = null; 
			
			result = " and enterorg = " + Common.sqlChar(getEnterOrg()) +
					" and ownership = " + Common.sqlChar(getOwnership()) +
					" and differencekind = " + Common.sqlChar(getDifferenceKind()) +
					" and propertyno = " + Common.sqlChar(getPropertyNo()) +
					" and serialno = " + Common.sqlChar(getSerialNo()) +
					" and verify = 'Y'";
			
			return result;
		}

}