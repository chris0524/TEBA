/*
*<br>程式目的：土地改良物增減值作業－批次新增明細資料
*<br>程式代號：untrf016f
*<br>程式日期：0941013
*<br>程式作者：judy
*<br>--------------------------------------------------------
*<br>修改作者　　修改日期　　　修改目的
*<br>--------------------------------------------------------
*/

package unt.rf;

import java.sql.ResultSet;
import java.util.ArrayList;
import util.*;

public class UNTRF016F extends SuperBean{

String enterOrg;
String enterOrgName;
String ownership;
String propertyNo;
String propertyName;
String serialNo;
String propertyName1;
String propertyKind;
String fundType;
String valuable;
String taxCredit;
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
String q_cause2;
String q_dataState;
String q_propertyNoS;
String q_propertyNoE;
String q_propertyNoSName;
String q_propertyNoEName;
String q_serialNoS;
String q_serialNoE;
String q_propertyKind;
String q_fundType;
String q_valuable;
String q_taxCredit;
String sSQL = "";
String strKeySet[] = null;

String measure;
String bookValue;

public String getMeasure(){ return checkGet(measure); }
public void setMeasure(String s){ measure=checkSet(s); }
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

public String getPropertyKind(){ return checkGet(propertyKind); }
public void setPropertyKind(String s){ propertyKind=checkSet(s); }
public String getFundType(){ return checkGet(fundType); }
public void setFundType(String s){ fundType=checkSet(s); }
public String getValuable(){ return checkGet(valuable); }
public void setValuable(String s){ valuable=checkSet(s); }
public String getTaxCredit(){ return checkGet(taxCredit); }
public void setTaxCredit(String s){ taxCredit=checkSet(s); }
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
public String getQ_propertyKind(){ return checkGet(q_propertyKind); }
public void setQ_propertyKind(String s){ q_propertyKind=checkSet(s); }
public String getQ_fundType(){ return checkGet(q_fundType); }
public void setQ_fundType(String s){ q_fundType=checkSet(s); }
public String getQ_valuable(){ return checkGet(q_valuable); }
public void setQ_valuable(String s){ q_valuable=checkSet(s); }
public String getQ_taxCredit(){ return checkGet(q_taxCredit); }
public void setQ_taxCredit(String s){ q_taxCredit=checkSet(s); }
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
public String getQ_cause2(){ return checkGet(q_cause2); }
public void setQ_cause2(String s){ q_cause2=checkSet(s); }
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

String adjustType;
String adjustBookValue;

public String getAdjustType(){ return checkGet(adjustType); }
public void setAdjustType(String s){ adjustType=checkSet(s); }
public String getAdjustBookValue(){ return checkGet(adjustBookValue); }
public void setAdjustBookValue(String s){ adjustBookValue=checkSet(s); }

String verifyError;

public String getVerifyError(){ return checkGet(verifyError); }
public void setVerifyError(String s){ verifyError=checkSet(s); }
/**
 * <br>
 * <br>目的：傳回insert sql
 * <br>參數：無 
 * <br>傳回：無 ; 直接更改變數為新增成功與否
*/	
public void insert(){
	String[] execSQLArray;
	setEditDate(Datetime.getYYYMMDD());
	setEditTime(Datetime.getHHMMSS());			
	StringBuffer sbSQL = new StringBuffer("");
	Database db = new Database();  
	int getcut=0;
	if(getsKeySet()!=null)
		getcut = getsKeySet().length;	//有勾選的list中資料數
	//String sSQL = "";
	//String[] execSQLArray = new String[getcut];
	String[] strKeys = null;
	ResultSet rs = null;
	int i = 0;	
	//int counter=0;
	String adjust="";
	if(adjustType.equals("1")){
		adjust = "+";
	}else if(adjustType.equals("2")){
		adjust = "-";
	}	
	try {
		for (i=0; i<getcut; i++) {	
			strKeys = getsKeySet()[i].split(",");
			//先找出 Attachment 的值
			String Attachmentsql =   " select  " + 
									 " propertyNo, " +
									 " serialNo, " +
									 " propertyName1, " +
									 " propertyKind, " +
									 " fundType, " +
									 " valuable, " +
									 " taxCredit, " +
									 " sourceDate, "+
									 " oldPropertyNo, " +
									 " oldSerialNo, " +
									 " originalBV, " +
									 " measure,"+
									 " bookValue, "+
									 " (bookValue"+adjust+adjustBookValue+") as newBookValue "+
									 " from UNTRF_Attachment "+
									 " where enterOrg = " + Common.sqlChar(strKeys[0]) +
									 " and ownership = " + Common.sqlChar(strKeys[1]) +
									 " and propertyNo = " + Common.sqlChar(strKeys[2]) +
									 " and serialNo = " + Common.sqlChar(strKeys[3]) +
									 "";
			rs = db.querySQL(Attachmentsql);
			if (rs.next()){
			//insert 資料
				sbSQL.append(" insert into UNTRF_AdjustDetail(" ).append( 
				  " enterOrg,").append(
				  " ownership,").append(
				  " caseNo,").append(
				  " propertyNo,").append(
				  " serialNo,").append(
				  " propertyName1,").append(
				  " cause,").append(
				  " cause1,").append(
				  " cause2,").append(
				  " adjustDate,").append(
				  " verify,").append(
				  " propertyKind,").append(
				  " fundType,").append(
				  " valuable, ").append(
				  " taxCredit,").append(
				  " sourceDate,").append(
				  " originalBV,").append(
				  " oldMeasure,").append(
				  " oldBookValue,").append(
				  " newMeasure,").append(
				  " newBookValue,").append(
				  " adjustType,").append(
				  " adjustMeasure,").append(
				  " adjustBookValue,").append(
				  " oldPropertyNo,").append(
				  " oldSerialNo,").append(
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
					Common.sqlChar(q_cause)                       ).append( "," ).append(
					Common.sqlChar(q_cause1)                      ).append( "," ).append(
					Common.sqlChar(q_cause2)                      ).append( "," ).append(
					Common.sqlChar(q_adjustDate)                  ).append( "," ).append(
					Common.sqlChar("N")					  ).append( "," ).append(
					Common.sqlChar(rs.getString("propertyKind"))  ).append( "," ).append(
					Common.sqlChar(rs.getString("fundType"))      ).append( "," ).append(
					Common.sqlChar(rs.getString("valuable"))      ).append( "," ).append(
					Common.sqlChar(rs.getString("taxCredit"))     ).append( "," ).append(
					Common.sqlChar(rs.getString("sourceDate"))    ).append( "," ).append(
					Common.sqlChar(rs.getString("originalBV"))    ).append( "," ).append(
					Common.sqlChar(rs.getString("measure")) 	  ).append( "," ).append(
					Common.sqlChar(rs.getString("bookValue"))     ).append( "," ).append(
					Common.sqlChar(rs.getString("measure")) 	  ).append( "," ).append(
					Common.sqlChar(rs.getString("newBookValue"))  ).append( "," ).append(
					Common.sqlChar(adjustType)					  ).append( "," ).append(
					"'0'," ).append(
					Common.sqlChar(adjustBookValue) 			  ).append( "," ).append(
					Common.sqlChar(rs.getString("oldPropertyNo")) ).append( "," ).append(
					Common.sqlChar(rs.getString("oldSerialNo"))   ).append( "," ).append(
					Common.sqlChar("N")   					      ).append( "," ).append(
					Common.sqlChar(getUserID())                   ).append( "," ).append(
					Common.sqlChar(getEditDate())                 ).append( "," ).append(
					Common.sqlChar(getEditTime())                 ).append(
					" ):;:");
			}
			if(q_cause.equals("1") && (Integer.parseInt(rs.getString("newBookValue"))<Integer.parseInt(rs.getString("originalBV")))){
				 setVerifyError("Y");
				 setErrorMsg("新帳面金額─總價不可低於原始入帳─總價。");
				 break;
			}//else if(adjustType.equals("2") && (rs.getString("valuable").equals("N")) && (Integer.parseInt(adjustBookValue)>=Integer.parseInt(rs.getString("bookValue")))){
			//	 setVerifyError("Y");
			//	 setErrorMsg("增減別為減少時，增減帳面金額─總價必須小於原帳面金額─總價。");
			//	 break;
			//}else if(adjustType.equals("2") && (rs.getString("valuable").equals("Y")) && (Integer.parseInt(adjustBookValue)>Integer.parseInt(rs.getString("bookValue")))){
			//	 setVerifyError("Y");
			//	 setErrorMsg("增減別為減少時，增減帳面金額─總價必須小於等於原帳面金額─總價。");
			//	 break;
			//}
			
			int adjustBookValue = 0, bookValue = 0;
			if(!"".equals(Common.get(getAdjustBookValue()))){
				adjustBookValue = Integer.parseInt(getAdjustBookValue());
			}
			if(!"".equals(Common.get(rs.getString("bookValue")))){
				bookValue = Integer.parseInt(rs.getString("bookValue"));
			}
			
			if(adjustType.equals("2")){
			    if((!"".equals(Common.get(rs.getString("fundType")))) && (bookValue-adjustBookValue > bookValue) ){
			    	setVerifyError("Y");
			    	setErrorMsg("增減別為減少,又為基金類別時，增減帳面金額─總價必須小於等於原帳面金額─總價\n"); 
			    	break;
			    }else if(("Y".equals(Common.get(rs.getString("valuable")))) && (bookValue-adjustBookValue > bookValue) ){
			    	setVerifyError("Y");
			    	setErrorMsg("增減別為減少,又為珍貴財產時，增減帳面金額─總價必須小於等於原帳面金額─總價\n"); 
			    	break;
			    }else if(bookValue-adjustBookValue >= bookValue){
			    	setVerifyError("Y");
			    	setErrorMsg("增減別為減少時,增減帳面金額─總價必須小於原帳面金額─總價\n");
			    	break;
			    }
		        
			    if( ( ("".equals(Common.get(rs.getString("fundType")))) && ("N".equals(Common.get(rs.getString("valuable")))) ) && (bookValue-adjustBookValue) <= 0 ){
			    	setVerifyError("Y");
			    	setErrorMsg("增減別為減少,又不為珍貴財產或基金類別時,新價值必須大於0\n");
			    	break;
			    }else if( ( (!"".equals(Common.get(rs.getString("fundType")))) || ("Y".equals(Common.get(rs.getString("valuable")))) ) && (bookValue-adjustBookValue) < 0 ){
			    	setVerifyError("Y");
			    	setErrorMsg("增減別為減少又為珍貴財產或基金類別時,新價值必須大等於0\n");
			    	break;
			    }
			}
		}
		//setStateQueryOneSuccess();
	} catch (Exception e) {
		e.printStackTrace();
	}
	try{
		if (!"Y".equals(getVerifyError())) {
			execSQLArray = sbSQL.toString().split(":;:");
			db.excuteSQL(execSQLArray);
			setStateInsertSuccess();
			setErrorMsg("新增完成");				
		} else {			   
			setStateInsertSuccess();
	       queryOne();
		}
	} catch (Exception e) {
		e.printStackTrace();
	} finally {
		db.closeAll();
	}	
}

/**
 * <br>
 * <br>目的：依主鍵查詢單一資料
 * <br>參數：無
 * <br>傳回：傳回本物件
*/

public UNTRF016F  queryOne() throws Exception{
	Database db = new Database();
	UNTRF016F obj = this;
	try {
		String sql=" select a.enterOrg, a.ownership, a.caseNo, a.propertyNo, " +
			" a.serialNo, a.propertyName1, " +
			" a.propertyKind, a.fundType, " + 
			" a.valuable, a.taxCredit, "+
			" a.originalBV,a.sourceDate,  " +
			" a.measure,a.bookValue,  " +
			" a.oldPropertyNo, a.oldSerialNo, " +
			" c.organSName as enterOrgName, d.propertyName, e.propertyName as oldpropertyName, "+
			" a.editID,a.editDate,a.editTime, d.PROPERTYUNIT " +
			" from UNTRF_Attachment a, SYSCA_ORGAN c, "+
			" SYSPK_PropertyCode d, SYSPK_PropertyCode e where 1=1 " +
			" and a.enterOrg 	= " + Common.sqlChar(enterOrg) +
			" and a.ownership 	= " + Common.sqlChar(ownership) +
			" and a.propertyNo 	= " + Common.sqlChar(propertyNo) +
			" and a.serialNo 	= " + Common.sqlChar(serialNo) +
			" and a.enterOrg	= c.organID " +
			" and a.propertyNo 	= d.propertyNo "+
			" and a.oldpropertyNo = e.propertyNo(+) "+
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
			obj.setOriginalBV(rs.getString("originalBV"));
			obj.setSourceDate(rs.getString("sourceDate"));
			obj.setMeasure(rs.getString("measure")+rs.getString("PROPERTYUNIT"));
			obj.setBookValue(rs.getString("bookValue"));
			obj.setOldPropertyNo(rs.getString("oldPropertyNo"));
			obj.setOldPropertyName(rs.getString("oldpropertyName"));
			obj.setOldSerialNo(rs.getString("oldSerialNo"));
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
	int counter=0;
	try {
		String sql= " select a.enterOrg, d.propertyName,a.ownership, a.caseNo, "+
					" a.propertyNo, a.serialNo," +
					" decode(a.propertyKind,'01','公務用','02','公共用','03','事業用','04','非公用','') propertyKind,"+
					" a.measure,a.bookValue, a.sourceDate "+
					" from UNTRF_Attachment a ,SYSCA_Organ c ,SYSPK_PropertyCode d " +
					" where a.enterOrg = c.organID and a.propertyNo = d.propertyNo "+
					" and a.enterOrg 	= " + Common.sqlChar(q_enterOrg) +
					" and a.ownership 	= " + Common.sqlChar(q_ownership) +
					" and a.dataState = " + Common.sqlChar(q_dataState) +
					" and (a.enterOrg , a.ownership , a.propertyNo , a.serialNo)  not in ( " +
					" select enterOrg , ownership , propertyNo , serialNo from UNTRF_AdjustDetail where verify='N' " +
					" ) " +
					" and (a.enterOrg , a.ownership , a.propertyNo , a.serialNo)  not in ( " +
					"  select enterOrg , ownership , propertyNo , serialNo from UNTRF_ReduceDetail " +
					"  ) " ;

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
			if (!"".equals(getQ_verify()))
				sql+=" and a.verify = " + Common.sqlChar(getQ_verify()) ;    
			
			if (!"".equals(getQ_propertyKind()))
				sql+=" and a.propertyKind = " + Common.sqlChar(getQ_propertyKind()) ;
			if (!"".equals(getQ_fundType()))
				sql+=" and a.fundType = " + Common.sqlChar(getQ_fundType()) ;
			if (!"".equals(getQ_valuable()))
				sql+=" and a.valuable = " + Common.sqlChar(getQ_valuable()) ;
			if (!"".equals(getQ_taxCredit()))
				sql+=" and a.taxCredit = " + Common.sqlChar(getQ_taxCredit()) ;
		System.out.println(sql);
		ResultSet rs = db.querySQL(sql);
		//把查到的資料放入list裡..
		//和.jsp裡的 List區 內容要搭配起來
		while (rs.next()){
			counter++;
			String rowArray[]=new String[6];
			rowArray[0]=rs.getString("enterOrg");
			rowArray[1]=rs.getString("ownership");
			rowArray[2]=rs.getString("propertyNo"); 
			rowArray[3]=rs.getString("serialNo"); 
			rowArray[4]=rs.getString("propertyName");
			rowArray[5]=rs.getString("bookValue"); 
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

}
