/*
*<br>程式目的：財產編號修正－土地改良物
*<br>程式代號：untdu102f
*<br>程式日期：0990112
*<br>程式作者：Timtoy.Tsai
*<br>--------------------------------------------------------
*<br>修改作者　　修改日期　　　修改目的
*<br>Yuan-Ren	20110907	 100年功能擴充案
*<br>--------------------------------------------------------
*/

package unt.du;

import java.sql.ResultSet;
import java.util.ArrayList;

import util.*;

public class UNTDU102F extends QueryBean{


String enterOrg;
String enterOrgName;
String ownership;
String propertyNo;
String propertyNoName;
String serialNo;


String q_enterOrg;
String q_enterOrgName;
String q_propertyNo;
String q_propertyNoName;
String q_newPropertyNo;
String q_newPropertyNoName;

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

public String getQ_enterOrg(){ return checkGet(q_enterOrg); }
public void setQ_enterOrg(String s){ q_enterOrg=checkSet(s); }
public String getQ_enterOrgName(){ return checkGet(q_enterOrgName); }
public void setQ_enterOrgName(String s){ q_enterOrgName=checkSet(s); }
public String getQ_propertyNo(){ return checkGet(q_propertyNo); }
public void setQ_propertyNo(String s){ q_propertyNo=checkSet(s); }
public String getQ_propertyNoName(){ return checkGet(q_propertyNoName); }
public void setQ_propertyNoName(String s){ q_propertyNoName=checkSet(s); }
public String getQ_newPropertyNo(){ return checkGet(q_newPropertyNo); }
public void setQ_newPropertyNo(String s){ q_newPropertyNo=checkSet(s); }
public String getQ_newPropertyNoName(){ return checkGet(q_newPropertyNoName); }
public void setQ_newPropertyNoName(String s){ q_newPropertyNoName=checkSet(s); }


String strKeySet[] = null;
public String[] getsKeySet(){ return strKeySet; }
public void setsKeySet(String[] s){ strKeySet=s; }
//String toggleAll;
//public String getToggleAll(){ if (!"".equals(checkGet(toggleAll))) return "checked"; else return "unchecked"; }    
//public void setToggleAll(String s){ toggleAll=checkSet(s); }
String[] sql_array = null;
int sql_array_index = -1;


public void updateBatch(){

	Database db = new Database(); 
	ResultSet rs = null;
	
	if(getsKeySet()!=null) {
		
		int checkbox_sum = getsKeySet().length;	//前端List區中，有被勾選的資料筆數
//System.out.println("-- untdu102f 前端List區中，有被勾選的資料筆數 --"+checkbox_sum);	
		//sql_array = new String[checkbox_sum*(10+1)];
		sql_array = new String[checkbox_sum*(12+1)];
//System.out.println("-- sql_array sum(index) --"+checkbox_sum*(10+1));		
		try {
			//========== 計算新的財產分號 ========================================================================//                                                               
			String get_newSerialNo_sql = " select (case when max(serialNo) is null then 0 else max(serialNo) end) newSerialNo " +"\n"+
										 " from UNTRF_ATTACHMENT where 1=1 " +"\n"+
										 " and enterorg   = " + Common.sqlChar(enterOrg) +"\n"+
										 " and ownership  = " + Common.sqlChar(ownership) +"\n"+
										 " and propertyno = "+ Common.sqlChar(q_newPropertyNo)+"\n"+
										 "";
			String newSerialNo = ""; //新的財產分號 
			rs = db.querySQL(get_newSerialNo_sql);
			if (rs.next()){
				newSerialNo = rs.getString("newSerialNo");
//System.out.println("首次計算_新的財產分號："+newSerialNo);
			}
			if("0".equals(newSerialNo)){
	        	newSerialNo = "0000001";
	        }else{
	        	newSerialNo = Common.formatFrontZero(newSerialNo, 7);
	        }
			
			for(int i=0; i<checkbox_sum; i++){

				String[] strKeys = getsKeySet()[i].split(",");
				String get_errorValue_sql = "" +
						" select a.enterorg, a.ownership, a.propertyno, a.serialno, a.oldpropertyno, a.oldserialno, " +"\n"+
						" (select case when x.limitYear is null then a.otherLimitYear else x.limitYear end from SYSPK_PROPERTYCODE x where x.enterorg in (a.enterorg,'000000000A') and x.propertyno="+Common.sqlChar(q_newPropertyNo)+") as limitYear, " +"\n"+
						" (case  when a.builddate is null then '0000000' else a.builddate end) as builddate, " +"\n"+
						" substring((case when a.builddate is null then '0000000' else a.builddate end),1,3) as builddate_yyy, " +"\n"+
						" substring((case when a.builddate is null then '0000000' else a.builddate end),4,2) as builddate_mm," +"\n"+
						" substring((case when a.builddate is null then '0000000' else a.builddate end),4,4) as builddate_mmdd " +"\n"+
					" from UNTRF_ATTACHMENT a where 1=1 " +"\n"+
						" and a.enterorg = " + Common.sqlChar(strKeys[0]) +"\n"+
						" and a.ownership = " + Common.sqlChar(strKeys[1]) +"\n"+
						" and a.propertyno = " + Common.sqlChar(strKeys[2]) +"\n"+
						" and a.serialno = " + Common.sqlChar(strKeys[3])+"\n"+
					"" ;
//System.out.println("-- untdu102f get_errorValue_sql --\n"+get_errorValue_sql);
				
				//========== 計算預備待會更新要用的值 ====================================================//
				String newUseEndYM = ""; //使用年限截止年月
				String newPermitReduceDate = ""; //可報廢日期
				String newapportionEndYM="";
				rs = db.querySQL(get_errorValue_sql);
				if (rs.next()){
					//入帳機關、權屬、新系統財產編號、新系統原財產分號、舊系統財產編號、舊系統財產分號
					this.setEnterOrg(rs.getString("enterOrg")); this.setOwnership(rs.getString("ownership")); 
					this.setPropertyNo(rs.getString("propertyNo")); this.setSerialNo(rs.getString("serialNo"));
					//使用年限
					int limitYear = Integer.parseInt(rs.getString("limitYear")); 
//System.out.println("使用年限："+limitYear); System.out.println("建築日期："+rs.getString("builddate"));
					//計算使用年限截止年月
					int builddate_yyy = Integer.parseInt(rs.getString("builddate_yyy"))+limitYear;
					int builddate_mm = Integer.parseInt(rs.getString("builddate_mm"));
					if(builddate_mm == 1){
						newUseEndYM = Common.formatFrontZero(String.valueOf(builddate_yyy-1),3)+"12";
					}else{
						newUseEndYM = Common.formatFrontZero(String.valueOf(builddate_yyy),3)+Common.formatFrontZero(String.valueOf(builddate_mm-1),2);
					}
//System.out.println("使用年限截止年月："+newUseEndYM);
					//計算可報廢日期
					newPermitReduceDate = Common.formatFrontZero(String.valueOf(builddate_yyy),3)+rs.getString("builddate_mmdd");
////System.out.println("可報廢日期："+newPermitReduceDate+"\n");
					newapportionEndYM = Common.formatFrontZero(String.valueOf(builddate_yyy),3)+rs.getString("builddate_mm");
				}

				Database db1=new Database();
				ResultSet rs1;	
				boolean limitYearFlag=false;
				try{
					rs1=db1.querySQL(" select limitYear "+
							" from SYSPK_PROPERTYCODE "+
							" where enterorg='000000000A' "+
							" and propertytype='1' "+
							" and substring(propertyno,1,3)='111' "+
							" and propertyno=" +Common.sqlChar(q_newPropertyNo) );
					
					if(rs1.next()){						
						limitYearFlag=true;					
					}		
				}catch(Exception e){
					e.getStackTrace();
				}finally{
					db1.closeAll();
				}
				
				//========== 取得ALL預備更新的SQLs ======================================================//
				getUpdateSQL(i, sql_array, sql_array_index, newUseEndYM, newPermitReduceDate, newapportionEndYM, newSerialNo ,limitYearFlag);
				newSerialNo = Common.formatFrontZero(String.valueOf(Integer.parseInt(newSerialNo)+1),7);
//System.out.println("多次計算_新的財產分號："+newSerialNo);
			}
			
			for(int i=0 ; i <= sql_array.length ; i++){
//System.out.println("--預備更新的SQLs--"+i+"----"+"\n"+sql_array[i]);
			}
			
			db.excuteSQL(sql_array);
			setStateUpdateSuccess();
			setErrorMsg("更新完成");		
			
		} catch (Exception e) {
			setErrorMsg("更新失敗！");	
			e.printStackTrace();
		} finally {
			db.closeAll();
		}
	}
}



protected void getUpdateSQL( int checkBoxNumber, String[] array, int index, 
							 String newUseEndYM, 
							 String newPermitReduceDate, 
							 String newapportionEndYM,
							 String newSerialNo,
							 boolean limitYearFlag)
{
	String[] strKeys = getsKeySet()[checkBoxNumber].split(",");
		
	/*
	 * 更新限用年度limitYear資料
	 * UNTRF_Attachment：otherLimitYear
	 * */
	index++;
	if(limitYearFlag){	
		array[index]="update UNTRF_ATTACHMENT set " +"\n"+
						   " otherlimityear = "+null+"\n"+
						   " where 1=1 " +"\n"+
						   " and enterorg = " + Common.sqlChar(strKeys[0]) +"\n"+
						   " and ownership = " + Common.sqlChar(strKeys[1]) +"\n"+
					       " and propertyno = " + Common.sqlChar(strKeys[2]) +"\n"+
						   " and serialno = " + Common.sqlChar(strKeys[3])+"\n"+
						   "";				
	//System.out.println("----"+index+"----"+array[index]);
	}else{
		array[index]="";
	}
	
	
	
	/*
	 * 更新「使用年限截止年月」資料
	 * UNTRF_Attachment：useEndYM
	 * */
//	index++;
//	array[index]="update UNTRF_ATTACHMENT set " +"\n"+
//					   " useendym = "+Common.sqlChar(newUseEndYM)+"\n"+
//					" where 1=1 " +"\n"+
//					   " and enterorg = " + Common.sqlChar(strKeys[0]) +"\n"+
//					   " and ownership = " + Common.sqlChar(strKeys[1]) +"\n"+
//				       " and propertyno = " + Common.sqlChar(strKeys[2]) +"\n"+
//					   " and serialno = " + Common.sqlChar(strKeys[3])+"\n"+
//					   "";
	//System.out.println("----"+index+"----"+array[index]);
	
	/*
	 * 更新「可報廢日期」資料
	 * UNTRF_Attachment：apportionEndYM
	 * UNTRF_ReduceDetail：apportionEndYM
	 * */
//	index++;
//	array[index]="update UNTRF_ATTACHMENT set " +"\n"+
//					   " permitreducedate = "+Common.sqlChar(newPermitReduceDate)+"\n"+
//					" where 1=1 " +"\n"+
//					   " and enterorg = " + Common.sqlChar(strKeys[0]) +"\n"+
//					   " and ownership = " + Common.sqlChar(strKeys[1]) +"\n"+
//				       " and propertyno = " + Common.sqlChar(strKeys[2]) +"\n"+
//					   " and serialno = " + Common.sqlChar(strKeys[3])+"\n"+
//					   "";
	//System.out.println("----"+index+"----"+array[index]);
	
//	index++;
//	array[index]="update UNTRF_REDUCEDETAIL set " +"\n"+
//					   " permitreducedate = "+Common.sqlChar(newPermitReduceDate)+"\n"+
//					" where 1=1 " +"\n"+
//					   " and enterorg = " + Common.sqlChar(strKeys[0]) +"\n"+
//					   " and ownership = " + Common.sqlChar(strKeys[1]) +"\n"+
//				       " and propertyno = " + Common.sqlChar(strKeys[2]) +"\n"+
//					   " and serialno = " + Common.sqlChar(strKeys[3])+"\n"+
//					   "";
	//System.out.println("----"+index+"----"+array[index]);

	/*
	 * 更新「攤提年限截止年月」資料
	 * UNTRF_Attachment：permitReduceDate
	 * */
//	index++;
//	array[index]="update UNTRF_ATTACHMENT set " +"\n"+
//					   " apportionendym = "+Common.sqlChar(newapportionEndYM)+"\n"+
//					" where 1=1 " +"\n"+
//					   " and enterorg = " + Common.sqlChar(strKeys[0]) +"\n"+
//					   " and ownership = " + Common.sqlChar(strKeys[1]) +"\n"+
//				       " and propertyno = " + Common.sqlChar(strKeys[2]) +"\n"+
//					   " and serialno = " + Common.sqlChar(strKeys[3])+"\n"+
//					   "";
	//System.out.println("----"+index+"----"+array[index]);
	
//	index++;
//	array[index]="update UNTRF_REDUCEDETAIL set " +"\n"+
//					   " apportionendym = "+Common.sqlChar(newapportionEndYM)+"\n"+
//					" where 1=1 " +"\n"+
//					   " and enterorg = " + Common.sqlChar(strKeys[0]) +"\n"+
//					   " and ownership = " + Common.sqlChar(strKeys[1]) +"\n"+
//				       " and propertyno = " + Common.sqlChar(strKeys[2]) +"\n"+
//					   " and serialno = " + Common.sqlChar(strKeys[3])+"\n"+
//					   "";
	//System.out.println("----"+index+"----"+array[index]);
	
	
	/*
	 * 更新「財產編號、財產分號、原財產編號、原財產分號」資料
	 * */
	index++;
	//土地改良物主檔 UNTRF_Attachment
	array[index]="update UNTRF_ATTACHMENT set " +"\n"+
					   " propertyno = "+Common.sqlChar(q_newPropertyNo)+ "," +"\n"+
					   " serialno = "+Common.sqlChar(newSerialNo)+ "," +"\n"+
					   " oldpropertyno = "+Common.sqlChar(strKeys[2])+ "," +"\n"+
					   " oldserialno = "+Common.sqlChar(strKeys[3])+"\n"+
					" where 1=1 " +"\n"+
					   " and enterorg = " + Common.sqlChar(strKeys[0]) +"\n"+
					   " and ownership = " + Common.sqlChar(strKeys[1]) +"\n"+
				       " and propertyno = " + Common.sqlChar(strKeys[2]) +"\n"+
					   " and serialno = " + Common.sqlChar(strKeys[3])+"\n"+
					   "";
	//System.out.println("----"+index+"----"+array[index]);
	
	index++;
	//土地改良物增減值單明細檔UNTRF_AdjustDetail
	array[index]="update UNTRF_ADJUSTDETAIL set " +"\n"+
					   " propertyno = "+Common.sqlChar(q_newPropertyNo)+ "," +"\n"+
					   " serialno = "+Common.sqlChar(newSerialNo)+ "," +"\n"+
					   " oldpropertyno = "+Common.sqlChar(strKeys[2])+ "," +"\n"+
					   " oldserialno = "+Common.sqlChar(strKeys[3])+"\n"+
					" where 1=1 " +"\n"+
					   " and enterorg = " + Common.sqlChar(strKeys[0]) +"\n"+
					   " and ownership = " + Common.sqlChar(strKeys[1]) +"\n"+
				       " and propertyno = " + Common.sqlChar(strKeys[2]) +"\n"+
					   " and serialno = " + Common.sqlChar(strKeys[3])+"\n"+
					   "";
	//System.out.println("----"+index+"----"+array[index]);
	
	index++;
	//土地改良物減損單明細檔UNTRF_ReduceDetail
	array[index]="update UNTRF_REDUCEDETAIL set " +"\n"+
					   " propertyno = "+Common.sqlChar(q_newPropertyNo)+ "," +"\n"+
					   " serialno = "+Common.sqlChar(newSerialNo)+ "," +"\n"+
					   " oldpropertyno = "+Common.sqlChar(strKeys[2])+ "," +"\n"+
					   " oldserialno = "+Common.sqlChar(strKeys[3])+"\n"+
					" where 1=1 " +"\n"+
					   " and enterorg = " + Common.sqlChar(strKeys[0]) +"\n"+
					   " and ownership = " + Common.sqlChar(strKeys[1]) +"\n"+
				       " and propertyno = " + Common.sqlChar(strKeys[2]) +"\n"+
					   " and serialno = " + Common.sqlChar(strKeys[3])+"\n"+
					   "";
	//System.out.println("----"+index+"----"+array[index]);
	
	
	/*
	 * 更新「財產編號、財產分號」資料
	 * */
	index++;
	//土地改良物基地檔UNTRF_Base
	array[index]="update UNTRF_BASE set " +"\n"+
					   " propertyno = "+Common.sqlChar(q_newPropertyNo)+ "," +"\n"+
					   " serialno = "+Common.sqlChar(newSerialNo)+"\n"+
					" where 1=1 " +"\n"+
					   " and enterorg = " + Common.sqlChar(strKeys[0]) +"\n"+
					   " and ownership = " + Common.sqlChar(strKeys[1]) +"\n"+
				       " and propertyno = " + Common.sqlChar(strKeys[2]) +"\n"+
					   " and serialno = " + Common.sqlChar(strKeys[3])+"\n"+
					   "";
	//System.out.println("----"+index+"----"+array[index]);
	
//	index++;
//	//土地改良物附屬設備檔UNTRF_Attachment2
//	array[index]="update UNTRF_ATTACHMENT2 set " +"\n"+
//					   " propertyno = "+Common.sqlChar(q_newPropertyNo)+ "," +"\n"+
//					   " serialno = "+Common.sqlChar(newSerialNo)+"\n"+
//					" where 1=1 " +"\n"+
//					   " and enterorg = " + Common.sqlChar(strKeys[0]) +"\n"+
//					   " and ownership = " + Common.sqlChar(strKeys[1]) +"\n"+
//				       " and propertyno = " + Common.sqlChar(strKeys[2]) +"\n"+
//					   " and serialno = " + Common.sqlChar(strKeys[3])+"\n"+
//					   "";
	//System.out.println("----"+index+"----"+array[index]);
	
//	index++;
//	//土地改良物現場勘查檔UNTRF_ViewScene
//	array[index]="update UNTRF_VIEWSCENE set " +"\n"+
//					   " propertyno = "+Common.sqlChar(q_newPropertyNo)+ "," +"\n"+
//					   " serialno = "+Common.sqlChar(newSerialNo)+"\n"+
//					" where 1=1 " +"\n"+
//					   " and enterorg = " + Common.sqlChar(strKeys[0]) +"\n"+
//					   " and ownership = " + Common.sqlChar(strKeys[1]) +"\n"+
//				       " and propertyno = " + Common.sqlChar(strKeys[2]) +"\n"+
//					   " and serialno = " + Common.sqlChar(strKeys[3])+"\n"+
//					   "";
	//System.out.println("----"+index+"----"+array[index]);
	
/*	index++;
	//土地改良物管理檔UNTRF_Manage
	array[index]="update UNTRF_Manage x set " +"\n"+
							   " x.propertyNo = "+Common.sqlChar(q_newPropertyNo)+ "," +"\n"+
							   " x.serialNo = "+Common.sqlChar(newSerialNo)+"\n"+
							" where 1=1 " +"\n"+
							   " and x.enterOrg = " + Common.sqlChar(strKeys[0]) +"\n"+
							   " and x.ownership = " + Common.sqlChar(strKeys[1]) +"\n"+
						       " and x.propertyNo = " + Common.sqlChar(strKeys[2]) +"\n"+
							   " and x.serialNo = " + Common.sqlChar(strKeys[3])+"\n"+
							   "";
	//System.out.println("----"+index+"----"+array[index]);
*/
	
	//========== 將異動資料紀錄至異動檔「財產編號修正記錄UNTDU_PropertyNo」 =======================//
	index++;
	array[index] = "insert into UNTDU_PROPERTYNO ( " +
							   "propertytype,"+
							   "enterorg,"+
							   "ownership,"+
							   "oldpropertyno1,"+
							   "oldserialno1,"+
							   "oldlotno,"+
							   "oldserialnos1,"+
							   "oldserialnoe1,"+ 							   
							   "newpropertyno,"+
							   "newserialno,"+
							   "newlotno,"+
							   "newserialnos,"+
							   "newserialnoe,"+
							   "editid,"+
							   "editdate,"+
							   "edittime"+
							" ) values ( " +
								Common.sqlChar("2") + "," +
								Common.sqlChar(strKeys[0]) + "," +
								Common.sqlChar(strKeys[1]) + "," +
								Common.sqlChar(strKeys[2]) + "," +
								Common.sqlChar(strKeys[3]) + "," +
								Common.sqlChar("") + "," +
								Common.sqlChar("") + "," +
								Common.sqlChar("") + "," +								
								Common.sqlChar(q_newPropertyNo) + "," +
								Common.sqlChar(newSerialNo) + "," +								
								Common.sqlChar("") + "," +
								Common.sqlChar("") + "," +
								Common.sqlChar("") + "," +
								Common.sqlChar(getUserID()) + "," +
								Common.sqlChar(util.Datetime.getYYYMMDD()) + "," +
								Common.sqlChar(util.Datetime.getHHMMSS()) + ")" ;
	
	sql_array_index = index ;
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
	try {
		String sql=" select a.enterorg, " +"\n"+
				   " ( select x.organaname from SYSCA_ORGAN x where x.organid = a.enterorg ) as enterOrgName, " +"\n"+
				   " ( select z.codename from SYSCA_CODE z where 1=1 and (z.codekindid='owa' or z.codekindid='OWA') and z.codeid=a.ownership ) as  ownershipName, " +
				   " a.ownership, a.propertyno, a.serialno, " +"\n"+
				   " (select x.propertyname from SYSPK_PROPERTYCODE x where x.enterorg in ('000000000A',"+Common.sqlChar(getQ_enterOrg())+") and x.propertytype='1' and x.propertyno = a.propertyno) as propertyNoName " +"\n"+
			       " from UNTRF_ATTACHMENT a where 1=1 "+"\n"+
				   " and a.propertykind != '04' " +
			       " ";
		
			       // 查詢後的資料需排除「舊系統財產編號」合乎規定的資料；1:合乎規定,0:不合乎規定
			       //" and (select count(*) from SYSPK_PropertyCode x where x.enterOrg in ('000000000A') and x.propertyType='1' and substr(x.propertyno,1,3)='111' and x.propertyno = a.propertyno) = 0 "+"\n";
		
			if (!"".equals(getQ_enterOrg()))
				sql+=" and a.enterorg = " + Common.sqlChar(getQ_enterOrg()) ;
			if (!"".equals(getQ_propertyNo()))
				sql+=" and a.propertyno = " + Common.sqlChar(getQ_propertyNo()) ;
//			if (!"".equals(getQ_serialNo()))
//				sql+=" and a.serialNo = " + Common.sqlChar(Common.formatFrontZero(getQ_serialNo(),7)) ;
			sql+=" order by enterorg, ownership, propertyno, a.serialno";	
//System.out.println("-- untdu102f queryAll --\n"+sql);

		ResultSet rs = db.querySQL(sql,true);
		processCurrentPageAttribute(rs);
		if (getTotalRecord() > 0) {
			int count = getRecordStart();
			int end = getRecordEnd();
			do {
				if (count > end)
					break;
				String rowArray[]=new String[7];
				rowArray[0]=Common.get(rs.getString("enterOrg")); 
				rowArray[1]=Common.get(rs.getString("enterOrgName")); 
				rowArray[2]=Common.get(rs.getString("ownershipName")); 
				rowArray[3]=Common.get(rs.getString("ownership")); 
				rowArray[4]=Common.get(rs.getString("propertyNo")); 
				rowArray[5]=Common.get(rs.getString("serialNo")); 
				rowArray[6]=Common.get(rs.getString("propertyNoName")); 
				count++;
				objList.add(rowArray);				
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

/**
	 * <br> 
	 * <br>目的：組合查詢列表之html
	 * <br>參數：(1)主鍵之index (2)顯示之index (3)列表集合 (4)是否有查詢旗標
	 * <br>傳回：傳回列表之html字串
	*/
public String getQuerylist(boolean primaryArray[], boolean displayArray[], ArrayList objList, String queryAllFlag) {
	int i;
	int counter=0;
	boolean trFlag = false;
	StringBuffer rtnStr = new StringBuffer();    
	StringBuffer sbQueryOne = new StringBuffer("");
	    	
	if ("true".equals(queryAllFlag) && objList.size()==0){
		rtnStr.append(" <tr class='listTR' ><td class='listTD' colspan='100' style='color:red'>查無資料，請您重新輸入查詢條件！</td></tr>");
		//rtnStr.append(" <tr class='listTR' ><td class='listTD' colspan='100'>&nbsp;</td></tr>");
	}else{
		String rowArray[]=new String[primaryArray.length];
		String isCheck = "unchecked";
		java.util.Iterator it=objList.iterator();
		while(it.hasNext()){			
			rowArray= (String[])it.next();
			counter++;
			//顯示TR
			rtnStr.append(" <tr class='listTR' onmouseover=\"this.className='listTRMouseover'\" onmouseout=\"this.className='listTR'\" onClick=\"queryOne(");
			
			for(i=0;i<primaryArray.length;i++){
				if (primaryArray[i]){
					if(trFlag){
						rtnStr.append(",'").append(rowArray[i]).append("'");
					}else{
						rtnStr.append("'").append(rowArray[i]).append("'");
						trFlag = true;
					}						
				}
			}

			rtnStr.append(")\" >\n");

			//顯示TD
			rtnStr.append(" <td class='listTD' >").append(counter).append(".</td>\n");
			rtnStr.append(" <td class='listTD' ><input type='checkbox' class='field_Q' name='sKeySet' value=\""+rowArray[0]+","+rowArray[3]+","+rowArray[4]+","+rowArray[5]+"\" onclick=\"Toggle(this, document.form1, 'sKeySet');\"" + isCheck + "></td>\n");
			for(i=0;i<displayArray.length;i++){
				if (displayArray[i]){
					rtnStr.append(" <td class='listTD' >").append(Common.get(rowArray[i])).append("</td>\n");
				}
			}				
			rtnStr.append("</tr>\n");
			trFlag = false;
		}
	}				
	return rtnStr.append(sbQueryOne).toString();     
}    


}
