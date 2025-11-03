
package unt.pd;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.swing.table.DefaultTableModel;

import unt.ch.UNTCH_Tools;
import util.Common;
import util.Database;
import util.Datetime;
import util.SuperBean;
import TDlib_Simple.tools.src.LogTools;

/**
 *<br/>程式目的：非消耗品盤點清冊
 *<br/>程式代號：untpd014p
 *<br/>撰寫日期：2014/09/30
 *<br/>程式作者：Mike Kao
 *<br/>--------------------------------------------------------
 *<br/>修改作者　　修改日期　　　修改目的
 *<br/>--------------------------------------------------------
 */
public class UNTPD014R extends SuperBean {

	public String getLockId(String organid) {
		return Common.get(organid) + "產製物品盤點資料";
	}
	
	private String q_enterOrg;
	private String q_enterOrgName;
	private String q_chengPageStyle;
	private String q_keepUnit;
	private String q_keeper;
	private String q_differenceKind;
	private String q_propertyNoS;
	private String q_propertyNoE;
	private String q_propertyNoSName;
	private String q_propertyNoEName;
	private String q_titleName;
	private String q_signature;
	private String fileName;
	private String organID;  
	private String q_reportType;						// 報表類型
	private String q_note;
	private String q_noteText;
	
	public String getQ_reportType() {	return q_reportType;}
	public void setQ_reportType(String q_reportType) { this.q_reportType = checkSet(q_reportType); }
	public String getQ_enterOrg(){ return checkGet(q_enterOrg); }
	public void setQ_enterOrg(String s){ q_enterOrg=checkSet(s); }
	public String getQ_enterOrgName(){ return checkGet(q_enterOrgName); }
	public void setQ_enterOrgName(String s){ q_enterOrgName=checkSet(s); }
	public String getQ_chengPageStyle(){ return checkGet(q_chengPageStyle); }
	public void setQ_chengPageStyle(String s){ q_chengPageStyle=checkSet(s); }
	public String getQ_keepUnit() {return checkGet(q_keepUnit);}
	public void setQ_keepUnit(String qKeepUnit) {q_keepUnit = checkSet(qKeepUnit);}
	public String getQ_keeper() {return checkGet(q_keeper);}
	public void setQ_keeper(String qKeeper) {q_keeper = checkSet(qKeeper);}
	public String getQ_propertyNoS(){ return checkGet(q_propertyNoS); }
	public void setQ_propertyNoS(String s){ q_propertyNoS=checkSet(s); }
	public String getQ_propertyNoE(){ return checkGet(q_propertyNoE); }
	public void setQ_propertyNoE(String s){ q_propertyNoE=checkSet(s); }
	public String getQ_propertyNoSName(){ return checkGet(q_propertyNoSName); }
	public void setQ_propertyNoSName(String s){ q_propertyNoSName=checkSet(s); }
	public String getQ_propertyNoEName(){ return checkGet(q_propertyNoEName); }
	public void setQ_propertyNoEName(String s){ q_propertyNoEName=checkSet(s); }
	public String getQ_differenceKind() {return checkGet(q_differenceKind);}
	public void setQ_differenceKind(String s) {q_differenceKind = checkSet(s);}
	public String getFileName() { return checkGet(fileName); }
	public void setFileName(String s) { fileName = checkSet(s); }  
	public String getOrganID() { return checkGet(organID); }
	public void setOrganID(String s) { organID = checkSet(s); }
	public String getQ_titleName() {return checkGet(q_titleName);}
	public void setQ_titleName(String q_titleName) {this.q_titleName = checkSet(q_titleName);}
	public String getQ_signature() {return checkGet(q_signature);}
	public void setQ_signature(String q_signature) {this.q_signature = checkSet(q_signature);}
	public String getQ_note() {return checkGet(q_note);}
	public void setQ_note(String q_note) {this.q_note = checkSet(q_note);}
	public String getQ_noteText() {return checkGet(q_noteText);}
	public void setQ_noteText(String q_noteText) {this.q_noteText = checkSet(q_noteText);}
	
