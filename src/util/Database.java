/*
*<br>程式目的：資料庫共用函數
*<br>程式代號：Database
*<br>撰寫日期：93/12/01
*<br>程式作者：griffin
*<br>--------------------------------------------------------
*<br>修改作者　　修改日期　　　修改目的
*<br>--------------------------------------------------------
*<br>
*/
package util;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Iterator;

import TDlib_Simple.com.DatabaseForCom;
import util.bo.NamedParameterStatement;

public class Database implements DatabaseForCom{
	
	Connection conn = null;
	Statement stmt = null;
	ResultSet rs=null;		
	
	public Database()   { 
		try {
			conn = DriverManager.getConnection("proxool.hcsa_microsoft");
			//本機連線測試單支程式用
//			conn = DriverManager.getConnection("jdbc:jtds:sqlserver://127.0.0.1:1433/TCLP","sa","1234");
		} catch (SQLException e) {
			e.printStackTrace();
		}		
	}	

	/**
	 * <br>
	 * <br>目的：取得Connection
	 * <br>參數：無
	 * <br>傳回：傳回Connection
	*/
	public Connection getConnection() {
		if (conn!=null) {
			return conn;
		} else {
			try {
				//DriverManager.registerDriver(new org.logicalcobwebs.proxool.ProxoolDriver());
				conn = DriverManager.getConnection("proxool.hcsa_microsoft");
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return conn;
		}
	}

	/**
	 * <br>
	 * <br>目的：取得Forward Only Statement
	 * <br>參數：Connection
	 * <br>傳回：傳回Statement
	*/
	public Statement getForwardStatement(Connection conn) {
		try {
			if (conn==null) conn = getConnection();
			stmt = conn.createStatement(ResultSet.TYPE_FORWARD_ONLY,ResultSet.CONCUR_READ_ONLY);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return stmt;
	}
	
	public Statement getForwardStatement() {
		try {
			if (conn==null) conn = getConnection();
			//if (stmt!=null) stmt.close();
			stmt = conn.createStatement(ResultSet.TYPE_FORWARD_ONLY,ResultSet.CONCUR_READ_ONLY);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return stmt;
	}	

	/**
	 * <br>
	 * <br>目的：取得Scroll Statement
	 * <br>參數：Connection
	 * <br>傳回：傳回Statement
	*/
	public Statement getScrollStatement(Connection conn){
		try {
			if (conn==null) conn = getConnection();
			//if (stmt!=null) stmt.close();
			stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return stmt;		
	}

	public Statement getScrollStatement(){
		try {
			if (conn==null) conn = getConnection();
			stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return stmt;		
	}
	
	/**
  	 * <br>
  	 * <br>目的：取得資料集的裡的欄位名稱　
  	 * <br>參數：ResultSet
  	 * <br>傳回：欄位名稱集合
  	*/	
	public String[] getColumnNames(ResultSet rs) throws SQLException{
		   ResultSetMetaData md = rs.getMetaData();
		   String[] colNames = new String[md.getColumnCount()];
		   for(int i = 0; i < colNames.length; i++){
		      String nomColonne = md.getColumnName(i+1);
		      colNames[i] = nomColonne;
		   }
		   return colNames;
	}		
	
	/**
  	 * <br>
  	 * <br>目的：執行查詢sql　
  	 * <br>參數：無
  	 * <br>傳回：傳回ResultSet
  	*/
	public ResultSet querySQL(String sql) throws Exception{
		return querySQL(sql,false,true);
	}
	public ResultSet querySQL_NoChange(String sql) throws Exception{
		return querySQL(sql,false,false);
	}
	public ResultSet querySQL_NoChange(String sql,boolean isChange) throws Exception{
		return querySQL(sql,false,isChange);
	}
	public ResultSet querySQL(String sql, boolean s, boolean isChange) throws Exception{
		if(isChange){
			sql = _transSQLFormat(sql);
		}
//		System.out.println("執行SQL: "+sql);
		return getForwardStatement().executeQuery(sql);		
	}
	public ResultSet querySQL(String sql, java.util.Map<String,Object> h) throws Exception{
		if (conn==null) conn = getConnection();		
		if(h != null && h.size()>0) {
			String hql = sql.replace("@", ":");			
			NamedParameterStatement pstmt = new NamedParameterStatement(conn, hql);
			for (String idx : h.keySet()) {
				if (hql.indexOf(":"+idx)!=-1) {
					Object value = h.get(idx);
					if(value instanceof String){
						pstmt.setString(idx, Common.get(value));
					} else if(value instanceof Integer){
						pstmt.setInt(idx, Common.getInt(value));
					} else if(value instanceof Long){
						pstmt.setLong(idx, Common.getLong(value));
					} else if(value instanceof Number){
						pstmt.setDouble(idx, Common.getNumeric(value));
					} else if(value instanceof Date){
						pstmt.setDate(idx, (Date) value);
					} else if(value instanceof Timestamp){
						pstmt.setTimestamp(idx, (Timestamp) value);
					} else if(value instanceof Time){
						pstmt.setTime(idx, (Time) value);
					} else {
						pstmt.setObject(idx, value);
						//throw new Exception("PreparedStatement type is not correct");
					}					
				}
			}
			return pstmt.executeQuery();
		} else {
			PreparedStatement pstmt = conn.prepareStatement(sql,ResultSet.TYPE_FORWARD_ONLY,ResultSet.CONCUR_READ_ONLY);
			return pstmt.executeQuery();					
		}
	}	
	
	public Object save(String tableName, java.util.Map<String,Object> h) throws SQLException {
		if(tableName!=null && tableName.length()>0 && h != null && h.size()>0) {
			if (conn==null) conn = getConnection();			
			StringBuilder sb = new StringBuilder();            
            StringBuilder vb = new StringBuilder();
            sb.append(" insert into ").append(tableName).append("(");
            vb.append(" values(");
            boolean sFlag = false;
            for (String idx : h.keySet()) {
                if (sFlag)
                {
                    sb.append(",");
                    vb.append(",");
                }
                else
                {
                    sFlag = true;
                }
                sb.append(idx);
                vb.append(":").append(idx);
            }
            sb.append(")").append(vb).append(")");            
			NamedParameterStatement pstmt = new NamedParameterStatement(conn, sb.toString(), true);
			for (String idx : h.keySet()) {
				Object value = h.get(idx);
				if(value instanceof String){
					pstmt.setString(idx, Common.get(value));
				} else if(value instanceof Integer){
					pstmt.setInt(idx, Common.getInt(value));
				} else if(value instanceof Long){
					pstmt.setLong(idx, Common.getLong(value));
				} else if(value instanceof Number){
					pstmt.setDouble(idx, Common.getNumeric(value));
				} else if(value instanceof Date){
					pstmt.setDate(idx, (Date) value);
				} else if(value instanceof Timestamp){
					pstmt.setTimestamp(idx, (Timestamp) value);
				} else if(value instanceof Time){
					pstmt.setTime(idx, (Time) value);
				} else {
					pstmt.setObject(idx, value);
					//throw new Exception("PreparedStatement type is not correct");
				}
			}
			Object id = null;
			int effectRow = pstmt.executeUpdate();
			if (effectRow>0) {
				java.sql.ResultSet rs = pstmt.getStatement().getGeneratedKeys();
				while (rs.next()) {
					id = rs.getObject(1);
				}
				rs.close();
			}
			pstmt.close();
			return id;	
		}
		return null;
	}
	
	public int update(String tableName, java.util.Map<String,Object> h, java.util.Map<String,Object> whereCondition) throws SQLException {
		if(tableName!=null && tableName.length()>0 && h != null && h.size()>0) {
			if (conn==null) conn = getConnection();			
			StringBuilder sb = new StringBuilder();            
            boolean sFlag = false;
			sb.append(" update ").append(tableName).append(" set ");
			if (h != null && h.size() > 0)
			{
				
				for (String idx : h.keySet())
				{
					if (sFlag)
					{
						sb.append(",");
					}
					else
					{
						sFlag = true;
					}
					sb.append(idx).append("=:").append(idx);
				}
			}
            if (whereCondition != null && whereCondition.size() > 0)
            {
                sFlag = false;
                sb.append(" where ");
                for (String idx : whereCondition.keySet())
                {
                    if (sFlag)
                    {
                        sb.append(" and ");
                    }
                    else
                    {
                        sFlag = true;
                    }
                    sb.append(idx).append("=:").append(idx);
                }
                java.util.Map<String,Object> m = new java.util.HashMap<String,Object>();
                m.putAll(h);
                m.putAll(whereCondition);
                NamedParameterStatement pstmt = new NamedParameterStatement(conn, sb.toString(), 0, 0);
                for (String idx : m.keySet()) {
    				Object value = m.get(idx);
    				if(value instanceof String){
    					pstmt.setString(idx, Common.get(value));
    				} else if(value instanceof Integer){
    					pstmt.setInt(idx, Common.getInt(value));
    				} else if(value instanceof Long){
    					pstmt.setLong(idx, Common.getLong(value));
    				} else if(value instanceof Number){
    					pstmt.setDouble(idx, Common.getNumeric(value));
    				} else if(value instanceof Date){
    					pstmt.setDate(idx, (Date) value);
    				} else if(value instanceof Timestamp){
    					pstmt.setTimestamp(idx, (Timestamp) value);
    				} else if(value instanceof Time){
    					pstmt.setTime(idx, (Time) value);
    				} else {
    					pstmt.setObject(idx, value);
    					//throw new Exception("PreparedStatement type is not correct");
    				}                	
                }
                return pstmt.executeUpdate();
            }
            else
            {
                return 0;
            }            
		}
		return 0;
	}	
	
	
	
	
	/**
  	 * <br>
  	 * <br>目的：執行可更新的查詢sql　
  	 * <br>參數：無
  	 * <br>傳回：傳回ResultSet
  	*/
	public ResultSet queryUpdateSQL(String sql) throws Exception{
		return queryUpdateSQL(sql, true);
	}
	public ResultSet queryUpdateSQL_NoChange(String sql) throws Exception{
		return queryUpdateSQL(sql, false);
	}
	public ResultSet queryUpdateSQL(String sql, boolean isChange) throws Exception{
		if (conn==null) conn = getConnection();		
		stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_UPDATABLE);
		if(isChange){
			sql = _transSQLFormat(sql);
		}
		rs = stmt.executeQuery(sql);
		return rs;
	}
	
	/**
     * <br>
     * <br>目的：執行查詢sql　
     * <br>參數：無
     * <br>傳回：傳回ResultSet
    */	
    public ResultSet querySQL(String sql,boolean scrollable) throws Exception{
    	sql = _transSQLFormat(sql);
		
//		System.out.println(sql);
		
    	if(scrollable){        	
        	return getScrollStatement().executeQuery(sql);            
        }else{
            return querySQL(sql);
        }        
    }       	
    
	/**
  	 * <br>
  	 * <br>目的：執行sql　
  	 * <br>參數：無
  	 * <br>傳回：傳回ResultSet
  	*/
    public void excuteSQL(String[] sql) throws Exception{
    	excuteSQL(sql, true, false);
    }
    public void excuteSQL_NoChange(String[] sql) throws Exception{
    	excuteSQL(sql, false, false);
    }
    /**
     * 供特殊字元使用，會於值之前加上 大寫N，令中文字都可正常存入DB
     * @param sql
     * @throws Exception
     */
    public void excuteSQL_UnicodePrefix(String[] sql) throws Exception{
    	excuteSQL(sql, true, true);
    }
    
	public void excuteSQL(String[] sql, boolean isChange, boolean isUnicode) throws Exception{
		int j=0;
		try {
			if (conn==null) conn = getConnection();
			conn.setAutoCommit(false);
			//stmt = getForwardStatement(conn);
			stmt = getForwardStatement();
			for(int i=0; i<sql.length; i++){
			    if(null!=sql[i]&&sql[i].length()>0){
			    	if(isChange){
			    		sql[i] = _transSQLFormat(sql[i].toString());
			    	}
			    	
			    	if(isUnicode) {
			    		sql[i] = setQuerySpace(sql[i].toString());
			    	}
//			    	System.out.println("執行SQL: "+sql[i]);
			        stmt.executeUpdate(sql[i]);
			    }
			    j++;
			}
//			stmt.close();
			conn.commit();
		} catch (Exception e) {
			conn.rollback();
			System.out.println("excuteSQL Exception = "+sql[j]);
			throw new Exception(e);			
		} 	
	}	
	
	
	public void excuteSQL(ArrayList sql) throws Exception{
		try {
			conn = getConnection();
			conn.setAutoCommit(false);
			stmt = getForwardStatement(conn);
			int i=1;
			for(Iterator iter = sql.iterator();iter.hasNext();){
				
				i++;
			    String temp = (String)iter.next();
			    stmt.executeUpdate(temp);
			    
			}
			conn.commit();
		} catch (Exception e) {
			conn.rollback();
			throw new Exception(e);			
		} 	
	}	
	
	public void excuteSQL(String sql) throws Exception{
		try {
			conn = getConnection();
			conn.setAutoCommit(false);
			stmt = getForwardStatement(conn);
			
//			System.out.println(sql);
			
			stmt.executeUpdate(sql);
			conn.commit();			
		} catch (Exception e) {
			conn.rollback();
			throw new Exception(e);			
		} 	
	}
	
	/**
	 * 不自動commit 交易由呼叫處自行控制
	 * @param sql
	 * @throws Exception
	 */
	public void excuteSQLNoAutoCommit(String sql) throws Exception {
		conn = getConnection();
		conn.setAutoCommit(false);
		stmt = getForwardStatement(conn);
		stmt.executeUpdate(sql);
	}
	
	/**
  	 * <br>
  	 * <br>目的：執行查詢sql(可捲動)　
  	 * <br>參數：無
  	 * <br>傳回：傳回ResultSet
  	*/
	public ResultSet querySQLByScroll(String sql) throws Exception{
		return querySQLByScroll(sql, true);
	}
	public ResultSet querySQLByScroll_NoChange(String sql) throws Exception{
		return querySQLByScroll(sql, false);
	}
	
	public ResultSet querySQLByScroll(String sql, boolean isChange) throws Exception{
		//conn = getConnection();
		if (conn==null) conn = getConnection();
		stmt = getScrollStatement(conn);
		if(isChange){
			sql = _transSQLFormat(sql);
		}
		rs = stmt.executeQuery(sql);
		return rs;
	}	
	
	/**
	 * <br>
	 * <br>目的：結束Connection, Statement, ResultSet
	 * <br>參數：Connection, Statement, ResultSet
	 * <br>傳回：無
	*/
	public void closeAll(){
		try {
			if (rs!=null){ rs.close(); }
			if (stmt!=null){ stmt.close(); }
			if (conn!=null){ conn.close(); }
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	
	//有些時候自動編號的位數有限,必須先清空原來的資料方能使Key不重覆, 就必須使用到以下的函式
	//例如在非公用批次開徵時就得用到它	
	public void setAutoCommit(boolean sFlag) throws SQLException {
	    try {
	      if ( conn != null ) {
		  	conn.setAutoCommit(sFlag);
	      }
	    } catch (SQLException sqle) {	      
	      throw new SQLException("SQLException: Unable to setAutoCommit properties.\n"+sqle);
	    }
	}
		  
	public void doCommit() throws Exception {
		if ( conn != null ) {		
			try {	
				conn.commit();
			} catch (SQLException sqle) {
				conn.rollback();	  	
				throw new SQLException("SQLException: Unable to commit sqlConnection.\n"+sqle);
			}
		}
	}
	
	public void doRollback() throws Exception {
		try {
			if ( conn != null ) {
				conn.rollback();
			}			
		} catch (SQLException sqle) {				  	
			throw new SQLException("SQLException: Unable to rollback transaction.\n"+sqle);
		}
	}
	
	//此函式沒有交易控制,需得自己維護整個交易,使用時要慬慎
	public void exeSQL(String[] sql) throws Exception{
		exeSQL(sql, true);
	}
	public void exeSQL_NoChange(String[] sql) throws Exception{
		exeSQL(sql, false);
	}
	
	public void exeSQL(String[] sql, boolean isChange) throws Exception{
		if (conn == null) conn = getConnection();
		try {
			//stmt = getForwardStatement(conn);
			//stmt = getForwardStatement();
			stmt = conn.createStatement();
			for(int i=0; i<sql.length; i++){
			    if(!"".equals(sql[i].toString()))
			    	if(isChange){
			    		sql[i] = _transSQLFormat(sql[i].toString());
			    	}
			        stmt.executeUpdate(sql[i]);
			}			
//			stmt.close();
		} catch (Exception e) {
//			System.out.println("excuteSQL Exception = "+sql[0]);
			throw new Exception(e);			
		} 	
	}	
	
	//此函式沒有交易控制,需得自己維護整個交易,使用時要慬慎
	public void exeSQL(String sSQL) throws Exception {
		exeSQL(sSQL, true);
	}
	public void exeSQL_NoChange(String sSQL) throws Exception {
		exeSQL(sSQL, false);
	}
	
	public void exeSQL(String sSQL, boolean isChange) throws Exception {
		if (conn == null) conn = getConnection();
		try {
			stmt = conn.createStatement();
			if(isChange){
				sSQL = _transSQLFormat(sSQL);
			}
			
			stmt.executeUpdate(sSQL);
//			stmt.close();
		} catch (Exception sqle) {	
//			System.out.println("excuteSQL Exception = "+sSQL);					
			throw new Exception(sqle);
		}		
	}	
	
	public String getLookupField(String sql){
		return getLookupField(sql,true);
	}
	public String getLookupField_NoChange(String sql){
		return getLookupField(sql,false);
	}


	
    public String getLookupField(String sql, boolean isChange) {
    	String rStr = "";    		
    	try {
    		if(isChange){
    			sql = _transSQLFormat(sql);
    		}
    		ResultSet rs = querySQL(sql);
    		if (rs.next()){
    			rStr = Common.checkGet(rs.getString(1));
    		}
    		rs.getStatement().close();
    		rs.close();
    	} catch (Exception e) {
    		e.printStackTrace();
    	}
    	return rStr;
    }	
	
    //=============================================================
    //轉換SQL格式用
    //因應科學園區要求，SQL Server 採用定序方式，區分大小寫
    //預設 table皆大寫，欄位皆小寫    
    //=============================================================

	public String _transSQLFormat(String sql){
		SQLtransfer slf = new SQLtransfer();
		slf.columnIsCaps = false;
		slf.tableIsCaps = true;
		String newsql = slf._transSQLFormat(sql);
		return newsql;		
	}
	
	/**
	 * <br>
	 * <br>目的：將QUERY符號用空格拉開,並增加字串前置詞N
	 * <br>參數：Query
	 * <br>傳回：resultQry
	*/
	public String setQuerySpace(String sql){
		//String resultQry = sql;
		//0. 空格取代為<space>
		
		char sqlArr[] = sql.toCharArray();
		String resultQry = "";
		int pointCount = 0;
		for(int i = 0 ; i < sqlArr.length; i++){
			if(sqlArr[i] == '\'' ){
				if((pointCount%2) == 0)
					resultQry += "N"+sqlArr[i];
				else
					resultQry += sqlArr[i];
				pointCount++;
			}else{
				resultQry += sqlArr[i];
			}
		}
		/*
		String resultQry = sql.replace("' '", "<space>");
		//System.out.println("1. resultQry = "+resultQry);
		//1. 等於前後使用空格隔開
		resultQry = resultQry.replace("=", " = ");
		//2. 逗點前後使用空格隔開
		resultQry = resultQry.replace(",", " , ");
		//System.out.println("2. resultQry = "+resultQry);
		//3. 前括號前後使用空格隔開
		resultQry = resultQry.replace("(", " ( ");
		//4. LIKE前後使用空格隔開
		resultQry = resultQry.replace("like", " like ");
		//5. 單引號前加上N
		resultQry = resultQry.replace(" '", " N'");
		//System.out.println("5. resultQry = "+resultQry);
		//6 等於前後使用空格隔開復原
		resultQry = resultQry.replace(" = ", "=");
		//7 逗點前後使用空格隔開復原
		resultQry = resultQry.replace(" , ", ",");
		//8 前括號前後使用空格隔開復原
		resultQry = resultQry.replace(" ( ", "(");
		//9. LIKE前後使用空格隔開復原
		resultQry = resultQry.replace(" like ", "like");
		//System.out.println("9. resultQry = "+resultQry);
		//10 <space>轉為空格
		resultQry = resultQry.replace("<space>", "' '");
		*/
		System.out.println("resultQry = "+resultQry);
		return resultQry;
	}
}
