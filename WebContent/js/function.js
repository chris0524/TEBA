/*主要用來取得程式代碼的前三碼, 如unt,npb等 */
var sScript = document.location.pathname;
sScript = sScript.substring(sScript.lastIndexOf("/")).substring(1,4);

var _setAutoCompleteOffFields = "propertyNo,q_propertyNo,q_propertyNoS,q_propertyNoE,serialNo,originalArea,originalHoldNume,originalHoldDeno,originalUnit,signNo4,signNo5,newArea,newHoldNume,newHoldDeno,newBookUnit,originalCArea,originalSArea,originalBV,area,holdNume,holdDeno,equipmentAmount,unitPrice,adjustCArea,adjustSArea,newHoldNume,newHoldDeno,adjustBookValue,originalMeasure,buildDate,adjustMeasure,buyDate,originalAmount,originalUnit,originalPlace,adjustBookAmount,caseNo,originalMoveDate,scrapValue,accumDepr,accumDeprYM";

//*********************************************
//程式功能：判斷物件是否正確
//*********************************************
function isObj(obj){
	return (!((obj==null)||(obj==undefined)));
}

/**
 * 判斷傳入物件是否為funciton
 */
function isFunction(obj) {
	return !!(obj && obj.constructor && obj.call && obj.apply);
}

/**
 * 補強isNaN, 真的不是數字則回傳false, 反之 null, undefined, 空字串, 無限大, 以及無法轉成數值型態 皆回傳true
 * @param val
 * @returns {Boolean}
 */
function isRealNaN(val) {
	if (val === null || val === undefined || val === "") {
		return true;
	} else {
		if (!isNaN(val) && isFinite(val)) {//數值 且 非無限大
			return false;
		} else {
			return true;
		}
	}
}

/**
 * 共用ajax查詢
 * @param url		:	目標ajax路徑, 會自動加 getVirtualPath() + url 所以傳入ex:  ajax/..略/targetAjax.jsp
 * @param data		:	data obj  {'data1' : 'value1', 'data2' : 'value2'}
 * @param async		:	async , 若為false 則為非同步, true 為同步, 預設為同步
 * @param datatype	: datatype, 不傳預設為 json
 */
function ajaxQuery(url, data, async, datatype) {
	var jObj;
	$.ajax({
		url : getVirtualPath() + url,
		data : data,
		type : "POST",
		async : async === false ? false : true,
		dataType: datatype === null || datatype === undefined ? "json" : datatype,
	    success: function(json){
	    	jObj = json;
	    },
	    error:function(xhr, ajaxOptions, thrownError){
	    	alert('ajaxQuery發生錯誤' + thrownError);
	    }
	 });
	return jObj;
}

function isIE() {
	var nav = navigator.userAgent.toLowerCase();
	if ((nav.indexOf("msie") != -1)) return true;
	else return false;
}
function isObjReadOnly(obj) {
	if((obj.type=="text")||(obj.type=="textarea")||(obj.type=="password")){
    	return obj.readOnly;
	}else if((obj.type=="button")||(obj.type=="select-one")||(obj.type=="select-multiple")||(obj.type=="select")||(obj.type=="checkbox")||(obj.type=="radio")){
		return obj.disabled;
   	}
	return true;
}
//*********************************************
var returnWindow = null;
document.onreadystatechange=function(){
	if(document.readyState=="complete"){
		//禁止使用滑鼠右鍵
		//document.body.oncontextmenu=function(){ return false; }
		//本頁開啟(window.open)的所有視窗關閉
		document.body.onunload=function(){
			if (isObj(returnWindow)){ returnWindow.close(); }
		};
	}
};
function closeReturnWindow(){	
	try {	
		if (returnWindow!=null && isObj(returnWindow)){ returnWindow.close(); }
	} catch(e) {}
}
//*********************************************
//程式功能：將小寫轉為大寫,利用onkeypress="toUpper();"事件
//*********************************************
function toUpper(obj){
	if (obj!=null && isObj(obj.value)) {
		obj.value = obj.value.toUpperCase();
	} else {
		var tmp=window.event.keyCode;
		if (tmp>=97 && tmp <=122){ tmp=tmp-32; }
		window.event.keyCode=tmp;		
	}
}
 
//*********************************************
//函數功能：pop萬年曆輔助視窗,並回傳年月日(0920101)
//參　　數：dateField顯示日期的欄位名稱
//傳 回 值：無
//*********************************************
function popCalndar(dateField,js,sY,sM){
	var prop="";
	var windowTop=(document.body.clientHeight-400)/2+180;
	var windowLeft=(document.body.clientWidth-400)/2+250;
	prop=prop+"width=320px,height=250,";
	prop=prop+"top="+windowTop+",";
	prop=prop+"left="+windowLeft+",";
	prop=prop+"scrollbars=no";	
	closeReturnWindow();
	returnWindow=window.open(getVirtualPath() + 'home/calendar.jsp?dateField=' + dateField + '&sY='+sY+'&sM='+sM,'popCalendar',prop);		
	//if (js!=null && js.length>0) eval(js);
}

//*********************************************
//函數功能：pop萬年曆輔助視窗,並回傳年月日(0920101)
//參　　數：dateField1:入帳, dateField2:入帳日期 , dateField3:傳票日期欄位名稱
//傳 回 值：無
//*********************************************
function popCalndar2(dateField1,dateField2,dateField3,js,sY,sM){
	var prop="";
	var windowTop=(document.body.clientHeight-400)/2+180;
	var windowLeft=(document.body.clientWidth-400)/2+250;
	prop=prop+"width=320px,height=250,";
	prop=prop+"top="+windowTop+",";
	prop=prop+"left="+windowLeft+",";
	prop=prop+"scrollbars=no";	
	closeReturnWindow();
	returnWindow=window.open(getVirtualPath() + 'home/calendar2.jsp?dateField1=' + dateField1 + '&dateField2=' + dateField2 + '&dateField3=' + dateField3 + '&sY='+sY+'&sM='+sM,'popCalendar',prop);		
	//if (js!=null && js.length>0) eval(js);
}

//*********************************************
//函數功能：pop機關輔助視窗,並回傳機關代碼及名稱
//參　　數：popOrganID顯示機關代碼的欄位名稱; popOrganName顯示機關名稱的欄位名稱; isLimit是否顯示全部機關名稱
//傳 回 值：無
//*********************************************
function popOrgan(popOrganID,popOrganName,isLimit){
	var prop="";
	var windowTop=(document.body.clientHeight-400)/2+180;
	var windowLeft=(document.body.clientWidth-400)/2+250;
	prop=prop+"width=550,height=420,";
	prop=prop+"top="+windowTop+",";
	prop=prop+"left="+windowLeft+",";
	prop=prop+"scrollbars=no";
	returnWindow=window.open("../../home/popOrgan.jsp?popOrganID="+popOrganID+"&popOrganName="+popOrganName+"&isLimit="+isLimit,"",prop);
}

//*********************************************
//函數功能：pop機關輔助視窗,並回傳機關代碼及名稱
//參　　數：popOrganID顯示機關代碼的欄位名稱; popOrganName顯示機關名稱的欄位名稱; isLimit是否顯示全部機關名稱
//傳 回 值：無
//*********************************************
function popOrgan2(popOrganID,popOrganName,isLimit){
	var prop="";
	var windowTop=(document.body.clientHeight-400)/2+180;
	var windowLeft=(document.body.clientWidth-400)/2+250;
	prop=prop+"width=550,height=420,";
	prop=prop+"top="+windowTop+",";
	prop=prop+"left="+windowLeft+",";
	prop=prop+"scrollbars=no";
	returnWindow=window.open("../../home/popOrgan2.jsp?popOrganID="+popOrganID+"&popOrganName="+popOrganName+"&isLimit="+isLimit,"",prop);
}

//*********************************************
//函數功能：pop機關輔助視窗,並回傳機關代碼及名稱
//參　　數：popOrganID顯示機關代碼的欄位名稱; popOrganName顯示機關名稱的欄位名稱; isLimit是否顯示全部機關名稱
//傳 回 值：無
//*********************************************
function popOrgan3(popOrganID,popOrganName,isLimit){
	var prop="";
	var windowTop=(document.body.clientHeight-400)/2+180;
	var windowLeft=(document.body.clientWidth-400)/2+250;
	prop=prop+"width=550,height=420,";
	prop=prop+"top="+windowTop+",";
	prop=prop+"left="+windowLeft+",";
	prop=prop+"scrollbars=no";
	returnWindow=window.open("../../home/popOrgan3.jsp?popOrganID="+popOrganID+"&popOrganName="+popOrganName+"&isLimit="+isLimit,"",prop);
}

//*********************************************
//函數功能：pop財產編號輔助視窗,並回傳財產編號及名稱
//參　　數：popPropertyNo顯示財產編號的欄位名稱; popPropertyName顯示財產編號名稱的欄位名稱; preWord 財產編號前置詞
//傳 回 值：無
//*********************************************
function popProperty(popPropertyNo,popPropertyName,preWord){
	var prop="";
	var windowTop=(document.body.clientHeight-400)/2+180;
	var windowLeft=(document.body.clientWidth-400)/2+250;
	prop=prop+"width=580,height=450,";
	prop=prop+"top="+windowTop+",";
	prop=prop+"left="+windowLeft+",";
	prop=prop+"scrollbars=no";
	returnWindow=window.open("../../home/popProperty.jsp?popPropertyNo="+popPropertyNo+"&popPropertyName="+popPropertyName+"&preWord="+preWord,"",prop);
}

