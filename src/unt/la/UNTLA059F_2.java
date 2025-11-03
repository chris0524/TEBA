/*
*<br>程式目的：土地合併分割重測重劃作業－增加單明細分割挑選
*<br>程式代號：untla059f
*<br>程式日期：
*<br>程式作者：Yuan-Ren Jheng
*<br>--------------------------------------------------------
*<br>修改作者　　修改日期　　　修改目的
*<br>--------------------------------------------------------
*/

package unt.la;

import java.sql.ResultSet;
import java.util.ArrayList;

import util.Common;
import unt.ch.UNTCH_Tools;
import util.*;

public class UNTLA059F_2 extends UNTLA054Q{
	
	String enterOrg;
	String ownership;
	String differenceKind;
	String editID;
	String editDate;
	String editTime;
	
	String before_signNo;
	String before_area;
	String before_holdNume;
	String before_holdDeno;
	String before_holdArea;
	String before_bookValue;

	String after_signNo1;
	String after_signNo2;
	String after_signNo3;
	String after_signNo4;
	String after_signNo5;
	String after_area;
	String after_holdNume;
	String after_holdDeno;
	String after_holdArea;

	String after_bulletinDate;
	String after_valueUnit;
	
	String enterOrgName;
	String before_signNo1;
	String before_signNo2;
	String before_signNo3;
	String before_signNo4;
	String before_signNo5;
	String after_signNo;
	String caseNo_Merge;
	String caseNo_Reduce;
	String propertyNo_Reduce;
	String propertyNo_ReduceName;
	String serialNo_Reduce;	
	String caseNo_Add;
	String propertyNo_Add;
	String propertyNo_AddName;
	String serialNo_Add;
//	String cause_Add;
//	String cause_before;	
	String mergeDivision;
	String mergeDivisionBatch;

	String amount;
	String message;
	String bulletinDate;
	String valueUnit;
	String[] strKeySet;
	String[] strKeySetMid;
	
	public String getEnterOrg() {return checkGet(enterOrg);}
	public void setEnterOrg(String enterOrg) {this.enterOrg = checkSet(enterOrg);}
	public String getOwnership() {return checkGet(ownership);}
	public void setOwnership(String ownership) {this.ownership = checkSet(ownership);}
	public String getDifferenceKind() {return checkGet(differenceKind);}
	public void setDifferenceKind(String differenceKind) {this.differenceKind = checkSet(differenceKind);}
	public String getEditID() {return checkGet(editID);}
	public void setEditID(String editID) {this.editID = checkSet(editID);}
	public String getEditDate() {return checkGet(editDate);}
	public void setEditDate(String editDate) {this.editDate = checkSet(editDate);}
	public String getEditTime() {return checkGet(editTime);}
	public void setEditTime(String editTime) {this.editTime = checkSet(editTime);}
	public String getBefore_signNo() {return checkGet(before_signNo);}
	public void setBefore_signNo(String beforeSignNo) {before_signNo = checkSet(beforeSignNo);}
	public String getBefore_area() {return checkGet(before_area);}
	public void setBefore_area(String beforeArea) {before_area = checkSet(beforeArea);}
	public String getBefore_holdNume() {return checkGet(before_holdNume);}
	public void setBefore_holdNume(String beforeHoldNume) {before_holdNume = checkSet(beforeHoldNume);}
	public String getBefore_holdDeno() {return checkGet(before_holdDeno);}
	public void setBefore_holdDeno(String beforeHoldDeno) {before_holdDeno = checkSet(beforeHoldDeno);}
	public String getBefore_holdArea() {return checkGet(before_holdArea);}
	public void setBefore_holdArea(String beforeHoldArea) {before_holdArea = checkSet(beforeHoldArea);}
	public String getBefore_bookValue() {return checkGet(before_bookValue);}
	public void setBefore_bookValue(String beforeBookValue) {before_bookValue = checkSet(beforeBookValue);}
	public String getAfter_signNo1() {return checkGet(after_signNo1);}
	public void setAfter_signNo1(String afterSignNo1) {after_signNo1 = checkSet(afterSignNo1);}
	public String getAfter_signNo2() {return checkGet(after_signNo2);}
	public void setAfter_signNo2(String afterSignNo2) {after_signNo2 = checkSet(afterSignNo2);}
	public String getAfter_signNo3() {return checkGet(after_signNo3);}
	public void setAfter_signNo3(String afterSignNo3) {after_signNo3 = checkSet(afterSignNo3);}
	public String getAfter_signNo4() {return checkGet(after_signNo4);}
	public void setAfter_signNo4(String afterSignNo4) {after_signNo4 = checkSet(afterSignNo4);}
	public String getAfter_signNo5() {return checkGet(after_signNo5);}
	public void setAfter_signNo5(String afterSignNo5) {after_signNo5 = checkSet(afterSignNo5);}
	public String getAfter_area() {return checkGet(after_area);}
	public void setAfter_area(String afterArea) {after_area = checkSet(afterArea);}
	public String getAfter_holdNume() {return checkGet(after_holdNume);}
	public void setAfter_holdNume(String afterHoldNume) {after_holdNume = checkSet(afterHoldNume);}
	public String getAfter_holdDeno() {return checkGet(after_holdDeno);}
	public void setAfter_holdDeno(String afterHoldDeno) {after_holdDeno = checkSet(afterHoldDeno);}
	public String getAfter_holdArea() {return checkGet(after_holdArea);}
	public void setAfter_holdArea(String afterHoldArea) {after_holdArea = checkSet(afterHoldArea);}
	public String getAfter_bulletinDate() {return checkGet(after_bulletinDate);}
	public void setAfter_bulletinDate(String afterBulletinDate) {after_bulletinDate = checkSet(afterBulletinDate);}
	public String getAfter_valueUnit() {return checkGet(after_valueUnit);}
	public void setAfter_valueUnit(String afterValueUnit) {after_valueUnit = checkSet(afterValueUnit);}

	public String getEnterOrgName() {return checkGet(enterOrgName);}
	public void setEnterOrgName(String enterOrgName) {this.enterOrgName = checkSet(enterOrgName);}	
	public String getBefore_signNo1() {return checkGet(before_signNo1);}
	public void setBefore_signNo1(String beforeSignNo1) {before_signNo1 = checkSet(beforeSignNo1);}
	public String getBefore_signNo2() {return checkGet(before_signNo2);}
	public void setBefore_signNo2(String beforeSignNo2) {before_signNo2 = checkSet(beforeSignNo2);}
	public String getBefore_signNo3() {return checkGet(before_signNo3);}
	public void setBefore_signNo3(String beforeSignNo3) {before_signNo3 = checkSet(beforeSignNo3);}
	public String getBefore_signNo4() {return checkGet(before_signNo4);}
	public void setBefore_signNo4(String beforeSignNo4) {before_signNo4 = checkSet(beforeSignNo4);}
	public String getBefore_signNo5() {return checkGet(before_signNo5);}
	public void setBefore_signNo5(String beforeSignNo5) {before_signNo5 = checkSet(beforeSignNo5);}
	public String getAfter_signNo() {return checkGet(after_signNo);}
	public void setAfter_signNo(String afterSignNo) {after_signNo = checkSet(afterSignNo);}
	public String getPropertyNo_ReduceName() {return checkGet(propertyNo_ReduceName);}
	public void setPropertyNo_ReduceName(String propertyNoReduceName) {propertyNo_ReduceName = checkSet(propertyNoReduceName);}
	public String getPropertyNo_AddName() {return checkGet(propertyNo_AddName);}
	public void setPropertyNo_AddName(String propertyNoName_Add) {propertyNo_AddName = checkSet(propertyNo_AddName);}
//	public String getCause_Add() {return checkGet(cause_Add);}
//	public void setCause_Add(String causeAdd) {cause_Add = checkSet(causeAdd);}
//	public String getCause_before() {return checkGet(cause_before);}
//	public void setCause_before(String causeBefore) {cause_before = checkSet(causeBefore);}
	
	
	
