package service.impl;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.ResultSet;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONObject;
import service.WS01;
import util.Database;


public class WS01Impl implements WS01 {

	@Override
	public String Query_list(String empid, String emppwd,String AuthCode) throws Exception  {
		
		Database db = new Database();
        String isSuccess = "";
        String message="";
        String AuthCodeChk="";
        String SqlStr="";
		JSONObject jsonobj=new JSONObject();
		JSONArray obj=null;
		
 	    obj = new JSONArray();
		
 	    if(empid==null){empid="";}
		if(emppwd==null){emppwd="";}
		
		//認證碼
		AuthCodeChk=empid+emppwd;
		//System.out.println("AuthCodeChk="+AuthCodeChk);
		AuthCodeChk=getAuthKey(AuthCodeChk);		
		System.out.println("AuthCodeChk="+AuthCodeChk);
		
		if(AuthCodeChk.equals(AuthCode)){
			try
			 {
				SqlStr=" select "+
				" a.empid,a.organid,a.empname "+ 
				"from SYSAP_EMP a where 1=1" +
				" and a.empid = '"+empid+"' " +
				" and a.emppwd = '"+emppwd+"' ";
			  
			//System.out.println(SqlStr);
			  ResultSet rs = db.querySQL(SqlStr);
			  if(rs.next()){
					isSuccess="Y";
					message="查詢成功";
					jsonobj.put("empid",rs.getString("empid"));
					jsonobj.put("organid",rs.getString("organid"));
					jsonobj.put("empname",rs.getString("empname"));
				}else{
					isSuccess="Y";
					message="登入失敗";
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