//*********************************************
//函數功能：財產編號輔助輸入,若preWord除了前置詞若尚有isLookup=Y的參則傳回SYSPK_PropertyCode的所有欄位值,否則只傳回No及Name
//參　　數：popPropertyNo財產編號欄位; popPropertyName財產名稱欄位; preWord 財產編號前置詞
//使用方式:(1)('propertyNo','propertyName','1')
//(2) ('propertyNo','propertyName','1,2,3&isLookup=Y')
//*********************************************
function getProperty(popPropertyNo,popPropertyName,preWord,isCls){
	var arrPreWord = "";
	var isLookup = false;
	var q_enterOrg = "";
	if (parse(preWord).length>0) arrPreWord = preWord.split("&");
	if (arrPreWord.length>1 && arrPreWord[1]=="isLookup=Y,fromAdd=Y") isLookup = true;
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
		
		var q = "1&q_propertyNo="+document.all.item(popPropertyNo).value+"&preWord="+preWord+"&q_enterOrg="+q_enterOrg+"&isCls="+isCls;
		var x = getRemoteData(getVirtualPath() + "ajax/jsonProperty.jsp",q);
		if(x != null && x.length > 0){
			var json = eval('(' + x + ')');
			if(json != null && json.length > 0){
				var i=0;
				for(i=0; i<json.length; i++){
					if (isObj(document.all.item(popPropertyName))) {
						document.all.item(popPropertyName).value=json[i].propertyName;	
					}					
					if (isObj(document.all.item("propertyName1"))) document.all.item("propertyName1").value=json[i].propertyName1;
					if (isObj(document.all.item("propertyType"))) document.all.item("propertyType").value=json[i].propertyType;
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
					
					// 使用年限
					if (isObj(document.all.item("originalLimitYear"))) {
						if (!isNaN(json[i].limitYear)) {
							document.all.item("originalLimitYear").value = json[i].limitYear;
						} else {
							document.all.item("originalLimitYear").value = '';
						}
					}

					// 使用年限
					if (isObj(document.all.item("limitYear"))) {
						if (!isNaN(json[i].limitYear)) {
							document.all.item("limitYear").value = json[i].limitYear;
						} else {
							document.all.item("limitYear").value = '';
						}
					}
					
					// 使用年限(月)
					if (isObj(document.all.item("otherLimitYear"))) {
						if (!isNaN(json[i].limitYear)) {
							document.all.item("otherLimitYear").value = json[i].limitYear * 12;
						} else {
							document.all.item("otherLimitYear").value = '';
						}
					}
				}
			}
			else{
				if (isCls!="N") {
					if (isObj(document.all.item(popPropertyNo))) document.all.item(popPropertyNo).value="";	
					if (isObj(document.all.item(popPropertyName))) document.all.item(popPropertyName).value="";
					if (isObj(document.all.item("propertyName1"))) document.all.item("propertyName1").value="";
					if (isObj(document.all.item("propertyType"))) document.all.item("propertyType").value="";
					if (isObj(document.all.item("propertyUnit"))) document.all.item("propertyUnit").value="";
					if (isObj(document.all.item("material"))) document.all.item("material").value="";
					if (isObj(document.all.item("limitYear"))) document.all.item("limitYear").value="";
					if (isObj(document.all.item("otherLimitYear"))) document.all.item("otherLimitYear").readOnly=false;
				}
			}
		}
		
	} else { 
		if (isCls!="N") {	
			document.all.item(popPropertyNo).value="";
			document.all.item(popPropertyName).value="";
		}
	}
	
	if (arrPreWord.length>2 && arrPreWord[2]!="") eval(arrPreWord[2].substring(arrPreWord[2].indexOf("=")+1));
}

//*********************************************
//函數功能：pop廠商輔助視窗,並回傳廠商編號及名稱
//參　　數：popStoreNo顯示廠商代碼的欄位名稱; popStoreName顯示廠商名稱的欄位名稱
//傳 回 值：無
//*********************************************
function popStore(popStoreNo,popStoreName){
	var prop="";
	prop=prop+"width=630,height=560,";
	prop=prop+"top=50,";
	prop=prop+"left=300,";
	prop=prop+"scrollbars=no";
	returnWindow=window.open("../../unt/ba/untba001f.jsp?popStoreNo="+popStoreNo+"&popStoreName="+popStoreName,"",prop);
}

//*********************************************
//函數功能：pop文號輔助視窗,並回傳文號編號及名稱
//參　　數：popDocNo顯示文號代碼的欄位名稱; popDocName顯示文號名稱的欄位名稱
//傳 回 值：無
//*********************************************
function popDocNo(popDocNo,popDocName){
	var prop="";
	prop=prop+"width=630,height=450,";
	prop=prop+"top=150,";
	prop=prop+"left=300,";
	prop=prop+"scrollbars=no";
	returnWindow=window.open("../../unt/ba/untba002f.jsp?popDocNo="+popDocNo+"&popDocName="+popDocName,"",prop);
}

//*********************************************
//函數功能：pop保管單位輔助視窗,並回傳文號編號及名稱
//參　　數：popUnitNo顯示保管單位代碼的欄位名稱; popUnitName顯示保管單位名稱的欄位名稱
//傳 回 值：無
//*********************************************
function popKeepUnit(popUnitNo,popUnitName){
	var prop="";
	prop=prop+"width=630,height=450,";
	prop=prop+"top=150,";
	prop=prop+"left=300,";
	prop=prop+"scrollbars=no";
	returnWindow=window.open("../../unt/ba/untba003f.jsp?popUnitNo="+popUnitNo+"&popUnitName="+popUnitName,"",prop);
}
//*********************************************
//函數功能：pop保管單位輔助視窗,並回傳文號編號及名稱
//參　　數：queryValue顯示保管單位代碼的欄位名稱; popUnitNo顯示保管單位名稱的欄位名稱
//傳 回 值：無
//*********************************************
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
//*********************************************
//函數功能：pop保管人輔助視窗,並回傳文號編號及名稱
//參　　數：queryValue顯示保管人代碼的欄位名稱; popUnitMan顯示保管人名稱的欄位名稱
//傳 回 值：無
//*********************************************
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
//*********************************************
//函數功能：pop存置地點輔助視窗,並回傳文號編號及名稱
//參　　數：queryValue顯示機關代碼的欄位名稱; popPlace顯示存置地點代碼的欄位名稱;popPlaceName顯示存置地點的欄位名稱
//傳 回 值：無
//*********************************************
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
//*********************************************
//函數功能：pop工程編號輔助視窗,並回傳文號編號及名稱
//參　　數：queryValue顯示機關代碼的欄位名稱; popEngineering顯示工程編號代碼的欄位名稱;popEngineeringName顯示工程編號的欄位名稱
//傳 回 值：無
//*********************************************
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
//*********************************************
//函數功能：顯示後端處理錯誤訊息
//參　　數：錯誤訊息
//傳 回 值：無
//*********************************************
function showErrorMsg(error){
	var msg=error;		
	if(msg !=null && msg.length!=0){
		var strMsg = "新增完成,修改完成,刪除完成,修改完成，已取得最新資料";
		var sFlag = false;
		try {	
			var arrMsg = strMsg.split(",");
			for (var i=0; i<arrMsg.length; i++) {
				if (arrMsg[i]==msg) sFlag = true;
			}
			if (sFlag) top.frames['title'].showBoxMsg(msg);
			else alert(msg);
		} catch(e) {		
		  alert(msg);	
		}
		try {
			listContainerLoadActiveRow();
		} catch(e) {		
		  //alert(msg);	
		}
	} else {
		try {	
			listContainerLoadActiveRow();
			if (isObj(top.frames['title'])) top.frames['title'].showBoxMsg(msg);
		} catch(e) {
		}
	}
	if (isObj(form1.permissionField)) doPermission(form1.permissionField.value); 
	return false;
} 

//*********************************************
//函數功能：權限按鈕設定，這是用東牆補西牆的做法，千萬別把它用到別的系統
//*********************************************
function doPermission(s) {
	try {
		if (s!=null && s=="Q")
	      setDisplayItem('spanInsert,spanUpdate,spanDelete,spanClear,spanConfirm,spanNextInsert,btnMaintain,btnMaintain1,btnMaintain2,btnMaintain3,btnMaintain4,btnMaintain5,approveAll','H');
	      setDisplayItem('spanAllInsert,spanBatchInsertBut,spanVerifyRe,spanID,spanUntmp003f,spanopenUpdate,spanUntmp023f,','H');
	} catch(e) {}
}
//*********************************************
//函數功能：由身分証號碼的判斷男女
//參　　數：IDcolumn身分証號碼;Sexcolumn性別欄位
//傳 回 值：
//*********************************************
function ChangeSex(IDcolumn,Sexcolumn){
	if( IDcolumn.value.length >= 2 ){
		Sexcolumn.value=IDcolumn.value.substr(1,1);
	}  
}
//*********************************************
//函數功能：取得下拉選項的預設值
//參　　數：elem:欄位物件;val:預設值
//傳 回 值：
//*********************************************
function getSelectedValue(elem,val) {
	var i=0;
	if (val !=""){
		for (i=0;i< elem.length;i++){
			if (elem.options[i].value==val)	{
				elem.options[i].selected=true;
				return;
		    }
		}
	}
}

//*********************************************
//程式功能：取得現在時間（HHMMSS）
//*********************************************
function getNow(){
	var x = getRemoteData(getVirtualPath() + "ajax/jsonDatetime.jsp", "");
	if (x!=null && x.length>0) {
		var json = eval('(' + x + ')'); 
		return json.HHMMSS;
	}
	/**
	myTime = new Date();
	var myHour = myTime.getHours().toString();
	var myMinute = myTime.getMinutes().toString();
	var mySecond = myTime.getSeconds().toString();

	if (myHour.length<=1){ myHour="0"+myHour; }
	if (myMinute.length<=1){ myMinute="0"+myMinute; }
	if (mySecond.length<=1){ mySecond="0"+mySecond; }	
	myNow = myHour + myMinute + mySecond;
	return myNow;
	**/
}

//*********************************************
//程式功能：計算兩個日期相差天數（含起始日）
//*********************************************
function getDayDiff(fDate, sDate){
	var new_fDate = new Date(parseInt(fDate.value.substring(0,3),10)+1911, 
							 parseInt(fDate.value.substring(3,5),10)-1, 
							 fDate.value.substring(5,7));
	var new_sDate = new Date(parseInt(sDate.value.substring(0,3),10)+1911, 
							 parseInt(sDate.value.substring(3,5),10)-1, 
							 sDate.value.substring(5,7));
	return ((new_sDate-new_fDate)/(1000*60*60*24))+1 ;
}

function getNumeric(s) {
	if (s==null || s=="" || isNaN(parseFloat(s))) return 0;
	else return parseFloat(s);
}

//*********************************************
//程式功能：取得虛擬路徑,return "../../"
//*********************************************
function getVirtualPath(){
	var pathName=window.location.pathname;		
	if (pathName.substring(0,1)=="/"){
		pathName=pathName.substring(1,pathName.length);
	}
	var pathArray=pathName.split("/");			
	var programLayer=pathArray.length - 2 ;//2	
	var virtualPath="";
	for(var i=0; i<programLayer; i++){
		virtualPath=virtualPath+"../";
	}
	return virtualPath;
}

