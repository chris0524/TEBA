package unt.ch.untch001f00;

import java.util.HashMap;
import java.util.Set;
import java.util.HashSet;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.LinkedHashMap;
import java.util.Iterator;

public class UNTCH001F00_MP_DEAL extends UNTCH001F00_Base{

	public List getData_forStart(){
		List result = new ArrayList();
		
		result.add(1);
		result.add(2);
		
		return result;
	}
	
	public Map getData_forLimitLink(){
		Map result = new HashMap();				
		
		return result;
	}
	
	public Map getData(){
		Map result = new LinkedHashMap();
		
		result.put("處理單資料", 			"../mp/untmp021f.jsp");			//1
		result.put("處理單明細資料", 		"../mp/untmp022f.jsp");			//2
		
		return result;
	}
		
}


