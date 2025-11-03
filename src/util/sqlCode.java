/*
*<br>程式目的：匯總報扛函數
*<br>程式代號：sqlCode
*<br>撰寫日期：97/7/31
*<br>程式作者：shan
*<br>--------------------------------------------------------
*<br>修改作者　　修改日期　　　修改目的
*<br>使用程式
*<br>pubgr027r:國有財產增減結存表
*<br>--------------------------------------------------------
*<br>
*/

package util;

import java.sql.ResultSet;

public class sqlCode {
	
	/**
	*取得某資料庫查詢!!單一位位值
	*sql_code：sql查詢字串　getFile：回傳欄位名稱
	**/
	static public String query_value(String sql_code ,String getFile){
		String reValue="";
		Database db = new Database();
		ResultSet rs;
		try{
			rs = db.querySQL(sql_code);
			if(rs.next()){
				reValue=rs.getString(getFile);
			}
			rs.getStatement().close();
		} catch (Exception e) {
				e.printStackTrace();
				System.out.println("DB-CODE:"+sql_code);
		} finally {
				db.closeAll();
		}
		if("".equals(reValue) || reValue == null ){
			reValue = "0";
		}
			return reValue;
	}
	
	//土地本期結存==============================
  	/**
  	 * <br>
   	 * <br>目的：土地-本期結存-數量
   	 * <br>參數：權屬 ,日期起 ,日期訖 ,珍貴財產 ,財產性質
     * <br>傳回：sqlcode
  	*/
	static public String la_end_Anount(String own ,String dateE ,String valuable ,String propertykind){
		String sql = " select count(*) as la_end_Anount " + "\n"
				   + " from untla_land a ,sysca_organ c  " + "\n"
				   + " where 1=1 " + "\n"
				   + " and a.enterorg = c.organid " + "\n"
				   + " and a.enterorg not like '3839999%' " + "\n"
				   + " and a.enterorg!='383145400I' " + "\n"
				   + " and a.verify='Y' " + "\n"

				   + " and a.ownership = " + Common.sqlChar(own) + "\n"
				   + " and ( a.reducedate is null or a.reducedate > " + Common.sqlChar(dateE) + " ) " + "\n"
				   + " and a.enterdate <= " + Common.sqlChar(dateE) + "\n"
				   + " and decode( " + Common.sqlChar(valuable) + " ,'99' ,'99' ,a.valuable ) = " + Common.sqlChar(valuable) + "\n"
				   + " and decode( " + Common.sqlChar(propertykind) + " ,'99' ,'99' ,a.propertykind ) = " + Common.sqlChar(propertykind) + "\n" 

				   + " group by a.propertykind " + "\n"
				   + " ";
		
		//System.out.println(sql);
		return query_value(sql ,"la_end_Anount");
	}
	
  	/**
  	 * <br>
   	 * <br>目的：土地-本期結存-面積
   	 * <br>參數：權屬 ,日期起 ,日期訖 ,珍貴財產 ,財產性質
     * <br>傳回：sqlcode
  	*/
	static public String la_end_Area(String own ,String dateE ,String valuable ,String propertykind){
		String sql = " select " + "\n"
		
				   + " sum( " + "\n"
				   + "     decode( " + "\n"
				   + "           sign(( select count(*) from UNTLA_AdjustDetail c " + "\n"
				   + "                  where c.enterorg=a.enterorg  " + "\n"
				   + "                  and c.ownership=a.ownership  " + "\n"
				   + "                  and c.propertyno = a.propertyno  " + "\n"
				   + "                  and c.serialno = a.serialno " + "\n"
				   + "                  and c.verify = 'Y' " + "\n"
				   + "                  and c.adjustdate > " + Common.sqlChar(dateE) + "\n"
				   + "               )) " + "\n"
				   + "           ,0 ,decode( a.datastate " + "\n"
				   + "                       ,'1' ,a.holdarea " + "\n"
				   + "                       ,'2' ,( select b.holdarea from untla_ReduceDetail b " + "\n"
				   + "                               where b.enterorg=a.enterorg  " + "\n"
				   + "                               and b.ownership=a.ownership  " + "\n"
				   + "                               and b.propertyno = a.propertyno  " + "\n"
				   + "                               and b.serialno = a.serialno " + "\n"
				   + "                               and b.verify = 'Y'  " + "\n"
				   + "                             ) " + "\n"
				   + " 					) " + "\n"
				   + "           ,1 ,( select d.oldholdarea from UNTLA_AdjustDetail d " + "\n"
				   + "                 where d.enterorg=a.enterorg  " + "\n"
				   + "                 and d.ownership=a.ownership  " + "\n"
				   + "                 and d.propertyno = a.propertyno  " + "\n"
				   + "                 and d.serialno = a.serialno " + "\n"
				   + "                 and d.caseno = ( select min(e.caseno) from UNTLA_AdjustDetail e " + "\n"
				   + "                                  where e.enterorg=a.enterorg  " + "\n"
				   + "                                  and e.ownership=a.ownership  " + "\n"
				   + "                                  and e.propertyno = a.propertyno  " + "\n"
				   + "                                  and e.serialno = a.serialno " + "\n"
				   + "                                  and e.verify = 'Y' " + "\n"
				   + "                                  and e.adjustdate > + " + Common.sqlChar(dateE) + "\n"
				   + "                                ) " + "\n"
				   + "               ) " + "\n"
				   + "           ) " + "\n"
				   + "    )/10000 as la_end_Area " + "\n"

				   + " from untla_land a ,sysca_organ c  " + "\n"
				   + " where 1=1 " + "\n"
				   + " and a.enterorg = c.organid " + "\n"
				   + " and a.enterorg not like '3839999%' " + "\n"
				   + " and a.enterorg!='383145400I' " + "\n"
				   + " and a.verify='Y' " + "\n"

				   + " and a.ownership = " + Common.sqlChar(own) + "\n"
				   + " and ( a.reducedate is null or a.reducedate > " + Common.sqlChar(dateE) + " ) " + "\n"
				   + " and a.enterdate <= " + Common.sqlChar(dateE) + "\n"
				   + " and decode( " + Common.sqlChar(valuable) + " ,'99' ,'99' ,a.valuable ) = " + Common.sqlChar(valuable) + "\n"
				   + " and decode( " + Common.sqlChar(propertykind) + " ,'99' ,'99' ,a.propertykind ) = " + Common.sqlChar(propertykind) + "\n" 

				   + " group by a.propertykind " + "\n"
				   + " ";
		return query_value(sql ,"la_end_Area");
	}
	
  	/**
  	 * <br>
   	 * <br>目的：土地-本期結存-價值
   	 * <br>參數：權屬 ,日期起 ,日期訖 ,珍貴財產 ,財產性質
     * <br>傳回：sqlcode
  	*/
	static public String la_end_Value(String own ,String dateE ,String valuable ,String propertykind){
		String sql = " select " + "\n"
		
				   + " sum( " + "\n"
				   + "     decode( " + "\n"
				   + "           sign(( select count(*) from UNTLA_AdjustDetail c " + "\n"
				   + "                  where c.enterorg=a.enterorg  " + "\n"
				   + "                  and c.ownership=a.ownership  " + "\n"
				   + "                  and c.propertyno = a.propertyno  " + "\n"
				   + "                  and c.serialno = a.serialno " + "\n"
				   + "                  and c.verify = 'Y' " + "\n"
				   + "                  and c.adjustdate > " + Common.sqlChar(dateE) + "\n"
				   + "               )) " + "\n"
				   + "           ,0 ,decode( a.datastate " + "\n"
				   + "                       ,'1' ,a.bookvalue " + "\n"
				   + "                       ,'2' ,( select b.bookvalue from untla_ReduceDetail b " + "\n"
				   + "                               where b.enterorg=a.enterorg  " + "\n"
				   + "                               and b.ownership=a.ownership  " + "\n"
				   + "                               and b.propertyno = a.propertyno  " + "\n"
				   + "                               and b.serialno = a.serialno " + "\n"
				   + "                               and b.verify = 'Y'  " + "\n"
				   + "                             ) " + "\n"
				   + " 					) " + "\n"
				   + "           ,1 ,( select d.oldbookvalue from UNTLA_AdjustDetail d " + "\n"
				   + "                 where d.enterorg=a.enterorg  " + "\n"
				   + "                 and d.ownership=a.ownership  " + "\n"
				   + "                 and d.propertyno = a.propertyno  " + "\n"
				   + "                 and d.serialno = a.serialno " + "\n"
				   + "                 and d.caseno = ( select min(e.caseno) from UNTLA_AdjustDetail e " + "\n"
				   + "                                  where e.enterorg=a.enterorg  " + "\n"
				   + "                                  and e.ownership=a.ownership  " + "\n"
				   + "                                  and e.propertyno = a.propertyno  " + "\n"
				   + "                                  and e.serialno = a.serialno " + "\n"
				   + "                                  and e.verify = 'Y' " + "\n"
				   + "                                  and e.adjustdate > + " + Common.sqlChar(dateE) + "\n"
				   + "                                ) " + "\n"
				   + "               ) " + "\n"
				   + "           ) " + "\n"
				   + "    ) as la_end_value " + "\n"

				   + " from untla_land a ,sysca_organ c  " + "\n"
				   + " where 1=1 " + "\n"
				   + " and a.enterorg = c.organid " + "\n"
				   + " and a.enterorg not like '3839999%' " + "\n"
				   + " and a.enterorg!='383145400I' " + "\n"
				   + " and a.verify='Y' " + "\n"

				   + " and a.ownership = " + Common.sqlChar(own) + "\n"
				   + " and ( a.reducedate is null or a.reducedate > " + Common.sqlChar(dateE) + " ) " + "\n"
				   + " and a.enterdate <= " + Common.sqlChar(dateE) + "\n"
				   + " and decode( " + Common.sqlChar(valuable) + " ,'99' ,'99' ,a.valuable ) = " + Common.sqlChar(valuable) + "\n"
				   + " and decode( " + Common.sqlChar(propertykind) + " ,'99' ,'99' ,a.propertykind ) = " + Common.sqlChar(propertykind) + "\n" 

				   + " group by a.propertykind " + "\n"
				   + " ";
		return query_value(sql ,"la_end_value");
	}
	