	public String getMergeDivision() {return checkGet(mergeDivision);}
	public void setMergeDivision(String mergeDivision) {this.mergeDivision = checkSet(mergeDivision);}
	public String getMergeDivisionBatch() {return checkGet(mergeDivisionBatch);}
	public void setMergeDivisionBatch(String mergeDivisionBatch) {this.mergeDivisionBatch = checkSet(mergeDivisionBatch);}
	
	public String getAmount() {return checkGet(amount);}
	public void setAmount(String amount) {this.amount = checkSet(amount);}
	public String getMessage() {return checkGet(message);}
	public void setMessage(String message) {this.message = checkSet(message);}
	public String getBulletinDate() {return checkGet(bulletinDate);}
	public void setBulletinDate(String bulletinDate) {this.bulletinDate = checkSet(bulletinDate);	}
	public String getValueUnit() {return checkGet(valueUnit);}
	public void setValueUnit(String valueUnit) {this.valueUnit = checkSet(valueUnit);}
	
	public String[] getsKeySet(){ return strKeySet; }
	public void setsKeySet(String[] s){ strKeySet=s; }
	public String[] getsKeySetMid(){ return strKeySetMid; }
	public void setsKeySetMid(String[] s){ strKeySetMid=s; }

	
	String sourceKind_Add;
	String sourceKind_before;		
	public String getSourceKind_Add() {return checkGet(sourceKind_Add);}
	public void setSourceKind_Add(String sourceKind_Add) {this.sourceKind_Add = checkSet(sourceKind_Add);}
	public String getSourceKind_before() {return checkGet(sourceKind_before);}
	public void setSourceKind_before(String sourceKind_before) {this.sourceKind_before = checkSet(sourceKind_before);}
		


	UNTLA054Q_data qbean;
	
	public UNTLA054Q_data getParameterData_Reduce(){		
		qbean=new UNTLA054Q_data();
		qbean.tableName="UNTLA_ReduceDetail";
		qbean.caseNo=this.getCaseNo_Reduce();
		qbean.enterOrg=this.getEnterOrg();
		qbean.ownership=this.getOwnership();
		qbean.propertyNo=this.getPropertyNo_Reduce();
		return qbean;
	}
	
	public UNTLA054Q_data getParameterData_Add(){		
		qbean=new UNTLA054Q_data();
		qbean.tableName="UNTLA_Land";
		qbean.caseNo=this.getCaseNo_Add();
		qbean.enterOrg=this.getEnterOrg();
		qbean.ownership=this.getOwnership();
		qbean.propertyNo=this.getPropertyNo_Add();
		return qbean;
	}
	
	



		protected void beforeWork_Add_dropTable_Untla_Land_Temp(){
			String sql="DROP TABLE UNTLA_LAND_" +  Common.get(this.getEditID()).replace(".", "");
			
			this.execSQL_ForNoResult_NoChange(sql, true);
		}		
		protected void beforeWork_Add_createTable_Untla_Land_Temp(){
			String sql="select top 1 * into UNTLA_LAND_" + Common.get( Common.get(this.getEditID()).replace(".", "")).replace(".", "") + " from UNTLA_LAND";
			
			this.execSQL_ForNoResult_NoChange(sql);
		}
		protected void beforeWork_Add_delete_Untla_Land_Temp(){
			String sql="DELETE FROM UNTLA_LAND_" +  Common.get(this.getEditID()).replace(".", "");
			
			this.execSQL_ForNoResult_NoChange(sql);
			
		}
		protected void beforeWork_Add_dropTable_Untla_Land_Temp2(){
			String sql="DROP TABLE UNTLA_LAND_" +  Common.get(this.getEditID()).replace(".", "") + "2";
			
			this.execSQL_ForNoResult_NoChange(sql, true);
		
		}
		protected void beforeWork_Add_createTable_Untla_Land_Temp2_from_Untla_Land(){
			String sql=" select b.* into UNTLA_LAND_"+ Common.get(this.getEditID()).replace(".", "")+"2 from UNTLA_REDUCEDETAIL a, UNTLA_LAND b where 1=1"+
						" and a.enterorg=b.enterorg and a.ownership=b.ownership and a.propertyno=b.propertyno and a.serialno=b.serialno and a.differencekind = b.differencekind"+
						" and a.enterorg = " + Common.sqlChar(this.getEnterOrg()) +
						" and a.ownership = " + Common.sqlChar(this.getOwnership()) +
						" and a.caseno = " + Common.sqlChar(this.getCaseNo_Reduce()) +
						" and a.differencekind = " + Common.sqlChar(this.getDifferenceKind()) +
						" and a.propertyno = " + Common.sqlChar(this.getPropertyNo_Reduce()) +
						" and a.serialno = " + Common.sqlChar(this.getSerialNo_Reduce());
			
			this.execSQL_ForNoResult_NoChange(sql);
		}
		protected void execGetMergeDivisionData(){
			String mergeDivisionBatchStr = afterWork_Add_getMaxMergeDivisionBatchPlusOne(this.getCaseNo_Reduce());
			this.setMergeDivision(this.getCaseNo_Merge());
			this.setMergeDivisionBatch(mergeDivisionBatchStr);		
		}
		protected void beforeWork_Add_update_Untla_Land_Temp2_forSerialNo_and_otherData(int i){
			String sql="update UNTLA_LAND_"+ Common.get(this.getEditID()).replace(".", "")+"2 set"+
					" serialno = (" +
									" select RIGHT(REPLICATE('0', 7) + CAST(case when MAX(c.serialno) is null then 1 else (MAX(c.serialno) + 1) end as NVARCHAR), 7) from (" +
										" select MAX(b.serialno) as serialno from UNTLA_LAND b" +
											" where 1=1 and b.enterorg = enterorg and b.ownership = ownership and b.propertyno = propertyno and b.differencekind = differencekind" +
										" union" +
										" select MAX(b.serialno) as serialno from UNTLA_LAND_" + Common.get(this.getEditID()).replace(".", "") + " b" +
											" where 1=1 and b.enterorg = enterorg and b.ownership = ownership and b.propertyno = propertyno and b.differencekind = differencekind" +
									" ) c" +
								" ) " + "," +
					" caseno = " + Common.sqlChar(this.getCaseNo_Add()) + "," +
					" originaldate = null" + "," +
					" mergedivision = " + Common.sqlChar(this.getCaseNo_Merge()) + "," +
					" mergedivisionbatch = " + Common.sqlChar(this.getMergeDivisionBatch());
			
			this.execSQL_ForNoResult_NoChange(sql);
		
		}
		protected void beforeWork_Add_update_Untla_Land_Temp2_forNotes(String type){
			StringBuilder stb = new StringBuilder();
			
			stb.append("update UNTLA_LAND_"+ Common.get(this.getEditID()).replace(".", "")+"2 set notes = ");

			if("402".equals(type) || "403".equals(type)){
				stb.append("'分割自' + (select top 1 z.signdesc from SYSCA_SIGN z where z.signno=substring(signno,1,7)) + substring(signno,8,4) + '-' + substring(signno,12,4) + '地號。' ");
				
			}else if("404".equals(type)){
				stb.append("'重測自' + (select top 1 z.signdesc from SYSCA_SIGN z where z.signno=substring(signno,1,7)) + substring(signno,8,4) + '-' + substring(signno,12,4) + '地號。' ");
				
			}else if("405".equals(type)){
				stb.append("'重劃自' + (select top 1 z.signdesc from SYSCA_SIGN z where z.signno=substring(signno,1,7)) + substring(signno,8,4) + '-' + substring(signno,12,4) + '地號。' ");
				
			}
			
			this.execSQL_ForNoResult_NoChange(stb.toString());
		}
		
		protected void beforeWork_Add_insertTo_Untla_Land_Temp_from_Untla_Land_Temp2(){
			String sql="insert into UNTLA_LAND_"+ Common.get(this.getEditID()).replace(".", "")+" select * from UNTLA_LAND_"+ Common.get(this.getEditID()).replace(".", "")+"2";
			
			this.execSQL_ForNoResult_NoChange(sql);
		}
		
