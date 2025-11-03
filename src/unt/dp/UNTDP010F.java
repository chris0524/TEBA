/*
*<br>程式目的：折舊作業_不折舊設定刪除
*<br>程式代號：untdp010f
*<br>程式日期：1030905
*<br>程式作者：Mike Kao
*<br>--------------------------------------------------------
*<br>修改作者　　修改日期　　　修改目的
*<br>--------------------------------------------------------
*/

package unt.dp;

import java.sql.ResultSet;
import java.util.ArrayList;

import util.Common;
import unt.ch.UNTCH_Tools;
import util.*;

public class UNTDP010F extends SuperBean{

String enterOrg;
String enterOrgName;
String ownership;
String propertyNo;
String propertyName;
String serialNo;
String propertyName1;
String signNo;
String noDeprSet;

String q_enterOrg; 
String q_ownership; 
String q_caseNo;
String q_reduceDate;
String q_verify;
String q_keepUnit;
String q_keeper;
String q_useUnit;
String q_userNo;
String q_differenceKind;
String q_propertyName;
String q_propertyName1;
String q_nameplate;
String q_specification;
String q_propertyNoS;
String q_propertyNoE;
String q_propertyNoSName;
String q_propertyNoEName;
String q_serialNoS;
String q_serialNoE;
String q_userNote;
String q_datastate;



String sSQL = "";
String strKeySet[] = null;

String toggleAll;
public String getToggleAll(){ if (!"".equals(checkGet(toggleAll))) return "checked"; else return "unchecked"; }    
public void setToggleAll(String s){ toggleAll=checkSet(s); }    

String q_jsp;
public String getQ_jsp() {return checkGet(q_jsp);}
public void setQ_jsp(String qJsp) {q_jsp = checkSet(qJsp);}


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
public String getQ_propertyName() {return checkGet(q_propertyName);}
public void setQ_propertyName(String qPropertyName) {q_propertyName = checkSet(qPropertyName);}
public String getPropertyName1(){ return checkGet(propertyName1); }
public void setPropertyName1(String s){ propertyName1=checkSet(s); }

public String getSignNo(){ return checkGet(signNo); }
public void setSignNo(String s){ signNo=checkSet(s); }
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
public String getQ_caseNo(){ return checkGet(q_caseNo); }
public void setQ_caseNo(String s){ q_caseNo=checkSet(s); }
public String getQ_reduceDate(){ return checkGet(q_reduceDate); }
public void setQ_reduceDate(String s){ q_reduceDate=checkSet(s); }
public String getQ_verify(){ return checkGet(q_verify); }
public void setQ_verify(String s){ q_verify=checkSet(s); }
public String getQ_enterOrg(){ return checkGet(q_enterOrg); }
public void setQ_enterOrg(String s){ q_enterOrg=checkSet(s); }
public String getQ_ownership(){ return checkGet(q_ownership); }
public void setQ_ownership(String s){ q_ownership=checkSet(s); }
public String getQ_keepUnit() {return checkGet(q_keepUnit);}
public void setQ_keepUnit(String qKeepUnit) {q_keepUnit = checkSet(qKeepUnit);}
public String getQ_keeper() {return checkGet(q_keeper);}
public void setQ_keeper(String qKeeper) {q_keeper = checkSet(qKeeper);}
public String getQ_useUnit() {return checkGet(q_useUnit);}
public void setQ_useUnit(String qUseUnit) {q_useUnit = checkSet(qUseUnit);}
public String getQ_userNo() {return checkGet(q_userNo);}
public void setQ_userNo(String qUserNo) {q_userNo = checkSet(qUserNo);}
public String getQ_differenceKind() {return checkGet(q_differenceKind);}
public void setQ_differenceKind(String qDifferenceKind) {q_differenceKind = checkSet(qDifferenceKind);}
public String getQ_propertyName1() {return checkGet(q_propertyName1);}
public void setQ_propertyName1(String qPropertyName1) {q_propertyName1 = checkSet(qPropertyName1);}
public String getQ_nameplate() {return checkGet(q_nameplate);}
public void setQ_nameplate(String qNameplate) {q_nameplate = checkSet(qNameplate);}
public String getQ_specification() {return checkGet(q_specification);}
public void setQ_specification(String qSpecification) {q_specification = checkSet(qSpecification);}
public String[] getStrKeySet() {return strKeySet;}
public void setStrKeySet(String[] strKeySet) {this.strKeySet = strKeySet;}
public String getNoDeprSet() {return checkGet(noDeprSet);}
public void setNoDeprSet(String noDeprSet) {this.noDeprSet = checkSet(noDeprSet);}
public String getQ_userNote() {return checkGet(q_userNote);}
public void setQ_userNote(String qUserNote) {q_userNote = checkSet(qUserNote);}
public String getQ_datastate() { return checkGet(q_datastate); }
public void setQ_datastate(String q_datastate) { this.q_datastate = checkSet(q_datastate); }

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
		String sql = "select a.enterorg, a.ownership, (select codename from SYSCA_CODE z where z.codekindid='OWA' and z.codeid=a.ownership) as ownershipName, " +
		"a.differencekind,(select codename from SYSCA_CODE z where z.codekindid = 'DFK' and z.codeid = a.differencekind) as differenceKindName, a.buydate, a.propertyno, a.serialno, "+
		"a.propertyname1, '' as nameplate, '' as specification, '1' as bookamount, a.bookvalue, a.keepunit, "+
		"(select unitname from UNTMP_KEEPUNIT d where d.enterorg=a.enterorg and d.unitno=a.keepunit )  as unitname, "+
		"a.place1, "+
		"(select placename from SYSCA_PLACE e where e.enterorg=a.enterorg and e.placeno=a.place1 )  as placename, "+
		"c.propertyname, c.propertyunit, ISNULL(c.limityear, a.otherlimityear) as limityear, "+
		" a.datastate " +
		"from UNTRF_ATTACHMENT a left join SYSPK_PROPERTYCODE c on "+
		" a.propertyno = c.propertyno and c.propertyno in ( a.propertyno, '000000000A')"+
		"where (a.nodeprset = 'Y')  ";
		
