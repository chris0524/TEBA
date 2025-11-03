package unt.ch;

import java.util.Set;
import java.util.HashSet;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.LinkedHashMap;
import java.util.Iterator;
import unt.ch.untch001f00.*;

public class UNTCH001_tab{

	public String _CH_ADD = "_CH_ADD";
	public String _CH_ADJUST = "_CH_ADJUST";
	public String _CH_REDUCE = "_CH_REDUCE";
	public String _CH_MOVE = "_CH_MOVE";
	public String _CH_DEAL = "_CH_DEAL";
	
	public String _LA_ADD = "_LA_ADD";
	
	public String _BU_ADD = "_BU_ADD";
	
	public String _RF_ADD = "_RF_ADD";
	
	public String _MP_ADD = "_MP_ADD";
	public String _MP_MOVE = "_MP_MOVE";
	public String _MP_DEAL = "_MP_DEAL";
	
	public String _VP_ADD = "_VP_ADD";
	public String _VP_ADJUST = "_VP_ADJUST";
	
	public String _RT_ADD = "_RT_ADD";
	public String _RT_ADJUST = "_RT_ADJUST";
	public String _RT_REDUCE = "_RT_REDUCE";
	
	//===============================================================
	private String viewType = "";
	public String _queryOrMaintenance = "_queryOrMaintenance";
	public String _query = "_query";
	public String _maintenance = "_maintenance";
	//===============================================================
	
	private String propertyNoType;	
	public String getPropertyNoType() {return propertyNoType;}
	public void setPropertyNoType(String propertyNoType) {this.propertyNoType = propertyNoType;}

	//===============================================================
	//增加單頁籤專用
	//區分需顯示財產增加單維護、財產查詢、還是財產維護頁籤
	public void _setupViewType(String s){	this.viewType = s;}
	
	//===============================================================

	//處理不同財產時所需要的頁籤資料
	public String _createTabData(String type, int i){		return doCreateTabDate(type, i);}
	
			//===============================================================
			private UNTCH001F00_Base doCheckWhichPropertyType(String type){
				UNTCH001F00_Base result = null;
				
				if(_CH_ADD.equals(type)){				UNTCH001F00_CH_ADD ch = new UNTCH001F00_CH_ADD();
														ch._setupViewType(viewType);
														result = ch;
				}else if(_CH_ADJUST.equals(type)){		result = new UNTCH001F00_CH_ADJUST();
				}else if(_CH_REDUCE.equals(type)){		result = new UNTCH001F00_CH_REDUCE();
				}else if(_CH_MOVE.equals(type)){		result = new UNTCH001F00_CH_MOVE();
				
				}else if(_LA_ADD.equals(type)){			UNTCH001F00_LA_ADD ch = new UNTCH001F00_LA_ADD();
														ch._setupViewType(viewType);
														result = ch;
				
				}else if(_BU_ADD.equals(type)){			UNTCH001F00_BU_ADD ch = new UNTCH001F00_BU_ADD();
														ch._setupViewType(viewType);
														result = ch;
				
				}else if(_RF_ADD.equals(type)){			UNTCH001F00_RF_ADD ch = new UNTCH001F00_RF_ADD();
														ch._setupViewType(viewType);
														result = ch;
				
				}else if(_MP_ADD.equals(type)){			UNTCH001F00_MP_ADD ch = new UNTCH001F00_MP_ADD();
														ch._setupViewType(viewType);
														result = ch;
				
				}else if(_MP_MOVE.equals(type)){		result = new UNTCH001F00_MP_MOVE();
				}else if(_MP_DEAL.equals(type)){		result = new UNTCH001F00_MP_DEAL();
								
				}else if(_VP_ADD.equals(type)){			UNTCH001F00_VP_ADD ch = new UNTCH001F00_VP_ADD();
														ch._setupViewType(viewType);
														result = ch;
														
				}else if(_VP_ADJUST.equals(type)){		result = new UNTCH001F00_VP_ADJUST();
				
				}else if(_RT_ADD.equals(type)){			UNTCH001F00_RT_ADD ch = new UNTCH001F00_RT_ADD();
														ch._setupViewType(viewType);
														result = ch;
														
				}else if(_RT_ADJUST.equals(type)){		result = new UNTCH001F00_RT_ADJUST();
				}else if(_RT_REDUCE.equals(type)){		result = new UNTCH001F00_RT_REDUCE();
				}
				return result;
			}
		
		
			
		//===============================================================
		//unable change part
		//===============================================================
		private String doCreateTabDate(String type, int tabNum){
			StringBuilder resultHead = new StringBuilder();
			StringBuilder resultTail = new StringBuilder();
			
			UNTCH001F00_Base uch = doCheckWhichPropertyType(type);			
			uch.setPropertyNoType(this.getPropertyNoType());
			
			List stars = uch.getData_forStart();
			Map map = uch.getData();
			
			Set limitLinkSet = null;
			Map limitLinks = uch.getData_forLimitLink();
			
			if(limitLinks.containsKey(tabNum)){
				int[] limitLinkValues = (int[])limitLinks.get(tabNum);
				limitLinkSet = new HashSet();
				for(int i:limitLinkValues){
					limitLinkSet.add(i);
				}
			}
			
			int i = 1;
			Iterator iter = map.keySet().iterator();
			while(iter.hasNext()){
				String str = iter.next().toString();
				
				if(i == tabNum){
					resultHead.append("<td ID=t").append(tabNum).append(" CLASS=\"tab_border1\" HEIGHT=\"25\">");					
					
					if(stars.contains(i)){	resultHead.append("*").append(str);
					}else{					resultHead.append(str);
					}
					resultHead.append("</td>");
										
					
					resultTail.append("<td class=\"tab_line1\"></td>");
					
					
				}else{
					resultHead.append("<td ID=t").append(tabNum).append(" CLASS=\"tab_border2\" >");
					
					if(limitLinkSet == null){				
						resultHead.append("<a href=\"#\" onClick=\"return checkURL('")
										.append(map.get(str))
										.append("');\">");
					}else{
						if(limitLinkSet.contains(i)){					
							resultHead.append("<a href=\"#\" onClick=\"return checkURL('")
										.append(map.get(str))
										.append("');\">");
						}
					}			
					
					if(stars.contains(i)){	resultHead.append("<font color=\"red\">*</font>").append(str);
					}else{					resultHead.append(str);
					}							
					resultHead.append("</a>").append("</td>");
					
					
					resultTail.append("<td class=\"tab_line2\"></td>");
					
					
				}
				i++;
			}
			
			return "<tr>" + resultHead.toString() + "</tr><tr>" + resultTail.toString() + "</tr>";
		}
		

}


