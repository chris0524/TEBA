package service.impl;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.ResultSet;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONObject;

import service.WS02;
import util.Common;
import util.Database;
import util.TCGHCommon;
 
 
public class WS02Impl implements WS02 {

	@Override
	public String Query_list(String enterorg, String useunit, String place1,
			String keepunit, String keeper, String userno, String propertyno,String Chktype,
			String AuthCode) throws Exception  {
		
		Database db = new Database();
        String isSuccess = "";
        String message="";
        String AuthCodeChk="";
        String SqlStr="";
        String tablename="";
		JSONObject jsonobj=new JSONObject();
		JSONArray obj=null;
		
 	    obj = new JSONArray();
		
 	    if(enterorg==null){enterorg="";}
		if(useunit==null){useunit="";}
		if(place1==null){place1="";}
		if(keepunit==null){keepunit="";}
		if(keeper==null){keeper="";}
		if(userno==null){userno="";}
		if(propertyno==null){propertyno="";}
		if(Chktype==null){Chktype="";}
		
		//認證碼
		AuthCodeChk=enterorg+useunit+place1+keepunit+keeper+userno+propertyno;
		System.out.println("AuthCodeChk="+AuthCodeChk);
		AuthCodeChk=getAuthKey(AuthCodeChk);		
		System.out.println("AuthCodeChk="+AuthCodeChk);
		
		if(AuthCodeChk.equals(AuthCode)){
			try
			 {
				if(Chktype.equals("1")){
						SqlStr=" select "+
					  "b.enterorg, b.serialno1, b.barcode, b.ownership,b.differencekind,b.differencekindname,b.propertytype,b.propertytypename, b.closingdate, b.propertyno, b.serialno,"+ 
					  "(select a.propertyname from SYSPK_PROPERTYCODE a where (a.enterorg=b.enterorg or a.enterorg='000000000A') and a.propertyno=b.propertyno ) as propertyname, "+
					  "b.propertyname1, b.nameplate, b.buydate, b.propertyunit,b.fundtype,b.sourcedate,b.oldserialno,b.specification,"+ 
					  "b.bookamount,b.bookunit,b.checkresult,b.oddscause, b.bookvalue, b.sourcedate, b.limityear, b.useyear, b.keepunitname,"+ 
					  "b.keepername,b.scrappednote,b.labelnote,b.movenote,b.propertykind, b.place1name, b.actualamount,b.material, b.notes," +
					  "b.keepunit,b.keeper,b.keepername,b.useunit,b.useunitname,b.oldpropertyno,b.userno,b.usernoname,b.usernote,b.place1,b.place,b.signno," +
					  "b.signnoname,b.doorplate4,b.editid,b.editdate,b.edittime,b.placenote "+  
					  "from UNTPD_CHECKMOVABLE b where 1=1";
				}
				else if(Chktype.equals("2")){
					SqlStr=" select "+
					  "b.enterorg, b.serialno1, b.barcode, b.ownership,b.differencekind,b.differencekindname,b.propertytype,b.propertytypename, b.closingdate, b.propertyno, b.serialno,"+ 
					  "(select a.propertyname from SYSPK_PROPERTYCODE2 a where (a.enterorg=b.enterorg or a.enterorg='000000000A') and a.propertyno=b.propertyno ) as propertyname, "+
					  "b.propertyname1, b.nameplate, b.buydate, b.propertyunit,b.fundtype,b.sourcedate,b.oldserialno,b.specification,"+ 
					  "b.bookamount,b.bookunit,b.checkresult,b.oddscause, b.bookvalue, b.sourcedate, b.limityear, b.useyear, b.keepunitname,"+ 
					  "b.keepername,b.scrappednote,b.labelnote,b.movenote,b.propertykind, b.place1name, b.actualamount,b.material, b.notes," +
					  "b.keepunit,b.keeper,b.keepername,b.useunit,b.useunitname,b.oldpropertyno,b.userno,b.usernoname,b.usernote,b.place1,b.place,'' as signno," +
					  "'' as signnoname,'' as doorplate4,b.editid,b.editdate,b.edittime,b.placenote "+  
					  "from UNTPD_CHECKNONEXP b where 1=1";
				}
				else{
					isSuccess="N";
					message="盤點類型輸入錯誤，1：財產 ; 2：物品";
					jsonobj.put("message",message);
				    jsonobj.put("isSuccess",isSuccess);
					return jsonobj.toString();
				}
			  if(enterorg.length()>0){
				SqlStr=SqlStr+" and b.enterorg = '"+enterorg+"' ";
			  }	
			  if(useunit.length()>0){
				SqlStr=SqlStr+" and b.useunit = '"+useunit+"' ";
			  }
			  if(place1.length()>0){
				SqlStr=SqlStr+" and b.place1 = '"+place1+"' ";
			  }
			  if(keepunit.length()>0){
				SqlStr=SqlStr+" and b.keepunit = '"+keepunit+"' ";
			  }
			  if(keeper.length()>0){
				SqlStr=SqlStr+" and b.keeper = '"+keeper+"' ";
			  }
			  if(userno.length()>0){
				SqlStr=SqlStr+" and b.userno = '"+userno+"' ";
			  }	
			  if(propertyno.length()>0){
				SqlStr=SqlStr+" and b.differencekind = '"+propertyno+"' ";
			  }
			  
			  SqlStr += " order by b.keepername COLLATE Chinese_Taiwan_Stroke_CS_AS ";
			  System.out.println(SqlStr);

			  ResultSet rs = db.querySQL(SqlStr);	
			  if(rs.next()){
					isSuccess="Y";
					message="查詢成功";
					rs.close();
					rs = db.querySQL(SqlStr);
					while(rs.next()){
					HashMap rows=new HashMap();
				 	rows.put("enterorg",checkGet(rs.getString("enterorg")));//
				 	rows.put("serialno1",checkGet(rs.getString("serialno1")));//
				 	rows.put("barcode",checkGet(rs.getString("barcode")));//
				 	rows.put("ownership",checkGet(rs.getString("ownership")));//
				 	rows.put("differencekind",checkGet(rs.getString("differencekind")));//
				 	rows.put("differencekindname",checkGet(rs.getString("differencekindname")));//
				 	rows.put("propertytype",checkGet(rs.getString("propertytype")));//
				 	rows.put("propertytypename",checkGet(rs.getString("propertytypename")));//
				 	rows.put("closingdate",checkGet(rs.getString("closingdate")));//
				 	rows.put("bookamount",checkGet(rs.getString("bookamount")));//
				 	rows.put("actualamount",checkGet(rs.getString("actualamount")));//
				 	rows.put("bookunit",checkGet(rs.getString("bookunit")));//
				 	rows.put("bookvalue",checkGet(rs.getString("bookvalue")));//
				 	rows.put("checkresult",checkGet(rs.getString("checkresult")));//
				 	rows.put("oddscause",checkGet(rs.getString("oddscause")));//
				 	rows.put("scrappednote",checkGet(rs.getString("scrappednote")));//
				 	rows.put("labelnote",checkGet(rs.getString("labelnote")));//
				 	rows.put("movenote",checkGet(rs.getString("movenote")));//
				 	rows.put("propertykind",checkGet(rs.getString("propertykind")));//
				 	rows.put("fundtype",checkGet(rs.getString("fundtype")));//
				 	rows.put("propertyno",checkGet(rs.getString("propertyno")));//
				 	rows.put("serialno",checkGet(rs.getString("serialno")));//
				 	rows.put("buydate",checkGet(rs.getString("buydate")));//
				 	rows.put("sourcedate",checkGet(rs.getString("sourcedate")));//
				 	rows.put("propertyname",checkGet(rs.getString("propertyname")));//
				 	rows.put("propertyname1",checkGet(rs.getString("propertyname1")));//
				 	rows.put("oldpropertyno",checkGet(rs.getString("oldpropertyno")));//
				 	rows.put("oldserialno",checkGet(rs.getString("oldserialno")));//
				 	rows.put("specification",checkGet(rs.getString("specification")));//
				 	rows.put("nameplate",checkGet(rs.getString("nameplate")));//
				 	rows.put("propertyunit",checkGet(rs.getString("propertyunit")));//
				 	rows.put("material",checkGet(rs.getString("material")));//
				 	rows.put("limityear", Common.MonthsFormat(rs.getString("limityear")));//
				 	rows.put("useyear",checkGet(rs.getString("useyear")));//
				 	rows.put("keepunit",checkGet(rs.getString("keepunit")));//
				 	rows.put("keepunitname",checkGet(rs.getString("keepunitname")));//
				 	rows.put("keeper",checkGet(rs.getString("keeper")));//
				 	rows.put("keepername",checkGet(rs.getString("keepername")));//
				 	rows.put("useunit",checkGet(rs.getString("useunit")));//
				 	rows.put("useunitname",checkGet(rs.getString("useunitname")));//
				 	rows.put("userno",checkGet(rs.getString("userno")));//
				 	rows.put("usernoname",checkGet(rs.getString("usernoname")));//
				 	rows.put("usernote",checkGet(rs.getString("usernote")));//
				 	rows.put("place1",checkGet(rs.getString("place1")));//
				 	rows.put("place1name",checkGet(rs.getString("place1name")));//
				 	rows.put("place",checkGet(rs.getString("place")));//
				 	rows.put("signno",checkGet(rs.getString("signno")));//
				 	rows.put("signnoname",checkGet(rs.getString("signnoname")));//
				 	rows.put("doorplate4",checkGet(rs.getString("doorplate4")));//
				 	rows.put("notes",checkGet(rs.getString("notes")));//
				 	rows.put("editid",checkGet(rs.getString("editid")));//
				 	rows.put("editdate",checkGet(rs.getString("editdate")));//
				 	rows.put("edittime",checkGet(rs.getString("edittime")));//
				 	rows.put("placeNote",checkGet(rs.getString("placeNote")));//
				 	obj.put(rows);
			 	    jsonobj.put("resultList", obj);
					}
				}else{
					isSuccess="Y";
					message="查無資料";
				}
			 }	
			 catch (Exception e) 
			 {
				 isSuccess="N";
				 message="WEB SERVICE程式發生錯誤："+e.getMessage();				 
				 throw new Exception(e.getMessage());
			 }
	 		finally
			{
	 			db.closeAll();
			} 
		}else{
			isSuccess="N";
			message="認證碼錯誤";
		}

	    jsonobj.put("message",message);
	    jsonobj.put("isSuccess",isSuccess);
	    
		return jsonobj.toString();
			 
	}
	
