
function getPropertyNo(strType, dataState, verify, closing, proofVerify){

	var alertStr = "", chkStr="";
	var i = 0, j=0;
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
			alertStr += "土地標示代碼不得為空白!\n";
		}
	}
	
	if(alertStr.length!=0){
	
		return false;
	} else {
		
		var enterOrg = form1.enterOrg.value;
		var ownership=form1.ownership.value;
	
		var sPropertyNo = form1.propertyNo.value;
		var sPropertyName;
		var sSerialNo = form1.serialNo.value;

		if (isObj(form1.propertyName)) { sPropertyName = form1.propertyName.value; }						
		if (isObj(form1.propertyNoName)) { sPropertyName = form1.propertyNoName.value; }
		
		var sSignNo = "";
		var queryValue = "";
		if (strType=="PN") {		
			queryValue = "enterOrg="+enterOrg+"&ownership="+ownership+"&sType=PN&propertyNo="+sPropertyNo+"&propertyNoName="+sPropertyName+"&serialNo="+sSerialNo;
		} else {
			queryValue = "enterOrg="+enterOrg+"&ownership="+ownership+"&sType=SN&sNo1="+sNo1+"&sNo2="+sNo2+"&sNo3="+sNo3+"&sNo4="+sNo4+"&sNo5="+sNo5;
		}
		if (dataState=="1"||dataState=="2") queryValue += "&dataState=" + dataState;
		if (verify=="Y"||verify=="N") queryValue += "&verify=" + verify;
		if (parse(""+closing).length>0) queryValue += "&closing" + closing;
		if (parse(""+proofVerify).length>0) queryValue += "&proofVerify" + proofVerify;
		
		//傳送一個亂數資料,防止瀏灠器由快取直接取得資料導至資料錯誤
		var randomnumber=Math.floor(Math.random()*1000);
		queryValue += "&randomnumber="+randomnumber;

		var xmlDoc=document.createElement("xml");		
		xmlDoc.async=false;
		
		if(xmlDoc.load("xmlUntch.jsp?"+queryValue)){
			for(i=0; i<xmlDoc.documentElement.childNodes.length; i++){
										
				var propertyNo = xmlDoc.documentElement.childNodes.item(i).getAttribute("propertyNo");
				form1.propertyNo.value=propertyNo;
				form1.serialNo.value=xmlDoc.documentElement.childNodes.item(i).getAttribute("serialNo");
				if (isObj(form1.propertyName)) {
					form1.propertyName.value=xmlDoc.documentElement.childNodes.item(i).getAttribute("propertyName");
				}				
				if (isObj(form1.propertyNoName)) {
					form1.propertyNoName.value=xmlDoc.documentElement.childNodes.item(i).getAttribute("propertyName");
				}
				
				sSignNo = xmlDoc.documentElement.childNodes.item(i).getAttribute("signNo");

				if(propertyNo.substring(0,3) == '111'){
					
				}else if(propertyNo.substring(0,1) == '1'){
					if (isObj(form1.laSignNo1)) {
						for (j = 0; j < form1.laSignNo1.options.length; j++) {						
							if (form1.laSignNo1.options[j].value==sSignNo.substring(0,1)+"000000") {
								form1.laSignNo1.options[j].selected=true;							
							}
						}
					}
					if (isObj(form1.laSignNo2)) {
						changeSignNo1('laSignNo1','laSignNo2','laSignNo3',sSignNo.substring(0,3)+'0000');					
					}
					if (isObj(form1.laSignNo3)) {
						changeSignNo2('laSignNo1','laSignNo2','laSignNo3',sSignNo.substring(0,7))						
					}		
					if (isObj(form1.laSignNo4)) {		
						form1.laSignNo4.value=sSignNo.substring(7,11);
					}										
					if (isObj(form1.laSignNo5)) {
						form1.laSignNo5.value=sSignNo.substring(11,15);
					}		
				}else if(propertyNo.substring(0,1) == '2'){
					if (isObj(form1.buSignNo1)) {
						for (j = 0; j < form1.buSignNo1.options.length; j++) {						
							if (form1.buSignNo1.options[j].value==sSignNo.substring(0,1)+"000000") {
								form1.buSignNo1.options[j].selected=true;							
							}
						}
					}
					if (isObj(form1.buSignNo2)) {
						changeSignNo1('buSignNo1','buSignNo2','buSignNo3',sSignNo.substring(0,3)+'0000');					
					}
					if (isObj(form1.buSignNo3)) {
						changeSignNo2('buSignNo1','buSignNo2','buSignNo3',sSignNo.substring(0,7))						
					}		
					if (isObj(form1.buSignNo4)) {		
						form1.buSignNo4.value=sSignNo.substring(7,12);
					}										
					if (isObj(form1.buSignNo5)) {
						form1.buSignNo5.value=sSignNo.substring(12,15);
					}		
				}

				if (isObj(form1.propertyName1)) {
					form1.propertyName1.value=xmlDoc.documentElement.childNodes.item(i).getAttribute("propertyName1");
				}
				if (isObj(form1.dataState))	 {
					form1.dataState.value=xmlDoc.documentElement.childNodes.item(i).getAttribute("dataState");
				}
				if (isObj(form1.dataStateName))	 {
					form1.dataStateName.value=xmlDoc.documentElement.childNodes.item(i).getAttribute("dataStateName");
				}				
				if (isObj(form1.oldPropertyNo))	 {
					form1.oldPropertyNo.value=xmlDoc.documentElement.childNodes.item(i).getAttribute("oldPropertyNo");
				}
				if (isObj(form1.oldSerialNo))	 {
					form1.oldSerialNo.value=xmlDoc.documentElement.childNodes.item(i).getAttribute("oldSerialNo");
				}												
				if (isObj(form1.lotNo))	 {
					form1.lotNo.value=xmlDoc.documentElement.childNodes.item(i).getAttribute("lotNo");
				}
				if (isObj(form1.propertyKind))	 {
					form1.propertyKind.value=xmlDoc.documentElement.childNodes.item(i).getAttribute("propertyKind");
				}
				if (isObj(form1.propertykindName))	 {
					form1.propertykindName.value=xmlDoc.documentElement.childNodes.item(i).getAttribute("propertykindName");
				}												
				if (isObj(form1.fundType))	 {
					form1.fundType.value=xmlDoc.documentElement.childNodes.item(i).getAttribute("fundType");
				}
				if (isObj(form1.fundTypeName))	 {
					form1.fundTypeName.value=xmlDoc.documentElement.childNodes.item(i).getAttribute("fundTypeName");
				}												
				if (isObj(form1.valuable))	 {
					form1.valuable.value=xmlDoc.documentElement.childNodes.item(i).getAttribute("valuable");
				}												
				if (isObj(form1.taxCredit))	 {
					form1.taxCredit.value=xmlDoc.documentElement.childNodes.item(i).getAttribute("taxCredit");
				}												
				if (isObj(form1.grass))	 {
					form1.grass.value=xmlDoc.documentElement.childNodes.item(i).getAttribute("grass");
				}												
				if (isObj(form1.grassName))	 {
					form1.grassName.value=xmlDoc.documentElement.childNodes.item(i).getAttribute("grassName");
				}											
				if (isObj(form1.originalBV))	 {
					form1.originalBV.value=xmlDoc.documentElement.childNodes.item(i).getAttribute("originalBV");
				}
				//���n
				if (isObj(form1.area))	 {
					form1.area.value=xmlDoc.documentElement.childNodes.item(i).getAttribute("area");
				}
				if (isObj(form1.holdNume))	 {
					form1.holdNume.value=xmlDoc.documentElement.childNodes.item(i).getAttribute("holdNume");
				}	
				if (isObj(form1.holdDeno))	 {
					form1.holdDeno.value=xmlDoc.documentElement.childNodes.item(i).getAttribute("holdDeno");
				}
				if (isObj(form1.holdArea))	 {
					form1.holdArea.value=xmlDoc.documentElement.childNodes.item(i).getAttribute("holdArea");
				}
				
				if (isObj(form1.buildDate)) {
					form1.buildDate.value=xmlDoc.documentElement.childNodes.item(i).getAttribute("buildDate");
				}

				if (isObj(form1.useYear)) {
					form1.useYear.value = parseInt((getDateDiff("m", form1.buildDate , form1.getToday)) / 12) ;						
				}
				if (isObj(form1.permitReduceDate)) {
					form1.permitReduceDate.value=xmlDoc.documentElement.childNodes.item(i).getAttribute("permitReduceDate");						
				}
				if (isObj(form1.enterDate)) {
					form1.enterDate.value=xmlDoc.documentElement.childNodes.item(i).getAttribute("enterDate");						
				}
				if (isObj(form1.buyDate)) {
					form1.buyDate.value=xmlDoc.documentElement.childNodes.item(i).getAttribute("buyDate");						
				}
				if (isObj(form1.oldBookAmount)) {
					form1.oldBookAmount.value=xmlDoc.documentElement.childNodes.item(i).getAttribute("oldBookAmount");						
				}
				if (isObj(form1.newBookAmount)) {
					form1.newBookAmount.value=xmlDoc.documentElement.childNodes.item(i).getAttribute("newBookAmount");						
				}
				
				if(propertyNo.substring(0,3) == '111'){

					if (isObj(form1.oldMeasure)) {
						form1.oldMeasure.value=xmlDoc.documentElement.childNodes.item(i).getAttribute("measure");
					}
					if (isObj(form1.newMeasure)) {
						form1.newMeasure.value=xmlDoc.documentElement.childNodes.item(i).getAttribute("measure");
					}
					if (isObj(form1.measure)) {
						form1.measure.value=xmlDoc.documentElement.childNodes.item(i).getAttribute("measure");
					}
					
					
				}else if(propertyNo.substring(0,1) == '1'){
					if (isObj(form1.laArea))	 {
						form1.laArea.value=xmlDoc.documentElement.childNodes.item(i).getAttribute("area");
					}
					if (isObj(form1.laHoldNume))	 {
						form1.laHoldNume.value=xmlDoc.documentElement.childNodes.item(i).getAttribute("holdNume");
					}	
					if (isObj(form1.laHoldDeno))	 {
						form1.laHoldDeno.value=xmlDoc.documentElement.childNodes.item(i).getAttribute("holdDeno");
					}
					if (isObj(form1.laHoldArea))	 {
						form1.laHoldArea.value=xmlDoc.documentElement.childNodes.item(i).getAttribute("holdArea");
					}
					
					if (isObj(form1.oldLaArea))	 {
						form1.oldLaArea.value=xmlDoc.documentElement.childNodes.item(i).getAttribute("area");
					}
					if (isObj(form1.oldLaHoldNume))	 {
						form1.oldLaHoldNume.value=xmlDoc.documentElement.childNodes.item(i).getAttribute("holdNume");
					}	
					if (isObj(form1.oldLaHoldDeno))	 {
						form1.oldLaHoldDeno.value=xmlDoc.documentElement.childNodes.item(i).getAttribute("holdDeno");
					}
					if (isObj(form1.oldLaHoldArea))	 {
						form1.oldLaHoldArea.value=xmlDoc.documentElement.childNodes.item(i).getAttribute("holdArea");
					}
					
					if (isObj(form1.newLaArea))	 {
						form1.newLaArea.value=xmlDoc.documentElement.childNodes.item(i).getAttribute("area");
					}
					if (isObj(form1.newLaHoldNume))	 {
						form1.newLaHoldNume.value=xmlDoc.documentElement.childNodes.item(i).getAttribute("holdNume");
					}	
					if (isObj(form1.newLaHoldDeno))	 {
						form1.newLaHoldDeno.value=xmlDoc.documentElement.childNodes.item(i).getAttribute("holdDeno");
					}
					if (isObj(form1.newLaHoldArea))	 {
						form1.newLaHoldArea.value=xmlDoc.documentElement.childNodes.item(i).getAttribute("holdArea");
					}
				}else if(propertyNo.substring(0,1) == '2'){
					if (isObj(form1.buCArea)) {						
						form1.buCArea.value=xmlDoc.documentElement.childNodes.item(i).getAttribute("carea");				
					}
					if (isObj(form1.buSArea)) {
						form1.buSArea.value=xmlDoc.documentElement.childNodes.item(i).getAttribute("sarea");
					}
					if (isObj(form1.buArea))	 {
						form1.buArea.value=xmlDoc.documentElement.childNodes.item(i).getAttribute("area");
					}
					if (isObj(form1.buHoldNume))	 {
						form1.buHoldNume.value=xmlDoc.documentElement.childNodes.item(i).getAttribute("holdNume");
					}	
					if (isObj(form1.buHoldDeno))	 {
						form1.buHoldDeno.value=xmlDoc.documentElement.childNodes.item(i).getAttribute("holdDeno");
					}
					if (isObj(form1.buHoldArea))	 {
						form1.buHoldArea.value=xmlDoc.documentElement.childNodes.item(i).getAttribute("holdArea");
					}
					
					if (isObj(form1.oldBuCArea)) {						
						form1.oldBuCArea.value=xmlDoc.documentElement.childNodes.item(i).getAttribute("carea");				
					}
					if (isObj(form1.oldBuSArea)) {
						form1.oldBuSArea.value=xmlDoc.documentElement.childNodes.item(i).getAttribute("sarea");
					}
					if (isObj(form1.oldBuArea))	 {
						form1.oldBuArea.value=xmlDoc.documentElement.childNodes.item(i).getAttribute("area");
					}
					if (isObj(form1.oldBuHoldNume))	 {
						form1.oldBuHoldNume.value=xmlDoc.documentElement.childNodes.item(i).getAttribute("holdNume");
					}	
					if (isObj(form1.oldBuHoldDeno))	 {
						form1.oldBuHoldDeno.value=xmlDoc.documentElement.childNodes.item(i).getAttribute("holdDeno");
					}
					if (isObj(form1.oldBuHoldArea))	 {
						form1.oldBuHoldArea.value=xmlDoc.documentElement.childNodes.item(i).getAttribute("holdArea");
					}
					
					if (isObj(form1.newBuCArea)) {
						form1.newBuCArea.value=xmlDoc.documentElement.childNodes.item(i).getAttribute("carea");
					}
					if (isObj(form1.newBuSArea)) {
						form1.newBuSArea.value=xmlDoc.documentElement.childNodes.item(i).getAttribute("sarea");
					}
					if (isObj(form1.newBuArea))	 {
						form1.newBuArea.value=xmlDoc.documentElement.childNodes.item(i).getAttribute("area");
					}
					if (isObj(form1.newBuHoldNume))	 {
						form1.newBuHoldNume.value=xmlDoc.documentElement.childNodes.item(i).getAttribute("holdNume");
					}	
					if (isObj(form1.newBuHoldDeno))	 {
						form1.newBuHoldDeno.value=xmlDoc.documentElement.childNodes.item(i).getAttribute("holdDeno");
					}
					if (isObj(form1.newBuHoldArea))	 {
						form1.newBuHoldArea.value=xmlDoc.documentElement.childNodes.item(i).getAttribute("holdArea");
					}
				}
				

								
				if (isObj(form1.accountingTitle))	 {
					form1.accountingTitle.value=xmlDoc.documentElement.childNodes.item(i).getAttribute("accountingTitle");
				}
				//�b�����
				if (isObj(form1.bookUnit))	 {
					form1.bookUnit.value=xmlDoc.documentElement.childNodes.item(i).getAttribute("bookUnit");
				}		
				if (isObj(form1.bookValue))	 {
					form1.bookValue.value=xmlDoc.documentElement.childNodes.item(i).getAttribute("bookValue");
				}
				//�±b�����				
				if (isObj(form1.oldBookUnit))	 {
					form1.oldBookUnit.value=xmlDoc.documentElement.childNodes.item(i).getAttribute("originalBV");
				}		
				if (isObj(form1.oldBookValue))	 {
					form1.oldBookValue.value=xmlDoc.documentElement.childNodes.item(i).getAttribute("oldBookValue");
				}
				if (isObj(form1.newBookUnit))	 {
					form1.newBookUnit.value=xmlDoc.documentElement.childNodes.item(i).getAttribute("originalBV");
				}		
				if (isObj(form1.newBookValue))	 {
					form1.newBookValue.value=xmlDoc.documentElement.childNodes.item(i).getAttribute("bookValue");
				}
				if (isObj(form1.adjustBookValue))	 {
					form1.adjustBookValue.value=xmlDoc.documentElement.childNodes.item(i).getAttribute("adjustBookValue");
				}
				
				if (isObj(form1.proofDoc))	 {
					form1.proofDoc.value=xmlDoc.documentElement.childNodes.item(i).getAttribute("proofDoc");
				}
				if (isObj(form1.ownershipDate))	 {
					form1.ownershipDate.value=xmlDoc.documentElement.childNodes.item(i).getAttribute("ownershipDate");
				}				
				if (isObj(form1.sourceDate))	 {
					form1.sourceDate.value=xmlDoc.documentElement.childNodes.item(i).getAttribute("sourceDate");
				}
				if (isObj(form1.useState))	 {						
					form1.useState.value=xmlDoc.documentElement.childNodes.item(i).getAttribute("useState");		
				}
				if (isObj(form1.useStateName))	 {
					form1.useStateName.value=xmlDoc.documentElement.childNodes.item(i).getAttribute("useStateName");
				}
				if (isObj(form1.useState1))	 {						
					form1.useState1.value=xmlDoc.documentElement.childNodes.item(i).getAttribute("useState1");		
				}
				if (isObj(form1.useState1Name))	 {
					form1.useState1Name.value=xmlDoc.documentElement.childNodes.item(i).getAttribute("useState1Name");		
				}
				if (isObj(form1.useSeparate))	 {
					form1.useSeparate.value=xmlDoc.documentElement.childNodes.item(i).getAttribute("useSeparate");
				}
				if (isObj(form1.useSeparateName))	 {
					form1.useSeparateName.value=xmlDoc.documentElement.childNodes.item(i).getAttribute("useSeparateName");
				}
				if (isObj(form1.useKind))	 {
					form1.useKind.value=xmlDoc.documentElement.childNodes.item(i).getAttribute("useKind");
				}
				if (isObj(form1.useKindName))	 {
					form1.useKindName.value=xmlDoc.documentElement.childNodes.item(i).getAttribute("useKindName");
				}
				if (isObj(form1.field))	 {
					form1.field.value=xmlDoc.documentElement.childNodes.item(i).getAttribute("field");
				}
				if (isObj(form1.fieldName))	 {
					form1.fieldName.value=xmlDoc.documentElement.childNodes.item(i).getAttribute("fieldName");
				}
				if (isObj(form1.originalBasis))	 {
					form1.originalBasis.value=xmlDoc.documentElement.childNodes.item(i).getAttribute("originalBasis");
				}
				if (isObj(form1.originalDate))	 {
					form1.originalDate.value=xmlDoc.documentElement.childNodes.item(i).getAttribute("originalDate");
				}
				if (isObj(form1.originalUnit))	 {
					form1.originalUnit.value=xmlDoc.documentElement.childNodes.item(i).getAttribute("originalUnit");
				}
				if (isObj(form1.oldUseSeparate))	 {
					form1.oldUseSeparate.value=xmlDoc.documentElement.childNodes.item(i).getAttribute("useSeparate");
				}
				if (isObj(form1.oldUseSeparateName))	 {
					form1.oldUseSeparateName.value=xmlDoc.documentElement.childNodes.item(i).getAttribute("useSeparateName");
				}											
				if (isObj(form1.oldUseKind)) {
					form1.oldUseKind.value=xmlDoc.documentElement.childNodes.item(i).getAttribute("useKind");
				}
				if (isObj(form1.oldUseKindName))	 {
					form1.oldUseKindName.value=xmlDoc.documentElement.childNodes.item(i).getAttribute("useKindName");
				}				
				if (isObj(form1.oldField))	 {
					form1.oldField.value=xmlDoc.documentElement.childNodes.item(i).getAttribute("field");
				}	
				if (isObj(form1.oldFieldName))	 {
					form1.oldFieldName.value=xmlDoc.documentElement.childNodes.item(i).getAttribute("fieldName");
				}	
				if (isObj(form1.check)) {
					form1.check.value=xmlDoc.documentElement.childNodes.item(i).getAttribute("check");
				}

				if (isObj(form1.newUseSeparate)) {
					for (j = 0; j < form1.newUseSeparate.options.length; j++) {						
						if (form1.newUseSeparate.options[j].value==xmlDoc.documentElement.childNodes.item(i).getAttribute("useSeparate")) {
							form1.newUseSeparate.options[j].selected=true;
						}
					}
				}

				if (isObj(form1.newUseKind)) {
					for (j = 0; j < form1.newUseKind.options.length; j++) {
						if (form1.newUseKind.options[j].value==xmlDoc.documentElement.childNodes.item(i).getAttribute("useKind")) {
							form1.newUseKind.options[j].selected=true;
						}
					}
				}

				if (isObj(form1.newField)) {
					for (j = 0; j < form1.newField.options.length; j++) {
						if (form1.newField.options[j].value==xmlDoc.documentElement.childNodes.item(i).getAttribute("field")) {
							form1.newField.options[j].selected=true;
						}
					}
				}
				
				if (isObj(form1.proceedType)) {
					for (j = 0; j < form1.proceedType.options.length; j++) {
						if (form1.proceedType.options[j].value==xmlDoc.documentElement.childNodes.item(i).getAttribute("proceedType")) {
							form1.proceedType.options[j].selected=true;
						}
					}					
				}
				if (isObj(form1.proceedTypeName))	 {
					form1.proceedTypeName.value=xmlDoc.documentElement.childNodes.item(i).getAttribute("proceedTypeName");
				}	
				if (isObj(form1.proceedDateS))	 {
					form1.proceedDateS.value=xmlDoc.documentElement.childNodes.item(i).getAttribute("proceedDateS");
				}	
				if (isObj(form1.proceedDateE))	 {
					form1.proceedDateE.value=xmlDoc.documentElement.childNodes.item(i).getAttribute("proceedDateE");
				}							
				if (isObj(form1.doorplate))	 {
					form1.doorplate.value=xmlDoc.documentElement.childNodes.item(i).getAttribute("doorplate");
				}
				
				if (isObj(form1.originalKeepUnit))	 {
					form1.originalKeepUnit.value=xmlDoc.documentElement.childNodes.item(i).getAttribute("originalKeepUnit");
				}
				if (isObj(form1.originalKeeper))	 {
					form1.originalKeeper.value=xmlDoc.documentElement.childNodes.item(i).getAttribute("originalKeeper");
				}
				if (isObj(form1.originalUseUnit))	 {
					form1.originalUseUnit.value=xmlDoc.documentElement.childNodes.item(i).getAttribute("originalUseUnit");
				}
				if (isObj(form1.originalUser))	 {
					form1.originalUser.value=xmlDoc.documentElement.childNodes.item(i).getAttribute("originalUser");
				}
				if (isObj(form1.keepUnit))	 {
					form1.keepUnit.value=xmlDoc.documentElement.childNodes.item(i).getAttribute("originalKeepUnit");
				}
				if (isObj(form1.keeper))	 {
					form1.keeper.value=xmlDoc.documentElement.childNodes.item(i).getAttribute("originalKeeper");
				}
				if (isObj(form1.useUnit))	 {
					form1.useUnit.value=xmlDoc.documentElement.childNodes.item(i).getAttribute("originalUseUnit");
				}
				if (isObj(form1.userNo))	 {
					form1.userNo.value=xmlDoc.documentElement.childNodes.item(i).getAttribute("originalUser");
				}
				
				if (isObj(form1.newBookValue))	 {
					form1.newBookValue.value=xmlDoc.documentElement.childNodes.item(i).getAttribute("bookValue");
				}	
				if (isObj(form1.useEndYM))	 {
					form1.useEndYM.value=xmlDoc.documentElement.childNodes.item(i).getAttribute("useEndYM");
				}	
			}
			
		}else{
			alert('Cant Load It!');
			return false;	
		}
	}	
}


