/*
*<br>程式目的：土地合併分割重測重劃作業－案件資料
*<br>程式代號：untla054f
*<br>程式日期：
*<br>程式作者：Yuan-Ren Jheng
*<br>--------------------------------------------------------
*<br>修改作者　　修改日期　　　修改目的
*<br>--------------------------------------------------------
*/

package unt.la;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.LinkedHashMap;
import java.util.Map;

import unt.ch.UNTCH_Tools;
import util.Common;
import util.Database;
import util.Datetime;
import util.QueryBean;

public class UNTLA054_ServerBean extends QueryBean{
	
	UNTLA054_SQLBean sqlbean = new UNTLA054_SQLBean();	
	protected void setSQLBeanValue(){}
	
	UNTLA054Q_data qbean = new UNTLA054Q_data();
	protected UNTLA054Q_data getParameterData(){return qbean;}
	
	public String getNewCaseNoFromDB(UNTLA054Q_data qbean){
		String resultStr="";
		
		String sql="select case when max(caseno) is null then '' else (max(caseno) + 1) end as CASENO from " + qbean.tableName + " where 1=1"+
					" and enterorg = " + Common.sqlChar(qbean.enterOrg) +
					" and caseno like " + Common.sqlChar(Datetime.getYYY() + "%") +
					"";
		
		resultStr=getNameData("caseno", sql);
		
		if("".equals(Common.get(resultStr))){
			resultStr = Datetime.getYYY() + "0000001";
		}else{
			resultStr = Datetime.getYYY() + Common.formatFrontZero(resultStr.substring(3),7);
		}	
		
		return resultStr;
	}
	
	public String getNewProofNoFromDB(UNTLA054Q_data qbean){
		String resultStr="";
		
		String sql="select (max(proofNo) + 1) as proofNo from " + qbean.tableName + " where 1=1"+
					" and enterorg = "+Common.sqlChar(qbean.enterOrg)+
					" and ownership = "+Common.sqlChar(qbean.ownership)+
					" and differenceKind = "+Common.sqlChar(qbean.differenceKind)+
					" and proofNo like " + Common.sqlChar(Datetime.getYYYMM() + "%") +
					"";
	
		resultStr=getNameData("proofno", sql);
		
		if("".equals(checkGet(resultStr))){
			resultStr = Datetime.getYYYMM() + "00001";
		}
		return resultStr;
	}
		
	public String getNewSerialNoFromDB(UNTLA054Q_data qbean){
		String resultStr="";
		
		String sql="select (max(serialNo) + 1) as serialNo from " + qbean.tableName + " where 1=1"+
					" and enterorg = "+Common.sqlChar(qbean.enterOrg)+
					" and differenceKind = "+Common.sqlChar(qbean.differenceKind)+
					" and ownership = "+Common.sqlChar(qbean.ownership)+
					" and propertyNo = " + Common.sqlChar(qbean.propertyNo)+
					"";
	
		resultStr=getNameData("serialno", sql);
		
		if("".equals(checkGet(resultStr))){
			resultStr = "0000001";
		}
		return resultStr;
	}
	
	public String getEnterOrgNameFromDB(String str){
		String resultStr="";
		
		String sql="select organsname from sysca_organ where 1=1"+
					" and organID = "+Common.sqlChar(str);
			
		resultStr=getNameData("organsname", sql);
		return resultStr;
	}
	public String getEnterOrgNameFromDB(UNTLA054Q_data qbean){
		String resultStr="";
		
		String sql="select organsname from sysca_organ where 1=1"+
					" and organID = "+Common.sqlChar(qbean.enterOrg);
			
		resultStr=getNameData("organsname", sql);
		return resultStr;
	}
	
	public String getPropertyNoNameFromDB(UNTLA054Q_data qbean){
		String resultStr="";
				
		String sql="SELECT PROPERTYNAME FROM SYSPK_PROPERTYCODE WHERE 1=1"+
					" AND PROPERTYNO = " + Common.sqlChar(qbean.propertyNo);
	
		resultStr=getNameData("propertyname", sql);
		return resultStr;
	}
	
	public String getSignNoNameFromDB(String signNo){
		String resultStr="";
		
		String sql="SELECT SIGNNAME from SYSCA_Sign WHERE 1=1"+
					" AND SIGNNO = " + Common.sqlChar(signNo);
	
		resultStr=getNameData("signname", sql);
		return resultStr;
	}
	
	public String getDefaultProofDoc(String enterOrg,String docNo){
		Database db = new Database();
		ResultSet rs;	
		String docName ="" ;
		String sql="select docName from UNTMP_doc " +
			" where enterOrg = " + Common.sqlChar(enterOrg) +
			"   and docNo=" + Common.sqlChar(docNo) +
			"";		

		try {		
			LogBean.outputLogDebug(sql);
			
			rs = db.querySQL(sql);
			if (rs.next()){
			    docName = rs.getString("docName");
			}
			rs.getStatement().close();
			rs.close();
		} catch (Exception e) {
			LogBean.outputLogError("Exception : "+e.getMessage()+"\n"+sql);
		} finally {
			db.closeAll();
		}
		return docName;
	}


