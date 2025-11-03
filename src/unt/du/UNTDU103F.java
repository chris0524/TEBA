/*
*<br>程式目的：財產編號修正－建物
*<br>程式代號：untdu103f
*<br>程式日期：0990118
*<br>程式作者：chuhung
*<br>--------------------------------------------------------
*<br>修改作者　　修改日期　　　修改目的
*<br>Yuan-Ren	20110905	 100年功能擴充案
*<br>--------------------------------------------------------
*/

package unt.du;

import java.sql.ResultSet;
import java.util.ArrayList;

import util.*;

public class UNTDU103F extends SuperBean {

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
public String getPropertyNoName(){ return checkGet(newPropertyNo); }
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
			   " where a.enterorg='000000000A' " +
               " and a.propertytype='1' and substring(a.propertyno,1,1)='2' " ;
  sql+=" and a.propertyno = '" + Common.get(newPropertyNo) +"' ";

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
	Database db2 = new Database();
	//String sql2 = "";
	int getcut=0;
	if(getsKeySet()!=null){
		getcut = getsKeySet().length;
	}
	
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
			
			Database db1=new Database();
			ResultSet rs1;	
			boolean limitYearFlag=false;
			try{
				rs1=db1.querySQL(" select limitYear "+
						" from SYSPK_PROPERTYCODE "+
						" where enterorg='000000000A' "+
						" and propertytype='1' "+
						" and substring(propertyno,1,1)='2' "+
						" and propertyno=" +Common.sqlChar(newPropertyNo) );
				if(rs1.next()){
					db1.querySQL("update UNTBU_BUILDING x set " +"\n"+
					   " x.otherlimityear = "+null+"\n"+
					   " where 1=1 " +"\n"+
					   " and x.enterorg = " + Common.sqlChar(strKeys[0]) +"\n"+
					   " and x.ownership = " + Common.sqlChar(strKeys[1]) +"\n"+
				       " and x.propertyno = " + Common.sqlChar(strKeys[2]) +"\n"+
					   " and x.serialno = " + Common.sqlChar(strKeys[3])+"\n"+
					   "");									
				}		
			}catch(Exception e){
				e.getStackTrace();
			}finally{
				db1.closeAll();
			}
			
			
			
			//取得新的財產分號
			ResultSet rs = null;
	        String sql = " select (case when max(serialno) is null then 0 else max(serialno) end) newSerialNo from UNTBU_BUILDING " +
		                    " where enterorg = " + Common.sqlChar(strKeys[0]) +
		                    " and ownership = " + Common.sqlChar(strKeys[1]) +
		                    " and propertyno = " + Common.sqlChar(newPropertyNo) ;
	        
