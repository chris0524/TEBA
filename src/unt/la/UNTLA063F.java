/*
*<br>程式目的：土地合併分割重測重劃作業－增加單管理資料挑選
*<br>程式代號：untla063f
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
import util.*;

public class UNTLA063F extends UNTLA054Q{

		String enterOrg;
		String ownership;
		String differenceKind;
		String propertyNo;
		String serialNo;
//		String signNo;
		String serialNo1;
		String useUnit;
		String useUnit1;
		String useRelation;
		String useDateS;
		String useDateE;
		String useArea;
		
		String editID;
		String editDate;
		String editTime;
		String useUnitName;
		String useRelationName;
		
		String message;
		String[] strKeySet;
		
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
//		public String getSignNo() {return checkGet(signNo);}
//		public void setSignNo(String signNo) {this.signNo = checkSet(signNo);}
		public String getSerialNo1() {return checkGet(serialNo1);}
		public void setSerialNo1(String serialNo1) {this.serialNo1 = checkSet(serialNo1);}
		public String getUseUnit() {return checkGet(useUnit);}
		public void setUseUnit(String useUnit) {this.useUnit = checkSet(useUnit);}
		public String getUseUnit1() {return checkGet(useUnit1);}
		public void setUseUnit1(String useUnit1) {this.useUnit1 = checkSet(useUnit1);}
		public String getUseRelation() {return checkGet(useRelation);}
		public void setUseRelation(String useRelation) {this.useRelation = checkSet(useRelation);}
		public String getUseDateS() {return checkGet(useDateS);}
		public void setUseDateS(String useDateS) {this.useDateS = checkSet(useDateS);}
		public String getUseDateE() {return checkGet(useDateE);}
		public void setUseDateE(String useDateE) {this.useDateE = checkSet(useDateE);}
		public String getUseArea() {return checkGet(useArea);}
		public void setUseArea(String useArea) {this.useArea = checkSet(useArea);}

		public String getEditID() {return checkGet(editID);}
		public void setEditID(String editID) {this.editID = checkSet(editID);}
		public String getEditDate() {return checkGet(editDate);}
		public void setEditDate(String editDate) {this.editDate = checkSet(editDate);}
		public String getEditTime() {return checkGet(editTime);}
		public void setEditTime(String editTime) {this.editTime = checkSet(editTime);}
		public String getUseUnitName() {return checkGet(useUnitName);}
		public void setUseUnitName(String useUnitName) {this.useUnitName = checkSet(useUnitName);}
		public String getUseRelationName() {return checkGet(useRelationName);}
		public void setUseRelationName(String useRelationName) {this.useRelationName = checkSet(useRelationName);}
	

		public String getMessage() {return checkGet(message);}
		public void setMessage(String message) {this.message = checkSet(message);}
		public String[] getsKeySet(){ return strKeySet; }
		public void setsKeySet(String[] s){ strKeySet=s; }
		



public void afterWork(){
	String sql="";
	String resultStr="";
	int checkBoxLength = strKeySet.length;
	
	sql="drop table UNTLA_MANAGE_"+getEditID();
	this.execSQL_ForNoResult_NoChange(sql,true);
	
	sql = "select top 1 * into UNTLA_MANAGE_" + getEditID() + " from UNTLA_MANAGE";
	this.execSQL_ForNoResult_NoChange(sql);
	
	sql="delete from UNTLA_MANAGE_"+getEditID();
	this.execSQL_ForNoResult_NoChange(sql);
	
	//=========================================================================
	
	String NewSerialNo1str = null;
	
	for(int i=0;i<checkBoxLength;i++){

		sql="drop table UNTLA_MANAGE_"+getEditID()+"2";
		this.execSQL_ForNoResult_NoChange(sql,true);
		
		
		sql=" select distinct a.* into UNTLA_MANAGE_"+getEditID()+"2 from UNTLA_MANAGE a, UNTLA_LAND b, UNTLA_REDUCEDETAIL c " + 
					" where 1=1" +
					" and a.enterorg=c.enterorg and a.ownership=c.ownership and a.differencekind=c.differencekind" +
					" and a.propertyno=c.propertyno and a.serialno=c.serialno"+					
					" and b.enterorg=c.enterorg and b.ownership=c.ownership and b.differencekind=c.differencekind" +
					" and b.mergedivision=c.mergedivision and b.mergedivisionbatch=c.mergedivisionbatch"+
					" and b.enterorg = " + Common.sqlChar(this.getEnterOrg()) +
					" and b.ownership = " + Common.sqlChar(this.getOwnership()) +
					" and b.differencekind = " + Common.sqlChar(this.getDifferenceKind()) +
					" and a.serialno1 = " + Common.sqlChar(strKeySet[i].split(",")[0]) +					
					" and b.propertyno = " + Common.sqlChar(this.getPropertyNo_Add()) +
					" and b.serialno = " + Common.sqlChar(this.getSerialNo_Add()) +
					" and c.serialno = " + Common.sqlChar(strKeySet[i].split(",")[1]) +
//					" and ((" +
//								" c.propertyKind = '04' " +
//								" and a.isdefault = '1'"+
//							") or (" +
//								" c.propertyKind != '04' " +
//								" and (case a.useDateS when null then '00000000' else a.useDateS end) <= " + Common.sqlChar(Datetime.getYYYYMMDD()) +
//								" and (case a.useDateE when null then '99990999' else a.useDateE end) >= " + Common.sqlChar(Datetime.getYYYYMMDD()) + 
//							"))" +
					"";

		this.execSQL_ForNoResult_NoChange(sql);
		
		//=========================================================================
		
		sql="select cause from UNTLA_MANAGE a, UNTLA_REDUCEDETAIL c" + 
				" where 1=1" +
				" and a.enterorg=c.enterorg and a.ownership=c.ownership and a.differencekind=c.differencekind" +
				" and a.propertyno=c.propertyno and a.serialno=c.serialno"+					
				" and c.enterorg = " + Common.sqlChar(this.getEnterOrg()) +
				" and c.ownership = " + Common.sqlChar(this.getOwnership()) +
				" and c.differencekind = " + Common.sqlChar(this.getDifferenceKind()) +
				" and a.serialno1 = " + Common.sqlChar(strKeySet[i].split(",")[0]) +					
				" and c.serialno = " + Common.sqlChar(strKeySet[i].split(",")[1]);
		
		resultStr=this.getNameData("cause", sql);
		
		NewSerialNo1str = getNewSerialNo1("UNTLA_MANAGE");
		
		sql="update UNTLA_MANAGE_"+getEditID()+"2 a set" +				
			(("401".equals(resultStr))
					?(" useArea = " +
							"case when " +
									"(select count(*) from UNTLA_MANAGE b"+
										" where 1=1"+
										" and b.enterorg = " + Common.sqlChar(this.getEnterOrg()) + 
										" and b.ownership = " + Common.sqlChar(this.getOwnership()) +
										" and b.differencekind = " + Common.sqlChar(this.getDifferenceKind()) +
										" and b.propertyno = " + Common.sqlChar(this.getPropertyNo_Add()) +
										" and b.serialno = " + Common.sqlChar(strKeySet[i].split(",")[1]) +
//										" and ((" +
//													"(select z.propertyKind from untla_ReduceDetail z" + 
//														" where 1=1" + 
//														" and z.enterorg=b.enterorg" + 
//														" and z.ownership=b.ownership" + 
//														" and z.differencekind=b.differencekind" +
//														" and z.propertyno=b.propertyno" +
//														" and z.serialno=b.serialno)" +
//													" = '04' " +
//													" and b.isdefault = '1'"+
//												") or (" +
//													"(select z.propertyKind from untla_ReduceDetail z" + 
//														" where 1=1" + 
//														" and z.enterorg=b.enterorg" + 
//														" and z.ownership=b.ownership" + 
//														" and z.differencekind=b.differencekind" +
//														" and z.propertyno=b.propertyno" +
//														" and z.serialno=b.serialno)" +
//													" != '04' " +
//													" and (case b.useDateS when null then '0000000' else b.useDateS end) <= " + Common.sqlChar(Datetime.getYYYMMDD()) +
//													" and (case b.useDateE when null then '9999999' else b.useDateE end) >= " + Common.sqlChar(Datetime.getYYYMMDD()) + 
//												"))" +
								") = 1" +
										" then " +
												"(select b.holdarea from UNTLA_REDUCEDETAIL b"+
													" where 1=1"+
													" and b.enterorg = " + Common.sqlChar(this.getEnterOrg()) + 
													" and b.ownership = " + Common.sqlChar(this.getOwnership()) + 
													" and b.differencekind = " + Common.sqlChar(this.getDifferenceKind()) +
													" and b.propertyno = " + Common.sqlChar(this.getPropertyNo_Add()) +
													" and b.serialno = " + Common.sqlChar(strKeySet[i].split(",")[1]) +
												")" +
										" else " +
												"(select b.usearea from UNTLA_MANAGE b"+
													" where 1=1"+
													" and b.enterOrg = " + Common.sqlChar(this.getEnterOrg()) + 
													" and b.ownership = " + Common.sqlChar(this.getOwnership()) +
													" and b.differencekind = " + Common.sqlChar(this.getDifferenceKind()) +
													" and b.propertyno = " + Common.sqlChar(this.getPropertyNo_Add()) +
													" and b.serialno = " + Common.sqlChar(strKeySet[i].split(",")[1]) +
													" and b.serialno1 = a.serialNo1" +
												")" +
										" end,")									
					:"") +			
			" serialNo1 = " + Common.sqlChar(NewSerialNo1str) + "," +
			" editID = " + Common.sqlChar(this.getEditID()) + "," +
			" editDate = " + Common.sqlChar(this.getEditDate()) + "," +
			" editTime = " + Common.sqlChar(this.getEditTime());
		
		this.execSQL_ForNoResult_NoChange(sql);
		
		sql="insert into UNTLA_MANAGE_"+getEditID()+" select * from UNTLA_MANAGE_"+getEditID()+"2";
		this.execSQL_ForNoResult_NoChange(sql);
				
		sql="drop table UNTLA_MANAGE_"+getEditID()+"2";
		this.execSQL_ForNoResult_NoChange(sql,true);
		
		//=========================================================================
	}
	
	sql="update UNTLA_MANAGE_"+getEditID()+" set" +
			((checkBoxLength == 1)?" usearea = (select holdarea from UNTLA_LAND where 1=1" +
								" and enterorg = " + Common.sqlChar(this.getEnterOrg()) + 
								" and ownership = " + Common.sqlChar(this.getOwnership()) + 
								" and differencekind = " + Common.sqlChar(this.getDifferenceKind()) +
								" and propertyno = " + Common.sqlChar(this.getPropertyNo_Add()) +
								" and serialno = " + Common.sqlChar(this.getSerialNo_Add()) + 
								"),"
						:"") + 
			" propertyno = " + Common.sqlChar(this.getPropertyNo_Add()) + "," +
			" serialno = " + Common.sqlChar(this.getSerialNo_Add());
	this.execSQL_ForNoResult_NoChange(sql);
	

	sql="insert into UNTLA_MANAGE select * from UNTLA_MANAGE_"+getEditID()+"";
	this.execSQL_ForNoResult_NoChange(sql);
	
	
	sql="drop table UNTLA_MANAGE_"+getEditID()+"";
	this.execSQL_ForNoResult_NoChange(sql,true);
	//=========================================================================
	
	extend_afterWork();
	
	NewSerialNo1str = null;
}

	private String getNewSerialNo1(String table){
		String resultStr="";
		
		String sql="select (max(serialno1) + 1) as serialno1 from " +
					"("+
					" select a.serialno1 from "+table+"_"+getEditID()+" a"+
					" union"+
					" select a.serialno1 from "+table+" a, UNTLA_LAND b" + 
					" where 1=1" +
					" and a.enterorg=b.enterorg and a.ownership=b.ownership and a.differencekind=b.differencekind" +
					" and a.propertyNo=b.propertyNo and a.serialNo=b.serialNo"+
					" and a.enterOrg = " + Common.sqlChar(this.getEnterOrg()) +
					" and a.ownership = " + Common.sqlChar(this.getOwnership()) +
					" and a.differencekind = " + Common.sqlChar(this.getDifferenceKind()) +					
					" and b.propertyno = " + Common.sqlChar(this.getPropertyNo_Add()) +
					" and b.serialNo = " + Common.sqlChar(this.getSerialNo_Add()) +
					") a";
	
		resultStr=getNameData("serialno1", sql);
		if("".equals(resultStr)){
			resultStr = "001";
		}
		return resultStr;
	}
	
public void cencelWork(){	
	String sql="drop table UNTLA_MANAGE_"+getEditID()+"";
	this.execSQL_ForNoResult(sql,true);
	
	extend_cencelWork();
}

public void extend_cencelWork(){
	String sql="drop table UNTLA_PERSON_"+getEditID()+"";
	this.execSQL_ForNoResult(sql,true);
	
}


//依查詢欄位查詢多筆資料
public ArrayList queryAll() throws Exception{
	Database db = new Database();
	ArrayList objList=new ArrayList();
	
	String sql="select " +
					" a.serialNo1, " +
//					" a.signno, " +
					" a.useUnit, a.useUnit1, a.useRelation, a.useDateS, a.useDateE,"+
					" a.useArea,"+
					" (select z.organsname from sysca_organ z where z.organid=a.useUnit) as useUnitName,"+
					" (select codename from SYSCA_CODE z where codekindid = 'URE' and z.codeid = a.useRelation) as useRelationName," +
					" a.serialNo" +
				" from untla_manage a, untla_land b, untla_reducedetail c where 1=1"+
					" and a.enterorg=b.enterorg and a.ownership=b.ownership and a.differencekind=b.differencekind"+
					" and a.enterorg=c.enterorg and a.ownership=c.ownership and a.differencekind=c.differencekind"+
					" and a.propertyNo=c.propertyNo and a.serialNo=c.serialNo"+
					" and b.mergedivision=c.mergedivision and b.mergedivisionBatch=c.mergedivisionBatch"+
					" and a.enterorg = " + Common.sqlChar(this.getEnterOrg()) +
					" and a.ownership = " + Common.sqlChar(this.getOwnership()) +
					" and a.differencekind = " + Common.sqlChar(this.getDifferenceKind()) +					
					" and b.propertyNo = " + Common.sqlChar(this.getPropertyNo_Add()) +
					" and b.serialNo = " + Common.sqlChar(this.getSerialNo_Add()) +
//					" and ((" +
//							" b.propertyKind  = '04' " +
//							" and a.isdefault = '1'"+
//						") or (" +
//							" b.propertyKind  != '04' " +
//							" and (case a.useDateS when null then '0000000' else a.useDateS end) <= " + Common.sqlChar(Datetime.getYYYMMDD()) +
//							" and (case a.useDateE when null then '9999999' else a.useDateE end) >= " + Common.sqlChar(Datetime.getYYYMMDD()) + 
//						"))" +
					"";
	
	sql+=" order by a.enterorg, a.ownership, a.differencekind, a.propertyno, a.serialno, a.serialNo1 ";

	try {
		LogBean.outputLogDebug(sql);
		
		ResultSet rs = db.querySQL(sql,true);
		processCurrentPageAttribute(rs);			
		if (getTotalRecord() > 0) {
			int count = getRecordStart();
			int end = getRecordEnd();
			do {
				if (count > end)
					break;
			String rowArray[]=new String[9];
			
			rowArray[0]=Common.get(rs.getString("serialno1"));
//			rowArray[1]=Common.get(rs.getString("signno")).substring(0,1) + "000000";
//			rowArray[2]=Common.get(rs.getString("signno")).substring(0,3) + "0000";
//			rowArray[3]=Common.get(rs.getString("signno")).substring(0,7);
//			rowArray[4]=Common.get(rs.getString("signno")).substring(8,11);
//			rowArray[5]=Common.get(rs.getString("signno")).substring(11);
			rowArray[1]=Common.get(rs.getString("useUnit"));
			rowArray[2]=Common.get(rs.getString("useUnitName"));
			rowArray[3]=Common.get(rs.getString("useUnit1"));
			rowArray[4]=Common.get(rs.getString("useRelation"));
			rowArray[5]=Common.get(rs.getString("useDateS"));
			rowArray[6]=Common.get(rs.getString("useDateE"));
			rowArray[7]=Common.get(rs.getString("useArea"));
			rowArray[8]=Common.get(rs.getString("serialNo"));
			
			objList.add(rowArray);
			count++;
			} while (rs.next());
		}
		setStateQueryAllSuccess();
	} catch (Exception e) {
		LogBean.outputLogError("Exception : "+e.getMessage()+"\n"+sql);
		
	} finally {
		db.closeAll();
	}
	return objList;
}

}


