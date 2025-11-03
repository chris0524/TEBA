/*
*<br>程式目的：
*<br>程式代號：
*<br>程式日期：
*<br>程式作者：shan
*<br>--------------------------------------------------------
*<br>修改作者　　修改日期　　　修改目的
*<br>--------------------------------------------------------
*/

package unt.ne;

import java.sql.ResultSet;
import java.util.ArrayList;
import util.*;

public class UNTNE044F extends UNTNE044Q{
	String toggleAll;
	public String getToggleAll(){ if (!"".equals(checkGet(toggleAll))) return "checked"; else return "unchecked"; }    
	public void setToggleAll(String s){ toggleAll=checkSet(s); }

	String strKeySet[] = null;
	public String[] getsKeySet(){ return strKeySet; }
	public void setsKeySet(String[] s){ strKeySet=s; }

	String enterOrg;
	public String getEnterOrg(){ return checkGet(enterOrg); }
	public void setEnterOrg(String s){ enterOrg=checkSet(s); }

	/**
	 * <br>
	 * <br>目的：依使用者挑選資料，寫入untmp_untmp044f
	 * <br>參數：無
	 * <br>傳回：無
	*/
	public void upPrintData(){
		int getcut=0;
		String str_barcode="";
		String[] strKeys = null;
		
		if(getsKeySet()!=null){
		getcut = getsKeySet().length;	//有勾選的list中資料數
		
		for (int i=0; i<getcut; i++) {	//將挑選的資料組成一字串
			strKeys = getsKeySet()[i].split(",");
			str_barcode += Common.sqlChar(strKeys[2]);
			if(i != (getcut-1)){
			str_barcode += ",";
			}
		}
		//System.out.println(str_barcode);
		String[] addsql = new String[1];
		addsql[0] = " insert into untne_untne044f " + "\n"
				  + " select "+ Common.sqlChar(getUserID())+ " as editid " + "\n"
				  + " ,p.enterorg ,o.organaname ,o.organsname " + "\n"
				  + " ,p.ownership ,decode(p.ownership,'1','市有','2','國有','3','KOC') as ownershipname " + "\n"
				  + " ,n.propertykind ,decode(n.propertykind,'01','公務用','02','公共用','03','事業用','04','非公用') as propertykindname " + "\n"
				  + " ,p.propertyno ,p.serialno ,k.propertyname ,n.propertyname1 " + "\n"
				  + " ,p.oldpropertyno ,p.oldserialno " + "\n"
				  + " ,k.limityear ,n.otherlimityear " + "\n"
				  + " ,n.buydate ,n.nameplate ,n.specification " + "\n"
				  + " ,p.keepunit ,u.unitname as unitnam " + "\n"
				  + " ,p.keeper ,r.keepername as keepername " + "\n"
				  + " ,p.place ,p.barcode " + "\n"
				  + " ,n.sourcedate ,p.bookvalue " + "\n"
				  + " ," + Common.sqlChar(Datetime.getYYYMMDD()) + "as editdate ," +Common.sqlChar(Datetime.getHHMMSS())+ "as edittime " + "\n"
				  + " from untne_addproof a,untne_nonexp n,untne_nonexpdetail p ,syspk_propertycode2 k ,untmp_keepunit u ,untmp_keeper r ,sysca_organ o " + "\n"
				  + " where 1=1 " + "\n"
				  + " and a.enterorg=n.enterorg and a.ownership=n.ownership and a.caseno=n.caseno " + "\n"
				  + " and p.enterorg=n.enterorg(+) and p.ownership=n.ownership(+) and p.propertyno=n.propertyno(+) and p.lotno=n.lotno(+) " + "\n" 
				  + " and p.propertyno = k.propertyno(+) and p.enterorg=k.enterorg(+) " + "\n"
				  + " and p.enterorg=u.enterorg(+) and p.keepunit=u.unitno(+) " + "\n"
				  + " and p.enterorg=r.enterorg and p.keepunit=r.unitno and p.keeper=r.keeperno " + "\n"
				  + " and p.enterorg=o.organid " + "\n"
				  + " and p.datastate='1' " + "\n"
				  + " and p.enterorg= " + Common.sqlChar(getQ_enterOrg()) + "\n"
				  + " and p.barcode in ( " + str_barcode + ") "
				  + " order by p.propertyno ,p.serialno ,p.useunit ,p.userno"
				  ;
				  System.out.println(addsql[0]);
		Database db = new Database();
		try{
			db.excuteSQL(addsql);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.closeAll();
		}
		}//if(getsKeySet()!=null)
	}