	//土地本期增加======================================
  	/**
  	 * <br>
   	 * <br>目的：土地-某期間-增加[數量]
   	 * <br>參數：權屬 ,日期起 ,日期訖 ,珍貴財產 ,財產性質
     * <br>傳回：sqlcode
  	*/
	static public String la_add_Amount(String own ,String dateS ,String dateE ,String valuable ,String propertykind){
		String sql = " select count(*) as la_add_Amount " + "\n"
				   + " from untla_land a ,sysca_organ c  " + "\n"
				   + " where 1=1 " + "\n"
				   + " and a.enterorg = c.organid " + "\n"
				   + " and a.enterorg not like '3839999%' " + "\n"
				   + " and a.enterorg!='383145400I' " + "\n"
				   + " and a.verify='Y' " + "\n"
				   + " and a.ownership = " + Common.sqlChar(own) + "\n"
				   + " and a.enterdate between " + Common.sqlChar(dateS) + " and " + Common.sqlChar(dateE) + "\n" 
				   + " and decode( " + Common.sqlChar(valuable) + " ,'99' ,'99' ,a.valuable ) = " + Common.sqlChar(valuable) + "\n"
				   + " and decode( " + Common.sqlChar(propertykind) + " ,'99' ,'99' ,a.propertykind ) = " + Common.sqlChar(propertykind) + "\n" 
				   + " group by a.propertykind ";
		return query_value(sql ,"la_add_Amount");
	}
	
  	/**
  	 * <br>
   	 * <br>目的：土地-本期增加-面積
   	 * <br>參數：權屬 ,日期起 ,日期訖 ,珍貴財產 ,財產性質
     * <br>傳回：sqlcode
  	*/
	static public String la_add_Area(String own ,String dateS ,String dateE ,String valuable ,String propertykind){
		String sql = " select ( ( " + "\n"
			   	   + " 			select nvl(sum(a.originalholdarea),0) " + "\n"
			   	   + " 			from untla_land a ,sysca_organ c  " + "\n"
			   	   + " 			where 1=1 " + "\n"
			   	   + " 			and a.enterorg = c.organid " + "\n"
			   	   + " 			and a.verify='Y' " + "\n"
			   	   + " 			and a.enterorg not like '3839999%' " + "\n"
			   	   + " 			and a.enterorg!='383145400I' " + "\n"
			   	   + " 			and a.ownership = " + Common.sqlChar(own) + "\n"
			   	   + " 			and a.enterdate between " + Common.sqlChar(dateS) + " and " + Common.sqlChar(dateE) + "\n" 
			   	   + " 			and decode( " + Common.sqlChar(valuable) + " ,'99' ,'99' ,a.valuable ) = " + Common.sqlChar(valuable) + "\n"
			   	   + " 			and decode( " + Common.sqlChar(propertykind) + " ,'99' ,'99' ,a.propertykind ) = " + Common.sqlChar(propertykind) + "\n" 
			   	   + " ) + ( "
				   + "          select nvl(sum(b.adjustholdarea),0) from UNTLA_AdjustDetail b " + "\n"
				   + "          where b.enterorg not like '3839999%' " + "\n"
				   + "          and b.enterorg != '383145400I' " + "\n"
				   + "          and b.verify='Y' " + "\n"
				   + "          and b.adjustdate between " + Common.sqlChar(dateS) + " and " + Common.sqlChar(dateE) + "\n" 
				   + "          and b.ownership = " + Common.sqlChar(own) + "\n"
				   + "          and decode( " + Common.sqlChar(valuable) + " ,'99' ,'99' ,b.valuable ) = " + Common.sqlChar(valuable) + "\n"
				   + "          and b.adjustBookValue >= 0 " + "\n"
				   + "          and decode( " + Common.sqlChar(propertykind) + " ,'99' ,'99' ,b.propertykind ) = " + Common.sqlChar(propertykind) + "\n"
			       + " ) )/10000" 
			       + " as la_add_Area from dual "
			       + "";
		return query_value(sql ,"la_add_area");
	}
	
  	/**
  	 * <br>
   	 * <br>目的：土地-本期增加-價值
   	 * <br>參數：權屬 ,日期起 ,日期訖 ,珍貴財產 ,財產性質
     * <br>傳回：sqlcode
  	*/
	static public String la_add_Value(String own ,String dateS ,String dateE ,String valuable ,String propertykind){
		String sql = " select ( "
				   + " 			select nvl(sum(a.originalbv),0)  " + "\n"
				   + " 			from untla_land a ,sysca_organ c  " + "\n"
				   + " 			where 1=1 " + "\n"
				   + " 			and a.enterorg = c.organid " + "\n"
				   + " 			and a.verify='Y' " + "\n"
				   + " 			and a.enterorg not like '3839999%' " + "\n"
				   + " 			and a.enterorg!='383145400I' " + "\n"
				   + " 			and a.ownership = " + Common.sqlChar(own) + "\n"
				   + " 			and a.enterdate between " + Common.sqlChar(dateS) + " and " + Common.sqlChar(dateE) + "\n" 
				   + " 			and decode( " + Common.sqlChar(valuable) + " ,'99' ,'99' ,a.valuable ) = " + Common.sqlChar(valuable) + "\n"
				   + " 			and decode( " + Common.sqlChar(propertykind) + " ,'99' ,'99' ,a.propertykind ) = " + Common.sqlChar(propertykind) + "\n" 
				   + " ) + ( "
				   + "          select nvl(sum(b.adjustBookValue),0) from UNTLA_AdjustDetail b " + "\n"
				   + "          where b.enterorg not like '3839999%' " + "\n"
				   + "          and b.enterorg != '383145400I' " + "\n"
				   + "          and b.verify='Y' " + "\n"
				   + "          and b.adjustdate between " + Common.sqlChar(dateS) + " and " + Common.sqlChar(dateE) + "\n" 
				   + "          and b.ownership = " + Common.sqlChar(own) + "\n"
				   + "          and decode( " + Common.sqlChar(valuable) + " ,'99' ,'99' ,b.valuable ) = " + Common.sqlChar(valuable) + "\n"
				   + "          and b.adjustBookValue >= 0 " + "\n"
				   + "          and decode( " + Common.sqlChar(propertykind) + " ,'99' ,'99' ,b.propertykind ) = " + Common.sqlChar(propertykind) + "\n"
				   + " ) " 
				   + " as la_add_value from dual "
				   + "";
		
		return query_value(sql ,"la_add_value");
	}
	
	//本期減少===========================
  	/**
  	 * <br>
   	 * <br>目的：土地-本期減少-數量
   	 * <br>參數：權屬 ,日期起 ,日期訖 ,珍貴財產 ,財產性質
     * <br>傳回：sqlcode
  	*/
	static public String la_red_Amount(String own ,String dateS ,String dateE ,String valuable ,String propertykind){
		String sql = " select count(*) as la_red_Amount " + "\n"
				   + " from UNTLA_ReduceDetail a ,sysca_organ c  " + "\n"
				   + " where 1=1 " + "\n"
				   + " and a.enterorg = c.organid " + "\n"
				   + " and a.enterorg not like '3839999%' " + "\n"
				   + " and a.enterorg!='383145400I' " + "\n"
				   + " and a.verify='Y' " + "\n"
				   + " and a.ownership = " + Common.sqlChar(own) + "\n"
				   + " and a.reduceDate between " + Common.sqlChar(dateS) + " and " + Common.sqlChar(dateE) + "\n" 
				   + " and decode( " + Common.sqlChar(valuable) + " ,'99' ,'99' ,a.valuable ) = " + Common.sqlChar(valuable) + "\n"
				   + " and decode( " + Common.sqlChar(propertykind) + " ,'99' ,'99' ,a.propertykind ) = " + Common.sqlChar(propertykind) + "\n" 
				   + " group by a.propertykind " + "\n"
				   + " ";
		return query_value(sql ,"la_red_Amount");
	}

  	/**
  	 * <br>
   	 * <br>目的：土地-本期減少-面積
   	 * <br>參數：權屬 ,日期起 ,日期訖 ,珍貴財產 ,財產性質
     * <br>傳回：sqlcode
  	*/
	static public String la_red_Area(String own ,String dateS ,String dateE ,String valuable ,String propertykind){
		String sql = " select (( "
				   + " 			select nvl(sum(a.holdarea),0) " + "\n"
				   + " 			from UNTLA_ReduceDetail a ,sysca_organ c  " + "\n"
				   + " 			where 1=1 " + "\n"
				   + " 			and a.enterorg = c.organid " + "\n"
				   + " 			and a.enterorg not like '3839999%' " + "\n"
				   + " 			and a.enterorg!='383145400I' " + "\n"
				   + " 			and a.verify='Y' " + "\n"
				   + " 			and a.ownership = " + Common.sqlChar(own) + "\n"
				   + " 			and a.reduceDate between " + Common.sqlChar(dateS) + " and " + Common.sqlChar(dateE) + "\n" 
				   + " 			and decode( " + Common.sqlChar(valuable) + " ,'99' ,'99' ,a.valuable ) = " + Common.sqlChar(valuable) + "\n"
				   + " 			and decode( " + Common.sqlChar(propertykind) + " ,'99' ,'99' ,a.propertykind ) = " + Common.sqlChar(propertykind) + "\n" 
				   + " ) - ( "
				   + "          select nvl(sum(b.adjustholdarea),0) from UNTLA_AdjustDetail b " + "\n"
				   + "          where b.enterorg not like '3839999%' " + "\n"
				   + "          and b.enterorg != '383145400I' " + "\n"
				   + "          and b.verify='Y' " + "\n"
				   + "          and b.adjustholdarea <= 0 " + "\n"
				   + "          and b.adjustdate between " + Common.sqlChar(dateS) + " and " + Common.sqlChar(dateE) + "\n" 
				   + "          and b.ownership = " + Common.sqlChar(own) + "\n"
				   + " 			and decode( " + Common.sqlChar(valuable) + " ,'99' ,'99' ,b.valuable ) = " + Common.sqlChar(valuable) + "\n"
				   + " 			and decode( " + Common.sqlChar(propertykind) + " ,'99' ,'99' ,b.propertykind ) = " + Common.sqlChar(propertykind) + "\n" 
				   + " ))/10000 " 
				   + " as la_red_Area from dual "
				   + "";

		return query_value(sql ,"la_red_Area");
	}
	
