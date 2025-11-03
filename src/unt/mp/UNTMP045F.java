/*
*<br>程式目的：動產條碼資料批次新增
*<br>程式代號：sysmt001f
*<br>程式日期：0961023
*<br>程式作者：shan
*<br>--------------------------------------------------------
*<br>修改作者　　修改日期　　　修改目的
*<br>--------------------------------------------------------
*/

package unt.mp;

import java.sql.ResultSet;
import java.util.ArrayList;
import util.*;

public class UNTMP045F extends UNTMP044Q{
String toggleAll;
public String getToggleAll(){ if (!"".equals(checkGet(toggleAll))) return "checked"; else return "unchecked"; }    
public void setToggleAll(String s){ toggleAll=checkSet(s); }

String strKeySet[] = null;
public String[] getsKeySet(){ return strKeySet; }
public void setsKeySet(String[] s){ strKeySet=s; }

String enterOrg;
public String getEnterOrg(){ return checkGet(enterOrg); }
public void setEnterOrg(String s){ enterOrg=checkSet(s); }

/**
 * <br>
 * <br>目的：依使用者挑選資料，寫入untmp_untmp044f
 * <br>參數：無
 * <br>傳回：無
*/
public void delPrintData(){
	int getcut=0;
	String str_barcode="";
	String[] strKeys = null;
if(getsKeySet()!=null){
	getcut = getsKeySet().length;	//有勾選的list中資料數
	String[] sqlcode=new String[getcut];
	for (int i=0; i<getcut; i++) {	//將挑選的資料組成一字串
		strKeys = getsKeySet()[i].split(",");
		sqlcode[i] = " delete untmp_untmp044f c "
				   + " where 1=1 "
				   + " and c.editid=" + Common.sqlChar(getUserID())
				   + " and c.enterorg=" + Common.sqlChar(strKeys[0])
				   + " and c.barcode=" + Common.sqlChar(strKeys[2])
				   + ""; 
	}
	Database db = new Database();
	try{
		db.excuteSQL(sqlcode);
	} catch (Exception e) {
		e.printStackTrace();
	} finally {
		db.closeAll();
	}
}//if(getsKeySet()!=null)
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
		String sql = " select c.editid " + "\n"
		 		   + " ,c.enterorg ,c.ownership ,c.propertyno ,c.serialno " + "\n"
		 		   + " ,c.propertyname ,c.nameplate ,c.specification ,c.unitname ,c.keepername ,c.barcode " + "\n"
		 		   + " from untmp_untmp044f c " + "\n"
		 		   + " where 1=1 " + "\n"
		 		   + " and c.editid= " + Common.sqlChar(getUserID()) + "\n"
		 		   + " order by c.propertyno ,c.serialno ,c.unitname ,c.keepername " + "\n"
				   + "";

			ResultSet rs = db.querySQL(sql);

			while (rs.next()) {
			String rowArray[]=new String[10];
			counter++;
			rowArray[0]=Common.get(rs.getString("enterorg")); 
			rowArray[1]=Common.get(rs.getString("ownership"));
			rowArray[2]=Common.get(rs.getString("propertyno")); 
			rowArray[3]=Common.get(rs.getString("serialno")); 
			rowArray[4]=Common.get(rs.getString("propertyname")); 
			rowArray[5]=Common.get(rs.getString("nameplate")); 
			rowArray[6]=Common.get(rs.getString("specification")); 
			rowArray[7]=Common.get(rs.getString("unitname"));
			rowArray[8]=Common.get(rs.getString("keepername"));
			rowArray[9]=Common.get(rs.getString("barcode"));
			
			objList.add(rowArray);
			if (counter==getListLimit()){
				setErrorMsg(getListLimitError());
				break;
			}
			};
		setStateQueryAllSuccess();
	} catch (Exception e) {
		e.printStackTrace();
	} finally {
		db.closeAll();
	}
	return objList;
}

}


