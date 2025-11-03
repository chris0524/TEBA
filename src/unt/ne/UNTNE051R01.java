/*
*<br>程式目的：非消耗品標籤查詢檔 
*<br>程式代號：untne007r
*<br>撰寫日期：94/11/28
*<br>程式作者：Cherry
*<br>--------------------------------------------------------
*<br>修改作者　　修改日期　　　修改目的
*<br>--------------------------------------------------------
*/

package unt.ne;

import java.sql.ResultSet;
import java.util.Vector;

import javax.swing.table.DefaultTableModel;

import util.Common;
import util.Database;
import util.Datetime;
import util.SuperBean;

public class UNTNE051R01 extends SuperBean{
	String q_labelType;
	String q_enterOrg;
	String q_enterOrgName;
	String q_ownership;
	String q_closing;
	String q_verify;
	String q_caseNoS;
	String q_caseNoE;
	String q_enterDateS;
	String q_enterDateE;
	String q_writeDateS;
	String q_writeDateE;
	String q_proofDoc;
	String q_proofYear;
	String q_proofNoS;
	String q_proofNoE;
	String q_propertyNoS;
	String q_propertyNoE;
	String q_propertyNoSName;
	String q_propertyNoEName;
	String q_dataState;
	String q_propertyKind;
	String q_fundType;
	String q_valuable;
	String q_serialNoS;
	String q_serialNoE;
	String q_keepUnit;
	String q_keeper;
	String q_useUnit;
	String q_userNo;
	String q_blankSpace;
	String q_printKeeper;
	String q_printType;
	String q_place;
	String q_place1;
	String q_place1Name;
	String q_userNote;
	String q_caseSerialNoS;
	String q_caseSerialNoE;
	String orderby1;
	String orderby2;
	String q_printSerial;
	private String q_reportType;						// 報表類型
	
	public String getQ_reportType() { return checkGet(q_reportType); }
	public void setQ_reportType(String q_reportType) { this.q_reportType = checkGet(q_reportType); }
	public String getQ_printSerial() { return checkGet(q_printSerial); }
	public void setQ_printSerial(String q_printSerial) { this.q_printSerial = checkSet(q_printSerial); }
	public String getQ_printType(){ return checkGet(q_printType); }
	public void setQ_printType(String s){ q_printType = checkSet(s); }
	public String getQ_enterOrg(){ return checkGet(q_enterOrg); }
	public void setQ_enterOrg(String s){ q_enterOrg=checkSet(s); }
	public String getQ_enterOrgName(){ return checkGet(q_enterOrgName); }
	public void setQ_enterOrgName(String s){ q_enterOrgName=checkSet(s); }
	public String getQ_ownership(){ return checkGet(q_ownership); }
	public void setQ_ownership(String s){ q_ownership=checkSet(s); }
	public String getQ_closing(){ return checkGet(q_closing); }
	public void setQ_closing(String s){ q_closing=checkSet(s); }
	public String getQ_verify(){ return checkGet(q_verify); }
	public void setQ_verify(String s){ q_verify=checkSet(s); }
	public String getQ_caseNoS(){ return checkGet(q_caseNoS); }
	public void setQ_caseNoS(String s){ q_caseNoS=checkSet(s); }
	public String getQ_caseNoE(){ return checkGet(q_caseNoE); }
	public void setQ_caseNoE(String s){ q_caseNoE=checkSet(s); }
	public String getQ_enterDateS(){ return checkGet(q_enterDateS); }
	public void setQ_enterDateS(String s){ q_enterDateS=checkSet(s); }
	public String getQ_enterDateE(){ return checkGet(q_enterDateE); }
	public void setQ_enterDateE(String s){ q_enterDateE=checkSet(s); }
	public String getQ_writeDateS(){ return checkGet(q_writeDateS); }
	public void setQ_writeDateS(String s){ q_writeDateS=checkSet(s); }
	public String getQ_writeDateE(){ return checkGet(q_writeDateE); }
	public void setQ_writeDateE(String s){ q_writeDateE=checkSet(s); }
	public String getQ_proofDoc(){ return checkGet(q_proofDoc); }
	public void setQ_proofDoc(String s){ q_proofDoc=checkSet(s); }
	public String getQ_proofYear(){ return checkGet(q_proofYear); }
	public void setQ_proofYear(String s){ q_proofYear=checkSet(s); }
	public String getQ_proofNoS(){ return checkGet(q_proofNoS); }
	public void setQ_proofNoS(String s){ q_proofNoS=checkSet(s); }
	public String getQ_proofNoE(){ return checkGet(q_proofNoE); }
	public void setQ_proofNoE(String s){ q_proofNoE=checkSet(s); }
    public String getQ_propertyNoS(){ return checkGet(q_propertyNoS); }
    public void setQ_propertyNoS(String s){ q_propertyNoS=checkSet(s); }
    public String getQ_propertyNoE(){ return checkGet(q_propertyNoE); }
    public void setQ_propertyNoE(String s){ q_propertyNoE=checkSet(s); }
    public String getQ_propertyNoSName(){ return checkGet(q_propertyNoSName); }
    public void setQ_propertyNoSName(String s){ q_propertyNoSName=checkSet(s); }
    public String getQ_propertyNoEName(){ return checkGet(q_propertyNoEName); }
    public void setQ_propertyNoEName(String s){ q_propertyNoEName=checkSet(s); }    
    public String getQ_dataState(){ return checkGet(q_dataState); }
    public void setQ_dataState(String s){ q_dataState=checkSet(s); }    
    public String getQ_propertyKind(){ return checkGet(q_propertyKind); }
    public void setQ_propertyKind(String s){ q_propertyKind=checkSet(s); }
    public String getQ_fundType(){ return checkGet(q_fundType); }
    public void setQ_fundType(String s){ q_fundType=checkSet(s); } 
    public String getQ_valuable(){ return checkGet(q_valuable); }
    public void setQ_valuable(String s){ q_valuable=checkSet(s); }
    public String getQ_serialNoS(){ return checkGet(q_serialNoS); }
    public void setQ_serialNoS(String s){ q_serialNoS=checkSet(s); }
    public String getQ_serialNoE(){ return checkGet(q_serialNoE); }
    public void setQ_serialNoE(String s){ q_serialNoE=checkSet(s); }
    public String getQ_keepUnit(){ return checkGet(q_keepUnit); }
    public void setQ_keepUnit(String s){ q_keepUnit=checkSet(s); }
    public String getQ_keeper(){ return checkGet(q_keeper); }
    public void setQ_keeper(String s){ q_keeper=checkSet(s); }
    public String getQ_useUnit(){ return checkGet(q_useUnit); }
    public void setQ_useUnit(String s){ q_useUnit=checkSet(s); }
    public String getQ_userNo(){ return checkGet(q_userNo); }
    public void setQ_userNo(String s){ q_userNo=checkSet(s); }
    public String getQ_blankSpace(){ return checkGet(q_blankSpace); }
    public void setQ_blankSpace(String s){ q_blankSpace=checkSet(s); }
	public String getQ_printKeeper(){ return checkGet(q_printKeeper); }
	public void setQ_printKeeper(String s){ q_printKeeper=checkSet(s); }
	public String getQ_place() {return checkGet(q_place);}
	public void setQ_place(String qPlace) {q_place = checkSet(qPlace);}
	public String getQ_place1() {return checkGet(q_place1);}
	public void setQ_place1(String qPlace1) {q_place1 = checkSet(qPlace1);}
	public String getQ_place1Name() {return checkGet(q_place1Name);}
	public void setQ_place1Name(String q_place1Name) {q_place1Name = checkSet(q_place1Name);}
	
	
	
