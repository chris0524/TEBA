/*
*<br>程式目的：非消耗品主檔資料維護－增加單資料
*<br>程式代號：untne001f
*<br>程式日期：0941028
*<br>程式作者：cherry
*<br>--------------------------------------------------------
*<br>修改作者　　修改日期　　　修改目的
*<br>--------------------------------------------------------
*/

package unt.ne;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Map;

import TDlib_Simple.tools.src.StringTools;

import unt.ch.UNTCH_Tools;
import util.*;

public class UNTNE057F_2 extends SuperBean2{



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
							"a.enterdate as ownershipDate," +
							"'增加單' as prooftype," +
							"(select proofYear + '年' + proofDoc + '字第' + proofNo + '號' from UNTNE_ADDPROOF z where z.enterorg = a.enterorg and z.ownership = a.ownership and z.differencekind = a.differencekind and z.caseno = a.caseno) as proofData," +
							"f.originalNote," +
							"0 as oldNetValue," +
							"a.originalBV as addNetValue," +
							"0 as reduceNetValue," +
							"a.bookValue as newNetValue" +
							" from UNTNE_NONEXPDETAIL a left join UNTNE_Nonexp f on a.enterOrg=f.enterOrg and a.ownership=f.ownership and a.propertyNo=f.propertyNo and a.lotNo=f.lotNo and f.serialNoS<=a.serialNo and f.serialNoE>=a.serialNo and a.differenceKind=f.differenceKind" +
							" where 1=1" +
						condition() +
					" union" +
					" select " +
							"2 as orderID," +
							"adjustdate as ownershipDate," +
							"'增減值單' as prooftype," +
							"(select proofYear + '年' + proofDoc + '字第' + proofNo + '號' from UNTNE_ADDPROOF z where z.enterorg = a.enterorg and z.ownership = a.ownership and z.differencekind = a.differencekind and z.caseno = a.caseno) as proofData," +
							"null as originalNote," +
							"oldBookValue," +
							"addBookValue," +
							"reduceBookValue," +
							"newBookValue" +
						" from UNTNE_ADJUSTDETAIL a where 1=1" +
						condition() +
					" union" +
					" select " +
							"3 as orderID," +
							"reducedate as ownershipDate," +
							"'減損單' as prooftype," +
							"(select proofYear + '年' + proofDoc + '字第' + proofNo + '號' from UNTNE_ADDPROOF z where z.enterorg = a.enterorg and z.ownership = a.ownership and z.differencekind = a.differencekind and z.caseno = a.caseno) as proofData," +
							"null as originalNote," +
							"oldBookValue as oldNetValue," +
							"0 as addNetValue," +
							"adjustBookValue as reduceNetValue," +
							"0 as newNetValue" +
						" from UNTNE_REDUCEDETAIL a where 1=1" +
						condition() +
					") a order by a.orderid";
		
		Database db = null;
		ArrayList objList=new ArrayList();
		int counter=0;
		try {
			db = new Database();	
			StringTools st = new StringTools();
			UNTCH_Tools ut = new UNTCH_Tools();
			ResultSet rs = db.querySQL(sql);			
			while (rs.next()){
				counter++;
				String rowArray[]=new String[8];
				rowArray[0] = ut._transToROC_Year(checkGet(rs.getString("ownershipDate")));
				rowArray[1] = checkGet(rs.getString("prooftype"));
				rowArray[2] = checkGet(rs.getString("proofData"));
				rowArray[3] = checkGet(rs.getString("originalNote"));
				rowArray[4] = st._getMoneyFormat(checkGet(rs.getString("oldNetValue")));
				rowArray[5] = st._getMoneyFormat(checkGet(rs.getString("addNetValue")));
				rowArray[6] = st._getMoneyFormat(checkGet(rs.getString("reduceNetValue")));
				rowArray[7] = st._getMoneyFormat(checkGet(rs.getString("newNetValue")));
				objList.add(rowArray);
				if (counter==getListLimit()){
					setErrorMsg(getListLimitError());
					break;
				}
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
			
			result =" and a.enterorg = " + Common.sqlChar(getEnterOrg()) +
					" and a.ownership = " + Common.sqlChar(getOwnership()) +
					" and a.differencekind = " + Common.sqlChar(getDifferenceKind()) +
					" and a.caseno = " + Common.sqlChar(getCaseNo()) + 
					" and a.propertyno = " + Common.sqlChar(getPropertyNo()) +
					" and a.serialno = " + Common.sqlChar(getSerialNo()) +
					" and a.verify = 'Y'";
			
			return result;
		}
}


