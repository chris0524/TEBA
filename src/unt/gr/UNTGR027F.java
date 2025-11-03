/*
*<br>程式目的：動產作業主檔資料修改
*<br>程式代號：untdu012f
*<br>程式日期：0970711
*<br>程式作者：shan
*<br>--------------------------------------------------------
*<br>修改作者　　修改日期　　　修改目的
*<br>--------------------------------------------------------
*/

package unt.gr;

import java.sql.ResultSet;
import java.util.ArrayList;
import util.*;

public class UNTGR027F extends QueryBean{


String enterOrg;
String enterOrgName;
public String getEnterOrg(){ return checkGet(enterOrg); }
public void setEnterOrg(String s){ enterOrg=checkSet(s); }
public String getEnterOrgName(){ return checkGet(enterOrgName); }
public void setEnterOrgName(String s){ enterOrgName=checkSet(s); }

String ownership;
public String getOwnership(){ return checkGet(ownership); }
public void setOwnership(String s){ ownership=checkSet(s); }

String fundCode;
public String getFundCode(){ return checkGet(fundCode); }
public void setFundCode(String s){ fundCode=checkSet(s); }

String propertyClass;
public String getPropertyClass(){ return checkGet(propertyClass); }
public void setPropertyClass(String s){ propertyClass=checkSet(s); }

String closeYM;
String closeAmount;
String closeArea;
String closeValue;
public String getCloseYM(){ return checkGet(closeYM); }
public void setCloseYM(String s){ closeYM=checkSet(s); }
public String getCloseAmount(){ return checkGet(closeAmount); }
public void setCloseAmount(String s){ closeAmount=checkSet(s); }
public String getCloseArea(){ return checkGet(closeArea); }
public void setCloseArea(String s){ closeArea=checkSet(s); }
public String getCloseValue(){ return checkGet(closeValue); }
public void setCloseValue(String s){ closeValue=checkSet(s); }

String notes;
public String getNotes(){ return checkGet(notes); }
public void setNotes(String s){ notes=checkSet(s); }

String q_enterOrg;
String q_enterOrgName;
String q_ownership;
public String getQ_enterOrg(){ return checkGet(q_enterOrg); }
public void setQ_enterOrg(String s){ q_enterOrg=checkSet(s); }
public String getQ_enterOrgName(){ return checkGet(q_enterOrgName); }
public void setQ_enterOrgName(String s){ q_enterOrgName=checkSet(s); }
public String getQ_ownership(){ return checkGet(q_ownership); }
public void setQ_ownership(String s){ q_ownership=checkSet(s); }

String q_propertyClass;
public String getQ_propertyClass(){ return checkGet(q_propertyClass); }
public void setQ_propertyClass(String s){ q_propertyClass=checkSet(s); }
String q_closeYM;
public String getQ_closeYM(){ return checkGet(q_closeYM); }
public void setQ_closeYM(String s){ q_closeYM=checkSet(s); }

//傳回執行insert前之檢查sql
protected String[][] getInsertCheckSQL(){
	String[][] checkSQLArray = new String[1][4];
 	checkSQLArray[0][0] = " select count(*) as checkResult from untgr_fundmanage where 1=1 " 
						+ " and enterOrg = " + Common.sqlChar(enterOrg)
						+ " and ownership = " + Common.sqlChar(ownership)
						+ " and fundCode = " + Common.sqlChar(fundCode)
						+ " and propertyClass = " + Common.sqlChar(propertyClass)
						+ " and closeYM = " + Common.sqlChar(closeYM) 
						+ "";
	checkSQLArray[0][1]=">";
	checkSQLArray[0][2]="0";
	checkSQLArray[0][3]="該筆資料己重複，請重新輸入！";
	return checkSQLArray;
}


//傳回insert sql
protected String[] getInsertSQL(){
	String[] execSQLArray = new String[1];
	execSQLArray[0]	= " insert into untgr_fundmanage( "
					+ " enterOrg "
					+ " ,ownership "
					+ " ,fundCode "
					+ " ,propertyClass "
					+ " ,closeYM "
					+ " ,closeAmount "
					+ " ,closeArea "
					+ " ,closeValue "
					+ " ,notes "
					+ " ,editID "
					+ " ,editDate "
					+ " ,editTime "
					+ " )Values( "
					+ Common.sqlChar(enterOrg)		+ ","
					+ Common.sqlChar(ownership)		+ ","
					+ Common.sqlChar(fundCode)		+ ","
					+ Common.sqlChar(propertyClass)	+ ","
					+ Common.sqlChar(closeYM)		+ ","
					+ Common.sqlChar(closeAmount)	+ ","
					+ Common.sqlChar(closeArea)		+ ","
					+ Common.sqlChar(closeValue)	+ ","
					+ Common.sqlChar(notes) 		+ ","
					+ Common.sqlChar(getEditID()) 	+ ","
					+ Common.sqlChar(getEditDate()) + ","
					+ Common.sqlChar(getEditTime()) 
					+ ")" ;
	return execSQLArray;
}


//傳回update sql
protected String[] getUpdateSQL(){
	String[] execSQLArray = new String[1];
	execSQLArray[0] = " update untgr_fundmanage set "
	    			+ " closeAmount = " 	+ Common.sqlChar(closeAmount)
	    			+ " ,closeArea = " 		+ Common.sqlChar(closeArea)
	    			+ " ,closeValue = " 		+ Common.sqlChar(closeValue)
	    			+ " ,notes = " 			+ Common.sqlChar(notes)
	    			+ " ,editID = " 			+ Common.sqlChar(getEditID())
	    			+ " ,editDate = " 		+ Common.sqlChar(getEditDate())
	    			+ " ,editTime = " 		+ Common.sqlChar(getEditTime())
	    			+ " where 1=1 "
					+ " and enterOrg = " + Common.sqlChar(enterOrg)
					+ " and ownership = " + Common.sqlChar(ownership)
					+ " and fundCode = " + Common.sqlChar(fundCode)
					+ " and propertyClass = " + Common.sqlChar(propertyClass)
					+ " and closeYM = " + Common.sqlChar(closeYM) 
					+ "";
//	System.out.println(execSQLArray[0]);
	return execSQLArray;
}

//傳回delete sql
protected String[] getDeleteSQL(){
	String[] execSQLArray = new String[1];
	execSQLArray[0] = " delete untgr_fundmanage where 1=1 "
					+ " and enterOrg = " + Common.sqlChar(enterOrg)
					+ " and ownership = " + Common.sqlChar(ownership)
					+ " and fundCode = " + Common.sqlChar(fundCode)
					+ " and propertyClass = " + Common.sqlChar(propertyClass)
					+ " and closeYM = " + Common.sqlChar(closeYM) 
					+ "";
	return execSQLArray;
}

/**
 * <br>
 * <br>目的：依主鍵查詢單一資料
 * <br>參數：無
 * <br>傳回：傳回本物件
*/
public UNTGR027F  queryOne() throws Exception{
	Database db = new Database();
	UNTGR027F obj = this;
	try {
		String sql = " select a.enterorg ,b.organsname as enterorgName ,a.ownership ,a.fundcode " + "\n"
				   + "        ,a.propertyclass " + "\n"
				   + "        ,a.closeym ,a.closeamount ,a.closearea ,a.closevalue ,a.notes ,a.editid ,a.editdate ,a.edittime " + "\n"
				   + " from untgr_fundmanage a ,sysca_organ b " + "\n"
				   + " where 1=1 " + "\n"
				   + " and a.enterorg = b.organid(+) " + "\n"
				   + " and a.enterOrg = " + Common.sqlChar(enterOrg)
				   + " and a.ownership = " + Common.sqlChar(ownership)
				   + " and a.fundCode = " + Common.sqlChar(fundCode)
				   + " and a.propertyClass = " + Common.sqlChar(propertyClass)
				   + " and a.closeYM = " + Common.sqlChar(closeYM) 
				   + " ";
		//System.out.println(sql);
		ResultSet rs = db.querySQL(sql);
		if (rs.next()){
			obj.setEnterOrg(rs.getString("enterorg"));
			obj.setEnterOrgName(rs.getString("enterorgName"));
			obj.setOwnership(rs.getString("ownership"));
			obj.setFundCode(rs.getString("fundcode"));
			obj.setPropertyClass(rs.getString("propertyclass"));
			obj.setCloseYM(rs.getString("closeym"));
			obj.setCloseAmount(rs.getString("closeamount"));
			obj.setCloseArea(rs.getString("closearea"));
			obj.setCloseValue(rs.getString("closevalue"));
			obj.setNotes(rs.getString("notes"));
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
		String sql = " select a.enterorg ,b.organsname as enterOrgName ,a.ownership ,a.fundcode " + "\n"
				   + "        ,a.propertyclass ,decode(a.propertyclass ,'1','土地','2','房屋建築及設備','3','動產設備') as propertyclassName " + "\n"
				   + "        ,a.closeym ,a.closeamount ,a.closearea ,a.closevalue ,a.notes " + "\n"
				   + "        ,( select x.codename from SYSCA_Code x where x.codekindid ='FNM' and x.codecon1 = a.enterorg and x.codeid = a.fundcode ) as FNM " + "\n"
				   + " from untgr_fundmanage a ,sysca_organ b " + "\n"
				   + " where 1=1 " + "\n"
				   + " and a.enterorg = b.organid(+) " + "\n"
				   + " ";
			   if(!"".equals(getQ_enterOrg()))
			   {	sql += " and a.enterorg = " + Common.sqlChar(getQ_enterOrg());				}
			   if(!"".equals(getQ_ownership()))
			   {	sql += " and a.ownership = " + Common.sqlChar(getQ_ownership());			}
			   if(!"".equals(getQ_propertyClass()))
			   {	sql += " and a.propertyclass = " + Common.sqlChar(getQ_propertyClass());	}
			   if(!"".equals(getQ_closeYM()))
			   {	sql += " and a.closeym = " + Common.sqlChar(getQ_closeYM());				}

		//System.out.println(sql);
		ResultSet rs = db.querySQL(sql,true);
		processCurrentPageAttribute(rs);
		if (getTotalRecord() > 0) {
			int count = getRecordStart();
			int end = getRecordEnd();
			do {
				if (count > end) break;
			String rowArray[]=new String[12];
			rowArray[0]=Common.get(rs.getString("enterOrg")); 
			rowArray[1]=Common.get(rs.getString("enterOrgName")); 
			rowArray[2]=Common.get(rs.getString("FNM"));
			rowArray[3]=Common.get(rs.getString("ownership"));
			rowArray[4]=Common.get(rs.getString("fundcode"));
			rowArray[5]=Common.get(rs.getString("propertyclass")); 
			rowArray[6]=Common.get(rs.getString("propertyclassName")); 
			rowArray[7]=Common.get(rs.getString("closeym"));
			rowArray[8]=Common.get(rs.getString("closeamount")); 
			rowArray[9]=Common.get(rs.getString("closearea")); 
			rowArray[10]=Common.get(rs.getString("closevalue")); 
			rowArray[11]=Common.get(rs.getString("notes")); 
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


