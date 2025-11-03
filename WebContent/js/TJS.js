//*********************************************************************
//							T JavaScript
//												20121229
//*********************************************************************						




//*********************************************************************
//目的 :
//		取到小數點指定位數
//參數 :
//		namestr : 欲選擇元件的Name
//		dotnum	: 取到小數點第幾位
//		cond	: 	1	四捨五入		(預設)
//					2	無條件進位
//					3	無條件捨去
//*********************************************************************
function setDotNumber(namestr, dotnum, cond){
	var nv=$('input[name='+namestr+']').val();
	var returnStr="";
	var itemp="1";
	if(nv==null){
	}else{
		for(var j=0;j<dotnum;j++){	itemp+="0";	}
		itemp=parseInt(itemp,10);
			
		if(cond=="2"){			
			returnStr=Math.floor(nv*itemp)/itemp;
	    }else if(cond=="3"){
	    	returnStr=Math.ceil(nv*itemp)/itemp;
	    }else{
	    	returnStr=new Number(nv).toFixed(dotnum);
	    }
	}		
	$('input[name='+namestr+']').val(returnStr);
}

//*********************************************************************
//目的 :
//		將數字轉成顯示千分號
//參數 :
//		namestr : 欲選擇元件的Name
//*********************************************************************
function setCurrency(namestr){
	var nv=$('input[name='+namestr+']').val();
	var i;
	if(nv==null){
	}else{
	    var arr = nv.split(".");
	    var re = /(\d{1,3})(?=(\d{3})+$)/g;
	    i=arr[0].replace(re,"$1,") + (arr.length == 2 ? "."+arr[1] : "");
	}		
	$('input[name='+namestr+']').val(i);
}

//*********************************************************************
//目的 :
//		將數字去除千分號
//參數 :
//		namestr : 欲選擇元件的Name
//*********************************************************************
function clearCurrency(namestr){
	var nv=$('input[name='+namestr+']').val();
	var length=nv.length
	var rStr="";
	
	if (nv!=null && nv!="" && length>0) {
		for(var i=0;i<length;i++){
			ch = nv.charAt(i);
			if(ch!=",") rStr+=nv.charAt(i);
		}
	}
	$('input[name='+namestr+']').val(rStr);
}


//*********************************************************************
//目的 :
//		取代字串中所有欲改變的字 ( 等於ReplaceAll )
//參數 :
//		selectOne  : 欲選擇元件的Name	(可用this.name)
//		searchStr  : 欲尋找的字
//		replaceStr : 欲取代的字
//*********************************************************************
function replaceAll(selectOne, searchStr, replaceStr){
	var st=$('input[name='+selectOne+']').val();
	var index=0;
	while(st.indexOf(searchStr,index) != -1){
		st=st.replace(searchStr ,replaceStr);
		index = st.indexOf(searchStr,index);
	}
	$('input[name='+selectOne+']').val(st);
}

//*********************************************
//目的 :
//		檢查欄位輸入為何種數字類型
//參數 :
//		namestr : 欲選擇元件的Name
//返回 :
//		0	無輸入數字
//		1	正整數
//		2	負整數
//		3	正浮點數
//		4	負浮點數
//		5	非以上類型
//*********************************************
function checkNumType(namestr){
	var nv=$('input[name='+namestr+']').val();
	var checkStr;
	
	if(nv==null){						return 0;
	}else if(/^-?\d+$/.test(nv)){
		if(/^-/.test(nv)){				return 2;
		}else{							return 1;
		}		
	}else if(/^(-?\d+)(\.\d+)?$/.test(nv)){
		if(/^-/.test(nv)){				return 4;
		}else{							return 3;
		}
	}else{								return 5;
	}	
}


//*********************************************
//目的 :
//		在前方補入零
//參數 :
//		namestr : 欲選擇元件的Name
//		itemp   : 欲輸入多少位的零	(預設為0)
//*********************************************************************
function getFrontZero(namestr,itemp){
	var nv=$('input[name='+namestr+']').val();
	if(itemp==null){
		itemp="";
	}
	if(nv!=""){
		itemp-=nv.length;
		var stemp="";		
		for (i=0;i<itemp;i++){
			stemp+="0";
		}		
		stemp+=nv;
		$('input[name='+namestr+']').val(stemp);
	}
}

//*********************************************
//目的 ：
//		欄位去除頭尾空白
//參數 :
//		namestr : 欲選擇元件的Name (可用this.name)
//*********************************************
function doTrim(namestr){	
	doLeftTrim(namestr);
	doRightTrim(namestr);
}


//*********************************************
//目的 ：
//		欄位去除開頭空白
//參數 :
//		namestr : 欲選擇元件的Name (可用this.name)
//*********************************************
function doLeftTrim(namestr){	
	var s=$('input[name='+namestr+']').val();
	if(s.length>0){
		while(s.substring(0,1)==" "){
			s = s.substring(1,s.length);
		}		
	}
	$('input[name='+namestr+']').val(s);
}

//*********************************************
//目的 ：
//		欄位去除尾端空白
//參數 :
//		namestr : 欲選擇元件的Name (可用this.name)
//*********************************************
function doRightTrim(namestr){	
	var s=$('input[name='+namestr+']').val();
	if(s.length>0){
		while(s.substring(s.length-1,s.length)==" "){
			s = s.substring(0,(s.length-1));
		}		
	}
	$('input[name='+namestr+']').val(s);
}