  	/**
  	 * <br>
   	 * <br>目的：土地-本期減少-價值
   	 * <br>參數：權屬 ,日期起 ,日期訖 ,珍貴財產 ,財產性質
     * <br>傳回：sqlcode
  	*/
	static public String la_red_Value(String own ,String dateS ,String dateE ,String valuable ,String propertykind){
		String sql = " select ( "
				   + " 			select nvl(sum(a.bookvalue),0) " + "\n"
				   + " 			from UNTLA_ReduceDetail a ,sysca_organ c  " + "\n"
				   + " 			where 1=1 " + "\n"
				   + " 			and a.enterorg = c.organid " + "\n"
				   + " 			and a.enterorg not like '3839999%' " + "\n"
				   + " 			and a.enterorg!='383145400I' " + "\n"
				   + " 			and a.verify='Y' " + "\n"
				   + " 			and a.ownership = " + Common.sqlChar(own) + "\n"
				   + " 			and a.reduceDate between " + Common.sqlChar(dateS) + " and " + Common.sqlChar(dateE) + "\n" 
				   + " 			and decode( " + Common.sqlChar(valuable) + " ,'99' ,'99' ,a.valuable ) = " + Common.sqlChar(valuable) + "\n"
				   + " 			and decode( " + Common.sqlChar(propertykind) + " ,'99' ,'99' ,a.propertykind ) = " + Common.sqlChar(propertykind) + "\n" 
				   + " ) - ( "
				   + "          select nvl(sum(b.adjustBookValue),0) from UNTLA_AdjustDetail b " + "\n"
				   + "          where b.enterorg not like '3839999%' " + "\n"
				   + "          and b.enterorg != '383145400I' " + "\n"
				   + "          and b.verify='Y' " + "\n"
				   + "          and b.adjustBookValue<=0 " + "\n"
				   + "          and b.adjustdate between " + Common.sqlChar(dateS) + " and " + Common.sqlChar(dateE) + "\n"
				   + "          and b.ownership = " + Common.sqlChar(own) + "\n"
				   + " 			and decode( " + Common.sqlChar(valuable) + " ,'99' ,'99' ,b.valuable ) = " + Common.sqlChar(valuable) + "\n"
				   + " 			and decode( " + Common.sqlChar(propertykind) + " ,'99' ,'99' ,b.propertykind ) = " + Common.sqlChar(propertykind) + "\n"
				   + " ) " 
				   + " as la_red_Value from dual "
				   + "";

		return query_value(sql ,"la_red_Value");
	}
	
	//土地改良物
  	/**
  	 * <br>
   	 * <br>目的：土地改良物-本期結存-數量
   	 * <br>參數：權屬 ,日期起 ,日期訖 ,珍貴財產 ,財產性質
     * <br>傳回：sqlcode
  	*/
	static public String rf_end_Amount(String own ,String dateE ,String valuable ,String propertykind){
		String sql = " select count(*) as rf_end_Amount " + "\n"
		   		   + " from untrf_Attachment a ,sysca_organ c  " + "\n"
		   		   + " where 1=1 " + "\n"
		   		   + " and a.enterorg = c.organid " + "\n"
		   		   + " and a.enterorg not like '3839999%' " + "\n"
		   		   + " and a.enterorg!='383145400I' " + "\n"
		   		   + " and a.verify='Y' " + "\n"

		   		   + " and a.ownership = " + Common.sqlChar(own) + "\n"
		   		   + " and ( a.reducedate is null or a.reducedate > " + Common.sqlChar(dateE) + " ) " + "\n"
		   		   + " and a.enterdate <= " + Common.sqlChar(dateE) + "\n"
		   		   + " and decode( " + Common.sqlChar(valuable) + " ,'99' ,'99' ,a.valuable ) = " + Common.sqlChar(valuable) + "\n"
		   		   + " and decode( " + Common.sqlChar(propertykind) + " ,'99' ,'99' ,a.propertykind ) = " + Common.sqlChar(propertykind) + "\n" 

		   		   + " group by a.propertykind " + "\n"
		   		   + " ";
		return query_value(sql ,"rf_end_Amount");
	}
	
  	/**
  	 * <br>
   	 * <br>目的：土地改良物-本期結存-價值
   	 * <br>參數：權屬 ,日期起 ,日期訖 ,珍貴財產 ,財產性質
     * <br>傳回：sqlcode
  	*/
	static public String rf_end_Value(String own ,String dateE ,String valuable ,String propertykind){
		String sql = " select sum( " + "\n"
				   + "      	  decode( sign(( select count(*) from untrf_AdjustDetail c " + "\n"
				   + "                    		 where c.enterorg = a.enterorg and c.ownership = a.ownership " + "\n"
				   + "                    		 and c.propertyno = a.propertyno and c.serialno = a.serialno " + "\n"
				   + "                    		 and c.verify = 'Y' " + "\n"
				   + "                    		 and c.adjustdate <= " + Common.sqlChar(dateE) + "\n"
				   + "                   	  )) " + "\n"
				   + "               	  ,0 ,decode( a.datastate  " + "\n"
				   + "                           	  ,'1' , a.bookvalue " + "\n"
				   + "                           	  ,'2' , ( select d.bookvalue from untrf_ReduceDetail d " + "\n"
				   + "                                    	   where d.enterorg = a.enterorg and d.ownership = a.ownership " + "\n"
				   + "                                    	   and d.propertyno = a.propertyno and d.serialno = a.serialno " + "\n"
				   + "                                    	   and d.verify = 'Y' " + "\n"
				   + "                                  	 ) " + "\n"
				   + "                         		) " + "\n"
				   + "               	  ,1 ,(	select d.oldbookvalue from untrf_AdjustDetail d " + "\n"
				   + "                    	    where d.enterorg=a.enterorg and d.ownership=a.ownership  " + "\n"
				   + "                    		and d.propertyno = a.propertyno and d.serialno = a.serialno " + "\n"
				   + "                    		and d.caseno = ( select min(e.caseno)  " + "\n"
				   + "                                     		 from untrf_AdjustDetail e " + "\n"
				   + "                                   	     where e.enterorg=a.enterorg and e.ownership=a.ownership  " + "\n"
				   + "                                     		 and e.propertyno = a.propertyno and e.serialno = a.serialno " + "\n"
				   + "                                    		 and e.verify = 'Y' and e.adjustdate > " + Common.sqlChar(dateE) + "\n"
				   + "                                  	   ) " + "\n"
				   + "                   ) " + "\n"
				   + "            ) " + "\n"
				   + "     ) " + "\n"
		
		   		   + " from untrf_Attachment a ,sysca_organ c  " + "\n"
		   		   + " where 1=1 " + "\n"
		   		   + " and a.enterorg = c.organid " + "\n"
		   		   + " and a.enterorg not like '3839999%' " + "\n"
		   		   + " and a.enterorg!='383145400I' " + "\n"
		   		   + " and a.verify='Y' " + "\n"

		   		   + " and a.ownership = " + Common.sqlChar(own) + "\n"
		   		   + " and ( a.reducedate is null or a.reducedate > " + Common.sqlChar(dateE) + " ) " + "\n"
		   		   + " and a.enterdate <= " + Common.sqlChar(dateE) + "\n"
		   		   + " and decode( " + Common.sqlChar(valuable) + " ,'99' ,'99' ,a.valuable ) = " + Common.sqlChar(valuable) + "\n"
		   		   + " and decode( " + Common.sqlChar(propertykind) + " ,'99' ,'99' ,a.propertykind ) = " + Common.sqlChar(propertykind) + "\n" 

		   		   + " group by a.propertykind " + "\n"
		   		   + " ";
		return query_value(sql ,"rf_end_Value");
	}
	
	//土地改良物本期增加
  	/**
  	 * <br>
   	 * <br>目的：土地-本期增加-數量
   	 * <br>參數：權屬 ,日期起 ,日期訖 ,珍貴財產 ,財產性質
     * <br>傳回：sqlcode
  	*/
	static public String rf_add_Amount(String own ,String dateS ,String dateE ,String valuable ,String propertykind){
		String sql = " select count(*) as la_add_Amount " + "\n"
				   + " from untrf_Attachment a ,sysca_organ c  " + "\n"
				   + " where 1=1 " + "\n"
				   + " and a.enterorg = c.organid " + "\n"
				   + " and a.enterorg not like '3839999%' " + "\n"
				   + " and a.enterorg!='383145400I' " + "\n"
				   + " and a.verify='Y' " + "\n"
				   
				   + " and a.ownership = " + Common.sqlChar(own) + "\n"
				   + " and a.enterdate between " + Common.sqlChar(dateS) + " and " + Common.sqlChar(dateE) + "\n" 
				   + " and decode( " + Common.sqlChar(valuable) + " ,'99' ,'99' ,a.valuable ) = " + Common.sqlChar(valuable) + "\n"
				   + " and decode( " + Common.sqlChar(propertykind) + " ,'99' ,'99' ,a.propertykind ) = " + Common.sqlChar(propertykind) + "\n" 
				   + " group by a.propertykind ";
		return query_value(sql ,"rf_add_Amount");
	}
	
