//*********************************************
//函數功能：pop減損單明細輔助視窗
//參　　數：enterOrg機關代碼; ownership權屬; reduceCaseNo減損單電腦單號
//傳 回 值：無
//使用時機：「untla031f土地合併分割作業－合併增加單明細」
//使用時機：「untla037f土地合併分割作業－分割增加單明細」
//*********************************************
function popReduceDetail(enterOrg,ownership,reduceCaseNo){
//	alert(enterOrg+"--"+ownership+"--"+reduceCaseNo);
	var prop="";
//	var windowTop=(document.body.clientHeight-400)/2+180;
//	var windowLeft=(document.body.clientWidth-400)/2+250;
	var windowTop=300;
	var windowLeft=230;
	prop=prop+"width=760,height=295,";
	prop=prop+"top="+windowTop+",";
	prop=prop+"left="+windowLeft+",";
	prop=prop+"scrollbars=no";
	returnWindow=window.open("untla031f1.jsp?enterOrg="+enterOrg+"&ownership="+ownership+"&reduceCaseNo="+reduceCaseNo,"",prop);
}

//*********************************************
//函數功能：pop減損單明細管理資料輔助視窗
//參　　數：enterOrg機關代碼; ownership權屬; reduceCaseNo減損單電腦單號; mergeDivisionDate合併分割日期
//傳 回 值：無
//使用時機：「untla032f土地合併分割作業－合併增加單管理資料」
//使用時機：「untla038f土地合併分割作業－分割增加單管理資料」
//*********************************************
function popReduceDetail1(enterOrg,ownership,reduceCaseNo,mergeDivisionDate){
//	alert(enterOrg+"--"+ownership+"--"+reduceCaseNo+"--"+mergeDivisionDate);
	var prop="";
//	var windowTop=(document.body.clientHeight-400)/2+180;
//	var windowLeft=(document.body.clientWidth-400)/2+250;
	var windowTop=300;
	var windowLeft=230;
	prop=prop+"width=760,height=295,";
	prop=prop+"top="+windowTop+",";
	prop=prop+"left="+windowLeft+",";
	prop=prop+"scrollbars=no";
	returnWindow=window.open("untla033f.jsp?enterOrg="+enterOrg+"&ownership="+ownership+"&reduceCaseNo="+reduceCaseNo+"&mergeDivisionDate="+mergeDivisionDate,"",prop);
}