	public String getNewSerialNo1FromDB(UNTLA054Q_data qbean){
		String resultStr="";
		
		String sql="select (max(serialNo1) + 1) as serialNo1 from " + qbean.tableName + " where 1=1"+
					" and enterorg = "+Common.sqlChar(qbean.enterOrg)+
					" and ownership = "+Common.sqlChar(qbean.ownership)+
					" and differenceKind = "+Common.sqlChar(qbean.differenceKind)+
					" and propertyNo = " + Common.sqlChar(qbean.propertyNo)+
					" and serialNo = " + Common.sqlChar(qbean.serialNo)+
					"";
	
		resultStr=getNameData("serialno1", sql);
		
		if("".equals(checkGet(resultStr))){
			resultStr = "001";
		}
		return resultStr;
	}
	
//=====================================================================================	
	
	private Map execSQLToDB_ForSingleColumn(String sql){
		Database db = new Database();
		ResultSet rs;
		Map map=new LinkedHashMap();
		
		try{
			LogBean.outputLogDebug(sql);
			
			rs=db.querySQL(sql);
			ResultSetMetaData rsmd = rs.getMetaData();
			while(rs.next()){
				for(int i=1;i<=rsmd.getColumnCount();i++){
					map.put(rsmd.getColumnName(i), rs.getString(i));
				}
			}
		}catch(Exception e){
			LogBean.outputLogError("Exception : "+e.getMessage()+"\n"+sql);
			
		}finally{
			db.closeAll();
		}	
		return map;
	}
	private Map execSQLToDB_ForSingleColumn_NoChange(String sql){
		Database db = new Database();
		ResultSet rs;
		Map map=new LinkedHashMap();
		
		try{
			LogBean.outputLogDebug(sql);
			
			rs=db.querySQL_NoChange(sql);
			ResultSetMetaData rsmd = rs.getMetaData();
			while(rs.next()){
				for(int i=1;i<=rsmd.getColumnCount();i++){
					map.put(rsmd.getColumnName(i), rs.getString(i));
				}
			}
		}catch(Exception e){
			LogBean.outputLogError("Exception : "+e.getMessage()+"\n"+sql);
			
		}finally{
			db.closeAll();
		}	
		return map;
	}
	
	public String getNameData(String NameCode, String sql){
		String resultStr="";
		Map map;
		try{			
			map=execSQLToDB_ForSingleColumn(sql);
			resultStr=map.get(NameCode).toString();
			map.clear();			
		}catch(Exception e){
			
		}finally{
			map=null;
		}
		return resultStr;
	}
	
	public String getNameData_NoChange(String NameCode, String sql){
		String resultStr="";
		Map map;
		try{
			map=execSQLToDB_ForSingleColumn_NoChange(sql);
			resultStr=map.get(NameCode).toString();
			map.clear();			
		}catch(Exception e){
			
		}finally{
			map=null;
		}
		return resultStr;
	}

	public void execSQL_ForNoResult(String sql){
		Database db=new Database();
		try{
			LogBean.outputLogDebug(sql);
			db.exeSQL_NoChange(sql);
		}catch(Exception e){
			LogBean.outputLogError("Exception : "+e.getMessage()+"\n"+sql);
		}finally{
			db.closeAll();
		}
	}
	
	public void execSQL_ForNoResult_NoChange(String sql){
		Database db=new Database();
		try{
			LogBean.outputLogDebug(sql);
			db.exeSQL_NoChange(sql);	
		}catch(Exception e){
			LogBean.outputLogError("Exception : "+e.getMessage()+"\n"+sql);
		}finally{
			db.closeAll();
		}
	}
	
	public void execSQL_ForNoResult(String sql, boolean NoLog){
		Database db=new Database();
		try{
			LogBean.outputLogDebug(sql);
			db.exeSQL(sql);
		}catch(Exception e){
			if(NoLog){
				
			}else{
				LogBean.outputLogError("Exception : "+e.getMessage()+"\n"+sql);
			}
		}finally{
			db.closeAll();
		}
	}
	
	public void execSQL_ForNoResult_NoChange(String sql, boolean NoLog){
		Database db=new Database();
		try{
			LogBean.outputLogDebug(sql);
			db.exeSQL_NoChange(sql);
		}catch(Exception e){
			if(NoLog){
				
			}else{
				LogBean.outputLogError("Exception : "+e.getMessage()+"\n"+sql);
			}
		}finally{
			db.closeAll();
		}
	}
	