		protected void beforeWork_Add_createTable_Untla_Land_Temp2_for_UNTLA060F(){
			String sql=" select b.* into UNTLA_LAND_"+ Common.get(this.getEditID()).replace(".", "")+"2 from UNTLA_LAND b where 1=1"+
						" and a.enterorg = " + Common.sqlChar(this.getEnterOrg()) +
						" and a.ownership = " + Common.sqlChar(this.getOwnership()) +
						" and a.differencekind = " + Common.sqlChar(this.getDifferenceKind()) +
						" and a.propertyno = " + Common.sqlChar(this.getPropertyNo_Reduce()) +
						" and a.serialno = (select * from SERIALNOTABLE)";
			
			this.execSQL_ForNoResult_NoChange(sql);
		}
		
				
		protected void beforeWork_Add_dropTable_Untla_Value_Temp(){										get_dropTable_Table_Temp("UNTLA_VALUE");}	
		protected void beforeWork_Add_createTable_Untla_Value_Temp(){									get_createTable_Table_Temp("UNTLA_VALUE");}
		protected void beforeWork_Add_deleteFrom_Untla_Value_Temp(){									get_deleteFrom_Table_Temp("UNTLA_VALUE");}	
		protected void beforeWork_Add_dropTable_Untla_Value_Temp2(){									get_dropTable_Table_Temp2("UNTLA_VALUE");}
		protected void beforeWork_Add_createTable_Untla_Value_Temp2_from_Untla_Value(){					get_createTable_Table_Temp2_from_Table("UNTLA_VALUE");}
		protected void beforeWork_Add_update_Untla_Value_Temp2_forSerialNo_from_Untla_Land_Temp2(){		get_update_Table_Temp2_forSerialNo_from_Untla_Land_Temp2("UNTLA_VALUE");}
		protected void beforeWork_Add_insertTo_Untla_Value_Temp_from_Untla_Value_Temp2(){				get_insertTo_Table_Temp_from_Table_Temp2("UNTLA_VALUE");}
		
		protected void beforeWork_Add_dropTable_Untla_Manage_Temp(){									get_dropTable_Table_Temp("UNTLA_MANAGE");}	
		protected void beforeWork_Add_createTable_Untla_Manage_Temp(){									get_createTable_Table_Temp("UNTLA_MANAGE");}
		protected void beforeWork_Add_deleteFrom_Untla_Manage_Temp(){									get_deleteFrom_Table_Temp("UNTLA_MANAGE");}	
		protected void beforeWork_Add_dropTable_Untla_Manage_Temp2(){									get_dropTable_Table_Temp2("UNTLA_MANAGE");}
		protected void beforeWork_Add_createTable_Untla_Manage_Temp2_from_Untla_Manage(){				get_createTable_Table_Temp2_from_Table("UNTLA_MANAGE");}
		protected void beforeWork_Add_update_Untla_Manage_Temp2_forSerialNo_from_Untla_Land_Temp2(){	get_update_Table_Temp2_forSerialNo_from_Untla_Land_Temp2("UNTLA_MANAGE");}
		protected void beforeWork_Add_insertTo_Untla_Manage_Temp_from_Untla_Manage_Temp2(){				get_insertTo_Table_Temp_from_Table_Temp2("UNTLA_MANAGE");}		
		protected void beforeWork_Add_updateArea_from_Untla_Manage_Temp2(){								get_updateArea_from_table_Temp2("UNTLA_MANAGE");}
		
		protected void beforeWork_Add_dropTable_UNTLA_Attachment_Temp(){	 								get_dropTable_Table_Temp("UNTLA_ATTACHMENT");}	
		protected void beforeWork_Add_createTable_UNTLA_Attachment_Temp(){									get_createTable_Table_Temp("UNTLA_ATTACHMENT");}
		protected void beforeWork_Add_deleteFrom_UNTLA_Attachment_Temp(){									get_deleteFrom_Table_Temp("UNTLA_ATTACHMENT");}	
		protected void beforeWork_Add_dropTable_UNTLA_Attachment_Temp2(){									get_dropTable_Table_Temp2("UNTLA_ATTACHMENT");}
		protected void beforeWork_Add_createTable_UNTLA_Attachment_Temp2_from_UNTLA_Attachment(){			get_createTable_Table_Temp2_from_Table("UNTLA_ATTACHMENT");}
		protected void beforeWork_Add_update_UNTLA_Attachment_Temp2_forSerialNo_from_Untla_Land_Temp2(){	get_update_Table_Temp2_forSerialNo_from_Untla_Land_Temp2("UNTLA_ATTACHMENT");}
		protected void beforeWork_Add_insertTo_UNTLA_Attachment_Temp_from_UNTLA_Attachment_Temp2(){			get_insertTo_Table_Temp_from_Table_Temp2("UNTLA_ATTACHMENT");}	
		protected void beforeWork_Add_updateArea_from_Untla_Attachment_Temp2(){								get_updateArea_from_table_Temp2("UNTLA_ATTACHMENT");}
			
			
		protected void beforeWork_getCause_FromUNTLA_Land(){
			String sql="SELECT A.sourcekind FROM UNTLA_LAND A"+
						" WHERE 1=1"+
							" AND A.enterorg = " + Common.sqlChar(this.getEnterOrg()) +
							" AND A.ownership = " + Common.sqlChar(this.getOwnership()) +
							" AND A.differencekind = " + Common.sqlChar(this.getDifferenceKind()) +
							" AND A.propertyno = " + Common.sqlChar(this.getPropertyNo_Reduce()) +
							" AND A.serialno = " + Common.sqlChar(this.getSerialNo_Reduce()) ;
			this.setSourceKind_Add(this.getNameData_NoChange("sourcekind", sql));
		}

		protected void beforeWork_getCause_From_UNTLA_Mergedivision(){
			String sql="SELECT A.cause FROM UNTLA_MERGEDIVISION A"+
						" WHERE 1=1"+
							" AND A.enterorg = " + Common.sqlChar(this.getEnterOrg()) +
							" AND A.ownership = " + Common.sqlChar(this.getOwnership()) +
							" AND A.caseno = " + Common.sqlChar(this.getCaseNo_Merge()) +
							" AND A.differencekind = " + Common.sqlChar(this.getDifferenceKind());

			this.setSourceKind_before(this.getNameData_NoChange("cause", sql));
		}
		
		

