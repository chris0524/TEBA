/*
*<br>程式目的：質詢或建決議案資料
*<br>程式代號：refap002f
*<br>程式日期：0950522
*<br>程式作者：clive.chang
*<br>--------------------------------------------------------
*<br>修改作者　　修改日期　　　修改目的
*<br>--------------------------------------------------------
*/

package ref.ap;

import java.sql.ResultSet;
import java.util.ArrayList;
import util.*;

public class REFAP002F extends REFAP002Q {


String seqID;
String itemID;
String expire;
String counter;
String meetKind;
String caseNo;
String councilman;
String kindID_main;
String kindID_other1;
String kindID_other2;
String caseSubject;
String caseDate;
String orgID;
String orgIDName;
String handle1;
String handle2;
String handle3;
String handle4;
String handle5;
String handle6;
String handle7;
String handle8;
String handle9;
String handle10;
String handleDate;
String responseDoc;
String responseDate;
String keyWord;
String isAssure;
String assureDate;
String memo;
String isDel;


public String getSeqID(){ return checkGet(seqID); }
public void setSeqID(String s){ seqID=checkSet(s); }
public String getItemID(){ return checkGet(itemID); }
public void setItemID(String s){ itemID=checkSet(s); }
public String getExpire(){ return checkGet(expire); }
public void setExpire(String s){ expire=checkSet(s); }
public String getCounter(){ return checkGet(counter); }
public void setCounter(String s){ counter=checkSet(s); }
public String getMeetKind(){ return checkGet(meetKind); }
public void setMeetKind(String s){ meetKind=checkSet(s); }
public String getCaseNo(){ return checkGet(caseNo); }
public void setCaseNo(String s){ caseNo=checkSet(s); }
public String getCouncilman(){ return checkGet(councilman); }
public void setCouncilman(String s){ councilman=checkSet(s); }
public String getKindID_main(){ return checkGet(kindID_main); }
public void setKindID_main(String s){ kindID_main=checkSet(s); }
public String getKindID_other1(){ return checkGet(kindID_other1); }
public void setKindID_other1(String s){ kindID_other1=checkSet(s); }
public String getKindID_other2(){ return checkGet(kindID_other2); }
public void setKindID_other2(String s){ kindID_other2=checkSet(s); }
public String getCaseSubject(){ return checkGet(caseSubject); }
public void setCaseSubject(String s){ caseSubject=checkSet(s); }
public String getCaseDate(){ return checkGet(caseDate); }
public void setCaseDate(String s){ caseDate=checkSet(s); }
public String getOrgID(){ return checkGet(orgID); }
public void setOrgID(String s){ orgID=checkSet(s); }
public String getOrgIDName(){ return checkGet(orgIDName); }
public void setOrgIDName(String s){ orgIDName=checkSet(s); }
public String getHandle1(){ return checkGet(handle1); }
public void setHandle1(String s){ handle1=checkSet(s); }
public String getHandle2(){ return checkGet(handle2); }
public void setHandle2(String s){ handle2=checkSet(s); }
public String getHandle3(){ return checkGet(handle3); }
public void setHandle3(String s){ handle3=checkSet(s); }
public String getHandle4(){ return checkGet(handle4); }
public void setHandle4(String s){ handle4=checkSet(s); }
public String getHandle5(){ return checkGet(handle5); }
public void setHandle5(String s){ handle5=checkSet(s); }
public String getHandle6(){ return checkGet(handle6); }
public void setHandle6(String s){ handle6=checkSet(s); }
public String getHandle7(){ return checkGet(handle7); }
public void setHandle7(String s){ handle7=checkSet(s); }
public String getHandle8(){ return checkGet(handle8); }
public void setHandle8(String s){ handle8=checkSet(s); }
public String getHandle9(){ return checkGet(handle9); }
public void setHandle9(String s){ handle9=checkSet(s); }
public String getHandle10(){ return checkGet(handle10); }
public void setHandle10(String s){ handle10=checkSet(s); }
public String getHandleDate(){ return checkGet(handleDate); }
public void setHandleDate(String s){ handleDate=checkSet(s); }
public String getResponseDoc(){ return checkGet(responseDoc); }
public void setResponseDoc(String s){ responseDoc=checkSet(s); }
public String getResponseDate(){ return checkGet(responseDate); }
public void setResponseDate(String s){ responseDate=checkSet(s); }
public String getKeyWord(){ return checkGet(keyWord); }
public void setKeyWord(String s){ keyWord=checkSet(s); }
public String getIsAssure(){ return checkGet(isAssure); }
public void setIsAssure(String s){ isAssure=checkSet(s); }
public String getAssureDate(){ return checkGet(assureDate); }
public void setAssureDate(String s){ assureDate=checkSet(s); }
public String getMemo(){ return checkGet(memo); }
public void setMemo(String s){ memo=checkSet(s); }
public String getIsDel(){ return checkGet(isDel); }
public void setIsDel(String s){ isDel=checkSet(s); }


//傳回執行insert前之檢查sql
protected String[][] getInsertCheckSQL(){
	setSeqID(Datetime.getYYYMMDD()+"00001");
	Database db = new Database();
	try {
		ResultSet rs = db.querySQL(" select max(substr(seqID,8,5))+1 from REF_CASE where substr(seqID,1,7)="+Common.sqlChar(Datetime.getYYYMMDD()));
		if (rs.next() && rs.getInt(1)>0) setSeqID(Datetime.getYYYMMDD()+Common.formatFrontZero(rs.getString(1),5));
		
	} catch (Exception e) {
		e.printStackTrace();
	} finally {
		db.closeAll();
	}
	String[][] checkSQLArray = new String[1][4];
 	checkSQLArray[0][0]=" select count(*) as checkResult from REF_CASE where 1=1 " + 
		" and seqID = " + Common.sqlChar(seqID) + 
		"";
	checkSQLArray[0][1]=">";
	checkSQLArray[0][2]="0";
	checkSQLArray[0][3]="該筆資料己重複，請重新輸入！";
	return checkSQLArray;
}


//傳回insert sql
protected String[] getInsertSQL(){
	String[] execSQLArray = new String[1];
	execSQLArray[0]=" insert into REF_CASE(" +
			" seqID,"+
			" itemID,"+
			" expire,"+
			" counter,"+
			" meetKind,"+
			" caseNo,"+
			" councilman,"+
			" kindID_main,"+
			" kindID_other1,"+
			" kindID_other2,"+
			" caseSubject,"+
			" caseDate,"+
			" orgID,"+
			" Handle1,"+
			" Handle2,"+
			" Handle3,"+
			" Handle4,"+
			" Handle5,"+
			" Handle6,"+
			" Handle7,"+
			" Handle8,"+
			" Handle9,"+
			" Handle10,"+
			" handleDate,"+
			" responseDoc,"+
			" responseDate,"+
			" keyWord,"+
			" isAssure,"+
			" assureDate,"+
			" memo,"+
			" isDel,"+
			" editID,"+
			" editDate,"+
			" editTime"+
		" )Values(" +
			Common.sqlChar(seqID) + "," +
			Common.sqlChar(itemID) + "," +
			Common.sqlChar(expire) + "," +
			Common.sqlChar(counter) + "," +
			Common.sqlChar(meetKind) + "," +
			Common.sqlChar(caseNo) + "," +
			Common.sqlChar(councilman) + "," +
			Common.sqlChar(kindID_main) + "," +
			Common.sqlChar(kindID_other1) + "," +
			Common.sqlChar(kindID_other2) + "," +
			Common.sqlChar(caseSubject) + "," +
			Common.sqlChar(caseDate) + "," +
			Common.sqlChar(orgID) + "," +
			Common.sqlChar(handle1) + "," +
			Common.sqlChar(handle2) + "," +
			Common.sqlChar(handle3) + "," +
			Common.sqlChar(handle4) + "," +
			Common.sqlChar(handle5) + "," +
			Common.sqlChar(handle6) + "," +
			Common.sqlChar(handle7) + "," +
			Common.sqlChar(handle8) + "," +
			Common.sqlChar(handle9) + "," +
			Common.sqlChar(handle10) + "," +
			Common.sqlChar(handleDate) + "," +
			Common.sqlChar(responseDoc) + "," +
			Common.sqlChar(responseDate) + "," +
			Common.sqlChar(keyWord) + "," +
			Common.sqlChar(isAssure) + "," +
			Common.sqlChar(assureDate) + "," +
			Common.sqlChar(memo) + "," +
			Common.sqlChar(isDel) + "," +
			Common.sqlChar(getEditID()) + "," +
			Common.sqlChar(getEditDate()) + "," +
			Common.sqlChar(getEditTime()) + ")" ;
	return execSQLArray;
}


//傳回update sql
protected String[] getUpdateSQL(){
	String[] execSQLArray = new String[1];
	execSQLArray[0]=" update REF_CASE set " +
			" itemID = " + Common.sqlChar(itemID) + "," +
			" expire = " + Common.sqlChar(expire) + "," +
			" counter = " + Common.sqlChar(counter) + "," +
			" meetKind = " + Common.sqlChar(meetKind) + "," +
			" caseNo = " + Common.sqlChar(caseNo) + "," +
			" councilman = " + Common.sqlChar(councilman) + "," +
			" kindID_main = " + Common.sqlChar(kindID_main) + "," +
			" kindID_other1 = " + Common.sqlChar(kindID_other1) + "," +
			" kindID_other2 = " + Common.sqlChar(kindID_other2) + "," +
			" caseSubject = " + Common.sqlChar(caseSubject) + "," +
			" caseDate = " + Common.sqlChar(caseDate) + "," +
			" orgID = " + Common.sqlChar(orgID) + "," +
			" Handle1 = " + Common.sqlChar(handle1) + "," +
			" handle2 = " + Common.sqlChar(handle2) + "," +
			" handle3 = " + Common.sqlChar(handle3) + "," +
			" handle4 = " + Common.sqlChar(handle4) + "," +
			" handle5 = " + Common.sqlChar(handle5) + "," +
			" handle6 = " + Common.sqlChar(handle6) + "," +
			" handle7 = " + Common.sqlChar(handle7) + "," +
			" handle8 = " + Common.sqlChar(handle8) + "," +
			" handle9 = " + Common.sqlChar(handle9) + "," +
			" handle10 = " + Common.sqlChar(handle10) + "," +
			" handleDate = " + Common.sqlChar(handleDate) + "," +
			" responseDoc = " + Common.sqlChar(responseDoc) + "," +
			" responseDate = " + Common.sqlChar(responseDate) + "," +
			" keyWord = " + Common.sqlChar(keyWord) + "," +
			" isAssure = " + Common.sqlChar(isAssure) + "," +
			" assureDate = " + Common.sqlChar(assureDate) + "," +
			" memo = " + Common.sqlChar(memo) + "," +
			" isDel = " + Common.sqlChar(isDel) + "," +
			" editID = " + Common.sqlChar(getEditID()) + "," +
			" editDate = " + Common.sqlChar(getEditDate()) + "," +
			" editTime = " + Common.sqlChar(getEditTime()) + 
		" where 1=1 " + 
			" and seqID = " + Common.sqlChar(seqID) +
			"";
	return execSQLArray;
}


//傳回delete sql
protected String[] getDeleteSQL(){
	String[] execSQLArray = new String[1];
	execSQLArray[0]=" delete REF_CASE where 1=1 " +
			" and seqID = " + Common.sqlChar(seqID) +
			"";
	return execSQLArray;
}


/**
 * <br>
 * <br>目的：依主鍵查詢單一資料
 * <br>參數：無
 * <br>傳回：傳回本物件
*/

public REFAP002F  queryOne() throws Exception{
	Database db = new Database();
	REFAP002F obj = this;
	try {
		String sql=" select a.seqID, a.itemID, a.expire, a.counter, a.meetKind, a.caseNo, a.councilman, a.kindID_main, a.kindID_other1, a.kindID_other2, a.caseSubject, a.caseDate, a.orgID, a.Handle1, a.Handle2, a.Handle3, a.Handle4, a.Handle5, a.Handle6, a.Handle7, a.Handle8, a.Handle9, a.Handle10, a.handleDate, a.responseDoc, a.responseDate, a.keyWord, a.isAssure, a.assureDate, a.memo, a.isDel, a.editID, a.editDate, a.editTime  "+
			" from REF_CASE a where 1=1 " +
			" and a.seqID = " + Common.sqlChar(seqID) +
			"";
		ResultSet rs = db.querySQL(sql);
		if (rs.next()){
			obj.setSeqID(rs.getString("seqID"));
			obj.setItemID(rs.getString("itemID"));
			obj.setExpire(rs.getString("expire"));
			obj.setCounter(rs.getString("counter"));
			obj.setMeetKind(rs.getString("meetKind"));
			obj.setCaseNo(rs.getString("caseNo"));
			obj.setCouncilman(rs.getString("councilman"));
			obj.setKindID_main(rs.getString("kindID_main"));
			obj.setKindID_other1(rs.getString("kindID_other1"));
			obj.setKindID_other2(rs.getString("kindID_other2"));
			obj.setCaseSubject(rs.getString("caseSubject"));
			obj.setCaseDate(rs.getString("caseDate"));
			obj.setOrgID(rs.getString("orgID"));
			obj.setHandle1(rs.getString("Handle1"));
			obj.setHandle2(rs.getString("Handle2"));
			obj.setHandle3(rs.getString("Handle3"));
			obj.setHandle4(rs.getString("Handle4"));
			obj.setHandle5(rs.getString("Handle5"));
			obj.setHandle6(rs.getString("Handle6"));
			obj.setHandle7(rs.getString("Handle7"));
			obj.setHandle8(rs.getString("Handle8"));
			obj.setHandle9(rs.getString("Handle9"));
			obj.setHandle10(rs.getString("Handle10"));
			obj.setHandleDate(rs.getString("handleDate"));
			obj.setResponseDoc(rs.getString("responseDoc"));
			obj.setResponseDate(rs.getString("responseDate"));
			obj.setKeyWord(rs.getString("keyWord"));
			obj.setIsAssure(rs.getString("isAssure"));
			obj.setAssureDate(rs.getString("assureDate"));
			obj.setMemo(rs.getString("memo"));
			obj.setIsDel(rs.getString("isDel"));
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
		String sql=" select a.seqID, decode(a.itemID,'1','總質詢','2','市政報告後質詢','3','部門質詢','4','議員提案','5','臨時提案','') itemID, a.expire, a.counter, decode(a.meetKind,'1','定期會議','2','臨時會','') meetKind, a.caseSubject, a.caseDate "+
			" from REF_CASE a where 1=1 "; 
			if (!"".equals(getQ_seqID()))
				sql+=" and a.seqID = " + Common.sqlChar(getQ_seqID()) ;
			if (!"".equals(getQ_itemID()))
				sql+=" and a.itemID = " + Common.sqlChar(getQ_itemID()) ;
			if (!"".equals(getQ_expire()))
				sql+=" and a.expire = " + Common.sqlChar(getQ_expire()) ;
			if (!"".equals(getQ_counter()))
				sql+=" and a.counter = " + Common.sqlChar(getQ_counter()) ;
			if (!"".equals(getQ_meetKind()))
				sql+=" and a.meetKind = " + Common.sqlChar(getQ_meetKind()) ;
			if (!"".equals(getQ_caseNo()))
				sql+=" and a.caseNo = " + Common.sqlChar(getQ_caseNo()) ;
			if (!"".equals(getQ_councilman()))
				sql+=" and a.councilman like " + Common.sqlChar("%"+getQ_councilman()+"%") ;
			if (!"".equals(getQ_kindID_main()))
				sql+=" and a.kindID_main = " + Common.sqlChar(getQ_kindID_main()) ;
			if (!"".equals(getQ_kindID_other1()))
				sql+=" and a.kindID_other1 = " + Common.sqlChar(getQ_kindID_other1()) ;
			if (!"".equals(getQ_kindID_other2()))
				sql+=" and a.kindID_other2 = " + Common.sqlChar(getQ_kindID_other2()) ;
			if (!"".equals(getQ_caseSubject()))
				sql+=" and a.caseSubject like " + Common.sqlChar("%"+getQ_caseSubject()+"%") ;
			if (!"".equals(getQ_caseDate()))
				sql+=" and a.caseDate >= " + Common.sqlChar(getQ_caseDate()) ;
			if (!"".equals(getQ_caseDateE()))
				sql+=" and a.caseDate <= " + Common.sqlChar(getQ_caseDateE()) ;
			if (!"".equals(getQ_isAssure()))
				sql+=" and a.isAssure = " + Common.sqlChar(getQ_isAssure()) ;						
			if (!"".equals(getQ_assureDate()))
				sql+=" and a.assureDate >= " + Common.sqlChar(getQ_assureDate()) ;			
			if (!"".equals(getQ_assureDateE()))
				sql+=" and a.assureDate <= " + Common.sqlChar(getQ_assureDateE()) ;						
			if (!"".equals(getQ_orgID()))
				sql+=" and a.orgID = " + Common.sqlChar(getQ_orgID()) ;
			if (!"".equals(getQ_keyWord()))
				sql+=" and a.keyWord like " + Common.sqlChar("%"+getQ_keyWord()+"%") ;
					
		ResultSet rs = db.querySQL(sql+ " order by caseDate",true);
		processCurrentPageAttribute(rs);
		if (getTotalRecord() > 0) {
			int count = getRecordStart();
			int end = getRecordEnd();
			do {
				if (count > end) break;
			String rowArray[]=new String[6];
			rowArray[0]=Integer.toString(count); 
			rowArray[1]=Common.get(rs.getString("seqID")); 
			rowArray[2]=Common.get(rs.getString("itemID")); 
			rowArray[3]="第"+Common.get(rs.getString("expire"))+"屆第"+Common.get(rs.getString("counter"))+"次"+Common.get(rs.getString("meetKind"));
			rowArray[4]=Common.get(rs.getString("caseSubject"));
			if (Common.get(rs.getString("caseSubject")).length()>10) {
				rowArray[4]=Common.get(rs.getString("caseSubject")).substring(0,10)+"...";
			}			 
			rowArray[5]=Common.get(rs.getString("caseDate")); 
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


public String getQueryAll() throws Exception{
	Database db = new Database();
	String rStr = "";
	try {
		String sql=" select a.caseDate, a.caseSubject, a.councilman, a.handle1,a.handle2,a.handle3,a.handle4,a.handle5,a.handle6,a.handle7,a.handle8,a.handle9,a.handle10, a.memo, c.organSName "+
			" from REF_CASE a, SYSCA_Organ c where a.orgID=c.organID "; 
			if (!"".equals(getQ_seqID()))
				sql+=" and a.seqID = " + Common.sqlChar(getQ_seqID()) ;
			if (!"".equals(getQ_itemID()))
				sql+=" and a.itemID = " + Common.sqlChar(getQ_itemID()) ;
			if (!"".equals(getQ_expire()))
				sql+=" and a.expire = " + Common.sqlChar(getQ_expire()) ;
			if (!"".equals(getQ_counter()))
				sql+=" and a.counter = " + Common.sqlChar(getQ_counter()) ;
			if (!"".equals(getQ_meetKind()))
				sql+=" and a.meetKind = " + Common.sqlChar(getQ_meetKind()) ;
			if (!"".equals(getQ_caseNo()))
				sql+=" and a.caseNo = " + Common.sqlChar(getQ_caseNo()) ;
			if (!"".equals(getQ_councilman()))
				sql+=" and a.councilman like " + Common.sqlChar("%"+getQ_councilman()+"%") ;
			if (!"".equals(getQ_kindID_main()))
				sql+=" and a.kindID_main = " + Common.sqlChar(getQ_kindID_main()) ;
			if (!"".equals(getQ_kindID_other1()))
				sql+=" and a.kindID_other1 = " + Common.sqlChar(getQ_kindID_other1()) ;
			if (!"".equals(getQ_kindID_other2()))
				sql+=" and a.kindID_other2 = " + Common.sqlChar(getQ_kindID_other2()) ;
			if (!"".equals(getQ_caseSubject()))
				sql+=" and a.caseSubject like " + Common.sqlChar("%"+getQ_caseSubject()+"%") ;
			if (!"".equals(getQ_caseDate()))
				sql+=" and a.caseDate >= " + Common.sqlChar(getQ_caseDate()) ;
			if (!"".equals(getQ_caseDateE()))
				sql+=" and a.caseDate <= " + Common.sqlChar(getQ_caseDateE()) ;
			if (!"".equals(getQ_isAssure()))
				sql+=" and a.isAssure = " + Common.sqlChar(getQ_isAssure()) ;						
			if (!"".equals(getQ_assureDate()))
				sql+=" and a.assureDate >= " + Common.sqlChar(getQ_assureDate()) ;			
			if (!"".equals(getQ_assureDateE()))
				sql+=" and a.assureDate <= " + Common.sqlChar(getQ_assureDateE()) ;						
			if (!"".equals(getQ_orgID()))
				sql+=" and a.orgID = " + Common.sqlChar(getQ_orgID()) ;
			if (!"".equals(getQ_keyWord()))
				sql+=" and a.keyWord like " + Common.sqlChar("%"+getQ_keyWord()+"%") ;
			
			sql+=" order by caseDate ";
					
		ResultSet rs = db.querySQL(Common.isoToBig5(sql));
		StringBuffer sbHTML = new StringBuffer();
		boolean sFlag = false;
		while (rs.next()) {		
			sFlag = true;
			sbHTML.append("<tr  valign=\"top\">"); 
			sbHTML.append("<td class=\"linetd\" >").append(Common.checkGet(rs.getString("councilman"))).append("&nbsp;</td>\n"); 
			sbHTML.append("<td class=\"linetd\" >").append(Common.FormatStr(Common.checkGet(rs.getString("caseSubject")),"N")).append("&nbsp;</td>\n"); 
			sbHTML.append("<td class=\"linetd\" >").append(Common.checkGet(rs.getString("organSName"))).append("&nbsp;</td>\n"); 
			sbHTML.append("<td class=\"linetd\" >");			
			sbHTML.append("<table>");
			for (int i=4; i<14; i++) {
				if (Common.get(rs.getString(i)).length()>0) {						
					sbHTML.append("<tr><td valign='top'>").append(getZhNumName(i-4)).append("、</td>");
					sbHTML.append("<td>").append( Common.checkGet(rs.getString(i)) ).append("&nbsp;</td></tr>\n");									
				}
			}
			sbHTML.append("</table>");
			sbHTML.append("&nbsp;</td>\n"); 
			sbHTML.append("<td class=\"linetd\" >").append(Common.checkGet(rs.getString("caseDate"))).append("&nbsp;</td>\n"); 
			sbHTML.append("<td class=\"linetd\" >").append(Common.FormatStr(Common.checkGet(rs.getString("memo")),"N")).append("&nbsp;</td>\n");
			sbHTML.append("</tr>\n"); 								
		}
		if (sFlag) rStr = sbHTML.toString();
		setStateQueryAllSuccess();
	} catch (Exception e) {
		e.printStackTrace();
	} finally {
		db.closeAll();
	}
	return rStr;
}

public String getZhNumName(int i) {
	String rStr = "";
	String[] zhName = {"一","二","三","四","五","六","七","八","九","十"};
	if (i<zhName.length) {
		rStr = zhName[i];
	}	
	return rStr;
}


}