	//檢查入帳日期必須是最大月結年月＋１
	public boolean checkClosingYMfromUNTAC_CLOSINGPT(String enterdate, String enterOrg, String differenceKind){
		boolean result = false;
		
		String closing1ym = queryClosingYMfromUNTAC_CLOSINGPT(enterOrg,differenceKind);
		if("".equals(checkGet(closing1ym))){			
		}else{
			if("".equals(checkGet(enterdate))){			
			}else{
				String checkDate = enterdate.substring(0,5);
				int maxdate = Integer.parseInt(new UNTCH_Tools()._transToCE_Year(checkDate));
				
				String yyyy = closing1ym.substring(0,4);
				String mm = closing1ym.substring(4);
				
				int i_mm = Integer.parseInt(mm);
				String tempStr = null;
				
				i_mm++;
				if(i_mm > 12){
					tempStr = Common.formatFrontZero(String.valueOf(Integer.parseInt(yyyy) + 1),4) + Common.formatFrontZero(String.valueOf(i_mm - 12),2);
				}else{
					tempStr = yyyy + Common.formatFrontZero(String.valueOf(i_mm),2);
				}
				
				if(maxdate != (Integer.parseInt(tempStr))){	result = true;}
			}
		}
		return result;
	}
	
	// 檢查入帳日期不可小於購置日期、財產取得日期
	public boolean checkBuyDate_sourceDate(String enterdate, String enterorg, String caseno){
		boolean result = false;
		String sql = "select "
				 + " max(buydate) as buydate,"
				 + " max(sourcedate) as sourcedate"
				 + " from ("
				 	+ " select isnull(buydate,0) as buydate,isnull(sourcedate,0) as sourcedate from UNTLA_LAND"
					+ " where 1=1"
					+ " and enterorg = " + Common.sqlChar(enterorg)
					+ " and caseno = " + Common.sqlChar(caseno)
				 + ") a";
		
		Database db = null;
		ResultSet rs = null;
		int buydate = 0;
		int sourcedate = 0;
		try{
			db = new Database();
			rs = db.querySQL(sql);
			while(rs.next()){
				buydate = rs.getInt("buydate");
				sourcedate = rs.getInt("sourcedate");
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			db.closeAll();
		}
		
		int maxdate = Integer.parseInt(new UNTCH_Tools()._transToCE_Year(enterdate));		
		if(maxdate < buydate || maxdate < sourcedate){
			result = true;
		}
		
		return result;
	}
	
	//檢查是否可回復入帳
	public boolean checkCanNotReVerify(String enterdate, String enterOrg, String differenceKind){
		boolean result = false;
		String closing1ym = queryClosingYMfromUNTAC_CLOSINGPT(enterOrg,differenceKind);
		if("".equals(checkGet(closing1ym))){			
		}else{
			
			if("".equals(checkGet(enterdate))){			
			}else{
				String checkDate = enterdate.substring(0,5);
				int maxdate = Integer.parseInt(new UNTCH_Tools()._transToCE_Year(checkDate));
				
				if(maxdate <= (Integer.parseInt(closing1ym))){	result = true;}
			}	
		}
		
		return result;
	}
	
	// 回復入帳時檢查 不可存在於 增減值單/減損單/移動單
	public boolean checkInOtherProof(String enterorg, String caseno){
		boolean result = false;
		String sql = " select count(*) as checkResult from UNTLA_LAND a "
				  + " where a.enterorg = " + Common.sqlChar(enterorg) 
				  + " and a.caseno = " + Common.sqlChar(caseno) 
				  + " and ( "
					  + " exists(select 1 from UNTLA_ADJUSTDETAIL b where a.enterorg=b.enterorg and a.ownership=b.ownership and a.differencekind=b.differencekind and a.propertyno=b.propertyno and a.serialno=b.serialno)"
					  + " or exists(select 1 from UNTLA_REDUCEDETAIL b where a.enterorg=b.enterorg and a.ownership=b.ownership and a.differencekind=b.differencekind and a.propertyno=b.propertyno and a.serialno=b.serialno) "
					  + " or exists(select 1 from UNTMP_MOVEDETAIL b where a.enterorg=b.enterorg and a.ownership=b.ownership and a.differencekind=b.differencekind and a.propertyno=b.propertyno and a.serialno=b.serialno) "
				  + ")";
		
		String checkResult = Common.get(queryDatafromDatabase(sql));
		
		if(!"0".equals(checkResult)){
			result = true;
		}		
		return result;
	}
	
	protected String queryClosingYMfromUNTAC_CLOSINGPT(String enterOrg, String differenceKind){
		String closing1ym = null;
		String sql = "select closing1ym from UNTAC_CLOSINGPT where 1=1" +
						" and enterorg = " + Common.sqlChar(enterOrg) +
						" and differencekind = " + Common.sqlChar(differenceKind);
		
		closing1ym = queryDatafromDatabase(sql);
		
		return closing1ym;
	}
	
	protected String queryDatafromDatabase(String sql){
		String result = null;			
		Database db = null;
		ResultSet rs = null;
		try{
			db = new Database();
			rs = db.querySQL_NoChange(sql);
			while(rs.next()){
				result = rs.getString(1);
			}
			rs.close();
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			db.closeAll();
		}
		return result;
	}
}