	        try {
		        rs = db2.querySQL(sql);
		        if(rs.next()){
		        	newSerialNo = rs.getString("newSerialNo");
		        }
		        //rs.close();
		        
		        if("0".equals(newSerialNo)){
		        	newSerialNo = "0000001";
		        }else{
		        	newSerialNo = Common.formatFrontZero(newSerialNo, 7);
		        }
	        } catch (Exception e) {
		        e.printStackTrace();
	        } 
	        
//	        String useYM = "";
//	        String sql3 = " select lpad(decode(substring(a.builddate,4,2),'01',substr(a.builddate,0,3)+nvl((select p.limityear from SYSPK_PropertyCode p where " +
//	        			  " p.propertyno = " +Common.sqlChar(newPropertyNo)+
//	        			  " ),a.otherLimitYear)-1 || '12',substr(a.builddate,0,3)+nvl((select p.limityear from SYSPK_PropertyCode p where " +
//	        			  " p.propertyno = " +Common.sqlChar(newPropertyNo)+
//	        			  " ),a.otherLimitYear)|| substr(a.builddate,4,2)-1),5,'0')as useEndYM " +
//	        		      " from UNTBU_Building a " +
//	        		      " where " +
//                          " a.enterOrg = " + Common.sqlChar(strKeys[0]) +
//                          " and a.ownership = " + Common.sqlChar(strKeys[1]) +
//                          " and a.propertyNo = " + Common.sqlChar(strKeys[2]) +
//	                      " and a.serialNo = " + Common.sqlChar(strKeys[3]);
//	                      ;
//
//	        try {
//	        	rs = db2.querySQL(sql3);
//		        while(rs.next()){
//		        	useYM = rs.getString("useEndYM");
//		        }
//		        rs.close();
//	        } catch (Exception e) {
//		        e.printStackTrace();
//	        } 
//
//	        String permitReduceDate = "";
//	        String sql4 = " select lpad(substr(a.builddate,0,3)+nvl((select p.limityear from SYSPK_PropertyCode p where " +
//	        			  " p.propertyno = " +Common.sqlChar(newPropertyNo)+
//	        			  " ),a.otherLimitYear)|| substr(a.builddate,4,2) || substr(a.builddate,6,2),7,'0')as permitReduceDate " +
//	        		      " from UNTBU_Building a " +
//	        		      " where " +
//                          " a.enterOrg = " + Common.sqlChar(strKeys[0]) +
//                          " and a.ownership = " + Common.sqlChar(strKeys[1]) +
//                          " and a.propertyNo = " + Common.sqlChar(strKeys[2]) +
//	                      " and a.serialNo = " + Common.sqlChar(strKeys[3]) ;
//
//	        try {
//	        	rs = db2.querySQL(sql4);
//		        while(rs.next()){
//		        	permitReduceDate = rs.getString("permitReduceDate");
//		        	
//		        }
//		        rs.close();
//	        } catch (Exception e) {
//		        e.printStackTrace();
//	        } 
//	        
//	        String apportionEndYM = "";
//	        String sql5 = " select lpad(substr(a.builddate,0,3)+nvl((select p.limityear from SYSPK_PropertyCode p where " +
//	        			  " p.propertyno = " +Common.sqlChar(newPropertyNo)+
//	        			  " ),a.otherLimitYear)|| substr(a.builddate,4,2),5,'0')as apportionEndYM " +
//	        		      " from UNTBU_Building a " +
//	        		      " where " +
//                          " a.enterOrg = " + Common.sqlChar(strKeys[0]) +
//                          " and a.ownership = " + Common.sqlChar(strKeys[1]) +
//                          " and a.propertyNo = " + Common.sqlChar(strKeys[2]) +
//	                      " and a.serialNo = " + Common.sqlChar(strKeys[3]) ;
//
//	        try {
//	        	rs = db2.querySQL(sql5);
//		        while(rs.next()){
//		        	apportionEndYM = rs.getString("apportionEndYM");
//		        	
//		        }
//		        rs.close();
//	        } catch (Exception e) {
//		        e.printStackTrace();
//	        }
	        
	        
	        
	        //開始update財產編號及分號,"UNTBU_AdjustDetail"
	        String[] table1 = {"UNTBU_Building","UNTBU_ReduceDetail","UNTBU_AdjustDetail"};
	        String[] table2 = {"UNTBU_Manage","UNTBU_Floor","UNTBU_Attachment1","UNTBU_Base",
	        		           "UNTBU_CommonUse","UNTBU_Attachment2","UNTBU_ViewScene","UNTBU_Tax",
	        		           "UNTBU_DrawProof"};
	        String sql2 = "";
//	        sql2 += ExecuteUpdate("1",table1,strKeys,newSerialNo,useYM,permitReduceDate,apportionEndYM);
//	        sql2 += ExecuteUpdate("2",table2,strKeys,newSerialNo,useYM,permitReduceDate,apportionEndYM); 
	        
	        sql2 += ExecuteUpdate("1",table1,strKeys,newSerialNo,"","","");
	        sql2 += ExecuteUpdate("2",table2,strKeys,newSerialNo,"","","");
	        
			setStateUpdateSuccess();

			//新增異動資料至異動檔
		    sql2 += " insert into UNTDU_PropertyNo( " +
			                   "  propertyType, " +
			                   "  enterOrg," +
			                   "  ownership," +
			                   "  oldPropertyNo1," +
			                   "  oldSerialNo1," +
			                   "  oldLotNo," +
			                   "  oldSerialNoS1," +
			                   "  oldSerialNoE1," +
			                   "  newPropertyNo," +
			                   "  newSerialNo," +
			                   "  newLotNo," +
			                   "  newSerialNoS," +
			                   "  newSerialNoE," +
			                   "  editID," +
			                   "  editDate," +
			                   "  editTime" +
                               " ) values ( " +
                               Common.sqlChar("3") + ", "+
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
                               ");";
			String[] strsql = sql2.split(";");

