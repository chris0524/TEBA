package unt.ch.untch001f00;

import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Iterator;

public class UNTCH001F00_BU_ADD extends UNTCH001F00_Base{

	public List getData_forStart(){
		List result = new ArrayList();
		
		if("".equals(viewType)){
			result.add(1);
			result.add(2);
		}else{
			result.add(1);
		}
		
		
		return result;
	}
	
	public Map getData_forLimitLink(){
		Map result = new HashMap();				
		
		if("".equals(viewType)){
			result.put(1, new int[]{1,2,10,11,12,13});	
			result.put(2, new int[]{1,2,3,10,11,12,13});	
		}else{
			result.put(1, new int[]{1,2,3});
		}
		
		
		return result;
	}
	
	public Map getData(){
		Map result = new LinkedHashMap();
		
		if("".equals(viewType)){
			result.put("增加單資料", 		"../ch/untch001f01.jsp");		//1
			result.put("財產明細",		"../ch/untch001f02.jsp");		//2
		}else if("_query".equals(viewType)){
			result.put("財產明細", 		"../ch/untch001f02_1.jsp");		//2
		}else if("_maintenance".equals(viewType)){
			result.put("財產明細", 		"../ch/untch001f02_2.jsp");		//2
		}
		
		
		result.put("建物基本資料", 		"../bu/untbu002f.jsp");			//3
		result.put("管理資料", 			"../bu/untbu003f.jsp");			//4
		result.put("樓層資料", 			"../bu/untbu004f.jsp");			//5
		result.put("附屬建物", 			"../bu/untbu005f.jsp");			//6
		result.put("基地資料", 			"../bu/untbu006f.jsp");			//7
		result.put("共同使用資料", 		"../bu/untbu007f.jsp");			//8
		result.put("評定現值", 			"../bu/untbu039f.jsp");			//9
		result.put("折舊比例", 			"../ch/untch001f03.jsp");		//10
		
		if("".equals(viewType)){
			
		}else if("_queryOrMaintenance".equals(viewType) || "_query".equals(viewType) || "_maintenance".equals(viewType)){
			result.put("折舊紀錄", 			"../ch/untch001f05.jsp");		//11
			result.put("帳務資料", 			"../ch/untch001f04.jsp");		//12
			result.put("移動紀錄", 			"../ch/untch001f06.jsp");		//13
		}
		return result;
	}
}


