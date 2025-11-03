/*
*<br>程式目的：動產產生盤點資料
*<br>程式代號：
*<br>程式日期：
*<br>程式作者：shan
*<br>--------------------------------------------------------
*<br>修改作者　　修改日期　　　修改目的
*<br>--------------------------------------------------------
*/

package unt.pd;

import java.sql.ResultSet;
import java.util.ArrayList;
import util.*;

public class UNTPD022F extends SuperBean{
String enterOrg;
String enterOrgName;
public String getEnterOrg(){ return checkGet(enterOrg); }
public void setEnterOrg(String s){ enterOrg=checkSet(s); }
public String getEnterOrgName(){ return checkGet(enterOrgName); }
public void setEnterOrgName(String s){ enterOrgName=checkSet(s); }

String checkDateS;
String checkDateE;
public String getCheckDateS(){return checkGet(checkDateS);}
public void setCheckDateS(String s){ checkDateS=checkSet(s); }
public String getCheckDateE(){return checkGet(checkDateE);}
public void setCheckDateE(String s){ checkDateE=checkSet(s); }

public void upDataPdresult(){
	upPdresult_Date();		//更新所有盤點資料日期
	upDataPdoddscause();	//更新主檔 異常原因有資料者
	setErrorMsg("更新主檔完成");
	setState("upDataPdresultSuccess");
}

//更新所有盤點資料日期
public void upPdresult_Date(){
	Database db = new Database();
	String[] updatesql = new String[3];
	try{
		updatesql[0] = " update untne_nonexpdetail m set " + "\n"
		   			 + " m.checkdates = " + Common.sqlChar(checkDateS) + "\n"
		   			 + " ,m.checkdatee = " + Common.sqlChar(checkDateE) + "\n"
		   			 + " where 1=1 " + "\n"
		   			 + " and exists(select p.barcode from untpd_checknonexp p " + "\n"
		   			 + " where 1=1 and p.enterorg=m.enterorg " + "\n"
		   			 + " and p.barcode=m.barcode ) " + "\n"
		   			 + " and m.enterorg = " + Common.sqlChar(enterOrg) + "\n"
		   			 + " and m.datastate = '1' " + "\n"
		   			 + "" ;
					 //System.out.println("date"+updatesql[0]);
		updatesql[1] = " update untne_nonexpdetail m set " + "\n"
			   		 + " m.checkresult = '1' " + "\n"
			   		 + " where 1=1 " + "\n"
			   		 + " and exists(select p.barcode from untpd_checknonexp p " + "\n"
			   		 + " where 1=1 and p.enterorg=m.enterorg " + "\n"
			   		 + " and p.barcode=m.barcode and p.checkresult='1') " + "\n"
			   		 + " and m.enterorg = " + Common.sqlChar(enterOrg) + "\n"
			   		 + " and m.datastate = '1' " + "\n"
			   		 + "" ;
		 			 //System.out.println("1"+updatesql[1]);
		updatesql[2] = " update untne_nonexpdetail m set " + "\n"
  		 			 + " m.checkresult = '2' " + "\n"
  		 			 + " where 1=1 " + "\n"
  		 			 + " and exists(select p.barcode from untpd_checknonexp p " + "\n"
  		 			 + " where 1=1 and p.enterorg=m.enterorg " + "\n"
  		 			 + " and p.barcode=m.barcode and p.checkresult='2') " + "\n"
  		 			 + " and m.enterorg = " + Common.sqlChar(enterOrg) + "\n"
  		 			 + " and m.datastate = '1' " + "\n"
  		 			 + "" ;
		 	  	 	 //System.out.println("2"+updatesql[2]);
					 db.excuteSQL(updatesql);
	} catch (Exception e) {
		e.printStackTrace();
	} finally {
		db.closeAll();
	}
}

//更新主檔 異常原因有資料者
public void upDataPdoddscause(){
	String sql = " select p.enterorg ,p.barcode ,p.oddscause from untpd_checknonexp p " + "\n"
			   + " where 1=1 " + "\n"
			   + " and p.enterorg is not null " + "\n"
			   + " and p.barcode is not null " + "\n"
			   + " and p.oddscause is not null " + "\n"
			   + "";
			   //System.out.println(sql);
	Database db = new Database();
	String[] updatesql = new String[1];
	ResultSet rs;
	
	try{
		rs = db.querySQL(sql);
		while(rs.next()){
			updatesql[0] = " update untne_nonexpdetail m set " 
						 + " m.oddscause = " + Common.sqlChar(rs.getString("oddscause"))
						 + " where 1=1 " 
						 + " and m.enterorg = " + Common.sqlChar(rs.getString("enterorg"))
						 + " and m.barcode = " + Common.sqlChar(rs.getString("barcode"))
						 + "";

			db.excuteSQL(updatesql);
		}//while(rs.next())
	} catch (Exception e) {
		e.printStackTrace();
	} finally {
		db.closeAll();
	}
}

//取得單位名稱
public String getOrgName(){
	String sql="select organSName from SYSCA_organ where " +
	" organID = " + Common.sqlChar(enterOrg) + 
	"";	
	String selorgName = "";
	Database db = new Database();
	ResultSet rs;
	//System.out.println(sql);
	try{
		rs = db.querySQL(sql);
		if (rs.next()){
			selorgName=rs.getString("organsname");
		}
	} catch (Exception e) {
		e.printStackTrace();
	} finally {
		db.closeAll();
	}
	return selorgName;
}//取得單位名稱
}//public class UNTPD022F extends QueryBean


