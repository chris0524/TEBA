//*********************************************
//函數功能：找出NPBGR_Person資料表裡的個人基本資料
//*********************************************
function getNPBGR_Person(){
	var alertStr = "";
	var i = 0, j=0;
	var q_applyID = "";
	var q_applyName = "";
	
	if (isObj(form1.applyID)) {
		form1.applyID.value = form1.applyID.value.toUpperCase();
		q_applyID = form1.applyID.value;
	}
	
	if (q_applyID!=null && q_applyID.length>7) {		
		var queryValue = "applyID="+q_applyID;
		var xmlDoc=document.createElement("xml");		
		xmlDoc.async=false;	
		var prop="";
		var windowTop=(document.body.clientHeight-400)/2+180;
		var windowLeft=(document.body.clientWidth-400)/2+250;
		prop=prop+"width=600,height=280,";
		prop=prop+"top="+windowTop+",";
		prop=prop+"left="+windowLeft+",";
		prop=prop+"scrollbars=no";	
		window.open("../../home/xmlNPBGR_Person.jsp?"+queryValue,"",prop);	
	}	
}