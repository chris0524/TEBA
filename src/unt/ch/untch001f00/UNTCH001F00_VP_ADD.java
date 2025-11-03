package unt.ch.untch001f00;

import java.util.HashMap;
import java.util.Set;
import java.util.HashSet;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.LinkedHashMap;
import java.util.Iterator;

public class UNTCH001F00_VP_ADD extends UNTCH001F00_Base{

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
			result.put("財產明細", 		"../ch/untch001f02.jsp");		//2
		}else if("_query".equals(viewType)){
			result.put("財產明細", 		"../ch/untch001f02_1.jsp");		//2
		}else if("_maintenance".equals(viewType)){
			result.put("財產明細", 		"../ch/untch001f02_2.jsp");		//2
		}
		
		result.put("有價證券基本資料",		"../vp/untvp001f.jsp");			//3
		result.put("股份資料",			"../vp/untvp002f.jsp");			//4
		result.put("資本額更動紀錄",		"../vp/untvp003f.jsp");			//5
		
		if("".equals(viewType)){
			
		}else if("_queryOrMaintenance".equals(viewType) || "_query".equals(viewType) || "_maintenance".equals(viewType)){
			result.put("帳務資料", 			"../ch/untch001f04.jsp");	//6
			result.put("移動紀錄", 			"../ch/untch001f06.jsp");	//7
		}
		
		return result;
	}
		
}


