/*
*<br>程式目的：物品主檔資料維護－接收撥入物品資料
*<br>程式代號：untne053f
*<br>程式日期：
*<br>程式作者：Yuan-Ren Jheng
*<br>--------------------------------------------------------
*<br>修改作者　　修改日期　　　修改目的
*<br>--------------------------------------------------------
*/

package unt.ch;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import util.Common;
import util.Database;
import util.QueryBean;

public class UNTCH001Q07 extends QueryBean{
	
	LogBean log = new LogBean();
	
	String Up_propertyNo;
	String Up_propertyUnit;
	String Up_material;
	String Up_limitYear;
	String Up_otherPropertyUnit;
	String Up_otherMaterial;
	String Up_otherLimitYear;
	String Up_cause;
	String Up_propertyKind;
	String Up_fundType;
	String Up_originalMoveDate;
	String Up_originalKeepBureau;
	String Up_originalKeepUnit;
	String Up_originalKeeper;
	String Up_originalUseBureau;
	String Up_originalUseUnit;
	String Up_originalUser;
	String Up_originalPlace;	
	String Up_propertyNoName;
	
	public String getUp_propertyNo() {return checkGet(Up_propertyNo);}
	public void setUp_propertyNo(String upPropertyNo) {Up_propertyNo = checkSet(upPropertyNo);}
	public String getUp_propertyUnit() {return checkGet(Up_propertyUnit);}
	public void setUp_propertyUnit(String upPropertyUnit) {Up_propertyUnit = checkSet(upPropertyUnit);}
	public String getUp_material() {return checkGet(Up_material);}
	public void setUp_material(String upMaterial) {Up_material = checkSet(upMaterial);}
	public String getUp_limitYear() {return checkGet(Up_limitYear);}
	public void setUp_limitYear(String upLimitYear) {Up_limitYear = checkSet(upLimitYear);}
	public String getUp_otherPropertyUnit() {return checkGet(Up_otherPropertyUnit);}
	public void setUp_otherPropertyUnit(String upOtherPropertyUnit) {Up_otherPropertyUnit = checkSet(upOtherPropertyUnit);}
	public String getUp_otherMaterial() {return checkGet(Up_otherMaterial);}
	public void setUp_otherMaterial(String upOtherMaterial) {Up_otherMaterial = checkSet(upOtherMaterial);}
	public String getUp_otherLimitYear() {return checkGet(Up_otherLimitYear);}
	public void setUp_otherLimitYear(String upOtherLimitYear) {Up_otherLimitYear = checkSet(upOtherLimitYear);}
	public String getUp_cause() {return checkGet(Up_cause);}
	public void setUp_cause(String upCause) {Up_cause = checkSet(upCause);}
	public String getUp_propertyKind() {return checkGet(Up_propertyKind);}
	public void setUp_propertyKind(String upPropertyKind) {Up_propertyKind = checkSet(upPropertyKind);}
	public String getUp_fundType() {return checkGet(Up_fundType);}
	public void setUp_fundType(String upFundType) {Up_fundType = checkSet(upFundType);}
	public String getUp_originalMoveDate() {return checkGet(Up_originalMoveDate);}
	public void setUp_originalMoveDate(String upOriginalMoveDate) {Up_originalMoveDate = checkSet(upOriginalMoveDate);}
	public String getUp_originalKeepBureau() {return checkGet(Up_originalKeepBureau);}
	public void setUp_originalKeepBureau(String upOriginalKeepBureau) {Up_originalKeepBureau = checkSet(upOriginalKeepBureau);}
	public String getUp_originalKeepUnit() {return checkGet(Up_originalKeepUnit);}
	public void setUp_originalKeepUnit(String upOriginalKeepUnit) {Up_originalKeepUnit = checkSet(upOriginalKeepUnit);}
	public String getUp_originalKeeper() {return checkGet(Up_originalKeeper);}
	public void setUp_originalKeeper(String upOriginalKeeper) {Up_originalKeeper = checkSet(upOriginalKeeper);}
	public String getUp_originalUseBureau() {return checkGet(Up_originalUseBureau);}
	public void setUp_originalUseBureau(String upOriginalUseBureau) {Up_originalUseBureau = checkSet(upOriginalUseBureau);}
	public String getUp_originalUseUnit() {return checkGet(Up_originalUseUnit);}
	public void setUp_originalUseUnit(String upOriginalUseUnit) {Up_originalUseUnit = checkSet(upOriginalUseUnit);}
	public String getUp_originalUser() {return checkGet(Up_originalUser);}
	public void setUp_originalUser(String upOriginalUser) {Up_originalUser = checkSet(upOriginalUser);}
	public String getUp_originalPlace() {return checkGet(Up_originalPlace);}
	public void setUp_originalPlace(String upOriginalPlace) {Up_originalPlace = checkSet(upOriginalPlace);}
	public String getUp_propertyNoName() {return checkGet(Up_propertyNoName);}
	public void setUp_propertyNoName(String upPropertyNoName) {Up_propertyNoName = checkSet(upPropertyNoName);}
	