		private void get_dropTable_Table_Temp(String table){
			String sql="DROP TABLE " + table + "_"+ Common.get(this.getEditID()).replace(".", "");
			
			this.execSQL_ForNoResult_NoChange(sql, true);
		}	
		private void get_createTable_Table_Temp(String table){
			String sql = "select top 1 * into " + table + "_" +  Common.get(this.getEditID()).replace(".", "") + " from " + table;
			this.execSQL_ForNoResult(sql);	
		}
		private void get_deleteFrom_Table_Temp(String table){
			String sql="delete from " + table + "_" +  Common.get(this.getEditID()).replace(".", "");
			
			this.execSQL_ForNoResult_NoChange(sql);		
		}	
		private void get_dropTable_Table_Temp2(String table){
			String sql="DROP TABLE " + table + "_"+ Common.get(this.getEditID()).replace(".", "")+"2";
			
			this.execSQL_ForNoResult_NoChange(sql, true);
		}
		private void get_createTable_Table_Temp2_from_Table(String table){
			String sql=" select a.* into " + table + "_"+ Common.get(this.getEditID()).replace(".", "")+"2 from " + table + " a, UNTLA_LAND_"+ Common.get(this.getEditID()).replace(".", "")+"2 b where 1=1 and a.enterorg=b.enterorg and a.ownership=b.ownership and a.propertyno=b.propertyno and a.serialno=b.serialno and a.differencekind=b.differencekind";
			
//			if("Untla_Manage".equals(table)){	
//				sql += " and (" +
//								" ( select x.propertykind from UNTLA_LAND_"+this.getEditID()+"2 x where x.enterorg=a.enterorg and x.ownership=a.ownership and x.propertyno=a.propertyno and x.serialno=a.serialno and x.differencekind=a.differencekind) = '04' " +
//								" and a.isdefault = '1' "+
//							") or (" +
//								" ( select x.propertykind from UNTLA_Land_"+this.getEditID()+"2 x where x.enterorg=a.enterorg and x.ownership=a.ownership and x.propertyno=a.propertyno and x.serialno=a.serialno and x.differencekind=a.differencekind) != '04' " +
//								" and (case when a.useeatew is null then '00000000' else a.useeatew end) <= " + Common.sqlChar(Datetime.getYYYYMMDD()) +
//								" and (case when a.useeatee is null then '99999999' else a.useeatee end) >= " + Common.sqlChar(Datetime.getYYYYMMDD()) +
//							")"; 
//			}
			
			this.execSQL_ForNoResult_NoChange(sql);
		}
		private void get_update_Table_Temp2_forSerialNo_from_Untla_Land_Temp2(String table){
			String sql="update " + table + "_"+ Common.get(this.getEditID()).replace(".", "")+"2 set serialno = (select b.serialno from UNTLA_LAND_"+ Common.get(this.getEditID()).replace(".", "")+"2 b where 1=1 and enterorg=b.enterorg and ownership=b.ownership and propertyno=b.propertyno and differencekind=b.differencekind)" ;
					
			this.execSQL_ForNoResult_NoChange(sql);
		}
		private void get_insertTo_Table_Temp_from_Table_Temp2(String table){
			String sql="insert into " + table + "_"+ Common.get(this.getEditID()).replace(".", "")+" select * from " + table + "_"+ Common.get(this.getEditID()).replace(".", "")+"2";
			
			this.execSQL_ForNoResult(sql);
		}
		
		private void get_updateArea_from_table_Temp2(String table){
			if(get_checkArea_from_Untla_Manage_Temp2(table) 
					&& get_checkAreaCount_from_Untla_Manage_Temp2(table)){
				
				String sql = "update " + table + "_" +  Common.get(this.getEditID()).replace(".", "") + "2 set " +
								" usearea = (select holdarea from UNTLA_LAND_" +  Common.get(this.getEditID()).replace(".", "") + "2 b where 1=1 " +
											" and enterorg=b.enterorg and ownership=b.ownership and differencekind=b.differencekind and propertyno=b.propertyno and serialno=b.serialno and differencekind = b.differencekind" +
											")";
				this.execSQL_ForNoResult_NoChange(sql);
			}			
		}
		
			private boolean get_checkAreaCount_from_Untla_Manage_Temp2(String table){
				String sql = "select count(*) as COUNT from " + table + "_" +  Common.get(this.getEditID()).replace(".", "") + "2";
				String returnStr = this.getNameData_NoChange("COUNT", sql);			
				return ("1".equals(returnStr))?true:false;
			}
			
			private boolean get_checkArea_from_Untla_Manage_Temp2(String table){
				String sql = "select case when a.holdarea=a.originalarea then 1 else 0 end as CHECKAREA from UNTLA_LAND_"+ Common.get(this.getEditID()).replace(".", "")+"2 a, "+table+"_"+ Common.get(this.getEditID()).replace(".", "")+"2 b"+
								" where a.enterorg=b.enterorg and a.ownership=b.ownership and a.propertyno=b.propertyno and a.serialno=b.serialno and a.differencekind=b.differencekind";
				String returnStr = this.getNameData_NoChange("CHECKAREA", sql);
				return ("1".equals(returnStr))?true:false;
			}
		
		protected String updateBulletinDate_queryCount(){
			UNTCH_Tools ut = new UNTCH_Tools();
			String sql="select count(*) as COUNT from UNTLA_VALUE_"+ Common.get(this.getEditID()).replace(".", "")+" a " + 
						" where 1=1" +
							" and a.enterorg = " + Common.sqlChar(this.getEnterOrg()) +
							" and a.ownership = " + Common.sqlChar(this.getOwnership()) +
							" and a.differencekind = " + Common.sqlChar(this.getDifferenceKind()) +
							" and a.propertyno = " + Common.sqlChar(this.getPropertyNo_Add()) +
							" and a.serialno = " + Common.sqlChar(this.getSerialNo_Add()) +
							" and a.bulletindate = " + Common.sqlChar(ut._transToCE_Year(this.getAfter_bulletinDate()));
			String returnStr = this.getNameData_NoChange("COUNT", sql);
			return returnStr;
		}
		protected void updateBulletinDate_createTable_Untla_Value_Temp2(){
			String sql="select * into UNTLA_VALUE_"+ Common.get(this.getEditID()).replace(".", "")+"2 from UNTLA_VALUE_"+ Common.get(this.getEditID()).replace(".", "")+" where bulletindate = (select max(bulletindate) from UNTLA_VALUE_"+ Common.get(this.getEditID()).replace(".", "")+")";
			this.execSQL_ForNoResult_NoChange(sql);
		}
		protected void updateBulletinDate_updateTo_Untla_Value_Temp2(){
			UNTCH_Tools ut = new UNTCH_Tools();
			String sql="update UNTLA_VALUE_"+ Common.get(this.getEditID()).replace(".", "")+"2 set " +
							" bulletindate = " + Common.sqlChar(ut._transToCE_Year(this.getAfter_bulletinDate())) + "," +
							" valueunit = 0," +
							" suitdates = (select z.suitdates from UNTLA_BULLETINDATE z where 1=1 and z.bulletinkind ='1' and z.bulletindate = " + Common.sqlChar(ut._transToCE_Year(this.getAfter_bulletinDate())) + ")," +
							" suitdatee = (select z.suitdatee from UNTLA_BULLETINDATE z where 1=1 and z.bulletinkind ='1' and z.bulletindate = " + Common.sqlChar(ut._transToCE_Year(this.getAfter_bulletinDate())) + ")" + 
						" where 1=1" +
							" and enterorg = " + Common.sqlChar(this.getEnterOrg()) +
							" and ownership = " + Common.sqlChar(this.getOwnership()) +
							" and differencekind = " + Common.sqlChar(this.getDifferenceKind());
			this.execSQL_ForNoResult_NoChange(sql);
		}
		protected void updateBulletinDate_insertTo_Untla_Value_Temp_from_Untla_Value_Temp2(){
			String sql="insert into UNTLA_VALUE_"+ Common.get(this.getEditID()).replace(".", "")+" select * from UNTLA_VALUE_"+ Common.get(this.getEditID()).replace(".", "")+"2";
			this.execSQL_ForNoResult_NoChange(sql);
		}
		protected void updateBulletinDate_droptable_Untla_Value_Temp2(){
			String sql="drop table UNTLA_VALUE_"+ Common.get(this.getEditID()).replace(".", "")+"2";
			this.execSQL_ForNoResult_NoChange(sql, true);
		}
		protected void updateBulletinDate_updateTo_Untla_Value_Temp_for_valueUnit(){
			UNTCH_Tools ut = new UNTCH_Tools();
			String sql="update UNTLA_VALUE_"+ Common.get(this.getEditID()).replace(".", "")+" set " +
							" valueunit = " + ("".equals(Common.get(getAfter_valueUnit()))?"0":Common.sqlChar(getAfter_valueUnit())) + "" +
						" where 1=1" +
							" and enterorg = " + Common.sqlChar(this.getEnterOrg()) +
							" and ownership = " + Common.sqlChar(this.getOwnership()) +
							" and differencekind = " + Common.sqlChar(this.getDifferenceKind()) +
							" and propertyno = " + Common.sqlChar(this.getPropertyNo_Add()) +
							" and serialno = " + Common.sqlChar(this.getSerialNo_Add()) +
							" and bulletindate = " + Common.sqlChar(ut._transToCE_Year(this.getAfter_bulletinDate()));
			this.execSQL_ForNoResult_NoChange(sql);
		}
		
		
		protected void execValueCalculate_update_Untla_Land_Temp_for_bookValue_from_Untla_Land(){
			String sql="update UNTLA_LAND_"+ Common.get(this.getEditID()).replace(".", "")+" set" +
						" bookvalue = round( (" +
									" select z.bookvalue from UNTLA_LAND z where 1=1" +
									" and z.enterorg = " + Common.sqlChar(this.getEnterOrg()) +
									" and z.ownership = " + Common.sqlChar(this.getOwnership()) +
									" and z.differencekind = " + Common.sqlChar(this.getDifferenceKind()) +
									" and z.propertyno = " + Common.sqlChar(this.getPropertyNo_Reduce()) +
									" and z.serialno = " + Common.sqlChar(this.getSerialNo_Reduce()) +
									") * holdarea ,0),  " +
						" netvalue = round( (" +
									" select z.bookvalue from UNTLA_LAND z where 1=1" +
									" and z.enterorg = " + Common.sqlChar(this.getEnterOrg()) +
									" and z.ownership = " + Common.sqlChar(this.getOwnership()) +
									" and z.differencekind = " + Common.sqlChar(this.getDifferenceKind()) +
									" and z.propertyno = " + Common.sqlChar(this.getPropertyNo_Reduce()) +
									" and z.serialno = " + Common.sqlChar(this.getSerialNo_Reduce()) +
									")  * holdarea ,0)";
			this.execSQL_ForNoResult_NoChange(sql);
		}
		protected void execValueCalculate_update_Untla_Land_Temp_for_bookValue_by_NotMaxSerialNo(){
			String sql="update UNTLA_LAND_"+ Common.get(this.getEditID()).replace(".", "")+" set" +
						" bookvalue = round( bookvalue * holdarea/(select sum(holdarea) from UNTLA_LAND_"+ Common.get(this.getEditID()).replace(".", "")+"),0 )," +
						" netvalue = round( bookvalue * holdarea/(select sum(holdarea) from UNTLA_LAND_"+ Common.get(this.getEditID()).replace(".", "")+"),0 )" +
						" where 1=1" +
						" and serialno != (select max(serialno) from UNTLA_LAND_"+ Common.get(this.getEditID()).replace(".", "")+")";
			this.execSQL_ForNoResult_NoChange(sql);
		}
		protected void execValueCalculate_update_Untla_Land_Temp_for_bookValue_by_MaxSerialNo(){
			String sql="update UNTLA_LAND_"+ Common.get(this.getEditID()).replace(".", "")+" set" +
						" bookvalue = round( bookvalue - (select sum(bookvalue) from UNTLA_LAND_"+ Common.get(this.getEditID()).replace(".", "")+" where 1=1 and serialno != (select max(serialno) from UNTLA_LAND_"+ Common.get(this.getEditID()).replace(".", "")+")),0 )," +
						" netvalue = round( bookvalue - (select sum(bookvalue) from UNTLA_LAND_"+ Common.get(this.getEditID()).replace(".", "")+" where 1=1 and serialno != (select max(serialno) from UNTLA_LAND_"+ Common.get(this.getEditID()).replace(".", "")+")),0 )" +
						" where 1=1" +
						" and serialno = (select max(serialno) from UNTLA_LAND_"+ Common.get(this.getEditID()).replace(".", "")+")";
			this.execSQL_ForNoResult_NoChange(sql);				
		}
		protected void execValueCalculate_update_Untla_Land_Temp_for_bookUnit(){
			String sql="update UNTLA_LAND_"+ Common.get(this.getEditID()).replace(".", "")+" set" +
						" bookunit = round(bookvalue/holdarea,2)," +
						" netunit = round(bookvalue/holdarea,2)" +
						" where 1=1";
			this.execSQL_ForNoResult_NoChange(sql);
		}
		
