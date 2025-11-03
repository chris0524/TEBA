/*
*<br>程式目的：非消耗品增加單查詢檔 
*<br>程式代號：untne005r
*<br>撰寫日期：103/09/19
*<br>程式作者：Mike Kao
*<br>--------------------------------------------------------
*<br>修改作者　　修改日期　　　修改目的
*<br>--------------------------------------------------------
*/

package unt.ne;

import java.sql.ResultSet;
import java.text.DecimalFormat;
import java.util.Collections;
import java.util.Comparator;
import java.util.Map;
import java.util.Vector;

import javax.swing.table.DefaultTableModel;

import net.sf.jasperreports.engine.data.JRTableModelDataSource;
import unt.ch.UNTCH_Tools;
import util.Common;
import util.Database;
import util.Datetime;
import util.TCGHCommon;

public class UNTNE005R extends UNTNE001Q{
	String enterOrg;
	String ownership;
	int count;
	String q_enterOrg;
	String q_enterOrgName;
	String q_ownership;
	String q_caseNoS;
	String q_caseNoE;
	String q_writeDateS;
	String q_writeDateE;
	String q_proofYear;
	String q_proofDoc;
	String q_proofNoS;
	String q_proofNoE;
	String q_kind;
	String q_note;
	
	String propertyNo;
	String lotNo;
	String originalPlace;
	String originalAmount;
	String originalBV;
	String proof;
	String enterOrgName;
	String caseNoPage;
	String organID;
	String editID;  
	
	private String q_reportType;						// 報表類型
	
	
	public String getQ_reportType() { return checkGet(q_reportType); }
	public void setQ_reportType(String q_reportType) { this.q_reportType = checkGet(q_reportType); }
	public String getOrganID() { return checkGet(organID); }
	public void setOrganID(String s) { organID = checkSet(s); }
	public String getEditID() {return checkGet(editID);}
	public void setEditID(String editID) {this.editID = checkSet(editID);}
	public String getEnterOrg(){ return checkGet(enterOrg); }
	public void setEnterOrg(String s){ enterOrg=checkSet(s); }
	public String getOwnership(){ return checkGet(ownership); }
	public void setOwnership(String s){ ownership=checkSet(s); }
//	public String getCaseNo(){ return checkGet(caseNo); }
//	public void setCaseNo(String s){ caseNo=checkSet(s); }
	public int getCount(){ return count; }
	public void setCount(int s){ count=s; }
	public String getQ_enterOrg(){ return checkGet(q_enterOrg); }
	public void setQ_enterOrg(String s){ q_enterOrg=checkSet(s); }
	public String getQ_enterOrgName(){ return checkGet(q_enterOrgName); }
	public void setQ_enterOrgName(String s){ q_enterOrgName=checkSet(s); }
	public String getQ_ownership(){ return checkGet(q_ownership); }
	public void setQ_ownership(String s){ q_ownership=checkSet(s); }
	public String getQ_caseNoS(){ return checkGet(q_caseNoS); }
	public void setQ_caseNoS(String s){ q_caseNoS=checkSet(s); }
	public String getQ_caseNoE(){ return checkGet(q_caseNoE); }
	public void setQ_caseNoE(String s){ q_caseNoE=checkSet(s); }
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
	public String getQ_kind(){ return checkGet(q_kind); }
	public void setQ_kind(String s){ q_kind=checkSet(s); }
	public String getQ_note() {return checkGet(q_note);}
	public void setQ_note(String q_note) {this.q_note = checkSet(q_note);}

	public String getOriginalBV(){ return checkGet(originalBV); }
	public void setOriginalBV(String s){ originalBV=checkSet(s); }
	public String getOriginalAmount(){ return checkGet(originalAmount); }
	public void setOriginalAmount(String s){ originalAmount=checkSet(s); }
	public String getPropertyNo(){ return checkGet(propertyNo); }
	public void setPropertyNo(String s){ propertyNo=checkSet(s); }
	public String getLotNo(){ return checkGet(lotNo); }
	public void setLotNo(String s){ lotNo=checkSet(s); }
	public String getOriginalPlace(){ return checkGet(originalPlace); }
	public void setOriginalPlace(String s){ originalPlace=checkSet(s); }
	public String getProof(){ return checkGet(proof); }
	public void setProof(String s){ proof=checkSet(s); }
	public String getEnterOrgName(){ return checkGet(enterOrgName); }
	public void setEnterOrgName(String s){ enterOrgName=checkSet(s); }
	public String getCaseNoPage(){ return checkGet(caseNoPage); }
	public void setCaseNoPage(String s){ caseNoPage=checkSet(s); }
	