	String enterOrg;
	String ownership;
	String caseNo;
	String lotNo;
	String enterDate;	
	
	String Dn_propertyNo;
	String Dn_serialNo;
	String Dn_propertyName;
	String Dn_propertyName1;
	String Dn_adjustBookAmount;
	String Dn_adjustBookValue;
	String Dn_propertyUnit;
	String Dn_material;
	String Dn_limitYear;

	public String getEnterOrg() {return checkGet(enterOrg);}
	public void setEnterOrg(String enterOrg) {this.enterOrg = checkSet(enterOrg);}
	public String getOwnership() {return checkGet(ownership);}
	public void setOwnership(String ownership) {this.ownership = checkSet(ownership);}
	public String getCaseNo() {return checkGet(caseNo);}
	public void setCaseNo(String caseNo) {this.caseNo = checkSet(caseNo);}
	public String getLotNo() {return checkGet(lotNo);}
	public void setLotNo(String lotNo) {this.lotNo = checkSet(lotNo);}
	public String getEnterDate() {return checkGet(enterDate);}
	public void setEnterDate(String enterDate) {this.enterDate = checkSet(enterDate);}
	
	public String getDn_propertyNo() {return checkGet(Dn_propertyNo);}
	public void setDn_propertyNo(String dnPropertyNo) {Dn_propertyNo = checkSet(dnPropertyNo);}	
	public String getDn_serialNo() {return checkGet(Dn_serialNo);}
	public void setDn_serialNo(String dnSerialNo) {Dn_serialNo = checkSet(dnSerialNo);}
	public String getDn_propertyName() {return checkGet(Dn_propertyName);}
	public void setDn_propertyName(String dnPropertyName) {Dn_propertyName = checkSet(dnPropertyName);}
	public String getDn_propertyName1() {return checkGet(Dn_propertyName1);}
	public void setDn_propertyName1(String dnPropertyName1) {Dn_propertyName1 = checkSet(dnPropertyName1);}
	public String getDn_adjustBookAmount() {return checkGet(Dn_adjustBookAmount);}
	public void setDn_adjustBookAmount(String dnAdjustBookAmount) {Dn_adjustBookAmount = checkSet(dnAdjustBookAmount);}
	public String getDn_adjustBookValue() {return checkGet(Dn_adjustBookValue);}
	public void setDn_adjustBookValue(String dnAdjustBookValue) {Dn_adjustBookValue = checkSet(dnAdjustBookValue);}
	public String getDn_propertyUnit() {return checkGet(Dn_propertyUnit);}
	public void setDn_propertyUnit(String dnPropertyUnit) {Dn_propertyUnit = checkSet(dnPropertyUnit);}
	public String getDn_material() {return checkGet(Dn_material);}
	public void setDn_material(String dnMaterial) {Dn_material = checkSet(dnMaterial);}
	public String getDn_limitYear() {return checkGet(Dn_limitYear);}
	public void setDn_limitYear(String dnLimitYear) {Dn_limitYear = checkSet(dnLimitYear);}
	
	String q_differenceKind;
	String q_oldEnterOrg;
	String q_ownership;
	String q_caseNo;
	String q_oldEnterOrgName;
	String q_newEnterOrgReceive;
	
	public String getQ_differenceKind() {return checkGet(q_differenceKind);}
	public void setQ_differenceKind(String q_differenceKind) {this.q_differenceKind = checkSet(q_differenceKind);}
	public String getQ_oldEnterOrg() {return checkGet(q_oldEnterOrg);}
	public void setQ_oldEnterOrg(String qOldEnterOrg) {q_oldEnterOrg = checkSet(qOldEnterOrg);}
	public String getQ_ownership() {return checkGet(q_ownership);}
	public void setQ_ownership(String qOwnership) {q_ownership = checkSet(qOwnership);}
	public String getQ_caseNo() {return checkGet(q_caseNo);}
	public void setQ_caseNo(String qCaseNo) {q_caseNo = checkSet(qCaseNo);}
	public String getQ_oldEnterOrgName() {return checkGet(q_oldEnterOrgName);}
	public void setQ_oldEnterOrgName(String qOldEnterOrgName) {q_oldEnterOrgName = checkSet(qOldEnterOrgName);}
	public String getQ_newEnterOrgReceive() {return checkGet(q_newEnterOrgReceive);}
	public void setQ_newEnterOrgReceive(String qNewEnterOrgReceive) {q_newEnterOrgReceive = checkSet(qNewEnterOrgReceive);}
	
