   package util;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import TDlib_Simple.iBase;
import TDlib_Simple.com.src.DBServerTools;
import unt.ch.UNTCH_Tools;

public class SuperBean2 extends QueryBean implements iBase{
	
	/**
	 * 逐個單據修改時 區別是否有改過  全部測完後 移除
	 */
	private boolean flag1 = false;
	public void setFlag1Enable() {
		this.flag1 = true;
	}
	private String engineeringNo;
	private String saveLog;
	private String isUnteg001f;
	private String isUntmp021f;
	private String isUntne020f;
	public String getEngineeringNo() { return checkGet(engineeringNo); }
	public void setEngineeringNo(String engineeringNo) { this.engineeringNo = checkSet(engineeringNo); }
	public String getSaveLog() { return checkGet(saveLog); }
	public void setSaveLog(String saveLog) { this.saveLog = checkSet(saveLog); }
	public String getIsUnteg001f() { return checkGet(isUnteg001f); }
	public void setIsUnteg001f(String isUnteg001f) { this.isUnteg001f = checkSet(isUnteg001f); }
	public String getIsUntmp021f() { return checkGet(isUntmp021f); }
	public void setIsUntmp021f(String isUntmp021f) { this.isUntmp021f = checkSet(isUntmp021f); }
	public String getIsUntne020f() { return checkGet(isUntne020f); }
	public void setIsUntne020f(String isUntne020f) { this.isUntne020f = checkSet(isUntne020f); }
	
	private String progID;
	public String getProgID() {return checkGet(progID);}
	public void setProgID(String progID) {this.progID = checkSet(progID);}
	
	private String isAddProof = "";
	public String getIsAddProof() {return checkGet(isAddProof);}
	public void setIsAddProof(String isAddProof) {this.isAddProof = checkSet(isAddProof);}
	
	private String caseNo1;
	public String getCaseNo1() { return checkGet(caseNo1); }
	public void setCaseNo1(String caseNo1) { this.caseNo1 = checkSet(caseNo1); }
	
	public void _execInsert(String table){doExecFunc("insert", true, table);}
	public void _execInsertforDetail(){	doExecFunc("insert", false, "");}
	
	public void _execUpdate(String table){doExecFunc("update", true, table);}	
	public void _execUpdateforDetail(){	doExecFunc("update", false, "");}
	
	public void _execDelete(String table){doExecFunc("delete", true, table);}
	public void _execDeleteforDetail(){	doExecFunc("delete", false, "");}
	
	public String[][] getUpdateCheckSQLBeforeAction(){String [][] rtnArray={{"","","",""}}; return rtnArray;}	

	//============================================================
	/** 土地類 **/
	public String _table_LA = "LA";
	/** 建物類 **/
	public String _table_BU = "BU";
	/** 土改類 **/
	public String _table_RF = "RF";
	/** 動產類 **/
	public String _table_MP = "MP";
	/** 股票類 **/
	public String _table_VP = "VP";
	/** 權利類 **/
	public String _table_RT = "RT";

	protected List _getBeans_asList(){return null;}

	protected List _getBeansExtend_asList(){return null;}

	protected void _setDefaultValue(){}

	protected String _getMaxSerialNo(){return null;}

	protected SuperBean2[] _checkPropertyNoType(){return null;}

	protected void queryDeleteData(SuperBean2 sb){}

	protected void execVerify(){}
	
	// TODO 改為 抽象
	protected void execVerify(Connection conn) throws Exception {}

	//============================================================

