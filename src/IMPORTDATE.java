import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;

import org.apache.commons.lang.StringUtils;

import util.Common;
import util.Datetime;

public class IMPORTDATE {
	//==========================設定區==========================
	public static boolean insert_write=true;// 是否寫入資料庫 true=寫入 false 不寫入
											// 若為true時，會自動刪除現有資料和寫入資料
											// 若為false時，只會印出sql指令，不會刪除和寫入資料
	public static int count = 0;
	public static int rowcount = 0;

	public static String inputEnterOrg = "A27040000G";	//竹科管局
//	public static String inputEnterOrg = "A10120000U";	//竹科實中
//	public static String inputEnterOrg = "A102V0000U";	//中科實中

	//選取來源的資料庫
//	public static String fromDBip="192.168.2.139";		//來源資料庫
//	public static String fromDBuser="TCGH";				//來源資料庫帳號
//	public static String fromDBpw="TCGH";				//來源資料庫密碼

//	public static String fromDBip="127.0.0.1";			//來源資料庫
//	public static String fromDBuser="sa";				//來源資料庫帳號
//	public static String fromDBpw="1qaz2wsx";			//來源資料庫密碼

	public static String fromDBip="127.0.0.1";			//來源資料庫
	public static String fromDBuser="sa";				//來源資料庫帳號
	public static String fromDBpw="qwer1234";			//來源資料庫密碼
	
	//選取要匯入的資料庫
//	public static String toDBip="172.16.30.154";		//匯入資料庫
//	public static String toDBuser="TCGH";				//匯入資料庫帳號
//	public static String toDBpw="1qaz@WSX";				//匯入資料庫密碼

//	public static String toDBip="192.168.137.5";		//匯入資料庫
//	public static String toDBuser="TCGH";				//匯入資料庫帳號
//	public static String toDBpw="TCGH";					//匯入資料庫密碼

	public static String toDBip="127.0.0.1";			//匯入資料庫
	public static String toDBuser="sa";					//匯入資料庫帳號
	public static String toDBpw="qwer1234";				//匯入資料庫密碼

