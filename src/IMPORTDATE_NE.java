import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import util.*;

public class IMPORTDATE_NE {
    public static boolean insert_write=true;// 是否寫入資料庫 true=寫入 false 不寫入
        
    public static int count = 1;
    public static int rowcount = 1;
    
//	public static String inputEnterOrg = "A27040000G";	//竹科管局
//	public static String inputEnterOrg = "A10120000U";	//竹科實中
  public static String inputEnterOrg = "A102V0000U";	//中科實中
    
	/**
	 * @資料匯入作業
	 */
	public static void main(String[] args) {
		System.out.println("資料匯入作業-開始");
		try {
			//科技部
			//oracle.CHGDS > mssql.UNTMP_ADDPROOF
			//conn = getDBConnection("MS");
			//SQLo = conn.prepareStatement(" select * from SYSCA_CODE ");
			//A27040000G 科技部新竹科學工業園區管理局 
			
			
//			UNTALL_ADDPROOF("UNTLA_ADDPROOF");
//			UNTALL_ADDPROOF("UNTBU_ADDPROOF");
//			UNTALL_ADDPROOF("UNTRF_ADDPROOF");
//			UNTALL_ADDPROOF("UNTMP_ADDPROOF");
//			
//			UNTMP_MOVABLEDETAIL("UNTMP_MOVABLEDETAIL");
//			
//			UNTMP_MOVABLE("UNTMP_MOVABLE"); 
//			
//			UNTMP_ADJUSTDETAIL("UNTMP_ADJUSTDETAIL");
//			UNTMP_REDUCEDETAIL("UNTMP_REDUCEDETAIL");
//			
//			UNTMP_MOVEDETAIL("UNTMP_MOVEDETAIL");
//			
//			UNTDP_MONTHDEPR("UNTDP_MONTHDEPR"); //還沒轉完
//			
//			UNTDP_MONTHDEPR_2("UNTDP_MONTHDEPR"); //可能有問題 需重轉
//			UNTDP_MONTHDEPR_3("UNTDP_MONTHDEPR"); //可能有問題 需重轉
//			
//			check_ASbldgBasic();
//			check_ASIprvBasic();
			//======物品======
			//UNTNE_ADDPROOF("UNTNE_ADDPROOF");
			
			//UNTNE_NONEXPDETAIL("UNTNE_NONEXPDETAIL");
			
			//UNTNE_NONEXP("UNTNE_NONEXP");
			
			//UNTNE_REDUCEDETAIL("UNTNE_REDUCEDETAIL");
			
			UNTNE_MOVEDETAIL("UNTNE_MOVEDETAIL");
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("資料匯入作業-結束");
	}
	
	/**
	 * @UNTMP_ADDPROOF
	 */
	public static void UNTALL_ADDPROOF(String tablename){
		count = 0;
		
		Connection smblconn;
		Connection tcghconn;
		
		PreparedStatement SQLo = null;
		PreparedStatement SQLo1 = null;
		PreparedStatement SQLo2 = null;
		ResultSet rs,rs2;
		
		String proofno="";
		String proofdoc="";
		String SqlStr="";
		try {
			//A27040000G 科技部新竹科學工業園區管理局 
			smblconn = getDBConnection("smblg");
			tcghconn = getDBConnection("TCGH");
			
			SqlStr+="select   ";
			SqlStr+="isnull(a.acctTagID,'') as acctTagID,isnull(a.AgtNo,'') as AgtNo,c.RECDate,c.RECDivID,isnull(a.AcctDocID,'') as AcctDocID,a.AcctDate,a.PostDate,b.note,b.DocID as DocNo "; 
			SqlStr+="from ASsetBasic a  ";
			SqlStr+="left join  ASAddDetail b on a.AssetID=b.AssetID "; 
			SqlStr+="left join  ASAddMaster c on b.docid=c.docid ";
			SqlStr+="where b.DocID is not null "; 
			SqlStr+="group by a.acctTagID,a.AgtNo,c.RECDate,c.RECDivID,a.AcctDocID,a.AcctDate,a.PostDate,b.note,b.DocID ";
			SqlStr+="order by convert(int,b.DocID) ";
			
			System.out.println(SqlStr);
			SQLo = smblconn.prepareStatement(SqlStr);
			
			rs = SQLo.executeQuery();
			
			while(rs.next())
			{
				
				//增加單單號處理
				proofno="";
				proofdoc="";
				/*
				if((proofno.indexOf("安審94")>=0) ){
					//System.out.println("*********************************"+proofno);
					//System.out.println(Common.formatFrontString(proofno.substring(8), 7, '0'));
					proofdoc="安審";
					proofno=Common.formatFrontString(proofno.substring(8), 7, '0');					
				}else{
					if((proofno.indexOf("1 ")>=0) || (proofno.indexOf("94 ")>=0)){
						//System.out.println("*********************************"+proofno);
						//System.out.println(Common.formatFrontString(proofno.substring(10), 7, '0'));
						proofdoc="財增";
						proofno=Common.formatFrontString(proofno.substring(10), 7, '0');						
					}else{
						if((proofno.indexOf("財折 ")>=0) || (proofno.indexOf("財增 ")>=0) || (proofno.indexOf("財修 ")>=0) || (proofno.indexOf("財移 ")>=0) || (proofno.indexOf("財基 ")>=0) || (proofno.indexOf("基廢 ")>=0) || (proofno.indexOf("減基 ")>=0) || (proofno.indexOf("基減 ")>=0) || (proofno.indexOf("雜折 ")>=0) || (proofno.indexOf("折舊 ")>=0) || (proofno.indexOf("基折 ")>=0) || (proofno.indexOf("機折 ")>=0)){
							//System.out.println("*********************************"+proofno);
							//System.out.println(Common.formatFrontString(proofno.substring(8), 7, '0'));
							proofdoc=Common.get(proofno.substring(0,2));
							proofno=Common.formatFrontString(proofno.substring(8), 7, '0');							
						}else{
							if((proofno.indexOf("折")>=0)||(proofno.indexOf("移")>=0)||(proofno.indexOf("竹科增")>=0)){
								//System.out.println("============================="+proofno);
								//System.out.println(Common.formatFrontString(proofno.substring(9), 7, '0'));
								proofdoc=Common.get(proofno.substring(0,3));								
								proofno=Common.formatFrontString(proofno.substring(9), 7, '0');
							}else{
								//System.out.println(proofno);
								//System.out.println(Common.formatFrontString(proofno, 7, '0'));
								proofdoc="財增";
								proofno=Common.formatFrontString(proofno, 7, '0');
							}
						}
					}
				}
				*/
				proofdoc="財增";
				proofno=Common.formatFrontString(Common.get(rs.getString("DocNo")), 7, '0');
				//單號為空處理
				/*
				if(proofno.equals("0000000")){
					proofno="";					
					SQLo2 = tcghconn.prepareStatement("select isnull(max(caseno)+1,'YYYY0000001') as maxcaseno from "+tablename+" where proofyear='"+Common.get(rs.getDate("PostDate").toString()).replaceAll("-", "").substring(0,4)+"' ");
					rs2 = SQLo2.executeQuery();
					if(rs2.next())
					{
						proofno=Common.get(rs2.getString("maxcaseno").substring(4));
					}
					SQLo2.close();
					rs2.close();
				}*/
				//System.out.println(proofno);
				//增加單單號處理
				//maxcaseno
				String maxcaseno="";
				SQLo2 = tcghconn.prepareStatement("select right(isnull(max(caseno)+1,'20000'),7) as maxcaseno from "+tablename+" where proofyear='"+Common.get(rs.getDate("PostDate").toString()).replaceAll("-", "").substring(0,4)+"' and enterorg='" + inputEnterOrg + "' ");				
				rs2 = SQLo2.executeQuery();
				if(rs2.next())
				{
					maxcaseno=changeTaiwanYYMMDD(Common.get(rs.getDate("PostDate").toString()).replaceAll("-", ""),"1").substring(0,3)+Common.formatFrontString(Common.get(rs2.getString("maxcaseno")), 7, '0');;
				}
				SQLo2.close();
				rs2.close();
				//System.out.println(Common.get(rs.getString("DocNo")));
				INSERT_ADDPROOF(tcghconn,tablename, maxcaseno, proofdoc, proofno, Common.get(rs.getString("acctTagID")), Common.get(rs.getString("AgtNo")), Common.get(rs.getDate("RECDate").toString()), Common.get(rs.getString("RECDivID").toString()), Common.get(rs.getDate("PostDate").toString()), Common.get(rs.getString("AcctDocID").toString()), Common.get(rs.getDate("AcctDate").toString()), Common.get(rs.getString("note").toString()));
				
				showWhatIsNow();
			}

			SQLo.close();
			rs.close();
			smblconn.close();			
			tcghconn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}		
	
	
	/**
	 * @UNTMP_MOVABLEDETAIL
	 */
	public static void UNTMP_MOVABLEDETAIL(String tablename){
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
			
			SqlStr+="select   ";
			SqlStr+="isnull(a.acctTagID,'') as acctTagID,isnull(a.AgtNo,'') as AgtNo,isnull(c.RECDate,'') as RECDate,isnull(c.RECDivID,'') as RECDivID,isnull(a.AcctDocID,'') as AcctDocID,a.AcctDate,a.PostDate,isnull(b.note,'') as note,isnull(b.DocID,'') as DocNo, "; 
			SqlStr+="a.CategoryID,a.OffAccount,isnull(a.OffAccountDate,'') as OffAccountDate,a.Qty,convert(int,a.AmtSTTL) as AmtSTTL,convert(int,a.ValDprTTL) as ValDprTTL,a.PurposeName,a.KeepDivID,a.KeeperID, ";
			SqlStr+="a.ASUseDivID,a.ASUserID,a.RecDate,a.AssetID,a.propertyno,a.lotno,a.serialno  ";
			SqlStr+="from ASsetBasic a  ";
			SqlStr+="left join  ASAddDetail b on a.AssetID=b.AssetID "; 
			SqlStr+="left join  ASAddMaster c on b.docid=c.docid ";
			SqlStr+=" order by convert(int,b.DocID) ";
			//System.out.println(SqlStr);
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
            insertstr+=" monthdepr, monthdepr1, apportionendym, nodeprset, notes1, barcode, oldpropertyno, oldserialno, notes, editid, "; 
            insertstr+=" editdate, edittime ) ";			
			insertstr+=" values ( ";
			rs = SQLo.executeQuery();
			while(rs.next())
			{
				//增加單單號ID
				proofno="";
				proofyear="";
				proofno=Common.formatFrontString(Common.get(rs.getString("DocNo")), 7, '0');
				proofyear=Common.get(rs.getDate("PostDate").toString()).replaceAll("-", "").substring(0,4);

				
				//caseno
				String caseno="";
				String proofdoc="";
				
				SQLo2 = tcghconn.prepareStatement("select caseno from UNTLA_ADDPROOF where proofyear='"+proofyear+"' and proofno='"+proofno+"' and enterorg='" + inputEnterOrg + "' ");				
				rs2 = SQLo2.executeQuery();
				if(rs2.next()) //查詢單號是存在
				{
					caseno=Common.get(rs2.getString("caseno"));
				}else{         //不存在新建一個增加單單號
					
					SQLo3 = tcghconn.prepareStatement("select right(isnull(max(caseno)+1,'20000'),7) as caseno from UNTLA_ADDPROOF where proofyear='"+proofyear+"' and enterorg='" + inputEnterOrg + "' ");				
					rs3 = SQLo3.executeQuery();
					if(rs3.next())
					{
						caseno=changeTaiwanYYMMDD(Common.get(rs.getDate("PostDate").toString()).replaceAll("-", ""),"1").substring(0,3)+Common.formatFrontString(Common.get(rs3.getString("caseno")), 7, '0');
					}
					SQLo3.close();
					SQLo3 = tcghconn.prepareStatement("select isnull(max(proofno)+1,'1') as proofno from UNTLA_ADDPROOF where proofyear='"+proofyear+"' and enterorg='" + inputEnterOrg + "' ");				
					rs3 = SQLo3.executeQuery();
					if(rs3.next())
					{
						proofno=Common.formatFrontString(Common.get(rs3.getString("proofno")), 7, '0');
					}
					proofdoc="財增";
					SQLo3.close();
					INSERT_ADDPROOF(tcghconn,"UNTLA_ADDPROOF", caseno, proofdoc, proofno, Common.get(rs.getString("acctTagID")), Common.get(rs.getString("AgtNo")), Common.get(rs.getDate("RECDate").toString()), Common.get(rs.getString("RECDivID").toString()), Common.get(rs.getDate("PostDate").toString()), Common.get(rs.getString("AcctDocID").toString()), Common.get(rs.getDate("AcctDate").toString()), Common.get(rs.getString("note").toString()));
					INSERT_ADDPROOF(tcghconn,"UNTBU_ADDPROOF", caseno, proofdoc, proofno, Common.get(rs.getString("acctTagID")), Common.get(rs.getString("AgtNo")), Common.get(rs.getDate("RECDate").toString()), Common.get(rs.getString("RECDivID").toString()), Common.get(rs.getDate("PostDate").toString()), Common.get(rs.getString("AcctDocID").toString()), Common.get(rs.getDate("AcctDate").toString()), Common.get(rs.getString("note").toString()));
					INSERT_ADDPROOF(tcghconn,"UNTRF_ADDPROOF", caseno, proofdoc, proofno, Common.get(rs.getString("acctTagID")), Common.get(rs.getString("AgtNo")), Common.get(rs.getDate("RECDate").toString()), Common.get(rs.getString("RECDivID").toString()), Common.get(rs.getDate("PostDate").toString()), Common.get(rs.getString("AcctDocID").toString()), Common.get(rs.getDate("AcctDate").toString()), Common.get(rs.getString("note").toString()));
					INSERT_ADDPROOF(tcghconn,"UNTMP_ADDPROOF", caseno, proofdoc, proofno, Common.get(rs.getString("acctTagID")), Common.get(rs.getString("AgtNo")), Common.get(rs.getDate("RECDate").toString()), Common.get(rs.getString("RECDivID").toString()), Common.get(rs.getDate("PostDate").toString()), Common.get(rs.getString("AcctDocID").toString()), Common.get(rs.getDate("AcctDate").toString()), Common.get(rs.getString("note").toString()));
					
				}
				//增加單單號ID
				SQLo2.close();
				rs2.close();
				subinsert="";
				subinsert+="'" + inputEnterOrg + "',";//enterorg
				subinsert+="'1',";//ownership				
				subinsert+="'"+caseno+"',";//caseno			
				//財產區分別
				String differencekind="";
				if(Common.get(rs.getString("acctTagID")).equals("1")){ //公務
					differencekind="110";//differencekind
				}else{
					if(Common.get(rs.getString("acctTagID")).equals("2")){ //基金
						differencekind="120";//differencekind
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
				subinsert+="'"+Common.get(rs.getString("AgtNo"))+"',";//engineeringno
				/*
				subinsert+="'',";//casename 停用
				subinsert+="'"+Common.get(rs.getDate("RECDate").toString()).replaceAll("-", "")+"',";//writedate  
				subinsert+="'"+Common.get(rs.getString("RECDivID"))+"',";//writeunit
				subinsert+="'"+Common.get(rs.getDate("PostDate").toString()).replaceAll("-", "").substring(0,4)+"',";//proofyear
				subinsert+="'"+proofdoc+"',";//proofdoc
				subinsert+="'"+proofno+"',";//proofno
				subinsert+="'',";//manageno 停用
				subinsert+="'"+Common.get(rs.getString("AcctDocID"))+"',";//summonsno
				subinsert+="'"+Common.get(rs.getString("AcctDate"))+"',";//summonsdate 
				subinsert+="'"+Common.get(rs.getDate("PostDate").toString()).replaceAll("-", "")+"',";//enterdate
				subinsert+="'',";//mergedivision 新系統欄位
				*/
				//System.out.println("CategoryID="+Common.get(rs.getString("CategoryID").toString()));
				//System.out.println("CategoryID="+Common.get(rs.getString("CategoryID").toString()).substring(0,3));
				//
				String propertyno=Common.get(rs.getString("propertyno"));
				subinsert+="'"+propertyno+"',"; //propertyno
				/*
				if(Common.get(rs.getString("CategoryID").toString()).substring(0,3).equals("503") || Common.get(rs.getString("CategoryID").toString()).substring(0,3).equals("504") || Common.get(rs.getString("CategoryID").toString()).substring(0,3).equals("505")){
					subinsert+="'"+Common.get(rs.getString("CategoryID").toString())+"',"; //propertyno
					propertyno=Common.get(rs.getString("CategoryID").toString());
				}else{
					if(Common.get(rs.getString("CategoryID").toString()).length()==11){
						subinsert+="'"+Common.get(rs.getString("CategoryID").toString()).replaceAll("-", "0")+"',"; //propertyno
						propertyno=Common.get(rs.getString("CategoryID").toString()).replaceAll("-", "0");
					}else{
						if(Common.get(rs.getString("CategoryID").toString()).length()==10){
							subinsert+="'"+Common.get(rs.getString("CategoryID").toString()).replaceAll("-", "00")+"',"; //propertyno
							propertyno=Common.get(rs.getString("CategoryID").toString()).replaceAll("-", "00");
						}else{
							subinsert+="'"+Common.formatRearString(Common.get(rs.getString("CategoryID").toString()),11,'0')+"',"; //propertyno
							propertyno=Common.formatRearString(Common.get(rs.getString("CategoryID").toString()),11,'0');
						}
					}
				}
				*/

				//
				/*
				SQLo3 = tcghconn.prepareStatement("select lotno from "+tablename+" where caseno='"+caseno+"' and enterorg='A27040000G' ");				
				rs3 = SQLo3.executeQuery();
				if(rs3.next())
				{
					lotno=Common.formatFrontString(Common.get(rs3.getString("lotno")), 7, '0');
				}else{
					SQLo3.close();
					SQLo3 = tcghconn.prepareStatement("select isnull(max(lotno)+1,'1') as lotno from "+tablename+" where enterorg='A27040000G' ");				
					rs3 = SQLo3.executeQuery();
					if(rs3.next()){
						lotno=Common.formatFrontString(Common.get(rs3.getString("lotno")), 7, '0');
					}
				}
				SQLo3.close();
				*/
				String lotno=Common.get(rs.getString("lotno"));
				subinsert+="'"+lotno+"',"; //lotno
				
				String serialno=Common.get(rs.getString("serialno"));
				subinsert+="'"+serialno+"',"; //serialno
				/*
				SQLo3 = tcghconn.prepareStatement("select isnull(max(serialno)+1,'1') as serialno from UNTMP_MOVABLEDETAIL where propertyno='"+propertyno+"' and enterorg='A27040000G' ");				
				rs3 = SQLo3.executeQuery();
				if(rs3.next())
				{
					serialno=Common.formatFrontString(Common.get(rs3.getString("serialno")), 7, '0');
				}
				SQLo3.close();				
				subinsert+="'"+serialno+"',"; //serialno
				*/
				String datastate="";
				if (Common.get(rs.getString("OffAccount")).equals("1")){ //減損
					datastate="2";
				}else{
					datastate="1";
				}
				subinsert+="'"+datastate+"',"; //datastate
				String reducedate="";
				if(Common.get(rs.getString("OffAccountDate")).length()>0){
					reducedate=Common.get(rs.getString("OffAccountDate")).replaceAll("-", "").substring(0,8);
				}
				subinsert+="'"+reducedate+"',"; //reducedate
				subinsert+="'',"; //reducecause
				subinsert+="'',"; //reducecause1				
				subinsert+="'Y',";//verify
				String enterdate="";
				if(Common.get(rs.getString("PostDate")).length()>0){
					enterdate=Common.get(rs.getString("PostDate")).replaceAll("-", "").substring(0,8);
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
					bookvalue=Common.get(rs.getString("AmtSTTL"));
				}				
				subinsert+=""+bookvalue+","; //bookvalue
				
				String netvalue="";
				if (Common.get(rs.getString("OffAccount")).equals("1")){  //減損
					netvalue="0";
				}else{
					//System.out.println(Common.get(rs.getString("AmtSTTL")));
					//System.out.println(Common.get(rs.getString("ValDprTTL")));
					netvalue=Integer.toString(Integer.valueOf(Common.get(rs.getString("AmtSTTL")))-Integer.valueOf(Common.get(rs.getString("ValDprTTL"))));
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
				originalkeeper=Common.get(rs.getString("KeeperID"));
				subinsert+="'"+originalkeeper+"',"; //originalkeeper
				
				String originaluseunit="";
				originaluseunit=Common.get(rs.getString("ASUseDivID"));
				subinsert+="'"+originaluseunit+"',"; //originaluseunit
				
				String originaluser="";
				originaluser=Common.get(rs.getString("ASUserID"));
				subinsert+="'"+originaluser+"',"; //originaluser
				
				subinsert+="'',"; //originalusernote
				subinsert+="'',"; //originalplace1
				subinsert+="'',"; //originalplace
				subinsert+="'',"; //movedate
				subinsert+="'',"; //keepunit
				subinsert+="'',"; //keeper
				subinsert+="'',"; //useunit
				subinsert+="'',"; //userno
				subinsert+="'',"; //usernote
				subinsert+="'',"; //place1
				subinsert+="'',"; //place
				subinsert+="'02',"; //originaldeprmethod
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
				subinsert+="'02',"; //deprmethod
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
				subinsert+="'',"; //nodeprset
				subinsert+="'',"; //notes1
				String barcode="";
				//※	條碼編碼規則：由「財產區分別-財產編號-財產分號」
				barcode=differencekind+"-"+propertyno+"-"+serialno;
				subinsert+="'"+barcode+"',"; //barcode
				
				String oldpropertyno="";
				oldpropertyno=Common.get(rs.getString("CategoryID"));
				subinsert+="'"+oldpropertyno+"',"; //oldpropertyno
				
				String oldserialno="";
				oldserialno=Common.get(rs.getString("AssetID"));
				subinsert+="'"+oldserialno+"',"; //oldserialno

				
				
				subinsert+="'"+Common.get(rs.getString("note"))+"',";//notes
				subinsert+="'GM',";//editid
				subinsert+="'"+Datetime.getYYYYMMDD()+"',";//editdate
				subinsert+="'"+Datetime.getHHMMSS()+"' ";//edittime
				
				
				Allinsert=insertstr+subinsert+") ;";
				System.out.println(Allinsert);
				if(!proofno.equals("")){
					try{
						if(insert_write==true){
							SQLo = tcghconn.prepareStatement(Allinsert);
							SQLo.execute();
							SQLo.close();
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
	
	
	/**
	 * @UNTMP_MOVABLE
	 */
	public static void UNTMP_MOVABLE(String tablename){
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
			
			SqlStr+="select   ";
			SqlStr+="isnull(a.acctTagID,'') as acctTagID,isnull(a.AgtNo,'') as AgtNo,isnull(a.AcctDocID,'') as AcctDocID,a.AcctDate,a.PostDate,b.note,isnull(b.DocID,'') as DocNo, "; 
			SqlStr+="a.CategoryID,a.OffAccount,a.OffAccountDate,a.Qty,convert(int,a.AmtSTTL) as AmtSTTL,convert(int,a.ValDprTTL) as ValDprTTL,a.KeepDivID,a.KeeperID, ";
			SqlStr+="a.propertyno,b.DocID,max(a.serialno) as serialno_max,min(a.serialno) as serialno_min,  ";
			
			SqlStr+="a.lotno,";
			
			SqlStr+="max(a.assetid) as assetid_max,min(a.assetid) as assetid_min  ";
			SqlStr+="from ASsetBasic a  ";
			SqlStr+="left join  ASAddDetail b on a.AssetID=b.AssetID "; 
			SqlStr+="left join  ASAddMaster c on b.docid=c.docid ";
			//SqlStr+="where (b.DocID is not null  or b.DocID='') "; 
			
			SqlStr+="where 1=1 and a.PostDate >= '2014-02-01 00:00:00.000'";
			
			SqlStr+="group by  a.acctTagID,a.AgtNo,a.AcctDocID,a.AcctDate,a.PostDate,b.note,b.DocID, ";  
			SqlStr+=" a.CategoryID,a.OffAccount,a.OffAccountDate,a.Qty,a.AmtSTTL,a.ValDprTTL,a.PurposeName,a.KeepDivID,a.KeeperID, ";
			SqlStr+=" a.ASUseDivID,a.ASUserID ";
			SqlStr+=" ,a.propertyno,b.DocID ";
			 
			SqlStr+=" order by convert(int,b.DocID) ";
			System.out.println(SqlStr);
			SQLo = smblconn.prepareStatement(SqlStr);
			
			insertstr+="insert into "+tablename+" ";
            insertstr+="( enterorg, ownership, caseno, differencekind, engineeringno, caseserialno, propertyno, lotno, serialnos, serialnoe,";
            insertstr+=" otherpropertyunit, othermaterial, otherlimityear, propertyname1, cause, cause1, approvedate, approvedoc, enterdate,";
            insertstr+=" buydate, datastate, verify, propertykind, fundtype, valuable, originalamount, originalunit, originalbv, originalnote,";
            insertstr+=" accountingtitle, bookamount, bookvalue, netvalue, fundssource, fundssource1, grantvalue, articlename, nameplate, ";
            insertstr+=" specification, storeno, sourcekind, sourcedate, sourcedoc, originaldeprmethod, originalbuildfeecb, originaldeprunitcb,";
            insertstr+=" originaldeprpark, originaldeprunit, originaldeprunit1, originaldepraccounts, originalscrapvalue, originaldepramount, ";
            insertstr+=" originalaccumdepr, originalapportionmonth, originalmonthdepr, originalmonthdepr1, originalapportionendym, escroworivalue, ";
            insertstr+=" escroworiaccumdepr, oldpropertyno, oldserialnos, oldserialnoe, notes, editid, editdate, edittime, picture ) ";			
			insertstr+=" values ( ";
			rs = SQLo.executeQuery();
			while(rs.next())
			{
				//增加單單號ID
				proofno="";
				proofyear="";
				proofno=Common.formatFrontString(Common.get(rs.getString("DocNo")), 7, '0');
				proofyear=Common.get(rs.getDate("PostDate").toString()).replaceAll("-", "").substring(0,4);

				
				//caseno
				String caseno="";
				String proofdoc="";
				
				SQLo2 = tcghconn.prepareStatement("select caseno from UNTMP_MOVABLE where caseno='"+changeTaiwanYYMMDD(Common.get(rs.getDate("PostDate").toString()).replaceAll("-", ""),"1").substring(0,3)+proofno+"' and enterorg='" + inputEnterOrg + "' ");				
				rs2 = SQLo2.executeQuery();
				//if(rs2.next()) //查詢單號是存在
				//{
				//	caseno=Common.get(rs2.getString("caseno").toString());
				//}else{         //不存在新建一個增加單單號
				if(!rs2.next()){
					SQLo3 = tcghconn.prepareStatement("select right(isnull(max(substring(caseno,4,7))+1,'20000'),7) as caseno from UNTMP_MOVABLE where substring(caseno,1,3)='"+changeTaiwanYYMMDD(Common.get(rs.getDate("PostDate").toString()).replaceAll("-", ""),"1").substring(0,3)+"' and enterorg='" + inputEnterOrg + "' ");				
					rs3 = SQLo3.executeQuery();
					if(rs3.next())
					{
						caseno=changeTaiwanYYMMDD(Common.get(rs.getDate("PostDate").toString()).replaceAll("-", ""),"1").substring(0,3)+Common.formatFrontString(Common.get(rs3.getString("caseno")), 7, '0');
					}
					SQLo3.close();
					SQLo3 = tcghconn.prepareStatement("select isnull(max(substring(caseno,4,7))+1,'1') as proofno from UNTMP_MOVABLE where substring(caseno,1,3)='"+changeTaiwanYYMMDD(Common.get(rs.getDate("PostDate").toString()).replaceAll("-", ""),"1").substring(0,3)+"'  and enterorg='" + inputEnterOrg + "' ");				
					rs3 = SQLo3.executeQuery();
					if(rs3.next())
					{
						proofno=Common.formatFrontString(Common.get(rs3.getString("proofno")), 7, '0');
					}
					proofdoc="財增";
					SQLo3.close();
					//INSERT_ADDPROOF(tcghconn,"UNTLA_ADDPROOF", caseno, proofdoc, proofno, Common.get(rs.getString("acctTagID")), Common.get(rs.getString("AgtNo")), Common.get(rs.getDate("RECDate").toString()), Common.get(rs.getString("RECDivID").toString()), Common.get(rs.getDate("PostDate").toString()), Common.get(rs.getString("AcctDocID").toString()), Common.get(rs.getDate("AcctDate").toString()), Common.get(rs.getString("note").toString()));
					//INSERT_ADDPROOF(tcghconn,"UNTBU_ADDPROOF", caseno, proofdoc, proofno, Common.get(rs.getString("acctTagID")), Common.get(rs.getString("AgtNo")), Common.get(rs.getDate("RECDate").toString()), Common.get(rs.getString("RECDivID").toString()), Common.get(rs.getDate("PostDate").toString()), Common.get(rs.getString("AcctDocID").toString()), Common.get(rs.getDate("AcctDate").toString()), Common.get(rs.getString("note").toString()));
					//INSERT_ADDPROOF(tcghconn,"UNTRF_ADDPROOF", caseno, proofdoc, proofno, Common.get(rs.getString("acctTagID")), Common.get(rs.getString("AgtNo")), Common.get(rs.getDate("RECDate").toString()), Common.get(rs.getString("RECDivID").toString()), Common.get(rs.getDate("PostDate").toString()), Common.get(rs.getString("AcctDocID").toString()), Common.get(rs.getDate("AcctDate").toString()), Common.get(rs.getString("note").toString()));
					//INSERT_ADDPROOF(tcghconn,"UNTMP_ADDPROOF", caseno, proofdoc, proofno, Common.get(rs.getString("acctTagID")), Common.get(rs.getString("AgtNo")), Common.get(rs.getDate("RECDate").toString()), Common.get(rs.getString("RECDivID").toString()), Common.get(rs.getDate("PostDate").toString()), Common.get(rs.getString("AcctDocID").toString()), Common.get(rs.getDate("AcctDate").toString()), Common.get(rs.getString("note").toString()));
					
				
				
				subinsert="";
				subinsert+="'" + inputEnterOrg + "',";//enterorg
				subinsert+="'1',";//ownership				
				subinsert+="'"+caseno+"',";//caseno			
				//財產區分別
				String differencekind="";
				if(Common.get(rs.getString("acctTagID")).equals("1")){ //公務
					differencekind="110";//differencekind
				}else{
					if(Common.get(rs.getString("acctTagID")).equals("2")){ //基金
						differencekind="120";//differencekind
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
				subinsert+="'"+Common.get(rs.getString("AgtNo"))+"',";//engineeringno
				subinsert+="1,";//caseserialno
				
				String propertyno=Common.get(rs.getString("propertyno"));
				subinsert+="'"+propertyno+"',"; //propertyno
				/*
				if(Common.get(rs.getString("CategoryID").toString()).substring(0,3).equals("503") || Common.get(rs.getString("CategoryID").toString()).substring(0,3).equals("504") || Common.get(rs.getString("CategoryID").toString()).substring(0,3).equals("505")){
					subinsert+="'"+Common.get(rs.getString("CategoryID").toString())+"',"; //propertyno
					propertyno=Common.get(rs.getString("CategoryID").toString());
				}else{
					if(Common.get(rs.getString("CategoryID").toString()).length()==11){
						subinsert+="'"+Common.get(rs.getString("CategoryID").toString()).replaceAll("-", "0")+"',"; //propertyno
						propertyno=Common.get(rs.getString("CategoryID").toString()).replaceAll("-", "0");
					}else{
						if(Common.get(rs.getString("CategoryID").toString()).length()==10){
							subinsert+="'"+Common.get(rs.getString("CategoryID").toString()).replaceAll("-", "00")+"',"; //propertyno
							propertyno=Common.get(rs.getString("CategoryID").toString()).replaceAll("-", "00");
						}else{
							subinsert+="'"+Common.formatRearString(Common.get(rs.getString("CategoryID").toString()),11,'0')+"',"; //propertyno
							propertyno=Common.formatRearString(Common.get(rs.getString("CategoryID").toString()),11,'0');
						}
					}
				}
				*/
				/*
				
				SQLo3 = tcghconn.prepareStatement("select lotno from "+tablename+" where caseno='"+caseno+"' and enterorg='A27040000G' ");				
				rs3 = SQLo3.executeQuery();
				if(rs3.next())
				{
					lotno=Common.formatFrontString(Common.get(rs3.getString("lotno")), 7, '0');
				}else{
					SQLo3.close();
					SQLo3 = tcghconn.prepareStatement("select isnull(max(lotno)+1,'1') as lotno from "+tablename+" where enterorg='A27040000G' ");				
					rs3 = SQLo3.executeQuery();
					if(rs3.next()){
						lotno=Common.formatFrontString(Common.get(rs3.getString("lotno")), 7, '0');
					}
				}
				SQLo3.close();
				*/
//				String lotno=Common.get(rs.getString("serialno_min"));
				String lotno=Common.get(rs.getString("lotno"));
				subinsert+="'"+lotno+"',"; //lotno
				
				String serialnos=Common.get(rs.getString("serialno_min"));			
				subinsert+="'"+serialnos+"',"; //serialnos
				String serialnoe=Common.get(rs.getString("serialno_max"));				
				subinsert+="'"+serialnoe+"',"; //serialnoe
				
				subinsert+="'',";//otherpropertyunit
				subinsert+="'',";//othermaterial
				subinsert+="null,";//otherlimityear
				subinsert+="'',";//propertyname1,
				subinsert+="'',";//cause,
				subinsert+="'',";//cause1,
				subinsert+="'',";//approvedate,
				subinsert+="'',";//approvedoc,
				String enterdate="";
				if(Common.get(rs.getString("PostDate")).length()>0){
					enterdate=Common.get(rs.getString("PostDate")).replaceAll("-", "").substring(0,8);
				}				
				subinsert+="'"+enterdate+"',"; //enterdate
				
				String buydate="";
				if(Common.get(rs.getString("PostDate")).length()>0){
					buydate=Common.get(rs.getString("PostDate")).replaceAll("-", "").substring(0,8);
				}				
				subinsert+="'"+buydate+"',"; //buydate
				
				
				
				
				String datastate="";
				if (Common.get(rs.getString("OffAccount")).equals("1")){ //減損
					datastate="2";
				}else{
					datastate="1";
				}
				subinsert+="'"+datastate+"',"; //datastate				
				subinsert+="'Y',";//verify
				
				subinsert+="'01',";//propertykind
				subinsert+="'',";//fundtype,
				subinsert+="'N',";//valuable,
								
				String originalamount="";
				originalamount=Common.get(rs.getString("Qty"));
				subinsert+=""+originalamount+","; //originalamount
				
				String originalunit="";
				originalunit=Common.get(rs.getString("AmtSTTL"));				
				subinsert+=""+originalunit+",";//originalunit				
				
				String originalbv="";
				originalbv=Common.get(rs.getString("AmtSTTL"));				
				subinsert+=""+originalbv+","; //originalbv
				
				subinsert+="'',";//originalnote				
				subinsert+="'',";//accountingtitle
				
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
					bookvalue=Common.get(rs.getString("AmtSTTL"));
				}				
				subinsert+=""+bookvalue+","; //bookvalue
				
				String netvalue="";
				if (Common.get(rs.getString("OffAccount")).equals("1")){  //減損
					netvalue="0";
				}else{					
					netvalue=Integer.toString(Integer.valueOf(Common.get(rs.getString("AmtSTTL")))-Integer.valueOf(Common.get(rs.getString("ValDprTTL"))));
				}
				
				
				subinsert+=""+netvalue+","; //netvalue
				
				subinsert+="'',";//fundssource,
				subinsert+="'',";//fundssource1,
				subinsert+="null,";//grantvalue,
				subinsert+="'',";//articlename,
				subinsert+="'',";//nameplate,
				subinsert+="'',";//specification,
				subinsert+="'',";//storeno,
				subinsert+="'',";//sourcekind,
				subinsert+="'',";//sourcedate,
				subinsert+="'',";//sourcedoc,
				subinsert+="'',";//originaldeprmethod,
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
				subinsert+="'"+oldserialnos+"',"; //oldserialnos
				
				String oldserialnoe="";
				oldserialnoe=Common.get(rs.getString("assetid_max"));
				subinsert+="'"+oldserialnoe+"',"; //oldserialnoe

				
				
				subinsert+="'"+Common.get(rs.getString("note"))+"',";//notes
				subinsert+="'GM',";//editid
				subinsert+="'"+Datetime.getYYYYMMDD()+"',";//editdate
				subinsert+="'"+Datetime.getHHMMSS()+"', ";//edittime
				subinsert+="'' ";//picture
				
				
				Allinsert=insertstr+subinsert+") ;";
				//System.out.println(Allinsert);
				if(!proofno.equals("")){
					try{
						if(insert_write==true){
							SQLo = tcghconn.prepareStatement(Allinsert);
							SQLo.execute();
							SQLo.close();
						}
					}catch(Exception e){
						System.out.println(e);
						System.out.println(Allinsert);
					}
				}
				
				}
				//單單號ID
				SQLo2.close();
				rs2.close();
				
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
			//A27040000G 科技部新竹科學工業園區管理局 
			smblconn = getDBConnection("smblg");
			tcghconn = getDBConnection("TCGH");
			
			SqlStr+="select   ";
			SqlStr+="isnull(a.acctTagID,'') as acctTagID,isnull(a.AgtNo,'') as AgtNo,c.RECDate,c.RECDivID,isnull(a.AcctDocID,'') as AcctDocID,a.AcctDate,a.PostDate,b.note,isnull(b.DocID,'') as DocNo, "; 
			SqlStr+="a.CategoryID,a.OffAccount,isnull(a.OffAccountDate,'') as OffAccountDate,isnull(c.FluctuatedDate,'') as FluctuatedDate,b.Qty,convert(int,a.AmtSTTL) as AmtSTTL,convert(int,a.ValDprTTL) as ValDprTTL,a.KeepDivID,a.KeeperID, ";
			SqlStr+="a.RecDate,a.AssetID,a.propertyno,a.lotno,a.serialno,b.ValOrg,b.ValAdd,b.ValSub,b.ValDprTTL, ";
			SqlStr+="b.ValCur,isnull(c.cfmdate,'') as cfmdate,isnull(c.docno,'') as  DocNotwo ";
//			SqlStr+="from ASFluctuateDetail b  ";
//			SqlStr+="left join ASFluctuateMaster c on b.docid=c.docid      ";
//			SqlStr+="left join ASsetBasic a on a.AssetID=b.AssetID  ";
			
			SqlStr+="from ASsetBasic a  ";
			SqlStr+="left join ASFluctuateDetail b on a.AssetID=b.AssetID  ";
			SqlStr+="left join ASFluctuateMaster c on b.docid=c.docid      ";
			//SqlStr+="and propertyno='30120050011' ";
			//SqlStr+="and serialno='0000001' ";
			//SqlStr+="where b.DocID is not null "; 
			//SqlStr+="group by a.acctTagID,a.AgtNo,c.RECDate,c.RECDivID,a.AcctDocID,a.AcctDate,a.PostDate,b.note,b.DocID ";
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
            		"oldpropertyno, oldserialno, notes, editid, editdate, edittime " +
            		") ";			
			insertstr+=" values ( ";
			rs = SQLo.executeQuery();
			while(rs.next())
			{
				//增減值單單號ID
				proofno="";
				proofyear="";
				proofno=Common.formatFrontString(Common.get(rs.getString("DocNo")), 7, '0');				
				proofyear=changeDateFormat(rs.getDate("PostDate")).replaceAll("-", "").substring(0,4);

				//System.out.println(rs.getString("propertyno"));
				//System.out.println(rs.getString("serialno"));
				//caseno
				String caseno="";
				String proofdoc="";			    
				SQLo2 = tcghconn.prepareStatement("select caseno from UNTLA_ADJUSTPROOF where proofyear='"+proofyear+"' and proofno='"+proofno+"' and enterorg='" + inputEnterOrg + "' ");				
				rs2 = SQLo2.executeQuery();
				if(rs2.next()) //查詢單號是存在
				{
					caseno=Common.get(rs2.getString("caseno").toString());
				}else{         //不存在新建一個增加單單號
					
					SQLo3 = tcghconn.prepareStatement("select right(isnull(max(caseno)+1,'20000'),7) as caseno from UNTLA_ADJUSTPROOF  where proofyear='"+proofyear+"' and enterorg='" + inputEnterOrg + "' ");				
					rs3 = SQLo3.executeQuery();
					if(rs3.next())
					{
//						if(rs.getDate("PostDate") == null){
//							caseno = "20000";
//						}else{
							caseno=changeTaiwanYYMMDD(changeDateFormat(rs.getDate("PostDate")).replaceAll("-", ""),"1").substring(0,3)+Common.formatFrontString(Common.get(rs3.getString("caseno")), 7, '0');
//						}
					}
					SQLo3.close();
					SQLo3 = tcghconn.prepareStatement("select isnull(max(proofno)+1,'1') as proofno from UNTLA_ADJUSTPROOF  where proofyear='"+proofyear+"' and enterorg='" + inputEnterOrg + "' ");				
					rs3 = SQLo3.executeQuery();
					if(rs3.next())
					{
						proofno=Common.formatFrontString(Common.get(rs3.getString("proofno")), 7, '0');
					}
					proofdoc="財增減值";
					SQLo3.close();
					
					INSERT_ADJUSTPROOF(tcghconn,"UNTLA_ADJUSTPROOF", caseno, proofdoc, proofno, Common.get(rs.getString("acctTagID")), Common.get(rs.getString("AgtNo")), changeDateFormat(rs.getDate("RECDate")), Common.get(rs.getString("RECDivID")), changeDateFormat(rs.getDate("PostDate")), Common.get(rs.getString("AcctDocID")), changeDateFormat(rs.getDate("AcctDate")), Common.get(rs.getString("note")), Common.get(rs.getString("fluctuateddate")), Common.get(rs.getString("cfmdate").toString()), Common.get(rs.getString("DocNotwo")));
					INSERT_ADJUSTPROOF(tcghconn,"UNTBU_ADJUSTPROOF", caseno, proofdoc, proofno, Common.get(rs.getString("acctTagID")), Common.get(rs.getString("AgtNo")), changeDateFormat(rs.getDate("RECDate")), Common.get(rs.getString("RECDivID")), changeDateFormat(rs.getDate("PostDate")), Common.get(rs.getString("AcctDocID")), changeDateFormat(rs.getDate("AcctDate")), Common.get(rs.getString("note")), Common.get(rs.getString("fluctuateddate")), Common.get(rs.getString("cfmdate").toString()), Common.get(rs.getString("DocNotwo")));
					INSERT_ADJUSTPROOF(tcghconn,"UNTRF_ADJUSTPROOF", caseno, proofdoc, proofno, Common.get(rs.getString("acctTagID")), Common.get(rs.getString("AgtNo")), changeDateFormat(rs.getDate("RECDate")), Common.get(rs.getString("RECDivID")), changeDateFormat(rs.getDate("PostDate")), Common.get(rs.getString("AcctDocID")), changeDateFormat(rs.getDate("AcctDate")), Common.get(rs.getString("note")), Common.get(rs.getString("fluctuateddate")), Common.get(rs.getString("cfmdate").toString()), Common.get(rs.getString("DocNotwo")));
					INSERT_ADJUSTPROOF(tcghconn,"UNTMP_ADJUSTPROOF", caseno, proofdoc, proofno, Common.get(rs.getString("acctTagID")), Common.get(rs.getString("AgtNo")), changeDateFormat(rs.getDate("RECDate")), Common.get(rs.getString("RECDivID")), changeDateFormat(rs.getDate("PostDate")), Common.get(rs.getString("AcctDocID")), changeDateFormat(rs.getDate("AcctDate")), Common.get(rs.getString("note")), Common.get(rs.getString("fluctuateddate")), Common.get(rs.getString("cfmdate").toString()), Common.get(rs.getString("DocNotwo")));
					
				}
				//增加單單號ID
				SQLo2.close();
				rs2.close();
				subinsert="";
				subinsert+="'" + inputEnterOrg + "',";//enterorg			
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
				subinsert+="'"+Common.get(rs.getString("AgtNo"))+"',";//engineeringno
				
				subinsert+="1,";//caseserialno
				/*
				subinsert+="'',";//casename 停用
				subinsert+="'"+Common.get(rs.getDate("RECDate").toString()).replaceAll("-", "")+"',";//writedate  
				subinsert+="'"+Common.get(rs.getString("RECDivID"))+"',";//writeunit
				subinsert+="'"+Common.get(rs.getDate("PostDate").toString()).replaceAll("-", "").substring(0,4)+"',";//proofyear
				subinsert+="'"+proofdoc+"',";//proofdoc
				subinsert+="'"+proofno+"',";//proofno
				subinsert+="'',";//manageno 停用
				subinsert+="'"+Common.get(rs.getString("AcctDocID"))+"',";//summonsno
				subinsert+="'"+Common.get(rs.getString("AcctDate"))+"',";//summonsdate 
				subinsert+="'"+Common.get(rs.getDate("PostDate").toString()).replaceAll("-", "")+"',";//enterdate
				subinsert+="'',";//mergedivision 新系統欄位
				*/
				//System.out.println("CategoryID="+Common.get(rs.getString("CategoryID").toString()));
				//System.out.println("CategoryID="+Common.get(rs.getString("CategoryID").toString()).substring(0,3));
				String propertyno=Common.get(rs.getString("propertyno"));
				subinsert+="'"+propertyno+"',"; //propertyno
				/*
				if(Common.get(rs.getString("CategoryID").toString()).substring(0,3).equals("503") || Common.get(rs.getString("CategoryID").toString()).substring(0,3).equals("504") || Common.get(rs.getString("CategoryID").toString()).substring(0,3).equals("505")){
					subinsert+="'"+Common.get(rs.getString("CategoryID").toString())+"',"; //propertyno
					propertyno=Common.get(rs.getString("CategoryID").toString());
				}else{
					if(Common.get(rs.getString("CategoryID").toString()).length()==11){
						subinsert+="'"+Common.get(rs.getString("CategoryID").toString()).replaceAll("-", "0")+"',"; //propertyno
						propertyno=Common.get(rs.getString("CategoryID").toString()).replaceAll("-", "0");
					}else{
						if(Common.get(rs.getString("CategoryID").toString()).length()==10){
							subinsert+="'"+Common.get(rs.getString("CategoryID").toString()).replaceAll("-", "00")+"',"; //propertyno
							propertyno=Common.get(rs.getString("CategoryID").toString()).replaceAll("-", "00");
						}else{
							subinsert+="'"+Common.formatRearString(Common.get(rs.getString("CategoryID").toString()),11,'0')+"',"; //propertyno
							propertyno=Common.formatRearString(Common.get(rs.getString("CategoryID").toString()),11,'0');
						}
					}
				}
				*/
				/*

				
				SQLo3 = tcghconn.prepareStatement("select lotno from "+tablename+" where caseno='"+caseno+"' and enterorg='A27040000G' ");				
				rs3 = SQLo3.executeQuery();
				if(rs3.next())
				{
					lotno=Common.formatFrontString(Common.get(rs3.getString("lotno")), 7, '0');
				}else{
					SQLo3.close();
					SQLo3 = tcghconn.prepareStatement("select isnull(max(lotno)+1,'1') as lotno from "+tablename+" where enterorg='A27040000G' ");				
					rs3 = SQLo3.executeQuery();
					if(rs3.next()){
						lotno=Common.formatFrontString(Common.get(rs3.getString("lotno")), 7, '0');
					}
				}
				SQLo3.close();
				*/
				String lotno=Common.get(rs.getString("lotno"));
				subinsert+="'"+lotno+"',"; //lotno
				
				String serialno=Common.get(rs.getString("serialno"));
				subinsert+="'"+serialno+"',"; //serialno
				/*
				String serialno="";
				SQLo3 = tcghconn.prepareStatement("select isnull(max(serialno)+1,'1') as serialno from UNTMP_MOVABLEDETAIL where propertyno='"+propertyno+"' and enterorg='A27040000G' ");				
				rs3 = SQLo3.executeQuery();
				if(rs3.next())
				{
					serialno=Common.formatFrontString(Common.get(rs3.getString("serialno")), 7, '0');
				}
				SQLo3.close();				
				subinsert+="'"+serialno+"',"; //serialno
				*/
				subinsert+="'',";//otherpropertyunit
				subinsert+="'',";//othermaterial
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
				oldnetunit=Common.get(rs.getString("ValOrg"));
//				subinsert+=""+oldnetunit+","; //oldnetunit
				subinsert+=("".equals(oldnetunit)?0:oldnetunit) + ",";//oldnetunit
				
				String oldnetvalue="";
				oldnetvalue=Common.get(rs.getString("ValOrg"));
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
				newnetunit=Common.get(rs.getString("ValCur"));
//				subinsert+=""+newnetunit+","; //newnetunit
				subinsert+=("".equals(newnetunit)?0:newnetunit) + ",";//newnetunit
				
				String newnetvalue="";
				newnetvalue=Common.get(rs.getString("ValCur"));
//				subinsert+=""+newnetvalue+","; //newnetvalue
				subinsert+=("".equals(newnetvalue)?0:newnetvalue) + ",";//newnetvalue
				
				subinsert+="'',"; //booknotes,
				subinsert+="'',"; //keepunit,
				subinsert+="'',"; //keeper,
				subinsert+="'',"; //useunit,
				subinsert+="'',"; //userno,
				subinsert+="'',"; //usernote,
				subinsert+="'',"; //place1,
				subinsert+="'',"; //place,
				
				subinsert+="'02',"; //olddeprmethod,


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
				subinsert+="'"+Datetime.getHHMMSS()+"' ";//edittime
				
				
				Allinsert=insertstr+subinsert+") ;";
				//System.out.println(Allinsert);
				if(!proofno.equals("")){
					try{
						if(insert_write==true){
							SQLo = tcghconn.prepareStatement(Allinsert);
							SQLo.execute();
							SQLo.close();
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
	
	/**
	 * @UNTMP_REDUCEDETAIL
	 */
	public static void UNTMP_REDUCEDETAIL(String tablename){
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
			
			SqlStr+="select  ";			
			SqlStr+="isnull(a.acctTagID,'') as acctTagID,isnull(a.AgtNo,'') as AgtNo,c.RECDate,c.RECDivID,isnull(a.AcctDocID,'') as AcctDocID,a.AcctDate,a.PostDate,b.note,isnull(b.DocID,'') as DocNo, ";
			SqlStr+="a.CategoryID,a.OffAccount,a.OffAccountDate,b.Qty,convert(int,a.AmtSTTL) as AmtSTTL,convert(int,a.ValDprTTL) as ValDprTTL,a.KeepDivID,a.KeeperID, ";
			SqlStr+="a.RecDate,a.AssetID,a.propertyno,a.lotno,a.serialno, ";
			SqlStr+="isnull(c.VoidDate,'') as VoidDate,a.BuyDate,c.PostDate as reduceDate,isnull(c.cfmdate,'') as cfmdate,isnull(c.DocNo,'') as  DocNotwo ";
			SqlStr+=" from ASDetractDetail b  ";
			SqlStr+="left join ASDetractMaster c on b.docid=c.docid   ";
			SqlStr+="left join ASsetBasic a on a.AssetID=b.AssetID ";
			
			
//			SqlStr+=" from ASsetBasic a  ";
//			SqlStr+="left join ASDetractDetail b on a.AssetID=b.AssetID ";
//			SqlStr+="left join ASDetractMaster c on b.docid=c.docid   ";
			
			//SqlStr+="where b.DocID is not null "; 
			//SqlStr+="group by a.acctTagID,a.AgtNo,c.RECDate,c.RECDivID,a.AcctDocID,a.AcctDate,a.PostDate,b.note,b.DocID ";
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
            		"editdate, edittime, dealcaseno, dealdate, reducedeal, realizevalue, shiftorg " +
            		") ";			
			insertstr+=" values ( ";
			rs = SQLo.executeQuery();
			while(rs.next())
			{
				//增減值單單號ID
				proofno="";
				proofyear="";
				proofno=Common.formatFrontString(Common.get(rs.getString("DocNo")), 7, '0');
				proofyear=Common.get(rs.getDate("PostDate").toString()).replaceAll("-", "").substring(0,4);

				
				//caseno
				String caseno="";
				String proofdoc="";			    
				SQLo2 = tcghconn.prepareStatement("select caseno from UNTLA_REDUCEPROOF where proofyear='"+proofyear+"' and proofno='"+proofno+"' and enterorg='" + inputEnterOrg + "' ");				
				rs2 = SQLo2.executeQuery();
				if(rs2.next()) //查詢單號是存在
				{
					caseno=Common.get(rs2.getString("caseno").toString());
				}else{         //不存在新建一個增加單單號
					
					SQLo3 = tcghconn.prepareStatement("select right(isnull(max(caseno)+1,'20000'),7) as caseno from UNTLA_REDUCEPROOF  where proofyear='"+proofyear+"' and enterorg='" + inputEnterOrg + "' ");				
					rs3 = SQLo3.executeQuery();
					if(rs3.next())
					{
						caseno=changeTaiwanYYMMDD(Common.get(rs.getDate("PostDate").toString()).replaceAll("-", ""),"1").substring(0,3)+Common.formatFrontString(Common.get(rs3.getString("caseno")), 7, '0');
					}
					SQLo3.close();
					SQLo3 = tcghconn.prepareStatement("select isnull(max(proofno)+1,'1') as proofno from UNTLA_REDUCEPROOF  where proofyear='"+proofyear+"' and enterorg='" + inputEnterOrg + "' ");				
					rs3 = SQLo3.executeQuery();
					if(rs3.next())
					{
						proofno=Common.formatFrontString(Common.get(rs3.getString("proofno")), 7, '0');
					}
					proofdoc="財減損";
					SQLo3.close();
					
					INSERT_REDUCEPROOF(tcghconn,"UNTLA_REDUCEPROOF", caseno, proofdoc, proofno, Common.get(rs.getString("acctTagID")), Common.get(rs.getString("AgtNo")), changeDateFormat(rs.getDate("RECDate")), Common.get(rs.getString("RECDivID")), changeDateFormat(rs.getDate("PostDate")), Common.get(rs.getString("AcctDocID")), changeDateFormat(rs.getDate("AcctDate")), Common.get(rs.getString("note")) , Common.get(rs.getString("cfmdate")), Common.get(rs.getString("DocNotwo")), Common.get(rs.getString("reduceDate")));
					INSERT_REDUCEPROOF(tcghconn,"UNTBU_REDUCEPROOF", caseno, proofdoc, proofno, Common.get(rs.getString("acctTagID")), Common.get(rs.getString("AgtNo")), changeDateFormat(rs.getDate("RECDate")), Common.get(rs.getString("RECDivID")), changeDateFormat(rs.getDate("PostDate")), Common.get(rs.getString("AcctDocID")), changeDateFormat(rs.getDate("AcctDate")), Common.get(rs.getString("note")) , Common.get(rs.getString("cfmdate")), Common.get(rs.getString("DocNotwo")), Common.get(rs.getString("reduceDate")));
					INSERT_REDUCEPROOF(tcghconn,"UNTRF_REDUCEPROOF", caseno, proofdoc, proofno, Common.get(rs.getString("acctTagID")), Common.get(rs.getString("AgtNo")), changeDateFormat(rs.getDate("RECDate")), Common.get(rs.getString("RECDivID")), changeDateFormat(rs.getDate("PostDate")), Common.get(rs.getString("AcctDocID")), changeDateFormat(rs.getDate("AcctDate")), Common.get(rs.getString("note")) , Common.get(rs.getString("cfmdate")), Common.get(rs.getString("DocNotwo")), Common.get(rs.getString("reduceDate")));
					INSERT_REDUCEPROOF(tcghconn,"UNTMP_REDUCEPROOF", caseno, proofdoc, proofno, Common.get(rs.getString("acctTagID")), Common.get(rs.getString("AgtNo")), changeDateFormat(rs.getDate("RECDate")), Common.get(rs.getString("RECDivID")), changeDateFormat(rs.getDate("PostDate")), Common.get(rs.getString("AcctDocID")), changeDateFormat(rs.getDate("AcctDate")), Common.get(rs.getString("note")) , Common.get(rs.getString("cfmdate")), Common.get(rs.getString("DocNotwo")), Common.get(rs.getString("reduceDate")));
					
				}
				//增加單單號ID
				SQLo2.close();
				rs2.close();
				subinsert="";
				subinsert+="'" + inputEnterOrg + "',";//enterorg			
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
				subinsert+="'"+Common.get(rs.getString("AgtNo"))+"',";//engineeringno
				
				subinsert+="1,";//caseserialno
				/*
				subinsert+="'',";//casename 停用
				subinsert+="'"+Common.get(rs.getDate("RECDate").toString()).replaceAll("-", "")+"',";//writedate  
				subinsert+="'"+Common.get(rs.getString("RECDivID"))+"',";//writeunit
				subinsert+="'"+Common.get(rs.getDate("PostDate").toString()).replaceAll("-", "").substring(0,4)+"',";//proofyear
				subinsert+="'"+proofdoc+"',";//proofdoc
				subinsert+="'"+proofno+"',";//proofno
				subinsert+="'',";//manageno 停用
				subinsert+="'"+Common.get(rs.getString("AcctDocID"))+"',";//summonsno
				subinsert+="'"+Common.get(rs.getString("AcctDate"))+"',";//summonsdate 
				subinsert+="'"+Common.get(rs.getDate("PostDate").toString()).replaceAll("-", "")+"',";//enterdate
				subinsert+="'',";//mergedivision 新系統欄位
				*/
				//System.out.println("CategoryID="+Common.get(rs.getString("CategoryID").toString()));
				//System.out.println("CategoryID="+Common.get(rs.getString("CategoryID").toString()).substring(0,3));
				String propertyno=Common.get(rs.getString("propertyno"));
				subinsert+="'"+propertyno+"',"; //propertyno
				/*
				if(Common.get(rs.getString("CategoryID").toString()).substring(0,3).equals("503") || Common.get(rs.getString("CategoryID").toString()).substring(0,3).equals("504") || Common.get(rs.getString("CategoryID").toString()).substring(0,3).equals("505")){
					subinsert+="'"+Common.get(rs.getString("CategoryID").toString())+"',"; //propertyno
					propertyno=Common.get(rs.getString("CategoryID").toString());
				}else{
					if(Common.get(rs.getString("CategoryID").toString()).length()==11){
						subinsert+="'"+Common.get(rs.getString("CategoryID").toString()).replaceAll("-", "0")+"',"; //propertyno
						propertyno=Common.get(rs.getString("CategoryID").toString()).replaceAll("-", "0");
					}else{
						if(Common.get(rs.getString("CategoryID").toString()).length()==10){
							subinsert+="'"+Common.get(rs.getString("CategoryID").toString()).replaceAll("-", "00")+"',"; //propertyno
							propertyno=Common.get(rs.getString("CategoryID").toString()).replaceAll("-", "00");
						}else{
							subinsert+="'"+Common.formatRearString(Common.get(rs.getString("CategoryID").toString()),11,'0')+"',"; //propertyno
							propertyno=Common.formatRearString(Common.get(rs.getString("CategoryID").toString()),11,'0');
						}
					}
				}
				*/
				/*

				
				SQLo3 = tcghconn.prepareStatement("select lotno from "+tablename+" where caseno='"+caseno+"' and enterorg='A27040000G' ");				
				rs3 = SQLo3.executeQuery();
				if(rs3.next())
				{
					lotno=Common.formatFrontString(Common.get(rs3.getString("lotno")), 7, '0');
				}else{
					SQLo3.close();
					SQLo3 = tcghconn.prepareStatement("select isnull(max(lotno)+1,'1') as lotno from "+tablename+" where enterorg='A27040000G' ");				
					rs3 = SQLo3.executeQuery();
					if(rs3.next()){
						lotno=Common.formatFrontString(Common.get(rs3.getString("lotno")), 7, '0');
					}
				}
				SQLo3.close();
				*/
				String lotno=Common.get(rs.getString("lotno"));
				subinsert+="'"+lotno+"',"; //lotno
				
				String serialno=Common.get(rs.getString("serialno"));
				subinsert+="'"+serialno+"',"; //serialno
				/*
				String serialno="";
				SQLo3 = tcghconn.prepareStatement("select isnull(max(serialno)+1,'1') as serialno from UNTMP_MOVABLEDETAIL where propertyno='"+propertyno+"' and enterorg='A27040000G' ");				
				rs3 = SQLo3.executeQuery();
				if(rs3.next())
				{
					serialno=Common.formatFrontString(Common.get(rs3.getString("serialno")), 7, '0');
				}
				SQLo3.close();				
				subinsert+="'"+serialno+"',"; //serialno
				*/
				subinsert+="'',";//otherpropertyunit
				subinsert+="'',";//othermaterial
				subinsert+="null,";//otherlimityear
				subinsert+="'',";//propertyname1,
				subinsert+="'99',";//cause,
				subinsert+="'',";//cause1,
				
				//====================
				/*
				String adjustdate="";
				if(Common.get(rs.getString("FluctuatedDate")).length()>0){
					adjustdate=Common.get(rs.getString("FluctuatedDate")).replaceAll("-", "").substring(0,8);
				}				
				subinsert+="'"+adjustdate+"',"; //adjustdate
			    */
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
				
				subinsert+="null,";//oldbookunit,
				
				String oldbookvalue="";
				oldbookvalue=Common.get(rs.getString("AmtSTTL"));
//				subinsert+=""+oldbookvalue+",";//oldbookvalue,
				subinsert+=("".equals(oldbookvalue)?0:oldbookvalue) + ",";//oldbookvalue,
				
				subinsert+="null,";//oldnetunit,
				
				String oldnetvalue="";
				oldnetvalue=Common.get(rs.getString("AmtSTTL"));
//				subinsert+=""+oldnetvalue+",";//oldnetvalue,
				subinsert+=("".equals(oldnetvalue)?0:oldnetvalue) + ",";//oldnetvalue,
				
				String adjustbookamount="";
				adjustbookamount=Common.get(rs.getString("Qty"));
//				subinsert+=""+adjustbookamount+",";//adjustbookamount,
				subinsert+=("".equals(adjustbookamount)?0:adjustbookamount) + ",";//adjustbookamount,
				
				subinsert+="null,";//adjustbookunit,
				
				String adjustbookvalue="";
				adjustbookvalue=Common.get(rs.getString("AmtSTTL"));
//				subinsert+=""+adjustbookvalue+",";//adjustbookvalue,
				subinsert+=("".equals(adjustbookvalue)?0:adjustbookvalue) + ",";//adjustbookvalue,
				
				subinsert+="null,";//adjustaccumdepr,
				subinsert+="null,";//adjustnetunit,
				
				String adjustnetvalue="";
				adjustnetvalue=Common.get(rs.getString("AmtSTTL"));
//				subinsert+=""+adjustnetvalue+",";//adjustnetvalue,
				subinsert+=("".equals(adjustnetvalue)?0:adjustnetvalue) + ",";//adjustnetvalue,
				
				String newbookamount="";
				newbookamount=Common.get(rs.getString("Qty"));
//				subinsert+=""+newbookamount+",";//newbookamount,
				subinsert+=("".equals(newbookamount)?0:newbookamount) + ",";//newbookamount,
				
				subinsert+="null,";//newbookunit,
				
				String newbookvalue="";
				newbookvalue=Common.get(rs.getString("AmtSTTL"));
//				subinsert+=""+newbookvalue+",";//newbookvalue,
				subinsert+=("".equals(newbookvalue)?0:newbookvalue) + ",";//newbookvalue,
				
				subinsert+="null,";//newnetunit,
				String newnetvalue="";
				newnetvalue=Common.get(rs.getString("AmtSTTL"));
//				subinsert+=""+newnetvalue+",";//newnetvalue,
				subinsert+=("".equals(newnetvalue)?0:newnetvalue) + ",";//newnetvalue,
				//***
				
				subinsert+="'',";//articlename,
				subinsert+="'',";//nameplate,
				subinsert+="'',";//specification,
				subinsert+="'',";//sourcedate,
				
				subinsert+="'"+Common.get(rs.getString("buydate")).replaceAll("-", "").substring(0,8)+"',";//buydate,***
				
				
				subinsert+="'',";//licenseplate,
				subinsert+="'',";//movedate,
				subinsert+="'',";//keepunit,
				subinsert+="'',";//keeper,
				subinsert+="'',";//useunit,
				subinsert+="'',";//userno,
				subinsert+="'',";//usernote,
				subinsert+="'',";//place1,
				subinsert+="'',";//place,
				
				String useyear="";
				
				if("".equals(Common.get(rs.getString("reduceDate")))){
					useyear="0";
				}else{
					useyear="'" + Integer.toString(Integer.valueOf(Common.get(rs.getString("reduceDate")).replaceAll("-", "").substring(0,4))-Integer.valueOf(Common.get(rs.getString("buydate")).replaceAll("-", "").substring(0,4))) + "'";
				}

//				useyear=Integer.toString(Integer.valueOf(Common.get(rs.getString("reduceDate")).replaceAll("-", "").substring(0,4))-Integer.valueOf(Common.get(rs.getString("buydate")).replaceAll("-", "").substring(0,4)));	
				subinsert+=useyear+",";//useyear,***
				subinsert+=useyear+",";//usemonth,***
				
				subinsert+="'',";//cause2,
				subinsert+="'',";//returnplace,
				subinsert+="'',";//reducedeal2,
				
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
				subinsert+="'' ";//shiftorg,
				
				
				Allinsert=insertstr+subinsert+") ;";
				//System.out.println(Allinsert);
				if(!proofno.equals("")){
					try{
						if(insert_write==true){
							SQLo = tcghconn.prepareStatement(Allinsert);
							SQLo.execute();
							SQLo.close();
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
	
	/**
	 * @UNTMP_REDUCEDETAIL
	 */
	public static void UNTNE_REDUCEDETAIL(String tablename){
		count = 0;
		
		Connection smblconn;
		Connection tcghconn;
		
		PreparedStatement SQLo = null;
		PreparedStatement SQLo1 = null;
		PreparedStatement SQLo2 = null;
		PreparedStatement SQLo3 = null;
		PreparedStatement SQLo4 = null;
		ResultSet rs,rs2,rs3,rs4;
		String insertstr="";
		String subinsert="";
		String Allinsert="";
		
		String proofno="";
		String proofyear="";
		String summonsno="";
		String summonsdate="";
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
			
			SqlStr+="select  ";			
			SqlStr+="isnull(a.acctTagID,'') as acctTagID,isnull(a.AgtNo,'') as AgtNo,c.RECDate ,c.RECDivID,isnull(b.AcctDocID,'') as AcctDocID,b.AcctDate,a.PostDate,b.note,isnull(c.DocID,'') as DocNo, ";
			SqlStr+="a.CategoryID,a.OffAccount,a.OffAccountDate,b.Qty,convert(int,c.AmtTTL) as AmtSTTL, ";
			SqlStr+="b.price,a.workingyears,b.spec,b.brand,b.reason,b.LocationID,b.LocationName,b.spec,b.brand,a.RecDate,d.propertyno,d.lotno,d.serialno,b.AssetID, ";
			SqlStr+="isnull(c.VoidDate,'') as VoidDate,a.BuyDate,c.PostDate as reduceDate,isnull(c.cfmdate,'') as cfmdate, ''  as  DocNotwo ";

			SqlStr+="from ASNXBasic a,ASNXDistribute d,ASNXDetractDetail b,ASNXDetractMaster c ";
			SqlStr+="where d.Assetid=b.Assetid ";
			SqlStr+="and b.docid=c.docid ";
			SqlStr+="and a.AssetNo=d.AssetNo "; 
			
			
			
//			SqlStr+=" from ASsetBasic a  ";
//			SqlStr+="left join ASDetractDetail b on a.AssetID=b.AssetID ";
//			SqlStr+="left join ASDetractMaster c on b.docid=c.docid   ";
			
			//SqlStr+="where b.DocID is not null "; 
			SqlStr+="group by a.acctTagID,a.AgtNo,c.RECDate,c.RECDivID,b.AcctDocID,b.AcctDate,a.PostDate,b.note,c.DocID,a.CategoryID,a.OffAccount,a.OffAccountDate,b.Qty,c.AmtTTL,a.RecDate,d.propertyno,d.lotno,d.serialno,b.AssetID,c.VoidDate,a.BuyDate,c.PostDate,c.cfmdate,b.price,a.workingyears,b.spec,b.brand,b.reason,b.LocationID,b.LocationName,b.spec,b.brand";
			//SqlStr+=" where a.OffAccountDate is not null ";
			SqlStr+=" order by convert(int,c.DocID) ";
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
            		"editdate, edittime, dealcaseno, dealdate, reducedeal, realizevalue, shiftorg " +
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
				
					proofdoc="非耗品減";
					
					
					INSERT_UNTNE_REDUCEPROOF(tcghconn,"UNTNE_REDUCEPROOF", caseno, proofdoc, proofno, Common.get(rs.getString("acctTagID")), Common.get(rs.getString("AgtNo")), changeDateFormat(rs.getDate("RECDate")), Common.get(rs.getString("RECDivID")), changeDateFormat(rs.getDate("PostDate")), Common.get(rs.getString("AcctDocID")), changeDateFormat(rs.getDate("AcctDate")), Common.get(rs.getString("note")) , Common.get(rs.getString("cfmdate")), Common.get(rs.getString("DocNotwo")), Common.get(rs.getString("reduceDate")));
					//INSERT_REDUCEPROOF(tcghconn,"UNTBU_REDUCEPROOF", caseno, proofdoc, proofno, Common.get(rs.getString("acctTagID")), Common.get(rs.getString("AgtNo")), changeDateFormat(rs.getDate("RECDate")), Common.get(rs.getString("RECDivID")), changeDateFormat(rs.getDate("PostDate")), Common.get(rs.getString("AcctDocID")), changeDateFormat(rs.getDate("AcctDate")), Common.get(rs.getString("note")) , Common.get(rs.getString("cfmdate")), Common.get(rs.getString("DocNotwo")), Common.get(rs.getString("reduceDate")));
					//INSERT_REDUCEPROOF(tcghconn,"UNTRF_REDUCEPROOF", caseno, proofdoc, proofno, Common.get(rs.getString("acctTagID")), Common.get(rs.getString("AgtNo")), changeDateFormat(rs.getDate("RECDate")), Common.get(rs.getString("RECDivID")), changeDateFormat(rs.getDate("PostDate")), Common.get(rs.getString("AcctDocID")), changeDateFormat(rs.getDate("AcctDate")), Common.get(rs.getString("note")) , Common.get(rs.getString("cfmdate")), Common.get(rs.getString("DocNotwo")), Common.get(rs.getString("reduceDate")));
					//INSERT_REDUCEPROOF(tcghconn,"UNTMP_REDUCEPROOF", caseno, proofdoc, proofno, Common.get(rs.getString("acctTagID")), Common.get(rs.getString("AgtNo")), changeDateFormat(rs.getDate("RECDate")), Common.get(rs.getString("RECDivID")), changeDateFormat(rs.getDate("PostDate")), Common.get(rs.getString("AcctDocID")), changeDateFormat(rs.getDate("AcctDate")), Common.get(rs.getString("note")) , Common.get(rs.getString("cfmdate")), Common.get(rs.getString("DocNotwo")), Common.get(rs.getString("reduceDate")));
					
				}
				//增加單單號ID
				SQLo2.close();
				rs2.close();
				subinsert="";
				subinsert+="'" + inputEnterOrg + "',";//enterorg			
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
						if(Common.get(rs.getString("acctTagID")).equals("5")){ //非消
							differencekind="500";//differencekind
						}else{
							if(Common.get(rs.getString("acctTagID")).equals("0")){ //非消
								differencekind="500";//differencekind
							}
							else{
							differencekind="";//differencekind
							}
						}
					}
				}
				subinsert+="'"+differencekind+"',";
				//subinsert+="'"+Common.get(rs.getString("AgtNo"))+"',";//engineeringno
				
				subinsert+="1,";//caseserialno
				/*
				subinsert+="'',";//casename 停用
				subinsert+="'"+Common.get(rs.getDate("RECDate").toString()).replaceAll("-", "")+"',";//writedate  
				subinsert+="'"+Common.get(rs.getString("RECDivID"))+"',";//writeunit
				subinsert+="'"+Common.get(rs.getDate("PostDate").toString()).replaceAll("-", "").substring(0,4)+"',";//proofyear
				subinsert+="'"+proofdoc+"',";//proofdoc
				subinsert+="'"+proofno+"',";//proofno
				subinsert+="'',";//manageno 停用
				subinsert+="'"+Common.get(rs.getString("AcctDocID"))+"',";//summonsno
				subinsert+="'"+Common.get(rs.getString("AcctDate"))+"',";//summonsdate 
				subinsert+="'"+Common.get(rs.getDate("PostDate").toString()).replaceAll("-", "")+"',";//enterdate
				subinsert+="'',";//mergedivision 新系統欄位
				*/
				//System.out.println("CategoryID="+Common.get(rs.getString("CategoryID").toString()));
				//System.out.println("CategoryID="+Common.get(rs.getString("CategoryID").toString()).substring(0,3));
				String propertyno=Common.get(rs.getString("propertyno"));
				subinsert+="'"+propertyno+"',"; //propertyno
				/*
				if(Common.get(rs.getString("CategoryID").toString()).substring(0,3).equals("503") || Common.get(rs.getString("CategoryID").toString()).substring(0,3).equals("504") || Common.get(rs.getString("CategoryID").toString()).substring(0,3).equals("505")){
					subinsert+="'"+Common.get(rs.getString("CategoryID").toString())+"',"; //propertyno
					propertyno=Common.get(rs.getString("CategoryID").toString());
				}else{
					if(Common.get(rs.getString("CategoryID").toString()).length()==11){
						subinsert+="'"+Common.get(rs.getString("CategoryID").toString()).replaceAll("-", "0")+"',"; //propertyno
						propertyno=Common.get(rs.getString("CategoryID").toString()).replaceAll("-", "0");
					}else{
						if(Common.get(rs.getString("CategoryID").toString()).length()==10){
							subinsert+="'"+Common.get(rs.getString("CategoryID").toString()).replaceAll("-", "00")+"',"; //propertyno
							propertyno=Common.get(rs.getString("CategoryID").toString()).replaceAll("-", "00");
						}else{
							subinsert+="'"+Common.formatRearString(Common.get(rs.getString("CategoryID").toString()),11,'0')+"',"; //propertyno
							propertyno=Common.formatRearString(Common.get(rs.getString("CategoryID").toString()),11,'0');
						}
					}
				}
				*/
				/*

				
				SQLo3 = tcghconn.prepareStatement("select lotno from "+tablename+" where caseno='"+caseno+"' and enterorg='A27040000G' ");				
				rs3 = SQLo3.executeQuery();
				if(rs3.next())
				{
					lotno=Common.formatFrontString(Common.get(rs3.getString("lotno")), 7, '0');
				}else{
					SQLo3.close();
					SQLo3 = tcghconn.prepareStatement("select isnull(max(lotno)+1,'1') as lotno from "+tablename+" where enterorg='A27040000G' ");				
					rs3 = SQLo3.executeQuery();
					if(rs3.next()){
						lotno=Common.formatFrontString(Common.get(rs3.getString("lotno")), 7, '0');
					}
				}
				SQLo3.close();
				*/
				String lotno=Common.get(rs.getString("lotno"));
				subinsert+="'"+lotno+"',"; //lotno
				
				String serialno=Common.get(rs.getString("serialno"));
				subinsert+="'"+serialno+"',"; //serialno
				/*
				String serialno="";
				SQLo3 = tcghconn.prepareStatement("select isnull(max(serialno)+1,'1') as serialno from UNTMP_MOVABLEDETAIL where propertyno='"+propertyno+"' and enterorg='A27040000G' ");				
				rs3 = SQLo3.executeQuery();
				if(rs3.next())
				{
					serialno=Common.formatFrontString(Common.get(rs3.getString("serialno")), 7, '0');
				}
				SQLo3.close();				
				subinsert+="'"+serialno+"',"; //serialno
				*/
				subinsert+="'',";//otherpropertyunit
				subinsert+="'',";//othermaterial
				subinsert+="'"+Common.get(rs.getString("workingyears"))+"',";//otherlimityear
				subinsert+="'',";//propertyname1,
				subinsert+="'499',";//cause,
				subinsert+="'"+Common.get(rs.getString("reason"))+"',";//cause1,
				
				//====================
				/*
				String adjustdate="";
				if(Common.get(rs.getString("FluctuatedDate")).length()>0){
					adjustdate=Common.get(rs.getString("FluctuatedDate")).replaceAll("-", "").substring(0,8);
				}				
				subinsert+="'"+adjustdate+"',"; //adjustdate
			    */
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
				oldbookvalue=Common.get(rs.getString("AmtSTTL"));
//				subinsert+=""+oldbookvalue+",";//oldbookvalue,
				subinsert+=("".equals(oldbookvalue)?0:oldbookvalue) + ",";//oldbookvalue,
				
				//subinsert+="null,";//oldnetunit,
				
//				String oldnetvalue="";
//				oldnetvalue=Common.get(rs.getString("AmtSTTL"));
//				subinsert+=""+oldnetvalue+",";//oldnetvalue,
//				subinsert+=("".equals(oldnetvalue)?0:oldnetvalue) + ",";//oldnetvalue,
				
				String adjustbookamount="";
				adjustbookamount=Common.get(rs.getString("Qty"));
//				subinsert+=""+adjustbookamount+",";//adjustbookamount,
				subinsert+=("".equals(adjustbookamount)?0:adjustbookamount) + ",";//adjustbookamount,
				String adjustbookunit="";
				adjustbookunit=Common.get(rs.getString("Price"));
				subinsert+=("".equals(adjustbookunit)?0:adjustbookunit) + ",";//adjustbookunit,
				
				String adjustbookvalue="";
				adjustbookvalue=Common.get(rs.getString("AmtSTTL"));
//				subinsert+=""+adjustbookvalue+",";//adjustbookvalue,
				subinsert+=("".equals(adjustbookvalue)?0:adjustbookvalue) + ",";//adjustbookvalue,
				
//				subinsert+="null,";//adjustaccumdepr,
//				subinsert+="null,";//adjustnetunit,
				
//				String adjustnetvalue="";
//				adjustnetvalue=Common.get(rs.getString("AmtSTTL"));
//				subinsert+=""+adjustnetvalue+",";//adjustnetvalue,
//				subinsert+=("".equals(adjustnetvalue)?0:adjustnetvalue) + ",";//adjustnetvalue,
				
//				String newbookamount="";
//				newbookamount=Common.get(rs.getString("Qty"));
//				subinsert+=""+newbookamount+",";//newbookamount,
				subinsert+="'0',";//newbookamount,
				
				subinsert+="'0',";//newbookunit,
				
//				String newbookvalue="";
//				newbookvalue=Common.get(rs.getString("AmtSTTL"));
//				subinsert+=""+newbookvalue+",";//newbookvalue,
				subinsert+="'0',";//newbookvalue,
				
//				subinsert+="null,";//newnetunit,
//				String newnetvalue="";
//				newnetvalue=Common.get(rs.getString("AmtSTTL"));
//				subinsert+=""+newnetvalue+",";//newnetvalue,
//				subinsert+=("".equals(newnetvalue)?0:newnetvalue) + ",";//newnetvalue,
				//***
				
				subinsert+="'',";//articlename,
				subinsert+="'"+Common.get(rs.getString("brand"))+"',";;//nameplate,
				subinsert+="'"+Common.get(rs.getString("spec"))+"',";;//specification,
				subinsert+="'',";//sourcedate,
				
				subinsert+="'"+Common.get(rs.getString("buydate")).replaceAll("-", "").substring(0,8)+"',";//buydate,***
				
				
				subinsert+="'',";//licenseplate,
				subinsert+="'',";//movedate,
				subinsert+="'',";//keepunit,
				subinsert+="'',";//keeper,
				subinsert+="'',";//useunit,
				subinsert+="'',";//userno,
				subinsert+="'',";//usernote,
				subinsert+="'"+Common.get(rs.getString("LocationID"))+"',";//place1,
				subinsert+="'"+Common.get(rs.getString("LocationName"))+"',";//place,
				changeTaiwanYYMMDD(Common.get(rs.getDate("RECDate").toString()).replaceAll("-", ""),"1").substring(0,3);
				String ROCRECDate=changeTaiwanYYMMDD(Common.get(rs.getString("RECDate")).replaceAll("-", ""),"1").substring(0,5);
				String ROCbuydate=changeTaiwanYYMMDD(Common.get(rs.getString("buydate")).replaceAll("-", ""),"1").substring(0,5);
				int TempMonth = Datetime.BetweenTwoMonth(ROCbuydate,ROCRECDate);
				
				
				String useyear="";
				useyear = String.valueOf(TempMonth/12);
				

//				useyear=Integer.toString(Integer.valueOf(Common.get(rs.getString("reduceDate")).replaceAll("-", "").substring(0,4))-Integer.valueOf(Common.get(rs.getString("buydate")).replaceAll("-", "").substring(0,4)));	
				subinsert+=useyear+",";//useyear,***
				String usemonth="";
				usemonth = String.valueOf(TempMonth%12);
				
				subinsert+=usemonth+",";//usemonth,***
				
				subinsert+="'',";//cause2,
				subinsert+="'"+Common.get(rs.getString("LocationName"))+"',";//returnplace,
				subinsert+="'',";//reducedeal2,
				
//				subinsert+="'02',";//olddeprmethod,
				
//				subinsert+="'',";//oldbuildfeecb,
//				subinsert+="'',";//olddeprunitcb,
//				subinsert+="'',";//olddeprpark,
//				subinsert+="'',";//olddeprunit,
//				subinsert+="'',";//olddeprunit1,
//				subinsert+="'',";//olddepraccounts,
				
//				subinsert+="null,";//oldscrapvalue,
//				subinsert+="null,";//olddepramount,
//				subinsert+="null,";//oldaccumdepr,
//				subinsert+="null,";//oldapportionmonth,
//				subinsert+="null,";//oldmonthdepr,
//				subinsert+="null,";//oldmonthdepr1,
//				
//				subinsert+="'',";//oldapportionendym,
//				
//				subinsert+="'02',";//newdeprmethod,
//				
//				
//				subinsert+="'',";//newbuildfeecb,
//				subinsert+="'',";//newdeprunitcb,
//				subinsert+="'',";//newdeprpark,
//				subinsert+="'',";//newdeprunit,
//				subinsert+="'',";//newdeprunit1,
//				subinsert+="'',";//newdepraccounts,
//				
//				subinsert+="null,";//newscrapvalue,
//				subinsert+="null,";//newdepramount,
//				subinsert+="null,";//newaccumdepr,
//				subinsert+="null,";//newapportionmonth,
//				subinsert+="null,";//newmonthdepr,
//				subinsert+="null,";//newmonthdepr1,
//				
//				
//				subinsert+="'',";//newapportionendym,				
				
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
				subinsert+="'' ";//shiftorg,
				
				
				Allinsert=insertstr+subinsert+") ;";
				//System.out.println(Allinsert);
				if(!proofno.equals("")){
					try{
						if(insert_write==true){
							SQLo = tcghconn.prepareStatement(Allinsert);
							SQLo.execute();
							SQLo.close();
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
	
	/**
	 * @UNTMP_MOVEDETAIL
	 */
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
			SqlStr+=" c.keeperidfrom,a.rcvdate,a.WorkingYears,isnull(a.acctTagID,'') as acctTagID,isnull(a.AgtNo,'') as AgtNo,c.RECDate,isnull(c.RECDivID,'') as RECDivID,isnull(a.AcctDocID,'') as AcctDocID,a.AcctDate,a.PostDate,b.note as note_d,c.note as note_m,isnull(b.DocID,'') as DocNo, ";
			SqlStr+=" a.CategoryID,a.OffAccount,b.Qty,convert(int,a.AmtSTTL) as AmtSTTL,";
			SqlStr+=" b.AssetName,b.spec,b.brand,b.KeepDivIDFrom,b.KeepDivIDTo,b.KeeperIDTo,b.ASUserIDTo,b.ASUseDivIDFrom,b.ASUseDivIDTo,b.Qty,b.Price,b.Note,b.LocationNameFrom,b.LocationIDTo, ";
			SqlStr+=" a.RecDate,b.AssetID,d.propertyno,d.lotno,d.serialno, ";
			SqlStr+=" isnull(c.VoidDate,'') as VoidDate,a.BuyDate,isnull(c.TransDate,'') as TransDate,isnull(c.DocID,'') as  DocNotwo ";
			SqlStr+=" from ASNXBasic a,ASNXDistribute d,ASNXTransDetail b,ASNXTransMaster c";
			SqlStr+=" where a.AssetNo=d.AssetNo  ";
			SqlStr+=" and d.Assetid=b.Assetid";
			SqlStr+=" and b.docid=c.docid ";

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
            		"notes, editid, editdate, edittime " +
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
				SQLo2 = tcghconn.prepareStatement("select caseno from UNTNE_MOVEPROOF where proofyear='"+proofyear+"' and proofno='"+proofno+"' and enterorg='" + inputEnterOrg + "' ");				
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
					proofdoc="非耗品移 ";
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
				String differencekind="";
				if(Common.get(rs.getString("acctTagID")).equals("1")){ //公務
					differencekind="110";//differencekind
				}else{
					if(Common.get(rs.getString("acctTagID")).equals("2")){ //基金
						differencekind="120";//differencekind
					}else{
						if(Common.get(rs.getString("acctTagID")).equals("5")){ //非消
							differencekind="500";//differencekind
						}else{
							if(Common.get(rs.getString("acctTagID")).equals("0")){ //非消
								differencekind="500";//differencekind
							}
							else{
							differencekind="";//differencekind
							}
						}
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
				
				subinsert+="'"+Common.get(rs.getString("AssetName"))+"',";//propertyname1,
				subinsert+="'',";//otherpropertyunit
				subinsert+="'',";//othermaterial
				String otherlimityear=Common.get(rs.getString("WorkingYears"));
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
				subinsert+="'"+Common.get(rs.getString("keeperidfrom"))+"',";//oldkeeper,			
				subinsert+="'"+Common.get(rs.getString("ASUseDivIDFrom"))+"',";//olduseunit,
				subinsert+="'',";//olduserno,
				subinsert+="'',";//oldusernote,
				subinsert+="'',";//oldplace1,
				subinsert+="'"+Common.get(rs.getString("LocationNameFrom"))+"',";//oldplace,
				String newmovedate="";
				if(Common.get(rs.getString("TransDate")).length()>0){
					newmovedate=Common.get(rs.getString("TransDate")).replaceAll("-", "").substring(0,8);
				}
				subinsert+="'"+newmovedate+"',";//newmovedate,
				subinsert+="'"+Common.get(rs.getString("KeepDivIDTo"))+"',";//newkeepunit,
				subinsert+="'"+Common.get(rs.getString("KeeperIDTo"))+"',";//newkeeper,
				subinsert+="'"+Common.get(rs.getString("ASUseDivIDTo"))+"',";//newuseunit,
				subinsert+="'"+Common.get(rs.getString("ASUserIDTo"))+"',";//newuserno,
				subinsert+="'',";//newusernote,
				subinsert+="'"+Common.get(rs.getString("LocationIDTo"))+"',";//newplace1,
				subinsert+="'',";//newplace,
				
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
				subinsert+="'"+Datetime.getHHMMSS()+"' ";//edittime
				
				
				Allinsert=insertstr+subinsert+") ;";
				//System.out.println(Allinsert);
				if(!proofno.equals("")){
					try{
						if(insert_write==true){
							SQLo = tcghconn.prepareStatement(Allinsert);
							SQLo.execute();
							SQLo.close();
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
			//A27040000G 科技部新竹科學工業園區管理局 
			smblconn = getDBConnection("smblg");
			tcghconn = getDBConnection("TCGH");
			
			SqlStr+="select  ";			
			SqlStr+="isnull(a.acctTagID,'') as acctTagID,isnull(a.AgtNo,'') as AgtNo,c.RECDate,isnull(c.RECDivID,'') as RECDivID,isnull(a.AcctDocID,'') as AcctDocID,a.AcctDate,a.PostDate,b.note,isnull(b.DocID,'') as DocNo, ";
			SqlStr+="a.CategoryID,a.OffAccount,b.Qty,convert(int,a.AmtSTTL) as AmtSTTL,convert(int,a.ValDprTTL) as ValDprTTL,a.KeepDivID,a.KeeperID, ";
			SqlStr+="a.RecDate,a.AssetID,a.propertyno,a.lotno,a.serialno, ";
			SqlStr+="isnull(c.VoidDate,'') as VoidDate,a.BuyDate,c.RECDate,isnull(c.TransDate,'') as TransDate,isnull(c.DocID,'') as  DocNotwo ";
			SqlStr+=" from ASsetBasic a  ";
			SqlStr+="left join ASTransDetail b on a.AssetID=b.AssetID ";
			SqlStr+="left join ASTransMaster c on b.docid=c.docid   ";
			
			

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
            		"bookamount, bookunit, bookvalue, netunit, netvalue, nameplate, specification, sourcedate, oldmovedate," +
            		" oldkeepunit, oldkeeper, olduseunit, olduserno, oldusernote, oldplace1, oldplace, newmovedate, " +
            		"newkeepunit, newkeeper, newuseunit, newuserno, newusernote, newplace1, newplace, useyear, usemonth, " +
            		"olddeprmethod, oldbuildfeecb, olddeprunitcb, olddeprpark, olddeprunit, olddeprunit1, olddepraccounts, " +
            		"oldscrapvalue, olddepramount, oldaccumdepr, oldapportionmonth, oldmonthdepr, oldmonthdepr1, " +
            		"oldapportionendym, newdeprmethod, newbuildfeecb, newdeprunitcb, newdeprpark, newdeprunit, newdeprunit1," +
            		"newdepraccounts, newscrapvalue, newdepramount, newaccumdepr, newapportionmonth, newmonthdepr, " +
            		"newmonthdepr1, newapportionendym, signnola, areala, holdnumela, holddenola, holdareala, signnobu, " +
            		"careabu, sareabu, areabu, holdnumebu, holddenobu, holdareabu, measure, oldpropertyno, oldserialno, " +
            		"notes, editid, editdate, edittime " +
            		") ";			
			insertstr+=" values ( ";
			rs = SQLo.executeQuery();
			while(rs.next())
			{
				//增減值單單號ID
				proofno="";
				proofyear="";
				proofno=Common.formatFrontString(Common.get(rs.getString("DocNo")), 7, '0');
				proofyear=Common.get(rs.getDate("PostDate").toString()).replaceAll("-", "").substring(0,4);

				
				//caseno
				String caseno="";
				String proofdoc="";			    
				SQLo2 = tcghconn.prepareStatement("select caseno from UNTMP_MOVEPROOF where proofyear='"+proofyear+"' and proofno='"+proofno+"' and enterorg='" + inputEnterOrg + "' ");				
				rs2 = SQLo2.executeQuery();
				if(rs2.next()) //查詢單號是存在
				{
					caseno=Common.get(rs2.getString("caseno").toString());
				}else{         //不存在新建一個增加單單號
					
					SQLo3 = tcghconn.prepareStatement("select right(isnull(max(caseno)+1,'20000'),7) as caseno from UNTMP_MOVEPROOF  where proofyear='"+proofyear+"' and enterorg='" + inputEnterOrg + "' ");				
					rs3 = SQLo3.executeQuery();
					if(rs3.next())
					{
						caseno=changeTaiwanYYMMDD(Common.get(rs.getDate("PostDate").toString()).replaceAll("-", ""),"1").substring(0,3)+Common.formatFrontString(Common.get(rs3.getString("caseno")), 7, '0');
					}
					SQLo3.close();
					SQLo3 = tcghconn.prepareStatement("select isnull(max(proofno)+1,'1') as proofno from UNTMP_MOVEPROOF  where proofyear='"+proofyear+"' and enterorg='" + inputEnterOrg + "' ");				
					rs3 = SQLo3.executeQuery();
					if(rs3.next())
					{
						proofno=Common.formatFrontString(Common.get(rs3.getString("proofno")), 7, '0');
					}
					proofdoc="財移 ";
					SQLo3.close();
					
					INSERT_MOVEPROOF(tcghconn,"UNTMP_MOVEPROOF", caseno, proofdoc, proofno, Common.get(rs.getDate("RECDate").toString()), Common.get(rs.getString("RECDivID").toString()), Common.get(rs.getDate("PostDate").toString()), Common.get(rs.getDate("TransDate").toString()), Common.get(rs.getString("note").toString()) );
					
				}
				//增加單單號ID
				SQLo2.close();
				rs2.close();
				subinsert="";
				subinsert+="'" + inputEnterOrg + "',";//enterorg			
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
				
				subinsert+="1,";//caseserialno
				
				String propertyno=Common.get(rs.getString("propertyno"));
				subinsert+="'"+propertyno+"',"; //propertyno
				
				String lotno=Common.get(rs.getString("lotno"));
				subinsert+="'"+lotno+"',"; //lotno
				
				String serialno=Common.get(rs.getString("serialno"));
				subinsert+="'"+serialno+"',"; //serialno
				
				subinsert+="'',";//propertyname1,
				subinsert+="'',";//otherpropertyunit
				subinsert+="'',";//othermaterial
				subinsert+="null,";//otherlimityear

				subinsert+="'',";//buydate***
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
				
				
				subinsert+="'',";//nameplate,
				subinsert+="'',";//specification,
				subinsert+="'',";//sourcedate,
				
				subinsert+="'',";//oldmovedate,
				subinsert+="'',";//oldkeepunit,
				subinsert+="'',";//oldkeeper,
				subinsert+="'',";//olduseunit,
				subinsert+="'',";//olduserno,
				subinsert+="'',";//oldusernote,
				subinsert+="'',";//oldplace1,
				subinsert+="'',";//oldplace,
				
				subinsert+="'',";//newmovedate,
				subinsert+="'',";//newkeepunit,
				subinsert+="'',";//newkeeper,
				subinsert+="'',";//newuseunit,
				subinsert+="'',";//newuserno,
				subinsert+="'',";//newusernote,
				subinsert+="'',";//newplace1,
				subinsert+="'',";//newplace,
				
				subinsert+="null,";//useyear
				subinsert+="null,";//usemonth
				
				
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
				
				String oldpropertyno="";
				oldpropertyno=Common.get(rs.getString("CategoryID"));
				subinsert+="'"+oldpropertyno+"',"; //oldpropertyno
				
				String oldserialno="";
				oldserialno=Common.get(rs.getString("AssetID"));
				subinsert+="'"+oldserialno+"',"; //oldserialno
				
				subinsert+="'"+Common.get(rs.getString("note"))+"',";//notes
				subinsert+="'GM',";//editid
				subinsert+="'"+Datetime.getYYYYMMDD()+"',";//editdate
				subinsert+="'"+Datetime.getHHMMSS()+"' ";//edittime
				
				
				Allinsert=insertstr+subinsert+") ;";
				//System.out.println(Allinsert);
				if(!proofno.equals("")){
					try{
						if(insert_write==true){
							SQLo = tcghconn.prepareStatement(Allinsert);
							SQLo.execute();
							SQLo.close();
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
			//A27040000G 科技部新竹科學工業園區管理局 
			smblconn = getDBConnection("smblg");
			tcghconn = getDBConnection("TCGH");
			
			SqlStr+="select ";
			SqlStr+="isnull(a.acctTagID,'') as acctTagID,isnull(a.AgtNo,'') as AgtNo,isnull(a.AcctDocID,'') as AcctDocID,a.AcctDate,a.PostDate, "; 
			SqlStr+="a.CategoryID,a.OffAccount,isnull(a.OffAccountDate,'') as OffAccountDate,convert(int,a.AmtSTTL) as AmtSTTL,convert(int,a.ValDprTTL) as ValDprTTL,a.KeepDivID,a.KeeperID, ";
			SqlStr+="a.RecDate,a.AssetID,a.propertyno,a.lotno,a.serialno,b.ValOrg,convert(int,b.ValDprTTL) AS ValDprTTL_B,convert(int,b.ValDpr) as ValDpr_B , ";
			SqlStr+="b.YearMonth,b.AssetID,convert(int,b.ValCur) as ValCur,   ";
			SqlStr+="b.Qty,b.Note,b.ValKeep,b.AssetType,b.WorkingYears,b.RecID  ";
			SqlStr+="from ASsetBasic a  ";
			SqlStr+="left join ASDprCountDetailAcctChild b on a.AssetID=b.AssetID ";
			//SqlStr+=",ASFluctuateMaster c    ";
//			SqlStr+="where  a.AssetID=b.AssetID  ";
			
//			SqlStr+="and b.RecID>='524426' "; //中斷繼續用
			
			
			SqlStr+=" and b.yearmonth = '201402'";
			
			//SqlStr+="and serialno='0000001' ";
			//SqlStr+="where b.DocID is not null "; 
			//SqlStr+="group by a.acctTagID,a.AgtNo,c.RECDate,c.RECDivID,a.AcctDocID,a.AcctDate,a.PostDate,b.note,b.DocID ";
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
				subinsert+="null,";//originalbv,
				
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
	
						String SqlStr3="SELECT TOP 1 * FROM UNTDP_MONTHDEPR WHERE propertyno='"+propertyno+"' and serialno='"+serialno+"' and serialno1="+serialno1+" ";
						System.out.println(SqlStr3);
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
			//A27040000G 科技部新竹科學工業園區管理局 
			smblconn = getDBConnection("smblg");
			tcghconn = getDBConnection("TCGH");
			
			SqlStr+="select ";
			SqlStr+="isnull(a.acctTagID,'') as acctTagID,isnull(a.AssetID,'') as AssetID,isnull(a.AcctDocID,'') as AcctDocID,a.AcctDate,a.PostDate, "; 
			SqlStr+="a.CategoryID,a.OffAccount,isnull(a.OffAccountDate,'') as OffAccountDate,convert(int,a.AmtSTTL) as AmtSTTL,convert(int,a.ValDprTTL) as ValDprTTL,a.KeepDivID,a.KeeperID, ";
			SqlStr+="a.RecDate,b.ValOrg,convert(int,b.ValDprTTL) AS ValDprTTL_B,convert(int,b.ValDpr) as ValDpr_B , ";
			SqlStr+="b.YearMonth,convert(int,b.ValCur) as ValCur,   ";
			SqlStr+="b.Qty,b.Note,b.ValKeep,b.AssetType,b.WorkingYears,b.RecID  ";
			SqlStr+="from ASIprvBasic a  ";
			SqlStr+="left join ASDprCountDetailAcctChild b on a.AssetID=b.AssetID ";
			//SqlStr+=",ASFluctuateMaster c    ";
//			SqlStr+="where  a.AssetID=b.AssetID  ";
			//SqlStr+="and b.RecID>='105349' ";
			//SqlStr+="and serialno='0000001' ";
			//SqlStr+="where b.DocID is not null "; 
			//SqlStr+="group by a.acctTagID,a.AgtNo,c.RECDate,c.RECDivID,a.AcctDocID,a.AcctDate,a.PostDate,b.note,b.DocID ";
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
				subinsert+="null,";//originalbv,
				
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
						String SqlStr3="SELECT TOP 1 * FROM UNTDP_MONTHDEPR WHERE propertyno='"+propertyno+"' and serialno='"+serialno+"' and serialno1="+serialno1+" ";
						System.out.println(SqlStr3);
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
			//A27040000G 科技部新竹科學工業園區管理局 
			smblconn = getDBConnection("smblg");
			tcghconn = getDBConnection("TCGH");
			
			SqlStr+="select ";
			SqlStr+="isnull(a.acctTagID,'') as acctTagID,isnull(a.AssetID,'') as AssetID,isnull(a.AcctDocID,'') as AcctDocID,a.AcctDate,a.PostDate, "; 
			SqlStr+="a.CategoryID,a.OffAccount,isnull(a.OffAccountDate,'') as OffAccountDate,convert(int,a.AmtSTTL) as AmtSTTL,convert(int,a.ValDprTTL) as ValDprTTL,a.KeepDivID,a.KeeperID, ";
			SqlStr+="a.RecDate,b.ValOrg,convert(int,b.ValDprTTL) AS ValDprTTL_B,convert(int,b.ValDpr) as ValDpr_B , ";
			SqlStr+="b.YearMonth,convert(int,b.ValCur) as ValCur,   ";
			SqlStr+="b.Qty,b.Note,b.ValKeep,b.AssetType,b.WorkingYears,b.RecID  ";
			SqlStr+="from ASbldgBasic a  ";
			SqlStr+="left join ASDprCountDetailAcctChild b on a.AssetID=b.AssetID ";
			//SqlStr+=",ASFluctuateMaster c    ";
//			SqlStr+="where  a.AssetID=b.AssetID  ";
			//SqlStr+="and b.RecID>='72662' ";
			//SqlStr+="and serialno='0000001' ";
			//SqlStr+="where b.DocID is not null "; 
			//SqlStr+="group by a.acctTagID,a.AgtNo,c.RECDate,c.RECDivID,a.AcctDocID,a.AcctDate,a.PostDate,b.note,b.DocID ";
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
				subinsert+="null,";//originalbv,
				
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
						String SqlStr3="SELECT TOP 1 * FROM UNTDP_MONTHDEPR WHERE propertyno='"+propertyno+"' and serialno='"+serialno+"' and serialno1="+serialno1+" ";
						System.out.println(SqlStr3);
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
	 * @ASIprvBasic財編檢查
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
			//A27040000G 科技部新竹科學工業園區管理局 
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
			//SqlStr+="group by a.acctTagID,a.AgtNo,c.RECDate,c.RECDivID,a.AcctDocID,a.AcctDate,a.PostDate,b.note,b.DocID ";
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
	 * @ASbldgBasic財編檢查
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
			//A27040000G 科技部新竹科學工業園區管理局 
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
			//SqlStr+="group by a.acctTagID,a.AgtNo,c.RECDate,c.RECDivID,a.AcctDocID,a.AcctDate,a.PostDate,b.note,b.DocID ";
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
	 * @寫入UNT**_ADDPROOF用
	 */
	public static void INSERT_ADDPROOF(Connection conn,String tablename,String caseno,String proofdoc,String proofno,String acctTagID,String AgtNo,String RECDate,String RECDivID,String PostDate,String AcctDocID,String AcctDate,String note){
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
		if(acctTagID.equals("1")){ //公務
			subinsert+="'110',";//differencekind
		}else{
			if(acctTagID.equals("2")){ //基金
				subinsert+="'120',";//differencekind
			}else{
				if(acctTagID.equals("6")){ //代管資產
					subinsert+="'111',";//differencekind
				}else if("A10120000U".equals(inputEnterOrg) || "A102V0000U".equals(inputEnterOrg)){
					subinsert+="110,";//differencekind
				}else{
					subinsert+="'',";//differencekind
				}
			}
		}
		
		subinsert+="'"+AgtNo+"',";//engineeringno
		subinsert+="'',";//casename 停用
		subinsert+="'"+RECDate.replaceAll("-", "")+"',";//writedate  
		subinsert+="'"+RECDivID+"',";//writeunit
		subinsert+="'"+PostDate.replaceAll("-", "").substring(0,4)+"',";//proofyear
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
		if(!proofno.equals("")){
			try{
				if(insert_write==true){
					SQLo = conn.prepareStatement(Allinsert);
					SQLo.execute();
					SQLo.close();
				}
			}catch(Exception e){
				System.out.println(e);
				System.out.println(Allinsert);
			}
		}
	}
	
	
	
	/**
	 * @寫入UNT**_ADJUSTPROOF用 
	 */	
	public static void INSERT_ADJUSTPROOF(Connection conn,String tablename,String caseno,String proofdoc,String proofno,String acctTagID,String AgtNo,String RECDate,String RECDivID,String PostDate,String AcctDocID,String AcctDate,String note,String fluctuateddate,String cfmdate,String docno){
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
		if(acctTagID.equals("1")){ //公務
			subinsert+="'110',";//differencekind
		}else{
			if(acctTagID.equals("2")){ //基金
				subinsert+="'120',";//differencekind
			}else{
				if(acctTagID.equals("6")){ //代管資產
					subinsert+="'111',";//differencekind
				}else if("A10120000U".equals(inputEnterOrg) || "A102V0000U".equals(inputEnterOrg)){
					subinsert+="110,";//differencekind
				}else{
					subinsert+="'',";//differencekind
				}
			}
		}
		
		subinsert+="'"+AgtNo+"',";//engineeringno
		subinsert+="'',";//casename 停用
		subinsert+="'"+RECDate.replaceAll("-", "")+"',";//writedate  
		subinsert+="'"+RECDivID+"',";//writeunit
		subinsert+="'"+PostDate.replaceAll("-", "").substring(0,4)+"',";//proofyear
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
		//System.out.println(Allinsert);
		if(!proofno.equals("")){
			try{
				if(insert_write==true){
					SQLo = conn.prepareStatement(Allinsert);
					SQLo.execute();
					SQLo.close();
				}
			}catch(Exception e){
				System.out.println(e);
				System.out.println(Allinsert);
			}
		}
	}
	
	
	/**
	 * @寫入UNT**_REDUCEPROOF用 
	 */	
	public static void INSERT_REDUCEPROOF(Connection conn,String tablename,String caseno,String proofdoc,String proofno,String acctTagID,String AgtNo,String RECDate,String RECDivID,String PostDate,String AcctDocID,String AcctDate,String note,String cfmdate,String docno,String reduceDate){
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
		if(acctTagID.equals("1")){ //公務
			subinsert+="'110',";//differencekind
		}else{
			if(acctTagID.equals("2")){ //基金
				subinsert+="'120',";//differencekind
			}else{
				if(acctTagID.equals("6")){ //代管資產
					subinsert+="'111',";//differencekind
				}else if("A10120000U".equals(inputEnterOrg) || "A102V0000U".equals(inputEnterOrg)){
					subinsert+="110,";//differencekind
				}else{
					subinsert+="'',";//differencekind
				}
			}
		}
		
		
		//subinsert+="'"+AgtNo+"',";//engineeringno
		subinsert+="'',";//casename 停用
		subinsert+="'"+RECDate.replaceAll("-", "")+"',";//writedate  
		subinsert+="'"+RECDivID+"',";//writeunit
		subinsert+="'"+PostDate.replaceAll("-", "").substring(0,4)+"',";//proofyear
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
				}
			}catch(Exception e){
				System.out.println(e);
				System.out.println(Allinsert);
			}
		}
	}
	
	/**
	 * @寫入UNT**_REDUCEPROOF用 
	 */	
	public static void INSERT_UNTNE_REDUCEPROOF(Connection conn,String tablename,String caseno,String proofdoc,String proofno,String acctTagID,String AgtNo,String RECDate,String RECDivID,String PostDate,String AcctDocID,String AcctDate,String note,String cfmdate,String docno,String reduceDate){
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
		if(acctTagID.equals("1")){ //公務
			subinsert+="'110',";//differencekind
		}else{
			if(acctTagID.equals("2")){ //基金
				subinsert+="'120',";//differencekind
			}else{
				if(acctTagID.equals("5")){ //非消
					subinsert+="'500',";//differencekind
				}else{
					if(acctTagID.equals("0")){ //非消
						subinsert+="'500',";//differencekind
					}
					else{subinsert+="'',";//differencekind
					}
				}
			}
		}	
		
		//subinsert+="'"+AgtNo+"',";//engineeringno
		subinsert+="'',";//casename 停用
		subinsert+="'"+RECDate.replaceAll("-", "")+"',";//writedate  
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
		System.out.println(Allinsert);
		if(!proofno.equals("")){
			try{
				if(insert_write==true){
					SQLo = conn.prepareStatement(Allinsert);
					SQLo.execute();
					SQLo.close();
				}
			}catch(Exception e){
				System.out.println(e);
				System.out.println(Allinsert);
			}
		}
	}
	
	/**
	 * @寫入UNT**_MOVEPROOF用 
	 */	
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
		subinsert+="'"+RECDate.replaceAll("-", "")+"',";//writedate  
		subinsert+="'"+RECDivID+"',";//writeunit
		subinsert+="'"+changeTaiwanYYMMDD(Common.get(RECDate).replaceAll("-", ""),"1").substring(0,3)+"',";//proofyear
		subinsert+="'"+proofdoc+"',";//proofdoc
		subinsert+="'"+proofno+"',";//proofno

		if(movedate.length()>=8){
			subinsert+="'"+movedate.replaceAll("-", "").substring(0,8)+"',";//movedate
		}else{
			subinsert+="'',";//movedate
		}

		subinsert+="'Y',";//verify
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
				}
			}catch(Exception e){
				System.out.println(e);
				System.out.println(Allinsert);
			}
		}
	}
	
	/**
	 * @寫入UNT**_MOVEPROOF用 
	 */	
	public static void INSERT_MOVEPROOF(Connection conn,String tablename,String caseno,String proofdoc,String proofno ,String RECDate,String RECDivID , String PostDate,String movedate,String note){
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
		subinsert+="'"+RECDate.replaceAll("-", "")+"',";//writedate  
		subinsert+="'"+RECDivID+"',";//writeunit
		subinsert+="'"+PostDate.replaceAll("-", "").substring(0,4)+"',";//proofyear
		subinsert+="'"+proofdoc+"',";//proofdoc
		subinsert+="'"+proofno+"',";//proofno

		if(movedate.length()>=8){
			subinsert+="'"+movedate.replaceAll("-", "").substring(0,8)+"',";//movedate
		}else{
			subinsert+="'',";//movedate
		}

		subinsert+="'Y',";//verify
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
				}
			}catch(Exception e){
				System.out.println(e);
				System.out.println(Allinsert);
			}
		}
	}
	
	/**
	 * @資料庫連線
	 */
	public static Connection getDBConnection(String DB) throws Exception
    {
		String url = null;
	    String div_str = null;
	    String user = null;
	    String pw = null;
		try
        {
			try
			{
			    if(DB.equals("TCGH")) //測試區
			    {	
			    	 url = "jdbc:microsoft:sqlserver://172.16.30.154:1433;DatabaseName=TCGH_TRANSFER";
				     div_str = "com.microsoft.jdbc.sqlserver.SQLServerDriver";
				     user = "TCGH";
				     pw = "1qaz@WSX";
			    	
//			    	url = "jdbc:jtds:sqlserver://192.168.137.5:1433/TCGH_transfer";
//		    		div_str = "com.microsoft.jdbc.sqlserver.SQLServerDriver";
//					user = "TCGH";
//					pw = "TCGH";
			    	
//					url = "jdbc:jtds:sqlserver://172.16.30.82:1433/TCGH_TRANSFER";
//		    		div_str = "com.microsoft.jdbc.sqlserver.SQLServerDriver";
//					user = "sa";
//					pw = "1qaz2wsx";
				     
			    }
//			    if (DB.equals("ORA"))//科技部
//			    {			    
//			        url = "jdbc:oracle:thin:@172.16.32.143:1521:orcl";
//	    			div_str = "oracle.jdbc.driver.OracleDriver";
//	    			user = "ntpsm";
//	    			pw = "ntpsm";
//			    }
//			     //192.168.2.139
//			    if(DB.equals("smblrpt")) //smblrpt 竹科管 excel
//			    {	
//			    	 url = "jdbc:microsoft:sqlserver://192.168.2.139:1433;DatabaseName=smblrpt";
//				     div_str = "com.microsoft.jdbc.sqlserver.SQLServerDriver";
//				     user = "TCGH";
//				     pw = "TCGH";
//				     
//			    }
			    if(DB.equals("smblg")) //smbl 竹科管 word
			    {	
//			    	 url = "jdbc:microsoft:sqlserver://192.168.2.139:1433;DatabaseName=smblg";
//				     div_str = "com.microsoft.jdbc.sqlserver.SQLServerDriver";
//				     user = "TCGH";
//				     pw = "TCGH";

			    	
			      if("A27040000G".equals(inputEnterOrg)){
//					    url = "jdbc:jtds:sqlserver://192.168.137.99:1433/smblg";
//				    	div_str = "net.sourceforge.jtds.jdbc.Driver";
//				    	user = "TCGH";
//					    pw = "TCGH";
//					    
					    url = "jdbc:jtds:sqlserver://172.16.30.82:1433/smblg";
			    		div_str = "com.microsoft.jdbc.sqlserver.SQLServerDriver";
						user = "sa";
						pw = "1qaz2wsx";
					    
			      }else if("A10120000U".equals(inputEnterOrg)){
//					    url = "jdbc:jtds:sqlserver://192.168.137.99:1433/smblg_002";
//				    	div_str = "net.sourceforge.jtds.jdbc.Driver";
//				    	user = "TCGH";
//					    pw = "TCGH";
//					    
					    url = "jdbc:jtds:sqlserver://172.16.30.82:1433/smblg_002";
			    		div_str = "com.microsoft.jdbc.sqlserver.SQLServerDriver";
						user = "sa";
						pw = "1qaz2wsx";
					     
			      }else if("A102V0000U".equals(inputEnterOrg)){
//					    url = "jdbc:jtds:sqlserver://192.168.137.99:1433/smblg_003";
//				    	div_str = "net.sourceforge.jtds.jdbc.Driver";
//				    	user = "TCGH";
//					    pw = "TCGH";
					    
					    url = "jdbc:jtds:sqlserver://172.16.30.82:1433/smblg_003";
			    		div_str = "com.microsoft.jdbc.sqlserver.SQLServerDriver";
						user = "sa";
						pw = "1qaz2wsx";
					     
			    	  
			      }
			    	
			    }			    
			    
			}
			catch (Exception e)
			{
				System.out.println("While getting properties:\n" + e);
			}
	
            Class.forName(div_str);
            return DriverManager.getConnection(url, user, pw);
        }
        catch (Exception e)
        {
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
		        	if(year<0){ sb.append("000"); }
		        	else if (year<=99){ sb.append("0"); }
		    		sb.append(Integer.toString(year));
		    		if (mm<=9){ sb.append("0"); }
		    		sb.append(Integer.toString(mm));
		    		if (dd<=9){ sb.append("0"); }
		    		sb.append(Integer.toString(dd));    
		    		rStr = sb.toString();
		    	}
		    	
		        
		    	return rStr;
		    }
		
}
