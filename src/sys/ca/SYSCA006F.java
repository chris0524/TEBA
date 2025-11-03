/*
*<br>程式目的：土地建物標示代碼維護
*<br>程式代號：sysca006f
*<br>程式日期：0950613
*<br>程式作者：amanda
*<br>--------------------------------------------------------
*<br>修改作者　　修改日期　　　修改目的
*<br>--------------------------------------------------------
*/

package sys.ca;

import java.sql.ResultSet;
import java.util.ArrayList;
import util.*;

public class SYSCA006F extends QueryBean{


String country;
String town;
String sectorNo;
String signName;
String signDesc;
String memo;

String q_country;
String q_town;
String q_signDesc;
String q_sign;

public String getCountry(){ return checkGet(country); }
public void setCountry(String s){ country=checkSet(s); }
public String getTown(){ return checkGet(town); }
public void setTown(String s){ town=checkSet(s); }
public String getSectorNo(){ return checkGet(sectorNo); }
public void setSectorNo(String s){ sectorNo=checkSet(s); }
public String getSignName(){ return checkGet(signName); }
public void setSignName(String s){ signName=checkSet(s); }
public String getSignDesc(){ return checkGet(signDesc); }
public void setSignDesc(String s){ signDesc=checkSet(s); }
public String getMemo(){ return checkGet(memo); }
public void setMemo(String s){ memo=checkSet(s); }

public String getQ_country(){ return checkGet(q_country); }
public void setQ_country(String s){ q_country=checkSet(s); }
public String getQ_town(){ return checkGet(q_town); }
public void setQ_town(String s){ q_town=checkSet(s); }
public String getQ_signDesc(){ return checkGet(q_signDesc); }
public void setQ_signDesc(String s){ q_signDesc=checkSet(s); }
public String getQ_sign(){ return checkGet(q_sign); }
public void setQ_sign(String s){ q_sign=checkSet(s); }

String signNo;
String oldSignNo;
String batchInsertFlag;
public String getSignNo() { return checkGet(signNo); }
public void setSignNo(String s) { signNo = checkSet(s); } 
public String getOldSignNo() { return checkGet(oldSignNo); }
public void setOldSignNo(String s) { oldSignNo = checkSet(s); }
public String getBatchInsertFlag(){ return checkGet(batchInsertFlag); }
public void setBatchInsertFlag(String s){ batchInsertFlag=checkSet(s); }


//傳回執行insert前之檢查sql
protected String[][] getInsertCheckSQL(){
	signNo = "";
	if (!"".equals(getCountry())) signNo = getCountry().substring(0,1)+getTown().substring(1,3)+getSectorNo();
	
	String[][] checkSQLArray = new String[1][4];
 	checkSQLArray[0][0]=" select count(*) as checkresult from SYSCA_SIGN where 1=1 " + 
 		" and signno = " + Common.sqlChar(signNo) + 
		"";
	checkSQLArray[0][1]=">";
	checkSQLArray[0][2]="0";
	checkSQLArray[0][3]="該筆資料己重複，請重新輸入！";
	return checkSQLArray;
}


//傳回insert sql
protected String[] getInsertSQL(){
	String[] execSQLArray = new String[1];
	execSQLArray[0]=" insert into SYSCA_SIGN(" +
			" signno,"+
			" signname,"+
			" signdesc,"+
			" memo,"+
			" editid,"+
			" editdate,"+
			" edittime"+
		" )Values(" +
			Common.sqlChar(signNo) + "," +
			Common.sqlChar(signName) + "," +
			Common.sqlChar(signDesc) + "," +
			Common.sqlChar(memo) + "," +
			Common.sqlChar(getEditID()) + "," +
			Common.sqlChar(getEditDate()) + "," +
			Common.sqlChar(getEditTime()) + ")" ;
	return execSQLArray;
}


//傳回update sql
protected String[] getUpdateSQL(){
	
	//signNo = "";
	//if (!"".equals(getCountry())) signNo = getCountry().substring(0,1)+getTown().substring(1,3)+getSectorNo();
	
	String[] execSQLArray = new String[1];	
	if (getSignNo().length()==7) {	
		execSQLArray[0]=" update SYSCA_SIGN set " +
				" signname = " + Common.sqlChar(signName) + "," +
				" signdesc = " + Common.sqlChar(signDesc) + "," +
				" memo = " + Common.sqlChar(memo) + "," +
				" editid = " + Common.sqlChar(getEditID()) + "," +
				" editdate = " + Common.sqlChar(getEditDate()) + "," +
				" edittime = " + Common.sqlChar(getEditTime()) + 
				" where signno= '" + signNo+ "'";	
		System.out.println(execSQLArray[0]);
	} else {
		execSQLArray[0]="";
	}
	return execSQLArray;
}


//傳回delete sql
protected String[] getDeleteSQL(){
//	signNo = "";
//	if (!"".equals(getCountry())) signNo = getCountry().substring(0,1)+getTown().substring(1,3)+getSectorNo();
//			
	String[] execSQLArray = new String[1];
	if (getSignNo().length()==7) {		
		execSQLArray[0]=" delete SYSCA_SIGN where 1=1 " +
				" and signno = " + Common.sqlChar(signNo) +
				"";
	} else {
		execSQLArray[0]="";
	}
	return execSQLArray;
}


/**
 * <br>
 * <br>目的：依主鍵查詢單一資料
 * <br>參數：無
 * <br>傳回：傳回本物件
*/

public SYSCA006F  queryOne() throws Exception{
	Database db = new Database();
	SYSCA006F obj = this;
	
	try {
		String sql=" select  a.signno, a.signname, a.signdesc, a.memo, a.editid, a.editdate, a.edittime  "+
			" from SYSCA_SIGN a where 1=1 " +
			" and a.signno = " + Common.sqlChar(signNo) +
			"";
		ResultSet rs = db.querySQL(sql);
		if (rs.next()){
			obj.setCountry(rs.getString("signNo").substring(0,1)+"000000");
			obj.setTown(rs.getString("signNo").substring(0,3)+"0000");
			obj.setSectorNo(rs.getString("signNo").substring(3));
			obj.setSignNo(rs.getString("signNo"));
			obj.setSignName(rs.getString("signName"));
			obj.setSignDesc(rs.getString("signDesc"));
			obj.setMemo(rs.getString("memo"));
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
		String sql=" select a.signno, b.signname as country, c.signname as town, a.signname, a.signdesc, a.memo "+
			" from SYSCA_SIGN a, SYSCA_SIGN b, SYSCA_SIGN c " +
				" where substring(a.signno,1,1) + '000000'=b.signno and substring(a.signno,1,3) + '0000'=c.signno "; 
			String q_signNo = "";
			if (!"".equals(getQ_country())){
				q_signNo=getQ_country().substring(0,1)+"______";
			}
			if (!"".equals(getQ_town())){
				q_signNo=getQ_town().substring(0,3)+"____";	
			}
			if (!"".equals(getQ_sign())){
				if (getQ_sign().length()==4){
					q_signNo="E__" + getQ_sign();
				}else{
					q_signNo=getQ_sign();
				}	
			}
			if (!"".equals(getQ_signDesc())){
					sql+=" and a.signdesc like " + Common.sqlChar("%"+getQ_signDesc()+"%") ;	
			}							
			if (!"".equals(q_signNo)){
				sql+=" and a.signno like '" + q_signNo + "%'";
			}
		ResultSet rs = db.querySQL(sql+" order by a.signno ", true);
		processCurrentPageAttribute(rs);
		if (getTotalRecord() > 0) {
			int count = getRecordStart();
			int end = getRecordEnd();
			do {
				if (count > end)
					break;
				String rowArray[]=new String[5];
				rowArray[0]=Common.get(rs.getString("signNo"));  
				rowArray[1]=rowArray[0].substring(0,1)+"-"+Common.get(rs.getString("country")); 
				rowArray[2]=rowArray[0].substring(1,3)+"-"+Common.get(rs.getString("town"));			
				rowArray[3]=rowArray[0].substring(3)+"-"+Common.get(rs.getString("signName")); 
				rowArray[4]=Common.get(rs.getString("signDesc"));
				count++;
				objList.add(rowArray);				
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