		protected void execValueCalculate_update_Untla_Land_Temp_for_bookValue_and_bookUnit(){
			String sql = "select" +
							" (case when b.valueunit is null then 0 else b.valueunit end) as bookunit" +
							" from UNTLA_VALUE_"+ Common.get(this.getEditID()).replace(".", "")+" b where enterorg=b.enterorg and ownership=b.ownership and differencekind=b.differencekind and propertyno=b.propertyno and serialno=b.serialno and b.bulletindate='"+getAfter_bulletinDate()+"'";
			
			String bookunit = getNameData_NoChange("bookunit",sql);
			if("".equals(checkGet(bookunit))){
				bookunit = "0";
			}
			
			sql = "select" +
					" (case when b.valueunit is null then 0 else b.valueunit end) as bookunit" +
					" from UNTLA_VALUE_"+ Common.get(this.getEditID()).replace(".", "")+" b where enterorg=b.enterorg and ownership=b.ownership and differencekind=b.differencekind and propertyno=b.propertyno and serialno=b.serialno and b.bulletindate='"+getAfter_bulletinDate()+"'";
	
			String bookvalue = getNameData_NoChange("bookunit",sql);
			if("".equals(checkGet(bookvalue))){
				bookvalue = "0";
			}
			
			sql="update UNTLA_LAND_"+ Common.get(this.getEditID()).replace(".", "")+" set" +
					" bookunit = " + Common.sqlChar(bookunit) + "," +
					" bookvalue = round( " + Common.sqlChar(bookvalue) + " * holdarea ,0) ," +
					" netunit = " + Common.sqlChar(bookunit) + "," +
					" netvalue = round( " + Common.sqlChar(bookvalue) + " * holdarea ,0) ";
			
			this.execSQL_ForNoResult_NoChange(sql);
		}

		
		

		protected void afterWork_update_Untla_Land_Temp_for_otherData(){
			UNTCH_Tools ut = new UNTCH_Tools();
			String sql="update UNTLA_LAND_"+ Common.get(this.getEditID()).replace(".", "")+" set" +
						" cause = (select z.cause from UNTLA_MERGEDIVISION z where z.enterorg="+Common.sqlChar(this.getEnterOrg())+" and z.ownership="+Common.sqlChar(this.getOwnership())+" and z.caseno="+Common.sqlChar(this.getCaseNo_Merge())+" and z.differencekind="+Common.sqlChar(this.getDifferenceKind()) + ")," +
						" cause1 = (select z.cause1 from UNTLA_MERGEDIVISION z where z.enterorg="+Common.sqlChar(this.getEnterOrg())+" and z.ownership="+Common.sqlChar(this.getOwnership())+" and z.caseno="+Common.sqlChar(this.getCaseNo_Merge())+" and z.differencekind="+Common.sqlChar(this.getDifferenceKind()) + ")," +
						" datastate = " + Common.sqlChar("1") + "," +
						" verify = " + Common.sqlChar("N") + "," +
						" enterdate = null," + 
						" originalunit = bookunit" + "," +
						" originalbv = bookvalue" + "," +
						" originaldate = " + (("".equals(this.getAfter_bulletinDate()))?"originaldate":Common.sqlChar(ut._transToCE_Year(this.getAfter_bulletinDate()))) + "," +
						" oldpropertyno = " + Common.sqlChar(this.getPropertyNo_Reduce()) + "," +
						" oldserialno = " + Common.sqlChar(this.getSerialNo_Reduce()) + "," +
						" editid = " + Common.sqlChar(this.getEditID()) + "," +
						" editdate = " + Common.sqlChar(ut._transToCE_Year(this.getEditDate())) + "," +
						" edittime = " + Common.sqlChar(this.getEditTime()) + "," +
						" mergedivision = " + Common.sqlChar(this.getCaseNo_Merge()) + "," +
						" sourcekind = "
						+ "( case (select top 1 cause from UNTLA_MERGEDIVISION where enterorg=" + Common.sqlChar(this.getEnterOrg()) + " and caseno=" + Common.sqlChar(this.getCaseNo_Merge()) + ")"
						+ " when '401' then '13' "
						+ " when '402' then '56' "
						+ " when '403' then '12' "
						+ " when '407' then '13' "
						+ " when '413' then '103' "
						+ " else sourcekind "
						+ " end "
						+ ")";
			
			this.execSQL_ForNoResult_NoChange(sql);
		}
		