function popUnitNo(queryValue,popUnitNo,popUnitNo2,popUnitNo3,popUnitNo4){
	var prop="";
	var windowTop=(document.body.clientHeight-400)/2+180;
	var windowLeft=(document.body.clientWidth-400)/2+250;
	prop=prop+"width=550,height=450,";
	prop=prop+"top="+windowTop+",";
	prop=prop+"left="+windowLeft+",";
	prop=prop+"scrollbars=no";
	returnWindow=window.open("../../home/popUnitNo.jsp?popUnitNo="+popUnitNo+"&queryValue="+queryValue+"&popUnitNo2="+popUnitNo2+"&popUnitNo3="+popUnitNo3+"&popUnitNo4="+popUnitNo4,"",prop);
}

function popUnitMan(queryValue,popUnitMan,popUnitMan2,popUnitMan3,popUnitMan4){
	var prop="";
	var windowTop=(document.body.clientHeight-400)/2+180;
	var windowLeft=(document.body.clientWidth-400)/2+250;
	prop=prop+"width=550,height=450,";
	prop=prop+"top="+windowTop+",";
	prop=prop+"left="+windowLeft+",";
	prop=prop+"scrollbars=no";
	returnWindow=window.open("../../home/popUnitMan.jsp?popUnitMan="+popUnitMan+"&queryValue="+queryValue+"&popUnitMan2="+popUnitMan2+"&popUnitMan3="+popUnitMan3+"&popUnitMan4="+popUnitMan4,"",prop);
}

