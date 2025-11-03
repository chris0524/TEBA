//***************************************************************
//函數功能：找出UNTMP_Movable資料表裡的
//originalBV,bookValue
//***************************************************************
function getUNTMPMovable_update(enterOrg, ownership, propertyNo, serialNo){
	var alertStr = "";
	var i = 0, j=0;
	var senterOrg="", sownership="", spropertyNo="", sserialNo="";
	if (isObj(enterOrg.value)) senterOrg = enterOrg.value;
	else senterOrg = enterOrg;
	
	if (isObj(ownership.value)) sownership = ownership.value;
	else sownership = ownership;
	
	if (isObj(propertyNo.value)) spropertyNo = propertyNo.value;
	else spropertyNo = propertyNo;
	
	if (isObj(serialNo.value)) sserialNo = serialNo.value;
	else sserialNo = serialNo;
	
	if (parse(senterOrg).length>0 && parse(sownership).length>0 && parse(spropertyNo).length>0 && parse(sserialNo).length>0) {
		queryValue = "enterOrg="+senterOrg+"&ownership="+sownership+"&propertyNo="+spropertyNo+"&serialNo="+sserialNo;
		var xmlDoc=document.createElement("xml");		
		xmlDoc.async=false;			
		if(xmlDoc.load("../../home/xmlUNTMPMovable_update.jsp?"+queryValue)){
			for(i=0; i<xmlDoc.documentElement.childNodes.length; i++){
				if (isObj(form1.originalBV)) form1.originalBV.value = xmlDoc.documentElement.childNodes.item(i).getAttribute("originalBV");				
				if (isObj(form1.bookValue)) form1.bookValue.value = xmlDoc.documentElement.childNodes.item(i).getAttribute("bookValue");
			}
		}else{
			alert('Failed to load xmlUNTMPMovable_update!');
			return false;
		}
	}		
	
}