		if (!"".equals(getQ_enterOrg()) && getQ_enterOrg() != null)
			sql+=" and a.enterorg= " + Common.sqlChar(getQ_enterOrg());		
		if (!"".equals(getQ_keepUnit()) && getQ_keepUnit() != null)
			sql+=" and a.keepunit=" + Common.sqlChar(getQ_keepUnit());						
		if (!"".equals(getQ_keeper()) && getQ_keeper() != null)
			sql+=" and a.keeper= " + Common.sqlChar(getQ_keeper());			
		if (!"".equals(getQ_useUnit()) && getQ_useUnit() != null)
			sql+=" and a.useunit=" + Common.sqlChar(getQ_useUnit());						
		if (!"".equals(getQ_userNo()) && getQ_userNo() != null)
			sql+=" and a.userno= " + Common.sqlChar(getQ_userNo());		
		if (!"".equals(getQ_userNote()) && getQ_userNote() != null)
			sql+=" and a.usernote like " + Common.sqlChar("%"+getQ_userNote()+"%");	
		if ((!"".equals(getQ_propertyNoS()) && !"".equals(getQ_propertyNoE())) && (getQ_propertyNoS() != null && getQ_propertyNoE() != null))
			sql+=" and a.propertyno between " + Common.sqlChar(getQ_propertyNoS()) +" and " + Common.sqlChar(getQ_propertyNoE());	
		if ((!"".equals(getQ_serialNoS()) && !"".equals(getQ_serialNoE())) && (getQ_serialNoS() != null && getQ_serialNoE() != null))
			sql+=" and a.serialno between " + Common.sqlChar(getQ_serialNoS()) +" and " + Common.sqlChar(getQ_serialNoE());	
		if (!"".equals(getQ_differenceKind()) && getQ_differenceKind() != null)
			sql+=" and a.differencekind= " + Common.sqlChar(getQ_differenceKind());
		if (!"".equals(getQ_propertyName()) && getQ_propertyName() != null)
			sql+=" and c.propertyname like " + Common.sqlChar("%"+getQ_propertyName()+"%");
		if (!"".equals(getQ_propertyName1()) && getQ_propertyName1() != null)
			sql+=" and a.propertyname1 like " + Common.sqlChar("%"+getQ_propertyName1()+"%");
		if (this.getQ_datastate() != null && !"".equals(this.getQ_datastate())) 
			sql += " and a.datastate = " + Common.sqlChar(this.getQ_datastate());
		