	public String getQ_userNote() {return checkGet(q_userNote);}
	public void setQ_userNote(String qUserNote) {q_userNote = checkSet(qUserNote);}
	public String getQ_caseSerialNoS() {return checkGet(q_caseSerialNoS);}
	public void setQ_caseSerialNoS(String q_caseSerialNoS) {this.q_caseSerialNoS = checkSet(q_caseSerialNoS);}
	public String getQ_caseSerialNoE() {return checkGet(q_caseSerialNoE);}
	public void setQ_caseSerialNoE(String q_caseSerialNoE) {this.q_caseSerialNoE = checkSet(q_caseSerialNoE);}
	public String getOrderby1() {return checkGet(orderby1);}
	public void setOrderby1(String orderby1) {this.orderby1 = checkSet(orderby1);}
	public String getOrderby2() {return checkGet(orderby2);}
	public void setOrderby2(String orderby2) {this.orderby2 = checkSet(orderby2);}

	String q_differenceKind;
	public String getQ_differenceKind(){ return checkGet(q_differenceKind); }
	public void setQ_differenceKind(String s){ q_differenceKind=checkSet(s); }
	
	String q_propertyName1;
	public String getQ_propertyName1(){ return checkGet(q_propertyName1); }
	public void setQ_propertyName1(String s){ q_propertyName1=checkSet(s); }
	
	String isOrganManager;
	String isAdminManager;
	String organID;    
	public String getOrganID() { return checkGet(organID); }
	public void setOrganID(String s) { organID = checkSet(s); }
	public String getIsOrganManager() { return checkGet(isOrganManager); }
	public void setIsOrganManager(String s) { isOrganManager = checkSet(s); }
	public String getIsAdminManager() { return checkGet(isAdminManager); }
	public void setIsAdminManager(String s) { isAdminManager = checkSet(s); }  

	String q_workKind;
	public String getQ_workKind(){ return checkGet(q_workKind); }
	public void setQ_workKind(String s){ q_workKind=checkSet(s); }
	
	String q_keepBureau;
	String q_useBureau;
	public String getQ_keepBureau(){ return checkGet(q_keepBureau); }
	public void setQ_keepBureau(String s){ q_keepBureau=checkSet(s); }
	public String getQ_useBureau(){ return checkGet(q_useBureau); }
	public void setQ_useBureau(String s){ q_useBureau=checkSet(s); }