//*********************************************
//函數功能：將檔名路徑改為javascript寫法
//參　　數：strPath:原始檔名路徑
//傳 回 值：
//*********************************************
function getDoublePath(strPath){
	var strReturn="";
	var i;
	var start=0;
	for(i=0; i<strPath.length; i++){
		if ("\\"==strPath.substring(i,i+1)){
			strReturn=strReturn + strPath.substring(start,i) +"\\";
			start=i;
		}
	}
	strReturn=strReturn + strPath.substring(start,strPath.length) ;
	return strReturn;
}

function deleteCommas( obj ) {
	return deleteChr(obj,',');
}

function deleteChr(obj, targetChr) {
  var str = "", rStr="";
  var flag = false;
	if (isObj(obj.value)) {
	    str = obj.value;
	    flag = true;
	} else str = obj;
			
	var ch;
	if (str!=null && str!="" && str.length>0) {
		for(var i=0;i<str.length;i++){
			ch = str.charAt(i);
			if(ch!=targetChr) rStr+=str.charAt(i);
		}
	}
  if (flag) obj.value = rStr;
  else return rStr;
}

function addCommas( obj ) {
    var s = "";
    var flag = false;
	if (obj!=null && isObj(obj)) {	
		if (isObj(obj.value)) {
		    s = obj.value;
		    flag = true;
		} else s = ""+obj;
	} else return "";
	
    if (s!=null && s.length>0) {
    	s = deleteCommas(s);
        var sValue = s;
        var r = "";
        var arr = s.split(".");
        if (arr!=null && arr.length>0) {
            sValue = arr[0];
            if (arr.length>1) r = "." + arr[1];
	        var sRegExp = new RegExp('(-?[0-9]+)([0-9]{3})'); 
	        while(sRegExp.test(sValue)) { 
		        sValue = sValue.replace(sRegExp, '$1,$2'); 
	        }
	        if (flag) obj.value = sValue + r;
	        else return sValue + r;
        }
    }
    return s;
}

//*********************************************
//函數功能：土地標示, 改變第一層
//參　　數：signNo1縣市; signNo2鄉鎮區;signNo3段/小段;selectValue所選的值
//傳 回 值：無
//*********************************************
function changeSignNo1(signNo1,signNo2,signNo3,selectValue){
	var queryValue = document.all.item(signNo1).value;
	
	//清除signNo2
	if (isObj(document.all.item(signNo2))) {
		$("select[name='" + signNo2 + "'] option").remove();
		$("select[name='" + signNo2 + "']").append($("<option></option>").attr("value", "").text("請選擇"));	
	}
	
	//清除signNo3
	if (isObj(document.all.item(signNo3))) {		
		$("select[name='" + signNo3 + "'] option").remove();
		$("select[name='" + signNo3 + "']").append($("<option></option>").attr("value", "").text("請選擇"));			
	}	
	
	if (queryValue!=""){
		var comment={};	
		comment.signNo1 = queryValue;
				
		$.post('../../home/xmlSign.jsp',
				comment,
				function(data){
					//將回傳資料寫入
					data=eval('('+data+')');

					var NumOfData = data.length;
					
					$("select[name='" + signNo2 + "'] option").remove();
					$("select[name='" + signNo2 + "']").append($("<option></option>").attr("value", "").text("請選擇"));
					for (var i = 0; i < NumOfData; i++) {
						$("select[name='" + signNo2 + "']").append($("<option></option>").attr("value", data[i]['signNo']).text(data[i]['signName']));
						
						if(data[i]['signNo'] == selectValue){
							$("select[name='" + signNo2 + "']")[0].selectedIndex = (i+1);							
						}
					}
					
				});	
		
	}	
}
	
function queryDeprUnitDataforDeprUnit1(enterOrg,deprUnit,deprUnit1,selectValue){
	var q_enterOrg = document.all.item(enterOrg).value;
	var q_deprUnit = document.all.item(deprUnit).value;
	
	//清除deprUnit1
	if (isObj(document.all.item(deprUnit1))) {
		$("select[name='" + deprUnit1 + "'] option").remove();
		$("select[name='" + deprUnit1 + "']").append($("<option></option>").attr("value", "").text("請選擇"));	
	}
	
	if(q_enterOrg != '' && q_deprUnit != ''){
		//傳送JSON
		var comment={};	
		comment.q_enterOrg = q_enterOrg;
		comment.q_deprUnit = q_deprUnit;
		
		$.post('../../home/xmlDeprUnit1.jsp',
				comment,
				function(data){
					//將回傳資料寫入
					data=eval('('+data+')');

					var NumOfData = data.length;
					
					$("select[name='" + deprUnit1 + "'] option").remove();
					$("select[name='" + deprUnit1 + "']").append($("<option></option>").attr("value", "").text("請選擇"));
					for (var i = 0; i < NumOfData; i++) {
						$("select[name='" + deprUnit1 + "']").append($("<option></option>").attr("value", data[i]['deprunit1no']).text(data[i]['deprunit1name']));
						if(data[i]['deprunit1no'] == selectValue){
							$("select[name='" + deprUnit1 + "']")[0].selectedIndex = (i+1);
						} else if (i == 0) {
							$("select[name='" + deprUnit1 + "']")[0].selectedIndex = 1;
						}
					}
					
		});	
	}
}
function queryDeprUnitDataforDeprAccounts(enterOrg,deprUnit,deprAccounts,selectValue){
	var q_enterOrg = document.all.item(enterOrg).value;
	var q_deprUnit = document.all.item(deprUnit).value;
	
	//清除deprUnit1
	if (isObj(document.all.item(deprAccounts))) {
		$("select[name='" + deprAccounts + "'] option").remove();
		$("select[name='" + deprAccounts + "']").append($("<option></option>").attr("value", "").text("請選擇"));	
	}
	
	if(q_enterOrg != '' && q_deprUnit != ''){
		//傳送JSON
		var comment={};	
		comment.q_enterOrg = q_enterOrg;
		comment.q_deprUnit = q_deprUnit;
		
		$.post('../../home/xmldeprAccounts.jsp',
				comment,
				function(data){
					//將回傳資料寫入
					data=eval('('+data+')');

					var NumOfData = data.length;
					
					$("select[name='" + deprAccounts + "'] option").remove();
					$("select[name='" + deprAccounts + "']").append($("<option></option>").attr("value", "").text("請選擇"));
					for (var i = 0; i < NumOfData; i++) {
						$("select[name='" + deprAccounts + "']").append($("<option></option>").attr("value", data[i]['depraccountsno']).text(data[i]['depraccountsname']));
						
						if(data[i]['depraccountsno'] == selectValue){
							$("select[name='" + deprAccounts + "']")[0].selectedIndex = (i+1);
						} else if (i == 0) {
							$("select[name='" + deprAccounts + "']")[0].selectedIndex = 1;
						}
					}
					
		});	
	}
}

//*********************************************
//函數功能：土地標示, 改變第二層
//參　　數：signNo1縣市; signNo2鄉鎮區;signNo3段/小段;selectValue所選的值
//傳 回 值：無
//*********************************************	
function changeSignNo2(signNo1,signNo2,signNo3,selectValue){
	var queryValue = document.all.item(signNo2).value;
	
	//清除signNo3
	if (isObj(document.all.item(signNo3))) {		
		$("select[name='" + signNo3 + "'] option").remove();
		$("select[name='" + signNo3 + "']").append($("<option></option>").attr("value", "").text("請選擇"));			
	}	
	
	if (queryValue!="" || selectValue!=""){
		var comment={};
		if(queryValue!=""){		comment.signNo2 = queryValue;
		}else{					comment.signNo2 = selectValue;
		}
				
		$.post('../../home/xmlSign.jsp',
				comment,
				function(data){
					//將回傳資料寫入
					data=eval('('+data+')');

					var NumOfData = data.length;
					
					$("select[name='" + signNo3 + "'] option").remove();
					$("select[name='" + signNo3 + "']").append($("<option></option>").attr("value", "").text("請選擇"));
					
					for (var i = 0; i < NumOfData; i++) {
						$("select[name='" + signNo3 + "']").append($("<option></option>").attr("value", data[i]['signNo']).text(data[i]['signName']));
						
						if(data[i]['signNo'] == selectValue){
							$("select[name='" + signNo3 + "']")[0].selectedIndex = (i+1);							
						}						
					}
				});
	}		
}


//*********************************************
//函數功能：地址, 改變第一層
//參　　數：addr1縣市; addr2鄉鎮區;addr3村里;selectValue所選的值
//傳 回 值：無
//*********************************************
function changeAddr1(addr1,addr2,addr3,selectValue){
	var queryValue=document.all.item(addr1).value;
	
	//清除addr2
	if (isObj(document.all.item(addr2))) {		
		var obj2 = document.all.item(addr2);
		obj2.options.length=0;
		var obj2Option = document.createElement("Option");
		obj2.options.add(obj2Option);
		obj2Option.innerText = "請選擇";
		obj2Option.value = "";	
		//清除addr3
		if (isObj(document.all.item(addr3))) {		
			var obj3 = document.all.item(addr3);
			obj3.options.length=0;
			var obj3Option = document.createElement("Option");	
			obj3.options.add(obj3Option);
			obj3Option.innerText = "請選擇";
			obj3Option.value = "";			
		}	
		if (queryValue!=""){
			var xmlDoc=document.createElement("xml");	
			xmlDoc.async=false;			
			if(xmlDoc.load("../../home/xmlAddr.jsp?addr1="+queryValue)){		
				var nodesLen=xmlDoc.documentElement.childNodes.length;
				for(var i=0; i<nodesLen; i++){
					addrID=xmlDoc.documentElement.childNodes.item(i).getAttribute("addrID");
					addrName=xmlDoc.documentElement.childNodes.item(i).getAttribute("addrName");
					var oOption = document.createElement("Option");	
					obj2.options.add(oOption);
					oOption.innerText = addrName;
					oOption.value = addrID;		
			      	if(addrID == selectValue){
	        			oOption.selected=true;
					}           										
				}
			}else{
				return false;	
			}
		}		
	}
	
}


