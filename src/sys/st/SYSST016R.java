/*
*<br>程式目的：資訊設備現況調查歷年資料查詢
*<br>程式代號：sysst016r
*<br>程式日期：0960704
*<br>程式作者：shan
*<br>--------------------------------------------------------
*<br>修改作者　　修改日期　　　修改目的
*<br>--------------------------------------------------------
*/

package sys.st;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import util.*;

import java.io.File;
import java.io.FileInputStream;
import java.sql.ResultSet;

import util.Common;
import util.Database;
import util.SuperBean;
import java.sql.ResultSet;

import jxl.*;
import jxl.format.Alignment;
import jxl.format.Border;
import jxl.format.BorderLineStyle;
import jxl.format.UnderlineStyle;
import jxl.format.VerticalAlignment;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

import java.sql.ResultSet;

public class SYSST016R extends QueryBean{


String editID;
String propertyNo;
String q_enterOrgName;
String propertyName;
String limitYear;
String propertyUnit;
String subTotal;
String amount1;
String amount2;
String amount3;
String amount4;
String amount5;
String amount6;

String q_editID;
String q_year;


public String getEditID(){ return checkGet(editID); }
public void setEditID(String s){ editID=checkSet(s); }
public String getQ_enterOrgName(){ return checkGet(q_enterOrgName); }
public void setQ_enterOrgName(String s){ q_enterOrgName=checkSet(s); }
public String getPropertyNo(){ return checkGet(propertyNo); }
public void setPropertyNo(String s){ propertyNo=checkSet(s); }
public String getPropertyName(){ return checkGet(propertyName); }
public void setPropertyName(String s){ propertyName=checkSet(s); }
public String getLimitYear(){ return checkGet(limitYear); }
public void setLimitYear(String s){ limitYear=checkSet(s); }
public String getPropertyUnit(){ return checkGet(propertyUnit); }
public void setPropertyUnit(String s){ propertyUnit=checkSet(s); }
public String getSubTotal(){ return checkGet(subTotal); }
public void setSubTotal(String s){ subTotal=checkSet(s); }
public String getAmount1(){ return checkGet(amount1); }
public void setAmount1(String s){ amount1=checkSet(s); }
public String getAmount2(){ return checkGet(amount2); }
public void setAmount2(String s){ amount2=checkSet(s); }
public String getAmount3(){ return checkGet(amount3); }
public void setAmount3(String s){ amount3=checkSet(s); }
public String getAmount4(){ return checkGet(amount4); }
public void setAmount4(String s){ amount4=checkSet(s); }
public String getAmount5(){ return checkGet(amount5); }
public void setAmount5(String s){ amount5=checkSet(s); }
public String getAmount6(){ return checkGet(amount6); }
public void setAmount6(String s){ amount6=checkSet(s); }

public String getQ_editID(){ return checkGet(q_editID); }
public void setQ_editID(String s){ q_editID=checkSet(s); }
public String getQ_year(){ return checkGet(q_year); }
public void setQ_year(String s){ q_year=checkSet(s); }

String fileName;
public String getFileName(){ return checkGet(fileName); }
public void setFileName(String s){ fileName=checkSet(s); }

String excelFile;
String filestoreLocation;
public String getFilestoreLocation(){ return checkGet(filestoreLocation); }
public void setFilestoreLocation(String s){ filestoreLocation=checkSet(s); }	
public String getExcelFileName() { return checkGet(excelFile); }
public void setExcelFileName(String s) { excelFile=checkSet(s); }

String Q_enterOrg;
public String getQ_enterOrg(){ return checkGet(Q_enterOrg); }
public void setQ_enterOrg(String s){ Q_enterOrg=checkSet(s); }


public String getSql(){
	String sql="";
	int yyy=Integer.parseInt(getQ_year());
	sql = " ( " + "\n"
		+ " select a.propertyno,a.computertype ,c.propertyname ,c.limityear ,c.propertyunit ,count(b.serialno) as subTotal " + "\n"
		+ "        ,( select count(d.serialno) " + "\n"
		+ "           from UNTMP_Movable c ,UNTMP_MovableDetail d " + "\n"
		+ "           where 1=1 " + "\n"
		+ "           and c.enterorg=d.enterorg          and c.ownership=d.ownership " + "\n"
		+ "           and c.propertyno=d.propertyno      and c.lotno=d.lotno " + "\n"
		+ "           and c.enterorg=a.enterorg          and c.ownership=a.ownership " + "\n"
		+ "           and c.propertyno=a.propertyno      and nvl(c.computertype,99)=nvl(a.computertype,99) " + "\n"
		+ "           and c.buydate<= " + Common.sqlChar(Common.formatFrontZero(String.valueOf(yyy-1), 3)+"1231") + "\n"//'0961231' " + "\n"
		+ "           and c.buydate>= " + Common.sqlChar(Common.formatFrontZero(String.valueOf(yyy-1), 3)+"0101") + "\n"//'0960101' " + "\n"
		+ "           and c.ownership='1' " + "\n"
		+ "           and d.datastate='1' " + "\n"
		+ "           and c.verify='Y' " + "\n"
		+ "         ) as amount1 --1 " + "\n"
		+ "        ,( select count(d.serialno) " + "\n"
		+ "           from UNTMP_Movable c ,UNTMP_MovableDetail d " + "\n"
		+ "           where 1=1 " + "\n"
		+ "           and c.enterorg=d.enterorg           and c.ownership=d.ownership " + "\n"
		+ "           and c.propertyno=d.propertyno       and c.lotno=d.lotno " + "\n"
		+ "           and c.enterorg=a.enterorg           and c.ownership=a.ownership " + "\n"
		+ "           and c.propertyno=a.propertyno       and nvl(c.computertype,99)=nvl(a.computertype,99) " + "\n"
		+ "           and c.buydate<= " + Common.sqlChar(Common.formatFrontZero(String.valueOf(yyy-2), 3)+"1231") + "\n"//'0951231' " + "\n"
		+ "           and c.buydate>= " + Common.sqlChar(Common.formatFrontZero(String.valueOf(yyy-2), 3)+"0101") + "\n"//'0950101' " + "\n"
		+ "           and c.ownership='1' " + "\n"
		+ "           and d.datastate='1' " + "\n"
		+ "           and c.verify='Y' " + "\n"
		+ "         ) as amount2 --2 " + "\n"
		+ "        ,( select count(d.serialno) " + "\n"
		+ "           from UNTMP_Movable c ,UNTMP_MovableDetail d " + "\n"
		+ "           where 1=1 " + "\n"
		+ "           and c.enterorg=d.enterorg           and c.ownership=d.ownership " + "\n"
		+ "           and c.propertyno=d.propertyno       and c.lotno=d.lotno " + "\n"
		+ "           and c.enterorg=a.enterorg           and c.ownership=a.ownership " + "\n"
		+ "           and c.propertyno=a.propertyno       and nvl(c.computertype,99)=nvl(a.computertype,99) " + "\n"
		+ "           and c.buydate<= " + Common.sqlChar(Common.formatFrontZero(String.valueOf(yyy-3), 3)+"1231") + "\n"//'0941231' " + "\n"
		+ "           and c.buydate>= " + Common.sqlChar(Common.formatFrontZero(String.valueOf(yyy-3), 3)+"0101") + "\n"//'0940101' " + "\n"
		+ "           and c.ownership='1' " + "\n"
		+ "           and d.datastate='1' " + "\n"
		+ "           and c.verify='Y' " + "\n"
		+ "         ) as amount3 --3 " + "\n"
		+ "        ,( select count(d.serialno) " + "\n"
		+ "           from UNTMP_Movable c ,UNTMP_MovableDetail d " + "\n"
		+ "           where 1=1 " + "\n"
		+ "           and c.enterorg=d.enterorg           and c.ownership=d.ownership " + "\n"
		+ "           and c.propertyno=d.propertyno       and c.lotno=d.lotno " + "\n"
		+ "           and c.enterorg=a.enterorg           and c.ownership=a.ownership " + "\n"
		+ "           and c.propertyno=a.propertyno       and nvl(c.computertype,99)=nvl(a.computertype,99) " + "\n"
		+ "           and c.buydate<= " + Common.sqlChar(Common.formatFrontZero(String.valueOf(yyy-4), 3)+"1231") + "\n"//'0931231' " + "\n"
		+ "           and c.buydate>= " + Common.sqlChar(Common.formatFrontZero(String.valueOf(yyy-4), 3)+"0101") + "\n"//'0930101' " + "\n"
		+ "           and c.ownership='1' " + "\n"
		+ "           and d.datastate='1' " + "\n"
		+ "           and c.verify='Y' " + "\n"
		+ "         ) as amount4 --4 " + "\n"
		+ "        ,( select count(d.serialno) " + "\n"
		+ "           from UNTMP_Movable c ,UNTMP_MovableDetail d " + "\n"
		+ "           where 1=1 " + "\n"
		+ "           and c.enterorg=d.enterorg           and c.ownership=d.ownership " + "\n"
		+ "           and c.propertyno=d.propertyno       and c.lotno=d.lotno " + "\n"
		+ "           and c.enterorg=a.enterorg           and c.ownership=a.ownership " + "\n"
		+ "           and c.propertyno=a.propertyno       and nvl(c.computertype,99)=nvl(a.computertype,99) " + "\n"
		+ "           and c.buydate<= " + Common.sqlChar(Common.formatFrontZero(String.valueOf(yyy-5), 3)+"1231") + "\n"//'0921231' " + "\n"
		+ "           and c.buydate>= " + Common.sqlChar(Common.formatFrontZero(String.valueOf(yyy-5), 3)+"0101") + "\n"//'0920101' " + "\n"
		+ "           and c.ownership='1' " + "\n"
		+ "           and d.datastate='1' " + "\n"
		+ "           and c.verify='Y' " + "\n"
		+ "         ) as amount5 --5 " + "\n"
		+ "        ,( select count(d.serialno) " + "\n"
		+ "           from UNTMP_Movable c ,UNTMP_MovableDetail d " + "\n"
		+ "           where 1=1 " + "\n"
		+ "           and c.enterorg=d.enterorg           and c.ownership=d.ownership " + "\n"
		+ "           and c.propertyno=d.propertyno       and c.lotno=d.lotno " + "\n"
		+ "           and c.enterorg=a.enterorg           and c.ownership=a.ownership " + "\n"
		+ "           and c.propertyno=a.propertyno       and nvl(c.computertype,99)=nvl(a.computertype,99) " + "\n"
		+ "           and c.buydate<= " + Common.sqlChar(Common.formatFrontZero(String.valueOf(yyy-6), 3)+"1231") + "\n"//'0911231' " + "\n"
		+ "           and c.ownership='1' " + "\n"
		+ "           and d.datastate='1' " + "\n"
		+ "           and c.verify='Y' " + "\n"
		+ "         ) as amount6 --6 " + "\n"
		+ " from UNTMP_Movable a ,UNTMP_MovableDetail b ,SYSPK_PropertyCode c " + "\n"
		+ " where 1=1 " + "\n"
		+ " and a.enterorg=b.enterorg           and a.ownership=b.ownership " + "\n"
		+ " and a.propertyno=b.propertyno       and a.lotno=b.lotno " + "\n"
		+ " and (a.enterorg=c.enterorg or c.enterOrg='000000000A') " + "\n"
		+ " and a.propertyno=c.propertyno(+) " + "\n"
		+ " and a.propertyNo like '314%' " + "\n"
		+ " and a.buydate<= " + Common.sqlChar(Common.formatFrontZero(String.valueOf(yyy-1), 3)+"1231") + "\n"//'0961231' " + "\n"
		+ " and a.enterorg="+ Common.sqlChar(getQ_enterOrg()) + "\n"
		+ " and a.ownership='1' " + "\n"
		+ " and b.datastate='1' " + "\n"
		+ " and a.verify='Y' " + "\n"
		+ " group by a.enterorg ,a.ownership ,a.propertyno ,a.computertype ,c.propertyname ,c.limityear ,c.propertyunit " + "\n"
		+ " )union( " + "\n"
		+ " select '9999999' as propertyno ,'' as computertype ,d.softname ,'' as limitYear ,'套' as propertyUnit ,sum(d.softAmount) as subtotal " + "\n"
		+ "        ,(nvl((select sum(e.softAmount) from UNTSO_PCSoftware e " + "\n"
		+ "               where 1=1 " + "\n"
		+ "               and e.enterorg=d.enterorg " + "\n"
		+ "               and e.softname=d.softname " + "\n"
		+ "               and e.verify='Y' " + "\n"
		+ "               and e.startDate>= " + Common.sqlChar(Common.formatFrontZero(String.valueOf(yyy-1), 3)+"0101") + "\n"//'0960101' " + "\n"
		+ "               and e.startDate<= " + Common.sqlChar(Common.formatFrontZero(String.valueOf(yyy-1), 3)+"1231") + "\n"//'0961231' " + "\n"
		+ "               ),0 ) " + "\n"
		+ "         )as amount1 --1 " + "\n"
		+ "        ,(nvl((select sum(e.softAmount) from UNTSO_PCSoftware e " + "\n"
		+ "               where 1=1 " + "\n"
		+ "               and e.enterorg=d.enterorg " + "\n" 
		+ "               and e.softname=d.softname " + "\n" 
		+ "               and e.verify='Y' " + "\n"
		+ "               and e.startDate>= " + Common.sqlChar(Common.formatFrontZero(String.valueOf(yyy-2), 3)+"0101") + "\n"//'0950101' " + "\n"
		+ "               and e.startDate<= " + Common.sqlChar(Common.formatFrontZero(String.valueOf(yyy-2), 3)+"1231") + "\n"//'0951231' " + "\n"
		+ "               ),0 ) " + "\n"
		+ "         )as amount2 --2 " + "\n"
		+ "        ,(nvl((select sum(e.softAmount) from UNTSO_PCSoftware e " + "\n"
		+ "               where 1=1 " + "\n" 
		+ "               and e.enterorg=d.enterorg " + "\n" 
		+ "               and e.softname=d.softname " + "\n" 
		+ "               and e.verify='Y' " + "\n" 
		+ "               and e.startDate>= " + Common.sqlChar(Common.formatFrontZero(String.valueOf(yyy-3), 3)+"0101") + "\n"//'0940101' " + "\n"
		+ "               and e.startDate<= " + Common.sqlChar(Common.formatFrontZero(String.valueOf(yyy-3), 3)+"1231") + "\n"//'0941231' " + "\n"
		+ "               ),0 ) " + "\n"
		+ "         )as amount3 --3 " + "\n"
		+ "        ,(nvl((select sum(e.softAmount) from UNTSO_PCSoftware e " + "\n"
		+ "               where 1=1 " + "\n"
		+ "               and e.enterorg=d.enterorg " + "\n"
		+ "               and e.softname=d.softname " + "\n"
		+ "               and e.verify='Y' " + "\n"
		+ "               and e.startDate>= " + Common.sqlChar(Common.formatFrontZero(String.valueOf(yyy-4), 3)+"0101") + "\n"//'0930101' " + "\n"
		+ "               and e.startDate<= " + Common.sqlChar(Common.formatFrontZero(String.valueOf(yyy-4), 3)+"1231") + "\n"//'0931231' " + "\n"
		+ "               ),0 ) " + "\n"
		+ "         )as amount4 --4 " + "\n"
		+ "        ,(nvl((select sum(e.softAmount) from UNTSO_PCSoftware e " + "\n"
		+ "               where 1=1 " + "\n"
		+ "               and e.enterorg=d.enterorg " + "\n"
		+ "               and e.softname=d.softname " + "\n"
		+ "               and e.verify='Y' " + "\n"
		+ "               and e.startDate>= " + Common.sqlChar(Common.formatFrontZero(String.valueOf(yyy-5), 3)+"0101") + "\n"//'0920101' " + "\n"
		+ "               and e.startDate<= " + Common.sqlChar(Common.formatFrontZero(String.valueOf(yyy-5), 3)+"1231") + "\n"//'0921231' " + "\n"
		+ "               ),0 ) " + "\n"
		+ "         )as amount3 --5 " + "\n"
		+ "        ,(nvl((select sum(e.softAmount) from UNTSO_PCSoftware e " + "\n"
		+ "               where 1=1 " + "\n"
		+ "               and e.enterorg=d.enterorg " + "\n"
		+ "               and e.softname=d.softname " + "\n"
		+ "               and e.verify='Y' " + "\n"
		+ "               and e.startDate<= " + Common.sqlChar(Common.formatFrontZero(String.valueOf(yyy-6), 3)+"1231") + "\n"//'0911231' " + "\n"
		+ "               ),0 ) " + "\n"
		+ "         )as amount6 --6 " + "\n"
		+ " from UNTSO_PCSoftware d " + "\n"
		+ " where 1=1 " + "\n"
		+ " and d.enterorg="+ Common.sqlChar(getQ_enterOrg()) + "\n"
		+ " and d.startDate<= " + Common.sqlChar(Common.formatFrontZero(String.valueOf(yyy-1), 3)+"1231") + "\n"//'0961231' " + "\n"
		+ " and d.verify='Y' " + "\n"
		+ " group by d.enterorg ,d.softname " + "\n"
		+ " ) " + "\n"
		+ " order by propertyno ,computertype ";
	
		//System.out.println(sql);
	return sql;
}
/**
 * <br>
 * <br>目的：依主鍵查詢單一資料
 * <br>參數：無
 * <br>傳回：傳回本物件
*/
public SYSST016R  queryOne() throws Exception{
	Database db = new Database();
	SYSST016R obj = this;
	try {
		String sql=" select a.editID, a.propertyNo, a.propertyName, a.limitYear, a.propertyUnit, a.subTotal, a.amount1, a.amount2, a.amount3, a.amount4, a.amount5, a.amount6, a.editID, a.editDate, a.editTime  "+
			" from SYSST_sysst016r a where 1=1 " +
			" and a.editID = " + Common.sqlChar(getEditID()) +
			"";
		ResultSet rs = db.querySQL(sql);
		if (rs.next()){
			obj.setEditID(rs.getString("editID"));
			obj.setPropertyNo(rs.getString("propertyNo"));
			obj.setPropertyName(rs.getString("propertyName"));
			obj.setLimitYear(rs.getString("limitYear"));
			obj.setPropertyUnit(rs.getString("propertyUnit"));
			obj.setSubTotal(rs.getString("subTotal"));
			obj.setAmount1(rs.getString("amount1"));
			obj.setAmount2(rs.getString("amount2"));
			obj.setAmount3(rs.getString("amount3"));
			obj.setAmount4(rs.getString("amount4"));
			obj.setAmount5(rs.getString("amount5"));
			obj.setAmount6(rs.getString("amount6"));
			obj.setEditID(rs.getString("editID"));
			obj.setEditDate(rs.getString("editDate"));
			obj.setEditTime(rs.getString("editTime"));
		}
		setStateQueryOneSuccess();
	} catch (Exception e) {
		e.printStackTrace();
	} finally {
		db.closeAll();
	}
	return obj;
}

public void genExcelFile() {
	String td = util.Datetime.getYYYMMDD();
	String sqlnm="select a.organSName from SYSCA_Organ a where 1=1" + " and organID = " + Common.sqlChar(getQ_enterOrg());
	Database db= new Database();
	Database db1=new Database();
	try {
		//執行sql檔案!!
		//System.out.println("@" + getFileName() + "\\sysst016r.sql " + getEditID() + " " + getQ_enterOrg() + " " + getPlanYear());
    	Workbook w1 = Workbook.getWorkbook(new FileInputStream(filestoreLocation));		    
        String uploadCaseID = new java.rmi.dgc.VMID().toString();
        uploadCaseID = uploadCaseID.replaceAll(":","_");		
		File tempDirectory = new File(System.getProperty("java.io.tmpdir"));
		tempDirectory = new File(tempDirectory,uploadCaseID);
		tempDirectory.mkdirs();
		
	    WritableFont fontDefault =new WritableFont(WritableFont.createFont("細明體"),12);
		WritableCellFormat nothin = new WritableCellFormat(); 	//格式1
	    	//設定邊線
			nothin.setFont(fontDefault);
		   		nothin.setBorder(Border.TOP,BorderLineStyle.DOUBLE);
		   		nothin.setBorder(Border.LEFT,BorderLineStyle.DOUBLE);
		   		nothin.setBorder(Border.RIGHT,BorderLineStyle.DOUBLE);
		   		nothin.setBorder(Border.BOTTOM,BorderLineStyle.DOUBLE);
	 		   	nothin.setAlignment(Alignment.CENTRE);     //文字置中
	 		   	nothin.setVerticalAlignment(VerticalAlignment.CENTRE);    //文字上下置中
	 		//格式2表頭左
		   	WritableCellFormat nothin2 = new WritableCellFormat();	
		   		nothin2.setFont(fontDefault);
		   		nothin2.setAlignment(Alignment.LEFT);     //文字靠右
	 		   	nothin2.setVerticalAlignment(VerticalAlignment.CENTRE);

	 		//格式3表頭右   	
	 		WritableCellFormat nothin3 = new WritableCellFormat();	
	 			nothin3.setFont(fontDefault);
	 			nothin3.setAlignment(Alignment.RIGHT);     //文字靠右
	 			nothin3.setVerticalAlignment(VerticalAlignment.CENTRE);    //文字上下置中
	 			
		 	//格式3表頭右   	
		 	WritableCellFormat nothin6 = new WritableCellFormat();	
		 		nothin6.setFont(fontDefault);
	 	 		nothin6.setAlignment(Alignment.CENTRE);     //文字置中
	 	 		nothin6.setVerticalAlignment(VerticalAlignment.CENTRE);    //文字上下置中
	 			
	 		//Label(15,5)專用
			WritableCellFormat nothin4 = new WritableCellFormat(); 	//格式1
			    //設定邊線
				nothin4.setFont(fontDefault);
	 		   	nothin4.setBorder(Border.TOP,BorderLineStyle.DOUBLE);
	 		   	nothin4.setBorder(Border.LEFT,BorderLineStyle.DOUBLE);
	 		   	nothin4.setBorder(Border.RIGHT,BorderLineStyle.DOUBLE);
	 		   	//nothin4.setBorder(Border.BOTTOM,BorderLineStyle.DOUBLE);
	 	 		nothin4.setAlignment(Alignment.CENTRE);     //文字置中
	 	 		nothin4.setVerticalAlignment(VerticalAlignment.CENTRE);    //文字上下置中
	 	 	
	 		WritableCellFormat nothin5 = new WritableCellFormat(); 	//格式1
	 			//設定邊線
	 			nothin5.setFont(fontDefault);
	 			nothin5.setBorder(Border.TOP,BorderLineStyle.DOUBLE);
	 			nothin5.setBorder(Border.LEFT,BorderLineStyle.DOUBLE);
	 			nothin5.setBorder(Border.RIGHT,BorderLineStyle.DOUBLE);
	 			nothin5.setBorder(Border.BOTTOM,BorderLineStyle.DOUBLE);
	 			nothin5.setAlignment(Alignment.LEFT);     //文字置中
	 			nothin5.setVerticalAlignment(VerticalAlignment.CENTRE);    //文字上下置中

		WritableWorkbook w2 = Workbook.createWorkbook(new File(tempDirectory.getAbsoluteFile()+File.separator+"sysst016r.xls"), w1);
	    WritableSheet sheet = w2.getSheet(0);
	    	if (!"".equals(getQ_enterOrg())){
        	ResultSet rs1 = db1.querySQL(sqlnm);
        	try{
        		while(rs1.next()){
        			sheet.addCell(new Label(0,2,"機關名稱：" + rs1.getString("organSName"),nothin2));
        		}
        	} catch (Exception e) {
        		e.printStackTrace();
        	} finally {
        		db1.closeAll();}
	    	}else{
    			sheet.addCell(new Label(0,2,"機關名稱：高雄市政府",nothin2));	    		
	    	}
	    //表頭設定  
    	sheet.addCell(new Label(0,3,"年    度：" + getQ_year()+"年",nothin2));
    	//sheet.addCell(new Label(6,2,"第"+String.valueOf(sheet.getNumberOfImages()),nothin3));
    	sheet.addCell(new Label(4,3,"人數：" + get_people_number() +"人",nothin6));
    	sheet.addCell(new Label(10,3,"製表日期：" + td.substring(0,3) + "年" + td.substring(3,5) + "月" + td.substring(5,7) +"日",nothin3));
	    sheet.addCell(new Label(5,4,String.valueOf(Integer.parseInt(getQ_year())-1) + "年度",nothin));
	    sheet.addCell(new Label(7,4,String.valueOf(Integer.parseInt(getQ_year())-2) + "年度",nothin));
	    sheet.addCell(new Label(9,4,String.valueOf(Integer.parseInt(getQ_year())-3) + "年度",nothin));
	    sheet.addCell(new Label(11,4,String.valueOf(Integer.parseInt(getQ_year())-4) + "年度",nothin));
	    sheet.addCell(new Label(13,4,String.valueOf(Integer.parseInt(getQ_year())-5) + "年度",nothin));
	    //sheet.addCell(new Label(15,5,String.valueOf(Integer.parseInt(getQ_year())-6) + "年度",nothin4));
	    //查詢資料
    	//String execSQL= Common.callSQLFileRead(getFileName()+"\\sysst016r_1.sql",1,getEditID());
	    String execSQL= getSql();
	    
        ResultSet rs = db.querySQL(execSQL);
        int a = 9;
        int b = 1;
		String computerTypeChin="";
        while(rs.next()){
			computerTypeChin="";
			computerTypeChin= query_value("select codeName from SYSCA_Code where 1=1 and codekindid='PCT' and codecon1="+Common.sqlChar(rs.getString("propertyNo"))+" and codeID="+Common.sqlChar(rs.getString("computertype")),"codeName");
        	sheet.addCell(new Label(0,a,String.valueOf(b),nothin));
        	if("".equals(computerTypeChin)){
        		sheet.addCell(new Label(1,a,rs.getString("propertyName"),nothin5));
        	}else{
        		sheet.addCell(new Label(1,a,rs.getString("propertyName")+"("+computerTypeChin+")",nothin5));
        	}
        	sheet.addCell(new Label(2,a,rs.getString("limitYear"),nothin));
        	sheet.addCell(new Label(3,a,rs.getString("propertyUnit"),nothin));
        	sheet.addCell(new Label(4,a,rs.getString("subTotal"),nothin));
        	sheet.addCell(new Label(5,a,rs.getString("amount1"),nothin));
        	sheet.addCell(new Label(6,a,"",nothin));
        	sheet.addCell(new Label(7,a,rs.getString("amount2"),nothin));
        	sheet.addCell(new Label(8,a,"",nothin));
        	sheet.addCell(new Label(9,a,rs.getString("amount3"),nothin));
        	sheet.addCell(new Label(10,a,"",nothin));
        	sheet.addCell(new Label(11,a,rs.getString("amount4"),nothin));
        	sheet.addCell(new Label(12,a,"",nothin));
        	sheet.addCell(new Label(13,a,rs.getString("amount5"),nothin));
        	sheet.addCell(new Label(14,a,"",nothin));
        	sheet.addCell(new Label(15,a,rs.getString("amount6"),nothin));
        	sheet.addCell(new Label(16,a,"",nothin));
        	a += 1;
        	b += 1;
        }
	    w1.close();
	    w2.write();
	    w2.close();
		this.setExcelFileName(tempDirectory.getAbsoluteFile()+File.separator+"sysst016r.xls");
        util.FileDeleteProcess.scheduleDeleteFile(tempDirectory,10*60000);
		super.setState("exportSuccess");			

	} catch (Exception e) {
		e.printStackTrace();
	} finally {
		db.closeAll();
		}
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

	try {
		String computerTypeChin="";
    	ResultSet rs = db.querySQL(getSql(),true);
		processCurrentPageAttribute(rs);
		if (getTotalRecord() > 0) {
			int count = getRecordStart();
			int end = getRecordEnd();
			do {
				if (count > end) break;
			String rowArray[]=new String[12];
			
			computerTypeChin= query_value("select codeName from SYSCA_Code where 1=1 and codekindid='PCT' and codecon1="+Common.sqlChar(rs.getString("propertyNo"))+" and codeID="+Common.sqlChar(rs.getString("computertype")),"codeName");
		
			rowArray[0]=Common.get(getEditID()); 
			rowArray[1]=Common.get(rs.getString("propertyNo"));
			
			if(!"".equals(computerTypeChin)){
				rowArray[2]=Common.get(rs.getString("propertyName"))+"("+computerTypeChin+")"; 
			}else{
				rowArray[2]=Common.get(rs.getString("propertyName")); 
			}
			
			rowArray[3]=Common.get(rs.getString("limitYear")); 
			rowArray[4]=Common.get(rs.getString("propertyUnit")); 
			rowArray[5]=Common.get(rs.getString("subTotal")); 
			rowArray[6]=Common.get(rs.getString("amount1")); 
			rowArray[7]=Common.get(rs.getString("amount2")); 
			rowArray[8]=Common.get(rs.getString("amount3")); 
			rowArray[9]=Common.get(rs.getString("amount4")); 
			rowArray[10]=Common.get(rs.getString("amount5")); 
			rowArray[11]=Common.get(rs.getString("amount6")); 
			objList.add(rowArray);
			count++;
			} while (rs.next());
		}
		setStateQueryAllSuccess();
	} catch (Exception e) {
		e.printStackTrace();
	} finally {
		db.closeAll();
	}
	return objList;
}

