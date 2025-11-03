/*
*<br>程式目的：動產財產卡查詢檔 
*<br>程式代號：untmp007r
*<br>撰寫日期：94/11/28
*<br>程式作者：Cherry
*<br>--------------------------------------------------------
*<br>修改作者　　修改日期　　　修改目的
*<br>--------------------------------------------------------
*/

package unt.mp;

import java.sql.ResultSet;
import java.text.DecimalFormat;
import java.util.Vector;

import javax.swing.table.DefaultTableModel;

import net.sf.jasperreports.engine.data.JRTableModelDataSource;
import util.Common;
import util.Database;
import util.Datetime;

public class UNTMP007R extends UNTMP001Q{

	String shareEnterOrg;
	String shareOwnership;
	String sharePropertyNo;
	String shareSerialNo;
	String shareDifferenceKind;
	String enterDate;
	String bookUnitValue;
	String equipmentName;
	String apportionEndYM;
	String deprAmount;
	String monthDepr;
	String accumDeprYM;
	String accumDepr;
	String deprMethod;
	String checkMonth;
	String q_enterOrg;
	String q_ownership;
	String q_caseNoS;
	String q_caseNoE;
	String q_enterDateS;
	String q_enterDateE;
	String q_closing;
	String q_writeDateS;
	String q_writeDateE;
	String q_proofYear;
	String q_proofDoc;
	String q_proofNoS;
	String q_proofNoE;
	String q_verify;
	String q_propertyNo1;
	String q_propertyNo2;
	String q_propertyNo1Name;
	String q_propertyNo2Name;
	String q_serialNoS;
	String q_serialNoE;
	String q_dataState;
	String q_propertyKind;
	String q_fundType;
	String q_valuable;
	String q_taxCredit;
	String q_differenceKind;
	String q_propertyName1;
	String q_keepUnit;
	String q_keeper;
	String q_useUnit;
	String q_userNo;
	String q_userNote;
	String q_place1;
	String q_placeNote;
	String q_originalUstNote;
	String q_place1Name;
	String q_printDepr;
	private String q_reportType;						// 報表類型
	
