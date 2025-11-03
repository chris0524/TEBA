package sys.ca;

import java.sql.ResultSet;
import java.util.ArrayList;
import util.*;

public class SYSCA013F extends QueryBean{

String enterOrg;
String propertyType;
String propertyNo;
String propertyName;
String propertyUnit;
String material;
String limitYear;
String memo;

String popPropertyNo;
String popPropertyName;
String preWord;
String isQuery;
String isLookup;
String cannotVerify;
String q_enterOrg;
String q_propertyNo;
String q_propertyName;

String isOrganManager;
String isAdminManager;

String jsFunction;

public String getCannotVerify() { return checkGet(cannotVerify); }
public void setCannotVerify(String s) { cannotVerify = checkSet(s); }

public String getJsFunction() { return checkGet(jsFunction); }
public void setJsFunction(String s) { jsFunction = checkSet(s); }
public String getIsOrganManager() { return checkGet(isOrganManager); }
public void setIsOrganManager(String s) { isOrganManager = checkSet(s); }
public String getIsAdminManager() { return checkGet(isAdminManager); }
public void setIsAdminManager(String s) { isAdminManager = checkSet(s); }   
public String getPopPropertyNo(){ return checkGet(popPropertyNo); }
public void setPopPropertyNo(String s){ popPropertyNo=checkSet(s); }
public String getPopPropertyName(){ return checkGet(popPropertyName); }
public void setPopPropertyName(String s){ popPropertyName=checkSet(s); }
public String getPreWord(){ return checkGet(preWord); }
public void setPreWord(String s){ preWord=checkSet(s); }
public String getIsQuery(){ return checkGet(isQuery); }
public void setIsQuery(String s){ isQuery=checkSet(s); }
public String getIsLookup(){ return checkGet(isLookup); }
public void setIsLookup(String s){ isLookup=checkSet(s); }


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


public String getQ_enterOrg(){ return checkGet(q_enterOrg); }
public void setQ_enterOrg(String s){ q_enterOrg=checkSet(s); }
public String getQ_propertyNo(){ return checkGet(q_propertyNo); }
public void setQ_propertyNo(String s){ q_propertyNo=checkSet(s); }
public String getQ_propertyName(){ return checkGet(q_propertyName); }
public void setQ_propertyName(String s){ q_propertyName=checkSet(s); }

String tableName;
public String getTableName() { return checkGet(tableName); }
public void setTableName(String s) { tableName=checkSet(s); }


public boolean checkPublicCode(String s) {
	String[] arrCode = new String[]{"50301","50302","50303","50304","50305","50306","50307","50308","50309"};
	for (int i=0; i<arrCode.length; i++) {
		if (s.equals(arrCode[i])) return true;
	}
	return false;
}

private boolean checkPublicPKCode(String s) {
	Database db = new Database();
	try {
		String sql = " select count(*) from SYSPK_PropertyCode where enterOrg='000000000A' and trim(propertyNo)='" + Common.get(s) + "'";
		ResultSet rs = db.querySQL(sql);
		if (rs.next() && rs.getInt(1)>0) return true;
		rs.close();
	} catch (Exception e) {		
		e.printStackTrace();
		return false;
	} finally {
		db.closeAll();
	}
	return false;
}


//傳回執行insert前之檢查sql
protected String[][] getInsertCheckSQL(){
	String[][] checkSQLArray = new String[2][4];
 	checkSQLArray[0][0]=" select 222 as checkResult from dual ";
	checkSQLArray[0][1]=">";
	checkSQLArray[0][2]="0";
	checkSQLArray[0][3]="機關自用代碼需為503、504、505或6開頭，請檢查！";
	
 	checkSQLArray[1][0]="";
	checkSQLArray[1][1]="";
	checkSQLArray[1][2]="";
	checkSQLArray[1][3]="";	
	
	String s = getPropertyNo().substring(0,1);
	if (s.equals("5")) {
		setTableName("SYSPK_PropertyCode");
		if (getPropertyNo().substring(0,3).equals("503") || getPropertyNo().substring(0,3).equals("504") || getPropertyNo().substring(0,3).equals("505")) {
		 	checkSQLArray[0][0]=" select count(*) as checkResult from SYSPK_PropertyCode where 1=1 " +
		 		" and enterOrg = " + Common.sqlChar(enterOrg) + 	
				" and trim(propertyNo) = " + Common.sqlChar(propertyNo);
			checkSQLArray[0][1]=">";
			checkSQLArray[0][2]="0";
			checkSQLArray[0][3]="該筆資料己重複，請重新輸入！";	
			setQ_propertyNo(getPropertyNo().substring(0,getPropertyNo().length()-1));
		}
		if (checkPublicPKCode(Common.get(propertyNo))) {
		 	checkSQLArray[1][0]=" select 222 as checkResult from dual ";
			checkSQLArray[1][1]=">";
			checkSQLArray[1][2]="0";
			checkSQLArray[1][3]="機關自用代碼" + propertyNo + "與一般動產公用代碼重覆，請檢查！";				
		}		
	} else if (s.equals("6")) {
		setQ_propertyNo(getPropertyNo().substring(0,getPropertyNo().length()-1));
		setTableName("SYSPK_PropertyCode2");
	 	checkSQLArray[0][0]=" select count(*) as checkResult from SYSPK_PropertyCode2 where 1=1 " +
	 		" and enterOrg = " + Common.sqlChar(enterOrg) +
			" and trim(propertyNo) = " + Common.sqlChar(propertyNo) + 
			"";
		checkSQLArray[0][1]=">";
		checkSQLArray[0][2]="0";
		checkSQLArray[0][3]="該筆資料己重複，請重新輸入！";		    	
    }	
	return checkSQLArray;
}


//傳回insert sql
protected String[] getInsertSQL(){
	String[] execSQLArray = new String[1];
	execSQLArray[0]=" insert into " + getTableName() + "(" +
			" enterOrg,"+
			" propertyNo,"+
			" propertyType,"+
			" propertyName,"+
			" propertyUnit,"+
			" material,"+
			" limitYear,"+
			" memo,"+
			" editID,"+
			" editDate,"+
			" editTime"+
		" )Values(" +
			Common.sqlChar(enterOrg) + "," +
			Common.sqlChar(propertyNo) + "," +
			Common.sqlChar("1") + "," +
			Common.sqlChar(propertyName) + "," +
			Common.sqlChar(propertyUnit) + "," +
			Common.sqlChar(material) + "," +
			Common.sqlChar(limitYear) + "," +
			Common.sqlChar(memo) + "," +
			Common.sqlChar(getEditID()) + "," +
			Common.sqlChar(getEditDate()) + "," +
			Common.sqlChar(getEditTime()) + ")" ;
	return execSQLArray;
}


protected String[][] getUpdateCheckSQL(){
	String[][] checkSQLArray = new String[2][4];
 	checkSQLArray[0][0]=" select 222 as checkResult from dual ";
	checkSQLArray[0][1]=">";
	checkSQLArray[0][2]="0";
	checkSQLArray[0][3]="機關自用代碼需為503、504、505或6開頭，請檢查！";
	
 	checkSQLArray[1][0]="";
	checkSQLArray[1][1]="";
	checkSQLArray[1][2]="";
	checkSQLArray[1][3]="";		
	
	String s = getPropertyNo().substring(0,1);
	if (s.equals("5")) {
		setTableName("SYSPK_PropertyCode");
		if (getPropertyNo().substring(0,3).equals("503") || getPropertyNo().substring(0,3).equals("504") || getPropertyNo().substring(0,3).equals("505")) {
			checkSQLArray[0][1]="<";			
		}		
		if (checkPublicPKCode(Common.get(propertyNo))) {
		 	checkSQLArray[1][0]=" select 222 as checkResult from dual ";
			checkSQLArray[1][1]=">";
			checkSQLArray[1][2]="0";
			checkSQLArray[1][3]="機關自用代碼" + propertyNo + "與一般動產公用代碼重覆，請檢查！";				
		}
	} else if (s.equals("6")) {
		setTableName("SYSPK_PropertyCode2");
		checkSQLArray[0][1]="<";		    	
    }	
	return checkSQLArray;	
}


//傳回update sql
protected String[] getUpdateSQL(){
	String s = getPropertyNo().substring(0,1);
	if (s.equals("5")) {
		setTableName("SYSPK_PropertyCode");
	} else if (s.equals("6")) {
		setTableName("SYSPK_PropertyCode2");		    	
    }	
	String[] execSQLArray = new String[1];
	execSQLArray[0]=" update " + getTableName() + " set " +
			" propertyName = " + Common.sqlChar(propertyName) + "," +
			" propertyUnit = " + Common.sqlChar(propertyUnit) + "," +
			" material = " + Common.sqlChar(material) + "," +
			" limitYear = " + Common.sqlChar(limitYear) + "," +
			" memo = " + Common.sqlChar(memo) + "," +
			" editID = " + Common.sqlChar(getEditID()) + "," +
			" editDate = " + Common.sqlChar(getEditDate()) + "," +
			" editTime = " + Common.sqlChar(getEditTime()) +  
		" where enterOrg=" + Common.sqlChar(enterOrg) + " and propertyType='1' and trim(propertyNo) = " + Common.sqlChar(propertyNo);
	return execSQLArray;
}


protected String[][] getDeleteCheckSQL(){
	String[][] checkSQLArray = new String[1][4];
 	checkSQLArray[0][0]=" select 222 as checkResult from dual ";
	checkSQLArray[0][1]=">";
	checkSQLArray[0][2]="0";
	checkSQLArray[0][3]="機關自用代碼需為503、504、505或6開頭，請檢查！";
	
	String s = getPropertyNo().substring(0,1);
	if (s.equals("5")) {
		setTableName("SYSPK_PropertyCode");
		if (getPropertyNo().substring(0,3).equals("503") || getPropertyNo().substring(0,3).equals("504") || getPropertyNo().substring(0,3).equals("505")) {
			checkSQLArray[0][1]="<";			
		}
	} else if (s.equals("6")) {
		setTableName("SYSPK_PropertyCode2");
		checkSQLArray[0][1]="<";		    	
    }
	return checkSQLArray;	
}

//傳回delete sql
protected String[] getDeleteSQL(){
	String[] execSQLArray = new String[1];
	execSQLArray[0]=" delete from " + getTableName() + " where enterOrg=" + Common.sqlChar(enterOrg) +
			" and propertyType='1' and trim(propertyNo) = " + Common.sqlChar(propertyNo);
	return execSQLArray;
}

/**
 * <br>
 * <br>目的：依主鍵查詢單一資料
 * <br>參數：無
 * <br>傳回：傳回本物件
*/

public SYSCA013F  queryOne() throws Exception{
	Database db = new Database();
	SYSCA013F obj = this;
	try {
		if ("".equals(getPrefix())) {
			setState("init");
			
		}else{
			String sql=" select enterOrg, propertyNo, propertyType, propertyName, propertyUnit, material, limitYear, memo, editID, editDate, editTime "+
				" from " + getTableName() + " where enterOrg=" + Common.sqlChar(enterOrg) +
				" and propertyType='1' and trim(propertyNo) = " + Common.sqlChar(propertyNo);
			//System.out.println(sql);
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
			}
			setStateQueryOneSuccess();
		}
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
		if ("".equals(getPrefix())) {
			setState("init");
			
		}else{
			String sql=" select enterOrg, propertyNo, propertyType, propertyName, propertyUnit, material, limitYear, memo, editID, editDate, editTime "+
				" from " + getTableName() + " a where a.propertyType='1' ";
				if (getIsAdminManager().equalsIgnoreCase("Y") || "Y".equals(getIsOrganManager())) {
					if (!"".equals(getQ_enterOrg()))
						sql += " and a.enterOrg="+Common.sqlChar(getQ_enterOrg());
					else
						sql+=" and a.enterOrg = " + Common.sqlChar(enterOrg);
				} else {
					sql+=" and a.enterOrg = " + Common.sqlChar(enterOrg); 
				}
				if ("6".equals(getPrefix())) {
					sql+=" and a.propertyNo like '6%' ";
				} else {
					sql+=" and substr(a.propertyNo,0,3) in (" + getPrefix() + ")";
				}				
				if (!"".equals(getQ_propertyNo()))
					sql+=" and a.propertyNo like '" + getQ_propertyNo() + "%'" ;
				if (!"".equals(getQ_propertyName()))
					sql+=" and a.propertyName like '%" + getQ_propertyName() + "%'" ;
				
			
			ResultSet rs = db.querySQL(sql+" order by propertyNo", true);
			//System.out.println(sql);
			processCurrentPageAttribute(rs);
			
			if (getTotalRecord() > 0) {
				int count = getRecordStart();
				int end = getRecordEnd();
				do {
					if (count > end)
						break;
				String rowArray[]=new String[10];
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
				objList.add(rowArray);
				count++;
				} while (rs.next());
			}
			setStateQueryAllSuccess();
		}
	} catch (Exception e) {
		e.printStackTrace();
	} finally {
		db.closeAll();
	}
	return objList;
}


