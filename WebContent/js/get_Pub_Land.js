//*********************************************
//函數功能：找出UNTLA_LAND資料表裡的propertyNo,propertyName,serialNo
//參　　數：
//一.strType：查詢依据[PN:財產編號;SN:地號]
//二.datastate：資料狀庇
//三.file_name：查無資料清空欄位['N'：不清空]
//*********************************************
function get_Pub_Land(file_name ,strType ,datastate ){
	var alertStr = "", chkStr = "";
	var i = 0, j=0;
	
	if ( strType == 'PN' ){
		/* 判斷財產編號及分號是否有資料 */
		if ( isObj(form1.propertyNo) && isObj(form1.serialNo)){
			if( (form1.propertyNo.value.length != 0) && (form1.serialNo.value.length != 0) ){
				form1.signNo1.value = '';
				form1.signNo2.value = '';
				form1.signNo3.value = '';
				form1.signNo4.value = '';
				form1.signNo5.value = '';
			}else{
				return false;
			}
		}
	}else if( strType == 'SN' ){
		/* 判斷土地標示是否有資料 */
		if ( isObj(form1.signNo1) && isObj(form1.signNo2) && isObj(form1.signNo3) && isObj(form1.signNo4) && isObj(form1.signNo5)){
			if( (form1.signNo1.value.length != 0) && (form1.signNo2.value.length != 0) && (form1.signNo3.value.length != 0) && (form1.signNo4.value.length != 0) && (form1.signNo5.value.length != 0) ){
				form1.propertyNo.value = "";
				form1.propertyNoName.value = "";
				form1.serialNo.value = "";
			}else{
				return false;
			}
		}
	}
	
	/* 判斷機關及權屬是否有資料 */
	var alertStr = "";
	alertStr += checkEmpty(form1.enterOrg,"入帳機關");
	alertStr += checkEmpty(form1.ownership,"權屬");
	if( alertStr.length != 0 ){
		alert(alertStr);
		file_name.value = '';
		return false;
	}
	
	/*  組html傳遞值 
		randomnumber：傳送一個亂數資料,防止瀏灠器由快取直接取得資料導至資料錯誤
	*/
	
	var randomnumber=Math.floor(Math.random()*1000);
	var queryValue = "sType=" + strType ;
		queryValue += "&enterOrg=" + form1.enterOrg.value ;
		queryValue += "&ownership=" + form1.ownership.value ;
		queryValue += "&datastate=" + datastate;
		
	if ( strType == 'PN' ){
		queryValue += "&propertyNo=" + form1.propertyNo.value ;
		queryValue += "&serialNo=" + form1.serialNo.value ;
	}else if( strType == 'SN' ){
		queryValue += "&signNo1=" + form1.signNo1.value ;
		queryValue += "&signNo2=" + form1.signNo2.value ;
		queryValue += "&signNo3=" + form1.signNo3.value ;
		queryValue += "&signNo4=" + form1.signNo4.value ;
		queryValue += "&signNo5=" + form1.signNo5.value ;
	}
		queryValue += "&randomnumber="+randomnumber;
		//alert(queryValue);
		
		var xmlDoc=document.createElement("xml");		
		xmlDoc.async=false;

		if(xmlDoc.load("../../home/xml_Pub_Land.jsp?"+queryValue)){
			j = xmlDoc.documentElement.childNodes.length;
			if( j == 0 ){
				if( file_name != 'N'){
					file_name.value = '';
				}
				alert("查無資料");
			}
			for(i=0; i<j; i++){
				if( strType == 'SN' ){
					if (isObj(form1.propertyNo)) {
					form1.propertyNo.value=xmlDoc.documentElement.childNodes.item(i).getAttribute("propertyNo");
					}
					if (isObj(form1.propertyNoName)) {
					form1.propertyNoName.value=xmlDoc.documentElement.childNodes.item(i).getAttribute("propertyNoName");
					}
					if (isObj(form1.serialNo)) {
					form1.serialNo.value=xmlDoc.documentElement.childNodes.item(i).getAttribute("serialNo");
					}
				}
				
				if (isObj(form1.signNo1)) {
					form1.signNo1.value=xmlDoc.documentElement.childNodes.item(i).getAttribute("signNo1");
				}
				if (isObj(form1.signNo2)) {
					changeSignNo1('signNo1','signNo2','signNo3',xmlDoc.documentElement.childNodes.item(i).getAttribute("signNo2"));
				}
				if (isObj(form1.signNo3)) {
					changeSignNo2('signNo1','signNo2','signNo3',xmlDoc.documentElement.childNodes.item(i).getAttribute("signNo3"))
				}
				if (isObj(form1.signNo4)) {
					form1.signNo4.value=xmlDoc.documentElement.childNodes.item(i).getAttribute("signNo4");
				}
				if (isObj(form1.signNo5)) {
					form1.signNo5.value=xmlDoc.documentElement.childNodes.item(i).getAttribute("signNo5");
				}

				if (isObj(form1.useSeparate)) {  //使用分區
					form1.useSeparate.value=xmlDoc.documentElement.childNodes.item(i).getAttribute("useSeparate");
				}
				if (isObj(form1.addDate)) { //管理機關增帳日期
					form1.addDate.value=xmlDoc.documentElement.childNodes.item(i).getAttribute("enterDate");
				}
				
			}
		}else{
			alert('Cant Load It!');
			return false;	
		}
}