	public String getQ_reportType() { return checkGet(q_reportType); }
	public void setQ_reportType(String q_reportType) { this.q_reportType = checkGet(q_reportType); }
	public String getCheckMonth(){ return checkGet(checkMonth); }
	public void setCheckMonth(String s){ checkMonth=checkSet(s); }
	public String getDeprMethod(){ return checkGet(deprMethod); }
	public void setDeprMethod(String s){ deprMethod=checkSet(s); }
	public String getApportionEndYM(){ return checkGet(apportionEndYM); }
	public void setApportionEndYM(String s){ apportionEndYM=checkSet(s); }
	public String getDeprAmount(){ return checkGet(deprAmount); }
	public void setDeprAmount(String s){ deprAmount=checkSet(s); }
	public String getMonthDepr(){ return checkGet(monthDepr); }
	public void setMonthDepr(String s){ monthDepr=checkSet(s); }
	public String getAccumDeprYM(){ return checkGet(accumDeprYM); }
	public void setAccumDeprYM(String s){ accumDeprYM=checkSet(s); }
	public String getAccumDepr(){ return checkGet(accumDepr); }
	public void setAccumDepr(String s){ accumDepr=checkSet(s); }
	public String getEquipmentName(){ return checkGet(equipmentName); }
	public void setEquipmentName(String s){ equipmentName=checkSet(s); }
	public String getShareEnterOrg(){ return checkGet(shareEnterOrg); }
	public void setShareEnterOrg(String s){ shareEnterOrg=checkSet(s); }
	public String getShareOwnership(){ return checkGet(shareOwnership); }
	public void setShareOwnership(String s){ shareOwnership=checkSet(s); }
	public String getSharePropertyNo(){ return checkGet(sharePropertyNo); }
	public void setSharePropertyNo(String s){ sharePropertyNo=checkSet(s); }
	public String getShareSerialNo(){ return checkGet(shareSerialNo); }
	public void setShareSerialNo(String s){ shareSerialNo=checkSet(s); }
	public String getShareDifferenceKind() {return checkGet(shareDifferenceKind);}
	public void setShareDifferenceKind(String shareDifferenceKind) {this.shareDifferenceKind = checkSet(shareDifferenceKind);}
	public String getEnterDate(){ return checkGet(enterDate); }
	public void setEnterDate(String s){ enterDate=checkSet(s); }
	public String getBookUnitValue(){ return checkGet(bookUnitValue); }
	public void setBookUnitValue(String s){ bookUnitValue=checkSet(s); }
	public String getQ_enterOrg(){ return checkGet(q_enterOrg); }
	public void setQ_enterOrg(String s){ q_enterOrg=checkSet(s); }
	public String getQ_ownership(){ return checkGet(q_ownership); }
	public void setQ_ownership(String s){ q_ownership=checkSet(s); }
	public String getQ_caseNoS(){ return checkGet(q_caseNoS); }
	public void setQ_caseNoS(String s){ q_caseNoS=checkSet(s); }
	public String getQ_caseNoE(){ return checkGet(q_caseNoE); }
	public void setQ_caseNoE(String s){ q_caseNoE=checkSet(s); }
	public String getQ_enterDateS(){ return checkGet(q_enterDateS); }
	public void setQ_enterDateS(String s){ q_enterDateS=checkSet(s); }
	public String getQ_enterDateE(){ return checkGet(q_enterDateE); }
	public void setQ_enterDateE(String s){ q_enterDateE=checkSet(s); }
	public String getQ_closing(){ return checkGet(q_closing); }
	public void setQ_closing(String s){ q_closing=checkSet(s); }
	public String getQ_writeDateS(){ return checkGet(q_writeDateS); }
	public void setQ_writeDateS(String s){ q_writeDateS=checkSet(s); }
	public String getQ_writeDateE(){ return checkGet(q_writeDateE); }
	public void setQ_writeDateE(String s){ q_writeDateE=checkSet(s); }
	public String getQ_proofYear() {return checkGet(q_proofYear);}
	public void setQ_proofYear(String qProofYear) {q_proofYear = checkSet(qProofYear);}
	public String getQ_proofDoc(){ return checkGet(q_proofDoc); }
	public void setQ_proofDoc(String s){ q_proofDoc=checkSet(s); }
	public String getQ_proofNoS(){ return checkGet(q_proofNoS); }
	public void setQ_proofNoS(String s){ q_proofNoS=checkSet(s); }
	public String getQ_proofNoE(){ return checkGet(q_proofNoE); }
	public void setQ_proofNoE(String s){ q_proofNoE=checkSet(s); }
	public String getQ_verify(){ return checkGet(q_verify); }
	public void setQ_verify(String s){ q_verify=checkSet(s); }
	public String getQ_propertyNo1(){ return checkGet(q_propertyNo1); }
	public void setQ_propertyNo1(String s){ q_propertyNo1=checkSet(s); }
	public String getQ_propertyNo1Name(){ return checkGet(q_propertyNo1Name); }
	public void setQ_propertyNo1Name(String s){ q_propertyNo1Name=checkSet(s); }
	public String getQ_propertyNo2(){ return checkGet(q_propertyNo2); }
	public void setQ_propertyNo2(String s){ q_propertyNo2=checkSet(s); }
	public String getQ_propertyNo2Name(){ return checkGet(q_propertyNo2Name); }
	public void setQ_propertyNo2Name(String s){ q_propertyNo2Name=checkSet(s); }
	public String getQ_serialNoS(){ return checkGet(q_serialNoS); }
	public void setQ_serialNoS(String s){ q_serialNoS=checkSet(s); }
	public String getQ_serialNoE(){ return checkGet(q_serialNoE); }
	public void setQ_serialNoE(String s){ q_serialNoE=checkSet(s); }
	public String getQ_dataState(){ return checkGet(q_dataState); }
	public void setQ_dataState(String s){ q_dataState=checkSet(s); }
	public String getQ_propertyKind(){ return checkGet(q_propertyKind); }
	public void setQ_propertyKind(String s){ q_propertyKind=checkSet(s); }
	public String getQ_fundType(){ return checkGet(q_fundType); }
	public void setQ_fundType(String s){ q_fundType=checkSet(s); }
	public String getQ_valuable(){ return checkGet(q_valuable); }
	public void setQ_valuable(String s){ q_valuable=checkSet(s); }
	public String getQ_taxCredit(){ return checkGet(q_taxCredit); }
	public void setQ_taxCredit(String s){ q_taxCredit=checkSet(s); }
	public String getQ_differenceKind() {return checkGet(q_differenceKind);	}
	public void setQ_differenceKind(String s) {q_differenceKind = checkSet(s);}
	public String getQ_propertyName1() {return checkGet(q_propertyName1);	}
	public void setQ_propertyName1(String s) {	q_propertyName1 = checkSet(s);	}
	public String getQ_keepUnit() {	return checkGet(q_keepUnit);	}
	public void setQ_keepUnit(String s) {	q_keepUnit = checkSet(s);	}
	public String getQ_keeper() {return checkGet(q_keeper);	}
	public void setQ_keeper(String s) {q_keeper = checkSet(s);	}
	public String getQ_useUnit() {	return checkGet(q_useUnit);	}
	public void setQ_useUnit(String s) {	q_useUnit = checkSet(s);	}
	public String getQ_userNo() {return checkGet(q_userNo);	}
	public void setQ_userNo(String s) {	q_userNo = checkSet(s);	}
	public String getQ_userNote() {	return checkGet(q_userNote);	}
	public void setQ_userNote(String s) {q_userNote = checkSet(s);	}
	public String getQ_place1() {return checkGet(q_place1);}
	public void setQ_place1(String s) {	q_place1 = checkSet(s);}
	public String getQ_placeNote() {return checkGet(q_placeNote);	}
	public void setQ_placeNote(String s) {	q_placeNote = checkSet(s);	}
   	public String getQ_originalUstNote() {return checkGet(q_originalUstNote);	}
	public void setQ_originalUstNote(String s) {	q_originalUstNote = checkSet(s);	}
	public String getQ_place1Name() {return checkGet(q_place1Name);	}
	public void setQ_place1Name(String s) {	q_place1Name = checkSet(s);}
	public String getQ_printDepr() {return checkGet(q_printDepr);	}
	public void setQ_printDepr(String s) {	q_printDepr = checkSet(s);}

	String q_workKind;
	public String getQ_workKind(){ return checkGet(q_workKind); }
	public void setQ_workKind(String s){ q_workKind=checkSet(s); }
	
