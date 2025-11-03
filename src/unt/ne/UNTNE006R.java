/*
*<br>程式目的：非消耗品登記卡查詢檔 
*<br>程式代號：untne006p
*<br>撰寫日期：103/09/19
*<br>程式作者：Mike Kao
*<br>--------------------------------------------------------
*<br>修改作者　　修改日期　　　修改目的
*<br>--------------------------------------------------------
*/

package unt.ne;

import java.sql.ResultSet;
import java.util.Vector;

import javax.swing.table.DefaultTableModel;

import net.sf.jasperreports.engine.data.JRTableModelDataSource;
import util.Common;
import util.Database;
import util.Datetime;

public class UNTNE006R extends UNTNE001Q{

	String shareEnterOrg;
	String shareOwnership;
	String sharePropertyNo;
	String shareSerialNo;
	String shareDifferenceKind;
	String enterDate;
	String bookUnitValue;
	String equipmentName;
	
	String q_workKind;
	private String q_reportType;						// 報表類型
	
	public String getQ_reportType() { return checkGet(q_reportType); }
	public void setQ_reportType(String q_reportType) { this.q_reportType = checkGet(q_reportType); }
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
	public String getShareDifferenceKind(){ return checkGet(shareDifferenceKind); }
	public void setShareDifferenceKind(String s){ shareDifferenceKind=checkSet(s); }
	public String getEnterDate(){ return checkGet(enterDate); }
	public void setEnterDate(String s){ enterDate=checkSet(s); }
	public String getBookUnitValue(){ return checkGet(bookUnitValue); }
	public void setBookUnitValue(String s){ bookUnitValue=checkSet(s); }

	public String getQ_workKind(){ return checkGet(q_workKind); }
	public void setQ_workKind(String s){ q_workKind=checkSet(s); }

