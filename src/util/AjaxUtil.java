/**
 * Copyright (c) 2000-2009 Kangda, Inc All Rights Reserved.
 */
package util;

import java.util.Date;
import java.util.Map;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.FileUtils;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;


/**
 * AjaxUtil
 * 
 * @author catty
 * @version 1.0, Created on 2006/11/9
 * 
 * @author shark
 * @version 1.1 Modify Date 2009/06/30
 * @version 1.2 Upgrade Apache HttpClient from 3.x to 4.x 2014/09/14
 */
public class AjaxUtil {

	public static final String DEFAULT_ENCODING = "utf-8";

	protected static Logger logger = Logger.getLogger(AjaxUtil.class);

	public static Object connectTo(String url) {
		return connectTo(url, DEFAULT_ENCODING);
	}

	public static Object connectTo(String url, String encoding) {
		return connectTo(url, encoding, null, false, false);
	}

	/**
	 * 
	 * @param url
	 * @param encoding
	 * @param params
	 * @param isMultipartPost = 若上傳檔案,請將它設定true
	 * @param isReturnBinary = 若回傳的是檔案類型請設成true
	 * @return
	 */
	public static Object connectTo(String url, String encoding, Map<String,Object> params,	boolean isMultipartPost, boolean isReturnBinary) {
		logger.info("[connectTo.url] = " + url);
		StringBuffer sb = new StringBuffer();
		try {
			CloseableHttpClient client = null;
			if (url.toLowerCase().startsWith("https")) {
				client = HttpClients.custom().setSSLHostnameVerifier(new NoopHostnameVerifier()).build();
			} else {
				client = HttpClientBuilder.create().build(); //HttpClients.createDefault();
			}
			HttpPost method = new AjaxPostMethod(url, encoding);
			
			HttpEntity entity = null;
			if (isMultipartPost) {
				MultipartEntityBuilder entityBuilder = MultipartEntityBuilder.create();   
				//entityBuilder.setCharset(Consts.UTF_8);
				//entityBuilder.setContentType(ContentType.APPLICATION_FORM_URLENCODED.withCharset(encoding));
				entityBuilder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);
				
				// add request parameter
				if (params != null && params.size() > 0) {
					for (Object idx : params.keySet()) {					
						String key = (String) idx;
						Object value = params.get(key);
						if (value instanceof Date) {
					        StringBody strBody = new StringBody(DateUtil.parseToString((Date) value), ContentType.MULTIPART_FORM_DATA.withCharset(encoding));						
							entityBuilder.addPart(key, strBody);
						} else if (value instanceof java.io.File) {	
							FileBody f = new FileBody((java.io.File)value);
							entityBuilder.addPart(key, f);
						} else {
					        StringBody strBody = new StringBody(value.toString(), ContentType.MULTIPART_FORM_DATA.withCharset(encoding));						
							entityBuilder.addPart(key, strBody);
						}
					}					
				}	
				entity = entityBuilder.build();
			} else {
				// add request parameter
				if (params != null && params.size() > 0) {
					java.util.List<NameValuePair> arrList = new java.util.ArrayList<NameValuePair>();					
					for (Object idx : params.keySet()) {					
						String key = (String) idx;
						Object value = params.get(key);
						if (value instanceof Date) {
							arrList.add(new BasicNameValuePair(key, DateUtil.parseToString((Date) value)));
						} else if (value instanceof java.io.File) {								
							arrList.add(new BasicNameValuePair(key, Base64.encodeBase64URLSafeString(FileUtils.readFileToByteArray((java.io.File)value))));
						} else {
							arrList.add(new BasicNameValuePair(key, Common.get(value)));
						}
					}
					entity = new UrlEncodedFormEntity(arrList, encoding);
				}				
			}

			// execute httpRequest
			method.setEntity(entity);				
			CloseableHttpResponse response = client.execute(method);

			// check status code
			if (response.getStatusLine().getStatusCode() == org.apache.http.HttpStatus.SC_OK) {
				try {
					HttpEntity respBody = response.getEntity();
					if (isReturnBinary) {
						return respBody.getContent();
					} else {
						sb.append(EntityUtils.toString(respBody, encoding));
					}
				} catch (Exception e) {
					e.printStackTrace();
					throw e;
				}
			} else {
				sb.append(response.getStatusLine());
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Connection Failure", e);
			sb.append(e.getMessage());
		}
		return sb.toString();
	}

}

class AjaxPostMethod extends HttpPost {
	private String encoding = "UTF-8"; // default

	public AjaxPostMethod(String url) {
		super(url);
	}

	public AjaxPostMethod(String url, String encoding) {
		super(url);		
		this.encoding = encoding;
		//this.addHeader("Content-Type", "text/html; charset=" + encoding);
		//this.addHeader("User-Agent", "Apache HttpComponents Client");
	}

	public String getRequestCharSet() {
		return encoding;
	}
}