	//抓取該動產之「UNTMP_Attachment2動產批號明細附屬設備檔」中資料狀態為「1:現存」者之「equipmentName名稱」，名稱與名稱之間以「、」區隔
	protected void getTotalEquipmentName() {
	    equipmentName="";
		Database db = new Database();	
		UNTMP007R obj = this;
		ResultSet rs;	
		String sql="select a.equipmentname " +
					" from UNTMP_ATTACHMENT2 a" +
					" where 1=1 and a.datastate='1'" + 
					" and a.enterorg=" + util.Common.sqlChar(obj.getShareEnterOrg())+
					" and a.ownership=" + util.Common.sqlChar(obj.getShareOwnership())+
					" and a.propertyno=" + util.Common.sqlChar(obj.getSharePropertyNo())+
					" and a.serialno=" + util.Common.sqlChar(obj.getShareSerialNo())+
					"";	
		try{
			rs = db.querySQL_NoChange(sql);
			while (rs.next()) {
			    equipmentName += ";"+rs.getString("equipmentName");
			}
			if(equipmentName.equals("")) {
			    equipmentName += ";";
			}
			equipmentName = equipmentName.substring(1); //若equipmentName為空值將";"去掉
			int equipmentNameOverLength =  String_length(equipmentName)-140;
			if(equipmentNameOverLength > 0) {	//判斷equipmentName有沒有超出長度
				equipmentName = equipmentName.substring(0,equipmentName.length()-equipmentNameOverLength);
				equipmentName = equipmentName.substring(0,equipmentName.length()-3)+"...";
				
//				 if(String_length(equipmentName.substring(0,equipmentName.lastIndexOf(";"))) > 125) { //最後一個";"距離欄位剩餘長度是否足夠放入"..."
//					equipmentName = equipmentName.substring(0,equipmentName.lastIndexOf(";"));
//					equipmentName = equipmentName.substring(0,equipmentName.lastIndexOf(";")+1)+"...";
				
//				} else {
//					equipmentName = equipmentName.substring(0,equipmentName.lastIndexOf(";")+1)+"..."; //若equipmentName過長將省略 以"..."表示
//				}

			}
			obj.setEquipmentName(equipmentName.toString());
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.closeAll();
		}

	}

public DefaultTableModel getResultModel() throws Exception{
	UNTMP007R obj = this;
    DefaultTableModel model = new javax.swing.table.DefaultTableModel();
    Database db = new Database();
    String enterDateName = "";
    try{
    	String[] columns = new String[] {"ENTERORG","OWNERSHIP","PROPERTYNOKIND","PROPERTYNO","PROPERTYNAME",
    									 "PROPERTYNAME1","PROPERTYKIND","FUNDTYPE","VALUABLE","ARTICLENAME",
    									 "LIMITYEAR","SPECIFICATION","SOURCEDATE","PROPERTYUNIT","BUYDATE",
    									 "BOOKVALUE","SOURCEKIND","MATERIAL","NOTES1","EQUIPMENTNAME",
    									 "ENTERDATE","ORIGINALNOTE","ORIGINALBV","PROOF","ORIGINALMOVEDATE",
    									 "ORIGINALPLACE","USEUNIT","USERNAME","KEEPUNIT","KEEPERNAME",
    									 "DEPRMETHOD","SCRAPVALUE","enterOrgOwnershipPropertyNoSerialNo",
    									 "subReportDataSourceProof","subReportDataSourceMoveDetail","subReportDataSourceReduce",
    									 "subReportDataSourceDepr","OLDSERIALNO","NAMEPLATE",
    									 "DIFFERENCEKIND","CASESERIALNO","BOOKAMOUNT","PRINTDEPR"};

    	String execSQL="select distinct TOP 100 b.enterorg as enterorgt, b.ownership as ownershipt, b.propertyno as propertynot, c.serialno as serialnot, " +"\n"+
    					" a.enterorg as shareenterorg, a.ownership as shareownership, b.propertyno as sharepropertyno, c.serialno as shareserialno,  c.differencekind as sharedifferencekind, " +"\n"+
    					" d.organaname as enterorg, " +"\n"+
    					" (select z.codename from SYSCA_CODE z where 1=1 and (z.codekindid='OWA' ) and a.ownership=z.codeid) as ownership, " +"\n"+
    					" ((substring(b.propertyno,1,7) + '-' + substring(b.propertyno,8,4)) + '-' + c.serialno) as propertyno, c.oldserialno, (case substring(b.propertyno,0,2) when '3' then '機械及設備' when '4' then '交通及運輸設備' when '5' then '什項設備' else '' end) propertynokind, " +"\n"+
    					" (select x.propertyname from SYSPK_PROPERTYCODE x where  b.propertyno = x.propertyno and x.enterorg in('000000000A',b.enterorg) and x.propertytype='1') as propertyname, " +"\n"+
    					" c.propertyname1, (case b.propertykind when '01' then '公務用' when '02' then '公共用' when '03' then '事業用' when '04' then '非公用' else '' end) propertykind, " +"\n"+
    					" (g.codename) as fundtype, (case b.valuable when 'Y' then '珍貴財產'  else '非珍貴財產' end) valuable, b.articlename, b.specification, b.nameplate, " +"\n"+
    					" (select x.propertyunit from SYSPK_PROPERTYCODE x where  b.propertyno = x.propertyno and x.enterorg in('000000000A',b.enterorg) and x.propertytype='1') as propertyunit, " +"\n"+
    					" b.otherpropertyunit, c.bookvalue, " +"\n"+
    					" h.codename as sourcekind, " +"\n"+
    					" (select x.material from SYSPK_PROPERTYCODE x where b.propertyno = x.propertyno and x.enterorg in('000000000A',b.enterorg) and x.propertytype='1') as material, " +"\n"+
    					" b.othermaterial, c.notes1, " +"\n"+
    					"  c.differencekind ,b.caseserialno,c.bookamount,c.deprmethod," +"\n"+
    					" (select codename from SYSCA_CODE t where codekindid='DFK' and t.codeid=c.differencekind ) as differencekindName," +"\n"+
    					" (select x.limityear from SYSPK_PROPERTYCODE x where  b.propertyno = x.propertyno and x.enterorg in('000000000A',b.enterorg) and x.propertytype='1') as limityear, " +"\n"+
    					" c.otherlimityear, b.sourcedate, b.buydate, a.enterdate, a.proofno, a.proofdoc,a.proofyear, b.originalnote, " +"\n"+
    					" c.originalbv, c.originalmovedate, " +
    					" (select j.unitname from UNTMP_KEEPUNIT j where b.enterorg=j.enterorg and c.keepunit=j.unitno) as keepunit,  " +
    					" (select k.keepername from UNTMP_KEEPER k where b.enterorg=k.enterorg  and c.keeper=k.keeperno) as keepername,  " +
    					" isnull((select placename from SYSCA_PLACE where placeno=c.place1 and enterorg in('000000000A',c.enterorg)),c.place) as originalplace,  " +"\n"+
    					" (select j.unitname from UNTMP_KEEPUNIT j where b.enterorg=j.enterorg and c.useunit=j.unitno) as useunit, " +"\n"+
    					"  (select k.keepername from UNTMP_KEEPER k where b.enterorg=k.enterorg  and c.userno=k.keeperno) as username, " +"\n"+
    					//TDCM問題單1797，修正折舊方法需顯示全名
    					"  (select x.codename from SYSCA_CODE x where x.codekindid='DEP' and x.codeid = c.deprmethod) deprmethodname, c.scrapvalue, " +"\n"+
    					"  c.depramount, c.monthdepr, c.accumdepr " +"\n"+
    					" from UNTMP_ADDPROOF a left join UNTMP_MOVABLE b on a.caseno=b.caseno and a.enterorg=b.enterorg and a.ownership=b.ownership "+"\n"+
    					" left join SYSCA_CODE h on b.sourcekind=h.codeid and h.codekindid = 'SKD' "+"\n"+
    					" left join UNTMP_MOVABLEDETAIL c on c.enterorg=b.enterorg and c.ownership=b.ownership and b.lotno=c.lotno and b.propertyno=c.propertyno"+"\n"+
    					" left join SYSCA_CODE g on b.fundtype=g.codeid and g.codekindid = 'FUD'"+"\n"+
    					" left join UNTMP_ADJUSTDETAIL k on k.enterorg=c.enterorg and k.ownership=c.ownership and k.propertyno=c.propertyno and k.lotno=c.lotno and k.serialno=c.serialno"+"\n"+
    					" left join UNTMP_ADJUSTPROOF j on j.enterorg=k.enterorg and j.ownership=k.ownership and j.caseno=k.caseno"+"\n"+
    					" left join UNTMP_REDUCEDETAIL m on m.enterorg=c.enterorg and m.ownership=c.ownership and m.propertyno=c.propertyno and m.lotno=c.lotno and m.serialno=c.serialno"+"\n"+
    					" left join UNTMP_REDUCEPROOF l on l.enterorg=m.enterorg and l.ownership=m.ownership and l.caseno=m.caseno"+"\n"+
    					" left join UNTMP_MOVEDETAIL o on  o.enterorg=c.enterorg and o.ownership=c.ownership and o.propertyno=c.propertyno and o.lotno=c.lotno and o.serialno=c.serialno"+"\n"+
    					" left join UNTMP_MOVEPROOF n on n.enterorg=o.enterorg and n.ownership=o.ownership and n.caseno=o.caseno"+"\n"+
    					" left join  SYSCA_ORGAN d on b.enterorg = d.organid " +"\n"+
    					" left join  UNTMP_KEEPER f on  b.enterorg=f.enterorg  and c.originalkeeper=f.keeperno " +"\n"+
    					" left join  UNTMP_KEEPUNIT i on b.enterorg=i.enterorg and c.originalkeepunit=i.unitno " +"\n"+
						" where 1=1  " +"\n"+
						//" and b.propertyno = e.propertyno and e.enterorg in('000000000a',b.enterorg) and e.propertytype='1' "+"\n"+
						
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
    	if (!"".equals(Common.get(getQ_enterOrg()))) {
			execSQL +=" and a.enterorg = " + Common.sqlChar(getQ_enterOrg()) ;
		} else {
			if (!getIsAdminManager().equalsIgnoreCase("Y")) {
				if ("Y".equals(getIsOrganManager())) {					
					execSQL += " and a.enterorg like '" + getOrganID().substring(0,5) + "%' ";					
				} else {
					execSQL +=" and a.enterorg = " + Common.sqlChar(getOrganID()) ;
				}
			}
		}
		if (!"".equals(Common.get(getQ_ownership())))
    		execSQL +=" and a.ownership = " + util.Common.sqlChar(getQ_ownership());
		if (!"".equals(Common.get(getQ_caseNoS())))
			execSQL +=" and "+getQ_workKind()+".caseno >= " + Common.sqlChar(Common.formatRearString(getQ_caseNoS(),10,'0'));		
		if (!"".equals(Common.get(getQ_caseNoE())))
			execSQL +=" and "+getQ_workKind()+".caseno <= " + Common.sqlChar(Common.formatRearString(getQ_caseNoE(),10, '9'));
    	if (!"".equals(getQ_proofYear())) 
			execSQL += " and "+getQ_workKind()+".proofyear = " + Common.sqlChar(getQ_proofYear());	
		if (!"".equals(Common.get(getQ_proofDoc())))
			execSQL += " and "+getQ_workKind()+".proofdoc like '%" + getQ_proofDoc() + "%'" ;
		if (!"".equals(Common.get(getQ_proofNoS()))) 
			execSQL += " and "+getQ_workKind()+".proofno >= " + Common.sqlChar(getQ_proofNoS());		
		if (!"".equals(Common.get(getQ_proofNoE()))) 
			execSQL += " and "+getQ_workKind()+".proofno <= " + Common.sqlChar(getQ_proofNoE());		 
		if (!"".equals(Common.get(getQ_writeDateS())))
    		execSQL += " and "+getQ_workKind()+".writedate >= " + util.Common.sqlChar(new Datetime().changeTaiwanYYMMDD(getQ_writeDateS(),"2"));
    	if (!"".equals(Common.get(getQ_writeDateE())))
    		execSQL += " and "+getQ_workKind()+".writedate <= " + util.Common.sqlChar(new Datetime().changeTaiwanYYMMDD(getQ_writeDateE(),"2"));
		if (!"".equals(Common.get(getQ_enterDateS())))
		    execSQL+=" and "+getQ_workKind()+"." + enterDateName + " >= " + Common.sqlChar(new Datetime().changeTaiwanYYMMDD(getQ_enterDateS(),"2"));
		if (!"".equals(Common.get(getQ_enterDateE())))
		    execSQL+=" and "+getQ_workKind()+"." + enterDateName + " <= " + Common.sqlChar(new Datetime().changeTaiwanYYMMDD(getQ_enterDateE(),"2"));
		if (!"".equals(Common.get(getQ_propertyNoS())))
		    execSQL+=" and b.propertyno >= " + Common.sqlChar(getQ_propertyNoS());		
		if (!"".equals(Common.get(getQ_propertyNoE())))
		    execSQL+=" and b.propertyno <= " + Common.sqlChar(getQ_propertyNoE());
		if (!"".equals(Common.get(getQ_dataState()))){
		    execSQL+=" and b.datastate = " + Common.sqlChar(getQ_dataState()) ;	    
			execSQL+=" and c.datastate = " + Common.sqlChar(getQ_dataState()) ;
		}
		if (!"".equals(Common.get(getQ_propertyKind())))
		    execSQL+=" and b.propertykind = " + Common.sqlChar(getQ_propertyKind()) ;
		if (!"".equals(Common.get(getQ_fundType())))
		    execSQL+=" and b.fundtype = " + Common.sqlChar(getQ_fundType()) ;
		if (!"".equals(Common.get(getQ_valuable())))
		    execSQL+=" and b.valuable = " + Common.sqlChar(getQ_valuable()) ;	    
		if (!"".equals(Common.get(getQ_serialNoS())))
		    execSQL+=" and c.serialno >= " + Common.sqlChar(getQ_serialNoS());		
		if (!"".equals(Common.get(getQ_serialNoE())))
		    execSQL+=" and c.serialno <= " + Common.sqlChar(getQ_serialNoE());			
		if (!"".equals(Common.get(getQ_keepUnit())))
		    execSQL+=" and c.keepunit = " + Common.sqlChar(getQ_keepUnit()) ;	    
		if (!"".equals(Common.get(getQ_keeper())))
		    execSQL+=" and c.keeper = " + Common.sqlChar(getQ_keeper()) ;	    
		if (!"".equals(Common.get(getQ_useUnit())))
		    execSQL+=" and c.useunit = " + Common.sqlChar(getQ_useUnit()) ;	    
		if (!"".equals(Common.get(getQ_userNo())))
		    execSQL+=" and c.userno = " + Common.sqlChar(getQ_userNo()) ;
		if(!"".equals(getQ_differenceKind()))
    		execSQL+=" and b.differencekind = " + Common.sqlChar(getQ_differenceKind()) ;
		if (!"".equals(Common.get(getQ_propertyName1())))
    		execSQL += " and b.propertyname1 like " + util.Common.sqlChar("%"+getQ_propertyName1()+"%");
		if(!"".equals(getQ_userNote()))
			execSQL+=" and c.usernote like " + Common.sqlChar("%"+getQ_userNote()+"%") ;
		if(!"".equals(getQ_place1Name()))
			execSQL+=" and c.place1 = " + Common.sqlChar(getQ_place1()) ;
		if(!"".equals(getQ_placeNote()))
			execSQL+=" and c.place like " + Common.sqlChar("%"+getQ_placeNote()+"%") ;
		execSQL+=" order by b.enterorg, b.ownership, b.propertyno, c.serialno";
    	
		//使用者操作記錄
		Common.insertRecordSql(execSQL, this.getOrganID(), this.getUserID(), "UNTMP007R", this.getObjPath().replaceAll("&gt;", ">"));
		ResultSet rs = db.querySQL_NoChange(execSQL);
		
        Vector rowData = new Vector();
        while(rs.next()){
        	obj.setShareEnterOrg(rs.getString("shareEnterOrg"));
        	obj.setShareOwnership(rs.getString("shareOwnership"));
        	obj.setSharePropertyNo(rs.getString("sharePropertyNo"));
        	obj.setShareSerialNo(rs.getString("shareSerialNo"));
        	obj.setShareDifferenceKind(rs.getString("shareDifferenceKind"));
        	//obj.setApportionEndYM(rs.getString("apportionEndYM"));
        	obj.setDeprAmount(rs.getString("deprAmount"));
        	obj.setMonthDepr(rs.getString("monthDepr"));
        	//obj.setAccumDeprYM(rs.getString("accumDeprYM"));
        	obj.setAccumDepr(rs.getString("accumDepr"));
        	obj.setDeprMethod(rs.getString("deprMethod"));
        	//obj.setCheckMonth(rs.getString("checkMonth"));
        	obj.setDifferenceKind(rs.getString("differencekind"));
        	Object[] data = new Object[columns.length];
            data[0] = rs.getString("enterOrg");
            data[1] = rs.getString("ownership");
            data[2] = rs.getString("propertyNoKind");
           	data[3] = rs.getString("propertyNo");
            data[4] = rs.getString("propertyName");
            data[5] = rs.getString("propertyName1");
           	data[6] = rs.getString("propertyKind");
            data[7] = Common.getCutStr(rs.getString("fundType"),6);
            data[8] = rs.getString("valuable");
            data[9] = rs.getString("articleName");
            
            int otherLimitMonth = "".equals(Common.get(rs.getString("otherLimitYear")))? 0 : Integer.parseInt(rs.getString("otherLimitYear")); // otherLimitYear以單位月分存取需轉成年
			int year = otherLimitMonth /12;
			int month = otherLimitMonth % 12;
			data[10] = year +"年"+month+"個月";
            
            
            data[11] = rs.getString("specification");
            if(rs.getString("sourceDate")!=null){
                data[12] = Common.MaskDate(new Datetime().changeTaiwanYYMMDD(rs.getString("sourceDate"),"1"));
            }else{
                data[12] = "";
            }
//		            if(rs.getString("propertyUnit")!=null){
//		                data[13] = rs.getString("propertyUnit");
//		            }else{
                data[13] = rs.getString("otherPropertyUnit");
//		            }
            data[14] = Common.MaskDate(new Datetime().changeTaiwanYYMMDD(rs.getString("buyDate"),"1"));
            data[15] = Common.valueFormat(rs.getString("bookValue"))+"　";
            data[16] = rs.getString("sourceKind");
//		            if(rs.getString("material")!=null){
//		                data[17] = rs.getString("material");
//		            }else{
                data[17] = rs.getString("otherMaterial");
//		            }
            data[18] = rs.getString("notes1");
            getTotalEquipmentName();
            data[19] = obj.getEquipmentName();
            data[20] = Common.MaskDate(new Datetime().changeTaiwanYYMMDD(rs.getString("enterDate"),"1"));
            data[21] = rs.getString("originalNote");
            data[22] = Common.valueFormat(rs.getString("originalBV"));
            data[23] = rs.getString("proofyear")+"年"+rs.getString("proofDoc")+" 字第 "+rs.getString("proofNo")+" 號";
            if(rs.getString("originalMoveDate")!=null){
                data[24] = Common.MaskDate(new Datetime().changeTaiwanYYMMDD(rs.getString("originalMoveDate"),"1"));
            }else{
                data[24] = "";
            }
            data[25] = rs.getString("originalPlace");
            data[26] = rs.getString("useunit");
            data[27] = rs.getString("userName");
            data[28] = rs.getString("keepUnit");
            data[29] = rs.getString("keeperName");
            data[30] = rs.getString("deprMethodName");
            data[31] = Common.valueFormat(rs.getString("scrapValue"));
            data[32] = rs.getString("shareEnterOrg")+rs.getString("shareOwnership")+rs.getString("sharePropertyNo")+rs.getString("shareSerialNo")+rs.getString("shareDifferenceKind");
            data[33] = new JRTableModelDataSource(getSubModel((String)data[0]));
            data[34] = new JRTableModelDataSource(getSubMode2((String)data[0]));
            data[35] = new JRTableModelDataSource(getSubMode3((String)data[0]));
            if("Y".equals(getQ_printDepr())){
            data[36] = new JRTableModelDataSource(getSubMode4((String)data[0]));
            data[42] = "Y";	
            }
            else {
            data[42] = "N";	
            }
//          data[37] = rs.getString("oldSerialNo");
           	data[38] = rs.getString("namePlate");
            data[39] = rs.getString("differencekind")+" "+rs.getString("differencekindName");
           	data[40] = new Integer(rs.getString("caseserialno"));
           	data[41] = rs.getString("bookamount");
            //for(i=0;i<38;i++)if(data[i]==null)data[i]="";
            rowData.addElement(data);
        }
        Object[][] data = new Object[0][0];
        data = (Object[][])rowData.toArray(data);
        model.setDataVector(data, columns);
    }catch(Exception x){
       x.printStackTrace();
    }finally{
        db.closeAll();
    }
   // System.out.println("跑完了!!!!!!!!!!");
    return model;
}

//帳務資料－多筆
public DefaultTableModel getSubModel(String caseCode) throws Exception{
	UNTMP007R obj = this;
    DefaultTableModel model = new javax.swing.table.DefaultTableModel();
    Database db = new Database();
    try{
        String[] columns = new String[] { "ENTERDATE", "PROOF","PROOFDOC","BOOKNOTES","OLDBOOKVALUE","ADJUSTBOOKVALUE","REDUCEBOOKVALUE","NEWBOOKVALUE","CASESERIALNO"};
    	String execSQL="(select a.adjustdate as enterdate, a.proofdoc, a.proofno, b.booknotes, b.oldbookvalue, b.addbookvalue,b.reducebookvalue, '1' as adjusttype, b.newbookvalue, b.caseserialno " +
    					" from UNTMP_ADJUSTPROOF a left join UNTMP_ADJUSTDETAIL b on a.enterorg=b.enterorg and a.ownership=b.ownership and a.caseno=b.caseno" +
    					" where 1=1"+
						" and a.enterorg=" + util.Common.sqlChar(obj.getShareEnterOrg())+
						" and a.ownership=" + util.Common.sqlChar(obj.getShareOwnership())+
						" and a.differencekind=" + util.Common.sqlChar(obj.getShareDifferenceKind())+
						" and b.propertyno=" + util.Common.sqlChar(obj.getSharePropertyNo())+
						" and b.serialno=" + util.Common.sqlChar(obj.getShareSerialNo())+
						" and b.verify = 'Y' " +
    					" ) " +
    					"union " +
    					"(select a.reducedate as enterdate, a.proofdoc, a.proofno, b.booknotes, b.oldbookvalue,'0' as addbookvalue, b.adjustbookvalue as reducebookvalue, '2' as adjusttype, b.newbookvalue,b.caseserialno " +
						" from UNTMP_REDUCEPROOF a left join  UNTMP_REDUCEDETAIL b on a.enterorg=b.enterorg and a.ownership=b.ownership and a.caseno=b.caseno" +
						" where 1=1"+
						" and a.enterorg=" + util.Common.sqlChar(obj.getShareEnterOrg())+
						" and a.ownership=" + util.Common.sqlChar(obj.getShareOwnership())+
						" and a.differencekind=" + util.Common.sqlChar(obj.getShareDifferenceKind())+
						" and b.propertyno=" + util.Common.sqlChar(obj.getSharePropertyNo())+
						" and b.serialno=" + util.Common.sqlChar(obj.getShareSerialNo())+
						" and b.verify = 'Y' " +
						" ) ";
    	execSQL += " order by enterdate";
    	ResultSet rs = db.querySQL_NoChange(execSQL);
    	Vector rowData = new Vector();
    	//int i;
    	while(rs.next()){
    		Object[] data = new Object[columns.length];
    		data[0] = Common.MaskDate(new Datetime().changeTaiwanYYMMDD(rs.getString("enterDate"),"1"));
    		if(rs.getString("adjustType").equals("2")){
    			data[1] = "財產減損單";
    		}else{
    			data[1] = "財產增減值單";
    		}
    		data[2] = Common.get(rs.getString("proofDoc"))+" 字第 "+Common.get(rs.getString("proofNo"))+" 號";
    		data[3] = Common.get(rs.getString("bookNotes"));
    		data[4] = new Integer(rs.getString("oldBookValue"));

    			if(rs.getString("adjustType").equals("1")){
    				data[5] = new Integer(rs.getString("addbookvalue"));
    				data[6] = new Integer(rs.getString("reducebookvalue"));
    			}else{
    				data[5] = new Integer(0);
    				data[6] = new Integer(rs.getString("reducebookvalue"));
    			}
    		

    		data[7] = new Integer(rs.getString("newBookValue"));
    		data[8] = new Integer(rs.getString("caseserialno"));
    		//for(i=0;i<8;i++)if(data[i]==null)data[i]="";
    		rowData.addElement(data);
    	}
    	
    	if(rowData.size()==0){ //若無資料也要顯示報表欄位
    		Object[] data = new Object[columns.length];
    		data[0] = "";
    		data[1] = "";
    		data[2] = "";
    		data[3] = "";
    		data[4] = new Integer("0");
    		data[5] = new Integer("0");
    		data[6] = new Integer("0");
    		data[7] = new Integer("0");
    		data[8] = new Integer("0");
    		rowData.addElement(data);
    	}
        Object[][] data = new Object[0][0];
        data = (Object[][])rowData.toArray(data);
        model.setDataVector(data, columns);
    }catch(Exception x){
    	x.printStackTrace();
    }finally{
        db.closeAll();
    }
    return model;

}

////移動紀錄－多筆
//public DefaultTableModel getSubMode2(String caseCode) throws Exception{
//	UNTMP007R obj = this;
//    DefaultTableModel model = new javax.swing.table.DefaultTableModel();
//    Database db = new Database();
//    try{
//        String[] columns = new String[] { "NEWMOVEDATE", "USEUNIT","KEEPUNIT","NEWPLACE","USERNAME","KEEPERNAME","PROOFDOCNO"};
//    	String execSQL="select  a.newmovedate, b.unitname as useunit, c.keepername as username, a.newplace," +
//						" (select d.unitname from UNTMP_KEEPUNIT d where a.enterorg=d.enterorg and a.newkeepunit=d.unitno) as keepunit, " +
//						" (select e.keepername from UNTMP_KEEPER e where a.enterorg=e.enterorg  and a.newkeeper=e.keeperno) as keepername, " +
//						" d.proofdoc, d.proofno " +
//    					" from UNTMP_MOVEDETAIL a left join UNTMP_MOVEPROOF d on d.enterorg=a.enterorg and d.ownership=a.ownership and d.caseno=a.caseno,"+
//						" UNTMP_KEEPUNIT b, UNTMP_KEEPER c  " +
//    					" where 1=1 " +
//						" and a.enterorg=b.enterorg and a.newuseunit=b.unitno " +
//						" and a.enterorg=c.enterorg and a.newuserno=c.keeperno " +
//						" and a.enterorg=" + util.Common.sqlChar(obj.getShareEnterOrg())+
//						" and a.ownership=" + util.Common.sqlChar(obj.getShareOwnership())+
//						" and a.propertyno=" + util.Common.sqlChar(obj.getSharePropertyNo())+
//						" and a.serialno=" + util.Common.sqlChar(obj.getShareSerialNo())+
//						" order by a.newmovedate ";
//    	ResultSet rs = db.querySQL_NoChange(execSQL);
//    	Vector rowData = new Vector();
//    	//int i;
//    	while(rs.next()){
//    		Object[] data = new Object[columns.length];
//    		data[0] = Common.MaskDate(new Datetime().changeTaiwanYYMMDD(rs.getString("newMoveDate"),"1"));
//    		data[1] = Common.get(rs.getString("useUnit"));
//    		data[2] = Common.get(rs.getString("keepUnit"));
//    		data[3] = Common.get(rs.getString("newPlace"));
//    		data[4] = Common.get(rs.getString("userName"));
//    		data[5] = Common.get(rs.getString("keeperName"));
//    		data[6] = Common.get(rs.getString("proofDoc"))+" 字第 "+Common.get(rs.getString("proofNo"))+" 號";
//    		//for(i=0;i<7;i++)if(data[i]==null)data[i]="";
//    		rowData.addElement(data);
//    	}
//    	
//    	if(rowData.size()==0){ //若無資料也要顯示報表欄位
//    		Object[] data = new Object[columns.length];
//    		data[0] = "";
//    		data[1] = "";
//    		data[2] = "";
//    		data[3] = "";
//    		data[4] = "";
//    		data[5] = "";
//    		data[6] = "";
//    		rowData.addElement(data);
//    	}
//    	
//        Object[][] data = new Object[0][0];
//        data = (Object[][])rowData.toArray(data);
//        model.setDataVector(data, columns);
//    }catch(Exception x){
//    	x.printStackTrace();
//    }finally{
//        db.closeAll();
//    }
//    return model;
//
//}


//異動紀錄－多筆
public DefaultTableModel getSubMode2(String caseCode) throws Exception{
	UNTMP007R obj = this;
    DefaultTableModel model = new javax.swing.table.DefaultTableModel();
    Database db = new Database();
    try{
        String[] columns = new String[] { "MOVEDATE", "PROOFDOCNO", "MOVEREASON"};
    	String execSQL=" select d.movedate, d.proofyear, d.proofdoc, d.proofno, a.olduseunit, a.newuseunit, " +
    					"  (select j.unitname from UNTMP_KEEPUNIT j where b.enterorg=j.enterorg and a.olduseunit=j.unitno) as olduseunitname,  " +
    					"  (select j.unitname from UNTMP_KEEPUNIT j where b.enterorg=j.enterorg and a.newuseunit=j.unitno) as newuseunitname  " +
    					" from UNTMP_MOVEDETAIL a left join UNTMP_MOVEPROOF d on d.enterorg=a.enterorg and d.ownership=a.ownership and d.caseno=a.caseno,"+
						" UNTMP_KEEPUNIT b, UNTMP_KEEPER c  " +
    					" where 1=1 " +
						" and a.enterorg=b.enterorg and a.newuseunit=b.unitno " +
						" and a.enterorg=c.enterorg and a.newuserno=c.keeperno " +
						" and a.enterorg=" + util.Common.sqlChar(obj.getShareEnterOrg())+
						" and a.ownership=" + util.Common.sqlChar(obj.getShareOwnership())+
						" and a.differencekind=" + util.Common.sqlChar(obj.getShareDifferenceKind())+
						" and a.propertyno=" + util.Common.sqlChar(obj.getSharePropertyNo())+
						" and a.serialno=" + util.Common.sqlChar(obj.getShareSerialNo())+
						" order by a.newmovedate ";
    	ResultSet rs = db.querySQL_NoChange(execSQL);
    	Vector rowData = new Vector();
    	//int i;
    	while(rs.next()){
    		Object[] data = new Object[columns.length];
    		data[0] = Common.MaskDate(new Datetime().changeTaiwanYYMMDD(rs.getString("movedate"),"1"));
    		data[1] = Common.get(rs.getString("proofYear"))+" 年 "+Common.get(rs.getString("proofDoc"))+" 字第 "+Common.get(rs.getString("proofNo"))+" 號";
    		data[2] = "原使用單位"+Common.get(rs.getString("olduseunitname"))+"移入"+Common.get(rs.getString("newuseunitname"));
    		rowData.addElement(data);
    	}
    	
    	if(rowData.size()==0){ //若無資料也要顯示報表欄位
    		Object[] data = new Object[columns.length];
    		data[0] = "";
    		data[1] = "";
    		data[2] = "";
    		rowData.addElement(data);
    	}
    	
        Object[][] data = new Object[0][0];
        data = (Object[][])rowData.toArray(data);
        model.setDataVector(data, columns);
    }catch(Exception x){
    	x.printStackTrace();
    }finally{
        db.closeAll();
    }
    return model;

}

//報廢(損)紀錄－多筆
public DefaultTableModel getSubMode3(String caseCode) throws Exception{
	UNTMP007R obj = this;
    DefaultTableModel model = new javax.swing.table.DefaultTableModel();
    Database db = new Database();
    try{
        String[] columns = new String[] { "ENTERDATE", "PROOF","CAUSE","APPROVE","REDUCEDEAL"};
    	String execSQL="select a.reducedate as enterdate, a.proofdoc, a.proofno, b.codename as cause, c.cause1, a.approvedoc, c.reducedeal2 " +
						" from UNTMP_REDUCEPROOF a left join UNTMP_REDUCEDETAIL c on a.enterorg=c.enterorg and a.ownership=c.ownership and a.caseno=c.caseno"+
    			        " left join SYSCA_CODE b on c.cause=b.codeid and b.codekindid = 'CAA'  "+
						" where 1=1" +
						" and a.enterorg=" + util.Common.sqlChar(obj.getShareEnterOrg())+
						" and a.ownership=" + util.Common.sqlChar(obj.getShareOwnership())+
						" and c.propertyno=" + util.Common.sqlChar(obj.getSharePropertyNo())+
						" and c.serialno=" + util.Common.sqlChar(obj.getShareSerialNo());
    	ResultSet rs = db.querySQL_NoChange(execSQL);
    	Vector rowData = new Vector();
    	//int i;
    	while(rs.next()){
    		Object[] data = new Object[columns.length];
    		data[0] = Common.MaskDate(new Datetime().changeTaiwanYYMMDD(rs.getString("enterDate"),"1"));
    		data[1] = Common.get(rs.getString("proofDoc"))+" 字第 "+Common.get(rs.getString("proofNo"))+" 號";
    		if(Common.get(rs.getString("cause")).equals("99")){
    		    data[2] = Common.get(rs.getString("cause1"));
    		}else{
    		    data[2] = Common.get(rs.getString("cause"));
    		}
    		data[3] = Common.get(rs.getString("approveDoc"));
    		data[4] = Common.get(rs.getString("reduceDeal2"));
    		//for(i=0;i<5;i++)if(data[i]==null)data[i]="";
    		rowData.addElement(data);
    	}
    	
    	if(rowData.size()==0){ //若無資料也要顯示報表欄位
    		Object[] data = new Object[columns.length];
    		data[0] = "";
    		data[1] = "";
    		data[2] = "";
    		data[3] = "";
    		data[4] = "";
    		
    		rowData.addElement(data);
    	}
    	
        Object[][] data = new Object[0][0];
        data = (Object[][])rowData.toArray(data);
        model.setDataVector(data, columns);
   
    }catch(Exception x){
    	x.printStackTrace();
    }finally{
        db.closeAll();
    }
    return model;

}

//折舊紀錄－多筆
public DefaultTableModel getSubMode4(String caseCode) throws Exception{
	UNTMP007R obj = this;
    DefaultTableModel model = new javax.swing.table.DefaultTableModel();
    Database db = new Database();
	Vector rowData = new Vector();
	//int i;
	try{
		String[] columns = new String[] { "DEPRYM", "OLDNETVALUE","MONTHDEPR","NEWACCUMDEPR","NEWNETVALUE"};
    	String execSQL="select distinct um.deprYM, um.oldNetValue,um.monthDepr,um.newAccumDepr,um.newNetValue,um.notes,"+ 
    					"case when exists(select 1 from UNTMP_ADJUSTDETAIL ua where um.enterorg = ua.enterorg " +
    					" and um.ownership = ua.ownership and um.differencekind = ua.differencekind and um.propertyno = ua.propertyno"+
    					" and um.serialno = ua.serialno and ua.cause = '98' and substring(ua.adjustdate,1,6)=um.deprym)" +
    					" then 'Y' else 'N' end as depradjust" + 
						" from UNTDP_MONTHDEPR um" +
						" where 1=1 and verify='Y' " +
						" and enterOrg=" + util.Common.sqlChar(obj.getShareEnterOrg())+
						" and ownership=" + util.Common.sqlChar(obj.getShareOwnership())+
						" and differenceKind=" + util.Common.sqlChar(obj.getDifferenceKind())+
						" and propertyNo=" + util.Common.sqlChar(obj.getSharePropertyNo())+
						" and serialNo=" + util.Common.sqlChar(obj.getShareSerialNo());
    		   execSQL+=" order by deprYM ";
    	
    	ResultSet rs = db.querySQL(execSQL);
    	    while(rs.next()){
    	        Object[] data = new Object[5];
    	        data[0] = new Datetime().changeTaiwanYYMM(Common.get(rs.getString("deprYM")),"1");
    	        data[1] = Common.valueFormat(rs.getString("oldNetValue"));
    	        //問題單1560 折舊月檔有備註時加星號、有增減值原因為折舊提列時加星號
    	        if("".equals(Common.get(rs.getString("notes"))) && "N".equals(Common.get(rs.getString("depradjust")))) {
    	        	data[2] = Common.valueFormat(rs.getString("monthDepr"));
    	        }else {
    	        	data[2] = "*"+Common.valueFormat(rs.getString("monthDepr"));
    	        }
    	        data[3] = Common.valueFormat(rs.getString("newAccumDepr"));
    	        data[4] = Common.valueFormat(rs.getString("newNetValue"));
    	        rowData.addElement(data);
    	}
        Object[][] data = new Object[0][0];
        data = (Object[][])rowData.toArray(data);
        model.setDataVector(data, columns);
   
    }catch(Exception x){
    	x.printStackTrace();
    }finally{
        db.closeAll();
    }
    return model;

}

//年度格式
public static String yearFormat(String year){
    String formatString = new String();
    DecimalFormat df = new DecimalFormat("000");
    if(year!=null && !year.equals("")){
        try{
            formatString = df.format(Double.parseDouble(year));
        }catch (NumberFormatException e) {
            formatString =year;
     }
    }else{
        formatString =year;
    }
    return formatString;
}
	public static int String_length(String value) {
	  int valueLength = 0;
	  String chinese = "[\u4e00-\u9fa5]";
	  for (int i = 0; i < value.length(); i++) {
	   String temp = value.substring(i, i + 1);
		   if (temp.matches(chinese)) {
		    valueLength += 2;
		   } else {
		    valueLength += 1;
		   }
	  }
	  return valueLength;
	}

}