    double sumAmount = 0;
    int sumBV = 0;
    int sumUnit = 0;
	
    DecimalFormat amount = new DecimalFormat("###,###,###,##0.00");
    
	public DefaultTableModel getResultModel() throws Exception{
	UNTNE005R obj = this;
    DefaultTableModel model = new javax.swing.table.DefaultTableModel();
    UNTCH_Tools ut = new UNTCH_Tools();
    Database db = new Database();
    Map<String,String> differenceMap = TCGHCommon.getSysca_Code("DFK"); //財產區分別
    try{
    	String[] columns = new String[] {"ENTERORGNAME","WRITEDATE","propertyKindName","WRITEUNIT","PROOF","Q_KIND","CASENO","MANAGENO","SUMMONSNO","subReportDataSource","caseNoPage","USEUNITUSER","tail01","q_notes","sumBookAmount","sumBookValue","KOC","propertyKind","PRINTUSER","PRINTDATE","summonsDate","sortList"};
    	String queryCondition = "";
    	String execSQL="select count(*) as count, a.enterorg, a.ownership, a.caseno,a.differencekind, " + 
				    	"b.organaname as enterorgname, a.writedate,a.summonsdate, " +
				    	"(select e.unitname from UNTMP_KEEPUNIT e where  e.enterorg = a.enterorg and e.unitno= a.writeunit) writeunit, " +
				    	"a.proofyear, a.proofdoc, a.proofno, a.manageno, " +
				    	"z.codename as ownershipName,  a.summonsno, " + 
				    	"sum(d.originalamount) as sumoriginalamount, sum(d.originalbv) as sumoriginalbv, sum(c.originalunit) as sumoriginalunit," +  
				    	"c.propertykind, " +  
				    	"(select codename from SYSCA_CODE  where codekindid='DFK' and codeid= a.differencekind) differenceKindName " +   
				    	"from UNTNE_ADDPROOF a, SYSCA_ORGAN b, UNTNE_NONEXP c, UNTNE_NONEXPDETAIL d, SYSCA_CODE z " +     
				    	"where 1=1 " +    
				    	"and a.enterorg = b.organid " +    
				    	"and a.enterorg=c.enterorg " +   
				    	"and a.ownership=c.ownership " +   
				    	"and a.caseno=c.caseno " +    
				    	"and c.enterorg=d.enterorg " +   
				    	"and c.ownership=d.ownership " +  
				    	"and c.differencekind=d.differencekind " +  
				    	"and c.lotno=d.lotno " +   
				    	"and c.propertyno=d.propertyno " +   
				    	"and a.ownership=z.codeid " +   
				    	"and z.codekindid='OWA' " ;   
    	if (!"".equals(Common.get(getQ_enterOrg()))) {
    		queryCondition +=" and a.enterorg = " + Common.sqlChar(getQ_enterOrg()) ;
    	} else {
    		if (!getIsAdminManager().equalsIgnoreCase("Y")) {
    			if ("Y".equals(Common.get(getIsOrganManager()))) {					
    				//execSQL += " and a.enterorg like '" + getOrganID().substring(0,5) + "%' ";
    				queryCondition += " and ( a.enterorg = "+ Common.sqlChar(getOrganID()) +" or a.enterorg in ( select organid from SYSCA_ORGAN where organsuperior = "+ Common.sqlChar(getOrganID()) +" ) ) ";		
    			} else {
    				queryCondition +=" and a.enterorg = " + Common.sqlChar(getOrganID()) ;
    			}
    		}
    	}

    	if (!"".equals(Common.get(getQ_ownership()))){
    		queryCondition +=" and a.ownership = " + util.Common.sqlChar(getQ_ownership());
    	}
    	if (!"".equals(Common.get(getQ_caseNoS())))
    		queryCondition +=" and a.caseno >= " + Common.sqlChar(Common.formatRearString(getQ_caseNoS(),10,'0'));		
    	if (!"".equals(Common.get(getQ_caseNoE())))
    		queryCondition +=" and a.caseno <= " + Common.sqlChar(Common.formatRearString(getQ_caseNoE(),10, '9'));

    	if (!"".equals(Common.get(getQ_writeDateS())))
    		queryCondition += " and a.writedate >= " + util.Common.sqlChar(new Datetime().changeTaiwanYYMMDD(getQ_writeDateS(),"2"));
    	if (!"".equals(Common.get(getQ_writeDateE())))
    		queryCondition += " and a.writedate <= " + util.Common.sqlChar(new Datetime().changeTaiwanYYMMDD(getQ_writeDateE(),"2"));

    	if (!"".equals(Common.get(getQ_proofYear()))) 
    		queryCondition += " and a.proofyear = " + Common.sqlChar(getQ_proofYear());		

    	if (!"".equals(Common.get(getQ_proofDoc())))
    		queryCondition += " and a.proofdoc like '%" + getQ_proofDoc() + "%'" ;
    	if (!"".equals(Common.get(getQ_proofNoS()))) 
    		queryCondition += " and a.proofno >= " + Common.sqlChar(getQ_proofNoS());		
    	if (!"".equals(Common.get(getQ_proofNoE()))) 
    		queryCondition += " and a.proofno <= " + Common.sqlChar(getQ_proofNoE());	
    	execSQL+= queryCondition;
    	//限定100個單號
    	execSQL+=" and a.caseno in ( ";
    		execSQL+="  select top 100 a.caseno  ";
    		execSQL+=" from UNTNE_ADDPROOF a, SYSCA_ORGAN b, UNTNE_NONEXP c, UNTNE_NONEXPDETAIL d, SYSCA_CODE z " +     
	    	"where 1=1 " +    
	    	"and a.enterorg = b.organid " +    
	    	"and a.enterorg=c.enterorg " +   
	    	"and a.ownership=c.ownership " +   
	    	"and a.caseno=c.caseno " +    
	    	"and c.enterorg=d.enterorg " +   
	    	"and c.ownership=d.ownership " +  
	    	"and c.differencekind=d.differencekind " +  
	    	"and c.lotno=d.lotno " +   
	    	"and c.propertyno=d.propertyno " +   
	    	"and a.ownership=z.codeid " +   
	    	"and z.codekindid='OWA' " ;
    		execSQL+= queryCondition;
    	execSQL+="  ) ";
    	//限定100個單號
    	execSQL+=" group by a.enterorg, a.ownership, a.caseno, c.propertykind, b.organaname, a.writedate, a.writeunit, a.proofyear, a.proofdoc, a.proofno, a.manageno, a.summonsno, z.codename, a.differencekind,a.summonsdate";
    	execSQL+=" order by a.caseno,c.propertykind ";

    	//System.out.println(execSQL);
    	String[] kindName = null;
    	if("1".equals(q_kind)){			kindName = new String[]{"第一聯"};
    	}else if("2".equals(q_kind)){	kindName = new String[]{"第二聯"};
    	}else if("3".equals(q_kind)){	kindName = new String[]{"第三聯"};
    	}else if("4".equals(q_kind)){	kindName = new String[]{"第一聯","第二聯","第三聯"};
    	}else if("5".equals(q_kind)){	kindName = new String[]{"第一聯","第二聯","第二聯 ","第三聯"};
    	}else if("6".equals(q_kind)){	kindName = new String[]{"第一聯","第三聯"};
    	}

    	Vector rowData = new Vector();

    	//使用者操作記錄
    	if("".equals(this.getObjPath())){
			this.setObjPath("功能選單 > > 單位財產系統 > > 物品管理 > > 非消耗品增加單維護 > > 列印增加單");
		}
		Common.insertRecordSql(execSQL, this.getOrganID(), this.getUserID(), "UNTNE005R", this.getObjPath().replaceAll("&gt;", ">"));
		
    	ResultSet rs = db.querySQL(execSQL);
    	String datetime = Datetime.getYYYMMDD();
    	//int i;
    	while(rs.next()){
    		for(int i=0; i<kindName.length; i++){
    	    	resetSum();
    			obj.setEnterOrg(rs.getString("enterorg"));
    			obj.setOwnership(rs.getString("ownership"));
    			obj.setCaseNo(rs.getString("caseno"));
    			obj.setCount(rs.getInt("count"));
    			obj.setProof(rs.getString("proofyear")+"年"+rs.getString("proofdoc")+"字第"+rs.getString("proofno")+"號");
    			obj.setEnterOrgName(rs.getString("enterorgname"));
    			obj.setCaseNoPage(rs.getString("caseNo"));
    			obj.setDifferenceKind(rs.getString("differenceKind"));
    			Object[] data = new Object[columns.length];
    			data[0] = Common.get(rs.getString("enterorgname"));
    			data[1] = Common.MaskCDate(new Datetime().changeTaiwanYYMMDD(rs.getString("writedate"),"1"));
    			data[2] = Common.get(rs.getString("differenceKindName"));
    			data[3] = Common.get(rs.getString("writeunit"));
    			data[4] = Common.get(rs.getString("proofyear")+"年"+rs.getString("proofdoc"))+"字第"+Common.get(rs.getString("proofno"))+"號";
    			data[5] = kindName[i];
    			data[21] = checkGet(rs.getString("caseno"))+i;
    			data[6] = "電腦單號："+Common.get(rs.getString("caseno"));
    			data[7] = Common.get(rs.getString("enterorg"))+"　";
    			data[8] = Common.get(rs.getString("summonsno"))+"　";
    			data[9] = new JRTableModelDataSource(getSubModel((String)data[0],rs.getString("propertykind")));
    			data[10] = Common.get(rs.getString("caseno"));
    			data[11] = getUseUnitUser(db,rs.getString("propertykind"));
    			//單據備註
    			data[12] = ut._queryData("notes")._from("UNTNE_ADDPROOF a ")._with(queryCondition)._toString();
    			//判斷是否列印備註用
    			data[13] = q_note;
    			data[14] = amount.format(sumAmount);
    			//		            data[15] = Common.valueFormat(String.valueOf(sumUnit))+"\n"+ Common.valueFormat(String.valueOf(sumBV));
    			data[15] = Common.valueFormat(String.valueOf(sumBV));
    			data[16] = "";
    			data[17] = Common.get(rs.getString("propertykind"));
    			//印表人
    			data[18] = ut._queryData("empname")._from("SYSAP_EMP a ")._with("and organid = '" + getOrganID() + "' and empid = '" + getEditID() + "'")._toString();
    			//印表日期
    			data[19] = Common.MaskCDate(datetime);
    			//傳票日期
    			String summonsdate = Common.get(rs.getString("summonsdate"));
    			if(!summonsdate.equals("")){
    				data[20] = ut._transToROC_Year(rs.getString("summonsdate")).substring(0,3) + "年" + ut._transToROC_Year(rs.getString("summonsDate")).substring(3,5) + "月" + ut._transToROC_Year(rs.getString("summonsDate")).substring(5) + "日";
    			}else{
    				data[20] = "";
    			}
    			//for(i=0;i<10;i++)if(data[i]==null)data[i]="";
    			rowData.addElement(data);
    		}
    	}
    	rs.close();

	    //第一聯、第二聯、第三聯 排序用
	    Collections.sort(rowData,
	            new Comparator<Object[]>() {
	                public int compare(Object[] o1, Object[] o2) {
	                    return o1[21].toString().compareTo(o2[21].toString());
	                }
	            });
	    
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

	//使用單位內資料
	public String getUseUnitUser(Database db,String propertyKind){
		String useUnitUser="";
		//Database db = new Database();	
		UNTNE005R obj = this;
		int count=0;
		ResultSet rs;	
    	String sql="select distinct " +
						" (select d.unitName from UNTMP_KeepUnit d where b.enterOrg=d.enterOrg and b.originalUseUnit=d.unitNo) as originalUseUnit, " +
						" (select e.keeperName from UNTMP_Keeper e where b.enterOrg=e.enterOrg and b.originalUser=e.keeperNo) as originalUser " +
						" from UNTNE_Nonexp a"+
						" left join UNTNE_NONEXPDETAIL b on a.enterOrg=b.enterOrg and a.ownership=b.ownership and a.propertyNo=b.propertyNo and a.lotNo=b.lotNo and a.differencekind=b.differencekind"+
						" where 1=1 " +
						" and a.enterOrg=" + util.Common.sqlChar(obj.getEnterOrg())+
						" and a.ownership=" + util.Common.sqlChar(obj.getOwnership())+
						" and a.caseNo=" + util.Common.sqlChar(obj.getCaseNo())+
						" and a.differencekind=" + util.Common.sqlChar(obj.getDifferenceKind())+
						" and a.propertyKind=" + util.Common.sqlChar(propertyKind)+
						" ";
		try{
			//System.out.println("sql: "+sql);
			rs = db.querySQL(sql);
			while (rs.next()){
				count++;
				useUnitUser = Common.get(rs.getString("originalUseUnit"))+"　"+Common.get(rs.getString("originalUser"));
			}
			if(count>=2){
				useUnitUser = useUnitUser+"等";
			}
			if (rs.getStatement()!=null){ rs.getStatement().close(); }
			if (rs!=null){ rs.close(); }
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			//db.closeAll();
		}	
	return useUnitUser;	
	}
	
/* 增加單明細 */
public DefaultTableModel getSubModel(String caseCode,String propertyKind) throws Exception{
	UNTNE005R obj = this;
	DefaultTableModel model = new javax.swing.table.DefaultTableModel();
    Database db = new Database();
    Map<String,String> placeMap = TCGHCommon.getSysca_PlaceCode(this.getQ_enterOrg());			//存置地點名稱
    String oldPropertyNo="";
    String oldLotNo="";
    String oldOriginalPlace="";
    String newPropertyNo="";
    String newLotNo="";
    String newOriginalPlace="";
    double originalAmount = 0;
    int originalBV = 0;
    int originalUnit = 0;
    int countDetail = 0;
    String data1="", data2="", data3="", data4="", data5="", data6="", data7="", data8="", data9="", data10="", data11="", data12="", data13="", data14="";
    try{
        String[] columns = new String[] {"SOURCEDATEBUYDATE","PROPERTYNOPROPERTYNAME","SPECIFICATIONNAMEPLATE"
        								,"SOURCEKINDNAME","PROPERTYUNIT","ORIGINALAMOUNT"
        								,"ORIGINALBV","ORIGINALPLACE","KEEPER","SCRAPVALUEACCOUNTINGTITLE"
        								,"LIMITYEAR","DEPRMETHOD"};
        
      
    	String execSQL = " select distinct a.propertyno, a.lotno, b.serialno, b.originalplace1, b.originalplace, b.propertyname1, " +
				       	" a.propertyno, a.sourcedate, a.buydate, c.propertyname, (isnull(a.specification,'') + '/' + isnull(a.nameplate,'')) as specificationnameplate, d.codename as sourcekind, " + 
				       	" case substring(a.propertyno,1,1) when '3' then '機械及設備' when '4' then '交通及運輸設備' when '5' then '雜項設備' when '6' then '非消耗品' end as propertytypename,"+
				       	" c.propertyunit, a.otherpropertyunit, a.originalunit, a.otherlimityear, a.originallimityear, " +  
				    	" a.accountingtitle, b.originalamount as  bookamount, b.originalbv as bookvalue, " +
				    	" (select e.keepername from UNTMP_KEEPER e where e.enterorg=a.enterorg and e.keeperno=b.keeper) as keeper" + 
				    	" from UNTNE_NONEXP a " +  
				    	" left join UNTNE_NONEXPDETAIL b on a.enterorg=b.enterorg and a.ownership=b.ownership and a.propertyno=b.propertyno and a.lotno=b.lotno and a.differencekind=b.differencekind" + 
				    	" left join SYSCA_CODE d on  a.sourcekind=d.codeid and d.codekindid = 'SKD', " + 
				    	" SYSPK_PROPERTYCODE2 c " +  
				    	" where 1=1 " + 
				    	" and a.enterorg = c.enterorg " + 
				    	" and a.propertyno = c.propertyno " +  
				    	" and a.differencekind = b.differencekind  " +
						" and a.enterorg=" + util.Common.sqlChar(obj.getEnterOrg())+
						" and a.ownership=" + util.Common.sqlChar(obj.getOwnership())+
						" and a.caseno=" + util.Common.sqlChar(obj.getCaseNo())+
						" and a.differencekind=" + util.Common.sqlChar(obj.getDifferenceKind())+
						" and a.propertykind=" + util.Common.sqlChar(propertyKind)+
						" order by a.propertyno, a.lotno, b.serialno, b.originalplace1";
    	
    	//System.out.println("----增加單明細---- "+execSQL);
    	ResultSet rs = db.querySQL_NoChange(execSQL);
    	Vector rowData = new Vector();

    	while(rs.next()){
    		countDetail++;
    		Object[] data = new Object[columns.length];
    		Object[] oldData = new Object[columns.length];
    		newPropertyNo=Common.get(rs.getString("propertyno"));
    		newLotNo=Common.get(rs.getString("lotno"));
    		newOriginalPlace=Common.get(rs.getString("originalplace1"));
//    		if(newPropertyNo.equals(oldPropertyNo) && newLotNo.equals(oldLotNo) && newOriginalPlace.equals(oldOriginalPlace)){
//    			originalAmount += rs.getInt("bookamount");
//    			originalBV += rs.getInt("bookvalue");
//    			originalUnit += rs.getInt("originalunit");
//    			sumAmount = originalAmount;
//    			sumBV = originalBV;
//    			sumUnit = originalUnit;
//    		}else{
    			originalAmount = rs.getDouble("bookamount");
    			originalBV = rs.getInt("bookvalue");
    			originalUnit = rs.getInt("originalunit");
    			sumAmount += originalAmount;
    			sumBV += originalBV;
    			sumUnit  += originalUnit;
//    		}
    		

    		data[0] = Common.MaskDate(Datetime.changeTaiwanYYMMDD(rs.getString("sourcedate"), "1")) + "\n" + Common.MaskDate(Datetime.changeTaiwanYYMMDD(rs.getString("buydate"), "1"));
    		data[1] = Common.get(rs.getString("propertyno")) + "-" + Common.get(rs.getString("serialno")) + "\n" + Common.get(rs.getString("propertyname")) + "\n" + Common.get(rs.getString("propertytypename"));
    		data[2] = Common.get(rs.getString("propertyname1")) + "/" +Common.get(rs.getString("specificationnameplate"));
    		data[3] = Common.get(rs.getString("sourcekind"));
//    		if(rs.getString("propertyunit")!=null){
//    		    data[4] = Common.get(rs.getString("propertyunit"));
//    		}else{
    		    data[4] = Common.get(rs.getString("otherpropertyunit"));
//    		}
    		data[5] = amount.format(originalAmount);
    		data[6] = Common.valueFormat(originalUnit+"") + "\n"  + Common.valueFormat(originalBV+"");
    		if(Common.get(placeMap.get(rs.getString("originalplace1")))!=""){
    			data[7] = Common.get(placeMap.get(rs.getString("originalplace1")) + "\n" + Common.get(rs.getString("originalplace")));
    		}else{
    			data[7] =Common.get(rs.getString("originalplace"));
    		}
    		data[8] = Common.get(rs.getString("keeper"));
    		data[9] = Common.get(rs.getString("accountingtitle"));
    		if(!"".equals(Common.get(rs.getString("originallimityear"))) && !"0".equals(Common.get(rs.getString("originallimityear")))){
    		    data[10] = Common.get(rs.getString("originallimityear"))+"年0個月";
    		}else{
    		    int otherLimitMonth = "".equals(Common.get(rs.getString("otherlimityear")))? 0 : Integer.parseInt(rs.getString("otherLimitYear")); // otherLimitYear以單位月分存取需轉成年
				int year = otherLimitMonth/12;
				int month = otherLimitMonth%12;
				data[10] = year +"年"+month+"個月";
    		}
    		data[11] = "";

    		if("".equals(oldPropertyNo) || oldPropertyNo==null){
	    		data1 = data[0]+"";
				data2 = data[1]+"";
				data3 = data[2]+"";
				data4 = data[3]+"";
				data5 = data[4]+"";
				data6 = data[5]+"";
				data7 = data[6]+"";
				data8 = data[7]+"";
				data9 = data[8]+"";
				data10 = data[9]+"";
				data11 = data[10]+"";
				data12 = data[11]+"";
				oldPropertyNo = newPropertyNo;
	    		oldLotNo = newLotNo;
	    		oldOriginalPlace = newOriginalPlace;
    		}
//    		if(!newPropertyNo.equals(oldPropertyNo) || !newLotNo.equals(oldLotNo) || !newOriginalPlace.equals(oldOriginalPlace)){
//    			oldData[0] = data1;
//    			oldData[1] = data2;
//    			oldData[2] = data3;
//    			oldData[3] = data4;
//    			oldData[4] = data5;
//    			oldData[5] = data6;
//    			oldData[6] = data7;
//    			oldData[7] = data8;
//    			oldData[8] = data9;
//    			oldData[9] = data10;
//    			oldData[10] = data11;
//    			oldData[11] = data12;
//    			rowData.addElement(oldData);
//    		}else{
    			rowData.addElement(data);
//    		}

    		data1 = data[0]+"";
			data2 = data[1]+"";
			data3 = data[2]+"";
			data4 = data[3]+"";
			data5 = data[4]+"";
			data6 = data[5]+"";
			data7 = data[6]+"";
			data8 = data[7]+"";
			data9 = data[8]+"";
			data10 = data[9]+"";
			data11 = data[10]+"";
			data12 = data[11]+"";
    		oldPropertyNo = newPropertyNo;
    		oldLotNo = newLotNo;
    		oldOriginalPlace = newOriginalPlace;
    	}
    	if(rowData.size()==0 ){ //若無資料也要顯示欄位
	    	 rowData.addElement(new Object[columns.length]);
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

public void resetSum(){
     sumAmount = 0;
     sumBV = 0;
     sumUnit = 0;
}


//使用保管核章明細表
public DefaultTableModel getSubModel1(String caseCode,String propertyKind) throws Exception{
	UNTNE005R obj = this;
	Map<String,String> placeMap = TCGHCommon.getSysca_PlaceCode(this.getQ_enterOrg());			//存置地點名稱
	DefaultTableModel model = new javax.swing.table.DefaultTableModel();
    Database db = new Database();
    String oldPropertyNo="";
    String oldLotNo="";
    String oldOriginalPlace="";
    String oldUseUnit="";
    String oldUser="";
    String oldKeeper="";
    String newPropertyNo="";
    String newLotNo="";
    String newOriginalPlace="";
    String newUseUnit="";
    String newUser="";
    String newKeeper="";
    String serialNoS="";
    String serialNoE="";
    double originalAmount = 0;
    int originalBV = 0;
    int countDetail = 0;
    String data1="", data2="", data3="", data4="", data5="", data6="", data7="", data8="", data9="", data10="", data11="", data12="";
    try{
        String[] columns = new String[] {"PROOF","CASENO","ENTERORGNAME","buyDate"
        								,"propertySerialNo","propertyNoName","originalAmount"
        								,"originalBV","originalUseUnit","originalUser"
        								,"originalKeeper","originalPlace"};
        
    	String execSQL = " select a.propertyNo ,a.lotNo ,b.serialno ,b.Originalplace1 " + "\n"
    				   + "        ,(substring(a.propertyNo,1,7) + '-' + substring(a.propertyNo,8, len(a.propertyno))) as propertyNoShow " + "\n"
    				   + "        ,b.propertyName1 ,(select c.propertyName from SYSPK_PropertyCode2 c where a.enterOrg = c.enterOrg and a.propertyNo = c.propertyNo ) as propertyName " + "\n"
    				   + "        ,a.buyDate ,a.originalAmount ,a.originalBV " + "\n"
    				   + "        ,(select d.unitName from UNTMP_KeepUnit d where b.enterOrg=d.enterOrg and b.originalUseUnit=d.unitNo) as originalUseUnit " + "\n"
    				   + "        ,(select e.keeperName from UNTMP_Keeper e where b.enterOrg=e.enterOrg and b.originalUser=e.keeperNo) as originalUser " + "\n"
    				   + "        ,(select e.keeperName from UNTMP_Keeper e where b.enterOrg=e.enterOrg and b.originalKeeper=e.keeperNo) as originalKeeper " + "\n"
    				   + " from UNTNE_Nonexp a"+
    				   " left join UNTNE_NonexpDetail b on a.enterOrg = b.enterOrg and a.ownership = b.ownership and a.propertyNo = b.propertyNo and a.lotNo = b.lotNo and a.differencekind=b.differencekind"+
    				   " left join SYSCA_Code d on a.sourceKind = d.codeID and d.codeKindID = 'SKC'" + "\n"
    				   + " where 1=1 " + "\n" 
    				   + " and a.enterOrg=" + util.Common.sqlChar(obj.getEnterOrg()) + "\n"
    				   + " and a.ownership=" + util.Common.sqlChar(obj.getOwnership()) +"\n"
    				   + " and a.caseNo=" + util.Common.sqlChar(obj.getCaseNo()) +"\n"
    				   + " and a.differencekind=" + util.Common.sqlChar(obj.getDifferenceKind())+"\n"
    				   + " and a.propertyKind=" + util.Common.sqlChar(propertyKind) +"\n"
    				   + " order by a.propertyNo, a.lotNo, b.serialNo, b.originalPlace1 "
    				   + "";
    	
    	//System.out.println("execSQL: "+execSQL);
    	ResultSet rs = db.querySQL(execSQL);
    	Vector rowData = new Vector();
    	//int i;
    	while(rs.next()){
    		countDetail++;
    		Object[] data = new Object[columns.length];
    		Object[] oldData = new Object[columns.length];
    		
    		newPropertyNo=Common.get(rs.getString("propertyNo"));
    		newLotNo=Common.get(rs.getString("lotNo"));
    		newOriginalPlace=Common.get(rs.getString("originalPlace1"));
    		newUseUnit=Common.get(rs.getString("originalUseUnit"));
    		newUser=Common.get(rs.getString("originalUser"));
    		newKeeper=Common.get(rs.getString("originalKeeper"));
    		serialNoE = Common.get(rs.getString("serialNo"));
    		
    		if(newPropertyNo.equals(oldPropertyNo) && newLotNo.equals(oldLotNo) && newOriginalPlace.equals(oldOriginalPlace) && newUseUnit.equals(oldUseUnit) && newUser.equals(oldUser) && newKeeper.equals(oldKeeper)){
    			originalAmount += rs.getDouble("originalAmount");
    			originalBV += rs.getInt("originalBV");
    			serialNoE = Common.get(rs.getString("serialNo"));
    		}else{
    			serialNoS = Common.get(rs.getString("serialNo"));
    			originalAmount = rs.getDouble("originalAmount");
    			originalBV = rs.getInt("originalBV");
    		}
			data[0] = obj.getProof();
			data[1] = obj.getCaseNoPage();
			data[2] = obj.getEnterOrgName();
			data[3] = Common.MaskDate(rs.getString("buyDate"));
    		data[4] = Common.get(rs.getString("propertyNoShow"))+"\n"+serialNoS+"-\n"+serialNoE+"　";
    		if("".equals(Common.get(rs.getString("propertyName1")))){
    			data[5] = Common.get(rs.getString("propertyName"));
    		}else{
    			data[5] = Common.get(rs.getString("propertyName"))+"\n("+Common.get(rs.getString("propertyName1"))+")";
    		}
    		data[6] = amount.format(originalAmount);
    		data[7] = Common.get(Common.valueFormat(originalBV+""));
    		data[8] = Common.get(rs.getString("originalUseUnit"));
    		data[9] = Common.get(rs.getString("originalUser"));
    		data[10] = Common.get(rs.getString("originalKeeper"));
    		data[11] = Common.get(placeMap.get(rs.getString("originalPlace1")));
    		
    		
    		//for(i=0;i<14;i++)if(data[i]==null)data[i]="";
    		if("".equals(oldPropertyNo) || oldPropertyNo==null){
	    		data1 = data[0]+"";
				data2 = data[1]+"";
				data3 = data[2]+"";
				data4 = data[3]+"";
				data5 = data[4]+"";
				data6 = data[5]+"";
				data7 = data[6]+"";
				data8 = data[7]+"";
				data9 = data[8]+"";
				data10 = data[9]+"";
				data11 = data[10]+"";
				data12 = data[11]+"";
				oldPropertyNo = newPropertyNo;
	    		oldLotNo = newLotNo;
	    		oldOriginalPlace = newOriginalPlace;
	    		oldUseUnit = newUseUnit;
	    		oldUser = newUser;
	    		oldKeeper = newKeeper;
    		}
    		if(!newPropertyNo.equals(oldPropertyNo) || !newLotNo.equals(oldLotNo) || !newOriginalPlace.equals(oldOriginalPlace) || !newUseUnit.equals(oldUseUnit) || !newUser.equals(oldUser) || !newKeeper.equals(oldKeeper)){
    			oldData[0] = Common.get(data1);
    			oldData[1] = Common.get(data2);
    			oldData[2] = Common.get(data3);
    			oldData[3] = Common.get(data4);
    			oldData[4] = Common.get(data5);
    			oldData[5] = Common.get(data6);
    			oldData[6] = Common.get(data7);
    			oldData[7] = Common.get(data8);
    			oldData[8] = Common.get(data9);
    			oldData[9] = Common.get(data10);
    			oldData[10] = Common.get(data11);
    			oldData[11] = Common.get(data12);
    			rowData.addElement(oldData);
    		}
    		if(countDetail==obj.getCount()){
    			rowData.addElement(data);
    		}
    		data1 = data[0]+"";
			data2 = data[1]+"";
			data3 = data[2]+"";
			data4 = data[3]+"";
			data5 = data[4]+"";
			data6 = data[5]+"";
			data7 = data[6]+"";
			data8 = data[7]+"";
			data9 = data[8]+"";
			data10 = data[9]+"";
			data11 = data[10]+"";
			data12 = data[11]+"";
    		oldPropertyNo = newPropertyNo;
    		oldLotNo = newLotNo;
    		oldOriginalPlace = newOriginalPlace;
    		oldUseUnit = newUseUnit;
    		oldUser = newUser;
    		oldKeeper = newKeeper;
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