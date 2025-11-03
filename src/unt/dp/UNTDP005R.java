package unt.dp;

import java.util.ArrayList;
import java.util.Vector;

import javax.swing.table.DefaultTableModel;

import TDlib_Simple.tools.src.StringTools;
import unt.ch.UNTCH_Tools;
import util.*;

public class UNTDP005R extends SuperBean{

	private String deprYM;
	private String bookValue;
	private String scrapValue;
	private String buyDate;
	private String enterDate;
	private String limitYear;
	private String adjustDate;
	private String addBookValue;
	private String reduceBookValue;
	private String apportionYear1;
	private String addValue;
	
	public String getDeprYM() {	return checkGet(deprYM);}
	public void setDeprYM(String deprYM) {this.deprYM = checkSet(deprYM);}
	public String getBookValue() {	return checkGet(bookValue);}
	public void setBookValue(String bookValue) {this.bookValue = checkSet(bookValue);	}
	public String getScrapValue() {return checkGet(scrapValue);}
	public void setScrapValue(String scrapValue) {this.scrapValue = checkSet(scrapValue);	}
	public String getBuyDate() {return checkGet(buyDate);	}
	public void setBuyDate(String buyDate) {this.buyDate = checkSet(buyDate);	}
	public String getEnterDate() {return checkGet(enterDate);}
	public void setEnterDate(String enterDate) {this.enterDate = checkSet(enterDate);	}
	public String getLimitYear() {return checkGet(limitYear);	}
	public void setLimitYear(String limitYear) {this.limitYear = checkSet(limitYear);	}
	public String getAdjustDate() {	return checkGet(adjustDate);}
	public void setAdjustDate(String adjustDate) {this.adjustDate = checkSet(adjustDate);	}
	public String getAddBookValue() {return checkGet(addBookValue);}
	public void setAddBookValue(String addBookValue) {	this.addBookValue = checkSet(addBookValue);}
	public String getReduceBookValue() {return checkGet(reduceBookValue);	}
	public void setReduceBookValue(String reduceBookValue) {this.reduceBookValue = checkSet(reduceBookValue);}
	public String getApportionYear1() {return checkGet(apportionYear1);}
	public void setApportionYear1(String apportionYear1) {this.apportionYear1 = checkSet(apportionYear1);	}
	public String getAddValue() {return checkGet(addValue);}
	public void setAddValue(String addValue) {this.addValue = checkSet(addValue);	}
	
	
	public ArrayList printAddProof1() throws Exception{
		
		UNTCH_Tools ul = new UNTCH_Tools();
		StringTools st = new StringTools();
		Database db = new Database();
		ArrayList objList=new ArrayList();
		int counter=0;
		try {
			String tempDeprYM = Common.get(getDeprYM());  //折舊年月
			String tempBuyDate = Common.get(getBuyDate()); //購置日期
			String tempEnterDate = Common.get(getEnterDate()); //入帳日期
			String tempBookValue = Common.get(getBookValue());//原值
			String tempScrapValue = Common.get(getScrapValue());//殘值
			String tempLimitYear = Common.get(getLimitYear());//年限
			String tempApportionYear1 = Common.get(getApportionYear1());//增減值年限
			String tempAddBookValue = Common.get(getAddBookValue());//增值
			String tempReduceBookValue = Common.get(getReduceBookValue());//減值
			String tempAdjustDate = Common.get(getAdjustDate());//增減值年月
			String TempAdjustLastDate="";
			//System.out.println("tempApportionYear1==>"+tempApportionYear1);
			int tempDeprToEnterDate=0;
			int tempBuyToEnterDate=0;
			int tempDeprToBuyDate=0;
			int tempBuyToLimitDate=0;
			int tempEnterToAdjustDate=0;
			int tempBuyToAdjustDate =0;
			int tempAdjustToDeprDate =0;
			int deprValueI=0;
			int deprValueI1=0;
			int deprValueI2=0;
			int TempLastValue=0;
			int TempAdjustLastValue=0;
			int accountValue=0;
			int tempDeprValue=0;
			int TempLastValue2=0;
			int TempAdjustLastValue2=0;
			int accountValue2=0;
			int tempDeprValue2=0;
			int tempAccountValue=Integer.parseInt(getBookValue());
			int tempAccountValue2=Integer.parseInt(getBookValue());
			int tempLimitYearToMonth = Integer.parseInt(tempLimitYear);
			int tempAdjustToApportionYear1 =0;
			
			
			//入帳日期+1到折舊月份有幾個月
			tempDeprToEnterDate = Datetime.BetweenTwoMonth(tempEnterDate,tempDeprYM);
			
			//購置日期到入帳日期有幾個月
			tempBuyToEnterDate = Datetime.BetweenTwoMonth(tempBuyDate,tempEnterDate);
			//購置日期到折舊月份有幾個月
			tempDeprToBuyDate = Datetime.BetweenTwoMonth(tempBuyDate,tempDeprYM);
			//若購置日期=入帳日期，從下一個月開始才提折舊
			if(tempBuyDate.equals(tempEnterDate)){
				tempDeprToEnterDate=tempDeprToEnterDate-1;
			}

			//計算折舊最後一個月日期  購置日期+年限
			String TempLastDate=Datetime.getDateAdd("y", +tempLimitYearToMonth,tempBuyDate+"01" ).substring(0, 5);
			//購置日期到購置日期+年限有幾個月
			tempBuyToLimitDate = Datetime.BetweenTwoMonth(tempBuyDate,TempLastDate);
			
			//勾選增減值checkbox後，所需的變數設定
			if(getAddValue().equals("Y")){
			//入帳日期到增減值年月前共幾個月
			tempEnterToAdjustDate = Datetime.BetweenTwoMonth(tempEnterDate,tempAdjustDate)-1;
			System.out.println("tempEnterToAdjustDate==>"+tempEnterToAdjustDate);
			//增減年月到折舊年月共幾個月
			tempAdjustToDeprDate = Datetime.BetweenTwoMonth(tempAdjustDate,tempDeprYM);
			//購置日期到增減值年月前共幾個月
			tempBuyToAdjustDate = Datetime.BetweenTwoMonth(tempBuyDate,tempAdjustDate);
			}		
			
			if(getAddValue().equals("N")){
						
			for(int i=0; i<=tempDeprToEnterDate; i++){
				String rowArray[]=new String[10];
				counter++;
				//計算每月折舊
				deprValueI = ((Integer.parseInt(tempBookValue)-Integer.parseInt(tempScrapValue))/Integer.parseInt(tempLimitYear))/12;
				if(tempBuyDate.equals(tempEnterDate)){
				rowArray[0]=Datetime.getDateAdd("m", +i+1,tempEnterDate+"01" ).substring(0, 5);
				}
				else {
				rowArray[0]=Datetime.getDateAdd("m", +i,tempEnterDate+"01" ).substring(0, 5);
				}
				
				//購置日期<入帳日期 補提折舊
					if(Integer.parseInt(tempBuyDate)<Integer.parseInt(tempEnterDate) && i==0){
					deprValueI = deprValueI*(tempBuyToEnterDate);
					}
					else if (i==0 && tempBuyDate.equals(tempEnterDate)){
					deprValueI = deprValueI;
					}
					else if (i==tempDeprToEnterDate){
						if(TempLastDate.equals(tempDeprYM)){
							TempLastValue = Integer.parseInt(getBookValue())-(deprValueI*(tempBuyToLimitDate-1));
							deprValueI = TempLastValue;
						}
						else{
						deprValueI=deprValueI;
						}
					}
					else{
						deprValueI=deprValueI;
						}
					rowArray[1]=st._getMoneyFormat(tempBookValue);
					rowArray[2]=st._getMoneyFormat(tempBookValue);
					rowArray[3]=st._getMoneyFormat(tempScrapValue);
					rowArray[4]=tempLimitYear;
					rowArray[5]=tempBuyDate;
					rowArray[6]=tempEnterDate;
					rowArray[7]=st._getMoneyFormat(String.valueOf(deprValueI));
					accountValue = Integer.parseInt(tempBookValue)-deprValueI;
					tempAccountValue = tempAccountValue+accountValue-Integer.parseInt(tempBookValue);
					rowArray[8]=st._getMoneyFormat(String.valueOf(tempAccountValue));
					tempDeprValue = tempDeprValue+deprValueI;
					rowArray[9]=st._getMoneyFormat(String.valueOf(tempDeprValue));
					objList.add(rowArray);
					
					
				}
			}
			//需要做增減值計算
				else if(getAddValue().equals("Y") && Integer.parseInt(tempAdjustDate) >= Integer.parseInt(tempEnterDate) ){														
					//跑入帳日期到增減值年月部分
					for(int i=0; i<tempEnterToAdjustDate; i++){
						String rowArray[]=new String[10];
						counter++;
						
						if(tempBuyDate.equals(tempEnterDate)){
						rowArray[0]=Datetime.getDateAdd("m", +i+1,tempEnterDate+"01" ).substring(0, 5);
						}
						else {
						rowArray[0]=Datetime.getDateAdd("m", +i,tempEnterDate+"01" ).substring(0, 5);
						}
						//計算每月折舊
						deprValueI = ((Integer.parseInt(tempBookValue)-Integer.parseInt(tempScrapValue))/Integer.parseInt(tempLimitYear))/12;
						//購置日期<入帳日期 補提折舊
							if(Integer.parseInt(tempBuyDate)<Integer.parseInt(tempEnterDate) && i==0){
							deprValueI = deprValueI*(tempBuyToEnterDate);
							}
							else if (i==0 && tempBuyDate.equals(tempEnterDate)){
							deprValueI = deprValueI;
							}
							else if (i==tempDeprToEnterDate){
								if(TempLastDate.equals(tempDeprYM)){
									TempLastValue = Integer.parseInt(getBookValue())-(deprValueI*(tempBuyToLimitDate-1));
									deprValueI = TempLastValue;
								}
								else{
								deprValueI=deprValueI;
								}
							}
							else{
								deprValueI=deprValueI;
								}
							rowArray[1]=st._getMoneyFormat(tempBookValue);
							rowArray[2]=st._getMoneyFormat(tempBookValue);
							rowArray[3]=st._getMoneyFormat(tempScrapValue);
							
							rowArray[4]=tempLimitYear;
							rowArray[5]=tempBuyDate;
							rowArray[6]=tempEnterDate;
							rowArray[7]=st._getMoneyFormat(String.valueOf(deprValueI));
							accountValue = Integer.parseInt(tempBookValue)-deprValueI;
							tempAccountValue = tempAccountValue+accountValue-Integer.parseInt(tempBookValue);
							rowArray[8]=st._getMoneyFormat(String.valueOf(tempAccountValue));
							tempDeprValue = tempDeprValue+deprValueI;
							rowArray[9]=st._getMoneyFormat(String.valueOf(tempDeprValue));
							objList.add(rowArray);
							
						}
					//跑增減值年月到折舊年月部分
					for(int j=0; j<=tempAdjustToDeprDate; j++){
						String rowArray[]=new String[10];
						counter++;
						
						if(tempBuyDate.equals(tempAdjustDate)){
							rowArray[0]=Datetime.getDateAdd("m", +j+1,tempAdjustDate+"01" ).substring(0, 5);
							}
							else {
							rowArray[0]=Datetime.getDateAdd("m", +j,tempAdjustDate+"01" ).substring(0, 5);
							}
						int tempApportionYear1ToMonth = Integer.parseInt(""==tempApportionYear1?"0":tempApportionYear1);
						//計算折舊最後一個月日期  購置日期+年限
						TempAdjustLastDate=Datetime.getDateAdd("y", +tempApportionYear1ToMonth,tempAdjustDate+"01" ).substring(0, 5);
						//增減值年月到增減值年月+增減值年限有幾個月
						tempAdjustToApportionYear1 = Datetime.BetweenTwoMonth(tempAdjustDate,TempAdjustLastDate);
						if(tempApportionYear1.equals("")){
							//如果增減值年限為空，年限計算為原年限減入帳年月到增減值年月，deprValueI為先算出原始每月折舊，deprValueI2為原值-殘值+增值-減值-(累計折舊)/(原年限-入帳到增減的月數)
							deprValueI = ((Integer.parseInt(tempBookValue)-Integer.parseInt(tempScrapValue))/Integer.parseInt(tempLimitYear))/12;
							deprValueI2 = (Integer.parseInt(tempBookValue)-Integer.parseInt(tempScrapValue)+Integer.parseInt(tempAddBookValue)-Integer.parseInt(tempReduceBookValue)-(deprValueI*(tempBuyToAdjustDate-1)))/(Integer.parseInt(tempLimitYear)*12-tempEnterToAdjustDate) ;
						}
						else{
							//如果增減值年限有值，年限計算為增減值年月+增減值年限再加上原本年限(需要減掉已使用的月數)， deprValueI為先算出原始每月折舊，deprValueI2為原值-殘值+增值-減值-(累計折舊)/(增減值年限+原年限-入帳到增減的月數)
							deprValueI = ((Integer.parseInt(tempBookValue)-Integer.parseInt(tempScrapValue))/Integer.parseInt(tempLimitYear))/12;
							deprValueI2 = (Integer.parseInt(tempBookValue)-Integer.parseInt(tempScrapValue)+Integer.parseInt(tempAddBookValue)-Integer.parseInt(tempReduceBookValue)-(deprValueI*(tempBuyToAdjustDate-1)))/(Integer.parseInt(tempApportionYear1)*12+(Integer.parseInt(tempLimitYear)*12)-tempEnterToAdjustDate) ;
						}
						if(tempAdjustDate.equals(tempEnterDate) && j==0){
						deprValueI2 = deprValueI2*(tempBuyToAdjustDate);
						}
					
					else if (j==0 && !tempEnterDate.equals(tempAdjustDate)){
						deprValueI2 = deprValueI2;
					}
					else if (j==tempDeprToEnterDate){
						if(TempAdjustLastDate.equals(tempDeprYM)){
							if(tempApportionYear1.equals("")){
								TempAdjustLastValue = Integer.parseInt(getBookValue())-(deprValueI2*(tempBuyToLimitDate-1));
								deprValueI2 = TempAdjustLastValue;
							}
							else{
								TempAdjustLastValue = Integer.parseInt(getBookValue())-(deprValueI2*(tempAdjustToApportionYear1-1));
								deprValueI2 = TempAdjustLastValue;
							}
						}
						else{
						deprValueI2=deprValueI2;
						}
					}
					else{
						deprValueI2=deprValueI2;
						}
				
						rowArray[1]=st._getMoneyFormat(tempBookValue);
						rowArray[2]=st._getMoneyFormat(tempBookValue);
						rowArray[3]=st._getMoneyFormat(tempScrapValue);
			
					    if(Integer.parseInt(tempDeprYM) > Integer.parseInt(tempAdjustDate)){
						if(tempApportionYear1.equals("")){
							rowArray[4]=tempLimitYear;
						}
						else{
							rowArray[4]=tempApportionYear1;
						}
					rowArray[5]=tempBuyDate;
					rowArray[6]=tempEnterDate;
					rowArray[7]=st._getMoneyFormat(String.valueOf(deprValueI2));
					accountValue = Integer.parseInt(tempBookValue)-deprValueI2;
					tempAccountValue = tempAccountValue+accountValue-Integer.parseInt(tempBookValue);
					rowArray[8]=st._getMoneyFormat(String.valueOf(tempAccountValue+Integer.parseInt(tempAddBookValue)-Integer.parseInt(tempReduceBookValue)));
					tempDeprValue = tempDeprValue+deprValueI2;
					rowArray[9]=st._getMoneyFormat(String.valueOf(tempDeprValue));
					    }			
				objList.add(rowArray);				
			}
			
		}
		}catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.closeAll();
		}
		return objList;
	}
	
	
	public DefaultTableModel getResultModel() throws Exception{
		
		UNTCH_Tools ul = new UNTCH_Tools();
	    DefaultTableModel model = new javax.swing.table.DefaultTableModel();
	    Database db = new Database();
		StringTools st = new StringTools();
		Vector rowData = new Vector();
		UNTDP005R obj = this;
		
		int counter=0;
		
		 try{
			    
		    	String[] columns = new String[] {"detail01","detail02","detail03","detail04","detail05","detail06","detail07",
		    			"detail08","detail09","detail10","detail11"};
		    	String tempDeprYM = Common.get(getDeprYM());  //折舊年月
				String tempBuyDate = Common.get(getBuyDate()); //購置日期
				String tempEnterDate = Common.get(getEnterDate()); //入帳日期
				String tempBookValue = Common.get(getBookValue());//原值
				String tempScrapValue = Common.get(getScrapValue());//殘值
				String tempLimitYear = Common.get(getLimitYear());//年限
				String tempApportionYear1 = Common.get(getApportionYear1());//增減值年限
				String tempAddBookValue = Common.get(getAddBookValue());//增值
				String tempReduceBookValue = Common.get(getReduceBookValue());//減值
				String tempAdjustDate = Common.get(getAdjustDate());//增減值年月
				String TempAdjustLastDate="";
				//System.out.println("tempApportionYear1==>"+tempApportionYear1);
				int tempDeprToEnterDate=0;
				int tempBuyToEnterDate=0;
				int tempDeprToBuyDate=0;
				int tempBuyToLimitDate=0;
				int tempEnterToAdjustDate=0;
				int tempBuyToAdjustDate =0;
				int tempAdjustToDeprDate =0;
				int deprValueI=0;
				int deprValueI1=0;
				int deprValueI2=0;
				int TempLastValue=0;
				int TempAdjustLastValue=0;
				int accountValue=0;
				int tempDeprValue=0;
				int TempLastValue2=0;
				int TempAdjustLastValue2=0;
				int accountValue2=0;
				int tempDeprValue2=0;
				int tempAccountValue=Integer.parseInt(getBookValue());
				int tempAccountValue2=Integer.parseInt(getBookValue());
				int tempLimitYearToMonth = Integer.parseInt(tempLimitYear);
				int tempAdjustToApportionYear1 =0;
				
				
				//入帳日期+1到折舊月份有幾個月
				tempDeprToEnterDate = Datetime.BetweenTwoMonth(tempEnterDate,tempDeprYM);
				
				//購置日期到入帳日期有幾個月
				tempBuyToEnterDate = Datetime.BetweenTwoMonth(tempBuyDate,tempEnterDate);
				//購置日期到折舊月份有幾個月
				tempDeprToBuyDate = Datetime.BetweenTwoMonth(tempBuyDate,tempDeprYM);
				//若購置日期=入帳日期，從下一個月開始才提折舊
				if(tempBuyDate.equals(tempEnterDate)){
					tempDeprToEnterDate=tempDeprToEnterDate-1;
				}

				//計算折舊最後一個月日期  購置日期+年限
				String TempLastDate=Datetime.getDateAdd("y", +tempLimitYearToMonth,tempBuyDate+"01" ).substring(0, 5);
				//購置日期到購置日期+年限有幾個月
				tempBuyToLimitDate = Datetime.BetweenTwoMonth(tempBuyDate,TempLastDate);
				
				//勾選增減值checkbox後，所需的變數設定
				if(getAddValue().equals("Y")){
				//入帳日期到增減值年月前共幾個月
				tempEnterToAdjustDate = Datetime.BetweenTwoMonth(tempEnterDate,tempAdjustDate);
				//增減年月到折舊年月共幾個月
				tempAdjustToDeprDate = Datetime.BetweenTwoMonth(tempAdjustDate,tempDeprYM);
				//購置日期到增減值年月前共幾個月
				tempBuyToAdjustDate = Datetime.BetweenTwoMonth(tempBuyDate,tempAdjustDate);
				}		
				if(getAddValue().equals("N")){
					
					for(int i=0; i<=tempDeprToEnterDate; i++){
						Object[] data = new Object[11];
						counter++;
						data[0]=counter;
						//計算每月折舊
						deprValueI = ((Integer.parseInt(tempBookValue)-Integer.parseInt(tempScrapValue))/Integer.parseInt(tempLimitYear))/12;
						if(tempBuyDate.equals(tempEnterDate)){
							data[1]=Datetime.getDateAdd("m", +i+1,tempEnterDate+"01" ).substring(0, 5);
						}
						else {
							data[1]=Datetime.getDateAdd("m", +i,tempEnterDate+"01" ).substring(0, 5);
						}
						
						//購置日期<入帳日期 補提折舊
							if(Integer.parseInt(tempBuyDate)<Integer.parseInt(tempEnterDate) && i==0){
							deprValueI = deprValueI*(tempBuyToEnterDate);
							}
							else if (i==0 && tempBuyDate.equals(tempEnterDate)){
							deprValueI = deprValueI;
							}
							else if (i==tempDeprToEnterDate){
								if(TempLastDate.equals(tempDeprYM)){
									TempLastValue = Integer.parseInt(getBookValue())-(deprValueI*(tempBuyToLimitDate-1));
									deprValueI = TempLastValue;
								}
								else{
								deprValueI=deprValueI;
								}
							}
							else{
								deprValueI=deprValueI;
								}
							data[2]=st._getMoneyFormat(tempBookValue);
							data[3]=st._getMoneyFormat(tempBookValue);
							data[4]=st._getMoneyFormat(tempScrapValue);
							data[5]=tempLimitYear;
							data[6]=tempBuyDate;
							data[7]=tempEnterDate;
							data[8]=st._getMoneyFormat(String.valueOf(deprValueI));
							accountValue = Integer.parseInt(tempBookValue)-deprValueI;
							tempAccountValue = tempAccountValue+accountValue-Integer.parseInt(tempBookValue);
							data[9]=st._getMoneyFormat(String.valueOf(tempAccountValue));
							tempDeprValue = tempDeprValue+deprValueI;
							data[10]=st._getMoneyFormat(String.valueOf(tempDeprValue));
							
				            rowData.addElement(data);
					}
				}
				//需要做增減值計算
				else if(getAddValue().equals("Y") && Integer.parseInt(tempAdjustDate) >= Integer.parseInt(tempEnterDate) ){														
					//跑入帳日期到增減值年月部分
					for(int i=0; i<tempEnterToAdjustDate; i++){
						Object[] data = new Object[11];
						counter++;
						data[0]=counter;
						//計算每月折舊
						deprValueI = ((Integer.parseInt(tempBookValue)-Integer.parseInt(tempScrapValue))/Integer.parseInt(tempLimitYear))/12;
						if(tempBuyDate.equals(tempEnterDate)){
							data[1]=Datetime.getDateAdd("m", +i+1,tempEnterDate+"01" ).substring(0, 5);
						}
						else {
							data[1]=Datetime.getDateAdd("m", +i,tempEnterDate+"01" ).substring(0, 5);
						}
						
						//購置日期<入帳日期 補提折舊
							if(Integer.parseInt(tempBuyDate)<Integer.parseInt(tempEnterDate) && i==0){
							deprValueI = deprValueI*(tempBuyToEnterDate);
							}
							else if (i==0 && tempBuyDate.equals(tempEnterDate)){
							deprValueI = deprValueI;
							}
							else if (i==tempDeprToEnterDate){
								if(TempLastDate.equals(tempDeprYM)){
									TempLastValue = Integer.parseInt(getBookValue())-(deprValueI*(tempBuyToLimitDate-1));
									deprValueI = TempLastValue;
								}
								else{
								deprValueI=deprValueI;
								}
							}
							else{
								deprValueI=deprValueI;
								}
							data[2]=st._getMoneyFormat(tempBookValue);
							data[3]=st._getMoneyFormat(tempBookValue);
							data[4]=st._getMoneyFormat(tempScrapValue);
							data[5]=tempLimitYear;
							data[6]=tempBuyDate;
							data[7]=tempEnterDate;
							data[8]=st._getMoneyFormat(String.valueOf(deprValueI));
							accountValue = Integer.parseInt(tempBookValue)-deprValueI;
							tempAccountValue = tempAccountValue+accountValue-Integer.parseInt(tempBookValue);
							data[9]=st._getMoneyFormat(String.valueOf(tempAccountValue));
							tempDeprValue = tempDeprValue+deprValueI;
							data[10]=st._getMoneyFormat(String.valueOf(tempDeprValue));
							
				            rowData.addElement(data);
					}
				
					//跑增減值年月到折舊年月部分
					for(int j=0; j<=tempAdjustToDeprDate; j++){
						Object[] data = new Object[11];
						counter++;
						data[0]=counter;
						
						if(tempBuyDate.equals(tempAdjustDate)){
							data[1]=Datetime.getDateAdd("m", +j+1,tempAdjustDate+"01" ).substring(0, 5);
							}
							else {
							data[1]=Datetime.getDateAdd("m", +j,tempAdjustDate+"01" ).substring(0, 5);
							}
						int tempApportionYear1ToMonth = Integer.parseInt(""==tempApportionYear1?"0":tempApportionYear1);
						//計算折舊最後一個月日期  購置日期+年限
						TempAdjustLastDate=Datetime.getDateAdd("y", +tempApportionYear1ToMonth,tempAdjustDate+"01" ).substring(0, 5);
						//增減值年月到增減值年月+增減值年限有幾個月
						tempAdjustToApportionYear1 = Datetime.BetweenTwoMonth(tempAdjustDate,TempAdjustLastDate);
						if(tempApportionYear1.equals("")){
							//如果增減值年限為空，年限計算為原年限減入帳年月到增減值年月，deprValueI為先算出原始每月折舊，deprValueI2為原值-殘值+增值-減值-(累計折舊)/(原年限-入帳到增減的月數)
							deprValueI = ((Integer.parseInt(tempBookValue)-Integer.parseInt(tempScrapValue))/Integer.parseInt(tempLimitYear))/12;
							deprValueI2 = (Integer.parseInt(tempBookValue)-Integer.parseInt(tempScrapValue)+Integer.parseInt(tempAddBookValue)-Integer.parseInt(tempReduceBookValue)-(deprValueI*(tempBuyToAdjustDate-1)))/(Integer.parseInt(tempLimitYear)*12-tempEnterToAdjustDate) ;
							
						}
						else{
							//如果增減值年限有值，年限計算為增減值年月+增減值年限再加上原本年限(需要減掉已使用的月數)， deprValueI為先算出原始每月折舊，deprValueI2為原值-殘值+增值-減值-(累計折舊)/(增減值年限+原年限-入帳到增減的月數)
							deprValueI = ((Integer.parseInt(tempBookValue)-Integer.parseInt(tempScrapValue))/Integer.parseInt(tempLimitYear))/12;
							deprValueI2 = (Integer.parseInt(tempBookValue)-Integer.parseInt(tempScrapValue)+Integer.parseInt(tempAddBookValue)-Integer.parseInt(tempReduceBookValue)-(deprValueI*(tempBuyToAdjustDate-1)))/(Integer.parseInt(tempApportionYear1)*12+(Integer.parseInt(tempLimitYear)*12)-tempEnterToAdjustDate) ;
						}
						if(tempAdjustDate.equals(tempEnterDate) && j==0){
						deprValueI2 = deprValueI2*(tempBuyToAdjustDate);
						}
					
					else if (j==0 && !tempEnterDate.equals(tempAdjustDate)){
						deprValueI2 = deprValueI2;
					}
					else if (j==tempDeprToEnterDate){
						if(TempAdjustLastDate.equals(tempDeprYM)){
							if(tempApportionYear1.equals("")){
								TempAdjustLastValue = Integer.parseInt(getBookValue())-(deprValueI2*(tempBuyToLimitDate-1));
								deprValueI2 = TempAdjustLastValue;
							}
							else{
								TempAdjustLastValue = Integer.parseInt(getBookValue())-(deprValueI2*(tempAdjustToApportionYear1-1));
								deprValueI2 = TempAdjustLastValue;
							}
						}
						else{
						deprValueI2=deprValueI2;
						}
					}
					else{
						deprValueI2=deprValueI2;
						}
				
						data[2]=st._getMoneyFormat(tempBookValue);
						data[3]=st._getMoneyFormat(tempBookValue);
						data[4]=st._getMoneyFormat(tempScrapValue);
			
					    if(Integer.parseInt(tempDeprYM) > Integer.parseInt(tempAdjustDate)){
						if(tempApportionYear1.equals("")){
							data[5]=tempLimitYear;
						}
						else{
							data[5]=tempApportionYear1;
						}
						data[6]=tempBuyDate;
						data[7]=tempEnterDate;
						data[8]=st._getMoneyFormat(String.valueOf(deprValueI2));
					accountValue = Integer.parseInt(tempBookValue)-deprValueI2;
					tempAccountValue = tempAccountValue+accountValue-Integer.parseInt(tempBookValue);
					data[9]=st._getMoneyFormat(String.valueOf(tempAccountValue+Integer.parseInt(tempAddBookValue)-Integer.parseInt(tempReduceBookValue)));
					tempDeprValue = tempDeprValue+deprValueI2;
					data[10]=st._getMoneyFormat(String.valueOf(tempDeprValue));
					    }
				
					    rowData.addElement(data);
					}
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