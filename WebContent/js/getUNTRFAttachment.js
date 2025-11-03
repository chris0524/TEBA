//*********************************************
//函數功能：找出UNTRF_Attachment資料表裡的propertyNo,propertyName,serialNo
//參　　數：無
//*********************************************
function getUNTRFAttachment(){
	var alertStr = "";
	var i = 0, j=0;
	var sFlag = false;
	alertStr += checkEmpty(form1.enterOrg,"入帳機關");
	alertStr += checkEmpty(form1.ownership,"權屬");
	alertStr += checkEmpty(form1.propertyNo,"財產編號");
	alertStr += checkEmpty(form1.serialNo,"財產分號");
	if (form1.propertyNo.value=="" || form1.serialNo.value=="") {
		form1.propertyNo.style.backgroundColor="";
		form1.serialNo.style.backgroundColor="";
	}
		
	if(alertStr.length!=0){
	/**	if (isObj(form1.propertyNo)) { form1.propertyNo.value=""; }		
		if (isObj(form1.propertyName)) { form1.propertyName.value=""; }
		if (isObj(form1.serialNo)) { form1.serialNo.value=""; }	
	**/
		return false;
	} else {
		var enterOrg = form1.enterOrg.value;
		var ownership=form1.ownership.value;
		var sPropertyNo = form1.propertyNo.value;
		var sPropertyName = form1.propertyNoName.value;
		var sSerialNo = form1.serialNo.value;
		var queryValue = "";	

		queryValue = "enterOrg="+enterOrg+"&ownership="+ownership+"&propertyNo="+sPropertyNo+"&propertyNoName="+sPropertyName+"&serialNo="+sSerialNo;
		
		//傳送一個亂數資料,防止瀏灠器由快取直接取得資料導至資料錯誤
		var randomnumber=Math.floor(Math.random()*1000);
		queryValue += "&randomnumber="+randomnumber;
		
		var xmlDoc=document.createElement("xml");		
		xmlDoc.async=false;		
		
		if(xmlDoc.load("../../home/xmlUntrfAttachment.jsp?"+queryValue)){	
			for(i=0; i<xmlDoc.documentElement.childNodes.length; i++){
			
				sFlag = true;

				if (isObj(form1.propertyNo)) {
					form1.propertyNo.value=xmlDoc.documentElement.childNodes.item(i).getAttribute("propertyNo");
				}
				if (isObj(form1.propertyNoName)) {
					form1.propertyNoName.value=xmlDoc.documentElement.childNodes.item(i).getAttribute("propertyNoName");
				}			
				if (isObj(form1.serialNo)) {
					form1.serialNo.value=xmlDoc.documentElement.childNodes.item(i).getAttribute("serialNo");
				}
				if (isObj(form1.propertyName1)) {
						form1.propertyName1.value=xmlDoc.documentElement.childNodes.item(i).getAttribute("propertyName1");
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
				if (isObj(form1.measure)) {
						form1.measure.value=xmlDoc.documentElement.childNodes.item(i).getAttribute("measure");
				}
				if (isObj(form1.accountingTitle)) {
						form1.accountingTitle.value=xmlDoc.documentElement.childNodes.item(i).getAttribute("accountingTitle");
				}
				if (isObj(form1.bookValue)) {
						form1.bookValue.value=xmlDoc.documentElement.childNodes.item(i).getAttribute("bookValue");
				}
				if (isObj(form1.buildDate)) {
						form1.buildDate.value=xmlDoc.documentElement.childNodes.item(i).getAttribute("buildDate");
				}
				if (isObj(form1.originalBV)) {
						form1.originalBV.value=xmlDoc.documentElement.childNodes.item(i).getAttribute("originalBV");
				}
				if (isObj(form1.sourceDate)) {
						form1.sourceDate.value=xmlDoc.documentElement.childNodes.item(i).getAttribute("sourceDate");
				}
				if (isObj(form1.deprMethod)) {
						form1.deprMethod.value=xmlDoc.documentElement.childNodes.item(i).getAttribute("deprMethod");
				}
				if (isObj(form1.deprAmount)) {
						form1.deprAmount.value=xmlDoc.documentElement.childNodes.item(i).getAttribute("deprAmount");
				}
				if (isObj(form1.monthDepr)) {
						form1.monthDepr.value=xmlDoc.documentElement.childNodes.item(i).getAttribute("monthDepr");
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
				if (isObj(form1.oldPropertyNo)) {
						form1.oldPropertyNo.value=xmlDoc.documentElement.childNodes.item(i).getAttribute("oldPropertyNo");
				}
				if (isObj(form1.oldSerialNo)) {
					form1.oldSerialNo.value=xmlDoc.documentElement.childNodes.item(i).getAttribute("oldSerialNo");
				}
				if (isObj(form1.propertyUnit)) {
					form1.propertyUnit.value=xmlDoc.documentElement.childNodes.item(i).getAttribute("propertyUnit");
				}
				if (isObj(form1.check)) {
						form1.check.value=xmlDoc.documentElement.childNodes.item(i).getAttribute("check");
				}				
			}
			if (!sFlag) {
				if (isObj(form1.propertyNo)) { form1.propertyNo.value=""; }		
				if (isObj(form1.propertyNoName)) { form1.propertyName.value=""; }
				if (isObj(form1.serialNo)) { form1.serialNo.value=""; }			
				return false;	
			}
		}else{
		/** alert('Cant Load It!'); **/
			return false;	
		}
	}	
}