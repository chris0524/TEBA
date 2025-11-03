package test;

import javax.xml.namespace.QName;

import org.apache.axis.client.Call;
import org.apache.axis.client.Service;

import util.Common;

public class TestWS {
	
	public void testXClient() throws Exception {
		Service axisService = new Service();				
		Call call = (Call) axisService.createCall();
		call.setTargetEndpointAddress("http://localhost:8081/TCGH/xws/WS02");					
		call.setOperationName(new QName("Query_list", "Query_list"));
		Object r = call.invoke(new String[]{"0001", "00", "11", "22", "uu", "cc", "123","1", "2"});					
		String jsonString = Common.get(r);        
		if (jsonString!=null && jsonString.length()>0) {
			System.out.println(jsonString);
		}        
	}		

	/**
	 * @param args
	 */
	public static void main(String[] args) throws Exception {
		long timeStart = System.currentTimeMillis();
		System.out.println(String.valueOf(new java.util.Date(timeStart)));		
		TestWS obj = new TestWS();
		//obj.testXFire();
		//obj.testAxisClient();
		obj.testXClient();
		//obj.testWSFile();
		//obj.testAxisWS();
		
		long timeEnd = System.currentTimeMillis();
		System.out.println(String.valueOf(new java.util.Date(timeEnd)));	
		
	}

}