  	/**
  	 * <br>
   	 * <br>目的：土地-本期增加-價值
   	 * <br>參數：權屬 ,日期起 ,日期訖 ,珍貴財產 ,財產性質
     * <br>傳回：sqlcode
  	*/
	static public String rf_add_Value(String own ,String dateS ,String dateE ,String valuable ,String propertykind){
		String sql = " select ( "
			       + " 			select nvl(sum(a.originalbv),0)  " + "\n"
				   + " 			from untrf_Attachment a ,sysca_organ c  " + "\n"
				   + " 			where 1=1 " + "\n"
				   + " 			and a.enterorg = c.organid " + "\n"
				   + " 			and a.enterorg not like '3839999%' " + "\n"
				   + " 			and a.enterorg!='383145400I' " + "\n"
				   + " 			and a.verify='Y' " + "\n"
				   + " and a.ownership = " + Common.sqlChar(own) + "\n"
				   + " and a.enterdate between " + Common.sqlChar(dateS) + " and " + Common.sqlChar(dateE) + "\n" 
				   + " and decode( " + Common.sqlChar(valuable) + " ,'99' ,'99' ,a.valuable ) = " + Common.sqlChar(valuable) + "\n"
				   + " and decode( " + Common.sqlChar(propertykind) + " ,'99' ,'99' ,a.propertykind ) = " + Common.sqlChar(propertykind) + "\n" 
			   	   + " ) + ( " + "\n"
				   + "          select nvl(sum(b.adjustBookValue),0) from untrf_AdjustDetail b " + "\n"
				   + "          where b.enterorg not like '3839999%' " + "\n"
				   + "          and b.enterorg != '383145400I' " + "\n"
				   + "          and b.verify='Y' " + "\n"
				   + "          and b.adjusttype = '1' " + "\n"
				   + "          and b.adjustdate between '0960101' and '0961231' " + "\n"
				   + "          and b.ownership = " + Common.sqlChar(own) + "\n"
				   + " 			and decode( " + Common.sqlChar(valuable) + " ,'99' ,'99' ,b.valuable ) = " + Common.sqlChar(valuable) + "\n"
				   + " 			and decode( " + Common.sqlChar(propertykind) + " ,'99' ,'99' ,b.propertykind ) = " + Common.sqlChar(propertykind) + "\n" 
			   	   + " ) " 
			   	   + " as rf_add_Value from dual "
			   	   + "";

		return query_value(sql ,"rf_add_Value");
	}

	//土地改良物本期減少
	/**
  	 * <br>
   	 * <br>目的：土地改良物-本期減少-數量
   	 * <br>參數：權屬 ,日期起 ,日期訖 ,珍貴財產 ,財產性質
     * <br>傳回：sqlcode
  	*/
	static public String rf_red_Amount(String own ,String dateS ,String dateE ,String valuable ,String propertykind){
		String sql = " select count(*) as rf_red_Amount " + "\n"
				   + " from UNTRF_ReduceDetail a ,sysca_organ c  " + "\n"
				   + " where 1=1 " + "\n"
				   + " and a.enterorg = c.organid " + "\n"
				   + " and a.enterorg not like '3839999%' " + "\n"
				   + " and a.enterorg!='383145400I' " + "\n"
				   + " and a.verify='Y' " + "\n"
				   
				   + " and a.ownership = " + Common.sqlChar(own) + "\n"
				   + " and a.reduceDate between " + Common.sqlChar(dateS) + " and " + Common.sqlChar(dateE) + "\n" 
				   + " and decode( " + Common.sqlChar(valuable) + " ,'99' ,'99' ,a.valuable ) = " + Common.sqlChar(valuable) + "\n"
				   + " and decode( " + Common.sqlChar(propertykind) + " ,'99' ,'99' ,a.propertykind ) = " + Common.sqlChar(propertykind) + "\n" 

				   + " group by a.propertykind " + "\n"
				   + " ";
		return query_value(sql ,"rf_red_Amount");
	}
	
  	/**
  	 * <br>
   	 * <br>目的：土地-本期減少-價值
   	 * <br>參數：權屬 ,日期起 ,日期訖 ,珍貴財產 ,財產性質
     * <br>傳回：sqlcode
  	*/
	static public String rf_red_Value(String own ,String dateS ,String dateE ,String valuable ,String propertykind){
		String sql = " select ( "
				   + " 			select nvl(sum(a.bookvalue),0) " + "\n"
				   + " 			from UNTRF_ReduceDetail a ,sysca_organ c  " + "\n"
				   + " 			where 1=1 " + "\n"
				   + " 			and a.enterorg = c.organid " + "\n"
				   + " 			and a.enterorg not like '3839999%' " + "\n"
				   + " 			and a.enterorg!='383145400I' " + "\n"
				   + " 			and a.verify='Y' " + "\n"
				   + " 			and a.ownership = " + Common.sqlChar(own) + "\n"
				   + " 			and a.reduceDate between " + Common.sqlChar(dateS) + " and " + Common.sqlChar(dateE) + "\n" 
				   + " 			and decode( " + Common.sqlChar(valuable) + " ,'99' ,'99' ,a.valuable ) = " + Common.sqlChar(valuable) + "\n"
				   + " 			and decode( " + Common.sqlChar(propertykind) + " ,'99' ,'99' ,a.propertykind ) = " + Common.sqlChar(propertykind) + "\n" 
			   	   + " ) + ( " + "\n"
				   + "          select nvl(sum(b.adjustBookValue),0) from untrf_AdjustDetail b " + "\n"
				   + "          where b.enterorg not like '3839999%' " + "\n"
				   + "          and b.enterorg != '383145400I' " + "\n"
				   + "          and b.verify='Y' " + "\n"
				   + "          and b.adjusttype = '2' " + "\n"
				   + "          and b.adjustdate between " + Common.sqlChar(dateS) + " and " + Common.sqlChar(dateE) + "\n"
				   + "          and b.ownership = " + Common.sqlChar(own) + "\n"
				   + " 			and decode( " + Common.sqlChar(valuable) + " ,'99' ,'99' ,b.valuable ) = " + Common.sqlChar(valuable) + "\n"
				   + " 			and decode( " + Common.sqlChar(propertykind) + " ,'99' ,'99' ,b.propertykind ) = " + Common.sqlChar(propertykind) + "\n"
			   	   + " ) " 
			   	   + " as rf_red_Value from dual "
			   	   + "";

		return query_value(sql ,"rf_red_Value");
	}
	
	//建物本結結存
  	/**
  	 * <br>
   	 * <br>目的：建物(辦公房屋)-本期結存-數量
   	 * <br>參數：權屬 ,日期起 ,日期訖 ,珍貴財產 ,財產性質
     * <br>傳回：sqlcode
  	*/
	static public String bu_end_Anount20102(String own ,String dateE ,String valuable ,String propertykind){
		String sql = " select count(*) as bu_end_Anount " + "\n"
				   + " from UNTBU_Building a ,sysca_organ c  " + "\n"
				   + " where 1=1 " + "\n"
				   + " and a.enterorg = c.organid " + "\n"
				   + " and a.enterorg not like '3839999%' " + "\n"
				   + " and a.enterorg!='383145400I' " + "\n"
				   + " and substr(a.propertyno,1,5)between'20102'and'20105' "
				   + " and a.verify='Y' " + "\n"

				   + " and a.ownership = " + Common.sqlChar(own) + "\n"
				   + " and ( a.reducedate is null or a.reducedate > " + Common.sqlChar(dateE) + " ) " + "\n"
				   + " and a.enterdate <= " + Common.sqlChar(dateE) + "\n"
				   + " and decode( " + Common.sqlChar(valuable) + " ,'99' ,'99' ,a.valuable ) = " + Common.sqlChar(valuable) + "\n"
				   + " and decode( " + Common.sqlChar(propertykind) + " ,'99' ,'99' ,a.propertykind ) = " + Common.sqlChar(propertykind) + "\n" 

				   + " group by a.propertykind " + "\n"
				   + " ";
		
		//System.out.println(sql);
		return query_value(sql ,"bu_end_Anount");
	}
	