		sql += " union "+

		"select a.enterorg, a.ownership, (select codename from SYSCA_CODE z where z.codekindid='OWA' and z.codeid=a.ownership) as ownershipName, " +
		"a.differencekind, (select codename from SYSCA_CODE z where z.codekindid = 'DFK' and z.codeid = a.differencekind) as differenceKindName, a.buydate, a.propertyno, a.serialno,  "+
		"a.propertyname1, '' as nameplate, '' as specification, '1' as bookamount, a.bookvalue, a.keepunit, "+
		"(select unitname from UNTMP_KEEPUNIT d where d.enterorg=a.enterorg and d.unitno=a.keepunit )  as unitname, "+
		"a.place1, "+
		"(select placename from SYSCA_PLACE e where e.enterorg=a.enterorg and e.placeno=a.place1 )  as placename, "+
		"c.propertyname, c.propertyunit, ISNULL(c.limityear, a.otherlimityear) as limityear, "+
		" a.datastate " +
		"from UNTBU_BUILDING a left join  SYSPK_PROPERTYCODE c on "+
		" a.propertyno = c.propertyno and c.propertyno in ( a.propertyno, '000000000A')"+
		"where (a.nodeprset = 'Y')  ";
		
		if (!"".equals(getQ_enterOrg()) && getQ_enterOrg() != null)
			sql+=" and a.enterorg= " + Common.sqlChar(getQ_enterOrg());		
		if (!"".equals(getQ_keepUnit()) && getQ_keepUnit() != null)
			sql+=" and a.keepunit=" + Common.sqlChar(getQ_keepUnit());						
		if (!"".equals(getQ_keeper()) && getQ_keeper() != null)
			sql+=" and a.keeper= " + Common.sqlChar(getQ_keeper());			
		if (!"".equals(getQ_useUnit()) && getQ_useUnit() != null)
			sql+=" and a.useunit=" + Common.sqlChar(getQ_useUnit());						
		if (!"".equals(getQ_userNo()) && getQ_userNo() != null)
			sql+=" and a.userno= " + Common.sqlChar(getQ_userNo());		
		if (!"".equals(getQ_userNote()) && getQ_userNote() != null)
			sql+=" and a.usernote like " + Common.sqlChar("%"+getQ_userNote()+"%");	
		if ((!"".equals(getQ_propertyNoS()) && !"".equals(getQ_propertyNoE())) && (getQ_propertyNoS() != null && getQ_propertyNoE() != null))
			sql+=" and a.propertyno between " + Common.sqlChar(getQ_propertyNoS()) +" and " + Common.sqlChar(getQ_propertyNoE());	
		if ((!"".equals(getQ_serialNoS()) && !"".equals(getQ_serialNoE())) && (getQ_serialNoS() != null && getQ_serialNoE() != null))
			sql+=" and a.serialno between " + Common.sqlChar(getQ_serialNoS()) +" and " + Common.sqlChar(getQ_serialNoE());	
		if (!"".equals(getQ_differenceKind()) && getQ_differenceKind() != null)
			sql+=" and a.differencekind= " + Common.sqlChar(getQ_differenceKind());
		if (!"".equals(getQ_propertyName()) && getQ_propertyName() != null)
			sql+=" and c.propertyname like " + Common.sqlChar("%"+getQ_propertyName()+"%");
		if (!"".equals(getQ_propertyName1()) && getQ_propertyName1() != null)
			sql+=" and a.propertyname1 like " + Common.sqlChar("%"+getQ_propertyName1()+"%");
		if (this.getQ_datastate() != null && !"".equals(this.getQ_datastate())) 
			sql += " and a.datastate = " + Common.sqlChar(this.getQ_datastate());
		
		sql += " union "+

