/*
*<br>程式目的：動產條碼資料批次新增
*<br>程式代號：sysmt001f
*<br>程式日期：0961023
*<br>程式作者：shan
*<br>--------------------------------------------------------
*<br>修改作者　　修改日期　　　修改目的
*<br>--------------------------------------------------------
*/

package unt.mp;

import java.sql.ResultSet;
import java.util.ArrayList;
import util.*;

public class UNTMP044F extends UNTMP044Q{
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
	
	//System.out.println(getUserID()+"-"+	Datetime.getYYYMMDD()+"-"+Datetime.getHHMMSS());
	//System.out.println(str_barcode);

	String[] addsql = new String[1];
	addsql[0] = " insert into untmp_untmp044f " + "\n"
			  + " select " + Common.sqlChar(getUserID()) + "\n"
			  + " ,p.enterorg ,o.organaname ,o.organsname " + "\n"
			  + " ,p.ownership ,decode(p.ownership,'1','市有','2','國有') as ownershipname " + "\n"
			  + " ,m.propertykind ,decode(m.propertykind,'01','公務用','02','公共用','03','事業用','04','非公用') as propertykindname " + "\n"
			  + " ,p.propertyno ,p.serialno " + "\n"
			  + " ,k.propertyname ,m.propertyname1 " + "\n"
			  + " ,p.oldpropertyno ,p.oldserialno " + "\n"
			  + " ,k.limityear ,m.otherlimityear ,m.buydate ,m.nameplate ,m.specification " + "\n"
			  + " ,p.keepunit ,u.unitname ,p.keeper  ,e.keepername " + "\n"
			  + " ,p.place ,p.barcode " + "\n"
			  + " ,m.sourcedate ,p.bookvalue " + " ," + Common.sqlChar(Datetime.getYYYMMDD()) + " ," + Common.sqlChar(Datetime.getHHMMSS()) + "\n"
			  + " from untmp_movable m ,untmp_movabledetail p ,sysca_organ o ,syspk_propertycode k ,untmp_keepunit u ,untmp_keeper e " + "\n"
			  + " where 1=1 " + "\n"
			  + " and m.enterorg=p.enterorg and m.ownership=p.ownership and m.propertyno=p.propertyno and m.lotno=p.lotno " + "\n"
			  + " and p.enterorg=o.organid " + "\n"
			  + " and p.propertyno=k.propertyno and (p.enterorg=k.enterorg or k.enterorg='000000000A') " + "\n"
			  + " and p.enterorg=u.enterorg and p.keepunit=u.unitno " + "\n"
			  + " and p.enterorg=e.enterorg(+) and p.keepunit=e.unitno(+) and p.keeper=e.keeperno(+) " + "\n"
			  + " and p.enterorg=" + Common.sqlChar(getQ_enterOrg())
			  + " and p.barcode in ( " + str_barcode + ") "
			  + "";
			  //System.out.println(addsql[0]);
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
	delsql[0] = " delete untmp_untmp044f c "
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


public String getWhereSQL(){
	String whereSQL="";
	if(!"".equals(getQ_ownership())){
		whereSQL += " and p.ownership = " + Common.sqlChar(getQ_ownership());
	}
	
	if(!"".equals(getQ_caseNoS())){
		whereSQL += " and a.caseno >= " + Common.sqlChar(getQ_caseNoS());
	}
	if(!"".equals(getQ_caseNoE())){
		whereSQL += " and a.caseno <= " + Common.sqlChar(getQ_caseNoE());
	}
	if(!"".equals(getQ_enterDateS())){
		whereSQL += " and m.enterdate >= " + Common.sqlChar(new Datetime().changeTaiwanYYMMDD(getQ_enterDateS(),"2"));
	}
	if(!"".equals(getQ_enterDateE())){
		whereSQL += " and m.enterdate <= " + Common.sqlChar(new Datetime().changeTaiwanYYMMDD(getQ_enterDateE(),"2"));
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
	//getQ_dataState()
	//getQ_verify()
	
	if(!"".endsWith(getQ_closing())){
		whereSQL += " and a.closing =" + Common.sqlChar(getQ_closing());
	}
	if(!"".endsWith(getQ_propertyKind())){
		whereSQL += "  and m.propertykind = " + Common.sqlChar(getQ_propertyKind());
	}

	if(!"".endsWith(getQ_fundType())){
		whereSQL += " and m.fundtype =" + Common.sqlChar(getQ_fundType());
	}

	if(!"".endsWith(getQ_valuable())){//珍貴財產
		whereSQL += " and m.valuable =" + Common.sqlChar(getQ_valuable());
	}
	
	if(!"".endsWith(getQ_writeDateS())){
		whereSQL += " and a.writedate >=" + Common.sqlChar(new Datetime().changeTaiwanYYMMDD(getQ_writeDateS(),"2"));
	}
	if(!"".endsWith(getQ_writeDateE())){
		whereSQL += " and a.writedate <=" + Common.sqlChar(new Datetime().changeTaiwanYYMMDD(getQ_writeDateE(),"2"));
	}
	
	//增加單編號
	if(!"".equals(getQ_proofDoc())){
		whereSQL += " and a.proofDoc <=" + Common.sqlChar(getQ_proofDoc());
	}
	if(!"".endsWith(getQ_proofNoS())){
		whereSQL += " and a.proofNo >=" + Common.sqlChar(getQ_proofNoS());
	}
	if(!"".endsWith(getQ_proofNoE())){
		whereSQL += " and a.proofNo <=" + Common.sqlChar(getQ_proofNoE());
	}

	if(!"".endsWith(getQ_keepUnit())){
		whereSQL += " and p.keepunit =" + Common.sqlChar(getQ_keepUnit());
	}
	if(!"".endsWith(getQ_keeper())){
		whereSQL += " and p.keeper =" + Common.sqlChar(getQ_keeper());
	}
	if(!"".endsWith(getQ_useUnit())){
		whereSQL += " and p.useunit =" + Common.sqlChar(getQ_useUnit());
	}
	if(!"".endsWith(getQ_userNo())){
		whereSQL += " and p.userno =" + Common.sqlChar(getQ_userNo());
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
		String sql = " select p.enterorg ,p.ownership ,p.propertyno ,p.serialno " + "\n"
				   + " ,k.propertyname ,m.propertyname1 " + "\n"
				   + " ,m.nameplate ,m.specification " + "\n"
				   + " ,p.keepunit ,p.keeper ,u.unitname ,e.keepername ,p.barcode " + "\n"
				   + " from untmp_addproof a ,untmp_movable m ,untmp_movabledetail p ,sysca_organ o ,syspk_propertycode k ,untmp_keepunit u ,untmp_keeper e " + "\n"
				   + " where 1=1 " + "\n"
				   + " and a.enterorg=m.enterorg and a.ownership=m.ownership and a.caseno=m.caseno " + "\n"
				   + " and m.enterorg=p.enterorg and m.ownership=p.ownership and m.propertyno=p.propertyno and m.lotno=p.lotno " + "\n"
				   + " and p.enterorg=o.organid " + "\n"
				   + " and p.propertyno=k.propertyno and (p.enterorg=k.enterorg or k.enterorg='000000000A') " + "\n"
				   + " and p.enterorg=u.enterorg and p.keepunit=u.unitno " + "\n"
				   + " and p.enterorg=e.enterorg(+) and p.keepunit=e.unitno(+) and p.keeper=e.keeperno(+) " + "\n"
				   + " and p.enterorg=" + Common.sqlChar(getQ_enterOrg()) + "\n"
				   //+ " and p.verify='Y' "
				   + " and p.datastate='1' "
				   + " and not exists (select l.barcode from untmp_untmp044f l where 1=1 and l.enterorg = p.enterorg and p.barcode=l.barcode and l.editid = " + Common.sqlChar(getUserID()) + ")" + "\n"
				   + getWhereSQL()
				   + " order by p.propertyno ,p.serialno " + "\n"
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