function popPlace(queryValue,popPlace,popPlaceName){
	var prop="";
	var windowTop=(document.body.clientHeight-400)/2+180;
	var windowLeft=(document.body.clientWidth-400)/2+250;
	prop=prop+"width=550,height=450,";
	prop=prop+"top="+windowTop+",";
	prop=prop+"left="+windowLeft+",";
	prop=prop+"scrollbars=no";
	returnWindow=window.open("../../home/popPlace.jsp?popPlace="+popPlace+"&queryValue="+queryValue+"&popPlaceName="+popPlaceName,"",prop);
}

function changeKeepUnit(enterOrg, fieldName, selectedValue){
	
	var queryValue = parse(enterOrg.value);

	if (isObj(fieldName)) {		
		var obj2 = fieldName;
		obj2.options.length=0;
		var obj2Option = document.createElement("Option");
		obj2.options.add(obj2Option);
		obj2Option.innerText = "請選擇";
		obj2Option.value = "";	
	}

	if (queryValue <=0) { 
		return false; 
	} else {
		var xmlDoc=document.createElement("xml");
		xmlDoc.async=false;
		if(xmlDoc.load("../../home/xmlKeepUnit_TCGH.jsp?enterOrg="+queryValue)){	
			var nodesLen=xmlDoc.documentElement.childNodes.length;
			for(i=0; i<nodesLen; i++){
				unitNo=xmlDoc.documentElement.childNodes.item(i).getAttribute("unitNo");
				unitName=xmlDoc.documentElement.childNodes.item(i).getAttribute("unitName");
				var oOption = document.createElement("Option");	
				obj2.options.add(oOption);
				oOption.innerText = unitName;
				oOption.value = unitNo;		
		      	if(unitNo == selectedValue){
        			oOption.selected=true;
				}           										
			}
		}else{
			return false;	
		}
	}	
}