  	/**
  	 * <br>
   	 * <br>目的：建物(辦公房屋)-本期結存-面積
   	 * <br>參數：權屬 ,日期起 ,日期訖 ,珍貴財產 ,財產性質
     * <br>傳回：sqlcode
  	*/
	static public String bu_end_Area20102(String own ,String dateE ,String valuable ,String propertykind){
		String sql = " select " + "\n"
		
		   		   + " sum( " + "\n"
		   		   + "     decode( " + "\n"
		   		   + "           sign(( select count(*) from UNTBU_AdjustDetail c " + "\n"
		   		   + "                  where c.enterorg=a.enterorg  " + "\n"
		   		   + "                  and c.ownership=a.ownership  " + "\n"
		   		   + "                  and c.propertyno = a.propertyno  " + "\n"
		   		   + "                  and c.serialno = a.serialno " + "\n"
		   		   + "                  and c.verify = 'Y' " + "\n"
		   		   + "                  and c.adjustdate > " + Common.sqlChar(dateE) + "\n"
		   		   + "               )) " + "\n"
		   		   + "           ,0 ,decode( a.datastate " + "\n"
		   		   + "                       ,'1' ,a.holdarea " + "\n"
		   		   + "                       ,'2' ,( select b.holdarea from UNTBU_ReduceDetail b " + "\n"
		   		   + "                               where b.enterorg=a.enterorg  " + "\n"
		   		   + "                               and b.ownership=a.ownership  " + "\n"
		   		   + "                               and b.propertyno = a.propertyno  " + "\n"
		   		   + "                               and b.serialno = a.serialno " + "\n"
		   		   + "                               and b.verify = 'Y'  " + "\n"
		   		   + "                             ) " + "\n"
		   		   + " 					) " + "\n"
		   		   + "           ,1 ,( select d.oldholdarea from UNTBU_AdjustDetail d " + "\n"
		   		   + "                 where d.enterorg=a.enterorg  " + "\n"
		   		   + "                 and d.ownership=a.ownership  " + "\n"
		   		   + "                 and d.propertyno = a.propertyno  " + "\n"
		   		   + "                 and d.serialno = a.serialno " + "\n"
		   		   + "                 and d.caseno = ( select min(e.caseno) from UNTLA_AdjustDetail e " + "\n"
		   		   + "                                  where e.enterorg=a.enterorg  " + "\n"
		   		   + "                                  and e.ownership=a.ownership  " + "\n"
		   		   + "                                  and e.propertyno = a.propertyno  " + "\n"
		   		   + "                                  and e.serialno = a.serialno " + "\n"
		   		   + "                                  and e.verify = 'Y' " + "\n"
		   		   + "                                  and e.adjustdate > + " + Common.sqlChar(dateE) + "\n"
		   		   + "                                ) " + "\n"
		   		   + "               ) " + "\n"
		   		   + "           ) " + "\n"
		   		   + "    ) as bu_end_Area " + "\n"
		
				   + " from UNTBU_Building a ,sysca_organ c  " + "\n"
				   + " where 1=1 " + "\n"
				   + " and a.enterorg = c.organid " + "\n"
				   + " and a.enterorg not like '3839999%' " + "\n"
				   + " and a.enterorg!='383145400I' " + "\n"
				   + " and substr(a.propertyno,1,5)between'20102'and'20105' "
				   + " and a.verify='Y' " + "\n"

				   + " and a.ownership = " + Common.sqlChar(own) + "\n"
				   + " and ( a.reducedate is null or a.reducedate > " + Common.sqlChar(dateE) + " ) " + "\n"
				   + " and a.enterdate <= " + Common.sqlChar(dateE) + "\n"
				   + " and decode( " + Common.sqlChar(valuable) + " ,'99' ,'99' ,a.valuable ) = " + Common.sqlChar(valuable) + "\n"
				   + " and decode( " + Common.sqlChar(propertykind) + " ,'99' ,'99' ,a.propertykind ) = " + Common.sqlChar(propertykind) + "\n" 

				   + " group by a.propertykind " + "\n"
				   + " ";
		
		//System.out.println(sql);
		return query_value(sql ,"bu_end_Area");
	}
	
  	/**
  	 * <br>
   	 * <br>目的：建物(宿舍)-本期結存-數量
   	 * <br>參數：權屬 ,日期起 ,日期訖 ,珍貴財產 ,財產性質
     * <br>傳回：sqlcode
  	*/
	static public String bu_end_Anount20106(String own ,String dateE ,String valuable ,String propertykind){
		String sql = " select count(*) as bu_end_Anount " + "\n"
				   + " from UNTBU_Building a ,sysca_organ c  " + "\n"
				   + " where 1=1 " + "\n"
				   + " and a.enterorg = c.organid " + "\n"
				   + " and a.enterorg not like '3839999%' " + "\n"
				   + " and a.enterorg!='383145400I' " + "\n"
				   + " and substr(a.propertyno,1,5) = '20106' "
				   + " and a.verify='Y' " + "\n"

				   + " and a.ownership = " + Common.sqlChar(own) + "\n"
				   + " and ( a.reducedate is null or a.reducedate > " + Common.sqlChar(dateE) + " ) " + "\n"
				   + " and a.enterdate <= " + Common.sqlChar(dateE) + "\n"
				   + " and decode( " + Common.sqlChar(valuable) + " ,'99' ,'99' ,a.valuable ) = " + Common.sqlChar(valuable) + "\n"
				   + " and decode( " + Common.sqlChar(propertykind) + " ,'99' ,'99' ,a.propertykind ) = " + Common.sqlChar(propertykind) + "\n" 

				   + " group by a.propertykind " + "\n"
				   + " ";
		
		//System.out.println(sql);
		return query_value(sql ,"bu_end_Anount");
	}
	
  	/**
  	 * <br>
   	 * <br>目的：建物(宿舍)-本期結存-面積
   	 * <br>參數：權屬 ,日期起 ,日期訖 ,珍貴財產 ,財產性質
     * <br>傳回：sqlcode
  	*/
	static public String bu_end_Area20106(String own ,String dateE ,String valuable ,String propertykind){
		String sql = " select " + "\n"
		
		   		   + " sum( " + "\n"
		   		   + "     decode( " + "\n"
		   		   + "           sign(( select count(*) from UNTBU_AdjustDetail c " + "\n"
		   		   + "                  where c.enterorg=a.enterorg  " + "\n"
		   		   + "                  and c.ownership=a.ownership  " + "\n"
		   		   + "                  and c.propertyno = a.propertyno  " + "\n"
		   		   + "                  and c.serialno = a.serialno " + "\n"
		   		   + "                  and c.verify = 'Y' " + "\n"
		   		   + "                  and c.adjustdate > " + Common.sqlChar(dateE) + "\n"
		   		   + "               )) " + "\n"
		   		   + "           ,0 ,decode( a.datastate " + "\n"
		   		   + "                       ,'1' ,a.holdarea " + "\n"
		   		   + "                       ,'2' ,( select b.holdarea from UNTBU_ReduceDetail b " + "\n"
		   		   + "                               where b.enterorg=a.enterorg  " + "\n"
		   		   + "                               and b.ownership=a.ownership  " + "\n"
		   		   + "                               and b.propertyno = a.propertyno  " + "\n"
		   		   + "                               and b.serialno = a.serialno " + "\n"
		   		   + "                               and b.verify = 'Y'  " + "\n"
		   		   + "                             ) " + "\n"
		   		   + " 					) " + "\n"
		   		   + "           ,1 ,( select d.oldholdarea from UNTBU_AdjustDetail d " + "\n"
		   		   + "                 where d.enterorg=a.enterorg  " + "\n"
		   		   + "                 and d.ownership=a.ownership  " + "\n"
		   		   + "                 and d.propertyno = a.propertyno  " + "\n"
		   		   + "                 and d.serialno = a.serialno " + "\n"
		   		   + "                 and d.caseno = ( select min(e.caseno) from UNTLA_AdjustDetail e " + "\n"
		   		   + "                                  where e.enterorg=a.enterorg  " + "\n"
		   		   + "                                  and e.ownership=a.ownership  " + "\n"
		   		   + "                                  and e.propertyno = a.propertyno  " + "\n"
		   		   + "                                  and e.serialno = a.serialno " + "\n"
		   		   + "                                  and e.verify = 'Y' " + "\n"
		   		   + "                                  and e.adjustdate > + " + Common.sqlChar(dateE) + "\n"
		   		   + "                                ) " + "\n"
		   		   + "               ) " + "\n"
		   		   + "           ) " + "\n"
		   		   + "    ) as bu_end_Area " + "\n"
		
				   + " from UNTBU_Building a ,sysca_organ c  " + "\n"
				   + " where 1=1 " + "\n"
				   + " and a.enterorg = c.organid " + "\n"
				   + " and a.enterorg not like '3839999%' " + "\n"
				   + " and a.enterorg!='383145400I' " + "\n"
				   + " and substr(a.propertyno,1,5) = '20106' "
				   + " and a.verify='Y' " + "\n"

				   + " and a.ownership = " + Common.sqlChar(own) + "\n"
				   + " and ( a.reducedate is null or a.reducedate > " + Common.sqlChar(dateE) + " ) " + "\n"
				   + " and a.enterdate <= " + Common.sqlChar(dateE) + "\n"
				   + " and decode( " + Common.sqlChar(valuable) + " ,'99' ,'99' ,a.valuable ) = " + Common.sqlChar(valuable) + "\n"
				   + " and decode( " + Common.sqlChar(propertykind) + " ,'99' ,'99' ,a.propertykind ) = " + Common.sqlChar(propertykind) + "\n" 

				   + " group by a.propertykind " + "\n"
				   + " ";
		
		//System.out.println(sql);
		return query_value(sql ,"bu_end_Area");
	}
	
  	/**
  	 * <br>
   	 * <br>目的：建物(其他)-本期結存-數量
   	 * <br>參數：權屬 ,日期起 ,日期訖 ,珍貴財產 ,財產性質
     * <br>傳回：sqlcode
  	*/
	static public String bu_end_AnountOther(String own ,String dateE ,String valuable ,String propertykind){
		String sql = " select count(*) as bu_end_AnountOther " + "\n"
				   + " from UNTBU_Building a ,sysca_organ c  " + "\n"
				   + " where 1=1 " + "\n"
				   + " and a.enterorg = c.organid " + "\n"
				   + " and a.enterorg not like '3839999%' " + "\n"
				   + " and a.enterorg!='383145400I' " + "\n"
				   
				   + " and a.verify='Y' " + "\n"

				   + " and a.ownership = " + Common.sqlChar(own) + "\n"
				   + " and ( a.reducedate is null or a.reducedate > " + Common.sqlChar(dateE) + " ) " + "\n"
				   + " and a.enterdate <= " + Common.sqlChar(dateE) + "\n"
				   + " and decode( " + Common.sqlChar(valuable) + " ,'99' ,'99' ,a.valuable ) = " + Common.sqlChar(valuable) + "\n"
				   + " and decode( " + Common.sqlChar(propertykind) + " ,'99' ,'99' ,a.propertykind ) = " + Common.sqlChar(propertykind) + "\n" 
				   + " ";
		if( "01".equals(propertykind) ){
			sql += " and not substr(a.propertyno,1,5)between'20102'and'20106' " ;
		}
		
		//System.out.println(sql);
		return query_value(sql ,"bu_end_AnountOther");
	}
	