	protected void doExecFunc(String type, boolean isProof, String table){
		// TODO 增減值單 , 減損單 update
		// TODO
		Database db = null;
		Connection conn = null;
		String className = this.getClass().getName();
		try {
			UNTCH_Tools uctls = new UNTCH_Tools();

			if (isProof) {
				if (this.flag1) {
					db = new Database();
					conn = db.getConnection();
					conn.setAutoCommit(false);
				}
				
				SuperBean2 sb = null;

				if ("insert".equals(type)) {
					String newCaseNo = this.getNewCaseNo(table);
					this.setCaseNo(newCaseNo);						
				}

				List list = _getBeans_asList();
				for (Object o : list) {
					sb = (SuperBean2) o;
					_setDefaultValue();
					uctls._setParameter(this, sb);
					checkExecType(sb, conn, type);
				}

				//權利，有價證劵用  增加單單據與主檔同一個table
				if ("update".equals(type)) {		
					List listExtend = _getBeansExtend_asList();
					if (listExtend != null) {
						for (Object o : listExtend) {
							sb = (SuperBean2) o;
							_setDefaultValue();
							uctls._setParameter(this,sb);
							
							if (conn != null) {
								sb.execVerify(conn);
							} else {
								sb.execVerify();
							}
							
						}	
					}
				}

				if ("delete".equals(type)) {
					List listExtend = _getBeansExtend_asList();
					if (listExtend != null) {
						for (Object o : listExtend) {
							sb = (SuperBean2)o;
							sb.queryDeleteData(this);
						}
					}
				}

				sb = null;
				
				if (conn != null) {
					conn.commit();
				}
				
			} else {
				//#region 明細 
				util.SuperBean2[] sb = _checkPropertyNoType();

				_setDefaultValue();

				util.SuperBean2 s = sb[0];

				uctls._setParameter(this,s);
				//執行insert、update、delete進入點
				checkExecType(s, null, type);
				s = null;

				sb = null;	
				//#end 
			}				
			
			if ("Y".equals(this.getSaveLog())) {
				if ("insert".equals(type)) {			
					super.setStateInsertSuccess();
					super.setErrorMsg("新增完成");
					//使用者操作記錄
					Common.insertCreateRecordSql("", this.getEnterOrg(), this.getUserID(), className.substring(className.lastIndexOf('.') + 1), this.getObjPath().replaceAll("&gt;", ">") + " > > 新增" + this.getCaseNo());
				} else if ("update".equals(type)) {	
					super.setStateUpdateSuccess();
					super.setErrorMsg("修改完成");
					//使用者操作記錄
					Common.insertUpdateRecordSql("", this.getEnterOrg(), this.getUserID(), className.substring(className.lastIndexOf('.') + 1), this.getObjPath().replaceAll("&gt;", ">") + " > > 修改" + this.getCaseNo());
				} else if ("delete".equals(type)) {	
					super.setStateDeleteSuccess();
					super.setErrorMsg("刪除完成");
					//使用者操作記錄
					Common.insertDeleteRecordSql("", this.getEnterOrg(), this.getUserID(), className.substring(className.lastIndexOf('.') + 1), this.getObjPath().replaceAll("&gt;", ">") + " > > 刪除" + this.getCaseNo());
				}	
			}
			
		} catch(Exception e) {
			e.printStackTrace();
			log._execLogError(e.getMessage());
			
			if ("insert".equals(type)) {			
				super.setStateInsertError();
				super.setErrorMsg("新增失敗，請洽系統工程師。");
			} else if ("update".equals(type)) {	
				super.setStateUpdateError();
				super.setErrorMsg("修改失敗，請洽系統工程師。");
			} else if ("delete".equals(type)) {	
				super.setStateDeleteError();
				super.setErrorMsg("刪除失敗，請洽系統工程師。");
			}			
			if (conn != null) {
				try {
					conn.rollback();
				} catch (Exception ee) {
					//ignore
				}
			}
		} finally {
			if (conn != null) {
				try {
					conn.close();
					conn = null;
				} catch (Exception e) {
					// ignore
					
				}
			}
			
			if (db != null) {
				db.closeAll();
			}
		}
	}

	private void checkExecType(SuperBean2 sb, Connection conn, String type) throws Exception {
		try {
			if ("insert".equals(type)) {
				if (conn != null) {
					sb.insert(conn);
				} else {
					sb.insert();
				}
			} else if ("update".equals(type)) {	
				if (conn != null) {
					sb.update(conn);
				} else {
					sb.update();
				}
			} else if ("delete".equals(type)) {
				if (conn != null) {
					sb.delete(conn);
				} else {
					sb.delete();
				}
			}

			this.setSerialNo(sb.getSerialNo());
			this.setSerialNoS(sb.getSerialNoS());
			this.setSerialNoE(sb.getSerialNoE());
			this.setLotNo(sb.getLotNo());

		} catch (Exception e) {
			throw e;
		}
	}
	