function getKeeper(enterOrg, fieldName, selectedValue ,incumbencyYN) {
	var alertStr = "";	
	var organID = parse(enterOrg.value);
	
	if (isObj(fieldName)) {		
		var obj2 = fieldName;
		obj2.options.length=0;
		var obj2Option = document.createElement("Option");
		obj2.options.add(obj2Option);
		obj2Option.innerText = "請選擇";
		obj2Option.value = "";	
	}				
	if (organID<=0) { 
		return false; 
	} else {
		//清除keeper
		var xmlDoc=document.createElement("xml");	
		xmlDoc.async=false;		
		if(xmlDoc.load("../../home/xmlKeeper_TCGH.jsp?enterOrg="+organID+"&incumbencyYN="+incumbencyYN)){		
			var nodesLen=xmlDoc.documentElement.childNodes.length;
			for(i=0; i<nodesLen; i++){
				keeperNo=xmlDoc.documentElement.childNodes.item(i).getAttribute("keeperNo");
				keeperName=xmlDoc.documentElement.childNodes.item(i).getAttribute("keeperName");
				var oOption = document.createElement("Option");	
				obj2.options.add(oOption);
				oOption.innerText = keeperName;
				oOption.value = keeperNo;		
		      	if(keeperNo == selectedValue){
        			oOption.selected=true;
				}           										
			}
		}else{
			return false;	
		}			
	}	
}

