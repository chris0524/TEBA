
/*
*<br>程式目的：財產編號維護
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

import unt.ch.UNTCH_Tools;
import util.Common;
import util.Database;
import util.Datetime;
import util.QueryBean;

public class SYSPK007F_S extends QueryBean{

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
String q_limitYear;
String isPop;
String isOrganManager;
String isAdminManager;
String q_propertyType;
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
public String getQ_propertyType(){ return checkGet(q_propertyType); }
public void setQ_propertyType(String s){ q_propertyType=checkSet(s); }

public String getIsPop() {
	return checkGet(isPop);
}
public void setIsPop(String isPop) {
	this.isPop = checkSet(isPop);
}
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
public String getQ_limitYear(){ return checkGet(q_limitYear); }
public void setQ_limitYear(String s){ q_limitYear=checkSet(s); }



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
		String sql = " select count(*) from SYSPK_PropertyCode where enterOrg='000000000A' and propertyNo = '" + Common.get(s.trim()) + "'";
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
 	checkSQLArray[0][0]=" select 222 as checkResult ";
	checkSQLArray[0][1]=">";
	checkSQLArray[0][2]="0";
	checkSQLArray[0][3]="機關自用代碼需為3、4、5、6開頭，請檢查！";
	
 	checkSQLArray[1][0]="";
	checkSQLArray[1][1]="";
	checkSQLArray[1][2]="";
	checkSQLArray[1][3]="";	
	
	String s = getPropertyNo().substring(0,1);
	if (getPropertyNo().substring(0,3).equals("503") || getPropertyNo().substring(0,3).equals("504") || getPropertyNo().substring(0,3).equals("505")) {
		setTableName("SYSPK_PropertyCode");	
		 	checkSQLArray[0][0]=" select count(*) as checkResult from SYSPK_PropertyCode where 1=1 " +
		 		" and enterOrg = " + Common.sqlChar(enterOrg) + 	
				" and propertyNo = " + Common.sqlChar(propertyNo.trim());
			checkSQLArray[0][1]=">";
			checkSQLArray[0][2]="0";
			checkSQLArray[0][3]="該筆資料己重複，請重新輸入！";	
			setQ_propertyNo(getPropertyNo().substring(0,getPropertyNo().length()-1));
		if (checkPublicPKCode(Common.get(propertyNo))) {
		 	checkSQLArray[1][0]=" select 222 as checkResult ";
			checkSQLArray[1][1]=">";
			checkSQLArray[1][2]="0";
			checkSQLArray[1][3]="機關自用代碼" + propertyNo + "與一般動產公用代碼重覆，請檢查！";				
		}		
	} else {
		setQ_propertyNo(getPropertyNo().substring(0,getPropertyNo().length()-1));
		setTableName("SYSPK_PropertyCode2");
	 	checkSQLArray[0][0]=" select count(*) as checkResult from SYSPK_PropertyCode2 where 1=1 " +
	 		" and enterOrg = " + Common.sqlChar(enterOrg) +
			" and propertyNo = " + Common.sqlChar(propertyNo.trim()) + 
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
	UNTCH_Tools ut = new UNTCH_Tools();
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
			Common.sqlChar(propertyType) + "," +
			Common.sqlChar(propertyName) + "," +
			Common.sqlChar(propertyUnit) + "," +
			Common.sqlChar(material) + "," +
			Common.sqlChar(limitYear) + "," +
			Common.sqlChar(memo) + "," +
			Common.sqlChar(getEditID()) + "," +
			Common.sqlChar(ut._transToCE_Year(getEditDate())) + "," +
			Common.sqlChar(getEditTime()) + ")" ;
	return execSQLArray;
}


protected String[][] getUpdateCheckSQL(){
	String[][] checkSQLArray = new String[2][4];
 	checkSQLArray[0][0]=" select 222 as checkResult ";
	checkSQLArray[0][1]=">";
	checkSQLArray[0][2]="0";
	checkSQLArray[0][3]="機關自用代碼需為3、4、5、6開頭，請檢查！";
	
 	checkSQLArray[1][0]="";
	checkSQLArray[1][1]="";
	checkSQLArray[1][2]="";
	checkSQLArray[1][3]="";		
	String s = getPropertyNo().substring(0,1);
	if (getPropertyNo().substring(0,3).equals("503") || getPropertyNo().substring(0,3).equals("504") || getPropertyNo().substring(0,3).equals("505")) {
		setTableName("SYSPK_PropertyCode");
		
			checkSQLArray[0][1]="<";			
				
		if (checkPublicPKCode(Common.get(propertyNo))) {
		 	checkSQLArray[1][0]=" select 222 as checkResult ";
			checkSQLArray[1][1]=">";
			checkSQLArray[1][2]="0";
			checkSQLArray[1][3]="機關自用代碼" + propertyNo + "與一般動產公用代碼重覆，請檢查！";				
		}
	} else if (s.equals("3")||s.equals("4")||s.equals("5")||s.equals("6")) {
		setTableName("SYSPK_PropertyCode2");
		checkSQLArray[0][1]="<";		    	
    }	
	return checkSQLArray;	
}


//傳回update sql
protected String[] getUpdateSQL(){
	String s = getPropertyNo().substring(0,1);
	if (getPropertyNo().substring(0,3).equals("503") || getPropertyNo().substring(0,3).equals("504") || getPropertyNo().substring(0,3).equals("505")) {
		setTableName("SYSPK_PropertyCode");
	} else if (s.equals("3")||s.equals("4")||s.equals("5")||s.equals("6")) {
		setTableName("SYSPK_PropertyCode2");		    	
    }	
	String[] execSQLArray = new String[1];
	UNTCH_Tools ut = new UNTCH_Tools();
	execSQLArray[0]=" update " + getTableName() + " set " +
			" propertytype = " + Common.sqlChar(propertyType) + "," +
			" propertyName = " + Common.sqlChar(propertyName) + "," +
			" propertyUnit = " + Common.sqlChar(propertyUnit) + "," +
			" material = " + Common.sqlChar(material) + "," +
			" limitYear = " + Common.sqlChar(limitYear) + "," +
			" memo = " + Common.sqlChar(memo) + "," +
			" editID = " + Common.sqlChar(getEditID()) + "," +
			" editDate = " + Common.sqlChar(ut._transToCE_Year(getEditDate())) + "," +
			" editTime = " + Common.sqlChar(getEditTime()) +  
		" where enterOrg=" + Common.sqlChar(enterOrg) + " and propertyNo = " + Common.sqlChar(propertyNo.trim());
	return execSQLArray;
}


protected String[][] getDeleteCheckSQL(){
	String[][] checkSQLArray = new String[1][4];
 	checkSQLArray[0][0]=" select 222 as checkResult ";
	checkSQLArray[0][1]=">";
	checkSQLArray[0][2]="0";
	checkSQLArray[0][3]="機關自用代碼需為3、4、5、6開頭，請檢查！";
	
	String s = getPropertyNo().substring(0,1);
	if (getPropertyNo().substring(0,3).equals("503") || getPropertyNo().substring(0,3).equals("504") || getPropertyNo().substring(0,3).equals("505")) {
		setTableName("SYSPK_PropertyCode");
		
			checkSQLArray[0][1]="<";			
		
	} else if (s.equals("3")||s.equals("4")||s.equals("5")||s.equals("6")) {
		setTableName("SYSPK_PropertyCode2");
		checkSQLArray[0][1]="<";		    	
    }
	return checkSQLArray;	
}

//傳回delete sql
protected String[] getDeleteSQL(){
	String[] execSQLArray = new String[1];
	execSQLArray[0]=" delete from " + getTableName() + " where enterOrg=" + Common.sqlChar(enterOrg) +
			" and propertyNo = " + Common.sqlChar(propertyNo.trim());
	return execSQLArray;
}

/**
 * <br>
 * <br>目的：依主鍵查詢單一資料
 * <br>參數：無
 * <br>傳回：傳回本物件
*/

