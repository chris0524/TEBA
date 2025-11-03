//*********************************************
//函數功能：找出UNTLA_LAND資料表裡的propertyNo,propertyName,serialNo
//參　　數：strType, PN=以財產編號做Lookup, 其它以土地標示代碼做Lookup
//enterOrg,ownership,signNo1,signNo2,signNo3,signNo4,signNo5
//*********************************************
function getPropertyNoPub(strType, sdataState, spropertyKind){
	var alertStr = "";
	//window.alert(form1.signNo1.value+form1.signNo2.value+form1.signNo3.value+form1.signNo4.value +form1.signNo5.value);
	var i = 0, j=0;	
	alertStr += checkEmpty(form1.enterOrg,"入帳機關");
	alertStr += checkEmpty(form1.ownership,"權屬");
	if (strType=="PN") {
		alertStr += checkEmpty(form1.propertyNo,"財產編號");
		alertStr += checkEmpty(form1.serialNo,"財產分號");
		if (form1.propertyNo.value=="" || form1.serialNo.value=="") {
			form1.propertyNo.style.backgroundColor="";
			form1.serialNo.style.backgroundColor="";
			/*form1.propertyNo.style.backgroundColor=errorbg;
			form1.serialNo.style.backgroundColor=errorbg;*/
		}
	} else {
		if ((form1.signNo1.value=="")||(form1.signNo2.value=="")||(form1.signNo3.value=="")||(form1.signNo4.value=="")||(form1.signNo5.value=="")){
			form1.signNo1.style.backgroundColor="";
			form1.signNo2.style.backgroundColor="";
			form1.signNo3.style.backgroundColor="";
			form1.signNo4.style.backgroundColor="";
			form1.signNo5.style.backgroundColor="";
			/*form1.signNo1.style.backgroundColor=errorbg;
			form1.signNo2.style.backgroundColor=errorbg;
			form1.signNo3.style.backgroundColor=errorbg;
			form1.signNo4.style.backgroundColor=errorbg;
			form1.signNo5.style.backgroundColor=errorbg;*/
			alertStr += "土地標示代碼不得為空白!\n";
		}
	}
	if(alertStr.length!=0){
		//if (isObj(form1.propertyNo)) { form1.propertyNo.value=""; }
		//if (isObj(form1.propertyName)) { form1.propertyName.value=""; }
		if (isObj(form1.dataState)) { form1.dataState.value="";	}
		if (isObj(form1.dataStateName)) { form1.dataStateName.value="";	}		
		if (isObj(form1.oldPropertyNo)) { form1.oldPropertyNo.value=""; }		
		if (isObj(form1.oldSerialNo)) { form1.oldSerialNo.value="";	}			
		if (isObj(form1.oldUseSeparate)) { form1.oldUseSeparate.value=""; }
		if (isObj(form1.oldUseSeparateName)) { form1.oldUseSeparateName.value=""; }		
		if (isObj(form1.oldUseKind)) { form1.oldUseKind.value="";}
		if (isObj(form1.oldUseKindName)) {form1.oldUseKindName.value=""; }		
		if (isObj(form1.oldField)) { form1.oldField.value="";	}
		if (isObj(form1.oldFieldName)) {form1.oldFieldName.value=""; }		
		
		return false;
	} else {	
	
	    form1.holdArea.value ="";
	    form1.useSeparate.value ="";
	    form1.valueUnit.value ="";
	    form1.priceUnit.value ="";

		var enterOrg = form1.enterOrg.value;
		var ownership=form1.ownership.value;
		var sNo1=form1.signNo1.value;
		var sNo2=form1.signNo2.value;
		var sNo3=form1.signNo3.value;
		var sNo4=form1.signNo4.value;
		var sNo5=form1.signNo5.value;	
		var sPropertyNo = form1.propertyNo.value;
		var sSerialNo = form1.serialNo.value;
		var sSignNo = "";
		var queryValue = "";	
		var sExtSQL = "";
		var bDataExist = false;
		
		if (strType=="PN") {		
			queryValue = "enterOrg="+enterOrg+"&ownership="+ownership+"&sType=PN&propertyNo="+sPropertyNo+"&serialNo="+sSerialNo + "&sdataState=" + sdataState + "&spropertyKind=" + spropertyKind;
		} else {
			queryValue = "enterOrg="+enterOrg+"&ownership="+ownership+"&sType=SN&sNo1="+sNo1+"&sNo2="+sNo2+"&sNo3="+sNo3+"&sNo4="+sNo4+"&sNo5="+sNo5 + "&sdataState=" + sdataState + "&spropertyKind=" + spropertyKind;
		}
    	//window.alert(queryValue);					
		var xmlDoc=document.createElement("xml");		
		xmlDoc.async=false;			
		if(xmlDoc.load("../../home/xmlForPub.jsp?"+queryValue)){

			for(i=0; i<xmlDoc.documentElement.childNodes.length; i++){
			    bDataExist = true;
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
					form1.signNo4.value=sSignNo.substring(7,11);
				}										
				if (isObj(form1.signNo5)) {
					form1.signNo5.value=sSignNo.substring(11,15);
				}												
				form1.propertyNo.value=xmlDoc.documentElement.childNodes.item(i).getAttribute("propertyNo");
				form1.serialNo.value=xmlDoc.documentElement.childNodes.item(i).getAttribute("serialNo");
				if (isObj(form1.propertyName)) {
					form1.propertyName.value=xmlDoc.documentElement.childNodes.item(i).getAttribute("propertyName");
				}
				if (isObj(form1.propertyNoName)) {
					form1.propertyNoName.value=xmlDoc.documentElement.childNodes.item(i).getAttribute("propertyName");
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
				if (isObj(form1.originalBV))	 {
					form1.originalBV.value=xmlDoc.documentElement.childNodes.item(i).getAttribute("originalBV");
				}
				//面積
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
				//舊面積
				if (isObj(form1.oldArea))	 {
					form1.oldArea.value=xmlDoc.documentElement.childNodes.item(i).getAttribute("area");
				}
				if (isObj(form1.oldHoldNume))	 {
					form1.oldHoldNume.value=xmlDoc.documentElement.childNodes.item(i).getAttribute("holdNume");
				}	
				if (isObj(form1.oldHoldDeno))	 {
					form1.oldHoldDeno.value=xmlDoc.documentElement.childNodes.item(i).getAttribute("holdDeno");
				}
				if (isObj(form1.oldHoldArea))	 {
					form1.oldHoldArea.value=xmlDoc.documentElement.childNodes.item(i).getAttribute("holdArea");
				}
				//新面積
				if (isObj(form1.newArea))	 {
					form1.oldArea.value=xmlDoc.documentElement.childNodes.item(i).getAttribute("area");
				}
				if (isObj(form1.newHoldNume))	 {
					form1.oldHoldNume.value=xmlDoc.documentElement.childNodes.item(i).getAttribute("holdNume");
				}	
				if (isObj(form1.newHoldDeno))	 {
					form1.oldHoldDeno.value=xmlDoc.documentElement.childNodes.item(i).getAttribute("holdDeno");
				}
				if (isObj(form1.newHoldArea))	 {
					form1.oldHoldArea.value=xmlDoc.documentElement.childNodes.item(i).getAttribute("holdArea");
				}
				
				if (isObj(form1.accountingTitle))	 {
					form1.accountingTitle.value=xmlDoc.documentElement.childNodes.item(i).getAttribute("accountingTitle");
				}
				//帳面價值
				if (isObj(form1.bookUnit))	 {
					form1.bookUnit.value=xmlDoc.documentElement.childNodes.item(i).getAttribute("bookUnit");
				}		
				if (isObj(form1.bookValue))	 {
					form1.bookValue.value=xmlDoc.documentElement.childNodes.item(i).getAttribute("bookValue");
				}
				//舊帳面價值
				if (isObj(form1.oldBookUnit))	 {
					form1.oldBookUnit.value=xmlDoc.documentElement.childNodes.item(i).getAttribute("bookUnit");
				}		
				if (isObj(form1.oldBookValue))	 {
					form1.oldBookValue.value=xmlDoc.documentElement.childNodes.item(i).getAttribute("bookValue");
				}
				//新帳面價值
				if (isObj(form1.newBookUnit))	 {
					form1.oldBookUnit.value=xmlDoc.documentElement.childNodes.item(i).getAttribute("bookUnit");
				}		
				if (isObj(form1.newBookValue))	 {
					form1.oldBookValue.value=xmlDoc.documentElement.childNodes.item(i).getAttribute("bookValue");
				}
				if (isObj(form1.proofDoc))	 {
					form1.proofDoc.value=xmlDoc.documentElement.childNodes.item(i).getAttribute("proofDoc");
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


			}
			if (! bDataExist) {
			  if (strType=="PN") {
			    //alert("PN");		
			  	if (isObj(form1.signNo1)) { form1.signNo1.options[0].selected=true; }
			  	if (isObj(form1.signNo2)) { form1.signNo2.options[0].selected=true; }			  	
			  	if (isObj(form1.signNo3)) { form1.signNo3.options[0].selected=true; }
			  	if (isObj(form1.signNo4)) { form1.signNo4.value=""; }			  	
			  	if (isObj(form1.signNo5)) { form1.signNo5.value=""; }			  				    
			  } 
			  else {
			    //alert("SN");					  
			  	if (isObj(form1.propertyNo)) { form1.propertyNo.value=""; }
			  	if (isObj(form1.serialNo)) { form1.serialNo.value=""; }			  	
			  	if (isObj(form1.propertyName)) { form1.propertyName.value=""; }			    
			  }	
			  //alert("資料不存在");
			  return false;		  
			}
		}else{
			alert('Cant Load It!');
			return false;	
		}
	}	
}

function LPAD(sData, iLen , sAdd) {
        var sformat = "";
		var iDataLen=sData.length;
		for (i=0; i<(iLen-iDataLen) ; i++) {
		  sformat += sAdd;
		}
		sformat += sData;
		return sformat;
}

function getSysDate() {
  var d, s = "";
  d = new Date();
  s += (d.getYear() - 1911);
  s = LPAD(s, 3, "0");
  s += LPAD((d.getMonth() + 1), 2, "0");
  s += LPAD(d.getDate(), 2, "0");
  return(s);

}

function getxmlDataisExistData(queryValue) {
  var i = 0, j=0;
  var xmlDoc=document.createElement("xml");		
  var xmlData="";
  xmlDoc.async=false;			
  if(xmlDoc.load("../../home/xmlDataisExist.jsp?"+queryValue)){
	for(i=0; i<xmlDoc.documentElement.childNodes.length; i++){
	  xmlData = xmlDoc.documentElement.childNodes.item(i).getAttribute("FirstFieldData");
	}
	return xmlData;
  }
  else{
	alert('Cant Load It!');
	return false;	
  }		
}

function getLandIsExist(){
	var alertStr = "";
	alertStr += checkEmpty(form1.enterOrg,"入帳機關");
	alertStr += checkEmpty(form1.ownership,"權屬");
	alertStr += checkEmpty(form1.propertyNo,"財產編號");	
	alertStr += checkEmpty(form1.serialNo,"財產分號");		
	form1.propertyNo.style.backgroundColor="";
	form1.serialNo.style.backgroundColor="";	

	if(alertStr.length==0){

	    //var sSQL1 = "SELECT COUNT(8) FROM UNTLA_LAND" + 	    
	    var sserialNo = LPAD(form1.serialNo.value, 7 , "0");	             
	    var sSQL = "  FROM UNTLA_LAND " +
	               " WHERE ENTERORG='" + form1.enterOrg.value + "'"+
	               "   AND PROPERTYNO='" + form1.propertyNo.value + "'"+
	               "   AND SERIALNO='" + sserialNo + "'"+	               
	               "   AND OWNERSHIP='" + form1.ownership.value + "'";
	               
	    var sSQL1 = "SELECT COUNT(8) " + sSQL;	               
	    var sSQL2 = "SELECT DATASTATE " + sSQL;	               	    
	    var sSQL3 = "SELECT COUNT(8) " + sSQL +
	                "   AND PROPERTYKIND IN ('01','02','03') " ;	               	    	    
	     
		var queryValue = "&sSQLType=CNT&sSQL="+sSQL1;
		var sLandIsExist ="";
        sLandIsExist = getxmlDataisExistData(queryValue);
		if (sLandIsExist=="true") {
		  alert("資料不存在!");
		  return false;
		}       
		
		queryValue = "&sSQLType=DATA&sSQL="+sSQL2;		 
		sLandIsExist = "";
        sLandIsExist = getxmlDataisExistData(queryValue);		
        //alert("減損" + sLandIsExist);
		if (sLandIsExist!="1") {
		  alert("該筆土地資料已經減損，請重新輸入!");
		  return false;		  
		}		
		
		queryValue = "&sSQLType=CNT&sSQL="+sSQL3;		 
		sLandIsExist = "";
        sLandIsExist = getxmlDataisExistData(queryValue);		
        //alert("非為公用土地" + sLandIsExist);        
		if (sLandIsExist=="true") {
		  alert("該筆土地資料非為公用土地，請重新輸入!");
		  return false;		  
		}		
		getValuePriceUnit();		
	}	
}

function getpropertyName(){
	var alertStr = "";
	alertStr += checkEmpty(form1.propertyNo,"財產編號");
	form1.propertyNo.style.backgroundColor="";
	//alert(alertStr);		
	if(alertStr.length==0){
	    var sSQL = "SELECT PROPERTYNAME FROM SYSPK_PROPERTYCODE" +
	               " WHERE PROPERTYNO='" + form1.propertyNo.value +"'";

		var queryValue = "&sSQLType=DATA&sSQL="+sSQL;
        var spropertyName="";
        spropertyName=getxmlDataisExistData(queryValue);
        form1.propertyName.value = spropertyName;			
	}
	else {
	  form1.propertyName.value = "";
	}	
}

function getValuePriceUnit() {
	var alertStr = "";
	alertStr += checkEmpty(form1.recordYM,"統計年月");	
	alertStr += checkEmpty(form1.enterOrg,"入帳機關");
	alertStr += checkEmpty(form1.ownership,"權屬");
	alertStr += checkEmpty(form1.propertyNo,"財產編號");	
	alertStr += checkEmpty(form1.serialNo,"財產分號");		
	form1.recordYM.style.backgroundColor="";	
	form1.propertyNo.style.backgroundColor="";
	form1.serialNo.style.backgroundColor="";	
	if(alertStr.length==0){
	    var srecYM = form1.recordYM.value + "01";
	    var sserialNo = LPAD(form1.serialNo.value, 7 , "0");
	    var sSQL = " WHERE ENTERORG='" + form1.enterOrg.value + "'"+
	               "   AND PROPERTYNO='" + form1.propertyNo.value + "'"+
	               "   AND SERIALNO='" + sserialNo + "'"+	               
	               "   AND OWNERSHIP='" + form1.ownership.value + "'"
	               "   AND '"+ srecYM + "' BETWEEN SUITDATES AND SUITDATEE";
	    var sSQL1 = "SELECT VALUEUNIT FROM UNTLA_VALUE" + sSQL; 
	    var sSQL2 = "SELECT PRICEUNIT FROM UNTLA_PRICE" + sSQL; 
		var queryValue = "&sSQLType=DATA&sSQL="+sSQL1;
        var sData="";
        sData=getxmlDataisExistData(queryValue);
        form1.valueUnit.value = sData;			
        
        queryValue = "&sSQLType=DATA&sSQL="+sSQL2;
        sData=getxmlDataisExistData(queryValue);
        form1.priceUnit.value = sData;			        
	}	
}

function getenterOrgName(){
	var alertStr = "";
	alertStr += checkEmpty(form1.enterOrg,"機關代碼");
	//alert("hi");
	//alert(alertStr);		
	if(alertStr.length==0){
	    var sSQL = "SELECT ORGANSNAME FROM SYSCA_ORGAN" +
	               " WHERE ORGANID='" + form1.enterOrg.value +"'";

		var queryValue = "&sSQLType=DATA&sSQL="+sSQL;
        var senterOrgName="";
        senterOrgName=getxmlDataisExistData(queryValue);
        form1.enterOrgName.value = senterOrgName;			
	}
	else {
	  form1.enterOrgName.value = "";
	}	
}

//*********************************************
//函數功能：找出UNTBU_Building資料表裡的propertyNo,propertyName,serialNo
//參　　數：strType, PN=以財產編號做Lookup, 其它以土地標示代碼做Lookup
//enterOrg,ownership,signNo1,signNo2,signNo3,signNo4,signNo5
//*********************************************
function getUNTBUBuildingPub(strType, sdataState, spropertyKind){
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
			form1.signNo1.style.backgroundColor="";
			form1.signNo2.style.backgroundColor="";
			form1.signNo3.style.backgroundColor="";
			form1.signNo4.style.backgroundColor="";
			form1.signNo5.style.backgroundColor="";		
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
				
		if (isObj(form1.propertyName)) { sPropertyName = form1.propertyName.value; }
		if (isObj(form1.propertyNoName)) { sPropertyName = form1.propertyNoName.value; }		
		
		if (strType=="PN") {		
			queryValue = "enterOrg="+enterOrg+"&ownership="+ownership+"&sType=PN&propertyNo="+sPropertyNo+"&propertyName="+sPropertyName+"&serialNo="+sSerialNo + "&sdataState=" + sdataState + "&spropertyKind=" + spropertyKind;
		} else {
			queryValue = "enterOrg="+enterOrg+"&ownership="+ownership+"&sType=SN&sNo1="+sNo1+"&sNo2="+sNo2+"&sNo3="+sNo3+"&sNo4="+sNo4+"&sNo5="+sNo5 + "&sdataState=" + sdataState + "&spropertyKind=" + spropertyKind;
		}

		var xmlDoc=document.createElement("xml");		
		xmlDoc.async=false;			
		if(xmlDoc.load("../../home/xmlUntbuBuildingPub.jsp?"+queryValue)){		
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
				if (isObj(form1.propertyName)) {
					form1.propertyName.value=xmlDoc.documentElement.childNodes.item(i).getAttribute("propertyName");
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

				if (isObj(form1.doorPlate1)) {
					form1.doorPlate1.value=xmlDoc.documentElement.childNodes.item(i).getAttribute("doorPlate1");
				}
				changeAddr1('doorPlate1','doorPlate2','doorPlate3','');				
				if (isObj(form1.doorPlate2)) {
					form1.doorPlate2.value=xmlDoc.documentElement.childNodes.item(i).getAttribute("doorPlate2");
				}
				changeAddr2('doorPlate1','doorPlate2','doorPlate3','');
				if (isObj(form1.doorPlate3)) {
					form1.doorPlate3.value=xmlDoc.documentElement.childNodes.item(i).getAttribute("doorPlate3");
				}
				if (isObj(form1.doorPlate4)) {
					form1.doorPlate4.value=xmlDoc.documentElement.childNodes.item(i).getAttribute("doorPlate4");
				}
				if (isObj(form1.buildStyle)) {
					form1.buildStyle.value=xmlDoc.documentElement.childNodes.item(i).getAttribute("buildStyle");
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
				if (isObj(form1.floor2)) {
					form1.floor2.value=xmlDoc.documentElement.childNodes.item(i).getAttribute("floor2");
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
				if (isObj(form1.proceedDateS)) {
					form1.proceedDateS.value=xmlDoc.documentElement.childNodes.item(i).getAttribute("proceedDateS");
				}
				if (isObj(form1.proceedDateE)) {
					form1.proceedDateE.value=xmlDoc.documentElement.childNodes.item(i).getAttribute("proceedDateE");
				}
				if (isObj(form1.proceedType)) {
					form1.proceedType.value=xmlDoc.documentElement.childNodes.item(i).getAttribute("proceedType");
				}
				if (isObj(form1.appraiseDate)) {
					form1.appraiseDate.value=xmlDoc.documentElement.childNodes.item(i).getAttribute("appraiseDate");
				}
				/*if (isObj(form1.notes)) {
					form1.notes.value=xmlDoc.documentElement.childNodes.item(i).getAttribute("notes");
				}*/
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
				//抓取建物useSeparate
				getBuilduseSeparate(enterOrg, ownership, form1.propertyNo.value, form1.serialNo.value);
				//抓取建物taxPrice				
				getTaxPrice();
			}
			if (!sFlag) {
				if (isObj(form1.propertyNo)) { form1.propertyNo.value=""; }		
				if (isObj(form1.propertyName)) { form1.propertyName.value=""; }
				if (isObj(form1.serialNo)) { form1.serialNo.value=""; }			
				return false;	
			}
		}else{
/**			alert('Cant Load It!'); **/
			return false;	
		}
	}	
}

function getBuilduseSeparate(enterOrg, ownership, propertyNo, serialNo){
	var alertStr = "";

	if(alertStr.length==0){
	    var sserialNo = LPAD(serialNo, 7 , "0");
	    var sSQL = "SELECT USESEPARATE FROM UNTBU_BASE" + 
	               " WHERE ENTERORG='" + enterOrg + "'"+
	               "   AND PROPERTYNO='" + propertyNo + "'"+
	               "   AND SERIALNO='" + sserialNo + "'"+	               
	               "   AND OWNERSHIP='" + ownership + "'";
	    
	    var queryValue = "&sSQLType=DATA&sSQL="+sSQL;
        var sData="";
        sData=getxmlDataisExistData(queryValue);
        
        form1.useSeparate.value = sData;			        
	}	
}

function getTaxPrice() {
	var alertStr = "";
	alertStr += checkEmpty(form1.recordYM,"統計年月");	
	alertStr += checkEmpty(form1.enterOrg,"入帳機關");
	alertStr += checkEmpty(form1.ownership,"權屬");
	alertStr += checkEmpty(form1.propertyNo,"財產編號");	
	alertStr += checkEmpty(form1.serialNo,"財產分號");		

	form1.recordYM.style.backgroundColor="";		
	form1.propertyNo.style.backgroundColor="";
	form1.serialNo.style.backgroundColor="";	
	if(alertStr.length==0){
	    var srecYM = form1.recordYM.value;
	    var sserialNo = LPAD(form1.serialNo.value, 7 , "0");
	    var sSQL = " WHERE ENTERORG='" + form1.enterOrg.value + "'"+
	               "   AND PROPERTYNO='" + form1.propertyNo.value + "'"+
	               "   AND SERIALNO='" + sserialNo + "'"+	               
	               "   AND OWNERSHIP='" + form1.ownership.value + "'" +
	               "   AND SUBSTR('"+ srecYM + "',1,3)<=TAXYEAR" +
	               " ORDER BY TAXYEAR DESC";
	    var sSQL1 = "SELECT TAXPRICE FROM UNTBU_TAX" + sSQL; 
	    //alert(sSQL1);
		var queryValue = "&sSQLType=DATA&sSQL="+sSQL1;
        var sData="";
        sData=getxmlDataisExistData(queryValue);
        form1.taxPrice.value = sData;			        
	}	
}

function getBuildIsExist(){
	var alertStr = "";
	alertStr += checkEmpty(form1.enterOrg,"入帳機關");
	alertStr += checkEmpty(form1.ownership,"權屬");
	alertStr += checkEmpty(form1.propertyNo,"財產編號");	
	alertStr += checkEmpty(form1.serialNo,"財產分號");		

	form1.propertyNo.style.backgroundColor="";
	form1.serialNo.style.backgroundColor="";	
	if(alertStr.length==0){

	    //var sSQL1 = "SELECT COUNT(8) FROM UNTBU_BUILDING" + 	    
	    var sserialNo = LPAD(form1.serialNo.value, 7 , "0");	             
	    var sSQL = "  FROM UNTBU_BUILDING " +
	               " WHERE ENTERORG='" + form1.enterOrg.value + "'"+
	               "   AND PROPERTYNO='" + form1.propertyNo.value + "'"+
	               "   AND SERIALNO='" + sserialNo + "'"+	               
	               "   AND OWNERSHIP='" + form1.ownership.value + "'";
	               
	    var sSQL1 = "SELECT COUNT(8) " + sSQL;	               
	    var sSQL2 = "SELECT DATASTATE " + sSQL;	               	    
	    var sSQL3 = "SELECT COUNT(8) " + sSQL +
	                "   AND PROPERTYKIND IN ('01','02','03') " ;	               	    	    
	     
		var queryValue = "&sSQLType=CNT&sSQL="+sSQL1;
		var sLandIsExist ="";
        sLandIsExist = getxmlDataisExistData(queryValue);
		if (sLandIsExist=="true") {
		  alert("資料不存在!");
		  return false;
		}       
		
		queryValue = "&sSQLType=DATA&sSQL="+sSQL2;		 
		sLandIsExist = "";
        sLandIsExist = getxmlDataisExistData(queryValue);		
        //alert("減損" + sLandIsExist);
		if (sLandIsExist!="1") {
		  alert("該筆建物資料已經減損，請重新輸入!");
		  return false;		  
		}		
		
		queryValue = "&sSQLType=CNT&sSQL="+sSQL3;		 
		sLandIsExist = "";
        sLandIsExist = getxmlDataisExistData(queryValue);		
        //alert("非為公用土地" + sLandIsExist);        
		if (sLandIsExist=="true") {
		  alert("該筆建物資料非為公用，請重新輸入!");
		  return false;		  
		}		
		//抓取建物useSeparate
		getBuilduseSeparate(form1.enterOrg.value, form1.ownership.value, form1.propertyNo.value, form1.serialNo.value);
		//抓取建物taxPrice				
		getTaxPrice();
	}	
}