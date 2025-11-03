/*
*<br>程式目的：土地增減值作業－批次公告地價調整
*<br>程式代號：untla021f
*<br>程式日期：0950110
*<br>程式作者：Cherry
*<br>--------------------------------------------------------
*<br>修改作者　　修改日期　　　修改目的
*<br>--------------------------------------------------------
*/

package unt.la;

import java.sql.ResultSet;
import java.util.ArrayList;

import unt.ch.DataStructor;
import unt.ch.UNTCH_Tools;
import util.Common;
import util.Database;
import util.Datetime;
import util.SuperBean;

public class UNTLA021F extends SuperBean{
	
	private String q_differenceKind;
	private String q_propertyName1;
	private String q_userNote;
	private String q_place;
	private String q_placeName;
	private String q_placeNote;
	private String q_engineeringNo;
	private String q_engineeringNoName;
	
	public String getQ_differenceKind() {return checkGet(q_differenceKind);}
	public void setQ_differenceKind(String q_differenceKind) {this.q_differenceKind = checkSet(q_differenceKind);}
	public String getQ_propertyName1() {return checkGet(q_propertyName1);}
	public void setQ_propertyName1(String q_propertyName1) {this.q_propertyName1 = checkSet(q_propertyName1);}
	public String getQ_userNote() {return checkGet(q_userNote);}
	public void setQ_userNote(String q_userNote) {this.q_userNote = checkSet(q_userNote);}
	public String getQ_place() {return checkGet(q_place);}
	public void setQ_place(String q_place) {this.q_place = checkSet(q_place);}
	public String getQ_placeName() {return checkGet(q_placeName);}
	public void setQ_placeName(String q_placeName) {this.q_placeName = checkSet(q_placeName);}
	public String getQ_placeNote() {return checkGet(q_placeNote);}
	public void setQ_placeNote(String q_placeNote) {this.q_placeNote = checkSet(q_placeNote);}
	public String getQ_engineeringNo() {return checkGet(q_engineeringNo);}
	public void setQ_engineeringNo(String q_engineeringNo) {this.q_engineeringNo = checkSet(q_engineeringNo);}
	public String getQ_engineeringNoName() {return checkGet(q_engineeringNoName);}
	public void setQ_engineeringNoName(String q_engineeringNoName) {this.q_engineeringNoName = checkSet(q_engineeringNoName);}

String strKeySet[] = null;
String enterOrg;
String enterOrgName;
String ownership;
String propertyNo;
String propertyName;
String serialNo;
String caseNo;
String differenceKind;
String cause;
String q_bulletinDate;
String propertyName1;
String signNo;
String signName;
String propertyKind;
String fundType;
String valuable;
String taxCredit;
String grass;
String originalBV;
String sourceDate;
String area;
String holdNume;
String holdDeno;
String holdArea;
String bookUnit;
String bookValue;
String useSeparate;
String useKind;
String oldPropertyNo;
String oldSerialNo;

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
public String getCaseNo(){ return checkGet(caseNo); }
public void setCaseNo(String s){ caseNo=checkSet(s); }
public String getDifferenceKind() {return differenceKind;}
public void setDifferenceKind(String differenceKind) {this.differenceKind = differenceKind;}
public String getCause(){ return checkGet(cause); }
public void setCause(String s){ cause=checkSet(s); }
public String getQ_bulletinDate(){ return checkGet(q_bulletinDate); }
public void setQ_bulletinDate(String s){ q_bulletinDate=checkSet(s); }
public String getPropertyName1(){ return checkGet(propertyName1); }
public void setPropertyName1(String s){ propertyName1=checkSet(s); }
public String getSignNo(){ return checkGet(signNo); }
public void setSignNo(String s){ signNo=checkSet(s); }
public String getSignName(){ return checkGet(signName); }
public void setSignName(String s){ signName=checkSet(s); }
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
public String getOriginalBV(){ return checkGet(originalBV); }
public void setOriginalBV(String s){ originalBV=checkSet(s); }
public String getSourceDate(){ return checkGet(sourceDate); }
public void setSourceDate(String s){ sourceDate=checkSet(s); }
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
public String getUseSeparate(){ return checkGet(useSeparate); }
public void setUseSeparate(String s){ useSeparate=checkSet(s); }
public String getUseKind(){ return checkGet(useKind); }
public void setUseKind(String s){ useKind=checkSet(s); }
public String getOldPropertyNo(){ return checkGet(oldPropertyNo); }
public void setOldPropertyNo(String s){ oldPropertyNo=checkSet(s); }
public String getOldSerialNo(){ return checkGet(oldSerialNo); }
public void setOldSerialNo(String s){ oldSerialNo=checkSet(s); }

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

String q_keepUnit;
String q_keeper;
String q_useUnit;
String q_userNo;
public String getQ_keepUnit() {return checkGet(q_keepUnit);}
public void setQ_keepUnit(String qKeepUnit) {q_keepUnit = checkSet(qKeepUnit);}
public String getQ_keeper() {return checkGet(q_keeper);}
public void setQ_keeper(String qKeeper) {q_keeper = checkSet(qKeeper);}
public String getQ_useUnit() {return checkGet(q_useUnit);}
public void setQ_useUnit(String qUseUnit) {q_useUnit = checkSet(qUseUnit);}
public String getQ_userNo() {return checkGet(q_userNo);}
public void setQ_userNo(String qUserNo) {q_userNo = checkSet(qUserNo);}


String getEnterOrg;
String getOwnership;
String getPropertyNo;
String getSerialNo;
String getDifferenceKind;

/**
 * <br>
 * <br>目的：傳回insert sql
 * <br>參數：無 
 * <br>傳回：無 ; 直接更改變數為新增成功與否
*/	

//傳回執行insert前之檢查sql
protected String[][] getInsertCheckSQL(){

	String[][] checkSQLArray = new String[1][4];

	//公告年月檢查
 	checkSQLArray[0][0]=" select count(*) as checkResult from UNTLA_BulletinDate where 1=1 " + 
						" and bulletinKind ='1' " +
						" and bulletinDate = " + Common.sqlChar(Datetime.changeTaiwanYYMM(getQ_bulletinDate(), "2")) +  	
						""; 	
	checkSQLArray[0][1]="<=";
	checkSQLArray[0][2]="0";
	checkSQLArray[0][3]="公告年月錯誤，請重新輸入!!";	
	
	return checkSQLArray;
}

protected String[] getInsertSQL(){
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
	try {
		DataStructor ds = new DataStructor();
		UNTCH_Tools ut = new UNTCH_Tools();
		String[] tables = new String[]{
				"UNTLA_ADJUSTDETAIL",
				"UNTBU_ADJUSTDETAIL",
				"UNTRF_ADJUSTDETAIL",
				"UNTMP_ADJUSTDETAIL",
				"UNTVP_ADJUSTPROOF",
				"UNTRT_ADJUSTPROOF"
				};

		for (i=0; i<getcut; i++) {	
			strKeys = getsKeySet()[i].split(",");
			getEnterOrg = strKeys[0];
			getOwnership = strKeys[1];
			getDifferenceKind = strKeys[2];
			getPropertyNo = strKeys[3];
			getSerialNo = strKeys[4];
			//先找出 Land 的值
			String landsql = " select  " + 
							 " propertyNo, " +
							 " serialNo, " +
							 " differenceKind, " +
							 " propertyName1, " +
							 " signNo, " +
							 " propertyKind, " +
							 " fundType, " +
							 " valuable, " +
							 " taxCredit, " +
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
							 " bookValue, "+
							 " buydate" +
							 " from UNTLA_Land "+
							 " where enterOrg = " + Common.sqlChar(strKeys[0]) +
							 " and ownership = " + Common.sqlChar(strKeys[1]) +
							 " and differenceKind = " + Common.sqlChar(strKeys[2]) +
							 " and propertyNo = " + Common.sqlChar(strKeys[3]) +
							 " and serialNo = " + Common.sqlChar(strKeys[4]) +							 
							 "";
			rs = db.querySQL(landsql);
			if (rs.next()){
			//insert 資料
			int newBookUnit = getNewBookUnit();
			long newBookValue = Math.round(newBookUnit*rs.getDouble("holdArea"));
			int adjustBookUnit = newBookUnit-rs.getInt("bookUnit");
			long adjustBookValue = newBookValue-rs.getInt("bookValue");
			int addBookUnit = 0;
			long addBookValue = 0;
			int reduceBookUnit = 0;
			long reduceBookValue = 0;
			if(adjustBookUnit > 0){		addBookUnit = adjustBookUnit;
										addBookValue = adjustBookValue;
			}else{						reduceBookUnit = adjustBookUnit;
										reduceBookValue = Math.abs(adjustBookValue);
			}
			String caseSerialNo = "";
			ds.enterOrg = this.getEnterOrg();
			ds.ownership = this.getOwnership();
			ds.caseNo = this.getCaseNo();
			ds.differenceKind = this.getDifferenceKind();
			
			caseSerialNo = ut._getNewCaseSerialNo(ds,tables);			
			
			sbSQL.append(" insert into UNTLA_ADJUSTDETAIL(" ).append( 
				  " enterOrg,").append(
				  " ownership,").append(
				  " caseNo,").append(
				  " differenceKind,").append(
				  " propertyNo,").append(
				  " serialNo,").append(
				  " caseSerialNo,").append(
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
				  " newArea,").append(
				  " newHoldNume,").append(
				  " newHoldDeno,").append(
				  " newHoldArea,").append(
				  " newBookUnit,").append(
				  " newBookValue,").append(
				  " newNetUnit,").append(
				  " newNetValue,").append(
				  " oldArea,").append(
				  " oldHoldNume,").append(
				  " oldHoldDeno,").append(
				  " oldHoldArea,").append(
				  " oldBookUnit,").append(
				  " oldBookValue,").append(
				  " oldNetUnit,").append(
				  " oldNetValue,").append(
				  " useSeparate,").append(
				  " useKind,").append(
				  " sourceDate,").append(
				  " oldPropertyNo,").append(
				  " oldSerialNo,").append(
				  " adjustArea,").append(
				  " adjustHoldArea,").append(
				  " addBookUnit,").append(
				  " addBookValue,").append(
				  " addNetUnit,").append(
				  " addNetValue,").append(
				  " reduceBookUnit,").append(
				  " reduceBookValue,").append(
				  " reduceNetUnit,").append(
				  " reduceNetValue,").append(
				  " originalBV," ).append(
				  " bulletinDate,").append(
				  " editID,").append(
				  " editDate,").append(
				  " editTime, ").append(
				  " buyDate ").append(
				  ") values ( ").append( 
					Common.sqlChar(enterOrg)                    ).append( "," ).append(
					Common.sqlChar(ownership)                   ).append( "," ).append(
					Common.sqlChar(caseNo)                      ).append( "," ).append(
					Common.sqlChar(differenceKind)              ).append( "," ).append(
					Common.sqlChar(rs.getString("propertyNo"))    ).append( "," ).append(
					Common.sqlChar(rs.getString("serialNo"))      ).append( "," ).append(
					Common.sqlChar(caseSerialNo)                  ).append( "," ).append(							
					Common.sqlChar(rs.getString("propertyName1")) ).append( "," ).append(
					Common.sqlChar(rs.getString("signNo"))        ).append( "," ).append(
					"'10', " ).append( //增減值原因:10為公告地價調整
					"'', " ).append(
					Common.sqlChar(q_adjustDate)                  ).append( "," ).append(
					Common.sqlChar("N")					  ).append( "," ).append(
					Common.sqlChar(rs.getString("propertyKind"))  ).append( "," ).append(
					Common.sqlChar(rs.getString("fundType"))      ).append( "," ).append(
					Common.sqlChar(rs.getString("valuable"))      ).append( "," ).append(
					Common.sqlChar(rs.getString("taxCredit"))     ).append( "," ).append(
					Common.sqlChar(rs.getString("area"))          ).append( "," ).append(
					Common.sqlChar(rs.getString("holdNume"))      ).append( "," ).append(
					Common.sqlChar(rs.getString("holdDeno"))      ).append( "," ).append(
					Common.sqlChar(rs.getString("holdArea"))      ).append( "," ).append(
					Common.sqlChar(newBookUnit+"")      ).append( "," ).append(
					Common.sqlChar(newBookValue+"")     ).append( "," ).append(
					Common.sqlChar(newBookUnit+"")      ).append( "," ).append(
					Common.sqlChar(newBookValue+"")     ).append( "," ).append(
					Common.sqlChar(rs.getString("area"))          ).append( "," ).append(
					Common.sqlChar(rs.getString("holdNume"))      ).append( "," ).append(
					Common.sqlChar(rs.getString("holdDeno"))      ).append( "," ).append(
					Common.sqlChar(rs.getString("holdArea"))      ).append( "," ).append(
					Common.sqlChar(rs.getString("bookUnit"))      ).append( "," ).append(
					Common.sqlChar(rs.getString("bookValue"))     ).append( "," ).append(
					Common.sqlChar(rs.getString("bookUnit"))      ).append( "," ).append(
					Common.sqlChar(rs.getString("bookValue"))     ).append( "," ).append(
					Common.sqlChar(rs.getString("useSeparate"))   ).append( "," ).append(
					Common.sqlChar(rs.getString("useKind"))       ).append( "," ).append(
					Common.sqlChar(rs.getString("sourceDate"))    ).append( "," ).append(
					Common.sqlChar(rs.getString("oldPropertyNo")) ).append( "," ).append(
					Common.sqlChar(rs.getString("oldSerialNo"))   ).append( "," ).append(
					"'0', " ).append(
					"'0', " ).append(
					Common.sqlChar(addBookUnit+"")   ).append( "," ).append(
					Common.sqlChar(addBookValue+"")   ).append( "," ).append(
					Common.sqlChar(addBookUnit+"")   ).append( "," ).append(
					Common.sqlChar(addBookValue+"")   ).append( "," ).append(
					Common.sqlChar(reduceBookUnit+"")   ).append( "," ).append(
					Common.sqlChar(reduceBookValue+"")   ).append( "," ).append(
					Common.sqlChar(reduceBookUnit+"")   ).append( "," ).append(
					Common.sqlChar(reduceBookValue+"")   ).append( "," ).append(
					Common.sqlChar(rs.getString("originalBV"))   ).append( "," ).append(
					Common.sqlChar(Datetime.changeTaiwanYYMM(getQ_bulletinDate(), "2"))).append( "," ).append(
					Common.sqlChar(getEditID())                   ).append( "," ).append(
					Common.sqlChar(getEditDate())                 ).append( "," ).append(
					Common.sqlChar(getEditTime())                 ).append( "," ).append(
					Common.sqlChar(rs.getString("buyDate"))		  ).append(
					" ):;:");	
			}
		}
		//setStateQueryOneSuccess();
	} catch (Exception e) {
		e.printStackTrace();
	} finally {
		db.closeAll();
	}
	//execSQLArray[i] = sSQL;
	setStateInsertSuccess();
	return sbSQL.toString().split(":;:");
}


public ArrayList queryAll() throws Exception{
	Database db = new Database();
	ArrayList objList=new ArrayList();
	int counter=0;
	try {
		String sql= " select a.enterorg, d.propertyname,a.ownership, a.caseno, a.differencekind, " + "\n" +
				" a.propertyno, a.serialno, a.propertyname1," + "\n" +
				" (select codename from SYSCA_CODE z where z.codekindid = 'SEP' and z.codeid = a.useseparate) as useSeparate," + "\n" +
				" (select codename from SYSCA_CODE z where z.codekindid = 'UKD' and z.codeid = a.usekind) as useKind," + "\n" +
				" a.signno," + "\n" +
				" (case a.propertykind when '01' then '公務用' when '02' then '公共用' when '03' then '事業用' when '04' then '非公用' else '' end) propertykind,"+ "\n" +
				" a.holdarea, a.bookvalue, a.bookunit, a.sourcedate "+ "\n" +
				" from UNTLA_LAND a ,UNTLA_VALUE z,UNTLA_BULLETINDATE b, SYSCA_ORGAN c ,SYSPK_PROPERTYCODE d " + "\n" +
				" where a.verify='Y' and a.datastate ='1' and a.enterorg = c.organid and a.propertyno = d.propertyno "+ "\n" +
				" and b.bulletinkind='1' "+ "\n" +
				" and z.enterorg = a.enterorg and z.ownership = a.ownership and z.differencekind = a.differencekind and z.propertyno = a.propertyno and z.serialno = a.serialno" +
				" and z.valueunit <> a.bookunit" +
				" and a.enterorg 	= " + Common.sqlChar(getQ_enterOrg()) + "\n" +
				" and a.ownership 	= " + Common.sqlChar(getQ_ownership()) + "\n" +
				" and a.differencekind 	= " + Common.sqlChar(getQ_differenceKind()) + "\n" +
				" and b.bulletindate = " + Common.sqlChar(Datetime.changeTaiwanYYMM(getQ_bulletinDate(), "2")) + "\n" +
				" and b.bulletindate = z.bulletindate "  +
				" and a.bookvalue != case when (select e.valueunit from UNTLA_VALUE e where a.enterorg=e.enterorg and a.ownership=e.ownership and a.differencekind = e.differencekind and a.propertyno=e.propertyno and a.serialno=e.serialno and b.bulletindate=e.bulletindate) is null then 0 else (select e.valueunit from UNTLA_VALUE e where a.enterorg=e.enterorg and a.ownership=e.ownership and a.differencekind = e.differencekind and a.propertyno=e.propertyno and a.serialno=e.serialno and b.bulletindate=e.bulletindate) end * a.holdarea"+ "\n" +
				
				" and (a.enterorg + a.ownership + a.differencekind + a.propertyno + a.serialno)  not in ( " + "\n" +
				" select enterorg + ownership + differencekind + propertyno + serialno from UNTLA_ADJUSTDETAIL where verify = 'N' " + "\n" +
				" ) " + "\n" +
				" and (a.enterorg + a.ownership + a.differencekind + a.propertyno + a.serialno)  not in ( " + "\n" +
				" select enterorg + ownership + differencekind + propertyno + serialno from UNTLA_REDUCEDETAIL where 1=1 " + "\n" +
				" ) ";
//				" and (a.enterorg)  not in ( " + "\n" +
//				" select enterorg from UNTLA_ADJUSTDETAIL where verify='N' " + "\n" +
//				" ) " + "\n" +
//				" and (a.ownership)  not in ( " + "\n" +
//				" select ownership from UNTLA_ADJUSTDETAIL where verify='N' " + "\n" +
//				" ) " + "\n" +
//				" and (a.propertyno)  not in ( " + "\n" +
//				" select propertyno from UNTLA_ADJUSTDETAIL where verify='N' " + "\n" +
//				" ) " + "\n" +
//				" and (a.serialno)  not in ( " + "\n" +
//				" select serialno from UNTLA_ADJUSTDETAIL where verify='N' " + "\n" +
//				" ) " + "\n" +
//				
//				" and (a.enterorg)  not in ( " + "\n" +
//				"  select enterorg from UNTLA_REDUCEDETAIL " + "\n" +
//				" ) " + "\n" +
//				" and (a.ownership)  not in ( " + "\n" +
//				"  select ownership from UNTLA_REDUCEDETAIL " + "\n" +
//				" ) " + "\n" +
//				" and (a.propertyno)  not in ( " + "\n" +
//				"  select propertyno from UNTLA_REDUCEDETAIL " + "\n" +
//				" ) " + "\n" +
//				" and (a.serialno)  not in ( " + "\n" +
//				"  select serialno from UNTLA_REDUCEDETAIL " + "\n" +
//				" ) ";

		String q_signLaNo = "";
		if (!"".equals(Common.get(getQ_signNo1()))){
			q_signLaNo = getQ_signNo1().substring(0,1)+"______";
		}
		if (!"".equals(getQ_signNo2())){
			q_signLaNo = getQ_signNo2().substring(0,3)+"____";			
		}
		if (!"".equals(getQ_signNo3())){
			q_signLaNo = getQ_signNo3();
		}


		if (!"".equals(q_signLaNo)){
			if (!"".equals(q_signLaNo)){
				sql += " and signno like '" + q_signLaNo + "%'";
				
				if (!"".equals(getQ_signNo4())){
					sql += " and substring(signno,8,4) = '" + getQ_signNo4() + "'";				
				}	
				if (!"".equals(getQ_signNo5())){
					sql += " and substring(signno,12,4) = '" + getQ_signNo5() + "'";				
				}
			}
				
		}

		if (!"".equals(getQ_differenceKind())){ 
			sql += " and a.differencekind = " + Common.sqlChar(getQ_differenceKind());
		}
		if (!"".equals(getQ_keepUnit())){ 
			sql += " and a.keepUnit = " + Common.sqlChar(getQ_keepUnit());
		}		
		if (!"".equals(getQ_keeper())){ 
			sql += " and a.keeper = " + Common.sqlChar(getQ_keeper());
		}
		if (!"".equals(getQ_useUnit())){ 
			sql += " and a.useUnit = " + Common.sqlChar(getQ_useUnit());
		}
		if (!"".equals(getQ_userNo())){ 
			sql += " and a.userno = " + Common.sqlChar(getQ_userNo());
		}
		if (!"".equals(getQ_userNote())){ 
			sql += " and a.userNote like " + Common.sqlChar("%" + getQ_userNote() + "%");
		}
		
		if (!"".equals(getQ_place())){ 
			sql += " and a.place = " + Common.sqlChar(getQ_place());
		}
		if (!"".equals(getQ_placeNote())){ 
			sql += " and a.placeNote like " + Common.sqlChar("%" + getQ_placeNote() + "%");
		}
		if (!"".equals(getQ_engineeringNo())){ 
			sql += " and a.engineeringNo = " + Common.sqlChar(getQ_engineeringNo());
		}
		
		if (!"".equals(getQ_propertyKind()))
			sql+=" and a.propertykind = " + Common.sqlChar(getQ_propertyKind()) ;
		if (!"".equals(getQ_fundType()))
			sql+=" and a.fundtype = " + Common.sqlChar(getQ_fundType()) ;
		if (!"".equals(getQ_valuable()))
			sql+=" and a.valuable = " + Common.sqlChar(getQ_valuable()) ;
		if (!"".equals(getQ_taxCredit()))
			sql+=" and a.taxcredit = " + Common.sqlChar(getQ_taxCredit()) ;
		if (!"".equals(getQ_grass()))
			sql+=" and a.grass = " + Common.sqlChar(getQ_grass()) ;
		sql+=" order by a.signno ";
		
		//System.out.println("SQL : " + sql);
		ResultSet rs = db.querySQL_NoChange(sql);
		//把查到的資料放入list裡..
		//和.jsp裡的 List區 內容要搭配起來
		while (rs.next()){
			counter++;
			String rowArray[]=new String[13];
			rowArray[0]=checkGet(rs.getString("enterOrg"));
			rowArray[1]=checkGet(rs.getString("ownership"));
			rowArray[2]=checkGet(rs.getString("differenceKind"));
			rowArray[3]=checkGet(rs.getString("propertyNo")); 
			rowArray[4]=checkGet(rs.getString("serialNo")); 		
			rowArray[5]=checkGet(rs.getString("propertyName"));
			rowArray[6]=checkGet(rs.getString("propertyname1"));
			rowArray[7]=getSignDescName(rs.getString("signNo").substring(0,7))+rs.getString("signNo").substring(7,11)+"-"+rs.getString("signNo").substring(11); 
			rowArray[8]=Common.areaFormat(rs.getString("holdArea"));
			rowArray[9]=checkGet(rs.getString("bookUnit"));
			rowArray[10]=checkGet(rs.getString("bookValue"));
			rowArray[11]=checkGet(rs.getString("useSeparate"));		
			rowArray[12]=checkGet(rs.getString("useKind"));		
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

protected int getNewBookUnit(){
	Database db = new Database();
	ResultSet rs;	
	int newBookUnit=0;
	String sql="select valueUnit from UNTLA_Value " +
		" where 1=1 " +
		" and enterOrg = " + Common.sqlChar(getEnterOrg) +
		" and ownership = " + Common.sqlChar(getOwnership) +
		" and differenceKind = " + Common.sqlChar(getDifferenceKind) +		
		" and propertyNo = " + Common.sqlChar(getPropertyNo) +
		" and serialNo = " + Common.sqlChar(getSerialNo) +
		" and bulletinDate = " + Common.sqlChar(Datetime.changeTaiwanYYMM(getQ_bulletinDate(), "2")) +
		"";	
	try {		
		rs = db.querySQL(sql);
		if (rs.next()){
			newBookUnit = rs.getInt("valueUnit");
		}
	} catch (Exception e) {
		e.printStackTrace();
	} finally {
		db.closeAll();
	}
	return newBookUnit;
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
