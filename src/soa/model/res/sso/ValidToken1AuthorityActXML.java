package soa.model.res.sso;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.annotations.XStreamAlias;

import soa.model.res.ActXML;

@XStreamAlias("ActXML")
public class ValidToken1AuthorityActXML extends ActXML {		
	private static XStream xs = createXStream(ValidToken1AuthorityActXML.class);
	
	public RsInfo RsInfo;
	
	public static ValidToken1AuthorityActXML fromXml(String xml) {
		return (ValidToken1AuthorityActXML) xs.fromXML(xml);
	}
	
	public static class RsInfo {
		public String Active;
		public String CharacterName;
	}

}