	String q_buyDateS;
	String q_buyDateE;
	public String getQ_buyDateS(){ return checkGet(q_buyDateS); }
	public void setQ_buyDateS(String s){ q_buyDateS=checkSet(s); }
	public String getQ_buyDateE(){ return checkGet(q_buyDateE); }
	public void setQ_buyDateE(String s){ q_buyDateE=checkSet(s); }
	
	private String getOrderbySQL() {
		StringBuilder orderby = new StringBuilder();
		
		// 排序條件1
		if ("1".equals(getOrderby1())) {
			if (!"1".equals(getQ_printSerial())) {
				orderby.append(" order by c.differencekind desc,b.propertyno desc,c.serialno desc ");
			} else {
				orderby.append(" order by c.differencekind, b.propertyno, c.serialno ");
			}
		} else if ("2".equals(getOrderby1())) {
			if ("n".equals(getQ_workKind())){
				if (!"1".equals(getQ_printSerial())) {
					orderby.append(" order by o.newkeeper desc ");
				} else {
					orderby.append(" order by o.newkeeper ");
				}
			} else {
				if (!"1".equals(getQ_printSerial())) {
					orderby.append(" order by c.keeper desc ");
				} else {
					orderby.append(" order by c.keeper ");
				}
			}
		} else if ("3".equals(getOrderby1())) {
			if ("n".equals(getQ_workKind())){
				if (!"1".equals(getQ_printSerial())) {
					orderby.append(" order by o.newkeepunit desc ");
				} else {
					orderby.append(" order by o.newkeepunit ");
				}
			} else {
				if (!"1".equals(getQ_printSerial())) {
					orderby.append(" order by c.keepunit desc ");
				} else {
					orderby.append(" order by c.keepunit ");
				}
			}
		} else if ("4".equals(getOrderby1())) {
			if ("n".equals(getQ_workKind())){
				if (!"1".equals(getQ_printSerial())) {
					orderby.append(" order by o.newplace1 desc ");
				} else {
					orderby.append(" order by o.newplace1 ");		//移動單(n)使用新存置地點代碼排序
				}
			} else {
				if (!"1".equals(getQ_printSerial())) {
					orderby.append(" order by c.place1 desc ");
				} else {
					orderby.append(" order by c.place1 ");				
				}
			}
		} 
		
		// 排序條件2
		if (!"".equals(getOrderby1())&&"1".equals(getOrderby2())&&!getOrderby1().equals(getOrderby2())) {
			if (!"1".equals(getQ_printSerial())) {
				orderby.append(" ,c.differencekind desc, b.propertyno desc, c.serialno desc ");
			} else {
				orderby.append(" ,c.differencekind, b.propertyno, c.serialno ");
			}
		} else if (!"".equals(getOrderby1())&&"2".equals(getOrderby2())&&!getOrderby1().equals(getOrderby2())) {
			if("n".equals(getQ_workKind())){
				if (!"1".equals(getQ_printSerial())) {
					orderby.append(" ,o.newkeeper desc ");	
				} else {
					orderby.append(" ,o.newkeeper ");	
				}
			}else{
				if (!"1".equals(getQ_printSerial())) {
					orderby.append(" ,c.keeper desc ");
				} else {
					orderby.append(" ,c.keeper ");
				}
			}
		} else if (!"".equals(getOrderby1())&&"3".equals(getOrderby2())&&!getOrderby1().equals(getOrderby2())) {			
			if("n".equals(getQ_workKind())){
				if (!"1".equals(getQ_printSerial())) {
					orderby.append(" ,o.newkeepunit desc ");
				} else {
					orderby.append(" ,o.newkeepunit ");
				}
			}else{
				if (!"1".equals(getQ_printSerial())) {
					orderby.append(" ,c.keepunit desc ");
				} else {
					orderby.append(" ,c.keepunit ");
				}
			}
		} else if (!"".equals(getOrderby1())&&"4".equals(getOrderby2())&&!getOrderby1().equals(getOrderby2())) {
			if("n".equals(getQ_workKind())){
				if (!"1".equals(getQ_printSerial())) {
					orderby.append(" ,o.newplace1 desc ");		//移動單(n)使用新存置地點代碼排序
				} else {
					orderby.append(" ,o.newplace1 ");		//移動單(n)使用新存置地點代碼排序
				}
			}else{
				if (!"1".equals(getQ_printSerial())) {
					orderby.append(" ,c.place1 desc ");
				} else {
					orderby.append(" ,c.place1 ");
				}
			}
		}
		
		if (orderby.length() == 0) {
			if (!"1".equals(getQ_printSerial())) {
				orderby.append(" order by b.enterorg desc, b.ownership desc, c.keepunit desc, c.keeper desc, c.differencekind desc, b.propertyno desc, c.serialno desc ");
			} else {
				orderby.append(" order by b.enterorg, b.ownership, c.keepunit, c.keeper, c.differencekind, b.propertyno, c.serialno ");
			}
		} else {
			// 不管前面選什麼  最後一定要是 區分別+財編+分號
			if (orderby.indexOf("c.differencekind") == -1) {
				if (!"1".equals(getQ_printSerial())) {
					orderby.append(",c.differencekind desc ");
				} else {
					orderby.append(",c.differencekind");
				}
			}
			if (orderby.indexOf("b.propertyno") == -1) {
				if (!"1".equals(getQ_printSerial())) {
					orderby.append(",b.propertyno desc ");
				} else {
					orderby.append(",b.propertyno");
				}
			}
			
			if (orderby.indexOf("c.serialno") == -1) {
				if (!"1".equals(getQ_printSerial())) {
					orderby.append(",c.serialno desc ");
				} else {
					orderby.append(",c.serialno");
				}
			}
		}
		
		return orderby.toString();
	}
	
