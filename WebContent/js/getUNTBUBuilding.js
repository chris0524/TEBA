//*********************************************
//函數功能：找出UNTBU_Building資料表裡的propertyNo,propertyName,serialNo
//參　　數：strType, PN=以財產編號做Lookup, 其它以土地標示代碼做Lookup
//enterOrg,ownership,signNo1,signNo2,signNo3,signNo4,signNo5
//*********************************************
function getUNTBUBuilding(strType, dataState, verify, closing, proofVerify){
	var alertStr = "";
	var i = 0, j=0;
	var signNo = "";
	var sFlag = false;
	alertStr += checkEmpty(form1.enterOrg,"入帳機關");
	alertStr += checkEmpty(form1.ownership,"權屬");
	if (strType=="PN") {
		alertStr += checkEmpty(form1.propertyNo,"財產編號");
		alertStr += checkEmpty(form1.serialNo,"財產分號");
		if (form1.propertyNo.value=="" || form1.serialNo.value=="") {
			form1.propertyNo.style.backgroundColor="";
			form1.serialNo.style.backgroundColor="";	
		}
	} else { 
		if ((form1.signNo1.value=="")||(form1.signNo2.value=="")||(form1.signNo3.value=="")||(form1.signNo4.value=="")||(form1.signNo5.value=="")){
			/**
			form1.signNo1.style.backgroundColor=errorbg;
			form1.signNo2.style.backgroundColor=errorbg;
			form1.signNo3.style.backgroundColor=errorbg;
			form1.signNo4.style.backgroundColor=errorbg;
			form1.signNo5.style.backgroundColor=errorbg;
			**/
			alertStr += "土地標示代碼不得為空白!\n";
		}
	}
		
	if(alertStr.length!=0){
	/**
		if (isObj(form1.propertyNo)) { form1.propertyNo.value=""; }		
		if (isObj(form1.propertyName)) { form1.propertyName.value=""; }
		if (isObj(form1.serialNo)) { form1.serialNo.value=""; }	
	**/
		return false;
	} else {	
		var enterOrg = form1.enterOrg.value;
		var ownership=form1.ownership.value;
		var sNo1=form1.signNo1.value;
		var sNo2=form1.signNo2.value;
		var sNo3=form1.signNo3.value;
		var sNo4=form1.signNo4.value;
		var sNo5=form1.signNo5.value;	
		var sPropertyNo = form1.propertyNo.value;
		var sPropertyName;
		var sSerialNo = form1.serialNo.value;
		var sSignNo = "";
		var queryValue = "";	
		
		if (isObj(form1.propertyNoName)) { sPropertyName = form1.propertyNoName.value; }
		if (isObj(form1.propertyNoName)) { sPropertyName = form1.propertyNoName.value; }		
		
		if (strType=="PN") {		
			queryValue = "enterOrg="+enterOrg+"&ownership="+ownership+"&sType=PN&propertyNo="+sPropertyNo+"&propertyNoName="+sPropertyName+"&serialNo="+sSerialNo;
		} else {
			queryValue = "enterOrg="+enterOrg+"&ownership="+ownership+"&sType=SN&sNo1="+sNo1+"&sNo2="+sNo2+"&sNo3="+sNo3+"&sNo4="+sNo4+"&sNo5="+sNo5;
		}
		if (parse(dataState).length>0) queryValue += "&dataState=" + dataState;
		if (parse(verify).length>0) queryValue += "&verify=" + verify;
		if (parse(closing).length>0) queryValue += "&closing" + closing;
		if (parse(proofVerify).length>0) queryValue += "&proofVerify" + proofVerify;
		
		//傳送一個亂數資料,防止瀏灠器由快取直接取得資料導至資料錯誤
		var randomnumber=Math.floor(Math.random()*1000);
		queryValue += "&randomnumber="+randomnumber;
		
		var xmlDoc=document.createElement("xml");		
		xmlDoc.async=false;			
		if(xmlDoc.load("../../home/xmlUntbuBuilding.jsp?"+queryValue)){		
			for(i=0; i<xmlDoc.documentElement.childNodes.length; i++){		
				sFlag = true;
				sSignNo = xmlDoc.documentElement.childNodes.item(i).getAttribute("signNo");			
				if (isObj(form1.signNo1)) {
					for (j = 0; j < form1.signNo1.options.length; j++) {						
						if (form1.signNo1.options[j].value==sSignNo.substring(0,1)+"000000") {
							form1.signNo1.options[j].selected=true;							
						}
					}
				}
				if (isObj(form1.signNo2)) {
					changeSignNo1('signNo1','signNo2','signNo3',sSignNo.substring(0,3)+'0000');					
				}
				if (isObj(form1.signNo3)) {
					changeSignNo2('signNo1','signNo2','signNo3',sSignNo.substring(0,7))						
				}		
				if (isObj(form1.signNo4)) {		
					form1.signNo4.value=sSignNo.substring(7,12);
				}										
				if (isObj(form1.signNo5)) {
					form1.signNo5.value=sSignNo.substring(12,15);
				}
														
/**				if (isObj(form1.enterOrg)) {
					form1.enterOrg.value=xmlDoc.documentElement.childNodes.item(i).getAttribute("enterOrg");
				}
				if (isObj(form1.enterOrgName)) {
					form1.enterOrgName.value=xmlDoc.documentElement.childNodes.item(i).getAttribute("enterOrgName");
				}			
				if (isObj(form1.ownership)) {
					form1.ownership.value=xmlDoc.documentElement.childNodes.item(i).getAttribute("ownership");
				}
				if (isObj(form1.caseNo)) {
					form1.caseNo.value=xmlDoc.documentElement.childNodes.item(i).getAttribute("caseNo");
				}
**/				
				if (isObj(form1.propertyNo)) {
					form1.propertyNo.value=xmlDoc.documentElement.childNodes.item(i).getAttribute("propertyNo");
				}
				if (isObj(form1.propertyNoName)) {
					form1.propertyNoName.value=xmlDoc.documentElement.childNodes.item(i).getAttribute("propertyNoName");
				}			
				if (isObj(form1.serialNo)) {
					form1.serialNo.value=xmlDoc.documentElement.childNodes.item(i).getAttribute("serialNo");
				}
				if (isObj(form1.signNo)) {
					form1.signNo.value=xmlDoc.documentElement.childNodes.item(i).getAttribute("signNo");
				}
				if (isObj(form1.signName)) {
					form1.signName.value=xmlDoc.documentElement.childNodes.item(i).getAttribute("signName");
				}
				if (isObj(form1.propertyName1)) {
					form1.propertyName1.value=xmlDoc.documentElement.childNodes.item(i).getAttribute("propertyName1");
				}
				if (isObj(form1.dataState)) {
					form1.dataState.value=xmlDoc.documentElement.childNodes.item(i).getAttribute("dataState");
				}
			
				if (isObj(form1.otherLimitYear)) {
					form1.otherLimitYear.value=xmlDoc.documentElement.childNodes.item(i).getAttribute("otherLimitYear");
				}	
				/**											
				if (isObj(form1.doorPlate1)) {
					form1.doorPlate1.value=xmlDoc.documentElement.childNodes.item(i).getAttribute("doorPlate1");
				}
				if (isObj(form1.doorPlate2)) {
					form1.doorPlate2.value=xmlDoc.documentElement.childNodes.item(i).getAttribute("doorPlate2");
				}
				if (isObj(form1.doorPlate3)) {
					form1.doorPlate3.value=xmlDoc.documentElement.childNodes.item(i).getAttribute("doorPlate3");
				}
				if (isObj(form1.doorPlate4)) {
					form1.doorPlate4.value=xmlDoc.documentElement.childNodes.item(i).getAttribute("doorPlate4");
				}
				**/
				if (isObj(form1.doorPlate1)) {
					for (j = 0; j < form1.doorPlate1.options.length; j++) {						
						if (form1.doorPlate1.options[j].value==xmlDoc.documentElement.childNodes.item(i).getAttribute("doorPlate1")) {
							form1.doorPlate1.options[j].selected=true;							
						}
					}
					changeAddr1('doorPlate1','doorPlate2','doorPlate3',xmlDoc.documentElement.childNodes.item(i).getAttribute("doorPlate2"));
					changeAddr2('doorPlate1','doorPlate2','doorPlate3',xmlDoc.documentElement.childNodes.item(i).getAttribute("doorPlate3"));
					form1.doorPlate4.value=xmlDoc.documentElement.childNodes.item(i).getAttribute("doorPlate4");					
				}					
				if (isObj(form1.doorPlateName)) {
					form1.doorPlateName.value=xmlDoc.documentElement.childNodes.item(i).getAttribute("doorPlateName");
				}				
				if (isObj(form1.buildStyle)) {
					for (j = 0; j < form1.buildStyle.options.length; j++) {						
						if (form1.buildStyle.options[j].value==xmlDoc.documentElement.childNodes.item(i).getAttribute("buildStyle")) {
							form1.buildStyle.options[j].selected=true;							
						}
					}				
					//form1.buildStyle.value=xmlDoc.documentElement.childNodes.item(i).getAttribute("buildStyle");
				}
				if (isObj(form1.buildStyleName)) {
					form1.buildStyleName.value=xmlDoc.documentElement.childNodes.item(i).getAttribute("buildStyleName");
				}				
/**				if (isObj(form1.cause)) {
					form1.cause.value=xmlDoc.documentElement.childNodes.item(i).getAttribute("cause");
				}
				if (isObj(form1.cause1)) {
					form1.cause1.value=xmlDoc.documentElement.childNodes.item(i).getAttribute("cause1");
				}
**/
				if (isObj(form1.enterDate)) {
					form1.enterDate.value=xmlDoc.documentElement.childNodes.item(i).getAttribute("enterDate");
				}
				if (isObj(form1.reduceDate)) {
					form1.reduceDate.value=xmlDoc.documentElement.childNodes.item(i).getAttribute("reduceDate");
				}
				if (isObj(form1.reduceCause)) {
					form1.reduceCause.value=xmlDoc.documentElement.childNodes.item(i).getAttribute("reduceCause");
				}
				if (isObj(form1.reduceCause1)) {
					form1.reduceCause1.value=xmlDoc.documentElement.childNodes.item(i).getAttribute("reduceCause1");
				}
				if (isObj(form1.closing)) {
					form1.closing.value=xmlDoc.documentElement.childNodes.item(i).getAttribute("closing");
				}
				if (isObj(form1.propertyKind)) {
					form1.propertyKind.value=xmlDoc.documentElement.childNodes.item(i).getAttribute("propertyKind");
				}
				if (isObj(form1.fundType)) {
					form1.fundType.value=xmlDoc.documentElement.childNodes.item(i).getAttribute("fundType");
				}
				if (isObj(form1.valuable)) {
					form1.valuable.value=xmlDoc.documentElement.childNodes.item(i).getAttribute("valuable");
				}
				if (isObj(form1.taxCredit)) {
					form1.taxCredit.value=xmlDoc.documentElement.childNodes.item(i).getAttribute("taxCredit");
				}
				if (isObj(form1.originalCArea)) {
					form1.originalCArea.value=xmlDoc.documentElement.childNodes.item(i).getAttribute("originalCArea");
				}
				if (isObj(form1.originalSArea)) {
					form1.originalSArea.value=xmlDoc.documentElement.childNodes.item(i).getAttribute("originalSArea");
				}
				if (isObj(form1.originalArea)) {
					form1.originalArea.value=xmlDoc.documentElement.childNodes.item(i).getAttribute("originalArea");
				}
				if (isObj(form1.originalHoldNume)) {
					form1.originalHoldNume.value=xmlDoc.documentElement.childNodes.item(i).getAttribute("originalHoldNume");
				}
				if (isObj(form1.originalHoldDeno)) {
					form1.originalHoldDeno.value=xmlDoc.documentElement.childNodes.item(i).getAttribute("originalHoldDeno");
				}
				if (isObj(form1.originalHoldArea)) {
					form1.originalHoldArea.value=xmlDoc.documentElement.childNodes.item(i).getAttribute("originalHoldArea");
				}
				if (isObj(form1.CArea)) {
					form1.CArea.value=xmlDoc.documentElement.childNodes.item(i).getAttribute("CArea");
				}
				if (isObj(form1.SArea)) {
					form1.SArea.value=xmlDoc.documentElement.childNodes.item(i).getAttribute("SArea");
				}
				if (isObj(form1.area)) {
					form1.area.value=xmlDoc.documentElement.childNodes.item(i).getAttribute("area");
				}
                       
				if (isObj(form1.holdNume)) {
					form1.holdNume.value=xmlDoc.documentElement.childNodes.item(i).getAttribute("holdNume");
				}
				if (isObj(form1.holdDeno)) {
					form1.holdDeno.value=xmlDoc.documentElement.childNodes.item(i).getAttribute("holdDeno");
				}
				if (isObj(form1.holdArea)) {
					form1.holdArea.value=xmlDoc.documentElement.childNodes.item(i).getAttribute("holdArea");
				}
				if (isObj(form1.originalBV)) {
					form1.originalBV.value=xmlDoc.documentElement.childNodes.item(i).getAttribute("originalBV");
				}
				if (isObj(form1.originalNote)) {
					form1.originalNote.value=xmlDoc.documentElement.childNodes.item(i).getAttribute("originalNote");
				}
				if (isObj(form1.accountingTitle)) {
					form1.accountingTitle.value=xmlDoc.documentElement.childNodes.item(i).getAttribute("accountingTitle");
				}
				if (isObj(form1.bookValue)) {
					form1.bookValue.value=xmlDoc.documentElement.childNodes.item(i).getAttribute("bookValue");
				}
				if (isObj(form1.fundsSource)) {
					form1.fundsSource.value=xmlDoc.documentElement.childNodes.item(i).getAttribute("fundsSource");
				}
				if (isObj(form1.ownershipDate)) {
					form1.ownershipDate.value=xmlDoc.documentElement.childNodes.item(i).getAttribute("ownershipDate");
				}
				if (isObj(form1.ownershipCause)) {
					form1.ownershipCause.value=xmlDoc.documentElement.childNodes.item(i).getAttribute("ownershipCause");
				}
				if (isObj(form1.nonProof)) {
					form1.nonProof.value=xmlDoc.documentElement.childNodes.item(i).getAttribute("nonProof");
				}
				if (isObj(form1.proofDoc)) {
					form1.proofDoc.value=xmlDoc.documentElement.childNodes.item(i).getAttribute("proofDoc");
				}
				if (isObj(form1.proofVerify)) {
					form1.proofVerify.value=xmlDoc.documentElement.childNodes.item(i).getAttribute("proofVerify");
				}
				if (isObj(form1.buildDate)) {
					form1.buildDate.value=xmlDoc.documentElement.childNodes.item(i).getAttribute("buildDate");
				}
				if (isObj(form1.floor1)) {
					form1.floor1.value=xmlDoc.documentElement.childNodes.item(i).getAttribute("floor1");
				}
				if (isObj(form1.floor1Name)) {
					form1.floor1Name.value=xmlDoc.documentElement.childNodes.item(i).getAttribute("floor1Name");
				}				
				if (isObj(form1.floor2)) {
					form1.floor2.value=xmlDoc.documentElement.childNodes.item(i).getAttribute("floor2");
				}
				if (isObj(form1.floor2Name)) {
					form1.floor2Name.value=xmlDoc.documentElement.childNodes.item(i).getAttribute("floor2Name");
				}				
				if (isObj(form1.stuff)) {
					form1.stuff.value=xmlDoc.documentElement.childNodes.item(i).getAttribute("stuff");
				}
				if (isObj(form1.ownershipNote)) {
					form1.ownershipNote.value=xmlDoc.documentElement.childNodes.item(i).getAttribute("ownershipNote");
				}
				if (isObj(form1.sourceKind)) {
					form1.sourceKind.value=xmlDoc.documentElement.childNodes.item(i).getAttribute("sourceKind");
				}
				if (isObj(form1.sourceDate)) {
					form1.sourceDate.value=xmlDoc.documentElement.childNodes.item(i).getAttribute("sourceDate");
				}
				if (isObj(form1.sourceDoc)) {
					form1.sourceDoc.value=xmlDoc.documentElement.childNodes.item(i).getAttribute("sourceDoc");
				}
				if (isObj(form1.manageOrg)) {
					form1.manageOrg.value=xmlDoc.documentElement.childNodes.item(i).getAttribute("manageOrg");
				}
				if (isObj(form1.damageDate)) {
					form1.damageDate.value=xmlDoc.documentElement.childNodes.item(i).getAttribute("damageDate");
				}
				if (isObj(form1.damageExpire)) {
					form1.damageExpire.value=xmlDoc.documentElement.childNodes.item(i).getAttribute("damageExpire");
				}
				if (isObj(form1.damageMark)) {
					form1.damageMark.value=xmlDoc.documentElement.childNodes.item(i).getAttribute("damageMark");
				}
				if (isObj(form1.deprMethod)) {
					form1.deprMethod.value=xmlDoc.documentElement.childNodes.item(i).getAttribute("deprMethod");
				}
				if (isObj(form1.scrapValue)) {
					form1.scrapValue.value=xmlDoc.documentElement.childNodes.item(i).getAttribute("scrapValue");
				}
				if (isObj(form1.deprAmount)) {
					form1.deprAmount.value=xmlDoc.documentElement.childNodes.item(i).getAttribute("deprAmount");
				}
				if (isObj(form1.apportionYear)) {
					form1.apportionYear.value=xmlDoc.documentElement.childNodes.item(i).getAttribute("apportionYear");
				}
				if (isObj(form1.monthDepr)) {
					form1.monthDepr.value=xmlDoc.documentElement.childNodes.item(i).getAttribute("monthDepr");
				}
				if (isObj(form1.useEndYM)) {
					form1.useEndYM.value=xmlDoc.documentElement.childNodes.item(i).getAttribute("useEndYM");
				}
				if (isObj(form1.apportionEndYM)) {
					form1.apportionEndYM.value=xmlDoc.documentElement.childNodes.item(i).getAttribute("apportionEndYM");
				}
				if (isObj(form1.accumDeprYM)) {
					form1.accumDeprYM.value=xmlDoc.documentElement.childNodes.item(i).getAttribute("accumDeprYM");
				}
				if (isObj(form1.accumDepr)) {
					form1.accumDepr.value=xmlDoc.documentElement.childNodes.item(i).getAttribute("accumDepr");
				}
				if (isObj(form1.permitReduceDate)) {
					form1.permitReduceDate.value=xmlDoc.documentElement.childNodes.item(i).getAttribute("permitReduceDate");
				}
				if (isObj(form1.useState)) {
					form1.useState.value=xmlDoc.documentElement.childNodes.item(i).getAttribute("useState");
				}
				if (isObj(form1.useStateName)) {
					form1.useStateName.value=xmlDoc.documentElement.childNodes.item(i).getAttribute("useStateName");
				}				
				if (isObj(form1.proceedDateS)) {
					form1.proceedDateS.value=xmlDoc.documentElement.childNodes.item(i).getAttribute("proceedDateS");
				}
				if (isObj(form1.proceedDateE)) {
					form1.proceedDateE.value=xmlDoc.documentElement.childNodes.item(i).getAttribute("proceedDateE");
				}
				if (isObj(form1.proceedType)) {
					form1.proceedType.value=xmlDoc.documentElement.childNodes.item(i).getAttribute("proceedType");
				}
				if (isObj(form1.proceedTypeName)) {
					form1.proceedTypeName.value=xmlDoc.documentElement.childNodes.item(i).getAttribute("proceedTypeName");
				}				
				if (isObj(form1.appraiseDate)) {
					form1.appraiseDate.value=xmlDoc.documentElement.childNodes.item(i).getAttribute("appraiseDate");
				}
				if (isObj(form1.notes)) {
					form1.notes.value=xmlDoc.documentElement.childNodes.item(i).getAttribute("notes");
				}
				if (isObj(form1.oldPropertyNo)) {
					form1.oldPropertyNo.value=xmlDoc.documentElement.childNodes.item(i).getAttribute("oldPropertyNo");
				}
				if (isObj(form1.oldSerialNo)) {
					form1.oldSerialNo.value=xmlDoc.documentElement.childNodes.item(i).getAttribute("oldSerialNo");
				}
				if (isObj(form1.check)) {
					form1.check.value=xmlDoc.documentElement.childNodes.item(i).getAttribute("check");
				}
				if (isObj(form1.material)) {
					form1.material.value=xmlDoc.documentElement.childNodes.item(i).getAttribute("material");
				}	
				if (isObj(form1.propertyUnit)) {
					form1.propertyUnit.value=xmlDoc.documentElement.childNodes.item(i).getAttribute("propertyUnit");
				}			
				if (isObj(form1.limitYear)) {
					form1.limitYear.value=xmlDoc.documentElement.childNodes.item(i).getAttribute("limitYear");
				}


			}
			//if (!sFlag) {
			//	if (isObj(form1.propertyNo)) { form1.propertyNo.value=""; }		
			//	if (isObj(form1.propertyNoName)) { form1.propertyNoName.value=""; }
			//	if (isObj(form1.serialNo)) { form1.serialNo.value=""; }			
			//	return false;	
			//}
		}else{
/**			alert('Cant Load It!'); **/
			return false;	
		}
	}	
}