	/**
	 * <br>
	 * <br>目的：依使用者機關刪除untmp_untmp044f資料
	 * <br>參數：無
	 * <br>傳回：無
	*/
	public void clearClickdata(){
		String[] delsql = new String[1];
		delsql[0] = " delete untne_untne044f c "
				  + " where 1=1 "
				  + " and c.editid= " + Common.sqlChar(getUserID())
				  + "";
				  //System.out.println(delsql[0]);
		Database db = new Database();
		try{
			db.excuteSQL(delsql);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.closeAll();
		}
	}

	/**
	 * <br>
	 * <br>目的：所有查詢欄位變數檔設定
	 * <br>參數：無
	 * <br>傳回：無
	*/
	public String getWhereSQL(){
		String whereSQL=""; 
		if (!"".equals(Common.get(getQ_ownership()))){
			whereSQL += " and a.ownership = " + util.Common.sqlChar(getQ_ownership());    	
		}
		if(!"".equals(getQ_caseNoS())){
			whereSQL += " and a.caseno >= " + Common.sqlChar(getQ_caseNoS());		
		}
		if(!"".equals(getQ_caseNoE())){
			whereSQL += " and a.caseno <= " + Common.sqlChar(getQ_caseNoE());		
		}
    	if (!"".equals(Common.get(getQ_enterDateS()))){
    		whereSQL += " and n.enterdate >= " + util.Common.sqlChar(new Datetime().changeTaiwanYYMMDD(getQ_enterDateS(),"2"));
    	}
    	if (!"".equals(Common.get(getQ_enterDateE()))){
    		whereSQL += " and n.enterdate <= " + util.Common.sqlChar(new Datetime().changeTaiwanYYMMDD(getQ_enterDateE(),"2"));
    	}
    	if(!"".equals(getQ_propertyNoS())){
    		whereSQL += " and p.propertyno >= " + Common.sqlChar(getQ_propertyNoS());
    	}
    	if(!"".equals(getQ_propertyNoE())){
    		whereSQL += " and p.propertyno <= " + Common.sqlChar(getQ_propertyNoE());
    	}
    	if(!"".equals(getQ_serialNoS())){
    		whereSQL += " and p.serialno >= " + Common.sqlChar(getQ_serialNoS());
    	}
    	if(!"".equals(getQ_serialNoE())){
    		whereSQL += " and p.serialno <= " + Common.sqlChar(getQ_serialNoE());
    	}
    	if(!"".equals(getQ_closing())){
    		whereSQL += " and p.closing = " + Common.sqlChar(getQ_closing());
    	}
    	if(!"".equals(getQ_propertyKind())){
    		whereSQL += " and n.propertykind = " + Common.sqlChar(getQ_propertyKind());
    	}
    	if(!"".equals(getQ_fundType())){
    		whereSQL += " and n.fundtype = " + Common.sqlChar(getQ_fundType());
    	}
    	if(!"".equals(getQ_writeDateS())){
    		whereSQL += " and a.writedate >= " + Common.sqlChar(new Datetime().changeTaiwanYYMMDD(getQ_writeDateS(),"2"));
    	}
    	if(!"".equals(getQ_enterDateE())){
    		whereSQL += " and a.writedate <= " + Common.sqlChar(new Datetime().changeTaiwanYYMMDD(getQ_writeDateE(),"2"));
    	}
    	if(!"".equals(getQ_proofDoc())){
    		whereSQL += " and a.proofdoc = " + Common.sqlChar(getQ_proofDoc());
    	}
    	if(!"".equals(getQ_proofNoS())){
    		whereSQL += " and a.proofno >= " + Common.sqlChar(getQ_proofNoS());
    	}
    	if(!"".equals(getQ_proofNoE())){
    		whereSQL += " and a.proofno <= " + Common.sqlChar(getQ_proofNoE());
    	}
		if (!"".equals(getQ_keepUnit())){
			whereSQL += " and p.keepUnit = " + Common.sqlChar(getQ_keepUnit());
		}
		if (!"".equals(getQ_keeper())){
			whereSQL += " and p.keeper = " + Common.sqlChar(getQ_keeper());
		}
		if (!"".equals(getQ_useUnit())){
			whereSQL += " and p.useUnit = " + Common.sqlChar(getQ_useUnit());
		}
		if (!"".equals(getQ_userNo())){
			whereSQL += " and p.userNo = " + Common.sqlChar(getQ_userNo());
		}
		
		return whereSQL;
	}
	
