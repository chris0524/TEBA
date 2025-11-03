package soa.model.res.data;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;

import soa.model.res.ActXML;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("ActXML")
public class GetOrgUserInfoActXML extends ActXML {
	private static XStream xs = createXStream(GetOrgUserInfoActXML.class);
	
	public RsInfo RsInfo;

	public static class RsInfo {
		public static User User;
		
		public static class User{
			public String userId; //人員登入帳號
			public String userName; //人員中文姓名
			public String userTelH; //人員家中電話聯絡資訊
			public String userEmail; //人員的電子郵件位址
			public String userType; //人員的使用者類別，類別代碼如右表示︰ 0.	離職	1.	會內人員	2.	附屬單位	3.	駐外人員	4.	委外人員	5.	工讀生6.	顧問	7.	廠商	8.	其他	9.	公用
			public String entryId; //該人員資料的建立者
			public String gender; //性別
			public String userDesc; //人員描述
			public String tryLoginCount; //人員嘗試登入錯誤次數
			public String userAddr; //住址
			public String orgUserId; //人員流水號
			public String userTel; //人員聯絡電話
			public String userEng; //人員英文姓名
			public String modifyId; //修改者的ID
			public String active; //人員是否啟用
			public String idno; //身分證字號
			public String titleId; //保留欄位2
			public String userDuty; //職稱
			public String mobile; //手機
			public String orgCode; //人員所屬單位代碼
			public String serialNumber; //人員憑證號碼
		}
	}

	public static GetOrgUserInfoActXML fromXml(String xml) {
		return (GetOrgUserInfoActXML) xs.fromXML(xml);
	}
	
	public static void main(String[] args) {
		String xml = getXml();
		GetOrgInfoActXML x = GetOrgInfoActXML.fromXml(xml);
		System.out.println(x);
	}

	private static String getXml() {
		try {
			return FileUtils.readFileToString(new File("C:/TCGH05_工作區/新文字文件.txt"), "UTF-8");
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
	
	public String toXml() {
		XStream xs = new XStream();

		xs.processAnnotations(ActXML.class);
		xs.processAnnotations(this.RsInfo.getClass());
		return xs.toXML(this);
	}
}
