function execAJAX_inputData(serialNo, colname, colvalue, colid){

	colid = colid-1;
	colname = colname.replace("after_","");

	var colvalueArray=[];
	
	colvalueArray.push("enterorg="+form1.enterOrg.value);
	colvalueArray.push("ownership="+form1.ownership.value);
	colvalueArray.push("differencekind="+form1.differenceKind.value);
	colvalueArray.push("propertyno="+form1.propertyNo_Reduce.value);
	colvalueArray.push("serialno="+serialNo);
	
	if(colname=="signNo3" || colname=="signNo4" || colname=="signNo5"){
		if(form1.after_signNo3[colid].value=='' || form1.after_signNo4[colid].value=='' || form1.after_signNo5[colid].value==''){

		}else{
			colvalueArray.push("signno="+form1.after_signNo3[colid].value + form1.after_signNo4[colid].value + form1.after_signNo5[colid].value);
		}
	}else{	
		colvalueArray.push(colname+"="+colvalue);
	}

	if(colname=='valueUnit'){
		colvalueArray.push("bulletinDate="+form1.after_bulletinDate[colid].value);
		form1.bulletinDate.value=form1.after_bulletinDate[colid].value;
	}else{
		colvalueArray.push("holdarea="+form1.after_holdArea[colid].value);
		colvalueArray.push("originalarea="+form1.after_area[colid].value);
		colvalueArray.push("originalholdnume="+form1.after_holdNume[colid].value);
		colvalueArray.push("originalholddeno="+form1.after_holdDeno[colid].value);
		colvalueArray.push("originalholdarea="+form1.after_holdArea[colid].value);
	}
	
	var comment={};

	comment.type=colname;
	comment.colvalue=colvalueArray;
	comment.edit=form1.editID.value;

	$('#canExecInsertNewDataFromAJAX').val("N");
	
	//�ǰeJSON
	$.post('untla059f_ajax.jsp',
		comment,
		function(data){
			$('#canExecInsertNewDataFromAJAX').val("Y");
		});
}


function execCreateNewData(){ 
	if(confirm("確定要新增資料？")){
		$('#wantToInsertNewData').val("Y");
		
		viewShow('messageContainer');

		checkCanInsertNewData();
	}
}

$(function(){
	$('#canExecInsertNewDataFromAJAX').bind("propertychange",function(){
		checkCanInsertNewData();
	});
});

function checkCanInsertNewData(){
	if($('#canExecInsertNewDataFromAJAX').val()=="Y" && $('#wantToInsertNewData').val()=="Y"){
		execInsertNewData();
	}
}

function execInsertNewData(){	
	form1.state.value="insertToUntla_land";	
	beforeSubmit();		
	form1.submit();
}

function checkBulletinDate(colid){
	var comment={};	

	colid = colid-1;
	comment.bulletinDate = form1.after_bulletinDate[colid].value;	
	
	//傳送JSON
	$.post('untla059f_ajax_checkBulletinDate.jsp',
		comment,
		function(data){
			//將回傳資料寫入				
			data=eval('('+data+')');
				 	
			if(data['BULLETINDATE']=="0"){
				alert("公告年月錯誤，請重新輸入!!");
				$("input[name='after_bulletinDate']").val('');
				$("input[name='after_bulletinDate']").css('backgroundColor',errorbg);				
			}else{
				$("input[name='after_bulletinDate']").css('backgroundColor','');
				$("input[name='after_bulletinDate']").val(form1.after_bulletinDate[colid].value);
			}			
		});
}



/*顯示視窗*/
function viewShow(viewName){
	var obj=document.all.item(viewName);
	var objHeight= obj.style.height;
	var objWidth= obj.style.width;
	
	objHeight= objHeight.substring(0,objHeight.length-2);
	objWidth= objWidth.substring(0,objWidth.length-2);

	obj.style.top=(document.body.clientHeight-Number(objHeight))/2-40;
	obj.style.left=(document.body.clientWidth-Number(objWidth))/2;
	obj.style.display="block";	
}

/*隱藏視窗*/
function viewHidden(viewName){
	var viewObj=document.all.item(viewName);		
	viewObj.style.display="none";
	
}

function changeSignNo1(selectValue){

	selectValue = selectValue-1;
	var queryValue=form1.after_signNo1[selectValue].value;	
	var obj2;
	//清除signNo2
	if (isObj(form1.after_signNo2[selectValue])) {		
		obj2 = form1.after_signNo2[selectValue];
		obj2.options.length=0;
		var obj2Option = document.createElement("Option");
		obj2.options.add(obj2Option);
		obj2Option.innerText = "請選擇";
		obj2Option.value = "";	
	}
	
	//清除signNo3
	if (isObj(form1.after_signNo3[selectValue])) {		
		var obj3 = form1.after_signNo3[selectValue];
		obj3.options.length=0;
		var obj3Option = document.createElement("Option");	
		obj3.options.add(obj3Option);
		obj3Option.innerText = "請選擇";
		obj3Option.value = "";			
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
				
				$("select[name='after_signNo2']:eq(" + selectValue + ") option").remove();
				$("select[name='after_signNo2']").eq(selectValue).append($("<option></option>").attr("value", "").text("請選擇"));
				for (var i = 0; i < NumOfData; i++) {
					$("select[name='after_signNo2']").eq(selectValue).append($("<option></option>").attr("value", data[i]['signNo']).text(data[i]['signName']));
					
					if(data[i]['signNo'] == selectValue){
						$("select[name='after_signNo2']:eq(" + selectValue + ") option").selectedIndex = (i+1);							
					}
				}
				
			});	
	}	
}