	protected String getNewCaseNo(String table) {
		String result = "";
		String sql="select case when max(caseno) is null then '' else (max(caseno) + 1) end as caseno from " + table +
				" where enterorg = " + Common.sqlChar(this.getEnterOrg()) +
				" and caseno like " + Common.sqlChar(Datetime.getYYY() + "%") +
				"";
		DBServerTools dbt = new DBServerTools();
		dbt._setDatabase(new Database());
		result = dbt._execSQL_asString(sql);

		if("".equals(Common.get(result))){
			result = Datetime.getYYY() + "0000001";
		}else{
			result = Datetime.getYYY() + Common.formatFrontZero(result.substring(3),7);
		}			
		return result;
	}
			
	protected Map queryPropertyNofrom(String table, SuperBean2 sb){
		Map map = new HashMap();

		String conditionsql = "select propertyNo, serialno from " + table + " where 1=1" + 
				" and enterorg = " + Common.sqlChar(sb.getEnterOrg()) +
				" and ownership = " + Common.sqlChar(sb.getOwnership()) +
				" and caseno = " + Common.sqlChar(sb.getCaseNo()) +
				" and differencekind = " + Common.sqlChar(sb.getDifferenceKind());

		Database db = null;						
		try {
			db = new Database();					
			ResultSet rs = db.querySQL(conditionsql);
			while(rs.next()){
				map.put(rs.getString("propertyno"), rs.getString("serialno"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.closeAll();
		}
		return map;
	}
			
	//=================================================================
	public void _execQueryOne(){
		List list = _getBeans_asList();
		util.SuperBean2 sb = (util.SuperBean2)list.get(0);	
		UNTCH_Tools uctls = null;
	
		//=======================================	
		sb.setEnterOrg(this.getEnterOrg());
		sb.setOwnership(this.getOwnership());
		sb.setCaseNo(this.getCaseNo());		
		sb.setDifferenceKind(this.getDifferenceKind());
		//=======================================
		
		try{
			uctls = new UNTCH_Tools();
			Object obj = sb.queryOne();			
			uctls._setParameter(obj,this);		
			
			//=======================================		
			this.setEnterOrgName(uctls._getOrganSName(this.getEnterOrg()));			
			//=======================================
			
			setStateQueryOneSuccess();
		}catch(Exception e){
			log._execLogError(e.getMessage());
		}finally{
			sb = null;
			uctls = null;
		}	
	}
		
	
	//=================================================================
	public String checkGet(String s){
		if(s==null){								return "";
		}else if("null".equals(s.toLowerCase())){	return "";
		}else{										return s.trim();
		}
	}
	//=================================================================
	
	private String enterOrg;
	private String enterOrgName;
	private String ownership;
	private String differenceKind;
	private String caseNo;
	private String propertyNo;
	private String serialNo;
	private String serialNoS;
	private String serialNoE;
	private String lotNo;
	private String verify;
	
	public String getEnterOrg() {return checkGet(enterOrg);}
	public void setEnterOrg(String enterOrg) {this.enterOrg = checkSet(enterOrg);}
	public String getEnterOrgName() {return checkGet(enterOrgName);}
	public void setEnterOrgName(String enterOrgName) {this.enterOrgName = checkSet(enterOrgName);}
	public String getOwnership() {return checkGet(ownership);}
	public void setOwnership(String ownership) {this.ownership = checkSet(ownership);}
	public String getDifferenceKind() {return checkGet(differenceKind);}
	public void setDifferenceKind(String differenceKind) {this.differenceKind = checkSet(differenceKind);}
	public String getCaseNo() {return checkGet(caseNo);}
	public void setCaseNo(String caseNo) {this.caseNo = checkSet(caseNo);}
	public String getPropertyNo() {return checkGet(propertyNo);}
	public void setPropertyNo(String propertyNo) {this.propertyNo = checkSet(propertyNo);}
	public String getSerialNo() {return checkGet(serialNo);}
	public void setSerialNo(String serialNo) {this.serialNo = checkSet(serialNo);}
	public String getSerialNoS() {return checkGet(serialNoS);}
	public void setSerialNoS(String serialNoS) {this.serialNoS = checkSet(serialNoS);}
	public String getSerialNoE() {return checkGet(serialNoE);}
	public void setSerialNoE(String serialNoE) {this.serialNoE = checkSet(serialNoE);}
	public String getLotNo() {return checkGet(lotNo);}
	public void setLotNo(String lotNo) {this.lotNo = checkSet(lotNo);}
	public String getVerify() {return checkGet(verify);}
	public void setVerify(String verify) {this.verify = checkSet(verify);}

	private String cause;
	private String cause1;
	private String cause2;
	private String addBookValue;
	private String reduceBookValue;
	public String getCause() {return checkGet(cause);}
	public void setCause(String cause) {this.cause = checkSet(cause);}
	public String getCause1() {return checkGet(cause1);}
	public void setCause1(String cause1) {this.cause1 = checkSet(cause1);}
	public String getCause2() {return checkGet(cause2);}
	public void setCause2(String cause2) {this.cause2 = checkSet(cause2);}
	public String getAddBookValue() {return checkGet(addBookValue);}
	public void setAddBookValue(String addBookValue) {this.addBookValue = checkSet(addBookValue);}
	public String getReduceBookValue() {return checkGet(reduceBookValue);}
	public void setReduceBookValue(String reduceBookValue) {this.reduceBookValue = checkSet(reduceBookValue);}
	

	private String roleid;
	private String unitID;
	private String keeperno;
	public String getRoleid() {return checkGet(roleid);}
	public void setRoleid(String roleid) {this.roleid = checkSet(roleid);}
	public String getUnitID() {return checkGet(unitID);}
	public void setUnitID(String unitID) {this.unitID = checkSet(unitID);}
	public String getKeeperno() {return checkGet(keeperno);}
	public void setKeeperno(String keeperno) {this.keeperno = checkSet(keeperno);}
	
	public Object queryOne() throws Exception{
		return null;
	}
	public void approveAll()throws Exception{
		
	}
	
	
	/**
  	 * <br>
  	 * <br>目的：新增儲存
  	 * <br>參數：無
  	 * <br>傳回：無
  	*/	
	public void insert() throws Exception{
		Database db = new Database();
		String className = this.getClass().getName();
		
		try {			
			if (!beforeExecCheck(getInsertCheckSQL())){
				setStateInsertError();
				throw new Exception(getErrorMsg());
			}else{
//				setEditID(getUserID());
				setEditDate(Datetime.getYYYMMDD());
				setEditTime(Datetime.getHHMMSS());	
				
				db.excuteSQL(getInsertSQL());		
				
				setStateInsertSuccess();
				setErrorMsg("新增完成");
				
				//使用者操作記錄
				if ("Y".equals(this.getSaveLog())) {
					if ((this.getIsUntmp021f() != null && !"".equals(this.getIsUntmp021f())) || (this.getIsUntne020f() != null && !"".equals(this.getIsUntne020f()))) { //動產/物品廢品處理作業
						Common.insertCreateRecordSql("", this.getEnterOrg(), this.getUserID(), className.substring(className.lastIndexOf('.') + 1), this.getObjPath().replaceAll("&gt;", ">") + " > > 新增" + this.getCaseNo1());
					} else {
						Common.insertCreateRecordSql("", this.getEnterOrg(), this.getUserID(), className.substring(className.lastIndexOf('.') + 1), this.getObjPath().replaceAll("&gt;", ">") + " > > 新增" + this.getCaseNo());
					}					
				}
			}

		} catch (Exception e) {
//			e.printStackTrace();
			throw e;
		} finally {
			db.closeAll();
		}			
	}
	
	public void insert(Connection conn) throws Exception {
		
		Statement statement = null;
		try {			
			if (!beforeExecCheck(getInsertCheckSQL())) {
				setStateInsertError();
				throw new Exception(getErrorMsg());
			} else {
				
				statement = conn.createStatement();
				
//				setEditID(getUserID());
				setEditDate(Datetime.getYYYMMDD());
				setEditTime(Datetime.getHHMMSS());	
				
				String[] insertSQLs = this.getInsertSQL();
				if (insertSQLs != null && insertSQLs.length > 0) {
					for (String tmpSQL : insertSQLs) {
						statement.execute(tmpSQL);
					}
				}
				setStateInsertSuccess();
				setErrorMsg("新增完成");
			}

		} catch (Exception e) {
			throw e;
		} finally {
			if (statement != null) {
				try {
					statement.close();
					statement = null;
				} catch (Exception e) {
					//ignore
				}
			}
		}	
	}

  	/**
  	 * <br>
  	 * <br>目的：修改儲存
  	 * <br>參數：無
  	 * <br>傳回：無
  	*/	
	public void update() throws Exception{
		Database db = new Database();
		String className = this.getClass().getName();
		
		try {			
			if (!beforeExecCheck(getUpdateCheckSQL())){
				setStateUpdateError();
				throw new Exception(getErrorMsg());
			}else{
//				setEditID(getUserID()); 
//				setEditDate(Datetime.getYYYMMDD());
//				setEditTime(Datetime.getHHMMSS());	
				db.excuteSQL(getUpdateSQL());		
				setStateUpdateSuccess();
				setErrorMsg("修改完成");
				
				//使用者操作記錄
				if ("Y".equals(this.getSaveLog())) {
					if (this.getIsUnteg001f() != null && !"".equals(this.getIsUnteg001f())) { //營建工程資料維護
						Common.insertUpdateRecordSql("", this.getEnterOrg(), this.getUserID(), className.substring(className.lastIndexOf('.') + 1), this.getObjPath().replaceAll("&gt;", ">") + " > > 修改" + this.getEngineeringNo());
					} else {
						Common.insertUpdateRecordSql("", this.getEnterOrg(), this.getUserID(), className.substring(className.lastIndexOf('.') + 1), this.getObjPath().replaceAll("&gt;", ">") + " > > 修改" + this.getCaseNo());
					}					
				}
			}

		} catch (Exception e) {
//			e.printStackTrace();
			throw e;
		} finally {
			db.closeAll();
		}			
	}	
	
	public void update(Connection conn) throws Exception {
		Statement statement = null;
		try {			
			if (!beforeExecCheck(getUpdateCheckSQL())){
				setStateUpdateError();
				throw new Exception(getErrorMsg());
			} else {
				
				statement = conn.createStatement();
				
				String[] updateSQLs = this.getUpdateSQL();
				if (updateSQLs != null && updateSQLs.length > 0) {
					for (String tmpSQL : updateSQLs) {
						statement.execute(tmpSQL);
					}
				}
				setStateUpdateSuccess();
				setErrorMsg("修改完成");
			}

		} catch (Exception e) {
			throw e;
		} finally {
			if (statement != null) {
				try {
					statement.close();
					statement = null;
				} catch (Exception e) {
					//ignore
				}
			}
		}	
	}

  	/**
  	 * <br>
  	 * <br>目的：刪除
  	 * <br>參數：無
  	 * <br>傳回：無
  	*/	
	public void delete() throws Exception{
		Database db = new Database();
		String className = this.getClass().getName();
		try {
			if (!beforeExecCheck(getDeleteCheckSQL())){
				setStateDeleteError();
				throw new Exception();
			}else{
//				setEditID(getUserID());
//				setEditDate(Datetime.getYYYMMDD());
//				setEditTime(Datetime.getHHMMSS());					
				db.excuteSQL(getDeleteSQL());		
				setStateDeleteSuccess();
				setErrorMsg("刪除完成");
				
				//使用者操作記錄
				if ("Y".equals(this.getSaveLog())) {
					if (this.getIsUnteg001f() != null && !"".equals(this.getIsUnteg001f())) { //營建工程資料維護
						Common.insertDeleteRecordSql("", this.getEnterOrg(), this.getUserID(), className.substring(className.lastIndexOf('.') + 1), this.getObjPath().replaceAll("&gt;", ">") + " > > 刪除" + this.getEngineeringNo());
					} else if ((this.getIsUntmp021f() != null && !"".equals(this.getIsUntmp021f())) || (this.getIsUntne020f() != null && !"".equals(this.getIsUntne020f()))) { //動產/物品廢品處理作業
						Common.insertDeleteRecordSql("", this.getEnterOrg(), this.getUserID(), className.substring(className.lastIndexOf('.') + 1), this.getObjPath().replaceAll("&gt;", ">") + " > > 刪除" + this.getCaseNo1());
					} else {
						Common.insertDeleteRecordSql("", this.getEnterOrg(), this.getUserID(), className.substring(className.lastIndexOf('.') + 1), this.getObjPath().replaceAll("&gt;", ">") + " > > 刪除" + this.getCaseNo());
					}					
				}
			}
		
		} catch (Exception e) {
//			e.printStackTrace();
			throw e;
		} finally {
			db.closeAll();
		}			
	}	
	
	public void delete(Connection conn) throws Exception {
		Statement statement = null;
		try {
			if (!beforeExecCheck(getDeleteCheckSQL())) {
				setStateDeleteError();
				throw new Exception();
			} else {
				
				statement = conn.createStatement();
				
				String[] deleteSQLs = this.getDeleteSQL();
				if (deleteSQLs != null && deleteSQLs.length > 0) {
					for (String tmpSQL : deleteSQLs) {
						statement.execute(tmpSQL);
					}
				}
				
				setStateDeleteSuccess();
				setErrorMsg("刪除完成");
			}
		
		} catch (Exception e) {
			throw e;
		} finally {
			if (statement != null) {
				try {
					statement.close();
					statement = null;
				} catch (Exception e) {
					//ignore
				}
				
			}
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

		//檢查物品入帳日期必須是最大月結年月＋１
		public boolean checkClosingYMfromUNTAC_CLOSINGNE(String enterdate, String enterOrg, String differenceKind){
			boolean result = false;
			
			String closing1ym = queryClosingYMfromUNTAC_CLOSINGNE(enterOrg,differenceKind);
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
		
		//檢查物品是否可回復入帳
		public boolean checkNECanNotReVerify(String enterdate, String enterOrg, String differenceKind){
			boolean result = false;
			String closing1ym = queryClosingYMfromUNTAC_CLOSINGNE(enterOrg,differenceKind);
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
		private String queryClosingYMfromUNTAC_CLOSINGNE(String enterOrg, String differenceKind){
			String closing1ym = null;
			String sql = "select closing1ym from UNTAC_CLOSINGNE where 1=1" +
							" and enterorg = " + Common.sqlChar(enterOrg) +
							" and differencekind = " + Common.sqlChar(differenceKind);
			
			closing1ym = queryDatafromDatabase(sql);
			
			return closing1ym;
		}
		
	private String p_proofYear;
	private String p_proofDoc;
	private String p_proofNo;
	private String p_summonsDate;
	public String getP_proofYear() {return checkGet(p_proofYear);}
	public void setP_proofYear(String p_proofYear) {this.p_proofYear = checkSet(p_proofYear);}
	public String getP_proofDoc() {return checkGet(p_proofDoc);}
	public void setP_proofDoc(String p_proofDoc) {this.p_proofDoc = checkSet(p_proofDoc);}
	public String getP_proofNo() {return checkGet(p_proofNo);}
	public void setP_proofNo(String p_proofNo) {this.p_proofNo = checkSet(p_proofNo);}
	public String getP_summonsDate() {return checkGet(p_summonsDate);}
	public void setP_summonsDate(String p_summonsDate) {this.p_summonsDate = checkSet(p_summonsDate);}
	

	String proofYear;
	String proofDoc;
	String proofNo;
	String summonsDate;
	public String getProofDoc(){ return checkGet(proofDoc); }
	public void setProofDoc(String s){ proofDoc=checkSet(s); }
	public String getProofNo(){ return checkGet(proofNo); }
	public void setProofNo(String s){ proofNo=checkSet(s); }	
	public String getSummonsDate() {return checkGet(summonsDate);}
	public void setSummonsDate(String summonsDate) {this.summonsDate = checkSet(summonsDate);}	
	public String getProofYear() {return checkGet(proofYear);}
	public void setProofYear(String proofYear) {this.proofYear = checkSet(proofYear);}
	

}
