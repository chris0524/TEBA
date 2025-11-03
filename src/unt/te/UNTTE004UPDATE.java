/*
*<br>程式目的：地價稅課稅明細上傳作業
*<br>程式代號：untup001f
*<br>撰寫日期：95/07/06
*<br>程式作者：clive.chang
*<br>--------------------------------------------------------
*<br>修改作者　　修改日期　　　修改目的
*<br>--------------------------------------------------------
*/

package unt.te;

import java.io.*;
import java.sql.ResultSet;
import java.util.ArrayList;
import util.*;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.swing.JOptionPane;

import util.Common;
import util.Database;

public class UNTTE004UPDATE extends UNTTE004F{

	String lndYear;
	String lndLosn;
	String signNo;
	String lndTaxTp1;
	String levyArea1;
	String abateArea1;
	String abateAll1;
	String lndPreTax1;
	String lndTaxTp2;
	String levyArea2;
	String abateArea2;
	String abateAll2;
	String lndPreTax2;
	String lndTaxTp3;
	String levyArea3;
	String abateArea3;
	String abateAll3;
	String lndPreTax3;
	String lndTaxTp4;
	String levyArea4;
	String abateArea4;
	String abateAll4;
	String lndPreTax4;
	String lndTaxTp5;
	String levyArea5;
	String abateArea5;
	String abateAll5;
	String lndPreTax5;
	String secChiNM;
	String town_NM;
	String OD;
	String OA;
	String editID;
	String editDate;
	String editTime;
	static String storeDate;
	
