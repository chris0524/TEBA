package unt.ch.untch001f00;

import java.util.HashMap;
import java.util.Set;
import java.util.HashSet;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.LinkedHashMap;
import java.util.Iterator;

public class UNTCH001F00_MP_ADD_QUERY extends UNTCH001F00_Base{

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
			result.put(2, new int[]{1,2,3,7,9,10});
			result.put(3, new int[]{1,2,3,4,5,7,8,9,10});
			result.put(4, new int[]{1,2,3,4,5,6,7,8,9,10});
			result.put(5, new int[]{1,2,3,4,5,6,7,8,9,10});
			result.put(7, new int[]{1,2,3,4,5,7,8,9,10});
			result.put(8, new int[]{1,2,3,4,5,7,8,9,10});
			result.put(9, new int[]{1,2,3,4,5,7,8,9,10});
			result.put(10, new int[]{1,2,3,4,5,7,8,9,10});
				
		}else{
			result.put(1, new int[]{1,2,3,7,9});
			result.put(2, new int[]{1,2,3,4,6,7,8,9});
			result.put(3, new int[]{1,2,3,4,6,7,8,9});
			result.put(4, new int[]{1,2,3,4,5,6,7,8,9});
			result.put(6, new int[]{1,2,3,4,6,7,8,9});
			result.put(7, new int[]{1,2,3,4,6,7,8,9});
			result.put(8, new int[]{1,2,3,4,6,7,8,9});
			result.put(9, new int[]{1,2,3,4,6,7,8,9});
			
		}
		
		return result;
	}
	
	public Map getData(){
		Map result = new LinkedHashMap();
		
		if("".equals(viewType)){
			result.put("增加單資料", 		"../ch/untch001f10.jsp");		//1
		}
		result.put("基本資料", 			"../ch/untch001f09.jsp");		//2
		
		result.put("動產基本資料",	 	"../mp/untmp002f.jsp");			//3
		result.put("動產明細", 			"../mp/untmp004f.jsp");			//4
		result.put("附屬設備",	 		"../mp/untmp035f.jsp");			//5
		result.put("附屬設備明細", 		"../mp/untmp005f.jsp");			//6
				
		if("".equals(viewType)){
			
		}else if("_queryOrMaintenance".equals(viewType)){
			result.put("折舊比例", 			"../ch/untch001f03.jsp");		//7
			result.put("折舊紀錄", 			"../ch/untch001f05.jsp");		//8
			result.put("帳務資料", 			"../ch/untch001f04.jsp");		//9
			result.put("移動紀錄", 			"../ch/untch001f06.jsp");		//10
		}
		
		return result;
	}
		
}


