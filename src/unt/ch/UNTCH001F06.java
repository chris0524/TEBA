package unt.ch;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import util.*;
import TDlib_Simple.tools.src.StringTools;

public class UNTCH001F06 extends UNTCH001Q{

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
		
		try {
			String sql=" select" +
						" a.newmovedate," + 
						" (select unitname from UNTMP_KEEPUNIT z where z.enterorg = a.enterorg and z.unitno = a.oldkeepunit) as oldkeepunit," +
						" (select keepername from UNTMP_KEEPER z where z.enterorg = a.enterorg and z.keeperno = a.oldkeeper) as oldkeeper," +
						" a.oldplace1," +
						" (select unitname from UNTMP_KEEPUNIT z where z.enterorg = a.enterorg and z.unitno = a.olduseunit) as olduseunit," +
						" (select keepername from UNTMP_KEEPER z where z.enterorg = a.enterorg and z.keeperno = a.olduserno) as olduserno," +

						" (select unitname from UNTMP_KEEPUNIT z where z.enterorg = a.enterorg and z.unitno = a.newkeepunit) as newkeepunit," +
						" (select keepername from UNTMP_KEEPER z where z.enterorg = a.enterorg and z.keeperno = a.newkeeper) as newkeeper," +
						" a.newplace1," +
						" (select unitname from UNTMP_KEEPUNIT z where z.enterorg = a.enterorg and z.unitno = a.newuseunit) as newuseunit," +
						" (select keepername from UNTMP_KEEPER z where z.enterorg = a.enterorg and z.keeperno = a.newuserno) as newuserno" +						
					" from UNTMP_MOVEDETAIL a" +
					" where 1=1 " +
						" and a.enterorg = " + Common.sqlChar(this.getEnterOrg()) +
						" and a.ownership = " + Common.sqlChar(this.getOwnership()) +
						" and a.differenceKind = " + Common.sqlChar(this.getDifferenceKind()) +
						" and a.propertyno = " + Common.sqlChar(this.getPropertyNo()) +
						" and a.serialno = " + Common.sqlChar(this.getSerialNo()) +
						" and a.verify = 'Y'";	

			ResultSet rs = db.querySQL(sql+ " order by a.newmovedate",true);
			UNTCH_Tools ut = new UNTCH_Tools();
			processCurrentPageAttribute(rs);			
			if (getTotalRecord() > 0) {
				int count = getRecordStart();
				int end = getRecordEnd();
				do {
					if (count > end)
						break;
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