package unt.eg;

import util.*;

public class UNTEG001Q extends SuperBean2{
	
	private String q_engineeringNo;
	private String q_engineeringName;
	public String getQ_engineeringNo() {return checkGet(q_engineeringNo);}
	public void setQ_engineeringNo(String q_engineeringNo) {this.q_engineeringNo = checkSet(q_engineeringNo);}
	public String getQ_engineeringName() {return checkGet(q_engineeringName);}
	public void setQ_engineeringName(String q_engineeringName) {this.q_engineeringName = checkSet(q_engineeringName);}
	

	private String q_deprUnitCB;
	public String getQ_deprUnitCB() {return checkGet(q_deprUnitCB);}
	public void setQ_deprUnitCB(String q_deprUnitCB) {this.q_deprUnitCB = checkSet(q_deprUnitCB);}
	
	private String enterOrg;
	private String ownership;
	private String differenceKind;
	public String getEnterOrg() {return checkGet(enterOrg);}
	public void setEnterOrg(String enterOrg) {this.enterOrg = checkSet(enterOrg);}
	public String getOwnership() {return checkGet(ownership);}
	public void setOwnership(String ownership) {this.ownership = checkSet(ownership);}
	public String getDifferenceKind() {return checkGet(differenceKind);}
	public void setDifferenceKind(String differenceKind) {this.differenceKind = checkSet(differenceKind);}
}