public final String getPrefix() {
	if (Common.get(preWord).length()>0 && Common.get(preWord).indexOf(",")>0) {
		String[] arrPreWord = preWord.split(",");
		for (int i=0; i<arrPreWord.length; i++) {
			if (arrPreWord[i].substring(0,1).equals("5")) {
				setTableName("SYSPK_PropertyCode");
				if (!"".equals(getQ_propertyNo())) {
					if (!getQ_propertyNo().substring(0,1).equals("5")) setQ_propertyNo("503");
				}
				return "'503','504','505'";
			} else if (arrPreWord[i].substring(0,1).equals("6")) {
				setTableName("SYSPK_PropertyCode2");			
				return "6";
			}
		}
	} else if (Common.get(preWord).length()>0) {
		if (preWord.substring(0,1).equals("5")) {
			setTableName("SYSPK_PropertyCode");
			if (!"".equals(getQ_propertyNo())) {
				if (!getQ_propertyNo().substring(0,1).equals("5")) setQ_propertyNo("503");
			}			
			return "'503','504','505'";
		} else if (preWord.substring(0,1).equals("6")) {
			setTableName("SYSPK_PropertyCode2");
			return "6";
		}
	}
	return "";
}


public String getPropertyList() throws Exception{
	Database db = new Database();
	StringBuffer sbHTML = new StringBuffer("");
	try {
		if ("".equals(getPrefix())) {
			setState("init");
			
		}else{
			String sql=" select a.enterOrg, a.propertyNo, a.propertyType, a.propertyName, a.propertyUnit, a.material, a.limitYear, a.memo, a.editID, a.editDate, a.editTime "+
				" from " + getTableName() + " a where a.propertyType='1' ";
				if (getIsAdminManager().equalsIgnoreCase("Y") || "Y".equals(getIsOrganManager())) {
					if (!"".equals(getQ_enterOrg()))
						sql += " and a.enterOrg="+Common.sqlChar(getQ_enterOrg());
					else
						sql+=" and a.enterOrg = " + Common.sqlChar(enterOrg);
				} else {
					sql+=" and a.enterOrg = " + Common.sqlChar(enterOrg); 
				}
				if ("6".equals(getPrefix())) {
					sql+=" and a.propertyNo like '6%' ";
				} else {
					sql+=" and substr(a.propertyNo,0,3) in (" + getPrefix() + ")";
				}
				if (!"".equals(getQ_propertyNo())) {
					String s = getQ_propertyNo().substring(0,1);
					if (s.equals("5") || s.equals("6"))
						sql+=" and a.propertyNo like '" + getQ_propertyNo() + "%'" ;
					else setQ_propertyNo("");
				}
				if (!"".equals(getQ_propertyName()))
					sql+=" and propertyName like '%" + getQ_propertyName() + "%'" ;
			//System.out.println(sql);	
			ResultSet rs = db.querySQL(sql+" order by propertyNo", true);
			
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
						Common.sqlChar(rs.getString("limitYear")) ).append( "," ).append(
						Common.sqlChar(Common.get(rs.getString("memo")).replaceAll("\n", "")) ).append( "," ).append(								
						Common.sqlChar(rs.getString("editID")) ).append( "," ).append(								
						Common.sqlChar(rs.getString("editDate")));				
					sbHTML.append(" <tr class='listTR' onmouseover=\"this.className='listTRMouseover'\" onmouseout=\"this.className='listTR'\" onClick=\"queryOne(").append( strLink ).append( ");\" >");
					sbHTML.append(" <td class='listTD' >").append(count).append(".</td> ");
					sbHTML.append(" <td class='listTD' ><a href='#'><img src='../../image/selectNo.gif' border='0' alt='選擇財產編號並返回' onClick=\"selectProperty(").append( strLink ).append( ");\"></a></td> ");							
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
			setStateQueryAllSuccess();
		}
	} catch (Exception e) {
		e.printStackTrace();
	} finally {
		db.closeAll();
	}
	return sbHTML.toString();
}