			setErrorMsg("更新完成");
			
			try {
			    db.excuteSQL(strsql);			    
		    } catch (Exception e) {
			    e.printStackTrace();
		    } finally {
				db.closeAll();
				db2.closeAll();
			}
		}
	}
}

public String ExecuteUpdate(String sqlNum,String[] tableName,String[] strKeys,String newSerialNo,String useYM,String permitReduceDate,String apportionEndYM){
	//Database db = new Database();
	String sql = "" ;
//	String useEndYMstr = " useEndYM = " + Common.sqlChar(useYM); 
//	String permitReduceDateStr = " permitReduceDate = " + Common.sqlChar(permitReduceDate) ;
//	String apportionEndYMStr = " apportionEndYM = " + Common.sqlChar(apportionEndYM) ;
	
	for(int i=0 ; i<tableName.length ; i++){
	    if("1".equals(sqlNum)){
	        sql += " update " + tableName[i] + " set "+
	              " propertyNo = " + Common.sqlChar(newPropertyNo) + "," +
	              " serialNo = " + Common.sqlChar(newSerialNo) + "," +
	              " oldPropertyNo = " + Common.sqlChar(strKeys[2]) + "," +
	              " oldSerialNo = " + Common.sqlChar(strKeys[3]);
	        
//	       //開始update使用年限截止年月
//	        if("UNTBU_Building".equals(tableName[i])){
//	        	sql += "," + useEndYMstr + "," + permitReduceDateStr+ "," + apportionEndYMStr;;
//	        }else if("UNTBU_ReduceDetail".equals(tableName[i])){
//	        	sql += "," + permitReduceDateStr;
//	        }
	    }else if("2".equals(sqlNum)){
	        sql += " update " + tableName[i] + " set "+
                  " propertyNo = " + Common.sqlChar(newPropertyNo) + "," +
                  " serialNo = " + Common.sqlChar(newSerialNo);
	    }
	    
	    sql += " where enterOrg = " + Common.sqlChar(strKeys[0]) +
               " and ownership = " + Common.sqlChar(strKeys[1]) +
               " and propertyNo = " + Common.sqlChar(strKeys[2]) +
               " and serialNo = " + Common.sqlChar(strKeys[3])+";" ;
	} 
	return sql;
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
	String limit = " (case when (select p.limityear from SYSPK_PropertyCode p where a.propertyno = p.propertyno) is null then a.otherLimitYear else (select p.limityear from SYSPK_PropertyCode p where a.propertyno = p.propertyno) end) ";	
	try {
		String sql = " select a.enterorg,a.ownership,a.propertyno,a.serialno " 
				   + " ,(select o.organAName from SYSCA_Organ o where a.enterorg = o.organid)as organAName "
			       + " ,(select z.codeName from sysca_code z where 1=1 and (z.codeKindID='owa' or z.codeKindID='OWA') and z.codeid=a.ownership ) as  ownershipName "
				   + " ,(select p.propertyName from SYSPK_PropertyCode p where  a.propertyno = p.propertyno)as propertyName "
				   + " ,(case substring(a.builddate,4,2) when '01' then (substring(a.builddate,0,3)+" + limit + "-1) + '12' else (substring(a.builddate,0,3)+" + limit + " +  substring(a.builddate,4,2)-1) end)as useEndYM " 
				   + " ,substring(a.builddate,0,3)+"+ limit +" + substring(a.builddate,4,2) as permitReduceDate "
				   + " from UNTBU_Building a "
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
System.out.println(sql);		
		ResultSet rs = db.querySQL(sql);
		while (rs.next()){
			counter++;
			String rowArray[]=new String[9];
			rowArray[0]=Common.get(rs.getString("enterOrg")); 
			rowArray[1]=Common.get(rs.getString("ownership")); 
			rowArray[2]=Common.get(rs.getString("propertyno")); 
			rowArray[3]=Common.get(rs.getString("serialno")); 
			rowArray[4]=Common.get(rs.getString("useEndYM")); 
			rowArray[5]=Common.get(rs.getString("permitReduceDate"));
			rowArray[6]=Common.get(rs.getString("organAName")); 
			rowArray[7]=Common.get(rs.getString("ownershipName")); 
			rowArray[8]=Common.get(rs.getString("propertyName")); 
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


