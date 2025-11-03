
/*
*<br>程式目的：共用財產編號維護 - 000000000A 為共用機關 
*<br>程式代號：syspk007f
*<br>程式日期：0950607
*<br>程式作者：clive.chang
*<br>--------------------------------------------------------
*<br>修改作者　　修改日期　　　修改目的
*<br>--------------------------------------------------------
*/

package sys.pk;

import java.sql.ResultSet;
import java.util.ArrayList;
import util.*;

public class SYSPK007F extends QueryBean{

String enterOrg;
String propertyType;
String propertyNo;
String propertyName;
String propertyUnit;
String material;
String limitYear;
String memo;
private String propertynoCount;


String q_propertyNo;
String q_propertyType;
String q_propertyName;

public String getEnterOrg(){ return checkGet(enterOrg); }
public void setEnterOrg(String s){ enterOrg=checkSet(s); }
public String getPropertyType(){ return checkGet(propertyType); }
public void setPropertyType(String s){ propertyType=checkSet(s); }
public String getPropertyNo(){ return checkGet(propertyNo); }
public void setPropertyNo(String s){ propertyNo=checkSet(s); }
public String getPropertyName(){ return checkGet(propertyName); }
public void setPropertyName(String s){ propertyName=checkSet(s); }
public String getPropertyUnit(){ return checkGet(propertyUnit); }
public void setPropertyUnit(String s){ propertyUnit=checkSet(s); }
public String getMaterial(){ return checkGet(material); }
public void setMaterial(String s){ material=checkSet(s); }
public String getLimitYear(){ return checkGet(limitYear); }
public void setLimitYear(String s){ limitYear=checkSet(s); }
public String getMemo(){ return checkGet(memo); }
public void setMemo(String s){ memo=checkSet(s); }
public String getPropertynoCount() { return checkGet(propertynoCount); }
public void setPropertynoCount(String s) { propertynoCount = checkSet(s); }


public String getQ_propertyNo(){ return checkGet(q_propertyNo); }
public void setQ_propertyNo(String s){ q_propertyNo=checkSet(s); }
public String getQ_propertyType(){ return checkGet(q_propertyType); }
public void setQ_propertyType(String s){ q_propertyType=checkSet(s); }
public String getQ_propertyName(){ return checkGet(q_propertyName); }
public void setQ_propertyName(String s){ q_propertyName=checkSet(s); }


//傳回執行insert前之檢查sql
protected String[][] getInsertCheckSQL(){
	String[][] checkSQLArray = new String[1][4];
 	checkSQLArray[0][0]=" select count(*) as checkresult from SYSPK_PROPERTYCODE where enterorg='000000000A' " + 
		" and propertyno = " + Common.sqlChar(propertyNo);
	checkSQLArray[0][1]=">";
	checkSQLArray[0][2]="0";
	checkSQLArray[0][3]="該筆資料己重複，請重新輸入！";
	return checkSQLArray;
}


//傳回insert sql
protected String[] getInsertSQL(){
	String[] execSQLArray = new String[1];
	execSQLArray[0]=" insert into SYSPK_PROPERTYCODE(" +
			" enterorg,"+
			" propertyno,"+
			" propertytype,"+
			" propertyname,"+
			" propertyunit,"+
			" material,"+
			" limityear,"+
			" memo,"+
			" editid,"+
			" editdate,"+
			" edittime,"+
			" propertynocount "+
		" )Values(" +
			Common.sqlChar("000000000A") + "," +
			Common.sqlChar(propertyNo) + "," +
			Common.sqlChar(propertyType) + "," +
			Common.sqlChar(propertyName) + "," +
			Common.sqlChar(propertyUnit) + "," +
			Common.sqlChar(material) + "," +
			Common.sqlChar(limitYear) + "," +
			Common.sqlChar(memo) + "," +
			Common.sqlChar(getEditID()) + "," +
			Common.sqlChar(getEditDate()) + "," +
			Common.sqlChar(getEditTime()) + "," +
			Common.sqlChar(getPropertynoCount()) + ")" ;
	return execSQLArray;
}


//傳回update sql
protected String[] getUpdateSQL(){
	String[] execSQLArray = new String[1];
	execSQLArray[0]=" update SYSPK_PROPERTYCODE set " +
			" propertytype = " + Common.sqlChar(propertyType) + "," +
			" propertyname = " + Common.sqlChar(propertyName) + "," +
			" propertyunit = " + Common.sqlChar(propertyUnit) + "," +
			" material = " + Common.sqlChar(material) + "," +
			" limityear = " + Common.sqlChar(limitYear) + "," +
			" memo = " + Common.sqlChar(memo) + "," +
			" editid = " + Common.sqlChar(getEditID()) + "," +
			" editdate = " + Common.sqlChar(getEditDate()) + "," +
			" edittime = " + Common.sqlChar(getEditTime()) + "," + 
			" propertynocount = " + Common.sqlChar(getPropertynoCount()) +  
		" where enterorg='000000000A' and propertyno = " + Common.sqlChar(propertyNo);
	return execSQLArray;
}


//傳回delete sql
protected String[] getDeleteSQL(){
	String[] execSQLArray = new String[1];
	execSQLArray[0]=" delete from SYSPK_PROPERTYCODE where enterorg='000000000A' and propertyno=" + Common.sqlChar(propertyNo);	
//	execSQLArray[0]=" update SYSPK_PropertyCode set propertyType='0' where enterOrg='000000000A' " +
//			" and trim(propertyNo) = " + Common.sqlChar(propertyNo);
	return execSQLArray;
}

/**
 * <br>
 * <br>目的：依主鍵查詢單一資料
 * <br>參數：無
 * <br>傳回：傳回本物件
*/

public SYSPK007F  queryOne() throws Exception{
	Database db = new Database();
	SYSPK007F obj = this;
	try {
		String sql=" select enterorg, propertyno, propertytype, propertyname, propertyunit, material, limityear, memo, editid, editdate, edittime, propertynocount"+
			" from SYSPK_PROPERTYCODE where enterorg='000000000A' and propertyno = " + Common.sqlChar(propertyNo);
        ResultSet rs = db.querySQL(sql);
		if (rs.next()){
			obj.setEnterOrg(rs.getString("enterOrg"));			
			obj.setPropertyNo(rs.getString("propertyNo"));
			obj.setPropertyType(rs.getString("propertyType"));
			obj.setPropertyName(rs.getString("propertyName"));
			obj.setPropertyUnit(rs.getString("propertyUnit"));
			obj.setMaterial(rs.getString("material"));
			obj.setLimitYear(rs.getString("limitYear"));
			obj.setMemo(rs.getString("memo"));
			obj.setEditID(rs.getString("editID"));
			obj.setEditDate(rs.getString("editDate"));
			obj.setEditTime(rs.getString("editTime"));
			obj.setPropertynoCount(rs.getString("propertynoCount"));
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
		String sql=" select a.enterorg, a.propertyno, a.propertytype, a.propertyname, a.propertyunit, a.material, a.limityear, a.memo, a.editid, a.editdate, a.edittime , a.propertynocount"+
			" from SYSPK_PROPERTYCODE a where a.enterorg='000000000A' ";
			if (!"".equals(getQ_propertyType())) 
				sql+=" and a.propertytype = "+Common.sqlChar(q_propertyType);
			if (!"".equals(getQ_propertyNo()))
				sql+=" and a.propertyno like '" + getQ_propertyNo() + "%'" ;
			if (!"".equals(getQ_propertyName()))
				sql+=" and a.propertyname like '%" + getQ_propertyName() + "%'" ;
		ResultSet rs = db.querySQL(sql+" order by propertyno", true);
		
		processCurrentPageAttribute(rs);
		
		if (getTotalRecord() > 0) {
			int count = getRecordStart();
			int end = getRecordEnd();
			do {
				if (count > end)
					break;
			String rowArray[]=new String[11];
			rowArray[0]=Common.get(rs.getString("enterOrg"));			
			rowArray[1]=Common.get(rs.getString("propertyNo")); 
			rowArray[2]=Common.get(rs.getString("propertyType"));			
			rowArray[3]=Common.get(rs.getString("propertyName")); 
			rowArray[4]=Common.get(rs.getString("propertyUnit")); 
			rowArray[5]=Common.get(rs.getString("material")); 
			rowArray[6]=Common.get(rs.getString("limitYear"));
			rowArray[7]=Common.get(rs.getString("memo"));
			rowArray[8]=Common.get(rs.getString("editID"));
			rowArray[9]=Common.get(rs.getString("editDate"));
			rowArray[10]=Common.get(rs.getString("propertynoCount"));
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


public String getPropertyList(String sql, String preWord) throws Exception{
	Database db = new Database();
	StringBuffer sbHTML = new StringBuffer("");
	try {
		if (sql!=null && sql.trim().length()>0) {
			ResultSet rs = db.querySQL(sql, true);				
			processCurrentPageAttribute(rs);
			if (getTotalRecord() > 0) {
				int count = getRecordStart();
				int end = getRecordEnd();
				do {
					if (count > end)
						break;
					StringBuffer strLink = new StringBuffer(0).append(Common.sqlChar(rs.getString("propertyNo")) ).append( "," ).append(
						Common.sqlChar(rs.getString("propertyName")) ).append( "," ).append(
						Common.sqlChar(rs.getString("propertyType")) ).append( "," ).append(
						Common.sqlChar(rs.getString("propertyUnit")) ).append( "," ).append(
						Common.sqlChar(rs.getString("material")) ).append( "," ).append(
						Common.sqlChar(rs.getString("propertynoCount")) ).append( "," ).append(
						Common.sqlChar(rs.getString("limitYear")));				
					sbHTML.append(" <tr class='listTR' onmouseover=\"this.className='listTRMouseover'\" onmouseout=\"this.className='listTR'\" onClick=\"selectProperty(").append( strLink ).append( ")\" >");
					sbHTML.append(" <td class='listTD' >").append(count).append(".</td> ");
					sbHTML.append(" <td class='listTD' >").append(Common.get(rs.getString("propertyNo"))).append("</td> ");
					sbHTML.append(" <td class='listTD' >").append(Common.get(rs.getString("propertyName"))).append("</td> ");
					sbHTML.append(" <td class='listTD' >").append(Common.get(rs.getString("material"))).append("</td> ");
					sbHTML.append(" </tr> ");
					count++;
				} while (rs.next());
			} else {
				sbHTML.append(" <tr class='listTR' ><td class='listTD' colspan='100' style='color:red'>查無資料，請您重新輸入查詢條件！</td></tr>");
			}
			rs.getStatement().close();
			rs.close();
		}
	} catch (Exception e) {
		e.printStackTrace();
	} finally {
		db.closeAll();
	}
	return sbHTML.toString();
}	


}