		protected void afterWork_update_Untla_Value_Temp_for_otherData(){		afterWork_update_Table_for_otherData("UNTLA_VALUE");}
		protected void afterWork_update_Untla_Manage_Temp_for_otherData(){		afterWork_update_Table_for_otherData("UNTLA_MANAGE");}
		protected void afterWork_update_Untla_Person_Temp_for_otherData(){		afterWork_update_Table_for_otherData("UNTLA_PERSON");}
		protected void afterWork_update_Untla_Attachment_Temp_for_otherData(){	afterWork_update_Table_for_otherData("UNTLA_ATTACHMENT");}
		
		private void afterWork_update_Table_for_otherData(String table){
			UNTCH_Tools ut = new UNTCH_Tools();
			String sql="update "+table+"_"+ Common.get(this.getEditID()).replace(".", "")+" set" +
						" editid = " + Common.sqlChar(this.getEditID()) + "," +
						" editdate = " + Common.sqlChar(ut._transToCE_Year(this.getEditDate())) + "," +
						" edittime = " + Common.sqlChar(this.getEditTime()) ;
			
			this.execSQL_ForNoResult_NoChange(sql);
		}
		
		protected void afterWork_insertTo_Untla_Land(){				get_InsertTo_Untla_Land("UNTLA_LAND");}		
		protected void afterWork_dropTable_Untla_Land_Temp(){		get_DropTable_Untla_Land_Temp("UNTLA_LAND");}
		protected void afterWork_insertTo_Untla_Value(){			get_InsertTo_Untla_Land("UNTLA_VALUE");}		
		protected void afterWork_dropTable_Untla_Value_Temp(){		get_DropTable_Untla_Land_Temp("UNTLA_VALUE");}
		protected void afterWork_insertTo_Untla_Manage(){			get_InsertTo_Untla_Land("UNTLA_MANAGE");}	
		protected void afterWork_dropTable_Untla_Manage_Temp(){		get_DropTable_Untla_Land_Temp("UNTLA_MANAGE");}
		protected void afterWork_insertTo_UNTLA_Attachment(){		get_InsertTo_Untla_Land("UNTLA_ATTACHMENT");}		
		protected void afterWork_dropTable_UNTLA_Attachment_Temp(){	get_DropTable_Untla_Land_Temp("UNTLA_ATTACHMENT");}

		protected void afterWork_updateTo_Untla_ReduceDetail(){
			String sql="update UNTLA_REDUCEDETAIL set" +
						" mergedivision = " + Common.sqlChar(this.getCaseNo_Merge()) + "," +
						" mergedivisionbatch = " + Common.sqlChar(this.getMergeDivisionBatch()) +
						" where 1=1 " +
							" and enterorg = " + Common.sqlChar(this.getEnterOrg()) +
							" and ownership = " + Common.sqlChar(this.getOwnership()) +
							" and caseno = " + Common.sqlChar(this.getCaseNo_Reduce()) +
							" and differencekind = " + Common.sqlChar(this.getDifferenceKind()) +
							" and propertyno = " + Common.sqlChar(this.getPropertyNo_Reduce()) +
							" and serialno = " + Common.sqlChar(this.getSerialNo_Reduce());
			this.execSQL_ForNoResult_NoChange(sql);
		}

		private void get_InsertTo_Untla_Land(String table){
			String sql="insert into " + table + " select * from " + table + "_"+ Common.get(this.getEditID()).replace(".", "");
			this.execSQL_ForNoResult_NoChange(sql);
		}		
		private void get_DropTable_Untla_Land_Temp(String table){					
			String sql="DROP TABLE " + table + "_"+ Common.get(this.getEditID()).replace(".", "");
			this.execSQL_ForNoResult_NoChange(sql, true);
		}
		

		
		private String afterWork_Add_getMaxMergeDivisionBatchPlusOne(String CaseNo){
			String sql="select (case when max(a.mergedivisionbatch) is null then '1' else (max(a.mergedivisionbatch) +1) end) as mergedivisionbatch from UNTLA_REDUCEDETAIL a" + 
						" where 1=1 and a.caseno = " + Common.sqlChar(CaseNo) ;
			String returnStr = this.getNameData_NoChange("mergedivisionbatch", sql);
			return returnStr;
		}
			
		
		//<<<<<<<<<<<<<<<<<<<<<<<< UNTLA060F >>>>>>>>>>>>>>>>>>>>>>>>>>>
		
		protected void beforeWork_060F_getSourceKind_FromUNTLA_Land(){
			String sql="select sum(sourcekind) as sourcekind from (" +
							"SELECT " +
							" case a.sourcekind"+
								" when '02' then 1"+
								" when '03' then 1"+
								" when '11' then 1"+
								" else 0"+
							" end as sourcekind"+
						" FROM UNTLA_LAND a"+
						" WHERE 1=1"+
							" AND a.enterorg = " + Common.sqlChar(this.getEnterOrg()) +
							" AND a.ownership = " + Common.sqlChar(this.getOwnership()) +
							" and a.differencekind = " + Common.sqlChar(this.getDifferenceKind()) +
							" AND (a.propertyno) in (select z.propertyno from PROPERTYTABLE_"+ Common.get(this.getEditID()).replace(".", "")+" z)"+
							" AND (a.serialno) in (select z.serialno from PROPERTYTABLE_"+ Common.get(this.getEditID()).replace(".", "")+" z)"+
						") a";
			
			this.setSourceKind_before("0".equals(this.getNameData_NoChange("sourcekind", sql))?"NotConform":"Conform");
		}
		protected void beforeWork_060F_CreateTable_Untla_Land_Temp(){
			String sql = "select * into UNTLA_LAND_" +  Common.get(this.getEditID()).replace(".", "") + " from UNTLA_LAND a" +
						" where 1=1" +
						" and a.enterorg = " + Common.sqlChar(this.getEnterOrg()) +
						" and a.ownership = " + Common.sqlChar(this.getOwnership()) +
						" and a.differencekind = " + Common.sqlChar(this.getDifferenceKind()) +
						" and (a.propertyno) in (select z.propertyno from PROPERTYTABLE_"+ Common.get(this.getEditID()).replace(".", "")+" z)"+
						" and (a.serialno) in (select z.serialno from PROPERTYTABLE_"+ Common.get(this.getEditID()).replace(".", "")+" z)";
			
			this.execSQL_ForNoResult_NoChange(sql);
		}
		
		protected void beforeWork_060F_CreateTable_Untla_Value_Temp(){
			String sql = "select * into UNTLA_VALUE_" +  Common.get(this.getEditID()).replace(".", "") + " from UNTLA_VALUE a" +
						" where 1=1" +
						" and a.enterorg = " + Common.sqlChar(this.getEnterOrg()) +
						" and a.ownership = " + Common.sqlChar(this.getOwnership()) +
						" and a.differencekind = " + Common.sqlChar(this.getDifferenceKind()) +
						" and (a.propertyno) in (select z.propertyno from PROPERTYTABLE_"+ Common.get(this.getEditID()).replace(".", "")+" z)" +
						" and (a.serialno) in (select z.serialno from PROPERTYTABLE_"+ Common.get(this.getEditID()).replace(".", "")+" z)";
			
			this.execSQL_ForNoResult_NoChange(sql);
		}
		
		protected void beforeWork_060F_CreateTable_Untla_Land_Temp2(){
			String sql = "select * into UNTLA_LAND_" +  Common.get(this.getEditID()).replace(".", "") + "2 from UNTLA_LAND_"+ Common.get(this.getEditID()).replace(".", "")+" a" +
						" where 1=1" +
						" and a.enterorg = " + Common.sqlChar(this.getEnterOrg()) +
						" and a.ownership = " + Common.sqlChar(this.getOwnership()) +
						" and a.differencekind = " + Common.sqlChar(this.getDifferenceKind()) +
						" and (a.propertyno) in (select z.propertyno from PROPERTYTABLE_"+ Common.get(this.getEditID()).replace(".", "")+" z where checkflag = 'Y')" +
						" and (a.serialno) in (select z.serialno from PROPERTYTABLE_"+ Common.get(this.getEditID()).replace(".", "")+" z where checkflag = 'Y')";
			
			this.execSQL_ForNoResult_NoChange(sql);
		}
		