function checkQ_originalBuildFeeCB(){	
	if($("input[name='cb_originalBuildFeeCB']").attr('checked') == 'checked'){
		$("input[name='q_originalBuildFeeCB']").val('Y');
	}else{
		$("input[name='q_originalBuildFeeCB']").val('N');
	}
}

function checkQ_originalDeprUnitCB(){
	if($("input[name='cb_originalDeprUnitCB']").attr('checked') == 'checked'){
		$("input[name='q_originalDeprUnitCB']").val('Y');
	}else{
		$("input[name='q_originalDeprUnitCB']").val('N');
	}
}


function popProperty(popPropertyNo,popPropertyName,preWord){
	var prop="";
	var windowTop=(document.body.clientHeight-400)/2+180;
	var windowLeft=(document.body.clientWidth-400)/2+250;
	prop=prop+"width=550,height=450,";
	prop=prop+"top="+windowTop+",";
	prop=prop+"left="+windowLeft+",";
	prop=prop+"scrollbars=no";
	returnWindow=window.open("chpopProperty.jsp?popPropertyNo="+popPropertyNo+"&popPropertyName="+popPropertyName+"&preWord="+preWord,"",prop);
}


function popEngineering(queryValue,popEngineering,popEngineeringName){
	var prop="";
	var windowTop=(document.body.clientHeight-400)/2;
	var windowLeft=(document.body.clientWidth-400)/2-50;
	prop=prop+"width=550,height=450,";
	prop=prop+"top="+windowTop+",";
	prop=prop+"left="+windowLeft+",";
	prop=prop+"scrollbars=no";
	returnWindow=window.open("../../home/popEngineering.jsp?queryValue="+queryValue+"&popEngineering="+popEngineering+"&popEngineeringName="+popEngineeringName,"",prop);
}


