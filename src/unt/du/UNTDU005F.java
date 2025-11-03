/*
*<br>程式目的：動產條碼資料批次新增
*<br>程式代號：sysmt001f
*<br>程式日期：0961023
*<br>程式作者：shan
*<br>--------------------------------------------------------
*<br>修改作者　　修改日期　　　修改目的
*<br>--------------------------------------------------------
*/

package unt.du;

import java.sql.ResultSet;
import java.util.ArrayList;
import util.*;

public class UNTDU005F extends SuperBean{


String q_enterOrg;
String q_enterOrgName;
public String getQ_enterOrg(){ return checkGet(q_enterOrg); }
public void setQ_enterOrg(String s){ q_enterOrg=checkSet(s); }
public String getQ_enterOrgName(){ return checkGet(q_enterOrgName); }
public void setQ_enterOrgName(String s){ q_enterOrgName=checkSet(s); }

String q_caseNo;
public String getQ_caseNo(){ return checkGet(q_caseNo); }
public void setQ_caseNo(String s){ q_caseNo=checkSet(s); }
String q_ownership;
public String getQ_ownership(){ return checkGet(q_ownership); }
public void setQ_ownership(String s){ q_ownership=checkSet(s); }
String q_propertyNo;
public String getQ_propertyNo(){ return checkGet(q_propertyNo); }
public void setQ_propertyNo(String s){ q_propertyNo=checkSet(s); }
String q_propertyNoName;
public String getQ_propertyNoName(){ return checkGet(q_propertyNoName); }
public void setQ_propertyNoName(String s){ q_propertyNoName=checkSet(s); }
String q_lotNoS;
public String getQ_lotNoS(){ return checkGet(q_lotNoS); }
public void setQ_lotNoS(String s){ q_lotNoS=checkSet(s); }
String q_lotNoE;
public String getQ_lotNoE(){ return checkGet(q_lotNoE); }
public void setQ_lotNoE(String s){ q_lotNoE=checkSet(s); }
String q_computerType;
public String getQ_computerType(){ return checkGet(q_computerType); }
public void setQ_computerType(String s){ q_computerType=checkSet(s); }

public void mpUPcomputerType(){
	
	String whereSQL="";
	Database db = new Database();
	/*
	if (!"".equals(getQ_enterOrg())){
		whereSQL += " and m.enterorg = " + Common.sqlChar(q_enterOrg);
	}
	if(!"".equals(getQ_ownership())){
		whereSQL += " and m.ownership = " + Common.sqlChar(q_ownership);
	}
	if(!"".equals(getQ_propertyNo())){
		whereSQL += " and m.propertyNo = " + Common.sqlChar(q_propertyNo);
	}
	if(!"".equals(getQ_lotNoS())){
		whereSQL += " and m.lotNo >= " + Common.sqlChar(Common.formatFrontZero(q_lotNoS,7));
	}
	if(!"".equals(getQ_lotNoE())){
		whereSQL += " and m.lotNo <= " + Common.sqlChar(Common.formatFrontZero(q_lotNoE,7));
	}
	*/
	//if(!"".equals(getQ_computerType())){
	//	whereSQL += " and m.computerType = " + Common.sqlChar(q_computerType);
	//}
	try {
		String[] updatesql = new String[1];
		updatesql[0] = " update untmp_movable m set "+"\n"
					 + " m.computerType = "+ Common.sqlChar(q_computerType)
					 + " where 1=1 "
					 + " and m.enterorg = " + Common.sqlChar(q_enterOrg)
					 + " and m.ownership = " + Common.sqlChar(q_ownership)
					 + " and m.propertyNo = " + Common.sqlChar(q_propertyNo)
					 + " and m.lotNo >= " + Common.sqlChar(Common.formatFrontZero(q_lotNoS,7))
					 + " and m.lotNo <= " + Common.sqlChar(Common.formatFrontZero(q_lotNoE,7));
					 //+ whereSQL;
		
		System.out.println(updatesql[0]);
		//db.excuteSQL(updatesql);
		setErrorMsg("動產細項更新完成");
	} catch (Exception k) {
		k.printStackTrace();
	} finally {
		db.closeAll();
	}
	setStateUpdateSuccess();

}//mpUPcomputerType()
}//superBean




