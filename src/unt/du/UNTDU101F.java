/*
*<br>程式目的：財產編號修正－土地
*<br>程式代號：untdu101f
*<br>程式日期：0990114
*<br>程式作者：chuhung
*<br>--------------------------------------------------------
*<br>修改作者　　修改日期　　　修改目的
*<br>Yuan-Ren	1000913		 100年功能擴充案
*<br>--------------------------------------------------------
*/

package unt.du;

import java.sql.ResultSet;
import java.util.ArrayList;

import unt.mp.UNTMP027F;
import util.*;

public class UNTDU101F extends SuperBean {

String q_enterOrg;
String q_enterOrgName;
String q_propertyNo;

public String getQ_enterOrg(){ return checkGet(q_enterOrg); }
public void setQ_enterOrg(String s){ q_enterOrg=checkSet(s); }
public String getQ_enterOrgName(){ return checkGet(q_enterOrgName); }
public void setQ_enterOrgName(String s){ q_enterOrgName=checkSet(s); }
public String getQ_propertyNo(){ return checkGet(q_propertyNo); }
public void setQ_propertyNo(String s){ q_propertyNo=checkSet(s); }

String enterOrg;
String enterOrgName;
String ownership;
String propertyNo;
String propertyNoName;
String serialNo;
String newPropertyNo;
String newPropertyNoSName;
public String getEnterOrg(){ return checkGet(enterOrg); }
public void setEnterOrg(String s){ enterOrg=checkSet(s); }
public String getEnterOrgName(){ return checkGet(enterOrgName); }
public void setEnterOrgName(String s){ enterOrgName=checkSet(s); }
public String getOwnership(){ return checkGet(ownership); }
public void setOwnership(String s){ ownership=checkSet(s); }
public String getPropertyNo(){ return checkGet(propertyNo); }
public void setPropertyNo(String s){ propertyNo=checkSet(s); }
public String getPropertyNoName(){ return checkGet(propertyNoName); }
public void setPropertyNoName(String s){ propertyNoName=checkSet(s); }
public String getSerialNo(){ return checkGet(serialNo); }
public void setSerialNo(String s){ serialNo=checkSet(s); }
public String getNewPropertyNo(){ return checkGet(newPropertyNo); }
public void setNewPropertyNo(String s){ newPropertyNo=checkSet(s); }
public String getNewPropertyNoSName(){ return checkGet(newPropertyNoSName); }
public void setNewPropertyNoSName(String s){ newPropertyNoSName=checkSet(s); }


String toggleAll;
public String getToggleAll(){ if (!"".equals(checkGet(toggleAll))) return "checked"; else return "unchecked"; }    
public void setToggleAll(String s){ toggleAll=checkSet(s); }    

String strKeySet[] = null;
public String[] getsKeySet(){ return strKeySet; }
public void setsKeySet(String[] s){ strKeySet=s; }


//確認所輸入的財產編號是否合乎規定
public boolean checkNewPropertyNo(){
	Database db = new Database();
	boolean flag = false;
	String sql = " select count(*) CheckPropertyNo from SYSPK_PROPERTYCODE a " +
	             " where a.enterorg in ('000000000A','" + this.getEnterOrg() + "')" +
                 " and a.propertyType='1' and substring(a.propertyno,1,1)='1' and substring(a.propertyno,1,3)!='111' " ;
    sql+=" and a.propertyNo = '" + Common.get(newPropertyNo) +"' ";
    
    try {
		ResultSet rs = db.querySQL(sql);
		while(rs.next()){
		    if("0".equals(rs.getString("CheckPropertyNo"))){
			    flag = true;
		    }
		}
	} catch (Exception e) {
		e.printStackTrace();
	}
	return flag;
}

public void update2(){
	Database db = new Database();
	int getcut=0;
	if(getsKeySet()!=null)
		getcut = getsKeySet().length;
	String[] strKeys = null;
    String newSerialNo = null;
	
	if (checkNewPropertyNo()){
		setErrorMsg("財產編號有誤,請重新輸入!!");	
		setStateUpdateError();
	}else{
		for (int i=0; i<getcut; i++) {   //依照勾選的項目一個個新增修改
			for(int j=0 ; j<4; j++){	 //將勾選的項目內容(enterOrg,ownership,propertyNo,serialno)取出
			    strKeys = getsKeySet()[i].split(",");
			}
			//取得新的財產分號
	        String sql = " select (case when max(serialNo) is null then 0 else max(serialNo) end) newSerialNo from UNTLA_LAND " +
	                     " where enterOrg = " + Common.sqlChar(strKeys[0]) +
	                     " and ownership = " + Common.sqlChar(strKeys[1]) +
	                     " and propertyNo = " + Common.sqlChar(newPropertyNo) ;
	        try {
		        ResultSet rs = db.querySQL(sql);
		        if(rs.next()){
		        	newSerialNo = rs.getString("newSerialNo");
		        }
		        
		        if("0".equals(newSerialNo)){
		        	newSerialNo = "0000001";
		        }else{
		        	newSerialNo = Common.formatFrontZero(newSerialNo, 7);
		        }
	        } catch (Exception e) {
		        e.printStackTrace();
	        } finally {
			    db.closeAll();
		    }
	        
	        //開始update財產編號及分號
	        String[] table1 = {"UNTLA_LAND","UNTLA_REDUCEDETAIL","UNTLA_ADJUSTDETAIL","UNTLA_MERGEDIVISION1",
				           		"UNTLA_USESEPARATE"};
			String[] table2 = {"UNTLA_MANAGE","UNTLA_ATTACHMENT","UNTLA_VALUE","UNTLA_PRICE","UNTLA_VIEWSCENE",
								"UNTLA_TAX","UNTLA_RIGHT","UNTLA_USE","UNTLA_DRAWPROOF"};
	        
	        ExecuteUpdate("1",table1,strKeys,newSerialNo);
	        ExecuteUpdate("2",table2,strKeys,newSerialNo); 
			setStateUpdateSuccess();
			//setErrorMsg("修改完成");
			
			//新增異動資料至異動檔
			String insertSql = " insert into UNTDU_PROPERTYNO( " +
								  "  propertytype, " +
				                   "  enterorg," +
				                   "  ownership," +
				                   "  oldpropertyno1," +
				                   "  oldserialno1," +
				                   "  oldlotno," +
				                   "  oldserialnos1," +
				                   "  oldserialnoe1," +
				                   "  newpropertyno," +
				                   "  newserialno," +
				                   "  newlotno," +
				                   "  newserialnos," +
				                   "  newserialnoe," +
				                   "  editid," +
				                   "  editdate," +
				                   "  edittime" +
                               " ) values ( " +
                               Common.sqlChar("1") + ", "+
                               Common.sqlChar(strKeys[0]) + ", "+
                               Common.sqlChar(strKeys[1]) + ", "+
                               Common.sqlChar(strKeys[2]) + ", "+
                               Common.sqlChar(strKeys[3]) + ", "+
                               Common.sqlChar("") + ", "+
                               Common.sqlChar("") + ", "+
                               Common.sqlChar("") + ", "+
                               Common.sqlChar(newPropertyNo) + ", "+
                               Common.sqlChar(newSerialNo) + ", "+
                               Common.sqlChar("") + ", "+
                               Common.sqlChar("") + ", "+
                               Common.sqlChar("") + ", "+
                               Common.sqlChar(getUserID()) + ", "+
                               Common.sqlChar(util.Datetime.getYYYMMDD()) + ", "+
                               Common.sqlChar(util.Datetime.getHHMMSS()) +
                               ")";
			try {
				db = new Database();
			    db.excuteSQL(insertSql);
		    } catch (Exception e) {
			    e.printStackTrace();
		    } finally {
				db.closeAll();
			}
		}
	}
}

public void ExecuteUpdate(String sqlNum,String[] tableName,String[] strKeys,String newSerialNo){
	Database db = null;
	String sql = null ;
	for(int i=0 ; i<tableName.length ; i++){
	    if("1".equals(sqlNum)){
	        sql = " update " + tableName[i] + " set "+
	              " propertyno = " + Common.sqlChar(newPropertyNo) + "," +
	              " serialno = " + Common.sqlChar(newSerialNo) + "," +
	              " oldpropertyno = " + Common.sqlChar(strKeys[2]) + "," +
	              " oldserialno = " + Common.sqlChar(strKeys[3]);
	    }else if("2".equals(sqlNum)){
	        sql = " update " + tableName[i] + " set "+
                  " propertyno = " + Common.sqlChar(newPropertyNo) + "," +
                  " serialno = " + Common.sqlChar(newSerialNo);
	    }
	    
	    sql += " where enterorg = " + Common.sqlChar(strKeys[0]) +
               " and ownership = " + Common.sqlChar(strKeys[1]) +
               " and propertyno = " + Common.sqlChar(strKeys[2]) +
               " and serialno = " + Common.sqlChar(strKeys[3]) ;
        try {
        	db = new Database();
        	db.excuteSQL(sql);        	
	    } catch (Exception e) {
		    e.printStackTrace();
	    } finally {
		    db.closeAll();
	    }
	}   	
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
		String sql = " select a.enterorg,a.ownership,a.propertyno,a.serialno " 
				   + " ,(select o.organaname from SYSCA_Organ o where a.enterorg = o.organid)as organAName "
			       + " ,(select z.codename from SYSCA_CODE z where 1=1 and (z.codekindid='owa' or z.codekindid='OWA') and z.codeid = a.ownership)as ownershipName "
				   + " ,(select p.propertyname from SYSPK_PROPERTYCODE p where  a.propertyno = p.propertyno)as propertyName "
				   + " from UNTLA_LAND a "
				   + " where 1=1 "
				   + " and a.propertykind != '04' " 
				   + "";
		
		if(!"".equals(getQ_enterOrg())){
			sql += " and a.enterorg =" +Common.sqlChar(q_enterOrg);
		}
		if(!"".equals(getQ_propertyNo())){
			sql += " and a.propertyno =" +Common.sqlChar(q_propertyNo);			
		}
		sql += " order by a.ownership,a.propertykind,a.propertyno,a.serialno ";
		//System.out.println(sql);
		ResultSet rs = db.querySQL(sql);
		while (rs.next()){
			counter++;
			String rowArray[]=new String[7];
			rowArray[0]=Common.get(rs.getString("enterOrg")); 
			rowArray[1]=Common.get(rs.getString("ownership")); 
			rowArray[2]=Common.get(rs.getString("propertyno")); 
			rowArray[3]=Common.get(rs.getString("serialno")); 
			rowArray[4]=Common.get(rs.getString("organAName")); 
			rowArray[5]=Common.get(rs.getString("ownershipName")); 
			rowArray[6]=Common.get(rs.getString("propertyName")); 
			objList.add(rowArray);
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


