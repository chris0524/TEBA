/*
*<br>程式目的：單位財產資料轉入作業－轉入暫存檔 
*<br>程式代號：untup001f
*<br>撰寫日期：95/07/06
*<br>程式作者：clive.chang
*<br>--------------------------------------------------------
*<br>修改作者　　修改日期　　　修改目的
*<br>--------------------------------------------------------
*/

package unt.up;

import java.io.File;

public class UNTUP001F extends UNTUP000Q{
		
	public boolean doUploadProcess(File s, String upType, String modifier) throws Exception {
		try {
			if (s.exists()) {
				util.excel.ReadExcelFile ex = new util.excel.ReadExcelFile();
				java.util.Iterator it, it1;
				//java.util.Iterator it= ex.getMSSpreadsheetModel(s.getAbsolutePath()).iterator();
				//java.util.Iterator it= ex.getJExcelModel(s.getAbsolutePath(),0,false,67).iterator();
				
				switch(getProcessType(upType)) {
					case 1: //土地
						it= ex.getJExcelModel(s.getAbsolutePath(),0,false,56).iterator();
						TransferLA la = new TransferLA();
						la.setEditID(modifier);
						la.doLandUploadProcess(getEnterOrg(),it);
						setErrorRecordCount(la.getErrorRecordCount());
						setSuccessRecordCount(la.getSuccessRecordCount());
						setErrorMsg(la.getErrorMsg());
						break;
					case 2: //建物
						it= ex.getJExcelModel(s.getAbsolutePath(),0,false,53).iterator();						
						TransferBU bu = new TransferBU();
						bu.setEditID(modifier);
						bu.doBuildingUploadProcess(getEnterOrg(),it);
						setErrorRecordCount(bu.getErrorRecordCount());
						setSuccessRecordCount(bu.getSuccessRecordCount());
						setErrorMsg(bu.getErrorMsg());						
						break;
					case 3: //土地改良物
						it= ex.getJExcelModel(s.getAbsolutePath(),0,false,39).iterator();						
						TransferRF rf = new TransferRF();
						rf.setEditID(modifier);
						rf.doRFUploadProcess(getEnterOrg(),it);
						setErrorRecordCount(rf.getErrorRecordCount());
						setSuccessRecordCount(rf.getSuccessRecordCount());
						setErrorMsg(rf.getErrorMsg());						
						break;
					case 4: //動產 						
						it= ex.getJExcelModel(s.getAbsolutePath(),0,false,67).iterator();						
						TransferMP mp = new TransferMP();
						mp.setEditID(modifier);
						mp.doMoveableUploadProcess(getEnterOrg(), it);
						setErrorRecordCount(mp.getErrorRecordCount());
						setSuccessRecordCount(mp.getSuccessRecordCount());
						setErrorMsg(mp.getErrorMsg());
						break;
					case 5: //有價證券
						it= ex.getJExcelModel(s.getAbsolutePath(),0,false,29).iterator();
						it1= ex.getJExcelModel(s.getAbsolutePath(),1,false,17).iterator();					
						TransferVP vp = new TransferVP();
						vp.setEditDate(modifier);						
						vp.doVPUploadProcess(enterOrg, it, it1);
						setErrorRecordCount(vp.getErrorRecordCount());
						setSuccessRecordCount(vp.getSuccessRecordCount());
						setErrorRecordCount(vp.getDtlErrorRecordCount());
						setSuccessRecordCount(vp.getDtlSuccessRecordCount());
						setErrorMsg(vp.getErrorMsg());
						break;					
					case 6://權利 
						it= ex.getJExcelModel(s.getAbsolutePath(),0,false,36).iterator();
						it1= ex.getJExcelModel(s.getAbsolutePath(),1,false,26).iterator();					
						TransferRT rt = new TransferRT();
						rt.setEditDate(modifier);						
						rt.doRTUploadProcess(enterOrg, it, it1);
						setErrorRecordCount(rt.getErrorRecordCount());
						setSuccessRecordCount(rt.getSuccessRecordCount());
						setDtlErrorRecordCount(rt.getDtlErrorRecordCount());
						setDtlSuccessRecordCount(rt.getDtlSuccessRecordCount());
						setErrorMsg(rt.getErrorMsg());						
						break;
				}
				return true;
			} else {				
				return false;
			}			
		} catch(Exception e) {
			e.printStackTrace();
			setErrorMsg("發生未預期的錯誤，請重新執行，若問題持續，請洽系統管理者或承辦人!");			
			return false;
		}		
	}

	public boolean doUploadProcessBak2(File s, String upType) throws Exception {
		try {
			if (s.exists()) {
				return true;
			} else {				
				return false;
			}
		} catch(Exception e) {
			e.printStackTrace();
			return false;
		}
	}	
    
	
	
}
