package unt.ch;

import java.util.List;

import java.sql.ResultSet;

import util.Common;
import util.Database;
import util.SQLtransfer;
import TDlib_Simple.com.src.DBServerTools;
import TDlib_Simple.tools.src.ReflectTools;
import TDlib_Simple.tools.src.StringTools;

public class UNTCH_Tools{

	//==============================================================================
	//									工具集
	//==============================================================================
	//取得財產名稱
	public String _getPropertyNoName(String propertyNo){return doGetPropertyNoName("000000000A", propertyNo);}
	public String _getPropertyNoName(String enterOrg, String propertyNo){return doGetPropertyNoName(enterOrg, propertyNo);}
	
	//取得材質
	public String _getMaterial(String propertyNo){return doGetMaterial("000000000A", propertyNo);}
	public String _getMaterial(String enterOrg, String propertyNo){return doGetMaterial(enterOrg, propertyNo);}
	
	//取得單位
	public String _getPropertyUnit(String propertyNo){return doGetPropertyunit("000000000A", propertyNo);}
	public String _getPropertyUnit(String enterOrg, String propertyNo){return doGetPropertyunit(enterOrg, propertyNo);}
	
	//取得使用年限
	public String _getLimitYear(String propertyNo){return doGetLimityear("000000000A", propertyNo);}
	public String _getLimitYear(String enterOrg, String propertyNo){return doGetLimityear(enterOrg, propertyNo);}

	//取得使用者名稱
	public String _getEmpName(String organid, String empid){			return doGetEmpName(organid,empid);}	
	
	//取得工程名稱
	public String _getEngineeringNoName(String enterOrg, String engineeringNo){	return doGetEngineeringNoName(enterOrg, engineeringNo);}
		
	
	//取得財產區分別名稱
	public String _getDifferenceKindName(String differenceKind){		return doGetDifferenceKindName(differenceKind);}
	
	//取得財產大類名稱
	public String _getPropertyNoGroupName(String propertyNo){			return doGetPropertyNoGroupName(propertyNo);}
	
	//取得來源名稱
	public String _getSourceKindName(String sourceKind){				return doGetSourceKindName(sourceKind);}
	
	//取得折舊方法名稱
	public String _getDeprMethodName(String deprMethod){				return doGetDeprMethodName(deprMethod);}
	
	//取得機關名稱
	public String _getOrganSName(String organID){						return doGetOrganSName(organID);}
		
	//取得機關名稱(新)
	public String _getOrganAName(String organID){						return doGetOrganAName(organID);}

	//取得權屬名稱
	public String _getOwnershipName(String ownership){					return doGetOwnershipName(ownership);}
	
	//取得是與否的中文名稱
	public String _getYNName(String s){									return doGetYNName(s);}

	//取得土地建物標示中文名稱(前7碼)
	public String _getSignNoName(String s){								return doGetSignNoName(s);}

	//取得資料狀態中文名稱(前7碼)
	public String _getDataStateName(String s){							return doGetDataStateName(s);}

	//判斷財產類別用
	public String _checkPropertyNoType(String propertyNo){				return doCheckPropertyNoType(propertyNo);}

	//自動將sourceObj的值自動寫入targetObj	
	public void _setParameter(Object sourceObj, Object targetObj){		doSetParameterToObject(sourceObj, targetObj);}
		
	//取得文號
	public String _getProofDoc(String enterOrg, String docNo){			return doGetProofDoc(enterOrg, docNo);}
	
	//日期轉成西元年
	public String _transToCE_Year(String s){							return doTransToCE_Year(s);}
	
	//日期轉成民國年
	public String _transToROC_Year(String s){							return doTransToYYYMMDD(s);}
	
	//取得序號caseSerialNo
	public String _getNewCaseSerialNo(DataStructor ds, String[] tables){return doGetNewCaseSerialNo(ds,tables);}
	
	//取得預設機關參數值
	public String _getDefaultValue(String enterOrg, String type){		return doGetDefaultValue(enterOrg, type);}
	
	//查詢單一欄位值
	public methodHelper _queryData(String columnName){					return doQueryData(columnName);}

