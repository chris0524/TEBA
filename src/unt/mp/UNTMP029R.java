/*
*<br>程式目的：動產明細清冊查詢檔 
*<br>程式代號：untmp029r
*<br>撰寫日期：94/12/8
*<br>程式作者：Cherry
*<br>--------------------------------------------------------
*<br>修改作者　　修改日期　　　修改目的
*<br>--------------------------------------------------------
*/

package unt.mp;

import java.sql.ResultSet;
import java.util.Map;
import java.util.Vector;

import javax.swing.table.DefaultTableModel;

import unt.ch.UNTCH_Tools;
import util.Common;
import util.Database;
import util.Datetime;
import util.SuperBean;
import util.TCGHCommon;
import TDlib_Simple.tools.src.StringTools;

public class UNTMP029R extends SuperBean{
	String q_enterOrg;
	String q_enterOrgName;
	String q_ownership;
	String q_propertyKind;
	String q_fundType;
	String q_valuable;
	String q_keepBureau;
	String q_differenceKind;
	private String q_keepUnit;
	private String q_keeper;
	private String q_reportType;						// 報表類型
	
	public String getQ_reportType() { return checkGet(q_reportType); }
	public void setQ_reportType(String q_reportType) { this.q_reportType = checkGet(q_reportType); }
    public String getQ_differenceKind() {return checkGet(q_differenceKind); }
	public void setQ_differenceKind(String s) {q_differenceKind = checkSet(s);	}
	public String getQ_enterOrg(){ return checkGet(q_enterOrg); }
	public void setQ_enterOrg(String s){ q_enterOrg=checkSet(s); }
	public String getQ_enterOrgName(){ return checkGet(q_enterOrgName); }
	public void setQ_enterOrgName(String s){ q_enterOrgName=checkSet(s); }
	public String getQ_ownership(){ return checkGet(q_ownership); }
	public void setQ_ownership(String s){ q_ownership=checkSet(s); }
	public String getQ_propertyKind(){ return checkGet(q_propertyKind); }
	public void setQ_propertyKind(String s){ q_propertyKind=checkSet(s); }
	public String getQ_fundType(){ return checkGet(q_fundType); }
	public void setQ_fundType(String s){ q_fundType=checkSet(s); }
	public String getQ_valuable(){ return checkGet(q_valuable); }
	public void setQ_valuable(String s){ q_valuable=checkSet(s); }
	public String getQ_keepBureau(){ return checkGet(q_keepBureau); }
	public void setQ_keepBureau(String s){ q_keepBureau=checkSet(s); }
	public String getQ_keepUnit() { return checkGet(q_keepUnit); }
	public void setQ_keepUnit(String s) { this.q_keepUnit = checkSet(s); }
	public String getQ_keeper() { return checkGet(q_keeper); }
	public void setQ_keeper(String qKeeper) { q_keeper = checkSet(qKeeper); }


	String q_propertyNoS;
	String q_propertyNoE;
	String q_propertyNoSName;
	String q_propertyNoEName;
	String q_serialNo;
	public String getQ_propertyNoS(){ return checkGet(q_propertyNoS); }
	public void setQ_propertyNoS(String s){ q_propertyNoS=checkSet(s); }
	public String getQ_propertyNoE(){ return checkGet(q_propertyNoE); }
	public void setQ_propertyNoE(String s){ q_propertyNoE=checkSet(s); }
	
	public String getQ_propertyNoSName(){ return checkGet(q_propertyNoSName); }
	public void setQ_propertyNoSName(String s){ q_propertyNoSName=checkSet(s); }
	public String getQ_propertyNoEName(){ return checkGet(q_propertyNoEName); }
	public void setQ_propertyNoEName(String s){ q_propertyNoEName=checkSet(s); }
	
	public String getQ_serialNo(){ return checkGet(q_serialNo); }
	public void setQ_serialNo(String s){ q_serialNo=checkSet(s); }

	String q_enterDateS;
	String q_enterDateE;
	String q_balanceDate;
	String q_dataType;
	String q_dataTypeName;
	String q_enterDateName;
	String q_enterDate;
	