public SYSPK007F_S  queryOne() throws Exception{
	Database db = new Database();
	SYSPK007F_S obj = this;
	try {
		if ("".equals(getPrefix())) {
			setState("init");
			setErrorMsg("對不起，操作錯誤或系統異常！\n請重新登入後，再操作！\n若問題持續，請洽系統管理者或承辦人員！\n");
		}else{
			String sql=" select enterOrg, propertyNo, propertyType, propertyName, propertyUnit, material, limitYear, memo, editID, editDate, editTime "+
				" from " + getTableName() + " where enterOrg=" + Common.sqlChar(enterOrg) +
				" and propertyNo = " + Common.sqlChar(propertyNo.trim());
			//System.out.println(sql);
			UNTCH_Tools ut = new UNTCH_Tools();
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
				obj.setEditDate(ut._transToROC_Year(rs.getString("editDate")));
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
			setErrorMsg("對不起，操作錯誤或系統異常！\n請重新登入後，再操作！\n若問題持續，請洽系統管理者或承辦人員！\n");
		}else{
			String sql=" select enterOrg, propertyNo, propertyType, propertyName, propertyUnit, material, limitYear, memo, editID, editDate, editTime "+
				" from " + getTableName() + " a where 1 = 1 ";
				if (getIsAdminManager().equalsIgnoreCase("Y") || "Y".equals(getIsOrganManager())) {
					if (!"".equals(getQ_enterOrg()))
						sql += " and a.enterOrg="+Common.sqlChar(getQ_enterOrg());
					else
						sql+=" and a.enterOrg = " + Common.sqlChar(enterOrg);
				} else {
					sql+=" and a.enterOrg = " + Common.sqlChar(enterOrg); 
				}
//				if ("6".equals(getPrefix())) {
//					sql+=" and a.propertyNo like '6%' ";
//				} else {
//					sql+=" and substring(a.propertyNo,1,3) in (" + getPrefix() + ")";
//				}				
				if (!"".equals(getQ_propertyNo()))
					sql+=" and a.propertyNo like '" + getQ_propertyNo() + "%'" ;
				if (!"".equals(getQ_propertyName()))
					sql+=" and a.propertyName like '%" + getQ_propertyName() + "%'" ;
				if (!"".equals(getQ_limitYear()))
					sql+=" and a.limitYear = '" + getQ_limitYear() + "'" ;
					
			UNTCH_Tools ut = new UNTCH_Tools();
			ResultSet rs = db.querySQL(sql+" order by propertyNo", true);
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
				rowArray[9]=ut._transToROC_Year(Common.get(rs.getString("editDate")));
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
	return getPropertyList(null, null);
}

public String getPropertyList(Boolean forceHasSelectIcon) throws Exception{
	return getPropertyList(forceHasSelectIcon, null);
}

/**
 * <br/> isPop : N 的情形表呼叫此方法的為  非彈出視窗 (例如維護作業
 * <br/>         點擊tr的動作為  QueryOne  & 列表區 沒有"選擇"欄位
 * <br/> isPop : 為Y or 未設定情形   泛指彈出視窗
 * <br/>         點擊tr的動作為 Selected  & 列表區 有 "選擇" 欄位
 * <br/> forceHasSelectIcon  true: 強制要有 "選擇"欄位   or false :強制不要有"選擇"欄位"
 * @param forceHasSelectIcon : 要指定強制要不要有"選擇" 欄位  傳遞參數務必是要  new Boolean("true")  or  new Boolean("false") 
 * @param fromAdd : 是否從增加基本資料進入
 * @return
 * @throws Exception
 */
public String getPropertyList(Boolean forceHasSelectIcon, String fromAdd) throws Exception{
	Database db = new Database();
	StringBuffer sbHTML = new StringBuffer("");
	try {
		if ("".equals(getPrefix())) {
			setState("init");
			setErrorMsg("對不起，操作錯誤或系統異常！\\n請重新登入後，再操作！\\n若問題持續，請洽系統管理者或承辦人員！\\n');window.close();('");
		}else{
			String sql=" select a.enterOrg, a.propertyNo, a.propertyType, a.propertyName, a.propertyUnit, a.material, a.limitYear, a.memo, a.editID, a.editDate, a.editTime "+
				" from " + getTableName() + " a where 1 = 1 ";
				if (getIsAdminManager().equalsIgnoreCase("Y") || "Y".equals(getIsOrganManager())) {
					if (!"".equals(getQ_enterOrg()))
						sql += " and a.enterOrg="+Common.sqlChar(getQ_enterOrg());
					else
						sql+=" and a.enterOrg = " + Common.sqlChar(enterOrg);
				} else {
					sql+=" and a.enterOrg = " + Common.sqlChar(enterOrg); 
				}
//				if ("6".equals(getPrefix())) {
//					sql+=" and a.propertyNo like '6%' ";
//				} else {
//					sql+=" and substring(a.propertyNo,1,3) in (" + getPrefix() + ")";
//				}
				if (!"".equals(getQ_propertyNo())) {
					String s = getQ_propertyNo().substring(0,1);
					if (!s.equals(""))
						sql+=" and a.propertyNo like '" + getQ_propertyNo() + "%'" ;
					else setQ_propertyNo("");
				}
				if (!"".equals(getQ_propertyName()))
					sql+=" and a.propertyName like '%" + getQ_propertyName() + "%'" ;
				if (!"".equals(getQ_limitYear()))
					sql+=" and a.limitYear = '" + getQ_limitYear() + "'" ;
				if ("Y".equals(fromAdd)) {
					if ("A42010000G".equals(getQ_enterOrg()) ) {
						sql += " and len(a.propertyNo) = 11 ";
						sql += " and a.propertyNo != '50103010004' ";
					}
				}
//			System.out.println(sql);	
			ResultSet rs = db.querySQL(sql+" order by propertyNo", true);
			UNTCH_Tools ut = new UNTCH_Tools();
			processCurrentPageAttribute(rs);
			if (getTotalRecord() > 0) {
				int count = getRecordStart();
				int end = getRecordEnd();
				do {
					if (count > end)
						break;
					StringBuffer strLink = new StringBuffer(0).append(Common.sqlChar(rs.getString("propertyNo")) ).append( "," ).append(
						Common.sqlChar(Common.checkGet(rs.getString("propertyName"))) ).append( "," ).append(
						Common.sqlChar(Common.checkGet(rs.getString("propertyType"))) ).append( "," ).append(
						Common.sqlChar(Common.checkGet(rs.getString("propertyUnit"))) ).append( "," ).append(
						Common.sqlChar(Common.checkGet(rs.getString("material"))) ).append( "," ).append(
						Common.sqlChar(Common.checkGet(rs.getString("limitYear"))) ).append( "," ).append(
						Common.sqlChar(Common.checkGet(rs.getString("memo")).replaceAll("\n", ""))).append( "," ).append(								
						Common.sqlChar(Common.checkGet(rs.getString("editID"))) ).append( "," ).append(								
						Common.sqlChar(ut._transToROC_Year(Common.checkGet(rs.getString("editDate")))));	
					if("N".equals(getIsPop())){ // 非彈出視窗 點了是QueryOne
						sbHTML.append(" <tr class='listTR' onmouseover=\"this.className='listTRMouseover'\" onmouseout=\"this.className='listTR'\" onClick=\"queryOne(").append( strLink ).append( ");\" >");
					}else{ // 彈出視窗點了就是選擇
						sbHTML.append(" <tr class='listTR' onmouseover=\"this.className='listTRMouseover'\" onmouseout=\"this.className='listTR'\" onClick=\"selectProperty(").append( strLink ).append( ");\" >");
//						sbHTML.append(" <tr class='listTR' onmouseover=\"this.className='listTRMouseover'\" onmouseout=\"this.className='listTR'\" >");
					}
					
					sbHTML.append(" <td class='listTD' >").append(count).append(".</td> ");
					if ((!"N".equals(this.getIsPop()) && forceHasSelectIcon == null) || 
						 ("N".equals(this.getIsPop()) && forceHasSelectIcon != null && forceHasSelectIcon)) {// 彈出視窗 就有 "選擇" 圖案
						sbHTML.append(" <td class='listTD' ><a href='#'><img src='../../image/selectNo.gif' border='0' alt='選擇財產編號並返回' onClick=\"selectProperty(").append( strLink ).append( ");\"></a></td> ");
					}
					sbHTML.append(" <td class='listTD' ><a href='#' onClick=\"selectProperty(").append( strLink ).append( ");\">").append(Common.checkGet(rs.getString("propertyNo"))).append("</a></td> ");
					sbHTML.append(" <td class='listTD' ><a href='#' onClick=\"selectProperty(").append( strLink ).append( ");\">").append(Common.checkGet(rs.getString("propertyName"))).append("</a></td> ");
					sbHTML.append(" <td class='listTD' ><a href='#' onClick=\"selectProperty(").append( strLink ).append( ");\">").append(Common.checkGet(rs.getString("material"))).append("</a></td> ");
					sbHTML.append(" <td class='listTD' ><a href='#' onClick=\"selectProperty(").append( strLink ).append( ");\">").append(Common.checkGet(rs.getString("limitYear"))).append("</a></td> ");
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
		if (forceHasSelectIcon != null) {
			forceHasSelectIcon = null;
		}
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
	sb.append("	if (isObj(opener.document.all.item(\"propertyName1\"))) {\n" ).append(
	"		opener.document.all.item(\"propertyName1\").value=propertyName;\n" ).append(
	"	}\n" );
	
	if (getIsLookup().equals("Y")) {
		sb.append("	if (isObj(opener.document.all.item(\"propertyType\"))) {\n" ).append(
		"		opener.document.all.item(\"propertyType\").value=propertyType;\n" ).append(
		"	}\n" ).append(
		"	if (isObj(opener.document.all.item(\"propertyUnit\"))) {\n" ).append(
		"		opener.document.all.item(\"propertyUnit\").value=propertyUnit;\n" ).append(
		"	}\n" ).append(
		"	if (isObj(opener.document.all.item(\"otherPropertyUnit\"))) {\n" ).append(
		"		opener.document.all.item(\"otherPropertyUnit\").value=propertyUnit;\n" ).append(
		"	}\n" ).append(
		"	if (isObj(opener.document.all.item(\"material\"))) {\n" ).append(
		"		opener.document.all.item(\"material\").value=material;\n" ).append(
		"	}\n" ).append(
		"	if (isObj(opener.document.all.item(\"otherMaterial\"))) {\n" ).append(
		"		opener.document.all.item(\"otherMaterial\").value=material;\n" ).append(
		"	}\n" );
		
		sb.append("if (isObj(opener.document.all.item('originalLimitYear'))) {\n")
		  .append("  opener.document.all.item('originalLimitYear').value=limitYear;\n")
		  .append("	 if (isObj(opener.document.all.item('limitYear'))) opener.document.all.item('limitYear').value=limitYear * 12;\n")
		  .append("} else {\n")
		  .append("	 if (isObj(opener.document.all.item('limitYear'))) opener.document.all.item('limitYear').value=limitYear;\n")
		  .append("  if (isObj(opener.document.all.item('otherLimitYear'))) opener.document.all.item('otherLimitYear').value=(limitYear*12);\n")
		  .append("  if (isObj(opener.document.all.item('otherLimitYear'))){\n")
		  .append("    if (parseInt(limitYear)>0) {\n")
		  .append("      opener.document.all.item('limitYear').readOnly=true;\n")
		  .append("      opener.document.all.item('otherLimitYear').readOnly=true;\n")
		  .append("    } else {\n")
		  .append("      opener.document.all.item('limitYear').value='';\n")
		  .append("      opener.document.all.item('otherLimitYear').readOnly=true;\n")
		  .append("    }\n")
		  .append("  }\n")
		  .append("}\n");
		
	}
	return sb.toString();
}


public boolean doImportProcess(String enterOrg, String editID) {
	Database db = new Database();
	try {
		db.setAutoCommit(false);
		String sql = " insert into SYSPK_PropertyCode(enterOrg,propertyNo,propertyType, propertyName, propertyUnit,material,limitYear,memo,editID,editDate,editTime) " +
			" select '"+ enterOrg + "',propertyNo,'1',propertyName,propertyUnit,material,limitYear,memo,'" + editID + "','"+Datetime.getYYYYMMDD()+"','"+Datetime.getHHMMSS()+"' from SYSPK_PropertyCode1 " +
			" where substring(propertyno,1,3) in ('503','504','505') and propertyno not in ( " +
			" select propertyNo from SYSPK_PropertyCode where enterOrg in ('000000000A','" + enterOrg + "') ) ";
		db.exeSQL(sql);		
		db.doCommit();	
//		System.out.println(sql);
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
				" (select '"+ enterOrg + "' enterOrg, '6' + propertyNo propertyNo, propertyType, propertyName, propertyUnit, material, limitYear, memo" +
				" from SYSPK_PropertyCode" +
				" where enterOrg='000000000A'" +
				" and propertyType='1'" +
				" and substring(propertyNo,1,1) in ('3','4','5'))" +
				" union" +
				" (select '"+ enterOrg + "' enterOrg, '6' + propertyNo propertyNo, '1' propertyType, propertyName, propertyUnit, material, limitYear, memo" +
				" from SYSPK_PropertyCode1 where 1=1)" +
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


