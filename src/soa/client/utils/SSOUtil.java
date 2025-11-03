package soa.client.utils;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;

import org.json.JSONObject;

import soa.model.SoaUser;
import soa.model.res.sso.GetToken1ActXML;
import soa.model.res.sso.GetToken2ActXML;
import soa.model.res.sso.ValidToken1ActXML;
import soa.model.res.sso.ValidToken1AuthorityActXML;
import tw.gov.nsc.soa.org.ssoservice_interface.SSOService;
import util.SslUtils;

public class SSOUtil extends SoaClientUtil{
	public static String wsdlUrl;
	
	public static String getWsdlUrl() {
		return wsdlUrl;
	}
	public static void setWsdlUrl(String wsdlUrl) {
		SSOUtil.wsdlUrl = wsdlUrl;
	}
	private static SSOService createSSOService(){
		try {
			SslUtils.ignoreSsl();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new tw.gov.nsc.soa.org.ssoservice.SSOService(createWsdlUrl(wsdlUrl), 
				tw.gov.nsc.soa.org.ssoservice.SSOService.SERVICE).getSSOServiceImplPort();
	}
	public static boolean validToken1(String ip, String token1) {
		
		SSOService ss = createSSOService();
		String xml = ss.validToken1(ip, token1);
		ValidToken1ActXML res = ValidToken1ActXML.fromXml(xml);
		System.out.println("res.RsInfo:" + res.RsInfo);
		if (!res.isSuccessful())
			throw new RuntimeException(res.getMessage());
		
		return "true".equalsIgnoreCase(res.RsInfo)? true: false;
	}

	public static String getToken1(String userID, String password, String ip) {
		SSOService ss = createSSOService();
		String xml = ss.getToken1(userID, password, ip);
		GetToken1ActXML res = GetToken1ActXML.fromXml(xml);

		if (!res.isSuccessful())
			throw new RuntimeException(res.getMessage());
		return res.RsInfo.TokenId;
	}

	public static boolean isNeedValidateToken1(ServletRequest request, int validateSSOToken1Type, long intervalForValidingSSOToken1){
		boolean isNeed = false;
		switch(validateSSOToken1Type){
		case 0:
			isNeed = false;
			break;
		case 1:
			isNeed = true;
			break;
		case 2:
			isNeed = caluValidateType2(request, intervalForValidingSSOToken1);
			break;
			default:
				break;
		}
		return isNeed;
	}
	
	private static boolean caluValidateType2(ServletRequest request, long intervalForValidingSSOToken1) {
		HttpServletRequest hr = (HttpServletRequest)request;
		long lastAccessedTime = hr.getSession().getLastAccessedTime();
		return ((System.currentTimeMillis() - lastAccessedTime) > intervalForValidingSSOToken1)? true: false;
	}
	
	public static soa.model.res.sso.GetToken2ActXML.RsInfo.User getToken2(String eipToken1,String userIP) {

	       HttpURLConnection connection = null;
	        BufferedReader reader = null;
	        System.out.println("eipToken1 :"+eipToken1 +";;" + "userIP :" +userIP  );
	        try {
	            // API 端点
	            String url = "https://soa.nstc.gov.tw/SOA/api/Authority/GetTokenValidate?tokenId="+eipToken1  ;
	    		try {
	    			SslUtils.ignoreSsl();
	    		} catch (Exception e) {
	    			e.printStackTrace();
	    		}
	            // 创建 URL 对象
	            URL obj = new URL(url);
	            // 打开连接
	            connection = (HttpURLConnection) obj.openConnection();
	            
	            // 设置请求方法为 GET
	            connection.setRequestMethod("GET");
	            connection.setRequestProperty("SOA-Token","SOASIMPLETOKEN");
	            connection.setRequestProperty("Accept", "application/json");
	            
	            // 获取响应码
	            int responseCode = connection.getResponseCode();
	            
	            // 获取响应输入流
	            InputStreamReader inputStreamReader;
	            if (responseCode >= HttpURLConnection.HTTP_BAD_REQUEST) {
	                // 获取错误流（4xx 或 5xx 响应）
	                inputStreamReader = new InputStreamReader(connection.getErrorStream(), "UTF-8");
	            } else {
	                // 获取正常流
	                inputStreamReader = new InputStreamReader(connection.getInputStream(), "UTF-8");
	            }
	            
	            // 读取响应体
	            reader = new BufferedReader(inputStreamReader);
	            StringBuilder response = new StringBuilder();
	            String line;
	            while ((line = reader.readLine()) != null) {
	                response.append(line);
	            }
	            
	            // 输出响应体
	            
	            String jsonResponse = response.toString();
	            JSONObject resultObject = new JSONObject(jsonResponse);
	            JSONObject dataObject = resultObject.getJSONObject("data");
	            System.out.println("resultObject :" + resultObject.toString());
	            soa.model.res.sso.GetToken2ActXML.RsInfo.User rsUser = new soa.model.res.sso.GetToken2ActXML.RsInfo.User();
	            rsUser.UserId = (String)dataObject.get("userId");
//	            String uid = (String)dataObject.get("uid");
	            rsUser.CName = (String)dataObject.get("cName");
	            rsUser.Email = (String)dataObject.get("userEmail");
	            rsUser.Gender = (String)dataObject.get("gender");
	            rsUser.Idno = (String)dataObject.get("idno");
	            rsUser.Sn = (String)dataObject.get("sn");
	            rsUser.AssistantUserId = (String)dataObject.get("AssistantUserId");
	            rsUser.GovCode = (String)dataObject.get("govCode");
	            System.out.println("SSOUITL 129Line userid:" + rsUser.UserId);
//	            System.out.println("SSOUITL 130Line uid:" + uid);
	            return rsUser;
	            
	        } catch (Exception e) {
	            e.printStackTrace();
	        } finally {
	            try {
	                if (reader != null) {
	                    reader.close();
	                }
	            } catch (Exception e) {
	               //錯誤紀錄
	            }
	            if (connection != null) {
	                connection.disconnect();
	            }
	        }
	        
	        return null;
		
	}
	
	public static boolean isValidToken1Authority(String eipToken1, String appCode, String userIp) {

		SSOService ss = createSSOService();
		String xml = ss.validToken1Authority(eipToken1, appCode, userIp);
		ValidToken1AuthorityActXML res = ValidToken1AuthorityActXML.fromXml(xml);
		if (!res.isSuccessful())
			throw new RuntimeException(res.getMessage());
		
		return "true".equalsIgnoreCase(res.RsInfo.Active)? true: false;
	}
	
}