		"select a.enterorg, b.ownership, (select codename from SYSCA_CODE z where z.codekindid='OWA' and z.codeid=a.ownership) as ownershipName, " +
		"b.differencekind, (select codename from SYSCA_CODE z where z.codekindid = 'DFK' and z.codeid = a.differencekind) as differenceKindName, a.buydate, b.propertyno, b.serialno,  "+
		"a.propertyname1, a.nameplate, a.specification, b.bookamount, b.bookvalue, b.keepunit, "+
		"(select unitname from UNTMP_KEEPUNIT d where d.enterorg=a.enterorg and d.unitno=b.keepunit )  as unitname, "+
		"b.place1, "+
		"(select placename from SYSCA_PLACE e where e.enterorg=a.enterorg and e.placeno=b.place1 )  as placename, "+
		"c.propertyname, c.propertyunit, ISNULL(c.limityear, a.otherlimityear) as limityear, "+
		" b.datastate " +
		"from UNTMP_MOVABLE a, UNTMP_MOVABLEDETAIL  b, SYSPK_PROPERTYCODE c "+
		"where a.enterorg = b.enterorg "+
		"and a.ownership = b.ownership "+
		"and a.propertyno = b.propertyno "+
		"and a.serialnos <= b.serialno "+
		"and a.serialnoe >= b.serialno "+
		"and a.differencekind = b.differencekind "+
		"and a.lotno = b.lotno "+
		"and c.propertyno in ( a.propertyno, '000000000A') "+
		"and a.propertyno = c.propertyno "+
		"and (b.nodeprset = 'Y')  ";

		if (!"".equals(getQ_enterOrg()) && getQ_enterOrg() != null)
			sql+=" and a.enterorg = " + Common.sqlChar(getQ_enterOrg());		
		if (!"".equals(getQ_keepUnit()) && getQ_keepUnit() != null)
			sql+=" and b.keepunit=" + Common.sqlChar(getQ_keepUnit());						
		if (!"".equals(getQ_keeper()) && getQ_keeper() != null)
			sql+=" and b.keeper= " + Common.sqlChar(getQ_keeper());			
		if (!"".equals(getQ_useUnit()) && getQ_useUnit() != null)
			sql+=" and b.useunit=" + Common.sqlChar(getQ_useUnit());						
		if (!"".equals(getQ_userNo()) && getQ_userNo() != null)
			sql+=" and b.userno= " + Common.sqlChar(getQ_userNo());		
		if (!"".equals(getQ_userNote()) && getQ_userNote() != null)
			sql+=" and b.usernote like " + Common.sqlChar("%"+getQ_userNote()+"%");	
		if ((!"".equals(getQ_propertyNoS()) && !"".equals(getQ_propertyNoE())) && (getQ_propertyNoS() != null && getQ_propertyNoE() != null))
			sql+=" and a.propertyno between " + Common.sqlChar(getQ_propertyNoS()) +" and " + Common.sqlChar(getQ_propertyNoE());	
		if ((!"".equals(getQ_serialNoS()) && !"".equals(getQ_serialNoE())) && (getQ_serialNoS() != null && getQ_serialNoE() != null))
			sql+=" and b.serialno between " + Common.sqlChar(getQ_serialNoS()) +" and " + Common.sqlChar(getQ_serialNoE());	
		if (!"".equals(getQ_differenceKind()) && getQ_differenceKind() != null)
			sql+=" and a.differencekind= " + Common.sqlChar(getQ_differenceKind());
		if (!"".equals(getQ_propertyName()) && getQ_propertyName() != null)
			sql+=" and c.propertyname like " + Common.sqlChar("%"+getQ_propertyName()+"%");
		if (!"".equals(getQ_propertyName1()) && getQ_propertyName1() != null)
			sql+=" and a.propertyname1 like " + Common.sqlChar("%"+getQ_propertyName1()+"%");
		
		if (!"".equals(getQ_nameplate()) && getQ_nameplate() != null )
			sql+=" and a.nameplate like " + Common.sqlChar("%"+getQ_nameplate()+"%");
		if (!"".equals(getQ_specification()) && getQ_specification() != null)
			sql+=" and a.specification like " + Common.sqlChar("%"+getQ_specification()+"%");
		if (this.getQ_datastate() != null && !"".equals(this.getQ_datastate())) 
			sql += " and a.datastate = " + Common.sqlChar(this.getQ_datastate());
		
		sql += " union "+

