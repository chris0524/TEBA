package unt.ch.untch001f00;

import java.util.HashMap;
import java.util.Set;
import java.util.HashSet;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.LinkedHashMap;
import java.util.Iterator;

public class UNTCH001F00_LA_ADD extends UNTCH001F00_Base{

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
			result.put("增加單資料", 		"../ch/untch001f01.jsp");		//1
			result.put("基本資料", 		"../ch/untch001f02.jsp");		//2
		}else if("_query".equals(viewType)){
			result.put("財產明細", 		"../ch/untch001f02_1.jsp");		//2
		}else if("_maintenance".equals(viewType)){
			result.put("財產明細", 		"../ch/untch001f02_2.jsp");		//2
		}
		
		
		result.put("土地基本資料",		"../la/untla002f.jsp");			//3
		result.put("管理資料", 			"../la/untla003f.jsp");			//4
		result.put("地上物", 			"../la/untla004f.jsp");			//5
		result.put("公告地價	", 			"../la/untla005f.jsp");			//6
		result.put("公告現值	", 			"../la/untla006f.jsp");			//7
		result.put("他項權利", 			"../la/untla051f.jsp");			//8
		result.put("土地使用權", 			"../la/untla052f.jsp");			//9
		result.put("折舊比例", 		"../ch/untch001f03.jsp");
		if("".equals(viewType)){
			
		}else if("_queryOrMaintenance".equals(viewType) || "_query".equals(viewType) || "_maintenance".equals(viewType)){
			result.put("帳務資料", 			"../ch/untch001f04.jsp");		//10
			result.put("移動紀錄", 			"../ch/untch001f06.jsp");		//11
		}
		
		return result;
	}
		
}