//*********************************************
//函數功能：地址, 改變第二層
//參　　數：addr1縣市; addr2鄉鎮區;addr3村里;selectValue所選的值
//傳 回 值：無
//*********************************************
function changeAddr2(addr1,addr2,addr3,selectValue){
	var queryValue=document.all.item(addr2).value;	
	
	//清除addr3
	if (isObj(document.all.item(addr3))) {		
		var obj3 = document.all.item(addr3);
		obj3.options.length=0;
		var obj3Option = document.createElement("Option");	
		obj3.options.add(obj3Option);
		obj3Option.innerText = "請選擇";
		obj3Option.value = "";	
		if (queryValue!=""){
			var xmlDoc=document.createElement("xml");	
			xmlDoc.async=false;			
			if(xmlDoc.load("../../home/xmlAddr.jsp?addr2="+queryValue)){		
				var nodesLen=xmlDoc.documentElement.childNodes.length;
				for(var i=0; i<nodesLen; i++){
					addrID=xmlDoc.documentElement.childNodes.item(i).getAttribute("addrID");
					addrName=xmlDoc.documentElement.childNodes.item(i).getAttribute("addrName");
					if (isObj(form1.zipcode)) {
						if (xmlDoc.documentElement.childNodes.item(i).getAttribute("zipcode")!="")	form1.zipcode.value = xmlDoc.documentElement.childNodes.item(i).getAttribute("zipcode");
					}
					var oOption = document.createElement("Option");	
					obj3.options.add(oOption);
					oOption.innerText = addrName;
					oOption.value = addrID;		
			      	if(addrID == selectValue){
	        			oOption.selected=true;
					}           										
				}
			}else{
				return false;	
			}
		}		
	}		
}


//*********************************************
//函數功能：取小數位數
//參　　數：v數值; dp小數位數,isUp是否是捨五入
//傳 回 值：無
//*********************************************
function roundp(v, dp , isUp) { 
	if(v!=null && v!=""){
		v = ""+v;		
		if(v.indexOf('.') != -1 ){	
			if(v.substr(0,v.indexOf("."))==""){
				v="0"+v;
			}
		}
	}
	var v1 , v2, sdp ="" ,dp1;
	var sh = Math.pow(10 , dp);
	if(!isNaN(parseInt(v))){
		v = ""+v;		
		sdp = (""+sh).substr(1);	
		if (v.indexOf('.') == -1 ){					 
			if(dp > 0) v = v+'.'+sdp;
		}else{
			v1 = v.substr(0,v.indexOf(".")); //整數部分
			v2 = v.substr(v1.length+1,v.length); //小數部分 
			if(isUp != "Y"){
				v = v1 + "." + (v2 + sdp).substr(0,dp);
			}else{
				v2 = "0."+ v2 ;
				dp1 = ""+(Math.round(v2 * sh) / sh) ;	
				if((Math.round(v2 * sh)) == sh){
					v1 = parseInt(v1)+1+"";
				}
				if (dp1.indexOf('.') == -1 ){
					v = v1 +'.' +sdp;
				}else{
					var dp1ln = dp1.substring(dp1.indexOf('.')+1,dp1.length).length;
					if(dp1ln < dp){
						sdp = "";
						for (var i = dp1ln ; i < dp ; i++) sdp = sdp + "0";
						dp1 = dp1 + sdp;					
					}					
					v = v1 +'.' + dp1.substr(2);
				}		
			}
		}
		return v ;
	}
	return "";
} 

//*********************************************
//函數功能：去除逗號 
//eg. 100,999.99 --> 100999.99
//eg. 一千,兩佰 --> 一仟兩佰
//*********************************************
function deleteCommas( obj ) {
	return deleteChr(obj,',');
}
function deleteChr(obj, targetChr) {
var str = "", rStr="";
var flag = false;
	if (isObj(obj.value)) {
	    str = obj.value;
	    flag = true;
	} else str = obj;
			
	var ch;
	if (str!=null && str!="" && str.length>0) {
		for(var i=0;i<str.length;i++){
			ch = str.charAt(i);
			if(ch!=targetChr) rStr+=str.charAt(i);
		}
	}
if (flag) obj.value = rStr;
else return rStr;
}

//*********************************************
//函數功能：將數字欄位加千分號
//參　　數：HTMLText field 或數值
//傳 回 值：若為數值則回傳加千分號後的數值,若為HTMLText field則直接格式化該欄位
//*********************************************
function addCommas( obj ) {
  var s = "";
  var flag = false;
	if (obj!=null && isObj(obj)) {	
		if (isObj(obj.value)) {
		    s = obj.value;
		    flag = true;
		} else s = ""+obj;
	} else return "";
	
  if (s!=null && s.length>0) {
  	s = deleteCommas(s);
      var sValue = s;
      var r = "";
      var arr = s.split(".");
      if (arr!=null && arr.length>0) {
          sValue = arr[0];
          if (arr.length>1) r = "." + arr[1];
	        var sRegExp = new RegExp('(-?[0-9]+)([0-9]{3})'); 
	        while(sRegExp.test(sValue)) { 
		        sValue = sValue.replace(sRegExp, '$1,$2'); 
	        }
	        if (flag) obj.value = sValue + r;
	        else return sValue + r;
      }
  }
  return s;
}

function getNumeric(s) {
	if (s==null || s=="" || isNaN(deleteCommas(s))) return 0;
	else return parseFloat(deleteCommas(s));
}

//*********************************************
//函數功能：比較兩個日期
//參　　數：objDateS,objDateE為起訖日期物件本身, strType可以是d(Day),m(Month),y(Year)
//傳 回 值：假如正確是傳回一字串值;假如錯誤則傳回空字串
//*********************************************
function getDateDiff(sType, objDateS, objDateE ){
	var sdate, edate;
	var dates, datee;
	var one_day=1000*60*60*24;
	var strType;

	if (isObj(sType.value)) strType = parse(sType.value);
	strType = parse(sType);
		
	if (isObj(objDateS.value)) sdate = parse(objDateS.value);
	else sdate = parse(objDateS);
	
	if (isObj(objDateE.value)) edate = parse(objDateE.value);
	else edate = parse(objDateE);	
	
	if( sdate.length==7 && edate.length==7 && strType.length>0){
		dates = new Date(parseInt(sdate.substring(0,3),10)+1911, sdate.substring(3,5)-1, sdate.substring(5,7));
		datee = new Date(parseInt(edate.substring(0,3),10)+1911, edate.substring(3,5)-1, edate.substring(5,7));
		if (strType=="d") {
			return (Math.ceil((datee-dates)/one_day));
		} else if (strType=="y") {
			return (parseInt(edate.substring(0,3),10)-parseInt(sdate.substring(0,3),10));
		} else if (strType=="m") {
			var sMonth = dates.getMonth() + (dates.getFullYear() * 12);
			var eMonth = datee.getMonth() + (datee.getFullYear() * 12);		
			return (eMonth-sMonth);			
		}		
	} else {
		return "";
	}
}

//*********************************************
//函數功能：某一日期加上一定期間的日或月或年
//參　　數：strType可以是d(Day),m(Month),y(Year); sNum數值; objDate為日期物件或日期
//傳 回 值：傳回加上特定期間之後的日期
//*********************************************
function getDateAdd(sType, sNum, objDate){
	var sdate = "";
	var rdate = new Date();
	var myYear, myMonth, myDay;
	var intNumber;
	var sNumber;
	var intYear, intMon, intDay;
	var strType;

	if (isObj(sType.value)) strType = parse(sType.value);
	strType = parse(sType);
		
	if (isObj(sNum.value)) sNumber = parse(sNum.value);
	else sNumber = sNum;
	
	if (objDate!=null && isObj(objDate.value)) sdate = parse(objDate.value);
	else sdate = parse(objDate);
		
	if (sNumber=="") sNumber = 0;	
	if (isNaN(sNumber)) intNumber = 0;
	else intNumber = parseInt(sNumber,10);

	if(sdate.length==7 && strType.length>0) {
		intYear = parseInt(sdate.substring(0,3),10)+1911;
		intMon = parseInt(sdate.substring(3,5),10)-1;
		intDay = parseInt(sdate.substring(5,7),10);	
		if (strType=="d") {
			rdate = new Date(intYear, intMon, intDay+intNumber);
		} else if (strType=="m") {
			rdate = new Date(intYear, intMon+intNumber, intDay);
		} else if (strType=="y") {
			rdate = new Date(intYear+intNumber, intMon, intDay);
		}
		//the bullshit js trancate 19xx to xx
		if (rdate.getYear()<2000) myYear = (rdate.getYear()-11).toString();
		else myYear = (rdate.getYear()-1911).toString();				
		//myYear = (rdate.getYear()-1911).toString();
		myMonth = (rdate.getMonth()+1).toString();
		myDay = rdate.getDate().toString();
			
		if (myYear.length<=2){ myYear="0"+myYear; }
		if (myMonth.length<=1){ myMonth="0"+myMonth; }
		if (myDay.length<=1){ myDay="0"+myDay; }	
		myToday = myYear + myMonth + myDay;
		return myToday;
		
	} else {
		return "";
	}		
}


function openUploadWindow(popFileID, popFileName, popFileID_1){
	if (form1.state.value=="insert"){
		popFileName="";
	}
	if (isObj(eval("form1."+popFileID))) {
	    var prop='';
	    prop=prop+'toolbar=no;location=no,directories=no,menubar=no,status=no,scrollbars=yes,resizable=yes,';
	    prop=prop+'width=450,';
	    prop=prop+'height=160';
	    window.open('../../home/popUploadSimple.jsp?popFileID=' + popFileID + '&popFileName='+popFileName+ '&popFileID_1=' + popFileID_1,'上傳檔案',prop);
	} else {
		alert("欄位不存在,請檢查!");
		return ;
	}
}

function openDownloadWindow(fileID, fileName){
    var prop='';
    prop=prop+'toolbar=no;location=no,directories=no,menubar=no,status=yes,scrollbars=yes,resizable=yes,';
    prop=prop+'width=400,';
    prop=prop+'height=400';
	if (parse(fileID).length>0) {
		var arrFileName = fileID.split(":;:");
		if (arrFileName.length>1) {
		    var url = "../../downloadFileSimple?fileID=" + fileID;    
		    window.open(url,'popDownload',prop);
		} else {
			alert("無法下載該檔案，因為檔案資訊不完整，若問題持續，請洽系統管理!");
		}		
	} else if (parse(fileName).length>0) {
	    var url = "../../downloadFileSimple?fileName=" + fileName;    
		window.open(url,'popDownload',prop);		
	} else {
		alert("目前沒有任何檔案可供下載!");	
	}
}