		"select a.enterorg, a.ownership, (select codename from SYSCA_CODE z where z.codekindid='OWA' and z.codeid=a.ownership) as ownershipName, " +
		"a.differencekind, (select codename from SYSCA_CODE z where z.codekindid = 'DFK' and z.codeid = a.differencekind) as differenceKindName, a.buydate, a.propertyno, a.serialno,  "+
		"a.propertyname1, '' as nameplate, '' as specification, '1' as bookamount, a.bookvalue, a.keepunit, "+
		"(select unitname from UNTMP_KEEPUNIT d where d.enterorg=a.enterorg and d.unitno=a.keepunit )  as unitname, "+
		"a.place1, "+
		"(select placename from SYSCA_PLACE e where e.enterorg=a.enterorg and e.placeno=a.place1 )  as placename, "+
		"c.propertyname, ISNULL(a.otherpropertyunit, c.propertyunit) as propertyunit , ISNULL(a.otherlimityear, c.limityear) as limityear, "+
		" a.datastate " +
		"from UNTRT_ADDPROOF a left join  SYSPK_PROPERTYCODE c on "+
		" a.propertyno = c.propertyno and c.propertyno in ( a.propertyno, '000000000A')"+
		"where (a.nodeprset = 'Y')  ";
		
		if (!"".equals(getQ_enterOrg()) && getQ_enterOrg() != null)
			sql+=" and a.enterorg= " + Common.sqlChar(getQ_enterOrg());		
		if (!"".equals(getQ_keepUnit()) && getQ_keepUnit() != null)
			sql+=" and a.keepunit=" + Common.sqlChar(getQ_keepUnit());						
		if (!"".equals(getQ_keeper()) && getQ_keeper() != null)
			sql+=" and a.keeper= " + Common.sqlChar(getQ_keeper());			
		if (!"".equals(getQ_useUnit()) && getQ_useUnit() != null)
			sql+=" and a.useunit=" + Common.sqlChar(getQ_useUnit());						
		if (!"".equals(getQ_userNo()) && getQ_userNo() != null)
			sql+=" and a.userno= " + Common.sqlChar(getQ_userNo());		
		if (!"".equals(getQ_userNote()) && getQ_userNote() != null)
			sql+=" and a.usernote like " + Common.sqlChar("%"+getQ_userNote()+"%");	
		if ((!"".equals(getQ_propertyNoS()) && !"".equals(getQ_propertyNoE())) && (getQ_propertyNoS() != null && getQ_propertyNoE() != null))
			sql+=" and a.propertyno between " + Common.sqlChar(getQ_propertyNoS()) +" and " + Common.sqlChar(getQ_propertyNoE());	
		if ((!"".equals(getQ_serialNoS()) && !"".equals(getQ_serialNoE())) && (getQ_serialNoS() != null && getQ_serialNoE() != null))
			sql+=" and a.serialno between " + Common.sqlChar(getQ_serialNoS()) +" and " + Common.sqlChar(getQ_serialNoE());	
		if (!"".equals(getQ_differenceKind()) && getQ_differenceKind() != null)
			sql+=" and a.differencekind= " + Common.sqlChar(getQ_differenceKind());
		if (!"".equals(getQ_propertyName()) && getQ_propertyName() != null)
			sql+=" and c.propertyname like " + Common.sqlChar("%"+getQ_propertyName()+"%");
		if (!"".equals(getQ_propertyName1()) && getQ_propertyName1() != null)
			sql+=" and a.propertyname1 like " + Common.sqlChar("%"+getQ_propertyName1()+"%");
		if (this.getQ_datastate() != null && !"".equals(this.getQ_datastate())) 
			sql += " and a.datastate = " + Common.sqlChar(this.getQ_datastate());
		
		sql+= " order by enterorg, ownership, differencekind, buydate, propertyno, serialno";