	public String[] q_place1;
	public String[] getQ_place1(){ return q_place1; }
	public void setQ_place1(String[] s){ q_place1=s; }
	
	private String[] q_sDestField;
	public String[] getQ_sDestField(){ return q_sDestField; }
	public void setQ_sDestField(String[] s){ q_sDestField=s; }
	
	String strEngFields="";
	
	private LogTools log = new LogTools();

	public DefaultTableModel getResultModel() throws Exception {
		
		Database db = null;
		ResultSet rs = null;
		List<Object[]> rows = null;
		DefaultTableModel model = null;
		try {
			this.export();
			String[] columns = new String[] { "head01","head02","head03","head04","head05",
											  "detail01","detail02","detail03","detail04","detail05",
											  "detail06","detail07","detail08","detail09","detail10",
											  "detail11", "pageChange" };

			//=================================================================
			String execSQL = "select a.organaname, b.differencekindname, b.propertytypename, b.propertyno, " +
							 "b.serialno, b.propertyname, b.propertyname1, b.specification, b.nameplate, " +
							 "b.propertyunit, b.bookamount, b.bookvalue, b.buydate, b.sourcedate," +
							 "case when b.limityear is null then '0' else b.limityear end  as limityear, " +
							 "case when b.useyear is null then '0' else b.useyear end as useyear, " +
							 "b.place1name, b.place, b.place1, b.keepunitname, " +
							 "b.useunitname, b.keepername, b.oldserialno " +
							 "from SYSCA_ORGAN a, " +
							 "UNTPD_CHECKNONEXP b " +
							 //"left join SYSPK_PROPERTYCODE2 c on (b.enterorg = c.enterorg and b.propertyno = c.propertyno) "+
							 "where a.organid = b.enterorg " +
							 "and b.enterorg = " + Common.sqlChar(getQ_enterOrg()) + " ";
			
			if (!"".equals(getQ_differenceKind())) {
				execSQL += "and b.differencekind = " + Common.sqlChar(getQ_differenceKind()) + " " ; 
			}
			
			if (!"".equals(getQ_propertyNoS())) {
				execSQL += "and b.propertyno >= " + Common.sqlChar(getQ_propertyNoS()) + " " ;
			}
			
			if (!"".equals(getQ_propertyNoE())) {
				execSQL += "and b.propertyno <= " + Common.sqlChar(getQ_propertyNoE()) + " " ;
			}
			
			if (!"".equals(getQ_keepUnit())) {
				execSQL += "and b.keepunit = " + Common.sqlChar(getQ_keepUnit()) + " " ;
			}
			
			if (!"".equals(getQ_keeper())) {
				execSQL += "and b.keeper = " + Common.sqlChar(getQ_keeper()) + " " ;
			}
			
			if (!(getQ_sDestField()==null)){
				execSQL += "and b.place1 in ("+ strEngFields +") ";
			}
			
			if ("1".equals(this.getQ_chengPageStyle())) { // 保管單位
				execSQL += "order by b.keepunitname, a.organaname, b.differencekindname, b.propertytypename, b.propertyno, b.serialno ";
			} else if ("2".equals(this.getQ_chengPageStyle())) { // 保管單位+保管人
				execSQL += "order by b.keepunitname, b.keepername,  a.organaname, b.differencekindname, b.propertytypename, b.propertyno, b.serialno ";
			} else if ("3".equals(this.getQ_chengPageStyle())) { // 財產類別
				execSQL += "order by a.organaname, b.differencekindname, b.propertytypename, b.propertyno, b.serialno ";
			} else if ("4".equals(this.getQ_chengPageStyle())) { // 財產編號
				execSQL += "order by b.propertyno, b.serialno ";
			} else if ("5".equals(this.getQ_chengPageStyle())) { // 存置地點
				execSQL += "order by b.place1,b.keepunitname, a.organaname, b.differencekindname, b.propertytypename, b.propertyno, b.serialno ";
			} else { // 保管人+保管單位
				execSQL += "order by b.keepername, b.keepunitname, a.organaname, b.differencekindname, b.propertytypename, b.propertyno, b.serialno ";
			}

			UNTCH_Tools ut = new UNTCH_Tools();
			String orgName = ut._queryData("organaname")._from("SYSCA_ORGAN a ")._with(" and organid = '" + this.getQ_enterOrg() + "'")._toString();
			String userName = ut._queryData("empname")._from("SYSAP_EMP a ")._with(" and empid = '" + this.getUserID() + "'")._toString();
			db = new Database();
			
			//使用者操作記錄
			Common.insertRecordSql(execSQL.toString(), this.getOrganID(), this.getUserID(), "UNTPD014R", this.getObjPath().replaceAll("&gt;", ">"));
			
			rs = db.querySQL(execSQL.toString());
			System.out.println(execSQL);
			while (rs.next()) {
				if (rows == null) {
					rows = new ArrayList<Object[]>();
				}
				
				Object[] data = new Object[columns.length];

				//===========================================================
				//						Head
				//===========================================================
				//全銜
				data[0] = orgName;

				//印表日期
				String datetime = Datetime.getYYYMMDD();
				data[1] = datetime.substring(0,3) + "年" + datetime.substring(3,5) + "月" + datetime.substring(5) + "日";

				//印表人
				data[2] = userName;

				//物品區分別
				data[3] = checkGet(rs.getString("differencekindname"));

				//物品大類
				data[4] = checkGet(rs.getString("propertytypename"));

				//===========================================================
				//						Detail
				//===========================================================
				//物品編號
				data[5] = checkGet(rs.getString("propertyno"))+"-"+checkGet(rs.getString("serialno"));
//				data[5] = ReportUtil.getStringWithOldSerialNo(this.getQ_enterOrg(), Common.get(data[5]), "\r\n", "原財產分號", Common.get(rs.getString("oldserialno")));

				//物品名稱/別名
				data[6] =checkGet(rs.getString("propertyname"))+"\n"+checkGet(rs.getString("propertyname1"));

				//型式/廠牌 
				data[7] =checkGet(rs.getString("specification")) + "\n" + checkGet(rs.getString("nameplate"));

				//數量  單位
				data[8] = checkGet(rs.getString("bookamount"))+"\n"+checkGet(rs.getString("propertyunit"));
				//String.valueOf(Double.parseDouble(checkGet(rs.getString("bookamount"))))+"\n"+checkGet(rs.getString("propertyunit"));

				//價值
				data[9] = Common.valueFormat(checkGet(rs.getString("bookvalue")));

				//購置日期  取得日期
				data[10] = Common.MaskDate(new Datetime().changeTaiwanYYMMDD(checkGet(rs.getString("buydate")),"1"))+"\n"+Common.MaskDate(new Datetime().changeTaiwanYYMMDD(checkGet(rs.getString("sourcedate")),"1"));

				//使用年限   已使用年數
				data[11] =Datetime.formatMonths(checkGet(rs.getString("limityear")))+"\n"+checkGet(rs.getString("useyear"));

				//存置地點   存置地點說明
				data[12] =checkGet(rs.getString("place1name"))+"\n"+checkGet(rs.getString("place"));

				//保管單位   使用單位
				data[13] =checkGet(rs.getString("keepunitname"))+"\n"+checkGet(rs.getString("useunitname"));

				//保管人
				data[14] =checkGet(rs.getString("keepername"));

				//檢查情形
				data[15] ="";

				if ("1".equals(getQ_chengPageStyle())) { // 保管單位
					data[16] =Common.get(rs.getString("keepunitname"))+Common.get(rs.getString("differencekindname")); 
				} else if ("2".equals(getQ_chengPageStyle()) || "6".equals(this.getQ_chengPageStyle())) { // 保管單位+保管人 or 保管人+保管單位
					data[16] =Common.get(rs.getString("keepunitname"))+"\n"+checkGet(rs.getString("keepername"))+Common.get(rs.getString("differencekindname"));
				} else if ("3".equals(getQ_chengPageStyle())) { // 財產類別
					data[16] =Common.get(rs.getString("differencekindname"));
				} else if ("4".equals(getQ_chengPageStyle())) { // 財產編號
					data[16] =Common.get(rs.getString("propertyno"));
				} else { // 存置地點
					data[16] =Common.get(rs.getString("place1"))+Common.get(rs.getString("differencekindname"));
				}
				
				rows.add(data);
			}

			//=================================================================
			if (rows != null && rows.size() > 0) {
				String uuid = UUID.randomUUID().toString();
				log._execLogInfo("非消耗品盤點清冊列印(" + uuid + ")  SQL:" + execSQL);
				log._execLogInfo("非消耗品盤點清冊列印(" + uuid + ")  列印總筆數:" + rows.size());
				model = new DefaultTableModel();
				model.setDataVector(rows.toArray(new Object[rows.size()][columns.length]), columns);
			}
		} catch(Exception x) {
			log._execLogError(x.getMessage());
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (Exception e) {
					//ignore
				}
			}
			db.closeAll();
			
			if (rows != null) {
				rows.clear();
				rows = null;
			}
		}