function openDownloadFileWindow(fileName)
{
	
    var prop='';
    prop=prop+'toolbar=no;location=no,directories=no,menubar=no,status=yes,scrollbars=yes,resizable=yes,';
    prop=prop+'width=400,';
    prop=prop+'height=400';
    
	if (parse(fileName).length>0) 
	{
	    var url = "../../downloadFile?fileName=" + fileName;    
		window.open(url,'popDownload',prop);		
	} 
	else 
	{
		alert("目前沒有任何檔案可供下載!");	
	}
}


//刪除上傳檔案
function delectUpload(fileID){
	var xmlDoc=document.createElement("xml");		
		xmlDoc.async=false;
		xmlDoc.load("../../home/delectUpload.jsp?fileID=" + fileID);
	
}

function openDownloadWindow_Cri(fileID, fileName){
    var prop='';
    prop=prop+'toolbar=no;location=no,directories=no,menubar=no,status=yes,scrollbars=yes,resizable=yes,';
    prop=prop+'width=400,';
    prop=prop+'height=400';
	if (parse(fileID).length>0) {
		var arrFileName = fileID.split(":;:");
		if (arrFileName.length>1) {
		    var url = "../../downloadFileSimple?fileID=" + fileID;    
		    window.open(url,'popDownload',prop);
		} else {
			alert("無法下載該檔案，因為檔案資訊不完整，若問題持續，請洽系統管理!");
		}		
	} else if (parse(fileName).length>0) {
	    var url = "../../downloadFileSimple?fileName=" + fileName;    
		window.open(url,'popDownload',prop);		
	} else {
		alert("目前沒有任何檔案可供下載!");	
	}
}

//非公用裡常用到, 所以放一個在這裡
function selectProperty(intType) {
	var propertyType = "";
	if (parse(form1.q_propertyType.value).length>0) propertyType=form1.q_propertyType.value;
	else propertyType = "1,2";
		
	switch (intType) {
		case "1":
			popProperty('q_propertyNoS','q_propertyNoSName',propertyType);
			break;
		case "2":
			popProperty('q_propertyNoE','q_propertyNoEName',propertyType);
			break;
	}
}

//js classes
 function StringBuffer() { 
   this.buffer = []; 
 }
 StringBuffer.prototype.append = function append(string) { 
   this.buffer.push(string);
   return this; 
 }; 
 StringBuffer.prototype.toString = function toString() { 
   return this.buffer.join(""); 
 };  
 
 String.prototype.trim = function() { return this.replace(/^\s+|\s+$/, ''); };
 String.prototype.startsWith = function(str) {return (this.match("^"+str)==str);};
 String.prototype.endsWith = function(str) {return (this.match(str+"$")==str);};
 String.prototype.replaceAll = function(s1,s2){return this.replace(new RegExp(s1,"gm"),s2);};

 /*模仿sql like功能*/
 function like(buttonName,likestr){
 	return (buttonName.indexOf(likestr)>=0);
 }
 function setObjectValue(objName,v) {
	 if (isObj(document.all.item(objName))) document.all.item(objName).value = v;
 }
 function getObjectValue(objName) {
	if (isObj(document.all.item(objName))) return document.all.item(objName).value;
}
 
 
function popBulletinDate(popField,preWord,isLookup,js){
	var prop="";
	var windowTop=(document.body.clientHeight-400)/2+180;
	var windowLeft=(document.body.clientWidth-400)/2+250;
	prop=prop+"width=550,height=450,";
	prop=prop+"top="+windowTop+",";
	prop=prop+"left="+windowLeft+",";
	prop=prop+"scrollbars=no";
	returnWindow=window.open("../../home/popBulletinDate.jsp?popField="+popField+"&preWord="+preWord+"&isLookup="+isLookup+"&js="+js,"",prop);
}

function popBulletinDate2(popField,OrganID,Ownership,SerialNo,LevyNo,PropertyNo,preWord,isLookup,js){
	var prop="";
	var windowTop=(document.body.clientHeight-400)/2+180;
	var windowLeft=(document.body.clientWidth-400)/2+250;
	prop=prop+"width=550,height=450,";
	prop=prop+"top="+windowTop+",";
	prop=prop+"left="+windowLeft+",";
	prop=prop+"scrollbars=no";
	returnWindow=window.open("../../home/popBulletinDate2.jsp?popField="+popField+"&OrganID="+OrganID+"&Ownership="+Ownership+"&SerialNo="+SerialNo+"&LevyNo="+LevyNo+"&PropertyNo="+PropertyNo+"&preWord="+preWord+"&isLookup="+isLookup+"&js="+js,"",prop);
}

function addLoadEventListener(func) {
  	var oldonload = document.onload;
	document.onload = function() {
		oldonload();			
		func();
	};
}

function setAutocompleteOff(strField, autoFlag) {	
	if (strField.trim().length>0) {
		var arrField = strField.split(",");
		if (arrField.length>0) {
			try {	
			 	for(var i=0; i<arrField.length; i++){
			 		if (isObj(document.all.item(arrField[i]))) {		 		  	
						var obj = document.all.item(arrField[i]);
						if (autoFlag=="on") obj.setAttribute('AutoComplete','on');
						else obj.setAttribute('AutoComplete','off');				
					}
				}
			} catch(e) {}
		}
	}
}

function addChar( o , l){
	var sv = o.value;
	var sk = sv.length;
	if(sk != 0){
		for(var i=0 ;i<( l - sk ) ;i++){
			sv = '0' + sv;
		}
	}
	o.value = sv;
}

function closebar() {
	if(isObj(top.title)) {
		top.title.document.cookie = "remainclosed=1";
		top.title.document.getElementById("topbar").style.visibility = "hidden";
	}
}

function getOptionsBy(file, src, trg, selectValue) {	
	var queryValue;
	if(isObj(document.all.item(src))) {
		queryValue = document.all.item(src).value;
	} else {
		queryValue = src;
	} 
	var url = file+"?queryValue="+queryValue;
		
	//清除target
	if (isObj(document.all.item(trg))) {		
		var trgObj = document.all.item(trg);
		trgObj.options.length = 0;
		var opt = document.createElement("Option");	
		trgObj.options.add(opt);
		opt.innerText = "請選擇";
		opt.value = "";		
		
		if (queryValue != "") {
			var xmlDoc = document.createElement("xml");	
			xmlDoc.async = false;			

			if(xmlDoc.load(url)) {		
				var nodesLen = xmlDoc.documentElement.childNodes.length;
				for(var i = 0; i < nodesLen; i++) {
					optValue = xmlDoc.documentElement.childNodes.item(i).getAttribute("optValue");
					optText = xmlDoc.documentElement.childNodes.item(i).getAttribute("optText");
					
					var opt = document.createElement("Option");	
					trgObj.options.add(opt);
					opt.innerText = optText;
					opt.value = optValue;	
			      	if(optValue == selectValue) {
	        			opt.selected = true;
					}
				}
			}else{
				return false;	
			}
		}			
	}	
	

}

//*********************************************
//程式功能：AJAX,擷取回應值
//*********************************************
function getRemoteData(uri,q,asyn)  {
	var x ;
	if(window.XMLHttpRequest){
	    x = new XMLHttpRequest();
	} else {
	   x = new ActiveXObject("Microsoft.XMLHTTP"); 
	}
	if (isObj(asyn) && asyn != null && asyn == true) {
		x.open('Post',uri,true); 		
	} else 	x.open('Post',uri,false); 
	x.setRequestHeader('Content-type', 'application/x-www-form-urlencoded');
	x.setRequestHeader( "If-Modified-Since", "Sat, 1 Jan 2000 00:00:00 GMT" );
	x.send(encodeURI('q='+q));
	return x.responseText.trim();
}

function getRemoteXML(uri,q,asyn)  {
	var x ;
	if(window.XMLHttpRequest){
	    x = new XMLHttpRequest();
	} else {
	   x = new ActiveXObject("Microsoft.XMLHTTP"); 
	}
	if (isObj(asyn) && asyn != null && asyn == true) {
		x.open('Post',uri,true); 		
	} else 	x.open('Post',uri,false); 
	x.setRequestHeader('Content-type', 'application/x-www-form-urlencoded');
	x.setRequestHeader( "If-Modified-Since", "Sat, 1 Jan 2000 00:00:00 GMT" );   
	x.send(encodeURI('q='+q));
	if (x.readyState == 4 && x.status == 200) return x.responseXML;
}

//判斷使用用者是否只有查詢全縣,如果有只顯示查詢紐,buttonName各個作業自己新增的按鈕
function SetGroup(buttonName,userGroupID){
    var userGroup = userGroupID; 
	if(userGroup == "unt05"){	    
        document.all.item("insert").disabled = true;   
  	    document.all.item("update").disabled = true; 
  	    document.all.item("delete").disabled = true; 
  	    document.all.item("clear").disabled = true;  
  	    document.all.item("confirm").disabled = true;
  	    document.all.item("nextInsert").disabled = true;
  	    for(var i=0 ; i<buttonName.length;i++){
	        buttonName[i].disabled = true;
	    }
	}
}

function disableBackspaceEvent(e) {
	var code = null;
	if (!e) e = window.event;
	if (e.keyCode) code = e.keyCode;
	else if (e.which) code = e.which;
	if (code==8 && e.srcElement!=null && (like(e.srcElement.className,"RO")||isObjReadOnly(e.srcElement))) {
		return false;
	}
	return true;
}

if (isIE()) document.onkeydown = disableBackspaceEvent;
else document.onkeypress = disableBackspaceEvent;
//將回上頁 的功能 Disabled，這是一個很差的處理方式，若有其它解決辦法，請用別的
window.history.forward(1);

//*********************************************
//程式功能：取得今天日期（YYYMMDD）
//*********************************************
function getToday(s){
	if (s==null) {
		var x = getRemoteData(getVirtualPath() + "ajax/jsonDatetime.jsp", "");
		if (x!=null && x.length>0) {
			var json = eval('(' + x + ')'); 
			return json.YYYMMDD;
		}
	}
	var myDate = new Date();		
	if (s!=null) myDate = s;
	var shif = 0;
	//var intYear = myDate.getFullYear();
	//if (intYear<300) shif = 1900;
	var myYear = (myDate.getFullYear()-1911+shif).toString();	
	var myMonth = (myDate.getMonth()+1).toString();
	var myDay = myDate.getDate().toString();
	myToday = lpad(myYear,3,'0') + lpad(myMonth,2,'0') + lpad(myDay,2,'0');
	return myToday;	
}

