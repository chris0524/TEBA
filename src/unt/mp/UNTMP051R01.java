/*
 *<br>程式目的：動產標籤查詢檔 
 *<br>程式代號：untmp008r
 *<br>撰寫日期：94/11/26
 *<br>程式作者：Cherry
 *<br>--------------------------------------------------------
 *<br>修改作者　　修改日期　　　修改目的
 *<br>--------------------------------------------------------
 */

package unt.mp;

import java.sql.ResultSet;
import java.util.Vector;

import javax.swing.table.DefaultTableModel;

import util.Common;
import util.Database;
import util.Datetime;
import util.SuperBean;
import util.report.ReportUtil;

public class UNTMP051R01 extends SuperBean {
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
	String q_buyDateS;
	String q_buyDateE;
	String q_writeDateS;
	String q_writeDateE;
	String q_proofYear;
	String q_proofDoc;
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
	String q_differenceKind;
	String q_propertyName1;
	String originalKeeper;
	String originalKeepUnit;
	String originalUseUnit;
	String originalUser;
	String originalPlace1;
	String originalPlace1Name;
	String originalPlace;
	String originalUserNote;
	String q_printType;
	String q_caseSerialNoS;
	String q_caseSerialNoE;
	String orderby1;
	String orderby2;
	String q_printSerial;
	private String q_reportType;						// 報表類型
	
	public String getQ_reportType() { return checkGet(q_reportType); }
	public void setQ_reportType(String q_reportType) { this.q_reportType = checkGet(q_reportType); }
	public String getOriginalUserNote() {return checkGet(originalUserNote);}
	public void setOriginalUserNote(String originalUserNote) {this.originalUserNote = checkSet(originalUserNote);}
	public String getOriginalPlace() {return checkGet(originalPlace);}
	public void setOriginalPlace(String originalPlace) {this.originalPlace = checkSet(originalPlace);}
	public String getOriginalPlace1Name() {return checkGet(originalPlace1Name);}
	public void setOriginalPlace1Name(String originalPlace1Name) {this.originalPlace1Name = checkSet(originalPlace1Name);}
	public String getOriginalPlace1() {return checkGet(originalPlace1);}
	public void setOriginalPlace1(String originalPlace1) {this.originalPlace1 = checkSet(originalPlace1);}
	public String getQ_differenceKind() {return checkGet(q_differenceKind); }
	public void setQ_differenceKind(String qDifferenceKind) {q_differenceKind = checkSet(qDifferenceKind);}
	public String getOriginalKeeper() {return checkGet(originalKeeper);}
	public void setOriginalKeeper(String originalKeeper) {this.originalKeeper = checkSet(originalKeeper);}
	public String getOriginalUseUnit() {return checkGet(originalUseUnit);}
	public void setOriginalUseUnit(String originalUseUnit) {this.originalUseUnit = checkSet(originalUseUnit);}
	public String getOriginalKeepUnit() {return checkGet(originalKeepUnit);}
	public void setOriginalKeepUnit(String originalKeepUnit) {this.originalKeepUnit = checkSet(originalKeepUnit);}
	public String getOriginalUser() {return checkGet(originalUser);}
	public void setOriginalUser(String originalUser) {this.originalUser = checkSet(originalUser);}


	public String getQ_printSerial() {
		return checkGet(q_printSerial);
	}
	public void setQ_printSerial(String q_printSerial) {
		this.q_printSerial = checkSet(q_printSerial);
	}
	public String getOrderby1() {
		return checkGet(orderby1);
	}
	public void setOrderby1(String orderby1) {
		this.orderby1 = checkSet(orderby1);
	}
	public String getOrderby2() {
		return checkGet(orderby2);
	}
	public void setOrderby2(String orderby2) {
		this.orderby2 = checkSet(orderby2);
	}
	public String getQ_printType() {
		return checkGet(q_printType);
	}
	public void setQ_printType(String q_printType) {
		this.q_printType = checkSet(q_printType);
	}
	public String getQ_labelType(){ return checkGet(q_labelType); }
	public void setQ_labelType(String s){ q_labelType=checkSet(s); }
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


	public String getQ_buyDateS(){ return checkGet(q_buyDateS); }
	public void setQ_buyDateS(String s){ q_buyDateS=checkSet(s); }
	public String getQ_buyDateE(){ return checkGet(q_buyDateE); }
	public void setQ_buyDateE(String s){ q_buyDateE=checkSet(s); }

	public String getQ_writeDateS(){ return checkGet(q_writeDateS); }
	public void setQ_writeDateS(String s){ q_writeDateS=checkSet(s); }
	public String getQ_writeDateE(){ return checkGet(q_writeDateE); }
	public void setQ_writeDateE(String s){ q_writeDateE=checkSet(s); }

	public String getQ_proofYear(){ return checkGet(q_proofYear); }
	public void setQ_proofYear(String s){ q_proofYear=checkSet(s); }

	public String getQ_proofDoc(){ return checkGet(q_proofDoc); }
	public void setQ_proofDoc(String s){ q_proofDoc=checkSet(s); }
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
	public String getQ_propertyName1() {return checkGet(q_propertyName1);}
	public void setQ_propertyName1(String s) {q_propertyName1 = checkSet(s);}
	public String getQ_caseSerialNoS() {return checkGet(q_caseSerialNoS);}
	public void setQ_caseSerialNoS(String q_caseSerialNoS) {this.q_caseSerialNoS = checkSet(q_caseSerialNoS);}
	public String getQ_caseSerialNoE() {return checkGet(q_caseSerialNoE);}
	public void setQ_caseSerialNoE(String q_caseSerialNoE) {this.q_caseSerialNoE = checkSet(q_caseSerialNoE);}


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

	private String getOrderBySQL() {
		StringBuilder orderby = new StringBuilder();
		
		// 排序條件1
		if ("1".equals(getOrderby1())) { // 財產編號
			if (!"1".equals(getQ_printSerial())) {
				orderby.append(" order by differencekind desc,propertyno desc,serialno desc");
			} else {
				orderby.append(" order by differencekind,propertyno,serialno ");
			}
		} else if ("2".equals(getOrderby1())) { // 保管人
			if (!"1".equals(getQ_printSerial())) {
				orderby.append(" order by keeper desc");
			} else {
				orderby.append(" order by keeper");
			}
		} else if ("3".equals(getOrderby1())) { // 保管單位
			if (!"1".equals(getQ_printSerial())) {
				orderby.append(" order by keepunit desc");
			} else {
				orderby.append(" order by keepunit");
			}
		} else if ("4".equals(getOrderby1())) { // 存置地點 (依照代碼順序排序)
			if (!"1".equals(getQ_printSerial())) {
				if("n".equals(getQ_workKind())){
					orderby.append(" order by newplaceCode desc");
				} else {
					orderby.append(" order by place1 desc");
				}
			} else {
				if("n".equals(getQ_workKind())){
					orderby.append(" order by newplaceCode ");
				} else {
					orderby.append(" order by place1 ");
				}
			}

		} 
		
		// 排序條件2
		if (!"".equals(getOrderby1()) && "1".equals(getOrderby2()) && !getOrderby1().equals(getOrderby2())) {
			if (!"1".equals(getQ_printSerial())) {
				orderby.append(" ,differencekind desc,propertyno desc,serialno desc");
			} else {
				orderby.append(" ,differencekind,propertyno,serialno ");
			}
		} else if (!"".equals(getOrderby1()) && "2".equals(getOrderby2()) && !getOrderby1().equals(getOrderby2())) {
			if (!"1".equals(getQ_printSerial())) {
				orderby.append(" ,keeper desc");
			}else {
				orderby.append(" ,keeper ");
			}
		} else if (!"".equals(getOrderby1()) && "3".equals(getOrderby2()) && !getOrderby1().equals(getOrderby2())) {
			if (!"1".equals(getQ_printSerial())) {
				orderby.append(" ,keepunit desc");
			} else {
				orderby.append(" ,keepunit ");
			}
		} else if (!"".equals(getOrderby1()) && "4".equals(getOrderby2()) && !getOrderby1().equals(getOrderby2())) {
			if (!"1".equals(getQ_printSerial())) {
				if("n".equals(getQ_workKind())){
					orderby.append(" ,newplaceCode desc");
				} else {
					orderby.append(" ,place1 desc");
				}
			} else {
				if("n".equals(getQ_workKind())){
					orderby.append(" ,newplaceCode ");
				} else {
					orderby.append(" ,place1 ");
				}
			}
		}
		
		if (orderby.length() == 0) {
			// 都不選的預設
			if (!"1".equals(getQ_printSerial())) {
				orderby.append(" order by enterorg desc, ownership desc, keepunit desc, keeper desc, differencekind desc, propertyno desc, serialno desc");
			} else {
				orderby.append(" order by enterorg, ownership, keepunit, keeper, differencekind, propertyno, serialno ");
			}
		} else {
			// 不管前面選什麼  最後一定要是 區分別+財編+分號
			if (orderby.indexOf("differencekind") == -1) {
				if (!"1".equals(getQ_printSerial())) {
					orderby.append(",differencekind desc");
				} else {
					orderby.append(",differencekind");
				}
			}
			if (orderby.indexOf("propertyno") == -1) {
				if (!"1".equals(getQ_printSerial())) {
					orderby.append(",propertyno desc");
				} else {
					orderby.append(",propertyno");
				}
			}
			
			if (orderby.indexOf("serialno") == -1) {
				if (!"1".equals(getQ_printSerial())) {
					orderby.append(",serialno desc");
				} else {
					orderby.append(",serialno");
				}
			}
		}
		
		// 移動單將keeper 改成 newkeeper
		String orderbyStr = orderby.toString();
		if ("n".equals(this.getQ_workKind())) {
			orderbyStr = orderbyStr.replaceAll("keeper", "newkeeper");
		}
		return orderbyStr;
	}
	
	/**
	 * TODO 跟 getOrderBySQL 只差在  b.propertyno VS c.propertyno  懶得合併了 先這樣
	 * @return
	 */
	private String getOrderBySQLForTypeD() {
		StringBuilder orderby = new StringBuilder();
		
		// 排序條件1
		if ("1".equals(getOrderby1())) { // 財產編號
			if (!"1".equals(getQ_printSerial())) {
				orderby.append(" order by differencekind desc,propertyno desc,serialno desc");
			} else {
				orderby.append(" order by differencekind,propertyno,serialno ");
			}
		} else if ("2".equals(getOrderby1())) { // 保管人
			if (!"1".equals(getQ_printSerial())) {
				orderby.append(" order by keeper desc");
			} else {
				orderby.append(" order by keeper");
			}
		} else if ("3".equals(getOrderby1())) { // 保管單位
			if (!"1".equals(getQ_printSerial())) {
				orderby.append(" order by keepunit desc");
			} else {
				orderby.append(" order by keepunit");
			}
		} else if ("4".equals(getOrderby1())) { // 存置地點 (依照代碼順序排序)
			if (!"1".equals(getQ_printSerial())) {
				if("n".equals(getQ_workKind())){
					orderby.append(" order by newplaceCode desc");
				} else {
					orderby.append(" order by place1 desc");
				}
			} else {
				if("n".equals(getQ_workKind())){
					orderby.append(" order by newplaceCode ");
				} else {
					orderby.append(" order by place1 ");
				}
			}

		}		
		// 排序條件2
		if (!"".equals(getOrderby1()) && "1".equals(getOrderby2()) && !getOrderby1().equals(getOrderby2())) {
			if (!"1".equals(getQ_printSerial())) {
				orderby.append(" ,differencekind desc,propertyno desc,serialno desc");
			} else {
				orderby.append(" ,differencekind,propertyno,serialno ");
			}
		} else if (!"".equals(getOrderby1()) && "2".equals(getOrderby2()) && !getOrderby1().equals(getOrderby2())) {
			if (!"1".equals(getQ_printSerial())) {
				orderby.append(" ,keeper desc");
			}else {
				orderby.append(" ,keeper ");
			}
		} else if (!"".equals(getOrderby1()) && "3".equals(getOrderby2()) && !getOrderby1().equals(getOrderby2())) {
			if (!"1".equals(getQ_printSerial())) {
				orderby.append(" ,keepunit desc");
			} else {
				orderby.append(" ,keepunit ");
			}
		} else if (!"".equals(getOrderby1()) && "4".equals(getOrderby2()) && !getOrderby1().equals(getOrderby2())) {
			if (!"1".equals(getQ_printSerial())) {
				if("n".equals(getQ_workKind())){
					orderby.append(" ,newplaceCode desc");
				} else {
					orderby.append(" ,place1 desc");
				}
			} else {
				if("n".equals(getQ_workKind())){
					orderby.append(" ,newplaceCode ");
				} else {
					orderby.append(" ,place1 ");
				}
			}
		}		
		if (orderby.length() == 0) {
			// 都不選的預設
			if (!"1".equals(getQ_printSerial())) {
				orderby.append(" order by enterorg desc, ownership desc, keepunit desc, keeper desc, differencekind desc, propertyno desc, serialno desc");
			} else {
				orderby.append(" order by enterorg, ownership, keepunit, keeper, differencekind, propertyno, serialno ");
			}
		} else {
			// 不管前面選什麼  最後一定要是 區分別+財編+分號
			if (orderby.indexOf("differencekind") == -1) {
				if (!"1".equals(getQ_printSerial())) {
					orderby.append(",differencekind desc");
				} else {
					orderby.append(",differencekind");
				}
			}
			if (orderby.indexOf("propertyno") == -1) {
				if (!"1".equals(getQ_printSerial())) {
					orderby.append(",propertyno desc");
				} else {
					orderby.append(",propertyno");
				}
			}
			
			if (orderby.indexOf("serialno") == -1) {
				if (!"1".equals(getQ_printSerial())) {
					orderby.append(",serialno desc");
				} else {
					orderby.append(",serialno");
				}
			}
		}
		return orderby.toString();
	}
	
