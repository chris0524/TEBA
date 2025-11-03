/*
*<br>程式目的：網站訊息管理作業
*<br>程式代號：syswm001f
*<br>程式日期：0941019
*<br>程式作者：clive.chang
*<br>--------------------------------------------------------
*<br>修改作者　　修改日期　　　修改目的
*<br>--------------------------------------------------------
*/

package sys.wm;

import java.io.File;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import unt.ch.UNTCH_Tools;
import util.Common;
import util.Database;
import util.Datetime;
import util.QueryBean;
import util.User;


public class SYSWM001F extends QueryBean{

String isQuery;
String newsCat;
String newsID;
String newsSubject;
String newsContent;
String newsDateS;
String newsDateE;
String isHTML;
String itemPicture1;
String itemPicture2;
String itemPicture3;
String itemPicture4;
String itemPicture5;
String itemPicture6;
String itemPicture7;
String itemPicture8;
String itemPicture9;
String itemPicture10;

String q_newsCat;
String q_newsID;
String q_newsIDS;
String q_newsIDE;
String q_newsSubject;
String q_newsContent;
String q_newsDateS;
String q_newsDateE;
String q_newsDate;


public String getIsQuery(){ return checkGet(isQuery); }
public void setIsQuery(String s){ isQuery=checkSet(s); }
public String getNewsCat(){ return checkGet(newsCat); }
public void setNewsCat(String s){ newsCat=checkSet(s); }
public String getNewsID(){ return checkGet(newsID); }
public void setNewsID(String s){ newsID=checkSet(s); }
public String getNewsSubject(){ return checkGet(newsSubject); }
public void setNewsSubject(String s){ newsSubject=checkSet(s); }
public String getNewsContent(){ return checkGet(newsContent); }
public void setNewsContent(String s){ newsContent=checkSet(s); }
public String getNewsDateS(){ return checkGet(newsDateS); }
public void setNewsDateS(String s){ newsDateS=checkSet(s); }
public String getNewsDateE(){ return checkGet(newsDateE); }
public void setNewsDateE(String s){ newsDateE=checkSet(s); }
public String getIsHTML(){ if ("Y".equals(checkGet(isHTML))) return "checked"; else return ""; }
public void setIsHTML(String s){ isHTML=checkSet(s); }
public String getItemPicture1(){ return checkGet(itemPicture1); }
public void setItemPicture1(String s){ itemPicture1=checkSet(s); }
public String getItemPicture2(){ return checkGet(itemPicture2); }
public void setItemPicture2(String s){ itemPicture2=checkSet(s); }
public String getItemPicture3(){ return checkGet(itemPicture3); }
public void setItemPicture3(String s){ itemPicture3=checkSet(s); }
public String getItemPicture4(){ return checkGet(itemPicture4); }
public void setItemPicture4(String s){ itemPicture4=checkSet(s); }
public String getItemPicture5(){ return checkGet(itemPicture5); }
public void setItemPicture5(String s){ itemPicture5=checkSet(s); }

public String getItemPicture6(){ return checkGet(itemPicture6); }
public void setItemPicture6(String s){ itemPicture6=checkSet(s); }
public String getItemPicture7(){ return checkGet(itemPicture7); }
public void setItemPicture7(String s){ itemPicture7=checkSet(s); }
public String getItemPicture8(){ return checkGet(itemPicture8); }
public void setItemPicture8(String s){ itemPicture8=checkSet(s); }
public String getItemPicture9(){ return checkGet(itemPicture9); }
public void setItemPicture9(String s){ itemPicture9=checkSet(s); }
public String getItemPicture10(){ return checkGet(itemPicture10); }
public void setItemPicture10(String s){ itemPicture10=checkSet(s); }

String filestoreLocation;
public String getFilestoreLocation(){ return checkGet(filestoreLocation); }
public void setFilestoreLocation(String s){ filestoreLocation=checkSet(s); }

public String getQ_newsCat(){ return checkGet(q_newsCat); }
public void setQ_newsCat(String s){ q_newsCat=checkSet(s); }
public String getQ_newsIDS(){ return checkGet(q_newsIDS); }
public void setQ_newsIDS(String s){ q_newsIDS=checkSet(s); }
public String getQ_newsIDE(){ return checkGet(q_newsIDE); }
public void setQ_newsIDE(String s){ q_newsIDE=checkSet(s); }
public String getQ_newsSubject(){ return checkGet(q_newsSubject); }
public void setQ_newsSubject(String s){ q_newsSubject=checkSet(s); }
public String getQ_newsContent(){ return checkGet(q_newsContent); }
public void setQ_newsContent(String s){ q_newsContent=checkSet(s); }
public String getQ_newsDateS(){ return checkGet(q_newsDateS); }
public void setQ_newsDateS(String s){ q_newsDateS=checkSet(s); }
public String getQ_newsDateE(){ return checkGet(q_newsDateE); }
public void setQ_newsDateE(String s){ q_newsDateE=checkSet(s); }
public String getQ_newsDate(){ return checkGet(q_newsDate); }
public void setQ_newsDate(String s){ q_newsDate=checkSet(s); }

String signName;
String fakedivision;
String usearea;
String holddates;
String holddatee;
public String getSignName(){ return checkGet(signName); }
public void setSignName(String s){ signName=checkSet(s); }
public String getFakedivision(){ return checkGet(fakedivision); }
public void setFignName(String s){ fakedivision=checkSet(s); }
public String getUsearea(){ return checkGet(usearea); }
public void setUsearea(String s){ usearea=checkSet(s); }
public String getHolddates(){ return checkGet(holddates); }
public void setHolddates(String s){ holddates=checkSet(s); }
public String getHolddatee(){ return checkGet(holddatee); }
public void setHolddatee(String s){ holddatee=checkSet(s); }

String itemPictureHTML;
public void setItemPictureHTML(String s) { itemPictureHTML = s; }
public String getItemPictureHTML(){
	if (itemPictureHTML==null) return "";
	else return itemPictureHTML.trim();
}

private String showMessageYN;
public String getShowMessageYN() {return checkGet(showMessageYN);}
public void setShowMessageYN(String showMessageYN) {this.showMessageYN = checkSet(showMessageYN);}

private String organID;
private String organName;
private String unitID;
private String unitName;
private String editIDName;
public String getOrganID() {return checkGet(organID);}
public void setOrganID(String organID) {this.organID = checkSet(organID);}
public String getUnitID() {return checkGet(unitID);}
public void setUnitID(String unitID) {this.unitID = checkSet(unitID);}
public String getOrganName() {return checkGet(organName);}
public void setOrganName(String organName) {this.organName = checkSet(organName);}
public String getUnitName() {return checkGet(unitName);}
public void setUnitName(String unitName) {this.unitName = checkSet(unitName);}
public String getEditIDName() {return checkGet(editIDName);}
public void setEditIDName(String editIDName) {this.editIDName = checkSet(editIDName);}

private String enterOrg;
public String getEnterOrg() {return checkGet(enterOrg);}
public void setEnterOrg(String enterOrg) {this.enterOrg = checkSet(enterOrg);}


final public String[] arrFileNames = new String[]{"itemPicture1","itemPicture2","itemPicture3","itemPicture4","itemPicture5","itemPicture6","itemPicture7","itemPicture8","itemPicture9","itemPicture10"};

//傳回執行insert前之檢查sql
protected String[][] getInsertCheckSQL(){
	//取得訊息編號
	Database db = new Database();
	ResultSet rs;	
	String sql="select max(substring(newsID,9,2))+1 as newsID from SYSWM_NEWS " +
		" where substring(newsID,1,8) = " + Common.sqlChar(Datetime.getYYYYMMDD()) + 
		"";		
	try {		
		rs = db.querySQL(sql);
		if (rs.next()){
			if (rs.getString("newsID")==null)
				setNewsID(Datetime.getYYYYMMDD()+"01");
			else
				setNewsID(Datetime.getYYYYMMDD()+Common.formatFrontZero(rs.getString("newsID"), 2));
		} else {
			setNewsID(Datetime.getYYYYMMDD()+"01");
		}
	} catch (Exception e) {
		e.printStackTrace();
	} finally {
		db.closeAll();
	}	
	String[][] checkSQLArray = new String[2][4];
 	checkSQLArray[0][0]=" select count(*) as checkResult from SYSWM_NEWS where 1=1 " + 
		" and newsID = " + Common.sqlChar(newsID) + 
		"";
	checkSQLArray[0][1]=">";
	checkSQLArray[0][2]="0";
	checkSQLArray[0][3]="該筆資料己重複，請重新輸入！";
	
	checkSQLArray[1][0]=" select count(*) as checkResult from SYSWM_NEWS where 1=1 " + 
				" and showMessageYN is not null" +
				" and newsID != " + Common.sqlChar(this.getNewsID()) +
				" and 'Y' = " + Common.sqlChar(this.getShowMessageYN());	
	checkSQLArray[1][1]=">";
	checkSQLArray[1][2]="0";
	checkSQLArray[1][3]="己有其他資料設定在登入時顯示訊息，請重新輸入！";

	return checkSQLArray;
}


//傳回insert sql
protected String[] getInsertSQL(){
	UNTCH_Tools ut = new UNTCH_Tools();
	String[] execSQLArray = new String[2];	
	execSQLArray[0]=" insert into SYSWM_NEWS(" +
			" newscat,"+
			" newsid,"+
			" newssubject,"+
			" newscontent,"+
			" newsdates,"+
			" newsdatee,"+
			" ishtml,"+
			" itempicture1,"+
			" itempicture2,"+
			" itempicture3,"+
			" itempicture4,"+
			" itempicture5,"+		
			" itempicture6,"+
			" itempicture7,"+
			" itempicture8,"+
			" itempicture9,"+
			" itempicture10,"+					
			" editid,"+
			" editdate,"+
			" edittime,"+
			" showMessageYN,"+
			" organid,"+
			" unitid"+
		" )Values(" +
			Common.sqlChar(newsCat) + "," +
			Common.sqlChar(newsID) + "," +
			Common.sqlChar(newsSubject) + "," +
			Common.sqlChar(newsContent) + "," +
			Common.sqlChar(ut._transToCE_Year(newsDateS)) + "," +
			Common.sqlChar(ut._transToCE_Year(newsDateE)) + "," +
			Common.sqlChar(isHTML) + "," +
			Common.sqlChar(itemPicture1) + "," +
			Common.sqlChar(itemPicture2) + "," +
			Common.sqlChar(itemPicture3) + "," +
			Common.sqlChar(itemPicture4) + "," +
			Common.sqlChar(itemPicture5) + "," +			
			Common.sqlChar(itemPicture6) + "," +
			Common.sqlChar(itemPicture7) + "," +
			Common.sqlChar(itemPicture8) + "," +
			Common.sqlChar(itemPicture9) + "," +
			Common.sqlChar(itemPicture10) + "," +			
			Common.sqlChar(getEditID()) + "," +
			Common.sqlChar(ut._transToCE_Year(getEditDate())) + "," +
			Common.sqlChar(getEditTime()) + "," +
			("".equals(getShowMessageYN())?"null":Common.sqlChar("Y")) + "," + 
			Common.sqlChar(getOrganID()) + "," +
			Common.sqlChar(getUnitID()) + ")";
	
	execSQLArray[1] = "update SYSWM_NEWS set" +
			" showMessageYN = null" +
			" where showMessageYN is not null" +
			" and newsdatee < " + Common.sqlChar(Datetime.getYYYYMMDD());
	
	return execSQLArray;
}

protected String[][] getUpdateCheckSQL(){
	String[][] checkSQLArray = new String[1][4]; 	
	checkSQLArray[0][0]=" select count(*) as checkResult from SYSWM_NEWS where 1=1 " + 
							" and showMessageYN is not null" +
							" and newsID != " + Common.sqlChar(this.getNewsID()) +
							" and 'Y' = " + Common.sqlChar(this.getShowMessageYN());

	checkSQLArray[0][1]=">";
	checkSQLArray[0][2]="0";
	checkSQLArray[0][3]="己有其他資料設定在登入時顯示訊息，請重新輸入！";
	
	return checkSQLArray;
}

//傳回update sql
protected String[] getUpdateSQL(){
	UNTCH_Tools ut = new UNTCH_Tools();
	String[] execSQLArray = new String[2];
	execSQLArray[0]=" update SYSWM_NEWS set " +
			" newscat = " + Common.sqlChar(newsCat) + "," +
			" newssubject = " + Common.sqlChar(newsSubject) + "," +
			" newscontent = " + Common.sqlChar(newsContent) + "," +
			" newsdates = " + Common.sqlChar(ut._transToCE_Year(newsDateS)) + "," +
			" newsdatee = " + Common.sqlChar(ut._transToCE_Year(newsDateE)) + "," +
			" ishtml = " + Common.sqlChar(isHTML) + "," +
			" itempicture1 = " + Common.sqlChar(itemPicture1) + "," +
			" itempicture2 = " + Common.sqlChar(itemPicture2) + "," +
			" itempicture3 = " + Common.sqlChar(itemPicture3) + "," +
			" itempicture4 = " + Common.sqlChar(itemPicture4) + "," +
			" itempicture5 = " + Common.sqlChar(itemPicture5) + "," +
			" itempicture6 = " + Common.sqlChar(itemPicture6) + "," +
			" itempicture7 = " + Common.sqlChar(itemPicture7) + "," +
			" itempicture8 = " + Common.sqlChar(itemPicture8) + "," +
			" itempicture9 = " + Common.sqlChar(itemPicture9) + "," +
			" itempicture10 = " + Common.sqlChar(itemPicture10) + "," +						
			" editid = " + Common.sqlChar(getEditID()) + "," +
			" editdate = " + Common.sqlChar(ut._transToCE_Year(getEditDate())) + "," +
			" edittime = " + Common.sqlChar(getEditTime()) + "," +
			" showMessageYN = " + ("".equals(getShowMessageYN())?"null":Common.sqlChar("Y")) + "," +
			" organID = " + Common.sqlChar(getOrganID()) + "," +
			" unitID = " + Common.sqlChar(getUnitID()) + 
		" where 1=1 " + 
			" and newsid = " + Common.sqlChar(newsID) +
			"";
	
	execSQLArray[1] = "update SYSWM_NEWS set" +
			" showMessageYN = null" +
			" where showMessageYN is not null" +
			" and newsdatee < " + Common.sqlChar(Datetime.getYYYYMMDD());
	
	return execSQLArray;
}


//傳回delete sql
protected String[] getDeleteSQL(){
	Database db = new Database();
	try {
		ResultSet rs = db.querySQL(" select itempicture1, itempicture2, itempicture3, itempicture4, itempicture5, itempicture6, itempicture7, itempicture8, itempicture9, itempicture10,  from SYSWM_NEWS where newsid = " + Common.sqlChar(newsID));
		if (rs.next()) {
			for (int i=0; i<arrFileNames.length; i++) {
				doDeleteFile(rs.getString(arrFileNames[i]));
			}			
		}
		rs.close();
	} catch (Exception e) {
		e.printStackTrace();
	} finally {
		db.closeAll();
	}
		
	String[] execSQLArray = new String[1];
	execSQLArray[0]=" delete SYSWM_NEWS where newsid = " + Common.sqlChar(newsID);
	/**
	//刪除檔案
	String[] arrFileName=null;	
	if (!"".equals(Common.get(itemPicture1))) {
		arrFileName=itemPicture1.split(":;:");
		if (arrFileName.length>1) Common.RemoveDirectory(new File(filestoreLocation+File.separator+arrFileName[0]));
	}
	if (!"".equals(Common.get(itemPicture2))) {
		arrFileName=itemPicture2.split(":;:");
		if (arrFileName.length>1) Common.RemoveDirectory(new File(filestoreLocation+File.separator+arrFileName[0]));
	}	
	if (!"".equals(Common.get(itemPicture3))) {
		arrFileName=itemPicture3.split(":;:");
		if (arrFileName.length>1) Common.RemoveDirectory(new File(filestoreLocation+File.separator+arrFileName[0]));
	}	
	if (!"".equals(Common.get(itemPicture4))) {
		arrFileName=itemPicture4.split(":;:");
		if (arrFileName.length>1) Common.RemoveDirectory(new File(filestoreLocation+File.separator+arrFileName[0]));
	}	
	if (!"".equals(Common.get(itemPicture5))) {
		arrFileName=itemPicture5.split(":;:");
		if (arrFileName.length>1) Common.RemoveDirectory(new File(filestoreLocation+File.separator+arrFileName[0]));
	}	
	**/
	return execSQLArray;
}

public void deleteAll() {
	UNTCH_Tools ut = new UNTCH_Tools();
	Database db = new Database();
	String strSQL = "";
	try {
		strSQL = " select itempicture1, itempicture2, itempicture3, itempicture4, itempicture5, itempicture6, itempicture7, itempicture8, itempicture9, itempicture10 from SYSWM_NEWS where 1=1 ";
		if (!"".equals(getQ_newsCat()))
			strSQL += " and newscat = " + Common.sqlChar(getQ_newsCat()) ;
		if (!"".equals(getQ_newsIDS()) && !"".equals(getQ_newsIDE()))				
			strSQL += " and newsid between " + Common.sqlChar(getQ_newsIDS()) + " and " + Common.sqlChar(getQ_newsIDE());
		else if (!"".equals(getQ_newsIDS()))
			strSQL += " and newsid = " + Common.sqlChar(getQ_newsIDS());
		if (!"".equals(getQ_newsSubject()))
			strSQL += " and newssubject like '%" + getQ_newsSubject() + "%'" ;
		if (!"".equals(getQ_newsContent()))
			strSQL += " and newscontent like '%" + Common.sqlChar(getQ_newsContent()) + "%'";
		if (!"".equals(getQ_newsDateS()) && !"".equals(getQ_newsDateE()))
			strSQL += " and newsdates >= " + Common.sqlChar(ut._transToCE_Year(getQ_newsDateS())) + " and newsdatee<=" + Common.sqlChar(ut._transToCE_Year(getQ_newsDateE()));
		else if (!"".equals(getQ_newsDateS()))
			strSQL += " and newsdates = " + Common.sqlChar(ut._transToCE_Year(getQ_newsDateS()));
		else if (!"".equals(getQ_newsDateE()))
			strSQL += " and newdatee=" + Common.sqlChar(ut._transToCE_Year(getQ_newsDateE()));		
		ResultSet rs = db.querySQL(strSQL);
						
		while (rs.next()) {
			for (int i=0; i<arrFileNames.length; i++) {
				doDeleteFile(rs.getString(arrFileNames[i]));
			}			
			//刪除檔案
			/**
			String[] arrFileName=null;	
			if (!"".equals(Common.get(rs.getString("itemPicture1")))) {
				arrFileName=rs.getString("itemPicture1").split(":;:");
				if (arrFileName.length>1) Common.RemoveDirectory(new File(filestoreLocation+File.separator+arrFileName[0]));
			}
			if (!"".equals(Common.get(rs.getString("itemPicture2")))) {
				arrFileName=rs.getString("itemPicture2").split(":;:");
				if (arrFileName.length>1) Common.RemoveDirectory(new File(filestoreLocation+File.separator+arrFileName[0]));
			}	
			if (!"".equals(Common.get(rs.getString("itemPicture3")))) {
				arrFileName=rs.getString("itemPicture3").split(":;:");
				if (arrFileName.length>1) Common.RemoveDirectory(new File(filestoreLocation+File.separator+arrFileName[0]));
			}	
			if (!"".equals(Common.get(rs.getString("itemPicture4")))) {
				arrFileName=rs.getString("itemPicture4").split(":;:");
				if (arrFileName.length>1) Common.RemoveDirectory(new File(filestoreLocation+File.separator+arrFileName[0]));
			}	
			if (!"".equals(Common.get(rs.getString("itemPicture5")))) {
				arrFileName=rs.getString("itemPicture5").split(":;:");
				if (arrFileName.length>1) Common.RemoveDirectory(new File(filestoreLocation+File.separator+arrFileName[0]));
			}
			**/
		}
		rs.close();
		
		strSQL += " delete SYSWM_NEWS where 1=1 ";
		if (!"".equals(getQ_newsCat()))
			strSQL += " and newscat = " + Common.sqlChar(getQ_newsCat()) ;
		if (!"".equals(getQ_newsIDS()) && !"".equals(getQ_newsIDE()))				
			strSQL += " and newsid between " + Common.sqlChar(getQ_newsIDS()) + " and " + Common.sqlChar(getQ_newsIDE());
		else if (!"".equals(getQ_newsIDS()))
			strSQL += " and newsid = " + Common.sqlChar(getQ_newsIDS());
		if (!"".equals(getQ_newsSubject()))
			strSQL += " and newssubject like '%" + getQ_newsSubject() + "%'" ;
		if (!"".equals(getQ_newsContent()))
			strSQL += " and newscontent like '%" + Common.sqlChar(getQ_newsContent()) + "%'";
		if (!"".equals(getQ_newsDateS()) && !"".equals(getQ_newsDateE()))
			strSQL += " and newsdatee >= " + Common.sqlChar(ut._transToCE_Year(getQ_newsDateS())) + " and newsdatee<=" + Common.sqlChar(ut._transToCE_Year(getQ_newsDateE()));
		else if (!"".equals(getQ_newsDateS()))
			strSQL += " and newsdatee = " + Common.sqlChar(ut._transToCE_Year(getQ_newsDateS()));
		else if (!"".equals(getQ_newsDateE()))
			strSQL += " and newdatee=" + Common.sqlChar(ut._transToCE_Year(getQ_newsDateE()));		
		db.exeSQL(strSQL);
		setStateDeleteSuccess();
		setErrorMsg("全部刪除完成");
	} catch (Exception e) {
		e.printStackTrace();
	} finally {
		db.closeAll();
	}		
}

/**
 * <br>
 * <br>目的：依主鍵查詢單一資料
 * <br>參數：無
 * <br>傳回：傳回本物件
*/

public SYSWM001F  queryOne() throws Exception{
	Database db = new Database();
	SYSWM001F obj = this;
	try {
		UNTCH_Tools ut = new UNTCH_Tools();
		String sql=" select a.newscat, a.newsid, a.newssubject, a.newscontent, a.newsdates, a.newsdatee, a.itempicture1, a.itempicture2, a.itempicture3, a.itempicture4, a.itempicture5, a.itempicture6, a.itempicture7, a.itempicture8, a.itempicture9, a.itempicture10, a.ishtml, a.editid, a.editdate, a.edittime, a.showMessageYN, a.organid, a.unitid,  "+
				"(select organsname from SYSCA_ORGAN z where z.organid = a.organid) as organIDName," +
				"(select unitname from UNTMP_KEEPUNIT z where z.enterorg = a.organid and z.unitno = a.unitid) as unitIDName," +
				"(select z.empname from SYSAP_EMP z where z.empid = a.editid and z.organid = a.organid) as editidName" +
			" from SYSWM_NEWS a where a.newsid = " + Common.sqlChar(newsID);		
		ResultSet rs = db.querySQL(sql);
		if (rs.next()){
			obj.setNewsCat(rs.getString("newsCat"));
			obj.setNewsID(rs.getString("newsID"));
			obj.setNewsSubject(rs.getString("newsSubject"));
			obj.setNewsContent(rs.getString("newsContent"));
			obj.setNewsDateS(ut._transToROC_Year(rs.getString("newsDateS")));
			obj.setNewsDateE(ut._transToROC_Year(rs.getString("newsDateE")));	
			/**
			StringBuffer sbHTML = new StringBuffer(500).append("");			
			for (int i=0; i<arrFileNames.length; i++) {
				String[] arrFileName;
				String attFile = Common.get(rs.getString(arrFileNames[i]));
				arrFileName=attFile.split(":;:");
				if (arrFileName.length>1) {
					sbHTML.append("<tr>\n");
					sbHTML.append("<td bgcolor='E2F0CF' class='td_form'>附件").append((i+1)).append("：</td>");
					sbHTML.append("<td bgcolor='F6FCE9' class='td_form_white'  style='text-align:left'>");
					sbHTML.append("<a href=\"downloadFileSimple?fileID=").append(attFile).append("\">");
					sbHTML.append(arrFileName[1]);
					sbHTML.append("</a></td>\n");
					sbHTML.append("</tr>\n");
				}
			}
			obj.setItemPictureHTML(sbHTML.toString());
			**/			
			obj.setItemPicture1(rs.getString("itemPicture1"));
			obj.setItemPicture2(rs.getString("itemPicture2"));
			obj.setItemPicture3(rs.getString("itemPicture3"));
			obj.setItemPicture4(rs.getString("itemPicture4"));
			obj.setItemPicture5(rs.getString("itemPicture5"));			
			obj.setItemPicture6(rs.getString("itemPicture6"));
			obj.setItemPicture7(rs.getString("itemPicture7"));
			obj.setItemPicture8(rs.getString("itemPicture8"));
			obj.setItemPicture9(rs.getString("itemPicture9"));
			obj.setItemPicture10(rs.getString("itemPicture10"));
				
			obj.setIsHTML(rs.getString("isHTML"));			
			obj.setEditID(rs.getString("editID"));
			obj.setEditIDName(rs.getString("editIDName"));
			obj.setEditDate(ut._transToROC_Year(rs.getString("editDate")));
			obj.setEditTime(rs.getString("editTime"));
			obj.setShowMessageYN(rs.getString("showMessageYN"));
			
			obj.setOrganID(rs.getString("organID"));
			obj.setOrganName(rs.getString("organIDName"));
			obj.setUnitID(rs.getString("unitID"));
			obj.setUnitName(rs.getString("unitIDName"));
			
			genAttFileHTML(rs);			
			
		}
		setStateQueryOneSuccess();
	} catch (Exception e) {
		e.printStackTrace();
	} finally {
		db.closeAll();
	}
	return obj;
}

private void genAttFileHTML(ResultSet rs) {
	StringBuffer sbHTML = new StringBuffer(500).append("");			
	for (int i=0; i<arrFileNames.length; i++) {
		String[] arrFileName;
		String attFile;
		try {
			attFile = Common.get(rs.getString(arrFileNames[i]));
			arrFileName=attFile.split(":;:");
			if (arrFileName.length>1) {
				sbHTML.append("<tr>\n");
				sbHTML.append("<td bgcolor='E2F0CF' class='td_form'>附件").append((i+1)).append("：</td>");
				sbHTML.append("<td bgcolor='F6FCE9' class='td_form_white'  style='text-align:left'>");
				sbHTML.append("<a class=\"text_link_b\" href=\"downloadFileSimple?fileID=").append(attFile).append("\">");
				sbHTML.append(arrFileName[1]);
				sbHTML.append("</a></td>\n");
				sbHTML.append("</tr>\n");
			}			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	setItemPictureHTML(sbHTML.toString());	
}

/**
 * <br>
 * <br>目的：依查詢欄位查詢多筆資料
 * <br>參數：無
 * <br>傳回：傳回ArrayList
*/

public ArrayList queryAll() throws Exception{
	UNTCH_Tools ut = new UNTCH_Tools();
	Database db = new Database();
	ArrayList objList=new ArrayList();
	try {
		String sql=" select a.newsid, a.newssubject, a.newsdates, a.newsdatee, "+
				"(select organsname from SYSCA_ORGAN z where z.organid = a.organid) as organIDName," +
				"(select unitname from UNTMP_KEEPUNIT z where z.enterorg = a.organid and z.unitno = a.unitid) as unitIDName," +
				"(select empname from SYSAP_EMP z where z.organid = a.organid and z.empid = a.editID) as editIDName" +
			" from SYSWM_NEWS a where 1=1 "; 
			if ("Y".equals(getIsQuery())) {
				if (!"".equals(getQ_newsDate())) 
					sql+=" and a.newsdates <= " + Common.sqlChar(ut._transToCE_Year(getQ_newsDate())) + " and a.newsdatee>=" + Common.sqlChar(ut._transToCE_Year(getQ_newsDate()));
				else sql+=" and a.newsdates <= " + Common.sqlChar(Datetime.getYYYYMMDD()) + " and a.newsdatee>=" + Common.sqlChar(Datetime.getYYYYMMDD());
				if (!"".equals(getQ_newsCat()))
					sql+=" and a.newscat = " + Common.sqlChar(getQ_newsCat()) ;				
			} else {
				if (!"".equals(getQ_newsCat()))
					sql+=" and a.newscat = " + Common.sqlChar(getQ_newsCat()) ;
				if (!"".equals(getQ_newsIDS()) && !"".equals(getQ_newsIDE()))				
					sql+=" and a.newsid between " + Common.sqlChar(getQ_newsIDS()) + " and " + Common.sqlChar(getQ_newsIDE());
				else if (!"".equals(getQ_newsIDS()))
					sql+=" and a.newsid = " + Common.sqlChar(getQ_newsIDS());
				if (!"".equals(getQ_newsSubject()))
					sql+=" and a.newssubject like '%" + getQ_newsSubject() + "%' " ;
				if (!"".equals(getQ_newsContent()))
					sql+=" and a.newscontent like '%" + getQ_newsContent() + "%' ";
				
				if (!"".equals(getQ_newsDateS()) && !"".equals(getQ_newsDateE()))
					sql += " and newsdatee >= " + Common.sqlChar(ut._transToCE_Year(getQ_newsDateS())) + " and newsdatee <= " + Common.sqlChar(ut._transToCE_Year(getQ_newsDateE()));
				else if (!"".equals(getQ_newsDateS()))
					sql+=" and a.newsdates >= " + Common.sqlChar(ut._transToCE_Year(getQ_newsDateS()));
				else if (!"".equals(getQ_newsDateE()))
					sql+=" and a.newdatee <= " + Common.sqlChar(ut._transToCE_Year(getQ_newsDateE()));
			}	
//System.out.println(sql + " order by newsID desc");			
		ResultSet rs = db.querySQL(sql + " order by newsid desc", true);
		processCurrentPageAttribute(rs);
		if (getTotalRecord() > 0) {
			int count = getRecordStart();
			int end = getRecordEnd();
			do {
				if (count > end)
					break;
			String rowArray[]=new String[7];
			rowArray[0]=rs.getString("newsID"); 
			rowArray[1]=rs.getString("newsSubject"); 
			rowArray[2]=ut._transToROC_Year(rs.getString("newsDateS")); 
			rowArray[3]=ut._transToROC_Year(rs.getString("newsDateE")); 
			rowArray[4]=rs.getString("organIDName"); 
			rowArray[5]=rs.getString("unitIDName");
			rowArray[6]=rs.getString("editIDName");
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

private boolean doDeleteFile(String fileName) {
	String[] arrFileName=null;	
	if (!"".equals(Common.get(fileName))) {
		arrFileName=Common.get(fileName).split(":;:");
		if (arrFileName.length>1) {
			Common.RemoveDirectory(new File(filestoreLocation+File.separator+arrFileName[0]));
			return true;
		}
		
	}	
	return false;
}

//租賃契約即將到期之案件
public String ForRent(String check_userName) {
	
	Database db = new Database();

	String check_signNo="";
	double num_year=0.0;
	int check_year=0;
	int check_month=0;
	int check_day=0;
	int rent_year=0;
	int rent_today=0;
	int real_num=0;
	String show_num="";
	int num=0;
	try {
		String sql=" select a.signno "+
			" from NPBBL_USER a " +
			" where 1=1 and a.empid = "+ Common.sqlChar(check_userName) +
			""; 
		
		ResultSet rs = db.querySQL(sql);
		while (rs.next()){		
			if(num==0){
				check_signNo=Common.sqlChar(rs.getString("signno"));
			}else{
				check_signNo += (","+Common.sqlChar(rs.getString("signno")));
			}		
			num++;
		}
		rs.getStatement().close();
		
		SimpleDateFormat sdf =  new SimpleDateFormat("yyyy");
	    Date today = new Date();
	    
	    SimpleDateFormat sdf_m =  new SimpleDateFormat("MM");
        Date today_m = new Date();
        
        SimpleDateFormat sdf_d =  new SimpleDateFormat("dd");
        Date today_d = new Date();
        
        if(!"".equals(check_signNo)) {
        	//if( (check_year == 3 && check_month > 10) || (check_year == 0 && check_month < 4 )){
        
	        	String sql_1=" select b.holddatee "+
					" from UNTLA_LAND a, UNTLA_MANAGE b " +
					" where 1=1  and a.dataState='1' and a.propertykind='04' and a.verify='Y' and b.isdefault='1' " +
					" and b.userelation in ('01','52','53','81') " +
					" and substring(a.signno,1,3) in ("+ check_signNo.substring(0,4) +"')" +
					" and a.enterorg=b.enterorg and a.ownership=b.ownership and a.propertyno=b.propertyno and a.serialno=b.serialno " +
					//" and (substr(a.holddatee,1,3) < " +Common.sqlChar(rent_year)+
					//" or substr(a.holddatee,1,3) = '999')" +
					""; 

				ResultSet rs_1 = db.querySQL(sql_1);
				while (rs_1.next()){
					if(!"".equals(Common.get(rs_1.getString("holddatee")))){
						check_year=Integer.parseInt(Common.get(rs_1.getString("holddatee")).substring(0,3));
					    check_month=Integer.parseInt(Common.get(rs_1.getString("holddatee")).substring(3,5));
					    check_day=Integer.parseInt(Common.get(rs_1.getString("holddatee")).substring(5,7));

					    if(check_month<6){
					       rent_year=Integer.parseInt(Common.formatFrontZero((check_year-1)+"",3)+Common.formatFrontZero((12-(6-check_month))+"",2)+Common.formatFrontZero(check_day+"",2));        	
					      }else{
					       rent_year=Integer.parseInt(Common.formatFrontZero((check_year)+"",3)+Common.formatFrontZero((check_month-6)+"",2)+Common.formatFrontZero(check_day+"",2));        	
					    }
					    
						rent_today=Integer.parseInt(Integer.parseInt(String.valueOf(sdf.format(today)))-1911+""+String.valueOf(sdf_m.format(today_m))+String.valueOf(sdf_d.format(today_d)));	
						
					    if(rent_year <= rent_today){
					    	real_num++;
					    }
					}
					
				}
        	//}else{
        	//	real_num="0";
        	//}
        }else{
        	real_num=0;
        }

        show_num="租賃契約尚未續約共有 "+real_num+" 筆";
		
	} catch (Exception e) {
		e.printStackTrace();
	} finally {
		db.closeAll();
	}

	return show_num;
}


/**
 * <br>
 * <br>目的：依尚未續約查詢多筆資料
 * <br>參數：無
 * <br>傳回：傳回ArrayList
*/

public ArrayList queryAllRent(String check_userName) throws Exception{
	Database db = new Database();
	ArrayList objList=new ArrayList();
	int counter=0;
	String check_signNo="";
	double num_year=0.0;
	int check_year=0;
	int check_month=0;
	int check_day=0;
	int rent_year=0;
	int rent_today=0;
	int num=0;
	try {
		String sql=" select a.signno "+
			" from NPBBL_USER a " +
			" where 1=1 and a.empid = "+ Common.sqlChar(check_userName) +
			""; 
		
		ResultSet rs = db.querySQL(sql);
		while (rs.next()){		
			if(num==0){
				check_signNo=Common.sqlChar(rs.getString("signno"));
			}else{
				check_signNo += (","+Common.sqlChar(rs.getString("signno")));
			}		
			num++;
		}
		rs.getStatement().close();
		
		SimpleDateFormat sdf =  new SimpleDateFormat("yyyy");
	    Date today = new Date();
	    
	    SimpleDateFormat sdf_m =  new SimpleDateFormat("MM");
        Date today_m = new Date();
        
        SimpleDateFormat sdf_d =  new SimpleDateFormat("dd");
        Date today_d = new Date();

	    if(!"".equals(check_signNo) ){
	    		String sql_1=" select d.applyname, (c.Signdesc||SubStr(a.signNo,8,4)||'-'||SubStr(a.signNo,12)) signName , b.usearea , b.holddates, b.holddatee  "+
				" from untla_land a, untla_manage b, SYSCA_Sign c, UNTLA_Person d  " +
				" where 1=1  and a.dataState='1' and a.propertyKind='04' and a.verify='Y' and b.isDefault='1' " +
				" and b.useRelation in ('01','52','53','81') " +
				" and a.enterorg=b.enterorg and a.ownership=b.ownership and a.propertyno=b.propertyno and a.serialno=b.serialno " +
				" and Substr(a.signNo,1,7)=c.signNo " +
				" and substr(a.signNo,1,3) in ("+ check_signNo.substring(0,4) +"')" +
				" and b.enterOrg=d.enterOrg and b.ownership=d.ownership and b.propertyNo=d.propertyNo and b.serialNo=d.serialNo and b.serialNo1=d.serialNo1 "+
				""; 
			//System.out.println("sql_1: "+sql_1);
			ResultSet rs_1 = db.querySQL(sql_1);
			while (rs_1.next()){
				if(!"".equals(Common.get(rs_1.getString("holddatee")))){
					if(!"".equals(Common.get(rs_1.getString("holddatee")))){
						check_year=Integer.parseInt(Common.get(rs_1.getString("holddatee")).substring(0,3));
					    check_month=Integer.parseInt(Common.get(rs_1.getString("holddatee")).substring(3,5));
					    check_day=Integer.parseInt(Common.get(rs_1.getString("holddatee")).substring(5,7));

					    if(check_month<6){
					       rent_year=Integer.parseInt(Common.formatFrontZero((check_year-1)+"",3)+Common.formatFrontZero((12-(6-check_month))+"",2)+Common.formatFrontZero(check_day+"",2));        	
					      }else{
					       rent_year=Integer.parseInt(Common.formatFrontZero((check_year)+"",3)+Common.formatFrontZero((check_month-6)+"",2)+Common.formatFrontZero(check_day+"",2));        	
					    }
					    
						rent_today=Integer.parseInt(Integer.parseInt(String.valueOf(sdf.format(today)))-1911+""+String.valueOf(sdf_m.format(today_m))+String.valueOf(sdf_d.format(today_d)));	
						
					    if(rent_year <= rent_today){
					    	counter++;
							String rowArray[]=new String[5];
							rowArray[0]=rs_1.getString("signName");	
							rowArray[1]=Common.get(rs_1.getString("applyname"));
							rowArray[2]=Common.get(rs_1.getString("usearea"));
							rowArray[3]=Common.get(rs_1.getString("holddates"));
							rowArray[4]=Common.get(rs_1.getString("holddatee"));
							objList.add(rowArray);
							if (counter==getListLimit()){
								setErrorMsg(getListLimitError());
								break;
							}
					    }
					}
				}
				 
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


//將逾法律追溯期限之案件
public ArrayList lowOver(String check_userName) {
	
	Database db = new Database();
	ArrayList objList=new ArrayList();
	
	String check_signNo="";
	double num_year=0.0;
	int check_year=0;
	int check_month=0;
	int check_day=0;
	String rent_year="";	
	String show_num="";
	int num=0;
	int check_count_1=0, check_count_2=0, check_count_3=0, check_count_4=0;
	String real_num_1="", real_num_2="", real_num_3="", real_num_4="";
	
	String rowArray_1[]=new String[2];
	String rowArray_2[]=new String[2];
	String rowArray_3[]=new String[2];
	String rowArray_4[]=new String[2];
	
	try {
		String sql=" select a.signno "+
			" from NPBBL_User a " +
			" where 1=1 and a.empid = "+ Common.sqlChar(check_userName) +
			""; 
//System.out.println("sql:"+sql);		
		ResultSet rs = db.querySQL(sql);
		while (rs.next()){		
			if(num==0){
				check_signNo=Common.sqlChar(rs.getString("signno"));
			}else{
				check_signNo += (","+Common.sqlChar(rs.getString("signno")));
			}		
			num++;
		}
		rs.getStatement().close();
		
		SimpleDateFormat sdf =  new SimpleDateFormat("yyyy");
      Date today = new Date();
      
      SimpleDateFormat sdf_m =  new SimpleDateFormat("MM");
      Date today_m = new Date();
      
      SimpleDateFormat sdf_d =  new SimpleDateFormat("dd");
      Date today_d = new Date();
      
      check_year=Integer.parseInt(String.valueOf(sdf.format(today)))-1911;
      check_month=Integer.parseInt(String.valueOf(sdf_m.format(today_m)));
      check_day=Integer.parseInt(String.valueOf(sdf_d.format(today_d)));

    
    if(!"".equals(check_signNo)) {
    	//支付命令確定日期將於一個月內到期
	        String sql_1=" select distinct c.caseno "+
				" from qry_npbpl_property_final b, npbpl_case c " +
				" where 1=1 " +
				" and b.caseno=c.caseno and c.picdate3 is not null " +
				" and substr(b.signNo,1,7) in ("+ check_signNo +")" +
				" and substr(c.picdate3,1,7) <= " +Common.sqlChar(Common.formatFrontZero((check_year-5)+"",3)+Common.formatFrontZero(check_month+1+"",2)+Common.formatFrontZero(check_day+"",2))+				
				""; 
	
			ResultSet rs_1 = db.querySQL(sql_1);
			while (rs_1.next()){
				check_count_1++;
			}
			real_num_1=check_count_1+"";
			
			rowArray_1[0]="1";
			rowArray_1[1]="支付命令確定日期將於一個月內到期共有 "+real_num_1+" 筆";
			
			rs_1.getStatement().close();
			
		//判決確定日期將於一個月內到期
	        String sql_2=" select distinct c.caseno "+
				" from qry_npbpl_property_final b, npbpl_law c " +
				" where 1=1 " +
				" and b.caseno=c.caseno and c.proofdate is not null " +
				" and substr(b.signNo,1,7) in ("+ check_signNo +")" +
				" and substr(c.proofdate,1,7) <= " +Common.sqlChar(Common.formatFrontZero((check_year-5)+"",3)+Common.formatFrontZero(check_month+1+"",2)+Common.formatFrontZero(check_day+"",2))+				
				" and c.lawType in ('1','2','3')" +
				""; 
		
			ResultSet rs_2 = db.querySQL(sql_2);
			while (rs_2.next()){
				check_count_2++;
			}
			real_num_2=check_count_2+"";
			
			rowArray_2[0]="2";
			rowArray_2[1]="判決確定日期將於一個月內到期共有 "+real_num_2+" 筆";
			
			rs_2.getStatement().close();
			
        //債權憑證日期將於一個月內到期
	        String sql_3=" select distinct c.caseno "+
				" from qry_npbpl_property_final b, npbpl_law c " +
				" where 1=1 " +
				" and b.caseno=c.caseno and c.proofdate is not null " +
				" and substr(b.signNo,1,7) in ("+ check_signNo +")" +
				" and substr(c.proofdate,1,7) <= " +Common.sqlChar(Common.formatFrontZero((check_year-5)+"",3)+Common.formatFrontZero(check_month+1+"",2)+Common.formatFrontZero(check_day+"",2))+				
				" and c.lawType = '7' " +
				""; 
		
			ResultSet rs_3 = db.querySQL(sql_3);
			while (rs_3.next()){
				check_count_3++;
			}
			real_num_3=check_count_3+"";
			
			rowArray_3[0]="3";
			rowArray_3[1]="債權憑證日期將於一個月內到期共有 "+real_num_3+" 筆";
			
			rs_3.getStatement().close();	
			
        //和解日期將於一個月內到期
	        String sql_4=" select distinct c.caseno "+
				" from qry_npbpl_property_final b, NPBPL_Compromise c " +
				" where 1=1 " +
				" and b.caseno=c.caseno and c.compdate is not null " +
				" and substr(b.signNo,1,7) in ("+ check_signNo +")" +
				" and substr(c.compdate,1,7) <= " +Common.sqlChar(Common.formatFrontZero((check_year-5)+"",3)+Common.formatFrontZero(check_month+1+"",2)+Common.formatFrontZero(check_day+"",2))+				
				""; 
		
			ResultSet rs_4 = db.querySQL(sql_4);
			while (rs_4.next()){
				check_count_4++;
			}
			real_num_4=check_count_4+"";
			
			rowArray_4[0]="4";
			rowArray_4[1]="和解日期將於一個月內到期共有 "+real_num_4+" 筆";
			
			rs_4.getStatement().close();	
			
      }else{
    	  rowArray_1[0]="1";
		  rowArray_1[1]="支付命令確定日期將於一個月內到期共有 0 筆";
		  rowArray_2[0]="2";
		  rowArray_2[1]="判決確定日期將於一個月內到期共有 0 筆";
		  rowArray_3[0]="3";
		  rowArray_3[1]="債權憑證日期將於一個月內到期共有 0 筆";
		  rowArray_4[0]="4";
		  rowArray_4[1]="和解日期將於一個月內到期共有 0 筆";
      }
    

	  objList.add(rowArray_1);
	  objList.add(rowArray_2);
	  objList.add(rowArray_3);
	  objList.add(rowArray_4);
	 
	} catch (Exception e) {
		e.printStackTrace();
	} finally {
		db.closeAll();
	}

	return objList;
}

/**
 * <br>
 * <br>目的：依將逾法律追溯期限查詢多筆資料
 * <br>參數：無
 * <br>傳回：傳回ArrayList
*/ 

public ArrayList queryAllLows(String check_userName,String check_count) throws Exception{
	Database db = new Database();
	ArrayList objList=new ArrayList();
	int counter=0;
	String check_signNo="";
	double num_year=0.0;
	int check_year=0;
	int check_month=0;
	int check_day=0;
	String rent_year="";
	int num=0;	
	
	try {
		String sql=" select a.signno "+
			" from NPBBL_User a " +
			" where 1=1 and a.empid = "+ Common.sqlChar(check_userName) +
			""; 
		
		ResultSet rs = db.querySQL(sql);
		while (rs.next()){		
			if(num==0){
				check_signNo=Common.sqlChar(rs.getString("signno"));
			}else{
				check_signNo += (","+Common.sqlChar(rs.getString("signno")));
			}		
			num++;
		}
		rs.getStatement().close();
		
		SimpleDateFormat sdf =  new SimpleDateFormat("yyyy");
	    Date today = new Date();
	    
	    SimpleDateFormat sdf_m =  new SimpleDateFormat("MM");
        Date today_m = new Date();
        
        SimpleDateFormat sdf_d =  new SimpleDateFormat("dd");
        Date today_d = new Date();
        
        check_month=Integer.parseInt(String.valueOf(sdf_m.format(today_m)));
        check_year=Integer.parseInt(String.valueOf(sdf.format(today)))-1911;
        check_day=Integer.parseInt(String.valueOf(sdf_d.format(today_d)));
	
	    if(!"".equals(check_signNo) ){
	    	String sql_1="";
	    	if("1".equals(check_count)){
	    		sql_1=" select distinct c.caseno, c.picdate3 "+
					" from qry_npbpl_property_final b, npbpl_case c " +
					" where 1=1 " +
					" and b.caseno=c.caseno and c.picdate3 is not null " +
					" and substr(b.signNo,1,7) in ("+ check_signNo +")" +
					" and substr(c.picdate3,1,7) <= " +Common.sqlChar(Common.formatFrontZero((check_year-5)+"",3)+Common.formatFrontZero(check_month+1+"",2)+Common.formatFrontZero(check_day+"",2))+				
					"";
	    	}else if("2".equals(check_count) || "3".equals(check_count)){
	    		sql_1=" select distinct c.caseno, c.proofdate "+
					" from qry_npbpl_property_final b, npbpl_law c " +
					" where 1=1 " +
					" and b.caseno=c.caseno and c.proofdate is not null " +
					" and substr(b.signNo,1,7) in ("+ check_signNo +")" +
					" and substr(c.proofdate,1,7) <= " +Common.sqlChar(Common.formatFrontZero((check_year-5)+"",3)+Common.formatFrontZero(check_month+1+"",2)+Common.formatFrontZero(check_day+"",2))+									
					""; 
	    		if("2".equals(check_count)){
	    			sql_1+=" and c.lawType in ('1','2','3')" ;
	    		}else{
	    			sql_1+=" and c.lawType = '7' " ;
	    		}
	    	}else if("4".equals(check_count)){
	    		sql_1=" select distinct c.caseno, c.compdate "+
					" from qry_npbpl_property_final b, NPBPL_Compromise c " +
					" where 1=1 " +
					" and b.caseno=c.caseno and c.compdate is not null " +
					" and substr(b.signNo,1,7) in ("+ check_signNo +")" +
					" and substr(c.compdate,1,7) <= " +Common.sqlChar(Common.formatFrontZero((check_year-5)+"",3)+Common.formatFrontZero(check_month+1+"",2)+Common.formatFrontZero(check_day+"",2))+				
					""; 
	    	}
	    		
			ResultSet rs_1 = db.querySQL(sql_1);
			while (rs_1.next()){
				
				String signName="";
	        	int check_int=0;
	        	String execSQL_2=" select a.signName, a.fakedivision " +
	        			" from qry_npbpl_property_final a " +
	        			" where 1=1 " +
	        			" and a.caseNo=" +Common.sqlChar(Common.get(rs_1.getString("caseno")))+
	        			"";
	        	ResultSet rs_2 = db.querySQL(execSQL_2);
	        	 while(rs_2.next()){
	        		 if(check_int==0){
	        			 signName+=Common.get(rs_2.getString("signName")+" "+Common.get(rs_2.getString("fakedivision")));
	        		 }else{
	        			 signName+=" , "+Common.get(rs_2.getString("signName")+" "+Common.get(rs_2.getString("fakedivision")));
	        		 }
	        		 check_int++;
	        	 }
				
				counter++;
				String rowArray[]=new String[3];
				rowArray[0]=Common.get(rs_1.getString("caseno"));
				rowArray[1]=signName;
				if("1".equals(check_count)){
					rowArray[2]=Common.get(rs_1.getString("picdate3"));
				}else if("2".equals(check_count) || "3".equals(check_count)){
					rowArray[2]=Common.get(rs_1.getString("proofdate"));
				}else if("4".equals(check_count)){
					rowArray[2]=Common.get(rs_1.getString("compdate"));
				}
				
				objList.add(rowArray);
				if (counter==getListLimit()){
					setErrorMsg(getListLimitError());
					break;
				}
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

/**
 * <br>
 * <br>目的：超過一個月未入帳表單
 * <br>參數：自定義表單編號
 * <br>傳回：傳回ArrayList
*/ 

	public ArrayList queryAllNoVerify(String tableNumber) throws Exception{
		Database db = new Database();
		ArrayList objList = new ArrayList();
		
		StringBuilder sql = new StringBuilder();
		
		sql.append("select * from (")
			.append("select distinct ").append(
					" '財產增加單' as type, a.enterorg, a.ownership, a.caseno, a.proofyear + '年' + a.proofdoc + '字第' + a.proofno + '號' as proofData, a.writedate,").append(
					" a.verify, a.editid, '1' as number").append(
				" from UNTLA_ADDPROOF a").append(
				" where not exists (select divisionadd from UNTLA_MERGEDIVISION b where b.enterorg = a.enterorg and a.caseno = b.divisionadd) ");
		sql.append(" union all ");
		sql.append("select distinct ").append(
				" '財產移動單' as type, a.enterorg, a.ownership, a.caseno, a.proofyear + '年' + a.proofdoc + '字第' + a.proofno + '號' as proofData, a.writedate,").append(
				" a.verify, a.editid, '2' as number").append(
				" from UNTMP_MOVEPROOF a");
		sql.append(" union all ");
		sql.append("select distinct ").append(
				" '財產增減值單' as type, a.enterorg, a.ownership, a.caseno, a.proofyear + '年' + a.proofdoc + '字第' + a.proofno + '號' as proofData, a.writedate,").append(
				" a.verify, a.editid, '3' as number").append(
				" from UNTLA_ADJUSTPROOF a");
		sql.append(" union all ");
		sql.append("select distinct ").append(
				" '財產減損單' as type, a.enterorg, a.ownership, a.caseno, a.proofyear + '年' + a.proofdoc + '字第' + a.proofno + '號' as proofData, a.writedate,").append(
				" a.verify, a.editid, '4' as number").append(
				" from UNTLA_REDUCEPROOF a").append(
				" where not exists (select divisionreduce from UNTLA_MERGEDIVISION b where b.enterorg = a.enterorg and a.caseno = b.divisionreduce) ");
		sql.append(" union all ");
		sql.append("select distinct ").append(
				" '土地合併分割單' as type, a.enterorg, a.ownership, a.caseno, a.caseno as proofData, a.editdate as writedate,").append(
				" a.verify, a.editid, '5' as number").append(
				" from UNTLA_MERGEDIVISION a");
		sql.append(" union all ");
		sql.append("select distinct ").append(
				" '非消耗品增加單' as type, a.enterorg, a.ownership, a.caseno, a.proofyear + '年' + a.proofdoc + '字第' + a.proofno + '號' as proofData, a.writedate,").append(
				" a.verify, a.editid, '6' as number").append(
				" from UNTNE_ADDPROOF a");
		sql.append(" union all ");
		sql.append("select distinct ").append(
				" '非消耗品移動單' as type, a.enterorg, a.ownership, a.caseno, a.proofyear + '年' + a.proofdoc + '字第' + a.proofno + '號' as proofData, a.writedate,").append(
				" a.verify, a.editid, '7' as number").append(
				" from UNTNE_MOVEPROOF a");
		sql.append(" union all ");
		sql.append("select distinct ").append(
				" '非消耗品增減值單' as type, a.enterorg, a.ownership, a.caseno, a.proofyear + '年' + a.proofdoc + '字第' + a.proofno + '號' as proofData, a.writedate,").append(
				" a.verify, a.editid, '8' as number").append(
				" from UNTNE_ADJUSTPROOF a");
		sql.append(" union all ");
		sql.append("select distinct ").append(
				" '非消耗品減損單' as type, a.enterorg, a.ownership, a.caseno, a.proofyear + '年' + a.proofdoc + '字第' + a.proofno + '號' as proofData, a.writedate,").append(
				" a.verify, a.editid, '9' as number").append(
				" from UNTNE_REDUCEPROOF a");
		sql.append(") a inner join SYSAP_EMP ap on a.editid = ap.empid where 1=1");
		sql.append(" and a.verify = 'N' and a.enterorg = '").append(Common.checkGet(getEnterOrg())).append("'");
		sql.append(" and a.writedate <= ").append(Common.sqlChar(oneMonth()));
		sql.append(" and a.number = ").append(Common.sqlChar(tableNumber));
		sql.append(" and ap.isstop='N' order by a.number, a.caseno desc, a.writedate desc");

		UNTCH_Tools ut = new UNTCH_Tools();
		try {
			ResultSet rs = db.querySQL_NoChange(sql.toString());
			while(rs.next()) {
				String rowArray[]  =new String[4];
				rowArray[0] = Common.get(rs.getString("type"));
				if (!"".equals(Common.get(rs.getString("proofData"))) && Common.get(rs.getString("proofData")) != null) {
					rowArray[1] = Common.get(rs.getString("proofData"));					
				} else {
					rowArray[1] = Common.get(rs.getString("caseno"));	
				}
				rowArray[2] = Common.get(ut._transToROC_Year(rs.getString("writeDate")));
				if (!"".equals(Common.get(rs.getString("editid"))) && Common.get(rs.getString("editid")) != null) {
					rowArray[3] = Common.get(User.getUser(Common.get(rs.getString("editid")), Common.checkGet(getEnterOrg())).getUserName());					
				}
				objList.add(rowArray);	
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.closeAll();
		}
		
		return objList;
	}
	
	/**
	 * <br>
	 * <br>目的：前一個月的日期
	 * <br>參數：無
	 * <br>傳回：傳回日期字串
	*/ 
	public String oneMonth() {		
		Calendar c = Calendar.getInstance();
		c.set(Calendar.MONTH, c.get(Calendar.MONTH) - 1);
		Date day =  c.getTime();
		return  new SimpleDateFormat("yyyyMMdd").format(day);
	}
	
	
	/**
	 * <br>
	 * <br>目的：超過一個月未入帳表單數量
	 * <br>參數：無
	 * <br>傳回：傳回ArrayList
	*/ 
	public ArrayList queryAllCountNoVerify() throws Exception{
		Database db = new Database();
		ArrayList objList = new ArrayList();
		
		StringBuilder sql = new StringBuilder();
		
		sql.append("select ").append(
				" count(*) as ct, '1' as number").append(
				" from UNTLA_ADDPROOF a inner join SYSAP_EMP ap on a.editid = ap.empid ").append(
				" where a.verify = 'N' and ap.isstop='N' and a.enterorg = '").append(Common.checkGet(getEnterOrg())).append("'").append(
				" and a.writedate <= ").append(Common.sqlChar(oneMonth())).append(
				" and not exists (select divisionadd from UNTLA_MERGEDIVISION b where b.enterorg = a.enterorg and a.caseno = b.divisionadd) ");
		sql.append(" union all ");
		sql.append("select ").append(
				" count(*) as ct, '2' as number").append(
				" from UNTMP_MOVEPROOF a inner join SYSAP_EMP ap on a.editid = ap.empid ").append(
				" where a.verify = 'N' and ap.isstop='N' and a.enterorg = '").append(Common.checkGet(getEnterOrg())).append("'").append(
				" and a.writedate <= ").append(Common.sqlChar(oneMonth()));
		sql.append(" union all ");
		sql.append("select ").append(
				" count(*) as ct, '3' as number").append(
				" from UNTLA_ADJUSTPROOF a inner join SYSAP_EMP ap on a.editid = ap.empid ").append(
				" where a.verify = 'N' and ap.isstop='N' and a.enterorg = '").append(Common.checkGet(getEnterOrg())).append("'").append(
				" and a.writedate <= ").append(Common.sqlChar(oneMonth()));
		sql.append(" union all ");
		sql.append("select ").append(
				" count(*) as ct, '4' as number").append(
				" from UNTLA_REDUCEPROOF a inner join SYSAP_EMP ap on a.editid = ap.empid ").append(
				" where a.verify = 'N' and ap.isstop='N' and a.enterorg = '").append(Common.checkGet(getEnterOrg())).append("'").append(
				" and a.writedate <= ").append(Common.sqlChar(oneMonth())).append(
				" and not exists (select divisionreduce from UNTLA_MERGEDIVISION b where b.enterorg = a.enterorg and a.caseno = b.divisionreduce) ");
		sql.append(" union all ");
		sql.append("select ").append(
				" count(*) as ct, '5' as number").append(
				" from UNTLA_MERGEDIVISION a inner join SYSAP_EMP ap on a.editid = ap.empid ").append(
				" where a.verify = 'N' and ap.isstop='N' and a.enterorg = '").append(Common.checkGet(getEnterOrg())).append("'").append(
				" and a.editdate <= ").append(Common.sqlChar(oneMonth()));
		sql.append(" union all ");
		sql.append("select ").append(
				" count(*) as ct, '6' as number").append(
				" from UNTNE_ADDPROOF a inner join SYSAP_EMP ap on a.editid = ap.empid ").append(
				" where a.verify = 'N' and ap.isstop='N' and a.enterorg = '").append(Common.checkGet(getEnterOrg())).append("'").append(
				" and a.writedate <= ").append(Common.sqlChar(oneMonth()));
		sql.append(" union all ");
		sql.append("select ").append(
				" count(*) as ct, '7' as number").append(
				" from UNTNE_MOVEPROOF a inner join SYSAP_EMP ap on a.editid = ap.empid ").append(
				" where a.verify = 'N' and ap.isstop='N' and a.enterorg = '").append(Common.checkGet(getEnterOrg())).append("'").append(
				" and a.writedate <= ").append(Common.sqlChar(oneMonth()));
		sql.append(" union all ");
		sql.append("select ").append(
				" count(*) as ct, '8' as number").append(
				" from UNTNE_ADJUSTPROOF a inner join SYSAP_EMP ap on a.editid = ap.empid ").append(
				" where a.verify = 'N' and ap.isstop='N' and a.enterorg = '").append(Common.checkGet(getEnterOrg())).append("'").append(
				" and a.writedate <= ").append(Common.sqlChar(oneMonth()));
		sql.append(" union all ");
		sql.append("select ").append(
				" count(*) as ct, '9' as number").append(
				" from UNTNE_REDUCEPROOF a inner join SYSAP_EMP ap on a.editid = ap.empid ").append(
				" where a.verify = 'N' and ap.isstop='N' and a.enterorg = '").append(Common.checkGet(getEnterOrg())).append("'").append(
				" and a.writedate <= ").append(Common.sqlChar(oneMonth()));

		try {
			ResultSet rs = db.querySQL_NoChange(sql.toString());
			while(rs.next()) {
				String rowArray[]  =new String[2];
				rowArray[0] = Common.get(rs.getString("ct"));
				rowArray[1] = Common.get(rs.getString("number"));
				objList.add(rowArray);	
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.closeAll();
		}
		
		return objList;
	}
}