function lpad(s,len,ch) {
	var format="";	
	if (s==null) s = "";
	var sLen=s.length;
	for(var i=0; i<(len-sLen); i++)	format += ch;	
	format += s;
	return format;
}
function rpad(s,len,ch){
	var format="";
	if (s==null) s = "";
	var sLen=s.length;
	for(var i=0;i<(len-sLen); i++) format += ch;	
	format = s + format;
	return format;
}

//*********************************************
//函數功能：pop,並回傳
//參　　數：dateField顯示日期的欄位名稱
//傳 回 值：無
//*********************************************
function popContact(dateField){
	var arr = dateField.split(',');
	var xmlDoc=document.createElement("xml");
	xmlDoc.async=false;
	var randomnumber=Math.floor(Math.random()*1000);
	if(xmlDoc.load("../../home/xmlContact.jsp?randomnumber="+randomnumber)){
			var nodesLen=xmlDoc.documentElement.childNodes.length;
			for(var i=0; i<nodesLen; i++){				
				if(arr[0]!=''){//姓名
					document.all.item(arr[0]).value=xmlDoc.documentElement.childNodes.item(i).getAttribute("empName");
				}
				if(arr[1]!=''){//電話
					document.all.item(arr[1]).value=xmlDoc.documentElement.childNodes.item(i).getAttribute("empTel");
				}
				if(arr[2]!=''){//傳真
					document.all.item(arr[2]).value=xmlDoc.documentElement.childNodes.item(i).getAttribute("empFax");
				}
				if(arr[3]!=''){//email
					document.all.item(arr[3]).value=xmlDoc.documentElement.childNodes.item(i).getAttribute("empEmail");
				}
				if(arr[4]!=''){//郵遞區號
					document.all.item(arr[4]).value=xmlDoc.documentElement.childNodes.item(i).getAttribute("govaddr1");
				}
				if(arr[5]!=''){//住址
					document.all.item(arr[5]).value=xmlDoc.documentElement.childNodes.item(i).getAttribute("govaddr2");
				}										
			}
		}else{
			alert("NO");
			return false;	
		}
	
}

//*********************************************
//程式功能：List區之定位JS工具（START） by Timtoy.Tsai
//*********************************************

//首先定義HttpCookie類別（ path、domain，非必填）
var HttpCookie = function( name, value, expires )
{
	if( name ) this.Name = name;
	if( value ) this.Value = value;
	if( expires ) this.Expires = expires;
};

//定義HttpCookie的屬性及Function
HttpCookie.prototype = 
{
	Name:'', Value:'', Expires:'',
	toCookie: function()
	{
		var NewCookie = this.Name + '=' + this.Value;
		if( this.Expires )	NewCookie += (';expires=' + this.Expires);
		return NewCookie;
	}
};

//定義 Cookie 輔助小幫手
var CookieHelper = function(){};

//將時間轉換成Millisecond, 再轉換為UTC String 
CookieHelper.ConvertToUTCString = function( expireHours ) { 
	var Timestamp = ( !expireHours || expireHours == 0 )?(0):(new Date().getTime()+(expireHours*3600000)); 
	return new Date(Timestamp).toUTCString(); 
};

//取得畫面程式代號
CookieHelper.getJSPName = function(url) { 
	var jspName;
	if (url.lastIndexOf("/")!= -1 && url.lastIndexOf("?")!= -1){
		jspName = url.substring(url.lastIndexOf("/")+1, url.lastIndexOf("?")) ;
	}else if (url.lastIndexOf("/")!= -1){
		jspName = url.substring(url.lastIndexOf("/")+1) ;
	}else if (url.lastIndexOf("?")!= -1){
		jspName = url.substring(0, url.lastIndexOf("?")) ;
	}else{
		jspName = url;
	}
	return jspName; 
};

//設定Cookie ( 名稱, 值, 保存小時數 )
CookieHelper.Set = function( cookieName, cookieValue, expireHour )
{
	var HC = new HttpCookie( cookieName, escape(cookieValue), CookieHelper.ConvertToUTCString(expireHour) );	
	document.cookie = HC.toCookie();
};

//取得特定對象Cookie
CookieHelper.Get = function( cookieName )
{
	var regex = new RegExp( ("(^|;)?" + cookieName + "=([^;]*)(;|$)") ); // 用正則表達式來取得我們要的值
	var Matchs = document.cookie.match( regex );
//alert(document.cookie);alert(Matchs);
	if( Matchs ) return unescape(Matchs[2]);
	else return null;
};

//刪除特定對象Cookie
CookieHelper.Delete = function( cookieName )
{
	if( !CookieHelper.Get(cookieName) ) return; // 若無法取得值就停止動作
	var HC = new HttpCookie( cookieName, null, CookieHelper.ConvertToUTCString(0) );
	document.cookie = HC.toCookie();
};

//刪除ALL Cookie(JESSIONID除外)
CookieHelper.DeleteAll = function()
{	
	if(top.fbody){	
		var Cookie_array = top.fbody.main.document.cookie.split(";"); // 請注意來源的正確性	
		for (var i=0; i < Cookie_array.length; i++){
		    var aCookie = Cookie_array[i].split("=");
		    if(aCookie[0].indexOf("JSESSIONID") == -1){
			    var HC = new HttpCookie( aCookie[0], null, CookieHelper.ConvertToUTCString(0) );
			    top.fbody.main.document.cookie = HC.toCookie(); // 請注意來源的正確性
		    }
		}
	}
};

//Enter Point：改變CSS風格形式＋儲存Cookie訊息
function listContainerRowClickAuto() 
{	
	try {
		if (isObj(document.all.item("listContainerActiveRowId"))) 
		{		
			
			setCookie(document.URL+'_listContainerActiveRowId',document.all.item("listContainerActiveRowId").value);		
			setDivPosition(CookieHelper.getJSPName(document.URL), "listContainer", document.all.item("listContainerActiveRowId").value);
		}	
	} catch(e) {
	
	}
}

//Enter Point：改變CSS風格形式＋儲存Cookie訊息

function listContainerRowClick(rowid) 
{	
	try {
		if (rowid!=null && isObj(document.all.item("listContainerActiveRowId"))) 
		{		
			
			setCookie(document.URL+'_listContainerActiveRowId',rowid);		
			document.all.item("listContainerActiveRowId").value = rowid;
			setDivPosition(CookieHelper.getJSPName(document.URL), "listContainer", rowid);
		}	
	} catch(e) {
	
	}

	//if(click_number<=1){
	//alert(rowid);
		//if(changeCssStyle(rowid))
		//{ 
//			setDivPosition(CookieHelper.getJSPName(document.URL), "listContainer", rowid);
//			
//			if(isObj(document.getElementById("listContainerActiveRowId")))
//				document.getElementById("listContainerActiveRowId").value= rowid;
//			
//			alert(isObj(document.getElementById("listContainerActiveRowId")));
//			
//			document.getElementById("listContainerRow"+rowid).className='activeRow';
//			document.getElementById("listContainerRow"+rowid).onmouseover='activeRow';
//			document.getElementById("listContainerRow"+rowid).onmouseout='activeRow';
		//}
	//}
}

function setCookie(c_name,value)
{
	c_name = validateCookie(escape(deleteChr(c_name)));
	value = validateCookie(escape(value));
	document.cookie=c_name+"="+value;
}



function validateCookie(cookieStr)
{
	//prevent Cookie Manipulation
	try{
		cookieStr = cookieStr.replace("%0d","");
		cookieStr = cookieStr.replace("%0D","");
		cookieStr = cookieStr.replace("\r","");
		cookieStr = cookieStr.replace("%0a","");
		cookieStr = cookieStr.replace("%0A","");
		cookieStr = cookieStr.replace("\n","");
	}catch(e){}
	return cookieStr;
}


function listContainerLoadActiveRow() 
{
	if (isObj(document.all.item("listContainerActiveRowId"))) 
	{				
		var rowid = getCookie(document.URL+'_listContainerActiveRowId');
		
		if (isObj(document.all.item("listContainerRow"+rowid))) 
		{			
			if (isObj(document.all.item("state")) && !like(document.all.item("state").value,'insert') 
					&& !like(document.all.item("state").value,'delete')) 
			{
				document.all.item("listContainerRow"+rowid).className = "activeRow";
			}
			document.all.item("listContainerRow"+rowid).onmouseover="activeRow";
			document.all.item("listContainerRow"+rowid).onmouseout="activeRow";
			getDivPosition('listContainer',deleteChr(document.URL));
		}	
	}	
}

function listContainerLoadlistTR() 
{
	if (isObj(document.all.item("listContainerActiveRowId"))) 
	{				
		var rowid = getCookie(document.URL+'_listContainerActiveRowId');
		
		if (isObj(document.all.item("listContainerRow"+rowid))) 
		{			
			if (isObj(document.all.item("state")) && !like(document.all.item("state").value,'insert') 
					&& !like(document.all.item("state").value,'delete')) 
			{
				document.all.item("listContainerRow"+rowid).className = "listTR";
			}
			document.all.item("listContainerRow"+rowid).onmouseover="listTRMouseover";
			document.all.item("listContainerRow"+rowid).onmouseout="listTR";
			getDivPosition('listContainer',deleteChr(document.URL));
		}	
	}	
}




//定義CSS風格形式（參考default.css）
function changeCssStyle(rowid) 
{ 
	if (rowid!=null && isObj(document.getElementById(rowid))) 
	{	
		document.getElementById("listContainerRow"+rowid).className='activeRow';
		document.getElementById("listContainerRow"+rowid).onmouseover='activeRow';
		document.getElementById("listContainerRow"+rowid).onmouseout='activeRow';
		return true;
	}		
	return false;
}


function getCookie(c_name)
{
	c_name = escape(deleteChr(c_name));
	if (document.cookie.length>0) {
  		c_start=document.cookie.indexOf(c_name + "=");
	  	if (c_start!=-1){
	    	c_start=c_start + c_name.length+1;
	    	c_end=document.cookie.indexOf(";",c_start);
	    	if (c_end==-1) c_end=document.cookie.length;
	    	return unescape(document.cookie.substring(c_start,c_end));
	    }
  }
  return "";
}



function setDivPosition(jspName, divName, rowid){
//alert(jspName+"_PK");alert(rowid);
	
	if (isObj(jspName) && isObj(divName)) 
	{
		CookieHelper.Set( jspName+"_Pos", document.getElementById(divName).scrollTop, 8 );
		CookieHelper.Set( jspName+"_PK" , rowid, 8 );
	}
}

