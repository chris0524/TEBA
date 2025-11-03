/*
*<br>程式目的：地政機關土地資料查詢
*<br>程式代號：syslc002f
*<br>程式日期：2014/09/29
*<br>程式作者：Mike Kao
*<br>--------------------------------------------------------
*<br>修改作者　　修改日期　　　修改目的
*<br>--------------------------------------------------------
*/

package sys.lc;

import java.sql.ResultSet;
import java.util.ArrayList;

import util.*;
import util.Common;

public class SYSLC002F extends QueryBean{
String enterOrg;
String SerialNo;
String Name;
String tranYYYMM;
String lsignNo1;
String lsignNo2;
String lsignNo3;
String lsignNo4;
String lsignNo1Name;
String lsignNo2Name;
String lsignNo3Name;
String lsignNo4Name;
String area;
String ownershipName;
String holdDeno;
String holdNume;
String useSeparate;
String useKind;
String priceDate;
String priceUnit;
String valueDate;
String valueUnit;
String registerserialNo;
String signNo1;
String signNo2;
String signNo3;

String q_enterOrg;
String q_tranYYYMM;
String q_lsignNo1;
String q_lsignNo2;
String q_lsignNo3;
String q_lsignNo4;
String q_lsignNo = "";
String q_valueDate;
String q_areaS;
String q_areaE;
String q_useSeparate;
String q_useKind;



public String getEnterOrg() {return checkGet(enterOrg);}
public void setEnterOrg(String enterOrg) {this.enterOrg = checkSet(enterOrg);}
public String getSerialNo() {return checkGet(SerialNo);}
public void setSerialNo(String serialNo) {SerialNo = checkSet(serialNo);}
public String getName() {return checkGet(Name);}
public void setName(String name) {Name = checkSet(name);}
public String getTranYYYMM(){ return checkGet(tranYYYMM); }
public void setTranYYYMM(String s){ tranYYYMM=checkSet(s); }
public String getLsignNo1(){ return checkGet(lsignNo1); }
public void setLsignNo1(String s){ lsignNo1=checkSet(s); }
public String getLsignNo2(){ return checkGet(lsignNo2); }
public void setLsignNo2(String s){ lsignNo2=checkSet(s); }
public String getLsignNo3(){ return checkGet(lsignNo3); }
public void setLsignNo3(String s){ lsignNo3=checkSet(s); }
public String getLsignNo4(){ return checkGet(lsignNo4); }
public void setLsignNo4(String s){ lsignNo4=checkSet(s); }
public String getLsignNo1Name() {return checkGet(lsignNo1Name);}
public void setLsignNo1Name(String lsignNo1Name) {this.lsignNo1Name = checkSet(lsignNo1Name);}
public String getLsignNo2Name() {return checkGet(lsignNo2Name);}
public void setLsignNo2Name(String lsignNo2Name) {this.lsignNo2Name = checkSet(lsignNo2Name);}
public String getLsignNo3Name() {return checkGet(lsignNo3Name);}
public void setLsignNo3Name(String lsignNo3Name) {this.lsignNo3Name = checkSet(lsignNo3Name);}
public String getLsignNo4Name() {return checkGet(lsignNo4Name);}
public void setLsignNo4Name(String lsignNo4Name) {this.lsignNo4Name = checkSet(lsignNo4Name);}
public String getArea(){ return checkGet(area); }
public void setArea(String s){ area=checkSet(s); }
public String getUseSeparate() {return checkGet(useSeparate);}
public void setUseSeparate(String useSeparate) {this.useSeparate = checkSet(useSeparate);}
public String getUseKind() {return checkGet(useKind);}
public void setUseKind(String useKind) {this.useKind = checkSet(useKind);}
public String getPriceDate() {return checkGet(priceDate);}
public void setPriceDate(String priceDate) {this.priceDate = checkSet(priceDate);}
public String getPriceUnit(){ return checkGet(priceUnit); }
public void setPriceUnit(String s){ priceUnit=checkSet(s); }
public String getValueDate() {return checkGet(valueDate);}
public void setValueDate(String valueDate) {this.valueDate = checkSet(valueDate);}
public String getValueUnit(){ return checkGet(valueUnit); }
public void setValueUnit(String s){ valueUnit=checkSet(s); }
public String getOwnershipName(){ return checkGet(ownershipName); }
public void setOwnershipName(String s){ ownershipName=checkSet(s); }
public String getHoldDeno(){ return checkGet(holdDeno); }
public void setHoldDeno(String s){ holdDeno=checkSet(s); }
public String getHoldNume(){ return checkGet(holdNume); }
public void setHoldNume(String s){ holdNume=checkSet(s); }
public String getRegisterserialNo() {return checkGet(registerserialNo);}
public void setRegisterserialNo(String registerserialNo) {this.registerserialNo = checkSet(registerserialNo);}


public String getQ_enterOrg() {return checkGet(q_enterOrg);}
public void setQ_enterOrg(String qEnterOrg) {q_enterOrg = checkSet(qEnterOrg);}
public String getQ_tranYYYMM(){ return checkGet(q_tranYYYMM); }
public void setQ_tranYYYMM(String s){ q_tranYYYMM=checkSet(s); }
public String getQ_lsignNo1(){ return checkGet(q_lsignNo1); }
public void setQ_lsignNo1(String s){ q_lsignNo1=checkSet(s); }
public String getQ_lsignNo2(){ return checkGet(q_lsignNo2); }
public void setQ_lsignNo2(String s){ q_lsignNo2=checkSet(s); }
public String getQ_lsignNo3(){ return checkGet(q_lsignNo3); }
public void setQ_lsignNo3(String s){ q_lsignNo3=checkSet(s); }
public String getQ_lsignNo4(){ return checkGet(q_lsignNo4); }
public void setQ_lsignNo4(String s){ q_lsignNo4=checkSet(s); }
public String getQ_valueDate(){ return checkGet(q_valueDate); }
public void setQ_valueDate(String s){ q_valueDate=checkSet(s); }
public String getQ_areaS() {return checkGet(q_areaS);}
public void setQ_areaS(String qAreaS) {q_areaS = checkSet(qAreaS);}
public String getQ_areaE() {return checkGet(q_areaE);}
public void setQ_areaE(String qAreaE) {q_areaE = checkSet(qAreaE);}
public String getQ_useSeparate() {return checkGet(q_useSeparate);}
public void setQ_useSeparate(String qUseSeparate) {q_useSeparate = checkSet(qUseSeparate);}
public String getQ_useKind() {return checkGet(q_useKind);}
public void setQ_useKind(String qUseKind) {q_useKind = checkSet(qUseKind);}

String d_tranYYYMM;
public String getD_tranYYYMM() {return checkGet(d_tranYYYMM);}
public void setD_tranYYYMM(String dTranYYYMM) {d_tranYYYMM = checkSet(dTranYYYMM);}

public String getSignNo1() {return checkGet(signNo1);}
public void setSignNo1(String signNo1) {this.signNo1 = checkSet(signNo1);}
public String getSignNo2() {return checkGet(signNo2);}
public void setSignNo2(String signNo2) {this.signNo2 = checkSet(signNo2);}
public String getSignNo3() {return checkGet(signNo3);}
public void setSignNo3(String signNo3) {this.signNo3 = checkSet(signNo3);}


//傳回delete sql
protected String[] getDeleteSQL(){
	String[] execSQLArray = new String[1];
	
	execSQLArray[0]=" delete SYSLC_Land where 1=1 and tranYYYMM = " +Common.sqlChar(new Datetime().changeTaiwanYYMM(getD_tranYYYMM(), "2"))+
			"";
	return execSQLArray;
}


/**
 * <br>
 * <br>目的：依主鍵查詢單一資料
 * <br>參數：無
 * <br>傳回：傳回本物件
*/

public SYSLC002F  queryOne() throws Exception{
	Database db = new Database();
	SYSLC002F obj = this;
	try {
		String sql=" select * "+
			" from SYSLC_Land a where 1=1 " +
			" and a.enterorg =  " +Common.sqlChar(getEnterOrg()) +
			" and a.tranYYYMM= " +Common.sqlChar(new Datetime().changeTaiwanYYMM(tranYYYMM, "2"))+
			" and a.lsignNo1= " +Common.sqlChar(signNo1.substring(0,1))+
			" and a.lsignNo2= " +Common.sqlChar(signNo2.substring(1,3))+
			" and a.lsignNo3= " +Common.sqlChar(signNo3.substring(3))+
			" and a.lsignNo4= " +Common.sqlChar(lsignNo4)+
			" and a.registerserialNo= " +Common.sqlChar(registerserialNo)+
			"";		
		
		ResultSet rs = db.querySQL(sql);
		if (rs.next()){
			obj.setSerialNo(rs.getString("serialNo"));
			obj.setName(rs.getString("name"));			
			obj.setTranYYYMM(new Datetime().changeTaiwanYYMM(rs.getString("tranYYYMM"), "1"));
			obj.setLsignNo1(Common.formatRearZero(rs.getString("lsignNo1"),7));
			obj.setLsignNo2(Common.formatRearZero(rs.getString("lsignNo1")+rs.getString("lsignNo2"),7));
			obj.setLsignNo3(rs.getString("lsignNo1")+rs.getString("lsignNo2")+rs.getString("lsignNo3"));
			obj.setLsignNo4(rs.getString("lsignNo4"));
			obj.setArea(Common.areaFormat(rs.getString("area")));			
			obj.setOwnershipName(rs.getString("ownershipName"));
			obj.setHoldDeno(rs.getString("holdDeno"));
			obj.setHoldNume(rs.getString("holdNume"));
			obj.setUseSeparate(rs.getString("useSeparate"));
			obj.setUseKind(rs.getString("useKind"));
			obj.setPriceDate(new Datetime().changeTaiwanYYMM(rs.getString("priceDate"), "1"));
			obj.setPriceUnit(Common.valueFormat(rs.getString("priceUnit")));			
			obj.setValueDate(new Datetime().changeTaiwanYYMM(rs.getString("valueDate"), "1"));
			obj.setValueUnit(Common.valueFormat(rs.getString("valueUnit")));
			obj.setRegisterserialNo(rs.getString("registerserialNo"));
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
		String sql=" select a.tranYYYMM,  "+
			" (select b.signname from sysca_sign b where a.lsignNo1+'000000'=b.signno) as lsignNoName1, "+
			" (select b.signname from sysca_sign b where a.lsignNo1+a.lsignNo2+'0000'=b.signno) as lsignNoName2, "+
			" (select b.signname from sysca_sign b where a.lsignNo1+a.lsignNo2+a.lsignNo3=b.signno) as lsignNoName3, "+
			" a.lsignNo1, a.lsignNo2, a.lsignNo3, a.lsignNo4, a.area, a.ownershipName, a.registerserialNo "+
			" from SYSLC_Land a where 1=1 ";
			   
			if (!"".equals(getQ_enterOrg()))
				sql+=" and a.enterorg = " + Common.sqlChar(getQ_enterOrg()) ;
			if (!"".equals(getQ_tranYYYMM()))
				sql+=" and a.tranYYYMM = " + Common.sqlChar(new Datetime().changeTaiwanYYMM(getQ_tranYYYMM(), "2")) ;
			if (!"".equals(getQ_lsignNo1()))
				q_lsignNo=getQ_lsignNo1().substring(0,1)+"______";
			if (!"".equals(getQ_lsignNo2()))
				q_lsignNo=getQ_lsignNo2().substring(0,3)+"____";
			if (!"".equals(getQ_lsignNo3())){
//				if (getQ_lsignNo3().length()==4){
					q_lsignNo=getQ_lsignNo3();
//				}
			}
			if (!"".equals(getQ_lsignNo4())){
				setQ_lsignNo4(Common.formatFrontZero(getQ_lsignNo4(),8));
				if ("".equals(q_lsignNo4)){
					q_lsignNo+=getQ_lsignNo4();
				}else{
					q_lsignNo=q_lsignNo+getQ_lsignNo4();				
				}
			}
			if (!"".equals(q_lsignNo))
				sql+=" and a.lsignNo1+a.lsignno2+a.lsignno3+a.lsignno4 like '%" + q_lsignNo + "%'" ;

			if (!"".equals(getQ_valueDate()))
				sql+=" and a.valueDate = " + Common.sqlChar(new Datetime().changeTaiwanYYMM(getQ_valueDate(), "2")) ;
			if (!"".equals(getQ_areaS()))
				sql+=" and a.area >= " + Common.sqlChar(getQ_areaS()) ;
			if (!"".equals(getQ_areaE()))
				sql+=" and a.area <= " + Common.sqlChar(getQ_areaE()) ;
			if (!"".equals(getQ_useSeparate()))
				sql+=" and a.useSeparate = " + Common.sqlChar(getQ_useSeparate()) ;
			if (!"".equals(getQ_useKind()))
				sql+=" and a.useKind = " + Common.sqlChar(getQ_useKind()) ;
			
			sql+=" order by a.lsignNo1, a.lsignNo2, a.lsignNo3, a.lsignNo4, a.registerserialNo";

		ResultSet rs = db.querySQL(sql,true);
		processCurrentPageAttribute(rs);
		if (getTotalRecord() > 0) {
			int count = getRecordStart();
			int end = getRecordEnd();
			do {
				if (count > end)
					break;
 
				String rowArray[]=new String[11];
				rowArray[0]=Common.get(new Datetime().changeTaiwanYYMM(rs.getString("tranYYYMM"), "1"));
				rowArray[1]=Common.get(Common.formatRearZero(rs.getString("lsignNo1"),7)); 
				rowArray[2]=Common.get(Common.formatRearZero(rs.getString("lsignNo1")+rs.getString("lsignNo2"),7)); 
				rowArray[3]=Common.get(rs.getString("lsignNo1")+rs.getString("lsignNo2")+rs.getString("lsignNo3"));
				rowArray[4]=Common.get(rs.getString("lsignNoName1")); 
				rowArray[5]=Common.get(rs.getString("lsignNoName2")); 
				rowArray[6]=Common.get(rs.getString("lsignNoName3"));
				rowArray[7]=Common.get(rs.getString("lsignNo4"));
				rowArray[8]=Common.areaFormat(Common.get(rs.getString("area")));
				rowArray[9]=Common.get(rs.getString("ownershipName"));
				rowArray[10]=Common.get(rs.getString("registerserialNo"));
				
				objList.add(rowArray);	
				count++;
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

}


