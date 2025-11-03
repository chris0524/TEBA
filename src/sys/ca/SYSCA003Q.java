package sys.ca;

import util.*;

public class SYSCA003Q extends SuperBean {
	

	String q_enterOrg;
	String q_enterOrgName;
	String q_unitNo;
	String q_unitName;
	String q_notes;	
	String q_incumbencyYN;
	String q_deprUnit;
	
	public String getQ_deprUnit() {	return checkGet(q_deprUnit);}
	public void setQ_deprUnit(String q_deprUnit) {	this.q_deprUnit = checkSet(q_deprUnit);}
	public String getQ_enterOrg(){ return checkGet(q_enterOrg); }
	public void setQ_enterOrg(String s){ q_enterOrg=checkSet(s); }
	public String getQ_enterOrgName(){ return checkGet(q_enterOrgName); }
	public void setQ_enterOrgName(String s){ q_enterOrgName=checkSet(s); }
	public String getQ_unitNo(){ return checkGet(q_unitNo); }
	public void setQ_unitNo(String s){ q_unitNo=checkSet(s); }
	public String getQ_unitName(){ return checkGet(q_unitName); }
	public void setQ_unitName(String s){ q_unitName=checkSet(s); }
	public String getQ_notes(){ return checkGet(q_notes); }
	public void setQ_notes(String s){ q_notes=checkSet(s); }
	public String getQ_incumbencyYN() {	return checkGet(q_incumbencyYN);}
	public void setQ_incumbencyYN(String q_incumbencyYN) {	this.q_incumbencyYN = checkSet(q_incumbencyYN);}


	String isOrganManager;
	String isAdminManager;
	String organID;    
	public String getOrganID() { return checkGet(organID); }
	public void setOrganID(String s) { organID = checkSet(s); }
	public String getIsOrganManager() { return checkGet(isOrganManager); }
	public void setIsOrganManager(String s) { isOrganManager = checkSet(s); }
	public String getIsAdminManager() { return checkGet(isAdminManager); }
	public void setIsAdminManager(String s) { isAdminManager = checkSet(s); }   
	
}