	//查詢單一欄位值
	public methodHelper_NoChange _queryData_NoChange(String columnName){return doQueryData_NoChange(columnName);}
	
	//財產來源HTML
	public String getSourceKind(String className, String inputName, String inputValue, String chineseDesc) {return doGetSourceKind(className,inputName,inputValue,chineseDesc);}
	
	//增加原因HTML
	public String getCause(String className, String inputName, String inputValue, String chineseDesc, String condition) {return doGetCause(className,inputName,inputValue,chineseDesc,condition,"");}
	
	//減損原因用HTML
	public String getReduceCause(String className, String inputName, String inputValue, String chineseDesc, String condition, String func) {return doGetCause(className,inputName,inputValue,chineseDesc,condition,func);}
		
	//修改util.View.getPopProperty()內容
	public String getPopProperty(String className, String inputName, String inputValue, String chineseDesc, String preWord) {return doGetPopProperty(className,inputName,inputValue,chineseDesc,preWord);}
	
	//檢查是否只有一筆基金財產資料
	public String getCountForFundType(String enterOrg){				return doGetCountForFundType(enterOrg);}
	
	//檢查存置地點名稱
	public String getPlace1Name(String enterOrg, String place1){				return doGetPlace1Name(enterOrg, place1);}
	
	
	//==============================================================================
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
	
		private String doCheckPropertyNoType(String propertyNo){
			String result = "";
			
			if(new StringTools()._isEmpty(propertyNo)){
				
			}else{
				String checkStr1 = propertyNo.substring(0,1);
				String checkStr3 = propertyNo.substring(0,3);
				
				
				if("2".equals(checkStr1)){				result = _table_BU;
				}else if("111".equals(checkStr3)){		result = _table_RF;
				}else if("1".equals(checkStr1)){		result = _table_LA;
				}else if("3".equals(checkStr1) 
						|| "4".equals(checkStr1) 
						|| "5".equals(checkStr1) 
						|| "6".equals(checkStr1)){		
														result = _table_MP;
				}else if("8".equals(checkStr1)){		result = _table_RT;
				}else if("9".equals(checkStr1)){		result = _table_VP;
				}
			}
			return result;
		}
	
	//==============================================================================	
		private void doSetParameterToObject(Object sourceObj, Object targetObj){
			ReflectTools rf = new ReflectTools();
			
			rf._setParameter_forNoLog_bothInteraction(sourceObj, targetObj);			
		}

	//==============================================================================	
		private String doGetPropertyNoName(String enterOrg, String propertyNo){			
			String sql = "select z.propertyName from syspk_propertycode z where 1=1" +
					" and enterorg in (" + Common.sqlChar(enterOrg) + ", '000000000A')" +
					" and propertyno = " + Common.sqlChar(propertyNo);
			
			return new StringTools()._isEmpty_asEmptyString(doGetDBServerTools()._execSQL_asString(_transSQLFormat(sql)));
		}
		
	//==============================================================================	
		private String doGetMaterial(String enterOrg, String propertyNo){			
			String sql = "select z.material from syspk_propertycode z where 1=1" +
					" and enterorg in (" + Common.sqlChar(enterOrg) + ", '000000000A')" +
					" and propertyno = " + Common.sqlChar(propertyNo);

			return new StringTools()._isEmpty_asEmptyString(doGetDBServerTools()._execSQL_asString(_transSQLFormat(sql)));
		}		
	
	//==============================================================================	
		private String doGetPropertyunit(String enterOrg, String propertyNo){			
			String sql = "select z.propertyunit from syspk_propertycode z where 1=1" +
					" and enterorg in (" + Common.sqlChar(enterOrg) + ", '000000000A')" +
					" and propertyno = " + Common.sqlChar(propertyNo);
			
			return new StringTools()._isEmpty_asEmptyString(doGetDBServerTools()._execSQL_asString(_transSQLFormat(sql)));
		}
		
