package unt.ch.untch001f00;

import java.util.HashMap;
import java.util.Set;
import java.util.HashSet;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.LinkedHashMap;
import java.util.Iterator;

public class UNTCH001F00_RF_ADD_QUERY extends UNTCH001F00_Base{

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
		
		result.put("土地改良物基本資料", 	"../rf/untrf002f.jsp");			//3
		result.put("基地資料", 			"../rf/untrf003f.jsp");			//4
		result.put("管理資料",	 		"../rf/untrf026f.jsp");			//5
		
		if("".equals(viewType)){
			
		}else if("_queryOrMaintenance".equals(viewType)){
			result.put("折舊比例", 			"../ch/untch001f03.jsp");		//6
			result.put("折舊紀錄", 			"../ch/untch001f05.jsp");		//7
			result.put("帳務資料", 			"../ch/untch001f04.jsp");		//8
			result.put("移動紀錄", 			"../ch/untch001f06.jsp");		//9
		}
		
		return result;
	}
		
}


