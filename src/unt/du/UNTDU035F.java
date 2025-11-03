/*
*<br>程式目的：動產增減值作業-增減值單明細資料
*<br>程式代號：
*<br>程式日期：0970711
*<br>程式作者：shan
*<br>--------------------------------------------------------
*<br>修改作者　　修改日期　　　修改目的
*<br>--------------------------------------------------------
*/

package unt.du;

import java.sql.ResultSet;
import java.util.ArrayList;
import util.*;

public class UNTDU035F extends QueryBean{

//主ｋｅｙ
String enterOrg;
String enterOrgName;
String ownership;
String caseNo;
String propertyNo;
String lotNo;
String serialNo;

public String getEnterOrg(){ return checkGet(enterOrg); }
public void setEnterOrg(String s){ enterOrg=checkSet(s); }
public String getEnterOrgName(){ return checkGet(enterOrgName); }
public void setEnterOrgName(String s){ enterOrgName=checkSet(s); }
public String getOwnership(){ return checkGet(ownership); }
public void setOwnership(String s){ ownership=checkSet(s); }
public String getCaseNo(){ return checkGet(caseNo); }
public void setCaseNo(String s){ caseNo=checkSet(s); }
public String getPropertyNo(){ return checkGet(propertyNo); }
public void setPropertyNo(String s){ propertyNo=checkSet(s); }
public String getLotNo(){ return checkGet(lotNo); }
public void setLotNo(String s){ lotNo=checkSet(s); }
public String getSerialNo(){ return checkGet(serialNo); }
public void setSerialNo(String s){ serialNo=checkSet(s); }

//查詢
String q_enterOrg;
String q_enterOrgName;
String q_ownership;
String q_caseNo;
String q_propertyNo;
String q_serialNo;
String q_lotNo;
public String getQ_enterOrg(){ return checkGet(q_enterOrg); }
public void setQ_enterOrg(String s){ q_enterOrg=checkSet(s); }
public String getQ_enterOrgName(){ return checkGet(q_enterOrgName); }
public void setQ_enterOrgName(String s){ q_enterOrgName=checkSet(s); }
public String getQ_ownership(){ return checkGet(q_ownership); }
public void setQ_ownership(String s){ q_ownership=checkSet(s); }
public String getQ_caseNo(){ return checkGet(q_caseNo); }
public void setQ_caseNo(String s){ q_caseNo=checkSet(s); }
public String getQ_propertyNo(){ return checkGet(q_propertyNo); }
public void setQ_propertyNo(String s){ q_propertyNo=checkSet(s); }
public String getQ_serialNo(){ return checkGet(q_serialNo); }
public void setQ_serialNo(String s){ q_serialNo=checkSet(s); }
public String getQ_lotNo(){ return checkGet(q_lotNo); }
public void setQ_lotNo(String s){ q_lotNo=checkSet(s); }
String q_chengClass;
public String getQ_chengClass(){ return checkGet(q_chengClass); }
public void setQ_chengClass(String s){ q_chengClass=checkSet(s); }

//提供修改欄位
String propertyName1;
String cause;
String adjustDate;
String verify;
String propertyKind;
String fundType;
String valuable;
String sourceDate;
String bookAmount;
String oldbookUnit;
String oldBookValue;
String newBookUnit;
String newBookValue;
String adjustType;
String adjustBookUnit;
String adjustBookValue;
String bookNotes;
String keepUnit;
String keeper;
String useUnit;
String userNo;
String place;
String notes;
String closing;
String otherMaterial;
String otherPropertyUnit;
String otherLimitYear;
String oldPropertyNo;
String oldSerialNo;
public String getPropertyName1(){ return checkGet(propertyName1); }	public void setPropertyName1(String s){ propertyName1=checkSet(s); }
public String getCause(){ return checkGet(cause); }	public void setCause(String s){ cause=checkSet(s); }
public String getAdjustDate(){ return checkGet(adjustDate); }	public void setAdjustDate(String s){ adjustDate=checkSet(s); }
public String getVerify(){ return checkGet(verify); }	public void setVerify(String s){ verify=checkSet(s); }
public String getPropertyKind(){ return checkGet(propertyKind); }	public void setPropertyKind(String s){ propertyKind=checkSet(s); }
public String getFundType(){ return checkGet(fundType); }	public void setFundType(String s){ fundType=checkSet(s); }
public String getValuable(){ return checkGet(valuable); }	public void setValuable(String s){ valuable=checkSet(s); }
public String getSourceDate(){ return checkGet(sourceDate); }	public void setSourceDate(String s){ sourceDate=checkSet(s); }
public String getBookAmount(){ return checkGet(bookAmount); }	public void setBookAmount(String s){ bookAmount=checkSet(s); }
public String getOldbookUnit(){ return checkGet(oldbookUnit); }	public void setOldbookUnit(String s){ oldbookUnit=checkSet(s); }
public String getOldBookValue(){ return checkGet(oldBookValue); }	public void setOldBookValue(String s){ oldBookValue=checkSet(s); }
public String getNewBookUnit(){ return checkGet(newBookUnit); }	public void setNewBookUnit(String s){ newBookUnit=checkSet(s); }
public String getNewBookValue(){ return checkGet(newBookValue); }	public void setNewBookValue(String s){ newBookValue=checkSet(s); }
public String getAdjustType(){ return checkGet(adjustType); }	public void setAdjustType(String s){ adjustType=checkSet(s); }
public String getAdjustBookUnit(){ return checkGet(adjustBookUnit); }	public void setAdjustBookUnit(String s){ adjustBookUnit=checkSet(s); }
public String getAdjustBookValue(){ return checkGet(adjustBookValue); }	public void setAdjustBookValue(String s){ adjustBookValue=checkSet(s); }
public String getBookNotes(){ return checkGet(bookNotes); }	public void setBookNotes(String s){ bookNotes=checkSet(s); }
public String getKeepUnit(){ return checkGet(keepUnit); }	public void setKeepUnit(String s){ keepUnit=checkSet(s); }
public String getKeeper(){ return checkGet(keeper); }	public void setKeeper(String s){ keeper=checkSet(s); }
public String getUseUnit(){ return checkGet(useUnit); }	public void setUseUnit(String s){ useUnit=checkSet(s); }
public String getUserNo(){ return checkGet(userNo); }	public void setUserNo(String s){ userNo=checkSet(s); }
public String getPlace(){ return checkGet(place); }	public void setPlace(String s){ place=checkSet(s); }
public String getNotes(){ return checkGet(notes); }	public void setNotes(String s){ notes=checkSet(s); }
public String getClosing(){ return checkGet(closing); }	public void setClosing(String s){ closing=checkSet(s); }
public String getOtherMaterial(){ return checkGet(otherMaterial); }	public void setOtherMaterial(String s){ otherMaterial=checkSet(s); }
public String getOtherPropertyUnit(){ return checkGet(otherPropertyUnit); }	public void setOtherPropertyUnit(String s){ otherPropertyUnit=checkSet(s); }
public String getOtherLimitYear(){ return checkGet(otherLimitYear); }	public void setOtherLimitYear(String s){ otherLimitYear=checkSet(s); }
public String getOldPropertyNo(){ return checkGet(oldPropertyNo); }	public void setOldPropertyNo(String s){ oldPropertyNo=checkSet(s); }
public String getOldSerialNo(){ return checkGet(oldSerialNo); }	public void setOldSerialNo(String s){ oldSerialNo=checkSet(s); }

//傳回update sql
protected String[] getUpdateSQL(){
	String[] execSQLArray = new String[1];
	execSQLArray[0] = " update UNTMP_AdjustDetail set "
					+ " propertyName1= " +  Common.sqlChar(propertyName1) + ","
					+ " cause= " +  Common.sqlChar(cause) + ","
					+ " adjustDate= " +  Common.sqlChar(adjustDate) + ","
					+ " verify= " +  Common.sqlChar(verify) + ","
					+ " propertyKind= " +  Common.sqlChar(propertyKind) + ","
					+ " fundType= " +  Common.sqlChar(fundType) + ","
					+ " valuable= " +  Common.sqlChar(valuable) + ","
					+ " sourceDate= " +  Common.sqlChar(sourceDate) + ","
					+ " bookAmount= " +  Common.sqlChar(bookAmount) + ","
					+ " oldbookUnit= " +  Common.sqlChar(oldbookUnit) + ","
					+ " oldBookValue= " +  Common.sqlChar(oldBookValue) + ","
					+ " newBookUnit= " +  Common.sqlChar(newBookUnit) + ","
					+ " newBookValue= " +  Common.sqlChar(newBookValue) + ","
					+ " adjustType= " +  Common.sqlChar(adjustType) + ","
					+ " adjustBookUnit= " +  Common.sqlChar(adjustBookUnit) + ","
					+ " adjustBookValue= " +  Common.sqlChar(adjustBookValue) + ","
					+ " bookNotes= " +  Common.sqlChar(bookNotes) + ","
					//+ " keepUnit= " +  Common.sqlChar(keepUnit) + ","
					//+ " keeper= " +  Common.sqlChar(keeper) + ","
					//+ " useUnit= " +  Common.sqlChar(useUnit) + ","
					//+ " userNo= " +  Common.sqlChar(userNo) + ","
					+ " place= " +  Common.sqlChar(place) + ","
					+ " notes= " +  Common.sqlChar(notes) + ","
					+ " closing= " +  Common.sqlChar(closing) + ","
					+ " otherMaterial= " +  Common.sqlChar(otherMaterial) + ","
					+ " otherPropertyUnit= " +  Common.sqlChar(otherPropertyUnit) + ","
					+ " otherLimitYear= " +  Common.sqlChar(otherLimitYear) + ","
					+ " oldPropertyNo= " +  Common.sqlChar(oldPropertyNo) + ","
					+ " oldSerialNo= " +  Common.sqlChar(oldSerialNo) + ","
					+ " editID= " +  Common.sqlChar(getEditID()) + ","
					+ " editDate= " +  Common.sqlChar(getEditDate()) + ","
					+ " editTime= " +  Common.sqlChar(getEditTime())
					+ " where 1=1 " 
			   		+ " and enterOrg = " + Common.sqlChar(enterOrg)
			   		+ " and ownership = " + Common.sqlChar(ownership)
			   		+ " and caseNo = " + Common.sqlChar(caseNo)
			   		+ " and propertyNo = " + Common.sqlChar(propertyNo)
			   		+ " and serialNo = " + Common.sqlChar(serialNo)
					+ "";
	//System.out.println(execSQLArray[0]);
	return execSQLArray;
}

/**
 * <br>
 * <br>目的：依主鍵查詢單一資料
 * <br>參數：無
 * <br>傳回：傳回本物件
*/

public UNTDU035F queryOne() throws Exception{
	Database db = new Database();
	UNTDU035F obj = this;
	try {
		String sql = " select a.enterOrg ,o.organsname as enterOrgName ,a.ownership ,a.caseNo ,a.propertyNo ,a.serialNo " + "\n"
				   + " 		  ,propertyName1 ,cause ,adjustDate ,verify ,propertyKind " + "\n"
				   + " 		  ,fundType ,valuable ,sourceDate ,bookAmount ,oldbookUnit " + "\n"
				   + " 		  ,oldBookValue ,newBookUnit ,newBookValue ,adjustType " + "\n"
				   + " 		  ,adjustBookUnit ,adjustBookValue ,bookNotes ,keepUnit " + "\n"
				   + " 		  ,keeper ,useUnit ,userNo ,place ,notes ,closing " + "\n"
				   + " 		  ,otherMaterial ,otherPropertyUnit ,otherLimitYear " + "\n"
				   + " 		  ,oldPropertyNo ,oldSerialNo " + "\n"
				   + "		  ,(select x.unitname From Untmp_Keepunit x where x.enterorg = a.enterorg and x.unitno = a.keepUnit ) as keepUnitName " + "\n"
				   + "		  ,(select x.unitname From Untmp_Keepunit x where x.enterorg = a.enterorg and x.unitno = a.useUnit ) as useUnitName " + "\n"
				   + "		  ,(select x.keepername from untmp_keeper x where x.enterorg = a.enterorg and x.unitno = a.keepUnit and x.keeperno = a.keeper ) as keeperName " + "\n"
				   + "		  ,(select x.keepername from untmp_keeper x where x.enterorg = a.enterorg and x.unitno = a.useUnit and x.keeperno = a.userNo ) as userName " + "\n"
				   + " 		  ,a.editid ,a.editDate ,a.editTime " + "\n"
		   		   + " from UNTMP_AdjustDetail a ,sysca_organ o " + "\n"
		   		   + " where 1=1 " + "\n"
		   		   + " and a.enterorg=o.organid " + "\n" 
		   		   
		   		   + " and a.enterOrg = " + Common.sqlChar(enterOrg)
		   		   + " and a.ownership = " + Common.sqlChar(ownership)
		   		   + " and a.caseNo = " + Common.sqlChar(caseNo)
		   		   + " and a.propertyNo = " + Common.sqlChar(propertyNo)
		   		   + " and a.serialNo = " + Common.sqlChar(serialNo)
		   		   
		   		   + " order by caseno ,propertyno ,serialno"
		   		   + "";

		//System.out.println(sql);
		ResultSet rs = db.querySQL(sql);
		if (rs.next()){
			
			obj.setEnterOrg(rs.getString("enterOrg"));
			obj.setEnterOrgName(rs.getString("enterOrgName"));
			obj.setOwnership(rs.getString("ownership"));
			obj.setPropertyNo(rs.getString("propertyNo"));
			obj.setSerialNo(rs.getString("serialNo"));

			obj.setPropertyName1(rs.getString("propertyName1"));
			obj.setCause(rs.getString("cause"));
			obj.setAdjustDate(rs.getString("adjustDate"));
			obj.setVerify(rs.getString("verify"));
			obj.setPropertyKind(rs.getString("propertyKind"));
			obj.setFundType(rs.getString("fundType"));
			obj.setValuable(rs.getString("valuable"));
			obj.setSourceDate(rs.getString("sourceDate"));
			obj.setBookAmount(rs.getString("bookAmount"));
			obj.setOldbookUnit(rs.getString("oldbookUnit"));
			obj.setOldBookValue(rs.getString("oldBookValue"));
			obj.setNewBookUnit(rs.getString("newBookUnit"));
			obj.setNewBookValue(rs.getString("newBookValue"));
			obj.setAdjustType(rs.getString("adjustType"));
			obj.setAdjustBookUnit(rs.getString("adjustBookUnit"));
			obj.setAdjustBookValue(rs.getString("adjustBookValue"));
			obj.setBookNotes(rs.getString("bookNotes"));
			obj.setKeepUnit(rs.getString("keepUnit"));
			obj.setKeeper(rs.getString("keeper"));
			obj.setUseUnit(rs.getString("useUnit"));
			obj.setUserNo(rs.getString("userNo"));
			obj.setPlace(rs.getString("place"));
			obj.setNotes(rs.getString("notes"));
			obj.setClosing(rs.getString("closing"));
			obj.setOtherMaterial(rs.getString("otherMaterial"));
			obj.setOtherPropertyUnit(rs.getString("otherPropertyUnit"));
			obj.setOtherLimitYear(rs.getString("otherLimitYear"));
			obj.setOldPropertyNo(rs.getString("oldPropertyNo"));
			obj.setOldSerialNo(rs.getString("oldSerialNo"));
			
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
	try {
		String sql = " select a.enterOrg ,o.organsname as enterOrgName ,a.ownership ,a.caseno ,a.propertyNo ,a.serialNo " + "\n"
				   + "		  ,(select x.codeName from sysca_code x where a.ownership = x.codeid and x.codekindid = 'OWA') as ownershipName "
				   + " from UNTMP_AdjustDetail a ,sysca_organ o " + "\n"
				   + " where 1=1 " + "\n"
				   + " and a.enterorg=o.organid " + "\n" ;
				   if (!"".equals(getQ_enterOrg()))
				   {	sql += " and a.enterOrg = " + Common.sqlChar(getQ_enterOrg()) ;	}
				   if (!"".equals(getQ_ownership()))
				   {	sql += " and a.ownership = " + Common.sqlChar(getQ_ownership()) ;	}
				   if (!"".equals(getQ_caseNo()))
				   {	sql += " and a.caseNo = " + Common.sqlChar(getQ_caseNo()) ;		}
				   if (!"".equals(getQ_propertyNo()))
				   {	sql += " and a.propertyNo = " + Common.sqlChar(getQ_propertyNo()) ;	}
				   if (!"".equals(getQ_serialNo()))
				   {	sql += " and a.serialNo = " + Common.sqlChar(getQ_serialNo());	}

				sql += " order by enterorg ,ownership ,caseno ,propertyNo ,serialNo ";
			
		//System.out.println(sql);
		ResultSet rs = db.querySQL(sql,true);
		processCurrentPageAttribute(rs);
		if (getTotalRecord() > 0) {
			int count = getRecordStart();
			int end = getRecordEnd();
			do {
				if (count > end) break;
			String rowArray[]=new String[7];
			rowArray[0]=Common.get(rs.getString("enterOrg")); 
			rowArray[1]=Common.get(rs.getString("enterOrgName")); 
			rowArray[2]=Common.get(rs.getString("ownership"));
			rowArray[3]=Common.get(rs.getString("ownershipName"));
			rowArray[4]=Common.get(rs.getString("caseno"));
			rowArray[5]=Common.get(rs.getString("propertyNo"));
			rowArray[6]=Common.get(rs.getString("serialNo"));
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