	private String getQueryForOther(){	
		String execSQL = "";
		String enterDateName = "";
		/**
		* 當作業種類(q_workKind)
		* 					   選擇"增加單"時則帶入"a"，因table為"a"
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
			execSQL +=" and c.enterorg = " + Common.sqlChar(getQ_enterOrg()) ;
		} else {
			if (!getIsAdminManager().equalsIgnoreCase("Y")) {
				if ("Y".equals(getIsOrganManager())) {					
					execSQL += " and ( c.enterorg = "+ Common.sqlChar(getOrganID()) +" or c.enterorg in ( select organid from SYSCA_ORGAN where organsuperior = "+ Common.sqlChar(getOrganID()) +" ) ) ";		
				} else {
					execSQL +=" and c.enterorg = " + Common.sqlChar(getOrganID()) ;
				}
			}
		}
		
		if (!"".equals(Common.get(getQ_dataState()))){
			execSQL +=" and c.datastate = " + util.Common.sqlChar(getQ_dataState());
		}
		if (!"".equals(Common.get(getQ_ownership())))
			execSQL +=" and c.ownership = " + util.Common.sqlChar(getQ_ownership());
		if (!"".equals(getQ_caseNoS()))
			execSQL +=" and "+getQ_workKind()+".caseno >= " + Common.sqlChar(Common.formatRearString(getQ_caseNoS(),10,'0'));		
		if (!"".equals(getQ_caseNoE()))
			execSQL +=" and "+getQ_workKind()+".caseno <= " + Common.sqlChar(Common.formatRearString(getQ_caseNoE(),10, '9'));
		if (!"".equals(Common.get(getQ_writeDateS())))
			execSQL += " and "+getQ_workKind()+".writedate >= " + util.Common.sqlChar(new Datetime().changeTaiwanYYMMDD(getQ_writeDateS(),"2"));
		if (!"".equals(Common.get(getQ_writeDateE())))
			execSQL += " and "+getQ_workKind()+".writedate <= " + util.Common.sqlChar(new Datetime().changeTaiwanYYMMDD(getQ_writeDateE(),"2"));
		if (!"".equals(getQ_proofYear()))
			execSQL += " and "+getQ_workKind()+".proofyear = " + Common.sqlChar(getQ_proofYear()) ;
		if (!"".equals(getQ_proofDoc()))
			execSQL += " and "+getQ_workKind()+".proofdoc like '%" + getQ_proofDoc() + "%'" ;
		if (!"".equals(getQ_proofNoS())) 
				execSQL += " and "+getQ_workKind()+".proofno >= " + Common.sqlChar(getQ_proofNoS());		
		if (!"".equals(getQ_proofNoE())) 
			execSQL += " and "+getQ_workKind()+".proofno <= " + Common.sqlChar(getQ_proofNoE());		 
		if (!"".equals(getQ_closing()))
			execSQL+=" and "+getQ_workKind()+".closing = " + Common.sqlChar(getQ_closing()) ;
		if (!"".equals(getQ_enterDateS()))
			execSQL+=" and "+getQ_workKind()+"." + enterDateName + " >= " + Common.sqlChar(new Datetime().changeTaiwanYYMMDD(getQ_enterDateS(),"2"));
		if (!"".equals(getQ_enterDateE()))
			execSQL+=" and "+getQ_workKind()+"." + enterDateName + " <= " + Common.sqlChar(new Datetime().changeTaiwanYYMMDD(getQ_enterDateE(),"2"));
		if (!"".equals(getQ_buyDateS()))
			execSQL+=" and c.buydate >= " + Common.sqlChar(new Datetime().changeTaiwanYYMMDD(getQ_buyDateS(),"2"));
		if (!"".equals(getQ_buyDateE()))	
		   execSQL+=" and c.buydate <= " + Common.sqlChar(new Datetime().changeTaiwanYYMMDD(getQ_buyDateE(),"2"));			
		if (!"".equals(getQ_propertyNoS()))
			execSQL+=" and c.propertyno >= " + Common.sqlChar(getQ_propertyNoS());		
		if (!"".equals(getQ_propertyNoE()))
			execSQL+=" and c.propertyno <= " + Common.sqlChar(getQ_propertyNoE());		
		if (!"".equals(getQ_differenceKind()))
			execSQL+=" and c.differencekind = " + Common.sqlChar(getQ_differenceKind()) ;		
		if (!"".equals(getQ_propertyName1()))
			execSQL += " and c.propertyname1 like " + Common.sqlChar("%" + getQ_propertyName1() + "%");
		if (!"".equals(getQ_propertyKind()))
			execSQL+=" and c.propertykind = " + Common.sqlChar(getQ_propertyKind()) ;
		if (!"".equals(getQ_fundType()))
			execSQL+=" and c.fundtype = " + Common.sqlChar(getQ_fundType()) ;
		if (!"".equals(getQ_valuable()))
			execSQL+=" and c.valuable = " + Common.sqlChar(getQ_valuable()) ;	    
		if (!"".equals(getQ_serialNoS()))
			execSQL+=" and c.serialno >= " + Common.sqlChar(getQ_serialNoS());
		if (!"".equals(getQ_serialNoE()))
			execSQL+=" and c.serialno <= " + Common.sqlChar(getQ_serialNoE());	
		if (!"".equals(getQ_caseSerialNoS())) {
			execSQL+=" and o.caseserialno >= " + Common.sqlChar(getQ_caseSerialNoS());
		}
		if (!"".equals(getQ_caseSerialNoE())) {
			execSQL+=" and o.caseserialno <= " + Common.sqlChar(getQ_caseSerialNoE());
		}
		
		if (!"".equals(getOriginalKeepUnit())) { // 保管單位
			if ("n".equals(this.getQ_workKind())) { // 移動單時  保管人條件為指定 newkeepunit
				execSQL+=" and o.newkeepunit = " + Common.sqlChar(getOriginalKeepUnit()) ;
			} else {
				execSQL+=" and c.keepunit = " + Common.sqlChar(getOriginalKeepUnit()) ;
			}
		}
		
		if (!"".equals(getOriginalKeeper())) { // 保管人
			if ("n".equals(this.getQ_workKind())) { // 移動單時  保管人條件為指定 newkeeper
				execSQL+=" and o.newkeeper = " + Common.sqlChar(getOriginalKeeper()) ;
			} else {
				execSQL+=" and c.keeper = " + Common.sqlChar(getOriginalKeeper()) ;
			}
		}
		
		if (!"".equals(getOriginalUseUnit()))
			execSQL+=" and c.useunit = " + Common.sqlChar(getOriginalUseUnit()) ;	    
		if (!"".equals(getOriginalUser()))
			execSQL+=" and c.userno = " + Common.sqlChar(getOriginalUser()) ;		
		if (!"".equals(getOriginalUserNote()))
			execSQL+=" and c.usernote like " + Common.sqlChar("%"+getOriginalUserNote()+"%") ;			
		if (!"".equals(getOriginalPlace1()))
			execSQL+=" and c.place1 = " + Common.sqlChar(getOriginalPlace1()) ;	
		if (!"".equals(getOriginalPlace()))
			execSQL+=" and c.place like " + Common.sqlChar("%"+getOriginalPlace()+"%") ;	
		
		return execSQL;
	}
	
	private String getSQLForLAOther(){

		String execSQL = " select distinct c.enterorg, c.ownership, c.keepunit, c.keeper, c.differencekind, "+
	 			 		" d.organaname as enterOrgName,( select g.placename from SYSCA_PLACE g where g.placeno=c.place1 and g.enterorg=c.enterorg) as placeName ," +
	 			 		" c.place1 ," + //1041029 存置地點排序用
	 			 		" (select z.codename from SYSCA_CODE z where 1=1 and (z.codekindid='owa' or z.codekindid='OWA') and c.ownership=z.codeid) as ownershipName, " +
	 			 		" case c.propertykind when '01' then '公務用' when '02' then '公共用' when '03' then'事業用' when '04' then '非公用' else '' end  as propertyKindName, " +
	 			 		"  c.propertyno, c.serialno, " +
	 			 		" (select e.limityear from SYSPK_PROPERTYCODE e where c.propertyno = e.propertyno and e.enterorg in('000000000A',c.enterorg) and e.propertytype='1') as limitYear, " +
	 			 		" 0 as otherlimityear, " +
	 			 		" (select e.propertyname from SYSPK_PROPERTYCODE e where c.propertyno = e.propertyno and e.enterorg in('000000000A',c.enterorg) and e.propertytype='1') as propertyName, " +
	 			 		" c.buydate,c.sourcedate, '' as specification, ";
	
		if("a".equals(getQ_workKind())){
			execSQL += " isnull(c.propertyname1,'') as propertyname1,";
		}else if("j".equals(getQ_workKind())){
			execSQL += " isnull(k.propertyname1,'') as propertyname1,";
		}else if("l".equals(getQ_workKind())){
			execSQL += " isnull(m.propertyname1,'') as propertyname1,";
		}else if("n".equals(getQ_workKind())){
			execSQL += " isnull(o.propertyname1,'') as propertyname1,";
		}
		
		execSQL += " isnull(f.keepername,'') as keeperName, c.place " +
				   " ,c.bookvalue , p.unitname " +
				   " ,(select k.unitname from UNTMP_KEEPUNIT k where k.enterorg = c.enterorg and k.unitno = c.keepunit) as keepunitName ";
		if("n".equals(getQ_workKind())) {
			execSQL += " ,(select unitname from UNTMP_KEEPUNIT z where z.enterorg = o.enterorg and z.unitno = o.newkeepunit) as newkeepunit" +
					   " ,(select placename from SYSCA_PLACE z where z.enterorg = o.enterorg and z.placeno = o.newplace1) as newplace1 " +
					   " ,o.newplace1 as newplaceCode, o.newkeeper";
		}
		execSQL += "\n"+" from UNTLA_LAND c " +"\n"+  
				   " left join SYSCA_ORGAN d on (c.enterorg = d.organid)" +"\n"+
				   " left join UNTLA_ADDPROOF a on (a.enterorg=c.enterorg and a.caseno=c.caseno and  a.ownership=c.ownership)" +"\n"+
				   " left join UNTLA_ADJUSTDETAIL k on (k.enterorg=c.enterorg and k.ownership=c.ownership and k.propertyno=c.propertyno and k.differencekind=c.differencekind and k.serialno=c.serialno)" +"\n"+  
				   " left join UNTLA_ADJUSTPROOF j on (j.enterorg=k.enterorg and j.ownership=k.ownership and j.caseno=k.caseno)" +"\n"+  
				   " left join UNTLA_REDUCEDETAIL m on (m.enterorg=c.enterorg and m.ownership=c.ownership and m.propertyno=c.propertyno and m.differencekind=c.differencekind and m.serialno=c.serialno)" +"\n"+  
				   " left join UNTLA_REDUCEPROOF l on (l.enterorg=m.enterorg and l.ownership=m.ownership and l.caseno=m.caseno)" +"\n"+  
				   " left join UNTMP_MOVEDETAIL o on (o.enterorg=c.enterorg and o.ownership=c.ownership and o.propertyno=c.propertyno and o.differencekind=c.differencekind and o.serialno=c.serialno)" +"\n"+  
				   " left join UNTMP_MOVEPROOF n on (n.enterorg=o.enterorg and n.ownership=o.ownership and n.caseno=o.caseno)" +"\n"+
				   "";
		if("n".equals(getQ_workKind())){
			execSQL += " left join UNTMP_KEEPER f on (c.enterorg=f.enterorg and o.newkeeper=f.keeperno)" +"\n";
		} else {
			execSQL += " left join UNTMP_KEEPER f on (c.enterorg=f.enterorg and c.keeper=f.keeperno)" +"\n";
		}
		execSQL += " left join UNTMP_KEEPUNIT p on (c.enterorg = p.enterorg and c.keepunit = p.unitno)" +"\n"+
				   " where 1=1 and c.enterorg = d.organid " +"\n"+
				   "";		
		execSQL += getQueryForOther();
		
		return execSQL;		
	}
	
	private String getSQLForBUOther(){

		String execSQL = " select distinct c.enterorg, c.ownership, c.keepunit, c.keeper, c.differencekind, "+
			 		" d.organaname as enterOrgName,( select g.placename from SYSCA_PLACE g where g.placeno=c.place1 and g.enterorg=c.enterorg) as placeName ," +
			 		" c.place1 ," + //1041029 存置地點排序用
			 		" (select z.codename from SYSCA_CODE z where 1=1 and (z.codekindid='owa' or z.codekindid='OWA') and c.ownership=z.codeid) as ownershipName, " +
			 		" case c.propertykind when '01' then '公務用' when '02' then '公共用' when '03' then'事業用' when '04' then '非公用' else '' end  as propertyKindName, " +
			 		"  c.propertyno, c.serialno, " +
			 		" (select e.limityear from SYSPK_PROPERTYCODE e where c.propertyno = e.propertyno and e.enterorg in('000000000A',c.enterorg) and e.propertytype='1') as limitYear, " +
			 		" c.otherlimityear, " +
			 		" (select e.propertyname from SYSPK_PROPERTYCODE e where c.propertyno = e.propertyno and e.enterorg in('000000000A',c.enterorg) and e.propertytype='1') as propertyName, " +
			 		" c.buydate,c.sourcedate, '' as specification, ";
	
		if("a".equals(getQ_workKind())){
			execSQL += " isnull(c.propertyname1,'') as propertyname1,";
		}else if("j".equals(getQ_workKind())){
			execSQL += " isnull(k.propertyname1,'') as propertyname1,";
		}else if("l".equals(getQ_workKind())){
			execSQL += " isnull(m.propertyname1,'') as propertyname1,";
		}else if("n".equals(getQ_workKind())){
			execSQL += " isnull(o.propertyname1,'') as propertyname1,";
		}
		
		execSQL += " isnull(f.keepername,'') as keeperName, c.place " +
				   " ,c.bookvalue , p.unitname " +
				   " ,(select k.unitname from UNTMP_KEEPUNIT k where k.enterorg = c.enterorg and k.unitno = c.keepunit) as keepunitName ";
		if("n".equals(getQ_workKind())) {
			execSQL += " ,(select unitname from UNTMP_KEEPUNIT z where z.enterorg = o.enterorg and z.unitno = o.newkeepunit) as newkeepunit" +
					   " ,(select placename from SYSCA_PLACE z where z.enterorg = o.enterorg and z.placeno = o.newplace1) as newplace1 " +
					   " ,o.newplace1 as newplaceCode, o.newkeeper ";
		}
		execSQL += "  from UNTBU_BUILDING c" +
				" left join UNTBU_ADDPROOF a on (a.enterorg=c.enterorg and a.caseno=c.caseno and  a.ownership=c.ownership)" +"\n"+
				" left join UNTBU_ADJUSTDETAIL k join UNTBU_ADJUSTPROOF j on j.enterorg=k.enterorg and j.ownership=k.ownership and j.caseno=k.caseno on k.enterorg=c.enterorg and k.ownership=c.ownership and k.propertyno=c.propertyno and k.differencekind=c.differencekind and k.serialno=c.serialno" +"\n"+
				" left join UNTBU_REDUCEDETAIL m join UNTBU_REDUCEPROOF l on l.enterorg=m.enterorg and l.ownership=m.ownership and l.caseno=m.caseno on m.enterorg=c.enterorg and m.ownership=c.ownership and m.propertyno=c.propertyno and m.differencekind=c.differencekind and m.serialno=c.serialno" +"\n"+
				" left join UNTMP_MOVEDETAIL o join UNTMP_MOVEPROOF n on n.enterorg=o.enterorg and n.ownership=o.ownership and n.caseno=o.caseno on o.enterorg=c.enterorg and o.ownership=c.ownership and o.propertyno=c.propertyno and o.differencekind=c.differencekind and o.serialno=c.serialno";
		if("n".equals(getQ_workKind())){
			execSQL += " left join UNTMP_KEEPER f on c.enterorg=f.enterorg and o.newkeeper=f.keeperno";
		} else {
			execSQL += " left join UNTMP_KEEPER f on c.enterorg=f.enterorg and c.keeper=f.keeperno";
		}
		execSQL += " left join UNTMP_KEEPUNIT p on c.enterorg = p.enterorg and c.keepunit = p.unitno" +
				   " 		, SYSCA_ORGAN d "+"\n"+
				   " where 1=1 and c.enterorg = d.organid " +"\n"+				   
				   "";
		execSQL += getQueryForOther();		
		
		return execSQL;
		
	}
	
	private String getSQLForRFOther(){

		String execSQL = " select distinct c.enterorg, c.ownership, c.keepunit, c.keeper, c.differencekind, " +"\n"+
	 			 		 " 		  d.organaname as enterOrgName,( select g.placename from SYSCA_PLACE g where g.placeno=c.place1 and g.enterorg=c.enterorg)as placeName ," +"\n"+
	 			 		 "       c.place1 ," + //1041029 存置地點排序用
	 			 		 " 		  (select z.codename from SYSCA_CODE z where 1=1 and (z.codekindid='owa' or z.codekindid='OWA') and c.ownership=z.codeid) as ownershipName, " +"\n"+
	 			 		 " 		  case c.propertykind when '01' then '公務用' when '02' then '公共用' when '03' then'事業用' when '04' then '非公用' else '' end  propertyKindName, " +"\n"+
	 			 		 "		  c.propertyno, c.serialno, " +
	 			 		 " 		 (select e.limityear from SYSPK_PROPERTYCODE e where c.propertyno = e.propertyno and e.enterorg in('000000000A',c.enterorg) and e.propertytype='1') as limitYear, " +"\n"+
	 			 		 " 		 c.otherlimityear, "+"\n"+
	 			 		 " 		 (select e.propertyname from SYSPK_PROPERTYCODE e where c.propertyno = e.propertyno and e.enterorg in('000000000A',c.enterorg) and e.propertytype='1') as propertyName, " +"\n"+
	 			 		 " 		 c.buydate,c.sourcedate, '' as specification,";
	
		if("a".equals(getQ_workKind())){
			execSQL += "		isnull(c.propertyname1,'') propertyname1,";
		}else if("j".equals(getQ_workKind())){
			execSQL += "		isnull(k.propertyname1,'') propertyname1,";
		}else if("l".equals(getQ_workKind())){
			execSQL += "		isnull(m.propertyname1,'') propertyname1,";
		}else if("n".equals(getQ_workKind())){
			execSQL += "		isnull(o.propertyname1,'') propertyname1,";
		}
		
		execSQL += "		isnull(f.keepername,'') keeperName, c.place, " +"\n"+
				   " 		 c.bookvalue , p.unitname "+"\n"+
				   "       ,(select k.unitname from UNTMP_KEEPUNIT k where k.enterorg = c.enterorg and k.unitno = c.keepunit) as keepunitName ";
		if("n".equals(getQ_workKind())) {
			execSQL += " ,(select unitname from UNTMP_KEEPUNIT z where z.enterorg = o.enterorg and z.unitno = o.newkeepunit) as newkeepunit" +
					   " ,(select placename from SYSCA_PLACE z where z.enterorg = o.enterorg and z.placeno = o.newplace1) as newplace1 " +
					   " ,o.newplace1 as newplaceCode, o.newkeeper ";
		}
		execSQL += "      from UNTRF_ATTACHMENT c" +
				   "       left join UNTRF_ADDPROOF a on (a.enterorg=c.enterorg and a.caseno=c.caseno and  a.ownership=c.ownership)" +"\n"+		          
				   " 		left join UNTRF_ADJUSTDETAIL k join UNTRF_ADJUSTPROOF j on j.enterorg=k.enterorg and j.ownership=k.ownership and j.caseno=k.caseno on k.enterorg=c.enterorg and k.ownership=c.ownership and k.propertyno=c.propertyno and k.differencekind=c.differencekind and k.serialno=c.serialno" +"\n"+
				   " 		left join UNTRF_REDUCEDETAIL m join UNTRF_REDUCEPROOF l on l.enterorg=m.enterorg and l.ownership=m.ownership and l.caseno=m.caseno on m.enterorg=c.enterorg and m.ownership=c.ownership and m.propertyno=c.propertyno and m.differencekind=c.differencekind and m.serialno=c.serialno" +"\n"+
				   " 		left join UNTMP_MOVEDETAIL o join UNTMP_MOVEPROOF n on n.enterorg=o.enterorg and n.ownership=o.ownership and n.caseno=o.caseno on o.enterorg=c.enterorg and o.ownership=c.ownership and o.propertyno=c.propertyno and o.differencekind=c.differencekind and o.serialno=c.serialno";
		if("n".equals(getQ_workKind())){
			execSQL += " left join UNTMP_KEEPER f on c.enterorg=f.enterorg and o.newkeeper=f.keeperno";
		} else {
			execSQL += " left join UNTMP_KEEPER f on c.enterorg=f.enterorg and c.keeper=f.keeperno";
		}
		execSQL += " left join UNTMP_KEEPUNIT p on c.enterorg = p.enterorg and c.keepunit = p.unitno" +
				   " 		, SYSCA_ORGAN d "+"\n"+
				   " where 1=1 and c.enterorg = d.organid " +"\n"+				   
				   "";
		execSQL += getQueryForOther();
		
		return execSQL;
		
	}
	
	private String getSQLForMPOther(){

		String execSQL = " select distinct b.enterorg, b.ownership, c.keepunit, c.keeper, b.differencekind, " +"\n"+
	 			 		 " 		  d.organaname as enterOrgName,( select g.placename from SYSCA_PLACE g where g.placeno=c.place1 and g.enterorg=c.enterorg)as placeName ," +"\n"+
	 			 		 "       c.place1 ," + //1041029 存置地點排序用
	 			 		 " 		  (select z.codename from SYSCA_CODE z where 1=1 and (z.codekindid='owa' or z.codekindid='OWA') and a.ownership=z.codeid) as ownershipName, " +"\n"+
	 			 		 " 		  case b.propertykind when '01' then '公務用' when '02' then '公共用' when '03' then'事業用' when '04' then '非公用' else '' end  propertyKindName, " +"\n"+
	 			 		 "		  b.propertyno, c.serialno, " +
	 			 		 " 		 (select e.limityear from SYSPK_PROPERTYCODE e where b.propertyno = e.propertyno and e.enterorg in('000000000A',b.enterorg) and e.propertytype='1') as limitYear, " +"\n"+
	 			 		 " 		 b.otherlimityear, "+"\n"+
	 			 		 " 		 (select e.propertyname from SYSPK_PROPERTYCODE e where b.propertyno = e.propertyno and e.enterorg in('000000000A',b.enterorg) and e.propertytype='1') as propertyName, " +"\n"+
	 			 		 " 		 b.buydate,b.sourcedate, b.specification,";
	
		if("a".equals(getQ_workKind())){
				execSQL += "		isnull(c.propertyname1,'') propertyname1,";	
		}else if("j".equals(getQ_workKind())){
			execSQL += "		isnull(k.propertyname1,'') propertyname1,";
		}else if("l".equals(getQ_workKind())){
			execSQL += "		isnull(m.propertyname1,'') propertyname1,";
		}else if("n".equals(getQ_workKind())){
			execSQL += "		isnull(o.propertyname1,'') propertyname1,";
		}
		
		execSQL += "		isnull(f.keepername,'') keeperName, c.place, " +"\n"+
				   " 		 c.bookvalue , p.unitname "+"\n"+
				   "       ,(select k.unitname from UNTMP_KEEPUNIT k where k.enterorg = c.enterorg and k.unitno = c.keepunit) as keepunitName ";
		if("n".equals(getQ_workKind())) {
			execSQL += " ,(select unitname from UNTMP_KEEPUNIT z where z.enterorg = o.enterorg and z.unitno = o.newkeepunit) as newkeepunit" +
					   " ,(select placename from SYSCA_PLACE z where z.enterorg = o.enterorg and z.placeno = o.newplace1) as newplace1 " +
					   " ,o.newplace1 as newplaceCode, o.newkeeper ";
		}
		execSQL += "      from UNTMP_MOVABLE b, UNTMP_MOVABLEDETAIL c" +
		          "        left  join UNTMP_ADDPROOF a on (a.caseno=c.caseno and a.enterorg=c.enterorg and a.ownership=c.ownership ) " +
				   " 		left join UNTMP_ADJUSTDETAIL k join UNTMP_ADJUSTPROOF j on j.enterorg=k.enterorg and j.ownership=k.ownership and j.caseno=k.caseno on k.enterorg=c.enterorg and k.ownership=c.ownership and k.propertyno=c.propertyno and k.differencekind=c.differencekind and k.serialno=c.serialno" +"\n"+
				   " 		left join UNTMP_REDUCEDETAIL m join UNTMP_REDUCEPROOF l on l.enterorg=m.enterorg and l.ownership=m.ownership and l.caseno=m.caseno on m.enterorg=c.enterorg and m.ownership=c.ownership and m.propertyno=c.propertyno and m.differencekind=c.differencekind and m.serialno=c.serialno" +"\n"+
				   " 		left join UNTMP_MOVEDETAIL o join UNTMP_MOVEPROOF n on n.enterorg=o.enterorg and n.ownership=o.ownership and n.caseno=o.caseno on o.enterorg=c.enterorg and o.ownership=c.ownership and o.propertyno=c.propertyno and o.differencekind=c.differencekind and o.serialno=c.serialno";
		if("n".equals(getQ_workKind())){
			execSQL += " left join UNTMP_KEEPER f on c.enterorg=f.enterorg and o.newkeeper=f.keeperno";
		} else {
			execSQL += " left join UNTMP_KEEPER f on c.enterorg=f.enterorg and c.keeper=f.keeperno";
		}
		execSQL += " left join UNTMP_KEEPUNIT p on c.enterorg = p.enterorg and c.keepunit = p.unitno" +
				   " 		, SYSCA_ORGAN d "+"\n"+
				   " where 1=1 and b.enterorg = d.organid " +"\n"+
				   " and c.enterorg=b.enterorg and c.ownership=b.ownership and b.lotno=c.lotno and b.differencekind = c.differencekind and b.propertyno=c.propertyno " +"\n"+
				   "";
		
		String enterDateName = "";
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
			execSQL +=" and b.enterorg = " + Common.sqlChar(getQ_enterOrg()) ;
		} else {
			if (!getIsAdminManager().equalsIgnoreCase("Y")) {
				if ("Y".equals(getIsOrganManager())) {					
					execSQL += " and ( b.enterorg = "+ Common.sqlChar(getOrganID()) +" or b.enterorg in ( select organid from SYSCA_ORGAN where organsuperior = "+ Common.sqlChar(getOrganID()) +" ) ) ";		
				} else {
					execSQL +=" and b.enterorg = " + Common.sqlChar(getOrganID()) ;
				}
			}
		}
		
		
		if (!"".equals(Common.get(getQ_dataState()))){
			execSQL +=" and b.datastate = " + util.Common.sqlChar(getQ_dataState());
			execSQL +=" and c.datastate = " + util.Common.sqlChar(getQ_dataState());
		}
		if (!"".equals(Common.get(getQ_ownership()))){
			execSQL +=" and b.ownership = " + util.Common.sqlChar(getQ_ownership());
		}
		if (!"".equals(getQ_caseNoS()))
			execSQL +=" and "+getQ_workKind()+".caseno >= " + Common.sqlChar(Common.formatRearString(getQ_caseNoS(),10,'0'));		
		if (!"".equals(getQ_caseNoE()))
			execSQL +=" and "+getQ_workKind()+".caseno <= " + Common.sqlChar(Common.formatRearString(getQ_caseNoE(),10, '9'));
		if (!"".equals(Common.get(getQ_writeDateS())))
			execSQL += " and "+getQ_workKind()+".writedate >= " + util.Common.sqlChar(new Datetime().changeTaiwanYYMMDD(getQ_writeDateS(),"2"));
		if (!"".equals(Common.get(getQ_writeDateE())))
			execSQL += " and "+getQ_workKind()+".writedate <= " + util.Common.sqlChar(new Datetime().changeTaiwanYYMMDD(getQ_writeDateE(),"2"));
		if (!"".equals(getQ_proofYear()))
			execSQL += " and "+getQ_workKind()+".proofyear = " + Common.sqlChar(getQ_proofYear()) ;
		if (!"".equals(getQ_proofDoc()))
			execSQL += " and "+getQ_workKind()+".proofdoc like '%" + getQ_proofDoc() + "%'" ;
		if (!"".equals(getQ_proofNoS())) 
				execSQL += " and "+getQ_workKind()+".proofno >= " + Common.sqlChar(getQ_proofNoS());		
		if (!"".equals(getQ_proofNoE())) 
			execSQL += " and "+getQ_workKind()+".proofno <= " + Common.sqlChar(getQ_proofNoE());		 
		if (!"".equals(getQ_closing()))
			execSQL+=" and "+getQ_workKind()+".closing = " + Common.sqlChar(getQ_closing()) ;
		if (!"".equals(getQ_enterDateS()))
			execSQL+=" and "+getQ_workKind()+"." + enterDateName + " >= " + Common.sqlChar(new Datetime().changeTaiwanYYMMDD(getQ_enterDateS(),"2"));
		if (!"".equals(getQ_enterDateE()))
			execSQL+=" and "+getQ_workKind()+"." + enterDateName + " <= " + Common.sqlChar(new Datetime().changeTaiwanYYMMDD(getQ_enterDateE(),"2"));
		if (!"".equals(getQ_buyDateS()))
			execSQL+=" and b.buydate >= " + Common.sqlChar(new Datetime().changeTaiwanYYMMDD(getQ_buyDateS(),"2"));
		if (!"".equals(getQ_buyDateE()))	
		   execSQL+=" and b.buydate <= " + Common.sqlChar(new Datetime().changeTaiwanYYMMDD(getQ_buyDateE(),"2"));
			
		if (!"".equals(getQ_propertyNoS()))
			execSQL+=" and b.propertyno >= " + Common.sqlChar(getQ_propertyNoS());		
		if (!"".equals(getQ_propertyNoE()))
			execSQL+=" and b.propertyno <= " + Common.sqlChar(getQ_propertyNoE());
		
		if (!"".equals(getQ_differenceKind()))
			execSQL+=" and b.differencekind = " + Common.sqlChar(getQ_differenceKind()) ;
		
		if (!"".equals(getQ_propertyName1()))
			execSQL += " and c.propertyname1 like " + Common.sqlChar("%" + getQ_propertyName1() + "%");
		
		
		if (!"".equals(getQ_propertyKind()))
			execSQL+=" and b.propertykind = " + Common.sqlChar(getQ_propertyKind()) ;
		if (!"".equals(getQ_fundType()))
			execSQL+=" and b.fundtype = " + Common.sqlChar(getQ_fundType()) ;
		if (!"".equals(getQ_valuable()))
			execSQL+=" and b.valuable = " + Common.sqlChar(getQ_valuable()) ;	    
		if (!"".equals(getQ_serialNoS()))
			execSQL+=" and c.serialno >= " + Common.sqlChar(getQ_serialNoS());
		if (!"".equals(getQ_serialNoE()))
			execSQL+=" and c.serialno <= " + Common.sqlChar(getQ_serialNoE());	
		if (!"".equals(getQ_caseSerialNoS())) {
			execSQL+=" and o.caseserialno >= " + Common.sqlChar(getQ_caseSerialNoS());
		}
		if (!"".equals(getQ_caseSerialNoE())) {
			execSQL+=" and o.caseserialno <= " + Common.sqlChar(getQ_caseSerialNoE());
		}
		
		if (!"".equals(getOriginalKeepUnit())) { // 保管單位
			if ("n".equals(this.getQ_workKind())) { // 移動單時  保管人條件為指定 newkeepunit
				execSQL+=" and o.newkeepunit = " + Common.sqlChar(getOriginalKeepUnit()) ;
			} else {
				execSQL+=" and c.keepunit = " + Common.sqlChar(getOriginalKeepUnit()) ;
			}
		}
		
		if (!"".equals(getOriginalKeeper())) { // 保管人
			if ("n".equals(this.getQ_workKind())) { // 移動單時  保管人條件為指定 newkeeper
				execSQL+=" and o.newkeeper = " + Common.sqlChar(getOriginalKeeper()) ;
			} else {
				execSQL+=" and c.keeper = " + Common.sqlChar(getOriginalKeeper()) ;
			}
		}
		
		if (!"".equals(getOriginalUseUnit()))
			execSQL+=" and c.useunit = " + Common.sqlChar(getOriginalUseUnit()) ;	    
		if (!"".equals(getOriginalUser()))
			execSQL+=" and c.userno = " + Common.sqlChar(getOriginalUser()) ;
		
		if (!"".equals(getOriginalUserNote()))
			execSQL+=" and c.usernote like " + Common.sqlChar("%"+getOriginalUserNote()+"%") ;	
		
		if (!"".equals(getOriginalPlace1()))
			execSQL+=" and c.place1 = " + Common.sqlChar(getOriginalPlace1()) ;	
		if (!"".equals(getOriginalPlace()))
			execSQL+=" and c.place like " + Common.sqlChar("%"+getOriginalPlace()+"%") ;	
		
		
		return execSQL;
		
	}
	
	private String getSQLForVPOther(){

		String execSQL = " select distinct a.enterorg, a.ownership, a.keepunit, a.keeper, a.differencekind," +"\n"+
		 		 " 		  d.organaname as enterOrgName,( select g.placename from SYSCA_PLACE g where g.placeno=a.place1 and g.enterorg=a.enterorg)as placeName ," +"\n"+
			 	 "       a.place1 ," + //1041029 存置地點排序用
		 		 " 		  (select z.codename from SYSCA_CODE z where 1=1 and (z.codekindid='owa' or z.codekindid='OWA') and a.ownership=z.codeid) as ownershipName, " +"\n"+
		 		 " 		  case a.propertykind when '01' then '公務用' when '02' then '公共用' when '03' then'事業用' when '04' then '非公用' else '' end  propertyKindName, " +"\n"+
		 		 "		  a.propertyno, a.serialno, " +
		 		 " 		 (select e.limityear from SYSPK_PROPERTYCODE e where a.propertyno = e.propertyno and e.enterorg in('000000000A',a.enterorg) and e.propertytype='1') as limitYear, " +"\n"+
		 		 " 		 0 as otherlimityear, "+"\n"+
		 		 " 		 (select e.propertyname from SYSPK_PROPERTYCODE e where a.propertyno = e.propertyno and e.enterorg in('000000000A',a.enterorg) and e.propertytype='1') as propertyName, " +"\n"+
		 		 " 		 a.buydate,a.sourcedate, '' as specification,";

		if("a".equals(getQ_workKind())){
			execSQL += "		isnull(a.propertyname1,'') propertyname1,";
		}else if("j".equals(getQ_workKind())){
			execSQL += "		isnull(j.propertyname1,'') propertyname1,";
		}else if("l".equals(getQ_workKind())){
			execSQL += "		isnull(l.propertyname1,'') propertyname1,";
		}else if("n".equals(getQ_workKind())){
			execSQL += "		isnull(o.propertyname1,'') propertyname1,";
		}
		
		execSQL += "		isnull(f.keepername,'') keeperName, a.place, " +"\n"+
				   " 		 a.bookvalue , p.unitname "+"\n"+
				   "       ,(select k.unitname from UNTMP_KEEPUNIT k where k.enterorg = a.enterorg and k.unitno = a.keepunit) as keepunitName ";
		if("n".equals(getQ_workKind())) {
			execSQL += " ,(select unitname from UNTMP_KEEPUNIT z where z.enterorg = o.enterorg and z.unitno = o.newkeepunit) as newkeepunit" +
					   " ,(select placename from SYSCA_PLACE z where z.enterorg = o.enterorg and z.placeno = o.newplace1) as newplace1 " +
					   " ,o.newplace1 as newplaceCode, o.newkeeper ";
		}
		execSQL += "      from UNTVP_ADDPROOF a" +		          
				   " 		left join UNTVP_ADJUSTPROOF j on j.enterorg=a.enterorg and j.ownership=a.ownership and j.differencekind=a.differencekind and j.propertyno=a.propertyno and j.serialno=a.serialno" +"\n"+
				   " 		left join UNTVP_REDUCEPROOF l on l.enterorg=a.enterorg and l.ownership=a.ownership and l.differencekind=a.differencekind and l.propertyno=a.propertyno and l.serialno=a.serialno" +"\n"+
				   " 		left join UNTMP_MOVEDETAIL o join UNTMP_MOVEPROOF n on n.enterorg=o.enterorg and n.ownership=o.ownership and n.caseno=o.caseno on o.enterorg=a.enterorg and o.ownership=a.ownership and o.propertyno=a.propertyno and o.differencekind=a.differencekind and o.serialno=a.serialno";
		if("n".equals(getQ_workKind())){
			execSQL += " left join UNTMP_KEEPER f on a.enterorg=f.enterorg and o.newkeeper=f.keeperno";
		} else {
			execSQL += " left join UNTMP_KEEPER f on a.enterorg=f.enterorg and a.keeper=f.keeperno";
		}
		execSQL += " left join UNTMP_KEEPUNIT p on a.enterorg = p.enterorg and a.keepunit = p.unitno" +
				   " 		, SYSCA_ORGAN d "+"\n"+
				   " where 1=1 and a.enterorg = d.organid " +"\n"+				   
				   "";
		
		String enterDateName = "";
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
			execSQL +=" and a.datastate = " + util.Common.sqlChar(getQ_dataState());
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
		if (!"".equals(getQ_proofYear()))
			execSQL += " and "+getQ_workKind()+".proofyear = " + Common.sqlChar(getQ_proofYear()) ;
		if (!"".equals(getQ_proofDoc()))
			execSQL += " and "+getQ_workKind()+".proofdoc like '%" + getQ_proofDoc() + "%'" ;
		if (!"".equals(getQ_proofNoS())) 
				execSQL += " and "+getQ_workKind()+".proofno >= " + Common.sqlChar(getQ_proofNoS());		
		if (!"".equals(getQ_proofNoE())) 
			execSQL += " and "+getQ_workKind()+".proofno <= " + Common.sqlChar(getQ_proofNoE());		 
		if (!"".equals(getQ_closing()))
			execSQL+=" and "+getQ_workKind()+".closing = " + Common.sqlChar(getQ_closing()) ;
		if (!"".equals(getQ_enterDateS()))
			execSQL+=" and "+getQ_workKind()+"." + enterDateName + " >= " + Common.sqlChar(new Datetime().changeTaiwanYYMMDD(getQ_enterDateS(),"2"));
		if (!"".equals(getQ_enterDateE()))
			execSQL+=" and "+getQ_workKind()+"." + enterDateName + " <= " + Common.sqlChar(new Datetime().changeTaiwanYYMMDD(getQ_enterDateE(),"2"));
		if (!"".equals(getQ_buyDateS()))
			execSQL+=" and a.buydate >= " + Common.sqlChar(new Datetime().changeTaiwanYYMMDD(getQ_buyDateS(),"2"));
		if (!"".equals(getQ_buyDateE()))	
		  execSQL+=" and a.buydate <= " + Common.sqlChar(new Datetime().changeTaiwanYYMMDD(getQ_buyDateE(),"2"));
			
		if (!"".equals(getQ_propertyNoS()))
			execSQL+=" and a.propertyno >= " + Common.sqlChar(getQ_propertyNoS());		
		if (!"".equals(getQ_propertyNoE()))
			execSQL+=" and a.propertyno <= " + Common.sqlChar(getQ_propertyNoE());
		
		if (!"".equals(getQ_differenceKind()))
			execSQL+=" and a.differencekind = " + Common.sqlChar(getQ_differenceKind()) ;
		
		if (!"".equals(getQ_propertyName1()))
			execSQL += " and a.propertyname1 like " + Common.sqlChar("%" + getQ_propertyName1() + "%");
		
		
		if (!"".equals(getQ_propertyKind()))
			execSQL+=" and a.propertykind = " + Common.sqlChar(getQ_propertyKind()) ;
		if (!"".equals(getQ_fundType()))
			execSQL+=" and a.fundtype = " + Common.sqlChar(getQ_fundType()) ;
		if (!"".equals(getQ_valuable()))
			execSQL+=" and a.valuable = " + Common.sqlChar(getQ_valuable()) ;	    
		if (!"".equals(getQ_serialNoS()))
			execSQL+=" and a.serialno >= " + Common.sqlChar(getQ_serialNoS());
		if (!"".equals(getQ_serialNoE()))
			execSQL+=" and a.serialno <= " + Common.sqlChar(getQ_serialNoE());			
		if (!"".equals(getOriginalKeepUnit()))
			execSQL+=" and a.keepunit = " + Common.sqlChar(getOriginalKeepUnit()) ;	    
		
		if (!"".equals(getOriginalKeeper())) { // 保管人
			if ("n".equals(this.getQ_workKind())) { // 移動單時  保管人條件為指定 newkeeper
				execSQL+=" and o.newkeeper = " + Common.sqlChar(getOriginalKeeper()) ;
			} else {
				execSQL+=" and a.keeper = " + Common.sqlChar(getOriginalKeeper()) ;
			}
		}
		
		if (!"".equals(getOriginalUseUnit()))
			execSQL+=" and a.useunit = " + Common.sqlChar(getOriginalUseUnit()) ;	    
		if (!"".equals(getOriginalUser()))
			execSQL+=" and a.userno = " + Common.sqlChar(getOriginalUser()) ;
		
		if (!"".equals(getOriginalUserNote()))
			execSQL+=" and a.usernote like " + Common.sqlChar("%"+getOriginalUserNote()+"%") ;	
		
		if (!"".equals(getOriginalPlace1()))
			execSQL+=" and a.place1 = " + Common.sqlChar(getOriginalPlace1()) ;	
		if (!"".equals(getOriginalPlace()))
			execSQL+=" and a.place like " + Common.sqlChar("%"+getOriginalPlace()+"%") ;
		if (!"".equals(getQ_caseSerialNoS())) {
			execSQL+=" and o.caseserialno >= " + Common.sqlChar(getQ_caseSerialNoS());
		}
		if (!"".equals(getQ_caseSerialNoE())) {
			execSQL+=" and o.caseserialno <= " + Common.sqlChar(getQ_caseSerialNoE());
		}
		
		
		return execSQL;
		
	}
	
	private String getSQLForRTOther(){

		String execSQL = " select distinct a.enterorg, a.ownership, a.keepunit, a.keeper, a.differencekind, " +"\n"+
	 			 		 " 		  d.organaname as enterOrgName,( select g.placename from SYSCA_PLACE g where g.placeno=a.place1 and g.enterorg=a.enterorg)as placeName ," +"\n"+
	 			 		 "       a.place1 ," + //1041029 存置地點排序用
	 			 		 " 		  (select z.codename from SYSCA_CODE z where 1=1 and (z.codekindid='owa' or z.codekindid='OWA') and a.ownership=z.codeid) as ownershipName, " +"\n"+
	 			 		 " 		  case a.propertykind when '01' then '公務用' when '02' then '公共用' when '03' then'事業用' when '04' then '非公用' else '' end  propertyKindName, " +"\n"+
	 			 		 "		  a.propertyno, a.serialno, " +
	 			 		 " 		 (select e.limityear from SYSPK_PROPERTYCODE e where a.propertyno = e.propertyno and e.enterorg in('000000000A',a.enterorg) and e.propertytype='1') as limitYear, " +"\n"+
	 			 		 " 		 a.otherlimityear, "+"\n"+
	 			 		 " 		 (select e.propertyname from SYSPK_PROPERTYCODE e where a.propertyno = e.propertyno and e.enterorg in('000000000A',a.enterorg) and e.propertytype='1') as propertyName, " +"\n"+
	 			 		 " 		 a.buydate,a.sourcedate, '' as specification,";
	
		if("a".equals(getQ_workKind())){
			execSQL += "		isnull(a.propertyname1,'') propertyname1,";
		}else if("j".equals(getQ_workKind())){
			execSQL += "		isnull(j.propertyname1,'') propertyname1,";
		}else if("l".equals(getQ_workKind())){
			execSQL += "		isnull(l.propertyname1,'') propertyname1,";
		}else if("n".equals(getQ_workKind())){
			execSQL += "		isnull(o.propertyname1,'') propertyname1,";
		}
		
		execSQL += "		isnull(f.keepername,'') keeperName, a.place, " +"\n"+
				   " 		 a.bookvalue , p.unitname "+"\n"+
				   "       ,(select k.unitname from UNTMP_KEEPUNIT k where k.enterorg = a.enterorg and k.unitno = a.keepunit) as keepunitName ";
		if("n".equals(getQ_workKind())) {
			execSQL += " ,(select unitname from UNTMP_KEEPUNIT z where z.enterorg = o.enterorg and z.unitno = o.newkeepunit) as newkeepunit" +
					   " ,(select placename from SYSCA_PLACE z where z.enterorg = o.enterorg and z.placeno = o.newplace1) as newplace1 " +
					   " ,o.newplace1 as newplaceCode, o.newkeeper ";
		}
		execSQL += "      from UNTRT_ADDPROOF a" +		          
				   " 		left join UNTRT_ADJUSTPROOF j on j.enterorg=a.enterorg and j.ownership=a.ownership and j.differencekind=a.differencekind and j.propertyno=a.propertyno and j.serialno=a.serialno" +"\n"+
				   " 		left join UNTRT_REDUCEPROOF l on l.enterorg=a.enterorg and l.ownership=a.ownership and l.differencekind=a.differencekind and l.propertyno=a.propertyno and l.serialno=a.serialno" +"\n"+
				   " 		left join UNTMP_MOVEDETAIL o join UNTMP_MOVEPROOF n on n.enterorg=o.enterorg and n.ownership=o.ownership and n.caseno=o.caseno on o.enterorg=a.enterorg and o.ownership=a.ownership and o.propertyno=a.propertyno and o.differencekind=a.differencekind and o.serialno=a.serialno";
		if("n".equals(getQ_workKind())){
			execSQL += " left join UNTMP_KEEPER f on a.enterorg=f.enterorg and o.newkeeper=f.keeperno";
		} else {
			execSQL += " left join UNTMP_KEEPER f on a.enterorg=f.enterorg and a.keeper=f.keeperno";
		}
		execSQL += " left join UNTMP_KEEPUNIT p on a.enterorg = p.enterorg and a.keepunit = p.unitno" +
				   " 		, SYSCA_ORGAN d "+"\n"+
				   " where 1=1 and a.enterorg = d.organid " +"\n"+
				   "";
		
		String enterDateName = "";
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
			execSQL +=" and a.datastate = " + util.Common.sqlChar(getQ_dataState());
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
		if (!"".equals(getQ_proofYear()))
			execSQL += " and "+getQ_workKind()+".proofyear = " + Common.sqlChar(getQ_proofYear()) ;
		if (!"".equals(getQ_proofDoc()))
			execSQL += " and "+getQ_workKind()+".proofdoc like '%" + getQ_proofDoc() + "%'" ;
		if (!"".equals(getQ_proofNoS())) 
				execSQL += " and "+getQ_workKind()+".proofno >= " + Common.sqlChar(getQ_proofNoS());		
		if (!"".equals(getQ_proofNoE())) 
			execSQL += " and "+getQ_workKind()+".proofno <= " + Common.sqlChar(getQ_proofNoE());		 
		if (!"".equals(getQ_closing()))
			execSQL+=" and "+getQ_workKind()+".closing = " + Common.sqlChar(getQ_closing()) ;
		if (!"".equals(getQ_enterDateS()))
			execSQL+=" and "+getQ_workKind()+"." + enterDateName + " >= " + Common.sqlChar(new Datetime().changeTaiwanYYMMDD(getQ_enterDateS(),"2"));
		if (!"".equals(getQ_enterDateE()))
			execSQL+=" and "+getQ_workKind()+"." + enterDateName + " <= " + Common.sqlChar(new Datetime().changeTaiwanYYMMDD(getQ_enterDateE(),"2"));
		if (!"".equals(getQ_buyDateS()))
			execSQL+=" and a.buydate >= " + Common.sqlChar(new Datetime().changeTaiwanYYMMDD(getQ_buyDateS(),"2"));
		if (!"".equals(getQ_buyDateE()))	
		   execSQL+=" and a.buydate <= " + Common.sqlChar(new Datetime().changeTaiwanYYMMDD(getQ_buyDateE(),"2"));
			
		if (!"".equals(getQ_propertyNoS()))
			execSQL+=" and a.propertyno >= " + Common.sqlChar(getQ_propertyNoS());		
		if (!"".equals(getQ_propertyNoE()))
			execSQL+=" and a.propertyno <= " + Common.sqlChar(getQ_propertyNoE());
		
		if (!"".equals(getQ_differenceKind()))
			execSQL+=" and a.differencekind = " + Common.sqlChar(getQ_differenceKind()) ;
		
		if (!"".equals(getQ_propertyName1()))
			execSQL += " and a.propertyname1 like " + Common.sqlChar("%" + getQ_propertyName1() + "%");
		
		
		if (!"".equals(getQ_propertyKind()))
			execSQL+=" and a.propertykind = " + Common.sqlChar(getQ_propertyKind()) ;
		if (!"".equals(getQ_fundType()))
			execSQL+=" and a.fundtype = " + Common.sqlChar(getQ_fundType()) ;
		if (!"".equals(getQ_valuable()))
			execSQL+=" and a.valuable = " + Common.sqlChar(getQ_valuable()) ;	    
		if (!"".equals(getQ_serialNoS()))
			execSQL+=" and a.serialno >= " + Common.sqlChar(getQ_serialNoS());
		if (!"".equals(getQ_serialNoE()))
			execSQL+=" and a.serialno <= " + Common.sqlChar(getQ_serialNoE());			
		if (!"".equals(getOriginalKeepUnit()))
			execSQL+=" and a.keepunit = " + Common.sqlChar(getOriginalKeepUnit()) ;	    
		
		if (!"".equals(getOriginalKeeper())) { // 保管人
			if ("n".equals(this.getQ_workKind())) { // 移動單時  保管人條件為指定 newkeeper
				execSQL+=" and o.newkeeper = " + Common.sqlChar(getOriginalKeeper()) ;
			} else {
				execSQL+=" and a.keeper = " + Common.sqlChar(getOriginalKeeper()) ;
			}
		}
		
		if (!"".equals(getOriginalUseUnit()))
			execSQL+=" and a.useunit = " + Common.sqlChar(getOriginalUseUnit()) ;	    
		if (!"".equals(getOriginalUser()))
			execSQL+=" and a.userno = " + Common.sqlChar(getOriginalUser()) ;
		
		if (!"".equals(getOriginalUserNote()))
			execSQL+=" and a.usernote like " + Common.sqlChar("%"+getOriginalUserNote()+"%") ;	
		
		if (!"".equals(getOriginalPlace1()))
			execSQL+=" and a.place1 = " + Common.sqlChar(getOriginalPlace1()) ;	
		if (!"".equals(getOriginalPlace()))
			execSQL+=" and a.place like " + Common.sqlChar("%"+getOriginalPlace()+"%") ;	
		if (!"".equals(getQ_caseSerialNoS())) {
			execSQL+=" and o.caseserialno >= " + Common.sqlChar(getQ_caseSerialNoS());
		}
		if (!"".equals(getQ_caseSerialNoE())) {
			execSQL+=" and o.caseserialno <= " + Common.sqlChar(getQ_caseSerialNoE());
		}
		
		
		return execSQL;
		
	}
	
	/**
	 * 其他如 增加單(a), 增減值單(j), 移動單(n)
	 * @return
	 */
	private String getSQLForOther() {
		
		StringBuilder sql = new StringBuilder();
		sql.append(" select * from ( ").append("\n");
		sql.append(getSQLForLAOther()).append("\n");
		sql.append(" union "+"\n"+getSQLForBUOther()).append("\n");
		sql.append(" union "+"\n"+getSQLForRFOther()).append("\n");
		sql.append(" union "+"\n"+getSQLForMPOther()).append("\n");
		sql.append(" union "+"\n"+getSQLForVPOther()).append("\n");
		sql.append(" union "+"\n"+getSQLForRTOther()).append("\n");
		sql.append(" ) aa  ");
		sql.append(this.getOrderBySQL());
		return sql.toString();
		
	}
	
	/**
	 * 現存專用
	 * @return
	 */
	private String getSQLForTypeD() {
		StringBuilder sql = new StringBuilder();
		sql.append(" select * from ( ");
		sql.append(getSQLForLATypeD());
		sql.append(" union "+getSQLForBUTypeD());
		sql.append(" union "+getSQLForRFTypeD());
		sql.append(" union "+getSQLForMPTypeD());
		sql.append(" union "+getSQLForVPTypeD());
		sql.append(" union "+getSQLForRTTypeD());
		sql.append(" ) aa  ");
		sql.append(this.getOrderBySQLForTypeD());
		return sql.toString();
	}
	
	private String getSQLForLATypeD() {
		StringBuilder sql = new StringBuilder();
		
		sql.append(" select distinct c.enterorg, c.ownership, c.keepunit, c.propertyno, c.serialno")
		   .append(" ,c.keeper, c.differencekind")
		   .append(" ,d.organaname as enterOrgName")
		   .append(" ,(select g.placename from SYSCA_PLACE g where g.placeno=c.place1 and g.enterorg=c.enterorg)as placeName")
		   .append(" ,c.place1")	//1041029 存置地點排序用
		   .append(" ,(select z.codename from SYSCA_CODE z where (z.codekindid='owa' or z.codekindid='OWA') and c.ownership=z.codeid) as ownershipName")
		   .append(" ,case c.propertykind when '01' then '公務用' when '02' then '公共用' when '03' then'事業用' when '04' then '非公用' else '' end  propertyKindName")
		   .append(" ,(select e.limityear from SYSPK_PROPERTYCODE e where c.propertyno = e.propertyno and e.enterorg in('000000000A',c.enterorg) and e.propertytype='1') as limitYear")
		   .append(" ,0 as otherlimityear")
		   .append(" ,(select e.propertyname from SYSPK_PROPERTYCODE e where c.propertyno = e.propertyno and e.enterorg in('000000000A',c.enterorg) and e.propertytype='1') as propertyName")
		   .append(" ,c.buydate, c.sourcedate, '' as specification")
		   .append(" ,isnull(f.keepername,'') keeperName, c.place")
		   .append(" ,c.bookvalue, p.unitname")
		   .append(" ,(select k.unitname from UNTMP_KEEPUNIT k where k.enterorg = c.enterorg and k.unitno = c.keepunit) as keepunitName")
		   .append(" ,isnull(c.propertyname1,'') propertyname1");
		   
		sql.append(" from UNTLA_LAND c ")
		   .append(" left join SYSCA_ORGAN d on c.enterorg = d.organid ")
		   .append(" left join UNTMP_KEEPER f on c.enterorg=f.enterorg and c.keeper=f.keeperno ")
		   .append(" left join UNTMP_KEEPUNIT p on c.enterorg = p.enterorg and c.keepunit = p.unitno")
		   .append(" where 1=1 ");
		
		sql.append(" and c.verify='Y' ")
		   .append(" and c.enterorg=c.enterorg and c.ownership=c.ownership  and c.differencekind = c.differencekind and c.propertyno=c.propertyno ");
		
		if (!"".equals(getQ_enterOrg())) {
			sql.append(" and c.enterorg = ").append(Common.sqlChar(getQ_enterOrg()));
		} else {
			if (!getIsAdminManager().equalsIgnoreCase("Y")) {
				if ("Y".equals(getIsOrganManager())) {	
					sql.append(" and ( c.enterorg = ").append(Common.sqlChar(getOrganID()))
					   .append(" or c.enterorg in ( select organid from SYSCA_ORGAN where organsuperior = ")
					   .append(Common.sqlChar(getOrganID())).append("))");
				} else {
					sql.append(" and c.enterorg = ").append(Common.sqlChar(getOrganID()));
				}
			}
		}
		
		if (!"".equals(Common.get(getQ_dataState()))){
			sql.append(" and c.datastate = ").append(Common.sqlChar(getQ_dataState()));
		}
		if (!"".equals(Common.get(getQ_ownership()))){
			sql.append(" and c.ownership = ").append(Common.sqlChar(getQ_ownership()));
		}
			
		if (!"".equals(getQ_buyDateS())) {
			sql.append(" and c.buydate >= ").append(Common.sqlChar(new Datetime().changeTaiwanYYMMDD(getQ_buyDateS(),"2")));
		}
		if (!"".equals(getQ_buyDateE())) {	
			sql.append(" and c.buydate <= ").append(Common.sqlChar(new Datetime().changeTaiwanYYMMDD(getQ_buyDateE(),"2")));
		}
			
		if (!"".equals(getQ_propertyNoS())) {
			sql.append(" and c.propertyno >= ").append(Common.sqlChar(getQ_propertyNoS()));
		}
		if (!"".equals(getQ_propertyNoE())) {
			sql.append(" and c.propertyno <= ").append(Common.sqlChar(getQ_propertyNoE()));
		}
		
		if (!"".equals(getQ_differenceKind())) {
			sql.append(" and c.differencekind = ").append(Common.sqlChar(getQ_differenceKind()));
		}
		
		if (!"".equals(getQ_propertyName1())) {
			sql.append(" and c.propertyname1 like ").append(Common.sqlChar("%" + getQ_propertyName1() + "%"));
		}
		
		if (!"".equals(getQ_propertyKind())) {
			sql.append(" and c.propertykind = ").append(Common.sqlChar(getQ_propertyKind()));
		}
		
		if (!"".equals(getQ_fundType())) {
			sql.append(" and c.fundtype = ").append(Common.sqlChar(getQ_fundType()));
		}
		if (!"".equals(getQ_valuable())) { 
			sql.append(" and c.valuable = ").append(Common.sqlChar(getQ_valuable()));
		}
		if (!"".equals(getQ_serialNoS())) {
			sql.append(" and c.serialno >= ").append(Common.sqlChar(getQ_serialNoS()));
		}
		if (!"".equals(getQ_serialNoE())) {
			sql.append(" and c.serialno <= ").append(Common.sqlChar(getQ_serialNoE()));
		}
		if (!"".equals(getOriginalKeepUnit())) {
			sql.append(" and c.keepunit = ").append(Common.sqlChar(getOriginalKeepUnit()));
		}
		if (!"".equals(getOriginalKeeper())) {
			sql.append(" and c.keeper = ").append(Common.sqlChar(getOriginalKeeper()));
		}
		if (!"".equals(getOriginalUseUnit())) {
			sql.append(" and c.useunit = ").append(Common.sqlChar(getOriginalUseUnit()));
		}
		if (!"".equals(getOriginalUser())) {
			sql.append(" and c.userno = ").append(Common.sqlChar(getOriginalUser()));
		}
		if (!"".equals(getOriginalUserNote())) {
			sql.append(" and  c.usernote like ").append(Common.sqlChar("%"+getOriginalUserNote()+"%"));
		}
		if (!"".equals(getOriginalPlace1())) {
			sql.append(" and c.place1 = ").append(Common.sqlChar(getOriginalPlace1()));
		}
		if (!"".equals(getOriginalPlace())) {
			sql.append(" and c.place like  ").append(Common.sqlChar("%"+getOriginalPlace()+"%"));
		}
		
		return sql.toString();
	}
	
	private String getSQLForBUTypeD() {
		StringBuilder sql = new StringBuilder();
		
		sql.append(" select distinct c.enterorg, c.ownership, c.keepunit, c.propertyno, c.serialno")
		   .append(" ,c.keeper, c.differencekind")
		   .append(" ,d.organaname as enterOrgName")
		   .append(" ,(select g.placename from SYSCA_PLACE g where g.placeno=c.place1 and g.enterorg=c.enterorg)as placeName")
		   .append(" ,c.place1")	//1041029 存置地點排序用
		   .append(" ,(select z.codename from SYSCA_CODE z where (z.codekindid='owa' or z.codekindid='OWA') and c.ownership=z.codeid) as ownershipName")
		   .append(" ,case c.propertykind when '01' then '公務用' when '02' then '公共用' when '03' then'事業用' when '04' then '非公用' else '' end  propertyKindName")
		   .append(" ,(select e.limityear from SYSPK_PROPERTYCODE e where c.propertyno = e.propertyno and e.enterorg in('000000000A',c.enterorg) and e.propertytype='1') as limitYear")
		   .append(" ,c.otherlimityear")
		   .append(" ,(select e.propertyname from SYSPK_PROPERTYCODE e where c.propertyno = e.propertyno and e.enterorg in('000000000A',c.enterorg) and e.propertytype='1') as propertyName")
		   .append(" ,c.buydate, c.sourcedate, '' as specification")
		   .append(" ,isnull(f.keepername,'') keeperName, c.place")
		   .append(" ,c.bookvalue, p.unitname")
		   .append(" ,(select k.unitname from UNTMP_KEEPUNIT k where k.enterorg = c.enterorg and k.unitno = c.keepunit) as keepunitName")
		   .append(" ,isnull(c.propertyname1,'') propertyname1");
		   
		sql.append(" from UNTBU_BUILDING c ")
		   .append(" left join SYSCA_ORGAN d on c.enterorg = d.organid ")
		   .append(" left join UNTMP_KEEPER f on c.enterorg=f.enterorg and c.keeper=f.keeperno ")
		   .append(" left join UNTMP_KEEPUNIT p on c.enterorg = p.enterorg and c.keepunit = p.unitno")
		   .append(" where 1=1 ");
		
		sql.append(" and c.verify='Y' ")
		   .append(" and c.enterorg=c.enterorg and c.ownership=c.ownership  and c.differencekind = c.differencekind and c.propertyno=c.propertyno ");
		
		if (!"".equals(getQ_enterOrg())) {
			sql.append(" and c.enterorg = ").append(Common.sqlChar(getQ_enterOrg()));
		} else {
			if (!getIsAdminManager().equalsIgnoreCase("Y")) {
				if ("Y".equals(getIsOrganManager())) {	
					sql.append(" and ( c.enterorg = ").append(Common.sqlChar(getOrganID()))
					   .append(" or c.enterorg in ( select organid from SYSCA_ORGAN where organsuperior = ")
					   .append(Common.sqlChar(getOrganID())).append("))");
				} else {
					sql.append(" and c.enterorg = ").append(Common.sqlChar(getOrganID()));
				}
			}
		}
		
		if (!"".equals(Common.get(getQ_dataState()))){
			sql.append(" and c.datastate = ").append(Common.sqlChar(getQ_dataState()));
		}
		if (!"".equals(Common.get(getQ_ownership()))){
			sql.append(" and c.ownership = ").append(Common.sqlChar(getQ_ownership()));
		}
			
		if (!"".equals(getQ_buyDateS())) {
			sql.append(" and c.buydate >= ").append(Common.sqlChar(new Datetime().changeTaiwanYYMMDD(getQ_buyDateS(),"2")));
		}
		if (!"".equals(getQ_buyDateE())) {	
			sql.append(" and c.buydate <= ").append(Common.sqlChar(new Datetime().changeTaiwanYYMMDD(getQ_buyDateE(),"2")));
		}
			
		if (!"".equals(getQ_propertyNoS())) {
			sql.append(" and c.propertyno >= ").append(Common.sqlChar(getQ_propertyNoS()));
		}
		if (!"".equals(getQ_propertyNoE())) {
			sql.append(" and c.propertyno <= ").append(Common.sqlChar(getQ_propertyNoE()));
		}
		
		if (!"".equals(getQ_differenceKind())) {
			sql.append(" and c.differencekind = ").append(Common.sqlChar(getQ_differenceKind()));
		}
		
		if (!"".equals(getQ_propertyName1())) {
			sql.append(" and c.propertyname1 like ").append(Common.sqlChar("%" + getQ_propertyName1() + "%"));
		}
		
		if (!"".equals(getQ_propertyKind())) {
			sql.append(" and c.propertykind = ").append(Common.sqlChar(getQ_propertyKind()));
		}
		
		if (!"".equals(getQ_fundType())) {
			sql.append(" and c.fundtype = ").append(Common.sqlChar(getQ_fundType()));
		}
		if (!"".equals(getQ_valuable())) { 
			sql.append(" and c.valuable = ").append(Common.sqlChar(getQ_valuable()));
		}
		if (!"".equals(getQ_serialNoS())) {
			sql.append(" and c.serialno >= ").append(Common.sqlChar(getQ_serialNoS()));
		}
		if (!"".equals(getQ_serialNoE())) {
			sql.append(" and c.serialno <= ").append(Common.sqlChar(getQ_serialNoE()));
		}
		if (!"".equals(getOriginalKeepUnit())) {
			sql.append(" and c.keepunit = ").append(Common.sqlChar(getOriginalKeepUnit()));
		}
		if (!"".equals(getOriginalKeeper())) {
			sql.append(" and c.keeper = ").append(Common.sqlChar(getOriginalKeeper()));
		}
		if (!"".equals(getOriginalUseUnit())) {
			sql.append(" and c.useunit = ").append(Common.sqlChar(getOriginalUseUnit()));
		}
		if (!"".equals(getOriginalUser())) {
			sql.append(" and c.userno = ").append(Common.sqlChar(getOriginalUser()));
		}
		if (!"".equals(getOriginalUserNote())) {
			sql.append(" and  c.usernote like ").append(Common.sqlChar("%"+getOriginalUserNote()+"%"));
		}
		if (!"".equals(getOriginalPlace1())) {
			sql.append(" and c.place1 = ").append(Common.sqlChar(getOriginalPlace1()));
		}
		if (!"".equals(getOriginalPlace())) {
			sql.append(" and c.place like  ").append(Common.sqlChar("%"+getOriginalPlace()+"%"));
		}
		
		return sql.toString();
	}
	
	private String getSQLForRFTypeD() {
		StringBuilder sql = new StringBuilder();
		
		sql.append(" select distinct c.enterorg, c.ownership, c.keepunit, c.propertyno, c.serialno")
		   .append(" ,c.keeper, c.differencekind")
		   .append(" ,d.organaname as enterOrgName")
		   .append(" ,(select g.placename from SYSCA_PLACE g where g.placeno=c.place1 and g.enterorg=c.enterorg)as placeName")
		   .append(" ,c.place1")	//1041029 存置地點排序用
		   .append(" ,(select z.codename from SYSCA_CODE z where (z.codekindid='owa' or z.codekindid='OWA') and c.ownership=z.codeid) as ownershipName")
		   .append(" ,case c.propertykind when '01' then '公務用' when '02' then '公共用' when '03' then'事業用' when '04' then '非公用' else '' end  propertyKindName")
		   .append(" ,(select e.limityear from SYSPK_PROPERTYCODE e where c.propertyno = e.propertyno and e.enterorg in('000000000A',c.enterorg) and e.propertytype='1') as limitYear")
		   .append(" ,c.otherlimityear")
		   .append(" ,(select e.propertyname from SYSPK_PROPERTYCODE e where c.propertyno = e.propertyno and e.enterorg in('000000000A',c.enterorg) and e.propertytype='1') as propertyName")
		   .append(" ,c.buydate, c.sourcedate, '' as specification")
		   .append(" ,isnull(f.keepername,'') keeperName, c.place")
		   .append(" ,c.bookvalue, p.unitname")
		   .append(" ,(select k.unitname from UNTMP_KEEPUNIT k where k.enterorg = c.enterorg and k.unitno = c.keepunit) as keepunitName")
		   .append(" ,isnull(c.propertyname1,'') propertyname1");
		   
		sql.append(" from UNTRF_ATTACHMENT c ")
		   .append(" left join SYSCA_ORGAN d on c.enterorg = d.organid ")
		   .append(" left join UNTMP_KEEPER f on c.enterorg=f.enterorg and c.keeper=f.keeperno ")
		   .append(" left join UNTMP_KEEPUNIT p on c.enterorg = p.enterorg and c.keepunit = p.unitno")
		   .append(" where 1=1 ");
		
		sql.append(" and c.verify='Y' ")
		   .append(" and c.enterorg=c.enterorg and c.ownership=c.ownership  and c.differencekind = c.differencekind and c.propertyno=c.propertyno ");
		
		if (!"".equals(getQ_enterOrg())) {
			sql.append(" and c.enterorg = ").append(Common.sqlChar(getQ_enterOrg()));
		} else {
			if (!getIsAdminManager().equalsIgnoreCase("Y")) {
				if ("Y".equals(getIsOrganManager())) {	
					sql.append(" and ( c.enterorg = ").append(Common.sqlChar(getOrganID()))
					   .append(" or c.enterorg in ( select organid from SYSCA_ORGAN where organsuperior = ")
					   .append(Common.sqlChar(getOrganID())).append("))");
				} else {
					sql.append(" and c.enterorg = ").append(Common.sqlChar(getOrganID()));
				}
			}
		}
		
		if (!"".equals(Common.get(getQ_dataState()))){
			sql.append(" and c.datastate = ").append(Common.sqlChar(getQ_dataState()));
		}
		if (!"".equals(Common.get(getQ_ownership()))){
			sql.append(" and c.ownership = ").append(Common.sqlChar(getQ_ownership()));
		}
			
		if (!"".equals(getQ_buyDateS())) {
			sql.append(" and c.buydate >= ").append(Common.sqlChar(new Datetime().changeTaiwanYYMMDD(getQ_buyDateS(),"2")));
		}
		if (!"".equals(getQ_buyDateE())) {	
			sql.append(" and c.buydate <= ").append(Common.sqlChar(new Datetime().changeTaiwanYYMMDD(getQ_buyDateE(),"2")));
		}
			
		if (!"".equals(getQ_propertyNoS())) {
			sql.append(" and c.propertyno >= ").append(Common.sqlChar(getQ_propertyNoS()));
		}
		if (!"".equals(getQ_propertyNoE())) {
			sql.append(" and c.propertyno <= ").append(Common.sqlChar(getQ_propertyNoE()));
		}
		
		if (!"".equals(getQ_differenceKind())) {
			sql.append(" and c.differencekind = ").append(Common.sqlChar(getQ_differenceKind()));
		}
		
		if (!"".equals(getQ_propertyName1())) {
			sql.append(" and c.propertyname1 like ").append(Common.sqlChar("%" + getQ_propertyName1() + "%"));
		}
		
		if (!"".equals(getQ_propertyKind())) {
			sql.append(" and c.propertykind = ").append(Common.sqlChar(getQ_propertyKind()));
		}
		
		if (!"".equals(getQ_fundType())) {
			sql.append(" and c.fundtype = ").append(Common.sqlChar(getQ_fundType()));
		}
		if (!"".equals(getQ_valuable())) { 
			sql.append(" and c.valuable = ").append(Common.sqlChar(getQ_valuable()));
		}
		if (!"".equals(getQ_serialNoS())) {
			sql.append(" and c.serialno >= ").append(Common.sqlChar(getQ_serialNoS()));
		}
		if (!"".equals(getQ_serialNoE())) {
			sql.append(" and c.serialno <= ").append(Common.sqlChar(getQ_serialNoE()));
		}
		if (!"".equals(getOriginalKeepUnit())) {
			sql.append(" and c.keepunit = ").append(Common.sqlChar(getOriginalKeepUnit()));
		}
		if (!"".equals(getOriginalKeeper())) {
			sql.append(" and c.keeper = ").append(Common.sqlChar(getOriginalKeeper()));
		}
		if (!"".equals(getOriginalUseUnit())) {
			sql.append(" and c.useunit = ").append(Common.sqlChar(getOriginalUseUnit()));
		}
		if (!"".equals(getOriginalUser())) {
			sql.append(" and c.userno = ").append(Common.sqlChar(getOriginalUser()));
		}
		if (!"".equals(getOriginalUserNote())) {
			sql.append(" and  c.usernote like ").append(Common.sqlChar("%"+getOriginalUserNote()+"%"));
		}
		if (!"".equals(getOriginalPlace1())) {
			sql.append(" and c.place1 = ").append(Common.sqlChar(getOriginalPlace1()));
		}
		if (!"".equals(getOriginalPlace())) {
			sql.append(" and c.place like  ").append(Common.sqlChar("%"+getOriginalPlace()+"%"));
		}
		
		return sql.toString();
	}
	
	private String getSQLForMPTypeD() {
		StringBuilder sql = new StringBuilder();
		
		sql.append(" select distinct b.enterorg, b.ownership, c.keepunit, c.propertyno, c.serialno")
		   .append(" ,c.keeper, b.differencekind")
		   .append(" ,d.organaname as enterOrgName")
		   .append(" ,(select g.placename from SYSCA_PLACE g where g.placeno=c.place1 and g.enterorg=c.enterorg)as placeName")
		   .append(" ,c.place1")	//1041029 存置地點排序用
		   .append(" ,(select z.codename from SYSCA_CODE z where (z.codekindid='owa' or z.codekindid='OWA') and c.ownership=z.codeid) as ownershipName")
		   .append(" ,case b.propertykind when '01' then '公務用' when '02' then '公共用' when '03' then'事業用' when '04' then '非公用' else '' end  propertyKindName")
		   .append(" ,(select e.limityear from SYSPK_PROPERTYCODE e where c.propertyno = e.propertyno and e.enterorg in('000000000A',b.enterorg) and e.propertytype='1') as limitYear")
		   .append(" ,b.otherlimityear")
		   .append(" ,(select e.propertyname from SYSPK_PROPERTYCODE e where c.propertyno = e.propertyno and e.enterorg in('000000000A',b.enterorg) and e.propertytype='1') as propertyName")
		   .append(" ,b.buydate, b.sourcedate, b.specification")
		   .append(" ,isnull(f.keepername,'') keeperName, c.place")
		   .append(" ,c.bookvalue, p.unitname")
		   .append(" ,(select k.unitname from UNTMP_KEEPUNIT k where k.enterorg = c.enterorg and k.unitno = c.keepunit) as keepunitName");
		
		
		   sql.append(" ,isnull(c.propertyname1,'') propertyname1");

		
		sql.append(" from UNTMP_MOVABLE b, UNTMP_MOVABLEDETAIL c ")
		   .append(" left join SYSCA_ORGAN d on c.enterorg = d.organid ")
		   .append(" left join UNTMP_KEEPER f on c.enterorg=f.enterorg and c.keeper=f.keeperno ")
		   .append(" left join UNTMP_KEEPUNIT p on c.enterorg = p.enterorg and c.keepunit = p.unitno")
		   .append(" where 1=1 ");
		
		sql.append(" and c.verify='Y' ")
		   .append(" and c.enterorg=b.enterorg and c.ownership=b.ownership and b.lotno=c.lotno and b.differencekind = c.differencekind and b.propertyno=c.propertyno ");
		
		if (!"".equals(getQ_enterOrg())) {
			sql.append(" and b.enterorg = ").append(Common.sqlChar(getQ_enterOrg()));
		} else {
			if (!getIsAdminManager().equalsIgnoreCase("Y")) {
				if ("Y".equals(getIsOrganManager())) {	
					sql.append(" and ( b.enterorg = ").append(Common.sqlChar(getOrganID()))
					   .append(" or b.enterorg in ( select organid from SYSCA_ORGAN where organsuperior = ")
					   .append(Common.sqlChar(getOrganID())).append("))");
				} else {
					sql.append(" and b.enterorg = ").append(Common.sqlChar(getOrganID()));
				}
			}
		}
		
		if (!"".equals(Common.get(getQ_dataState()))){
			sql.append(" and b.datastate = ").append(Common.sqlChar(getQ_dataState()));
			sql.append(" and c.datastate = ").append(Common.sqlChar(getQ_dataState()));
		}
		if (!"".equals(Common.get(getQ_ownership()))){
			sql.append(" and b.ownership = ").append(Common.sqlChar(getQ_ownership()));
		}
			
		if (!"".equals(getQ_buyDateS())) {
			sql.append(" and b.buydate >= ").append(Common.sqlChar(new Datetime().changeTaiwanYYMMDD(getQ_buyDateS(),"2")));
		}
		if (!"".equals(getQ_buyDateE())) {	
			sql.append(" and b.buydate <= ").append(Common.sqlChar(new Datetime().changeTaiwanYYMMDD(getQ_buyDateE(),"2")));
		}
			
		if (!"".equals(getQ_propertyNoS())) {
			sql.append(" and b.propertyno >= ").append(Common.sqlChar(getQ_propertyNoS()));
		}
		if (!"".equals(getQ_propertyNoE())) {
			sql.append(" and b.propertyno <= ").append(Common.sqlChar(getQ_propertyNoE()));
		}
		
		if (!"".equals(getQ_differenceKind())) {
			sql.append(" and b.differencekind = ").append(Common.sqlChar(getQ_differenceKind()));
		}
		
		if (!"".equals(getQ_propertyName1())) {
			sql.append(" and c.propertyname1 like ").append(Common.sqlChar("%" + getQ_propertyName1() + "%"));
		}
		
		if (!"".equals(getQ_propertyKind())) {
			sql.append(" and b.propertykind = ").append(Common.sqlChar(getQ_propertyKind()));
		}
		
		if (!"".equals(getQ_enterDateS()))
			sql.append(" and c.enterdate >= ").append( Common.sqlChar(new Datetime().changeTaiwanYYMMDD(getQ_enterDateS(),"2")));
		if (!"".equals(getQ_enterDateE()))
			sql.append(" and c.enterdate <= ").append( Common.sqlChar(new Datetime().changeTaiwanYYMMDD(getQ_enterDateE(),"2")));

		
		if (!"".equals(getQ_fundType())) {
			sql.append(" and b.fundtype = ").append(Common.sqlChar(getQ_fundType()));
		}
		if (!"".equals(getQ_valuable())) { 
			sql.append(" and b.valuable = ").append(Common.sqlChar(getQ_valuable()));
		}
		if (!"".equals(getQ_serialNoS())) {
			sql.append(" and c.serialno >= ").append(Common.sqlChar(getQ_serialNoS()));
		}
		if (!"".equals(getQ_serialNoE())) {
			sql.append(" and c.serialno <= ").append(Common.sqlChar(getQ_serialNoE()));
		}
		if (!"".equals(getOriginalKeepUnit())) {
			sql.append(" and c.keepunit = ").append(Common.sqlChar(getOriginalKeepUnit()));
		}
		if (!"".equals(getOriginalKeeper())) {
			sql.append(" and c.keeper = ").append(Common.sqlChar(getOriginalKeeper()));
		}
		if (!"".equals(getOriginalUseUnit())) {
			sql.append(" and c.useunit = ").append(Common.sqlChar(getOriginalUseUnit()));
		}
		if (!"".equals(getOriginalUser())) {
			sql.append(" and c.userno = ").append(Common.sqlChar(getOriginalUser()));
		}
		if (!"".equals(getOriginalUserNote())) {
			sql.append(" and  c.usernote like ").append(Common.sqlChar("%"+getOriginalUserNote()+"%"));
		}
		if (!"".equals(getOriginalPlace1())) {
			sql.append(" and c.place1 = ").append(Common.sqlChar(getOriginalPlace1()));
		}
		if (!"".equals(getOriginalPlace())) {
			sql.append(" and c.place like  ").append(Common.sqlChar("%"+getOriginalPlace()+"%"));
		}
		
		return sql.toString();
	}
	
	private String getSQLForVPTypeD() {
		StringBuilder sql = new StringBuilder();
		
		sql.append(" select distinct c.enterorg, c.ownership, c.keepunit, c.propertyno, c.serialno")
		   .append(" ,c.keeper, c.differencekind")
		   .append(" ,d.organaname as enterOrgName")
		   .append(" ,(select g.placename from SYSCA_PLACE g where g.placeno=c.place1 and g.enterorg=c.enterorg)as placeName")
		   .append(" ,c.place1")	//1041029 存置地點排序用
		   .append(" ,(select z.codename from SYSCA_CODE z where (z.codekindid='owa' or z.codekindid='OWA') and c.ownership=z.codeid) as ownershipName")
		   .append(" ,case c.propertykind when '01' then '公務用' when '02' then '公共用' when '03' then'事業用' when '04' then '非公用' else '' end  propertyKindName")
		   .append(" ,(select e.limityear from SYSPK_PROPERTYCODE e where c.propertyno = e.propertyno and e.enterorg in('000000000A',c.enterorg) and e.propertytype='1') as limitYear")
		   .append(" ,0 as otherlimityear")
		   .append(" ,(select e.propertyname from SYSPK_PROPERTYCODE e where c.propertyno = e.propertyno and e.enterorg in('000000000A',c.enterorg) and e.propertytype='1') as propertyName")
		   .append(" ,c.buydate, c.sourcedate, '' as specification")
		   .append(" ,isnull(f.keepername,'') keeperName, c.place")
		   .append(" ,c.bookvalue, p.unitname")
		   .append(" ,(select k.unitname from UNTMP_KEEPUNIT k where k.enterorg = c.enterorg and k.unitno = c.keepunit) as keepunitName")
		   .append(" ,isnull(c.propertyname1,'') propertyname1");
		   
		sql.append(" from UNTVP_ADDPROOF c ")
		   .append(" left join SYSCA_ORGAN d on c.enterorg = d.organid ")
		   .append(" left join UNTMP_KEEPER f on c.enterorg=f.enterorg and c.keeper=f.keeperno ")
		   .append(" left join UNTMP_KEEPUNIT p on c.enterorg = p.enterorg and c.keepunit = p.unitno")
		   .append(" where 1=1 ");
		
		sql.append(" and c.verify='Y' ")
		   .append(" and c.enterorg=c.enterorg and c.ownership=c.ownership  and c.differencekind = c.differencekind and c.propertyno=c.propertyno ");
		
		if (!"".equals(getQ_enterOrg())) {
			sql.append(" and c.enterorg = ").append(Common.sqlChar(getQ_enterOrg()));
		} else {
			if (!getIsAdminManager().equalsIgnoreCase("Y")) {
				if ("Y".equals(getIsOrganManager())) {	
					sql.append(" and ( c.enterorg = ").append(Common.sqlChar(getOrganID()))
					   .append(" or c.enterorg in ( select organid from SYSCA_ORGAN where organsuperior = ")
					   .append(Common.sqlChar(getOrganID())).append("))");
				} else {
					sql.append(" and c.enterorg = ").append(Common.sqlChar(getOrganID()));
				}
			}
		}
		
		if (!"".equals(Common.get(getQ_dataState()))){
			sql.append(" and c.datastate = ").append(Common.sqlChar(getQ_dataState()));
		}
		if (!"".equals(Common.get(getQ_ownership()))){
			sql.append(" and c.ownership = ").append(Common.sqlChar(getQ_ownership()));
		}
			
		if (!"".equals(getQ_buyDateS())) {
			sql.append(" and c.buydate >= ").append(Common.sqlChar(new Datetime().changeTaiwanYYMMDD(getQ_buyDateS(),"2")));
		}
		if (!"".equals(getQ_buyDateE())) {	
			sql.append(" and c.buydate <= ").append(Common.sqlChar(new Datetime().changeTaiwanYYMMDD(getQ_buyDateE(),"2")));
		}
			
		if (!"".equals(getQ_propertyNoS())) {
			sql.append(" and c.propertyno >= ").append(Common.sqlChar(getQ_propertyNoS()));
		}
		if (!"".equals(getQ_propertyNoE())) {
			sql.append(" and c.propertyno <= ").append(Common.sqlChar(getQ_propertyNoE()));
		}
		
		if (!"".equals(getQ_differenceKind())) {
			sql.append(" and c.differencekind = ").append(Common.sqlChar(getQ_differenceKind()));
		}
		
		if (!"".equals(getQ_propertyName1())) {
			sql.append(" and c.propertyname1 like ").append(Common.sqlChar("%" + getQ_propertyName1() + "%"));
		}
		
		if (!"".equals(getQ_propertyKind())) {
			sql.append(" and c.propertykind = ").append(Common.sqlChar(getQ_propertyKind()));
		}
		
		if (!"".equals(getQ_fundType())) {
			sql.append(" and c.fundtype = ").append(Common.sqlChar(getQ_fundType()));
		}
		if (!"".equals(getQ_valuable())) { 
			sql.append(" and c.valuable = ").append(Common.sqlChar(getQ_valuable()));
		}
		if (!"".equals(getQ_serialNoS())) {
			sql.append(" and c.serialno >= ").append(Common.sqlChar(getQ_serialNoS()));
		}
		if (!"".equals(getQ_serialNoE())) {
			sql.append(" and c.serialno <= ").append(Common.sqlChar(getQ_serialNoE()));
		}
		if (!"".equals(getOriginalKeepUnit())) {
			sql.append(" and c.keepunit = ").append(Common.sqlChar(getOriginalKeepUnit()));
		}
		if (!"".equals(getOriginalKeeper())) {
			sql.append(" and c.keeper = ").append(Common.sqlChar(getOriginalKeeper()));
		}
		if (!"".equals(getOriginalUseUnit())) {
			sql.append(" and c.useunit = ").append(Common.sqlChar(getOriginalUseUnit()));
		}
		if (!"".equals(getOriginalUser())) {
			sql.append(" and c.userno = ").append(Common.sqlChar(getOriginalUser()));
		}
		if (!"".equals(getOriginalUserNote())) {
			sql.append(" and  c.usernote like ").append(Common.sqlChar("%"+getOriginalUserNote()+"%"));
		}
		if (!"".equals(getOriginalPlace1())) {
			sql.append(" and c.place1 = ").append(Common.sqlChar(getOriginalPlace1()));
		}
		if (!"".equals(getOriginalPlace())) {
			sql.append(" and c.place like  ").append(Common.sqlChar("%"+getOriginalPlace()+"%"));
		}
		
		return sql.toString();
	}
	
	private String getSQLForRTTypeD() {
		StringBuilder sql = new StringBuilder();
		
		sql.append(" select distinct c.enterorg, c.ownership, c.keepunit, c.propertyno, c.serialno")
		   .append(" ,c.keeper, c.differencekind")
		   .append(" ,d.organaname as enterOrgName")
		   .append(" ,(select g.placename from SYSCA_PLACE g where g.placeno=c.place1 and g.enterorg=c.enterorg)as placeName")
		   .append(" ,c.place1")	//1041029 存置地點排序用
		   .append(" ,(select z.codename from SYSCA_CODE z where (z.codekindid='owa' or z.codekindid='OWA') and c.ownership=z.codeid) as ownershipName")
		   .append(" ,case c.propertykind when '01' then '公務用' when '02' then '公共用' when '03' then'事業用' when '04' then '非公用' else '' end  propertyKindName")
		   .append(" ,(select e.limityear from SYSPK_PROPERTYCODE e where c.propertyno = e.propertyno and e.enterorg in('000000000A',c.enterorg) and e.propertytype='1') as limitYear")
		   .append(" ,c.otherlimityear")
		   .append(" ,(select e.propertyname from SYSPK_PROPERTYCODE e where c.propertyno = e.propertyno and e.enterorg in('000000000A',c.enterorg) and e.propertytype='1') as propertyName")
		   .append(" ,c.buydate, c.sourcedate , '' as specification")
		   .append(" ,isnull(f.keepername,'') keeperName, c.place")
		   .append(" ,c.bookvalue, p.unitname")
		   .append(" ,(select k.unitname from UNTMP_KEEPUNIT k where k.enterorg = c.enterorg and k.unitno = c.keepunit) as keepunitName")
		   .append(" ,isnull(c.propertyname1,'') propertyname1");
		   
		sql.append(" from UNTRT_ADDPROOF c ")
		   .append(" left join SYSCA_ORGAN d on c.enterorg = d.organid ")
		   .append(" left join UNTMP_KEEPER f on c.enterorg=f.enterorg and c.keeper=f.keeperno ")
		   .append(" left join UNTMP_KEEPUNIT p on c.enterorg = p.enterorg and c.keepunit = p.unitno")
		   .append(" where 1=1 ");
		
		sql.append(" and c.verify='Y' ")
		   .append(" and c.enterorg=c.enterorg and c.ownership=c.ownership  and c.differencekind = c.differencekind and c.propertyno=c.propertyno ");
		
		if (!"".equals(getQ_enterOrg())) {
			sql.append(" and c.enterorg = ").append(Common.sqlChar(getQ_enterOrg()));
		} else {
			if (!getIsAdminManager().equalsIgnoreCase("Y")) {
				if ("Y".equals(getIsOrganManager())) {	
					sql.append(" and ( c.enterorg = ").append(Common.sqlChar(getOrganID()))
					   .append(" or c.enterorg in ( select organid from SYSCA_ORGAN where organsuperior = ")
					   .append(Common.sqlChar(getOrganID())).append("))");
				} else {
					sql.append(" and c.enterorg = ").append(Common.sqlChar(getOrganID()));
				}
			}
		}
		
		if (!"".equals(Common.get(getQ_dataState()))){
			sql.append(" and c.datastate = ").append(Common.sqlChar(getQ_dataState()));
		}
		if (!"".equals(Common.get(getQ_ownership()))){
			sql.append(" and c.ownership = ").append(Common.sqlChar(getQ_ownership()));
		}
			
		if (!"".equals(getQ_buyDateS())) {
			sql.append(" and c.buydate >= ").append(Common.sqlChar(new Datetime().changeTaiwanYYMMDD(getQ_buyDateS(),"2")));
		}
		if (!"".equals(getQ_buyDateE())) {	
			sql.append(" and c.buydate <= ").append(Common.sqlChar(new Datetime().changeTaiwanYYMMDD(getQ_buyDateE(),"2")));
		}
			
		if (!"".equals(getQ_propertyNoS())) {
			sql.append(" and c.propertyno >= ").append(Common.sqlChar(getQ_propertyNoS()));
		}
		if (!"".equals(getQ_propertyNoE())) {
			sql.append(" and c.propertyno <= ").append(Common.sqlChar(getQ_propertyNoE()));
		}
		
		if (!"".equals(getQ_differenceKind())) {
			sql.append(" and c.differencekind = ").append(Common.sqlChar(getQ_differenceKind()));
		}
		
		if (!"".equals(getQ_propertyName1())) {
			sql.append(" and c.propertyname1 like ").append(Common.sqlChar("%" + getQ_propertyName1() + "%"));
		}
		
		if (!"".equals(getQ_propertyKind())) {
			sql.append(" and c.propertykind = ").append(Common.sqlChar(getQ_propertyKind()));
		}
		
		if (!"".equals(getQ_fundType())) {
			sql.append(" and c.fundtype = ").append(Common.sqlChar(getQ_fundType()));
		}
		if (!"".equals(getQ_valuable())) { 
			sql.append(" and c.valuable = ").append(Common.sqlChar(getQ_valuable()));
		}
		if (!"".equals(getQ_serialNoS())) {
			sql.append(" and c.serialno >= ").append(Common.sqlChar(getQ_serialNoS()));
		}
		if (!"".equals(getQ_serialNoE())) {
			sql.append(" and c.serialno <= ").append(Common.sqlChar(getQ_serialNoE()));
		}
		if (!"".equals(getOriginalKeepUnit())) {
			sql.append(" and c.keepunit = ").append(Common.sqlChar(getOriginalKeepUnit()));
		}
		if (!"".equals(getOriginalKeeper())) {
			sql.append(" and c.keeper = ").append(Common.sqlChar(getOriginalKeeper()));
		}
		if (!"".equals(getOriginalUseUnit())) {
			sql.append(" and c.useunit = ").append(Common.sqlChar(getOriginalUseUnit()));
		}
		if (!"".equals(getOriginalUser())) {
			sql.append(" and c.userno = ").append(Common.sqlChar(getOriginalUser()));
		}
		if (!"".equals(getOriginalUserNote())) {
			sql.append(" and  c.usernote like ").append(Common.sqlChar("%"+getOriginalUserNote()+"%"));
		}
		if (!"".equals(getOriginalPlace1())) {
			sql.append(" and c.place1 = ").append(Common.sqlChar(getOriginalPlace1()));
		}
		if (!"".equals(getOriginalPlace())) {
			sql.append(" and c.place like  ").append(Common.sqlChar("%"+getOriginalPlace()+"%"));
		}
		
		return sql.toString();
	}
	private String getSQL() {
		if ("d".equals(this.getQ_workKind())) {
			return this.getSQLForTypeD();
		} else {
			return this.getSQLForOther();
		}
	}
	
	public DefaultTableModel getResultModel() throws Exception{



		UNTMP051R01 obj = this;
		DefaultTableModel model = new javax.swing.table.DefaultTableModel();
		Database db = new Database();
		Object[] data ;
		Vector rowData = new Vector();
		try{
			String[] columns = new String[] {"propertyKindName","enterOrgName","ownershipName","propertyNo"
					,"limitYear","propertyname","buyDate","specification","keepUnitName"
					,"place","keeperName","option"
			};

			String sql = this.getSQL();
			System.out.println("\n" + sql);
			ResultSet rs = db.querySQL_NoChange(sql);
			while(rs.next()){    	
				data = new Object[12];
				data[0] = Common.get(rs.getString("propertyKindName"));//propertyKindName
				data[1] = ReportUtil.getTitleByEnterOrgCode(Common.get(rs.getString("enterOrg")), Common.get(rs.getString("differenceKind")));//enterOrgName
				data[2] = Common.get(rs.getString("ownershipName"));//ownershipName
				data[3] = "財產編號:" + Common.get(rs.getString("differencekind")) + "-" + Common.get(rs.getString("propertyNo")) + "-" + Common.get(rs.getString("serialNo"));//propertyNo
				int otherLimitMonth = "".equals(Common.get(rs.getString("otherlimityear")))? 0 : Integer.parseInt(rs.getString("otherlimityear")); // otherLimitYear以單位月分存取需轉成年
				int year = otherLimitMonth /12;
				int month = otherLimitMonth % 12;
				data[4] = "年限:" + year +"年"; //limitYear  //使用年限
				data[5] = "財產名稱:" + Common.get(rs.getString("propertyname"));//propertyname
				data[6] = "購買/取得日期:" + Common.MaskDate(new Datetime().changeTaiwanYYMMDD(rs.getString("buyDate"),"1"))+ " , " + Common.MaskDate(new Datetime().changeTaiwanYYMMDD(rs.getString("sourcedate"),"1"));//buyDate
				data[7] = "財產別名:" + Common.get(rs.getString("propertyname1"));//propertyname1
				data[8] = "單位:" + Common.get(rs.getString("keepUnitName"));//keepUnitName
				//       	data[9] = "存置地點：" + Common.get(rs.getString("place"));//place
				//        	if("Y".equals(getQ_printKeeper())){
				//        		data[10] = "保管人：" + Common.get(rs.getString("keeperName"));//keeperName
				//        	}else{
				//        		data[10] = "";
				//        	}
				data[10] = "保管人：" + Common.get(rs.getString("keeperName"));//keeperName

				if ("n".equals(this.getQ_workKind())) {
					// 移動單
					if("0".equals(getQ_printType())){
						data[11] ="保管單位："+Common.get(rs.getString("newkeepunit"));
					}else if("1".equals(getQ_printType())){
						data[11] ="存置地點："+Common.get(rs.getString("newplace1"));
					}else if("2".equals(getQ_printType())){
						data[11] ="保管人："+Common.get(rs.getString("keeperName"));
					}else if("3".equals(getQ_printType())){
						data[11] ="保管單位："+Common.get(rs.getString("newkeepunit"))+"/"+Common.get(rs.getString("newplace1"));
					}else if ("4".equals(getQ_printType())){
						data[11] ="保管單位/保管人："+Common.get(rs.getString("newkeepunit"))+"/"+Common.get(rs.getString("keeperName"));
					}else if ("5".equals(getQ_printType())){
						data[11] ="存置地點/保管人："+Common.get(rs.getString("newplace1"))+"/"+Common.get(rs.getString("keeperName"));
					}		
				} else {
					// 非移動單
					if("0".equals(getQ_printType())){
						data[11] ="保管單位："+Common.get(rs.getString("keepunitName"));
					}else if("1".equals(getQ_printType())){
						data[11] ="存置地點："+Common.get(rs.getString("placeName"));
					}else if("2".equals(getQ_printType())){
						data[11] ="保管人："+Common.get(rs.getString("keeperName"));
					}else if("3".equals(getQ_printType())){
						data[11] ="保管單位："+Common.get(rs.getString("keepunitName"))+"/"+Common.get(rs.getString("placeName"));
					}else if ("4".equals(getQ_printType())){
						data[11] ="保管單位/保管人："+Common.get(rs.getString("keepunitName"))+"/"+Common.get(rs.getString("keeperName"));
					}else if ("5".equals(getQ_printType())){
						data[11] ="存置地點/保管人："+Common.get(rs.getString("placeName"))+"/"+Common.get(rs.getString("keeperName"));
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
