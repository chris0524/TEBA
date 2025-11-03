package service.impl;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;
import service.WS03;
import util.Common;
import util.Database;
import util.TCGHCommon;


public class WS03Impl implements WS03 {

	@Override
	public String Get_list(String enterorg, String strJSONResult,String Chktype,String AuthCode) throws Exception  {
		
		Database db = new Database();
        String isSuccess = "";
        String message="";
        String AuthCodeChk="";
        String SqlStr="";
        int count=0; //update
        int count1=0;//insert
        int count2=0;
        int count3=0;
		JSONObject jsonobj=new JSONObject();
		JSONArray obj=null;
		
 	    obj = new JSONArray();
		
 	    if(enterorg==null){enterorg="";}
		if(strJSONResult==null){strJSONResult="";}
		
		//認證碼
		AuthCodeChk=enterorg+strJSONResult;
		System.out.println("AuthCodeChk="+AuthCodeChk);
		AuthCodeChk=getAuthKey(AuthCodeChk);		
		System.out.println("AuthCodeChk="+AuthCodeChk);
		
		if(AuthCodeChk.equals(AuthCode)){
			try
			 {
				if (strJSONResult!=null && strJSONResult.length()>0) {
					JSONArray list = new JSONArray(strJSONResult);
                    //JSONArray list = JSONArray.fromObject(strJSONResult);
                if (list!=null && list.length()>0) {                                
                for (int i=0; i<list.length(); i++) {                                        
                    StringBuilder sb = new StringBuilder(""); 
                    //JSONArray x = JSONArray.fromObject(list.get(i));
                    JSONObject PD = list.getJSONObject(i);
                    //count1++;
			
			for (int j=0; j<PD.length(); j++) {    
				//System.out.println("54321==>"+PD.getJSONObject("barcodes").getString("barcode"));
				HashMap map=new HashMap();
				map.put("enterorg",checkGet(enterorg));
				map.put("barcode",checkGet(PD.getJSONObject("barcodes").getString("barcode")));
				map.put("serialno1",checkGet(PD.getJSONObject("barcodes").getString("serialno1")));
				map.put("propertyno",checkGet(PD.getJSONObject("barcodes").getString("propertyno")));
				map.put("serialno",checkGet(PD.getJSONObject("barcodes").getString("serialno")));
				map.put("actualamount",checkGet(PD.getJSONObject("barcodes").getString("actualamount")));
				map.put("differencekind",checkGet(PD.getJSONObject("barcodes").getString("differencekind")));
				map.put("scrappednote",checkGet(PD.getJSONObject("barcodes").getString("scrappednote")));
				map.put("oddscause",checkGet(PD.getJSONObject("barcodes").getString("oddscause")));
				map.put("labelnote",checkGet(PD.getJSONObject("barcodes").getString("labelnote")));
				map.put("movenote",checkGet(PD.getJSONObject("barcodes").getString("movenote")));
				map.put("ownership",checkGet(PD.getJSONObject("barcodes").getString("ownership")));
				map.put("checkresult",checkGet(PD.getJSONObject("barcodes").getString("checkresult")));
				map.put("closingdate",checkGet(PD.getJSONObject("barcodes").getString("closingdate")));
				map.put("propertyname",checkGet(PD.getJSONObject("barcodes").getString("propertyname")));
				map.put("nameplate",checkGet(PD.getJSONObject("barcodes").getString("nameplate")));
				map.put("buydate",checkGet(PD.getJSONObject("barcodes").getString("buydate")));
				map.put("propertyunit",checkGet(PD.getJSONObject("barcodes").getString("propertyunit")));
				map.put("bookamount",checkGet(PD.getJSONObject("barcodes").getString("bookamount")));
				map.put("bookvalue",checkGet(PD.getJSONObject("barcodes").getString("bookvalue")));
				map.put("sourcedate",checkGet(PD.getJSONObject("barcodes").getString("sourcedate")));
				map.put("limityear",checkGet(PD.getJSONObject("barcodes").getString("limityear")));
				map.put("useyear",checkGet(PD.getJSONObject("barcodes").getString("useyear")));
				map.put("keepunitname",checkGet(PD.getJSONObject("barcodes").getString("keepunitname")));
				map.put("keepername",checkGet(PD.getJSONObject("barcodes").getString("keepername")));
				map.put("place1name",checkGet(PD.getJSONObject("barcodes").getString("place1name")));
				map.put("notes",checkGet(PD.getJSONObject("barcodes").getString("notes")));
				map.put("place",checkGet(PD.getJSONObject("barcodes").getString("place")));
				map.put("place1",checkGet(PD.getJSONObject("barcodes").getString("place1")));
				map.put("placenote",checkGet(PD.getJSONObject("barcodes").getString("placenote")));
				HashMap rows=new HashMap();
				if(Chktype.equals("1")){
					if(!map.get("serialno1").equals("")){
						int count5 = update("UNTPD_CHECKMOVABLE","UNTMP_MOVABLEDETAIL",map);
						if(count5==0){
						rows.put("serialno1",checkGet(map.get("serialno1").toString()));//
						rows.put("isSuccess","Y");//
						count++;
						}
						else 
						{						
						rows.put("serialno1",checkGet(map.get("serialno1").toString()));//
						rows.put("isSuccess","N");//
						count1++;
						}
						obj.put(rows);
						jsonobj.put("resultList", obj);
					}
					else {
						try{
						db.excuteSQL(insert("UNTPD_CHECKMOVABLE",map));
						count2++;
						}catch(Exception e) 
						{
						count3++;
						}
					}
					
				}
				else if(Chktype.equals("2")){
					if(!map.get("serialno1").equals("")){
						
						int count5 = update("UNTPD_CHECKNONEXP","UNTNE_NONEXPDETAIL",map);
						if(count5==0){
						rows.put("serialno1",checkGet(map.get("serialno1").toString()));//
						rows.put("isSuccess","Y");//
						count++;
						}
						else 
						{						
						rows.put("serialno1",checkGet(map.get("serialno1").toString()));//
						rows.put("isSuccess","N");//
						count1++;
						}
						obj.put(rows);
						jsonobj.put("resultList", obj);
					}
					else {
						try{
						db.excuteSQL(insert("UNTPD_CHECKNONEXP",map));
						count2++;
						}catch(Exception e) 
						{
						count3++;
						}
					}
				}
				else{
					isSuccess="N";
					message="盤點類型錯誤，1：財產 ; 2：物品";
					jsonobj.put("message",message);
				    jsonobj.put("isSuccess",isSuccess);
					return jsonobj.toString();
				}  
			}
				}
                if(count1+count3==0){
                isSuccess="Y";
        		message="盤點資料上傳成功:共"+(count+count2)+"筆";
                 }
                else{
					isSuccess="N";
					message="盤點資料上傳失敗:更新成功"+count+"筆，失敗"+count1+"筆;新增成功"+count2+"筆，失敗"+count3+"筆";
					}
                 }
              }
			 }catch (Exception e) 
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
	public Integer update(String tablename,String tablename1, HashMap map) {
		Database db = new Database();
		String sql="";
		int count=0;
		 try{
			 sql=" select * from "+ tablename +" "+
			 	  " where 1=1 " +
			 	  " and enterorg = "+Common.sqlChar(map.get("enterorg").toString())+"" +
			 	  " and serialno1 = "+Common.sqlChar(map.get("serialno1").toString())+"" +
			 	  "";
			 ResultSet rs = db.querySQL(sql);
			  if(rs.next()){
				  String placeUpdate = "";//選擇存置地點註記 才更新
				  if(map.get("placenote").toString().equals("Y")) {
					  placeUpdate = ", place = "+Common.sqlChar(map.get("place").toString())+","+
					   " place1 = "+Common.sqlChar(map.get("place1").toString());
				  }
				  sql=" update "+ tablename +" set " +
				   " actualamount = "+Common.sqlChar(map.get("actualamount").toString())+","+
				   " checkresult = "+Common.sqlChar(map.get("checkresult").toString())+","+
				   " scrappednote = "+Common.sqlChar(map.get("scrappednote").toString())+","+
				   " labelnote = "+Common.sqlChar(map.get("labelnote").toString())+","+
				   " movenote = "+Common.sqlChar(map.get("movenote").toString())+","+
				   " placenote = "+Common.sqlChar(map.get("placenote").toString())+","+
				   " notes = "+Common.sqlChar(map.get("notes").toString())+""+ 
				   placeUpdate +
				   " where 1=1 " +
		 		   " and enterorg = "+Common.sqlChar(map.get("enterorg").toString())+"" +
		 		   " and serialno1 = "+Common.sqlChar(map.get("serialno1").toString())+"" +
				   "";
				  db.excuteSQL(sql);
				  if(map.get("placenote").toString().equals("Y")){
					  sql=" update "+ tablename1 +" set " +
					  " place = "+Common.sqlChar(map.get("place").toString())+","+
					  " place1 = "+Common.sqlChar(map.get("place1").toString())+
					  " where 1=1 " +
					  " and enterorg = "+Common.sqlChar(map.get("enterorg").toString())+"" +
					  " and ownership = "+Common.sqlChar(map.get("ownership").toString())+"" +
					  " and propertyno = "+Common.sqlChar(map.get("propertyno").toString())+"" +
					  " and serialno = "+Common.sqlChar(map.get("serialno").toString())+"" +
					  " and differencekind = "+Common.sqlChar(map.get("differencekind").toString())+"" +
					  "";
				 db.excuteSQL(sql);
				  }
				  count=0;
			  }
			  else{
				  count++;  
			  }
			 
		 }catch(Exception e){
			  e.getStackTrace();
		  } finally {
				db.closeAll();
		  }
		return count;
		
	}
	public String insert(String tablename, HashMap map){
		Database db = new Database();
		String sql="";
		int tempSerialno1=0;
		Map<String,String> propertynameMap = TCGHCommon.getSyspk_PropertyCode(map.get("enterorg").toString());
		Map<String,String> PropertyMap = TCGHCommon.getSyspk_PropertyCode2(map.get("enterorg").toString()); //物品名稱
		 try{
			 if(tablename.equals("UNTPD_CHECKMOVABLE")){
			 tempSerialno1 = checkDataMatch(tablename,map); 
			 sql=" insert into UNTPD_CHECKMOVABLE (enterorg,differencekind,propertyno,serialno,serialno1,propertyname,bookamount) values " +
			 	 " ("+Common.sqlChar(map.get("enterorg").toString())+"," +
			 	 Common.sqlChar(map.get("differencekind").toString())+"," +
			 	 Common.sqlChar(map.get("propertyno").toString())+"," +
			 	 Common.sqlChar(map.get("serialno").toString())+"," +
			 	 tempSerialno1+"," +
			 	 Common.sqlChar(propertynameMap.get(map.get("propertyno").toString()))+"," +
			 	 Common.sqlChar(map.get("bookamount").toString())+
			 	 ")";
			 }
			 else if(tablename.equals("UNTPD_CHECKNONEXP")){
			 tempSerialno1 = checkDataMatch(tablename,map); 
			 sql=" insert into UNTPD_CHECKNONEXP (enterorg,differencekind,propertyno,serialno,serialno1,propertyname,bookamount) values " +
			 	 " ("+Common.sqlChar(map.get("enterorg").toString())+"," +
			 	 Common.sqlChar(map.get("differencekind").toString())+"," +
			 	 Common.sqlChar(map.get("propertyno").toString())+"," +
			 	 Common.sqlChar(map.get("serialno").toString())+"," +
			 	 tempSerialno1+"," +
			 	 Common.sqlChar(PropertyMap.get(map.get("propertyno").toString()))+"," +
			 	 Common.sqlChar(map.get("bookamount").toString())+
			 	 ")";
			 }
		 }catch(Exception e){
			  e.getStackTrace();
		  } finally {
				db.closeAll();
		  }
		return sql;
		
	}
	public Integer checkDataMatch(String tablename, HashMap map){
		Database db = new Database();
		int count = 0;
		 try{
			 String sql = " select max(serialno1) as max from "+tablename+ "" +
			 		      " where 1=1 " +
			 		      " and enterorg = '"+map.get("enterorg").toString()+"'" +
			 		      "";
			 ResultSet rs = db.querySQL(sql,true);
			 if (rs.next()){
				 count=Integer.parseInt(rs.getString("max"))+1;
			 }
			 else{
				 count=1;
			 }
		 }catch(Exception e){
			  e.getStackTrace();
		  } finally {
				db.closeAll();
		  }
		return count;
		
		
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
	
}