	public String getLndYear(){ return checkGet(lndYear); }
	public void setLndYear(String s){ lndYear=checkSet(s); }
	public String getLndLosn(){ return checkGet(lndLosn); }
	public void setLndLosn(String s){ lndLosn=checkSet(s); }
	public String getSignNO(){ return checkGet(signNo); }
	public void setSignNO(String s){ signNo=checkSet(s); }
	public String getLndTaxTp1(){ return checkGet(lndTaxTp1); }
	public void setLndTaxTp1(String s){ lndTaxTp1=checkSet(s); }
	public String getLevyArea1(){ return checkGet(levyArea1); }
	public void setLevyArea1(String s){ levyArea1=checkSet(s); }
	public String getAbateArea1(){ return checkGet(abateArea1); }
	public void setAbateArea1(String s){ abateArea1=checkSet(s); }
	public String getAbateAll1(){ return checkGet(abateAll1); }
	public void setAbateAll1(String s){ abateAll1=checkSet(s); }
	public String getLndPreTax1(){ return checkGet(lndPreTax1); }
	public void setLndPreTax1(String s){ lndPreTax1=checkSet(s); }
	public String getLndTaxTp2(){ return checkGet(lndTaxTp2); }
	public void setLndTaxTp2(String s){ lndTaxTp2=checkSet(s); }
	public String getLevyArea2(){ return checkGet(levyArea2); }
	public void setLevyArea2(String s){ levyArea2=checkSet(s); }
	public String getAbateArea2(){ return checkGet(abateArea2); }
	public void setAbateArea2(String s){ abateArea2=checkSet(s); }
	public String getAbateAll2(){ return checkGet(abateAll2); }
	public void setAbateAll2(String s){ abateAll2=checkSet(s); }
	public String getLndPreTax2(){ return checkGet(lndPreTax2); }
	public void setLndPreTax2(String s){ lndPreTax2=checkSet(s); }
	public String getLndTaxTp3(){ return checkGet(lndTaxTp3); }
	public void setLndTaxTp3(String s){ lndTaxTp3=checkSet(s); }
	public String getLevyArea3(){ return checkGet(levyArea3); }
	public void setLevyArea3(String s){ levyArea3=checkSet(s); }
	public String getAbateArea3(){ return checkGet(abateArea3); }
	public void setAbateArea3(String s){ abateArea3=checkSet(s); }
	public String getAbateAll3(){ return checkGet(abateAll3); }
	public void setAbateAll3(String s){ abateAll3=checkSet(s); }
	public String getLndPreTax3(){ return checkGet(lndPreTax3); }
	public void setLndPreTax3(String s){ lndPreTax3=checkSet(s); }
	public String getLndTaxTp4(){ return checkGet(lndTaxTp4); }
	public void setLndTaxTp4(String s){ lndTaxTp4=checkSet(s); }
	public String getLevyArea4(){ return checkGet(levyArea4); }
	public void setLevyArea4(String s){ levyArea4=checkSet(s); }
	public String getAbateArea4(){ return checkGet(abateArea4); }
	public void setAbateArea4(String s){ abateArea4=checkSet(s); }
	public String getAbateAll4(){ return checkGet(abateAll4); }
	public void setAbateAll4(String s){ abateAll4=checkSet(s); }
	public String getLndPreTax4(){ return checkGet(lndPreTax4); }
	public void setLndPreTax4(String s){ lndPreTax4=checkSet(s); }
	public String getLndTaxTp5(){ return checkGet(lndTaxTp5); }
	public void setLndTaxTp5(String s){ lndTaxTp5=checkSet(s); }
	public String getLevyArea5(){ return checkGet(levyArea5); }
	public void setLevyArea5(String s){ levyArea5=checkSet(s); }
	public String getAbateArea5(){ return checkGet(abateArea5); }
	public void setAbateArea5(String s){ abateArea5=checkSet(s); }
	public String getAbateAll5(){ return checkGet(abateAll5); }
	public void setAbateAll5(String s){ abateAll5=checkSet(s); }
	public String getLndPreTax5(){ return checkGet(lndPreTax5); }
	public void setLndPreTax5(String s){ lndPreTax5=checkSet(s); }
	public String getSecChiNM(){ return checkGet(secChiNM); }
	public void setSecChiNM(String s){ secChiNM=checkSet(s); }
	public String getTown_NM(){ return checkGet(town_NM); }
	public void setTown_NM(String s){ town_NM=checkSet(s); }
	public String getOD(){ return checkGet(OD); }
	public void setOD(String s){ OD=checkSet(s); }
	public String getOA(){ return checkGet(OA); }
	public void setOA(String s){ OA=checkSet(s); }
	public String getEditID(){ return checkGet(editID); }
	public void setEditID(String s){ editID=checkSet(s); }
	public String getEditDate(){ return checkGet(editDate); }
	public void setEditDate(String s){ editDate=checkSet(s); }
	public String getEditTime(){ return checkGet(editTime); }
	public void setEditTime(String s){ editTime=checkSet(s); }


	
	String fileName;
	public String getFileName(){ return checkGet(fileName); }
	public void setFileName(String s){ fileName=checkSet(s); }


	
public void oneLndYYY() throws IOException{
	System.out.println("lndYYY:"+lndYear);
	storeDate = lndYear;
}

public String getoneLndYYY() throws IOException{
	System.out.println("lndYYY:"+lndYear);
	return storeDate ;
	
}


public void oneCLICKone() throws IOException{
	String fisline= "c:\\\\Upload\\"+ fileName ;
	
	System.out.println("lndYear:"+lndYear);
	
	Database db = new Database();
	BufferedReader br = new BufferedReader(new FileReader(fisline));
	String[] execSQLArray=new String [1];
	String lineCh;//取得字串	
	
	//File myFile=new File("C:/Upload/test.txt");  
	//FileWriter myFileWriter=new FileWriter(myFile);
	//BufferedWriter myBuffer=new BufferedWriter(myFileWriter);
	
	
	try{
		while((lineCh=br.readLine()) != null ){
			execSQLArray[0]="insert into LND_Value ("+"\n"+
				"lndYYY,lndLosn,signNo,"+"\n"+
				"lndTaxTp1,levyArea1,abateArea1,abateAll1,lndPreTax1,"+"\n"+
				"lndTaxTp2,levyArea2,abateArea2,abateAll2,lndPreTax2,"+"\n"+
				"lndTaxTp3,levyArea3,abateArea3,abateAll3,lndPreTax3,"+"\n"+
				"lndTaxTp4,levyArea4,abateArea4,abateAll4,lndPreTax4,"+"\n"+
				"lndTaxTp5,levyArea5,abateArea5,abateAll5,lndPreTax5,"+"\n"+
				"secChiNM,town_NM,OD,OA,editID,editDate,editTime"+"\n"+
			" )Values("+"\n"+
				Common.sqlChar(storeDate) + "," +
				Common.sqlChar(lineCh.substring(0,12)) + "," +
				Common.sqlChar("E"+lineCh.substring(12,26)) + "," +"\n"+
				Common.sqlChar(lineCh.substring(26,27)) + "," +
				Common.sqlChar(Double.toString(Double.parseDouble(lineCh.substring(27,36))*0.01)) + "," +
				Common.sqlChar(Double.toString(Double.parseDouble(lineCh.substring(36,45))*0.01)) + "," +
				Common.sqlChar(Double.toString(Double.parseDouble(lineCh.substring(45,54))*0.01)) + "," +
				Common.sqlChar(Integer.toString(Integer.parseInt(lineCh.substring(54,65)))) + "," +"\n"+
				Common.sqlChar(lineCh.substring(65,66)) + "," +
				Common.sqlChar(Double.toString(Double.parseDouble(lineCh.substring(66,75))*0.01)) + "," +
				Common.sqlChar(Double.toString(Double.parseDouble(lineCh.substring(75,84))*0.01)) + "," +
				Common.sqlChar(Double.toString(Double.parseDouble(lineCh.substring(84,93))*0.01)) + "," +
				Common.sqlChar(Integer.toString(Integer.parseInt(lineCh.substring(93,104)))) + "," +"\n"+
				Common.sqlChar(lineCh.substring(104,105)) + "," +
				Common.sqlChar(Double.toString(Double.parseDouble(lineCh.substring(105,114))*0.01)) + "," +
				Common.sqlChar(Double.toString(Double.parseDouble(lineCh.substring(114,123))*0.01)) + "," +
				Common.sqlChar(Double.toString(Double.parseDouble(lineCh.substring(123,132))*0.01)) + "," +
				Common.sqlChar(Integer.toString(Integer.parseInt(lineCh.substring(132,143)))) + "," +"\n"+
				Common.sqlChar(lineCh.substring(143,144)) + "," +
				Common.sqlChar(Double.toString(Double.parseDouble(lineCh.substring(144,153))*0.01)) + "," +
				Common.sqlChar(Double.toString(Double.parseDouble(lineCh.substring(153,162))*0.01)) + "," +
				Common.sqlChar(Double.toString(Double.parseDouble(lineCh.substring(162,171))*0.01)) + "," +
				Common.sqlChar(Integer.toString(Integer.parseInt(lineCh.substring(171,182)))) + "," +"\n"+
				Common.sqlChar(lineCh.substring(182,183)) + "," +
				Common.sqlChar(Double.toString(Double.parseDouble(lineCh.substring(183,192))*0.01)) + "," +
				Common.sqlChar(Double.toString(Double.parseDouble(lineCh.substring(192,201))*0.01)) + "," +
				Common.sqlChar(Double.toString(Double.parseDouble(lineCh.substring(201,210))*0.01)) + "," +
				Common.sqlChar(Integer.toString(Integer.parseInt(lineCh.substring(210,221)))) + "," +"\n"+
				Common.sqlChar(lineCh.substring(221,261)) + "," +
				Common.sqlChar(lineCh.substring(261,277)) + "," +
				Common.sqlChar(lineCh.substring(277,278)) + "," +"\n"+
				Common.sqlChar(lineCh.substring(278,279)) + "," +
				Common.sqlChar(getEditID()) + "," +
				Common.sqlChar(getEditDate()) + "," +
				Common.sqlChar(getEditTime()) + ")" ;
			System.out.println("execSQLArray[0]："+execSQLArray[0]);
			//myBuffer.write(execSQLArray[0]);
			//myBuffer.newLine();
			//myBuffer.newLine();
			db.exeSQL(execSQLArray);
		}
	} catch (Exception e) {
		//myBuffer.write(e.toString());
		//myBuffer.newLine();
		e.printStackTrace();
	} finally {
		db.closeAll();
	}
	System.out.println("");
	br.close();
	//myBuffer.close();
	//myFileWriter.close();
	File a=new File("c:\\\\Upload\\",fileName);
	a.delete();
	setErrorMsg("上傳檔案成功");
	setStateInsertSuccess();	
}

/*
public void oneCLICKone() throws IOException{
	String fisline= "c:\\\\Upload\\"+ fileName ;
	
	System.out.println("lndYear:"+lndYear);
	
	Database db = new Database();
	BufferedReader br = new BufferedReader(new FileReader(fisline));
	String[] execSQLArray=new String [1];
	String lineCh;//取得字串	
	
	while((lineCh=br.readLine()) != null ){
		try{
			execSQLArray[0]="insert into LND_Value ("+"\n"+
				"lndYYY,lndLosn,signNo,"+"\n"+
				"lndTaxTp1,levyArea1,abateArea1,abateAll1,lndPreTax1,"+"\n"+
				"lndTaxTp2,levyArea2,abateArea2,abateAll2,lndPreTax2,"+"\n"+
				"lndTaxTp3,levyArea3,abateArea3,abateAll3,lndPreTax3,"+"\n"+
				"lndTaxTp4,levyArea4,abateArea4,abateAll4,lndPreTax4,"+"\n"+
				"lndTaxTp5,levyArea5,abateArea5,abateAll5,lndPreTax5,"+"\n"+
				"secChiNM,town_NM,OD,OA,editID,editDate,editTime"+"\n"+
			" )Values("+"\n"+
				Common.sqlChar(storeDate) + "," +
				Common.sqlChar(lineCh.substring(0,12)) + "," +
				Common.sqlChar("E"+lineCh.substring(12,26)) + "," +"\n"+
				Common.sqlChar(lineCh.substring(26,27)) + "," +
				Common.sqlChar(Double.toString(Double.parseDouble(lineCh.substring(27,36))*0.01)) + "," +
				Common.sqlChar(Double.toString(Double.parseDouble(lineCh.substring(36,45))*0.01)) + "," +
				Common.sqlChar(Double.toString(Double.parseDouble(lineCh.substring(45,54))*0.01)) + "," +
				Common.sqlChar(Integer.toString(Integer.parseInt(lineCh.substring(54,65)))) + "," +"\n"+
				Common.sqlChar(lineCh.substring(65,66)) + "," +
				Common.sqlChar(Double.toString(Double.parseDouble(lineCh.substring(66,75))*0.01)) + "," +
				Common.sqlChar(Double.toString(Double.parseDouble(lineCh.substring(75,84))*0.01)) + "," +
				Common.sqlChar(Double.toString(Double.parseDouble(lineCh.substring(84,93))*0.01)) + "," +
				Common.sqlChar(Integer.toString(Integer.parseInt(lineCh.substring(93,104)))) + "," +"\n"+
				Common.sqlChar(lineCh.substring(104,105)) + "," +
				Common.sqlChar(Double.toString(Double.parseDouble(lineCh.substring(105,114))*0.01)) + "," +
				Common.sqlChar(Double.toString(Double.parseDouble(lineCh.substring(114,123))*0.01)) + "," +
				Common.sqlChar(Double.toString(Double.parseDouble(lineCh.substring(123,132))*0.01)) + "," +
				Common.sqlChar(Integer.toString(Integer.parseInt(lineCh.substring(132,143)))) + "," +"\n"+
				Common.sqlChar(lineCh.substring(143,144)) + "," +
				Common.sqlChar(Double.toString(Double.parseDouble(lineCh.substring(144,153))*0.01)) + "," +
				Common.sqlChar(Double.toString(Double.parseDouble(lineCh.substring(153,162))*0.01)) + "," +
				Common.sqlChar(Double.toString(Double.parseDouble(lineCh.substring(162,171))*0.01)) + "," +
				Common.sqlChar(Integer.toString(Integer.parseInt(lineCh.substring(171,182)))) + "," +"\n"+
				Common.sqlChar(lineCh.substring(182,183)) + "," +
				Common.sqlChar(Double.toString(Double.parseDouble(lineCh.substring(183,192))*0.01)) + "," +
				Common.sqlChar(Double.toString(Double.parseDouble(lineCh.substring(192,201))*0.01)) + "," +
				Common.sqlChar(Double.toString(Double.parseDouble(lineCh.substring(201,210))*0.01)) + "," +
				Common.sqlChar(Integer.toString(Integer.parseInt(lineCh.substring(210,221)))) + "," +"\n"+
				Common.sqlChar(lineCh.substring(221,261)) + "," +
				Common.sqlChar(lineCh.substring(261,277)) + "," +
				Common.sqlChar(lineCh.substring(277,278)) + "," +"\n"+
				Common.sqlChar(lineCh.substring(278,279)) + "," +
				Common.sqlChar(getEditID()) + "," +
				Common.sqlChar(getEditDate()) + "," +
				Common.sqlChar(getEditTime()) + ")" ;
			System.out.println("execSQLArray[0]："+execSQLArray[0]);
			db.exeSQL(execSQLArray);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.closeAll();
		}
	}
	System.out.println("");
	br.close();
	setErrorMsg("上傳檔案成功");
	setStateInsertSuccess();	
}
*/

public void delLndData() throws IOException{
	Database db = new Database();
	String[] execSQLArray=new String [1];	
		try{
			execSQLArray[0]="delete from lnd_value where lndYYY="+Common.sqlChar(Common.formatFrontZero(lndYear,3));
			System.out.println("execSQLArray[0]："+execSQLArray[0]);
			db.exeSQL(execSQLArray);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.closeAll();
		}
	System.out.println("");
	setErrorMsg("資料刪除");
	setStateInsertSuccess();	
}


}
