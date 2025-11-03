/* 
*<br>程式目的：財產編號修正－動產
*<br>程式代號：untdu102f
*<br>程式日期：0990112
*<br>程式作者：Timtoy.Tsai
*<br>--------------------------------------------------------
*<br>修改作者　　修改日期　　　修改目的
*<br>Yuan-Ren	201108		   高雄市市有財產資訊系統100年功能擴充案
*<br>--------------------------------------------------------
*/

package unt.du;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import util.*;

public class UNTDU104F extends QueryBean{


String enterOrg;
String enterOrgName;
String ownership;
String propertyNo;
String propertyNoName;
String serialNo;
String serialNoS;
String serialNoE;
String lotNo;

String q_enterOrg;
String q_enterOrgName;
String q_propertyNo;
String q_propertyNoName;
String q_newPropertyNo;
String q_newPropertyNoName;

public String getEnterOrg(){ return checkGet(enterOrg); }
public void setEnterOrg(String s){ enterOrg=checkSet(s); }
public String getEnterOrgName(){ return checkGet(enterOrgName); }
public void setEnterOrgName(String s){ enterOrgName=checkSet(s); }
public String getOwnership(){ return checkGet(ownership); }
public void setOwnership(String s){ ownership=checkSet(s); }
public String getPropertyNo(){ return checkGet(propertyNo); }
public void setPropertyNo(String s){ propertyNo=checkSet(s); }
public String getPropertyNoName(){ return checkGet(propertyNoName); }
public void setPropertyNoName(String s){ propertyNoName=checkSet(s); }
public String getSerialNo(){ return checkGet(serialNo); }
public void setSerialNo(String s){ serialNo=checkSet(s); }
public String getSerialNoS(){ return checkGet(serialNoS); }
public void setSerialNoS(String s){ serialNoS=checkSet(s); }
public String getSerialNoE(){ return checkGet(serialNoE); }
public void setSerialNoE(String s){ serialNoE=checkSet(s); }
public String getLotNo(){ return checkGet(lotNo); }
public void setLotNo(String s){ lotNo=checkSet(s); }

public String getQ_enterOrg(){ return checkGet(q_enterOrg); }
public void setQ_enterOrg(String s){ q_enterOrg=checkSet(s); }
public String getQ_enterOrgName(){ return checkGet(q_enterOrgName); }
public void setQ_enterOrgName(String s){ q_enterOrgName=checkSet(s); }
public String getQ_propertyNo(){ return checkGet(q_propertyNo); }
public void setQ_propertyNo(String s){ q_propertyNo=checkSet(s); }
public String getQ_propertyNoName(){ return checkGet(q_propertyNoName); }
public void setQ_propertyNoName(String s){ q_propertyNoName=checkSet(s); }
public String getQ_newPropertyNo(){ return checkGet(q_newPropertyNo); }
public void setQ_newPropertyNo(String s){ q_newPropertyNo=checkSet(s); }
public String getQ_newPropertyNoName(){ return checkGet(q_newPropertyNoName); }
public void setQ_newPropertyNoName(String s){ q_newPropertyNoName=checkSet(s); }

String strKeySet[] = null;
public String[] getsKeySet(){ return strKeySet; }
public void setsKeySet(String[] s){ strKeySet=s; }
//String toggleAll;
//public String getToggleAll(){ if (!"".equals(checkGet(toggleAll))) return "checked"; else return "unchecked"; }    
//public void setToggleAll(String s){ toggleAll=checkSet(s); }

public boolean check(){
	boolean flag = false;
	if(getsKeySet()!=null) {
		String[] strKeys = getsKeySet()[0].split(","); 
		if(strKeys[2].substring(0, 1).equals(q_newPropertyNo.substring(0, 1))){
			flag = true;
		}else{
			setErrorMsg("注意：財產編號不可跨類變更！謝謝！");	
		}
	}
	return flag;
}

public void updateBatch() throws Exception{
	Database db = new Database(); 
	Connection conn = null; 
	ResultSet rs = null; 
	Statement stmt = null;
	
	if(getsKeySet()!=null) {
		
		int checkbox_sum = getsKeySet().length;	//前端List區中，有被勾選的資料筆數
//System.out.println("-- untdu104f 前端List區中，有被勾選的資料筆數 --"+checkbox_sum);	
		ArrayList sql_array1 = new ArrayList();

		try {
			conn = db.getConnection(); conn.setAutoCommit(false);
			stmt = conn.createStatement(ResultSet.TYPE_FORWARD_ONLY,ResultSet.CONCUR_READ_ONLY);
			
			for(int checkbox_number=0; checkbox_number<checkbox_sum; checkbox_number++){
								
				get_update_column_value(db, stmt, rs, checkbox_number, sql_array1);
				
			}
			conn.commit();
		} catch (Exception ex) {
			conn.rollback();
			setErrorMsg("更新失敗！");	
			ex.printStackTrace();
		} finally {
			db.closeAll();
		}
	}
}

public void get_update_column_value(Database db, Statement stmt, ResultSet rs, int checkbox_number, ArrayList sql_array1) throws Exception{
	String[] strKeys = getsKeySet()[checkbox_number].split(",");
	
	//==== 計算新財產批號  ============================================================================//
	String newLotNo = null; 
	String get_newLotNo_sql = "select (case when max(lotno) is null then 0 else max(lotno) end) newLotNo " +"\n"+
							  " from UNTMP_MOVABLE where 1=1 " +"\n"+
							  " and enterorg   = " + Common.sqlChar(strKeys[0]) +"\n"+
							  " and ownership  = " + Common.sqlChar(strKeys[1]) +"\n"+
							  " and propertyno = "+ Common.sqlChar(q_newPropertyNo);
	
	try{
		rs = db.querySQL(get_newLotNo_sql);
//System.out.println("---- 計算新財產批號 ----\n"+get_newLotNo_sql);
		if (rs.next()){
				newLotNo = rs.getString("newLotNo");
//System.out.println("新的財產批號："+newLotNo);
			}
		
		if("0".equals(newLotNo)){
			newLotNo = "0000001";
		}else{
			newLotNo = Common.formatFrontZero(newLotNo, 7);
		}
	}catch(Exception ex){
		//System.out.println("---- Exception：計算新財產批號 ----\n"+get_newLotNo_sql);
		ex.printStackTrace();
	}
	
	//==== 計算 Untmp_Movabledetail 每筆資料的使用年限截止年月、可報廢日期、財產分號，並列出與之相關聯的更新SQLs ====//
	String newUseEndYM = ""; String newPermitReduceDate = ""; String newapportionEndYM="";
	int position = -1; //DB中游標初始位置
	
	String get_errorValue_sql = "" +
			" select a.enterorg, a.ownership, a.propertyno, b.serialno, a.serialnos, a.serialnoe, a.originalamount," +"\n"+
			" (select distinct (case when x.limityear is null then a.otherlimityear else x.limityear end) from SYSPK_PROPERTYCODE x where x.enterorg in (a.enterorg,'000000000A') and x.propertyno="+Common.sqlChar(q_newPropertyNo)+") as limitYear, " +"\n"+
			" (case when a.buyDate is null then '0000000' else a.buyDate end) as buyDate, " +"\n"+
			" substring(case when a.buyDate is null then '0000000' else a.buyDate end,1,3) as buyDate_yyy, " +"\n"+
			" substring(case when a.buyDate is null then '0000000' else a.buyDate end,4,2) as buyDate_mm," +"\n"+
			" substring(case when a.buyDate is null then '0000000' else a.buyDate end,4,4) as buyDate_mmdd " +"\n"+
		" from UNTMP_MOVABLE a, UNTMP_MOVABLEDETAIL b where 1=1 " +
			" and a.enterorg=b.enterorg and  a.ownership=b.ownership and a.propertyno=b.propertyno and a.lotno=b.lotno " +"\n"+
			" and a.enterorg = " + Common.sqlChar(strKeys[0]) +"\n"+
			" and a.ownership = " + Common.sqlChar(strKeys[1]) +"\n"+
			" and a.propertyno = " + Common.sqlChar(strKeys[2]) +"\n"+
			" and a.serialnos = " + Common.sqlChar(strKeys[3])+"\n"+
			" and a.serialnoe = " + Common.sqlChar(strKeys[4])+"\n"+
			" and a.lotno = " + Common.sqlChar(strKeys[5]);

//System.out.println("-- untdu104f get_errorValue_sql --\n"+get_errorValue_sql);
	try {
		rs = db.querySQL(get_errorValue_sql);
		while (rs.next()){			
			position++;

			//入帳機關、權屬、新系統財產編號、新系統原財產分號、舊系統財產編號、舊系統財產分號
			this.setEnterOrg(rs.getString("enterOrg"));this.setOwnership(rs.getString("ownership"));
			this.setPropertyNo(rs.getString("propertyNo"));
			this.setSerialNo(rs.getString("serialNo"));this.setSerialNoS(rs.getString("serialNoS"));this.setSerialNoE(rs.getString("serialNoE"));
		
			//使用年限
			int limitYear = ("".equals(Common.get(rs.getString("limitYear"))))? 0 : Integer.parseInt(Common.get(rs.getString("limitYear"))); 
//System.out.println("使用年限："+limitYear); System.out.println("建築日期："+rs.getString("buyDate"));
			//計算使用年限截止年月 // 
			int buyDate_yyy = Integer.parseInt(rs.getString("buyDate_yyy"))+limitYear;
			int buyDate_mm = Integer.parseInt(rs.getString("buyDate_mm"));
			if(buyDate_mm == 1){
				newUseEndYM = Common.formatFrontZero(String.valueOf(buyDate_yyy-1),3)+"12";
			}else{
				newUseEndYM = Common.formatFrontZero(String.valueOf(buyDate_yyy),3)+Common.formatFrontZero(String.valueOf(buyDate_mm-1),2);
			}
			
//System.out.println("使用年限截止年月："+newUseEndYM);
			//計算可報廢日期 // 
			newPermitReduceDate = Common.formatFrontZero(String.valueOf(buyDate_yyy),3)+rs.getString("buyDate_mmdd");
//System.out.println("可報廢日期："+newPermitReduceDate+"\n");
			newapportionEndYM = Common.formatFrontZero(String.valueOf(buyDate_yyy),3)+rs.getString("buyDate_mm");
	
			//計算新的財產分號 // 
			String newSerialNo = ""; 
			String get_newSerialNo_sql = " select (case when max(serialNo) is null then 0 else max(serialNo) end) newSerialNo " +"\n"+
										 " from UNTMP_MOVABLEDETAIL where 1=1 " +"\n"+
										 " and enterorg   = " + Common.sqlChar(enterOrg) +"\n"+
										 " and ownership  = " + Common.sqlChar(ownership) +"\n"+
										 " and propertyno = "+ Common.sqlChar(q_newPropertyNo);

			rs = db.querySQL(get_newSerialNo_sql); 
			if (rs.next()){
				
				newSerialNo = rs.getString("newSerialNo");
				
//System.out.println("新的財產分號："+newSerialNo);
			}
			if("0".equals(newSerialNo)){
	        	newSerialNo = "0000001";
	        }else{
	        	newSerialNo = Common.formatFrontZero(newSerialNo, 7);
	        }
			
			Database db1=new Database();
			ResultSet rs1;	
			boolean limitYearFlag=false;
			boolean propertyUnitFlag=false;
			boolean materialFlag=false;
			try{
				rs1=db1.querySQL(" select limityear "+
						" from SYSPK_PROPERTYCODE "+
						" where enterorg in ('000000000A', "+ Common.sqlChar(enterOrg) +")"+
						" and propertytype='1' "+
						" and substring(propertyno,1,1) in ('3','4','5') "+
						" and propertyno=" +Common.sqlChar(q_newPropertyNo) );
				
				if(rs1.next()){						
					limitYearFlag=true;					
				}		
				rs1.close();
				
				rs1=db1.querySQL(" select propertyunit "+
						" from SYSPK_PROPERTYCODE "+
						" where enterorg in ('000000000A', "+ Common.sqlChar(enterOrg) +")"+
						" and propertytype='1' "+
						" and substring(propertyno,1,1) in ('3','4','5') "+
						" and propertyno=" +Common.sqlChar(q_newPropertyNo) );
				
				if(rs1.next()){						
					propertyUnitFlag=true;					
				}		
				rs1.close();
				
				rs1=db1.querySQL(" select material "+
						" from SYSPK_PROPERTYCODE "+
						" where enterorg in ('000000000A', "+ Common.sqlChar(enterOrg) +")"+
						" and propertytype='1' "+
						" and substring(propertyno,1,1) in ('3','4','5') "+
						" and propertyno=" +Common.sqlChar(q_newPropertyNo) );
				
				if(rs1.next()){						
					materialFlag=true;					
				}		
				rs1.close();
			}catch(Exception e){
				e.getStackTrace();
			}finally{
				db1.closeAll();
			}
			
			//SQLs from sql_array1//
			getUpdateSQL(checkbox_number, sql_array1, serialNo, newUseEndYM, newPermitReduceDate, newapportionEndYM, newLotNo, newSerialNo, limitYearFlag ,propertyUnitFlag ,materialFlag);
		}
		
	}catch(Exception ex){
//		System.out.println("---- Exception：計算 Untmp_Movabledetail 每筆資料的使用年限截止年月、可報廢日期、財產分號，並列出與之相關聯的更新SQLs ----");
		ex.printStackTrace();
	}
	
	//==== 計算 Untmp_Movable 每筆資料的財產分號起訖，執行 sql_array1 、sql_array2 資料的SQLs ====================================================//
//	String[] sql_array2 = { " select lpad((max(b.serialNo)-"+position+"),7,'0') as newSerialNoS, max(b.serialNo) as newSerialNoE  " +"\n"+
//							" from Untmp_Movabledetail b where 1=1 " +"\n"+
	
	String[] sql_array2 = { " select (case when max(b.serialnoe) is null then 0 else max(b.serialnoe) end) as newSerialNoS " +"\n"+	
							" from UNTMP_MOVABLE b where 1=1 " +"\n"+
							" and b.enterorg = " + Common.sqlChar(enterOrg) +"\n"+
							" and b.ownership = " + Common.sqlChar(ownership) +"\n"+
							" and b.propertyno = " + Common.sqlChar(q_newPropertyNo)};

	Database db2 = null;
	try{		
		this.excuteSQL(db, stmt, rs, sql_array1, sql_array2, newLotNo);
		db.doCommit();
		
		ResultSet rs2;
		
		String sqlTemp="";
		int itemp=0;
		
		sqlTemp = " select a.originalamount " +"\n"+
				" from UNTMP_MOVABLE a where 1=1 " +			
					" and a.enterorg = " + Common.sqlChar(strKeys[0]) +"\n"+
					" and a.ownership = " + Common.sqlChar(strKeys[1]) +"\n"+
					" and a.propertyno = " + Common.sqlChar(strKeys[2]) +"\n"+
					" and a.serialnos = " + Common.sqlChar(strKeys[3])+"\n"+
					" and a.serialnoe = " + Common.sqlChar(strKeys[4])+"\n"+
					" and a.lotno = " + Common.sqlChar(strKeys[5]);						

			db2 = new Database();
			try{
				rs2 = db2.querySQL(sqlTemp);
		
				if(rs2.next()){			
					itemp=Integer.parseInt(rs2.getString("originalamount"));
				}
				rs2.close();
			}catch(Exception e){
				e.printStackTrace();
			}
			
		if(itemp>1){
			String sql="";
			int serialNoTemp=0;
			db.doCommit();
			
			sqlTemp = " select substring(case when max(a.serialno) is null then 0 else max(a.serialno) end)+10000001,2) serialNo" +"\n"+
						" from UNTMP_MOVABLEDETAIL a where 1=1 " +			
						" and a.enterorg = " + Common.sqlChar(strKeys[0]) +"\n"+
						" and a.ownership = " + Common.sqlChar(strKeys[1]) +"\n"+
						" and a.propertyno = " + Common.sqlChar(q_newPropertyNo);
						
				rs2 = db2.querySQL(sqlTemp);	
				rs2.next();
				serialNoTemp=Integer.parseInt(rs2.getString("serialno"));				
				rs2.close();
				
			for(int i=1;i<itemp;i++){

					sql="update UNTMP_MOVABLEDETAIL set " +"\n"+
							   " propertyno = "+Common.sqlChar(q_newPropertyNo)+ "," +"\n"+
							   " lotno = "+Common.sqlChar(newLotNo)+ "," +"\n"+
							   " serialno = "+Common.sqlChar( Common.formatFrontZero(String.valueOf(serialNoTemp+i-1),7) )+ "," +"\n"+
							   " oldpropertyno = "+Common.sqlChar(strKeys[2])+ "," +"\n"+
							   " oldserialno = "+Common.sqlChar( Common.formatFrontZero(String.valueOf(Integer.parseInt(strKeys[3])+i),7) )+"\n"+
							" where 1=1 " +
							 " and enterorg = " + Common.sqlChar(strKeys[0]) +"\n"+
							   " and ownership = " + Common.sqlChar(strKeys[1]) +"\n"+
						       " and propertyno = " + Common.sqlChar(strKeys[2]) +"\n"+
							   " and serialno = " + Common.sqlChar( Common.formatFrontZero(String.valueOf(Integer.parseInt(strKeys[3])+i),7) )+"\n"+
							   "";

					rs2 = db2.queryUpdateSQL(sql);
					rs2.close();
			}
					
			db2.doCommit();
			
			sql="update UNTMP_MOVABLE set " +"\n"+
					" serialnos = "+Common.sqlChar( Common.formatFrontZero(String.valueOf(serialNoTemp-1),7) )+ "," +"\n"+
					" serialnoe = "+Common.sqlChar( Common.formatFrontZero(String.valueOf(serialNoTemp+(itemp-2)),7) )+"\n"+								 
					" where 1=1 " +
					" and enterorg = " + Common.sqlChar(strKeys[0]) +"\n"+
					" and ownership = " + Common.sqlChar(strKeys[1]) +"\n"+
					" and propertyno = " + Common.sqlChar(q_newPropertyNo) +"\n"+		
					" and lotno = "+Common.sqlChar(newLotNo)+
				   "";
			
			rs2 = db2.queryUpdateSQL(sql);
			rs2.close();
		
		}
		
		setStateUpdateSuccess();
		setErrorMsg("更新完成");	
	}catch(Exception ex){
//		System.out.println("---- Exception：計算 Untmp_Movable 每筆資料的財產分號起訖，執行 sql_array1 、sql_array2 資料的SQLs ----");
		ex.printStackTrace();
		throw new Exception(ex);
	}finally{
		db2.closeAll();
	}
			
	position = -1; sql_array1.clear(); sql_array2 = null; newLotNo = null;
	

}


protected void getUpdateSQL( int checkBoxNumber, ArrayList sql_array,
							 String serialNo,
							 String newUseEndYM, 
							 String newPermitReduceDate, 
							 String newapportionEndYM,
							 String newLotNo,
							 String newSerialNo,
							 boolean limitYearFlag,
							 boolean propertyUnitFlag,
							 boolean materialFlag )
{
	String[] strKeys = getsKeySet()[checkBoxNumber].split(",");	
	
	/*
	 * 更新限用年度limitYear資料	 
	 * */
	
	if(limitYearFlag){	
		sql_array.add("update UNTMP_MOVABLE set " +"\n"+
				   " otherlimityear = "+null+"\n"+
				   " where 1=1 " +"\n"+
				   " and enterorg = " + Common.sqlChar(strKeys[0]) +"\n"+
				   " and ownership = " + Common.sqlChar(strKeys[1]) +"\n"+
			       " and propertyno = " + Common.sqlChar(strKeys[2]) +"\n"+
			       " and serialnos = " + Common.sqlChar(serialNo)+"\n"+
				   "");				
		
		sql_array.add("update UNTMP_MOVEDETAIL set " +"\n"+
				   " otherlimityear = "+null+"\n"+
				   " where 1=1 " +"\n"+
				   " and enterorg = " + Common.sqlChar(strKeys[0]) +"\n"+
				   " and ownership = " + Common.sqlChar(strKeys[1]) +"\n"+
			       " and propertyno = " + Common.sqlChar(strKeys[2]) +"\n"+
			       " and serialno = " + Common.sqlChar(serialNo)+"\n"+
				   "");	
		
		sql_array.add("update UNTMP_REDUCEDETAIL set " +"\n"+
				   " otherlimityear = "+null+"\n"+
				   " where 1=1 " +"\n"+
				   " and enterorg = " + Common.sqlChar(strKeys[0]) +"\n"+
				   " and ownership = " + Common.sqlChar(strKeys[1]) +"\n"+
			       " and propertyno = " + Common.sqlChar(strKeys[2]) +"\n"+
			       " and serialno = " + Common.sqlChar(serialNo)+"\n"+
				   "");
		
		sql_array.add("update UNTMP_DEALDETAIL set " +"\n"+
				   " otherlimityear = "+null+"\n"+
				   " where 1=1 " +"\n"+
				   " and enterorg = " + Common.sqlChar(strKeys[0]) +"\n"+
				   " and ownership = " + Common.sqlChar(strKeys[1]) +"\n"+
			       " and propertyno = " + Common.sqlChar(strKeys[2]) +"\n"+
			       " and serialno = " + Common.sqlChar(serialNo)+"\n"+
				   "");
		
		sql_array.add("update UNTMP_ADJUSTDETAIL set " +"\n"+
				   " otherlimityear = "+null+"\n"+
				   " where 1=1 " +"\n"+
				   " and enterorg = " + Common.sqlChar(strKeys[0]) +"\n"+
				   " and ownership = " + Common.sqlChar(strKeys[1]) +"\n"+
			       " and propertyno = " + Common.sqlChar(strKeys[2]) +"\n"+
				   " and serialno = " + Common.sqlChar(serialNo)+"\n"+
				   "");
	
	}
	
	/*
	 * 更新單位propertyUnit資料	 
	 * */
	
	if(propertyUnitFlag){	
		sql_array.add("update UNTMP_MOVABLE set " +"\n"+
				   " otherpropertyunit = "+null+"\n"+
				   " where 1=1 " +"\n"+
				   " and enterorg = " + Common.sqlChar(strKeys[0]) +"\n"+
				   " and ownership = " + Common.sqlChar(strKeys[1]) +"\n"+
			       " and propertyno = " + Common.sqlChar(strKeys[2]) +"\n"+
			       " and serialnos = " + Common.sqlChar(serialNo)+"\n"+
				   "");				
		
		sql_array.add("update UNTMP_MOVEDETAIL set " +"\n"+
				   " otherpropertyunit = "+null+"\n"+
				   " where 1=1 " +"\n"+
				   " and enterorg = " + Common.sqlChar(strKeys[0]) +"\n"+
				   " and ownership = " + Common.sqlChar(strKeys[1]) +"\n"+
			       " and propertyno = " + Common.sqlChar(strKeys[2]) +"\n"+
			       " and serialno = " + Common.sqlChar(serialNo)+"\n"+
				   "");	
		
		sql_array.add("update UNTMP_REDUCEDETAIL set " +"\n"+
				   " otherpropertyunit = "+null+"\n"+
				   " where 1=1 " +"\n"+
				   " and enterorg = " + Common.sqlChar(strKeys[0]) +"\n"+
				   " and ownership = " + Common.sqlChar(strKeys[1]) +"\n"+
			       " and propertyno = " + Common.sqlChar(strKeys[2]) +"\n"+
			       " and serialno = " + Common.sqlChar(serialNo)+"\n"+
				   "");
		
		sql_array.add("update UNTMP_DEALDETAIL set " +"\n"+
				   " otherpropertyunit = "+null+"\n"+
				   " where 1=1 " +"\n"+
				   " and enterorg = " + Common.sqlChar(strKeys[0]) +"\n"+
				   " and ownership = " + Common.sqlChar(strKeys[1]) +"\n"+
			       " and propertyno = " + Common.sqlChar(strKeys[2]) +"\n"+
			       " and serialno = " + Common.sqlChar(serialNo)+"\n"+
				   "");
		
		sql_array.add("update UNTMP_ADJUSTDETAIL set " +"\n"+
				   " otherpropertyunit = "+null+"\n"+
				   " where 1=1 " +"\n"+
				   " and enterorg = " + Common.sqlChar(strKeys[0]) +"\n"+
				   " and ownership = " + Common.sqlChar(strKeys[1]) +"\n"+
			       " and propertyno = " + Common.sqlChar(strKeys[2]) +"\n"+
				   " and serialno = " + Common.sqlChar(serialNo)+"\n"+
				   "");
	
	}
	
	/*
	 * 更新材質material 
	 * */
	
	if(materialFlag){	
		sql_array.add("update UNTMP_MOVABLE set " +"\n"+
				   " othermaterial = "+null+"\n"+
				   " where 1=1 " +"\n"+
				   " and enterorg = " + Common.sqlChar(strKeys[0]) +"\n"+
				   " and ownership = " + Common.sqlChar(strKeys[1]) +"\n"+
			       " and propertyno = " + Common.sqlChar(strKeys[2]) +"\n"+
			       " and serialnos = " + Common.sqlChar(serialNo)+"\n"+
				   "");				
		
		sql_array.add("update UNTMP_MOVEDETAIL set " +"\n"+
				   " othermaterial = "+null+"\n"+
				   " where 1=1 " +"\n"+
				   " and enterorg = " + Common.sqlChar(strKeys[0]) +"\n"+
				   " and ownership = " + Common.sqlChar(strKeys[1]) +"\n"+
			       " and propertyno = " + Common.sqlChar(strKeys[2]) +"\n"+
			       " and serialno = " + Common.sqlChar(serialNo)+"\n"+
				   "");	
		
		sql_array.add("update UNTMP_REDUCEDETAIL set " +"\n"+
				   " othermaterial = "+null+"\n"+
				   " where 1=1 " +"\n"+
				   " and enterorg = " + Common.sqlChar(strKeys[0]) +"\n"+
				   " and ownership = " + Common.sqlChar(strKeys[1]) +"\n"+
			       " and propertyno = " + Common.sqlChar(strKeys[2]) +"\n"+
			       " and serialno = " + Common.sqlChar(serialNo)+"\n"+
				   "");
		
		sql_array.add("update UNTMP_DEALDETAIL set " +"\n"+
				   " othermaterial = "+null+"\n"+
				   " where 1=1 " +"\n"+
				   " and enterorg = " + Common.sqlChar(strKeys[0]) +"\n"+
				   " and ownership = " + Common.sqlChar(strKeys[1]) +"\n"+
			       " and propertyno = " + Common.sqlChar(strKeys[2]) +"\n"+
			       " and serialno = " + Common.sqlChar(serialNo)+"\n"+
				   "");
		
		sql_array.add("update UNTMP_ADJUSTDETAIL set " +"\n"+
				   " othermaterial = "+null+"\n"+
				   " where 1=1 " +"\n"+
				   " and enterorg = " + Common.sqlChar(strKeys[0]) +"\n"+
				   " and ownership = " + Common.sqlChar(strKeys[1]) +"\n"+
			       " and propertyno = " + Common.sqlChar(strKeys[2]) +"\n"+
				   " and serialno = " + Common.sqlChar(serialNo)+"\n"+
				   "");
	
	}
	
	
	/*
	 * 更新「使用年限截止年月」資料
	 * */
//	//動產主檔－批號資料UNTMP_Movable
//	sql_array.add("update UNTMP_MOVABLE set " +"\n"+
//				   " useendym = "+Common.sqlChar(newUseEndYM)+"\n"+
//				" where 1=1 " +"\n"+
//				   " and enterorg = " + Common.sqlChar(strKeys[0]) +"\n"+
//				   " and ownership = " + Common.sqlChar(strKeys[1]) +"\n"+
//			       " and propertyno = " + Common.sqlChar(strKeys[2]) +"\n"+
//				   " and lotno = " + Common.sqlChar(strKeys[5])+"\n"+
//				   "");
//	
//	//動產減損單明細檔UNTMP_ReduceDetail
//	sql_array.add("update UNTMP_REDUCEDETAIL set " +"\n"+
//				   " useendym = "+Common.sqlChar(newUseEndYM)+"\n"+
//				" where 1=1 " +"\n"+
//				   " and enterorg = " + Common.sqlChar(strKeys[0]) +"\n"+
//				   " and ownership = " + Common.sqlChar(strKeys[1]) +"\n"+
//			       " and propertyno = " + Common.sqlChar(strKeys[2]) +"\n"+
//				   " and serialno = " + Common.sqlChar(serialNo)+"\n"+
//				   "");
//	
//	//動產廢品處理單明細檔UNTMP_DealDetail
//	sql_array.add("update UNTMP_DEALDETAIL set " +"\n"+
//				   " useendym = "+Common.sqlChar(newUseEndYM)+"\n"+
//				" where 1=1 " +"\n"+
//				 " and enterorg = " + Common.sqlChar(strKeys[0]) +"\n"+
//				   " and ownership = " + Common.sqlChar(strKeys[1]) +"\n"+
//			       " and propertyno = " + Common.sqlChar(strKeys[2]) +"\n"+
//				   " and serialno = " + Common.sqlChar(serialNo)+"\n"+
//				   "");
	
	
	/*
	 * 更新「可報廢日期」資料
	 * */
//	//動產主檔－批號資料UNTMP_Movable
//	sql_array.add("update UNTMP_MOVABLE set " +"\n"+
//				   " permitreducedate = "+Common.sqlChar(newPermitReduceDate)+"\n"+
//				" where 1=1 " +"\n"+
//				   " and enterorg = " + Common.sqlChar(strKeys[0]) +"\n"+
//				   " and ownership = " + Common.sqlChar(strKeys[1]) +"\n"+
//			       " and propertyno = " + Common.sqlChar(strKeys[2]) +"\n"+
//				   " and lotno = " + Common.sqlChar(strKeys[5])+"\n"+
//				   "");
//	
//	//動產減損單明細檔UNTMP_ReduceDetail
//	sql_array.add("update UNTMP_REDUCEDETAIL set " +"\n"+
//				   " permitreducedate = "+Common.sqlChar(newPermitReduceDate)+"\n"+
//				" where 1=1 " +"\n"+
//				 " and enterorg = " + Common.sqlChar(strKeys[0]) +"\n"+
//				   " and ownership = " + Common.sqlChar(strKeys[1]) +"\n"+
//			       " and propertyno = " + Common.sqlChar(strKeys[2]) +"\n"+
//				   " and serialno = " + Common.sqlChar(serialNo)+"\n"+
//				   "");
//	
//	//動產廢品處理單明細檔UNTMP_DealDetail
//	sql_array.add("update UNTMP_DEALDETAIL set " +"\n"+
//					   " permitreducedate = "+Common.sqlChar(newPermitReduceDate)+"\n"+
//					" where 1=1 " +"\n"+
//					 " and enterorg = " + Common.sqlChar(strKeys[0]) +"\n"+
//					   " and ownership = " + Common.sqlChar(strKeys[1]) +"\n"+
//				       " and propertyno = " + Common.sqlChar(strKeys[2]) +"\n"+
//					   " and serialno = " + Common.sqlChar(serialNo)+"\n"+
//					   "");
//
//	/*
//	 * 更新「攤提年限截止年月」資料
//	 * */
//	//動產主檔－批號資料UNTMP_Movable
//	sql_array.add("update UNTMP_MOVABLE set " +"\n"+
//					   " apportionendym = "+Common.sqlChar(newapportionEndYM)+"\n"+
//					" where 1=1 " +"\n"+
//					   " and enterorg = " + Common.sqlChar(strKeys[0]) +"\n"+
//					   " and ownership = " + Common.sqlChar(strKeys[1]) +"\n"+
//				       " and propertyno = " + Common.sqlChar(strKeys[2]) +"\n"+
//					   " and lotno = " + Common.sqlChar(strKeys[5])+"\n"+
//					   "");
//	
//	//動產減損單明細檔UNTMP_ReduceDetail
//	sql_array.add("update UNTMP_REDUCEDETAIL x set " +"\n"+
//					   " x.apportionendym = "+Common.sqlChar(newapportionEndYM)+"\n"+
//					" where 1=1 " +"\n"+
//					 " and x.enterorg = " + Common.sqlChar(strKeys[0]) +"\n"+
//					   " and x.ownership = " + Common.sqlChar(strKeys[1]) +"\n"+
//				       " and x.propertyno = " + Common.sqlChar(strKeys[2]) +"\n"+
//					   " and x.serialno = " + Common.sqlChar(serialNo)+"\n"+
//					   "");
//	
//	//動產廢品處理單明細檔UNTMP_DealDetail
//	sql_array.add("update UNTMP_DEALDETAIL x set " +"\n"+
//					   " x.apportionendym = "+Common.sqlChar(newapportionEndYM)+"\n"+
//					" where 1=1 " +"\n"+
//					 " and x.enterorg = " + Common.sqlChar(strKeys[0]) +"\n"+
//					   " and x.ownership = " + Common.sqlChar(strKeys[1]) +"\n"+
//				       " and x.propertyno = " + Common.sqlChar(strKeys[2]) +"\n"+
//					   " and x.serialno = " + Common.sqlChar(serialNo)+"\n"+
//					   "");
//	
//	/*
//	 * 更新「財產編號、財產批號」資料
//	 * 動產批號附屬設備檔UNTMP_Attachment1
//	 * */
//	sql_array.add("update UNTMP_ATTACHMENT1 x set " +"\n"+
//					   " x.propertyno = "+Common.sqlChar(q_newPropertyNo)+ "," +"\n"+
//					   " x.lotno = "+Common.sqlChar(newLotNo)+"\n"+
//					" where 1=1 " +"\n"+
//					   " and x.enterorg = " + Common.sqlChar(strKeys[0]) +"\n"+
//					   " and x.ownership = " + Common.sqlChar(strKeys[1]) +"\n"+
//				       " and x.propertyno = " + Common.sqlChar(strKeys[2]) +"\n"+
//					   " and x.lotno = " + Common.sqlChar(strKeys[5])+"\n"+
//					   "");
	
	
	/*
	 * 更新「財產編號、財產批號、財產分號、原財產編號、原財產分號」資料
	 * */
	//動產主檔－批號明細UNTMP_MovableDetail
				
	sql_array.add("update UNTMP_MOVABLEDETAIL set " +"\n"+
			   " propertyno = "+Common.sqlChar(q_newPropertyNo)+ "," +"\n"+
			   " lotno = "+Common.sqlChar(newLotNo)+ "," +"\n"+
			   " serialno = "+Common.sqlChar(newSerialNo )+ "," +"\n"+
			   " oldpropertyno = "+Common.sqlChar(strKeys[2])+ "," +"\n"+
			   " oldserialno = "+Common.sqlChar(strKeys[3])+"\n"+
			" where 1=1 " +
			 " and enterorg = " + Common.sqlChar(strKeys[0]) +"\n"+
			   " and ownership = " + Common.sqlChar(strKeys[1]) +"\n"+
		       " and propertyno = " + Common.sqlChar(strKeys[2]) +"\n"+
//			   " and x.serialno = " + Common.sqlChar(serialNo)+"\n"+
			   " and serialno = " + Common.sqlChar(strKeys[3])+"\n"+
			   "");					
				
	//動產增減值單明細檔UNTMP_AdjustDetail
	sql_array.add("update UNTMP_ADJUSTDETAIL set " +"\n"+
					   " propertyno = "+Common.sqlChar(q_newPropertyNo)+ "," +"\n"+
					   " lotno = "+Common.sqlChar(newLotNo)+ "," +"\n"+
					   " serialno = "+Common.sqlChar(newSerialNo)+ "," +"\n"+
					   " oldpropertyno = "+Common.sqlChar(strKeys[2])+ "," +"\n"+
					   " oldserialno = "+Common.sqlChar(strKeys[3])+"\n"+
					" where 1=1 " +
					 " and enterorg = " + Common.sqlChar(strKeys[0]) +"\n"+
					   " and ownership = " + Common.sqlChar(strKeys[1]) +"\n"+
				       " and propertyno = " + Common.sqlChar(strKeys[2]) +"\n"+
					   " and serialno = " + Common.sqlChar(serialNo)+"\n"+
					   "");
	
	//動產減損單明細檔UNTMP_ReduceDetail
	sql_array.add("update UNTMP_REDUCEDETAIL set " +"\n"+
					   " propertyno = "+Common.sqlChar(q_newPropertyNo)+ "," +"\n"+
					   " lotno = "+Common.sqlChar(newLotNo)+ "," +"\n"+
					   " serialno = "+Common.sqlChar(newSerialNo)+ "," +"\n"+
					   " oldpropertyno = "+Common.sqlChar(strKeys[2])+ "," +"\n"+
					   " oldserialno = "+Common.sqlChar(strKeys[3])+"\n"+
					" where 1=1 " +
					 " and enterorg = " + Common.sqlChar(strKeys[0]) +"\n"+
					   " and ownership = " + Common.sqlChar(strKeys[1]) +"\n"+
				       " and propertyno = " + Common.sqlChar(strKeys[2]) +"\n"+
					   " and serialno = " + Common.sqlChar(serialNo)+"\n"+
					   "");
	
	//動產移動單明細檔UNTMP_MoveDetail
	sql_array.add("update UNTMP_MOVEDETAIL set " +"\n"+
					   " propertyno = "+Common.sqlChar(q_newPropertyNo)+ "," +"\n"+
					   " lotno = "+Common.sqlChar(newLotNo)+ "," +"\n"+
					   " serialno = "+Common.sqlChar(newSerialNo)+ "," +"\n"+
					   " oldpropertyno = "+Common.sqlChar(strKeys[2])+ "," +"\n"+
					   " oldserialno = "+Common.sqlChar(strKeys[3])+"\n"+
					" where 1=1 " +"\n"+
					 " and enterorg = " + Common.sqlChar(strKeys[0]) +"\n"+
					   " and ownership = " + Common.sqlChar(strKeys[1]) +"\n"+
				       " and propertyno = " + Common.sqlChar(strKeys[2]) +"\n"+
					   " and serialno = " + Common.sqlChar(serialNo)+"\n"+
					   "");
	
	//動產廢品處理單明細檔UNTMP_DealDetail
	sql_array.add("update UNTMP_DEALDETAIL set " +"\n"+
					   " propertyno = "+Common.sqlChar(q_newPropertyNo)+ "," +"\n"+
					   " lotno = "+Common.sqlChar(newLotNo)+ "," +"\n"+
					   " serialno = "+Common.sqlChar(newSerialNo)+ "," +"\n"+
					   " oldpropertyno = "+Common.sqlChar(strKeys[2])+ "," +"\n"+
					   " oldserialno = "+Common.sqlChar(strKeys[3])+"\n"+
					" where 1=1 " +"\n"+
					   " and enterorg = " + Common.sqlChar(strKeys[0]) +"\n"+
					   " and ownership = " + Common.sqlChar(strKeys[1]) +"\n"+
					   " and propertyno = " + Common.sqlChar(strKeys[2]) +"\n"+
					   " and serialno = " + Common.sqlChar(serialNo)+"\n"+
					   "");

	
	/*
	 * 更新「財產編號、財產批號、財產分號」資料
	 * 動產批號明細附屬設備檔UNTMP_Attachment2
	 * */
	sql_array.add("update UNTMP_ATTACHMENT2 set " +"\n"+
					   " propertyno = "+Common.sqlChar(q_newPropertyNo)+ "," +"\n"+
					   " lotno = "+Common.sqlChar(newLotNo)+ "," +"\n"+
					   " serialno = "+Common.sqlChar(newSerialNo)+"\n"+
					" where 1=1 " +"\n"+
					   " and enterorg = " + Common.sqlChar(strKeys[0]) +"\n"+
					   " and ownership = " + Common.sqlChar(strKeys[1]) +"\n"+
					   " and propertyno = " + Common.sqlChar(strKeys[2]) +"\n"+
					   " and serialno = " + Common.sqlChar(serialNo)+"\n"+
					   "");
	
	/*
	 * 更新「財產編號、財產批號、原財產編號、原財產分號－起、原財產分號－訖」資料
	 * 動產主檔_批號資料 UNTMP_Movable
	 * */
	sql_array.add("update UNTMP_MOVABLE set " +"\n"+
					   " propertyno = "+Common.sqlChar(q_newPropertyNo)+ "," +"\n"+
					   " lotno = "+Common.sqlChar(newLotNo)+ "," +"\n"+
					   " oldpropertyno = "+Common.sqlChar(strKeys[2])+ "," +"\n"+
					   " oldserialnos = "+Common.sqlChar(strKeys[3])+ "," +"\n"+
					   " oldserialnoe = "+Common.sqlChar(strKeys[4])+"\n"+								
					" where 1=1 " +"\n"+
					   " and enterorg = " + Common.sqlChar(strKeys[0]) +"\n"+
					   " and ownership = " + Common.sqlChar(strKeys[1]) +"\n"+
				       " and propertyno = " + Common.sqlChar(strKeys[2]) +"\n"+
					   " and lotno = " + Common.sqlChar(strKeys[5])+"\n"+
					   "");
	
	/*
	 * 將異動資料紀錄至異動檔「財產編號修正記錄UNTDU_PropertyNo」
	 * */
	sql_array.add("insert into UNTDU_PROPERTYNO ( " +"\n"+
							   "propertytype,"+"\n"+
							   "enterorg,"+"\n"+
							   "ownership,"+"\n"+
							   "oldpropertyno1,"+"\n"+
							   "oldserialno1,"+"\n"+
							   "oldlotno,"+"\n"+
							   "oldserialnos1,"+"\n"+
							   "oldserialnoe1,"+"\n"+
							   "newpropertyno,"+"\n"+
							   "newserialno,"+"\n"+
							   "newlotno,"+"\n"+
							   "editid,"+"\n"+
							   "editdate,"+"\n"+
							   "edittime"+"\n"+
							" ) values ( " +"\n"+
								Common.sqlChar("4") + "," +"\n"+
								Common.sqlChar(strKeys[0]) + "," +"\n"+
								Common.sqlChar(strKeys[1]) + "," +"\n"+
								Common.sqlChar(strKeys[2]) + "," +"\n"+
								Common.sqlChar(serialNo) + "," +"\n"+
								Common.sqlChar(strKeys[5]) + "," +"\n"+ 
								Common.sqlChar(strKeys[3]) + "," +"\n"+ 
								Common.sqlChar(strKeys[4]) + "," +"\n"+ 
								Common.sqlChar(q_newPropertyNo) + "," +"\n"+
								Common.sqlChar(newSerialNo) + "," +"\n"+
								Common.sqlChar(newLotNo) + "," +"\n"+ 
								Common.sqlChar(getUserID()) + "," +"\n"+
								Common.sqlChar(util.Datetime.getYYYMMDD()) + "," +"\n"+
								Common.sqlChar(util.Datetime.getHHMMSS()) + ")" );
}


/**
	 * <br>
	 * <br>目的：最終執行sql　
	*/
public void excuteSQL(Database db, Statement stmt, ResultSet rs, ArrayList sql1, String[] sql2, String newLotNo) throws Exception{
	//========== 執行查詢新的財產分號之起訖 =============================//
	String newSerialNoS = ""; String newSerialNoE = ""; 
	try {
		rs = db.querySQL(sql2[0]);
			String serialnotemp = ""; 

			if(rs.next()){
				serialnotemp = Common.get(rs.getString("newSerialNoS"));				
								
//System.out.println("---- 執行查詢新的財產分號之起訖  ----"+newSerialNoS +" # "+newSerialNoE);
			}
			
			if("0".equals(serialnotemp)){
				newSerialNoS = "0000001";				
				newSerialNoE = "0000001";
			}else{
				newSerialNoS = Common.get(rs.getString("newSerialNoS"));				
				newSerialNoE = Common.get(rs.getString("newSerialNoS"));
			}
	}catch(Exception ex){
//		System.out.println("---- Exception：執行查詢新的財產分號之起訖  ----\n"+newSerialNoS +" # "+newSerialNoE);
		throw new Exception(ex);
	}
	
	//========== 執行更新第一階段SQLs ================================//
	for (int i = 0; i < sql1.size(); i++){   
		try {
			stmt.executeUpdate((String) sql1.get(i));

//System.out.println("---- 執行更新第一階段SQLs "+i+"/"+(sql1.size()-1)+" ----\n"+(String) sql1.get(i)+"\n");
		}catch(Exception ex){
//			System.out.println("---- Exception：執行更新第一階段SQLs "+i+"/"+(sql1.size()-1)+" ----\n"+(String) sql1.get(i));
			throw new Exception(ex);
		}
	} 
	
	//========== 執行更新第二階段SQLs ================================//
	String[] sql3 = new String[2];
	sql3[0] = "update UNTMP_MOVABLE set " +"\n"+
				  " serialnos  = "+Common.sqlChar(newSerialNoS)+ "," +"\n"+
				  " serialnoe = "+Common.sqlChar(newSerialNoE)+"\n"+
			 " where 1=1 " +"\n"+
				  " and enterorg = " + Common.sqlChar(enterOrg) +"\n"+
				  " and ownership = " + Common.sqlChar(ownership) +"\n"+
				  " and propertyno = " + Common.sqlChar(q_newPropertyNo) +"\n"+
				  " and lotno = " + Common.sqlChar(newLotNo);
	sql3[1] = "update UNTDU_PROPERTYNO set " +"\n"+
				  " newserialnos  = "+Common.sqlChar(newSerialNoS)+ "," +"\n"+
				  " newserialnoe = "+Common.sqlChar(newSerialNoE)+"\n"+
			 " where 1=1 " +"\n"+
				  " and enterorg = " + Common.sqlChar(enterOrg) +"\n"+
				  " and ownership = " + Common.sqlChar(ownership) +"\n"+
				  " and newpropertyno = " + Common.sqlChar(q_newPropertyNo) +"\n"+
				  " and newlotno = " + Common.sqlChar(newLotNo);

	for (int i = 0; i < sql3.length ; i++){  
		try{					

			stmt.executeUpdate(sql3[i]);			
//System.out.println("---- 執行更新第二階段SQLs ----\n"+sql3[i]+"\n");
		}catch(Exception ex){
//			System.out.println("---- Exception：執行更新第二階段SQLs ----\n"+sql3[i]);
			throw new Exception(ex);
		}
	} 
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
		String sql=" select a.enterorg, " +"\n"+
				   " ( select x.organaname from SYSCA_ORGAN x where x.organid = a.enterorg ) as enterOrgName, " +"\n"+
				   " ( select z.codename from sysca_code z where 1=1 and (z.codekindid='owa' or z.codekindid='OWA') and z.codeid=a.ownership ) as  ownershipName, " +
				   " a.ownership, a.propertyno, a.serialnos, a.serialnoe, lotno," +"\n"+
				   " (select x.propertyname from SYSPK_PROPERTYCODE x where x.enterorg in ('000000000A',"+Common.sqlChar(getQ_enterOrg())+") and x.propertytype='1' and x.propertyno = a.propertyno) as propertyNoName " +"\n"+
			       " from UNTMP_MOVABLE a where 1=1 "+"\n";
			       
			       // 查詢後的資料需排除「舊系統財產編號」合乎規定的資料；1:合乎規定,0:不合乎規定
			       //" and (select count(*) from SYSPK_PropertyCode x where x.enterOrg in ('000000000A') and x.propertyType='1' and substr(x.propertyno,1,3)='111' and x.propertyno = a.propertyno) = 0 "+"\n";
		
					if (!"".equals(getQ_enterOrg()))
						sql+=" and a.enterorg = " + Common.sqlChar(getQ_enterOrg()) ;
					if (!"".equals(getQ_propertyNo()))
						sql+=" and a.propertyno = " + Common.sqlChar(getQ_propertyNo()) ;			
//			if (!"".equals(getQ_serialNo()))
//				sql+=" and a.serialNo = " + Common.sqlChar(Common.formatFrontZero(getQ_serialNo(),7)) ;
			sql+=" order by enterorg, ownership, propertyno, a.serialnos, a.serialnoe ";	
//System.out.println("-- untdu104f queryAll --\n"+sql);

		ResultSet rs = db.querySQL(sql,true);
		processCurrentPageAttribute(rs);
		if (getTotalRecord() > 0) {
			int count = getRecordStart();
			int end = getRecordEnd();
			do {
				if (count > end)
					break;
				String rowArray[]=new String[9];
				rowArray[0]=Common.get(rs.getString("enterOrg")); 
				rowArray[1]=Common.get(rs.getString("enterOrgName")); 
				rowArray[2]=Common.get(rs.getString("ownershipName")); 
				rowArray[3]=Common.get(rs.getString("ownership")); 
				rowArray[4]=Common.get(rs.getString("propertyNo")); 
				rowArray[5]=Common.get(rs.getString("propertyNoName"));
				rowArray[6]=Common.get(rs.getString("serialNoS")); 
				rowArray[7]=Common.get(rs.getString("serialNoE")); 
				rowArray[8]=Common.get(rs.getString("lotNo"));  
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


/**
 * <br> 
 * <br>目的：組合查詢列表之html
 * <br>參數：(1)主鍵之index (2)顯示之index (3)列表集合 (4)是否有查詢旗標
 * <br>傳回：傳回列表之html字串
*/
public String getQuerylist(boolean primaryArray[], boolean displayArray[], ArrayList objList, String queryAllFlag) {
	int i;
	int counter=0;
	boolean trFlag = false;
	StringBuffer rtnStr = new StringBuffer();    
	StringBuffer sbQueryOne = new StringBuffer("");
	    	
	if ("true".equals(queryAllFlag) && objList.size()==0){
		rtnStr.append(" <tr class='listTR' ><td class='listTD' colspan='100' style='color:red'>查無資料，請您重新輸入查詢條件！</td></tr>");
		//rtnStr.append(" <tr class='listTR' ><td class='listTD' colspan='100'>&nbsp;</td></tr>");
	}else{
		String rowArray[]=new String[primaryArray.length];
		String isCheck = "unchecked";
		java.util.Iterator it=objList.iterator();
		while(it.hasNext()){			
			rowArray= (String[])it.next();
			counter++;
			//顯示TR
			rtnStr.append(" <tr class='listTR' onmouseover=\"this.className='listTRMouseover'\" onmouseout=\"this.className='listTR'\" onClick=\"queryOne(");
			
			for(i=0;i<primaryArray.length;i++){
				if (primaryArray[i]){
					if(trFlag){
						rtnStr.append(",'").append(rowArray[i]).append("'");
					}else{
						rtnStr.append("'").append(rowArray[i]).append("'");
						trFlag = true;
					}						
				}
			}

			rtnStr.append(")\" >\n");

			//顯示TD
			rtnStr.append(" <td class='listTD' >").append(counter).append(".</td>\n");
			rtnStr.append(" <td class='listTD' ><input type='checkbox' class='field_Q' name='sKeySet' value=\""+rowArray[0]+","+rowArray[3]+","+rowArray[4]+","+rowArray[6]+","+rowArray[7]+","+rowArray[8]+"\" onclick=\"Toggle(this, document.form1, 'sKeySet');\"" + isCheck + "></td>\n");
			for(i=0;i<displayArray.length;i++){
				if (displayArray[i]){
					rtnStr.append(" <td class='listTD' >").append(Common.get(rowArray[i])).append("</td>\n");
				}
			}				
			rtnStr.append("</tr>\n");
			trFlag = false;
		}
	}				
	return rtnStr.append(sbQueryOne).toString();     
}    


}
