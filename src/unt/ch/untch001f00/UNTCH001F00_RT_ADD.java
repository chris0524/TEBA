package unt.ch.untch001f00;

import java.util.HashMap;
import java.util.Set;
import java.util.HashSet;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.LinkedHashMap;
import java.util.Iterator;

public class UNTCH001F00_RT_ADD extends UNTCH001F00_Base{

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
			result.put("增加單資料", 		"../ch/untch001f01.jsp");
			result.put("基本資料", 		"../ch/untch001f02.jsp");
		}else if("_query".equals(viewType)){
			// 財產查詢
			result.put("財產明細", 		"../ch/untch001f02_1.jsp");
		}else if("_maintenance".equals(viewType)){
			// 財產維護
			result.put("財產明細", 		"../ch/untch001f02_2.jsp");
		}
		
		result.put("權利基本資料",		"../rt/untrt001f.jsp");
		result.put("標的資料",			"../rt/untrt002f.jsp");
		result.put("折舊比例", 			"../ch/untch001f03.jsp");
		result.put("折舊紀錄", 		"../ch/untch001f05.jsp");
		result.put("帳務資料", 			"../ch/untch001f04.jsp");
		result.put("移動紀錄", 			"../ch/untch001f06.jsp");
		return result;
	}
		
}


