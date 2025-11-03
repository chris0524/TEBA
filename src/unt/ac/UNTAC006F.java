package unt.ac;

import util.*;

public class UNTAC006F extends UNTAC005F{

	//=================================================
	public void calculateBalanceInMonth(){
		this.setRestoration2ID(getUserID());
		this.setRestoration2Name(getUserName());
		this.setRestoration2Date(Datetime.getYYYMMDD());
		
		execCalculateBalanceInMonth(false);
		setErrorMsg("關帳回復執行完成");
	}
	//=================================================
}