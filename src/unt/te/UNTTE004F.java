/*
*<br>程式目的：地價稅課稅明細異動作業
*<br>程式代號：untte004f
*<br>程式日期：0960905
*<br>程式作者：blair
*<br>--------------------------------------------------------
*<br>修改作者　　修改日期　　　修改目的
*<br>--------------------------------------------------------
*/

package unt.te;

import java.sql.ResultSet;
import java.util.ArrayList;
import util.*;

public class UNTTE004F extends QueryBean{


String lndYYY;
String lndLosn;
String signNo;
String lndTaxTp1;
String levyArea1;
String abateArea1;
String abateAll1;
String lndPreTax1;
String lndTaxTp2;
String levyArea2;
String abateArea2;
String abateAll2;
String lndPreTax2;
String lndTaxTp3;
String levyArea3;
String abateArea3;
String abateAll3;
String lndPreTax3;
String lndTaxTp4;
String levyArea4;
String abateArea4;
String abateAll4;
String lndPreTax4;
String lndTaxTp5;
String levyArea5;
String abateArea5;
String abateAll5;
String lndPreTax5;
String secChiNM;
String OD;
String OA;
String editID;
String editDate;
String editTime;

String q_lndYYY;
String q_lndLosn;
String q_signNo;
String q_signNo1;
String q_signNo2;
String q_signNo3;
String q_signNo4;
String q_signNo5;

public String getLndYYY(){ return checkGet(lndYYY); }
public void setLndYYY(String s){ lndYYY=checkSet(s); }
public String getLndLosn(){ return checkGet(lndLosn); }
public void setLndLosn(String s){ lndLosn=checkSet(s); }
public String getSignNO(){ return checkGet(signNo); }
public void setSignNO(String s){ signNo=checkSet(s); }
public String getLndTaxTp1(){ return checkGet(lndTaxTp1); }
public void setLndTaxTp1(String s){ lndTaxTp1=checkSet(s); }
public String getLevyArea1(){ return checkGet(levyArea1); }
public void setLevyArea1(String s){ levyArea1=checkSet(s); }
public String getAbateArea1(){ return checkGet(abateArea1); }
public void setAbateArea1(String s){ abateArea1=checkSet(s); }
public String getAbateAll1(){ return checkGet(abateAll1); }
public void setAbateAll1(String s){ abateAll1=checkSet(s); }
public String getLndPreTax1(){ return checkGet(lndPreTax1); }
public void setLndPreTax1(String s){ lndPreTax1=checkSet(s); }
public String getLndTaxTp2(){ return checkGet(lndTaxTp2); }
public void setLndTaxTp2(String s){ lndTaxTp2=checkSet(s); }
public String getLevyArea2(){ return checkGet(levyArea2); }
public void setLevyArea2(String s){ levyArea2=checkSet(s); }
public String getAbateArea2(){ return checkGet(abateArea2); }
public void setAbateArea2(String s){ abateArea2=checkSet(s); }
public String getAbateAll2(){ return checkGet(abateAll2); }
public void setAbateAll2(String s){ abateAll2=checkSet(s); }
public String getLndPreTax2(){ return checkGet(lndPreTax2); }
public void setLndPreTax2(String s){ lndPreTax2=checkSet(s); }
public String getLndTaxTp3(){ return checkGet(lndTaxTp3); }
public void setLndTaxTp3(String s){ lndTaxTp3=checkSet(s); }
public String getLevyArea3(){ return checkGet(levyArea3); }
public void setLevyArea3(String s){ levyArea3=checkSet(s); }
public String getAbateArea3(){ return checkGet(abateArea3); }
public void setAbateArea3(String s){ abateArea3=checkSet(s); }
public String getAbateAll3(){ return checkGet(abateAll3); }
public void setAbateAll3(String s){ abateAll3=checkSet(s); }
public String getLndPreTax3(){ return checkGet(lndPreTax3); }
public void setLndPreTax3(String s){ lndPreTax3=checkSet(s); }
public String getLndTaxTp4(){ return checkGet(lndTaxTp4); }
public void setLndTaxTp4(String s){ lndTaxTp4=checkSet(s); }
public String getLevyArea4(){ return checkGet(levyArea4); }
public void setLevyArea4(String s){ levyArea4=checkSet(s); }
public String getAbateArea4(){ return checkGet(abateArea4); }
public void setAbateArea4(String s){ abateArea4=checkSet(s); }
public String getAbateAll4(){ return checkGet(abateAll4); }
public void setAbateAll4(String s){ abateAll4=checkSet(s); }
public String getLndPreTax4(){ return checkGet(lndPreTax4); }
public void setLndPreTax4(String s){ lndPreTax4=checkSet(s); }
public String getLndTaxTp5(){ return checkGet(lndTaxTp5); }
public void setLndTaxTp5(String s){ lndTaxTp5=checkSet(s); }
public String getLevyArea5(){ return checkGet(levyArea5); }
public void setLevyArea5(String s){ levyArea5=checkSet(s); }
public String getAbateArea5(){ return checkGet(abateArea5); }
public void setAbateArea5(String s){ abateArea5=checkSet(s); }
public String getAbateAll5(){ return checkGet(abateAll5); }
public void setAbateAll5(String s){ abateAll5=checkSet(s); }
public String getLndPreTax5(){ return checkGet(lndPreTax5); }
public void setLndPreTax5(String s){ lndPreTax5=checkSet(s); }
public String getSecChiNM(){ return checkGet(secChiNM); }
public void setSecChiNM(String s){ secChiNM=checkSet(s); }
public String getOD(){ return checkGet(OD); }
public void setOD(String s){ OD=checkSet(s); }
public String getOA(){ return checkGet(OA); }
public void setOA(String s){ OA=checkSet(s); }
public String getEditID(){ return checkGet(editID); }
public void setEditID(String s){ editID=checkSet(s); }
public String getEditDate(){ return checkGet(editDate); }
public void setEditDate(String s){ editDate=checkSet(s); }
public String getEditTime(){ return checkGet(editTime); }
public void setEditTime(String s){ editTime=checkSet(s); }

public String getQ_lndYYY(){ return checkGet(q_lndYYY); }
public void setQ_lndYYY(String s){ q_lndYYY=checkSet(s); }
public String getQ_lndLosn(){ return checkGet(q_lndLosn); }
public void setQ_lndLosn(String s){ q_lndLosn=checkSet(s); }
public String getQ_signNo(){ return checkGet(q_signNo); }
public void setQ_signNo(String s){ q_signNo=checkSet(s); }
public String getQ_signNo1(){ return checkGet(q_signNo1); }
public void setQ_signNo1(String s){ q_signNo1=checkSet(s); }
public String getQ_signNo2(){ return checkGet(q_signNo2); }
public void setQ_signNo2(String s){ q_signNo2=checkSet(s); }
public String getQ_signNo3(){ return checkGet(q_signNo3); }
public void setQ_signNo3(String s){ q_signNo3=checkSet(s); }
public String getQ_signNo4(){ return checkGet(q_signNo4); }
public void setQ_signNo4(String s){ q_signNo4=checkSet(s); }
public String getQ_signNo5(){ return checkGet(q_signNo5); }
public void setQ_signNo5(String s){ q_signNo5=checkSet(s); }

//傳回執行insert前之檢查sql
protected String[][] getInsertCheckSQL(){
	String[][] checkSQLArray = new String[1][4];
 	checkSQLArray[0][0]=" select count(*) as checkResult from LND_Value where 1=1 " + 
		" and lndYYY = " + Common.sqlChar(lndYYY) + 
		" and lndLosn = " + Common.sqlChar(lndLosn) + 
		" and signNO = " + Common.sqlChar(signNo) + 
		"";
	checkSQLArray[0][1]=">";
	checkSQLArray[0][2]="0";
	checkSQLArray[0][3]="該筆資料己重複，請重新輸入！";
	return checkSQLArray;
}


//傳回insert sql
protected String[] getInsertSQL(){
	String[] execSQLArray = new String[1];
	execSQLArray[0]=" insert into LND_Value(" +
			" lndYYY,"+
			" lndLosn,"+
			" signNO,"+
			" lndTaxTp1,"+
			" levyArea1,"+
			" abateArea1,"+
			" abateAll1,"+
			" lndPreTax1,"+
			" lndTaxTp2,"+
			" levyArea2,"+
			" abateArea2,"+
			" abateAll2,"+
			" lndPreTax2,"+
			" lndTaxTp3,"+
			" levyArea3,"+
			" abateArea3,"+
			" abateAll3,"+
			" lndPreTax3,"+
			" lndTaxTp4,"+
			" levyArea4,"+
			" abateArea4,"+
			" abateAll4,"+
			" lndPreTax4,"+
			" lndTaxTp5,"+
			" levyArea5,"+
			" abateArea5,"+
			" abateAll5,"+
			" lndPreTax5,"+
			" secChiNM,"+
			" OD,"+
			" OA,"+
			" editID,"+
			" editDate,"+
			" editTime"+
		" )Values(" +
			Common.sqlChar("lndYYY") + "," +
			Common.sqlChar(lndLosn) + "," +
			Common.sqlChar(signNo) + "," +
			Common.sqlChar(lndTaxTp1) + "," +
			Common.sqlChar(levyArea1) + "," +
			Common.sqlChar(abateArea1) + "," +
			Common.sqlChar(abateAll1) + "," +
			Common.sqlChar(lndPreTax1) + "," +
			Common.sqlChar(lndTaxTp2) + "," +
			Common.sqlChar(levyArea2) + "," +
			Common.sqlChar(abateArea2) + "," +
			Common.sqlChar(abateAll2) + "," +
			Common.sqlChar(lndPreTax2) + "," +
			Common.sqlChar(lndTaxTp3) + "," +
			Common.sqlChar(levyArea3) + "," +
			Common.sqlChar(abateArea3) + "," +
			Common.sqlChar(abateAll3) + "," +
			Common.sqlChar(lndPreTax3) + "," +
			Common.sqlChar(lndTaxTp4) + "," +
			Common.sqlChar(levyArea4) + "," +
			Common.sqlChar(abateArea4) + "," +
			Common.sqlChar(abateAll4) + "," +
			Common.sqlChar(lndPreTax4) + "," +
			Common.sqlChar(lndTaxTp5) + "," +
			Common.sqlChar(levyArea5) + "," +
			Common.sqlChar(abateArea5) + "," +
			Common.sqlChar(abateAll5) + "," +
			Common.sqlChar(lndPreTax5) + "," +
			Common.sqlChar(secChiNM) + "," +
			Common.sqlChar(OD) + "," +
			Common.sqlChar(OA) + "," +
			Common.sqlChar(getEditID()) + "," +
			Common.sqlChar(getEditDate()) + "," +
			Common.sqlChar(getEditTime()) + ")" ;
	return execSQLArray;
}


//傳回update sql
protected String[] getUpdateSQL(){
	String[] execSQLArray = new String[1];
	execSQLArray[0]=" update LND_Value set " +
			" lndTaxTp1 = " + Common.sqlChar(lndTaxTp1) + "," +
			" levyArea1 = " + Common.sqlChar(levyArea1) + "," +
			" abateArea1 = " + Common.sqlChar(abateArea1) + "," +
			" abateAll1 = " + Common.sqlChar(abateAll1) + "," +
			" lndPreTax1 = " + Common.sqlChar(lndPreTax1) + "," +
			" lndTaxTp2 = " + Common.sqlChar(lndTaxTp2) + "," +
			" levyArea2 = " + Common.sqlChar(levyArea2) + "," +
			" abateArea2 = " + Common.sqlChar(abateArea2) + "," +
			" abateAll2 = " + Common.sqlChar(abateAll2) + "," +
			" lndPreTax2 = " + Common.sqlChar(lndPreTax2) + "," +
			" lndTaxTp3 = " + Common.sqlChar(lndTaxTp3) + "," +
			" levyArea3 = " + Common.sqlChar(levyArea3) + "," +
			" abateArea3 = " + Common.sqlChar(abateArea3) + "," +
			" abateAll3 = " + Common.sqlChar(abateAll3) + "," +
			" lndPreTax3 = " + Common.sqlChar(lndPreTax3) + "," +
			" lndTaxTp4 = " + Common.sqlChar(lndTaxTp4) + "," +
			" levyArea4 = " + Common.sqlChar(levyArea4) + "," +
			" abateArea4 = " + Common.sqlChar(abateArea4) + "," +
			" abateAll4 = " + Common.sqlChar(abateAll4) + "," +
			" lndPreTax4 = " + Common.sqlChar(lndPreTax4) + "," +
			" lndTaxTp5 = " + Common.sqlChar(lndTaxTp5) + "," +
			" levyArea5 = " + Common.sqlChar(levyArea5) + "," +
			" abateArea5 = " + Common.sqlChar(abateArea5) + "," +
			" abateAll5 = " + Common.sqlChar(abateAll5) + "," +
			" lndPreTax5 = " + Common.sqlChar(lndPreTax5) + "," +
			" secChiNM = " + Common.sqlChar(secChiNM) + "," +
			" OD = " + Common.sqlChar(OD) + "," +
			" OA = " + Common.sqlChar(OA) + "," +
			" editID = " + Common.sqlChar(getEditID()) + "," +
			" editDate = " + Common.sqlChar(getEditDate()) + "," +
			" editTime = " + Common.sqlChar(getEditTime()) + 
		" where 1=1 " + 
			" and lndYYY = " + Common.sqlChar(lndYYY) +
			" and lndLosn = " + Common.sqlChar(lndLosn) +
			" and signNO = " + Common.sqlChar(signNo) +
			"";
	return execSQLArray;
}


//傳回delete sql
protected String[] getDeleteSQL(){
	String[] execSQLArray = new String[1];
	execSQLArray[0]=" delete LND_Value where 1=1 " +
			" and lndYYY = " + Common.sqlChar(lndYYY) +
			" and lndLosn = " + Common.sqlChar(lndLosn) +
			" and signNO = " + Common.sqlChar(signNo) +
			"";
	return execSQLArray;
}


/**
 * <br>
 * <br>目的：依主鍵查詢單一資料
 * <br>參數：無
 * <br>傳回：傳回本物件
*/

public UNTTE004F  queryOne() throws Exception{
	Database db = new Database();
	UNTTE004F obj = this;
	try {
		String sql=" select a.lndYYY, a.lndLosn, a.signNO, a.lndTaxTp1, a.levyArea1, a.abateArea1, a.abateAll1, a.lndPreTax1, a.lndTaxTp2, a.levyArea2, a.abateArea2, a.abateAll2, a.lndPreTax2, a.lndTaxTp3, a.levyArea3, a.abateArea3, a.abateAll3, a.lndPreTax3, a.lndTaxTp4, a.levyArea4, a.abateArea4, a.abateAll4, a.lndPreTax4, a.lndTaxTp5, a.levyArea5, a.abateArea5, a.abateAll5, a.lndPreTax5, a.OD, a.OA, a.editID, a.editDate, a.editTime, a.editID, a.editDate, a.editTime,  "+
			"(select substr(signdesc,4) from SYSCA_SIGN where signno=substr(a.signNo,1,7)) as secChiNM " +
			" from LND_Value a where 1=1 " +
			" and a.lndYYY = " + Common.sqlChar(lndYYY) +
			" and a.lndLosn = " + Common.sqlChar(lndLosn) +
			" and a.signNO = " + Common.sqlChar(signNo) +
			"";
		System.out.println("sql-one:"+sql);
		ResultSet rs = db.querySQL(sql);
		
		if (rs.next()){
			obj.setLndYYY(rs.getString("lndYYY"));
			obj.setLndLosn(rs.getString("lndLosn"));
			obj.setSignNO(rs.getString("signNO"));
			obj.setLndTaxTp1(rs.getString("lndTaxTp1"));
			obj.setLevyArea1(rs.getString("levyArea1"));
			obj.setAbateArea1(rs.getString("abateArea1"));
			obj.setAbateAll1(rs.getString("abateAll1"));
			obj.setLndPreTax1(rs.getString("lndPreTax1"));
			obj.setLndTaxTp2(rs.getString("lndTaxTp2"));
			obj.setLevyArea2(rs.getString("levyArea2"));
			obj.setAbateArea2(rs.getString("abateArea2"));
			obj.setAbateAll2(rs.getString("abateAll2"));
			obj.setLndPreTax2(rs.getString("lndPreTax2"));
			obj.setLndTaxTp3(rs.getString("lndTaxTp3"));
			obj.setLevyArea3(rs.getString("levyArea3"));
			obj.setAbateArea3(rs.getString("abateArea3"));
			obj.setAbateAll3(rs.getString("abateAll3"));
			obj.setLndPreTax3(rs.getString("lndPreTax3"));
			obj.setLndTaxTp4(rs.getString("lndTaxTp4"));
			obj.setLevyArea4(rs.getString("levyArea4"));
			obj.setAbateArea4(rs.getString("abateArea4"));
			obj.setAbateAll4(rs.getString("abateAll4"));
			obj.setLndPreTax4(rs.getString("lndPreTax4"));
			obj.setLndTaxTp5(rs.getString("lndTaxTp5"));
			obj.setLevyArea5(rs.getString("levyArea5"));
			obj.setAbateArea5(rs.getString("abateArea5"));
			obj.setAbateAll5(rs.getString("abateAll5"));
			obj.setLndPreTax5(rs.getString("lndPreTax5"));
			obj.setSecChiNM(rs.getString("secChiNM")+rs.getString("signNo").substring(7,11)+"-"+rs.getString("signNo").substring(11,15));
			obj.setOD(rs.getString("OD"));
			obj.setOA(rs.getString("OA"));
			obj.setEditID(rs.getString("editID"));
			obj.setEditDate(rs.getString("editDate"));
			obj.setEditTime(rs.getString("editTime"));
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
	String loc="";
	ArrayList objList=new ArrayList();
	try {
		String sql=" select a.lndYYY, a.lndLosn, a.signNo "+
			" from LND_Value a where 1=1 "; 
			if (!"".equals(getQ_lndYYY()))
				sql+=" and a.lndYYY = " + Common.sqlChar(getQ_lndYYY()) ;
			if (!"".equals(getQ_lndLosn()))
				sql+=" and a.lndLosn = " + Common.sqlChar(getQ_lndLosn()) ;
			if(!"".equals(getQ_signNo1()))
				loc="E";
			if(!"".equals(getQ_signNo2()))
				loc=getQ_signNo2().substring(0,3);
			if(!"".equals(getQ_signNo3()))
				loc=getQ_signNo3().substring(0,7);
			if(!"".equals(getQ_signNo4()))
				loc+=Common.formatFrontZero(getQ_signNo4(),4);
			if(!"".equals(getQ_signNo5())){
				loc+=Common.formatFrontZero(getQ_signNo5(),4);
			}
			sql+=" and a.signNo like "+"'"+loc+"%'";
			System.out.println("sql:"+sql);
				
		ResultSet rs = db.querySQL(sql,true);
		processCurrentPageAttribute(rs);
		if (getTotalRecord() > 0) {
			int count = getRecordStart();
			int end = getRecordEnd();
			do {
				if (count > end) break;
			String rowArray[]=new String[3];
			rowArray[0]=Common.get(rs.getString("lndYYY")); 
			rowArray[1]=Common.get(rs.getString("lndLosn")); 
			rowArray[2]=Common.get(rs.getString("signNO")); 
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


