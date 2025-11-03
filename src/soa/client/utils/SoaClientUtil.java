package soa.client.utils;

import java.net.URL;

import util.SslUtils;

public class SoaClientUtil {
	protected static URL createWsdlUrl(String wsdlUrl){
		try {
			SslUtils.ignoreSsl();
			return new URL(wsdlUrl);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