		protected void beforeWork_060F_CreateTable_Untla_Value_Temp2(){
			String sql = "select * into UNTLA_VALUE_" +  Common.get(this.getEditID()).replace(".", "") + "2 from UNTLA_VALUE_"+ Common.get(this.getEditID()).replace(".", "")+" a" +
						" where 1=1" +
						" and a.enterorg = " + Common.sqlChar(this.getEnterOrg()) +
						" and a.ownership = " + Common.sqlChar(this.getOwnership()) +
						" and a.differencekind = " + Common.sqlChar(this.getDifferenceKind()) +
						" and (a.propertyno) in (select z.propertyno from PROPERTYTABLE_"+ Common.get(this.getEditID()).replace(".", "")+" z where checkflag = 'Y')" +
						" and (a.serialno) in (select z.serialno from PROPERTYTABLE_"+ Common.get(this.getEditID()).replace(".", "")+" z where checkflag = 'Y')";
			
			this.execSQL_ForNoResult_NoChange(sql);
		}
		
		protected void beforeWork_060F_Update_Area_Untla_Land_Temp(){
			String sql="update UNTLA_LAND_"+ Common.get(this.getEditID()).replace(".", "")+" set " +
						" area = (select sum(area) from UNTLA_LAND a" +
								" where 1=1" +
								" and a.enterorg = " + Common.sqlChar(this.getEnterOrg()) +
								" and a.ownership = " + Common.sqlChar(this.getOwnership()) +
								" and a.differencekind = " + Common.sqlChar(this.getDifferenceKind()) +
								" and (a.propertyno) in (select z.propertyno from PROPERTYTABLE_"+ Common.get(this.getEditID()).replace(".", "")+" z)" +
								" and (a.serialno) in (select z.serialno from PROPERTYTABLE_"+ Common.get(this.getEditID()).replace(".", "")+" z)" +
						" ), " +
						" holdarea = (select sum(holdarea) from UNTLA_LAND a" +
								" where 1=1" +
								" and a.enterorg = " + Common.sqlChar(this.getEnterOrg()) +
								" and a.ownership = " + Common.sqlChar(this.getOwnership()) +
								" and a.differencekind = " + Common.sqlChar(this.getDifferenceKind()) +
								" and (a.propertyno) in (select z.propertyno from PROPERTYTABLE_"+ Common.get(this.getEditID()).replace(".", "")+" z)" +
								" and (a.serialno) in (select z.serialno from PROPERTYTABLE_"+ Common.get(this.getEditID()).replace(".", "")+" z)" +
						" )";
			
			this.execSQL_ForNoResult_NoChange(sql);
		}
		
		protected void execValueCalculate_060F_update_Untla_Land_Temp2_for_BookvalueAndBookunit(){
			String sql="update UNTLA_LAND_"+ Common.get(this.getEditID()).replace(".", "")+"2 set" +
						" bookunit = '"+this.getAfter_valueUnit()+"'," +
						" bookvalue = (holdarea*"+this.getAfter_valueUnit()+")" +						
						" where 1=1";
			this.execSQL_ForNoResult_NoChange(sql);			
		}		
		protected void execValueCalculate_060F_update_Untla_Land_Temp2_for_bookValue_and_bookUnit(){
			String sql="update UNTLA_LAND_"+ Common.get(this.getEditID()).replace(".", "")+"2 set" +
						" bookunit = (select round(sum(z.bookvalue)/(select sum(holdarea) from UNTLA_LAND_"+ Common.get(this.getEditID()).replace(".", "")+"2),2) from UNTLA_LAND_"+ Common.get(this.getEditID()).replace(".", "")+" z),"+
						" bookvalue = (select (case when sum(z.bookvalue) is null then 0 else sum(z.bookvalue) end) from UNTLA_LAND_"+ Common.get(this.getEditID()).replace(".", "")+" z)";
			
			this.execSQL_ForNoResult_NoChange(sql);
		}
		
		protected void updateNetValue_forUNTLA_LAND(){
			String sql="update UNTLA_LAND_"+ Common.get(this.getEditID()).replace(".", "")+"2 set" +
						" netunit = bookunit,"+
						" netvalue = bookvalue";
		
			this.execSQL_ForNoResult_NoChange(sql);			
		}
		
		/**
		 * 401 合併        13        自某項整理變更
		 * 402 分割        56        分割
		 * 403 逕為分割  12        逕行分割
		 * 407 逕為合併  13        自某項整理變更
		 * 413 地籍整理  103      地籍整理
		 */
		protected void updateSourceKind_forUNTLA_LAND() {
			
			String sql = "update UNTLA_LAND_" +  Common.get(this.getEditID()).replace(".", "") + "2 set "
					   + " sourcekind = " 
					   + " ( case (select top 1 cause from UNTLA_MERGEDIVISION where enterorg=" + Common.sqlChar(this.getEnterOrg()) + " and caseno=" + Common.sqlChar(this.getCaseNo_Merge()) + ")"
					   + " 	 when '401' then '13' "
					   + "   when '402' then '56' "
					   + "   when '403' then '12' "
					   + "   when '407' then '13' "
					   + "   when '413' then '103' "
					   + " else sourcekind "
					   + " end "
					   + ") ";
					   
			this.execSQL_ForNoResult_NoChange(sql);				   
		}
		
		
		protected void afterWork_060F_update_Untla_Land_Temp2_forNotes(){
			StringBuilder stb = new StringBuilder();
			
			stb.append("update UNTLA_LAND_"+ Common.get(this.getEditID()).replace(".", "")+"2 set notes = ")			
				.append("'合併自").append(getNotesContent()).append("。'");
			
			this.execSQL_ForNoResult_NoChange(stb.toString());
		
		}
			private String getNotesContent(){				
				java.util.List lt; 
				Database db = new Database();
				ResultSet rs;
				StringBuilder stb = new StringBuilder();
				String returnStr="";
				
				try{
					lt = getNotesContent_getCount();
										
					int count=0;
					String str, strTemp=null;
					boolean checkFlag=false;
					
					for(java.util.Iterator iter = lt.iterator();iter.hasNext();){
						str=iter.next().toString();
						
						//「縣市、鄉鎮市區、段小段」相同者，其「縣市、鄉鎮市區、段小段」不要重覆顯示
						if(strTemp==null){												checkFlag = false;
						}else{
							if( (str.substring(0,7)).equals(strTemp.substring(0,7)) ){	checkFlag = true;
							}else{														checkFlag = false;
							}
						}
						
						
						if("".equals(Common.get(str)) || str.length()<15){
							
						}else{
							if(checkFlag){
								stb.append(str.substring(7,11)).append("-").append(str.substring(11)).append("地號");
							}else{
								stb.append(getNotesContent_getSignName(str.substring(0,7)));
								stb.append(str.substring(7,11)).append("-").append(str.substring(11)).append("地號");
							}
							if(iter.hasNext()){stb.append("、");}
							
						}
						
						count++;
						strTemp=str;			
					}
					
					stb.append("等" + count + "筆");
					
					
					//若超過備註最大字數(250字)
					if(stb.length() >= 250){
						str = lt.get(0).toString();
						returnStr = getNotesContent_getSignName(str.substring(0,7)) +
									str.substring(7,11) + "-" + str.substring(11) +
									"地號等" + count +"筆";
					}else{
						returnStr = stb.toString();
					}

				}catch(Exception e){
					LogBean.outputLogError();
				}finally{
					db.closeAll();
				}
				return returnStr;
			}
			