	private String getSQLForOtherType() {
		String enterDateName = "";
		String execSQL="select distinct b.enterorg, b.ownership, c.keepunit, c.keeper, c.differencekind, b.propertyno, c.serialno,  d.organaname as enterorgname, b.buydate, b.sourcedate, b.specification,  c.place ," ;
		execSQL +=" c.place1 ,";	//1041029 存置地點排序用
    	if("n".equals(getQ_workKind())){
    		execSQL += " o.newkeeper,(select u.keepername from UNTMP_KEEPER u where u.enterorg = c.enterorg  and u.keeperno = o.newkeeper ) as keeperName,";
    	} else {
    		execSQL += " (select u.keepername from UNTMP_KEEPER u where u.enterorg = c.enterorg  and u.keeperno = c.keeper ) as keeperName,";
    	}
    		execSQL += " (select ul.placename from SYSCA_PLACE ul where ul.enterorg = c.enterorg  and ul.placeno = c.place1 )as placeName" +"\n"+
    					" ,  c.bookvalue, " +"\n"+
    					" (select z.codename from SYSCA_CODE z where 1=1 and (z.codekindid='owa' or z.codekindid='OWA') and a.ownership=z.codeid) as ownershipname, " +"\n"+
    					" case b.propertykind when '01' then '公務用' when '02' then '公共用' when '03' then '事業用' when '04' then '非公用' else null end as propertykindname, " +"\n"+
//    					" (select e.limityear from SYSPK_PROPERTYCODE2 e where b.enterorg = e.enterorg and b.propertyno = e.propertyno) as limityear, " +
    					"  b.otherlimityear " +"\n";
    		
		if ("n".equals(this.getQ_workKind())) {
			execSQL += " ,o.newkeepunit, (select k.unitname from UNTMP_KEEPUNIT k where k.enterorg = o.enterorg and k.unitno = o.newkeepunit) as newkeepunitname " +
						" , (select i.placename from SYSCA_PLACE i where i.enterorg=o.enterorg and i.placeno=o.newplace1 ) as newplace1name " +
					    " , o.newplace1, o.propertyname1 ";
		} else {
			execSQL += ", c.propertyname1";
		}

			execSQL += " , (select e.propertyname from SYSPK_PROPERTYCODE2 e where b.enterorg = e.enterorg and b.propertyno = e.propertyno) as propertyname, " +"\n"+
    					" (select f.keepername  from UNTMP_KEEPER f where b.enterorg=f.enterorg and c.keepunit=f.keeperno and c.keeper=f.keeperno   ) as keepername1,   " +"\n"+
    					" (select p.unitname  from UNTMP_KEEPUNIT p where c.enterorg = p.enterorg and c.keepunit = p.unitno  ) as unitname,   "+"\n"+
    					" (select k.unitname from UNTMP_KEEPUNIT k where k.enterorg = c.enterorg and k.unitno = c.keepunit) as keepunitname, " +
    					" (select u.keepername from UNTMP_KEEPER u where u.enterorg = c.enterorg and u.keeperno = c.keepunit and u.keeperno = c.keeper ) as keepername2 " +    		
    					" from UNTNE_ADDPROOF a, UNTNE_NONEXP b, UNTNE_NONEXPDETAIL c " +
    					" left join UNTNE_ADJUSTDETAIL k join UNTNE_ADJUSTPROOF j on j.enterorg=k.enterorg and j.ownership=k.ownership and j.caseno=k.caseno on k.enterorg=c.enterorg and k.ownership=c.ownership and k.propertyno=c.propertyno and k.differencekind=c.differencekind and k.serialno=c.serialno" +"\n";
    					
		if ("n".equals(this.getQ_workKind())) {
			execSQL += " left join UNTNE_MOVEDETAIL o join UNTNE_MOVEPROOF n on n.enterorg=o.enterorg and n.ownership=o.ownership and n.caseno=o.caseno on o.enterorg=c.enterorg and o.ownership=c.ownership and o.propertyno=c.propertyno and o.differencekind=c.differencekind and o.serialno=c.serialno"+"\n";
		}
    					
    		execSQL += " , SYSCA_ORGAN d"+"\n"+
						" where 1=1 and a.enterorg = d.organid " +"\n"+
						" and a.caseno=b.caseno and a.enterorg=b.enterorg and a.ownership=b.ownership " +"\n"+
						" and c.enterorg=b.enterorg and c.ownership=b.ownership and b.lotno=c.lotno and b.differencekind = c.differencekind and b.propertyno=c.propertyno " +"\n"+
						"";
    	/**
    	 * 當作業種類(q_workKind)選擇"增加單"時則帶入"a"，因table為"a"
    	 *                     選擇"增減值單"時則帶入"j"，因table為"j"
    	 *                     選擇"減損單"時則帶入"l"，因table為"l"
    	 *                     選擇"移動單"時則帶入"n"，因table為"n"
    	 */
    	if("a".equals(getQ_workKind())){
    		enterDateName = "enterdate";
    	}else if("j".equals(getQ_workKind())){
    		enterDateName = "adjustdate";
    	}else if("l".equals(getQ_workKind())){
    		enterDateName = "reducedate";
    	}else if("n".equals(getQ_workKind())){
    		enterDateName = "movedate";
    	}
    	if (!"".equals(getQ_enterOrg())) {
			execSQL +=" and a.enterorg = " + Common.sqlChar(getQ_enterOrg()) ;
		} else {
			if (!getIsAdminManager().equalsIgnoreCase("Y")) {
				if ("Y".equals(getIsOrganManager())) {					
					execSQL += " and ( a.enterorg = "+ Common.sqlChar(getOrganID()) +" or a.enterorg in ( select organid from SYSCA_ORGAN where organsuperior = "+ Common.sqlChar(getOrganID()) +" ) ) ";
				} else {
					execSQL +=" and a.enterorg = " + Common.sqlChar(getOrganID()) ;
				}
			}
		}

    	if (!"".equals(Common.get(getQ_dataState()))){
    		execSQL +=" and b.datastate = " + util.Common.sqlChar(getQ_dataState());
			execSQL +=" and c.datastate = " + util.Common.sqlChar(getQ_dataState());
    	}
		if (!"".equals(Common.get(getQ_ownership()))){
    		execSQL +=" and a.ownership = " + util.Common.sqlChar(getQ_ownership());
    	}
		if (!"".equals(getQ_caseNoS()))
			execSQL +=" and "+getQ_workKind()+".caseno >= " + Common.sqlChar(Common.formatRearString(getQ_caseNoS(),10,'0'));		
		if (!"".equals(getQ_caseNoE()))
			execSQL +=" and "+getQ_workKind()+".caseno <= " + Common.sqlChar(Common.formatRearString(getQ_caseNoE(),10, '9'));
    	if (!"".equals(Common.get(getQ_writeDateS())))
    		execSQL += " and "+getQ_workKind()+".writedate >= " + util.Common.sqlChar(new Datetime().changeTaiwanYYMMDD(getQ_writeDateS(),"2"));
    	if (!"".equals(Common.get(getQ_writeDateE())))
    		execSQL += " and "+getQ_workKind()+".writedate <= " + util.Common.sqlChar(new Datetime().changeTaiwanYYMMDD(getQ_writeDateE(),"2"));
    	if (!"".equals(Common.get(getQ_buyDateS())))
    		execSQL += " and b.buydate >= " + util.Common.sqlChar(new Datetime().changeTaiwanYYMMDD(getQ_buyDateS(),"2"));
    	if (!"".equals(Common.get(getQ_buyDateE())))
    		execSQL += " and b.buydate <= " + util.Common.sqlChar(new Datetime().changeTaiwanYYMMDD(getQ_buyDateE(),"2"));
    	
    	if (!"".equals(getQ_proofYear())) 
			execSQL += " and "+getQ_workKind()+".proofyear = " + Common.sqlChar(getQ_proofYear());		
		if (!"".equals(getQ_proofDoc()))
			execSQL += " and "+getQ_workKind()+".proofdoc like '%" + getQ_proofDoc() + "%'" ;
		if (!"".equals(getQ_proofNoS())) 
			execSQL += " and "+getQ_workKind()+".proofno >= " + Common.sqlChar(getQ_proofNoS());		
		if (!"".equals(getQ_proofNoE())) 
			execSQL += " and "+getQ_workKind()+".proofno <= " + Common.sqlChar(getQ_proofNoE());		 
		if (!"".equals(getQ_enterDateS()))
		    execSQL+=" and "+getQ_workKind()+"." + enterDateName + " >= " + Common.sqlChar(new Datetime().changeTaiwanYYMMDD(getQ_enterDateS(),"2"));
		if (!"".equals(getQ_enterDateE()))
		    execSQL+=" and "+getQ_workKind()+"." + enterDateName + " <= " + Common.sqlChar(new Datetime().changeTaiwanYYMMDD(getQ_enterDateE(),"2"));
		if(!"".equals(getQ_differenceKind()))
			execSQL+=" and b.differencekind = " + Common.sqlChar(getQ_differenceKind()) ;
		if (!"".equals(getQ_propertyNoS()))
		    execSQL+=" and b.propertyno >= " + Common.sqlChar(getQ_propertyNoS());		
		if (!"".equals(getQ_propertyNoE()))
		    execSQL+=" and b.propertyno <= " + Common.sqlChar(getQ_propertyNoE());						
		if (!"".equals(getQ_propertyKind()))
		    execSQL+=" and b.propertykind = " + Common.sqlChar(getQ_propertyKind()) ;
		if (!"".equals(getQ_serialNoS()))
		    execSQL+=" and c.serialno >= " + Common.sqlChar(getQ_serialNoS());		
		if (!"".equals(getQ_serialNoE()))
		    execSQL+=" and c.serialno <= " + Common.sqlChar(getQ_serialNoE());			
		if (!"".equals(getQ_keepUnit())){
			if("n".equals(getQ_workKind())){
				execSQL+=" and o.newkeepunit = " + Common.sqlChar(getQ_keepUnit()) ;
			}else{
				execSQL+=" and c.keepunit = " + Common.sqlChar(getQ_keepUnit()) ;
			}		
		}
		if (!"".equals(getQ_keeper())){
			if("n".equals(getQ_workKind())){
				execSQL+=" and o.newkeeper = " + Common.sqlChar(getQ_keeper()) ;
			}else{
				execSQL+=" and c.keeper = " + Common.sqlChar(getQ_keeper()) ;
			}
		}
		if (!"".equals(getQ_useUnit()))
			if("n".equals(getQ_workKind())){
				execSQL+=" and o.newuseunit = " + Common.sqlChar(getQ_useUnit()) ;
			}else{
				execSQL+=" and c.useunit = " + Common.sqlChar(getQ_useUnit()) ;
			}
		if (!"".equals(getQ_userNo())){
			if("n".equals(getQ_workKind())){
				execSQL+=" and o.newuserno = " + Common.sqlChar(getQ_userNo()) ;
			}else{
				execSQL+=" and c.userno = " + Common.sqlChar(getQ_userNo()) ;
			}
		}
		if(!"".equals(getQ_userNote()))
			execSQL+=" and c.usernote like " + Common.sqlChar("%" + getQ_userNote() + "%");
		if (!"".equals(getQ_propertyName1()))
			execSQL+=" and b.propertyname1 like " + Common.sqlChar("%"+getQ_propertyName1()+"%") ;	
		
		if(!"".equals(getQ_place1())){
			if("n".equals(getQ_workKind())){
				execSQL+=" and o.newplace1 = " + Common.sqlChar(getQ_place1());		
			}else{
				execSQL+=" and c.place1 = " + Common.sqlChar(getQ_place1());
			}
		}
		if(!"".equals(getQ_place())){
			if("n".equals(getQ_workKind())){
				execSQL+=" and o.newplace like " + Common.sqlChar("%" + getQ_place() + "%");
			}else{
				execSQL+=" and c.place like " + Common.sqlChar("%" + getQ_place() + "%");
			}
		}
		if (!"".equals(getQ_caseSerialNoS())) {
			execSQL+=" and o.caseserialno >= " + Common.sqlChar(getQ_caseSerialNoS());
		}
		if (!"".equals(getQ_caseSerialNoE())) {
			execSQL+=" and o.caseserialno <= " + Common.sqlChar(getQ_caseSerialNoE());
		}
		
		execSQL += this.getOrderbySQL();
		return execSQL;
	}
	