	String[] strKeySet;
	public String[] getsKeySet(){ return strKeySet; }
	public void setsKeySet(String[] s){ strKeySet=s; }
	
	

	protected Map execSQLToDB_ForSingleColumn(String sql){
		Database db = new Database();
		ResultSet rs;
		Map map=new LinkedHashMap();
		
		try{
			log.outputLogDebug(sql);
			
			rs=db.querySQL(sql);
			ResultSetMetaData rsmd = rs.getMetaData();
			while(rs.next()){
				for(int i=1;i<=rsmd.getColumnCount();i++){
					map.put(rsmd.getColumnName(i), rs.getString(i));
				}
			}
		}catch(Exception e){
			log.outputLogError("Exception : "+e.getMessage()+"\n"+sql);
			
		}finally{
			db.closeAll();
		}	
		return map;
	}

	protected String getNameData(String NameCode, String sql){
		String resultStr="";
		Map map;
		try{
			map=execSQLToDB_ForSingleColumn(sql);
			resultStr=map.get(NameCode.toUpperCase()).toString();
			map.clear();			
		}catch(Exception e){
			
		}finally{
			map=null;
		}
		return resultStr;
	}

	protected void execSQL_ForNoResult(String sql){
		Database db=new Database();
		try{
			log.outputLogDebug(sql);
			db.querySQL(sql);
		}catch(Exception e){
			log.outputLogError("Exception : "+e.getMessage()+"\n"+sql);
		}finally{
			db.closeAll();
		}
	}
	
	protected void execSQL_ForNoResult(String sql, boolean NoLog){
		Database db=new Database();
		try{
			log.outputLogDebug(sql);
			db.querySQL(sql);
		}catch(Exception e){
			if(NoLog){
				
			}else{
				log.outputLogError("Exception : "+e.getMessage()+"\n"+sql);
			}
		}finally{
			db.closeAll();
		}
	}
}


class LogBean{
	
	//<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
	//可使用方法
	private boolean showMethodName=false;
		
	public void outputLogInfo(){						setLoggerOutput("INFO", "", "");}
	public void outputLogInfo(String str){				setLoggerOutput("INFO", str, "");}
	public void outputLogInfo(String str, String col){	setLoggerOutput("INFO", str, col);}
	
	
	public void outputLogDebug(){						setLoggerOutput("DEBUG", "", "");}
	public void outputLogDebug(String str){				setLoggerOutput("DEBUG", str, "");}
	public void outputLogDebug(String str, String col){	setLoggerOutput("DEBUG", str, col);}
	
	
	public void outputLogError(){						setLoggerOutput("ERROR", "", "");}
	public void outputLogError(String str){				setLoggerOutput("ERROR", str, "");}
	public void outputLogError(String str, String col){	setLoggerOutput("DEBUG", str, col);}
	
	
	//>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
	
	
	Logger logger = Logger.getLogger("");
	
	private String SourceName ;
	private String MethodName ;
	
	
	private void getMessage(String col){
		StackTraceElement[] eles =  Thread.currentThread().getStackTrace();
		int itemp=0;
		
		for(int i=0;i<eles.length;i++){
			if(showMethodName){	System.out.println(i + " : " + eles[i].getMethodName());}
			
			try{
				if("_jspService".equals(eles[i].getMethodName())){

					if("".equals(col)){		itemp = i-1;
					}else{					itemp = i-Integer.parseInt(col);	
					}
							
				}
				
				if(itemp==0){
					
				}else{
					SourceName = eles[itemp].getClassName();
					MethodName = eles[itemp].getMethodName();
					if(showMethodName){}else{break;}
				}
				
			}catch(Exception e){
				e.printStackTrace();
			}
		}				
		eles = null;
	}
	
	
	
	private void setLoggerOutput(String type, String str, String col){
		getMessage(col);
		
		String outputStr="";
		if("".equals(Common.get(str))){	outputStr = SourceName + " => " + MethodName;
		}else{				outputStr = SourceName + " => " + MethodName + " : \n" + str;
		}
		
		if("INFO".equals(type)){			logger.info(outputStr);	
		}else if("DEBUG".equals(type)){		logger.debug(outputStr);			
		}else if("ERROR".equals(type)){		logger.error(outputStr);			
		}
		outputStr = null;
	}
		
}

