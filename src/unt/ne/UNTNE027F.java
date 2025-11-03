/*
*<br>程式目的：非消耗品增減值作業--批次新增明細資料
*<br>程式代號：untne026f
*<br>程式日期：0941123
*<br>程式作者：judy
*<br>--------------------------------------------------------
*<br>修改作者　　修改日期　　　修改目的
*<br>--------------------------------------------------------
*/

package unt.ne;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Map;

import unt.ch.UNTCH_Tools;
import util.Common;
import util.Database;
import util.Datetime;
import util.SuperBean;
import util.TCGHCommon;
import TDlib_Simple.tools.src.MathTools;

public class UNTNE027F extends SuperBean{

String enterOrg;
String differenceKind;
String enterOrgName;
String ownership;
String propertyNo;
String lotNo;
String propertyName;
String serialNo;
String propertyName1;
String propertyKind;
String fundType;
String valuable;
String sourceDate;
String bookAmount;
String keepUnit;
String keepUnitName;
String keeper;
String keeperName;
String useUnit;
String useUnitName;
String userNo;
String userName;
String place;
String oldPropertyNo;
String oldPropertyName;
String oldSerialNo;
String caseNo;

String q_enterOrg; 
String q_ownership; 
String q_caseNo;
String q_adjustDate;
String q_verify;

String q_cause;
String q_cause1;
String q_dataState;
String q_propertyNoS;
String q_propertyNoE;
String q_propertyNoSName;
String q_propertyNoEName;
String q_serialNoS;
String q_serialNoE;
String q_propertyKind;
String q_fundType;
String q_valuable;
String sSQL = "";
String strKeySet[] = null;
String bookValue;
String q_differenceKind;
String q_useUnit;
String q_userNo;
String q_userNote;
String q_place1;
String q_place1Name;
String q_placeNote;
String q_keepUnit;
String q_keeper;
public String getBookValue(){ return checkGet(bookValue); }
public void setBookValue(String s){ bookValue=checkSet(s); }

public String getsSQL(){ return checkGet(sSQL); }
public void setsSQL(String s){ sSQL=checkSet(s); }
public String[] getsKeySet(){ return strKeySet; }
public void setsKeySet(String[] s){ strKeySet=s; }
public String getEnterOrg(){ return checkGet(enterOrg); }
public void setEnterOrg(String s){ enterOrg=checkSet(s); }
public String getEnterOrgName(){ return checkGet(enterOrgName); }
public void setEnterOrgName(String s){ enterOrgName=checkSet(s); }
public String getOwnership(){ return checkGet(ownership); }
public void setOwnership(String s){ ownership=checkSet(s); }
public String getPropertyNo(){ return checkGet(propertyNo); }
public void setPropertyNo(String s){ propertyNo=checkSet(s); }
public String getPropertyName(){ return checkGet(propertyName); }
public void setPropertyName(String s){ propertyName=checkSet(s); }
public String getSerialNo(){ return checkGet(serialNo); }
public void setSerialNo(String s){ serialNo=checkSet(s); }
public String getLotNo(){ return checkGet(lotNo); }
public void setLotNo(String s){ lotNo=checkSet(s); }
public String getPropertyName1(){ return checkGet(propertyName1); }
public void setPropertyName1(String s){ propertyName1=checkSet(s); }
public String getPropertyKind(){ return checkGet(propertyKind); }
public void setPropertyKind(String s){ propertyKind=checkSet(s); }
public String getFundType(){ return checkGet(fundType); }
public void setFundType(String s){ fundType=checkSet(s); }
public String getValuable(){ return checkGet(valuable); }
public void setValuable(String s){ valuable=checkSet(s); }
public String getSourceDate(){ return checkGet(sourceDate); }
public void setSourceDate(String s){ sourceDate=checkSet(s); }
public String getBookAmount(){ return checkGet(bookAmount); }
public void setBookAmount(String s){ bookAmount=checkSet(s); }
public String getKeepUnit(){ return checkGet(keepUnit); }
public void setKeepUnit(String s){ keepUnit=checkSet(s); }
public String getKeepUnitName(){ return checkGet(keepUnitName); }
public void setKeepUnitName(String s){ keepUnitName=checkSet(s); }
public String getKeeper(){ return checkGet(keeper); }
public void setKeeper(String s){ keeper=checkSet(s); }
public String getKeeperName(){ return checkGet(keeperName); }
public void setKeeperName(String s){ keeperName=checkSet(s); }
public String getUseUnit(){ return checkGet(useUnit); }
public void setUseUnit(String s){ useUnit=checkSet(s); }
public String getUseUnitName(){ return checkGet(useUnitName); }
public void setUseUnitName(String s){ useUnitName=checkSet(s); }
public String getUserNo(){ return checkGet(userNo); }
public void setUserNo(String s){ userNo=checkSet(s); }
public String getUserName(){ return checkGet(userName); }
public void setUserName(String s){ userName=checkSet(s); }
public String getPlace(){ return checkGet(place); }
public void setPlace(String s){ place=checkSet(s); }

public String getOldPropertyNo(){ return checkGet(oldPropertyNo); }
public void setOldPropertyNo(String s){ oldPropertyNo=checkSet(s); }
public String getOldPropertyName(){ return checkGet(oldPropertyName); }
public void setOldPropertyName(String s){ oldPropertyName=checkSet(s); }
public String getOldSerialNo(){ return checkGet(oldSerialNo); }
public void setOldSerialNo(String s){ oldSerialNo=checkSet(s); }
public String getCaseNo(){ return checkGet(caseNo); }
public void setCaseNo(String s){ caseNo=checkSet(s); }

public String getQ_dataState(){ return checkGet(q_dataState); }
public void setQ_dataState(String s){ q_dataState=checkSet(s); }
public String getQ_propertyNoS(){ return checkGet(q_propertyNoS); }
public void setQ_propertyNoS(String s){ q_propertyNoS=checkSet(s); }
public String getQ_propertyNoE(){ return checkGet(q_propertyNoE); }
public void setQ_propertyNoE(String s){ q_propertyNoE=checkSet(s); }
public String getQ_propertyNoSName(){ return checkGet(q_propertyNoSName); }
public void setQ_propertyNoSName(String s){ q_propertyNoSName=checkSet(s); }
public String getQ_propertyNoEName(){ return checkGet(q_propertyNoEName); }
public void setQ_propertyNoEName(String s){ q_propertyNoEName=checkSet(s); }
public String getQ_serialNoS(){ return checkGet(q_serialNoS); }
public void setQ_serialNoS(String s){ q_serialNoS=checkSet(s); }
public String getQ_serialNoE(){ return checkGet(q_serialNoE); }
public void setQ_serialNoE(String s){ q_serialNoE=checkSet(s); }
public String getQ_propertyKind(){ return checkGet(q_propertyKind); }
public void setQ_propertyKind(String s){ q_propertyKind=checkSet(s); }
public String getQ_fundType(){ return checkGet(q_fundType); }
public void setQ_fundType(String s){ q_fundType=checkSet(s); }
public String getQ_valuable(){ return checkGet(q_valuable); }
public void setQ_valuable(String s){ q_valuable=checkSet(s); }
public String getQ_caseNo(){ return checkGet(q_caseNo); }
public void setQ_caseNo(String s){ q_caseNo=checkSet(s); }
public String getQ_adjustDate(){ return checkGet(q_adjustDate); }
public void setQ_adjustDate(String s){ q_adjustDate=checkSet(s); }
public String getQ_verify(){ return checkGet(q_verify); }
public void setQ_verify(String s){ q_verify=checkSet(s); }
public String getQ_cause(){ return checkGet(q_cause); }
public void setQ_cause(String s){ q_cause=checkSet(s); }
public String getQ_cause1(){ return checkGet(q_cause1); }
public void setQ_cause1(String s){ q_cause1=checkSet(s); }
public String getQ_enterOrg(){ return checkGet(q_enterOrg); }
public void setQ_enterOrg(String s){ q_enterOrg=checkSet(s); }
public String getQ_ownership(){ return checkGet(q_ownership); }
public void setQ_ownership(String s){ q_ownership=checkSet(s); }
public String getQ_differenceKind() {return checkGet(q_differenceKind);}
public void setQ_differenceKind(String s) {q_differenceKind = checkSet(s);}
public String getQ_useUnit() {return checkGet(q_useUnit);}
public void setQ_useUnit(String qUseUnit) {q_useUnit = checkSet(qUseUnit);}
public String getQ_userNo() {return checkGet(q_userNo);}
public void setQ_userNo(String qUserNo) {q_userNo = checkSet(qUserNo);}
public String getQ_userNote() {return checkGet(q_userNote);}
public void setQ_userNote(String q_userNote) {this.q_userNote = checkSet(q_userNote);}
public String getQ_place1() {return checkGet(q_place1);}
public void setQ_place1(String q_place1) {this.q_place1 = checkSet(q_place1);}
public String getQ_place1Name() {return checkGet(q_place1Name);}
public void setQ_place1Name(String q_place1Name) {this.q_place1Name = checkSet(q_place1Name);}
public String getQ_placeNote() {return checkGet(q_placeNote);}
public void setQ_placeNote(String q_placeNote) {this.q_placeNote = checkSet(q_placeNote);}
public String getQ_keepUnit(){ return checkGet(q_keepUnit); }
public void setQ_keepUnit(String s){ q_keepUnit=checkSet(s); }
public String getQ_keeper(){ return checkGet(q_keeper); }
public void setQ_keeper(String s){ q_keeper=checkSet(s); }
public String getDifferenceKind() {	return checkGet(differenceKind);}
public void setDifferenceKind(String s) {this.differenceKind = checkSet(s);}


String material;
String otherMaterial;
String propertyUnit;
String otherPropertyUnit;
String limitYear;
String otherLimitYear;

public String getMaterial(){ return checkGet(material); }
public void setMaterial(String s){ material=checkSet(s); }
public String getOtherMaterial(){ return checkGet(otherMaterial); }
public void setOtherMaterial(String s){ otherMaterial=checkSet(s); }
public String getPropertyUnit(){ return checkGet(propertyUnit); }
public void setPropertyUnit(String s){ propertyUnit=checkSet(s); }
public String getOtherPropertyUnit(){ return checkGet(otherPropertyUnit); }
public void setOtherPropertyUnit(String s){ otherPropertyUnit=checkSet(s); }
public String getLimitYear(){ return checkGet(limitYear); }
public void setLimitYear(String s){ limitYear=checkSet(s); }
public String getOtherLimitYear(){ return checkGet(otherLimitYear); }
public void setOtherLimitYear(String s){ otherLimitYear=checkSet(s); }

String toggleAll;
public String getToggleAll(){ if (!"".equals(checkGet(toggleAll))) return "checked"; else return "unchecked"; }    
public void setToggleAll(String s){ toggleAll=checkSet(s); }    

String closing;

public String getClosing(){ return checkGet(closing); }
public void setClosing(String s){ closing=checkSet(s); }

String adjustType;
String adjustBookValue;

public String getAdjustType(){ return checkGet(adjustType); }
public void setAdjustType(String s){ adjustType=checkSet(s); }
public String getAdjustBookValue(){ return checkGet(adjustBookValue); }
public void setAdjustBookValue(String s){ adjustBookValue=checkSet(s); }
String q_addBookValue;
String q_reduceBookValue;
public String getQ_addBookValue() {return checkGet(q_addBookValue);}
public void setQ_addBookValue(String q_addBookValue) {this.q_addBookValue = checkSet(q_addBookValue);}
public String getQ_reduceBookValue() {return checkGet(q_reduceBookValue);}
public void setQ_reduceBookValue(String q_reduceBookValue) {this.q_reduceBookValue = checkSet(q_reduceBookValue);}