function getDivPosition(jspName, divName) {
//alert(jspName+"_PK");alert(CookieHelper.Get(jspName+"_PK"));
	if( changeCssStyle(CookieHelper.Get(jspName+"_PK")) )
		document.getElementById(divName).scrollTop = parseInt(CookieHelper.Get(jspName+"_Pos"));
}

//*********************************************
//函數功能：使用 iframe 框架印報表
//*********************************************
function printReportFun(url)
{	
	var iframe ;
	if(isObj(url)){
		// for 維護畫面
		iframe = $j("<iframe style='display:none' src=''></iframe>").attr("src",url);
	}else{
		// for 報表畫面
		// 輸出格式選擇HTML時，將會開啟一個新視窗；反之，則否。L
		if($j("[name=q_prtType]").size() > 1){
			$j("[name=q_prtType]").each(
				function(){
					if($j(this).attr("checked")){
						if(this.value=="HTML"){
							$j("#form1").attr('target','_blank');
							return;
						}else{
							$j("#form1").attr('target','iframe4report');
						}
					}
				}
			);
		}

		if(!($j("#iframe4report").length > 0)) {
			iframe = "<iframe style='display:none' id='iframe4report' name='iframe4report'></iframe>";
		}else return;
	}

	$j("body").append(iframe);
}

//*********************************************
//函數功能：將字串前面補零
//參　　數：o,欄位物件 ；l,字串總長度
//傳 回 值：字串
//*********************************************
function formatFrontZero( o , l){
	var sv = o.value;
	var sk = sv.length;
	if(sk != 0){
		for(var i=0 ;i<( l - sk ) ;i++){
			sv = '0' + sv;
		}
	}
	o.value = sv;
}

//*********************************************
//函數功能：彈跳視窗輔助程式
//*********************************************
function goToURL(url,width,height,windowName){
	//alert(url); return;	
	var wName = "" ;
	if(isObj(windowName)){
		if(windowName.length > 0) wName=windowName;
	}

	var windowTop=(document.body.clientHeight-400)/2;
	var windowLeft=(document.body.clientWidth-400)/4;
	var prop= new Array();
	if(isObj(width),isObj(height)){
		if( !isNaN(parseInt(length,10)) && !isNaN(parseInt(height,10)) ){
			prop.push("width="+width);
			prop.push("height="+height);
		}
	}
	prop.push("top="+windowTop);
	prop.push("left="+windowLeft);
	prop.push("toolbar=no,menubar=no,scrollbars=yes,resizable=yes,location=no,status=no");
	window.open(url, wName, prop.join(",")); 	//window.open(url,'MyWindow',prop);
}

//*********************************************
//函數功能：執行 QueryOne 後，依照該筆資料的editID 獲得 govID
//引用者 ：ToolBar.jsp
//*********************************************
function getGovID(){
	if(isObj(form1.editID)){
		var q_editID = form1.editID.value;
		if(q_editID == "") return;
		
		var xmlDoc=document.createElement("xml");
		xmlDoc.async=false;
		
		if(xmlDoc.load("../../home/xmlGovID.jsp?"+new Date().getTime()+"&q_word="+q_editID)){
			return xmlDoc.documentElement.childNodes.item(0).getAttribute("govID");
		}else return;
	}else return;
}

//*********************************************
//函數功能：將fromFdName欄位的值傳給toFdName欄位
//參　　數：fromFdName:欄位名稱(字串), toFdName:欄位名稱(字串)
//傳 回 值：無
//*********************************************
function identicalID(fromFdName, toFdName){
	document.getElementById(toFdName).value = document.getElementById(fromFdName).value;
}


//*********************************************
//函數功能：將fromFdName欄位的值清空
//參　　數：fromFdName:欄位名稱(字串)
//傳 回 值：無
//*********************************************
function doClearField(fromFdName){
	document.getElementById(fromFdName).value = "";
}

//*********************************************
//函數功能：將toFdName欄位的值放置thisValue
//參　　數：toFdName:欄位名稱(字串), thisValue:置入的資料(字串)
//傳 回 值：無
//*********************************************
function putValue(toFdName, thisValue){
	document.getElementById(toFdName).value = thisValue;	
}

//*********************************************
//函數功能：連動改變欄位值
//參　　數：file1原欄位; file2連動欄位; selectValue所選的值
//傳 回 值：無
//*********************************************
function getSelectRM02(file1,file2,selectValue){
	var queryValue=document.all.item(file1).value;
	//清除file2
	if (isObj(document.all.item(file2))) {		
		var obj2 = document.all.item(file2);
		obj2.options.length=0;
		var obj2Option = document.createElement("Option");
		obj2.options.add(obj2Option);
		obj2Option.innerText = "請選擇";
		obj2Option.value = "";	
		
		if (queryValue!=""){
			var xmlDoc=document.createElement("xml");	
			xmlDoc.async=false;
			var randomnumber=Math.floor(Math.random()*1000);
			if(xmlDoc.load("../../home/xmlGetSelectRM02.jsp?file1="+queryValue+"&randomnumber="+randomnumber)){		
				var nodesLen=xmlDoc.documentElement.childNodes.length;
				for(var i=0; i<nodesLen; i++){
					codeID=xmlDoc.documentElement.childNodes.item(i).getAttribute("codeID");
					codeName=xmlDoc.documentElement.childNodes.item(i).getAttribute("codeName");
					var oOption = document.createElement("Option");	
					obj2.options.add(oOption);
					oOption.innerText = codeName;
					oOption.value = codeID;		
			      	if(codeID == selectValue){
	      			oOption.selected=true;
					}           										
				}
			}else{
				alert("NO");
				return false;	
			}
		}		
	}

	
}

//*********************************************
//函數功能：取得代碼檔的內容
//參　　數：inputValue=要查的代碼
//傳 回 值：無
//*********************************************
function getSyscaCodeSubj(inputValue){
	var queryValue=inputValue;
	//清除file2
	if (queryValue != ""){
		var xmlDoc=document.createElement("xml");	
		xmlDoc.async=false;
		var randomnumber=Math.floor(Math.random()*1000);
		if(xmlDoc.load("../../home/xmlGetSyscaCodeSubj.jsp?queryValue="+queryValue+"&randomnumber="+randomnumber)){		
			var nodesLen=xmlDoc.documentElement.childNodes.length;
			for(var i=0; i<nodesLen; i++){
				codeName=xmlDoc.documentElement.childNodes.item(i).getAttribute("codeName");
				return codeName;
				//codeName=xmlDoc.documentElement.childNodes.item(i).getAttribute("codeName");
				//var oOption = document.createElement("Option");	
				//obj2.options.add(oOption);
				//oOption.innerText = codeName;
				//oOption.value = codeID;		
		      	//if(codeID == selectValue){
      		//	oOption.selected=true;
				//}           										
			}
		}else{
			alert("NO");
			return false;	
		}
	}	
}


//*********************************************
//函數功能：取得代碼檔的內容
//參　　數：inputValue=要查的代碼
//傳 回 值：無
//*********************************************
function getSyscaCodeSubj2(inputValue1,inputValue2){
	var queryValue1=inputValue1;
	var queryValue2=inputValue2;
	//清除file2
	if (queryValue1 != "" && queryValue2 != "" ){
		var xmlDoc=document.createElement("xml");	
		xmlDoc.async=false;
		var randomnumber=Math.floor(Math.random()*1000);
		if(xmlDoc.load("../../home/xmlGetSyscaCodeSubj2.jsp?queryValue1="+queryValue1+"&queryValue2="+queryValue2+"&randomnumber="+randomnumber)){		
			var nodesLen=xmlDoc.documentElement.childNodes.length;
			for(var i=0; i<nodesLen; i++){
				codeName=xmlDoc.documentElement.childNodes.item(i).getAttribute("codeName");
				return codeName;
				//codeName=xmlDoc.documentElement.childNodes.item(i).getAttribute("codeName");
				//var oOption = document.createElement("Option");	
				//obj2.options.add(oOption);
				//oOption.innerText = codeName;
				//oOption.value = codeID;		
		      	//if(codeID == selectValue){
      		//	oOption.selected=true;
				//}           										
			}
		}else{
			alert("NO");
			return false;	
		}
	}	
}

//*********************************************
//函數功能：將英文小寫轉成大寫
//參　　數：fromFdName=要轉換的欄位名稱
//傳 回 值：無
//*********************************************
function azToUpperCase(fromFdName){
	document.getElementById(fromFdName).value = document.getElementById(fromFdName).value.toUpperCase();
}


//*********************************************
//程式功能：依登入者的群組權限是維護、查詢來顯示或隱藏非toolbar的按鈕
//*********************************************
function controlOtherButton(buttonName,userMQ){
	var arr = buttonName.split(',');
	for(var i=0; i<arr.length; i++){
		if(userMQ=='Q'){
			document.getElementById(arr[i]).style.display = "none";
		}else{
			document.getElementById(arr[i]).style.display = "";
		}	
	}
}

//*********************************************
//函數功能：上傳檔案(4個參數)
//參　　數：popFileID:欄位ID/NAME, dirLocation:資料夾名稱
//傳 回 值：
//*********************************************
function openFUWindow(popFileID, dirLocation){
//	if (isObj(eval("form1."+popFileID)) && isObj(eval("form1."+popFullFileID))) {
	if (isObj(eval("form1."+popFileID))) {
	    var prop='';
	    prop=prop+'toolbar=no;location=no,directories=no,menubar=no,status=no,scrollbars=yes,resizable=yes,';
	    prop=prop+'width=450,';
	    prop=prop+'height=160';
	    window.open('../../home/popFU.jsp?popFileID=' + popFileID +'&dirLocation='+dirLocation, '上傳檔案', prop);
	} else {
		alert("欄位不存在,請檢查!");
		return ;
	}
}