	private String getSQLForTypeD() {
		StringBuilder sql = new StringBuilder();
		sql.append(" select distinct b.enterorg, b.ownership, c.keepunit, c.keeper, c.differencekind, b.propertyno, c.serialno,  d.organaname as enterorgname, b.buydate, b.sourcedate, b.specification, c.place ")
		   .append(" ,(select u.keepername from UNTMP_KEEPER u where u.enterorg = c.enterorg  and u.keeperno = c.keeper ) as keeperName")
 		   .append(" ,(select ul.placename from SYSCA_PLACE ul where ul.enterorg = c.enterorg  and ul.placeno = c.place1 )as placeName")
		   .append(" ,c.place1")	//1041029 存置地點排序用
 		   .append(" ,c.bookvalue, c.propertyname1")
 		   .append(" ,(select z.codename from SYSCA_CODE z where 1=1 and (z.codekindid='owa' or z.codekindid='OWA') and c.ownership=z.codeid) as ownershipname")
 		   .append(" ,case b.propertykind when '01' then '公務用' when '02' then '公共用' when '03' then '事業用' when '04' then '非公用' else null end as propertykindname")
 		   .append(" ,b.otherlimityear")
 		   .append(" ,(select e.propertyname from SYSPK_PROPERTYCODE2 e where b.enterorg = e.enterorg and b.propertyno = e.propertyno) as propertyname")
 		   .append(" ,(select f.keepername  from UNTMP_KEEPER f where b.enterorg=f.enterorg and c.keepunit=f.keeperno and c.keeper=f.keeperno) as keepername1")
 		   .append(" ,(select p.unitname  from UNTMP_KEEPUNIT p where c.enterorg = p.enterorg and c.keepunit = p.unitno) as unitname")
 		   .append(" ,(select k.unitname from UNTMP_KEEPUNIT k where k.enterorg = c.enterorg and k.unitno = c.keepunit) as keepunitname")
 		   .append(" ,(select u.keepername from UNTMP_KEEPER u where u.enterorg = c.enterorg and u.keeperno = c.keepunit and u.keeperno = c.keeper ) as keepername2")
 		   .append(" from UNTNE_NONEXP b, UNTNE_NONEXPDETAIL c ,SYSCA_ORGAN d")
 		   .append(" where 1=1 ")
 		   .append(" and c.enterorg = d.organid and c.verify='Y' ")
 		   .append(" and c.enterorg=b.enterorg and c.ownership=b.ownership and b.lotno=c.lotno and b.differencekind = c.differencekind and b.propertyno=c.propertyno");
		
    	if (!"".equals(getQ_enterOrg())) {
    		sql.append(" and c.enterorg = ").append(Common.sqlChar(getQ_enterOrg()));
		} else {
			if (!getIsAdminManager().equalsIgnoreCase("Y")) {
				if ("Y".equals(getIsOrganManager())) {					
					sql.append(" and ( c.enterorg = ").append(Common.sqlChar(getOrganID()))
					   .append("  or c.enterorg in ( select organid from SYSCA_ORGAN where organsuperior = ")
					   .append(Common.sqlChar(getOrganID())).append("))");
				} else {
					sql.append(" and c.enterorg = ").append(Common.sqlChar(getOrganID()));
				}
			}
		}

    	if (!"".equals(Common.get(getQ_dataState()))){
			sql.append(" and b.datastate = ").append(Common.sqlChar(getQ_dataState()));
			sql.append(" and c.datastate = ").append(Common.sqlChar(getQ_dataState()));
    	}
		if (!"".equals(Common.get(getQ_ownership()))){
			sql.append(" and c.ownership = ").append(Common.sqlChar(getQ_ownership()));
    	}
    	if (!"".equals(Common.get(getQ_buyDateS()))) {
    		sql.append("  and b.buydate >= ").append(Common.sqlChar(new Datetime().changeTaiwanYYMMDD(getQ_buyDateS(),"2")));
    	}
    	if (!"".equals(Common.get(getQ_buyDateE()))) {
    		sql.append("  and b.buydate <= ").append(Common.sqlChar(new Datetime().changeTaiwanYYMMDD(getQ_buyDateE(),"2")));
    	}
    	
		if (!"".equals(getQ_differenceKind())) {
			sql.append(" and b.differencekind = ").append(Common.sqlChar(getQ_differenceKind()));
		}
		if (!"".equals(getQ_propertyNoS())) {
			sql.append(" and b.propertyno >= ").append(Common.sqlChar(getQ_propertyNoS()));
		}
		if (!"".equals(getQ_propertyNoE())) {
			sql.append(" and b.propertyno <= ").append(Common.sqlChar(getQ_propertyNoE()));
		}
		if (!"".equals(getQ_propertyKind())) {
			sql.append(" and b.propertykind = ").append(Common.sqlChar(getQ_propertyKind()));
		}
		if (!"".equals(getQ_serialNoS())) {
			sql.append(" and c.serialno >= ").append(Common.sqlChar(getQ_serialNoS()));
		}
		if (!"".equals(getQ_serialNoE())) {
			sql.append(" and c.serialno <= ").append(Common.sqlChar(getQ_serialNoE()));
		}
		if (!"".equals(getQ_keepUnit())) {
			sql.append(" and c.keepunit = ").append(Common.sqlChar(getQ_keepUnit()));
		}
		if (!"".equals(getQ_keeper())) {
			sql.append(" and c.keeper = ").append(Common.sqlChar(getQ_keeper()));
		}
		if (!"".equals(getQ_useUnit())) {
			sql.append(" and c.useunit = ").append(Common.sqlChar(getQ_useUnit()));
		}
		if (!"".equals(getQ_userNo())) {
			sql.append(" and c.userno = ").append(Common.sqlChar(getQ_userNo()));
		}
		if(!"".equals(getQ_userNote())) {
			sql.append(" and c.usernotelike= ").append(Common.sqlChar("%" + getQ_userNote() + "%"));
		}
		if (!"".equals(getQ_propertyName1())) {
			sql.append(" and b.propertyname1 like ").append(Common.sqlChar("%"+getQ_propertyName1()+"%"));
		}
		if (!"".equals(getQ_enterDateS())) {
			sql.append(" and b.enterdate >= ").append( Common.sqlChar(new Datetime().changeTaiwanYYMMDD(getQ_enterDateS(),"2")));
		}
		if (!"".equals(getQ_enterDateE())) {
			sql.append(" and b.enterdate <= ").append( Common.sqlChar(new Datetime().changeTaiwanYYMMDD(getQ_enterDateE(),"2")));
		}

		if (!"".equals(getQ_place1())) {
			sql.append(" and c.place1 = ").append(Common.sqlChar(getQ_place1()));
		}
		if (!"".equals(getQ_place())) {
			sql.append(" and c.place like ").append(Common.sqlChar("%" + getQ_place() + "%"));
		}
		
		sql.append(this.getOrderbySQL());
		return sql.toString();
	}
	