  	/**
  	 * <br>
   	 * <br>目的：建物-本期結存-價值
   	 * <br>參數：權屬 ,日期起 ,日期訖 ,珍貴財產 ,財產性質
     * <br>傳回：sqlcode
  	*/
	static public String bu_end_Value(String own ,String dateE ,String valuable ,String propertykind){
		String sql = "  select sum(nvl( m.oldbookvalue ,nvl( r.bookvalue ,nvl(a.bookvalue ,0 )))) as bu_end_Value " + "\n"
				   + "  from UNTBU_Building a ,sysca_organ c " + "\n"
		
				   + "       ,( select mb.enterorg ,mb.ownership ,mb.caseno ,mb.propertyno ,mb.serialno ,mb.oldbookvalue " + "\n"
				   + "          from untbu_adjustproof ma ,untbu_adjustdetail mb " + "\n"
				   + "          where 1=1 " + "\n"
				   + "          and ma.enterorg = mb.enterorg and ma.ownership = mb.ownership and ma.caseno = mb.caseno " + "\n"
				   + "          and ma.verify = 'Y' " + "\n"
				   + "          and ma.caseno = ( select min(md.caseno) from untbu_adjustdetail md " + "\n"
				   + "                            where md.enterorg = mb.enterorg and md.ownership = mb.ownership  " + "\n"
				   + "                            and md.propertyno = mb.propertyno and md.serialno = mb.serialno " + "\n"
				   + "                            and ma.adjustdate > + " + Common.sqlChar(dateE) + "\n"
				   + "                          ) " + "\n"
				   + "        ) m " + "\n"
		
				   + "       ,( select rb.enterorg ,rb.ownership ,rb.caseno ,rb.propertyno ,rb.serialno ,rb.bookvalue " + "\n"
				   + "          from untbu_reduceproof ra ,untbu_reducedetail rb " + "\n"
				   + "          where 1=1 " + "\n"
				   + "          and ra.enterorg = rb.enterorg and ra.ownership =  rb.ownership and ra.caseno = rb.caseno " + "\n"
				   + "          and ra.verify = 'Y' " + "\n"
				   + "        ) r " + "\n"
		
				   + "  where 1=1  " + "\n"
				   + "  and a.enterorg = c.organid  " + "\n"
				   + "  and a.enterorg = m.enterorg(+) and a.ownership = m.ownership(+)  and a.propertyno = m.propertyno(+)  and a.serialno = m.serialno(+)  " + "\n"
				   + "  and a.enterorg = r.enterorg(+)  and a.ownership = r.ownership(+)  and a.propertyno = r.propertyno(+)  and a.serialno = r.serialno(+)  " + "\n"
				   + "  and a.enterorg not like '3839999%'  " + "\n"
				   + "  and a.enterorg!='383145400I'  " + "\n"
		
				   + "  and a.verify='Y'  " + "\n"
		
				   + " and a.ownership = " + Common.sqlChar(own) + "\n"
				   + " and ( a.reducedate is null or a.reducedate > " + Common.sqlChar(dateE) + " ) " + "\n"
				   + " and a.enterdate <= " + Common.sqlChar(dateE) + "\n"
				   + " and decode( " + Common.sqlChar(valuable) + " ,'99' ,'99' ,a.valuable ) = " + Common.sqlChar(valuable) + "\n"
				   + " and decode( " + Common.sqlChar(propertykind) + " ,'99' ,'99' ,a.propertykind ) = " + Common.sqlChar(propertykind) + "\n"
				   + " ";

		//System.out.println(sql);
		return query_value(sql ,"bu_end_Value");
	}
	
	//建物本期增加
  	/**
  	 * <br>
   	 * <br>目的：建物(辦公用房屋)-本期增加-數量
   	 * <br>參數：權屬 ,日期起 ,日期訖 ,珍貴財產 ,財產性質
     * <br>傳回：sqlcode
  	*/
	static public String bu_add_Anount20102(String own ,String dateS ,String dateE ,String valuable ,String propertykind){
		String sql = " select count(*) as bu_add_Anount20102 " + "\n"
		   + " from UNTBU_Building a ,sysca_organ c  " + "\n"
		   + " where 1=1 " + "\n"
		   + " and a.enterorg = c.organid " + "\n"
		   + " and a.enterorg not like '3839999%' " + "\n"
		   + " and a.enterorg!='383145400I' " + "\n"
		   + " and a.verify='Y' " + "\n"
		   + " and substr(a.propertyno,1,5)between'20102'and'20105' "
		   
		   + " and a.ownership = " + Common.sqlChar(own) + "\n"
		   + " and a.enterdate between " + Common.sqlChar(dateS) + " and " + Common.sqlChar(dateE) + "\n" 
		   + " and decode( " + Common.sqlChar(valuable) + " ,'99' ,'99' ,a.valuable ) = " + Common.sqlChar(valuable) + "\n"
		   + " and decode( " + Common.sqlChar(propertykind) + " ,'99' ,'99' ,a.propertykind ) = " + Common.sqlChar(propertykind) + "\n" 
		   + " group by a.propertykind ";
		
		return query_value(sql ,"bu_add_Anount20102");
	}
	
  	/**
  	 * <br>
   	 * <br>目的：建物(宿舍)-本期增加-數量
   	 * <br>參數：權屬 ,日期起 ,日期訖 ,珍貴財產 ,財產性質
     * <br>傳回：sqlcode
  	*/
	static public String bu_add_Anount20106(String own ,String dateS ,String dateE ,String valuable ,String propertykind){
		String sql = " select count(*) as bu_add_Anount20106 " + "\n"
		   + " from UNTBU_Building a ,sysca_organ c  " + "\n"
		   + " where 1=1 " + "\n"
		   + " and a.enterorg = c.organid " + "\n"
		   + " and a.enterorg not like '3839999%' " + "\n"
		   + " and a.enterorg!='383145400I' " + "\n"
		   + " and a.verify='Y' " + "\n"
		   + " and substr(a.propertyno,1,5) = '20106' "
		   
		   + " and a.ownership = " + Common.sqlChar(own) + "\n"
		   + " and a.enterdate between " + Common.sqlChar(dateS) + " and " + Common.sqlChar(dateE) + "\n" 
		   + " and decode( " + Common.sqlChar(valuable) + " ,'99' ,'99' ,a.valuable ) = " + Common.sqlChar(valuable) + "\n"
		   + " and decode( " + Common.sqlChar(propertykind) + " ,'99' ,'99' ,a.propertykind ) = " + Common.sqlChar(propertykind) + "\n" 
		   + " group by a.propertykind ";
		
		return query_value(sql ,"bu_add_Anount20106");
	}
	
  	/**
  	 * <br>
   	 * <br>目的：建物(其他)-本期增加-數量
   	 * <br>參數：權屬 ,日期起 ,日期訖 ,珍貴財產 ,財產性質
     * <br>傳回：sqlcode
  	*/
	static public String bu_add_AnountOther(String own ,String dateS ,String dateE ,String valuable ,String propertykind){
		String sql = " select count(*) as bu_add_AnountOther " + "\n"
		   + " from UNTBU_Building a ,sysca_organ c  " + "\n"
		   + " where 1=1 " + "\n"
		   + " and a.enterorg = c.organid " + "\n"
		   + " and a.enterorg not like '3839999%' " + "\n"
		   + " and a.enterorg!='383145400I' " + "\n"
		   + " and a.verify='Y' " + "\n"
		   //+ " and not substr(a.propertyno,1,5) between'20102'and'20106' "
		   
		   + " and a.ownership = " + Common.sqlChar(own) + "\n"
		   + " and a.enterdate between " + Common.sqlChar(dateS) + " and " + Common.sqlChar(dateE) + "\n" 
		   + " and decode( " + Common.sqlChar(valuable) + " ,'99' ,'99' ,a.valuable ) = " + Common.sqlChar(valuable) + "\n"
		   + " and decode( " + Common.sqlChar(propertykind) + " ,'99' ,'99' ,a.propertykind ) = " + Common.sqlChar(propertykind) + "\n" 
		   + "";

			if( "01".equals(propertykind) ){
				sql += " and not substr(a.propertyno,1,5)between'20102'and'20106' " ;
			}
		//System.out.println(sql);
		return query_value(sql ,"bu_add_AnountOther");
	}
	
