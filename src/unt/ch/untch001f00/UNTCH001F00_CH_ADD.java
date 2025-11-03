package unt.ch.untch001f00;

import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Iterator;

public class UNTCH001F00_CH_ADD extends UNTCH001F00_Base{
	
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
			result.put(1, new int[]{1,2});
		}
		
		return result;
	}
	
	public Map getData(){
		Map result = new LinkedHashMap();
		
		if("".equals(viewType)){
			result.put("增加單資料", 		"../ch/untch001f01.jsp");			//1
			result.put("基本資料", 		"../ch/untch001f02.jsp");			//2
		}else if("_query".equals(viewType)){
			result.put("財產明細", 		"../ch/untch001f02_1.jsp");			//2
		}else if("_maintenance".equals(viewType)){
			result.put("財產明細", 		"../ch/untch001f02_2.jsp");			//2
		}		
		
		String checkStr = this.getPropertyNoType();
		String linkStr = "";
		if(this._table_LA.equals(checkStr)){			linkStr = "../la/untla002f.jsp";
		}else if(this._table_BU.equals(checkStr)){		linkStr = "../bu/untbu002f.jsp";
		}else if(this._table_RF.equals(checkStr)){		linkStr = "../rf/untrf002f.jsp";
		}else if(this._table_VP.equals(checkStr)){		linkStr = "../vp/untvp001f.jsp";
		}else if(this._table_RT.equals(checkStr)){		linkStr = "../rt/untrt001f.jsp";
		}else if(this._table_MP.equals(checkStr)){
			if("_query".equals(viewType)){
				linkStr = "../mp/untmp005f_1.jsp";
			}else if("_maintenance".equals(viewType)){
				linkStr = "../mp/untmp005f_2.jsp";
			}else{
				linkStr = "../mp/untmp004f.jsp";
			}
		}
		result.put("......", 				linkStr);					//3
		
//		if("".equals(viewType)){
//			
//		}else if("_queryOrMaintenance".equals(viewType)){
//
//			result.put("折舊比例", 			"../ch/untch001f03.jsp");		//4
//			result.put("折舊紀錄", 			"../ch/untch001f05.jsp");		//5
//			
//			result.put("帳務資料", 			"../ch/untch001f04.jsp");		//6
//			result.put("移動紀錄", 			"../ch/untch001f06.jsp");		//7
//			
//		}
		
		return result;
	}
}