//*********************************************
//函數功能：下傳檔案(4個參數)
//參　　數：fileValue:欄位值, popFileID:欄位ID/NAME, fullPath:檔案存放的資料夾名稱
//傳 回 值：
//*********************************************
function openFDWindow(fileValue, popFileID, fullPath){
var prop='';
prop=prop+'toolbar=no;location=no,directories=no,menubar=no,status=yes,scrollbars=yes,resizable=yes,';
prop=prop+'width=400,';
prop=prop+'height=400';
//alert("function>>>>>"+fileValue);
//alert("function>>>>>"+fullPath);
	if (parse(fileValue).length>0) {
		var arrFileName = fileValue.split(":::");
		if (arrFileName.length>1) {
		    var url = "../../fileDownload?fileID="+fileValue+ "&realPath="+fullPath;
//		    alert("url>>>"+url);
		    window.open(url,'popDownload',prop);
		} else {
			alert("無法下載該檔案，因為檔案資訊不完整，若問題持續，請洽系統管理!");
		}
	} else if (parse(popFileID).length>0) {
	    var url = "../../fileDownload?fileName=" + popFileID+ "&realPath="+fullPath;    
		window.open(url,'popDownload',prop);
	} else {
		alert("目前沒有任何檔案可供下載!");	
	}
}

//*********************************************
//函數功能：pop萬年曆輔助視窗,並回傳年月日(20120101)(西元)
//參　　數：dateField顯示日期的欄位名稱
//傳 回 值：無
//*********************************************
function popAdCalndar(dateField){
	var prop="";
	prop=prop+"scroll:yes;status:no;help:no;";
	prop=prop+"dialogWidth:210px;";
	prop=prop+"dialogHeight:240px";
	var strDate=window.showModalDialog('../../home/calendar_AD.jsp',window,prop);
	if (isObj(strDate)){ document.all.item(dateField).value=strDate; }
}


function addLandNum(landSct,landM,landS,landNum){
	var sct=document.all.item(landSct).value;
	var m=document.all.item(landM).value;	
	var s=document.all.item(landS).value;	
	var nums = document.all.item(landNum).innerHTML;
	if(sct != "" && m != "" && s != ""){
		document.all.item(landNum).innerHTML = nums + sct + " " + m+"-"+s+";";	
		document.all.item(landSct).value = "";
		document.all.item(landM).value = "";	
		document.all.item(landS).value = "";	
	}
	
}

/**
for dsFieldLookup
*/
function changeDsField(q,parentObj,targetObj,selectValue) {	
	if (isObj(document.all.item(targetObj))) {
		if (isObj(parentObj)) q += "&pre=" + parentObj.value;
		var x = getRemoteData('./jsonDsFieldLookup.jsp',q);		
		var obj3 = document.all.item(targetObj);
		obj3.type = "select-one";
		obj3.options.length=0;
		var obj3Option = new Option("請選擇","");		
		obj3.options.add(obj3Option);
				
		var fd = eval('(' + x + ')'); 
		var i;
		for (i=0; i<fd.length; i++) {
			var flKeyField =  fd[i].flKeyField;			
			var oOption = new Option(fd[i].flDisField,flKeyField);
			obj3.options.add(oOption);
	    	if(flKeyField == selectValue) oOption.selected=true;			
		}
	}else{
		alert(targetObj);
		alert("主表單查詢欄位設定之對應關係出錯，請重新執行！\n\n若問題持續，請洽相關承辦人員或系統建置廠商。\n");
	}
}

function getAlphaIntOnly(sStr){
	var s = "";
	for(var i=0;i<sStr.length;i++){
		var ch=sStr.charAt(i);
	    if(!((ch < "A" || ch >"Z")&&(ch < "a"||ch > "z")&&(ch < "0" || ch >"9"))) s += ch;	    
	}
	return s;
}

function changeGov(govKind,gov1,gov2,gov3,selvalue){
	var q_gov1=document.all.item(gov1).value;	
	//var q_gov2=document.all.item(gov2).value;	
	var objGov = document.all.item(gov3);
	if(govKind == "2"){
		objGov = document.all.item(gov2);
	}
	if(govKind == "3"){
		objGov = document.all.item(gov3);
	}
			
	objGov.options.length=0;
	var obj3Option = new Option("請選擇","");
	objGov.options.add(obj3Option);
	/**
	var GovOption = document.createElement("Option");	
	objGov.options.add(GovOption);
	GovOption.innerText = "請選擇";
	GovOption.value = "";	
	var url = "";		
	**/
	
	var x = null;
	if(govKind == "2"){
		//url = "../xmlGov.jsp?govKind="+govKind+"&pgov="+q_gov1+"&ppgov="+"&P="+Math.floor((Math.random()*100)+1);
		x = getRemoteData("../../home/jsonGov.jsp","&q_govKind="+govKind+"&q_govID="+q_gov1+"&ppgov=&P="+Math.floor((Math.random()*100)+1));
	}
	if(govKind == "3"){
		//url = "../xmlGov.jsp?govKind="+govKind+"&pgov="+q_gov2+"&ppgov="+q_gov1+"&P="+Math.floor((Math.random()*100)+1);
		x = getRemoteData("../../home/jsonGov.jsp","&q_govKind="+govKind+"&q_govID="+q_gov1+"&ppgov=&P="+Math.floor((Math.random()*100)+1));
	}
	if (x!=null && x.length>0) {
		var json = JSON.parse(x);
		var codeid = '', codename = '';
		if (json!=null && json.length>0) {
			for (var i=0; i<json.length; i++) {
				codeid=json[i].codeId;
				codename=json[i].codeName;
				
				var oOption = new Option(codename,codeid);
				objGov.options.add(oOption);
		    	if(codeid == selvalue) objGov.selected=true;			
			}
		}
	}
	/**
	//載入xml
	var xmlDoc=document.createElement("xml");	
	xmlDoc.async=false;			
	if(xmlDoc.load(url)){		
		var nodesLen=xmlDoc.documentElement.childNodes.length;
		for(i=0; i<nodesLen; i++){
			codeid=xmlDoc.documentElement.childNodes.item(i).getAttribute("codeid");
			codename=xmlDoc.documentElement.childNodes.item(i).getAttribute("codename");
			var oOption = document.createElement("Option");	
			objGov.options.add(oOption);
			oOption.innerText = codename;
			oOption.value = codeid;
	      	if(codeid == selvalue){
	              oOption.selected=true;
	        }  												
		}
	}else{
		return false;	
	}
	**/
}

function beforeSubmit(){
	var arrObj = document.forms[0].elements;
	var arrLen = arrObj.length;		
	for(var i=0; i<arrLen; i++){  			
		var obj = arrObj[i];
		if ((obj.type=="select")||(obj.type=="select-one")||obj.type=="checkbox"||obj.type=="radio"){ obj.disabled = false; }
	}
}

//此函數查找相應的項是否存在
//repeatCheck是否進行重複性檢查
//若為"v"，按值進行重複值檢查
//若為"t"，按文字進行重複值檢查
//若為"vt"，按值和文字進行重複值檢查
//其它值，不進行重複性檢查，返回false
function OptionExists(list, optText, optValue, repeatCheck){
	var i = 0;
	var find = false;
	
	if (repeatCheck == "v"){
		//按值進行重複值檢查
		for (i=0; i<list.options.length; i++){
			if (list.options[i].value == optValue){
				find = true;
				break;
			}
		}
	} else if (repeatCheck == "t"){
		//按文字進行重複檢查
		for (i=0; i<list.options.length; i++){
			if (list.options[i].text == optText){
				find = true;
				break;
			}
		}
	} else if (repeatCheck == "vt"){
		//按值和文字進行重複檢查
		for (i=0; i<list.options.length; i++){
			if ((list.options[i].value == optValue) && (list.options[i].text == optText)){
				find = true;
				break;
			}
		}
	}
	
	return find;
}

//向列表中追加一個項
//list 是要追加的列表
//optText 和 optValue 分別表示項的文字和值
//repeatCheck 是否進行重複性檢查，參見 OptionExists
//添加成功返回 true，失敗返回 false
function AppendOption(list, optText, optValue, repeatCheck){
	if (!OptionExists(list, optText, optValue, repeatCheck)){
		list.options[list.options.length] = new Option(optText, optValue);
		return true;
	} else {
		return false;
	}
}

//將一個列表中的項導到另一個列表中
//repeatCheck是否進行重複性檢查，參見OptionExists
//deleteSource項導到目標後，是否刪除源列表中的項
//返回影響的項數量
function ListToList(sList, dList, repeatCheck, deleteSource)
{
	//所影響的行數
	var lines = 0;
	var i = 0;
	while (i<sList.options.length){
		if (sList.options[i].selected && AppendOption(dList, sList.options[i].text,sList.options[i].value, repeatCheck)){
			//添加成功
			lines++;
			if (deleteSource){
				//刪除源列表中的項
				sList.options[i] = null;
			} else {
				i++;
			}
		} else {
			i++;
		}
	}
	
	return lines;
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
			
			$.post('../../home/queryDeprUnitData.jsp',
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
			
			$.post('../../home/queryDeprUnitData.jsp',
				comment,
				function(data){
					//將回傳資料寫入
					data=eval('('+data+')');
		
					$("select[name='originalDeprAccounts']").val(data['defaultValue']);
					
				});
		}
	//}
}

function queryPlaceName(queryEnterOrg, queryPlace){
	//傳送JSON
	var comment={};	
	comment.enterOrg = document.all.item(queryEnterOrg).value;
	comment.place = document.all.item(queryPlace).value;
	
	$.post('../../home/queryPlaceName.jsp',
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

//*********************************************
//函數功能：問題單2108特殊車輛報廢時檢核
//參　　數：useDate:使用年限, propertyNo:財產編號, serialNo:財產分號
//傳 回 值：檢核提醒字串
//*********************************************
function checkInsertProperty(useDate, propertyNo, serialNo){
	var alertStr = "";
	if (propertyNo == '40107010001' && useDate >= 12 && useDate < 15) {
		alertStr += propertyNo + "-" + serialNo + "『大客車未達使用年限但已滿12年，請檢查里程數是否超過12.5萬公里，方可申請報廢。』\n";
	} else if ((propertyNo == '4010704004A' || propertyNo == '4010704004B') && useDate >= 7 && useDate < 15) {
		alertStr += propertyNo + "-" + serialNo + "『警用巡邏車, 偵緝(防)車未達使用年限但已滿7年，請檢查里程數是否超過12.5萬公里，方可申請報廢。』\n";
	} else if ((propertyNo.substring(0,7) == '4010701' || propertyNo.substring(0,7) == '4010702' || propertyNo.substring(0,7) == '4010703' || propertyNo.substring(0,7) == '4010704') && useDate >= 10 && useDate < 15) {
		alertStr += propertyNo + "-" + serialNo + "『未達使用年限但已滿10年，請檢查里程數是否超過12.5萬公里，方可申請報廢。』\n";
	}  
	return alertStr;
}