function getProperty(popPropertyNo,popPropertyName,preWord,isCls){
	var alertStr = "";
	var arrPreWord = "";
	var isLookup = false;
	var q_enterOrg = "";
	
	if (parse(document.all.item(popPropertyNo).value).length>0) {
		
		if (isObj(document.all.item(popPropertyNo))) {
			var obj = document.all.item(popPropertyNo);
			if (obj.className=="field_Q") {
				if ((isObj(document.all.item("q_enterOrg"))) && (document.all.item("q_enterOrg").value!="")) {
					q_enterOrg = document.all.item("q_enterOrg").value;
				}
			} else {
				if ((isObj(document.all.item("enterOrg"))) && (document.all.item("enterOrg").value!="")) {
					q_enterOrg = document.all.item("enterOrg").value;
				}			
			}
		}
		
		//if (q_enterOrg.length>0) q_enterOrg = "&q_enterOrg="+q_enterOrg;
		
		var q = "1&q_propertyNo="+document.all.item(popPropertyNo).value+"&preWord="+preWord+"&q_enterOrg="+q_enterOrg;
		var x = getRemoteData(getVirtualPath() + "/ajax/jsonProperty.jsp",q);

		if(x != null && x.length > 0){
			var json = eval('(' + x +')');
			if(json != null && json.length > 0){
				for(var i=0; i<json.length; i++){
					if (isObj(document.all.item(popPropertyName))) {
						document.all.item(popPropertyName).value = json[i].propertyName;
					}
					if (isObj(document.all.item("propertyName1"))) {
						document.all.item("propertyName1").value = json[i].propertyName1;
					}
					if (isObj(document.all.item("propertyUnit"))) {
						document.all.item("propertyUnit").value=json[i].propertyUnit;
						if (isObj(document.all.item("otherPropertyUnit"))) {
							document.all.item("otherPropertyUnit").value=json[i].propertyUnit;
						}
					}
					if (isObj(document.all.item("material"))) {
						document.all.item("material").value=json[i].material;
						if (isObj(document.all.item("otherMaterial"))) {
							document.all.item("otherMaterial").value=json[i].material;
						}
					}
					if (isObj(document.all.item("originalLimitYear"))){
						document.all.item("originalLimitYear").value=json[i].originalLimitYear;
					}
					if (isObj(document.all.item("limitYear"))){
						document.all.item("limitYear").value=json[i].limitYear;
						document.all.item("otherLimitYear").value=json[i].limitYear * 12;
					}
					
					if (isObj(document.all.item("propertyUnitRf"))) document.all.item("propertyUnitRf").value="";
					if (isObj(document.all.item("originalAmount"))) document.all.item("originalAmount").disabled=false;
					
					var propertyNo = json[i].propertyNo;
					var checkStr1 = propertyNo.substring(0,1);
					var checkStr3 = propertyNo.substring(0,3);
					
					if (isObj(document.all.item("propertyUnitRf")))	document.all.item("propertyUnitRf").value=json[i].propertyUnit;
					
					
					if(checkStr3 == '111'){	
						if (isObj(document.all.item("originalPropertyUnit"))) document.all.item("originalPropertyUnit").value=json[i].propertyUnit;
						
					}else if(checkStr1 == '1'){		
						if (isObj(document.all.item("originalPropertyUnit"))) document.all.item("originalPropertyUnit").value="平方公尺";
						if (isObj(document.all.item("originalAmount"))) document.all.item("originalAmount").value="1";
						if (isObj(document.all.item("originalAmount"))) document.all.item("originalAmount").disabled=true;
						
					}else if(checkStr1 == '2'){		
						if (isObj(document.all.item("originalPropertyUnit"))) document.all.item("originalPropertyUnit").value="平方公尺";
						if (isObj(document.all.item("originalAmount"))) document.all.item("originalAmount").value="1";
						if (isObj(document.all.item("originalAmount"))) document.all.item("originalAmount").disabled=true;
						
					}else if(checkStr1 == '3' || checkStr1 == '4' || checkStr1 == '5'){												
						if (isObj(document.all.item("originalPropertyUnit"))) document.all.item("originalPropertyUnit").value=json[i].propertyUnit;
						
					}else if(checkStr1 == '9'){
					
					}else {
						if (isObj(document.all.item("originalAmount"))) document.all.item("originalAmount").value="1";
						if (isObj(document.all.item("originalAmount"))) document.all.item("originalAmount").disabled=true;
					}
				}
			}
		}
		else{
			if (isObj(document.all.item(popPropertyNo))) {
				document.all.item(popPropertyNo).value = "";
			}
		}
		
		/*
		var i = 0;
		var xmlDoc=document.createElement("xml");		
		xmlDoc.async=false;

		if(xmlDoc.load("../../home/xmlProperty.jsp?q_propertyNo="+document.all.item(popPropertyNo).value+"&preWord="+preWord+q_enterOrg)) {
			for(i=0; i<xmlDoc.documentElement.childNodes.length; i++){
				
				if (isObj(document.all.item(popPropertyName))) {
					document.all.item(popPropertyName).value=xmlDoc.documentElement.childNodes.item(i).getAttribute("propertyName");
				}
				
				if (isObj(document.all.item("material"))){
					document.all.item("material").value=xmlDoc.documentElement.childNodes.item(i).getAttribute("material");
				}
						
				if (isObj(document.all.item("propertyUnit"))){
					document.all.item("propertyUnit").value=xmlDoc.documentElement.childNodes.item(i).getAttribute("propertyUnit");
				}
				if (isObj(document.all.item("limitYear"))){
					document.all.item("limitYear").value=xmlDoc.documentElement.childNodes.item(i).getAttribute("limitYear");
				}
				if (isObj(document.all.item("propertyUnitRf"))) document.all.item("propertyUnitRf").value="";
				if (isObj(document.all.item("originalAmount"))) document.all.item("originalAmount").disabled=false;
				
				var propertyNo = xmlDoc.documentElement.childNodes.item(i).getAttribute("propertyNo");
				var checkStr1 = propertyNo.substring(0,1);
				var checkStr3 = propertyNo.substring(0,3);
				
				if (isObj(document.all.item("propertyUnitRf")))	document.all.item("propertyUnitRf").value=xmlDoc.documentElement.childNodes.item(i).getAttribute("propertyUnit");
				
				
				if(checkStr3 == '111'){	
					if (isObj(document.all.item("originalPropertyUnit"))) document.all.item("originalPropertyUnit").value=xmlDoc.documentElement.childNodes.item(i).getAttribute("propertyUnit");
					
				}else if(checkStr1 == '1'){		
					if (isObj(document.all.item("originalPropertyUnit"))) document.all.item("originalPropertyUnit").value="平方公尺";
					if (isObj(document.all.item("originalAmount"))) document.all.item("originalAmount").value="1";
					if (isObj(document.all.item("originalAmount"))) document.all.item("originalAmount").disabled=true;
					
				}else if(checkStr1 == '2'){		
					if (isObj(document.all.item("originalPropertyUnit"))) document.all.item("originalPropertyUnit").value="平方公尺";
					if (isObj(document.all.item("originalAmount"))) document.all.item("originalAmount").value="1";
					if (isObj(document.all.item("originalAmount"))) document.all.item("originalAmount").disabled=true;
					
				}else if(checkStr1 == '3' || checkStr1 == '4' || checkStr1 == '5'){												
					if (isObj(document.all.item("originalPropertyUnit"))) document.all.item("originalPropertyUnit").value=xmlDoc.documentElement.childNodes.item(i).getAttribute("propertyUnit");
					
				}else if(checkStr1 == '9'){
				
				}else {
					if (isObj(document.all.item("originalAmount"))) document.all.item("originalAmount").value="1";
					if (isObj(document.all.item("originalAmount"))) document.all.item("originalAmount").disabled=true;
				}
			}
			
			if(xmlDoc.documentElement.childNodes.length == 0){
				if (isObj(document.all.item(popPropertyNo))) {
					document.all.item(popPropertyNo).value = "";
				}
			}			
			
		}
		*/
	} else { 
		if (isCls!="N") {	
			document.all.item(popPropertyNo).value="";
			document.all.item(popPropertyName).value="";
		}
	}
	
	if (arrPreWord.length>2 && arrPreWord[2]!="") eval(arrPreWord[2].substring(arrPreWord[2].indexOf("=")+1));
	
}

