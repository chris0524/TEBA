/*
*<br>程式目的：土地增減值作業－批次新增明細資料
*<br>程式代號：untla020f
*<br>程式日期：0940928
*<br>程式作者：judy
*<br>--------------------------------------------------------
*<br>修改作者　　修改日期　　　修改目的
*<br>--------------------------------------------------------
*/

package unt.la;

import java.sql.ResultSet;
import java.util.ArrayList;
import util.*;

public class UNTLA020F extends SuperBean{

String enterOrg;
String enterOrgName;
String ownership;
String propertyNo;
String propertyName;
String serialNo;
String propertyName1;
String signNo;
String signNo1;
String signNo2;
String signNo3;
String signNo4;
String signNo5;
String propertyKind;
String fundType;
String valuable;
String taxCredit;
String grass;

String useSeparate;
String useKind;
String sourceDate;
String oldPropertyNo;
String oldPropertyName;
String oldSerialNo;
String caseNo;
String originalBV;

String q_enterOrg; 
String q_ownership; 
String q_caseNo;
String q_adjustDate;
String q_verify;

String q_cause;
String q_cause1;
String q_dataState;
String q_propertyNoS;
String q_propertyNoE;
String q_propertyNoSName;
String q_propertyNoEName;
String q_serialNoS;
String q_serialNoE;
String q_signNo1;
String q_signNo2;
String q_signNo3;
String q_signNo4;
String q_signNo5;
String q_signNo = "";
String q_propertyKind;
String q_fundType;
String q_valuable;
String q_taxCredit;
String q_grass;
String sSQL = "";
String strKeySet[] = null;

String area;
String holdNume;
String holdDeno;
String holdArea;
String bookUnit;
String bookValue;

public String getArea(){ return checkGet(area); }
public void setArea(String s){ area=checkSet(s); }
public String getHoldNume(){ return checkGet(holdNume); }
public void setHoldNume(String s){ holdNume=checkSet(s); }
public String getHoldDeno(){ return checkGet(holdDeno); }
public void setHoldDeno(String s){ holdDeno=checkSet(s); }
public String getHoldArea(){ return checkGet(holdArea); }
public void setHoldArea(String s){ holdArea=checkSet(s); }
public String getBookUnit(){ return checkGet(bookUnit); }
public void setBookUnit(String s){ bookUnit=checkSet(s); }
public String getBookValue(){ return checkGet(bookValue); }
public void setBookValue(String s){ bookValue=checkSet(s); }

public String getOriginalBV(){ return checkGet(originalBV); }
public void setOriginalBV(String s){ originalBV=checkSet(s); }
public String getsSQL(){ return checkGet(sSQL); }
public void setsSQL(String s){ sSQL=checkSet(s); }
public String[] getsKeySet(){ return strKeySet; }
public void setsKeySet(String[] s){ strKeySet=s; }
public String getEnterOrg(){ return checkGet(enterOrg); }
public void setEnterOrg(String s){ enterOrg=checkSet(s); }
public String getEnterOrgName(){ return checkGet(enterOrgName); }
public void setEnterOrgName(String s){ enterOrgName=checkSet(s); }
public String getOwnership(){ return checkGet(ownership); }
public void setOwnership(String s){ ownership=checkSet(s); }
public String getPropertyNo(){ return checkGet(propertyNo); }
public void setPropertyNo(String s){ propertyNo=checkSet(s); }
public String getPropertyName(){ return checkGet(propertyName); }
public void setPropertyName(String s){ propertyName=checkSet(s); }
public String getSerialNo(){ return checkGet(serialNo); }
public void setSerialNo(String s){ serialNo=checkSet(s); }
public String getPropertyName1(){ return checkGet(propertyName1); }
public void setPropertyName1(String s){ propertyName1=checkSet(s); }

public String getSignNo(){ return checkGet(signNo); }
public void setSignNo(String s){ signNo=checkSet(s); }
public String getSignNo1(){ return checkGet(signNo1); }
public void setSignNo1(String s){ signNo1=checkSet(s); }
public String getSignNo2(){ return checkGet(signNo2); }
public void setSignNo2(String s){ signNo2=checkSet(s); }
public String getSignNo3(){ return checkGet(signNo3); }
public void setSignNo3(String s){ signNo3=checkSet(s); }
public String getSignNo4(){ return checkGet(signNo4); }
public void setSignNo4(String s){ signNo4=checkSet(s); }
public String getSignNo5(){ return checkGet(signNo5); }
public void setSignNo5(String s){ signNo5=checkSet(s); }

public String getPropertyKind(){ return checkGet(propertyKind); }
public void setPropertyKind(String s){ propertyKind=checkSet(s); }
public String getFundType(){ return checkGet(fundType); }
public void setFundType(String s){ fundType=checkSet(s); }
public String getValuable(){ return checkGet(valuable); }
public void setValuable(String s){ valuable=checkSet(s); }
public String getTaxCredit(){ return checkGet(taxCredit); }
public void setTaxCredit(String s){ taxCredit=checkSet(s); }
public String getGrass(){ return checkGet(grass); }
public void setGrass(String s){ grass=checkSet(s); }
public String getUseSeparate(){ return checkGet(useSeparate); }
public void setUseSeparate(String s){ useSeparate=checkSet(s); }
public String getUseKind(){ return checkGet(useKind); }
public void setUseKind(String s){ useKind=checkSet(s); }
public String getSourceDate(){ return checkGet(sourceDate); }
public void setSourceDate(String s){ sourceDate=checkSet(s); }
public String getOldPropertyNo(){ return checkGet(oldPropertyNo); }
public void setOldPropertyNo(String s){ oldPropertyNo=checkSet(s); }
public String getOldPropertyName(){ return checkGet(oldPropertyName); }
public void setOldPropertyName(String s){ oldPropertyName=checkSet(s); }
public String getOldSerialNo(){ return checkGet(oldSerialNo); }
public void setOldSerialNo(String s){ oldSerialNo=checkSet(s); }
public String getCaseNo(){ return checkGet(caseNo); }
public void setCaseNo(String s){ caseNo=checkSet(s); }

public String getQ_dataState(){ return checkGet(q_dataState); }
public void setQ_dataState(String s){ q_dataState=checkSet(s); }
public String getQ_propertyNoS(){ return checkGet(q_propertyNoS); }
public void setQ_propertyNoS(String s){ q_propertyNoS=checkSet(s); }
public String getQ_propertyNoE(){ return checkGet(q_propertyNoE); }
public void setQ_propertyNoE(String s){ q_propertyNoE=checkSet(s); }
public String getQ_propertyNoSName(){ return checkGet(q_propertyNoSName); }
public void setQ_propertyNoSName(String s){ q_propertyNoSName=checkSet(s); }
public String getQ_propertyNoEName(){ return checkGet(q_propertyNoEName); }
public void setQ_propertyNoEName(String s){ q_propertyNoEName=checkSet(s); }
public String getQ_serialNoS(){ return checkGet(q_serialNoS); }
public void setQ_serialNoS(String s){ q_serialNoS=checkSet(s); }
public String getQ_serialNoE(){ return checkGet(q_serialNoE); }
public void setQ_serialNoE(String s){ q_serialNoE=checkSet(s); }
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
public String getQ_propertyKind(){ return checkGet(q_propertyKind); }
public void setQ_propertyKind(String s){ q_propertyKind=checkSet(s); }
public String getQ_fundType(){ return checkGet(q_fundType); }
public void setQ_fundType(String s){ q_fundType=checkSet(s); }
public String getQ_valuable(){ return checkGet(q_valuable); }
public void setQ_valuable(String s){ q_valuable=checkSet(s); }
public String getQ_taxCredit(){ return checkGet(q_taxCredit); }
public void setQ_taxCredit(String s){ q_taxCredit=checkSet(s); }
public String getQ_grass(){ return checkGet(q_grass); }
public void setQ_grass(String s){ q_grass=checkSet(s); }
public String getQ_caseNo(){ return checkGet(q_caseNo); }
public void setQ_caseNo(String s){ q_caseNo=checkSet(s); }
public String getQ_adjustDate(){ return checkGet(q_adjustDate); }
public void setQ_adjustDate(String s){ q_adjustDate=checkSet(s); }
public String getQ_verify(){ return checkGet(q_verify); }
public void setQ_verify(String s){ q_verify=checkSet(s); }
public String getQ_cause(){ return checkGet(q_cause); }
public void setQ_cause(String s){ q_cause=checkSet(s); }
public String getQ_cause1(){ return checkGet(q_cause1); }
public void setQ_cause1(String s){ q_cause1=checkSet(s); }
public String getQ_signNo(){ return checkGet(q_signNo); }
public void setQ_signNo(String s){ q_signNo=checkSet(s); }
public String getQ_enterOrg(){ return checkGet(q_enterOrg); }
public void setQ_enterOrg(String s){ q_enterOrg=checkSet(s); }
public String getQ_ownership(){ return checkGet(q_ownership); }
public void setQ_ownership(String s){ q_ownership=checkSet(s); }

String toggleAll;
public String getToggleAll(){ if (!"".equals(checkGet(toggleAll))) return "checked"; else return "unchecked"; }    
public void setToggleAll(String s){ toggleAll=checkSet(s); }    

String closing;
public String getClosing(){ return checkGet(closing); }
public void setClosing(String s){ closing=checkSet(s); }

/**
 * <br>
 * <br>目的：傳回insert sql
 * <br>參數：無 
 * <br>傳回：無 ; 直接更改變數為新增成功與否
*/	
protected String[] getInsertSQL(){
	StringBuffer sbSQL = new StringBuffer("");
	Database db = new Database();  
	int getcut=0;
	if(getsKeySet()!=null)
		getcut = getsKeySet().length;	//有勾選的list中資料數
	//String sSQL = "";
	//String[] execSQLArray = new String[getcut];
	String[] strKeys = null;
	int i = 0;	
	ResultSet rs = null;
	//int counter=0;
	try {
		for (i=0; i<getcut; i++) {	
			strKeys = getsKeySet()[i].split(",");
			//先找出 Land 的值
			String landsql = " select  " + 
							 " propertyNo, " +
							 " serialNo, " +
							 " propertyName1, " +
							 " signNo, " +
							 " propertyKind, " +
							 " fundType, " +
							 " valuable, " +
							 " taxCredit, " +
							 " grass, " +
							 " useSeparate, " +
							 " useKind, " +
							 " sourceDate, "+
							 " oldPropertyNo, " +
							 " oldSerialNo, " +
							 " originalBV, " +
							 " Area,"+
							 " holdNume,"+
							 " holdDeno,"+
							 " holdArea,"+
							 " bookUnit,"+
							 " bookValue "+
							 " from UNTLA_Land "+
							 " where enterOrg = " + Common.sqlChar(strKeys[0]) +
							 " and ownership = " + Common.sqlChar(strKeys[1]) +
							 " and propertyNo = " + Common.sqlChar(strKeys[2]) +
							 " and serialNo = " + Common.sqlChar(strKeys[3]) +
							 "";
			rs = db.querySQL(landsql);
			if (rs.next()){
			//insert 資料
				sbSQL.append(" insert into UNTLA_AdjustDetail(" ).append( 
				  " enterOrg,").append(
				  " ownership,").append(
				  " caseNo,").append(
				  " propertyNo,").append(
				  " serialNo,").append(
				  " propertyName1,").append(
				  " signNo,").append(
				  " cause,").append(
				  " cause1,").append(
				  " adjustDate,").append(
				  " verify,").append(
				  " propertyKind,").append(
				  " fundType,").append(
				  " valuable, ").append(
				  " taxCredit,").append(
				  " grass,").append(
				  " newArea,").append(
				  " newHoldNume,").append(
				  " newHoldDeno,").append(
				  " newHoldArea,").append(
				  " newBookUnit,").append(
				  " newBookValue,").append(
				  " oldArea,").append(
				  " oldHoldNume,").append(
				  " oldHoldDeno,").append(
				  " oldHoldArea,").append(
				  " oldBookUnit,").append(
				  " oldBookValue,").append(
				  " useSeparate,").append(
				  " useKind,").append(
				  " sourceDate,").append(
				  " oldPropertyNo,").append(
				  " oldSerialNo,").append(
				  " adjustArea,").append(
				  " adjustHoldArea,").append(
				  " adjustBookUnit,").append(
				  " adjustBookValue,").append(
				  " originalBV,").append(
				  " closing,").append(
				  " editID,").append(
				  " editDate,").append(
				  " editTime ").append(
				  ") values ( ").append( 
					Common.sqlChar(q_enterOrg)                    ).append( "," ).append(
					Common.sqlChar(q_ownership)                   ).append( "," ).append(
					Common.sqlChar(q_caseNo)                      ).append( "," ).append(
					Common.sqlChar(rs.getString("propertyNo"))    ).append( "," ).append(
					Common.sqlChar(rs.getString("serialNo"))      ).append( "," ).append(
					Common.sqlChar(rs.getString("propertyName1")) ).append( "," ).append(
					Common.sqlChar(rs.getString("signNo"))        ).append( "," ).append(
					Common.sqlChar(q_cause)                       ).append( "," ).append(
					Common.sqlChar(q_cause1)                      ).append( "," ).append(
					Common.sqlChar(q_adjustDate)                  ).append( "," ).append(
					Common.sqlChar("N")					  ).append( "," ).append(
					Common.sqlChar(rs.getString("propertyKind"))  ).append( "," ).append(
					Common.sqlChar(rs.getString("fundType"))      ).append( "," ).append(
					Common.sqlChar(rs.getString("valuable"))      ).append( "," ).append(
					Common.sqlChar(rs.getString("taxCredit"))     ).append( "," ).append(
					Common.sqlChar(rs.getString("grass"))         ).append( "," ).append(
					Common.sqlChar(rs.getString("area"))          ).append( "," ).append(
					Common.sqlChar(rs.getString("holdNume"))      ).append( "," ).append(
					Common.sqlChar(rs.getString("holdDeno"))      ).append( "," ).append(
					Common.sqlChar(rs.getString("holdArea"))      ).append( "," ).append(
					Common.sqlChar(rs.getString("bookUnit"))      ).append( "," ).append(
					Common.sqlChar(rs.getString("bookValue"))     ).append( "," ).append(
					Common.sqlChar(rs.getString("area"))          ).append( "," ).append(
					Common.sqlChar(rs.getString("holdNume"))      ).append( "," ).append(
					Common.sqlChar(rs.getString("holdDeno"))      ).append( "," ).append(
					Common.sqlChar(rs.getString("holdArea"))      ).append( "," ).append(
					Common.sqlChar(rs.getString("bookUnit"))      ).append( "," ).append(
					Common.sqlChar(rs.getString("bookValue"))     ).append( "," ).append(
					Common.sqlChar(rs.getString("useSeparate"))   ).append( "," ).append(
					Common.sqlChar(rs.getString("useKind"))       ).append( "," ).append(
					Common.sqlChar(rs.getString("sourceDate"))    ).append( "," ).append(
					Common.sqlChar(rs.getString("oldPropertyNo")) ).append( "," ).append(
					Common.sqlChar(rs.getString("oldSerialNo"))   ).append( "," ).append(
					" '0','0','0','0',  " ).append(
					Common.sqlChar(rs.getString("originalBV"))    ).append( "," ).append(
					Common.sqlChar("N")   					      ).append( "," ).append(
					Common.sqlChar(getEditID())                   ).append( "," ).append(
					Common.sqlChar(getEditDate())                 ).append( "," ).append(
					Common.sqlChar(getEditTime())                 ).append(
					" ):;:");
			}
		}
		//setStateQueryOneSuccess();
	} catch (Exception e) {
		e.printStackTrace();
	} finally {
		db.closeAll();
	}
	//=====================================================================
	//execSQLArray[i] = sSQL;
	setStateInsertSuccess();
	return sbSQL.toString().split(":;:");
}

/**
 * <br>
 * <br>目的：依主鍵查詢單一資料
 * <br>參數：無
 * <br>傳回：傳回本物件
*/

public UNTLA020F  queryOne() throws Exception{
	Database db = new Database();
	UNTLA020F obj = this;
	try {
		String sql=" select a.enterOrg, a.ownership, a.caseNo, a.propertyNo, " +
			" a.serialNo, a.propertyName1, a.signNo,  " +
			" a.propertyKind, a.fundType, " + 
			" a.valuable, a.taxCredit, a.grass, "+
			" a.area, a.holdNume, a.holdDeno, a.holdArea,a.bookUnit, a.bookValue,  " +
			" a.useSeparate, " +
			" a.useKind, a.sourceDate,  " +
			" a.oldPropertyNo, a.oldSerialNo, " +
			" c.organSName as enterOrgName, d.propertyName, e.propertyName as oldpropertyName, "+
			" a.originalBV,a.editID,a.editDate,a.editTime " +
			" from UNTLA_Land a, SYSCA_ORGAN c, "+
			" SYSPK_PropertyCode d" +
			" left join SYSPK_PropertyCode e on a.oldpropertyNo = e.propertyNo" +
			" where 1=1 " +
			" and a.enterOrg 	= " + Common.sqlChar(enterOrg) +
			" and a.ownership 	= " + Common.sqlChar(ownership) +
			" and a.propertyNo 	= " + Common.sqlChar(propertyNo) +
			" and a.serialNo 	= " + Common.sqlChar(serialNo) +
			" and a.enterOrg	= c.organID " +
			" and a.propertyNo 	= d.propertyNo "+
			" order by a.signNo " +
			"";
		ResultSet rs = db.querySQL(sql);
		if (rs.next()){
			obj.setEnterOrg(rs.getString("enterOrg"));
			obj.setEnterOrgName(rs.getString("enterOrgName"));
			obj.setOwnership(rs.getString("ownership"));
			obj.setPropertyNo(rs.getString("propertyNo"));
			obj.setPropertyName(rs.getString("propertyName"));
			obj.setSerialNo(rs.getString("serialNo"));
			obj.setPropertyName1(rs.getString("propertyName1"));
			obj.setPropertyKind(rs.getString("propertyKind"));
			obj.setFundType(rs.getString("fundType"));
			obj.setValuable(rs.getString("valuable"));
			obj.setTaxCredit(rs.getString("taxCredit"));
			obj.setGrass(rs.getString("grass"));
			obj.setArea(rs.getString("area"));
			obj.setHoldNume(rs.getString("holdNume"));
			obj.setHoldDeno(rs.getString("holdDeno"));
			obj.setHoldArea(rs.getString("holdArea"));
			obj.setBookUnit(rs.getString("bookUnit"));
			obj.setBookValue(rs.getString("bookValue"));
			obj.setUseSeparate(rs.getString("useSeparate"));
			obj.setUseKind(rs.getString("useKind"));
			obj.setSourceDate(rs.getString("sourceDate"));
			obj.setOldPropertyNo(rs.getString("oldPropertyNo"));
			obj.setOldPropertyName(rs.getString("oldpropertyName"));
			obj.setOldSerialNo(rs.getString("oldSerialNo"));
			obj.setOriginalBV(rs.getString("originalBV"));
			obj.setEditID(rs.getString("editID"));
			obj.setEditDate(rs.getString("editDate"));
			obj.setEditTime(rs.getString("editTime"));
			obj.setSignNo(rs.getString("signNo"));
			obj.setSignNo1(rs.getString("signNo").substring(0,1)+"000000");
			obj.setSignNo2(rs.getString("signNo").substring(0,3)+"0000");
			obj.setSignNo3(rs.getString("signNo").substring(0,7));
			obj.setSignNo4(rs.getString("signNo").substring(7,11));
			obj.setSignNo5(rs.getString("signNo").substring(11,15));			
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
	int counter=0;
	try {
		String sql= " select a.enterOrg, d.propertyName,a.ownership, a.caseNo, "+
					" a.propertyNo, a.serialNo," +
					" a.signNo," +
					" (case a.propertyKind when '01' then '公務用' when '02' then '公共用' when '03' then '事業用' when '04' then '非公用' else '' end) propertyKind,"+
					" a.area, a.bookValue, a.sourceDate "+
					" from UNTLA_Land a ,SYSCA_Organ c ,SYSPK_PropertyCode d" +
					" where a.verify='Y' and a.dataState ='1' and a.enterOrg = c.organID and a.propertyNo = d.propertyNo "+
					" and a.enterOrg 	= " + Common.sqlChar(q_enterOrg) +
					" and a.ownership 	= " + Common.sqlChar(q_ownership) +
					" and (a.enterOrg)  not in ( " +
					"  select enterOrg from UNTLA_AdjustDetail where verify='N' " +
					"  ) " +					
					" and (a.ownership)  not in ( " +
					"  select ownership from UNTLA_AdjustDetail where verify='N' " +
					"  ) " +
					" and (a.propertyNo)  not in ( " +
					"  select propertyNo from UNTLA_AdjustDetail where verify='N' " +
					"  ) " +
					" and (a.serialNo)  not in ( " +
					"  select serialNo from UNTLA_AdjustDetail where verify='N' " +
					"  ) " +
					
					" and (a.enterOrg)  not in ( " +
					"  select enterOrg from UNTLA_ReduceDetail " +
					"  ) "  +
					" and (a.ownership)  not in ( " +
					"  select ownership from UNTLA_ReduceDetail " +
					"  ) "  +
					" and (a.propertyNo)  not in ( " +
					"  select propertyNo from UNTLA_ReduceDetail " +
					"  ) "  +
					" and (a.serialNo)  not in ( " +
					"  select serialNo from UNTLA_ReduceDetail " +
					"  ) ";

			if (!"".equals(getQ_propertyNoS()))
				sql+=" and a.propertyNo >= " + Common.sqlChar(getQ_propertyNoS());		
			if (!"".equals(getQ_propertyNoE()))
				sql+=" and a.propertyNo<=" + Common.sqlChar(getQ_propertyNoE());						
			if (!"".equals(getQ_serialNoS())){
				setQ_serialNoS(Common.formatFrontZero(getQ_serialNoS(),7));
				sql+=" and a.serialNo >= " + Common.sqlChar(getQ_serialNoS()) ;
			}
			if (!"".equals(getQ_serialNoE())){
				setQ_serialNoE(Common.formatFrontZero(getQ_serialNoE(),7));
				sql+=" and a.serialNo <= " + Common.sqlChar(getQ_serialNoE()) ;
			}
			
			if (!"".equals(getQ_signNo1()))
				q_signNo=getQ_signNo1().substring(0,1)+"______";
			if (!"".equals(getQ_signNo2()))
				q_signNo=getQ_signNo2().substring(0,3)+"____";			
			if (!"".equals(getQ_signNo3())){
				if (getQ_signNo3().length()==4){
					q_signNo="E__" + getQ_signNo3();
				}else{
					q_signNo=getQ_signNo3();
				}	
			}
			if (!"".equals(getQ_signNo4())){
				setQ_signNo4(Common.formatFrontZero(getQ_signNo4(),4));
				setQ_signNo5(Common.formatFrontZero(getQ_signNo5(),4));	
				if ("".equals(q_signNo)){
					q_signNo="_______"+getQ_signNo4()+getQ_signNo5();
				}else{
					q_signNo=q_signNo+getQ_signNo4()+getQ_signNo5();				
				}
			}	

			if (!"".equals(q_signNo))
				sql+=" and a.signNo like '" + q_signNo + "%'" ;			
			if (!"".equals(getQ_propertyKind()))
				sql+=" and a.propertyKind = " + Common.sqlChar(getQ_propertyKind()) ;
			if (!"".equals(getQ_fundType()))
				sql+=" and a.fundType = " + Common.sqlChar(getQ_fundType()) ;
			if (!"".equals(getQ_valuable()))
				sql+=" and a.valuable = " + Common.sqlChar(getQ_valuable()) ;
			if (!"".equals(getQ_taxCredit()))
				sql+=" and a.taxCredit = " + Common.sqlChar(getQ_taxCredit()) ;
			if (!"".equals(getQ_grass()))
				sql+=" and a.grass = " + Common.sqlChar(getQ_grass()) ;
			
			sql+=" order by a.signNo ";
		ResultSet rs = db.querySQL(sql);
		//把查到的資料放入list裡..
		//和.jsp裡的 List區 內容要搭配起來
		while (rs.next()){
			counter++;
			String rowArray[]=new String[8];
			rowArray[0]=rs.getString("enterOrg");
			rowArray[1]=rs.getString("ownership");
			rowArray[2]=rs.getString("propertyNo"); 
			rowArray[3]=rs.getString("serialNo"); 
			rowArray[4]=rs.getString("propertyName");
			rowArray[5]=getSignDescName(rs.getString("signNo").substring(0,7))+rs.getString("signNo").substring(7,11)+"-"+rs.getString("signNo").substring(11); 
			rowArray[6]=rs.getString("area"); 
			rowArray[7]=rs.getString("bookValue"); 
			objList.add(rowArray);
			if (counter==getListLimit()){
				setErrorMsg(getListLimitError());
				break;
			}
		}
		setStateQueryAllSuccess();
	} catch (Exception e) {
		e.printStackTrace();
	} finally {
		db.closeAll();
	}
	return objList;
}

private String getSignDescName(String signNo){
	Database db = null;
	ResultSet rs = null;
	String sql = null;
	String result = null;
	try{
		sql = "select signdesc from SYSCA_SIGN where" +
				" signNo = " + Common.sqlChar(signNo);
		
		db = new Database();
		rs = db.querySQL(sql);
		if(rs.next()){				
			result = rs.getString("signdesc");
		}
		rs.close();
	}catch(Exception e){
		System.out.println("getSignDescName Exception => " + e.getMessage());
	}finally{
		db.closeAll();
	}
	return result;
}
}
