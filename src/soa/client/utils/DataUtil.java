package soa.client.utils;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import org.json.JSONObject;

import soa.model.res.data.GetOrgInfoActXML;
import soa.model.res.data.GetOrgListActXML;
import soa.model.res.data.GetOrgUserInfoActXML;
import soa.model.res.data.GetOrgUserInfoActXML.RsInfo;
import soa.model.res.data.GetOrgListActXML.RsInfo.Org;
import tw.gov.nsc.soa.org.dataservice_interface.DataService;

public class DataUtil extends SoaClientUtil{
	public static String wsdlUrl;
	
	private static DataService createDataService(){		
		return new tw.gov.nsc.soa.org.dataservice.DataService(createWsdlUrl(wsdlUrl), 
				tw.gov.nsc.soa.org.dataservice.DataService.SERVICE).getDataServiceImplPort();
	}
	public static List<Org> getOrgList(String govCode, String nscuCode, String level){
		DataService ds = createDataService();
		String xml = ds.getOrgList(govCode, nscuCode, level);
		GetOrgListActXML res = GetOrgListActXML.fromXml(xml);
		if (!res.isSuccessful())
			throw new RuntimeException(res.getMessage());
		
		return res.RsInfo.Orgs;
	}
	
	public static soa.model.res.data.GetOrgInfoActXML.RsInfo.Org getOrgInfo(String govCode, String nscuCode, String userStatus){
		DataService ds = createDataService();
		String xml = ds.getOrgInfo(govCode, nscuCode, userStatus);
		GetOrgInfoActXML res = GetOrgInfoActXML.fromXml(xml);
		if (!res.isSuccessful())
			throw new RuntimeException(res.getMessage());
		return res.RsInfo.Org;
	}
	
	public static soa.model.res.data.GetOrgUserInfoActXML.RsInfo.User getOrgUserInfo(String UserId){
		   HttpURLConnection connection = null;
	        BufferedReader reader = null;
	        try {
	            // API 端点
	            String url = "https://soa.nstc.gov.tw/SOA/api/Users/"+UserId ;
	            
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
	            System.out.println("Response Body : " + response.toString());
	            
	            String jsonResponse = response.toString();
	            JSONObject resultObject = new JSONObject(jsonResponse);
	            JSONObject dataObject = resultObject.getJSONObject("data");
	            GetOrgUserInfoActXML.RsInfo.User soaUser = new GetOrgUserInfoActXML.RsInfo.User();
	            soaUser.userId = (String)dataObject.get("userId"); //人員登入帳號
	            soaUser.userName = (String)dataObject.get("userName"); //人員中文姓名
	            soaUser.userTelH = (String)dataObject.get("userTelH"); //人員家中電話聯絡資訊
	            soaUser.userEmail = (String)dataObject.get("userEmail"); //人員的電子郵件位址
	            soaUser.userType = (String)dataObject.get("userType"); //人員的使用者類別，類別代碼如右表示︰ 0.	離職	1.	會內人員	2.	附屬單位	3.	駐外人員	4.	委外人員	5.	工讀生6.	顧問	7.	廠商	8.	其他	9.	公用
	            soaUser.entryId = (String)dataObject.get("entryId"); //該人員資料的建立者
	            soaUser.gender = (String)dataObject.get("gender"); //性別
	            soaUser.userDesc = (String)dataObject.get("userDesc"); //人員描述
	            int tryLoginCount = dataObject.getInt("tryLoginCount");
	            soaUser.tryLoginCount = String.valueOf(tryLoginCount); //人員嘗試登入錯誤次數
	            soaUser.userAddr = (String)dataObject.get("userAddr"); //住址
	            int orgUserid = dataObject.getInt("orgUserId");
	            soaUser.orgUserId = String.valueOf(orgUserid);
	            soaUser.userTel = (String)dataObject.get("userTel"); //人員聯絡電話
	            soaUser.userEng = (String)dataObject.get("userEng"); //人員英文姓名
	            soaUser.modifyId = (String)dataObject.get("modifyId"); //修改者的ID
	            soaUser.active = (String)dataObject.get("active"); //人員是否啟用
	            soaUser.idno = (String)dataObject.get("idno"); //身分證字號
	            soaUser.userDuty = (String)dataObject.get("userDuty"); //職稱
	            soaUser.mobile = (String)dataObject.get("mobile"); //手機
	            soaUser.orgCode = (String)dataObject.get("orgCode"); //人員所屬單位代碼
		
	            return RsInfo.User;
	    }catch (Exception e) {
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
	
	public static void setWsdlUrl(String wsdlUrl) {
		DataUtil.wsdlUrl = wsdlUrl;		
	}
	
	public static String getWsdlUrl() {
		return wsdlUrl;
	}
}