	public String getQ_enterDateS(){ return checkGet(q_enterDateS); }
	public void setQ_enterDateS(String s){ q_enterDateS=checkSet(s); }
	public String getQ_enterDateE(){ return checkGet(q_enterDateE); }
	public void setQ_enterDateE(String s){ q_enterDateE=checkSet(s); }
	public String getQ_balanceDate(){ return checkGet(q_balanceDate); }
	public void setQ_balanceDate(String s){ q_balanceDate=checkSet(s); }
	public String getQ_dataType(){ return checkGet(q_dataType); }
	public void setQ_dataType(String s){ q_dataType=checkSet(s); }
	
	String q_chengPage;
	public String getQ_chengPage(){ return checkGet(q_chengPage); }
	public void setQ_chengPage(String s){ q_chengPage=checkSet(s); }

	String equipmentName;
	String propertyKindFundType;
	
	public String getEquipmentName(){ return checkGet(equipmentName); }
	public void setEquipmentName(String s){ equipmentName=checkSet(s); }
	public String getPropertyKindFundType(){ return checkGet(propertyKindFundType); }
	public void setPropertyKindFundType(String s){ propertyKindFundType=checkSet(s); }
	
	String isOrganManager;
	String isAdminManager;
	String organID;    
	public String getOrganID() { return checkGet(organID); }
	public void setOrganID(String s) { organID = checkSet(s); }
	public String getIsOrganManager() { return checkGet(isOrganManager); }
	public void setIsOrganManager(String s) { isOrganManager = checkSet(s); }
	public String getIsAdminManager() { return checkGet(isAdminManager); }
	public void setIsAdminManager(String s) { isAdminManager = checkSet(s); }

	String fileName;

	public String getFileName() { return checkGet(fileName); }
	public void setFileName(String s) { fileName = checkSet(s); }
	
	private String[] q_sDestField;
	public String[] getQ_sDestField(){ return q_sDestField; }
	public void setQ_sDestField(String[] s){ q_sDestField = s; }

