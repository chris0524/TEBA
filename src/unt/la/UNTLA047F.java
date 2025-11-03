/*
*<br>程式目的：土地使用分區異動資料維護
*<br>程式代號：UNTLA047R
*<br>程式日期：0940827
*<br>程式作者：clive.chang
*<br>--------------------------------------------------------
*<br>修改作者　　修改日期　　　修改目的
*<br>--------------------------------------------------------
*/

package unt.la;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Map;

import util.*;

public class UNTLA047F extends SuperBean{
	
String enterOrg;
String ownership;
String propertyNo;
String differenceKind;
String serialNo;
String serialNo1;
String changeDate;
String changeCause;
String changeItem;
String oldUseSeparate;
String oldUseKind;
String newUseSeparate;
String newUseKind;
String notes1;
String notes;
String oldPropertyNo;
String oldSerialNo;

String enterOrgName;
String ownershipName;
String propertyName;
String propertyNoName;
String signNo;
String signNo1;
String signNo2;
String signNo3;
String signNo4;
String signNo5;
String oldUseSeparateName;
String oldUseKindName;

String q_enterOrgName;
String q_signNo1;
String q_signNo2;
String q_signNo3;
String q_signNo4;
String q_signNo5;
String q_signNo = "";

String q_propertyNo;
String q_propertyNoName;
String q_propertyNo1;
String q_propertyNo2;
String q_serialNoS;
String q_serialNoE;
String q_changeDateS;
String q_changeDateE;
String q_enterOrg;
String q_ownership;

String organID;
String isAdminManager;
String isOrganManager;	
String isManager;


public String getDifferenceKind(){ return checkGet(differenceKind); }
public void setDifferenceKind(String s){ differenceKind=checkSet(s); }
public String getOrganID(){ return checkGet(organID); }
public void setOrganID(String s){ organID=checkSet(s); }
public String getIsAdminManager(){ return checkGet(isAdminManager); }
public void setIsAdminManager(String s){ isAdminManager=checkSet(s); }
public String getIsOrganManager(){ return checkGet(isOrganManager); }
public void setIsOrganManager(String s){ isOrganManager=checkSet(s); }
public String getIsManager(){ return checkGet(isManager); }
public void setIsManager(String s){ isManager=checkSet(s); }	

public String getEnterOrg(){ return checkGet(enterOrg); }
public void setEnterOrg(String s){ enterOrg=checkSet(s); }
public String getOwnership(){ return checkGet(ownership); }
public void setOwnership(String s){ ownership=checkSet(s); }
public String getPropertyNo(){ return checkGet(propertyNo); }
public void setPropertyNo(String s){ propertyNo=checkSet(s); }
public String getSerialNo(){ return checkGet(serialNo); }
public void setSerialNo(String s){ serialNo=checkSet(s); }
public String getSerialNo1(){ return checkGet(serialNo1); }
public void setSerialNo1(String s){ serialNo1=checkSet(s); }
public String getChangeDate(){ return checkGet(changeDate); }
public void setChangeDate(String s){ changeDate=checkSet(s); }
public String getChangeCause(){ return checkGet(changeCause); }
public void setChangeCause(String s){ changeCause=checkSet(s); }
public String getChangeItem(){ return checkGet(changeItem); }
public void setChangeItem(String s){ changeItem=checkSet(s); }
public String getOldUseSeparate(){ return checkGet(oldUseSeparate); }
public void setOldUseSeparate(String s){ oldUseSeparate=checkSet(s); }
public String getOldUseKind(){ return checkGet(oldUseKind); }
public void setOldUseKind(String s){ oldUseKind=checkSet(s); }
public String getNewUseSeparate(){ return checkGet(newUseSeparate); }
public void setNewUseSeparate(String s){ newUseSeparate=checkSet(s); }
public String getNewUseKind(){ return checkGet(newUseKind); }
public void setNewUseKind(String s){ newUseKind=checkSet(s); }
public String getNotes1(){ return checkGet(notes1); }
public void setNotes1(String s){ notes1=checkSet(s); }
public String getNotes(){ return checkGet(notes); }
public void setNotes(String s){ notes=checkSet(s); }
public String getOldPropertyNo(){ return checkGet(oldPropertyNo); }
public void setOldPropertyNo(String s){ oldPropertyNo=checkSet(s); }
public String getOldSerialNo(){ return checkGet(oldSerialNo); }
public void setOldSerialNo(String s){ oldSerialNo=checkSet(s); }

public String getEnterOrgName(){ return checkGet(enterOrgName); }
public void setEnterOrgName(String s){ enterOrgName=checkSet(s); }
public String getOwnershipName(){ return checkGet(ownershipName); }
public void setOwnershipName(String s){ ownershipName=checkSet(s);} 
public String getPropertyName(){ return checkGet(propertyName); }
public void setPropertyName(String s){ propertyName=checkSet(s); }
public String getPropertyNoName(){ return checkGet(propertyNoName); }
public void setPropertyNoName(String s){ propertyNoName=checkSet(s); }
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
public String getSignNo(){ return checkGet(signNo); }
public void setSignNo(String s){ signNo=checkSet(s); }

public String getOldUseSeparateName(){ return checkGet(oldUseSeparateName); }
public void setOldUseSeparateName(String s){ oldUseSeparateName=checkSet(s); }
public String getOldUseKindName(){ return checkGet(oldUseKindName); }
public void setOldUseKindName(String s){ oldUseKindName=checkSet(s); }


public String getQ_enterOrgName(){ return checkGet(q_enterOrgName); }
public void setQ_enterOrgName(String s){ q_enterOrgName=checkSet(s); }
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


public String getQ_propertyNo(){ return checkGet(q_propertyNo); }
public void setQ_propertyNo(String s){ q_propertyNo=checkSet(s); }
public String getQ_propertyNoName(){ return checkGet(q_propertyNoName); }
public void setQ_propertyNoName(String s){ q_propertyNoName=checkSet(s); }
public String getQ_propertyNo1(){ return checkGet(q_propertyNo1); }
public void setQ_propertyNo1(String s){ q_propertyNo1=checkSet(s); }
public String getQ_propertyNo2(){ return checkGet(q_propertyNo2); }
public void setQ_propertyNo2(String s){ q_propertyNo2=checkSet(s); }
public String getQ_serialNoS(){ return checkGet(q_serialNoS); }
public void setQ_serialNoS(String s){ q_serialNoS=checkSet(s); }
public String getQ_serialNoE(){ return checkGet(q_serialNoE); }
public void setQ_serialNoE(String s){ q_serialNoE=checkSet(s); }
public String getQ_changeDateS(){ return checkGet(q_changeDateS); }
public void setQ_changeDateS(String s){ q_changeDateS=checkSet(s); }
public String getQ_changeDateE(){ return checkGet(q_changeDateE); }
public void setQ_changeDateE(String s){ q_changeDateE=checkSet(s); }
public String getQ_enterOrg(){ return checkGet(q_enterOrg); }
public void setQ_enterOrg(String s){ q_enterOrg=checkSet(s); }
public String getQ_ownership(){ return checkGet(q_ownership); }
public void setQ_ownership(String s){ q_ownership=checkSet(s); }


//傳回執行insert前之檢查sql
protected String[][] getInsertCheckSQL(){
	String s1, s2, s3, s4, s5;
	s1 = getSignNo1().substring(0,1);
	s2 = getSignNo2().substring(1,3);
	s3 = getSignNo3().substring(3,7);	
	s4 = Common.formatFrontZero(getSignNo4(),4);
	s5 = Common.formatFrontZero(getSignNo5(),4);
	this.setSignNo(s1+s2+s3+s4+s5);			
	String[][] checkSQLArray = new String[3][4];
 	checkSQLArray[0][0]=" select count(*) as checkResult from UNTLA_USESEPARATE where 1=1 " + 
		" and enterOrg = " + Common.sqlChar(enterOrg) + 
		" and ownership = " + Common.sqlChar(ownership) +
		" and differenceKind = " + Common.sqlChar(differenceKind) +
		" and propertyNo = " + Common.sqlChar(propertyNo) + 
		" and serialNo = " + Common.sqlChar(serialNo) + 
		" and serialNo1 = " + Common.sqlChar(serialNo1) + 
		"";
	checkSQLArray[0][1]=">";
	checkSQLArray[0][2]="0";
	checkSQLArray[0][3]="該筆資料己重複，請重新輸入！";
	
 	checkSQLArray[1][0]=" select count(*) as checkResult from UNTLA_LAND where 1=1 " + 
	" and enterOrg = " + Common.sqlChar(enterOrg) + 
	" and ownership = " + Common.sqlChar(ownership) + 
	" and differenceKind = " + Common.sqlChar(differenceKind) +
	" and propertyNo = " + Common.sqlChar(propertyNo) + 
	" and serialNo = " + Common.sqlChar(serialNo) + 
	"";
	checkSQLArray[1][1]="<=";
	checkSQLArray[1][2]="0";
	checkSQLArray[1][3]="土地主檔中找不到該財產編號和分號，請重新輸入！";
	
 	checkSQLArray[2][0]=" select count(*) as checkResult from UNTLA_LAND where 1=1 " + 
	" and enterOrg = " + Common.sqlChar(enterOrg) + 
	" and ownership = " + Common.sqlChar(ownership) + 
	" and differenceKind = " + Common.sqlChar(differenceKind) +
	" and signNo = " + Common.sqlChar(signNo) + 	
	""; 	
	checkSQLArray[2][1]="<=";
	checkSQLArray[2][2]="0";
	checkSQLArray[2][3]="土地主檔中找不到該筆土地標示資料，請重新輸入！";
	return checkSQLArray;
}


//傳回insert sql
protected String[] getInsertSQL(){
	//取得自動編號
	Database db = new Database();
	ResultSet rs;	
	String sql="select max(serialNo1)+1 as serialNo1 from UNTLA_USESEPARATE " +
		" where enterOrg = " + Common.sqlChar(enterOrg) +
		" and ownership = " + Common.sqlChar(ownership) +
		" and differenceKind = " + Common.sqlChar(differenceKind) +
		" and propertyNo = " + Common.sqlChar(propertyNo)+
		" and serialNo = " + Common.sqlChar(serialNo);	
	try {		
		
//System.out.println("異動次序="+sql);
		rs = db.querySQL(sql);
		if (rs.next()){
			if (rs.getString("serialNo1")==null)
				setSerialNo1("1");
			else
				setSerialNo1(rs.getString("serialNo1"));
		}
	} catch (Exception e) {
		e.printStackTrace();
	} finally {
		db.closeAll();
	}	

	String[] execSQLArray = new String[2];
	execSQLArray[0]=" insert into UNTLA_USESEPARATE(" +
			" enterOrg,"+
			" ownership,"+
			" differenceKind,"+
			" propertyNo,"+
			" serialNo,"+
			" serialNo1,"+
			" signNo,"+
			" changeDate,"+
			" changeCause,"+
			" changeItem,"+
			" oldUseSeparate,"+
			" oldUseKind,"+
			" newUseSeparate,"+
			" newUseKind,"+
			" notes1,"+
			" notes,"+
			" oldPropertyNo,"+
			" oldSerialNo,"+
			" editID,"+
			" editDate,"+
			" editTime"+
		" )Values(" +
			Common.sqlChar(enterOrg) + "," +
			Common.sqlChar(ownership) + "," +
			Common.sqlChar(differenceKind) + "," +
			Common.sqlChar(propertyNo) + "," +
			Common.sqlChar(serialNo) + "," +
			Common.sqlChar(serialNo1) + "," +
			Common.sqlChar(signNo) + "," +
			Common.sqlChar(Datetime.changeTaiwanYYMMDD(changeDate, "2")) + "," +
			Common.sqlChar(changeCause) + "," +
			Common.sqlChar(changeItem) + "," +
			Common.sqlChar(oldUseSeparate) + "," +
			Common.sqlChar(oldUseKind) + "," +
			Common.sqlChar(newUseSeparate) + "," +
			Common.sqlChar(newUseKind) + "," +
			Common.sqlChar(notes1) + "," +
			Common.sqlChar(notes) + "," +
			Common.sqlChar(oldPropertyNo) + "," +
			Common.sqlChar(oldSerialNo) + "," +
			Common.sqlChar(getEditID()) + "," +
			Common.sqlChar(getEditDate()) + "," +
			Common.sqlChar(getEditTime()) + ")" ;

	execSQLArray[1]=" update UNTLA_LAND set useSeparate="+Common.sqlChar(newUseSeparate) + "," +
			" useKind=" + Common.sqlChar(newUseKind) + " where enterOrg=" + Common.sqlChar(enterOrg) +
			" and ownership=" + Common.sqlChar(ownership) + " and differenceKind=" + Common.sqlChar(differenceKind) +
			" and propertyNo=" + Common.sqlChar(propertyNo) +" and serialNo=" + Common.sqlChar(serialNo);
			
	return execSQLArray;
}


//傳回update sql
protected String[] getUpdateSQL(){
	String[] execSQLArray = new String[2];
	execSQLArray[0]=" update UNTLA_USESEPARATE set " +
			" changeDate = " + Common.sqlChar(changeDate) + "," +
			" changeCause = " + Common.sqlChar(changeCause) + "," +
			" changeItem = " + Common.sqlChar(changeItem) + "," +
			" oldUseSeparate = " + Common.sqlChar(oldUseSeparate) + "," +
			" oldUseKind = " + Common.sqlChar(oldUseKind) + "," +
			" newUseSeparate = " + Common.sqlChar(newUseSeparate) + "," +
			" newUseKind = " + Common.sqlChar(newUseKind) + "," +
			" notes1 = " + Common.sqlChar(notes1) + "," +
			" notes = " + Common.sqlChar(notes) + "," +
			" oldPropertyNo = " + Common.sqlChar(oldPropertyNo) + "," +
			" oldSerialNo = " + Common.sqlChar(oldSerialNo) + "," +
			" editID = " + Common.sqlChar(getEditID()) + "," +
			" editDate = " + Common.sqlChar(getEditDate()) + "," +
			" editTime = " + Common.sqlChar(getEditTime()) + 
		" where 1=1 " + 
			" and enterOrg = " + Common.sqlChar(enterOrg) +
			" and ownership = " + Common.sqlChar(ownership) +
			" and differenceKind = " + Common.sqlChar(differenceKind) +
			" and propertyNo = " + Common.sqlChar(propertyNo) +
			" and serialNo = " + Common.sqlChar(serialNo) +
			" and serialNo1 = " + Common.sqlChar(serialNo1) +
			"";
	
	execSQLArray[1]=" update UNTLA_LAND set useSeparate="+Common.sqlChar(newUseSeparate) + "," +
		" useKind=" + Common.sqlChar(newUseKind) + " where enterOrg=" + Common.sqlChar(enterOrg) +
		" and ownership=" + Common.sqlChar(ownership) + " and differenceKind=" + Common.sqlChar(differenceKind) +
		" and propertyNo=" + Common.sqlChar(propertyNo) +" and serialNo=" + Common.sqlChar(serialNo);

	return execSQLArray;
}


//傳回delete sql
protected String[] getDeleteSQL(){
	String[] execSQLArray = new String[1];
	execSQLArray[0]=" delete UNTLA_USESEPARATE where 1=1 " +
			" and enterOrg = " + Common.sqlChar(enterOrg) +
			" and ownership = " + Common.sqlChar(ownership) +
			" and differenceKind = " + Common.sqlChar(differenceKind) +
			" and propertyNo = " + Common.sqlChar(propertyNo) +
			" and serialNo = " + Common.sqlChar(serialNo) +
			" and serialNo1 = " + Common.sqlChar(serialNo1) +
			"";
	return execSQLArray;
}


/**
 * <br>
 * <br>目的：依主鍵查詢單一資料
 * <br>參數：無
 * <br>傳回：傳回本物件
*/

public UNTLA047F  queryOne() throws Exception{
	Database db = new Database();
	UNTLA047F obj = this;
	try {
		String sql=" select a.enterOrg, a.ownership, a.propertyNo, a.serialNo, a.serialNo1, a.changeDate, a.changeCause, a.changeItem, a.oldUseSeparate, a.oldUseKind, a.newUseSeparate, a.newUseKind, a.notes1, a.notes, a.oldPropertyNo, a.oldSerialNo, a.editID, a.editDate, a.editTime,  "+
			" a.signNo, c.organSName as enterOrgName " +
			" ,a.differenceKind, d.propertyName " +
			" from UNTLA_USESEPARATE a, UNTLA_LAND b, SYSCA_ORGAN c, SYSPK_PropertyCode d where 1=1 " +
			" and a.enterOrg=b.enterOrg and a.ownership=b.ownership and a.differencekind=b.differencekind and a.propertyNo=b.propertyNo and a.serialNo=b.serialNo " +
			" and a.enterOrg=c.organID "+
			" and a.propertyNo=d.propertyNo "+
			" and a.enterOrg = " + Common.sqlChar(enterOrg) +
			" and a.ownership = " + Common.sqlChar(ownership) +
			" and a.differenceKind = " + Common.sqlChar(differenceKind) +
			" and a.propertyNo = " + Common.sqlChar(propertyNo) +
			" and a.serialNo = " + Common.sqlChar(serialNo) +
			" and a.serialNo1 = " + Common.sqlChar(serialNo1) +
			"";
//System.out.println("queryone="+sql);
		ResultSet rs = db.querySQL(sql);
		if (rs.next()){
			obj.setEnterOrg(rs.getString("enterOrg"));
			obj.setEnterOrgName(rs.getString("enterOrgName"));
			obj.setOwnership(rs.getString("ownership"));
			obj.setDifferenceKind(rs.getString("differenceKind"));
			obj.setPropertyNo(rs.getString("propertyNo"));
			obj.setPropertyName(rs.getString("propertyName"));
			obj.setPropertyNoName(rs.getString("propertyName"));
			obj.setSerialNo(rs.getString("serialNo"));
			obj.setSerialNo1(rs.getString("serialNo1"));
			if (rs.getString("changeDate") != null && rs.getString("changeDate").length() >= 8) {
				obj.setChangeDate(Datetime.changeTaiwanYYMMDD(rs.getString("changeDate"), "1"));
			} else {
				obj.setChangeDate("");
			}
			obj.setChangeCause(rs.getString("changeCause"));
			obj.setChangeItem(rs.getString("changeItem"));
			obj.setOldUseSeparate(rs.getString("oldUseSeparate"));
			obj.setOldUseKind(rs.getString("oldUseKind"));
			obj.setOldUseSeparateName(getLookupName("select codeName from SYSCA_CODE where codekindid='SEP' and codeid='" + rs.getString("oldUseSeparate") + "'")); 
			obj.setOldUseKindName(getLookupName("select codeName from SYSCA_CODE where codekindid='UKD' and codeid='" + rs.getString("oldUseKind") + "'")); 			
			obj.setNewUseSeparate(rs.getString("newUseSeparate"));
			obj.setNewUseKind(rs.getString("newUseKind"));
			obj.setNotes1(rs.getString("notes1"));
			obj.setNotes(rs.getString("notes"));
			obj.setOldPropertyNo(rs.getString("oldPropertyNo"));
			obj.setOldSerialNo(rs.getString("oldSerialNo"));
			obj.setEditID(rs.getString("editID"));
			obj.setEditDate(rs.getString("editDate"));
			obj.setEditTime(rs.getString("editTime"));
			if (rs.getString("signNo") == null || "".equals(rs.getString("signNo"))) {
				obj.setSignNo1("");
				obj.setSignNo2("");
				obj.setSignNo3("");
				obj.setSignNo4("");
				obj.setSignNo5("");
			} else {
				obj.setSignNo1(rs.getString("signNo").substring(0,1)+"000000");
				obj.setSignNo2(rs.getString("signNo").substring(0,3)+"0000");
				obj.setSignNo3(rs.getString("signNo").substring(0,7));
				obj.setSignNo4(rs.getString("signNo").substring(7,11));
				obj.setSignNo5(rs.getString("signNo").substring(11,15));
			}
			
			
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
		String sql=" select a.enterOrg, a.ownership, a.propertyNo, a.serialNo, a.serialNo1,a.differenceKind, "+
		    " (select codename from sysca_code x where x.codekindid = 'OWA' and x.codeid = a.ownership ) as ownershipName, "+
			" a.signNo, "+	
			" (select c.propertyName from SYSPK_PropertyCode c where a.propertyNo=c.propertyNo)propertyName, "+
			" a.changeDate, a.oldUseSeparate, a.oldUseKind, a.newUseSeparate, a.newUseKind "+
			" from UNTLA_USESEPARATE a, UNTLA_LAND d, SYSCA_Organ e " +
			" where 1=1 " +
			" and a.enterOrg=d.enterOrg " +
			" and a.ownership=d.ownership " +
			" and a.differenceKind = d.differenceKind  " + 
			" and a.propertyNo=d.propertyNo " +
			" and a.serialNo=d.serialNo " +
			" and a.enterorg = e.organid " +
			"" ; 
		
		if(getIsAdminManager().equals("Y") && getIsOrganManager().equals("Y")){
			sql+=" and e.IsManager='Y' and ( organID=decode('Y','Y',organID,'"+getOrganID()+"') or "+
			" ( 'Y'='Y' and organID in (select organID from SYSCA_Organ where organSuperior='"+getOrganID()+"') ) ) ";
		}else if(getIsAdminManager().equals("Y")&&getIsOrganManager().equals("N")){
			sql+=" and e.IsManager='Y' and ( organID=decode('Y','Y',organID,'"+getOrganID()+"') or "+
			" ( 'N'='Y' and organID in (select organID from SYSCA_Organ where organSuperior='"+getOrganID()+"') ) ) ";
		}else if(getIsAdminManager().equals("N")&&getIsOrganManager().equals("Y")){
			sql+=" and e.IsManager='Y' and ( organID=decode('N','Y',organID,'"+getOrganID()+"') or "+
			" ( 'Y'='Y' and organID in (select organID from SYSCA_Organ where organSuperior='"+getOrganID()+"') ) ) ";
		}else if(getIsAdminManager().equals("N")&&getIsOrganManager().equals("N")){
			sql+=" and e.IsManager='Y' and ( organID=decode('N','Y',organID,'"+getOrganID()+"') or "+
			" ( 'N'='Y' and organID in (select organID from SYSCA_Organ where organSuperior='"+getOrganID()+"') ) ) ";
		}
		
		if (!"".equals(getQ_enterOrg()))
			sql+=" and a.enterorg = " + Common.sqlChar(getQ_enterOrg()) ;		
		if (!"".equals(getQ_ownership()))
			sql+=" and a.ownership = " + Common.sqlChar(getQ_ownership()) ;		
		if (!"".equals(getQ_propertyNo()))
			sql+=" and a.propertyNo = " + Common.sqlChar(getQ_propertyNo()) ;		
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
			sql+=" and d.signNo like '" + q_signNo + "%'" ;

		if (!"".equals(getQ_serialNoS()) && !"".equals(getQ_serialNoE())) {
			sql+=" and a.serialNo between " + Common.sqlChar(Common.formatFrontZero(getQ_serialNoS(),7)) + " and " + Common.sqlChar(Common.formatFrontZero(getQ_serialNoE(),7));		
		} else if (!"".equals(getQ_serialNoS())) {
			sql+=" and a.serialNo=" + Common.sqlChar(Common.formatFrontZero(getQ_serialNoS(),7));
		}

		if (!"".equals(getQ_changeDateS()) && !"".equals(getQ_changeDateE())) {
			sql+=" and a.changeDate between " + Common.sqlChar(getQ_changeDateS()) + " and " + Common.sqlChar(getQ_changeDateE());		
		} else if (!"".equals(getQ_changeDateS())) {
			sql+=" and a.changeDate=" + Common.sqlChar(getQ_changeDateS());
		}		
		
		System.out.println(sql);
		Map<String,String> DifferenceKindMap = TCGHCommon.getSysca_Code("DFK");
		ResultSet rs = db.querySQL(sql);
		while (rs.next()){
			counter++;
			String rowArray[]=new String[14];
			rowArray[0]=rs.getString("ownershipName"); 
			rowArray[1]=rs.getString("enterOrg"); 
			rowArray[2]=rs.getString("ownership"); 
			rowArray[3]=rs.getString("differenceKind"); 
			rowArray[4]=DifferenceKindMap.get(rs.getString("differenceKind")); 
			rowArray[5]=rs.getString("propertyNo"); 
			rowArray[6]=rs.getString("serialNo"); 
			rowArray[7]=rs.getString("serialNo1");
			if (rs.getString("signNo") == null) {
				rowArray[8]="";
			} else {
				rowArray[8]=getSignDescName(rs.getString("signNo").substring(0,7)) + rs.getString("signNo").substring(7,11) + '-' + rs.getString("signNo").substring(11); 
			}
			if (rs.getString("changeDate") != null && rs.getString("changeDate").length() >= 8) {
				rowArray[9]= Datetime.changeTaiwanYYMMDD(rs.getString("changeDate"), "1"); 
			} else {
				rowArray[9]=""; 
			}
			
			rowArray[10]=getLookupName("select codeName from SYSCA_CODE where codekindid='SEP' and codeid='" + rs.getString("oldUseSeparate") + "'"); 
			rowArray[11]=getLookupName("select codeName from SYSCA_CODE where codekindid='UKD' and codeid='" + rs.getString("oldUseKind") + "'"); 
			rowArray[12]=getLookupName("select codeName from SYSCA_CODE where codekindid='SEP' and codeid='" + rs.getString("newUseSeparate") + "'"); 
			rowArray[13]=getLookupName("select codeName from SYSCA_CODE where codekindid='UKD' and codeid='" + rs.getString("newUseKind") + "'");
	
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

public String getLookupName(String sSQL) throws Exception {
	Database db = new Database();
	String sStr = "";
	try {
		ResultSet rs = db.querySQL(sSQL);
		while (rs.next()) {
			sStr = rs.getString(1);			
		}
	} catch (Exception e) {
		e.printStackTrace();
	} finally {
		db.closeAll();
	}			
	return sStr;
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
//		System.out.println("getSignDescName Exception => " + e.getMessage());
	}finally{
		db.closeAll();
	}
	return result;
}


}