function queryPlaceName(queryEnterOrg, queryPlace){
	//傳送JSON
	var comment={};	
	comment.enterOrg = document.all.item(queryEnterOrg).value;
	comment.place = document.all.item(queryPlace).value;
	
	$.post('queryPlaceName.jsp',
		comment,
		function(data){
			//將回傳資料寫入
			data=eval('('+data+')');
			if (data['placeName'] == "") {
				$("input[name='" + queryPlace + "Name']").val('');
				$("input[name='" + queryPlace + "']").val('');
				alert("不存在的存置地點");
			} else {
				$("input[name='" + queryPlace + "Name']").val(data['placeName']);
			}
		});	
}

function queryEngineeringName(queryEnterOrg, queryPlace){
	//傳送JSON
	var comment={};	
	comment.enterOrg = document.all.item(queryEnterOrg).value;
	comment.engineeringno = document.all.item(queryPlace).value;
	
	$.post('../ch/queryEngineeringName.jsp',
		comment,
		function(data){
			//將回傳資料寫入
			data=eval('('+data+')');

			$("input[name='" + queryPlace + "Name']").val(data['engineeringName']);
			
		});	
}

function queryforDeprUnit(){
	//傳送JSON
	var comment={};	
	comment.enterOrg = document.all.item("enterOrg").value;
	comment.queryData = document.all.item("originalKeepUnit").value;
	
	$.post('queryKeepUnitData.jsp',
		comment,
		function(data){
			//將回傳資料寫入
			data=eval('('+data+')');

			$("input[name='originalDeprUnittemp']").val(data['defaultValue']);
			
		});
}

function queryDeprUnitData(){
	if($("input[name='originalDeprUnittemp']").val() != ''){
		$("select[name='originalDeprUnit']").val($("input[name='originalDeprUnittemp']").val());
		$("input[name='originalDeprUnittemp']").val('');
	}
	queryDeprUnitDataforDeprUnit1();
	queryDeprUnitDataforDeprAccounts();
}

function queryDeprUnitDataforDeprUnit1(){
	//if($("select[name='originalDeprUnit1']").val() == ''){
		if($("select[name='originalDeprUnit']").val() != ''){		
			//傳送JSON		
			var comment={};	
			comment.enterOrg = document.all.item("enterOrg").value;
			comment.queryData = document.all.item("originalDeprUnit").value;
			comment.queryType = "deprUnit1";
			
			$.post('queryDeprUnitData.jsp',
				comment,
				function(data){
					//將回傳資料寫入
					data=eval('('+data+')');
	
					$("select[name='originalDeprUnit1']").val(data['defaultValue']);
			
				});
		}
	//}
}
function queryDeprUnitDataforDeprAccounts(){
	//if($("select[name='originalDeprAccounts']").val() == ''){
		if($("select[name='originalDeprUnit']").val() != ''){	
			//傳送JSON
			var comment={};	
			comment.enterOrg = document.all.item("enterOrg").value;
			comment.queryData = document.all.item("originalDeprUnit").value;
			comment.queryType = "deprAccounts";
			
			$.post('queryDeprUnitData.jsp',
				comment,
				function(data){
					//將回傳資料寫入
					data=eval('('+data+')');
		
					$("select[name='originalDeprAccounts']").val(data['defaultValue']);
					
				});
		}
	//}
}

