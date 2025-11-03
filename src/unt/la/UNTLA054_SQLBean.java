/*
 * 2012.12.04
 * 
 * 可自動產出經常使用的SQL語句
 * 提供 Insert、 Update 及 Delete 三種方法的基本型SQL
 * ========================================================================
 * 需求
 * 		每次執行函式前, 需先將值重新寫入Map, 再傳遞給函式
 * ========================================================================
 * 函式：
 * 		getSQLMethod_Insert()
 * 		getSQLMethod_Update()
 * 		getSQLMethod_Delete()
 * 
 * ========================================================================
 */

package unt.la;

import org.apache.log4j.Logger;
import java.util.Iterator;
import java.lang.reflect.Method;
import util.*;

import java.sql.*;
import java.beans.PropertyDescriptor;

public class UNTLA054_SQLBean extends QueryBean{

	public java.util.Map primarykeyMap=new java.util.HashMap();
	public java.util.Map columnMap=new java.util.HashMap();
	
	String tableName;
	public String getTableName() {return checkGet(tableName);}
	public void setTableName(String tableName) {this.tableName = checkSet(tableName);}
	
	//************************************************************************
	//							    主要產出函式
	//************************************************************************
	
	//產生Insert的SQL語法
	public String getSQLMethod_Insert(UNTLA054_SQLBean sqlbean){
		String sql=null;
		try{
//		    checkPrimaryKeyMapIsNull(sqlbean.primarykeyMap);
		
			StringBuffer strbuf01=new StringBuffer();
			StringBuffer strbuf02=new StringBuffer();
			String nextKey, nextValue;
			
			strbuf01.append("INSERT INTO ").append(sqlbean.tableName).append(" (");
			strbuf02.append(")VALUES(");
			
			java.util.Iterator iter=sqlbean.columnMap.keySet().iterator();
			while(iter.hasNext()){
				nextKey = iter.next().toString();
				nextValue = Common.get(sqlbean.columnMap.get(nextKey).toString());
				
				strbuf01.append(nextKey).append(",");			

				if("".equals(Common.get(nextValue))){	strbuf02.append(" null,");
				}else{						strbuf02.append("'").append(nextValue).append("',");}
			}	
			
			sql = strbuf01.toString();
			sql = sql.substring(0, sql.length()-1);
			
			sql += strbuf02.toString();
			sql = sql.substring(0, sql.length()-1);
			
			sql += ")";
			
		}catch(Exception e){
			LogBean.outputLogError("Exception : "+e.getMessage()+"\n"+sql);
		}finally{
			clearMapData(sqlbean);
		}
		
		return sql;
	}
	
	//產生Update的SQL語法
	public String getSQLMethod_Update(UNTLA054_SQLBean sqlbean){
		String sql=null;
		try{
//		    checkPrimaryKeyMapIsNull(sqlbean.primarykeyMap);
		
			StringBuffer strbuf01=new StringBuffer();
			StringBuffer strbuf02=new StringBuffer();
			String nextKey, nextValue;
			
			strbuf01.append("UPDATE ").append(sqlbean.tableName).append(" SET ");
			strbuf02.append(" WHERE 1=1");
			
			java.util.Iterator iter=sqlbean.columnMap.keySet().iterator();
			while(iter.hasNext()){
				nextKey = iter.next().toString();
				strbuf01.append(nextKey).append(" = ");
				
				nextValue = Common.get(sqlbean.columnMap.get(nextKey).toString());			
				if("".equals(Common.get(nextValue))){	strbuf01.append(" null,");
				}else{						strbuf01.append("'").append(nextValue).append("',");}
			}
			
			iter=sqlbean.primarykeyMap.keySet().iterator();
			while(iter.hasNext()){
				nextKey = iter.next().toString();
				nextValue = Common.get(sqlbean.primarykeyMap.get(nextKey).toString());
				
				strbuf02.append(" AND ").append(nextKey).append(" = '").append(nextValue).append("'");
			}
			
			sql = strbuf01.toString();
			sql = sql.substring(0, sql.length()-1);
			sql += strbuf02.toString();
			
		}catch(Exception e){
			LogBean.outputLogError("Exception : "+e.getMessage()+"\n"+sql);
		}finally{
			clearMapData(sqlbean);
		}
		return sql;
	}

	//產生Delete的SQL語法
	public String getSQLMethod_Delete(UNTLA054_SQLBean sqlbean){
		String sql=null;
		try{
//		    checkPrimaryKeyMapIsNull(sqlbean.primarykeyMap);
		
			StringBuffer strbuf=new StringBuffer();
			String nextKey, nextValue;
			
			strbuf.append("DELETE FROM ").append(sqlbean.tableName).append(" WHERE 1=1 ");
			
			java.util.Iterator it2=sqlbean.primarykeyMap.keySet().iterator();
			while(it2.hasNext()){
				nextKey = it2.next().toString();
				nextValue = Common.get(sqlbean.primarykeyMap.get(nextKey).toString());
				
				strbuf.append(" AND ").append(nextKey).append(" = '").append(nextValue).append("'");
			}
			sql = strbuf.toString();
			
		}catch(Exception e){
			LogBean.outputLogError("Exception : "+e.getMessage()+"\n"+sql);			
		}finally{
			clearMapData(sqlbean);
		}
		return sql;
	}
	
	//************************************************************************
	//							        其他函式
	//************************************************************************
	
	private void checkPrimaryKeyMapIsNull(java.util.Map map) throws Exception{
		java.util.Iterator iter = map.keySet().iterator();
		String nextKey=null;
		String str=null;
		
		while(iter.hasNext()){
			nextKey = iter.next().toString();
			str = map.get(nextKey).toString();			
			if("".equals(Common.get(str))){				
				LogBean.outputLogError("PrimaryKeyMap欄位設定錯誤！！ \n" + nextKey + " : " + str);
				throw new Exception();
			}
		}
	}

	private void clearMapData(UNTLA054_SQLBean sqlbean){
		sqlbean.primarykeyMap.clear();
		sqlbean.columnMap.clear();
	}

	

	private void isNull_tableName() throws Exception{
		if("".equals(Common.get(getTableName()))){	
			LogBean.outputLogError("tableName 尚未設定！！");					
			throw new Exception();		
		}
	}
		
}