public String getJS() {
	StringBuffer sb = new StringBuffer();
	sb.append("	if (isObj(opener.document.all.item(\"").append(popPropertyNo).append("\"))) {\n" ).append(
		"		opener.document.all.item(\"").append(popPropertyNo).append("\").value=propertyNo;\n" ).append(
		"	}\n" );
	
	sb.append("	if (isObj(opener.document.all.item(\"").append(popPropertyName).append("\"))) {\n" ).append(
		"		opener.document.all.item(\"").append(popPropertyName).append("\").value=propertyName;\n" ).append(
		"	}\n" );
		
	if (getIsLookup().equals("Y")) {
		sb.append("	if (isObj(opener.document.all.item(\"propertyType\"))) {\n" ).append(
		"		opener.document.all.item(\"propertyType\").value=propertyType;\n" ).append(
		"	}\n" ).append(
		"	if (isObj(opener.document.all.item(\"propertyUnit\"))) {\n" ).append(
		"		opener.document.all.item(\"propertyUnit\").value=propertyUnit;\n" ).append(
		"	}\n" ).append(
		"	if (isObj(opener.document.all.item(\"material\"))) {\n" ).append(
		"		opener.document.all.item(\"material\").value=material;\n" ).append(
		"	}\n" ).append(
		"	if (isObj(opener.document.all.item(\"limitYear\"))) {\n" ).append(
		"		opener.document.all.item(\"limitYear\").value=limitYear;\n").append(
		"	}\n" ).append(
		"	if (isObj(opener.document.all.item(\"otherLimitYear\"))) {\n" ).append(
		"		if (parseInt(limitYear)>0) {\n").append(
		"			opener.document.all.item(\"otherLimitYear\").readOnly=true;\n").append(	
		"		}\n").append(		
		"	}\n");		
	}
	return sb.toString();
}