	//==============================================================================	
		private String doGetLimityear(String enterOrg, String propertyNo){			
			String sql = "select z.limityear from syspk_propertycode z where 1=1" +
					" and enterorg in (" + Common.sqlChar(enterOrg) + ", '000000000A')" +
					" and propertyno = " + Common.sqlChar(propertyNo);
			
			return new StringTools()._isEmpty_asEmptyString(doGetDBServerTools()._execSQL_asString(_transSQLFormat(sql)));
		}		
		
	//==============================================================================
		private String doGetEngineeringNoName(String enterOrg, String engineeringNo){			
			String sql = "select engineeringName from UNTEG_EngineeringCase z where 1=1" +
					" and enterOrg = " + Common.sqlChar(enterOrg) +
					" and engineeringNo = " + Common.sqlChar(engineeringNo);
			
			return new StringTools()._isEmpty_asEmptyString(doGetDBServerTools()._execSQL_asString(_transSQLFormat(sql)));
		}
		
	//==============================================================================
		private String doGetEmpName(String organid, String empid){			
			String sql = "select empname from SYSAP_EMP where 1=1" +
					" and organid = " + Common.sqlChar(organid) +
					" and empid = " + Common.sqlChar(empid);
			
			return new StringTools()._isEmpty_asEmptyString(doGetDBServerTools()._execSQL_asString(_transSQLFormat(sql)));
		}		
		
	//==============================================================================
		
		private String doGetPlace1Name(String enterOrg, String place1){			
			String sql = "select placename from SYSCA_PLACE where enterorg =" + Common.sqlChar(enterOrg) +
					     " and placeno = " + Common.sqlChar(place1);

			return new StringTools()._isEmpty_asEmptyString(doGetDBServerTools()._execSQL_asString(_transSQLFormat(sql)));
		}		
		
	//==============================================================================	
		
		private String doGetDifferenceKindName(String differenceKind){			
			String sql = "select codeName from SYSCA_Code where codeKindID='DFK'" +
					" and codeid = " + Common.sqlChar(differenceKind);

			return new StringTools()._isEmpty_asEmptyString(doGetDBServerTools()._execSQL_asString(_transSQLFormat(sql)));
		}		
		
	//==============================================================================
		
		private String doGetPropertyNoGroupName(String propertyNo){
			String result = "";
			
			if(new StringTools()._isEmpty(propertyNo)){
				
			}else{
				String checkStr1 = propertyNo.substring(0,1);
				String checkStr3 = propertyNo.substring(0,3);
				
				if("111".equals(checkStr3)){			result = "土地改良物";
				}else if("1".equals(checkStr1)){		result = "土地";
				}else if("2".equals(checkStr1)){ 		result = "房屋建築及設備";
				}else if("3".equals(checkStr1)){ 		result = "機械及設備";
				}else if("4".equals(checkStr1)){		result = "交通及運輸設備";
				}else if("5".equals(checkStr1)){		result = "什項設備";
				}else if("8".equals(checkStr1)){		result = "權利";
				}else if("9".equals(checkStr1)){		result = "有價證券";
				}
			}
			return result;
		}
	
	//==============================================================================
		private String doGetSourceKindName(String s){
			String sql = "select codeName from SYSCA_Code where codeKindID='SKD'" +
					" and codeid = " + Common.sqlChar(s);
			
			return new StringTools()._isEmpty_asEmptyString(doGetDBServerTools()._execSQL_asString(_transSQLFormat(sql)));
		}

	//==============================================================================
		
		private String doGetDeprMethodName(String s){
			String sql = "select codeName from SYSCA_Code where codeKindID='DEP'" +
					" and codeid = " + Common.sqlChar(s);
			
			return new StringTools()._isEmpty_asEmptyString(doGetDBServerTools()._execSQL_asString(_transSQLFormat(sql)));
		}
		

	//==============================================================================
	
		private String doGetOrganSName(String s){
			String sql = "select organsname from SYSCA_Organ z where" +
						" organid = " + Common.sqlChar(s);
			
			return new StringTools()._isEmpty_asEmptyString(doGetDBServerTools()._execSQL_asString(_transSQLFormat(sql)));
		}
		
		private String doGetOrganAName(String s){
			String sql = "select organaname from SYSCA_Organ z where" +
						" organid = " + Common.sqlChar(s);
			
			return new StringTools()._isEmpty_asEmptyString(doGetDBServerTools()._execSQL_asString(_transSQLFormat(sql)));
		}