function changeSignNo2(selectValue){
	selectValue = selectValue-1;
	var queryValue=form1.after_signNo2[selectValue].value;	
	var obj3;
	//清除signNo3
	if (isObj(form1.after_signNo3[selectValue])) {		
		obj3 = form1.after_signNo3[selectValue];
		obj3.options.length=0;
		var obj3Option = document.createElement("Option");	
		obj3.options.add(obj3Option);
		obj3Option.innerText = "請選擇";
		obj3Option.value = "";			
	}	
	
	if (queryValue!=""){
		var comment={};	
		comment.signNo2 = queryValue;
		
		$.post('../../home/xmlSign.jsp',
			comment,
			function(data){
				//將回傳資料寫入
				data=eval('('+data+')');

				var NumOfData = data.length;
				
				$("select[name='after_signNo3']:eq(" + selectValue + ") option").remove();
				$("select[name='after_signNo3']").eq(selectValue).append($("<option></option>").attr("value", "").text("請選擇"));
				for (var i = 0; i < NumOfData; i++) {
					$("select[name='after_signNo3']").eq(selectValue).append($("<option></option>").attr("value", data[i]['signNo']).text(data[i]['signName']));
					
					if(data[i]['signNo'] == selectValue){
						$("select[name='after_signNo3']:eq(" + selectValue + ") option").selectedIndex = (i+1);							
					}
				}
				
			});	
	}	
}	

function getFrontZero(colid){
	var nv=form1.after_signNo4[colid-1].value;
	var itemp=4;

	if(nv!=""){
		itemp-=nv.length;	
		var stemp="";		
		for (var i=0;i<itemp;i++){
			stemp+="0";
		}	
		stemp+=nv;		
		form1.after_signNo4[colid-1].value=stemp;
	}

	nv=form1.after_signNo5[colid-1].value;
	itemp=4;

	if(nv!=""){
		itemp-=nv.length;	
		var stemp="";		
		for (var i=0;i<itemp;i++){
			stemp+="0";
		}	
		stemp+=nv;		
		form1.after_signNo5[colid-1].value=stemp;
	}
}

function execSubmitNewInsert(){
	if(sourcekind=='101' || sourcekind=='105' || sourcekind=='108'){
		execCreateNewData();
		
	}else{
		if(execCheckBulletinDateAndValueUnit()){
			execCreateNewData();
		}
	}
}

function execCencel(){
	viewShow('messageContainer');
	
	form1.state.value="execCencel";
	
	beforeSubmit();		
	form1.submit();
}

function changeArea(colid){
	
	var holdAreaTemp;
	
	//「原始整筆面積×原始權利範圍」四捨五入取至小數第2位
	holdAreaTemp = roundp((form1.after_area[colid-1].value * (form1.after_holdNume[colid-1].value / form1.after_holdDeno[colid-1].value) ),'2',"Y") ;

	if(holdAreaTemp==null || holdAreaTemp==""){	holdAreaTemp=0;}
	form1.after_holdArea[colid-1].value = holdAreaTemp;
}

function checkHoldareaSum(){
	var sumStr=0;
	for(var i=0;i<form1.after_holdArea.length;i++){
		sumStr+=parseFloat(form1.after_holdArea[i].value,10);
	}
	sumStr = Math.round(sumStr*100)/100
	if(form1.before_holdarea.value!=sumStr){	alert("分割前「權利範圍面積」與分割後「各筆權利範圍面積合計」不一致");}
}


function popBulletinDate(popField,preWord,isLookup,js){
	var prop="";
	var windowTop=(document.body.clientHeight-400)/2+180;
	var windowLeft=(document.body.clientWidth-400)/2+250;
	prop=prop+"width=550,height=450,";
	prop=prop+"top="+windowTop+",";
	prop=prop+"left="+windowLeft+",";
	prop=prop+"scrollbars=no";
	returnWindow=window.open("untla059f_popBulletinDate.jsp?popField="+popField+"&preWord="+preWord+"&isLookup="+isLookup+"&js="+js,"",prop);
}


function checkNumType(namestr, colid){
	colid=colid-1;
	
	var nv=document.all.item(namestr)[colid];
	if(nv.value==""){
		nv.value=0 ;
	}
	if(isNaN(nv.value)){
		nv.style.backgroundColor=errorbg;
		nv.value='';
		alert("請輸入整數");
		return false;
	}
	if(/^-/.test(nv.value)){
		nv.style.backgroundColor=errorbg;
		nv.value='';
		alert("請輸入正整數");
		return false;
	}
	nv.style.backgroundColor="";
	return true;
}

function execCheckValueUnit(colid){
	colid = colid-1;
	if(form1.after_bulletinDate[colid].value==''){
		form1.after_valueUnit[colid].value='';
		form1.after_valueUnit[colid].style.backgroundColor=errorbg;
		alert('請先輸入公告年月！！');
		return false;
	}else{
		form1.after_valueUnit[colid].style.backgroundColor='';
		return true;
	}
}

function formatFloat(colid){
	colid = colid-1;
	var size = Math.pow(10, 2);
	var resultStr = Math.round(form1.after_valueUnit[colid].value * size) / size;
	form1.after_valueUnit[colid].value = resultStr;
}


window.onbeforeunload = function confirmExit(){
	if(form1.state.value=='beforeWork_Add' ||
			form1.state.value=='insertToUntla_land' ||
			form1.state.value=='execCencel' ||
			form1.state.value=='execCloseWindow' ||
			form1.state.value=='clickwrap'				
		){
		
	}else{
		opener.form1.action="untla058f.jsp";
		opener.form1.state.value='queryAll';
		opener.form1.submit();
		execCloseWindow();
	}
};

function execCloseWindow(){
	form1.state.value='execCloseWindow';
	form1.submit();
}
