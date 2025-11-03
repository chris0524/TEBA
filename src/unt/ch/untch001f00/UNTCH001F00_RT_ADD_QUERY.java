package unt.ch.untch001f00;

import java.util.HashMap;
import java.util.Set;
import java.util.HashSet;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.LinkedHashMap;
import java.util.Iterator;

public class UNTCH001F00_RT_ADD_QUERY extends UNTCH001F00_Base{

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
		
		return result;
	}
	
	public Map getData(){
		Map result = new LinkedHashMap();
		
		if("".equals(viewType)){
			result.put("增加單資料", 		"../ch/untch001f10.jsp");		//1
		}
		result.put("基本資料", 			"../ch/untch001f09.jsp");		//2
		
		result.put("權利基本資料",		"../rt/untrt001f.jsp");			//3
		result.put("標的資料",			"../rt/untrt002f.jsp");			//4
		
		if("".equals(viewType)){
			
		}else if("_queryOrMaintenance".equals(viewType)){
			result.put("帳務資料", 			"../ch/untch001f04.jsp");		//5
			result.put("移動紀錄", 			"../ch/untch001f06.jsp");		//6
		}
		
		return result;
	}
		
}