 	//==========================設定區==========================
	/**
	 * @資料匯入作業
	 */
	public static void main(String[] args) {
		System.out.println("資料匯入作業-開始");
		System.out.println("單位為："+inputEnterOrg);
		try {
			//conn = getDBConnection("MS");
			//SQLo = conn.prepareStatement(" select * from SYSCA_CODE ");
			
			//=========工程案件檔===========
//			if("A27040000G".equals(inputEnterOrg)) {
//				UNTEG_ENGINEERINGCASE("UNTEG_ENGINEERINGCASE");
//				showWhatIsEnd();
//			}
			//=========工程案件檔===========
			
			//=========動產===========
//			UNTMP_MOVABLEDETAIL("UNTMP_MOVABLEDETAIL");
//			showWhatIsEnd();
//			UNTMP_ADJUSTDETAIL("UNTMP_ADJUSTDETAIL");
//			showWhatIsEnd();
//			UNTMP_REDUCEDETAIL("UNTMP_REDUCEDETAIL");
//			showWhatIsEnd();
//			UNTMP_MOVEDETAIL("UNTMP_MOVEDETAIL");
//			showWhatIsEnd();
			//=========動產===========
			
			//=========動產折舊===========
			UNTDP_MONTHDEPR("UNTDP_MONTHDEPR");  
			showWhatIsEnd();
			//=====土改折舊=====
			UNTDP_MONTHDEPR_2("UNTDP_MONTHDEPR");  
			showWhatIsEnd();
			//=====建物折舊=====
			UNTDP_MONTHDEPR_3("UNTDP_MONTHDEPR");  
			showWhatIsEnd();
			//=========折舊===========
			
			//=======檢查不動產財編用======
//			check_ASLandBasic();
//			check_ASbldgBasic();
//			check_ASIprvBasic();
			//=======檢查不動產財編用======
			
			//======物品廠商代碼======
//			UNTMP_STORE("UNTMP_STORE");
//			showWhatIsEnd();
			//======物品廠商代碼======
			
			//======物品======
//			UNTNE_NONEXPDETAIL("UNTNE_NONEXPDETAIL");
//			showWhatIsEnd();
//			UNTNE_ADJUSTDETAIL("UNTNE_ADJUSTDETAIL");
//			showWhatIsEnd();
//			UNTNE_REDUCEDETAIL("UNTNE_REDUCEDETAIL");
//			showWhatIsEnd();
//			UNTNE_MOVEDETAIL("UNTNE_MOVEDETAIL");
//			showWhatIsEnd();
			//======物品======

			
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("資料匯入作業-結束");
	}
	
	
	/**
	 * @UNTMP_MOVABLEDETAIL
	 */
	public static void UNTMP_MOVABLEDETAIL(String tablename){
		count = 0;
		
		Connection smblconn;
		Connection tcghconn;
		
		PreparedStatement SQLo = null;
//		PreparedStatement SQLo1 = null;
		PreparedStatement SQLo2 = null;
		PreparedStatement SQLo3 = null;
		ResultSet rs,rs2,rs3;
		String insertstr="";
		String subinsert="";
		String Allinsert="";
		
		String proofno="";
		String proofyear="";
		String SqlStr="";
		String tempAssetNo="";
		
		int serialno1 = 0;
		try {
//			String tempLotNo ="";
//			String tempDocNo ="";
			smblconn = getDBConnection("smblg");
			tcghconn = getDBConnection("TCGH");
			
			if(insert_write==true){
			SQLo = tcghconn.prepareStatement("delete "+tablename+"  where enterorg='"+inputEnterOrg+"'");
			SQLo.execute();
			SQLo.close();
			SQLo = tcghconn.prepareStatement("delete UNTLA_ADDPROOF  where enterorg='"+inputEnterOrg+"' and (editid='GM' or not (caseno like '___000____' or caseno like '___001____'))");
			SQLo.execute();
			SQLo.close();
			SQLo = tcghconn.prepareStatement("delete UNTBU_ADDPROOF  where enterorg='"+inputEnterOrg+"' and (editid='GM' or not (caseno like '___000____' or caseno like '___001____'))");
			SQLo.execute();
			SQLo.close();
			SQLo = tcghconn.prepareStatement("delete UNTRF_ADDPROOF where enterorg='"+inputEnterOrg+"' and (editid='GM' or not (caseno like '___000____' or caseno like '___001____'))");
			SQLo.execute();
			SQLo.close();
			SQLo = tcghconn.prepareStatement("delete UNTMP_ADDPROOF  where enterorg='"+inputEnterOrg+"' and (editid='GM' or not (caseno like '___000____' or caseno like '___001____'))");
			SQLo.execute();
			SQLo.close();
			SQLo = tcghconn.prepareStatement("delete UNTMP_MOVABLE  where enterorg='"+inputEnterOrg+"'");
			SQLo.execute();
			SQLo.close();
			SQLo = tcghconn.prepareStatement("delete UNTMP_ATTACHMENT1  where enterorg='"+inputEnterOrg+"'");
			SQLo.execute();
			SQLo.close(); 
			}
			
			SqlStr+="select  ";
			SqlStr+="a.AssetNo,b.WorkingYears as originalLimitYear,a.WorkingYears*12 as otherLimitYear,isnull(a.acctTagID,'') as acctTagID,isnull(a.AgtNo,'') as AgtNo,isnull(c.RECDate,'') as RECDate,isnull(c.RECDivID,'') as RECDivID,isnull(a.AcctDocID,'') as AcctDocID,a.AcctDate,a.RecDate,isnull(b.note,'') as note,isnull(b.DocID,'') as DocNo, "; 
			SqlStr+="a.CategoryID,a.OffAccount,isnull(a.OffAccountDate,'') as OffAccountDate,a.Qty,convert(int,a.AmtSTTL) as AmtSTTL,convert(int,a.ValCur) as ValCur,convert(int,a.ValDprTTL) as ValDprTTL,a.PurposeName,a.KeepDivID,a.KeeperID,isnull(c.docno,'') as DocNoNote, ";
			SqlStr+="a.ASUseDivID,a.ASUserID,a.postdate,b.rcvdate,a.AssetID,a.propertyno,a.lotno,a.serialno,a.differencekind,a.LocationID,a.LocationName,b.LocationID as oldLocationID,b.LocationName as oldLocationName,b.KeepDivID,b.KeeperID,b.ASUseDivID,b.ASUserID,b.docid  ";
			SqlStr+="from ASsetBasic a  ";
			SqlStr+="left join  ASAddDetail b on a.AssetNo=b.AssetNo "; 
			SqlStr+="left join  ASAddMaster c on b.docid=c.docid ";
			SqlStr+="where a.propertyno is not null ";
//			SqlStr+=" and a.propertyno='40502030003'";
//			SqlStr+=" and a.lotno='0002515'";
			SqlStr+="order by convert(int,b.DocID),a.RecDate,a.propertyno  ";
			System.out.println(SqlStr);
			SQLo = smblconn.prepareStatement(SqlStr);
			
			insertstr+="insert into "+tablename+" ";
			insertstr+="( enterorg, ownership, caseno, differencekind, engineeringno, propertyno, lotno, serialno, datastate, reducedate, "; 
			insertstr+=" reducecause, reducecause1, verify, enterdate, originalamount, originalbv, bookamount, bookvalue, netvalue, "; 
			insertstr+=" licenseplate, purpose, originalmovedate, originalkeepunit, originalkeeper, originaluseunit, originaluser, "; 
			insertstr+=" originalusernote, originalplace1, originalplace, movedate, keepunit, keeper, useunit, userno, usernote, "; 
			insertstr+=" place1, place, originaldeprmethod, originalbuildfeecb, originaldeprunitcb, originaldeprpark, originaldeprunit, "; 
			insertstr+=" originaldeprunit1, originaldepraccounts, originalscrapvalue, originaldepramount, originalaccumdepr, "; 
			insertstr+=" originalapportionmonth, originalmonthdepr, originalmonthdepr1, originalapportionendym, deprmethod, buildfeecb, "; 
			insertstr+=" deprunitcb, deprpark, deprunit, deprunit1, depraccounts, scrapvalue, depramount, accumdepr, apportionmonth, "; 
			insertstr+=" monthdepr, monthdepr1, apportionendym, nodeprset, notes1, barcode, oldpropertyno, oldserialno, notes,originallimityear,otherlimityear, editid, "; 
			insertstr+=" editdate, edittime, oldlotno ) ";			
			insertstr+=" values ( ";
			rs = SQLo.executeQuery();
			while(rs.next())
			{
				//增加單單號ID
				proofno="";
				proofyear="";
				String caseno="";
				String proofdoc="";
			
				//proofno=Common.formatFrontString(Common.get(rs.getString("Docid")), 7, '0');
				proofno=Common.get(rs.getString("Docid"));
				proofyear=changeTaiwanYYMMDD(Common.get(rs.getDate("RECDate").toString()).replaceAll("-", ""),"1").substring(0,3);
				
				
				SQLo2 = tcghconn.prepareStatement("select caseno from UNTLA_ADDPROOF where proofyear='"+proofyear+"' and proofno='"+proofno+"' and enterorg='" + inputEnterOrg + "' and editid='GM' ");				
				rs2 = SQLo2.executeQuery();
				if(rs2.next()) {//查詢單號是存在
					
					caseno=rs2.getString("caseno");
					if(!rs.getString("AssetNo").equals(tempAssetNo)){//此為同樣單號但是會有多筆基本資料，所以要新增一筆
						
						UNTMP_MOVABLE("UNTMP_MOVABLE",caseno,rs.getString("AssetNo"),smblconn,tcghconn);serialno1 = 0;
						serialno1 = UNTMP_ATTACHMENT1(rs ,caseno,serialno1);
					}
				} else {		//不存在新建一個增加單單號
					
					SQLo3 = tcghconn.prepareStatement("select right(isnull(max(caseno)+1,'20000'),7) as caseno from UNTLA_ADDPROOF where proofyear='"+proofyear+"' and enterorg='" + inputEnterOrg + "' and editid='GM' ");				
					rs3 = SQLo3.executeQuery();
					if(rs3.next()) {
						caseno=changeTaiwanYYMMDD(Common.get(rs.getDate("RECDate").toString()).replaceAll("-", ""),"1").substring(0,3)+Common.formatFrontString(Common.get(rs3.getString("caseno")), 7, '0');
					}
					SQLo3.close();
					rs3.close();
					proofdoc=Common.get(rs.getString("DocNoNote"));
					UNTMP_MOVABLE("UNTMP_MOVABLE",caseno,rs.getString("AssetNo"),smblconn,tcghconn);serialno1 = 0;
					serialno1 = UNTMP_ATTACHMENT1(rs ,caseno,serialno1);
					INSERT_ADDPROOF(tcghconn,"UNTLA_ADDPROOF", caseno, proofdoc, proofno, Common.get(rs.getString("acctTagID")), Common.get(rs.getString("AgtNo")), Common.get(rs.getDate("RECDate").toString()), Common.get(rs.getString("RECDivID").toString()), Common.get(rs.getDate("PostDate").toString()), Common.get(rs.getString("AcctDocID").toString()), Common.get(rs.getDate("AcctDate").toString()), Common.get(rs.getString("DocNoNote").toString()),Common.get(rs.getString("differencekind")),Common.get(rs.getString("DocID")));
					INSERT_ADDPROOF(tcghconn,"UNTBU_ADDPROOF", caseno, proofdoc, proofno, Common.get(rs.getString("acctTagID")), Common.get(rs.getString("AgtNo")), Common.get(rs.getDate("RECDate").toString()), Common.get(rs.getString("RECDivID").toString()), Common.get(rs.getDate("PostDate").toString()), Common.get(rs.getString("AcctDocID").toString()), Common.get(rs.getDate("AcctDate").toString()), Common.get(rs.getString("DocNoNote").toString()),Common.get(rs.getString("differencekind")),Common.get(rs.getString("DocID")));
					INSERT_ADDPROOF(tcghconn,"UNTRF_ADDPROOF", caseno, proofdoc, proofno, Common.get(rs.getString("acctTagID")), Common.get(rs.getString("AgtNo")), Common.get(rs.getDate("RECDate").toString()), Common.get(rs.getString("RECDivID").toString()), Common.get(rs.getDate("PostDate").toString()), Common.get(rs.getString("AcctDocID").toString()), Common.get(rs.getDate("AcctDate").toString()), Common.get(rs.getString("DocNoNote").toString()),Common.get(rs.getString("differencekind")),Common.get(rs.getString("DocID")));
					INSERT_ADDPROOF(tcghconn,"UNTMP_ADDPROOF", caseno, proofdoc, proofno, Common.get(rs.getString("acctTagID")), Common.get(rs.getString("AgtNo")), Common.get(rs.getDate("RECDate").toString()), Common.get(rs.getString("RECDivID").toString()), Common.get(rs.getDate("PostDate").toString()), Common.get(rs.getString("AcctDocID").toString()), Common.get(rs.getDate("AcctDate").toString()), Common.get(rs.getString("DocNoNote").toString()),Common.get(rs.getString("differencekind")),Common.get(rs.getString("DocID")));
					
				}
				SQLo2.close();
				rs2.close();
				//增加單單號ID
				tempAssetNo=rs.getString("AssetNo");
				subinsert="";
				subinsert+="'" + inputEnterOrg + "',";//enterorg
				subinsert+="'1',";//ownership				
				subinsert+="'"+caseno+"',";//caseno			
				//財產區分別
				subinsert+="'"+Common.get(rs.getString("differencekind"))+"',";//differencekind
				if("A27040000G".equals(inputEnterOrg)) {
					subinsert+="'"+getEngineeringnoAdd("Chtl", rs.getString("DocID"))+"',";//engineeringno
				} else {
					subinsert+="'"+Common.get(rs.getString("AgtNo"))+"',";//engineeringno
				}
				String propertyno=Common.get(rs.getString("propertyno"));
				subinsert+="'"+propertyno+"',"; //propertyno
				
				String lotno=Common.get(rs.getString("lotno"));
				subinsert+="'"+lotno+"',"; //lotno
				
				String serialno=Common.get(rs.getString("serialno"));
				subinsert+="'"+serialno+"',"; //serialno

				String datastate="";
				if (Common.get(rs.getString("OffAccount")).equals("1")){ //減損
					datastate="2";
				}else{
					datastate="1";
				}
				subinsert+="'"+datastate+"',"; //datastate
				String reducedate="";
				if ("1".equals(datastate)) {
					reducedate = "";
				} else if(Common.get(rs.getString("OffAccountDate")).length()>0){
					reducedate=Common.get(rs.getString("OffAccountDate")).replaceAll("-", "").substring(0,8);
				}
				subinsert+="'"+reducedate+"',"; //reducedate
				subinsert+="'',"; //reducecause
				subinsert+="'',"; //reducecause1				
				//if(Common.get(rs.getString("verify")).equals("N")){
				//	subinsert+="'N',";//verify
				//	}else{
					subinsert+="'Y',";//verify	
				//	}
				String enterdate="";
				if(Common.get(rs.getString("postdate")).length()>0){
					enterdate=Common.get(rs.getString("postdate")).replaceAll("-", "").substring(0,8);
				}				
				subinsert+="'"+enterdate+"',"; //enterdate
								
				String originalamount="";
				originalamount=Common.get(rs.getString("Qty"));
				subinsert+=""+originalamount+","; //originalamount
				
				String originalbv="";
				originalbv=Common.get(rs.getString("AmtSTTL"));				
				subinsert+=""+originalbv+","; //originalbv
				
				String bookamount="";
				if (Common.get(rs.getString("OffAccount")).equals("1")){  //減損
					bookamount="0";
				}else{
					bookamount=Common.get(rs.getString("Qty"));
				}
				subinsert+=""+bookamount+","; //bookamount
				String bookvalue="";
				if (Common.get(rs.getString("OffAccount")).equals("1")){  //減損
					bookvalue="0";
				}else{
					bookvalue=Common.get(rs.getString("ValCur"));
				}				
				subinsert+=""+bookvalue+","; //bookvalue
				
				String netvalue="";
				if (Common.get(rs.getString("OffAccount")).equals("1")){  //減損
					netvalue="0";
				}else{
					//System.out.println(Common.get(rs.getString("AmtSTTL")));
					//System.out.println(Common.get(rs.getString("ValDprTTL")));
					netvalue=Integer.toString(Integer.valueOf(Common.get(rs.getString("ValCur")))-Integer.valueOf(Common.get(rs.getString("ValDprTTL"))));
				}
				
				
				subinsert+=""+netvalue+","; //netvalue
				
				
				subinsert+="'',"; //licenseplate
				String purpose="";
				purpose=Common.get(rs.getString("PurposeName"));
				subinsert+="'"+purpose+"',"; //purpose
				
				String originalmovedate="";
				originalmovedate=Common.get(rs.getString("RecDate")).replaceAll("-", "").substring(0,8);
				subinsert+="'"+originalmovedate+"',"; //originalmovedate

				String originalkeepunit="";
				originalkeepunit=Common.get(rs.getString("KeepDivID"));
				subinsert+="'"+originalkeepunit+"',"; //originalkeepunit
				
				String originalkeeper="";
				originalkeeper=transKeeperNo(Common.get(rs.getString("KeeperID")));
				subinsert+="'"+originalkeeper+"',"; //originalkeeper
				
				String originaluseunit="";
				originaluseunit=Common.get(rs.getString("ASUseDivID"));
				subinsert+="'"+originaluseunit+"',"; //originaluseunit
				
				String originaluser="";
				originaluser=transKeeperNo(Common.get(rs.getString("ASUserID")));
				subinsert+="'"+originaluser+"',"; //originaluser
				
				subinsert+="'',"; //originalusernote
				subinsert+="'"+getPlaceno(Common.get(rs.getString("oldLocationID")), Common.get(rs.getString("oldLocationName")))+"',"; //originalplace1
				subinsert+="'"+Common.get(rs.getString("oldLocationName"))+"',"; //originalplace
				subinsert+="'',"; //movedate
				subinsert+="'"+Common.get(rs.getString("KeepDivID"))+"',"; //keepunit
				subinsert+="'"+transKeeperNo(Common.get(rs.getString("KeeperID")))+"',"; //keeper
				subinsert+="'"+Common.get(rs.getString("ASUseDivID"))+"',"; //useunit
				subinsert+="'"+transKeeperNo(Common.get(rs.getString("ASUserID")))+"',"; //userno
				subinsert+="'',"; //usernote
				subinsert+="'"+getPlaceno(Common.get(rs.getString("LocationID")), Common.get(rs.getString("LocationName")))+"',";//place1,
				subinsert+="'"+Common.get(rs.getString("LocationName"))+"',";//place,
				subinsert+=Common.sqlChar(getDeprMethod(rs.getString("acctTagID"))) + ",";//originaldeprmethod
				subinsert+="'',"; //originalbuildfeecb
				subinsert+="'',"; //originaldeprunitcb
				subinsert+="'',"; //originaldeprpark
				subinsert+="'',"; //originaldeprunit
				subinsert+="'',"; //originaldeprunit1
				subinsert+="'',"; //originaldepraccounts
				subinsert+="null,"; //originalscrapvalue
				subinsert+="null,"; //originaldepramount
				subinsert+="null,"; //originalaccumdepr
				subinsert+="null,"; //originalapportionmonth
				subinsert+="null,"; //originalmonthdepr
				subinsert+="null,"; //originalmonthdepr1
				subinsert+="'',"; //originalapportionendym
				subinsert+=Common.sqlChar(getDeprMethod(rs.getString("acctTagID"))) + ",";//deprmethod	
				subinsert+="'',"; //buildfeecb
				subinsert+="'',"; //deprunitcb
				subinsert+="'',"; //deprpark
				subinsert+="'',"; //deprunit
				subinsert+="'',"; //deprunit1
				subinsert+="'',"; //depraccounts
				subinsert+="null,"; //scrapvalue
				subinsert+="null,"; //depramount
				subinsert+="null,"; //accumdepr
				subinsert+="null,"; //apportionmonth
				subinsert+="null,"; //monthdepr
				subinsert+="null,"; //monthdepr1
				subinsert+="'',"; //apportionendym
				if("A27040000G".equals(inputEnterOrg)) {
					subinsert+=Common.sqlChar(getNoDeprset(rs.getString("AssetID"))) + ",";; //nodeprset
				} else {
					subinsert+="'N',"; //nodeprset
				}   
				subinsert+="'',"; //notes1
				String barcode="";
				//※	條碼編碼規則：由「財產區分別-財產編號-財產分號」
				barcode=Common.get(rs.getString("differencekind"))+"-"+propertyno+"-"+serialno;
				subinsert+="'"+barcode+"',"; //barcode
				
				String oldpropertyno="";
				oldpropertyno=Common.get(rs.getString("CategoryID"));
				subinsert+="'"+oldpropertyno+"',"; //oldpropertyno
				
				String oldserialno="";
				oldserialno=Common.get(rs.getString("AssetID"));
				//if(Common.get(rs.getString("verify")).equals("N")){
				//	subinsert+="'',"; //oldserialno
				//}else{
					subinsert+="'"+oldserialno+"',"; //oldserialno	
				//}
								
				subinsert+="'"+Common.get(rs.getString("note"))+"',";//notes
				subinsert+="'"+Common.get(rs.getString("originalLimitYear"))+"',";//originallimityear
				subinsert+="'"+Common.get(rs.getString("otherLimitYear"))+"',";//otherlimityear
				subinsert+="'GM',";//editid
				subinsert+="'"+Datetime.getYYYYMMDD()+"',";//editdate
				subinsert+="'"+Datetime.getHHMMSS()+"',";//edittime
				subinsert+="'"+Common.get(rs.getString("AssetNo"))+"' ";//oldlotno
				
				Allinsert=insertstr+subinsert+") ;";
				//System.out.println(Allinsert);
				//if(!proofno.equals("")){
					try{
						if(insert_write==true){
							SQLo = tcghconn.prepareStatement(Allinsert);
							SQLo.execute();
							SQLo.close();
						}else{
							System.out.println(Allinsert);
						}
					}catch(Exception e){
						System.out.println(e);
						System.out.println(Allinsert);
					}
				//}
					showWhatIsNow();
			}
			UPDATE_UNTMP_MOVABLE_serialnoSE(tcghconn);//修改動產基本資料的的流水號起訖
			SQLo.close();
			rs.close();
			smblconn.close();			
			tcghconn.close();
		} catch (Exception e) {
			System.out.println(Allinsert);
			e.printStackTrace();
		}

	}
	
	
	/**
	 * @UNTMP_MOVABLEDETAIL
	 */
	public static void UNTEG_ENGINEERINGCASE(String tablename){
		count = 0;
		
		Connection smblconn;
		Connection tcghconn;
		
		PreparedStatement SQLo = null;
		ResultSet rs;
		String insertstr="";
		String subinsert="";
		String Allinsert="";

		String SqlStr="";

		try {

			smblconn = getDBConnection("smblg");
			tcghconn = getDBConnection("TCGH");
			
			if(insert_write==true){
				SQLo = tcghconn.prepareStatement("delete "+tablename+"  where enterorg='"+inputEnterOrg+"'");
				SQLo.execute();
				SQLo.close();
			}
			
			SqlStr+="SELECT * FROM ZKProjectJob ";
			System.out.println(SqlStr);
			SQLo = smblconn.prepareStatement(SqlStr);
			
			insertstr+="insert into "+tablename+" ";
			insertstr+="( enterorg,engineeringno,engineeringname,createdate,notes,editid,editdate,edittime ) ";			
			insertstr+=" values ( ";
			rs = SQLo.executeQuery();
			while(rs.next())
			{
				subinsert="";
				subinsert+="'" + inputEnterOrg + "',";//enterorg
				subinsert+="'"+Common.get(rs.getString("ProjectID"))+"',";//engineeringno
				subinsert+="'"+Common.get(rs.getString("ProjectName"))+"',";//engineeringname
				subinsert+="'"+changeDateFormat(rs.getDate("RecDate"))+"',";//createdate
				subinsert+="null,";//notes
				subinsert+="'GM',";//editid
				subinsert+="'"+Datetime.getYYYYMMDD()+"',";//editdate
				subinsert+="'"+Datetime.getHHMMSS()+"'";//edittime
				Allinsert=insertstr+subinsert+") ;";
				try{
					if(insert_write==true){
						SQLo = tcghconn.prepareStatement(Allinsert);
						SQLo.execute();
						SQLo.close();
					}else{
						System.out.println(Allinsert);
					}
				}catch(Exception e){
					System.out.println(e);
					System.out.println(Allinsert);
				}
				showWhatIsNow();
			}
			SQLo.close();
			rs.close();
			smblconn.close();			
			tcghconn.close();
		} catch (Exception e) {
			System.out.println(Allinsert);
			e.printStackTrace();
		}
	}
	
	public static int UNTMP_ATTACHMENT1(ResultSet rs ,String caseno,int serialno1) throws Exception{
		Connection smblconn = null;
		Connection tcghconn = null;
		PreparedStatement SQLo1 = null;
		PreparedStatement SQLo2 = null;
		ResultSet rs1 = null;
		ResultSet rs2 = null;
		String insertstr="";
		String subinsert="";
		String Allinsert="";
		String updatestr="";
		insertstr="insert into UNTMP_ATTACHMENT1( " + "\n";
		insertstr+="enterorg," + "\n";
		insertstr+="ownership," + "\n";
		insertstr+="differencekind," + "\n";
		insertstr+="propertyno," + "\n";
		insertstr+="lotno," + "\n";
		insertstr+="serialno1," + "\n";
		insertstr+="equipmentname," + "\n";
		insertstr+="buydate," + "\n";
		insertstr+="equipmentunit," + "\n";
		insertstr+="equipmentamount," + "\n";
		insertstr+="unitprice," + "\n";
		insertstr+="totalvalue," + "\n";
		insertstr+="sourcekind," + "\n";
		insertstr+="nameplate," + "\n";
		insertstr+="specification," + "\n";
		insertstr+="notes," + "\n";
		insertstr+="editid," + "\n";
		insertstr+="editdate," + "\n";
		insertstr+="edittime) values (" + "\n";
		try {
			smblconn = getDBConnection("smblg");
			tcghconn = getDBConnection("TCGH");
			String SqlStrforUNTMP_MOVABLE = " select * from UNTMP_MOVABLE where enterorg = '" + inputEnterOrg + "' and caseno = '" + Common.get(caseno) + "'";
			String SqlStrforASAddDetailAcce=" select * from ASAddDetailAcce where AssetNo = '" + Common.get(rs.getString("AssetNo")) + "'";
			SQLo1 = smblconn.prepareStatement(SqlStrforASAddDetailAcce);
			rs1 = SQLo1.executeQuery();
			SQLo2 = tcghconn.prepareStatement(SqlStrforUNTMP_MOVABLE);
			rs2 = SQLo2.executeQuery();
			String propertyno=Common.get(rs.getString("propertyno"));
			if (rs2.next()) {
				while(rs1.next()) {
					subinsert = "";
					subinsert += "'" + inputEnterOrg + "',";//enterorg
					subinsert += "'1',";//ownership				
					subinsert += "'"+Common.get(rs.getString("differencekind"))+"',";//differencekind
					subinsert += "'"+propertyno+"',";//propertyno
					subinsert += "'"+Common.get(rs.getString("lotno"))+"',"; //lotno
					subinsert += "'" + ++serialno1 + "',"; //serialno1
					subinsert += "'"+Common.get(rs1.getString("AccName"))+"',"; //equipmentname
					subinsert += "'"+Common.get(rs2.getString("buydate"))+"',"; //buydate
					subinsert += "'',"; //equipmentunit 單位
					subinsert += "'"+Common.get(rs1.getInt("Qty"))+"',"; //equipmentamount 數量
					subinsert += "'"+Common.get(rs1.getInt("AccPrice")/rs1.getInt("Qty"))+"',"; //unitprice 單價
					subinsert += "'"+Common.get(rs1.getInt("AccPrice"))+"',"; //totalvalue 總價
					subinsert += "'"+Common.get(rs2.getString("sourcekind"))+"',"; //sourcekind 來源
					subinsert += "'"+Common.get(rs2.getString("nameplate"))+"',"; //nameplate 廠牌
					subinsert += "'"+Common.get(rs2.getString("specification"))+"',"; //specification 型式
					subinsert += "'',"; //notes 備註
					subinsert+="'GM',";//editid
					subinsert+="'"+Datetime.getYYYYMMDD()+"',";//editdate
					subinsert+="'"+Datetime.getHHMMSS()+"'";//edittime
					Allinsert=insertstr+subinsert+") ;";

					try{
						if(insert_write==true){
							SQLo1 = tcghconn.prepareStatement(Allinsert);
							SQLo1.execute();
							SQLo1.close();
						}else{
							System.out.println(Allinsert);
						}
//					}catch(SQLException ex) {
//						updatestr += " update UNTMP_ATTACHMENT1 set ";
//						updatestr += " equipmentamount = equipmentamount + '"+Common.get(rs1.getInt("Qty"))+"',";
//						updatestr += " totalvalue = totalvalue + '"+Common.get(rs1.getInt("AccPrice"))+"'";
//						updatestr += " where 1=1";
//						updatestr += " and enterorg = '" + inputEnterOrg + "',";//enterorg
//						updatestr += " and ownership = '" + inputEnterOrg + "',";//enterorg
//						updatestr += " and differencekind = '" + inputEnterOrg + "',";//enterorg
//						updatestr += " and propertyno = '" + inputEnterOrg + "',";//enterorg
//						updatestr += " and enterorg = '" + inputEnterOrg + "',";//enterorg
//						
					}catch(Exception e){
						System.out.println(e);
						System.out.println(Allinsert);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs1 != null) {
					rs1.close();
				}
				if (rs2 != null) {
					rs2.close();
				}
				if (smblconn != null) {
					smblconn.close();
				}
				if (tcghconn != null) {
					tcghconn.close();
				}
				if (SQLo1 != null) {
					SQLo1.close();
				}
				if (SQLo2 != null) {
					SQLo2.close();
				}
			} catch (Exception ex) {
				throw ex;
			}
		}
		return serialno1;
	}
	
	/**
	 * @UNTMP_MOVABLE
	 */
	public static void UNTMP_MOVABLE(String tablename,String caseno,String AssetNo,Connection smblconn,Connection tcghconn){
		
		
		//Connection smblconn;
		//Connection tcghconn;
		
		PreparedStatement SQLo = null;
		PreparedStatement SQLo2 = null;
		ResultSet rs,rs2;
		String insertstr="";
		String subinsert="";
		String Allinsert="";
		String caseserialno="";
		String proofno="";
		String proofyear="";
		String SqlStr="";
		try {
			
			//smblconn = getDBConnection("smblg");
			//tcghconn = getDBConnection("TCGH");
			
			
			
			SqlStr+="select   ";
			SqlStr+="convert(int,b.Price) as Price,convert(int,b.AmtSTTL) as AmtSTTL,b.WorkingYears as originalLimitYear,a.WorkingYears*12 as otherLimitYear,isnull(a.acctTagID,'') as acctTagID,isnull(a.AgtNo,'') as AgtNo,c.RecDate,b.note,isnull(b.DocID,'') as DocNo, "; 
			SqlStr+="a.CategoryID,b.Qty,sum(convert(int,a.ValDprTTL)) as ValDprTTL,a.postdate,b.buydate,b.rcvdate,b.AssetName,b.ExpName,b.AcctName,a.differencekind, ";
			SqlStr+="a.propertyno,b.DocID,max(a.serialno) as serialno_max,min(a.serialno) as serialno_min,  ";
			
			SqlStr+="max(a.lotno) as lotno,max(b.brand) as brand,max(b.spec) as spec, b.RefType,";
			
			SqlStr+="max(a.assetid) as assetid_max,min(a.assetid) as assetid_min,a.assetno,b.unit,b.matter,a.expname   ";
			SqlStr+="from ASsetBasic a  ";
			//if(!verify.equals("")){
			//SqlStr+="left join  ASAddDetail b on a.AssetNo=b.AssetNo and a.AssetID=b.newAssetID ";	
			//}
			//else{
			SqlStr+="left join  ASAddDetail b on a.AssetNo=b.AssetNo "; 
//			SqlStr+="left join  ASAddDetail b on a.AssetNo=b.AssetNo and a.AssetID=b.AssetID "; 
			//}
			SqlStr+="left join  ASAddMaster c on b.docid=c.docid ";
			SqlStr+="where b.AssetNo='"+AssetNo+"' ";
			SqlStr+=" and a.propertyno is not null ";
			//SqlStr+="where (b.DocID is not null  or b.DocID='') "; 			
			//SqlStr+="where 1=1 and c.RecDate >= '2014-02-01 00:00:00.000'";
			
			SqlStr+="group by  a.acctTagID,isnull(a.AgtNo,''),c.RecDate,b.note,b.DocID, ";  
			SqlStr+=" a.CategoryID,b.Qty,a.differencekind, ";
			SqlStr+=" b.WorkingYears,a.WorkingYears,a.postdate,b.buydate,b.rcvdate,b.AssetName,b.ExpName,b.AcctName, ";
			SqlStr+=" a.propertyno,b.DocID,b.Price,b.AmtSTTL,b.RefType,a.assetno,b.unit,b.matter,a.expname  ";
			 
			SqlStr+=" order by convert(int,b.DocID),c.RecDate,a.propertyno ";
			if(insert_write!=true){
			System.out.println(SqlStr);
			}
			SQLo = smblconn.prepareStatement(SqlStr);
			
			insertstr+="insert into "+tablename+" ";
			insertstr+="( enterorg, ownership, caseno, differencekind, engineeringno, caseserialno, propertyno, lotno, serialnos, serialnoe,";
			insertstr+=" otherpropertyunit, othermaterial,originallimityear, otherlimityear, propertyname1, cause, cause1, approvedate, approvedoc, enterdate,";
			insertstr+=" buydate, datastate, verify, propertykind, fundtype, valuable, originalamount, originalunit, originalbv, originalnote,";
			insertstr+=" accountingtitle, bookamount, bookvalue, netvalue, fundssource, fundssource1, grantvalue, articlename, nameplate, ";
			insertstr+=" specification, storeno, sourcekind, sourcedate, sourcedoc, originaldeprmethod, originalbuildfeecb, originaldeprunitcb,";
			insertstr+=" originaldeprpark, originaldeprunit, originaldeprunit1, originaldepraccounts, originalscrapvalue, originaldepramount, ";
			insertstr+=" originalaccumdepr, originalapportionmonth, originalmonthdepr, originalmonthdepr1, originalapportionendym, escroworivalue, ";
			insertstr+=" escroworiaccumdepr, oldpropertyno, oldserialnos, oldserialnoe, notes, editid, editdate, edittime, picture, oldlotno ) ";			
			insertstr+=" values ( ";
			rs = SQLo.executeQuery();
			int cnt = 0;
			while(rs.next())
			{
				cnt++;
				if (cnt > 1) {
					System.out.println("cnt=" + cnt);
				}
				//增加單單號ID
				proofno="";
				proofyear="";
				proofno=Common.formatFrontString(Common.get(rs.getString("DocNo")), 7, '0');
				proofyear=changeTaiwanYYMMDD(Common.get(rs.getDate("RecDate").toString()).replaceAll("-", ""),"1").substring(0,3);

				
		
				String proofdoc="";
				//enterorg (ASC), ownership (ASC), differencekind (ASC), propertyno (ASC), lotno (ASC)
				
				SQLo2 = tcghconn.prepareStatement("select isnull(max(caseserialno)+1,'1') as caseserialno  from UNTMP_MOVABLE where caseno='"+caseno+"' and enterorg='" + inputEnterOrg + "' ");				
				rs2 = SQLo2.executeQuery();
							
				//if(rs2.next()) //查詢單號是存在
				//{
				//	caseno=Common.get(rs2.getString("caseno").toString());
				//}else{         //不存在新建一個增加單單號
				if(rs2.next()){
					caseserialno=rs2.getString("caseserialno");
				}
				SQLo2.close();
				rs2.close();
				proofdoc="";
				subinsert="";
				subinsert+="'" + inputEnterOrg + "',";//enterorg
				subinsert+="'1',";//ownership
				//System.out.println("@@@@@@@@@==>"+caseno);
				subinsert+="'"+caseno+"',";//caseno			
				//財產區分別
				subinsert+="'"+Common.get(rs.getString("differencekind"))+"',";//differencekind
				if("A27040000G".equals(inputEnterOrg)) {
					subinsert+="'"+getEngineeringnoAdd("Chtl", rs.getString("DocID"))+"',";//engineeringno
				} else {
					subinsert+="'"+Common.get(rs.getString("AgtNo"))+"',";//engineeringno
				}
				subinsert+="'"+caseserialno+"',";//caseserialno
				
				String propertyno=Common.get(rs.getString("propertyno"));
				subinsert+="'"+propertyno+"',"; //propertyno
				
				
//				String lotno=Common.get(rs.getString("serialno_min"));
				String lotno=Common.get(rs.getString("lotno"));
				subinsert+="'"+lotno+"',"; //lotno
				
				String serialnos=Common.get(rs.getString("serialno_min"));			
				subinsert+="'"+serialnos+"',"; //serialnos
				String serialnoe=Common.get(rs.getString("serialno_max"));				
				subinsert+="'"+serialnoe+"',"; //serialnoe
				
				subinsert+="'"+Common.get(rs.getString("unit"))+"',";//otherpropertyunit
				subinsert+="'"+Common.get(rs.getString("matter"))+"',";//othermaterial
				subinsert+="'"+Common.get(rs.getString("originalLimitYear"))+"',";//originallimityear
				subinsert+="'"+Common.get(rs.getString("otherLimitYear"))+"',";//otherlimityear
				subinsert+="N'"+Common.get(rs.getString("AssetName"))+"',";//propertyname1,
				subinsert+="'',";//cause,
				subinsert+="'',";//cause1,
				subinsert+="'',";//approvedate,
				subinsert+="'',";//approvedoc,
				String enterdate="";
				if(Common.get(rs.getString("postdate")).length()>0){
					enterdate=Common.get(rs.getString("postdate")).replaceAll("-", "").substring(0,8);
				}				
				subinsert+="'"+enterdate+"',"; //enterdate
				
				String buydate="";
				if(Common.get(rs.getString("buydate")).length()>0){
					buydate=Common.get(rs.getString("buydate")).replaceAll("-", "").substring(0,8);
				}				
				subinsert+="'"+buydate+"',"; //buydate
				
				
				
				
				String datastate="1";
//				if (Common.get(rs.getString("OffAccount")).equals("1")){ //減損
//					datastate="2";
//				}else{
//					datastate="1";
//				}
				subinsert+="'"+datastate+"',"; //datastate				
				subinsert+="'Y',";//verify
				
				subinsert+="'01',";//propertykind
				subinsert+="'',";//fundtype,
				
				String valuable = "";
				if("A27040000G".equals(inputEnterOrg) && Common.get(rs.getString("acctTagID")).equals("4")){
					valuable ="Y";//valuable,
				} else {
					valuable ="N";//valuable,	
				}
				subinsert+="'"+valuable+"',";//valuable,
								
				String originalamount="";
				originalamount=Common.get(rs.getString("Qty"));
				subinsert+=""+originalamount+","; //originalamount
				
				String originalunit="";
				originalunit=Common.get(rs.getString("Price"));				
				subinsert+=""+originalunit+",";//originalunit				
				
				String originalbv="";
				originalbv=Common.get(rs.getString("AmtSTTL"));				
				subinsert+=""+originalbv+","; //originalbv
				
				subinsert+="'',";//originalnote				
				subinsert+="'"+Common.get(rs.getString("AcctName"))+"',";;//accountingtitle
				
				String bookamount="";
//				if (Common.get(rs.getString("OffAccount")).equals("1")){  //減損
//					bookamount="0";
//				}else{
					bookamount=Common.get(rs.getString("Qty"));
//				}
				subinsert+=""+bookamount+","; //bookamount
				String bookvalue="";
//				if (Common.get(rs.getString("OffAccount")).equals("1")){  //減損
//					bookvalue="0";
//				}else{
					bookvalue=Common.get(rs.getString("AmtSTTL"));
//				}				
				subinsert+=""+bookvalue+","; //bookvalue
				
				String netvalue="";
//				if (Common.get(rs.getString("OffAccount")).equals("1")){  //減損
//					netvalue="0";
//				}else{					
					netvalue=Integer.toString(Integer.valueOf(Common.get(rs.getString("AmtSTTL")))-Integer.valueOf(Common.get(rs.getString("ValDprTTL"))));
//				}
				
				
				subinsert+=""+netvalue+","; //netvalue
				
				//竹科管沒有經費來源
				if("A27040000G".equals(inputEnterOrg)) {
					subinsert+="'',";//fundssource,
					subinsert+="'',";//fundssource1,
				}else{
					subinsert+="'"+getSYSCA_CODENAME(tcghconn,Common.get(rs.getString("expname")),"FSO","03")+"',";//fundssource,
					subinsert+="'"+Common.get(rs.getString("expname"))+"',";//fundssource1,
				}

				
				subinsert+="null,";//grantvalue,
				subinsert+="'"+Common.get(rs.getString("AssetName"))+"',";//articlename,
				subinsert+="'"+Common.get(rs.getString("brand"))+"',";//nameplate,
				subinsert+="'"+Common.get(rs.getString("spec")).replace("\'", "")+"',";//specification,
				subinsert+="'',";//storeno,
				
				SQLo2 = tcghconn.prepareStatement("select codeid from SYSCA_CODE where codekindid = 'SKD' AND codename = '" + Common.get(rs.getString("RefType")) + "'");
				rs2 = SQLo2.executeQuery();
				if(rs2.next()){
					subinsert+="'"+Common.get(rs2.getString("codeid"))+"',";//sourcekind,
				} else {
					subinsert+="'99',";//sourcekind,
				}
				//subinsert+="'"+Common.get(rs.getString("expno"))+"',";//sourcekind,
				String sourcedate="";
				if(Common.get(rs.getString("rcvdate")).length()>0){
					sourcedate=Common.get(rs.getString("rcvdate")).replaceAll("-", "").substring(0,8);
				}				
				subinsert+="'"+sourcedate+"',"; //sourcedate,
				subinsert+="'',";//sourcedoc,
				subinsert+=Common.sqlChar(getDeprMethod(rs.getString("acctTagID"))) + ",";//originaldeprmethod	
				subinsert+="'',";//originalbuildfeecb,
				subinsert+="'',";//originaldeprunitcb,
				subinsert+="'',";//originaldeprpark,
				subinsert+="'',";//originaldeprunit,
				subinsert+="'',";//originaldeprunit1,
				subinsert+="'',";//originaldepraccounts,
				
				subinsert+="null,";//originalscrapvalue,
				subinsert+="null,";//originaldepramount,
				subinsert+="null,";//originalaccumdepr,
				subinsert+="null,";//originalapportionmonth,
				subinsert+="null,";//originalmonthdepr,
				subinsert+="null,";//originalmonthdepr1,
				subinsert+="null,";//originalapportionendym,
				
				
				subinsert+="null,";//escroworivalue,
				subinsert+="null,";//escroworiaccumdepr,
				
				
				String oldpropertyno="";
				oldpropertyno=Common.get(rs.getString("CategoryID"));
				subinsert+="'"+oldpropertyno+"',"; //oldpropertyno
				
				String oldserialnos="";
				oldserialnos=Common.get(rs.getString("assetid_min"));
				//if(!verify.equals("")){
				//	subinsert+="'',"; //oldserialnos
				//}else{
					subinsert+="'"+oldserialnos+"',"; //oldserialnos	
				//}
				
				
				String oldserialnoe="";
				oldserialnoe=Common.get(rs.getString("assetid_max"));
				//if(!verify.equals("")){
				//	subinsert+="'',"; //oldserialnoe
				//}else{
					subinsert+="'"+oldserialnoe+"',"; //oldserialnoe	
				//}
								
				subinsert+="'"+Common.get(rs.getString("note"))+"',";//notes
				subinsert+="'GM',";//editid
				subinsert+="'"+Datetime.getYYYYMMDD()+"',";//editdate
				subinsert+="'"+Datetime.getHHMMSS()+"', ";//edittime
				subinsert+="'', ";//picture
				subinsert+="'"+Common.get(rs.getString("AssetNo"))+"' ";//oldlotno
				
				Allinsert=insertstr+subinsert+") ;";
				//System.out.println(Allinsert);
				//if(!proofno.equals("")){
					try{
						if(insert_write==true){
							SQLo = tcghconn.prepareStatement(Allinsert);
							SQLo.execute();
							SQLo.close();
						}else{
							System.out.println(Allinsert);
						}
					}catch(Exception e){
						System.out.println(e);
						System.out.println(Allinsert);
					}
				//}
				
				}
				//單單號ID
			//	SQLo2.close();
			//	rs2.close();
				
				showWhatIsNow();
			//}

			SQLo.close();
			rs.close();
			//smblconn.close();			
			//tcghconn.close();
		} catch (Exception e) {
			System.out.println(Allinsert);
			e.printStackTrace();
		}

	}
	
	
	
	/**
	 * @UNTMP_ADJUSTDETAIL
	 */
	public static void UNTMP_ADJUSTDETAIL(String tablename){
		count = 0;
		
		Connection smblconn;
		Connection tcghconn;
		
		PreparedStatement SQLo = null;
		PreparedStatement SQLo1 = null;
		PreparedStatement SQLo2 = null;
		PreparedStatement SQLo3 = null;
		ResultSet rs,rs2,rs3;
		String insertstr="";
		String subinsert="";
		String Allinsert="";
		
		String proofno="";
		String proofyear="";
		String SqlStr="";
		try {
			
			smblconn = getDBConnection("smblg");
			tcghconn = getDBConnection("TCGH");
			
			if(insert_write==true){
			SQLo = tcghconn.prepareStatement("delete "+tablename+"  where enterorg='"+inputEnterOrg+"'");
			SQLo.execute();
			SQLo.close();
			SQLo = tcghconn.prepareStatement("delete UNTLA_ADJUSTPROOF  where enterorg='"+inputEnterOrg+"' and (editid='GM' or not (caseno like '___000____' or caseno like '___001____'))");
			SQLo.execute();
			SQLo.close();
			SQLo = tcghconn.prepareStatement("delete UNTBU_ADJUSTPROOF  where enterorg='"+inputEnterOrg+"' and (editid='GM' or not (caseno like '___000____' or caseno like '___001____'))");
			SQLo.execute();
			SQLo.close();
			SQLo = tcghconn.prepareStatement("delete UNTRF_ADJUSTPROOF  where enterorg='"+inputEnterOrg+"' and (editid='GM' or not (caseno like '___000____' or caseno like '___001____'))");
			SQLo.execute();
			SQLo.close();
			SQLo = tcghconn.prepareStatement("delete UNTMP_ADJUSTPROOF  where enterorg='"+inputEnterOrg+"' and (editid='GM' or not (caseno like '___000____' or caseno like '___001____'))");
			SQLo.execute();
			SQLo.close();
			}
			
			SqlStr+="select   ";
			SqlStr+="isnull(a.acctTagID,'') as acctTagID,a.rcvdate,a.buydate,isnull(a.AgtNo,'') as AgtNo,c.RECDate,c.RECDivID,isnull(a.AcctDocID,'') as AcctDocID,a.AcctDate,a.RecDate,b.note,isnull(b.DocID,'') as DocNo, "; 
			SqlStr+="a.CategoryID,a.OffAccount,isnull(a.OffAccountDate,'') as OffAccountDate,isnull(c.FluctuatedDate,'') as FluctuatedDate,b.Qty,convert(int,a.AmtSTTL) as AmtSTTL,a.KeepDivID,a.KeeperID,a.ASUseDivID,a.ASUserID, ";
			SqlStr+="a.RecDate,a.AssetID,a.propertyno,a.lotno,a.serialno,a.differencekind,b.ValOrg,b.ValAdd,b.ValSub,b.ValDprTTL, ";
			SqlStr+="a.WorkingYears*12 as otherLimitYear,b.Reason,";
			SqlStr+="b.ValCur,isnull(c.cfmdate,'') as cfmdate,isnull(c.docno,'') as  DocNotwo,a.assetno,a.unit,a.matter,b.DocID ";
//			SqlStr+="from ASFluctuateDetail b  ";
//			SqlStr+="left join ASFluctuateMaster c on b.docid=c.docid      ";
//			SqlStr+="left join ASsetBasic a on a.AssetID=b.AssetID  ";
			
			SqlStr+="from ASsetBasic a  ";
			SqlStr+=",ASFluctuateDetail b,ASFluctuateMaster c where  a.AssetID=b.AssetID  ";
			SqlStr+=" and b.docid=c.docid      ";
			if ("A102V0000U".equals(inputEnterOrg)) {
				SqlStr+=" and c.FluctuatedDate is not null";//中科實中不轉未入帳的資料
			}
			//SqlStr+="and propertyno='30120050011' ";
			//SqlStr+="and serialno='0000001' ";
			//SqlStr+="where b.DocID is not null "; 
			SqlStr+= " and c.void = '0' "; 
			//SqlStr+="group by a.acctTagID,a.AgtNo,c.RECDate,c.RECDivID,a.AcctDocID,a.AcctDate,a.RecDate,b.note,b.DocID ";
			//SqlStr+=" where a.OffAccountDate is not null ";
			SqlStr+=" order by convert(int,b.DocID) ";
			System.out.println(SqlStr);
			SQLo = smblconn.prepareStatement(SqlStr);
			
			insertstr+="insert into "+tablename+" ";
			insertstr+="( " +
					"enterorg, caseno, ownership, differencekind, engineeringno, caseserialno, propertyno, lotno, " +
					"serialno, otherpropertyunit, othermaterial, otherlimityear, propertyname1, cause, cause1, adjustdate," +
					" verify, propertykind, fundtype, valuable, sourcedate, buydate, bookamount, oldbookunit, oldbookvalue, " +
					"oldnetunit, oldnetvalue, addbookunit, addbookvalue, addnetunit, addnetvalue, reducebookunit, reducebookvalue," +
					" reduceaccumdepr, reducenetunit, reducenetvalue, newbookunit, newbookvalue, newnetunit, newnetvalue," +
					" booknotes, keepunit, keeper, useunit, userno, usernote, place1, place, olddeprmethod, oldbuildfeecb," +
					" olddeprunitcb, olddeprpark, olddeprunit, olddeprunit1, olddepraccounts, oldscrapvalue, olddepramount," +
					" oldaccumdepr, oldapportionmonth, oldmonthdepr, oldmonthdepr1, oldapportionendym, newdeprmethod, " +
					"newbuildfeecb, newdeprunitcb, newdeprpark, newdeprunit, newdeprunit1, newdepraccounts, newscrapvalue, " +
					"newdepramount, newaccumdepr, newapportionmonth, newmonthdepr, newmonthdepr1, newapportionendym, " +
					"oldpropertyno, oldserialno, notes, editid, editdate, edittime, oldlotno, updatedeprcb " +
					") ";			
			insertstr+=" values ( ";
			rs = SQLo.executeQuery();
			while(rs.next())
			{
				//增減值單單號ID
				proofno="";
				proofyear="";
				proofno=Common.formatFrontString(Common.get(rs.getString("DocNo")), 7, '0');				
				proofyear=changeTaiwanYYMMDD(Common.get(rs.getDate("RecDate").toString()).replaceAll("-", ""),"1").substring(0,3);

								
				//caseno
				String caseno="";
				String proofdoc="";			    
				SQLo2 = tcghconn.prepareStatement("select caseno from UNTLA_ADJUSTPROOF where proofyear='"+proofyear+"' and proofno='"+proofno+"' and enterorg='" + inputEnterOrg + "' and editid='GM' ");				
				rs2 = SQLo2.executeQuery();
				if(rs2.next())	//查詢單號是存在
				{
					caseno=Common.get(rs2.getString("caseno").toString());
				}else{			//不存在新建一個增加單單號
					
					SQLo3 = tcghconn.prepareStatement("select right(isnull(max(caseno)+1,'20000'),7) as caseno from UNTLA_ADJUSTPROOF  where proofyear='"+proofyear+"' and enterorg='" + inputEnterOrg + "' and editid='GM' ");				
					rs3 = SQLo3.executeQuery();
					if(rs3.next())
					{
							caseno=changeTaiwanYYMMDD(changeDateFormat(rs.getDate("RecDate")).replaceAll("-", ""),"1").substring(0,3)+Common.formatFrontString(Common.get(rs3.getString("caseno")), 7, '0');

					}					
					SQLo3.close();
					proofdoc="";
					
					INSERT_ADJUSTPROOF(tcghconn,"UNTLA_ADJUSTPROOF", caseno, proofdoc, proofno, Common.get(rs.getString("acctTagID")), Common.get(rs.getString("AgtNo")), changeDateFormat(rs.getDate("RECDate")), Common.get(rs.getString("RECDivID")), changeDateFormat(rs.getDate("RecDate")), Common.get(rs.getString("AcctDocID")), changeDateFormat(rs.getDate("AcctDate")), Common.get(rs.getString("note")), Common.get(rs.getString("fluctuateddate")), Common.get(rs.getString("cfmdate").toString()), Common.get(rs.getString("DocNotwo")),Common.get(rs.getString("differencekind")),Common.get(rs.getString("DocID")));
					INSERT_ADJUSTPROOF(tcghconn,"UNTBU_ADJUSTPROOF", caseno, proofdoc, proofno, Common.get(rs.getString("acctTagID")), Common.get(rs.getString("AgtNo")), changeDateFormat(rs.getDate("RECDate")), Common.get(rs.getString("RECDivID")), changeDateFormat(rs.getDate("RecDate")), Common.get(rs.getString("AcctDocID")), changeDateFormat(rs.getDate("AcctDate")), Common.get(rs.getString("note")), Common.get(rs.getString("fluctuateddate")), Common.get(rs.getString("cfmdate").toString()), Common.get(rs.getString("DocNotwo")),Common.get(rs.getString("differencekind")),Common.get(rs.getString("DocID")));
					INSERT_ADJUSTPROOF(tcghconn,"UNTRF_ADJUSTPROOF", caseno, proofdoc, proofno, Common.get(rs.getString("acctTagID")), Common.get(rs.getString("AgtNo")), changeDateFormat(rs.getDate("RECDate")), Common.get(rs.getString("RECDivID")), changeDateFormat(rs.getDate("RecDate")), Common.get(rs.getString("AcctDocID")), changeDateFormat(rs.getDate("AcctDate")), Common.get(rs.getString("note")), Common.get(rs.getString("fluctuateddate")), Common.get(rs.getString("cfmdate").toString()), Common.get(rs.getString("DocNotwo")),Common.get(rs.getString("differencekind")),Common.get(rs.getString("DocID")));
					INSERT_ADJUSTPROOF(tcghconn,"UNTMP_ADJUSTPROOF", caseno, proofdoc, proofno, Common.get(rs.getString("acctTagID")), Common.get(rs.getString("AgtNo")), changeDateFormat(rs.getDate("RECDate")), Common.get(rs.getString("RECDivID")), changeDateFormat(rs.getDate("RecDate")), Common.get(rs.getString("AcctDocID")), changeDateFormat(rs.getDate("AcctDate")), Common.get(rs.getString("note")), Common.get(rs.getString("fluctuateddate")), Common.get(rs.getString("cfmdate").toString()), Common.get(rs.getString("DocNotwo")),Common.get(rs.getString("differencekind")),Common.get(rs.getString("DocID")));
					
				}
				//增加單單號ID
				SQLo2.close();
				rs2.close();
				subinsert="";
				subinsert+="'" + inputEnterOrg + "',";//enterorg			
				subinsert+="'"+caseno+"',";//caseno	
				subinsert+="'1',";//ownership
				//財產區分別
				subinsert+="'"+Common.get(rs.getString("differencekind"))+"',";//differencekind
				if("A27040000G".equals(inputEnterOrg)) {
					subinsert+="'"+getEngineeringnoFluctuate(Common.get(rs.getString("DocID")))+"',";//engineeringno
				} else {
					subinsert+="'"+Common.get(rs.getString("AgtNo"))+"',";//engineeringno
				}
				
				subinsert+="1,";//caseserialno
				String propertyno=Common.get(rs.getString("propertyno"));
				subinsert+="'"+propertyno+"',"; //propertyno
				
				String lotno=Common.get(rs.getString("lotno"));
				subinsert+="'"+lotno+"',"; //lotno
				
				String serialno=Common.get(rs.getString("serialno"));
				subinsert+="'"+serialno+"',"; //serialno

				subinsert+="'"+Common.get(rs.getString("unit"))+"',";//otherpropertyunit
				subinsert+="'"+Common.get(rs.getString("matter"))+"',";//othermaterial
				String otherlimityear=Common.get(rs.getString("otherlimityear"));
				subinsert+=""+otherlimityear+",";//otherlimityear
				subinsert+="'',";//propertyname1,
				subinsert+="'99',";//cause,
				subinsert+="'"+Common.get(rs.getString("reason"))+"',";//cause1,
				
				String adjustdate="";
				if(Common.get(rs.getString("FluctuatedDate")).length()>0){
					adjustdate=Common.get(rs.getString("FluctuatedDate")).replaceAll("-", "").substring(0,8);
				}
				
				if(adjustdate.equals("19000101")){
					subinsert+="null,";//adjustdate
					subinsert+="'N',";//verify
				}else{
					subinsert+="'"+adjustdate.replaceAll("-", "").substring(0,8)+"',";//adjustdate
					subinsert+="'Y',";//verify
				}				
				
				subinsert+="'01',";//propertykind
				subinsert+="'',";//fundtype,
				subinsert+="'N',";//valuable,
				
				String sourcedate="";
				if(Common.get(rs.getString("rcvdate")).length()>0){
					sourcedate=Common.get(rs.getString("rcvdate")).replaceAll("-", "").substring(0,8);
				}				
				subinsert+="'"+sourcedate+"',"; //sourcedate
				
				String buydate="";
				if(Common.get(rs.getString("buydate")).length()>0){
					buydate=Common.get(rs.getString("buydate")).replaceAll("-", "").substring(0,8);
				}				
				subinsert+="'"+buydate+"',"; //buydate
				
				String bookamount="";
				bookamount=Common.get(rs.getString("Qty"));
//				subinsert+=""+bookamount+","; //bookamount
				subinsert+=("".equals(bookamount)?0:bookamount) + ",";//bookamount
				
				String oldBookUnit="";
				oldBookUnit=Common.get(rs.getString("ValOrg"));
//				subinsert+=""+oldBookUnit+","; //oldBookUnit
				subinsert+=("".equals(oldBookUnit)?0:oldBookUnit) + ",";//oldBookUnit
				
				String oldbookvalue="";
				oldbookvalue=Common.get(rs.getString("ValOrg"));
//				subinsert+=""+oldbookvalue+","; //oldbookvalue
				subinsert+=("".equals(oldbookvalue)?0:oldbookvalue) + ",";//oldbookvalue
				
				String oldnetunit="";
				oldnetunit=Double.toString(Double.parseDouble(Common.get(rs.getString("ValOrg")))-Double.parseDouble(Common.get(rs.getString("ValDprTTL"))));
//				subinsert+=""+oldnetunit+","; //oldnetunit
				subinsert+=("".equals(oldnetunit)?0:oldnetunit) + ",";//oldnetunit
				
				String oldnetvalue="";
				oldnetvalue=Double.toString(Double.parseDouble(Common.get(rs.getString("ValOrg")))-Double.parseDouble(Common.get(rs.getString("ValDprTTL"))));
//				subinsert+=""+oldnetvalue+","; //oldnetvalue			
				subinsert+=("".equals(oldnetvalue)?0:oldnetvalue) + ",";//oldnetvalue
				
				String addbookunit="";
				addbookunit=Common.get(rs.getString("ValAdd"));
//				subinsert+=""+addbookunit+","; //addbookunit	
				subinsert+=("".equals(addbookunit)?0:addbookunit) + ",";//addbookunit
				
				String addbookvalue="";
				addbookvalue=Common.get(rs.getString("ValAdd"));
//				subinsert+=""+addbookvalue+","; //addbookvalue	
				subinsert+=("".equals(addbookvalue)?0:addbookvalue) + ",";//addbookvalue
				
				String addnetunit="";
				addnetunit=Common.get(rs.getString("ValAdd"));
//				subinsert+=""+addnetunit+","; //addnetunit	
				subinsert+=("".equals(addnetunit)?0:addnetunit) + ",";//addnetunit
				
				String addnetvalue="";
				addnetvalue=Common.get(rs.getString("ValAdd"));
//				subinsert+=""+addnetvalue+","; //addnetvalue
				subinsert+=("".equals(addnetvalue)?0:addnetvalue) + ",";//addnetvalue
				
				String reducebookunit="";
				reducebookunit=Common.get(rs.getString("ValSub"));
//				subinsert+=""+reducebookunit+","; //reducebookunit
				subinsert+=("".equals(reducebookunit)?0:reducebookunit) + ",";//reducebookunit
				
				String reducebookvalue="";
				reducebookvalue=Common.get(rs.getString("ValSub"));
//				subinsert+=""+reducebookvalue+","; //reducebookvalue
				subinsert+=("".equals(reducebookvalue)?0:reducebookvalue) + ",";//reducebookvalue
				
				String reduceaccumdepr="";
				reduceaccumdepr=Common.get(rs.getString("ValDprTTL"));
//				subinsert+=""+reduceaccumdepr+","; //reduceaccumdepr	
				subinsert+=("".equals(reduceaccumdepr)?0:reduceaccumdepr) + ",";//reduceaccumdepr

				String reducenetunit="";
				reducenetunit=Common.get(rs.getString("ValSub"));
//				subinsert+=""+reducenetunit+","; //reducenetunit	
				subinsert+=("".equals(reducenetunit)?0:reducenetunit) + ",";//reducenetunit
				
				String reducenetvalue="";
				reducenetvalue=Common.get(rs.getString("ValSub"));
//				subinsert+=""+reducenetvalue+","; //reducenetvalue
				subinsert+=("".equals(reducenetvalue)?0:reducenetvalue) + ",";//reducenetvalue
				
				
				String newbookunit="";
				newbookunit=Common.get(rs.getString("ValCur"));
//				subinsert+=""+newbookunit+","; //newbookunit
				subinsert+=("".equals(newbookunit)?0:newbookunit) + ",";//newbookunit
				
				String newbookvalue="";
				newbookvalue=Common.get(rs.getString("ValCur"));
//				subinsert+=""+newbookvalue+","; //newbookvalue
				subinsert+=("".equals(newbookvalue)?0:newbookvalue) + ",";//newbookvalue
				
				String newnetunit="";
				newnetunit=Double.toString(Double.parseDouble(Common.get(rs.getString("ValOrg")))-Double.parseDouble(Common.get(rs.getString("ValDprTTL")))+Double.parseDouble(Common.get(rs.getString("ValAdd")))-Double.parseDouble(Common.get(rs.getString("ValSub"))));
//				subinsert+=""+newnetunit+","; //newnetunit
				subinsert+=("".equals(newnetunit)?0:newnetunit) + ",";//newnetunit
				
				String newnetvalue="";
				newnetvalue=Double.toString(Double.parseDouble(Common.get(rs.getString("ValOrg")))-Double.parseDouble(Common.get(rs.getString("ValDprTTL")))+Double.parseDouble(Common.get(rs.getString("ValAdd")))-Double.parseDouble(Common.get(rs.getString("ValSub"))));
//				subinsert+=""+newnetvalue+","; //newnetvalue
				subinsert+=("".equals(newnetvalue)?0:newnetvalue) + ",";//newnetvalue
				
				subinsert+="'',"; //booknotes,
				subinsert+="'"+Common.get(rs.getString("KeepDivID"))+"',"; //keepunit
				subinsert+="'"+transKeeperNo(Common.get(rs.getString("KeeperID")))+"',"; //keeper
				subinsert+="'"+Common.get(rs.getString("ASUseDivID"))+"',"; //useunit
				subinsert+="'"+transKeeperNo(Common.get(rs.getString("ASUserID")))+"',"; //userno
				subinsert+="'',"; //usernote,
				subinsert+="'',"; //place1,
				subinsert+="'',"; //place,
				
				subinsert+=Common.sqlChar(getDeprMethod(rs.getString("acctTagID"))) + ",";//olddeprmethod	
				
				subinsert+="'',";//oldbuildfeecb,
				subinsert+="'',";//olddeprunitcb,
				subinsert+="'',";//olddeprpark,
				subinsert+="'',";//olddeprunit,
				subinsert+="'',";//olddeprunit1,
				subinsert+="'',";//olddepraccounts,

				subinsert+="null,";//oldscrapvalue,
				subinsert+="null,";//olddepramount,
				subinsert+="null,";//oldaccumdepr,
				subinsert+="null,";//oldapportionmonth,
				subinsert+="null,";//oldmonthdepr,
				subinsert+="null,";//oldmonthdepr1,
				
				subinsert+="'',";//oldapportionendym,
				subinsert+=Common.sqlChar(getDeprMethod(rs.getString("acctTagID"))) + ",";//newdeprmethod	
				subinsert+="'',";//newbuildfeecb,
				subinsert+="'',";//newdeprunitcb,
				subinsert+="'',";//newdeprpark,
				subinsert+="'',";//newdeprunit,
				subinsert+="'',";//newdeprunit1,
				
				subinsert+="null,";//newdepraccounts,
				subinsert+="null,";//newscrapvalue,
				subinsert+="null,";//newdepramount,
				subinsert+="null,";//newaccumdepr,
				subinsert+="null,";//newapportionmonth,
				subinsert+="null,";//newmonthdepr,
				subinsert+="null,";//newmonthdepr1,
				
				subinsert+="'',";//newapportionendym,				
				
				String oldpropertyno="";
				oldpropertyno=Common.get(rs.getString("CategoryID"));
				subinsert+="'"+oldpropertyno+"',"; //oldpropertyno
				
				String oldserialno="";
				oldserialno=Common.get(rs.getString("AssetID"));
				subinsert+="'"+oldserialno+"',"; //oldserialno

				
				
				subinsert+="'"+Common.get(rs.getString("note"))+"',";//notes
				subinsert+="'GM',";//editid
				subinsert+="'"+Datetime.getYYYYMMDD()+"',";//editdate
				subinsert+="'"+Datetime.getHHMMSS()+"',";//edittime
				subinsert+="'"+Common.get(rs.getString("AssetNo"))+"',";//oldlotno
				subinsert+="'N' ";//updatedeprcb
				
				Allinsert=insertstr+subinsert+") ;";
				//System.out.println(Allinsert);
				//if(!proofno.equals("")){
					try{
						if(insert_write==true){
							SQLo = tcghconn.prepareStatement(Allinsert);
							SQLo.execute();
							SQLo.close();
						}else{
							System.out.println(Allinsert);
						}
					}catch(Exception e){
						System.out.println(e);
						System.out.println(Allinsert);
					}
				//}
				
				showWhatIsNow();
			}

			SQLo.close();
			rs.close();
			smblconn.close();			
			tcghconn.close();
		} catch (Exception e) {
			System.out.println(Allinsert);
			e.printStackTrace();
		}

	}
	
	/**
	 * @UNTMP_REDUCEDETAIL
	 */
	public static void UNTMP_REDUCEDETAIL(String tablename){
		count = 0;
		
		Connection smblconn;
		Connection tcghconn;
		
		PreparedStatement SQLo = null;
//		PreparedStatement SQLo1 = null;
		PreparedStatement SQLo2 = null;
		PreparedStatement SQLo3 = null;
		ResultSet rs,rs2,rs3;
		String insertstr="";
		String subinsert="";
		String Allinsert="";
		
		String proofno="";
		String proofyear="";
		String SqlStr="";
		try {
			
			smblconn = getDBConnection("smblg");
			tcghconn = getDBConnection("TCGH");
			
			if(insert_write==true){
			SQLo = tcghconn.prepareStatement("delete "+tablename+"  where enterorg='"+inputEnterOrg+"'");
			SQLo.execute();
			SQLo.close();
			SQLo = tcghconn.prepareStatement("delete UNTLA_REDUCEPROOF  where enterorg='"+inputEnterOrg+"' and (editid='GM' or not (caseno like '___000____' or caseno like '___001____'))");
			SQLo.execute();
			SQLo.close();
			SQLo = tcghconn.prepareStatement("delete UNTBU_REDUCEPROOF  where enterorg='"+inputEnterOrg+"' and (editid='GM' or not (caseno like '___000____' or caseno like '___001____'))");
			SQLo.execute();
			SQLo.close();
			SQLo = tcghconn.prepareStatement("delete UNTRF_REDUCEPROOF  where enterorg='"+inputEnterOrg+"' and (editid='GM' or not (caseno like '___000____' or caseno like '___001____'))");
			SQLo.execute();
			SQLo.close();
			SQLo = tcghconn.prepareStatement("delete UNTMP_REDUCEPROOF  where enterorg='"+inputEnterOrg+"' and (editid='GM' or not (caseno like '___000____' or caseno like '___001____'))");
			SQLo.execute();
			SQLo.close();
			}
			
			SqlStr+="select  ";			
			SqlStr+="isnull(a.acctTagID,'') as acctTagID,isnull(a.AgtNo,'') as AgtNo,c.RECDate,c.RECDivID,isnull(a.AcctDocID,'') as AcctDocID,a.AcctDate,a.RecDate,b.note,isnull(b.DocID,'') as DocNo, ";
			SqlStr+="a.CategoryID,a.OffAccount,a.OffAccountDate,b.Qty,convert(int,c.AmtTTL) as AmtSTTL,convert(int,a.ValDprTTL) as ValDprTTL,a.KeepDivID,a.KeeperID, ";
			SqlStr+="b.price,b.ValCur,a.WorkingYears*12 as otherLimitYear,b.spec,b.brand,b.reason,b.LocationID,b.LocationName,b.DetractLocationName,b.spec,b.brand,a.RecDate,a.AssetID,a.propertyno,a.differencekind,a.lotno,a.serialno, ";
			SqlStr+="isnull(c.VoidDate,'') as VoidDate,a.Rcvdate,a.BuyDate,c.PostDate as reduceDate,isnull(c.cfmdate,'') as cfmdate,isnull(c.DocNo,'') as  DocNotwo,a.assetno,b.unit,a.matter  ";
			SqlStr+=" from ASDetractDetail b  ";
			SqlStr+="left join ASDetractMaster c on b.docid=c.docid   ";
			SqlStr+="left join ASsetBasic a on a.AssetID=b.AssetID ";
			
			
//			SqlStr+=" from ASsetBasic a  ";
//			SqlStr+="left join ASDetractDetail b on a.AssetID=b.AssetID ";
//			SqlStr+="left join ASDetractMaster c on b.docid=c.docid   ";
			SqlStr+= " where c.void = '0' ";
			//SqlStr+="where b.DocID is not null "; 
			//SqlStr+="group by a.acctTagID,a.AgtNo,c.RECDate,c.RECDivID,a.AcctDocID,a.AcctDate,a.RecDate,b.note,b.DocID ";
			//SqlStr+=" where a.OffAccountDate is not null ";
			SqlStr+=" order by convert(int,b.DocID) ";
			System.out.println(SqlStr);
			SQLo = smblconn.prepareStatement(SqlStr);
			
			insertstr+="insert into "+tablename+" ";
			insertstr+="( enterorg, caseno, ownership, differencekind, engineeringno, caseserialno, propertyno, lotno, serialno, " +
					"otherpropertyunit, othermaterial, otherlimityear, propertyname1, cause, cause1, reducedate, newenterorg, " +
					"transferdate, verify, propertykind, fundtype, valuable, booknotes, accountingtitle, oldbookamount, " +
					"oldbookunit, oldbookvalue, oldnetunit, oldnetvalue, adjustbookamount, adjustbookunit, adjustbookvalue, " +
					"adjustaccumdepr, adjustnetunit, adjustnetvalue, newbookamount, newbookunit, newbookvalue, newnetunit, " +
					"newnetvalue, articlename, nameplate, specification, sourcedate, buydate, licenseplate, movedate, keepunit," +
					" keeper, useunit, userno, usernote, place1, place, useyear, usemonth, cause2, returnplace, reducedeal2, " +
					"olddeprmethod, oldbuildfeecb, olddeprunitcb, olddeprpark, olddeprunit, olddeprunit1, olddepraccounts, " +
					"oldscrapvalue, olddepramount, oldaccumdepr, oldapportionmonth, oldmonthdepr, oldmonthdepr1, " +
					"oldapportionendym, newdeprmethod, newbuildfeecb, newdeprunitcb, newdeprpark, newdeprunit, newdeprunit1," +
					" newdepraccounts, newscrapvalue, newdepramount, newaccumdepr, newapportionmonth, newmonthdepr, " +
					"newmonthdepr1, newapportionendym, oldpropertyno, oldserialno, newenterorgreceive, notes, editid, " +
					"editdate, edittime, dealcaseno, dealdate, reducedeal, realizevalue, shiftorg, oldlotno, updatedeprcb " +
					") ";			
			insertstr+=" values ( ";
			rs = SQLo.executeQuery();
			while(rs.next())
			{
				//增減值單單號ID
				proofno="";
				proofyear="";
				proofno=Common.formatFrontString(Common.get(rs.getString("DocNo")), 7, '0');
				proofyear=changeTaiwanYYMMDD(Common.get(rs.getDate("RecDate").toString()).replaceAll("-", ""),"1").substring(0,3);

				
				//caseno
				String caseno="";
				String proofdoc="";			    
				SQLo2 = tcghconn.prepareStatement("select caseno from UNTLA_REDUCEPROOF where proofyear='"+proofyear+"' and proofno='"+proofno+"' and enterorg='" + inputEnterOrg + "' ");				
				rs2 = SQLo2.executeQuery();
				if(rs2.next()) //查詢單號是存在
				{
					caseno=Common.get(rs2.getString("caseno").toString());
				}else{         //不存在新建一個增加單單號
					
					SQLo3 = tcghconn.prepareStatement("select right(isnull(max(caseno)+1,'20000'),7) as caseno from UNTLA_REDUCEPROOF  where proofyear='"+proofyear+"' and enterorg='" + inputEnterOrg + "' and editid='GM' ");				
					rs3 = SQLo3.executeQuery();
					if(rs3.next())
					{
						caseno=changeTaiwanYYMMDD(Common.get(rs.getDate("RecDate").toString()).replaceAll("-", ""),"1").substring(0,3)+Common.formatFrontString(Common.get(rs3.getString("caseno")), 7, '0');
					}
					SQLo3.close();
					proofdoc="";
					
					
					INSERT_REDUCEPROOF(tcghconn,"UNTLA_REDUCEPROOF", caseno, proofdoc, proofno, Common.get(rs.getString("acctTagID")), Common.get(rs.getString("AgtNo")), changeDateFormat(rs.getDate("RECDate")), Common.get(rs.getString("RECDivID")), changeDateFormat(rs.getDate("RecDate")), Common.get(rs.getString("AcctDocID")), changeDateFormat(rs.getDate("AcctDate")), Common.get(rs.getString("note")) , Common.get(rs.getString("cfmdate")), Common.get(rs.getString("DocNotwo")), Common.get(rs.getString("reduceDate")),Common.get(rs.getString("differencekind")));
					INSERT_REDUCEPROOF(tcghconn,"UNTBU_REDUCEPROOF", caseno, proofdoc, proofno, Common.get(rs.getString("acctTagID")), Common.get(rs.getString("AgtNo")), changeDateFormat(rs.getDate("RECDate")), Common.get(rs.getString("RECDivID")), changeDateFormat(rs.getDate("RecDate")), Common.get(rs.getString("AcctDocID")), changeDateFormat(rs.getDate("AcctDate")), Common.get(rs.getString("note")) , Common.get(rs.getString("cfmdate")), Common.get(rs.getString("DocNotwo")), Common.get(rs.getString("reduceDate")),Common.get(rs.getString("differencekind")));
					INSERT_REDUCEPROOF(tcghconn,"UNTRF_REDUCEPROOF", caseno, proofdoc, proofno, Common.get(rs.getString("acctTagID")), Common.get(rs.getString("AgtNo")), changeDateFormat(rs.getDate("RECDate")), Common.get(rs.getString("RECDivID")), changeDateFormat(rs.getDate("RecDate")), Common.get(rs.getString("AcctDocID")), changeDateFormat(rs.getDate("AcctDate")), Common.get(rs.getString("note")) , Common.get(rs.getString("cfmdate")), Common.get(rs.getString("DocNotwo")), Common.get(rs.getString("reduceDate")),Common.get(rs.getString("differencekind")));
					INSERT_REDUCEPROOF(tcghconn,"UNTMP_REDUCEPROOF", caseno, proofdoc, proofno, Common.get(rs.getString("acctTagID")), Common.get(rs.getString("AgtNo")), changeDateFormat(rs.getDate("RECDate")), Common.get(rs.getString("RECDivID")), changeDateFormat(rs.getDate("RecDate")), Common.get(rs.getString("AcctDocID")), changeDateFormat(rs.getDate("AcctDate")), Common.get(rs.getString("note")) , Common.get(rs.getString("cfmdate")), Common.get(rs.getString("DocNotwo")), Common.get(rs.getString("reduceDate")),Common.get(rs.getString("differencekind")));
					
				}
				//增加單單號ID
				SQLo2.close();
				rs2.close();
				subinsert="";
				subinsert+="'" + inputEnterOrg + "',";//enterorg			
				subinsert+="'"+caseno+"',";//caseno	
				subinsert+="'1',";//ownership
				//財產區分別
				subinsert+="'"+Common.get(rs.getString("differencekind"))+"',";//differencekind
				subinsert+="'"+Common.get(rs.getString("AgtNo"))+"',";//engineeringno
				
				subinsert+="1,";//caseserialno
				
				String propertyno=Common.get(rs.getString("propertyno"));
				subinsert+="'"+propertyno+"',"; //propertyno
				
				String lotno=Common.get(rs.getString("lotno"));
				subinsert+="'"+lotno+"',"; //lotno
				
				String serialno=Common.get(rs.getString("serialno"));
				subinsert+="'"+serialno+"',"; //serialno
				 
				subinsert+="'"+Common.get(rs.getString("unit"))+"',";//otherpropertyunit
				subinsert+="'"+Common.get(rs.getString("matter"))+"',";//othermaterial
				subinsert+="'"+Common.get(rs.getString("otherLimitYear"))+"',";//otherlimityear
				subinsert+="'',";//propertyname1,
				subinsert+="'"+getSYSCA_CODENAME(tcghconn,Common.get(rs.getString("reason")),"CAA","499")+"',";//cause,
				subinsert+="'"+Common.get(rs.getString("reason"))+"',";//cause1,
				
				//====================
				 
				String reducedate="";
				if(Common.get(rs.getString("reduceDate")).length()>0){
					reducedate=Common.get(rs.getString("reduceDate")).replaceAll("-", "").substring(0,8);
				}
				subinsert+="'"+reducedate+"',";//reducedate,
				subinsert+="'',";//newenterorg,
				subinsert+="'',";//transferdate,
			    //======================
				subinsert+="'Y',";//verify
				
				subinsert+="'01',";//propertykind
				subinsert+="'',";//fundtype,
				subinsert+="'N',";//valuable,
				
				subinsert+="'',";//booknotes,
				subinsert+="'',";//accountingtitle,
				
				
				String oldbookamount="";
				oldbookamount=Common.get(rs.getString("Qty"));
//				subinsert+=""+oldbookamount+",";//oldbookamount,
				subinsert+=("".equals(oldbookamount)?0:oldbookamount) + ",";//oldbookamount,
				
				String oldbookunit="";
//				oldbookunit=Common.get(rs.getString("Price"));
				oldbookunit=Common.get(rs.getString("ValCur"));
				subinsert+=("".equals(oldbookunit)?0:oldbookunit) + ",";//oldbookunit,
				
				
				String oldbookvalue="";
//				oldbookvalue=Common.get(rs.getString("Price"));
				oldbookvalue=Common.get(rs.getString("ValCur"));
//				subinsert+=""+oldbookvalue+",";//oldbookvalue,
				subinsert+=("".equals(oldbookvalue)?0:oldbookvalue) + ",";//oldbookvalue,
				
				subinsert+="null,";//oldnetunit,
				
				String oldnetvalue="";
//				oldnetvalue=Common.get(rs.getString("Price"));
				oldnetvalue=Common.get(rs.getString("ValCur"));
//				subinsert+=""+oldnetvalue+",";//oldnetvalue,
				subinsert+=("".equals(oldnetvalue)?0:oldnetvalue) + ",";//oldnetvalue,
				
				String adjustbookamount="";
				adjustbookamount=Common.get(rs.getString("Qty"));
//				subinsert+=""+adjustbookamount+",";//adjustbookamount,
				subinsert+=("".equals(adjustbookamount)?0:adjustbookamount) + ",";//adjustbookamount,
				
				String adjustbookunit="";
//				adjustbookunit=Common.get(rs.getString("Price"));
				adjustbookunit=Common.get(rs.getString("ValCur"));
				subinsert+=("".equals(adjustbookunit)?0:adjustbookunit) + ",";//adjustbookunit,
				
				
				String adjustbookvalue="";
//				adjustbookvalue=Common.get(rs.getString("Price"));
				adjustbookvalue=Common.get(rs.getString("ValCur"));
//				subinsert+=""+adjustbookvalue+",";//adjustbookvalue,
				subinsert+=("".equals(adjustbookvalue)?0:adjustbookvalue) + ",";//adjustbookvalue,
				
				subinsert+="null,";//adjustaccumdepr,
				subinsert+="null,";//adjustnetunit,
				
				String adjustnetvalue="";
//				adjustnetvalue=Common.get(rs.getString("Price"));
				adjustnetvalue=Common.get(rs.getString("ValCur"));
//				subinsert+=""+adjustnetvalue+",";//adjustnetvalue,
				subinsert+=("".equals(adjustnetvalue)?0:adjustnetvalue) + ",";//adjustnetvalue,
				
				//String newbookamount="";
				//newbookamount=Common.get(rs.getString("Qty"));
//				subinsert+=""+newbookamount+",";//newbookamount,
				subinsert+="'0',";//newbookamount,
				
				subinsert+="'0',";//newbookunit,
				
				//String newbookvalue="";
				//newbookvalue=Common.get(rs.getString("Price"));
//				subinsert+=""+newbookvalue+",";//newbookvalue,
				subinsert+="'0',";//newbookvalue,
				
				subinsert+="null,";//newnetunit,
				String newnetvalue="";
//				newnetvalue=Common.get(rs.getString("Price"));
				newnetvalue=Common.get(rs.getString("ValCur"));
//				subinsert+=""+newnetvalue+",";//newnetvalue,
				subinsert+=("".equals(newnetvalue)?0:newnetvalue) + ",";//newnetvalue,
				
				
				subinsert+="'',";//articlename,
				subinsert+="'"+Common.get(rs.getString("brand"))+"',";//nameplate,
				subinsert+="'"+Common.get(rs.getString("spec")).replace("\'", "")+"',";//specification,
				String sourcedate="";
				if(Common.get(rs.getString("rcvdate")).length()>0){
					sourcedate=Common.get(rs.getString("rcvdate")).replaceAll("-", "").substring(0,8);
				}				
				subinsert+="'"+sourcedate+"',"; //sourcedate
				
				String buydate="";
				if(Common.get(rs.getString("buydate")).length()>0){
					buydate=Common.get(rs.getString("buydate")).replaceAll("-", "").substring(0,8);
				}
				
				subinsert+="'"+buydate+"',"; //buydate,***
				
				
				subinsert+="'',";//licenseplate,
				subinsert+="'',";//movedate,
				subinsert+="'',";//keepunit,
				subinsert+="'',";//keeper,
				subinsert+="'',";//useunit,
				subinsert+="'',";//userno,
				subinsert+="'',";//usernote,
				subinsert+="'"+getPlaceno(Common.get(rs.getString("LocationID")), Common.get(rs.getString("LocationName")))+"',";//place1,
				subinsert+="'"+Common.get(rs.getString("LocationName"))+"',";//place,
				changeTaiwanYYMMDD(Common.get(rs.getDate("RECDate").toString()).replaceAll("-", ""),"1").substring(0,3);
				String ROCRECDate=changeTaiwanYYMMDD(Common.get(rs.getString("RECDate")).replaceAll("-", ""),"1").substring(0,5);
				String ROCbuydate=changeTaiwanYYMMDD(Common.get(rs.getString("buydate")).replaceAll("-", ""),"1").substring(0,5);
				int TempMonth = Datetime.BetweenTwoMonth(ROCbuydate,ROCRECDate);
								
				if (TempMonth < 0 ) {
					subinsert+=0+",";//useyear
					subinsert+=0+",";//usemonth
				} else {
					String useyear="";
					useyear = String.valueOf(TempMonth/12);
					String usemonth="";
					usemonth = String.valueOf(TempMonth%12);
					subinsert+=useyear+",";//useyear
					subinsert+=usemonth+",";//usemonth
				}
				subinsert+="'',";//cause2,
				subinsert+="'"+Common.get(rs.getString("DetractLocationName"))+"',";//returnplace,
				subinsert+="'',";//reducedeal2,
				
				subinsert+=Common.sqlChar(getDeprMethod(rs.getString("acctTagID"))) + ","; //olddeprmethod	
				subinsert+="'',";//oldbuildfeecb,
				subinsert+="'',";//olddeprunitcb,
				subinsert+="'',";//olddeprpark,
				subinsert+="'',";//olddeprunit,
				subinsert+="'',";//olddeprunit1,
				subinsert+="'',";//olddepraccounts,
				
				subinsert+="null,";//oldscrapvalue,
				subinsert+="null,";//olddepramount,
				subinsert+="null,";//oldaccumdepr,
				subinsert+="null,";//oldapportionmonth,
				subinsert+="null,";//oldmonthdepr,
				subinsert+="null,";//oldmonthdepr1,
				
				subinsert+="'',";//oldapportionendym,
				
				subinsert+=Common.sqlChar(getDeprMethod(rs.getString("acctTagID"))) + ",";//newdeprmethod	
				
				subinsert+="'',";//newbuildfeecb,
				subinsert+="'',";//newdeprunitcb,
				subinsert+="'',";//newdeprpark,
				subinsert+="'',";//newdeprunit,
				subinsert+="'',";//newdeprunit1,
				subinsert+="'',";//newdepraccounts,
				
				subinsert+="null,";//newscrapvalue,
				subinsert+="null,";//newdepramount,
				subinsert+="null,";//newaccumdepr,
				subinsert+="null,";//newapportionmonth,
				subinsert+="null,";//newmonthdepr,
				subinsert+="null,";//newmonthdepr1,
				
				
				subinsert+="'',";//newapportionendym,				
				
				String oldpropertyno="";
				oldpropertyno=Common.get(rs.getString("CategoryID"));
				subinsert+="'"+oldpropertyno+"',"; //oldpropertyno
				
				String oldserialno="";
				oldserialno=Common.get(rs.getString("AssetID"));
				subinsert+="'"+oldserialno+"',"; //oldserialno

				subinsert+="'Y',";//newenterorgreceive,
				
				subinsert+="'"+Common.get(rs.getString("note"))+"',";//notes
				subinsert+="'GM',";//editid
				subinsert+="'"+Datetime.getYYYYMMDD()+"',";//editdate
				subinsert+="'"+Datetime.getHHMMSS()+"',";//edittime
				
				subinsert+="'',";//dealcaseno,
				subinsert+="'',";//dealdate,
				subinsert+="'',";//reducedeal,
				subinsert+="null,";//realizevalue,
				subinsert+="'', ";//shiftorg,
				subinsert+="'"+Common.get(rs.getString("AssetNo"))+"',";//oldlotno
				subinsert+="'N' ";//updatedeprcb
				Allinsert=insertstr+subinsert+") ;";
				//System.out.println(Allinsert);
				
				try{
					if(insert_write==true){
						SQLo = tcghconn.prepareStatement(Allinsert);
						SQLo.execute();
						SQLo.close();
					}else{
						System.out.println(Allinsert);
					}
				}catch(Exception e){
					System.out.println(e);
					System.out.println(Allinsert);
				}
				
				
				showWhatIsNow();
			}
			UPDATE_DATASTATE(tcghconn,"UNTMP_MOVABLE","UNTMP_MOVABLEDETAIL");		//動產主檔明細
			UPDATE_REDUCEDETAIL(tcghconn);//修改竹科管局減損金額(因為104年1月的基金已全部折舊因此減損金額為0)
			SQLo.close();
			rs.close();
			smblconn.close();			
			tcghconn.close();
		} catch (Exception e) {
			System.out.println(Allinsert);
			e.printStackTrace();
		}

	}
	
	
	/**
	 * @UNTMP_MOVEDETAIL
	 */
	public static void UNTMP_MOVEDETAIL(String tablename){
		count = 0;
		
		Connection smblconn;
		Connection tcghconn;
		
		PreparedStatement SQLo = null;
		PreparedStatement SQLo1 = null;
		PreparedStatement SQLo2 = null;
		PreparedStatement SQLo3 = null;
		ResultSet rs,rs2,rs3;
		String insertstr="";
		String subinsert="";
		String Allinsert="";
		
		String proofno="";
		String proofyear="";
		String SqlStr="";
		try {
			
			smblconn = getDBConnection("smblg");
			tcghconn = getDBConnection("TCGH");
			
			if(insert_write==true){
			SQLo = tcghconn.prepareStatement("delete "+tablename+"  where enterorg='"+inputEnterOrg+"'");
			SQLo.execute();
			SQLo.close();
			SQLo = tcghconn.prepareStatement("delete UNTMP_MOVEPROOF  where enterorg='"+inputEnterOrg+"' and (editid='GM' or not (caseno like '___000____' or caseno like '___001____'))");
			SQLo.execute();
			SQLo.close();
			
			}
			
			SqlStr+="select  ";			
			SqlStr+="c.keeperidfrom,a.rcvdate,a.WorkingYears*12 as otherLimitYear ,isnull(a.acctTagID,'') as acctTagID,isnull(a.AgtNo,'') as AgtNo,c.RECDate,isnull(c.RECDivID,'') as RECDivID,isnull(a.AcctDocID,'') as AcctDocID,a.AcctDate,a.RecDate,b.note,isnull(b.DocID,'') as DocNo, ";
			SqlStr+="a.CategoryID,a.OffAccount,b.Qty,convert(int,a.AmtSTTL) as AmtSTTL,convert(int,a.ValDprTTL) as ValDprTTL,a.KeepDivID,a.KeeperID, ";
			SqlStr+=" b.AssetName,b.spec,b.brand,b.KeepDivIDFrom,b.KeepDivIDTo,b.KeeperIDTo,b.ASUserIDTo,b.ASUseDivIDFrom,b.ASUseDivIDTo,b.Qty,b.Price,b.Note,b.LocationNameFrom,b.LocationIDTo,b.LocationNameTo, ";
			SqlStr+="a.RecDate,a.AssetID,a.propertyno,a.differencekind,a.lotno,a.serialno,b.unit,a.matter, ";
			SqlStr+="isnull(c.VoidDate,'') as VoidDate,a.BuyDate,c.RECDate,isnull(c.TransDate,'') as TransDate,isnull(c.DocID,'') as  DocNotwo, a.assetno ";
			SqlStr+=" from ASsetBasic a, ASTransDetail b ,ASTransMaster c ";
			SqlStr+=" Where a.AssetID=b.AssetID and b.docid=c.docid ";
			//SqlStr+="left join ASTransDetail b on a.AssetID=b.AssetID ";
			//SqlStr+="left join ASTransMaster c on b.docid=c.docid   ";
			SqlStr+= " and c.void = '0' "; 
			//SqlStr+="where b.DocID is not null "; 
			//SqlStr+="group by a.acctTagID,a.AgtNo,c.RECDate,c.RECDivID,a.AcctDocID,a.AcctDate,a.RecDate,b.note,b.DocID ";
			//SqlStr+=" where a.OffAccountDate is not null ";
			SqlStr+=" order by convert(int,b.DocID) ";
			System.out.println(SqlStr);
			SQLo = smblconn.prepareStatement(SqlStr);
			
			insertstr+="insert into "+tablename+" ";
			insertstr+="( " +
					"enterorg, caseno, ownership, differencekind, caseserialno, propertyno, lotno, serialno, propertyname1, " +
					"otherpropertyunit, othermaterial, otherlimityear, buydate, verify, propertykind, fundtype, valuable, " +
					"bookamount, bookunit, bookvalue, netunit, netvalue, nameplate, specification, sourcedate, oldmovedate," +
					" oldkeepunit, oldkeeper, olduseunit, olduserno, oldusernote, oldplace1, oldplace, newmovedate, " +
					"newkeepunit, newkeeper, newuseunit, newuserno, newusernote, newplace1, newplace, useyear, usemonth, " +
					"olddeprmethod, oldbuildfeecb, olddeprunitcb, olddeprpark, olddeprunit, olddeprunit1, olddepraccounts, " +
					"oldscrapvalue, olddepramount, oldaccumdepr, oldapportionmonth, oldmonthdepr, oldmonthdepr1, " +
					"oldapportionendym, newdeprmethod, newbuildfeecb, newdeprunitcb, newdeprpark, newdeprunit, newdeprunit1," +
					"newdepraccounts, newscrapvalue, newdepramount, newaccumdepr, newapportionmonth, newmonthdepr, " +
					"newmonthdepr1, newapportionendym, signnola, areala, holdnumela, holddenola, holdareala, signnobu, " +
					"careabu, sareabu, areabu, holdnumebu, holddenobu, holdareabu, measure, oldpropertyno, oldserialno, " +
					"notes, editid, editdate, edittime, oldlotno, updatedeprcb " +
					") ";			
			insertstr+=" values ( ";
			rs = SQLo.executeQuery();
			while(rs.next())
			{
				//增減值單單號ID
				proofno="";
				proofyear="";
				proofno=Common.formatFrontString(Common.get(rs.getString("DocNo")), 7, '0');
				proofyear=changeTaiwanYYMMDD(Common.get(rs.getDate("RecDate").toString()).replaceAll("-", ""),"1").substring(0,3);

				
				//caseno
				String caseno="";
				String proofdoc="";			    
				SQLo2 = tcghconn.prepareStatement("select caseno from UNTMP_MOVEPROOF where proofyear='"+proofyear+"' and proofno='"+proofno+"' and enterorg='" + inputEnterOrg + "' ");				
				rs2 = SQLo2.executeQuery();
				if(rs2.next()) //查詢單號是存在
				{
					caseno=Common.get(rs2.getString("caseno").toString());
				}else{         //不存在新建一個增加單單號
					
					SQLo3 = tcghconn.prepareStatement("select right(isnull(max(caseno)+1,'20000'),7) as caseno from UNTMP_MOVEPROOF  where proofyear='"+proofyear+"' and enterorg='" + inputEnterOrg + "' and editid='GM' ");				
					rs3 = SQLo3.executeQuery();
					if(rs3.next())
					{
						caseno=changeTaiwanYYMMDD(Common.get(rs.getDate("RecDate").toString()).replaceAll("-", ""),"1").substring(0,3)+Common.formatFrontString(Common.get(rs3.getString("caseno")), 7, '0');
					}
					SQLo3.close();
					proofdoc="";
					
					
					INSERT_MOVEPROOF(tcghconn,"UNTMP_MOVEPROOF", caseno, proofdoc, proofno, Common.get(rs.getDate("RECDate").toString()), Common.get(rs.getString("RECDivID").toString()), Common.get(rs.getDate("RecDate").toString()), Common.get(rs.getDate("TransDate").toString()), Common.get(rs.getString("note").toString()) );
					
				}
				//增加單單號ID
				SQLo2.close();
				rs2.close();
				subinsert="";
				subinsert+="'" + inputEnterOrg + "',";//enterorg			
				subinsert+="'"+caseno+"',";//caseno	
				subinsert+="'1',";//ownership
				//財產區分別
				subinsert+="'"+Common.get(rs.getString("differencekind"))+"',";//differencekind
				
				subinsert+="1,";//caseserialno
				
				String propertyno=Common.get(rs.getString("propertyno"));
				subinsert+="'"+propertyno+"',"; //propertyno
				
				String lotno=Common.get(rs.getString("lotno"));
				subinsert+="'"+lotno+"',"; //lotno
				
				String serialno=Common.get(rs.getString("serialno"));
				subinsert+="'"+serialno+"',"; //serialno
				
				subinsert+="N'"+Common.get(rs.getString("AssetName"))+"',";//propertyname1,
				subinsert+="'"+Common.get(rs.getString("unit"))+"',";//otherpropertyunit
				subinsert+="'"+Common.get(rs.getString("matter"))+"',";//othermaterial
				String otherlimityear=Common.get(rs.getString("otherLimitYear"));
				subinsert+=""+otherlimityear+",";//otherlimityear

				String buydate="";
				if(Common.get(rs.getString("buyDate")).length()>0){
					buydate=Common.get(rs.getString("buyDate")).replaceAll("-", "").substring(0,8);
				}
				subinsert+="'"+buydate+"',";//buydate
				subinsert+="'Y',";//verify
				
				subinsert+="'01',";//propertykind
				subinsert+="'',";//fundtype,
				subinsert+="'N',";//valuable,
				

				String bookamount="";
				bookamount=Common.get(rs.getString("Qty"));
				subinsert+=""+bookamount+","; //bookamount,
				
				subinsert+="null,";//bookunit,

				String bookvalue="";
				bookvalue=Common.get(rs.getString("AmtSTTL"));
				subinsert+=""+bookvalue+","; //bookvalue
				
				subinsert+="null,";//netunit,
				
				String netvalue="";
				netvalue=Common.get(rs.getString("AmtSTTL"));
				subinsert+=""+netvalue+",";//netvalue,
				
				
				subinsert+="'"+Common.get(rs.getString("Brand"))+"',";//nameplate,
				subinsert+="'"+Common.get(rs.getString("Spec"))+"',";//specification,
				String sourcedate="";
				if(Common.get(rs.getString("rcvdate")).length()>0){
					sourcedate=Common.get(rs.getString("rcvdate")).replaceAll("-", "").substring(0,8);
				}				
				subinsert+="'"+sourcedate+"',"; //sourcedate
				
				subinsert+="'',";//oldmovedate,
				subinsert+="'"+Common.get(rs.getString("KeepDivIDFrom"))+"',";//oldkeepunit,
				subinsert+="'"+transKeeperNo(Common.get(rs.getString("keeperidfrom")))+"',";//oldkeeper,			
				subinsert+="'"+Common.get(rs.getString("ASUseDivIDFrom"))+"',";//olduseunit,
				subinsert+="'',";//olduserno,
				subinsert+="'',";//oldusernote,
				subinsert+="'"+getPlaceno("", Common.get(rs.getString("LocationNameFrom")))+"',";//oldplace1,
				subinsert+="'"+Common.get(rs.getString("LocationNameFrom"))+"',";//oldplace,
				
				String newmovedate="";
				if(Common.get(rs.getString("TransDate")).length()>0){
					newmovedate=Common.get(rs.getString("TransDate")).replaceAll("-", "").substring(0,8);
				}
				subinsert+="'"+newmovedate+"',";//newmovedate,
				subinsert+="'"+Common.get(rs.getString("KeepDivIDTo"))+"',";//newkeepunit,
				subinsert+="'"+transKeeperNo(Common.get(rs.getString("KeeperIDTo")))+"',";//newkeeper,
				subinsert+="'"+Common.get(rs.getString("ASUseDivIDTo"))+"',";//newuseunit,
				subinsert+="'"+transKeeperNo(Common.get(rs.getString("ASUserIDTo")))+"',";//newuserno,
				subinsert+="'',";//newusernote,
				subinsert+="'"+getPlaceno(Common.get(rs.getString("LocationIDTo")), Common.get(rs.getString("LocationNameTo")))+"',";//newplace1,
				subinsert+="'"+Common.get(rs.getString("LocationNameTo")) + "',";//newplace,
				
				String ROCRECDate=changeTaiwanYYMMDD(Common.get(rs.getString("RECDate")).replaceAll("-", ""),"1").substring(0,5);
				String ROCbuydate=changeTaiwanYYMMDD(Common.get(rs.getString("buydate")).replaceAll("-", ""),"1").substring(0,5);
				int TempMonth = Datetime.BetweenTwoMonth(ROCbuydate,ROCRECDate);
								
				String useyear="";
				useyear = String.valueOf(TempMonth/12);
					
				subinsert+=useyear+",";//useyear
				
				String usemonth="";
				usemonth = String.valueOf(TempMonth%12);
				
				subinsert+=usemonth+",";//usemonth
				
				
				subinsert+=Common.sqlChar(getDeprMethod(rs.getString("acctTagID"))) + ",";//olddeprmethod,
				
				subinsert+="'',";//oldbuildfeecb,
				subinsert+="'',";//olddeprunitcb,
				subinsert+="'',";//olddeprpark,
				subinsert+="'',";//olddeprunit,
				subinsert+="'',";//olddeprunit1,
				subinsert+="'',";//olddepraccounts,
				
				subinsert+="null,";//oldscrapvalue,
				subinsert+="null,";//olddepramount,
				subinsert+="null,";//oldaccumdepr,
				subinsert+="null,";//oldapportionmonth,
				subinsert+="null,";//oldmonthdepr,
				subinsert+="null,";//oldmonthdepr1,
				
				subinsert+="'',";//oldapportionendym,
				
				subinsert+=Common.sqlChar(getDeprMethod(rs.getString("acctTagID"))) + ",";//newdeprmethod,
				
				
				subinsert+="'',";//newbuildfeecb,
				subinsert+="'',";//newdeprunitcb,
				subinsert+="'',";//newdeprpark,
				subinsert+="'',";//newdeprunit,
				subinsert+="'',";//newdeprunit1,
				subinsert+="'',";//newdepraccounts,
				
				subinsert+="null,";//newscrapvalue,
				subinsert+="null,";//newdepramount,
				subinsert+="null,";//newaccumdepr,
				subinsert+="null,";//newapportionmonth,
				subinsert+="null,";//newmonthdepr,
				subinsert+="null,";//newmonthdepr1,
				
				
				subinsert+="'',";//newapportionendym,				
				
				subinsert+="'',";//signnola,
				subinsert+="null,";//areala,
				subinsert+="null,";//holdnumela,
				subinsert+="null,";//holddenola,
				subinsert+="null,";//holdareala,
				subinsert+="'',";//signnobu,
				subinsert+="null,";//careabu,
				subinsert+="null,";//sareabu,
				subinsert+="null,";//areabu,
				subinsert+="null,";//holdnumebu,
				subinsert+="null,";//holddenobu,
				subinsert+="null,";//holdareabu,
				subinsert+="null,";//measure,
				
				String oldpropertyno="";
				oldpropertyno=Common.get(rs.getString("CategoryID"));
				subinsert+="'"+oldpropertyno+"',"; //oldpropertyno
				
				String oldserialno="";
				oldserialno=Common.get(rs.getString("AssetID"));
				subinsert+="'"+oldserialno+"',"; //oldserialno
				
				subinsert+="'"+Common.get(rs.getString("note"))+"',";//notes
				subinsert+="'GM',";//editid
				subinsert+="'"+Datetime.getYYYYMMDD()+"',";//editdate
				subinsert+="'"+Datetime.getHHMMSS()+"',";//edittime
				subinsert+="'"+Common.get(rs.getString("AssetNo"))+"',";//oldlotno
				subinsert+="'N' ";//updatedeprcb
				
				Allinsert=insertstr+subinsert+") ;";
				//System.out.println(Allinsert);
				//if(!proofno.equals("")){
					try{
						if(insert_write==true){
							SQLo = tcghconn.prepareStatement(Allinsert);
							SQLo.execute();
							SQLo.close();
						}else{
							System.out.println(Allinsert);
						}
					}catch(Exception e){
						System.out.println(e);
						System.out.println(Allinsert);
					}
				//}
				
				showWhatIsNow();
			}

			SQLo.close();
			rs.close();
			smblconn.close();			
			tcghconn.close();
		} catch (Exception e) {
			System.out.println(Allinsert);
			e.printStackTrace();
		}

	}
	
	/**
	 * @UNTDP_MONTHDEPR
	 */
	public static void UNTDP_MONTHDEPR(String tablename){
		count = 0;
		
		Connection smblconn;
		Connection tcghconn;
		
		PreparedStatement SQLo = null;
		PreparedStatement SQLo1 = null;
		PreparedStatement SQLo2 = null;
		PreparedStatement SQLo3 = null;
		ResultSet rs,rs2,rs3;
		String insertstr="";
		String subinsert="";
		String Allinsert="";
		
		String SqlStr="";
		try {
			
			smblconn = getDBConnection("smblg");
			tcghconn = getDBConnection("TCGH");
			
			
			if(insert_write==true){
			SQLo = tcghconn.prepareStatement("delete "+tablename+"  where enterorg='"+inputEnterOrg+"' and propertyno=''  ");
			SQLo.execute();
			SQLo.close();
			SQLo = tcghconn.prepareStatement("delete "+tablename+"  where enterorg='"+inputEnterOrg+"' and propertytype in ('4','5','6')  ");
			SQLo.execute();
			SQLo.close();
			}
			
			//1856828
			SqlStr+="select ";
			SqlStr+="isnull(a.acctTagID,'') as acctTagID,isnull(a.AgtNo,'') as AgtNo,isnull(a.AcctDocID,'') as AcctDocID,a.AcctDate,a.RecDate, "; 
			SqlStr+="a.CategoryID,a.OffAccount,isnull(a.OffAccountDate,'') as OffAccountDate,convert(int,isnull(a.AmtSTTL,0)) as AmtSTTL,convert(int,a.ValDprTTL) as ValDprTTL,a.KeepDivID,a.KeeperID, ";
			SqlStr+="a.RecDate,a.AssetID,a.propertyno,a.lotno,a.serialno,b.ValOrg,convert(int,isnull(b.ValDprTTL,0)) AS ValDprTTL_B,convert(int,isnull(b.ValDpr,0)) as ValDpr_B , ";
			SqlStr+="b.YearMonth,b.AssetID,convert(int,isnull(b.ValCur,0)) as ValCur,a.differencekind,   ";
			SqlStr+="b.Qty,b.Note,b.ValKeep,b.AssetType,b.WorkingYears,b.RecID,a.buydate,a.postdate  ";
			SqlStr+="from ASsetBasic a  ";
			SqlStr+=",ASDprCountDetailAcctChild b where a.AssetID=b.AssetID  ";
			//竹科管局不轉入110公務類財產之折舊
			if("A27040000G".equals(inputEnterOrg)) {
				SqlStr+=" and a.differencekind <>'110' and b.YearMonth = '201504' ";
			}
			//SqlStr+=",ASFluctuateMaster c    ";
//			SqlStr+="where  a.AssetID=b.AssetID  ";
			
//			SqlStr+="and b.RecID>='1856828' "; //中斷繼續用
			
			
			//SqlStr+=" and b.yearmonth = '201402'";
			
			//SqlStr+="and serialno='0000001' ";
			//SqlStr+="where b.DocID is not null "; 
			//SqlStr+="group by a.acctTagID,a.AgtNo,c.RECDate,c.RECDivID,a.AcctDocID,a.AcctDate,a.RecDate,b.note,b.DocID ";
			//SqlStr+=" where a.OffAccountDate is not null ";
			SqlStr+=" order by convert(int,b.RecID) ";
			System.out.println(SqlStr);
			SQLo = smblconn.prepareStatement(SqlStr);
			
			insertstr+="insert into "+tablename+" ";
			insertstr+="( " +
					"enterorg, ownership, differencekind, propertyno, serialno, lotno, serialno1, deprym, deprfrequency, " +
					"verify, propertytype, propertykind, fundtype, valuable, deprmethod, buildfeecb, deprunitcb, deprpark, " +
					"deprunit, deprunit1, depraccounts, deprpercent, originalbv, bookvalue, scrapvalue, depramount, apportionmonth, " +
					"oldnetvalue, oldaccumdepr, addaccumdepr, reduceaccumdepr, monthdepr1, monthdepr2, monthdepr, newaccumdepr, " +
					"newnetvalue, scaledbookvalue, scaledoldnetvalue, scaledoldaccumdepr, scaledaddaccumdepr, scaledreduceaccumdepr, " +
					"scaledmonthdepr1, scaledmonthdepr2, scaledmonthdepr, scalednewaccumdepr, scalednewnetvalue, buydate, enterdate, " +
					"sourcedate, propertyname1, limityear, bookamount, keepunit, keeper, escroworilimitmonth, escroworivalue, " +
					"escroworiaccumdepr, engineeringno, notes, editid, editdate, edittime, oldpropertyno, oldserialno " +
					") ";			
			insertstr+=" values ( ";
			rs = SQLo.executeQuery();
			while(rs.next())
			{

				subinsert="";
				subinsert+="'" + inputEnterOrg + "',";//enterorg				
				subinsert+="'1',";//ownership

				String differencekind =  Common.get(rs.getString("differencekind"));
				subinsert+="'"+differencekind+"',";//differencekind
				
				String propertyno=Common.get(rs.getString("propertyno"));
				subinsert+="'"+propertyno+"',"; //propertyno

				String serialno=Common.get(rs.getString("serialno"));
				subinsert+="'"+serialno+"',"; //serialno
				
				String lotno=Common.get(rs.getString("lotno"));
				subinsert+="'"+lotno+"',"; //lotno
				
				String serialno1="";
				serialno1=Common.get(rs.getString("RecID"));
				subinsert+=""+serialno1+",";//serialno1
				
				String deprym="";
				deprym=Common.get(rs.getString("YearMonth"));
				subinsert+="'"+deprym+"',";//deprym
				
				subinsert+="null,";//deprfrequency
			
				subinsert+="'Y',";//verify				
				
				if(propertyno.length()>3){
					if(propertyno.subSequence(0, 3).equals("111")){
						subinsert+="'2',";//propertytype,
					}else{
						if(propertyno.subSequence(0, 1).equals("1")){
							subinsert+="'1',";//propertytype,
						}
						if(propertyno.subSequence(0, 1).equals("2")){
							subinsert+="'3',";//propertytype,
						}
						if(propertyno.subSequence(0, 1).equals("3")){
							subinsert+="'4',";//propertytype,
						}
						if(propertyno.subSequence(0, 1).equals("4")){
							subinsert+="'5',";//propertytype,
						}
						if(propertyno.subSequence(0, 1).equals("5")){
							subinsert+="'6',";//propertytype,
						}
					}					
				}else{
					subinsert+="'',";//propertytype,
				}
				subinsert+="'01',";//propertykind,
				
				String fundtype="";
				if(Common.get(rs.getString("differencekind")).equals("120")){ //基金
					fundtype="H064000010000";
				}
				subinsert+="'"+fundtype+"',";//fundtype,
				subinsert+="'N',";//valuable,
				subinsert+="'02',";//deprmethod,
				subinsert+="'',";//buildfeecb,
				subinsert+="'',";//deprunitcb,
				subinsert+="'',";//deprpark,
				subinsert+="'',";//deprunit,
				subinsert+="'',";//deprunit1,
				subinsert+="'',";//depraccounts,
				
				subinsert+="null,";//deprpercent,
				subinsert+=""+Common.get(rs.getString("AmtSTTL"))+",";//originalbv,
				
				String bookvalue="";
				bookvalue=Common.get(rs.getString("ValCur"));
				subinsert+=""+bookvalue+",";//bookvalue,
				
				String scrapvalue=Common.get(rs.getString("WorkingYears"));
				subinsert+=""+scrapvalue+",";//scrapvalue,

				subinsert+="null,";//depramount,
				subinsert+="null,";//apportionmonth,
				
				String oldnetvalue="";
				oldnetvalue=Integer.toString(Integer.valueOf(Common.get(rs.getString("ValCur")))-Integer.valueOf(Common.get(rs.getString("ValDprTTL_B")))+Integer.valueOf(Common.get(rs.getString("ValDpr_B"))));
				subinsert+=""+oldnetvalue+",";//oldnetvalue,
				
				String oldaccumdepr="";
				oldaccumdepr=Integer.toString(Integer.valueOf(Common.get(rs.getString("ValDprTTL_B")))-Integer.valueOf(Common.get(rs.getString("ValDpr_B"))));
				subinsert+=""+oldaccumdepr+",";//oldaccumdepr,
				
				subinsert+="0,";//addaccumdepr,
				subinsert+="0,";//reduceaccumdepr,
				
				String monthdepr1="";
				monthdepr1=Common.get(rs.getString("ValDpr_B"));
				subinsert+=""+monthdepr1+",";//monthdepr1,
				subinsert+="0,";//monthdepr2,
				
				String monthdepr="";
				monthdepr=Common.get(rs.getString("ValDpr_B"));
				subinsert+=""+monthdepr+",";///monthdepr,
				
				String newaccumdepr="";
				newaccumdepr=Common.get(rs.getString("ValDprTTL_B"));
				subinsert+=""+newaccumdepr+",";//newaccumdepr,
				
				String newnetvalue="";
				newnetvalue=Integer.toString(Integer.valueOf(Common.get(rs.getString("ValCur")))-Integer.valueOf(Common.get(rs.getString("ValDprTTL_B"))));
				
				subinsert+=""+newnetvalue+",";//newnetvalue,
				subinsert+="null,";//scaledbookvalue,
				subinsert+="null,";//scaledoldnetvalue,
				subinsert+="null,";//scaledoldaccumdepr,
				subinsert+="null,";//scaledaddaccumdepr,
				subinsert+="null,";//scaledreduceaccumdepr,
				subinsert+="null,";//scaledmonthdepr1,
				subinsert+="null,";//scaledmonthdepr2,
				subinsert+="null,";//scaledmonthdepr,
				subinsert+="null,";//scalednewaccumdepr,
				subinsert+="null,";//scalednewnetvalue,

				String buydate="";
				if(Common.get(rs.getString("buydate")).length()>0){
					buydate=Common.get(rs.getString("buydate")).replaceAll("-", "").substring(0,8);
				}				
				subinsert+="'"+buydate+"',"; //buydate

				String enterdate="";
				if(Common.get(rs.getString("postdate")).length()>0){
					enterdate=Common.get(rs.getString("postdate")).replaceAll("-", "").substring(0,8);
				}				
				subinsert+="'"+enterdate+"',"; //enterdate
				
				subinsert+="'',";//sourcedate,
				subinsert+="'',";//propertyname1,				
				
				String limityear=Common.get(rs.getString("WorkingYears"));
				subinsert+=""+limityear+",";//limityear,
				
				String bookamount="";
				bookamount=Common.get(rs.getString("Qty"));
				subinsert+=""+bookamount+","; //bookamount
				
				
				subinsert+="'',";//keepunit,
				subinsert+="'',";//keeper,
				
				subinsert+="null,";//escroworilimitmonth,
				subinsert+="null,";//escroworivalue,
				subinsert+="null,";//escroworiaccumdepr,
				
				subinsert+="'"+Common.get(rs.getString("AgtNo"))+"',";//engineeringno
	
				subinsert+="'"+Common.get(rs.getString("note"))+"',";//notes
				subinsert+="'GM',";//editid
				subinsert+="'"+Datetime.getYYYYMMDD()+"',";//editdate
				subinsert+="'"+Datetime.getHHMMSS()+"',";//edittime
				
				String oldpropertyno="";
				oldpropertyno=Common.get(rs.getString("CategoryID"));
				subinsert+="'"+oldpropertyno+"',"; //oldpropertyno
				
				String oldserialno="";
				oldserialno=Common.get(rs.getString("AssetID"));
				subinsert+="'"+oldserialno+"' "; //oldserialno
				
				
				Allinsert=insertstr+subinsert+") ;";
				//System.out.println(Allinsert);
				
				try{
					if(insert_write==true){
	
						String SqlStr3="SELECT TOP 1 * FROM UNTDP_MONTHDEPR WHERE enterorg='"+inputEnterOrg+"' and ownership='1' and differencekind='"+differencekind+"' and propertyno='"+propertyno+"' and serialno='"+serialno+"' and serialno1="+serialno1+" ";
						//System.out.println(SqlStr3);
						SQLo3 = tcghconn.prepareStatement(SqlStr3);
						rs3 = SQLo3.executeQuery();
						if(!rs3.next())
						{
							SQLo = tcghconn.prepareStatement(Allinsert);
							SQLo.execute();
							SQLo.close();
						}
						SQLo3.close();
						rs3.close();
					}
				}catch(Exception e){
					System.out.println(e);
					System.out.println(Allinsert);
				}
				
				showWhatIsNow();
				
			}
			
			//竹科管局進行特殊調帳
			if(inputEnterOrg.equals("A27040000G")){
				MODIFY_UNTDP_MONTHDEPR();
			}
			
			
			SQLo.close();
			rs.close();
			smblconn.close();			
			tcghconn.close();
		} catch (Exception e) {
			System.out.println(Allinsert);
			e.printStackTrace();
		}

	}
	
	/**
	 * @MODIFY_UNTDP_MONTHDEPR 竹科管局折舊金額帳務修改 by 限定財產編號單一個案使用
	 */
	public static void MODIFY_UNTDP_MONTHDEPR(){
		
		//Connection smblconn;
		Connection tcghconn;
		
		PreparedStatement SQLo = null;
		PreparedStatement SQLo1 = null;
		PreparedStatement SQLo2 = null;
		PreparedStatement SQLo3 = null;
		ResultSet rs,rs2,rs3;
		String updatestr="";
		String StartMonth="201504";//起始修改月份
		String SqlStr="";
		String newaccumdepr="";
		String tempnewaccumdepr="";
		String tempoldnetvalue="";
		String oldaccumdepr="";
		try {
			
			//smblconn = getDBConnection("smblg");
			tcghconn = getDBConnection("TCGH");
			
			SqlStr+="select * from UNTDP_MONTHDEPR  where enterorg='A27040000G' and differencekind='120' " +
					"and oldpropertyno='3013002-01' and oldserialno='309400512' and deprym>='"+StartMonth+"' order by deprym ";

			System.out.println(SqlStr);
			SQLo = tcghconn.prepareStatement(SqlStr);
			
			updatestr+="insert into   ";
			
			rs = SQLo.executeQuery();
			while(rs.next())
			{

				updatestr="";
				String enterorg="";
				enterorg=Common.get(rs.getString("enterorg"));
				String ownership="";
				ownership=Common.get(rs.getString("ownership"));
				String differencekind="";
				differencekind=Common.get(rs.getString("differencekind"));
				String propertyno="";
				propertyno=Common.get(rs.getString("propertyno"));
				String serialno="";
				serialno=Common.get(rs.getString("serialno"));
				String lotno="";
				lotno=Common.get(rs.getString("lotno"));
				String serialno1="";
				serialno1=Common.get(rs.getString("serialno1"));				
				String deprym="";
				deprym=Common.get(rs.getString("deprym"));
				String oldnetvalue ="";
				//oldnetvalue=Common.get(rs.getString("oldnetvalue"));
				String newnetvalue ="";
				newnetvalue=Common.get(rs.getString("newnetvalue"));
				
				updatestr+=" update  UNTDP_MONTHDEPR set "; //deprym

				//System.out.println(Datetime.BetweenTwoMonthCE(StartMonth,deprym));
				if(Datetime.BetweenTwoMonthCE(StartMonth,deprym)%2==0){//單數月折舊金額 258278.00
					updatestr+=" monthdepr =258278.00,monthdepr1 =258278.00," ;
				}else{//雙數月折舊金額 258277.00
					updatestr+=" monthdepr =258277.00,monthdepr1 =258277.00," ;
				}
				//第一個月  折舊金額 258278
				if(Datetime.BetweenTwoMonthCE(StartMonth,deprym)==0){
					//上期累計折舊
					oldaccumdepr="31307441";
					updatestr+=" oldaccumdepr ="+oldaccumdepr+"," ;
					//累計折舊
					newaccumdepr=Integer.toString(Integer.parseInt(oldaccumdepr)+258278);					
					updatestr+=" newaccumdepr ="+newaccumdepr+"," ;
					
					tempnewaccumdepr=Integer.toString(Integer.parseInt(oldaccumdepr)+258278);

					//上期現值
					oldnetvalue="61986600";
					updatestr+=" oldnetvalue ="+oldnetvalue+"," ;
					//現值
					newnetvalue=Integer.toString(Integer.parseInt(oldnetvalue)-258278);
					updatestr+=" newnetvalue ="+newnetvalue+" " ;
					
					tempoldnetvalue=newnetvalue;
				}else{
					if(Datetime.BetweenTwoMonthCE(StartMonth,deprym)%2==0){//單數月累計折舊金額+ 258278
						//上期累計折舊
						oldaccumdepr=tempnewaccumdepr;
						updatestr+=" oldaccumdepr ="+oldaccumdepr+"," ;
						//累計折舊
						newaccumdepr=Integer.toString(Integer.parseInt(tempnewaccumdepr)+258278);
						updatestr+=" newaccumdepr ="+newaccumdepr+"," ;
						
						tempnewaccumdepr=Integer.toString(Integer.parseInt(tempnewaccumdepr)+258278);//本期累計折舊=下期的上期累計折舊
						//上期現值
						oldnetvalue=tempoldnetvalue;
						updatestr+=" oldnetvalue ="+oldnetvalue+"," ;						
						//現值
						newnetvalue=Integer.toString(Integer.parseInt(tempoldnetvalue)-258278);
						updatestr+=" newnetvalue ="+newnetvalue+" " ;
						
						tempoldnetvalue=newnetvalue;//本期現值=下期的上期現值
					}else{//雙數月累計折舊金額+ 258277
						//上期累計折舊
						oldaccumdepr=tempnewaccumdepr;
						updatestr+=" oldaccumdepr ="+oldaccumdepr+"," ;	
						//累計折舊
						newaccumdepr=Integer.toString(Integer.parseInt(tempnewaccumdepr)+258277);
						updatestr+=" newaccumdepr ="+newaccumdepr+"," ;
						
						tempnewaccumdepr=Integer.toString(Integer.parseInt(tempnewaccumdepr)+258277);//本期累計折舊=下期的上期累計折舊
						//上期現值
						oldnetvalue=tempoldnetvalue;
						updatestr+=" oldnetvalue ="+oldnetvalue+"," ;						
						//現值
						newnetvalue=Integer.toString(Integer.parseInt(tempoldnetvalue)-258277);
						updatestr+=" newnetvalue ="+newnetvalue+" " ;
						
						tempoldnetvalue=newnetvalue;//本期現值=下期的上期現值
					}
					
				}
				updatestr+=" where enterorg='"+enterorg+"' and ownership='"+ownership+"' and differencekind='"+differencekind+"' and propertyno='"+propertyno+"' and serialno='"+serialno+"' and lotno='"+lotno+"' and serialno1='"+serialno1+"' and deprym='"+deprym+"' " ;
				try{
					if(insert_write==true){
						SQLo1 = tcghconn.prepareStatement(updatestr);
						SQLo1.execute();
						SQLo1.close();
					}else{
						System.out.println(updatestr);
					}
				}catch(Exception e){
					System.out.println(e);
					System.out.println(updatestr);
				}
				
				showWhatIsNow();
				
			}

			SQLo.close();
			rs.close();
			//smblconn.close();			
			tcghconn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	/**
	 * @UNTDP_MONTHDEPR_2 土地改良物用折舊轉入
	 */
	public static void UNTDP_MONTHDEPR_2(String tablename){
		count = 0;
		
		Connection smblconn;
		Connection tcghconn;
		
		PreparedStatement SQLo = null;
		PreparedStatement SQLo1 = null;
		PreparedStatement SQLo2 = null;
		PreparedStatement SQLo3 = null;
		ResultSet rs,rs2,rs3;
		String insertstr="";
		String subinsert="";
		String Allinsert="";
		
		String SqlStr="";
		try {
			
			smblconn = getDBConnection("smblg");
			tcghconn = getDBConnection("TCGH");
			
			if(insert_write==true){
			SQLo = tcghconn.prepareStatement("delete "+tablename+"  where enterorg='"+inputEnterOrg+"' and propertyno=''  ");
			SQLo.execute();
			SQLo.close();
			SQLo = tcghconn.prepareStatement("delete "+tablename+"  where enterorg='"+inputEnterOrg+"' and propertytype = '2'  ");
			SQLo.execute();
			SQLo.close();
			}
			
			//17657
			SqlStr+="select ";
			SqlStr+="isnull(a.acctTagID,'') as acctTagID,isnull(a.AssetID,'') as AssetID,isnull(a.AcctDocID,'') as AcctDocID,a.AcctDate,a.RecDate, "; 
			SqlStr+="a.CategoryID,a.OffAccount,isnull(a.OffAccountDate,'') as OffAccountDate,convert(int,isnull(a.AmtSTTL,0)) as AmtSTTL,convert(int,a.ValDprTTL) as ValDprTTL,a.KeepDivID,a.KeeperID, ";
			SqlStr+="a.RecDate,b.ValOrg,convert(int,b.ValDprTTL) AS ValDprTTL_B,convert(int,b.ValDpr) as ValDpr_B , ";
			SqlStr+="b.YearMonth,convert(int,b.ValCur) as ValCur,   ";
			SqlStr+="b.Qty,b.Note,b.ValKeep,b.AssetType,b.WorkingYears,b.RecID  ";
			SqlStr+="from ASIprvBasic a  ";
			SqlStr+=",ASDprCountDetailAcctChild b where a.AssetID=b.AssetID ";
			//SqlStr+=",ASFluctuateMaster c    ";
//			SqlStr+="where  a.AssetID=b.AssetID  ";
			//SqlStr+="and b.RecID>='105349' ";
			//SqlStr+="and serialno='0000001' ";
			//SqlStr+="where b.DocID is not null "; 
			//SqlStr+="group by a.acctTagID,a.AgtNo,c.RECDate,c.RECDivID,a.AcctDocID,a.AcctDate,a.RecDate,b.note,b.DocID ";
			//SqlStr+=" where a.OffAccountDate is not null ";
			SqlStr+=" and b.YearMonth = '201504' order by convert(int,b.RecID) ";
			System.out.println(SqlStr);
			SQLo = smblconn.prepareStatement(SqlStr);
			
			insertstr+="insert into "+tablename+" ";
			insertstr+="( " +
					"enterorg, ownership, differencekind, propertyno, serialno, lotno, serialno1, deprym, deprfrequency, " +
					"verify, propertytype, propertykind, fundtype, valuable, deprmethod, buildfeecb, deprunitcb, deprpark, " +
					"deprunit, deprunit1, depraccounts, deprpercent, originalbv, bookvalue, scrapvalue, depramount, apportionmonth, " +
					"oldnetvalue, oldaccumdepr, addaccumdepr, reduceaccumdepr, monthdepr1, monthdepr2, monthdepr, newaccumdepr, " +
					"newnetvalue, scaledbookvalue, scaledoldnetvalue, scaledoldaccumdepr, scaledaddaccumdepr, scaledreduceaccumdepr, " +
					"scaledmonthdepr1, scaledmonthdepr2, scaledmonthdepr, scalednewaccumdepr, scalednewnetvalue, buydate, enterdate, " +
					"sourcedate, propertyname1, limityear, bookamount, keepunit, keeper, escroworilimitmonth, escroworivalue, " +
					"escroworiaccumdepr, engineeringno, notes, editid, editdate, edittime, oldpropertyno, oldserialno " +
					") ";			
			insertstr+=" values ( ";
			rs = SQLo.executeQuery();
			while(rs.next())
			{

				subinsert="";
				subinsert+="'" + inputEnterOrg + "',";//enterorg				
				subinsert+="'1',";//ownership
				//財產區分別
				String differencekind="";
				String fundtype="";
				if(Common.get(rs.getString("acctTagID")).equals("1")){ //公務
					differencekind="110";//differencekind
				}else{
					if(Common.get(rs.getString("acctTagID")).equals("2")){ //基金
						differencekind="120";//differencekind
						fundtype="H064000010000";
					}else{
						if(Common.get(rs.getString("acctTagID")).equals("6")){ //代管資產
							differencekind="111";//differencekind
						}else if("A10120000U".equals(inputEnterOrg) || "A102V0000U".equals(inputEnterOrg)){
							differencekind="110";//differencekind
						}else{
							differencekind="";//differencekind
						}
					}
				}
				subinsert+="'"+differencekind+"',";
				
				String AssetID=Common.get(rs.getString("AssetID"));
				String CategoryID=Common.get(rs.getString("CategoryID"));
				String SqlStr2="SELECT TOP 1 * FROM UNTRF_ATTACHMENT WHERE oldserialno='"+AssetID+"' ";
				//System.out.println(SqlStr2);
				SQLo2 = tcghconn.prepareStatement(SqlStr2);
				rs2 = SQLo2.executeQuery();
				String propertyno="";
				String serialno="";
				if(rs2.next())
				{
					propertyno=Common.get(rs2.getString("propertyno"));
					serialno=Common.get(rs2.getString("serialno"));
				}else{
					System.out.println("缺少財編="+CategoryID+'-'+AssetID);
				}
				SQLo2.close();
				rs2.close();
				
				subinsert+="'"+propertyno+"',"; //propertyno
				subinsert+="'"+serialno+"',"; //serialno
				
				
				subinsert+="'',"; //lotno
				
				String serialno1="";
				serialno1=Common.get(rs.getString("RecID"));
				subinsert+=""+serialno1+",";//serialno1
				
				String deprym="";
				deprym=Common.get(rs.getString("YearMonth"));
				subinsert+="'"+deprym+"',";//deprym
				
				subinsert+="null,";//deprfrequency
			
				subinsert+="'Y',";//verify				
				
				if(propertyno.length()>3){
					if(propertyno.subSequence(0, 3).equals("111")){
						subinsert+="'2',";//propertytype,
					}else{
						if(propertyno.subSequence(0, 1).equals("1")){
							subinsert+="'1',";//propertytype,
						}
						if(propertyno.subSequence(0, 1).equals("2")){
							subinsert+="'3',";//propertytype,
						}
						if(propertyno.subSequence(0, 1).equals("3")){
							subinsert+="'4',";//propertytype,
						}
						if(propertyno.subSequence(0, 1).equals("4")){
							subinsert+="'5',";//propertytype,
						}
						if(propertyno.subSequence(0, 1).equals("5")){
							subinsert+="'6',";//propertytype,
						}
					}					
				}else{
					subinsert+="'',";//propertytype,
				}
				subinsert+="'01',";//propertykind,
				
				
				subinsert+="'"+fundtype+"',";//fundtype,
				subinsert+="'N',";//valuable,
				subinsert+="'02',";//deprmethod,
				subinsert+="'',";//buildfeecb,
				subinsert+="'',";//deprunitcb,
				subinsert+="'',";//deprpark,
				subinsert+="'',";//deprunit,
				subinsert+="'',";//deprunit1,
				subinsert+="'',";//depraccounts,
				
				subinsert+="null,";//deprpercent,
				subinsert+=""+Common.get(rs.getString("AmtSTTL"))+",";//originalbv,
				
				String bookvalue="";
				bookvalue=Common.get(rs.getString("ValCur"));
				subinsert+=""+bookvalue+",";//bookvalue,
				
				String scrapvalue=Common.get(rs.getString("WorkingYears"));
				subinsert+=""+scrapvalue+",";//scrapvalue,

				subinsert+="null,";//depramount,
				subinsert+="null,";//apportionmonth,
				
				String oldnetvalue="";
				oldnetvalue=Integer.toString(Integer.valueOf(Common.get(rs.getString("ValCur")))-Integer.valueOf(Common.get(rs.getString("ValDprTTL_B")))+Integer.valueOf(Common.get(rs.getString("ValDpr_B"))));
				subinsert+=""+oldnetvalue+",";//oldnetvalue,
				
				String oldaccumdepr="";
				oldaccumdepr=Integer.toString(Integer.valueOf(Common.get(rs.getString("ValDprTTL_B")))-Integer.valueOf(Common.get(rs.getString("ValDpr_B"))));
				subinsert+=""+oldaccumdepr+",";//oldaccumdepr,
				
				subinsert+="0,";//addaccumdepr,
				subinsert+="0,";//reduceaccumdepr,
				
				String monthdepr1="";
				monthdepr1=Common.get(rs.getString("ValDpr_B"));
				subinsert+=""+monthdepr1+",";//monthdepr1,
				subinsert+="0,";//monthdepr2,
				
				String monthdepr="";
				monthdepr=Common.get(rs.getString("ValDpr_B"));
				subinsert+=""+monthdepr+",";///monthdepr,
				
				String newaccumdepr="";
				newaccumdepr=Common.get(rs.getString("ValDprTTL_B"));
				subinsert+=""+newaccumdepr+",";//newaccumdepr,
				
				String newnetvalue="";
				newnetvalue=Integer.toString(Integer.valueOf(Common.get(rs.getString("ValCur")))-Integer.valueOf(Common.get(rs.getString("ValDprTTL_B"))));
				
				subinsert+=""+newnetvalue+",";//newnetvalue,
				subinsert+="null,";//scaledbookvalue,
				subinsert+="null,";//scaledoldnetvalue,
				subinsert+="null,";//scaledoldaccumdepr,
				subinsert+="null,";//scaledaddaccumdepr,
				subinsert+="null,";//scaledreduceaccumdepr,
				subinsert+="null,";//scaledmonthdepr1,
				subinsert+="null,";//scaledmonthdepr2,
				subinsert+="null,";//scaledmonthdepr,
				subinsert+="null,";//scalednewaccumdepr,
				subinsert+="null,";//scalednewnetvalue,

				subinsert+="'',";//buydate,
				subinsert+="'',";//enterdate,
				subinsert+="'',";//sourcedate,
				subinsert+="'',";//propertyname1,				
				
				String limityear=Common.get(rs.getString("WorkingYears"));
				subinsert+=""+limityear+",";//limityear,
				
				String bookamount="";
				bookamount=Common.get(rs.getString("Qty"));
				subinsert+=""+bookamount+","; //bookamount
				
				
				subinsert+="'',";//keepunit,
				subinsert+="'',";//keeper,
				
				subinsert+="null,";//escroworilimitmonth,
				subinsert+="null,";//escroworivalue,
				subinsert+="null,";//escroworiaccumdepr,
				
				subinsert+="'',";//engineeringno
	
				subinsert+="'"+Common.get(rs.getString("note"))+"',";//notes
				subinsert+="'GM',";//editid
				subinsert+="'"+Datetime.getYYYYMMDD()+"',";//editdate
				subinsert+="'"+Datetime.getHHMMSS()+"',";//edittime
				
				String oldpropertyno="";
				oldpropertyno=Common.get(rs.getString("CategoryID"));
				subinsert+="'"+oldpropertyno+"',"; //oldpropertyno
				
				String oldserialno="";
				oldserialno=Common.get(rs.getString("AssetID"));
				subinsert+="'"+oldserialno+"' "; //oldserialno
				
				
				Allinsert=insertstr+subinsert+") ;";
				//System.out.println(Allinsert);
				
				try{
					if(insert_write==true){
						if(!CategoryID.equals("") && !AssetID.equals("")){
							String SqlStr3="SELECT TOP 1 * FROM UNTDP_MONTHDEPR WHERE enterorg='"+inputEnterOrg+"' and ownership='1' and differencekind='"+differencekind+"' and propertyno='"+propertyno+"' and serialno='"+serialno+"' and serialno1="+serialno1+" ";
							//System.out.println(SqlStr3);
							SQLo3 = tcghconn.prepareStatement(SqlStr3);
							rs3 = SQLo3.executeQuery();
							if(!rs3.next())
							{
								SQLo = tcghconn.prepareStatement(Allinsert);
								SQLo.execute();
								SQLo.close();
							}
							SQLo3.close();
							rs3.close();
						}
					}
				}catch(Exception e){
					System.out.println(e);
					System.out.println(Allinsert);
				}
				
				showWhatIsNow();
			}

			SQLo.close();
			rs.close();
			smblconn.close();			
			tcghconn.close();
		} catch (Exception e) {
			System.out.println(Allinsert);
			e.printStackTrace();
		}

	}
	
	
	/**
	 * @UNTDP_MONTHDEPR_3 建物用折舊轉入
	 */
	public static void UNTDP_MONTHDEPR_3(String tablename){
		count = 0;
		
		Connection smblconn;
		Connection tcghconn;
		
		PreparedStatement SQLo = null;
		PreparedStatement SQLo1 = null;
		PreparedStatement SQLo2 = null;
		PreparedStatement SQLo3 = null;
		ResultSet rs,rs2,rs3;
		String insertstr="";
		String subinsert="";
		String Allinsert="";
		
		String SqlStr="";
		try {
			
			smblconn = getDBConnection("smblg");
			tcghconn = getDBConnection("TCGH");
			
			if(insert_write==true){
			SQLo = tcghconn.prepareStatement("delete "+tablename+"  where enterorg='"+inputEnterOrg+"' and propertyno=''  ");
			SQLo.execute();
			SQLo.close();
			SQLo = tcghconn.prepareStatement("delete "+tablename+"  where enterorg='"+inputEnterOrg+"' and propertytype = '3'  ");
			SQLo.execute();
			SQLo.close();
			}
			
			//73355
			SqlStr+="select ";
			SqlStr+="isnull(a.acctTagID,'') as acctTagID,isnull(a.AssetID,'') as AssetID,isnull(a.AcctDocID,'') as AcctDocID,a.AcctDate,a.RecDate, "; 
			SqlStr+="a.CategoryID,a.OffAccount,isnull(a.OffAccountDate,'') as OffAccountDate,convert(int,isnull(a.AmtSTTL,0)) as AmtSTTL,convert(int,a.ValDprTTL) as ValDprTTL,a.KeepDivID,a.KeeperID, ";
			SqlStr+="a.RecDate,b.ValOrg,convert(int,b.ValDprTTL) AS ValDprTTL_B,convert(int,b.ValDpr) as ValDpr_B , ";
			SqlStr+="b.YearMonth,convert(int,b.ValCur) as ValCur,   ";
			SqlStr+="b.Qty,b.Note,b.ValKeep,b.AssetType,b.WorkingYears,b.RecID  ";
			SqlStr+="from ASbldgBasic a  ";
			SqlStr+=",ASDprCountDetailAcctChild b where a.AssetID=b.AssetID ";
			//SqlStr+=",ASFluctuateMaster c    ";
//			SqlStr+="where  a.AssetID=b.AssetID  ";
			//SqlStr+="and b.RecID>='72662' ";
			//SqlStr+="and serialno='0000001' ";
			//SqlStr+="where b.DocID is not null "; 
			//SqlStr+="group by a.acctTagID,a.AgtNo,c.RECDate,c.RECDivID,a.AcctDocID,a.AcctDate,a.RecDate,b.note,b.DocID ";
			//SqlStr+=" where a.OffAccountDate is not null ";
			SqlStr+=" and b.YearMonth = '201504' order by convert(int,b.RecID) ";
			System.out.println(SqlStr);
			SQLo = smblconn.prepareStatement(SqlStr);
			
			insertstr+="insert into "+tablename+" ";
			insertstr+="( " +
					"enterorg, ownership, differencekind, propertyno, serialno, lotno, serialno1, deprym, deprfrequency, " +
					"verify, propertytype, propertykind, fundtype, valuable, deprmethod, buildfeecb, deprunitcb, deprpark, " +
					"deprunit, deprunit1, depraccounts, deprpercent, originalbv, bookvalue, scrapvalue, depramount, apportionmonth, " +
					"oldnetvalue, oldaccumdepr, addaccumdepr, reduceaccumdepr, monthdepr1, monthdepr2, monthdepr, newaccumdepr, " +
					"newnetvalue, scaledbookvalue, scaledoldnetvalue, scaledoldaccumdepr, scaledaddaccumdepr, scaledreduceaccumdepr, " +
					"scaledmonthdepr1, scaledmonthdepr2, scaledmonthdepr, scalednewaccumdepr, scalednewnetvalue, buydate, enterdate, " +
					"sourcedate, propertyname1, limityear, bookamount, keepunit, keeper, escroworilimitmonth, escroworivalue, " +
					"escroworiaccumdepr, engineeringno, notes, editid, editdate, edittime, oldpropertyno, oldserialno " +
					") ";			
			insertstr+=" values ( ";
			rs = SQLo.executeQuery();
			while(rs.next())
			{

				subinsert="";
				subinsert+="'" + inputEnterOrg + "',";//enterorg				
				subinsert+="'1',";//ownership
				//財產區分別
				String differencekind="";
				String fundtype="";
				if(Common.get(rs.getString("acctTagID")).equals("1")){ //公務
					differencekind="110";//differencekind
				}else{
					if(Common.get(rs.getString("acctTagID")).equals("2")){ //基金
						differencekind="120";//differencekind
						fundtype="H064000010000";
					}else{
						if(Common.get(rs.getString("acctTagID")).equals("6")){ //代管資產
							differencekind="111";//differencekind
						}else if("A10120000U".equals(inputEnterOrg) || "A102V0000U".equals(inputEnterOrg)){
							differencekind="110";//differencekind
						}else{
							differencekind="";//differencekind
						}
					}
				}
				subinsert+="'"+differencekind+"',";
				
				String AssetID=Common.get(rs.getString("AssetID"));
				String CategoryID=Common.get(rs.getString("CategoryID"));
				String SqlStr2="SELECT TOP 1 * FROM UNTBU_BUILDING WHERE oldserialno='"+AssetID+"' ";
				//System.out.println(SqlStr2);
				SQLo2 = tcghconn.prepareStatement(SqlStr2);
				rs2 = SQLo2.executeQuery();
				String propertyno="";
				String serialno="";
				if(rs2.next())
				{
					propertyno=Common.get(rs2.getString("propertyno"));
					serialno=Common.get(rs2.getString("serialno"));
				}else{
					System.out.println("缺少財編="+CategoryID+'-'+AssetID);
				}
				SQLo2.close();
				rs2.close();
				
				subinsert+="'"+propertyno+"',"; //propertyno
				subinsert+="'"+serialno+"',"; //serialno
				
				
				subinsert+="'',"; //lotno
				
				String serialno1="";
				serialno1=Common.get(rs.getString("RecID"));
				subinsert+=""+serialno1+",";//serialno1
				
				String deprym="";
				deprym=Common.get(rs.getString("YearMonth"));
				subinsert+="'"+deprym+"',";//deprym
				
				subinsert+="null,";//deprfrequency
			
				subinsert+="'Y',";//verify				
				
				if(propertyno.length()>3){
					if(propertyno.subSequence(0, 3).equals("111")){
						subinsert+="'2',";//propertytype,
					}else{
						if(propertyno.subSequence(0, 1).equals("1")){
							subinsert+="'1',";//propertytype,
						}
						if(propertyno.subSequence(0, 1).equals("2")){
							subinsert+="'3',";//propertytype,
						}
						if(propertyno.subSequence(0, 1).equals("3")){
							subinsert+="'4',";//propertytype,
						}
						if(propertyno.subSequence(0, 1).equals("4")){
							subinsert+="'5',";//propertytype,
						}
						if(propertyno.subSequence(0, 1).equals("5")){
							subinsert+="'6',";//propertytype,
						}
					}					
				}else{
					subinsert+="'',";//propertytype,
				}
				subinsert+="'01',";//propertykind,
				
				
				subinsert+="'"+fundtype+"',";//fundtype,
				subinsert+="'N',";//valuable,
				subinsert+="'02',";//deprmethod,
				subinsert+="'',";//buildfeecb,
				subinsert+="'',";//deprunitcb,
				subinsert+="'',";//deprpark,
				subinsert+="'',";//deprunit,
				subinsert+="'',";//deprunit1,
				subinsert+="'',";//depraccounts,
				
				subinsert+="null,";//deprpercent,
				subinsert+=""+Common.get(rs.getString("AmtSTTL"))+",";//originalbv,
				
				String bookvalue="";
				bookvalue=Common.get(rs.getString("ValCur"));
				subinsert+=""+bookvalue+",";//bookvalue,
				
				String scrapvalue=Common.get(rs.getString("WorkingYears"));
				subinsert+=""+scrapvalue+",";//scrapvalue,

				subinsert+="null,";//depramount,
				subinsert+="null,";//apportionmonth,
				
				String oldnetvalue="";
				oldnetvalue=Integer.toString(Integer.valueOf(Common.get(rs.getString("ValCur")))-Integer.valueOf(Common.get(rs.getString("ValDprTTL_B")))+Integer.valueOf(Common.get(rs.getString("ValDpr_B"))));
				subinsert+=""+oldnetvalue+",";//oldnetvalue,
				
				String oldaccumdepr="";
				oldaccumdepr=Integer.toString(Integer.valueOf(Common.get(rs.getString("ValDprTTL_B")))-Integer.valueOf(Common.get(rs.getString("ValDpr_B"))));
				subinsert+=""+oldaccumdepr+",";//oldaccumdepr,
				
				subinsert+="0,";//addaccumdepr,
				subinsert+="0,";//reduceaccumdepr,
				
				String monthdepr1="";
				monthdepr1=Common.get(rs.getString("ValDpr_B"));
				subinsert+=""+monthdepr1+",";//monthdepr1,
				subinsert+="0,";//monthdepr2,
				
				String monthdepr="";
				monthdepr=Common.get(rs.getString("ValDpr_B"));
				subinsert+=""+monthdepr+",";///monthdepr,
				
				String newaccumdepr="";
				newaccumdepr=Common.get(rs.getString("ValDprTTL_B"));
				subinsert+=""+newaccumdepr+",";//newaccumdepr,
				
				String newnetvalue="";
				newnetvalue=Integer.toString(Integer.valueOf(Common.get(rs.getString("ValCur")))-Integer.valueOf(Common.get(rs.getString("ValDprTTL_B"))));
				
				subinsert+=""+newnetvalue+",";//newnetvalue,
				subinsert+="null,";//scaledbookvalue,
				subinsert+="null,";//scaledoldnetvalue,
				subinsert+="null,";//scaledoldaccumdepr,
				subinsert+="null,";//scaledaddaccumdepr,
				subinsert+="null,";//scaledreduceaccumdepr,
				subinsert+="null,";//scaledmonthdepr1,
				subinsert+="null,";//scaledmonthdepr2,
				subinsert+="null,";//scaledmonthdepr,
				subinsert+="null,";//scalednewaccumdepr,
				subinsert+="null,";//scalednewnetvalue,

				subinsert+="'',";//buydate,
				subinsert+="'',";//enterdate,
				subinsert+="'',";//sourcedate,
				subinsert+="'',";//propertyname1,				
				
				String limityear=Common.get(rs.getString("WorkingYears"));
				subinsert+=""+limityear+",";//limityear,
				
				String bookamount="";
				bookamount=Common.get(rs.getString("Qty"));
				subinsert+=""+bookamount+","; //bookamount
				
				
				subinsert+="'',";//keepunit,
				subinsert+="'',";//keeper,
				
				subinsert+="null,";//escroworilimitmonth,
				subinsert+="null,";//escroworivalue,
				subinsert+="null,";//escroworiaccumdepr,
				
				subinsert+="'',";//engineeringno
	
				subinsert+="'"+Common.get(rs.getString("note"))+"',";//notes
				subinsert+="'GM',";//editid
				subinsert+="'"+Datetime.getYYYYMMDD()+"',";//editdate
				subinsert+="'"+Datetime.getHHMMSS()+"',";//edittime
				
				String oldpropertyno="";
				oldpropertyno=Common.get(rs.getString("CategoryID"));
				subinsert+="'"+oldpropertyno+"',"; //oldpropertyno
				
				String oldserialno="";
				oldserialno=Common.get(rs.getString("AssetID"));
				subinsert+="'"+oldserialno+"' "; //oldserialno
				
				
				Allinsert=insertstr+subinsert+") ;";
				//System.out.println(Allinsert);
				
				try{
					if(insert_write==true){
						if(!CategoryID.equals("") && !AssetID.equals("")){
							String SqlStr3="SELECT TOP 1 * FROM UNTDP_MONTHDEPR WHERE enterorg='"+inputEnterOrg+"' and ownership='1' and differencekind='"+differencekind+"' and propertyno='"+propertyno+"' and serialno='"+serialno+"' and serialno1="+serialno1+" ";
							//System.out.println(SqlStr3);
							SQLo3 = tcghconn.prepareStatement(SqlStr3);
							rs3 = SQLo3.executeQuery();
							if(!rs3.next())
							{
								SQLo = tcghconn.prepareStatement(Allinsert);
								SQLo.execute();
								SQLo.close();
							}
							SQLo3.close();
							rs3.close();
						}
					}
				}catch(Exception e){
					System.out.println(e);
					System.out.println(Allinsert);
				}
				
				showWhatIsNow();
			}
			
			//修改折舊檔資料(目前因竹科管局104年1月的報表與資料庫不合)
			UPDATE_UNTDP(tcghconn);
			SQLo.close();
			rs.close();
			smblconn.close();			
			tcghconn.close();
		} catch (Exception e) {
			System.out.println(Allinsert);
			e.printStackTrace();
		}

	}
	
	

	/**
	 * @ASIprvBasic土地改良物財編檢查
	 */
	public static void check_ASIprvBasic(){
		Connection smblconn;
		Connection tcghconn;
		
		PreparedStatement SQLo = null;
		PreparedStatement SQLo1 = null;
		PreparedStatement SQLo2 = null;
		PreparedStatement SQLo3 = null;
		ResultSet rs,rs2,rs3;
		String Allinsert="";
		
		String SqlStr="";
		try {
			
			smblconn = getDBConnection("smblg");
			tcghconn = getDBConnection("TCGH");
			
			SqlStr+="select ";
			SqlStr+="a.CategoryID, "; 
			SqlStr+="a.AssetID ";
			
			SqlStr+="from ASIprvBasic a  ";
			//SqlStr+=",ASDprCountDetailAcctChild b  ";
			//SqlStr+=",ASFluctuateMaster c    ";
			//SqlStr+="where  a.AssetID=b.AssetID  ";
			//SqlStr+="and b.RecID>='105349' ";
			//SqlStr+="and serialno='0000001' ";
			//SqlStr+="where b.DocID is not null "; 
			//SqlStr+="group by a.acctTagID,a.AgtNo,c.RECDate,c.RECDivID,a.AcctDocID,a.AcctDate,a.RecDate,b.note,b.DocID ";
			//SqlStr+=" where a.OffAccountDate is not null ";
			SqlStr+=" order by convert(int,a.RecID) ";
			System.out.println(SqlStr);
			SQLo = smblconn.prepareStatement(SqlStr);
			
			rs = SQLo.executeQuery();
			while(rs.next())
			{
				
				String AssetID=Common.get(rs.getString("AssetID"));
				
				String SqlStr2="SELECT TOP 1 * FROM UNTRF_ATTACHMENT WHERE oldserialno='"+AssetID+"' ";
				//System.out.println(SqlStr2);
				SQLo2 = tcghconn.prepareStatement(SqlStr2);
				rs2 = SQLo2.executeQuery();
				if(!rs2.next())
				{
					System.out.println(rs.getString("CategoryID")+"-"+rs.getString("AssetID"));
				}
				SQLo2.close();
				rs2.close();
				
				
			}

			SQLo.close();
			rs.close();
			smblconn.close();			
			tcghconn.close();
		} catch (Exception e) {
			System.out.println(Allinsert);
			e.printStackTrace();
		}

	}
	/**
	 * @ASbldgBasic建物財編檢查
	 */
	public static void check_ASbldgBasic(){
		Connection smblconn;
		Connection tcghconn;
		
		PreparedStatement SQLo = null;
		PreparedStatement SQLo1 = null;
		PreparedStatement SQLo2 = null;
		PreparedStatement SQLo3 = null;
		ResultSet rs,rs2,rs3;
		String Allinsert="";
		
		String SqlStr="";
		try {
			
			smblconn = getDBConnection("smblg");
			tcghconn = getDBConnection("TCGH");
			
			SqlStr+="select ";
			SqlStr+="a.CategoryID, "; 
			SqlStr+="a.AssetID ";
			//SqlStr+="a.RecDate,b.ValOrg,convert(int,b.ValDprTTL) AS ValDprTTL_B,convert(int,b.ValDpr) as ValDpr_B , ";
			//SqlStr+="b.YearMonth,convert(int,b.ValCur) as ValCur,   ";
			//SqlStr+="b.Qty,b.Note,b.ValKeep,b.AssetType,b.WorkingYears,b.RecID  ";
			SqlStr+="from ASbldgBasic a  ";
			//SqlStr+=",ASDprCountDetailAcctChild b  ";
			//SqlStr+=",ASFluctuateMaster c    ";
			//SqlStr+="where  a.AssetID=b.AssetID  ";
			//SqlStr+="and b.RecID='1001' ";
			//SqlStr+="and serialno='0000001' ";
			//SqlStr+="where b.DocID is not null "; 
			//SqlStr+="group by a.acctTagID,a.AgtNo,c.RECDate,c.RECDivID,a.AcctDocID,a.AcctDate,a.RecDate,b.note,b.DocID ";
			//SqlStr+=" where a.OffAccountDate is not null ";
			SqlStr+=" order by convert(int,a.RecID) ";
			System.out.println(SqlStr);
			SQLo = smblconn.prepareStatement(SqlStr);
			
			rs = SQLo.executeQuery();
			while(rs.next())
			{
				
				String AssetID=Common.get(rs.getString("AssetID"));
				
				String SqlStr2="SELECT TOP 1 * FROM UNTBU_BUILDING WHERE oldserialno='"+AssetID+"' ";
				//System.out.println(SqlStr2);
				SQLo2 = tcghconn.prepareStatement(SqlStr2);
				rs2 = SQLo2.executeQuery();
				if(!rs2.next())
				{
					System.out.println(rs.getString("CategoryID")+"-"+rs.getString("AssetID"));
				}
				SQLo2.close();
				rs2.close();
				
				
			}

			SQLo.close();
			rs.close();
			smblconn.close();			
			tcghconn.close();
		} catch (Exception e) {
			System.out.println(Allinsert);
			e.printStackTrace();
		}

	}
	
	/**
	 * @ASLandBasic土地財編檢查
	 */
	public static void check_ASLandBasic(){
		Connection smblconn;
		Connection tcghconn;
		
		PreparedStatement SQLo = null;
		PreparedStatement SQLo1 = null;
		PreparedStatement SQLo2 = null;
		PreparedStatement SQLo3 = null;
		ResultSet rs,rs2,rs3;
		String Allinsert="";
		
		String SqlStr="";
		try {
			
			smblconn = getDBConnection("smblg");
			tcghconn = getDBConnection("TCGH");
			
			SqlStr+="select ";
			SqlStr+="a.CategoryID, "; 
			SqlStr+="a.AssetID ";
			//SqlStr+="a.RecDate,b.ValOrg,convert(int,b.ValDprTTL) AS ValDprTTL_B,convert(int,b.ValDpr) as ValDpr_B , ";
			//SqlStr+="b.YearMonth,convert(int,b.ValCur) as ValCur,   ";
			//SqlStr+="b.Qty,b.Note,b.ValKeep,b.AssetType,b.WorkingYears,b.RecID  ";
			SqlStr+="from ASLandBasic a  ";
			//SqlStr+=",ASDprCountDetailAcctChild b  ";
			//SqlStr+=",ASFluctuateMaster c    ";
			//SqlStr+="where  a.AssetID=b.AssetID  ";
			//SqlStr+="and b.RecID='1001' ";
			//SqlStr+="and serialno='0000001' ";
			//SqlStr+="where b.DocID is not null "; 
			//SqlStr+="group by a.acctTagID,a.AgtNo,c.RECDate,c.RECDivID,a.AcctDocID,a.AcctDate,a.RecDate,b.note,b.DocID ";
			//SqlStr+=" where a.OffAccountDate is not null ";
			SqlStr+=" order by convert(int,a.RecID) ";
			System.out.println(SqlStr);
			SQLo = smblconn.prepareStatement(SqlStr);
			
			rs = SQLo.executeQuery();
			while(rs.next())
			{
				
				String AssetID=Common.get(rs.getString("AssetID"));
				
				String SqlStr2="SELECT TOP 1 * FROM UNTLA_LAND WHERE oldserialno='"+AssetID+"' ";
				//System.out.println(SqlStr2);
				SQLo2 = tcghconn.prepareStatement(SqlStr2);
				rs2 = SQLo2.executeQuery();
				if(!rs2.next())
				{
					System.out.println(rs.getString("CategoryID")+"-"+rs.getString("AssetID"));
				}
				SQLo2.close();
				rs2.close();
				
				
			}

			SQLo.close();
			rs.close();
			smblconn.close();			
			tcghconn.close();
		} catch (Exception e) {
			System.out.println(Allinsert);
			e.printStackTrace();
		}

	}
	
	/**
	 * @throws SQLException 
	 * @寫入UNT**_ADDPROOF用
	 */
	public static void INSERT_ADDPROOF(Connection conn,String tablename,String caseno,String proofdoc,String proofno,String acctTagID,String AgtNo,String RECDate,String RECDivID,String PostDate,String AcctDocID,String AcctDate,String note,String differencekind,String DocID) throws SQLException{
		PreparedStatement SQLo = null;
		String insertstr="";
		String subinsert="";
		String Allinsert="";
		
		insertstr+="insert into "+tablename+" ";
		insertstr+="( enterorg, ownership, caseno, differencekind, engineeringno, casename,";
		insertstr+="writedate, writeunit, proofyear, proofdoc, proofno, manageno, summonsno ,summonsdate,";
		insertstr+="enterdate, mergedivision, verify, notes, editid, editdate,";
		insertstr+="edittime ) ";			
		insertstr+=" values ( ";
		
		subinsert="";
		subinsert+="'" + inputEnterOrg + "',";//enterorg
		subinsert+="'1',";//ownership				
		subinsert+="'"+caseno+"',";//caseno			
		//財產區分別
		subinsert+="'"+Common.get(differencekind)+"',";//differencekind
		
		if("A27040000G".equals(inputEnterOrg)) {
			subinsert+="'"+getEngineeringnoAdd("Chtl", DocID)+"',";//engineeringno
		} else {
			subinsert+="'"+AgtNo+"',";//engineeringno
		}
		subinsert+="'',";//casename 停用
		if(RECDate.startsWith("1900-01-01")){
			subinsert+="null,";//writedate
		}else{
			subinsert+="'"+RECDate.replaceAll("-", "")+"',";//writedate  
		}
		subinsert+="'"+RECDivID+"',";//writeunit
		subinsert+="'"+changeTaiwanYYMMDD(Common.get(RECDate).replaceAll("-", ""),"1").substring(0,3)+"',";//proofyear
		subinsert+="'"+proofdoc+"',";//proofdoc
		subinsert+="'"+proofno+"',";//proofno
		subinsert+="'',";//manageno 停用
		subinsert+="'"+AcctDocID+"',";//summonsno
		subinsert+="'"+AcctDate.replaceAll("-", "")+"',";//summonsdate 
		subinsert+="'"+PostDate.replaceAll("-", "").substring(0,8)+"',";//enterdate
		subinsert+="'',";//mergedivision 新系統欄位
		subinsert+="'Y',";//verify
		subinsert+="'"+note+"',";//notes
		subinsert+="'GM',";//editid
		subinsert+="'"+Datetime.getYYYYMMDD()+"',";//editdate
		subinsert+="'"+Datetime.getHHMMSS()+"' ";//edittime
		
		
		Allinsert=insertstr+subinsert+") ;";
		//System.out.println(Allinsert);
		//if(!proofno.equals("")){
			try{
				if(insert_write==true){
					SQLo = conn.prepareStatement(Allinsert);
					SQLo.execute();
					SQLo.close();
				}else{
					System.out.println(Allinsert);
				}
			}catch(Exception e){
				System.out.println(e);
				System.out.println(Allinsert);
			}
		//}
	}
	
	
	
	/**
	 * @throws SQLException 
	 * @寫入UNT**_ADJUSTPROOF用 
	 */	
	public static void INSERT_ADJUSTPROOF(Connection conn,String tablename,String caseno,String proofdoc,String proofno,String acctTagID,String AgtNo,String RECDate,String RECDivID,String RecDate,String AcctDocID,String AcctDate,String note,String fluctuateddate,String cfmdate,String docno,String differencekind,String DocID) throws SQLException{
		PreparedStatement SQLo = null;
		String insertstr="";
		String subinsert="";
		String Allinsert="";
		
		insertstr+="insert into "+tablename+" ";
		insertstr+="( enterorg, caseno, ownership,  differencekind, engineeringno, casename,";
		insertstr+="writedate, writeunit, proofyear, proofdoc, proofno, manageno, summonsno ,summonsdate,";
		insertstr+="adjustdate, approveorg, approvedate, approvedoc, verify, notes, editid, editdate,";
		insertstr+="edittime ) ";			
		insertstr+=" values ( ";
		
		subinsert="";
		subinsert+="'" + inputEnterOrg + "',";//enterorg					
		subinsert+="'"+caseno+"',";//caseno	
		subinsert+="'1',";//ownership	
		//財產區分別
		subinsert+="'"+Common.get(differencekind)+"',";//differencekind
		
		if("A27040000G".equals(inputEnterOrg)) {
			subinsert+="'"+getEngineeringnoFluctuate(DocID)+"',";//engineeringno
		} else {
			subinsert+="'"+AgtNo+"',";//engineeringno
		}
		subinsert+="'',";//casename 停用
		if(RECDate.startsWith("1900-01-01")){
			subinsert+="null,";//writedate
		}else{
			subinsert+="'"+RECDate.replaceAll("-", "")+"',";//writedate  
		}
		subinsert+="'"+RECDivID+"',";//writeunit
		subinsert+="'"+changeTaiwanYYMMDD(Common.get(RECDate).replaceAll("-", ""),"1").substring(0,3)+"',";//proofyear
		subinsert+="'"+proofdoc+"',";//proofdoc
		subinsert+="'"+proofno+"',";//proofno
		subinsert+="'',";//manageno 停用
		subinsert+="'"+AcctDocID+"',";//summonsno
		
		if(AcctDate.length()>=8){
			subinsert+="'"+AcctDate.replaceAll("-", "").substring(0, 8)+"',";//summonsdate
		}else{
			subinsert+="'',";
		}
		
		if(fluctuateddate.startsWith("1900-01-01")){
			subinsert+="null,";//adjustdate
		}else{
			subinsert+="'"+fluctuateddate.replaceAll("-", "").substring(0,8)+"',";//adjustdate
		}

		subinsert+="'',";//approveorg
		
		if(cfmdate.length()>=8){
			subinsert+="'"+cfmdate.replaceAll("-", "").substring(0, 8)+"',";//approvedate
		}else{
			subinsert+="'',";
		}
		subinsert+="'"+docno+"',";//approvedoc
		
		if(fluctuateddate.startsWith("1900-01-01")){
			subinsert+="'N',";//verify
		}else{
			subinsert+="'Y',";//verify
		}
		
		subinsert+="'"+note+"',";//notes
		subinsert+="'GM',";//editid
		subinsert+="'"+Datetime.getYYYYMMDD()+"',";//editdate
		subinsert+="'"+Datetime.getHHMMSS()+"' ";//edittime
		
		
		Allinsert=insertstr+subinsert+") ;";
		//System.out.println(Allinsert);
		//if(!proofno.equals("")){
			try{
				if(insert_write==true){
					SQLo = conn.prepareStatement(Allinsert);
					SQLo.execute();
					SQLo.close();
				}else{
					System.out.println(Allinsert);
				}
			}catch(Exception e){
				System.out.println(e);
				System.out.println(Allinsert);
			}
		//}
	}
	
	
	/**
	 * @寫入UNT**_REDUCEPROOF用 
	 */	
	public static void INSERT_REDUCEPROOF(Connection conn,String tablename,String caseno,String proofdoc,String proofno,String acctTagID,String AgtNo,String RECDate,String RECDivID,String RecDate,String AcctDocID,String AcctDate,String note,String cfmdate,String docno,String reduceDate,String differencekind){
		PreparedStatement SQLo = null;
		String insertstr="";
		String subinsert="";
		String Allinsert="";
		
		insertstr+="insert into "+tablename+" ";
		insertstr+="( " +
				"enterorg, caseno, ownership, differencekind, casename, writedate, writeunit, " +
				"proofyear, proofdoc, proofno, manageno, summonsno, reducedate, approveorg, approvedate, " +
				"approvedoc, verify, notes, editid, editdate, edittime, cause, cause1, summonsdate" +
				" ) ";			
		insertstr+=" values ( ";
		
		subinsert="";
		subinsert+="'" + inputEnterOrg + "',";//enterorg
		subinsert+="'"+caseno+"',";//caseno
		subinsert+="'1',";//ownership				
		//財產區分別
		subinsert+="'"+Common.get(differencekind)+"',";//differencekind
		
		
		//subinsert+="'"+AgtNo+"',";//engineeringno
		subinsert+="'',";//casename 停用
		if(RECDate.startsWith("1900-01-01")){
			subinsert+="null,";//writedate
		}else{
			subinsert+="'"+RECDate.replaceAll("-", "")+"',";//writedate  
		} 
		subinsert+="'"+RECDivID+"',";//writeunit
		subinsert+="'"+changeTaiwanYYMMDD(Common.get(RECDate).replaceAll("-", ""),"1").substring(0,3)+"',";//proofyear
		subinsert+="'"+proofdoc+"',";//proofdoc
		subinsert+="'"+proofno+"',";//proofno
		subinsert+="'',";//manageno 停用
		subinsert+="'"+AcctDocID+"',";//summonsno

		if(reduceDate.startsWith("1900-01-01")){
			subinsert+="null,";//reducedate
		}else{
			subinsert+="'"+reduceDate.replaceAll("-", "").substring(0,8)+"',";//reducedate
		}
		
		subinsert+="'',";//approveorg
		
		if(cfmdate.startsWith("1900-01-01")){
			subinsert+="null,";//approvedate
		} else if(cfmdate.length()>=8){
			subinsert+="'"+cfmdate.replaceAll("-", "").substring(0,8)+"',";//approvedate
		}else{
			subinsert+="'',";//approvedate
		}
		subinsert+="'"+docno+"',";//approvedoc
		if(reduceDate.startsWith("1900-01-01")){
			subinsert+="'N',";//verify
		}else{
			subinsert+="'Y',";//verify
		}
		
		subinsert+="'"+docno+"',";//notes
		subinsert+="'GM',";//editid
		subinsert+="'"+Datetime.getYYYYMMDD()+"',";//editdate
		subinsert+="'"+Datetime.getHHMMSS()+"',";//edittime
		subinsert+="'',";//cause
		subinsert+="'',";//cause1
		if(AcctDate.length()>=8){
			subinsert+="'"+AcctDate.replaceAll("-", "").substring(0,8)+"' ";//summonsdate
		}else{
			subinsert+="'' ";//summonsdate
		}
		Allinsert=insertstr+subinsert+") ;";
		//System.out.println(Allinsert);
		//if(!proofno.equals("")){
			try{
				if(insert_write==true){
					SQLo = conn.prepareStatement(Allinsert);
					SQLo.execute();
					SQLo.close();
				}else{
					System.out.println(Allinsert);
				}
			}catch(Exception e){
				System.out.println(e);
				System.out.println(Allinsert);
			}
		//}
	}
	
	
	/**
	 * @寫入UNT**_MOVEPROOF用 
	 */	
	public static void INSERT_MOVEPROOF(Connection conn,String tablename,String caseno,String proofdoc,String proofno ,String RECDate,String RECDivID , String RecDate,String movedate,String note){
		PreparedStatement SQLo = null;
		String insertstr="";
		String subinsert="";
		String Allinsert="";
		
		insertstr+="insert into "+tablename+" ";
		insertstr+="( " +
				"enterorg, caseno, ownership, casename, writedate, writeunit, proofyear, " +
				"proofdoc, proofno, movedate, verify, notes, editid, editdate, edittime " +
				" ) ";			
		insertstr+=" values ( ";
		
		subinsert="";
		subinsert+="'" + inputEnterOrg + "',";//enterorg
		subinsert+="'"+caseno+"',";//caseno
		subinsert+="'1',";//ownership				
		
		subinsert+="'',";//casename 停用
		if(RECDate.startsWith("1900-01-01")){
			subinsert+="null,";//writedate
		}else{
			subinsert+="'"+RECDate.replaceAll("-", "")+"',";//writedate  
		}
		subinsert+="'"+RECDivID+"',";//writeunit
		subinsert+="'"+changeTaiwanYYMMDD(Common.get(RECDate).replaceAll("-", ""),"1").substring(0,3)+"',";//proofyear
		subinsert+="'"+proofdoc+"',";//proofdoc
		subinsert+="'"+proofno+"',";//proofno

		if(movedate.startsWith("1900-01-01")){
			subinsert+="null,";//movedate
			subinsert+="'N',";//verify
		}else{
			subinsert+="'"+movedate.replaceAll("-", "").substring(0,8)+"',";//movedate
			subinsert+="'Y',";//verify
		}

		subinsert+="'"+note+"',";//notes
		subinsert+="'GM',";//editid
		subinsert+="'"+Datetime.getYYYYMMDD()+"',";//editdate
		subinsert+="'"+Datetime.getHHMMSS()+"' ";//edittime

		Allinsert=insertstr+subinsert+") ;";
		//System.out.println(Allinsert);
		//if(!proofno.equals("")){
			try{
				if(insert_write==true){
					SQLo = conn.prepareStatement(Allinsert);
					SQLo.execute();
					SQLo.close();
				}else{
					System.out.println(Allinsert);
				}
			}catch(Exception e){
				System.out.println(e);
				System.out.println(Allinsert);
			}
		//}
	}
	
	/**
	 * @UNTNE_NONEXPDETAIL
	 */
	public static void UNTNE_NONEXPDETAIL(String tablename){
		count = 0;
		Connection smblconn;
		Connection tcghconn;
		
		PreparedStatement SQLo = null;
//		PreparedStatement SQLo1 = null;
		PreparedStatement SQLo2 = null;
		PreparedStatement SQLo3 = null;
		ResultSet rs,rs2,rs3;
		String insertstr="";
		String subinsert="";
		String Allinsert="";
		String tempAssetNo="";
		String proofno="";
		String proofyear="";
		String SqlStr="";

		try {
			//"+inputEnterOrg+" 科技部新竹科學工業園區管理局 
			smblconn = getDBConnection("smblg");
			tcghconn = getDBConnection("TCGH");
			
			if(insert_write==true){
			SQLo = tcghconn.prepareStatement("delete UNTNE_NONEXPDETAIL  where enterorg='"+inputEnterOrg+"' ");
			SQLo.execute();
			SQLo.close();
			SQLo = tcghconn.prepareStatement("delete UNTNE_NONEXP  where enterorg='"+inputEnterOrg+"'  ");
			SQLo.execute();
			SQLo.close();
			SQLo = tcghconn.prepareStatement("delete UNTNE_ADDPROOF  where enterorg='"+inputEnterOrg+"'  ");
			SQLo.execute();
			SQLo.close();
			}
			
			SqlStr+="select  ";
			SqlStr+="a.AssetNo,a.differencekind,b.WorkingYears as originalLimitYear,a.WorkingYears*12 as otherLimitYear,isnull(a.acctTagID,'') as acctTagID,isnull(a.AgtNo,'') as AgtNo,isnull(c.RECDate,'') as RECDate,isnull(c.RECDivID,'') as RECDivID,isnull(a.AcctDocID,'') as AcctDocID,a.AcctDate,a.RecDate,isnull(b.note,'') as note,isnull(b.DocID,'') as DocNo, "; 
			SqlStr+="a.CategoryID,a.OffAccount,isnull(a.OffAccountDate,'') as OffAccountDate,a.Qty,convert(int,a.price) as AmtSTTL,a.PurposeName, ";
			SqlStr+="a.ASUseDivID,a.ASUserID,a.RecDate,a.AssetNO,d.propertyno,d.lotno,d.serialno,d.Assetid,c.postdate,";
			SqlStr+="b.LocationID as originalPlace1,b.LocationName as originalPlace,b.KeepDivID as originalKeepUnit,b.KeeperID as originalKeeper,b.ASUseDivID as originalUseUnit,b.ASUserID as originalUser,b.Price as originalBV,'1' as originalAmount,  ";
			SqlStr+="d.LocationID as place1,d.LocationName as place,d.KeepDivID as keepUnit,d.KeeperID as Keeper,d.ASUseDivID as UseUnit,d.ASUserID as userNo,a.Price as bookValue,'1' as bookAmount ";
			SqlStr+="from ASNXBasic a,ASNXDistribute  d  ";
			SqlStr+="left join  ASNXAddDetail b on d.AssetNo=b.AssetNo "; 
			SqlStr+="left join  ASNXAddMaster c on b.docid=c.docid ";
			SqlStr+="where a.AssetNo=d.AssetNo ";
//			SqlStr+="and b.DocID='11871' "; //中斷接轉條件
			SqlStr+=" order by convert(int,b.DocID) ";
			System.out.println(SqlStr);
			SQLo = smblconn.prepareStatement(SqlStr);
			
			insertstr+="insert into "+tablename+" ";
			insertstr+="( enterorg, ownership, caseno, differencekind, propertyno, lotno, serialno, datastate, reducedate, reducecause, " +
					"reducecause1, verify, enterdate, originalamount, originalbv, bookamount, bookvalue, licenseplate, purpose, " +
					"originalmovedate, originalkeepunit, originalkeeper, originaluseunit, originaluser, originalusernote, " +
					"originalplace1, originalplace, movedate, keepunit, keeper, useunit, userno, usernote, place1, place, notes1," +
					" barcode, notes, oldpropertyno, oldserialno,originallimityear,otherlimityear, editid, editdate, edittime, oldlotno " +
					" ) ";			
			insertstr+=" values ( ";
			rs = SQLo.executeQuery();
			while(rs.next())
			{
				//增加單單號ID
				proofno="";
				proofyear="";
				proofno=Common.formatFrontString(Common.get(rs.getString("DocNo")), 7, '0');
				proofyear=changeTaiwanYYMMDD(Common.get(rs.getDate("RecDate").toString()).replaceAll("-", ""),"1").substring(0,3);

				
				//caseno
				String caseno="";
				String proofdoc="";
				
				SQLo2 = tcghconn.prepareStatement("select caseno from UNTNE_ADDPROOF where proofyear='"+proofyear+"' and proofno='"+proofno+"' and enterorg='"+inputEnterOrg+"' ");				
				rs2 = SQLo2.executeQuery();
				if(rs2.next()) //查詢單號是存在
				{
					caseno=Common.get(rs2.getString("caseno"));
					if(!rs.getString("AssetNo").equals(tempAssetNo)){//此為同樣單號但是會有多筆基本資料，所以要新增一筆
						UNTNE_NONEXP(smblconn,tcghconn,"UNTNE_NONEXP",caseno,rs.getString("AssetNo"));
					}
				}else{         //不存在新建一個增加單單號
					
					SQLo3 = tcghconn.prepareStatement("select right(isnull(max(caseno),'0'),7)+1 as caseno from UNTNE_ADDPROOF where proofyear='"+proofyear+"' and enterorg='"+inputEnterOrg+"' ");				
					rs3 = SQLo3.executeQuery();
					if(rs3.next())
					{
						caseno=changeTaiwanYYMMDD(Common.get(rs.getDate("RecDate").toString()).replaceAll("-", ""),"1").substring(0,3)+Common.formatFrontString(Common.get(rs3.getString("caseno")), 7, '0');
					}
					SQLo3.close();
					proofdoc="";
					
					UNTNE_NONEXP(smblconn,tcghconn,"UNTNE_NONEXP",caseno,rs.getString("AssetNo"));
					INSERT_UNTNE_ADDPROOF(tcghconn,"UNTNE_ADDPROOF", caseno, proofdoc, proofno, Common.get(rs.getString("acctTagID")), Common.get(rs.getString("AgtNo")), Common.get(rs.getDate("RECDate").toString()), Common.get(rs.getString("RECDivID").toString()), Common.get(rs.getDate("RecDate").toString()), Common.get(rs.getString("AcctDocID").toString()), Common.get(rs.getDate("AcctDate").toString()), Common.get(rs.getString("note").toString()),Common.get(rs.getString("differencekind").toString()),Common.get(rs.getDate("Postdate").toString()));
					
				}
				//增加單單號ID
				tempAssetNo=rs.getString("AssetNo");
				SQLo2.close();
				rs2.close();
				subinsert="";
				subinsert+="'"+inputEnterOrg+"',";//enterorg
				subinsert+="'1',";//ownership				
				subinsert+="'"+caseno+"',";//caseno			

				//財產區分別
				subinsert+="'"+Common.get(rs.getString("differencekind"))+"',";//differencekind

				String propertyno=Common.get(rs.getString("propertyno"));
				subinsert+="'"+propertyno+"',"; //propertyno
				
				String lotno=Common.get(rs.getString("lotno"));
				subinsert+="'"+lotno+"',"; //lotno
				
				String serialno=Common.get(rs.getString("serialno"));
				subinsert+="'"+serialno+"',"; //serialno
 
				String datastate="";
				if (Common.get(rs.getString("OffAccount")).equals("1")){ //減損
					datastate="2";
				}else{
					datastate="1";
				}
				subinsert+="'"+datastate+"',"; //datastate
				String reducedate="";
				if ("1".equals(datastate)) {
					reducedate = "";
				} else if(Common.get(rs.getString("OffAccountDate")).length()>0){
					reducedate=Common.get(rs.getString("OffAccountDate")).replaceAll("-", "").substring(0,8);
				}
				subinsert+="'"+reducedate+"',"; //reducedate
				subinsert+="'',"; //reducecause
				subinsert+="'',"; //reducecause1				
				subinsert+="'Y',";//verify
				String enterdate="";
				if(Common.get(rs.getString("postdate")).length()>0){
					enterdate=Common.get(rs.getString("postdate")).replaceAll("-", "").substring(0,8);
				}				
				subinsert+="'"+enterdate+"',"; //enterdate
								
				String originalamount="";
				originalamount=Common.get(rs.getString("originalamount"));
				subinsert+=""+originalamount+","; //originalamount
				
				String originalbv="";
				originalbv=Common.get(rs.getString("originalbv"));				
				subinsert+=""+originalbv+","; //originalbv
				
				String bookamount="";
				if (Common.get(rs.getString("OffAccount")).equals("1")){  //減損
					bookamount="0";
				}else{
					bookamount=Common.get(rs.getString("bookamount"));
				}
				subinsert+=""+bookamount+","; //bookamount
				String bookvalue="";
				if (Common.get(rs.getString("OffAccount")).equals("1")){  //減損
					bookvalue="0";
				}else{
					bookvalue=Common.get(rs.getString("bookvalue"));
				}				
				subinsert+=""+bookvalue+","; //bookvalue
				
				
				subinsert+="'',"; //licenseplate
				String purpose="";
				purpose=Common.get(rs.getString("PurposeName"));
				subinsert+="'"+purpose+"',"; //purpose
				
				String originalmovedate="";
				originalmovedate=Common.get(rs.getString("RecDate")).replaceAll("-", "").substring(0,8);
				subinsert+="'"+originalmovedate+"',"; //originalmovedate

				
				subinsert+="'"+Common.get(rs.getString("originalkeepunit"))+"',"; //originalkeepunit
				
				
				subinsert+="'"+transKeeperNo(Common.get(rs.getString("originalkeeper")))+"',"; //originalkeeper
				
				String originaluseunit="";
				originaluseunit=Common.get(rs.getString("originaluseunit"));
				subinsert+="'"+originaluseunit+"',"; //originaluseunit
				
				String originaluser="";
				originaluser=transKeeperNo(Common.get(rs.getString("originaluser")));
				subinsert+="'"+originaluser+"',"; //originaluser
				
				subinsert+="'',"; //originalusernote
				subinsert+="'"+getPlaceno(Common.get(rs.getString("originalplace1")), Common.get(rs.getString("originalplace")))+"',"; //originalplace1
				subinsert+="'"+Common.get(rs.getString("originalplace"))+"',"; //originalplace
				subinsert+="'',"; //movedate
				subinsert+="'"+Common.get(rs.getString("keepunit"))+"',"; //keepunit
				subinsert+="'"+transKeeperNo(Common.get(rs.getString("keeper")))+"',"; //keeper
				subinsert+="'"+Common.get(rs.getString("useunit"))+"',"; //useunit
				subinsert+="'"+transKeeperNo(Common.get(rs.getString("userno")))+"',"; //userno
				subinsert+="'',"; //usernote
				subinsert+="'"+getPlaceno(Common.get(rs.getString("place1")),Common.get(rs.getString("place")))+"',";//place1,
				subinsert+="'"+Common.get(rs.getString("place"))+"',";//place,
				subinsert+="'',"; //notes1
				
				String barcode="";
				//※	條碼編碼規則：由「財產區分別-財產編號-財產分號」
				barcode=Common.get(rs.getString("differencekind"))+"-"+propertyno+"-"+serialno;
				subinsert+="'"+barcode+"',"; //barcode
				
				subinsert+="'"+Common.get(rs.getString("note"))+"',";//notes
				
				String oldpropertyno="";
				oldpropertyno=Common.get(rs.getString("CategoryID"));
				subinsert+="'"+oldpropertyno+"',"; //oldpropertyno
				
				String oldserialno="";
				oldserialno=Common.get(rs.getString("AssetID"));
				subinsert+="'"+oldserialno+"',"; //oldserialno

				subinsert+="'"+Common.get(rs.getString("originallimityear"))+"',";//originallimityear
				subinsert+="'"+Common.get(rs.getString("otherlimityear"))+"',";//otherlimityear
				
				
				
				subinsert+="'GM',";//editid
				subinsert+="'"+Datetime.getYYYYMMDD()+"',";//editdate
				subinsert+="'"+Datetime.getHHMMSS()+"',";//edittime
				subinsert+="'"+Common.get(rs.getString("AssetNo"))+"' ";//oldlotno
				
				Allinsert=insertstr+subinsert+") ;";
				
				if(insert_write==false){
					System.out.println(Allinsert);
				}
				//if(!proofno.equals("")){
					try{
						if(insert_write==true){
							SQLo = tcghconn.prepareStatement(Allinsert);
							SQLo.execute();
							SQLo.close();
						}else{
							System.out.println(Allinsert);
						}
					}catch(Exception e){
						System.out.println(e);
						System.out.println(Allinsert);
					}
				//}
				showWhatIsNow();
			}
			UPDATE_UNTNE_NONEXP_serialnoSE(tcghconn);//修改動產基本資料的的流水號起訖
			SQLo.close();
			rs.close();
			smblconn.close();			
			tcghconn.close();
		} catch (Exception e) {
			System.out.println(Allinsert);
			e.printStackTrace();
		}

	}
	
	/**
	 * @UNTNE_NONEXP
	 */
	public static void UNTNE_NONEXP(Connection smblconn,Connection tcghconn,String tablename,String caseno,String AssetNo){
		
		//Connection smblconn;
		//Connection tcghconn;
		
		PreparedStatement SQLo = null;
//		PreparedStatement SQLo1 = null;
		PreparedStatement SQLo2 = null;
//		PreparedStatement SQLo3 = null;
		ResultSet rs,rs2;
		String insertstr="";
		String subinsert="";
		String Allinsert="";
		String caseserialno="";
		String proofno="";
		String proofyear="";
		String SqlStr="";
		try {
			//"+inputEnterOrg+" 科技部新竹科學工業園區管理局 
			//smblconn = getDBConnection("smblg");
			//tcghconn = getDBConnection("TCGH");
			
			
			SqlStr+="select  ";
			SqlStr+="b.WorkingYears as originalLimitYear,a.WorkingYears*12 as otherLimitYear,isnull(a.acctTagID,'') as acctTagID,isnull(a.AgtNo,'') as AgtNo,a.RecDate,b.note,isnull(b.DocID,'') as DocNo, "; 
			SqlStr+="a.CategoryID, ";
			SqlStr+="d.propertyno,b.DocID,max(d.serialno) as serialno_max,min(d.serialno) as serialno_min,  ";
			SqlStr+="max(d.lotno) as lotno, max(b.brand) as brand,max(b.spec) as spec, b.RefType, ";
			SqlStr+="max(d.assetid) as assetid_max,min(d.assetid) as assetid_min,c.docno, b.acctname, ";
			SqlStr+="a.differencekind,b.qty as originalAmount,b.price as originalUnit,convert(int,a.AmtSTTL) as originalBV,b.Note as originalNote,b.AssetName as propertyName1,b.AssetName,a.VenderName,";
			SqlStr+="a.Qty as bookAmount,a.AmTsttl as bookValue,a.expname as fundsSource,a.buydate,c.postdate,b.rcvdate as sourcedate,a.assetno,b.unit,a.matter ";
			SqlStr+="from ASNXBasic a,ASNXDistribute d  ";
//			SqlStr+="left join  ASNXAddDetail b on d.AssetNo=b.AssetNo and d.AssetID=b.AssetID "; 
			SqlStr+="left join  ASNXAddDetail b on d.AssetNo=b.AssetNo "; 
			SqlStr+="left join  ASNXAddMaster c on b.docid=c.docid ";
			SqlStr+="where a.AssetNo=d.AssetNo and  b.AssetNo='"+AssetNo+"'";
			//SqlStr+="where (b.DocID is not null  or b.DocID='') "; 
			
			SqlStr+="group by a.acctTagID,a.AgtNo,a.RecDate,b.note,b.DocID,b.RefType, ";  
			SqlStr+=" a.CategoryID,a.AmtSTTL,a.PurposeName,a.buydate,c.postdate,b.rcvdate, ";
			SqlStr+=" b.WorkingYears,a.WorkingYears,a.Qty,a.AmTsttl,a.expname,a.differencekind, ";
			SqlStr+=" b.qty,b.price,b.Note,b.AssetName,a.VenderName,b.acctname,d.propertyno,b.DocID,c.docno,a.assetno,b.unit,a.matter ";
			 
			SqlStr+=" order by convert(int,b.DocID) ";
			//System.out.println(SqlStr);
			SQLo = smblconn.prepareStatement(SqlStr);
			
			insertstr+="insert into "+tablename+" ";
			insertstr+="( enterorg, ownership, caseno, differencekind, caseserialno, propertyno, lotno, serialnos, serialnoe, " +
					"otherpropertyunit, othermaterial,originallimityear ,otherlimityear, propertyname1, cause, cause1, approvedate, approvedoc, " +
					"enterdate, buydate, datastate, verify, propertykind, fundtype, valuable, originalamount, originalunit, " +
					"originalbv, originalnote, accountingtitle, bookamount, bookvalue, fundssource, fundssource1, grantvalue, " +
					"articlename, nameplate, specification, storeno, sourcekind, sourcedate, sourcedoc, oldpropertyno, " +
					"oldserialnos, oldserialnoe, notes, editid, editdate, edittime, oldlotno  ) ";			
			insertstr+=" values ( ";
			rs = SQLo.executeQuery();
			
			int cnt = 0;
			
			while(rs.next())
			{
				cnt++;
				if(cnt > 1) {
					System.out.println("cnt=" + cnt);
				}
				//增加單單號ID
				proofno="";
				proofyear="";
				proofno=Common.formatFrontString(Common.get(rs.getString("DocNo")), 7, '0');
				proofyear=changeTaiwanYYMMDD(Common.get(rs.getDate("RecDate").toString()).replaceAll("-", ""),"1").substring(0,3);

				String proofdoc="";
				SQLo2 = tcghconn.prepareStatement("select isnull(max(caseserialno)+1,'1') as caseserialno  from UNTNE_NONEXP where caseno='"+caseno+"' and enterorg='" + inputEnterOrg + "' ");				
				rs2 = SQLo2.executeQuery();
/*				SQLo2 = tcghconn.prepareStatement("select caseno from UNTNE_NONEXP where caseno='"+changeTaiwanYYMMDD(Common.get(rs.getDate("RecDate").toString()).replaceAll("-", ""),"1").substring(0,3)+proofno+"' and enterorg='"+inputEnterOrg+"' ");				
				rs2 = SQLo2.executeQuery();
				//if(rs2.next()) //查詢單號是存在
				//{
				//	caseno=Common.get(rs2.getString("caseno").toString());
				//}else{         //不存在新建一個增加單單號
				if(!rs2.next()){
					SQLo3 = tcghconn.prepareStatement("select right(isnull(max(substring(caseno,4,7))+1,'1'),7) as caseno from UNTNE_NONEXP where substring(caseno,1,3)='"+changeTaiwanYYMMDD(Common.get(rs.getDate("RecDate").toString()).replaceAll("-", ""),"1").substring(0,3)+"' and enterorg='"+inputEnterOrg+"' ");				
					rs3 = SQLo3.executeQuery();
					if(rs3.next())
					{
						caseno=changeTaiwanYYMMDD(Common.get(rs.getDate("RecDate").toString()).replaceAll("-", ""),"1").substring(0,3)+Common.formatFrontString(Common.get(rs3.getString("caseno")), 7, '0');
					}
					SQLo3.close();
					proofdoc="";
	*/				
				
				if(rs2.next()){
					caseserialno=rs2.getString("caseserialno");
				}
				SQLo2.close();
				rs2.close();
				subinsert="";
				subinsert+="'"+inputEnterOrg+"',";//enterorg
				subinsert+="'1',";//ownership				
				subinsert+="'"+caseno+"',";//caseno			
				//財產區分別
				subinsert+="'"+Common.get(rs.getString("differencekind"))+"',";//differencekind
				
				subinsert+="'"+caseserialno+"',";//caseserialno
				
				String propertyno=Common.get(rs.getString("propertyno"));
				subinsert+="'"+propertyno+"',"; //propertyno
				
				String lotno=Common.get(rs.getString("lotno"));
				subinsert+="'"+lotno+"',"; //lotno
				
				String serialnos=Common.get(rs.getString("serialno_min"));			
				subinsert+="'"+serialnos+"',"; //serialnos
				String serialnoe=Common.get(rs.getString("serialno_max"));				
				subinsert+="'"+serialnoe+"',"; //serialnoe
				
				subinsert+="'"+Common.get(rs.getString("unit"))+"',";//otherpropertyunit
				subinsert+="'"+Common.get(rs.getString("matter"))+"',";//othermaterial
				subinsert+="'"+Common.get(rs.getString("originallimityear"))+"',";//originallimityear
				subinsert+="'"+Common.get(rs.getString("otherlimityear"))+"',";//otherlimityear
				subinsert+="N'"+Common.get(rs.getString("propertyname1"))+"',";//propertyname1,
				subinsert+="'',";//cause,
				subinsert+="'',";//cause1,
				subinsert+="'',";//approvedate,
				subinsert+="'',";//approvedoc,
				String enterdate="";
				if(Common.get(rs.getString("postdate")).length()>0){
					enterdate=Common.get(rs.getString("postdate")).replaceAll("-", "").substring(0,8);
				}				
				subinsert+="'"+enterdate+"',"; //enterdate
				
				String buydate="";
				if(Common.get(rs.getString("buydate")).length()>0){
					buydate=Common.get(rs.getString("buydate")).replaceAll("-", "").substring(0,8);
				}				
				subinsert+="'"+buydate+"',"; //buydate
				
				
				
				
				String datastate="1";
//				if (Common.get(rs.getString("OffAccount")).equals("1")){ //減損
//					datastate="2";
//				}else{
//					datastate="1";
//				}
				subinsert+="'"+datastate+"',"; //datastate				
				subinsert+="'Y',";//verify
				
				subinsert+="'01',";//propertykind
				subinsert+="'',";//fundtype,
				subinsert+="'N',";//valuable,
								
				String originalamount="";
				originalamount=Common.get(rs.getString("originalamount"));
				subinsert+=""+originalamount+","; //originalamount
				
				String originalunit="";
				originalunit=Common.get(rs.getString("originalunit"));				
				subinsert+=""+originalunit+",";//originalunit				
				
				String originalbv="";
				originalbv=Common.get(rs.getString("originalbv"));				
				subinsert+=""+originalbv+","; //originalbv
				
				subinsert+="'',";//originalnote
				String accountingtitle="";
				accountingtitle=Common.get(rs.getString("AcctName"));
				subinsert+="'"+accountingtitle+"',";//accountingtitle  
				
				String bookamount="";
//				if (Common.get(rs.getString("OffAccount")).equals("1")){  //減損
//					bookamount="0";
//				}else{
					bookamount=Common.get(rs.getString("bookamount"));
//				}
				subinsert+=""+bookamount+","; //bookamount
				String bookvalue="";
//				if (Common.get(rs.getString("OffAccount")).equals("1")){  //減損
//					bookvalue="0";
//				}else{
					bookvalue=Common.get(rs.getString("bookvalue"));
//				}				
				subinsert+=""+bookvalue+","; //bookvalue
				
				SQLo2 = tcghconn.prepareStatement("select codeid from SYSCA_CODE where codekindid = 'FSO' AND codename = '" + Common.get(rs.getString("fundssource")) + "'");
				rs2 = SQLo2.executeQuery();
				if(rs2.next()){
					subinsert+="'"+Common.get(rs2.getString("codeid"))+"',";//fundssource,
					subinsert+="'',";//fundssource1,
				} else {
					subinsert+="'',";//fundssource,
					subinsert+="'"+Common.get(rs.getString("fundssource"))+"',";//fundssource1,
				}
				SQLo2.close();
				
				subinsert+="null,";//grantvalue,
				subinsert+="'"+Common.get(rs.getString("AssetName"))+"',";//articlename,
				subinsert+="'"+Common.get(rs.getString("brand"))+"',";//nameplate,
				subinsert+="'"+Common.get(rs.getString("spec")).replace("\'", "")+"',";//specification,
				//VenderName
				SQLo2 = tcghconn.prepareStatement("select storeno from UNTMP_STORE where storename = '" + Common.get(rs.getString("vendername")) + "'");
				rs2 = SQLo2.executeQuery();
				if(rs2.next()){
					subinsert+="'"+Common.get(rs2.getString("storeno"))+"',";//storeno,
				} else {
					subinsert+="'',";//storeno,
				}
				SQLo2.close();
				
				SQLo2 = tcghconn.prepareStatement("select codeid from SYSCA_CODE where codekindid = 'SKD' AND codename = '" + Common.get(rs.getString("RefType")) + "'");
				rs2 = SQLo2.executeQuery();
				if(rs2.next()){
					subinsert+="'"+Common.get(rs2.getString("codeid"))+"',";//sourcekind,
				} else {
					subinsert+="'99',";//sourcekind,
				}
//				subinsert+="'',";//sourcekind,
				String sourcedate="";
				if(Common.get(rs.getString("sourcedate")).length()>0){
					sourcedate=Common.get(rs.getString("sourcedate")).replaceAll("-", "").substring(0,8);
				}				
				subinsert+="'"+sourcedate+"',";//sourcedate,
				subinsert+="'',";//sourcedoc,
				
				
				
				String oldpropertyno="";
				oldpropertyno=Common.get(rs.getString("CategoryID"));
				subinsert+="'"+oldpropertyno+"',"; //oldpropertyno
				
				String oldserialnos="";
				oldserialnos=Common.get(rs.getString("assetid_min"));
				subinsert+="'"+oldserialnos+"',"; //oldserialnos
				
				String oldserialnoe="";
				oldserialnoe=Common.get(rs.getString("assetid_max"));
				subinsert+="'"+oldserialnoe+"',"; //oldserialnoe

				
				String note=Common.get(rs.getString("note"));
				if(!Common.get(rs.getString("docno")).equals("")){
					note=note+"字號："+Common.get(rs.getString("docno"));
				}
				subinsert+="'"+note+"',";//notes 
				subinsert+="'GM',";//editid
				subinsert+="'"+Datetime.getYYYYMMDD()+"',";//editdate
				subinsert+="'"+Datetime.getHHMMSS()+"',";//edittime
				subinsert+="'"+Common.get(rs.getString("AssetNo"))+"' ";//oldlotno

				
				
				Allinsert=insertstr+subinsert+") ;";
				if(insert_write==false){
					System.out.println(Allinsert);
				}
				//if(!proofno.equals("")){
					try{
						if(insert_write==true){
							SQLo = tcghconn.prepareStatement(Allinsert);
							SQLo.execute();
							SQLo.close();
						}else{
							System.out.println(Allinsert);
						}
					}catch(Exception e){
						System.out.println(e);
						System.out.println(Allinsert);
					}
				//}
				showWhatIsNow();
			}
			if(cnt == 0) {
				System.out.println("cnt=" + cnt);
			}
				//單單號ID
				//SQLo2.close();
				//rs2.close();
			//}

			SQLo.close();
			rs.close();
			//smblconn.close();			
			//tcghconn.close();
		} catch (Exception e) {
			System.out.println(Allinsert);
			e.printStackTrace();
		}

	}
	

	/**
	 * @UNTNE_ADJUSTDETAIL
	 */
	public static void UNTNE_ADJUSTDETAIL(String tablename){
		count = 0;
		Connection smblconn;
		Connection tcghconn;
		
		PreparedStatement SQLo = null;
		PreparedStatement SQLo1 = null;
		PreparedStatement SQLo2 = null;
		PreparedStatement SQLo3 = null;
		ResultSet rs,rs2,rs3;
		String insertstr="";
		String subinsert="";
		String Allinsert="";
		
		String proofno="";
		String proofyear="";
		String SqlStr="";
		try {
			//"+inputEnterOrg+" 科技部新竹科學工業園區管理局 
			smblconn = getDBConnection("smblg");
			tcghconn = getDBConnection("TCGH");
			
			if(insert_write==true){
			SQLo = tcghconn.prepareStatement("delete UNTNE_ADJUSTDETAIL  where enterorg='"+inputEnterOrg+"' ");
			SQLo.execute();
			SQLo.close();
			
			SQLo = tcghconn.prepareStatement("delete UNTNE_ADJUSTPROOF  where enterorg='"+inputEnterOrg+"' ");
			SQLo.execute();
			SQLo.close();
			}
			
			
			SqlStr+="select   ";
			SqlStr+="isnull(a.acctTagID,'') as acctTagID,isnull(a.AgtNo,'') as AgtNo,c.RECDate,c.RECDivID,isnull(a.AcctDocID,'') as AcctDocID,a.AcctDate,a.RecDate,b.note,isnull(b.DocID,'') as DocNo, "; 
			SqlStr+="a.CategoryID,a.OffAccount,isnull(a.AssetNO,'') as AssetNO,isnull(a.OffAccountDate,'') as OffAccountDate,isnull(c.FluctuatedDate,'') as FluctuatedDate,b.Qty,convert(int,a.AmtSTTL) as AmtSTTL, ";
			SqlStr+="a.RecDate,d.propertyno,d.lotno,d.serialno,b.ValOrg,b.ValAdd,b.ValSub,b.ValDprTTL,d.AssetID, ";
			SqlStr+="b.ValCur,isnull(c.docno,'') as  DocNotwo,a.assetno,a.unit,a.matter   ";
			SqlStr+="from ASNXBasic a, ASNXDistribute  d  ";
			SqlStr+=",ASNXFluctuateDetail b  ";
			SqlStr+=",ASNXFluctuateMaster c    ";
			SqlStr+="where  a.AssetNO=b.AssetNO and b.docid=c.docid and a.AssetNO=d.AssetNO";
			SqlStr+=" and c.void = '0' ";
			//SqlStr+="and propertyno='30120050011' ";
			//SqlStr+="and serialno='0000001' ";
			//SqlStr+="where b.DocID is not null "; 
			//SqlStr+="group by a.acctTagID,a.AgtNo,c.RECDate,c.RECDivID,a.AcctDocID,a.AcctDate,a.RecDate,b.note,b.DocID ";
			//SqlStr+=" where a.OffAccountDate is not null ";
			
			SqlStr+=" order by convert(int,b.DocID) ";
			System.out.println(SqlStr);
			SQLo = smblconn.prepareStatement(SqlStr);
			
			insertstr+="insert into "+tablename+" ";
			insertstr+="( " +
					"enterorg, caseno, ownership, differencekind, caseserialno, propertyno, lotno, serialno, " +
					"otherpropertyunit, othermaterial, otherlimityear, propertyname1, cause, cause1, adjustdate, " +
					"verify, propertykind, fundtype, valuable, sourcedate, buydate, bookamount, oldbookunit, " +
					"oldbookvalue, addbookunit, addbookvalue, reducebookunit, reducebookvalue, newbookunit, " +
					"newbookvalue, booknotes, keepunit, keeper, useunit, userno, usernote, place1, place, notes, " +
					"oldpropertyno, oldserialno, editid, editdate, edittime, oldlotno " +
					") ";			
			insertstr+=" values ( ";
			rs = SQLo.executeQuery();
			while(rs.next())
			{
				//增減值單單號ID
				proofno="";
				proofyear="";
				proofno=Common.formatFrontString(Common.get(rs.getString("DocNo")), 7, '0');
				proofyear=changeTaiwanYYMMDD(Common.get(rs.getDate("RecDate").toString()).replaceAll("-", ""),"1").substring(0,3);

				//System.out.println(rs.getString("propertyno"));
				//System.out.println(rs.getString("serialno"));
				//caseno
				String caseno="";
				String proofdoc="";			    
				SQLo2 = tcghconn.prepareStatement("select caseno from UNTNE_ADJUSTPROOF where proofyear='"+proofyear+"' and proofno='"+proofno+"' and enterorg='"+inputEnterOrg+"' ");				
				rs2 = SQLo2.executeQuery();
				if(rs2.next()) //查詢單號是存在
				{
					caseno=Common.get(rs2.getString("caseno").toString());
				}else{         //不存在新建一個增加單單號
					
					SQLo3 = tcghconn.prepareStatement("select right(isnull(max(caseno),'0'),7)+1 as caseno from UNTNE_ADJUSTPROOF  where proofyear='"+proofyear+"' and enterorg='"+inputEnterOrg+"' ");				
					rs3 = SQLo3.executeQuery();
					if(rs3.next())
					{
						caseno=changeTaiwanYYMMDD(Common.get(rs.getDate("RecDate").toString()).replaceAll("-", ""),"1").substring(0,3)+Common.formatFrontString(Common.get(rs3.getString("caseno")), 7, '0');
					}
					SQLo3.close();
					proofdoc="非耗品增減";
					

					INSERT_UNTNE_ADJUSTPROOF(tcghconn,"UNTNE_ADJUSTPROOF", caseno, proofdoc, proofno, Common.get(rs.getString("acctTagID")), Common.get(rs.getString("AgtNo")), Common.get(rs.getDate("RECDate").toString()), Common.get(rs.getString("RECDivID").toString()), Common.get(rs.getDate("RecDate").toString()), Common.get(rs.getString("AcctDocID").toString()), Common.get(rs.getDate("AcctDate").toString()), Common.get(rs.getString("note").toString()), Common.get(rs.getString("fluctuateddate").toString()), Common.get(rs.getString("cfmdate").toString()), Common.get(rs.getString("DocNotwo").toString()));
					
					
				}
				//增加單單號ID
				SQLo2.close();
				rs2.close();
				subinsert="";
				subinsert+="'"+inputEnterOrg+"',";//enterorg			
				subinsert+="'"+caseno+"',";//caseno	
				subinsert+="'1',";//ownership
				//財產區分別
				String differencekind="";
				if(Common.get(rs.getString("acctTagID")).equals("1")){ //公務
					differencekind="110";//differencekind
				}else{
					if(Common.get(rs.getString("acctTagID")).equals("2")){ //基金
						differencekind="120";//differencekind
					}else{
							differencekind="500";//differencekind
					}
				}

				subinsert+="'"+differencekind+"',";
				
				subinsert+="1,";//caseserialno
				String propertyno=Common.get(rs.getString("propertyno"));
				subinsert+="'"+propertyno+"',"; //propertyno
				
				String lotno=Common.get(rs.getString("lotno"));
				subinsert+="'"+lotno+"',"; //lotno
				
				String serialno=Common.get(rs.getString("serialno"));
				subinsert+="'"+serialno+"',"; //serialno
				
				subinsert+="'"+Common.get(rs.getString("unit"))+"',";//otherpropertyunit
				subinsert+="'"+Common.get(rs.getString("matter"))+"',";//othermaterial
				subinsert+="null,";//otherlimityear
				subinsert+="'',";//propertyname1,
				subinsert+="'99',";//cause,
				subinsert+="'',";//cause1,
				
				String adjustdate="";
				if(Common.get(rs.getString("FluctuatedDate")).length()>0){
					adjustdate=Common.get(rs.getString("FluctuatedDate")).replaceAll("-", "").substring(0,8);
				}				
				subinsert+="'"+adjustdate+"',"; //adjustdate
			
				subinsert+="'Y',";//verify
				
				subinsert+="'01',";//propertykind
				subinsert+="'',";//fundtype,
				subinsert+="'N',";//valuable,
				
				String sourcedate="";
				if(Common.get(rs.getString("FluctuatedDate")).length()>0){
					sourcedate=Common.get(rs.getString("FluctuatedDate")).replaceAll("-", "").substring(0,8);
				}				
				subinsert+="'"+sourcedate+"',"; //sourcedate
				
				String buydate="";
				if(Common.get(rs.getString("FluctuatedDate")).length()>0){
					buydate=Common.get(rs.getString("FluctuatedDate")).replaceAll("-", "").substring(0,8);
				}				
				subinsert+="'"+buydate+"',"; //buydate
				
				String bookamount="";
				bookamount=Common.get(rs.getString("Qty"));
				subinsert+=""+bookamount+","; //bookamount
				
				
				String oldBookUnit="";
				oldBookUnit=Common.get(rs.getString("ValOrg"));
				subinsert+=""+oldBookUnit+","; //oldBookUnit
				
				String oldbookvalue="";
				oldbookvalue=Common.get(rs.getString("ValOrg"));
				subinsert+=""+oldbookvalue+","; //oldbookvalue
							
				
				String addbookunit="";
				addbookunit=Common.get(rs.getString("ValAdd"));
				subinsert+=""+addbookunit+","; //addbookunit	
				
				String addbookvalue="";
				addbookvalue=Common.get(rs.getString("ValAdd"));
				subinsert+=""+addbookvalue+","; //addbookvalue	
				
				
				String reducebookunit="";
				reducebookunit=Common.get(rs.getString("ValSub"));
				subinsert+=""+reducebookunit+","; //reducebookunit	
				
				String reducebookvalue="";
				reducebookvalue=Common.get(rs.getString("ValSub"));
				subinsert+=""+reducebookvalue+","; //reducebookvalue	
				
				
				String newbookunit="";
				newbookunit=Common.get(rs.getString("ValCur"));
				subinsert+=""+newbookunit+","; //newbookunit
				
				String newbookvalue="";
				newbookvalue=Common.get(rs.getString("ValCur"));
				subinsert+=""+newbookvalue+","; //newbookvalue
				
				
				subinsert+="'',"; //booknotes,
				subinsert+="'',"; //keepunit,
				subinsert+="'',"; //keeper,
				subinsert+="'',"; //useunit,
				subinsert+="'',"; //userno,
				subinsert+="'',"; //usernote,
				subinsert+="'',";//place1,
				subinsert+="'',";//place,
								
				subinsert+="'"+Common.get(rs.getString("note"))+"',";//notes
				
				String oldpropertyno="";
				oldpropertyno=Common.get(rs.getString("CategoryID"));
				subinsert+="'"+oldpropertyno+"',"; //oldpropertyno
				
				String oldserialno="";
				oldserialno=Common.get(rs.getString("AssetID"));
				subinsert+="'"+oldserialno+"',"; //oldserialno

				
				
				subinsert+="'GM',";//editid
				subinsert+="'"+Datetime.getYYYYMMDD()+"',";//editdate
				subinsert+="'"+Datetime.getHHMMSS()+"',";//edittime
				subinsert+="'"+Common.get(rs.getString("AssetNo"))+"' ";//oldlotno
				
				Allinsert=insertstr+subinsert+") ;";
				if(insert_write==false){
					System.out.println(Allinsert);
				}
				//if(!proofno.equals("")){
					try{
						if(insert_write==true){
							SQLo = tcghconn.prepareStatement(Allinsert);
							SQLo.execute();
							SQLo.close();
						}else{
							System.out.println(Allinsert);
						}
					}catch(Exception e){
						System.out.println(e);
						System.out.println(Allinsert);
					}
				//}
				showWhatIsNow();
			}

			SQLo.close();
			rs.close();
			smblconn.close();			
			tcghconn.close();
		} catch (Exception e) {
			System.out.println(Allinsert);
			e.printStackTrace();
		}

	}
	
	public static void UNTNE_REDUCEDETAIL(String tablename){
		count = 0;
		
		Connection smblconn;
		Connection tcghconn;
		
		PreparedStatement SQLo = null;
//		PreparedStatement SQLo1 = null;
		PreparedStatement SQLo2 = null;
		PreparedStatement SQLo3 = null;
//		PreparedStatement SQLo4 = null;
		ResultSet rs,rs2,rs3;
		String insertstr="";
		String subinsert="";
		String Allinsert="";
		
		String proofno="";
		String proofyear="";
//		String summonsno="";
//		String summonsdate="";
		String SqlStr="";
		try {
			//A27040000G 科技部新竹科學工業園區管理局 
			smblconn = getDBConnection("smblg");
			tcghconn = getDBConnection("TCGH");
			
			if(insert_write==true){
				SQLo = tcghconn.prepareStatement("delete UNTNE_REDUCEDETAIL  where enterorg='"+inputEnterOrg+"' ");
				SQLo.execute();
				SQLo.close();
				
				SQLo = tcghconn.prepareStatement("delete UNTNE_REDUCEPROOF  where enterorg='"+inputEnterOrg+"' ");
				SQLo.execute();
				SQLo.close();
				
			}
			
			SqlStr+="select distinct ";			
			SqlStr+="isnull(a.acctTagID,'') as acctTagID,isnull(a.AgtNo,'') as AgtNo,c.RECDate ,d.keepdivid,isnull(b.AcctDocID,'') as AcctDocID,b.AcctDate,a.PostDate,b.note,";
			if( inputEnterOrg == "A10120000U"){
				SqlStr+=" isnull(c.DetractRefDocID,'') as DocNo, ";
			}else{
				SqlStr+=" isnull(c.DocID,'') as DocNo, ";
			}

			SqlStr+="a.CategoryID,a.OffAccount,a.OffAccountDate,b.Qty,convert(int,c.AmtTTL) as AmtSTTL, ";
			SqlStr+="b.price,a.WorkingYears*12 as otherLimitYear,b.spec,b.brand,b.reason,b.LocationID,b.LocationName,b.spec,b.brand,a.RecDate,d.propertyno,d.lotno,d.serialno,b.AssetID, ";
			SqlStr+="isnull(c.VoidDate,'') as VoidDate,a.BuyDate,c.PostDate as reduceDate,isnull(c.cfmdate,'') as cfmdate, ''  as  DocNotwo,a.AssetName,a.rcvdate,a.differencekind, ";
			SqlStr+="a.assetno,b.unit,a.matter   ";

			SqlStr+="from ASNXBasic a,ASNXDistribute d,ASNXDetractDetail b,ASNXDetractMaster c ";
			SqlStr+="where d.Assetid=b.Assetid ";
			SqlStr+="and b.docid=c.docid ";
			SqlStr+="and a.AssetNo=d.AssetNo "; 
			SqlStr+="and c.void = '0' ";
 
			//SqlStr+="group by a.acctTagID,a.AgtNo,c.RECDate,d.keepdivid,b.AcctDocID,b.AcctDate,a.PostDate,b.note,c.DetractRefDocID,a.CategoryID,a.OffAccount,a.OffAccountDate,b.Qty,c.AmtTTL,a.RecDate,d.propertyno,d.lotno,d.serialno,b.AssetID,c.VoidDate,a.BuyDate,c.PostDate,c.cfmdate,b.price,a.workingyears,b.spec,b.brand,b.reason,b.LocationID,b.LocationName,b.spec,b.brand,a.AssetName,a.rcvdate,a.differencekind,c.DocID ";
			//SqlStr+=" where a.OffAccountDate is not null ";
//			SqlStr+=" order by convert(int,c.DocID) ";
			System.out.println(SqlStr);
			SQLo = smblconn.prepareStatement(SqlStr);
			
			insertstr+="insert into "+tablename+" ";
			insertstr+="( enterorg, caseno, ownership, differencekind, caseserialno, propertyno, lotno, serialno, " +
					"otherpropertyunit, othermaterial, otherlimityear, propertyname1, cause, cause1, reducedate, newenterorg, " +
					"transferdate, verify, propertykind, fundtype, valuable, booknotes, accountingtitle, oldbookamount, " +
					"oldbookunit, oldbookvalue, adjustbookamount, adjustbookunit, adjustbookvalue, " +
					"newbookamount, newbookunit, newbookvalue, " +
					"articlename, nameplate, specification, sourcedate, buydate, licenseplate, movedate, keepunit," +
					"keeper, useunit, userno, usernote, place1, place, useyear, usemonth, cause2, returnplace, reducedeal2, " +
					"oldpropertyno, oldserialno, newenterorgreceive, notes, editid, " +
					"editdate, edittime, dealcaseno, dealdate, reducedeal, realizevalue, shiftorg, oldlotno " +
					") ";			
			insertstr+=" values ( ";
			rs = SQLo.executeQuery();
			while(rs.next())
			{
				//增減值單單號ID
				proofno="";
				proofyear="";
				
				proofno=Common.formatFrontString(Common.get(rs.getString("DocNo")), 7, '0');
				proofyear=changeTaiwanYYMMDD(Common.get(rs.getDate("RECDate").toString()).replaceAll("-", ""),"1").substring(0,3);
				//caseno
				String caseno="";
				String proofdoc="";			    
				SQLo2 = tcghconn.prepareStatement("select caseno,summonsno,summonsdate,proofno from UNTNE_REDUCEPROOF where proofyear='"+proofyear+"' and proofno='"+proofno+"' and enterorg='" + inputEnterOrg + "' ");				
				
				rs2 = SQLo2.executeQuery();
				if(rs2.next()) //查詢單號是存在
				{
					caseno=Common.get(rs2.getString("caseno").toString());
				}else{         //不存在新建一個增加單單號
					
					SQLo3 = tcghconn.prepareStatement("select right(isnull(max(caseno)+1,'1'),7) as caseno from UNTNE_REDUCEPROOF  where proofyear='"+proofyear+"' and enterorg='" + inputEnterOrg + "' ");				
					rs3 = SQLo3.executeQuery();
					if(rs3.next())
					{
						caseno=changeTaiwanYYMMDD(Common.get(rs.getDate("RECDate").toString()).replaceAll("-", ""),"1").substring(0,3)+Common.formatFrontString(Common.get(rs3.getString("caseno")), 7, '0');
					}
					SQLo3.close();
				
					proofdoc="";

					INSERT_UNTNE_REDUCEPROOF(tcghconn,"UNTNE_REDUCEPROOF", caseno, proofdoc, proofno, Common.get(rs.getString("acctTagID")), Common.get(rs.getString("AgtNo")), changeDateFormat(rs.getDate("RECDate")), Common.get(rs.getString("keepdivid")), changeDateFormat(rs.getDate("PostDate")), Common.get(rs.getString("AcctDocID")), changeDateFormat(rs.getDate("AcctDate")), Common.get(rs.getString("note")) , Common.get(rs.getString("cfmdate")), Common.get(rs.getString("DocNotwo")), Common.get(rs.getString("reduceDate")), Common.get(rs.getString("differencekind")));					
					
				}
				//增加單單號ID
				SQLo2.close();
				rs2.close();
				subinsert="";
				subinsert+="'" + inputEnterOrg + "',";//enterorg			
				subinsert+="'"+caseno+"',";//caseno	
				subinsert+="'1',";//ownership
				//財產區分別
				subinsert+="'"+Common.get(rs.getString("differencekind"))+"',";//differencekind
				//subinsert+="'"+Common.get(rs.getString("AgtNo"))+"',";//engineeringno
				
				subinsert+="1,";//caseserialno

				String propertyno=Common.get(rs.getString("propertyno"));
				subinsert+="'"+propertyno+"',"; //propertyno
				
				String lotno=Common.get(rs.getString("lotno"));
				subinsert+="'"+lotno+"',"; //lotno
				
				String serialno=Common.get(rs.getString("serialno"));
				subinsert+="'"+serialno+"',"; //serialno
 
				subinsert+="'"+Common.get(rs.getString("unit"))+"',";//otherpropertyunit
				subinsert+="'"+Common.get(rs.getString("matter"))+"',";//othermaterial
				subinsert+="'"+Common.get(rs.getString("otherlimityear"))+"',";//otherlimityear
				subinsert+="N'"+Common.get(rs.getString("AssetName"))+"',";//propertyname1,
				subinsert+="'"+getSYSCA_CODENAME(tcghconn,Common.get(rs.getString("reason")),"CAA","499")+"',";//cause,
				subinsert+="'"+Common.get(rs.getString("reason"))+"',";//cause1,
				
 
				String reducedate="";
				if(Common.get(rs.getString("reduceDate")).length()>0){
					reducedate=Common.get(rs.getString("reduceDate")).replaceAll("-", "").substring(0,8);
				}
				subinsert+="'"+reducedate+"',";//reducedate,
				subinsert+="'',";//newenterorg,
				subinsert+="'',";//transferdate,
			    //======================
				subinsert+="'Y',";//verify
				
				subinsert+="'01',";//propertykind
				subinsert+="'',";//fundtype,
				subinsert+="'N',";//valuable,
				
				subinsert+="'',";//booknotes,
				subinsert+="'',";//accountingtitle,
				
				//***
				String oldbookamount="";
				oldbookamount=Common.get(rs.getString("Qty"));
//				subinsert+=""+oldbookamount+",";//oldbookamount,
				subinsert+=("".equals(oldbookamount)?0:oldbookamount) + ",";//oldbookamount,
				
				String oldbookunit="";
				oldbookunit=Common.get(rs.getString("Price"));
				subinsert+=("".equals(oldbookunit)?0:oldbookunit) + ",";//oldbookunit,
				
				String oldbookvalue="";
				oldbookvalue=Common.get(rs.getString("Price"));
//				subinsert+=""+oldbookvalue+",";//oldbookvalue,
				subinsert+=("".equals(oldbookvalue)?0:oldbookvalue) + ",";//oldbookvalue,
				
				
				String adjustbookamount="";
				adjustbookamount=Common.get(rs.getString("Qty"));
//				subinsert+=""+adjustbookamount+",";//adjustbookamount,
				subinsert+=("".equals(adjustbookamount)?0:adjustbookamount) + ",";//adjustbookamount,
				String adjustbookunit="";
				adjustbookunit=Common.get(rs.getString("Price"));
				subinsert+=("".equals(adjustbookunit)?0:adjustbookunit) + ",";//adjustbookunit,
				
				String adjustbookvalue="";
				adjustbookvalue=Common.get(rs.getString("Price"));
//				subinsert+=""+adjustbookvalue+",";//adjustbookvalue,
				subinsert+=("".equals(adjustbookvalue)?0:adjustbookvalue) + ",";//adjustbookvalue,
				
 
				subinsert+="'0',";//newbookamount,
				
				subinsert+="'0',";//newbookunit,
 
				subinsert+="'0',";//newbookvalue,
				
				subinsert+="'',";//articlename,
				subinsert+="'"+Common.get(rs.getString("brand"))+"',";;//nameplate,
				subinsert+="'"+Common.get(rs.getString("spec"))+"',";;//specification,
				String sourcedate="";
				if(Common.get(rs.getString("rcvdate")).length()>0){
					sourcedate=Common.get(rs.getString("rcvdate")).replaceAll("-", "").substring(0,8);
				}				
				subinsert+="'"+sourcedate+"',"; //sourcedate
				
				String buydate="";
				if(Common.get(rs.getString("buydate")).length()>0){
					buydate=Common.get(rs.getString("buydate")).replaceAll("-", "").substring(0,8);
				}				
				subinsert+="'"+buydate+"',"; //buydate
				
				subinsert+="'',";//licenseplate,
				subinsert+="'',";//movedate,
				subinsert+="'',";//keepunit,
				subinsert+="'',";//keeper,
				subinsert+="'',";//useunit,
				subinsert+="'',";//userno,
				subinsert+="'',";//usernote,
				subinsert+="'"+getPlaceno(Common.get(rs.getString("LocationID")), Common.get(rs.getString("LocationName")))+"',";//place1,
				subinsert+="'"+Common.get(rs.getString("LocationName"))+"',";//place,
				changeTaiwanYYMMDD(Common.get(rs.getDate("RECDate").toString()).replaceAll("-", ""),"1").substring(0,3);
				String ROCRECDate=changeTaiwanYYMMDD(Common.get(rs.getString("RECDate")).replaceAll("-", ""),"1").substring(0,5);
				String ROCbuydate=changeTaiwanYYMMDD(Common.get(rs.getString("buydate")).replaceAll("-", ""),"1").substring(0,5);
				int TempMonth = Datetime.BetweenTwoMonth(ROCbuydate,ROCRECDate);
				
				
				String useyear="";
				useyear = String.valueOf(TempMonth/12);
				

//				useyear=Integer.toString(Integer.valueOf(Common.get(rs.getString("reduceDate")).replaceAll("-", "").substring(0,4))-Integer.valueOf(Common.get(rs.getString("buydate")).replaceAll("-", "").substring(0,4)));	
				subinsert+=useyear+",";//useyear,
				String usemonth="";
				usemonth = String.valueOf(TempMonth%12);
				
				subinsert+=usemonth+",";//usemonth,
				
				subinsert+="'',";//cause2,
				subinsert+="'"+Common.get(rs.getString("LocationName"))+"',";//returnplace,
				subinsert+="'',";//reducedeal2,
				
 			
				
				String oldpropertyno="";
				oldpropertyno=Common.get(rs.getString("CategoryID"));
				subinsert+="'"+oldpropertyno+"',"; //oldpropertyno
				
				String oldserialno="";
				oldserialno=Common.get(rs.getString("AssetID"));
				subinsert+="'"+oldserialno+"',"; //oldserialno

				subinsert+="'Y',";//newenterorgreceive,
				
				subinsert+="'"+Common.get(rs.getString("note"))+"',";//notes
				subinsert+="'GM',";//editid
				subinsert+="'"+Datetime.getYYYYMMDD()+"',";//editdate
				subinsert+="'"+Datetime.getHHMMSS()+"',";//edittime
				
				subinsert+="'',";//dealcaseno,
				subinsert+="'',";//dealdate,
				subinsert+="'',";//reducedeal,
				subinsert+="null,";//realizevalue,
				subinsert+="'', ";//shiftorg,
				subinsert+="'"+Common.get(rs.getString("AssetNo"))+"' ";//oldlotno
				
				Allinsert=insertstr+subinsert+") ;";
				//System.out.println(Allinsert);
				if(!proofno.equals("")){
					try{
						if(insert_write==true){
							SQLo = tcghconn.prepareStatement(Allinsert);
							SQLo.execute();
							SQLo.close();
						}else{
							System.out.println(Allinsert);
						}
					}catch(Exception e){
						System.out.println(e);					
						System.out.println(Allinsert);
					}
					UPDATE_DATASTATE_DETAIL(tcghconn,rs,"UNTNE_NONEXPDETAIL");
				}
				
				showWhatIsNow();
			}
			UPDATE_DATASTATE(tcghconn,"UNTNE_NONEXP","UNTNE_NONEXPDETAIL");			//物品主檔明細
			SQLo.close();
			rs.close();
			smblconn.close();			
			tcghconn.close();
		} catch (Exception e) {
			System.out.println(Allinsert);
			e.printStackTrace();
		}

	}
	
	/**
	 * @寫入UNTNE_ADDPROOF用
	 */
	public static void INSERT_UNTNE_ADDPROOF(Connection conn,String tablename,String caseno,String proofdoc,String proofno,String acctTagID,String AgtNo,String RECDate,String RECDivID,String RecDate,String AcctDocID,String AcctDate,String note,String differencekind,String Postdate){
		PreparedStatement SQLo = null;
		String insertstr="";
		String subinsert="";
		String Allinsert="";
		
		insertstr+="insert into "+tablename+" ";
		insertstr+="( enterorg, ownership, caseno, differencekind, casename,";
		insertstr+="writedate, writeunit, proofyear, proofdoc, proofno, manageno, summonsno ,summonsdate,";
		insertstr+="enterdate, verify, notes, editid, editdate,";
		insertstr+="edittime ) ";			
		insertstr+=" values ( ";
		
		subinsert="";
		subinsert+="'"+inputEnterOrg+"',";//enterorg
		subinsert+="'1',";//ownership				
		subinsert+="'"+caseno+"',";//caseno			
		//財產區分別
		subinsert+="'"+Common.get(differencekind)+"',";//differencekind
	
		
		subinsert+="'',";//casename 停用
		if(RECDate.startsWith("1900-01-01")){
			subinsert+="null,";//writedate
		}else{
			subinsert+="'"+RECDate.replaceAll("-", "")+"',";//writedate  
		}
		subinsert+="'"+RECDivID+"',";//writeunit
		subinsert+="'"+changeTaiwanYYMMDD(Common.get(RECDate).replaceAll("-", ""),"1").substring(0,3)+"',";//proofyear
		subinsert+="'"+proofdoc+"',";//proofdoc
		subinsert+="'"+proofno+"',";//proofno
		subinsert+="'',";//manageno 停用
		subinsert+="'"+AcctDocID+"',";//summonsno
		subinsert+="'"+AcctDate.replaceAll("-", "")+"',";//summonsdate 
		subinsert+="'"+Postdate.replaceAll("-", "").substring(0,8)+"',";//enterdate
		subinsert+="'Y',";//verify
		subinsert+="'"+note+"',";//notes
		subinsert+="'GM',";//editid
		subinsert+="'"+Datetime.getYYYYMMDD()+"',";//editdate
		subinsert+="'"+Datetime.getHHMMSS()+"' ";//edittime
		
		
		Allinsert=insertstr+subinsert+") ;";
		if(insert_write==false){
			System.out.println(Allinsert);
		}
		//if(!proofno.equals("")){
			try{
				if(insert_write==true){
					SQLo = conn.prepareStatement(Allinsert);
					SQLo.execute();
					SQLo.close();
				}else{
					System.out.println(Allinsert);
				}
			}catch(Exception e){
				System.out.println(e);
				System.out.println(Allinsert);
			}
		//}
	}
	
	public static void UNTNE_MOVEDETAIL(String tablename){
		count = 0;
		
		Connection smblconn;
		Connection tcghconn;
		
		PreparedStatement SQLo = null;
		PreparedStatement SQLo1 = null;
		PreparedStatement SQLo2 = null;
		PreparedStatement SQLo3 = null;
		ResultSet rs,rs2,rs3;
		String insertstr="";
		String subinsert="";
		String Allinsert="";
		
		String proofno="";
		String proofyear="";
		String SqlStr="";
		try {
			//A27040000G 科技部新竹科學工業園區管理局 
			smblconn = getDBConnection("smblg");
			tcghconn = getDBConnection("TCGH");
			
			if(insert_write==true){
				SQLo = tcghconn.prepareStatement("delete UNTNE_MOVEDETAIL  where enterorg='"+inputEnterOrg+"' ");
				SQLo.execute();
				SQLo.close();
				
				SQLo = tcghconn.prepareStatement("delete UNTNE_MOVEPROOF  where enterorg='"+inputEnterOrg+"' ");
				SQLo.execute();
				SQLo.close();
				
			}
			
			SqlStr+=" select  ";			
			SqlStr+=" c.keeperidfrom,a.rcvdate,a.WorkingYears*12 as otherLimitYear,isnull(a.acctTagID,'') as acctTagID,isnull(a.AgtNo,'') as AgtNo,c.RECDate,isnull(c.RECDivID,'') as RECDivID,isnull(a.AcctDocID,'') as AcctDocID,a.AcctDate,a.PostDate,b.note as note_d,c.note as note_m,isnull(b.DocID,'') as DocNo, ";
			SqlStr+=" a.CategoryID,a.OffAccount,b.Qty,convert(int,a.AmtSTTL) as AmtSTTL,";
			SqlStr+=" b.AssetName,b.spec,b.brand,b.KeepDivIDFrom,b.KeepDivIDTo,b.KeeperIDTo,b.ASUserIDTo,b.ASUseDivIDFrom,b.ASUseDivIDTo,b.Qty,b.Price,b.Note,b.LocationNameFrom,b.LocationIDTo,b.LocationNameTo, ";
			SqlStr+=" a.RecDate,b.AssetID,a.differencekind,d.propertyno,d.lotno,d.serialno,b.unit,a.matter, ";
			SqlStr+=" isnull(c.VoidDate,'') as VoidDate,a.BuyDate,isnull(c.TransDate,'') as TransDate,isnull(c.DocID,'') as  DocNotwo,a.assetno ";
			SqlStr+=" from ASNXBasic a,ASNXDistribute d,ASNXTransDetail b,ASNXTransMaster c";
			SqlStr+=" where a.AssetNo=d.AssetNo  ";
			SqlStr+=" and d.Assetid=b.Assetid";
			SqlStr+=" and b.docid=c.docid ";
			SqlStr+=" and c.void = '0' ";
			//SqlStr+="where b.DocID is not null "; 
			//SqlStr+="group by a.acctTagID,a.AgtNo,c.RECDate,c.RECDivID,a.AcctDocID,a.AcctDate,a.PostDate,b.note,b.DocID ";
			//SqlStr+=" where a.OffAccountDate is not null ";
			SqlStr+=" order by convert(int,b.DocID) ";
			System.out.println(SqlStr);
			SQLo = smblconn.prepareStatement(SqlStr);
			
			insertstr+="insert into "+tablename+" ";
			insertstr+="( " +
					"enterorg, caseno, ownership, differencekind, caseserialno, propertyno, lotno, serialno, propertyname1, " +
					"otherpropertyunit, othermaterial, otherlimityear, buydate, verify, propertykind, fundtype, valuable, " +
					"bookamount, bookunit, bookvalue,  nameplate, specification, sourcedate, oldmovedate," +
					" oldkeepunit, oldkeeper, olduseunit, olduserno, oldusernote, oldplace1, oldplace, newmovedate, " +
					"newkeepunit, newkeeper, newuseunit, newuserno, newusernote, newplace1, newplace, useyear, usemonth, " +
					"oldpropertyno, oldserialno, " +
					"notes, editid, editdate, edittime, oldlotno " +
					") ";			
			insertstr+=" values ( ";
			rs = SQLo.executeQuery();
			while(rs.next())
			{
				//增減值單單號ID
				proofno="";
				proofyear="";
				proofno=Common.formatFrontString(Common.get(rs.getString("DocNo")), 7, '0');
				proofyear=changeTaiwanYYMMDD(Common.get(rs.getDate("RECDate").toString()).replaceAll("-", ""),"1").substring(0,3);

				
				//caseno
				String caseno="";
				String proofdoc="";			    
				SQLo2 = tcghconn.prepareStatement("select caseno,proofno from UNTNE_MOVEPROOF where proofyear='"+proofyear+"' and proofno='"+proofno+"' and enterorg='" + inputEnterOrg + "' ");				
				rs2 = SQLo2.executeQuery();
				if(rs2.next()) //查詢單號是存在
				{
					caseno=Common.get(rs2.getString("caseno").toString());
				}else{         //不存在新建一個增加單單號
					
					SQLo3 = tcghconn.prepareStatement("select right(isnull(max(caseno)+1,'1'),7) as caseno from UNTNE_MOVEPROOF  where proofyear='"+proofyear+"' and enterorg='" + inputEnterOrg + "' ");
					rs3 = SQLo3.executeQuery();
					if(rs3.next())
					{
						caseno=changeTaiwanYYMMDD(Common.get(rs.getDate("RECDate").toString()).replaceAll("-", ""),"1").substring(0,3)+Common.formatFrontString(Common.get(rs3.getString("caseno")), 7, '0');
					}
					SQLo3.close();
//					SQLo3 = tcghconn.prepareStatement("select isnull(max(proofno)+1,'1') as proofno from UNTNE_MOVEPROOF  where proofyear='"+proofyear+"' and enterorg='" + inputEnterOrg + "' ");				
//					rs3 = SQLo3.executeQuery();
//					if(rs3.next())
//					{
//						proofno=Common.formatFrontString(Common.get(rs3.getString("proofno")), 7, '0');
//					}
					proofdoc=" ";
//					SQLo3.close();
					
					INSERT_UNTNE_MOVEPROOF(tcghconn,"UNTNE_MOVEPROOF", caseno, proofdoc, proofno, Common.get(rs.getDate("RECDate").toString()), Common.get(rs.getString("RECDivID").toString()), Common.get(rs.getDate("PostDate").toString()), Common.get(rs.getDate("TransDate").toString()), Common.get(rs.getString("note_m").toString()) );
					
				}
				//增加單單號ID
				SQLo2.close();
				rs2.close();
				subinsert="";
				subinsert+="'" + inputEnterOrg + "',";//enterorg			
				subinsert+="'"+caseno+"',";//caseno	
				subinsert+="'1',";//ownership
				//財產區分別
				subinsert+="'"+Common.get(rs.getString("differencekind"))+"',";//differencekind
				
				subinsert+="1,";//caseserialno
				
				String propertyno=Common.get(rs.getString("propertyno"));
				subinsert+="'"+propertyno+"',"; //propertyno
				
				String lotno=Common.get(rs.getString("lotno"));
				subinsert+="'"+lotno+"',"; //lotno
				
				String serialno=Common.get(rs.getString("serialno"));
				subinsert+="'"+serialno+"',"; //serialno
				
				subinsert+="N'"+Common.get(rs.getString("AssetName"))+"',";//propertyname1,
				subinsert+="'"+Common.get(rs.getString("unit"))+"',";//otherpropertyunit
				subinsert+="'"+Common.get(rs.getString("matter"))+"',";//othermaterial
				String otherlimityear=Common.get(rs.getString("otherLimitYear"));
				subinsert+=""+otherlimityear+",";//otherlimityear
				String buydate="";
				if(Common.get(rs.getString("buyDate")).length()>0){
					buydate=Common.get(rs.getString("buyDate")).replaceAll("-", "").substring(0,8);
				}
				subinsert+="'"+buydate+"',";//buydate***
				subinsert+="'Y',";//verify
				
				subinsert+="'01',";//propertykind
				subinsert+="'',";//fundtype,
				subinsert+="'N',";//valuable,
				

				String bookamount="";
				bookamount=Common.get(rs.getString("Qty"));
				subinsert+=""+bookamount+","; //bookamount,
				
				subinsert+=""+Common.get(rs.getString("Price"))+",";//bookunit,

				String bookvalue="";
				bookvalue=Common.get(rs.getString("Price"));
				subinsert+=""+bookvalue+","; //bookvalue
				
/*非消無			subinsert+="null,";//netunit,
					
				String netvalue="";
				netvalue=Common.get(rs.getString("AmtSTTL"));
				subinsert+=""+netvalue+",";//netvalue,
*/				
				
				subinsert+="'"+Common.get(rs.getString("Brand"))+"',";//nameplate,
				subinsert+="'"+Common.get(rs.getString("Spec"))+"',";//specification,
				String sourcedate="";
				if(Common.get(rs.getString("rcvdate")).length()>0){
					sourcedate=Common.get(rs.getString("rcvdate")).replaceAll("-", "").substring(0,8);
				}				
				subinsert+="'"+sourcedate+"',"; //sourcedate
				
				subinsert+="'',";//oldmovedate,
				subinsert+="'"+Common.get(rs.getString("KeepDivIDFrom"))+"',";//oldkeepunit,
				subinsert+="'"+transKeeperNo(Common.get(rs.getString("keeperidfrom")))+"',";//oldkeeper,			
				subinsert+="'"+Common.get(rs.getString("ASUseDivIDFrom"))+"',";//olduseunit,
				subinsert+="'',";//olduserno,
				subinsert+="'',";//oldusernote,
				subinsert+="'"+getPlaceno("", Common.get(rs.getString("LocationNameFrom")))+"',";//oldplace1,
				subinsert+="'"+Common.get(rs.getString("LocationNameFrom"))+"',";//oldplace,
				String newmovedate="";
				if(Common.get(rs.getString("TransDate")).length()>0){
					newmovedate=Common.get(rs.getString("TransDate")).replaceAll("-", "").substring(0,8);
				}
				subinsert+="'"+newmovedate+"',";//newmovedate,
				subinsert+="'"+Common.get(rs.getString("KeepDivIDTo"))+"',";//newkeepunit,
				subinsert+="'"+transKeeperNo(Common.get(rs.getString("KeeperIDTo")))+"',";//newkeeper,
				subinsert+="'"+Common.get(rs.getString("ASUseDivIDTo"))+"',";//newuseunit,
				subinsert+="'"+transKeeperNo(Common.get(rs.getString("ASUserIDTo")))+"',";//newuserno,
				subinsert+="'',";//newusernote,
				subinsert+="'"+getPlaceno(Common.get(rs.getString("LocationIDTo")), Common.get(rs.getString("LocationNameTo")))+"',";//newplace1,
				subinsert+="'"+Common.get(rs.getString("LocationNameTo"))+"',";//newplace,
				
				changeTaiwanYYMMDD(Common.get(rs.getDate("RECDate").toString()).replaceAll("-", ""),"1").substring(0,3);
				String ROCRECDate=changeTaiwanYYMMDD(Common.get(rs.getString("RECDate")).replaceAll("-", ""),"1").substring(0,5);
				String ROCbuydate=changeTaiwanYYMMDD(Common.get(rs.getString("buydate")).replaceAll("-", ""),"1").substring(0,5);
				int TempMonth = Datetime.BetweenTwoMonth(ROCbuydate,ROCRECDate);
								
				String useyear="";
				useyear = String.valueOf(TempMonth/12);
					
				subinsert+=useyear+",";//useyear
				String usemonth="";
				usemonth = String.valueOf(TempMonth%12);
				
				subinsert+=usemonth+",";//usemonth
				
/*非消無				
				subinsert+="'02',";//olddeprmethod,
				
				subinsert+="'',";//oldbuildfeecb,
				subinsert+="'',";//olddeprunitcb,
				subinsert+="'',";//olddeprpark,
				subinsert+="'',";//olddeprunit,
				subinsert+="'',";//olddeprunit1,
				subinsert+="'',";//olddepraccounts,
				
				subinsert+="null,";//oldscrapvalue,
				subinsert+="null,";//olddepramount,
				subinsert+="null,";//oldaccumdepr,
				subinsert+="null,";//oldapportionmonth,
				subinsert+="null,";//oldmonthdepr,
				subinsert+="null,";//oldmonthdepr1,
				
				subinsert+="'',";//oldapportionendym,
				
				subinsert+="'02',";//newdeprmethod,
				
				
				subinsert+="'',";//newbuildfeecb,
				subinsert+="'',";//newdeprunitcb,
				subinsert+="'',";//newdeprpark,
				subinsert+="'',";//newdeprunit,
				subinsert+="'',";//newdeprunit1,
				subinsert+="'',";//newdepraccounts,
				
				subinsert+="null,";//newscrapvalue,
				subinsert+="null,";//newdepramount,
				subinsert+="null,";//newaccumdepr,
				subinsert+="null,";//newapportionmonth,
				subinsert+="null,";//newmonthdepr,
				subinsert+="null,";//newmonthdepr1,
				
				
				subinsert+="'',";//newapportionendym,				
				
				subinsert+="'',";//signnola,
				subinsert+="null,";//areala,
				subinsert+="null,";//holdnumela,
				subinsert+="null,";//holddenola,
				subinsert+="null,";//holdareala,
				subinsert+="'',";//signnobu,
				subinsert+="null,";//careabu,
				subinsert+="null,";//sareabu,
				subinsert+="null,";//areabu,
				subinsert+="null,";//holdnumebu,
				subinsert+="null,";//holddenobu,
				subinsert+="null,";//holdareabu,
				subinsert+="null,";//measure,
*/		
				String oldpropertyno="";
				oldpropertyno=Common.get(rs.getString("CategoryID"));
				subinsert+="'"+oldpropertyno+"',"; //oldpropertyno
				
				String oldserialno="";
				oldserialno=Common.get(rs.getString("AssetID"));
				subinsert+="'"+oldserialno+"',"; //oldserialno
				
				subinsert+="'"+Common.get(rs.getString("note_d"))+"',";//notes
				subinsert+="'GM',";//editid
				subinsert+="'"+Datetime.getYYYYMMDD()+"',";//editdate
				subinsert+="'"+Datetime.getHHMMSS()+"',";//edittime
				subinsert+="'"+Common.get(rs.getString("AssetNo"))+"' ";//oldlotno
				
				Allinsert=insertstr+subinsert+") ;";
				//System.out.println(Allinsert);
				if(!proofno.equals("")){
					try{
						if(insert_write==true){
							SQLo = tcghconn.prepareStatement(Allinsert);
							SQLo.execute();
							SQLo.close();
						}else{
							System.out.println(Allinsert);
						}
					}catch(Exception e){
						System.out.println(e);
						System.out.println(Allinsert);
					}
				}
				
				showWhatIsNow();
			}

			SQLo.close();
			rs.close();
			smblconn.close();			
			tcghconn.close();
		} catch (Exception e) {
			System.out.println(Allinsert);
			e.printStackTrace();
		}

	}
	
	public static void INSERT_UNTNE_MOVEPROOF(Connection conn,String tablename,String caseno,String proofdoc,String proofno ,String RECDate,String RECDivID , String PostDate,String movedate,String note){
		PreparedStatement SQLo = null;
		String insertstr="";
		String subinsert="";
		String Allinsert="";
		
		insertstr+="insert into "+tablename+" ";
		insertstr+="( " +
				"enterorg, caseno, ownership, casename, writedate, writeunit, proofyear, " +
				"proofdoc, proofno, movedate, verify, notes, editid, editdate, edittime " +
				" ) ";			
		insertstr+=" values ( ";
		
		subinsert="";
		subinsert+="'" + inputEnterOrg + "',";//enterorg
		subinsert+="'"+caseno+"',";//caseno
		subinsert+="'1',";//ownership				
		
		subinsert+="'',";//casename 停用
		if(RECDate.startsWith("1900-01-01")){
			subinsert+="null,";//writedate
		}else{
			subinsert+="'"+RECDate.replaceAll("-", "")+"',";//writedate  
		}
		subinsert+="'"+RECDivID+"',";//writeunit
		subinsert+="'"+changeTaiwanYYMMDD(Common.get(RECDate).replaceAll("-", ""),"1").substring(0,3)+"',";//proofyear
		subinsert+="'"+proofdoc+"',";//proofdoc
		subinsert+="'"+proofno+"',";//proofno
		if(movedate.startsWith("1900-01-01")){
			subinsert+="null,";//movedate
			subinsert+="'N',";//verify
		}
		else {
			subinsert+="'"+movedate.replaceAll("-", "").substring(0,8)+"',";//movedate
			subinsert+="'Y',";//verify
		}		
		subinsert+="'"+note+"',";//notes
		subinsert+="'GM',";//editid
		subinsert+="'"+Datetime.getYYYYMMDD()+"',";//editdate
		subinsert+="'"+Datetime.getHHMMSS()+"' ";//edittime

		Allinsert=insertstr+subinsert+") ;";
		//System.out.println(Allinsert);
		if(!proofno.equals("")){
			try{
				if(insert_write==true){
					SQLo = conn.prepareStatement(Allinsert);
					SQLo.execute();
					SQLo.close();
				}else{
					System.out.println(Allinsert);
				}
			}catch(Exception e){
				System.out.println(e);
				System.out.println(Allinsert);
			}
		}
	}
	
	public static void INSERT_UNTNE_REDUCEPROOF(Connection conn,String tablename,String caseno,String proofdoc,String proofno,String acctTagID,String AgtNo,String RECDate,String RECDivID,String PostDate,String AcctDocID,String AcctDate,String note,String cfmdate,String docno,String reduceDate,String differencekind){
		PreparedStatement SQLo = null;
		String insertstr="";
		String subinsert="";
		String Allinsert="";
		
		insertstr+="insert into "+tablename+" ";
		insertstr+="( " +
				"enterorg, caseno, ownership, differencekind, casename, writedate, writeunit, " +
				"proofyear, proofdoc, proofno, manageno, summonsno, reducedate, approveorg, approvedate, " +
				"approvedoc, verify, notes, editid, editdate, edittime, cause, cause1, summonsdate" +
				" ) ";			
		insertstr+=" values ( ";
		
		subinsert="";
		subinsert+="'" + inputEnterOrg + "',";//enterorg
		subinsert+="'"+caseno+"',";//caseno
		subinsert+="'1',";//ownership				
		//財產區分別
		subinsert+="'"+differencekind+"',";//differencekind
		subinsert+="'',";//casename 停用
		if(RECDate.startsWith("1900-01-01")){
			subinsert+="null,";//writedate
		}else{
			subinsert+="'"+RECDate.replaceAll("-", "")+"',";//writedate  
		}
		subinsert+="'"+RECDivID+"',";//writeunit
		subinsert+="'"+changeTaiwanYYMMDD(Common.get(RECDate).replaceAll("-", ""),"1").substring(0,3)+"',";//proofyear
		subinsert+="'"+proofdoc+"',";//proofdoc
		subinsert+="'"+proofno+"',";//proofno
		subinsert+="'',";//manageno 停用
		subinsert+="'"+AcctDocID+"',";//summonsno
		// 		
		//subinsert+="'"+fluctuateddate.replaceAll("-", "")+"',";//adjustdate
		if(reduceDate.length()>=8){
			subinsert+="'"+reduceDate.replaceAll("-", "").substring(0,8)+"',";//reducedate
		}else{
			subinsert+="'',";//reducedate
		}
		
		subinsert+="'',";//approveorg
		if(cfmdate.length()>=8){
			subinsert+="'"+cfmdate.replaceAll("-", "").substring(0,8)+"',";//approvedate
		}else{
			subinsert+="'',";//approvedate
		}
		subinsert+="'"+docno+"',";//approvedoc
		subinsert+="'Y',";//verify
		subinsert+="'"+note+"',";//notes
		subinsert+="'GM',";//editid
		subinsert+="'"+Datetime.getYYYYMMDD()+"',";//editdate
		subinsert+="'"+Datetime.getHHMMSS()+"',";//edittime
		subinsert+="'',";//cause
		subinsert+="'',";//cause1
		if(AcctDate.length()>=8){
			subinsert+="'"+AcctDate.replaceAll("-", "").substring(0,8)+"' ";//summonsdate
		}else{
			subinsert+="'' ";//summonsdate
		}
		Allinsert=insertstr+subinsert+") ;";
		//System.out.println(Allinsert);
		if(!proofno.equals("")){
			try{
				if(insert_write==true){
					SQLo = conn.prepareStatement(Allinsert);
					SQLo.execute();
					SQLo.close();
				}else{
					System.out.println(Allinsert);
				}
			}catch(Exception e){
				System.out.println(e);
				System.out.println(Allinsert);
			}
		}
	}

	/**
	 * @寫入UNTNE_ADJUSTPROOF用 
	 */	
	public static void INSERT_UNTNE_ADJUSTPROOF(Connection conn,String tablename,String caseno,String proofdoc,String proofno,String acctTagID,String AgtNo,String RECDate,String RECDivID,String RecDate,String AcctDocID,String AcctDate,String note,String fluctuateddate,String cfmdate,String docno){
		PreparedStatement SQLo = null;
		String insertstr="";
		String subinsert="";
		String Allinsert="";
		
		insertstr+="insert into "+tablename+" ";
		insertstr+="( enterorg, caseno, ownership,  differencekind, engineeringno, casename,";
		insertstr+="writedate, writeunit, proofyear, proofdoc, proofno, manageno, summonsno ,summonsdate,";
		insertstr+="adjustdate, approveorg, approvedate, approvedoc, verify, notes, editid, editdate,";
		insertstr+="edittime ) ";			
		insertstr+=" values ( ";
		
		subinsert="";
		subinsert+="'"+inputEnterOrg+"',";//enterorg					
		subinsert+="'"+caseno+"',";//caseno	
		subinsert+="'1',";//ownership	
		//財產區分別
		if(acctTagID.equals("1")){ //公務
			subinsert+="'110',";//differencekind
		}else{
			if(acctTagID.equals("2")){ //基金
				subinsert+="'120',";//differencekind
			}else{
					subinsert+="'500',";//differencekind

			}
		}
		
		subinsert+="'"+AgtNo+"',";//engineeringno
		subinsert+="'',";//casename 停用
		if(RECDate.startsWith("1900-01-01")){
			subinsert+="null,";//writedate
		}else{
			subinsert+="'"+RECDate.replaceAll("-", "")+"',";//writedate  
		}
		subinsert+="'"+RECDivID+"',";//writeunit
		subinsert+="'"+changeTaiwanYYMMDD(Common.get(RECDate).replaceAll("-", ""),"1").substring(0,3)+"',";//proofyear
		subinsert+="'"+proofdoc+"',";//proofdoc
		subinsert+="'"+proofno+"',";//proofno
		subinsert+="'',";//manageno 停用
		subinsert+="'"+AcctDocID+"',";//summonsno
		
		if(AcctDate.length()>=8){
			subinsert+="'"+AcctDate.replaceAll("-", "").substring(0, 8)+"',";//summonsdate
		}else{
			subinsert+="'',";
		}
		
		if(fluctuateddate.length()>=8){
			subinsert+="'"+fluctuateddate.replaceAll("-", "").substring(0, 8)+"',";//adjustdate
		}else{
			subinsert+="'',";
		}
		subinsert+="'',";//approveorg
		
		if(cfmdate.length()>=8){
			subinsert+="'"+cfmdate.replaceAll("-", "").substring(0, 8)+"',";//approvedate
		}else{
			subinsert+="'',";
		}
		subinsert+="'"+docno+"',";//approvedoc
		subinsert+="'Y',";//verify
		subinsert+="'"+note+"',";//notes
		subinsert+="'GM',";//editid
		subinsert+="'"+Datetime.getYYYYMMDD()+"',";//editdate
		subinsert+="'"+Datetime.getHHMMSS()+"' ";//edittime
		
		
		Allinsert=insertstr+subinsert+") ;";
		if(insert_write==false){
		System.out.println(Allinsert);
		}
		//if(!proofno.equals("")){
			try{
				if(insert_write==true){
					SQLo = conn.prepareStatement(Allinsert);
					SQLo.execute();
					SQLo.close();
				}else{
					System.out.println(Allinsert);
				}
			}catch(Exception e){
				System.out.println(e);
				System.out.println(Allinsert);
			}
		//}
	}
	
	//修改動產基本資料的的流水號起訖
	private static void UPDATE_UNTMP_MOVABLE_serialnoSE(Connection tcghconn) throws SQLException {
		PreparedStatement SQLo = null;
		StringBuffer updateSql = new StringBuffer("");
		updateSql.append(" UPDATE b ");
		updateSql.append(" SET ");
		updateSql.append(" b.serialnos = (SELECT MIN(a.serialno) FROM UNTMP_MOVABLEDETAIL a WHERE ");
		updateSql.append(" a.enterorg = b.enterorg ");
		updateSql.append(" and a.ownership = b.ownership ");
		updateSql.append(" and a.differencekind = b.differencekind ");
		updateSql.append(" AND a.propertyno = b.propertyno ");
		updateSql.append(" AND a.lotno = b.lotno ");
		updateSql.append(" ), ");
		updateSql.append(" b.serialnoe = (SELECT MAX(a.serialno) FROM UNTMP_MOVABLEDETAIL a WHERE ");
		updateSql.append(" a.enterorg = b.enterorg ");
		updateSql.append(" and a.ownership = b.ownership ");
		updateSql.append(" and a.differencekind = b.differencekind ");
		updateSql.append(" AND a.propertyno = b.propertyno ");
		updateSql.append(" AND a.lotno = b.lotno ");
		updateSql.append(" ), ");
		updateSql.append(" b.oldserialnos = (SELECT MIN(a.oldserialno) FROM UNTMP_MOVABLEDETAIL a WHERE ");
		updateSql.append(" a.enterorg = b.enterorg ");
		updateSql.append(" and a.ownership = b.ownership ");
		updateSql.append(" and a.differencekind = b.differencekind ");
		updateSql.append(" AND a.propertyno = b.propertyno ");
		updateSql.append(" AND a.lotno = b.lotno ");
		updateSql.append(" ), ");
		updateSql.append(" b.oldserialnoe = (SELECT MAX(a.oldserialno) FROM UNTMP_MOVABLEDETAIL a WHERE ");
		updateSql.append(" a.enterorg = b.enterorg ");
		updateSql.append(" and a.ownership = b.ownership ");
		updateSql.append(" and a.differencekind = b.differencekind ");
		updateSql.append(" AND a.propertyno = b.propertyno ");
		updateSql.append(" AND a.lotno = b.lotno ");
		updateSql.append(" ) ");
		updateSql.append(" FROM dbo.UNTMP_MOVABLE b ");
		updateSql.append(" WHERE ");
		updateSql.append(" b.enterorg = '").append(inputEnterOrg).append("';");
		try {
			if(insert_write==true){
				SQLo = tcghconn.prepareStatement(updateSql.toString());
				SQLo.execute();
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		} finally {
			SQLo.close();
		}
	}
	
	//修改物品基本資料的的流水號起訖
	private static void UPDATE_UNTNE_NONEXP_serialnoSE(Connection tcghconn) throws SQLException {
		PreparedStatement SQLo = null;
		StringBuffer updateSql = new StringBuffer("");
		updateSql.append(" UPDATE b ");
		updateSql.append(" SET ");
		updateSql.append(" b.serialnos = (SELECT MIN(a.serialno) FROM UNTNE_NONEXPDETAIL a WHERE ");
		updateSql.append(" a.enterorg = b.enterorg ");
		updateSql.append(" and a.ownership = b.ownership ");
		updateSql.append(" and a.differencekind = b.differencekind ");
		updateSql.append(" AND a.propertyno = b.propertyno ");
		updateSql.append(" AND a.lotno = b.lotno ");
		updateSql.append(" ), ");
		updateSql.append(" b.serialnoe = (SELECT MAX(a.serialno) FROM UNTNE_NONEXPDETAIL a WHERE ");
		updateSql.append(" a.enterorg = b.enterorg ");
		updateSql.append(" and a.ownership = b.ownership ");
		updateSql.append(" and a.differencekind = b.differencekind ");
		updateSql.append(" AND a.propertyno = b.propertyno ");
		updateSql.append(" AND a.lotno = b.lotno ");
		updateSql.append(" ), ");
		updateSql.append(" b.oldserialnos = (SELECT MIN(a.oldserialno) FROM UNTNE_NONEXPDETAIL a WHERE ");
		updateSql.append(" a.enterorg = b.enterorg ");
		updateSql.append(" and a.ownership = b.ownership ");
		updateSql.append(" and a.differencekind = b.differencekind ");
		updateSql.append(" AND a.propertyno = b.propertyno ");
		updateSql.append(" AND a.lotno = b.lotno ");
		updateSql.append(" ), ");
		updateSql.append(" b.oldserialnoe = (SELECT MAX(a.oldserialno) FROM UNTNE_NONEXPDETAIL a WHERE ");
		updateSql.append(" a.enterorg = b.enterorg ");
		updateSql.append(" and a.ownership = b.ownership ");
		updateSql.append(" and a.differencekind = b.differencekind ");
		updateSql.append(" AND a.propertyno = b.propertyno ");
		updateSql.append(" AND a.lotno = b.lotno ");
		updateSql.append(" ) ");
		updateSql.append(" FROM dbo.UNTNE_NONEXP b ");
		updateSql.append(" WHERE ");
		updateSql.append(" b.enterorg = '").append(inputEnterOrg).append("';");
		try {
			if(insert_write==true){
				SQLo = tcghconn.prepareStatement(updateSql.toString());
				SQLo.execute();
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		} finally {
			SQLo.close();
		}
	}
	
	//修改竹科管局減損金額(因為104年1月的基金已全部折舊因此減損金額為0)
	private static void UPDATE_REDUCEDETAIL(Connection tcghconn) throws SQLException {
		PreparedStatement SQLo = null;
		StringBuffer updateSql = new StringBuffer("");
		updateSql.append(" UPDATE UNTMP_REDUCEDETAIL ");
		updateSql.append(" SET  ");
		updateSql.append(" adjustbookvalue = '0', ");
		updateSql.append(" adjustnetvalue = '0' ");
		updateSql.append(" WHERE ");
		updateSql.append(" enterorg = 'A27040000G' ");
		updateSql.append(" AND reducedate LIKE '201501%' ");
		updateSql.append(" AND differencekind = '120' ");
		try {
			if(insert_write==true){
				SQLo = tcghconn.prepareStatement(updateSql.toString());
				SQLo.execute();
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		} finally {
			SQLo.close();
		}
	}

	//修改折舊檔資料(目前因竹科管局104年1月的報表與資料庫不合)
	private static void UPDATE_UNTDP(Connection tcghconn) throws SQLException {
		PreparedStatement SQLo = null;
		StringBuffer updateSql = new StringBuffer("");
		updateSql.append(" UPDATE UNTDP_MONTHDEPR ");
		updateSql.append(" SET  ");
		updateSql.append(" monthdepr1= '1238244', ");
		updateSql.append(" monthdepr = '1238244' ");
		updateSql.append(" WHERE ");
		updateSql.append(" enterorg = 'A27040000G' ");
		updateSql.append(" AND oldpropertyno = '2010101-01A' ");
		updateSql.append(" AND oldserialno = '209500056001' ");
		updateSql.append(" AND (deprym = '201501' or deprym = '201502' or deprym = '201503') ");
		try {
			if(insert_write==true){
				SQLo = tcghconn.prepareStatement(updateSql.toString());
				SQLo.execute();
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		} finally {
			SQLo.close();
		}
	}
	
	
	//修改明細檔的datastate
	private static void UPDATE_DATASTATE_DETAIL(Connection tcghconn,ResultSet rs,String detail_tablename) throws SQLException {
		PreparedStatement SQLo = null;
		StringBuffer updateSql = new StringBuffer("");
		updateSql.append(" UPDATE m ");
		updateSql.append(" SET  ");
		updateSql.append(" m.datastate='2', ");
		updateSql.append(" m.reducedate=" +Common.sqlChar(Common.get(rs.getString("reducedate")).replaceAll("-", "").substring(0,8)) + ",");
		updateSql.append(" m.bookvalue = 0, ");
		updateSql.append(" m.bookamount = 0 ");
		updateSql.append(" FROM ");
		updateSql.append( detail_tablename + " m ");
		updateSql.append(" WHERE ");
		updateSql.append(" m.enterorg = '").append(inputEnterOrg).append("'");
		updateSql.append(" AND m.ownership = '1' ");
		updateSql.append(" AND m.differencekind = " + Common.sqlChar(Common.get(rs.getString("differencekind"))));
		updateSql.append(" AND m.propertyno = " + Common.sqlChar(Common.get(rs.getString("propertyno"))));
		updateSql.append(" AND m.serialno = " + Common.sqlChar(Common.get(rs.getString("serialno"))));
		
		
		try {
			if(insert_write==true){
				SQLo = tcghconn.prepareStatement(updateSql.toString());
				SQLo.execute();
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		} finally {
			SQLo.close();
		}
	}
	
	//修改主檔的datastate
	private static void UPDATE_DATASTATE(Connection tcghconn,String tablename,String detail_tablename) throws SQLException {
		PreparedStatement SQLo = null;
		StringBuffer updateSql = new StringBuffer("");
		updateSql.append(" UPDATE m ");
		updateSql.append(" SET  ");
		updateSql.append(" m.datastate='2', ");
		updateSql.append(" m.bookvalue = 0, ");
		if (!"UNTNE_NONEXP".endsWith(tablename)) {
			updateSql.append(" m.netvalue = 0, ");
		}
		updateSql.append(" m.bookamount = 0 ");
		updateSql.append(" FROM ");
		updateSql.append( tablename + " m ");
		updateSql.append(" WHERE ");
		updateSql.append(" m.enterorg = '").append(inputEnterOrg).append("';");
		
		updateSql.append(" UPDATE m ");
		updateSql.append(" SET m.datastate = '1' , ");
		updateSql.append(" m.bookvalue = (SELECT SUM(d.bookvalue) FROM " + detail_tablename + " d WHERE   m.enterorg = d.enterorg AND m.ownership = d.ownership AND m.lotno = d.lotno AND m.propertyno = d.propertyno AND m.differencekind = d.differencekind) , ");
		if (!"UNTNE_NONEXP".endsWith(tablename)) {
			updateSql.append(" m.netvalue = (SELECT SUM(d.netvalue) FROM " + detail_tablename + " d WHERE   m.enterorg = d.enterorg AND m.ownership = d.ownership AND m.lotno = d.lotno AND m.propertyno = d.propertyno AND m.differencekind = d.differencekind) , ");
		}
		updateSql.append(" m.bookamount = (SELECT SUM(d.bookamount) FROM " + detail_tablename + " d WHERE   m.enterorg = d.enterorg AND m.ownership = d.ownership AND m.lotno = d.lotno AND m.propertyno = d.propertyno AND m.differencekind = d.differencekind) ");
		updateSql.append(" FROM " + tablename + " m ");
		updateSql.append(" WHERE  m.enterorg ='").append(inputEnterOrg).append("'");
		updateSql.append(" AND EXISTS ( SELECT 1 ");
		updateSql.append(" FROM " + detail_tablename + " d ");
		updateSql.append(" WHERE  m.enterorg = d.enterorg ");
		updateSql.append(" AND m.ownership = d.ownership ");
		updateSql.append(" AND m.lotno = d.lotno ");
		updateSql.append(" AND m.propertyno = d.propertyno ");
		updateSql.append(" AND m.differencekind = d.differencekind ");
		updateSql.append(" AND d.datastate = '1' ); ");
		
//		updateSql.append(" UPDATE a ");
//		updateSql.append(" SET ");
//		updateSql.append(" datastate = '2' ");
//		updateSql.append(" FROM ");
//		updateSql.append(tablename).append(" a ");
//		updateSql.append(" INNER JOIN ").append(detail_tablename).append(" b ON ");
//		updateSql.append(" a.enterorg = b.enterorg ");
//		updateSql.append(" AND a.ownership = b.ownership ");
//		updateSql.append(" AND a.differencekind = b.differencekind ");
//		updateSql.append(" AND a.propertyno = b. propertyno ");
//		if ("UNTMP_MOVABLE".equals(tablename) || "UNTNE_NONEXP".equals(tablename)) {
//			updateSql.append(" AND  b.serialno BETWEEN a.serialnos AND a.serialnoe ");
//		} else {
//			updateSql.append(" AND a.serialno = b.serialno ");
//		}
//		updateSql.append(" WHERE ");
//		updateSql.append(" a.enterorg = '").append(inputEnterOrg).append("';");
		try {
			if(insert_write==true){
				SQLo = tcghconn.prepareStatement(updateSql.toString());
				SQLo.execute();
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		} finally {
			SQLo.close();
		}
	}
	
	
	public static String getDeprMethod(String acctTagID){
		String DeprMethod = "01";
		if("A10120000U".equals(inputEnterOrg) || "A102V0000U".equals(inputEnterOrg)){
			DeprMethod = "01";	//deprmethod
		}else{
			if(Common.get(acctTagID).equals("2") || Common.get(acctTagID).equals("6")){ //基金、代管
				DeprMethod = "02";	//deprmethod
			}else{
				DeprMethod = "01";	//deprmethod
			}
		}
		return DeprMethod;
	}
	
	public static String getNoDeprset(String AssetID) throws SQLException{
		String NoDeprset = "N";
		PreparedStatement SQLo = null;
		ResultSet rs = null;
		Connection smblconn = null;
		try {
			smblconn = getDBConnection("smblg");
			SQLo = smblconn.prepareStatement("select 1 from ASDprNotDprSingleAssets where AssetID = '"+AssetID+"'");
			
			rs = SQLo.executeQuery();
			
			if(rs.next())
			{
				NoDeprset = "Y";
			}


		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			if (SQLo != null)
				SQLo.close();
			if (rs != null)
				rs.close();
			if (smblconn != null)
				smblconn.close();
		}
		return NoDeprset;
	}
	
	public static String getEngineeringnoAdd(String AssetType,String AddDocID) throws SQLException{
		String Engineeringno = "";
		PreparedStatement SQLo = null;
		ResultSet rs = null;
		Connection smblconn = null;
		try {
			smblconn = getDBConnection("smblg");
			SQLo = smblconn.prepareStatement("select ProjectID from ZKProjectJobAdd where AssetType = '"+AssetType+"' and AddDocID = '"+AddDocID+"'");
			
			rs = SQLo.executeQuery();
			
			if(rs.next())
			{
				Engineeringno = rs.getString("ProjectID");
			}


		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			if (SQLo != null)
				SQLo.close();
			if (rs != null)
				rs.close();
			if (smblconn != null)
				smblconn.close();
		}
		return Engineeringno;
	}
	
	public static String getEngineeringnoFluctuate(String FluctuateDocID) throws SQLException{
		String Engineeringno = "";
		PreparedStatement SQLo = null;
		ResultSet rs = null;
		Connection smblconn = null;
		try {
			smblconn = getDBConnection("smblg");
			SQLo = smblconn.prepareStatement("select ProjectID from ZKProjectJobFluctuate where FluctuateDocID = '"+FluctuateDocID+"'");
			
			rs = SQLo.executeQuery();
			
			if(rs.next())
			{
				Engineeringno = rs.getString("ProjectID");
			}


		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			if (SQLo != null)
				SQLo.close();
			if (rs != null)
				rs.close();
			if (smblconn != null)
				smblconn.close();
		}
		return Engineeringno;
	}
		
	public static String getPlaceno(String placeno,String placename) throws SQLException {
		if ("A102V0000U".equals(inputEnterOrg)) { //目前只有中科實中
			PreparedStatement SQLo = null;
			ResultSet rs = null;
			Connection tcghconn = null;
			try {
				tcghconn = getDBConnection("TCGH");
				SQLo = tcghconn.prepareStatement("select placeno from SYSCA_PLACE where enterorg = '" + inputEnterOrg + "' and placename = '" + placename + "'");
				
				rs = SQLo.executeQuery();
				
				if(rs.next())
				{
					placeno = rs.getString("placeno");
				}
			} catch(Exception e) {
				e.printStackTrace();
			} finally {
				if (SQLo != null)
					SQLo.close();
				if (rs != null)
					rs.close();
				if (tcghconn != null)
					tcghconn.close();
			}
		}
		return placeno;
	}
	
	
	//目前只有中科實中提出要合併保管人的要求
	private static String transKeeperNo(String s){
		String result = s;
		if ("A27040000G".endsWith(inputEnterOrg)) {
			
		} else if ("A10120000U".endsWith(inputEnterOrg)) {
			
		} else if ("A102V0000U".endsWith(inputEnterOrg)) {
			if("p0077".equals(s)){			result = "p0087";
			}else if("p0109".equals(s)){	result = "p0087";
			}else if("p0112".equals(s)){	result = "p0092";
			}else if("P0014".equals(s)){	result = "p0041";
			}else if("p0120".equals(s)){	result = "p0041";
			}else if("P0031".equals(s)){	result = "p0042";
			}else if("p0115".equals(s)){	result = "p0042";
			}else if("p0081".equals(s)){	result = "p0097";
			}else if("p0118".equals(s)){	result = "p0097";
			}else if("P0023".equals(s)){	result = "p0049";
			}else if("d0107".equals(s)){	result = "p0049";
			}else if("p0113".equals(s)){	result = "p0049";
			}else if("p0083".equals(s)){	result = "p0088";
			}else if("p0110".equals(s)){	result = "p0088";
			}else if("p0079".equals(s)){	result = "p0090";
			}else if("p0114".equals(s)){	result = "p0090";
			}else if("p0074".equals(s)){	result = "p0117";
			}else if("p0089".equals(s)){	result = "p0117";
			}else if("p0111".equals(s)){	result = "p0091";
			}else if("p0116".equals(s)){	result = "p0054";
			}else if("p0067".equals(s)){	result = "p0096";
			}else if("p0119".equals(s)){	result = "P0046";
			}else if("p0075".equals(s)){	result = "p0126";
			}
		}
		return result;
	}
	
	/**
	 * @UNTMP_STORE
	 */
	public static void UNTMP_STORE(String tablename){
		count = 0;
		Connection smblconn;
		Connection tcghconn;
		
		PreparedStatement SQLo = null;
		PreparedStatement SQLo1 = null;
		PreparedStatement SQLo2 = null;
		PreparedStatement SQLo3 = null;
		ResultSet rs,rs2,rs3;
		String insertstr="";
		String subinsert="";
		String Allinsert="";
		String tempAssetNo="";
		String storename="";
		String storeno="";
		String SqlStr="";
		int i=1;

		try {
			//"+inputEnterOrg+" 科技部新竹科學工業園區管理局 
			smblconn = getDBConnection("smblg");
			tcghconn = getDBConnection("TCGH");
			
			if(insert_write==true){
			SQLo = tcghconn.prepareStatement("delete UNTMP_STORE  where enterorg='"+inputEnterOrg+"' ");
			SQLo.execute();
			SQLo.close();
			}
			

			SqlStr+="select vendername from ASNXBASIC where isnull(vendername,'')<>''  group by vendername order by convert(varchar,vendername) "; 
			
			System.out.println(SqlStr);
			SQLo = smblconn.prepareStatement(SqlStr);
			
			insertstr+="insert into "+tablename+" ";
			insertstr+="( enterorg, storeno, storename, editid, editdate, edittime " +
					" ) ";			
			insertstr+=" values ( ";
			rs = SQLo.executeQuery();
			while(rs.next())
			{
				
				storeno=Common.formatFrontString(Integer.toString(i), 10, '0');
				storename=Common.get(rs.getString("vendername").toString());

				subinsert="";
				subinsert+="'"+inputEnterOrg+"',";//enterorg
				subinsert+="'"+storeno+"',";//storeno
				subinsert+="'"+storename+"',";//storename
				
				
				subinsert+="'GM',";//editid
				subinsert+="'"+Datetime.getYYYYMMDD()+"',";//editdate
				subinsert+="'"+Datetime.getHHMMSS()+"' ";//edittime
				
				
				Allinsert=insertstr+subinsert+") ;";
				
				if(insert_write==false){
					System.out.println(Allinsert);
				}
				//if(!proofno.equals("")){
					try{
						if(insert_write==true){
							SQLo = tcghconn.prepareStatement(Allinsert);
							SQLo.execute();
							SQLo.close();
						}else{
							System.out.println(Allinsert);
						}
					}catch(Exception e){
						System.out.println(e);
						System.out.println(Allinsert);
					}
				//}
				showWhatIsNow();
				i++;
			}
			SQLo.close();
			rs.close();
			smblconn.close();			
			tcghconn.close();
		} catch (Exception e) {
			System.out.println(Allinsert);
			e.printStackTrace();
		}
	}
	
	/**
	 * @資料庫連線
	 */
	public static Connection getDBConnection(String DB) throws Exception {
		String url = null;
		String div_str = null;
		String user = null;
		String pw = null;
		try {
			try {
				if(DB.equals("TCGH")) {//測試區 
					url = "jdbc:jtds:sqlserver://"+toDBip+":1433/TCGH_TRANSFER";
					div_str = "com.microsoft.jdbc.sqlserver.SQLServerDriver";
					user = toDBuser;
					pw = toDBpw;
				}

				if(DB.equals("smblg")) {//smbl 竹科管 word
					if("A27040000G".equals(inputEnterOrg)){
						url = "jdbc:jtds:sqlserver://"+fromDBip+":1433/smblg_1";
						div_str = "net.sourceforge.jtds.jdbc.Driver";
						user = fromDBuser;
						pw = fromDBpw;
					}else if("A10120000U".equals(inputEnterOrg)){
						url = "jdbc:jtds:sqlserver://"+fromDBip+":1433/smblg_002";
						div_str = "net.sourceforge.jtds.jdbc.Driver";
						user = fromDBuser;
						pw = fromDBpw;
						
					}else if("A102V0000U".equals(inputEnterOrg)){
						url = "jdbc:jtds:sqlserver://"+fromDBip+":1433/smblg_003";
						div_str = "net.sourceforge.jtds.jdbc.Driver";
						user = fromDBuser;
						pw = fromDBpw;
					}
				}
			}
			catch (Exception e)
			{
				System.out.println("While getting properties:\n" + e);
			}
			Class.forName(div_str);
			return DriverManager.getConnection(url, user, pw);
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
	}
		
		
		public static void showWhatIsNow(){
			//顯示進度用
			if(count < 100){	System.out.print("-");
			}else{				System.out.print("-");
								System.out.println(rowcount);
								count = 0;
			}
			rowcount++;
			count++;
			//System.out.println(count);
		}
		
		public static void showWhatIsEnd(){
			//結束顯示進度用
			System.out.println(rowcount);
			count=0;
			rowcount=0;
		}
		
		public static String changeDateFormat(Date date){
			String result = "";
			try{
				if((date == null) || ("".equals(Common.get(date).toString()))){
					result = "null";
				}else{
					result = Common.get(date.toString()).replaceAll("-", "");
				}
			}catch(Exception e){
				e.printStackTrace();
			}
			return result;
		}
		
		
		/**
	     * <br>
	  	 * <br>目的：西元年與民國年加月日轉換
	  	 * <br>參數：String sdate ,ex:日期 String type ,ex:日期種類(1民國年,2西元年)
	  	 * <br>傳回：傳回日期
	     */
		public static String changeTaiwanYYMMDD(String sdate,String type){
			Calendar calendar = Calendar.getInstance();	
			StringBuffer sb = new StringBuffer();
			String rStr="";
			int year=0,mm=0,dd=0;
			if(StringUtils.isNotEmpty(sdate)){
				if ("1".equals(type)){
					year = Integer.parseInt(sdate.substring(0,4))-1911;
					mm = Integer.parseInt(sdate.substring(4,6));
					dd = Integer.parseInt(sdate.substring(6,8));								
				}else if("2".equals(type)){
					year = Integer.parseInt(sdate.substring(0,3))+1911;
					mm = Integer.parseInt(sdate.substring(3,5));
					dd = Integer.parseInt(sdate.substring(5,7));
				}				
				
				if (year<=0){
					sb.append("000");
				}else{
					if (year<=99){ sb.append("0"); }
					sb.append(Integer.toString(year));	
				}								
				if (mm<=9){ sb.append("0"); }
				sb.append(Integer.toString(mm));
				if (dd<=9){ sb.append("0"); }
				sb.append(Integer.toString(dd));	
				rStr = sb.toString();
			}
			
			
			return rStr;
		}
		
		/**
		 * <br>
	  	 * <br>目的：輸入代碼中文換回代碼ID
	  	 * <br>參數：Connection tcghconn 連線字串,String CODENAME 代碼中文 EX：報廢 ,String CODEKINDID 代碼 EX：CAA,String DEFAULTID 查無代碼時預設代碼 EX：499
	  	 * <br>傳回：傳回日期
		 */
		public static String getSYSCA_CODENAME(Connection tcghconn,String CODENAME,String CODEKINDID,String DEFAULTID ){
			String codeid="";
			String SqlStr="select top 1 codeid from SYSCA_CODE where codekindid = '"+CODEKINDID+"' AND codename ='" + CODENAME + "' ";
			PreparedStatement SQLo = null;
			ResultSet rs;
			try {
				SQLo = tcghconn.prepareStatement(SqlStr);
				rs = SQLo.executeQuery();
				if(rs.next()){
					codeid=Common.get(rs.getString("codeid"));
				} else {
					codeid=DEFAULTID;
				}
				SQLo.close();
				rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
			return codeid;
		}
		
}
