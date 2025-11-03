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

public class UNTTE004INSERT extends QueryBean{


String lndYYY;
String signNo;
String levyArea1;
String lndPreTax1;
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
public String getSignNO(){ return checkGet(signNo); }
public void setSignNO(String s){ signNo=checkSet(s); }
public String getLevyArea1(){ return checkGet(levyArea1); }
public void setLevyArea1(String s){ levyArea1=checkSet(s); }
public String getLndPreTax1(){ return checkGet(lndPreTax1); }
public void setLndPreTax1(String s){ lndPreTax1=checkSet(s); }
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
 	checkSQLArray[0][0]=" select count(*) as checkResult from LND_Value2 where 1=1 " + 
		" and lndYYY = " + Common.sqlChar(lndYYY) +  
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
	execSQLArray[0]=" insert into LND_Value2(" +
			" lndYYY,"+
			" signNO,"+
			" levyArea1,"+
			" lndPreTax1,"+
		" )Values(" +
			Common.sqlChar("lndYYY") + "," +
			Common.sqlChar(signNo) + "," +
			Common.sqlChar(levyArea1) + "," +
			Common.sqlChar(lndPreTax1);
	return execSQLArray;
}


//傳回update sql
protected String[] getUpdateSQL(){
	String[] execSQLArray = new String[1];
	execSQLArray[0]=" update LND_Value set2 " +
			" levyArea1 = " + Common.sqlChar(levyArea1) + "," +
			" lndPreTax1 = " + Common.sqlChar(lndPreTax1) + "," +
		" where 1=1 " + 
			" and lndYYY = " + Common.sqlChar(lndYYY) +
			" and signNO = " + Common.sqlChar(signNo) +
			"";
	return execSQLArray;
}


//傳回delete sql
protected String[] getDeleteSQL(){
	String[] execSQLArray = new String[1];
	execSQLArray[0]=" delete LND_Value2 where 1=1 " +
			" and lndYYY = " + Common.sqlChar(lndYYY) +
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

public UNTTE004INSERT  queryOne() throws Exception{
	Database db = new Database();
	UNTTE004INSERT obj = this;
	try {
		String sql=" select a.lndYYY,a.signNO, a.levyArea1, a.lndPreTax1 "+
			" from LND_Value a where 1=1 " +
			" and a.lndYYY = " + Common.sqlChar(lndYYY) +
			" and a.signNO = " + Common.sqlChar(signNo) +
			"";
		System.out.println("sql-one:"+sql);
		ResultSet rs = db.querySQL(sql);
		
		if (rs.next()){
			obj.setLndYYY(rs.getString("lndYYY"));
			obj.setSignNO(rs.getString("signNO"));
			obj.setLevyArea1(rs.getString("levyArea1"));
			obj.setLndPreTax1(rs.getString("lndPreTax1"));
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
		String sql=" select a.lndYYY, a.signNo, a.levyArea1, a.lndPreTax1"+
			" from LND_Value a where 1=1 "; 
			if (!"".equals(getQ_lndYYY()))
				sql+=" and a.lndYYY = " + Common.sqlChar(getQ_lndYYY()) ;
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
			int i=0;
			do {
				i++;
				if (count > end) break;
			String rowArray[]=new String[5];
			rowArray[0]="<input type=\'checkbox\' class=\'field_Q\' name=\'strKeys\' value=\"Y\">";
			rowArray[1]=Common.get(rs.getString("lndYYY")); 
			rowArray[2]=Common.get(rs.getString("signNo")); 
			rowArray[3]=Common.get(rs.getString("levyArea1")); 
			rowArray[4]=Common.get(rs.getString("lndPreTax1")); 
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