		return model;
	}


	/**
	 * 無序號資料判斷
	 * @param chkSerialno
	 * @return
	 * @deprecated
	 */
	private String checkserialno(String chkSerialno){
		String reSerialno=chkSerialno;
		if ("".endsWith(chkSerialno) || chkSerialno==null){
			reSerialno="0";
		}
		return reSerialno;
	}

	/**
	 * 複製陣列
	 * @deprecated
	 */
	private String[] copyArray(String inArray[]){
		String outArray[]=new String[18];
		for(int i=0 ;i<inArray.length ;i++){
			outArray[i]=inArray[i];
		}
		return outArray;
	}
	
	/**
	 * 己使用年限
	 * @param useDateS
	 * @return
	 * @deprecated
	 */
	private String useDate(String useDateS){
		String reuseDateS="";
		int a=0;
		int b=0;
		if(!"".equals(useDateS) && useDateS != null){
			a=(Integer.parseInt(useDateS.substring(0,3))*12)+Integer.parseInt(useDateS.substring(3,5));
			b=(Integer.parseInt(Datetime.getYYYMMDD().substring(0,3))*12)+Integer.parseInt(Datetime.getYYYMMDD().substring(3,5));
			reuseDateS=String.valueOf((b-a)/12)+"年"+String.valueOf((b-a)%12)+"個月";
			//System.out.println(useDateS.substring(0,3)+"-"+useDateS.substring(3,5)+"-"+useDateS.substring(5,7));
		}else{
			reuseDateS="";
		}
		return reuseDateS;
	}
	
	public String getFieldList(String mgrOrg, String userOrg) throws Exception {
		Database db = new Database();
		ResultSet rs = null;
		String sSQL = "";
		StringBuffer sb = new StringBuffer("");
		try {
			sSQL = "select placeno,placename from SYSCA_PLACE where enterorg = ";
			sSQL +="'"+userOrg+"'" + " order by placeno";
			/*if (!checkGet(mgrOrg).equalsIgnoreCase(checkGet(userOrg))){
				sSQL += " and isMgr='N'";
			}
			sSQL += " order by orderby " ;*/
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
	
	public void export() throws Exception{
		String strFields = "", strContext="";
		String strZhFields="";
		strEngFields="";
		String[] arrContext = null, arrField=null;
		int i = 0, j=0;
		try{
			//Get Selected Fields and Field Names
			for (i=0; i<q_sDestField.length; i++) {
				arrField = q_sDestField[i].split(":;:");			
				if (i==0) {
					strFields += arrField[0];
					strEngFields += Common.sqlChar(arrField[0]);
					strZhFields += arrField[1];
					strContext += arrField[1];
				} else {
					strFields += "," + arrField[0];
					strEngFields += "," + Common.sqlChar(arrField[0]);
					strZhFields += "," + arrField[1];
					strContext += "," + arrField[1];
				} 
				if (i==q_sDestField.length-1){
					strContext += ";;";
				}
			}
		}catch(Exception e)
		{
			System.out.println("getSignDescName Exception => " + e.getMessage());
		}
		
	}
	
}
