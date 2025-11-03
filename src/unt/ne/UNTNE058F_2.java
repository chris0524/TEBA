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

import unt.ch.UNTCH_Tools;
import util.Common;
import util.Database;
import util.SuperBean2;

public class UNTNE058F_2 extends SuperBean2{


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
		Database db = new Database();
		ArrayList objList=new ArrayList();
		int counter=0;
		try {
			String sql=" select" +
						" a.newmovedate," + 
						" (select unitname from UNTMP_KEEPUNIT z where z.enterorg = a.enterorg and z.unitno = a.oldkeepunit) as oldkeepunit," +
						" (select keepername from UNTMP_KEEPER z where z.enterorg = a.enterorg and z.keeperno = a.oldkeeper) as oldkeeper," +
						" (select placename from SYSCA_PLACE z where z.enterorg = a.enterorg and z.placeno = a.oldplace1) as oldplace1," +
						" (select unitname from UNTMP_KEEPUNIT z where z.enterorg = a.enterorg and z.unitno = a.olduseunit) as olduseunit," +
						" (select keepername from UNTMP_KEEPER z where z.enterorg = a.enterorg and z.keeperno = a.olduserno) as olduserno," +

						" (select unitname from UNTMP_KEEPUNIT z where z.enterorg = a.enterorg and z.unitno = a.newkeepunit) as newkeepunit," +
						" (select keepername from UNTMP_KEEPER z where z.enterorg = a.enterorg and z.keeperno = a.newkeeper) as newkeeper," +
						" (select placename from SYSCA_PLACE z where z.enterorg = a.enterorg and z.placeno = a.newplace1) as newplace1," +
						" (select unitname from UNTMP_KEEPUNIT z where z.enterorg = a.enterorg and z.unitno = a.newuseunit) as newuseunit," +
						" (select keepername from UNTMP_KEEPER z where z.enterorg = a.enterorg and z.keeperno = a.newuserno) as newuserno" +						
					" from UNTNE_MOVEDETAIL a" +
					" where 1=1 " +
						" and a.enterorg = " + Common.sqlChar(this.getEnterOrg()) +
						" and a.ownership = " + Common.sqlChar(this.getOwnership()) +
						" and a.differenceKind = " + Common.sqlChar(this.getDifferenceKind()) +
						" and a.propertyno = " + Common.sqlChar(this.getPropertyNo()) +
						" and a.serialno = " + Common.sqlChar(this.getSerialNo())+
						" and a.verify = 'Y'";	
			
//System.out.print("3223==>"+sql);
			ResultSet rs = db.querySQL(sql+ " order by a.newmovedate",true);
			UNTCH_Tools ut = new UNTCH_Tools();
			while (rs.next()){
				counter++;
				String rowArray[]=new String[11];
				rowArray[0] = ut._transToROC_Year(checkGet(rs.getString("newmovedate"))); 
				rowArray[1] = checkGet(rs.getString("oldkeepunit")); 
				rowArray[2] = checkGet(rs.getString("oldkeeper")); 
				rowArray[3] = checkGet(rs.getString("oldplace1")); 
				rowArray[4] = checkGet(rs.getString("olduseunit")); 
				rowArray[5] = checkGet(rs.getString("olduserno")); 
				rowArray[6] = checkGet(rs.getString("newkeepunit")); 
				rowArray[7] = checkGet(rs.getString("newkeeper")); 
				rowArray[8] = checkGet(rs.getString("newplace1")); 
				rowArray[9] = checkGet(rs.getString("newuseunit")); 
				rowArray[10] = checkGet(rs.getString("newuserno"));  
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
}