	//==============================================================================
		private String doGetOwnershipName(String s){
			String sql = "select codename from sysca_code x where x.codekindid = 'OWA' " +
							" and x.codeid = " + Common.sqlChar(s);						
			
			return new StringTools()._isEmpty_asEmptyString(doGetDBServerTools()._execSQL_asString(_transSQLFormat(sql)));
		}
		
	//==============================================================================
		private String doGetYNName(String s){
			String result = "";						
			if("Y".equals(s)){		result = "是";
			}else{					result = "否";
			}
			return result;
		}
		
	//==============================================================================
		private String doGetSignNoName(String s){
			String sql = "select signDesc from sysca_sign x where 1 = 1 " +
							" and x.signNo = " + Common.sqlChar(s);						
			
			return new StringTools()._isEmpty_asEmptyString(doGetDBServerTools()._execSQL_asString(_transSQLFormat(sql)));
		}
	
	//==============================================================================
		private String doGetDataStateName(String s){
			String result = "";						
			if("1".equals(s)){			result = "現存";
			}else if("2".equals(s)){	result = "已減損";
			}
			return result;
		}
		
		
	//==============================================================================
			private DBServerTools doGetDBServerTools(){
				DBServerTools dbt = new DBServerTools();
				dbt._setDatabase(new Database());
				return dbt;
			}
		
	//==============================================================================			
			private String doGetProofDoc(String enterOrg,String docNo){
				String sql="select docname from UNTMP_DOC " +
							" where enterorg = " + Common.sqlChar(enterOrg) +
							" and docno=" + Common.sqlChar(docNo) +
							"";						
				
				return new StringTools()._isEmpty_asEmptyString(doGetDBServerTools()._execSQL_asString(_transSQLFormat(sql)));			
			}
			
	//==============================================================================
			
			private String doTransToCE_Year(String s){
				String result = "";
				if(Common.get(s).equals("") || Common.get(s).indexOf("NaN") != -1 ){
				}else{
					//System.out.println(s);
					result = String.valueOf(Integer.parseInt(s.substring(0,3)) + 1911) + s.substring(3);
				}
				return result;
			}
			
	//==============================================================================
			
			private String doTransToYYYMMDD(String s){
				String result = "";
				if(Common.get(s).equals("")){					
				}else{
					int year = Integer.parseInt(s.substring(0,4));
					if(year < 1911){
						year = 1911;
					}
					result = Common.formatFrontZero(String.valueOf(year - 1911),3) + s.substring(4);
				}
				return result;
			}
	//==============================================================================
			
			private String doGetNewCaseSerialNo(DataStructor ds, String[] tables){
				StringBuilder stb = new StringBuilder();
				for(String s : tables){
					if(stb.length() == 0){
						
					}else{
						stb.append(" union ");
					}
					stb.append(" select max(caseSerialNo) as caseSerialNo from ").append(s)
						.append(" where enterorg = '").append(ds.enterOrg).append("'")
//						.append(" and ownership = '").append(ds.ownership).append("'")
						.append(" and caseNo = '").append(ds.caseNo).append("'");
//						.append(" and differenceKind = '").append(ds.differenceKind).append("'")
						
				}
				
				String sql = "select case when max(caseSerialNo) is null then '1' else (max(caseSerialNo) + 1) end from (" + 
								stb.toString() +
								") a";						
				
				return new StringTools()._isEmpty_asEmptyString(doGetDBServerTools()._execSQL_asString(_transSQLFormat(sql)));		
			}
		//==============================================================================
			
			private String doGetDefaultValue(String enterOrg, String type){
				String result = "";
				if("".equals(type)){
					
				}else{
					String sql = "select " + type + " from SYSCA_OrgArgu" +
								" where enterorg = '" + enterOrg + "'";
					result = new StringTools()._isEmpty_asEmptyString(doGetDBServerTools()._execSQL_asString(_transSQLFormat(sql)));
				}
				return result;
			}