			private java.util.List getNotesContent_getCount(){				
				java.util.List lt = new java.util.LinkedList (); 
				Database db = new Database();
				ResultSet rs;
				String sql=null;
				
				try{
					sql="select signno from UNTLA_REDUCEDETAIL a" +
								" where 1=1" +
									" and a.enterorg = " + Common.sqlChar(this.getEnterOrg()) +
									" and a.ownership = " + Common.sqlChar(this.getOwnership()) +
									" and a.differencekind = " + Common.sqlChar(this.getDifferenceKind()) +
									" and (a.propertyno) in (select z.propertyno from PROPERTYTABLE_"+ Common.get(this.getEditID()).replace(".", "")+" z)" +
									" and (a.serialno) in (select z.serialno from PROPERTYTABLE_"+ Common.get(this.getEditID()).replace(".", "")+" z)" +
									" order by a.signno";
					
					rs=db.querySQL_NoChange(sql);
					while(rs.next()){
						lt.add(rs.getString("signNo"));
					}
					
				}catch(Exception e){
					LogBean.outputLogError(sql);
				}finally{
					db.closeAll();
				}
				return lt;
			}
		
			private String getNotesContent_getSignName(String signNo){
				String sql="select signdesc from SYSCA_SIGN where signno = '" + signNo + "'";
				String returnStr = this.getNameData_NoChange("signdesc", sql);
				return returnStr;
				
			}
		
		protected void afterWork_060F_UpdateTo_Untla_Land_Temp2_forOtherData(){
			UNTCH_Tools ut = new UNTCH_Tools();
			String sql="update UNTLA_LAND_"+ Common.get(this.getEditID()).replace(".", "")+"2 set" +
						" originalarea = area," +
						" originalholdarea = holdarea," +
						" cause = (select z.cause from UNTLA_MERGEDIVISION z where z.enterorg="+Common.sqlChar(this.getEnterOrg())+" and z.ownership="+Common.sqlChar(this.getOwnership())+" and z.caseno="+Common.sqlChar(this.getCaseNo_Merge()) + ")," +
						" cause1 = (select z.cause1 from UNTLA_MERGEDIVISION z where z.enterorg="+Common.sqlChar(this.getEnterOrg())+" and z.ownership="+Common.sqlChar(this.getOwnership())+" and z.caseno="+Common.sqlChar(this.getCaseNo_Merge()) + ")," +
						" caseno = " + Common.sqlChar(this.getCaseNo_Add()) + "," +
						" serialno = RIGHT(REPLICATE('0', 7) + CAST('" + getNewSerialNoFromDB() + "' as NVARCHAR), 7)," +
						" datastate = " + Common.sqlChar("1") + "," +
						" verify = " + Common.sqlChar("N") + "," +
						" enterdate = null," + 
						" originalunit = bookunit" + "," +
						" originalbv = bookvalue" + "," +
						" originaldate = " + (("".equals(this.getAfter_bulletinDate()))?"originaldate":Common.sqlChar(ut._transToCE_Year(this.getAfter_bulletinDate()))) + "," +
						" oldpropertyno = (select propertyno from PROPERTYTABLE_"+ Common.get(this.getEditID()).replace(".", "")+" where checkflag = 'Y')," +
						" oldserialno = (select serialno from PROPERTYTABLE_"+ Common.get(this.getEditID()).replace(".", "")+" where checkflag = 'Y')," +
						" editid = " + Common.sqlChar(this.getEditID()) + "," +
						" editdate = " + Common.sqlChar(ut._transToCE_Year(this.getEditDate())) + "," +
						" edittime = " + Common.sqlChar(this.getEditTime()) + "," +
						" mergedivision = " + Common.sqlChar(this.getCaseNo_Merge());
			
			this.execSQL_ForNoResult_NoChange(sql);
		}
		
			private String getNewSerialNoFromDB(){
				String resultStr="";
				String sql=null;
				
				try{
					sql="select (case when max(serialno) is null then '0000001' else RIGHT(REPLICATE('0', 7) + CAST(case when MAX(serialno) is null then 1 else (MAX(serialno) + 1) end as NVARCHAR), 7) end) as serialno from UNTLA_LAND where 1=1"+
								" and enterorg = " + Common.sqlChar(this.getEnterOrg()) +
								" and ownership = " + Common.sqlChar(this.getOwnership()) +
								" and differencekind = " + Common.sqlChar(this.getDifferenceKind()) +
								" and propertyno = (select propertyno from UNTLA_LAND_"+ Common.get(this.getEditID()).replace(".", "")+"2)" ;
				
					resultStr=getNameData_NoChange("serialno", sql);
					
				}catch(Exception e){
					LogBean.outputLogError(sql);
				}
				
				return resultStr;
			}
			
		protected void afterWork_060F_UpdateTo_Untla_Value_Temp2_forOtherData(){
			UNTCH_Tools ut = new UNTCH_Tools();
			String sql="update UNTLA_VALUE_"+ Common.get(this.getEditID()).replace(".", "")+"2 set" +
						" editid = " + Common.sqlChar( this.getEditID()) + "," +
						" editdate = " + Common.sqlChar(ut._transToCE_Year(this.getEditDate())) + "," +
						" edittime = " + Common.sqlChar(this.getEditTime()) ;
			
			this.execSQL_ForNoResult_NoChange(sql);
		}
			
		protected void afterWork_060F_UpdateTo_for_mergeDivisionBatch(){
			String sql="update UNTLA_LAND_"+ Common.get(this.getEditID()).replace(".", "")+"2 set" +
						" mergedivisionbatch = " + ("".equals(checkGet(getMergeDivisionBatch()))?"null":Common.sqlChar(getMergeDivisionBatch()));
			
			this.execSQL_ForNoResult_NoChange(sql);
		}
				
		protected void afterWork_060F_updateTo_Untla_ReduceDetail(){
			String sql="update UNTLA_REDUCEDETAIL set" +
						" mergedivision = " + Common.sqlChar(this.getCaseNo_Merge()) + "," +
						" mergedivisionbatch = " + ("".equals(checkGet(getMergeDivisionBatch()))?"null":Common.sqlChar(getMergeDivisionBatch())) +
						" where 1=1 " +
							" and enterorg = " + Common.sqlChar(this.getEnterOrg()) +
							" and ownership = " + Common.sqlChar(this.getOwnership()) +
							" and differencekind = " + Common.sqlChar(this.getDifferenceKind()) +
							" and caseno = " + Common.sqlChar(this.getCaseNo_Reduce()) +
							" and (propertyno) in (select propertyno from PROPERTYTABLE_"+ Common.get(this.getEditID()).replace(".", "")+") " +
							" and (serialno) in (select serialno from PROPERTYTABLE_"+ Common.get(this.getEditID()).replace(".", "")+") ";
			this.execSQL_ForNoResult_NoChange(sql);
		}		
		
		protected void afterWork_060F_Update_Untla_Value_Temp2_from_Untla_Land_Temp(){
			String sql="Update UNTLA_VALUE_"+ Common.get(this.getEditID()).replace(".", "")+"2 set" +
						" serialno = (select serialno from UNTLA_LAND_"+ Common.get(this.getEditID()).replace(".", "")+"2)";
			
			this.execSQL_ForNoResult_NoChange(sql);
		}
		protected void afterWork_060F_InsertTo_Untla_Land(){
			String sql="insert into UNTLA_LAND select * from UNTLA_LAND_"+ Common.get(this.getEditID()).replace(".", "")+"2";			
			this.execSQL_ForNoResult_NoChange(sql);
		}
		protected void afterWork_060F_InsertTo_Untla_Value(){
			String sql="insert into UNTLA_VALUE select * from UNTLA_VALUE_"+ Common.get(this.getEditID()).replace(".", "")+"2";			
			this.execSQL_ForNoResult_NoChange(sql);
		}
		protected void afterWork_060F_DropTable_Untla_Land_Temp2(){
			String sql="drop table UNTLA_LAND_"+ Common.get(this.getEditID()).replace(".", "")+"2";			
			this.execSQL_ForNoResult_NoChange(sql, true);
		}
		protected void afterWork_060F_DropTable_Untla_Value_Temp2(){
			String sql="drop table UNTLA_VALUE_"+ Common.get(this.getEditID()).replace(".", "")+"2";			
			this.execSQL_ForNoResult_NoChange(sql, true);
		}

}