function initQ_Form(querySelect){
	if(querySelect == "proof"){	
		$("tr[name='div_q_proof']").show();
		$("tr[name='div_q_detail']").hide();
	}else{	
		$("tr[name='div_q_proof']").hide();
		$("tr[name='div_q_detail']").show();
	}
	
}



function calValue(name){
	var checkStr1 = form1.propertyNo.value.substring(0,1);
	var checkStr3 = form1.propertyNo.value.substring(0,3);
	
	//土地
	if(checkStr3 != '111' && checkStr1 == '1'){
		//輸入單價	權利面積 * 單價 四捨五入至元
		if(name == 'originalUnit'){
			form1.originalUnit.value = roundp(form1.originalUnit.value,'2',"Y") ;
			form1.originalBV.value = roundp((form1.originalHoldAreaLa.value * form1.originalUnit.value),'0',"Y").replace(".","");
			
		//輸入總價	總價 /權利面積  四捨五入至元
		}else if(name == 'originalBV'){
			form1.originalBV.value = roundp(form1.originalBV.value,'0',"Y").replace(".","");
			form1.originalUnit.value = roundp((form1.originalBV.value / form1.originalHoldAreaLa.value),'2',"Y");
			
		}else if(form1.originalUnit.value != ''){
			form1.originalBV.value = roundp((form1.originalHoldAreaLa.value * form1.originalUnit.value),'0',"Y").replace(".","");
		}	
	//動產
	}else if(checkStr1 == '3' || checkStr1 == '4' | checkStr1 == '5'){
		//輸入單價	數量 * 單價 = 總價
		if(name == 'originalUnit'){
			if(parseInt(form1.originalUnit.value) == 0 || form1.originalUnit.value == ''){
				alert('無單價只有總價時，數量必須為１');
				form1.originalAmount.value = '1';				
			}else{
				if(parseInt(form1.originalUnit.value) < 10000){
					form1.originalUnit.value = '';
					form1.originalUnit.style.backgroundColor = errorbg;
					alert('單價必須大於等於10000');
				}else{
					form1.originalUnit.style.backgroundColor = "";
					form1.originalBV.value = roundp((form1.originalAmount.value * form1.originalUnit.value),'0',"Y").replace(".","");	
				}
			}
			
		//輸入總價
		}else if(name == 'originalBV'){
			form1.originalUnit.value = '';
			form1.originalBV.value = roundp(form1.originalBV.value,'0',"Y").replace(".","");
			
			if(parseInt(form1.originalBV.value) < 10000){
				form1.originalBV.value = '';
				form1.originalUnit.value = '';
				form1.originalBV.style.backgroundColor = errorbg;
				form1.originalUnit.style.backgroundColor = errorbg;
				alert('總價必須大於等於10000');
			}else{
				form1.originalBV.style.backgroundColor = "";
				form1.originalUnit.style.backgroundColor = "";
			}
			
		}else if(name == 'originalAmount'){
			form1.originalBV.value = form1.originalUnit.value * form1.originalAmount.value;
		}
	}else if(checkStr1 == '8' || checkStr1 == '9'){
		if(name == 'originalUnit'){
			form1.originalBV.value = roundp((form1.originalAmount.value * form1.originalUnit.value),'0',"Y").replace(".","");	
		}else{
			if(form1.originalAmount.value != ''){
				form1.originalUnit.value = roundp((parseInt(form1.originalBV.value) / parseInt(form1.originalAmount.value)),'2',"Y");
			}
		}
	}else{
		form1.originalBV.value = roundp(form1.originalBV.value,'0',"Y").replace(".","");
		form1.originalUnit.value = roundp(form1.originalUnit.value,'0',"Y").replace(".","");
	}
}

function checkOriginalBuildFeeCB(){	
	if($("input[name='cb_originalBuildFeeCB']").attr('checked') == 'checked'){
		$("input[name='originalBuildFeeCB']").val('Y');
	}else{
		$("input[name='originalBuildFeeCB']").val('N');
	}
}

function checkOriginalDeprUnitCB(){
	if($("input[name='cb_originalDeprUnitCB']").attr('checked') == 'checked'){
		$("input[name='originalDeprUnitCB']").val('Y');
	}else{
		$("input[name='originalDeprUnitCB']").val('N');
	}
}


var time = 2;
function refreshTime() {
	$("select[name='laSignNo1']").attr('disabled',true);
	$("select[name='laSignNo2']").attr('disabled',true);
	$("select[name='laSignNo3']").attr('disabled',true);
	$("select[name='buSignNo1']").attr('disabled',true);
	$("select[name='buSignNo2']").attr('disabled',true);
	$("select[name='buSignNo3']").attr('disabled',true);
	$("select[name='q_signLaNo1']").attr('disabled',true);
	$("select[name='q_signLaNo2']").attr('disabled',true);
	$("select[name='q_signLaNo3']").attr('disabled',true);
	$("select[name='q_signBuNo1']").attr('disabled',true);
	$("select[name='q_signBuNo2']").attr('disabled',true);
	$("select[name='q_signBuNo3']").attr('disabled',true);
	
	time--;
	if (time < 1) {
		time = 2;
		$("select[name='laSignNo1']").attr('disabled',false);
		$("select[name='laSignNo2']").attr('disabled',false);
		$("select[name='laSignNo3']").attr('disabled',false);
		$("select[name='buSignNo1']").attr('disabled',false);
		$("select[name='buSignNo2']").attr('disabled',false);
		$("select[name='buSignNo3']").attr('disabled',false);
		$("select[name='q_signLaNo1']").attr('disabled',false);
		$("select[name='q_signLaNo2']").attr('disabled',false);
		$("select[name='q_signLaNo3']").attr('disabled',false);
		$("select[name='q_signBuNo1']").attr('disabled',false);
		$("select[name='q_signBuNo2']").attr('disabled',false);
		$("select[name='q_signBuNo3']").attr('disabled',false);
    }else{
    	setTimeout("refreshTime()", "1000");
    }	
}