	private String getSQL() {
		if ("d".equals(this.getQ_workKind())) {
			return this.getSQLForTypeD();
		} else {
			return this.getSQLForOtherType();
		}
	}
	
	public DefaultTableModel getResultModel() throws Exception{
	
	    DefaultTableModel model = new javax.swing.table.DefaultTableModel();
	    Database db = new Database();
		Object[] data ;
		Vector rowData = new Vector();
		//int i;
	    try{
	    	String[] columns = new String[] {"propertyKindName","enterOrgName","ownershipName","propertyNo"
					,"limitYear","propertyname","propertyname1","buyDate","specification","keepUnitName"
					,"place","keeperName"
					};
	
	    	
	    	String sql = this.getSQL();
			//System.out.println("execSQL:"+sql);
	        ResultSet rs = db.querySQL_NoChange(sql);
	        while(rs.next()){
	        	data = new Object[columns.length];
	        	data[0] = Common.get(rs.getString("propertyKindName"));//propertyKindName
	        	data[1] = Common.get(rs.getString("enterOrgName"));//enterOrgName
	        	data[2] = Common.get(rs.getString("ownershipName"));//ownershipName
	        	data[3] = "財產編號:" + Common.get(rs.getString("differencekind")) + "-" + Common.get(rs.getString("propertyNo")) + "-" + Common.get(rs.getString("serialNo")) + "(非消耗品)";//propertyNo
	        	int otherLimitMonth = "".equals(Common.get(rs.getString("otherLimitYear")))? 0 : Integer.parseInt(rs.getString("otherLimitYear")); // otherLimitYear以單位月分存取需轉成年
				int year = otherLimitMonth /12;
				int month = otherLimitMonth % 12;
				data[4] =  "年限:" + year +"年"; //limitYear
	        	data[5] = "財產名稱:" + Common.get(rs.getString("propertyname"));//propertyname
	        	data[6] = "財產別名:" + Common.get(rs.getString("propertyname1"));//propertyname1
	        	data[7] = "購買/取得日期:" + Common.MaskDate(new Datetime().changeTaiwanYYMMDD(rs.getString("buyDate"),"1")) + " , " + Common.MaskDate(new Datetime().changeTaiwanYYMMDD(rs.getString("sourcedate"),"1"));//buyDate
	        	
	        	if ("n".equals(this.getQ_workKind())) {
	        		// 移動單
	        		if("1".equals(getQ_printType())){
		        		data[10] = "保管單位:" + Common.get(rs.getString("newkeepunitname"));//place
		        	}else if("2".equals(getQ_printType())){
		        		data[10] = "存置地點:" + Common.get(rs.getString("newplace1name"));//place
		        	}else if("3".equals(getQ_printType())){
		        		data[10] = "保管人:" + Common.get(rs.getString("keeperName"));//place
		        	}else if("4".equals(getQ_printType())){
		        		data[10] = "保管單位:" +Common.get(rs.getString("newkeepunitname")) + "/" +Common.get(rs.getString("newplace1name"));//place
		        	}else if("5".equals(getQ_printType())){
		        		data[10] =  "保管單位/保管人:" +Common.get(rs.getString("newkeepunitname")) + "/ " +Common.get(rs.getString("keeperName"));//place
		        	}else if("6".equals(getQ_printType())){
		        		data[10] = "存置地點:" +Common.get(rs.getString("newplace1name")) + "/" +Common.get(rs.getString("keeperName"));//place
		        	}
	        	} else {
	        		// 非移動單
	        		if("1".equals(getQ_printType())){
	            		data[10] = "保管單位:" + Common.get(rs.getString("keepunitName"));//place
	            	}else if("2".equals(getQ_printType())){
	            		data[10] = "存置地點:" + Common.get(rs.getString("placeName"));//place
	            	}else if("3".equals(getQ_printType())){
	            		data[10] = "保管人:" + Common.get(rs.getString("keeperName"));//place
	            	}else if("4".equals(getQ_printType())){
	            		data[10] = "保管單位:" +Common.get(rs.getString("keepunitName")) + "/" +Common.get(rs.getString("placeName"));//place
	            	}else if("5".equals(getQ_printType())){
	            		data[10] =  "保管單位/保管人:" +Common.get(rs.getString("keepunitName")) + "/ " +Common.get(rs.getString("keeperName"));//place
	            	}else if("6".equals(getQ_printType())){
	            		data[10] = "存置地點:" +Common.get(rs.getString("placeName")) + "/" +Common.get(rs.getString("keeperName"));//place
	            	}
	        	}
	
				rowData.addElement(data);
	        }
	    	
	        Object[][] finalData = new Object[0][0];
	        finalData = (Object[][])rowData.toArray(finalData);
	        model.setDataVector(finalData, columns);  
	
	    }catch(Exception x){
	       x.printStackTrace();
	    }finally{
	        db.closeAll();
	    }
	    
	    return model;
	}

}
