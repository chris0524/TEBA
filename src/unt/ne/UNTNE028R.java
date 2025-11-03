/*
*<br>程式目的：非消耗品明細清冊查詢檔 
*<br>程式代號：untne028p
*<br>撰寫日期：103/09/17
*<br>程式作者：Mike Kao
*<br>--------------------------------------------------------
*<br>修改作者　　修改日期　　　修改目的
*<br>--------------------------------------------------------
*/

package unt.ne;

import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

import javax.swing.table.DefaultTableModel;

import util.Common;
import util.Database;
import util.Datetime;
import util.SuperBean;

public class UNTNE028R extends SuperBean{
	String q_enterOrg;
	String q_enterOrgName;
	String q_ownership;
	String q_valuable;
	String q_propertyNoS;
	String q_propertyNoE;
	String q_propertyNoSName;
	String q_propertyNoEName;
	String q_serialNo;
	String q_enterDateS;
	String q_enterDateE;
	String q_dataTypeName;
	String q_enterDateName;
	String q_enterDate;
	String q_chengPage;
	String q_differenceKind;
	String equipmentName;
	String propertyKindFundType;
	String isOrganManager;
	String isAdminManager;
	String organID;   
	String fileName;
	private String q_keepUnit;
	private String q_keeper;
	private String q_reportType;						// 報表類型
	
	public String getQ_reportType() { return checkGet(q_reportType); }
	public void setQ_reportType(String q_reportType) { this.q_reportType = checkGet(q_reportType); }
	public String getQ_enterOrg(){ return checkGet(q_enterOrg); }
	public void setQ_enterOrg(String s){ q_enterOrg=checkSet(s); }
	public String getQ_enterOrgName(){ return checkGet(q_enterOrgName); }
	public void setQ_enterOrgName(String s){ q_enterOrgName=checkSet(s); }
	public String getQ_ownership(){ return checkGet(q_ownership); }
	public void setQ_ownership(String s){ q_ownership=checkSet(s); }
	public String getQ_valuable(){ return checkGet(q_valuable); }
	public void setQ_valuable(String s){ q_valuable=checkSet(s); }
	public String getQ_propertyNoS(){ return checkGet(q_propertyNoS); }
	public void setQ_propertyNoS(String s){ q_propertyNoS=checkSet(s); }
	public String getQ_propertyNoE(){ return checkGet(q_propertyNoE); }
	public void setQ_propertyNoE(String s){ q_propertyNoE=checkSet(s); }
	public String getQ_propertyNoSName(){ return checkGet(q_propertyNoSName); }
	public void setQ_propertyNoSName(String s){ q_propertyNoSName=checkSet(s); }
	public String getQ_differenceKind() {return checkGet(q_differenceKind);}
	public void setQ_differenceKind(String s) {q_differenceKind = checkSet(s);}
	public String getQ_propertyNoEName(){ return checkGet(q_propertyNoEName); }
	public void setQ_propertyNoEName(String s){ q_propertyNoEName=checkSet(s); }	
	public String getQ_serialNo(){ return checkGet(q_serialNo); }
	public void setQ_serialNo(String s){ q_serialNo=checkSet(s); }	
	public String getQ_enterDateS(){ return checkGet(q_enterDateS); }
	public void setQ_enterDateS(String s){ q_enterDateS=checkSet(s); }
	public String getQ_enterDateE(){ return checkGet(q_enterDateE); }
	public void setQ_enterDateE(String s){ q_enterDateE=checkSet(s); }
	
	public String getQ_chengPage(){ return checkGet(q_chengPage); }
	public void setQ_chengPage(String s){ q_chengPage=checkSet(s); }
	public String getEquipmentName(){ return checkGet(equipmentName); }
	public void setEquipmentName(String s){ equipmentName=checkSet(s); }
	public String getPropertyKindFundType(){ return checkGet(propertyKindFundType); }
	public void setPropertyKindFundType(String s){ propertyKindFundType=checkSet(s); }
	public String getOrganID() { return checkGet(organID); }
	public void setOrganID(String s) { organID = checkSet(s); }
	public String getIsOrganManager() { return checkGet(isOrganManager); }
	public void setIsOrganManager(String s) { isOrganManager = checkSet(s); }
	public String getIsAdminManager() { return checkGet(isAdminManager); }
	public void setIsAdminManager(String s) { isAdminManager = checkSet(s); }
	public String getFileName() { return checkGet(fileName); }
	public void setFileName(String s) { fileName = checkSet(s); }
	public String getQ_keepUnit() { return checkGet(q_keepUnit); }
	public void setQ_keepUnit(String s) { this.q_keepUnit = checkSet(s); }
	public String getQ_keeper() { return checkGet(q_keeper); }
	public void setQ_keeper(String qKeeper) { q_keeper = checkSet(qKeeper); }
	
	
	private String[] q_sDestField;
	public String[] getQ_sDestField(){ return q_sDestField; }
	public void setQ_sDestField(String[] s){ q_sDestField = s; }
	