	StringBuilder strEngFields = new StringBuilder();
	public DefaultTableModel getResultModel() throws Exception{
	String endDate="";
	String where="";
	String dataTypeName="";
	String dataSE="";
	/*
	if("1".equals(q_dataType)){			//�W�[
		endDate = q_enterDateE ;
		dataSE = "" + Common.MaskDate(new UNTCH_Tools()._transToCE_Year(q_enterDateS)) + "~" 
		+ Common.MaskDate(new UNTCH_Tools()._transToCE_Year(q_enterDateE));
		dataTypeName ="";
		where = " and a.enterdate >= " + Common.sqlChar(new UNTCH_Tools()._transToCE_Year(q_enterDateS)) 
				+ " and a.enterdate <= " + Common.sqlChar(new UNTCH_Tools()._transToCE_Year(q_enterDateE));
	}else if("2".equals(q_dataType)){	//���
		endDate = q_enterDateE ;
		dataSE = "" + Common.MaskDate(new UNTCH_Tools()._transToCE_Year(q_enterDateS)) + "~" +
					Common.MaskDate(new UNTCH_Tools()._transToCE_Year(q_enterDateE));
		dataTypeName ="";
		where = " and c.reducedate >= " + Common.sqlChar(new UNTCH_Tools()._transToCE_Year(q_enterDateS)) 
				+ " and c.reducedate <= " + Common.sqlChar(new UNTCH_Tools()._transToCE_Year(q_enterDateE));
	}else if("3".equals(q_dataType)){	//���s
		endDate = q_balanceDate ;
		dataSE = "" + Common.MaskDate(new UNTCH_Tools()._transToCE_Year(q_balanceDate));
		dataTypeName ="";
		where = " and (c.reducedate <= " + Common.sqlChar(new UNTCH_Tools()._transToCE_Year(q_balanceDate)) 
				+ " or c.reducedate is null) " + "\n"
			  + " and a.enterdate <= " + Common.sqlChar(new UNTCH_Tools()._transToCE_Year(q_balanceDate));
	}
	*/
	if (!"".equals(getQ_differenceKind())) {
		where += " and b.differencekind = " + Common.sqlChar(q_differenceKind);
	}
	if(!"".equals(getQ_ownership())){
		where += " and b.ownership = " + Common.sqlChar(q_ownership);
	}
	if(!"".equals(getQ_propertyKind())){
		where += " and b.propertyKind = " + Common.sqlChar(q_propertyKind);
	}
	if(!"".equals(getQ_fundType())){
		where += " and b.fundType = " + Common.sqlChar(q_fundType);
	}
	if(!"".equals(getQ_valuable())){
		where += " and b.valuable = " + Common.sqlChar(q_valuable);
	}
	if(!"".equals(getQ_propertyNoS())){
		where += " and b.propertyno >= " + Common.sqlChar(q_propertyNoS);
	}
	if(!"".equals(getQ_propertyNoE())){
		where += " and b.propertyno <= " + Common.sqlChar(q_propertyNoE);
	}
	if (!"".equals(getQ_keepUnit())) {
		where += "and c.keepunit = " + Common.sqlChar(getQ_keepUnit()) + " " ;
	}
	
	if (!"".equals(getQ_keeper())) {
		where += "and c.keeper = " + Common.sqlChar(getQ_keeper()) + " " ;
	}

	if (!(getQ_sDestField()==null)) {
		
		String[] arrField = null;
		for (int i=0; i<q_sDestField.length; i++) {
			arrField = q_sDestField[i].split(":;:");			
			if (i==0) {
				strEngFields.append(Common.sqlChar(arrField[0]));
			} else {
				strEngFields.append("," + Common.sqlChar(arrField[0]));
			}
		}
		where += "and c.place1 in ("+ strEngFields.toString() +") ";
	}
	
	if(!"".equals(getQ_keepBureau())){
		where += " and ub.bureau = '" + getQ_keepBureau() +"' ";
		where += " and ua.bureau = '" + getQ_keepBureau() +"' ";
	}
	
	String xls_Sql = " select b.enterorg ,o.organaname,b.differencekind " + "\n"
				   + "        ,b.ownership ,(select x.codename from SYSCA_CODE x where b.ownership = x.codeid and x.codekindid = 'OWA') as ownershipName " + "\n"
				   + "        ,b.propertykind ,( select x.codename from SYSCA_CODE x where x.codekindid='PKD' and x.codeid = b.propertykind ) as propertykindName " + "\n"
				   + "        ,b.fundtype ,( select pp.codename from SYSCA_CODE pp where pp.codekindid='FUD' and pp.codeid=b.fundtype) as fundtypeName " + "\n"
				   + "        ,c.propertyno " + "\n" 
			//	   + "		  ,substring(c.propertyno,1,7) +'-' +substring(c.propertyno ,8,LEN(c.propertyno) -7) as propertyno_a " + "\n" 
				   + "	      ,(case when LEN(c.propertyno) < 8  then c.propertyno else substring(c.propertyno,1,7) +'-' +substring(c.propertyno ,8,LEN(c.propertyno) -7) end) propertyno_a  " + "\n" 
				   + "		  ,c.serialno " + "\n" 
				   + "        ,c.propertyname1 ,b.specification ,b.nameplate " + "\n"
				   + "        ,(case when c.oldserialno is null then '1' else c.oldserialno end) oldserialno " + "\n"
				   + "        ,b.sourcedate ,b.buydate ,c.place " + "\n"
				   + "        ,(select placename from SYSCA_PLACE z where z.enterorg = c.enterorg and z.placeno = c.place1) as place1Name " 
				   + "        ,(select d.propertyname from SYSPK_PROPERTYCODE d where d.propertyno = b.propertyno and d.enterorg in (b.enterorg,'000000000A'))propertyname " + "\n"
				   //+ "        ,isnull((select d.material from syspk_propertyCode d where d.propertyno = b.propertyno and d.enterorg in (b.enterorg,'000000000A')),b.othermaterial) as material " + "\n"
//				   + "        ,isnull((select d.material from SYSPK_PROPERTYCODE d where d.propertyno = b.propertyno and d.enterorg in (b.enterorg,'000000000A')),b.othermaterial) as material " + "\n"
				   + "        ,isnull(b.othermaterial,p.material) as othermaterial " + "\n"
				   //+ "        ,isnull((select d.limityear from syspk_propertyCode d where d.propertyno = b.propertyno and d.enterorg in (b.enterorg,'000000000A')),b.otherlimityear) as limityear " + "\n"				   
//				   + "        ,isnull((select d.limityear from SYSPK_PROPERTYCODE d where d.propertyno = b.propertyno and d.enterorg in (b.enterorg,'000000000A')),b.otherlimityear) as limityear " + "\n"
				   + "  	  ,c.otherlimityear "
				   + "        ,c.keepunit ,c.keeper,ua.unitname as keepUnitName ,ka.keepername as keeperName " + "\n"
				   + "		  ,c.useunit ,c.userno ,ub.unitname as useUnitName  ,kb.keepername as userName " + "\n"
				   //+ "        ,isnull(( select min(h.caseno) " + "\n"
//				   + "          ,isnull(zb.oldbookvalue ,isnull(r.oldbookvalue ,c.bookvalue)) as bookvalue " + "\n"
				   + "          ,c.bookvalue as bookvalue " + "\n"
				   + "          ,c.netvalue as netvalue " + "\n"
				   + "          ,c.bookvalue as nowbookvalue " + "\n"
				   
				   + "          ,c.bookamount as Amount " + "\n"
				   
				   // + " from UNTMP_ADDPROOF a ,UNTMP_MOVABLE b ,UNTMP_MOVABLEDETAIL c "
				   + " from UNTMP_MOVABLE b ,UNTMP_MOVABLEDETAIL c "
				   + " left join UNTMP_KEEPUNIT ua on c.enterorg = ua.enterorg and c.keepunit = ua.unitno"
				   + " left join UNTMP_KEEPUNIT ub on c.enterorg = ub.enterorg and c.keepunit = ub.unitno"
				   + " LEFT join UNTMP_KEEPER   ka on c.enterorg = ka.enterorg and c.keeper = ka.keeperno"
				   + " LEFT join UNTMP_KEEPER   kb on c.enterorg = kb.enterorg and c.keeper = kb.keeperno" 
				   + " left join UNTMP_REDUCEDETAIL r on c.enterorg = r.enterorg  and c.ownership = r.ownership  and c.differencekind=r.differencekind and c.propertyno = r.propertyno  and c.serialno = r.serialno" 
				   + " left join SYSPK_PROPERTYCODE p on p.enterorg in ('000000000A',c.enterorg) and c.propertyno = p.propertyno " 
//				   + " left join ( select w.enterorg ,w.ownership ,z.propertyno ,z.serialno ,z.oldbookvalue "
//				            + " from UNTMP_ADJUSTPROOF w ,UNTMP_ADJUSTDETAIL z"
//				            + " where 1=1"  
//				            + " and w.enterorg = z.enterorg and w.ownership = z.ownership and w.caseno =z.caseno"  
//				            + " and w.caseno = ( select min(wa.caseno) from UNTMP_ADJUSTPROOF wa ,UNTMP_ADJUSTDETAIL za" 
//				                             + " where wa.enterorg = za.enterorg and wa.ownership = za.ownership and wa.caseno =za.caseno" 
//				                             + " and za.enterorg = z.enterorg and za.ownership = z.ownership and za.propertyno = z.propertyno and za.serialno = z.serialno" 
//				                             + " and w.verify='Y'"  
//				                             + " and w.writedate > '" + Datetime.getYYYYMMDD() + "'"
//				                           + " )" 
//				          + " ) zb on c.enterorg = zb.enterorg  and c.ownership = zb.ownership  and c.propertyno = zb.propertyno  and c.serialno = zb.serialno"
				          + " ,SYSCA_ORGAN o" 
				          
				   + " where 1=1 " + "\n"
				   // + " and a.enterorg = b.enterorg and a.ownership = b.ownership and a.caseno = b.caseno and a.differencekind = b.differencekind" + "\n"
				   + " and b.enterorg = c.enterorg and b.ownership = c.ownership and b.differencekind = c.differencekind and b.propertyno = c.propertyno and b.lotno = c.lotno " + "\n"
				   + " and b.enterorg = o.organid   and c.datastate='1' " + "\n"

				   + " and b.enterorg = " + Common.sqlChar(q_enterOrg) + "\n"
				   + " and b.verify='Y' " + "\n"
				   + where + "\n"
				   + " order by enterorg ,ownership ,b.differencekind ,propertykind ,fundtype ,propertyno ,serialno";
	
	    System.out.println("mp029: "+xls_Sql);

	    DefaultTableModel model = new javax.swing.table.DefaultTableModel();
	    Database db = new Database();
	    try{
	    	String[] columns = new String[] {"entergName","propertyKind","dataType","dateSE"
	    									 ,"ownershipName","propertyno","propertyname"
	    									 ,"otherproperytnoName","nameplate","material"
	    									 ,"bookvalue","sourcedate","limityear","useYM","place"
	    									 ,"user","unit","equipmentName","printDate","chengType"
	    									 ,"Amount", "subCount", "totalCount","netvalue","sumBookvalue"
	    									 ,"sumAllBookvalue"};
	    	//使用者操作記錄
			Common.insertRecordSql(xls_Sql, this.getOrganID(), this.getUserID(), "UNTMP029R", this.getObjPath().replaceAll("&gt;", ">"));
			
	        ResultSet rs = db.querySQL_NoChange(xls_Sql);

	        Map<String,String> DifferenceKindMap = TCGHCommon.getSysca_Code("DFK");
	        StringTools st = new StringTools();	
	        Vector rowData = new Vector();
	        int subCount = 0 ;
	        int totalCount = 0;
	        String tempOwnershipName = "";
	        long sumbookvalue = 0;
	        long sumnetvalue = 0;
	    	while(rs.next()){
	    		ResultSet rs3 = db.querySQL(getTotalEquipmentName(rs.getString("ownership"),rs.getString("differencekind"),rs.getString("propertyno"),rs.getString("serialno")));
	    		sumbookvalue += rs.getLong("bookvalue");
	    		sumnetvalue += rs.getLong("netvalue");
				while (rs3.next()){
				    equipmentName += rs3.getString("equipmentName")+";";
				}
				setEquipmentName(equipmentName);
				rs3.close();
				Object[] data = new Object[columns.length];
				data[0]=rs.getString("organaname");										//entergName
				String DIF = rs.getString("differencekind");
				data[1]= DifferenceKindMap.get(DIF).toString();		//differenceKind
				data[2]= dataTypeName;													//dataType
				data[3]= dataSE;														//dateSE
				data[4]= Common.get(rs.getString("ownershipName"))
					+" "+Common.get(rs.getString("fundtypeName"));	//ownershipName
				
				if(tempOwnershipName.equals(Common.get(rs.getString("ownershipName")))){
					tempOwnershipName = Common.get(rs.getString("ownershipName"));
					subCount = 0;
				}
				
				subCount++;
				totalCount++;
				
				String str;
				str = rs.getString("propertyno_a") + "\n-" + rs.getString("serialno");
				if(rs.getString("oldserialno")=="1"){
					str += "" + rs.getString("oldserialno") + ")";
				}//propertyno
				data[5] = str;
				data[6]=rs.getString("propertyname");									//propertyname
				data[7]=rs.getString("propertyname1");									//otherproperytnoName
				data[8]=Common.get(rs.getString("specification")) + "/" + Common.get(rs.getString("nameplate"));		//nameplate
				data[9]=rs.getString("othermaterial");										//material
				data[10]= st._getMoneyFormat(checkGet(rs.getString("bookvalue")));						//bookvalue
				data[11]=Common.MaskDate(new Datetime().changeTaiwanYYMMDD(rs.getString("sourcedate"), "1")) + "\n" + Common.MaskDate(new Datetime().changeTaiwanYYMMDD(rs.getString("buydate"), "1"));//sourcedate
			
				int otherLimitMonth = "".equals(Common.get(rs.getString("otherLimitYear")))? 0 : Integer.parseInt(rs.getString("otherLimitYear")); // otherLimitYear以單位月分存取需轉成年
				int year = otherLimitMonth /12;
				int month = otherLimitMonth % 12;
				data[12] = year +"年"+month+"月";								    //limityear
				data[13]=useDate(new Datetime().changeTaiwanYYMMDD(rs.getString("buydate"), "1"));							//useYM
				if(!"".equals(Common.get(rs.getString("place1Name")))){                                                      //place
					data[14] = Common.get(rs.getString("place1Name"));
				}else{
					data[14] = Common.get(rs.getString("place"));
				}									
				
				String user = null,unit = null;
				
				user= Common.get(rs.getString("keepUnitName"))+"\n"+Common.get(rs.getString("keeperName"));	//user
				unit = Common.get(rs.getString("useUnitName"))+"\n"+Common.get(rs.getString("userName"));		//unit
				data[15] = user;
				data[16] = unit;
				if("".equals(user)) data[15] = " ";
				if("".equals(unit)) data[16] = " ";
				
				//getTotalEquipmentName();
				data[17]=equipmentName;
	    	    		//data[20] = getEquipmentName();
				data[18] = "製表日期：" + Datetime.getYYYMMDD().substring(0,3) + "年" + Datetime.getYYYMMDD().substring(3,5) + "月" + Datetime.getYYYMMDD().substring(5)+ "日";
				
//				if("1".equals(getQ_chengPage())){
//					data[19] = rs.getString("propertykind") + rs.getString("fundtype");
//				}else{
//					data[19] = rs.getString("propertykind") + rs.getString("fundtype") + rs.getString("propertyno");
//				}
				// 分頁模式, but ireport根本沒用到, 2016/11/29移除畫面上分頁模式選項, 程式暫且保留
				data[19] = rs.getString("propertykind") + rs.getString("fundtype");
				
				data[20] = rs.getString("Amount");
				
				data[21] = String.valueOf(subCount);
					
				data[22] = String.valueOf(totalCount);
				
				data[23] = st._getMoneyFormat(checkGet(rs.getString("netvalue")));
				
				data[24] = st._getMoneyFormat(checkGet(String.valueOf(sumbookvalue))) + "\n" + st._getMoneyFormat(checkGet(String.valueOf(sumnetvalue)));
				
				data[25] = st._getMoneyFormat(checkGet(String.valueOf(sumbookvalue))) + "\n" + st._getMoneyFormat(checkGet(String.valueOf(sumnetvalue)));
				
	   			rowData.addElement(data);
	    	}
	    	rs.getStatement().close();
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
	
	/*
	**�p��Y����ܨt�Τ�����~��e��e�����f
	**useDateS�G�_�l���
	*/
	public String useDate(String useDateS){
		String reuseDateS="";
		int a=0;
		int b=0;
		if(!"".equals(useDateS) && useDateS != null){
			a=(Integer.parseInt(useDateS.substring(0,3))*12)+Integer.parseInt(useDateS.substring(3,5));
			b=(Integer.parseInt(Datetime.getYYYMMDD().substring(0,3))*12)+Integer.parseInt(Datetime.getYYYMMDD().substring(3,5));
			reuseDateS=String.valueOf((b-a)/12)+"年"+String.valueOf((b-a)%12)+"月";
		}else{
			reuseDateS="0年0月";
		}
		return reuseDateS;
	}
	
	//���Ӱʲ����uUNTMP_Attachment2�ʲ��帹��Ӫ��ݳ]���ɡv����ƪ��A���u1:�{�s�v�̤��uequipmentName�W�١v�A�W�ٻP�W�٤����H�u�B�v�Ϲj
	protected String getTotalEquipmentName(String ownership,String differencekind,String propertyno,String serialno){
		equipmentName="";
		String sql="select DISTINCT a.equipmentname " +
					" from UNTMP_ATTACHMENT2 a" +
					" where 1=1 and a.datastate='1'" + 
					"";	
		if (!"".equals(getQ_enterOrg()))
			sql+=" and a.enterorg = " + Common.sqlChar(getQ_enterOrg()) ;
		sql+=" and a.ownership = " + Common.sqlChar(ownership) ;
		sql += " and a.propertyno = " + Common.sqlChar(propertyno);
		sql += " and a.differencekind = " + Common.sqlChar(differencekind);
		sql+=" and a.serialno = " + Common.sqlChar(serialno) ;
		return sql;
	}
	public String getFieldList(String mgrOrg, String userOrg) throws Exception {
		Database db = new Database();
		ResultSet rs = null;
		String sSQL = "";
		StringBuffer sb = new StringBuffer("");
		try {
			sSQL = "select placeno,placename from SYSCA_PLACE where enterorg = ";
			sSQL +="'"+userOrg+"'" + " order by placeno";
			rs = db.querySQL(sSQL);
			System.out.println(sSQL);
			while (rs.next()) {
				sb.append("<option value='" ).append( rs.getString("placeno") ).append( ":;:" ).append( rs.getString("placename") ).append( "'>" ).append(rs.getString("placeno")).append("\t").append(rs.getString("placename")).append( "</option>");
			}
			rs.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.closeAll();
		}
		return sb.toString();
	}

}