	public String query_value(String sql_code ,String getFile){
	String reValue="";
	Database db = new Database();
	ResultSet rs;
	try{
		rs = db.querySQL(sql_code);
		if(rs.next()){
			reValue=rs.getString(getFile);
		}
	} catch (Exception e) {
			e.printStackTrace();
	} finally {
			db.closeAll();
	}
		return reValue;
	}
	//連結人事系統資料庫
	public String get_people_number(){
		int reValue=0;
	    String sql = " (select count(*) as cnt from v02a a where 1=1 and a.v02acorg= " + Common.sqlChar(getQ_enterOrg())+ " and a.v02alvl='0') "
	    		   + " union "
	    		   + " (select count(*) as cnt from vkb b where 1=1 and b.vkbcorg= " + Common.sqlChar(getQ_enterOrg()) + " and b.vkblvl='0') "
	    		   ;
	    Connection myconn;
	    Statement sqlstatement;
	    ResultSet rs;

	    try{
	      //載入JDBC-ODBC driver
	      Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
	      //使用data sourece來連結資料庫
	      myconn = DriverManager.getConnection("jdbc:oracle:thin:@128.1.1.30:1521:psn","kcg38303","org80225");
	      sqlstatement =  myconn.createStatement();
	      rs = sqlstatement.executeQuery(sql);
	      while (rs.next())
	      {
	        reValue += Integer.parseInt(rs.getString("cnt"));
	      }
	      rs.close();
	      sqlstatement.close();
	      myconn.close();
	      //System.exit(0);
	    }
	    catch (Exception e)
	    {
	      System.err.println(e);
	    }
	    
		return String.valueOf(reValue);
	}
}