	//抓取該動產之「UNTNE_Attachment2動產批號明細附屬設備檔」中資料狀態為「1:現存」者之「equipmentName名稱」，名稱與名稱之間以「、」區隔
	protected void getTotalEquipmentName(){
	    equipmentName="";
		Database db = new Database();	
		UNTNE006R obj = this;
		ResultSet rs;	
		String sql="select a.equipmentName " +
					" from UNTNE_Attachment2 a" +
					" where 1=1 and a.dataState='1'" + 
					" and a.enterOrg=" + util.Common.sqlChar(obj.getShareEnterOrg())+
					" and a.ownership=" + util.Common.sqlChar(obj.getShareOwnership())+
					" and a.propertyNo=" + util.Common.sqlChar(obj.getSharePropertyNo())+
					" and a.serialNo=" + util.Common.sqlChar(obj.getShareSerialNo())+
					"";	
		try{
			rs = db.querySQL(sql);
			while (rs.next()){
			    equipmentName += "、"+rs.getString("equipmentName");
			}
			if(equipmentName.equals("")){
			    equipmentName += "、 ";
			}
			obj.setEquipmentName(equipmentName.substring(1));
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.closeAll();
		}

	}
	
public DefaultTableModel getResultModel() throws Exception{
	UNTNE006R obj = this;
    DefaultTableModel model = new javax.swing.table.DefaultTableModel();
    Database db = new Database();
    String enterDateName = ""; 
    try{
    	String[] columns = new String[] {"ENTERORG","OWNERSHIP","PROPERTYKIND","PROPERTYNO","PROPERTYNAME","OLDSERIALNO","PROPERTYNAME1","FUNDTYPE","ARTICLENAME","LIMITYEAR","SPECIFICATION","SOURCEDATE","NAMEPLATE","BUYDATE","PROPERTYUNIT","PLACE1","BOOKAMOUNT","USEUNIT","BOOKVALUE","USERNAME","SOURCEKIND","KEEPUNIT","MATERIAL","KEEPERNAME","NOTES1","EQUIPMENTNAME","ENTERDATE","ORIGINALNOTE","ORIGINALBV","PROOF","ORIGINALMOVEDATE","ORIGINALPLACE","MOVEUSEUNIT","MOVEUSERNAME","MOVEKEEPUNIT","MOVEKEEPERNAME","enterOrgOwnershipPropertyNoSerialNo","subReportDataSourceProof","subReportDataSourceMoveDetail","subReportDataSourceReduce"};

    	String execSQL="select distinct TOP 100 b.enterorg as enterorgT, b.ownership as ownershipT, b.propertyno as propertynoT, c.serialno as serialnoT,  a.enterorg as shareenterorg, a.ownership as shareOwnership,c.differencekind as sharedifferencekind, b.propertyno as sharepropertyno, c.serialno as shareserialno,  d.organaname as enterorg,  "+
    	"(select z.codename from SYSCA_CODE z where 1=1 and (z.codekindid='owa' or z.codekindid='OWA') and a.ownership=z.codeid) as ownership,  "+
    	"(b.propertyno + '-' + c.serialno) as propertyno, "+
    	"(c.oldpropertyno + '-' + c.oldserialno) as oldpropertyno, c.oldserialno, "+
    	"(case substring(b.propertyno,0,1) when '3' then '機械及設備' when '4' then '交通及運輸設備' when '5' then '什項設備' else '' end) propertynoKind, "+
    	" e.propertyname, c.propertyname1, "+
    	" (case b.propertykind when '01' then '公務用' when '02' then '公共用' when '03' then '事業用' when '04' then '非公用' else '' end) propertykind,  "+
    	"b.articlename, b.specification, b.nameplate, b.fundtype, e.propertyunit, b.otherpropertyunit, b.bookamount, c.bookvalue, " +
    	"(select placename from SYSCA_PLACE p where b.enterorg=p.enterorg and p.placeno=c.place1) as place1,c.place as place,    "+
    	"(select j.unitname from UNTMP_KEEPUNIT j where b.enterorg=j.enterorg and j.unitno=c.useunit  ) as useunit, "+ 
		"(select k.keepername from UNTMP_KEEPER k where b.enterorg=k.enterorg and k.keeperno=c.userno ) as userName,  "+
    	"(select j.unitname from UNTMP_KEEPUNIT j where b.enterorg=j.enterorg and j.unitno=c.keepunit ) as keepunit,  "+
    	"(select k.keepername from UNTMP_KEEPER k where b.enterorg=k.enterorg and k.keeperno=c.keeper) as keepername, "+
    	" h.codename as sourceKind,  e.material, b.othermaterial, c.notes1, c.otherlimityear, b.sourcedate, b.buydate, a.enterdate, a.proofno, a.proofdoc,a.proofyear, b.originalnote,  c.originalbv, c.originalmovedate, " +
    	"c.originalplace, "+
    	"(select j.unitname from UNTMP_KEEPUNIT j where b.enterorg=j.enterorg and j.unitno=o.newuseunit) as moveuseunit,  "+
    	"(select k.keepername from UNTMP_KEEPER k where b.enterorg=k.enterorg and k.keeperno=o.newuserno) as moveUserName,  "+
    	"(select j.unitname from UNTMP_KEEPUNIT j where b.enterorg=j.enterorg and j.unitno=o.newkeepunit) as movekeepunit,  "+
    	"(select k.keepername from UNTMP_KEEPER k where b.enterorg=k.enterorg and k.keeperno=o.newkeeper ) as movekeepername  "+
    	"from UNTNE_ADDPROOF a "+
    	"left join UNTNE_NONEXP b on a.caseno=b.caseno and a.differencekind = b.differencekind and a.enterorg=b.enterorg and a.ownership=b.ownership "+
    	"left join UNTNE_NONEXPDETAIL c on c.enterorg=b.enterorg and c.ownership=b.ownership AND b.differencekind = c.differencekind and b.lotno=c.lotno and b.propertyno=c.propertyno "+
    	"left join SYSCA_CODE g on b.fundtype=g.codeid and g.codekindid = 'FUD' "+
    	"left join UNTNE_ADJUSTDETAIL k on k.enterorg=c.enterorg and k.ownership=c.ownership AND k.differencekind = c.differencekind and k.propertyno=c.propertyno and k.lotno=c.lotno and k.serialno=c.serialno "+
    	"left join UNTNE_ADJUSTPROOF j on j.enterorg=k.enterorg and j.ownership=k.ownership AND j.differencekind = k.differencekind and j.caseno=k.caseno "+
    	"left join UNTNE_REDUCEDETAIL m on m.enterorg=c.enterorg and m.ownership=c.ownership AND m.differencekind = c.differencekind and m.propertyno=c.propertyno and m.lotno=c.lotno and m.serialno=c.serialno "+
    	"left join UNTNE_REDUCEPROOF l on l.enterorg=m.enterorg and l.ownership=m.ownership AND l.differencekind = m.differencekind and l.caseno=m.caseno "+
    	"left join UNTNE_MOVEDETAIL o on o.enterorg=c.enterorg and o.ownership=c.ownership AND o.differencekind = c.differencekind and o.propertyno=c.propertyno and o.lotno=c.lotno and o.serialno=c.serialno "+
    	"left join UNTNE_MOVEPROOF n on n.enterorg=o.enterorg and n.ownership=o.ownership and n.caseno=o.caseno "+
    	"left join SYSCA_CODE h on b.sourcekind=h.codeid and h.codekindid = 'SKD', "+
    	"SYSCA_ORGAN d, SYSPK_PROPERTYCODE2 e  "+
    	"where 1=1 and a.enterorg = d.organid  "+
    	"and b.enterorg = e.enterorg "+
    	"and b.propertyno = e.propertyno  ";

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
					execSQL += " and a.enterorg like '" + getOrganID().substring(0,5) + "%' ";					
				} else {
					execSQL +=" and a.enterorg = " + Common.sqlChar(getOrganID()) ;
				}
			}
		}
		if (!"".equals(Common.get(getQ_ownership())))
    		execSQL +=" and a.ownership = " + util.Common.sqlChar(getQ_ownership());
		if (!"".equals(getQ_caseNoS()))
			execSQL +=" and "+getQ_workKind()+".caseno >= " + Common.sqlChar(Common.formatRearString(getQ_caseNoS(),10,'0'));		
		if (!"".equals(getQ_caseNoE()))
			execSQL +=" and "+getQ_workKind()+".caseno <= " + Common.sqlChar(Common.formatRearString(getQ_caseNoE(),10, '9'));
    	if (!"".equals(Common.get(getQ_writeDateS())))
    		execSQL += " and "+getQ_workKind()+".writedate >= " + util.Common.sqlChar( new Datetime().changeTaiwanYYMMDD(getQ_writeDateS(),"2"));
    	if (!"".equals(Common.get(getQ_writeDateE())))
    		execSQL += " and "+getQ_workKind()+".writedate <= " + util.Common.sqlChar( new Datetime().changeTaiwanYYMMDD(getQ_writeDateE(),"2"));
		if (!"".equals(getQ_proofYear()))
			execSQL += " and "+getQ_workKind()+".proofyear = "  + Common.sqlChar(getQ_proofYear());
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
		if (!"".equals(getQ_propertyNoS()))
		    execSQL+=" and b.propertyno >= " + Common.sqlChar(getQ_propertyNoS());		
		if (!"".equals(getQ_propertyNoE()))
		    execSQL+=" and b.propertyno <= " + Common.sqlChar(getQ_propertyNoE());
		if (!"".equals(getQ_dataState())){
		    execSQL+=" and b.datastate = " + Common.sqlChar(getQ_dataState()) ;
			execSQL+=" and c.datastate = " + Common.sqlChar(getQ_dataState()) ;
		}
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
		if (!"".equals(getQ_keepUnit()))
		    execSQL+=" and c.keepunit = " + Common.sqlChar(getQ_keepUnit()) ;	    
		if (!"".equals(getQ_keeper()))
		    execSQL+=" and c.keeper = " + Common.sqlChar(getQ_keeper()) ;	    
		if (!"".equals(getQ_useUnit()))
		    execSQL+=" and c.useunit = " + Common.sqlChar(getQ_useUnit()) ;	    
		if (!"".equals(getQ_userNo()))
		    execSQL+=" and c.userno = " + Common.sqlChar(getQ_userNo()) ;	
    	execSQL+=" order by b.enterorg, b.ownership, b.propertyno, c.serialno";
    	
    	//使用者操作記錄
    	if("".equals(this.getObjPath())){
			this.setObjPath("功能選單 > > 單位財產系統 > > 物品管理 > > 非消耗品增加單維護 > > 列印登記卡");
		}
		Common.insertRecordSql(execSQL, this.getOrganID(), this.getUserID(), "UNTNE006R", this.getObjPath().replaceAll("&gt;", ">"));
		
        ResultSet rs = db.querySQL(execSQL);
        Vector rowData = new Vector();
        //int i;
        while(rs.next()){
        	obj.setShareEnterOrg(rs.getString("shareEnterOrg"));
        	obj.setShareOwnership(rs.getString("shareOwnership"));
        	obj.setSharePropertyNo(rs.getString("sharePropertyNo"));
        	obj.setShareSerialNo(rs.getString("shareSerialNo"));
        	obj.setShareDifferenceKind(rs.getString("shareDifferenceKind"));
        	
        	Object[] data = new Object[41];
            data[0] = rs.getString("enterorg");
            data[1] = rs.getString("ownership");
        	data[2] = rs.getString("propertykind");
           	data[3] = rs.getString("propertyno");
            data[4] = rs.getString("propertyname");
//          data[5] = rs.getString("oldSerialno");
            data[6] = rs.getString("propertyname1");
            data[7] = Common.getCutStr(rs.getString("fundtype"),19);
            data[8] = rs.getString("articlename");
            int otherLimitMonth = "".equals(Common.get(rs.getString("otherLimitYear")))? 0 : Integer.parseInt(rs.getString("otherLimitYear")); // otherLimitYear以單位月分存取需轉成年
			int year = otherLimitMonth /12;
			int month = otherLimitMonth % 12;
			data[9] = year +"年"+month+"個月";
            data[10] = rs.getString("specification");
            if(rs.getString("sourcedate")!=null){
                data[11] = Common.MaskDate( new Datetime().changeTaiwanYYMMDD(rs.getString("sourcedate"),"1"));
            }else{
                data[11] = "";
            }
            data[12] = rs.getString("nameplate");
            data[13] = Common.MaskDate(new Datetime().changeTaiwanYYMMDD( rs.getString("buydate"),"1"));
//            if(rs.getString("propertyunit")!=null){
//                data[14] = rs.getString("propertyunit");
//            }else{
                data[14] = rs.getString("otherpropertyunit");
//            }
            data[15] = (!"".equals(rs.getString("place1"))&&rs.getString("place1")!=null)?rs.getString("place1"):rs.getString("place");
            data[16] = rs.getString("bookamount");
            data[17] = rs.getString("useunit");
            data[18] = Common.valueFormat(rs.getString("bookvalue"))+"　";  
            data[19] = rs.getString("username");
            data[20] = rs.getString("sourcekind");
            data[21] = rs.getString("keepunit");
//            if(rs.getString("material")!=null){
//                data[22] = rs.getString("material");
//            }else{
                data[22] = rs.getString("othermaterial");
//            }
            data[23] = rs.getString("keepername");
            data[24] = rs.getString("notes1");
            getTotalEquipmentName();
            data[25] = obj.getEquipmentName();
            
            data[26] = Common.MaskDate(new Datetime().changeTaiwanYYMMDD(rs.getString("enterdate"),"1"));
            data[27] = rs.getString("originalnote");
            data[28] = Common.valueFormat(rs.getString("originalbv"));
            data[29] = rs.getString("proofyear")+"年"+Common.get(rs.getString("proofdoc"))+"字第"+Common.get(rs.getString("proofno"))+"號";
            if(rs.getString("originalmovedate")!=null){
                data[30] = Common.MaskDate(rs.getString("originalmovedate"));
            }else{
                data[30] = "";
            }
            data[31] = rs.getString("originalplace");
            data[32] = rs.getString("moveuseunit");
            data[33] = rs.getString("moveusername");
            data[34] = rs.getString("movekeepunit");
            data[35] = rs.getString("movekeepername");
            data[36] = rs.getString("shareenterorg")+rs.getString("shareownership")+rs.getString("sharedifferencekind")+rs.getString("sharepropertyno")+rs.getString("shareserialno");
            data[37] = new JRTableModelDataSource(getSubModel((String)data[0]));
       //     data[38] = new JRTableModelDataSource(getSubMode2((String)data[0]));
            data[38] = "";
            data[39] = new JRTableModelDataSource(getSubMode3((String)data[0]));
            data[40] = rs.getString("oldserialno");

            //for(i=0;i<34;i++)if(data[i]==null)data[i]="";
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

//帳務資料－多筆
public DefaultTableModel getSubModel(String caseCode) throws Exception{
	UNTNE006R obj = this;
    DefaultTableModel model = new javax.swing.table.DefaultTableModel();
    Database db = new Database();
    try{
        String[] columns = new String[] { "ENTERDATE", "PROOF","PROOFDOC","BOOKNOTES","OLDBOOKVALUE","ADDBOOKVALUE","REDUCEBOOKVALUE","NEWBOOKVALUE"};
    	String execSQL="(select a.adjustDate as enterDate,a.proofyear ,a.proofDoc, a.proofNo, b.bookNotes, b.oldBookValue, b.addbookvalue as addBookValue, b.reducebookvalue as reduceBookValue, b.newBookValue, '1' as adjustType " +
    					" from UNTNE_AdjustProof a"+
    			        " left join UNTNE_AdjustDetail b on a.enterOrg=b.enterOrg and a.ownership=b.ownership and a.caseNo=b.caseNo " +
    					" where 1=1 "+
						" and a.enterOrg=" + util.Common.sqlChar(obj.getShareEnterOrg())+
						" and a.ownership=" + util.Common.sqlChar(obj.getShareOwnership())+
						" and b.propertyNo=" + util.Common.sqlChar(obj.getSharePropertyNo())+
						" and b.serialNo=" + util.Common.sqlChar(obj.getShareSerialNo())+
						" and b.differenceKind=" + util.Common.sqlChar(obj.getShareDifferenceKind())+
    					" ) " +
    					"union " +
						"(select a.reduceDate as enterDate,a.proofyear , a.proofDoc, a.proofNo, b.bookNotes, b.oldBookValue, '0' as addBookValue, b.adjustbookvalue as reduceBookValue, b.newBookValue, '2' as adjustType " +
						" from UNTNE_ReduceProof a"+
						" left join UNTNE_ReduceDetail b on a.enterOrg=b.enterOrg and a.ownership=b.ownership and a.caseNo=b.caseNo " +
						" where 1=1 "+
						" and a.enterOrg=" + util.Common.sqlChar(obj.getShareEnterOrg())+
						" and a.ownership=" + util.Common.sqlChar(obj.getShareOwnership())+
						" and b.propertyNo=" + util.Common.sqlChar(obj.getSharePropertyNo())+
						" and b.serialNo=" + util.Common.sqlChar(obj.getShareSerialNo())+
						" and b.differenceKind=" + util.Common.sqlChar(obj.getShareDifferenceKind())+
						" ) ";
    	execSQL += " order by enterDate";
    	ResultSet rs = db.querySQL(execSQL);
    	Vector rowData = new Vector();
    	//int i;
    	while(rs.next()){
    		Object[] data = new Object[columns.length];
    		data[0] = Common.MaskDate(new Datetime().changeTaiwanYYMMDD(rs.getString("enterDate"),"1"));
    		if(rs.getString("adjustType").equals("2")){
    			data[1] = "物品減損單";
    		}else{
    			data[1] = "物品增減值單";
    		}
    		data[2] = rs.getString("proofyear")+Common.get(rs.getString("proofDoc"))+"字第"+Common.get(rs.getString("proofNo"))+"號";
    		data[3] = rs.getString("bookNotes");
    		data[4] = new Integer(rs.getString("oldBookValue"));
//    		if(rs.getString("adjustType").equals("no") || rs.getString("adjustType").equals("2")){
//    			data[5] = new Integer(0);
//    		}else{
//    			if(rs.getString("adjustType").equals("1")){
    				data[5] = new Integer(rs.getString("addBookValue"));
//    			}else{
//    				data[5] = new Integer(0);
//    			}
//    		}
//    		if(rs.getString("adjustType").equals("no") || rs.getString("adjustType").equals("2")){
    			data[6] = new Integer(rs.getString("reduceBookValue"));
//    		}else{
//    			data[6] = new Integer(0);
//    		}
    		data[7] = new Integer(rs.getString("newBookValue"));
    		//for(i=0;i<8;i++)if(data[i]==null)data[i]="";
    		rowData.addElement(data);
    	}
    	
    	if(rowData.size()==0){    //無資料也要顯示欄位
    		Object[] data = new Object[columns.length];
    		data[0]="";
    		data[1]="";
    		data[2]="";
    		data[3]="";
    		data[4]= new Integer("0");
    		data[5]= new Integer("0");
    		data[6]= new Integer("0");
    		data[7]= new Integer("0");
    		
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

//移動紀錄－多筆
public DefaultTableModel getSubMode2(String caseCode) throws Exception{
	UNTNE006R obj = this;
    DefaultTableModel model = new javax.swing.table.DefaultTableModel();
    Database db = new Database();
    try{
        String[] columns = new String[] { "NEWMOVEDATE", "MOVEUSEUNIT2","MOVEKEEPUNIT2","NEWPLACE","MOVEUSERNAME2","MOVEKEEPERNAME2","PROOFDOCNO"};
    	String execSQL="select  a.newMoveDate, b.unitName as useUnit, c.keeperName as userName, a.newPlace," +
						" (select d.unitName from UNTMP_KeepUnit d where a.enterOrg=d.enterOrg and a.newKeepUnit=d.unitNo) as keepUnit, " +
						" (select e.keeperName from UNTMP_Keeper e where a.enterOrg=e.enterOrg and a.newKeeper=e.keeperNo) as keeperName, " +
						" d.proofDoc, d.proofNo,d.proofyear " +
    					" from UNTNE_MoveDetail a"+
						" left join UNTNE_MoveProof d on d.enterOrg=a.enterOrg and d.ownership=a.ownership and d.caseNo=a.caseNo,"+
    					" UNTMP_KeepUnit b, UNTMP_Keeper c  " +
    					" where 1=1 " +
						" and a.enterOrg=b.enterOrg and a.newUseUnit=b.unitNo " +
						" and a.enterOrg=c.enterOrg and a.newUserNo=c.keeperNo " +
						" and a.enterOrg=" + util.Common.sqlChar(obj.getShareEnterOrg())+
						" and a.ownership=" + util.Common.sqlChar(obj.getShareOwnership())+
						" and a.propertyNo=" + util.Common.sqlChar(obj.getSharePropertyNo())+
						" and a.serialNo=" + util.Common.sqlChar(obj.getShareSerialNo())+
						" order by a.newMoveDate ";
    	ResultSet rs = db.querySQL(execSQL);
    	Vector rowData = new Vector();
    	//int i;
    	while(rs.next()){
    		Object[] data = new Object[columns.length];
    		data[0] = Common.MaskDate(new Datetime().changeTaiwanYYMMDD(rs.getString("newMoveDate"),"1"));
    		data[1] = rs.getString("useUnit");
    		data[2] = rs.getString("keepUnit");
    		data[3] = rs.getString("newPlace");
    		data[4] = rs.getString("userName");
    		data[5] = rs.getString("keeperName");
    		data[6] = rs.getString("proofyear")+Common.get(rs.getString("proofDoc"))+"字第"+Common.get(rs.getString("proofNo"))+"號";
    		//for(i=0;i<7;i++)if(data[i]==null)data[i]="";
    		rowData.addElement(data);
    	}
    	
//    	if(rowData.size()==0){    //無資料也要顯示欄位
//    		Object[] data = new Object[columns.length];
//    		data[0]="";
//    		data[1]="";
//    		data[2]="";
//    		data[3]="";
//    		data[4]="";
//    		data[5]="";
//    		data[6]="";
//    		
//    		rowData.addElement(data);
//    	}
    	
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
	UNTNE006R obj = this;
    DefaultTableModel model = new javax.swing.table.DefaultTableModel();
    Database db = new Database();
    try{
        String[] columns = new String[] { "ENTERDATE", "PROOF","CAUSE","APPROVE","REDUCEDEAL"};
    	String execSQL="select a.reduceDate as enterDate, a.proofDoc, a.proofNo, b.codeName as cause, c.cause1, a.approveDoc, c.reduceDeal " +
						" from UNTNE_ReduceProof a"+
    			        " left join UNTNE_ReduceDetail c on a.enterOrg=c.enterOrg and a.ownership=c.ownership and a.caseNo=c.caseNo "+
    			        " left join SYSCA_Code b on c.cause=b.codeID and b.codeKindID = 'CAC'"+
						" where 1=1 " +
						" and a.enterOrg=" + util.Common.sqlChar(obj.getShareEnterOrg())+
						" and a.ownership=" + util.Common.sqlChar(obj.getShareOwnership())+
						" and c.propertyNo=" + util.Common.sqlChar(obj.getSharePropertyNo())+
						" and c.serialNo=" + util.Common.sqlChar(obj.getShareSerialNo())+
						" and c.differenceKind=" + util.Common.sqlChar(obj.getShareDifferenceKind());
    	ResultSet rs = db.querySQL(execSQL);
    	Vector rowData = new Vector();
    	//int i;
    	while(rs.next()){
    		Object[] data = new Object[columns.length];
    		data[0] = Common.MaskDate(new Datetime().changeTaiwanYYMMDD(rs.getString("enterDate"),"1"));
    		data[1] = Common.get(rs.getString("proofDoc"))+"字第"+Common.get(rs.getString("proofNo"))+"號";
    		if(Common.get(rs.getString("cause")).equals("99")){
    		    data[2] = rs.getString("cause1");
    		}else{
    		    data[2] = rs.getString("cause");
    		}
    		data[3] = rs.getString("approveDoc");
    		data[4] = rs.getString("reduceDeal");
    		//for(i=0;i<5;i++)if(data[i]==null)data[i]="";
    		rowData.addElement(data);
    	}
    	
    	if(rowData.size()==0){    //無資料也要顯示欄位
    		Object[] data = new Object[columns.length];
    		data[0]="";
    		data[1]="";
    		data[2]="";
    		data[3]="";
    		data[4]="";
    		
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

}