	//取得key的值(enterorg,differencekind,caseno,propertyno,serialno)
	String kys[] = null;
	//取得map內的資料
	private Map<String, String> detailmap;
	public String[] getKys() {return kys;}
	public void setKys(String[] kys) {this.kys = kys;}
	public Map<String, String> getDetailmap() {return detailmap;}
	public void setDetailmap(Map<String, String> detailmap) {this.detailmap = detailmap;}
	

public void update(){

	int i;
	int getcut=getKys().length;
	String[] pkkys= null;
	Database db = new Database();
	String sql="";
	MathTools mt = new MathTools();
	try{
		if(getKys()!=null){
			for(i = 0 ; i < getcut;i++){
				pkkys = getKys()[i].split(",");
				
				String newBookValue = Common.ZeroInt(detailmap.get(kys[i]+"_oldbookvalue"));
				if (!(BigDecimal.ZERO.compareTo(new BigDecimal(detailmap.get(kys[i]+"_addbookvalue"))) == 0)) {
					newBookValue = mt._addition_withBigDecimal(newBookValue, Common.ZeroInt(detailmap.get(kys[i]+"_addbookvalue".trim())));
				}
				if (!(BigDecimal.ZERO.compareTo(new BigDecimal(detailmap.get(kys[i]+"_reducebookvalue"))) == 0)) {
					newBookValue = mt._subtraction_withBigDecimal(newBookValue, Common.ZeroInt(detailmap.get(kys[i]+"_reducebookvalue".trim())));
				}
				
				String newBookUnit = Common.ZeroInt(detailmap.get(kys[i]+"_oldbookunit"));
				if (!(BigDecimal.ZERO.compareTo(new BigDecimal(detailmap.get(kys[i]+"_addbookvalue"))) == 0)) {
					newBookUnit = mt._addition_withBigDecimal(newBookUnit, Common.ZeroInt(detailmap.get(kys[i]+"_addbookvalue".trim())));
				}
				if (!(BigDecimal.ZERO.compareTo(new BigDecimal(detailmap.get(kys[i]+"_reducebookvalue"))) == 0)) {
					newBookUnit = mt._subtraction_withBigDecimal(newBookUnit, Common.ZeroInt(detailmap.get(kys[i]+"_reducebookvalue".trim())));
				}	
						sql+="update UNTNE_ADJUSTDETAIL set";
						sql+=" cause = '"+detailmap.get(kys[i]+"_cause")+"'";
						sql+=" ,newbookvalue = "+newBookValue;
						sql+=" ,newbookunit = "+newBookUnit;
						
						if(!"".equals(detailmap.get(kys[i]+"_addbookvalue")))
							sql+=" ,addbookvalue = "+detailmap.get(kys[i]+"_addbookvalue");
							
						if(!"".equals(detailmap.get(kys[i]+"_reducebookvalue")))
							sql+=" ,reducebookvalue = "+detailmap.get(kys[i]+"_reducebookvalue");
							
						if(!"".equals(detailmap.get(kys[i]+"_cause1")))
							sql+=" ,cause1 = '"+detailmap.get(kys[i]+"_cause1")+"'";
						else if(!"".equals(detailmap.get(kys[i]+"cause1")))
							sql+=" ,cause1 = '"+detailmap.get(kys[i]+"_cause1")+"'";
						
						
						if(!"".equals(detailmap.get(kys[i]+"_reducebookvalue").trim()))
							sql+=" ,reducebookunit ="+detailmap.get(kys[i]+"_reducebookvalue");
						
						if (!"".equals(detailmap.get(kys[i]+"_addbookvalue").trim()))
							sql+=" ,addbookunit ="+detailmap.get(kys[i]+"_addbookvalue");
						
						sql+=" ,editid = "+Common.sqlChar(this.getUserID());
						sql+=" ,editdate = "+Datetime.getYYYYMMDD();
						sql+=" ,edittime = "+Datetime.getHHMMSS();
				
				
				sql+=" where 1=1 and enterorg = '"+pkkys[0]+"' and caseno ='"+pkkys[1]+"' and differencekind = '"+pkkys[2]+
						"' and propertyno ='"+pkkys[3]+"' and serialno='"+pkkys[4]+"'";
				
				System.out.println(sql);
				db.excuteSQL(sql);
				setState("updateSuccess");
			}
		}
	}catch(Exception e){
		System.out.println(e);
	}
	
	
	
	
	
}
public String getList(){
	Database db = new Database();
	StringBuffer sbHTML = new StringBuffer();
	int counter=0;
	try{
		StringBuffer sbSQL = new StringBuffer();
		sbSQL.append("select ").append(
				" a.enterorg, ").append(
				" a.ownership, ").append(
				" a.caseno, ").append(
				" a.differenceKind, ").append(
				" a.caseSerialNo, ").append(
				" a.propertyNo, ").append(
				" a.serialNo, ").append(
				" (select codename from SYSCA_CODE z where z.codekindid = 'AJC' and z.codeid = a.cause) as causename, ").append(
				" a.cause,").append(
				" a.cause1,").append(
				" a.addbookvalue,").append(
				" a.reducebookvalue,").append(
				" a.oldbookvalue,").append(
				" a.propertyname1,").append(
				" a.oldbookunit").append(
				" from UNTNE_ADJUSTDETAIL a").append(
				" where 1 = 1");
		if (!"".equals(getEnterOrg())) {
			sbSQL.append(" and a.enterOrg = ").append(Common.sqlChar(getEnterOrg()));
		}
		if (!"".equals(getCaseNo())) {
			sbSQL.append(" and a.caseNo = ").append(Common.sqlChar(getCaseNo()));
		}
		if (!"".equals(getOwnership())) {
			sbSQL.append(" and a.ownership = ").append(Common.sqlChar(getOwnership()));
		}
		if (!"".equals(getDifferenceKind())) {
			sbSQL.append(" and a.differenceKind = ").append(Common.sqlChar(getDifferenceKind()));
		}
		
		
		ResultSet rs = db.querySQL(sbSQL.toString());
		//標頭列
		sbHTML.append("<table class='queryTable'  border='1'>\n");
		sbHTML.append("<tr><td class='listTH'><input type=checkbox name=toggleAll onclick=\"ToggleAll(this, document.form1, 'strKeys');\" ></td>\n");			
		sbHTML.append("<td class='listTH' align='center'>物品編號</td>\n");
		sbHTML.append("<td class='listTH' align='center'>物品分號</td>\n");
		sbHTML.append("<td class='listTH' align='center'>物品名稱</td>\n");
		sbHTML.append("<td class='listTH' align='center'>增減值原因</td>\n");
		sbHTML.append("<td class='listTH' align='center'>其他說明</td>\n");
		sbHTML.append("<td class='listTH' align='center'>增加價值</td>\n");
		sbHTML.append("<td class='listTH' align='center'>減少價值</td>\n");
		sbHTML.append("</tr>\n");
		
		while (rs.next()){
			//第一欄：checkbox  value為pk與要使用的值 塞到value後再由前端用Map回傳
			sbHTML.append("<tr>\n");
			sbHTML.append("<td class='queryTDInput'><input type=\"checkbox\" name=\"strKeys\" " )
			.append( "\" onClick=\"Toggle(this,document.form1,'strKeys');\" value =\"")
			.append(Common.get(rs.getString("enterorg"))).append(",")//0
			.append(Common.get(rs.getString("caseno"))).append(",")//1
			.append(Common.get(rs.getString("differenceKind"))).append(",")//2
			.append(Common.get(rs.getString("propertyno"))).append(",")//3
			.append(Common.get(rs.getString("serialno"))).append(",")//4
			.append(Common.get(rs.getString("oldbookvalue"))).append(",")//5
			.append(Common.get(rs.getString("oldbookunit"))).append(",")
			.append(Common.get(rs.getString("cause1")));
			sbHTML.append("\"/></td>\n");
			
			//第二欄 財產編號
			sbHTML.append("<td class='queryTDInput'>[<input type='text' class='field_PRO' size='11' maxlength='11' name='propertyno_")
			.append(rs.getString("propertyno")).append("_")//設定name
			.append(rs.getString("serialno"))//設定name
			.append( "' value='" )
			.append(Common.get(rs.getString("propertyno")))//預設值
			.append( "' readOnly=true>]</td>\n");
			
			//第三欄 財產分號
			sbHTML.append("<td class='queryTDInput'>[<input type='text' class='field_PRO' size='7' maxlength='7' name='serialno_")
			.append(rs.getString("propertyno")).append("_")//設定name
			.append(rs.getString("serialno"))//設定name
			.append( "' value='" )
			.append(Common.get(rs.getString("serialno")))//預設值
			.append( "' readOnly=true>]</td>\n");
			
			//第四欄 財產名稱
			sbHTML.append("<td class='queryTDInput'>[<input type='text' class='field_PRO' size='15' maxlength='11' name='propertyname1_")
			.append(rs.getString("propertyno")).append("_")//設定name
			.append(rs.getString("serialno"))//設定name
			.append( "' value='" )
			.append(Common.get(rs.getString("propertyname1")))//預設值
			.append( "' readOnly=true>]</td>\n");
			
			//第五欄 增減值原因   
			sbHTML.append("<td>");
			sbHTML.append(util.View._getSelectHTML("queryTDInput", "cause_"+rs.getString("propertyno")+"_"+rs.getString("serialno"),rs.getString("cause"), "AJC"));
			sbHTML.append("</td>");
			
			//第六欄 其他說明
			sbHTML.append("<td class='queryTDInput'><input type='text' class='field' size='10' maxlength='10' name='cause1_")
			.append(rs.getString("propertyno")).append("_")//設定name
			.append(rs.getString("serialno"))//設定name
			.append( "' value='" )
			.append(Common.get(rs.getString("cause1")))//預設值
			.append( "'></td>\n");
			
			//第七欄 增加價值
			sbHTML.append("<td class='queryTDInput'><input type='text' class='field' size='10' maxlength='10' name='addbookvalue_")
			.append(rs.getString("propertyno")).append("_")//設定name
			.append(rs.getString("serialno"))//設定name
			.append( "' value='" )
			.append(Common.get(rs.getString("addbookvalue")))//預設值
			.append( "'></td>\n");
			
			//第八欄 減少價值
			sbHTML.append("<td class='queryTDInput'><input type='text' class='field' size='10' maxlength='10' name='reducebookvalue_")
			.append(rs.getString("propertyno")).append("_")//設定name
			.append(rs.getString("serialno"))//設定name
			.append( "' value='" )
			.append(Common.get(rs.getString("reducebookvalue")))//預設值
			.append( "'></td>\n");
			sbHTML.append("</tr>");
		}
		sbHTML.append("</table>\n");
			
	}catch(Exception e){
		System.out.println(e);
	}
	finally {
		db.closeAll();
		}
	return sbHTML.toString();
}
	




}

