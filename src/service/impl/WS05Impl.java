package service.impl;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.ResultSet;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONObject;

import service.WS05;
import util.Common;
import util.Database;


public class WS05Impl implements WS05 {

	@Override
	public String Query_list(String enterorg,String AuthCode) throws Exception  {
		
		Database db = new Database();
        String isSuccess = "";
        String message="";
        String AuthCodeChk="";
        String SqlStr="";
		JSONObject jsonobj=new JSONObject();
		JSONArray obj=null;
		
 	    obj = new JSONArray();
		
 	    if(enterorg==null){enterorg="";}
		
		//認證碼
		AuthCodeChk=enterorg;
		//System.out.println("AuthCodeChk="+AuthCodeChk);
		AuthCodeChk=getAuthKey(AuthCodeChk);		
		System.out.println("AuthCodeChk="+AuthCodeChk);
		
		if(AuthCodeChk.equals(AuthCode)){
			try
			 {
				SqlStr=" select "+
				" a.enterOrg,a.placeno,a.placename"+ 
				" from SYSCA_PLACE a where 1=1";		
			  if(enterorg.length()>0){
				SqlStr=SqlStr+" and a.enterorg = '"+enterorg+"'";
			  }	
			  SqlStr+=" order by a.placename COLLATE Chinese_Taiwan_Stroke_CS_AS";
			  
			//System.out.println(SqlStr);
			  ResultSet rs = db.querySQL(SqlStr);
			  if(rs.next()){
					isSuccess="Y";
					message="查詢成功";
					rs.close();
					rs = db.querySQL(SqlStr);
					int order_i = 1;
					while(rs.next()){
						HashMap rows=new HashMap();
					 	rows.put("enterorg",checkGet(rs.getString("enterorg")));//
					 	rows.put("placeno",checkGet(rs.getString("placeno")));//
					 	rows.put("placename",checkGet(rs.getString("placename")));//
					 	rows.put("order", Common.get(order_i++));
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