	/**
	 * <br>
	 * <br>目的：依查詢欄位查詢多筆資料
	 * <br>參數：無
	 * <br>傳回：傳回ArrayList
	*/
	public ArrayList queryAll() throws Exception{
		Database db = new Database();
		ArrayList objList=new ArrayList();
		int counter=0;
		try {
			String sql = " select p.enterorg "
	 		   		   + " ,p.ownership " + "\n"
	 		   		   + " ,p.propertyno " + "\n" 
	 		   		   + " ,p.serialno " + "\n"
	 		   		   + " ,nvl(k.propertyname,n.propertyname1) as propertyname " + "\n"
	 		   		   + " ,n.nameplate " + "\n"
	 		   		   + " ,n.specification " + "\n"
	 		   		   + " ,u.unitname " + "\n"
	 		   		   + " ,r.keepername " + "\n"
	 		   		   + " ,p.barcode " + "\n"
	 		   		   + " from untne_addproof a,untne_nonexp n,untne_nonexpdetail p ,syspk_propertycode2 k ,untmp_keepunit u ,untmp_keeper r " + "\n"
	 		   		   + " where 1=1 " + "\n"
	 		   		   + " and a.enterorg=n.enterorg and a.ownership=n.ownership and a.caseno=n.caseno " + "\n"
	 		   		   + " and p.enterorg=n.enterorg(+) and p.ownership=n.ownership(+) and p.propertyno=n.propertyno(+) and p.lotno=n.lotno(+) " + "\n"
	 		   		   + " and p.propertyno = k.propertyno(+) and p.enterorg=k.enterorg(+) " + "\n"
	 		   		   + " and p.enterorg=u.enterorg(+) and p.keepunit=u.unitno(+) " + "\n"
	 		   		   + " and p.enterorg=r.enterorg and p.keepunit=r.unitno and p.keeper=r.keeperno " + "\n"
	 		   		   + " and not exists(select l.barcode from untne_untne044f l where 1=1 and p.enterorg=l.enterorg and p.barcode=l.barcode and l.editid= " + Common.sqlChar(getUserID()) + ")" + "\n"
	 		   		   + " and p.datastate='1' " + "\n"
					   //+ " and p.verify='Y' "
	 		   		   + " and p.enterorg=" + Common.sqlChar(getQ_enterOrg())
	 		   		   + getWhereSQL()
	 		   		   + " order by p.propertyno ,p.serialno ,p.useunit ,p.userno " + "\n"
	 		   		   + "";
				System.out.println(sql);
				ResultSet rs = db.querySQL(sql);
		
				while (rs.next()) {
				String rowArray[]=new String[10];
				counter++;
				
				rowArray[0]=Common.get(rs.getString("enterorg")); 
				rowArray[1]=Common.get(rs.getString("ownership"));
				rowArray[2]=Common.get(rs.getString("propertyno")); 
				rowArray[3]=Common.get(rs.getString("serialno")); 
				rowArray[4]=Common.get(rs.getString("propertyname")); 
				rowArray[5]=Common.get(rs.getString("nameplate")); 
				rowArray[6]=Common.get(rs.getString("specification")); 
				rowArray[7]=Common.get(rs.getString("unitname"));
				rowArray[8]=Common.get(rs.getString("keepername"));
				rowArray[9]=Common.get(rs.getString("barcode"));

				objList.add(rowArray);
				if (counter==getListLimit()){
					setErrorMsg(getListLimitError());
					break;
				}
				};
			setStateQueryAllSuccess();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.closeAll();
		}
		return objList;
	}

}


