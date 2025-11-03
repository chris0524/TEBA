package soa.model.res.sso;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.annotations.XStreamAlias;

import soa.model.res.ActXML;

@XStreamAlias("ActXML")
public class ValidToken1ActXML extends ActXML {		
	private static XStream xs = createXStream(ValidToken1ActXML.class);
	
	public String RsInfo;
	
	public static ValidToken1ActXML fromXml(String xml) {
		return (ValidToken1ActXML) xs.fromXML(xml);
	}

}