  	/**
  	 * <br>
   	 * <br>目的：建物(辦公用房屋)-本期增加-面積
   	 * <br>參數：權屬 ,日期起 ,日期訖 ,珍貴財產 ,財產性質
     * <br>傳回：sqlcode
  	*/
	static public String bu_add_Area20102(String own ,String dateS ,String dateE ,String valuable ,String propertykind){
		String sql = " select ( "
				   + " 			select nvl(sum(a.originalholdarea),0)  " + "\n"
				   + " 			from UNTBU_Building a ,sysca_organ c  " + "\n"
				   + " 			where 1=1 " + "\n"
				   + " 			and a.enterorg = c.organid " + "\n"
				   + " 			and a.verify='Y' " + "\n"
				   + " 			and a.enterorg not like '3839999%' " + "\n"
				   + " 			and a.enterorg!='383145400I' " + "\n"
				   + " 			and substr(a.propertyno,1,5)between'20102'and'20105'"
				   + " and a.ownership = " + Common.sqlChar(own) + "\n"
				   + " and a.enterdate between " + Common.sqlChar(dateS) + " and " + Common.sqlChar(dateE) + "\n" 
				   + " and decode( " + Common.sqlChar(valuable) + " ,'99' ,'99' ,a.valuable ) = " + Common.sqlChar(valuable) + "\n"
				   + " and decode( " + Common.sqlChar(propertykind) + " ,'99' ,'99' ,a.propertykind ) = " + Common.sqlChar(propertykind) + "\n" 
				   + " ) + ( " + "\n"
				   + "          select nvl(sum(b.adjustholdarea),0) from UNTBU_AdjustDetail b " + "\n"
				   + "          where b.enterorg not like '3839999%' " + "\n"
				   + "          and b.enterorg != '383145400I' " + "\n"
				   + "          and b.verify='Y' " + "\n"
				   + "          and substr(b.propertyno,1,5)between'20102'and'20105' "
				   + "          and b.adjustdate between " + Common.sqlChar(dateS) + " and " + Common.sqlChar(dateE) + "\n" 
				   + "          and b.ownership = " + Common.sqlChar(own) + "\n"
				   + "          and decode( " + Common.sqlChar(valuable) + " ,'99' ,'99' ,b.valuable ) = " + Common.sqlChar(valuable) + "\n"
				   + "          and b.adjusttype = '1' " + "\n"
				   + "          and decode( " + Common.sqlChar(propertykind) + " ,'99' ,'99' ,b.propertykind ) = " + Common.sqlChar(propertykind) + "\n"
				   + " ) " 
				   + " as bu_add_Area20102 from dual "
				   + "";

		return query_value(sql ,"bu_add_Area20102");
	}
	
  	/**
  	 * <br>
   	 * <br>目的：建物(宿舍)-本期增加-面積
   	 * <br>參數：權屬 ,日期起 ,日期訖 ,珍貴財產 ,財產性質
     * <br>傳回：sqlcode
  	*/
	static public String bu_add_Area20106(String own ,String dateS ,String dateE ,String valuable ,String propertykind){
		String sql = " select ( "
				   + " 			select nvl(sum(a.originalholdarea),0)  " + "\n"
				   + " 			from UNTBU_Building a ,sysca_organ c  " + "\n"
				   + " 			where 1=1 " + "\n"
				   + " 			and a.enterorg = c.organid " + "\n"
				   + " 			and a.verify='Y' " + "\n"
				   + " 			and a.enterorg not like '3839999%' " + "\n"
				   + " 			and a.enterorg!='383145400I' " + "\n"
				   + " 			and substr(a.propertyno,1,5) = '20106'"
				   + " 			and a.ownership = " + Common.sqlChar(own) + "\n"
				   + " 			and a.enterdate between " + Common.sqlChar(dateS) + " and " + Common.sqlChar(dateE) + "\n" 
				   + " 			and decode( " + Common.sqlChar(valuable) + " ,'99' ,'99' ,a.valuable ) = " + Common.sqlChar(valuable) + "\n"
				   + " 			and decode( " + Common.sqlChar(propertykind) + " ,'99' ,'99' ,a.propertykind ) = " + Common.sqlChar(propertykind) + "\n" 
				   + " ) + ( " + "\n"
				   + "          select nvl(sum(b.adjustholdarea),0) from UNTBU_AdjustDetail b " + "\n"
				   + "          where b.enterorg not like '3839999%' " + "\n"
				   + "          and b.enterorg != '383145400I' " + "\n"
				   + "          and b.verify='Y' " + "\n"
				   + "          and substr(b.propertyno,1,5) = '20106' "
				   + "          and b.adjustdate between " + Common.sqlChar(dateS) + " and " + Common.sqlChar(dateE) + "\n" 
				   + "          and b.ownership = " + Common.sqlChar(own) + "\n"
				   + "          and decode( " + Common.sqlChar(valuable) + " ,'99' ,'99' ,b.valuable ) = " + Common.sqlChar(valuable) + "\n"
				   + "          and b.adjusttype = '1' " + "\n"
				   + "          and decode( " + Common.sqlChar(propertykind) + " ,'99' ,'99' ,b.propertykind ) = " + Common.sqlChar(propertykind) + "\n"
				   + " ) " 
				   + " as bu_add_Area20106 from dual "
				   + "";

		//System.out.println(sql);
		return query_value(sql ,"bu_add_Area20106");
	}
	
  	/**
  	 * <br>
   	 * <br>目的：建物-本期增加-價值
   	 * <br>參數：權屬 ,日期起 ,日期訖 ,珍貴財產 ,財產性質
     * <br>傳回：sqlcode
  	*/
	static public String bu_add_Value(String own ,String dateS ,String dateE ,String valuable ,String propertykind){
		String sql = " select ( "
			 	   + " 			select nvl(sum(a.originalbv),0)  " + "\n"
				   + " 			from UNTBU_Building a ,sysca_organ c  " + "\n"
				   + " 			where 1=1 " + "\n"
				   + " 			and a.enterorg = c.organid " + "\n"
				   + " 			and a.verify='Y' " + "\n"
				   + " 			and a.enterorg not like '3839999%' " + "\n"
				   + " 			and a.enterorg!='383145400I' " + "\n"
				   + " 			and a.ownership = " + Common.sqlChar(own) + "\n"
				   + " 			and a.enterdate between " + Common.sqlChar(dateS) + " and " + Common.sqlChar(dateE) + "\n" 
				   + " 			and decode( " + Common.sqlChar(valuable) + " ,'99' ,'99' ,a.valuable ) = " + Common.sqlChar(valuable) + "\n"
				   + " 			and decode( " + Common.sqlChar(propertykind) + " ,'99' ,'99' ,a.propertykind ) = " + Common.sqlChar(propertykind) + "\n" 
				   + " ) + ( " + "\n"
				   + "          select nvl(sum(b.adjustBookValue),0) from UNTBU_AdjustDetail b " + "\n"
				   + "          where b.enterorg not like '3839999%' " + "\n"
				   + "          and b.enterorg != '383145400I' " + "\n"
				   + "          and b.verify='Y' " + "\n"
				   + "          and b.adjustdate between " + Common.sqlChar(dateS) + " and " + Common.sqlChar(dateE) + "\n" 
				   + "          and b.ownership = " + Common.sqlChar(own) + "\n"
				   + "          and decode( " + Common.sqlChar(valuable) + " ,'99' ,'99' ,b.valuable ) = " + Common.sqlChar(valuable) + "\n"
				   + "          and b.adjusttype = '1' " + "\n"
				   + "          and decode( " + Common.sqlChar(propertykind) + " ,'99' ,'99' ,b.propertykind ) = " + Common.sqlChar(propertykind) + "\n"
				   + " ) " 
				   + " as bu_add_Value from dual "
				   + "";

		return query_value(sql ,"bu_add_Value");
	}
	//建物本期減少===========================
  	/**
  	 * <br>
   	 * <br>目的：建物(辦公房屋)-本期減少-數量
   	 * <br>參數：權屬 ,日期起 ,日期訖 ,珍貴財產 ,財產性質
     * <br>傳回：sqlcode
  	*/
	static public String bu_red_Amount20102(String own ,String dateS ,String dateE ,String valuable ,String propertykind){
		String sql = " select count(*) as bu_red_Amount20102 " + "\n"
				   + " from UNTBU_ReduceDetail a ,sysca_organ c  " + "\n"
				   + " where 1=1 " + "\n"
				   + " and a.enterorg = c.organid " + "\n"
				   + " and a.enterorg not like '3839999%' " + "\n"
				   + " and a.enterorg!='383145400I' " + "\n"
				   + " and substr(a.propertyno,1,5) between '20102' and '20105'"
				   + " and a.verify='Y' " + "\n"
				   
				   + " and a.ownership = " + Common.sqlChar(own) + "\n"
				   + " and a.reduceDate between " + Common.sqlChar(dateS) + " and " + Common.sqlChar(dateE) + "\n" 
				   + " and decode( " + Common.sqlChar(valuable) + " ,'99' ,'99' ,a.valuable ) = " + Common.sqlChar(valuable) + "\n"
				   + " and decode( " + Common.sqlChar(propertykind) + " ,'99' ,'99' ,a.propertykind ) = " + Common.sqlChar(propertykind) + "\n" 

				   + " group by a.propertykind " + "\n"
				   + " ";
		return query_value(sql ,"bu_red_Amount20102");
	}
	
  	/**
  	 * <br>
   	 * <br>目的：建物(宿舍)-本期減少-數量
   	 * <br>參數：權屬 ,日期起 ,日期訖 ,珍貴財產 ,財產性質
     * <br>傳回：sqlcode
  	*/
	static public String bu_red_Amount20106(String own ,String dateS ,String dateE ,String valuable ,String propertykind){
		String sql = " select count(*) as bu_red_Amount20106 " + "\n"
				   + " from UNTBU_ReduceDetail a ,sysca_organ c  " + "\n"
				   + " where 1=1 " + "\n"
				   + " and a.enterorg = c.organid " + "\n"
				   + " and a.enterorg not like '3839999%' " + "\n"
				   + " and a.enterorg!='383145400I' " + "\n"
				   + " and substr(a.propertyno,1,5) = '20106' "
				   + " and a.verify='Y' " + "\n"
				   
				   + " and a.ownership = " + Common.sqlChar(own) + "\n"
				   + " and a.reduceDate between " + Common.sqlChar(dateS) + " and " + Common.sqlChar(dateE) + "\n" 
				   + " and decode( " + Common.sqlChar(valuable) + " ,'99' ,'99' ,a.valuable ) = " + Common.sqlChar(valuable) + "\n"
				   + " and decode( " + Common.sqlChar(propertykind) + " ,'99' ,'99' ,a.propertykind ) = " + Common.sqlChar(propertykind) + "\n" 

				   + " group by a.propertykind " + "\n"
				   + " ";
		return query_value(sql ,"bu_red_Amount20106");
	}
	