		ResultSet rs = db.querySQL_NoChange(sql);
		//把查到的資料放入list裡..
		//和.jsp裡的 List區 內容要搭配起來
		while (rs.next()){

			counter++;
			String rowArray[]=new String[22];
			rowArray[0]=Common.get(rs.getString("enterorg"));
			rowArray[1]=Common.getORGANANAME(getQ_enterOrg());
			rowArray[2]=Common.get(rs.getString("ownership"));
			rowArray[3]=Common.get(rs.getString("ownershipName"));
			rowArray[4]=Common.get(rs.getString("differencekind")); 
			rowArray[5]=Common.get(rs.getString("differenceKindName")); 
			rowArray[6]=new Datetime().changeTaiwanYYMMDD(rs.getString("buydate"),"1"); 
			rowArray[7]=Common.get(rs.getString("propertyno"));
			rowArray[8]=Common.get(rs.getString("serialno")); 
			rowArray[9]=Common.get(rs.getString("propertyname")); 
			rowArray[10]=Common.get(rs.getString("propertyname1")); 
			rowArray[11]=Common.get(rs.getString("nameplate"));		
			rowArray[12]=Common.get(rs.getString("specification"));
			rowArray[13]=rs.getString("bookamount") == null ? "0":rs.getString("bookamount");
			rowArray[14]=Common.get(rs.getString("propertyunit"));
			rowArray[15]=rs.getString("bookvalue") == null ? "0":Common.valueFormat(Common.get(rs.getString("bookvalue")));
			rowArray[16]=Common.get(rs.getString("keepunit"));
			rowArray[17]=Common.get(rs.getString("unitname"));
			rowArray[18]=Common.get(rs.getString("place1"));
			rowArray[19]=Common.get(rs.getString("placename"));
			rowArray[20]=Common.get(rs.getString("limityear"));
			rowArray[21] = "1".equals(Common.get(rs.getString("datastate"))) ? "是" : "否";

			
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

//傳回update sql
public void update(){
	int getcut=0;
	String[] strKeys = null;
	if(getsKeySet()!=null){
		getcut = getsKeySet().length;	//有勾選的list中資料數
		String[] sqlcode=new String[getcut];
		for (int i=0; i<getcut; i++) {	//將挑選的資料組成一字串
			strKeys = getsKeySet()[i].split(",");
			String tempPropertyNo = strKeys[3];
			
			if(tempPropertyNo.substring(0,3).equals("111")){
				sqlcode[i] = " update UNTRF_ATTACHMENT set nodeprset='N' where enterorg= "+ Common.sqlChar(strKeys[0])+" and ownership= "+ Common.sqlChar(strKeys[1])+
				 " and differencekind= "+ Common.sqlChar(strKeys[2])+ " and propertyno= "+ Common.sqlChar(strKeys[3])+ " and serialno= "+ Common.sqlChar(strKeys[4]);
			}
			
			if(tempPropertyNo.substring(0,1).equals("2")){
				sqlcode[i] = " update UNTBU_BUILDING set nodeprset='N' where enterorg= "+ Common.sqlChar(strKeys[0])+" and ownership= "+ Common.sqlChar(strKeys[1])+
				 " and differencekind= "+ Common.sqlChar(strKeys[2])+ " and propertyno= "+ Common.sqlChar(strKeys[3])+ " and serialno= "+ Common.sqlChar(strKeys[4]);
			}
			
			if(tempPropertyNo.substring(0,1).equals("3") || tempPropertyNo.substring(0,1).equals("4") || tempPropertyNo.substring(0,1).equals("5")  ){
				sqlcode[i] = " update UNTMP_MOVABLEDETAIL set nodeprset='N' where enterorg= "+ Common.sqlChar(strKeys[0])+" and ownership= "+ Common.sqlChar(strKeys[1])+
				 " and differencekind= "+ Common.sqlChar(strKeys[2])+ " and propertyno= "+ Common.sqlChar(strKeys[3])+ " and serialno= "+ Common.sqlChar(strKeys[4]);
			}
			
			if (tempPropertyNo.substring(0,1).equals("8")) {
				sqlcode[i] = " update UNTRT_ADDPROOF set nodeprset='N' where enterorg= "+ Common.sqlChar(strKeys[0])+" and ownership= "+ Common.sqlChar(strKeys[1])+
						     " and differencekind= "+ Common.sqlChar(strKeys[2])+ " and propertyno= "+ Common.sqlChar(strKeys[3])+ " and serialno= "+ Common.sqlChar(strKeys[4]);
			}
		}
		
		//System.out.println(str_barcode);
	
		Database db = new Database();
		try{
			db.excuteSQL(sqlcode);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.closeAll();
		}
		
		setStateUpdateSuccess();
		setErrorMsg("修改完成");	
		
		
	}//if(getsKeySet()!=null)
}

}