	//參數多一個PageIndex
	public String Query_list1(String enterorg, String useunit, String place1,
			String keepunit, String keeper, String userno, String propertyno, String Chktype,
			String PageIndex, String AuthCode) throws Exception  {
		
		Database db = new Database();
        String isSuccess = "";
        String message="";
        String AuthCodeChk="";
        String SqlStr="";
        String SqlStrCount="";
        String tablename="";
		JSONObject jsonobj=new JSONObject();
		JSONArray obj=null;
		//新增的變數
		int pageIndex = 0; //傳回的頁碼
		int pageCount = 0; //查詢條件共分幾頁
		int pageSize = 500; //每頁資料筆數
		int dataCount = 0; //資料總筆數
		
 	    obj = new JSONArray();
		
 	    if(enterorg==null){enterorg="";}
		if(useunit==null){useunit="";}
		if(place1==null){place1="";}
		if(keepunit==null){keepunit="";}
		if(keeper==null){keeper="";}
		if(userno==null){userno="";}
		if(propertyno==null){propertyno="";}
		if(Chktype==null){Chktype="";}
		
		//認證碼
		AuthCodeChk=enterorg+useunit+place1+keepunit+keeper+userno+propertyno;
		System.out.println("AuthCodeChk="+AuthCodeChk);
		AuthCodeChk=getAuthKey(AuthCodeChk);		
		System.out.println("AuthCodeChk="+AuthCodeChk);
		
		if(AuthCodeChk.equals(AuthCode)){
			try
			 {
				if(Chktype.equals("1")){
					//計算筆數用SQL
					  SqlStrCount = " select count(1) from UNTPD_CHECKMOVABLE b ";
					
						SqlStr=" select "+
					  "b.enterorg, b.serialno1, b.barcode, b.ownership,b.differencekind,b.differencekindname,b.propertytype,b.propertytypename, b.closingdate, b.propertyno, b.serialno,"+ 
					  "(select a.propertyname from SYSPK_PROPERTYCODE a where (a.enterorg=b.enterorg or a.enterorg='000000000A') and a.propertyno=b.propertyno ) as propertyname, "+
					  "b.propertyname1, b.nameplate, b.buydate, b.propertyunit,b.fundtype,b.sourcedate,b.oldserialno,b.specification,"+ 
					  "b.bookamount,b.bookunit,b.checkresult,b.oddscause, b.bookvalue, b.sourcedate, b.limityear, b.useyear, b.keepunitname,"+ 
					  "b.keepername,b.scrappednote,b.labelnote,b.movenote,b.propertykind, b.place1name, b.actualamount,b.material, b.notes," +
					  "b.keepunit,b.keeper,b.keepername,b.useunit,b.useunitname,b.oldpropertyno,b.userno,b.usernoname,b.usernote,b.place1,b.place,b.signno," +
					  "b.signnoname,b.doorplate4,b.editid,b.editdate,b.edittime,b.placenote "+  
					  "from UNTPD_CHECKMOVABLE b where 1=1";
				}
				else if(Chktype.equals("2")){
					//計算筆數用SQL
					 SqlStrCount = " select count(1) from UNTPD_CHECKNONEXP b ";
					 
					SqlStr=" select "+
					  "b.enterorg, b.serialno1, b.barcode, b.ownership,b.differencekind,b.differencekindname,b.propertytype,b.propertytypename, b.closingdate, b.propertyno, b.serialno,"+ 
					  "(select a.propertyname from SYSPK_PROPERTYCODE2 a where (a.enterorg=b.enterorg or a.enterorg='000000000A') and a.propertyno=b.propertyno ) as propertyname, "+
					  "b.propertyname1, b.nameplate, b.buydate, b.propertyunit,b.fundtype,b.sourcedate,b.oldserialno,b.specification,"+ 
					  "b.bookamount,b.bookunit,b.checkresult,b.oddscause, b.bookvalue, b.sourcedate, b.limityear, b.useyear, b.keepunitname,"+ 
					  "b.keepername,b.scrappednote,b.labelnote,b.movenote,b.propertykind, b.place1name, b.actualamount,b.material, b.notes," +
					  "b.keepunit,b.keeper,b.keepername,b.useunit,b.useunitname,b.oldpropertyno,b.userno,b.usernoname,b.usernote,b.place1,b.place,'' as signno," +
					  "'' as signnoname,'' as doorplate4,b.editid,b.editdate,b.edittime,b.placenote "+  
					  "from UNTPD_CHECKNONEXP b where 1=1";
				}
				else{
					isSuccess="N";
					message="盤點類型輸入錯誤，1：財產 ; 2：物品";
					jsonobj.put("message",message);
				    jsonobj.put("isSuccess",isSuccess);
					return jsonobj.toString();
				}
			  if(enterorg.length()>0){
				SqlStr=SqlStr+" and b.enterorg = '"+enterorg+"' ";
			  }	
			  if(useunit.length()>0){
				SqlStr=SqlStr+" and b.useunit = '"+useunit+"' ";
			  }
			  if(place1.length()>0){
				SqlStr=SqlStr+" and b.place1 = '"+place1+"' ";
			  }
			  if(keepunit.length()>0){
				SqlStr=SqlStr+" and b.keepunit = '"+keepunit+"' ";
			  }
			  if(keeper.length()>0){
				SqlStr=SqlStr+" and b.keeper = '"+keeper+"' ";
			  }
			  if(userno.length()>0){
				SqlStr=SqlStr+" and b.userno = '"+userno+"' ";
			  }	
			  if(propertyno.length()>0){
				SqlStr=SqlStr+" and b.differencekind = '"+propertyno+"' ";
			  }	
			  System.out.println(SqlStr);
			  SqlStrCount +=  SqlStr.substring(SqlStr.indexOf("where 1=1"));			  
			  
			  SqlStr += " order by b.keepername COLLATE Chinese_Taiwan_Stroke_CS_AS ";
			  
			  System.out.println(SqlStrCount);
			  
			  ResultSet rs = db.querySQL(SqlStrCount);	
			  pageIndex = ( PageIndex.equals(null)||PageIndex.equals("") )? 1: Integer.parseInt(PageIndex);

			  System.out.println("input pageIndex: " + pageIndex);
			  
			  if(rs.next()){				  
				  dataCount = rs.getInt(1);
				  pageCount = (dataCount + pageSize -1) / pageSize;

				  System.out.println("pageIndex: " + pageIndex);
				  System.out.println("pageSize: " + pageSize);
				  System.out.println("dataCount: " + dataCount);
				  System.out.println("pageCount: " + pageCount);
				  
				  isSuccess="Y";
				  message="查詢成功";
				  rs.close();					
					
				  rs = db.querySQL(SqlStr);					

				  int dataStartRow =(pageIndex-1)*pageSize +1;
				  int dataRow = 0;
				  if(pageIndex>pageCount){
					  dataStartRow = 1;
					  pageIndex = 1;
				  }
					
					while(rs.next()){						
						dataRow++;
						if(dataRow<dataStartRow){
							continue;
						}else if(dataRow==dataStartRow){
							  System.out.println("== 加入資料開始 ==");	
							  System.out.println("dataStartRow: " + dataStartRow);						
						}
						
						HashMap rows=new HashMap();
					 	rows.put("enterorg",checkGet(rs.getString("enterorg")));//
					 	rows.put("serialno1",checkGet(rs.getString("serialno1")));//
					 	rows.put("barcode",checkGet(rs.getString("barcode")));//
					 	rows.put("ownership",checkGet(rs.getString("ownership")));//
					 	rows.put("differencekind",checkGet(rs.getString("differencekind")));//
					 	rows.put("differencekindname",checkGet(rs.getString("differencekindname")));//
					 	rows.put("propertytype",checkGet(rs.getString("propertytype")));//
					 	rows.put("propertytypename",checkGet(rs.getString("propertytypename")));//
					 	rows.put("closingdate",checkGet(rs.getString("closingdate")));//
					 	rows.put("bookamount",checkGet(rs.getString("bookamount")));//
					 	rows.put("actualamount",checkGet(rs.getString("actualamount")));//
					 	rows.put("bookunit",checkGet(rs.getString("bookunit")));//
					 	rows.put("bookvalue",checkGet(rs.getString("bookvalue")));//
					 	rows.put("checkresult",checkGet(rs.getString("checkresult")));//
					 	rows.put("oddscause",checkGet(rs.getString("oddscause")));//
					 	rows.put("scrappednote",checkGet(rs.getString("scrappednote")));//
					 	rows.put("labelnote",checkGet(rs.getString("labelnote")));//
					 	rows.put("movenote",checkGet(rs.getString("movenote")));//
					 	rows.put("propertykind",checkGet(rs.getString("propertykind")));//
					 	rows.put("fundtype",checkGet(rs.getString("fundtype")));//
					 	rows.put("propertyno",checkGet(rs.getString("propertyno")));//
					 	rows.put("serialno",checkGet(rs.getString("serialno")));//
					 	rows.put("buydate",checkGet(rs.getString("buydate")));//
					 	rows.put("sourcedate",checkGet(rs.getString("sourcedate")));//
					 	rows.put("propertyname",checkGet(rs.getString("propertyname")));//
					 	rows.put("propertyname1",checkGet(rs.getString("propertyname1")));//
					 	rows.put("oldpropertyno",checkGet(rs.getString("oldpropertyno")));//
					 	rows.put("oldserialno",checkGet(rs.getString("oldserialno")));//
					 	rows.put("specification",checkGet(rs.getString("specification")));//
					 	rows.put("nameplate",checkGet(rs.getString("nameplate")));//
					 	rows.put("propertyunit",checkGet(rs.getString("propertyunit")));//
					 	rows.put("material",checkGet(rs.getString("material")));//
					 	rows.put("limityear",Common.MonthsFormat(checkGet(rs.getString("limityear"))));//
					 	rows.put("useyear",checkGet(rs.getString("useyear")));//
					 	rows.put("keepunit",checkGet(rs.getString("keepunit")));//
					 	rows.put("keepunitname",checkGet(rs.getString("keepunitname")));//
					 	rows.put("keeper",checkGet(rs.getString("keeper")));//
					 	rows.put("keepername",checkGet(rs.getString("keepername")));//
					 	rows.put("useunit",checkGet(rs.getString("useunit")));//
					 	rows.put("useunitname",checkGet(rs.getString("useunitname")));//
					 	rows.put("userno",checkGet(rs.getString("userno")));//
					 	rows.put("usernoname",checkGet(rs.getString("usernoname")));//
					 	rows.put("usernote",checkGet(rs.getString("usernote")));//
					 	rows.put("place1",checkGet(rs.getString("place1")));//
					 	rows.put("place1name",checkGet(rs.getString("place1name")));//
					 	rows.put("place",checkGet(rs.getString("place")));//
					 	rows.put("signno",checkGet(rs.getString("signno")));//
					 	rows.put("signnoname",checkGet(rs.getString("signnoname")));//
					 	rows.put("doorplate4",checkGet(rs.getString("doorplate4")));//
					 	rows.put("notes",checkGet(rs.getString("notes")));//
					 	rows.put("editid",checkGet(rs.getString("editid")));//
					 	rows.put("editdate",checkGet(rs.getString("editdate")));//
					 	rows.put("edittime",checkGet(rs.getString("edittime")));//
					 	rows.put("placeNote",checkGet(rs.getString("placeNote")));//
					 	obj.put(rows);
				 	    jsonobj.put("resultList", obj);
				 	    
				 	    if(dataRow == pageIndex*pageSize){
				 	    	break;
				 	    }
					}
		 	    	
					System.out.println("== 加入資料結束 ==");
					System.out.println("dataRow: " + dataRow);
					rs.close();
				}else{
					isSuccess="Y";
					message="查無資料";
				}
			 }	
			 catch (Exception e) 
			 {
				 isSuccess="N";
				 message="WEB SERVICE程式發生錯誤："+e.getMessage();				 
				 throw new Exception(e.getMessage());
			 }
	 		finally
			{
	 			
	 			db.closeAll();
			} 
		}else{
			isSuccess="N";
			message="認證碼錯誤";
		}

	    jsonobj.put("message",message);
	    jsonobj.put("isSuccess",isSuccess);
	    jsonobj.put("PageIndex",pageIndex);
	    jsonobj.put("PageCount",pageCount);
	    jsonobj.put("PageSize",pageSize);
	    jsonobj.put("DataCount",dataCount);
	    
		return jsonobj.toString();
			 
	}
		
	
	private static String getAuthKey(String str)