  	/**
  	 * <br>
   	 * <br>目的：建物(其他)-本期減少-數量
   	 * <br>參數：權屬 ,日期起 ,日期訖 ,珍貴財產 ,財產性質
     * <br>傳回：sqlcode
  	*/
	static public String bu_red_AmountOther(String own ,String dateS ,String dateE ,String valuable ,String propertykind){
		String sql = " select count(*) as bu_red_AmountOther " + "\n"
				   + " from UNTBU_ReduceDetail a ,sysca_organ c  " + "\n"
				   + " where 1=1 " + "\n"
				   + " and a.enterorg = c.organid " + "\n"
				   + " and a.enterorg not like '3839999%' " + "\n"
				   + " and a.enterorg!='383145400I' " + "\n"
				   //+ " and not substr(a.propertyno,1,5) between '20102' and '20106'"
				   + " and a.verify='Y' " + "\n"
				   + " and a.ownership = " + Common.sqlChar(own) + "\n"
				   + " and a.reduceDate between " + Common.sqlChar(dateS) + " and " + Common.sqlChar(dateE) + "\n" 
				   + " and decode( " + Common.sqlChar(valuable) + " ,'99' ,'99' ,a.valuable ) = " + Common.sqlChar(valuable) + "\n"
				   + " and decode( " + Common.sqlChar(propertykind) + " ,'99' ,'99' ,a.propertykind ) = " + Common.sqlChar(propertykind) + "\n" 

				   + " ";
		
			if( "01".equals(propertykind) ){
				sql += " and not substr(a.propertyno,1,5)between'20102'and'20106' " ;
			}
		return query_value(sql ,"bu_red_AmountOther");
	}

  	/**
  	 * <br>
   	 * <br>目的：建物(辦公室)-本期減少-面積
   	 * <br>參數：權屬 ,日期起 ,日期訖 ,珍貴財產 ,財產性質
     * <br>傳回：sqlcode
  	*/
	static public String bu_red_Area20102(String own ,String dateS ,String dateE ,String valuable ,String propertykind){
		String sql = " select ( "
				   + " 			select nvl(sum(a.holdarea),0) " + "\n"
				   + " 			from UNTBU_ReduceDetail a ,sysca_organ c  " + "\n"
				   + " 			where 1=1 " + "\n"
				   + " 			and a.enterorg = c.organid " + "\n"
				   + " 			and a.enterorg not like '3839999%' " + "\n"
				   + " 			and a.enterorg!='383145400I' " + "\n"
				   + " 			and a.verify='Y' " + "\n"
				   + " 			and substr(a.propertyno,1,5) between '20102' and '20105'" + "\n"
				   + " 			and a.ownership = " + Common.sqlChar(own) + "\n"
				   + " 			and a.reduceDate between " + Common.sqlChar(dateS) + " and " + Common.sqlChar(dateE) + "\n" 
				   + " 			and decode( " + Common.sqlChar(valuable) + " ,'99' ,'99' ,a.valuable ) = " + Common.sqlChar(valuable) + "\n"
				   + " 			and decode( " + Common.sqlChar(propertykind) + " ,'99' ,'99' ,a.propertykind ) = " + Common.sqlChar(propertykind) + "\n" 
				   + " ) + ( " + "\n"
				   + "          select nvl(sum(b.adjustholdarea),0) from UNTBU_AdjustDetail b " + "\n"
				   + "          where b.enterorg not like '3839999%' " + "\n"
				   + "          and b.enterorg != '383145400I' " + "\n"
				   + "          and b.verify='Y' " + "\n"
				   + "          and substr(b.propertyno,1,5) between '20102' and '20105'" + "\n"
				   + "          and b.adjusttype = '2' " + "\n"
				   + "          and b.adjustdate between " + Common.sqlChar(dateS) + " and " + Common.sqlChar(dateE) + "\n" 
				   + "          and b.ownership = " + Common.sqlChar(own) + "\n"
				   + " 			and decode( " + Common.sqlChar(valuable) + " ,'99' ,'99' ,b.valuable ) = " + Common.sqlChar(valuable) + "\n"
				   + " 			and decode( " + Common.sqlChar(propertykind) + " ,'99' ,'99' ,b.propertykind ) = " + Common.sqlChar(propertykind) + "\n" 
				   + " ) " 
				   + " as bu_red_Area20102 from dual "
				   + "";

		return query_value(sql ,"bu_red_Area20102");
	}
	
  	/**
  	 * <br>
   	 * <br>目的：建物(宿舍)-本期減少-面積
   	 * <br>參數：權屬 ,日期起 ,日期訖 ,珍貴財產 ,財產性質
     * <br>傳回：sqlcode
  	*/
	static public String bu_red_Area20106(String own ,String dateS ,String dateE ,String valuable ,String propertykind){
		String sql = " select ( "
			   	   + " 			select nvl(sum(a.holdarea),0) " + "\n"
				   + " 			from UNTBU_ReduceDetail a ,sysca_organ c  " + "\n"
				   + " 			where 1=1 " + "\n"
				   + " 			and a.enterorg = c.organid " + "\n"
				   + " 			and a.enterorg not like '3839999%' " + "\n"
				   + " 			and a.enterorg!='383145400I' " + "\n"
				   + " 			and a.verify='Y' " + "\n"
				   + " 			and substr(a.propertyno,1,5) = '20106'" + "\n"
				   + " 			and a.ownership = " + Common.sqlChar(own) + "\n"
				   + " 			and a.reduceDate between " + Common.sqlChar(dateS) + " and " + Common.sqlChar(dateE) + "\n" 
				   + " 			and decode( " + Common.sqlChar(valuable) + " ,'99' ,'99' ,a.valuable ) = " + Common.sqlChar(valuable) + "\n"
				   + " 			and decode( " + Common.sqlChar(propertykind) + " ,'99' ,'99' ,a.propertykind ) = " + Common.sqlChar(propertykind) + "\n" 
				   + " ) + ( " + "\n"
				   + "          select nvl(sum(b.adjustholdarea),0) from UNTBU_AdjustDetail b " + "\n"
				   + "          where b.enterorg not like '3839999%' " + "\n"
				   + "          and b.enterorg != '383145400I' " + "\n"
				   + "          and b.verify='Y' " + "\n"
				   + "          and substr(b.propertyno,1,5) = '20106' " + "\n"
				   + "          and b.adjusttype = '2' " + "\n"
				   + "          and b.adjustdate between " + Common.sqlChar(dateS) + " and " + Common.sqlChar(dateE) + "\n" 
				   + "          and b.ownership = " + Common.sqlChar(own) + "\n"
				   + " 			and decode( " + Common.sqlChar(valuable) + " ,'99' ,'99' ,b.valuable ) = " + Common.sqlChar(valuable) + "\n"
				   + " 			and decode( " + Common.sqlChar(propertykind) + " ,'99' ,'99' ,b.propertykind ) = " + Common.sqlChar(propertykind) + "\n" 
				   + " ) " 
				   + " as bu_red_Area20106 from dual "
				   + "";

		return query_value(sql ,"bu_red_Area20106");
	}

  	/**
  	 * <br>
   	 * <br>目的：建物-本期減少-價值
   	 * <br>參數：權屬 ,日期起 ,日期訖 ,珍貴財產 ,財產性質
     * <br>傳回：sqlcode
  	*/
	static public String bu_red_Value(String own ,String dateS ,String dateE ,String valuable ,String propertykind){
		String sql = " select ( " + "\n"
				   + " 			select nvl(sum(a.bookvalue),0) " + "\n"
				   + " 			from UNTBU_ReduceDetail a ,sysca_organ c  " + "\n"
				   + " 			where 1=1 " + "\n"
				   + " 			and a.enterorg = c.organid " + "\n"
				   + " 			and a.enterorg not like '3839999%' " + "\n"
				   + " 			and a.enterorg!='383145400I' " + "\n"
				   + " 			and a.verify='Y' " + "\n"
				   + " 			and a.ownership = " + Common.sqlChar(own) + "\n"
				   + " 			and a.reduceDate between " + Common.sqlChar(dateS) + " and " + Common.sqlChar(dateE) + "\n" 
				   + " 			and decode( " + Common.sqlChar(valuable) + " ,'99' ,'99' ,a.valuable ) = " + Common.sqlChar(valuable) + "\n"
				   + " 			and decode( " + Common.sqlChar(propertykind) + " ,'99' ,'99' ,a.propertykind ) = " + Common.sqlChar(propertykind) + "\n" 
				   + " ) + ( " + "\n"
				   + "          select nvl(sum(b.adjustBookValue),0) from UNTBU_AdjustDetail b " + "\n"
				   + "          where b.enterorg not like '3839999%' " + "\n"
				   + "          and b.enterorg != '383145400I' " + "\n"
				   + "          and b.verify='Y' " + "\n"
				   + "          and b.adjusttype = '2' " + "\n"
				   + "          and b.adjustdate between " + Common.sqlChar(dateS) + " and " + Common.sqlChar(dateE) + "\n"
				   + "          and b.ownership = " + Common.sqlChar(own) + "\n"
				   + " 			and decode( " + Common.sqlChar(valuable) + " ,'99' ,'99' ,b.valuable ) = " + Common.sqlChar(valuable) + "\n"
				   + " 			and decode( " + Common.sqlChar(propertykind) + " ,'99' ,'99' ,b.propertykind ) = " + Common.sqlChar(propertykind) + "\n"
				   + " ) " 
				   + " as bu_red_Value from dual "
				   + "";

		return query_value(sql ,"bu_red_Value");
	}

}