package soa.model.res.sso;

import soa.model.res.ActXML;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("ActXML")
public class GetToken2ActXML extends ActXML {
	private static XStream xs = createXStream(GetToken2ActXML.class);
	
	public RsInfo RsInfo;

	public static GetToken2ActXML fromXml(String xml) {

		return (GetToken2ActXML) xs.fromXML(xml);
	}

	public String toXml() {
		XStream xs = new XStream();

		xs.processAnnotations(ActXML.class);
		xs.processAnnotations(this.RsInfo.getClass());
		return xs.toXML(this);
	}

	public static void main(String[] args) {


		String xml = "<ActXML><StatusCode>200</StatusCode><Message>123</Message><RsInfo><TokenId>c8e33430d5863f8ac16cd2a0d92c9cfd</TokenId></RsInfo></ActXML>";
		GetToken2ActXML x = GetToken2ActXML.fromXml(xml);
		System.out.println(x);
	}
	
	public static class RsInfo {					
		public User User;
		
		public static class User{
			public String UserId;
			public String Sn;
			public String CName;
			public String Email;
			public String Gender;
			public String Uid;
			public String Idno;
			public String AssistantUserId;
			public String GovCode;
			public String HasCert;
		}
	}
}
