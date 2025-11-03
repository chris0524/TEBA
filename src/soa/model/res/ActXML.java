package soa.model.res;

import org.apache.commons.lang.StringUtils;

import com.thoughtworks.xstream.XStream;

public class ActXML {
	
	public String StatusCode;
	
	private String Message;
	
	public String Messsage;
	
	/**
	 * 由於SOA開發者筆誤的關係，在不同的服務所回傳的ActXML內容的子節點的名稱，
	 * 有些服務會回傳Message，有些服務回傳Messsage。由於節點名稱可能不同，
	 * 因此在本類別對於該節點的處理上，就是兩個欄位都定義，
	 * 而XStream會找到名稱存在的節點，並把內容設定上去。
	 * 為了讓本類別的getMessage()可以取得可能正確的內容，
	 * 因此以如下的處理方式取得該值。
	 * @return
	 */
	public String getMessage() {		
		return StringUtils.isNotEmpty(Message)? Message: Messsage;
	}

	public void setMessage(String message) {
		Message = message;
	}

	public boolean isSuccessful(){
		return "200".equals(this.StatusCode)? true: false;
	}
	
	public static XStream createXStream(Class annotationClass){
		XStream xs = new XStream();
		xs.ignoreUnknownElements();
		xs.processAnnotations(annotationClass);
		
		return xs;
	}
}
