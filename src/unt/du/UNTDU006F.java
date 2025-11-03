/*
*<br>程式目的：建物及土地，土地標示資料(補登，修改)
*<br>程式代號：untdu006f
*<br>程式日期：0981222
*<br>程式作者：shan
*<br>--------------------------------------------------------
*<br>修改作者　　修改日期　　　修改目的
*<br>--------------------------------------------------------
*/

package unt.du;

import java.sql.ResultSet;
import java.util.ArrayList;
import util.*;

public class UNTDU006F extends SuperBean{

String q_enterOrg;
String q_enterOrgName;
public String getQ_enterOrg(){ return checkGet(q_enterOrg); }
public void setQ_enterOrg(String s){ q_enterOrg=checkSet(s); }
public String getQ_enterOrgName(){ return checkGet(q_enterOrgName); }
public void setQ_enterOrgName(String s){ q_enterOrgName=checkSet(s); }

String q_caseNo;
String q_ownership;
String q_propertyNo;
String q_propertyNoName;
public String getQ_caseNo(){ return checkGet(q_caseNo); }
public void setQ_caseNo(String s){ q_caseNo=checkSet(s); }
public String getQ_ownership(){ return checkGet(q_ownership); }
public void setQ_ownership(String s){ q_ownership=checkSet(s); }
public String getQ_propertyNo(){ return checkGet(q_propertyNo); }
public void setQ_propertyNo(String s){ q_propertyNo=checkSet(s); }
public String getQ_propertyNoName(){ return checkGet(q_propertyNoName); }
public void setQ_propertyNoName(String s){ q_propertyNoName=checkSet(s); }

String q_serialNo;
public String getQ_serialNo(){ return checkGet(q_serialNo); }
public void setQ_serialNo(String s){ q_serialNo=checkSet(s); }

String q_lotNoS;
public String getQ_lotNoS(){ return checkGet(q_lotNoS); }
public void setQ_lotNoS(String s){ q_lotNoS=checkSet(s); }
String q_lotNoE;
public String getQ_lotNoE(){ return checkGet(q_lotNoE); }
public void setQ_lotNoE(String s){ q_lotNoE=checkSet(s); }
String q_computerType;
public String getQ_computerType(){ return checkGet(q_computerType); }
public void setQ_computerType(String s){ q_computerType=checkSet(s); }

String q_signNo1;
String q_signNo2;
String q_signNo3;
String q_signNo4;

public String getQ_signNo1(){ return checkGet(q_signNo1); }
public void setQ_signNo1(String s){ q_signNo1=checkSet(s); }
public String getQ_signNo2(){ return checkGet(q_signNo2); }
public void setQ_signNo2(String s){ q_signNo2=checkSet(s); }
public String getQ_signNo3(){ return checkGet(q_signNo3); }
public void setQ_signNo3(String s){ q_signNo3=checkSet(s); }
public String getQ_signNo4(){ return checkGet(q_signNo4); }
public void setQ_signNo4(String s){ q_signNo4=checkSet(s); }

String q_upType;
public String getQ_upType(){ return checkGet(q_upType); }
public void setQ_upType(String s){ q_upType=checkSet(s); }

public void mpUPcomputerType(){
	String q_upType_name ="";
	String[] array = null;
	int array_len = 0;
	
/*
 * ALL Tables 更新一覽表  by timtoy 2009.12.22
 * 
 * 土地主檔：UNTLA_Land
 * 土地增減值作業：UNTLA_AdjustDetail
 * 土地減少作業：UNTLA_ReduceDetail
 * 土地使用分區異動資料維護：UNTLA_UseSeparate
 * 土地合併分割對照檔：UNTLA_MergeDivision1
 * 
 * 建物主檔：UNTBU_Building
 * 建物增減值作業：UNTBU_AdjustDetail
 * 建物減少作業：UNTBU_ReduceDetail
 * 
 */
	if("01".equals(getQ_upType())){
		q_upType_name = "土地資料";
		array = new String[]{"UNTLA_Land","UNTLA_AdjustDetail","UNTLA_ReduceDetail","UNTLA_UseSeparate","UNTLA_MergeDivision1"};
		array_len = array.length;
	}else if("02".equals(getQ_upType())){
		q_upType_name = "建物資料";
		array = new String[]{"UNTBU_Building","UNTBU_AdjustDetail","UNTBU_ReduceDetail"};
		array_len = array.length;
	}
	
	//==== SQL 預備更新內容區  ====//
	String signno="";
	if(!"".equals(getQ_signNo1())){ 
			signno += q_signNo1.substring(0,1);
	}
	if(!"".equals(getQ_signNo2())){
			signno += q_signNo2.substring(1,3);	
	}
	if(!"".equals(getQ_signNo3())){
			signno += q_signNo3.substring(3,7);	
	}
	if(!"".equals(getQ_signNo4())){
			signno += q_signNo4;	
	}
	signno = " a.signno = " + Common.sqlChar(Common.get(signno));
	
	//==== SQL Where 條件區  ====//
	String whereSQL="";
	if (!"".equals(getQ_enterOrg())){
		whereSQL += " and a.enterorg = " + Common.sqlChar(q_enterOrg);
	}
	if(!"".equals(getQ_ownership())){
		whereSQL += " and a.ownership = " + Common.sqlChar(q_ownership);
	}
	if(!"".equals(getQ_caseNo())){
		whereSQL += " and a.caseno = " + Common.sqlChar(q_caseNo);
	}
	if(!"".equals(getQ_propertyNo())){
		whereSQL += " and a.propertyNo = " + Common.sqlChar(q_propertyNo);
	}
	if(!"".equals(getQ_serialNo())){
		whereSQL += " and a.serialno = " + Common.sqlChar(Common.formatFrontZero(q_serialNo,7));
	}
	
	String[] updatesql = new String[array_len];
	for(int i=0 ; i<array_len; i++){
		updatesql[i]= " update "+ array[i] +" a set " + signno + " where 1=1 " + whereSQL ;
//System.out.println("-- untdu006f --\n"+updatesql[i]);
	}
	array = null;
	array_len = 0;


	Database db = new Database();
	try {
		db.excuteSQL(updatesql);
		setStateUpdateSuccess();
		setErrorMsg(q_upType_name + "：更新完成！");
	} catch (Exception k) {
		setStateUpdateError();
		setErrorMsg(q_upType_name + "：更新失敗！");
		System.out.println("-- untdu006f 更新失敗 --\n");
		k.printStackTrace();
	} finally {
		db.closeAll();
	}
	

	
}

}




