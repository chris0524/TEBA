package soa.model.res.sso;

import soa.model.res.ActXML;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("ActXML")
public class GetToken1ActXML extends ActXML {
	private static XStream xs = createXStream(GetToken1ActXML.class);
	
	public RsInfo RsInfo;

	public static class RsInfo {
		public String TokenId;
	}

	public static GetToken1ActXML fromXml(String xml) {
		return (GetToken1ActXML) xs.fromXML(xml);
	}

	public String toXml() {
		XStream xs = new XStream();

		xs.processAnnotations(ActXML.class);
		xs.processAnnotations(this.RsInfo.getClass());
		return xs.toXML(this);
	}

	public static void main(String[] args) {

		toActXml();
		String xml = "<ActXML><StatusCode>500</StatusCode><Messsage>內部錯誤</Messsage></ActXML>>";
		GetToken1ActXML x = GetToken1ActXML.fromXml(xml);
		System.out.println(x);
	}

	private static void toActXml() {
		GetToken1ActXML x = new GetToken1ActXML();
		x.setMessage("123");
		x.StatusCode = "222";
		RsInfo ri = new RsInfo();
		ri.TokenId = "1111";
		x.RsInfo = ri;
		System.out.println(x.toXml());
	}
}