		//==============================================================================
			public String _transSQLFormat(String sql){
				SQLtransfer slf = new SQLtransfer();
				slf.columnIsCaps = false;
				slf.tableIsCaps = true;
				String newsql = slf._transSQLFormat(sql);
				return newsql;		
			}
			
		//==============================================================================
			private methodHelper doQueryData(String columnName){return new methodHelper(columnName);}
			
			public class methodHelper{
				private String columnName = "";
				private String table = "";
				private String condition;
				public methodHelper(String columnName){			this.columnName = columnName;}
				public methodHelper _from(String table){		this.table = table;return this;}
				public methodHelper _with(String condition){	this.condition = condition;return this;}
							
				public String _toString(){		
					StringBuilder stb = new StringBuilder();
					stb.append("select ").append(columnName).append(" from ").append(table).append(
							" where 1=1 ").append(
							condition);
					
					return new StringTools()._isEmpty_asEmptyString(doGetDBServerTools()._execSQL_asString(_transSQLFormat(stb.toString())));
				}				
			}
		
		//==============================================================================
			private methodHelper_NoChange doQueryData_NoChange(String columnName){return new methodHelper_NoChange(columnName);}
			
			public class methodHelper_NoChange{
				private String columnName = "";
				private String table = "";
				private String condition;
				public methodHelper_NoChange(String columnName){this.columnName = columnName;}
				public methodHelper_NoChange _from(String table){		this.table = table;return this;}
				public methodHelper_NoChange _with(String condition){	this.condition = condition;return this;}
							
				public String _toString(){		
					StringBuilder stb = new StringBuilder();
					stb.append("select ").append(columnName).append(" from ").append(table).append(
							" where 1=1 ").append(
							condition);
					
					Database db = new Database();
					ResultSet rs = null;
					String result = "";
					try{
						rs = db.querySQL(stb.toString());
						if(rs.next()){
							result = rs.getString(columnName);
						}
					}catch(Exception e){
						e.printStackTrace();
					}finally{
						db.closeAll();
					}
					
					return result;
				}				
			}			
			
		//==============================================================================
			private String doGetSourceKind(String className, String inputName, String inputValue, String chineseDesc) {
				StringBuffer rtnStr = new StringBuffer();
		    	
				if(className.endsWith("_RO") || className.endsWith("_QRO")){
					rtnStr.append("[<input class=\""+ className +"\" type=\"text\" name=\""+inputName+"\" size=\"3\" maxlength=\"2\" value=\""+inputValue+"\" onChange=\"getSourceKindName('"+inputName+"','"+inputName+"Name');\">]\n");
			        rtnStr.append("[<input class=\""+ className + "\" type=\"text\" name=\""+inputName+"Name\" size=\"20\" maxlength=\"50\" value=\""+chineseDesc+"\">]\n");
				}else{
			    	rtnStr.append("<input class=\""+ className +"\" type=\"text\" name=\""+inputName+"\" size=\"3\" maxlength=\"2\" value=\""+inputValue+"\" onChange=\"getSourceKindName('"+inputName+"','"+inputName+"Name');\">\n");
			        rtnStr.append("<input class=\""+ className +"\" type=\"button\" name=\"btn_"+inputName+"\" onclick=\"popSysca_Code('"+inputName+"','"+inputName+"Name','財產來源','SKD');\" value=\"...\" title=\"輔助視窗\">\n");    	
			        rtnStr.append("[<input class=\""+ className + "_RO" + "\" type=\"text\" name=\""+inputName+"Name\" size=\"20\" maxlength=\"50\" value=\""+chineseDesc+"\">]\n");
				}
				
		        return rtnStr.toString();
			}
			