public boolean doImportProcess(String enterOrg, String editID) {
	Database db = new Database();
	try {
		db.setAutoCommit(false);
		String sql = " insert into SYSPK_PropertyCode(enterOrg,propertyNo,propertyType, propertyName, propertyUnit,material,limitYear,memo,editID,editDate,editTime) " +
			" select '"+ enterOrg + "',propertyNo,'1',propertyName,propertyUnit,material,limitYear,memo,'" + editID + "','"+Datetime.getYYYMMDD()+"','"+Datetime.getHHMMSS()+"' from SYSPK_PropertyCode1 " +
			" where substr(propertyno,1,3) in ('503','504','505') and propertyno not in ( " +
			" select propertyNo from SYSPK_PropertyCode where enterOrg in ('000000000A','" + enterOrg + "') ) ";
		db.exeSQL(sql);		
		db.doCommit();	
		System.out.println(sql);
		return true;
	} catch (Exception e) {
		try{
			db.doRollback();
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		e.printStackTrace();
		return false;
	} finally {
		db.closeAll();
	}
}

public boolean doImportNeProcess(String enterOrg, String editID) {
	Database db = new Database();
	try {
		db.setAutoCommit(false);
		//String sql = " insert into SYSPK_PropertyCode(enterOrg,propertyNo,propertyType, propertyName, propertyUnit,material,limitYear,memo,editID,editDate,editTime) " +
			//" select '"+ enterOrg + "',propertyNo,'1',propertyName,propertyUnit,material,limitYear,memo,'" + editID + "','"+Datetime.getYYYMMDD()+"','"+Datetime.getHHMMSS()+"' from SYSPK_PropertyCode1 " +
			//" where substr(propertyno,1,3) in ('503','504','505') and propertyno not in ( " +
			//" select propertyNo from SYSPK_PropertyCode where enterOrg in ('000000000A','" + enterOrg + "') ) ";
		String sql = " insert into SYSPK_PropertyCode2(enterOrg, propertyNo, propertyType, propertyName, propertyUnit, material, limitYear,memo)" +
				" select enterOrg, propertyNo, propertyType, propertyName, propertyUnit, material, limitYear, memo" +
				" from (" +
				" (select '"+ enterOrg + "' enterOrg, '6'||propertyNo propertyNo, propertyType, propertyName, propertyUnit, material, limitYear, memo" +
				" from SYSPK_PropertyCode" +
				" where enterOrg='000000000A'" +
				" and propertyType='1'" +
				" and substr(propertyNo,1,1) in ('3','4','5'))" +
				" union" +
				" (select '"+ enterOrg + "' enterOrg, '6'||propertyNo propertyNo, '1' propertyType, propertyName, propertyUnit, material, limitYear, memo" +
				" from SYSPK_PropertyCode1)" +
				" union" +
				" (select '"+ enterOrg + "' enterOrg, propertyNo, '1' propertyType, propertyName, propertyUnit, material, limitYear, memo" +
				" from SYSPK_PropertyCode3)" +
				" ) b" +
				" where propertyNo not in (" +
				" select propertyNo" +
				" from SYSPK_PropertyCode2" +
				" where enterOrg='"+ enterOrg + "')" +
				"";
		db.exeSQL(sql);		
		db.doCommit();	
		return true;
	} catch (Exception e) {
		try{
			db.doRollback();
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		e.printStackTrace();
		return false;
	} finally {
		db.closeAll();
	}
}

}


