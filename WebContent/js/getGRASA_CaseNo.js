//*********************************************
//函數功能：找出GRASA_Case資料表裡的資料
//*********************************************
function getGRASA_Case(){
	var alertStr = "";
	var i = 0, j=0;
	var sEnterOrg  = form1.enterOrg.value;
	var sOwnership = form1.ownership.value;
	var sPropertyNo= form1.propertyNo.value;
	var sSerialNo = form1.serialNo.value;
	if (sPropertyNo !="" && sPropertyNo!= null) {		
		var xmlDoc=document.createElement("xml");		
		xmlDoc.async=false;			
		queryValue = "enterOrg="+sEnterOrg+"&ownership="+sOwnership+"&propertyNo="+sPropertyNo+"&serialNo="+sSerialNo;
		if(xmlDoc.load("../../home/xmlGrasaCase.jsp?"+queryValue)){
			for(i=0; i<xmlDoc.documentElement.childNodes.length; i++){		
				if (isObj(form1.caseNo)) form1.caseNo.value=xmlDoc.documentElement.childNodes.item(i).getAttribute("caseNo");
				if (isObj(form1.payType))	form1.payType.value=xmlDoc.documentElement.childNodes.item(i).getAttribute("payType");				
				if (isObj(form1.payDateS)) form1.payDateS.value=xmlDoc.documentElement.childNodes.item(i).getAttribute("payDateS");				
				if (isObj(form1.payDateE)) form1.payDateE.value=xmlDoc.documentElement.childNodes.item(i).getAttribute("payDateE");		
				if (isObj(form1.holdArea)) form1.holdArea.value=xmlDoc.documentElement.childNodes.item(i).getAttribute("holdArea");				
				if (isObj(form1.holdArea1)) form1.holdArea1.value=xmlDoc.documentElement.childNodes.item(i).getAttribute("holdArea1");	
				if (isObj(form1.holdArea2)) form1.holdArea2.value=xmlDoc.documentElement.childNodes.item(i).getAttribute("holdArea2");
				if (isObj(form1.divideArea)) form1.divideArea.value=xmlDoc.documentElement.childNodes.item(i).getAttribute("holdArea");				
				if (isObj(form1.disArea1)) form1.disArea1.value=xmlDoc.documentElement.childNodes.item(i).getAttribute("holdArea1");	
				if (isObj(form1.disArea2)) form1.disArea2.value=xmlDoc.documentElement.childNodes.item(i).getAttribute("holdArea2");

			}
		} else alert("pp");
	}	
}
//*********************************************
//函數功能：改變欄位屬性
//參數：sfiled1 第一各為欄位屬性,弟2各為欄位顏色,弟3各為className,弟4各為欄位名稱陣列
//*********************************************
function changeFiled(form,sfiled1){
	sfiled = sfiled1[3];
	for (i=0;i<sfiled.length;i++){
		if(sfiled1[2] != ""){
			form.elements[sfiled[i]].className= sfiled1[2];
		}else{
			form.elements[sfiled[i]].style.backgroundColor=sfiled1[1];
			switch (form.elements[sfiled[i]].tagName){
			case "INPUT":
				switch (form.elements[sfiled[i]].type){
					case "text":case "file":case "password":
						form.elements[sfiled[i]].readOnly=eval(sfiled1[0]);
					break;
					case "radio":case "checkbox":case "button":
						form.elements[sfiled[i]].disabled=eval(sfiled1[0]);
					break;
				}
				break;
			case "SELECT":
				form.elements[sfiled[i]].disabled=eval(sfiled1[0]);
			break;
			}
		}
   }
}