    { 
			String strR="";
            String strKey1="TCGH"+str+"KANGDA";

            byte[] b;
			try {
				b = strKey1.getBytes("UTF-8");
				strR=toMd5(b).toUpperCase();
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
            return strR;

    }

    

    

    private static String toMd5(byte[] bytes) {

    try {

            MessageDigest algorithm = MessageDigest.getInstance("MD5");

        algorithm.reset();

        algorithm.update(bytes);

        return toHexString(algorithm.digest(), "");

    } 

    catch (NoSuchAlgorithmException e) 

    {

        throw new RuntimeException(e);

    }

}



private static String toHexString(byte[] bytes, String separator) 

{

    StringBuilder hexString = new StringBuilder();

    for (byte b : bytes) {

            if  (Integer.toHexString(0xFF & b).length() == 1)   

            {

            hexString.append("0").append(Integer.toHexString(0xFF & b)).append(separator);

            }

            else

            {

            hexString.append(Integer.toHexString(0xFF & b)).append(separator);

            }

    }

    return hexString.toString();

}
private String checkGet(String s){
	if(s==null){								return "";
	}else if("null".equals(s.toLowerCase())){	return "";
	}else{										return s.trim();
	}
}
	
public static void main(String[] args){
	String enterorg = TCGHCommon.getSYSCODEName("01", "ETO");
	String useunit ="";
	String place1 ="";
	String keepunit ="";
	String keeper ="";
	String userno ="";
	String propertyno ="";
	String Chktype ="1";
	String PageIndex ="8";
	String authCode ="2D15FFD95726D26DE93F8CABB3E218EB";
	
	WS02Impl ws02impl = new WS02Impl();
	try{
		
		String result = ws02impl.Query_list1(enterorg, useunit, place1, keepunit, keeper, userno, propertyno, Chktype, PageIndex, authCode);
//		System.out.println(result.substring(0, 1000));
		System.out.println(result);
		
	}catch(Exception e){
		e.printStackTrace();
	}
	

}
}