		//==============================================================================
			private String doGetCause(String className, String inputName, String inputValue, String chineseDesc, String condition, String func) {
				StringBuffer rtnStr = new StringBuffer();
		    	
				if("field_Q".equals(className)){
					rtnStr.append("<input class=\""+ className +"\" type=\"text\" name=\""+inputName+"\" size=\"3\" maxlength=\"3\" value=\""+inputValue+"\" onChange=\"getCauseName('"+inputName+"','"+inputName+"Name');"+func+"\">\n");
			        rtnStr.append("<input class=\""+ className +"\" type=\"button\" name=\"btn_"+inputName+"\" onclick=\"popSysca_Code('"+inputName+"','"+inputName+"Name','增加原因','CAA','"+condition+"');\" value=\"...\" title=\"輔助視窗\">\n");    	
			        rtnStr.append("[<input class=\"field_RO" + "\" type=\"text\" name=\""+inputName+"Name\" size=\"20\" maxlength=\"50\" value=\""+chineseDesc+"\">]\n");
			        
				}else if("field_QRO".equals(className)){
					rtnStr.append("[<input class=\""+ className + "\" type=\"text\" name=\""+inputName+"\" size=\"3\" maxlength=\"3\" value=\""+inputValue+"\" onChange=\"getCauseName('"+inputName+"','"+inputName+"Name');"+func+"\">]\n");
				    rtnStr.append("[<input class=\""+ className + "\" type=\"text\" name=\""+inputName+"Name\" size=\"20\" maxlength=\"50\" value=\""+chineseDesc+"\">]\n");
			        
				}else{
					rtnStr.append("<input class=\""+ className +"\" type=\"text\" name=\""+inputName+"\" size=\"3\" maxlength=\"3\" value=\""+inputValue+"\" onChange=\"getCauseName('"+inputName+"','"+inputName+"Name');"+func+"\">\n");
					rtnStr.append("<input class=\""+ className +"\" type=\"button\" name=\"btn_"+inputName+"\" onclick=\"popSysca_Code('"+inputName+"','"+inputName+"Name','增加原因','CAA','"+condition+"');\" value=\"...\" title=\"輔助視窗\">\n");
			        rtnStr.append("[<input class=\""+ className + "_RO" + "\" type=\"text\" name=\""+inputName+"Name\" size=\"20\" maxlength=\"50\" value=\""+chineseDesc+"\">]\n");
			        
				}
				
		        return rtnStr.toString();
			}
			
		//==============================================================================
			private String doGetPopProperty(String className, String inputName, String inputValue, String chineseDesc, String preWord) {
		    	StringBuffer rtnStr = new StringBuffer();
		    	String ROStr="RO";
		    	if ("field".equals(className)) ROStr="_RO";
		    	else if (className.indexOf("RO")>0) {
		    		ROStr="";
		    		rtnStr.append("[<input class=\""+ className +"\" type=\"text\" name=\""+inputName+"\" size=\"11\" maxlength=\"11\" value=\""+inputValue+"\">");
		    		rtnStr.append("<input class=\""+ className + ROStr + "\" type=\"text\" name=\""+inputName+"Name\" size=\"20\" maxlength=\"50\" value=\""+chineseDesc+"\">]\n");    		
		    	} else {
		    		rtnStr.append("<input class=\""+ className +"\" type=\"text\" name=\""+inputName+"\" size=\"11\" maxlength=\"11\" value=\""+inputValue+"\" onChange=\"getProperty('"+inputName+"','"+inputName+"Name','"+preWord+"');changePropertyNo('"+inputName+"');\">\n");
		        	if(!"".equals(ROStr)) rtnStr.append("<input class=\""+ className +"\" type=\"button\" name=\"btn_"+inputName+"\" onclick=\"popProperty('"+inputName+"','"+inputName+"Name','"+ preWord +"');\" value=\"...\" title=\"財產編號輔助視窗\">\n");    	
		        	rtnStr.append("[<input class=\""+ className + ROStr + "\" type=\"text\" name=\""+inputName+"Name\" size=\"20\" maxlength=\"50\" value=\""+chineseDesc+"\">]\n");    		
		    	}
		        return rtnStr.toString();
		    }

		//==============================================================================
			private String doGetCountForFundType(String enterOrg){
				String result = "";
				String sql = "select codeid from SYSCA_CODE where codekindid='FUD' and codeid in (select fundno from SYSCA_FUNDORGAN where enterorg = '" + enterOrg + "')";
				
				List querylist = doGetDBServerTools()._execSQL_asList(sql);
				
				if(querylist.size() != 2){
				}else{
					result = querylist.get(0).toString();
				}
				
				return result;
			}
}
