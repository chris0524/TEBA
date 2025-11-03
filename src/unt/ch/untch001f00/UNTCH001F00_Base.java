package unt.ch.untch001f00;

import java.util.List;
import java.util.Map;

public abstract class UNTCH001F00_Base extends unt.ch.UNTCH_Tools{
	public abstract List getData_forStart();			//設定顯示＊號的頁籤
	public abstract Map getData_forLimitLink();			//設定哪個頁籤要限制連結
	public abstract Map getData();						//設定頁籤名稱及連結
	
	private String propertyNoType;						//設定是哪一種財產
	public String getPropertyNoType() {return propertyNoType;}
	public void setPropertyNoType(String propertyNo) {this.propertyNoType = _checkPropertyNoType(propertyNo);}

	protected String viewType = "";
	//增加單頁籤專用
	//區分需顯示財產增加單維護、財產查詢、還是財產維護頁籤
	public void _setupViewType(String s){	this.viewType = s;}
}