	StringBuilder strEngFields = new StringBuilder();
	public DefaultTableModel getResultModel() throws Exception{		
		String endDate=Datetime.changeTaiwanYYMM(Datetime.getYYYMMDD(), "2");
		String where="";
		
		if(!"".equals(getQ_enterOrg())) {
			where += " and c.enterorg = " + Common.sqlChar(q_enterOrg);
		}
		if(!"".equals(getQ_ownership())) {
			where += " and c.ownership = " + Common.sqlChar(q_ownership);
		}
		if(!"".equals(getQ_differenceKind())) {
			where += " and c.differencekind = " + Common.sqlChar(q_differenceKind);
		}
		if(!"".equals(getQ_valuable())) {
			where += " and b.valuable = " + Common.sqlChar(q_valuable);
		}
		if(!"".equals(getQ_propertyNoS())) {
			where += " and c.propertyno >= " + Common.sqlChar(q_propertyNoS);
		}
		if(!"".equals(getQ_propertyNoE())) {
			where += " and c.propertyno <= " + Common.sqlChar(q_propertyNoE);
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
				
		
	    DefaultTableModel model = new javax.swing.table.DefaultTableModel();
	    Database db = new Database();
	    
	    String xlsSql = " select c.enterorg, o.organaname " + "\n"
					  + "        ,c.ownership ,( select pp.codename from SYSCA_CODE pp where pp.codekindid='OWA' and pp.codeid=c.ownership ) as ownershipName  " + "\n"
					  + "        ,c.differencekind ,( select pp.codename from SYSCA_CODE pp where pp.codekindid='DFK' and pp.codeid=c.differencekind ) as differencekindName " + "\n"
					  + "        ,b.propertyno + '-' +c.serialno as propertySerialno ,b.propertyno ,c.serialno ,c.oldserialno " + "\n"
					  + "        ,d.propertyname ,c.propertyname1 ,b.specification ,b.nameplate " + "\n"
					  + "        ,isnull(b.othermaterial,p.material) as othermaterial,d.material ,b.sourcedate ,b.buydate , b.otherlimityear ,c.place  " + "\n"
					  + "        ,(select placename from SYSCA_PLACE z where z.enterorg = c.enterorg and z.placeno = c.place1) as place1Name" + "\n"
					  + "        ,c.keepunit ,c.keeper,ua.unitname as keepUnitName ,ka.keepername as keeperName " + "\n"
					  + "        ,c.useunit ,c.userno ,ub.unitname as useUnitName  ,kb.keepername as userName " + "\n"
//					  + "        ,ISNULL(zb.oldbookvalue ,ISNULL(r.oldbookvalue ,c.bookvalue)) as bookvalue " + "\n"
					  + "        ,c.bookvalue as bookvalue " + "\n"
					  + "        ,c.bookvalue as nowbookvalue" + "\n"
					  
					  + "  from UNTNE_NONEXP b ,SYSCA_ORGAN o ,UNTNE_NONEXPDETAIL c " +"\n"
					  + "  left join SYSPK_PROPERTYCODE2 d on  c.enterorg = d.enterorg and c.propertyno = d.propertyno"+"\n"
					  + "  left join UNTMP_KEEPUNIT ua on  c.enterorg = ua.enterorg and c.keepunit = ua.unitno"+"\n"
					  + "  left join UNTMP_KEEPUNIT ub on c.enterorg = ub.enterorg and c.useunit = ub.unitno"+"\n"
					  + "  left join UNTMP_KEEPER   ka on c.enterorg = ka.enterorg and c.keeper = ka.keeperno"+"\n"
					  + "  left join UNTMP_KEEPER   kb on  c.enterorg = kb.enterorg and c.userno = kb.keeperno"+"\n"
					  + "  left join SYSPK_PROPERTYCODE2 p on p.enterorg in ('000000000A',c.enterorg) and c.propertyno = p.propertyno " 
//					  + "  left join UNTNE_REDUCEDETAIL  r on c.enterorg = r.enterorg and c.ownership = r.ownership and c.propertyno = r.propertyno and c.serialno = r.serialno" + "\n"
//
//					  //+ "      ,UNTNE_Attachment2 ua2" + "\n"
//					  
//					  + " 	left join	( select w.enterorg ,w.ownership ,z.propertyno ,z.serialno ,z.oldbookvalue " + "\n"
//					  + "          from UNTNE_ADJUSTPROOF w ,UNTNE_ADJUSTDETAIL z " + "\n"
//					  + "          where 1=1  " + "\n"
//					  + "          and w.enterorg = z.enterorg and w.ownership = z.ownership and w.caseno =z.caseno  " + "\n"
//					  + "          and w.caseno = ( select min(wa.caseno) from UNTNE_ADJUSTPROOF wa ,UNTNE_ADJUSTDETAIL za " + "\n"
//					  + "                           where wa.enterorg = za.enterorg and wa.ownership = za.ownership and wa.caseno =za.caseno " + "\n"
//					  + "                           and za.enterorg = z.enterorg and za.ownership = z.ownership and za.propertyno = z.propertyno and za.serialno = z.serialno " + "\n"
//					  + "                           and w.verify='Y'  " + "\n"
//					  + "                           and w.writedate > " + Common.sqlChar(endDate) + "\n"
//					  + "                         ) " + "\n"
//					  + "        ) zb on c.enterorg = zb.enterorg and c.ownership = zb.ownership and c.propertyno = zb.propertyno and c.serialno = zb.serialno" + "\n"

					  + " where 1=1 " + "\n"
					  + " and b.enterorg = c.enterorg and b.ownership = c.ownership and b.differencekind = c.differencekind and b.propertyno = c.propertyno and b.lotno = c.lotno " + "\n"
					  + " and c.enterorg = o.organid " + "\n"
					  + " and c.verify = 'Y' " + "\n"
					  + " and c.datastate = '1'" +"\n"
					  + where +"\n"					 
					  + " order by enterorg ,ownership ,differencekind ,propertyno ,serialno  ";
					
System.out.println("-- untne028r getResultModel --\n"+xlsSql);
	    try{
	    	Map totalEquipmentName = getTotalEquipmentName(db);
	    	String[] columns = new String[] {"entergName","differenceKind","dataType","dateSE"
					 						 ,"ownershipName","propertyno","propertyname"
					 						 ,"otherproperytnoName","nameplate","material"
					 						 ,"bookvalue","sourcedateBuydate","limityear","useYM","place"
					 						 ,"user","unit","equipmentName","printDate","chengType"};

	        ResultSet rs = db.querySQL_NoChange(xlsSql);
	        Vector rowData = new Vector();
	    	while(rs.next()){
				Object[] data = new Object[20];
				
				data[0]= Common.get(rs.getString("organaname"));									//entergName
				data[1]= "物品區分別：" + rs.getString("differencekindName") ;							//differencekindName
				data[2]= "";													//dataType  不知道幹嘛的
				data[3]= "";													//dateSE	不知道幹嘛得
				data[4]= "權　　屬：" + Common.get(rs.getString("ownershipName"));					//ownershipName
//				if(!"".equals(Common.get(rs.getString("oldserialno")))){
//					data[5]= Common.get(rs.getString("propertyno")) + "\n-" + Common.get(rs.getString("serialno"))
//						   + "\n(原" + Common.get(rs.getString("oldserialno")) + ")";//propertyno
//				}else{
					data[5]= Common.get(rs.getString("propertyno")) + "\n-" + Common.get(rs.getString("serialno"));//propertyno
//				}
				data[6]= Common.get(rs.getString("propertyname"));									//propertyname
				data[7]= Common.get(rs.getString("propertyname1"));									//otherproperytnoName
				data[8]= Common.get(rs.getString("specification")) + "╱" + Common.get(rs.getString("nameplate"));		//nameplate
//				data[9]= Common.get(rs.getString("material"));										//material
				data[9]= Common.get(rs.getString("othermaterial"));									//othermaterial
				data[10]= Common.get(rs.getInt("bookvalue"));										//bookvalue
				data[11]= Common.MaskDate(new Datetime().changeTaiwanYYMMDD(rs.getString("sourcedate"),"1")) + "\n" + Common.MaskDate(new Datetime().changeTaiwanYYMMDD(rs.getString("buydate"),"1"));					//sourcedate
				int otherLimitMonth = "".equals(Common.get(rs.getString("otherLimitYear")))? 0 : Integer.parseInt(rs.getString("otherLimitYear")); // otherLimitYear以單位月分存取需轉成年
				int year = otherLimitMonth /12;
				int month = otherLimitMonth % 12;
				data[12] = year +"年"+month+"月"; //limityear
				data[13]= useDate(new Datetime().changeTaiwanYYMMDD(rs.getString("sourcedate"),"1"));							//useYM
				if(!"".equals(Common.get(rs.getString("place1Name")))){                                     //place1
					data[14] = Common.get(rs.getString("place1Name"));
				}else{
					data[14] = Common.get(rs.getString("place"));
				}										
				data[15]= Common.get(rs.getString("keepUnitName"))+"\n"+ Common.get(rs.getString("keeperName"));	//user
				data[16]= Common.get(rs.getString("useUnitName"))+"\n"+ Common.get(rs.getString("userName"));		//unit
				data[17]=Common.get((String)totalEquipmentName.get(rs.getString("propertyno")+"-"+rs.getString("serialNo"))); 

				data[18] = "製表日期：" + Datetime.getYYYMMDD().substring(0,3) + "年" + Datetime.getYYYMMDD().substring(3,5) + "月" + Datetime.getYYYMMDD().substring(5)+ "日";
				
//				if("1".equals(getQ_chengPage())){
//					data[19] = Common.get(rs.getString("enterorg")) + Common.get(rs.getString("ownership")) ;
//				}else{
//					data[19] = Common.get(rs.getString("enterorg")) + Common.get(rs.getString("ownership")) + Common.get(rs.getString("differencekind")) + Common.get(rs.getString("propertyno"));
//				}
				// 2016/11/29移除畫面上分頁模式選項, 程式暫且保留
				data[19] = Common.get(rs.getString("enterorg")) + Common.get(rs.getString("ownership")) ;
				
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
	**計算某日期至系統日期之年月〔算前不算後〕
	**useDateS：起始日期
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
	
	
	//抓取該物品之「UNTNE_Attachment2 物品批號明細附屬設備檔」中資料狀態為「1:現存」者之「equipmentName名稱」，名稱與名稱之間以「、」區隔
	protected Map getTotalEquipmentName(Database db){
	    StringBuffer equipmentName = new StringBuffer(); 
	    Map map = new HashMap();
	    String key = "";
		int count = 0 ;
		ResultSet rs;	
		String sql="select ua2.propertyno, ua2.serialNo, ua2.serialNo1, ua2.equipmentName " +"\n"+
					" from UNTNE_NONEXPDETAIL c, UNTNE_ATTACHMENT2 ua2" +"\n"+
					" where 1=1 and c.verify = 'Y' and ua2.dataState='1' " + "\n"+
					" and c.enterorg=ua2.enterorg and c.ownership=ua2.ownership and c.differencekind=ua2.differencekind and c.propertyno=ua2.propertyno and c.lotno=ua2.lotno and c.serialno=ua2.serialno " +"\n"+
					" and c.enterOrg = " + Common.sqlChar(getQ_enterOrg()) +"\n"+
					" and c.ownership = " + Common.sqlChar(getQ_ownership()) +"\n"+
					" and c.differencekind = " + Common.sqlChar(getQ_differenceKind()) +"\n"  ;
		if(!"".equals(getQ_propertyNoS())) sql += " and c.propertyno >= " + Common.sqlChar(q_propertyNoS);
		if(!"".equals(getQ_propertyNoE())) sql += " and c.propertyno <= " + Common.sqlChar(q_propertyNoE);
		
		sql += " order by c.enterorg ,c.ownership, c.differencekind ,c.propertyno ,c.serialno" ;
		
		try{
			rs = db.querySQL(sql);
			while (rs.next()){
				if (!"".equals(key) && !key.equalsIgnoreCase(rs.getString("propertyno")+"-"+rs.getString("serialNo"))){
					map.put(key,equipmentName.toString()); 
					count = 0;  equipmentName.setLength(0);
				}
				count++;
				equipmentName=count==1?equipmentName.append(rs.getString("equipmentName")):equipmentName.append("、"+rs.getString("equipmentName"));
				key = rs.getString("propertyno")+"-"+rs.getString("serialNo");
			}
			map.put(key,equipmentName.toString());
			return map;
		} catch (Exception e) {
			System.out.println("-- Exception：untne028r getTotalEquipmentName --\n"+sql);
			e.printStackTrace();
		}
		return map;
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